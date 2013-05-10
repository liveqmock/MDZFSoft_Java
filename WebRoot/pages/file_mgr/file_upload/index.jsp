<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
</head>
<body>
	<%@ include file="/pages/top.jsp"%>
	<object id="MDOCX" classid="clsid:8B2CE00D-FEE7-4B4A-B6A4-BDCF5A0BA624"  width=0 height=0></object>
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
				<h4 class="content_hd long_content_hd">文件上传</h4>
				<div class="content_bd">
					<div class="error msg" id="infoMsg" style="display: none" onclick="hideObj('infoMsg')">Message if login failed</div>
					<TABLE>
						<TR>
							<TD valign="top">
								<div class="my_uplist">
									<ul class="my_upitem clearfix" id="uploadType1">
										<div class="up_help_img ">
											<img src="${ctx}/images/up_step1.png" alt="选择进入" style="cursor: pointer;" onclick="javascript:showUploadTypeDetail('1')">
										</div>
									</ul>
									<ul class="my_upitem clearfix" id="uploadType2">
										<div class="up_help_img ">
											<img src="${ctx}/images/up_step2.png" alt="选择进入" style="cursor: pointer;" onclick="javascript:showUploadTypeDetail('2')">
										</div>
									</ul>
									<ul class="my_upitem clearfix" id="uploadType3">
										<div class="up_help_img ">
											<img src="${ctx}/images/up_step3.png" alt="选择进入" style="cursor: pointer;" onclick="javascript:showUploadTypeDetail('3')">
										</div>
									</ul>
								</div>
							</TD>
							<TD valign="top">
								<div id="fileUploadDiv" style="display: none;padding-left: 30px">
								</div>
							</TD>
						</TR>
					</TABLE>
				</div>
			</div>
		</div>
	</div>
<a id="modalWindowAction" class="nyroModal" href="#" target="_blank" style="display: none" title="采集人选择">采集人选择</a>
<%@ include file="/pages/footer.jsp"%>
<script type="text/javascript" src="${ctx }/pages/file_mgr/file_upload/file_upload.js"></script>
<script>
var isPluginOk = false;
$(document).ready(function()
{
	//插件检查
	isPluginOk = checkPlugin();
	if(isPluginOk)
		showMsg(3	,"请您选择需要上传的方式");
	$('.nyroModal').nyroModal();
});

//根据上传方式显示相应的上传详细页面
function showUploadTypeDetail(uploadType)
{
	if(isPluginOk)
	{
		$('#uploadNameValue1').val("");
		hideObj("infoMsg");
		var loadUrl = "${ctx}/pages/file_mgr/file_upload/file_upload.jsp?uploadType="+uploadType;
		if(uploadType!=3)
		{
			var usbDrives = getUsbDriver();
			if(usbDrives=='')
			{
				showMsg(2,"您还没有插入USB设备，请确认~");
				return;
			}
			else
				loadUrl+="&usbDrives="+usbDrives;
		}
		//uploadTable('${sessionScope.loginToken.sysCorp.corpId},${sessionScope.loginToken.sysLogin.loginId}');
		loadPage("fileUploadDiv", loadUrl,null);
		showObj("fileUploadDiv");
		for(var i=1;i<4;i++)
		{
			if(i!=uploadType)
				hideObj("uploadType"+i);
		}
	}
	else
		checkPlugin();
}

/**
 * 弹出用户选择窗口
 */
function showUserSelectPage()
{
	var url = '${ctx}/userMgr/userSelect?userId=fileEditId&userName=editName&r='+Math.random();
  	$('#modalWindowAction').attr("href",url);
  	$('#modalWindowAction').attr("title","采集人选择");
  	$('#modalWindowAction').click();
}


</script>
</body>
</html>
