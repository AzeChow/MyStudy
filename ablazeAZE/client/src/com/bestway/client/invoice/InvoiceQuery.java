/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.invoice;

import java.awt.Component;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.invoice.entity.Invoice;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InvoiceQuery {
	private static InvoiceQuery invoiceQuery = null;

	private static InvoiceAction invoiceAction = null;

	public static InvoiceQuery getInstance() {
		if (invoiceQuery == null) {
			invoiceQuery = new InvoiceQuery();
			invoiceAction = (InvoiceAction) CommonVars.getApplicationContext()
					.getBean("invoiceAction");
		}
		return invoiceQuery;
	}

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public Object findExportCustomsDeclaration(Invoice invoice) {
		List dataSource = invoiceAction.findExportCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), invoice);
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("报关单号码", "customsDeclarationCode", 100));
		list.add(new JTableListColumn("金额", "money", 100));
		list.add(new JTableListColumn("报关单来源", "projectType", 100));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("报关单资料");
		dgCommonQuery.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				JTable table = dgCommonQuery.getJTable();
				table.getColumnModel().getColumn(3).setCellRenderer(
						new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								String str = "";
								if (value != null) {
									Integer projectType = Integer.valueOf(value
											.toString());
									switch (projectType) {
									case ProjectType.BCUS:
										str = "BCUS";
										break;
									case ProjectType.BCS:
										str = "BCS";
										break;
									}
								}
								this.setText(str);
								return this;
							}
						});
			}
		});
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}

	/**
	 * 抓取报关单号为空的发票,根据
	 * 
	 * @param
	 * @return
	 */
	public Object findInvoiceCustomsDeclarationCodeIsNull(
			Date customsDeclarationDate) {
		// List dataSource =invoiceAction
		// .findInvoiceCustomsDeclarationCodeIsNull(new
		// Request(CommonVars.getCurrUser()),
		// customsDeclarationDate);
		List dataSource = invoiceAction
				.findInvoiceCustomsDeclarationCodeIsNull(new Request(CommonVars
						.getCurrUser()));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("版次号", "versionCode", 150));
		list.add(new JTableListColumn("发票号", "invoiceCode", 200));
		DgCommonQuery.setTableColumns(list);
		final DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("发票资料");
		DgCommonQuery.setSingleResult(true);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnValue();
		}
		return null;
	}
}
