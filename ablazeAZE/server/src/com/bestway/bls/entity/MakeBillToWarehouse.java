package com.bestway.bls.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.materialbase.entity.Materiel;
/**
 * 存放仓单头和表体的中间实体类
 * @author hw
 *
 */
public class MakeBillToWarehouse implements Serializable{
	/**
	 *  出入仓单表头
	 */
	private StorageBill sb;

	/**
	 * 仓单表体信息
	 */
	private StorageBillAfter storageBillAfter;
	
	/**
	 * PartNo 企业内部货号(Materiel)
	 * 
	 *  
	 */
	private Materiel partNo;
	
	/**
	 * SubpoenaNo 送货传票(String) 没有则填空
	 * 
	 */
	private String subpoenaNo;
	
	/**
	 * PartNameE 内部英文名称(String)
	 * 
	 */
	private String partNameE;
	
	/**
	 * PartNameC 内部中文名称(String)
	 * 
	 */
	private String partNameC;
	
	/**
	 * Color 颜色(String) 不使用Color用clor，避免于类的冲突
	 * 
	 */
	private String clor;
	
	/**
	 * DetailQty 数量(Double)
	 *
	 */
	private Double detailQty;

	/**
	 * 进出口岸
	 */
	private Customs customs;
	
	/**
	 * 仓单类型(String) （00-申报初始库存，01-后报关方式 02-先报关分批送货方式03-特殊审核）
	 * 
	 */
	private String billType;
	
	/**
	 * 帐册编号(String) 电子账册编号或底账编号
	 * 
	 *
	 */
	private String emsNo;
	
	/**
	 * WrapType 包装种类，按海关代码表(wrap) 来自关务基础资料包装种类表
	 * 
	 */
	private Wrap wrap;
	
	/**
	 * 海关代码(Customs) 申报主管海关编码
	 *
	 */
	private Customs customsCode;
	
	
	
	/**
	 * 得到申报主管海关编码
	 * @return 申报主管海关编码
	 */
	public Customs getCustomsCode() {
		return customsCode;
	}
	
    /**
     * 设置申报主管海关编码 
     * @param customsCode
     */
	public void setCustomsCode(Customs customsCode) {
		this.customsCode = customsCode;
	}

	/**
	 * 得到包装类型
	 * @return 包装类型
	 */
	public Wrap getWrap() {
		return wrap;
	}

	/**
	 * 设置包装类型
	 * @param wrap
	 */
	public void setWrap(Wrap wrap) {
		this.wrap = wrap;
	}

	/**
	 * 得到账册编号
	 * @return 账册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}
	
	/**
	 * 设置帐册编号
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * 得到仓单类型
	 * @return 仓单类型
	 */
	public String getBillType() {
		return billType;
	}
	
	/**
	 * 设置仓单类型
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	/**
	 * 得到进出口岸
	 * @return 进出口岸
	 */
	public Customs getCustoms() {
		return customs;
	}
	
    /**
     * 设置进出口岸
     * @param customs
     */
	public void setCustoms(Customs customs) {
		this.customs = customs;
	}

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
	 * 得到 企业内部货号(Materiel)
	 * 
	 */
	public Materiel getPartNo() {
		return partNo;
	}

	/**
	 * 设置 企业内部货号(Materiel)
	 * 
	 */
	public void setPartNo(Materiel partNo) {
		this.partNo = partNo;
	}
	
	/**
	 * 得到出入仓单表头
	 * @return
	 */
	public StorageBill getSb() {
		return sb;
	}
	
    /**
     * 设置出入仓单表头
     * @param sb
     */
	public void setSb(StorageBill sb) {
		this.sb = sb;
	}
}
