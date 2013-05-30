<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body>
	<form:form id="addForm" name="addForm" method="POST" action="${ctx}/noticeMgr/save" modelAttribute="resultObject">
		<input type="hidden" name="status" id="status" value="1"/>
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
								<font color="red">*&nbsp;</font>公告标题
							</td>
							<td>
								<form:input path="noticeTitle" cssClass="form_input {required:true,maxlength:50}" size="30" />
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								公告内容
							</td>
							<td>
								<form:textarea path="noticeContent" cols="80" rows="9" cssClass="form_input {maxlength:500}"/>
							</td>
						</tr>
						<tr>
							<td class="title"><font color="red">*</font>发布对象：</td>
							<td>
								<form:hidden path="targetIds"/> 
								<div id="treeDemo" class="ztree" style="height: 200px;width: 400px;overflow: auto;">
								</div>
								<label id="treeError" style="color: red;font-style: italic"></label>
							</td>
						</tr>
						<tr>
							<td align="center" valign="middle" colspan="2">
								<input type="submit" id="submitBtn" value="提交" class="blue_mod_btn">
								<input type="button" value="取消" class="blue_mod_btn" onclick="parent.closeModalWindow();">
							</td>
						</tr>
					</table>
				</div>
			</div>
	</form:form>
	<script>
	
	$(function(){
		//树形菜单
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {enable: true}
			}
		};
		var zNodes = ${tree.json};  
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		
		$('#addForm').validate({
			 submitHandler: function(form) {
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
					var nodes = treeObj.getCheckedNodes(true);  
					var checkIds = "";  
					if (nodes.length<=0) {
						$('#treeError').html('请至少选择一个公告发布对象');
						$('#treeDemo').attr("style","height: 200px;width: 400px;overflow: auto;border: 1px dotted red;");
						return false;
					}
					for (var i = 0; i < nodes.length; i++) {  
						//if(nodes[i].isParent==false){ 
						    //if (checkIds != '')
						    	//checkIds += ',';  
						    checkIds += nodes[i].id+",";  
						//}
					}
					$("#targetIds").val(checkIds);
					$('#addForm').ajaxSubmit(function(data){
						if(data.messageType=='1')
					    {
					    	alert(data.promptInfo);
					    	parent.closeModalWindow(true);
					    }
					    else
					    {
					    	alert(data.promptInfo);
					    }
			       });
			 }
		});
	});
</script>
</body>
</html>