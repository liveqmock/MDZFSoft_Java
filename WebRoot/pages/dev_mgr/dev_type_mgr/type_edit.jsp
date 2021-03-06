<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
<%@ include file="/plugins/jquery-powerFloat.jsp"%>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body>
	<form:form id="editForm" name="editForm" method="POST" action="${ctx}/devTypeMgr/update" modelAttribute="resultObject">
		<form:hidden path="devTypeId" />
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
							<font color="red">*&nbsp;</font>设备类型
						</td>
						<td>
							<form:input path="devTypeName" cssClass="form_input {required:true,maxlength:20}" size="20" />
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
	<script>
	
	$(function(){
		$('#editForm').validate();
		
		$('#editForm').ajaxForm({
			 dataType:  'json',
		     success:   onSuccess
		});
	});
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
</script>
</body>
</html>