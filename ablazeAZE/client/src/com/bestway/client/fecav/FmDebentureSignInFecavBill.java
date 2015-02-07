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
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmDebentureSignInFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane spNotSignIn = null;

	private JTable tbNotSignIn = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTableListModel notSignInTableModel = null;

	private JTableListModel signInTableModel = null;

	private FecavAction fecavAction = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnNotSignIn = null;

	private JPanel pnSignIn = null;

	private JScrollPane spSignIn = null;

	private JTable tbSignIn = null;

	private JButton btnSignInObtain = null;

	private JButton btnUndoSignInObtain = null;

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
	public FmDebentureSignInFecavBill() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initData();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(594, 365));
		this.setContentPane(getJContentPane());
		this.setTitle("核销单退税联签收");
//		this
//				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
//					public void internalFrameOpened(
//							javax.swing.event.InternalFrameEvent e) {
//						if (jTabbedPane.getSelectedIndex() == 0) {
//							//
//							// 状态为使用并且出口日期不为空的数据
//							//
//							List list = fecavAction.findFecavBillByUsedAfter(
//									new Request(CommonVars.getCurrUser()), "",
//									new ArrayList());
//							// List list = fecavAction.findFecavBillByState(
//							// new Request(CommonVars.getCurrUser()),
//							// FecavState.USED);
//							initNotSignInTable(list);
//							showCount(list);
//						} else if (jTabbedPane.getSelectedIndex() == 1) {
//							List list = fecavAction.findFecavBillByState(
//									new Request(CommonVars.getCurrUser()),
//									FecavState.DEBENTURE_SIGN_IN);
//							initSignInTable(list);
//							showCount(list);
//						}
//						setState();
//					}
//				});

	}

	private void initData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			//
			// 状态为使用并且出口日期不为空的数据
			//
			List list = fecavAction.findFecavBillByUsedAfter(new Request(
					CommonVars.getCurrUser()), "", new ArrayList());
			// List list = fecavAction.findFecavBillByState(
			// new Request(CommonVars.getCurrUser()),
			// FecavState.USED);
			initNotSignInTable(list);
			showCount(list);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			List list = fecavAction.findFecavBillByState(new Request(CommonVars
					.getCurrUser()), FecavState.DEBENTURE_SIGN_IN);
			initSignInTable(list);
			showCount(list);
		}
		setState();
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
			jToolBar.add(getBtnSignInObtain());
			jToolBar.add(getBtnUndoSignInObtain());
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
	private JScrollPane getSpNotSignIn() {
		if (spNotSignIn == null) {
			spNotSignIn = new JScrollPane();
			spNotSignIn.setViewportView(getTbNotSignIn());
		}
		return spNotSignIn;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbNotSignIn() {
		if (tbNotSignIn == null) {
			tbNotSignIn = new JTable();
		}
		return tbNotSignIn;
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
					FmDebentureSignInFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initNotSignInTable(List list) {
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
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel.dataBind(tbNotSignIn, list, jTableListModelAdapter);
		TableColumn column = tbNotSignIn.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		column = this.tbNotSignIn.getColumnModel().getColumn(11);
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
				case FecavState.USED:
					this.setText("已使用");
					break;
				}
				return this;
			}
		});
		notSignInTableModel = (JTableListModel) tbNotSignIn.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.notSignInTableModel);
	}

	private void initSignInTable(List list) {
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
		jTableListModelAdapter.setEditableColumn(1);
		JTableListModel.dataBind(tbSignIn, list, jTableListModelAdapter);
		TableColumn column = tbSignIn.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		column = this.tbSignIn.getColumnModel().getColumn(11);
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
					this.setText("退税联签收");
					break;
				}
				return this;
			}
		});
		signInTableModel = (JTableListModel) tbSignIn.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.signInTableModel);
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
			jTabbedPane.addTab("退税联未签收", null, getPnNotSignIn(), null);
			jTabbedPane.addTab("退税联签收", null, getPnSignIn(), null);
			jTabbedPane.setSelectedIndex(0);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							refresh();
						}
					});
		}
		return jTabbedPane;
	}

	private void refresh() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			List list = fecavAction.findFecavBillByUsedAfter(new Request(
					CommonVars.getCurrUser()), "", new ArrayList());
			initNotSignInTable(list);
			showCount(list);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			List list = fecavAction.findFecavBillByState(new Request(CommonVars
					.getCurrUser()), FecavState.DEBENTURE_SIGN_IN, "",
					new ArrayList());
			initSignInTable(list);
			showCount(list);
		}
		setState();
	}

	/**
	 * This method initializes pnOuter
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnNotSignIn() {
		if (pnNotSignIn == null) {
			lbCountfirst = new JLabel();
			lbCountfirst.setText("JLabel");
			lbCountfirst.setForeground(Color.blue);

			pnNotSignIn = new JPanel();
			pnNotSignIn.setLayout(new BorderLayout());
			pnNotSignIn.add(getSpNotSignIn(), java.awt.BorderLayout.CENTER);
			pnNotSignIn.add(lbCountfirst, java.awt.BorderLayout.SOUTH);
		}
		return pnNotSignIn;
	}

	/**
	 * This method initializes pnInner
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSignIn() {
		if (pnSignIn == null) {
			lbCountsec = new JLabel();
			lbCountsec.setText("JLabel");
			lbCountsec.setForeground(Color.blue);
			pnSignIn = new JPanel();
			pnSignIn.setLayout(new BorderLayout());
			pnSignIn.add(getSpSignIn(), java.awt.BorderLayout.CENTER);
			pnSignIn.add(lbCountsec, java.awt.BorderLayout.SOUTH);
		}
		return pnSignIn;
	}

	/**
	 * This method initializes spInner
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpSignIn() {
		if (spSignIn == null) {
			spSignIn = new JScrollPane();
			spSignIn.setViewportView(getTbSignIn());
		}
		return spSignIn;
	}

	/**
	 * This method initializes tbInner
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSignIn() {
		if (tbSignIn == null) {
			tbSignIn = new JTable();
		}
		return tbSignIn;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSignInObtain() {
		if (btnSignInObtain == null) {
			btnSignInObtain = new JButton();
			btnSignInObtain.setText("退税联签收");
			btnSignInObtain
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = new ArrayList();// notSignInTableModel.getCurrentRows();
							for (int i = 0; i < notSignInTableModel.getList()
									.size(); i++) {
								FecavBill bill = (FecavBill) notSignInTableModel
										.getList().get(i);
								if (bill.getIsSelected()) {
									list.add(bill);
								}
							}
							if (list.isEmpty()) {
								JOptionPane.showMessageDialog(
										FmDebentureSignInFecavBill.this,
										"请选择你要退税联签收的核销单", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgDebentureSignInFecavBill dg = new DgDebentureSignInFecavBill();
							dg.setLsFecavBill(list);
							dg.setVisible(true);
							List lsResult = dg.getLsResult();
							if (lsResult != null && lsResult.size() > 0) {
								notSignInTableModel.deleteRows(list);
								showCount(notSignInTableModel.getList());
								// signInTableModel.addRows(lsResult);
							}
						}

					});
		}
		return btnSignInObtain;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoSignInObtain() {
		if (btnUndoSignInObtain == null) {
			btnUndoSignInObtain = new JButton();
			btnUndoSignInObtain.setText("取消退税联签收");
			btnUndoSignInObtain
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = new ArrayList();// notSignInTableModel.getCurrentRows();
							for (int i = 0; i < signInTableModel.getList()
									.size(); i++) {
								FecavBill bill = (FecavBill) signInTableModel
										.getList().get(i);
								if (bill.getIsSelected()) {
									list.add(bill);
								}
							}
							if (list.isEmpty()) {
								JOptionPane.showMessageDialog(
										FmDebentureSignInFecavBill.this,
										"请选择你要取消退税联签收的核销单", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmDebentureSignInFecavBill.this,
									"确定要取消退税联已签收的核销单??", "提示",
									JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							// List list = signInTableModel.getCurrentRows();
							// for (int i = 0; i < list.size(); i++) {
							// FecavBill f = (FecavBill) list
							// .get(i);
							// f.setDrawbackSignInMan(null);
							// f.setDrawbackSignInDate(null);
							// f.setBillState(FecavState.USED);
							// }
							list = fecavAction
									.undoDebentureSignInFecavBill(new Request(
											CommonVars.getCurrUser()), list);
							if (list != null && list.size() > 0) {
								signInTableModel.deleteRows(list);
								showCount(notSignInTableModel.getList());
								// notSignInTableModel.addRows(lsResult);
							}
						}
					});
		}
		return btnUndoSignInObtain;
	}

	private void setState() {
		this.btnSignInObtain.setVisible(jTabbedPane.getSelectedIndex() == 0);
		this.btnUndoSignInObtain
				.setVisible(jTabbedPane.getSelectedIndex() == 1);
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
						DgFecavBillQuery dg = new DgFecavBillQuery();
						dg.setFecavState(FecavState.USED);
						dg.setBefore(false);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initNotSignInTable(list);
							showCount(list);
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						DgFecavBillQuery dg = new DgFecavBillQuery();
						dg.setFecavState(FecavState.DEBENTURE_SIGN_IN);
						dg.setBefore(true);
						dg.setVisible(true);
						if (dg.isOk()) {
							List list = dg.getLsResult();
							initSignInTable(list);
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
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0 ? notSignInTableModel == null
							: signInTableModel == null) {
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							jTabbedPane.getSelectedIndex() == 0 ? notSignInTableModel
									.getList()
									: signInTableModel.getList());
					InputStream reportStream = FmDebentureSignInFecavBill.class
							.getResourceAsStream("report/FecavBillReport.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("reportTitle", "外汇核销单退税联签收");
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
			List list = notSignInTableModel.getList();
			for (int i = 0; i < list.size(); i++) {
				FecavBill fecavBill = (FecavBill) list.get(i);
				fecavBill.setIsSelected(b);
			}
			notSignInTableModel.setList(list);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			List list = signInTableModel.getList();
			for (int i = 0; i < list.size(); i++) {
				FecavBill fecavBill = (FecavBill) list.get(i);
				fecavBill.setIsSelected(b);
			}
			signInTableModel.setList(list);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
