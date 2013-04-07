package com.njmd.zfms.web.service;

import java.util.List;

import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysLoginRole;
import com.njmd.zfms.web.entity.sys.SysPermission;

/**
 * @title: FSysLogBO
 * @description: 登陆角色管理类接口
 * 
 * 
 * @author: zhujie
 * 
 */
public interface SysLoginRoleService extends BaseCrudService<SysLoginRole, Long>
{
	public void saveLoginRole(SysLogin login,Long[] roleIds) throws Exception;
	
	/**
	 * <p>
	 * Description:[根据用户更新此用的登陆角色信息]
	 * </p>
	 * 
	 * @param login
	 * @param roleIds
	 * @throws Exception
	 */
	public void updateLoginRole(SysLogin login, Long[] roleIds) throws Exception;
	
	/**
	 * <p>
	 * Description:[根据登陆用户查找菜单权限的集合]
	 * </p>
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public List<SysPermission> findSysPermissionByLogin(SysLogin login) throws Exception;
	
	/**
	 * <p>
	 * Description:[根据角色id，去判断是否有用户跟此角色关联]
	 * </p>
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean findLoginByRole(Long[] roleId);

}
