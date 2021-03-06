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
<%@ include file="/plugins/calendar.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
</head>
<body>
	<%@ include file="/pages/top.jsp"%>
	<form id="mainForm" action="${ctx}/fileMgr/deleteList" method="post">
		<input type="hidden" name="pageNo" id="pageNo" value="${page.pageNo}" />
		<input type="hidden" name="pageSize" id="pageSize" value="${page.pageSize}" />
		<input type="hidden" name="orderBy" id="orderBy" value="${page.orderBy}" />
		<input type="hidden" name="order" id="order" value="${page.order}" />
		<div id="container">
			<div class="layout clearfix">
				<div class="white_p10">
					<h4 class="content_hd long_content_hd">文件删除</h4>
					<div class="content_bd">
						<div class="gray_bor_bg">
							<div class="error msg" id="userManagerMsg" style="display: none" onclick="hideObj('userManagerMsg')">Message if login failed</div>
							<h5 class="gray_blod_word">按条件查询</h5>
							<div class="search_form">
								<div class="mt_10">
									<label>文&nbsp;件&nbsp;名:</label>
									<input type="text" class="input_79x19" name="filter_LIKE_fileUploadName" id="fileUploadName" value="${param['filter_LIKE_fileUploadName']}" />&nbsp;&nbsp;&nbsp;&nbsp;
									<label>上&nbsp;传&nbsp;人:</label>
									<input type="hidden" name="filter_EQ_uploadUserInfo.loginId" id="uploadUserId" value="${param['filter_EQ_uploadUserInfo.loginId']}" />
									<input type="text" class="input_79x19"  name="uploadUserName" id="uploadUserName" value="${param['uploadUserName']}" style="cursor: pointer;"  onclick="showUserSelectPage('上传人选择','uploadUserId','uploadUserName')"/>&nbsp;&nbsp;&nbsp;&nbsp;
									<label>上传时间:</label>
									<input name="_startFileUploadTime" id="_startFileUploadTime" type="text" class="input_79x19" value="${param['_startFileUploadTime']}" style="width:130px;cursor: pointer;"  /> 
									&nbsp;-&nbsp;<input name="_endFileUploadTime" id="_endFileUploadTime" type="text" class="input_79x19" value="${param['_endFileUploadTime']}" style="width:130px;cursor: pointer;"  />
									<div style="display: none">
										<textarea rows="1" cols="12" name="filter_GE_fileUploadTime" id="filter_GE_fileUploadTime">${param['filter_GE_fileUploadTime']}</textarea>
										<textarea rows="1" cols="12" name="filter_LE_fileUploadTime" id="filter_LE_fileUploadTime">${param['filter_LE_fileUploadTime']}</textarea>
									</div>
								</div>
								<div class="mt_10">
									<label>文件备注:</label>
									<input type="text" class="input_79x19"  name="filter_LIKE_fileRemark" id="fileRemark" value="${param['filter_LIKE_fileRemark']}" />&nbsp;&nbsp;&nbsp;&nbsp;
									<label>录&nbsp;制&nbsp;人:</label>
									<input type="hidden" name="filter_EQ_editUserInfo.loginId" id="fileEditId" value="${param['filter_EQ_editUserInfo.loginId']}" />
									<input type="text" class="input_79x19"  name="fileEditUserName" id="fileEditUserName" value="${param['fileEditUserName']}" style="cursor: pointer;"  onclick="showUserSelectPage('录制人选择','fileEditId','fileEditUserName')"/>&nbsp;&nbsp;&nbsp;&nbsp;
									<label>录制时间:</label>
									<input name="_startFileRecordTime" id="_startFileRecordTime" type="text" class="input_79x19" value="${param['_startFileRecordTime']}" style="width:130px;cursor: pointer;"  /> 
									&nbsp;-&nbsp;<input name="_endFileRecordTime" id="_endFileRecordTime" type="text" class="input_79x19" value="${param['_endFileRecordTime']}" style="width:130px;cursor: pointer;"  />
									<div style="display: none">
										<textarea rows="1" cols="12" name="filter_GE_fileRecordTime" id="filter_GE_fileRecordTime">${param['filter_GE_fileRecordTime']}</textarea>
										<textarea rows="1" cols="12" name="filter_LE_fileRecordTime" id="filter_LE_fileRecordTime">${param['filter_LE_fileRecordTime']}</textarea>
									</div>
								</div>
								<div class="mt_10">
									<label>接警编号:</label>
									<input class="input_79x19" id="policeCode" type="text" name="filter_EQ_policeCode" value="${param['filter_EQ_policeCode']}"/>&nbsp;&nbsp;&nbsp;&nbsp;
									<label>简要警情:</label>
									<input class="input_79x19" id="policeDesc" type="text" name="filter_LIKE_policeDesc" value="${param['filter_LIKE_policeDesc']}"/>&nbsp;&nbsp;&nbsp;&nbsp;
									<label>接警时间:</label>
									<input name="_startPoliceTime" id="_startPoliceTime" type="text" class="input_79x19" value="${param['_startPoliceTime']}" style="width:130px;cursor: pointer;"  /> 
									&nbsp;-&nbsp;<input name="_endPoliceTime" id="_endPoliceTime" type="text" class="input_79x19" value="${param['_endPoliceTime']}"  style="width:130px;cursor: pointer;"  />
									<div style="display: none">
										<textarea rows="1" cols="12" name="filter_GE_policeTime" id="filter_GE_policeTime">${param['filter_GE_policeTime']}</textarea>
										<textarea rows="1" cols="12" name="filter_LE_policeTime" id="filter_LE_policeTime">${param['filter_LE_policeTime']}</textarea>
									</div>
								</div>
								<div class="mt_10">
									<label>上传部门:</label>&nbsp;<input type="text" name="corpName" id="corpName" value="${param['corpName']}"  class="input_79x19" readonly="readonly" style="cursor: pointer;"/>
									<input type="hidden" name="filter_EQ_uploadCorpInfo.corpId" id="uploadCorpId" value="${param['filter_EQ_uploadCorpInfo.corpId']}"/>
									<div id="corpChooseDiv" style="position:absolute; border:solid 1px #CCCCCC; width:250px; height:200px; top:23px; left:0px; background:#FFFFFF;display: none;z-index:99;overflow:auto">
								         <ul id="treeDemo" class="ztree" style="width: 180px;">
								         </ul>
							        </div>&nbsp;&nbsp;&nbsp;
							        <label>文件分类:</label>
							        <select style="width: 135px" id="typeId" name="filter_EQ_fileTypeInfo.typeId">
							        	<option  value=""></option>
							        	<c:forEach var="fileType" items="${fileTypeList}">
							        		<option  value="${fileType.typeId }">${fileType.typeName }</option>
							        	</c:forEach>
							        </select>&nbsp;&nbsp;&nbsp;&nbsp;
									<label>到达时间:</label>
									<input type="text" class="input_38x19"  name="filter_GE_policeCostTime" id="filter_GE_policeCostTime" value="${param['filter_GE_policeCostTime']}" />&nbsp;&nbsp;-&nbsp;&nbsp;
									<input type="text" class="input_38x19" name="filter_LE_policeCostTime" id="filter_LE_policeCostTime" value="${param['filter_LE_policeCostTime']}" /> 分钟内
								</div>
								<div class="mt_10">
									<center>
									<a href="javascript:$('#mainForm').submit()" class="blue_mod_btn">查&nbsp;询</a>
									<a href="javascript:clearForm()" class="blue_mod_btn">重&nbsp;置</a>
									</center>
								</div>
							</div>
						</div>
						<div style="margin-top: 10px">
							<a href="javascript:batchDelete()" class="blue_mod_btn" >删除所选</a>
						</div>
						<div class="mange_table log_table mt_10">
							<table id="tableList" cellspacing="1" class="tablesorter" width="100%">
								<thead>
									<tr align="center">
										<th><input class="checkbox" type="checkbox" name="operid" onclick="javascript:selectAll('id',this.checked)"></th>
										<th class="sortable" onclick="javascript:sort('roleName','asc')">文件名</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">录制时间</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">上传时间</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">上传人</th>
										<th class="sortable" onclick="javascript:sort('status','asc')">采集人</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${page.totalCount != '0'}">
										<c:forEach var="file" items="${page.result}" varStatus="status">
											<tr align="center">
												<td><input class="checkbox" type="checkbox" name="id" value="${file.fileId}" /></td>
												<td>
													<c:if test="${file.fileStatus=='A'}">
														<c:if test="${file.fileType=='1'}">
															<a href="${ctx}/fileMgr/fileView/${file.fileId}?r=<%=Math.random() %>"  class="nyroModal" style="color: blue">
																${file.fileUploadName}
															</a>
														</c:if>
														<c:if test="${file.fileType=='2'}">
															<a href="${ctx}/fileMgr/fileView/${file.fileId}?r=<%=Math.random() %>"  class="nyroModal" style="color: blue">
																${file.fileUploadName}
															</a>
														</c:if>
														<c:if test="${file.fileType=='3'}">
															<a href="${ctx}/fileMgr/fileView/${file.fileId}?r=<%=Math.random() %>"  class="nyroModal" style="color: blue">
																${file.fileUploadName}
															</a>
														</c:if>
													</c:if>
													<c:if test="${file.fileStatus=='C' }">
														<a href="javascript:alert('视频正在剪辑中，请稍后');" style="color: blue">
															${file.fileUploadName}
														</a>
													</c:if>
												</td>
												<td>
													<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedFileRecordTime" parseLocale="en_US">
														${file.fileRecordTime}
													</fmt:parseDate>
													<fmt:formatDate value='${parsedFileRecordTime}' pattern="yyyy-MM-dd HH:mm"/>
												</td>
												<td>
													<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedFileUploadTime" parseLocale="en_US">
														${file.fileUploadTime}
													</fmt:parseDate>
													<fmt:formatDate value='${parsedFileUploadTime}' pattern="yyyy-MM-dd HH:mm"/>
												</td>
												<td>
													${file.uploadUserInfo.userName }
												</td>
												<td>
													${file.editUserInfo.userName }
												</td>
												<td>
													<a href="${ctx}/fileMgr/detail/${file.fileId}?r=<%=Math.random() %>" title="详情" class="nyroModal"><img src="${ctx }/images/icons/information-octagon.png" alt="详情" /></a>
													<a href="${ctx}/fileMgr/edit/${file.fileId}?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="修改"><img src="${ctx }/images/icons/edit.png" alt="修改" /></a>
													<a href="javascript:fileDelete('${file.fileId}')" title="删除"><img src="${ctx }/images/icons/cross.png" alt="删除" /></a>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${page.totalCount == '0'}">
										<tr align="center">
											<td colspan="7" align="left">暂无符合条件的记录</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<jsp:include page="/plugins/pager.jsp" flush="true" />
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@ include file="/pages/footer.jsp"%>
	<a id="modalWindowAction" class="nyroModal" href="#" target="_blank" style="display: none" title="采集人选择">采集人选择</a>
