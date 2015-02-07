package com.bestway.client.custom.common;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JPanelBase;
import java.awt.Rectangle;

public class PnMakeDzscCustomsDeclaration extends JPanelBase {

    private JRadioButton           rbTransferImport       = null;
    private JRadioButton           rbTransferExport       = null;
    private ButtonGroup            buttonGroup            = null;  //  @jve:decl-index=0:
    private ButtonGroup            buttonGroup1           = null;  //  @jve:decl-index=0:
    private ButtonGroup buttonGroup2 = null; // @jve:decl-index=0:
    private JPanel                 jPanel                 = null;
    private JTextField             tfMemo1                = null;
    private JPanel                 jPanel1                = null;
    private JRadioButton           rbMakeNewBill          = null;
    private JRadioButton           rbIntoOldBill          = null;
    private JComboBox              cbbImpExpType          = null;
    private JTextField             tfEnterpriseBillNo     = null;
    private JComboBox              cbbCustoms             = null;
    private JComboBox              cbbImpExpPort          = null;
    private JComboBox              cbbTradeMode           = null;
    private JComboBox              cbbTransportMode       = null;
    private JButton                btnEnterpriseBillNo    = null;
    private DzscAction             dzscAction             = null;
    private boolean                isImportGoods          = true;
    private int                    type                   = -1;
    private JComboBox              cbbEmsNo               = null;
    private DzscCustomsDeclaration dzscCustomsDeclaration = null;  //  @jve:decl-index=0:
    private DzscContractExeAction  dzsccontractExeAction  = null;
	private boolean isNewBill = true;
	private JRadioButton rbSelectContract = null;
	private JRadioButton rbAutoSelectContract = null;
	private boolean isfromCustoms = false;
	private List<DzscEmsPorHead> contracts = null;  //  @jve:decl-index=0:
    /**
     * This is the default constructor
     */
    public PnMakeDzscCustomsDeclaration() {
        super();
        initialize();
        this.dzscAction = (DzscAction) CommonVars.getApplicationContext()
                .getBean("dzscAction");
        dzsccontractExeAction = (DzscContractExeAction) CommonVars
                .getApplicationContext().getBean("dzscContractExeAction");
        getButtonGroup();
        getButtonGroup1();
        getButtonGroup2();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
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

        jLabel4.setBounds(75, 219, 101, 25);

        jLabel4.setText("备注");

        jLabel.setBounds(75, 96, 101, 25);
        jLabel.setText("报关单类型");
        jLabel.setForeground(java.awt.Color.blue);
        jLabel1.setBounds(342, 96, 67, 25);
        jLabel1.setText("帐册编号");
        jLabel2.setBounds(75, 126, 101, 25);
        jLabel2.setText("报关单编号");
        jLabel3.setBounds(342, 157, 67, 25);
        jLabel3.setText("进出口岸");
        jLabel5.setBounds(342, 126, 67, 25);
        jLabel5.setText("申报地海关");
        jLabel6.setBounds(75, 157, 101, 25);
        jLabel6.setText("运输方式");
        jLabel7.setBounds(75, 188, 101, 25);
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
        this.add(getCbbEmsNo(), null);
    }

