/**
 * 
 */
package com.njmd.zfms.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.MD5Utils;
import com.njmd.framework.utils.web.SessionUtils;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.constants.CommonConstants;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.constants.SessionNameConstants;
import com.njmd.zfms.web.dao.SysLoginDAO;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.entity.sys.SysLoginRole;
import com.njmd.zfms.web.entity.sys.SysPermission;
import com.njmd.zfms.web.entity.sys.SysRole;
import com.njmd.zfms.web.entity.sys.SysRolePermission;
import com.njmd.zfms.web.service.SysLogService;
import com.njmd.zfms.web.service.SysLoginRoleService;
import com.njmd.zfms.web.service.SysLoginService;
import com.njmd.zfms.web.service.SysPermissionService;

/**
 * @title:
 * @description:
 * 
 * @author: dongyuese
 * 
 */
@Service
public class SysLoginServiceImpl extends BaseCrudServiceImpl<SysLogin, Long> implements SysLoginService
{

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private SysLoginRoleService sysLoginRoleService;

	@Override
	@Autowired
	@Qualifier(value = "sysLoginDAO")
	public void setBaseDao(BaseHibernateDAO<SysLogin, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int login(String loginName, String loginPwd, String checkCode, Long systemId, HttpServletRequest request) throws Exception
	{
		// 检查验证码是否正确
		if (!SessionUtils.checkSessionValue(request, SessionNameConstants.IMG_CHECK_CODE, checkCode))// 验证失败
		{
			return ResultConstants.IMG_CODE_FAILED;
		}

		// 检查登录名和密码是否正确 MD5Utils.toMD5(loginPwd) 
		SysLogin sysLogin = ((SysLoginDAO) baseDao).login(loginName, MD5Utils.toMD5(loginPwd), systemId);
		if (sysLogin == null)
		{
			return ResultConstants.LOGIN_INFO_FAILED;
		}
		if (sysLogin.getStatus().equals(CommonConstants.STATUS_INVALID))
		{
			return ResultConstants.LOGIN_INFO_INVAILD;
		}

		// 加载登录用户的相关信息到登录令牌
		LoginToken loginToken = this.getAdminLoginToken(sysLogin);
		// 保存登录用户信息到http session
		SessionUtils.setObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN, loginToken);

		// 修改最后登录时间
		sysLogin.setLoginLastTime(DateTimeUtil.getChar14());
		baseDao.update(sysLogin);
		// 保存系统日志
		sysLogService.save(SysLog.OPERATE_TYPE_LOGIN, "【用户登录】用户名：" + sysLogin.getLoginName());
		return ResultConstants.LOGIN_SUCCESS;

	}

