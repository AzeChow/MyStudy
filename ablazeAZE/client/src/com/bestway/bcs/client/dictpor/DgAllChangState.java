package com.bestway.bcs.client.dictpor;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.ui.winuicontrol.JDialogBase;

public class DgAllChangState extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private boolean ok = false;
	private boolean CheckBoxState = false;
	private JLabel jLabel = null;
	private JButton btnOk = null;
	private JButton jButton1 = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JCheckBox jCheckBox = null;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @param owner
	 */
	public DgAllChangState() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(297, 186);
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
			jLabel2.setBounds(new Rectangle(14, 46, 259, 18));
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("后续造成的一切后果我司将不承担");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(14, 28, 255, 18));
			jLabel1.setForeground(Color.blue);
			jLabel1.setText("修改标志属于不规范作业，如果确实要修改");
			jLabel = new JLabel();
			jLabel.setText("提示:");
			jLabel.setBounds(new Rectangle(2, 7, 64, 18));
			jLabel.setForeground(Color.blue);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJCheckBox(), null);

		}
		return jContentPane;
	}

	/**
	 * This method initializes checkstate
	 * 
	 * @return javax.swing.JCheckBox
	 */

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(125, 117, 60, 28));
			btnOk.setText("\u786e\u5b9a");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jCheckBox.isSelected()) {
						ok = true;
						CheckBoxState = true;
					} else {
						ok = false;
					}
					dispose();
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(205, 116, 60, 28));
			jButton1.setText("\u53d6\u6d88");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ok = false;
					CheckBoxState = false;
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(14, 73, 166, 21));
			jCheckBox.setText("请再次确认是否要修改");
		}
		return jCheckBox;
	}

	public boolean isCheckBoxState() {
		return CheckBoxState;
	}

	public void setCheckBoxState(boolean checkBoxState) {
		CheckBoxState = checkBoxState;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
