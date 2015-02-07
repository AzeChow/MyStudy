package com.bestway.bcus.enc.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 进出口申请单顺序
 * 
 * @author Administrator
 * 
 */
public class InputInExRequestBillOrder extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 单据号
	 */
	private Integer billNo = null;

	/**
	 * 客户全称
	 */
	private Integer scmCoc = null;

	/**
	 * 客户供应商代码/名称选项
	 */
	private String scmCocCodeName = null;

	/**
	 * 集装箱号
	 */
	private Integer containerCode = null;

	/**
	 * 运输工具
	 */
	private Integer conveyance = null;

	/**
	 * 开始生效
	 */
	private Integer beginAvailability = null;

	/**
	 * 料号
	 */
	private Integer ptNo = null;

	/**
	 * 毛重
	 */
	private Integer grossWeight = null;
   /**
    * 单毛重
    */
	private Integer invgrossWeight = null;
	/**
	 * 数量
	 */
	private Integer quantity = null;

	/**
	 * 物料名称
	 */
	private Integer factoryName = null;

	/**
	 * 物料规格
	 */
	private Integer falctorySpece = null;

	/**
	 * 净重
	 */
	private Integer netWeight = null;
	/**
	 *  单净重
	 */
	private Integer invnetWeight = null;
	/**
	 * 制单号
	 */
	private Integer makeBillNo = null;

	/**
	 * 工厂单位
	 */
	private Integer calUnit = null;

	/**
	 * 版本号
	 */
	private Integer version = null;

	/**
	 * 单位单价
	 */
	private Integer unitPrice = null;

	/**
	 * 国家
	 */
	private Integer country = null;

	/**
	 * 产销国代码/名称选项
	 */
	private String countryCodeName = null;

	/**
	 * 进出口岸代码/名称选项
	 */
	private String ieportCodeName = null;

	/**
	 * 总价
	 */
	private Integer amountPrice = null;

	/**
	 * 币制
	 */
	private Integer curency = null;

	/**
	 * 币制代码/名称选项
	 */
	private String curencyCodeName = null;

	/**
	 * 件数
	 */
	private Integer picec = null;

	/**
	 * 是否默认生效
	 * 
	 * @return
	 */
	private String isvalid = null;

	/**
	 * 是否允许单据号重复
	 * 
	 * @return
	 */
	private String isdjnore = null;

	/**
	 * 扩展备注栏
	 * 
	 * @return
	 */
	private Integer extendMemo = null;

	/**
	 * 扩展备注栏
	 * 
	 * @return
	 */
	private Integer extendMemoHead = null;

	/**
	 * 进出口岸
	 * 
	 * @return
	 */
	private Integer ieport = null;
	/**
	 * 运输方式代码/名称选项
	 */
	private String TransferModeCodeName = null;
	

	public String getTransferModeCodeName() {
		return TransferModeCodeName;
	}

	public void setTransferModeCodeName(String transferModeCodeName) {
		TransferModeCodeName = transferModeCodeName;
	}

	public Integer getAmountPrice() {
		return amountPrice;
	}

	public void setAmountPrice(Integer amountPrice) {
		this.amountPrice = amountPrice;
	}

	public Integer getBeginAvailability() {
		return beginAvailability;
	}

	public void setBeginAvailability(Integer beginAvailability) {
		this.beginAvailability = beginAvailability;
	}

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}

	public Integer getCalUnit() {
		return calUnit;
	}

	public void setCalUnit(Integer calUnit) {
		this.calUnit = calUnit;
	}

	public Integer getContainerCode() {
		return containerCode;
	}

	public void setContainerCode(Integer containerCode) {
		this.containerCode = containerCode;
	}

	public Integer getConveyance() {
		return conveyance;
	}

	public void setConveyance(Integer conveyance) {
		this.conveyance = conveyance;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getCurency() {
		return curency;
	}

	public void setCurency(Integer curency) {
		this.curency = curency;
	}

	public Integer getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(Integer factoryName) {
		this.factoryName = factoryName;
	}

	public Integer getFalctorySpece() {
		return falctorySpece;
	}

	public void setFalctorySpece(Integer falctorySpece) {
		this.falctorySpece = falctorySpece;
	}

	public Integer getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Integer getMakeBillNo() {
		return makeBillNo;
	}

	public void setMakeBillNo(Integer makeBillNo) {
		this.makeBillNo = makeBillNo;
	}

	public Integer getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Integer netWeight) {
		this.netWeight = netWeight;
	}

	public Integer getPtNo() {
		return ptNo;
	}

	public void setPtNo(Integer ptNo) {
		this.ptNo = ptNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(Integer scmCoc) {
		this.scmCoc = scmCoc;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getPicec() {
		return picec;
	}

	public void setPicec(Integer picec) {
		this.picec = picec;
	}

	public String getCountryCodeName() {
		return countryCodeName;
	}

	public void setCountryCodeName(String countryCodeName) {
		this.countryCodeName = countryCodeName;
	}

	public String getCurencyCodeName() {
		return curencyCodeName;
	}

	public void setCurencyCodeName(String curencyCodeName) {
		this.curencyCodeName = curencyCodeName;
	}

	public String getScmCocCodeName() {
		return scmCocCodeName;
	}
	

	public void setScmCocCodeName(String scmCocCodeName) {
		this.scmCocCodeName = scmCocCodeName;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getIsdjnore() {
		return isdjnore;
	}

	public void setIsdjnore(String isdjnore) {
		this.isdjnore = isdjnore;
	}

	public Integer getExtendMemo() {
		return extendMemo;
	}

	public void setExtendMemo(Integer extendMemo) {
		this.extendMemo = extendMemo;
	}

	public Integer getExtendMemoHead() {
		return extendMemoHead;
	}

	public void setExtendMemoHead(Integer extendMemoHead) {
		this.extendMemoHead = extendMemoHead;
	}

	public Integer getIeport() {
		return ieport;
	}

	public void setIeport(Integer ieport) {
		this.ieport = ieport;
	}

	public String getIeportCodeName() {
		return ieportCodeName;
	}

	public void setIeportCodeName(String ieportCodeName) {
		this.ieportCodeName = ieportCodeName;
	}

	public Integer getInvgrossWeight() {
		return invgrossWeight;
	}

	public void setInvgrossWeight(Integer invgrossWeight) {
		this.invgrossWeight = invgrossWeight;
	}

	public Integer getInvnetWeight() {
		return invnetWeight;
	}

	public void setInvnetWeight(Integer invnetWeight) {
		this.invnetWeight = invnetWeight;
	}

}
