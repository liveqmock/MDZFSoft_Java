package com.njmd.zfms.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
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
import com.njmd.framework.utils.web.RequestUtils;
import com.njmd.zfms.annotation.Permission;
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
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.LIST)
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
		
		List<SysCorp> childrens=sysCorpService.findByParentId(this.getLoginToken().getSysCorp().getCorpId());
		List childrenIds=new ArrayList();
		childrenIds.add(this.getLoginToken().getSysCorp().getCorpId());
		for(SysCorp sysCorp:childrens){
			childrenIds.add(sysCorp.getCorpId());
		}
		PropertyFilter pf3= new PropertyFilter("sysCorp.corpId",PropertyFilter.MatchType.IN,childrenIds);
		
		filters.add(pf);
		filters.add(pf2);
		filters.add(pf3);
		Page pageResult = sysLoginService.query(page, filters);
		Tree tree = sysCorpService.getCorpTree(request, false);

		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "user_list";
	}

	/** 查看 */
	@RequestMapping(value = "/view/{id}")
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.VIEW)
	public String view(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysLogin entity = sysLoginService.findById(id);
		List<SysRole> roleList = sysRoleService.findAll();
		model.addAttribute("roleList", roleList);
		List<SysCorp> corpList = sysCorpService.findAll();

		savedObjectForLog(entity);
		model.addAttribute("roleList", roleList);
		model.addAttribute("corpList", corpList);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return BASE_DIR + "user_view";

	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.ADD)
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
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.ADD)
	public ResultInfo save(HttpServletRequest request, Model model, SysLogin entity, Long[] roleIds, Long deptId) throws Exception
	{
		try
		{
			entity.setSystemId(this.getLoginToken().getSysLogin().getSystemId());
			entity.setLoginPwd(CommonConstants.DEFAULT_PWD);
			int resultTag = sysLoginService.save(entity, roleIds, deptId);
			savedObjectForLog(entity);
			if (resultTag == ResultConstants.SAVE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else if(resultTag==ResultConstants.SAVE_FAILED_CODE_IS_EXIST)
			{
				String[] PARAMAS = { "用户", "警员编号" };
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, PARAMAS));
			}else if(resultTag==ResultConstants.SAVE_FAILED_IDCARD_IS_EXIST){
				String[] PARAMAS = { "用户", "身份证号" };
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, PARAMAS));				
			}else{
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
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
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.UPDATE)
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysLogin entity = sysLoginService.findById(id);
		List<SysRole> roleList = sysRoleService.findAll();
		model.addAttribute("roleList", roleList);
		List<SysCorp> corpList = sysCorpService.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("corpList", corpList);
		Tree tree = sysCorpService.getCorpTree(request, false);

		savedObjectForLog(entity);
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		return BASE_DIR + "user_edit";

	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	@ResponseBody
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.UPDATE)
	public ResultInfo update(HttpServletRequest request, Model model, SysLogin entity, Long[] roleIds, String newLoginPwd) throws Exception
	{
		try
		{
			int resultTag = sysLoginService.update(entity, roleIds, newLoginPwd);
			savedObjectForLog(entity);
			if (resultTag == ResultConstants.UPDATE_SUCCEED)
			{
				return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
			}
			else if (resultTag == ResultConstants.UPDATE_FAILED_CODE_IS_EXIST)
			{	
				String[] PARAMAS = { "用户", "警员编号" };
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, PARAMAS));
			}else if(resultTag== ResultConstants.UPDATE_FAILED_IDCARD_IS_EXIST){
				String[] PARAMAS = { "用户", "身份证号" };
				model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, PARAMAS));
			
			}
			else
			{
				return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
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
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.DELETE)
	public String delete(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		SysLogin entity=sysLoginService.findById(id);
		int resultTag = sysLoginService.delete(id);
		if (resultTag == ResultConstants.DELETE_SUCCEED)
		{	savedObjectForLog(entity);
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
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.BATCHDELETE)
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
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.RESETPWD)
	public ResultInfo resetPwd(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		try
		{
			int resultTag = sysLoginService.resetPwd(id);
			if (resultTag == ResultConstants.UPDATE_SUCCEED)
			{	savedObjectForLog(sysLoginService.findById(id));
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
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.SELECT)
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
		
		PropertyFilter pf1 = new PropertyFilter("loginId", PropertyFilter.MatchType.NE, "0");
		filters.add(pf1);
		
		Page pageResult = sysLoginService.query(page, filters);
		Tree tree = sysCorpService.getCorpTree(request, false);

		//EditBy 孙强伟,fixedCorpId是用来标志是否已经选择部门
		model.addAttribute("fixedCorpId",request.getParameter("fixedCorpId"));
		model.addAttribute("tree", tree);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		return BASE_DIR + "user_select";
	}
	
	/**
	 * <p>
	 * Description:[用户修改密码]
	 * </p>
	 * 
	 * @param request
	 * @param loginName
	 * @param loginPwd
	 * @param newLoginPwd
	 * @return
	 */
	@RequestMapping(value = "/updatePwd")
	@ResponseBody
	@Permission(resource=Permission.Resources.SYSLOGIN,action=Permission.Actions.UPDATEPWD)
	public ResultInfo updatePwd(HttpServletRequest request, Model model, String loginPwd, String newLoginPwd) throws Exception
	{
		int resultTag = sysLoginService.updPassword(loginPwd, newLoginPwd, request);
		if (resultTag == ResultConstants.UPDATE_SUCCEED)
		{
			model.addAttribute(RequestNameConstants.RESULT_OBJECT, this.getLoginToken().getSysLogin());
			savedObjectForLog(this.getLoginToken().getSysLogin());
			return ResultInfo.saveMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS), null);
		}
		else
		{
			return ResultInfo.saveErrorMessage(ResultConstants.getResultInfo(resultTag, INFORMATION_PARAMAS));
		}
	}
}
