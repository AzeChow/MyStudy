/*
 * Created on 2004-9-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.message.action;

import com.bestway.common.Request;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface DzscMessageAction extends CspMessageAction {
	/**
	 * 查询报文收发存放路径设定
	 * 
	 * @return
	 */
	DzscParameterSet findDzscMessageDirSet(Request request);

	DzscParameterSet findDzscMessageDirSet1(Request request);
	
	void checkDzscMessageQueryAuthority(Request request);
	
	void exceptionfilesdeal(Request request, String filename);
}