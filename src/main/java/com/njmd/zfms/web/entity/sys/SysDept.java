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

import com.njmd.zfms.web.entity.AuditableEntity;

/**
 * The persistent class for the SYS_DEPT database table.
 * 
 */
@Entity
@Table(name = "SYS_DEPT")
public class SysDept extends AuditableEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Long deptId;

	private Long corpId;

	private String deptName;

	private Integer status;

	// bi-directional many-to-one association to SysLogin

	private List<SysLogin> sysLogins;

	public SysDept()
	{
	}

	@Id
	@SequenceGenerator(name = "DEPT_ID_GENERATOR", sequenceName = "SEQ_SYS_DEPT", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPT_ID_GENERATOR")
	@Column(name = "DEPT_ID")
	public Long getDeptId()
	{
		return deptId;
	}

	public void setDeptId(Long deptId)
	{
		this.deptId = deptId;
	}

	@Column(name = "CORP_ID")
	public Long getCorpId()
	{
		return corpId;
	}

	public void setCorpId(Long corpId)
	{
		this.corpId = corpId;
	}

	@Column(name = "DEPT_NAME")
	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	@Column(name = "STATUS")
	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysDept")
	public List<SysLogin> getSysLogins()
	{
		return sysLogins;
	}

	public void setSysLogins(List<SysLogin> sysLogins)
	{
		this.sysLogins = sysLogins;
	}

}