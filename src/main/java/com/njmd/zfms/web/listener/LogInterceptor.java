package com.njmd.zfms.web.listener;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.exec.util.MapUtils;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.web.RequestUtils;
import com.njmd.framework.utils.web.SessionUtils;
import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.annotation.Permission;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.ConfigConstants;
import com.njmd.zfms.web.constants.SessionNameConstants;
import com.njmd.zfms.web.controller.ApiController;
import com.njmd.zfms.web.controller.CorpMgrController;
import com.njmd.zfms.web.controller.DevFacturerMgrController;
import com.njmd.zfms.web.controller.DevInfoMgrController;
import com.njmd.zfms.web.controller.DevTypeMgrController;
import com.njmd.zfms.web.controller.FileMgrController;
import com.njmd.zfms.web.controller.FileTypeMgrController;
import com.njmd.zfms.web.controller.FtpMgrController;
import com.njmd.zfms.web.controller.IndexController;
import com.njmd.zfms.web.controller.LogMgrController;
import com.njmd.zfms.web.controller.LoginController;
import com.njmd.zfms.web.controller.NoticeMgrController;
import com.njmd.zfms.web.controller.ReportStatisticsController;
import com.njmd.zfms.web.controller.RoleMgrController;
import com.njmd.zfms.web.controller.SysMonitorController;
import com.njmd.zfms.web.controller.SysPermissionController;
import com.njmd.zfms.web.controller.UserMgrController;
import com.njmd.zfms.web.entity.dev.DevFacturerInfo;
import com.njmd.zfms.web.entity.dev.DevInfo;
import com.njmd.zfms.web.entity.dev.DevTypeInfo;
import com.njmd.zfms.web.entity.file.FileTypeInfo;
import com.njmd.zfms.web.entity.file.FileUploadInfo;
import com.njmd.zfms.web.entity.notice.NoticeInfo;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysFtp;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.entity.sys.SysRole;
import com.njmd.zfms.web.service.SysLogService;

