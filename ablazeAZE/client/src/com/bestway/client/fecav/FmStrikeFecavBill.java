package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FecavState;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.fecav.entity.StrikeBillOfExchange;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmStrikeFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JSplitPane jSplitPane = null;

	private JButton btnStrike = null;

	private JButton btnUndoStrike = null;

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

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbStrikeImport = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbStrikeBillOfExchange = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel7 = null;

	private JLabel lbStrikeImpMoney = null;

	private JLabel lbStrikeExchangeMoney = null;

	private JCalendarComboBox cbbStrikeDate = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JTableListModel strikeTableModel = null;

	private JTableListModel notStrikeTableModel = null;

	private JTableListModel impTableModel = null;

	private JTableListModel exchangeTableModel = null;

	private FecavAction fecavAction = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JCustomFormattedTextField tfCanStrikeTotalMoney = null;

	private JCustomFormattedTextField tfCanStrikeImportMoney = null;

	private JCustomFormattedTextField tfCanStrikeExchangeMoney = null;

	private JCustomFormattedTextField tfStrikeImportMoney = null;

	private JCustomFormattedTextField tfStrikeExchangeMoney = null;

	private JCustomFormattedTextField tfRemainStrikeImportMoney = null;

	private JPanel jPanel4 = null;

	private JButton btnEditMaster = null;

	private JButton btnUndo = null;

	private JButton btnSaveMaster = null;

	private int dataState = DataState.BROWSE;

	private JCustomFormattedTextField tfRemainStrikeExchangeMoney = null;

	private JLabel jLabel6 = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	/**
	 * This method initializes
	 * 
	 */
	public FmStrikeFecavBill() {
		super();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(726, 458));
		this.setContentPane(getJContentPane());
		this.setTitle("外汇核销单冲销");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						if (jTabbedPane.getSelectedIndex() == 0) {
							List list = fecavAction
									.findFecavBillStrikeControl(new Request(
											CommonVars.getCurrUser()));
							initNotStrikeTable(list);
						} else if (jTabbedPane.getSelectedIndex() == 1) {
							List list = fecavAction
									.findFecavBillStrikeBlance(new Request(
											CommonVars.getCurrUser()));
							initStrikeTable(list);
						}
						showFecavBillAllData(getCurrentFecavBillStrike());
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
			jToolBar.add(getBtnStrike());
			jToolBar.add(getBtnEditMaster());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnSaveMaster());
			jToolBar.add(getBtnUndoStrike());
			jToolBar.add(getBtnPrint());
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnStrike() {
		if (btnStrike == null) {
			btnStrike = new JButton();
			btnStrike.setText("冲销");
			btnStrike.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (notStrikeTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmStrikeFecavBill.this,
								"请选择你要冲销确认的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = notStrikeTableModel.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						FecavBillStrike fecavBillStrike = (FecavBillStrike) list
								.get(i);
						if (fecavBillStrike.getStrikedExchangeMoney() == null
								|| fecavBillStrike.getStrikedExchangeMoney() <= 0) {
							JOptionPane.showMessageDialog(
									FmStrikeFecavBill.this,
									"汇票冲销金额不能为空或不能小于等于零", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						List lsBrikeBillOfExchange = fecavAction
								.findBrikeBillOfExchange(new Request(CommonVars
										.getCurrUser()), fecavBillStrike);
						if (lsBrikeBillOfExchange.size() <= 0) {
							JOptionPane.showMessageDialog(
									FmStrikeFecavBill.this, "冲销的汇票数目不能为零",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					DgStrikeFecavBill dg = new DgStrikeFecavBill();
					dg.setLsFecavBill(list);
					dg.setVisible(true);
					List lsResult = dg.getLsResult();
					if (lsResult != null && lsResult.size() > 0) {
						notStrikeTableModel.deleteRows(list);
						strikeTableModel.addRows(lsResult);
						FecavBillStrike fecavBillStrike = (FecavBillStrike) notStrikeTableModel
								.getCurrentRow();
						showFecavBillAllData(fecavBillStrike);
					}
				}
			});
		}
		return btnStrike;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoStrike() {
		if (btnUndoStrike == null) {
			btnUndoStrike = new JButton();
			btnUndoStrike.setText("取消冲销");
			btnUndoStrike
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// JTableListModel model = (JTableListModel)
							// tbCancel
							// .getModel();
							if (strikeTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmStrikeFecavBill.this, "请选择你要取消冲销的数据",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmStrikeFecavBill.this, "确定要取消已冲销的核销单??",
									"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							List list = strikeTableModel.getCurrentRows();
							// for (int i = 0; i < list.size(); i++) {
							// FecavBillStrike f = (FecavBillStrike) list
							// .get(i);
							// f.setFecavState(FecavState.CONTROL);
							// f.setStrikeDate(null);
							// f.setStrikeMan(null);
							// }
							list = fecavAction
									.undoStrikeBalanceFecavBill(new Request(
											CommonVars.getCurrUser()), list);
							if (list.size() > 0) {
								strikeTableModel.deleteRows(list);
								FecavBillStrike fecavBillStrike = (FecavBillStrike) strikeTableModel
										.getCurrentRow();
								showFecavBillAllData(fecavBillStrike);
							}

						}
					});
		}
		return btnUndoStrike;
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
					FmStrikeFecavBill.this.doDefaultCloseAction();
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
			jTabbedPane.addTab("未冲销", null, getPnNotStrike(), "");
			jTabbedPane.addTab("已冲销", null, getPnStrike(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								List list = fecavAction
										.findFecavBillStrikeByState(
												new Request(CommonVars
														.getCurrUser()),
												FecavState.CONTROL);
								initNotStrikeTable(list);
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								List list = fecavAction
										.findFecavBillStrikeByState(
												new Request(CommonVars
														.getCurrUser()),
												FecavState.STRIKE_BALANCE);
								initStrikeTable(list);
							}
							showFecavBillAllData(getCurrentFecavBillStrike());
							setState();
						}
					});
		}
		return jTabbedPane;
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
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(296, 116, 81, 25));
			jLabel6.setText("汇票冲销余额");
			jLabel11 = new JLabel();
			jLabel11.setText("报关单冲销余额");
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
			jLabel3.setText("冲销者");
			jLabel3.setBounds(new java.awt.Rectangle(296, 12, 83, 24));
			jLabel2 = new JLabel();
			jLabel2.setText("核销编码");
			jLabel2.setBounds(new java.awt.Rectangle(32, 12, 96, 24));
			jLabel1 = new JLabel();
			jLabel1.setText("待核销总值");
			jLabel1.setBounds(new java.awt.Rectangle(296, 38, 83, 24));
			jLabel = new JLabel();
			jLabel.setText("冲销日期");
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
			jPanel.add(getTfRemainStrikeImportMoney(), null);
			jPanel.add(getTfRemainStrikeExchangeMoney(), null);
			jPanel.add(jLabel6, null);
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
			jTabbedPane1.addTab("进口报关单冲销", null, getJPanel1(), null);
			jTabbedPane1.addTab("汇票冲销", null, getJPanel2(), null);
			jTabbedPane1
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane1.getSelectedIndex() == 0) {
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbStrikeImpMoney = new JLabel();
			lbStrikeImpMoney.setText("JLabel");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
			jPanel1.add(lbStrikeImpMoney, java.awt.BorderLayout.SOUTH);
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
			jPanel3.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getJTabbedPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAdd());
			jToolBar1.add(getBtnDelete());
			jToolBar1.add(getBtnEdit());
		}
		return jToolBar1;
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
					FecavBillStrike fecavBillStrike = getCurrentFecavBillStrike();
					if (jTabbedPane1.getSelectedIndex() == 0
							&& fecavBillStrike != null) {
						List<ImpCustomsDeclaration> list = FecavQuery
								.getInstance()
								.findNotStrikeImpCustomsDeclaration(
										fecavBillStrike);
						if (list != null && list.size() > 0) {
							List lsResult = fecavAction
									.addStrikeImpCustomsDeclaration(
											new Request(CommonVars
													.getCurrUser()),
											fecavBillStrike, list);
							impTableModel.addRows(lsResult);
							refreshMoney();
						}
					} else if (jTabbedPane1.getSelectedIndex() == 1
							&& fecavBillStrike != null) {
						List list = FecavQuery.getInstance()
								.findNotBrikeBillOfExchange(fecavBillStrike);
						if (list != null && list.size() > 0) {
							list = fecavAction.addBrikeBillOfExchange(
									new Request(CommonVars.getCurrUser()),
									fecavBillStrike, list);
							exchangeTableModel.addRows(list);
							refreshMoney();
						}
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane1.getSelectedIndex() == 0) {
						if (impTableModel.getCurrentRows().size() < 1) {
							JOptionPane.showMessageDialog(
									FmStrikeFecavBill.this, "请选择你要修改的数据", "提示",
									JOptionPane.OK_OPTION);
							return;
						}
						StrikeImpCustomsDeclaration imp = (StrikeImpCustomsDeclaration) impTableModel
								.getCurrentRow();
						DgEditStrikeImport dg = new DgEditStrikeImport();
						dg.setStrikeImpCustomsDeclaration(imp);
						dg.setVisible(true);
						if (dg.isOk()) {
							imp = dg.getStrikeImpCustomsDeclaration();
							impTableModel.updateRow(imp);
							refreshMoney();
						}

					} else if (jTabbedPane1.getSelectedIndex() == 1) {
						if (exchangeTableModel.getCurrentRows().size() < 1) {
							JOptionPane.showMessageDialog(
									FmStrikeFecavBill.this, "请选择你要修改的数据", "提示",
									JOptionPane.OK_OPTION);
							return;
						}
						StrikeBillOfExchange exchange = (StrikeBillOfExchange) exchangeTableModel
								.getCurrentRow();
						DgEditStrikeExchange dg = new DgEditStrikeExchange();
						dg.setStrikeBillOfExchange(exchange);
						dg.setVisible(true);
						if (dg.isOk()) {
							exchange = dg.getStrikeBillOfExchange();
							exchangeTableModel.updateRow(exchange);
							refreshMoney();
						}

					}
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane1.getSelectedIndex() == 0) {
						if (impTableModel.getCurrentRows().size() < 1) {
							JOptionPane.showMessageDialog(
									FmStrikeFecavBill.this, "请选择你要删除的数据", "提示",
									JOptionPane.OK_OPTION);
							return;
						}
						if (JOptionPane.showConfirmDialog(
								FmStrikeFecavBill.this, "你确定要删除这些数据吗", "提示",
								JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						List list = impTableModel.getCurrentRows();
						if (list != null && list.size() > 0) {
							fecavAction
									.deleteBrikeImpCustomsDeclaration(
											new Request(CommonVars
													.getCurrUser()), list);
							impTableModel.deleteRows(list);
						}
					} else if (jTabbedPane1.getSelectedIndex() == 1) {
						if (exchangeTableModel.getCurrentRows().size() < 1) {
							JOptionPane.showMessageDialog(
									FmStrikeFecavBill.this, "请选择你要删除的数据", "提示",
									JOptionPane.OK_OPTION);
							return;
						}
						if (JOptionPane.showConfirmDialog(
								FmStrikeFecavBill.this, "你确定要删除这些数据吗", "提示",
								JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						List list = exchangeTableModel.getCurrentRows();
						if (list != null && list.size() > 0) {
							fecavAction.deleteBrikeBillOfExchange(new Request(
									CommonVars.getCurrUser()), list);
							exchangeTableModel.deleteRows(list);
						}
					}
					refreshMoney();
				}
			});
		}
		return btnDelete;
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

	// private void initCancel(List list) {
	// JTableListModel tableModel = new JTableListModel(tbCancel, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List<Object> list = (List<Object>) (new Vector());
	// list.add(addColumn("核销编码", "signInNo", 100));
	// list.add(addColumn("冲销者", "strikeMan", 100));
	// list.add(addColumn("冲销日期 ", "strikeDate", 200));
	// return list;
	// }
	// });
	// tbCancel
	// .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
	// this.tfFieldValue, this.btnSearch, tableModel);
	// }

	private void initImportTable(List list) {
		impTableModel = new JTableListModel(tbStrikeImport, list,
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
		tbStrikeImport
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void initExchangeTable(List list) {
		exchangeTableModel = new JTableListModel(tbStrikeBillOfExchange, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("汇票号码", "billOfExchangeCode", 100));
						list.add(addColumn("结汇日期", "endDate", 100));
						list.add(addColumn("币制 ", "curr.name", 100));
						list.add(addColumn("结汇金额 ", "exchangeMoney", 200));
						list.add(addColumn("冲销金额", "strikeMoney", 100));
						list.add(addColumn("折美元", "converUSDMoney", 200));
						return list;
					}
				});
		tbStrikeBillOfExchange
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void showImportData(FecavBillStrike fecavBillStrike) {
		List list = new ArrayList();
		if (fecavBillStrike != null) {
			list = fecavAction.findBrikeImpCustomsDeclaration(new Request(
					CommonVars.getCurrUser()), fecavBillStrike);
			double importMoney = this.fecavAction.findFecavStrikeImpMoney(
					new Request(CommonVars.getCurrUser()), fecavBillStrike);
			this.lbStrikeImpMoney.setText("已经冲销的进口报关单金额为:"
					+ String.valueOf(importMoney));
		} else {
			this.lbStrikeImpMoney.setText("已经冲销的进口报关单金额为:0");
		}
		initImportTable(list);
	}

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
	}

	private void showFecavBillData(FecavBillStrike fecavBillStrike) {
		if (fecavBillStrike != null) {
			this.tfSignInNo.setText(fecavBillStrike.getSignInNo());
			// if (jTabbedPane.getSelectedIndex() == 2) {
			// this.tfStrikeMan.setText(fecavBillStrike.getCancelMan());
			// this.cbbStrikeDate.setValue(fecavBillStrike.getCancelDate());
			// jLabel3.setText("核销者");
			// jLabel.setText("核销日期");
			// } else {
			this.tfStrikeMan.setText(fecavBillStrike.getStrikeMan());
			this.cbbStrikeDate.setValue(fecavBillStrike.getStrikeDate());
			// jLabel3.setText("冲销者");
			// jLabel.setText("冲销日期");
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
			this.tfRemainStrikeExchangeMoney.setValue(fecavBillStrike
					.getRemainStrikeExchangeMoney());
			this.tfRemainStrikeImportMoney.setValue(fecavBillStrike
					.getRemainStrikeImportMoney());
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
			this.tfRemainStrikeExchangeMoney.setValue(null);
			this.tfRemainStrikeImportMoney.setValue(null);
			// this.tfRemainMoney.setValue(fecavBillStrike.getr());
		}
	}

	private void fillFecavBillData(FecavBillStrike fecavBillStrike) {
		if (fecavBillStrike != null) {
			fecavBillStrike.setCanStrikeImportMoney(this.tfCanStrikeImportMoney
					.getValue() == null ? 0.0 : Double
					.parseDouble(this.tfCanStrikeImportMoney.getValue()
							.toString()));
			fecavBillStrike
					.setCanStrikeExchangeMoney(this.tfCanStrikeExchangeMoney
							.getValue() == null ? 0.0 : Double
							.parseDouble(this.tfCanStrikeExchangeMoney
									.getValue().toString()));
			fecavBillStrike.setCanStrikeTotalMoney(this.tfCanStrikeTotalMoney
					.getValue() == null ? 0.0 : Double
					.parseDouble(this.tfCanStrikeTotalMoney.getValue()
							.toString()));

			if (this.tfStrikeExchangeMoney.getValue() != null) {
				fecavBillStrike.setStrikedExchangeMoney(Double
						.valueOf(this.tfStrikeExchangeMoney.getValue()
								.toString()));
			} else {
				fecavBillStrike.setStrikedExchangeMoney(null);
			}
			if (this.tfStrikeImportMoney.getValue() != null) {
				fecavBillStrike
						.setStrikedImportMoney(Double
								.valueOf(this.tfStrikeImportMoney.getValue()
										.toString()));
			} else {
				fecavBillStrike.setStrikedImportMoney(null);
			}
		}
	}

	private void showFecavBillAllData(FecavBillStrike fecavBillStrike) {
		showFecavBillData(fecavBillStrike);
		showImportData(fecavBillStrike);
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
		return null;
	}

	private void setState() {
		btnStrike.setVisible(jTabbedPane.getSelectedIndex() == 0);
		btnUndoStrike.setVisible(jTabbedPane.getSelectedIndex() == 1);
		btnAdd.setEnabled(jTabbedPane.getSelectedIndex() == 0);
		btnEdit.setEnabled(jTabbedPane.getSelectedIndex() == 0);
		btnDelete.setEnabled(jTabbedPane.getSelectedIndex() == 0);

		btnEditMaster.setVisible(jTabbedPane.getSelectedIndex() == 0);
		btnSaveMaster.setVisible(jTabbedPane.getSelectedIndex() == 0);
		btnUndo.setVisible(jTabbedPane.getSelectedIndex() == 0);

		tfStrikeImportMoney.setEditable(dataState != DataState.BROWSE);
		tfStrikeExchangeMoney.setEditable(dataState != DataState.BROWSE);
		tfCanStrikeImportMoney.setEditable(dataState != DataState.BROWSE);
		tfCanStrikeExchangeMoney.setEditable(dataState != DataState.BROWSE);
		btnEditMaster.setEnabled(dataState == DataState.BROWSE);
		btnSaveMaster.setEnabled(dataState != DataState.BROWSE);
		btnUndo.setEnabled(dataState != DataState.BROWSE);
		btnStrike.setEnabled(dataState == DataState.BROWSE);
	}

	private void refreshMoney() {
		FecavBillStrike fecavBillStrike = getCurrentFecavBillStrike();
		fecavBillStrike = fecavAction.findFecavBillStrikeById(new Request(
				CommonVars.getCurrUser()), fecavBillStrike.getId());
		showFecavBillData(fecavBillStrike);
		if (jTabbedPane.getSelectedIndex() == 0) {
			notStrikeTableModel.updateRow(fecavBillStrike);
		} else {
			strikeTableModel.updateRow(fecavBillStrike);
		}
		if (jTabbedPane1.getSelectedIndex() == 0) {
			double importMoney = this.fecavAction.findFecavStrikeImpMoney(
					new Request(CommonVars.getCurrUser()), fecavBillStrike);
			this.lbStrikeImpMoney.setText("已经冲销的进口报关单金额为:"
					+ CommonVars.formatDoubleToString(importMoney));
		} else {
			double exchangeMoney = this.fecavAction
					.findFecavStrikeExchangeMoney(new Request(CommonVars
							.getCurrUser()), fecavBillStrike);
			this.lbStrikeExchangeMoney.setText("已经冲销的汇票金额为:"
					+ CommonVars.formatDoubleToString(exchangeMoney));
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
						// .findFecavBillStrikeByState(new Request(
						// CommonVars.getCurrUser(), true),
						// FecavState.CONTROL);
						// initNotStrikeTable(list);
						DgFecavBillStrikeQuery dg = new DgFecavBillStrikeQuery();
						dg.setFecavState(FecavState.CONTROL);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initNotStrikeTable(list);
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						// List list = fecavAction
						// .findFecavBillStrikeByState(new Request(
						// CommonVars.getCurrUser(), true),
						// FecavState.STRIKE_BALANCE);
						DgFecavBillStrikeQuery dg = new DgFecavBillStrikeQuery();
						dg.setFecavState(FecavState.STRIKE_BALANCE);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initStrikeTable(list);
						}
					}
					showFecavBillAllData(getCurrentFecavBillStrike());
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
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印进口报关核查清单");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FecavBillStrike f = getCurrentFecavBillStrike();
					if (f == null) {
						JOptionPane.showMessageDialog(FmStrikeFecavBill.this,
								"请选择你要打印核销单批次", "提示", JOptionPane.OK_OPTION);
						return;
					}
					DgPrintImportFacavBill dg = new DgPrintImportFacavBill();
					dg.setFecavBillStrike(f);
					dg.setVisible(true);
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanStrikeTotalMoney() {
		if (tfCanStrikeTotalMoney == null) {
			tfCanStrikeTotalMoney = new JCustomFormattedTextField();
			tfCanStrikeTotalMoney.setBounds(new java.awt.Rectangle(379, 37,
					154, 24));
			tfCanStrikeTotalMoney
					.setFormatterFactory(getDefaultFormatterFactory());
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
			tfCanStrikeImportMoney = new JCustomFormattedTextField();
			tfCanStrikeImportMoney.setBounds(new java.awt.Rectangle(130, 64,
					151, 24));
			tfCanStrikeImportMoney
					.setFormatterFactory(getDefaultFormatterFactory());
			tfCanStrikeImportMoney.setEditable(false);
			tfCanStrikeImportMoney.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}

							try {
								tfCanStrikeImportMoney.commitEdit();
							} catch (ParseException e1) {
							}
							settfCanStrikeExchangeMoneyValue();
						}

						public void removeUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}
							try {
								tfCanStrikeImportMoney.commitEdit();
							} catch (ParseException e1) {
							}
							settfCanStrikeExchangeMoneyValue();
						}

						public void changedUpdate(DocumentEvent e) {

						}
					});
		}
		return tfCanStrikeImportMoney;
	}

	private void settfCanStrikeExchangeMoneyValue() {
		Double itotalmoney = (this.tfCanStrikeTotalMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfCanStrikeTotalMoney.getValue()
						.toString()));
		Double importmoney = (this.tfCanStrikeImportMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfCanStrikeImportMoney.getValue()
						.toString()));
		// Double outportmoney = (this.tfCanStrikeExchangeMoney.getValue() ==
		// null?0.0:Double.valueOf(this.tfCanStrikeExchangeMoney.getValue().toString()));

		Double outportmoney = itotalmoney - importmoney;

		this.tfCanStrikeExchangeMoney.setValue(outportmoney);
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanStrikeExchangeMoney() {
		if (tfCanStrikeExchangeMoney == null) {
			tfCanStrikeExchangeMoney = new JCustomFormattedTextField();
			tfCanStrikeExchangeMoney.setBounds(new java.awt.Rectangle(379, 63,
					154, 24));
			tfCanStrikeExchangeMoney
					.setFormatterFactory(getDefaultFormatterFactory());
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
			tfStrikeImportMoney = new JCustomFormattedTextField();
			tfStrikeImportMoney.setBounds(new java.awt.Rectangle(130, 90, 151,
					24));
			tfStrikeImportMoney
					.setFormatterFactory(getDefaultFormatterFactory());
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
			tfStrikeExchangeMoney = new JCustomFormattedTextField();
			tfStrikeExchangeMoney.setBounds(new java.awt.Rectangle(379, 89,
					154, 24));
			tfStrikeExchangeMoney
					.setFormatterFactory(getDefaultFormatterFactory());
			tfStrikeExchangeMoney.setEditable(false);
		}
		return tfStrikeExchangeMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField5
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfRemainStrikeImportMoney() {
		if (tfRemainStrikeImportMoney == null) {
			tfRemainStrikeImportMoney = new JCustomFormattedTextField();
			tfRemainStrikeImportMoney.setBounds(new java.awt.Rectangle(130,
					116, 151, 24));
			tfRemainStrikeImportMoney
					.setFormatterFactory(getDefaultFormatterFactory());
			tfRemainStrikeImportMoney.setEditable(false);
		}
		return tfRemainStrikeImportMoney;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditMaster() {
		if (btnEditMaster == null) {
			btnEditMaster = new JButton();
			btnEditMaster.setText("修改");
			btnEditMaster
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (notStrikeTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmStrikeFecavBill.this, "请选择要修改的数据",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							dataState = DataState.EDIT;
							setState();
						}
					});
		}
		return btnEditMaster;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FecavBillStrike fecavBillStrike = (FecavBillStrike) notStrikeTableModel
							.getCurrentRow();
					showFecavBillData(fecavBillStrike);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveMaster() {
		if (btnSaveMaster == null) {
			btnSaveMaster = new JButton();
			btnSaveMaster.setText("保存");
			btnSaveMaster
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							double canStrikeTotal = (tfCanStrikeTotalMoney
									.getValue() == null ? 0 : Double
									.valueOf(tfCanStrikeTotalMoney.getValue()
											.toString()));
							double canStrikeImport = (tfCanStrikeImportMoney
									.getValue() == null ? 0 : Double
									.valueOf(tfCanStrikeImportMoney.getValue()
											.toString()));
							double canStrikeExchange = (tfCanStrikeExchangeMoney
									.getValue() == null ? 0 : Double
									.valueOf(tfCanStrikeExchangeMoney
											.getValue().toString()));
							double strikeImport = (tfStrikeImportMoney
									.getValue() == null ? 0 : Double
									.valueOf(tfStrikeImportMoney.getValue()
											.toString()));
							double strikeExchange = (tfStrikeExchangeMoney
									.getValue() == null ? 0 : Double
									.valueOf(tfStrikeExchangeMoney.getValue()
											.toString()));
							if (canStrikeImport < 0) {
								JOptionPane.showMessageDialog(
										FmStrikeFecavBill.this, "可用抵扣料值不能小于零",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (canStrikeExchange < 0) {
								JOptionPane.showMessageDialog(
										FmStrikeFecavBill.this, "待结汇金额不能小于零",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (strikeImport < 0) {
								JOptionPane.showMessageDialog(
										FmStrikeFecavBill.this, "报关单冲销金额不能小于零",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (strikeExchange < 0) {
								JOptionPane.showMessageDialog(
										FmStrikeFecavBill.this, "汇票冲销金额不能小于零",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if ((canStrikeImport + canStrikeExchange) > canStrikeTotal) {
								if (JOptionPane.showConfirmDialog(
										FmStrikeFecavBill.this,
										"可用抵扣料值+待结汇金额不能大于待核销总值，你确定要保存吗", "提示",
										JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION){
									return;
								}
							}
							if (strikeImport > canStrikeImport) {
								if (JOptionPane.showConfirmDialog(
										FmStrikeFecavBill.this,
										"报关单冲销金额不能大于可用抵扣料值，你确定要保存吗", "提示",
										JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION){
									return;
								}
							}

							if (strikeExchange > canStrikeExchange) {
								if (JOptionPane.showConfirmDialog(
										FmStrikeFecavBill.this,
										"汇票冲销金额不能大于待结汇金额，你确定要保存吗", "提示",
										JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION){
									return;
								}
							}
							FecavBillStrike fecavBillStrike = (FecavBillStrike) notStrikeTableModel
									.getCurrentRow();
							fillFecavBillData(fecavBillStrike);
							fecavAction.saveFecavBillStrike(new Request(
									CommonVars.getCurrUser()), fecavBillStrike);
							notStrikeTableModel.updateRow(fecavBillStrike);
							showFecavBillData(fecavBillStrike);
							dataState = DataState.BROWSE;
							setState();
						}
					});
		}
		return btnSaveMaster;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfRemainStrikeExchangeMoney() {
		if (tfRemainStrikeExchangeMoney == null) {
			tfRemainStrikeExchangeMoney = new JCustomFormattedTextField();
			tfRemainStrikeExchangeMoney.setBounds(new java.awt.Rectangle(381,
					115, 152, 27));
			tfRemainStrikeExchangeMoney
					.setFormatterFactory(getDefaultFormatterFactory());
			tfRemainStrikeExchangeMoney.setEditable(false);
		}
		return tfRemainStrikeExchangeMoney;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			FecavParameters fecavParameters = fecavAction
					.findFecavParameters(new Request(CommonVars.getCurrUser(),
							true));
			if (fecavParameters == null
					|| fecavParameters.getFecavControlDigitsNum() == null) {
				decimalFormat.setMaximumFractionDigits(0);
			} else {
				decimalFormat.setMaximumFractionDigits(fecavParameters
						.getFecavControlDigitsNum());
			}
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
