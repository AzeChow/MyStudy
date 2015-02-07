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
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 中期核查－－工厂盘点－－料件
 */
public class FactoryCheckImgForBg extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
	 * 参数设定
	 */
    private CheckParameter  head = null;
    /**
     * 序号
     */
    private Integer seqNum = null;
    /**
     * 名称
     */
    private String name = null;   
    /**
     * 规格
     */
    private String spec = null;  
    /**
     * 对应海关单位
     */
    private Unit unit = null;  
    /**
     * 折算报关数量
     */
    private Double bgNum = null;  
    

	/**
	 * 获取参数设定
	 * 
	 * @return head 参数设定
	 */
	public CheckParameter getHead() {
		return head;
	}

	/**
	 * 设置参数设定
	 * 
	 * @param head 参数设定
	 */
	public void setHead(CheckParameter head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getBgNum() {
		return bgNum;
	}

	public void setBgNum(Double bgNum) {
		this.bgNum = bgNum;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}


	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

}
