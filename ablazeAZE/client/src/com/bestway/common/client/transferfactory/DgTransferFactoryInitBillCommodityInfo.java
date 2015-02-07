/*
 * Created on 2004-10-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBillCommodityInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgTransferFactoryInitBillCommodityInfo extends JDialogBase {

    private JPanel                               jContentPane                = null;
    private JToolBar                             jToolBar                    = null;
    private JPanel                               jPanel                      = null;
    private JButton                              btnEdit                     = null;
    private JButton                              btnSave                     = null;
    private JButton                              btnUndo                     = null;
    private JButton                              btnExit                     = null;
    private JTextField                           tfMaterialCode              = null;
    private JTextField                           tfMaterialName              = null;
    private JTextField                           tfMaterialSpec              = null;
    private JTextField                           tfUnit                      = null;
    private JTextField                           tfMemo                      = null;
    private JTableListModel                      tableModel                  = null;
    private TransferFactoryInitBillCommodityInfo commInfo                    = null;
    private JFormattedTextField                  tfNoTransferQuantity        = null;
    private int                                  dataState                   = DataState.BROWSE;
    private TransferFactoryManageAction          transferFactoryManageAction = null;
    private DefaultFormatterFactory              defaultFormatterFactory     = null;            //  @jve:decl-index=0:parse
    private NumberFormatter                      numberFormatter             = null;   
    private boolean isEffective=false;//  @jve:decl-index=0:parse

    /**
     * @return Returns the isEffective.
     */
    public boolean isEffective() {
        return isEffective;
    }
    /**
     * @param isEffective The isEffective to set.
     */
    public void setEffective(boolean isEffective) {
        this.isEffective = isEffective;
    }
    /**
     * This method initializes
     *  
     */
    public DgTransferFactoryInitBillCommodityInfo() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("[进出货转厂期初单]商品信息维护");
        this.setContentPane(getJContentPane());
        this.setSize(458, 259);
        //        this.addWindowListener(new java.awt.event.WindowAdapter() {
        //            public void windowOpened(java.awt.event.WindowEvent e) {
        //
        //            }
        //        });
        transferFactoryManageAction = (TransferFactoryManageAction) CommonVars
                .getApplicationContext().getBean("transferFactoryManageAction");
    }

    @Override
	public void setVisible(boolean b) {
        if (b) {
            commInfo = (TransferFactoryInitBillCommodityInfo) tableModel
                    .getCurrentRow();
            showData();
            setState();
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
            jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
            jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
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
            jToolBar.add(getBtnEdit());
            jToolBar.add(getBtnSave());
            jToolBar.add(getBtnUndo());
            jToolBar.add(getBtnExit());
        }
        return jToolBar;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            javax.swing.JLabel jLabel5 = new JLabel();
            javax.swing.JLabel jLabel4 = new JLabel();
            javax.swing.JLabel jLabel3 = new JLabel();
            javax.swing.JLabel jLabel2 = new JLabel();
            javax.swing.JLabel jLabel1 = new JLabel();
            javax.swing.JLabel jLabel = new JLabel();
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jLabel.setBounds(23, 30, 78, 22);
            jLabel.setText("料号");
            jLabel1.setBounds(23, 54, 76, 22);
            jLabel1.setText("型号规格");
            jLabel2.setBounds(23, 84, 75, 22);
            jLabel2.setText("已收/送货数");
            jLabel3.setBounds(23, 112, 75, 22);
            jLabel3.setText("备注");
            jLabel4.setBounds(239, 30, 66, 21);
            jLabel4.setText("物料名称");
            jLabel5.setBounds(240, 58, 63, 21);
            jLabel5.setText("单位");
            jPanel.add(jLabel, null);
            jPanel.add(jLabel1, null);
            jPanel.add(jLabel2, null);
            jPanel.add(jLabel3, null);
            jPanel.add(jLabel4, null);
            jPanel.add(jLabel5, null);
            jPanel.add(getTfMaterialCode(), null);
            jPanel.add(getTfMaterialName(), null);
            jPanel.add(getTfMaterialSpec(), null);
            jPanel.add(getTfUnit(), null);
            jPanel.add(getTfMemo(), null);
            jPanel.add(getTfNoTransferQuantity(), null);
        }
        return jPanel;
    }

    /**
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
                    dataState = DataState.EDIT;
                    setState();
                }
            });
        }
        return btnEdit;
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
                    fillData();
                    commInfo = transferFactoryManageAction
                            .saveTransferFactoryInitBillCommodityInfo(
                                    new Request(CommonVars.getCurrUser()),
                                    commInfo);
                    tableModel.updateRow(commInfo);
                    dataState = DataState.BROWSE;
                    setState();
                    
                }
            });
        }
        return btnSave;
    }

    /**
     * This method initializes btnUndo
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnUndo() {
        if (btnUndo == null) {
            btnUndo = new JButton();
            btnUndo.setText("取消");
            btnUndo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    showData();
                    dataState = DataState.BROWSE;
                    setState();
                }
            });
        }
        return btnUndo;
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
     * This method initializes tfMaterialCode
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMaterialCode() {
        if (tfMaterialCode == null) {
            tfMaterialCode = new JTextField();
            tfMaterialCode.setBounds(115, 30, 120, 21);
            tfMaterialCode.setEditable(false);
        }
        return tfMaterialCode;
    }

    /**
     * This method initializes tfMaterialName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMaterialName() {
        if (tfMaterialName == null) {
            tfMaterialName = new JTextField();
            tfMaterialName.setBounds(306, 31, 119, 21);
            tfMaterialName.setEditable(false);
        }
        return tfMaterialName;
    }

    /**
     * This method initializes tfMaterialSpec
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMaterialSpec() {
        if (tfMaterialSpec == null) {
            tfMaterialSpec = new JTextField();
            tfMaterialSpec.setBounds(115, 56, 120, 21);
            tfMaterialSpec.setEditable(false);
        }
        return tfMaterialSpec;
    }

    /**
     * This method initializes tfUnit
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfUnit() {
        if (tfUnit == null) {
            tfUnit = new JTextField();
            tfUnit.setBounds(306, 59, 119, 21);
            tfUnit.setEditable(false);
        }
        return tfUnit;
    }

    /**
     * This method initializes tfMemo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMemo() {
        if (tfMemo == null) {
            tfMemo = new JTextField();
            tfMemo.setBounds(115, 112, 310, 21);
        }
        return tfMemo;
    }

    /**
     * This method initializes tfNoTransferQuantity
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JFormattedTextField getTfNoTransferQuantity() {
        if (tfNoTransferQuantity == null) {
            tfNoTransferQuantity = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
            tfNoTransferQuantity.setBounds(115, 84, 144, 21);
            tfNoTransferQuantity
                    .setFormatterFactory(getDefaultFormatterFactory());
            tfNoTransferQuantity.setText("0");
        }
        return tfNoTransferQuantity;
    }

    private void showEmptyData() {
        this.tfMaterialCode.setText("");
        this.tfMaterialName.setText("");
        this.tfMaterialSpec.setText("");
        this.tfUnit.setText("");
        this.tfNoTransferQuantity.setValue(new Double(0));
        this.tfMemo.setText("");
    }

    private void showData() {
        if (commInfo == null) {
            showEmptyData();
            return;
        }
        this.tfMaterialCode.setText(commInfo.getComplex().getCode());
        this.tfMaterialName.setText(commInfo.getCommName());
        this.tfMaterialSpec.setText(commInfo.getCommSpec());
        this.tfUnit.setText(commInfo.getUnit()==null?"":commInfo.getUnit().getName());
        this.tfNoTransferQuantity.setValue(commInfo.getNoTransferQuantity());
        this.tfMemo.setText(commInfo.getMemo());
    }

    private void fillData() {
        commInfo
                .setNoTransferQuantity(this.tfNoTransferQuantity.getValue() == null ? new Double(
                        0)
                        : Double.valueOf(this.tfNoTransferQuantity.getValue()
                                .toString()));
        commInfo.setMemo(this.tfMemo.getText());
    }

    private void setState() {
        this.btnEdit.setEnabled(dataState == DataState.BROWSE&&!isEffective);
        this.btnSave.setEnabled(dataState != DataState.BROWSE&&!isEffective);
        this.btnUndo.setEnabled(dataState != DataState.BROWSE&&!isEffective);
        this.btnExit.setEnabled(dataState == DataState.BROWSE&&!isEffective);
        this.tfNoTransferQuantity.setEditable(dataState != DataState.BROWSE&&!isEffective);
        this.tfMemo.setEditable(dataState != DataState.BROWSE&&!isEffective);
    }

    /**
     * This method initializes defaultFormatterFactory
     * 
     * @return javax.swing.text.DefaultFormatterFactory
     */
    private DefaultFormatterFactory getDefaultFormatterFactory() {
        if (defaultFormatterFactory == null) {
            defaultFormatterFactory = new DefaultFormatterFactory();
            defaultFormatterFactory.setEditFormatter(getNumberFormatter());
            defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
        }
        return defaultFormatterFactory;
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
} //  @jve:decl-index=0:visual-constraint="10,10"
