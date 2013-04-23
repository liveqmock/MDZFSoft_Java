package com.njmd.zfms.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.file.FileTypeInfo;
import com.njmd.zfms.web.entity.sys.SysCorp;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.service.FileTypeInfoService;
import com.njmd.zfms.web.service.FileUploadInfoService;

/**
 * 设备类型管理
 * 
 * @author Yao
 * 
 */
@Service
public class FileTypeInfoServiceImpl extends BaseCrudServiceImpl<FileTypeInfo, Long> implements FileTypeInfoService
{
	@Autowired
	private FileUploadInfoService fileUploadInfoService;

	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long id) throws Exception
	{
		if (fileUploadInfoService.getCountByTypeId(id) > 0)
			return ResultConstants.DELETE_FAILED_IS_REF;

		FileTypeInfo fileTypeInfo = baseDao.findById(id);
		baseDao.delete(fileTypeInfo);
		return ResultConstants.DELETE_SUCCEED;
	}

	@Autowired
	@Override
	@Qualifier(value = "fileTypeInfoDAO")
	public void setBaseDao(BaseHibernateDAO<FileTypeInfo, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

}
