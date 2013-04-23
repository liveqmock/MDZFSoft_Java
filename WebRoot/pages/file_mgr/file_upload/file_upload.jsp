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
	<div>
		<ul class="mt_10">
			<label>选择盘符：</label>
			<c:forTokens items="${param['usbDrives']}" delims="*" var="usbDrive">
				<input type=radio name=r_letter value="${usbDrive}" onclick="selectDrive('${usbDrive}')">(${usbDrive}:)盘
			</c:forTokens>
		</ul>
		<ul class="mt_10">
			<label>选择文件夹：</label>
			<input type="text" id="localSaveDir" readonly onclick="selectLocalSaveDir()"/>
		</ul>
		<ul class="mt_10">
			<a class="blue_mod_btn" href="#" onclick="copyLocalFile()">开始上传</a>
			<a href="${ctx}/pages/file_mgr/file_upload/index.jsp" class="green_mod_btn">重新选择</a>
		</ul>
		<ul class="mt_10">
			<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
			<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
		</ul>
	</div>
</c:if>
<!-- Local to Server -->
<c:if test="${param['uploadType']=='3'}">
	<div>
		<ul class="mt_10">
			<label>选择文件夹：</label><input type="text" id="localUploadDir" readonly onclick="selectLocalSaveDir2()" class="input_79x19" />
		</ul>
		<ul class="mt_10">
			<label>采&nbsp;集&nbsp;人：</label>
			<input type="text" id="editName" name="editName" value="133" class="input_79x19" readonly onclick="userChoose()" />
		</ul>
		<ul class="mt_10">
			<label>上传名称：</label>
			<input type="text" style="width:280px" id="uploadNameValue1" class="input_79x19" name="uploadNameValue" value="" dataType="LimitB" min="4" max="80" msg="上传名称【4-80】个字符" />
		</ul>
		<ul class="mt_10">
			<a class="blue_mod_btn" href="#" onclick="startUpload()">开始上传</a>
			<a href="${ctx}/pages/file_mgr/file_upload/index.jsp" class="green_mod_btn">重新选择</a>
		</ul>
		<ul>
			<iframe id="sc" src="" width="470px" height="200px" frameborder="0"></iframe>
			<iframe name="db" src="" width="0" height="0" frameborder="0"></iframe>
		</ul>
	</div>
</c:if>