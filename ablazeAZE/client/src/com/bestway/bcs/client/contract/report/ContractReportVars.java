/*
 * Created on 2005-5-27
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractReportVars {

	private static ContractReportVars c = null;
	public static String[] strs = null;

	private ContractReportVars() {

	}

	public static ContractReportVars getInstance() {
		if (c == null) {
			c = new ContractReportVars();
		}
		return c;
	}

	/**
	 * 获得成品当前页的金额总和
	 * 
	 * @param groupPage
	 * @param pageRecordCount
	 * @return
	 */

	public Double getTotalPricesByFinishedProductCurrentPage(List list,
			int groupPage, int pageRecordCount) {
		if (list == null) {
			return new Double(0);
		}
		ContractExg contractExg = null;
		double prices = 0;

		for (int i = 0; i < list.size(); i++) {
			if (i >= ((groupPage - 1) * pageRecordCount)
					&& i < (groupPage * pageRecordCount)) {
				contractExg = (ContractExg) list.get(i);
				if (contractExg.getTotalPrice() != null) {
					prices += contractExg.getTotalPrice().doubleValue();
				}
			}
		}
		return new Double(prices);
	}

	/**
	 * 获得料件当前页的金额总和
	 * 
	 * @param groupPage
	 * @param pageRecordCount
	 * @return
	 */

	public Double getTotalPricesByMaterielCurrentPage(List list, int groupPage,
			int pageRecordCount) {
		if (list == null) {
			return new Double(0);
		}
		String key = String.valueOf(groupPage) + pageRecordCount;

		ContractImg contractImg = null;
		double prices = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i >= ((groupPage - 1) * pageRecordCount)
					&& i < (groupPage * pageRecordCount)) {
				contractImg = (ContractImg) list.get(i);
				if (contractImg.getTotalPrice() != null) {
					prices += contractImg.getTotalPrice().doubleValue();
				}
			}
		}
		return new Double(prices);

	}

	public String getObjectByList(List list, int index) {
		if (list.size() > index) {
			return list.get(index).toString();
		} else {
			return "";
		}
	}

	/**
	 * 格式化double的小数位数
	 * 
	 * @param sourceDouble
	 * @param n
	 * @return
	 */
	public static String getDoubleByDigit(double sourceDouble, int n) {
		if (sourceDouble == 0) {
			return "0.0";
		}
		BigDecimal b = new BigDecimal(sourceDouble);
		return ((Double) b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue())
				.toString();
	}
	public static String[] getStrs() {
		return strs;
	}

	public static void setStrs(String[] strs) {
		ContractReportVars.strs = strs;
	}
}
