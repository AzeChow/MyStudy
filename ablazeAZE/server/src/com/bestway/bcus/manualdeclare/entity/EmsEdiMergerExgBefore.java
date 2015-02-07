/*
 * Created on 2004-7-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 归并前成品
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */ 
public class EmsEdiMergerExgBefore extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 归并后实体
	 */
	private EmsEdiMergerExgAfter emsEdiMergerExgAfter;  // 归并后实体
	/**
	 * 成品序号
	 */
	private Integer seqNum;   
	/**
	 * 成品货号
	 */
	private String ptNo;      
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
	
	//private Unit legalUnit;      //法定单位
	//private Unit legalUnit2;     //法定单位2
	/**
	 * 最大批准余量
	 */
	private Double maxApprSpace; 
	/**
	 * 币制
	 */
	private Curr curr;     
	/**
	 * 备注
	 */
	private String note;   
	/**
	 * 变更次数
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
	 * 单价
	 */
	private Double price;     
	
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
	 * 内部归并资料
	 */
	private InnerMergeData innerMergerData;
	
	/**
	 * 
	 */
	private Integer maxVersion;
	
	public Integer getMaxVersion() {
		return maxVersion;
	}
	public void setMaxVersion(Integer maxVersion) {
		this.maxVersion = maxVersion;
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
	 * @return Returns the legalUnit.
	 */
	/*public Unit getLegalUnit() {
		return this.getComplex().getFirstUnit();
	}*/
	/**
	 * @param legalUnit The legalUnit to set.
	 *//*
	public void setLegalUnit(Unit legalUnit) {
		this.legalUnit = legalUnit;
	}
	*//**
	 * @return Returns the legalUnit2.
	 */
	/*public Unit getLegalUnit2() {
		return this.getComplex().getSecondUnit();
	}*/
	/**
	 * @param legalUnit2 The legalUnit2 to set.
	 *//*
	public void setLegalUnit2(Unit legalUnit2) {
		this.legalUnit2 = legalUnit2;
	}*/
	/**
	 * @return Returns the maxApprSpace.
	 */
	public Double getMaxApprSpace() {
		return maxApprSpace;
	}
	/**
	 * @param maxApprSpace The maxApprSpace to set.
	 */
	public void setMaxApprSpace(Double maxApprSpace) {
		this.maxApprSpace = maxApprSpace;
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
	 * @return Returns the ptNo.
	 */
	public String getPtNo() {
		return ptNo;
	}
	/**
	 * @param ptNo The ptNo to set.
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
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
	 * @return Returns the emsEdiMergerHead.
	 */
	public EmsEdiMergerHead getEmsEdiMergerHead() {
		return emsEdiMergerExgAfter.getEmsEdiMergerHead();
	}

	/**
	 * @return Returns the emsEdiMergerExgAfter.
	 */
	public EmsEdiMergerExgAfter getEmsEdiMergerExgAfter() {
		return emsEdiMergerExgAfter;
	}
	/**
	 * @param emsEdiMergerExgAfter The emsEdiMergerExgAfter to set.
	 */
	public void setEmsEdiMergerExgAfter(
			EmsEdiMergerExgAfter emsEdiMergerExgAfter) {
		this.emsEdiMergerExgAfter = emsEdiMergerExgAfter;
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
	/**
	 * @return Returns the price.
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	public InnerMergeData getInnerMergerData() {
		return innerMergerData;
	}
	public void setInnerMergerData(InnerMergeData innerMergerData) {
		this.innerMergerData = innerMergerData;
	}
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
	
	public String getNameSpec(){
		String name = (this.getName() == null) ?"":this.getName();
		String spec = (this.getSpec() == null) ?"":this.getSpec();
		return name + "/" + spec;
	}
	
}
