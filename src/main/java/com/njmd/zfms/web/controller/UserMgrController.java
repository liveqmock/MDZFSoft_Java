package com.njmd.zfms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.commons.Tree;
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.HibernateWebUtils;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.zfms.web.constants.CommonConstants;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysRole;
import com.njmd.zfms.web.service.SysCorpService;
import com.njmd.zfms.web.service.SysLoginService;
import com.njmd.zfms.web.service.SysRoleService;

/**
 * 用户管理
 * 
 * @author Yao
 * 
 */
@Controller
@RequestMapping("/userMgr")
public class UserMgrController extends BaseController
{
	private final String[] INFORMATION_PARAMAS = { "用户", "用户账号" };
	// 基础目录
	private final String BASE_DIR = "/sys_mgr/user_mgr/";

	private final String REDIRECT_PATH = "/userMgr";

	@Autowired
	private SysLoginService sysLoginService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysCorpService sysCorpService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.ASC);
			page.setOrderBy("loginName");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		PropertyFilter pf = new PropertyFilter("loginId", PropertyFilter.MatchType.NE, this.getLoginToken().getSysLogin().getLoginId());
		PropertyFilter pf2 = new PropertyFilter("userType", PropertyFilter.MatchType.NE, SysLogin.USER_TYPE_SUPER_ADMIN);
		filters.add(pf);
		filters.add(pf2);
		Page pageResult = sysLoginService.query(page, filters);
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "user_list";
	}

	/** 查看 */
	@RequestMapping(value = "/view/{id}")
	public String view(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysLogin entity = sysLoginService.findById(id);
		List<SysRole> roleList = sysRoleService.findAll();
		model.addAttribute("roleList", roleList);
		List<SysCorp> corpList = sysCorpService.findAll();

		model.addAttribute("roleList", roleList);
		model.addAttribute("corpList", corpList);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return BASE_DIR + "user_view";

	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		List<SysRole> roleList = sysRoleService.findAll();
		model.addAttribute("roleList", roleList);
		List<SysCorp> corpList = sysCorpService.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("corpList", corpList);
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new SysLogin());
		return BASE_DIR + "user_add";
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResultInfo save(HttpServletRequest request, Model model, SysLogin entity, Long[] roleIds, Long deptId) throws Exception
	{
		try
		{
			entity.setSystemId(this.getLoginToken().getSysLogin().getSystemId());
			entity.setLoginPwd(CommonConstants.DEFAULT_PWD);
			int resultTag = sysLoginService.save(entity, roleIds, deptId);
			if (resultTag == ResultConstants.SAVE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else if (resultTag == ResultConstants.SAVE_FAILED_NAME_IS_EXIST)
			{
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
			else
			{
				String[] PARAMAS = { "用户", "警员编号" };
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error("用户保存异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}

	}

	/** 进入编辑 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysLogin entity = sysLoginService.findById(id);
		List<SysRole> roleList = sysRoleService.findAll();
		model.addAttribute("roleList", roleList);
		List<SysCorp> corpList = sysCorpService.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("corpList", corpList);
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return BASE_DIR + "user_edit";

	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResultInfo update(HttpServletRequest request, Model model, SysLogin entity, Long[] roleIds, String newLoginPwd) throws Exception
	{
		try
		{
			int resultTag = sysLoginService.update(entity, roleIds, newLoginPwd);
			if (resultTag == ResultConstants.UPDATE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else if (resultTag == ResultConstants.UPDATE_FAILED_NAME_IS_EXIST)
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
			else
			{
				String[] PARAMAS = { "用户", "警员编号" };
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error("用户修改异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}

	}

	/** 删除 */
	@RequestMapping(value = "/delete/{id}")
	public String delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{

		int resultTag = sysLoginService.delete(id);
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

		int resultTag = sysLoginService.delete(id);
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

	/** 密码重置 */
	@RequestMapping(value = "/resetPwd/{id}")
	@ResponseBody
	public ResultInfo resetPwd(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		try
		{
			int resultTag = sysLoginService.resetPwd(id);
			if (resultTag == ResultConstants.UPDATE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, "用户密码"), null);
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, "用户密码"));
			}
		}
		catch (Throwable t)
		{
			logger.error("用户修改异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, "用户密码"));
		}

	}

	/** 弹出用户选择列表 */
	@RequestMapping(value = "/userSelect")
	public String userSelect(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.ASC);
			page.setOrderBy("loginName");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		// 是否已经存在机构过滤条件，如果存在则不加入机构编码条件过滤，避免Hibernate出错
		boolean isExistCorpFilter = false;
		for (PropertyFilter propertyFilter : filters)
		{
			if (propertyFilter.getPropertyName().equals("sysCorp.corpId"))
			{
				isExistCorpFilter = true;
			}

		}
		if (!isExistCorpFilter)
		{
			PropertyFilter pf = new PropertyFilter("sysCorp.treeCode", PropertyFilter.MatchType.START, this.getLoginToken().getSysCorp().getTreeCode());
			filters.add(pf);
		}
		Page pageResult = sysLoginService.query(page, filters);
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "user_select";
	}
}
