/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.invoice;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.invoice.entity.InvoiceParameters;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Dimension;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgAutoMakeInvoice extends JDialogBase {

    private javax.swing.JPanel        jContentPane            = null;

    private JLabel                    jLabel                  = null;

    private JTextField                tfVersionCode           = null;

    private JLabel                    jLabel1                 = null;

    private JLabel                    jLabel2                 = null;

    private JTextField                tfBeginSerialNo         = null;

    private JTextField                tfEndSerialNo           = null;

    private JButton                   btnOk                   = null;

    private JButton                   btnCancel               = null;

    private DefaultFormatterFactory   defaultFormatterFactory = null; // @jve:decl-index=0:

    private NumberFormatter           numberFormatter         = null; // @jve:decl-index=0:

    private List                      lsResult                = null;

    private InvoiceAction             invoiceAction           = null;

    private JLabel                    jLabel3                 = null;

    private JCustomFormattedTextField tfAmount                = null;

    /**
     * This is the default constructor
     */
    public DgAutoMakeInvoice() {
        super();
        initialize();
        invoiceAction = (InvoiceAction) CommonVars.getApplicationContext()
                .getBean("invoiceAction");
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("自动产生发票");
        this.setSize(331, 222);
        this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jLabel3 = new JLabel();
            jLabel3.setBounds(new Rectangle(66, 90, 38, 22));
            jLabel3.setText("份数:");
            jLabel2 = new JLabel();
            jLabel1 = new JLabel();
            jLabel = new JLabel();
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(null);
            jLabel.setBounds(21, 29, 84, 24);
            jLabel.setText("版次号(12位):");
            jLabel1.setBounds(25, 59, 80, 24);
            jLabel1.setText("发票始号(8位):");
            jLabel2.setBounds(21, 120, 84, 24);
            jLabel2.setText("发票末号(8位):");
            jContentPane.add(jLabel, null);
            jContentPane.add(getTfVersionCode(), null);
            jContentPane.add(jLabel1, null);
            jContentPane.add(jLabel2, null);
            jContentPane.add(getTfBeginSerialNo(), null);
            jContentPane.add(getTfEndSerialNo(), null);
            jContentPane.add(getBtnOk(), null);
            jContentPane.add(getBtnCancel(), null);
            jContentPane.add(jLabel3, null);
            jContentPane.add(getTfAmount(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfVersionCode() {
        if (tfVersionCode == null) {
            tfVersionCode = new JTextField();
            tfVersionCode.setBounds(105, 29, 197, 24);
        }
        return tfVersionCode;
    }

    /**
     * This method initializes jCustomFormattedTextField
     * 
     * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
     */
    private JTextField getTfBeginSerialNo() {
        if (tfBeginSerialNo == null) {
            tfBeginSerialNo = new JTextField();
            tfBeginSerialNo.setBounds(105, 60, 197, 24);
            // tfBeginSerialNo.setFormatterFactory(getDefaultFormatterFactory());
        }
        return tfBeginSerialNo;
    }

    /**
     * This method initializes jCustomFormattedTextField1
     * 
     * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
     */
    private JTextField getTfEndSerialNo() {
        if (tfEndSerialNo == null) {
            tfEndSerialNo = new JTextField();
            tfEndSerialNo.setBounds(105, 120, 197, 24);
            // tfEndSerialNo.setFormatterFactory(getDefaultFormatterFactory());
            tfEndSerialNo.setText("");
        }
        return tfEndSerialNo;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnOk() {
        if (btnOk == null) {
            btnOk = new JButton();
            btnOk.setBounds(53, 154, 82, 26);
            btnOk.setText("确定");
            btnOk.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (tfVersionCode.getText().length() != 12) {
                        JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,
                                "发票的版次号要等于12位,请检查输入", "提示",
                                JOptionPane.OK_OPTION);
                        return;
                    }
                    if (tfBeginSerialNo.getText().length() != 8) {
                        JOptionPane
                                .showMessageDialog(DgAutoMakeInvoice.this,
                                        "发票开始号要等于8位,请检查输入", "提示",
                                        JOptionPane.OK_OPTION);
                        return;
                    }
                    if (tfEndSerialNo.getText().length() != 8) {
                        JOptionPane
                                .showMessageDialog(DgAutoMakeInvoice.this,
                                        "发票结束号要等于8位,请检查输入", "提示",
                                        JOptionPane.OK_OPTION);
                        return;
                    }
                    // if (tfBeginSerialNo.getValue() == null
                    // || "".equals(tfBeginSerialNo.getValue().toString())) {
                    // JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,
                    // "发票开始流水号不能为空,请检查输入", "提示",
                    // JOptionPane.OK_OPTION);
                    // return;
                    // }
                    // if (tfEndSerialNo.getValue() == null
                    // || "".equals(tfEndSerialNo.getValue().toString())) {
                    // JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,
                    // "发票结束流水号不能为空,请检查输入", "提示",
                    // JOptionPane.OK_OPTION);
                    // return;
                    // }
                    String versionCode = tfVersionCode.getText();
                    try {
                        Integer.valueOf(tfBeginSerialNo.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,
                                "发票开始流水号不是有效的流水号,请检查输入", "提示",
                                JOptionPane.OK_OPTION);
                        return;
                    }
                    try {
                        Integer.valueOf(tfEndSerialNo.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,
                                "发票结束流水号不是有效的流水号,请检查输入", "提示",
                                JOptionPane.OK_OPTION);
                        return;
                    }   
                    InvoiceParameters invoiceParameters = invoiceAction.findInvoiceParameters(new Request(CommonVars.getCurrUser()));
                    Calendar c=Calendar.getInstance();
                    String nowYear=String.valueOf(c.get(Calendar.YEAR)).substring(2);
                    String year=invoiceParameters.getIsInvoiceWithyear().booleanValue()==true?nowYear:"";
                    lsResult = invoiceAction.autoMakeInvoice(new Request(
                            CommonVars.getCurrUser()), versionCode,
                            tfBeginSerialNo.getText(), tfEndSerialNo.getText(),year);                    
                    dispose();
                }
            });
        }
        return btnOk;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setBounds(191, 154, 82, 26);
            btnCancel.setText("取消");
            btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return btnCancel;
    }

    /**
     * This method initializes defaultFormatterFactory
     * 
     * @return javax.swing.text.DefaultFormatterFactory
     */
    private DefaultFormatterFactory getDefaultFormatterFactory() {
        if (defaultFormatterFactory == null) {
            defaultFormatterFactory = new DefaultFormatterFactory();
            defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
            defaultFormatterFactory.setEditFormatter(getNumberFormatter());
            defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
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

    /**
     * @return Returns the lsResult.
     */
    public List getLsResult() {
        return lsResult;
    }

    /**
     * @param lsResult
     *            The lsResult to set.
     */
    public void setLsResult(List lsResult) {
        this.lsResult = lsResult;
    }

    /**
     * This method initializes tfAmount
     * 
     * @return javax.swing.JTextField
     */
    private JCustomFormattedTextField getTfAmount() {
        if (tfAmount == null) {
            tfAmount = new JCustomFormattedTextField();
            tfAmount.setBounds(new Rectangle(105, 90, 197, 24));
            tfAmount.getDocument().addDocumentListener(new DocumentListener() {

                public void insertUpdate(DocumentEvent e) {
                    setValue();
                }

                public void removeUpdate(DocumentEvent e) {
                    setValue();
                }

                public void changedUpdate(DocumentEvent e) {
                    setValue();
                }

                private void setValue() {
                    try {
                        tfAmount.commitEdit();
                    } catch (Exception ex) {

                    }
                    Integer beginSerialNo = 0;
                    String endSerialNo = "";
                    try {
                        beginSerialNo = Integer.valueOf(tfBeginSerialNo
                                .getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DgAutoMakeInvoice.this,
                                "发票开始流水号不是有效的流水号,请检查输入", "提示",
                                JOptionPane.OK_OPTION);
                        return;
                    }
                    Integer number = Double.valueOf(tfAmount.getValue()
                            .toString()).intValue();
                    endSerialNo = CommonUtils.convertIntToStringByLength(
                            beginSerialNo + number, 8);
                    tfEndSerialNo.setText(endSerialNo);
                }
            });
        }
        return tfAmount;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
