<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.turbo.msgp.pub.commons.Tree"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/plugins/validation.jsp"%>
		<script language="javascript" src="${ctx }/js/tips_alert.js"></script>
		<%@ include file="/plugins/tree.jsp"%>
		<%@ include file="/plugins/tablesorter.jsp"%>
	</head>
	<body onload="loadTree();">
		<%
			String treeXML = "<?xml version='1.0' encoding='GB18030'?><tree id='0></tree>";
			Tree permissionTree = (Tree)request.getAttribute("menuTree");
			treeXML = permissionTree.getXml();
		%>
		<table width="98%" border="0" align="center" cellpadding="3" cellspacing="0">
			<tr>
				<td class="page-title">
					<img src="${ctx }/images/page_title.gif" alt="title" width="17" height="12" align="absmiddle">
					&nbsp;&nbsp; 权限管理 » 角色管理 » 角色查看
				</td>
			</tr>
		</table>
		<br>
		<form:form name="form" method="PUT" action="${ctx}/restful/sysrole/${resultObject.roleId}"  modelAttribute="resultObject">
			<table width="50%" border="0" align="center" cellpadding="5" cellspacing="0">
				<tr>
					<td align="right" valign="middle">
						<font color="red">*&nbsp;</font>角色名称
					</td>
					<td>
						<form:input path="roleName" cssClass="required max-length-25" id="roleName" size="30"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="角色名称，25字以内" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						角色描述
					</td>
					<td>
						<form:textarea path="roleDesc" rows="4" cols="30" id="roleDesc" cssClass="max-length-100"/>
						<a href="#"><img src="${ctx}/images/help.gif" tips="角色的描述信息，100字以内" width="14" height="15" border="0" align="absmiddle"> </a>
					</td>
				</tr>
				<tr>
					<td align="right" valign="top">
						<font color="red">*</font>角色权限
					</td>
					<td>
						<input type="hidden" id="menuTreeXml" name="menuTreeXml" value='<%=treeXML%>' />
						<div id="menuTree" style="height: 300px; width: 250px; background-color: #f5f5f5; border: 1px solid Silver;"></div>
					</td>
				</tr>
				<tr>
					<td align="right" valign="middle">
						角色状态
					</td>
					<td>
						<form:radiobutton path="status" value="1" label="有效" /> <form:radiobutton path="status" value="0" label="无效" />
					</td>
				</tr>

				<tr>
					<td align="center" valign="middle" colspan="2">
						<input type="button" class="short-button" value="返  回" onclick="window.location='${ctx}/restful/sysrole'" />
					</td>
				</tr>
			</table>
		</form:form>
	</body>
	<script>	
		//加载权限树

		var tree;
		function loadTree(){
			try
			{
				tree = new dhtmlXTreeObject("menuTree","100%","90%",0);
				var treeXml = document.getElementById("menuTreeXml").value;
				tree.setImagePath("${ctx}/plugins/dhtmlxTree/codebase/imgs/csh_yellowbooks/");
				tree.enableTreeLines(true);
				tree.enableCheckBoxes(true);
				tree.enableThreeStateCheckboxes(true);
				tree.loadXMLString(treeXml);
			}
			catch(e)
			{
				alert('菜单加载失败！');
			}
		}
	</script>
</html>