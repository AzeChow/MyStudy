package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;

public class FmCloseAccountFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnStrike = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbStrike = null;

	private JToolBar jToolBar1 = null;

	private JTableListModel strikeTableModel = null;

//	private JTableListModel notStrikeTableModel = null;

//	private JTableListModel impTableModel = null;
//
//	private JTableListModel exchangeTableModel = null;

	private FecavAction fecavAction = null;

	private JButton btnSearch = null;

	private JButton btnCloseAccount = null;

	private JButton btnCancelCloseAccount = null;

	private JTable tbCloseAccount = null;

	private JScrollPane jScrollPane4 = null;

	private JSplitPane jSplitPane = null;

	private JTabbedPane jTabbedPane1 = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JLabel lbExport = null;

	private JScrollPane jScrollPane = null;

	private JTable tbExport = null;

	private JLabel lbImport = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbImport = null;

	private JLabel lbExchange = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbExchange = null;

	private JTableListModel tableModelImport;

	private JTableListModel tableModelExport;

	private JTableListModel tableModelExchange;

	/**
	 * This method initializes
	 * 
	 */
	public FmCloseAccountFecavBill() {
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
		this.setSize(new java.awt.Dimension(726, 458));
		this.setContentPane(getJContentPane());
		this.setTitle("出口外汇关帐");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						if (jTabbedPane.getSelectedIndex() == 0) {
							List list = fecavAction
									.findFecavBillStrikeByStateSignIn(new Request(
											CommonVars.getCurrUser()));
							initStrikeTable(list);
						} else if (jTabbedPane.getSelectedIndex() == 1) {
							List list = fecavAction.findFecavBillStrikeByState(
									new Request(CommonVars.getCurrUser()),
									FecavState.CLOSE_ACCOUNT);
							initCloseAccount(list);
						}
						showFecavBillAllData(getCurrentFecavBillStrike());
						setState();
					}
				});

	}

	private void initCloseAccount(List list) {
		JTableListModel.dataBind(tbCloseAccount, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("核销编码", "signInNo", 100));
						list.add(addColumn("冲销者", "strikeMan", 100));
						list.add(addColumn("冲销日期 ", "strikeDate", 200));

						list.add(addColumn("核销者", "cavSignInMan", 80));
						list.add(addColumn("核销期", "cavSignInDate", 80));
						list.add(addColumn("财务签收者", "financeSignInMan", 80));
						list.add(addColumn("财务签收日期", "financeSignInDate", 80));
						list.add(addColumn("关帐人", "closeAccountMan", 80));
						list.add(addColumn("关帐日期", "closeAccountDate", 80));
						return list;
					}
				});
		JTableListModel tableModel = (JTableListModel) tbCloseAccount
				.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, tableModel);
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
			jToolBar.add(getBtnCloseAccount());
			jToolBar.add(getJButton22());
			jToolBar.add(getBtnUndoStrike());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoStrike() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						List list = fecavAction.findFecavBillStrikeByState(
								new Request(CommonVars.getCurrUser()),
								FecavState.FINANCE_SIGN_IN);
						initStrikeTable(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						List list = fecavAction.findFecavBillStrikeByState(
								new Request(CommonVars.getCurrUser()),
								FecavState.CLOSE_ACCOUNT);
						initCloseAccount(list);
					}
					showFecavBillAllData(getCurrentFecavBillStrike());
					setState();
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
					FmCloseAccountFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("未关帐", null, getPnStrike(), null);
			jTabbedPane.addTab("已关帐", null, getJScrollPane4(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								List list = fecavAction
										.findFecavBillStrikeByState(
												new Request(CommonVars
														.getCurrUser()),
												FecavState.FINANCE_SIGN_IN);
								initStrikeTable(list);
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								List list = fecavAction
										.findFecavBillStrikeByState(
												new Request(CommonVars
														.getCurrUser()),
												FecavState.CLOSE_ACCOUNT);
								initCloseAccount(list);
							}
							showFecavBillAllData(getCurrentFecavBillStrike());
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	private void setState() {
		int index = jTabbedPane.getSelectedIndex();
		// this.jButton.setVisible(index == 1);
		// this.btnCancel.setVisible(index == 0);
		// cbbStrikeDate.setEnabled(false);
		// tfStrikeMan.setEnabled(false);
		// jTabbedPane1.setEnabledAt(0, false);
		this.btnCancelCloseAccount.setVisible(index == 1);
		this.btnCloseAccount.setVisible(index == 0);
		// if (jTabbedPane.getSelectedIndex() == 2) {
		// jLabel3.setText("关帐者");
		// jLabel.setText("关帐日期");
		// } else {
		// jLabel3.setText("签收者");
		// jLabel.setText("签收日期");
		// }

	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnStrike() {
		if (pnStrike == null) {
			pnStrike = new JPanel();
			pnStrike.setLayout(new BorderLayout());
			pnStrike.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return pnStrike;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbStrike());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbStrike() {
		if (tbStrike == null) {
			tbStrike = new JTable();
			tbStrike.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbStrike
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
//								if (tableModel.getCurrentRow() != null) {
									FecavBillStrike fecavBillStrike = (FecavBillStrike) tableModel
											.getCurrentRow();
									showFecavBillAllData(fecavBillStrike);
//								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbStrike;
	}

	// private void initNotStrikeTable(List list) {
	// notStrikeTableModel = new JTableListModel(tbNotStrike, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List<Object> list = (List<Object>) (new Vector());
	// list.add(addColumn("核销编码", "signInNo", 100));
	// list.add(addColumn("冲销者", "strikeMan", 100));
	// list.add(addColumn("冲销日期 ", "strikeDate", 200));
	// return list;
	// }
	// });
	// tbNotStrike
	// .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
	// this.tfFieldValue, this.btnSearch, this.notStrikeTableModel);
	// }

	private void initStrikeTable(List list) {
		JTableListModel.dataBind(tbStrike, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("核销编码", "signInNo", 100));
				list.add(addColumn("冲销者", "strikeMan", 100));
				list.add(addColumn("冲销日期 ", "strikeDate", 200));

				list.add(addColumn("核销者", "cavSignInMan", 80));
				list.add(addColumn("核销期", "cavSignInDate", 80));
				list.add(addColumn("财务签收者", "financeSignInMan", 80));
				list.add(addColumn("财务签收日期", "financeSignInDate", 80));
				return list;
			}
		});
		strikeTableModel = (JTableListModel) tbStrike.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.strikeTableModel);
	}

	private FecavBillStrike getCurrentFecavBillStrike() {
		// if (jTabbedPane.getSelectedIndex() == 0) {
		// if (notStrikeTableModel.getCurrentRow() != null) {
		// return (FecavBillStrike) notStrikeTableModel.getCurrentRow();
		// }
		// } else
		if (jTabbedPane.getSelectedIndex() == 0) {
			if (strikeTableModel.getCurrentRow() != null) {
				return (FecavBillStrike) strikeTableModel.getCurrentRow();
			}
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			JTableListModel model = (JTableListModel) tbCloseAccount.getModel();
			if (model.getCurrentRow() != null) {
				return (FecavBillStrike) model.getCurrentRow();
			}
		}
		return null;
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
						// .findFecavBillStrikeByState(
						// new Request(CommonVars
						// .getCurrUser()),
						// FecavState.FINANCE_SIGN_IN);
						DgFecavBillStrikeQuery dg = new DgFecavBillStrikeQuery();
						dg.setFecavState(FecavState.FINANCE_SIGN_IN);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initStrikeTable(list);
						}
						// initStrikeTable(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						// List list = fecavAction
						// .findFecavBillStrikeByState(
						// new Request(CommonVars
						// .getCurrUser()),
						// FecavState.CLOSE_ACCOUNT);
						DgFecavBillStrikeQuery dg = new DgFecavBillStrikeQuery();
						dg.setFecavState(FecavState.CLOSE_ACCOUNT);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initCloseAccount(list);
						}
						// initCloseAccount(list);
					}
					showFecavBillAllData(getCurrentFecavBillStrike());
					setState();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCloseAccount() {
		if (btnCloseAccount == null) {
			btnCloseAccount = new JButton();
			btnCloseAccount.setText("关帐");
			btnCloseAccount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JTableListModel model = (JTableListModel) tbStrike
									.getModel();
							if (model.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmCloseAccountFecavBill.this,
										"请选择你要关帐确认的数据", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							List list = model.getCurrentRows();
							DgCloseAccountFecavBill dg = new DgCloseAccountFecavBill();
							dg.setLsFecavBill(list);
							dg.setVisible(true);
							List lsResult = dg.getLsResult();
							if (lsResult != null && lsResult.size() > 0) {
								model.deleteRows(list);
							}
						}
					});
		}
		return btnCloseAccount;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton22() {
		if (btnCancelCloseAccount == null) {
			btnCancelCloseAccount = new JButton();
			btnCancelCloseAccount.setText("取消关帐");
			btnCancelCloseAccount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JTableListModel model = (JTableListModel) tbCloseAccount
									.getModel();
							if (model.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmCloseAccountFecavBill.this,
										"请选择你要取消关帐确认的数据", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmCloseAccountFecavBill.this,
									"确定要取消已关帐的单据 ??", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							List list = model.getCurrentRows();
							// for (int i = 0; i < list.size(); i++) {
							// FecavBillStrike f = (FecavBillStrike) list
							// .get(i);
							// f.setFecavState(FecavState.FINANCE_SIGN_IN);
							// f.setCloseAccountMan(null);
							// f.setCloseAccountDate(null);
							// }
							list = fecavAction
									.undoCloseAccountFecavBill(new Request(
											CommonVars.getCurrUser()), list);
							if (list.size() > 0) {
								model.deleteRows(list);
							}
						}
					});
		}
		return btnCancelCloseAccount;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCloseAccount() {
		if (tbCloseAccount == null) {
			tbCloseAccount = new JTable();
			tbCloseAccount.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbCloseAccount
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
//								if (tableModel.getCurrentRow() != null) {
									FecavBillStrike fecavBillStrike = (FecavBillStrike) tableModel
											.getCurrentRow();
									showFecavBillAllData(fecavBillStrike);
//								}e
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbCloseAccount;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getTbCloseAccount());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(4);
			jSplitPane.setTopComponent(getJTabbedPane());
			jSplitPane.setBottomComponent(getJTabbedPane1());
			jSplitPane.setDividerLocation(180);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.addTab("出口报关单", null, getJPanel(), null);
			jTabbedPane1.addTab("进口报关单冲销", null, getJPanel1(), null);
			jTabbedPane1.addTab("汇票冲销", null, getJPanel2(), null);
			jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (jTabbedPane1.getSelectedIndex() == 0) {
						showExportData(getCurrentFecavBillStrike());
					} else if (jTabbedPane1.getSelectedIndex() == 1) {
						showImportData(getCurrentFecavBillStrike());
					} else {
						showExchangeData(getCurrentFecavBillStrike());
					}
				}
			});
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbExport = new JLabel();
			lbExport.setText("JLabel");
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(lbExport, BorderLayout.SOUTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbImport = new JLabel();
			lbImport.setText("JLabel");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(lbImport, BorderLayout.SOUTH);
			jPanel1.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			lbExchange = new JLabel();
			lbExchange.setText("JLabel");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(lbExchange, BorderLayout.SOUTH);
			jPanel2.add(getJScrollPane3(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbExport());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExport() {
		if (tbExport == null) {
			tbExport = new JTable();
		}
		return tbExport;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbImport());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImport() {
		if (tbImport == null) {
			tbImport = new JTable();
		}
		return tbImport;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbExchange());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExchange() {
		if (tbExchange == null) {
			tbExchange = new JTable();
		}
		return tbExchange;
	}

	private void initExportData(List list) {
		JTableListModel.dataBind(this.tbExport, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("出口日期", "exportDate", 100));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("核销单号 ", "code", 200));
						list.add(addColumn("领单日期", "innerObtainDate", 100));
						list.add(addColumn("报关单号", "customsDeclarationCode",
								200));
						list.add(addColumn("合同号码", "contractNo", 100));
						list.add(addColumn("手册号码", "emsNo", 100));
						list.add(addColumn("币别", "curr.name", 100));
						list.add(addColumn("总金额", "totalPrice", 100));
						return list;
					}
				}, ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableModelExport = (JTableListModel) tbExport.getModel();
	}

	private void initImportTable(List list) {
		tableModelImport = new JTableListModel(tbImport, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("进口报关单号", "customsDeclarationCode",
								100));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("白单号", "whiteBillNo", 200));
						list.add(addColumn("合同号", "contractNo", 100));
						list.add(addColumn("手册编号 ", "emsNo", 200));
						list.add(addColumn("币制 ", "curr.name", 100));
						list.add(addColumn("报关单总金额", "totalMoney", 100));
						list.add(addColumn("冲销金额", "strikeMoney", 200));
						list.add(addColumn("折美元", "converUSDMoney", 200));
						return list;
					}
				});
		tbImport
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void initExchangeTable(List list) {
		JTableListModel.dataBind(tbExchange, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("汇票号码", "billOfExchangeCode", 100));
						list.add(addColumn("结汇日期", "endDate", 100));
						list.add(addColumn("结汇金额 ", "exchangeMoney", 200));
						list.add(addColumn("冲销金额", "strikeMoney", 100));
						return list;
					}
				});
		tableModelExchange = (JTableListModel) tbImport.getModel();
	}

	private void refreshMoney() {
		FecavBillStrike fecavBillStrike = getCurrentFecavBillStrike();
		if (fecavBillStrike == null) {
			if (jTabbedPane1.getSelectedIndex() == 0) {
				this.lbExport.setText("出口报关单金额为:" + 0 + "  份数:" + 0);
			} else if (jTabbedPane1.getSelectedIndex() == 1) {
				this.lbImport.setText("进口口报关单金额为:" + 0 + "  份数:" + 0);
			} else {
				this.lbExchange.setText("汇票金额为:" + 0 + "  份数:" + 0);
			}
			return;
		}
		if (jTabbedPane1.getSelectedIndex() == 0) {
			double expMoney = 0.0;
			for (int i = 0; i < tableModelExport.getList().size(); i++) {
				FecavBill fecavBill = (FecavBill) tableModelExport.getList()
						.get(i);
				expMoney += CommonVars.formatDouble(fecavBill.getTotalPrice());
			}
			this.lbExport.setText("出口报关单金额为:"
					+ CommonVars.formatDoubleToString(expMoney)
					+ "  份数:"
					+ (tableModelExport == null ? 0 : tableModelExport
							.getList().size()));
		} else if (jTabbedPane1.getSelectedIndex() == 1) {
			double importMoney = this.fecavAction.findFecavStrikeImpMoney(
					new Request(CommonVars.getCurrUser()), fecavBillStrike);
			this.lbImport.setText("进口报关单金额为:"
					+ CommonVars.formatDoubleToString(importMoney)
					+ "  份数:"
					+ (tableModelImport == null ? 0 : tableModelImport
							.getList().size()));
		} else {
			double exchangeMoney = this.fecavAction
					.findFecavStrikeExchangeMoney(new Request(CommonVars
							.getCurrUser()), fecavBillStrike);
			this.lbExchange.setText("汇票金额为:"
					+ CommonVars.formatDoubleToString(exchangeMoney)
					+ "  份数:"
					+ (this.tableModelExchange == null ? 0 : tableModelExchange
							.getList().size()));
		}
	}

	/**
	 * show export customs bill
	 * 
	 * @param fecavBillStrike
	 */
	private void showExportData(FecavBillStrike fecavBillStrike) {
		List list = new ArrayList();
		if (fecavBillStrike != null && !"".equals(fecavBillStrike.getId())) {
			list = fecavAction.findFecavBillByStrike(new Request(CommonVars
					.getCurrUser()), fecavBillStrike);
		}
		this.initExportData(list);
		this.refreshMoney();
	}

	private void showImportData(FecavBillStrike fecavBillStrike) {
		List list = new ArrayList();
		if (fecavBillStrike != null) {
			list = fecavAction.findBrikeImpCustomsDeclaration(new Request(
					CommonVars.getCurrUser()), fecavBillStrike);
		}
		initImportTable(list);
		this.refreshMoney();
	}

	private void showExchangeData(FecavBillStrike fecavBillStrike) {
		List list = new ArrayList();
		if (fecavBillStrike != null) {
			list = fecavAction.findBrikeBillOfExchange(new Request(CommonVars
					.getCurrUser()), fecavBillStrike);
		}
		initExchangeTable(list);
		this.refreshMoney();
	}

	private void showFecavBillAllData(FecavBillStrike fecavBillStrike) {
		this.showExportData(fecavBillStrike);
		this.showImportData(fecavBillStrike);
		this.showExchangeData(fecavBillStrike);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
