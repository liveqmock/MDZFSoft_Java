package com.njmd.zfms.web.listener;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.njmd.framework.entity.AuditableEntity;
import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.web.commons.LoginToken;

/**
 * 在自动为entity添加审计信息的Hibernate EventListener.
 * 
 * 在hibernate执行saveOrUpdate()时,自动为AuditableEntity的子类添加审计信息.
 * 
 * @author Yao
 * 
 */
@SuppressWarnings("serial")
public class AuditListener extends EmptyInterceptor
{
	public boolean onFlushDirty(Object object, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types)
	{
		try
		{
			if (object instanceof AuditableEntity)
			{
				AuditableEntity entity = (AuditableEntity) object;
				for (int i = 0; i < propertyNames.length; i++)
				{
					if ("lastModifyTime".equals(propertyNames[i]))
					{
						currentState[i] = DateTimeUtil.getChar14();
					}
					if ("lastModifyBy".equals(propertyNames[i]))
					{
						LoginToken loginToken = WebContextHolder.getCurrLoginToken();
						if (loginToken != null)
						{
							currentState[i] = loginToken.getSysLogin().getLoginName();
						}
					}
				}
			}
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}

	public boolean onSave(Object object, Serializable id, Object[] state, String[] propertyNames, Type[] types)
	{
		// 如果对象是AuditableEntity子类,添加审计信息.
		if (object instanceof AuditableEntity)
		{
			AuditableEntity entity = (AuditableEntity) object;
			LoginToken loginToken = WebContextHolder.getCurrLoginToken();
			// 创建新对象
			entity.setCreateTime(DateTimeUtil.getChar14());
			if (loginToken != null)
			{
				entity.setCreateBy(loginToken.getSysLogin().getLoginName());
			}

		}
		return false;
	}

}
