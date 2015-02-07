/*
 * Created on 2004-12-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 内部归并－－反向归并第三层数据资料
 * @author Administrator
 */
public class ReverseMergeFourData extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 料件成品标志
	 * FINISHED_PRODUCT="0";	成品
	 * SEMI_FINISHED_PRODUCT="1";	半成品
	 * MATERIEL="2";	材料--主料
	 * MACHINE="3";	设备
	 * REMAIN_MATERIEL="4";	边角料
	 * BAD_PRODUCT="5";	残次品
	 * ASSISTANT_MATERIAL = "6";	辅料
	 * WASTE_MATERIAL = "7";	消耗料
	 */
	private String imrType; 

	/**
	 * 四位编码序号
	 */
	private Integer hsFourNo;

	/**
	 * 四位商品名称
	 */
	private String hsFourMaterielName; 

	/**
	 * 四位商品编码
	 */
	private String hsFourCode; 

	/**
	 * 获取类型
	 * 
	 * @return imrType 类型
	 */
	public String getImrType() {
		return imrType;
	}

	/**
	 * 设置类型
	 * 
	 * @param imrType 类型
	 */
	public void setImrType(String imrType) {
		this.imrType = imrType;
	}

	/**
	 * 获取四位商品编码
	 * 
	 * @return hsFourCode 四位商品编码
	 */
	public String getHsFourCode() {
		return hsFourCode;
	}

	/**
	 * 设置四位商品编码
	 * 
	 * @param hsFourCode 四位商品编码
	 */
	public void setHsFourCode(String hsFourCode) {
		this.hsFourCode = hsFourCode;
	}

	/**
	 * 获取四位商品名称
	 * 
	 * @return hsFourMaterielName 四位商品名称
	 */
	public String getHsFourMaterielName() {
		return hsFourMaterielName;
	}

	/**
	 * 设置四位商品名称
	 * 
	 * @param hsFourMaterielName 四位商品名称
	 */
	public void setHsFourMaterielName(String hsFourMaterielName) {
		this.hsFourMaterielName = hsFourMaterielName;
	}

	/**
	 * 获取四位编码序号
	 * 
	 * @return hsFourNo 四位编码序号
	 */
	public Integer getHsFourNo() {
		return hsFourNo;
	}

	/**
	 * 设置四位编码序号
	 * 
	 * @param hsFourNo 四位编码序号
	 */
	public void setHsFourNo(Integer hsFourNo) {
		this.hsFourNo = hsFourNo;
	}
}
