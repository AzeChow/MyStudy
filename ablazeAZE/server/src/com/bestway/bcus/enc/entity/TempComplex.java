/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;


import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 */
public class TempComplex extends Complex {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 规格
     */
    private String spec = null;
    /**
     * 名称规格
     */
    private String namespec = null;
    
    /**
     * 是否生效
     */
    private boolean effective = false;
	public boolean isEffective() {
		return effective;
	}
	public void setEffective(boolean effective) {
		this.effective = effective;
	}
	/**
	 * 获得名称规格
	 * @return 名称规格
	 */
	public String getNamespec() {
		return namespec;
	}
	/**
	 * 设置名称规格
	 * @param namespec 名称规格
	 */
	public void setNamespec(String namespec) {
		this.namespec = namespec;
	}
	/**
	 * 获得规格
	 * @return 规格
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * 设置规格
	 * @param spec 规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
}