package com.bestway.common.client.tools;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.FtpConfig;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JPasswordField;
import java.awt.Color;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgFtpConfig extends JDialogBase {

	private JPanel jContentPane = null;

	private JTextField tfServer = null;

	private JTextField tfRemoteDir = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private ToolsAction toolsAction = null;

	private Point screenPoint = null;

	private boolean isOk = false;

	/**
	 * This method initializes
	 * 
	 */
	public DgFtpConfig() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(283, 233));
		this.setTitle("FTP 参数设置");
		this.setContentPane(getJContentPane());

	}

	public void setVisible(boolean isFlag) {
		super.setVisible(isFlag, this.getScreenPoint());
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
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(16, 128, 78, 22));
			jLabel4.setText("报表前缀名");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(16, 100, 78, 22));
			jLabel3.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel3.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel3.setText("远程目录");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(16, 72, 78, 22));
			jLabel2.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel2.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel2.setText("FTP 用户密码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(16, 43, 78, 22));
			jLabel1.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel1.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel1.setText("FTP 用户名");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 15, 78, 22));
			jLabel.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabel.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel.setText("FTP 服务名称");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getTfServer(), null);
			jContentPane.add(getTfRemoteDir(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(getTfPassword(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfPrefixReportName(), null);
			jContentPane.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.isShiftDown() && e.getClickCount() > 2) {
						tfServer.setEditable(true);
						tfName.setEditable(true);
						tfPassword.setEditable(true);
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
			tfServer.setBounds(new Rectangle(93, 15, 158, 22));
			tfServer.setBackground(Color.white);
			tfServer.setEditable(false);
		}
		return tfServer;
	}

	/**
	 * This method initializes tfRemoteDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteDir() {
		if (tfRemoteDir == null) {
			tfRemoteDir = new JTextField();
			tfRemoteDir.setBounds(new Rectangle(93, 100, 158, 22));
		}
		return tfRemoteDir;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(106, 168, 68, 26));
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!isValidate()) {
						return;
					}
					fillData();
					ftpConfig = toolsAction.saveFtpConfig(new Request(
							CommonVars.getCurrUser()), ftpConfig);
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(182, 168, 67, 26));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	FtpConfig ftpConfig = null;

	private JPasswordField tfName = null;

	private JPasswordField tfPassword = null;

	private JLabel jLabel4 = null;

	private JTextField tfPrefixReportName = null;

	private void initUIComponents() {
		ftpConfig = this.toolsAction.findFtpConfig(new Request(CommonVars
				.getCurrUser()));
		String companyName = CommonVars.getCurrUser().getCompany().getName();
		if (ftpConfig != null) {
			String server  = "218.16.119.187";;
			String userName = ftpConfig.getUserName();
			String password = ftpConfig.getPassword();
			String directory = ftpConfig.getRemoteDir();
			this.tfServer.setText(server);
			this.tfName.setText(userName);
			this.tfPassword.setText(password);
			this.tfRemoteDir.setText(directory);
			this.tfPrefixReportName
					.setText(ftpConfig.getPrefixReportName() != null ? ftpConfig
							.getPrefixReportName().trim()
							: "");
		} else {
			String server = "218.16.119.187";
			String userName = "Remotepr9";
			String password = "printer9";
			String directory = "报关行目录";
			this.tfServer.setText(server);
			this.tfName.setText(userName);
			this.tfPassword.setText(password);
			this.tfRemoteDir.setText(directory);
			if (companyName.length() > 5) {
				companyName = companyName.substring(0, 5);
			}
			this.tfPrefixReportName.setText(companyName);
		}
	}

	private void fillData() {
		if (ftpConfig == null) {
			ftpConfig = new FtpConfig();
		}
		String server = this.tfServer.getText();
		String userName = this.tfName.getText();
		String password = this.tfPassword.getText();
		String directory = this.tfRemoteDir.getText();
		String prefixReportName = this.tfPrefixReportName.getText();
		this.ftpConfig.setServer(server);
		this.ftpConfig.setUserName(userName);
		this.ftpConfig.setPassword(password);
		this.ftpConfig.setRemoteDir(directory);
		this.ftpConfig.setPrefixReportName(prefixReportName);
	}

	private boolean isValidate() {
		String server = this.tfServer.getText();
		if (server.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "FTP 服务名称不能为空", "", 2);
			return false;
		}
		String userName = this.tfName.getText();
		if (userName.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "FTP 用户名不能为空", "", 2);
			return false;
		}
		String password = this.tfPassword.getText();
		if (password.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "FTP 密码不能为空", "", 2);
			return false;
		}
		String directory = this.tfRemoteDir.getText();
		if (directory.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "远程目录不能为空", "", 2);
			return false;
		}
		String prefixReportName = this.tfPrefixReportName.getText();
		if (prefixReportName.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "报表前缀名不可为空", "", 2);
			return false;
		}
		if (prefixReportName.trim().length() > 5) {
			JOptionPane.showMessageDialog(this, "报表前缀名字符串数长度不能大于5", "", 2);
			return false;
		}
		return true;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getTfName() {
		if (tfName == null) {
			tfName = new JPasswordField();
			tfName.setBounds(new Rectangle(93, 43, 158, 22));
			tfName.setBackground(Color.white);
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes tfPassword
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getTfPassword() {
		if (tfPassword == null) {
			tfPassword = new JPasswordField();
			tfPassword.setBounds(new Rectangle(93, 72, 158, 22));
			tfPassword.setBackground(Color.white);
			tfPassword.setEditable(false);
		}
		return tfPassword;
	}

	/**
	 * This method initializes tfPrefixReportName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPrefixReportName() {
		if (tfPrefixReportName == null) {
			tfPrefixReportName = new JTextField();
			tfPrefixReportName.setBounds(new Rectangle(93, 128, 158, 22));
		}
		return tfPrefixReportName;
	}

}
