/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放核销表头临时信息
 */
public class CancelTemp extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    /**
	 * 帐册序号
	 */
	private String emsSeqNum;   
		
    /**
	 * 料件名称
	 */
	private String name;     
		
    /**
	 * 规格型号
	 */
	private String spec;     
		
    /**
	 * 数量
	 */
	private double num;        
		
    /**
	 * 价值
	 */
	private double price;    
		
    /**
	 * 重量
	 */
	private double weight;  
	

	/**
	 * 获取帐册序号
	 * 
	 * @return emsSeqNum 帐册序号
	 */
	public String getEmsSeqNum() {
		return emsSeqNum;
	}
		
	/**
	 * 设置帐册序号
	 * 
	 * @param emsSeqNum 帐册序号
	 */
	public void setEmsSeqNum(String emsSeqNum) {
		this.emsSeqNum = emsSeqNum;
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
	 * 获取数量
	 * 
	 * @return num 数量
	 */
	public double getNum() {
		return num;
	}
		
	/**
	 * 设置数量
	 * 
	 * @param num 数量
	 */
	public void setNum(double num) {
		this.num = num;
	}
	
	/**
	 * 获取价值
	 * 
	 * @return price 价值
	 */
	public double getPrice() {
		return price;
	}
		
	/**
	 * 设置价值
	 * 
	 * @param price 价值
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * 获取规格型号
	 * 
	 * @return spec 规格型号
	 */
	public String getSpec() {
		return spec;
	}
		
	/**
	 * 设置规格型号
	 * 
	 * @param spec 规格型号
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	/**
	 * 获取重量
	 * 
	 * @return weight 重量
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * 设置重量
	 * 
	 * @param weight 重量
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
