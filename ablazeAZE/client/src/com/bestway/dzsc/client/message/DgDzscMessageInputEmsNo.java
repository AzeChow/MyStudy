package com.bestway.dzsc.client.message;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscMessageInputEmsNo extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfEmsNo = null;

	private JLabel jLabel1 = null;

	private JTextField tfCorrEmsNo = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private boolean isOK = false;

	private JLabel jLabel2 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscMessageInputEmsNo() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(295, 183));
		this.setTitle("分别输入合同备案和通关备案手册号");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(29, 84, 234, 21));
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("*如果<取消>，则系统生成一个默认的号码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(31, 55, 86, 23));
			jLabel1.setText("通关备案手册号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(31, 20, 86, 24));
			jLabel.setText("合同备案手册号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfEmsNo(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfCorrEmsNo(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel2, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(119, 20, 142, 24));
			tfEmsNo.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					if (str == null) {
						return;
					}
					if (super.getLength() >= 12 || str.getBytes().length > 12
							|| (super.getLength() + str.getBytes().length) > 12) {
						return;
					}
					super.insertString(offs, str, a);
				}
			});
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCorrEmsNo() {
		if (tfCorrEmsNo == null) {
			tfCorrEmsNo = new JTextField();
			tfCorrEmsNo.setBounds(new Rectangle(119, 55, 142, 23));
			tfCorrEmsNo.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					if (str == null) {
						return;
					}
					if (super.getLength() >= 12 || str.getBytes().length > 12
							|| (super.getLength() + str.getBytes().length) > 12) {
						return;
					}
					super.insertString(offs, str, a);
				}
			});
		}
		return tfCorrEmsNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(49, 113, 63, 24));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ("".equals(tfEmsNo.getText().trim())) {
						JOptionPane.showMessageDialog(
								DgDzscMessageInputEmsNo.this, "合同备案手册号不能为空",
								"提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if ("".equals(tfCorrEmsNo.getText().trim())) {
						JOptionPane.showMessageDialog(
								DgDzscMessageInputEmsNo.this, "通关备案手册号不能为空",
								"提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					isOK = true;
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
			jButton1.setBounds(new Rectangle(170, 113, 61, 24));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	public String getEmsNo() {
		return this.tfEmsNo.getText().trim();
	}

	public String getCorrEmsNo() {
		return this.tfCorrEmsNo.getText().trim();
	}

	public boolean isOK() {
		return isOK;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
