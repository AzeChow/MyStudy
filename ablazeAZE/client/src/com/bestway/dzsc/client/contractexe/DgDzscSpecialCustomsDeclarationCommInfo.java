/*
 * Created on 2004-8-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

import com.bestway.client.custom.common.DgBaseSpecialCustomsDeclarationCommInfo;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscSpecialCustomsDeclarationCommInfo extends DgBaseSpecialCustomsDeclarationCommInfo {
	/**
	 * This is the default constructor
	 */
	public DgDzscSpecialCustomsDeclarationCommInfo() {
		super();
		projectType = ProjectType.DZSC;
	}	

	protected BaseCustomsDeclarationCommInfo newCustomsDeclarationCommInfo() {
		return new DzscCustomsDeclarationCommInfo();
	}
	
  } 
