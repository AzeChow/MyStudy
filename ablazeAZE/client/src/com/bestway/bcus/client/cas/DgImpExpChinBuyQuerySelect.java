package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class DgImpExpChinBuyQuerySelect extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JTextPane jTextPane = null;
	private JEditorPane jEditorPane = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private ButtonGroup buttonGroup = null; 
	private boolean ok = true;
	private int selectItem = -1;

	/**
	 * This is the default constructor
	 */
	public DgImpExpChinBuyQuerySelect() {
		super();
		initialize();
		getButtonGroup();
	}

	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(380, 284);
		this.setContentPane(getJContentPane());
		this.setTitle("选择计算方式");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(16,11,318,30));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setText("请选择如下计算方式：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextPane(), null);
			jContentPane.add(getJEditorPane(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBounds(new java.awt.Rectangle(51,77,290,41));
			jTextPane.setBackground(java.awt.SystemColor.info);
			jTextPane.setText("国内购买结存 = 国内购买期初单 + 国内购买进仓 - 国内购买出仓");
			jTextPane.setEditable(false);
		}
		return jTextPane;
	}

	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setBounds(new java.awt.Rectangle(53,141,289,48));
			jEditorPane.setBackground(java.awt.SystemColor.info);
			jEditorPane.setText("国内购买出仓 = 国内购买期初单 + 国内购买进仓 - 国内购买结存 , 并产生国内购买出仓单据");
			jEditorPane.setEditable(false);
		}
		return jEditorPane;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(82,205,82,29));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jRadioButton.isSelected()){
						setSelectItem(1);
					} else if (jRadioButton1.isSelected()){
						setSelectItem(2);
					}
					setOk(true);
					DgImpExpChinBuyQuerySelect.this.dispose();
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
			jButton1.setBounds(new java.awt.Rectangle(201,205,82,29));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    setSelectItem(-1);
				    setOk(false);
				    DgImpExpChinBuyQuerySelect.this.dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new java.awt.Rectangle(26,54,176,21));
			jRadioButton.setText("1，计算结存");
			jRadioButton.setSelected(true);
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new java.awt.Rectangle(26,121,150,21));
			jRadioButton1.setText("2，产生出仓单据");
		}
		return jRadioButton1;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public int getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
