package com.njmd.zfms.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.zfms.web.entity.sys.SysServerInfo;
import com.njmd.zfms.web.service.SysServerInfoService;

/**
 * 服务器信息处理实现类
 * 
 * @author CFG
 * 
 */
@Service
public class SysServerInfoServiceImpl extends BaseCrudServiceImpl<SysServerInfo, Long> implements SysServerInfoService
{

	@Autowired
	@Override
	@Qualifier(value = "sysServerInfoDAO")
	public void setBaseDao(BaseHibernateDAO<SysServerInfo, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

	@Override
	public List<SysServerInfo> serverInfoListByDay(String statTime,String ip) {
		String hql="from SysServerInfo as model where  model.createTime like '"+statTime.replaceAll("-","")+"%' and model.saveIp ='"+ip+"' order by model.createTime asc";
		Object[] param=new Object[]{};
		return (List<SysServerInfo>)baseDao.findByHql(hql,param);
	}

	@Override
	public List<Object[]> serverInfoListByMonth(String year, String month,
			String ip) { 
		String hql="select substr(model.createTime,0,8)   ,avg(ratioCpu)  ,avg(ratioMemory)  ,avg(ratioHarddisk)   from SysServerInfo as model " +
				"where   model.createTime like '"+year+month+"%'" + 
						"and model.saveIp ='"+ip+"' group by  substr(model.createTime,0,8)  order by substr(model.createTime,0,8) asc";
		Object[] param=new Object[]{};
		return (List<Object[]>)baseDao.findByHql(hql,param);
	}

	@Override
	public List<Object[]> serverInfoListByWeek(String endDate, String ip) {
		String startDate=DateTimeUtil.formatChar8(DateTimeUtil.rollDate(endDate.replaceAll("-",""),-6));
		String hql="select substr(model.createTime,0,8)   ,avg(ratioCpu)  ,avg(ratioMemory)  ,avg(ratioHarddisk)   from SysServerInfo as model " +
				"where to_number(substr(model.createTime,0,8)) >="+startDate.replaceAll("-","")+" " +
				"and to_number(substr(model.createTime,0,8))<="+endDate.replaceAll("-","")+" " +
						"and model.saveIp ='"+ip+"' group by  substr(model.createTime,0,8)  order by substr(model.createTime,0,8) asc";
		Object[] param=new Object[]{};
		return (List<Object[]>)baseDao.findByHql(hql,param);
	}

	@Override
	public List<Object[]> serverInfoListByYear(String year, String ip) {
		String hql="select substr(model.createTime,0,6)   ,avg(ratioCpu)  ,avg(ratioMemory)  ,avg(ratioHarddisk)   from SysServerInfo as model " +
		"where   model.createTime like '"+year+"%'" + 
				"and model.saveIp ='"+ip+"' group by  substr(model.createTime,0,6)  order by substr(model.createTime,0,6) asc";
	Object[] param=new Object[]{};
	return (List<Object[]>)baseDao.findByHql(hql,param);
	}

	 

}
