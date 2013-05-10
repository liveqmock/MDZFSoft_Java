<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
 
 <%@ include file="/common/taglibs.jsp"%>
<html>
	<head> 
		<%@ include file="/common/header.jsp"%> 
   <%@ include file="/plugins/highcharts.jsp"%>
 		<script type="text/javascript">

		var chartTitle='${chartTitle}';
		$(document).ready(function() {
			var chart = new Highcharts.Chart({
				chart: {
					renderTo: 'container1',
					defaultSeriesType: 'areaspline'
				},
				title: {
					text: '服务器(${saveIp})状态数据分析图'
				},
				legend: {
					layout: 'vertical',
					style: {
						position: 'absolute',
						bottom: 'auto',
						left: '150px',
						top: '100px'
					},
					borderWidth: 1,
					backgroundColor: '#FFFFFF'
				},
				xAxis: {
					categories: [${categories}],
					plotBands: [ ]
				},
				yAxis: {
					title: {
						text: chartTitle
					}
				},
				tooltip: {
					formatter: function() {
			                return '<b>'+ this.series.name +'</b><br/>'+
							this.x +': '+ this.y +' %';
					}
				},
				credits: {
					enabled: false
				},
				plotOptions: {
					areaspline: {
						fillOpacity: .5
					}
				},
				series: [{
					name: 'CPU占用率',
					data: [${ratioCpuData}]
				}, {
					name: '内存占用率',
					data: [${ratioMemoryData}]
				}, {
					name: '硬盘占用率',
					data: [${ratioHarddiskData}]
				}]
			});
			
			
		});
		</script>
		
	</head>
	<body> 
	<%
 int countType = request.getParameter("queryType")==null?0:Integer.parseInt(request.getParameter("queryType"));
 
 if(countType>0)
 { %>
		<div id="container1" style="width: 916px; height: 410px"></div>
		<%
	}
	else
	{
%>
您输入的查询条件无结果~
<%
	} %>

	</body>
</html>

 
