/*
 * Created on 2004-8-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.Certificate;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCertificate extends JDialogBase {
    private JPanel      jContentPane    = null;
    private JPanel      jPanel          = null;
    private JButton     btnOk           = null;
    private JButton     btnCancel       = null;
    private JButton     btnAttachedBill = null;

    private boolean     ok              = false;
    private Certificate certificate     = null;

    private JTextField  jTextField      = null;
    private JTextField  jTextField1     = null;

    private JLabel      jLabel          = null;
    private JLabel      jLabel1         = null;

    public DgCertificate() {
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setTitle("证件编辑");
        this.setContentPane(getJContentPane());
        this.setSize(350, 187);

    }

    public void setVisible(boolean isFlag) {
        if (isFlag) {
            initComponent();
        }
        super.setVisible(isFlag);
    }

    /**
     * @return Returns the ok.
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * @param ok
     *            The ok to set.
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getBtnOk(), null);
            jContentPane.add(getBtnCancel(), null);
            jContentPane.add(getJPanel(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jLabel1 = new JLabel();
            jLabel = new JLabel();
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "证件代码",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    null, null));
            jPanel.setBounds(17, 11, 308, 102);
            jLabel.setBounds(9, 23, 154, 25);
            jLabel.setText("证件代码及分隔符如(A:或A*)");
            jLabel1.setBounds(9, 54, 89, 25);
            jLabel1.setText("证件号码");
            jPanel.add(getJTextField(), null);
            jPanel.add(getJTextField1(), null);
            jPanel.add(jLabel, null);
            jPanel.add(jLabel1, null);
        }
        return jPanel;
    }

    /**
     * This method initializes btnOk
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnOk() {
        if (btnOk == null) {
            btnOk = new JButton();
            btnOk.setBounds(142, 123, 64, 25);
            btnOk.setText("确定");
            btnOk.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (!validateCertificateCode()) {
                        JOptionPane.showMessageDialog(
                                DgCertificate.this,
                                "证件代码及分隔符填写错误!!!!", "警告!!!",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        setOk(true);
                        Certificate certificate = new Certificate(jTextField
                                .getText(), jTextField1.getText());
                        setCertificate(certificate);
                        DgCertificate.this.dispose();
                    }
                }
            });
        }
        return btnOk;
    }

    /**
     * This method initializes btnCancel
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setBounds(217, 123, 64, 25);
            btnCancel.setText("取消");
            btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    setOk(false);
                    DgCertificate.this.dispose();
                }
            });
        }
        return btnCancel;
    }

    class CustomDocument extends PlainDocument {
        int charQuantity = 0;

        public CustomDocument(int charQuantity) {
            this.charQuantity = charQuantity;
        }

        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str == null) {
                return;
            }
            if (super.getLength() >= charQuantity
                    || str.length() > charQuantity
                    || super.getLength() + str.length() > charQuantity) {
                return;
            }
            super.insertString(offs, str, a);
        }
    }

    private void initComponent() {
        if (certificate != null) {
            this.jTextField.setText(certificate.getCode());
            this.jTextField1.setText(certificate.getName());
        }
    }

    /**
     * This method initializes jTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextField() {
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setToolTipText("如(A:或A*)");
            jTextField.setBounds(165, 23, 129, 25);
        }
        return jTextField;
    }

    /**
     * This method initializes jTextField1
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextField1() {
        if (jTextField1 == null) {
            jTextField1 = new JTextField();
            jTextField1.setBounds(100, 54, 194, 25);
            jTextField1.setDocument(new PlainDocument() {
                public void insertString(int offs, String str, AttributeSet a)
                        throws BadLocationException {
                    if (str == null) {
                        return;
                    }
                    if (jTextField1.getText().getBytes().length >= 32
                            || str.getBytes().length > 32
                            || jTextField1.getText().getBytes().length
                                    + str.getBytes().length > 32) {
                        return;
                    }
                    super.insertString(offs, str, a);
                }
            });
            jTextField1.setToolTipText("证件号码 <=32 位");
        }
        return jTextField1;
    }

    /**
     * @return Returns the certificate.
     */
    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * @param certificate
     *            The certificate to set.
     */
    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    /**
     * 验证证件代码
     * 
     * @return
     */
    private boolean validateCertificateCode() {
        return true;
//        boolean isValidate = false;
//        if (this.jTextField.getText().trim().equals("A*")
//                || this.jTextField.getText().trim().equals("A:")) {
//            isValidate = true;
//        }
//        return isValidate;
    }
    
    
    

} // @jve:decl-index=0:visual-constraint="34,72"
