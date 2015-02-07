/*
 * Created on 2004-7-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 电子帐册归并关系归并后成品
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiMergerExgAfter extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 归并关系表头
	 */
	private EmsEdiMergerHead emsEdiMergerHead;
	/**
	 * 成品序号
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

	///private Unit legalUnit;                   
	/**
	 * 法定单位比例因子
	 */
	private Double legalUnitGene;              
	//private Unit legalUnit2;                    //第二法定单位
	/**
	 * 第二法定单位比例因子
	 */
	private Double legalUnit2Gene;              
	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;             
	/**
	 *最大批准余量
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
	/*public EmsEdiMergerExgAfter(){
		emsEdiMergerBefores = new HashSet();
	}*/
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
	 * @return Returns the emsEdiMergerBefores.
	 */
	/*public Set getEmsEdiMergerBefores() { 
		return emsEdiMergerBefores;
	}
	*//**
	 * @param emsEdiMergerBefores The emsEdiMergerBefores to set.
	 *//*
	public void setEmsEdiMergerBefores(Set emsEdiMergerBefores) {
		this.emsEdiMergerBefores = emsEdiMergerBefores;
	}*/
	/**
	 * @return Returns the emsEdiMergerHead.
	 */
	public EmsEdiMergerHead getEmsEdiMergerHead() {
		return emsEdiMergerHead;
	}
	/**
	 * @param emsEdiMergerHead The emsEdiMergerHead to set.
	 */
	public void setEmsEdiMergerHead(EmsEdiMergerHead emsEdiMergerHead) {
		this.emsEdiMergerHead = emsEdiMergerHead;
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
	 * @return Returns the legalUnit2Gene.
	 */
	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}
	/**
	 * @param legalUnit2Gene The legalUnit2Gene to set.
	 */
	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}
	/**
	 * @return Returns the legalUnitGene.
	 */
	public Double getLegalUnitGene() {
		return legalUnitGene;
	}
	/**
	 * @param legalUnitGene The legalUnitGene to set.
	 */
	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}
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
	 * @return Returns the weigthUnitGene.
	 */
	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}
	/**
	 * @param weigthUnitGene The weigthUnitGene to set.
	 */
	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
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
