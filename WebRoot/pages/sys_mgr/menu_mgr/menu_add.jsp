<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<script language="javascript" src="${ctx}/plugins/tips_alert.js"></script>
</head>
<body>
<form id="addForm" method="POST" action="${ctx}/menu/save">
	<input type="hidden" name="systemId" value="${param['systemId']}">
	<input type="hidden" name="status" value="1">
	<input type="hidden" name="permissionType" value="1">
	<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0">
		<tr align="left">
			<td width="20%">&nbsp;</td>
			<td align="left">带<font color="red">&nbsp;*&nbsp;</font>为必填项</td>
		</tr>
		<tr>
			<td align="right" valign="middle">菜单名：</td>
			<td><input type="text" name="permissionName" value="" class="required max-length-25"> </td>
		</tr>
		<tr>
			<td align="right">上级菜单：</td>
			<td><select name="parentPermissionId" id="parentPermissionId">
				<option value="0">-无上级菜单-</option>
				<c:forEach var="sysMenu" items="${allMenu}">
					<option value="<c:out value='${sysMenu.permissionId}'/>" <c:if test="${sysMenu.permissionId == resultObject.permissionId}">selected</c:if>><c:out value="${sysMenu.permissionName}"/></option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td align="right" valign="middle">菜单URL：</td>
			<td><input type="text" name="permissionUrl" value="" class="max-length-125" size="70"></td>
		</tr>
		<tr height="12">
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" class="short-button" value="提交">&nbsp;&nbsp; <input type="button"
				class="short-button" value="返回" onclick="back(${menuId})"></td>
		</tr>
	</table>
</form>
</body>
<script type="text/javascript">
  	function back(id)
  	{
  		history.back();
  	}
  </script>
</html>
