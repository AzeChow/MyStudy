package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DgErrorMessage extends JDialogBase {
	private JScrollPane scrollPane;
	private JTable table;
	private JTableListModel tableModel = null;
	private JTableListModelAdapter jlma = null;
	private List<ErrorMessage> list = new ArrayList<ErrorMessage>();

	public DgErrorMessage() {
		this.setTitle("错误信息");
		this.setBounds(100, 100, 574, 400);
		initialize();
	}

	private void initialize() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getScrollPane(), BorderLayout.CENTER);

		initTable(Collections.EMPTY_LIST);
	}

	public void setList(String[] stu) {
		for (int i = 0; i < stu.length; i++) {
			this.list.add(new ErrorMessage(stu[i], "在物料与报关对应表中不存在或没有做归并！"));
		}
	}
	public void setList(List<String> list,String errorMessage) {
		if(list==null)
			return;
		for (int i = 0; i < list.size(); i++) {
			this.list.add(new ErrorMessage(list.get(i), errorMessage));
		}
		initTables(this.list);
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

	public void initTable(List list) {
		jlma = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("序号", "ptNo", 130));
				list.add(addColumn("错误信息", "errorMessage", 360));
				return list;
			}
		};
		tableModel = new JTableListModel(table, list, jlma);
	}
	
	public void initTables(List list) {
		jlma = new JTableListModelAdapter() {
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("序号", "ptNo", 360));
				list.add(addColumn("错误信息", "errorMessage", 130));
				return list;
			}
		};
		tableModel = new JTableListModel(table, list, jlma);
	}

	public class ErrorMessage {
		private String ptNo;
		private String errorMessage;

		public ErrorMessage(String ptNo, String errorMessage) {
			this.errorMessage = errorMessage;
			this.ptNo = ptNo;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getPtNo() {
			return ptNo;
		}

		public void setPtNo(String ptNo) {
			this.ptNo = ptNo;
		}
	}
}
