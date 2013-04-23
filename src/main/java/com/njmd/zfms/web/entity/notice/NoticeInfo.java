package com.njmd.zfms.web.entity.notice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.njmd.framework.entity.AuditableEntity;
import com.njmd.zfms.web.entity.sys.SysCorp;

/**
 * The persistent class for the SYS_NOTICE database table.
 * 
 */
@Entity
@Table(name = "NOTICE_INFO")
public class NoticeInfo extends AuditableEntity implements Serializable
{
	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NOTICE_ID")
	private Long noticeId;

	/*
	 * @Column(name = "CORP_ID") private Long corpId;
	 */

	@Lob
	@Column(name = "NOTICE_CONTENT")
	private String noticeContent;

	@Column(name = "NOTICE_TITLE")
	private String noticeTitle;

	@Column(name = "NOTICE_TYPE")
	private Integer noticeType;

	@Column(name = "TARGET_IDS")
	private String targetIds;

	@ManyToOne
	@JoinColumn(name = "CORP_ID")
	private SysCorp sysCorp;

	public NoticeInfo()
	{
	}

	public Long getNoticeId()
	{
		return this.noticeId;
	}

	public void setNoticeId(Long noticeId)
	{
		this.noticeId = noticeId;
	}

	/*
	 * public Long getCorpId() { return this.corpId; }
	 * 
	 * public void setCorpId(Long corpId) { this.corpId = corpId; }
	 */

	public String getCreateBy()
	{
		return this.createBy;
	}

	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}

	public String getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getLastModifyBy()
	{
		return this.lastModifyBy;
	}

	public void setLastModifyBy(String lastModifyBy)
	{
		this.lastModifyBy = lastModifyBy;
	}

	public String getLastModifyTime()
	{
		return this.lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime)
	{
		this.lastModifyTime = lastModifyTime;
	}

	public String getNoticeContent()
	{
		return this.noticeContent;
	}

	public void setNoticeContent(String noticeContent)
	{
		this.noticeContent = noticeContent;
	}

	public String getNoticeTitle()
	{
		return this.noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle)
	{
		this.noticeTitle = noticeTitle;
	}

	public Integer getNoticeType()
	{
		return this.noticeType;
	}

	public void setNoticeType(Integer noticeType)
	{
		this.noticeType = noticeType;
	}

	public String getTargetIds()
	{
		return this.targetIds;
	}

	public void setTargetIds(String targetIds)
	{
		this.targetIds = targetIds;
	}

	public SysCorp getSysCorp()
	{
		return sysCorp;
	}

	public void setSysCorp(SysCorp sysCorp)
	{
		this.sysCorp = sysCorp;
	}

}