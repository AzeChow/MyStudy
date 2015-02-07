/*
 * Created on 2004-8-7
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.message.action;

import com.bestway.bcus.message.logic.ExportReceiptCustomLogic;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.message.action.CspMessageActionImpl;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class BcsMessageActionImpl extends CspMessageActionImpl implements
		BcsMessageAction {
	private ExportReceiptCustomLogic customBillLogic = null;
	
	@AuthorityFunctionAnnotation(caption = "系统参数设置--浏览", index = 0.0)
	public void checkSystemParameterAuthority(Request request) {
		// TODO Auto-generated method stub

	}
	@AuthorityFunctionAnnotation(caption = "报文查询", index = 17)
	public void checkMassageQueryAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}
	@AuthorityFunctionAnnotation(caption = "异常回执处理", index = 9)
	public void exceptionfilesdeal(Request request, String filename) {
		this.customBillLogic.exceptionfilesdeal(filename);
	}
}
