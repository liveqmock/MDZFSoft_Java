package com.njmd.framework.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.njmd.framework.utils.web.WebContextHolder;

/**
 * @title:系统登录过滤器
 * 
 * @description:判断用户是否已经登录
 * 
 * @author: Yao
 * 
 */
public class LoginTokenFilter implements Filter
{
	protected Logger logger = Logger.getLogger(LoginTokenFilter.class);

	protected FilterConfig filterConfig = null;

	private String redirectURL = null;

	private final List<String> notFilterURLList = new ArrayList<String>();

	private String sessionKey = null;

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
			ServletException
	{

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String uri = request.getRequestURI();

		HttpSession session = request.getSession();

		if (WebContextHolder.getRequest() == null)
		{
			WebContextHolder.setRequest((HttpServletRequest) servletRequest);
		}

		if (this.isFilter(uri) && session.getAttribute(this.sessionKey) == null)
		{
			response.sendRedirect(request.getContextPath() + this.redirectURL);
		}
		else
		{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		WebContextHolder.setRequest(null);
		return;
	}

	public void destroy()
	{
		this.notFilterURLList.clear();
	}

	/**
	 * 检查是否需要过滤
	 * 
	 * @param request
	 * @return
	 */
	private boolean isFilter(String uri)
	{
		boolean result = true;

		if (uri == null)
			return false;

		if (uri.indexOf(this.redirectURL) >= 0)
		{
			result = false;
		}

		if (this.notFilterURLList != null && this.notFilterURLList.size() > 0)
		{
			for (String notFilterURL : this.notFilterURLList)
				if (uri.indexOf(notFilterURL) >= 0)
				{
					result = false;
					break;
				}
		}
		return result;
	}

	/**
	 * 初始化
	 */
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
		this.redirectURL = filterConfig.getInitParameter("redirectURL");// 重定向的URL路径
		this.sessionKey = filterConfig.getInitParameter("sessionKey");// 检查的SessionKey

		String notCheckURLListStr = filterConfig.getInitParameter("notFilterURL");// 不需要过滤的URL路径

		if (notCheckURLListStr != null)
		{
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ",");
			this.notFilterURLList.clear();
			while (st.hasMoreTokens())
			{
				this.notFilterURLList.add(st.nextToken());
			}
		}
	}
}
