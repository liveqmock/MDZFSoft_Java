<%@page import="com.njmd.zfms.web.constants.ConfigConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/jquery-form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/date.js"></script>
<!-- USB to Local -->
<form id="uploadForm" method="post" action="${ctx }/fileMgr/save">
<input type="hidden" id="fileEditId" name="editUserInfo.loginId"/>
<input type="hidden" id="fileCreateTime" name="fileCreateTime"/>
<input type="hidden" id="fileSavePath" name="fileSavePath"/>
<input type="hidden" id="fileUploadName" name="fileUploadName"/>
<c:if test="${param['uploadType']=='1'}">
	<div style="width:500px">
		<ul class="mt_10">
			<label>USB设&nbsp;备：</label>
			<c:forTokens items="${param['usbDrives']}" delims="*" var="usbDrive" varStatus="status">
				<c:if test="${status.index==0 }">
					<input type="radio" name="usbDrive" value="${usbDrive}" checked="checked">(${usbDrive}:)盘
				</c:if>
				<c:if test="${status.index!=0 }">
					<input type="radio" name="usbDrive" value="${usbDrive}">(${usbDrive}:)盘
				</c:if>
			</c:forTokens>
		</ul>
		<ul class="mt_10">
			<label>采&nbsp;集&nbsp;人：</label>
			<input type="text" id="editName" name="editName" value="" class="input_79x19" readonly onclick="showUserSelectPage()" style="cursor: pointer;" />
		</ul>
		<ul class="mt_10">
			<a class="blue_mod_btn" href="#" onclick="startUpload(1)">开始上传</a>
			<a href="${ctx}/pages/file_mgr/file_upload/index.jsp" class="green_mod_btn">重新选择</a>
		</ul>
	</div>
</c:if>
<!-- USB to Server -->
<c:if test="${param['uploadType']=='2'}">
	<div style="width:500px">
		<ul class="mt_10">
			<label>U&nbsp;S&nbsp;B&nbsp;设&nbsp;&nbsp;&nbsp;&nbsp;备：</label>
			<c:forTokens items="${param['usbDrives']}" delims="*" var="usbDrive" varStatus="status">
				<c:if test="${status.index==0 }">
					<input type="radio" name="usbDrive" value="${usbDrive}" checked="checked">(${usbDrive}:)盘
				</c:if>
				<c:if test="${status.index!=0 }">
					<input type="radio" name="usbDrive" value="${usbDrive}">(${usbDrive}:)盘
				</c:if>
			</c:forTokens>
		</ul>
		<ul class="mt_10">
			<label>本地保存文件夹：</label>
			<input type="text" id="localDir" readonly onclick="showSelectLocalDir()" class="input_79x19"/>
		</ul>
		<ul class="mt_10">
			<a class="blue_mod_btn" href="#" onclick="usbToLocalUpload()">开始上传</a>
			<a href="${ctx}/pages/file_mgr/file_upload/index.jsp" class="green_mod_btn">重新选择</a>
		</ul>
	</div>
</c:if>
<!-- Local to Server -->
<c:if test="${param['uploadType']=='3'}">
	<div style="width:500px">
		<ul class="mt_10">
			<label>本地文件夹：</label>
			<input type="text" id="localDir" readonly onclick="showSelectLocalDir()" class="input_79x19" />
		</ul>
		<ul class="mt_10">
			<label>采&nbsp;&nbsp;集&nbsp;&nbsp;人：</label>
			<input type="text" id="editName" name="editName" value="" class="input_79x19" readonly onclick="showUserSelectPage()" style="cursor: pointer;" />
		</ul>
		<ul class="mt_10">
			<a class="blue_mod_btn" href="#" onclick="startUpload(3)">开始上传</a>
			<a href="${ctx}/pages/file_mgr/file_upload/index.jsp" class="green_mod_btn">重新选择</a>
		</ul>
	</div>
