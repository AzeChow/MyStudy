package com.bestway.common.client.fpt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.TempCustomsEmsInfo;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

public class PnMakeCustomsBillList extends PnCommon {

	private JRadioButton rbTransferImport = null;

	private JRadioButton rbTransferExport = null;

	private ButtonGroup buttonGroup = null;

	private ButtonGroup buttonGroup1 = null;

	private javax.swing.JLabel lbScmCoc = null;

	private JPanel jPanel = null;

	private JComboBox cbbScmCoc = null;

	private JTextField tfMemo1 = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbMakeNewBill = null;

	private JRadioButton rbIntoOldBill = null;

	private JComboBox cbbImpExpType = null;

	private JTextField tfEnterpriseBillNo = null;

	private JComboBox cbbCustoms = null;

	private JComboBox cbbImpExpPort = null;

	private JComboBox cbbTradeMode = null;

	private JComboBox cbbTransportMode = null;

	private JButton btnEnterpriseBillNo = null;

	private boolean isImportGoods = true;

	private ApplyToCustomsBillList applyToCustomsBillList = null;

	private DzscCustomsBillList dzscCustomsBillList = null;

	// private EmsHeadH2k emsHeadH2k = null;

	private JComboBox cbbEmsNo = null;

	private JLabel jLabel8 = null;

	private JLabel lbEmsNo = null;

	private JComboBox cbbProjectType = null;

	public int getProjectType() {
		int projectType = -1;
		if (cbbProjectType.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbProjectType.getSelectedItem();
			projectType = Integer.parseInt(item.getCode());
		}
		return projectType;
	}

	/**
	 * This is the default constructor
	 */
	public PnMakeCustomsBillList() {
		super();
		initialize();
		// List list = manualDeclearAction.findEmsHeadH2kInExecuting(new
		// Request(
		// CommonVars.getCurrUser()));
		// if (list == null || list.size() <= 0) {
		// emsHeadH2k = null;
		// } else {
		// emsHeadH2k = (EmsHeadH2k) (list.get(0));
		// }
		getButtonGroup();
		getButtonGroup1();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel8 = new JLabel();
		jLabel8.setBounds(new java.awt.Rectangle(285, 72, 69, 23));
		jLabel8.setText("\u9879\u76ee\u7c7b\u578b");
		javax.swing.JLabel jLabel7 = new JLabel();
		javax.swing.JLabel jLabel6 = new JLabel();
		javax.swing.JLabel jLabel5 = new JLabel();
		javax.swing.JLabel jLabel3 = new JLabel();
		javax.swing.JLabel jLabel2 = new JLabel();
		lbEmsNo = new JLabel();
		javax.swing.JLabel jLabel = new JLabel();
		javax.swing.JLabel jLabel4 = new JLabel();

		lbScmCoc = new JLabel();

		this.setLayout(null);
		this.setSize(500, 248);

		lbScmCoc.setBounds(18, 148, 101, 20);
		lbScmCoc.setText(" 客户/供应商名称");
		jLabel4.setBounds(18, 199, 101, 20);

		jLabel4.setText("备注");

		jLabel.setBounds(19, 96, 101, 20);
		jLabel.setText("清单类型");
		lbEmsNo.setBounds(285, 96, 67, 20);
		lbEmsNo.setText("帐册编号");
		jLabel2.setBounds(19, 121, 101, 20);
		jLabel2.setText("企业清单编号");
		jLabel3.setBounds(285, 148, 67, 20);
		jLabel3.setText("进出口岸");
		jLabel5.setBounds(285, 121, 67, 20);
		jLabel5.setText("申报地海关");
		jLabel6.setBounds(18, 174, 101, 20);
		jLabel6.setText("运输方式");
		jLabel7.setBounds(285, 174, 67, 20);
		jLabel7.setText("贸易方式");
		this.add(lbScmCoc, null);
		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);

