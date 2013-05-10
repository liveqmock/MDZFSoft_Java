<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/jquery-progressbar/js/jquery.progressbar.js"></script>
</head>
<body style="background: white;">
<%
//处理中文文件名问题
String currentUploadFileName=request.getParameter("currentUploadFileName");
currentUploadFileName = java.net.URLDecoder.decode(currentUploadFileName,"UTF-8");  
%>
	<div class="mt_10" style="width:320px ;border: 1px solid #6cd858;padding: 8px;background-color: rgb(227, 255, 222)">
		<ul style="margin-top: 5px">
			<b>文件名称：</b><label id="currentFileLabel"><%=currentUploadFileName %></label>
		</ul>
		<ul style="margin-top: 5px">
			<b>总体进度：</b><label id="uploadedFileNumLabel">${param['uploadedFileNum']}</label>/<label id="totalFileNumLabel">${param['totalFileNum']}</label>
		</ul>
		<ul style="margin-top: 5px">
			<b>当前进度：</b><span class="progressBar" id="uploadProgressBar">0%</span>
		</ul>
		<ul style="margin-top: 5px">
			<b>当前速度：</b><label id="speedLabel">0</label> kb/秒
		</ul>
	</div>
</body>
<script type="text/javascript">
	var interval= window.setInterval('refreshProgress()', 500);
	//进度刷新
	function refreshProgress()
	{
		var uploadFilePercent = parent.ftpGetUploadFilePercent();
		var speed = parent.ftpGetUploadSpeed();
		$("#uploadProgressBar").progressBar(uploadFilePercent,{barImage: {
			0:	'${ctx}/plugins/jquery-progressbar/images/progressbg_red.gif',
			30: '${ctx}/plugins/jquery-progressbar/images/progressbg_orange.gif',
			70: '${ctx}/plugins/jquery-progressbar/images/progressbg_green.gif'
		}});
		$('#speedLabel').html(speed);
		if(uploadFilePercent == "100")
	    {
			window.clearInterval(interval);
			parent.uploadSuccess();
	    }
	}
</script>
</html>