package com.njmd.zfms.web.service;

import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.entity.dev.DevInfo;

/**
 * 设备管理
 * 
 * @author Yao
 * 
 */
public interface DevInfoService extends BaseCrudService<DevInfo, Long>
{
	/**
	 * 根据设备类型获得设备数量
	 * 
	 * @param typeId
	 * @return
	 */
	public long getCountByTypeId(Long typeId);

	/**
	 * 根据厂商获得设备数量
	 * 
	 * @param typeId
	 * @return
	 */
	public long getCountByFacturerId(Long FacturerId);
}
