package com.njmd.zfms.web.controller;

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
import com.njmd.zfms.annotation.Permission;
import com.njmd.zfms.web.constants.CommonConstants;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.service.SysCorpService;

@Controller
@RequestMapping("/corpMgr")
public class CorpMgrController extends BaseController
{

	private final String[] INFORMATION_PARAMAS = { "部门", "部门名称" };
	// 基础目录
	private final String BASE_DIR = "/sys_mgr/corp_mgr/";

	private final String REDIRECT_PATH = "/corpMgr";

	@Autowired
	private SysCorpService sysCorpService;

	/** 列表查询 */
	@RequestMapping
	@Permission(resource=Permission.Resources.SYSCORP,action=Permission.Actions.LIST)
	public String index(HttpServletRequest request, Model model) throws Exception
	{
		// 设置默认排序方式
		Tree tree = sysCorpService.getCorpTree(request, false);
		model.addAttribute("tree", tree);
		return BASE_DIR + "corp_list";
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	@Permission(resource=Permission.Resources.SYSCORP,action=Permission.Actions.ADD)
	public String add(HttpServletRequest request, Long parentCorpId, Model model) throws Exception
	{
		SysCorp sysCorp = new SysCorp();
		sysCorp.setStatus(CommonConstants.STATUS_VALID);
		sysCorp.setCorpType(1);
		if (parentCorpId != null)
		{
			SysCorp entity = sysCorpService.findById(parentCorpId);
			sysCorp.setParentCorpId(parentCorpId);
			sysCorp.setParentCorpName(entity.getCorpName());
			//EditBy 孙强伟  at 20130711 ，当新建下级部门时，如果上级部门已经设置了文件服务器，
			//则子部门默认使用的文件服务器与父部门的文件服务器一致。
			model.addAttribute("parent",entity);
		}
		Tree tree = sysCorpService.getCorpTree(request, false);
		model.addAttribute("tree", tree);

		model.addAttribute(RequestNameConstants.RESULT_OBJECT, sysCorp);
		return BASE_DIR + "corp_add";
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	@ResponseBody
	@Permission(resource=Permission.Resources.SYSCORP,action=Permission.Actions.ADD)
	public ResultInfo save(HttpServletRequest request, Model model, SysCorp entity) throws Exception
	{
		try
		{
			int resultTag = sysCorpService.save(entity);
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
			logger.error("部门保存异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}
	}

	/** 删除 */
	@RequestMapping(value = "/delete/{id}")
	@ResponseBody
	@Permission(resource=Permission.Resources.SYSCORP,action=Permission.Actions.DELETE)
	public ResultInfo delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysCorp entity=sysCorpService.findById(id);
		int resultTag = sysCorpService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{	savedObjectForLog(entity);
			return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), REDIRECT_PATH);
		}
		else
		{
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
		}
	}

	/** 批量删除 */
	@RequestMapping(value = "/batchDelete")
	@Permission(resource=Permission.Resources.SYSCORP,action=Permission.Actions.BATCHDELETE)
	public String batchDelete(HttpServletRequest request, Model model, Long[] id) throws Exception
	{

		int resultTag = sysCorpService.delete(id);
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
	@Permission(resource=Permission.Resources.SYSCORP,action=Permission.Actions.UPDATE)
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysCorp entity = sysCorpService.findById(id);
		Tree tree = sysCorpService.getCorpTree(request, false);
		
		savedObjectForLog(entity);
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);

		return BASE_DIR + "corp_edit";
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	@Permission(resource=Permission.Resources.SYSCORP,action=Permission.Actions.UPDATE)
	public ResultInfo update(HttpServletRequest request, Model model, SysCorp entity) throws Exception
	{
		try
		{
			int resultTag = sysCorpService.update(entity);
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
			logger.error("部门修改异常", t);
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(ResultConstants.SYSTEM_ERROR, INFORMATION_PARAMAS));
		}
	}

}
