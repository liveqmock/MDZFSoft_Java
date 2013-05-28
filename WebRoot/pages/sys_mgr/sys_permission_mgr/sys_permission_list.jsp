<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/tablesorter.jsp"%>
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
</head>
<body>
	<%@ include file="/pages/top.jsp"%>
	<form id="mainForm" action="${ctx}/sysPermissionMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">菜单管理</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">按条件查询</h5>
							<div class="search_form">
								<div class="mt_10">
									<label>菜单名称：</label>
									<input type="text" id="permissionName" name="filter_LIKE_permissionName" value="${param.filter_LIKE_permissionName}" class="input_79x19" />
									&nbsp;&nbsp;&nbsp;&nbsp;
									<label>URL：</label>
									<input type="text" id="permissionUrl" name="filter_LIKE_permissionUrl" value="${param.filter_LIKE_permissionUrl}" class="input_79x19" />
									&nbsp;&nbsp;&nbsp;&nbsp;
								</div>
								<div class="mt_10">
									<center>
										<a href="javascript:$('#mainForm').submit()" class="blue_mod_btn">查&nbsp;询</a>
										<a href="javascript:clearForm()" class="blue_mod_btn">重&nbsp;置</a>
									</center>
								</div>
							</div>
						</div>
						<div style="margin-top: 10px">
							<a href="${ctx}/sysPermissionMgr/add?r=<%=Math.random() %>" class="blue_mod_btn nyroModal" target="_blank" title="新增菜单">新增菜单</a>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th>
											
										</th>
										<th class="sortable" onclick="javascript:sort('permissionName','asc')">菜单名称</th>
										<th class="sortable" onclick="javascript:sort('permissionType','asc')">权限类型</th>
										<th class="sortable" onclick="javascript:sort('permissionUrl','asc')">URL</th>
										<th class="sortable" onclick="javascript:sort('permissionSort','asc')">排序</th>
										<th class="sortable" onclick="javascript:sort('parentPermissionId','asc')">上级菜单</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="sysPermission" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>
													${status.count}
												</td>
												<td align="left" style="padding-left: 10px">
													<c:if test="${sysPermission.parentPermissionId==0}">
														${sysPermission.permissionName}
													</c:if>
													<c:if test="${sysPermission.parentPermissionId!=0}">
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${sysPermission.permissionName}
													</c:if>
												</td>
												<td>${sysPermission.permissionType}</td>
												<td>${sysPermission.permissionUrl}</td>
												<td>${sysPermission.permissionSort}</td>
												<td>${sysPermission.parentPermissionId}</td>
												<td>${sysPermission.status}</td>
												<td>
													<a href="${ctx}/sysPermissionMgr/edit/${sysPermission.permissionId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="修改菜单"><img src="images/icons/edit.png" alt="修改" /></a>
													<a href="javascript:del(${sysPermission.permissionId})" title="删除"><img src="images/icons/cross.png" alt="删除" /></a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${page.totalCount == '0'}">
										<tr align="center">
											<td colspan="9" align="left">暂无符合条件的记录</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<jsp:include page="/plugins/pager.jsp" flush="true" />
						<div class=" mt_10 pb_200"></div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@ include file="/pages/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function() {
		$('.nyroModal').nyroModal();
		init();
	});
	
	//页面加载初始化方法，实现查询条件下拉列表的回显功能
	function init()
	{
		
	}
	

	function query(){
		$("#pageNo").val("1");
		$("#pageSize").val("10");
		$("#mainForm").submit();
	}
	
	function clearForm(){
		$("#permissionName").val("");
		$("#permissionType").val("");
		$("#permissionUrl").val("");
		$("#permissionSort").val("");
		$("#parentPermissionId").val("");
		$("#systemId").val("");
		$("#permissionIco").val("");
		$("#status").val("");
		$("#createBy").val("");
		$("#createTime").val("");
		$("#lastModifyBy").val("");
		$("#lastModifyTime").val("");
	}
	
	//批量删除
	function delChecked()
	{
		if(getCheckboxCheckedValue("id") != "")
		{
			if(confirm('确定删除所选的SysPermission？'))
			{
				$("#mainForm").attr("action","${ctx}/sysPermissionMgr/batchDelete");
				$("#mainForm").submit();
			}
		}
		else
		{
			alert("请选择需要删除的SysPermission!");
		}
	}
	//密码重置
	function del(id)
	{
		if(confirm("您确认删除该SysPermission吗？"))
		{
			$.getJSON("${ctx}/sysPermissionMgr/delete/"+id+"?r="+Math.random(), function(data){
				if(data.messageType=='1')
			    {
			    	alert(data.promptInfo);
			    	location.href = location.href;
			    }
			    else
			    {
			    	alert(data.promptInfo);
			    }
			});
		}
	}
	</script>
</html>