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
import com.njmd.zfms.web.entity.sys.SysFtp;
import com.njmd.zfms.web.service.SysFtpService;

@Controller
@RequestMapping("/ftpMgr")
public class FtpMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "FTP服务器信息","FTP地址"};
	// 基础目录
	private final String BASE_DIR = "/sys_mgr/ftp_mgr/";
	private final String LIST_PAGE = BASE_DIR + "ftp_list";
	private final String ADD_PAGE = BASE_DIR + "ftp_add";
	private final String EDIT_PAGE = BASE_DIR + "ftp_edit";

	private final String REDIRECT_PATH = "/ftpMgr";

	@Autowired
	private SysFtpService sysFtpService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.ASC);	
			page.setOrderBy("ftpId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		Page pageResult = sysFtpService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return LIST_PAGE;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new SysFtp());
		return ADD_PAGE;
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResultInfo save(HttpServletRequest request, Model model, SysFtp entity) throws Exception
	{
		try
		{
			int resultTag = sysFtpService.save(entity);
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
		//判断是否为默认记录
		if(0!=id){
			int resultTag = sysFtpService.delete(id);
			if (resultTag == ResultConstants.DELETE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), REDIRECT_PATH);
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
			}
		}else{
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.DELETE_SYSTEM_DEFAULT_ERROR));
		}
	}

	/** 进入编辑 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysFtp entity = sysFtpService.findById(id);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return EDIT_PAGE;
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResultInfo update(HttpServletRequest request, Model model, SysFtp entity) throws Exception
	{
		try
		{
			int resultTag = sysFtpService.update(entity);
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
	
	@RequestMapping(value = "/ftpSelect")
	public String ftpSelect(HttpServletRequest request, Page page, Model model) throws Exception
	{
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.ASC);
			page.setOrderBy("ftpId");
		}
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		
		PropertyFilter pf1 = new PropertyFilter("status", PropertyFilter.MatchType.EQ, "1");
		filters.add(pf1);
		
		Page pageResult = sysFtpService.query(page, filters);

		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "ftp_select";
	}

}
