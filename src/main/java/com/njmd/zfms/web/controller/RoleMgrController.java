package com.njmd.zfms.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.commons.Tree;
import com.njmd.framework.commons.TreeNode;
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.HibernateWebUtils;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.framework.dao.PropertyFilter.MatchType;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysLoginRole;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.entity.sys.SysRole;
import com.njmd.zfms.web.service.SysLoginRoleService;
import com.njmd.zfms.web.service.SysRolePermissionService;
import com.njmd.zfms.web.service.SysRoleService;

@Controller
@RequestMapping("/roleMgr")
public class RoleMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "角色", "角色名称" };
	// 基础目录
	private final String BASE_DIR = "/sys_mgr/role_mgr/";

	private final String REDIRECT_PATH = "/roleMgr";

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysLoginRoleService sysLoginRoleService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.DESC);
			page.setOrderBy("status");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		SysLogin sysLogin = this.getLoginToken().getSysLogin();
		if (sysLogin.getUserType().longValue() != SysLogin.USER_TYPE_SUPER_ADMIN)
		{
			if (sysLogin.getSysLoginRoles() != null)
			{
				for (SysLoginRole sysLoginRole : sysLogin.getSysLoginRoles())
				{
					SysRole sysRole = sysLoginRole.getSysRole();
					filters.add(new PropertyFilter("roleId", MatchType.NE, sysRole.getRoleId()));
				}
			}
		}
		Page pageResult = sysRoleService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "role_index";
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		Map<Long, SysPermission> menuMap = this.getLoginToken().getMenuPermissions();

		Iterator menuIterator = menuMap.keySet().iterator();
		Tree tree = new Tree();
		String context = request.getContextPath();
		while (menuIterator.hasNext())
		{
			SysPermission menu = menuMap.get(menuIterator.next());
			TreeNode treeNode = new TreeNode();
			treeNode.setId(menu.getPermissionId().toString());
			treeNode.setName(menu.getPermissionName());
			treeNode.setpId(menu.getParentPermissionId().toString());
			if (menu.getParentPermissionId() == 0)
			{
				treeNode.setIcon(context + "/plugins/zTree/css/zTreeStyle/img/diy/1_open.png");
			}
			else
			{
				treeNode.setIcon(context + "/plugins/zTree/css/zTreeStyle/img/diy/3.png");
			}
			tree.addNode(treeNode);
		}

		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new SysRole());
		return BASE_DIR + "role_add";
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model, SysRole entity) throws Exception
	{
		entity.setCorpId(this.getLoginToken().getSysCorp().getCorpId());
		int resultTag = sysRoleService.save(entity);
		if (resultTag == ResultConstants.SAVE_SUCCEED)
		{
			ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request, REDIRECT_PATH);
		}
		else
		{
			model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}

	/** 删除 */
	@RequestMapping(value = "/delete/{id}")
	public String delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{

		int resultTag = sysRoleService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{
			ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request, REDIRECT_PATH);
		}
		else
		{
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}

	/** 批量删除 */
	@RequestMapping(value = "/batchDelete")
	public String batchDelete(HttpServletRequest request, Model model, Long[] id) throws Exception
	{

		int resultTag = sysRoleService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{
			ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request, REDIRECT_PATH);
		}
		else
		{
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}

	/** 进入编辑 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{

		SysRole entity = sysRoleService.findById(id);
		String rolePermissIds = sysRolePermissionService.findPermissionIdsByRoleIds(id);
		Map<Long, SysPermission> menuMap = this.getLoginToken().getMenuPermissions();

		Iterator menuIterator = menuMap.keySet().iterator();
		Tree tree = new Tree();
		String context = request.getContextPath();
		while (menuIterator.hasNext())
		{
			SysPermission menu = menuMap.get(menuIterator.next());
			TreeNode treeNode = new TreeNode();
			treeNode.setId(menu.getPermissionId().toString());
			treeNode.setpId(menu.getParentPermissionId().toString());
			treeNode.setName(menu.getPermissionName());
			if (menu.getParentPermissionId() == 0)
			{
				treeNode.setIcon(context + "/plugins/zTree/css/zTreeStyle/img/diy/1_open.png");
			}
			else
			{
				treeNode.setIcon(context + "/plugins/zTree/css/zTreeStyle/img/diy/3.png");
			}
			tree.addNode(treeNode);
		}

		model.addAttribute("rolePermissIds", rolePermissIds);
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);

		return BASE_DIR + "role_edit";
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, Model model, SysRole entity) throws Exception
	{
		int resultTag = sysRoleService.update(entity);
		if (resultTag == ResultConstants.UPDATE_SUCCEED)
		{
			ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request, REDIRECT_PATH);
		}
		else
		{
			ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}
}
