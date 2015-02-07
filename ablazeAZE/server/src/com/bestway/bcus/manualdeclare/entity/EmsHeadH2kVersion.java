/*
 * Created on 2004-7-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 电子帐册历版本
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsHeadH2kVersion extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 电子帐册成品表
	 */
	private EmsHeadH2kExg emsHeadH2kExg; 
	/**
	 * 版本序号
	 */
	private Integer version;           
	/**
	 * 版本名称
	 */
	private String name;                
	/**
	 * 修改次数
	 */
	private Integer modifyTimes;         
	/**
	 * 修改标志
	 */
	private String modifyMark;           
	/**
	 * 是否禁止
	 */
	private Boolean isForbid;            
	/**
	 * 单位毛重
	 */
	private Double unitGrossWeight; 
	/**
	 * 单位净重
	 */
	private Double unitNetWeight;   
	/**
	 * 工厂单价
	 */
	private Double factoryPrice;   
    /**
     * 开始有效期
     */
	private Date beginDate;         
    
	/**
	 * 结束有效期
	 */
	private Date endDate;          
    
	/**
	 * @return Returns the emsHeadH2k.
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		if(emsHeadH2kExg == null){
			return null;
		}
		else {
			return emsHeadH2kExg.getEmsHeadH2k();
		}
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the version.
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * @param version The version to set.
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return Returns the emsHeadH2kExg.
	 */
	public EmsHeadH2kExg getEmsHeadH2kExg() {
		return emsHeadH2kExg;
	}
	/**
	 * @param emsHeadH2kExg The emsHeadH2kExg to set.
	 */
	public void setEmsHeadH2kExg(EmsHeadH2kExg emsHeadH2kExg) {
		this.emsHeadH2kExg = emsHeadH2kExg;
	}
	/**
	 * @return Returns the modifyMark.
	 */
	public String getModifyMark() {
		return modifyMark;
	}
	/**
	 * @param modifyMark The modifyMark to set.
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
	/**
	 * @return Returns the modifyTimes.
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}
	/**
	 * @param modifyTimes The modifyTimes to set.
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
	/**
	 * @return Returns the isForbid.
	 */
	public Boolean getIsForbid() {
		return isForbid;
	}
	/**
	 * @param isForbid The isForbid to set.
	 */
	public void setIsForbid(Boolean isForbid) {
		this.isForbid = isForbid;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(Double factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public Double getUnitGrossWeight() {
		return unitGrossWeight;
	}

	public void setUnitGrossWeight(Double unitGrossWeight) {
		this.unitGrossWeight = unitGrossWeight;
	}

	public Double getUnitNetWeight() {
		return unitNetWeight;
	}

	public void setUnitNetWeight(Double unitNetWeight) {
		this.unitNetWeight = unitNetWeight;
	}
}
