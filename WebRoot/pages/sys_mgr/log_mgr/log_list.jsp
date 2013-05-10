<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/tablesorter.jsp"%>
<%@ include file="/plugins/calendar.jsp"%>
</head>
<body>
	<%@ include file="/pages/top.jsp"%>
	<form id="mainForm" action="${ctx}/logMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">操作日志</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<div class="error msg" id="userManagerMsg" style="display: none" onclick="hideObj('userManagerMsg')">Message if login failed</div>
							<h5 class="gray_blod_word">按条件查询</h5>
							<div class="search_form">
								<div class="mt_10">
									<label>警员编号：</label>
									<input type="text" id="operUserCode"  name="filter_LIKE_operUserCode" value="${param['filter_LIKE_operUserCode']}" class="input_79x19" />
									&nbsp;&nbsp;&nbsp;&nbsp; 
									<label>操作类型：</label>
									<select name="filter_EQ_operType" id="operType">
										<option value="">--全部--</option>
										<option value="0">登陆</option>
										<option value="1">增加</option>
										<option value="2">删除</option>
										<option value="3">修改</option>
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp; 
									<label>操作时间：</label>
									<input name="_startDate" id="_startDate" type="text" class="input_79x19"  style="cursor: pointer;"  /> 
									&nbsp;-&nbsp;<input name="_endDate" id="_endDate" type="text" class="input_79x19"  style="cursor: pointer;"  />
									<div style="display: none">
										<textarea rows="1" cols="12" name="filter_GE_operTime" id="filter_GE_operTime"></textarea>
										<textarea rows="1" cols="12" name="filter_LE_operTime" id="filter_LE_operTime"></textarea>
									</div>
								</div>
								<div class="mt_10">
									<center>
									<a href="javascript:$('#mainForm').submit()" class="blue_mod_btn">查&nbsp;询</a>
									<a href="javascript:clearForm()" class="blue_mod_btn">重&nbsp;置</a>
									</center>
								</div>
							</div>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
									<thead>
									<tr align="center">
										<th class="sortable" onclick="javascript:sort('operUserCode','asc')">警员编号</th>
										<th class="sortable" onclick="javascript:sort('operIp','asc')">操作IP</th>
										<th class="sortable" onclick="javascript:sort('operTime','asc')">操作时间</th>
										<th class="sortable" onclick="javascript:sort('operCorpName','asc')">所属部门</th>
										<th class="sortable" onclick="javascript:sort('operType','asc')">操作类型</th>
										<th>操作描述</th>
									</tr>
									</thead>
									<tbody>								
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="log" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>
													${log.operUserCode}
												</td>
												<td>
													${log.operIp}
												</td>
												<td>
													<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedDateTime" parseLocale="en_US">
														${log.operTime}
													</fmt:parseDate>
													<fmt:formatDate value='${parsedDateTime}' pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td>
													${log.operCorpName}
												</td>
												<td>
													<c:choose>
														<c:when test="${log.operType=='0'}">登录</c:when>
														<c:when test="${log.operType=='1'}">增加</c:when>
														<c:when test="${log.operType=='2'}">删除</c:when>
														<c:when test="${log.operType=='3'}">修改</c:when>
													</c:choose>
												</td>
												<td>
													<c:out value="${log.operDesc}" />
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
//页面加载初始化方法，实现查询条件下拉列表的回显功能
function init()
{
	var operType = "${param['filter_EQ_operType']}";
	$("#operType").val(operType);
	$("#_startDate").val("${param['_startDate']}");
	$("#filter_GE_operTime").val("${param['filter_GE_operTime']}");
	$("#_endDate").val("${param['_endDate']}");
	$("#filter_LE_operTime").val("${param['filter_LE_operTime']}");
}
init();

Calendar.setup(
{
     inputField  : "_startDate",         // ID of the input field
     displayArea : "filter_GE_operTime",
     ifFormat    : "%Y-%m-%d",    // the date format
     daFormat    : "%Y%m%d000000",
     button      : "_startDate"       // ID of the button
});
Calendar.setup(
{
     inputField  : "_endDate",         // ID of the input field
     displayArea : "filter_LE_operTime",
     ifFormat    : "%Y-%m-%d",    // the date format
     daFormat    : "%Y%m%d235959",
     button      : "_endDate"       // ID of the button
});

function query(){
	$("#mainForm").submit();
}

function clearForm(){
	$("#filter_GE_operTime").val("");
	$("#filter_LE_operTime").val("");
	$("#operUserCode").val("");
	$("#operType").val("");
	$("#_startDate").val("");
	$("#_endDate").val("");
}

</script>
</html>