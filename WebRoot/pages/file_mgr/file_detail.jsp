<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
</head>
<body>
<div class="gray_bor_bg">
	<div class="table_div">
		<table width="100%" class="table_border">
			<tr>
				<td class="title" width="100">文件名</td>
				<td width="400">${resultObject.fileUploadName}</td>
			</tr>
			<tr>
				<td class="title" width="100">上传人</td>
				<td>${resultObject.uploadUserInfo.userName}</td>
			</tr>
			<tr>
				<td class="title" width="100">上传时间</td>
				<td>
					<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedFileUploadTime" parseLocale="en_US">
						${resultObject.fileUploadTime}
					</fmt:parseDate>
					<fmt:formatDate value='${parsedFileUploadTime}' pattern="yy-MM-dd HH:mm"/>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">采集人</td>
				<td>${resultObject.editUserInfo.userName}</td>
			</tr>
			<tr>
				<td class="title" width="100">文件备注</td>
				<td>${resultObject.fileRemark}</td>
			</tr>
			<tr>
				<td class="title" width="100">接警编号</td>
				<td>${resultObject.policeCode}</td>
			</tr>
			<tr>
				<td class="title" width="100">简要警情</td>
				<td>${resultObject.policeDesc}</td>
			</tr>
			<tr>
				<td class="title" width="100">接警时间</td>
				<td>
					<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedPoliceTime" parseLocale="en_US">
						${resultObject.policeTime}
					</fmt:parseDate>
					<fmt:formatDate value='${parsedPoliceTime}' pattern="yy-MM-dd HH:mm"/>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">录制时间</td>
				<td>
					<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedRecordTime" parseLocale="en_US">
						${resultObject.fileRecordTime}
					</fmt:parseDate>
					<fmt:formatDate value='${parsedRecordTime}' pattern="yy-MM-dd HH:mm"/>
				</td>
			</tr>
			<tr>
				<td class="title" width="100">达到时间</td>
				<td>${resultObject.policeCostTime}分钟</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>