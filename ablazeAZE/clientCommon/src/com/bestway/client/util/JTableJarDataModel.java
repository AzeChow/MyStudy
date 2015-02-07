package com.bestway.client.util;

/**
 * An adaptor, transforming the JDBC interface to the TableModel interface.
 *
 * @version 1.20 09/25/97
 * @author Philip Milne
 */

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class JTableJarDataModel extends JTableListModelBase {
	String[] columnNames = {};
	List<List> rows = new ArrayList<List>();
	JTable table = null;
	int[] columnWidth = null;
	ListSelectionListener[] listSelectionListeners = null;
	String keyString = "hashKeyString";

	public JTableJarDataModel(JTable table, String[] columnNames,
			List<List> rows, String keyString) {
		this.table = table;

		this.columnNames = columnNames;
		this.rows = rows;
		this.keyString = keyString;
		this.initData();
		fireTableDataChanged();
	}

	public JTableJarDataModel(JTable table, String[] columnNames,
			int[] columnWidth, List<List> rows, String keyString) {
		this.columnWidth = columnWidth;
		this.table = table;
		this.columnNames = columnNames;
		this.rows = rows;
		this.keyString = keyString;
		this.initData();
		fireTableDataChanged();
	}

	private void initData() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//
		// 删除
		//
		TableModel old = table.getModel();

		if (old != null && old != this) {

			ListSelectionModel selectModel = table.getSelectionModel();

			if (selectModel instanceof DefaultListSelectionModel) {

				listSelectionListeners = ((DefaultListSelectionModel) selectModel)
						.getListSelectionListeners();

				this.removeTableListSelectionListeners(listSelectionListeners);
			}
		}

		table.setModel(this);

		table.setDoubleBuffered(true);

		new JTableJarContextPopupMenu(this, table);

		if (columnWidth != null) {
			int columnCount = this.getColumnCount(); // 5
			int columnWidthSize = columnWidth.length; // 3
			for (int i = 0; i < columnCount; i++) {
				if (i == 0) {
					table.getColumnModel().getColumn(i).setPreferredWidth(25);
				} else {
					if (i > columnWidthSize) {// 4 > 3 index 超界
						table.getColumnModel().getColumn(i)
								.setPreferredWidth(100);
					} else {
						table.getColumnModel().getColumn(i)
								.setPreferredWidth(columnWidth[i]);
					}
				}
			}
		} else {
			int columnCount = this.getColumnCount(); // 5
			for (int i = 0; i < columnCount; i++) {
				if (i == 0) {
					// table.getColumnModel().getColumn(i).setWidth(25);
					table.getColumnModel().getColumn(i).setPreferredWidth(25);
				} else {
					table.getColumnModel().getColumn(i).setWidth(100);
					table.getColumnModel().getColumn(i).setPreferredWidth(100);
				}
			}
		}
		table.getColumnModel().getColumn(0)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						JTableHeader header = table.getTableHeader();
						if (header != null) {
							setForeground(header.getForeground());
							setBackground(header.getBackground());
							setFont(header.getFont());
						}
						setHorizontalAlignment(JLabel.CENTER);
						return this;
					}
				});
	}

	/**
	 * 去掉老的 不会触发事件
	 * 
	 * @param listSelectionListeners
	 */
	private void removeTableListSelectionListeners(
			ListSelectionListener[] listSelectionListeners) {
		DefaultListSelectionModel defaultListSelectionModel = (DefaultListSelectionModel) this.table
				.getSelectionModel();
		for (ListSelectionListener l : listSelectionListeners) {
			if (l instanceof JTable) {
				continue;
			}
			defaultListSelectionModel.removeListSelectionListener(l);
		}
	}

	// ////////////////////////////////////////////////////////////////////////
	//
	// Implementation of the TableModel Interface
	//
	// ////////////////////////////////////////////////////////////////////////

	public String getColumnName(int column) {
		if (column == 0) {
			return "序号";
		}
		if (columnNames[column - 1] != null) {
			return columnNames[column - 1];
		} else {
			return "";
		}
	}

	public int getColumnCount() {
		return columnNames.length + 1;
	}

	// Data methods
	public int getRowCount() {
		return rows.size();
	}

	public Object getValueAt(int aRow, int aColumn) {
		if (aColumn == 0) {
			return aRow + 1;
		}
		ArrayList row = (ArrayList) rows.get(aRow);
		return row.get(aColumn - 1);
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public List<List> getRows() {
		return rows;
	}

	public List getList() {
		return rows;
	}

	public void setRows(List<List> rows) {
		this.rows = rows;
	}

	@Override
	public Class getTypeByColumn(int col) {
		if (col == 0) {
			return String.class;
		}
		List list = rows.get(0);
		return list.get(col - 1) == null ? String.class : list.get(col - 1)
				.getClass();
	}

	@Override
	public JTable getTable() {
		return this.table;
	}

	@Override
	public int getColumnWidth(int i) {
		return table.getColumnModel().getColumn(i).getPreferredWidth();
	}

	@Override
	public Object getValueAt(Object obj, int col) {
		if (col == 0) {
			return "";
		}
		return ((List) obj).get(col - 1);
	}

	public ListSelectionListener[] getListSelectionListeners() {
		return listSelectionListeners;
	}

	public void setListSelectionListeners(
			ListSelectionListener[] listSelectionListeners) {
		this.listSelectionListeners = listSelectionListeners;
	}
}