	/**
	 * 根据密码和新密码 进行密码修改
	 * 
	 * @param loginPwd
	 *            原来的密码
	 * @param newLoginPwd
	 *            新密码
	 * @return 成功或者失败
	 * @throws Exception
	 *             抛出异常
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int updPassword(String loginPwd, String newLoginPwd, HttpServletRequest request) throws Exception
	{
		LoginToken loginToken = (LoginToken) SessionUtils.getObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
		if (loginToken.getSysLogin().getLoginPwd().equals(MD5Utils.toMD5(loginPwd)))
		{
			SysLogin login = loginToken.getSysLogin();
			// 用MD5算法加密
			login.setLoginPwd(MD5Utils.toMD5(newLoginPwd));
			baseDao.update(login);
			// 保存系统日志
			String operDesc = "【修改密码】用户名:" + login.getLoginName();
			sysLogService.save(SysLog.OPERATE_TYPE_UPDATE, operDesc);
			return ResultConstants.UPDATE_SUCCEED;
		}
		else
			return ResultConstants.UPDATE_FAILED;
	}

	/**
	 * 加载登录用户的相关信息到登录令牌
	 * 
	 * @param frameSysLogin
	 * @return
	 */
	public LoginToken getAdminLoginToken(SysLogin sysLogin) throws Exception
	{
		LoginToken loginToken = new LoginToken();
		loginToken.setSysLogin(sysLogin);
		// 获得该登录者单位信息
		if (sysLogin.getSysCorp() != null)
		{
			Hibernate.initialize(sysLogin.getSysCorp());
			loginToken.setSysCorp(sysLogin.getSysCorp());
		}
		List<SysRole> roles = new ArrayList<SysRole>();
		if (sysLogin.getSysLoginRoles() != null && sysLogin.getSysLoginRoles().size() > 0)
		{

			List<SysLoginRole> sysLoginRoles = sysLogin.getSysLoginRoles();
			for (SysLoginRole sysLoginRole : sysLoginRoles)
			{
				roles.add(sysLoginRole.getSysRole());
			}

		}
		else
		{
			SysRole role = new SysRole();
			role.setRoleName("超级管理员");
			roles.add(role);
		}
		loginToken.setSysRoles(roles);
		// 设置登录信息

		Map<Long, SysPermission> menuPermissions = new HashMap<Long, SysPermission>();
		List<SysPermission> menus = null;
		// 如果是超级管理员用户，则显示所有菜单
		if (sysLogin.getUserType().longValue() == SysLogin.USER_TYPE_SUPER_ADMIN)
		{
			// 获得本系统下所有的菜单项和权限项
			menus = sysPermissionService.findMenuBySystemId(sysLogin.getSystemId());
			for (SysPermission menu : menus)
				menuPermissions.put(menu.getPermissionId(), menu);

		}
		// 如果不是超级管理员，则根据分配的角色进行权限查询
		else if (sysLogin.getSysLoginRoles() != null)
		{
			for (SysLoginRole sysLoginRole : sysLogin.getSysLoginRoles())
			{
				SysRole sysRole = sysLoginRole.getSysRole();

				for (SysRolePermission rolePermission : sysRole.getSysRolePermissions())
					menuPermissions.put(rolePermission.getSysPermission().getPermissionId(), rolePermission.getSysPermission());

			}
		}
		loginToken.setMenuPermissions(menuPermissions);

		// 取得数据库中所有一级菜单列表
		List<SysPermission> allLevel1MenuList = sysPermissionService.findLevel1Menu(1);

		List<SysPermission> level1MenuList = new ArrayList<SysPermission>();
		Map<Long, List<SysPermission>> level2MenuMap = new HashMap<Long, List<SysPermission>>();

		if (allLevel1MenuList != null && allLevel1MenuList.size() > 0)
		{
			for (SysPermission level1Menu : allLevel1MenuList)
			{
				Long level1MenuId = level1Menu.getPermissionId();

				List<SysPermission> allLevel2Menus = sysPermissionService.findMenuByparentIdAndType(level1MenuId, SysPermission.PERMISSION_TYPE_1);
				List<SysPermission> level2Menus = new ArrayList<SysPermission>();
				if (allLevel2Menus != null && allLevel2Menus.size() > 0)
				{
					for (SysPermission level2menu : allLevel2Menus)
					{
						Long level2MenuId = level2menu.getPermissionId();
						if (menuPermissions.containsKey(level2MenuId))
						{
							level2Menus.add(level2menu);
						}
					}
					if (level2Menus.size() > 0)
					{
						level1MenuList.add(level1Menu);
						level2MenuMap.put(level1MenuId, level2Menus);
					}
				}
			}
		}
		loginToken.setLevel1MenuList(level1MenuList);
		loginToken.setLevel2MenuMap(level2MenuMap);
		return loginToken;
	}

	@Override
	public List<SysLogin> findByCorpId(Long corpId) throws Exception
	{
		String hql = "from SysLogin s where s.sysCorp.corpId=? and status=1";
		return baseDao.findByHql(hql, corpId);
	}

