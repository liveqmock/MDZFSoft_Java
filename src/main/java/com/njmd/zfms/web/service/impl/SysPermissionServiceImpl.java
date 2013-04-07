/**
 * 
 */
package com.njmd.zfms.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.dao.SysPermissionDAO;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.service.SysPermissionService;

/**
 * 权限管理
 * 
 * @author Yao
 * 
 */
@Service
@Transactional(readOnly = true)
public class SysPermissionServiceImpl extends BaseCrudServiceImpl<SysPermission, Long> implements SysPermissionService
{
	/**
	 * 权限信息数据访问对象
	 */
	@Autowired
	private SysPermissionDAO sysPermissionDAO;

	/**
	 * 根据系统类型查询菜单信息
	 * 
	 * @param systemId
	 *            　系统类型
	 * @return　权限信息集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPermission> findMenuBySystemId(Integer systemId) throws Exception
	{
		String hql = "from SysPermission as model where model.systemId =? and model.permissionType=1 order by model.permissionSort asc";
		return sysPermissionDAO.findByHql(hql, systemId);
	}

	/**
	 * 根据系统类型查询菜单信息
	 * 
	 * @param systemId
	 *            　系统类型
	 * @return　权限信息集合
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPermission> findLevel1Menu(Integer systemId) throws Exception
	{
		String hql = "from SysPermission as model where model.systemId =? and model.permissionType=1 and (model.parentPermissionId is null or model.parentPermissionId=0) order by model.permissionSort asc";
		return sysPermissionDAO.findByHql(hql, systemId);
	}

	/**
	 * 通过父id和权限类型找到相对应的权限list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPermission> findMenuByparentIdAndType(Long parentId, Long permissionType) throws Exception
	{
		List<SysPermission> permissionList = new ArrayList<SysPermission>();
		List<SysPermission> resultList = new ArrayList<SysPermission>();
		if (parentId == -1)// 属于运营商系统
		{
			String hql = "from SysPermission as model where model.systemId =? and model.permissionType=? and model.parentPermissionId is null order by model.permissionSort asc";
			Object[] values = new Object[] { 1L, permissionType };
			permissionList = sysPermissionDAO.findByHql(hql, values);
		}
		else if (parentId == -2)// 属于会员系统
		{
			String hql = "from SysPermission as model where model.systemId =? and model.permissionType=? and model.parentPermissionId is null order by model.permissionSort asc";
			Object[] values = new Object[] { 2L, permissionType };
			permissionList = sysPermissionDAO.findByHql(hql, values);
		}
		else
		{
			List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
			filterList.add(new PropertyFilter("parentPermissionId", PropertyFilter.MatchType.EQ, parentId));
			filterList.add(new PropertyFilter("permissionType", PropertyFilter.MatchType.EQ, permissionType));

			permissionList = sysPermissionDAO.findByFilters(filterList, Order.asc("permissionSort"));
		}
		for (SysPermission frameSysMenu : permissionList)
		{
			SysPermission frameSysMenuDTO = new SysPermission();
			BeanUtils.copyProperties(frameSysMenuDTO, frameSysMenu);
			resultList.add(frameSysMenuDTO);
		}
		return resultList;
	}

	/**
	 * 通过权限id删除权限
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long menuId) throws Exception
	{
		sysPermissionDAO.deleteById(menuId);
		List<SysPermission> list = sysPermissionDAO.findByProperty("parentPermissionId", menuId);
		if (list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				sysPermissionDAO.deleteById(list.get(i).getPermissionId());
			}
		}
		return ResultConstants.DELETE_SUCCEED;
	}

	/**
	 * 添加菜单
	 * 
	 * @param sysPermissionupd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int save(SysPermission sysPermission) throws Exception
	{

		String hql = "from SysPermission as model where model.parentPermissionId=? and model.systemId=? order by model.permissionSort desc";
		Object[] values = new Object[] {sysPermission.getParentPermissionId(),1};
		List<SysPermission> menuList = sysPermissionDAO.findByHql(hql, values);
		Integer menuSort = 1;
		if (menuList.size() != 0)
		{
			menuSort = menuList.get(0).getPermissionSort() + 1;
		}
		sysPermission.setSystemId(1);
		sysPermission.setPermissionSort(menuSort);
		sysPermissionDAO.save(sysPermission);
		return ResultConstants.SAVE_SUCCEED;
	}

	/**
	 * 改变菜单的排序
	 * 
	 * @param menuId
	 * @param sortOffset
	 *            1－下移 -1－上移
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public void changSort(Long menuId, int sortOffset) throws Exception
	{
		SysPermission frameSysMenu = sysPermissionDAO.findById(menuId);
		Long parentId = frameSysMenu.getParentPermissionId();
		Integer sort = frameSysMenu.getPermissionSort();
		Integer newSort = sort + sortOffset;
		String hql = null;
		Object[] values = null;
		if (parentId == null)
		{
			hql = "from SysPermission as model where model.systemId =? and model.permissionSort =? and model.parentPermissionId is null";
			values = new Object[] { frameSysMenu.getSystemId(), newSort };
		}
		else
		{
			hql = "from SysPermission as model where model.systemId =? and model.permissionSort =? and model.parentPermissionId =?";
			values = new Object[] { frameSysMenu.getSystemId(), newSort, frameSysMenu.getParentPermissionId() };
		}
		List<SysPermission> menuList = sysPermissionDAO.findByHql(hql, values);
		if (menuList != null && menuList.size() > 0)
		{
			SysPermission menu = menuList.get(0);
			menu.setPermissionSort(sort);
			frameSysMenu.setPermissionSort(newSort);
			sysPermissionDAO.update(menu);
			sysPermissionDAO.update(frameSysMenu);
		}
	}

	@Autowired
	@Qualifier(value = "sysPermissionDAO")
	@Override
	public void setBaseDao(BaseHibernateDAO<SysPermission, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	public List<SysPermission> sortList(List<SysPermission> list) throws Exception {
		if(list.isEmpty()){
			return new ArrayList<SysPermission>();
		}else{
			int size = list.size();
			Long[] pIds = new Long[size];
			for(int i=0;i<size;i++){
				pIds[i] = list.get(i).getPermissionId();
			}
			String hql = "from SysPermission as model where model.permissionId in (:pIds) order by model.permissionSort asc";
			Map m = new HashMap();
			m.put("pIds", pIds);
			List<SysPermission> spList = baseDao.findByHql(hql,m);
			return spList;
		}
	}

}
