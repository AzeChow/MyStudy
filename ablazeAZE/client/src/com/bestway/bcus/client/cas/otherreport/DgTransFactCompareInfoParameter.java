package com.bestway.bcus.client.cas.otherreport;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DgTransFactCompareInfoParameter extends JDialogBase {

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField tfLinkMan = null;
	private JTextField tfTel = null;
	private JTextField tfFax = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private boolean isOK=false;

	public boolean isOK() {
		return isOK;
	}

	/**
	 * This method initializes 
	 * 
	 */
	public DgTransFactCompareInfoParameter() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(346, 216));
        this.setContentPane(getJContentPane());
			
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
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
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
			jLabel2 = new JLabel();
			
			jLabel2.setBounds(new Rectangle(26, 96, 54, 21));
			jLabel2.setText("FAX:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(26, 63, 54, 21));
			jLabel1.setText("TEL:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(26, 32, 54, 21));
			jLabel.setText("联系人： ");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfLinkMan(), null);
			jPanel.add(getTfTel(), null);
			jPanel.add(getTfFax(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfLinkMan() {
		if (tfLinkMan == null) {
			tfLinkMan = new JTextField();
			tfLinkMan.setBounds(new Rectangle(82, 30, 219, 25));
		}
		return tfLinkMan;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfTel() {
		if (tfTel == null) {
			tfTel = new JTextField();
			tfTel.setBounds(new Rectangle(82, 61, 218, 25));
		}
		return tfTel;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfFax() {
		if (tfFax == null) {
			tfFax = new JTextField();
			tfFax.setBounds(new Rectangle(83, 94, 217, 25));
		}
		return tfFax;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(76, 143, 72, 24));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOK=true;
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(199, 143, 72, 24));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}
	
	public String getLinkMan(){
		return this.tfLinkMan.getText().trim();
	}
	
	public String getTel(){
		return this.tfTel.getText().trim();
	}
	
	public String getFax(){
		return this.tfFax.getText().trim();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
