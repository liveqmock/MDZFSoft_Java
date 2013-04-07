package com.njmd.framework.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

/**
 * Hibernate针对Web应用的Utils函数集合.
 * 
 * @author calvin
 */
public final class HibernateWebUtils
{

	private HibernateWebUtils()
	{
	}

	/**
	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
	 * 默认Filter属性名前缀为filter_.
	 * 
	 * @see #buildPropertyFilters(HttpServletRequest, String)
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request)
	{
		return buildPropertyFilters(request, "filter_");
	}

	/**
	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
	 * PropertyFilter命名规则为Filter属性前缀_比较类型_属性名.
	 * 
	 * eg. filter_EQUAL_name filter_LIKE_name_OR_email
	 */
	@SuppressWarnings("unchecked")
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request, final String filterPrefix)
	{
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix);

		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet())
		{
			String filterName = entry.getKey();
			Object value = entry.getValue();
			// 如果value值为空,则忽略此filter.
			// boolean omit = StringUtils.isBlank(value);
			// if (!omit) {

			if (value instanceof String)
			{
				value = ((String) value).trim();
			}

			if ((value != null) && (!"".equals(value)))
			{
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}
		return filterList;
	}
}
