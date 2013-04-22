<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<%@ include file="/common/header.jsp"%>
	<script language="javascript" src="${ctx}/js/cookie.js"></script>
</head>
<body class="login_box">
	<!--header=============================================begin-->
	<div id="header">
		<div class="af_hd">
			<a href="" class="logo"> <img src="${ctx }/images/logo.png" alt="" />
			</a>
		</div>
	</div>
	<!--content============================================begin-->
	<div class="login_main">
		<div class="error msg" id="loginMsg" style="display: none" onclick="hideObj($(this).attr('id'))"></div>
		<form id="loginForm" action="${ctx}/login" method="post">
			<ul class="form_list">
				<li class="form_item">
					<label class="label">用户名</label> <input type="text" class="input_186x25" name="loginName"  id="loginName" tabindex="1" maxlength="20"/>
				</li>
				<li class="form_item">
					<label class="label">密&nbsp;&nbsp;码</label> <input type="password" class="input_186x25" id="loginPwd" name="loginPwd" tabindex="2" maxlength="20"/>
				</li>
				<li class="form_item">
					<label class="label">验证码</label> <input type="text" class="input_67x25" id="imgCheckCode" name="imgCheckCode" tabindex="3" maxlength="5"/> <img id="checkImg" src="${ctx}/plugins/checkCode.jsp" class="validate_img" /><a
						href="javascript:changeCheckImg()">重获验证码</a>
					<p class="pl_50 mt_10">
						<input id="remeberLoginName" name="remeberLoginName" type="checkbox" value="" tabindex="4"/>记住账号
					</p>
					<p class="pl_50 mt_10">
						<input type="submit" class="login_btn" value="" tabindex="5" />
					</p>
				</li>
			</ul>
		</form>
		<div class="login_opt white">
			<span class="h_l"></span> <span class="h_r"></span> <a href="#" onclick="javascript:downloadFile('mdDrivers.zip')" class="white" target="_blank">执法记录仪驱动下载</a>&nbsp;&nbsp;|&nbsp; <a
				href="#" onclick="javascript:downloadFile('md_1.0.0.5.CAB')" class="white">上传插件下载</a>
		</div>
	</div>
	<div class="footer_out">
		<jsp:include page="footer.jsp" />
	</div>
	
	<script language="JavaScript" type="text/JavaScript">
	if (top.location !== self.location)
	{
		top.location = self.location;
	}	
	
	$(function(){
		//分析cookie值，显示上次的登陆信息   
		var userNameValue = getCookieValue("loginName");   
		document.getElementById("loginName").value = userNameValue; 
		
		$('#loginForm').ajaxForm({
			 dataType:  'json',
			 beforeSubmit: validate,
		     success:   onSuccess,
		});
		$('#loginName').focus();
	});
	
	function onSuccess(data) {
	    if(data.messageType=='1')
	    {
	    	window.location='${ctx}'+data.gotoUrlForward;
	    }
	    else
	    {
	    	alert(data.promptInfo);
	    }
	}
	
	//登录验证
	function validate(formData, jqForm, options) 
	{
		var theForm = jqForm[0]; 
		if (theForm.loginName.value == "")
		{
			alert('登陆用户名不能为空');
			$('#loginName').focus();
			return (false);
		}
		else if(!/^[^"'\\<>\*&]+$/.test(theForm.loginName.value))
		{
			alert("登录用户名不能包含非法字符 \"'\\<>\*\&");
			$('#loginName').focus();
			return (false);
		}
		else if (theForm.loginPwd.value == "")
		{
			alert("登录密码不能为空");
			$('#loginPwd').focus();
			return (false);
		}
		else if (theForm.imgCheckCode.value == "")
		{
			alert("验证码不能为空");
			$('#imgCheckCode').focus();
			return (false);
		}
		if(document.getElementById("remeberLoginName").checked){     
            setCookie("loginName",document.getElementById("loginName").value,24,"/");   
        }  
		return (true);
	}
	/**
	 * 验证码 换一张
	 */
	function changeCheckImg()
	{
		$("#checkImg").attr("src", "${ctx}/plugins/checkCode.jsp?"+Math.random());
	}
	
	
</script>
</body>
</html>