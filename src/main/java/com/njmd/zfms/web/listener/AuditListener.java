package com.njmd.zfms.web.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;

import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.entity.AuditableEntity;

/**
 * 在自动为entity添加审计信息的Hibernate EventListener.
 * 
 * 在hibernate执行saveOrUpdate()时,自动为AuditableEntity的子类添加审计信息.
 * 
 * @author Yao
 * 
 */
@SuppressWarnings("serial")
public class AuditListener implements SaveOrUpdateEventListener
{

	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException
	{
		final Object object = event.getObject();
		// 如果对象是AuditableEntity子类,添加审计信息.
		if (object instanceof AuditableEntity)
		{
			AuditableEntity entity = (AuditableEntity) object;
			LoginToken loginToken = WebContextHolder.getCurrLoginToken();
			// 此处不区分是增加还是修改，Hibernate会根据Entity的配置来增加或者更新字段
			/*
			 * if (entity.getCreateTime() == null) {
			 */
			// 创建新对象
			entity.setCreateTime(DateTimeUtil.getChar14());
			if (loginToken != null)
			{
				entity.setCreateBy(loginToken.getSysLogin().getLoginName());
			}
			/*
			 * } else {
			 */
			// 修改旧对象
			entity.setLastModifyTime(DateTimeUtil.getChar14());
			if (loginToken != null)
			{
				entity.setLastModifyBy(loginToken.getSysLogin().getLoginName());
			}
			// }
		}

	}
}
