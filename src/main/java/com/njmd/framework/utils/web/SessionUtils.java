package com.njmd.framework.utils.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @title: Session工具类
 * 
 * @description:
 * 
 * @author: Yao
 * 
 */
public final class SessionUtils
{

	public static Object getObjectAttribute(HttpSession session, String objectName)
	{
		return session.getAttribute(objectName);
	}

	public static void setObjectAttribute(HttpSession session, String objectName, Object object)
	{
		session.setAttribute(objectName, object);
	}

	public static void removeObjectAttribute(HttpSession session, String objectName)
	{
		session.removeAttribute(objectName);
	}

	public static Object getObjectAttribute(HttpServletRequest request, String objectName)
	{
		if (request != null)
		{
			if (request.getSession() != null)
				return request.getSession().getAttribute(objectName);
		}
		return null;
	}

	public static void setObjectAttribute(HttpServletRequest request, String objectName, Object object)
	{
		request.getSession().setAttribute(objectName, object);
	}

	public static void removeObjectAttribute(HttpServletRequest request, String objectName)
	{
		request.getSession().removeAttribute(objectName);
	}

	/**
	 * 检查Session中sessionKey的值，是否与checkValue值相等
	 * 
	 * @param request
	 * @param sessionKey
	 * @param checkValue
	 * @return
	 */
	public static boolean checkSessionValue(HttpServletRequest request, String sessionKey, String checkValue)
	{
		boolean result = (checkValue != null && SessionUtils.getObjectAttribute(request, sessionKey) != null && checkValue
				.compareTo(SessionUtils.getObjectAttribute(request, sessionKey).toString()) == 0);
		return result;
	}
}
