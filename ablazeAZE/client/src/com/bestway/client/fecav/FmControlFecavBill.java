package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FecavState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.TempCustomsEmsInfo;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JFormattedTextField;

public class FmControlFecavBill extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTableListModel fecavBillTableModel = null;

	private JTableListModel signInTableModel = null;

	private FecavAction fecavAction = null;

	private JScrollPane spSignIn = null;

	private JTable tbSignIn = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	private JSplitPane jSplitPane = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar1 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbFecavBill = null;

	private JButton btnAddDetail = null;

	private JButton btnDeleteDetail = null;

	private JLabel jLabel = null;

	private JTextField tfSignInNo = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JTextField tfSignInMan = null;

	private JCalendarComboBox cbbSignInDate = null;

	private JCustomFormattedTextField tfCanStrikeTotalMoney = null;

	private JCustomFormattedTextField tfCanStrikeImportMoney = null;

	private JCustomFormattedTextField tfCanStrikeExchangeMoney = null;

	private JComboBox cbbCurr = null;

	private JButton btnEdit = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private FecavBillStrike fecavBillStrike = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private int dataState = DataState.BROWSE;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JCustomFormattedTextField tfPieces = null;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private JLabel lbCount = null;

	private JLabel jLabel9 = null;

	private JComboBox cbbTrade = null;

	private JLabel jLabel10 = null;

	private JComboBox cbbProjectType = null;

	private JLabel lbEmsNo = null;

	private JComboBox cbbEmsNo = null;

	private JLabel jLabel11 = null;

	private JFormattedTextField tfStrikeExchangeRate = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	// private TransferFactoryManageAction fptManageAction = null;
	/**
	 * This method initializes
	 * 
	 */
	public FmControlFecavBill() {
		super();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initialize();
		// this.fptManageAction = (TransferFactoryManageAction)
		// CommonVars
		// .getApplicationContext().getBean(
		// "fptManageAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(694, 436));
		this.setContentPane(getJContentPane());
		this.setTitle("出口收汇管制");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {

						refresh();

					}
				});

	}

	private void refresh() {
		List list = fecavAction.findFecavBillStrikeByState(new Request(
				CommonVars.getCurrUser()), FecavState.CONTROL);
		initSignInTable(list);
		showDetailData();
		setState();
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
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
					FmControlFecavBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initFecavBillTable(List list) {
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
				list.add(addColumn("折美元", "converUSDMoney", 100));
				return list;
			}

		};
		JTableListModel
				.dataBind(this.tbFecavBill, list, jTableListModelAdapter);
		fecavBillTableModel = (JTableListModel) tbFecavBill.getModel();
		// fecavBillTableModel.setAllowSetValue(true);
		jTableListModelAdapter.setEditableColumn(1);
		TableColumn column = tbFecavBill.getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(new CheckBoxEditor(new JCheckBox()));
		// column.setCellEditor(new DefaultCellEditor(new JCheckBox()));
		// column = this.tbFecavBill.getColumnModel().getColumn(11);
		// column.setCellRenderer(new DefaultTableCellRenderer() {
		// // JLabel label = new JLabel();
		// public Component getTableCellRendererComponent(JTable table,
		// Object value, boolean isSelected, boolean hasFocus,
		// int row, int column) {
		// super.getTableCellRendererComponent(table, value, isSelected,
		// hasFocus, row, column);
		// int state = -1;
		// if (value != null) {
		// state = Integer.parseInt(value.toString());
		// }
		// switch (state) {
		// case FecavState.DEBENTURE_SIGN_IN:
		// this.setText("退税联签收");
		// break;
		// }
		// return this;
		// }
		// });

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
		this.lbCount.setText(" 总份数: " + list.size() + "         已选份数: "
				+ isselected + "           统计金额："
				+ CommonVars.formatDoubleToString(total, 999, 6));
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
			return cb;
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

	private void initSignInTable(List list) {
		JTableListModel.dataBind(tbSignIn, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("核销编码", "signInNo", 100));
				list.add(addColumn("签收者", "cavSignInMan", 100));
				list.add(addColumn("签收日期 ", "cavSignInDate", 200));
				return list;
			}
		});
		signInTableModel = (JTableListModel) tbSignIn.getModel();
		// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
		// this.tfFieldValue, this.btnSearch, this.signInTableModel);
	}

	// private void initTbCancel(List list) {
	// JTableListModel tableModel = new JTableListModel(tbCancel, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List<Object> list = new Vector<Object>();
	// list.add(addColumn("签收编码", "signInNo", 100));
	// list.add(addColumn("签收者", "cavSignInMan", 100));
	// list.add(addColumn("签收日期 ", "cavSignInDate", 200));
	//
	// return list;
	// }
	// });
	// CommonQuery.getInstance().addCommonFilter(this.cbbSearchField,
	// this.tfFieldValue, this.btnSearch, tableModel);
	// }

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
			tbSignIn.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbSignIn
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								fecavBillStrike = (FecavBillStrike) tableModel
										.getCurrentRow();
								// System.out.println("-----------table
								// ListSelection");
								showAllData();
							} catch (Exception cx) {

							}
						}
					});
		}
		return tbSignIn;
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
					dataState = DataState.ADD;
					showEmptyData();
					addInitData();
					setState();
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
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (signInTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"请选择你要删除核销签收的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmControlFecavBill.this,
							"你确定要删除这些数据吗", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					fecavBillStrike = (FecavBillStrike) signInTableModel
							.getCurrentRow();
					fecavAction.deleteFecavBillStrike(new Request(CommonVars
							.getCurrUser()), fecavBillStrike);

					signInTableModel.deleteRow(fecavBillStrike);
					fecavBillStrike = (FecavBillStrike) signInTableModel
							.getCurrentRow();
					showAllData();
				}
			});
		}
		return btnDelete;
	}

	private void setState() {
		// if (fecavBillStrike == null) {
		// return;
		// }
		this.btnAdd.setEnabled(dataState == DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE
				&& fecavBillStrike != null);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnCancel.setEnabled(dataState != DataState.BROWSE);
		this.btnDelete.setEnabled(dataState == DataState.BROWSE
				&& fecavBillStrike != null);
		this.btnRefresh.setEnabled(dataState == DataState.BROWSE);
		this.btnPrint.setEnabled(dataState == DataState.BROWSE
				&& fecavBillStrike != null);
		this.btnExit.setEnabled(dataState == DataState.BROWSE);
		this.btnSearch.setEnabled(dataState == DataState.BROWSE);
		this.btnAddDetail.setEnabled(dataState == DataState.BROWSE
				&& fecavBillStrike != null);
		// && (fecavBillStrike.getStrikedImportMoney() == null ||
		// fecavBillStrike
		// .getStrikedImportMoney() <= 0)
		// && (fecavBillStrike.getStrikedExchangeMoney() == null ||
		// fecavBillStrike
		// .getStrikedExchangeMoney() <= 0)
		this.btnDeleteDetail.setEnabled(dataState == DataState.BROWSE
				&& fecavBillStrike != null);

		// && (fecavBillStrike.getStrikedImportMoney() == null ||
		// fecavBillStrike
		// .getStrikedImportMoney() <= 0)
		// && (fecavBillStrike.getStrikedExchangeMoney() == null ||
		// fecavBillStrike
		// .getStrikedExchangeMoney() <= 0)
		this.cbbTrade.setEnabled(dataState != DataState.BROWSE
				&& fecavBillTableModel.getList().size() <= 0);
		// this.tfSignInNo.setEnabled(dataState != DataState.BROWSE);
		this.tfSignInMan.setEnabled(dataState != DataState.BROWSE
				&& fecavBillStrike != null);
		this.cbbSignInDate.setEnabled(dataState != DataState.BROWSE
				&& fecavBillStrike != null);
		this.tfCanStrikeTotalMoney.setEnabled(dataState == DataState.ADD
				|| (dataState == DataState.EDIT && fecavBillStrike != null));
		// && (fecavBillStrike.getStrikedImportMoney() == null ||
		// fecavBillStrike
		// .getStrikedImportMoney() <= 0) && (fecavBillStrike
		// .getStrikedExchangeMoney() == null || fecavBillStrike
		// .getStrikedExchangeMoney() <= 0))
		this.tfCanStrikeImportMoney.setEnabled(dataState == DataState.ADD
				|| (dataState == DataState.EDIT && fecavBillStrike != null));
		// && (fecavBillStrike.getStrikedImportMoney() == null ||
		// fecavBillStrike
		// .getStrikedImportMoney() <= 0) && (fecavBillStrike
		// .getStrikedExchangeMoney() == null || fecavBillStrike
		// .getStrikedExchangeMoney() <= 0))
		this.tfCanStrikeExchangeMoney.setEnabled(dataState == DataState.ADD
				|| (dataState == DataState.EDIT && fecavBillStrike != null));
		this.tfStrikeExchangeRate.setEnabled(dataState == DataState.ADD
				|| (dataState == DataState.EDIT && fecavBillStrike != null));
		this.tfPieces.setEnabled(dataState == DataState.ADD
				|| (dataState == DataState.EDIT && fecavBillStrike != null));
		this.cbbEmsNo.setEnabled(dataState != DataState.BROWSE
				&& fecavBillTableModel.getList().size() <= 0);
		this.cbbProjectType.setEnabled(dataState != DataState.BROWSE
				&& fecavBillTableModel.getList().size() <= 0);
		this.tbSignIn.setEnabled(dataState == DataState.BROWSE);
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
					DgFecavBillStrikeQuery dg = new DgFecavBillStrikeQuery();
					dg.setFecavState(FecavState.CONTROL);
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = dg.getLsResult();
						initSignInTable(list);
						showDetailData();
						setState();
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
					DgPrintExportFacavBill dg = new DgPrintExportFacavBill();
					dg.setVisible(true);
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(150);
			jSplitPane.setDividerSize(8);
			jSplitPane.setRightComponent(getJSplitPane1());
			jSplitPane.setLeftComponent(getSpSignIn());
		}
		return jSplitPane;
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
			jSplitPane1.setBottomComponent(getJPanel1());
			jSplitPane1.setDividerLocation(180);
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
			lbEmsNo = new JLabel();
			lbEmsNo.setBounds(new java.awt.Rectangle(37, 118, 86, 21));
			lbEmsNo.setText("\u624b\u518c\u7f16\u53f7");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new java.awt.Rectangle(416, 37, 77, 23));
			jLabel13.setText(">0并且<=100");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(401, 37, 14, 24));
			jLabel12.setText(" %");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(279, 39, 70, 19));
			jLabel11.setText("应收汇比例");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(37, 92, 86, 19));
			jLabel10.setText("\u9879\u76ee\u7c7b\u578b");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(37, 144, 72, 18));
			jLabel9.setText("贸易方式");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(279, 140, 67, 22));
			jLabel8.setText("核销单份数");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(267, 117, 87, 18));
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel6.setText("折合币别(美元)");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(279, 90, 72, 19));
			jLabel5.setText("结汇值");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(279, 64, 58, 19));
			jLabel4.setText("物料值");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(37, 65, 72, 19));
			jLabel3.setText("核销值");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(37, 39, 86, 19));
			jLabel2.setText("核销日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(279, 13, 72, 19));
			jLabel1.setText("核销者");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(37, 13, 86, 19));
			jLabel.setText("核销编码");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfSignInNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfSignInMan(), null);
			jPanel.add(getCbbSignInDate(), null);
			jPanel.add(getTfCanStrikeTotalMoney(), null);
			jPanel.add(getTfCanStrikeImportMoney(), null);
			jPanel.add(getTfCanStrikeExchangeMoney(), null);
			jPanel.add(getCbbCurr(), null);
			jPanel.add(jLabel8, null);
			jPanel.add(getTfPieces(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getCbbTrade(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(getCbbProjectType(), null);
			jPanel.add(lbEmsNo, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getTfStrikeExchangeRate(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(getCbbEmsNo(), null);
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
			lbCount = new JLabel();
			lbCount.setText("JLabel");
			lbCount.setForeground(Color.blue);

			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel1.add(lbCount, java.awt.BorderLayout.SOUTH);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("新增的是已交单的外汇核销单");
			jLabel7.setForeground(java.awt.Color.blue);
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAddDetail());
			jToolBar1.add(getBtnDeleteDetail());
			jToolBar1.add(getBtnSelectAll());
			jToolBar1.add(getBtnNotSelectAll());
			jToolBar1.add(jLabel7);
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbFecavBill());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFecavBill() {
		if (tbFecavBill == null) {
			tbFecavBill = new JTable();
		}
		return tbFecavBill;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddDetail() {
		if (btnAddDetail == null) {
			btnAddDetail = new JButton();
			btnAddDetail.setText("新增");
			btnAddDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbProjectType.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"项目类型不能为空,请选择", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (cbbEmsNo.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								lbEmsNo.getText() + "不能为空,请选择", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (cbbTrade.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"贸易方式不能为空,请选择", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (fecavBillStrike == null) {
						return;
					}
					Integer itemCount = null;
					if (tfPieces.getValue() != null) {
						itemCount = Integer.valueOf(tfPieces.getValue()
								.toString());
					}
					List list = FecavQuery.getInstance()
							.findFecavBillNotStrike(fecavBillStrike, itemCount);
					if (list != null && list.size() > 0) {
						list = fecavAction.addFecavBillForBrike(new Request(
								CommonVars.getCurrUser()), fecavBillStrike,
								list);
						fecavBillTableModel.addRows(list);
						showCount(fecavBillTableModel.getList());
						refreshHeadMoney();
						setState();
					}
					// }
				}
			});
		}
		return btnAddDetail;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteDetail() {
		if (btnDeleteDetail == null) {
			btnDeleteDetail = new JButton();
			btnDeleteDetail.setText("删除");
			btnDeleteDetail
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (fecavBillStrike == null) {
								return;
							}
							if (tbFecavBill.getCellEditor() != null) {
								tbFecavBill.getCellEditor().stopCellEditing();
							}
							List list = new ArrayList();// notSignInTableModel.getCurrentRows();
							for (int i = 0; i < fecavBillTableModel.getList()
									.size(); i++) {
								FecavBill bill = (FecavBill) fecavBillTableModel
										.getList().get(i);
								if (bill.getIsSelected()) {
									list.add(bill);
								}
							}
							if (list.isEmpty()) {
								JOptionPane.showMessageDialog(
										FmControlFecavBill.this, "请选择你要删除核销单",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmControlFecavBill.this, "你确定要删除这些数据吗",
									"提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
								return;
							}
							fecavAction.removeFecavBillForBrike(new Request(
									CommonVars.getCurrUser()), fecavBillStrike,
									list);
							fecavBillTableModel.deleteRows(list);
							showCount(fecavBillTableModel.getList());
							refreshHeadMoney();
							setState();
						}
					});
		}
		return btnDeleteDetail;
	}

	private void refreshHeadMoney() {
		fecavBillStrike = fecavAction.findFecavBillStrikeById(new Request(
				CommonVars.getCurrUser()), fecavBillStrike.getId());
		signInTableModel.updateRow(fecavBillStrike);
		showMasterData();
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSignInNo() {
		if (tfSignInNo == null) {
			tfSignInNo = new JTextField();
			tfSignInNo.setEditable(false);
			tfSignInNo.setBounds(new java.awt.Rectangle(125, 12, 142, 23));
		}
		return tfSignInNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSignInMan() {
		if (tfSignInMan == null) {
			tfSignInMan = new JTextField();
			tfSignInMan.setBounds(new java.awt.Rectangle(352, 13, 143, 23));
		}
		return tfSignInMan;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbSignInDate() {
		if (cbbSignInDate == null) {
			cbbSignInDate = new JCalendarComboBox();
			cbbSignInDate.setBounds(new java.awt.Rectangle(125, 38, 142, 23));
		}
		return cbbSignInDate;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanStrikeTotalMoney() {
		if (tfCanStrikeTotalMoney == null) {
			tfCanStrikeTotalMoney = new JCustomFormattedTextField();
			tfCanStrikeTotalMoney.setBounds(new java.awt.Rectangle(124, 64,
					143, 23));
			tfCanStrikeTotalMoney
					.setFormatterFactory(getDefaultFormatterFactory());
			tfCanStrikeTotalMoney.setEditable(true);
			tfCanStrikeTotalMoney.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}
							try {
								tfCanStrikeTotalMoney.commitEdit();
							} catch (ParseException e1) {
							}
							calCanStikeExchangeImportMoney();
						}

						public void removeUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}
							try {
								tfCanStrikeTotalMoney.commitEdit();
							} catch (ParseException e1) {
							}
							calCanStikeExchangeImportMoney();
						}

						public void changedUpdate(DocumentEvent e) {

						}
					});
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
			tfCanStrikeImportMoney.setBounds(new java.awt.Rectangle(352, 63,
					143, 23));
			tfCanStrikeImportMoney.setEditable(true);
			tfCanStrikeImportMoney
					.setFormatterFactory(getDefaultFormatterFactory());

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

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanStrikeExchangeMoney() {
		if (tfCanStrikeExchangeMoney == null) {
			tfCanStrikeExchangeMoney = new JCustomFormattedTextField();
			tfCanStrikeExchangeMoney.setBounds(new java.awt.Rectangle(352, 88,
					143, 24));
			tfCanStrikeExchangeMoney.setEditable(true);
			tfCanStrikeExchangeMoney
					.setFormatterFactory(getDefaultFormatterFactory());

		}

		return tfCanStrikeExchangeMoney;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new java.awt.Rectangle(352, 115, 143, 23));
			cbbCurr.setEnabled(false);
		}
		return cbbCurr;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (signInTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"请选择你要修改的核销签收的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
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

	private void fillData() {
		if (this.fecavBillStrike == null) {
			return;
		}
		fecavBillStrike.setSignInNo(this.tfSignInNo.getText());
		fecavBillStrike.setCavSignInMan(this.tfSignInMan.getText());
		fecavBillStrike.setCavSignInDate(this.cbbSignInDate.getDate());
		// fecavBillStrike.setCanStrikeTotalMoney(this.tfCanStrikeTotalMoney.getValue()==null?0.0:Double.parseDouble(this.tfCanStrikeTotalMoney.getValue().toString()));
		fecavBillStrike
				.setCanStrikeImportMoney(this.tfCanStrikeImportMoney.getValue() == null ? 0.0
						: Double.parseDouble(this.tfCanStrikeImportMoney
								.getValue().toString()));
		fecavBillStrike.setCanStrikeExchangeMoney(this.tfCanStrikeExchangeMoney
				.getValue() == null ? 0.0 : Double
				.parseDouble(this.tfCanStrikeExchangeMoney.getValue()
						.toString()));
		fecavBillStrike.setCanStrikeTotalMoney(this.tfCanStrikeTotalMoney
				.getValue() == null ? 0.0 : Double
				.parseDouble(this.tfCanStrikeTotalMoney.getValue().toString()));
		fecavBillStrike.setPieces(this.tfPieces.getValue() == null ? 0 : Double
				.valueOf(this.tfPieces.getValue().toString()).intValue());
		fecavBillStrike.setCurr((Curr) this.cbbCurr.getSelectedItem());
		fecavBillStrike.setTradeMode((Trade) this.cbbTrade.getSelectedItem());
		fecavBillStrike
				.setStrikeRate(this.tfStrikeExchangeRate.getValue() == null ? 0.0
						: Double.parseDouble(this.tfStrikeExchangeRate
								.getValue().toString()));
		if (cbbProjectType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
			int projectType = Integer.parseInt(item.getCode());
			fecavBillStrike.setProjectType(projectType);
		} else {
			fecavBillStrike.setProjectType(null);
		}
		if (cbbEmsNo.getSelectedItem() != null) {
			TempCustomsEmsInfo temp = (TempCustomsEmsInfo) cbbEmsNo
					.getSelectedItem();
			fecavBillStrike.setEmsNo(temp.getEmsNo());
		} else {
			fecavBillStrike.setEmsNo(null);
		}
	}

	private void showEmptyData() {
		this.tfSignInNo.setText("");
		this.tfSignInMan.setText("");
		this.cbbSignInDate.setValue(null);
		this.tfCanStrikeTotalMoney.setValue(null);
		this.tfCanStrikeImportMoney.setValue(null);
		this.tfCanStrikeExchangeMoney.setValue(null);
		// this.cbbCurr.setSelectedItem(null);
		this.initFecavBillTable(new ArrayList());

	}

	private void showMasterData() {
		if (this.fecavBillStrike == null) {
			this.showEmptyData();
			return;
		}
		this.tfSignInNo.setText(fecavBillStrike.getSignInNo());
		this.tfSignInMan.setText(fecavBillStrike.getCavSignInMan());
		this.cbbSignInDate.setValue(fecavBillStrike.getCavSignInDate());
		this.tfCanStrikeTotalMoney.setValue(fecavBillStrike
				.getCanStrikeTotalMoney());
		this.tfCanStrikeImportMoney.setValue(fecavBillStrike
				.getCanStrikeImportMoney());
		this.tfCanStrikeExchangeMoney.setValue(fecavBillStrike
				.getCanStrikeExchangeMoney());
		this.tfPieces.setValue(fecavBillStrike.getPieces());
		this.cbbCurr.setSelectedItem(fecavBillStrike.getCurr());
		this.cbbTrade.setSelectedItem(fecavBillStrike.getTradeMode());
		this.tfStrikeExchangeRate.setValue(fecavBillStrike.getStrikeRate());
		if (fecavBillStrike.getProjectType() != null) {
			this.cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(
					fecavBillStrike.getProjectType().toString(),
					this.cbbProjectType));
		} else {
			this.cbbProjectType.setSelectedIndex(-1);
		}
		if (fecavBillStrike.getEmsNo() != null
				&& !"".equals(fecavBillStrike.getEmsNo())) {
			for (int i = 0; i < this.cbbEmsNo.getItemCount(); i++) {
				if (this.cbbEmsNo.getItemAt(i).toString().equals(
						fecavBillStrike.getEmsNo())) {
					this.cbbEmsNo.setSelectedIndex(i);
					break;
				}
			}
		} else {
			this.cbbEmsNo.setSelectedIndex(-1);
		}
	}

	private void showDetailData() {
		List list = new ArrayList();
		if (this.fecavBillStrike != null && !"".equals(fecavBillStrike.getId())) {
			list = fecavAction.findFecavBillByStrike(new Request(CommonVars
					.getCurrUser()), fecavBillStrike);
		}
		this.initFecavBillTable(list);
		showCount(list);
	}

	private void showAllData() {
		this.showMasterData();
		this.showDetailData();
		setState();
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
					if (tfStrikeExchangeRate.getValue() == null
							|| Double.valueOf(tfStrikeExchangeRate.getValue()
									.toString()) <= 0.0) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"应收汇比例不能为空或为零", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					Double strikeRate = (tfStrikeExchangeRate.getValue() == null ? 0.0
							: Double.valueOf(tfStrikeExchangeRate.getValue()
									.toString()));
					if (strikeRate >100.0) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"应收汇比例不能大于等于100", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (dataState == DataState.ADD) {
						fecavBillStrike = new FecavBillStrike();
						fecavBillStrike.setFecavState(FecavState.CONTROL);
					}
					// if (tfCanStrikeTotalMoney.getValue() == null
					// || Double.valueOf(tfCanStrikeTotalMoney.getValue()
					// .toString()) <= 0) {
					// JOptionPane.showMessageDialog(FmControlFecavBill.this,
					// "待核销总值不能为空，不能小于等于0", "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }
					double canStrikeTotal = (tfCanStrikeTotalMoney.getValue() == null ? 0
							: Double.valueOf(tfCanStrikeTotalMoney.getValue()
									.toString()));
					double canStrikeImport = (tfCanStrikeImportMoney.getValue() == null ? 0
							: Double.valueOf(tfCanStrikeImportMoney.getValue()
									.toString()));
					double canStrikeExchange = (tfCanStrikeExchangeMoney
							.getValue() == null ? 0 : Double
							.valueOf(tfCanStrikeExchangeMoney.getValue()
									.toString()));
					if ((canStrikeImport + canStrikeExchange) > canStrikeTotal) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"可用抵扣料值+待结汇金额不能大于待核销总值", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					double strikeImport = (fecavBillStrike
							.getStrikedImportMoney() == null ? 0.0
							: fecavBillStrike.getStrikedImportMoney());
					double strikeExchange = (fecavBillStrike
							.getStrikedExchangeMoney() == null ? 0.0
							: fecavBillStrike.getStrikedExchangeMoney());
					if ((canStrikeImport - strikeImport) < 0) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"可冲销料值不能小于已冲销料值", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if ((canStrikeExchange - strikeExchange) < 0) {
						JOptionPane.showMessageDialog(FmControlFecavBill.this,
								"可冲销结汇金额不能小于已冲销结汇金额", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					fillData();
					fecavBillStrike = fecavAction.saveFecavBillStrike(
							new Request(CommonVars.getCurrUser()),
							fecavBillStrike);
					if (dataState == DataState.ADD) {
						signInTableModel.addRow(fecavBillStrike);
					} else {
						signInTableModel.updateRow(fecavBillStrike);
					}
					dataState = DataState.BROWSE;
					setState();
					showMasterData();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					showAllData();
					setState();
				}
			});
		}
		return btnCancel;
	}

	private void initUIComponents() {
		// 初始化货币
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCurr);
		CustomBaseComboBoxEditor.getInstance().setSelectedIndex(this.cbbCurr,
				"502");
		this.cbbTrade.setModel(CustomBaseModel.getInstance().getTradeModel());
		this.initComboBoxRenderer(cbbTrade);

		this.cbbProjectType.removeAllItems();
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "电子帐册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCS), "纸质手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.DZSC), "电子手册"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(-1);
	}

	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

	/**
	 * 新增显示初始化数据
	 */
	private void addInitData() {
		String signInNo = this.fecavAction.getMaxSignInNo(new Request(
				CommonVars.getCurrUser()));
		this.tfSignInNo.setText(signInNo);
		if (CommonVars.getCurrUser() != null) {
			AclUser aclUser = (AclUser) CommonVars.getCurrUser();
			this.tfSignInMan.setText(aclUser.getUserName());
		}
		if (tfStrikeExchangeRate.getValue() == null) {
			tfStrikeExchangeRate.setValue(20);
		}
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfPieces() {
		if (tfPieces == null) {
			tfPieces = new JCustomFormattedTextField();
			tfPieces.setBounds(new java.awt.Rectangle(352, 141, 143, 23));
			tfPieces.setFormatterFactory(defaultFormatterFactory);
		}
		return tfPieces;
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
		List list = fecavBillTableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill fecavBill = (FecavBill) list.get(i);
			fecavBill.setIsSelected(b);
		}
		fecavBillTableModel.setList(list);
	}

	private void calCanStikeExchangeImportMoney() {
		Double strikeRate = (this.tfStrikeExchangeRate.getValue() == null ? 0.0
				: Double.valueOf(this.tfStrikeExchangeRate.getValue()
						.toString()));
		if (strikeRate >= 100.0) {
			return;
		}
		Double totalMoney = (this.tfCanStrikeTotalMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfCanStrikeTotalMoney.getValue()
						.toString()));
		Double canExchangeMoney = (totalMoney * (strikeRate / 100.0));
		Double canImportMoney = (totalMoney - canExchangeMoney);
		this.tfCanStrikeExchangeMoney.setValue(canExchangeMoney);
		this.tfCanStrikeImportMoney.setValue(canImportMoney);
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

	private void settfCanStrikeImportMoneyValue() {
		// Double importmoney = (this.tfCanStrikeImportMoney.getValue() ==
		// null?0.0:Double.valueOf(this.tfCanStrikeImportMoney.getValue().toString()));
		Double outportmoney = (this.tfCanStrikeExchangeMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfCanStrikeExchangeMoney.getValue()
						.toString()));
		Double itotalmoney = (this.tfCanStrikeTotalMoney.getValue() == null ? 0.0
				: Double.valueOf(this.tfCanStrikeTotalMoney.getValue()
						.toString()));

		Double importmoney = itotalmoney - outportmoney;

		this.tfCanStrikeImportMoney.setValue(importmoney);
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTrade() {
		if (cbbTrade == null) {
			cbbTrade = new JComboBox();
			cbbTrade.setBounds(new java.awt.Rectangle(125, 143, 142, 23));
		}
		return cbbTrade;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new java.awt.Rectangle(125, 89, 142, 25));
			cbbProjectType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						ItemProperty item = (ItemProperty) cbbProjectType
								.getSelectedItem();
						int projectType = Integer.parseInt(item.getCode());
						showEmsNoData(projectType);
					}
				}
			});
		}
		return cbbProjectType;
	}

	private void showEmsNoData(int projectType) {
		this.cbbEmsNo.removeAllItems();
		List list = fecavAction.findEmsHeadByProjectType(new Request(CommonVars
				.getCurrUser(), true), projectType);
		for (int i = 0; i < list.size(); i++) {
			this.cbbEmsNo.addItem((TempCustomsEmsInfo) list.get(i));
		}
		this.cbbEmsNo.setSelectedIndex(-1);
		switch (projectType) {
		case ProjectType.BCS:
			lbEmsNo.setText("纸质手册号");
			break;
		case ProjectType.BCUS:
			lbEmsNo.setText("电子帐册号");
			break;
		case ProjectType.DZSC:
			lbEmsNo.setText("电子手册号");
			break;
		}
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new java.awt.Rectangle(125, 117, 142, 23));
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfStrikeExchangeRate() {
		if (tfStrikeExchangeRate == null) {
			tfStrikeExchangeRate = new JFormattedTextField();
			tfStrikeExchangeRate.setBounds(new java.awt.Rectangle(352, 37, 48,
					24));
			tfStrikeExchangeRate
					.setFormatterFactory(getDefaultFormatterFactory());
			tfStrikeExchangeRate.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}

							try {
								tfStrikeExchangeRate.commitEdit();
							} catch (ParseException e1) {
							}
							calCanStikeExchangeImportMoney();
						}

						public void removeUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}
							try {
								tfStrikeExchangeRate.commitEdit();
							} catch (ParseException e1) {
							}
							calCanStikeExchangeImportMoney();
						}

						public void changedUpdate(DocumentEvent e) {

						}
					});
		}
		return tfStrikeExchangeRate;
	}

}
