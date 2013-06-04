package com.njmd.zfms.web.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.dev.DevInfo;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.entity.sys.SysLogin;
import com.njmd.zfms.web.service.DevInfoService;
import com.njmd.zfms.web.service.SysLogService;
import com.njmd.zfms.web.service.SysLoginService;

/**
 * 设备管理
 * 
 * @author Yao
 * 
 */
@Service
public class DevInfoServiceImpl extends BaseCrudServiceImpl<DevInfo, Long> implements DevInfoService
{
	@Autowired
	private SysLogService sysLogBO;

	@Autowired
	private SysLoginService sysLoginBO;

	/**
	 * 设备保存
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int save(DevInfo devInfo) throws Exception
	{
		// 判断是否已经该设备编号
		if (baseDao.findUnique("devNo", devInfo.getDevNo()) == null)
		{
			if (devInfo.getDevUserInfo() != null && devInfo.getDevUserInfo().getLoginId() != null)
			{
				//使用单位已经属于必填项目，因此可以注释掉。    EditBy 孙强伟
//				SysLogin sysLogin = sysLoginBO.findById(devInfo.getDevUserInfo().getLoginId());
//				devInfo.setSysCorp(sysLogin.getSysCorp());
			}
			else
				devInfo.setDevUserInfo(null);
			baseDao.save(devInfo);
			sysLogBO.save(SysLog.OPERATE_TYPE_ADD, "【设备新增】设备编号：" + devInfo.getDevNo());
			return ResultConstants.SAVE_SUCCEED;
		}
		return ResultConstants.SAVE_FAILED_NAME_IS_EXIST;
	}

	/**
	 * 设备修改
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int update(DevInfo devInfo) throws Exception
	{
		DevInfo devInfoTemp = baseDao.findUnique("devNo", devInfo.getDevNo());

		// 修改后的设备编号是否跟原有的设备编号存在重复
		if ((devInfoTemp != null) && (devInfoTemp.getDevId().longValue() != devInfo.getDevId().longValue()))
			return ResultConstants.UPDATE_FAILED_NAME_IS_EXIST;

		if (devInfoTemp == null)
		{
			devInfoTemp = new DevInfo();
		}
		if (devInfo.getDevUserInfo() != null)
		{
//			SysLogin sysLogin = sysLoginBO.findById(devInfo.getDevUserInfo().getLoginId());
//			devInfo.setSysCorp(sysLogin.getSysCorp());
		}
		BeanUtils.copyProperties(devInfoTemp, devInfo);

		baseDao.update(devInfoTemp);
		sysLogBO.save(SysLog.OPERATE_TYPE_UPDATE, "【设备更新】设备编号：" + devInfo.getDevNo());
		return ResultConstants.UPDATE_SUCCEED;
	}

	/**
	 * 设备删除
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long id) throws Exception
	{
		DevInfo devInfo = baseDao.findById(id);
		baseDao.delete(devInfo);
		sysLogBO.save(SysLog.OPERATE_TYPE_DELETE, "【设备删除】设备编号：" + devInfo.getDevNo());
		return ResultConstants.DELETE_SUCCEED;
	}

	@Autowired
	@Override
	@Qualifier(value = "devInfoDAO")
	public void setBaseDao(BaseHibernateDAO<DevInfo, Long> baseDao)
	{
		this.baseDao = baseDao;

	}

	@Override
	public long getCountByTypeId(Long typeId)
	{
		String hql = "select count(model.devId) from DevInfo model where model.devTypeInfo.devTypeId=?";
		return baseDao.findLong(hql, typeId);
	}

	@Override
	public long getCountByFacturerId(Long facturerId)
	{
		String hql = "select count(model.devId) from DevInfo model where model.devFacturerInfo.devFacturerId=?";
		return baseDao.findLong(hql, facturerId);
	}

}
