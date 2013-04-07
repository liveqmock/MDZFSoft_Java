package com.njmd.framework.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.njmd.framework.dao.BaseHibernateDAO;
import com.njmd.framework.dao.Page;
import com.njmd.framework.dao.PropertyFilter;
import com.njmd.zfms.web.constants.ResultConstants;

@Transactional(readOnly = true)
public abstract class BaseCrudServiceImpl<T, PK extends Serializable> implements BaseCrudService<T, PK>
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected BaseHibernateDAO<T, PK> baseDao;

	public abstract void setBaseDao(BaseHibernateDAO<T, PK> baseDao);

	@Override
	public Page query(Page page, List<PropertyFilter> filters) throws Exception
	{
		return baseDao.findByPage(page, filters);
	}

	@Transactional(readOnly = false)
	@Override
	public int save(T entity) throws Exception
	{
		baseDao.save(entity);
		return ResultConstants.SAVE_SUCCEED;
	}

	@Transactional(readOnly = false)
	@Override
	public int update(T entity) throws Exception
	{
		baseDao.update(entity);
		return ResultConstants.UPDATE_SUCCEED;

	}

	@Transactional(readOnly = false)
	@Override
	public int delete(PK id) throws Exception
	{
		baseDao.deleteById(id);
		return ResultConstants.DELETE_SUCCEED;
	}

	@Transactional(readOnly = false)
	@Override
	public int delete(PK[] ids) throws Exception
	{
		for (PK id : ids)
		{
			this.delete(id);
		}
		return ResultConstants.DELETE_SUCCEED;
	}

	@Override
	public T findById(PK id) throws Exception
	{
		return baseDao.findById(id);
	}

	@Override
	public List<T> findAll() throws Exception
	{
		return baseDao.findAll();
	}

}
