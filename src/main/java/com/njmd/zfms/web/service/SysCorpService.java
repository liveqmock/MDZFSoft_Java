package com.njmd.zfms.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.njmd.framework.commons.Tree;
import com.njmd.framework.service.BaseCrudService;
import com.njmd.zfms.web.entity.sys.SysCorp;

public interface SysCorpService extends BaseCrudService<SysCorp, Long>
{

	/**
	 * 按照单位类型获取单位信息
	 * 
	 * @param corpType
	 *            　属性值
	 * @return　单位信息集合
	 * @throws Exception
	 */
	List<SysCorp> findByCorpType(final Long corpType) throws Exception;

	/**
	 * 根据父单位ID查询下级单位
	 * 
	 * @param parentId
	 *            　父单位ID
	 * @return　返回下级单位
	 * @throws Exception
	 */
	List<SysCorp> findByParentId(Long parentId) throws Exception;

	/**
	 * 根据父单位ID查询下一级单位
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<SysCorp> findChildsByParentId(Long parentId) throws Exception;
	
	/**
	 * 获得单位树
	 * 
	 * @param request
	 * @param enableCheckBox
	 *            是否需要显示复选框
	 * @return
	 * @throws Exception
	 */
	Tree getCorpTree(HttpServletRequest request, boolean enableCheckBox) throws Exception;

}
