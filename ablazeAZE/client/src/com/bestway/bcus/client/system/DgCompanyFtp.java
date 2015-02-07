package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCompanyFtp extends JDialogBase {

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JTextField tfBcusftpAddress = null;

	private JTextField tfBcusftpUser = null;

	private JTextField tfBcusftpPassword = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JTextField tfBcusbcusEDICode = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JTextField tfDzscFtpAddress = null;

	private JTextField tfDzscFtpUser = null;

	private JTextField tfDzscFtpPassword = null;

	private JLabel jLabel17 = null;

	private JTextField tftfDzscEDICode = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JTextField tfBcsFtpAddress = null;

	private JTextField tfBcsFtpUser = null;

	private JTextField tfBcsFtpPassword = null;

	private JLabel jLabel11 = null;

	private JTextField tBcsEDICode = null;

	private JToolBar jToolBar = null; // @jve:decl-index=0:visual-constraint="58,126"

	private JButton btnEdit = null;

	private JButton btnCancel = null;

	private JPanel jPanel3 = null; // @jve:decl-index=0:visual-constraint="80,150"

	private JButton btnSave = null;

	private Company company = null;

	private int dataState = DataState.BROWSE;

	SystemAction service = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgCompanyFtp() {
		super();
		initialize();
		service = (SystemAction) CommonVars.getApplicationContext().getBean(
				"systemAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(314, 305));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("公司Ftp设置");
		this.setContentPane(getJPanel3());

	}

	public void setVisible(boolean is) {
		if (is) {
			showData();
			setState();
		}
		super.setVisible(is);
	}

	private void setState() {
		this.btnEdit.setEnabled(dataState != DataState.EDIT);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.tfBcusftpAddress.setEditable(dataState != DataState.BROWSE);
		this.tfBcusftpUser.setEditable(dataState != DataState.BROWSE);
		this.tfBcusftpPassword.setEditable(dataState != DataState.BROWSE);
		this.tfBcusbcusEDICode.setEditable(dataState != DataState.BROWSE);
		this.tfDzscFtpAddress.setEditable(dataState != DataState.BROWSE);
		this.tfDzscFtpUser.setEditable(dataState != DataState.BROWSE);
		this.tfDzscFtpPassword.setEditable(dataState != DataState.BROWSE);
		this.tftfDzscEDICode.setEditable(dataState != DataState.BROWSE);
		this.tfBcsFtpAddress.setEditable(dataState != DataState.BROWSE);
		this.tfBcsFtpUser.setEditable(dataState != DataState.BROWSE);
		this.tfBcsFtpPassword.setEditable(dataState != DataState.BROWSE);
		this.tBcsEDICode.setEditable(dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("电子帐册", null, getJPanel(), null);
			jTabbedPane.addTab("纸质手册", null, getJPanel2(), null);
			jTabbedPane.addTab("电子手册", null, getJPanel1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(36, 151, 62, 17));
			jLabel3.setText("EDI\u53f7\u7801");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(37, 110, 62, 17));
			jLabel2.setText("FTP\u5bc6\u7801");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(36, 70, 62, 17));
			jLabel1.setText("FTP\u7528\u6237\u540d");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(36, 32, 62, 17));
			jLabel.setText("FTP\u5730\u5740");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel.add(getJTextField(), null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField3(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfBcusftpAddress == null) {
			tfBcusftpAddress = new JTextField();
			tfBcusftpAddress
					.setBounds(new java.awt.Rectangle(101, 31, 169, 22));
			tfBcusftpAddress.setText("61.145.126.185");
		}
		return tfBcusftpAddress;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (tfBcusftpUser == null) {
			tfBcusftpUser = new JTextField();
			tfBcusftpUser.setBounds(new java.awt.Rectangle(101, 69, 169, 22));
			tfBcusftpUser.setText("Anonymous");
		}
		return tfBcusftpUser;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (tfBcusftpPassword == null) {
			tfBcusftpPassword = new JPasswordField();
			tfBcusftpPassword.setBounds(new java.awt.Rectangle(101, 109, 169,
					22));
		}
		return tfBcusftpPassword;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (tfBcusbcusEDICode == null) {
			tfBcusbcusEDICode = new JTextField();
			tfBcusbcusEDICode.setBounds(new java.awt.Rectangle(101, 149, 169,
					22));
		}
		return tfBcusbcusEDICode;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel17 = new JLabel();
			jLabel17.setBounds(new java.awt.Rectangle(35, 151, 64, 18));
			jLabel17.setText("EDI\u53f7\u7801");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(36, 112, 64, 18));
			jLabel6.setText("FTP\u5bc6\u7801");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(35, 74, 64, 18));
			jLabel5.setText("FTP\u7528\u6237\u540d");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(36, 32, 64, 18));
			jLabel4.setText("FTP\u5730\u5740");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel1.add(jLabel4, null);
			jPanel1.add(jLabel5, null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getJTextField4(), null);
			jPanel1.add(getJTextField5(), null);
			jPanel1.add(getJTextField6(), null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(getJTextField7(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (tfDzscFtpAddress == null) {
			tfDzscFtpAddress = new JTextField();
			tfDzscFtpAddress
					.setBounds(new java.awt.Rectangle(100, 32, 167, 23));
			tfDzscFtpAddress.setText("61.145.126.185");
		}
		return tfDzscFtpAddress;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (tfDzscFtpUser == null) {
			tfDzscFtpUser = new JTextField();
			tfDzscFtpUser.setBounds(new java.awt.Rectangle(100, 71, 167, 23));
			tfDzscFtpUser.setText("Anonymous");
		}
		return tfDzscFtpUser;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (tfDzscFtpPassword == null) {
			tfDzscFtpPassword = new JPasswordField();
			tfDzscFtpPassword.setBounds(new java.awt.Rectangle(100, 111, 167,
					23));
		}
		return tfDzscFtpPassword;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (tftfDzscEDICode == null) {
			tftfDzscEDICode = new JTextField();
			tftfDzscEDICode
					.setBounds(new java.awt.Rectangle(100, 150, 167, 23));
		}
		return tftfDzscEDICode;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(31, 144, 65, 17));
			jLabel11.setText("EDI\u53f7\u7801");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(32, 107, 65, 17));
			jLabel10.setText("FTP\u5bc6\u7801");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(32, 66, 65, 17));
			jLabel9.setText("FTP\u7528\u6237\u540d");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(31, 27, 65, 17));
			jLabel8.setText("FTP\u5730\u5740");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jPanel2.add(jLabel8, null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(getJTextField8(), null);
			jPanel2.add(getJTextField9(), null);
			jPanel2.add(getJTextField10(), null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(getJTextField11(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField8() {
		if (tfBcsFtpAddress == null) {
			tfBcsFtpAddress = new JTextField();
			tfBcsFtpAddress.setBounds(new java.awt.Rectangle(99, 27, 167, 23));
			tfBcsFtpAddress.setText("61.145.126.185");
		}
		return tfBcsFtpAddress;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField9() {
		if (tfBcsFtpUser == null) {
			tfBcsFtpUser = new JTextField();
			tfBcsFtpUser.setBounds(new java.awt.Rectangle(99, 65, 167, 23));
			tfBcsFtpUser.setText("Anonymous");
		}
		return tfBcsFtpUser;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField10() {
		if (tfBcsFtpPassword == null) {
			tfBcsFtpPassword = new JPasswordField();
			tfBcsFtpPassword
					.setBounds(new java.awt.Rectangle(99, 103, 167, 23));
		}
		return tfBcsFtpPassword;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField11() {
		if (tBcsEDICode == null) {
			tBcsEDICode = new JTextField();
			tBcsEDICode.setBounds(new java.awt.Rectangle(99, 142, 167, 23));
		}
		return tBcsEDICode;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setSize(new java.awt.Dimension(47, 30));

			jToolBar.add(getJButton3());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton8());

		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCompanyFtp.this.dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("\u5173\u95ed");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCompanyFtp.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setSize(new java.awt.Dimension(83, 41));
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJToolBar(), BorderLayout.NORTH);
			jPanel3.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("\u4fdd\u5b58");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					DgCompanyFtp.this.service.saveCompany(new Request(
							CommonVars.getCurrUser()), company);
					DgCompanyFtp.this.dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	public void fillData() {
		this.company
				.setBcusftpAddress(this.tfBcusftpAddress.getText() == null ? ""
						: this.tfBcusftpAddress.getText());
		this.company.setBcusftpUser(this.tfBcusftpUser.getText() == null ? ""
				: this.tfBcusftpUser.getText());
		this.company
				.setBcusftpPassword(this.tfBcusftpPassword.getText() == null ? ""
						: this.tfBcusftpPassword.getText());
		this.company
				.setBcusbcusEDICode(this.tfBcusbcusEDICode.getText() == null ? ""
						: this.tfBcusbcusEDICode.getText());
		this.company
				.setDzscFtpAddress(this.tfDzscFtpAddress.getText() == null ? ""
						: this.tfDzscFtpAddress.getText());
		this.company.setDzscFtpUser(this.tfDzscFtpUser.getText() == null ? ""
				: this.tfDzscFtpUser.getText());
		this.company
				.setDzscFtpPassword(this.tfDzscFtpPassword.getText() == null ? ""
						: this.tfDzscFtpPassword.getText());
		this.company.setDzscEDICode(this.tftfDzscEDICode.getText() == null ? ""
				: this.tftfDzscEDICode.getText());
		this.company
				.setBcsFtpAddress(this.tfBcsFtpAddress.getText() == null ? ""
						: this.tfBcsFtpAddress.getText());
		this.company.setBcsFtpUser(this.tfBcsFtpUser.getText() == null ? ""
				: this.tfBcsFtpUser.getText());
		this.company
				.setBcsFtpPassword(this.tfBcsFtpPassword.getText() == null ? ""
						: this.tfBcsFtpPassword.getText());
		this.company.setBcsEDICode(this.tBcsEDICode.getText() == null ? ""
				: this.tBcsEDICode.getText());

	}

	private void showData() {
		this.tfBcusftpAddress
				.setText(this.company.getBcusftpAddress() == null ? ""
						: this.company.getBcusftpAddress());
		this.tfBcusftpUser.setText(this.company.getBcusftpUser() == null ? ""
				: this.company.getBcusftpUser());
		this.tfBcusftpPassword
				.setText(this.company.getBcusftpPassword() == null ? ""
						: this.company.getBcusftpPassword());
		this.tfBcusbcusEDICode
				.setText(this.company.getBcusbcusEDICode() == null ? ""
						: this.company.getBcusbcusEDICode());
		this.tfDzscFtpAddress
				.setText(this.company.getDzscFtpAddress() == null ? ""
						: this.company.getDzscFtpAddress());
		this.tfDzscFtpUser.setText(this.company.getDzscFtpUser() == null ? ""
				: this.company.getDzscFtpUser());
		this.tfDzscFtpPassword
				.setText(this.company.getDzscFtpPassword() == null ? ""
						: this.company.getDzscFtpPassword());
		this.tftfDzscEDICode.setText(this.company.getDzscEDICode() == null ? ""
				: this.company.getDzscEDICode());
		this.tfBcsFtpAddress
				.setText(this.company.getBcsFtpAddress() == null ? ""
						: this.company.getBcsFtpAddress());
		this.tfBcsFtpUser.setText(this.company.getBcsFtpUser() == null ? ""
				: this.company.getBcsFtpUser());
		this.tfBcsFtpPassword
				.setText(this.company.getBcsFtpPassword() == null ? ""
						: this.company.getBcsFtpPassword());
		this.tBcsEDICode.setText(this.company.getBcsEDICode() == null ? ""
				: this.company.getBcsEDICode());

	}

	public void setCompany(Company company) {
		this.company = company;
	}
} // @jve:decl-index=0:visual-constraint="33,12"
