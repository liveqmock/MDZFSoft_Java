<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="page">
	<ul>
		<c:if test="${page.pageNo!=1}">
			<a href="javascript:jumpPage(1)">首页</a>
		</c:if>
		<c:if test="${page.pageNo==1}">
			<a href="javascript:jumpPage(1)" disabled="disabled">首页</a>
		</c:if>
		<c:if test="${page.hasPre}">
			<a href="javascript:jumpPage(${page.prePage})">前一页</a>
		</c:if>
		<c:if test="${not page.hasPre}">
			<a href="javascript:jumpPage(${page.prePage})" disabled="disabled">前一页</a>
		</c:if>
		<span style="color: red">${page.pageNo}</span> /
		<span>${page.totalPages}</span>
		<c:if test="${page.hasNext}">
			<a href="javascript:jumpPage(${page.nextPage})" >下一页</a>
		</c:if>
		<c:if test="${not page.hasNext}">
			<a href="javascript:jumpPage(${page.nextPage})" disabled="disabled">下一页</a>
		</c:if>
		<c:if test="${page.pageNo!=page.totalPages}">
			<a href="javascript:jumpPage(${page.totalPages})">末页</a>
		</c:if>
		<c:if test="${page.pageNo==page.totalPages}">
			<a href="javascript:jumpPage(${page.totalPages})" disabled="disabled">末页</a>
		</c:if>
		<select id="curPageSize" name="curPageSize" onchange="changePageSize()">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
			<option value="50">50</option>
		</select> 条/页&nbsp;&nbsp;&nbsp;
		<script>
			document.getElementById("curPageSize").value='${page.pageSize}';
		</script>
		转到
		<input type="text" class="page_input" id="pageNum" value="${page.pageNo}" />页
		<input name="" value="GO" class="page_anniu" type="button"
			onclick="javascript:jumpPage(document.getElementById('pageNum').value)" /> <%-- 记录总数:
		<c:out value="${page.totalCount}" /> --%>
	</ul>
</div>