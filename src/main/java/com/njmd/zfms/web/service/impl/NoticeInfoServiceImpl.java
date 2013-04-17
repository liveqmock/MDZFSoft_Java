package com.njmd.zfms.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.entity.notice.NoticeInfo;
import com.njmd.zfms.web.service.NoticeInfoService;

/**
 * 系统公告业务处理实现类
 * 
 * @author Yao
 * 
 */
@Service
public class NoticeInfoServiceImpl extends BaseCrudServiceImpl<NoticeInfo, Long> implements NoticeInfoService
{

	@Autowired
	@Override
	@Qualifier(value = "noticeInfoDAO")
	public void setBaseDao(BaseHibernateDAO<NoticeInfo, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

}
