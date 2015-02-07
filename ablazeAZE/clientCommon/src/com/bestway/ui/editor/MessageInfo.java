package com.bestway.ui.editor;


import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class MessageInfo extends JDialog {

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JTextArea txMessage = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	/**
	 * This method initializes 
	 * 
	 */
	public MessageInfo() {
		super();
		initialize();
		this.setVisible(true);
	}
	
	
	public MessageInfo(Dialog owner, boolean modal,String title) {
		super(owner);
		// TODO Auto-generated constructor stub		
		owner.setEnabled(!modal);
		initialize();
		this.setTitle(title);
		this.setVisible(true);
		
	}


	public MessageInfo(Frame owner, boolean modal,String title) {
		super(owner);
		// TODO Auto-generated constructor stub
		owner.setEnabled(!modal);
		initialize();
		this.setTitle(title);
		this.setVisible(true);
		
	}
	
	public MessageInfo(JInternalFrame owner, boolean modal,String title) {		
		// TODO Auto-generated constructor stub
		owner.setEnabled(!modal);
		initialize();
		this.setTitle(title);
		this.setVisible(true);
		
	}


	public MessageInfo(Window owner,String title) {
		super(owner);
		// TODO Auto-generated constructor stub		
		initialize();
		this.setTitle(title);
		this.setVisible(true);
	}


	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(437, 302));
        this.setContentPane(getJPanel());
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout());
			jPanel2.add(getJButton(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes txMessage	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTxMessage() {
		if (txMessage == null) {
			txMessage = new JTextArea();
			txMessage.setAutoscrolls(true);
			txMessage.setLineWrap(true);
		}
		return txMessage;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				     MessageInfo.this.dispose();
				}
			});
			jButton.setText("确定");
		}
		return jButton;
	}
	
	public void addMessage(String str){
		txMessage.append(str);
		
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTxMessage());
		}
		return jScrollPane;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
