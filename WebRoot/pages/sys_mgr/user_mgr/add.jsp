<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/plugins/jquery-validation.jsp"%>
		<%@ include file="/plugins/calendar.jsp"%>
	</head>
	<body>
		<div class="mianbaoxie">
			当前位置：<a href="#1">系统管理</a> > <a href="${ctx}/deptMgr">部门管理</a>> <a href="${ctx}/userMgr">用户列表</a>  > 新增
		</div>
		<form:form id="mainForm" name="mainForm" method="POST" action="${ctx}/userMgr/save"  modelAttribute="resultObject">
			<input type="hidden" name="status" id="status" value="1">
			<input type="hidden" name="userType" id="userType" value="1"/>
		<div class="tab_search" style="margin-top: 20px;">
		<div class="table_div">
			<table width="100%" class="table_border">
				<tr>
					<td class="title" width="100"></td>
					<td align="left">带<font color="red">&nbsp;*&nbsp;</font>为必填项</td>
				</tr>
				<tr>
						<td class="title" width="100"><font color="red">*&nbsp;</font>部门：</td>
						<td colspan="3">
							<select name="deptId" id="deptId" class="form_input" style="width: 200px;">
								<c:if test="${deptListSize != '0'}">
									<c:forEach var="dept" items="${deptList}" varStatus="status">
										<option value="${dept.deptId}">${dept.deptName}</option>
									</c:forEach>
								</c:if>
							</select>
						</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>用户帐号
					</td>
					<td>
						<form:input path="loginName" cssClass="form_input {required:true,maxlength:20}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>用户密码
					</td>
					<td>
						<form:password path="loginPwd" id="loginPwd" cssClass="form_input {required:true,maxlength:16,minlength:6}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>确认密码
					</td>
					<td>
						<input type="password" name="reLoginPwd" class="form_input {required:true,equalTo:'#loginPwd'}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>用户姓名
					</td>
					<td>
						<form:input path="userName" cssClass="form_input {required:true,maxlength:10}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						手机号码
					</td>
					<td>
						<form:input path="tel" cssClass="form_input {mobilePhone:true,maxlength:11}" size="20" maxlength="11"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						车牌号
					</td>
					<td>
						<form:input path="carNo" cssClass="form_input {maxlength:11}" size="20" maxlength="11"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						Email
					</td>
					<td>
						<form:input path="email" cssClass="form_input {email:true,maxlength:100}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>用户角色
					</td>
					<td id="roleTd">
							<table style="border-collapse:collapse; background:white;">
								<tr>
								<c:forEach var="role" items="${roleList}" varStatus="status">
									<td width="200px" style="border:solid 0px #cee0f4; padding:3px;">
										<form:checkbox cssClass="required"  path="roleIds" value="${role.roleId}" />${role.roleName}
									</td>
									<c:if test="${(status.index + 1)%2 == 0}"></tr><tr></c:if>
								</c:forEach>
								</tr>
							</table>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						用户描述
					</td>
					<td>
						<form:textarea path="userRemark" cols="70" rows="4" id="userRemark" cssClass="form_input {maxlength:70}"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>是否永久生效
					</td>
					<td>
						<form:radiobutton path="validTag" onclick="$('#validDateTr').hide();$('#validDate').val('')" value="1" cssClass="{required:true}"/>是
						<form:radiobutton path="validTag"  onclick="$('#validDateTr').show()" value="0" cssClass="{required:true}"/>否
					</td>
				</tr>
				<tr id="validDateTr" style="display: none">
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>有效日期
					</td>
					<td>
						<input type="text" name="_validDate" id="_validDate" class="form_input {required:true,greatThanEqualToday:true}" size="20" readonly style="width:130px;cursor: pointer;">
							&nbsp;
						<div style="display: none">
							<form:textarea rows="1" cols="12" path="validDate" id="validDate" readonly="readonly"></form:textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" valign="middle" colspan="2">
						<input type="submit" class="form_botton" value="提  交"/>&nbsp;&nbsp;
						<input type="button" class="form_botton" value="返  回" onclick="window.location='${ctx}/userMgr'" />
					</td>					
				</tr>
			</table>	
			</div>
			</div>
		</form:form>
	</body>
	<script>
	$(function(){
		$('#mainForm').validate({
			submitHandler: function(form) {
				var roles = document.getElementsByName("roleIds");
				if(roles.length == 0)
				{
					alert("没有可选择的角色，不能创建用户，请先创建角色");
					return false;
				}
			 	form.submit();
	 		}
		});
	});
		
		Calendar.setup(
		{ 
	      	inputField  : "_validDate",      // ID of the input field
	      	displayArea : "validDate",
	      	ifFormat    : "%Y-%m-%d",    		// the date format
	      	daFormat    : "%Y%m%d",
		    button      : "_validDate"        // ID of the button
		});
	</script>
</html>