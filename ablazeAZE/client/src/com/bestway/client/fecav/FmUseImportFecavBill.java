package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;
import com.bestway.fecav.entity.TempCustomsDeclarationInfoForFecav;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmUseImportFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane spOuter = null;

	private JTable tbCancel = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTableListModel cancelTableModel = null;

	private JTableListModel usedTableModel = null;

	private JTableListModel canStrikTableModel = null;

	private FecavAction fecavAction = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnCancel = null;

	private JPanel pnCanStrik = null;

	private JPanel pnUsed = null;

	private JScrollPane spInner = null;

	private JScrollPane spIerr = null;

	private JTable tbUsed = null;

	private JButton btnEdit = null;

	private JButton btnSearch = null;

	private JButton btnAdd = null;

	private JLabel lbCountthr = null;

	private JLabel lbCounttsec = null;

	private JLabel lbCountfirst = null;

	private JButton btnSave = null;

	private JButton btnUpdate = null;

	private JButton btnDelete = null;

	private JTable tbCanStrik = null;

	private JButton btnWhiteBill = null;

	private JButton btnCanStrik = null;

	private JButton btnCanNotStrik = null;

	private JButton btnUpdateRemainMoney = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmUseImportFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(553, 365));
		this.setContentPane(getJContentPane());
		this.setTitle("进口白单管制");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						// if (jTabbedPane.getSelectedIndex() == 0) {
						// List list = fecavAction
						// .findImpCustomsDeclarationNotCancel(
						// new Request(CommonVars
						// .getCurrUser()), "",
						// new ArrayList());
						// initUsedTable(list);
						// showCount(list);
						// } else if (jTabbedPane.getSelectedIndex() == 1) {
						// List list = fecavAction
						// .findImpCustomsDeclarationIsCancel(
						// new Request(CommonVars
						// .getCurrUser()), "",
						// new ArrayList());
						// initCancelTable(list);
						// showCount(list);
						//
						// }
						refresh();
						setState();
					}
				});

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnUpdate());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnWhiteBill());
			jToolBar.add(getBtnCanNotStrik());
			jToolBar.add(getBtnCanStrik());
			jToolBar.add(getBtnUpdateRemainMoney());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpOuter() {
		if (spOuter == null) {
			spOuter = new JScrollPane();
			spOuter.setViewportView(getTbUnused());
		}
		return spOuter;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUnused() {
		if (tbCancel == null) {
			tbCancel = new JTable();
		}
		return tbCancel;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					refresh();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmUseImportFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initCancelTable(List list) {
		JTableListModel.dataBind(tbCancel, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("进口报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("申报日期", "declareDate", 100));
				list.add(addColumn("白单号", "whiteBillNo", 100));
				list.add(addColumn("签收编码", "fecavBillStrike.signInNo", 100));
				list.add(addColumn("合同号", "contractNo", 100));
				list.add(addColumn("手册编号 ", "emsNo", 200));
				list.add(addColumn("币制 ", "curr.name", 100));
				list.add(addColumn("报关单总金额", "totalMoney", 100));
				list.add(addColumn("冲销金额", "strikeMoney", 200));
				// list.add(addColumn("剩余金额", "remainMoney", 200));
				return list;
			}
		});
		cancelTableModel = (JTableListModel) tbCancel.getModel();
	}

	private void initUsedTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("进口报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("申报日期", "declareDate", 100));
				list.add(addColumn("白单号", "whiteBillNo", 200));
				// list.add(addColumn("签收编码", "signInNo", 100));
				list.add(addColumn("合同号", "contractNo", 100));
				list.add(addColumn("贸易方式", "tradeMode", 100));
				list.add(addColumn("手册编号 ", "emsNo", 200));
				list.add(addColumn("币制 ", "curr.name", 100));
				list.add(addColumn("报关单总金额", "totalMoney", 100));
				list.add(addColumn("冲销金额", "strikeMoney", 200));
				list.add(addColumn("剩余金额", "remainMoney", 200));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(3);
		usedTableModel = new JTableListModel(tbUsed, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		usedTableModel.setAllowSetValue(true);
		tbUsed.getColumnModel().getColumn(3).setCellEditor(
				new JTextFieldEditor(new JTextField()));
		tbUsed.getColumnModel().getColumn(3).setCellRenderer(
				new ForcedEditTableCellRenderer());
		tbUsed.setRowHeight(20);
		setRowColorDeclareDateOver30Day(tbUsed, 3);
	}

	private void initCanStrikTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("可核销", "canStrike", 50));
				list.add(addColumn("剩余金额", "remainMoney", 80));
				list.add(addColumn("进口报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("申报日期", "declareDate", 100));
				list.add(addColumn("白单号", "whiteBillNo", 200));
				list.add(addColumn("签收编码", "signInNo", 100));
				list.add(addColumn("合同号", "contractNo", 100));
				list.add(addColumn("贸易方式", "tradeMode", 100));
				list.add(addColumn("手册编号 ", "emsNo", 200));
				list.add(addColumn("币制 ", "curr.name", 100));
				list.add(addColumn("报关单总金额", "totalMoney", 100));
				list.add(addColumn("冲销金额", "strikeMoney", 200));
				return list;
			}
		};
		canStrikTableModel = new JTableListModel(tbCanStrik, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		TableColumn column = this.tbCanStrik.getColumnModel().getColumn(1);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				boolean state = false;
				if (value != null) {
					state = Boolean.parseBoolean(value.toString());
				}
				if (state) {
					this.setText("是");
					this.setForeground(Color.BLACK);
				} else {
					this.setText("否");
					this.setForeground(Color.RED);
				}
				return this;
			}
		});
		tbCanStrik.setRowHeight(20);
		setRowColorDeclareDateOver30Day(tbCanStrik, 1);
	}

	private void showCount(List list) {
		Double total = 0.0;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof ImpCustomsDeclaration) {
					ImpCustomsDeclaration impCustomsDeclaration = (ImpCustomsDeclaration) list
							.get(i);
					total += (impCustomsDeclaration.getTotalMoney() == null ? 0.0
							: impCustomsDeclaration.getTotalMoney());
				} else if (list.get(i) instanceof StrikeImpCustomsDeclaration) {
					StrikeImpCustomsDeclaration strikeImpCustomsDeclaration = (StrikeImpCustomsDeclaration) list
							.get(i);
					total += (strikeImpCustomsDeclaration.getStrikeMoney() == null ? 0.0
							: strikeImpCustomsDeclaration.getStrikeMoney());
				}
			}
		}
		if (jTabbedPane.getSelectedIndex() == 0) {
			this.lbCountfirst.setText(" 总份数: " + list.size()
					+ "           统计金额："
					+ CommonVars.formatDoubleToString(total, 999, 6));

		} else if (jTabbedPane.getSelectedIndex() == 1) {
			this.lbCounttsec.setText(" 总份数: " + list.size()
					+ "           统计金额："
					+ CommonVars.formatDoubleToString(total, 999, 6));

		} else if (jTabbedPane.getSelectedIndex() == 2) {
			this.lbCountthr.setText(" 总份数: " + list.size() + "           统计金额："
					+ CommonVars.formatDoubleToString(total, 999, 6));

		}
	}

	/**
	 * 编辑列
	 */
	class JTextFieldEditor extends DefaultCellEditor implements KeyListener,
			FocusListener {
		private JTextField tf;

		private JTable table = null;

		private String editBeforeValue = "";

		public JTextFieldEditor(JTextField tf) {
			super(tf);
			this.tf = tf;
			// this.tf.setBorder(null);
			this.setClickCountToStart(1);
			this.tf.addKeyListener(this);
			this.tf.addFocusListener(this);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value != null) {
				tf.setText((String) value);
			}
			this.table = table;
			// tf.addKeyListener(this);
			return tf;
		}

		public Object getCellEditorValue() {
			// tf.removeKeyListener(this);
			// System.out.println("---------- getCellEditorValue");
			// if(tf.getText().length()>0&&tf.getText().length()!=9){
			// tf.setText("");
			// JTableListModel tableModel=(JTableListModel)table.getModel();
			// tableModel.setTableSelectedRow(tableModel.getCurrentRow());
			// }
			return tf.getText();
		}

		public void focusGained(FocusEvent e) {
			JTableListModel tableModel = (JTableListModel) table.getModel();
			ImpCustomsDeclaration icd = (ImpCustomsDeclaration) tableModel
					.getCurrentRow();
			tf.setText(icd.getWhiteBillNo());
			editBeforeValue = tf.getText();
			tf.selectAll();
		}

		// public boolean saveImpCustomsDeclaration() {
		// JTableListModel tableModel = (JTableListModel) this.table
		// .getModel();
		// Object obj = tableModel.getCurrentRow();
		// if (obj instanceof ImpCustomsDeclaration) {
		// ImpCustomsDeclaration temp = (ImpCustomsDeclaration) obj;
		// if (tf.getText().trim().equals("")) {
		// temp.setWhiteBillNo(null);
		// } else {
		// if (fecavAction.checkImpWhiteBillNoIsDuple(new Request(
		// CommonVars.getCurrUser()), temp, tf.getText())) {
		// JOptionPane.showMessageDialog(
		// FmUseImportFecavBill.this, "白单号不能重复", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// temp.setWhiteBillNo(tf.getText());
		// }
		// // System.out.println(temp.getCustomsDeclarationCode() + " "
		// // + temp.getWhiteBillNo());
		// // temp = fecavAction.saveBrikeImpCustomsDeclaration(new
		// // Request(
		// // CommonVars.getCurrUser()), temp);
		// }
		// return true;
		// }

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10 || e.getKeyCode() == 40
					|| e.getKeyCode() == 38) {
				JTableListModel tableModel = (JTableListModel) this.table
						.getModel();
				// if (saveImpCustomsDeclaration()) {
				if (e.getKeyCode() == 10) {
					if (tableModel.hasNextRow()) {
						tableModel.nextRow();
					}
				}
				// } else {
				// this.table.requestFocus();
				// }
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void focusLost(FocusEvent e) {
			// System.out.println("---------- focusLost");
		}
	}

	public class ForcedEditTableCellRenderer extends DefaultTableCellRenderer {

		/** Creates a new instance of ForcedEditTableCellRenderer */
		public ForcedEditTableCellRenderer() {
			super();
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// Obtain the default component.
			Component comp = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			if (hasFocus && isSelected) {
				TableModel tblModel = table.getModel();
				if (tblModel.isCellEditable(row, column)) {
					// Cell is editable
					table.editCellAt(row, column);
					table.getEditorComponent().requestFocus();
					JTextField tf = (JTextField) table.getEditorComponent();
					if (value == null) {
						tf.setText("");
					} else {
						tf.setText(value.toString());
						tf.selectAll();
					}
				}
			} else {
				if (value != null && value.toString().length() != 9) {
					comp.setForeground(Color.RED);
				} else {
					if (isSelected) {
						comp.setForeground(table.getSelectionForeground());
						comp.setBackground(table.getSelectionBackground());
					} else {
						comp.setForeground(table.getForeground());
						comp.setBackground(table.getBackground());
					}
				}
			}
			return comp;
		}

	}

	// /**
	// * render table color row
	// */
	// class CheckWhiteCodeTableCellRenderer extends DefaultTableCellRenderer {
	// public Component getTableCellRendererComponent(JTable table,
	// Object value, boolean isSelected, boolean hasFocus, int row,
	// int column) {
	// Component c = super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// if (value!=null&&value.toString().length()!=9) {
	// c.setForeground(Color.RED);
	// } else {
	// if (isSelected) {
	// c.setForeground(table.getSelectionForeground());
	// c.setBackground(table.getSelectionBackground());
	// } else {
	// c.setForeground(table.getForeground());
	// c.setBackground(table.getBackground());
	// }
	// }
	// return c;
	// }
	// }
	/**
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (checkValue(table, row, column)) {
				c.setForeground(Color.BLUE);
			} else {
				if (isSelected) {
					c.setForeground(table.getSelectionForeground());
					c.setBackground(table.getSelectionBackground());
				} else {
					c.setForeground(table.getForeground());
					c.setBackground(table.getBackground());
				}
			}
			return c;
		}
	}

	/**
	 * 设置数据着色
	 */
	private void setRowColorDeclareDateOver30Day(JTable jTable, int exceptColumn) {
		JTableListModel tableModel = (JTableListModel) jTable.getModel();
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			if (i == exceptColumn) {
				continue;
			}
			jTable.getColumnModel().getColumn(i).setCellRenderer(
					new ColorTableCellRenderer());
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		JTableListModel tableModel = (JTableListModel) table.getModel();
		ImpCustomsDeclaration data = (ImpCustomsDeclaration) tableModel
				.getDataByRow(row);
		if (data.getDeclareDate() == null) {
			return false;
		}
		Calendar tempDate = Calendar.getInstance();
		Calendar declareDate = Calendar.getInstance();
		tempDate.setTime(new Date());
		declareDate.setTime(data.getDeclareDate());
		declareDate.add(Calendar.DAY_OF_YEAR, 30);
		//
		// 时间超过30天
		//
		if (tempDate.after(declareDate)) {
			return true;
		}
		return false;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("未出白单", null, getPnUsed(), null);
			jTabbedPane.addTab("已出白单", null, getPnCanStrik(), null);
			jTabbedPane.addTab("已核销", null, getPnCancel(), null);
			// jTabbedPane.setSelectedIndex(0);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							refresh();
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	private void refresh() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			List<Object> al = new ArrayList<Object>();
			// al.add(new Boolean(false));
			// List list = fecavAction.findImpCustomsDeclarationNotCancel(
			// new Request(CommonVars.getCurrUser()),
			// " and a.canStrike=? or a.canStrike is null ", al);
			al.add("");
			List list = fecavAction.findImpCustomsDeclarationNotCancel(
					new Request(CommonVars.getCurrUser()),
					" and (a.whiteBillNo=?  or  a.whiteBillNo is null) ", al);
			initUsedTable(list);
			showCount(list);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			btnWhiteBill.setText("不可核销");
			List<Object> al = new ArrayList<Object>();
			// al.add(new Boolean(true));
			// List list = fecavAction.findImpCustomsDeclarationNotCancel(
			// new Request(CommonVars.getCurrUser()),
			// " and a.canStrike=? ", al);
			List list = fecavAction.findImpCustomsDeclarationNotCancel(
					new Request(CommonVars.getCurrUser()),
					" and a.whiteBillNo is not null ", al);
			initCanStrikTable(list);
			showCount(list);

		} else if (jTabbedPane.getSelectedIndex() == 2) {
			List list = fecavAction.findImpCustomsDeclarationIsCancel(
					new Request(CommonVars.getCurrUser()), "", new ArrayList());
			initCancelTable(list);
			showCount(list);

		}
		setState();
	}

	/**
	 * This method initializes pnOuter
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCancel() {
		if (pnCancel == null) {
			lbCountthr = new JLabel();
			lbCountthr.setText("JLabel");
			lbCountthr.setForeground(Color.BLUE);
			pnCancel = new JPanel();
			pnCancel.setLayout(new BorderLayout());
			pnCancel.add(getSpOuter(), java.awt.BorderLayout.CENTER);
			pnCancel.add(lbCountthr, java.awt.BorderLayout.SOUTH);
		}
		return pnCancel;
	}

	private JPanel getPnCanStrik() {
		if (pnCanStrik == null) {
			// pnCanStrik = new JLabel();
			// pnCanStrik.setText("JLabel");
			// pnCanStrik.setForeground(Color.BLUE);
			pnCanStrik = new JPanel();
			pnCanStrik.setLayout(new BorderLayout());
			pnCanStrik.add(getSpIerr(), java.awt.BorderLayout.CENTER);
			pnCanStrik.add(getLbCounttsec(), java.awt.BorderLayout.SOUTH);
			// pnCanStrik.add(lbCountsec, java.awt.BorderLayout.SOUTH);
		}
		return pnCanStrik;
	}

	/**
	 * This method initializes pnInner
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnUsed() {
		if (pnUsed == null) {
			lbCountfirst = new JLabel();
			lbCountfirst.setText("JLabel");
			lbCountfirst.setForeground(Color.BLUE);
			pnUsed = new JPanel();
			pnUsed.setLayout(new BorderLayout());
			pnUsed.add(getSpInner(), java.awt.BorderLayout.CENTER);
			pnUsed.add(lbCountfirst, java.awt.BorderLayout.SOUTH);
		}
		return pnUsed;
	}

	/**
	 * This method initializes spInner
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpInner() {
		if (spInner == null) {
			spInner = new JScrollPane();
			spInner.setViewportView(getTbUsed());
		}
		return spInner;
	}

	private JScrollPane getSpIerr() {
		if (spIerr == null) {
			spIerr = new JScrollPane();
			spIerr.setViewportView(getTbCanStrik());
		}
		return spIerr;
	}

	/**
	 * This method initializes tbInner
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUsed() {
		if (tbUsed == null) {
			tbUsed = new JTable();
		}
		return tbUsed;
	}

	private void setState() {
		this.btnEdit.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnAdd.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnDelete.setVisible(jTabbedPane.getSelectedIndex() == 0);
		if (jTabbedPane.getSelectedIndex() == 0) {
			btnWhiteBill.setVisible(false);
			btnWhiteBill.setText("出白单");
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			btnWhiteBill.setVisible(true);
			btnWhiteBill.setText("取消出白单");
		} else if (jTabbedPane.getSelectedIndex() == 2) {
			btnWhiteBill.setVisible(false);

		}
		btnSave.setVisible(jTabbedPane.getSelectedIndex() == 0);
		btnCanStrik.setVisible(jTabbedPane.getSelectedIndex() == 1);
		btnCanNotStrik.setVisible(jTabbedPane.getSelectedIndex() == 1);
		btnUpdateRemainMoney.setVisible(jTabbedPane.getSelectedIndex() == 1);
		// this.repaint();
		// this.validate();
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (usedTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmUseImportFecavBill.this, "请选择你要修改的单", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					ImpCustomsDeclaration imp = (ImpCustomsDeclaration) usedTableModel
							.getCurrentRow();
					DgEditImpCustomsDeclaration dg = new DgEditImpCustomsDeclaration();
					dg.setWriteInWhiteNo(true);
					dg.setImpCustomsDeclaration(imp);
					dg.setVisible(true);
					if (dg.isOk()) {
						imp = dg.getImpCustomsDeclaration();
						usedTableModel.updateRow(imp);
						refresh();
					}
					showCount(usedTableModel.getList());
				}
			});

		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						// List list = fecavAction
						// .findBrikeImpCustomsDeclarationNotCancel(new Request(
						// CommonVars.getCurrUser()),"",new ArrayList());
						DgUseImportQuery dg = new DgUseImportQuery();
						dg.setBefore(true);
						dg.setPnViseble(false);
						dg.setWhiteBill(false);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							showCount(list);
							initUsedTable(list);
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						DgUseImportQuery dg = new DgUseImportQuery();
						dg.setBefore(true);
						dg.setWhiteBill(true);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initCanStrikTable(list);
							showCount(list);
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						// List list = fecavAction
						// .findBrikeImpCustomsDeclarationIsCancel(new Request(
						// CommonVars.getCurrUser()),"",new ArrayList());
						DgUseImportQuery dg = new DgUseImportQuery();
						dg.setBefore(false);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initCancelTable(list);
							showCount(list);
						}
					}
					setState();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
				}
			});
		}
		return btnAdd;
	}

	private void addData() {
		List<TempCustomsDeclarationInfoForFecav> list = FecavQuery
				.getInstance().findNotUseImpCustomsDeclaration();
		if (list != null && list.size() > 0) {
			List<ImpCustomsDeclaration> dataSource = fecavAction
					.addImpCustomsDeclaration(new Request(CommonVars
							.getCurrUser()), list);
			this.usedTableModel.addRows(dataSource);
		}
		showCount(usedTableModel.getList());
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tbUsed.getCellEditor() != null) {
						tbUsed.getCellEditor().stopCellEditing();
					}
					List list = usedTableModel.getList();
					List lsWhiteNo = new ArrayList();
					for (int i = 0; i < list.size(); i++) {
						ImpCustomsDeclaration imp = (ImpCustomsDeclaration) list
								.get(i);
						if (imp.getWhiteBillNo() != null
								&& !"".equals(imp.getWhiteBillNo().trim())) {
							if (lsWhiteNo.contains(imp.getWhiteBillNo())) {
								JOptionPane.showMessageDialog(
										FmUseImportFecavBill.this, "白单号"
												+ imp.getWhiteBillNo() + "重复",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							} else if (fecavAction.checkImpWhiteBillNoIsDuple(
									new Request(CommonVars.getCurrUser()), imp,
									imp.getWhiteBillNo())) {
								JOptionPane.showMessageDialog(
										FmUseImportFecavBill.this, "白单号"
												+ imp.getWhiteBillNo() + "重复",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								lsWhiteNo.add(imp.getWhiteBillNo());
							}
						}
					}
					String stemp = getWhiteBillNoLenLessThanNine(list);
					if (stemp != null && !"".equals(stemp.trim())) {
						if (JOptionPane.showConfirmDialog(
								FmUseImportFecavBill.this, "下列白单号\n" + stemp
										+ "\n的长度不等于18位，你确定保存吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					}
					for (int i = 0; i < list.size(); i++) {
						ImpCustomsDeclaration imp = (ImpCustomsDeclaration) list
								.get(i);
						if (imp.getWhiteBillNo() != null
								&& !"".equals(imp.getWhiteBillNo().trim())) {
							imp.setCanStrike(true);
						}
						fecavAction.saveImpCustomsDeclaration(new Request(
								CommonVars.getCurrUser()), imp);
					}
					refresh();
				}
			});
		}
		return btnSave;
	}

	private String getWhiteBillNoLenLessThanNine(List list) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			ImpCustomsDeclaration imp = (ImpCustomsDeclaration) list.get(i);
			if (imp.getWhiteBillNo() != null
					&& imp.getWhiteBillNo().trim().length() != 18) {
				buffer.append(imp.getWhiteBillNo().trim() + ";");
			}
		}
		return buffer.toString();
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton();
			btnUpdate.setText("更新");
			btnUpdate.setVisible(false);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fecavAction.deleteOldImpCustomsDeclaration(new Request(
							CommonVars.getCurrUser()));
					refresh();
				}
			});
		}
		return btnUpdate;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (usedTableModel.getCurrentRow() == null) {
						return;
					}
					ImpCustomsDeclaration icd = (ImpCustomsDeclaration) usedTableModel
							.getCurrentRow();
					if (fecavAction.deleteImpCustomsDeclaration(new Request(
							CommonVars.getCurrUser()), icd)) {
						usedTableModel.deleteRow(icd);
						// JOptionPane.showMessageDialog(null,"","",JOptionPane.OK_OPTION);
					} else {
						JOptionPane.showMessageDialog(null, "白单已核销，不能删除！",
								"提示", JOptionPane.WARNING_MESSAGE);
					}

				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes tbCanStrik
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCanStrik() {
		if (tbCanStrik == null) {
			tbCanStrik = new JTable();
		}
		return tbCanStrik;
	}

	/**
	 * This method initializes btnCanStrik
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWhiteBill() {
		if (btnWhiteBill == null) {
			btnWhiteBill = new JButton();
			btnWhiteBill.setText("出白单");
			btnWhiteBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (usedTableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this,
									"    请选择可以核销的白单!", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}

						ImpCustomsDeclaration sc = (ImpCustomsDeclaration) usedTableModel
								.getCurrentRow();
						if (sc.getWhiteBillNo() == null
								|| sc.getWhiteBillNo().trim().equals("")) {
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this, "  请先填写白单号！",
									"提示！", JOptionPane.WARNING_MESSAGE);
							return;
						}
						sc.setCanStrike(true);
						fecavAction.saveImpCustomsDeclaration(new Request(
								CommonVars.getCurrUser()), sc);
						usedTableModel.deleteRow(sc);
						showCount(usedTableModel.getList());
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						if (canStrikTableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this,
									"                请选择白单!", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						ImpCustomsDeclaration sc = (ImpCustomsDeclaration) canStrikTableModel
								.getCurrentRow();
						if(sc.getRemainMoney()==null||sc.getRemainMoney()<=0){
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this,
									"此白单余额为零,不能取消出白单!", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (sc.getSignInNo() != null
								&& !"".equals(sc.getSignInNo().trim())) {
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this,
									"此白单已经有冲销,不能取消出白单!", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						sc.setCanStrike(false);
						sc.setWhiteBillNo(null);
						fecavAction.saveImpCustomsDeclaration(new Request(
								CommonVars.getCurrUser()), sc);
						canStrikTableModel.deleteRow(sc);
						showCount(canStrikTableModel.getList());
					}
				}
			});
		}
		return btnWhiteBill;
	}

	private JLabel getLbCounttsec() {
		if (lbCounttsec == null) {
			lbCounttsec = new JLabel("    111");
			lbCounttsec.setForeground(Color.BLUE);
		}

		return lbCounttsec;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCanStrik() {
		if (btnCanStrik == null) {
			btnCanStrik = new JButton();
			btnCanStrik.setText("允许核销");
			btnCanStrik.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (canStrikTableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this,
									"                请选择白单!", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						ImpCustomsDeclaration sc = (ImpCustomsDeclaration) canStrikTableModel
								.getCurrentRow();
						if (sc.getRemainMoney() == null
								|| sc.getRemainMoney() <= 0.0) {
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this, "剩余金额大于零才能允许核销",
									"提示！", JOptionPane.WARNING_MESSAGE);
							return;
						}
						sc.setCanStrike(true);
						fecavAction.saveImpCustomsDeclaration(new Request(
								CommonVars.getCurrUser()), sc);
						canStrikTableModel.updateRow(sc);
						showCount(canStrikTableModel.getList());
					}
				}
			});
		}
		return btnCanStrik;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCanNotStrik() {
		if (btnCanNotStrik == null) {
			btnCanNotStrik = new JButton();
			btnCanNotStrik.setText("不允许核销");
			btnCanNotStrik
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (jTabbedPane.getSelectedIndex() == 1) {
								if (canStrikTableModel.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmUseImportFecavBill.this,
											"                请选择白单!", "提示！",
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								if (JOptionPane.showConfirmDialog(
										FmUseImportFecavBill.this,
										"你确定不允许再核销此白单?", "提示！",
										JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
									return;
								}
								ImpCustomsDeclaration sc = (ImpCustomsDeclaration) canStrikTableModel
										.getCurrentRow();
								sc.setCanStrike(false);
								fecavAction.saveImpCustomsDeclaration(
										new Request(CommonVars.getCurrUser()),
										sc);
								canStrikTableModel.updateRow(sc);
								showCount(canStrikTableModel.getList());
							}
						}

					});
		}
		return btnCanNotStrik;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdateRemainMoney() {
		if (btnUpdateRemainMoney == null) {
			btnUpdateRemainMoney = new JButton();
			btnUpdateRemainMoney.setText("更新余额");
			btnUpdateRemainMoney.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						if (canStrikTableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmUseImportFecavBill.this,
									"                请选择白单!", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (JOptionPane.showConfirmDialog(
								FmUseImportFecavBill.this,
								"你确定更新此白单的余额?", "提示！",
								JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						ImpCustomsDeclaration sc = (ImpCustomsDeclaration) canStrikTableModel
								.getCurrentRow();
						sc=fecavAction.updateRemainImpCustomsDecMoney(
								new Request(CommonVars.getCurrUser()),
								sc);
						canStrikTableModel.updateRow(sc);
					}
				}
			});
		}
		return btnUpdateRemainMoney;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