</c:if>
<input type="submit" id="submitBtn" style="display: none">
</form>
<iframe id="uploadProgressFrame" src="" width="400px" height="300px" frameborder="0"></iframe>

<script type="text/javascript">
	var ftpHost = "${sessionScope.loginToken.sysCorp.ftpIp}";
	var ftpPort = "${sessionScope.loginToken.sysCorp.ftpPort}";
	var ftpUser = "${sessionScope.loginToken.sysCorp.ftpUser}";
	var ftpPswd = "${sessionScope.loginToken.sysCorp.ftpPwd}";
	var supportFileExtension = "<%=ConfigConstants.SUPPORT_FILE_EXTENSION%>";//支持上传文件格式
	var totalFileNum=0;//上传文件总数
	var uploadedFileNum=0;//已上传文件总数
	var currentUploadFileUrl="";//当前上传文件全路径
	var currentUploadFileName="";//当前上传文件名
	var sourcePath;//上传源盘符或路径
	var targetPath;//上传目标路径
	var targetSaveFileName;//上传目标保存文件名称

	var loginStatus=false;
	//登陆FTP
	if(ftpLogin(ftpHost,ftpUser,ftpPswd,ftpPort))
	{
		//创建目录
		targetPath = '${sessionScope.loginToken.sysCorp.corpId}/${sessionScope.loginToken.sysLogin.loginId}/'+(new Date()).format('yyyy-MM-dd');
		ftpCreateRemoteDir(targetPath);
		loginStatus =true;
	}
	
	//本地USB设备->服务器 or 本地文件夹->服务器
	function startUpload(type)
	{
		targetPath = '${sessionScope.loginToken.sysCorp.corpId}/${sessionScope.loginToken.sysLogin.loginId}/'+(new Date()).format('yyyy-MM-dd');
		//1-表单验证
		if(type==1)
		{
			sourcePath=$('input:radio[name="usbDrive"]:checked').val();
			if(sourcePath==null){
	
	            showMsg(1,"请选择一个USB设备 ");
	            return false;
	        }
			if($('#editName').val()=='')
			{

				showMsg(1,"请选择采集人 ");
	            return false;
			}
			//2-USB设备验证
			totalFileNum = getLocalDirFilesNum(sourcePath);
			if(totalFileNum==0)
			{
				showMsg(1,"您选择的USB设备下没有可上传文件，请确认！ ");
	            return false;
			}
		}
		else
		{
			sourcePath=$('#localDir').val();
			if(sourcePath==""){
	
	            showMsg(1,"请选择一个本地文件夹");
	            return false;
	        }
			if($('#editName').val()=='')
			{
				showMsg(1,"请选择采集人 ");
	            return false;
			}
			//2-USB设备验证
			totalFileNum = getLocalDirFilesNum(sourcePath);
			if(totalFileNum==0)
			{
				showMsg(1,"您选择的文件夹下没有可上传文件，请确认！ ");
	            return false;
			}
		}
		

		//3-服务器磁盘空间验证
		$.getJSON("${ctx}/fileMgr/checkDisk?r="+Math.random(), function(data){
			if(data=='1')
		    {
				if(loginStatus==true)
				{
					uploadedFileNum=0;
					uploadFile();
				}
				else
					showMsg(1,"ftp服务器登录失败，请与管理员联系!");
		    }
		    else
		    {
		    	showMsg(1,"服务器磁盘空间将用尽，上传功能暂时被停止，请联系管理员！ ");
		    	return false;
		    }
		});
	}
	//上传文件至服务器
	function uploadFile()
	{
		currentUploadFileUrl = getLocalFirstFile(sourcePath);
		if(currentUploadFileUrl!="")
		{
			currentUploadFileName = currentUploadFileUrl.substring(currentUploadFileUrl.lastIndexOf("\\")+1);
			var extension = "";//扩展名
			var supportFileExtensions = supportFileExtension.split(",");
			for(var i=0;i<supportFileExtensions.length;i++)
			{
			  	if(currentUploadFileUrl.toLowerCase().lastIndexOf(supportFileExtensions[i])>0) {
			  		extension = supportFileExtensions[i];
			  	} 
			}
			var fileName = new Date().format("yyyy-MM-dd").replaceAll("-","")+"_"+new Date().format("HH:mm:ss").replaceAll(":","")+"_"+new Date().format("ll");
			targetSaveFileName = fileName+"."+extension;
			ftpUploadFile(currentUploadFileUrl, targetSaveFileName);
			$('#uploadProgressFrame').attr('src', "${ctx}/pages/file_mgr/file_upload/file_upload_progress.jsp?totalFileNum="+totalFileNum+"&currentUploadFileName="+encodeURI(encodeURI(currentUploadFileName))+"&uploadedFileNum="+(uploadedFileNum+1)+"&random="+Math.random());
		}
		else
		{
			showMsg(0,"上传成功！");
			$('#uploadProgressFrame').attr('src',"");
		}	  
	}
	//本地USB设备->服务器
	function usbToLocalUpload()
	{
		//1-表单验证
		sourcePath=$('input:radio[name="usbDrive"]:checked').val();
		if(sourcePath==null){

            showMsg(1,"请选择一个USB设备 ");
            return false;
        }
		if($('#localDir').val()=='')
		{
			showMsg(1,"请选择本地保存文件夹 ");
            return false;
		}
		//2-USB设备验证
		totalFileNum = getLocalDirFilesNum(sourcePath);
		if(totalFileNum==0)
		{
			showMsg(1,"您选择的USB设备下没有可上传文件，请确认！ ");
            return false;
		}
		
		if(loginStatus==true)
		{
			targetPath = $('#localDir').val();
			uploadedFileNum=0;
			copyUsbToLocal();
		}
		else
			showMsg(1,"ftp服务器登录失败，请与管理员联系!");
		   
	}
	
	//拷贝文件至本地
	function copyUsbToLocal()
	{
		currentUploadFileUrl = getLocalFirstFile(sourcePath);
		if(currentUploadFileUrl!="")
		{
			currentUploadFileName = currentUploadFileUrl.substring(currentUploadFileUrl.lastIndexOf("\\")+1);
			targetSaveFileName = currentUploadFileName;
			copyLocalFile(currentUploadFileUrl,targetPath+"\\"+targetSaveFileName);
			$('#uploadProgressFrame').attr('src', "${ctx}/pages/file_mgr/file_upload/file_upload_info.jsp?info="+encodeURI(encodeURI('文件拷贝中....'))+"&random="+Math.random());
			delLocalFile(currentUploadFileUrl);
			uploadedFileNum++;
			window.setTimeout(copyUsbToLocal,500);
		}
		else
		{
			showMsg(0,"上传成功！");
			$('#uploadProgressFrame').attr('src',"");
		}	  	
	}
	//单个文件上传成功后的回调函数
	function uploadSuccess()
	{
		$("#fileUploadName").val(currentUploadFileName);
  		$("#fileCreateTime").val(getLocalFileCreateTime(currentUploadFileUrl));
	  	$("#fileSavePath").val(targetPath +'/'+ targetSaveFileName);
	  	$('#uploadForm').ajaxSubmit(function(data){
			if(data.messageType=='1')
		    {
		    	<%//editby 孙强伟  at 20130624,修改当本地磁盘文件删除失败时，上传操作停止。 %>
				var isSuccess=delLocalFile(currentUploadFileUrl);
				if(!isSuccess){
					alert("本地磁盘文件删除失败,上传操作停止!");
				}else{
					uploadedFileNum++;
					window.setTimeout(uploadFile,500);
				}
		    }
		    else
		    {
		    	//alert(data.promptInfo);
		    }
       });
	}
	//本地文件夹选择
	function showSelectLocalDir()
	{
		var localDir = selectLocalSaveDir();
		$('#localDir').val(localDir);
	}
	
</script>