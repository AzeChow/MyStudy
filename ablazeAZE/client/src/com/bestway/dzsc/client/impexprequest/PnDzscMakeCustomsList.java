package com.bestway.dzsc.client.impexprequest;

import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.client.custom.common.EncCommon;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscListType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JPanelBase;
import java.awt.Rectangle;

public class PnDzscMakeCustomsList extends JPanelBase {

	private JRadioButton rbTransferImport = null;

	private JRadioButton rbTransferExport = null;

	private ButtonGroup buttonGroup = null;

	private ButtonGroup buttonGroup1 = null;

	private JPanel jPanel = null;

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

	private DzscCustomsBillList applyToCustomsBillList = null;

	private DzscEmsPorHead dzscEmsPorHead = null;

	// private EncAction encAction = null;
	protected DzscAction dzscAction = null;

	private int type = -1;

	private JCheckBox cbMakeApplyToCustoms = null;

	private JCheckBox cbMakeSpecialCustomsDeclaration = null;

	private JComboBox cbbContract = null;

	private JLabel jLabel71 = null;

	private JComboBox cbbListType = null;

	/**
	 * This is the default constructor
	 */
	public PnDzscMakeCustomsList() {
		super();
		initialize();
		this.dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		List list = dzscAction.findDzscEmsPorHeadExcu(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbContract.removeAllItems();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				this.cbbContract.addItem((DzscEmsPorHead) list.get(i));
			}
			this.cbbContract.setRenderer(CustomBaseRender.getInstance()
					.getRender("", "emsNo", 0, 200));
		}
		if (this.cbbContract.getItemCount() > 0) {
			this.cbbContract.setSelectedIndex(0);
			dzscEmsPorHead = (DzscEmsPorHead) (list.get(0));
		} else {
			dzscEmsPorHead = null;
		}
		getButtonGroup();
		getButtonGroup1();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel71 = new JLabel();
		jLabel71.setBounds(new Rectangle(349, 161, 65, 21));
		jLabel71.setText("清单类型");
		javax.swing.JLabel jLabel7 = new JLabel();
		javax.swing.JLabel jLabel6 = new JLabel();
		javax.swing.JLabel jLabel5 = new JLabel();
		javax.swing.JLabel jLabel3 = new JLabel();
		javax.swing.JLabel jLabel2 = new JLabel();
		javax.swing.JLabel jLabel1 = new JLabel();
		javax.swing.JLabel jLabel = new JLabel();
		javax.swing.JLabel jLabel4 = new JLabel();

		this.setLayout(null);
		this.setSize(630, 248);

		jLabel4.setBounds(82, 188, 101, 20);

		jLabel4.setText("备注");

