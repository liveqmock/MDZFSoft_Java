<%@page import="com.njmd.zfms.web.constants.RequestNameConstants"%>
<%@page import="com.njmd.framework.commons.ResultInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.HashMap"%>
<%
ResultInfo resultInfo = null;
resultInfo = (ResultInfo)request.getAttribute(RequestNameConstants.RESULT_INFO);
%>
<html>
<head>
<title>系统提示信息</title>
<%@ include file="/common/header.jsp"%>
<base target="_self">
</head>
<body>
	<form id="infoForm" action="" method="post">
		<%
			if(resultInfo.getIsAlert()==false)
			{
				if(resultInfo.getMessageType()==ResultInfo.MESSAGE_TYPE_SUCCESS)
				{
		%>
				<div class="tips_a">
		<%
				}
				if(resultInfo.getMessageType()==ResultInfo.MESSAGE_TYPE_ERROR)
				{
		%>
				<div class="tips_b">
		<%
				}
		%>	
			<div class="tips_con">
				<p class="a">${resultInfo.promptInfo}</p> 
				<p class="b">页面正在跳转中，请等待...... </p> 
				<p class="c"><a href="#" onclick="pageHandle();">点击这里直接进行跳转</a></p>
			</div>
		</div>
		<%
			}
		%>
	</form>
</body>
</html>
<script>
	var ctx = '${ctx}';
</script>
<%
	//超时提示信息
	if (resultInfo.getIsOverTime()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"
					+ resultInfo.getAlertInfo() + "');");
			out.println("	top.location=ctx+'/pages/login.jsp';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("function pageHandle(){");
			out.println("	top.location=ctx+'/pages/login.jsp';");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}

	//没有权限提示信息
	if (resultInfo.getIsPopedom()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"+ resultInfo.getAlertInfo() + "');");
			out.println("	history.back();");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("function pageHandle(){");
			out.println("	history.back();");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}

	// 返回提示信息
	if (resultInfo.getIsBack()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"
					+ resultInfo.getAlertInfo() + "');");
			out.println("	history.back();");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("function pageHandle(){");
			out.println("	history.back();");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}

	//关闭当前窗口
	if (resultInfo.getIsCloseWindow()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"
					+ resultInfo.getAlertInfo() + "');");
			out.println("	window.close();");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("function pageHandle(){");
			out.println("	window.close();");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}

	//关闭父窗口
	if (resultInfo.getIsOpenTopWindow()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"
					+ resultInfo.getAlertInfo() + "');");
			out.println("	window.top.location.href = ctx+'"
					+ resultInfo.getGotoUrlForward() + "';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("function pageHandle(){");
			out.println("	window.top.location.href = ctx+'"
					+ resultInfo.getGotoUrlForward() + "';");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}

	//前进跳转提示信息
	if (resultInfo.getIsRedirect()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"
					+ resultInfo.getAlertInfo() + "');");
			out.println("	window.location = ctx+'"
					+ resultInfo.getGotoUrlForward() + "';");
			out.println("</script>");
		} else {

			out.println("<script>");
			out.println("function pageHandle(){");
			out.println("	window.infoForm.action = ctx+'"
					+ resultInfo.getGotoUrlForward() + "';");
			out.println("	window.infoForm.submit(); ");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}

	//是否刷新父窗口	if (resultInfo.getIsRefreshParentWindow()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"
					+ resultInfo.getAlertInfo() + "');");
			out
					.println("	parent.window.location.href = parent.window.location.href;");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("function pageHandle(){");
			out
					.println("	parent.window.location.href = parent.window.location.href;");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}

	//是否刷新创始者窗口	if (resultInfo.getIsRefreshOpenerWindow()) {
		if (resultInfo.getIsAlert()) {
			out.println("<script>alert('【系统提示】\\n\\n"
					+ resultInfo.getAlertInfo() + "');");
			out
					.println("opener.window.location.href = opener.window.location.href;");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("function pageHandle(){");
			out
					.println("	opener.window.location.href = opener.window.location.href;");
			out.println("}");
			out.println("setTimeout(\"pageHandle()\",3000);");
			out.println("</script>");
		}
		return;
	}
%>
