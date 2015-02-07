package com.bestway.bcus.client;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DgErrorMessage extends JDialogBase {

	private JPanel jPanel = null;
	private JButton btnCenael = null;
	private JButton btnCopy = null;
	private JLabel jLabel = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel1 = null;
	private String message = "";
	private static DgErrorMessage dgErrorMessage = null;
	private JScrollPane jScrollPane = null;
	private JTextArea jTextArea = null;

	/**
	 * This method initializes
	 * 
	 */
	private DgErrorMessage() {
		super();
		initialize();
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			getJTextArea().setText(message);
			super.setVisible(b);
		}
	}

	public static void showMessage() {
		if (dgErrorMessage == null) {
			return;
		} else {
			dgErrorMessage.setVisible(true);
		}
	}

	public static DgErrorMessage getInstance(String message) {
		if (dgErrorMessage == null) {
			dgErrorMessage = new DgErrorMessage();
		}
		dgErrorMessage.message = message;
		return dgErrorMessage;

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(560, 271));
		this.setTitle("错误信息提示!");
		this.setContentPane(getJPanel());

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("以下是服务返回信息：");
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel.add(jLabel, BorderLayout.NORTH);
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnCenael
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCenael() {
		if (btnCenael == null) {
			btnCenael = new JButton();
			btnCenael.setText("关闭");
			btnCenael.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCenael;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("复制");
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getJTextArea().selectAll();
					getJTextArea().copy();
				}
			});
		}
		return btnCopy;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("     ");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getBtnCopy(), gridBagConstraints);
			jPanel1.add(getBtnCenael(), gridBagConstraints1);
			jPanel1.add(jLabel1, gridBagConstraints11);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
		}
		return jTextArea;
	}

} // @jve:decl-index=0:visual-constraint="145,19"
