package com.njmd.zfms.web.service;

import java.util.Map;

import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.entity.file.FileUploadInfo;

/**
 * 系统公告业务处理对象接口
 * 
 * @author Yao
 * 
 */
public interface FileUploadInfoService extends BaseCrudService<FileUploadInfo, Long>
{
	/**
	 * 根据文件类型获得文件数量
	 * 
	 * @param typeId
	 * @return
	 */
	public long getCountByTypeId(Long typeId);

	/**
	 * 获取机构图标数据
	 * 
	 * @param queryType
	 * @param corpId
	 * @param year
	 * @param month
	 * @param startDate
	 * @param endDate
	 * @return key=corpId_时间,value=上传文件数量
	 */
	public Map<String, Integer> getCorpChartData(Integer queryType, Long corpId, String year, String month, String startDate, String endDate);

	/**
	 * 获取机构详细数据
	 * 
	 * @param queryType
	 * @param corpId
	 * @param year
	 * @param month
	 * @param startDate
	 * @param endDate
	 * @return key=corpId_typeid_时间,value=上传文件数量
	 */
	public Map<String, Integer> getCorpDetialData(Integer queryType, Long corpId, String year, String month, String startDate, String endDate);

	/**
	 * 获取用户图标数据
	 * 
	 * @param queryType
	 * @param userID
	 * @param year
	 * @param month
	 * @param startDate
	 * @param endDate
	 * @return key=corpId_时间,value=上传文件数量
	 */
	public Map<String, Integer> getUserChartData(Integer queryType, Long userId, String year, String month, String startDate, String endDate);

}
