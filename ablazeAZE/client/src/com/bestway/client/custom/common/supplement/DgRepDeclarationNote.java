package com.bestway.client.custom.common.supplement;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;

import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Color;

public class DgRepDeclarationNote extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTextArea taNote = null;
	private JButton btSave = null;
	private JButton btCancel = null;
	private JButton button = null;
	private String note = null;
	private boolean isOk = false;

	/**
	 * @param owner
	 */
	public DgRepDeclarationNote(JButton btn, String note) {
		super();
		this.button = btn;
		this.note = note;
		initialize();
		initUI(note);
	}

	private void initUI(String note) {
		taNote.setText(note);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(355, 228);
		this.setForeground(Color.black);
		this.setTitle("备注：");
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
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.gridx = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getTaNote(), gridBagConstraints);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new FlowLayout());
			jPanel1.add(getBtSave(), null);
			jPanel1.add(getBtCancel(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes taNote
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaNote() {
		if (taNote == null) {
			taNote = new JTextArea();
			taNote.setPreferredSize(new Dimension(200, 228));
		}
		return taNote;
	}

	/**
	 * This method initializes btSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtSave() {
		if (btSave == null) {
			btSave = new JButton();
			btSave.setText("确定");
			btSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = true;
					dispose();
				}
			});
		}
		return btSave;
	}

	/**
	 * This method initializes btCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtCancel() {
		if (btCancel == null) {
			btCancel = new JButton();
			btCancel.setText("取消");
			btCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btCancel;
	}

	public String getNote() {
		if (isOk) {
			return taNote.getText();
		} else {
			return note;
		}
	}
}