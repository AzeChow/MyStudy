package com.bestway.bcs.contract.entity;

import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspParameterSet;

/**
 * BCS参数设置
 */
public class BcsParameterSet extends CspParameterSet {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 管理类别 0为既用于关务管理也向海关申报数据;1为仅用于关务管理不向海关申报数据
	 */
	private String manageType = "0";

	/**
	 * QP数据导入路径
	 */
	private String loadQPDataXmlDir = "";

	/**
	 * 是否是纸质手册电子化
	 */
	private Boolean isContractEms;

	/**
	 * 修改成品反写BOM和料件
	 */
	public Boolean updateContractExgWriteBackBomImg = false;

	/**
	 * 修改料件反写BOM和成品
	 */
	public Boolean updateContractImgWriteBackBomExg = false;

	/**
	 * 修改BOM反写料件
	 */
	public Boolean updateContractBomWriteBackImg = false;

	/**
	 * 修改BOM反写成品
	 */
	public Boolean updateContractBomWriteBackExg = false;

	/**
	 * 允许多本备案资料库
	 */
	public Boolean putOnRecords = true;

	/**
	 * 单价小数位控制
	 */
	private Integer priceBit = 5;

	/**
	 * 数量小数位控制
	 */
	private Integer countBit = 5;

	/**
	 * 金额小数位控制
	 */
	private Integer moneyBit = 5;

	/**
	 * 名称、规格是否要限定长度
	 */
	private Boolean isControlLength = false;

	/**
	 * 名称、规格限定多长,默认为30字节,以字节为当为
	 */
	private Integer bytesLength = 30;

	/**
	 * 报表小数位数,默认为2位
	 */
	private Integer reportDecimalLength = null;
	/**
	 * 报表单耗小数位控制，默认为2位
	 */
	private Integer reportDecimalLengthUnitWaste = null;

	/**
	 * 报表损耗量小数位控制
	 */
	private Integer wasteAmount = null;

	/**
	 * 报表总用量小数位控制
	 */
	private Integer totalAmount = null;

	/**
	 * 核销表进口总金额扣减退料出口金额
	 */
	private Boolean checkIsComputeReturn = false;
	// ==========出口加工发票栏位
	/**
	 * 出口发票字体保存
	 */
	private String exportfpfont = "";
	/**
	 * 出口发票左边距
	 */
	private Integer pageLeft;
	/**
	 * 出口发票右边距
	 */
	private Integer pageRight;
	/**
	 * 出口发票上边距
	 */
	private Integer pageTop;
	/**
	 * 出口发票下边距
	 */
	private Integer pageBottom;

	/**
	 * 手册表头进口总值是否统计备案料件内购金额
	 */
	private Boolean isTotalMoney = false;

	/**
	 * 是否 从Qp导入文件计算导出手册资料原料费用
	 * 
	 */
	private Boolean isCalulateSubjectCost;

	public Integer getPageLeft() {
		return pageLeft;
	}

	public void setPageLeft(Integer pageLeft) {
		this.pageLeft = pageLeft;
	}

	public Integer getPageRight() {
		return pageRight;
	}

	public void setPageRight(Integer pageRight) {
		this.pageRight = pageRight;
	}

	public Integer getPageTop() {
		return pageTop;
	}

	public void setPageTop(Integer pageTop) {
		this.pageTop = pageTop;
	}

	public Integer getPageBottom() {
		return pageBottom;
	}

	public void setPageBottom(Integer pageBottom) {
		this.pageBottom = pageBottom;
	}

	public String getLoadQPDataXmlDir() {
		return loadQPDataXmlDir;
	}

	public void setLoadQPDataXmlDir(String loadQPDataXmlDir) {
		this.loadQPDataXmlDir = loadQPDataXmlDir;
	}

	public Boolean getIsContractEms() {
		return isContractEms;
	}

	public void setIsContractEms(Boolean isContractEms) {
		this.isContractEms = isContractEms;
	}

	public Boolean getUpdateContractBomWriteBackExg() {
		return updateContractBomWriteBackExg;
	}

