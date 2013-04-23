package com.njmd.zfms.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.dev.DevTypeInfo;
import com.njmd.zfms.web.entity.file.FileTypeInfo;
import com.njmd.zfms.web.service.DevInfoService;
import com.njmd.zfms.web.service.DevTypeInfoService;

/**
 * 设备类型管理
 * 
 * @author Yao
 * 
 */
@Service
public class DevTypeInfoServiceImpl extends BaseCrudServiceImpl<DevTypeInfo, Long> implements DevTypeInfoService
{
	@Autowired
	private DevInfoService devInfoService;

	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long id) throws Exception
	{
		if (devInfoService.getCountByTypeId(id) > 0)
			return ResultConstants.DELETE_FAILED_IS_REF;

		DevTypeInfo entity = baseDao.findById(id);
		baseDao.delete(entity);
		return ResultConstants.DELETE_SUCCEED;
	}

	@Autowired
	@Override
	@Qualifier(value = "devTypeInfoDAO")
	public void setBaseDao(BaseHibernateDAO<DevTypeInfo, Long> baseDao)
	{
		this.baseDao = baseDao;

	}

}
