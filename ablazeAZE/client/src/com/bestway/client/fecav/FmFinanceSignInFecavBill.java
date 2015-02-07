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

public class FmFinanceSignInFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JSplitPane jSplitPane = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnNotStrike = null;

	private JPanel pnStrike = null;

	private JScrollPane jScrollPane = null;

	private JTable tbNotStrike = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbStrike = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane1 = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfSignInNo = null;

	private JTextField tfStrikeMan = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbStrikeImport = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbStrikeBillOfExchange = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel7 = null;

	private JLabel lbStrikeExchangeMoney = null;

	private JCalendarComboBox cbbStrikeDate = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JTableListModel strikeTableModel = null;

	private JTableListModel notStrikeTableModel = null;

	private JTableListModel expTableModel = null;

	private JTableListModel exchangeTableModel = null;

	private FecavAction fecavAction = null;

	private JButton btnSearch = null;

	private JCustomFormattedTextField tfCanStrikeTotalMoney = null;

	private JCustomFormattedTextField tfCanStrikeImportMoney = null;

	private JCustomFormattedTextField tfCanStrikeExchangeMoney = null;

	private JCustomFormattedTextField tfStrikeImportMoney = null;

	private JCustomFormattedTextField tfStrikeExchangeMoney = null;

	private JCustomFormattedTextField tfRemainMoney = null;

	private JButton btnSignIn = null;

	private JButton btnUndoSignIn = null;

	private JLabel lbStrikeExpMoney = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFinanceSignInFecavBill() {
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
		this.setTitle("出口外汇财务签收");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						if (jTabbedPane.getSelectedIndex() == 0) {
							List list = fecavAction
									.findFecavBillStrikeByCancel(new Request(
											CommonVars.getCurrUser()));
							initNotStrikeTable(list);
						} else if (jTabbedPane.getSelectedIndex() == 1) {
							List list = fecavAction.findFecavBillStrikeByState(
									new Request(CommonVars.getCurrUser()),
									FecavState.FINANCE_SIGN_IN);
							initStrikeTable(list);
						}
						// else if (jTabbedPane.getSelectedIndex() == 2) {
						// List list = fecavAction.findFecavBillStrikeByState(
						// new Request(CommonVars.getCurrUser()),
						// FecavState.CLOSE_ACCOUNT);
						// initCloseAccount(list);
						// }
						showFecavBillAllData(getCurrentFecavBillStrike());
					}
				});

	}

	// private void initCloseAccount(List list) {
	// JTableListModel tableModel = new JTableListModel(tbCloseAccount, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List<Object> list = (List<Object>) (new Vector());
	// list.add(addColumn("核销编码", "signInNo", 100));
	// list.add(addColumn("冲销者", "strikeMan", 100));
	// list.add(addColumn("冲销日期 ", "strikeDate", 200));
	// return list;
	// }
	// });
	// tbCloseAccount
	// .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
	// this.tfFieldValue, this.btnSearch, tableModel);
	// }

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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getBtnSignIn());
			jToolBar.add(getBtnUndoSignIn());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(8);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setRightComponent(getJSplitPane1());
			jSplitPane.setLeftComponent(getJTabbedPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jButton1
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
						List list = fecavAction.findFecavBillStrikeByState(
								new Request(CommonVars.getCurrUser()),
								FecavState.CANCEL);
						initNotStrikeTable(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						List list = fecavAction.findFecavBillStrikeByState(
								new Request(CommonVars.getCurrUser()),
								FecavState.FINANCE_SIGN_IN);
						initStrikeTable(list);
					}
					// else if (jTabbedPane.getSelectedIndex() == 2) {
					// List list = fecavAction.findFecavBillStrikeByState(
					// new Request(CommonVars.getCurrUser()),
					// FecavState.CLOSE_ACCOUNT);
					// initCloseAccount(list);
					// }
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
					FmFinanceSignInFecavBill.this.doDefaultCloseAction();
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
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("未签收", null, getPnNotStrike(), "");
			jTabbedPane.addTab("已签收", null, getPnStrike(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								List list = fecavAction
										.findFecavBillStrikeByState(
												new Request(CommonVars
														.getCurrUser()),
												FecavState.CANCEL);
								initNotStrikeTable(list);
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								List list = fecavAction
										.findFecavBillStrikeByState(
												new Request(CommonVars
														.getCurrUser()),
												FecavState.FINANCE_SIGN_IN);
								initStrikeTable(list);
							}
							// else if (jTabbedPane.getSelectedIndex() == 2) {
							// List list = fecavAction
							// .findFecavBillStrikeByState(
							// new Request(CommonVars
							// .getCurrUser()),
							// FecavState.CLOSE_ACCOUNT);
							// initCloseAccount(list);
							// }
							showFecavBillAllData(getCurrentFecavBillStrike());
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	private void setState() {
		int index = jTabbedPane.getSelectedIndex();
		this.btnUndoSignIn.setVisible(index == 1);
		this.btnSignIn.setVisible(index == 0);
		cbbStrikeDate.setEnabled(false);
		tfStrikeMan.setEnabled(false);
		// jTabbedPane1.setEnabledAt(0, false);
		// this.btnCancelCloseAccount.setEnabled(index == 2);
		// this.btnCloseAccount.setEnabled(index == 1);
		// if (jTabbedPane.getSelectedIndex() == 2) {
		// jLabel3.setText("关帐者");
		// jLabel.setText("关帐日期");
		// } else {
		// jLabel3.setText("签收者");
		// jLabel.setText("签收日期");
		// }

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnNotStrike() {
		if (pnNotStrike == null) {
			pnNotStrike = new JPanel();
			pnNotStrike.setLayout(new BorderLayout());
			pnNotStrike.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return pnNotStrike;
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
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbNotStrike());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbNotStrike() {
		if (tbNotStrike == null) {
			tbNotStrike = new JTable();
			tbNotStrike.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbNotStrike
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								if (tableModel.getCurrentRow() != null) {
									FecavBillStrike fecavBillStrike = (FecavBillStrike) tableModel
											.getCurrentRow();
									showFecavBillAllData(fecavBillStrike);
									// if (CommonVars.getCurrUser() != null) {
									// AclUser aclUser = (AclUser) CommonVars
									// .getCurrUser();
									// tfStrikeMan.setText(aclUser
									// .getLoginName());
									//
									// }
									// cbbStrikeDate.setDate(new Date());
								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbNotStrike;
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
								if (tableModel.getCurrentRow() != null) {
									FecavBillStrike fecavBillStrike = (FecavBillStrike) tableModel
											.getCurrentRow();
									showFecavBillAllData(fecavBillStrike);
								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbStrike;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerSize(6);
			jSplitPane1.setTopComponent(getJPanel());
			jSplitPane1.setBottomComponent(getJPanel3());
			jSplitPane1.setDividerLocation(150);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("剩余金额");
			jLabel11.setBounds(new java.awt.Rectangle(32, 116, 96, 24));
			jLabel10 = new JLabel();
			jLabel10.setText("汇票冲销金额");
			jLabel10.setBounds(new java.awt.Rectangle(296, 90, 83, 24));
			jLabel7 = new JLabel();
			jLabel7.setText("报关单冲销金额");
			jLabel7.setBounds(new java.awt.Rectangle(32, 90, 96, 24));
			jLabel5 = new JLabel();
			jLabel5.setText("待结汇金额");
			jLabel5.setBounds(new java.awt.Rectangle(296, 64, 83, 24));
			jLabel4 = new JLabel();
			jLabel4.setText("可用抵扣料值");
			jLabel4.setBounds(new java.awt.Rectangle(32, 64, 96, 24));
			jLabel3 = new JLabel();
			jLabel3.setText("签收者");
			jLabel3.setBounds(new java.awt.Rectangle(296, 12, 83, 24));
			jLabel2 = new JLabel();
			jLabel2.setText("核销编码");
			jLabel2.setBounds(new java.awt.Rectangle(32, 12, 96, 24));
			jLabel1 = new JLabel();
			jLabel1.setText("待核销总值");
			jLabel1.setBounds(new java.awt.Rectangle(296, 38, 83, 24));
			jLabel = new JLabel();
			jLabel.setText("签收日期");
			jLabel.setBounds(new java.awt.Rectangle(32, 38, 96, 24));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfSignInNo(), null);
			jPanel.add(getTfStrikeMan(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getCbbStrikeDate(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getTfCanStrikeTotalMoney(), null);
			jPanel.add(getTfCanStrikeImportMoney(), null);
			jPanel.add(getTfCanStrikeExchangeMoney(), null);
			jPanel.add(getTfStrikeImportMoney(), null);
			jPanel.add(getTfStrikeExchangeMoney(), null);
			jPanel.add(getTfRemainMoney(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane1.addTab("出口报关单", null, getJPanel1(), null);
			jTabbedPane1.addTab("汇票冲销", null, getJPanel2(), null);
			jTabbedPane1
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane1.getSelectedIndex() == 0) {
								// showImportData(getCurrentFecavBillStrike());
							} else {
								showExchangeData(getCurrentFecavBillStrike());
							}
						}
					});
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbStrikeExpMoney = new JLabel();
			lbStrikeExpMoney.setText("JLabel");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
			jPanel1.add(lbStrikeExpMoney, BorderLayout.SOUTH);
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
			lbStrikeExchangeMoney = new JLabel();
			lbStrikeExchangeMoney.setText("JLabel");
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
			jPanel2.add(lbStrikeExchangeMoney, java.awt.BorderLayout.SOUTH);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSignInNo() {
		if (tfSignInNo == null) {
			tfSignInNo = new JTextField();
			tfSignInNo.setBackground(java.awt.Color.white);
			tfSignInNo.setBounds(new java.awt.Rectangle(130, 12, 151, 24));
			tfSignInNo.setEditable(false);
		}
		return tfSignInNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStrikeMan() {
		if (tfStrikeMan == null) {
			tfStrikeMan = new JTextField();
			tfStrikeMan.setBackground(java.awt.Color.white);
			tfStrikeMan.setBounds(new java.awt.Rectangle(379, 12, 154, 24));
			tfStrikeMan.setEditable(false);
		}
		return tfStrikeMan;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			// jPanel3.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getJTabbedPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbStrikeImport());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbStrikeImport() {
		if (tbStrikeImport == null) {
			tbStrikeImport = new JTable();
		}
		return tbStrikeImport;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbStrikeBillOfExchange());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbStrikeBillOfExchange() {
		if (tbStrikeBillOfExchange == null) {
			tbStrikeBillOfExchange = new JTable();
		}
		return tbStrikeBillOfExchange;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbStrikeDate() {
		if (cbbStrikeDate == null) {
			cbbStrikeDate = new JCalendarComboBox();
			cbbStrikeDate.setForeground(java.awt.Color.black);
			cbbStrikeDate.setBounds(new java.awt.Rectangle(130, 38, 151, 24));
			cbbStrikeDate.setEnabled(false);
		}
		return cbbStrikeDate;
	}

	private void initNotStrikeTable(List list) {
		JTableListModel.dataBind(tbNotStrike, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("核销编码", "signInNo", 100));
						list.add(addColumn("冲销者", "strikeMan", 100));
						list.add(addColumn("冲销日期 ", "strikeDate", 200));
						return list;
					}
				});
		notStrikeTableModel = (JTableListModel) tbNotStrike.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.notStrikeTableModel);
	}

	private void initStrikeTable(List list) {
		JTableListModel.dataBind(tbStrike, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("核销编码", "signInNo", 100));
				list.add(addColumn("冲销者", "strikeMan", 100));
				list.add(addColumn("冲销日期 ", "strikeDate", 200));
				return list;
			}
		});
		strikeTableModel = (JTableListModel) tbStrike.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.strikeTableModel);
	}

	// private void initImportTable(List list) {
	// impTableModel = new JTableListModel(tbStrikeImport, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List<Object> list = (List<Object>) (new Vector());
	// list.add(addColumn("进口报关单号", "customsDeclarationCode",
	// 100));
	// list.add(addColumn("合同号", "contractNo", 100));
	// list.add(addColumn("手册编号 ", "emsNo", 200));
	// list.add(addColumn("报关单总金额", "totalMoney", 100));
	// list.add(addColumn("冲销金额", "strikeMoney", 200));
	// return list;
	// }
	// });
	// tbStrikeImport
	// .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	// }

	private void initExchangeTable(List list) {
		JTableListModel.dataBind(tbStrikeBillOfExchange, list,
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
		exchangeTableModel = (JTableListModel) tbStrikeBillOfExchange
				.getModel();
	}

	// private void showImportData(FecavBillStrike fecavBillStrike) {
	// List list = new ArrayList();
	// if (fecavBillStrike != null) {
	// list = fecavAction.findBrikeImpCustomsDeclaration(new Request(
	// CommonVars.getCurrUser()), fecavBillStrike);
	// double importMoney = this.fecavAction.findFecavStrikeImpMoney(
	// new Request(CommonVars.getCurrUser()), fecavBillStrike);
	// //
	// this.lbStrikeImpMoney.setText("已经冲销的进口报关单金额为:"+String.valueOf(importMoney));
	// } else {
	// // this.lbStrikeImpMoney.setText("已经冲销的进口报关单金额为:0");
	// }
	// // initImportTable(list);
	// }

	private void showExchangeData(FecavBillStrike fecavBillStrike) {
		List list = new ArrayList();
		if (fecavBillStrike != null) {
			list = fecavAction.findBrikeBillOfExchange(new Request(CommonVars
					.getCurrUser()), fecavBillStrike);
			double exchangeMoney = this.fecavAction
					.findFecavStrikeExchangeMoney(new Request(CommonVars
							.getCurrUser()), fecavBillStrike);
			this.lbStrikeExchangeMoney.setText("已经冲销的汇票金额为:"
					+ String.valueOf(exchangeMoney));
		} else {
			this.lbStrikeExchangeMoney.setText("已经冲销的汇票金额为:0");
		}
		initExchangeTable(list);
		this.refreshMoney();
	}

	private void showFecavBillData(FecavBillStrike fecavBillStrike) {
		if (fecavBillStrike != null) {
			this.tfSignInNo.setText(fecavBillStrike.getSignInNo());
			// if (jTabbedPane.getSelectedIndex() == 2) {
			// this.tfStrikeMan.setText(fecavBillStrike.getCloseAccountMan());
			// this.cbbStrikeDate.setDate(fecavBillStrike
			// .getCloseAccountDate());
			// jLabel3.setText("关帐者");
			// jLabel.setText("关帐日期");
			// } else {
			this.tfStrikeMan.setText(fecavBillStrike.getStrikeMan());
			this.cbbStrikeDate.setValue(fecavBillStrike.getStrikeDate());
			// jLabel3.setText("签收者");
			// jLabel.setText("签收日期");
			// }

			this.tfCanStrikeTotalMoney.setValue(fecavBillStrike
					.getCanStrikeTotalMoney());
			this.tfCanStrikeImportMoney.setValue(fecavBillStrike
					.getCanStrikeImportMoney());
			this.tfCanStrikeExchangeMoney.setValue(fecavBillStrike
					.getCanStrikeExchangeMoney());
			this.tfStrikeExchangeMoney.setValue(fecavBillStrike
					.getStrikedExchangeMoney());
			this.tfStrikeImportMoney.setValue(fecavBillStrike
					.getStrikedImportMoney());
			// this.tfRemainMoney.setValue(fecavBillStrike.getr());
		} else {
			this.tfSignInNo.setText("");
			this.tfStrikeMan.setText("");
			this.cbbStrikeDate.setValue(null);
			this.tfCanStrikeTotalMoney.setValue(null);
			this.tfCanStrikeImportMoney.setValue(null);
			this.tfCanStrikeExchangeMoney.setValue(null);
			this.tfStrikeExchangeMoney.setValue(null);
			this.tfStrikeImportMoney.setValue(null);
			// this.tfRemainMoney.setValue(fecavBillStrike.getr());
		}
	}

	private void showFecavBillAllData(FecavBillStrike fecavBillStrike) {
		showFecavBillData(fecavBillStrike);
		showExportData(fecavBillStrike);
		// showImportData(fecavBillStrike);
		showExchangeData(fecavBillStrike);
	}

	private FecavBillStrike getCurrentFecavBillStrike() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			if (notStrikeTableModel.getCurrentRow() != null) {
				return (FecavBillStrike) notStrikeTableModel.getCurrentRow();
			}
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			if (strikeTableModel.getCurrentRow() != null) {
				return (FecavBillStrike) strikeTableModel.getCurrentRow();
			}
		}
		// else if(jTabbedPane.getSelectedIndex() == 2){
		// JTableListModel model = (JTableListModel) tbCloseAccount
		// .getModel();
		// if (model.getCurrentRow() != null) {
		//				
		// return (FecavBillStrike) model.getCurrentRow();
		// }
		// }
		return null;
	}

	private void refreshMoney() {
		FecavBillStrike fecavBillStrike = getCurrentFecavBillStrike();
		if (fecavBillStrike == null) {
			if (jTabbedPane1.getSelectedIndex() == 0) {
				this.lbStrikeExpMoney.setText("出口报关单金额为:" + 0 + "  份数:" + 0);
			} else {
				this.lbStrikeExchangeMoney.setText("汇票金额为:" + 0 + "  份数:" + 0);
			}
			return;
		}
		fecavBillStrike = fecavAction.findFecavBillStrikeById(new Request(
				CommonVars.getCurrUser()), fecavBillStrike.getId());
		showFecavBillData(fecavBillStrike);
//		if (jTabbedPane.getSelectedIndex() == 0) {
//			if (notStrikeTableModel != null) {
//				notStrikeTableModel.updateRow(fecavBillStrike);
//			}
//		} else {
//			if (strikeTableModel != null) {
//				strikeTableModel.updateRow(fecavBillStrike);
//			}
//		}
		if (jTabbedPane1.getSelectedIndex() == 0) {
			double expMoney = 0.0;
			for (int i = 0; i < expTableModel.getList().size(); i++) {
				FecavBill fecavBill = (FecavBill) expTableModel.getList()
						.get(i);
				expMoney += CommonVars.formatDouble(fecavBill.getTotalPrice());
			}
			this.lbStrikeExpMoney.setText("出口报关单金额为:"
					+ CommonVars.formatDoubleToString(expMoney)
					+ "  份数:"
					+ (expTableModel == null ? 0 : expTableModel.getList()
							.size()));
			// double importMoney = this.fecavAction.findFecavStrikeImpMoney(
			// new Request(CommonVars.getCurrUser()), fecavBillStrike);
			// this.lbStrikeImpMoney.setText("已经冲销的进口报关单金额为:"+String.valueOf(importMoney));
		} else {
			double exchangeMoney = this.fecavAction
					.findFecavStrikeExchangeMoney(new Request(CommonVars
							.getCurrUser()), fecavBillStrike);
			this.lbStrikeExchangeMoney.setText("汇票金额为:"
					+ CommonVars.formatDoubleToString(exchangeMoney)
					+ "  份数:"
					+ (this.exchangeTableModel == null ? 0 : exchangeTableModel
							.getList().size()));
		}
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
						// FecavState.CANCEL);
						DgFecavBillStrikeQuery dg = new DgFecavBillStrikeQuery();
						dg.setFecavState(FecavState.CANCEL);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initNotStrikeTable(list);
						}
						// initNotStrikeTable(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
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
					}
					showFecavBillAllData(getCurrentFecavBillStrike());
					setState();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanStrikeTotalMoney() {
		if (tfCanStrikeTotalMoney == null) {
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(new NumberFormatter());
			tfCanStrikeTotalMoney = new JCustomFormattedTextField();
			tfCanStrikeTotalMoney.setBounds(new java.awt.Rectangle(379, 37,
					154, 24));
			tfCanStrikeTotalMoney.setFormatterFactory(defaultFormatterFactory);
			tfCanStrikeTotalMoney.setEditable(false);
		}
		return tfCanStrikeTotalMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanStrikeImportMoney() {
		if (tfCanStrikeImportMoney == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory1.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			tfCanStrikeImportMoney = new JCustomFormattedTextField();
			tfCanStrikeImportMoney.setBounds(new java.awt.Rectangle(130, 64,
					151, 24));
			tfCanStrikeImportMoney
					.setFormatterFactory(defaultFormatterFactory1);
			tfCanStrikeImportMoney.setEditable(false);
		}
		return tfCanStrikeImportMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanStrikeExchangeMoney() {
		if (tfCanStrikeExchangeMoney == null) {
			DefaultFormatterFactory defaultFormatterFactory2 = new DefaultFormatterFactory();
			defaultFormatterFactory2.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory2.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory2.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory2.setDisplayFormatter(new NumberFormatter());
			tfCanStrikeExchangeMoney = new JCustomFormattedTextField();
			tfCanStrikeExchangeMoney.setBounds(new java.awt.Rectangle(379, 63,
					154, 24));
			tfCanStrikeExchangeMoney
					.setFormatterFactory(defaultFormatterFactory2);
			tfCanStrikeExchangeMoney.setEditable(false);
		}
		return tfCanStrikeExchangeMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField3
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfStrikeImportMoney() {
		if (tfStrikeImportMoney == null) {
			DefaultFormatterFactory defaultFormatterFactory3 = new DefaultFormatterFactory();
			defaultFormatterFactory3.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory3.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory3.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory3.setDisplayFormatter(new NumberFormatter());
			tfStrikeImportMoney = new JCustomFormattedTextField();
			tfStrikeImportMoney.setBounds(new java.awt.Rectangle(130, 90, 151,
					24));
			tfStrikeImportMoney.setFormatterFactory(defaultFormatterFactory3);
			tfStrikeImportMoney.setEditable(false);
		}
		return tfStrikeImportMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField4
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfStrikeExchangeMoney() {
		if (tfStrikeExchangeMoney == null) {
			DefaultFormatterFactory defaultFormatterFactory4 = new DefaultFormatterFactory();
			defaultFormatterFactory4.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory4.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory4.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory4.setDisplayFormatter(new NumberFormatter());
			tfStrikeExchangeMoney = new JCustomFormattedTextField();
			tfStrikeExchangeMoney.setBounds(new java.awt.Rectangle(379, 89,
					154, 24));
			tfStrikeExchangeMoney.setFormatterFactory(defaultFormatterFactory4);
			tfStrikeExchangeMoney.setEditable(false);
		}
		return tfStrikeExchangeMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField5
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfRemainMoney() {
		if (tfRemainMoney == null) {
			DefaultFormatterFactory defaultFormatterFactory5 = new DefaultFormatterFactory();
			defaultFormatterFactory5.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory5.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory5.setDefaultFormatter(new NumberFormatter());
			defaultFormatterFactory5.setDisplayFormatter(new NumberFormatter());
			tfRemainMoney = new JCustomFormattedTextField();
			tfRemainMoney.setBounds(new java.awt.Rectangle(130, 116, 151, 24));
			tfRemainMoney.setFormatterFactory(defaultFormatterFactory5);
			tfRemainMoney.setEditable(false);
		}
		return tfRemainMoney;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSignIn() {
		if (btnSignIn == null) {
			btnSignIn = new JButton();
			btnSignIn.setText("签收");
			btnSignIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel model = (JTableListModel) tbNotStrike
							.getModel();
					if (model.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmFinanceSignInFecavBill.this, "请选择你要签收确认的数据",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = model.getCurrentRows();
					DgFinanceSignInFecavBill dg = new DgFinanceSignInFecavBill();
					dg.setLsFecavBill(list);
					dg.setVisible(true);
					List lsResult = dg.getLsResult();
					if (lsResult != null && lsResult.size() > 0) {
						model.deleteRows(list);
					}
				}
			});
		}
		return btnSignIn;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoSignIn() {
		if (btnUndoSignIn == null) {
			btnUndoSignIn = new JButton();
			btnUndoSignIn.setText("取消签收");
			btnUndoSignIn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (strikeTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmFinanceSignInFecavBill.this,
										"请选择你要取消签收的数据", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmFinanceSignInFecavBill.this,
									"确定要取消财务已签收的单据 ??", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							List list = strikeTableModel.getCurrentRows();
							List lsResult = fecavAction
									.undoFinanceSignInFecavBill(new Request(
											CommonVars.getCurrUser()), list);
							if (lsResult != null && lsResult.size() > 0) {
								// strikeTableModel.addRows(list);
								strikeTableModel.deleteRows(lsResult);
							}
						}
					});
		}
		return btnUndoSignIn;
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
		// this.lbStrikeImpMoney.setText("");
		this.initFecavBillTable(list);
		this.refreshMoney();
	}

	private void initFecavBillTable(List list) {
		JTableListModel.dataBind(this.tbStrikeImport, list,
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
				});
		this.expTableModel = (JTableListModel) tbStrikeImport.getModel();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
