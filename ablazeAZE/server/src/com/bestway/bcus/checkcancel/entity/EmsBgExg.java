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
 *  存放滚动核销－－帐帐分析－－报关数据分析－－成品进出口
 */
public class EmsBgExg extends BaseScmEntity implements Cloneable{
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
	 * 成品名称
	 */
	private String name = null; 
	    
	/**
	 * 成品规格
	 */
	private String spec = null;
	    
	/**
	 * 版本号
	 */
	private String versionNo = null;
	    
	/**
	 * 单位
	 */
	private Unit unit = null;
	    
	/**
	 * 出口数量
	 */
	private Double outNum = null; 
	    
	/**
	 * 转厂数量
	 */
	private Double changeNum = null;
	    
	/**
	 * 退厂返工
	 */
//	private Double exitNum = null;
	private Double backReWork=null;
	 
	/**
	 * 返工复出
	 */
	private Double reWorkBack=null;
	/**
	 * 总出口数量
	 */
	private Double totalNum = null;
	
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
		
	
	
//	/**
//	 * 获取退运数量
//	 * 
//	 * @return exitNum 退运数量
//	 */
//	public Double getExitNum() {
//		return exitNum;
//	}
//		
//	/**
//	 * 设置退运数量
//	 * 
//	 * @param exitNum 退运数量
//	 */
//	public void setExitNum(Double exitNum) {
//		this.exitNum = exitNum;
//	}
	/**
	 * 获取退厂返工	
	 */
	public Double getBackReWork() {
		return backReWork;
	}
    /**
     * 设置退厂返工
     * @param backReWork
     */
	public void setBackReWork(Double backReWork) {
		this.backReWork = backReWork;
	}
    /**
     * 获取返工复出
     * @return
     */
	public Double getReWorkBack() {
		return reWorkBack;
	}
    /**
     * 设置返工复出
     * @param reWorkBack
     */
	public void setReWorkBack(Double reWorkBack) {
		this.reWorkBack = reWorkBack;
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
	 * 获取成品名称
	 * 
	 * @return name 成品名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置成品名称
	 * 
	 * @param name 成品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
		
	/**
	 * 获取出口数量
	 * 
	 * @return outNum 出口数量
	 */
	public Double getOutNum() {
		return outNum;
	}
		
	/**
	 * 设置出口数量
	 * 
	 * @param outNum 出口数量
	 */
	public void setOutNum(Double outNum) {
		this.outNum = outNum;
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
	 * 获取成品规格
	 * 
	 * @return spec 成品规格
	 */
	public String getSpec() {
		return spec;
	}
		
	/**
	 * 设置成品规格
	 * 
	 * @param spec 成品规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
		
	/**
	 * 获取总出口数量
	 * 
	 * @return totalNum 总出口数量
	 */
	public Double getTotalNum() {
		return totalNum;
	}
		
	/**
	 * 设置总出口数量
	 * 
	 * @param totalNum 总出口数量
	 */
	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}
		
	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}
		
	/**
	 * 设置单位
	 * 
	 * @param unit 单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
		
	/**
	 * 获取版本号
	 * 
	 * @return versionNo 版本号
	 */
	public String getVersionNo() {
		return versionNo;
	}
	
	/**
	 * 设置版本号
	 * 
	 * @param versionNo 版本号
	 */
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
}
