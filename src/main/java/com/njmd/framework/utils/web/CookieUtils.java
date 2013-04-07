// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CookieUtils.java

package com.njmd.framework.utils.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @title: CookieUtils.java
 * @description:Cookie工具类 
 *
 * @author: Yao
 * 
 */
public final class CookieUtils
{

	private CookieUtils()
	{
	}

	/**
	 * 根据名称获得Cookie对象
	 * @param request Request请求对象
	 * @param name Cookie名称
	 * @return
	 */
	public static Cookie getCookie(final HttpServletRequest request, final String name)
	{
		final Cookie[] cookies = request.getCookies();
		Cookie result = null;
		if (!(cookies == null || name == null || name.length() == 0))
		{
			for (int i = 0; i < cookies.length; i++)
			{
				if (cookies[i].getName().equals(name))
				{
					result = cookies[i];
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 根据名称获得Cookie对象的值
	 * @param request Request请求对象
	 * @param name Cookie名称
	 * @return
	 */
	public static String getCookieValue(final HttpServletRequest request, final String name)
	{
		String value = "";
		if (!(name == null || name.length() < 1))
		{
			Cookie[] cookies = (Cookie[]) null;
			if (request != null)
			{
				cookies = request.getCookies();
			}
			if (cookies != null)
			{
				value = get(name, cookies);
			}
		}
		return value;
	}

	/**
	 * 从Cookie数组中获取指定名称的值
	 * @param name 
	 * @param cookies
	 * @return
	 */
	private static String get(final String name, final Cookie[] cookies)
	{
		String value = "";
		for (int i = 0; i < cookies.length; i++)
		{
			final String name2 = cookies[i].getName();
			if (name.equals(name2))
			{
				value = cookies[i].getValue();
				break;
			}
		}
		return value;
	}

	/**
	 * 设置Cookie
	 * @param name
	 * @param value
	 * @param maxAge
	 * @param res
	 */
	public static void sendCookie(final String name, final String value, final int maxAge, final HttpServletResponse res)
	{
		if (!(name == null || name.length() < 1 || !Character.isLetter(name.charAt(0)) && !Character.isDigit(name.charAt(0))))
		{
			final Cookie cookie = new Cookie(name, value);
			cookie.setMaxAge(maxAge);
			cookie.setPath("/");
			res.addCookie(cookie);

		}
	}

	/**
	 * 删除Cookie
	 * @param response
	 * @param cookie
	 */
	public static void deleteCookie(final HttpServletResponse response, final Cookie cookie)
	{
		if (cookie != null)
		{
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
}
