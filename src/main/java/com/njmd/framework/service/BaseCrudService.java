package com.njmd.framework.service;

import java.io.Serializable;
import java.util.List;

import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;

/**
 * 基础的增删改查业务接口
 * 
 * @author yao
 * 
 * @param <Entity>
 * @param <PK>
 */
public interface BaseCrudService<Entity, PK extends Serializable>
{
	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	Page query(final Page page, final List<PropertyFilter> filters) throws Exception;

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	int save(Entity entity) throws Exception;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	int update(Entity entity) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(PK id) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	int delete(PK[] ids) throws Exception;

	/**
	 * 根据主键获得对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Entity findById(PK id) throws Exception;

	/**
	 * 查询所有信息
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Entity> findAll() throws Exception;
}
