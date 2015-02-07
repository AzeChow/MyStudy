package com.bestway.bcus.client.common;

import java.awt.Dialog;

import javax.swing.JFrame;

import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class DgMultiConditionQuery extends JDialogBase {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable tbCondition = null;
	private JButton btnAdd = null;
	private JButton btnDelete = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;

	public DgMultiConditionQuery() {
		super();
		initialize();
	}

	public DgMultiConditionQuery(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgMultiConditionQuery(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgMultiConditionQuery(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(541,377));
        this.setTitle("多条件查询");
        this.setContentPane(getJContentPane());
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			
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
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getBtnAdd(), null);
			jContentPane.add(getBtnDelete(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(16,17,421,311));
			jScrollPane.setViewportView(getTbCondition());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTbCondition() {
		if (tbCondition == null) {
			tbCondition = new JTable();
		}
		return tbCondition;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setBounds(new java.awt.Rectangle(442,20,80,26));
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // 
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(new java.awt.Rectangle(442,57,80,26));
			btnDelete.setText("移除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // 
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(442,199,80,26));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // 
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
			btnCancel.setBounds(new java.awt.Rectangle(442,237,80,26));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // 
				}
			});
		}
		return btnCancel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
