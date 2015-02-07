package com.bestway.bls.client.entrance;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bls.action.BillSpecialApplyAction;
import com.bestway.bls.client.checkcancel.FmCollateBind;
import com.bestway.bls.client.message.BlsMessageHelper;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BillSpecialApplyType;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;
/**
 * 仓单特殊申请
 * @author chen
 *
 */
public class FmBillSpecialApply extends JInternalFrameBase {

	private JPanel jPanel = null;
	private JToolBar jJToolBarBar = null;
	/**
	 * 新增按钮
	 */
	private JButton btnAdd = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	/**
	 * 仓单特殊申请表表格
	 */
	private JTable tableBillSpecialApply = null;

	/**
	 * 仓单特殊申请表表格模型
	 */
	private JTableListModel tableModel = null;
	/**
	 * 编辑按钮
	 */
	private JButton btnEdit = null;
	/**
	 * 删除按钮
	 */
	private JButton btnDelete = null;
	/**
	 * 海关申报
	 */
	private JButton btnSend = null;

	/**
	 * 仓单特殊申请Action
	 */
	BillSpecialApplyAction billSpecialApplyAction = null; // @jve:decl-index=0:
	/**
	 *关闭按钮
	 */
	private JButton btnExit = null;
	/**
	 * 浏览按钮
	 */
	private JButton btnBrowse = null;
	/**
	 * 回执处理按钮
	 */
	private JButton btnReturnProcess = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBillSpecialApply() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(768, 477));
		this.setContentPane(getJPanel());
		this.setTitle("仓单特殊申请");

		// 初始化属性
		billSpecialApplyAction = (BillSpecialApplyAction) CommonVars
				.getApplicationContext().getBean("billSpecialApplyAction");

		// 初始化表格
		List<BillSpecialApplyHead> billList = billSpecialApplyAction
				.findBillSpecialApplyHead(new Request(CommonVars.getCurrUser()));
		initTable(billList);

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnBrowse());
			jJToolBarBar.add(getBtnSend());
			jJToolBarBar.add(getBtnReturnProcess());
			jJToolBarBar.add(getBtnExit());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillSpecialApplyList dgBillSpecialApplyList = new DgBillSpecialApplyList();
					dgBillSpecialApplyList.setTableModel(tableModel);
					dgBillSpecialApplyList.setInitData(null);
					dgBillSpecialApplyList.setDataState(DataState.ADD);
					dgBillSpecialApplyList.setState();
					dgBillSpecialApplyList.setVisible(true);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 305;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJScrollPane(), gridBagConstraints);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTableBillSpecialApply());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tableBillSpecialApply
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTableBillSpecialApply() {
		if (tableBillSpecialApply == null) {
			tableBillSpecialApply = new JTable();
			tableBillSpecialApply
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() == 2) {
								btnEdit.doClick();
							}
						}
					});
		}
		return tableBillSpecialApply;
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
					BillSpecialApplyHead billSpecialApplyHead = (BillSpecialApplyHead) tableModel
							.getCurrentRow();
					if (billSpecialApplyHead == null) {
						JOptionPane.showMessageDialog(FmBillSpecialApply.this,
								"请选择一份申请单！");
						return;
					}
					DgBillSpecialApplyList dgBillSpecialApplyList = new DgBillSpecialApplyList();
					dgBillSpecialApplyList.setTableModel(tableModel);
					dgBillSpecialApplyList.setInitData(billSpecialApplyHead);
					dgBillSpecialApplyList.setDataState(DataState.EDIT);
					dgBillSpecialApplyList.setState();
					dgBillSpecialApplyList.setVisible(true);
				}
			});
		}
		return btnEdit;
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
					BillSpecialApplyHead billSpecialApplyHead = (BillSpecialApplyHead) tableModel
							.getCurrentRow();
					if (billSpecialApplyHead == null) {
						JOptionPane.showMessageDialog(FmBillSpecialApply.this,
								"请选择一份申请单！");
						return;
					}
					billSpecialApplyAction.deleteBillSpecialApplyHead(
							new Request(CommonVars.getCurrUser()),
							billSpecialApplyHead);
					tableModel.deleteRow(billSpecialApplyHead);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnSend
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSend() {
		if (btnSend == null) {
			btnSend = new JButton();
			btnSend.setText("海关申报");
			btnSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBillSpecialApply.this,
								"请选择要海关申报的资料！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBillSpecialApply.this,
							"确定要进行海关申报吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					BillSpecialApplyHead billSpecialApplyHead = (BillSpecialApplyHead) tableModel
							.getCurrentRow();
					billSpecialApplyHead = billSpecialApplyAction
							.applyBillSpecialApply(new Request(CommonVars
									.getCurrUser()), billSpecialApplyHead);
					String declareInfo = "";
					if (DeclareState.PROCESS_EXE.equals(billSpecialApplyHead
							.getDeclareState())) {
						declareInfo = ("特殊申请单申报"
								+ billSpecialApplyHead.getBillNo() + " 申报成功！");
					} else if (DeclareState.APPLY_POR
							.equals(billSpecialApplyHead.getDeclareState())) {
						declareInfo = ("特殊申请单申报"
								+ billSpecialApplyHead.getBillNo() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA
							.equals(billSpecialApplyHead.getDeclareState())) {
						declareInfo = ("特殊申请单申报"
								+ billSpecialApplyHead.getBillNo() + " 正在等待审批！");
					}
					tableModel.updateRow(billSpecialApplyHead);
					JOptionPane
							.showMessageDialog(FmBillSpecialApply.this,
									declareInfo, "提示！",
									JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnSend;
	}

	/**
	 * 表格数据显示
	 * @param list
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(this.tableBillSpecialApply, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业编码", "tradeCo", 100));
						list.add(addColumn("仓单号", "billNo", 100));
						list.add(addColumn("特殊申请类型", "applyType", 100));
						list.add(addColumn("申报状态", "declareState", 150));
						list.add(addColumn("申请原因", "applyReason", 200));
						list.add(addColumn("备注", "note", 500));
						return list;
					}
				});
		tableBillSpecialApply
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		tableBillSpecialApply.getColumnModel().getColumn(4).setCellRenderer(
//				new DefaultTableCellRenderer() {
//					public Component getTableCellRendererComponent(
//							JTable table, Object value, boolean isSelected,
//							boolean hasFocus, int row, int column) {
//						JCheckBox cbbIsSend = new JCheckBox();
//						Boolean isSend;
//						if (value == null) {
//							isSend = null;
//						} else {
//							isSend = Boolean.valueOf((String) value);
//						}
//						if (isSend != null && isSend) {
//							cbbIsSend.setSelected(true);
//						} else {
//							cbbIsSend.setSelected(false);
//						}
//						cbbIsSend.setBackground(table.getBackground());
//						if (isSelected) {
//							cbbIsSend.setForeground(table
//									.getSelectionForeground());
//							cbbIsSend.setBackground(table
//									.getSelectionBackground());
//						} else {
//							cbbIsSend.setForeground(table.getForeground());
//							cbbIsSend.setBackground(table.getBackground());
//						}
//						return cbbIsSend;
//					}
//				});
		tableBillSpecialApply.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "初始状态";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DeclareState.APPLY_POR)) {
							returnValue = "初始状态";

						} else if (value.equals(DeclareState.WAIT_EAA))
							returnValue = "等待审批";
						else if (value.equals(DeclareState.PROCESS_EXE))
							returnValue = "正在执行";
						else if (value.equals(DeclareState.BACK_BILL))
							returnValue = "退单";
						return returnValue;
					}
				});
		tableBillSpecialApply.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						JTextField lable = new JTextField();
						lable.setBorder(null);
						if (value != null && !"".equals(value)) {
							if (value.equals(BillSpecialApplyType.PRC)) {
								lable.setText("增值性加工");
							} else if (value.equals(BillSpecialApplyType.ABD)) {
								lable.setText("弃货");
							} else if (value.equals(BillSpecialApplyType.RPL)) {
								lable.setText("换货");
							} else if (value.equals(BillSpecialApplyType.CAN)) {
								lable.setText("仓单作废");
							} else if (value.equals(BillSpecialApplyType.DLA)) {
								lable.setText("货物延期存放");
							}
						}
						lable.setBackground(table.getBackground());
						if (isSelected) {
							lable.setForeground(table.getSelectionForeground());
							lable.setBackground(table.getSelectionBackground());
						} else {
							lable.setForeground(table.getForeground());
							lable.setBackground(table.getBackground());
						}
						return lable;
					}
				});
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBillSpecialApply.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes btnBrowse
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BillSpecialApplyHead billSpecialApplyHead = (BillSpecialApplyHead) tableModel
							.getCurrentRow();
					if (billSpecialApplyHead == null) {
						JOptionPane.showMessageDialog(FmBillSpecialApply.this,
								"请选择一份申请单！");
						return;
					}
					DgBillSpecialApplyList dgBillSpecialApplyList = new DgBillSpecialApplyList();
					dgBillSpecialApplyList.setTableModel(tableModel);
					dgBillSpecialApplyList.setInitData(billSpecialApplyHead);
					dgBillSpecialApplyList.setDataState(DataState.BROWSE);
					dgBillSpecialApplyList.setState();
					dgBillSpecialApplyList.setVisible(true);
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes btnReturnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReturnProcess() {
		if (btnReturnProcess == null) {
			btnReturnProcess = new JButton();
			btnReturnProcess.setText("回执处理");
			btnReturnProcess
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmBillSpecialApply.this,
										"请选择要回执处理的单证核销资料！", "提示！",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									FmBillSpecialApply.this, "确定要进行回执处理吗？",
									"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							BillSpecialApplyHead billSpecialApplyHead = (BillSpecialApplyHead) tableModel
									.getCurrentRow();
							String serviceType = "";
							String applyType = billSpecialApplyHead
									.getApplyType();
							if (applyType.equals(BillSpecialApplyType.ABD)) {
								serviceType = BlsServiceType.CARGOABANDON_APPLY;
							} else if (applyType
									.equals(BillSpecialApplyType.CAN)) {
								serviceType = BlsServiceType.BILLCANCEL_APPLY;
								// }else
								// if(applyType.equals(BillSpecialApplyType.DLA)){
								// serviceType =
								// BlsServiceType.CARGOREPLACE_APPLY;
							} else if (applyType
									.equals(BillSpecialApplyType.PRC)) {
								serviceType = BlsServiceType.PROCESS_APPLY;
							} else if (applyType
									.equals(BillSpecialApplyType.RPL)) {
								serviceType = BlsServiceType.CARGOREPLACE_APPLY;
							}
							BlsReceiptResult blsReceiptResult = BlsMessageHelper
									.getInstance().showBlsReceiptFile(
											serviceType,
											billSpecialApplyHead.getId());
							if (blsReceiptResult == null) {
								return;
							}
							billSpecialApplyHead = billSpecialApplyAction
									.processBillSpecialApply(new Request(CommonVars
											.getCurrUser()),
											billSpecialApplyHead,
											blsReceiptResult);
							String declareInfo = "";
							if (DeclareState.PROCESS_EXE
									.equals(billSpecialApplyHead
											.getDeclareState())) {
								declareInfo = ("单证核销申报"
										+ billSpecialApplyHead.getBillNo() + " 申报成功！");
							} else if (DeclareState.APPLY_POR
									.equals(billSpecialApplyHead
											.getDeclareState())) {
								declareInfo = ("单证核销申报"
										+ billSpecialApplyHead.getBillNo() + " 申报失败！");
							} else if (DeclareState.WAIT_EAA
									.equals(billSpecialApplyHead
											.getDeclareState())) {
								declareInfo = ("单证核销申报"
										+ billSpecialApplyHead.getBillNo() + " 正在等待审批！");
							}
							tableModel.updateRow(billSpecialApplyHead);
//							setState();
							JOptionPane.showMessageDialog(
									FmBillSpecialApply.this, declareInfo,
									"提示！", JOptionPane.INFORMATION_MESSAGE);
						}
					});
		}
		return btnReturnProcess;
	}

} // @jve:decl-index=0:visual-constraint="10,22"
