/*
 * Created on 2004-8-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * 报关单
 * 
 * @author Administrator 报关单 table="customsdeclaration"
 */
@SuppressWarnings("rawtypes")
public class CustomsDeclaration extends BaseCustomsDeclaration implements
		Comparable {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	public int compareTo(Object o) {
		if (o == null) {
			return -1;
		}
		CustomsDeclaration ob = (CustomsDeclaration) o;
		if (ob.getSerialNumber() == null) {
			return -1;
		}
		if (this.getSerialNumber() > ob.getSerialNumber()) {
			return 1;
		} else if (this.getSerialNumber() == ob.getSerialNumber()) {
			return 0;
		} else if (this.getSerialNumber() < ob.getSerialNumber()) {
			return -1;
		}
		return -1;
	}

}