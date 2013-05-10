var pluginFileName = "md_1.0.0.5.CAB";//安装插件的链接地址
var nowVersion = "1.0.0.3";//当前使用的版本号
var SUCCESS='1';
/**
 * 提示信息
 * @param msg 提示信息
 * @param type 提示类型 0-success 1-error 2-warning 3-information
 */
function showMsg(type,msg)
{
	$("#infoMsg").html(msg);
	if(type=='0')
		$("#infoMsg").attr("class", "success msg");
	else if(type=='1')
		$("#infoMsg").attr("class", "error msg");
	else if(type=='2')
		$("#infoMsg").attr("class", "warning msg");
	else if(type=='3')
		$("#infoMsg").attr("class", "information msg");
	
	$("#infoMsg").css("opacity" ,4);
	showObj("infoMsg");
}


/**
 * 检查插件状态及版本
 * 如果没有安装过插件，则提示安装插件
 */
function checkPlugin(){
  var isSuccess = false;
  try
  {
    var version = MDOCX.getVersion();
	if(parseInt(nowVersion.split(".")[3])>parseInt(version.split(".")[3]))
		showMsg(1,"您当前插件版本过低，请重新<a href='#' onclick=\"javascript:downloadFile('"+pluginFileName+"')\" style='color:blue'>下载</a>并安装插件!");
	else
		isSuccess = true;
  }
  catch(ex){
    showMsg(1,"您还没有安装上传插件，请先<a href='#' onclick=\"javascript:downloadFile('"+pluginFileName+"')\" style='color:blue'>下载</a>并安装插件!");
  }
  return isSuccess;
}

/**
 * ftp登录
 */
function ftpLogin(ftpHost, ftpUser, ftpPswd, ftpPort){
  var isSuccess=false;
  try{
	  	MDOCX.setParm(ftpHost, ftpUser, ftpPswd, ftpPort);
		if(MDOCX.ftpLogin()!=SUCCESS)
		   showMsg(1,"ftp服务器登录失败，请与管理员联系!");
		else
			isSuccess = true;
  }
  catch(ex){
    showMsg(1,"ftp服务器登录异常，"+ex.description);
  }
  return isSuccess;
}

/**
 * ftp登出
 */
