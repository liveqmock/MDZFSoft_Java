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
@Table(name = "SYS_ROLE_ROOM")
public class SysRoleRoom implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SYS_ROLE_ROOM_ID_GENERATOR", sequenceName = "SEQ_SYS_ROLE_ROOM", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SYS_ROLE_ROOM_ID_GENERATOR")
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private SysRole sysRole;

	@Column(name = "CONTROLL_FLAG")
	private Integer controllFlag;

	public SysRoleRoom()
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

	public SysRole getSysRole()
	{
		return sysRole;
	}

	public void setSysRole(SysRole sysRole)
	{
		this.sysRole = sysRole;
	}

	public Integer getControllFlag()
	{
		return controllFlag;
	}

	public void setControllFlag(Integer controllFlag)
	{
		this.controllFlag = controllFlag;
	}

}