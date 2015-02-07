/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * @author Administrator
 *
 */
public class TempCustomsMessage implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    /**
     * 是否选中
     */
    private Boolean           isSelected        = null;
    /**
     * 报关单
     */
    private BaseCustomsDeclaration customsDeclaration = null;
    
    
    
	public BaseCustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}
	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
    
}
