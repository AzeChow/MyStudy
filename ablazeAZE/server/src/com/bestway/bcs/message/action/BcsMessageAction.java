/*
 * Created on 2004-9-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.message.action;

import com.bestway.common.Request;
import com.bestway.common.message.action.CspMessageAction;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface BcsMessageAction extends CspMessageAction {

	void checkSystemParameterAuthority(Request request);

	void checkMassageQueryAuthority(Request request);
	
	void exceptionfilesdeal(Request request, String filename);

}