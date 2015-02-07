package com.bestway.bcs.client.contractexe;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JPanelBase;

import java.awt.Rectangle;

import javax.swing.JCheckBox;

import java.awt.Dimension;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

public class PnMakeBcsCustomsDeclaration extends JPanelBase {

	private JRadioButton rbTransferImport = null;
	private JRadioButton rbTransferExport = null;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:
	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:
//	private ButtonGroup buttonGroup2 = null;  //  @jve:decl-index=0:
	private ButtonGroup groupFrom = null;  //  @jve:decl-index=0:
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
	private ContractAction contractAction = null;
	private boolean isImportGoods = true;
	private int type = -1;
	private BcsCustomsDeclaration bcsCustomsDeclaration = null; // @jve:decl-index=0:
	private ContractExeAction contractExeAction = null;
	private boolean isNewBill = true;
	private boolean isfromCustoms = false;
	private JLabel jLabel8 = null;
	private JTextField tfConveyance = null;
	private JLabel jLabel9 = null;
	private JComboBox cbbScmCoc = null;
	private MaterialManageAction materialManageAction = null;
	private boolean isTransOut = false;
	private ImpExpRequestBill headImpExpRequestBill=null;  //  @jve:decl-index=0:
	private JPanel pnPrice = null;
	private JRadioButton rbContract = null;
	private JRadioButton rbApply = null;
	private JPanel jPanel2 = null;
	private SystemAction systemAction = null;
	/**
	 * This is the default constructor
	 */
	public PnMakeBcsCustomsDeclaration(ImpExpRequestBill headImpExpRequestBill) {
		super();
		this.headImpExpRequestBill=headImpExpRequestBill;
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		contractExeAction = (ContractExeAction) CommonVars
				.getApplicationContext().getBean("contractExeAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext().getBean("systemAction");
		getButtonGroup();
		getButtonGroup1();
//		getButtonGroup2();
		getGroupFrom();
	
	}
//根据申请单带出数据
	 void initData() {
//		 if(headImpExpRequestBill!=null){
//		this.tfConveyance.setText(headImpExpRequestBill.getConveyance()==null?"":headImpExpRequestBill.getConveyance());
//		this.cbbTransportMode.setSelectedItem(headImpExpRequestBill.getTransfMode());
//		
//		this.cbbImpExpPort.setSelectedItem(headImpExpRequestBill.getIePort());
//		 }
		 
		 if(cbbImpExpType.getSelectedItem()!=null){
			 int impExpType =  Integer.parseInt(((ItemProperty) (cbbImpExpType.getSelectedItem())).getCode());
			 CustomsDeclarationSet parameter = systemAction.findCustomsSet(new Request(CommonVars.getCurrUser()), impExpType);
			 if(parameter!=null){
				 this.cbbTransportMode.setSelectedItem(parameter.getTransferMode());
				 this.cbbCustoms.setSelectedItem(parameter.getDeclarationCustoms());
				 this.cbbTradeMode.setSelectedItem(parameter.getTradeMode());
				 this.cbbImpExpPort.setSelectedItem(parameter.getCustoms());
				 this.tfConveyance.setText(parameter.getConveyance());
			 }
			 if(headImpExpRequestBill!=null){
				 this.cbbScmCoc.setSelectedItem(headImpExpRequestBill.getScmCoc());
			 }
		 }

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel9 = new JLabel();
		jLabel9.setBounds(new Rectangle(335, 208, 68, 23));
		jLabel9.setText("客户/供应商");
		jLabel8 = new JLabel();
		jLabel8.setBounds(new Rectangle(33, 213, 80, 23));
		jLabel8.setText("运输工具");
		javax.swing.JLabel jLabel7 = new JLabel();
		javax.swing.JLabel jLabel6 = new JLabel();
		javax.swing.JLabel jLabel5 = new JLabel();
		javax.swing.JLabel jLabel3 = new JLabel();
		javax.swing.JLabel jLabel2 = new JLabel();
		javax.swing.JLabel jLabel = new JLabel();
		javax.swing.JLabel jLabel4 = new JLabel();

		this.setLayout(null);
		this.setSize(640, 296);

		jLabel4.setBounds(33, 247, 80, 23);

		jLabel4.setText("备注");

		jLabel.setBounds(33, 117, 80, 23);
		jLabel.setText("报关单类型");
		jLabel.setForeground(java.awt.Color.blue);
		jLabel2.setBounds(332, 118, 71, 23);
		jLabel2.setText("报关单编号");
		jLabel3.setBounds(334, 174, 68, 23);
		jLabel3.setText("进出口岸");
		jLabel5.setBounds(333, 146, 68, 23);
		jLabel5.setText("申报地海关");
		jLabel6.setBounds(33, 148, 80, 23);
		jLabel6.setText("运输方式");
		jLabel7.setBounds(33, 180, 80, 23);
		jLabel7.setText("贸易方式");
		this.add(jLabel, null);
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
		this.add(jLabel8, null);
		this.add(getTfMemo2(), null);
		this.add(jLabel9, null);
		this.add(getCbbScmCoc(), null);
		this.add(getJPanel2(), null);
	}

	public void setVisible(boolean isFalg) {
		if (isFalg == true) {
			initUIComponents();
			showData(bcsCustomsDeclaration);
			setState();
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
			cbbTradeMode.setBounds(118, 180, 203, 25);
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
			cbbTransportMode.setBounds(118, 150, 203, 25);
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

//	private ButtonGroup getButtonGroup2() {
//		if (buttonGroup2 == null) {
//			buttonGroup2 = new ButtonGroup();
//			buttonGroup2.add(getRbSelectContract());
//			buttonGroup2.add(getRbAutoSelectContract());
//		}
//		return buttonGroup2;
//	}

	public ButtonGroup getGroupFrom() {
		if (groupFrom == null) {
			groupFrom = new ButtonGroup();
			groupFrom.add(getRbApply());
			groupFrom.add(getRbContract());
			rbApply.setSelected(true);
		}
		return groupFrom;
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
			rbTransferImport.setBounds(20, 29, 51, 26);

			rbTransferImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isImportGoods = true;
							initCbbComponents();
							initOtherCbbComponents();

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
			rbTransferExport.setBounds(100, 32, 51, 22);

			rbTransferExport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isImportGoods = false;
							initCbbComponents();
							initOtherCbbComponents();
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
			jPanel
					.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "\u8fdb\u51fa\u53e3\u7c7b\u578b", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
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
					.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "\u751f\u6210\u65b9\u5f0f", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel1.setLayout(null);
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
			rbMakeNewBill.setBounds(34, 26, 119, 21);
			rbMakeNewBill.setText("生成新的报关单");
			rbMakeNewBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isNewBill = true;
							setState();
						}
					});
		}
		return rbMakeNewBill;
	}

	// private void setState(boolean isNewApplyToCustomsBill) {
	// this.tfEnterpriseBillNo.setEditable(!isNewApplyToCustomsBill);
	// this.btnEnterpriseBillNo.setEnabled(!isNewApplyToCustomsBill);
	// this.cbbEmsNo.setEnabled(isNewApplyToCustomsBill);
	// this.tfMemo1.setEditable(isNewApplyToCustomsBill);
	// this.cbbCustoms.setEnabled(isNewApplyToCustomsBill);
	// this.cbbImpExpPort.setEnabled(isNewApplyToCustomsBill);
	// // this.cbbImpExpType.setEnabled(isNewApplyToCustomsBill);
	// this.cbbTradeMode.setEnabled(isNewApplyToCustomsBill);
	// this.cbbTransportMode.setEnabled(isNewApplyToCustomsBill);
	//        
	// }

	private void setState() {
		// this.tfEnterpriseBillNo.setEditable(!isNewBill);
		this.btnEnterpriseBillNo.setEnabled(!isNewBill);
		if (isNewBill) {
			this.tfEnterpriseBillNo.setText("");
		}
//		this.cbbEmsNo.setEnabled(isNewBill);
		this.tfMemo1.setEditable(isNewBill);
		this.tfConveyance.setEditable(isNewBill);
		this.cbbCustoms.setEnabled(isNewBill);
		this.cbbImpExpPort.setEnabled(isNewBill);
		this.cbbImpExpType.setEnabled(isNewBill);
		this.cbbTradeMode.setEnabled(isNewBill);
		this.cbbTransportMode.setEnabled(isNewBill);
		this.rbApply.setEnabled(isNewBill);
		this.rbContract.setEnabled(isNewBill);
		this.rbApply.setSelected(true);
		if (!isNewBill) {
			this.rbIntoOldBill.setSelected(true);
		}
		// this.rbIntoOldBill.setEnabled(isNewBill);
		// this.rbMakeNewBill.setEnabled(isNewBill);
		this.rbTransferExport.setEnabled(isNewBill);
		this.rbTransferImport.setEnabled(isNewBill);
//		getRbSelectContract().setEnabled(isNewBill);
//		getRbSelectContract().setSelected(true);
//		getRbAutoSelectContract().setEnabled(isNewBill);
//		getRbAutoSelectContract().setSelected(false);
//		if (isfromCustoms) {
//			getRbAutoSelectContract().setEnabled(false);
//			getRbAutoSelectContract().setSelected(false);
//			getRbMakeNewBill().setEnabled(false);
//		}
	}

	/**
	 * This method initializes rbIntoOldBill
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getRbIntoOldBill() {
		if (rbIntoOldBill == null) {
			rbIntoOldBill = new JRadioButton();
			rbIntoOldBill.setBounds(34, 53, 144, 21);
			rbIntoOldBill.setText("追加到现有的报关单");
			rbIntoOldBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isNewBill = false;
							setState();
							showData(bcsCustomsDeclaration);
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
			cbbImpExpType.setBounds(116, 117, 203, 25);
			cbbImpExpType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
//					System.out.println("e.item=" + e.getItem().toString());
					if (e.getStateChange() == ItemEvent.SELECTED
							&& (e.getItem().toString().equals("料件转厂") || e
									.getItem().toString().equals("转厂出口"))) {
						isTransOut = true;
					} else {
						isTransOut = false;
					}
					initScmCoc();
					initData();
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
			tfEnterpriseBillNo.setBounds(410, 120, 183, 25);
			tfEnterpriseBillNo.setEditable(false);
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
			cbbCustoms.setBounds(410, 149, 203, 25);
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
			cbbImpExpPort.setBounds(410, 177, 203, 25);
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
			btnEnterpriseBillNo.setBounds(593, 120, 20, 25);
			btnEnterpriseBillNo.setText("...");

			btnEnterpriseBillNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							int impExpType = Integer
									.parseInt(((ItemProperty) (cbbImpExpType
											.getSelectedItem())).getCode());
							List<BaseCustomsDeclaration> dataSource = contractExeAction
									.findCustomsDeclarationByImpExpType(
											new Request(CommonVars
													.getCurrUser()), impExpType);
							Object obj = CommonQuery.getInstance()
									.getCustomsDeclarationByImpExpType(
											dataSource, impExpType);
							if (obj != null) {
								bcsCustomsDeclaration = (BcsCustomsDeclaration) obj;
								tfEnterpriseBillNo
										.setText(bcsCustomsDeclaration
												.getEmsHeadH2k());
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
			tfMemo1.setBounds(117, 243, 497, 25);
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
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
//		//
//		// 初始化电子备案合同(正在执行的合同)
//		//
//		cbbEmsNo.removeAllItems();
//		List contracts = contractAction
//				.findContractByProcessExeEndDate(new Request(CommonVars
//						.getCurrUser()));
//		long minx = 0l;
//		if (contracts != null && contracts.size() > 0) {
//			for (int i = 0; i < contracts.size(); i++) {
//				this.cbbEmsNo.addItem((Contract) contracts.get(i));
//			}
//			this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance().getRender(
//					"impContractNo", "emsNo", 80, 100));
//			cbbEmsNo.setUI(new CustomBaseComboBoxUI(200));
//		}
//		if (this.cbbEmsNo.getItemCount() > 0) {
//			this.cbbEmsNo.setSelectedIndex(0);
//		}
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
		// 初始化运输方式
		this.cbbTransportMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransportMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransportMode);
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		cbbTradeMode.setSelectedIndex(-1);
		// 初始化客户/供应商
		initScmCoc();
		initData();
	}

	private void initScmCoc() {

		List list = new ArrayList();
		if (!isImportGoods) {
			list = this.getCustomer();
		} else {
			list = this.getManufacturer();
		}
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		this.cbbScmCoc.setSelectedIndex(-1);
	}

	/**
	 * 获得是客户的对象列表
	 */
	protected List getCustomer() {
		List list = null;
		if (isTransOut) {

			list = this.materialManageAction.findScmManuFactoryOut(new Request(
					CommonVars.getCurrUser()));
			// String string[] =(String[])list.get(0);
			// System.out.print(string[0]+"sdfgsdgfdsgdfsgdfsh");
		} else
			list = this.materialManageAction.findScmManucustomer(new Request(
					CommonVars.getCurrUser()));
		List customerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsCustomer() != null
					&& scmCoc.getIsCustomer().booleanValue()) {
				customerList.add(scmCoc);
			}
		}
		return customerList;
	}

	/**
	 * 获得是供应商的对象列表
	 */
	protected List getManufacturer() {
		List list = null;
		if (isTransOut) {

			list = this.materialManageAction.findScmManuFactoryIn(new Request(
					CommonVars.getCurrUser()));

		} else

			list = this.materialManageAction.findScmManufacturer(new Request(
					CommonVars.getCurrUser()));

		List manufacturerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if (scmCoc.getIsManufacturer() != null
					&& scmCoc.getIsManufacturer().booleanValue()) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
	}

	/**
	 * 初始化RadioButton窗体控件
	 * 
	 */
	private void initRbComponents() {
		this.rbTransferExport.setSelected(!this.isImportGoods());
		this.rbTransferImport.setSelected(this.isImportGoods());
		this.rbMakeNewBill.setSelected(true);
		setState();
	}

	/**
	 * true 成功,false 失败
	 * 
	 * @return
	 */
	public boolean vaildateData() {
		if (this.cbbImpExpType.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "进出口类型不可为空!", "提示!",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.rbIntoOldBill.isSelected() == true) {
			if (this.bcsCustomsDeclaration == null) {
				JOptionPane.showMessageDialog(this, "报关单编号不可为空!", "提示!",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
//		if (getRbSelectContract().isSelected()
//				&& this.cbbEmsNo.getSelectedItem() == null) {
//			JOptionPane.showMessageDialog(this, "没有正在执行的合同,不能生成报关单!", "提示!",
//					JOptionPane.INFORMATION_MESSAGE);
//			return false;
//		}
		return true;
	}

	/**
	 * 获得报关单表头
	 */
	public BcsCustomsDeclaration getBcsCustomsDeclaration() {
		if (this.rbMakeNewBill.isSelected() == true) {
			BcsCustomsDeclaration a = new BcsCustomsDeclaration();
			a.setCompany(CommonVars.getCurrUser().getCompany());
			a.setMemos(this.tfMemo1.getText());
			a.setConveyance(this.tfConveyance.getText());
			a.setTransferMode((Transf) this.cbbTransportMode.getSelectedItem());
			a.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			a.setCustoms((Customs) this.cbbImpExpPort.getSelectedItem());
			a
					.setDeclarationCustoms((Customs) this.cbbCustoms
							.getSelectedItem());

			a.setTradeMode((Trade) this.cbbTradeMode.getSelectedItem());
			Integer impExpType = Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode());
			a.setImpExpType(impExpType);
//			Contract d = (Contract) cbbEmsNo.getSelectedItem();
//			a.setContract(d == null ? "" : d.getImpContractNo());
//			a.setEmsHeadH2k(d == null ? "" : d.getEmsNo());
//			a.setTradeName(d == null ? "" : d.getTradeName());
//			a.setMachName(d == null ? "" : d.getMachName());
			if (this.isImportGoods == true) {
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.IMPORT));
			} else {
				a.setImpExpFlag(Integer.valueOf(ImpExpFlag.EXPORT));
			}
			return a;
		} else {
			return this.bcsCustomsDeclaration;
		}
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData(BcsCustomsDeclaration a) {
		if (a == null) {
			return;
		}
		this.tfMemo1.setText(a.getMemos());
		this.cbbTransportMode.setSelectedItem(a.getTransferMode());
		this.cbbTradeMode.setSelectedItem(a.getTradeMode());
		this.cbbImpExpPort.setSelectedItem(a.getCustoms());
		this.cbbCustoms.setSelectedItem(a.getDeclarationCustoms());
		this.cbbTradeMode.setSelectedItem(a.getTradeMode());
		getTfEnterpriseBillNo().setText(
				a == null ? null : a.getCustomsDeclarationCode());
		int index = -1;
//		ComboBoxModel model = this.cbbEmsNo.getModel();
//		int size = model.getSize();
//		for (int i = 0; i < size; i++) {
//			Contract entity = (Contract) model.getElementAt(i);
//			if (entity.getImpContractNo().equals(a.getContract())
//					&& entity.getEmsNo().equals(a.getEmsHeadH2k())) {
//				index = i;
//				break;
//			}
//		}
//		cbbEmsNo.setSelectedIndex(index);
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

//	/**
//	 * 是否是新的记录
//	 */
//	public Contract getContract() {
//		return (Contract) cbbEmsNo.getSelectedItem();
//	}

	/**
	 * @param dzbaCustomsDeclaration
	 *            The dzbaCustomsDeclaration to set.
	 */
	public void setBcsCustomsDeclaration(
			BcsCustomsDeclaration bcsCustomsDeclaration) {
		this.bcsCustomsDeclaration = bcsCustomsDeclaration;
	}

	/**
	 * @return Returns the isInRequestBill.
	 */
	public boolean isNewBill() {
		return isNewBill;
	}

	/**
	 * @param isInRequestBill
	 *            The isInRequestBill to set.
	 */
	public void setNewBill(boolean isInRequestBill) {
		this.isNewBill = isInRequestBill;
	}

	public boolean isIsfromCustoms() {
		return isfromCustoms;
	}

	public void setIsfromCustoms(boolean isfromCustoms) {
		this.isfromCustoms = isfromCustoms;
	}

	/**
	 * This method initializes tfMemo2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo2() {
		if (tfConveyance == null) {
			tfConveyance = new JTextField();
			tfConveyance.setBounds(new Rectangle(118, 213, 203, 25));
		}
		return tfConveyance;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(410, 209, 203, 25));
		}
		return cbbScmCoc;
	}

	public ImpExpRequestBill getHeadImpExpRequestBill() {
		return headImpExpRequestBill;
	}

	public void setHeadImpExpRequestBill(ImpExpRequestBill headImpExpRequestBill) {
		this.headImpExpRequestBill = headImpExpRequestBill;
	}
	/**
	 * This method initializes pnPrice	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnPrice() {
		if (pnPrice == null) {
			pnPrice = new JPanel();
			pnPrice.setLayout(null);
			pnPrice.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "\u5546\u54c1\u5355\u4ef7\u6765\u6e90", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			pnPrice.add(getRbContract(), null);
			pnPrice.add(getRbApply(), null);
		}
		return pnPrice;
	}
	/**
	 * This method initializes rbContract	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbContract() {
		if (rbContract == null) {
			rbContract = new JRadioButton();
			rbContract.setBounds(new Rectangle(98, 34, 80, 22));
			rbContract.setText("通关手册");
		}
		return rbContract;
	}
	/**
	 * This method initializes rbApply	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	public JRadioButton getRbApply() {
		if (rbApply == null) {
			rbApply = new JRadioButton();
			rbApply.setBounds(new Rectangle(10, 34, 79, 21));
			rbApply.setText("申请单");
		}
		return rbApply;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			java.awt.GridLayout gridLayout2 = new GridLayout();
			jPanel2.setLayout(gridLayout2);
			gridLayout2.setRows(1);
			gridLayout2.setColumns(0);
			jPanel2.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel2.setBounds(new Rectangle(27, 15, 584, 90));
			jPanel2.add(getJPanel1(), null);
			jPanel2.add(getPnPrice(), null);
			jPanel2.add(getJPanel(), null);
		}
		return jPanel2;
	}
	
}  //  @jve:decl-index=0:visual-constraint="-391,54"
