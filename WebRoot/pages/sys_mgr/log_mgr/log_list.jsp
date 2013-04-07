<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/plugins/tablesorter.jsp"%>
		<%@ include file="/plugins/calendar.jsp"%>
	</head>
	<body>
		<div class="mianbaoxie">
			当前位置：<a href="#1">系统管理</a> > 系统日志
		</div>
		<form id="mainForm" action="${ctx}/logMgr" method="post">
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
						<td class="title" width="100">操作者名称：</td>
						<td><input type="text" name="filter_LIKE_operUserName" id="operUserName" value="${param['filter_LIKE_operUserName']}" size="20" class="form_input"/>
						</td>
						<td class="title">操作类型：</td>
						<td>
							<select name="filter_EQ_operType" id="operType" class="form_input" style="width: 200px;">
								<option value="">--全部--</option>
								<option value="0">登陆</option>
								<option value="1">增加</option>
								<option value="2">删除</option>
								<option value="3">修改</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="title" width="100">操作时间：</td>
						<td colspan="3">
							<input name="_startDate" id="_startDate" type="text" class="form_input"  style="width:130px;cursor: pointer;"  /> 
							<div style="display: none">
								<textarea rows="1" cols="12" name="filter_LIKE_operTime" id="filter_LIKE_operTime"></textarea>
							</div>
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
		</div>
		<div class="table_list table_div" >
			<table id="tableList" cellspacing="1" class="table_border" width="100%">
					<tr align="center">
						<td class="table_list_title title_border_a" onclick="javascript:sort('operTime','asc')" width="20%"><div class="paixu_3">操作时间</div></td>
						<td class="table_list_title title_border_a" onclick="javascript:sort('operType','asc')" width="15%"><div class="paixu_3">操作类型</div></td>
						<td class="table_list_title title_border_a" onclick="javascript:sort('operUserName','asc')" width="20%"><div class="paixu_3">操作者名称</div></td>
						<td class="table_list_title" width="45%">操作描述</td>
					</tr>
					<c:if test="${page.totalCount != '0'}">
						<c:forEach var="log" items="${page.result}" varStatus="status">
							<tr align="center">
								<td>
									<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedDateTime" parseLocale="en_US">
										${log.operTime}
									</fmt:parseDate>
									<fmt:formatDate value='${parsedDateTime}' pattern="yyyy-MM-dd HH:mm:ss"/>
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
									<c:out value="${log.operUserName}" />
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
		var operType = "${param['filter_EQ_operType']}";
		$("#operType").val(operType);

		$("#_startDate").val("${param['_startDate']}");
		$("#filter_LIKE_operTime").val("${param['filter_LIKE_operTime']}");
	}
	init();

	Calendar.setup(
			{
		      inputField  : "_startDate",         // ID of the input field
		      displayArea : "filter_LIKE_operTime",
		      ifFormat    : "%Y-%m-%d",    // the date format
		      daFormat    : "%Y%m%d",
		      button      : "_startDate"       // ID of the button
			});
	
	function query(){
		$("#mainForm").submit();
	}

	function clearForm(){
		$("#filter_LIKE_operTime").val("");
		$("#operUserName").val("");
		$("#operType").val("");
		$("#_startDate").val("");
	}

	</script>
</html>