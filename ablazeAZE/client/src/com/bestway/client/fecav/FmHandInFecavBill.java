package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmHandInFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane spNotHandIn = null;

	private JTable tbNotHandIn = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTableListModel notHandInTableModel = null;

	private JTableListModel handInTableModel = null;

	private FecavAction fecavAction = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnNotHandIn = null;

	private JPanel pnHandIn = null;

	private JScrollPane spHandIn = null;

	private JTable tbHandIn = null;

	private JButton btnHandIn = null;

	private JButton btnUndoHandIn = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private JLabel lbCountfirst = null;

	private JLabel lbCountsec = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmHandInFecavBill() {
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
		this.setSize(new java.awt.Dimension(676,365));
		this.setContentPane(getJContentPane());
		this.setTitle("核销单交单明细");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						if (jTabbedPane.getSelectedIndex() == 0) {
							List list = fecavAction.findFecavBillByState(
									new Request(CommonVars.getCurrUser()),
									FecavState.DEBENTURE_SIGN_IN);
							initNotHandInTable(list);
							showCount(list);
						} else if (jTabbedPane.getSelectedIndex() == 1) {
							List list = fecavAction
									.findFecavBillNotStrike(new Request(CommonVars
											.getCurrUser()),"",new ArrayList());
							initHandInTable(list);
							showCount(list);
						}
					}
				});

	}
	
	private void showCount(List list) {
		Double total = 0.0;
		int isselected = 0;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				FecavBill fecavBill = (FecavBill) list.get(i);
				total += (fecavBill.getTotalPrice() == null ? 0.0 : fecavBill
						.getTotalPrice());
				if (fecavBill.getIsSelected() != null) {
					isselected += ((fecavBill.getIsSelected() == true) ? 1 : 0);
				}
			}
		}
		if (jTabbedPane.getSelectedIndex() == 0) {
			this.lbCountfirst.setText(" 总份数: " + list.size()
					+ "         已选份数: " + isselected + "           统计金额："
					+ CommonVars.formatDoubleToString(total, 999, 6));

		} else if (jTabbedPane.getSelectedIndex() == 1) {
			this.lbCountsec.setText(" 总份数: " + list.size() + "         已选份数: "
					+ isselected + "           统计金额："
					+ CommonVars.formatDoubleToString(total, 999, 6));

		}

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
			jToolBar.add(getBtnSelectAll());
			jToolBar.add(getBtnNotSelectAll());
			jToolBar.add(getBtnHandIn());
			jToolBar.add(getBtnUndoHandIn());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnExit());			
		}
		return jToolBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpNotHandIn() {
		if (spNotHandIn == null) {
			spNotHandIn = new JScrollPane();
			spNotHandIn.setViewportView(getTbNotHandIn());
		}
		return spNotHandIn;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbNotHandIn() {
		if (tbNotHandIn == null) {
			tbNotHandIn = new JTable();
		}
		return tbNotHandIn;
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
					if (jTabbedPane.getSelectedIndex() == 0) {
						List list = fecavAction.findFecavBillByState(
								new Request(CommonVars.getCurrUser()),
								FecavState.DEBENTURE_SIGN_IN);
						initNotHandInTable(list);
						showCount(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						// List list = fecavAction.findFecavBillByState(
						// new Request(CommonVars.getCurrUser()),
						// FecavState.HAND_IN_BILL);
						List list = fecavAction
								.findFecavBillNotStrike(new Request(CommonVars
										.getCurrUser()),"",new ArrayList());
						initHandInTable(list);
						showCount(list);
					}
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
					FmHandInFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initNotHandInTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("出口日期", "exportDate", 100));
				list.add(addColumn("申报日期", "declareDate", 100));
				list.add(addColumn("核销单号 ", "code", 200));
				list.add(addColumn("领单日期", "innerObtainDate", 100));
				list.add(addColumn("报关单号", "customsDeclarationCode", 200));
				list.add(addColumn("合同号码", "contractNo", 100));
				list.add(addColumn("手册号码", "emsNo", 100));
				list.add(addColumn("币别", "curr.name", 100));
				list.add(addColumn("总金额", "totalPrice", 100));
				list.add(addColumn("标志", "billState", 100));
				return list;
			}
		};
		JTableListModel.dataBind(tbNotHandIn, list, jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		TableColumn column = tbNotHandIn.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		column = this.tbNotHandIn.getColumnModel().getColumn(11);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			// JLabel label = new JLabel();
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				int state = -1;
				if (value != null) {
					state = Integer.parseInt(value.toString());
				}
				switch (state) {
				case FecavState.DEBENTURE_SIGN_IN:
					this.setText("退税联已领用");
					break;
				}
				return this;
			}
		});
		notHandInTableModel = (JTableListModel) tbNotHandIn.getModel();
