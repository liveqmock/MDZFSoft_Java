<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
<%@ include file="/plugins/jquery-validation.jsp"%>
<%@ include file="/plugins/jquery-powerFloat.jsp" %>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body>
	<form:form id="addForm" name="addForm" method="POST" action="${ctx}/devMgr/save" modelAttribute="resultObject">
			<div class="gray_bor_bg">
				<div class="table_div">
					<table width="100%" class="table_border">
						<tr>
							<td class="title" width="100"></td>
							<td align="left">
								带<font color="red">&nbsp;*&nbsp;</font>为必填项
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								<font color="red">*&nbsp;</font>设备编号
							</td>
							<td>
								<form:input path="devNo" cssClass="form_input {required:true,maxlength:30}" size="20" />
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								<font color="red">*&nbsp;</font>设备类型
							</td>
							<td>
								<form:select path="devTypeInfo.devTypeId" cssClass="form_input {required:true}" style="width: 140px">
									<form:option value=""></form:option>
									<form:options items="${devTypeList}" itemLabel="devTypeName" itemValue="devTypeId"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								<font color="red">*&nbsp;</font>设备厂商
							</td>
							<td>
								<form:select path="devFacturerInfo.devFacturerId" cssClass="form_input {required:true}" style="width: 140px">
									<form:option value=""></form:option>
									<form:options items="${devFacturerList}" itemLabel="devFacturerName" itemValue="devFacturerId"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								<font color="red">*&nbsp;</font>部门
							</td>
							<td>
								<input type="hidden" name="sysCorp.corpId" id="corpId"/>
								<input type="text" name="corpName" id="corpName" value="" class="form_input {required:true}" readonly="readonly" style="cursor: pointer;"/>
								<div id="corpChooseDiv" style="position:absolute; border:solid 1px #CCCCCC; width:250px; height:200px; top:23px; left:0px; background:#FFFFFF;display: none;z-index:99;overflow:auto">
							         <ul id="treeDemo" class="ztree" style="width: 180px;">
							         </ul>
						        </div>
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								使用人
							</td>
							<td>
								<input type="hidden" name="devUserInfo.loginId" id="userId" />
								<input type="text" class="form_input"  name="userName" id="userName" style="cursor: pointer;"  onclick="showUserSelectPage('使用人选择','userId','userName')"/>
							</td>
						</tr>
						<tr>
							<td align="center" valign="middle" colspan="2">
								<input type="submit" value="提交" class="blue_mod_btn">
								<input type="button" value="取消" class="blue_mod_btn" onclick="parent.closeModalWindow();">
							</td>
						</tr>
					</table>
				</div>
			</div>
	</form:form>
	<a id="modalWindowAction" class="nyroModal" href="#" target="_blank" style="display: none" title="使用人选择">使用人选择</a>
	<script>
	
	$(function(){
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
		
		$("#corpName").powerFloat({
			eventType: "click",
			target: $("#corpChooseDiv")	
		});
	
		$('.nyroModal').nyroModal();
		
		$('#addForm').validate();
		
		$('#addForm').ajaxForm({
			 dataType:  'json',
		     success:   onSuccess
		});
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
	
	function onSuccess(data) {
	    if(data.messageType=='1')
	    {
	    	alert(data.promptInfo);
	    	parent.closeModalWindow(true);
	    }
	    else
	    {
	    	alert(data.promptInfo);
	    }
	}
	
	//
	function showUserSelectPage(title,userId,userName)
	{
		//只能选择已选择单位下的人员
		var corpId=$("#corpId").val();
		var corpName=$("#corpName").val();
		if(undefined ==corpId || corpId==""){
			alert("请先选择设备的划归单位,于进行使用人的选择");
			return ;
		}
		//增加了fixedCorpId参数，用来说明在选择人员时，不可以修改单位，也就是说只能选择某单位下的人员。   EditBy 孙强伟
		var url = '${ctx}/userMgr/userSelect?fixedCorpId=true&filter_EQ_sysCorp.corpId='+corpId+'&corpName='+encodeURI(encodeURI(corpName))+'&userId='+userId+'&userName='+userName+'&r='+Math.random();
		$('#modalWindowAction').attr("href",url);
	  	$('#modalWindowAction').attr("title",title);
	  	$('#modalWindowAction').click();
	}
</script>
</body>
</html>