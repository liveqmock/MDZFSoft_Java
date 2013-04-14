<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
.page {width:100%;text-align:right;height:23px;margin-top:3px;}
.page ul {height:26px;padding:0;margin:0;color:#000;list-style:none;float:right;}
.page ul li{float:left;margin:0 5px 0 0;display:block;line-height:26px;}
.page ul li a {display:block;padding:1px 0 0 0;}
*html .page ul li a {display:block;padding:2px 0 0 0;}
.page ul li a.pre {background: #fff url(${ctx}/plugins/tablesorter/images/pager_pre.gif) no-repeat 0 0;width:14px;height:13px;font-size:0px;line-height:0px;margin:7px 0 0 0;}
.page ul li a.next {background: #fff url(${ctx}/plugins/tablesorter//images/pager_next.gif) no-repeat 0 0;width:14px;height:13px;font-size:0px;line-height:0px;margin:7px 0 0 0;}
.page ul li .num {height:16px;line-height:16px;padding:0 3px;font-size:12px;}
*html .page ul .bu {padding:3px 0 0 0;}
.page ul .inbu {padding:0px 0 0 0;}
*html .page ul .inbu {padding:5px 0 0 0;}
.page ul li a.go {background: #fff url(${ctx}/plugins/tablesorter/images/pager_go.gif) no-repeat 0 0;width:24px;height:23px;font-size:0px;line-height:0px;margin:4px 0 0 0;}
</style>
<div align="center" class="page">
<ul>
    <li>
    	<c:if test="${page.pageNo!=1}">
			<a href="javascript:jumpPage(1)">首页</a>
		</c:if>
		<c:if test="${page.pageNo==1}">
			<a href="javascript:jumpPage(1)"  disabled="disabled">首页</a>
		</c:if>
	</li>
    <li>
		<a href="javascript:jumpPage(${page.prePage})" class="pre" ></a>
    </li>
    <li>
    	<span  style="color:red">${page.pageNo}</span> / <span>${page.totalPages}</span>
    </li>
    <li>
		<a href="javascript:jumpPage(${page.nextPage})" class="next"></a>
    </li>
    <li>
    	<c:if test="${page.pageNo!=page.totalPages}">
			<a href="javascript:jumpPage(${page.totalPages})">末页</a>
		</c:if>
		<c:if test="${page.pageNo==page.totalPages}">
			<a href="javascript:jumpPage(${page.totalPages})" disabled="disabled">末页</a>
		</c:if>
    </li>
     <li>
		<select id="curPageSize" name="curPageSize" onchange="changePageSize()">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
			<option value="50">50</option>
		</select>
		条/页&nbsp;&nbsp;&nbsp;
		<script>
			document.getElementById("curPageSize").value='${page.pageSize}';
		</script>
    </li>
    <li class="bu"><span>转到<input type="text" class="num" style="width:26px;text-align: center" id="pageNum" value="${page.pageNo}"/>页</span></li>
    <li class="inbu">
    	<a href="javascript:jumpPage(document.getElementById('pageNum').value)" class="go" ></a>
    </li>
    <li>
		记录总数: <c:out value="${page.totalCount}"/> 
    </li>
</ul>
</div>