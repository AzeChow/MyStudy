package com.bestway.common.fpt.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempFptBillItemForMakeBGD  implements Serializable{
	/**
     * 备注 :发货与收退货是0 ;收货与发退货是1
     */
    /**
     * ---------------------新增字段------------------------------*
     */
    private List<FptBillItem> fptBillItemSet = new ArrayList<FptBillItem>();
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
     * @returntrGno
     */
    private Integer outNo = null;
    /**
     * 手册号
     */
    private String inEmsNo = null;

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
    private String complexCode = null;
    
	/**
	 * 海关商品编码 
	 */
	private Complex complex = null; //由于前端使用 PropertyUtils.copyProperties 所以添加字段 HH 2013.11.5
	/**
	 * 转厂进出货单表头
	 */
	private FptBillHead fptBillHead = null; //由于前端使用 PropertyUtils.copyProperties 所以添加字段 HH 2013.11.5
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

    private Boolean isSelected = true;

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

    public String getComplexCode() {
        return complexCode;
    }

    public void setComplexCode(String complexCode) {
        this.complexCode = complexCode;
    }
    public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public FptBillHead getFptBillHead() {
		return fptBillHead;
	}

	public void setFptBillHead(FptBillHead fptBillHead) {
		this.fptBillHead = fptBillHead;
	}
//    /**
//     * @return Returns the transferFactoryBill.
//     */
//    public FptBillHead getTransferFactoryBill() {
//        return fptBillHead;
//    }
//
//    /**
//     * @param transferFactoryBill The transferFactoryBill to set.
//     */
//    public void setTransferFactoryBill(FptBillHead fptBillHead) {
//        this.fptBillHead = fptBillHead;
//    }
//    public FptBillHead getFptBillHead() {
//        return fptBillHead;
//    }
//
//    public void setFptBillHead(FptBillHead fptBillHead) {
//        this.fptBillHead = fptBillHead;
//    }
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
//
//    public String getModifyMake() {
//        return modifyMake;
//    }
//
//    public void setModifyMake(String modifyMake) {
//        this.modifyMake = modifyMake;
//    }

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


    public String getBillSort() {
        return billSort;
    }

    public void setBillSort(String billSort) {
        this.billSort = billSort;
    }


    public List<FptBillItem> getFptBillItemSet() {
        return fptBillItemSet;
    }

    public void setFptBillItemSet(List<FptBillItem> fptBillItemSet) {
        this.fptBillItemSet = fptBillItemSet;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}
