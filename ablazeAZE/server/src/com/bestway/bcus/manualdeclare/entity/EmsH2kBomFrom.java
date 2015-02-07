/*
 * Created on 2004-9-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsH2kBomFrom extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 
	 */
	private Integer version;  
	/**
	 * 电子帐册成品BOM
	 */
	private EmsHeadH2kBom bom;
	
	
	/**
	 * @return Returns the bom.
	 */
	public EmsHeadH2kBom getBom() {
		return bom;
	}
	/**
	 * @param bom The bom to set.
	 */
	public void setBom(EmsHeadH2kBom bom) {
		this.bom = bom;
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
}
