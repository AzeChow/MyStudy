/*
 * Created on 2004-9-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare.report;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsBomScriptlet extends JRDefaultScriptlet{

	//String
	
	/* (non-Javadoc)
	 * @see net.sf.jasperreports.engine.JRAbstractScriptlet#beforePageInit()
	 */
	public void beforePageInit() throws JRScriptletException {		
		this.setVariableValue("tempFlag","");			
	}
	
	
	

	public void afterDetailEval() throws JRScriptletException {		
		String seqNum = this.getFieldValue("pSeqNum")==null?"":(String)this.getFieldValue("pSeqNum");
		String version = this.getFieldValue("version")==null?"":(String)this.getFieldValue("version");
//		System.out.println("beforeDetailEval+version   "+seqNum+version);
		this.setVariableValue("tempFlag",seqNum+version);		
	}	
	
	
}
