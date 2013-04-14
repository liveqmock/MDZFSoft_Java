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
<div class="gray_bor_bg">
	<div class="table_div">
		<table width="100%" class="table_border">
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>用户帐号
				</td>
				<td>
					<form:input path="loginName" cssClass="form_input" size="20" />
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>用户姓名
				</td>
				<td>
					<form:input path="userName" cssClass="form_input" size="20" />
				</td>
			</tr>
			<tr>
				<td class="title" width="100">警员编号</td>
				<td>
					<form:input path="userCode" cssClass="form_input" size="20" maxlength="11" />
				</td>
			</tr>
			<tr>
				<td class="title" width="100">性别</td>
				<td>
					<form:select path="sex" class="form_input">
						<form:option value="男">男</form:option>
						<form:option value="女">女</form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>所属部门
				</td>
				<td>
					<form:select path="sysCorp.corpId" cssClass="form_input {required:true}" cssStyle="width: 150px;">
						<form:option  value="">--请选择--</form:option>
						<c:if test="${not empty corpList}">
							<c:forEach var="corp" items="${corpList}" varStatus="status">
								<form:option value="${corp.corpId}">${corp.corpName}</form:option>
							</c:forEach>
						</c:if>
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>用户角色
				</td>
				<td>
					<select name="roleIds" id="roleIds" class="form_input {required:true}" style="width: 150px;">
						<option value="">--请选择--</option>
						<c:if test="${not empty roleList}">
							<c:forEach var="role" items="${roleList}" varStatus="status">
								<option value="${role.roleId}">${role.roleName}</option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>状态
				</td>
				<td>
					<form:select path="status" cssClass="form_input">
						<form:option value="1">有效</form:option>
						<form:option  value="0">无效</form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td align="center" valign="middle" colspan="2">
					<input type="button" value="关闭" class="blue_mod_btn" onclick="parent.closeModalWindow();">
				</td>
			</tr>
		</table>
	</div>
</div>
</form:form>
<script>
	
	$(function(){
		$("#roleIds").val("${resultObject.sysLoginRoles[0].sysRole.roleId}");
	});

</script>
</body>
</html>