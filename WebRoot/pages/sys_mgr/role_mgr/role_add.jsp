<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/plugins/jquery-validation.jsp"%>
		<%@ include file="/plugins/ztree.jsp"%>
	</head>
	<body >
		<div class="mianbaoxie">
			当前位置：<a href="#1">系统管理</a> > <a href="${ctx}/roleMgr">角色管理</a>  > 新增
		</div>
		<form:form id="mainForm" name="mainForm" method="POST" action="${ctx}/roleMgr/save"  modelAttribute="resultObject">
		<input type="hidden" name="status" id="status" value="1">
		<div class="tab_search" style="margin-top: 20px;">
		<div class="table_div">
			<table width="100%" class="table_border">
				<tr>
					<td class="title" width="100"></td>
					<td align="left" colspan="4">带<font color="red">&nbsp;*&nbsp;</font>为必填项</td>
				</tr>
				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>角色名称
					</td>
					<td colspan="3">
						<form:input path="roleName" cssClass="form_input {required:true,maxlength:20}" size="20"/>
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						角色描述
					</td>
					<td colspan="3">
						<form:textarea path="roleDesc" cols="70" rows="4" cssClass="form_input {maxlength:70}"/>
					</td>
				</tr>
				<tr>
					<td class="title"><font color="red">*</font>菜单权限：</td>
					<td width="400px">
					<form:hidden path="permissionIds"/> 
					<div >
						<ul id="treeDemo" class="ztree"></ul>
					</div>
					</td>
					<td colspan="2" valign="bottom">
						<label id="treeError" style="color: red;font-style: italic"></label>
					</td>
				</tr>
				<tr>
					<td class="title"><font color="red">*</font>房间权限：</td>
					<td width="400px">
					<form:hidden path="roomIds"/>
					<div >
						<ul id="treeRoom" class="ztree"></ul>
					</div>
					</td>
					<td colspan="2" valign="bottom">
						<label id="tree2Error" style="color: red;font-style: italic"></label>
					</td>
				</tr>
				<tr>
					<td align="center" valign="middle" colspan="4">
						<input type="submit" class="form_botton" value="提  交"/>&nbsp;&nbsp;
						<input type="button" class="form_botton" value="返  回" onclick="window.location='${ctx}/roleMgr'" />
					</td>					
				</tr>
			</table>	
			</div>
			</div>
		</form:form>
	</body>
	<script>
	$(function(){
		$('#mainForm').validate({
			 submitHandler: function(form) {
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
					var nodes = treeObj.getCheckedNodes(true);  
					var checkIds = "";  
					if (nodes.length<=0) {
						$('#treeError').html('请选择菜单');
						$('#treeDemo').attr("style","border: 1px dotted red;");
						return false;
					}
					for (var i = 0; i < nodes.length; i++) {  
						if(nodes[i].isParent==false){ 
						    if (checkIds != '')
						    	checkIds += ',';  
						    checkIds += nodes[i].id;  
						}
					}
					$("#permissionIds").val(checkIds);

					var treeObj2 = $.fn.zTree.getZTreeObj("treeRoom");  
					var nodes2 = treeObj2.getCheckedNodes(true);  
					var checkIds2 = "";  
					if (nodes2.length<=0) {
						$('#tree2Error').html('请选择房间');
						$('#treeRoom').attr("style","border: 1px dotted red;");
						return false;
					}
					for (var i = 0; i < nodes2.length; i++) {
						if(nodes2[i].isParent==false){
<%--							var str = nodes2[i].id+":"+$("#diyBtn_"+nodes2[i].id).val();--%>
							var str = nodes2[i].id+":"+1;
						    if (checkIds2 != ''){
						    	checkIds2 += (','+str);  
							}else{
							    checkIds2 += str;  
							}
						}
					}
					$("#roomIds").val(checkIds2);
				 	form.submit();
			 }
		});
	});
	
	var IDMark_Switch = "_switch",
	IDMark_Icon = "_ico",
	IDMark_Span = "_span",
	IDMark_Input = "_input",
	IDMark_Check = "_check",
	IDMark_Edit = "_edit",
	IDMark_Remove = "_remove",
	IDMark_Ul = "_ul",
	IDMark_A = "_a";
	
	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: treeOnCheck
			}
	};

	var settingRoom = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: treeOnCheck2
			}
	};
	var zNodes = ${tree.json};  
	var zNodesRoom = ${tree2.json};  
	
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		$.fn.zTree.init($("#treeRoom"), settingRoom, zNodesRoom);
	});
	$(function(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var treeObj2 = $.fn.zTree.getZTreeObj("treeRoom");
		var nodes = treeObj.getCheckedNodes(true);  
		var nodes = treeObj2.getCheckedNodes(true);  
	});

	function treeOnCheck(){
	}

	function treeOnCheck2(e, treeId, treeNode) {
		if(treeNode.checked){
			var aObj = $("#" + treeNode.tId+IDMark_A);
			if ($("#diyBtn_"+treeNode.tId).length>0) return;
			var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" +treeNode.id+ "'><option value=1>可控制</option><option value=0 selected>不可控制</option></select>";
<%--			aObj.after(editStr);--%>
		}else{
			$("#diyBtn_"+treeNode.id).remove();
		}
		
	}
	</script>
</html>