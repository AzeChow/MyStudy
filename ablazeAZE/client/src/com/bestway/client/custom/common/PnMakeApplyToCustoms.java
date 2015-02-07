package com.bestway.client.custom.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
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
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.FptQuery;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JPanelBase;

public class PnMakeApplyToCustoms extends JPanelBase {

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private JTextField tfMemo1 = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbMakeNewBill = null;

	private JRadioButton rbIntoOldBill = null;

	private JTextField tfEmsNo = null;

	private JComboBox cbbImpExpType = null;

	private JTextField tfEnterpriseBillNo = null;

	private JComboBox cbbCustoms = null;

	private JComboBox cbbImpExpPort = null;

	private JComboBox cbbTradeMode = null;

	private JComboBox cbbTransportMode = null;

	private JButton btnEnterpriseBillNo = null;

	private ApplyToCustomsBillList applyToCustomsBillList = null; // @jve:decl-index=0:

	private EmsHeadH2k emsHeadH2k = null;

	protected ManualDeclareAction manualDeclearAction = null;

	private JCheckBox cbMakeApplyToCustoms = null;

	private SystemAction systemAction = null;

	private JCheckBox jCheckBox = null;

	private JCheckBox cbUniteBillList = null;

	private JComboBox cbbUnitePriceType = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel61 = null;

	private JLabel jLabel71 = null;

	private JLabel jLabel41 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel32 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel311 = null;

	private MakeCusomsDeclarationParam para = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public PnMakeApplyToCustoms() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		List list = manualDeclearAction.findEmsHeadH2kInExecuting(new Request(
				CommonVars.getCurrUser(), true));
		if (list == null || list.size() <= 0) {
			emsHeadH2k = null;
		} else {
			emsHeadH2k = (EmsHeadH2k) (list.get(0));
		}
		getButtonGroup();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.insets = new Insets(6, 52, 36, 54);
		gridBagConstraints3.gridy = 2;
		gridBagConstraints3.ipadx = 547;
		gridBagConstraints3.ipady = 64;
		gridBagConstraints3.gridx = 0;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.insets = new Insets(4, 52, 5, 54);
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.ipadx = 547;
		gridBagConstraints2.ipady = 150;
		gridBagConstraints2.gridx = 0;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.insets = new Insets(14, 52, 4, 54);
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.ipadx = 547;
		gridBagConstraints1.ipady = 66;
		gridBagConstraints1.gridx = 0;
		this.setLayout(new GridBagLayout());
		this.setSize(654, 352);

