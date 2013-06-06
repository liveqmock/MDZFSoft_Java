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
	<form id="mainForm" action="${ctx}/noticeMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">公告管理</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">按条件查询</h5>
							<div class="search_form">
								<div class="mt_10">
									<label>公告标题：</label>
									<input type="text" id=noticeTitle name="filter_LIKE_noticeTitle" value="${param['filter_LIKE_noticeTitle']}" class="input_79x19" />
									<a href="javascript:$('#mainForm').submit()" class="blue_mod_btn">查&nbsp;询</a>
									<a href="javascript:clearForm()" class="blue_mod_btn">重&nbsp;置</a>
								</div>
							</div>
						</div>
						<div style="margin-top: 10px">
							<a href="${ctx}/noticeMgr/add?r=<%=Math.random() %>" class="blue_mod_btn nyroModal" target="_blank" title="新增公告">新增公告</a>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th>序号</th>
										<th class="sortable" onclick="javascript:sort('noticeTitle','asc')">公告标题</th>
										<th class="sortable" onclick="javascript:sort('noticeContent','asc')">公告内容</th>
										<th class="sortable" onclick="javascript:sort('createTime','asc')">创建时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="notice" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>${status.count}</td>
												<td align="left">
													<a class="nyroModal" style="color: blue" href="${ctx}/noticeMgr/view/${notice.noticeId}?mgrFlag=1&r=<%=Math.random() %>" target="_blank" title="公告查看">${notice.noticeTitle}</a>
												</td>
												<td align="left">
												<c:set var="noticeContent" value="${notice.noticeContent}"></c:set>
												<c:choose> 
											     	<c:when test="${fn:length(noticeContent) > 40}"> 
											     		<c:out value="${fn:substring(noticeContent, 0, 40)}..." /> 
											     	</c:when> 
											     	<c:otherwise> 
											      		<c:out value="${noticeContent}" /> 
											     	</c:otherwise>
											    </c:choose>
												</td>
												<td>
													<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedDateTime" parseLocale="en_US">
														<c:out value="${notice.createTime}" />
													</fmt:parseDate>
													<fmt:formatDate value='${parsedDateTime}' pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<!--img class="move" src="images/icons/arrow-move.png" alt="Move" title="Move" /-->
													<a href="${ctx}/noticeMgr/edit/${notice.noticeId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="公告修改"><img src="images/icons/edit.png" alt="修改" /></a>
													<a href="javascript:noticeDelete('${notice.noticeId}')" title="删除"><img src="images/icons/cross.png" alt="删除" /></a>
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
		$("#noticeName").val("");
	}
	
	//批量删除
	function delChecked()
	{
		if(getCheckboxCheckedValue("id") != "")
		{
			if(confirm('确定删除所选的公告？'))
			{
				$("#mainForm").attr("action","${ctx}/noticeMgr/batchDelete");
				$("#mainForm").submit();
			}
		}
		else
		{
			alert("请选择需要删除的公告!");
		}
	}
	
	//公告删除
	function noticeDelete(noticeId)
	{
		if(confirm("您确认删除该公告吗？"))
		{
			$.getJSON("${ctx}/noticeMgr/delete/"+noticeId+"?r="+Math.random(), function(data){
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