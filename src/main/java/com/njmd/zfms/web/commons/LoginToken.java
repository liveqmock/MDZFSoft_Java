/**
 * 
 */
package com.njmd.zfms.web.commons;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.entity.sys.SysRole;

/**
 * 
 * @title: 系统登录令牌类
 * 
 * @description: 用户登录后，该用户的所有信息将通过该对象保存至用户会话中
 * 
 * 
 * @author: Yao
 * 
 */
@Scope("prototype")
@Component
public class LoginToken
{
	// 登录信息
	private SysLogin sysLogin;
	// 单位信息
	private SysCorp sysCorp;

	private List<SysRole> sysRoles;

	// 菜单权限
	private Map<Long, SysPermission> menuPermissions;

	private List<SysPermission> level1MenuList;

	private Map<Long, List<SysPermission>> level2MenuMap;

	public SysLogin getSysLogin()
	{
		return sysLogin;
	}

	public void setSysLogin(SysLogin sysLogin)
	{
		this.sysLogin = sysLogin;
	}

	public Map<Long, SysPermission> getMenuPermissions()
	{
		return menuPermissions;
	}

	public void setMenuPermissions(Map<Long, SysPermission> menuPermissions)
	{
		this.menuPermissions = menuPermissions;
	}

	public SysCorp getSysCorp()
	{
		return sysCorp;
	}

	public void setSysCorp(SysCorp sysCorp)
	{
		this.sysCorp = sysCorp;
	}

	public List<SysRole> getSysRoles()
	{
		return sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles)
	{
		this.sysRoles = sysRoles;
	}

	public List<SysPermission> getLevel1MenuList()
	{
		return level1MenuList;
	}

	public void setLevel1MenuList(List<SysPermission> level1MenuList)
	{
		this.level1MenuList = level1MenuList;
	}

	public Map<Long, List<SysPermission>> getLevel2MenuMap()
	{
		return level2MenuMap;
	}

	public void setLevel2MenuMap(Map<Long, List<SysPermission>> level2MenuMap)
	{
		this.level2MenuMap = level2MenuMap;
	}

}
