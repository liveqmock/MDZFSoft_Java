<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
</head>
<body>
<form:form id="addForm" name="addForm" method="POST" action="${ctx}/ftpMgr/save" modelAttribute="resultObject">
	<form:hidden path="ftpId"/>
	<input type="hidden" name="status" value="1"/>
	<div class="gray_bor_bg">
		<div class="table_div">
			<table width="100%" class="table_border">
				<tr>
					<td class="title" width="100"></td>
					<td align="left">
						带<font color="red">&nbsp;*&nbsp;</font>为必填项
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>服务器名称
					</td>
					<td>
						<form:input path="serverName" cssClass="form_input {required:true}" size="20" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>服务器地址
					</td>
					<td>
						<form:input path="ftpIp" cssClass="form_input {required:true,ipv4:true}" size="20" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>服务器端口
					</td>
					<td>
						<form:input path="ftpPort" cssClass="form_input {required:true,maxlength:20}" size="20" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>登陆用户名
					</td>
					<td>
						<form:input path="ftpUser" cssClass="form_input {required:true,maxlength:20}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>登陆密码
					</td>
					<td>
						<form:password path="ftpPwd" cssClass="form_input {required:true,maxlength:20}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>路径url
					</td>
					<td>
						<form:input path="fileRootUrl" cssClass="form_input {required:true,maxlength:100}" size="60" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						描述
					</td>
					<td>
						<form:textarea path="ftpDesc" cols="70" rows="3" cssClass="form_input {maxlength:70}"/>
					</td>
				</tr>
				<tr>
					<td align="center" valign="middle" colspan="2">
						<input type="submit" value="提交" class="blue_mod_btn">
						<input type="button" value="取消" class="blue_mod_btn" onclick="parent.closeModalWindow();">
					</td>
				</tr>
			</table>
		</div>
	</div>
</form:form>
<script>
	$(function(){
		$('#addForm').validate();
		
		$('#addForm').ajaxForm({
			 dataType:  'json',
		     success:   onSuccess
		});
	});
	
	function onSuccess(data) {
	    if(data.messageType=='1')
	    {
	    	alert(data.promptInfo);
	    	parent.closeModalWindow(true);
	    }
	    else
	    {
	    	alert(data.promptInfo);
	    }
	}
</script>
</body>
</html>