<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
<%@ include file="/plugins/jquery-powerFloat.jsp" %>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body>
	<form:form id="editForm" name="editForm" method="POST" action="${ctx}/userMgr/update" modelAttribute="resultObject">
	<input type="hidden" name="loginId" id="loginId" value="${resultObject.loginId}"/>
		<form:hidden path="recordCorpId"/>
		<form:hidden path="systemId"/>
		<form:hidden path="userType"/>
		<form:hidden path="loginId"/>
		<form:hidden path="loginPwd"/>
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
								<font color="red">*&nbsp;</font>用户帐号
							</td>
							<td>
								<form:input path="loginName" cssClass="form_input {required:true,maxlength:20}" size="20" />
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								<font color="red">*&nbsp;</font>用户姓名
							</td>
							<td>
								<form:input path="userName" cssClass="form_input {required:true,maxlength:10}" size="20" />
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
								<input id="corpId" name="sysCorp.corpId" type="hidden" value="${resultObject.sysCorp.corpId}"/>
								<input type="text" name="corpName" id="corpName" value="${resultObject.sysCorp.corpName}" size="20" class="form_input {required:true}" readonly="readonly" style="cursor: pointer;"/>
								<div id="corpChooseDiv" style="position:absolute; border:solid 1px #CCCCCC; width:250px; height:200px; top:23px; left:0px; background:#FFFFFF;display: none">
							         <ul id="treeDemo" class="ztree" style="width: 180px;">
							         </ul>
						        </div>
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
		//树形菜单
		var setting = {
			data: {
				simpleData: {enable: true}
			},
			callback: {
				onClick: onClick
			}
		};
		var zNodes = ${tree.json};  
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		
		$("#roleIds").val("${resultObject.sysLoginRoles[0].sysRole.roleId}");
		
		$('#editForm').validate();
		
		$('#editForm').ajaxForm({
			 dataType:  'json',
		     success:   onSuccess
		});
	});
	$("#corpName").powerFloat({
		eventType: "click",
		target: $("#corpChooseDiv")	
	});
	
	function onClick(e, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		var corpId = nodes[0].id;
		var corpName = nodes[0].name;
		$("#corpName").val(corpName);
		$("#corpId").val(corpId);
		$.powerFloat.hide();
		$("#corpChooseDiv").css("display","none");
	}
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