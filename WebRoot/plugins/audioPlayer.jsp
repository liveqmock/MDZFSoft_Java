<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>媒体播放器</title>
</head>
<body onunload="javascript:alert('1');">
	<iframe style="position:absolute;width:98%;height:98%;_filter:alpha(opacity=0);opacity=0;border-style:none;z-index:-1;"></iframe>
	<OBJECT id="audioPlayerObj" type="application/x-oleobject" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
		<PARAM NAME="URL" VALUE="${filePath}">
		<PARAM NAME="rate" VALUE="1">
		<PARAM NAME="balance" VALUE="0">
		<PARAM NAME="currentPosition" VALUE="0">
		<PARAM NAME="defaultFrame" VALUE="">
		<PARAM NAME="playCount" VALUE="1">
		<PARAM NAME="autoStart" VALUE="-1">
		<PARAM NAME="currentMarker" VALUE="0">
		<PARAM NAME="invokeURLs" VALUE="-1">
		<PARAM NAME="baseURL" VALUE="">
		<PARAM NAME="volume" VALUE="80">
		<PARAM NAME="mute" VALUE="0">
		<PARAM NAME="uiMode" VALUE="full">
		<PARAM NAME="stretchToFit" VALUE="-1">
		<PARAM NAME="windowlessVideo" VALUE="0">
		<PARAM NAME="enabled" VALUE="-1">
		<PARAM NAME="enableContextMenu" VALUE="0">
		<PARAM NAME="fullScreen" VALUE="0">
		<PARAM NAME="SAMIStyle" VALUE="">
		<PARAM NAME="SAMILang" VALUE="">
		<PARAM NAME="SAMIFilename" VALUE="">
		<PARAM NAME="captioningID" VALUE="">
		<PARAM NAME="enableErrorDialogs" VALUE="0">
		<PARAM NAME="_cx" VALUE="10372">
		<PARAM NAME="_cy" VALUE="1693">
		<!--为了支持FF 开始-->
		<embed type="application/x-mplayer2"></embed>
		<!--为了支持FF 结束-->
	</OBJECT>
</body>