/*
 * Created on 2004-8-31
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.system.report;
import java.util.List;

import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.system.action.SystemAction;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CompanySubReportDataSource extends
		CustomReportDataSource {
	private static SystemAction systemAction = null;

	private static List list = null;

	/**
	 * @param list
	 */
	private CompanySubReportDataSource(List list) {
		super(list);
	}

}