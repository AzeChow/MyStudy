package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.MaterialBomDetail;

/**
 * 制单号折料中间类
 */
public class WorkOrderList implements Serializable {

	/**
	 * 日期
	 */
	private String date = null;
	/**
	 * 单据类型
	 */
	private String billType = null;
	/**
	 * 工厂料号
	 */
	private String ptPart = null;
	/**
	 * /** 工厂名称
	 */
	private String ptName = null;
	/**
	 *工厂规格
	 */
	private String ptSpec = null;
	/**
	 * 工厂单位
	 */
	private CalUnit ptUnit = null;

	/**
	 * 报关名称
	 */
	private String hsName = null;

	/**
	 * 报关规格
	 */
	private String hsSpec = null;

	/**
	 * 报关单位
	 */
	private Unit hsUnit = null;
	/**
	 * 海关库存数量
	 */
	private MaterialBomDetail bom = null;
	/**
	 * 工厂进项数量
	 */
	private Double ptIn = null;
	/**
	 * 海关进项数量
	 */
	private Double hsIn = null;
	/**
	 * 工厂出项数量
	 */
	private Double ptOut = null;
	/**
	 * 海关出项数量
	 */
	private Double hsOut = null;
	/**
	 * 工厂库存数量
	 */
	private Double ptAmount = null;
	/**
	 * 海关库存数量
	 */
	private Double hsAmount = null;
	/**
	 *是否进项
	 */
	private boolean isIn = false;
	/**
	 * 进项单据号
	 */
	private String inBillNo = null;
	/**
	 * 出项单据号
	 */
	private String outBillNo = null;

	/**
	 * 出项单据号
	 */
	private Double unitConvert = null;
	/**
	 * 父单据
	 */
	private BillDetail bill = null;
	/**
	 * 成品-料号
	 */
	private String productPtPart;
	/**
	 * 成品-名称
	 */
	private String productPtName;
	/**
	 * 成品-规格型号
	 */
	private String productPtSpec;
	/**
	 * 成品-单位
	 */
	private CalUnit productPtUnit;

	/**
	 * 成品-数量
	 */
	private Double productPtAmount;
	/**
	 * 成品-折算报关单位比率
	 */
	private Double productUnitConvert = null;
	/**
	 * 物料类别
	 */
	private String materielType = null;

