package com.njmd.zfms.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.zfms.web.entity.sys.SysLogin;

/**
 * @title: SysLoginDAO.java
 * @description: 用户登录信息数据访问类
 * 
 * @author: dongyuese
 * 
 */
@Repository
public class SysLoginDAO extends BaseHibernateDAO<SysLogin, Long>
{

	/**
	 * 用户登录验证
	 * 
	 * @param loginName
	 * @param loginPwd
	 * @param systemId
	 * @return
	 */
	public SysLogin login(String loginName, String loginPwd, Long systemId)
	{
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("loginName", PropertyFilter.MatchType.EQ, loginName));
		filters.add(new PropertyFilter("loginPwd", PropertyFilter.MatchType.EQ, loginPwd));
		filters.add(new PropertyFilter("systemId", PropertyFilter.MatchType.EQ, systemId));
		filters.add(new PropertyFilter("status", PropertyFilter.MatchType.EQ, Long.valueOf("1")));
		List<SysLogin> list = this.findByFilters(filters);
		if (list != null && list.size() == 1)
			return list.get(0);
		else
			return null;
	}
}
