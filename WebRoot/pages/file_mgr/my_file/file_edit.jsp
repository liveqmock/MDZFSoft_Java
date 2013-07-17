<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/calendar.jsp"%>
<%@ include file="/plugins/jquery-validation.jsp"%>
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
<%@ include file="/plugins/jquery-powerFloat.jsp" %>
</head>
<body>
<form:form id="editForm" name="editForm" method="POST" action="${ctx}/fileMgr/update" modelAttribute="resultObject">
<form:hidden path="fileId"/>
<div class="gray_bor_bg">
	<div class="table_div">
		<table width="100%" class="table_border">
			<tr>
				<td class="title" width="15%">文件名</td>
				<td width="35%">${resultObject.fileUploadName}</td>
				<td class="title" width="15%">文件类型</td>
				<td width="35%">${resultObject.fileTypeDesc}</td>
			</tr>
			<tr>
				<td class="title" >上传人</td>
				<td>${resultObject.uploadUserInfo.userName}</td>
				<td class="title" >上传时间</td>
				<td>
					<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedFileUploadTime" parseLocale="en_US">
						${resultObject.fileUploadTime}
					</fmt:parseDate>
					<fmt:formatDate value='${parsedFileUploadTime}' pattern="yy-MM-dd HH:mm"/>
				</td>
			</tr>
			<tr>
				<td class="title" >采集人</td>
				<td>
				<input type="text" class="input_79x19"  
				name="fileEditUserName" id="fileEditUserName" value="${resultObject.editUserInfo.userName}" 
				style="cursor: pointer;"  onclick="showUserSelectPage('采集人选择','fileEditId','fileEditUserName')"/>
				<input type="hidden" name="editUserInfo.loginId" id="fileEditId" value="${resultObject.editUserInfo.loginId }";
				</td>
				<td class="title" >创建时间</td>
				<td>
					<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedFileCreateTime" parseLocale="en_US">
						${resultObject.fileCreateTime}
					</fmt:parseDate>
					<fmt:formatDate value='${parsedFileCreateTime}' pattern="yy-MM-dd HH:mm"/>
				</td>
			</tr>
			<tr>
				<td class="title" >文件状态</td>
				<td colspan="3">
					${resultObject.fileStatusDesc}
				</td>
			</tr>

			<tr>
				<td class="title" >文件分类</td>
				<td>
					<form:select path="fileTypeInfo.typeId" cssClass="form_input" style="width: 140px">
						<form:options items="${fileTypeList}" itemLabel="typeName" itemValue="typeId"/>
					</form:select>
				</td>
				<td class="title" >接警编号</td>
				<td>
					<form:input path="policeCode" style="width:180px" cssClass="input_79x19 form_input {maxlength:20}"/>
				</td>
			</tr>
			<tr>

				<td class="title" >简要警情</td>
				<td colspan="3">
					<form:textarea path="policeDesc" cols="71" rows="3" cssClass="form_input {maxlength:200}"/>
				</td>
			</tr>
			<tr>
				<td class="title" >接警时间</td>
				<td>
					<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedPoliceTime" parseLocale="en_US">${resultObject.policeTime}</fmt:parseDate>
					<fmt:formatDate value='${parsedPoliceTime}' pattern="yyyy-MM-dd HH:mm" var="policeTime"/>
					<input name="_policeTime" id="_policeTime" type="text" class="input_79x19 form_input"  style="cursor: pointer;" value="${policeTime}" /> 
					<div style="display: none">
						<textarea rows="1" cols="12" name="policeTime" id="policeTime">${resultObject.policeTime}</textarea>
					</div>
					
				</td>
				<td class="title" >录制时间</td>
				<td>
					<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedFileRecordTime" parseLocale="en_US">${resultObject.fileRecordTime}</fmt:parseDate>
					<fmt:formatDate value='${parsedFileRecordTime}' pattern="yyyy-MM-dd HH:mm" var="fileRecordTime"/>
					<input name="_fileRecordTime" id="_fileRecordTime" type="text" class="input_79x19 form_input"  style="cursor: pointer;" value="${fileRecordTime}" /> 
					<div style="display: none">
						<textarea rows="1" cols="12" name="fileRecordTime" id="fileRecordTime">${resultObject.fileRecordTime}</textarea>
					</div>
				</td>
			</tr>
			<tr>
				<td class="title" >文件备注</td>
				<td colspan="3">
					<form:textarea path="fileRemark" cols="71" rows="3" cssClass="form_input {maxlength:200}"/>
				</td>
			</tr>
			<tr>
				<td align="center" valign="middle" colspan="4">
					<input type="submit" value="提交" class="blue_mod_btn">
					<input type="button" value="取消" class="blue_mod_btn" onclick="parent.closeModalWindow();">
				</td>
			</tr>
		</table>
	</div>
</div>
</form:form>
<a id="modalWindowAction" class="nyroModal" href="#" target="_blank" style="display: none" title="采集人选择">采集人选择</a>

<script>
	Calendar.setup(
	{
	     inputField  : "_policeTime",         // ID of the input field
	     displayArea : "policeTime",
	     ifFormat    : "%Y-%m-%d %H:%M",    // the date format
	     daFormat    : "%Y%m%d%H%M00",
	     showsTime   : true,
	     timeFormat  : 24,
	     button      : "_policeTime"       // ID of the button
	});
	Calendar.setup(
	{
	     inputField  : "_fileRecordTime",         // ID of the input field
	     displayArea : "fileRecordTime",
	     ifFormat    : "%Y-%m-%d %H:%M",    // the date format
	     daFormat    : "%Y%m%d%H%M00",
	     showsTime   : true,
	     timeFormat  : 24,
	     button      : "_fileRecordTime"       // ID of the button
	});
	$(function(){
		$('#editForm').validate();
		
		$('#editForm').ajaxForm({
			 dataType:  'json',
		     success:   onSuccess
		});
		
		$('.nyroModal').nyroModal({
			callbacks:{
			close:function(){
				if(document.getElementById('audioPlayerObj')!=null)
				{
					audioPlayerObj.controls.stop();
				}
			}
		}});
	});

	function onSuccess(data) {
	    if(data.messageType=='1')
	    {
	    	alert(data.promptInfo);
	    	parent.closeModalWindow();
	    }
	    else
	    {
	    	alert(data.promptInfo);
	    }
	}
	
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