package com.njmd.zfms.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.sys.SysDept;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.service.SysDeptService;
import com.njmd.zfms.web.service.SysLogService;
import com.njmd.zfms.web.service.SysLoginService;

/**
 * 系统部门相关服务
 * 
 * @author yao
 * 
 */
@Service
@Transactional(readOnly = true)
public class SysDeptServiceImpl extends BaseCrudServiceImpl<SysDept, Long> implements SysDeptService
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public SysLoginService sysLoginService;

	/**
	 * 系统日志操作接口
	 */
	@Autowired
	private SysLogService sysLogService;

	@Autowired
	@Override
	@Qualifier(value = "sysDeptDAO")
	public void setBaseDao(BaseHibernateDAO<SysDept, Long> baseDao)
	{
		this.baseDao = baseDao;

	}

	@Override
	public List<SysDept> findByCorpId(Long corpId)
	{
		return baseDao.findByProperty("corpId", corpId);
	}

	@Override
	@Transactional(readOnly = false)
	public int save(SysDept entity)
	{
		Object[] params = { entity.getCorpId(), entity.getDeptName() };
		String hql = "select count(*) from SysDept as model where model.corpId= ? and model.deptName=?)";
		long count = baseDao.findLong(hql, params);
		if (count > 0)
		{
			return ResultConstants.SAVE_FAILED_NAME_IS_EXIST;
		}
		else
		{
			baseDao.save(entity);
			return ResultConstants.SAVE_SUCCEED;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(Long deptId) throws Exception
	{
		long count = sysLoginService.getCountByDeptId(deptId);

		if (count > 0)
		{
			return ResultConstants.DELETE_FAILED;
		}
		else
		{
			baseDao.deleteById(deptId);
			return ResultConstants.DELETE_SUCCEED;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public int delete(Long[] deptIds) throws Exception
	{
		long count = sysLoginService.getCountByDeptIds(deptIds);

		if (count > 0)
		{
			return ResultConstants.DELETE_FAILED;
		}
		else
		{
			super.delete(deptIds);
			return ResultConstants.DELETE_SUCCEED;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public int update(SysDept entity)
	{
		String hql = "select count(*) from SysDept as dept where dept.deptName=? and dept.deptId!=? and dept.corpId=?";
		long count = baseDao.findLong(hql, entity.getDeptName(), entity.getDeptId(), entity.getCorpId());
		if (count > 0)
		{
			return ResultConstants.UPDATE_FAILED_NAME_IS_EXIST;
		}
		else
		{
			baseDao.update(entity);
			sysLogService.save(SysLog.OPERATE_TYPE_UPDATE, "更新部门成功,deptId:" + entity.getDeptId());
		}
		return ResultConstants.UPDATE_SUCCEED;
	}

}
