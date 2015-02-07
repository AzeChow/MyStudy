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
 * 存放滚动核销－－帐帐分析－－报关数据分析－－进出口总表
 */
public class EmsBgTotal extends BaseScmEntity implements Cloneable{
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
	private String name = null;
	  
	/**
	 * 型号规格
	 */
	private String spec = null;
	  
	/**
	 * 计量单位
	 */
	private Unit unit = null; 
	  
	/**
	 * 进口数量
	 */
	private Double inNum = null;
	  
	/**
	 * 成品耗用
	 */
	private Double exgWare = null;
	  
	/**
	 * 结余
	 */
	private Double balance = null;
	
	/**
	 * 获取结余
	 * 
	 * @return balance 结余
	 */
	public Double getBalance() {
		return balance;
	}
		
	/**
	 * 设置结余
	 * 
	 * @param balance 结余
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
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
	 * 获取成品耗用
	 * 
	 * @return exgWare 成品耗用
	 */
	public Double getExgWare() {
		return exgWare;
	}
		
	/**
	 * 设置成品耗用
	 * 
	 * @param exgWare 成品耗用
	 */
	public void setExgWare(Double exgWare) {
		this.exgWare = exgWare;
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
}
