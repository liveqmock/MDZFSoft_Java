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
 * The persistent class for the SYS_ROLE database table.
 * 
 */
@Entity
@Table(name = "SYS_ROLE")
public class SysRole extends AuditableEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "ROLE_ID_GENERATOR", sequenceName = "SEQ_SYS_ROLE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_ID_GENERATOR")
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "CORP_ID")
	private Long corpId;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "SYSTEM_ID")
	private Integer systemId;

	// bi-directional many-to-one association to SysLoginRole
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
	private List<SysLoginRole> sysLoginRoles;

	// bi-directional many-to-one association to SysRolePermission
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
	private List<SysRolePermission> sysRolePermissions;

	// bi-directional many-to-one association to SysRolePermission
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
	private List<SysRoleRoom> sysRoleRoom;

	/**** 非实体化属性 *********/
	// 单位名称
	@Transient
	private String corpName = null;

	@Transient
	private String permissionIds;

	@Transient
	private String roomIds;

	public SysRole()
	{
	}

	public SysRole(Long roleId)
	{
		this.roleId = roleId;
	}

	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	public Long getCorpId()
	{
		return corpId;
	}

	public void setCorpId(Long corpId)
	{
		this.corpId = corpId;
	}

	public String getRoleDesc()
	{
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc)
	{
		this.roleDesc = roleDesc;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
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

	public List<SysLoginRole> getSysLoginRoles()
	{
		return sysLoginRoles;
	}

	public void setSysLoginRoles(List<SysLoginRole> sysLoginRoles)
	{
		this.sysLoginRoles = sysLoginRoles;
	}

	public List<SysRolePermission> getSysRolePermissions()
	{
		return sysRolePermissions;
	}

	public void setSysRolePermissions(List<SysRolePermission> sysRolePermissions)
	{
		this.sysRolePermissions = sysRolePermissions;
	}

	public List<SysRoleRoom> getSysRoleRoom()
	{
		return sysRoleRoom;
	}

	public void setSysRoleRoom(List<SysRoleRoom> sysRoleRoom)
	{
		this.sysRoleRoom = sysRoleRoom;
	}

	public void setCorpName(String corpName)
	{
		this.corpName = corpName;
	}

	public String getCorpName()
	{
		return corpName;
	}

	public String getPermissionIds()
	{
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds)
	{
		this.permissionIds = permissionIds;
	}

	public String getRoomIds()
	{
		return roomIds;
	}

	public void setRoomIds(String roomIds)
	{
		this.roomIds = roomIds;
	}

}