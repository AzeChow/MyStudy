package com.bsw.core.client.eport;

import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public abstract class DgErrorMessage extends JDialogBase{
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTable table;
	private JTableListModel tableModel = null;
	public DgErrorMessage() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("错误提示");
		this.setBounds(new Rectangle(500,300));
		getContentPane().add(getPanel(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
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
		tableModel = new JTableListModel(table, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						return getTableColumns();
					}
		});
	}
	
	public abstract List<JTableListColumn> getTableColumns();
	
	/**
	 * 使用示例
	 * @param args
	 */
	public static void main(String[] args) {
		DgErrorMessage dg = new DgErrorMessage() {
			@Override
			public List<JTableListColumn> getTableColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(new JTableListColumn("报关单号", "", 100));
				list.add(new JTableListColumn("导入失败原因", "",300));
				list.add(new JTableListColumn("其他", "",100));
				return list;
			}
		};
		
		List ls = new ArrayList();
		ls.add(new Object[]{"123","合同号001找不到","其他1"});
		ls.add(new Object[]{"456","合同号002找不到","其他2"});
		dg.initTable(ls);
		dg.setVisible(true);
	}
}