</body>
<script type="text/javascript">
	Calendar.setup(
	{
	     inputField  : "_startFileUploadTime",         // ID of the input field
	     displayArea : "filter_GE_fileUploadTime",
	     ifFormat    : "%Y-%m-%d",    // the date format
	     daFormat    : "%Y%m%d000000",
	     button      : "_startFileUploadTime"       // ID of the button
	});
	Calendar.setup(
	{
	     inputField  : "_endFileUploadTime",         // ID of the input field
	     displayArea : "filter_LE_fileUploadTime",
	     ifFormat    : "%Y-%m-%d",    // the date format
	     daFormat    : "%Y%m%d235959",
	     button      : "_endFileUploadTime"       // ID of the button
	});
	Calendar.setup(
	{
	     inputField  : "_startFileRecordTime",         // ID of the input field
	     displayArea : "filter_GE_fileRecordTime",
	     ifFormat    : "%Y-%m-%d",    // the date format
	     daFormat    : "%Y%m%d000000",
	     button      : "_startFileRecordTime"       // ID of the button
	});
	Calendar.setup(
	{
	     inputField  : "_endFileRecordTime",         // ID of the input field
	     displayArea : "filter_LE_fileRecordTime",
	     ifFormat    : "%Y-%m-%d",    // the date format
	     daFormat    : "%Y%m%d235959",
	     button      : "_endFileRecordTime"       // ID of the button
	});
	Calendar.setup(
	{
	     inputField  : "_startPoliceTime",         // ID of the input field
	     displayArea : "filter_GE_policeTime",
	     ifFormat    : "%Y-%m-%d",    // the date format
	     daFormat    : "%Y%m%d000000",
	     button      : "_startPoliceTime"       // ID of the button
	});
	Calendar.setup(
	{
	     inputField  : "_endPoliceTime",         // ID of the input field
	     displayArea : "filter_LE_policeTime",
	     ifFormat    : "%Y-%m-%d",    // the date format
	     daFormat    : "%Y%m%d235959",
	     button      : "_endPoliceTime"       // ID of the button
	});
	$(function() {
		$('.nyroModal').nyroModal({
			callbacks:{
			close:function(){
				if(document.getElementById('audioPlayerObj')!=null)
				{
					audioPlayerObj.controls.stop();
				}
			}
		}});
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
		$("#uploadCorpId").val(corpId);
		$.powerFloat.hide();
		$("#corpChooseDiv").css("display","none");
	}
	//页面加载初始化方法，实现查询条件下拉列表的回显功能
	function init()
	{
		$('#typeId').val("${param['filter_EQ_fileTypeInfo.typeId']}");
	}
	init();

	function query(){
		$("#pageNo").val("1");
		$("#pageSize").val("10");
		$("#mainForm").submit();
	}
	
	function clearForm(){
		$('#fileUploadName').val("");
		$('#uploadUserId').val("");
		$('#uploadUserName').val("");
		$('#_startFileUploadTime').val("");
		$('#_endFileUploadTime').val("");
		$('#filter_GE_fileUploadTime').val("");
		$('#filter_LE_fileUploadTime').val("");
		$('#fileRemark').val("");
		$('#fileEditId').val("");
		$('#fileEditUserName').val("");
		$('#_startFileRecordTime').val("");
		$('#_endFileRecordTime').val("");
		$('#filter_GE_fileRecordTime').text("");
		$('#filter_LE_fileRecordTime').text("");
		$('#policeCode').val("");
		$('#policeDesc').val("");
		$('#_startPoliceTime').val("");
		$('#_endPoliceTime').val("");
		$('#filter_GE_policeTime').text("");
		$('#filter_LE_policeTime').text("");
		$('#corpName').val("");
		$('#uploadCorpId').val("");
		$('#typeId').val("");
		$('#filter_GE_policeCostTime').val("");
		$('#filter_LE_policeCostTime').val("");
	}
	//
	function showUserSelectPage(title,userId,userName)
	{
		var url = '${ctx}/userMgr/userSelect?userId='+userId+'&userName='+userName+'&r='+Math.random();
	  	$('#modalWindowAction').attr("href",url);
	  	$('#modalWindowAction').attr("title",title);
	  	$('#modalWindowAction').click();
	}
	
	//文件删除
	function fileDelete(fileId)
	{
		if(confirm("您确认删除该文件吗？"))
		{
			$.getJSON("${ctx}/fileMgr/delete/"+fileId+"?r="+Math.random(), function(data){
				if(data.messageType=='1')
			    {
			    	alert(data.promptInfo);
			    	jumpPage(document.getElementById('pageNum').value);
			    }
			    else
			    {
			    	alert(data.promptInfo);
			    }
			});
		}
	}
	
	//文件批量删除
	function batchDelete()
	{
		var ids = getCheckboxCheckedValue("id");
		if(ids!= "")
		{
			if(confirm("您确认删除所选文件吗？"))
			{					
				$.getJSON("${ctx}/fileMgr/batchDelete?ids="+ids+"&r="+Math.random(), function(data){
					if(data.messageType=='1')
				    {
				    	alert(data.promptInfo);
				    	//location.href = location.href;
				    	jumpPage(document.getElementById('pageNum').value);
				    }
				    else
				    {
				    	alert(data.promptInfo);
				    }
				});
			}
		}
		else
		{
			alert("请选择需要删除的文件！");
		}
	}

	</script>
</html>