package com.njmd.framework.utils.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @title: RequestUtils.java
 * @description: Request 工具类
 * 
 * @Copyright: Copyright (c) 2009-8-10
 * @Company: 南京欣网视讯软件技术有限公司
 * 
 * @description: Yao
 * 
 */
public final class RequestUtils
{

	public static String getParameter(HttpServletRequest request, String name)
	{
		return getParameter(request, name, false);
	}

	public static String getParameter(HttpServletRequest request, String name, boolean emptyStringsOK)
	{
		String temp = request.getParameter(name);
		if (temp != null)
		{
			if ("".equals(temp) && !emptyStringsOK)
				return null;
			else
				return temp;
		}
		else
		{
			return null;
		}
	}

	public static String[] getParameters(HttpServletRequest request, String name)
	{
		if (name == null)
			return new String[0];
		String[] paramValues = request.getParameterValues(name);
		if (paramValues == null || paramValues.length == 0)
			return new String[0];
		List<String> values = new ArrayList<String>(paramValues.length);
		for (int i = 0; i < paramValues.length; i++)
			if (paramValues[i] != null && !"".equals(paramValues[i]))
				values.add(paramValues[i]);

		return values.toArray(new String[0]);
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String name)
	{
		return getBooleanParameter(request, name, false);
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String name, boolean defaultVal)
	{
		String temp = request.getParameter(name);
		if ("true".equals(temp) || "on".equals(temp))
			return true;
		if ("false".equals(temp) || "off".equals(temp))
			return false;
		else
			return defaultVal;
	}

	public static int getIntParameter(HttpServletRequest request, String name, int defaultNum)
	{
		String temp = request.getParameter(name);
		if (temp != null && !"".equals(temp))
		{
			int num = defaultNum;
			try
			{
				num = Integer.parseInt(temp);
			}
			catch (Exception exception)
			{
			}
			return num;
		}
		else
		{
			return defaultNum;
		}
	}

	public static int[] getIntParameters(HttpServletRequest request, String name, int defaultNum)
	{
		String[] paramValues = request.getParameterValues(name);
		if (paramValues == null || paramValues.length == 0)
			return new int[0];
		int[] values = new int[paramValues.length];
		for (int i = 0; i < paramValues.length; i++)
			try
			{
				values[i] = Integer.parseInt(paramValues[i]);
			}
			catch (Exception e)
			{
				values[i] = defaultNum;
			}

		return values;
	}

	public static double getDoubleParameter(HttpServletRequest request, String name, double defaultNum)
	{
		String temp = request.getParameter(name);
		if (temp != null && !"".equals(temp))
		{
			double num = defaultNum;
			try
			{
				num = Double.parseDouble(temp);
			}
			catch (Exception exception)
			{
			}
			return num;
		}
		else
		{
			return defaultNum;
		}
	}

	public static long getLongParameter(HttpServletRequest request, String name, long defaultNum)
	{
		String temp = request.getParameter(name);
		if (temp != null && !"".equals(temp))
		{
			long num = defaultNum;
			try
			{
				num = Long.parseLong(temp);
			}
			catch (Exception exception)
			{
			}
			return num;
		}
		else
		{
			return defaultNum;
		}
	}

	public static long[] getLongParameters(HttpServletRequest request, String name, long defaultNum)
	{
		String[] paramValues = request.getParameterValues(name);
		if (paramValues == null || paramValues.length == 0)
			return new long[0];
		long[] values = new long[paramValues.length];
		for (int i = 0; i < paramValues.length; i++)
			try
			{
				values[i] = Long.parseLong(paramValues[i]);
			}
			catch (Exception e)
			{
				values[i] = defaultNum;
			}

		return values;
	}

	public static String getAttribute(HttpServletRequest request, String name)
	{
		return getAttribute(request, name, false);
	}

	public static String getAttribute(HttpServletRequest request, String name, boolean emptyStringsOK)
	{
		String temp = (String) request.getAttribute(name);
		if (temp != null)
		{
			if ("".equals(temp) && !emptyStringsOK)
				return null;
			else
				return temp;
		}
		else
		{
			return null;
		}
	}

	public static boolean getBooleanAttribute(HttpServletRequest request, String name)
	{
		String temp = (String) request.getAttribute(name);
		return temp != null && "true".equals(temp);
	}

	public static int getIntAttribute(HttpServletRequest request, String name, int defaultNum)
	{
		String temp = (String) request.getAttribute(name);
		if (temp != null && !"".equals(temp))
		{
			int num = defaultNum;
			try
			{
				num = Integer.parseInt(temp);
			}
			catch (Exception exception)
			{
			}
			return num;
		}
		else
		{
			return defaultNum;
		}
	}

	public static long getLongAttribute(HttpServletRequest request, String name, long defaultNum)
	{
		String temp = (String) request.getAttribute(name);
		if (temp != null && !"".equals(temp))
		{
			long num = defaultNum;
			try
			{
				num = Long.parseLong(temp);
			}
			catch (Exception exception)
			{
			}
			return num;
		}
		else
		{
			return defaultNum;
		}
	}

	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
