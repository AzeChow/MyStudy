package com.bestway.client.owp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.ui.winuicontrol.JDialogBase;

public class DgOwpMessageInputNo extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfEmsNo = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private boolean isOK = false;

	private String emsNoType = ""; // @jve:decl-index=0:

	private JLabel jLabel1 = null;

	public void setEmsNoType(String emsNoType) {
		this.emsNoType = emsNoType;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgOwpMessageInputNo() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(295, 171));
		this.setTitle("分别输入XX号");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean b) {
		if (b) {
			this.setTitle("请输入" + emsNoType);
			this.jLabel.setText(emsNoType);
			if (emsNoType.indexOf("统一编号") >= 0) {
				jLabel1.setText(emsNoType
									+ "长度必须是18位");
			} else if (emsNoType.indexOf("申请表") >= 0) {
				if (tfEmsNo.getText().getBytes().length != 12) {
					jLabel1.setText(emsNoType
									+ "长度必须是12位");
				}
			} else if (emsNoType.indexOf("发货单") >= 0
					|| emsNoType.indexOf("发退货单") >= 0) {
				if (tfEmsNo.getText().getBytes().length != 17) {
					jLabel1.setText(emsNoType
									+ "长度必须是17位");
				}
			}
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(32, 57, 226, 21));
			jLabel1.setForeground(Color.blue);
			// jLabel1.setText("*如果<取消>，则系统生成一个默认的号码");
			// jLabel1.setVisible(false);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(15, 23, 102, 24));
			jLabel.setText("合同备案手册号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfEmsNo(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel1, null);
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
			tfEmsNo.setBounds(new Rectangle(119, 23, 153, 24));
			// tfEmsNo.setDocument(new PlainDocument() {
			// public void insertString(int offs, String str, AttributeSet a)
			// throws BadLocationException {
			// if (str == null) {
			// return;
			// }
			// if (super.getLength() >= 12 || str.getBytes().length > 12
			// || (super.getLength() + str.getBytes().length) > 12) {
			// return;
			// }
			// super.insertString(offs, str, a);
			// }
			// });
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(49, 93, 63, 24));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (emsNoType.indexOf("统一编号") >= 0) {
						if (tfEmsNo.getText().getBytes().length != 18) {
							JOptionPane.showMessageDialog(
									DgOwpMessageInputNo.this, emsNoType
											+ "长度必须是18位");
							return;
						}
					} else if (emsNoType.indexOf("申请表") >= 0) {
						if (tfEmsNo.getText().getBytes().length != 12) {
							JOptionPane.showMessageDialog(
									DgOwpMessageInputNo.this, emsNoType
											+ "长度必须是12位");
							return;
						}
					} else if (emsNoType.indexOf("发货单") >= 0
							|| emsNoType.indexOf("收货单") >= 0) {
						if (tfEmsNo.getText().getBytes().length != 17) {
							JOptionPane.showMessageDialog(
									DgOwpMessageInputNo.this, emsNoType
											+ "长度必须是17位");
							return;
						}
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
			jButton1.setBounds(new Rectangle(170, 93, 61, 24));
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

	public boolean isOK() {
		return isOK;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
