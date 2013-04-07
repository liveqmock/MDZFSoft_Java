<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<script language="javascript" src="${ctx}/plugins/tips_alert.js"></script>
</head>

<body>
<form:form method="POST" action="${ctx}/menu/update?menuId=${resultObject.permissionId}"  modelAttribute="resultObject">
	<fieldset style="width: 95%; padding: 10px"><legend> 菜单详情 </legend>
	<table width="90%" border="0" align="center" cellpadding="4" cellspacing="0">
	<form:hidden path="permissionId"/>
	<form:hidden path="permissionSort"/>
	<form:hidden path="permissionType"/>
	<form:hidden path="systemId"/>
		<tr>
			<td>菜单名：</td>
			<td><form:input path="permissionName" cssClass="required max-length-25" /></td>
		</tr>
		<tr>
			<td>上级菜单：</td>
			<td><form:select path="parentPermissionId">
				<option value="0">-无上级菜单-</option>
				<form:options items="${allMenu}" itemLabel="permissionName" itemValue="permissionId" />
			</form:select></td>
		</tr>
		<tr>
			<td>菜单URL：</td>
			<td><form:input path="permissionUrl" cssClass="max-length-125" size="70" /></td>
		</tr>
		<tr>
			<td>菜单状态：</td>
			<td><form:select path="status">
				<option value="1">有效</option>
				<option value="0">无效</option>
			</form:select></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" class="short-button" value=" 修  改 ">&nbsp;&nbsp;<input type="button"
				class="short-button" value=" 返 回  " onclick="back();">&nbsp;&nbsp;</td>
		</tr>
	</table>
	</fieldset>
	<br>
</form:form>
</body>
<script type="text/javascript">
	function back()
	{
		history.back();
	}
</script>
</html>
