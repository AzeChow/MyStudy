/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 临时实体类，存放临时的报关常用工厂物料资料
 * 
 * @author bsway
 */
public class TempMateriel implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 报关常用工厂物料
	 */
	private Materiel materiel;

	/**
	 * 是否备案
	 */
	private Boolean isMemo=false;//是否内总归并备案


	private Integer seqNum = null;
	
	private Boolean  isEmsRecords=false;//是否帐册备案
	
	/**
	 * 归并后的名称
	 */
	private String afterTenName = null;
	
	/**
	 * 归并后的规格
	 * 
	 */
	private String afterSpec = null;
	
	/**
	 * 归并后的单位
	 */
	
	private String afterUnit = null;
	/**
	 * 获取报关常用工厂物料
	 * 
	 * @return materiel 报关常用工厂物料
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置报关常用工厂物料
	 * 
	 * @param materiel
	 *            报关常用工厂物料
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * 获取是否备案
	 * 
	 * @return memo 是否备案
	 */
	public Boolean getIsMemo() {
		return isMemo;
	}

	/**
	 * 设置是否备案
	 * 
	 * @param isMemo
	 *            是否备案
	 */
	public void setIsMemo(Boolean isMemo) {
		this.isMemo = isMemo;
	}

	public Boolean getIsEmsRecords() {
		return isEmsRecords;
	}

	public void setIsEmsRecords(Boolean isEmsRecords) {
		this.isEmsRecords = isEmsRecords;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getAfterTenName() {
		return afterTenName;
	}

	public void setAfterTenName(String afterTenName) {
		this.afterTenName = afterTenName;
	}

	public String getAfterSpec() {
		return afterSpec;
	}

	public void setAfterSpec(String afterSpec) {
		this.afterSpec = afterSpec;
	}

	public String getAfterUnit() {
		return afterUnit;
	}

	public void setAfterUnit(String afterUnit) {
		this.afterUnit = afterUnit;
	}




}
