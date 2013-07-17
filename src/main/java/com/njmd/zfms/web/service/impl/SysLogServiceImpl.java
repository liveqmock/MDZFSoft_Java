/**
 * 
 */
package com.njmd.zfms.web.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.framework.utils.DateTimeUtil;
import com.njmd.framework.utils.web.RequestUtils;
import com.njmd.framework.utils.web.WebContextHolder;
import com.njmd.zfms.annotation.Permission;
import com.njmd.zfms.web.commons.LoginToken;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.service.SysLogService;

/**
 * @title: FSysLogBOImpl
 * @description: 系统日志管理接口的实现
 * 
 * 
 * @author: zhujie
 * 
 */
@Service
public class SysLogServiceImpl extends BaseCrudServiceImpl<SysLog, Long> implements SysLogService
{
	/**
	 * 保存日志信息
	 * 
	 * @param request
	 * @param operType
	 *            操作类型
	 * @param operDesc
	 *            操作说明
	 * @throws Exception
	 */
	@Override
	public void save(Integer operType, String operDesc)
	{
		LoginToken loginToken = WebContextHolder.getCurrLoginToken();
		HttpServletRequest request = WebContextHolder.getRequest();
		SysLog sysLog = new SysLog();
		sysLog.setOperDesc(operDesc);
		sysLog.setOperIp(RequestUtils.getIpAddr(request));
		sysLog.setOperTime(DateTimeUtil.getChar14());
		sysLog.setOperType(operType);
		sysLog.setOperUserId(loginToken.getSysLogin().getLoginId());
		sysLog.setOperUserName(loginToken.getSysLogin().getUserName());
		sysLog.setOperUserCode(loginToken.getSysLogin().getUserCode());
		if (loginToken.getSysLogin().getSysCorp() != null)
		{
			sysLog.setOperCorpId(loginToken.getSysLogin().getSysCorp().getCorpId());
			sysLog.setOperCorpName(loginToken.getSysLogin().getSysCorp().getCorpName());
		}
		sysLog.setSystemId(loginToken.getSysLogin().getSystemId());
//		baseDao.save(sysLog);
	}

	@Override
	public void save(String operAction, String operResource, String operDesc,
			String operRecordId) {
		LoginToken loginToken = WebContextHolder.getCurrLoginToken();
		HttpServletRequest request = WebContextHolder.getRequest();
		SysLog sysLog = new SysLog();
		sysLog.setOperDesc(operDesc);
		sysLog.setOperIp(RequestUtils.getIpAddr(request));
		sysLog.setOperTime(DateTimeUtil.getChar14());
		sysLog.setOperType(Permission.Actions.valueOf(operAction).ordinal());
		sysLog.setOperUserId(loginToken.getSysLogin().getLoginId());
		sysLog.setOperUserName(loginToken.getSysLogin().getUserName());
		sysLog.setOperUserCode(loginToken.getSysLogin().getUserCode());
		if (loginToken.getSysLogin().getSysCorp() != null)
		{
			sysLog.setOperCorpId(loginToken.getSysLogin().getSysCorp().getCorpId());
			sysLog.setOperCorpName(loginToken.getSysLogin().getSysCorp().getCorpName());
		}
		sysLog.setSystemId(loginToken.getSysLogin().getSystemId());
		sysLog.setOperResource(operResource);
		sysLog.setOperAction(operAction);
		sysLog.setOperRecordId(operRecordId);
		baseDao.save(sysLog);
	}
	
	@Autowired
	@Qualifier("sysLogDAO")
	@Override
	public void setBaseDao(BaseHibernateDAO<SysLog, Long> baseDao)
	{
		this.baseDao = baseDao;

	}


}
