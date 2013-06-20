<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
 
 <%@ include file="/common/taglibs.jsp"%>
 <%@ include file="/plugins/tablesorter.jsp"%>
<html>
	<head> 
	<script type="text/javascript" src="${ctx}/plugins/jquery/jquery-1.8.0.min.js"></script>
   	 <%@ include file="/plugins/highcharts.jsp"%>
 		<script type="text/javascript">

 		$(function () {
 	        $('#container1').highcharts({
 	            chart: {
 	                type: 'column'
 	            },
 	            title: {
 	                text: '${uploadUser.userName} 上传文件统计图'
 	            }, 
 	            xAxis: {
 	                categories: [${categories}]
 	            }, 
 	           yAxis: {
 	                title: {
 	                    text: '文件数量'
 	                }
 	            },
 	            tooltip: {
 	                enabled: true,
 	                formatter: function() {
 	                    return '<b>'+ this.series.name +'</b>:'+   this.y +'个';
 	                }
 	            },
 	            plotOptions: {
 	                line: {
 	                    dataLabels: {
 	                        enabled: true
 	                    },
 	                    enableMouseTracking: true
 	                }
 	            },
 	           series: [
<c:forEach items="${corpCategoriesMap }" var="mapItem" varStatus="status">  
{
	name: '${mapItem.key }',
	data: [${mapItem.value}]}   <c:if test="${status.index!=(fn:length(corpCategoriesMap)-1)}">,</c:if>
</c:forEach>  ],
				credits: { href: '',                        //隐藏右下角的highchart的图标和链接
				            text: ''
				        }
 	        });
 	    });

 
		</script>
		
		<style type="text/css">
		body {
	background-color: #FFFFFF;
} 
		</style>
	</head>
<body>
	<div id="container1" style="width: 916px; height: 410px;overflow: auto"></div>
	<div style="overflow: auto; height: 490px">
		<table cellspacing="1" class="tablesorter" width="100%">
			<thead>
				<tr align="center">
					<th></th>
					<c:forEach items="${categoriesList}" var="categoriesItem">
						<th>${categoriesItem}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${fileTypeInfoList}" var="fileTypeInfoItem" varStatus="fileTypeInfoVarStatus">
					<tr align="center">
						<td>${fileTypeInfoItem.typeName}</td>
						<c:forEach items="${categoriesList}" var="categoriesItem">
							<c:set var="key" value="${fileTypeInfoItem.typeId}_${categoriesItem}"></c:set>
							<c:if test="${fileTypeInfoItem.typeId==null}">
								<c:set var="key" value="null_${categoriesItem}"></c:set>
							</c:if>
							<td>
								<c:if test="${chartData[key]==null}">0</c:if>
								<c:if test="${chartData[key]!=null}">${chartData[key]}</c:if>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>

 
