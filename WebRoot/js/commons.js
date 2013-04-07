//阻止冒泡函数
function stopBubble(e){   
    if(e && e.stopPropagation){
        e.stopPropagation();    //w3c
    }else{
        window.event.cancelBubble=true; //IE
    }
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
 * 批量选择CheckBox
 * 
 * @param checkboxName
 *            checkBox名称
 * @param checked
 *            选中状态 true or false
 */
function checkAll(checkboxName, firstCheckboxName)
{
	var obj = document.getElementsByName(checkboxName);
	var firstObj = document.getElementById(firstCheckboxName);
	if (obj != null && firstObj != null)
	{
		var j = 0;
		for ( var i = 0; i < obj.length; i++)
		{
			if (obj.item(i).checked)
			{
				j++;
			}
		}
		if(j == obj.length)
		{
			firstObj.checked = true;
		}
		else
		{
			firstObj.checked = false;
		}
	}
}
/**
 * 批量选择CheckBox 根据自定义属性及属性值选中
 * 
 * @param checkboxName
 *            checkBox名称
 * @param checked
 *            选中状态 true or false
 * @param attrName
 *            属性名称
 * @param attrValue
 *            属性值
 */
function selectAll(checkboxName, checked, attrName, attrValue)
{
	var obj = document.getElementsByName(checkboxName);
	if (obj != null)
	{
		for ( var i = 0; i < obj.length; i++)
		{
			if (!obj.item(i).disabled && obj.item(i).getAttribute(attrName) == attrValue)
			{
				obj.item(i).checked = checked;
			}
		}
	}
}
/**
 * 根据值选中相应checkbox
 * 
 * @param checkboxName
 *            checkBox名称
 * @param values
 *            选中值 多个值直接用逗号隔开
 */
function setCheckboxCheckedValue(checkboxName, values)
{
	if (values == "")
		return;
	var valueArray = values.split(",");
	var obj = document.getElementsByName(checkboxName);
	if (obj != null)
	{
		for ( var i = 0; i < obj.length; i++)
		{
			for ( var j = 0; j < valueArray.length; j++)
				if (obj.item(i).value == valueArray[j])
				{
					obj.item(i).checked = true;
					break;
				}
		}
	}
}

/**
 * 根据数值选中相应单选按钮
 * 
 * @param radioName
 *            radio名称
 * @param checkedValue
 *            选中值
 * 
 */
function checkedRadio(radioName, checkedValue)
{
	var obj = document.getElementsByName(radioName);
	if (obj != null)
	{
		for ( var i = 0; i < obj.length; i++)
		{
			if (obj.item(i).value == checkedValue)
			{
				obj.item(i).checked = true;
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
 * setSelectSelected
 * 
 * @param objName
 * @param objValue
 * @return
 */
function setObjSelected(objName, objValue)
{
	var obj = document.getElementById(objName);
	if (obj != null)
	{
		for ( var i = 0; i < obj.length; i++)
		{
			alert(obj.options[i].value+"  "+objValue)
			if (obj.options[i].value == objValue)
			{
				obj.options[i].selected = true;
				break;
			}
		}
	}
}

/**
 * getMutiSelected
 * 
 * @param objName
 * @return
 */
function getMutiSelectedValue(objName)
{
	var selList = document.getElementById(objName);
	var selListOptions = selList.options;
	var selectedIds = "";

	for ( var i = 0; i < selListOptions.length; i++)
	{
		selectedIds += (selList.options[i].value + ",");
	}
	selectedIds = selectedIds.substring(0, selectedIds.lastIndexOf(","));
	return selectedIds;
}

/**
 * 添加选项
 * 
 * @param leftObjName
 * @param rightObjName
 * @return
 */
function addSelected(leftObjName, rightObjName)
{
	var leftSelectList = document.getElementById(leftObjName);
	var rightSelectList = document.getElementById(rightObjName);
	var leftSelectOptions = leftSelectList.options;
	var rightSelectOptions = rightSelectList.options;
	var len = leftSelectOptions.length;

	for ( var i = 0; i < len; i++)
	{
		if (leftSelectList.options[i].selected)
		{
			var isExit = false;
			for ( var j = 0; j < rightSelectOptions.length; j++)
			{
				if (leftSelectList.options[i].value == rightSelectList.options[j].value)
					isExit = true;
			}
			if (isExit == false)
			{
				rightSelectList.options[rightSelectList.length] = new Option(leftSelectList.options[i].text,
						leftSelectList.options[i].value);
				leftSelectList.options[i].style.color = "gray";
			}
		}
	}
}

/**
 * 删除所选项
 * 
 * @param leftObjName
 * @param rightObjName
 * @return
 */
function delSelected(leftObjName, rightObjName)
{
	var leftSelectList = document.getElementById(leftObjName);
	var rightSelectList = document.getElementById(rightObjName);
	var leftSelectOptions = leftSelectList.options;
	var rightSelectOptions = rightSelectList.options;

	var optionLen = rightSelectOptions.length;
	var offset = 0;
	for ( var i = 0; i < optionLen; i++)
	{
		if (rightSelectList.options[i - offset].selected)
		{
			for ( var j = 0; j < leftSelectOptions.length; j++)
			{
				if (leftSelectList.options[j].value == rightSelectList.options[i - offset].value)
					leftSelectList.options[j].style.color = "black";
			}

			rightSelectList.remove(i - offset);
			offset++;
		}
	}
}

/**
 * 获得radio的选中值
 * 
 * 
 * @param name
 * @return
 */
function getRadioValue(radioName)
{
	var radios = document.getElementsByName(radioName);
	var value = "";
	for ( var i = 0; i < radios.length; i++)
	{
		if (radios[i].checked == true)
		{
			value = radios[i].value;
			break;
		}
	}
	return value;
}

/**
 * 获得下拉列表选中值
 * 
 * 
 * @param selectName
 * @return
 */
function getSelectedValue(selectName)
{
	var selList = document.getElementById(selectName);
	var value = "";
	for ( var i = 0; i < selList.length; i++)
	{
		if (selList.options[i].selected == true)
		{
			value = selList.options[i].value;
			break;
		}
	}
	return value;
}
/**
 * showPicture
 * 
 * @param sUrl
 * @param divName
 * @return
 */
function showPic(sUrl, divName, offset, width, height)
{
	var x, y;
	x = event.clientX;
	y = event.clientY - offset;
	document.getElementById(divName).style.left = x;
	document.getElementById(divName).style.top = y;
	document.getElementById(divName).innerHTML = "<img src=\"" + sUrl + "\" width=\"" + width + "\" height=\"" + height + "\">";
	document.getElementById(divName).style.display = "block";
}

/**
 * hiddenPicture
 * 
 * @param divName
 * @return
 */
function hiddenPic(divName)
{
	document.getElementById(divName).innerHTML = "";
	document.getElementById(divName).style.display = "none";
}

/**
 * show DIV
 * 
 * @param divName
 */
function showDiv(divName)
{
	document.getElementById(divName).style.display = "";
}

/**
 * hidden div
 * 
 * @param divName
 */
function hiddenDiv(divName)
{
	document.getElementById(divName).style.display = "none";
}

String.prototype.Trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 上移
 * 
 * @param oSelect
 *            源列表框
 * @param isToTop
 *            true为移动到顶端，false反之，默认为false
 * @return
 */
function moveUp(oSelect, isToTop)
{
	if (isToTop == null)
		isToTop = false;

	if (oSelect.multiple)
	{
		for ( var selIndex = 0; selIndex < oSelect.options.length; selIndex++)
		{
			if (isToTop)
			{ // 移动到顶端

				if (oSelect.options[selIndex].selected)
				{
					var transferIndex = selIndex;
					while (transferIndex > 0 && !oSelect.options[transferIndex - 1].selected)
					{
						oSelect.options[transferIndex].swapNode(oSelect.options[transferIndex - 1]);
						transferIndex--;
					}
				}
			}
			else
			{ // 不移动到顶端
				if (oSelect.options[selIndex].selected)
				{
					if (selIndex > 0)
					{
						if (!oSelect.options[selIndex - 1].selected)
							oSelect.options[selIndex].swapNode(oSelect.options[selIndex - 1]);
					}
				}
			}
		}
	}
	else
	{
		var selIndex = oSelect.selectedIndex;
		if (selIndex <= 0)
			return;
		if (isToTop)
		{ // 移动到顶端

			while (selIndex > 0)
			{
				oSelect.options[selIndex].swapNode(oSelect.options[selIndex - 1]);
				selIndex--;
			}
		}
		else // 不移动到顶端
		oSelect.options[selIndex].swapNode(oSelect.options[selIndex - 1]);
	}
}

/**
 * 下移
 * 
 * @param oSelect
 *            源列表框
 * @param isToTop
 *            true为移动到顶端，false反之，默认为false
 * @return
 */
function moveDown(oSelect, isToBottom)
{
	if (isToBottom == null)
		var isToBottom = false;

	var selLength = oSelect.options.length - 1;

	if (oSelect.multiple)
	{
		for ( var selIndex = oSelect.options.length - 1; selIndex >= 0; selIndex--)
		{
			if (isToBottom)
			{ // 移动到顶端

				if (oSelect.options[selIndex].selected)
				{
					var transferIndex = selIndex;
					while (transferIndex < selLength && !oSelect.options[transferIndex + 1].selected)
					{
						oSelect.options[transferIndex].swapNode(oSelect.options[transferIndex + 1]);
						transferIndex++;
					}
				}
			}
			else
			{ // 不移动到顶端
				if (oSelect.options[selIndex].selected)
				{
					if (selIndex < selLength)
					{
						if (!oSelect.options[selIndex + 1].selected)
							oSelect.options[selIndex].swapNode(oSelect.options[selIndex + 1]);
					}
				}
			}
		}
	}
	else
	{
		var selIndex = oSelect.selectedIndex;
		if (selIndex >= selLength - 1)
			return;
		if (isToBottom)
		{ // 移动到顶端

			while (selIndex < selLength - 1)
			{
				oSelect.options[selIndex].swapNode(oSelect.options[selIndex + 1]);
				selIndex++;
			}
		}
		else //不移动到顶端
		oSelect.options[selIndex].swapNode(oSelect.options[selIndex + 1]);
	}
}

/**
 * 验证文本文件后缀
 * 
 * @param fileUrl
 * @return
 */
function _checkTxtType(fileUrl)
{
	if (fileUrl != "")
	{
		if (!/\.(txt|TXT)$/.test(fileUrl)) { return false; }
	}
	return true;
}


/*   
函数：格式化日期   
参数：formatStr-格式化字符串   
d：将日显示为不带前导零的数字，如1   
dd：将日显示为带前导零的数字，如01   
ddd：将日显示为缩写形式，如Sun   
dddd：将日显示为全名，如Sunday   
M：将月份显示为不带前导零的数字，如一月显示为1   
MM：将月份显示为带前导零的数字，如01  
MMM：将月份显示为缩写形式，如Jan  
MMMM：将月份显示为完整月份名，如January  
yy：以两位数字格式显示年份  
yyyy：以四位数字格式显示年份  
h：使用12小时制将小时显示为不带前导零的数字，注意||的用法  
hh：使用12小时制将小时显示为带前导零的数字  
H：使用24小时制将小时显示为不带前导零的数字  
HH：使用24小时制将小时显示为带前导零的数字  
m：将分钟显示为不带前导零的数字  
mm：将分钟显示为带前导零的数字  
s：将秒显示为不带前导零的数字  
ss：将秒显示为带前导零的数字  
l：将毫秒显示为不带前导零的数字  
ll：将毫秒显示为带前导零的数字  
tt：显示am/pm  
TT：显示AM/PM  
返回：格式化后的日期  
*/ 
Date.prototype.format = function (formatStr) {  
var date = this;  
/*  
函数：填充0字符  
参数：value-需要填充的字符串, length-总长度  
返回：填充后的字符串  
*/ 
var zeroize = function (value, length) {  
if (!length) {  
length = 2;  
}  
value = new String(value);  
for (var i = 0, zeros = ''; i < (length - value.length); i++) {  
zeros += '0';  
}  
return zeros + value;  
};  
return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {  
switch ($0) {  
case 'd': return date.getDate();  
case 'dd': return zeroize(date.getDate());  
case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];  
case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];  
case 'M': return date.getMonth() + 1;  
case 'MM': return zeroize(date.getMonth() + 1);  
case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];  
case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];  
case 'yy': return new String(date.getFullYear()).substr(2);  
case 'yyyy': return date.getFullYear();  
case 'h': return date.getHours() % 12 || 12;  
case 'hh': return zeroize(date.getHours() % 12 || 12);  
case 'H': return date.getHours();  
case 'HH': return zeroize(date.getHours());  
case 'm': return date.getMinutes();  
case 'mm': return zeroize(date.getMinutes());  
case 's': return date.getSeconds();  
case 'ss': return zeroize(date.getSeconds());  
case 'l': return date.getMilliseconds();  
case 'll': return zeroize(date.getMilliseconds());  
case 'tt': return date.getHours() < 12 ? 'am' : 'pm';  
case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';  
}  
});  
} 
