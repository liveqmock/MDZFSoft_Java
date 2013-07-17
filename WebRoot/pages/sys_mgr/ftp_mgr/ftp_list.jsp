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
	<form id="mainForm" action="${ctx}/ftpMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">文件服务器管理</h4>
					<div class="content_bd">
						<div style="margin-top: 10px">
							<a href="${ctx}/ftpMgr/add?r=<%=Math.random() %>" class="blue_mod_btn nyroModal" target="_blank" title="新增FTP服务器">新增FTP</a>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th>
											
										</th>
										<th class="sortable" onclick="javascript:sort('serverName','asc')">服务器名称</th>
										<th class="sortable" onclick="javascript:sort('ftpIp','asc')">服务器地址</th>
										<th class="sortable" onclick="javascript:sort('ftpPort','asc')">服务器端口</th>
										<th class="sortable" onclick="javascript:sort('ftpUser','asc')">登陆用户名</th>
										<th class="sortable" onclick="javascript:sort('fileRootUrl','asc')">文件存储URL</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="sysFtp" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>
													${status.count}
												</td>
												<td> ${sysFtp.serverName}
												</td>
												<td> ${sysFtp.ftpIp}
												</td>
												<td>${sysFtp.ftpPort}</td>
												<td>${sysFtp.ftpUser}</td>
												<td align="left">${sysFtp.fileRootUrl}</td>
												<td>
												<c:choose>
													<c:when test="${sysFtp.status=='1'}">
														<font color="blue">有效</font>
													</c:when>
													<c:when test="${sysFtp.status=='0'}">
														<font color="red">无效</font>
													</c:when>
												</c:choose>
												</td>
												<td>
													<a href="${ctx}/ftpMgr/edit/${sysFtp.ftpId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="修改Ftp服务器信息"><img src="images/icons/edit.png" alt="修改" /></a>
													<a href="javascript:ftpDelete(${sysFtp.ftpId})" title="删除"><img src="images/icons/cross.png" alt="删除" /></a>
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
	
		//角色删除
	function ftpDelete(ftpId)
	{
		if(confirm("您确认删除该FTP服务器吗？"))
		{
			$.getJSON("${ctx}/ftpMgr/delete/"+ftpId+"?r="+Math.random(), function(data){
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