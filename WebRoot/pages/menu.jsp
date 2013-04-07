<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-ui.jsp"%>
<%@ include file="/plugins/ztree.jsp"%>
<LINK href="${ctx}/css/left_menu.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div style="height: 100%; overflow: auto;">
		<c:forEach var="level1Menu" items="${level1MenuList}">
			<div id="menu_${level1Menu.permissionId}" class="menu_out">
				<img src="${ctx}/images/${level1Menu.permissionIco}"><a href="#1" class="menu_ico2">${level1Menu.permissionName}</a>
			</div>
			<div id="submenu_${level1Menu.permissionId}" class="menu_con" style="display: none">
				<c:forEach var="level2Menu" items="${level2MenuMap[level1Menu.permissionId]}">
				<a href="${ctx}/${level2Menu.permissionUrl}" target="mainFrame">${level2Menu.permissionName}</a>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	<script>

	var openMenuId = "menu_0";
	$(document).ready(function(){
		//根据屏幕高度，重新设置每个子菜单的高度
		$("div[id^='submenu']").height($(document).height()-$("#menu_${level1MenuList[0].permissionId}").height()*'${fn:length(level1MenuList)}');

		$("div[id^='menu']").click(function(){
			 var menuId= $(this).attr("id");
			 $("#sub"+menuId).slideToggle();
			 if(openMenuId!=menuId)
				 $('#'+openMenuId).click();
			 if($(this).hasClass("menu_out"))
			 {
				 $(this).attr("class","menu_over");
				 openMenuId = menuId;
			 }
			 else
			 {
				 $(this).attr("class","menu_out");
				 openMenuId="";
			 }
		});	
	});
	</script>
</body>
</html>
