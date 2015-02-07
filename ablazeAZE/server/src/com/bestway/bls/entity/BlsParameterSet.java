package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

/**
 * 报税物流参数设置实体类
 * 
 * @author Administrator
 * 
 */
public class BlsParameterSet extends BaseScmEntity {
	/**
	 * 远程加签地址
	 */
	private String remoteHostAddress = "";
	/**
	 * 远程加签端口
	 */
	private String remoteHostPort = "6666";
	/**
	 * 签名人IC卡ID
	 */
	private String idCard;
	/**
	 * 序号
	 */
	private String seqNo = "";

	/**
	 * 密码
	 */
	private String pwd = "";
	/**
	 * 是否是内部使用
	 */
	private Boolean isInnerUse = false;
	/**
	 * 黄埔海关服务地址
	 */
	private String hpServiceUrl = null;
	/**
	 * 是否自动申报
	 */
	private Boolean isAutoDeclare = false;

	/**
	 * 在自动申报时是否自动回执处理
	 */
	private Boolean isAutoProcess = true;
	/**
	 * 进仓申报开始时间 (24小时)
	 */
	private Integer impBeginTime = null;
	/**
	 * 进仓申报结束时间 (24小时)
	 */
	private Integer impEndTime = null;
	/**
	 * 每日发生频率类型(0:秒 1:分 2:时)
	 */
	private Integer impEverydayFrequencyType = 0;
	/**
	 * 每日发生频率
	 */
	private Integer impEverydayFrequency = 0;

	/**
	 * 发生频率(XXXXXXX)第一个X代表周日，第7个X代表周六 X=0或1
	 */
	private String impWeeklyFrequency;

	/**
	 * 进仓申报开始时间 (24小时)
	 */
	private Integer expBeginTime = null;
	/**
	 * 进仓申报结束时间 (24小时)
	 */
	private Integer expEndTime = null;
	/**
	 * 每日发生频率类型(0:秒 1:分 2:时)
	 */
	private Integer expEverydayFrequencyType = 0;
	/**
	 * 每日发生频率
	 */
	private Integer expEverydayFrequency = 0;

	/**
	 * 发生频率(XXXXXXX)第一个X代表周日，第7个X代表周六 X=0或1
	 */
	private String expWeeklyFrequency;

	/**
	 * 核销申报开始时间 (24小时)
	 */
	private Integer cobBeginTime = null;
	/**
	 * 核销申报结束时间 (24小时)
	 */
	private Integer cobEndTime = null;
	/**
	 * 每日发生频率类型(0:秒 1:分 2:时)
	 */
	private Integer cobEverydayFrequencyType = 0;
	/**
	 * 每日发生频率
	 */
	private Integer cobEverydayFrequency = 0;

	/**
	 * 发生频率(XXXXXXX)第一个X代表周日，第7个X代表周六 X=0或1
	 */
	private String cobWeeklyFrequency = "";
	/**
	 * 账册编号
	 */
	private String emsNO = "";

	public String getRemoteHostAddress() {
		return remoteHostAddress;
	}

	public void setRemoteHostAddress(String remoteHostAddress) {
		this.remoteHostAddress = remoteHostAddress;
	}

	public String getRemoteHostPort() {
		return remoteHostPort;
	}

