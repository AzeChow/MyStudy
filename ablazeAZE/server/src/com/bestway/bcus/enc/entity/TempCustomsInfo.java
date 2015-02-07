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
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author Administrator
 *
 */
public class TempCustomsInfo implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    private BaseCustomsDeclarationCommInfo info = null;
    private Integer customsPiece = null;
	public Integer getCustomsPiece() {
		return customsPiece;
	}
	public void setCustomsPiece(Integer customsPiece) {
		this.customsPiece = customsPiece;
	}
	public BaseCustomsDeclarationCommInfo getInfo() {
		return info;
	}
	public void setInfo(BaseCustomsDeclarationCommInfo info) {
		this.info = info;
	}
}