		this.add(getJPanel1(), gridBagConstraints1);
		this.add(getJPanel2(), gridBagConstraints2);
		this.add(getJPanel3(), gridBagConstraints3);
	}

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
			cbbTradeMode.setBounds(new Rectangle(124, 115, 149, 20));
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
			cbbTransportMode.setBounds(new Rectangle(124, 85, 149, 20));
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
									"清单生成选项",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			jPanel1.setLayout(null);
			jPanel1.add(getRbMakeNewBill(), null);
			jPanel1.add(getRbIntoOldBill(), null);
			jPanel1.add(getJCheckBox(), null);
			jPanel1.add(getCbUniteBillList(), null);
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
			rbMakeNewBill.setBounds(4, 27, 70, 21);
			rbMakeNewBill.setText("新清单");
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
		this.tfEmsNo.setEditable(isNewApplyToCustomsBill);
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
			rbIntoOldBill.setBounds(92, 27, 124, 21);
			rbIntoOldBill.setText("追加到现有清单");
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
	 * This method initializes tfEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setEditable(false);
			tfEmsNo.setBounds(new Rectangle(398, 25, 127, 20));
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes cbbImpExpType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setUI(new CustomBaseComboBoxUI(300));
			cbbImpExpType.setBounds(new Rectangle(124, 25, 148, 20));
			cbbImpExpType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initUI();
					}
				}
			});
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
			tfEnterpriseBillNo.setBounds(new Rectangle(124, 55, 127, 20));
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
			cbbCustoms.setBounds(new Rectangle(398, 55, 127, 20));
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
			cbbImpExpPort.setBounds(new Rectangle(398, 85, 127, 20));
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
			btnEnterpriseBillNo.setText("...");
			btnEnterpriseBillNo.setBounds(new Rectangle(249, 55, 23, 20));
			btnEnterpriseBillNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Object obj = FptQuery
									.getInstance()
									.getApplyToCustomsBillListByType(
											Integer
													.parseInt(((ItemProperty) (cbbImpExpType
															.getSelectedItem()))
															.getCode()));
							if (obj != null) {
								applyToCustomsBillList = (ApplyToCustomsBillList) obj;
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
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo1() {
		if (tfMemo1 == null) {
			tfMemo1 = new JTextField();
			tfMemo1.setBounds(new Rectangle(398, 115, 127, 20));
		}
		return tfMemo1;
	}

	/**
	 * 初始化窗体控件
	 */
	private void initUIComponents() {
		this.initCbbComponents();
		this.initOtherCbbComponents();
		if (para.isFromCustomsDeclaretion()) {
			initFromCustoms();
		} else if (!para.isOtherBilllist()) {
			initInOldBillList();
		}
		
		initUI();
	}
	
	private void initUI(){
		CustomsDeclarationSet other = null;
		
		List<CustomsDeclarationSet> list = systemAction.findCustomsDeclarationSet(new Request(CommonVars.getCurrUser()));
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationSet customsSet = list.get(i);
			ItemProperty item = (ItemProperty)cbbImpExpType.getSelectedItem();
			if(item.getCode()!=null&&customsSet.getImpType()!=null){
				if(String.valueOf(customsSet.getImpType()).equals(item.getCode())){
					other = customsSet;
				}
			}
		}
		
		if(other!=null&&other.getTransferMode()!=null){
			this.cbbTransportMode.setSelectedItem(other.getTransferMode());
		}else{
			this.cbbTransportMode.setSelectedIndex(-1);
		}
		
		if(other!=null&&other.getCustoms()!=null){
			this.cbbImpExpPort.setSelectedItem(other.getCustoms());
		}else{
			this.cbbImpExpPort.setSelectedIndex(-1);
		}
	}

	private void initFromCustoms() {
		Component[] comps = jPanel2.getComponents();
		for (int k = 0; k < comps.length; k++) {
			Component comp = comps[k];
			comp.setEnabled(false);
		}
		rbMakeNewBill.setSelected(true);
		rbIntoOldBill.setSelected(false);
		rbIntoOldBill.setEnabled(false);
		rbMakeNewBill.setEnabled(false);
		cbMakeApplyToCustoms.setSelected(true);
		cbMakeApplyToCustoms.setEnabled(false);
	}

	private void initInOldBillList() {
		Component[] comps = jPanel2.getComponents();
		for (int k = 0; k < comps.length; k++) {
			Component comp = comps[k];
			comp.setEnabled(false);
		}
		btnEnterpriseBillNo.setEnabled(true);
		rbMakeNewBill.setSelected(false);
		rbIntoOldBill.setSelected(true);
		rbIntoOldBill.setEnabled(true);
		rbMakeNewBill.setEnabled(true);
	}

	/**
	 * 初始化ComboBox窗体控件
	 * 
	 */
	private void initCbbComponents() {
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		if (para.isImportFlag()) {//
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "直接进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "转厂进口"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "一般贸易进口"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.IMPORT_EXG_BACK).toString(), "进料成品退换"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.IMPORT_REPAIR_MATERIAL).toString(), "修理物品"));
		} else {
			this.cbbImpExpType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "直接出口"));
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

			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.EXPORT_MATERIAL_REBACK).toString(), "修理物品复出"));
			this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
					ImpExpType.EXPORT_EXG_REBACK).toString(), "进料成品退换复出"));
		}
		this.cbbImpExpType.setSelectedIndex(ItemProperty.getIndexByCode(String
				.valueOf((Integer) para.getImpExpType() == null ? -1 : para
						.getImpExpType()), cbbImpExpType));
		this.tfEmsNo.setText(emsHeadH2k == null ? "" : emsHeadH2k.getEmsNo());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
	}

	private void initOtherCbbComponents() {
		cbMakeApplyToCustoms.setSelected(para.isCustomsDeclaration());
		// 后加来自系统参数设置
		// CompanyOther other = CommonVars.getOther();
		CustomsDeclarationSet other = null;
		if (para.isImportFlag()) {
			other = systemAction.findCustomsSet(new Request(CommonVars
					.getCurrUser()), ImpExpType.DIRECT_IMPORT);
			if (other != null) {
				cbbCustoms.setSelectedItem((Customs) other
						.getDeclarationCustoms());
				cbbTransportMode.setSelectedItem((Transf) other
						.getTransferMode());
				cbbImpExpPort.setSelectedItem((Customs) other.getCustoms());
				cbbTradeMode.setSelectedItem((Trade) other.getTradeMode());
			}
		} else {
			other = systemAction.findCustomsSet(new Request(CommonVars
					.getCurrUser()), ImpExpType.DIRECT_EXPORT);
			if (other != null) {
				cbbCustoms.setSelectedItem((Customs) other
						.getDeclarationCustoms());
				cbbTransportMode.setSelectedItem((Transf) other
						.getTransferMode());
				cbbImpExpPort.setSelectedItem((Customs) other.getCustoms());
				cbbTradeMode.setSelectedItem((Trade) other.getTradeMode());
			}
		}

		this.rbMakeNewBill.setSelected(true);
		setState(true);
		// 初始化进口口岸
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbCustoms.setSelectedItem(para.getDeclarationCustoms());
		// 初始化进出口海关
		this.cbbImpExpPort.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpPort);
		this.cbbImpExpPort.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbImpExpPort.setSelectedItem(para.getCustoms());
		// 初始化运输方式
		this.cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransportMode);
		this.cbbTransportMode.setSelectedItem(para.getTransac());
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		this.cbbTradeMode.setSelectedItem(para.getTrade());
		this.rbIntoOldBill.setSelected(!para.isOtherBilllist());
		this.rbMakeNewBill.setSelected(para.isOtherBilllist());
		this.cbbUnitePriceType.setEnabled(para.isCustomsDeclaration());
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
		if (this.rbIntoOldBill.isSelected()) {
			if (this.applyToCustomsBillList == null) {
				isNull = true;
			}
		}
		return isNull;
	}

	public boolean isImg() {
		if (this.cbbImpExpType.getSelectedItem() == null) {
			return true;
		}
		Integer impExpType = Integer.valueOf(((ItemProperty) this.cbbImpExpType
				.getSelectedItem()).getCode());
		if (impExpType.equals(ImpExpType.DIRECT_IMPORT)) {
			return true;
		} else if (impExpType.equals(ImpExpType.DIRECT_IMPORT)) {
			return true;
		} else if (impExpType.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)) {
			return true;
		} else if (impExpType.equals(ImpExpType.GENERAL_TRADE_IMPORT)) {
			return true;
		} else if (impExpType.equals(ImpExpType.BACK_MATERIEL_EXPORT)) {
			return true;
		} else if (impExpType.equals(ImpExpType.REMIAN_MATERIAL_BACK_PORT)) {
			return true;
		} else if (impExpType.equals(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES)) {
			return true;
		} else if (impExpType.equals(ImpExpType.MATERIAL_DOMESTIC_SALES)) {
			return true;
		} else if (impExpType.equals(ImpExpType.MATERIAL_EXCHANGE)) {
			return true;
		} else if (impExpType.equals(ImpExpType.MATERIAL_REOUT)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得报关清单表头
	 */
	public ApplyToCustomsBillList getApplyToCustomsBillList() {
		if (this.rbMakeNewBill.isSelected()) {
			ApplyToCustomsBillList a = new ApplyToCustomsBillList();
			a.setCompany(CommonVars.getCurrUser().getCompany());
			a.setMemos(this.tfMemo1.getText());
			a.setCreatedDate(new Date());
			a.setCreatedUser(CommonVars.getCurrUser());
			a.setDeclareCIQ((Customs) this.cbbCustoms.getSelectedItem());
			a.setImpExpCIQ((Customs) this.cbbImpExpPort.getSelectedItem());
			
			a.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			a
					.setTransportMode((Transf) this.cbbTransportMode
							.getSelectedItem());
			a.setEmsHeadH2k(emsHeadH2k == null ? "" : this.emsHeadH2k
					.getEmsNo());
			a.setListState(Integer.valueOf(ApplyToCustomsBillList.DRAFT));
			Integer impExpType = Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode());
			a.setImpExpType(impExpType);
			a.setMaterielProductFlag(EncCommon
					.getMaterielTypeByBillType(impExpType));
			a.setTradeName(this.emsHeadH2k == null ? "" : this.emsHeadH2k
					.getTradeName());
			a.setTradeCode(this.emsHeadH2k == null ? "" : this.emsHeadH2k
					.getTradeCode());
			a.setImpExpFlag(EncCommon.isImport(impExpType) ? 0 : 1);
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
	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * @param emsHeadH2k
	 *            The emsHeadH2k to set.
	 */
	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMakeApplyToCustoms() {
		if (cbMakeApplyToCustoms == null) {
			cbMakeApplyToCustoms = new JCheckBox();
			cbMakeApplyToCustoms.setText("生成报关单");
			cbMakeApplyToCustoms.setBounds(new Rectangle(15, 21, 96, 22));
			cbMakeApplyToCustoms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getCbbUnitePriceType().setEnabled(
									!getCbbUnitePriceType().isEnabled());
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

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("使用单位折算");
			jCheckBox.setBounds(new Rectangle(270, 27, 116, 21));
			jCheckBox.setSelected(true);

		}
		return jCheckBox;
	}

	/**
	 * This method initializes cbUniteBillList
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUniteBillList() {
		if (cbUniteBillList == null) {
			cbUniteBillList = new JCheckBox();
			cbUniteBillList.setText("清单商品合并");
			cbUniteBillList.setBounds(new Rectangle(388, 27, 126, 21));
			cbUniteBillList.setSelected(true);
		}
		return cbUniteBillList;
	}

	/**
	 * This method initializes cbbUnitePriceType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnitePriceType() {
		if (cbbUnitePriceType == null) {
			cbbUnitePriceType = new JComboBox();
			cbbUnitePriceType.setBounds(new Rectangle(211, 21, 159, 22));
			cbbUnitePriceType.addItem("平均单价");
			cbbUnitePriceType.addItem("对应帐册申报单价");
			cbbUnitePriceType.addItem("对应帐册工厂单价");
			cbbUnitePriceType.setSelectedIndex(0);
		}
		return cbbUnitePriceType;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(326, 85, 68, 19));
			jLabel32.setText("\u8fdb\u51fa\u53e3\u5cb8");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(326, 55, 68, 19));
			jLabel51.setText("申报海关");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(326, 25, 68, 19));
			jLabel11.setText("\u5e10\u518c\u7f16\u53f7");
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(326, 115, 68, 19));
			jLabel41.setText("\u5907\u6ce8");
			jLabel71 = new JLabel();
			jLabel71.setBounds(new Rectangle(20, 115, 83, 20));
			jLabel71.setText("\u8d38\u6613\u65b9\u5f0f");
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(20, 85, 83, 20));
			jLabel61.setText("\u8fd0\u8f93\u65b9\u5f0f");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(20, 55, 83, 20));
			jLabel21.setText("\u4f01\u4e1a\u6e05\u5355\u7f16\u53f7");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(20, 25, 83, 20));
			jLabel8.setText("\u6e05\u5355\u7c7b\u578b");
			jLabel8.setForeground(Color.blue);
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getTfMemo1(), null);
			jPanel2.add(getCbbTradeMode(), null);
			jPanel2.add(getCbbTransportMode(), null);
			jPanel2.add(getTfEnterpriseBillNo(), null);
			jPanel2.add(getCbbImpExpType(), null);
			jPanel2.add(getTfEmsNo(), null);
			jPanel2.add(getCbbCustoms(), null);
			jPanel2.add(getCbbImpExpPort(), null);
			jPanel2.add(getBtnEnterpriseBillNo(), null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(jLabel21, null);
			jPanel2.add(jLabel61, null);
			jPanel2.add(jLabel71, null);
			jPanel2.add(jLabel41, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel51, null);
			jPanel2.add(jLabel32, null);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"清单表头设置",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel311 = new JLabel();
			jLabel311.setBounds(new Rectangle(115, 21, 94, 22));
			jLabel311.setText("\u62a5\u5173\u5355\u5355\u4ef7\u65b9\u5f0f");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(getCbMakeApplyToCustoms(), null);
			jPanel3.add(jLabel311, null);
			jPanel3.add(getCbbUnitePriceType(), null);
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"生成报关单选项",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
		}
		return jPanel3;
	}

	public MakeCusomsDeclarationParam getPara() {
		if (para == null) {
			para = new MakeCusomsDeclarationParam();
		}
		if (this.rbMakeNewBill.isSelected()) {
			para.setTransf((Transf) this.cbbTransportMode.getSelectedItem());// 运输方式
			para.setTrade((Trade) this.cbbTradeMode.getSelectedItem());// 贸易方式
			para.setCustoms((Customs) this.cbbImpExpPort.getSelectedItem());// 进出口口岸
			para.setDeclarationCustoms((Customs) this.cbbCustoms
					.getSelectedItem());// 申报海关
			para.setMemo(tfMemo1.getText());// 备注
			para.setImpExpType(Integer.parseInt(((ItemProperty) (cbbImpExpType
					.getSelectedItem())).getCode()));// 进出口类型
			para.setMaterielType(Integer
					.parseInt(isImg() ? MaterielType.MATERIEL
							: MaterielType.FINISHED_PRODUCT));
		}
		para.setApplyToCustomsBillList(getApplyToCustomsBillList());
		para.setEmsHeadH2k(emsHeadH2k);
		para.setUniteBillLists(cbUniteBillList.isSelected());
		para.setUseConvert(jCheckBox.isSelected());
		para.setCustomsDeclaration(cbMakeApplyToCustoms.isSelected());
		para.setPriceType(cbbUnitePriceType.getSelectedItem().toString());
		para.setOtherBilllist(rbMakeNewBill.isSelected());

		return para;
	}

	public void setPara(MakeCusomsDeclarationParam para) {

		this.para = para;
	}

} // @jve:decl-index=0:visual-constraint="-371,58"
