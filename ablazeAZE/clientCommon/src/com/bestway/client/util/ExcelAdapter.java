/*
 * Created on 2005-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.util;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.client.util.mutispan.MultiSpanCellTable;

/**
 * ExcelAdapter 实现 JTables 中的复制粘贴 剪贴板功能。 适配器所用的剪贴板数据格式 与 Excel 所用的剪贴板格式兼容。这提供了
 * 支持的 JTables 和 Excel 间的互操作。
 */
public class ExcelAdapter extends JTableListModelAdapter implements
		ActionListener {
	private String rowstring, value;

	private Clipboard system;

	private StringSelection stsel;

	private JTable jTable1;

	/**
	 * Excel 适配器由 JTable 构成， 它实现了 JTable 上的复制粘贴 功能，并充当剪贴板监听程序。
	 */
	public ExcelAdapter(final JTable myJTable, Integer selectionModel) {
		jTable1 = myJTable;
		KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK, false);

		// 确定复制按键用户可以对其进行修改
		// 以实现其它按键组合的复制功能。
		// KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,
		// ActionEvent.CTRL_MASK, false);

		// 确定粘贴按键用户可以对其进行修改
		// 以实现其它按键组合的复制功能。

		jTable1.registerKeyboardAction(this, "Copy", copy,
				JComponent.WHEN_FOCUSED);

		// jTable1.registerKeyboardAction(this, "Paste", paste,
		// JComponent.WHEN_FOCUSED);

		system = Toolkit.getDefaultToolkit().getSystemClipboard();

		myJTable.setCellSelectionEnabled(true);
		if (myJTable instanceof MultiSpanCellTable) {
			return;
		}
		if (selectionModel != null
				&& selectionModel.intValue() == ListSelectionModel.SINGLE_SELECTION) {
			return;
		}
		myJTable.getColumnModel().addColumnModelListener(
				new TableColumnModelListener() {
					/** Tells listeners that a column was added to the model. */
					public void columnAdded(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was removed from the model. */
					public void columnRemoved(TableColumnModelEvent e) {
					}

					/** Tells listeners that a column was repositioned. */
					public void columnMoved(TableColumnModelEvent e) {
					}

					/**
					 * Tells listeners that a column was moved due to a margin
					 * change.
					 */
					public void columnMarginChanged(ChangeEvent e) {
					}

					public void columnSelectionChanged(ListSelectionEvent e) {

						int[] columns = myJTable.getSelectedColumns();
						int[] rows = myJTable.getSelectedRows();
						if (columns.length < 1 || rows.length < 1) {
							return;
						}

						if (myJTable.getColumnClass(0).equals(
								SerialColumn.class)) {
							myJTable.setColumnSelectionInterval(1, myJTable
									.getColumnCount() - 1);
						} else {
							myJTable.setColumnSelectionInterval(0, myJTable
									.getColumnCount() - 1);
						}

						myJTable.repaint();
					}
				});

	}

	/**
	 * 此适配器运行图表的公共读方法。
	 */
	public JTable getJTable() {
		return jTable1;
	}

	public void setJTable(JTable jTable1) {
		this.jTable1 = jTable1;
	}

	/**
	 * 在我们监听此实现的按键上激活这种方法。 此处，它监听复制和粘贴 ActionCommands。 包含不相邻单元格的选择导致选择无效，
	 * 而且此后复制动作无法执行。 粘贴的方法是将选定内容的左上角与 JTable 的当前选定内容的第一个元素对齐。
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().compareTo("Copy") == 0) {
			this.copy();
			// StringBuffer sbf = new StringBuffer();

			// 检查以确保我们仅选择了单元格的
			// 相邻块
			// int numcols = jTable1.getSelectedColumnCount();
			// int numrows = jTable1.getSelectedRowCount();
			// int[] rowsselected = jTable1.getSelectedRows();
			// int[] colsselected = jTable1.getSelectedColumns();

			// if (!((numrows - 1 == rowsselected[rowsselected.length - 1]
			// - rowsselected[0] && numrows == rowsselected.length) &&
			//
			// (numcols - 1 == colsselected[colsselected.length - 1]
			// - colsselected[0] && numcols == colsselected.length))) {
			// JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
			// "Invalid Copy Selection", JOptionPane.ERROR_MESSAGE);
			// return;
			// }
			// for (int i = 0; i < numrows; i++) {
			// for (int j = 0; j < numcols; j++) {
			//
			// sbf.append(jTable1.getValueAt(rowsselected[i],
			// colsselected[j]));
			// if (j < numcols - 1)
			// sbf.append("\t");
			// }
			// sbf.append("\n");
			// }
			// stsel = new StringSelection(sbf.toString());
			// system = Toolkit.getDefaultToolkit().getSystemClipboard();
			// system.setContents(stsel, stsel);
		}

		if (e.getActionCommand().compareTo("Paste") == 0) {
			System.out.println("Trying to Paste");
			int startRow = (jTable1.getSelectedRows())[0];
			int startCol = (jTable1.getSelectedColumns())[0];
			try {
				String trstring = (String) (system.getContents(this)
						.getTransferData(DataFlavor.stringFlavor));

				System.out.println("String is:" + trstring);
				StringTokenizer st1 = new StringTokenizer(trstring, "\n");
				for (int i = 0; st1.hasMoreTokens(); i++) {
					rowstring = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(rowstring, "\t");

					for (int j = 0; st2.hasMoreTokens(); j++) {
						value = (String) st2.nextToken();
						if (startRow + i < jTable1.getRowCount()
								&& startCol + j < jTable1.getColumnCount())
							jTable1.setValueAt(value, startRow + i, startCol
									+ j);
						System.out.println("Putting " + value + "atrow="
								+ startRow + i + "column=" + startCol + j);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public void copyCurrValues(int x, int y) {
		StringBuffer sbf = new StringBuffer();

		int row = 0;
		int selectStartPointX = 0;
		Hashtable hsx = new Hashtable();
		for (int i = 0; i < jTable1.getColumnCount(); i++) {
			selectStartPointX += jTable1.getColumnModel().getColumn(i)
					.getWidth();
			hsx.put(i, selectStartPointX);
		}
		Enumeration hx = hsx.keys();
		while (hx.hasMoreElements()) {
			Object key = hx.nextElement();
			int x1 = Integer.parseInt(hsx.get(key).toString());
			if (x > x1) {
				row = Integer.parseInt(key.toString()) + 1;
				break;
			}
		}

		int[] rowsselected = jTable1.getSelectedRows();

		if (rowsselected.length < 1) {
			return;
		}

		for (int i = rowsselected[0]; i <= rowsselected[rowsselected.length - 1]; i++) {
			boolean isSelectedRow = false;
			for (int k = 0; k < rowsselected.length; k++) {
				if (i == rowsselected[k]) {
					isSelectedRow = true;
					break;
				}
			}
			if (isSelectedRow == false) {
				continue;
			}
			sbf.append(jTable1.getValueAt(i, row));
			if (i < rowsselected[rowsselected.length - 1]) {
				sbf.append("\n");
			}
		}
		stsel = new StringSelection(sbf.toString());
		system = Toolkit.getDefaultToolkit().getSystemClipboard();
		system.setContents(stsel, stsel);
	}

	public void copy() {
		StringBuffer sbf = new StringBuffer();

		int[] rowsselected = jTable1.getSelectedRows();
		int[] colsselected = jTable1.getSelectedColumns();
		
		Map<Integer,Integer> hideColumnMap = new HashMap<Integer,Integer>();
		for (int j = colsselected[0]; j <= colsselected[colsselected.length - 1]; j++) {
			TableColumn tc = jTable1.getColumnModel().getColumn(j);
			if(tc.getPreferredWidth()<=0){
				hideColumnMap.put(j, j);
			}
		}
		
		if (rowsselected.length < 1 || colsselected.length < 1) {
			return;
		}

		for (int i = rowsselected[0]; i <= rowsselected[rowsselected.length - 1]; i++) {
			boolean isSelectedRow = false;
			for (int k = 0; k < rowsselected.length; k++) {
				if (i == rowsselected[k]) {
					isSelectedRow = true;
					break;
				}
			}
			if (isSelectedRow == false) {
				continue;
			}
			for (int j = colsselected[0]; j <= colsselected[colsselected.length - 1]; j++) {
				TableCellRenderer renderer = jTable1.getCellRenderer(i, j);
				if(hideColumnMap.containsKey(j)){
					continue;
				}
				Component comp = renderer.getTableCellRendererComponent(
						jTable1, jTable1.getValueAt(i, j), false, false, i, j);
				String str = "";
				if(comp  instanceof JLabel){
					str =((JLabel)comp).getText();
				}else if(comp  instanceof JTextField){
					str =((JTextField)comp).getText();
				}else {
					Object obj = jTable1.getValueAt(i, j);
					if (obj == null) {
						str = "";
					} else if (obj.toString().equals("true")) {
						str = "Y";
					} else if (obj.toString().equals("false")) {
						str = "N";
					} else {
						str = jTable1.getValueAt(i, j).toString();
					}
				}
				sbf.append(str);
				if (j < colsselected[colsselected.length - 1]) {
					sbf.append("\t");
				}
			}
			if (i < rowsselected[rowsselected.length - 1]) {
				sbf.append("\n");
			}
		}
		stsel = new StringSelection(sbf.toString());
		system = Toolkit.getDefaultToolkit().getSystemClipboard();
		system.setContents(stsel, stsel);
	}

	/*
	 * public void copyCurrValues(int x,int y) { StringBuffer sbf = new
	 * StringBuffer(); int selectStartPointX = 0; int selectStartPointY = 0;
	 * Hashtable hsx = new Hashtable(); for (int i=0;i<jTable1.getColumnCount();i++){
	 * selectStartPointX += jTable1.getColumnModel().getColumn(i).getWidth();
	 * hsx.put(i,selectStartPointX); System.out.println("f:"+i+"
	 * "+selectStartPointX); }
	 * 
	 * Hashtable hsy = new Hashtable(); for (int i=0;i<jTable1.getRowCount();i++){
	 * selectStartPointY += jTable1.getRowHeight(i);
	 * hsy.put(i,selectStartPointY); System.out.println("i:"+i+"
	 * "+selectStartPointY); } int row = 0; int col = 0;
	 * System.out.println("xxx:"+x); Enumeration hx = hsx.keys(); while
	 * (hx.hasMoreElements()) { Object key = hx.nextElement(); int x1 =
	 * Integer.parseInt(hsx.get(key).toString()); System.out.println("x1:"+x1);
	 * if (x > x1){ row = Integer.parseInt(key.toString())+1;
	 * System.out.println("row:"+row); break; } } System.out.println("y:"+y);
	 * Enumeration hy = hsy.keys(); while (hy.hasMoreElements()) { Object key =
	 * hy.nextElement(); int y1 = Integer.parseInt(hsy.get(key).toString());
	 * System.out.println("y1:"+y1); if (y > y1){ col =
	 * Integer.parseInt(key.toString())+1; break; } }
	 * sbf.append(jTable1.getValueAt(col, row)); stsel = new
	 * StringSelection(sbf.toString()); system =
	 * Toolkit.getDefaultToolkit().getSystemClipboard();
	 * system.setContents(stsel, stsel); }
	 */

	@Override
	public List InitColumns() {
		// TODO Auto-generated method stub
		return null;
	}
}
