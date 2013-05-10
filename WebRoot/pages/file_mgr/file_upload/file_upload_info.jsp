<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/jquery-progressbar/js/jquery.progressbar.js"></script>
</head>
<body style="background: white;">
<%
//处理中文文件名问题
String info=request.getParameter("info");
info = java.net.URLDecoder.decode(info,"UTF-8");  
%>
<div class="mt_10" style="width:320px ;border: 1px solid #6cd858;padding: 8px;background-color: rgb(227, 255, 222)">
	<ul style="margin-top: 5px">
		<%=info %>
	</ul>
</div>
</body>
</html>