/*****************
contextPath 获取工程根目录路径
loadPage 载入页面
showObj 显示对象
hideObj 隐藏对象
getArray 获取数组
getJqueryArrayStr 获取数组
userLogout 用户退出
selectRole 选择角色
closeSelectRole 关闭选择角色
selectTree 选择部门（单选）
closeSelectTree 关闭选择部门
chooseTree 选择部门（多选）
closeChooseTree 关闭选择部门
selectUser 选择警员
closeSelectUser 关闭选择警员
selectDateTime 选择日期时间
*****************/

/**
 * 获取工程根目录路径
 */
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
/**
 * 下载文件
 * @param sourceFile
 * @param targetFileNm
 */
function downloadFile(downloadFile){
	downloadFile =encodeURI(encodeURI(downloadFile));
	window.open(getContextPath()+"/servlet/downloadFile?sourceFilePath="+downloadFile);
}

/**
 * 批量选择CheckBox
 * 
 * @param checkboxName
 *            checkBox名称
 * @param checked
 *            选中状态 true or false
 */
function selectAll(checkboxName, checked)
{
	var obj = document.getElementsByName(checkboxName);
	if (obj != null)
	{
		for ( var i = 0; i < obj.length; i++)
		{
			if (!obj.item(i).disabled)
			{
				obj.item(i).checked = checked;
			}
		}
	}
}

/**
 * 获得下拉框所选值信息
 * 
 * @param checkboxName
 *            checkbox名称
 */
function getCheckboxCheckedValue(checkboxName)
{
	var vtmp = "";
	var obj = document.getElementsByName(checkboxName);
	if (obj != null)
	{
		for ( var i = 0; i < obj.length; i++)
		{
			if (obj.item(i).checked == true)
			{
				vtmp += obj.item(i).value + ",";
			}
		}
		if (vtmp.length > 0)
		{
			vtmp = vtmp.substring(0, vtmp.length - 1);
			return vtmp;
		}
		else return "";
	}
}
/**
 * 获取工程根目录路径
 * @param objId 需要载入id名称
 * @param loadUrl 被载入的url
 * @param ifCache 如果为null 默认请求+时间戳
 */
function loadPage(objId, loadUrl, ifCache)
{
jQuery(function($) {
	if(ifCache==null)
	{
		if(loadUrl.indexOf("?")>=0)
		{
			loadUrl += "&loadtime="+Math.random();
		}
		else
		{
			loadUrl += "?loadtime="+Math.random();
		}
	}
	$("#"+objId).load(loadUrl);
});
}


/**
 * 显示对象
 * @param objId 对象id名称
 * @param nospeed 关闭速度
 */
function showObj(objId, nospeed)
{
jQuery(function($) {
	var speed = 500;
	if(nospeed!=null)
	{
		speed = 0;
	}
	$("#"+objId).show(speed,function(){
		$(this).css("display", "block");
	});
});
}

/**
 * 隐藏对象
 * @param objId 对象id名称
 * @param nospeed 关闭速度
 */
function hideObj(objId, nospeed)
{
jQuery(function($) {
	var speed = 1000;
	if(nospeed!=null)
	{
		speed = 0;
	}
	$("#"+objId).hide(speed,function(){
	});
});
}

//关闭弹出窗口并刷新页面
function closeModalWindow(isRefresh)
{
	try
	{
	$.nmTop().close();
	}
	catch(e)
	{
		
	}
	if(isRefresh)
		location.href = location.href;
		//window.location.replace(window.location.href);
}


String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}  