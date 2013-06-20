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
<body>
	<form id="mainForm" action="${ctx}/userMgr/userSelect" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<!-- editby 孙  20130620 -->
		<input type="hidden" name="userName" id="userName" value="${param['userName']}" />
		<input type="hidden" name="userId" id="userId" value="${param['userId']}" />
			<div class="clearfix">
			<div class="white_p10">
			<div class="gray_bor_bg">
				<h5 class="gray_blod_word">按条件查询</h5>
				<div class="search_form">
					<div class="mt_10">
						<label>姓名：</label>
						<input type="text" id="userName" name="filter_LIKE_userName" value="${param['filter_LIKE_userName']}" class="input_79x19" size="10"/>
						&nbsp;&nbsp;&nbsp;&nbsp; 
						<label>警员编号：</label>
						<input type="text" id="userCode" name="filter_LIKE_userCode" value="${param['filter_LIKE_userCode']}" class="input_79x19" size="10"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label>所属部门：</label>
						<%
							String corpName=request.getParameter("corpName");
							if(null!=corpName)
								corpName=URLDecoder.decode(corpName,"UTF-8");
							else
								corpName="";
						 %>
						<input type="text" name="corpName" id="corpName" value="<%=corpName %>" size="20" class="input_79x19" readonly="readonly"  style="cursor: pointer;"/>
						<input type="hidden" name="fixedCorpId" id="fixedCorpId" value="${param['fixedCorpId']}"/>
						<input type="hidden" name="filter_EQ_sysCorp.corpId" id="corpId" value="${param['filter_EQ_sysCorp.corpId']}"/>
						
						<div id="corpChooseDiv" style="position:absolute; border:solid 1px #CCCCCC; width:250px; height:200px; top:23px; left:0px; background:#FFFFFF;display: none;z-index:99;overflow:auto">
					         <ul id="treeDemo" class="ztree" style="width: 180px;">
					         </ul>
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
							<th class="sortable" onclick="javascript:sort('userCode','asc')">警员编号</th>
<!-- 							<th class="sortable" onclick="javascript:sort('loginName','asc')">登录帐号</th>
 -->							<th class="sortable" onclick="javascript:sort('userName','asc')">真实姓名</th>
							<th class="sortable" onclick="javascript:sort('sex','asc')">性&nbsp;&nbsp;别</th>
							<th>所属部门</th>
							<!-- <th>所属角色</th> -->
							<th>选择</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${page.totalCount != '0'}">
							<c:forEach var="user" items="${page.result}" varStatus="status">
								<tr align="center">
									<td>${user.userCode}</td>
									<%-- <td>${user.loginName}</td> --%>
									<td>${user.userName}</td>
									<td>${user.sex}</td>
									<td>${user.sysCorp.corpName}</td>
									<%-- <td>${user.sysLoginRoles[0].sysRole.roleName}</td> --%>
									<td><a href="javascript:userSelect('${user.loginId}','${user.userName}')" class="green_mod_btn">选择</a></td>
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
	$(function() {
		//树形菜单
		var setting = {
			data: {
				simpleData: {enable: true}
			},
			callback: {
				onClick: onClick
			}
		};
		var zNodes = ${tree.json};  
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
	});
	
	<c:if test="${fixedCorpId ne 'true'}">
	$("#corpName").powerFloat({
		eventType: "click",
		target: $("#corpChooseDiv")	
	});
	</c:if>
	function onClick(e, treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		var corpId = nodes[0].id;
		var corpName = nodes[0].name;
		$("#corpName").val(corpName);
		$("#corpId").val(corpId);
		$.powerFloat.hide();
		$("#corpChooseDiv").css("display","none");
	}
	//页面加载初始化方法，实现查询条件下拉列表的回显功能
	function init()
	{
		$("#corpId").val("${param['filter_EQ_sysCorp.corpId']}");
	}
	init();

	function query(){
		$("#pageNo").val("1");
		$("#pageSize").val("10");
		$("#mainForm").submit();
	}
	
	function clearForm(){
		$("#userName").val("");
		$("#userCode").val("");
		<c:if test="${fixedCorpId ne 'true'}">
		$("#corpId").val("");
		$("#corpName").val("");
		</c:if>
	}
	
	function userSelect(userId,userName)
	{
		window.parent.document.getElementById("${param['userId']}").value=userId;
		window.parent.document.getElementById("${param['userName']}").value=userName;
		parent.closeModalWindow(false);
	}
	
	</script>
</html>