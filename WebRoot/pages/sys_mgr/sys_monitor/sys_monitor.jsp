<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.njmd.framework.utils.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>  
<%@ include file="/plugins/jquery-nyroModal.jsp" %>
 <%@ include file="/plugins/calendar.jsp"%> 
<script>
//数据初始化
jQuery(function($) {
	 Calendar.setup(
			{
			     inputField  : "date1",         // ID of the input field
			     displayArea : "filter_GE_operTime",
			     ifFormat    : "%Y-%m-%d",    // the date format
			     daFormat    : "%Y%m%d000000",
			     button      : "date1"       // ID of the button
			});
	 Calendar.setup(
				{
				     inputField  : "date2",         // ID of the input field
				     displayArea : "filter_GE_operTime",
				     ifFormat    : "%Y-%m-%d",    // the date format
				     daFormat    : "%Y%m%d000000",
				     button      : "date2"       // ID of the button
				});
});

 
/**
 * 根据时间粒度 切换需要选择的时间区域
 */
function showChooseTime()
{ 
	for(i=1; i<=4; i++)
	{
		if(i==$("#req_queryType").val())
		{
			$("#cycletype_"+i).css("display", "block");
		}
		else
		{
			$("#cycletype_"+i).css("display", "none");
		}
	} 
}
 function changeWeekDay(){
	var d=new Date($("#date2").val());  
	d.setDate(d.getDate()-6);  
	$("#date3").val(d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate());
}
 
	 
</script>
</head>
<body>
	<%@ include file="/pages/top.jsp"%>
	<div id="container">
		<div class="layout clearfix">
			<div class="white_p10">
					<h4 class="content_hd long_content_hd">系统监控</h4>
					<div class="content_bd">
<div class="error msg" id="serverInfoMsg" style="display:none" onclick="hideObj($(this).attr('id'))">Message if login failed</div>
						<div class="gray_bor_bg">
							<h5 class="gray_blod_word">组合条件搜索</h5>
					<form action="<%=request.getContextPath() %>/sysMonitor/chart" method="post" target="monitor">
	 
							<div class="search_form">
							<table>
							<tr>
							<td>
							服务器：
									<select class="input_79x19"   name="saveIp">
										<option value="127.0.0.1">127.0.0.1</option>
										 
									</select>&nbsp;&nbsp;&nbsp;&nbsp;
								
								统计粒度：
									<select class="input_79x19" id="req_queryType" name="queryType" onchange="showChooseTime()">
										<option value="1">按天统计</option>
										<option value="2">按周统计</option>
										<option value="3">按月统计</option>
										<option value="4">按年统计</option>
									</select>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td width=400>
							<div>
									<div id="cycletype_1">
									统计时间：<input type="text" class="input_79x19" id="date1" name="date1"  value="<%=DateTimeUtil.formatChar8(DateTimeUtil.getChar8()).trim() %>" readonly />
									</div>
									<div id="cycletype_2" style="display:none">
									统计时间：<input type="text" class="input_79x19" id="date3" value="<%=DateTimeUtil.formatChar8(DateTimeUtil.rollDate(DateTimeUtil.getChar8(),-6)).trim() %>" disabled />~<input type="text" class="input_79x19" id="date2" name="date2" value="<%=DateTimeUtil.formatChar8(DateTimeUtil.getChar8()).trim() %>" onchange="changeWeekDay()" readonly />
									</div>
									<dt id="cycletype_3" style="display:none">
									统计时间：<select id="req_statMonth_1" name="year1" class="input_79x19">
									<%
									int year=Integer.parseInt(DateTimeUtil.getChar8().substring(0,4));
									for(int i=0;i<8;i++){
										 
										 out.print("<option  value=\""+(year-i)+"\">"+(year-i)+"</option>");
									}
									%>
									
									
									</select>
									<select id="req_statMonth_2" name="month" class="input_79x19">
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
									</div>	
									<div id="cycletype_4" style="display:none">
									统计时间：<select class="input_79x19"  name="year">
									<% 
									for(int i=0;i<8;i++){
										 
										 out.print("<option  value=\""+(year-i)+"\">"+(year-i)+"</option>");
									}
									%>
									</select>
									</div>
							</td>
							<td>
							 
									<input type="submit" class="blue_mod_btn" value="搜 &nbsp;索" />
							</td>
							</tr>
							
							</table>
								 
							</div>
					</form>
						</div>
					<iframe id="monitor" name="monitor" src="" width="916px" height="410px" frameborder="0" scrolling="no"></iframe>
						<div class=" mt_10">
							
						</div>
						<div class=" mt_10 pb_200">
							
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