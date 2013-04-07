<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<%@ include file="/common/header.jsp"%>
</head>

<body>
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<div class="w_185 fl">
				<div class="mod_white">
					<h4 class="mod_hd">
						个人信息
					</h4>
					<div class="inner_bor">
						<ul class="user_info">
						<li>警员编号：<span>${sessionScope.loginToken.sysLogin.userCode }</span></li>
						<li>所属部门：<span></span></li>
						</ul>
					</div>
				</div>
				<div class="mod_white mt_10">
					<h4 class="mod_hd">
						个人菜单
					</h4>
					<div class="inner_bor">
						<ul class="user_orders">
						<li><a href="###" onclick="javascript:mineUpload();">我的上传</a></li>
						<li><a href="###" onclick="javascript:showNotice();">所有公告</a></li>
						<li><a href="###" onclick="javascript:showChangePswd();">修改密码</a></li>
						<li><a href="###" onclick="javascript:userLogout();">退出系统</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="w_805 fr">
				<div class="white_p10">
					<h4 class="content_hd" id="mainTitle">公告管理</h4>
					<div class="content_bd">
						<div class="mange_table" id="mainContext">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<jsp:include page="footer.jsp" />
</body>
</html>
