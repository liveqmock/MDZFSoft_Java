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
 	                text: '${sysCorp.corpName} 上传文件统计图'
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
 	                    return '<b>'+ this.series.name +'</b><br/>'+
 	                       '于'+ this.x +' 上传文件 '+ this.y +'个';
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
</c:forEach>  ]
 	        });
 	    });

 
 
		</script>
		
		<style type="text/css">
		body {
	background-color: #FFFFFF;
} 
		</style>
	</head>
	<body >
	<div id="container1" style="width: 916px; height: 410px"></div>
	<div style="overflow: auto; height: 510px">
		<table cellspacing="1" class="tablesorter" width="100%">
			<thead>
				<tr align="center">
					<th colspan=2></th>
					<c:forEach items="${corpList}" var="item">
						<th>${item.corpName}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${categoriesList}" var="categoriesItem">
					<tr align="center">
						<td rowspan="${fn:length(fileTypeInfoList)}">${categoriesItem}</td>
						<c:forEach items="${fileTypeInfoList}" var="fileTypeInfoItem" varStatus="fileTypeInfoVarStatus">
							<c:if test="${fileTypeInfoVarStatus.index>1}">
								<tr>
							</c:if>
							<td>${fileTypeInfoItem.typeName}</td>
							<c:forEach items="${corpList}" var="item">
								<c:set var="key" value="${item.corpId}_${fileTypeInfoItem.typeId}_${categoriesItem}"></c:set>
								<c:if test="${fileTypeInfoItem.typeId==null}">
									<c:set var="key" value="${item.corpId}_null_${categoriesItem}"></c:set>
								</c:if>
								<td>
									<c:if test="${detialData[key]==null}">0</c:if>
									<c:if test="${detialData[key]!=null}">${detialData[key]}</c:if>
								</td>
							</c:forEach>
							<c:if test="${fileTypeInfoVarStatus.index!=(fn:length(fileTypeInfoList)-1)}">
					</tr>
					</c:if>
				</c:forEach>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>

 
