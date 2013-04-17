package com.njmd.zfms.web.entity.notice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the SYS_NOTICE_READ_LOG database table.
 * 
 */
@Entity
@Table(name = "NOTICE_READ_LOG")
public class NoticeReadLog implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "LOGIN_ID")
	private Long loginId;

	@Column(name = "NOTICE_ID")
	private Long noticeId;

	@Column(name = "READ_TIME")
	private String readTime;

	public NoticeReadLog()
	{
	}

	public long getId()
	{
		return this.id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return this.loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getNoticeId()
	{
		return this.noticeId;
	}

	public void setNoticeId(Long noticeId)
	{
		this.noticeId = noticeId;
	}

	public String getReadTime()
	{
		return this.readTime;
	}

	public void setReadTime(String readTime)
	{
		this.readTime = readTime;
	}

}