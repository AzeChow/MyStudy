package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementGroupDetail;
import com.bestway.fixasset.entity.ImpAgreementCommInfo;
import com.bestway.fixasset.entity.TempGroupImpCommInfo;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgAgreementGroup extends JDialogBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jToolBar = null;

	private JButton btnAddSelect = null;

	private JButton btnDeleteSelect = null;

	private JScrollPane jScrollPane = null;

	private JTable tbImp = null;

	private JToolBar jToolBar1 = null;

	private JButton btnDeleteGroup = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnEditSelect = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbSelect = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbGroup = null;

	private JButton btnAddGroup = null;

	private JCustomFormattedTextField tfGroupNo = null;

	private JLabel jLabel3 = null;

	private JButton btnMakeBill = null;

	private JButton btnHandIn = null;

	private Agreement agreement;

	private FixAssetAction fixAssetAction; // @jve:decl-index=0:

	private JTableListModel tableModelImp;

	private JTableListModel tableModelSelect;

	private JTableListModel tableModelGroup;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JCheckBox cbInvoice = null;

	private JCheckBox cbWar = null;

	private JCheckBox cbDutyCert = null;

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public DgAgreementGroup() {
		super();
		initialize();
	}

	public DgAgreementGroup(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgAgreementGroup(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgAgreementGroup(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(751, 499));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("分组集结");
		this.setContentPane(getJContentPane());
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.tfGroupNo.setValue(1);
			showImpCommInfo();
			initSelectTable(new ArrayList());
			List lsGroup = fixAssetAction.findAgreementGroupDetailNotHandIn(
					new Request(CommonVars.getCurrUser()), agreement);
			initGroupTable(lsGroup);
		}
		super.setVisible(b);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jSplitPane.setDividerSize(4);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setBottomComponent(getJSplitPane1());
			jSplitPane.setTopComponent(getJPanel());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("待集结的物品：");
			jLabel.setForeground(java.awt.Color.blue);
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(jLabel, java.awt.BorderLayout.NORTH);
			jPanel.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerLocation(300);
			jSplitPane1.setLeftComponent(getJPanel1());
			jSplitPane1.setRightComponent(getJPanel2());
			jSplitPane1.setDividerSize(4);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("选取的物品（可拆分数量）");
			jLabel1.setForeground(java.awt.Color.blue);
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(jLabel1, java.awt.BorderLayout.NORTH);
			jPanel1.add(getJJToolBarBar(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
			jLabel2 = new JLabel();
			jLabel2.setText("已集结的物品：");
			jLabel2.setForeground(java.awt.Color.blue);
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar1(), java.awt.BorderLayout.SOUTH);
			jPanel2.add(jLabel2, java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAddSelect());
			jToolBar.add(getBtnDeleteSelect());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddSelect() {
		if (btnAddSelect == null) {
			btnAddSelect = new JButton();
			btnAddSelect.setText("加入");
			btnAddSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelImp.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreementGroup.this,
								"请选择商品", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					ImpAgreementCommInfo commInfo = (ImpAgreementCommInfo) tableModelImp
							.getCurrentRow();
					if (commInfo.getRemainAmount() == null
							|| commInfo.getRemainAmount() <= 0) {
						JOptionPane.showMessageDialog(DgAgreementGroup.this,
								"你选择要集结的商品的数量已为零 ,不能集结", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = tableModelSelect.getList();
					for (int i = 0; i < list.size(); i++) {
						TempGroupImpCommInfo temp = (TempGroupImpCommInfo) list
								.get(i);
						if (commInfo.getMainNo().equals(temp.getMainNo())) {
							return;
						}
					}
					TempGroupImpCommInfo tempCommInfo = addSelectImpCommInfo(commInfo);
					tableModelSelect.addRow(tempCommInfo);
				}
			});
		}
		return btnAddSelect;
	}

	private TempGroupImpCommInfo addSelectImpCommInfo(
			ImpAgreementCommInfo commInfo) {
		TempGroupImpCommInfo tempCommInfo = new TempGroupImpCommInfo();
		tempCommInfo.setMainNo(commInfo.getMainNo());
		tempCommInfo.setComplex(commInfo.getComplex());
		tempCommInfo.setCommName(commInfo.getCommName());
		tempCommInfo.setCommSpec(commInfo.getCommSpec());
		tempCommInfo.setUnit(commInfo.getUnit());
		tempCommInfo.setUnitPrice(commInfo.getUnitPrice());
		tempCommInfo.setAllotAmout(commInfo.getRemainAmount());
		tempCommInfo.setAllotTotalPrice(commInfo.getRemainPrice());
		tempCommInfo.setCountry(commInfo.getCountry());
		return tempCommInfo;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteSelect() {
		if (btnDeleteSelect == null) {
			btnDeleteSelect = new JButton();
			btnDeleteSelect.setText("移除");
			btnDeleteSelect
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelSelect.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgAgreementGroup.this, "请选择商品", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							TempGroupImpCommInfo tempCommInfo = (TempGroupImpCommInfo) tableModelSelect
									.getCurrentRow();
							tableModelSelect.deleteRow(tempCommInfo);
						}
					});
		}
		return btnDeleteSelect;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbImp());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImp() {
		if (tbImp == null) {
			tbImp = new JTable();
		}
		return tbImp;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnDeleteGroup());
			jToolBar1.add(getJPanel4());
			jToolBar1.add(getBtnHandIn());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteGroup() {
		if (btnDeleteGroup == null) {
			btnDeleteGroup = new JButton();
			btnDeleteGroup.setText("从分组移除");
			btnDeleteGroup
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelGroup.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgAgreementGroup.this, "请选择取消集结的商品",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = tableModelGroup.getCurrentRows();
							fixAssetAction
									.removeGroupAgreementCommInfo(new Request(
											CommonVars.getCurrUser()), list);
							tableModelGroup.deleteRows(list);
							showImpCommInfo();
						}
					});
		}
		return btnDeleteGroup;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("组");
			jLabel3.setBounds(new Rectangle(135, 6, 12, 18));
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnEditSelect());
			jJToolBarBar.add(getJPanel3());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditSelect() {
		if (btnEditSelect == null) {
			btnEditSelect = new JButton();
			btnEditSelect.setText("修改");
			btnEditSelect
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelSelect.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgAgreementGroup.this, "请选择商品", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							String input = JOptionPane.showInputDialog(
									DgAgreementGroup.this, "请输入数量", "数量修改",
									JOptionPane.INFORMATION_MESSAGE);
							if (input == null || "".equals(input.trim())) {
								JOptionPane.showMessageDialog(
										DgAgreementGroup.this, "请输入数量", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}

							TempGroupImpCommInfo tempCommInfo = (TempGroupImpCommInfo) tableModelSelect
									.getCurrentRow();
							double allotAmout = Double.valueOf(input.trim());
							List list = tableModelImp.getList();
							for (int i = 0; i < list.size(); i++) {
								ImpAgreementCommInfo commInfo = (ImpAgreementCommInfo) list
										.get(i);
								if (commInfo.getMainNo().equals(
										tempCommInfo.getMainNo())) {
									if (allotAmout > commInfo.getRemainAmount()) {
										JOptionPane
												.showMessageDialog(
														DgAgreementGroup.this,
														"待集结的数量不能大于未集结的数量",
														"提示",
														JOptionPane.INFORMATION_MESSAGE);
										return;
									}
								}
							}
							tempCommInfo.setAllotAmout(allotAmout);
							tempCommInfo.setAllotTotalPrice(CommonVars
									.formatDouble(tempCommInfo.getAllotAmout())
									* CommonVars.formatDouble(tempCommInfo
											.getUnitPrice()));
							tableModelSelect.updateRow(tempCommInfo);
						}
					});
		}
		return btnEditSelect;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbSelect());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSelect() {
		if (tbSelect == null) {
			tbSelect = new JTable();
		}
		return tbSelect;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbGroup());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbGroup() {
		if (tbGroup == null) {
			tbGroup = new JTable();
		}
		return tbGroup;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddGroup() {
		if (btnAddGroup == null) {
			btnAddGroup = new JButton();
			btnAddGroup.setText("集结为第");
			btnAddGroup.setBounds(new Rectangle(22, 1, 82, 28));
			btnAddGroup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelSelect.getList().size() <= 0) {
						JOptionPane.showMessageDialog(DgAgreementGroup.this,
								"请加入要集结的商品", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (tfGroupNo.getValue() == null) {
						JOptionPane.showMessageDialog(DgAgreementGroup.this,
								"请加入组号", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String groupNo = String.valueOf(Double.valueOf(
							tfGroupNo.getValue().toString()).intValue());
					List list = fixAssetAction.groupImpAgreementCommInfo(
							new Request(CommonVars.getCurrUser()), agreement,
							tableModelSelect.getList(), groupNo);
					tableModelGroup.addRows(list);
					tableModelSelect.deleteRows(tableModelSelect.getList());

					showImpCommInfo();
				}
			});
		}
		return btnAddGroup;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfGroupNo() {
		if (tfGroupNo == null) {
			tfGroupNo = new JCustomFormattedTextField();
			tfGroupNo.setBounds(new Rectangle(105, 1, 30, 28));
		}
		return tfGroupNo;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMakeBill() {
		if (btnMakeBill == null) {
			btnMakeBill = new JButton();
			btnMakeBill.setText("刷新以下单证");
			btnMakeBill.setBounds(new Rectangle(5, 1, 113, 27));
			btnMakeBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fixAssetAction.makeOtherBill(new Request(CommonVars
							.getCurrUser()), agreement, cbInvoice.isSelected(),
							cbWar.isSelected(), cbDutyCert.isSelected());
					JOptionPane.showMessageDialog(DgAgreementGroup.this,
							"刷新单证完成!", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnMakeBill;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnHandIn() {
		if (btnHandIn == null) {
			btnHandIn = new JButton();
			btnHandIn.setText("向海关递单");
			btnHandIn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgAgreementGroup.this,
							"一旦递单完成，上述物品将不能再进行分组集结，你确认吗!", "提示",
							JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					List lsGroupHead = new ArrayList();
					List lsGroupDetail = tableModelGroup.getList();
					for (int i = 0; i < lsGroupDetail.size(); i++) {
						AgreementGroupDetail groupDetail = (AgreementGroupDetail) lsGroupDetail
								.get(i);
						if (!lsGroupHead.contains(groupDetail.getGroupHead())) {
							lsGroupHead.add(groupDetail.getGroupHead());
						}
					}
					fixAssetAction.handInBill(new Request(CommonVars
							.getCurrUser()), agreement, lsGroupHead);
					JOptionPane.showMessageDialog(DgAgreementGroup.this,
							"向海关递单完成!", "提示", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			});
		}
		return btnHandIn;
	}

	private void initImpTable(List list) {
		JTableListModel.dataBind(tbImp, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("主序号", "mainNo", 100));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("数量", "remainAmount", 100));
				list.add(addColumn("金额", "remainPrice", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("状态", "stateMark", 100));
				return list;
			}
		});
		tableModelImp = (JTableListModel) tbImp.getModel();
	}

	private void initSelectTable(List list) {
		JTableListModel.dataBind(tbSelect, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("主序号", "mainNo", 100));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("数量", "allotAmout", 100));
				list.add(addColumn("金额", "allotTotalPrice", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("状态", "stateMark", 100));
				return list;
			}
		});
		tableModelSelect = (JTableListModel) tbSelect.getModel();
	}

	private void initGroupTable(List list) {
		JTableListModel.dataBind(tbGroup, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("组号", "groupHead.groupNo", 100));
				list.add(addColumn("主序号", "mainNo", 100));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("金额", "totalPrice", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("状态", "stateMark", 100));
				return list;
			}
		});
		tableModelGroup = (JTableListModel) tbGroup.getModel();
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(getTfGroupNo(), null);
			jPanel3.add(getBtnAddGroup(), null);
		}
		return jPanel3;
	}

	private void showImpCommInfo() {
		List lsAdd = fixAssetAction.findImpCommInfoNotGroup(new Request(
				CommonVars.getCurrUser()), agreement);
		initImpTable(lsAdd);
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.add(getBtnMakeBill(), null);
			jPanel4.add(getCbInvoice(), null);
			jPanel4.add(getCbWar(), null);
			jPanel4.add(getCbDutyCert(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbInvoice() {
		if (cbInvoice == null) {
			cbInvoice = new JCheckBox();
			cbInvoice.setBounds(new Rectangle(125, 1, 65, 14));
			cbInvoice.setSelected(true);
			cbInvoice.setText("发票");
		}
		return cbInvoice;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbWar() {
		if (cbWar == null) {
			cbWar = new JCheckBox();
			cbWar.setBounds(new Rectangle(194, 1, 70, 14));
			cbWar.setSelected(true);
			cbWar.setText("保证书");
		}
		return cbWar;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbDutyCert() {
		if (cbDutyCert == null) {
			cbDutyCert = new JCheckBox();
			cbDutyCert.setBounds(new Rectangle(125, 14, 92, 15));
			cbDutyCert.setSelected(true);
			cbDutyCert.setText("征免税证明");
		}
		return cbDutyCert;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
