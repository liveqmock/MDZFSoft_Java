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
import com.njmd.framework.dao.PropertyFilter.MatchType;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.notice.NoticeInfo;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.service.NoticeInfoService;
import com.njmd.zfms.web.service.SysCorpService;

@Controller
@RequestMapping("/noticeMgr")
public class NoticeMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "公告" };
	// 基础目录
	private final String BASE_DIR = "/notice_mgr/";

	private final String REDIRECT_PATH = "/noticeMgr";

	@Autowired
	private NoticeInfoService noticeInfoService;

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
			page.setOrderBy("noticeId");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);

		SysLogin sysLogin = this.getLoginToken().getSysLogin();

		if (sysLogin.getUserType().longValue() != SysLogin.USER_TYPE_SUPER_ADMIN)
		{
			filters.add(new PropertyFilter("corpId", MatchType.EQ, sysLogin.getSysCorp().getCorpId()));
		}
		Page pageResult = noticeInfoService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "notice_list";
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		Tree tree = sysCorpService.getCorpTree(request);
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new NoticeInfo());
		return BASE_DIR + "notice_add";
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public ResultInfo save(HttpServletRequest request, Model model, NoticeInfo entity) throws Exception
	{
		try
		{
			int resultTag = noticeInfoService.save(entity);
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
			logger.error("公告保存异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}

	}

	/** 删除 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	public ResultInfo delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{

		int resultTag = noticeInfoService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{
			return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), REDIRECT_PATH);
		}
		else
		{
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
		}
	}

	/** 批量删除 */
	@RequestMapping(value = "/batchDelete")
	public String batchDelete(HttpServletRequest request, Model model, Long[] id) throws Exception
	{

		int resultTag = noticeInfoService.delete(id);
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

		NoticeInfo entity = noticeInfoService.findById(id);
		Tree tree = sysCorpService.getCorpTree(request);
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);

		return BASE_DIR + "notice_edit";
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResultInfo update(HttpServletRequest request, Model model, NoticeInfo entity) throws Exception
	{
		try
		{
			int resultTag = noticeInfoService.update(entity);
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
			logger.error("公告修改异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}

	}
}
