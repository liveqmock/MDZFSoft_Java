package com.njmd.framework.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 含审计信息的Entity基类.
 * 
 * @author Yao
 * 
 */
@MappedSuperclass
public class AuditableEntity
{

	protected String createTime;

	protected String createBy;

	protected String lastModifyTime;

	protected String lastModifyBy;

	/**
	 * 创建时间.
	 */
	// 本属性只在save时有效,update时无效.
	@Column(updatable = false)
	public String getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * 创建的操作员的登录名.
	 */
	@Column(updatable = false)
	public String getCreateBy()
	{
		return this.createBy;
	}

	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}

	/**
	 * 最后修改时间.
	 */
	// 本属性只在update时有效,save时无效.
	@Column(insertable = false)
	public String getLastModifyTime()
	{
		return this.lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime)
	{
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * 最后修改的操作员的登录名.
	 */
	@Column(insertable = false)
	public String getLastModifyBy()
	{
		return this.lastModifyBy;
	}

	public void setLastModifyBy(String lastModifyBy)
	{
		this.lastModifyBy = lastModifyBy;
	}
}
