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

/**
 * The persistent class for the SYS_PERMISSION database table.
 * 
 */
@Entity
@Table(name = "SYS_PERMISSION")
public class SysPermission implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * 权限类型--1 菜单
	 */
	public static final long PERMISSION_TYPE_1 = 1;

	/**
	 * 权限类型--2 按钮
	 */
	public static final long PERMISSION_TYPE_2 = 2;

	@Id
	@SequenceGenerator(name = "PERMISSION_ID_GENERATOR", sequenceName = "SEQ_SYS_PERMISSION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_ID_GENERATOR")
	@Column(name = "PERMISSION_ID")
	private Long permissionId;

	@Column(name = "PARENT_PERMISSION_ID")
	private Long parentPermissionId;

	@Column(name = "PERMISSION_NAME")
	private String permissionName;

	@Column(name = "PERMISSION_SORT")
	private Integer permissionSort;

	@Column(name = "PERMISSION_TYPE")
	private Integer permissionType;

	@Column(name = "PERMISSION_URL")
	private String permissionUrl;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "SYSTEM_ID")
	private Integer systemId;

	@Column(name = "PERMISSION_ICO")
	private String permissionIco;

	// bi-directional many-to-one association to SysRolePermission
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysPermission")
	private List<SysRolePermission> sysRolePermissions;

	public SysPermission()
	{
	}

	public SysPermission(Long permissionId)
	{
		this.permissionId = permissionId;
	}

	public Long getPermissionId()
	{
		return permissionId;
	}

	public void setPermissionId(Long permissionId)
	{
		this.permissionId = permissionId;
	}

	public Long getParentPermissionId()
	{
		return parentPermissionId;
	}

	public void setParentPermissionId(Long parentPermissionId)
	{
		this.parentPermissionId = parentPermissionId;
	}

	public String getPermissionName()
	{
		return permissionName;
	}

	public void setPermissionName(String permissionName)
	{
		this.permissionName = permissionName;
	}

	public Integer getPermissionSort()
	{
		return permissionSort;
	}

	public void setPermissionSort(Integer permissionSort)
	{
		this.permissionSort = permissionSort;
	}

	public Integer getPermissionType()
	{
		return permissionType;
	}

	public void setPermissionType(Integer permissionType)
	{
		this.permissionType = permissionType;
	}

	public String getPermissionUrl()
	{
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl)
	{
		this.permissionUrl = permissionUrl;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getSystemId()
	{
		return systemId;
	}

	public void setSystemId(Integer systemId)
	{
		this.systemId = systemId;
	}

	public String getPermissionIco()
	{
		return permissionIco;
	}

	public void setPermissionIco(String permissionIco)
	{
		this.permissionIco = permissionIco;
	}

	public List<SysRolePermission> getSysRolePermissions()
	{
		return sysRolePermissions;
	}

	public void setSysRolePermissions(List<SysRolePermission> sysRolePermissions)
	{
		this.sysRolePermissions = sysRolePermissions;
	}

}