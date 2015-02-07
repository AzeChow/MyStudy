/*
 * Created on 2005-6-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.client.custom.common.DgBaseSpecialCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgSpecialCustomsDeclarationCommInfo extends
		DgBaseSpecialCustomsDeclarationCommInfo {

	/**
	 * This is the default constructor
	 */
	public DgSpecialCustomsDeclarationCommInfo() {
		super();
	}	

	protected BaseCustomsDeclarationCommInfo newCustomsDeclarationCommInfo() {
		return new CustomsDeclarationCommInfo();
	}
}
