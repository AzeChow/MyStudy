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

import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TempEmsEdiHeadH2k extends BaseEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	
	private Boolean      isSelected          = null;
	
	private EmsHeadH2kImg emsHeadH2kImg = null;
	
	private EmsHeadH2kExg emsHeadH2kExg = null;
	
	
	
	

	public EmsHeadH2kExg getEmsHeadH2kExg() {
		return emsHeadH2kExg;
	}

	public void setEmsHeadH2kExg(EmsHeadH2kExg emsHeadH2kExg) {
		this.emsHeadH2kExg = emsHeadH2kExg;
	}

	public EmsHeadH2kImg getEmsHeadH2kImg() {
		return emsHeadH2kImg;
	}

	public void setEmsHeadH2kImg(EmsHeadH2kImg emsHeadH2kImg) {
		this.emsHeadH2kImg = emsHeadH2kImg;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
	
}
