<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
</head>
<body>
<form:form id="editForm" name="editForm" method="POST" action="${ctx}/userMgr/update" modelAttribute="resultObject">
		<form:hidden path="permissionId"/>
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
						<font color="red">*&nbsp;</font>权限名称
					</td>
					<td>
						<form:input path="permissionName" id="permissionName" cssClass="form_input required " maxlength="50" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>权限类型 1－菜单 2－操作
					</td>
					<td>
						<form:input path="permissionType" id="permissionType" cssClass="form_input required validate-integer max-value-2147483647" maxlength="2" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						当权限类型为菜单时，该处表示菜单的URL 当权限类型为操作时，该处表示操作对应的按钮名称
					</td>
					<td>
						<form:input path="permissionUrl" id="permissionUrl" cssClass="form_input " maxlength="250" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>树形展现的排列顺序，类似 1    11    12 2    21    22
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
						<form:input path="parentPermissionId" id="parentPermissionId" cssClass="form_input validate-integer " maxlength="10" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>该菜单的所属系统
					</td>
					<td>
						<form:input path="systemId" id="systemId" cssClass="form_input required validate-integer max-value-2147483647" maxlength="2" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						权限图标
					</td>
					<td>
						<form:input path="permissionIco" id="permissionIco" cssClass="form_input " maxlength="100" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>状态 0－无效 1－有效
					</td>
					<td>
						<form:input path="status" id="status" cssClass="form_input required validate-integer max-value-2147483647" maxlength="2" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						创建人 系统自动创建则此处为system
					</td>
					<td>
						<form:input path="createBy" id="createBy" cssClass="form_input " maxlength="50" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						创建时间
					</td>
					<td>
						<form:input path="createTime" id="createTime" cssClass="form_input " maxlength="14" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						最新一次修改者
					</td>
					<td>
						<form:input path="lastModifyBy" id="lastModifyBy" cssClass="form_input " maxlength="50" />
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						最新一次修改时间
					</td>
					<td>
						<form:input path="lastModifyTime" id="lastModifyTime" cssClass="form_input " maxlength="14" />
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
</body>
</html>