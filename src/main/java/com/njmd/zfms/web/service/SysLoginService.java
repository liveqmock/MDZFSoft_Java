package com.njmd.zfms.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.entity.sys.SysLogin;

public interface SysLoginService extends BaseCrudService<SysLogin, Long>
{
	int login(String loginName, String loginPwd, String checkCode, Long systemId, HttpServletRequest request) throws Exception;

	SysLogin login(String loginName, String loginPwd, HttpServletRequest request) throws Exception;

	int updPassword(String loginPwd, String newLoginPwd, HttpServletRequest request) throws Exception;

	List<SysLogin> findByCorpId(Long corpId) throws Exception;

	List<SysLogin> findByRoleId(Long roleId) throws Exception;

	public long getCountByDeptId(Long deptId) throws Exception;

	public long getCountByDeptIds(Long[] deptIds) throws Exception;

	public int save(SysLogin entity, Long[] roleIds, Long deptId) throws Exception;

	public int update(SysLogin entity, Long[] roleIds, String newLoginPwd, Long deptId) throws Exception;

	public SysLogin findByLoginName(String loginName) throws Exception;

	public LoginToken getAdminLoginToken(SysLogin sysLogin) throws Exception;
}
