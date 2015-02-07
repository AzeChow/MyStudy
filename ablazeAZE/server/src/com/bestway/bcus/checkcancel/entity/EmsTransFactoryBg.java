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
 * 存放滚动核销－－帐帐分析－－转厂数据分析－－料件转厂－－报关料件
 */
public class EmsTransFactoryBg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
   
    /**
     * 帐帐分析表头
     */
    private EmsAnalyHead head = null;
    
    /**
     * 转厂料件
     */
    private EmsTransFactory emsTransFactory=null;
    
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
     * 报关商品规格
     */
    private String spec = null; 
     
    /**
     * 计量单位
     */
    private Unit unit = null; 
    /**
     * 已收货未转厂数量
     */
    private Double unTransferNum = null; 
     
    /**
     * 已送货未转厂数量
     */
    private Double unReceiveNum = null;
    /**
     * 折算系数
     */
    private Double convertRate = null;
	
    /**
     * 获取折算系数
     * 
	 * @return convertRate 折算系数
	 */
	public Double getConvertRate() {
		return convertRate;
	}
	
	/**
	 * 设置折算系数
	 * 
	 * @param convertRate 折算系数
	 */
	public void setConvertRate(Double convertRate) {
		this.convertRate = convertRate;
	}
	
	
	/**
     * 获取转厂料件
     * 
	 * @return emsNo 转厂料件
	 */
    public EmsTransFactory getemsTransFactory() {
		return emsTransFactory;
	}

    /**
	 * 设置转厂料件
	 * 
	 * @param emsNo 转厂料件
	 */
	public void setemsTransFactory(EmsTransFactory emsTransFactory) {
		this.emsTransFactory = emsTransFactory;
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
	
//    /**
//     * 获取非保税数量
//     * 
//	 * @return notProNum 非保税数量
//	 */
//	public Double getNotProNum() {
//		return notProNum;
//	}
//	
//	/**
//	 * 设置非保税数量
//	 * 
//	 * @param notProNum 非保税数量
//	 */
//	public void setNotProNum(Double notProNum) {
//		this.notProNum = notProNum;
//	}
//	
//    /**
//     * 获取保税数量
//     * 
//	 * @return proNum 保税数量
//	 */
//	public Double getProNum() {
//		return proNum;
//	}
//	
//	/**
//	 * 设置保税数量
//	 * 
//	 * @param proNum 保税数量
//	 */
//	public void setProNum(Double proNum) {
//		this.proNum = proNum;
//	}
	
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
     * 获取报关商品规格
     * 
	 * @return spec 报关商品规格
	 */
	public String getSpec() {
		return spec;
	}
	
	/**
	 * 设置报关商品规格
	 * 
	 * @param spec 报关商品规格
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
