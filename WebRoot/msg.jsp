<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String message = (String) request.getAttribute("msg");
%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<title>权限错误</title>
</head>
<body>
	<div class="tips_c">
		<div class="tips_con">
		<p class="a">访问出错！</p>
		<p class="b">
		<%
	   		out.println(message+"<br/><br/>");
	    %>
	    </p>
		<p class="c"><a href="${ctx}/index" >返回首页</a></p>
		</div>
	</div>
</body>
</html>
