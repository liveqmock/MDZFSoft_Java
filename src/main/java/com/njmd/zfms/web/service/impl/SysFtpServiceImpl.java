package com.njmd.zfms.web.service.impl;

import java.util.List;


import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.framework.service.BaseCrudServiceImpl;
import com.njmd.zfms.web.constants.ResultConstants;
import com.njmd.zfms.web.entity.sys.SysFtp;
import com.njmd.zfms.web.entity.sys.SysLog;
import com.njmd.zfms.web.service.SysFtpService;
import com.njmd.zfms.web.service.SysLogService;

/**
 * @title: FSysFtpBOImpl.java
 * @description: FTP管理业务操作类
 * 
 */
@Service
public class SysFtpServiceImpl extends BaseCrudServiceImpl<SysFtp, Long> implements SysFtpService
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 系统日志操作接口
	 */
	@Autowired
	private SysLogService sysLogBO;

	/**
	 * 保存FTP信息
	 * 
	 * @param sysFtpDTO
	 *            FTP信息数据传输对象
	 * @return 保存成功或失败
	 * 
	 * 
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int save(SysFtp sysFtpDTO) throws Exception
	{
		SysFtp sysFtp=new SysFtp();
		BeanUtils.copyProperties(sysFtp, sysFtpDTO);
		
		if(baseDao.findUnique("ftpIp", sysFtp.getFtpIp())==null){
			baseDao.save(sysFtp);
			
			sysLogBO.save(SysLog.OPERATE_TYPE_ADD, "【FTP服务器新增】部门名称：" + sysFtp.getFtpIp());
			return ResultConstants.SAVE_SUCCEED;
		}
		return ResultConstants.SAVE_FAILED_IP_IS_EXIST;
	}

	/**
	 * 根据单位ID查询FTP信息
	 * 
	 * @param ftpId
	 *            FTP ID
	 * @return FTP信息数据传输对象
	 */
	@Override
	public SysFtp findById(Long ftpId) throws Exception
	{
		SysFtp sysFtp = baseDao.findById(ftpId);
		return sysFtp;
	}

	/**
	 * 根据FTP ID删除FTP信息
	 * 
	 * @param ftpId
	 *            FTP ID
	 * @return 删除成功或失败
	 * 
	 * 
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int delete(Long ftpId) throws Exception
	{
		// 判断是否有其它部门关联此FTP。
		SysFtp sysFtp = baseDao.findById(ftpId);
		if(null!=sysFtp.getSysCorps() && sysFtp.getSysCorps().size()>0){
			return ResultConstants.DELETE_FAILED_IS_REF;
		}
		baseDao.delete(sysFtp);
		sysLogBO.save(SysLog.OPERATE_TYPE_DELETE, "【FTP服务器删除】部门名称：" + sysFtp.getFtpIp());
		return ResultConstants.DELETE_SUCCEED;
	}

	/**
	 * 按照条件分页查询FTP信息
	 * 
	 * @param page
	 *            分页信息
	 * @param filters
	 *            过滤条件列表
	 * @return 单位信息集合
	 * @throws Exception
	 */
	@Override
	public Page query(Page page, List<PropertyFilter> filters) throws Exception
	{
		Page pageTemp = baseDao.findByPage(page, filters);
		List resultList = pageTemp.getResult();
		pageTemp.setResult(resultList);
		return pageTemp;
	}

	/**
	 * 修改FTP信息
	 * 
	 * @param sysFtp
	 *            FTP信息数据传输对象
	 * @return 修改成功或失败
	 * 
	 * 
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public int update(SysFtp sysFtp) throws Exception
	{

		SysFtp tmp=baseDao.findUnique("ftpIp", sysFtp.getFtpIp());
		if ((tmp != null) && (tmp.getFtpId().longValue() != sysFtp.getFtpId().longValue()))
			return ResultConstants.UPDATE_FAILED_IP_IS_EXIST;

		
		SysFtp sysFtpTemp=baseDao.findById(sysFtp.getFtpId());
		
		if (sysFtpTemp == null){
			sysFtpTemp = new SysFtp();
		}
		
		BeanUtils.copyProperties(sysFtpTemp, sysFtp);
		
		baseDao.update(sysFtpTemp);
		
		sysLogBO.save(SysLog.OPERATE_TYPE_UPDATE, "【FTP服务器更新】部门名称：" + sysFtp.getFtpIp());
		return ResultConstants.UPDATE_SUCCEED;
	}


	@Autowired
	@Override
	@Qualifier(value = "sysFtpDAO")
	public void setBaseDao(BaseHibernateDAO<SysFtp, Long> baseDao)
	{
		this.baseDao = baseDao;
	}

}
