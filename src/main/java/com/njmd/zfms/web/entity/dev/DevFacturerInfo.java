package com.njmd.zfms.web.entity.dev;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.njmd.framework.entity.AuditableEntity;

/**
 * The persistent class for the DEV_FACTURER_INFO database table.
 * 
 */
@Entity
@Table(name = "DEV_FACTURER_INFO")
public class DevFacturerInfo extends AuditableEntity implements Serializable
{
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEV_FACTURER_ID")
	private Long devFacturerId;

	private String address;

	private String contact;

	@Column(name = "DEV_FACTURER_NAME")
	private String devFacturerName;

	private String phone;

	// bi-directional many-to-one association to DevInfo
	@OneToMany(mappedBy = "devFacturerInfo")
	private List<DevInfo> devInfos;

	public DevFacturerInfo()
	{
	}

	public Long getDevFacturerId()
	{
		return this.devFacturerId;
	}

	public void setDevFacturerId(Long devFacturerId)
	{
		this.devFacturerId = devFacturerId;
	}

	public String getAddress()
	{
		return this.address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getContact()
	{
		return this.contact;
	}

	public void setContact(String contact)
	{
		this.contact = contact;
	}

	public String getDevFacturerName()
	{
		return this.devFacturerName;
	}

	public void setDevFacturerName(String devFacturerName)
	{
		this.devFacturerName = devFacturerName;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public List<DevInfo> getDevInfos()
	{
		return this.devInfos;
	}

	public void setDevInfos(List<DevInfo> devInfos)
	{
		this.devInfos = devInfos;
	}

}