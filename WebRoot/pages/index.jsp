<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/header.jsp"%>
</head>

<frameset rows="58,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="${ctx}/pages/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame"/>
  <frameset cols="169,7,*" frameborder="no" border="0" framespacing="0" id="mainFrm">
    <frame src="${ctx}/menu" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame"/>
    <frame src="${ctx}/pages/hidden.html" name="leftFrame" scrolling="No" noresize="noresize" />
    <frame src="${ctx}/map" name="mainFrame" id="mainFrame"/>
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes>
</html>
