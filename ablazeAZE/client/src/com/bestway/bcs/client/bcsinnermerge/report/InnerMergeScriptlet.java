/*
 * Created on 2004-9-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge.report;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InnerMergeScriptlet extends JRDefaultScriptlet{

	/* (non-Javadoc)
	 * @see net.sf.jasperreports.engine.JRAbstractScriptlet#beforePageInit()
	 */
	public void beforePageInit() throws JRScriptletException {
		
		this.setVariableValue("tempAfterTenNo","");		
	}
	/* (non-Javadoc)
	 * @see dori.jasper.engine.JRAbstractScriptlet#afterDetailEval()
	 */
	public void afterDetailEval() throws JRScriptletException {		
		this.setVariableValue("tempAfterTenNo",this.getFieldValue("tenInnerMergeNo"));		
	}	
	
}
