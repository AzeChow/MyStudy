/*
 * Created on 2004-8-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclarereport.action;

import java.util.Date;
import java.util.List;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public interface ManualDeclareReportAction {

	public void report1(Request request);

	public void report2(Request request);

	public void report3(Request request);

	public void report4(Request request);

	public void report5(Request request);

	public void report6(Request request);

	public void report7(Request request);

	public void report8(Request request);

	public void report9(Request request);

	public void report10(Request request);

	public void report11(Request request);

	public void report12(Request request);

	public void report13(Request request);

	public void report14(Request request);

	public void report15(Request request);

	public void report16(Request request);

	public void report9_1(Request request);

	public void imreport17(Request request);

	public void exreport18(Request request);

	public void report19(Request request);

	public void report20(Request request);

	// @AuthorityFunctionAnnotation(caption = "大小清单查询", index = 19)
	public void requestTOApplyTOCustomsReport(Request request);

	// @AuthorityFunctionAnnotation(caption = "进/出包装统计", index = 21)
	// public void report21(Request request);

	public void financialImpReport(Request request);

	public void financialExgReport(Request request);

	public void financialTrunReport(Request request);

	/**
	 * 报关单边角料统计
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List statRemainMaterial(Request request, Date beginDate, Date endDate);

	public void ApplyReportTOCustomsReport(Request request);

	public void checkReportQueryPrintAuthority(Request request);

	public void checkFinancialReportQueryAuthority(Request request);

}