<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<div id="nav">
		<div class="nav_box">
			<div class="layout">
				<ul class="nav_list">
					<c:forEach var="level1Menu" items="${level1MenuList}" varStatus="status">
					<li class="nav_item current" id="menuIndex_${status.index}"><a class="nav_target" href="${ctx}/${level1Menu.permissionUrl}">${level1Menu.permissionName}</a>
					<ul class="nav_sublist" id="childMenu_1">
					<c:forEach var="level2Menu" items="${level2MenuMap[level1Menu.permissionId]}">
						<li class="nav_subitem" id="menu_${level2Menu.permissionId}"><a class="nav_subtarget" href="${ctx}/${level2Menu.permissionUrl}">${level2Menu.permissionName}</a></li>
					</c:forEach>
					</ul>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<script>
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
