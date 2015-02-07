package com.bestway.fixasset.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 批文协议表头
 * @author Administrator
 *
 */
public class Agreement extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 批文状态
	 * APPLY_POR = "1"; // 申请备案
	 * WAIT_EAA = "2"; // 等待审批
	 * PROCESS_EXE = "3"; // 正在执行
	 * CHANGING_EXE = "4"; // 正在变更
	 * CHANGING_CANCEL = "5"; // 已经核销
	 */
	private Integer declareState;

	/**
	 * 手册编号
	 */
	private String emsNo;

	/**
	 * 协议批文号
	 */
	private String sancEmsNo;

	/**
	 * 经营单位代码
	 */
	private String tradeCode;

	/**
	 * 经营单位名称
	 */
	private String tradeName;

	/**
	 * 收货单位代码
	 */
	private String machCode;

	/**
	 * 收货单位名称
	 */
	private String machName;

	/**
	 * 开始有效期
	 */
	private Date beginDate;

	/**
	 * 结束有效期
	 */
	private Date endDate;

	/**
	 * 审批日期
	 */
	private Date approveDate;

	/**
	 * 联系人
	 */
	private String linkMan;

	/**
	 * 联系电话
	 */
	private String linkTel;

	/**
	 * 币制
	 */
	private Curr curr;

	/**
	 * 项目用汇额
	 */
	private Double totalMoney;

	/**
	 * 变更累计余额
	 */
	private Double changeRemainMoney;

	/**
	 * 企业地址
	 */
	private String address;

	/**
	 * 征面性质
	 */
	private LevyKind levyKind;

	/**
	 * 注册资金
	 */
	private Double registeredMoney;

	/**
	 * 投资总额
	 */
	private Double investMoney;

	/**
	 * 经营范围
	 */
	private String workingRange;
	
	/**
	 * 变更次数
	 */
	private Integer changedTimes;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Double getChangeRemainMoney() {
		return changeRemainMoney;
	}

	public void setChangeRemainMoney(Double changeRemainMoney) {
		this.changeRemainMoney = changeRemainMoney;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public Integer getDeclareState() {
		return declareState;
	}

	public void setDeclareState(Integer declareState) {
		this.declareState = declareState;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(Double investMoney) {
		this.investMoney = investMoney;
	}

	public LevyKind getLevyKind() {
		return levyKind;
	}

	public void setLevyKind(LevyKind levyKind) {
		this.levyKind = levyKind;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getMachCode() {
		return machCode;
	}

	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}

	public String getMachName() {
		return machName;
	}

	public void setMachName(String machName) {
		this.machName = machName;
	}

	public Double getRegisteredMoney() {
		return registeredMoney;
	}

	public void setRegisteredMoney(Double registeredMoney) {
		this.registeredMoney = registeredMoney;
	}

	public String getSancEmsNo() {
		return sancEmsNo;
	}

	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getWorkingRange() {
		return workingRange;
	}

	public void setWorkingRange(String workingRange) {
		this.workingRange = workingRange;
	}

	public Integer getChangedTimes() {
		return changedTimes;
	}

	public void setChangedTimes(Integer changedTimes) {
		this.changedTimes = changedTimes;
	}
	
}
