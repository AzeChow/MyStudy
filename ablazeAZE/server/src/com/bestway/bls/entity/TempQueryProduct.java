package com.bestway.bls.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;

/**
 * 商品库存信息
 * 
 * @author hw
 * 
 */
public class TempQueryProduct implements Serializable {
	/**
	 * 电子帐册序号
	 */
	private String contrItem = null;

	/**
	 * 商品编码
	 */
	private String codeTS = null;

	/**
	 * 商品名称
	 */
	private String name = null;

	/**
	 * 获取商品名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取商品型号
	 * 
	 * @return
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 设置商品型号
	 * 
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 获取商品单位
	 * 
	 * @return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 商品单位
	 * 
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 商品型号
	 */
	private String model = null;

	/**
	 * 商品单位
	 */
	private String unit = null;


	/**
	 * 库存数量
	 */
	private Double amount = null;

	/**
	 * 获取库存数量
	 * 
	 * @return
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置库存数量
	 * 
	 * @param amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 获取电子帐册序号
	 * 
	 * @return
	 */
	public String getContrItem() {
		return contrItem;
	}

	/**
	 * 设置电子帐册序号
	 * 
	 * @param contrItem
	 */
	public void setContrItem(String contrItem) {
		this.contrItem = contrItem;
	}

	/**
	 * 获取商品编码
	 * 
	 * @return
	 */
	public String getCodeTS() {
		return codeTS;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param codeTS
	 */
	public void setCodeTS(String codeTS) {
		this.codeTS = codeTS;
	}
}
