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
				<!-- 	<form:input path="loginName" cssClass="form_input" size="20" />  -->
				${resultObject.loginName }
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
				 	<font color="red">*&nbsp;</font>用户姓名  
				</td>
				<td>
				<!-- 	<form:input path="userName" cssClass="form_input" size="20" />  -->
					${resultObject.userName }
				</td>
			</tr>
			<tr>
				<td class="title" width="100">身份证号 </td>
				<td>
				<!-- 	<form:input path="idCard" cssClass="form_input" size="25" maxlength="18" />  -->
				${resultObject.idCard }
				</td>
			</tr>
			<tr>
				<td class="title" width="100">警员编号</td>
				<td>
				<!--  	<form:input path="userCode" cssClass="form_input" size="20" maxlength="6" />  -->
				${resultObject.userCode }
				</td>
			</tr>
			<tr>
				<td class="title" width="100">性别</td>
				<td>
					${resultObject.sex }
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>所属部门
				</td>
				<td>
					<c:if test="${not empty corpList}">
						<c:forEach var="corp" items="${corpList}" varStatus="status">
							<c:if test="${resultObject.sysCorp.corpId eq corp.corpId }">${corp.corpName}</c:if>
						</c:forEach>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>用户角色
				</td>
				<td>	
					<c:if test="${fn:length(resultObject.sysLoginRoles) gt 0 }">
						<c:forEach var="role" items="${resultObject.sysLoginRoles}">
							${role.sysRole.roleName }
					    </c:forEach>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">
					<font color="red">*&nbsp;</font>状态
				</td>
				<td>
					<c:if test="${resultObject.status eq '1' }">有效</c:if>
					<c:if test="${resultObject.status eq '0' }">无效</c:if>
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