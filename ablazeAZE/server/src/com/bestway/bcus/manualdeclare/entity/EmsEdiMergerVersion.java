
/*
 * Created on 2004-7-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 归并关系版本
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiMergerVersion extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 归并关系归并前成品表
	 */
	private EmsEdiMergerExgBefore emsEdiMergerBefore; 
	/**
	 * 版本序号(海关版本)
	 */
	private Integer version;  
	/**
	 * 海关版本名称
	 */
	private String name;  
	/**
	 * 企业级版本
	 */
	private String cmpVersion; 
	/**
	 * 修改次数
	 */
	private Integer modifyTimes;
	/**
	 * 修改标志
	 */
	private String modifyMark; 
	
	/**
	 * 实际单重==来源常用工厂BOM实际单重
	 */
	private Double unitWeight;

	
	/**
	 * 获取企业级版本
	 */
	public String getCmpVersion() {
		return cmpVersion;
	}
	/**
	 * 设置企业级版本
	 */
	public void setCmpVersion(String cmpVersion) {
		this.cmpVersion = cmpVersion;
	}
	/**
	 * @return Returns the emsEdiMergerBefore.
	 */
	public EmsEdiMergerExgBefore getEmsEdiMergerBefore() {
		return emsEdiMergerBefore;
	}
	/**
	 * @param emsEdiMergerBefore The emsEdiMergerBefore to set.
	 */
	public void setEmsEdiMergerBefore(EmsEdiMergerExgBefore emsEdiMergerBefore) {
		this.emsEdiMergerBefore = emsEdiMergerBefore;
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
	public Double getUnitWeight() {
		if(unitWeight==null){
			return 0d;
		}
		return unitWeight;
	}
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

}
