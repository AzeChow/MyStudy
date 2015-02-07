package com.bestway.wjsz.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bestway.bcus.client.common.DataState;
import com.bestway.jptds.client.common.RegistryUtil;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.waijing.action.WjszAction;
import com.bestway.waijing.entity.WjszParaSet;
import java.awt.Font;
import javax.swing.SwingConstants;

public class FmWjszParaSet extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JButton btnSave = null;
	private JButton btnLogin = null;
	private JButton jButton = null;
	private JLabel jLabel = null;
	private JTextField tfUserId = null;
	private JPasswordField tfPassword = null;
	private JLabel jLabel1 = null;

	private WjszAction wjszAction = (WjszAction) com.bestway.bcus.client.common.CommonVars
			.getApplicationContext().getBean("wjszAction");
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField tfServerName = null;
	private JTextField tfServerPort = null;
	private int dataState = DataState.BROWSE;
	private JButton btnEdit = null;
	private JLabel jLabel4 = null;
	private JLabel lbLoginStatus = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmWjszParaSet() {
		super();
		initialize();
		try {
			WjszClientUtil.login();
		} catch (Exception e) {
		}
		setState();
		showLoginStatus();
	}

	private void setState() {
		this.tfPassword.setEditable(dataState != DataState.BROWSE);
		this.tfServerName.setEditable(dataState != DataState.BROWSE);
		this.tfServerPort.setEditable(dataState != DataState.BROWSE);

		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnLogin.setEnabled(dataState == DataState.BROWSE);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(483, 400));
		this.setTitle("参数设置");
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						Map map = RegistryUtil.getRegistUser();
						if (map == null) {
							return;
						}
						tfUserId.setText((map.get("userid") == null || ""
								.equals(map.get("userid"))) ? "" : map.get(
								"userid").toString());
						WjszParaSet wjszParaSet = (WjszParaSet) wjszAction
								.findWjszParaSet(new com.bestway.common.Request(
										com.bestway.bcus.client.common.CommonVars
												.getCurrUser()));
						tfPassword.setText(wjszParaSet.getPassword());
						tfServerName.setText(wjszParaSet.getServerName());
						tfServerPort.setText(wjszParaSet.getServerPort());
					}
				});

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbLoginStatus = new JLabel();
			lbLoginStatus.setBounds(new Rectangle(114, 183, 284, 34));
			lbLoginStatus
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			lbLoginStatus.setHorizontalAlignment(SwingConstants.CENTER);
			lbLoginStatus.setText("");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(41, 187, 68, 27));
			jLabel4.setText("登录状态:");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(40, 61, 68, 15));
			jLabel3.setText("服务器端口");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(40, 27, 68, 15));
			jLabel2.setText("服务器地址");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(40, 146, 68, 15));
			jLabel1.setText("密码");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(40, 111, 68, 15));
			jLabel.setText("用户编号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getBtnLogin(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfUserId(), null);
			jContentPane.add(getTfPassword(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfServerName(), null);
			jContentPane.add(getTfServerPort(), null);
			jContentPane.add(getBtnEdit(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(lbLoginStatus, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(130, 236, 65, 24));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					WjszParaSet wjszParaSet = (WjszParaSet) wjszAction
							.findWjszParaSet(new com.bestway.common.Request(
									com.bestway.bcus.client.common.CommonVars
											.getCurrUser()));
					wjszParaSet.setPassword(String.valueOf(tfPassword
							.getPassword()));
					wjszParaSet.setServerName(tfServerName.getText().trim());
					wjszParaSet.setServerPort(tfServerPort.getText().trim());
					wjszAction.saveWjszParaSet(new com.bestway.common.Request(
							com.bestway.bcus.client.common.CommonVars
									.getCurrUser()), wjszParaSet);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnLogin
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLogin() {
		if (btnLogin == null) {
			btnLogin = new JButton();
			btnLogin.setBounds(new Rectangle(351, 236, 65, 24));
			btnLogin.setText("登录");
			btnLogin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if ("".equals(tfServerName.getText().trim())) {
						JOptionPane.showMessageDialog(FmWjszParaSet.this,
								"外经服务器地址不能为空！");
						return;
					}
					if ("".equals(tfServerPort.getText().trim())) {
						JOptionPane.showMessageDialog(FmWjszParaSet.this,
								"外经服务器端口不能为空！");
						return;
					}
					String userId = tfUserId.getText().trim();
					String password = String.valueOf(tfPassword.getPassword())
							.trim();
					if ("".equals(userId)) {
						JOptionPane.showMessageDialog(FmWjszParaSet.this,
								"用户编码不能为空！");
						return;
					}
					if ("".equals(password)) {
						JOptionPane.showMessageDialog(FmWjszParaSet.this,
								"密码不能为空！");
						return;
					}
					WjszClientUtil.login(userId, password);
					showLoginStatus();
				}
			});
		}
		return btnLogin;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(233, 236, 65, 24));
			jButton.setText("注册");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					register();
				}
			});
		}
		return jButton;
	}

	private void register() {
		File txtFile = getFile();
		if (txtFile == null) {
			return;
		}
		try {
			regeditXML(txtFile);
			JOptionPane.showMessageDialog(this, "用户注册成功！");
			Map map = RegistryUtil.getRegistUser();
			if (map == null) {
				return;
			}
			this.tfUserId.setText((map.get("userid") == null || "".equals(map
					.get("userid"))) ? "" : map.get("userid").toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "用户注册失败！" + e);
		}

	}

	private File getFile() {
		JFileChooser filechooser = new JFileChooser();// 创建文件选择器
		filechooser.setAcceptAllFileFilterUsed(false);
		filechooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

			public boolean accept(File f) { // 设定可用的文件的后缀名
				if (f.getName().endsWith(".key") || f.isDirectory()) {
					return true;
				}
				return false;
			}

			public String getDescription() {
				return "选择文件(*.key)";
			}
		});
		int state = filechooser.showOpenDialog(FmWjszParaSet.this);
		if (state != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File file = filechooser.getSelectedFile();
		if (file == null || !file.exists()) {
			return null;
		}
		return file;
	}

	/**
	 * 写入注册表中
	 * 
	 * @param file
	 */
	public void regeditXML(File file) {
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(file);
			Element root = DocumentHelper.parseText(document.asXML())
					.getRootElement();
			Element listUnitElement = root.element("PTS_TR_KEY");
			if (listUnitElement != null) {
				Element codeId = listUnitElement.element("USER_ID");// 用户编码
				Element key = listUnitElement.element("PUBLIC_KEY");// key
				Element type = listUnitElement.element("MODE_TYPE");// key
				Map map = new HashMap();
				map.put("userid", codeId == null ? "" : codeId.getTextTrim());
				map.put("key", key == null ? "" : key.getTextTrim());
				map.put("type", type == null ? "" : type.getTextTrim());
				RegistryUtil.setRegistUser(map);
			}
		} catch (DocumentException ex) {
			throw new RuntimeException(ex);
		}

	}

	/**
	 * This method initializes tfUserId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUserId() {
		if (tfUserId == null) {
			tfUserId = new JTextField();
			tfUserId.setBounds(new Rectangle(114, 111, 285, 19));
			tfUserId.setEditable(false);
		}
		return tfUserId;
	}

	/**
	 * This method initializes tfPassword
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getTfPassword() {
		if (tfPassword == null) {
			tfPassword = new JPasswordField();
			tfPassword.setBounds(new Rectangle(114, 144, 285, 19));
		}
		return tfPassword;
	}

	/**
	 * This method initializes tfServerName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfServerName() {
		if (tfServerName == null) {
			tfServerName = new JTextField();
			tfServerName.setBounds(new Rectangle(114, 27, 285, 19));
		}
		return tfServerName;
	}

	/**
	 * This method initializes tfServerPort
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfServerPort() {
		if (tfServerPort == null) {
			tfServerPort = new JTextField();
			tfServerPort.setBounds(new Rectangle(114, 61, 285, 19));
		}
		return tfServerPort;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(43, 236, 65, 24));
			btnEdit.setText("修改");
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
	 * 显示登录状态
	 */
	private void showLoginStatus() {
		this.lbLoginStatus.setText(WjszClientUtil.isLoginStatus() ? "已登录"
				: "未登录");
		this.lbLoginStatus
				.setForeground(WjszClientUtil.isLoginStatus() ? Color.GREEN
						: Color.RED);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
