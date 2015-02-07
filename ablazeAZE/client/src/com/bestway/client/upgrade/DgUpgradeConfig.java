package com.bestway.client.upgrade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.upgrade.httpconfig.HttpConfigEntity;
import com.bestway.client.upgrade.httpconfig.UpdateHttpConfigService;
import com.bestway.client.upgrade.httpconfig.UpdateHttpConfigServiceService;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgUpgradeConfig extends JDialogBase {

	private JPanel jContentPane = null;

	private JTextField tfServer = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;


	private Point screenPoint = null;


	private JTextField tfTomcatDir = null;

	private JPanel jPanel = null;

	private JLabel jLabel2 = null;

	private JPanel jPanel1 = null;

	private JButton btnExit = null;

	private int dataState = DataState.BROWSE;

	private UpdateHttpConfigServiceService service = null;  //  @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgUpgradeConfig() {
		super();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initialize();
		initUIComponents();
	}

	private void initUIComponents() {
		String serverHostAddress = CommonVars.getServerName();
		service = UpdateHttpConfigServiceService
				.getUpdateHttpConfigServiceService(serverHostAddress);
		showData();
		setState();
	}

	private void setState() {
		tfServer.setEditable(dataState != DataState.BROWSE);
		tfTomcatDir.setEditable(dataState != DataState.BROWSE);
		btnEdit.setEnabled(dataState == DataState.BROWSE);
		btnSave.setEnabled(dataState != DataState.BROWSE);
	}

	private void showData() {

		UpdateHttpConfigService servicePort = service
				.getUpdateHttpConfigServicePort();
		HttpConfigEntity entity = servicePort.getHttpConfig();
		if (entity != null) {
			String server = entity.getServer();
			String tomcatDir = entity.getTomcatDir();
			this.tfServer.setText(server);
			this.tfTomcatDir.setText(tomcatDir);
		}
	}

	private void saveData() {
		UpdateHttpConfigService servicePort = service
				.getUpdateHttpConfigServicePort();
		HttpConfigEntity entity = new HttpConfigEntity();
		String server = this.tfServer.getText();
		String userName = this.tfTomcatDir.getText();
		entity.setServer(server);
		entity.setTomcatDir(userName);
		servicePort.saveHttpConfig(entity);
	}

	private boolean isValidate() {
		String server = this.tfServer.getText();
		if (server.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Http 升级路径不能为空", "", 2);
			return false;
		}
		String userName = this.tfTomcatDir.getText();
		if (userName.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Tomcat 目录名不能为空", "", 2);
			return false;
		}

		return true;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(492, 351));
		this.setTitle("JBCUS 升级参数设置");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean isFlag) {
		super.setVisible(isFlag);
	}

	public Point getScreenPoint() {
		return screenPoint;
	}

	public void setScreenPoint(Point screenPoint) {
		this.screenPoint = screenPoint;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(21, 116, 113, 22));
			jLabel1.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel1.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel1.setText("Tomcat 安装目录");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(22, 85, 112, 22));
			jLabel.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel.setText("HTTP 升级服务路径");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getTfServer(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.isShiftDown() && e.getClickCount() > 2) {
						tfServer.setEditable(true);
						tfTomcatDir.setEditable(true);
					}
				}
			});
		}
		return jContentPane;
	}

	/**
	 * This method initializes tfServer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfServer() {
		if (tfServer == null) {
			tfServer = new JTextField();
			tfServer.setBounds(new Rectangle(139, 85, 326, 22));
			tfServer.setBackground(Color.white);
			// tfServer.setEditable(false);
		}
		return tfServer;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setBounds(new Rectangle(321, 8, 60, 24));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!isValidate()) {
						return;
					}
					saveData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setBounds(new Rectangle(258, 8, 60, 24));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JTextField getTfName() {
		if (tfTomcatDir == null) {
			tfTomcatDir = new JTextField();
			tfTomcatDir.setBounds(new Rectangle(139, 115, 326, 22));
			tfTomcatDir.setBackground(Color.white);
			// tfName.setEditable(false);
		}
		return tfTomcatDir;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(18, 17, 313, 31));
			jLabel2.setText("JBCUS 程式升级参数设置");
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 24));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(2, 1, 483, 60));
			jPanel.setBackground(Color.white);
			jPanel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel.add(jLabel2, null);
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
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(1, 258, 483, 63));
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(getBtnSave(), null);
			jPanel1.add(getBtnEdit(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new Rectangle(385, 8, 60, 24));
			btnExit.setText("\u5173\u95ed");
			btnExit.setFont(new Font("Dialog", Font.PLAIN, 12));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
