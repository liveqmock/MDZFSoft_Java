<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/tablesorter.jsp"%>
<%@ include file="/plugins/jquery-powerFloat.jsp" %>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body style="width:580px;">
	<form id="mainForm" action="${ctx}/ftpMgr/ftpSelect" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<!-- editby 孙  20130620 -->
		<input type="hidden" name="ftpId" id="ftpId" value="${param['ftpId']}" />
		<input type="hidden" name="ftpIp" id="ftpIp" value="${param['ftpIp']}" />
			<div class="clearfix">
			<div class="white_p10">
			<div class="mange_table log_table mt_10">
				<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
					<thead>
						<tr align="center">
							<th style='width:100px;' class="sortable" onclick="javascript:sort('ftpIp','asc')">IP地址</th>
							<th class="sortable" onclick="javascript:sort('ftpDesc','asc')">描述</th>
							<th style='width:100px;'>选择</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${page.totalCount != '0'}">
							<c:forEach var="ftp" items="${page.result}" varStatus="status">
								<tr align="center">
									<td>${ftp.ftpIp}</td>
									<td id='${ftp.ftpId }'>${ftp.ftpDesc}</td>
									<td><a href="javascript:ftpSelect(this,'${ftp.ftpId}','${ftp.ftpIp }')" class="green_mod_btn">选择</a></td>
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
			</div>
			</div>
	</form>
</body>
<script type="text/javascript">
	function ftpSelect(obj,ftpId,ftpIp)
	{
		window.parent.document.getElementById("${param['ftpId']}").value=ftpId;
		window.parent.document.getElementById("${param['ftpIp']}").value=ftpIp;
		window.parent.document.getElementById("${param['ftpDesc']}").innerHTML=$("#"+ftpId).html();
		parent.closeModalWindow(false);
	}
	
	</script>
</html>