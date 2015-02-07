package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.TransParameterSet;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransferFactoryCommodityInfo;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgTransferFactoryBill extends DgCommon {

	private JPanel pnMaster = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAddTop = null;

	private JButton btnEditTop = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JButton btnAddBottom = null;

	private JButton btnEditBottom = null;

	private JButton btnDelete = null;

	private JButton btnAvailability = null;

	private JButton btnCancelAvailability = null;

	private JTextField tfMaterielFinishedProductFlag = null;

	private JTextField tfCompanyName = null;

	private JTextField tfAclUserName = null;

	private JTextField tfItemCount = null;

	private JTextField tfTransferFactoryBillNo = null;

	private JComboBox cbbScmCoc = null;

	private JTextField tfMemo = null;

	private JCalendarComboBox cbbBeginAvailability = null;

	private JComboBox cbbWareSet = null;

	private List list = null; // @jve:decl-index=0:

	private JTableListModel commodityTableModel = null;

	private JTableListModel transferFactoryBillModel = null;

	private TransferFactoryBill transferFactoryBill = null;

	private ScmCoc scmCoc = null; // @jve:decl-index=0:

	private boolean isImportGoods = true;

	private int materielProductType = -1;

	private int dataState = -1;

	private String declareState = null;

	private JLabel jLabel9 = null;

	private JTextField tfCustomsEnvelopBillCode = null;

	private JButton btnCustomsEnvelopBillCode = null;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JLabel lbScmCoc = null;

	TransParameterSet transParameterSet = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgTransferFactoryBill() {
		super();
		transParameterSet = super.transferFactoryManageAction
				.findTransParameterSet(new Request(CommonVars.getCurrUser()));
		initialize();
		this.declareState = DeclareState.PROCESS_EXE;

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("结转单据");
		this.setContentPane(getJContentPane());
		this.setSize(673, 484);
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	public void setVisible(boolean b) {
		if (b) {
			initComponents();
			if (dataState == DataState.ADD) {
				emptyData();
				addInitData();
				setState();
			} else if (dataState == DataState.EDIT) {
				transferFactoryBill = (TransferFactoryBill) transferFactoryBillModel
						.getCurrentRow();
				showData(transferFactoryBill);
				setState();
			}
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the scmCoc.
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * @param scmCoc
	 *            The scmCoc to set.
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMaster() {
		if (pnMaster == null) {
			lbScmCoc = new JLabel();
			lbScmCoc.setBounds(new java.awt.Rectangle(340, 21, 88, 24));
			lbScmCoc.setText("JLabel");
			jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			pnMaster = new JPanel();
			pnMaster.setLayout(null);
			jLabel.setText("单据号");
			jLabel.setBounds(46, 21, 67, 24);
			jLabel2.setText("生效日期");
			jLabel2.setBounds(46, 48, 67, 24);
			jLabel3.setText("录入员代码");
			jLabel3.setBounds(46, 102, 69, 24);
			jLabel4.setText("物料成品标识");
			jLabel4.setBounds(340, 48, 88, 24);
			jLabel5.setText("录入单位名称");
			jLabel5.setBounds(340, 102, 86, 24);
			jLabel6.setText("仓库名称");
			jLabel6.setBounds(340, 75, 88, 24);
			jLabel7.setBounds(46, 75, 67, 24);
			jLabel7.setText("商品项数");
			jLabel8.setBounds(46, 128, 65, 24);
			jLabel8.setText("备注");
			jLabel9.setBounds(340, 129, 85, 23);
			jLabel9.setText("关封号");
			pnMaster.add(jLabel, null);
			pnMaster.add(jLabel2, null);
			pnMaster.add(jLabel3, null);
			pnMaster.add(jLabel4, null);
			pnMaster.add(jLabel5, null);
			pnMaster.add(jLabel6, null);
			pnMaster.add(getTfMaterielFinishedProductFlag(), null);
			pnMaster.add(getTfCompanyName(), null);
			pnMaster.add(getTfAclUserName(), null);
			pnMaster.add(getTfItemCount(), null);
			pnMaster.add(getTfTransferFactoryBillNo(), null);
			pnMaster.add(getCbbScmCoc(), null);
			pnMaster.add(jLabel7, null);
			pnMaster.add(getTfMemo(), null);
			pnMaster.add(jLabel8, null);
			pnMaster.add(getCbbBeginAvailability(), null);
			pnMaster.add(getCbbWareSet(), null);
			pnMaster.add(jLabel9, null);
			pnMaster.add(getTfCustomsEnvelopBillCode(), null);
			pnMaster.add(getBtnCustomsEnvelopBillCode(), null);
			pnMaster.add(lbScmCoc, null);
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnAddTop());
			jToolBar.add(getBtnEditTop());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnAvailability());
			jToolBar.add(getBtnCancelAvailability());
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
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editImpExpCommodityInfo();
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes cbbBeginAvailability
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginAvailability() {
		if (cbbBeginAvailability == null) {
			cbbBeginAvailability = new JCalendarComboBox();
			cbbBeginAvailability.setBounds(122, 47, 191, 24);
		}
		return cbbBeginAvailability;
	}

	/**
	 * This method initializes cbbWareSet
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWareSet() {
		if (cbbWareSet == null) {
			cbbWareSet = new JComboBox();
			cbbWareSet.setBounds(431, 75, 176, 24);
		}
		return cbbWareSet;
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1 = new JToolBar();
			jToolBar1.setLayout(f);
			jToolBar1.add(getBtnAddBottom());
			jToolBar1.add(getBtnEditBottom());
			jToolBar1.add(getBtnDelete());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAddTop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddTop() {
		if (btnAddTop == null) {
			btnAddTop = new JButton();
			btnAddTop.setText("新增");
			btnAddTop.setPreferredSize(new Dimension(70, 30));
			btnAddTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					emptyData();
					addInitData();
					dataState = DataState.ADD;
					setState();
				}
			});
		}
		return btnAddTop;
	}

	/**
	 * 
	 * This method initializes btnEditTop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditTop() {
		if (btnEditTop == null) {
			btnEditTop = new JButton();
			btnEditTop.setText("修改");
			btnEditTop.setPreferredSize(new Dimension(70, 30));
			btnEditTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (transferFactoryBill != null) {
						showData(transferFactoryBill);
					}
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEditTop;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(70, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull() == true) {
						return;
					}
					saveData();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setPreferredSize(new Dimension(70, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (transferFactoryBill != null) {
						showData(transferFactoryBill);
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnAddBottom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddBottom() {
		if (btnAddBottom == null) {
			btnAddBottom = new JButton();
			btnAddBottom.setVisible(false);
			btnAddBottom.setText("新增");
			btnAddBottom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					// List lsTemp = TransferFactoryQuery.getInstance()
					// .findTempTransferFactoryCommInfo(
					// tfCustomsEnvelopBillCode.getText().trim());
					// if (lsTemp.size() <= 0) {
					// return;
					// }
					// list = transferFactoryManageAction
					// .saveTransferFactoryCommodityInfo(new Request(
					// CommonVars.getCurrUser()),
					// transferFactoryBill, lsTemp);
					if (dataState == DataState.EDIT
							&& (commodityTableModel.getList().size() <= 0)) {
						saveData();
						dataState = DataState.EDIT;
					}
					commodityTableModel.addRows(list);
					Double leftAmount = transferFactoryManageAction.findTransferFactoryCommodityInfoLeft(
							new Request(CommonVars.getCurrUser()),
							(TransferFactoryCommodityInfo) commodityTableModel
									.getCurrentRow());
					DgTransferFactoryCommodityInfo dg = new DgTransferFactoryCommodityInfo();
					dg.setTableModel(commodityTableModel);
					dg.setDataState(DataState.EDIT);
					dg.setTransferFactoryBill(transferFactoryBill);
					dg.setLeftAmount(leftAmount);
					dg.setVisible(true);
					setState();
				}
			});
		}
		return btnAddBottom;
	}

	/**
	 * This method initializes btnEditBottom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditBottom() {
		if (btnEditBottom == null) {
			btnEditBottom = new JButton();
			btnEditBottom.setText("修改");
			btnEditBottom.setPreferredSize(new Dimension(65, 30));
			btnEditBottom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							editImpExpCommodityInfo();
						}
					});
		}
		return btnEditBottom;
	}

	/**
	 * 编缉数据
	 */
	private void editImpExpCommodityInfo() {
		if (!this.jTable.isEnabled()) {
			return;
		}
		if (commodityTableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择你要修改的资料", "提示", 0);
			return;
		}
		Double leftAmount = transferFactoryManageAction
				.findTransferFactoryCommodityInfoLeft(
						new Request(CommonVars.getCurrUser()),
						(TransferFactoryCommodityInfo) commodityTableModel
								.getCurrentRow());
		DgTransferFactoryCommodityInfo dg = new DgTransferFactoryCommodityInfo();
		dg.setTableModel(commodityTableModel);
		dg.setDataState(DataState.EDIT);
		dg.setTransferFactoryBill(this.getTransferFactoryBill());
		dg.setLeftAmount(leftAmount);
		dg.setVisible(true);
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
			btnDelete.setPreferredSize(new Dimension(65, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commodityTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择你要删除的数据", "提示",
								0);
						return;
					}
					if (JOptionPane.showConfirmDialog(null, "是否确定删除数据!!!",
							"提示", 0) != 0) {
						return;
					}
					List list = commodityTableModel.getCurrentRows();

					transferFactoryManageAction
							.deleteTransferFactoryCommodityInfo(new Request(
									CommonVars.getCurrUser()), list);
					if (list.size() == commodityTableModel.getList().size()) {
						tfCustomsEnvelopBillCode.setText("");
					}
					for (int i = 0; i < list.size(); i++) {
						commodityTableModel.deleteRow(list.get(i));
					}
					setState();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnAvailability
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAvailability() {
		if (btnAvailability == null) {
			btnAvailability = new JButton();
			btnAvailability.setText("生效");
			btnAvailability.setPreferredSize(new Dimension(70, 30));
			btnAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = transferFactoryManageAction
									.findTransferFactoryCommodityInfoByCheck(
											new Request(CommonVars
													.getCurrUser()),
											transferFactoryBill.getId());
							if (list.size() > 0) {
								JOptionPane.showMessageDialog(null,
										"商品信息中数量中有空的数据,\n结转单据记录不能生效!!",
										"非法数据!!",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							beginAvailability(true);
						}
					});
		}
		return btnAvailability;
	}

	/**
	 * This method initializes btnCancelAvailability
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancelAvailability() {
		if (btnCancelAvailability == null) {
			btnCancelAvailability = new JButton();
			btnCancelAvailability.setText("撤消生效");
			btnCancelAvailability.setPreferredSize(new Dimension(70, 30));
			btnCancelAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							//
							// 有数据已报关清单在转厂单据中 或者 有数据已转关封申请单在转厂单据中
							//
							if (transferFactoryManageAction
									.hasDataTransferApplyToCustomsBillByTransferFactoryBill(
											new Request(CommonVars
													.getCurrUser()),
											transferFactoryBill)) {
								JOptionPane.showMessageDialog(
										DgTransferFactoryBill.this,
										"在转厂单据中有数据已转报关清单,不能撤消生效!!", "警告",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (transferFactoryManageAction
									.hasDataCustomsDeclarationByTransFactBill(
											new Request(CommonVars
													.getCurrUser()),
											transferFactoryBill)) {
								JOptionPane.showMessageDialog(
										DgTransferFactoryBill.this,
										"在转厂单据中有数据已转报关单,不能撤消生效!!", "警告",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							beginAvailability(false);
						}
					});
		}
		return btnCancelAvailability;
	}

	/**
	 * This method initializes tfMaterielFinishedProductFlag
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielFinishedProductFlag() {
		if (tfMaterielFinishedProductFlag == null) {
			tfMaterielFinishedProductFlag = new JTextField();
			tfMaterielFinishedProductFlag.setBounds(431, 48, 176, 24);
		}
		return tfMaterielFinishedProductFlag;
	}

	/**
	 * This method initializes tfCompanyName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCompanyName() {
		if (tfCompanyName == null) {
			tfCompanyName = new JTextField();
			tfCompanyName.setBounds(431, 102, 176, 24);
		}
		return tfCompanyName;
	}

	/**
	 * This method initializes tfAclUserName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAclUserName() {
		if (tfAclUserName == null) {
			tfAclUserName = new JTextField();
			tfAclUserName.setBounds(122, 102, 191, 24);
		}
		return tfAclUserName;
	}

	/**
	 * This method initializes tfCustomName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfItemCount() {
		if (tfItemCount == null) {
			tfItemCount = new JTextField();
			tfItemCount.setBounds(122, 75, 191, 24);
		}
		return tfItemCount;
	}

	/**
	 * This method initializes tfTransferFactoryBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTransferFactoryBillNo() {
		if (tfTransferFactoryBillNo == null) {
			tfTransferFactoryBillNo = new JTextField();
			tfTransferFactoryBillNo.setBounds(122, 21, 191, 24);
		}
		return tfTransferFactoryBillNo;
	}

	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getCommodityTableModel() {
		return commodityTableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setCommodityTableModel(JTableListModel tableModel) {
		this.commodityTableModel = tableModel;
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		if (this.isImportGoods == true) {
			return Integer.parseInt(MaterielType.MATERIEL);
		} else {
			return Integer.parseInt(MaterielType.FINISHED_PRODUCT);
		}
	}

	/**
	 * @return Returns the impExpRequestModel.
	 */
	public JTableListModel getTransferFactoryBillModel() {
		return transferFactoryBillModel;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(431, 21, 176, 24);
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(122, 128, 191, 24);
		}
		return tfMemo;
	}

	/**
	 * @return Returns the TransferFactoryBill.
	 */
	public TransferFactoryBill getTransferFactoryBill() {
		return transferFactoryBill;
	}

	/**
	 * @param TransferFactoryBill
	 *            The TransferFactoryBill to set.
	 */
	public void setTransferFactoryBill(TransferFactoryBill TransferFactoryBill) {
		this.transferFactoryBill = TransferFactoryBill;
	}

	/**
	 * @param transferFactoryBillModel
	 *            The transferFactoryBillModel to set.
	 */
	public void setTransferFactoryBillModel(
			JTableListModel transferFactoryBillModel) {
		this.transferFactoryBillModel = transferFactoryBillModel;
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
	 * 设置商品项数
	 * 
	 */
	private void setItemCount() {
		if (this.getTransferFactoryBill() != null
				&& this.dataState != DataState.ADD) {
			int itemCount = super.transferFactoryManageAction
					.findTransferFactoryCommodityInfoCount(new Request(
							CommonVars.getCurrUser()), transferFactoryBill
							.getId());
			this.tfItemCount.setText(String.valueOf(itemCount));
		} else {
			this.tfItemCount.setText("0");
		}
	}

	/**
	 * 
	 * 设置状态时判断是否可以生效
	 */
	public boolean isAvailability(boolean isAva) {
		boolean isAvailability = false;
		int itemCount = Integer.parseInt(this.tfItemCount.getText());
		if (itemCount > 0) {
			if (isAva) {
				isAvailability = !this.getTransferFactoryBill()
						.getIsAvailability().booleanValue();
			} else {
				isAvailability = this.getTransferFactoryBill()
						.getIsAvailability().booleanValue();
			}
		}
		return isAvailability;
	}

	/**
	 * 
	 * 设置状态时判断是否已经生效
	 */
	private boolean isAvailability() {
		boolean isAvailability = false;
		if (this.transferFactoryBill != null) {
			isAvailability = transferFactoryBill.getIsAvailability()
					.booleanValue();
		}
		return isAvailability;
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void initTable(List list) {
		commodityTableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = new Vector<Object>();
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("备案序号", "seqNum", 50));
						list.add(addColumn("商品编码", "complex.code", 90));
						list.add(addColumn("品名", "commName", 120));
						list.add(addColumn("规格型号", "commSpec", 120));
						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("关封数量", "quantity", 60));
						list.add(addColumn("转厂数量", "transFactAmount", 60));
						list.add(addColumn("单价", "unitPrice", 60));
						list.add(addColumn("总价", "totalPrice", 60));
						list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("毛重", "grossWeight", 60));
						list.add(addColumn("净重", "netWeight", 60));
						list.add(addColumn("产销国", "country.name", 60));
						list.add(addColumn("来源单据号", "sourceBill", 60));
						list.add(addColumn("体积", "cubage", 60));
						list.add(addColumn("版本号", "version", 60));
						list.add(addColumn("备注", "memo", 60));
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 
	 * 填充对象
	 */
	private void fillData(TransferFactoryBill transferFactoryBill) {
		transferFactoryBill.setAclUser(CommonVars.getCurrUser());
		transferFactoryBill.setBeginAvailability(this.cbbBeginAvailability
				.getDate());
		transferFactoryBill.setCompany(CommonVars.getCurrUser().getCompany());
		transferFactoryBill.setIsApplyToCustomsBill(new Boolean(false));
		transferFactoryBill.setIsAvailability(new Boolean(false));
		transferFactoryBill.setIsCustomsEnvelopRequestBill(Boolean
				.valueOf(false));
		transferFactoryBill.setIsImpExpGoods(Boolean.valueOf(isImportGoods()));
		transferFactoryBill.setItemCount(Integer.valueOf(this.tfItemCount
				.getText()));
		transferFactoryBill.setMemo(this.tfMemo.getText());
		transferFactoryBill
				.setScmCoc((ScmCoc) this.cbbScmCoc.getSelectedItem());
		transferFactoryBill.setWareSet((WareSet) this.cbbWareSet
				.getSelectedItem());
		transferFactoryBill.setTransferFactoryBillNo(String
				.valueOf(this.tfTransferFactoryBillNo.getText()));
		transferFactoryBill.setEnvelopNo(this.tfCustomsEnvelopBillCode
				.getText().trim());
		// transferFactoryBill.setEnvelopId(this.tfCustomsEnvelopBillId.getText()
		// .trim());
	}

	/**
	 * 
	 * 显示数据并设置对象
	 */
	private void showData(TransferFactoryBill transferFactoryBill) {
		if (transferFactoryBill.getTransferFactoryBillNo() != null) {
			this.tfTransferFactoryBillNo.setText(transferFactoryBill
					.getTransferFactoryBillNo().toString());
		} else {
			this.tfTransferFactoryBillNo.setText("");
		}
		if (transferFactoryBill.getBeginAvailability() != null) {
			this.cbbBeginAvailability.setDate(transferFactoryBill
					.getBeginAvailability());
		} else {
			this.cbbBeginAvailability.setDate(null);
		}
		if (transferFactoryBill.getCompany() != null) {
			this.tfCompanyName.setText(transferFactoryBill.getCompany()
					.getName());
		} else {
			this.tfCompanyName.setText("");
		}
		if (transferFactoryBill.getAclUser() != null) {
			this.tfAclUserName.setText(transferFactoryBill.getAclUser()
					.getUserName());
		} else {
			this.tfAclUserName.setText("");
		}
		if (transferFactoryBill.getMemo() != null) {
			this.tfMemo.setText(transferFactoryBill.getMemo());
		} else {
			this.tfMemo.setText("");
		}
		this.tfCustomsEnvelopBillCode.setText(transferFactoryBill
				.getEnvelopNo());// 关封号
		// this.tfCustomsEnvelopBillId.setText(transferFactoryBill.getEnvelopId());//
		// 关封Id
		cbbScmCoc.setSelectedItem(transferFactoryBill.getScmCoc());
		cbbWareSet.setSelectedItem(transferFactoryBill.getWareSet());
		List dataSource = this.transferFactoryManageAction
				.findTransferFactoryCommodityInfo(
						new Request(CommonVars.getCurrUser()),
						this.transferFactoryBill.getId());
		initTable(dataSource);
		this.tfItemCount.setText(String.valueOf(dataSource.size()));
	}

	/**
	 * 清空数据
	 * 
	 */
	private void emptyData() {
		this.tfTransferFactoryBillNo.setText("");
		this.tfCompanyName.setText("");
		this.tfMemo.setText("");
		this.tfItemCount.setText("0");
		this.cbbScmCoc.setSelectedItem(null);
		this.cbbWareSet.setSelectedItem(null);
		this.cbbBeginAvailability.setDate(null);
		this.tfCustomsEnvelopBillCode.setText("");
		initTable(new ArrayList());
	}

	/**
	 * 是否生成报关清单或关封申请单
	 */
	private boolean isMakeBill() {
		if (transferFactoryBill != null) {
			if ((transferFactoryBill.getIsApplyToCustomsBill() != null && transferFactoryBill
					.getIsApplyToCustomsBill().booleanValue() == true)
					|| (transferFactoryBill.getIsCustomsEnvelopRequestBill() != null && transferFactoryBill
							.getIsCustomsEnvelopRequestBill().booleanValue() == true)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 窗体状态控制
	 */
	private void setState() {
		this.setItemCount();

		this.btnAddTop.setEnabled(dataState == DataState.BROWSE
				&& !isMakeBill());
		this.btnEditTop.setEnabled(dataState == DataState.BROWSE
				&& !isAvailability() && !isMakeBill());
		this.btnSave.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill());
		this.btnCancel.setEnabled(dataState != DataState.BROWSE
				&& !isMakeBill() && this.transferFactoryBill != null);
		this.btnAvailability.setEnabled(isAvailability(true) && !isMakeBill());
		this.btnCancelAvailability.setEnabled(isAvailability(false)
				&& !isMakeBill());

		this.tfMaterielFinishedProductFlag.setEditable(false);
		if (transParameterSet.getIsNoEdit() && dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill()) {
			this.tfTransferFactoryBillNo.setEditable(true);
		} else {
			this.tfTransferFactoryBillNo.setEditable(false);
		}
		this.tfCompanyName.setEditable(false);
		this.tfItemCount.setEditable(false);
		this.tfAclUserName.setEditable(false);
		this.tfMemo.setEditable(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill());
		this.tfCustomsEnvelopBillCode.setEditable(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill());// &&
		// commodityTableModel.getList().size()
		// <= 0
		btnCustomsEnvelopBillCode.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill());// &&
		// commodityTableModel.getList().size()
		// <= 0
		this.cbbScmCoc.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill()
				&& commodityTableModel.getList().isEmpty());
		this.cbbWareSet.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill());
		this.cbbBeginAvailability.setEnabled(dataState != DataState.BROWSE
				&& !isAvailability() && !isMakeBill());

		this.btnAddBottom.setEnabled(dataState != DataState.ADD
				&& this.transferFactoryBill != null && !isAvailability()
				&& !isMakeBill());
		this.btnEditBottom.setEnabled(dataState != DataState.ADD
				&& this.transferFactoryBill != null && !isAvailability()
				&& !isMakeBill());
		this.btnDelete.setEnabled(dataState != DataState.ADD
				&& this.transferFactoryBill != null && !isAvailability()
				&& !isMakeBill());
		this.jTable.setEnabled(dataState != DataState.ADD
				&& this.transferFactoryBill != null && !isAvailability()
				&& !isMakeBill());

	}

	private boolean vaildatorDataIsNull() {
		if (this.tfTransferFactoryBillNo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "关封单号不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		// if (this.cbbBeginAvailability.getDate() == null) {
		// JOptionPane.showMessageDialog(null, "生效日期不可为空", "警告",
		// JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }
		return false;
	}

	/**
	 * 保存数据
	 */
	private boolean saveData() {
		if ("".equals(tfCustomsEnvelopBillCode.getText().trim())) {
			JOptionPane.showMessageDialog(DgTransferFactoryBill.this,
					"关封号未输入，保存无效", "提示", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		if (dataState == DataState.ADD) {
			TransferFactoryBill data = new TransferFactoryBill();
			fillData(data);
			if (!transParameterSet.getIsNoEdit()) {
				long maxBillNo = super.transferFactoryManageAction
						.getMaxTransferFactoryBillNoByImpExpGoodsFlag(
								new Request(CommonVars.getCurrUser()),
								this.isImportGoods());
				if (data.getTransferFactoryBillNo().equals(maxBillNo)) {
					data.setTransferFactoryBillNo(String.valueOf(maxBillNo));
				}
			} else {
				String no = this.tfTransferFactoryBillNo.getText();
				// 2011-7-11
				// 判断是否重复
				// if(no==null || no.length()!=12){
				// JOptionPane.showMessageDialog(DgTransferFactoryBill.this,
				// "此单据号长度应为12位！");
				// return;
				// }
				if (no == null) {
					return true;
				}
				if (no.matches("\\d+")) {
					if (transferFactoryManageAction.isExistsNo(new Request(
							CommonVars.getCurrUser()), no)) {
						JOptionPane.showMessageDialog(
								DgTransferFactoryBill.this, "此单据号已存在！");
						return true;
					}
				} else {
					JOptionPane.showMessageDialog(DgTransferFactoryBill.this,
							"此单据号格式不正确！");
					return true;
				}

			}
			data = this.transferFactoryManageAction.saveTransferFactoryBill(
					new Request(CommonVars.getCurrUser()), data);
			transferFactoryBillModel.addRow(data);
			this.setTransferFactoryBill(data);
		} else if (dataState == DataState.EDIT) {
			TransferFactoryBill data = null;
			if (transferFactoryBill != null) {
				data = transferFactoryBill;
			}
			String oldNo = data.getTransferFactoryBillNo();
			fillData(data);
			// 判断是否重复
			String no = this.tfTransferFactoryBillNo.getText();
			// 2011-7-11
			// if(no==null || no.length()!=12){
			// JOptionPane.showMessageDialog(DgTransferFactoryBill.this,
			// "此单据号长度应为12位！");
			// return;
			// }
			if (no.matches("\\d+")) {
				if (!oldNo.equals(no)
						&& transferFactoryManageAction.isExistsNo(new Request(
								CommonVars.getCurrUser()), no)) {
					JOptionPane.showMessageDialog(DgTransferFactoryBill.this,
							"此单据号已存在！");
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(DgTransferFactoryBill.this,
						"此单据号格式不正确！");
				return true;
			}
			data = this.transferFactoryManageAction.saveTransferFactoryBill(
					new Request(CommonVars.getCurrUser()), data);
			transferFactoryBillModel.updateRow(data);
			this.setTransferFactoryBill(data);
		}
		dataState = DataState.BROWSE;
		setState();
		return false;
	}

	/**
	 * 新增对象时初始化窗体控件
	 * 
	 */
	private void initComponents() {
		List list = new ArrayList();
		if (this.isImportGoods() == true) {
			list = super.getManufacturer();
			this.lbScmCoc.setText("供应商名称");
			this.tfMaterielFinishedProductFlag.setText("料件");
		} else {
			list = super.getCustomer();
			this.lbScmCoc.setText("客户名称");
			this.tfMaterielFinishedProductFlag.setText("成品");
		}
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));

		List wareSetList = super.materialManageAction.findWareSet(new Request(
				CommonVars.getCurrUser(), true));
		this.cbbWareSet
				.setModel(new DefaultComboBoxModel(wareSetList.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbWareSet,
				"code", "name", 250);
		this.cbbWareSet.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));

	}

	/**
	 * 
	 * 生效和失效记录并设置状态
	 */
	private void beginAvailability(boolean isAvailability) {
		try {
			this.getTransferFactoryBill().setIsAvailability(
					Boolean.valueOf(isAvailability));
			if (isAvailability) {
				if (this.getTransferFactoryBill().getBeginAvailability() == null) {
					this.getTransferFactoryBill().setBeginAvailability(
							new Date());
				}
			} else {
				// this.getTransferFactoryBill().setBeginAvailability(null);
			}
			transferFactoryBill = this.transferFactoryManageAction
					.saveTransferFactoryBill(
							new Request(CommonVars.getCurrUser()),
							getTransferFactoryBill());
			this.transferFactoryBillModel.updateRow(transferFactoryBill);
			showData(transferFactoryBill);
			setState();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "数据可能有误,生效失败!", "提示", 0);
		}
	}

	/**
	 * 新增显示初始化数据
	 */
	private void addInitData() {
		long billNo = super.transferFactoryManageAction
				.getMaxTransferFactoryBillNoByImpExpGoodsFlag(new Request(
						CommonVars.getCurrUser()), this.isImportGoods());
		this.tfTransferFactoryBillNo.setText(String.valueOf(billNo));
		if (CommonVars.getCurrUser() != null) {
			AclUser aclUser = (AclUser) CommonVars.getCurrUser();
			this.tfAclUserName.setText(aclUser.getUserName());
			if (aclUser.getCompany() != null) {
				this.tfCompanyName.setText(aclUser.getCompany().getName());
			}
		}
		this.cbbScmCoc.setSelectedItem(scmCoc);
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsEnvelopBillCode() {
		if (tfCustomsEnvelopBillCode == null) {
			tfCustomsEnvelopBillCode = new JTextField();
			tfCustomsEnvelopBillCode.setBounds(431, 129, 156, 24);
		}
		return tfCustomsEnvelopBillCode;
	}

	/**
	 * 查询 关封号
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCustomsEnvelopBillCode() {
		if (btnCustomsEnvelopBillCode == null) {
			btnCustomsEnvelopBillCode = new JButton();
			btnCustomsEnvelopBillCode.setBounds(587, 129, 21, 23);
			btnCustomsEnvelopBillCode.setText("...");
			btnCustomsEnvelopBillCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Object obj = TransferFactoryQuery.getInstance()
							// .getCustomsEnvelopBill(isImportGoods);
							ScmCoc scmCoc = (ScmCoc) cbbScmCoc
									.getSelectedItem();

							List lsTemp = TransferFactoryQuery.getInstance()
									.findTempTransferFactoryCommInfo(
											isImportGoods, scmCoc);
							if (lsTemp.size() <= 0) {
								return;
							}
							CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) lsTemp
									.get(0);

							if (tfCustomsEnvelopBillCode.getText() != null
									&& !"".equals(tfCustomsEnvelopBillCode
											.getText())
									&& !c.getCustomsEnvelopBill()
											.getCustomsEnvelopBillNo()
											.equals(tfCustomsEnvelopBillCode
													.getText().trim())) {
								JOptionPane.showMessageDialog(null,
										"你选择的关封号与现在的关封号不一致", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}

							tfCustomsEnvelopBillCode.setText(c
									.getCustomsEnvelopBill()
									.getCustomsEnvelopBillNo());
							if (commodityTableModel.getList().size() <= 0) {
								if (saveData()) {
									return;
								}

								dataState = DataState.EDIT;
							}
							list = transferFactoryManageAction
									.saveTransferFactoryCommodityInfo(
											new Request(CommonVars
													.getCurrUser()),
											transferFactoryBill, lsTemp);

							commodityTableModel.addRows(list);

							Double leftAmount = transferFactoryManageAction.findTransferFactoryCommodityInfoLeft(
									new Request(CommonVars.getCurrUser()),
									(TransferFactoryCommodityInfo) commodityTableModel
											.getCurrentRow());

							// 查询 转厂 一般信息
							DgTransferFactoryCommodityInfo dg = new DgTransferFactoryCommodityInfo();
							dg.setTableModel(commodityTableModel);
							dg.setDataState(DataState.EDIT);
							dg.setTransferFactoryBill(transferFactoryBill);
							dg.setLeftAmount(leftAmount);
							dg.setVisible(true);
							setState();

						}
					});
		}
		return btnCustomsEnvelopBillCode;
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
			jSplitPane.setDividerLocation(200);
			jSplitPane.setDividerSize(6);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
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
} // @jve:decl-index=0:visual-constraint="10,10"
