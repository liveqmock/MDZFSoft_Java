/**
 * 
 */
package com.njmd.zfms.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.utils.web.SessionUtils;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.SessionNameConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.service.SysLoginService;
import com.njmd.zfms.web.service.SysPermissionService;

/**
 * 
 * @title:登录操作控制器
 * @description:
 * 
 * @author: Yao
 * 
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController
{
	private static Long SYSTEM_ID = 1l;

	@Autowired
	private SysLoginService sysLoginService;

	private final String[] INFORMATION_PARAMAS = { "用户密码", "用户账号" };

	private final String REDIRECT_PATH = "/login";

	// 基础目录
	private final String BASE_DIR = "/reset_pwd/";
	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param loginName
	 * @param loginPwd
	 * @param imgCheckCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping
	@ResponseBody
	public ResultInfo login(HttpServletRequest request, String loginName, String loginPwd, String imgCheckCode)
	{
		try
		{
			int loginResultCode = sysLoginService.login(loginName, loginPwd, imgCheckCode, SYSTEM_ID, request);
			LoginToken loginToken = this.getLoginToken();

			if (loginResultCode == ResultConstants.LOGIN_SUCCESS)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(loginResultCode, INFORMATION_PARAMAS), "/pages/index.jsp");
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(loginResultCode, INFORMATION_PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error("登陆异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}
	}

	/**
	 * 用户登出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String loginOut(HttpServletRequest request)
	{
		SessionUtils.removeObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
		return "login";
	}

	@RequestMapping(value = "/editPwd")
	public String editPwd(HttpServletRequest request, Model model) throws Exception
	{
		SysLogin entity = this.getLoginToken().getSysLogin();
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return BASE_DIR + "index";
	}

	/**
	 * <p>
	 * Description:[用户修改密码]
	 * </p>
	 * 
	 * @param request
	 * @param loginName
	 * @param loginPwd
	 * @param newLoginPwd
	 * @return
	 */
	@RequestMapping(value = "/updatePwd")
	public String updatePwd(HttpServletRequest request, Model model, String loginPwd, String newLoginPwd) throws Exception
	{
		int resultTag = sysLoginService.updPassword(loginPwd, newLoginPwd, request);
		if (resultTag == ResultConstants.UPDATE_SUCCEED)
		{
			model.addAttribute(RequestNameConstants.RESULT_OBJECT, this.getLoginToken().getSysLogin());
			ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request, REDIRECT_PATH + "/editPwd");
		}
		else
		{
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request);
		}
		return UrlConstants.INFORMATION_PAGE;
	}
}
