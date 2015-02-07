package com.bestway.bcus.client.common.tableeditor;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.client.util.JTableListModel;

// DefaultCellEditor , KeyListener,FocusListener
public class JNumberTableCellEditor extends CustomTableCellEditor implements
		TableCellEditor {

	private static final long serialVersionUID = 1L;
	private JFormattedTextField tf;
	private int dataRow = -1;
	private Object editingData = null;
	private List<TableCellEditorListener> autoCalcListeners = new ArrayList<TableCellEditorListener>();
	private List<TableCellEditorListener> persistListeners = new ArrayList<TableCellEditorListener>();
	private TableCellEditorEnableListener enableListener = null;
//	private MouseAdapterExtend mouseAdapterExtend = null;
	private Object oldValue = null;
	private JTable table = null;
	private int digitNum = 999;

	// private JTable table = null;
	public JNumberTableCellEditor() {
		// super(tf);
		this.tf = new JFormattedTextField();
		this.tf.setBorder(null);
		this.tf.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tf, 999);
		// this.setClickCountToStart(1);		
		this.tf.addKeyListener(new java.awt.event.KeyAdapter() {

			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == 10) {// Enter
					// tf.dispatchEvent(new KeyEvent((Container)
					// evt.getSource(),
					// KeyEvent.KEY_PRESSED, 0, 0,
					// KeyEvent.VK_TAB, evt.getKeyChar()));
					// table.dispatchEvent(new KeyEvent((Container)
					// evt.getSource(),
					// KeyEvent.KEY_PRESSED, 0, 0,
					// KeyEvent.VK_TAB, evt.getKeyChar()));
					System.out.println("----------enterNextFocus");
					enterNextFocus(table);
					evt.consume();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							if (tf.getText().trim().equals("")) {
								tf.setText("0");
							}			
							tf.commitEdit();
						} catch (Exception ex) {
						}
					}
				});
			}
		});
		this.tf.addFocusListener(new java.awt.event.FocusAdapter() {

			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						tf.selectAll();

					}

				});
			}
		});
		this.addCellEditorListener(new CellEditorListener() {

			public void editingStopped(ChangeEvent e) {
				// System.out.println("----------enterNextFocus");
//				 enterNextFocus(table);
			}

			public void editingCanceled(ChangeEvent e) {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
			}
		});

		this.tf.addFocusListener(new java.awt.event.FocusAdapter() {

			public void focusGained(java.awt.event.FocusEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						tf.selectAll();
					}
				});
			}
		});
//		mouseAdapterExtend = new MouseAdapterExtend(this.tf);
//		this.tf.addMouseListener(mouseAdapterExtend);
//		this.tf.getDocument().addUndoableEditListener(
//				mouseAdapterExtend.getUndoManager());
	}

	public JNumberTableCellEditor(int digitNum) {
		// super(tf);
		this.tf = new JFormattedTextField();
		this.tf.setBorder(null);
		this.tf.setHorizontalAlignment(SwingConstants.RIGHT);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tf, digitNum);
		this.digitNum = digitNum;
		// this.setClickCountToStart(1);
		this.tf.addKeyListener(new java.awt.event.KeyAdapter() {

			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == 10) {// Enter

					// tf.dispatchEvent(new KeyEvent((Container)
					// evt.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
					// KeyEvent.VK_TAB, evt.getKeyChar()));
					// table.dispatchEvent(new KeyEvent((Container)
					// evt.getSource(), KeyEvent.KEY_PRESSED, 0, 0,
					// KeyEvent.VK_TAB, evt.getKeyChar()));
					System.out.println("----------enterNextFocus");					
					enterNextFocus(table);					
					evt.consume();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						try {
							if (tf.getText().trim().equals("")) {
								tf.setText("0");
							}
							tf.commitEdit();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
			}
		});
		this.tf.addFocusListener(new java.awt.event.FocusAdapter() {

			@Override
			public void focusGained(java.awt.event.FocusEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						tf.selectAll();
					}
				});
			}
		});

//		mouseAdapterExtend = new MouseAdapterExtend(this.tf);
//		this.tf.addMouseListener(mouseAdapterExtend);
//		this.tf.getDocument().addUndoableEditListener(
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
		if (value == null) {
			this.tf.setValue(null);
			setOldVaue(null);
		} else {
			if (digitNum == 0) {
				this.tf.setValue(Double.valueOf(value.toString()).intValue());
				setOldVaue(Double.valueOf(value.toString()).intValue());
			} else {
				this.tf.setValue(Double.valueOf(value.toString()));
				setOldVaue(Double.valueOf(value.toString()));
			}
		}
		if (enableListener != null) {
			this.tf.setEditable(enableListener.isCanEdit(new TableCellEditorParameter(dataRow,editingData)));
		}
		return this.tf;
	}

	public Object getCellEditorValue() {
		if (tf.getValue() == null) {
			return null;
		} else {
			Double value = CustomFormattedTextFieldUtils
					.getFormattedTextFieldValue(tf);
			if (digitNum == 0) {
				return value.intValue();
			} else {
				return value;
			}
		}
	}

	public void addAutoCalcListener(final TableCellEditorListener callBack) {
		if (callBack != null) {
			autoCalcListeners.add(callBack);
		}
	}

	public void addPersistListener(final TableCellEditorListener callBack) {
		if (callBack != null) {
			persistListeners.add(callBack);
		}
	}

	/**
	 * 设置小数位
	 * 
	 */
	public void setEditorDigitNum(int digitNum) {
		this.digitNum = digitNum;
		if (tf != null) {
			CustomFormattedTextFieldUtils
					.setFormatterFactory(this.tf, digitNum);
		}
	}

	@Override
	public boolean stopCellEditing() {
		super.stopCellEditing();
		// System.out.println("----------stopCellEditing");
		Object newValue = this.getCellEditorValue();
		// System.out.println("---oldValue:" + oldValue + "  newValue:" +
		// newValue);
		if (!compareObject(newValue, oldValue)) {
			for (TableCellEditorListener callBack : autoCalcListeners) {
				callBack
						.run(new TableCellEditorParameter(dataRow, editingData));
			}
			for (TableCellEditorListener callBack : persistListeners) {
				callBack
						.run(new TableCellEditorParameter(dataRow, editingData));
			}
			// System.out.println("---oldValue:" + oldValue + "  newValue:" +
			// newValue);
		}
		return true;
	}

	public TableCellEditorEnableListener getEnableListener() {
		return enableListener;
	}

	public void setEnableListener(TableCellEditorEnableListener enableListener) {
		this.enableListener = enableListener;
	}

	private void setOldVaue(Object value) {
		this.oldValue = value;
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
}
