package com.njmd.framework.dao;

import java.util.Collection;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

/**
 * @title: 属性条件过滤类
 * @description:与具体ORM实现无关的属性过滤条件封装类. 
 *                                    PropertyFilter主要记录页面中简单的搜索过滤条件,比Hibernate的Criterion要简单
 *                                    . 扩展其他对比方式如大于、小于及其他数据类型如数字和日期.
 * 
 * @author chenzhijun
 */
public class PropertyFilter
{

	/**
	 * 多个属性间OR关系的分隔符.
	 */
	private static final String OR_SEPARATOR = "_OR_";

	/**
	 * 属性比较类型.
	 */
	public enum MatchType
	{
		/** 等于 */
		EQ,
		/** LIKE */
		LIKE,
		/** 小于 */
		LT,
		/** 大于 */
		GT,
		/** 小于等于 */
		LE,
		/** 大于等于 */
		GE,
		/** IN */
		IN,
		/** 以XXX开始 */
		START,
		/** 以XXX结束 */
		END,
		/** 不等于 */
		NE;
	}

	private String[] propertyNames = null;

	private Object value;

	private MatchType matchType = MatchType.EQ;

	public PropertyFilter()
	{
	}

	public PropertyFilter(final String propertyName, MatchType matchType, final Object value)
	{
		propertyNames = StringUtils.split(propertyName, PropertyFilter.OR_SEPARATOR);
		this.matchType = matchType;
		this.value = value;
	}

	/**
	 * 
	 * @param filterName
	 *            EQ_S_NAME
	 * @param value
	 */
	public PropertyFilter(final String filterName, final Object value)
	{

		String matchTypeCode = StringUtils.substringBefore(filterName, "_");
		try
		{
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		}
		catch (RuntimeException e)
		{
			throw new IllegalArgumentException("filter名称没有按规则编写,无法得到属性比较类型.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		// propertyNames = StringUtils.split(propertyNameStr,
		// PropertyFilter.OR_SEPARATOR);
		propertyNames = propertyNameStr.split(PropertyFilter.OR_SEPARATOR);
		this.value = value;
	}

	/**
	 * 是否有多个属性
	 */
	public boolean isMultiProperty()
	{
		return (propertyNames.length > 1);
	}

	/**
	 * 获取唯一的属性名称.
	 */
	public String getPropertyName()
	{
		// if (propertyNames.length > 1)
		// throw new
		// IllegalArgumentException("There are not only one property");
		return propertyNames[0];
	}

	/**
	 * 获取属性名称列表.
	 */
	public String[] getPropertyNames()
	{
		return propertyNames;
	}

	@SuppressWarnings("unchecked")
	public Object getValue()
	{

		if (value instanceof String[])
		{
			StringBuffer sb = new StringBuffer();
			Collection collection = new TreeSet();
			for (String str : (String[]) value)
			{
				sb.append(str).append(",");
			}
			String temp = sb.toString();
			String[] result = temp.substring(0, temp.length() - 1).split(",");
			for (String string : result)
			{
				collection.add(string);
			}
			return collection;

		}

		return value;
	}

	public MatchType getMatchType()
	{
		return matchType;
	}
}
