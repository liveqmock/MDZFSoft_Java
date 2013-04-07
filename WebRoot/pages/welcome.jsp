<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" href="${ctx}/plugins/jquery-ui/css/cupertino/jquery-ui-1.8.23.custom.css" rel="stylesheet" />
<script language="javascript" src="${ctx}/plugins/jquery-ui/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/jquery-ui/js/jquery-ui-1.8.23.custom.min.js"></script>
	<script>
	$(function() {
		$( "#accordion" ).accordion();
	});
	</script>
</head>
<body>

	<%-- <table border="0" height="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="menu_bg" valign="top">
				<div class="menu_out">
					<a href="#1" class="menu_ico1">总控制室</a>
				</div>
				<div class="menu_over">
					<a href="#1" class="menu_ico2">总控制室</a>
				</div>
				<div class="menu_con">
					<a href="#1">一楼大厅</a> <a href="#1">二楼会议室</a> <a href="#1">三楼会议室</a>
				</div>
				<div class="menu_out">
					<a href="#1" class="menu_ico1">总控制室</a>
				</div>
				<div class="menu_out">
					<a href="#1" class="menu_ico1">总控制室</a>
				</div></td>
			<td class="menu_border" valign="middle"><img src="${ctx}/images/left_menu_04.jpg"
				style="width: 7px; height: 50px; cursor: pointer;">
			</td>
		</tr>
	</table> --%>
	<div id="accordion">
	<h3><a href="#">Section 1</a></h3>
	<div>
		<p>Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.</p>
	</div>
	<h3><a href="#">Section 2</a></h3>
	<div>
		<p>Sed non urna. Donec et ante. Phasellus eu ligula. Vestibulum sit amet purus. Vivamus hendrerit, dolor at aliquet laoreet, mauris turpis porttitor velit, faucibus interdum tellus libero ac justo. Vivamus non quam. In suscipit faucibus urna. </p>
	</div>
	<h3><a href="#">Section 3</a></h3>
	<div>
		<p>Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis. Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui. </p>
		<ul>
			<li>List item one</li>
			<li>List item two</li>
			<li>List item three</li>
		</ul>
	</div>
	<h3><a href="#">Section 4</a></h3>
	<div>
		<p>Cras dictum. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia mauris vel est. </p><p>Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. </p>
	</div>
</div>
</body>
</html>
