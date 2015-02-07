/*
 * Created on 2005-7-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.message;

import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JComboBox;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscExportFileName extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfFileName = null;

	private String fileName;  //  @jve:decl-index=0:

	private JButton btnOk = null;
	private JButton btnCancel = null;
	
	private boolean isOk=false;
	private JLabel jLabel1 = null;
	private JComboBox jComboBox = null;
	/**
	 * This is the default constructor
	 */
	public DgDzscExportFileName() {
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
		this.setTitle("导出文件名称");
		this.setSize(429, 199);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(20, 36, 62, 19);
			jLabel.setText("文件名：");
			jLabel.setForeground(java.awt.Color.black);
			jLabel1.setBounds(20, 69, 62, 21);
			jLabel1.setText("文件类型：");
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfFileName(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJComboBox(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFileName() {
		if (tfFileName == null) {
			tfFileName = new JTextField();
			tfFileName.setBounds(82, 34, 314, 25);
			tfFileName.setText("");
			tfFileName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		}
		return tfFileName;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
		tfFileName.setText(fileName);
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(230, 112, 73, 29);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					isOk=true;
					fileName=tfFileName.getText()+".xml";
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
			btnCancel.setBounds(319, 112, 73, 29);
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
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}
	/**
	 * @param isOk The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(82, 67, 314, 26);
			jComboBox.addItem(".xml");
			jComboBox.setSelectedIndex(0);
		}
		return jComboBox;
	}
 } // @jve:decl-index=0:visual-constraint="10,10"
