<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>图片浏览</title>
</head>
  
<body>
	<iframe style="position:absolute;width:99%;height:99%;_filter:alpha(opacity=0);opacity=0;border-style:none;z-index:-1;"></iframe>
	<%
	//处理中文文件名问题
	String filePath=request.getParameter("filePath");
	filePath = java.net.URLDecoder.decode(filePath,"UTF-8");  
	String width=request.getParameter("width");
	String height=request.getParameter("height");
	%>
	<img alt="" src="<%=filePath%>" width="<%=width %>" height="<%=height %>">
</body>
</html>