function ftpLogout(){
  var isSuccess=false;
  try{
    if(MDOCX.ftpLogout()!=SUCCESS)
    	showMsg(1,"ftp服务器退出失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    else
    	isSuccess=true;
  }
  catch(ex){
    showMsg(1,"ftp服务器退出异常， "+ex.description);
  }
  return isSuccess;
}

/**
 * ftp文件缓存设置
 * @param valueMB 缓存大小 1即1MB缓存
 */
function ftpSetFileStepSize(valueMB){
 var isSuccess=false;
 try
 {
    if(MDOCX.ftpSetFileStepSize(1024*valueMB)!=SUCCESS)
      showMsg(1,"ftp文件缓存设置失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    else
    	isSuccess=true;
  }
  catch(ex){
    showMsg(1,"ftp文件缓存设置异常， "+ex.description);
  }
  return isSuccess;
} 

/**
 * 文件夹创建 创建完成后即进入到创建的目录级
 * @param remoteDir 文件夹目录名，如果创建多级目录则用AAA/BBB/CCC方式传递
 */
function ftpCreateRemoteDir(remoteDir){
  try{
    ftpSetRemoteRoot();
    var dirArr = remoteDir.split("/");
    for(var i=0; i<dirArr.length; i++)
    {
      if(MDOCX.ftpCreateRemoteDir(dirArr[i])=="0"){
        ftpSetRemoteDir(dirArr[i]);
      }
    }
  }
  catch(ex){
    showMsg(1,"文件夹创建异常， "+ex.description);
  }
}

/**
 * 文件夹删除 包括文件夹下的所有文件 ***慎用***
 * @param remoteDir 文件夹目录名
 */
function ftpDelRemoteDir(filePath){
  try
  {
    MDOCX.ftpDelRemoteDir(filePath);
  }
  catch(ex){
    showMsg(1,"info "+ex.description);
  }
}

/**
 * 设置进入文件夹的位置 如果想进入"\test\test\",那么传入的参数为"test\\test"
 * @param remoteDir 文件夹目录名
 */
function ftpSetRemoteDir(remoteDir){
  try{
    var str =  MDOCX.ftpSetRemoteDir(remoteDir);
    if(str!="1")
    {
      showMsg(1,"设置进入文件夹的位置失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    showMsg(1,"设置进入文件夹的位置异常， "+ex.description);
  }
}

/**
 * 设置进入ftp的根目录
 * @param remoteDir 文件夹目录名
 */
function ftpSetRemoteRoot(){
  try{
    if(MDOCX.ftpSetRemoteRoot()!=SUCCESS)
    {
      showMsg(1,"设置进入ftp的根目录失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    showMsg(1,"设置进入ftp的根目录异常， "+ex.description);
  }
} 

/**
 * 获取当前进入文件夹的位置 ***基本可以不用这个方法，此方法调试模式下用***
 */
function ftpGetRemoteDir(){
  try{
    if(MDOCX.ftpGetRemoteDir()!=SUCCESS)
    {
      showMsg(1,"获取当前进入文件夹的位置失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
  }
  catch(ex){
    showMsg(1,"获取当前进入文件夹的位置异常， "+ex.description);
  }
}

/**
 * ftp开始上传文件
 * @param localFile 需要上传的文件全路径 例如"c:\\2223.mp3"
 * @param ftpFile 上传在ftp的文件名称（前提，设置进入文件夹的位置）  例如"222.sys"
 * @return ture-成功，开始上传； false-失败
 */
function ftpUploadFile(localFile, ftpFile){
  var isSuccess = "";
  try{
    if(MDOCX.ftpUploadFile(localFile, ftpFile)!=SUCCESS)
    {
      showMsg(1,"ftp开始上传文件失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    }
    else
    	isSuccess = true;
  }
  catch(ex){
    showMsg(1,"ftp开始上传文件异常, "+ex.description);
  }
  return isSuccess;
} 

/**
 * 获取ftp文件上传进度
 */
function ftpGetUploadFilePercent(){
  var percent = 0;
  try{
	  percent =  MDOCX.ftpGetUploadFilePercent();
  }
  catch(ex){
    showMsg(1,"获取ftp文件上传进度异常 "+ex.description);
  }
  return percent;
} 

/**
 * 删除本地文件 ***慎用***
 * @param localFile 需要删除本地的文件全路径 例如"c:\\2223.mp3"
 */
function delLocalFile(filePath){
  var isSuccess = false;
  try
  {
    if(MDOCX.DelLocalFile(filePath)!=SUCCESS)
      showMsg(1,"删除本地文件失败，请与管理员联系~ 失败编码："+MDOCX.ftpGetErrorMsg());
    else
      isSuccess=true;
  }
  catch(ex){
    showMsg(1,"删除本地文件异常 "+ex.description);
  }
  return isSuccess;
}


/**
 * 取得本地文件夹下的所有文件（含文件夹下的文件） 迭代查询
 * @param remoteDir 例如"D:\\wavecut\\"
 */
function getLocalDirFiles(fileDir){
  var files = "";
  try{
	  files =  MDOCX.getLocalDirFiles(fileDir,1);
  }
  catch(ex){
    showMsg(1,"取得本地文件夹下的所有文件（含文件夹下的文件）异常 "+ex.description);
  }
  return files;
}

/**
 * 获取ftp文件上传速度
 */
function ftpGetUploadSpeed(){
  var speed = 0;
  try{
	  speed =  MDOCX.ftpGetUploadSpeed();
  }
  catch(ex){
    showMsg(1,"获取ftp文件上传速度异常 "+ex.description);
  }
  return speed;
}

/**
 * 获取USB外接设备连接的盘符,多个盘符直接以*号隔开
 */
function getUsbDriver(){
 var usbDrivers = ""; 
 try
 {
	usbDrivers =  MDOCX.getUsbDriver();
 }
 catch(ex){
    showMsg(2,ex.description);
 }
  return usbDrivers;
}

/**
 * 选择本地文件
 */
function selectLocalSaveDir()
{
	var fileDir="";
    try{
		fileDir =  MDOCX.selectLocalSaveDir();
	}
    catch(ex){
    	showMsg(1,"选择本地文件异常 "+ex.description);
	}
    return fileDir;
}

/**
 * 设置本地文件路径
 */
function setLocalSaveDir(saveDir){
    try{
		MDOCX.setLocalSaveDir(saveDir);
	}
    catch(ex){
		alert("info "+ex.description);
	}
}


/**
 * 获得选择需要进行上传盘符下的第一个文件
 */
function getLocalFirstFile(letter){
  var file = "";
  try
  {
  	if(letter.indexOf(':')>0)
  	{
  		file =  MDOCX.getLocalFirstFile(letter,1);
  	}
  	else
  	{
  		file =  MDOCX.getLocalFirstFile(letter+":\\",1);
    }
  }
  catch(ex){
    showMsg(1,"获得选择需要进行上传盘符下的第一个文件异常 "+ex.description);
  }
  return file;
}

/**
 * 获取上传文件的文件创建时间
 * @param localFile 例如"c:\\2223.mp3"
 */
function getLocalFileCreateTime(filePath){
  var createTime = "";
  try{
	  createTime =  MDOCX.getLocalFileCreateTime(filePath);
  }
  catch(ex){
    showMsg(1,"获取上传文件的文件创建时间异常 "+ex.description);
  }
  return createTime;
}

/**
 * 获取选择需要进行上传盘符下的文件个数
 * @param letter 盘符 例如"C"
 */
function getLocalDirFilesNum(letter){
  var fileNum = 0;
  try{
    if(letter.indexOf(":")>0)
    {
    	fileNum = MDOCX.getLocalDirFilesNum(letter+"\\",1);
    }
    else
    {
    	fileNum = MDOCX.getLocalDirFilesNum(letter+":\\",1);
    }
  }
  catch(ex){
    showMsg(1,"获取选择需要进行上传盘符下的文件个数异常 "+ex.description);
  }
  return fileNum;
}


function copyLocalFile(sourceFile,targetFile){
    try{
        MDOCX.copyLocalFile(sourceFile,targetFile);
     }
    catch(ex){
          alert("info "+ex.description);
     }
}


