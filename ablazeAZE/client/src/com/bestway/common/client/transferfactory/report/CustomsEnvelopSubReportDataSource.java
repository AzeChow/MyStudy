/*
 * Created on 2004-10-14
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory.report;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;

public class CustomsEnvelopSubReportDataSource extends CustomReportDataSource {

	private static TransferFactoryManageAction transferFactoryManageAction = null;

	private static List list = null;

	private static List subReportData = null;

	private CustomsEnvelopSubReportDataSource(List list) {
		super(list);
	}

	public static CustomsEnvelopSubReportDataSource getInstance(String parentId) {
		if (subReportData == null) {
			return null;
		}
		parentId = parentId.trim().toUpperCase();
		list = new ArrayList();
		for (int i = 0; i < subReportData.size(); i++) {
			CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) subReportData
					.get(i);
			if (c.getCustomsEnvelopBill().getId().trim().toUpperCase().equals(
					parentId)) {
				list.add(c);
			}
		}
		CustomsEnvelopSubReportDataSource.list = list;
		return new CustomsEnvelopSubReportDataSource(list);
	}

	/**
	 * @param subReportData
	 *            The subReportData to set.
	 */
	public static void setSubReportData(List subReportData) {
		CustomsEnvelopSubReportDataSource.subReportData = subReportData;
	}
}