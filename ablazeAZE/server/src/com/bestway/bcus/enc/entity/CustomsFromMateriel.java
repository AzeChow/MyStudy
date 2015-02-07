/*
 * Created on 2004-8-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;

/**
 * 来自物料主档的报关单
 * @author Administrator 报关单 
 * table="customsfrommateriel"
 */
public class CustomsFromMateriel extends BaseCustomsFromMateriel {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 料件单据
     */
    private EmsHeadH2kImg bcusimgbill = null;
	/**
	 * 成品单据
	 */
	private EmsHeadH2kExg bcusexgbill = null;
	/**
	 *BCUS料件序号
	 */
	private Integer bcusimgNo = null;
	/**
	 * BCUS成品序号
	 */
	private Integer bcusexgNo = null;
	
	
	
	
	public Integer getBcusexgNo() {
		return bcusexgNo;
	}
	public void setBcusexgNo(Integer bcusexgNo) {
		this.bcusexgNo = bcusexgNo;
	}
	public Integer getBcusimgNo() {
		return bcusimgNo;
	}
	public void setBcusimgNo(Integer bcusimgNo) {
		this.bcusimgNo = bcusimgNo;
	}
	public EmsHeadH2kExg getBcusexgbill() {
		return bcusexgbill;
	}
	public void setBcusexgbill(EmsHeadH2kExg bcusexgbill) {
		this.bcusexgbill = bcusexgbill;
	}
	public EmsHeadH2kImg getBcusimgbill() {
		return bcusimgbill;
	}
	public void setBcusimgbill(EmsHeadH2kImg bcusimgbill) {
		this.bcusimgbill = bcusimgbill;
	}
	
	
	
	
	
}