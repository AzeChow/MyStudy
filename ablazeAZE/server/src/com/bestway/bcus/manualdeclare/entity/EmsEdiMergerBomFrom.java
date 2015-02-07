/*
 * Created on 2004-9-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 电子帐册归并关系BOM导入
 * 
 * @author
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiMergerBomFrom extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 是否已备案
	 * 
	 */
	private String isMerger;
	/**
	 * 成品序号
	 */
	private String ptNo;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 企业级版本
	 */
	private String cmpVersion; 
	
	/**
	 * 版本单重
	 */
	private Double unitWeight;
	/**
	 * 归并关系成品BOM
	 */
	private EmsEdiMergerExgBom bom;
	/**
	 * 材料来源
	 */
	private String materialSource;
	/**
	 * 错误信息
	 */
	private String errinfo;

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
	 * @return Returns the bom.
	 */
	public EmsEdiMergerExgBom getBom() {
		return bom;
	}

	/**
	 * @param bom
	 *            The bom to set.
	 */
	public void setBom(EmsEdiMergerExgBom bom) {
		this.bom = bom;
	}

	/**
	 * @return Returns the version.
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            The version to set.
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public String getIsMerger() {
		return isMerger;
	}

	public void setIsMerger(String isMerger) {
		this.isMerger = isMerger;
	}

	public String getMaterialSource() {
		return materialSource;
	}

	public void setMaterialSource(String materialSource) {
		this.materialSource = materialSource;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}
	
	
}
