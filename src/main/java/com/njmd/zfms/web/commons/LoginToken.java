/**
 * 
 */
package com.njmd.zfms.web.commons;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysPermission;

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

	// 菜单权限
	private Map<Long, SysPermission> menuPermissions;

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

}
