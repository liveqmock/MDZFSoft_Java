<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>视频浏览</title>
	<style type="text/css">
	body,td,th {
		font-size: 14px;
		line-height: 26px;
	}
	body {
		margin-left: 0px;
		margin-top: 0px;
		margin-right: 0px;
		margin-bottom: 0px;
	}
	p {
		margin-top: 5px;
		margin-right: 0px;
		margin-bottom: 0px;
		margin-left: 0px;
		padding-left: 10px;
	}
	</style>
	<script type="text/javascript" src="${ctx}/plugins/ckplayer/js/offlights.js"></script>
</head>
  
<body >
	<%
	//处理中文文件名问题
	String filePath=request.getParameter("filePath");
	filePath = java.net.URLDecoder.decode(filePath,"UTF-8");  
	%>
<div id="flashcontent"></div>
<div id="video" style="position:relative;z-index: 100;width:560px;height:400px;"><div id="a1"></div></div>

<script type="text/javascript">
//如果你不需要某项设置，可以直接删除，注意var flashvars的最后一个值后面不能有逗号
var flashvars={
	f:'<%=filePath%>',//视频地址
	a:'',//调用时的参数，只有当s>0的时候有效
	s:'0',//调用方式，0=普通方法（f=视频地址），1=网址形式,2=xml形式，3=swf形式(s>0时f=网址，配合a来完成对地址的组装)
	c:'0',//是否读取文本配置,0不是，1是
	x:'',//调用xml风格路径，为空的话将使用ckplayer.js的配置
	i:'',//初始图片地址
	d:'',//暂停时播放的广告，swf/图片,多个用竖线隔开，图片要加链接地址，没有的时候留空就行
	u:'',//暂停时如果是图片的话，加个链接地址
	l:'',//前置广告，swf/图片/视频，多个用竖线隔开，图片和视频要加链接地址
	r:'',//前置广告的链接地址，多个用竖线隔开，没有的留空
	t:'',//视频开始前播放swf/图片时的时间，多个用竖线隔开
	y:'',//这里是使用网址形式调用广告地址时使用，前提是要设置l的值为空
	z:'',//缓冲广告，只能放一个，swf格式
	e:'2',//视频结束后的动作，0是调用js函数，1是循环播放，2是暂停播放并且不调用广告，3是调用视频推荐列表的插件，4是清除视频流并调用js功能和1差不多，5是暂停播放并且调用暂停广告
	v:'80',//默认音量，0-100之间
	p:'1',//视频默认0是暂停，1是播放
	h:'3',//播放http视频流时采用何种拖动方法，=0不使用任意拖动，=1是使用按关键帧，=2是按时间点，=3是自动判断按什么(如果视频格式是.mp4就按关键帧，.flv就按关键时间)，=4也是自动判断(只要包含字符mp4就按mp4来，只要包含字符flv就按flv来)
	q:'',//视频流拖动时参考函数，默认是start
	m:'0',//默认是否采用点击播放按钮后再加载视频，0不是，1是,设置成1时不要有前置广告
	o:'',//当m=1时，可以设置视频的时间，单位，秒
	w:'',//当m=1时，可以设置视频的总字节数
	g:'',//视频直接g秒开始播放
	j:'',//视频提前j秒结束
	k:'',//提示点时间，如 30|60鼠标经过进度栏30秒，60秒会提示n指定的相应的文字
	n:'',//提示点文字，跟k配合使用，如 提示点1|提示点2
	b:'1'
	};
	var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always'};//这里定义播放器的其它参数如背景色（跟flashvars中的b不同），是否支持全屏，是否支持交互
	var attributes={id:'ckplayer_a1',name:'ckplayer_a1',menu:'false'};
	//下面一行是调用播放器了，括号里的参数含义：（播放器文件，要显示在的div容器，宽，高，需要flash的版本，当用户没有该版本的提示，加载初始化参数，加载设置参数如背景，加载attributes参数，主要用来设置播放器的id）
	swfobject.embedSWF('${ctx}/plugins/ckplayer/ckplayer/ckplayer.swf', 'a1', '560', '400', '10.0.0','${ctx}/plugins/ckplayer/ckplayer/expressInstall.swf', flashvars, params, attributes); //播放器地址，容器id，宽，高，需要flash插件的版本，flashvars,params,attributes	
	//CKobject.embedSWF('${ctx}/plugins/ckplayer/ckplayer/ckplayer.swf','a1','ckplayer_a1','560','400',flashvars,params);
	//调用播放器结束
</script>

  </body>
</html>
