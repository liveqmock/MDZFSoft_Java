package com.njmd.zfms.web.service;

import java.util.List;

import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.entity.sys.SysServerInfo;

/**
 * 服务器信息处理对象接口
 * 
 * @author CFG
 * 
 */
public interface SysServerInfoService extends BaseCrudService<SysServerInfo, Long>
{
	/**
	 * 系统状态列表查询 —— 日查询
	 * 
	 * @param statTime	统计日期yyyy-MM-dd
	 * @return List<ServerInfo>
	 */
	public List<SysServerInfo> serverInfoListByDay(String statTime,String ip);

	/**
	 * 系统状态列表查询 —— 周查询
	 *  
	 * @param endTime 查询截止时间yyyy-MM-dd
	 * @param ip 
	 * @return List<ServerInfo>
	 */
	public List<Object[]> serverInfoListByWeek(String endDate,String ip);

	/**
	 * 系统状态列表查询 —— 月查询
	 * 
	 * @param month	查询月yyyyMM
	 * @return List<ServerInfo>
	 */
	public List<Object[]> serverInfoListByMonth(String year,String month,String ip);

	/**
	 * 系统状态列表查询 —— 年查询
	 * 
	 * @param month	查询年yyyy
	 * @return List<ServerInfo>
	 */
	public List<Object[]> serverInfoListByYear(String year,String ip);
}
