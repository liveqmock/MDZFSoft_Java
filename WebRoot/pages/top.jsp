<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/plugins/jquery/jquery.jclock.js"></script>
<div id="header">
	<div class="logobox">
		<div class="layout">
			<a href="#" class="mainlogo"><img src="${ctx}/images/mainlogo.png" alt=""/></a>
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
				<li class="nav_item current" id="menuIndex_0">
					<a class="nav_target" href="${ctx}/index">我的主页</a>
				</li>
				<c:forEach var="level1Menu" items="${sessionScope.loginToken.level1MenuList}" varStatus="status">
				<li class="nav_item" id="menuIndex_${status.count}">
					<c:if test="${empty level1Menu.permissionUrl}">
						<a class="nav_target" href="#">${level1Menu.permissionName}</a>
					</c:if>
					<c:if test="${not empty level1Menu.permissionUrl}">
						<a class="nav_target" href="${ctx}/${level1Menu.permissionUrl}">${level1Menu.permissionName}</a>
					</c:if>
					<ul class="nav_sublist" id="childMenu_${status.count}">
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
	var selectedIndex=0;
	<% //EditBy 孙强伟  at 20130711  ，修正不管单击哪个菜单项之后，current class 总是被附给首页的问题。
	   //提示，request.getRequestURI和getRequestURL在经过Spring mvc 执行之后其前后值是不一样的。
	   //因此为了实现功能，我在LogInterceptor.java的拦截器中添加了preHandle的方法，
	   //对需要经过Spring mvc处理的URL事先取到并放到request中，只有这样才能实现需求。
	   //对于不需要经过Spring MVC处理的URL请求，就直接使用request.getRequestURL来处理。
	%>
	<c:if test="${!empty(requestScope.oldRequestURI)}">
		<c:if test="${!fn:endsWith(requestScope.oldRequestURI,'index')}">
			<c:forEach var="level1Menu" items="${sessionScope.loginToken.level1MenuList}" varStatus="status">
				<c:forEach var="level2Menu" items="${sessionScope.loginToken.level2MenuMap[level1Menu.permissionId]}">
					<c:if test="${fn:endsWith(requestScope.oldRequestURI,level2Menu.permissionUrl)}">
						var selectedIndex=${status.count};
					</c:if>			
				</c:forEach>
			</c:forEach>
		</c:if>
	</c:if>
	<c:if test="${empty(requestScope.oldRequestURI)}">
		<c:if test="${!fn:endsWith(pageContext.request.requestURI,'index')}">
			<c:forEach var="level1Menu" items="${sessionScope.loginToken.level1MenuList}" varStatus="status">
				<c:forEach var="level2Menu" items="${sessionScope.loginToken.level2MenuMap[level1Menu.permissionId]}">
					<c:if test="${fn:endsWith(pageContext.request.requestURI,level2Menu.permissionUrl)}">
						var selectedIndex=${status.count};
					</c:if>			
				</c:forEach>
			</c:forEach>
		</c:if>
	</c:if>
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
   	
   	$(document).ready(function () {
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

   	var menuLenth = '${fn:length(sessionScope.loginToken.level1MenuList)}';
    for(var i=0; i<menuLenth; i++)
    {
    	$('#menuIndex_'+i).attr('class', 'nav_item');
    }
    $('#menuIndex_'+selectedIndex).attr('class', 'nav_item current');

   // if(menuIndex>0)
   // {
    $('#childMenu_'+selectedIndex).css('display', 'block');
   // }
</script>
