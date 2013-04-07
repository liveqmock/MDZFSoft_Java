<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/plugins/validation.jsp"%>
		<script language="javascript" src="${ctx }/js/tips_alert.js"></script>
		<%@ include file="/plugins/calendar.jsp"%>
	</head>
	<body>
		<table width="98%" border="0" align="center" cellpadding="3" cellspacing="0">
			<tr>
				<td class="page-title">
					<img src="${ctx}/images/page_title.gif" alt="title" width="17" height="12" align="absmiddle">
					&nbsp;&nbsp; 权限管理 » 用户管理 » 用户查看
				</td>
			</tr>
		</table>
		<br>
		<form:form name="form" method="PUT" action="${ctx}/restful/sysuser/${resultObject.loginId}"  modelAttribute="resultObject" onsubmit="return check()">
			<table width="50%" border="0" cellpadding="5" cellspacing="0">
				<tr>
					<td></td>
					<td align="left">带<font color="red">&nbsp;*&nbsp;</font>为必填项</td>
				</tr>

				<tr>
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>用户帐号
					</td>
					<td>
						<form:input path="loginName" cssClass="required max-length-20 validate-il-character" size="20" maxlength="20"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="用户帐号，最长20个字符" width="14" height="15" border="0" align="absmiddle"> </a>
						<input type="button" value="检查唯一性" onclick="checkLoginName();" class="short-button">&nbsp;&nbsp;&nbsp;<label id="message"></label>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>用户密码
					</td>
					<td>
						<input type="password" name="loginPwd" class="min-length-6 max-length-15" size="21" maxlength="15"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="密码，6至15位英文、数字或符号" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>确认密码
					</td>
					<td>
						<input type="password" name="reLoginPwd" cssClass="equals-loginPwd" size="21" maxlength="15"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="请再次输入上面的密码" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>用户姓名
					</td>
					<td>
						<form:input path="userName" cssClass="required max-length-10" size="20" maxlength="10"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="用户姓名，10字以内" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						手机号码
					</td>
					<td>
						<form:input path="terminalId" cssClass="validate-mobile-phone" size="20" maxlength="11"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="输入11位手机号码" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						联系地址
					</td>
					<td>
						<form:input path="address" cssClass="max-length-100" size="50" maxlength="21"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="联系地址，100字符以内" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						Email
					</td>
					<td>
						<form:input path="email" cssClass="validate-email" size="20" maxlength="100"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="Email，100字以内"
								width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>用户角色
					</td>
					<td id="roleTd">
						<c:forEach items="${roleList}" var="role" varStatus="status">
							<form:checkbox path="roleIds" value="${role.roleId}" label="${role.roleName}"/>
							<c:if test="${(status.index + 1)%3 == 0}">
									<br>
								</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						用户描述
					</td>
					<td>
						<form:textarea path="userRemark" rows="4" cols="20" id="userRemark" cssClass="max-length-100"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="用户描述，100字以内" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>是否永久生效
					</td>
					<td>
						<form:select path="validTag" cssClass="validate-one-checked" onchange="changeValidTag(this.value);">
							<option value="1">是</option>
							<option value="0">否</option>
						</form:select>
						<a href="#"><img src="${ctx}/images/help.gif" tips="是否永久生效" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr id="validDateTr" style="display: none">
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>有效日期
					</td>
					<td>
						<input type="text" name="_validDate" id="_validDate" class="required max-length-10" size="20" readonly>
							&nbsp;
						<a href="#"><img src="${ctx}/images/help.gif" tips="截止时间，10字以内" width="14" height="15" border="0" align="absmiddle"> </a>
						<div style="display: none">
							<textarea rows="1" cols="12" name="validDate" id="validDate" readonly="readonly"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" valign="middle" colspan="2">
						<input type="button" class="short-button" value="返  回" onclick="window.location='${ctx}/restful/sysuser'" />
					</td>					
				</tr>
			</table>	
		</form:form>
	</body>
</html>