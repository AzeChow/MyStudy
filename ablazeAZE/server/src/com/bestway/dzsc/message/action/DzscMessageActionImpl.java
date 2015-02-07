/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.message.action;

import com.bestway.bcus.message.logic.ExportReceiptCustomLogic;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.message.action.CspMessageActionImpl;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * @author
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
 @AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class DzscMessageActionImpl extends CspMessageActionImpl implements
		DzscMessageAction {
	 private ExportReceiptCustomLogic customBillLogic = null;
	@AuthorityFunctionAnnotation(caption = "参数设定", index = 0)
	public DzscParameterSet findDzscMessageDirSet1(Request request) {
		return (DzscParameterSet) this.getCspMessageDao().findCspParameterSet();
	}
	public DzscParameterSet findDzscMessageDirSet(Request request) {
		return (DzscParameterSet) this.getCspMessageDao().findCspParameterSet();
	}
	
	@AuthorityFunctionAnnotation(caption = "报文查询", index = 12)
	public void checkDzscMessageQueryAuthority(Request request) {
		// TODO Auto-generated method stub
	}
	
	@AuthorityFunctionAnnotation(caption = "报关单--异常回执处理", index = 8)
	public void exceptionfilesdeal(Request request, String filename) {
		this.customBillLogic.exceptionfilesdeal(filename);
	}

}
