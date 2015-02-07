package com.bestway.common.client.fpt;

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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFptMessageInputNo extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfEmsNo = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private boolean isOK = false;

	private String emsNoType = ""; // @jve:decl-index=0:

	private JLabel jLabel1 = null;
	private FptManageAction fptManageAction;

	public void setEmsNoType(String emsNoType) {
		this.emsNoType = emsNoType;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgFptMessageInputNo() {
		super();
		initialize();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
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

	@Override
	public void setVisible(boolean b) {

		if (b) {
			this.setTitle("请输入" + emsNoType);
			this.jLabel.setText(emsNoType);
			if (emsNoType.indexOf("统一编号") >= 0) {
				jLabel1.setText(emsNoType + "长度必须是18位");
				tfEmsNo.setDocument(new CustomDocument(18));
			} else if (emsNoType.indexOf("申请表") >= 0) {
				if (tfEmsNo.getText().getBytes().length != 12) {
					jLabel1.setText(emsNoType + "长度必须是12位");
					tfEmsNo.setDocument(new CustomDocument(12));
				}
			} else if (emsNoType.indexOf("发货单") >= 0
					|| emsNoType.indexOf("发退货单") >= 0) {
				if (tfEmsNo.getText().getBytes().length != 17) {
					jLabel1.setText(emsNoType + "长度必须是17位");
					tfEmsNo.setDocument(new CustomDocument(17));
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
		}
		return tfEmsNo;
	}

	// 判断字符限数
	class CustomDocument extends PlainDocument {

		int len;

		public CustomDocument(int len) {
			this.len = len;
		}

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= len || str.length() > len
					|| super.getLength() + str.length() > len) {
				return;
			}

			super.insertString(offs, str, a);
		}
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
									DgFptMessageInputNo.this, emsNoType
											+ "长度必须是18位");
							return;
						}
						// 验证重复的 统一编号
						boolean isExists = fptManageAction
								.findExistsSeqNoOrAppNo(CommonVars.getRequst(),
										tfEmsNo.getText().trim(),null, 
										FptInOutFlag.IN);
						if (!isExists) {
							JOptionPane.showMessageDialog(
									DgFptMessageInputNo.this, "统一编号:"+tfEmsNo.getText()
											+ "已经存在");
							return;
						}
					} else if (emsNoType.indexOf("申请表") >= 0) {
						if (tfEmsNo.getText().getBytes().length != 12) {
							JOptionPane.showMessageDialog(
									DgFptMessageInputNo.this, emsNoType
											+ "长度必须是12位");
							return;
						}
						// 验证重复的 统一编号 申请表编号
						boolean isExists = fptManageAction
								.findExistsSeqNoOrAppNo(CommonVars.getRequst(),
										null, tfEmsNo.getText().trim(),
										FptInOutFlag.IN);
						if (!isExists) {
							JOptionPane.showMessageDialog(
									DgFptMessageInputNo.this, "申请表编号"+tfEmsNo.getText()
											+ "已经存在");
							return;
						}
					} else if (emsNoType.indexOf("发货单") >= 0
							|| emsNoType.indexOf("发退货单") >= 0) {
						if (tfEmsNo.getText().getBytes().length != 17) {
							JOptionPane.showMessageDialog(
									DgFptMessageInputNo.this, emsNoType
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
					isOK = false;
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
