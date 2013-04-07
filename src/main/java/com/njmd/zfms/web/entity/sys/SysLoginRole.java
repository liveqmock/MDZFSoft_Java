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
 * The persistent class for the SYS_LOGIN_ROLE database table.
 * 
 */
@Entity
@Table(name = "SYS_LOGIN_ROLE")
public class SysLoginRole implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SYS_LOGIN_ROLE_ID_GENERATOR", sequenceName = "SEQ_SYS_LOGIN_ROLE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYS_LOGIN_ROLE_ID_GENERATOR")
	@Column(name = "ID")
	private Long id;

	// bi-directional many-to-one association to SysLogin
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOGIN_ID")
	private SysLogin sysLogin;

	// bi-directional many-to-one association to SysRole
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private SysRole sysRole;

	public SysLoginRole()
	{
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public SysLogin getSysLogin()
	{
		return this.sysLogin;
	}

	public void setSysLogin(SysLogin sysLogin)
	{
		this.sysLogin = sysLogin;
	}

	public SysRole getSysRole()
	{
		return this.sysRole;
	}

	public void setSysRole(SysRole sysRole)
	{
		this.sysRole = sysRole;
	}

}