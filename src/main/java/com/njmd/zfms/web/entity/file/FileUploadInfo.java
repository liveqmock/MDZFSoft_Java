package com.njmd.zfms.web.entity.file;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLogin;

/**
 * The persistent class for the FILE_UPLOAD_INFO database table.
 * 
 */
@Entity
@Table(name = "FILE_UPLOAD_INFO")
public class FileUploadInfo implements Serializable
{
	private static final Long serialVersionUID = 1L;

	/** 文件状态-有效 */
	public static final String FILE_STATUS_VALID = "A";

	/** 文件状态-无效 */
	public static final String FILE_STATUS_INVALID = "U";

	/** 文件状态-过期 */
	public static final String FILE_STATUS_EXPIRED = "F";

	/** 文件状态-未剪辑 */
	public static final String FILE_STATUS_UNCLIP = "C";

	/** 文件状态-剪辑完成 */
	public static final String FILE_STATUS_CLIPING = "I";

	/** 文件状态-剪辑完成 */
	public static final String FILE_STATUS_WAIT_DELETE = "W";

	/** 文件格式-图片 */
	public static final Long FILE_TYPE_IMAGE = 1l;
	/** 文件格式-视频 */
	public static final Long FILE_TYPE_VIDEO = 2l;
	/** 文件格式-音频 */
	public static final Long FILE_TYPE_AUDIO = 3l;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FILE_ID")
	private Long fileId;

	@Column(name = "FILE_CONTEXT_PATH")
	private String fileContextPath;

	@Column(name = "FILE_CREATE_TIME")
	private String fileCreateTime;

	// @Column(name = "FILE_EDIT_ID")
	// private Long fileEditId;

	@Column(name = "FILE_IMPORTANCE")
	private Long fileImportance;

	@Column(name = "FILE_UPLOAD_NAME")
	private String fileUploadName;

	@Column(name = "FILE_SAVE_PATH")
	private String fileSavePath;

	@Column(name = "FILE_PLAY_PATH")
	private String filePlayPath;

	@Column(name = "FILE_REMARK")
	private String fileRemark;

	@Column(name = "FILE_SHOW_PATH")
	private String fileShowPath;

	@Column(name = "FILE_STATUS")
	private String fileStatus;

	@Column(name = "FILE_RECORD_TIME")
	private String fileRecordTime;

	@Column(name = "FILE_TYPE")
	private Long fileType;

	@Column(name = "FILE_UPLOAD_TIME")
	private String fileUploadTime;

	@Column(name = "POLICE_CODE")
	private String policeCode;

	@Column(name = "POLICE_COST_TIME")
	private Long policeCostTime;

	@Column(name = "POLICE_DESC")
	private String policeDesc;

	@Column(name = "POLICE_TIME")
	private String policeTime;

	// @Column(name = "UPLOAD_CORP_ID")
	// private Long uploadCorpId;

	// @Column(name = "UPLOAD_USER_ID")
	// private Long uploadUserId;

	@Column(name = "UPLOAD_USER_IP")
	private String uploadUserIp;

	@Column(name = "DELETE_BY")
	private Long deleteBy;

	@Column(name = "DELETE_TIME")
	private String deleteTime;

	// bi-directional many-to-one association to FileTypeInfo
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private FileTypeInfo fileTypeInfo;

	@ManyToOne
	@JoinColumn(name = "FILE_EDIT_ID")
	private SysLogin editUserInfo;

	@ManyToOne
	@JoinColumn(name = "UPLOAD_USER_ID")
	private SysLogin uploadUserInfo;

	@ManyToOne
	@JoinColumn(name = "UPLOAD_CORP_ID")
	private SysCorp uploadCorpInfo;

	@Transient
	private String fileTypeDesc;
	@Transient
	private String fileStatusDesc;

	public FileUploadInfo()
	{
	}

	public Long getFileId()
	{
		return this.fileId;
	}

	public void setFileId(Long fileId)
	{
		this.fileId = fileId;
	}

	public String getFileContextPath()
	{
		return this.fileContextPath;
	}

	public void setFileContextPath(String fileContextPath)
	{
		this.fileContextPath = fileContextPath;
	}

	public String getFileCreateTime()
	{
		return this.fileCreateTime;
	}

	public void setFileCreateTime(String fileCreateTime)
	{
		this.fileCreateTime = fileCreateTime;
	}

	/*
	 * public Long getFileEditId() { return this.fileEditId; }
	 * 
	 * public void setFileEditId(Long fileEditId) { this.fileEditId =
	 * fileEditId; }
	 */
	public Long getFileImportance()
	{
		return this.fileImportance;
	}

	public void setFileImportance(Long fileImportance)
	{
		this.fileImportance = fileImportance;
	}

	public String getFileUploadName()
	{
		return this.fileUploadName;
	}

	public void setFileUploadName(String fileUploadName)
	{
		this.fileUploadName = fileUploadName;
	}

	public String getFileSavePath()
	{
		return this.fileSavePath;
	}

	public void setFileSavePath(String fileSavePath)
	{
		this.fileSavePath = fileSavePath;
	}

