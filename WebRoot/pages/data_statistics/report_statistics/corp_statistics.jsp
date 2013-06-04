<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.njmd.framework.utils.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>  
<%@ include file="/plugins/jquery-nyroModal.jsp" %>

<%@ include file="/plugins/jquery-powerFloat.jsp" %>
 <%@ include file="/plugins/calendar.jsp"%> 
  <%@ include file="/plugins/ztree.jsp"%>
<script>
//数据初始化
jQuery(function($) { 
	 Calendar.setup(
				{
				     inputField  : "startDate",         // ID of the input field
				     displayArea : "filter_GE_operTime",
				     ifFormat    : "%Y-%m-%d",    // the date format
				     daFormat    : "%Y%m%d000000",
				     button      : "startDate"       // ID of the button
				});
		 Calendar.setup(
					{
					     inputField  : "endDate",         // ID of the input field
					     displayArea : "filter_GE_operTime",
					     ifFormat    : "%Y-%m-%d",    // the date format
					     daFormat    : "%Y%m%d000000",
					     button      : "endDate"       // ID of the button
					});

	
	//树形菜单
	var setting = {
		data: {
			simpleData: {enable: true}
		},
		callback: {
			onClick: onClick1
		}
	};
	var zNodes = ${tree.json};  
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
});


function onClick1(e, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var corpId = nodes[0].id;
	var corpName = nodes[0].name;
	$("#corpName").val(corpName);
	$("#corpId").val(corpId);
	$.powerFloat.hide();
	$("#corpChooseDiv").css("display","none");
}

/**
 * 根据时间粒度 切换需要选择的时间区域
 */
function showChooseTime()
{ 
	if(1==$("#queryType").val()){
		$("#year").show();
		$("#month").hide();
		$("#startDate").hide();
		$("#endDate").hide();
		
	}
	if(2==$("#queryType").val()){
		$("#year").show();
		$("#month").show();
		$("#startDate").hide();
		$("#endDate").hide();
		
	}
	if(3==$("#queryType").val()){
		$("#year").hide();
		$("#month").hide();
		$("#startDate").show();
		$("#endDate").show();
		
	}
}
function _submit(){
	if($("#corpName").val()==""){
alert("请选择机构");
return;
		}
	$('#mainForm').submit();
}
</script>
</head>
<body>
	<%@ include file="/pages/top.jsp"%>
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">部门统计</h4>
					<div class="content_bd">
<div class="error msg" id="serverInfoMsg" style="display:none" onclick="hideObj($(this).attr('id'))">Message if login failed</div>
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">组合条件搜索</h5>
					<form id="mainForm" action="<%=request.getContextPath() %>/reportStatistics/corpChart" method="post" target="corpChart">
	 
						<div class="mt_10">
								<label>所属部门：</label>
									<input type="hidden" name="corpId" id="corpId" value=""/>
									<input type="text" name="corpName" id="corpName" value="" size="20" class="input_79x19" readonly="readonly" style="cursor: pointer;"/>
									<div id="corpChooseDiv" style="position:absolute; border:solid 1px #CCCCCC; width:250px; height:200px; top:23px; left:0px; background:#FFFFFF;display: none;overflow:auto">
								         <ul id="treeDemo" class="ztree" style="width: 180px;">
								         </ul>
							        </div>
							        <label>统计口径：</label>
							        <select class="input_79x19" id="queryType" name="queryType" onchange="showChooseTime()">
									<option value="1">按年统计</option>
									<option value="2">按月统计</option>
									<option value="3">按天统计</option>
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<select id="year" name="year" class="input_79x19">
									<%
									int year=Integer.parseInt(DateTimeUtil.getChar8().substring(0,4));
									for(int i=0;i<8;i++){
										 
										 out.print("<option  value=\""+(year-i)+"\">"+(year-i)+"</option>");
									}
									%>
									</select>
									<select id="month" name="month" class="input_79x19"  style="display:none">
									<%
									String month=DateTimeUtil.getChar8().substring(4,6);
									for(int i=1;i<=12;i++){
										String mon="0"+i;
										 if(i>=10){
											 mon=Integer.toString(i);
										 }
										 out.print("<option "+(month.equals(mon)?"selected":"")+"  value=\""+mon+"\">"+mon+"</option>");
									}
									%>
									</select>
									
									<input type="text" class="input_79x19" id="startDate" name="startDate" value="<%=DateTimeUtil.formatChar8(DateTimeUtil.rollDate(DateTimeUtil.getChar8(),-6)).trim() %>"  readonly  style="display:none"/>&nbsp;&nbsp;<input type="text" class="input_79x19" id="endDate" name="endDate" value="<%=DateTimeUtil.formatChar8(DateTimeUtil.getChar8()).trim() %>"  readonly  style="display:none"/>
									<a href="javascript:_submit()" class="blue_mod_btn">查&nbsp;询</a> 
								</div>
					</form>
						</div>
					<iframe id="corpChart" name="corpChart" src="" width="916px" height="940px" frameborder="0" scrolling="no"></iframe>
						<div class=" mt_10">
							
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
	$("#corpName").powerFloat({
		eventType: "click",
		target: $("#corpChooseDiv")	
	});
});


function onClick1(e, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	var corpId = nodes[0].id;
	var corpName = nodes[0].name;
	$("#corpName").val(corpName);
	$("#corpId").val(corpId);
	$.powerFloat.hide();
	$("#corpChooseDiv").css("display","none");
}
//关闭弹出窗口并刷新页面
function closeModalWindow()
{
	$.nmTop().close();
}
</script>
</body>
 
</html>