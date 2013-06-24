package com.njmd.zfms.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.web.SessionUtils;
import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.SessionNameConstants;
import com.njmd.zfms.web.entity.file.FileUploadInfo;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.service.FileUploadInfoService;
import com.njmd.zfms.web.service.SysLogService;
import com.njmd.zfms.web.service.SysLoginService;

/**
 * 用户终端上传文件的数据接口
 * @since 20130621
 * @author sunqw
 */
@Controller
@RequestMapping("/api")
public class ApiController {
	private static String APPCODE="njmd84588111"; 
	//目前支持的方法
	private static List<String> METHODS=new ArrayList<String>(){{
		add("getFtpPath".toLowerCase());
		add("uploadFile".toLowerCase());
	}};
	
	@Autowired
	private SysLoginService sysLoginService;

	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private FileUploadInfoService fileUploadInfoService;
	
	@RequestMapping
	@ResponseBody
	public String index(HttpServletRequest request,HttpServletResponse response,String appCode,String method,String userCode,String editCode,String uploadName,String filePath,String createTime,String fileSize,String fileTime,Model model) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		if(null==appCode || appCode.trim().length()==0 || !appCode.trim().toLowerCase().equals(APPCODE)){
			response.getWriter().print("1;终端使用的连接Key错误，请重新设置!");
			return null;
		}
		
		if(null==method || method.trim().length()==0 ||
				!(METHODS.contains(method.trim().toLowerCase()))){
			response.getWriter().print("1;您要进行的操作本服务器目前没有支持，请检查!");
			return null;
		}
		
		if(null==userCode || userCode.trim().length()==0 || null==editCode || editCode.trim().length()==0){
			response.getWriter().print("1;您请求的参数中没有包含采集人或者工作站的编号，请检查!");
			return null;
		}
		
		SysLogin userSysLogin= sysLoginService.findByUserCode(userCode.trim());
		SysLogin editSysLogin= sysLoginService.findByUserCode(editCode.trim());
		if(null==userSysLogin){
			response.getWriter().print("1;您请求的参数中，工作站编号不存在，请检查!");
			return null;
		}
		if(null==editSysLogin){
			response.getWriter().print("1;您请求的参数中，采集人编号不存在，请检查!");
			return null;
		}
		
		// 加载登录用户的相关信息到登录令牌
		LoginToken loginToken=sysLoginService.getAdminLoginToken(userSysLogin);
		// 保存登录用户信息到http session
		SessionUtils.setObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN, loginToken);
		WebContextHolder.setRequest(request);
		// 修改最后登录时间
		userSysLogin.setLoginLastTime(DateTimeUtil.getChar14());
		sysLoginService.update(userSysLogin);
		// 保存系统日志
		sysLogService.save(SysLog.OPERATE_TYPE_LOGIN, "【用户登录】用户名：" + userSysLogin.getLoginName());
		
		method=method.trim().toLowerCase();
		if(method.equals(METHODS.get(0))){
			 SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");
			 String path=userSysLogin.getSysCorp().getCorpId()+"/"+userSysLogin.getLoginId()+"/"+dateformat.format(new Date())+"/";
			 response.getWriter().print("0;"+path);
			 return null;
		}
		
		if(method.equals(METHODS.get(1))){
			if(null==filePath|| null==uploadName || null==createTime || null==fileSize||null==fileTime){
				response.getWriter().print("1;您请求的参数不全或格式错误，请检查!");
				return null;
			}else{
				FileUploadInfo fileUploadInfo=new FileUploadInfo();
				fileUploadInfo.setFileSavePath(filePath);
				fileUploadInfo.setFileUploadName(uploadName);
				fileUploadInfo.setFileCreateTime(createTime);
				fileUploadInfo.setFileTime(fileTime);
				try {
					fileUploadInfo.setFileSize(Long.valueOf(fileSize));
				} catch (Exception e) {
					fileUploadInfo.setFileSize(0l);
				}
				
				int resultTag = fileUploadInfoService.save(fileUploadInfo);
				if (resultTag == ResultConstants.SAVE_SUCCEED){
					response.getWriter().print("0;文件上传成功!");
					return null;
				}else{
					response.getWriter().print("1;文件上传失败!");
					return null;
				}
			}
		}
		return null;
	}
	
}
