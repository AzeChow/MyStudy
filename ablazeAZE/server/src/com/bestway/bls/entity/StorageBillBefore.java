package com.bestway.bls.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 仓单原始清单（StoragBillBefore） 相当于清单
 * @author  kangbo
 */
public class StorageBillBefore extends BaseScmEntity  {
	/**
	 * 仓单表体信息
	 * @uml.property  name="storageBillAfter"
	 * @uml.associationEnd  
	 */
	private StorageBillAfter storageBillAfter;
	/**
	 * PartNo 企业内部货号(Materiel)
	 * @uml.property  name="partNo"
	 * @uml.associationEnd  
	 */
	private Materiel partNo;
	/**
	 * SubpoenaNo 送货传票(String) 没有则填空
	 * @uml.property  name="subpoenaNo"
	 */
	private String subpoenaNo;
	/**
	 * PartNameE 内部英文名称(String)
	 * @uml.property  name="partNameE"
	 */
	private String partNameE;
	/**
	 * PartNameC 内部中文名称(String)
	 * @uml.property  name="partNameC"
	 */
	private String partNameC;
	/**
	 * Color 颜色(String) 不使用Color用clor，避免于类的冲突
	 * @uml.property  name="clor"
	 */
	private String clor;
	/**
	 * DetailQty 数量(Double)
	 * @uml.property  name="detailQty"
	 */
	private Double detailQty;

	/**
	 * @return
	 * @uml.property  name="storageBillAfter"
	 */
	public StorageBillAfter getStorageBillAfter() {
		return storageBillAfter;
	}

	/**
	 * @param storageBillAfter
	 * @uml.property  name="storageBillAfter"
	 */
	public void setStorageBillAfter(StorageBillAfter storageBillAfter) {
		this.storageBillAfter = storageBillAfter;
	}


	/**
	 * @return
	 * @uml.property  name="subpoenaNo"
	 */
	public String getSubpoenaNo() {
		return subpoenaNo;
	}

	/**
	 * @param subpoenaNo
	 * @uml.property  name="subpoenaNo"
	 */
	public void setSubpoenaNo(String subpoenaNo) {
		this.subpoenaNo = subpoenaNo;
	}

	/**
	 * @return
	 * @uml.property  name="partNameE"
	 */
	public String getPartNameE() {
		return partNameE;
	}

	/**
	 * @param partNameE
	 * @uml.property  name="partNameE"
	 */
	public void setPartNameE(String partNameE) {
		this.partNameE = partNameE;
	}

	/**
	 * @return
	 * @uml.property  name="partNameC"
	 */
	public String getPartNameC() {
		return partNameC;
	}

	/**
	 * @param partNameC
	 * @uml.property  name="partNameC"
	 */
	public void setPartNameC(String partNameC) {
		this.partNameC = partNameC;
	}

	/**
	 * @return
	 * @uml.property  name="clor"
	 */
	public String getClor() {
		return clor;
	}

	/**
	 * @param clor
	 * @uml.property  name="clor"
	 */
	public void setClor(String clor) {
		this.clor = clor;
	}

	/**
	 * @return
	 * @uml.property  name="detailQty"
	 */
	public Double getDetailQty() {
		return detailQty;
	}

	/**
	 * @param detailQty
	 * @uml.property  name="detailQty"
	 */
	public void setDetailQty(Double detailQty) {
		this.detailQty = detailQty;
	}

	/**
	 * @return
	 * @uml.property  name="partNo"
	 */
	public Materiel getPartNo() {
		return partNo;
	}

	/**
	 * @param partNo
	 * @uml.property  name="partNo"
	 */
	public void setPartNo(Materiel partNo) {
		this.partNo = partNo;
	}
}
