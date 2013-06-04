package com.njmd.zfms.web.entity.dev;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.njmd.framework.entity.AuditableEntity;

/**
 * The persistent class for the DEV_TYPE_INFO database table.
 * 
 */
@Entity
@Table(name = "DEV_TYPE_INFO")
public class DevTypeInfo extends AuditableEntity implements Serializable
{
	private static final Long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="DEVTYPEINFO_GENERATOR",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="DEVTYPEINFO_GENERATOR",sequenceName="DEVTYPEINFO_SEQUENCE",allocationSize=1)
	@Column(name = "DEV_TYPE_ID")
	private Long devTypeId;

	@Column(name = "DEV_TYPE_NAME")
	private String devTypeName;

	// bi-directional many-to-one association to DevInfo
	@OneToMany(mappedBy = "devTypeInfo")
	private List<DevInfo> devInfos;

	public DevTypeInfo()
	{
	}

	public Long getDevTypeId()
	{
		return this.devTypeId;
	}

	public void setDevTypeId(Long devTypeId)
	{
		this.devTypeId = devTypeId;
	}

	public String getDevTypeName()
	{
		return this.devTypeName;
	}

	public void setDevTypeName(String devTypeName)
	{
		this.devTypeName = devTypeName;
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