/*
 * Created on 2004-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.message;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.message.action.BcsMessageAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgReturnDeal extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JButton jButton = null;
	private JButton jButton1 = null;
	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse
	private JTextField jTextField = null;
	private MessageAction messageAction = null;
	private BcsMessageAction bcsMessageAction = null;
	private DzscMessageAction dzscMessageAction = null;
	protected int projectType;

	/**
	 * This is the default constructor
	 */
	public DgReturnDeal() {
		super();
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		bcsMessageAction = (BcsMessageAction) CommonVars.getApplicationContext()
		.getBean("bcsMessageAction");
		dzscMessageAction = (DzscMessageAction) CommonVars.getApplicationContext()
		.getBean("dzscMessageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(385, 138);
		this.setTitle("异常回执处理");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(4, 27, 88, 17);
			jLabel.setText("回执文件名称：");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJTextField(), null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(111, 63, 67, 23);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						messageAction.exceptionfilesdeal(new Request(CommonVars
								.getCurrUser()), jTextField.getText());
					} else if (projectType == ProjectType.DZSC) {
						dzscMessageAction.exceptionfilesdeal(new Request(CommonVars
								.getCurrUser()), jTextField.getText());
					} else if (projectType == ProjectType.BCS) {
						bcsMessageAction.exceptionfilesdeal(new Request(CommonVars
								.getCurrUser()), jTextField.getText());
					}
					JOptionPane.showMessageDialog(DgReturnDeal.this, "处理完毕！",
							"提示", 2);
					DgReturnDeal.this.dispose();
				}
			});

		}
		return jButton;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(201, 63, 65, 23);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgReturnDeal.this.dispose();
				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(95, 25, 259, 22);
		}
		return jTextField;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
