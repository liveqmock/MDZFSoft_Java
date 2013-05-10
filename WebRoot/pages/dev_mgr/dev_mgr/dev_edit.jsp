<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
<%@ include file="/plugins/jquery-validation.jsp"%>
</head>
<body>
	<form:form id="editForm" name="editForm" method="POST" action="${ctx}/devMgr/update" modelAttribute="resultObject">
		<form:hidden path="devId"/>
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
								设备类型
							</td>
							<td>
								<form:select path="devTypeInfo.devTypeId" cssClass="form_input" style="width: 140px">
									<form:option value=""></form:option>
									<form:options items="${devTypeList}" itemLabel="devTypeName" itemValue="devTypeId"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								设备厂商
							</td>
							<td>
								<form:select path="devFacturerInfo.devFacturerId" cssClass="form_input" style="width: 140px">
									<form:option value=""></form:option>
									<form:options items="${devFacturerList}" itemLabel="devFacturerName" itemValue="devFacturerId"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="title" width="100">
								使用人
							</td>
							<td>
								<input type="hidden" name="devUserInfo.loginId" id="userId" value="${resultObject.devUserInfo.loginId}" />
								<input type="text" class="input_79x19 form_input"  name="userName" id="userName" value="${resultObject.devUserInfo.userName}" style="cursor: pointer;"  onclick="showUserSelectPage('使用人选择','userId','userName')"/>
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
		$('.nyroModal').nyroModal();
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
	
	//
	function showUserSelectPage(title,userId,userName)
	{
		var url = '${ctx}/userMgr/userSelect?userId='+userId+'&userName='+userName+'&r='+Math.random();
	  	$('#modalWindowAction').attr("href",url);
	  	$('#modalWindowAction').attr("title",title);
	  	$('#modalWindowAction').click();
	}
</script>
</body>
</html>