/**
 * 
 */
package com.njmd.zfms.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.commons.tree.Tree;
import com.njmd.framework.commons.tree.TreeNode;
import com.njmd.framework.controller.BaseController;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.service.SysPermissionService;

/**
 * 
 * @title:登录操作控制器
 * @description:
 * 
 * @author: Yao
 * 
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController
{

	@Autowired
	private SysPermissionService sysPermissionService;

	private final String VIEW_PATH = "/sys_mgr/menu_mgr/";

	private final String RELOAD_PATH = "/menu/menuMgr";

	@RequestMapping
	public String index(HttpServletRequest request, Model model) throws Exception
	{
		LoginToken loginToken = this.getLoginToken();
		// 当前用户拥有菜单权限
		Map<Long, SysPermission> menuPermissions = loginToken.getMenuPermissions();

		// 取得数据库中所有一级菜单列表
		List<SysPermission> allLevel1MenuList = sysPermissionService.findLevel1Menu(1);

		List<SysPermission> level1MenuList = new ArrayList<SysPermission>();
		Map<Long, List<SysPermission>> level2MenuMap = new HashMap<Long, List<SysPermission>>();

		if (allLevel1MenuList != null && allLevel1MenuList.size() > 0)
		{
			for (SysPermission level1Menu : allLevel1MenuList)
			{
				Long level1MenuId = level1Menu.getPermissionId();

				List<SysPermission> allLevel2Menus = sysPermissionService.findMenuByparentIdAndType(level1MenuId, SysPermission.PERMISSION_TYPE_1);
				List<SysPermission> level2Menus = new ArrayList<SysPermission>();
				if (allLevel2Menus != null && allLevel2Menus.size() > 0)
				{
					for (SysPermission level2menu : allLevel2Menus)
					{
						Long level2MenuId = level2menu.getPermissionId();
						if (menuPermissions.containsKey(level2MenuId))
						{
							level2Menus.add(level2menu);
						}
					}
					if (level2Menus.size() > 0)
					{
						level1MenuList.add(level1Menu);
						level2MenuMap.put(level1MenuId, level2Menus);
					}
				}
			}
		}
		model.addAttribute("level1MenuList", level1MenuList);
		model.addAttribute("level2MenuMap", level2MenuMap);
		return "menu";
	}

	@RequestMapping(value = "/menuMgr")
	public String menuMgr(HttpServletRequest request, Model model, Long menuId)
	{
		try
		{
			// 根据系统编号获得菜单树
			List<SysPermission> permissions = sysPermissionService.findAll();
			permissions = sysPermissionService.sortList(permissions);
			Tree menuTree = new Tree();
			for (SysPermission sysMenu : permissions)
			{
				TreeNode node = new TreeNode(String.valueOf(sysMenu.getPermissionId()));
				node.setText(sysMenu.getPermissionName());
				node.setParentId(String.valueOf(sysMenu.getParentPermissionId()));
				menuTree.addNode(node);
			}
			model.addAttribute("menuTree", menuTree);

			if (menuId == null)
			{
				menuId = 0L;
			}
			permissions = sysPermissionService.findMenuByparentIdAndType(menuId, 1L);
			model.addAttribute(RequestNameConstants.RESULT_LIST, permissions);
			SysPermission sysPermission = sysPermissionService.findById(menuId);
			model.addAttribute(RequestNameConstants.RESULT_OBJECT, sysPermission);
		}
		catch (Exception e)
		{
			this.logger.error("获取菜单列表异常", e);
		}
		return VIEW_PATH + "menu_index";
	}

	/**
	 * 权限上移
	 * 
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */

	@RequestMapping(value = "/up")
	public String up(HttpServletRequest request, Model model, Long menuId)
	{
		try
		{
			this.sysPermissionService.changSort(menuId, -1);
			ResultInfo.saveMessage("菜单上移操作成功!", request, RELOAD_PATH);
		}
		catch (Exception e)
		{
			this.logger.error("菜单上移操作异常", e);
			ResultInfo.saveErrorMessage("菜单上移操作异常", request);
		}
		return UrlConstants.INFORMATION_PAGE;
	}

	/**
	 * 权限下移
	 * 
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */

	@RequestMapping(value = "/down")
	public String down(HttpServletRequest request, Model model, Long menuId)
	{
		try
		{
			this.sysPermissionService.changSort(menuId, 1);
			ResultInfo.saveMessage("菜单下移操作成功!", request, RELOAD_PATH);
		}
		catch (Exception e)
		{
			this.logger.error("菜单下移操作异常", e);
			ResultInfo.saveErrorMessage("菜单下移操作异常", request);
		}
		return UrlConstants.INFORMATION_PAGE;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model, Long menuId) throws Exception
	{
		try
		{
			SysPermission entity = sysPermissionService.findById(menuId);
			model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
			List<SysPermission> allMenuList = new ArrayList<SysPermission>();
			allMenuList = sysPermissionService.findAll();
			model.addAttribute("allMenu", allMenuList);
		}
		catch (Exception e)
		{
			this.logger.error("新增异常", e);
		}
		return VIEW_PATH + "menu_add";
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model, SysPermission entity) throws Exception
	{
		try
		{
			int resultTag = this.sysPermissionService.save(entity);
			if (resultTag == ResultConstants.ADD_SUCCEED)
			{
				ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag), request, RELOAD_PATH);
			}
			else
			{
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag), request);
			}
		}
		catch (Exception e)
		{
			this.logger.error("新增保存异常", e);
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.ADD_ERROR), request);
		}
		return UrlConstants.INFORMATION_PAGE;
	}

	/** 进入编辑 */
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, Model model, Long menuId) throws Exception
	{
		try
		{
			SysPermission entity = sysPermissionService.findById(menuId);
			List<SysPermission> allMenuList = sysPermissionService.findAll();
			model.addAttribute("allMenu", allMenuList);
			model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		}
		catch (Exception e)
		{
			this.logger.error("编辑异常", e);
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.EDIT_ERROR), request);
			return UrlConstants.INFORMATION_PAGE;
		}
		return VIEW_PATH + "menu_edit";
	}

	/** 保存更新 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, Model model, SysPermission entity) throws Exception
	{
		try
		{
			int resultTag = this.sysPermissionService.update(entity);
			if (resultTag == ResultConstants.UPDATE_SUCCEED)
			{
				ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag), request, RELOAD_PATH);
			}
			else
			{
				ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag), request);
			}

		}
		catch (Exception e)
		{
			this.logger.error("修改异常", e);
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.UPDATE_ERROR), request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}

	/**
	 * 查看
	 * 
	 * @param request
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request, Model model, Long menuId)
	{
		try
		{
			SysPermission entity = sysPermissionService.findById(menuId);
			model.addAttribute("menu", entity);
		}
		catch (Exception e)
		{
			this.logger.error("查看异常", e);

		}
		return VIEW_PATH + "menu_view";
	}

	/** 删除 */
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Model model, Long menuId)
	{
		try
		{
			int resultTag = this.sysPermissionService.delete(menuId);
			if (resultTag == ResultConstants.DELETE_SUCCEED)
			{
				ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag), request, RELOAD_PATH);
			}
			else
			{
				ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag), request);
			}
		}
		catch (Exception e)
		{
			this.logger.error("删除异常", e);
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.DELETE_ERR0R), request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}

	/** 批量删除 */
	@RequestMapping(value = "/batchDel")
	public String batchDel(HttpServletRequest request, Model model, Long[] id)
	{
		try
		{
			int resultTag = this.sysPermissionService.delete(id);
			if (resultTag == ResultConstants.DELETE_SUCCEED)
			{
				ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag), request, RELOAD_PATH);
			}
			else
			{
				ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag), request);
			}
		}
		catch (Exception e)
		{
			this.logger.error("删除异常", e);
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.DELETE_ERR0R), request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}
}
