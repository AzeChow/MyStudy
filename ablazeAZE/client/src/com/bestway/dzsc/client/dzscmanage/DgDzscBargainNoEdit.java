/*
 * Created on 2005-3-31
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;


import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDzscBargainNoEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	public boolean isok = false;
	private Integer baraNo = null;
	/**
	 * This is the default constructor
	 */
	public DgDzscBargainNoEdit() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(267, 152);
		this.setContentPane(getJContentPane());
		this.setTitle("新合同序号");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(28, 17, 163, 25);
			jLabel.setText("请输入新合同序号：");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(29, 47, 212, 22);
		}
		return jTextField;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(46, 82, 70, 24);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (isEmpty()){
						return;
					}
					exe(true);
				}
			});
		}
		return jButton;
	}
	private void exe(boolean isok){
		if (isok){
			DgDzscBargainNoEdit.this.setIsok(true);
			DgDzscBargainNoEdit.this.setBaraNo(Integer.valueOf(jTextField.getText().trim()));
		} else {
			DgDzscBargainNoEdit.this.setIsok(false);			
		}
		DgDzscBargainNoEdit.this.dispose();
	}
	private boolean isEmpty(){
		if (jTextField.getText().equals("")){
			JOptionPane.showMessageDialog(this,"序号不能为空!","提示",2);
			return true;
		}
		try{
			Integer.valueOf(jTextField.getText().trim());
		} catch(Exception e){
			JOptionPane.showConfirmDialog(this,"请输入正确的合同序号!","提示",2);
			return true;
		}
		return false;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(142, 81, 70, 24);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					exe(false);
				}
			});
		}
		return jButton1;
	}
	/**
	 * @return Returns the isok.
	 */
	public boolean isIsok() {
		return isok;
	}
	/**
	 * @param isok The isok to set.
	 */
	public void setIsok(boolean isok) {
		this.isok = isok;
	}
	/**
	 * @return Returns the baraNo.
	 */
	public Integer getBaraNo() {
		return baraNo;
	}
	/**
	 * @param baraNo The baraNo to set.
	 */
	public void setBaraNo(Integer baraNo) {
		this.baraNo = baraNo;
	}
   }  //  @jve:decl-index=0:visual-constraint="10,10"
