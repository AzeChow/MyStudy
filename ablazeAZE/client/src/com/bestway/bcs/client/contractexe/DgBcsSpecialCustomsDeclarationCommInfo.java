/*
 * Created on 2004-8-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractexe;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.client.custom.common.DgBaseSpecialCustomsDeclarationCommInfo;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBcsSpecialCustomsDeclarationCommInfo extends DgBaseSpecialCustomsDeclarationCommInfo {
	/**
	 * This is the default constructor
	 */
	public DgBcsSpecialCustomsDeclarationCommInfo() {
		super();
		projectType = ProjectType.BCS;
	}	

	protected BaseCustomsDeclarationCommInfo newCustomsDeclarationCommInfo() {
		return new BcsCustomsDeclarationCommInfo();
	}
	
  } 
