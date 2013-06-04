<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
<%@ include file="/plugins/ztree.jsp"%>
</head>
<body>
<%@ include file="/pages/top.jsp"%>
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
				<h4 class="content_hd long_content_hd">部门管理</h4>
				<div class="content_bd">
					<div style="margin-bottom: 5px">
						<a href="javascript:corpAdd()" class="blue_mod_btn">部门新增</a>
						<a href="javascript:corpMoidfy()" class="blue_mod_btn">部门修改</a>
						<a href="javascript:corpDelete()" class="blue_mod_btn">部门删除</a>
					</div>
					<div style="background-color:#f2edda; border:1px solid #d5cfaf; overflow:auto">
						<ul id="treeDemo" class="ztree" style="width: 180px;height: 300px">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<a id="modalWindowAction" class="nyroModal" href="#" target="_blank" style="display: none" title="设备新增">触发设备操作</a>
<%@ include file="/pages/footer.jsp"%>
</body>
<script>
	var treeObj;
		//树形菜单
		var setting = {
			data : {
				simpleData : {
					enable : true
				}
			}
		};
		var zNodes = ${tree.json};
		$(document).ready(function()
		{
			treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$('.nyroModal').nyroModal();
		});
		
		//部门新增
		function corpAdd()
		{
			var url = '${ctx}/corpMgr/add?r='+Math.random();
			var nodes = treeObj.getSelectedNodes();
			if(nodes.length!=0)
			{
				url+='&parentCorpId='+nodes[0].id;	
			}
			
	   	  	$('#modalWindowAction').attr("href",url);
	   	  	$('#modalWindowAction').attr("title","部门新增");
	      	$('#modalWindowAction').click();
		}
		//部门修改
		function corpMoidfy()
		{
			var nodes = treeObj.getSelectedNodes();
			if(nodes.length==0)
			{
				alert('请先选择一个要修改的部门');
				return;		
			}
			var url = '${ctx}/corpMgr/edit/'+nodes[0].id+'?r='+Math.random();
	   	  	$('#modalWindowAction').attr("href",url);
	   	  	$('#modalWindowAction').attr("title","部门修改");
	      	$('#modalWindowAction').click();
		}
		//部门删除
		function corpDelete()
		{
			var nodes = treeObj.getSelectedNodes();
			if(nodes.length==0)
			{
				alert('请先选择一个要删除的部门');
				return;		
			}
			if(confirm("您确认删除该部门吗？"))
			{
				$.getJSON("${ctx}/corpMgr/delete/"+nodes[0].id+"?r="+Math.random(), function(data){
					if(data.messageType=='1')
				    {
				    	alert(data.promptInfo);
				    	window.location.replace(window.location.href);
				    }
				    else
				    {
				    	alert(data.promptInfo);
				    }
				});
			}
		}

</script>
</html>