    public void setVisible(boolean isFalg) {
        if (isFalg == true) {
        	//初始化ComboBox
            initUIComponents();
            //显示数据
            showData(dzscCustomsDeclaration);
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
            cbbTradeMode.setBounds(178, 188, 149, 25);
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
            cbbTransportMode.setBounds(178, 157, 149, 25);
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
        return buttonGroup1;
    }

    
    private ButtonGroup getButtonGroup2() {
		if (buttonGroup2 == null) {
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(getRbSelectContract());
			buttonGroup2.add(getRbAutoSelectContract());
		}
		return buttonGroup2;
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
            jPanel.setBounds(394, 14, 158, 72);

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
            jPanel1.setBounds(75, 14, 314, 73);
            jPanel1.add(getRbMakeNewBill(), null);
            jPanel1.add(getRbIntoOldBill(), null);
            jPanel1.add(getRbSelectContract(), null);
            jPanel1.add(getRbAutoSelectContract(), null);
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
            rbMakeNewBill.setBounds(9, 20, 130, 21);
            rbMakeNewBill.setText("生成新的报关单");
            rbMakeNewBill.setSelected(true);
            rbMakeNewBill
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                        	isNewBill = true;
                            setState(true);                            
                        }
                    });
        }
        return rbMakeNewBill;
    }

    private void setState(boolean isNewApplyToCustomsBill) {
        this.tfEnterpriseBillNo.setEditable(!isNewApplyToCustomsBill);
        this.btnEnterpriseBillNo.setEnabled(!isNewApplyToCustomsBill);
        this.cbbEmsNo.setEnabled(isNewApplyToCustomsBill);
        this.tfMemo1.setEditable(isNewApplyToCustomsBill);
        this.cbbCustoms.setEnabled(isNewApplyToCustomsBill);
        this.cbbImpExpPort.setEnabled(isNewApplyToCustomsBill);
        this.cbbTradeMode.setEnabled(isNewApplyToCustomsBill);
        this.cbbTransportMode.setEnabled(isNewApplyToCustomsBill);
        this.rbSelectContract.setEnabled(isNewApplyToCustomsBill);
        this.rbAutoSelectContract.setEnabled(isNewApplyToCustomsBill);
    }
    
    private void setState() {
    	this.btnEnterpriseBillNo.setEnabled(!isNewBill);
		if (isNewBill) {
			this.tfEnterpriseBillNo.setText("");
		}
		this.cbbEmsNo.setEnabled(isNewBill);
		this.tfMemo1.setEditable(isNewBill);
		this.cbbCustoms.setEnabled(isNewBill);
		this.cbbImpExpPort.setEnabled(isNewBill);
		this.cbbImpExpType.setEnabled(isNewBill);
		this.cbbTradeMode.setEnabled(isNewBill);
		this.cbbTransportMode.setEnabled(isNewBill);
		if (!isNewBill) {
			this.rbIntoOldBill.setSelected(true);
		}
		// this.rbIntoOldBill.setEnabled(isNewBill);
		// this.rbMakeNewBill.setEnabled(isNewBill);
		this.rbTransferExport.setEnabled(isNewBill);
		this.rbTransferImport.setEnabled(isNewBill);
		getRbSelectContract().setEnabled(isNewBill);
		getRbSelectContract().setSelected(true);
		getRbAutoSelectContract().setEnabled(isNewBill);
		getRbAutoSelectContract().setSelected(false);
		if (isfromCustoms) {
			getRbAutoSelectContract().setEnabled(false);
			getRbAutoSelectContract().setSelected(false);
			getRbMakeNewBill().setEnabled(false);
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
            rbIntoOldBill.setBounds(148, 20, 156, 21);
            rbIntoOldBill.setText("追加到现有的报关单");
            rbIntoOldBill
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                        	isNewBill = false;
                            setState(false);
                            showData(dzscCustomsDeclaration);
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
            cbbImpExpType.setBounds(178, 96, 148, 25);
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
            tfEnterpriseBillNo.setBounds(178, 126, 127, 25);
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
            cbbCustoms.setBounds(409, 126, 144, 25);
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
            cbbImpExpPort.setBounds(409, 157, 144, 25);
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
            btnEnterpriseBillNo.setBounds(304, 126, 23, 25);
            btnEnterpriseBillNo.setText("...");
            btnEnterpriseBillNo
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            int impExpType = Integer
                                    .parseInt(((ItemProperty) (cbbImpExpType
                                            .getSelectedItem())).getCode());
                            List<BaseCustomsDeclaration> dataSource = dzsccontractExeAction
                                    .findCustomsDeclarationByImpExpType(
                                            new Request(CommonVars
                                                    .getCurrUser()), impExpType);
                            Object obj = CommonQuery.getInstance()
                                    .getCustomsDeclarationByImpExpType(
                                            dataSource, impExpType);
                            if (obj != null) {
                                dzscCustomsDeclaration = (DzscCustomsDeclaration) obj;
                                tfEnterpriseBillNo
                                        .setText(dzscCustomsDeclaration
                                                .getEmsHeadH2k());
                                String emsHeadH2k = dzscCustomsDeclaration.getEmsHeadH2k();
                                for(DzscEmsPorHead head : contracts){
                                	if(head.getEmsNo().equals(emsHeadH2k)){
                                		cbbEmsNo.setSelectedItem(head);
                                		break;
                                	}
                                }
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
            tfMemo1.setBounds(178, 219, 377, 25);
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
        //
        // 初始化电子手册合同(正在执行的合同)
        //
        cbbEmsNo.removeAllItems();
         contracts = dzscAction.findDzscEmsPorHead(new Request(CommonVars
                .getCurrUser()), DzscState.EXECUTE);
        if (contracts != null && contracts.size() > 0) {
            for (int i = 0; i < contracts.size(); i++) {
                this.cbbEmsNo.addItem((DzscEmsPorHead) contracts.get(i));
            }
            this.cbbEmsNo.setRenderer(CustomBaseRender.getInstance().getRender(
                    "ieContractNo", "emsNo", 100, 100));
            cbbEmsNo.setUI(new CustomBaseComboBoxUI(200));
        }
        if (this.cbbEmsNo.getItemCount() > 0) {
            this.cbbEmsNo.setSelectedIndex(0);
        }
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
     * true 成功,false 失败
     * 
     * @return
     */
    public boolean vaildateData() {
        if (this.cbbImpExpType.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "进出口类型不可为空!!", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (this.rbIntoOldBill.isSelected() == true) {
            if (this.dzscCustomsDeclaration == null) {
                JOptionPane.showMessageDialog(this, "报关单对象不可为空!!", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        if(rbSelectContract.isSelected()){
	        if (this.cbbEmsNo.getSelectedItem() == null) {
	            JOptionPane.showMessageDialog(this, "没有正在执行的合同,不能生成报关单!!", "提示",
	                    JOptionPane.INFORMATION_MESSAGE);
	            return false;
	        }
        }
        return true;
    }

    /**
     * 获得报关单表头
     */
    public DzscCustomsDeclaration getDzscCustomsDeclaration() {
        if (this.rbMakeNewBill.isSelected() == true) {
            DzscCustomsDeclaration a = new DzscCustomsDeclaration();
            a.setCompany(CommonVars.getCurrUser().getCompany());
            a.setMemos(this.tfMemo1.getText());
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
            DzscEmsPorHead d = (DzscEmsPorHead) cbbEmsNo.getSelectedItem();
            a.setContract(d == null ? "" : d.getIeContractNo());
            a.setEmsHeadH2k(d == null ? "" : d.getEmsNo());
            if (this.isImportGoods == true) {
                a.setImpExpFlag(Integer.valueOf(ImpExpFlag.IMPORT));
            } else {
                a.setImpExpFlag(Integer.valueOf(ImpExpFlag.EXPORT));
            }
            return a;
        } else {
            return this.dzscCustomsDeclaration;
        }
    }

    /**
     * 显示数据
     * 
     */
    private void showData(DzscCustomsDeclaration a) {
        if (a == null) {
            return;
        }
        this.tfMemo1.setText(a.getMemos());
        this.cbbTransportMode.setSelectedItem(a.getTransferMode());
        this.cbbTradeMode.setSelectedItem(a.getTradeMode());
        this.cbbImpExpPort.setSelectedItem(a.getCustoms());
        this.cbbCustoms.setSelectedItem(a.getDeclarationCustoms());
        this.cbbTradeMode.setSelectedItem(a.getTradeMode());
        int index = -1;
        ComboBoxModel model = this.cbbEmsNo.getModel();
        int size = model.getSize();
        for (int i = 0; i < size; i++) {
            DzscEmsPorHead entity = (DzscEmsPorHead) model.getElementAt(i);
            if (entity.getIeContractNo().equals(a.getContract())
                    && entity.getEmsNo().equals(a.getEmsHeadH2k())) {
                index = i;
                break;
            }
        }
        cbbEmsNo.setSelectedIndex(index);
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
     * 是否自动选合同
     * @return
     */
    public boolean getIsAutoSelectContract(){
    	return this.rbAutoSelectContract.isSelected();
    }

    /**
     * 是否是新的记录
     */
    public DzscEmsPorHead getDzscEmsPorHead() {
        return (DzscEmsPorHead) cbbEmsNo.getSelectedItem();
    }

    /**
     * This method initializes cbbEmsNo
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getCbbEmsNo() {
        if (cbbEmsNo == null) {
            cbbEmsNo = new JComboBox();
            cbbEmsNo.setBounds(new Rectangle(409, 96, 144, 25));
        }
        return cbbEmsNo;
    }

    /**
     * @param dzscCustomsDeclaration
     *            The dzscCustomsDeclaration to set.
     */
    public void setDzscCustomsDeclaration(
            DzscCustomsDeclaration dzscCustomsDeclaration) {
        this.dzscCustomsDeclaration = dzscCustomsDeclaration;
    }


	/**
	 * This method initializes rbSelectContract	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbSelectContract() {
		if (rbSelectContract == null) {
			rbSelectContract = new JRadioButton();
			rbSelectContract.setBounds(new Rectangle(9, 46, 109, 23));
			rbSelectContract.setText("手选合同");
//			rbSelectContract.setSelected(true);
			rbSelectContract.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getCbbEmsNo().setEnabled(getRbMakeNewBill().isSelected());
				}
			});
			
		}
		return rbSelectContract;
	}

	/**
	 * This method initializes rbAutoSelectContract	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbAutoSelectContract() {
		if (rbAutoSelectContract == null) {
			rbAutoSelectContract = new JRadioButton();
			rbAutoSelectContract.setBounds(new Rectangle(148, 46, 145, 22));
			rbAutoSelectContract.setText("根据余量自动选合同");
			rbAutoSelectContract.setSelected(true);
			rbAutoSelectContract.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getCbbEmsNo().setSelectedItem(null);
					getCbbEmsNo().setEnabled(false);
				}
			});
		}
		return rbAutoSelectContract;
	}
	
	public boolean isIsfromCustoms() {
		return isfromCustoms;
	}

	public void setIsfromCustoms(boolean isfromCustoms) {
		this.isfromCustoms = isfromCustoms;
	}

} // @jve:decl-index=0:visual-constraint="-404,52"
