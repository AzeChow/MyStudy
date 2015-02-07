/*
 * Created on 2004-10-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptInitBill;
import com.bestway.common.fpt.entity.FptInitBillItem;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFptInitBill extends DgCommon {

	private JPanel pnMaster = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JTextField tfBillCode = null;

	private JCalendarComboBox cbbEffectiveDate = null;

	private JComboBox cbbScmCoc = null;

	private JTextField tfMaterielProductFlag = null;

	private JTextField tfUser = null;

	private JTextField tfItemCount = null;

	private JTextField tfBCompayCode = null;

	private JTextField tfBCompanyName = null;

	private JTextField tfMemo = null;

	private JButton btnMasterAdd = null;

	private JButton btnMasterEdit = null;

	private JButton btnMasterSave = null;

	private JButton btnMasterUndo = null;

	private JButton btnDetailAdd = null;

	private JButton btnDetailEdit = null;

	private JButton btnEffective = null;

	private JButton btnUnreel = null;

	private JButton btnExit = null;

	private JButton btnDetailDelete = null;

	private FptInitBill transferFactoryInitBill = null; // 公共变量,起初单单据数据  //  @jve:decl-index=0:

	private JTableListModel commInfoTableModel = null;

	private JTableListModel transferTableModel = null;

	private FptManageAction fptManageAction = null;  //  @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private boolean isImport = true;

	private JLabel jLabel9 = null;

	private JTextField jTextField = null;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JLabel lbScmCoc = null;

	private JLabel jLabel91 = null;

	private JTextField tfCustomsEnvelopBillCode = null;

	private JButton btnCustomsEnvelopBillCode = null;

	/**
	 * @return Returns the transferTableModel.
	 */
	public JTableListModel getTransferTableModel() {
		return transferTableModel;
	}

	/**
	 * @param transferTableModel
	 *            The transferTableModel to set.
	 */
	public void setTransferTableModel(JTableListModel transferTableModel) {
		this.transferTableModel = transferTableModel;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgFptInitBill() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("进出货转厂期初单");
		this.setContentPane(getJContentPane());
		this.setSize(664, 524);
		// this.addWindowListener(new java.awt.event.WindowAdapter() {
		// public void windowOpened(java.awt.event.WindowEvent e) {
		// }
		// });
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			if (dataState == DataState.ADD) {
				showPrimalData();
			} else {
				if (transferTableModel != null) {
					transferFactoryInitBill = (FptInitBill) transferTableModel
							.getCurrentRow();
				}
				showMasterData();
				showDetailData();
			}
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMaster() {
		if (pnMaster == null) {
			jLabel91 = new JLabel();
			jLabel91.setBounds(new Rectangle(328, 133, 77, 18));
			jLabel91.setText("\u5173\u5c01\u53f7");
			lbScmCoc = new JLabel();
			lbScmCoc.setBounds(new java.awt.Rectangle(41, 45, 85, 25));
			lbScmCoc.setText("JLabel");
			jLabel9 = new JLabel();
			pnMaster = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			pnMaster.setLayout(null);
			jLabel.setBounds(41, 19, 84, 22);
			jLabel.setText("单据号");
			jLabel1.setBounds(328, 21, 82, 21);
			jLabel1.setText("生效日期");
			jLabel3.setBounds(41, 131, 82, 21);
			jLabel3.setText("录入单位名称");
			jLabel4.setBounds(41, 161, 84, 22);
			jLabel4.setText("备注");
			jLabel5.setBounds(41, 73, 84, 22);
			jLabel5.setText("录入员代号");
			jLabel6.setBounds(328, 79, 82, 21);
			jLabel6.setText("料件成品标志");
			jLabel7.setBounds(328, 108, 82, 21);
			jLabel7.setText("商品项数");
			jLabel8.setBounds(41, 103, 84, 22);
			jLabel8.setText("录入单位代号");
			jLabel9.setBounds(328, 49, 84, 24);
			jLabel9.setText("客户供应商代码");
			pnMaster.add(jLabel, null);
			pnMaster.add(jLabel1, null);
			pnMaster.add(jLabel3, null);
			pnMaster.add(jLabel4, null);
			pnMaster.add(jLabel5, null);
			pnMaster.add(jLabel6, null);
			pnMaster.add(jLabel7, null);
			pnMaster.add(jLabel8, null);
			pnMaster.add(getTfBillCode(), null);
			pnMaster.add(getCbbEffectiveDate(), null);
			pnMaster.add(getCbbScmCoc(), null);
			pnMaster.add(getTfMaterielProductFlag(), null);
			pnMaster.add(getTfUser(), null);
			pnMaster.add(getTfItemCount(), null);
			pnMaster.add(getTfBCompayCode(), null);
			pnMaster.add(getTfBCompanyName(), null);
			pnMaster.add(getTfMemo(), null);
			pnMaster.add(jLabel9, null);
			pnMaster.add(getJTextField(), null);
			pnMaster.add(lbScmCoc, null);
			pnMaster.add(jLabel91, null);
			pnMaster.add(getTfCustomsEnvelopBillCode(), null);
			pnMaster.add(getBtnCustomsEnvelopBillCode(), null);
		}
		return pnMaster;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnMasterAdd());
			jToolBar.add(getBtnMasterEdit());
			jToolBar.add(getBtnMasterSave());
			jToolBar.add(getBtnMasterUndo());
			jToolBar.add(getBtnEffective());
			jToolBar.add(getBtnUnreel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (commInfoTableModel.getCurrentRow() == null) {
							return;
						}
						boolean isEffective = false;
						if (transferFactoryInitBill != null) {
							isEffective = transferFactoryInitBill
									.getIsEffective() == null ? false
									: transferFactoryInitBill.getIsEffective()
											.booleanValue();
						}
						DgFptInitBillItem dg = new DgFptInitBillItem();
						dg.setTableModel(commInfoTableModel);
						dg.setEffective(isEffective);
						dg.setDataState(DataState.EDIT);
						dg.setVisible(true);
					}
				}
			});
		}
		return jTable;
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
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnDetailAdd());
			jToolBar1.add(getBtnDetailEdit());
			jToolBar1.add(getBtnDetailDelete());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes tfBillCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillCode() {
		if (tfBillCode == null) {
			tfBillCode = new JTextField();
			tfBillCode.setBounds(131, 20, 176, 23);
			tfBillCode.setEditable(false);
		}
		return tfBillCode;
	}

	/**
	 * This method initializes cbbEffectiveDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEffectiveDate() {
		if (cbbEffectiveDate == null) {
			cbbEffectiveDate = new JCalendarComboBox();
			cbbEffectiveDate.setBounds(414, 20, 187, 23);
			cbbEffectiveDate.setEnabled(true);
		}
		return cbbEffectiveDate;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(131, 47, 176, 23);
			cbbScmCoc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbScmCoc.getSelectedItem() != null) {
						ScmCoc s = (ScmCoc) cbbScmCoc.getSelectedItem();
						jTextField.setText(s != null ? s.getCode() : "");
					} else {
						jTextField.setText("");
					}
				}
			});
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes tfMaterielProductFlag
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielProductFlag() {
		if (tfMaterielProductFlag == null) {
			tfMaterielProductFlag = new JTextField();
			tfMaterielProductFlag.setBounds(414, 74, 187, 23);
			tfMaterielProductFlag.setEditable(false);
		}
		return tfMaterielProductFlag;
	}

	/**
	 * This method initializes tfUser
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUser() {
		if (tfUser == null) {
			tfUser = new JTextField();
			tfUser.setBounds(131, 74, 176, 23);
			tfUser.setEditable(false);
		}
		return tfUser;
	}

	/**
	 * This method initializes tfItemCount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfItemCount() {
		if (tfItemCount == null) {
			tfItemCount = new JTextField();
			tfItemCount.setBounds(414, 103, 187, 23);
			tfItemCount.setEditable(false);
		}
		return tfItemCount;
	}

	/**
	 * This method initializes tfBCompayCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBCompayCode() {
		if (tfBCompayCode == null) {
			tfBCompayCode = new JTextField();
			tfBCompayCode.setBounds(131, 103, 176, 23);
			tfBCompayCode.setEditable(false);
		}
		return tfBCompayCode;
	}

	/**
	 * This method initializes tfBCompanyName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBCompanyName() {
		if (tfBCompanyName == null) {
			tfBCompanyName = new JTextField();
			tfBCompanyName.setBounds(131, 130, 175, 23);
			tfBCompanyName.setEditable(false);
		}
		return tfBCompanyName;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(129, 160, 473, 23);
		}
		return tfMemo;
	}

	/**
	 * This method initializes btnMasterAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMasterAdd() {
		if (btnMasterAdd == null) {
			btnMasterAdd = new JButton();
			btnMasterAdd.setText("新增");
			btnMasterAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showPrimalData();
					dataState = DataState.ADD;
					setState();
				}
			});
		}
		return btnMasterAdd;
	}

	/**
	 * This method initializes btnMasterEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMasterEdit() {
		if (btnMasterEdit == null) {
			btnMasterEdit = new JButton();
			btnMasterEdit.setText("修改");
			btnMasterEdit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							dataState = DataState.EDIT;
							setState();
						}
					});
		}
		return btnMasterEdit;
	}

	/**
	 * This method initializes btnMasterSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMasterSave() {
		if (btnMasterSave == null) {
			btnMasterSave = new JButton();
			btnMasterSave.setText("保存");
			btnMasterSave
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (dataState == DataState.ADD) {
								transferFactoryInitBill = new FptInitBill();
								String maxBillCode = fptManageAction
										.getTransferFactoryInitBillMaxCode(new Request(
												CommonVars.getCurrUser()));
								tfBillCode.setText(maxBillCode);
							}
							saveData();
							dataState = DataState.BROWSE;
							setState();
						}
					});
		}
		return btnMasterSave;
	}

	/**
	 * This method initializes btnMasterUndo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMasterUndo() {
		if (btnMasterUndo == null) {
			btnMasterUndo = new JButton();
			btnMasterUndo.setText("取消");
			btnMasterUndo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (dataState == DataState.BROWSE) {
								showEmptyData();
							} else {
								showMasterData();
								showDetailData();
							}
							dataState = DataState.BROWSE;
							setState();
						}
					});
		}
		return btnMasterUndo;
	}

	/**
	 * This method initializes btnDetailAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDetailAdd() {
		if (btnDetailAdd == null) {
			btnDetailAdd = new JButton();
			btnDetailAdd.setText("新增");
			btnDetailAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (transferFactoryInitBill == null) {
						JOptionPane.showMessageDialog(
								DgFptInitBill.this,
								"请先新增初始化单据的主资料!", "提示", 0);
						return;
					}
					if ("".equals(tfCustomsEnvelopBillCode.getText().trim())) {
						JOptionPane.showMessageDialog(
								DgFptInitBill.this, "请输入关封号", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// TempMateriel tempMateriel = null;
					FptInitBillItem initBillCommodityInfo = null;
					List commodityInfoes = new ArrayList();
					// ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
					List lsTemp = FptQuery.getInstance()
							.findComplexNotInInitBill(
									transferFactoryInitBill.getId(),
									tfCustomsEnvelopBillCode.getText().trim());
					if (lsTemp.size() <= 0) {
						return;
					}
//					for (int i = 0; i < lsTemp.size(); i++) {
//						CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo = (CustomsEnvelopCommodityInfo) lsTemp
//								.get(i);
//						initBillCommodityInfo = new TransferFactoryInitBillCommodityInfo();
//						// initBillCommodityInfo.setMateriel(tempMateriel
//						// .getMateriel());
//						initBillCommodityInfo
//								.setEmsNo(customsEnvelopCommodityInfo
//										.getEmsNo());
//						initBillCommodityInfo
//								.setSeqNum(customsEnvelopCommodityInfo
//										.getSeqNum());
//						initBillCommodityInfo
//								.setComplex(customsEnvelopCommodityInfo
//										.getComplex());
//						initBillCommodityInfo
//								.setCommName(customsEnvelopCommodityInfo
//										.getPtName());
//						initBillCommodityInfo
//								.setCommSpec(customsEnvelopCommodityInfo
//										.getPtSpec());
//						initBillCommodityInfo
//								.setUnit(customsEnvelopCommodityInfo.getUnit());
//						initBillCommodityInfo
//								.setTransferFactoryInitBill(transferFactoryInitBill);
//						commodityInfoes.add(initBillCommodityInfo);
//					}
					transferFactoryInitBill = fptManageAction
							.saveTransferFactoryInitBillCommodityInfo(
									new Request(CommonVars.getCurrUser()),
									transferFactoryInitBill, commodityInfoes);
					transferTableModel.updateRow(transferFactoryInitBill);
					if (dataState == DataState.EDIT
							&& (commInfoTableModel.getList().size() <= 0)) {
						saveData();
						dataState = DataState.EDIT;
					}
					showDetailData();
					showMasterData();
					setState();
				}
			});
		}
		return btnDetailAdd;
	}

	/**
	 * This method initializes btnDetailEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDetailEdit() {
		if (btnDetailEdit == null) {
			btnDetailEdit = new JButton();
			btnDetailEdit.setText("修改");
			btnDetailEdit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (commInfoTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgFptInitBill.this,
										"请选择要修改的资料!", "提示", 0);
								return;
							}
							DgFptInitBillItem dg = new DgFptInitBillItem();
							dg.setTableModel(commInfoTableModel);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					});
		}
		return btnDetailEdit;
	}

	/**
	 * This method initializes btnEffective
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEffective() {
		if (btnEffective == null) {
			btnEffective = new JButton();
			btnEffective.setText("生效");
			btnEffective.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					transferFactoryInitBill.setIsEffective(new Boolean(true));
					if (transferFactoryInitBill.getEffectiveDate() == null) {
						transferFactoryInitBill.setEffectiveDate(new Date());
						cbbEffectiveDate.setValue(transferFactoryInitBill
								.getEffectiveDate());
					}
					fptManageAction.saveTransferFactoryInitBill(
							new Request(CommonVars.getCurrUser()),
							transferFactoryInitBill);
					transferTableModel.updateRow(transferFactoryInitBill);
					setState();
				}
			});
		}
		return btnEffective;
	}

	/**
	 * This method initializes btnUnreel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUnreel() {
		if (btnUnreel == null) {
			btnUnreel = new JButton();
			btnUnreel.setText("回卷");
			btnUnreel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					transferFactoryInitBill.setIsEffective(new Boolean(false));
					// transferFactoryInitBill.setEffectiveDate(null);
					//cbbEffectiveDate.setValue(transferFactoryInitBill
					//		.getEffectiveDate());
					fptManageAction.saveTransferFactoryInitBill(
							new Request(CommonVars.getCurrUser()),
							transferFactoryInitBill);
					transferTableModel.updateRow(transferFactoryInitBill);
					setState();
				}
			});
		}
		return btnUnreel;
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
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes btnDetailDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDetailDelete() {
		if (btnDetailDelete == null) {
			btnDetailDelete = new JButton();
			btnDetailDelete.setText("删除");
			btnDetailDelete
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (commInfoTableModel.getCurrentRows().size() < 1) {
								JOptionPane.showMessageDialog(
										DgFptInitBill.this,
										"请选择要删除的资料!", "提示", 0);
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgFptInitBill.this,
									"是否确定删除数据!!!", "提示", 0) != 0) {
								return;
							}
							List list = commInfoTableModel.getCurrentRows();
							transferFactoryInitBill = fptManageAction
									.deleteTransferFactoryInitBillCommodityInfo(
											new Request(CommonVars
													.getCurrUser()),
											transferFactoryInitBill, list);
							transferTableModel
									.updateRow(transferFactoryInitBill);
							commInfoTableModel.deleteRows(list);
							showMasterData();
							setState();
						}
					});
		}
		return btnDetailDelete;
	}

	private void showMasterData() {
		if (transferFactoryInitBill == null) {
			showEmptyData();
			return;
		}
		this.tfBillCode.setText(transferFactoryInitBill
				.getFptInitBillCode());
		this.cbbEffectiveDate.setValue(transferFactoryInitBill
				.getEffectiveDate());
		this.cbbScmCoc.setSelectedItem(transferFactoryInitBill.getScmCoc());
		this.jTextField
				.setText(transferFactoryInitBill.getScmCoc() != null ? transferFactoryInitBill
						.getScmCoc().getCode()
						: "");
		if (transferFactoryInitBill.getIsImpExpFlag() != null) {
			if (transferFactoryInitBill.getIsImpExpFlag()) {
				this.tfMaterielProductFlag.setText("料件");
			} else {
				this.tfMaterielProductFlag.setText("成品");
			}
		} else {
			this.tfMaterielProductFlag.setText("");
		}
		this.tfUser.setText(transferFactoryInitBill.getAclUser() == null ? ""
				: transferFactoryInitBill.getAclUser().getLoginName());
		this.tfItemCount
				.setText(transferFactoryInitBill.getItemCount() == null ? ""
						: transferFactoryInitBill.getItemCount().toString());
		this.tfBCompayCode.setText(transferFactoryInitBill
				.getBuildCompanyCode());
		this.tfBCompanyName.setText(transferFactoryInitBill
				.getBuildCompanyName());
		this.tfCustomsEnvelopBillCode.setText(transferFactoryInitBill
				.getEnvelopNo());
		this.tfMemo.setText(transferFactoryInitBill.getMemo());
	}

	private void showDetailData() {
		List list = new ArrayList();
		if (transferFactoryInitBill != null) {
			list = this.fptManageAction
					.findTransferFactoryInitCommodityInfo(new Request(
							CommonVars.getCurrUser()), transferFactoryInitBill
							.getId());
		}
		initTable(list);
	}
	
	private void initTable(List list){
		commInfoTableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("账册/手册号", "emsNo", 100));
						list.add(addColumn("账册/手册序号", "seqNum", 80));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 150));
						list.add(addColumn("型号规格", "commSpec", 150));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("结余", "noTransferQuantity", 80));
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	private void showEmptyData() {
		this.tfBillCode.setText("");
		// this.cbbEffectiveDate.setValue(null);
		this.cbbScmCoc.setSelectedItem(null);
		this.tfMaterielProductFlag.setText("");
		this.tfItemCount.setText("");
		this.tfItemCount.setText("");
		this.tfBCompayCode.setText("");
		this.tfBCompanyName.setText("");
		this.jTextField.setText("");
		this.tfMemo.setText("");
		initTable(new ArrayList());
	}

	/**
	 * 
	 */
	private void showPrimalData() {
		showEmptyData();
		String maxBillCode = fptManageAction
				.getTransferFactoryInitBillMaxCode(new Request(CommonVars
						.getCurrUser()));
		tfBillCode.setText(maxBillCode);
		tfUser.setText(CommonVars.getCurrUser().getLoginName());
		tfMaterielProductFlag.setText(isImport == true ? "料件" : "成品");
		Company company = (Company) CommonVars.getCurrUser().getCompany();
		this.tfBCompayCode.setText(company.getBuCode());
		this.tfBCompanyName.setText(company.getBuName());
		this.cbbEffectiveDate.setValue(null);
	}

	private void fillMasterData() {
		if (dataState == DataState.ADD) {
			this.transferFactoryInitBill.setBuildDate(new Date());
		}
		this.transferFactoryInitBill
				.setFptInitBillCode(this.tfBillCode.getText());
		this.transferFactoryInitBill.setEffectiveDate(this.cbbEffectiveDate
				.getDate());
		this.transferFactoryInitBill.setScmCoc((ScmCoc) this.cbbScmCoc
				.getSelectedItem());
		this.transferFactoryInitBill.setAclUser(CommonVars.getCurrUser());
		this.transferFactoryInitBill.setBuildCompanyCode(this.tfBCompayCode
				.getText());
		this.transferFactoryInitBill.setBuildCompanyName(this.tfBCompanyName
				.getText());
		this.transferFactoryInitBill.setMemo(this.tfMemo.getText());
		this.transferFactoryInitBill.setIsImpExpFlag(isImport);
		this.transferFactoryInitBill.setCompany(CommonVars.getCurrUser()
				.getCompany());
		this.transferFactoryInitBill.setEffectiveDate(this.cbbEffectiveDate
				.getDate());
		this.transferFactoryInitBill.setEnvelopNo(this.tfCustomsEnvelopBillCode
				.getText().trim());
	}

	private void initUIComponents() {
		List list = new ArrayList();
		if (this.isImport() == true) {
			list = super.getManufacturer();
			this.lbScmCoc.setText("供应商名称");
		} else {
			list = super.getCustomer();
			this.lbScmCoc.setText("客户名称");
		}
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
	}

	private void setState() {
		boolean isEffective = false;
		if (transferFactoryInitBill != null) {
			isEffective = this.transferFactoryInitBill.getIsEffective() == null ? false
					: this.transferFactoryInitBill.getIsEffective()
							.booleanValue();
		}
		this.btnMasterAdd.setEnabled(dataState == DataState.BROWSE
				&& !isEffective);
		this.btnMasterEdit.setEnabled(dataState == DataState.BROWSE
				&& !isEffective);
		this.btnMasterSave.setEnabled(dataState != DataState.BROWSE);
		this.btnMasterUndo.setEnabled(dataState != DataState.BROWSE);
		this.btnEffective.setEnabled(dataState == DataState.BROWSE
				&& !isEffective);
		this.btnUnreel.setEnabled(dataState == DataState.BROWSE && isEffective);
		this.btnExit.setEnabled(dataState == DataState.BROWSE);
		this.btnDetailAdd
				.setEnabled(dataState != DataState.ADD && !isEffective);
		this.btnDetailEdit.setEnabled(dataState != DataState.ADD
				&& !isEffective);
		this.btnDetailDelete.setEnabled(dataState != DataState.ADD
				&& !isEffective);
		this.tfCustomsEnvelopBillCode.setEditable(dataState != DataState.BROWSE
				&& !isEffective && commInfoTableModel.getList().size() <= 0);
		btnCustomsEnvelopBillCode.setEnabled(dataState != DataState.BROWSE
				&& !isEffective && commInfoTableModel.getList().size() <= 0);
		this.cbbScmCoc.setEnabled(dataState != DataState.BROWSE);
		this.tfMemo.setEnabled(dataState != DataState.BROWSE);
		this.cbbEffectiveDate.setEnabled(dataState != DataState.BROWSE
				&& !isEffective);
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(414, 47, 187, 23);
		}
		return jTextField;
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
			jSplitPane.setDividerSize(6);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(230);
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getPnMaster(), java.awt.BorderLayout.CENTER);
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
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfCustomsEnvelopBillCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsEnvelopBillCode() {
		if (tfCustomsEnvelopBillCode == null) {
			tfCustomsEnvelopBillCode = new JTextField();
			tfCustomsEnvelopBillCode
					.setBounds(new Rectangle(414, 130, 167, 24));
		}
		return tfCustomsEnvelopBillCode;
	}

	/**
	 * This method initializes btnCustomsEnvelopBillCode
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCustomsEnvelopBillCode() {
		if (btnCustomsEnvelopBillCode == null) {
			btnCustomsEnvelopBillCode = new JButton();
			btnCustomsEnvelopBillCode
					.setBounds(new Rectangle(580, 130, 21, 23));
			btnCustomsEnvelopBillCode.setText("...");
			btnCustomsEnvelopBillCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							ScmCoc scmCoc = (ScmCoc) cbbScmCoc
									.getSelectedItem();
//							Object obj = TransferFactoryQuery.getInstance()
//									.findCustomsEnvelopBill(isImport, true,
//											scmCoc);
//							if (obj != null) {
//								CustomsEnvelopBill c = (CustomsEnvelopBill) obj;
//								tfCustomsEnvelopBillCode.setText(c
//										.getCustomsEnvelopBillNo());
//							}
						}
					});
		}
		return btnCustomsEnvelopBillCode;
	}

	private void saveData() {
		fillMasterData();
		transferFactoryInitBill = fptManageAction
				.saveTransferFactoryInitBill(new Request(CommonVars
						.getCurrUser()), transferFactoryInitBill);
		if (dataState == DataState.ADD) {
			transferTableModel.addRow(transferFactoryInitBill);
		} else if (dataState == DataState.EDIT) {
			transferTableModel.updateRow(transferFactoryInitBill);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
