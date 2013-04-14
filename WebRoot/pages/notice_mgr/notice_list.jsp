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
	<form id="mainForm" action="${ctx}/roleMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">角色管理</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">按条件查询</h5>
							<div class="search_form">
								<div class="mt_10">
									<label>角色名：</label>
									<input type="text" id="roleName" name="filter_LIKE_roleName" value="${param['filter_LIKE_roleName']}" class="input_79x19" />
									<a href="javascript:$('#mainForm').submit()" class="blue_mod_btn">查&nbsp;询</a>
									<a href="javascript:clearForm()" class="blue_mod_btn">重&nbsp;置</a>
								</div>
							</div>
						</div>
						<div style="margin-top: 10px">
							<a href="${ctx}/roleMgr/add?r=<%=Math.random() %>" class="blue_mod_btn nyroModal" target="_blank" title="新增角色">新增角色</a>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th>序号</th>
										<th class="sortable" onclick="javascript:sort('roleName','asc')">角色名称</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">角色状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="role" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>${status.count}</td>
												<td>${role.roleName}</td>
												<td>
													<c:choose>
														<c:when test="${role.status=='1'}">
															<font color="#dd6239">有效</font>
														</c:when>
														<c:when test="${role.status=='0'}">
															<font color="red">无效</font>
														</c:when>
													</c:choose>
												</td>
												<td>
													<!--img class="move" src="images/icons/arrow-move.png" alt="Move" title="Move" /-->
													<a href="${ctx}/roleMgr/view/${role.roleId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="角色查看"><img src="images/icons/information-octagon.png" alt="查看" /></a>
													<a href="${ctx}/roleMgr/edit/${role.roleId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="角色修改"><img src="images/icons/edit.png" alt="修改" /></a>
													<a href="#" onclick="roleDelete('${role.roleId}')" title="删除"><img src="images/icons/cross.png" alt="删除" /></a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${page.totalCount == '0'}">
										<tr align="center">
											<td colspan="4" align="left">暂无符合条件的记录</td>
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
	});

	function query(){
		$("#pageNo").val("1");
		$("#pageSize").val("10");
		$("#mainForm").submit();
	}
	
	function clearForm(){
		$("#roleName").val("");
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
	//关闭弹出窗口并刷新页面
	function closeModalWindow()
	{
		$.nmTop().close();
		//window.location=window.location;
		window.location.reload();
	}
	
	//角色删除
	function roleDelete(roleId)
	{
		if(confirm("您确认删除该角色吗？"))
		{
			$.getJSON("${ctx}/roleMgr/delete/"+roleId+"?r="+Math.random(), function(data){
				if(data.messageType=='1')
			    {
			    	alert(data.promptInfo);
			    	//window.location=window.location;
			    	window.location.reload();
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