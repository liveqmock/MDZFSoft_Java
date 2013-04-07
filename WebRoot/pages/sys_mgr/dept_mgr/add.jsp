<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/plugins/jquery-validation.jsp"%>
	</head>
	<body>
		<div class="mianbaoxie">
			当前位置：<a href="#1">系统管理</a> > <a href="${ctx}/deptMgr">部门管理</a>  > 新增
		</div>
		<form:form id="mainForm" name="mainForm" method="POST" action="${ctx}/deptMgr/save"  modelAttribute="resultObject">
			<input type="hidden" name="status" id="status" value="1">
		<div class="tab_search" style="margin-top: 20px;">
		<div class="table_div">
			<table width="100%" class="table_border">
				<tr>
					<td class="title" width="100"></td>
					<td align="left">带<font color="red">&nbsp;*&nbsp;</font>为必填项</td>
				</tr>

				<tr>
					<td class="title" width="100">
						<font color="red">*&nbsp;</font>部门名称
					</td>
					<td>
						<form:input path="deptName" cssClass="form_input {required:true,maxlength:20}" size="20"/>
					</td>
				</tr>
				<tr>
					<td align="center" valign="middle" colspan="2">
						<input type="submit" class="form_botton" value="提  交" />&nbsp;&nbsp;
						<input type="button" class="form_botton" value="返  回" onclick="window.location='${ctx}/deptMgr'" />
					</td>					
				</tr>
			</table>	
			</div>
			</div>
		</form:form>
	</body>
	<script>
	$(function(){
		$('#mainForm').validate();
	});
	</script>
</html>