//		CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
//				this.tfFieldValue, this.btnSearch, this.notHandInTableModel);
	}

	private void initHandInTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("出口日期", "exportDate", 100));
				list.add(addColumn("申报日期", "declareDate", 100));
				list.add(addColumn("核销单号 ", "code", 200));
				list.add(addColumn("领单日期", "innerObtainDate", 100));
				list.add(addColumn("报关单号", "customsDeclarationCode", 200));
				list.add(addColumn("合同号码", "contractNo", 100));
				list.add(addColumn("手册号码", "emsNo", 100));
				list.add(addColumn("币别", "curr.name", 100));
				list.add(addColumn("总金额", "totalPrice", 100));
				list.add(addColumn("标志", "billState", 100));
				return list;
			}
		};
		JTableListModel.dataBind(tbHandIn, list, jTableListModelAdapter);
		jTableListModelAdapter.setEditableColumn(1);
		TableColumn column = tbHandIn.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		column = this.tbHandIn.getColumnModel().getColumn(11);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			// JLabel label = new JLabel();
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				int state = -1;
				if (value != null) {
					state = Integer.parseInt(value.toString());
				}
				switch (state) {
				case FecavState.HAND_IN_BILL:
					this.setText("已交单");
					break;
				}
				return this;
			}
		});
		handInTableModel = (JTableListModel) tbHandIn.getModel();
