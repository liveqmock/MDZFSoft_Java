<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.njmd.zfms.web.constants.SessionNameConstants"%>
<%@page import="com.njmd.framework.utils.web.SessionUtils"%>
<%@page import="com.njmd.zfms.web.commons.LoginToken"%>
<%@page import="com.njmd.zfms.web.entity.sys.SysLogin"%>
<%@page import="java.util.List"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
	<%@ include file="/common/header.jsp"%>
	<script type="text/javascript" src="${ctx}/plugins/jquery/jquery.jclock.js"></script>
	<LINK href="${ctx}/css/top.css" rel="stylesheet" type="text/css">
</HEAD>
<body>
	<%
	    LoginToken loginToken = (LoginToken) SessionUtils.getObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
		String userName ="";
		Integer userType = Integer.valueOf(0);
		if(loginToken!=null)
		{
			userName = loginToken.getSysLogin().getUserName();
			userType = loginToken.getSysLogin().getUserType();
		}
	%>
   <div class="top_bg">
      <div class="logo"></div>
      <div class="quit" onclick="logout()"></div><!--退出系统-->
      <div class="top_menu">
        <div class="top_menu_left"></div>
        <div class="top_menu_center">
           <a href="${ctx }/map" target="mainFrame">首    页</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
           <a href="${ctx }/login/editPwd" target="mainFrame">重设密码</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
           <a href="#" target="mainFrame">帮    助</a>
        </div>
        <div class="top_menu_right"></div>
      </div>
      <div class="user_info"><span id="goodView"></span><a href="#1" style="font-weight:bold;"><%=userName %></a> &nbsp;&nbsp; <span id="curDate"></span></div>
   </div>
   <form name="logout_form" method="post" action="${ctx}/login/logout" target="_top">
	</form>
   	<script>
   	function logout()
   	{
   		if (confirm("确认退出系统吗？"))
   		{
   			document.logout_form.submit();
   		}
   	}

   	$('#curDate').jclock({withDate:true, withWeek:true})
    var now = new Date();
    var hour = now.getHours();
   	if(hour < 6){$("#goodView").text("凌晨好！")} 
   	else if (hour < 9){$("#goodView").text("早上好！")} 
   	else if (hour < 12){$("#goodView").text("上午好！")} 
   	else if (hour < 14){$("#goodView").text("中午好！")} 
   	else if (hour < 17){$("#goodView").text("下午好！")} 
   	else if (hour < 19){$("#goodView").text("傍晚好！")} 
   	else if (hour < 22){$("#goodView").text("晚上好！")} 
   	else {$("#goodView").text("夜里好！")} 
	</script>
</body>
</HTML>