/**
 * 日志记录拦截器
 * @author sunqw
 * @since 2013.07.16
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private SysLogService sysLogService;
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			final ModelAndView modelAndView) throws Exception {
		
		if(!(handler instanceof HandlerMethod)){
			return ;
		}
		
		final HandlerMethod handlerMethod=(HandlerMethod)handler;
		//获得请求地址中以{xx}的参数值映射对
		final Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		//获得在保存Entity时
		final Object entity=WebContextHolder.getSavedObject();
		//获得HttpServletRequest请求的参数对
		final Map parameterMap=request.getParameterMap();
		//获得当前登陆的用户信息
		final LoginToken loginToken=(LoginToken)SessionUtils.getObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
		
		SysLog sysLog=buildSysLog();
		
		//通过启动另外一个线程处理日志入库问题，这样可以提高主线程的处理速度
		new Thread(new LogRunnable(sysLog,MapUtils.copy(parameterMap), pathVariables,entity,loginToken, handlerMethod)).start();
		
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 日志入库处理线程
	 * @author sunqw
	 *
	 */
	class LogRunnable implements Runnable {
		
		private SysLog sysLog;
		private Map parameterMap;
		private Map pathVariables;
		private LoginToken loginToken;
		private HandlerMethod handlerMethod;
		private Object entity;
		private Permission permission;
		
		/**
		 * 
		 * @param sysLog	//日志Entity
		 * @param parameterMap	//Request请求参数Map
		 * @param pathVariables	//Request中URL{xxx} 中的参数对
		 * @param record	//Entity的数据库记录ID
		 * @param loginToken	//登陆用户
		 * @param handlerMethod	//处理当前请求的方法
		 */
		public LogRunnable(SysLog sysLog,Map parameterMap,Map pathVariables,Object entity,LoginToken loginToken,HandlerMethod handlerMethod){
			this.sysLog=sysLog;
			this.parameterMap=parameterMap;
			this.pathVariables=pathVariables;
			this.loginToken=loginToken;
			this.handlerMethod=handlerMethod;
			this.entity=entity;
		}
		
		@Override
		public void run() {
			try {
				Class clazz=handlerMethod.getMethod().getDeclaringClass();
				sysLog.setOperResource(clazz.getName());
				sysLog.setOperAction(handlerMethod.getMethod().getName());
				if(null!=loginToken){
					if(clazz == LoginController.class){
						logLoginController(sysLog);
					}else if(clazz==UserMgrController.class){
						logUserMgrController(sysLog);
					}else if(clazz==CorpMgrController.class){
						logCorpMgrController(sysLog);
					}else if(clazz==FtpMgrController.class){
						logFtpMgrController(sysLog);
					}else if(clazz==RoleMgrController.class){
						logRoleMgrController(sysLog);
					}else if(clazz==DevTypeMgrController.class){
						logDevTypeMgrController(sysLog);
					}else if(clazz==DevFacturerMgrController.class){
						logDevFacturerMgrController(sysLog);
					}else if(clazz==DevInfoMgrController.class){
						logDevInfoMgrController(sysLog);
					}else if(clazz==FileTypeMgrController.class){
						logFileTypeMgrController(sysLog);
					}else if(clazz==NoticeMgrController.class){
						logNoticeMgrController(sysLog);
					}else if(clazz==IndexController.class){
						logIndexController(sysLog);
					}else if(clazz==SysPermissionController.class){
						logSysPermissionController(sysLog);
					}else if(clazz==LogMgrController.class){
						logLogMgrController(sysLog);
					}else if(clazz==ReportStatisticsController.class){
						logReportStatisticsController(sysLog);
					}else if(clazz==FileMgrController.class){
						logFileMgrController(sysLog);
					}else if(clazz==ApiController.class){
						logApiController(sysLog);
					}else if(clazz==SysMonitorController.class){
						logSysMonitorController(sysLog);
					}
					
					//如果信息的内容不存在，则表示不需要进行日志记录
					if(sysLog.getOperDesc().length()>0)
						try {
							sysLogService.save(sysLog);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			} catch (Exception e) {
			} 
		}
		

		/**
		 * 处理IndexController.class中的方法日志记录
		 */
		private void logIndexController(SysLog sysLog) {
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【显示我的主页】");
				sysLog.setOperRecordId("");
			}
		}

		/**
		 * 处理LoginController.class中的方法日志记录
		 */
		private void logLoginController(SysLog sysLog) {
			if(handlerMethod.getMethod().getName().equals("login")){
				if(null==loginToken){
					String loginName=parameterMap.get(LoginController.LOGINNAME)==null?"":((String[])parameterMap.get(LoginController.LOGINNAME))[0];
					sysLog.setOperType(SysLog.OPERATE_TYPE_LOGIN);
					sysLog.setOperDesc("【用户登录】失败,登陆名:" + loginName);
					sysLog.setOperRecordId("");
				}else{
					sysLog.setOperType(SysLog.OPERATE_TYPE_LOGIN);
					sysLog.setOperDesc("【用户登录】成功,登陆用户:"+loginToken.getSysLogin().getUserName());
					sysLog.setOperRecordId(""+loginToken.getSysLogin().getLoginId());
				}
			}else if(handlerMethod.getMethod().getName().equals("loginOut")){
				sysLog.setOperType(SysLog.OPERATE_TYPE_LOGOUT);
				sysLog.setOperDesc("【用户登出】用户名:"+loginToken.getSysLogin().getUserName());
				sysLog.setOperRecordId(""+loginToken.getSysLogin().getLoginId());
			}
		}
		
		/**
		 * 处理UserMgrController.class中的方法日志记录
		 */
		private void logUserMgrController(SysLog sysLog) {
			SysLogin sysLogin=(SysLogin)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【用户列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("view")  && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【查看用户】");
				sysLog.setOperRecordId("");
				if(null!=sysLogin){
					sysLog.setOperDesc("【查看用户】用户名:"+sysLogin.getLoginName());
					sysLog.setOperRecordId(""+sysLogin.getLoginId());
				}
			}else if(handlerMethod.getMethod().getName().equals("save")&& 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				sysLog.setOperRecordId("");
				if(null!=sysLogin){
					sysLog.setOperDesc("【新增用户】用户名:"+sysLogin.getLoginName());
					sysLog.setOperRecordId(""+sysLogin.getLoginId());
				}else{
					sysLog.setOperDesc("【新增用户】失败");
				}
			}else if(handlerMethod.getMethod().getName().equals("edit")&& 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("");
				sysLog.setOperDesc("【查看用户】");
				if(null!=sysLogin){
					sysLog.setOperDesc("【查看用户】用户名:"+sysLogin.getLoginName());
					sysLog.setOperRecordId(""+sysLogin.getLoginId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update")&& 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新用户】");
				sysLog.setOperRecordId("");
				if(null!=sysLogin){
					sysLog.setOperDesc("【更新用户】用户名:"+sysLogin.getLoginName());
					sysLog.setOperRecordId(""+sysLogin.getLoginId());
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==sysLogin){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除用户】失败,用户编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除用户】用户名称:"+sysLogin.getLoginName());
					sysLog.setOperRecordId(""+sysLogin.getLoginId());
				}
			}else if(handlerMethod.getMethod().getName().equals("batchDelete") && 1==ConfigConstants.LOG_DELETE){
				String[] ids=(String[])parameterMap.get("id");
				
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				sysLog.setOperDesc("【批量删除用户】");
				sysLog.setOperRecordId(Arrays.toString(ids));
			}else if(handlerMethod.getMethod().getName().equals("resetPwd") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				if(null==sysLogin){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【重置用户密码】失败,用户编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【重置用户密码】用户名称:"+sysLogin.getLoginName());
					sysLog.setOperRecordId(""+sysLogin.getLoginId());
				}
			}else if(handlerMethod.getMethod().getName().equals("userSelect") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【选择用户】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("updatePwd") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				if(null==sysLogin){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【修改用户密码】失败,用户编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【修改用户密码】用户名称:"+sysLogin.getLoginName());
					sysLog.setOperRecordId(""+sysLogin.getLoginId());
				}
			}
		}
		
		/**
		 * 处理CorpMgrController.class中的方法日志记录
		 */
		private void logCorpMgrController(SysLog sysLog) {
			SysCorp sysCorp=(SysCorp)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【部门列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				if(null!=sysCorp){
					sysLog.setOperDesc("【新增部门】部门名称:"+sysCorp.getCorpName());
					sysLog.setOperRecordId(""+sysCorp.getCorpId());
				}else{
					sysLog.setOperDesc("【新增部门】失败");
					sysLog.setOperRecordId("");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==sysCorp){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除部门】失败,部门编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除部门】部门名称:"+sysCorp.getCorpName());
					sysLog.setOperRecordId(""+sysCorp.getCorpId());
				}
			}else if(handlerMethod.getMethod().getName().equals("batchDelete") && 1==ConfigConstants.LOG_DELETE){
				String[] ids=(String[])parameterMap.get("id");
								
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				sysLog.setOperDesc("【批量删除部门】");
				sysLog.setOperRecordId(Arrays.toString(ids));
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("查看部门");
				sysLog.setOperDesc("");
				if(null!=sysCorp){
					sysLog.setOperDesc("【查看部门】部门名称:"+sysCorp.getCorpName());
					sysLog.setOperRecordId(""+sysCorp.getCorpId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新部门】");
				sysLog.setOperRecordId("");
				if(null!=sysCorp){
					sysLog.setOperDesc("【更新部门】部门名称:"+sysCorp.getCorpName());
					sysLog.setOperRecordId(""+sysCorp.getCorpId());
				}
			}
		}
		
		/**
		 * 处理SysPermissionController.class中的方法日志记录
		 */
		private void logSysPermissionController(SysLog sysLog) {
			SysPermission sysPermission=(SysPermission)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【菜单列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				sysLog.setOperRecordId("");
				if(null!=sysPermission){
					sysLog.setOperDesc("【新增菜单】菜单名称:"+sysPermission.getPermissionName());
					sysLog.setOperRecordId(""+sysPermission.getPermissionId());
				}else{
					sysLog.setOperDesc("【新增菜单】失败");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==sysPermission){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除菜单】失败,菜单编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除菜单】菜单名称:"+sysPermission.getPermissionName());
					sysLog.setOperRecordId(""+sysPermission.getPermissionId());
				}
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("查看菜单");
				if(null!=sysPermission){
					sysLog.setOperDesc("【查看菜单】菜单名称:"+sysPermission.getPermissionName());
					sysLog.setOperRecordId(""+sysPermission.getPermissionId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新菜单】");
				sysLog.setOperRecordId("");
				if(null!=sysPermission){
					sysLog.setOperDesc("【更新菜单】菜单名称:"+sysPermission.getPermissionName());
					sysLog.setOperRecordId(""+sysPermission.getPermissionId());
				}
			}
		}
		
		/**
		 * 处理FtpMgrController.class中的方法日志记录
		 */
		private void logFtpMgrController(SysLog sysLog){
			SysFtp sysFtp=(SysFtp)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【文件服务器列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				sysLog.setOperRecordId("");
				if(null!=sysFtp){
					sysLog.setOperDesc("【新增文件服务器】文件服务器:"+sysFtp.getServerName());
					sysLog.setOperRecordId(""+sysFtp.getFtpId());
				}else{
					sysLog.setOperDesc("【新增文件服务器】失败");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==sysFtp){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除文件服务器】失败,文件服务器编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除文件服务器】文件服务器名称:"+sysFtp.getServerName());
					sysLog.setOperRecordId(""+sysFtp.getFtpId());
				}
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看文件服务器】");
				if(null!=sysFtp){
					sysLog.setOperDesc("【查看文件服务器】文件服务器:"+sysFtp.getServerName());
					sysLog.setOperRecordId(""+sysFtp.getFtpId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新文件服务器】");
				sysLog.setOperRecordId("");
				if(null!=sysFtp){
					sysLog.setOperDesc("【更新文件服务器】文件服务器:"+sysFtp.getServerName());
					sysLog.setOperRecordId(""+sysFtp.getFtpId());
				}
			}else if(handlerMethod.getMethod().getName().equals("ftpSelect") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【选择文件服务器】");
				sysLog.setOperRecordId("");
			}
		}
		
		/**
		 * 处理FtpMgrController.class中的方法日志记录
		 */
		private void logRoleMgrController(SysLog sysLog){
			SysRole sysRole=(SysRole)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【角色列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				sysLog.setOperRecordId("");
				if(null!=sysRole){
					sysLog.setOperDesc("【新增角色】角色名称:"+sysRole.getRoleName());
					sysLog.setOperRecordId(""+sysRole.getRoleId());
				}else{
					sysLog.setOperDesc("【新增角色】失败");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==sysRole){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除角色】失败,角色编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除角色】角色名称:"+sysRole.getRoleName());
					sysLog.setOperRecordId(""+sysRole.getRoleId());
				}
			}else if(handlerMethod.getMethod().getName().equals("batchDelete") && 1==ConfigConstants.LOG_DELETE){
				String[] ids=(String[])parameterMap.get("id");
				
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				sysLog.setOperDesc("【批量删除角色】");
				sysLog.setOperRecordId(Arrays.toString(ids));
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看角色】");
				if(null!=sysRole){
					sysLog.setOperDesc("【查看角色】角色名称:"+sysRole.getRoleName());
					sysLog.setOperRecordId(""+sysRole.getRoleId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新角色】");
				sysLog.setOperRecordId("");
				if(null!=sysRole){
					sysLog.setOperDesc("【更新角色】角色名称:"+sysRole.getRoleName());
					sysLog.setOperRecordId(""+sysRole.getRoleId());
				}
			}
		}
		
		/**
		 * 处理DevTypeMgrController.class中的方法日志记录
		 */
		private void logDevTypeMgrController(SysLog sysLog){
			DevTypeInfo devTypeInfo=(DevTypeInfo)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【设备类型列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save")&& 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				if(null!=devTypeInfo){
					sysLog.setOperDesc("【新增设备类型】类型名称:"+devTypeInfo.getDevTypeName());
					sysLog.setOperRecordId(""+devTypeInfo.getDevTypeId());
				}else{
					sysLog.setOperDesc("【新增设备类型】失败");
					sysLog.setOperRecordId("");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==devTypeInfo){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除设备类型】失败,类型编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除设备类型】类型名称:"+devTypeInfo.getDevTypeName());
					sysLog.setOperRecordId(""+devTypeInfo.getDevTypeId());
				}
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看设备类型】");
				if(null!=devTypeInfo){
					sysLog.setOperDesc("【查看设备类型】类型名称:"+devTypeInfo.getDevTypeName());
					sysLog.setOperRecordId(""+devTypeInfo.getDevTypeId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新设备类型】");
				sysLog.setOperRecordId("");
				if(null!=devTypeInfo){
					sysLog.setOperDesc("【更新设备类型】类型名称:"+devTypeInfo.getDevTypeName());
					sysLog.setOperRecordId(""+devTypeInfo.getDevTypeId());
				}
			}
		}
		
		/**
		 * 处理DevFacturerMgrController.class中的方法日志记录
		 */
		private void logDevFacturerMgrController(SysLog sysLog) {
			DevFacturerInfo devFacturerInfo=(DevFacturerInfo)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【设备厂商列表】");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				sysLog.setOperRecordId("");
				if(null!=devFacturerInfo){
					sysLog.setOperDesc("【新增厂商】厂商名称:"+devFacturerInfo.getDevFacturerName());
					sysLog.setOperRecordId(""+devFacturerInfo.getDevFacturerId());
				}else{
					sysLog.setOperDesc("【新增厂商】失败");
					sysLog.setOperRecordId("");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==devFacturerInfo){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除厂商】失败,厂商编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除厂商】厂商名称:"+devFacturerInfo.getDevFacturerName());
					sysLog.setOperRecordId(""+devFacturerInfo.getDevFacturerId());
				}
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看厂商】");
				if(null!=devFacturerInfo){
					sysLog.setOperDesc("【查看厂商】厂商名称:"+devFacturerInfo.getDevFacturerName());
					sysLog.setOperRecordId(""+devFacturerInfo.getDevFacturerId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新厂商】");
				sysLog.setOperRecordId("");
				if(null!=devFacturerInfo){
					sysLog.setOperDesc("【更新厂商】厂商名称:"+devFacturerInfo.getDevFacturerName());
					sysLog.setOperRecordId(""+devFacturerInfo.getDevFacturerId());
				}
			}
		}
		
		/**
		 * 处理DevInfoMgrController.class中的方法日志记录
		 */
		private void logDevInfoMgrController(SysLog sysLog) {
			DevInfo devInfo=(DevInfo)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【设备列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				if(null!=devInfo){
					sysLog.setOperDesc("【新增设备】设备编号:"+devInfo.getDevNo());
					sysLog.setOperRecordId(""+devInfo.getDevId());
				}else{
					sysLog.setOperDesc("【新增设备】失败");
					sysLog.setOperRecordId("");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==devInfo){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除设备】失败,设备编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除设备】设备编号:"+devInfo.getDevNo());
					sysLog.setOperRecordId(""+devInfo.getDevId());
				}
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看设备】");
				if(null!=devInfo){
					sysLog.setOperDesc("【查看设备】设备编号:"+devInfo.getDevNo());
					sysLog.setOperRecordId(""+devInfo.getDevId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新设备】");
				sysLog.setOperRecordId("");
				if(null!=devInfo){
					sysLog.setOperDesc("【更新设备】类型编号:"+devInfo.getDevNo());
					sysLog.setOperRecordId(""+devInfo.getDevId());
				}
			}
		}
		
		/**
		 * 处理FileTypeMgrController.class中的方法日志记录
		 */
		private void logFileTypeMgrController(SysLog sysLog) {
			FileTypeInfo fileTypeInfo=(FileTypeInfo)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【文件类型列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				if(null!=fileTypeInfo){
					sysLog.setOperDesc("【新增文件类型】类型名称:"+fileTypeInfo.getTypeName());
					sysLog.setOperRecordId(""+fileTypeInfo.getTypeId());
				}else{
					sysLog.setOperDesc("【新增文件类型】失败");
					sysLog.setOperRecordId("");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==fileTypeInfo){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除文件类型】失败,类型编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除文件类型】类型名称:"+fileTypeInfo.getTypeName());
					sysLog.setOperRecordId(""+fileTypeInfo.getTypeId());
				}
			}else if(handlerMethod.getMethod().getName().equals("edit") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看文件类型】");
				if(null!=fileTypeInfo){
					sysLog.setOperDesc("【查看文件类型】类型名称:"+fileTypeInfo.getTypeName());
					sysLog.setOperRecordId(""+fileTypeInfo.getTypeId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新文件类型】");
				sysLog.setOperRecordId("");
				if(null!=fileTypeInfo){
					sysLog.setOperDesc("【更新文件类型】类型名称:"+fileTypeInfo.getTypeName());
					sysLog.setOperRecordId(""+fileTypeInfo.getTypeId());
				}
			}
		}
		
		/**
		 * 处理NoticeMgrController.class中的方法日志记录
		 */
		private void logNoticeMgrController(SysLog sysLog) {
			NoticeInfo noticeInfo=(NoticeInfo)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【新闻列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				if(null!=noticeInfo){
					sysLog.setOperDesc("【新增新闻】新闻标题:"+noticeInfo.getNoticeTitle());
					sysLog.setOperRecordId(""+noticeInfo.getNoticeId());
				}else{
					sysLog.setOperDesc("【新增新闻】失败");
					sysLog.setOperRecordId("");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==noticeInfo){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除新闻】失败,新闻编码:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除新闻】新闻标题:"+noticeInfo.getNoticeTitle());
					sysLog.setOperRecordId(""+noticeInfo.getNoticeId());
				}
			}else if((handlerMethod.getMethod().getName().equals("edit")
					||handlerMethod.getMethod().getName().equals("view")) && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看新闻】");
				if(null!=noticeInfo){
					sysLog.setOperDesc("【查看新闻】新闻标题:"+noticeInfo.getNoticeTitle());
					sysLog.setOperRecordId(""+noticeInfo.getNoticeId());
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新新闻】");
				sysLog.setOperRecordId("");
				if(null!=noticeInfo){
					sysLog.setOperDesc("【更新新闻】新闻标题:"+noticeInfo.getNoticeTitle());
					sysLog.setOperRecordId(""+noticeInfo.getNoticeId());
				}
			}else if(handlerMethod.getMethod().getName().equals("batchDelete") && 1==ConfigConstants.LOG_DELETE){
				String[] ids=(String[])parameterMap.get("id");
				
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				sysLog.setOperDesc("【批量删除新闻】");
				sysLog.setOperRecordId(Arrays.toString(ids));
			}
		}
		
		/**
		 * 处理LogMgrController.class中的方法日志记录
		 */
		private void logLogMgrController(SysLog sysLog){
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【日志列表】");
				sysLog.setOperRecordId("");
			}
		}
		
		/**
		 * 处理ReportStatisticsController.class中的方法日志记录
		 */
		private void logReportStatisticsController(SysLog sysLog) {
			if((handlerMethod.getMethod().getName().equals("index")
					|| handlerMethod.getMethod().getName().equals("corpStatistics")
//					|| handlerMethod.getMethod().getName().equals("corpChart")
					) && 1==ConfigConstants.LOG_VIEW
					){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【部门统计】");
				sysLog.setOperRecordId("");
			}else if((handlerMethod.getMethod().getName().equals("userStatistics")
//					|| handlerMethod.getMethod().getName().equals("userChart")
					) && 1==ConfigConstants.LOG_VIEW
					){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【警员统计】");
				sysLog.setOperRecordId("");
			}
		}
		
		/**
		 * 处理FileMgrController.class中的方法日志记录
		 */
		private void logFileMgrController(SysLog sysLog){
			FileUploadInfo fileUploadInfo=(FileUploadInfo)entity;
			if(handlerMethod.getMethod().getName().equals("index") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【文件列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("save") && 1==ConfigConstants.LOG_ADD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_ADD);
				if(null!=fileUploadInfo){
					sysLog.setOperDesc("【新增文件】文件名称:"+fileUploadInfo.getFileUploadName());
					sysLog.setOperRecordId(""+fileUploadInfo.getFileId());
				}else{
					sysLog.setOperDesc("【新增文件】失败");
					sysLog.setOperRecordId("");
				}
			}else if(handlerMethod.getMethod().getName().equals("delete") && 1==ConfigConstants.LOG_DELETE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				if(null==fileUploadInfo){
					String id=pathVariables.get("id")==null?"":(String)pathVariables.get("id");
									
					sysLog.setOperDesc("【删除文件】失败,文件编号:"+id);
					sysLog.setOperRecordId(id);
				}else{
					sysLog.setOperDesc("【删除文件】文件名称:"+fileUploadInfo.getFileUploadName());
					sysLog.setOperRecordId(""+fileUploadInfo.getFileId());
				}
			}else if((handlerMethod.getMethod().getName().equals("detail")
					||handlerMethod.getMethod().getName().equals("edit")
					||handlerMethod.getMethod().getName().equals("fileView")) 
					&& 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperRecordId("【查看文件】");
				if(null!=fileUploadInfo){
					sysLog.setOperRecordId(""+fileUploadInfo.getFileId());
					String message="【查看文件】文件名称:"+fileUploadInfo.getFileUploadName();
					if(fileUploadInfo.getPoliceCode()!=null && fileUploadInfo.getPoliceCode().length()>0)
						message=message+"接警编号:"+fileUploadInfo.getPoliceCode();
					
					sysLog.setOperDesc(message);
				}
			}else if(handlerMethod.getMethod().getName().equals("update") && 1==ConfigConstants.LOG_UPDATE){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【更新文件】");
				sysLog.setOperRecordId("");
				
				if(null!=fileUploadInfo){
					String message="【更新文件】";
					sysLog.setOperRecordId(""+fileUploadInfo.getFileId());
					message=message+"文件名称:"+fileUploadInfo.getFileUploadName();
					if(fileUploadInfo.getPoliceCode()!=null && fileUploadInfo.getPoliceCode().length()>0)
						message=message+"接警编号:"+fileUploadInfo.getPoliceCode();
					
					sysLog.setOperDesc(message);
				}
			}else if(handlerMethod.getMethod().getName().equals("batchDelete") 
					&& 1==ConfigConstants.LOG_DELETE){
				String[] ids=(String[])parameterMap.get("id");
				
				sysLog.setOperType(SysLog.OPERATE_TYPE_DELETE);
				sysLog.setOperDesc("【批量删除文件】");
				sysLog.setOperRecordId(Arrays.toString(ids));
			}else if(handlerMethod.getMethod().getName().equals("download") && 1==ConfigConstants.LOG_DOWNLOAD){
				sysLog.setOperType(SysLog.OPERATE_TYPE_UPDATE);
				sysLog.setOperDesc("【下载文件】");
				sysLog.setOperRecordId("");
				
				if(null!=fileUploadInfo){
					sysLog.setOperRecordId(""+fileUploadInfo.getFileId());
					String message="【下载文件】文件名称:"+fileUploadInfo.getFileUploadName();
					if(fileUploadInfo.getPoliceCode()!=null && fileUploadInfo.getPoliceCode().length()>0)
						message=message+"接警编号:"+fileUploadInfo.getPoliceCode();
					
					sysLog.setOperDesc(message);
				}
			}else if(handlerMethod.getMethod().getName().equals("myFileList") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【我的上传文件列表】");
				sysLog.setOperRecordId("");
			}else if(handlerMethod.getMethod().getName().equals("deleteList") && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【文件删除列表】");
				sysLog.setOperRecordId("");
			}
		}
		
		/**
		 * 处理ApiController.class中的方法日志记录
		 */
		private void logApiController(SysLog sysLog){
		
		}
		
		/**
		 * 处理SysMonitorController.class中的方法日志记录
		 */
		private void logSysMonitorController(SysLog sysLog) {
			if((handlerMethod.getMethod().getName().equals("index")
					||handlerMethod.getMethod().getName().equals("chart")) && 1==ConfigConstants.LOG_VIEW){
				sysLog.setOperType(SysLog.OPERATE_TYPE_VIEW);
				sysLog.setOperDesc("【系统监控列表】");
				sysLog.setOperRecordId("");
			}
		}

	}
	
	/**
	 * 根据当前用户登陆对象构建系统日志对象
	 * @return
	 */
	private SysLog buildSysLog(){
		LoginToken loginToken = WebContextHolder.getCurrLoginToken();
		HttpServletRequest request = WebContextHolder.getRequest();
		SysLog sysLog = new SysLog();
		sysLog.setOperIp(RequestUtils.getIpAddr(request));
		sysLog.setOperTime(DateTimeUtil.getChar14());
		if(null!=loginToken){
			sysLog.setOperUserId(loginToken.getSysLogin().getLoginId());
			sysLog.setOperUserName(loginToken.getSysLogin().getUserName());
			sysLog.setOperUserCode(loginToken.getSysLogin().getUserCode());
			if (loginToken.getSysLogin().getSysCorp() != null)
			{
				sysLog.setOperCorpId(loginToken.getSysLogin().getSysCorp().getCorpId());
				sysLog.setOperCorpName(loginToken.getSysLogin().getSysCorp().getCorpName());
			}
			sysLog.setSystemId(loginToken.getSysLogin().getSystemId());
		}
		return sysLog;
	}
	
}