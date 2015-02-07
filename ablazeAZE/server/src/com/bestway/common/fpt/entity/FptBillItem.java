package com.bestway.common.fpt.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 * 结转单据商品信息
 * 
 * @author Administrator
 * 
 */
public class FptBillItem extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 备注 :发货与收退货是0 ;收货与发退货是1
	 */
	/** ---------------------新增字段------------------------------* */
	/**
	 * 转厂进出货单表头
	 */
	private FptBillHead fptBillHead = null;
	/**
	 * 转入转出标志
	 */
	private String billSort = null;
	/**
	 * 序号
	 * 
	 */
	private Integer listNo = null;
	/**
	 * 发货序号
	 * 
	 * @return
	 */
	private Integer outNo = null;
	/**
	 * 手册号
	 */
	private String inEmsNo = null;
	/**
	 * 料号
	 * 
	 * @return
	 */
	private String copGNo = null;
	/**
	 * 归并前商品名称
	 * 
	 * @return
	 */
	private String copGName = null;
	/**
	 * 归并前规格型号
	 */
	private String copGModel = null;
	/**
	 * 申请表序号
	 */
	private Integer appGNo = null; // 对应申请表中表体的序号
	/**
	 * 项号
	 */
	private Integer trGno = null; // 对应申请表中表体的备案序号
	/**
	 * 海关商品编码
	 */
	private Complex complex = null;

	/**
	 * 商品名称
	 */
	private String commName = null;

	/**
	 * 商品规格
	 */
	private String commSpec = null;
	/**
	 * 交易单位
	 */
	private Unit tradeUnit = null;
	/**
	 * 交易数量
	 */
	private Double tradeQty = null;
	/**
	 * 计量单位
	 */
	private Unit unit = null;
	/**
	 * 申报数量
	 */
	private Double qty = null;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 工厂单位
	 */
	private CalUnit ptUnit= null;
	/**
	 * 工厂数量
	 */
	private Double ptAmount= null;

	private Boolean isSelected = true;
	/**
	 * 是否转报关单
	 */
	private Boolean isCustomsDeclaration = false;

	public String getEmsNo() {
		return inEmsNo;
	}

	public void setEmsNo(String emsNo) {
		this.inEmsNo = emsNo;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * @return Returns the transferFactoryBill.
	 */
	public FptBillHead getTransferFactoryBill() {
		return fptBillHead;
	}

	/**
	 * @param transferFactoryBill
	 *            The transferFactoryBill to set.
	 */
	public void setTransferFactoryBill(FptBillHead fptBillHead) {
		this.fptBillHead = fptBillHead;
	}

	public FptBillHead getFptBillHead() {
		return fptBillHead;
	}

	public void setFptBillHead(FptBillHead fptBillHead) {
		this.fptBillHead = fptBillHead;
	}

	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public Integer getOutNo() {
		return outNo;
	}

	public void setOutNo(Integer outNo) {
		this.outNo = outNo;
	}

	public String getCopGNo() {
		return copGNo;
	}

	public void setCopGNo(String copGNo) {
		this.copGNo = copGNo;
	}

	public String getCopGName() {
		return copGName;
	}

	public void setCopGName(String copGName) {
		this.copGName = copGName;
	}

	public String getCopGModel() {
		return copGModel;
	}

	public void setCopGModel(String copGModel) {
		this.copGModel = copGModel;
	}

	public Integer getAppGNo() {
		return appGNo;
	}

	public void setAppGNo(Integer appGNo) {
		this.appGNo = appGNo;
	}

	public Unit getTradeUnit() {
		return tradeUnit;
	}

	public void setTradeUnit(Unit tradeUnit) {
		this.tradeUnit = tradeUnit;
	}

	public Double getTradeQty() {
		return tradeQty;
	}

	public void setTradeQty(Double tradeQty) {
		this.tradeQty = tradeQty;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public String getInEmsNo() {
		return inEmsNo;
	}

	public void setInEmsNo(String isEmsNo) {
		this.inEmsNo = isEmsNo;
	}

	public Integer getTrGno() {
		return trGno;
	}

	public void setTrGno(Integer trGno) {
		this.trGno = trGno;
	}

	public Boolean getIsCustomsDeclaration() {
		return isCustomsDeclaration;
	}

	public void setIsCustomsDeclaration(Boolean isCustomsDeclaration) {
		this.isCustomsDeclaration = isCustomsDeclaration;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getBillSort() {
		return billSort;
	}

	public void setBillSort(String billSort) {
		this.billSort = billSort;
	}

	public CalUnit getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public Double getPtAmount() {
		return ptAmount;
	}

	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}

}