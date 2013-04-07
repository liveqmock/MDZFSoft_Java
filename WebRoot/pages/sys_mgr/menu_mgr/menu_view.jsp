<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<link href="${ctx}/plugins/dhtmlxTree/codebase/dhtmlxtree.css" rel="stylesheet" type="text/css">
<script src="${ctx}/plugins/dhtmlxTree/codebase/dhtmlxcommon.js"></script>
<script src="${ctx}/plugins/dhtmlxTree/codebase/dhtmlxtree.js"></script>
<script language="javascript" src="${ctx}/plugins/tips_alert.js"></script>
</head>

<body>
<form:form method="post" modelAttribute="resultObject">
	<fieldset style="width: 80%; padding: 10px"><legend> 菜单详情 </legend>
	<table width="60%" border="0" align="center" cellpadding="4" cellspacing="0">
		<tr>
			<td align="right">菜单名：</td>
			<td align="left">${menu.permissionName}</td>
		</tr>
		<tr>
			<td align="right">菜单URL：</td>
			<td align="left">${menu.permissionUrl}</td>
		</tr>
		<tr>
			<td align="right">菜单状态：</td>
	  		<td align="left"><c:if test="${menu.status==1}">有效</c:if> &nbsp;&nbsp;&nbsp;
	  			 <c:if test="${menu.status==0}"><font color="red">无效</font></c:if>
	  		</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="button" class="short-button" value=" 返 回  " onclick="back();">&nbsp;&nbsp;</td>
		</tr>
	</table>
	</fieldset>
	<br>
</form:form>
</body>
<script type="text/javascript">
  	function back(){
		history.back();
  	}
  </script>
</html>
