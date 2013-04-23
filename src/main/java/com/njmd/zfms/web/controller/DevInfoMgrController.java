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
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.dev.DevInfo;
import com.njmd.zfms.web.service.DevFacturerInfoService;
import com.njmd.zfms.web.service.DevInfoService;
import com.njmd.zfms.web.service.DevTypeInfoService;
import com.njmd.zfms.web.service.SysCorpService;

@Controller
@RequestMapping("/devMgr")
public class DevInfoMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "设备", "设备编号" };
	// 基础目录
	private final String BASE_DIR = "/dev_mgr/dev_mgr/";
	private final String LIST_PAGE = BASE_DIR + "dev_list";
	private final String ADD_PAGE = BASE_DIR + "dev_add";
	private final String EDIT_PAGE = BASE_DIR + "dev_edit";

	private final String REDIRECT_PATH = "/devMgr";

	@Autowired
	private DevInfoService devInfoService;
	@Autowired
	private DevTypeInfoService devTypeInfoService;
	@Autowired
	private DevFacturerInfoService devFacturerInfoService;
	@Autowired
	private SysCorpService sysCorpService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.DESC);
			page.setOrderBy("devId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		Page pageResult = devInfoService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		model.addAttribute("devFacturerList", devFacturerInfoService.findAll());
		model.addAttribute("devTypeList", devTypeInfoService.findAll());
		Tree tree = sysCorpService.getCorpTree(request, false);
		model.addAttribute("tree", tree);
		return LIST_PAGE;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		model.addAttribute("devFacturerList", devFacturerInfoService.findAll());
		model.addAttribute("devTypeList", devTypeInfoService.findAll());
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new DevInfo());
		return ADD_PAGE;
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResultInfo save(HttpServletRequest request, Model model, DevInfo entity) throws Exception
	{
		try
		{
			int resultTag = devInfoService.save(entity);
			if (resultTag == ResultConstants.SAVE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), REDIRECT_PATH);
			}
			else
			{
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error(INFORMATION_PARAMAS[0] + "保存异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}
	}

	/** 删除 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public ResultInfo delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		int resultTag = devInfoService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{
			return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), REDIRECT_PATH);
		}
		else
		{
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
		}
	}

	/** 进入编辑 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		DevInfo entity = devInfoService.findById(id);
		model.addAttribute("devFacturerList", devFacturerInfoService.findAll());
		model.addAttribute("devTypeList", devTypeInfoService.findAll());
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return EDIT_PAGE;
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResultInfo update(HttpServletRequest request, Model model, DevInfo entity) throws Exception
	{
		try
		{
			int resultTag = devInfoService.update(entity);
			if (resultTag == ResultConstants.UPDATE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), REDIRECT_PATH);
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
		}
		catch (Throwable t)
		{
			logger.error(INFORMATION_PARAMAS[0] + "修改异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}
	}

}
