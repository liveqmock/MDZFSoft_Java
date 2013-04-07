package com.njmd.framework.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.njmd.framework.dao.PropertyFilter.MatchType;
import com.njmd.framework.utils.ReflectionUtil;

/**
 * 
 * @title:DAO基础类,所有的DAO都通过该类继承
 * @description:
 * 
 * @param <T>
 *            DAO操作类
 * 
 * 
 * 
 * @param <PK>
 *            操作对象主键类型
 * @author: Yao
 * 
 */
@Repository
public class BaseHibernateDAO<T, PK extends Serializable>
{
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;
	// DAO操作类

	protected Class<T> entityClass;

	public BaseHibernateDAO()
	{
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
	}

	public BaseHibernateDAO(final SessionFactory sessionFactory, final Class<T> entityClass)
	{
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;

	}

	/**
	 * 取得sessionFactory.
	 */
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	@Autowired
	protected void initSessionFactory(final SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 取得当前Session.
	 */
	public Session getSession()
	{
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存新增的对象.
	 * 
	 * @param entity
	 *            要保存修改的对象
	 * 
	 */
	public void save(final T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		getSession().save(entity);
		logger.debug("save entity: {}", entity);
	}

	/**
	 * 批量保存新增的对象
	 * 
	 * 
	 * 
	 * 
	 * @param entities
	 *            要保存修改的对象集合
	 * 
	 */
	public void saveAll(final Collection<T> entities)
	{
		Assert.notEmpty(entities, "entities不能为空");
		for (T entity : entities)
		{
			save(entity);
		}
	}

	/**
	 * 保存修改的对象.
	 * 
	 * @param entity
	 *            要保存修改的对象
	 * 
	 */
	public void update(final T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		getSession().update(entity);
		logger.debug("update entity: {}", entity);
	}

	/**
	 * 批量保存修改的对象
	 * 
	 * 
	 * @param entities
	 *            要保存修改的对象集合
	 * 
	 */
	public void update(final Collection<T> entities)
	{
		Assert.notEmpty(entities, "entities不能为空");
		for (T entity : entities)
		{
			update(entity);
		}
	}

	/**
	 * 保存新增或修改的对象.
	 * 
	 * @param entity
	 *            要保存修改的对象
	 * 
	 */
	public void saveOrUpdate(final T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		getSession().saveOrUpdate(entity);
		logger.debug("save or update entity: {}", entity);
	}

	/**
	 * 批量保存新增或修改的对象
	 * 
	 * @param entities
	 *            要保存修改的对象集合
	 * 
	 */
	public void saveOrUpdateAll(final Collection<T> entities)
	{
		Assert.notEmpty(entities, "entities不能为空");
		for (T entity : entities)
		{
			saveOrUpdate(entity);
		}
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity
	 *            要删除的对象，对象必须是session中的对象或含id属性的transient对象.
	 * 
	 */
	public void delete(final T entity)
	{
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/**
	 * 根据主键删除对象.
	 * 
	 * @param id
	 *            对象主键
	 */
	public void deleteById(final PK id)
	{
		Assert.notNull(id, "id不能为空");
		delete(findById(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/**
	 * 根据主键集合删除对象
	 * 
	 * @param ids
	 *            对象主键集合
	 */
	public void deleteById(final Collection<PK> ids)
	{
		Assert.notEmpty(ids, "ids不能为空");
		for (PK id : ids)
			delete(findById(id));
	}

	/**
	 * 批量删除
	 * 
	 * @param entities
	 */
	public void deleteAll(final Collection<T> entities)
	{
		Assert.notEmpty(entities, "entities不能为空");
		for (T entity : entities)
		{
			delete(entity);
		}
	}

	/**
	 * 按id获取对象.
	 * 
	 * @param id
	 *            对象主键
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findById(final PK id)
	{
		Assert.notNull(id, "id不能为空");
		return (T) getSession().get(entityClass, id);
	}

	/**
	 * 查找全部对象
	 * 
	 * @return
	 */
	public List<T> findAll()
	{
		Order order = null;
		return findAll(order);
	}

	/**
	 * 查找全部对象,并按指定规则排序
	 * 
	 * @param order
	 *            Hibernate排序对象 Order.asc("字段名") 或者 Order.desc("字段名")
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(final Order... orders)
	{
		return createCriteria(orders).list();
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等
	 * 
	 * 
	 * 
	 * 
	 * @param propertyName
	 *            属性名称
	 * 
	 * 
	 * 
	 * @param value
	 *            属性值
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public List<T> findByProperty(final String propertyName, final Object value)
	{
		Order order = null;
		return findByProperty(propertyName, value, MatchType.EQ, order);
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等
	 * 
	 * 
	 * 
	 * 
	 * @param propertyName
	 *            属性名称
	 * 
	 * 
	 * 
	 * @param value
	 *            属性值
	 * 
	 * 
	 * 
	 * @param order
	 *            排序对象
	 * 
	 * @return
	 */
	public List<T> findByProperty(final String propertyName, final Object value, final Order... orders)
	{
		return findByProperty(propertyName, value, MatchType.EQ, orders);
	}

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param propertyName
	 *            属性名称
	 * 
	 * 
	 * 
	 * @param value
	 *            属性值
	 * 
	 * 
	 * 
	 * @param matchType
	 *            匹配方式
	 * 
	 */
	public List<T> findByProperty(final String propertyName, final Object value, final MatchType matchType)
	{
		Order order = null;
		return findByProperty(propertyName, value, matchType, order);
	}

	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param propertyName
	 *            属性名称
	 * 
	 * 
	 * 
	 * @param value
	 *            属性值
	 * 
	 * 
	 * 
	 * @param matchType
	 *            匹配方式
	 * @param orders
	 *            排序对象集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(final String propertyName, final Object value, final MatchType matchType, Order... orders)
	{
		Assert.hasText(propertyName, "propertyName不能为空");
		Assert.notNull(value, "value不能为空");
		Assert.notNull(matchType, "matchType不能为空");
		Criterion criterion = buildPropertyFilterCriterion(propertyName, value, matchType);

		Collection<Criterion> criterionList = new ArrayList<Criterion>();
		Collection<Order> orderList = new ArrayList<Order>();

		CollectionUtils.addAll(orderList, orders);
		criterionList.add(criterion);
		Criteria criteria = createCriteria(criterionList, orderList);
		return criteria.list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 *            查询语句
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List findByHql(final String hql, final Object... values)
	{
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List findByHql(final String hql, final Map<String, Object> values)
	{
		return createQuery(hql, values).list();
	}

	/**
	 * 
	 * 按属性过滤条件列表查找对象列表.
	 * 
	 * @param filters
	 * @return
	 */
	public List<T> findByFilters(final List<PropertyFilter> filters)
	{
		Criterion[] criterions = buildPropertyFilterCriterions(filters);
		return findByCriterion(criterions);
	}

	/**
	 * 
	 * 按属性过滤条件列表查找对象列表.
	 * 
	 * @param filters
	 * @return
	 */
	public List<T> findByFilters(final List<PropertyFilter> filters, Order... orders)
	{
		Criterion[] criterions = buildPropertyFilterCriterions(filters);

		Collection<Criterion> criterionList = new ArrayList<Criterion>();
		Collection<Order> orderList = new ArrayList<Order>();

		CollectionUtils.addAll(orderList, orders);
		CollectionUtils.addAll(criterionList, criterions);
		Criteria criteria = createCriteria(criterionList, orderList);
		return criteria.list();
		// return findByCriterion(criterions);
	}

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page
	 *            分页参数.
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page findByPage(final Page page, final Criterion... criterions)
	{
		Assert.notNull(page, "page不能为空");
		Criteria criteria = createCriteria(criterions);
		if (page.isAutoCount())
		{
			long totalCount = getCountByCriteria(criteria);
			page.setTotalCount(totalCount);
		}

		setPageParameter(criteria, page);
		List result = criteria.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.不支持其中的orderBy参数.
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page findByPage(final Page page, final String hql, final Object... values)
	{
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount())
		{
			long totalCount = getCountByHql(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 *            分页参数.(不支持orderBy参数)
	 * @param hql
	 *            hql语句.
	 * @param values
	 *            命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@SuppressWarnings("unchecked")
	public Page findByPage(final Page page, final String hql, final Map<String, Object> values)
	{
		Assert.notNull(page, "page不能为空");

		Query q = createQuery(hql, values);

		if (page.isAutoCount())
		{
			long totalCount = getCountByHql(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 * 
	 * @param page
	 * @param filters
	 * @return
	 */
	public Page findByPage(final Page page, final List<PropertyFilter> filters)
	{
		Criterion[] criterions = buildPropertyFilterCriterions(filters);

		Criteria criteria = createCriteria(criterions);

		if (filters != null && filters.size() > 0)
		{
			for (PropertyFilter filter : filters)
			{
				if (filter.getPropertyName().indexOf(".") > 0)
				{
					String refBeanName = filter.getPropertyName().split("\\.")[0];
					criteria.createAlias(refBeanName, refBeanName);
				}
			}
		}
		if (page.isAutoCount())
		{
			long totalCount = getCountByCriteria(criteria);
			page.setTotalCount(totalCount);
		}

		setPageParameter(criteria, page);
		List result = criteria.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriterion(final Criterion... criterions)
	{
		return createCriteria(criterions).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final String hql, final Object... values)
	{
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final String propertyName, final Object value)
	{
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final String hql, final Map<String, Object> values)
	{
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按Criteria查询唯一对象.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions)
	{
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 按HQL查询Integer类型结果.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Integer findInt(final String hql, final Object... values)
	{
		return (Integer) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Integer类型结果.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Integer findInt(final String hql, final Map<String, Object> values)
	{
		return (Integer) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Long类型结果.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Long findLong(final String hql, final Object... values)
	{
		return (Long) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Long类型结果.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public Long findLong(final String hql, final Map<String, Object> values)
	{
		return (Long) findUnique(hql, values);
	}

	/**
	 * 执行HQL进行批量修改/删除操作
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public int executeHQL(final String hql, final Object... values)
	{
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public int executeHQL(final String hql, final Map<String, Object> values)
	{
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values)
	{
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null)
		{
			for (int i = 0; i < values.length; i++)
			{
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, Object> values)
	{
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null)
		{
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 初始化对象. 使用load()方法得到的仅是对象Proxy后, 在传到View层前需要进行初始化. initObject(user)
	 * ,初始化User的直接属性，但不会初始化延迟加载的关联集合和属性.
	 * initObject(user.getRoles())，初始化User的直接属性和关联集合.
	 * initObject(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initObject(Object object)
	{
		Hibernate.initialize(object);
	}

	/**
	 * 批量初始化对象.
	 * 
	 * @see #initObject(Object)
	 */
	@SuppressWarnings("unchecked")
	public void initObjects(List list)
	{
		for (Object object : list)
		{
			Hibernate.initialize(object);
		}
	}

	/**
	 * 通过Set将不唯一的对象列表唯一化. 主要用于HQL/Criteria预加载关联集合形成重复记录,又不方便使用distinct查询语句时.
	 * 
	 * @param <X>
	 * @param list
	 * @return
	 */
	public <X> List<X> distinct(List<X> list)
	{
		Set<X> set = new LinkedHashSet<X>(list);
		return new ArrayList<X>(set);
	}

	/**
	 * 取得对象的主键名.
	 * 
	 * @return
	 */
	public String getIdName()
	{
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		Assert.notNull(meta, "Class " + entityClass.getSimpleName() + " not define in HibernateSessionFactory.");
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 * 
	 * @param propertyName
	 *            属性名称
	 * 
	 * 
	 * 
	 * @param newValue
	 *            新值
	 * 
	 * 
	 * 
	 * @param oldValue
	 *            旧值
	 * 
	 * 
	 * 
	 * @return
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue)
	{
		if (newValue == null || newValue.equals(oldValue))
			return true;
		Object object = findUnique(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 根据Criterion条件创建DetachedCriteria.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 * 
	 */
	protected Criteria createCriteria(Criterion... criterions)
	{
		Collection<Order> orderList = new ArrayList<Order>();
		Collection<Criterion> criterionList = new ArrayList<Criterion>();
		CollectionUtils.addAll(criterionList, criterions);
		return createCriteria(criterionList, orderList);
	}

	/**
	 * 根据Criterion条件创建DetachedCriteria.
	 * 
	 * @param orders
	 *            数量可变的Order.
	 * 
	 */
	protected Criteria createCriteria(final Order... orders)
	{
		Collection<Order> orderList = new ArrayList<Order>();
		Collection<Criterion> criterionList = new ArrayList<Criterion>();
		CollectionUtils.addAll(orderList, orders);
		return createCriteria(criterionList, orderList);
	}

	/**
	 * 根据Criterion条件创建DetachedCriteria.
	 * 
	 * @param criterions
	 *            Criterion集合.
	 * @param orders
	 *            Order集合.
	 * 
	 */
	protected Criteria createCriteria(final Collection<Criterion> criterions, final Collection<Order> orders)
	{
		Criteria criteria = this.getSession().createCriteria(entityClass);
		if (criterions != null && criterions.size() > 0)
		{
			for (Criterion criterion : criterions)
			{
				if (criterion != null)
					criteria.add(criterion);
			}
		}
		if (orders != null && orders.size() > 0)
		{
			for (Order order : orders)
			{
				if (order != null)
					criteria.addOrder(order);
			}
		}
		return criteria;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 * 
	 * @param q
	 * @param page
	 * @return
	 */
	protected Query setPageParameter(final Query q, final Page page)
	{
		// hibernate的firstResult的序号从0开始

		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 * 
	 * @param c
	 * @param page
	 * @return
	 */
	protected Criteria setPageParameter(final Criteria c, final Page page)
	{
		// hibernate的firstResult的序号从0开始

		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted())
		{
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++)
			{
				if (Page.ASC.equals(orderArray[i]))
				{
					c.addOrder(Order.asc(orderByArray[i]));
				}
				else
				{
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		/*
		 * List<Order> orderList = page.getOrder(); if (orderList.size() > 0) {
		 * for (Order order : orderList) { c.addOrder(order);
		 * 
		 * } }
		 */
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	protected long getCountByHql(final String hql, final Object... values)
	{
		long count = 0;
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try
		{
			count = findLong(countHql, values);
		}
		catch (Exception e)
		{
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
		return count;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	protected long getCountByHql(final String hql, final Map<String, Object> values)
	{
		long count = 0;
		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try
		{
			count = findLong(countHql, values);
		}
		catch (Exception e)
		{
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}

		return count;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	@SuppressWarnings("unchecked")
	protected long getCountByCriteria(final Criteria c)
	{
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try
		{
			orderEntries = (List) ReflectionUtil.getFieldValue(impl, "orderEntries");
			ReflectionUtil.setFieldValue(impl, "orderEntries", new ArrayList());
		}
		catch (Exception e)
		{
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		// 执行Count查询
		// System.out.println("********" +
		// c.setProjection(Projections.rowCount()).uniqueResult());
		long totalCount = (Long) c.setProjection(Projections.rowCount()).uniqueResult();

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去

		c.setProjection(projection);

		if (projection == null)
		{
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null)
		{
			c.setResultTransformer(transformer);
		}
		try
		{
			ReflectionUtil.setFieldValue(impl, "orderEntries", orderEntries);
		}
		catch (Exception e)
		{
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}

	/**
	 * 按属性条件列表创建Criterion数组,辅助函数.
	 */
	protected Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters)
	{
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for (PropertyFilter filter : filters)
		{
			if (!filter.isMultiProperty())
			{ // 只有一个属性需要比较的情况.
				Criterion criterion = buildPropertyFilterCriterion(filter.getPropertyName(), filter.getValue(), filter.getMatchType());
				criterionList.add(criterion);
			}
			else
			{// 包含多个属性需要比较的情况,进行or处理.
				Disjunction disjunction = Restrictions.disjunction();
				for (String param : filter.getPropertyNames())
				{
					Criterion criterion = buildPropertyFilterCriterion(param, filter.getValue(), filter.getMatchType());
					disjunction.add(criterion);
				}
				criterionList.add(disjunction);
			}
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	/**
	 * 按属性条件参数创建Criterion,辅助函数.
	 */
	@SuppressWarnings("unchecked")
	protected Criterion buildPropertyFilterCriterion(final String propertyName, final Object value, final MatchType matchType)
	{
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = null;
		try
		{
			if (MatchType.IN.equals(matchType))
			{
				Object realValue = ReflectionUtil.convertHibernateValue(value, entityClass, propertyName);
				criterion = Restrictions.in(propertyName, (Collection) realValue);
			}
			else
			{
				// 按entity property中的类型将字符串转化为实际类型.
				// Object realValue = ReflectionUtil.convertValue(value,
				// entityClass, propertyName);
				Object realValue = ReflectionUtil.convertHibernateValue(value, entityClass, propertyName);
				// 根据MatchType构造criterion
				// 等于
				if (MatchType.EQ.equals(matchType))
				{
					criterion = Restrictions.eq(propertyName, realValue);
				}
				// Like
				if (MatchType.LIKE.equals(matchType))
				{
					criterion = Restrictions.like(propertyName, (String) realValue, MatchMode.ANYWHERE);
				}
				// 小于等于
				if (MatchType.LE.equals(matchType))
				{
					criterion = Restrictions.le(propertyName, realValue);
				}
				// 小于
				if (MatchType.LT.equals(matchType))
				{
					criterion = Restrictions.lt(propertyName, realValue);
				}
				// 大于等于
				if (MatchType.GE.equals(matchType))
				{
					criterion = Restrictions.ge(propertyName, realValue);
				}
				// 大于
				if (MatchType.GT.equals(matchType))
				{
					criterion = Restrictions.gt(propertyName, realValue);
				}
				// 开始于
				if (MatchType.START.equals(matchType))
				{
					criterion = Restrictions.like(propertyName, (String) realValue, MatchMode.START);
				}
				// 结束于
				if (MatchType.END.equals(matchType))
				{
					criterion = Restrictions.like(propertyName, (String) realValue, MatchMode.END);
				}
				// 不等于
				if (MatchType.NE.equals(matchType))
				{
					criterion = Restrictions.ne(propertyName, realValue);
				}
			}

		}
		catch (Exception e)
		{
			throw ReflectionUtil.convertToUncheckedException(e);
		}
		return criterion;
	}
}
