<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- USB to Local -->
<c:if test="${param['uploadType']=='1'}">
	<div>
		<ul class="mt_10">
			<label>选择盘符：</label>
			<c:forTokens items="${param['usbDrives']}" delims="*" var="usbDrive">
				<input type=radio name=r_letter value="${usbDrive}" onclick="selectDrive('${usbDrive}')">(${usbDrive}:)盘
			</c:forTokens>
		</ul>
		<ul id="localSaveDiv" class="mt_10">
			<label>采&nbsp;集&nbsp;人：</label>
			<input type="text" id="editName" name="editName" value="133" class="input_79x19" readonly onclick="userChoose()" />
		</ul>
		<ul class="mt_10">
			<label>上传名称：</label>
			<input type="text" style="width:280px" id="uploadNameValue1" class="input_79x19" name="uploadNameValue" value="" dataType="LimitB" min="4" max="80" msg="上传名称【4-80】个字符" />
		</ul>
		<ul class="mt_10">
			<a href="#" class="blue_mod_btn"  onclick="startUpload()">确定上传</a>
			<a href="${ctx}/pages/file_mgr/file_upload/index.jsp" class="green_mod_btn">重新选择</a>
		</ul>
		<ul>
			<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
			<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
		</ul>
	</div>
</c:if>
<!-- USB to Server -->
<c:if test="${param['uploadType']=='2'}">
	<table align="right">
	<tr valign="top"><td>
		<ul class="mt_10">
			<label>选择盘符：</label>
			<c:forTokens items="${param['usbDrives']}" delims="*" var="usbDrive">
				<input type=radio name=r_letter value="${usbDrive}" onclick="selectDrive('${usbDrive}')">(${usbDrive}:)盘
			</c:forTokens>
		</ul>
		<div id="localSaveDiv" style="display:none">
		&nbsp;&nbsp;&nbsp;&nbsp;<label>选择文件夹：</label>
		<input type="text" id="localSaveDir" readonly onclick="selectLocalSaveDir()"/>
		<input type="button" class="button" id="localSaveButton" onclick="copyLocalFile()" value="确认"/>
		<br/>
		</div>
		<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>
		<div>
		<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
		<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
		</div>
		</label>
	</td></tr>
	</table>
</c:if>
<!-- Local to Server -->
<c:if test="${param['uploadType']=='3'}">
	<table>
		<tr valign="top">
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;<label>选择文件夹(上传至服务器)：</label> <input type="text" id="localUploadDir" readonly onclick="selectLocalSaveDir2()" class="input_79x19" /> <br />
			</td>
		</tr>
		<tr valign="top" id="localUploadDiv" style="display:none"><td>
		<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>采&nbsp;集&nbsp;人：</label>
		<input type="text" id="editName" name="editName" value="133" class="input_79x19" readonly onclick="userChoose()" /><br/><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;<label>上传名称：</label>
		<input type="text" style="width:280px" id="uploadNameValue1" class="input_79x19" name="uploadNameValue" value="" dataType="LimitB" min="4" max="80" msg="上传名称【4-80】个字符" />
		<br/>
		<br/>&nbsp;&nbsp;&nbsp;&nbsp;<a class="blue_mod_btn" href="#" onclick="startUpload()">开始上传</a>
		<br/>&nbsp;&nbsp;&nbsp;&nbsp;<label>
		<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
		<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
		<!--/div-->
		</label>
	</td></tr>
	</table>
</c:if>