package com.njmd.zfms.web.entity.file;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the FILE_TYPE_INFO database table.
 * 
 */
@Entity
@Table(name = "FILE_TYPE_INFO")
public class FileTypeInfo implements Serializable
{
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TYPE_ID")
	private Long typeId;

	@Column(name = "TYPE_NAME")
	private String typeName;

	@Column(name = "VALID_TIME")
	private Long validTime;

	// bi-directional many-to-one association to FileUploadInfo
	@OneToMany(mappedBy = "fileTypeInfo")
	private List<FileUploadInfo> fileUploadInfos;

	public FileTypeInfo()
	{
	}

	public Long getTypeId()
	{
		return this.typeId;
	}

	public void setTypeId(Long typeId)
	{
		this.typeId = typeId;
	}

	public String getTypeName()
	{
		return this.typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	public Long getValidTime()
	{
		return this.validTime;
	}

	public void setValidTime(Long validTime)
	{
		this.validTime = validTime;
	}

	public List<FileUploadInfo> getFileUploadInfos()
	{
		return this.fileUploadInfos;
	}

	public void setFileUploadInfos(List<FileUploadInfo> fileUploadInfos)
	{
		this.fileUploadInfos = fileUploadInfos;
	}

}