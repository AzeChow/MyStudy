/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放滚动核销－－帐帐分析－－报关数据分析－－源料进出口
 */
public class EmsBgImg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
	/**
	 * 帐帐分析表头
	 */
	private EmsAnalyHead head = null; 
	   
	/**
	 * 帐册编号
	 */
	private String emsNo = null;
	   
	/**
	 * 备案序号
	 */
	private Integer seqNum = null;
	   
	/**
	 * 料件名称
	 */
	private String  name = null;
	   
	/**
	 * 型号规格
	 */
	private String spec = null;
	   
	/**
	 * 计量单位
	 */
	private Unit unit = null;
	
	
	/**
	 * 期初数
	 */
	private Double initNum=null;
	   
	/**
	 * 进口数量
	 */
	private Double inNum = null;
	   
	/**
	 * 转厂数量
	 */
	private Double changeNum = null;
	
	/**
	 * 余量转入
	 */
	private Double remainInNum = null;
	/**
	 * 余量转出
	 */
	private Double remainOutNum = null;
	   
	/**
	 * 退料数量
	 */
	private Double exitNum = null;
	   
	/**
	 * 总进口数量
	 */
	private Double totalNum = null;
	
	
	
	/**
	 * 获取期初数
	 */
	public Double getInitNum() {
		return initNum;
	}
    /**
     * 设置期初数
     * @param initNum
     */
	public void setInitNum(Double initNum) {
		this.initNum = initNum;
	}

	/**
	 * 获取转厂数量
	 * 
	 * @return changeNum 转厂数量
	 */
	public Double getChangeNum() {
		return changeNum;
	}
		
	/**
	 * 设置转厂数量
	 * 
	 * @param changeNum 转厂数量
	 */
	public void setChangeNum(Double changeNum) {
		this.changeNum = changeNum;
	}
		
	/**
	 * 获取帐册编号
	 * 
	 * @return emsNo 帐册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}
		
	/**
	 * 设置帐册编号
	 * 
	 * @param emsNo 帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
		
	/**
	 * 获取退料数量
	 * 
	 * @return exitNum 退料数量
	 */
	public Double getExitNum() {
		return exitNum;
	}
		
	/**
	 * 设置退料数量
	 * 
	 * @param exitNum 退料数量
	 */
	public void setExitNum(Double exitNum) {
		this.exitNum = exitNum;
	}
		
	/**
	 * 获取帐帐分析表头
	 * 
	 * @return head 帐帐分析表头
	 */
	public EmsAnalyHead getHead() {
		return head;
	}
		
	/**
	 * 设置帐帐分析表头
	 * 
	 * @param head 帐帐分析表头
	 */
	public void setHead(EmsAnalyHead head) {
		this.head = head;
	}
		
	/**
	 * 获取进口数量
	 * 
	 * @return inNum 进口数量
	 */
	public Double getInNum() {
		return inNum;
	}
		
	/**
	 * 设置进口数量
	 * 
	 * @param inNum 进口数量
	 */
	public void setInNum(Double inNum) {
		this.inNum = inNum;
	}
		
	/**
	 * 获取料件名称
	 * 
	 * @return name 料件名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置料件名称
	 * 
	 * @param name 料件名称
	 */
	public void setName(String name) {
		this.name = name;
	}
		
	/**
	 * 获取备案序号
	 * 
	 * @return seqNum 备案序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
		
	/**
	 * 设置备案序号
	 * 
	 * @param seqNum 备案序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
		
	/**
	 * 获取型号规格
	 * 
	 * @return spec 型号规格
	 */
	public String getSpec() {
		return spec;
	}
		
	/**
	 * 设置型号规格
	 * 
	 * @param spec 型号规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
		
	/**
	 * 获取总进口数量
	 * 
	 * @return totalNum 总进口数量
	 */
	public Double getTotalNum() {
		return totalNum;
	}
		
	/**
	 * 设置总进口数量
	 * 
	 * @param totalNum 总进口数量
	 */
	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}
		
	/**
	 * 获取计量单位
	 * 
	 * @return unit 计量单位
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * 设置计量单位
	 * 
	 * @param unit 计量单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 * @return the remainInNum
	 */
	public Double getRemainInNum() {
		return remainInNum;
	}
	/**
	 * @param remainInNum the remainInNum to set
	 */
	public void setRemainInNum(Double remainInNum) {
		this.remainInNum = remainInNum;
	}
	/**
	 * @return the remainOutNum
	 */
	public Double getRemainOutNum() {
		return remainOutNum;
	}
	/**
	 * @param remainOutNum the remainOutNum to set
	 */
	public void setRemainOutNum(Double remainOutNum) {
		this.remainOutNum = remainOutNum;
	}
}
