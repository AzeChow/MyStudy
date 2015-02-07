/*
 * Created on 2004-7-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;


import java.util.Date;

import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsBom;

/**
 * 电子帐册成品BOM
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsHeadH2kBom extends BaseEmsBom {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 版本号
	 */
	private EmsHeadH2kVersion emsHeadH2kVersion;  
//	private Integer seqNum;        //料件序号
//	private Complex complex;      //商品编码
//	private String name;          //商品名称
//	private String spec;          //规格型号
//	private Unit unit;            //计量单位
//	private Double unitWear;      //单耗
//	private Double wear;          //损耗
//	private Double price;         //单价
//	private String note;          //备注
	/**
	 * 单耗标志
	 */
	private String cmMark = "1"; 
	/**
	 * 修改次数
	 */
	private Integer modifyTimes;  
    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;   
	
	/**
	 * 变更日期
	 */
	private Date changeDate;    
	/**
	 * 发送状态
	 * NOT_SEND = "0";	未发送
	 * WAIT_SEND = "1";	准备发送
	 * SEND = "2";	已经发送
	 */
	private Integer sendState;   
	/**
	 * @return Returns the modifyMark.
	 */
	public String getModifyMark() {
		return modifyMark;
	}
	/**
	 * @param modifyMark The modifyMark to set.
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
	/**
	 * @return Returns the modifyTimes.
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}
	/**
	 * @param modifyTimes The modifyTimes to set.
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
//	/**
//	 * @return Returns the complex.
//	 */
//	public Complex getComplex() {
//		return complex;
//	}
//	/**
//	 * @param complex The complex to set.
//	 */
//	public void setComplex(Complex complex) {
//		this.complex = complex;
//	}
	/**
	 * @return Returns the emsHeadH2k.
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		if(emsHeadH2kVersion == null)
			return null;
		else if(emsHeadH2kVersion.getEmsHeadH2kExg()==null)
			return null;
		else 
			return emsHeadH2kVersion.getEmsHeadH2kExg().getEmsHeadH2k();
	}

	/**
	 * @return Returns the emsHeadH2kExg.
	 */
	public EmsHeadH2kExg getEmsHeadH2kExg() {
		if(emsHeadH2kVersion == null)
			return null;
		else 
			return emsHeadH2kVersion.getEmsHeadH2kExg();
	}

	/**
	 * @return Returns the emsHeadH2kVersion.
	 */
	public EmsHeadH2kVersion getEmsHeadH2kVersion() {
		return emsHeadH2kVersion;
	}
	/**
	 * @param emsHeadH2kVersion The emsHeadH2kVersion to set.
	 */
	public void setEmsHeadH2kVersion(EmsHeadH2kVersion emsHeadH2kVersion) {
		this.emsHeadH2kVersion = emsHeadH2kVersion;
	}
//	/**
//	 * @return Returns the name.
//	 */
//	public String getName() {
//		return name;
//	}
//	/**
//	 * @param name The name to set.
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}
//	/**
//	 * @return Returns the note.
//	 */
//	public String getNote() {
//		return note;
//	}
//	/**
//	 * @param note The note to set.
//	 */
//	public void setNote(String note) {
//		this.note = note;
//	}
//	/**
//	 * @return Returns the seqNum.
//	 */
//	public String getSeqNum() {
//		return seqNum;
//	}
//	/**
//	 * @param seqNum The seqNum to set.
//	 */
//	public void setSeqNum(String seqNum) {
//		this.seqNum = seqNum;
//	}
//
//	/**
//	 * @return Returns the spec.
//	 */
//	public String getSpec() {
//		return spec;
//	}
//	/**
//	 * @param spec The spec to set.
//	 */
//	public void setSpec(String spec) {
//		this.spec = spec;
//	}
//	/**
//	 * @return Returns the unit.
//	 */
//	public Unit getUnit() {
//		return unit;
//	}
//	/**
//	 * @param unit The unit to set.
//	 */
//	public void setUnit(Unit unit) {
//		this.unit = unit;
//	}
//	/**
//	 * @return Returns the unitWear.
//	 */
//	public Double getUnitWear() {
//		return unitWear;
//	}
//	/**
//	 * @param unitWear The unitWear to set.
//	 */
//	public void setUnitWear(Double unitWear) {
//		this.unitWear = unitWear;
//	}
//	/**
//	 * @return Returns the wear.
//	 */
//	public Double getWear() {
//		return wear;
//	}
//	/**
//	 * @param wear The wear to set.
//	 */
//	public void setWear(Double wear) {
//		this.wear = wear;
//	}
	/**
	 * @return Returns the cmMark.
	 */
	public String getCmMark() {
		return cmMark;
	}
	/**
	 * @return Returns the price.
	 */
//	public Double getPrice() {
//		return price;
//	}
//	/**
//	 * @param price The price to set.
//	 */
//	public void setPrice(Double price) {
//		this.price = price;
//	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public Integer getSendState() {
		return sendState;
	}
	public void setSendState(Integer sendState) {
		this.sendState = sendState;
	}
}
