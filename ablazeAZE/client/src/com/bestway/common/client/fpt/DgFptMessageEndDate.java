package com.bestway.common.client.fpt;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgFptMessageEndDate extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private boolean isOK = false;


	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbEndDate;


	/**
	 * This method initializes
	 * 
	 */
	public DgFptMessageEndDate() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(295, 171));
		this.setTitle("申请表有效期");
		this.setContentPane(getJContentPane());

		getJContentPane().add(getCbbEndDate());
	}

	@Override
	public void setVisible(boolean b) {

		if (b) {
			
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
			// jLabel1.setText("*如果<取消>，则系统生成一个默认的号码");
			// jLabel1.setVisible(false);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(15, 23, 102, 24));
			jLabel.setText("申请表有效期");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
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

	public Date getEndDate() {
		return this.cbbEndDate.getDate();
	}

	public boolean isOK() {
		return isOK;
	}
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(99, 27, 132, 20);
		}
		return cbbEndDate;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
