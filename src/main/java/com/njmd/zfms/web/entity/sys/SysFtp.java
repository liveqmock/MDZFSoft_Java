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

import com.njmd.framework.entity.AuditableEntity;

/**
 * The persistent class for the SYS_FTP database table.
 * 
 */
@Entity
@Table(name = "SYS_FTP")
public class SysFtp extends AuditableEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="SYSFTP_GENERATOR",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="SYSFTP_GENERATOR",sequenceName="SYSFTP_SEQUENCE",allocationSize=1)
	@Column(name = "FTP_ID")
	private Long ftpId;

	@Column(name="SERVER_NAME")
	private String serverName;
	
	@Column(name = "FTP_DESC")
	private String ftpDesc;

	@Column(name = "FTP_IP")
	private String ftpIp;

	@Column(name = "FTP_PORT")
	private String ftpPort="21";

	@Column(name = "FTP_USER")
	private String ftpUser;

	@Column(name = "FTP_PWD")
	private String ftpPwd;

	@Column(name = "FILE_ROOT_URL")
	private String fileRootUrl;

	@Column(name = "STATUS")
	private Integer status;

	// bi-directional many-to-one association to SysLogin
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysFtp")
	private List<SysCorp> sysCorps;

	public SysFtp()
	{
	}


	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
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

	public String getFtpPwd()
	{
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd)
	{
		this.ftpPwd = ftpPwd;
	}

	public String getFileRootUrl()
	{
		return fileRootUrl;
	}

	public void setFileRootUrl(String fileRootUrl)
	{
		this.fileRootUrl = fileRootUrl;
	}

	public Long getFtpId() {
		return ftpId;
	}

	public void setFtpId(Long ftpId) {
		this.ftpId = ftpId;
	}

	public String getFtpDesc() {
		return ftpDesc;
	}

	public void setFtpDesc(String ftpDesc) {
		this.ftpDesc = ftpDesc;
	}

	public List<SysCorp> getSysCorps() {
		return sysCorps;
	}

	public void setSysCorps(List<SysCorp> sysCorps) {
		this.sysCorps = sysCorps;
	}
	
	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}