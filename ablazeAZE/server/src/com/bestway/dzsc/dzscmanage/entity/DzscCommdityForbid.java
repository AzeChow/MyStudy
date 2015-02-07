/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.dzscmanage.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 商品禁用信息
 * @author Administrator
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DzscCommdityForbid extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 手册编号
	 */
	private String emsNo; 
	/**
	 * 手册序号
	 */
	private String seqNum; 
    /**
     * 商品编码
     */
	private String complex;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 型号规格
	 */
    private String spec;
    /**
     * 版本
     */
    private String version;
    /**
     * 单位
     */
	private String unit;
	/**
	 * 禁用日期
	 */
    private Date forbiddate;
    /**
     * 禁用人
     */
    private String forbiduser; 
    /**
     * 恢复日期
     */
    private Date revertdate;
    /**
     * 恢复人
     */
    private String revertuser;
    
    
    public String getComplex() {
        return complex;
    }
    public void setComplex(String complex) {
        this.complex = complex;
    }
    public Date getForbiddate() {
        return forbiddate;
    }
    public void setForbiddate(Date forbiddate) {
        this.forbiddate = forbiddate;
    }
    public String getForbiduser() {
        return forbiduser;
    }
    public void setForbiduser(String forbiduser) {
        this.forbiduser = forbiduser;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getRevertdate() {
        return revertdate;
    }
    public void setRevertdate(Date revertdate) {
        this.revertdate = revertdate;
    }
    public String getRevertuser() {
        return revertuser;
    }
    public void setRevertuser(String revertuser) {
        this.revertuser = revertuser;
    }
    public String getSeqNum() {
        return seqNum;
    }
    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }
    public String getSpec() {
        return spec;
    }
    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
    
    
    
    


}
