package com.njmd.framework.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.entity.sys.SysPermission;

/**
 * @title:系统登录过滤器
 * 
 * @description:判断用户是否已经登录
 * 
 * @author: Yao
 * 
 * @since 2013.07.16
 * 
 */
public class LoginTokenFilter implements Filter
{
	protected Logger logger = Logger.getLogger(LoginTokenFilter.class);

	protected FilterConfig filterConfig = null;

	private String redirectURL = null;

	private final List<String> notFilterURLList = new ArrayList<String>();

	private String sessionKey = null;

	private List<String> mustLoginNotPermission=new ArrayList<String>();
	
	private String hasNoPermissionMsg="对不起，你没有权限访问当前资源!";
	
	/**
	 * 登陆之后，用户就可以拥有的权限。
	 */
	{
//		mustLoginNotPermission.add("/index");
		mustLoginNotPermission.add("/logout");
		
		mustLoginNotPermission.add("/reset_pwd/reset_pwd.jsp");
		mustLoginNotPermission.add("/file_mgr/file_upload");
		
		mustLoginNotPermission.add("/userMgr/view");
		mustLoginNotPermission.add("/userMgr/updatePwd");
		mustLoginNotPermission.add("/userMgr/userSelect");
	
		mustLoginNotPermission.add("/ftpMgr/ftpSelect");
		mustLoginNotPermission.add("/noticeMgr/view");
	
		mustLoginNotPermission.add("/fileMgr/fileView");
		mustLoginNotPermission.add("/fileMgr/detail");
		mustLoginNotPermission.add("/fileMgr/download");
		mustLoginNotPermission.add("/fileMgr/checkDisk");
		mustLoginNotPermission.add("/fileMgr/myFileList");
		mustLoginNotPermission.add("/fileMgr/edit");
		mustLoginNotPermission.add("/fileMgr/update");
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
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
//			System.out.println("*******" + uri + "===" + session.getAttribute(this.sessionKey));
			response.sendRedirect(request.getContextPath() + this.redirectURL);
		}
		else
		{
			//EditBy 孙强伟  at 20130711  ，修正不管单击哪个菜单项之后，current class 总是被附给首页的问题。
			//提示，request.getRequestURI和getRequestURL在经过Spring mvc 执行之后其前后值是不一样的。
			//因此为了实现功能，我在LogInterceptor.java的拦截器中添加了preHandle的方法，
			//对需要经过Spring mvc处理的URL事先取到并放到request中，只有这样才能实现需求。
			
			request.setAttribute("oldRequestURI", uri);
			
			boolean hasPermission=false;
			
			LoginToken loginToken=(LoginToken)request.getSession().getAttribute("loginToken");
			if(isFilter(uri)&&
					checkPermissionURL(uri) &&
					!uri.endsWith("/index")&&
					null!=loginToken){
				//判断权限。
				List<SysPermission> level1MenuList= loginToken.getLevel1MenuList();
				Map<Long,List<SysPermission>> level2MenuMap= loginToken.getLevel2MenuMap();
				for(SysPermission level1Menu :level1MenuList){
					List<SysPermission> level2MenuList=level2MenuMap.get(level1Menu.getPermissionId());
					for(SysPermission level2Menu:level2MenuList){
						if(uri.contains(level2Menu.getPermissionUrl()) ||
								(level2Menu.getPermissionUrl().endsWith("corpStatistics")&&uri.endsWith("corpChart") )||
								(level2Menu.getPermissionUrl().endsWith("userStatistics")&&uri.endsWith("userChart") )
								){
							hasPermission=true;
						}
					}
				}
				
				if(!hasPermission){
					String requestType = request.getHeader("X-Requested-With");  
					//如果是Ajax的请求时的返回信息
					if(null!=requestType && requestType.equals("XMLHttpRequest") && uri.contains("/userMgr/updatePwd")){
						ResultInfo resultInfo=ResultInfo.saveErrorMessage(hasNoPermissionMsg);
						
						ObjectMapper mapper = new ObjectMapper();
					    String json = mapper.writeValueAsString(resultInfo);
						
						response.setHeader("Content-type", "application/json");
						response.getWriter().write(json);
						return ;
					}
					//正常情况下的返回信息
					else{
						request.setAttribute("msg", hasNoPermissionMsg);    
			            request.getRequestDispatcher("/msg.jsp").forward(request, response); 
						return ;
					}
				}
			}
			
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
	
	private boolean checkPermissionURL(String uri){
		for (String url : this.mustLoginNotPermission)
			if (uri.indexOf(url) >= 0)
			{
				return false;
			}
		return true;
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
