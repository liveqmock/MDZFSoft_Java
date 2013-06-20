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
 	           colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', 
 	                   '#FFF263', '#6AF9C4','#058DF9', '#50B532', '#ED571B', '#D00F00', '#24CEE5'
 	                  ,'#058CC7', '#50C432', '#ED861B', '#D7DF00', '#20CBE5', '#6FE572', '#F29655' ],
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
	<body > 
					
	<c:if test="${fn:length(corpList)<=10}"> <div id="container1" style="width:916px; height: 410px"></div></c:if>
	
	<c:if test="${fn:length(corpList)>10}">
	<div style="overflow:auto;height:410px;width:916px">
	 <div id="container1" style="width:${fn:length(corpList)}0%;height:390px"></div>
	 </div>
	 </c:if>							
 <br>
	
	<div style="overflow:auto; height:490px">
	<c:if test="${fn:length(corpList)<=10}"> 
	<table cellspacing="1" class="tablesorter" width="98%" >
								</c:if>
    <c:if test="${fn:length(corpList)>10}"> 
	<table cellspacing="1" class="tablesorter" width="${fn:length(corpList)}0%" >
								</c:if> 
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

 
