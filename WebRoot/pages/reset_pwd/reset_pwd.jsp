<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
</head>
<body>
	<form:form id="mainForm" name="mainForm" method="POST" action="${ctx}/userMgr/updatePwd"  modelAttribute="resultObject">
		<div class="gray_bor_bg">
		<div class="table_div">
			<table width="100%" class="table_border">
				<tr>
					<td class="title" width="100"></td>
					<td align="left">带<font color="red">&nbsp;*&nbsp;</font>为必填项</td>
				</tr>
				<tr>
					<td class="title" width="100">登录账号：</td>
					<td><input name="loginName" class="form_input" size="20" type="text" disabled="true" value="${sessionScope.loginToken.sysLogin.loginName}"/></td>
				</tr>
				<tr>
					<td class="title"><font color="red">*&nbsp;</font>原密码：</td>
					<td><input name="loginPwd" id="loginPwd" type="password" class="form_input {required:true,maxlength:16,minlength:6}"/></td>
				</tr>
					<tr>
					<td class="title"><font color="red">*&nbsp;</font>新密码：</td>
					<td><input id="newLoginPwd" name="newLoginPwd" type="password" class="form_input {required:true,maxlength:16,minlength:6}"/></td>
				</tr>
					<tr>
					<td class="title"><font color="red">*&nbsp;</font>密码确认：</td>
					<td><input type="password" name="reLoginPwd" id="reLoginPwd" class="form_input {required:true,equalTo:'#newLoginPwd'}" size="20"/></td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input name="确定" type="submit" value="确定" class="blue_mod_btn"/>
						<input type="button" value="取消" class="blue_mod_btn" onclick="parent.closeModalWindow();">
					</td>
				</tr>
			</table>
		</div>
		</div>
	</form:form>
	<script>
	$(function(){
		
		$('#mainForm').validate();
		
		$('#mainForm').ajaxForm({
			 dataType:  'json',
		     success:   onSuccess
		});
	});
	
	function onSuccess(data) {
	    if(data.messageType=='1')
	    {
	    	alert(data.promptInfo);
	    	parent.closeModalWindow();
	    }
	    else
	    {
	    	alert(data.promptInfo);
	    }
	}
</script>
</body>
</html>
