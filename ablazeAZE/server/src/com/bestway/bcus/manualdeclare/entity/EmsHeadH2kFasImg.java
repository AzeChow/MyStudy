/*
 * Created on 2004-7-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;


import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 电子帐册分册料件
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsHeadH2kFasImg extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 电子帐册表头
	 */
	private EmsHeadH2kFas emsHeadH2kFas; 
	/**
	 * 料件序号
	 */
	private Integer seqNum;          
	/**
	 * 商品编码
	 */
	private Complex complex;       
	/**
	 * 商品名称
	 */
	private String name;       
	/**
	 * 规格型号
	 */
	private String spec;         
	/**
	 * 计量单位
	 */
	private Unit unit;          
	/**
	 * 币制
	 */
	private Curr curr;          
	/**
	 * 允许数量
	 */
	private Double allowAmount;      
	/**
	 * 企业申报单价
	 */
	private Double declarePrice;    
	/**
	 * 备注
	 */
	private String note;                
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
	 * @return Returns the allowAmount.
	 */
	public Double getAllowAmount() {
		return allowAmount;
	}
	/**
	 * @param allowAmount The allowAmount to set.
	 */
	public void setAllowAmount(Double allowAmount) {
		this.allowAmount = allowAmount;
	}
	/**
	 * @return Returns the complex.
	 */
	public Complex getComplex() {
		return complex;
	}
	/**
	 * @param complex The complex to set.
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	/**
	 * @return Returns the curr.
	 */
	public Curr getCurr() {
		return curr;
	}
	/**
	 * @param curr The curr to set.
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}
	/**
	 * @return Returns the declarePrice.
	 */
	public Double getDeclarePrice() {
		return declarePrice;
	}
	/**
	 * @param declarePrice The declarePrice to set.
	 */
	public void setDeclarePrice(Double declarePrice) {
		this.declarePrice = declarePrice;
	}
	/**
	 * @return Returns the emsHeadH2kFas.
	 */
	public EmsHeadH2kFas getEmsHeadH2kFas() {
		return emsHeadH2kFas;
	}
	/**
	 * @param emsHeadH2kFas The emsHeadH2kFas to set.
	 */
	public void setEmsHeadH2kFas(EmsHeadH2kFas emsHeadH2kFas) {
		this.emsHeadH2kFas = emsHeadH2kFas;
	}
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
	/**
	 * 返回更改次数（海关申报用，固定为0）
	 */
	public Integer getModifyTimesMark() {
		return 0;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return Returns the spec.
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * @param spec The spec to set.
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * @return Returns the unit.
	 */
	public Unit getUnit() {
		return unit;
	}
	/**
	 * @param unit The unit to set.
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 * @return Returns the seqNum.
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum The seqNum to set.
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}
