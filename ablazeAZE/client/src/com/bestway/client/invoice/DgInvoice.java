/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.invoice;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.invoice.entity.Invoice;
import com.bestway.invoice.entity.TempCustomsDelcarationInfo;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgInvoice extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfVersionCode = null;

	private JLabel jLabel1 = null;

	private JTextField tfInvoiceCode = null;

	private JLabel jLabel2 = null;

	private JTextField tfCustomsDeclarationCode = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JCustomFormattedTextField tfMoney = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnFind = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private Invoice invoice = null;

	private InvoiceAction invoiceAction = null;
	
	private Integer projectType;// 报关单来源 ：bcus;bcs;dzba
	
	private String customsDeclarationId; // 报关单ID号
	
	private int dataState=DataState.BROWSE;

	/**
	 * This is the default constructor
	 */
	public DgInvoice() {
		super();
		initialize();
		invoiceAction = (InvoiceAction) CommonVars.getApplicationContext()
				.getBean("invoiceAction");

	}
	public void setVisible(boolean b){
		if(b){
			if(invoice!=null){
				showData(invoice);
				setState();
			}
		}
		super.setVisible(b);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("出口专用发票管理");
		this.setSize(399, 310);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(54, 32, 71, 24);
			jLabel.setText("版次号：");
			jLabel1.setBounds(54, 62, 71, 24);
			jLabel1.setText("发票号：");
			jLabel2.setBounds(54, 92, 71, 24);
			jLabel2.setText("报关单号：");
			jLabel3.setBounds(54, 121, 71, 24);
			jLabel3.setText("金额：");
			jLabel4.setBounds(54, 150, 71, 24);
			jLabel4.setText("录入日期：");
			jLabel5.setBounds(54, 180, 71, 24);
			jLabel5.setText("截止日期：");
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfVersionCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfInvoiceCode(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfCustomsDeclarationCode(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfMoney(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getBtnFind(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
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
			tfVersionCode.setBounds(129, 32, 206, 24);
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
			tfInvoiceCode.setBounds(129, 62, 206, 24);
			tfInvoiceCode.setEditable(false);
			tfInvoiceCode.setBackground(java.awt.Color.white);
		}
		return tfInvoiceCode;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(129, 92, 179, 24);
			tfCustomsDeclarationCode.setEditable(false);
			tfCustomsDeclarationCode.setBackground(java.awt.Color.white);
		}
		return tfCustomsDeclarationCode;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfMoney() {
		if (tfMoney == null) {
			tfMoney = new JCustomFormattedTextField();
			tfMoney.setBounds(129, 121, 206, 24);
			tfMoney.setFormatterFactory(getDefaultFormatterFactory());
			tfMoney.setEditable(true);
			tfMoney.setBackground(java.awt.Color.white);
			tfMoney.setText("");
		}
		return tfMoney;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(129, 150, 206, 24);
			cbbBeginDate.addChangeListener(new javax.swing.event.ChangeListener() { 
				public void stateChanged(javax.swing.event.ChangeEvent e) {    
					Date date= cbbBeginDate.getDate();
					if(date==null){
						cbbEndDate.setValue(null);
					}else{
						GregorianCalendar worldTour = new GregorianCalendar();
						worldTour.setTime(date);
						worldTour.add(GregorianCalendar.DATE, 180);
						cbbEndDate.setValue(worldTour.getTime());
					}
				}
			});
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(129, 180, 206, 24);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton();
			btnFind.setBounds(308, 92, 26, 23);
			btnFind.setText("...");
			btnFind.setEnabled(false);
			btnFind.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					TempCustomsDelcarationInfo temp=(TempCustomsDelcarationInfo)InvoiceQuery.getInstance()
					.findExportCustomsDeclaration(invoice);
					if(temp!=null){
						if(temp.getCustomsDeclarationCode()!=null){
							tfCustomsDeclarationCode.setText(temp.getCustomsDeclarationCode());
						}else{
							tfCustomsDeclarationCode.setText("");
						}
						tfMoney.setValue(temp.getMoney());
						projectType=temp.getProjectType();
						customsDeclarationId=temp.getCustomsDeclarationId();
					}
				}
			});
		}
		return btnFind;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(54, 227, 108, 27);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (invoice == null) {
						return;
					}
					fillData(invoice);
					invoice = invoiceAction.saveInvoice(new Request(CommonVars
							.getCurrUser()), invoice);
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(227, 227, 108, 27);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void showData(Invoice invoice) {
		tfVersionCode.setText(invoice.getVersionCode());
		tfInvoiceCode.setText(invoice.getInvoiceCode());
		if (invoice.getCustomsDeclaredCode() != null) {
			tfCustomsDeclarationCode.setText(invoice.getCustomsDeclaredCode());
		}
		tfMoney.setValue(invoice.getMoney());
		cbbBeginDate.setDate(invoice.getBeginDate());
		cbbEndDate.setDate(invoice.getEndDate());
		this.projectType=invoice.getProjectType();
	}

	private void fillData(Invoice invoice) {
		invoice.setCustomsDeclaredCode(tfCustomsDeclarationCode.getText());
		if (tfMoney.getValue() != null) {
			invoice.setMoney(Double.valueOf(tfMoney.getValue().toString()));
		} else {
			invoice.setMoney(0.0);
		}
		invoice.setBeginDate(cbbBeginDate.getDate());
		invoice.setEndDate(cbbEndDate.getDate());
		invoice.setProjectType(this.projectType);
		invoice.setCustomsDeclarationId(this.customsDeclarationId);
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
	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}
	/**
	 * @param dataState The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}
	
	private void setState(){
		this.tfCustomsDeclarationCode.setEnabled(dataState!=DataState.BROWSE);
		this.tfMoney.setEnabled(dataState!=DataState.BROWSE);
		this.cbbBeginDate.setEnabled(dataState!=DataState.BROWSE);
		this.cbbEndDate.setEnabled(dataState!=DataState.BROWSE);
		//this.btnFind.setEnabled(dataState!=DataState.BROWSE);
		this.btnOk.setEnabled(dataState!=DataState.BROWSE);
		this.btnCancel.setEnabled(dataState!=DataState.BROWSE);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
