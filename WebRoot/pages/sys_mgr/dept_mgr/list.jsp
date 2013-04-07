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
		当前位置：<a href="#1">系统管理</a> > 部门管理
	</div>
	<form id="mainForm" action="${ctx}/deptMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
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
						<td class="title" width="100">部门名称：</td>
						<td><input type="text" name="filter_LIKE_deptName" id="deptName" value="${param['filter_LIKE_deptName']}" size="20" class="form_input" />
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
		<input type="button" class="form_botton" value="新  增" onclick="javascript:window.location='${ctx}/deptMgr/add'"/>
		&nbsp;&nbsp;&nbsp;
		<input type="button" class="form_botton" value="删除所选" onclick="delChecked()" />
		</div>
		<div class="table_list table_div" >
			<table id="tableList" class="table_border" width="100%">
					<tr align="center">
						<td class="table_list_title title_border_a" width="4%"><input class="checkbox" type="checkbox" name="operid"
								onclick="javascript:selectAll('id',this.checked)"></td>
						<td class="table_list_title title_border_a" onclick="javascript:sort('deptName','asc')" width="40%"><div class="paixu_3">部门名称</div></td>
						<td class="table_list_title title_border_a"  width="20%">操作</td>
					</tr>
					<c:if test="${page.totalCount != '0'}">
						<c:forEach var="dept" items="${page.result}" varStatus="status">
							<tr align="center">		
								<td><input class="checkbox" type="checkbox" name="id" value="${dept.deptId}" /></td>
								<td>${dept.deptName}</td>
								<td>
									<a href="${ctx}/userMgr/list/${dept.deptId}">用户列表</a>
									<a href="${ctx}/deptMgr/edit/${dept.deptId}">编辑</a>
									<a href="${ctx}/deptMgr/delete/${dept.deptId}" onclick="if(!confirm('你确认要删除该部门？'))return false;">删除</a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${page.totalCount == '0' || empty page}">
						<tr align="center">
							<td colspan="4" align="left">
								暂无符合条件的记录
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</form>
		<jsp:include page="/plugins/pager.jsp" flush="true" />
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
		$("#deptName").val("");
		$("#status").val("");
	}
	
	//批量删除
	function delChecked()
	{
		if(getCheckboxCheckedValue("id") != "")
		{
			if(confirm('确定删除所选的部门？'))
			{
				$("#mainForm").attr("action","${ctx}/deptMgr/batchDelete");
				$("#mainForm").submit();
			}
		}
		else
		{
			alert("请选择需要删除的部门!");
		}
	}
	</script>
</html>