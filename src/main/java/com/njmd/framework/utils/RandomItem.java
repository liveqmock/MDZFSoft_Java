package com.njmd.framework.utils;

/**
 * 随机项
 * 
 * @author yao
 * 
 */
public class RandomItem
{
	// 随机项ID
	private Integer id;
	// 随机项产生概率
	private Integer probabity;

	public RandomItem(Integer id, Integer probabity)
	{
		this.id = id;
		this.probabity = probabity;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getProbabity()
	{
		return probabity;
	}

	public void setProbabity(Integer probabity)
	{
		this.probabity = probabity;
	}

}
