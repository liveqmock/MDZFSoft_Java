<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="w_185 fl">
	<div class="mod_white">
		<h4 class="mod_hd">
			个人信息
		</h4>
		<div class="inner_bor">
			<ul class="user_info">
			<li>姓&nbsp;&nbsp;&nbsp;&nbsp;名：<span>${sessionScope.loginToken.sysLogin.userName }</span></li>
			<li>警员编号：<span>${sessionScope.loginToken.sysLogin.userCode }</span></li>
			<li>所属部门：<span>${sessionScope.loginToken.sysLogin.sysCorp.corpName}</span></li>
			<li>所属角色：<span>${sessionScope.loginToken.sysRoles[0].roleName }</span></li>
			</ul>
		</div>
	</div>
	<div class="mod_white mt_10">
		<h4 class="mod_hd">
			个人菜单
		</h4>
		<div class="inner_bor">
			<ul class="user_orders">
			<li><a href="${ctx}/fileMgr/myFileList">我的上传</a></li>
			<li><a href="${ctx }/pages/reset_pwd/reset_pwd.jsp?r=<%=Math.random() %>" class="nyroModal" target="_blank" title="修改密码">修改密码</a></li>
			<li><a href="###" onclick="javascript:logout();">退出系统</a></li>
			</ul>
		</div>
	</div>
</div>
			