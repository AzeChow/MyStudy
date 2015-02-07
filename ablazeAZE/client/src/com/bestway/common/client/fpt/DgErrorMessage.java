package com.bestway.common.client.fpt;

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
	private JTableListModelAdapter	jlma = null;
	private List<Object[]> list = new ArrayList<Object[]>();
	
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

	public void setList(List<Object[]> list) {
		this.list = list;
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
	
	public void initTable(List list){
		jlma = new JTableListModelAdapter(){
			@Override
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("栏位","setCoulmn",130));
				list.add(addColumn("错误信息","errorMessage",360));
				return list;
			}
		};
		tableModel = new JTableListModel(table, list, jlma);
	}
	
	
}
