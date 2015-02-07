package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.AppClass;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgFptAppHead extends DgCommon {
	private JPanel pnMaster = null;

	private JToolBar jToolBar = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JToolBar jToolBar1 = null;

	private JButton btnEdit = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JButton btnAddBottom = null;

	private JButton btnEditBottom = null;

	private JButton btnDelete = null;

	private JTextField tfSeqNo = null;

	private JTextField tfAppNo = null;

	private JComboBox cbbCustoms = null;

	private JTextField tfConveySpa = null;

	private JTextField tfContrNo = null;

	private JComboBox cbbScmCoc = null;

	private JComboBox cbbInDistrict = null;

	private JCalendarComboBox cbbOutDate = null;

	private List list = null; // @jve:decl-index=0:

	private JTableListModel commodityTableModel = null;

	private JTableListModel fptAppHeadModel = null;

	private FptAppHead fptAppHead = null; // @jve:decl-index=0:

	private boolean isImportGoods = true;

	private int dataState = -1;

	private int itemCount = 0;

	private JPanel jContentPane = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel51 = null;

	private JLabel lbEmsNo = null;

	private JComboBox cbbProjectType = null;

	private JLabel jLabel52 = null;

	private JTextField tfOutAgentCode = null;

	private JTextField tfOutTradeCode2 = null;

	private JPanel pnOut = null;

	private JPanel pnIn = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel53 = null;

	private JLabel jLabel54 = null;

	private JLabel jLabel56 = null;

	private JComboBox cbbOutDistrict = null;

	private JLabel jLabel561 = null;

	private JLabel jLabel562 = null;

	private JLabel jLabel563 = null;

	private JLabel jLabel564 = null;

	private JLabel jLabel565 = null;

	private JLabel jLabel566 = null;

	private JLabel jLabel567 = null;

	private JLabel jLabel568 = null;

	private JLabel jLabel569 = null;

	private JTextField tfOutTradeName = null;

	private JTextField tfOutTradeName2 = null;

	private JTextField tfOutAgentName = null;

	private JTextField tfConveyDay = null;

	private JTextField tfOutTradeCode = null;

	private JTextField tfOutNote = null;

	private JTextField tfOutCorp = null;

	private JTextField tfOutDecl = null;

	private JLabel jLabel55 = null;

	private JLabel jLabel57 = null;

	private JLabel jLabel571 = null;

	private JLabel jLabel572 = null;

	private JLabel jLabel573 = null;

	private JLabel jLabel58 = null;

	private JLabel jLabel59 = null;

	private JLabel jLabel510 = null;

	private JLabel jLabel511 = null;

	private JLabel jLabel574 = null;

	private JLabel jLabel512 = null;

	private JLabel jLabel513 = null;

	private JTextField tfInCopAppNo = null;

	private JTextField tfInTradeCode2 = null;

	private JTextField tfInCorp = null;

	private JTextField tfInAgentCode = null;

	private JTextField tfInLiceNo = null;

	private JTextField tfInNote = null;

	private JTextField tfInAgentName = null;

	private JTextField tfInDecl = null;

	private JTextField tfInTradeName2 = null;

	private JCalendarComboBox cbbInDate = null;

	private JComboBox cbbInCustoms = null;

	private JButton btnClose = null;

	private JTabbedPane jTabbedPane = null;

	private JLabel jLabel2 = null;

	private JTextField tfOutCopAppNo = null;

	private JButton btnImport = null;

	private JButton btnChange = null;

	private String fptInOutFlag = FptInOutFlag.OUT; // @jve:decl-index=0:

	private JButton btnCopy = null;

	private JButton btnCopy1 = null;

	private JButton btnAmountToInteger = null;

	private JTextField tfOutLiceNo = null;

	private JLabel jLabel514 = null;

	private JLabel jLabel515 = null;

	private JButton btnAddBottom1 = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JLabel jLabel43 = null;
	private boolean isFirst = true;
	protected ManualDeclareAction manualDeclareAction = null;
	private ContractAction contractAction = null;
	private DzscAction dzscAction = null;

	private FptParameterSet parameterSet = null;

	private Integer projectType = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgFptAppHead() {
		super();
		initialize();
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		parameterSet = fptManageAction.findTransParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		projectType = parameterSet.getProjectType();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("申请表编辑");
		this.setContentPane(getJContentPane());
		this.setSize(738, 650);
	}

	public void setVisible(boolean b) {
		if (b) {
			fptAppHead = (FptAppHead) fptAppHeadModel.getCurrentRow();
			initUIComponents();
			if (dataState == DataState.ADD) {
				emptyData();
			} else if (dataState == DataState.EDIT
					|| dataState == DataState.BROWSE) {

				showItems();
				showData(fptAppHead);
			}
			setState();
		}
		super.setVisible(b);
	}

	private javax.swing.JLabel lbSeqNo = null;

	private JButton btnResetListNo = null;

	private JButton btnChangingListNo = null;

	private JComboBox cbbAppClass = null;

	private JComboBox cbbEmsHead = null;

	private JButton btnOutTradeCode2 = null;

	private JButton btnInTradeCode2 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel5161 = null;

	private JCalendarComboBox cbbOppositeEmsDate = null;

	private JTextField tfInTradeCode = null;

	private JTextField tfInTradeName = null;

	private JLabel jLabel531 = null;

	private JLabel jLabel521 = null;
	private JTextField txtSaleContractNo;

	private JLabel jLabel5121 = null;

	private JLabel jLabel5101 = null;

	private JCalendarComboBox cbbInDate1 = null;

	private JComboBox cbbEmsHeadIn = null;
	private JCalendarComboBox cbbEndDate;
	private JLabel label;

	private JPanel getPnMaster() {
		if (pnMaster == null) {
			jLabel5161 = new JLabel();
			jLabel5161.setBounds(new Rectangle(277, 27, 103, 21));
			jLabel5161.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel5161.setText("对方手册到期日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(43, 27, 60, 22));
			jLabel1.setForeground(Color.blue);
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel1.setText("客户");
			jLabel52 = new JLabel();
			jLabel52.setText("转出企业代码");
			// lbEmsNo = new JLabel();
			jLabel52.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel52.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel52.setForeground(Color.blue);
			jLabel52.setBounds(new Rectangle(29, 41, 142, 22));
			// lbEmsNo.setBounds(new Rectangle(40, 170, 74, 24));
			// lbEmsNo.setText("手册编号");
			jLabel51 = new JLabel();
			jLabel51.setText("手册类型");
			jLabel51.setForeground(Color.blue);
			jLabel51.setBounds(new Rectangle(537, 4, 48, 22));
			lbSeqNo = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			pnMaster = new JPanel();
			pnMaster.setLayout(null);
			jLabel.setText("申请表编号");
			jLabel.setBounds(44, 4, 60, 22);
			lbSeqNo.setText("电子口岸统一编号");
			lbSeqNo.setBounds(277, 4, 98, 22);
			pnMaster.add(jLabel, null);
			pnMaster.add(lbSeqNo, null);
			pnMaster.add(getTfSeqNo(), null);
			pnMaster.add(getTfAppNo(), null);
			pnMaster.add(getPnOut(), null);
			pnMaster.add(getPnIn(), null);
			pnMaster.add(jLabel51, null);
			pnMaster.add(getCbbProjectType(), null);
			pnMaster.add(getCbbScmCoc(), null);
			pnMaster.add(jLabel1, null);
			pnMaster.add(jLabel5161, null);
			pnMaster.add(getCbbOppositeEmsDate(), null);
			pnMaster.add(getCbbEndDate());
			pnMaster.add(getLabel());
		}
		return pnMaster;
	}

	private JLabel getLbEmsNo() {
		if (lbEmsNo == null) {
			lbEmsNo = new JLabel();
			lbEmsNo.setText("申请表类型");
			lbEmsNo.setHorizontalTextPosition(SwingConstants.RIGHT);
			lbEmsNo.setHorizontalAlignment(SwingConstants.RIGHT);
			lbEmsNo.setForeground(Color.blue);
			lbEmsNo.setBounds(new Rectangle(29, 16, 142, 22));
		}
		return lbEmsNo;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return j pnMaster.add(jLabel5, null); avax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnClose());
			jToolBar.add(getJPanel3());
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
						browseData();
					}
				}
			});
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							// if (e.getValueIsAdjusting()) {
							// return;
							// }
							JTableListModel tableModel = (JTableListModel) jTable
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								if (tableModel.getCurrentRow() != null) {
									setState();
								}
							} catch (Exception cx) {

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
			jLabel43 = new JLabel();
			jLabel43.setForeground(new Color(227, 145, 0));
			jLabel43.setText("                              转出信息");
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAddBottom());
			jToolBar1.add(getBtnAddBottom1());
			jToolBar1.add(getBtnEditBottom());
			jToolBar1.add(getBtnDelete());
			jToolBar1.add(getBtnChange());
			jToolBar1.add(getBtnChangingListNo());
			jToolBar1.add(getBtnResetListNo());
			jToolBar1.add(getBtnAmountToInteger());
			jToolBar1.add(getBtnCopy());
			jToolBar1.add(getBtnCopy1());
			jToolBar1.add(jLabel43);
		}
		return jToolBar1;
	}

	/**
	 * 
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
					if (fptAppHead != null) {
						showData(fptAppHead);
					}
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
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
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (vaildatorDataIsNull()) {
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
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (fptAppHead != null) {
						showData(fptAppHead);
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
			btnAddBottom.setText("");
			btnAddBottom.setVisible(false);
			btnAddBottom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (fptAppHead == null) {

						return;

					}

					FptAppItem fptAppItem = fptManageAction.newFptAppItem(
							new Request(CommonVars.getCurrUser()), fptAppHead);
					commodityTableModel.addRow(fptAppItem);

					// DgFptAppItem dg = new DgFptAppItem();
					// dg.setTableModel(commodityTableModel);
					// dg.setFptInOutFlag(getFptInOutFlag());
					// dg.setDataState(DataState.ADD);
					// dg.setFptAppHead(getFptAppHead());
					// dg.setVisible(true);
					// setState();
					// addData();
				}
			});
		}
		return btnAddBottom;
	}

	// /**
	// * 新增关封商品信息
	// */
	// private void addData() {
	// List customsEnvelopCommodityInfos = getCustomsEnvelopCommodityInfos();
	// if (customsEnvelopCommodityInfos == null) {
	// return;
	// }
	// customsEnvelopCommodityInfos = transferFactoryManageAction
	// .saveCustomsEnvelopCommodityInfo(new Request(CommonVars
	// .getCurrUser()), customsEnvelopCommodityInfos);
	// for (int i = 0; i < customsEnvelopCommodityInfos.size(); i++) {
	// commodityTableModel.addRow(customsEnvelopCommodityInfos.get(i));
	// }
	// setState();
	// }

	/** 新增来自三种报关(bcs,bcus,dzsc)模式 */
	private void addData2() {

		if (fptAppHead == null) {

			JOptionPane.showMessageDialog(DgFptAppHead.this, "请先保存申请表表头记录!",
					"提示", JOptionPane.INFORMATION_MESSAGE);

			return;

		} else {

			if (fptAppHead.getProjectType() == null) {

				JOptionPane.showMessageDialog(DgFptAppHead.this,
						"项目类型不可为空,\n请先保存申请表表头记录!", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			}
			//
			// 如果是转出,请先填写 emsNo
			//
			if (fptAppHead.getInOutFlag().equals(FptInOutFlag.OUT)
					&& (fptAppHead.getEmsNo() == null || "".equals(fptAppHead
							.getEmsNo().trim()))) {

				JOptionPane.showMessageDialog(DgFptAppHead.this,
						"转出手册/帐册编号不可为空,\n请先保存申请表表头记录!", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return;

			}

			//
			// 手册/帐册内销
			//
			int projectType = fptAppHead.getProjectType();

			// 出口的 手册号
			String emsNoOut = this.fptAppHead.getEmsNo();

			// 进口的 手册号
			String emsNoIn = this.fptAppHead.getInEmsNo();

			// HH 2013.10.22 不根据表头ID过滤料件和 物品

			// String parentId = this.fptAppHead.getId();
			String parentId = null;

			//
			// 如果是转出
			//
			if (fptAppHead.getInOutFlag().equals(FptInOutFlag.OUT)) {

				switch (projectType) {

				case ProjectType.BCS:

					List<ContractExg> contractExgs = FptQuery.getInstance()
							.findContractExgByProcessExe(parentId, emsNoOut);

					if (contractExgs != null) {

						// this.transferFactoryManageAction.

						List<FptAppItem> fptAppItems = fptManageAction
								.newFptAppItemByContractExg(new Request(
										CommonVars.getCurrUser()), fptAppHead,
										contractExgs);

						commodityTableModel.addRows(fptAppItems);

						editData(fptAppItems.get(0));
					}

					break;

				case ProjectType.BCUS:

					List<EmsHeadH2kExg> emsHeadH2kExgs = FptQuery.getInstance()
							.findEmsHeadH2kExgByProcessExe(parentId, emsNoOut);
					if (emsHeadH2kExgs != null) {
						// this.transferFactoryManageAction.
						List<FptAppItem> fptAppItems = fptManageAction
								.newFptAppItemByEmsHeadH2kExg(new Request(
										CommonVars.getCurrUser()), fptAppHead,
										emsHeadH2kExgs);
						commodityTableModel.addRows(fptAppItems);
						editData(fptAppItems.get(0));
					}
					break;

				case ProjectType.DZSC:
					List<DzscEmsExgBill> dzscEmsExgBills = FptQuery
							.getInstance().findDzscEmsExgBillByProcessExe(
									parentId, emsNoOut);
					if (dzscEmsExgBills != null) {
						// this.transferFactoryManageAction.
						List<FptAppItem> fptAppItems = fptManageAction
								.newFptAppItemByDzscEmsExgBill(new Request(
										CommonVars.getCurrUser()), fptAppHead,
										dzscEmsExgBills);
						commodityTableModel.addRows(fptAppItems);
						editData(fptAppItems.get(0));
					}
					break;
				}

			} else { // 转入

				switch (projectType) {

				case ProjectType.BCS:

					List<ContractImg> contractImgs = FptQuery.getInstance()
							.findContractImgByProcessExe(parentId, emsNoIn);

					if (contractImgs != null) {

						List<FptAppItem> fptAppItems = fptManageAction
								.newFptAppItemByContractImg(new Request(
										CommonVars.getCurrUser()), fptAppHead,
										contractImgs);

						commodityTableModel.addRows(fptAppItems);

						editData(fptAppItems.get(0));

					}
					break;

				case ProjectType.BCUS:

					List<EmsHeadH2kImg> emsHeadH2kImgs = FptQuery.getInstance()
							.findEmsHeadH2kImgByProcessExe(parentId, emsNoIn);

					if (emsHeadH2kImgs != null) {

						List<FptAppItem> fptAppItems = fptManageAction
								.newFptAppItemByEmsHeadH2kImg(new Request(
										CommonVars.getCurrUser()), fptAppHead,
										emsHeadH2kImgs);

						commodityTableModel.addRows(fptAppItems);

						editData(fptAppItems.get(0));
					}

					break;

				case ProjectType.DZSC:

					List<DzscEmsImgBill> dzscEmsImgBills = FptQuery
							.getInstance().findDzscEmsImgBillByProcessExe(
									parentId, emsNoIn);

					if (dzscEmsImgBills != null) {

						List<FptAppItem> fptAppItems = fptManageAction
								.newFptAppItemByDzscEmsImgBill(new Request(
										CommonVars.getCurrUser()), fptAppHead,
										dzscEmsImgBills);

						commodityTableModel.addRows(fptAppItems);

						editData(fptAppItems.get(0));

					}
					break;
				}

			}
		}
	}

	// /**
	// * 获得关封商品信息
	// */
	// private List getCustomsEnvelopCommodityInfos() {
	// List list = manualDeclearAction.findEmsHeadH2kInExecuting(new Request(
	// CommonVars.getCurrUser(), true));
	// if (list == null || list.size() <= 0) {
	// JOptionPane.showMessageDialog(null, "没有正在执行的电子帐册，不能新增!!", "信息",
	// JOptionPane.INFORMATION_MESSAGE);
	// return null;
	// }
	// EmsHeadH2k emsHeadH2k = (EmsHeadH2k) (list.get(0));
	// List customsEnvelopCommodityInfos = new ArrayList();
	// if (true) {
	// List emsMateriels = (List) CommonQuery.getInstance()
	// .getEmsMateriel(emsHeadH2k, fptAppHead.getId());
	// if (emsMateriels == null || emsMateriels.size() <= 0) {
	// return null;
	// }
	// for (int i = 0; i < emsMateriels.size(); i++) {
	// CustomsEnvelopCommodityInfo data = new CustomsEnvelopCommodityInfo();
	// EmsHeadH2kImg temp = (EmsHeadH2kImg) emsMateriels.get(i);
	//
	// data.setSeqNum(temp.getSeqNum());
	// data.setPtName(temp.getName());
	// data.setPtSpec(temp.getSpec());
	// data.setUnit(temp.getUnit());
	// data.setComplex(temp.getComplex());
	// data.setCurr(temp.getCurr());
	//
	// // data.setCustomsEnvelopBill(fptAppHead);
	// customsEnvelopCommodityInfos.add(data);
	// }
	// } else {
	// List emsFinishedProduct = (List) CommonQuery.getInstance()
	// .getEmsFinishedProduct(emsHeadH2k, fptAppHead.getId());
	// if (emsFinishedProduct == null || emsFinishedProduct.size() <= 0) {
	// return null;
	// }
	// for (int i = 0; i < emsFinishedProduct.size(); i++) {
	// CustomsEnvelopCommodityInfo data = new CustomsEnvelopCommodityInfo();
	// EmsHeadH2kExg temp = (EmsHeadH2kExg) emsFinishedProduct.get(i);
	//
	// data.setSeqNum(temp.getSeqNum());
	// data.setPtName(temp.getName());
	// data.setPtSpec(temp.getSpec());
	// data.setUnit(temp.getUnit());
	// data.setComplex(temp.getComplex());
	// data.setCurr(temp.getCurr());
	//
	// // data.setCustomsEnvelopBill(fptAppHead);
	// customsEnvelopCommodityInfos.add(data);
	// }
	// }
	// return customsEnvelopCommodityInfos;
	// }

	/**
	 * This method initializes btnEditBottom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditBottom() {
		if (btnEditBottom == null) {
			btnEditBottom = new JButton();
			btnEditBottom.setText("修改");
			btnEditBottom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (commodityTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgFptAppHead.this, "请选择你要修改的资料", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							editData((FptAppItem) commodityTableModel
									.getCurrentRow());
						}
					});
		}
		return btnEditBottom;
	}

	/**
	 * 编缉数据
	 */
	private void editData(FptAppItem f) {

		DgFptAppItem dg = new DgFptAppItem();
		dg.setTableModel(commodityTableModel);
		dg.setFptInOutFlag(f.getInOutFlag());
		dg.setDataState(DataState.EDIT);
		dg.setFptAppHead(this.getFptAppHead());
		dg.setVisible(true);
	}

	/**
	 * 编缉数据
	 */
	private void browseData() {
		DgFptAppItem dg = new DgFptAppItem();
		FptAppItem f = (FptAppItem) commodityTableModel.getCurrentRow();
		dg.setTableModel(commodityTableModel);
		dg.setFptInOutFlag(f.getInOutFlag());
		dg.setDataState(DataState.BROWSE);
		dg.setFptAppHead(this.getFptAppHead());
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
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commodityTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(null, "请选择你要删除的数据", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(null, "是否确定删除数据!", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = commodityTableModel.getCurrentRows();
					fptManageAction.deleteFptAppItem(
							new Request(CommonVars.getCurrUser()), list);

					for (int i = 0; i < list.size(); i++) {
						commodityTableModel.deleteRow(list.get(i));
					}

					// HH 2013.11.12 删除料件物品时重排序号
					FptAppItem data = null;
					@SuppressWarnings("unchecked")
					List<FptAppItem> listAllAppItems = (List<FptAppItem>) commodityTableModel
							.getList();
					for (int i = 0; i < listAllAppItems.size(); i++) {
						data = listAllAppItems.get(i);
						data.setListNo(i + 1);
					}
					fptManageAction.saveFptAppItem(
							new Request(CommonVars.getCurrUser()),
							listAllAppItems);
					setState();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes tfSeqNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNo() {
		if (tfSeqNo == null) {
			tfSeqNo = new JTextField();
			tfSeqNo.setBounds(374, 4, 137, 22);
		}
		return tfSeqNo;
	}

	/**
	 * This method initializes tfAppNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAppNo() {
		if (tfAppNo == null) {
			tfAppNo = new JTextField();
			tfAppNo.setBounds(104, 4, 167, 22);
			tfAppNo.setEditable(false);
		}
		return tfAppNo;
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
	public JTableListModel getFptAppHeadModel() {
		return fptAppHeadModel;
	}

	/**
	 * This method initializes cbbCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(new Rectangle(454, 85, 126, 22));
		}
		return cbbCustoms;
	}

	/**
	 * This method initializes tfContrNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfContrNo() {
		if (tfContrNo == null) {
			tfContrNo = new JTextField();
			tfContrNo.setBounds(new Rectangle(455, 16, 126, 22));
		}
		return tfContrNo;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(104, 27, 167, 21));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes cbbCompany
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbInDistrict() {
		if (cbbInDistrict == null) {
			cbbInDistrict = new JComboBox();
			cbbInDistrict.setBounds(new Rectangle(454, 110, 126, 22));
		}
		return cbbInDistrict;
	}

	/**
	 * This method initializes cbbOutDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbOutDate() {
		if (cbbOutDate == null) {
			cbbOutDate = new JCalendarComboBox();
			// cbbBeginAvailability.addChangeListener(new ChangeListener() {
			cbbOutDate.setBounds(new Rectangle(174, 112, 126, 22));
			//
			cbbOutDate.setEnabled(false);
			// public void stateChanged(ChangeEvent arg0) {
			//
			// }
			//
			// });
			cbbOutDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					if (cbbOutDate.getDate() == null) {
						return;
					}
					Calendar beginDate = cbbOutDate.getCalendar();
					Calendar endDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.add(Calendar.DAY_OF_YEAR, 90);
					}
					// cbbEndAvailability.setCalendar(endDate);
				}

			});
		}
		return cbbOutDate;
	}

	/**
	 * This method initializes tfConveySpa
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConveySpa() {
		if (tfConveySpa == null) {
			tfConveySpa = new JTextField();
			tfConveySpa.setBounds(new Rectangle(454, 205, 126, 22));
		}
		return tfConveySpa;
	}

	/**
	 * @param impExpRequestModel
	 *            The impExpRequestModel to set.
	 */
	@SuppressWarnings("unused")
	public void setFptAppHeadModel(JTableListModel impExpRequestModel) {
		String ds = ((FptAppHead) impExpRequestModel.getCurrentRow())
				.getDeclareState();
		if (DeclareState.IS_CAN.equals(((FptAppHead) impExpRequestModel
				.getCurrentRow()).getDeclareState())) {
			jToolBar.setEnabled(false);
		}
		this.fptAppHeadModel = impExpRequestModel;
	}

	/**
	 * @return Returns the FptAppHead.
	 */
	public FptAppHead getFptAppHead() {
		return fptAppHead;
	}

	/**
	 * @param FptAppHead
	 *            The FptAppHead to set.
	 */
	public void setFptAppHead(FptAppHead FptAppHead) {
		this.fptAppHead = FptAppHead;
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

	// /**
	// * 设置商品信息个数
	// *
	// */
	// private void setItemCount() {
	// if (this.fptAppHead == null || this.dataState == DataState.ADD) {
	// itemCount = 0;
	// return;
	// }
	// itemCount = this.transferFactoryManageAction
	// .findCustomsEnvelopCommodityInfoCount(new Request(CommonVars
	// .getCurrUser()), this.fptAppHead.getId());
	// }

	/**
	 * 
	 * 设置状态时判断是否可以生效
	 */
	public boolean isAvailability(boolean isAva) {
		boolean isAvailability = false;
		// if (this.itemCount > 0) {
		// if (isAva) {
		// isAvailability = !this.getFptAppHead()
		// .getIsAvailability().booleanValue();
		// } else {
		// isAvailability = this.getFptAppHead()
		// .getIsAvailability().booleanValue();
		// }
		// }
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
						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("转入转出标记", "inOutFlag", 100));
						list.add(addColumn("修改标记", "modifyMarkState", 75));
						list.add(addColumn("序号", "listNo", 50));
						if (FptInOutFlag.IN.equals(getFptInOutFlag())) {
							list.add(addColumn("转出序号", "inOutNo", 80));
							list.add(addColumn("转入手册号", "inEmsNo", 100));
						} else {
							// list.add(addColumn("转出序号", "inOutNo", 0));
							// list.add(addColumn("转入手册号", "inEmsNo", 0));
						}
						list.add(addColumn("商品项号", "trNo", 80));
						list.add(addColumn("商品编码", "codeTs.code", 120));
						list.add(addColumn("商品名称", "name", 80));
						list.add(addColumn("规格型号", "spec", 80));
						list.add(addColumn("计量单位", "unit.name", 80));

						list.add(addColumn("法定单位", "unit1.name", 80));
						list.add(addColumn("申报数量", "qty", 80));
						list.add(addColumn("法定数量", "qty1", 80));
						// list.add(addColumn("单价", "unitPrice", 60));
						// list.add(addColumn("总价", "totalPrice", 60));
						//
						// list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("备用商品编码", "standComplex.code", 100));
						list.add(addColumn("备注", "note", 60));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return FptInOutFlag.getNote(value.toString());
					}
				});
		jTable.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});

		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 
	 * 初始化表格对象
	 */
	private void initTable2(List list) {
		new JTableListModel(jTable1, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();

				list.add(addColumn("转入转出标记", "inOutFlag", 75));
				list.add(addColumn("修改标记", "modifyMarkState", 100));
				list.add(addColumn("序号", "listNo", 90));
				if (FptInOutFlag.IN.equals(getFptInOutFlag())) {
					// list.add(addColumn("转出序号", "inOutNo", 0));
					// list.add(addColumn("转入手册号", "inEmsNo", 0));
				} else {
					list.add(addColumn("转出序号", "inOutNo", 80));
					list.add(addColumn("转入手册号", "inEmsNo", 100));
				}

				list.add(addColumn("商品项号", "trNo", 120));
				list.add(addColumn("商品编码", "codeTs.code", 120));
				list.add(addColumn("商品名称", "name", 60));
				list.add(addColumn("规格型号", "spec", 60));
				list.add(addColumn("计量单位", "unit.name", 60));

				list.add(addColumn("法定单位", "unit1.name", 80));
				list.add(addColumn("申报数量", "qty", 60));
				list.add(addColumn("法定数量", "qty1", 80));
				// list.add(addColumn("单价", "unitPrice", 60));
				// list.add(addColumn("总价", "totalPrice", 60));
				//
				// list.add(addColumn("币制", "curr.name", 60));
				list.add(addColumn("备用商品编码", "standComplex.code", 60));
				list.add(addColumn("备注", "note", 60));
				return list;
			}
		});
		jTable1.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return FptInOutFlag.getNote(value.toString());
					}
				});
		jTable1.getColumnModel().getColumn(2)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});

		jTable1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 
	 * 填充对象
	 */
	private void fillData(FptAppHead f) {
		f.setAppNo(this.tfAppNo.getText());
		f.setSeqNo(this.tfSeqNo.getText());

		if (cbbProjectType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
			int projectType = Integer.parseInt(item.getCode());
			f.setProjectType(projectType);
		} else {
			f.setProjectType(null);
		}

		//
		// 转出填写
		//
		if (cbbAppClass.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbAppClass.getSelectedItem();
			f.setAppClass(item.getCode());
		} else {
			f.setAppClass(null);
		}

		f.setContrNo(this.tfContrNo.getText());
		f.setOutTradeCode(this.tfOutTradeCode.getText());
		f.setOutTradeName(this.tfOutTradeName.getText());
		f.setOutDistrict((District) this.cbbOutDistrict.getSelectedItem());
		f.setOutCustoms((Customs) this.cbbCustoms.getSelectedItem());
		f.setSaleContractNo(this.txtSaleContractNo.getText());
		if (this.cbbEmsHead.getSelectedItem() != null) {
			String emsNo = ((BaseEmsHead) this.cbbEmsHead.getSelectedItem())
					.getEmsNo();
			f.setEmsNo(emsNo);
		} else {
			f.setEmsNo(null);
		}

		ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
		f.setScmCoc(scmCoc);

		if (this.getFptInOutFlag().equals(FptInOutFlag.OUT)) {
			if (scmCoc != null) {
				Brief b = scmCoc.getBrief();
				if (b != null) {
					f.setInTradeCode(b.getCode());
					f.setInTradeName(b.getName());
				} else {
					f.setInTradeCode("");
					f.setInTradeName("");
				}
			} else {
				f.setInTradeCode("");
				f.setInTradeName("");
			}
		}

		f.setInDistrict((District) this.cbbInDistrict.getSelectedItem());
		f.setOutTradeCode2(this.tfOutTradeCode2.getText());
		f.setOutTradeName2(this.tfOutTradeName2.getText());
		f.setOutAgentCode(this.tfOutAgentCode.getText());
		f.setOutAgentName(this.tfOutAgentName.getText());
		f.setOutCorp(this.tfOutCorp.getText());
		f.setOutDecl(this.tfOutDecl.getText());

		// Integer converyDay = this.tfConveyDay.getText() == null
		// || "".equals(this.tfConveyDay.getText()) ? 0 : Integer
		// .valueOf(tfConveyDay.getText().toString());
		if (this.tfConveyDay.getText() != null
				&& !"".equals(this.tfConveyDay.getText().trim())) {
			f.setConveyDay(Integer.valueOf(tfConveyDay.getText().toString()));
		}
		// Integer converySpa = this.tfConveySpa.getText() == null
		// || "".equals(this.tfConveySpa.getText()) ? 0 : Integer
		// .valueOf(tfConveySpa.getText().toString());
		// f.setConveySpa(converySpa);
		if (this.tfConveySpa.getText() != null
				&& !"".equals(this.tfConveySpa.getText().trim())) {
			f.setConveySpa(Integer.valueOf(tfConveySpa.getText().toString()));
		}
		f.setOutDate(this.cbbOutDate.getDate());
		f.setOppositeEmsDate(this.cbbOppositeEmsDate.getDate());
		f.setOutCopAppNo(this.tfOutCopAppNo.getText());
		f.setOutNote(this.tfOutNote.getText());
		f.setOutLiceNo(this.tfOutLiceNo.getText());
		//
		// 转入填写
		//
		f.setInDate(this.cbbInDate.getDate());
		f.setInCopAppNo(this.tfInCopAppNo.getText());
		f.setInNote(this.tfInNote.getText());
		f.setInCustoms((Customs) this.cbbInCustoms.getSelectedItem());
		f.setInLiceNo(this.tfInLiceNo.getText());
		f.setInTradeCode2(this.tfInTradeCode2.getText());
		f.setInTradeName2(this.tfInTradeName2.getText());
		f.setInAgentCode(this.tfInAgentCode.getText());
		f.setInAgentName(this.tfInAgentName.getText());
		f.setInCorp(this.tfInCorp.getText());
		f.setInDecl(this.tfInDecl.getText());
		// f.setInEmsNo(this.cbbEmsHeadIn.getText());
		f.setEndDate(this.cbbInDate1.getDate());
		if (this.cbbEmsHeadIn.getSelectedItem() != null) {
			String emsNo = ((BaseEmsHead) this.cbbEmsHeadIn.getSelectedItem())
					.getEmsNo();
			f.setInEmsNo(emsNo);
		} else {
			f.setInEmsNo(null);
		}
		f.setEndDate(this.cbbEndDate.getDate());
	}

	/**
	 * 
	 * 显示数据并设置对象
	 */
	private void showData(FptAppHead f) {
		//
		// 刷新
		//
		if (f.getId() != null) {
			fptAppHead = this.fptManageAction.findFptAppHeadById(new Request(
					CommonVars.getCurrUser()), f.getId());
			f = fptAppHead;
			fptAppHeadModel.updateRow(fptAppHead);
		}
		this.tfAppNo.setText(f.getAppNo());
		this.tfSeqNo.setText(f.getSeqNo());
		if (f.getProjectType() != null) {
			this.cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(f
					.getProjectType().toString(), this.cbbProjectType));
		} else {
			this.cbbProjectType.setSelectedIndex(-1);
		}
		if (f.getAppClass() != null) {
			this.cbbAppClass.setSelectedIndex(ItemProperty.getIndexByCode(
					f.getAppClass(), this.cbbAppClass));
		} else {
			this.cbbAppClass.setSelectedIndex(-1);
		}

		//
		// 转出填写
		//
		// this.tfAppClass.setText(f.getAppClass());
		this.tfContrNo.setText(f.getContrNo());
		this.txtSaleContractNo.setText(f.getSaleContractNo());
		this.tfOutTradeCode.setText(f.getOutTradeCode());
		this.tfOutTradeName.setText(f.getOutTradeName());
		this.tfInTradeCode.setText(f.getInTradeCode());
		this.tfInTradeName.setText(f.getInTradeName());
		this.cbbOutDistrict.setSelectedItem(f.getOutDistrict());
		this.cbbCustoms.setSelectedItem(f.getOutCustoms());

		int anIndex = -1;
		if (f.getEmsNo() != null && !"".equals(f.getEmsNo())) {
			ComboBoxModel m = this.cbbEmsHead.getModel();
			for (int i = 0; i < m.getSize(); i++) {
				BaseEmsHead b = ((BaseEmsHead) m.getElementAt(i));
				if (b != null && b.getEmsNo().equals(f.getEmsNo())) {
					anIndex = i;
					break;
				}
			}
			// 在下拉框中 没有找到的时候 往下拉中添加 一个对象。
			if (anIndex == -1) {
				BaseEmsHead baseEmsHead = new BaseEmsHead();
				baseEmsHead.setEmsNo(f.getEmsNo());
				this.cbbEmsHead.removeAllItems();
				this.cbbEmsHead.addItem(baseEmsHead);
				anIndex++;
			}
		}
		cbbEmsHead.setSelectedIndex(anIndex);

		//
		// 转入企业代码
		//
		if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
			anIndex = -1;
			if (f.getOutTradeCode() != null && !"".equals(f.getOutTradeCode())) {
				ComboBoxModel m = this.cbbScmCoc.getModel();
				for (int i = 0; i < m.getSize(); i++) {
					Brief b = ((ScmCoc) m.getElementAt(i)).getBrief();
					if (b != null && b.getCode().equals(f.getOutTradeCode())) {
						anIndex = i;
						break;
					}
				}
			}

			if (anIndex != -1) {
				this.cbbScmCoc.setSelectedIndex(anIndex);
			} else {
				this.cbbScmCoc.setSelectedItem(f.getScmCoc());
			}
		}

		//
		// 转出企业代码
		//
		if (this.getFptInOutFlag().equals(FptInOutFlag.OUT)) {
			anIndex = -1;
			if (f.getInTradeCode() != null && !"".equals(f.getInTradeCode())) {
				ComboBoxModel m = this.cbbScmCoc.getModel();
				for (int i = 0; i < m.getSize(); i++) {
					Brief b = ((ScmCoc) m.getElementAt(i)).getBrief();
					if (b != null && b.getCode().equals(f.getInTradeCode())) {
						anIndex = i;
						break;
					}
				}
			}

			if (anIndex != -1) {
				this.cbbScmCoc.setSelectedIndex(anIndex);
			} else {
				this.cbbScmCoc.setSelectedItem(f.getScmCoc());
			}
		}

		this.cbbInDistrict.setSelectedItem(f.getInDistrict());
		this.tfOutTradeCode2.setText(f.getOutTradeCode2());
		this.tfOutTradeName2.setText(f.getOutTradeName2());
		this.tfOutAgentCode.setText(f.getOutAgentCode());
		this.tfOutAgentName.setText(f.getOutAgentName());
		this.tfOutCorp.setText(f.getOutCorp());
		this.tfOutDecl.setText(f.getOutDecl());
		this.tfConveyDay.setText(f.getConveyDay() == null ? "" : f
				.getConveyDay().toString());
		this.tfConveySpa.setText(f.getConveySpa() == null ? "" : f
				.getConveySpa().toString());
		this.cbbOutDate.setDate(f.getOutDate());
		this.cbbOppositeEmsDate.setDate(f.getOppositeEmsDate());
		this.tfOutCopAppNo.setText(f.getOutCopAppNo());
		this.tfOutNote.setText(f.getOutNote());
		this.tfOutLiceNo.setText(f.getOutLiceNo());
		//
		// 转入填写
		//
		this.cbbInDate.setDate(f.getInDate());
		this.tfInCopAppNo.setText(f.getInCopAppNo());
		this.tfInNote.setText(f.getInNote());
		this.cbbInCustoms.setSelectedItem(f.getInCustoms());
		this.tfInLiceNo.setText(f.getInLiceNo());
		this.tfInTradeCode2.setText(f.getInTradeCode2());
		this.tfInTradeName2.setText(f.getInTradeName2());
		this.tfInAgentCode.setText(f.getInAgentCode());
		this.tfInAgentName.setText(f.getInAgentName());
		this.tfInCorp.setText(f.getInCorp());
		this.tfInDecl.setText(f.getInDecl());
		// this.tfInAgentCode1.setText(f.getInEmsNo());
		this.cbbInDate1.setDate(f.getEndDate());
		int anIndexin = -1;
		if (f.getInEmsNo() != null && !"".equals(f.getInEmsNo())) {
			ComboBoxModel m = this.cbbEmsHeadIn.getModel();
			for (int i = 0; i < m.getSize(); i++) {
				BaseEmsHead b = ((BaseEmsHead) m.getElementAt(i));
				if (b != null && b.getEmsNo().equals(f.getInEmsNo())) {
					anIndexin = i;
					break;
				}
			}
		}
		cbbEmsHeadIn.setSelectedIndex(anIndexin);
		this.cbbEndDate.setDate(f.getEndDate());
	}

	/**
	 * 清空数据
	 * 
	 */
	private void emptyData() {
		initTable(new ArrayList());
	}

	/**
	 * 
	 * 窗体状态控制
	 */
	private void setState() {

		if (this.fptAppHead == null) {
			return;
		}
		String declareState = this.fptAppHead.getDeclareState();

		boolean isChangingExe = DeclareState.CHANGING_EXE.equals(declareState);
		boolean isProcessExe = DeclareState.PROCESS_EXE.equals(declareState);
		boolean isApplyPor = DeclareState.APPLY_POR.equals(declareState);
		boolean isWaitEaa = DeclareState.WAIT_EAA.equals(declareState);

		//
		// 能编辑
		//
		boolean isCanEdit = isApplyPor || isChangingExe;
		//
		// 不能编辑
		//
		boolean isNotCanEdit = isProcessExe || isWaitEaa;

		//
		// 合同基本信息面板
		//
		btnSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE && isCanEdit); // 保存
		btnEdit.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState == DataState.BROWSE && isCanEdit); // 修改
		btnCancel.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE && isCanEdit); // 撤消
		btnImport.setEnabled(dataState != DataState.BROWSE && isCanEdit); // 导入

		//
		// 明细
		//
		FptAppItem f = (FptAppItem) commodityTableModel.getCurrentRow();
		boolean isAdd = false;
		if (f != null) {
			isAdd = f.getModifyMarkState() == null
					|| ModifyMarkState.ADDED.equals(f.getModifyMarkState());
		}
		this.btnAddBottom
				.setEnabled(dataState == DataState.BROWSE && isCanEdit);
		this.btnAddBottom1.setEnabled(dataState == DataState.BROWSE
				&& isCanEdit);
		this.btnEditBottom.setEnabled(dataState == DataState.BROWSE
				&& isCanEdit && isAdd);
		this.btnDelete.setEnabled(dataState == DataState.BROWSE && isCanEdit
				&& isAdd);
		this.btnChange.setEnabled(dataState == DataState.BROWSE
				&& isChangingExe && !isAdd);
		this.btnChangingListNo.setEnabled(dataState == DataState.BROWSE
				&& isCanEdit && isAdd);
		this.btnResetListNo.setEnabled(dataState == DataState.BROWSE
				&& isCanEdit);
		this.btnAmountToInteger.setEnabled(dataState == DataState.BROWSE
				&& isCanEdit);
		// this.btnCopy.setEnabled(dataState == DataState.BROWSE && isCanEdit);
		this.btnCopy1.setEnabled(dataState == DataState.BROWSE && isCanEdit);

		boolean isHasDetail = (this.commodityTableModel != null && this.commodityTableModel
				.getList().size() > 0);

		boolean isEnabled = dataState != DataState.BROWSE;

		this.cbbProjectType.setEnabled(false);

		String projectTypeName = "";
		// if (fptAppHead != null && fptAppHead.getProjectType() != null) {
		// projectTypeName = ProjectType.getNote(fptAppHead.getProjectType());
		// }
		// this.btnAddBottom1.setText("新增来自" + projectTypeName);
		this.btnAddBottom1.setText("新增");

		//
		// 显示控制面板
		//
		if (this.fptInOutFlag.equals(FptInOutFlag.OUT)) {
			this.cbbAppClass.setEnabled(isEnabled);
			this.tfContrNo.setEditable(isEnabled);
			this.txtSaleContractNo.setEditable(isEnabled);
			// this.tfOutTradeCode.setEditable(isEnabled);
			// this.tfOutTradeName.setEditable(isEnabled);
			this.tfOutTradeCode.setEditable(false);
			this.tfOutTradeName.setEditable(false);

			this.cbbOutDistrict.setEnabled(isEnabled);
			this.cbbCustoms.setEnabled(isEnabled);
			this.cbbEmsHead.setEnabled(isEnabled && !isHasDetail);

			this.cbbScmCoc.setEnabled(isEnabled);
			this.tfSeqNo.setEditable(false);
			this.cbbInDistrict.setEnabled(isEnabled);
			this.tfOutTradeCode2.setEditable(isEnabled);
			this.tfOutTradeName2.setEditable(isEnabled);
			this.btnOutTradeCode2.setEnabled(isEnabled);
			this.tfOutAgentCode.setEditable(isEnabled);
			this.tfOutAgentName.setEditable(isEnabled);
			this.tfOutCorp.setEditable(isEnabled);
			this.tfOutDecl.setEditable(isEnabled);
			this.tfConveyDay.setEditable(isEnabled);
			this.tfConveySpa.setEditable(isEnabled);
			// this.cbbOutDate.setEnabled(isEnabled);
			this.tfOutCopAppNo.setEditable(false);
			this.tfOutNote.setEditable(isEnabled);
			this.tfOutLiceNo.setEditable(isEnabled);
			this.cbbOppositeEmsDate.setEnabled(isEnabled);
			this.cbbInDate1.setEnabled(isEnabled);

		} else if (this.fptInOutFlag.equals(FptInOutFlag.IN)) {

			// this.tfAppNo.setEditable(isEnabled && !isChangingExe);
			this.txtSaleContractNo.setEnabled(isEnabled);
			this.tfSeqNo.setEditable(isEnabled);
			// this.cbbInDate.setEnabled(isEnabled);
			this.tfInCopAppNo.setEditable(isEnabled);
			this.tfInNote.setEditable(isEnabled);
			this.cbbInCustoms.setEnabled(isEnabled);
			this.tfInLiceNo.setEditable(isEnabled);
			this.tfInTradeCode2.setEditable(isEnabled);
			this.tfInTradeName2.setEditable(isEnabled);
			this.btnInTradeCode2.setEnabled(isEnabled);
			this.tfInAgentCode.setEditable(isEnabled);
			this.tfInAgentName.setEditable(isEnabled);
			this.tfInCorp.setEditable(isEnabled);
			this.tfInDecl.setEditable(isEnabled);
			this.cbbScmCoc.setEnabled(isEnabled);
			this.cbbOppositeEmsDate.setEnabled(isEnabled);
			// this.tfInAgentCode1.setEditable(isEnabled);
			// this.cbbInDate1.setEnabled(isEnabled);
			this.cbbEmsHeadIn.setEnabled(isEnabled);
			if (declareState.equals("4")) {
				this.cbbInDate1.setEnabled(true);
			} else {
				this.cbbInDate1.setEnabled(false);
			}
			this.cbbEmsHead.setEnabled(false);
		}

		this.cbbEndDate.setEnabled(dataState == DataState.EDIT);

		//
		// 只变更第一次,为什么写在这里,是因为我想把改变状态的代码都放在 setState 方法中
		//
		if (isFirst) {
			if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
				TitledBorder t = (TitledBorder) pnIn.getBorder();
				t.setTitleColor(Color.BLUE);
				setContainerEnabled(pnOut, false);
				this.setTitle("转入申请表表头编辑");
				this.jLabel43.setText("             转入状态(转入明细) ");
				this.lbSeqNo.setForeground(Color.blue);
				//
				// 如果是进改变其状态位置
				//
				this.pnIn.setLocation(pnIn.getX(), pnOut.getY());
				this.pnOut.setLocation(pnOut.getX(),
						pnIn.getHeight() + pnIn.getY() + 2);

			} else {
				TitledBorder t = (TitledBorder) pnOut.getBorder();
				t.setTitleColor(Color.BLUE);

				setContainerEnabled(pnIn, false);
				this.cbbInDate1.setEnabled(false);
				this.setTitle("转出申请表表头编辑");
				this.jLabel43.setText("             转出状态(转出明细) ");
				this.lbSeqNo.setForeground(Color.black);
			}
			isFirst = false;
		}

	}

	private boolean vaildatorDataIsNull() {

		if (cbbProjectType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "项目类型不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		if (this.fptInOutFlag.equals(FptInOutFlag.OUT)) {
			if (this.tfOutTradeCode.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转出企业代码不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.tfOutTradeName.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转出企业名称不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.tfOutTradeName2.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转出申报企业名称不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.cbbEmsHead.toString().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转出手册/帐册编号不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.tfOutCopAppNo.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转出企业内部编号不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (this.cbbOutDistrict.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(null, "转出地不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.cbbCustoms.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(null, "转出地海关不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.tfInTradeCode.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转入企业代码/名称不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.cbbInDistrict.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(null, "目的地不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		} else if (this.fptInOutFlag.equals(FptInOutFlag.IN)) {

			if (null == tfSeqNo.getText()
					|| "".equals(tfSeqNo.getText().trim())) {
				JOptionPane.showMessageDialog(null, "【电子口岸统一编号】不能为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (this.tfInCopAppNo.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转入企业内部编号不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (this.tfInTradeCode2.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "转入申报企业代码不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (this.cbbInCustoms.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(null, "转入地海关不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (this.cbbEmsHeadIn.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(null, "转入手册号不可为空!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (!"".equals(this.tfOutAgentCode.getText())
					&& this.tfOutAgentCode.getText().trim().length() != 9) {
				JOptionPane.showMessageDialog(null, "转入申报企业代码必须为9位!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (this.tfInLiceNo.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null,
						"转出申报企业组织机构名称不可谓空，无审批时填写“人工审批”!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		String declareState = this.fptAppHead.getDeclareState();
		boolean isChangingExe = DeclareState.CHANGING_EXE.equals(declareState);
		if (isChangingExe && this.cbbEndDate.getDate() == null) {
			JOptionPane.showMessageDialog(null, "申请表有效期不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		return false;
	}

	/**
	 * 保存数据
	 */
	private void saveData() {

		FptAppHead head = this.fptAppHead;
		head = this.fptManageAction.findFptAppHeadById(
				new Request(CommonVars.getCurrUser()), head.getId());
		if (head == null) {
			JOptionPane.showMessageDialog(this,
					"申请表企业内部编号 " + head.getOutCopAppNo() + " 已经删除不能修改", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (head != null && head.getDeclareState() != null
				&& head.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			JOptionPane.showMessageDialog(this,
					"申请表企业内部编号 " + head.getOutCopAppNo() + " 已经生效不能修改", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		fillData(head);
		// if (!DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
		// if (transferFactoryManageAction.isExistFptAppHeadByOutCopAppNo(
		// new Request(CommonVars.getCurrUser()), head)) {
		// JOptionPane.showMessageDialog(this, "申请表手册编号重复,不能备案!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// // return;
		// }
		// }
		head = this.fptManageAction.saveFptAppHead(
				new Request(CommonVars.getCurrUser()), head);

		this.fptAppHead = head;

		if (dataState == DataState.ADD) {
			fptAppHeadModel.addRow(fptAppHead);
		} else {
			fptAppHeadModel.updateRow(fptAppHead);
		}
		dataState = DataState.BROWSE;
		setState();
	}

	/**
	 * 设置容器除JLabel外所有控件的enabled属性
	 */
	protected void setContainerEnabled(Container container, boolean isFlag) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component component = container.getComponent(i);
			if (!(component instanceof JLabel)) {
				if (!(component instanceof JTextField)) {
					component.setEnabled(isFlag);
				} else {
					((JTextField) component).setEditable(isFlag);
				}
				if (component instanceof Container) {
					setContainerEnabled((Container) component, isFlag);
				}
			} else {
				((JLabel) component).setForeground(new Color(51, 51, 51));
			}
		}
	}

	/**
	 * 新增对象时初始化窗体控件
	 * 
	 */
	private void initUIComponents() {
		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
			this.jLabel1.setText("客户");
		} else {
			this.jLabel1.setText("供应商");
		}
		this.cbbOutDistrict.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		initComboBoxRenderer(this.cbbOutDistrict);
		this.cbbInDistrict.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		initComboBoxRenderer(this.cbbInDistrict);
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());

		this.cbbInCustoms.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbInCustoms);
		this.cbbInCustoms.setRenderer(CustomBaseRender.getInstance()
				.getRender());

		// List<Company> companies = this.systemAction.findCompanies();
		DefaultComboBoxModel scmCocs = new DefaultComboBoxModel();
		if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
			scmCocs = new DefaultComboBoxModel(super.getManufacturer()
					.toArray());
		} else {
			scmCocs = new DefaultComboBoxModel(super.getCustomer().toArray());
		}
		this.cbbScmCoc.setModel(scmCocs);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 280);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 110, 170));
		// this.cbbScmCoc.setSelectedItem(null);
		cbbScmCoc.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					initFptAppHeadByScmCoc();
				}
			}
		});

		// if (this.cbbOutComp.getItemCount() > 0) {
		// this.cbbOutComp.setSelectedIndex(-1);
		// }

		this.cbbAppClass.removeAllItems();
		this.cbbAppClass.addItem(new ItemProperty(AppClass.Z, AppClass
				.getNote(AppClass.Z)));
		this.cbbAppClass.addItem(new ItemProperty(AppClass.H, AppClass
				.getNote(AppClass.H)));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbAppClass,
				"code", "name", 280);
		this.cbbAppClass.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 230));
		this.cbbAppClass.setSelectedIndex(0);

		this.cbbProjectType.removeAllItems();
		if (ProjectType.BCUS == parameterSet.getProjectType()) {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCUS), "电子帐册"));
		} else if (ProjectType.BCS == parameterSet.getProjectType()) {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCS), "电子化手册"));
		} else if (ProjectType.DZSC == parameterSet.getProjectType()) {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.DZSC), "电子手册"));
		} else {
			this.cbbProjectType.addItem(new ItemProperty(String
					.valueOf(ProjectType.BCUS), "电子帐册"));
		}
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(0);
		initUICbb();
		// this.cbbProjectType.addItemListener(new java.awt.event.ItemListener()
		// {
		// public void itemStateChanged(java.awt.event.ItemEvent e) {
		// if (e.getStateChange() == ItemEvent.SELECTED) {
		// initUICbb();
		// }
		// }
		//
		// });
		this.cbbEmsHead.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					initUIEmsHead();
				}
			}
		});
		// this.cbbEmsHeadIn.addItemListener(new java.awt.event.ItemListener() {
		// public void itemStateChanged(java.awt.event.ItemEvent e) {
		// if (e.getStateChange() == ItemEvent.SELECTED) {
		// initUIEmsHeadIn();
		// }
		// }
		// });
		initTable(new ArrayList());
		// 当申请单状态为正在变更时，申请单有效期位置放于最上方，由转出方填写
		if (fptAppHead.getDeclareState().equals(DeclareState.CHANGING_EXE)
				&& FptInOutFlag.OUT.equals(fptInOutFlag)) {
			pnIn.remove(cbbInDate1);
			pnIn.remove(jLabel5101);
		} else if (!fptAppHead.getDeclareState().equals(
				DeclareState.CHANGING_EXE)
				&& FptInOutFlag.OUT.equals(fptInOutFlag)) {
			pnMaster.remove(cbbInDate1);
			pnIn.add(cbbInDate1);
			pnMaster.remove(jLabel5101);
			pnIn.add(jLabel5101);
		}
	}

	private void initFptAppHeadByScmCoc() {
		if (cbbScmCoc.getSelectedItem() == null) {
			return;
		}
		ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
		Brief f = scmCoc.getBrief();

		if (this.getFptInOutFlag().equals(FptInOutFlag.OUT)) {
			// 由于公司没有在海关注册所以不可返写 基础资料 需要控制
			if (f != null) {
				this.tfInTradeCode.setText(f.getCode());
				this.tfInTradeName.setText(f.getName());
			} else {
				this.tfInTradeCode.setText("");
				this.tfInTradeName.setText("");
			}

		} else if (this.getFptInOutFlag().equals(FptInOutFlag.IN)) {
			// 由于公司没有在海关注册所以不可返写 基础资料 需要控制
			if (f != null) {
				this.tfOutTradeCode.setText(f.getCode());
				this.tfOutTradeName.setText(f.getName());
			} else {
				this.tfOutTradeCode.setText("");
				this.tfOutTradeName.setText("");
			}
		}
		this.cbbInDistrict.setSelectedItem(scmCoc.getSrcDistrict());
		if (scmCoc.getTransportationTime() != null
				&& scmCoc.getTransportationTime().intValue() != 0) {
			this.tfConveyDay.setText(String.valueOf(scmCoc
					.getTransportationTime().intValue()));
		}
		// String converDay = scmCoc.getTransportationTime()==null?"":
		// String.valueOf(scmCoc.getTransportationTime().intValue());
		if (scmCoc.getDeliveryDistance() != null
				&& scmCoc.getDeliveryDistance().intValue() != 0) {
			this.tfConveySpa.setText(String.valueOf(scmCoc
					.getDeliveryDistance().intValue()));
		}
		// String tfConveySpa = scmCoc.getDeliveryDistance()==null?"":
		// String.valueOf(scmCoc.getDeliveryDistance().intValue());
		// this.tfConveySpa.setText(tfConveySpa);
	}

	/**
	 * 初始化Cbb呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
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
			jPanel1.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new Rectangle(587, 4, 113, 22));
			cbbProjectType.setEnabled(false);
		}
		return cbbProjectType;
	}

	private void initUIEmsHead() {
		if (cbbEmsHead.getSelectedItem() == null) {
			return;
		}

		String emsNo = ((BaseEmsHead) this.cbbEmsHead.getSelectedItem())
				.getEmsNo();
		ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
		if (item == null) {
			JOptionPane.showMessageDialog(DgFptAppHead.this, "请先选择手册类型");
			return;
		}
		int projectType = Integer.parseInt(item.getCode());
		if (projectType == ProjectType.BCS) {
			if (emsNo != null && !(emsNo.equals(""))) {
				Contract con = contractAction.findContractByEmsNO(new Request(
						CommonVars.getCurrUser()), emsNo);
				if (con != null && !(emsNo.equals(""))) {
					this.tfContrNo.setText(con.getImpContractNo());
				}
			}
		}

	}

	// private void initUIEmsHeadIn(){
	// if(cbbEmsHeadIn.getSelectedItem()==null){
	// return ;
	// }
	// String emsNo = ((BaseEmsHead)
	// this.cbbEmsHeadIn.getSelectedItem()).getEmsNo();
	// ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
	// int projectType = Integer.parseInt(item.getCode());
	// if (projectType == ProjectType.BCS) {
	// if(emsNo != null && !(emsNo.equals(""))){
	// Contract con = contractAction.findContractByEmsNO(new
	// Request(CommonVars.getCurrUser()),emsNo);
	// }}
	//
	// }
	private void initUICbb() {
		if (cbbProjectType.getSelectedItem() == null) {
			return;
		}

		cbbEmsHead.removeAllItems();
		cbbEmsHeadIn.removeAllItems();
		// 电子帐册
		// cbbEmsHead.setForeground(Color.red);
		ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
		int projectType = Integer.parseInt(item.getCode());
		if (projectType == ProjectType.BCUS) {
			List contracts = manualDeclareAction
					.findEmsHeadH2kInExecuting(new Request(CommonVars
							.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					this.cbbEmsHead.addItem(contracts.get(i));
					this.cbbEmsHeadIn.addItem(contracts.get(i));
				}

			}
		} else if (projectType == ProjectType.DZSC) {

			List contracts = dzscAction.findExeEmsPorHead(new Request(
					CommonVars.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					// this.cbbEmsHead.addItem(((BaseEmsHead) contracts.get(i))
					// .getEmsNo());
					this.cbbEmsHead.addItem(contracts.get(i));
					this.cbbEmsHeadIn.addItem(contracts.get(i));
				}

			}
			this.cbbEmsHead.setSelectedItem(null);
		} else if (projectType == ProjectType.BCS) {

			List contracts = contractAction
					.findContractByProcessExe(new Request(CommonVars
							.getCurrUser(), true));
			if (contracts != null && contracts.size() > 0) {
				for (int i = 0; i < contracts.size(); i++) {
					// this.cbbEmsHead.addItem(((BaseEmsHead) contracts.get(i))
					// .getEmsNo());
					this.cbbEmsHead.addItem(contracts.get(i));
					this.cbbEmsHeadIn.addItem(contracts.get(i));
				}

			}
			this.cbbEmsHead.setSelectedItem(null);
			this.cbbEmsHeadIn.setSelectedItem(null);
		}

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEmsHead,
				"emsNo", "emsNo", 150);// Contract
		this.cbbEmsHead.setRenderer(CustomBaseRender.getInstance().getRender(
				"emsNo", "emsNo", 150, 0));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEmsHeadIn,
				"emsNo", "emsNo", 150);
		this.cbbEmsHeadIn.setRenderer(CustomBaseRender.getInstance().getRender(
				"emsNo", "emsNo", 150, 0));

	}

	// private void showEmsNoData(int projectType) {
	// // this.cbbEmsNo.removeAllItems();
	// // List list = this.transferFactoryManageAction.findEmsHeadByProjectType(
	// // new Request(CommonVars.getCurrUser(), true), projectType);
	// // for (int i = 0; i < list.size(); i++) {
	// // this.cbbEmsNo.addItem((TempCustomsEmsInfo) list.get(i));
	// // }
	// // this.cbbEmsNo.setSelectedIndex(-1);
	// this.tfEmsNo.setText("");
	// switch (projectType) {
	// case ProjectType.BCS:
	// lbEmsNo.setText("纸质手册号");
	// break;
	// case ProjectType.BCUS:
	// lbEmsNo.setText("联网监管帐册号");
	// break;
	// case ProjectType.DZSC:
	// lbEmsNo.setText("电子手册号");
	// break;
	// }
	// }

	/**
	 * This method initializes tfOutAgentCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutAgentCode() {
		if (tfOutAgentCode == null) {
			tfOutAgentCode = new JTextField();
			tfOutAgentCode.setBounds(new Rectangle(174, 157, 126, 22));
			tfOutAgentCode.setDocument(new CustomDocument(9));
		}
		return tfOutAgentCode;
	}

	/**
	 * 限定字符长度
	 * 
	 * @author Administrator
	 *
	 */
	class CustomDocument extends PlainDocument {

		int len;

		public CustomDocument(int len) {
			this.len = len;
		}

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= len || str.length() > len
					|| super.getLength() + str.length() > len) {
				return;
			}

			super.insertString(offs, str, a);
		}
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutTradeCode2() {
		if (tfOutTradeCode2 == null) {
			tfOutTradeCode2 = new JTextField();
			tfOutTradeCode2.setEditable(true);
			tfOutTradeCode2.setBounds(new Rectangle(174, 250, 109, 22));
		}
		return tfOutTradeCode2;
	}

	/**
	 * This method initializes pnOut
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnOut() {
		if (pnOut == null) {
			jLabel521 = new JLabel();
			jLabel521.setBounds(new Rectangle(29, 135, 142, 21));
			jLabel521.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel521.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel521.setText("转入企业代码");
			jLabel521.setForeground(Color.blue);
			jLabel531 = new JLabel();
			jLabel531.setBounds(new Rectangle(356, 134, 96, 21));
			jLabel531.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel531.setText("转入企业名称");
			jLabel531.setForeground(Color.blue);
			jLabel515 = new JLabel();
			jLabel515.setBounds(new Rectangle(47, 63, 124, 22));
			jLabel515.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel515.setForeground(Color.blue);
			jLabel515.setText("转出手册/帐册编号");
			jLabel514 = new JLabel();
			jLabel514.setBounds(new Rectangle(45, 228, 126, 22));
			jLabel514.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel514.setBackground(new Color(238, 238, 238));
			jLabel514.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel514.setForeground(Color.blue);
			jLabel514.setText("转出企业批准证编号");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(351, 61, 102, 22));
			jLabel2.setForeground(Color.blue);
			jLabel2.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel2.setText("转出企业内部编号");
			jLabel55 = new JLabel();
			jLabel55.setBounds(new Rectangle(46, 275, 125, 22));
			jLabel55.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel55.setText("转出备注");
			jLabel569 = new JLabel();
			jLabel569.setBounds(new Rectangle(72, 181, 99, 22));
			jLabel569.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel569.setText("转出企业法人/电话");
			jLabel568 = new JLabel();
			jLabel568.setBounds(new Rectangle(308, 157, 145, 23));
			jLabel568.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel568.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel568.setForeground(Color.blue);
			jLabel568.setText("转出申报企业组织机构名称");
			jLabel567 = new JLabel();
			jLabel567.setBounds(new Rectangle(391, 205, 62, 22));
			jLabel567.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel567.setText("送货距离");
			jLabel566 = new JLabel();
			jLabel566.setBounds(new Rectangle(80, 205, 91, 22));
			jLabel566.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel566.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel566.setText("预计运输耗时");
			jLabel565 = new JLabel();
			jLabel565.setBounds(new Rectangle(355, 250, 96, 22));
			jLabel565.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel565.setText("转出申报企业名称");
			jLabel565.setForeground(Color.BLACK);
			jLabel564 = new JLabel();
			jLabel564.setBounds(new Rectangle(6, 157, 165, 22));
			jLabel564.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel564.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel564.setForeground(Color.blue);
			jLabel564.setText("转出申报企业9位组织机构代码");
			jLabel563 = new JLabel();
			jLabel563.setBounds(new Rectangle(93, 112, 76, 22));
			jLabel563.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel563.setForeground(Color.blue);
			jLabel563.setText("转出申报日期");
			jLabel562 = new JLabel();
			jLabel562.setBounds(new Rectangle(29, 250, 142, 22));
			jLabel562.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel562.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel562.setText("转出申报企业代码");
			jLabel562.setForeground(Color.BLACK);
			jLabel561 = new JLabel();
			jLabel561.setBounds(new Rectangle(409, 110, 44, 22));
			jLabel561.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel561.setForeground(Color.blue);
			jLabel561.setText("目的地");
			jLabel56 = new JLabel();
			jLabel56.setBounds(new Rectangle(386, 85, 67, 22));
			jLabel56.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel56.setForeground(Color.blue);
			jLabel56.setText("转出地海关");
			jLabel54 = new JLabel();
			jLabel54.setBounds(new Rectangle(130, 87, 41, 22));
			jLabel54.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel54.setForeground(Color.blue);
			jLabel54.setText("转出地");
			jLabel53 = new JLabel();
			jLabel53.setBounds(new Rectangle(378, 39, 75, 22));
			jLabel53.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel53.setForeground(Color.blue);
			jLabel53.setText("转出企业名称");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(354, 181, 99, 22));
			jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4.setText("转出申报人/电话 ");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(373, 16, 78, 22));
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			// jLabel3.setForeground(Color.blue);
			jLabel3.setText("企业合同号");
			pnOut = new JPanel();
			pnOut.setLayout(null);
			pnOut.setBounds(new Rectangle(8, 50, 710, 305));
			// pnOut.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
			// "\u5bf9\u65b9\u516c\u53f8\u5546\u54c1\u8d44\u6599",
			// TitledBorder.DEFAULT_JUSTIFICATION,
			// TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN,
			// 12), Color.blue));
			pnOut.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u8f6c\u51fa\u4f01\u4e1a\u586b\u5199",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			pnOut.add(getCbbCustoms(), null);
			pnOut.add(getCbbOutDate(), null);
			pnOut.add(getCbbInDistrict(), null);
			pnOut.add(getTfContrNo(), null);
			pnOut.add(jLabel52, null);
			pnOut.add(getTfOutAgentCode(), null);
			pnOut.add(getLbEmsNo(), null);
			pnOut.add(getTfOutTradeCode2(), null);
			pnOut.add(getTfConveySpa(), null);
			pnOut.add(jLabel3, null);
			pnOut.add(jLabel4, null);
			pnOut.add(jLabel53, null);
			pnOut.add(jLabel54, null);
			pnOut.add(jLabel56, null);
			pnOut.add(getCbbOutDistrict(), null);
			pnOut.add(jLabel561, null);
			pnOut.add(jLabel562, null);
			pnOut.add(jLabel563, null);
			pnOut.add(jLabel564, null);
			pnOut.add(jLabel565, null);
			pnOut.add(jLabel566, null);
			pnOut.add(jLabel567, null);
			pnOut.add(jLabel568, null);
			pnOut.add(jLabel569, null);
			pnOut.add(getTfOutTradeName(), null);
			pnOut.add(getTfOutTradeName2(), null);
			pnOut.add(getTfOutAgentName(), null);
			pnOut.add(getTfConveyDay(), null);
			pnOut.add(getTfOutTradeCode(), null);
			pnOut.add(getTfOutCorp(), null);
			pnOut.add(getTfOutDecl(), null);
			pnOut.add(getTfOutNote(), null);
			pnOut.add(jLabel55, null);
			pnOut.add(jLabel2, null);
			pnOut.add(getTfOutCopAppNo(), null);
			pnOut.add(getTfOutLiceNo(), null);
			pnOut.add(jLabel514, null);
			pnOut.add(jLabel515, null);
			pnOut.add(getCbbAppClass(), null);
			pnOut.add(getCbbEmsHead(), null);
			pnOut.add(getBtnOutTradeCode2(), null);
			pnOut.add(getTfInTradeCode(), null);
			pnOut.add(getTfInTradeName(), null);
			pnOut.add(jLabel531, null);
			pnOut.add(jLabel521, null);

			JLabel labelsale = new JLabel();
			labelsale.setText("购销合同号");
			labelsale.setHorizontalAlignment(SwingConstants.RIGHT);
			labelsale.setBounds(new Rectangle(376, 229, 78, 22));
			pnOut.add(labelsale);

			txtSaleContractNo = new JTextField();
			txtSaleContractNo.setBounds(new Rectangle(453, 230, 126, 22));
			pnOut.add(txtSaleContractNo);
		}
		return pnOut;
	}

	/**
	 * This method initializes pnIn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnIn() {
		if (pnIn == null) {
			jLabel5101 = new JLabel();
			jLabel5101.setBounds(new Rectangle(376, 111, 75, 22));
			jLabel5101.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel5101.setText("申请表有效期");
			jLabel5101.setPreferredSize(new Dimension(163, 22));
			jLabel5121 = new JLabel();
			jLabel5121.setBounds(new Rectangle(103, 112, 70, 22));
			jLabel5121.setPreferredSize(new Dimension(163, 22));
			jLabel5121.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel5121.setText("转入手册号");
			jLabel5121.setForeground(Color.blue);
			jLabel513 = new JLabel();
			jLabel513.setBounds(new Rectangle(300, 87, 150, 22));
			jLabel513.setPreferredSize(new Dimension(163, 22));
			jLabel513.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel513.setForeground(Color.blue);
			jLabel513.setText("转入申报企业组织机构名称");
			jLabel512 = new JLabel();
			jLabel512.setBounds(new Rectangle(8, 87, 165, 22));
			jLabel512.setPreferredSize(new Dimension(163, 22));
			jLabel512.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel512.setForeground(Color.blue);
			jLabel512.setText("转入申报企业9位组织机构代码");
			jLabel574 = new JLabel();
			jLabel574.setPreferredSize(new Dimension(163, 22));
			jLabel574.setLocation(new Point(119, 160));
			jLabel574.setSize(new Dimension(53, 22));
			jLabel574.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel574.setText("转入备注");
			jLabel511 = new JLabel();
			jLabel511.setBounds(new Rectangle(336, 64, 113, 22));
			jLabel511.setPreferredSize(new Dimension(163, 22));
			jLabel511.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel511.setText("转入申报人/联系电话");
			jLabel510 = new JLabel();
			jLabel510.setBounds(new Rectangle(50, 64, 123, 22));
			jLabel510.setPreferredSize(new Dimension(163, 22));
			jLabel510.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel510.setText("转入企业法人/联系电话");
			jLabel59 = new JLabel();
			jLabel59.setBounds(new Rectangle(95, 42, 78, 22));
			jLabel59.setPreferredSize(new Dimension(163, 22));
			jLabel59.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel59.setForeground(Color.blue);
			jLabel59.setText("转入申报日期");
			jLabel58 = new JLabel();
			jLabel58.setBounds(new Rectangle(352, 132, 99, 22));
			jLabel58.setPreferredSize(new Dimension(163, 22));
			jLabel58.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel58.setText("转入申报企业名称");
			jLabel58.setForeground(Color.BLACK);
			jLabel573 = new JLabel();
			jLabel573.setBounds(new Rectangle(74, 132, 99, 22));
			jLabel573.setPreferredSize(new Dimension(163, 22));
			jLabel573.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel573.setText("转入申报企业代码");
			jLabel573.setForeground(Color.BLACK);
			jLabel572 = new JLabel();
			jLabel572.setBounds(new Rectangle(339, 42, 112, 22));
			jLabel572.setPreferredSize(new Dimension(163, 22));
			jLabel572.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel572.setForeground(Color.blue);
			jLabel572.setText("转入企业批准证编号");
			jLabel571 = new JLabel();
			jLabel571.setBounds(new Rectangle(389, 18, 62, 22));
			jLabel571.setPreferredSize(new Dimension(163, 22));
			jLabel571.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel571.setForeground(Color.blue);
			jLabel571.setText("转入地海关");
			jLabel57 = new JLabel();
			jLabel57.setBounds(new Rectangle(74, 18, 99, 22));
			jLabel57.setPreferredSize(new Dimension(163, 22));
			jLabel57.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel57.setForeground(Color.blue);
			jLabel57.setText("转入企业内部编号");
			pnIn = new JPanel();
			pnIn.setLayout(null);
			pnIn.setBounds(new Rectangle(8, 360, 710, 191));
			pnIn.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u8f6c\u5165\u4f01\u4e1a\u586b\u5199",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			pnIn.add(jLabel57, null);
			pnIn.add(jLabel571, null);
			pnIn.add(jLabel572, null);
			pnIn.add(jLabel573, null);
			pnIn.add(jLabel58, null);
			pnIn.add(jLabel59, null);
			pnIn.add(jLabel510, null);
			pnIn.add(jLabel511, null);
			pnIn.add(jLabel574, null);
			pnIn.add(jLabel512, null);
			pnIn.add(jLabel513, null);
			pnIn.add(getTfInCopAppNo(), null);
			pnIn.add(getTfInTradeCode2(), null);
			pnIn.add(getTfInCorp(), null);
			pnIn.add(getTfInAgentCode(), null);
			pnIn.add(getTfInLiceNo(), null);
			pnIn.add(getTfInNote(), null);
			pnIn.add(getTfInAgentName(), null);
			pnIn.add(getTfInDecl(), null);
			pnIn.add(getTfInTradeName2(), null);
			pnIn.add(getCbbInDate(), null);
			pnIn.add(getCbbInCustoms(), null);
			pnIn.add(getBtnInTradeCode2(), null);
			pnIn.add(jLabel5121, null);
			pnIn.add(jLabel5101, null);
			pnIn.add(getCbbInDate1(), null);
			pnIn.add(getCbbEmsHead1(), null);
		}
		return pnIn;
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
		}
		return jPanel3;
	}

	/**
	 * This method initializes cbbOutDistrict
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOutDistrict() {
		if (cbbOutDistrict == null) {
			cbbOutDistrict = new JComboBox();
			cbbOutDistrict.setBounds(new Rectangle(174, 87, 126, 22));
		}
		return cbbOutDistrict;
	}

	/**
	 * This method initializes tfOutTradeName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutTradeName() {
		if (tfOutTradeName == null) {
			tfOutTradeName = new JTextField();
			tfOutTradeName.setBounds(new Rectangle(454, 39, 237, 22));
		}
		return tfOutTradeName;
	}

	/**
	 * This method initializes tfOutTradeName2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutTradeName2() {
		if (tfOutTradeName2 == null) {
			tfOutTradeName2 = new JTextField();
			tfOutTradeName2.setBounds(new Rectangle(454, 251, 237, 22));
		}
		return tfOutTradeName2;
	}

	/**
	 * This method initializes tfOutAgentName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutAgentName() {
		if (tfOutAgentName == null) {
			tfOutAgentName = new JTextField();
			tfOutAgentName.setBounds(new Rectangle(454, 157, 237, 22));
		}
		return tfOutAgentName;
	}

	/**
	 * This method initializes tfConveyDay
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConveyDay() {
		if (tfConveyDay == null) {
			tfConveyDay = new JTextField();
			tfConveyDay.setBounds(new Rectangle(174, 205, 126, 22));
		}
		return tfConveyDay;
	}

	/**
	 * This method initializes tfOutTradeCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutTradeCode() {
		if (tfOutTradeCode == null) {
			tfOutTradeCode = new JTextField();
			tfOutTradeCode.setBounds(new Rectangle(174, 41, 126, 22));
		}
		return tfOutTradeCode;
	}

	/**
	 * This method initializes tfOutNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutNote() {
		if (tfOutNote == null) {
			tfOutNote = new JTextField();
			tfOutNote.setBounds(new Rectangle(175, 275, 515, 22));
		}
		return tfOutNote;
	}

	/**
	 * This method initializes tfOutCorp
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutCorp() {
		if (tfOutCorp == null) {
			tfOutCorp = new JTextField();
			tfOutCorp.setBounds(new Rectangle(174, 181, 126, 22));
		}
		return tfOutCorp;
	}

	/**
	 * This method initializes tfOutDecl
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutDecl() {
		if (tfOutDecl == null) {
			tfOutDecl = new JTextField();
			tfOutDecl.setBounds(new Rectangle(454, 181, 126, 22));
		}
		return tfOutDecl;
	}

	/**
	 * This method initializes tfInCopAppNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInCopAppNo() {
		if (tfInCopAppNo == null) {
			tfInCopAppNo = new JTextField();
			tfInCopAppNo.setBounds(new Rectangle(175, 18, 126, 22));
		}
		return tfInCopAppNo;
	}

	/**
	 * This method initializes tfInTradeCode2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInTradeCode2() {
		if (tfInTradeCode2 == null) {
			tfInTradeCode2 = new JTextField();
			tfInTradeCode2.setBounds(new Rectangle(175, 135, 109, 22));
		}
		return tfInTradeCode2;
	}

	/**
	 * This method initializes tfInCorp
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInCorp() {
		if (tfInCorp == null) {
			tfInCorp = new JTextField();
			tfInCorp.setBounds(new Rectangle(175, 64, 126, 22));
		}
		return tfInCorp;
	}

	/**
	 * This method initializes tfInAgentCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInAgentCode() {
		if (tfInAgentCode == null) {
			tfInAgentCode = new JTextField();
			tfInAgentCode.setBounds(new Rectangle(175, 87, 126, 22));
			tfInAgentCode.setDocument(new CustomDocument(9));
		}
		return tfInAgentCode;
	}

	/**
	 * This method initializes tfInLiceNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInLiceNo() {
		if (tfInLiceNo == null) {
			tfInLiceNo = new JTextField();
			tfInLiceNo.setBounds(new Rectangle(453, 42, 126, 22));
		}
		return tfInLiceNo;
	}

	/**
	 * This method initializes tfInNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInNote() {
		if (tfInNote == null) {
			tfInNote = new JTextField();
			tfInNote.setBounds(new Rectangle(174, 160, 518, 22));
		}
		return tfInNote;
	}

	/**
	 * This method initializes tfInAgentName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInAgentName() {
		if (tfInAgentName == null) {
			tfInAgentName = new JTextField();
			tfInAgentName.setBounds(new Rectangle(453, 87, 241, 22));
		}
		return tfInAgentName;
	}

	/**
	 * This method initializes tfInDecl
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInDecl() {
		if (tfInDecl == null) {
			tfInDecl = new JTextField();
			tfInDecl.setBounds(new Rectangle(453, 64, 126, 22));
		}
		return tfInDecl;
	}

	/**
	 * This method initializes tfInTradeName2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInTradeName2() {
		if (tfInTradeName2 == null) {
			tfInTradeName2 = new JTextField();
			tfInTradeName2.setBounds(new Rectangle(453, 135, 126, 22));
		}
		return tfInTradeName2;
	}

	/**
	 * This method initializes cbbInDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbInDate() {
		if (cbbInDate == null) {
			cbbInDate = new JCalendarComboBox();
			cbbInDate.setBounds(new Rectangle(175, 42, 126, 22));
			cbbInDate.setEnabled(false);
		}
		return cbbInDate;
	}

	/**
	 * This method initializes cbbInCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbInCustoms() {
		if (cbbInCustoms == null) {
			cbbInCustoms = new JComboBox();
			cbbInCustoms.setBounds(new Rectangle(453, 18, 126, 22));
			cbbInCustoms.setEditable(true);
		}
		return cbbInCustoms;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("\u5173\u95ed");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("基本信息", null, getPnMaster(), null);
			jTabbedPane.addTab("明细资料", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {

							JTabbedPane tab = (JTabbedPane) e.getSource();
							if (tab.getSelectedIndex() == 0) {
								if (fptAppHead != null) {
									showData(fptAppHead);
									setState();
								}
							} else if (tab.getSelectedIndex() == 1) {
								if (dataState != DataState.BROWSE) {
									saveData();
								}
								showItems();
							}
							setState();
						}

					});
		}
		return jTabbedPane;
	}

	private void showItems() {
		if (fptAppHead == null) {
			return;
		}
		List inList = fptManageAction.findFptAppItems(
				new Request(CommonVars.getCurrUser()), fptAppHead.getId(),
				FptInOutFlag.IN);
		List outList = fptManageAction.findFptAppItems(
				new Request(CommonVars.getCurrUser()), fptAppHead.getId(),
				FptInOutFlag.OUT);

		if (FptInOutFlag.IN.equals(fptAppHead.getInOutFlag())) {
			initTable(inList);
			initTable2(outList);
		} else {
			initTable(outList);
			initTable2(inList);
		}
	}

	/**
	 * This method initializes tfOutCopAppNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutCopAppNo() {
		if (tfOutCopAppNo == null) {
			tfOutCopAppNo = new JTextField();
			tfOutCopAppNo.setBounds(new Rectangle(454, 61, 237, 22));
		}
		return tfOutCopAppNo;
	}

	/**
	 * This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnImport.setVisible(false);
			btnImport.setText("\u5bfc\u5165");
		}
		return btnImport;
	}

	/**
	 * This method initializes btnChange
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.setVisible(true);
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commodityTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFptAppHead.this,
								"请选择你要变更的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					editData((FptAppItem) commodityTableModel.getCurrentRow());
				}
			});
		}
		return btnChange;
	}

	/**
	 * @return the fptInOutFlag
	 */
	public String getFptInOutFlag() {
		return fptInOutFlag;
	}

	/**
	 * @param fptInOutFlag
	 *            the fptInOutFlag to set
	 */
	public void setFptInOutFlag(String fptInOutFlag) {
		this.fptInOutFlag = fptInOutFlag;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.setEnabled(false);
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commodityTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFptAppHead.this,
								"请选择你要转抄的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = commodityTableModel.getCurrentRows();
					if (list.size() <= 0) {
						return;
					}
					if (JOptionPane.showConfirmDialog(DgFptAppHead.this,
							"是否转抄!", "提示", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						list = DgFptAppHead.this.fptManageAction
								.copyFptAppItemToHead(
										new Request(CommonVars.getCurrUser()),
										fptAppHead, list);
						commodityTableModel.addRows(list);
					}
				}
			});
		}
		return btnCopy;
	}

	/**
	 * This method initializes btnCopy1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy1() {
		if (btnCopy1 == null) {
			btnCopy1 = new JButton();
			btnCopy1.setVisible(false);
			btnCopy1.setText("转抄到");
			btnCopy1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commodityTableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFptAppHead.this,
								"请选择你要转抄的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = commodityTableModel.getCurrentRows();
					if (list.size() <= 0) {
						return;
					}

					FptAppHead newHead = FptQuery.getInstance()
							.findFptAppHeadByNotExceute(fptAppHead);
					if (newHead != null) {
						if (JOptionPane
								.showConfirmDialog(
										DgFptAppHead.this,
										"是否转抄到 [转出企业内部编号] 是 "
												+ fptAppHead.getOutCopAppNo()
												+ " 的申请表", "提示",
										JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							list = DgFptAppHead.this.fptManageAction
									.copyFptAppItemToHead(new Request(
											CommonVars.getCurrUser()), newHead,
											list);
							JOptionPane.showMessageDialog(DgFptAppHead.this,
									"转抄资料成功", "提示",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
		}
		return btnCopy1;
	}

	/**
	 * This method initializes btnAmountToInteger
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAmountToInteger() {
		if (btnAmountToInteger == null) {
			btnAmountToInteger = new JButton();
			btnAmountToInteger.setVisible(false);
			btnAmountToInteger.setText("\u6570\u91cf\u53d6\u6574");
			btnAmountToInteger
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (commodityTableModel == null
									|| commodityTableModel.getList().size() <= 0) {
								return;
							}
							List list = commodityTableModel.getList();
							if (list.size() <= 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(
									DgFptAppHead.this, "是否将所有记录的数量取整!", "提示",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								list = DgFptAppHead.this.fptManageAction
										.saveFptAppItemAmountInteger(
												new Request(CommonVars
														.getCurrUser()), list);
								commodityTableModel.updateRows(list);
							}
						}
					});
		}
		return btnAmountToInteger;
	}

	/**
	 * This method initializes tfOutLiceNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutLiceNo() {
		if (tfOutLiceNo == null) {
			tfOutLiceNo = new JTextField();
			tfOutLiceNo.setBounds(new Rectangle(174, 228, 126, 22));
		}
		return tfOutLiceNo;
	}

	/**
	 * This method initializes btnAddBottom1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddBottom1() {
		if (btnAddBottom1 == null) {
			btnAddBottom1 = new JButton();
			btnAddBottom1.setText("新增2");

			btnAddBottom1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							addData2();

						}

					});
		}
		return btnAddBottom1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(225);
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes btnResetListNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnResetListNo() {
		if (btnResetListNo == null) {
			btnResetListNo = new JButton();
			btnResetListNo.setText("重排序号");
			btnResetListNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (commodityTableModel == null
									|| commodityTableModel.getList().size() <= 0) {
								return;
							}
							List list = commodityTableModel.getList();
							if (list.size() <= 0) {
								return;
							}
							Vector vet = new Vector();
							list = fptManageAction
									.findFptAppItemsByModifyMarkState(
											new Request(CommonVars
													.getCurrUser()), fptAppHead
													.getId(), fptAppHead
													.getInOutFlag(),
											ModifyMarkState.ADDED);
							int beginSeqNum = fptManageAction
									.findMaxValueByFptAppItemExceptAdd(
											new Request(CommonVars
													.getCurrUser()), fptAppHead
													.getId(), fptAppHead
													.getInOutFlag()) + 1;
							if (list.size() <= 0) {
								JOptionPane.showMessageDialog(
										DgFptAppHead.this, "没有新增的明细项，不能重排序号！",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							//
							// add all to vet
							//
							vet.addAll(list);
							DgFptAppItemSort dg = new DgFptAppItemSort();
							dg.setList(vet);
							dg.setFptManageAction(fptManageAction);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								// list = dg.getList();
								// commodityTableModel.updateRows(list);
								if (fptAppHead != null) {
									if (FptInOutFlag.IN.equals(fptAppHead
											.getInOutFlag())) {
										List inList = fptManageAction.findFptAppItems(
												new Request(CommonVars
														.getCurrUser()),
												fptAppHead.getId(),
												FptInOutFlag.IN);
										initTable(inList);
										// initTable2(outList);
									} else {
										List outList = fptManageAction.findFptAppItems(
												new Request(CommonVars
														.getCurrUser()),
												fptAppHead.getId(),
												FptInOutFlag.OUT);
										initTable(outList);
										// initTable2(inList);
									}
								}
							}
						}
					});
		}
		return btnResetListNo;
	}

	/**
	 * This method initializes btnChangingListNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangingListNo() {
		if (btnChangingListNo == null) {
			btnChangingListNo = new JButton();
			btnChangingListNo.setText("修改序号");
			btnChangingListNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (commodityTableModel == null
									|| commodityTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgFptAppHead.this, "请选择要变更的记录!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							FptAppItem f = (FptAppItem) commodityTableModel
									.getCurrentRow();
							FptAppHead base = fptAppHead;
							//
							// 是否能变更
							//
							if (!ModifyMarkState.ADDED.equals(f
									.getModifyMarkState())) {
								JOptionPane.showMessageDialog(
										DgFptAppHead.this, "该项已经备案，不能变更序号！",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							// int beginSeqNum = transferFactoryManageAction
							// .findMaxValueByFptAppItemExceptAdd(
							// new Request(CommonVars
							// .getCurrUser()), fptAppHead
							// .getId(), fptAppHead
							// .getInOutFlag()) + 1;
							DgFptAppItemChangeListNo dg = new DgFptAppItemChangeListNo();
							dg.setTableModel(commodityTableModel);
							dg.setModifyMark(f.getModifyMarkState());
							dg.setFptAppHead(fptAppHead);
							// dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								//
								// 暂时不用影响性能
								//
								// if (fptAppHead != null) {
								// if (FptInOutFlag.IN.equals(fptAppHead
								// .getInOutFlag())) {
								// List inList = transferFactoryManageAction
								// .findFptAppItems(
								// new Request(CommonVars
								// .getCurrUser()),
								// fptAppHead.getId(),
								// FptInOutFlag.IN);
								// initTable(inList);
								// // initTable2(outList);
								// } else {
								// List outList = transferFactoryManageAction
								// .findFptAppItems(
								// new Request(CommonVars
								// .getCurrUser()),
								// fptAppHead.getId(),
								// FptInOutFlag.OUT);
								// initTable(outList);
								// // initTable2(inList);
								// }
								// }
							}
						}
					});
		}
		return btnChangingListNo;
	}

	/**
	 * This method initializes cbbAppClass
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAppClass() {
		if (cbbAppClass == null) {
			cbbAppClass = new JComboBox();
			cbbAppClass.setBounds(new Rectangle(174, 16, 126, 22));
		}
		return cbbAppClass;
	}

	/**
	 * This method initializes cbbEmsHead
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsHead() {
		if (cbbEmsHead == null) {
			cbbEmsHead = new JComboBox();
			cbbEmsHead.setBounds(new Rectangle(174, 63, 126, 22));
		}
		return cbbEmsHead;
	}

	/**
	 * This method initializes btnOutTradeCode2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOutTradeCode2() {
		if (btnOutTradeCode2 == null) {
			btnOutTradeCode2 = new JButton();
			btnOutTradeCode2.setBounds(new Rectangle(282, 250, 18, 22));
			btnOutTradeCode2.setText("...");
			btnOutTradeCode2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Brief brief = (Brief) CommonQuery.getInstance()
									.getCustomBrief();
							if (brief != null) {
								tfOutTradeCode2.setText(brief.getCode());
								tfOutTradeName2.setText(brief.getName());
							}
						}
					});
		}
		return btnOutTradeCode2;
	}

	/**
	 * This method initializes btnInTradeCode2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInTradeCode2() {
		if (btnInTradeCode2 == null) {
			btnInTradeCode2 = new JButton();
			btnInTradeCode2.setBounds(new Rectangle(281, 135, 20, 21));
			btnInTradeCode2.setText("...");
			btnInTradeCode2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Brief brief = (Brief) CommonQuery.getInstance()
									.getCustomBrief();
							if (brief != null) {
								tfInTradeCode2.setText(brief.getCode());
								tfInTradeName2.setText(brief.getName());
							}
						}
					});
		}
		return btnInTradeCode2;
	}

	/**
	 * This method initializes cbbOppositeEmsDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbOppositeEmsDate() {
		if (cbbOppositeEmsDate == null) {
			cbbOppositeEmsDate = new JCalendarComboBox();
			cbbOppositeEmsDate.setBounds(new Rectangle(374, 27, 137, 21));
			cbbOppositeEmsDate.setEnabled(false);
		}
		return cbbOppositeEmsDate;
	}

	/**
	 * This method initializes tfInTradeCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInTradeCode() {
		if (tfInTradeCode == null) {
			tfInTradeCode = new JTextField();
			tfInTradeCode.setEditable(false);
			tfInTradeCode.setBounds(new Rectangle(174, 135, 126, 21));
		}
		return tfInTradeCode;
	}

	/**
	 * This method initializes tfInTradeName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInTradeName() {
		if (tfInTradeName == null) {
			tfInTradeName = new JTextField();
			tfInTradeName.setEditable(false);
			tfInTradeName.setBounds(new Rectangle(454, 133, 237, 21));
		}
		return tfInTradeName;
	}

	/**
	 * This method initializes cbbInDate1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbInDate1() {
		if (cbbInDate1 == null) {
			cbbInDate1 = new JCalendarComboBox();
			cbbInDate1.setBounds(new Rectangle(453, 112, 126, 22));
			// cbbInDate1.setEnabled(false);
		}
		return cbbInDate1;
	}

	/**
	 * This method initializes cbbEmsHead1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsHead1() {
		if (cbbEmsHeadIn == null) {
			cbbEmsHeadIn = new JComboBox();
			cbbEmsHeadIn.setBounds(new Rectangle(175, 113, 126, 21));
			cbbEmsHeadIn.setEditable(false);
		}
		return cbbEmsHeadIn;
	}

	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			// cbbEndDate.setBounds(new Rectangle(374, 27, 107, 21));
			cbbEndDate.setBounds(588, 30, 112, 21);
			cbbEndDate.setEnabled(false);
		}
		return cbbEndDate;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("申请表有效期");
			label.setHorizontalAlignment(SwingConstants.LEFT);
			// label.setBounds(new Rectangle(277, 27, 103, 21));
			label.setBounds(515, 32, 73, 21);
		}
		return label;
	}
} // @jve:decl-index=0:visual-constraint="10,2"
