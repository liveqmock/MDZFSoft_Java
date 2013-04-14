<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/plugins/jquery-nyroModal.jsp" %>
</head>

<body>
<%@ include file="/pages/top.jsp"%>
	<!--content============================================begin-->
	<div id="container">
		<div class="layout clearfix">
			<jsp:include page="/pages/left.jsp" flush="true" />
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
<%@ include file="/pages/footer.jsp"%>
<script>
$(document).ready(function()
{
	$('.nyroModal').nyroModal();
});
//关闭弹出窗口并刷新页面
function closeModalWindow()
{
	$.nmTop().close();
}
</script>
</body>
</html>
