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

/**
 * The persistent class for the FILE_UPLOAD_INFO database table.
 * 
 */
@Entity
@Table(name = "FILE_UPLOAD_INFO")
public class FileUploadInfo implements Serializable
{
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UPLOAD_ID")
	private Long uploadId;

	@Column(name = "CONTEXT_PATH")
	private String contextPath;

	@Column(name = "CORP_ID")
	private Long corpId;

	@Column(name = "CORP_NAME")
	private String corpName;

	@Column(name = "COST_TIME")
	private Long costTime;

	@Column(name = "EDIT_ID")
	private Long editId;

	@Column(name = "FILE_CREATE_TIME")
	private String fileCreateTime;

	@Column(name = "FILE_IMPORTANCE")
	private Long fileImportance;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "FILE_PATH")
	private String filePath;

	@Column(name = "FILE_REMARK")
	private String fileRemark;

	@Column(name = "FILE_STATUS")
	private String fileStatus;

	@Column(name = "FILE_TYPE")
	private Long fileType;

	@Column(name = "PARENT_CORP_ID")
	private Long parentCorpId;

	@Column(name = "PLAY_PATH")
	private String playPath;

	@Column(name = "POLICE_CODE")
	private String policeCode;

	@Column(name = "POLICE_DESC")
	private String policeDesc;

	@Column(name = "POLICE_TIME")
	private String policeTime;

	@Column(name = "SHOW_PATH")
	private String showPath;

	@Column(name = "TAKE_TIME")
	private String takeTime;

	@Column(name = "UPLOAD_TIME")
	private String uploadTime;

	@Column(name = "UPLOAD_USER_ID")
	private Long uploadUserId;

	@Column(name = "UPLOAD_USER_IP")
	private String uploadUserIp;

	// bi-directional many-to-one association to FileTypeInfo
	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	private FileTypeInfo fileTypeInfo;

	public FileUploadInfo()
	{
	}

	public Long getUploadId()
	{
		return this.uploadId;
	}

	public void setUploadId(Long uploadId)
	{
		this.uploadId = uploadId;
	}

	public String getContextPath()
	{
		return this.contextPath;
	}

	public void setContextPath(String contextPath)
	{
		this.contextPath = contextPath;
	}

	public Long getCorpId()
	{
		return this.corpId;
	}

	public void setCorpId(Long corpId)
	{
		this.corpId = corpId;
	}

	public String getCorpName()
	{
		return this.corpName;
	}

	public void setCorpName(String corpName)
	{
		this.corpName = corpName;
	}

	public Long getCostTime()
	{
		return this.costTime;
	}

	public void setCostTime(Long costTime)
	{
		this.costTime = costTime;
	}

	public Long getEditId()
	{
		return this.editId;
	}

	public void setEditId(Long editId)
	{
		this.editId = editId;
	}

	public String getFileCreateTime()
	{
		return this.fileCreateTime;
	}

	public void setFileCreateTime(String fileCreateTime)
	{
		this.fileCreateTime = fileCreateTime;
	}

	public Long getFileImportance()
	{
		return this.fileImportance;
	}

	public void setFileImportance(Long fileImportance)
	{
		this.fileImportance = fileImportance;
	}

	public String getFileName()
	{
		return this.fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFilePath()
	{
		return this.filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getFileRemark()
	{
		return this.fileRemark;
	}

	public void setFileRemark(String fileRemark)
	{
		this.fileRemark = fileRemark;
	}

	public String getFileStatus()
	{
		return this.fileStatus;
	}

	public void setFileStatus(String fileStatus)
	{
		this.fileStatus = fileStatus;
	}

	public Long getFileType()
	{
		return this.fileType;
	}

	public void setFileType(Long fileType)
	{
		this.fileType = fileType;
	}

	public Long getParentCorpId()
	{
		return this.parentCorpId;
	}

	public void setParentCorpId(Long parentCorpId)
	{
		this.parentCorpId = parentCorpId;
	}

	public String getPlayPath()
	{
		return this.playPath;
	}

	public void setPlayPath(String playPath)
	{
		this.playPath = playPath;
	}

	public String getPoliceCode()
	{
		return this.policeCode;
	}

	public void setPoliceCode(String policeCode)
	{
		this.policeCode = policeCode;
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

	public String getShowPath()
	{
		return this.showPath;
	}

	public void setShowPath(String showPath)
	{
		this.showPath = showPath;
	}

	public String getTakeTime()
	{
		return this.takeTime;
	}

	public void setTakeTime(String takeTime)
	{
		this.takeTime = takeTime;
	}

	public String getUploadTime()
	{
		return this.uploadTime;
	}

	public void setUploadTime(String uploadTime)
	{
		this.uploadTime = uploadTime;
	}

	public Long getUploadUserId()
	{
		return this.uploadUserId;
	}

	public void setUploadUserId(Long uploadUserId)
	{
		this.uploadUserId = uploadUserId;
	}

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

}