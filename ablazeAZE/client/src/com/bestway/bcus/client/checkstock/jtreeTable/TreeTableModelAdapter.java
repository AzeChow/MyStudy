package com.bestway.bcus.client.checkstock.jtreeTable;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * This is a wrapper class takes a TreeTableModel and implements the table model
 * interface. The implementation is trivial, with all of the event dispatching
 * support provided by the superclass: the AbstractTableModel.
 * 
 * @version %I% %G%
 * 
 * @author Philip Milne
 * @author Scott Violet
 */

public class TreeTableModelAdapter extends AbstractTableModel {
	JTree tree;
	TreeTableModel treeTableModel;

	public TreeTableModelAdapter(TreeTableModel treeTableModel, JTree tree) {
		this.tree = tree;
		this.treeTableModel = treeTableModel;

		tree.addTreeExpansionListener(new TreeExpansionListener() {
			// Don't use fireTableRowsInserted() here;
			// the selection model would get updated twice.
			public void treeExpanded(TreeExpansionEvent event) {
				fireTableDataChanged();
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				fireTableDataChanged();
			}
		});
	}
	
	public TreeTableModel getModel() {
		return treeTableModel;
	}

	// Wrappers, implementing TableModel interface.

	public int getColumnCount() {
		return treeTableModel.getColumnCount();
	}

	public String getColumnName(int column) {
		return treeTableModel.getColumnName(column);
	}

	public Class getColumnClass(int column) {
		return treeTableModel.getColumnClass(column);
	}

	public int getRowCount() {
		return tree.getRowCount();
	}

	protected Object nodeForRow(int row) {
		TreePath treePath = tree.getPathForRow(row);
		return treePath.getLastPathComponent();
	}
	/**
	 * 获取行数据
	 * @param row
	 * @return
	 */
	public <T> T getDataByRow(int row){
		TreePath treePath = tree.getPathForRow(row);
		if(treePath==null){
			return null;
		}
		return (T)((DefaultMutableTreeNode)treePath.getLastPathComponent()).getUserObject();
	}

	public Object getValueAt(int row, int column) {
		return treeTableModel.getValueAt(nodeForRow(row), column);
	}

	public boolean isCellEditable(int row, int column) {
		return treeTableModel.isCellEditable(nodeForRow(row), column);
	}

	public void setValueAt(Object value, int row, int column) {
		treeTableModel.setValueAt(value, nodeForRow(row), column);
	}
}
