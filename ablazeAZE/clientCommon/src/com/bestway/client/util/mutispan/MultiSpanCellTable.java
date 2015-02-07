/*
 * (swing1.1beta3)
 * 
 */

package com.bestway.client.util.mutispan;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;

import com.bestway.client.util.groupableheader.GroupableHeaderTable;

/**
 * @version 1.0 11/26/98
 */

public class MultiSpanCellTable extends GroupableHeaderTable {

	public MultiSpanCellTable() {
		super();
		this.setDoubleBuffered(true);
		// this.addPropertyChangeListener(new PropertyChangeListener()
		// {
		//
		// public void propertyChange(PropertyChangeEvent evt) {
		// // 
		// MultiSpanCellTable.this.revalidate();
		// MultiSpanCellTable.this.repaint();
		// System.out.println("propertyChange");
		// }
		//			
		// });
	}

	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		Rectangle sRect = super.getCellRect(row, column, includeSpacing);
		if ((row < 0) || (column < 0) || (getRowCount() <= row)
				|| (getColumnCount() <= column)) {
			return sRect;
		}
		CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel())
				.getCellAttribute();
		if (cellAtt == null) {
			return sRect;
		}
		if (!cellAtt.isVisible(row, column)) {
			int temp_row = row;
			int temp_column = column;
			row += cellAtt.getSpan(temp_row, temp_column)[CellSpan.ROW];
			column += cellAtt.getSpan(temp_row, temp_column)[CellSpan.COLUMN];
		}
		int[] n = cellAtt.getSpan(row, column);

		int index = 0;
		// int columnMargin = getColumnModel().getColumnMargin() - 1;
		Rectangle cellFrame = new Rectangle();
		int aCellHeight = rowHeight + rowMargin;
		cellFrame.y = row * aCellHeight;
		cellFrame.height = n[CellSpan.ROW] * aCellHeight;

		Enumeration enumeration = getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			cellFrame.width = aColumn.getWidth();// + columnMargin;
			if (index == column)
				break;
			cellFrame.x += cellFrame.width;
			index++;
		}
		for (int i = 0; i < n[CellSpan.COLUMN] - 1; i++) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			cellFrame.width += aColumn.getWidth();// + columnMargin;
		}

		// if (!includeSpacing) {
		// Dimension spacing = getIntercellSpacing();
		// cellFrame.setBounds(cellFrame.x + spacing.width / 2, cellFrame.y
		// + spacing.height / 2, cellFrame.width - spacing.width,
		// cellFrame.height - spacing.height);
		// }
		return cellFrame;
	}

	private int[] rowColumnAtPoint(Point point) {
		int[] retValue = { -1, -1 };
		int row = point.y / (rowHeight + rowMargin);
		if ((row < 0) || (getRowCount() <= row))
			return retValue;
		int column = getColumnModel().getColumnIndexAtX(point.x);

		CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel())
				.getCellAttribute();
		if (cellAtt != null) {
			if (cellAtt.isVisible(row, column)) {
				retValue[CellSpan.COLUMN] = column;
				retValue[CellSpan.ROW] = row;
				return retValue;
			}
			retValue[CellSpan.COLUMN] = column
					+ cellAtt.getSpan(row, column)[CellSpan.COLUMN];
			retValue[CellSpan.ROW] = row
					+ cellAtt.getSpan(row, column)[CellSpan.ROW];
		}
		return retValue;
	}

	public int rowAtPoint(Point point) {
		return rowColumnAtPoint(point)[CellSpan.ROW];
	}

	public int columnAtPoint(Point point) {
		return rowColumnAtPoint(point)[CellSpan.COLUMN];
	}

	// public void columnSelectionChanged(ListSelectionEvent e) {
	// repaint();
	// }

	// public void valueChanged(ListSelectionEvent e) {
	// int firstIndex = e.getFirstIndex();
	// int lastIndex = e.getLastIndex();
	// if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
	// repaint();
	// }
	// Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
	// int numCoumns = getColumnCount();
	// int index = firstIndex;
	// for (int i = 0; i < numCoumns; i++) {
	// dirtyRegion.add(getCellRect(index, i, false));
	// }
	// index = lastIndex;
	// for (int i = 0; i < numCoumns; i++) {
	// dirtyRegion.add(getCellRect(index, i, false));
	// }
	// repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width,
	// dirtyRegion.height);
	// }

	/*
	 * 初始化CellSpan中的数据,以方便于重绘网格
	 */
	private void initValue() {
		CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) this
				.getModel()).getCellAttribute();
		cellAtt.initValue();
	}

	/*
	 * 合并Table中第@column列,@ht中的行的单元格.
	 */
	private void combineRowsByRow(Hashtable ht, int column) {
		CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) this
				.getModel()).getCellAttribute();
		for (int i = 0; i < ht.size(); i++) {
			int[] rows = (int[]) ht.get(Integer.valueOf(i));
			cellAtt.combine(rows, new int[] { column });
		}
	}

	/*
	 * 合并Table中第@column列,@ht中的行的单元格.
	 */
	private void combineRowsByRow(Hashtable ht, int[] columns) {
		for (int i = 0; i < columns.length; i++) {
			combineRowsByRow(ht, columns[i]);
		}
	}

	private void combineRows(int[] sortRows, int getDataColumn,
			int[] combineCloumns) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();
		ml.sortByColumns(sortRows);
		Hashtable ht = ml.getSpanRows(getDataColumn);
		this.initValue();
		refreshTable(combineCloumns, ht);
	}
	
	public void combineRows(int[] sortRows, int[] getDataColumn,
			int[] combineCloumns) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();
		ml.sortByColumns(sortRows);
		Hashtable ht = ml.getSpanRows(getDataColumn);
		this.initValue();
		refreshTable(combineCloumns, ht);
	}

	private void refreshTable(int[] combineCloumns, Hashtable ht) {
		this.combineRowsByRow(ht, combineCloumns);
		this.revalidate();
		this.repaint();
	}
	
	public void combineRows(int[] combineCloumns) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();		
		Hashtable ht = ml.getSpanRows(combineCloumns);
		refreshTable(combineCloumns, ht);
	} 

	public void combineRows(int getDataColumn, int[] combineCloumns) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();		
		Hashtable ht = ml.getSpanRows(getDataColumn);
		refreshTable(combineCloumns, ht);
	}

	public void combineRows(int[] sortRows, int[] combineCloumns) {
		combineRows(sortRows, sortRows[0], combineCloumns);
	}

	public void combineRows(int sortColumn, int getDataColumn,
			int[] combineCloumns) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();
		ml.sortByColumns(sortColumn);
		Hashtable ht = ml.getSpanRows(getDataColumn);
		this.initValue();
		refreshTable(combineCloumns, ht);
	}

	public void splitRows(int[] sortRows) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();
		ml.sortByColumns(sortRows);
		this.initValue();
		this.revalidate();
		this.repaint();
	}
	
	public void splitRows2(int[] sortRows) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();
		ml.sortByColumns2(sortRows);
		this.initValue();
		this.revalidate();
		this.repaint();
	}
	

	public void splitRows(int sortRow) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();
		ml.sortByColumns(sortRow);
		this.initValue();
		this.revalidate();
		this.repaint();
	}

	/**
	 * 合并列来自indexColumns行数据相匹配的数据都相同的
	 * 
	 * @param indexColumns
	 * @param combineCloumns
	 */
	public void combineRowsByIndeies(int[] indexColumns, int[] combineCloumns) {
		AttributiveCellTableModel ml = (AttributiveCellTableModel) getModel();
		Hashtable ht = ml.getSpanRows(indexColumns);
		refreshTable(combineCloumns, ht);
	}

	/*
	 * 
	 * 重载JTable的getSelectedRows的方法,让其在合并单元格存在的状况下,返回实际的行号
	 * 最重要的用处是在JTableListModel的getCurrentRows()方法中返回正确的选择数据.
	 */
	public int[] getSelectedRows() {
		int[] selectedRows = super.getSelectedRows();
		if (selectedRows.length < 1) {
			return new int[0];
		}
		CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel())
				.getCellAttribute();

		int selectedColumn = 0;
		if (this.getSelectedColumnCount() > 0) {
			selectedColumn = this.getSelectedColumns()[this
					.getSelectedColumnCount() - 1];
		} else {
			return new int[0];
		}
		Hashtable ht = new Hashtable();
		int n = 0;
		for (int i = 0; i < selectedRows.length; i++) {
			int[] span = cellAtt.getSpan(selectedRows[i], selectedColumn);
			if (span[CellSpan.ROW] > 1) {
				for (int j = 0; j < span[CellSpan.ROW]; j++) {
					ht.put(Integer.valueOf(n++), Integer
							.valueOf(selectedRows[i] + j));
				}
			} else if (span[CellSpan.ROW] == 1) {
				ht.put(Integer.valueOf(n++), Integer.valueOf(selectedRows[i]));
			}
		}
		int[] result = new int[ht.size()];
		for (int i = 0; i < ht.size(); i++) {
			result[i] = Integer.parseInt(ht.get(Integer.valueOf(i)).toString());
		}
		return result;
	}
}
