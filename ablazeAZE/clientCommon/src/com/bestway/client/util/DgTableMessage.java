package com.bestway.client.util;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgTableMessage extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable table;
	private List<?> list;
	private List<JTableListColumn> columns;

	private DgTableMessage() {
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

	private void initJTable() {
		JTableListModelAdapter jlma = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				return columns;
			}
		};
		new JTableListModel(table, list, jlma);
	}


	public static void show(String title,List<JTableListColumn> columns, List<?> list) {
		DgTableMessage dg = new DgTableMessage();
		if(title != null) {
			dg.setTitle(title);
		}
		
		if(list == null) {
			list = new ArrayList<String>();
		}
		dg.list = list;
		
		if(columns == null) {
			columns = new ArrayList<JTableListColumn>();
			columns.add(new JTableListColumn("料号", "title", 130));
			columns.add(new JTableListColumn("错误信息", "errorMessage", 320));
		}
		dg.columns = columns;
		
		
		dg.initJTable();
		dg.setVisible(true);
	}
}
