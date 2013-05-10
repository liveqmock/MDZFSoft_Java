<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/tablesorter.jsp"%>
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
<%@ include file="/plugins/jquery-powerFloat.jsp" %>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body>
	<%@ include file="/pages/top.jsp"%>
	<form id="mainForm" action="${ctx}/userMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">用户管理</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<div class="error msg" id="userManagerMsg" style="display: none" onclick="hideObj('userManagerMsg')">Message if login failed</div>
							<h5 class="gray_blod_word">按条件查询</h5>
							<div class="search_form">
								<div class="mt_10">
									<label>姓名：</label>
									<input type="text" id="userName" name="filter_LIKE_userName" value="${param['filter_LIKE_userName']}" class="input_79x19" />
									&nbsp;&nbsp;&nbsp;&nbsp; <label>警员编号：</label>
									<input type="text" id="userCode" name="filter_LIKE_userCode" value="${param['filter_LIKE_userCode']}" class="input_79x19" />
									&nbsp;&nbsp;&nbsp;&nbsp; 
									<label>所属部门：</label>
									<input type="hidden" name="filter_EQ_sysCorp.corpId" id="corpId" value="${param['filter_EQ_sysCorp.corpId']}"/>
									<input type="text" name="corpName" id="corpName" value="${param['corpName']}" size="20" class="input_79x19" readonly="readonly" style="cursor: pointer;"/>
									<div id="corpChooseDiv" style="position:absolute; border:solid 1px #CCCCCC; width:250px; height:200px; top:23px; left:0px; background:#FFFFFF;display: none;z-index:99;">
								         <iframe style="position:absolute;width:100%;height:100%;_filter:alpha(opacity=0);opacity=0;border-style:none;z-index:-1;"></iframe>
								         <ul id="treeDemo" class="ztree" style="width: 180px;">
								         </ul>
							        </div>
									<a href="javascript:$('#mainForm').submit()" class="blue_mod_btn">查&nbsp;询</a>
									<a href="javascript:clearForm()" class="blue_mod_btn">重&nbsp;置</a>
								</div>
							</div>
						</div>
						<div style="margin-top: 10px">
							<a href="${ctx}/userMgr/add?r=<%=Math.random() %>" class="blue_mod_btn nyroModal" target="_blank" title="新增用户">新增用户</a>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th class="sortable" onclick="javascript:sort('userCode','asc')">警员编号</th>
										<th class="sortable" onclick="javascript:sort('loginName','asc')">登录帐号</th>
										<th class="sortable" onclick="javascript:sort('userName','asc')">真实姓名</th>
										<th class="sortable" onclick="javascript:sort('sex','asc')">性&nbsp;&nbsp;别</th>
										<th>所属部门</th>
										<th>所属角色</th>
										<th class="sortable" onclick="javascript:sort('loginLastTime','asc')">最后登录时间</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">用户状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="user" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>${user.userCode}</td>
												<td>${user.loginName}</td>
												<td>${user.userName}</td>
												<td>${user.sex}</td>
												<td>${user.sysCorp.corpName}</td>
												<td>
													${user.sysLoginRoles[0].sysRole.roleName}
												</td>
												<td>
													<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedDateTime" parseLocale="en_US">
														<c:out value="${user.loginLastTime}" />
													</fmt:parseDate>
													<fmt:formatDate value='${parsedDateTime}' pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<c:choose>
														<c:when test="${user.status=='1'}">
															<font color="#dd6239">有效</font>
														</c:when>
														<c:when test="${user.status=='0'}">
															<font color="red">无效</font>
														</c:when>
													</c:choose>
												</td>
												<td>
													<!--img class="move" src="images/icons/arrow-move.png" alt="Move" title="Move" /-->
													<a href="${ctx}/userMgr/view/${user.loginId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="用户查看"><img src="images/icons/information-octagon.png" alt="查看" /></a>
													<a href="${ctx}/userMgr/edit/${user.loginId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="修改用户"><img src="images/icons/edit.png" alt="修改" /></a>
													<!--a href="###" onclick="userDel('')" title="删除"><img src="images/icons/cross.png" alt="删除" /></a-->
													<a href="#" onclick="resetPwd('${user.loginId}')" title="密码重置">密码重置</a>
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
	$("#corpName").powerFloat({
		eventType: "click",
		target: $("#corpChooseDiv")	
	});
	
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
		$("#corpId").val("${param['filter_sysCorp.corpId']}");
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
		$("#corpId").val("");
		$("#corpName").val("");
	}
	
	//批量删除
	function delChecked()
	{
		if(getCheckboxCheckedValue("id") != "")
		{
			if(confirm('确定删除所选的用户？'))
			{
				$("#mainForm").attr("action","${ctx}/userMgr/batchDelete");
				$("#mainForm").submit();
			}
		}
		else
		{
			alert("请选择需要删除的用户!");
		}
	}
	//密码重置
	function resetPwd(loginId)
	{
		if(confirm("您确认重置该用户的密码吗？"))
		{
			$.getJSON("${ctx}/userMgr/resetPwd/"+loginId+"?r="+Math.random(), function(data){
				if(data.messageType=='1')
			    {
			    	alert(data.promptInfo);
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