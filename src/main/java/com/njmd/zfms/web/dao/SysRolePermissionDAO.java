/**
 * 
 */
package com.njmd.zfms.web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.zfms.web.entity.sys.SysRolePermission;

/**
 * @title:
 * @description:
 * 
 * @author: dongyuese
 * 
 */
@Repository
public class SysRolePermissionDAO extends BaseHibernateDAO<SysRolePermission, Long>
{
	public void deleteByRoleId(Long roleId)
	{
		List<SysRolePermission> list = this.findByProperty("sysRole.roleId", roleId);
	}
}
