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
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.HibernateWebUtils;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.zfms.annotation.Permission;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.dev.DevTypeInfo;
import com.njmd.zfms.web.service.DevTypeInfoService;

@Controller
@RequestMapping("/devTypeMgr")
public class DevTypeMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "设备类型", "设备类型" };
	// 基础目录
	private final String BASE_DIR = "/dev_mgr/dev_type_mgr/";
	private final String LIST_PAGE = BASE_DIR + "type_list";
	private final String ADD_PAGE = BASE_DIR + "type_add";
	private final String EDIT_PAGE = BASE_DIR + "type_edit";

	private final String REDIRECT_PATH = "/devTypeMgr";

	@Autowired
	private DevTypeInfoService devTypeInfoService;

	/** 列表查询 */
	@RequestMapping
	@Permission(resource=Permission.Resources.DEVTYPE,action=Permission.Actions.LIST)
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.DESC);
			page.setOrderBy("devTypeId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		Page pageResult = devTypeInfoService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return LIST_PAGE;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	@Permission(resource=Permission.Resources.DEVTYPE,action=Permission.Actions.ADD)
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new DevTypeInfo());
		return ADD_PAGE;
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	@ResponseBody
	@Permission(resource=Permission.Resources.DEVTYPE,action=Permission.Actions.ADD)
	public ResultInfo save(HttpServletRequest request, Model model, DevTypeInfo entity) throws Exception
	{
		try
		{
			int resultTag = devTypeInfoService.save(entity);
			savedObjectForLog(entity);
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
	@Permission(resource=Permission.Resources.DEVTYPE,action=Permission.Actions.DELETE)
	public ResultInfo delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		DevTypeInfo entity=devTypeInfoService.findById(id);
		int resultTag = devTypeInfoService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{	savedObjectForLog(entity);
			return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), REDIRECT_PATH);
		}
		else
		{
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
		}
	}

	/** 进入编辑 */
	@RequestMapping(value = "/edit/{id}")
	@Permission(resource=Permission.Resources.DEVTYPE,action=Permission.Actions.UPDATE)
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		DevTypeInfo entity = devTypeInfoService.findById(id);
		
		savedObjectForLog(entity);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return EDIT_PAGE;
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	@Permission(resource=Permission.Resources.DEVTYPE,action=Permission.Actions.UPDATE)
	public ResultInfo update(HttpServletRequest request, Model model, DevTypeInfo entity) throws Exception
	{
		try
		{
			int resultTag = devTypeInfoService.update(entity);
			savedObjectForLog(entity);
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
