package com.njmd.zfms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.HibernateWebUtils;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.framework.dao.PropertyFilter.MatchType;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysDept;
import com.njmd.zfms.web.service.SysDeptService;

@Controller
@RequestMapping("/deptMgr")
public class DeptMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "部门","部门名称" };
	// 基础目录
	private final String BASE_DIR = "/sys_mgr/dept_mgr/";

	private final String REDIRECT_PATH = "/deptMgr";

	@Autowired
	private SysDeptService sysDeptService;

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
		PropertyFilter pf = new PropertyFilter("corpId", MatchType.EQ, this.getLoginToken().getSysCorp().getCorpId());
		filters.add(pf);
		Page pageResult = sysDeptService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "list";
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new SysDept());
		return BASE_DIR + "add";
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model, SysDept entity) throws Exception
	{
		entity.setCorpId(this.getLoginToken().getSysCorp().getCorpId());
		int resultTag = sysDeptService.save(entity);
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

		int resultTag = sysDeptService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{
			ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request, REDIRECT_PATH);
		}
		else
		{
			ResultInfo.saveErrorMessage("部门删除失败！此部门下面还有用户存在", request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}

	/** 批量删除 */
	@RequestMapping(value = "/batchDelete")
	public String batchDelete(HttpServletRequest request, Model model, Long[] id) throws Exception
	{

		int resultTag = sysDeptService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{
			ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), request, REDIRECT_PATH);
		}
		else
		{
			ResultInfo.saveErrorMessage("部门批量删除失败！所选择的部门下面还有用户存在", request);
		}

		return UrlConstants.INFORMATION_PAGE;
	}

	/** 进入编辑 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{

		SysDept entity = sysDeptService.findById(id);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);

		return BASE_DIR + "edit";
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, Model model, SysDept entity) throws Exception
	{
		int resultTag = sysDeptService.update(entity);
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
