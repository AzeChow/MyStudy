package com.bestway.bcs.qp.entity;

import com.bestway.common.CommonUtils;

/**
 * 备案资料库表头
 * 
 */
public class BcsQPDictPorHead implements java.io.Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 报送海关
	 */
	private String declareCustomsCode = null;

	/**
	 * 主管外经部门
	 */
	private String redDepCode = null;

	/**
	 * 备案资料库编号
	 */
	private String dictPorEmsNo;

	/**
	 * 企业内部编号
	 */
	private String copEmsNo;

	/**
	 * 经营单位编号
	 */
	private String tradeCode;

	/**
	 * 经营单位名称
	 */
	private String tradeName;

	/**
	 * 加工单位编号
	 */
	private String machCode;

	/**
	 * 加工单位名称
	 */
	private String machName;

	/**
	 * 年加工生产能力
	 */
	private Double productRatio;

	/**
	 * 申报日期(yyyy-MM-dd HH:mm:ss)
	 */
	private String declareDate;

	/**
	 * 征免性质
	 */
	private String levyKindCode;

	/**
	 * 收货地区
	 */
	private String receiveAreaCode = null;

	/**
	 * 加工种类
	 */
	private String machiningTypeCode;

	/**
	 * 贸易方式
	 */
	private String tradeModeCode= null;

	/**
	 * 币制
	 */
	private String currCode = null;

	/**
	 * 管理对象
	 */
	private String manageObject;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 录入员代号
	 */
	private String inputER = null;

	/**
	 * 开始有效期(yyyy-MM-dd HH:mm:ss)
	 */
	private String beginDate;

	/**
	 * 结束有效期(yyyy-MM-dd HH:mm:ss)
	 */
	private String endDate;
	/**
	 * 限制类标志
	 * ADJUST_BEFORE_EMS = "1";	调整前旧手册
	 * ADJUST_AFTER_EMS = "2";	调整后新手册
	 * ACOUNT_BOOK_USE = "3";	台账专用手册
	 */
	private String limitFlag;
	/**
	 * 进口料件范围1
	 */
	private String imgRangeSpec;
	/**
	 * 出口成品范围
	 */
	private String exgRangeSpec;
	/**
	 * 是否选中
	 */
	private Boolean isSelected=false;

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public String getDictPorEmsNo() {
		return dictPorEmsNo;
	}

	public void setDictPorEmsNo(String credenceEmsNo) {
		this.dictPorEmsNo = credenceEmsNo;
	}


	public Double getProductRatio() {
		return productRatio;
	}

	public void setProductRatio(Double productRatio) {
		this.productRatio = productRatio;
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


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getManageObject() {
		return manageObject;
	}

	public void setManageObject(String manageObject) {
		this.manageObject = manageObject;
	}

	public String getDeclareCode() {
		if ("0".equals(manageObject)) {
			return this.getTradeCode();
		} else {
			return this.getMachCode();
		}
	}

	public String getDeclareName() {
		if ("0".equals(manageObject)) {
			return this.getTradeName();
		} else {
			return this.getMachName();
		}
	}

	public String getEmsType() {
		return "Z";
	}

	public String getInputER() {
		return inputER;
	}

	public void setInputER(String inputER) {
		this.inputER = inputER;
	}

	public String getLimitFlag() {
		return limitFlag;
	}

	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}

	public String getDeclareCustomsCode() {
		return declareCustomsCode;
	}

	public void setDeclareCustomsCode(String declareCustomsCode) {
		this.declareCustomsCode = declareCustomsCode;
	}

	public String getRedDepCode() {
		return redDepCode;
	}

	public void setRedDepCode(String redDepCode) {
		this.redDepCode = redDepCode;
	}

	public String getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(String declareDate) {
		this.declareDate = declareDate;
	}

	public String getLevyKindCode() {
		return levyKindCode;
	}

	public void setLevyKindCode(String levyKindCode) {
		this.levyKindCode = levyKindCode;
	}

	public String getReceiveAreaCode() {
		return receiveAreaCode;
	}

	public void setReceiveAreaCode(String receiveAreaCode) {
		this.receiveAreaCode = receiveAreaCode;
	}

	public String getMachiningTypeCode() {
		return machiningTypeCode;
	}

	public void setMachiningTypeCode(String machiningTypeCode) {
		this.machiningTypeCode = machiningTypeCode;
	}

	public String getTradeModeCode() {
		return tradeModeCode;
	}

	public void setTradeModeCode(String tradeModelCode) {
		this.tradeModeCode = tradeModelCode;
	}

	public String getCurrCode() {
		return currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getImgRangeSpec() {
		return imgRangeSpec;
	}

	public void setImgRangeSpec(String imgRangeSpec) {
		this.imgRangeSpec = imgRangeSpec;
	}

	public String getExgRangeSpec() {
		return exgRangeSpec;
	}

	public void setExgRangeSpec(String exgRangeSpec) {
		this.exgRangeSpec = exgRangeSpec;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
