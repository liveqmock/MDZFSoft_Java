package com.njmd.zfms.web.entity.sys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.njmd.zfms.web.entity.AuditableEntity;

/**
 * The persistent class for the SYS_CORP database table.
 * 
 */
@Entity
@Table(name = "SYS_CORP")
public class SysCorp extends AuditableEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CORP_ID")
	private Long corpId;

	@Column(name = "CORP_DESC")
	private String corpDesc;

	@Column(name = "CORP_NAME")
	private String corpName;

	@Column(name = "CORP_TYPE")
	private Integer corpType;

	@Column(name = "PARENT_CORP_ID")
	private Long parentCorpId = 0l;

	@Column(name = "FTP_IP")
	private String ftpIp;

	@Column(name = "FTP_PORT")
	private String ftpPort;

	@Column(name = "FTP_USER")
	private String ftpUser;

	@Column(name = "TREE_CODE")
	private String treeCode;

	@Column(name = "STATUS")
	private Integer status;

	// bi-directional many-to-one association to SysLogin
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysCorp")
	private List<SysLogin> sysLogins;

	/**** 非实体化属性 *********/
	// 上级单位名称
	@Transient
	private String parentCorpName = null;

	public SysCorp()
	{
	}

	public Long getCorpId()
	{
		return corpId;
	}

	public void setCorpId(Long corpId)
	{
		this.corpId = corpId;
	}

	public String getCorpDesc()
	{
		return corpDesc;
	}

	public void setCorpDesc(String corpDesc)
	{
		this.corpDesc = corpDesc;
	}

	public String getCorpName()
	{
		return corpName;
	}

	public void setCorpName(String corpName)
	{
		this.corpName = corpName;
	}

	public Integer getCorpType()
	{
		return corpType;
	}

	public void setCorpType(Integer corpType)
	{
		this.corpType = corpType;
	}

	public Long getParentCorpId()
	{
		return parentCorpId;
	}

	public void setParentCorpId(Long parentCorpId)
	{
		this.parentCorpId = parentCorpId;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public List<SysLogin> getSysLogins()
	{
		return sysLogins;
	}

	public void setSysLogins(List<SysLogin> sysLogins)
	{
		this.sysLogins = sysLogins;
	}

	public void setParentCorpName(String parentCorpName)
	{
		this.parentCorpName = parentCorpName;
	}

	public String getParentCorpName()
	{
		return parentCorpName;
	}

	public String getFtpIp()
	{
		return ftpIp;
	}

	public void setFtpIp(String ftpIp)
	{
		this.ftpIp = ftpIp;
	}

	public String getFtpPort()
	{
		return ftpPort;
	}

	public void setFtpPort(String ftpPort)
	{
		this.ftpPort = ftpPort;
	}

	public String getFtpUser()
	{
		return ftpUser;
	}

	public void setFtpUser(String ftpUser)
	{
		this.ftpUser = ftpUser;
	}

	public String getTreeCode()
	{
		return treeCode;
	}

	public void setTreeCode(String treeCode)
	{
		this.treeCode = treeCode;
	}
}