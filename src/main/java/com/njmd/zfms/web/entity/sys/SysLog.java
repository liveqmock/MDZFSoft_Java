package com.njmd.zfms.web.entity.sys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the SYS_LOG database table.
 * 
 */
@Entity
@Table(name = "SYS_LOG")
public class SysLog implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * 操作类型-登陆
	 */
	public static final Integer OPERATE_TYPE_LOGIN = 0;

	/**
	 * 操作类型-增加
	 */
	public static final Integer OPERATE_TYPE_ADD = 1;

	/**
	 * 操作类型-删除
	 */
	public static final Integer OPERATE_TYPE_DELETE = 2;

	/**
	 * 操作类型-修改
	 */
	public static final Integer OPERATE_TYPE_UPDATE = 3;

	/**
	 * 操作类型-审核
	 */
	public static final Integer OPERATE_TYPE_AUDIT = 4;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "OPER_DESC")
	private String operDesc;

	@Column(name = "OPER_IP")
	private String operIp;

	@Column(name = "OPER_TIME")
	private String operTime;

	@Column(name = "OPER_TYPE")
	private Integer operType;

	@Column(name = "OPER_USER_ID")
	private Long operUserId;

	@Column(name = "OPER_USER_NAME")
	private String operUserName;

	@Column(name = "OPER_CORP_ID")
	private Long operCorpId;

	@Column(name = "OPER_CORP_NAME")
	private String operCorpName;

	@Column(name = "SYSTEM_ID")
	private Integer systemId;

	public SysLog()
	{
	}

	public Long getLogId()
	{
		return logId;
	}

	public void setLogId(Long logId)
	{
		this.logId = logId;
	}

	public String getOperDesc()
	{
		return operDesc;
	}

	public void setOperDesc(String operDesc)
	{
		this.operDesc = operDesc;
	}

	public String getOperIp()
	{
		return operIp;
	}

	public void setOperIp(String operIp)
	{
		this.operIp = operIp;
	}

	public String getOperTime()
	{
		return operTime;
	}

	public void setOperTime(String operTime)
	{
		this.operTime = operTime;
	}

	public Integer getOperType()
	{
		return operType;
	}

	public void setOperType(Integer operType)
	{
		this.operType = operType;
	}

	public Long getOperUserId()
	{
		return operUserId;
	}

	public void setOperUserId(Long operUserId)
	{
		this.operUserId = operUserId;
	}

	public String getOperUserName()
	{
		return operUserName;
	}

	public void setOperUserName(String operUserName)
	{
		this.operUserName = operUserName;
	}

	public Integer getSystemId()
	{
		return systemId;
	}

	public void setSystemId(Integer systemId)
	{
		this.systemId = systemId;
	}

	public Long getOperCorpId()
	{
		return operCorpId;
	}

	public void setOperCorpId(Long operCorpId)
	{
		this.operCorpId = operCorpId;
	}

	public String getOperCorpName()
	{
		return operCorpName;
	}

	public void setOperCorpName(String operCorpName)
	{
		this.operCorpName = operCorpName;
	}

}