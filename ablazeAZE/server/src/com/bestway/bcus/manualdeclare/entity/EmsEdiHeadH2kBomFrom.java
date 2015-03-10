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
 * 电子帐册BOM
 * 
 * @author
 *
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiHeadH2kBomFrom extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 成品序号
	 */
	private Integer seqNum;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 电子帐册成品BOM
	 */
	private EmsHeadH2kBom bom;
	/**
	 * 错误信息
	 */
	private String errinfo;
	/**
	 * 单耗
	 */
	private Double unitWear;

	private String isMerger;

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

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public EmsHeadH2kBom getBom() {
		return bom;
	}

	public void setBom(EmsHeadH2kBom bom) {
		this.bom = bom;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Double getUnitWear() {
		return unitWear;
	}

	public void setUnitWear(Double unitWear) {
		this.unitWear = unitWear;
	}

	public Double getUnitWearCy() {
		Double emsUnitWear = bom.getUnitWear() == null ? Double.valueOf(0)
				: bom.getUnitWear();
		Double factoryWear = this.unitWear == null ? Double.valueOf(0)
				: unitWear;
		return emsUnitWear - factoryWear;
	}

	public String getIsMerger() {
		return isMerger;
	}

	public void setIsMerger(String isMerger) {
		this.isMerger = isMerger;
	}
}
