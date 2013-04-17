package com.njmd.zfms.web.entity.sys;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SYS_SERVER_INFO database table.
 * 
 */
@Entity
@Table(name="SYS_SERVER_INFO")
public class SysServerInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SERVERINFO_ID")
	private long serverinfoId;

	@Column(name="CREATE_TIME")
	private String createTime;

	private String letter;

	@Column(name="RATIO_CPU")
	private BigDecimal ratioCpu;

	@Column(name="RATIO_HARDDISK")
	private BigDecimal ratioHarddisk;

	@Column(name="RATIO_MEMORY")
	private BigDecimal ratioMemory;

	@Column(name="SAVE_IP")
	private String saveIp;

	@Column(name="USE_HARDDISK")
	private String useHarddisk;

	@Column(name="USE_MEMORY")
	private String useMemory;

	public SysServerInfo() {
	}

	public long getServerinfoId() {
		return this.serverinfoId;
	}

	public void setServerinfoId(long serverinfoId) {
		this.serverinfoId = serverinfoId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLetter() {
		return this.letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public BigDecimal getRatioCpu() {
		return this.ratioCpu;
	}

	public void setRatioCpu(BigDecimal ratioCpu) {
		this.ratioCpu = ratioCpu;
	}

	public BigDecimal getRatioHarddisk() {
		return this.ratioHarddisk;
	}

	public void setRatioHarddisk(BigDecimal ratioHarddisk) {
		this.ratioHarddisk = ratioHarddisk;
	}

	public BigDecimal getRatioMemory() {
		return this.ratioMemory;
	}

	public void setRatioMemory(BigDecimal ratioMemory) {
		this.ratioMemory = ratioMemory;
	}

	public String getSaveIp() {
		return this.saveIp;
	}

	public void setSaveIp(String saveIp) {
		this.saveIp = saveIp;
	}

	public String getUseHarddisk() {
		return this.useHarddisk;
	}

	public void setUseHarddisk(String useHarddisk) {
		this.useHarddisk = useHarddisk;
	}

	public String getUseMemory() {
		return this.useMemory;
	}

	public void setUseMemory(String useMemory) {
		this.useMemory = useMemory;
	}

}