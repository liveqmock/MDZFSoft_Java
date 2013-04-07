package com.njmd.zfms.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.dao.SysPermissionDAO;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysLoginRole;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.entity.sys.SysRole;
import com.njmd.zfms.web.service.SysLoginRoleService;
import com.njmd.zfms.web.service.SysRolePermissionService;

@Service
public class SysLoginRoleServiceImpl extends BaseCrudServiceImpl<SysLoginRole, Long> implements SysLoginRoleService {
	
	@Autowired
	private SysPermissionDAO sysPermissionDAO;
	
	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	@Override
	@Autowired
	@Qualifier(value = "sysLoginRoleDAO")
	public void setBaseDao(BaseHibernateDAO<SysLoginRole, Long> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void saveLoginRole(SysLogin login, Long[] roleIds) throws Exception {
		List<SysLoginRole> list = new ArrayList<SysLoginRole>();
		for(Long roleId:roleIds){
			SysLoginRole sysLoginRole = new SysLoginRole();
			sysLoginRole.setSysLogin(login);
			sysLoginRole.setSysRole(new SysRole(roleId));
			list.add(sysLoginRole);
		}
		baseDao.saveAll(list);
	}
	
	@Override
	public void updateLoginRole(SysLogin login, Long[] roleIds) throws Exception {
		String hql = "from SysLoginRole model where model.sysLogin.loginId = ?";
		List<SysLoginRole> list = baseDao.findByHql(hql, login.getLoginId());
		if(!list.isEmpty()){
			for(SysLoginRole slr:list){
				baseDao.delete(slr);
			}
		}
		
		List<SysLoginRole> tmpList = new ArrayList<SysLoginRole>();
		for(Long roleId:roleIds){
			SysLoginRole sysLoginRole = new SysLoginRole();
			sysLoginRole.setSysLogin(login);
			sysLoginRole.setSysRole(new SysRole(roleId));
			tmpList.add(sysLoginRole);
		}
		baseDao.saveAll(tmpList);
	}

	@Override
	public List<SysPermission> findSysPermissionByLogin(SysLogin login) throws Exception {
		String hql = "from SysLoginRole as model where model.sysLogin.loginId = ?";
		if(login.getUserType() == 0){//超级管理员
			hql = "from SysPermission as model where systemId = 1 and permissionType = 1";
			List<SysPermission> list = sysPermissionDAO.findByHql(hql);
			if(list.isEmpty()){
				return new ArrayList<SysPermission>();
			}
			return list;
		}else{
			List<SysLoginRole> list = baseDao.findByHql(hql, login.getLoginId());
			
			if(list == null || list.isEmpty()){
				return new ArrayList<SysPermission>();
			}else{
				int size = list.size();
				Long[] roleIds = new Long[size];
				for(int i=0;i<size;i++){
					roleIds[i] = list.get(i).getSysRole().getRoleId();
				}
				List<SysPermission> _list = sysRolePermissionService.findByRoleIds(roleIds);
				if(_list.isEmpty()){
					return new ArrayList<SysPermission>();
				}
				return _list;
			}
		}
	}

	@Override
	public boolean findLoginByRole(Long[] roleId) {
		String hql = "from SysLoginRole model where model.sysRole.roleId in (:roleIds)";
		Map m = new HashMap();
		m.put("roleIds", roleId);
		List<SysLoginRole> list = baseDao.findByHql(hql, m);
		if(list == null || list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
