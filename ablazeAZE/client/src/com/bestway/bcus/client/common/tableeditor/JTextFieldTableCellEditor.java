package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;

import com.bestway.client.util.JTableListModel;

public class JTextFieldTableCellEditor extends CustomTableCellEditor implements
		TableCellEditor {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private int dataRow = -1;
	private Object editingData = null;
	private List<TableCellEditorListener> dataChangedListeners = new ArrayList<TableCellEditorListener>();
	private TableCellEditorEnableListener enableListener = null;
	private Object oldValue = null;
	private JTable table = null;

//	private MouseAdapterExtend mouseAdapterExtend = null;

	// private JTable table = null;
	public JTextFieldTableCellEditor() {
		this.textField = new JTextField();
		this.textField.setBorder(null);
		this.textField.addKeyListener(new java.awt.event.KeyAdapter() {

			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == 10) {// Enter
					//
					// textField.dispatchEvent(new KeyEvent((Container)
					// evt.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
					// KeyEvent.VK_TAB, evt.getKeyChar()));
					// table.dispatchEvent(new KeyEvent((Container)
					// evt.getSource(),
					// KeyEvent.KEY_PRESSED, 0, 0,
					// KeyEvent.VK_TAB, evt.getKeyChar()));
					enterNextFocus(table);
					evt.consume();
				}
			}
		});
		this.textField.addFocusListener(new java.awt.event.FocusAdapter() {

			public void focusGained(java.awt.event.FocusEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						textField.requestFocus();
						textField.selectAll();
					}
				});
			}
		});
//		mouseAdapterExtend = new MouseAdapterExtend(this.textField);
//		this.textField.addMouseListener(mouseAdapterExtend);
//		this.textField.getDocument().addUndoableEditListener(
//				mouseAdapterExtend.getUndoManager());
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		this.dataRow = row;
		this.table = table;
//		if (!mouseAdapterExtend.hasExtendMenuItem()) {
//			mouseAdapterExtend.addExtendMenuItem(TableCellExtendMenuItemUtil
//					.getExtendMenuItems((JTableListModel) table.getModel()));
//		}
		if (table.getModel() instanceof JTableListModel) {
			editingData = ((JTableListModel) table.getModel())
					.getDataByRow(row);
		}
		// System.out.println("---------" + value);
		setOldVaue(value);
		if (value == null) {
			this.textField.setText("");
		} else {
			this.textField.setText(value.toString());
		}
		if (enableListener != null) {
			this.textField.setEditable(enableListener
					.isCanEdit(new TableCellEditorParameter(dataRow,
							editingData)));
		}
		return this.textField;
	}

	// @Override
	// public Object getCellEditorValue() {
	// return textField.getText();
	// }

	public void addDataChangedListener(final TableCellEditorListener callBack) {
		if (callBack != null) {
			dataChangedListeners.add(callBack);
		}
	}

	private void setOldVaue(Object value) {
		this.oldValue = value;
	}

	@Override
	public boolean stopCellEditing() {
		super.stopCellEditing();
		Object newValue = this.textField.getText();
		if (!compareObject(oldValue, newValue)) {
			if (dataChangedListeners.size() > 0) {
				for (TableCellEditorListener callBack : dataChangedListeners) {
					callBack.run(new TableCellEditorParameter(dataRow,
							editingData));
					// System.out.println("---oldValue:" + oldValue +
					// "  newValue:" + newValue);
				}
			}
		}
		return true;
	}

	public TableCellEditorEnableListener getEnableListener() {
		return enableListener;
	}

	public void setEnableListener(TableCellEditorEnableListener enableListener) {
		this.enableListener = enableListener;
	}

	private boolean compareObject(Object oldValue, Object newValue) {
		if (oldValue == null && newValue == null) {
			return true;
		} else if (oldValue != null && newValue == null) {
			return false;
		} else if (oldValue == null && newValue != null) {
			return false;
		} else {
			return oldValue.equals(newValue);
		}
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return textField.getText();
	}
}
