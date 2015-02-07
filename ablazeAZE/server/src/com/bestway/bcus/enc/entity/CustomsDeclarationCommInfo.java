/*
 * Created on 2004-8-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 报关单物料信息
 * 
 * @author Administrator table="customsdeclarationcomminfo"
 */
public class CustomsDeclarationCommInfo extends BaseCustomsDeclarationCommInfo
		implements Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	public int compareTo(Object o) {
		if (o == null) {
			return -1;
		}
		CustomsDeclarationCommInfo ob = (CustomsDeclarationCommInfo) o;
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