	public void setUpdateContractBomWriteBackExg(
			Boolean updateContractBomWriteBackExg) {
		this.updateContractBomWriteBackExg = updateContractBomWriteBackExg;
	}

	public Boolean getUpdateContractBomWriteBackImg() {
		return updateContractBomWriteBackImg;
	}

	public void setUpdateContractBomWriteBackImg(
			Boolean updateContractBomWriteBackImg) {
		this.updateContractBomWriteBackImg = updateContractBomWriteBackImg;
	}

	public Boolean getUpdateContractExgWriteBackBomImg() {
		return updateContractExgWriteBackBomImg;
	}

	public void setUpdateContractExgWriteBackBomImg(
			Boolean updateContractExgWriteBackBomImg) {
		this.updateContractExgWriteBackBomImg = updateContractExgWriteBackBomImg;
	}

	public Boolean getUpdateContractImgWriteBackBomExg() {
		return updateContractImgWriteBackBomExg;
	}

	public void setUpdateContractImgWriteBackBomExg(
			Boolean updateContractImgWriteBackBomExg) {
		this.updateContractImgWriteBackBomExg = updateContractImgWriteBackBomExg;
	}

	public Boolean getPutOnRecords() {
		return putOnRecords;
	}

	public void setPutOnRecords(Boolean putOnRecords) {
		this.putOnRecords = putOnRecords;
	}

	public Integer getCountBit() {
		return countBit;
	}

	public void setCountBit(Integer countBit) {
		this.countBit = countBit;
	}

	public Integer getMoneyBit() {
		return moneyBit;
	}

	public void setMoneyBit(Integer moneyBit) {
		this.moneyBit = moneyBit;
	}

	public Integer getPriceBit() {
		return priceBit;
	}

	public void setPriceBit(Integer priceBit) {
		this.priceBit = priceBit;
	}

	/**
	 * @return the controlLength
	 */
	public Integer getBytesLength() {
		return bytesLength;
	}

	/**
	 * @param controlLength
	 *            the controlLength to set
	 */
	public void setBytesLength(Integer controlLength) {
		this.bytesLength = controlLength;
	}

	/**
	 * @return the isControlLength
	 */
	public Boolean getIsControlLength() {
		return isControlLength;
	}

	/**
	 * @param isControlLength
	 *            the isControlLength to set
	 */
	public void setIsControlLength(Boolean isControlLength) {
		this.isControlLength = isControlLength;
	}

	/**
	 * @return the reportDecimalLength
	 */
	public Integer getReportDecimalLength() {
		return reportDecimalLength;
	}

	/**
	 * @param reportDecimalLength
	 *            the reportDecimalLength to set
	 */
	public void setReportDecimalLength(Integer reportDecimalLength) {
		this.reportDecimalLength = reportDecimalLength;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public String getExportfpfont() {
		return exportfpfont;
	}

	public void setExportfpfont(String exportfpfont) {
		this.exportfpfont = exportfpfont;
	}

	public Boolean getCheckIsComputeReturn() {
		return checkIsComputeReturn;
	}

	public void setCheckIsComputeReturn(Boolean checkIsComputeReturn) {
		this.checkIsComputeReturn = checkIsComputeReturn;
	}

	public Integer getReportDecimalLengthUnitWaste() {
		return reportDecimalLengthUnitWaste;
	}

	public void setReportDecimalLengthUnitWaste(
			Integer reportDecimalLengthUnitWaste) {
		this.reportDecimalLengthUnitWaste = reportDecimalLengthUnitWaste;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getWasteAmount() {
		return wasteAmount;
	}

	public void setWasteAmount(Integer wasteAmount) {
		this.wasteAmount = wasteAmount;
	}

	public Boolean getIsTotalMoney() {
		return isTotalMoney;
	}

	public void setIsTotalMoney(Boolean isTotalMoney) {
		this.isTotalMoney = isTotalMoney;
	}

	public Boolean getIsCalulateSubjectCost() {
		return isCalulateSubjectCost;
	}

	public void setIsCalulateSubjectCost(Boolean isCalulateSubjectCost) {
		this.isCalulateSubjectCost = isCalulateSubjectCost;
	}
}
