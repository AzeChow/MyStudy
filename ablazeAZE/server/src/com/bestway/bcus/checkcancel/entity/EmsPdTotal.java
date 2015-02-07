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
 * 存放滚动核销－－帐帐分析－－盘点数据分析－－分析总表
 */
public class EmsPdTotal extends BaseScmEntity implements Cloneable{
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
	 * 商品编码
	 */
	private String complex = null;
	 
	/**
	 * 商品名称
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
	 * 料件库存数量
	 */
	private Double imgNum = null;
	 
	/**
	 * 成品折料库存
	 */
	private Double exgNum = null;
	/**
     * 已收货未转厂数量
     */
    private Double unTransferNum = null; 
     
    /**
     * 已送货未转厂数量
     */
    private Double unReceiveNum = null;
	/**
	 * 总库存
	 */
	private Double totalNum = null;
	
	/**
	 * 获取商品编码
	 * 
	 * @return complex 商品编码
	 */
	public String getComplex() {
		return complex;
	}
	
	/**
	 * 设置商品编码
	 * 
	 * @param complex 商品编码
	 */
	public void setComplex(String complex) {
		this.complex = complex;
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
	 * 获取成品折料库存
	 * 
	 * @return exgNum 成品折料库存
	 */
	public Double getExgNum() {
		return exgNum;
	}
	
	/**
	 * 设置成品折料库存
	 * 
	 * @param exgNum 成品折料库存
	 */
	public void setExgNum(Double exgNum) {
		this.exgNum = exgNum;
	}
	
	/**
	 * 获取 帐帐分析表头
	 * 
	 * @return head  帐帐分析表头
	 */
	public EmsAnalyHead getHead() {
		return head;
	}
	
	/**
	 * 设置 帐帐分析表头
	 * 
	 * @param head  帐帐分析表头
	 */
	public void setHead(EmsAnalyHead head) {
		this.head = head;
	}
	
	/**
	 * 获取料件库存数量
	 * 
	 * @return imgNum 料件库存数量
	 */
	public Double getImgNum() {
		return imgNum;
	}
	
	/**
	 * 设置料件库存数量
	 * 
	 * @param imgNum 料件库存数量
	 */
	public void setImgNum(Double imgNum) {
		this.imgNum = imgNum;
	}
	
	/**
	 * 获取商品名称
	 * 
	 * @return name 商品名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置商品名称
	 * 
	 * @param name 商品名称
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
	 * 获取总库存
	 * 
	 * @return totalNum 总库存
	 */
	public Double getTotalNum() {
		return totalNum;
	}
	
	/**
	 * 设置总库存
	 * 
	 * @param totalNum 总库存
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
	 * @return the unTransferNum
	 */
	public Double getUnTransferNum() {
		return unTransferNum;
	}

	/**
	 * @param unTransferNum the unTransferNum to set
	 */
	public void setUnTransferNum(Double unTransferNum) {
		this.unTransferNum = unTransferNum;
	}

	/**
	 * @return the unReceiveNum
	 */
	public Double getUnReceiveNum() {
		return unReceiveNum;
	}

	/**
	 * @param unReceiveNum the unReceiveNum to set
	 */
	public void setUnReceiveNum(Double unReceiveNum) {
		this.unReceiveNum = unReceiveNum;
	}
}
