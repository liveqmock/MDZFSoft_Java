package com.njmd.framework.utils.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.SessionNameConstants;

public class WebContextHolder
{
	private static Logger logger = Logger.getLogger(WebContextHolder.class);

	private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	public static void setRequest(HttpServletRequest request)
	{
		requestHolder.set(request);
	}

	public static HttpServletRequest getRequest()
	{
		return requestHolder.get();
	}

	/**
	 * 获得当前登录用户会话
	 * 
	 * @return
	 */
	public static LoginToken getCurrLoginToken()
	{

		HttpServletRequest request = requestHolder.get();
		if (request != null)
			return (LoginToken) SessionUtils.getObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
		else
		{
			System.out.println("null");
			return null;
		}
	}
}
