package com.bestway.client.fecav;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.BillOfExchange;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmBillOfExchange extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnRefresh = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private FecavAction fecavAction = null;

	private JButton btnEdit = null;

	private JButton btnSearch = null;

	private JButton btnPrint = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBillOfExchange() {
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
		this.setTitle("银行汇票输入");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						List list = fecavAction.findBillOfExchange(new Request(
								CommonVars.getCurrUser()));
						initTable(list);
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnEdit());
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
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
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
					DgBillOfExchange dg = new DgBillOfExchange();
					dg.setDataState(DataState.ADD);
					dg.setVisible(true);
					if (dg.isOk()) {
						BillOfExchange billOfExchange = dg.getBillOfExchange();
						tableModel.addRow(billOfExchange);
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
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmBillOfExchange.this,
							"你确定要删除这些汇票吗？", "提示", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBillOfExchange.this,
								"请选择你要删除的汇票", "提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = tableModel.getCurrentRows();
					if(fecavAction.isBillOfExchangeIsUer(list)){
						JOptionPane.showMessageDialog(FmBillOfExchange.this,
								"选中的汇票中有已被冲消的,不可以删除,请检查", "提示", JOptionPane.OK_OPTION);
						return;
					}
					fecavAction.deleteBillOfExchange(new Request(CommonVars
							.getCurrUser()), list);
					tableModel.deleteRows(list);
				}
			});
		}
		return btnDelete;
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
					List list = fecavAction.findBillOfExchange(new Request(
							CommonVars.getCurrUser()));
					initTable(list);
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
					FmBillOfExchange.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initTable(List list) {
		JTableListModel.dataBind(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("汇票号码 ", "code", 200));
				list.add(addColumn("结汇日期", "endDate", 100));
				list.add(addColumn("结汇银行", "bank", 100));
				list.add(addColumn("银行帐号", "bankAccounts", 100));
				list.add(addColumn("币别", "curr.name", 100));
//				list.add(addColumn("汇率", "exchangeRate", 100));
				list.add(addColumn("结汇金额", "exchangeMoney", 100));
				list.add(addColumn("可冲销金额", "canStrikeMoney", 100));
				list.add(addColumn("操作员", "operator", 100));
				list.add(addColumn("操作日期", "operateDate", 100));
				return list;
			}
		});
		tableModel = (JTableListModel) jTable.getModel();

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
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBillOfExchange.this,
								"请选择你要修改的汇票", "提示", JOptionPane.OK_OPTION);
						return;
					}
					BillOfExchange billOfExchange = (BillOfExchange) tableModel
							.getCurrentRow();
					DgBillOfExchange dg = new DgBillOfExchange();
					dg.setBillOfExchange(billOfExchange);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
					if (dg.isOk()) {
						billOfExchange = dg.getBillOfExchange();
						tableModel.updateRow(billOfExchange);
					}
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
					DgBillOfExchangeQuery dgBillOfExchangeQuery = new DgBillOfExchangeQuery();
					dgBillOfExchangeQuery.setVisible(true);
					if (dgBillOfExchangeQuery.isOk) {
						List list = dgBillOfExchangeQuery.getResultList();
						initTable(list);
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
			btnPrint.setVisible(false);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				}
			});
		}
		return btnPrint;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
