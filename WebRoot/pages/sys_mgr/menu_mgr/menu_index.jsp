<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import=" com.njmd.framework.commons.tree.Tree"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>功能菜单管理</title>
<link href="${ctx}/plugins/dhtmlxTree/codebase/dhtmlxtree.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/plugins/dhtmlxTree/codebase/dhtmlxcommon.js"></script>
<script src="${ctx}/plugins/dhtmlxTree/codebase/dhtmlxtree.js"></script>
<%@ include file="/plugins/tablesorter.jsp"%>
<%
	String treeXML = "<?xml version='1.0' encoding='GB18030'?><tree id='0></tree>";
	Tree menuTree = (Tree) request.getAttribute("menuTree");
	treeXML = menuTree.getXml();
%>
</head>
<body onload="loadTree();">
	<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0">
		<tr>
			<td>
				<a href="javascript:window.location.reload();">刷 新</a>
			</td>
			<td align="left">
				<input type="button" class="short-button" value="删除所选" onclick="doRestBatchDelete('${ctx}/restful/sysmenu','ids',this)" /> <input type="button" class="short-button"
					value="添加菜单" onclick="add('${resultObject.permissionId}')" />
			</td>
		</tr>
		<tr>
			<td width="250px" valign="top">
				<div id="menuTree" style="height: 600px; width: 250px; background-color: #f5f5f5; border: 1px solid Silver;"></div>
				<font color="#FF0000">*单击菜单节点可对该菜单进行管理</font>
			</td>
			<td valign="top">
				<fieldset style="width: 95%; padding: 10px">
					<legend> 菜单列表 </legend>
					<table id="tableList" width="100%" cellspacing="1" align="center" class="tablesorter">
						<thead>
							<tr align="center">
								<th class="{sorter: false}" width="4%"><input class="checkbox" type="checkbox" name="operid" onclick="javascript:selectAll('id',this.checked)" /></th>
								<th width="40%">菜单名</th>
								<th width="10%">状态</th>
								<th width="50%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty resultList}">
								<c:forEach var="menu" items="${resultList}" varStatus="status">
									<tr align="center" style="border: 1px;">
										<td>
											<input class="checkbox" type="checkbox" name="id" value="${menu.permissionId}" />
										</td>
										<td>
											<c:if test="${menu.parentPermissionId=='0'}">
												<b>${menu.permissionName}</b>
											</c:if>
											<c:if test="${menu.parentPermissionId!='0'}">
												${menu.permissionName}
											</c:if>
										</td>
										<td>
											<c:if test="${menu.status=='0'}">
												<font color="red">无效</font>
											</c:if>
											<c:if test="${menu.status=='1'}">
							有效
						</c:if>
										</td>
										<td>
											<a href="${ctx}/menu/view?menuId=${menu.permissionId}" class="view">查看</a> <a href="${ctx}/menu/edit?menuId=${menu.permissionId}" class="edit">编辑</a> <a href="#"
												class="delete" onclick="del('${menu.permissionId}')">删除</a> <a href="${ctx}/menu/up?menuId=${menu.permissionId}" class="delete">上移</a> <a
												href="${ctx}/menu/down?menuId=${menu.permissionId}" class="delete">下移</a>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty resultList}">
								<tr align="center">
									<td colspan="5" align="left">暂无符合条件的记录</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</fieldset>
			</td>
		</tr>
	</table>
	<script>
	//加载菜单树
	var tree;
	function loadTree()
	{
		try
		{
			tree = new dhtmlXTreeObject("menuTree", "100%", "90%", 0);
			tree.setImagePath("${ctx}/plugins/dhtmlxTree/codebase/imgs/csh_yellowbooks/");
			tree.enableTreeLines(true);
			tree.setOnClickHandler(treeOnClick);
			tree.loadXMLString('<%=treeXML%>');
			tree.openAllItems("0");
		}
		catch (e)
		{
			alert('菜单列表加载失败！');
		}
	}

	function showTree()
	{
		window.location = "${ctx}/menu/menuMgr";
	}
	//单击改变颜色
	function treeOnClick(id)
	{
		tree.setItemColor(id, '#000000', '#FF0000');
		window.location = "${ctx}/menu/menuMgr?menuId=" + id;
	}
	//菜单增加
	function add(id)
	{
		window.location = "${ctx}/menu/add?menuId=" + id;
	}
	//菜单增加
	function del(id)
	{
		if (confirm('你确定要删除该菜单吗?'))
			window.location = "${ctx}/menu/del?menuId=" + id;
	}
	//批量删除
	function doRestBatchDelete()
	{
		var tag = false;
		if (tag)
		{
			var strCheck = getCheckboxCheckedValue("id");
			var id = strCheck.split(",");
			if (strCheck != "")
			{
				if (confirm("你确定要删除所选菜单吗?\n") == true)
				{
					window.location = "${ctx}/menu/batchDel?id=" + id;
				}
			}
			else
			{
				alert("请选择需要删除的菜单！");
			}
		}
		else
		{
			alert("批量删除功能未开放");
		}
	}
</script>
</body>
</html>
