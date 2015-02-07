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
 * 存放滚动核销－－帐帐分析－－盘点数据分析－－成品盘点－－报关资料
 */
public class EmsPdExgBg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
   
    /**
     * 帐帐分析表头
     */
    private EmsAnalyHead head = null;
    
    /**
     * 盘点成品
     */
    private EmsPdExg pdExg=null;
     
    /**
     * 帐册编号
     */
    private String emsNo = null; 
     
    /**
     * 备案序号
     */
    private Integer seqNum = null;
     
    /**
     * 报关商品名称
     */
    private String name = null;
     
    /**
     * 规格
     */
    private String spec = null;
     
    /**
     * 计量单位
     */
    private Unit unit = null;
     
    /**
     * 版本号
     */
    private String versionNo = null;
     
    /**
     * 分配数量
     */
    private Double allotNum = null;  
     
    /**
     * 折算系数
     */
    private Double convertNUm = null; 
     
    /**
     * 总数量
     */
    private Double totalNum = null; 
    
	/**
	 * 获取分配数量
	 * 
	 * @return allotNum 分配数量
	 */
	public Double getAllotNum() {
		return allotNum;
	}
		
	/**
	 * 设置分配数量
	 * 
	 * @param allotNum 分配数量
	 */
	public void setAllotNum(Double allotNum) {
		this.allotNum = allotNum;
	}
	/**
	 * 获取折算系数
	 * 
	 * @return convertNUm 折算系数
	 */
	public Double getConvertNUm() {
		return convertNUm;
	}
		
	/**
	 * 设置折算系数
	 * 
	 * @param convertNUm 折算系数
	 */
	public void setConvertNUm(Double convertNUm) {
		this.convertNUm = convertNUm;
	}
	
	
	/**
	 * 获取盘点成品
	 * 
	 * @return emsNo 盘点成品
	 */
	public EmsPdExg getPdExg() {
		return pdExg;
	}
	/**
	 * 设置盘点成品
	 * 
	 * @param emsNo 盘点成品
	 */
	public void setPdExg(EmsPdExg pdExg) {
		this.pdExg = pdExg;
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
	 * 获取报关商品名称
	 * 
	 * @return name 报关商品名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置报关商品名称
	 * 
	 * @param name 报关商品名称
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
	 * 获取规格
	 * 
	 * @return spec 规格
	 */
	public String getSpec() {
		return spec;
	}
		
	/**
	 * 设置规格
	 * 
	 * @param spec 规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * 获取总数量
	 * 
	 * @return totalNum 总数量
	 */
	public Double getTotalNum() {
		return totalNum;
	}
		
	/**
	 * 设置总数量
	 * 
	 * @param totalNum 总数量
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
}
