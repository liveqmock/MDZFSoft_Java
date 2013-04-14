package com.njmd.zfms.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.njmd.framework.commons.Tree;
import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.entity.sys.SysPermission;

/**
 * @title:
 * @description:
 * 
 * @author: zhujie
 * 
 */
public interface SysPermissionService extends BaseCrudService<SysPermission, Long>
{
	/**
	 * 获得菜单树
	 * 
	 * @return
	 * @throws Exception
	 */
	Tree getMenuTree(HttpServletRequest request) throws Exception;

	/**
	 * 根据系统类型查询菜单信息
	 * 
	 * @param systemId
	 *            　系统类型
	 * @return　权限信息集合
	 * @throws Exception
	 */
	List<SysPermission> findMenuBySystemId(Integer systemId) throws Exception;

	/**
	 * 根据系统类型获得所有菜单集合
	 * 
	 * @param systemId
	 *            　系统类型
	 * @return
	 * @throws Exception
	 */
	List<SysPermission> findLevel1Menu(Integer systemId) throws Exception;

	/**
	 * 通过菜单的父菜单id和菜单类型找到菜单信息
	 * 
	 * @param parentId
	 *            父id
	 * @param permissionType
	 *            菜单类型
	 * @return 权限信息集合
	 * @throws Exception
	 */
	List<SysPermission> findMenuByparentIdAndType(Long parentId, Long permissionType) throws Exception;

	/**
	 * 改变菜单的排序
	 * 
	 * @param menuId
	 * @param sortOffset
	 *            1－下移 -1－上移
	 * @throws Exception
	 */
	void changSort(Long menuId, int sortOffset) throws Exception;

	/**
	 * <p>
	 * Description:[把菜单集合进行排序]
	 * </p>
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	List<SysPermission> sortList(List<SysPermission> list) throws Exception;

}