		this.add(jLabel, null);
		this.add(lbEmsNo, null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		this.add(jLabel5, null);
		this.add(getCbbImpExpType(), null);
		this.add(getCbbTransportMode(), null);
		this.add(getTfEnterpriseBillNo(), null);
		this.add(getCbbCustoms(), null);
		this.add(getCbbTradeMode(), null);
		this.add(jLabel6, null);
		this.add(jLabel7, null);
		this.add(getCbbImpExpPort(), null);
		this.add(getBtnEnterpriseBillNo(), null);
		this.add(getCbbScmCoc(), null);
		this.add(jLabel4, null);
		this.add(getTfMemo1(), null);
		this.add(getCbbEmsNo(), null);
		this.add(jLabel8, null);
		this.add(getCbbProjectType(), null);
	}

	@Override
	public void setVisible(boolean isFalg) {
		if (isFalg == true) {
			initUIComponents();
		}
		super.setVisible(isFalg);
	}

	/**
	 * This method initializes cbbTradeMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(352, 174, 127, 20);
		}
		return cbbTradeMode;
	}

	/**
	 * This method initializes cbbTransportMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransportMode() {
		if (cbbTransportMode == null) {
			cbbTransportMode = new JComboBox();
			cbbTransportMode.setBounds(121, 174, 149, 20);
		}
		return cbbTransportMode;
	}

	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbMakeNewBill());
			buttonGroup.add(getRbIntoOldBill());
		}
		return buttonGroup;
	}

	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getRbTransferExport());
			buttonGroup1.add(getRbTransferImport());
		}
		return buttonGroup;
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

	/**
	 * This method initializes rbTransferImport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransferImport() {
		if (rbTransferImport == null) {
			rbTransferImport = new JRadioButton();
			rbTransferImport.setText("进口");
			rbTransferImport.setBounds(20, 23, 51, 26);

			rbTransferImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isImportGoods = true;
							initCbbComponents();
						}
					});
		}
		return rbTransferImport;
	}

	/**
	 * This method initializes rbTransferExport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransferExport() {
		if (rbTransferExport == null) {
			rbTransferExport = new JRadioButton();
			rbTransferExport.setText("出口");
			rbTransferExport.setBounds(85, 24, 51, 26);

			rbTransferExport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isImportGoods = false;
							initCbbComponents();
						}
					});
		}
		return rbTransferExport;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(331, 14, 158, 57);

			jPanel
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"进出口类型",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			jPanel.add(getRbTransferImport(), null);
			jPanel.add(getRbTransferExport(), null);
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
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"生成方式",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			jPanel1.setLayout(null);
			jPanel1.setBounds(12, 14, 314, 57);
			jPanel1.add(getRbMakeNewBill(), null);
			jPanel1.add(getRbIntoOldBill(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes rbMakeNewBill
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMakeNewBill() {
		if (rbMakeNewBill == null) {
			rbMakeNewBill = new JRadioButton();
			rbMakeNewBill.setBounds(4, 27, 130, 21);
			rbMakeNewBill.setText("生成新的报关清单");
			rbMakeNewBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState(true);
						}
					});
		}
		return rbMakeNewBill;
	}

	private void setState(boolean isNewApplyToCustomsBill) {
		this.tfEnterpriseBillNo.setEditable(!isNewApplyToCustomsBill);
		this.btnEnterpriseBillNo.setEnabled(!isNewApplyToCustomsBill);
		this.cbbEmsNo.setEditable(isNewApplyToCustomsBill);
		this.cbbProjectType.setEditable(isNewApplyToCustomsBill);
		this.tfMemo1.setEditable(isNewApplyToCustomsBill);
		this.cbbCustoms.setEnabled(isNewApplyToCustomsBill);
		this.cbbImpExpPort.setEnabled(isNewApplyToCustomsBill);
		this.cbbImpExpType.setEnabled(isNewApplyToCustomsBill);
		this.cbbTradeMode.setEnabled(isNewApplyToCustomsBill);
		this.cbbTransportMode.setEnabled(isNewApplyToCustomsBill);
		if (isNewApplyToCustomsBill == true) {
			this.applyToCustomsBillList = null;
		}
	}

	/**
	 * This method initializes rbIntoOldBill
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIntoOldBill() {
		if (rbIntoOldBill == null) {
			rbIntoOldBill = new JRadioButton();
			rbIntoOldBill.setBounds(143, 27, 156, 21);
			rbIntoOldBill.setText("追加到现有的报关清单");
			rbIntoOldBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState(false);
						}
					});
		}
		return rbIntoOldBill;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(121, 96, 148, 20);
		}
		return cbbImpExpType;
	}

	/**
	 * This method initializes tfEnterpriseBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEnterpriseBillNo() {
		if (tfEnterpriseBillNo == null) {
			tfEnterpriseBillNo = new JTextField();
			tfEnterpriseBillNo.setBounds(121, 121, 127, 20);
		}
		return tfEnterpriseBillNo;
	}

	/**
	 * This method initializes cbbCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(352, 121, 127, 20);
		}
		return cbbCustoms;
	}

	/**
	 * This method initializes cbbImpExpProt
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpPort() {
		if (cbbImpExpPort == null) {
			cbbImpExpPort = new JComboBox();
			cbbImpExpPort.setBounds(352, 148, 127, 20);
		}
		return cbbImpExpPort;
	}

	/**
	 * This method initializes btnEnterpriseBillNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEnterpriseBillNo() {
		if (btnEnterpriseBillNo == null) {
			btnEnterpriseBillNo = new JButton();
			btnEnterpriseBillNo.setBounds(247, 121, 23, 20);
			btnEnterpriseBillNo.setText("...");
			btnEnterpriseBillNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object obj = null;
							switch (getProjectType()) {
							case ProjectType.BCUS:
								if (rbTransferImport.isSelected() == true) {
									obj = FptQuery
											.getInstance()
											.getApplyToCustomsBillListByType(
													ImpExpType.TRANSFER_FACTORY_IMPORT);
								} else {
									obj = FptQuery
											.getInstance()
											.getApplyToCustomsBillListByType(
													ImpExpType.TRANSFER_FACTORY_EXPORT);
								}
								if (obj != null) {
									applyToCustomsBillList = (ApplyToCustomsBillList) obj;
									tfEnterpriseBillNo
											.setText(applyToCustomsBillList
													.getListNo());
								}
								break;
							case ProjectType.DZSC:
								if (rbTransferImport.isSelected() == true) {
									obj = FptQuery
											.getInstance()
											.getDzscCustomsBillListByType(
													ImpExpType.TRANSFER_FACTORY_IMPORT);
								} else {
									obj = FptQuery
											.getInstance()
											.getDzscCustomsBillListByType(
													ImpExpType.TRANSFER_FACTORY_EXPORT);
								}
								if (obj != null) {
									dzscCustomsBillList = (DzscCustomsBillList) obj;
									tfEnterpriseBillNo
											.setText(dzscCustomsBillList
													.getListNo());
								}
								break;
							}
						}
					});
		}
		return btnEnterpriseBillNo;
	}

	/**
	 * This method initializes cbbscmCocOrCompany
	 * 
	 * @return javax.swing.JComboBox
	 */

	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(121, 148, 149, 20);
		}
		return cbbScmCoc;
	}

	/**
	 * ======= return cbbCompany; }
	 * 
	 * /** >>>>>>> 1.2 This method initializes tfMemo1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo1() {
		if (tfMemo1 == null) {
			tfMemo1 = new JTextField();
			tfMemo1.setBounds(121, 199, 275, 22);
		}
		return tfMemo1;
	}

	/**
	 * 初始化窗体控件
	 */
	private void initUIComponents() {
		this.initCbbComponents();
		this.initRbComponents();
		this.initOtherCbbComponents();
	}

	/**
	 * 初始化ComboBox窗体控件
	 * 
	 */
	private void initCbbComponents() {
		List scmCoces = new ArrayList();
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (this.isImportGoods() == false) {
			scmCoces = super.getCustomer();
			this.cbbScmCoc
					.setModel(new DefaultComboBoxModel(scmCoces.toArray()));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
					"code", "name", 250);
			this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 80, 170));
			this.cbbScmCoc.setSelectedItem(null);
			this.lbScmCoc.setText("客户");
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
		} else {
			scmCoces = super.getManufacturer();
			this.cbbScmCoc
					.setModel(new DefaultComboBoxModel(scmCoces.toArray()));
			CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
					"code", "name", 250);
			this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance()
					.getRender("code", "name", 80, 170));
			this.cbbScmCoc.setSelectedItem(null);
			this.lbScmCoc.setText("供应商");			
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "转厂进口"));			
		}
		this.cbbProjectType.removeAllItems();
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "联网监管"));
		// this.cbbProjectType.addItem(new ItemProperty(String
		// .valueOf(ProjectType.BCS), "纸质手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.DZSC), "电子手册"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(-1);

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
	}

	private void initOtherCbbComponents() {
		// 初始化进口口岸
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbCustoms.setSelectedItem(null);
		// 初始化进出口海关
		this.cbbImpExpPort.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpPort);
		this.cbbImpExpPort.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpPort.setSelectedItem(null);
		// 初始化运输方式
		this.cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransportMode);
		this.cbbTransportMode.setSelectedIndex(-1);
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		this.cbbTradeMode.setSelectedIndex(-1);
	}

	/**
	 * 初始化RadioButton窗体控件
	 * 
	 */
	private void initRbComponents() {
		this.rbTransferExport.setSelected(!this.isImportGoods());
		this.rbTransferImport.setSelected(this.isImportGoods());
		this.rbMakeNewBill.setSelected(true);
		setState(true);
	}

	/**
	 * 验证数据是否为空
	 */
	public boolean validateDataIsNull() {
		boolean isNull = false;
		if (this.cbbScmCoc.getSelectedItem() == null
				|| this.cbbScmCoc.getSelectedItem() == null) {
			isNull = true;
		}
		return isNull;
	}

	/**
	 * 验证报关清单表头数据是否为空
	 */
	public boolean validateApplyToCustomsBillListIsNull() {
		boolean isNull = false;
		if (this.rbIntoOldBill.isSelected() == true) {
			switch(getProjectType()){
			case ProjectType.BCUS:
				if (this.applyToCustomsBillList == null) {
					isNull = true;
				}
				break;
			case ProjectType.DZSC:
				if (this.dzscCustomsBillList == null) {
					isNull = true;
				}
				break;
			}
			
		}
		return isNull;
	}

	/**
	 * 获得报关清单表头
	 */
	public ApplyToCustomsBillList getApplyToCustomsBillList() {
		if (this.cbbEmsNo.getSelectedItem() == null) {
			throw new RuntimeException(this.lbEmsNo.getText() + "不能为空");
		}
		if (this.rbMakeNewBill.isSelected() == true) {
			ApplyToCustomsBillList a = new ApplyToCustomsBillList();
			String emsNo = ((TempCustomsEmsInfo) this.cbbEmsNo
					.getSelectedItem()).getEmsNo();
			EmsHeadH2k emsHeadH2k = this.fptManageAction
					.findEmsHeadH2kInExecuting(new Request(CommonVars
							.getCurrUser(), true), emsNo);
			a.setCompany(CommonVars.getCurrUser().getCompany());
			a.setMemos(this.tfMemo1.getText());
			a.setCreatedDate(new Date());
			a.setCreatedUser(CommonVars.getCurrUser());
			// a.setListNo(getMaxBillListNo());
			a.setDeclareCIQ((Customs) this.cbbCustoms.getSelectedItem());
			a.setImpExpCIQ((Customs) this.cbbImpExpPort.getSelectedItem());
			a.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			a
					.setTransportMode((Transf) this.cbbTransportMode
							.getSelectedItem());
			a.setEmsHeadH2k(emsHeadH2k == null ? "" : emsHeadH2k.getEmsNo());
			a.setListState(Integer.valueOf(ApplyToCustomsBillList.DRAFT));
			Integer impExpType = Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode());
			a.setImpExpType(impExpType);
			a.setTradeName(emsHeadH2k == null ? "" : emsHeadH2k.getTradeName());
			a.setTradeCode(emsHeadH2k == null ? "" : emsHeadH2k.getTradeCode());
			if (this.isImportGoods == true) {
				a
						.setMaterielProductFlag(Integer
								.valueOf(MaterielType.MATERIEL));
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.IMPORT));
			} else {
				a.setMaterielProductFlag(Integer
						.valueOf(MaterielType.FINISHED_PRODUCT));
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.EXPORT));
			}
			return a;
		} else {
			return this.applyToCustomsBillList;
		}
	}

	/**
	 * 获得供应商或公司名称
	 */
	public ScmCoc getScmCoc() {

		return (ScmCoc) this.cbbScmCoc.getSelectedItem();

	}

	/**
	 * 是否是新的记录
	 */
	public boolean getIsNewRecord() {
		return this.rbMakeNewBill.isSelected();
	}

	/**
	 * @return Returns the emsHeadH2k.
	 */
	public String getEmsNo() {
		if (this.cbbEmsNo.getSelectedItem() == null) {
			return null;
		} else {
			return ((TempCustomsEmsInfo) this.cbbEmsNo.getSelectedItem())
					.getEmsNo();
		}
	}

	//
	// /**
	// * @param emsHeadH2k
	// * The emsHeadH2k to set.
	// */
	// public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
	// this.emsHeadH2k = emsHeadH2k;
	// }

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsNo() {
		if (cbbEmsNo == null) {
			cbbEmsNo = new JComboBox();
			cbbEmsNo.setBounds(new java.awt.Rectangle(352, 96, 127, 22));
			cbbEmsNo.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
				}
			});
		}
		return cbbEmsNo;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new java.awt.Rectangle(352, 72, 127, 22));
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
		List list = this.fptManageAction.findEmsHeadByProjectType(
				new Request(CommonVars.getCurrUser(), true), projectType);
		for (int i = 0; i < list.size(); i++) {
			this.cbbEmsNo.addItem(list.get(i));
		}
		this.cbbEmsNo.setSelectedIndex(-1);
		switch (projectType) {
		case ProjectType.BCS:
			lbEmsNo.setText("纸质手册号");
			break;
		case ProjectType.BCUS:
			lbEmsNo.setText("电子账册号");
			break;
		case ProjectType.DZSC:
			lbEmsNo.setText("电子手册号");
			break;
		}
	}

	public DzscCustomsBillList getDzscCustomsBillList() {
		if (this.cbbEmsNo.getSelectedItem() == null) {
			throw new RuntimeException(this.lbEmsNo.getText() + "不能为空");
		}
		if (this.rbMakeNewBill.isSelected() == true) {
			DzscCustomsBillList a = new DzscCustomsBillList();
			String emsNo = ((TempCustomsEmsInfo) this.cbbEmsNo
					.getSelectedItem()).getEmsNo();
			DzscEmsPorHead emsHeadH2k = this.fptManageAction
					.findDzscEmsPorHeadExcu(new Request(CommonVars
							.getCurrUser(), true), emsNo);
			a.setCompany(CommonVars.getCurrUser().getCompany());
			a.setMemos(this.tfMemo1.getText());
			a.setCreatedDate(new Date());
			a.setCreatedUser(CommonVars.getCurrUser());
			// a.setListNo(getMaxBillListNo());
			a.setDeclareCIQ((Customs) this.cbbCustoms.getSelectedItem());
			a.setImpExpCIQ((Customs) this.cbbImpExpPort.getSelectedItem());
			a.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			a
					.setTransportMode((Transf) this.cbbTransportMode
							.getSelectedItem());
			a.setEmsHeadH2k(emsHeadH2k == null ? "" : emsHeadH2k.getEmsNo());
			a.setListState(Integer.valueOf(ApplyToCustomsBillList.DRAFT));
			Integer impExpType = Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode());
			a.setImpExpType(impExpType);
			a.setTradeName(emsHeadH2k == null ? "" : emsHeadH2k.getTradeName());
			a.setTradeCode(emsHeadH2k == null ? "" : emsHeadH2k.getTradeCode());
			if (this.isImportGoods == true) {
				a
						.setMaterielProductFlag(Integer
								.valueOf(MaterielType.MATERIEL));
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.IMPORT));
			} else {
				a.setMaterielProductFlag(Integer
						.valueOf(MaterielType.FINISHED_PRODUCT));
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.EXPORT));
			}
			return a;
		} else {
			return dzscCustomsBillList;
		}

	}
}