/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 *存放中期核查－－自用中期核查计算表－－自定义差异颜色资料
 * 
 * @author Administrator
 */
public class TempDD extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 成品临时序号1
     */
    private String cpseqnum1 = null;
    
    /**
     * 成品临时序号2
     */
    private String cpseqnum = null;
    
    /**
     * 版本号
     */
    private String version = null;
    /**
     * 料件序号
     */
    private String ljseqnum = null;
    
    /**
     * 总计量
     */
    private Double unitUse = null;
    
    /**
     * 损耗
     */
    private Double wear = null;
    
    /**
     * 临时字段aa
     */
    private Double aa = null;
    
    /**
     * 临时字段bb
     */
    private Double bb = null;
    /**
     * 临时字段zchy
     */
    private Double zchy = null;
    /**
     * 临时字段zjckhy
     */
    private Double zjckhy = null;
    /**
     * 临时字段fgfchy
     */
    private Double fgfchy = null;
    /**
     * 临时字段tcfghy
     */
    private Double tcfghy = null;
    
    
	public Double getAa() {
		return aa;
	}
	public void setAa(Double aa) {
		this.aa = aa;
	}
	public Double getBb() {
		return bb;
	}
	public void setBb(Double bb) {
		this.bb = bb;
	}
	public String getCpseqnum() {
		return cpseqnum;
	}
	public void setCpseqnum(String cpseqnum) {
		this.cpseqnum = cpseqnum;
	}
	public String getLjseqnum() {
		return ljseqnum;
	}
	public void setLjseqnum(String ljseqnum) {
		this.ljseqnum = ljseqnum;
	}
	public Double getUnitUse() {
		return unitUse;
	}
	public void setUnitUse(Double unitUse) {
		this.unitUse = unitUse;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Double getWear() {
		return wear;
	}
	public void setWear(Double wear) {
		this.wear = wear;
	}
	public String getCpseqnum1() {
		return cpseqnum1;
	}
	public void setCpseqnum1(String cpseqnum1) {
		this.cpseqnum1 = cpseqnum1;
	}
	public Double getZchy() {
		return zchy;
	}
	public void setZchy(Double zchy) {
		this.zchy = zchy;
	}
	public Double getZjckhy() {
		return zjckhy;
	}
	public void setZjckhy(Double zjckhy) {
		this.zjckhy = zjckhy;
	}
	public Double getFgfchy() {
		return fgfchy;
	}
	public void setFgfchy(Double fgfchy) {
		this.fgfchy = fgfchy;
	}
	public Double getTcfghy() {
		return tcfghy;
	}
	public void setTcfghy(Double tcfghy) {
		this.tcfghy = tcfghy;
	}
	
	
}
