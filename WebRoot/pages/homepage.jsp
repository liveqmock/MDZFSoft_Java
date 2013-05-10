<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/plugins/tablesorter.jsp"%>
	<%@ include file="/plugins/jquery-nyroModal.jsp" %>
</head>

<body>
<%@ include file="/pages/top.jsp"%>
<form id="mainForm" action="${ctx}/index" method="post">
	<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
	<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
	<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
	<input type="hidden" name="order" id="order" value="${page.order}" />
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<jsp:include page="/pages/left.jsp" flush="true" />
			<div class="w_805 fr">
				<div class="white_p10">
					<h4 class="content_hd" id="mainTitle">公告</h4>
					<div class="content_bd">
						<div class="mange_table" id="mainContext">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th>序号</th>
										<th class="sortable" onclick="javascript:sort('noticeTitle','asc')">公告标题</th>
										<th class="sortable" onclick="javascript:sort('createTime','asc')">发布时间</th>
										<th class="sortable" onclick="javascript:sort('createTime','asc')">发布部门</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="notice" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>${status.count}</td>
												<td>
													<a class="nyroModal" style="color: blue" href="${ctx}/noticeMgr/view/${notice.noticeId}?r=<%=Math.random() %>" target="_blank" title="公告查看">${notice.noticeTitle}</a>
												</td>
												<td>
													<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedDateTime" parseLocale="en_US">
														<c:out value="${notice.createTime}" />
													</fmt:parseDate>
													<fmt:formatDate value='${parsedDateTime}' pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													${notice.sysCorp.corpName}
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${page.totalCount == '0'}">
										<tr align="center">
											<td colspan="5" align="left">暂无符合条件的记录</td>
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
	</div>
</form>
<%@ include file="/pages/footer.jsp"%>

<script>
$(document).ready(function()
{
	$('.nyroModal').nyroModal();
});
//关闭弹出窗口并刷新页面
function closeModalWindow()
{
	$.nmTop().close();
}

//
function showNoticeDetail(id)
{
	var url = '${ctx}/noticeMgr/view/'+id+'?r='+Math.random();
  	$('#modalWindowAction').attr("href",url);
  	$('#modalWindowAction').click();
}

//关闭弹出窗口并刷新页面
function closeModalWindow()
{
	$.nmTop().close();
}
</script>
</body>
</html>
