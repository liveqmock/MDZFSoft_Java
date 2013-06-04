package com.njmd.zfms.web.entity.sys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.njmd.framework.entity.AuditableEntity;

/**
 * The persistent class for the SYS_LOGIN database table.
 * 
 */
@Entity
@Table(name = "SYS_LOGIN")
public class SysLogin extends AuditableEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	/** 用户是否永久有效 1-有效 */
	public static final String USER_VALID_YES = "1";

	/** 用户是否永久有效 0-无效 */
	public static final String USER_VALID_NO = "0";

	/** 用户类型 0-超级管理员 */
	public static final int USER_TYPE_SUPER_ADMIN = 0;

	/** 用户类型 1-管理员 */
	public static final int USER_TYPE_ADMIN = 1;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator="SYSLOGIN_GENERATOR",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="SYSLOGIN_GENERATOR",sequenceName="SYSLOGIN_SEQUENCE",allocationSize=1)
	@Column(name = "LOGIN_ID")
	private Long loginId;

	@Column(name = "LOGIN_LAST_TIME")
	private String loginLastTime;

	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Column(name = "LOGIN_PWD")
	private String loginPwd;

	@Column(name = "RECORD_CORP_ID")
	private Long recordCorpId;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "SYSTEM_ID")
	private Integer systemId;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "USER_CODE")
	private String userCode;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "USER_REMARK")
	private String userRemark;

	@Column(name = "USER_TYPE")
	private Integer userType;

	@Column(name = "VALID_DATE")
	private String validDate;

	@Column(name = "VALID_TAG")
	private String validTag;

	// bi-directional many-to-one association to SysCorp
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CORP_ID")
	private SysCorp sysCorp;

	// bi-directional many-to-one association to SysLoginRole
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysLogin")
	private List<SysLoginRole> sysLoginRoles;

	// 用户的角色ID（以逗号拼接）
	@Transient
	private String roleIds;

	public SysLogin()
	{
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getLoginLastTime()
	{
		return loginLastTime;
	}

	public void setLoginLastTime(String loginLastTime)
	{
		this.loginLastTime = loginLastTime;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getLoginPwd()
	{
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd)
	{
		this.loginPwd = loginPwd;
	}

	public Long getRecordCorpId()
	{
		return recordCorpId;
	}

	public void setRecordCorpId(Long recordCorpId)
	{
		this.recordCorpId = recordCorpId;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getSystemId()
	{
		return systemId;
	}

	public void setSystemId(Integer systemId)
	{
		this.systemId = systemId;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getUserCode()
	{
		return userCode;
	}

	public void setUserCode(String userCode)
	{
		this.userCode = userCode;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserRemark()
	{
		return userRemark;
	}

	public void setUserRemark(String userRemark)
	{
		this.userRemark = userRemark;
	}

	public Integer getUserType()
	{
		return userType;
	}

	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}

	public String getValidDate()
	{
		return validDate;
	}

	public void setValidDate(String validDate)
	{
		this.validDate = validDate;
	}

	public String getValidTag()
	{
		return validTag;
	}

	public void setValidTag(String validTag)
	{
		this.validTag = validTag;
	}

	public SysCorp getSysCorp()
	{
		return sysCorp;
	}

	public void setSysCorp(SysCorp sysCorp)
	{
		this.sysCorp = sysCorp;
	}

	public List<SysLoginRole> getSysLoginRoles()
	{
		return sysLoginRoles;
	}

	public void setSysLoginRoles(List<SysLoginRole> sysLoginRoles)
	{
		this.sysLoginRoles = sysLoginRoles;
	}

	public String getRoleIds()
	{
		return roleIds;
	}

	public void setRoleIds(String roleIds)
	{
		this.roleIds = roleIds;
	}

}