	public void setRemoteHostPort(String remoteHostPort) {
		this.remoteHostPort = remoteHostPort;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Boolean getIsInnerUse() {
		return isInnerUse;
	}

	public void setIsInnerUse(Boolean isInnerUse) {
		this.isInnerUse = isInnerUse;
	}

	public String getHpServiceUrl() {
		if (hpServiceUrl == null || "".equals(hpServiceUrl.trim())) {
			return "http://www3.hpedi.com.cn/HpService/HpService.asmx";
		}
		return hpServiceUrl;
	}

	public void setHpServiceUrl(String hpServiceUrl) {
		this.hpServiceUrl = hpServiceUrl;
	}

	public Integer getImpBeginTime() {
		return impBeginTime;
	}

	public void setImpBeginTime(Integer impBeginTime) {
		this.impBeginTime = impBeginTime;
	}

	public Integer getImpEndTime() {
		return impEndTime;
	}

	public void setImpEndTime(Integer impEndTime) {
		this.impEndTime = impEndTime;
	}

	public Integer getImpEverydayFrequencyType() {
		return impEverydayFrequencyType;
	}

	public void setImpEverydayFrequencyType(Integer impEverydayFrequencyType) {
		this.impEverydayFrequencyType = impEverydayFrequencyType;
	}

	public Integer getImpEverydayFrequency() {
		return impEverydayFrequency;
	}

	public void setImpEverydayFrequency(Integer impEverydayFrequency) {
		this.impEverydayFrequency = impEverydayFrequency;
	}

	public String getImpWeeklyFrequency() {
		return impWeeklyFrequency;
	}

	public void setImpWeeklyFrequency(String impWeeklyFrequency) {
		this.impWeeklyFrequency = impWeeklyFrequency;
	}

	public Integer getExpBeginTime() {
		return expBeginTime;
	}

	public void setExpBeginTime(Integer expBeginTime) {
		this.expBeginTime = expBeginTime;
	}

	public Integer getExpEndTime() {
		return expEndTime;
	}

	public void setExpEndTime(Integer expEndTime) {
		this.expEndTime = expEndTime;
	}

	public Integer getExpEverydayFrequencyType() {
		return expEverydayFrequencyType;
	}

	public void setExpEverydayFrequencyType(Integer expEverydayFrequencyType) {
		this.expEverydayFrequencyType = expEverydayFrequencyType;
	}

	public Integer getExpEverydayFrequency() {
		return expEverydayFrequency;
	}

	public void setExpEverydayFrequency(Integer expEverydayFrequency) {
		this.expEverydayFrequency = expEverydayFrequency;
	}

	public String getExpWeeklyFrequency() {
		return expWeeklyFrequency;
	}

	public void setExpWeeklyFrequency(String expWeeklyFrequency) {
		this.expWeeklyFrequency = expWeeklyFrequency;
	}

	public Boolean getIsAutoDeclare() {
		return isAutoDeclare;
	}

	public void setIsAutoDeclare(Boolean isAutoDeclare) {
		this.isAutoDeclare = isAutoDeclare;
	}

	public Boolean getIsAutoProcess() {
		return isAutoProcess;
	}

	public void setIsAutoProcess(Boolean isAutoProcess) {
		this.isAutoProcess = isAutoProcess;
	}

	public Integer getCobBeginTime() {
		return cobBeginTime;
	}

	public void setCobBeginTime(Integer cobBeginTime) {
		this.cobBeginTime = cobBeginTime;
	}

	public Integer getCobEndTime() {
		return cobEndTime;
	}

	public void setCobEndTime(Integer cobEndTime) {
		this.cobEndTime = cobEndTime;
	}

	public Integer getCobEverydayFrequencyType() {
		return cobEverydayFrequencyType;
	}

	public void setCobEverydayFrequencyType(Integer cobEverydayFrequencyType) {
		this.cobEverydayFrequencyType = cobEverydayFrequencyType;
	}

	public Integer getCobEverydayFrequency() {
		return cobEverydayFrequency;
	}

	public void setCobEverydayFrequency(Integer cobEverydayFrequency) {
		this.cobEverydayFrequency = cobEverydayFrequency;
	}

	public String getCobWeeklyFrequency() {
		return cobWeeklyFrequency;
	}

	public void setCobWeeklyFrequency(String cobWeeklyFrequency) {
		this.cobWeeklyFrequency = cobWeeklyFrequency;
	}

	public String getEmsNO() {
		return emsNO;
	}

	public void setEmsNO(String emsNO) {
		this.emsNO = emsNO;
	}

}
