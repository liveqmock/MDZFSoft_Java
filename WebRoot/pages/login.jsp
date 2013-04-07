<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<LINK href="${ctx}/css/login.css" rel="stylesheet" type="text/css">
<script language="javascript" src="${ctx}/js/cookie.js"></script>
<%@ include file="/plugins/ymPrompt.jsp"%>
</head>
<body>
<form name="loginForm" id="loginForm" action="${ctx}/login" method="post" onsubmit="javascript:return validate(this);">
   <div class="login_div">
      <input name="loginName"  id="loginName" tabindex="1" maxlength="15" type="text" class="input_a"/>
      <input name="loginPwd" type="password" tabindex="2"  class="input_b"  />
      <input name="imgCheckCode" maxlength="5" type="text" class="input_c"/>
      <img src="${ctx}/plugins/checkCode.jsp" width="78px" height="28" class="yzm"  />
      <div class="ps"><input id="remeberLoginName" name="remeberLoginName" type="checkbox" value="" />记住账号</div>
      <input name="Submit" type="submit" value="登录" class="anniu" />
   </div>
	</form>
	<script language="JavaScript" type="text/JavaScript">
	if (top.location !== self.location)
	{
		top.location = self.location;
	}	
	//登录验证
	function validate(theForm)
	{
		if (theForm.loginName.value == "")
		{
			ymPrompt.errorInfo({message:'帐号不能为空',title:'提示',useSlide:true,slideCfg:{increment:0.2,interval:100}});
			setTimeout(function(){ymPrompt.doHandler('ok');},3000);
			return (false);
		}
		else if(!/^[^"'\\<>\*&]+$/.test(theForm.loginName.value))
		{
			//ymPrompt.errorInfo({message:"登录帐号不能包含非法字符 \"'\\<>\*\&amp;",title:'提示',useSlide:true,slideCfg:{increment:0.2,interval:100}});
			//setTimeout(function(){ymPrompt.doHandler('ok');},2000);
			return (false);
		}
		else if (theForm.loginPwd.value == "")
		{
			ymPrompt.errorInfo({message:"密码不能为空",title:'提示',useSlide:true,slideCfg:{increment:0.2,interval:100}});
			setTimeout(function(){ymPrompt.doHandler('ok');},3000);
			return (false);
		}
		else if (theForm.imgCheckCode.value == "")
		{
			ymPrompt.errorInfo({message:"验证码不能为空",title:'提示',useSlide:true,slideCfg:{increment:0.2,interval:100}});
			setTimeout(function(){ymPrompt.doHandler('ok');},3000);
			return (false);
		}
		if(document.getElementById("remeberLoginName").checked){     
            setCookie("loginName",document.getElementById("loginName").value,24,"/");   
        }  
		return (true);
	}
	
	//分析cookie值，显示上次的登陆信息   
	var userNameValue = getCookieValue("loginName");   
	document.getElementById("loginName").value = userNameValue; 
</script>
</body>
</html>