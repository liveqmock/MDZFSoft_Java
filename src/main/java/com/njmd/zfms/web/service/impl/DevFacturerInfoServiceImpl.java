package com.njmd.zfms.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.dev.DevFacturerInfo;
import com.njmd.zfms.web.service.DevFacturerInfoService;
import com.njmd.zfms.web.service.DevInfoService;

/**
 * 设备厂商管理
 * 
 * @author Yao
 * 
 */
@Service
public class DevFacturerInfoServiceImpl extends BaseCrudServiceImpl<DevFacturerInfo, Long> implements DevFacturerInfoService
{
	@Autowired
	private DevInfoService devInfoService;

	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long id) throws Exception
	{
		if (devInfoService.getCountByFacturerId(id) > 0)
			return ResultConstants.DELETE_FAILED_IS_REF;

		DevFacturerInfo entity = baseDao.findById(id);
		baseDao.delete(entity);
		return ResultConstants.DELETE_SUCCEED;
	}

	@Autowired
	@Override
	@Qualifier(value = "devFacturerInfoDAO")
	public void setBaseDao(BaseHibernateDAO<DevFacturerInfo, Long> baseDao)
	{
		this.baseDao = baseDao;

	}

}
