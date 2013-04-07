package com.njmd.zfms.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.entity.sys.SysRolePermission;
import com.njmd.zfms.web.service.SysPermissionService;
import com.njmd.zfms.web.service.SysRolePermissionService;

@Service
public class SysRolePermissionServiceImpl extends BaseCrudServiceImpl<SysRolePermission, Long> implements SysRolePermissionService {

	@Autowired
	private SysPermissionService sysPermissionService;
	
	@Override
	@Autowired
	@Qualifier(value = "sysRolePermissionDAO")
	public void setBaseDao(BaseHibernateDAO<SysRolePermission, Long> baseDao)
	{
		this.baseDao = baseDao;
	}
	
	public List<SysPermission> findByRoleIds(Long[] roleIds) throws Exception{
		String hql ="from SysRolePermission as model where model.sysRole.roleId in (:roleIds)";
		Map m = new HashMap();
		m.put("roleIds", roleIds);
		List<SysRolePermission> list = baseDao.findByHql(hql, m);
		Map<Long,SysPermission> map = new HashMap<Long, SysPermission>();
		if(list.isEmpty()){
			return new ArrayList<SysPermission>();
		}else{
			for(SysRolePermission srp:list){
				SysPermission sp = srp.getSysPermission();
				if(!map.containsKey(sp.getPermissionId()) && sp.getPermissionType() == 1){
					map.put(sp.getPermissionId(), sp);
				}
			}
		}
		Iterator<Long> iterator = map.keySet().iterator();
		List<SysPermission> spList = new ArrayList<SysPermission>();
		while(iterator.hasNext()){
			spList.add(map.get(iterator.next()));
		}
		return sysPermissionService.sortList(spList);
	}

	@Override
	public String findPermissionIdsByRoleIds(Long roleId) throws Exception {
		String hql ="from SysRolePermission as model where model.sysRole.roleId = ?";
		List<SysRolePermission> list = baseDao.findByHql(hql, roleId);
		String returnStr = "";
		if(!list.isEmpty()){
			for(int i =0;i<list.size();i++){
				SysRolePermission srp = list.get(i);
				if(i == (list.size()-1)){
					returnStr+=srp.getSysPermission().getPermissionId();
				}else{
					returnStr+=(srp.getSysPermission().getPermissionId()+",");
				}
			}
		}
		return returnStr;
	}
	
}
