/*
 * Created on 2005-7-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.invoice;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.invoice.entity.Invoice;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgMemo extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfVersionCode = null;

	private JLabel jLabel1 = null;

	private JTextField tfInvoiceCode = null;

	private JLabel jLabel2 = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JTextArea tfMemo = null; // @jve:decl-index=0:visual-constraint="412,131"

	private JScrollPane jScrollPane = null;

	private Invoice invoice = null;

	private InvoiceAction invoiceAction = null;

	/**
	 * This is the default constructor
	 */
	public DgMemo() {
		super();
		initialize();
		invoiceAction = (InvoiceAction) CommonVars.getApplicationContext()
		.getBean("invoiceAction");
	}
	public void setVisible(boolean b){
		if(b){
			this.showData(invoice);
		}
		super.setVisible(true);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("请输入作废原因");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(364, 251);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(33, 25, 80, 20);
			jLabel.setText("版次号:");
			jLabel1.setBounds(33, 56, 80, 20);
			jLabel1.setText("发票号:");
			jLabel2.setBounds(33, 85, 80, 20);
			jLabel2.setText("发票作废原因:");
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfVersionCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfInvoiceCode(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getJScrollPane(), null);
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
			tfVersionCode.setBounds(116, 25, 203, 25);
			tfVersionCode.setEditable(false);
			tfVersionCode.setBackground(java.awt.Color.white);
		}
		return tfVersionCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceCode() {
		if (tfInvoiceCode == null) {
			tfInvoiceCode = new JTextField();
			tfInvoiceCode.setBounds(116, 54, 203, 25);
			tfInvoiceCode.setEditable(false);
			tfInvoiceCode.setBackground(java.awt.Color.white);
		}
		return tfInvoiceCode;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(45, 175, 86, 27);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(invoice==null){
						return;
					}
					fillData(invoice);
					invoice=invoiceAction.cancelInvoice(new Request(CommonVars.getCurrUser()),invoice);
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
			btnCancel.setBounds(218, 175, 86, 27);
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
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextArea();
		}
		return tfMemo;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(116, 85, 203, 80);
			jScrollPane.setViewportView(getTfMemo());
		}
		return jScrollPane;
	}

	/**
	 * @return Returns the invoice.
	 */
	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * @param invoice
	 *            The invoice to set.
	 */
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	private void showData(Invoice invoice){
		if(invoice==null){
			return;
		}
		this.tfVersionCode.setText(invoice.getVersionCode());
		this.tfInvoiceCode.setText(invoice.getInvoiceCode());
		this.tfMemo.setText(invoice.getMemo());
	}
	
	private void fillData(Invoice invoice){
		if(invoice==null){
			return;
		}
		invoice.setMemo(this.tfMemo.getText());
	}
} // @jve:decl-index=0:visual-constraint="10,10"
