package com.bestway.common.transferfactory.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 关封管理明细
 * 
 * 2011-03-03 check by hcl
 */
public class CustomsEnvelopCommodityInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 关封表头
	 */
	private CustomsEnvelopBill customsEnvelopBill = null;
	/**
	 * 关封序号
	 */
	private Integer ceseqNum;
	/**
	 * 账册/手册号
	 */
	private String emsNo = null;
	/**
	 * 帐册/手册序号
	 */
	private Integer seqNum = null;
	/**
	 * 海关商品编码
	 */
	private Complex complex = null;
	/**
	 * 商品名称
	 */
	private String ptName = null;
	/**
	 * 商品规格
	 */
	private String ptSpec = null; 
	/**
	 * 单位
	 */
	private Unit unit = null; 
	/**
	 * 币制
	 */
	private Curr curr = null;
	/**
	 * 本厂数量
	 */
	private Double ownerQuantity = null; 
	/**
	 * 报关单已转数量
	 */
	private Double transferQuantity = null;
	/**
	 * 可分配数量
	 */
	private Double dispensabilityQuantity = null;
	/**
	 * 当前结转数量
	 */
	private Double currentTransferQuantity = null; 
	/**
	 * 单价
	 */
	private Double unitPrice = null; 
	/**
	 * 总价
	 */
	private Double totalPrice = null; 
	/**
	 * 对方数量
	 */
	private Double oppositeQuantity = null; 
	/**
	 * 对方商品名称
	 */
	private String oppositeName = null;
	/**
	 * 对方商品代码
	 */
	private String oppositeComplexCode = null;
	/**
	 * 对方商品型号
	 */
	private String oppositeSpec = null;
	/**
	 * 对方商品代码
	 */
	private Unit oppositeUnit = null; 
	/**
	 * 对方帐册序号
	 */
	private String oppositeEmsSerialNo = null; 
	/**
	 * 对方帐册号
	 */
	private String oppositeEmsNo = null; 
	

	/**
	 *获取可分配数量
	 */
	public Double getDispensabilityQuantity() {
		if (ownerQuantity != null) {
			int transferQuantity = 0;
			if (this.transferQuantity != null) {
				transferQuantity = this.transferQuantity.intValue();
			}
			return new Double(ownerQuantity.intValue() - transferQuantity);
		} else {
			return null;
		}
	}
	/**
	 * 获取总金额
	 */
	public Double getTotalPrice() {
		return (ownerQuantity == null ? 0.0 : ownerQuantity)
				* (unitPrice == null ? 0.0 : unitPrice);
		// return this.totalPrice;
	}
	/**
	 * 获取单价
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}
	/**
	 * 设置单价
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 设置总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获取商品编码
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 获取币制
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币制
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 获取当前结转数量
	 */
	public Double getCurrentTransferQuantity() {
		return currentTransferQuantity;
	}

	/**
	 * 设置当前结转数量
	 */
	public void setCurrentTransferQuantity(Double currentTransferQuantity) {
		this.currentTransferQuantity = currentTransferQuantity;
	}

	/**
	 * 获取关封表头
	 */
	public CustomsEnvelopBill getCustomsEnvelopBill() {
		return customsEnvelopBill;
	}

	/**
	 * 设置关封表头
	 */
	public void setCustomsEnvelopBill(CustomsEnvelopBill customsEnvelopBill) {
		this.customsEnvelopBill = customsEnvelopBill;
	}
	/**
	 * 获取对方商品编码
	 */
	public String getOppositeComplexCode() {
		return oppositeComplexCode;
	}

	/**
	 * 设置对方商品编码
	 */
	public void setOppositeComplexCode(String oppositeComplexCode) {
		this.oppositeComplexCode = oppositeComplexCode;
	}

	/**
	 * 获取对方账号
	 */
	public String getOppositeEmsNo() {
		return oppositeEmsNo;
	}

	/**
	 * 设置对方账号
	 */
	public void setOppositeEmsNo(String oppositeEmsNo) {
		this.oppositeEmsNo = oppositeEmsNo;
	}

	/**
	 * 获取对方帐册序号
	 */
	public String getOppositeEmsSerialNo() {
		return oppositeEmsSerialNo;
	}

	/**
	 * 设置对方帐册序号
	 */
	public void setOppositeEmsSerialNo(String oppositeEmsSerialNo) {
		this.oppositeEmsSerialNo = oppositeEmsSerialNo;
	}

	/**
	 * 获取对方商品名称
	 */
	public String getOppositeName() {
		return oppositeName;
	}

	/**
	 * 设置对方商品名称
	 */
	public void setOppositeName(String oppositeName) {
		this.oppositeName = oppositeName;
	}

	/**
	 * 获取对方数量
	 */
	public Double getOppositeQuantity() {
		return oppositeQuantity;
	}

	/**
	 * 设置对方数量
	 */
	public void setOppositeQuantity(Double oppositeQuantity) {
		this.oppositeQuantity = oppositeQuantity;
	}

	/**
	 * 获取对方规格
	 */
	public String getOppositeSpec() {
		return oppositeSpec;
	}

	/**
	 * 设置对方规格
	 */
	public void setOppositeSpec(String oppositeSpec) {
		this.oppositeSpec = oppositeSpec;
	}

	/**
	 * 获取对方商品代码
	 */
	public Unit getOppositeUnit() {
		return oppositeUnit;
	}

	/**
	 * 设置对方商品代码
	 */
	public void setOppositeUnit(Unit oppositeUnit) {
		this.oppositeUnit = oppositeUnit;
	}

	/**
	 * 获取本厂数量
	 */
	public Double getOwnerQuantity() {
		return ownerQuantity;
	}

	/**
	 * 设置本厂数量
	 */
	public void setOwnerQuantity(Double ownerQuantity) {
		this.ownerQuantity = ownerQuantity;
	}

	/**
	 * 获取商品名称
	 */
	public String getPtName() {
		return ptName;
	}

	/**
	 * 设置商品名称
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 获取商品规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	/**
	 * 设置商品规格
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	/**
	 * 获取报关单以转数量
	 */
	public Double getTransferQuantity() {
		return transferQuantity;
	}

	/**
	 * 设置报关单未转数量
	 */
	public void setTransferQuantity(Double transferQuantity) {
		this.transferQuantity = transferQuantity;
	}

	/**
	 * 获取单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 设置可分配数量
	 */
	public void setDispensabilityQuantity(Double dispensabilityQuantity) {
		this.dispensabilityQuantity = dispensabilityQuantity;
	}

	/**
	 * 获取序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取帐册、手册号
	 */
	public String getEmsNo() {
		if(emsNo==null||"".equals(emsNo.trim())){
			return this.customsEnvelopBill.getEmsNo();
		}
		return emsNo;
	}
	/**
	 * 设置帐册手册号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public Integer getCeseqNum() {
		return ceseqNum;
	}
	public void setCeseqNum(Integer ceseqNum) {
		this.ceseqNum = ceseqNum;
	}
}