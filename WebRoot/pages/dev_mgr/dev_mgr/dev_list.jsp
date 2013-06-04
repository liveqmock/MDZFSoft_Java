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
	<form id="mainForm" action="${ctx}/devMgr" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">设备管理</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">按条件查询</h5>
							<div class="search_form">
								<div class="mt_10">
									<label>设备编号：</label>
									<input type="text" id="devNo" name="filter_LIKE_devNo" value="${param['filter_LIKE_devNo']}" class="input_79x19" />
									&nbsp;&nbsp;&nbsp;&nbsp; 
									<label>设备类型：</label>
									<select style="width: 142px" id="devTypeId" name="filter_EQ_devTypeInfo.devTypeId">
							        	<option value=""></option>
							        	<c:forEach items="${devTypeList}" var="item">
							        		<option value="${item.devTypeId }">${item.devTypeName}</option>
							        	</c:forEach>
							        </select>
							        &nbsp;&nbsp;&nbsp;&nbsp;
							        <label>设备厂商：</label>
									<select style="width: 142px" id="devFacturerId" name="filter_EQ_devFacturerInfo.devFacturerId">
							        	<option value=""></option>
							        	<c:forEach items="${devFacturerList}" var="item">
							        		<option value="${item.devFacturerId }">${item.devFacturerName}</option>
							        	</c:forEach>
							        </select>
							   	</div>
							   	<div class="mt_10">
									<label>所属部门：</label>
									<input type="hidden" name="filter_EQ_sysCorp.corpId" id="corpId" value="${param['filter_EQ_sysCorp.corpId']}"/>
									<input type="text" name="corpName" id="corpName" value="${param['corpName']}" size="20" class="input_79x19" readonly="readonly" style="cursor: pointer;"/>
									<div id="corpChooseDiv" style="position:absolute; border:solid 1px #CCCCCC; width:250px; height:200px; top:23px; left:0px; background:#FFFFFF;display: none;z-index:99;overflow:auto">
								         <ul id="treeDemo" class="ztree" style="width: 180px;">
								         </ul>
							        </div>
							        &nbsp;&nbsp;&nbsp;&nbsp;
							        <label>所属警员：</label>
							        <input type="hidden" name="filter_EQ_devUserInfo.loginId" id="userId" value="${param['filter_EQ_devUserInfo.loginId']}" />
									<input type="text" class="input_79x19"  name="userName" id="userName" value="${param['userName']}" style="cursor: pointer;"  onclick="showUserSelectPage('警员选择','userId','userName')"/>
							        &nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:$('#mainForm').submit()" class="blue_mod_btn">查&nbsp;询</a>
									<a href="javascript:clearForm()" class="blue_mod_btn">重&nbsp;置</a>
								</div>
							</div>
						</div>
						<div style="margin-top: 10px">
							<a href="${ctx}/devMgr/add?r=<%=Math.random() %>" class="blue_mod_btn nyroModal" target="_blank" title="新增设备">新增设备</a>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th class="sortable" onclick="javascript:sort('devNo','asc')">设备编号</th>
										<th>所属部门</th>
										<th>所属警员</th>
										<th>设备类型</th>
										<th>设备厂商</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="dev" items="${page.result}" varStatus="status">
											<tr align="center">
												<td>${dev.devNo}</td>
												<td>${dev.sysCorp.corpName}</td>
												<td>${dev.devUserInfo.userName}</td>
												<td>${dev.devTypeInfo.devTypeName}</td>
												<td>${dev.devFacturerInfo.devFacturerName}</td>
												<td>
													<a href="${ctx}/devMgr/edit/${dev.devId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="设备修改"><img src="images/icons/edit.png" alt="修改" /></a>
													<a href="javascript:devDelete('${dev.devId}')" title="删除"><img src="images/icons/cross.png" alt="删除" /></a>
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
	<a id="modalWindowAction" class="nyroModal" href="#" target="_blank" style="display: none" title="使用人选择">使用人选择</a>
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
		$("#devFacturerId").val("${param['filter_EQ_devFacturerInfo.devFacturerId']}");
		$("#devTypeId").val("${param['filter_EQ_devTypeInfo.devTypeId']}");
	}
	init();

	function query(){
		$("#pageNo").val("1");
		$("#pageSize").val("10");
		$("#mainForm").submit();
	}
	
	function clearForm(){
		$("#devNo").val("");
		$("#corpId").val("");
		$("#corpName").val("");
		$("#devFacturerId").val("");
		$("#devTypeId").val("");
		$("#userId").val("");
		$("#userName").val("");
	}
	
	//删除
	function devDelete(roleId)
	{
		if(confirm("您确认删除该设备吗？"))
		{
			$.getJSON("${ctx}/devMgr/delete/"+roleId+"?r="+Math.random(), function(data){
				if(data.messageType=='1')
			    {
			    	alert(data.promptInfo);
			    	//window.location.reload();
			    	location.href = location.href;
			    }
			    else
			    {
			    	alert(data.promptInfo);
			    }
			});
		}
	}
	
	//
	function showUserSelectPage(title,userId,userName)
	{
		var url = '${ctx}/userMgr/userSelect?userId='+userId+'&userName='+userName+'&r='+Math.random();
	  	$('#modalWindowAction').attr("href",url);
	  	$('#modalWindowAction').attr("title",title);
	  	$('#modalWindowAction').click();
	}
	</script>
</html>