	/**
	 * 
	 * @param bill
	 */
	public WorkOrderList(TempBomBillDetail bill) {
		this.bill = bill.getBill();
		if (isIn(bill.getBill())) {
			ptIn = bill.getBill().getPtAmount();
			hsIn = bill.getBill().getHsAmount();
			inBillNo = bill.getBill().getBillMaster().getBillNo();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			date = format.format(bill.getBill().getBillMaster().getValidDate());
		} else {
			ptOut = bill.getBill().getPtAmount();
			hsOut = bill.getBill().getHsAmount();
			outBillNo = bill.getBill().getBillMaster().getBillNo();
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		date = format.format(bill.getBill().getBillMaster().getValidDate());
		billType = bill.getBill().getBillMaster().getBillType().getName();
		ptPart = bill.getBill().getPtPart();
		ptName = bill.getBill().getPtName();
		hsName = bill.getBill().getHsName();
		ptSpec = bill.getBill().getPtSpec();
		hsSpec = bill.getBill().getHsSpec();
		ptUnit = bill.getBill().getPtUnit();
		hsUnit = bill.getBill().getHsUnit();
		// 下面是成品信息
		productPtPart = bill.getPtPart();
		productPtName = bill.getPtName();
		productPtSpec = bill.getPtSpec();
		productPtUnit = bill.getPtUnit();
		productPtAmount = bill.getPtAmount();
		productUnitConvert = bill.getUnitConvert();
		materielType=bill.getMaterielType();

	}

	public boolean isIn(BillDetail bill2) {
		if (bill.getBillMaster().getBillType().getCode().equals("1002")
				|| bill.getBillMaster().getBillType().getCode().equals("2103")
				|| bill.getBillMaster().getBillType().getCode().equals("4002")
				|| bill.getBillMaster().getBillType().getCode().equals("4003")
				|| bill.getBillMaster().getBillType().getCode().equals("4004")
				|| bill.getBillMaster().getBillType().getCode().equals("4005")
				|| bill.getBillMaster().getBillType().getCode().equals("4006")) {
			isIn = true;
			return true;
		} else {
			isIn = false;
			return false;
		}
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public BillDetail getBill() {
		return bill;
	}

	public void setBill(BillDetail bill) {
		this.bill = bill;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public CalUnit getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public String getHsName() {
		return hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public String getInBillNo() {
		return inBillNo;
	}

	public void setInBillNo(String inBillNo) {
		this.inBillNo = inBillNo;
	}

	public String getOutBillNo() {
		return outBillNo;
	}

	public void setOutBillNo(String outBillNo) {
		this.outBillNo = outBillNo;
	}

	public Double getPtIn() {
		return ptIn;
	}

	public void setPtIn(Double ptIn) {
		this.ptIn = ptIn;
	}

	public boolean isIn() {
		return isIn;
	}

	public void setIn(boolean isIn) {
		this.isIn = isIn;
	}

	public Double getHsIn() {
		return hsIn;
	}

	public void setHsIn(Double hsIn) {
		this.hsIn = hsIn;
	}

	public Double getPtOut() {
		return ptOut;
	}

	public void setPtOut(Double ptOut) {
		this.ptOut = ptOut;
	}

	public Double getHsOut() {
		return hsOut;
	}

	public void setHsOut(Double hsOut) {
		this.hsOut = hsOut;
	}

	public Double getPtAmount() {
		return ptAmount;
	}

	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}

	public Double getHsAmount() {
		return hsAmount;
	}

	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}

	public MaterialBomDetail getBom() {
		return bom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProductPtPart() {
		return productPtPart;
	}

	public void setProductPtPart(String productPtPart) {
		this.productPtPart = productPtPart;
	}

	public String getProductPtName() {
		return productPtName;
	}

	public void setProductPtName(String productPtName) {
		this.productPtName = productPtName;
	}

	public String getProductPtSpec() {
		return productPtSpec;
	}

	public void setProductPtSpec(String productPtSpec) {
		this.productPtSpec = productPtSpec;
	}

	public CalUnit getProductPtUnit() {
		return productPtUnit;
	}

	public void setProductPtUnit(CalUnit productPtUnit) {
		this.productPtUnit = productPtUnit;
	}

	public Double getProductPtAmount() {
		return productPtAmount;
	}

	public void setProductPtAmount(Double productPtAmount) {
		this.productPtAmount = productPtAmount;
	}

	public Double getProductUnitConvert() {
		return productUnitConvert;
	}

	public void setProductUnitConvert(Double productUnitConvert) {
		this.productUnitConvert = productUnitConvert;
	}

	public void setBom(MaterialBomDetail bom) {

		this.bom = bom;
		// 名称
		ptName = (bom.getMateriel().getFactoryName());
		hsName = (bom.getMateriel().getPtName());
		// 规格
		ptSpec = (bom.getMateriel().getFactorySpec());
		hsSpec = (bom.getMateriel().getPtSpec());
		// 单位
		ptUnit = (bom.getMateriel().getCalUnit());
		hsUnit = (bom.getMateriel().getPtUnit());
		// 比率
		unitConvert = (bom.getMateriel().getUnitConvert());

		if (isIn(bill)) {
			if (bom.getUnitUsed() != null)
				ptIn = bill.getPtAmount() * bom.getUnitUsed();
			if (bill.getUnitConvert() != null)
				hsIn = ptIn * bill.getUnitConvert();
		} else {
			if (bom.getUnitUsed() != null)
				ptOut = bill.getPtAmount() * bom.getUnitUsed();
			if (bill.getUnitConvert() != null)
				hsOut = ptOut * bill.getUnitConvert();
		}

	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	

}