		jLabel.setBounds(83, 85, 101, 20);
		jLabel.setText("进出口类型");
		jLabel.setForeground(java.awt.Color.blue);
		jLabel1.setBounds(349, 85, 67, 20);
		jLabel1.setText("手册编号");
		jLabel2.setBounds(83, 110, 101, 20);
		jLabel2.setText("企业清单编号");
		jLabel3.setBounds(349, 135, 67, 20);
		jLabel3.setText("进出口岸");
		jLabel5.setBounds(349, 110, 67, 20);
		jLabel5.setText("申报地海关");
		jLabel6.setBounds(82, 135, 101, 20);
		jLabel6.setText("运输方式");
		jLabel7.setBounds(82, 163, 101, 20);
		jLabel7.setText("贸易方式");
		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);

		this.add(jLabel, null);
		this.add(jLabel1, null);
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
		this.add(jLabel4, null);
		this.add(getTfMemo1(), null);
		this.add(getCbMakeApplyToCustoms(), null);

		this.add(getCbMakeSpecialCustomsDeclaration(), null);
		this.add(getCbbContract(), null);
		this.add(jLabel71, null);
		this.add(getCbbListType(), null);
	}

	public void setVisible(boolean isFalg) {
		if (isFalg == true) {
			initUIComponents();
		}
		super.setVisible(isFalg);
	}

	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * This method initializes cbbTradeMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(185, 163, 149, 20);
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
			cbbTransportMode.setBounds(185, 135, 149, 20);
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
			jPanel.setBounds(394, 14, 158, 57);

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
			jPanel1.setBounds(75, 14, 314, 57);
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
		// this.tfEmsNo.setEditable(isNewApplyToCustomsBill);
		this.tfMemo1.setEditable(isNewApplyToCustomsBill);
		this.cbbCustoms.setEnabled(isNewApplyToCustomsBill);
		this.cbbImpExpPort.setEnabled(isNewApplyToCustomsBill);
		// this.cbbImpExpType.setEnabled(isNewApplyToCustomsBill);
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
			cbbImpExpType.setBounds(185, 85, 148, 20);
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
			tfEnterpriseBillNo.setBounds(185, 110, 127, 20);
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
			cbbCustoms.setBounds(416, 110, 127, 20);
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
			cbbImpExpPort.setBounds(416, 135, 127, 20);
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
			btnEnterpriseBillNo.setBounds(311, 110, 23, 20);
			btnEnterpriseBillNo.setText("...");
			btnEnterpriseBillNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object obj = DzscImpExpRequestQuery
									.getInstance()
									.getDzscCustomsBillListByType(
											Integer
													.parseInt(((ItemProperty) (cbbImpExpType
															.getSelectedItem()))
															.getCode()));
							if (obj != null) {
								applyToCustomsBillList = (DzscCustomsBillList) obj;
								tfEnterpriseBillNo
										.setText(applyToCustomsBillList
												.getListNo());
							} else {
								tfEnterpriseBillNo.setText("");
							}

						}
					});
		}
		return btnEnterpriseBillNo;
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
			tfMemo1.setBounds(185, 188, 238, 22);
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
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (this.isImportGoods() == true) {

			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "料件进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "一般贸易进口"));

		} else {

			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
					"边角料内销"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
		}
		this.cbbImpExpType.setSelectedIndex(ItemProperty.getIndexByCode(String
				.valueOf(type), cbbImpExpType));
		// this.tfEmsNo.setText(dzscEmsPorHead == null ? "" :
		// dzscEmsPorHead.getEmsNo());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());

		// 清单类型
		this.cbbListType.removeAllItems();
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.NORMAL_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.NORMAL_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.PRE_TRANS_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.PRE_TRANS_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.SECOND_TRANS_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.SECOND_TRANS_DCR)));
		this.cbbListType.addItem(new ItemProperty(Integer.valueOf(
				DzscListType.APANAGE_DCR).toString(), DzscListType
				.getListTypeDesc(DzscListType.APANAGE_DCR)));
		this.cbbListType
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbListType);
		this.cbbListType.setSelectedIndex(-1);
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
	 * 验证报关清单表头数据类型是否为空
	 */
	public boolean validateImpExpTypeIsNull() {
		boolean isNull = false;
		if (this.cbbImpExpType.getSelectedItem() == null) {
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
			if (this.applyToCustomsBillList == null) {
				isNull = true;
			}
		}
		return isNull;
	}

	/**
	 * 验证报关清单类型是否为空
	 */
	public boolean validateListTypeIsNull() {
		boolean isNull = false;
		if (this.cbbListType.getSelectedItem() == null) {
			isNull = true;
		}
		return isNull;
	}

	public MakeCusomsDeclarationParam getMakeCusomsDeclarationParam() {
		if (this.rbMakeNewBill.isSelected() == true) {
			MakeCusomsDeclarationParam a = new MakeCusomsDeclarationParam();
			a.setTransf((Transf) this.cbbTransportMode.getSelectedItem());
			a.setTrade((Trade) this.cbbTradeMode.getSelectedItem());
			a.setCustoms((Customs) this.cbbImpExpPort.getSelectedItem());
			a
					.setDeclarationCustoms((Customs) this.cbbCustoms
							.getSelectedItem());
			return a;
		}
		return null;
	}

	/**
	 * 获得报关清单表头
	 */
	public DzscCustomsBillList getApplyToCustomsBillList() {
		if (this.rbMakeNewBill.isSelected() == true) {
			DzscCustomsBillList a = new DzscCustomsBillList();
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
			a.setEmsHeadH2k(dzscEmsPorHead == null ? "" : this.dzscEmsPorHead
					.getEmsNo());
			a.setListState(Integer.valueOf(DzscState.ORIGINAL));
			Integer impExpType = Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode());
			a.setImpExpType(impExpType);
			a.setMaterielProductFlag(EncCommon
					.getMaterielTypeByBillType(impExpType));
			a.setTradeName(this.dzscEmsPorHead == null ? ""
					: this.dzscEmsPorHead.getTradeName());
			a.setTradeCode(this.dzscEmsPorHead == null ? ""
					: this.dzscEmsPorHead.getTradeCode());
			if (this.isImportGoods == true) {
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.IMPORT));
			} else {
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.EXPORT));
			}
			Integer listType = Integer.valueOf(((ItemProperty) this.cbbListType
					.getSelectedItem()).getCode());
			a.setListType(listType);
			return a;
		} else {
			return this.applyToCustomsBillList;
		}
	}

	/**
	 * 获得供应商或公司名称
	 */
	public int getImpExpType() {
		if (this.cbbImpExpType.getSelectedItem() == null) {
			return -1;
		}
		return Integer.parseInt(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());
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
	public DzscEmsPorHead getDzscEmsPorHead() {
		return dzscEmsPorHead;
	}

	/**
	 * @param emsHeadH2k
	 *            The emsHeadH2k to set.
	 */
	public void setDzscEmsPorHead(DzscEmsPorHead emsHeadH2k) {
		this.dzscEmsPorHead = emsHeadH2k;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMakeApplyToCustoms() {
		if (cbMakeApplyToCustoms == null) {
			cbMakeApplyToCustoms = new JCheckBox();
			cbMakeApplyToCustoms.setBounds(79, 215, 125, 22);
			cbMakeApplyToCustoms.setText("是否生成报关单");
			cbMakeApplyToCustoms.setForeground(new java.awt.Color(227, 147, 0));
			cbMakeApplyToCustoms.setVisible(false);
			cbMakeApplyToCustoms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// cbMakeApplyToCustoms.setSelected(!cbMakeApplyToCustoms.isSelected());
							cbMakeSpecialCustomsDeclaration.setSelected(false);
						}
					});
		}
		return cbMakeApplyToCustoms;
	}

	/**
	 * @return Returns the isMakeApplyToCustoms.
	 */
	public boolean isMakeApplyToCustoms() {
		return cbMakeApplyToCustoms.isSelected();
	}

	public boolean isMakeSpecialApplyToCustoms() {
		return cbMakeSpecialCustomsDeclaration.isSelected();
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMakeSpecialCustomsDeclaration() {
		if (cbMakeSpecialCustomsDeclaration == null) {
			cbMakeSpecialCustomsDeclaration = new JCheckBox();
			cbMakeSpecialCustomsDeclaration.setBounds(236, 215, 157, 22);
			cbMakeSpecialCustomsDeclaration.setText("是否生成特殊报关单");
			cbMakeSpecialCustomsDeclaration.setForeground(new java.awt.Color(
					227, 147, 0));
			cbMakeSpecialCustomsDeclaration.setVisible(false);
			cbMakeSpecialCustomsDeclaration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// cbMakeSpecialCustomsDeclaration.setSelected(!cbMakeSpecialCustomsDeclaration.isSelected());
							cbMakeApplyToCustoms.setSelected(false);
						}
					});
		}
		return cbMakeSpecialCustomsDeclaration;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setBounds(new java.awt.Rectangle(416, 84, 127, 20));
		}
		return cbbContract;
	}

	/**
	 * This method initializes cbbTradeMode1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbListType() {
		if (cbbListType == null) {
			cbbListType = new JComboBox();
			cbbListType.setBounds(new Rectangle(416, 161, 127, 22));
		}
		return cbbListType;
	}
} // @jve:decl-index=0:visual-constraint="-404,52"
