package com.bestway.bcus.client.manualdeclare;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgEmsHeadH2kFasDeclare extends JDialogBase {

	private int dataState = DataState.BROWSE;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTextField jTextField = null;
	private JLabel jLabel = null;
	private JTableListModel tableModel = null;
	
	
	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kFasDeclare() {
		super();
		initialize();
	}
	
	public JTableListModel getTableModel() {
		return tableModel;
	}
	
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}
	
	public void setVisible(boolean b) {
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		this.setTitle("打印进口料件备案变更清单");
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
			jContentPane.setLayout(null);
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
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(45,41,72,22));
			jLabel.setText("变更次数：");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new java.awt.Rectangle(1,1,290,170));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(58,110,65,30));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			jButton1.setBounds(new java.awt.Rectangle(174,110,66,30));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(131,40,106,26));
		}
		return jTextField;
	}

}  //  @jve:decl-index=0:visual-constraint="84,11"
