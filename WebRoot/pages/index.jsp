<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
</head>

<frameset rows="142,*" cols="*" frameborder="no" border="1" framespacing="0">
  <frame src="${ctx}/pages/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame"/>
  <frame src="${ctx}/pages/homepage.jsp" name="mainFrame" id="mainFrame"/>
</frameset>
<noframes><body>
</body>
</noframes>
</html>
