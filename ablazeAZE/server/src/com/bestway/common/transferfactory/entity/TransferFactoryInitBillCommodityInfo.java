/*
 * Created on 2004-10-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 结转初始化单据单据(转厂)
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TransferFactoryInitBillCommodityInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 表头资料
	 */
	// private Materiel materiel = null;
	private TransferFactoryInitBill transferFactoryInitBill = null;
	/**
	 * 手册号
	 */
	private String emsNo=null;
	/**
	 * 账册手册序号
	 */
	private Integer seqNum;
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
	 * 单位
	 */
	private Unit unit = null;
	/**
	 * 未转厂数量
	 */
	private Double noTransferQuantity = null;
	/**
	 * 备注
	 */
	private String memo = null; 

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
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

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * @return Returns the noTransferQuantity.
	 */
	public Double getNoTransferQuantity() {
		return noTransferQuantity;
	}

	/**
	 * @param noTransferQuantity
	 *            The noTransferQuantity to set.
	 */
	public void setNoTransferQuantity(Double noTransferQuantity) {
		this.noTransferQuantity = noTransferQuantity;
	}

	// /**
	// * @return Returns the materiel.
	// */
	// public Materiel getMateriel() {
	// return materiel;
	// }
	//
	// /**
	// * @param materiel
	// * The materiel to set.
	// */
	// public void setMateriel(Materiel materiel) {
	// this.materiel = materiel;
	// }

	/**
	 * @return Returns the memo.
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            The memo to set.
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return Returns the transferFactoryInitBill.
	 */
	public TransferFactoryInitBill getTransferFactoryInitBill() {
		return transferFactoryInitBill;
	}

	/**
	 * @param transferFactoryInitBill
	 *            The transferFactoryInitBill to set.
	 */
	public void setTransferFactoryInitBill(
			TransferFactoryInitBill transferFactoryInitBill) {
		this.transferFactoryInitBill = transferFactoryInitBill;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
}