//		CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
//				this.tfFieldValue, this.btnSearch, this.handInTableModel);
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb.isSelected();
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof FecavBill) {
				FecavBill temp = (FecavBill) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
				showCount(tableModel.getList());
			}
			fireEditingStopped();
		}
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
			jTabbedPane.addTab("未交单", null, getPnNotHandIn(), null);
			jTabbedPane.addTab("已交单", null, getPnHandIn(), null);
			jTabbedPane.setSelectedIndex(0);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								List list = fecavAction.findFecavBillByState(
										new Request(CommonVars.getCurrUser()),
										FecavState.DEBENTURE_SIGN_IN);
								initNotHandInTable(list);
								showCount(list);
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								List list = fecavAction.findFecavBillByState(
										new Request(CommonVars.getCurrUser()),
										FecavState.HAND_IN_BILL);
								initHandInTable(list);
								showCount(list);
							}
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes pnOuter
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnNotHandIn() {
		if (pnNotHandIn == null) {
			lbCountfirst = new JLabel();
			lbCountfirst.setText("JLabel");
			lbCountfirst.setForeground(Color.blue);
			pnNotHandIn = new JPanel();
			pnNotHandIn.setLayout(new BorderLayout());
			pnNotHandIn.add(getSpNotHandIn(), java.awt.BorderLayout.CENTER);
			pnNotHandIn.add(lbCountfirst, java.awt.BorderLayout.SOUTH);
		}
		return pnNotHandIn;
	}

	/**
	 * This method initializes pnInner
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnHandIn() {
		if (pnHandIn == null) {
			lbCountsec = new JLabel();
			lbCountsec.setText("JLabel");
			lbCountsec.setForeground(Color.blue);
			pnHandIn = new JPanel();
			pnHandIn.setLayout(new BorderLayout());
			pnHandIn.add(getSpHandIn(), java.awt.BorderLayout.CENTER);
			pnHandIn.add(lbCountsec, java.awt.BorderLayout.SOUTH);
		}
		return pnHandIn;
	}

	/**
	 * This method initializes spInner
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpHandIn() {
		if (spHandIn == null) {
			spHandIn = new JScrollPane();
			spHandIn.setViewportView(getTbHandIn());
		}
		return spHandIn;
	}

	/**
	 * This method initializes tbInner
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbHandIn() {
		if (tbHandIn == null) {
			tbHandIn = new JTable();
		}
		return tbHandIn;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHandIn() {
		if (btnHandIn == null) {
			btnHandIn = new JButton();
			btnHandIn.setText("交单");
			btnHandIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = new ArrayList();// notSignInTableModel.getCurrentRows();
					for (int i = 0; i < notHandInTableModel.getList().size(); i++) {
						FecavBill bill = (FecavBill) notHandInTableModel
								.getList().get(i);
						if (bill.getIsSelected()) {
							list.add(bill);
						}
					}
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(FmHandInFecavBill.this,
								"请选择你要交单的核销单", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// List list=notHandInTableModel.getCurrentRows();
					DgHandInFecavBill dg = new DgHandInFecavBill();
					dg.setLsFecavBill(list);
					dg.setVisible(true);
					List lsResult = dg.getLsResult();
					if (lsResult != null && lsResult.size() > 0) {
						notHandInTableModel.deleteRows(list);
					}
					showCount(notHandInTableModel.getList());
				}
			});
		}
		return btnHandIn;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoHandIn() {
		if (btnUndoHandIn == null) {
			btnUndoHandIn = new JButton();
			btnUndoHandIn.setText("取消交单");
			btnUndoHandIn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = new ArrayList();// notSignInTableModel.getCurrentRows();
							for (int i = 0; i < handInTableModel.getList()
									.size(); i++) {
								FecavBill bill = (FecavBill) handInTableModel
										.getList().get(i);
								if (bill.getIsSelected()) {
									list.add(bill);
								}
							}
							if (list.isEmpty()) {
								JOptionPane.showMessageDialog(
										FmHandInFecavBill.this,
										"请选择你要取消交单的核销单", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmHandInFecavBill.this, "确定要取消已交单的核销单??",
									"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							// List list=handInTableModel.getCurrentRows();
							list = fecavAction
									.undoHandBillFecavBill(new Request(
											CommonVars.getCurrUser()), list);
							if (list.size() > 0) {
								handInTableModel.deleteRows(list);
							}
							showCount(notHandInTableModel.getList());
						}
					});
		}
		return btnUndoHandIn;
	}

	private void setState() {
		this.btnHandIn.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnUndoHandIn.setVisible(jTabbedPane.getSelectedIndex() == 1);
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
//						List list = fecavAction.findFecavBillByState(
//								new Request(CommonVars.getCurrUser()),
//								FecavState.DEBENTURE_SIGN_IN);
						DgFecavBillQuery dg = new DgFecavBillQuery();
						dg.setFecavState(FecavState.DEBENTURE_SIGN_IN);
						dg.setBefore(true);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initNotHandInTable(list);
							showCount(list);
						}	
						
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						// List list = fecavAction.findFecavBillByState(
						// new Request(CommonVars.getCurrUser()),
						// FecavState.HAND_IN_BILL);
//						List list = fecavAction
//								.findFecavBillNotStrike(new Request(CommonVars
//										.getCurrUser()),"",new ArrayList());
						DgFecavBillQuery dg = new DgFecavBillQuery();
						dg.setFecavState(FecavState.HAND_IN_BILL);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initHandInTable(list);
							showCount(list);
						}							
					}
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
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0 ? notHandInTableModel == null
							: handInTableModel == null) {
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							jTabbedPane.getSelectedIndex() == 0 ? notHandInTableModel
									.getList()
									: handInTableModel.getList());
					InputStream reportStream = FmHandInFecavBill.class
							.getResourceAsStream("report/FecavBillReport.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("reportTitle", "外汇核销单交单");
					JasperPrint jasperPrint = null;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setText("全否");
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectAll(false);
						}
					});
		}
		return btnNotSelectAll;
	}

	private void selectAll(boolean b) {
		if (jTabbedPane.getSelectedIndex() == 0) {
			List list = notHandInTableModel.getList();
			for (int i = 0; i < list.size(); i++) {
				FecavBill fecavBill = (FecavBill) list.get(i);
				fecavBill.setIsSelected(b);
			}
			notHandInTableModel.setList(list);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			List list = handInTableModel.getList();
			for (int i = 0; i < list.size(); i++) {
				FecavBill fecavBill = (FecavBill) list.get(i);
				fecavBill.setIsSelected(b);
			}
			handInTableModel.setList(list);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
