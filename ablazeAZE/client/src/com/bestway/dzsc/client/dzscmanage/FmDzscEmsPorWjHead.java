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
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmDzscEmsPorWjHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnShow = null;

	private JButton btnDeclare = null;

	private JButton btnCopy = null;

	private JButton btnExit = null;

	private DzscAction dzscAction = null;

	// private JTableListModel tableModelHg = null;

	private JTableListModel tableModelWj = null;

	private boolean isChange = false;

	private JButton btnChange = null;

	private JTable tbWj = null;

	private JScrollPane jScrollPane1 = null;

	private JPanel jPanel3 = null;

	private JButton btnApply = null;

	private JButton btnEditDzscEmsNo = null;// 修改手册号

	private JButton btnRefresh = null;

	private JCheckBox cbIsCancel = null;

	private JButton btnImport = null;

	private JPopupMenu pmImport = null; // @jve:decl-index=0:visual-constraint="756,117"

	private JMenuItem miImportFromQP = null;

	/**
	 * This is the default constructor
	 */
	public FmDzscEmsPorWjHead() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(702, 454);
		this.setTitle("手册合同备案");
		this.setContentPane(getJContentPane());
		initTable();
		setState();
		// setState();
		// jTabbedPane.setSelectedIndex(1);
	}

	private void initTable() {
		List dataSourceWj = null;
		dataSourceWj = dzscAction.findDzscEmsPorWjHead(
				new Request(CommonVars.getCurrUser()), cbIsCancel.isSelected());
		if (dataSourceWj != null && dataSourceWj.size() > 0) {
			initTableWj(dataSourceWj);
		} else {
			initTableWj(new Vector());
		}
		// List dataSourceHg = null;
		// dataSourceHg = dzscAction.findDzscEmsPorHead(new Request(CommonVars
		// .getCurrUser()));
		// if (dataSourceHg != null && dataSourceHg.size() > 0) {
		// initTableHg(dataSourceHg);
		// } else {
		// initTableHg(new Vector());
		// }
	}

	private void initTableWj(final List list) { // 外经管理
		tableModelWj = new JTableListModel(tbWj, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 50));
						list.add(addColumn("合同号", "ieContractNo", 100));
						list.add(addColumn("手册编号", "corrEmsNo", 100));
						list.add(addColumn("合同状态", "declareState", 100));
						list.add(addColumn("手册类型", "emsType", 100));
						list.add(addColumn("起始日期", "beginDate", 100));
						list.add(addColumn("结束日期", "endDate", 100));
						list.add(addColumn("核销期限", "destroyDate", 100));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("延期期限", "deferDate", 100));
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
		tbWj.getColumnModel().getColumn(4)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
						}
						return returnValue;
					}
				});
		tbWj.getColumnModel().getColumn(5)
				.setCellRenderer(new DefaultTableCellRenderer() {
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
						if (value.equals(DzscEmsType.COME_PROCESS_CONTRACT)) {
							returnValue = "来料加工合同手册";
						} else if (value
								.equals(DzscEmsType.IMPORT_PROCESS_CONTRACT)) {
							returnValue = "进料加工合同手册";
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
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnShow());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnApply());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnEditDzscEmsNo());
			jToolBar.add(getBtnExit());
			jToolBar.add(getCbIsCancel());
		}
		return jToolBar;
	}

	// private void fillHead(DzscInnerMergeHead head) {
	// head.setSeqNum(Integer.valueOf(dzscAction.getNum(new Request(CommonVars
	// .getCurrUser()), "DzscInnerMergeHead", "seqNum")));
	// head.setType(DzscState.Application);
	// head.setCreateDate(CommonVars.nowToStandDate());
	// // head.setHistoryState(new Boolean(false));
	// head.setCompany(CommonVars.getCurrUser().getCompany());
	// }

	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增"); // 外经
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscEmsPorWjHead head = dzscAction
							.newEmsPorWjHead(new Request(CommonVars
									.getCurrUser()));
					tableModelWj.addRow(head);
					setState();
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");// wj
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelWj.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"请选择你将要修改的记录", "提示！", 0);
						return;
					}
					DgDzscEmsPorWj dg = new DgDzscEmsPorWj();
					dg.setTableModelHead(tableModelWj);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");// wj
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModelWj.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"请选择要删除的合同资料！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmDzscEmsPorWjHead.this,
							"确定要删除该合同吗?", "提示", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						DzscEmsPorWjHead head = (DzscEmsPorWjHead) tableModelWj
								.getCurrentRow();
						dzscAction.deleteAllEmsPorWj(
								new Request(CommonVars.getCurrUser()), head);
						dzscAction.deleteDzscEmsPorWjHead(new Request(
								CommonVars.getCurrUser()), head);
						tableModelWj.deleteRow(head);
					}
					setState();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnShow() {
		if (btnShow == null) {
			btnShow = new JButton();
			btnShow.setText("显示"); // wj
			btnShow.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelWj.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"请选中要显示的合同！", "提示", 2);
						return;
					}
					DgDzscEmsPorWj dg = new DgDzscEmsPorWj();
					dg.setDataState(DataState.BROWSE);
					dg.setTableModelHead(tableModelWj);
					dg.setVisible(true);
				}
			});

		}
		return btnShow;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("处理回执"); // wj
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelWj.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"请选择要处理回执的合同", "提示", 2);
						return;
					}
					DzscEmsPorWjHead obj = (DzscEmsPorWjHead) tableModelWj
							.getCurrentRow();
					List lsReturnFile = DzscCommon.getInstance()
							.showDzscReceiptFile(DzscBusinessType.EMS_POR_WJ,
									obj.getCopTrNo());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					DzscEmsPorWjHead headExing = null;
					List list = dzscAction.findIsRepeatChangeWj(new Request(
							CommonVars.getCurrUser()), obj, DzscState.EXECUTE);
					if (list != null && list.size() > 0) { // 已经存在正在执行
						headExing = (DzscEmsPorWjHead) list.get(0);
					}
					try {
						String result = dzscAction.processDzscEmsPorWjHead(
								new Request(CommonVars.getCurrUser()), obj,
								headExing, lsReturnFile);
						obj = dzscAction.findDzscEmsPorWjHeadById(new Request(
								CommonVars.getCurrUser()), obj.getId());
						tableModelWj.updateRow(obj);
						if (headExing != null) {
							DzscEmsPorWjHead headExingTemp = dzscAction
									.findDzscEmsPorWjHeadById(new Request(
											CommonVars.getCurrUser()),
											headExing.getId());
							if (headExingTemp == null) {
								tableModelWj.deleteRow(headExing);
							}
						}
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"回执处理成功！\n" + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"回执处理失败" + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					setState();
				}
			});

		}
		return btnDeclare;
	}

	// private void setState() {
	// jButton8.setEnabled(getValue().equals(DzscState.Change)); // 修改清单
	// jButton9.setEnabled(!getValue().equals(DzscState.Execute)); // 删除
	// jButton10.setEnabled(!getValue().equals(DzscState.Application)); // 显示
	// btnPutOnRecord.setEnabled(!getValue().equals(DzscState.Execute)); // 备案
	// jButton12.setEnabled(getValue().equals(DzscState.Execute)); // 变更
	// jButton13.setEnabled(!getValue().equals(DzscState.Change)); // 转抄
	// jButton14.setEnabled(!getValue().equals(DzscState.Execute)); // 修改合同
	// }

	// private String getValue() {
	// if (tableModelHg.getCurrentRow() != null) {
	// return ((DzscEmsPorHead) tableModelHg.getCurrentRow())
	// .getDeclareState();
	// }
	// return "";
	// }

	/**
	 * 
	 * This method initializes btnPrint
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄"); // wj
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelWj.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"请选中要转抄的合同！", "提示", 2);
						return;
					}
					DzscEmsPorWjHead headChange = null;
					headChange = dzscAction.emsPorChangeWj(new Request(
							CommonVars.getCurrUser()),
							(DzscEmsPorWjHead) tableModelWj.getCurrentRow(),
							false);
					tableModelWj.addRow(headChange);
					setState();
				}
			});
		}
		return btnCopy;
	}

	private JButton getBtnEditDzscEmsNo() {
		if (btnEditDzscEmsNo == null) {
			btnEditDzscEmsNo = new JButton();
			btnEditDzscEmsNo.setText("修改手册号");
			btnEditDzscEmsNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// 权限控制
							dzscAction.checkEditPorWjHeadEmsNo(new Request(
									CommonVars.getCurrUser()));
							if (tableModelWj.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmDzscEmsPorWjHead.this, "请先选择一本合同!",
										"提示", JOptionPane.ERROR_MESSAGE);
								return;
							}
							DzscEmsPorWjHead head = (DzscEmsPorWjHead) tableModelWj
									.getCurrentRow();
							if (head.getEmsNo() == null) {
								JOptionPane.showMessageDialog(
										FmDzscEmsPorWjHead.this, "手册号为空,无法修改！",
										"提示", JOptionPane.ERROR_MESSAGE);
								return;
							}
							DgEditDzscEmsNo dgEditDzscEmsNo = new DgEditDzscEmsNo();
							dgEditDzscEmsNo.setHead("DzscEmsPorWjHead");
							dgEditDzscEmsNo.setOldEmsNo(head.getCorrEmsNo());
							dgEditDzscEmsNo.setVisible(true);
							if (dgEditDzscEmsNo.getHadChange()) {
								head.setCorrEmsNo(dgEditDzscEmsNo.getNewEmsNo());
							}
							tableModelWj.updateRow(head);
						}
					});
		}
		return btnEditDzscEmsNo;
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmDzscEmsPorWjHead.this.dispose();
				}
			});

		}
		return btnExit;
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
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更"); // wj
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelWj.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"请选中要变更的合同！", "提示", 2);
						return;
					}
					DzscEmsPorWjHead head = (DzscEmsPorWjHead) tableModelWj
							.getCurrentRow();
					List list = dzscAction.findIsRepeatChangeWj(new Request(
							CommonVars.getCurrUser()), head, DzscState.CHANGE);
					if (list != null && list.size() > 0) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"已经存在变更合同!", "提示", 2);
						return;
					}
					DzscEmsPorWjHead headChange = null;
					headChange = dzscAction.emsPorChangeWj(new Request(
							CommonVars.getCurrUser()), head, true);
					tableModelWj.addRow(headChange);
					setState();
				}
			});
		}
		return btnChange;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbWj() {
		if (tbWj == null) {
			tbWj = new JTable();
			tbWj.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelWj == null)
								return;
							if (tableModelWj.getCurrentRow() == null)
								return;
							setState();
						}
					});

			tbWj.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {

						if (tableModelWj.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmDzscEmsPorWjHead.this, "请选中要显示的合同！",
									"提示", 2);
							return;
						}
						DgDzscEmsPorWj dg = new DgDzscEmsPorWj();
						dg.setDataState(DataState.BROWSE);
						dg.setTableModelHead(tableModelWj);
						dg.setVisible(true);

					}
				}
			});

		}
		return tbWj;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jScrollPane1.setViewportView(getTbWj());
		}
		return jScrollPane1;
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
			jPanel3.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	// /**
	// * 编辑海关合同
	// * isEditBill 是编辑清单
	// */
	// private void editHg(boolean isEditBill) {
	// if (tableModelHg.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this, "请选中要修改的合同！",
	// "提示", 2);
	// return;
	// }
	// DgDzscEmsPor dg = new DgDzscEmsPor();
	// dg.setDataState(DataState.EDIT);
	// dg.setEditBill(isEditBill);
	// dg.setTableModelHead(tableModelHg);
	// dg.setVisible(true);
	// }

	private void setState() {
		btnEdit.setEnabled(getValueWj().equals(DzscState.ORIGINAL)
				|| getValueWj().equals(DzscState.BACK_BILL)
				|| getValueWj().equals(DzscState.CHANGE)); // 修改合同
		btnDelete.setEnabled(getValueWj().equals(DzscState.ORIGINAL)
				|| getValueWj().equals(DzscState.BACK_BILL)
				|| getValueWj().equals(DzscState.CHANGE)); // 删除
		btnApply.setEnabled(getValueWj().equals(DzscState.ORIGINAL)
				|| getValueWj().equals(DzscState.BACK_BILL)
				|| getValueWj().equals(DzscState.CHANGE));// 申报
		btnDeclare.setEnabled(getValueWj().equals(DzscState.APPLY)); // 备案
		btnChange.setEnabled(getValueWj().equals(DzscState.EXECUTE)); // 变更
		btnCopy.setEnabled(!getValueWj().equals(DzscState.CHANGE)); // 转抄
	}

	private String getValueWj() {
		if (tableModelWj.getCurrentRow() != null) {
			return ((DzscEmsPorWjHead) tableModelWj.getCurrentRow())
					.getDeclareState();
		}
		return "";
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelWj.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
								"请选择要申报的合同", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DzscEmsPorWjHead head = (DzscEmsPorWjHead) tableModelWj
							.getCurrentRow();
					head = dzscAction.findDzscEmsPorWjHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					tableModelWj.updateRow(head);
					if (DzscClientLogic.isEmpty(head, FmDzscEmsPorWjHead.this)) {
						return;
					}
					new ApplyThread().start();
				}
			});
		}
		return btnApply;
	}

	class ApplyThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				DzscEmsPorWjHead head = (DzscEmsPorWjHead) tableModelWj
						.getCurrentRow();
				try {
					DeclareFileInfo fileInfo = dzscAction.applyEmsPorWjHead(
							new Request(CommonVars.getCurrUser()), head);
					head = dzscAction.findDzscEmsPorWjHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					tableModelWj.updateRow(head);
					setState();
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
							fileInfo.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscEmsPorWjHead.this,
							"系统申报失败 " + ex.getMessage(), "确认", 1);
				}
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton
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
			cbIsCancel.setText("\u5df2\u6838\u9500\u7684\u624b\u518c");
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
			btnImport.setText("\u5bfc\u5165");
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
			miImportFromQP.setText("直接导入合同备案");
			miImportFromQP
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// List lsExistEmsNo = new ArrayList();
							// List listWjHead = tableModelWj.getList();
							// for (int i = 0; i < listWjHead.size(); i++) {
							// DzscEmsPorWjHead wjHead = (DzscEmsPorWjHead)
							// listWjHead
							// .get(i);
							// if (DzscState.EXECUTE.equals(wjHead
							// .getDeclareState())) {
							// lsExistEmsNo.add(wjHead.getCorrEmsNo());
							// }
							// }
							// DgDzscQPEmsPorWjHead dg = new
							// DgDzscQPEmsPorWjHead();
							// dg.setLsExistEmsNo(lsExistEmsNo);
							// dg.setVisible(true);
							// List list = dg.getLsResult();
							// boolean isOverWrite = dg.isOverWrite();
							// if (list != null && !list.isEmpty()) {
							// dzscAction.importDzscEmsPorWjFromQP(
							// new Request(CommonVars.getCurrUser(),
							// true), list, isOverWrite);
							// initTable();
							// setState();
							// /com/bestway/dzsc/client/dzscmanage/importDzscEmsPorBill.py
							// }

							// InputStream in=
							// FmDzscEmsPorWjHead.class.getClassLoader().getResourceAsStream("/com/bestway/dzsc/client/dzscmanage/importDzscEmsPorBill.py");

							// 1.创建py文件
							File dir = new File(System
									.getProperty("java.io.tmpdir")
									+ File.separator + "pytemp");
							if (!dir.isDirectory()) {
								dir.mkdirs();
							}

							String path = "/com/bestway/dzsc/client/dzscmanage/importDzscEmsPorWj.py";
							InputStream in = FmDzscEmsPorWjHead.class
									.getResourceAsStream(path);

							// 3.写入文件
							FileOutputStream out = null;
							File fileTmp = null;
							try {
								fileTmp = File.createTempFile(
										"importDzscEmsPorWj", ".py", dir);
								out = new FileOutputStream(fileTmp);
								byte[] bytes = new byte[2048];
								int len = -1;
								while ((len = in.read(bytes)) > 0) {
									out.write(bytes, 0, len);
								}
								out.flush();
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							} finally {
								try {
									out.close();
									in.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}

							}
							int exitVal = 1;
							// 4.执行py文件
							File f = new File("C:\\\\Python27\\python.exe");
							String cmd = "";
							if (f.exists()) {
								cmd = "C:\\\\Python27\\python.exe  \""
										+ fileTmp.getAbsolutePath() + "\"";
							} else {
								cmd = "python  \"" + fileTmp.getAbsolutePath()
										+ "\"";
							}
							String resultStr = "";
							String resultErrorStr = "";
							try {
								Runtime rt = Runtime.getRuntime();
								Process subprocess = rt.exec(cmd);

								BufferedReader br = new BufferedReader(
										new InputStreamReader(subprocess
												.getInputStream()));
								BufferedReader ebr = new BufferedReader(
										new InputStreamReader(subprocess
												.getErrorStream()));
								String line = null;
								while ((line = br.readLine()) != null) {
									resultStr += line;
								}

								line = null;
								while ((line = ebr.readLine()) != null) {
									resultErrorStr += line;
								}
								exitVal = subprocess.waitFor();
								System.out.println("Process exitValue: "
										+ exitVal);
								System.out.println("resultErrorStr----->"
										+ resultErrorStr);
								System.out.println("resultStr------->"
										+ resultStr);
							} catch (Exception ex) {
								ex.printStackTrace();
							}

							if (!resultStr.isEmpty()) {
								dzscAction.importDzscEmsPorWjFromQP(
										new Request(CommonVars.getCurrUser(),
												true), resultStr);// --------------
								initTable();
								setState();
							}
							fileTmp.delete();
						}
					});
		}
		return miImportFromQP;
	}

	private void importQP() {

	}

}
