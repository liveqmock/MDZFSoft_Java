package com.njmd.zfms.web.entity.sys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the SYS_ROLE_PERMISSION database table.
 * 
 */
@Entity
@Table(name = "SYS_ROLE_PERMISSION")
public class SysRolePermission implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	// bi-directional many-to-one association to SysPermission
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERMISSION_ID")
	private SysPermission sysPermission;

	// bi-directional many-to-one association to SysRole
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private SysRole sysRole;

	public SysRolePermission()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public SysPermission getSysPermission()
	{
		return sysPermission;
	}

	public void setSysPermission(SysPermission sysPermission)
	{
		this.sysPermission = sysPermission;
	}

	public SysRole getSysRole()
	{
		return sysRole;
	}

	public void setSysRole(SysRole sysRole)
	{
		this.sysRole = sysRole;
	}

}