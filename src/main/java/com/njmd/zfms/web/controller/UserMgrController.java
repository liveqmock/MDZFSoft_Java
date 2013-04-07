package com.njmd.zfms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.njmd.framework.commons.ResultInfo;
import com.njmd.framework.controller.BaseController;
import com.njmd.framework.dao.HibernateWebUtils;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.zfms.web.constants.RequestNameConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.UrlConstants;
import com.njmd.zfms.web.entity.sys.SysDept;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysLoginRole;
import com.njmd.zfms.web.entity.sys.SysRole;
import com.njmd.zfms.web.service.SysDeptService;
import com.njmd.zfms.web.service.SysLoginService;
import com.njmd.zfms.web.service.SysRoleService;

@Controller
@RequestMapping("/userMgr")
public class UserMgrController extends BaseController
{
	private final String[] INFORMATION_PARAMAS = { "用户" ,"用户账号"};
	// 基础目录
	private final String BASE_DIR = "/sys_mgr/user_mgr/";

	private final String REDIRECT_PATH = "/userMgr";

	@Autowired
	private SysLoginService sysLoginService;

	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysDeptService sysDeptService;

	/** 列表查询 */
	@RequestMapping
	public String index(HttpServletRequest request, Page page, Model model) throws Exception
	{
		List<SysDept> deptList = sysDeptService.findAll();
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
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		model.addAttribute("deptList", deptList);
		model.addAttribute("deptListSize", deptList.size());
		return BASE_DIR + "list";
	}
	
	@RequestMapping(value = "/list/{id}")
	public String list(HttpServletRequest request,Page page, Model model,@PathVariable("id") Long deptId) throws Exception{
		List<SysDept> deptList = sysDeptService.findAll();
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrder(Page.ASC);
			page.setOrderBy("loginName");
		}
		Long loginId = this.getLoginToken().getSysLogin().getLoginId();
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);
		PropertyFilter pf = new PropertyFilter("loginId", PropertyFilter.MatchType.NE, loginId);
		PropertyFilter pf2 = new PropertyFilter("sysDept.deptId", PropertyFilter.MatchType.EQ, deptId);
		filters.add(pf);
		filters.add(pf2);
		Page pageResult = sysLoginService.query(page, filters);
		model.addAttribute(RequestNameConstants.PAGE_OBJECT, pageResult);
		model.addAttribute("deptId", deptId);
		model.addAttribute("deptList", deptList);
		model.addAttribute("deptListSize", deptList.size());
		return BASE_DIR + "list";
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model) throws Exception
	{
		List<SysDept> deptList = sysDeptService.findAll();
		List<SysRole> roleList = sysRoleService.findByCorpId(this.getLoginToken().getSysCorp().getCorpId());
		model.addAttribute("roleList", roleList);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new SysLogin());
		model.addAttribute("deptList", deptList);
		model.addAttribute("deptListSize", deptList.size());
		return BASE_DIR + "add";
	}

	/** 保存新增 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model, SysLogin entity,Long[] roleIds,Long deptId) throws Exception
	{
		entity.setSystemId(this.getLoginToken().getSysLogin().getSystemId());
		entity.setSysCorp(this.getLoginToken().getSysCorp());
		int resultTag = sysLoginService.save(entity,roleIds,deptId);
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

	/** 进入编辑 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id) throws Exception
	{
		List<SysDept> deptList = sysDeptService.findAll();
		model.addAttribute("deptList", deptList);
		model.addAttribute("deptListSize", deptList.size());
		List<SysRole> roleList = sysRoleService.findByCorpId(this.getLoginToken().getSysCorp().getCorpId());
		model.addAttribute("roleList", roleList);
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, new SysLogin());
		model.addAttribute("currenDate", DateTimeUtil.getChar8());
		SysLogin entity = sysLoginService.findById(id);
		List<SysLoginRole> list = entity.getSysLoginRoles();
		String roleIds ="";
		for(int i = 0;i<list.size();i++){
			String roleId = list.get(i).getSysRole().getRoleId().toString();
			if(i == (list.size() -1)){
				roleIds+=roleId;
			}else{
				roleIds+=roleId+",";
			}
		}
		model.addAttribute(RequestNameConstants.RESULT_OBJECT, entity);
		model.addAttribute("roleIds", roleIds);
		model.addAttribute("deptList", deptList);
		model.addAttribute("deptListSize", deptList.size());
		return BASE_DIR + "edit";
	}

	/** 修改保存 */
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, Model model, SysLogin entity,Long[] roleIds,String newLoginPwd ,Long deptId) throws Exception
	{
		int resultTag = sysLoginService.update(entity,roleIds,newLoginPwd,deptId);
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

	/**
	 * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("entity")
	private SysLogin getModel(@RequestParam(value = "id", required = false) Long id) throws Exception
	{
		if (id != null)
		{
			return sysLoginService.findById(id);
		}
		return null;
	}
}
