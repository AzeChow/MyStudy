/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.TransParameterSet;
/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCustomsEnvelopCommodityInfo extends DgCommon {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel                  jContentPane             = null;
    private JToolBar                jToolBar                 = null;
    private JButton                 btnSave                  = null;
    private JButton                 btnCancel                = null;
    private JTextField              tfName                   = null;
    private JComboBox               cbbUnit                  = null;
    private JTextField              tfOppositeComplexCode    = null;
    private JTextField              tfComplexCode            = null;
    private JTextField              tfSpec                   = null;
    private JFormattedTextField     tfOwnerQuantity          = null;
    private JFormattedTextField     tfUnitPrice              = null;
    private JTableListModel         tableModel               = null;
    private CustomsEnvelopBill      customsEnvelopBill       = null;  //  @jve:decl-index=0:
    private int                     dataState                = DataState.BROWSE;
    private boolean                 isImportGoods            = true;
    private JPanel                  jPanel                   = null;
    private JTextField              tfOppositeSpec           = null;
    private JTextField              tfOppositeName           = null;
    private JTextField              tfAmount                 = null;
    private JComboBox               cbbOppositeUnit          = null;

    private DefaultFormatterFactory defaultFormatterFactory1 = null;            //  @jve:decl-index=0:parse
    private NumberFormatter         numberFormatter          = null;            //  @jve:decl-index=0:parse
    private JFormattedTextField     tfOppositeQuantity       = null;
    private JLabel                  jLabel16                 = null;
    private JLabel                  jLabel17                 = null;
    private JTextField              tfOppositeEmsSerialNo    = null;
    private JTextField              tfOppositeEmsNo          = null;
    private JLabel                  jLabel18                 = null;
    private JTextField              tfEmsSerialNo            = null;
    private JLabel                  jLabel19                 = null;
    private JTextField              tfEmsNo                  = null;
	private JLabel lbContractRemain = null;
	private double contractRemainUse =0.0;
	private JComboBox cbbCurr = null;
	private ManualDeclareAction manualDeclareAction = null;
	
    public DgCustomsEnvelopCommodityInfo() {
		super();
		this.transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
				.getApplicationContext().getBean("transferFactoryManageAction");

		this.manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		initUIComponents();
	}

    private void initialize() {
        this.setContentPane(getJContentPane());
        this.setSize(510, 374);

    }

    @Override
	public void setVisible(boolean isFlag) {
        if (isFlag) {
            if (dataState == DataState.ADD) {
                //			emptyData();
                //			fillMateriel();
                //			ok!!
            } else if (dataState == DataState.EDIT) {
                CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo = (CustomsEnvelopCommodityInfo) tableModel
                        .getCurrentRow();
                showData(customsEnvelopCommodityInfo);
                showMessage(customsEnvelopCommodityInfo);
            }
            if (isImportGoods() == true) {
                this.setTitle("关封单据---转入商品信息表");
            } else {
                this.setTitle("关封单据---转出商品信息表");
            }
        }
        super.setVisible(isFlag);
    }
    
    private void showMessage(CustomsEnvelopCommodityInfo data) {
    	if(isImportGoods){
    	//获取合同定量
    	System.out.println("data.getEmsNo()="+data.getEmsNo());
    	System.out.println("data.getSeqNum()="+data.getSeqNum());
    	Double contractAmount = this.transferFactoryManageAction.findContractImgByConNoAndEmsNo(
                new Request(CommonVars.getCurrUser()),data.getEmsNo(), data.getSeqNum());
    	System.out.println("合同定量="+contractAmount);
    	//获取合同已使用量
    	
    	Double contractUse =  this.transferFactoryManageAction.findCommInfoTotalAmount(
    			new Request(CommonVars.getCurrUser()),data.getSeqNum()
    			, ImpExpFlag.IMPORT,new Integer[]{0,1,21},data.getEmsNo());
    	System.out.println("合同已使用量="+contractUse);
    	//获取关封已使用量
    	//2013-11-22 经过与顾问同事讨论，关封可备案量 = 合同定量 - 合同已使用量 所以关封已使用量删除
//    	Double contractEnvelopUse = this.transferFactoryManageAction.findCECI(new Request(CommonVars.getCurrUser()),
//    			data.getEmsNo(), data.getSeqNum(), true);
//    	System.out.println("关封已使用量="+contractEnvelopUse);
    	//合同余量 
    	double contractRemain = contractAmount - contractUse;
    	contractRemainUse = contractRemain;
    	lbContractRemain.setText("合同定量="
					+ CommonUtils.formatDoubleByDigit(contractAmount, 2)
					+ ",合同已使用量="
					+ CommonUtils.formatDoubleByDigit(contractUse , 2) 
					+ ",合同余量 ="
					+ CommonUtils.formatDoubleByDigit(contractRemain, 2));
    	}else{
    		//获取合同定量
        	System.out.println("data.getEmsNo()="+data.getEmsNo());
        	System.out.println("data.getSeqNum()="+data.getSeqNum());
        	Double contractAmount = this.transferFactoryManageAction.findContractExgByConNoAndEmsNo(
                    new Request(CommonVars.getCurrUser()),data.getEmsNo(), data.getSeqNum());
        	System.out.println("合同定量="+contractAmount);
        	//获取合同已使用量
        	Double contractUse =  this.transferFactoryManageAction.findCommInfoTotalAmount(
        			new Request(CommonVars.getCurrUser()),data.getSeqNum()
        			, ImpExpFlag.EXPORT,new Integer[]{4,5,7},data.getEmsNo());
        	System.out.println("合同已使用量="+contractUse);
        	//获取关封已使用量
        	//获取关封已使用量
        	//2013-11-22 经过与顾问同事讨论，关封可备案量 = 合同定量 - 合同已使用量 所以关封已使用量删除
//        	Double contractEnvelopUse = this.transferFactoryManageAction.findCECI(new Request(CommonVars.getCurrUser()),
//        			data.getEmsNo(), data.getSeqNum(), false);
//        	System.out.println("关封已使用量="+contractEnvelopUse);
        	//合同余量 
        	double contractRemain = contractAmount - contractUse;
        	contractRemainUse = contractRemain;
        	lbContractRemain.setText("合同定量="+contractAmount+",合同已使用量="+(contractUse)
        			+",合同余量 ="+contractRemain);
    	}
    }
    /**
     * @return Returns the transferFactoryManageAction.
     */
    public TransferFactoryManageAction getTransferFactoryManageAction() {
        return transferFactoryManageAction;
    }

    /**
     * @param transferFactoryManageAction
     *            The transferFactoryManageAction to set.
     */
    public void setTransferFactoryManageAction(
            TransferFactoryManageAction transferFactoryManageAction) {
        this.transferFactoryManageAction = transferFactoryManageAction;
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
     * @return Returns the impExpRequestBill.
     */
    public CustomsEnvelopBill getCustomsEnvelopBill() {
        return customsEnvelopBill;
    }

    /**
     * @param impExpRequestBill
     *            The impExpRequestBill to set.
     */
    public void setCustomsEnvelopBill(CustomsEnvelopBill impExpRequestBill) {
        this.customsEnvelopBill = impExpRequestBill;
    }

    /**
     * @return Returns the tableModel.
     */
    public JTableListModel getTableModel() {
        return tableModel;
    }

    /**
     * @param tableModel
     *            The tableModel to set.
     */
    public void setTableModel(JTableListModel tableModel) {
        this.tableModel = tableModel;
    }

    /**
     * This method initializes
     *  
     */
    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {

            lbContractRemain = new JLabel();
            lbContractRemain.setBounds(new Rectangle(26, 166, 450, 30));
            lbContractRemain.setForeground(Color.blue);
            jLabel19 = new JLabel();
            jLabel18 = new JLabel();
            java.awt.GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            javax.swing.JLabel jLabel14 = new JLabel();
            javax.swing.JLabel jLabel = new JLabel();
            javax.swing.JLabel jLabel13 = new JLabel();
            javax.swing.JLabel jLabel11 = new JLabel();
            javax.swing.JLabel jLabel10 = new JLabel();
            jContentPane = new JPanel();
            javax.swing.JLabel jLabel2 = new JLabel();
            javax.swing.JLabel jLabel6 = new JLabel();
            javax.swing.JLabel jLabel7 = new JLabel();
            javax.swing.JLabel jLabel3 = new JLabel();
            javax.swing.JLabel jLabel4 = new JLabel();
            javax.swing.JLabel jLabel5 = new JLabel();
            jLabel10.setText("名称");
            jLabel11.setText("海关商品编号");
            jLabel13.setText("数量");
            jLabel14.setText("币制");
            jContentPane.setLayout(null);
            jLabel.setText("规格型号");
            jLabel.setBounds(54, 92, 49, 22);
            jLabel2.setText("单位");
            jLabel2.setBounds(299, 91, 25, 22);
            jLabel6.setText("单价");
            jLabel6.setBounds(78, 142, 25, 22);
            jLabel7.setText("总价");
            jLabel7.setBounds(298, 143, 26, 22);
            gridBagConstraints10.gridx = 3;
            gridBagConstraints10.gridy = 7;
            gridBagConstraints10.ipadx = -24;
            gridBagConstraints10.ipady = -8;
            gridBagConstraints10.insets = new java.awt.Insets(3, 1, 2, 31);
            gridBagConstraints11.gridx = 3;
            gridBagConstraints11.gridy = 6;
            gridBagConstraints11.ipadx = -24;
            gridBagConstraints11.ipady = -8;
            gridBagConstraints11.insets = new java.awt.Insets(5, 1, 1, 31);
            jLabel10.setBounds(299, 66, 25, 22);
            jLabel11.setBounds(29, 67, 74, 22);
            jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                    12));
            jLabel13.setBounds(78, 118, 25, 22);
            jLabel14.setBounds(298, 115, 25, 22);
            jLabel3.setBounds(0, 0, 0, 0);
            jLabel4.setBounds(0, 0, 0, 0);
            jLabel5.setBounds(0, 0, 0, 0);

            jLabel18.setBounds(53, 42, 50, 21);
            jLabel18.setText("帐册序号");
            jLabel19.setBounds(287, 42, 36, 21);
            jLabel19.setText("帐册号");
            jContentPane.add(getTfName(), null);
            jContentPane.add(getCbbUnit(), null);
            jContentPane.add(getTfComplexCode(), null);
            jContentPane.add(getTfSpec(), null);
            jContentPane.add(jLabel10, null);
            jContentPane.add(jLabel11, null);
            jContentPane.add(jLabel13, null);
            jContentPane.add(jLabel14, null);
            jContentPane.add(getJToolBar(), null);
            jContentPane.add(jLabel, null);
            jContentPane.add(jLabel2, null);
            jContentPane.add(jLabel3, null);
            jContentPane.add(jLabel4, null);
            jContentPane.add(jLabel5, null);
            jContentPane.add(jLabel6, null);
            jContentPane.add(jLabel7, null);
            jContentPane.add(getTfOwnerQuantity(), null);
            jContentPane.add(getTfUnitPrice(), null);
            jContentPane.add(getJPanel(), null);
            jContentPane.add(getTfAmount(), null);
            jContentPane.add(jLabel18, null);
            jContentPane.add(getTfEmsSerialNo(), null);
            jContentPane.add(jLabel19, null);
            jContentPane.add(getTfEmsNo(), null);
            jContentPane.add(lbContractRemain, null);
            jContentPane.add(getCbbCurr(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.setBounds(3, 2, 500, 31);
            jToolBar.add(getBtnSave());
            jToolBar.add(getBtnCancel());
        }
        return jToolBar;
    }

    /**
     * This method initializes btnSave
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSave() {
        if (btnSave == null) {
            btnSave = new JButton();
            btnSave.setText("   保存   ");
            btnSave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (vaildatorDataIsNull()) {
                        return;
                    }
                    saveData();
                    if (dataState == DataState.EDIT) {
                        DgCustomsEnvelopCommodityInfo.this.dispose();
                    }
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
            btnCancel.setText("   取消   ");
        }
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                DgCustomsEnvelopCommodityInfo.this.dispose();
            }
        });
        return btnCancel;
    }

    /**
     * This method initializes tfOppositeQuantity
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTfOwnerQuantity() {
        if (tfOwnerQuantity == null) {
            tfOwnerQuantity = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
            tfOwnerQuantity.setFormatterFactory(getDefaultFormatterFactory1());
            tfOwnerQuantity.setText("0");
            tfOwnerQuantity.getDocument().addDocumentListener(
                    new DocumentListenerAdapter());
            tfOwnerQuantity.setBounds(104, 118, 130, 22);

        }
        return tfOwnerQuantity;
    }

    /**
     * This method initializes tfOppositeQuantity
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTfUnitPrice() {
        if (tfUnitPrice == null) {
            tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
            tfUnitPrice.setEditable(true);
            tfUnitPrice.getDocument().addDocumentListener(
                    new DocumentListenerAdapter());
            tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory1());
            tfUnitPrice.setBounds(104, 143, 130, 22);
        }
        return tfUnitPrice;
    }

    /**
     * This method initializes tfName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfName() {
        if (tfName == null) {
            tfName = new JTextField();
            tfName.setEditable(false);
            tfName.setBounds(325, 66, 130, 22);

        }
        return tfName;
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
     * This method initializes cbbUnit
     * 
     * @return javax.swing.JTextField
     */
    private JComboBox getCbbUnit() {
        if (cbbUnit == null) {
            cbbUnit = new JComboBox();
            cbbUnit.setBounds(325, 91, 130, 22);
        }
        return cbbUnit;
    }

    /**
     * This method initializes tfOppositeComplexCode
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfOppositeComplexCode() {
        if (tfOppositeComplexCode == null) {
            tfOppositeComplexCode = new JTextField();
            tfOppositeComplexCode.setBounds(86, 47, 120, 22);
        }
        return tfOppositeComplexCode;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jLabel17 = new JLabel();
            jLabel16 = new JLabel();
            jPanel = new JPanel();
            javax.swing.JLabel jLabel15 = new JLabel();
            javax.swing.JLabel jLabel12 = new JLabel();
            javax.swing.JLabel jLabel9 = new JLabel();
            javax.swing.JLabel jLabel8 = new JLabel();
            javax.swing.JLabel jLabel1 = new JLabel();
            jPanel.setLayout(null);
            jLabel12.setText("规格型号");
            jLabel12.setBounds(35, 71, 49, 22);
            jLabel15.setText("单位");
            jLabel15.setBounds(275, 72, 26, 22);
            jLabel8.setText("名称");
            jLabel8.setBounds(275, 47, 26, 22);
            jLabel9.setText("海关商品编号");
            jLabel9.setBounds(12, 47, 72, 22);
            jLabel1.setText("数量");
            jLabel1.setBounds(58, 96, 26, 22);
            jPanel.setBounds(25, 198, 452, 128);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "对方公司商品资料",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    new java.awt.Font("Dialog",
                                            java.awt.Font.PLAIN, 12),
                                    new java.awt.Color(51, 51, 51)));
            jLabel16.setBounds(33, 22, 51, 22);
            jLabel16.setText("帐册序号");
            jLabel17.setBounds(264, 23, 37, 22);
            jLabel17.setText("帐册号");
            jPanel.add(getTfOppositeComplexCode(), null);
            jPanel.add(jLabel12, null);
            jPanel.add(jLabel9, null);
            jPanel.add(jLabel8, null);
            jPanel.add(jLabel1, null);
            jPanel.add(jLabel15, null);
            jPanel.add(getTfOppositeSpec(), null);
            jPanel.add(getTfOppositeName(), null);
            jPanel.add(getCbbOppositeUnit(), null);
            jPanel.add(getTfOppositeQuantity(), null);
            jPanel.add(jLabel16, null);
            jPanel.add(jLabel17, null);
            jPanel.add(getTfOppositeEmsSerialNo(), null);
            jPanel.add(getTfOppositeEmsNo(), null);
        }
        return jPanel;
    }

    /**
     * This method initializes tfOppositeSpec
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfOppositeSpec() {
        if (tfOppositeSpec == null) {
            tfOppositeSpec = new JTextField();
            tfOppositeSpec.setBounds(86, 72, 120, 22);
        }
        return tfOppositeSpec;
    }

    /**
     * This method initializes tfOppositeName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfOppositeName() {
        if (tfOppositeName == null) {
            tfOppositeName = new JTextField();
            tfOppositeName.setBounds(302, 47, 128, 22);
        }
        return tfOppositeName;
    }

    /**
     * This method initializes tfAmount
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfAmount() {
        if (tfAmount == null) {
            tfAmount = new JTextField();
            tfAmount.setBounds(325, 143, 130, 22);
        }
        return tfAmount;
    }

    /**
     * This method initializes cbbOppositeUnit
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getCbbOppositeUnit() {
        if (cbbOppositeUnit == null) {
            cbbOppositeUnit = new JComboBox();
            cbbOppositeUnit.setBounds(302, 72, 128, 22);
        }
        return cbbOppositeUnit;
    }

    /**
     * This method initializes tfComplexCode
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfComplexCode() {
        if (tfComplexCode == null) {
            tfComplexCode = new JTextField();
            tfComplexCode.setEditable(false);
            tfComplexCode.setBounds(104, 67, 130, 22);
        }
        return tfComplexCode;
    }

    /**
     * This method initializes tfSpec
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfSpec() {
        if (tfSpec == null) {
            tfSpec = new JTextField();
            tfSpec.setEditable(false);
            tfSpec.setBounds(104, 92, 130, 22);
        }
        return tfSpec;
    }

    /**
     * 显示数据
     */
    private void showData(CustomsEnvelopCommodityInfo data) {
    	tfComplexCode.setText(data.getComplex()==null?"":data.getComplex().getCode());
	    tfName.setText(data.getPtName());  
	    tfSpec.setText(data.getPtSpec());
	    // 选择默认单位
//	    if(data.getUnit() != null) {
//	    	for (int i = 0; i < cbbUnit.getItemCount(); i++) {
//		    	cbbUnit.setSelectedIndex(i);
//		    	CalUnit calUnit = (CalUnit)(cbbUnit.getSelectedItem());
//		    	if(calUnit.getCode().equals(data.getUnit().getCode())) {
//		    		break;
//		    	}
//			}
//	    }
	    
	    cbbUnit.setSelectedItem(data.getUnit());
	    this.cbbCurr.setSelectedItem(data.getCurr());
	    tfEmsNo.setText(data.getEmsNo());
	    tfEmsSerialNo.setText(String.valueOf(data.getSeqNum()));
	    
	    
        if (data.getUnitPrice() != null) {
            tfUnitPrice.setValue(data.getUnitPrice());
        } else {
            tfUnitPrice.setValue(new Double(0));
        }
        if (data.getOwnerQuantity() != null) {
            tfOwnerQuantity.setValue(data.getOwnerQuantity());
        } else {
            tfOwnerQuantity.setValue(new Integer(0));
        }
        //
        // 对方公司商品信息
        //        
        if (data.getOppositeComplexCode() != null) {
            tfOppositeComplexCode.setText(data.getOppositeComplexCode());
        } else {
            tfOppositeComplexCode.setText("");
        }
        if (data.getOppositeName() != null) {
            tfOppositeName.setText(data.getOppositeName());
        } else {
            tfOppositeName.setText("");
        }
        if (data.getOppositeSpec() != null) {
            tfOppositeSpec.setText(data.getOppositeSpec());
        } else {
            tfOppositeSpec.setText("");
        }
        if (data.getOppositeQuantity() != null) {
            tfOppositeQuantity.setValue(data.getOppositeQuantity());
        } else {
            tfOppositeQuantity.setValue(new Integer(0));
        }
        if (data.getOppositeEmsNo() != null) {
            tfOppositeEmsNo.setText(data.getOppositeEmsNo());
        } else {
            tfOppositeEmsNo.setText("");
        }
        if (data.getOppositeEmsSerialNo() != null) {
            tfOppositeEmsSerialNo.setText(data.getOppositeEmsSerialNo());
        } else {
            tfOppositeEmsSerialNo.setText("");
        }
        cbbOppositeUnit.setSelectedItem(data.getOppositeUnit());
    }

    /**
     * 填充数据
     */
    private void fillData(CustomsEnvelopCommodityInfo data) {
        data.setCustomsEnvelopBill(this.customsEnvelopBill);
        data.setOwnerQuantity(Double.valueOf(this.tfOwnerQuantity.getValue()
                .toString()));
        data.setUnitPrice(Double
                .valueOf(this.tfUnitPrice.getValue().toString()));
//        data.setEmsNo(this.tfEmsNo.getText());
        data.setOppositeComplexCode(this.tfOppositeComplexCode.getText());
        data.setOppositeName(this.tfOppositeName.getText());
        data.setOppositeQuantity(Double.valueOf(this.tfOppositeQuantity
                .getValue().toString()));
        data.setOppositeUnit((Unit) this.cbbOppositeUnit.getSelectedItem());
        data.setOppositeSpec(this.tfOppositeSpec.getText());
        data.setOppositeEmsNo(this.tfOppositeEmsNo.getText());
        data.setOppositeEmsSerialNo(this.tfOppositeEmsSerialNo.getText());
        data.setCurr((Curr) cbbCurr.getSelectedItem());
        //设置创建日期
        if(data.getCreateDate()==null||"".equals(data.getCreateDate())){
        	SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	String defaultDate = defaultDateFormat.format(new Date());
        	data.setCreateDate(java.sql.Date.valueOf(defaultDate));
        }
    }

    /**
     * 保存数据
     */
    @SuppressWarnings("unchecked")
	private void saveData() {
        List list = new ArrayList();
        if (dataState == DataState.ADD) {
            CustomsEnvelopCommodityInfo data = new CustomsEnvelopCommodityInfo();
            fillData(data);
            list.add(data);
            this.transferFactoryManageAction.saveCustomsEnvelopCommodityInfo(
                    new Request(CommonVars.getCurrUser()), list);
            tableModel.addRow(data);
        } else if (dataState == DataState.EDIT) {
            CustomsEnvelopCommodityInfo data = (CustomsEnvelopCommodityInfo) tableModel
                    .getCurrentRow();
            fillData(data);
            list.add(data);
            this.transferFactoryManageAction.saveCustomsEnvelopCommodityInfo(
                    new Request(CommonVars.getCurrUser()), list);
            tableModel.updateRow(list.get(0));
        }
    }

    private boolean vaildatorDataIsNull() {
    	 CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo = (CustomsEnvelopCommodityInfo) tableModel
				.getCurrentRow();
    	 
        if (this.tfOwnerQuantity.getText().trim().equals("")
                || this.tfOwnerQuantity.getText().trim().equals("0")) {
            JOptionPane.showMessageDialog(null, "数量不可为空", "警告",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        double d = 0.0;
        if(null!=customsEnvelopCommodityInfo){
        	if(null != customsEnvelopCommodityInfo.getOwnerQuantity()){
        		d = customsEnvelopCommodityInfo.getOwnerQuantity();
        	}
        }
        
        TransParameterSet parameterSet = transferFactoryManageAction
		.findTransParameterSet(new Request(CommonVars.getCurrUser(),
				true));
        // 限制类商品一定要进行数量卡控，restirictCommodity
        RestirictCommodity restirictCommodity = manualDeclareAction
				.findRerictCommodity(
						new Request(CommonVars.getCurrUser(), true),
						customsEnvelopBill.getIsImpExpGoods(),
						customsEnvelopCommodityInfo.getSeqNum().toString());
		if (restirictCommodity != null) {
			if ((restirictCommodity.getAmount() + d < Double
					.valueOf(this.tfOwnerQuantity.getValue().toString()))) {
				JOptionPane.showMessageDialog(null, "限制类商品的数量不可以大于限制数量！", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}

		// 电子账册不进行数量卡控
		if(ProjectType.BCUS == customsEnvelopBill.getProjectType()) {
			return false;
		}

		if (null != parameterSet && null != parameterSet.getIsLimit()) {
			if (parameterSet.getIsLimit()
					&& (contractRemainUse + d < Double
							.valueOf(this.tfOwnerQuantity.getValue().toString()))) {
				if (JOptionPane.showConfirmDialog(
						DgCustomsEnvelopCommodityInfo.this, "数量是否超量输入！", "提示",
						0) != 0) {
					return true;
				} else {
					return false;
				}
			} else {
				if (null != tfOwnerQuantity.getValue()) {
					if ((contractRemainUse + d < Double
							.valueOf(this.tfOwnerQuantity.getValue().toString()))) {
						JOptionPane.showMessageDialog(null, "可使用数量不可大于合同余量！",
								"警告", JOptionPane.INFORMATION_MESSAGE);
						return true;
					}
				}
			}
		} else {
			if (null != tfOwnerQuantity.getValue()) {
				if ((contractRemainUse + d < Double
						.valueOf(this.tfOwnerQuantity.getValue().toString()))) {
					JOptionPane.showMessageDialog(null, "可使用数量不可大于合同余量！", "警告",
							JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
			}
		}
        
       
        return false;
    }

    /**
	 * 统计数据
	 */
    private String getAmountStr() {
        String amountStr = "0";
        try {
            double amount = Double.parseDouble(tfOwnerQuantity.getValue()
                    .toString())
                    * Double.parseDouble(tfUnitPrice.getValue().toString());
            BigDecimal bd = new BigDecimal(amount);
            amountStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception ex) {
        }
        return amountStr;
    }

    /**
     * 文档事件监听类
     */
    class DocumentListenerAdapter implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            tfAmount.setText(getAmountStr());
        }

        public void removeUpdate(DocumentEvent e) {
            tfAmount.setText(getAmountStr());
        }

        public void changedUpdate(DocumentEvent e) {
            tfAmount.setText(getAmountStr());
        }

    }

    /**
     * 初始化组件
     */
    @SuppressWarnings("unchecked")
	private void initUIComponents() {
    	this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCurr,
				"code", "name", 200);
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 150));
		
        //对方公司商品信息单位
        this.cbbOppositeUnit.setModel(CustomBaseModel.getInstance()
                .getUnitModel());
        this.cbbOppositeUnit.setRenderer(CustomBaseRender.getInstance()
                .getRender());
        CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
                this.cbbOppositeUnit);
        
        //工厂单位
		//List listunit = materialManageAction.findCalUnit(new Request(CommonVars
		//		.getCurrUser()));
        
		//重量单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);
		
		//this.cbbUnit.setModel(new DefaultComboBoxModel(listunit.toArray()));
		//this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender(
		//				"code", "name"));
		
		cbbUnit.setEnabled(false);
		
    }

    /**
     * This method initializes defaultFormatterFactory1
     * 
     * @return javax.swing.text.DefaultFormatterFactory
     */
    private DefaultFormatterFactory getDefaultFormatterFactory1() {
        if (defaultFormatterFactory1 == null) {
            defaultFormatterFactory1 = new DefaultFormatterFactory();
            defaultFormatterFactory1.setDefaultFormatter(getNumberFormatter());
            defaultFormatterFactory1.setDisplayFormatter(getNumberFormatter());
            defaultFormatterFactory1.setEditFormatter(getNumberFormatter());
        }
        return defaultFormatterFactory1;
    }

    /**
     * This method initializes numberFormatter
     * 
     * @return javax.swing.text.NumberFormatter
     */
    private NumberFormatter getNumberFormatter() {
        if (numberFormatter == null) {
            numberFormatter = new NumberFormatter();
        }
        return numberFormatter;
    }

    /**
     * This method initializes tfOppositeQuantity
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTfOppositeQuantity() {
        if (tfOppositeQuantity == null) {
            tfOppositeQuantity = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
            tfOppositeQuantity
                    .setFormatterFactory(getDefaultFormatterFactory1());
            tfOppositeQuantity.setBounds(86, 96, 120, 22);
        }
        return tfOppositeQuantity;
    }

    /**
     * This method initializes tfOppositeEmsSerialNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfOppositeEmsSerialNo() {
        if (tfOppositeEmsSerialNo == null) {
            tfOppositeEmsSerialNo = new JTextField();
            tfOppositeEmsSerialNo.setBounds(86, 23, 120, 22);
        }
        return tfOppositeEmsSerialNo;
    }

    /**
     * This method initializes tfOppositeEmsNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfOppositeEmsNo() {
        if (tfOppositeEmsNo == null) {
            tfOppositeEmsNo = new JTextField();
            tfOppositeEmsNo.setBounds(302, 23, 128, 21);
        }
        return tfOppositeEmsNo;
    }

    /**
     * This method initializes tfEmsSerialNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfEmsSerialNo() {
        if (tfEmsSerialNo == null) {
            tfEmsSerialNo = new JTextField();
            tfEmsSerialNo.setEditable(false);
            tfEmsSerialNo.setBounds(105, 42, 131, 23);
        }
        return tfEmsSerialNo;
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
            tfEmsNo.setBounds(324, 41, 130, 22);
        }
        return tfEmsNo;
    }

	/**
	 * This method initializes cbbCurr	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(325, 117, 130, 22));
		}
		return cbbCurr;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
