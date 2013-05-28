<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
</head>
<body>
<form:form id="addForm" name="addForm" method="POST" action="${ctx}/sysPermissionMgr/save" modelAttribute="resultObject">
	<form:hidden path="permissionId"/>
	<input type="hidden" name="permissionType" value="1"/>
	<input type="hidden" name="systemId" value="1"/>
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
						<font color="red">*&nbsp;</font>菜单名称
					</td>
					<td>
						<form:input path="permissionName" id="permissionName" cssClass="form_input required " maxlength="50" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						URL
					</td>
					<td>
						<form:input path="permissionUrl" id="permissionUrl" cssClass="form_input " maxlength="250" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>顺序
					</td>
					<td>
						<form:input path="permissionSort" id="permissionSort" cssClass="form_input required validate-integer " maxlength="10" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						上级权限ID
					</td>
					<td>
						<form:select path="parentPermissionId" cssClass="form_input" cssStyle="width:140px">
							<form:option value="0" label=""></form:option>
							<form:options items="${menuList}" itemLabel="permissionName" itemValue="permissionId"/>
						</form:select>
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