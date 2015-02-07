package com.bestway.bcs.client.verification.factoryanalyse;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgErrorMessage extends JDialogBase {
	private JScrollPane scrollPane;
	private JTable table;
	private JTableListModel tableModel = null;
	private JTableListModelAdapter jlma = null;

	private DgErrorMessage() {
		this.setTitle("错误信息");
		this.setBounds(100, 100, 500, 400);
		initialize();
	}

	private void initialize() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}

	private void initJTable(List<ErrorMessage> list) {
		jlma = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("料号", "title", 130));
				list.add(addColumn("错误信息", "errorMessage", 320));
				return list;
			}
		};
		tableModel = new JTableListModel(table, list, jlma);
	}

	private void initJTable(List list, final String titleCol,
			final String msgCol) {
		jlma = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("料号", titleCol, 130));
				list.add(addColumn("错误信息", msgCol, 320));
				return list;
			}
		};
		tableModel = new JTableListModel(table, list, jlma);
	}

	public static void show(List<ErrorMessage> list) {
		DgErrorMessage dg = new DgErrorMessage();
		dg.initJTable(list);
		dg.setVisible(true);
	}

	public static void show(List list, final String titleCol,
			final String msgCol) {
		DgErrorMessage dg = new DgErrorMessage();
		dg.initJTable(list, titleCol, msgCol);
		dg.setVisible(true);
	}
}
