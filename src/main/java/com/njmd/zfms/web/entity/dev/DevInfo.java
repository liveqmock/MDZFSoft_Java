package com.njmd.zfms.web.entity.dev;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.njmd.framework.entity.AuditableEntity;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLogin;

/**
 * The persistent class for the DEV_INFO database table.
 * 
 */
@Entity
@Table(name = "DEV_INFO")
public class DevInfo extends AuditableEntity implements Serializable
{
	private static final Long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="DEVINFO_GENERATOR",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="DEVINFO_GENERATOR",sequenceName="DEVINFO_SEQUENCE",allocationSize=1)
	@Column(name = "DEV_ID")
	private Long devId;

	@Column(name = "DEV_NO")
	private String devNo;

	// @Column(name = "DEV_USER_ID")
	// private Long devUserId;
	//
	// @Column(name = "DEV_USER_ID")
	// private Long devUserId;

	@ManyToOne
	@JoinColumn(name = "DEV_USER_ID")
	private SysLogin devUserInfo;

	@ManyToOne
	@JoinColumn(name = "CORP_ID")
	private SysCorp sysCorp;

	// bi-directional many-to-one association to DevFacturerInfo
	@ManyToOne
	@JoinColumn(name = "DEV_FACTURER_ID")
	private DevFacturerInfo devFacturerInfo;

	// bi-directional many-to-one association to DevTypeInfo
	@ManyToOne
	@JoinColumn(name = "DEV_TYPE_ID")
	private DevTypeInfo devTypeInfo;

	public DevInfo()
	{
	}

	public Long getDevId()
	{
		return this.devId;
	}

	public void setDevId(Long devId)
	{
		this.devId = devId;
	}

	public String getDevNo()
	{
		return this.devNo;
	}

	public void setDevNo(String devNo)
	{
		this.devNo = devNo;
	}

	/*
	 * public Long getDevUserId() { return this.devUserId; }
	 * 
	 * public void setDevUserId(Long devUserId) { this.devUserId = devUserId; }
	 */

	public DevFacturerInfo getDevFacturerInfo()
	{
		return this.devFacturerInfo;
	}

	public void setDevFacturerInfo(DevFacturerInfo devFacturerInfo)
	{
		this.devFacturerInfo = devFacturerInfo;
	}

	public DevTypeInfo getDevTypeInfo()
	{
		return this.devTypeInfo;
	}

	public void setDevTypeInfo(DevTypeInfo devTypeInfo)
	{
		this.devTypeInfo = devTypeInfo;
	}

	public SysLogin getDevUserInfo()
	{
		return devUserInfo;
	}

	public void setDevUserInfo(SysLogin devUserInfo)
	{
		this.devUserInfo = devUserInfo;
	}

	public SysCorp getSysCorp()
	{
		return sysCorp;
	}

	public void setSysCorp(SysCorp sysCorp)
	{
		this.sysCorp = sysCorp;
	}

}