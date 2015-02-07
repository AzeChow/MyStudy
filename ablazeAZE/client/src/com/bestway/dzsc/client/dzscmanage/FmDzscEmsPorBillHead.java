/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DzscEmsType;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 
 * lm modify by 2009-5-7
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmDzscEmsPorBillHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTable tbHg = null;

	private JScrollPane jScrollPane = null;

	// private DzscInnerMergeHead head = null;
	/** 电子手册通关备案Action类 */

	private DzscAction dzscAction = null;
	/** 扩展的JTableModel 通过List数据源来显示&绑定数据 */

	private JTableListModel tableModelHg = null;

	// private JTableListModel tableModelWj = null;
	/** 是否更改 */

	private boolean isChange = false;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAdd = null;

	private JButton btnDelete = null;

	private JButton btnShow = null;

	private JButton btnProcess = null;

	private JButton btnChange = null;

	private JButton btnCopy = null;

	private JButton btnEdit = null;

	private JButton btnExit = null;

	private JButton btnApply = null;

	private JButton btnRefresh = null;

	private JButton btnEditDzscEmsNo = null;

	private JCheckBox cbIsCancel = null;

	private DzscMessageAction dzscMessageAction = null;

	private JButton btnImport = null;

	private JPopupMenu pmImport = null; // @jve:decl-index=0:visual-constraint="758,119"

	private JMenuItem miImportFromQP = null;

	/**
	 * This is the default constructor
	 */
	public FmDzscEmsPorBillHead() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");

		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(702, 454);
		this.setTitle("手册通关备案");
		this.setContentPane(getJContentPane());
		initTable();
		setState();
		this.setHelpId("dzsc");
		// jTabbedPane.setSelectedIndex(1);
	}

	/**
	 * This method initializes 手册通关备案
	 * 
	 * @return void
	 */
	private void initTable() {
		// List dataSourceWj = null;
		// dataSourceWj = dzscAction.findDzscEmsPorWjHead(new Request(CommonVars
		// .getCurrUser()));
		// if (dataSourceWj != null && dataSourceWj.size() > 0) {
		// initTableWj(dataSourceWj);
		// } else {
		// initTableWj(new Vector());
		// }
		List dataSourceHg = null;
		dataSourceHg = dzscAction.findDzscEmsPorHead1(new Request(CommonVars
				.getCurrUser()), cbIsCancel.isSelected());
		if (dataSourceHg != null && dataSourceHg.size() > 0) {
			initTableHg(dataSourceHg);
		} else {
			initTableHg(new Vector());
		}
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			DzscParameterSet parameterSet = dzscMessageAction
					.findDzscMessageDirSet(new Request(CommonVars.getCurrUser()));
			if ((parameterSet != null)
					&& (parameterSet.getManageType() != null && parameterSet
							.getManageType().equals("1"))) {
				getBtnApply().setText("备案");
			}
			super.setVisible(f);
		}
	}

	// private void initTableWj(final List list) { // 外经管理
	// tableModelWj = new JTableListModel(tbWj, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(addColumn("流水号", "seqNum", 50));
	// list.add(addColumn("合同号", "ieContractNo", 100));
	// list.add(addColumn("合同状态", "declareState", 100));
	// list.add(addColumn("合同类型", "bargainType.name", 100));
	// list.add(addColumn("起始日期", "beginDate", 100));
	// list.add(addColumn("结束日期", "endDate", 100));
	// list.add(addColumn("核销期限", "destroyDate", 100));
	// list.add(addColumn("备注", "note", 100));
	// list.add(addColumn("延期期限", "deferDate", 100));
	// list.add(addColumn("进出口岸", "iePort1.name", 100));
	// list.add(addColumn("经营单位", "tradeName", 100));
	// list.add(addColumn("贸易方式", "trade.name", 100));
	// list.add(addColumn("收货单位", "machName", 100));
	// list.add(addColumn("贸易国别", "tradeCountry.name", 100));
	// list.add(addColumn("批文号", "sancEmsNo", 100));
	// list.add(addColumn("协议书号", "agreementNo", 100));
	// list.add(addColumn("出口合同号", "imContractNo", 100));
	// list.add(addColumn("进口总值", "imgAmount", 100));
	// list.add(addColumn("出口总值", "exgAmount", 100));
	// list.add(addColumn("币制", "curr.name", 100));
	// list.add(addColumn("监管费率", "wardshipRate", 100));
	// list.add(addColumn("监管费", "wardshipFee", 100));
	// list.add(addColumn("进出口岸二", "iePort2.name", 100));
	// list.add(addColumn("进出口岸三", "iePort3.name", 100));
	// list.add(addColumn("进出口岸四", "iePort4.name", 100));
	// list.add(addColumn("进出口岸五", "iePort5.name", 100));
	// list.add(addColumn("企业地址", "enterpriseAddress", 100));
	// list.add(addColumn("联系人", "linkMan", 100));
	// list.add(addColumn("联系电话", "contactTel", 100));
	//
	// return list;
	// }
	// });
	// tbWj.getColumnModel().getColumn(3).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value, boolean isSelected,
	// boolean hasFocus, int row, int column) {
	// super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// super.setText((value == null) ? "" : castValue1(value));
	// return this;
	// }
	//
	// private String castValue1(Object value) {
	// String returnValue = "";
	// if (String.valueOf(value).trim().equals("")) {
	// return "";
	// }
	// if (value.equals(DzscState.Application)) {
	// returnValue = "正在申请";
	// } else if (value.equals(DzscState.Execute)) {
	// returnValue = "正在执行";
	// } else if (value.equals(DzscState.Change)) {
	// returnValue = "正在变更";
	// }
	// return returnValue;
	// }
	// });
	// }
	/**
	 * 初始化海关管理表
	 */

	private void initTableHg(final List list) { // 海关管理
		tableModelHg = new JTableListModel(tbHg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 50));
						list.add(addColumn("合同号", "ieContractNo", 100));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("合同状态", "declareState", 100));
						list.add(addColumn("起始日期", "beginDate", 100));
						list.add(addColumn("结束日期", "endDate", 100));
						list.add(addColumn("核销期限", "destroyDate", 100));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("合同类型", "emsType", 100));
						// list.add(addColumn("延期期限", "deferDate", 100));
						list.add(addColumn("进出口岸", "iePort1.name", 100));
						list.add(addColumn("经营单位", "tradeName", 100));
						list.add(addColumn("贸易方式", "trade.name", 100));
						list.add(addColumn("收货单位", "machName", 100));
						list.add(addColumn("贸易国别", "tradeCountry.name", 100));
						list.add(addColumn("批文号", "sancEmsNo", 100));
						list.add(addColumn("协议书号", "agreementNo", 100));
						list.add(addColumn("出口合同号", "imContractNo", 100));
						list.add(addColumn("进口总值", "imgAmount", 100));
						list.add(addColumn("出口总值", "exgAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("监管费率", "wardshipRate", 100));
						list.add(addColumn("监管费", "wardshipFee", 100));
						list.add(addColumn("进出口岸二", "iePort2.name", 100));
						list.add(addColumn("进出口岸三", "iePort3.name", 100));
						list.add(addColumn("进出口岸四", "iePort4.name", 100));
						list.add(addColumn("进出口岸五", "iePort5.name", 100));
						list.add(addColumn("企业地址", "enterpriseAddress", 100));
						list.add(addColumn("联系人", "linkMan", 100));
						list.add(addColumn("联系电话", "contactTel", 100));

						return list;
					}
				});
		tbHg.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}

						if (value.equals(DzscState.ORIGINAL)) {
							returnValue = "原始状态";
						} else if (value.equals(DzscState.APPLY)) {
							returnValue = "等待审批";
						} else if (value.equals(DzscState.EXECUTE)) {
							returnValue = "正在执行";
						} else if (value.equals(DzscState.CHANGE)) {
							returnValue = "正在变更";
						} else if (value.equals(DzscState.BACK_BILL)) {
							returnValue = "退单状态";
						} else if (value.equals(DzscState.CHECK_CANCEL)) {
							returnValue = "核销状态";
						}
						return returnValue;
					}
				});
		tbHg.getColumnModel().getColumn(8).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DzscEmsType.COME_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易来料手册";
						} else if (value
								.equals(DzscEmsType.IMPORT_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易进料手册";
						}
						if (value.equals(DzscEmsType.MACHINE_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易设备手册";
						}
						return returnValue;
					}
				});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel3(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 设置控件的状态
	 */

	private void setState() {
		String declareState = "";
		String corrEmsNo = null;
		if (tableModelHg.getCurrentRow() != null) {
			declareState = ((DzscEmsPorHead) tableModelHg.getCurrentRow())
					.getDeclareState();
			corrEmsNo = ((DzscEmsPorHead) tableModelHg.getCurrentRow())
					.getCorrEmsNo();
		}
		btnEdit.setEnabled(declareState.equals(DzscState.ORIGINAL)
				|| declareState.equals(DzscState.BACK_BILL)
				|| declareState.equals(DzscState.CHANGE)); // 修改合同
		btnDelete.setEnabled(declareState.equals(DzscState.ORIGINAL)
				|| declareState.equals(DzscState.BACK_BILL)
				|| declareState.equals(DzscState.CHANGE)); // 删除
		btnApply.setEnabled((declareState.equals(DzscState.ORIGINAL)
				|| declareState.equals(DzscState.BACK_BILL) || declareState
				.equals(DzscState.CHANGE))
				|| !btnApply.getText().equals("海关申报"));// 申报
		// btnProcess.setVisible((declareState.equals(DzscState.EXECUTE))
		// && btnApply.getText().equals("海关申报")); // 备案
		btnProcess.setVisible((declareState.equals(DzscState.APPLY))); // 备案
		btnChange.setEnabled(declareState.equals(DzscState.EXECUTE)); // 变更
		btnCopy.setEnabled(!declareState.equals(DzscState.CHANGE)); // 转抄&&
		// (corrEmsNo
		// == null
		// ||
		// "".equals(corrEmsNo.trim()))
	}

	/**
	 * 
	 * This method initializes tbHg
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getTbHg() {
		if (tbHg == null) {
			tbHg = new JTable();
			tbHg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelHg == null)
								return;
							if (tableModelHg.getCurrentRow() == null)
								return;
							setState();
						}
					});
			tbHg.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						//
					}
				}
			});

		}
		return tbHg;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbHg());
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	// /**
	// * @return Returns the head.
	// */
	// public DzscInnerMergeHead getHead() {
	// return head;
	// }
	//
	// /**
	// * @param head
	// * The head to set.
	// */
	// public void setHead(DzscInnerMergeHead head) {
	// this.head = head;
	// }

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
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar1.add(getBtnEdit());
			// jToolBar1.add(getJButton8());
			jToolBar1.add(getBtnDelete());
			jToolBar1.add(getBtnShow());
			jToolBar1.add(getBtnApply());
			jToolBar1.add(getBtnProcess());
			jToolBar1.add(getBtnCopy());
			jToolBar1.add(getBtnChange());
			jToolBar1.add(getBtnRefresh());
			jToolBar1.add(getBtnImport());
			jToolBar1.add(getBtnEditDzscEmsNo());
			jToolBar1.add(getBtnExit());
			jToolBar1.add(getCbIsCancel());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");// 海关
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscEmsPorHead head = dzscAction
							.newDzscEmsPorHead(new Request(CommonVars
									.getCurrUser()));
					tableModelHg.addRow(head);
					setState();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");// 海关
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelHg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this, "请选择要删除的合同资料！",
								"提示", 2);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							FmDzscEmsPorBillHead.this, "确定要删除该合同吗?", "提示", 2) == 0) {
						DzscEmsPorHead head = (DzscEmsPorHead) tableModelHg
								.getCurrentRow();
						// dzscAction.deleteAllEmsPor(new Request(CommonVars
						// .getCurrUser()), head);
						long beginTime=System.currentTimeMillis();
						dzscAction.deleteDzscEmsPorHead(new Request(CommonVars
								.getCurrUser()), head);
						long endTime=System.currentTimeMillis();
						System.out.println("----删除花费时间:"+(endTime-beginTime)/1000.0+"秒");
						tableModelHg.deleteRow(head);
					}
					setState();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnShow
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShow() {
		if (btnShow == null) {
			btnShow = new JButton();
			btnShow.setText("显示"); // 海关
			btnShow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelHg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this, "请选中要显示的合同！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgDzscEmsPor dg = new DgDzscEmsPor();
					dg.setDataState(DataState.BROWSE);
					dg.setTableModelHead(tableModelHg);
					dg.setVisible(true);
				}
			});
		}
		return btnShow;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("处理回执"); // 海关
			// (申请--执行)--加detailChanged.setModifyMark(ModifyMarkState.UNCHANGE);
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelHg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this, "请选择要回执处理的合同", "提示",
								2);
						return;
					}
					DzscEmsPorHead obj = (DzscEmsPorHead) tableModelHg
							.getCurrentRow();// 变更
					List lsReturnFile = DzscCommon.getInstance()
							.showDzscReceiptFile(DzscBusinessType.EMS_POR_BILL,
									obj.getCopTrNo());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					List list = dzscAction.findIsRepeatChange(new Request(
							CommonVars.getCurrUser()), obj, DzscState.EXECUTE);
					DzscEmsPorHead billExingHead = null;
					if (list != null && list.size() > 0) { // 已经存在正在执行
						billExingHead = (DzscEmsPorHead) list.get(0);
						// dzscAction.deleteAllEmsPor(new Request(CommonVars
						// .getCurrUser()), head);
						// dzscAction.deleteDzscEmsPorHead(new
						// Request(CommonVars
						// .getCurrUser()), head);
						// tableModelHg.deleteRow(head);
					}
					try {
						String result = dzscAction.processDzscEmsPorBillHead(
								new Request(CommonVars.getCurrUser()), obj,
								billExingHead, lsReturnFile);
						obj = dzscAction.findDzscEmsPorHeadById(new Request(
								CommonVars.getCurrUser()), obj.getId());
						tableModelHg.updateRow(obj);
						if (billExingHead != null) {
							DzscEmsPorHead exingHeadTemp = dzscAction
									.findDzscEmsPorHeadById(new Request(
											CommonVars.getCurrUser()),
											billExingHead.getId());
							if (exingHeadTemp == null) {
								tableModelHg.deleteRow(billExingHead);
							}
						}
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this,
								"回执处理成功！\n" + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this, "回执处理失败"
										+ ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					setState();
					// obj.setDeclareState(DzscState.EXECUTE);
					// obj = dzscAction.saveEmsPor(new Request(CommonVars
					// .getCurrUser()), obj);
					// tableModelHg.updateRow(obj);
					// setState();
				}
			});
		}
		return btnProcess;
	}

	/**
	 * This method initializes btnChange
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更"); // 海关
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelHg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this, "请选中要变更的合同！", "提示",
								2);
						return;
					}
					DzscEmsPorHead head = (DzscEmsPorHead) tableModelHg
							.getCurrentRow();
					List list = dzscAction.findIsRepeatChange(new Request(
							CommonVars.getCurrUser()), head, DzscState.CHANGE);
					if (list != null && list.size() > 0) {
						JOptionPane
								.showMessageDialog(FmDzscEmsPorBillHead.this,
										"已经存在变更合同!", "提示", 2);
						return;
					}
					DzscEmsPorHead headChange = null;
					long beginTime=System.currentTimeMillis();
					headChange = dzscAction.dzscEmsPorHeadChange(new Request(
							CommonVars.getCurrUser()), head, true);
					long endTime=System.currentTimeMillis();
					System.out.println("----变更花费时间:"+(endTime-beginTime)/1000.0+"秒");
					tableModelHg.addRow(headChange);
					setState();
				}
			});
		}
		return btnChange;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄"); // 海关
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelHg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this, "请选中要转抄的手册！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DzscEmsPorHead headCopy = dzscAction.dzscEmsPorHeadChange(
							new Request(CommonVars.getCurrUser()),
							(DzscEmsPorHead) tableModelHg.getCurrentRow(),
							false);
					tableModelHg.addRow(headCopy);
					setState();
				}
			});
		}
		return btnCopy;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改"); // 海关
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editHg(false);
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 编辑海关合同 isEditBill 是编辑清单
	 */
	private void editHg(boolean isEditBill) {
		if (tableModelHg.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmDzscEmsPorBillHead.this,
					"请选中要修改的合同！", "提示", 2);
			return;
		}
		DgDzscEmsPor dg = new DgDzscEmsPor();
		dg.setDataState(DataState.EDIT);
		dg.setEditBill(isEditBill);
		dg.setTableModelHead(tableModelHg);
		dg.setVisible(true);
	}

	/**
	 * This method initializes btnEditDzscEmsNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditDzscEmsNo() {
		if (btnEditDzscEmsNo == null) {
			btnEditDzscEmsNo = new JButton();
			btnEditDzscEmsNo.setText("修改手册号");
			btnEditDzscEmsNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// 权限控制
							dzscAction.checkEditPorHeadEmsNo(new Request(
									CommonVars.getCurrUser()));
							if (tableModelHg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmDzscEmsPorBillHead.this, "请先选择一本合同!",
										"提示", JOptionPane.ERROR_MESSAGE);
								return;
							}
							DzscEmsPorHead head = (DzscEmsPorHead) tableModelHg
									.getCurrentRow();
							if (head.getEmsNo() == null) {
								JOptionPane.showMessageDialog(
										FmDzscEmsPorBillHead.this,
										"手册号为空,无法修改！", "提示",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							DgEditDzscEmsNo dgEditDzscEmsNo = new DgEditDzscEmsNo();
							dgEditDzscEmsNo.setHead("DzscEmsPorHead");
							dgEditDzscEmsNo.setOldEmsNo(head.getEmsNo());
							dgEditDzscEmsNo.setVisible(true);
							if (dgEditDzscEmsNo.getHadChange()) {
								head.setEmsNo(dgEditDzscEmsNo.getNewEmsNo());
							}
							tableModelHg.updateRow(head);
						}
					});
		}
		return btnEditDzscEmsNo;
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
					FmDzscEmsPorBillHead.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes btnApply
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelHg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmDzscEmsPorBillHead.this, "请选择要申报的合同", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DzscParameterSet parameterSet = dzscMessageAction
							.findDzscMessageDirSet(new Request(CommonVars
									.getCurrUser()));
					if ((parameterSet != null)
							&& (parameterSet.getManageType() != null && parameterSet
									.getManageType().equals("1"))) {
						if (getBtnApply().getText().equals("海关申报")) {
							JOptionPane.showMessageDialog(
									FmDzscEmsPorBillHead.this,
									"您已经对参数设置进行了修改,关闭后重新打开才能生效！", "警告！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (CommonVars.getCurrUser().getUserFlag() != null
								&& CommonVars.getCurrUser().getUserFlag()
										.equals("S")) {
							DzscEmsPorHead head = (DzscEmsPorHead) tableModelHg
									.getCurrentRow();
							String dstate = head.getDeclareState() == null ? ""
									: head.getDeclareState();
							if (dstate.equals(DzscState.ORIGINAL)) {
								head.setDeclareState(DzscState.APPLY);
							} else if (dstate.equals(DzscState.APPLY)) {
								head.setDeclareState(DzscState.EXECUTE);
							} else if (dstate.equals(DzscState.EXECUTE)) {
								JOptionPane.showMessageDialog(
										FmDzscEmsPorBillHead.this,
										"对不起，不能在正在执行状态下改变状态！", "警告！",
										JOptionPane.WARNING_MESSAGE);
								return;
							} else if (dstate.equals(DzscState.CHECK_CANCEL)) {
								JOptionPane.showMessageDialog(
										FmDzscEmsPorBillHead.this,
										"对不起，不能在已经核销状态下改变状态！", "警告！",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
							head = dzscAction.saveDzscEmsPorHead(new Request(
									CommonVars.getCurrUser()), head);
							tableModelHg.updateRow(head);
						} else {
							JOptionPane.showMessageDialog(
									FmDzscEmsPorBillHead.this,
									"您不是超级用户，没有权限操作此功能！", "警告！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					} else {
						// if (!getBtnApply().getText().equals("海关申报")) {
						// JOptionPane.showMessageDialog(
						// FmDzscEmsPorBillHead.this,
						// "您已经对参数设置进行了修改,关闭后重新打开才能生效！", "警告！",
						// JOptionPane.WARNING_MESSAGE);
						// return;
						// }
						DzscEmsPorHead head = (DzscEmsPorHead) tableModelHg
								.getCurrentRow();
						head = dzscAction.findDzscEmsPorHeadById(new Request(
								CommonVars.getCurrUser()), head.getId());
						tableModelHg.updateRow(head);
						if (DzscClientLogic.isEmpty(head,
								FmDzscEmsPorBillHead.this)) {
							return;
						}
						new ApplyThread().start();
					}
				}
			});
		}
		return btnApply;
	}

	/**
	 * 多线程
	 * 
	 * @author Administrator
	 * 
	 */

	class ApplyThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				DzscEmsPorHead head = (DzscEmsPorHead) tableModelHg
						.getCurrentRow();
				String checkModifyMarkMsg = dzscAction
				.checkContractIsUnitModifyMarkExgBom(new Request(
						CommonVars.getCurrUser()), head);
				if (checkModifyMarkMsg != null
						&& checkModifyMarkMsg.trim().length() > 0) {
					if (JOptionPane.showConfirmDialog(FmDzscEmsPorBillHead.this,
							"成品序号" + checkModifyMarkMsg
									+ "修改标志与成品单耗【修改标志】不一致，确定继续申报吗?", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
				}
				
				try {
					DeclareFileInfo fileInfo = dzscAction.applyDzscEmsPorHead(
							new Request(CommonVars.getCurrUser()), head);
					head = dzscAction.findDzscEmsPorHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableModelHg.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscEmsPorBillHead.this,
							fileInfo.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscEmsPorBillHead.this,
							"系统申报失败 " + ex.getMessage(), "确认", 1);
				}
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
					setState();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes cbIsCancel
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCancel() {
		if (cbIsCancel == null) {
			cbIsCancel = new JCheckBox();
			cbIsCancel.setText("已核销的手册");
			cbIsCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
					setState();
				}
			});
		}
		return cbIsCancel;
	}

	/**
	 * This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmImport().show(
							getBtnImport(),
							getBtnImport().getWidth()
									- getBtnImport().getPreferredSize().width,
							getBtnImport().getY()
									+ getBtnImport().getPreferredSize().height);
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes pmImport
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmImport() {
		if (pmImport == null) {
			pmImport = new JPopupMenu();
			pmImport.add(getMiImportFromQP());
		}
		return pmImport;
	}

	/**
	 * This method initializes miImportFromQP
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportFromQP() {
		if (miImportFromQP == null) {
			miImportFromQP = new JMenuItem();
			miImportFromQP.setText("直接导入通关备案");
			miImportFromQP
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							List lsExistEmsNo = new ArrayList();
//							List listBillHead = tableModelHg.getList();
//							for (int i = 0; i < listBillHead.size(); i++) {
//								DzscEmsPorHead billHead = (DzscEmsPorHead) listBillHead
//										.get(i);
//								if (DzscState.EXECUTE.equals(billHead
//										.getDeclareState())) {
//									lsExistEmsNo.add(billHead.getEmsNo());
//								}
//							}
//							DgDzscQPEmsPorBillHead dg = new DgDzscQPEmsPorBillHead();
//							dg.setLsExistEmsNo(lsExistEmsNo);
//							dg.setVisible(true);
//							List list = dg.getLsResult();
//							boolean isOverWrite = dg.isOverWrite();
//							if (list != null && !list.isEmpty()) {
//								dzscAction.importDzscEmsPorBillFromQP(
//										new Request(CommonVars.getCurrUser(),
//												true), list, isOverWrite);
//								initTable();
//								setState();
//							}
							// 1.创建py文件
							long time = System.currentTimeMillis();
					        File dir = new File(System.getProperty("java.io.tmpdir") + File.separator + "pytemp");
					        if (!dir.isDirectory()) {
					            dir.mkdirs();
					        }
							
							String path = "/com/bestway/dzsc/client/dzscmanage/importDzscEmsPorBill.py";
							InputStream in= FmDzscEmsPorBillHead.class.getResourceAsStream(path);

					        // 3.写入文件
							FileOutputStream out = null;
							File fileTmp  = null;
							try {
								fileTmp  = File.createTempFile("importDzscEmsPorBill", ".py", dir);
								out = new FileOutputStream(fileTmp);
								byte[] bytes = new byte[2048];
								int len = -1;
								while((len = in.read(bytes)) > 0){
									out.write(bytes,0,len);
								}
								out.flush();
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}finally{
								try {
									out.close();
									in.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								
							}
							int exitVal = 1;
					        // 4.执行py文件
					        File f=new File("C:\\\\Python27\\python.exe");
					         String cmd="";
					         if(f.exists()){
					             cmd = "C:\\\\Python27\\python.exe  \"" +fileTmp.getAbsolutePath() + "\"";
					         }else{
					             cmd = "python  \"" + fileTmp.getAbsolutePath() + "\"";
					         }
					        String resultStr = "";
					        String resultErrorStr = "";
					        try {
					            Runtime rt = Runtime.getRuntime();
					            Process subprocess = rt.exec(cmd);               
					            
					            BufferedReader br = new BufferedReader(new InputStreamReader(subprocess.getInputStream()));
					            BufferedReader ebr = new BufferedReader(new InputStreamReader(subprocess.getErrorStream()));
					            String line = null;
					            while ((line = br.readLine()) != null) {
					                resultStr += line;
					            }
					            
					            line = null;
					            while ((line = ebr.readLine()) != null) {
					                resultErrorStr += line;
					            }
					            exitVal = subprocess.waitFor();
					            System.out.println("Process exitValue: " + exitVal);
					            System.out.println("resultErrorStr----->"+resultErrorStr);
					            System.out.println("resultStr------->" + resultStr);
					        } catch (Exception ex) {
					            ex.printStackTrace();				                
					        }

							
							if (!resultStr.isEmpty()) {
								System.out.println("执行python获取数据用时："+(System.currentTimeMillis()-time)/1000+"秒");
								dzscAction.importDzscEmsPorBillFromQP(
										new Request(CommonVars.getCurrUser(),
												true), resultStr);
								initTable();
								setState();
							}
						}
					});
		}
		return miImportFromQP;
	}
}
