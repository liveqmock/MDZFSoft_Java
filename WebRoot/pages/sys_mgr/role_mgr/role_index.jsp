<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/plugins/tablesorter.jsp"%>
	</head>
	<body>
		<div class="mianbaoxie">
			当前位置：<a href="#1">系统管理</a> > 角色管理
		</div>
		<form id="mainForm" action="${ctx}/roleMgr" method="post">
			<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
			<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
			<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
			<input type="hidden" name="order" id="order" value="${page.order}" />
			<input type="hidden" name="systemType" id="systemType" value="${systemType}"/>
			<div class="tab_search" style="margin-top: 20px;">
			<div class="tab_top">
				<div class="tab_text">
					<div class="tab_text_left"></div>
					<div class="tab_text_con">按条件查询</div>
					<div class="tab_text_right" id="dateBtn"></div>
				</div>
			</div>
			<div class="table_div">
				<table width="100%" class="table_border">
					<tr>
						<td class="title" width="100">角色名称：</td>
						<td><input type="text" name="filter_LIKE_roleName" id="roleName" value="${param['filter_LIKE_roleName']}" size="20" class="form_input"/>
						</td>
						<td class="title">状态：</td>
						<td>
							<select name="filter_EQ_status" id="status" class="form_input" style="width: 200px;">
								<option value="">--全部--</option>
								<option value="1">有效</option>
								<option value="0">无效</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center"><input name="" type="button" value="查询" class="form_botton" onclick="query()"/>&nbsp;&nbsp;&nbsp;&nbsp;<input
							name="" type="button" value="重置" class="form_botton" onclick="clearForm()"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="table_list " style="margin-top: 20px;">
			<input type="button" class="form_botton" value="新  增" onclick="javascript:window.location='${ctx}/roleMgr/add'"/>
			&nbsp;&nbsp;&nbsp;
			<input type="button" class="form_botton" value="删除所选" onclick="delChecked()" />
		</div>
		<div class="table_list table_div" >
			<table id="tableList" cellspacing="1" class="table_border" width="100%">
					<tr align="center">
						<td class="table_list_title title_border_a" width="4%"><input class="checkbox" type="checkbox" name="operid"
								onclick="javascript:selectAll('id',this.checked)"></td>
						<td class="table_list_title title_border_a" onclick="javascript:sort('roleName','asc')" width="15%"><div class="paixu_3">角色名称</div></td>
						<td class="table_list_title title_border_a" onclick="javascript:sort('status','asc')" width="10%"><div class="paixu_3">状态</div></td>
						<td class="table_list_title" width="25%">角色描述</td>
						<td class="table_list_title " width="10%">操作</td>
					</tr>
					<c:if test="${page.totalCount != '0'}">
						<c:forEach var="role" items="${page.result}" varStatus="status">
							<tr align="center">
								<td>
									<input class="checkbox" type="checkbox" name="id" value="<c:out value='${role.roleId}' />" />
								</td>
								<td>
									${role.roleName}
								</td>
								<td>
									<c:choose>
										<c:when test="${role.status=='1'}">有效
									</c:when>
										<c:when test="${role.status=='0'}">
											<font color="red">无效</font>
										</c:when>
									</c:choose>
								</td>
								<td>
									<c:out value="${role.roleDesc}" />
								</td>
								<td>
									<a href="${ctx}/roleMgr/edit/${role.roleId}" class="edit">编辑</a>
									<a href="${ctx}/roleMgr/delete/${role.roleId}" class="delete" onclick="if(!confirm('你确认要删除该角色？'))return false;">删除</a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${page.totalCount == '0'}">						
						<tr align="center">
							<td colspan="5" align="left">
								暂无符合条件的记录
							</td>
						</tr>
					</c:if>
			</table>
		</div>
		</form>
		<div>
			<jsp:include page="/plugins/pager.jsp" flush="true" />
		</div>
	</body>
	<script type="text/javascript">
	//页面加载初始化方法，实现查询条件下拉列表的回显功能
	function init()
	{
		var status = "${param['filter_EQ_status']}";
		$("#status").val(status);
	}
	init();

	function query(){
		$("#mainForm").submit();
	}

	function clearForm(){
		$("#roleName").val("");
		$("#status").val("");
	}

	//批量删除
	function delChecked()
	{
		if(getCheckboxCheckedValue("id") != "")
		{
			if(confirm('确定删除所选的角色？'))
			{
				$("#mainForm").attr("action","${ctx}/roleMgr/batchDelete");
				$("#mainForm").submit();
			}
		}
		else
		{
			alert("请选择需要删除的角色!");
		}
	}
	</script>
</html>