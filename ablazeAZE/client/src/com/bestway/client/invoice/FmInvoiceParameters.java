package com.bestway.client.invoice;

import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.invoice.entity.InvoiceParameters;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;
import javax.swing.JRadioButton;

/**
 * 发票参数设置
 * @author chensir
 * check 2008-8-30
 */
public class FmInvoiceParameters extends JInternalFrameBase {
	/**
	 * 发票action
	 */
	private InvoiceAction invoiceAction;
	
	/**
	 * 状态
	 */
	private int dataState = DataState.BROWSE;

	private JPanel jContentPane = null;	

	private JSeparator jSeparator = null;

	/**
	 * 编辑按钮
	 */
	private JButton btnEdit = null;

	/**
	 * 保存按钮
	 */
	private JButton btnSave = null;

	/**
	 * 关闭按钮
	 */
	private JButton btnClose = null;

	/**
	 * 单选按钮---发票号前是否加上年份(两位)	
	 */
	private JRadioButton jRadioButton = null;

	public FmInvoiceParameters() {
		super();
		initialize();
		invoiceAction = (InvoiceAction) CommonVars.getApplicationContext().getBean(
				"invoiceAction");
		InvoiceParameters invoiceParameters = invoiceAction
				.findInvoiceParameters(new Request(CommonVars.getCurrUser()));
		showData(invoiceParameters);
		setState();
	}
	
	/**
	 * 把数据库的参数显示到界面
	 * @param invoiceParameters
	 */
	private void showData(InvoiceParameters invoiceParameters){
		if(invoiceParameters==null){
			return;
		}
		this.getJRadioButton().setSelected(
		    invoiceParameters.getIsInvoiceWithyear()?true:false		
		);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(725, 435));
		this.setContentPane(getJContentPane());
		this.setTitle("参数设置");
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
			jContentPane.add(getJSeparator(), null);
			jContentPane.add(getBtnEdit(), null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnClose(), null);
			jContentPane.add(getJRadioButton(), null);
		}
		return jContentPane;
	}

	

	
	/**
	 * This method initializes jSeparator
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
			jSeparator.setBounds(new java.awt.Rectangle(39, 335, 644, 8));
		}
		return jSeparator;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new java.awt.Rectangle(352, 346, 67, 27));
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new java.awt.Rectangle(449, 346, 67, 27));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					InvoiceParameters invoiceParameters = invoiceAction
							.findInvoiceParameters(new Request(CommonVars
									.getCurrUser(), true));
					fillData(invoiceParameters);
					invoiceAction.saveInvoiceParameters(new Request(CommonVars
							.getCurrUser()), invoiceParameters);
					dataState = DataState.BROWSE;
					setState();
				}				
			});
		}
		return btnSave;
	}
	
	/**
	 * 把当前的参数保存到数据库
	 * @param invoiceParameters
	 */
	private void fillData(InvoiceParameters invoiceParameters) {
		// TODO Auto-generated method stub
		if(invoiceParameters==null)
			return;
		invoiceParameters.setIsInvoiceWithyear(
				this.getJRadioButton().isSelected());
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new java.awt.Rectangle(545, 346, 67, 27));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmInvoiceParameters.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	protected void paintComponent(Graphics g) {
		jSeparator.setBounds(3, this.jContentPane.getHeight() - 50,
				this.jContentPane.getWidth() - 6, 3);
		// // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
		// this.jPanel1.getWidth() - 28 -28, 3);
		btnEdit.setBounds(this.jContentPane.getWidth() - 392, this.jContentPane
				.getHeight() - 40, 68, 26);
		btnSave.setBounds(this.jContentPane.getWidth() - 292, this.jContentPane
				.getHeight() - 40, 68, 26);
		btnClose.setBounds(this.jContentPane.getWidth() - 192,
				this.jContentPane.getHeight() - 40, 68, 26);
		super.paintComponent(g);
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(65, 49, 134, 36));
			jRadioButton.setText("发票号前加上年号");
		}
		return jRadioButton;
	}
	
	/**
	 * 根据当前状态设置各组件的可编辑状态
	 */
	private void setState(){
		this.jRadioButton.setEnabled(dataState != DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.jRadioButton.setEnabled(dataState != DataState.BROWSE);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
