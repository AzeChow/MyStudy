/*
 * (swing1.1beta3)
 * 
 */

package com.bestway.client.util.mutispan;

import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.List;

import javax.swing.ListSelectionModel;

import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;

/**
 * @version 1.0 11/22/98
 */

public class AttributiveCellTableModel extends JTableListModel {

	protected CellAttribute cellAtt;

	public AttributiveCellTableModel(MultiSpanCellTable table, List list,
			JTableListModelAdapter adapter) {
		super(table, list, adapter,
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//可以进行排序
//		MouseListener[] mouseListeners = table.getTableHeader()
//				.getMouseListeners();
//		for (int i = 0; i < mouseListeners.length; i++) {
//			if (mouseListeners[i] instanceof SortMouseListener) {
//				table.getTableHeader().removeMouseListener(mouseListeners[i]);
//			}
//		}
//		table.setRowHeight(18);
		cellAtt = new DefaultCellAttribute(list.size(), adapter
				.getColumnCount());
		table.setUI(new MultiSpanCellTableUI());
		table.getTableHeader().setReorderingAllowed(false);
		table.setCellSelectionEnabled(true);		
	}

	public Hashtable getSpanRows(int column) {
		Hashtable ht = getGroupByColumn(column);
		Hashtable htGroup = new Hashtable();

		for (int i = 0; i < (ht.size() / 2); i++) {
			int start = Integer.parseInt(ht.get("start" + i).toString());
			int end = Integer.parseInt(ht.get("end" + i).toString());
			int[] group = new int[end - start + 1];
			int n = 0;
			for (int j = start; j <= end; j++) {
				group[n++] = j;
			}
			htGroup.put(Integer.valueOf(i), group);
		}
		return htGroup;
	}

	public Hashtable getSpanRows(int[] columns) {
		Hashtable ht = getGroupByColumn(columns);
		Hashtable htGroup = new Hashtable();

		for (int i = 0; i < (ht.size() / 2); i++) {
			int start = Integer.parseInt(ht.get("start" + i).toString());
			int end = Integer.parseInt(ht.get("end" + i).toString());
			int[] group = new int[end - start + 1];
			int n = 0;
			for (int j = start; j <= end; j++) {
				group[n++] = j;
			}
			htGroup.put(Integer.valueOf(i), group);
		}
		return htGroup;
	}

	public CellAttribute getCellAttribute() {
		return cellAtt;
	}

	public void setCellAttribute(CellAttribute newCellAtt) {
		int numColumns = getColumnCount();
		int numRows = getRowCount();
		if ((newCellAtt.getSize().width != numColumns)
				|| (newCellAtt.getSize().height != numRows)) {
			newCellAtt.setSize(new Dimension(numRows, numColumns));
		}
		cellAtt = newCellAtt;
		fireTableDataChanged();
	}

	@Override
	public void setList(List list) {
		super.setList(list);		
		cellAtt = new DefaultCellAttribute(list.size(), adapter
				.getColumnCount());
	}
	
	
	

}
