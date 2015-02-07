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
public class TempContainerCode extends Complex {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    private String containerCode = null;
    private CustomsDeclaration customs = null;
    
    
    
	public String getContainerCode() {
		return containerCode;
	}
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}
	public CustomsDeclaration getCustoms() {
		return customs;
	}
	public void setCustoms(CustomsDeclaration customs) {
		this.customs = customs;
	}

}