	public String getFilePlayPath()
	{
		return this.filePlayPath;
	}

	public void setFilePlayPath(String filePlayPath)
	{
		this.filePlayPath = filePlayPath;
	}

	public String getFileRemark()
	{
		return this.fileRemark;
	}

	public void setFileRemark(String fileRemark)
	{
		this.fileRemark = fileRemark;
	}

	public String getFileShowPath()
	{
		return this.fileShowPath;
	}

	public void setFileShowPath(String fileShowPath)
	{
		this.fileShowPath = fileShowPath;
	}

	public String getFileStatus()
	{
		return this.fileStatus;
	}

	public void setFileStatus(String fileStatus)
	{
		this.fileStatus = fileStatus;
	}

	public String getFileRecordTime()
	{
		return this.fileRecordTime;
	}

	public void setFileRecordTime(String fileRecordTime)
	{
		this.fileRecordTime = fileRecordTime;
	}

	public Long getFileType()
	{
		return this.fileType;
	}

	public void setFileType(Long fileType)
	{
		this.fileType = fileType;
	}

	public String getFileUploadTime()
	{
		return this.fileUploadTime;
	}

	public void setFileUploadTime(String fileUploadTime)
	{
		this.fileUploadTime = fileUploadTime;
	}

	public String getPoliceCode()
	{
		return this.policeCode;
	}

	public void setPoliceCode(String policeCode)
	{
		this.policeCode = policeCode;
	}

	public Long getPoliceCostTime()
	{
		return this.policeCostTime;
	}

	public void setPoliceCostTime(Long policeCostTime)
	{
		this.policeCostTime = policeCostTime;
	}

	public String getPoliceDesc()
	{
		return this.policeDesc;
	}

	public void setPoliceDesc(String policeDesc)
	{
		this.policeDesc = policeDesc;
	}

	public String getPoliceTime()
	{
		return this.policeTime;
	}

	public void setPoliceTime(String policeTime)
	{
		this.policeTime = policeTime;
	}

	/*
	 * public Long getUploadCorpId() { return this.uploadCorpId; }
	 * 
	 * public void setUploadCorpId(Long uploadCorpId) { this.uploadCorpId =
	 * uploadCorpId; }
	 */

	/*
	 * public Long getUploadUserId() { return this.uploadUserId; }
	 * 
	 * public void setUploadUserId(Long uploadUserId) { this.uploadUserId =
	 * uploadUserId; }
	 */

	public String getUploadUserIp()
	{
		return this.uploadUserIp;
	}

	public void setUploadUserIp(String uploadUserIp)
	{
		this.uploadUserIp = uploadUserIp;
	}

	public FileTypeInfo getFileTypeInfo()
	{
		return this.fileTypeInfo;
	}

	public void setFileTypeInfo(FileTypeInfo fileTypeInfo)
	{
		this.fileTypeInfo = fileTypeInfo;
	}

	public SysLogin getEditUserInfo()
	{
		return editUserInfo;
	}

	public void setEditUserInfo(SysLogin editUserInfo)
	{
		this.editUserInfo = editUserInfo;
	}

	public SysLogin getUploadUserInfo()
	{
		return uploadUserInfo;
	}

	public void setUploadUserInfo(SysLogin uploadUserInfo)
	{
		this.uploadUserInfo = uploadUserInfo;
	}

	public SysCorp getUploadCorpInfo()
	{
		return uploadCorpInfo;
	}

	public void setUploadCorpInfo(SysCorp uploadCorpInfo)
	{
		this.uploadCorpInfo = uploadCorpInfo;
	}

	public Long getDeleteBy()
	{
		return deleteBy;
	}

	public void setDeleteBy(Long deleteBy)
	{
		this.deleteBy = deleteBy;
	}

	public String getDeleteTime()
	{
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime)
	{
		this.deleteTime = deleteTime;
	}

	public String getFileTypeDesc()
	{
		if (FileUploadInfo.FILE_TYPE_IMAGE.equals(this.fileType))
			return "图片";
		else if (FileUploadInfo.FILE_TYPE_AUDIO.equals(this.fileType))
			return "音频";
		else if (FileUploadInfo.FILE_TYPE_VIDEO.equals(this.fileType))
			return "视频";
		else
			return "";
	}

	public String getFileStatusDesc()
	{
		if (FileUploadInfo.FILE_STATUS_VALID.equals(this.fileStatus))
			return "有效";
		else if (FileUploadInfo.FILE_STATUS_INVALID.equals(this.fileStatus) || FileUploadInfo.FILE_STATUS_WAIT_DELETE.equals(this.fileStatus))
			return "无效";
		else if (FileUploadInfo.FILE_STATUS_EXPIRED.equals(this.fileStatus))
			return "过期";
		else if (FileUploadInfo.FILE_STATUS_UNCLIP.equals(this.fileStatus))
			return "未剪辑";
		else if (FileUploadInfo.FILE_STATUS_CLIPING.equals(this.fileStatus))
			return "剪辑中";
		return "";
	}
}