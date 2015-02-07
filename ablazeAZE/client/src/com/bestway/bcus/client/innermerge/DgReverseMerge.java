package com.bestway.bcus.client.innermerge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
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
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 
 * 2008-09-16
 * 
 * @author lxr
 * 
 */
public class DgReverseMerge extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel pnThirdMerge = null;
	private JPanel pnSecondMerge = null;
	private JPanel pnFirstMerge = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel7 = null;

	private JButton btnAddMergerBefore = null;
	private JButton btnEditMergerBefore = null;
	private JButton btnDeleteMergerBefore = null;
	private JButton btnExitMergerBefore = null;
	private JButton btnAddMergerTen = null;
	private JButton btnEditMergerTen = null;
	private JButton btnDeleteMergerTen = null;
	private JButton btnExitMergerTen = null;
	private JButton btnAddMergerFour = null;
	private JButton btnEditMergerFour = null;
	private JButton btnDeleteMergerFour = null;
	private JButton btnExitMergerFour = null;
	private JButton btnConcatMergerTen = null;
	private JButton btnConcatMergerBefore = null;

	private JTable tbThirdMerge = null;
	private JTable tbFirstMergeTenCode = null;
	private JTable tbSecondMergeFourCode = null;
	private JTable tbFirstMerge = null;
	private JTable tbSecondMerge = null;

	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane3 = null;
	private JScrollPane jScrollPane5 = null;
	private JScrollPane jScrollPane6 = null;

	private JSplitPane jSplitPane1 = null;
	private JSplitPane jSplitPane = null;

	private JTabbedPane jTabbedPane = null;

	private JToolBar jToolBar3 = null;
	private JToolBar jToolBar1 = null;
	private JToolBar jToolBar = null;
	private JToolBar jToolBar4 = null;
	private JToolBar jToolBar2 = null;

	private JComboBox cbbMaterielTypeTen = null;
	private JComboBox cbbMaterielTypeBefore = null;
	private JComboBox cbbMaterielTypeFour = null;
	/**
	 * 返回是否ok
	 */
	private boolean isOk = false;
	/**
	 * 传递内部归并的物料类别
	 */
	private String materielType = null;

	private JTableListModel tableModelThirdMerge = null;
	private JTableListModel tableModelSecondMerge = null;
	private JTableListModel tableModelFirstMerge = null;
	private CommonBaseCodeAction commonBaseCodeAction = null;

	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel5 = null;
	private JPanel jPanel5 = null;
	private JLabel jLabel8 = null;
	private JPanel jPanel6 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;

	/**
	 * This is the default constructor
	 */
	public DgReverseMerge() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initialize();
		this.initUIComponents();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			new AddThread().start();
		}

		super.setVisible(isFlag, 10);
	}

	/**
	 * 把内部归并的中资料分别插入反向归并实体类中
	 * 
	 * @author ower
	 * 
	 */
	class AddThread extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgReverseMerge.this);
				CommonProgress.setMessage("正在刷新数据表,请稍候...");
				try {

					// 把归并关系中的第三层资料插入ReverseMergeFourData类中
					CommonProgress.setMessage("正在获取4码数据，请稍后...");
					commonBaseCodeAction
							.addInnerMergeDataToReverseMergeFourData(
									new Request(CommonVars.getCurrUser()),
									materielType);
					
					CommonProgress.setMessage("正在获取10码数据，请稍后...");
					 // 把归并关系中的第二层资料插入ReverseMergeTenData类中
					 commonBaseCodeAction
					 .addInnerMergeDataToReverseMergeTenData(
					 new Request(CommonVars.getCurrUser()),
					 materielType);
					
					 CommonProgress.setMessage("正在获取归并前数据，请稍后...");
					 // 把归并关系中的第一层资料插入ReverseMergeBeforeData类中
					 commonBaseCodeAction
					 .addInnerMergeDataToReverseMergeBeforeData(
					 new Request(CommonVars.getCurrUser()),
					 materielType);
					
					 CommonProgress.closeProgressDialog();
				} catch (Exception ex) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgReverseMerge.this,
							"数据显示失败！ " + ex.getMessage(), "确认", 1);
				}
				showPremissData3();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("反向归并");
		this.setSize(723, 533);
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("第三层归并", null, getPnThirdMerge(), null);
			jTabbedPane.addTab("第二层归并", null, getPnSecondMerge(), null);
			jTabbedPane.addTab("第一层归并", null, getPnFirstMerge(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							refreshTable();
							if (jTabbedPane.getSelectedIndex() == 0) {
								showPremissData3();
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								showPremissData2();
							} else if (jTabbedPane.getSelectedIndex() == 2) {
								showPremissData1();
							}
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes pnThirdMerge
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnThirdMerge() {
		if (pnThirdMerge == null) {
			pnThirdMerge = new JPanel();
			pnThirdMerge.setLayout(new BorderLayout());
			pnThirdMerge.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			pnThirdMerge.add(getJPanel7(), BorderLayout.CENTER);
		}
		return pnThirdMerge;
	}

	/**
	 * This method initializes pnSecondMerge
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSecondMerge() {
		if (pnSecondMerge == null) {
			pnSecondMerge = new JPanel();
			pnSecondMerge.setLayout(new BorderLayout());
			pnSecondMerge.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			pnSecondMerge.add(getJToolBar4(), java.awt.BorderLayout.NORTH);
		}
		return pnSecondMerge;
	}

	/**
	 * This method initializes pnFirstMerge
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnFirstMerge() {
		if (pnFirstMerge == null) {
			pnFirstMerge = new JPanel();
			pnFirstMerge.setLayout(new BorderLayout());
			pnFirstMerge.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
			pnFirstMerge.add(getJToolBar3(), java.awt.BorderLayout.NORTH);
		}
		return pnFirstMerge;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd1());
			jToolBar.add(getBtnConcat1());
			jToolBar.add(getBtnEdit1());
			jToolBar.add(getBtnDelete1());
			jToolBar.add(getBtnExit1());
			jToolBar.add(getJPanel6());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnAdd1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd1() {
		if (btnAddMergerBefore == null) {
			btnAddMergerBefore = new JButton();
			btnAddMergerBefore.setText("新增");
			btnAddMergerBefore
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object currentRow = ((JTableListModel) tbFirstMergeTenCode
									.getModel()).getCurrentRow();
							if (currentRow == null) {
								JOptionPane.showMessageDialog(
										DgReverseMerge.this, "请选择十码归并表记录",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = CommonQuery.getInstance()
									.getMaterielNotInInnerMerge(materielType);
							if (list == null || list.size() <= 0) {
								return;
							}
							commonBaseCodeAction.addReverseMergeBeforeData(
									new Request(CommonVars.getCurrUser()),
									list, (ReverseMergeTenData) currentRow);
							list = commonBaseCodeAction
									.findReverseMergeBeforeDataByTen(
											new Request(CommonVars
													.getCurrUser()),
											(ReverseMergeTenData) currentRow);
							initTbFirstMerge(list);
						}
					});
		}
		return btnAddMergerBefore;
	}

	/**
	 * This method initializes btnEdit1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit1() {
		if (btnEditMergerBefore == null) {
			btnEditMergerBefore = new JButton();
			btnEditMergerBefore.setText("修改");
			btnEditMergerBefore.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					editDataByFirstMerge();
				}
			});
		}
		return btnEditMergerBefore;
	}

	/**
	 * 编辑第一层数据
	 * 
	 */
	private void editDataByFirstMerge() {
		List list = tableModelFirstMerge.getCurrentRows();
		if (tableModelFirstMerge.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		boolean isPutOnRecord = commonBaseCodeAction
				.checkWhetherReverseBeforeDataPutOnRecord(new Request(
						CommonVars.getCurrUser()), list);
		if (isPutOnRecord == true) {
			JOptionPane.showMessageDialog(DgReverseMerge.this,
					"你选择的资料已经在归并关系中备案！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgBeforeMergeMateriel dg = new DgBeforeMergeMateriel();
		dg.setTableModel(this.tableModelFirstMerge);
		dg.setMaterielType(this.materielType);
		dg.setVisible(true);
	}

	/**
	 * This method initializes btnDelete1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete1() {
		if (btnDeleteMergerBefore == null) {
			btnDeleteMergerBefore = new JButton();
			btnDeleteMergerBefore.setText("删除");
			btnDeleteMergerBefore
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelFirstMerge.getCurrentRow() != null) {
								if (JOptionPane.showConfirmDialog(
										DgReverseMerge.this, "是否确定删除数据！", "提示",
										0) != 0) {
									return;
								}
								List list = tableModelFirstMerge
										.getCurrentRows();
								boolean isPutOnRecord = commonBaseCodeAction
										.checkWhetherReverseBeforeDataPutOnRecord(
												new Request(CommonVars
														.getCurrUser()), list);
								if (isPutOnRecord == true) {
									JOptionPane.showMessageDialog(
											DgReverseMerge.this,
											"你选择的资料已经在归并关系中备案！", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								commonBaseCodeAction
										.deleteReverseMergeBeforeData(
												new Request(CommonVars
														.getCurrUser()), list);
								tableModelFirstMerge.deleteRows(list);
							} else {
								JOptionPane.showMessageDialog(
										DgReverseMerge.this, "请选择要删除的数据行！",
										"提示", 0);
							}
						}
					});
		}
		return btnDeleteMergerBefore;
	}

	/**
	 * This method initializes btnExit1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit1() {
		if (btnExitMergerBefore == null) {
			btnExitMergerBefore = new JButton();
			btnExitMergerBefore.setText("关闭");
			btnExitMergerBefore
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgReverseMerge.this.dispose();
						}
					});
		}
		return btnExitMergerBefore;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAdd2());
			jToolBar1.add(getBtnConcat2());
			jToolBar1.add(getBtnEdit2());
			jToolBar1.add(getBtnDelete2());
			jToolBar1.add(getBtnExit2());
			jToolBar1.add(getJPanel5());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAdd2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd2() {
		if (btnAddMergerTen == null) {
			btnAddMergerTen = new JButton();
			btnAddMergerTen.setText("新增");
			btnAddMergerTen
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object currentRow = ((JTableListModel) tbSecondMergeFourCode
									.getModel()).getCurrentRow();
							if (currentRow == null) {
								JOptionPane.showMessageDialog(
										DgReverseMerge.this, "请选择四码归并表记录",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgSecondMerge dg = new DgSecondMerge();
							dg.setMaterielType(materielType);
							dg.setTableModel(tableModelSecondMerge);
							dg
									.setReverseMergeFourData((ReverseMergeFourData) currentRow);
							dg.setDataState(DataState.ADD);
							dg.setVisible(true);
						}
					});
		}
		return btnAddMergerTen;
	}

	/**
	 * This method initializes btnEdit2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit2() {
		if (btnEditMergerTen == null) {
			btnEditMergerTen = new JButton();
			btnEditMergerTen.setText("修改");
			btnEditMergerTen
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							editDataBySecondMerge();
						}
					});
		}
		return btnEditMergerTen;
	}

	/**
	 * 编辑第二层数据
	 * 
	 */
	private void editDataBySecondMerge() {
		List list = tableModelSecondMerge.getCurrentRows();
		if (tableModelSecondMerge.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List tempList = commonBaseCodeAction
				.findWhetherReverseMergeInEmsHeadH2k(new Request(CommonVars
						.getCurrUser()), list);
		if (tempList.size() > 0) {
			JOptionPane.showMessageDialog(DgReverseMerge.this,
					"选中的行中有数据在电子帐册中备案,不能修改！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		boolean isPutOnRecord = this.commonBaseCodeAction
				.checkWhetherReverseTenDataPutOnRecord(new Request(CommonVars
						.getCurrUser()), list);
		if (isPutOnRecord) {
			JOptionPane.showMessageDialog(DgReverseMerge.this,
					"你选择的资料已经在归并关系中备案！", "提示", 0);
			return;
		}
		DgSecondMerge dg = new DgSecondMerge();
		dg.setTableModel(tableModelSecondMerge);
		dg.setDataState(DataState.EDIT);
		dg.setReverseMergeTenData((ReverseMergeTenData) tableModelSecondMerge
				.getCurrentRow());
		dg.setMaterielType(materielType);
		dg.setVisible(true);

	}

	/**
	 * This method initializes btnDelete2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete2() {
		if (btnDeleteMergerTen == null) {
			btnDeleteMergerTen = new JButton();
			btnDeleteMergerTen.setText("删除");
			btnDeleteMergerTen
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelSecondMerge.getCurrentRow() != null) {
								if (JOptionPane.showConfirmDialog(
										DgReverseMerge.this, "是否确定删除数据！", "提示",
										0) != 0) {
									return;
								}
								List list = tableModelSecondMerge
										.getCurrentRows();
								// 判断是否在电子帐册中存在
								List tempList = commonBaseCodeAction
										.findWhetherReverseMergeInEmsHeadH2k(
												new Request(CommonVars
														.getCurrUser()), list);
								if (tempList.size() > 0) {
									JOptionPane.showMessageDialog(
											DgReverseMerge.this,
											"选中的行中有数据在电子帐册中备案,不能删除!", "提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
								// 判断是否在归并关系中存在
								boolean isPutOnRecord = commonBaseCodeAction
										.checkWhetherReverseTenDataPutOnRecord(
												new Request(CommonVars
														.getCurrUser()), list);
								if (isPutOnRecord) {
									JOptionPane.showMessageDialog(
											DgReverseMerge.this,
											"你选择的资料已经在归并关系中备案!", "提示", 0);
									return;
								}
								commonBaseCodeAction.deleteReverseMergeTenData(
										new Request(CommonVars.getCurrUser()),
										tableModelSecondMerge.getCurrentRows());
								tableModelSecondMerge.deleteRows(list);
							} else {
								JOptionPane.showMessageDialog(
										DgReverseMerge.this, "请选择要删除的数据行!",
										"提示", 0);
							}
						}
					});
		}
		return btnDeleteMergerTen;
	}

	/**
	 * This method initializes btnExit2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit2() {
		if (btnExitMergerTen == null) {
			btnExitMergerTen = new JButton();
			btnExitMergerTen.setText("关闭");
			btnExitMergerTen
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgReverseMerge.this.dispose();
						}
					});
		}
		return btnExitMergerTen;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getBtnAdd3());
			jToolBar2.add(getBtnEdit3());
			jToolBar2.add(getBtnDelete3());
			jToolBar2.add(getBtnExit3());
			jToolBar2.add(getJPanel2());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes btnAdd3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd3() {
		if (btnAddMergerFour == null) {
			btnAddMergerFour = new JButton();
			btnAddMergerFour.setText("新增");
			btnAddMergerFour
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgThirdMerge dg = new DgThirdMerge(
									DgReverseMerge.this);
							dg.setTableModel(tableModelThirdMerge);
							dg.setDataState(DataState.ADD);
							dg.setMaterielType(materielType);
							dg.setVisible(true);
						}
					});
		}
		return btnAddMergerFour;
	}

	/**
	 * This method initializes btnEdit3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit3() {
		if (btnEditMergerFour == null) {
			btnEditMergerFour = new JButton();
			btnEditMergerFour.setText("修改");
			btnEditMergerFour
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							editDataByThirdMerge();
						}
					});
		}
		return btnEditMergerFour;
	}

	/**
	 * 编辑第三层数据
	 * 
	 */
	private void editDataByThirdMerge() {
		Object currentRow = this.tableModelThirdMerge.getCurrentRow();
		if (currentRow == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示", 0);
			return;
		}
		boolean isPutOnRecord = this.commonBaseCodeAction
				.checkWhetherReverseFourDataPutOnRecord(new Request(CommonVars
						.getCurrUser()), (ReverseMergeFourData) currentRow);
		DgThirdMerge dg = new DgThirdMerge(DgReverseMerge.this);
		dg.setTableModel(tableModelThirdMerge);
		dg.setPutOnRecord(isPutOnRecord);
		dg.setDataState(DataState.EDIT);
		dg.setReverseMergeFourData((ReverseMergeFourData) tableModelThirdMerge
				.getCurrentRow());
		dg.setMaterielType(materielType);
		dg.setVisible(true);
	}

	/**
	 * This method initializes btnDelete3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete3() {
		if (btnDeleteMergerFour == null) {
			btnDeleteMergerFour = new JButton();
			btnDeleteMergerFour.setText("删除");
			btnDeleteMergerFour
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelThirdMerge.getCurrentRow() != null) {
								if (JOptionPane.showConfirmDialog(
										DgReverseMerge.this, "是否确定删除数据!", "提示",
										0) != 0) {
									return;
								}
								List list = tableModelThirdMerge
										.getCurrentRows();
								boolean isPutOnRecord = commonBaseCodeAction
										.checkWhetherReverseFourDataPutOnRecord(
												new Request(CommonVars
														.getCurrUser()), list);
								if (isPutOnRecord == true) {
									JOptionPane.showMessageDialog(
											DgReverseMerge.this,
											"删除的数据中有记录在经营范围备案!", "提示", 0);
									return;
								}
								commonBaseCodeAction
										.deleteReverseMergeFourData(
												new Request(CommonVars
														.getCurrUser()), list);
								tableModelThirdMerge.deleteRows(list);
							} else {
								JOptionPane.showMessageDialog(
										DgReverseMerge.this, "请选择要删除的数据行!",
										"提示", 0);
							}
						}
					});
		}
		return btnDeleteMergerFour;
	}

	/**
	 * This method initializes btnExit3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit3() {
		if (btnExitMergerFour == null) {
			btnExitMergerFour = new JButton();
			btnExitMergerFour.setText("关闭");
			btnExitMergerFour
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgReverseMerge.this.dispose();
						}
					});
		}
		return btnExitMergerFour;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param isOk
	 *            The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * This method initializes tbThirdMerge
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbThirdMerge() {
		if (tbThirdMerge == null) {
			tbThirdMerge = new JTable();
			tbThirdMerge.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editDataByThirdMerge();
					}
				}
			});
		}
		return tbThirdMerge;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(21, 3, 149, 23);
			jLabel.setText("选择要维护的归并资料类型");
			jLabel.setForeground(java.awt.Color.black);
			jPanel2.add(getCbbMaterielType2(), null);
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbMaterielType2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielType2() {
		if (cbbMaterielTypeFour == null) {
			cbbMaterielTypeFour = new JComboBox();
			cbbMaterielTypeFour.setSize(117, 23);
			cbbMaterielTypeFour.setLocation(178, 3);
			cbbMaterielTypeFour.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbbMaterielTypeFour.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterielTypeFour.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbbMaterielTypeFour.addItem(new ItemProperty(MaterielType.MACHINE,
					"设备"));
			cbbMaterielTypeFour.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbbMaterielTypeFour.addItem(new ItemProperty(
					MaterielType.BAD_PRODUCT, "残次品"));
			cbbMaterielTypeFour
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() == ItemEvent.SELECTED) {
								materielType = ((ItemProperty) cbbMaterielTypeFour
										.getSelectedItem()).getCode();
								List list = commonBaseCodeAction
										.findReverseMergeFourDataByType(
												new Request(CommonVars
														.getCurrUser()),
												materielType);
								initTbThirdMerge(list);
							}
						}
					});
		}
		return cbbMaterielTypeFour;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBorder(null);
			jScrollPane.setViewportView(getTbThirdMerge());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(6);
			jSplitPane.setDividerLocation(128);
			jSplitPane.setTopComponent(getJScrollPane1());
			jSplitPane.setBottomComponent(getJPanel3());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes tbSecondMergeFourCode
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSecondMergeFourCode() {
		if (tbSecondMergeFourCode == null) {
			tbSecondMergeFourCode = new JTable();
			tbSecondMergeFourCode.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel dataModel = (JTableListModel) tbSecondMergeFourCode
									.getModel();
							if (dataModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								List list = new ArrayList();
								if (dataModel.getCurrentRow() != null) {
									list = commonBaseCodeAction
											.findReverseMergeTenDataByFour(
													new Request(CommonVars
															.getCurrUser()),
													(ReverseMergeFourData) dataModel
															.getCurrentRow());
								}
								initTbSecondMerge(list);
							}
						}
					});
		}
		return tbSecondMergeFourCode;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbSecondMergeFourCode());
		}
		return jScrollPane1;
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
			jSplitPane1.setDividerLocation(128);
			jSplitPane1.setTopComponent(getJScrollPane3());
			jSplitPane1.setBottomComponent(getJPanel());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes tbFirstMergeTenCode
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFirstMergeTenCode() {
		if (tbFirstMergeTenCode == null) {
			tbFirstMergeTenCode = new JTable();
			tbFirstMergeTenCode.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel dataModel = (JTableListModel) tbFirstMergeTenCode
									.getModel();
							if (dataModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								List list = new ArrayList();
								if (dataModel.getCurrentRow() != null) {
									list = commonBaseCodeAction
											.findReverseMergeBeforeDataByTen(
													new Request(CommonVars
															.getCurrUser()),
													(ReverseMergeTenData) dataModel
															.getCurrentRow());
								}
								initTbFirstMerge(list);
							}
						}
					});
		}
		return tbFirstMergeTenCode;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbFirstMergeTenCode());
		}
		return jScrollPane3;
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane5(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar3
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jToolBar3 = new JToolBar();
			jToolBar3.add(getJPanel1());
		}
		return jToolBar3;
	}

	/**
	 * This method initializes tbFirstMerge
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFirstMerge() {
		if (tbFirstMerge == null) {
			tbFirstMerge = new JTable();
			tbFirstMerge.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editDataByFirstMerge();
					}
				}
			});
		}
		return tbFirstMerge;
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getTbFirstMerge());
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel7 = new JLabel();
			jLabel5 = new JLabel();
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jLabel5.setText("选择要维护的归并资料类型");
			jLabel7.setText("十码归并表");
			jLabel7.setForeground(new java.awt.Color(227, 145, 0));
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.ipadx = 5;
			gridBagConstraints8.ipady = 3;
			gridBagConstraints8.insets = new java.awt.Insets(5, 14, 4, 2);
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.ipadx = 68;
			gridBagConstraints9.ipady = -6;
			gridBagConstraints9.insets = new java.awt.Insets(5, 2, 4, 17);
			gridBagConstraints10.gridx = 2;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.ipadx = 8;
			gridBagConstraints10.ipady = 3;
			gridBagConstraints10.insets = new java.awt.Insets(5, 17, 4, 286);
			jPanel1.add(jLabel5, gridBagConstraints8);
			jPanel1.add(getCbbMaterielType1(), gridBagConstraints9);
			jPanel1.add(jLabel7, gridBagConstraints10);
		}
		return jPanel1;
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
			jPanel3.add(getJScrollPane6(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jToolBar4
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar4() {
		if (jToolBar4 == null) {
			jToolBar4 = new JToolBar();
			jToolBar4.add(getJPanel4());
		}
		return jToolBar4;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel6 = new JLabel();
			jLabel1 = new JLabel();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			jPanel4 = new JPanel();
			jPanel4.setLayout(new GridBagLayout());
			jLabel1.setText("选择要维护的归并资料类型");
			jLabel1.setForeground(java.awt.Color.black);
			jLabel6.setText("四码归并表");
			jLabel6.setForeground(new java.awt.Color(227, 145, 0));
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.ipadx = 6;
			gridBagConstraints11.ipady = 3;
			gridBagConstraints11.insets = new java.awt.Insets(5, 13, 4, 1);
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.ipadx = 65;
			gridBagConstraints12.ipady = -6;
			gridBagConstraints12.insets = new java.awt.Insets(5, 2, 4, 16);
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.ipadx = 3;
			gridBagConstraints13.ipady = -1;
			gridBagConstraints13.insets = new java.awt.Insets(7, 17, 6, 296);
			jPanel4.add(jLabel1, gridBagConstraints11);
			jPanel4.add(getJComboBox2(), gridBagConstraints12);
			jPanel4.add(jLabel6, gridBagConstraints13);
		}
		return jPanel4;
	}

	/**
	 * This method initializes cbbMaterielType2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (cbbMaterielTypeTen == null) {
			cbbMaterielTypeTen = new JComboBox();
			cbbMaterielTypeTen.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbbMaterielTypeTen.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterielTypeTen.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbbMaterielTypeTen.addItem(new ItemProperty(MaterielType.MACHINE,
					"设备"));
			cbbMaterielTypeTen.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbbMaterielTypeTen.addItem(new ItemProperty(
					MaterielType.BAD_PRODUCT, "残次品"));
			cbbMaterielTypeTen
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() == ItemEvent.SELECTED) {
								materielType = ((ItemProperty) cbbMaterielTypeTen
										.getSelectedItem()).getCode();
								List list = commonBaseCodeAction
										.findReverseMergeFourDataByType(
												new Request(CommonVars
														.getCurrUser()),
												materielType);
								if (list == null || list.size() <= 0) {
									initTbSecondMerge(new ArrayList());
								}
								initTbSecondMergeFourCode(list);
							}
						}
					});
		}
		return cbbMaterielTypeTen;
	}

	/**
	 * This method initializes tbSecondMerge
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSecondMerge() {
		if (tbSecondMerge == null) {
			tbSecondMerge = new JTable();
			tbSecondMerge.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editDataBySecondMerge();
					}
				}
			});
		}
		return tbSecondMerge;
	}

	/**
	 * This method initializes jScrollPane6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setViewportView(getTbSecondMerge());
		}
		return jScrollPane6;
	}

	/**
	 * This method initializes cbbMaterielType1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielType1() {
		if (cbbMaterielTypeBefore == null) {
			cbbMaterielTypeBefore = new JComboBox();
			cbbMaterielTypeBefore.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbbMaterielTypeBefore.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterielTypeBefore.addItem(new ItemProperty(
					MaterielType.MATERIEL, "料件"));
			cbbMaterielTypeBefore.addItem(new ItemProperty(
					MaterielType.MACHINE, "设备"));
			cbbMaterielTypeBefore.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbbMaterielTypeBefore.addItem(new ItemProperty(
					MaterielType.BAD_PRODUCT, "残次品"));
			cbbMaterielTypeBefore
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() == ItemEvent.SELECTED) {
								materielType = ((ItemProperty) cbbMaterielTypeBefore
										.getSelectedItem()).getCode();
								List list = commonBaseCodeAction
										.findReverseMergeTenDataByType(
												new Request(CommonVars
														.getCurrUser()),
												materielType);
								if (list == null || list.size() <= 0) {
									initTbFirstMerge(new ArrayList());
								}
								initFirstMergeTenCode(list);
							}
						}
					});
		}
		return cbbMaterielTypeBefore;
	}

	/**
	 * This method initializes btnConcat2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConcat2() {
		if (btnConcatMergerTen == null) {
			btnConcatMergerTen = new JButton();
			btnConcatMergerTen.setText("连接");
			btnConcatMergerTen.setToolTipText("加入内部归并中没有归并成四码的十码数据");
			btnConcatMergerTen.setVisible(false);
			btnConcatMergerTen
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object currentRow = ((JTableListModel) tbSecondMergeFourCode
									.getModel()).getCurrentRow();
							if (currentRow == null) {
								JOptionPane.showMessageDialog(
										DgReverseMerge.this, "请选择四码归并表记录",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = CommonQuery.getInstance()
									.getTenDataNotFourMerge(
											(ReverseMergeFourData) currentRow,
											materielType);
							if (list == null || list.size() <= 0) {
								return;
							}
							commonBaseCodeAction.concatInnerTenAndReverseFour(
									new Request(CommonVars.getCurrUser()),
									list, (ReverseMergeFourData) currentRow);
							list = commonBaseCodeAction
									.findReverseMergeTenDataByFour(new Request(
											CommonVars.getCurrUser()),
											(ReverseMergeFourData) currentRow);
							initTbSecondMerge(list);
						}
					});
		}
		return btnConcatMergerTen;
	}

	/**
	 * This method initializes btnConcat1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConcat1() {
		if (btnConcatMergerBefore == null) {
			btnConcatMergerBefore = new JButton();
			btnConcatMergerBefore.setText("连接");
			btnConcatMergerBefore.setToolTipText("加入内部归并中没有归并成十码的归并前数据");
			btnConcatMergerBefore.setVisible(false);
			btnConcatMergerBefore
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object currentRow = ((JTableListModel) tbFirstMergeTenCode
									.getModel()).getCurrentRow();
							if (currentRow == null) {
								JOptionPane.showMessageDialog(
										DgReverseMerge.this, "请选择十码归并表记录",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = CommonQuery.getInstance()
									.getBeforeDataNotTenMerge(materielType);
							if (list == null || list.size() <= 0) {
								return;
							}
							commonBaseCodeAction
									.concatInnerBeforeAndReverseTen(
											new Request(CommonVars
													.getCurrUser()), list,
											(ReverseMergeTenData) currentRow);
							list = commonBaseCodeAction
									.findReverseMergeBeforeDataByTen(
											new Request(CommonVars
													.getCurrUser()),
											(ReverseMergeTenData) currentRow);
							initTbFirstMerge(list);
						}
					});

		}
		return btnConcatMergerBefore;
	}

	/**
	 * 初始化数据
	 */
	private void initUIComponents() {
		refreshTable();
		List list = new ArrayList();
		initTbFirstMerge(list);
		initTbSecondMerge(list);
	}

	/**
	 * 刷新所有记录
	 * 
	 */
	private void refreshTable() {
		cbbMaterielTypeBefore.setSelectedItem(null);
		cbbMaterielTypeTen.setSelectedItem(null);
		cbbMaterielTypeFour.setSelectedItem(null);
	}

	/**
	 * 显示初期数据1
	 * 
	 */
	private void showPremissData1() {
		this.cbbMaterielTypeBefore.setSelectedIndex(ItemProperty
				.getIndexByCode(this.materielType, this.cbbMaterielTypeBefore));
	}

	/**
	 * 显示初期数据2
	 * 
	 */
	private void showPremissData2() {
		this.cbbMaterielTypeTen.setSelectedIndex(ItemProperty.getIndexByCode(
				this.materielType, this.cbbMaterielTypeTen));
	}

	/**
	 * 显示初期数据3
	 * 
	 */
	private void showPremissData3() {
		this.cbbMaterielTypeFour.setSelectedIndex(ItemProperty.getIndexByCode(
				this.materielType, this.cbbMaterielTypeFour));
	}

	/**
	 * 初始第三层反归并表
	 * 
	 */
	private void initTbThirdMerge(List list) {
		tableModelThirdMerge = new JTableListModel(tbThirdMerge, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("类别", "imrType", 80));
						list.add(addColumn("归并序号", "hsFourNo", 100));
						list.add(addColumn("商品编码", "hsFourCode", 80));
						list.add(addColumn("商品名称", "hsFourMaterielName", 100));
						return list;
					}
				});
		tbThirdMerge.getColumnModel().getColumn(1).setCellRenderer(
				new materielTypeCellRenderer());
		tbThirdMerge
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 初始第二层反归并四码表
	 * 
	 */
	private void initTbSecondMergeFourCode(List list) {
		new JTableListModel(tbSecondMergeFourCode, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("类别", "imrType", 80));
						list.add(addColumn("归并序号", "hsFourNo", 100));
						list.add(addColumn("商品编码", "hsFourCode", 80));
						list.add(addColumn("商品名称", "hsFourMaterielName", 100));
						return list;
					}
				});
		tbSecondMergeFourCode.getColumnModel().getColumn(1).setCellRenderer(
				new materielTypeCellRenderer());
	}

	/**
	 * 初始第二层反归并十码表
	 * 
	 */
	private void initTbSecondMerge(List list) {
		tableModelSecondMerge = new JTableListModel(this.tbSecondMerge, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("十位备案序号", "hsAfterTenMemoNo", 80));
						list.add(addColumn("商品编码", "hsAfterComplex.code", 100));
						list
								.add(addColumn("商品名称",
										"hsAfterMaterielTenName", 80));
						list.add(addColumn("商品规格,型号", "hsAfterMaterielTenSpec",
								100));
						list
								.add(addColumn("备案单位", "hsAfterMemoUnit.name",
										100));
						list.add(addColumn("第一法定单位", "hsAfterLegalUnit.name",
								100));
						list.add(addColumn("第二法定单位",
								"hsAfterSecondLegalUnit.name", 100));
						return list;
					}
				});
		tbSecondMerge
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 初始第一层反归并十码表
	 * 
	 */
	private void initFirstMergeTenCode(List list) {
		new JTableListModel(this.tbFirstMergeTenCode, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("十位备案序号", "hsAfterTenMemoNo", 80));
						list.add(addColumn("商品编码", "hsAfterComplex.code", 100));
						list
								.add(addColumn("商品名称",
										"hsAfterMaterielTenName", 80));
						list.add(addColumn("商品规格,型号", "hsAfterMaterielTenSpec",
								100));
						list
								.add(addColumn("备案单位", "hsAfterMemoUnit.name",
										100));
						list.add(addColumn("第一法定单位", "hsAfterLegalUnit.name",
								100));
						list.add(addColumn("第二法定单位",
								"hsAfterSecondLegalUnit.name", 100));
						return list;
					}
				});
	}

	/**
	 * 初始第一层反归并表
	 * 
	 */
	private void initTbFirstMerge(List list) {
		tableModelFirstMerge = new JTableListModel(this.tbFirstMerge, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("类别", "materiel.scmCoi.name", 80));
						list.add(addColumn("料号", "materiel.ptNo", 100));
						list
								.add(addColumn("商品编码", "materiel.complex.code",
										80));
						list.add(addColumn("工厂商品名称", "materiel.factoryName",
								100));
						list.add(addColumn("工厂型号规格", "materiel.factorySpec",
								100));
						list.add(addColumn("报关商品名称", "materiel.ptName", 100));
						list.add(addColumn("报关型号规格", "materiel.ptSpec", 100));
						list.add(addColumn("报关单位", "materiel.ptUnit.name", 50));
						list.add(addColumn("单价", "materiel.ptPrice", 50));
						list.add(addColumn("净重", "materiel.ptNetWeight", 50));
						list.add(addColumn("版本号", "materiel.ptVersion", 50));
						list
								.add(addColumn("企业单位", "materiel.calUnit.name",
										80));
						list.add(addColumn("折算系数", "materiel.unitConvert", 80));
						return list;
					}
				});
		tbFirstMerge
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 物料类别renderer
	 */
	class materielTypeCellRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			super.setText((value == null) ? "" : castValue1(value));
			return this;
		}

		private String castValue1(Object value) {
			String strValue = value.toString().trim();
			String returnValue = "";
			if (strValue.equals("")) {
				return "";
			}
			if (strValue.equals(MaterielType.FINISHED_PRODUCT)) {
				returnValue = "成品";
			} else if (strValue.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				returnValue = "半成品";
			} else if (strValue.equals(MaterielType.MACHINE)) {
				returnValue = "设备";
			} else if (strValue.equals(MaterielType.REMAIN_MATERIEL)) {
				returnValue = "边角料";
			} else if (strValue.equals(MaterielType.BAD_PRODUCT)) {
				returnValue = "残次品";
			} else if (strValue.equals(MaterielType.MATERIEL)) {
				returnValue = "材料";
			}
			return returnValue;
		}
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLabel8 = new JLabel();
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jLabel8.setBounds(153, 7, 73, 18);
			jLabel8.setText("十码归并表");
			jLabel8.setForeground(new java.awt.Color(227, 145, 0));
			jPanel5.add(jLabel8, null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel9 = new JLabel();
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jLabel9.setText("归并前数据");
			jLabel9.setBounds(156, 5, 73, 18);
			jLabel9.setForeground(new java.awt.Color(227, 145, 0));
			jPanel6.add(jLabel9, null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel7;
	}
} // @jve:decl-index=0:visual-constraint="10,0"

