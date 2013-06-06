<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body>
	<div class="gray_bor_bg">
		<div class="table_div">
			<table width="100%" class="table_border">
				<tr>
					<td class="title" width="100">
						公告标题
					</td>
					<td>
						${resultObject.noticeTitle }
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						发布时间
					</td>
					<td>
						<fmt:parseDate pattern="yyyyMMddHHmmss" var="parsedDateTime" parseLocale="en_US">
							<c:out value="${resultObject.createTime}" />
						</fmt:parseDate>
						<fmt:formatDate value='${parsedDateTime}' pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr id="corpTr">
					<td class="title" width="100">
						发布部门
					</td>
					<td>
						${resultObject.sysCorp.corpName}
					</td>
				</tr>
				<tr>
					<td class="title" width="100">
						公告内容
					</td>
					<td>
						${resultObject.noticeContent }
					</td>
				</tr>
				<tr id="targetTr">
					<td class="title">发布对象：</td>
					<td>
						<div id="treeDemo" class="ztree" style="height: 200px;width: 400px;overflow: auto;">
						</div>
						<label id="treeError" style="color: red;font-style: italic"></label>
					</td>
				</tr>
			</table>
		</div>
	</div>
<script>
	$(function(){
		//树形菜单
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {enable: true}
			}
		};
		var zNodes = ${tree.json};  
		$(document).ready(function(){
			
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			initCheck('${resultObject.targetIds}');
		});
	});
	
	//初始化选中
	function initCheck(str){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(false);
		var strs= new Array(); //定义一数组
		strs=str.split(","); //字符分割      
		for(var i=0;i<strs.length ;i++){
			var pId = strs[i];
			var node = treeObj.getNodeByParam("id", pId,null);
			if(node!=null)
			treeObj.checkNode(node, true, false);
		}
	}
	
</script>
<c:if test="${param['mgrFlag']!=1}">
	<script>
		$('#targetTr').hide();
	</script>
</c:if>
</body>
</html>