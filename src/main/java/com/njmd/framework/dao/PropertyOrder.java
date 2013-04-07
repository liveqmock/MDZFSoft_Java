package com.njmd.framework.dao;

/**
 * 属性排序对象
 * 
 * @author Yao
 * 
 */
public class PropertyOrder
{
	// 公共变量 //
	public static final String ASC = "asc";

	public static final String DESC = "desc";

	// 排序字段
	protected String orderBy = null;

	// 排序规则
	protected String order = null;

	public String getOrderBy()
	{
		return orderBy;
	}

	public void setOrderBy(String orderBy)
	{
		this.orderBy = orderBy;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

}
