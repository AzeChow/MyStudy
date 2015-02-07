
/*
 * Created on 2004-9-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage.report;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DzscInnerMergeScriptlet extends JRDefaultScriptlet{

	@Override
	public void beforePageInit() throws JRScriptletException {
		this.setVariableValue("tempAfterTenNo","");
		this.setVariableValue("tempFourNo","");
	}
	
	@Override
	public void afterDetailEval() throws JRScriptletException {
		this.setVariableValue("tempAfterTenNo",this.getFieldValue("tenSeqNum"));
		this.setVariableValue("tempFourNo",this.getFieldValue("fourSeqNum"));
	}
}