	@Override
	public long getCountByDeptId(Long deptId) throws Exception
	{
		String hql = "select count(*) from SysLogin as model where model.sysDept.deptId= ?)";
		long count = baseDao.findLong(hql, deptId);
		return count;
	}

	@Override
	public long getCountByDeptIds(Long[] deptIds) throws Exception
	{
		String hql = "select count(*) from SysLogin as model where model.sysDept.deptId in (:deptIds))";
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("deptIds", deptIds);
		long count = baseDao.findLong(hql, m);
		return count;
	}

	@Override
	@Transactional(readOnly = false)
	public int save(SysLogin entity, Long[] roleIds, Long deptId) throws Exception
	{
		String hql = "select count(*) from SysLogin as model where model.loginName= ?)";
		long count = baseDao.findLong(hql, entity.getLoginName());
		if (count > 0)
		{
			return ResultConstants.SAVE_FAILED_NAME_IS_EXIST;
		}

		hql = "select count(*) from SysLogin as model where model.userCode= ?)";
		count = baseDao.findLong(hql, entity.getUserCode());
		if (count > 0)
		{
			return ResultConstants.SAVE_FAILED_CODE_IS_EXIST;
		}

		entity.setLoginPwd(MD5Utils.toMD5(entity.getLoginPwd()));
		baseDao.save(entity);
		sysLoginRoleService.saveLoginRole(entity, roleIds);
		// 保存系统日志
		String operDesc = "【用户新增】用户名:" + entity.getLoginName();
		sysLogService.save(SysLog.OPERATE_TYPE_ADD, operDesc);
		return ResultConstants.SAVE_SUCCEED;
	}

	@Override
	@Transactional(readOnly = false)
	public int update(SysLogin entity, Long[] roleIds, String newLoginPwd) throws Exception
	{
		String hql = "select count(*) from SysLogin as model where model.loginName= ? and model.loginId != ?)";
		long count = baseDao.findLong(hql, entity.getLoginName(), entity.getLoginId());
		if (count > 0)
		{
			return ResultConstants.UPDATE_FAILED_NAME_IS_EXIST;
		}

		hql = "select count(*) from SysLogin as model where model.userCode= ? and model.loginId != ?)";
		count = baseDao.findLong(hql, entity.getUserCode(), entity.getLoginId());
		if (count > 0)
		{
			return ResultConstants.UPDATE_FAILED_CODE_IS_EXIST;
		}

		if (newLoginPwd != null && !"".equals(newLoginPwd))
		{
			entity.setLoginPwd(MD5Utils.toMD5(newLoginPwd));
		}
		baseDao.update(entity);
		sysLoginRoleService.updateLoginRole(entity, roleIds);
		// 保存系统日志
		String operDesc = "【用户修改】用户名：" + entity.getLoginName();
		sysLogService.save(SysLog.OPERATE_TYPE_UPDATE, operDesc);
		return ResultConstants.UPDATE_SUCCEED;
	}

	@Override
	public List<SysLogin> findByRoleId(Long roleId) throws Exception
	{
		return null;
	}

	@Override
	public SysLogin findByLoginName(String loginName) throws Exception
	{
		String hql = "from SysLogin where loginName=?";
		List<SysLogin> result = baseDao.findByHql(hql, loginName);
		if (result != null && result.size() > 0)
			return result.get(0);
		else
			return null;
	}

	@Override
	@Transactional(readOnly = false)
	public int resetPwd(Long loginId) throws Exception
	{
		SysLogin sysLogin = baseDao.findById(loginId);
		sysLogin.setLoginPwd(MD5Utils.toMD5(CommonConstants.DEFAULT_PWD));
		baseDao.update(sysLogin);
		// 保存系统日志
		String operDesc = "【密码重置】用户名：" + sysLogin.getLoginName();
		sysLogService.save(SysLog.OPERATE_TYPE_UPDATE, operDesc);
		return ResultConstants.UPDATE_SUCCEED;
	}

}
