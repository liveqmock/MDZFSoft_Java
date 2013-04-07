<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/jquery/jquery.jclock.js"></script>
</HEAD>
<body>
	<div id="header">
		<div class="logobox">
			<div class="layout">
				<a href="" class="mainlogo"><img src="${ctx }/images/mainlogo.png" alt=""/></a>
				<div class="cur_time">
					当前时间：<span id="curDate"></span>
				</div>
				<div class="show_user">
					<div class="cureent_user">
						<span id="goodView"></span><a href="#">${sessionScope.loginToken.sysLogin.userName }</a>,欢迎登录<a href="javascript:logout();" class="blue_btn">退出系统</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="nav">
		<div class="nav_box">
			<div class="layout">
				<ul class="nav_list">
					<c:forEach var="level1Menu" items="${sessionScope.loginToken.level1MenuList}" varStatus="status">
					<li class="nav_item current" id="menuIndex_${status.index}"><a class="nav_target" href="${ctx}/${level1Menu.permissionUrl}">${level1Menu.permissionName}</a>
					<ul class="nav_sublist" id="childMenu_1">
					<c:forEach var="level2Menu" items="${sessionScope.loginToken.level2MenuMap[level1Menu.permissionId]}">
						<li class="nav_subitem" id="menu_${level2Menu.permissionId}"><a class="nav_subtarget" href="${ctx}/${level2Menu.permissionUrl}">${level2Menu.permissionName}</a></li>
					</c:forEach>
					</ul>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<form name="logout_form" method="post" action="${ctx}/login/logout" target="_top">
	</form>
	<script>
		function logout()
		{
			if (confirm("确认退出系统吗？"))
			{
				document.logout_form.submit();
			}
		}

		$('#curDate').jclock({withDate:true, withWeek:true});
	    var now = new Date();
	    var hour = now.getHours();
	   	if(hour < 6){$("#goodView").text("凌晨好！");} 
	   	else if (hour < 9){$("#goodView").text("早上好！");} 
	   	else if (hour < 12){$("#goodView").text("上午好！");} 
	   	else if (hour < 14){$("#goodView").text("中午好！");} 
	   	else if (hour < 17){$("#goodView").text("下午好！");} 
	   	else if (hour < 19){$("#goodView").text("傍晚好！");} 
	   	else if (hour < 22){$("#goodView").text("晚上好！");} 
	   	else {$("#goodView").text("夜里好！");}
	   	
	    jQuery(function($) {
	     	$(".nav_item").hover(function(){
	    		var _this=$(this);
	    		_this.siblings(".nav_item").find(".nav_sublist").hide();
	    		if(_this.find(".nav_sublist").length>0)
	    		{
	    			_this.addClass("nav_hover");
	    			_this.find(".nav_sublist").show();
	    			
	    		}
	    	},function(){
	    		var _this=$(this);
	    		if(_this.find(".nav_sublist").length>0)
	    		{
	    			_this.removeClass("nav_hover");
	    			_this.find(".nav_sublist").hide();
	    			
	    		}
	    		$(".nav_item").each(function(){
	    			if($(this).hasClass("current")){
	    			$(this).find(".nav_sublist").show();
	    		}
	    		});
	    		
	    	});
	     });

	    for(var i=0; i<6; i++)
	    {
	    	$('#menuIndex_'+i).attr('class', 'nav_item');
	    }
	    $('#menuIndex_'+menuIndex).attr('class', 'nav_item current');

	    if(menuIndex>0)
	    {
	    $('#childMenu_'+menuIndex).css('display', 'block');
	    }
	</script>
</body>
</html>
