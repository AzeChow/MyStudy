/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptEmailParamver;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFptEmail extends DgCommon {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField tfmyEmailAdress = null;

	private JTextField tfuserName = null;

	private JTextField tfsmtpServer = null;

	private JTableListModel tableModel = null;

	private int dataState = -1;// DataState.BROWSE;

	private JLabel jLabel194 = null;

	private JTextField tfshowName = null;

	private JTextField tfpopServer = null;

	private JButton btnEdit = null;

	private JButton btnExit = null;

	private JButton btnAddTop = null;

	private JLabel jLabel1 = null;

	private JComboBox cbbtype = null;

	private FptEmailParamver email = null; // @jve:decl-index=0:

	private FptManageAction fptManageAction = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JPasswordField tfpassWord = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel = null;

	private JTextField tfSmtpport = null;

	private JTextField tfpopport = null;

	private JLabel jLabel7 = null;

	private JCheckBox cbbAuthenticator = null;

	public DgFptEmail() {
		super();
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		initialize();
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("邮件设置信息表");
		this.setSize(439, 378);
	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			initComponents();
			email = fptManageAction
					.findFptEmailParamver(new Request(CommonVars.getCurrUser()));
			if (email == null) {
				dataState = DataState.ADD;
			} else {
				dataState = DataState.BROWSE;
			}
			showData(email);
			setState();
		}
		super.setVisible(isFlag);
	}

	private void setState() {
		btnSave.setEnabled(dataState != DataState.BROWSE); // 保存
		btnEdit.setEnabled(dataState == DataState.BROWSE); // 修改
		btnCancel.setEnabled(dataState != DataState.BROWSE); // 撤消

		this.tfshowName.setEditable(this.dataState != DataState.BROWSE);
		this.tfmyEmailAdress.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbtype.setEnabled(this.dataState != DataState.BROWSE);
		this.tfpopServer.setEnabled(this.dataState != DataState.BROWSE);
		this.tfsmtpServer.setEditable(this.dataState != DataState.BROWSE);
		this.tfuserName.setEnabled(this.dataState != DataState.BROWSE);
		this.tfpassWord.setEditable(this.dataState != DataState.BROWSE);
		tfpopport.setEditable(this.dataState != DataState.BROWSE);
		tfSmtpport.setEditable(this.dataState != DataState.BROWSE);
		this.cbbAuthenticator.setEnabled(this.dataState != DataState.BROWSE);

	}

	/**
	 * @return Returns the transferFactoryManageAction.
	 */
	public FptManageAction getFptManageAction() {
		return fptManageAction;
	}

	/**
	 * @param transferFactoryManageAction
	 *            The transferFactoryManageAction to set.
	 */
	public void setFptManageAction(
			FptManageAction transferFactoryManageAction) {
		this.fptManageAction = transferFactoryManageAction;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(1, 2, 419, 35);
			jToolBar.add(getBtnAddTop());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
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
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateDataIsNull()) {
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
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
		}
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dataState = DataState.BROWSE;
				showData(email);
				setState();
			}
		});
		return btnCancel;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getTfuserName() {
		if (tfuserName == null) {
			tfuserName = new JTextField();
			tfuserName.setBounds(new Rectangle(184, 206, 219, 22));
		}
		return tfuserName;
	}

	/**
	 * This method initializes tfsmtpServer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfsmtpServer() {
		if (tfsmtpServer == null) {
			tfsmtpServer = new JTextField();
			tfsmtpServer.setBounds(new Rectangle(184, 150, 219, 22));
		}
		return tfsmtpServer;
	}

	/**
	 * This method initializes tfMaterielNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfmyEmailAdress() {
		if (tfmyEmailAdress == null) {
			tfmyEmailAdress = new JTextField();
			tfmyEmailAdress.setBounds(new Rectangle(184, 42, 219, 22));
		}
		return tfmyEmailAdress;
	}

	/**
	 * 显示数据
	 */
	private void showData(FptEmailParamver email) {
		if (email == null) {
			return;
		}
		tfshowName.setText(email.getShowName());
		tfmyEmailAdress.setText(email.getMyEmailAdress());
		this.cbbtype.setSelectedIndex(ItemProperty.getIndexByCode(email
				.getType().toString(), this.cbbtype));
		tfpopServer.setText(email.getPopServer());
		tfsmtpServer.setText(email.getSmptServer());
		tfuserName.setText(email.getUserName());
		tfpassWord.setText(email.getPassword());
		tfSmtpport.setText(email.getSmtpport());
		tfpopport.setText(email.getPoppport());
		if (email.getIsAuthenticator() == null) {
			this.cbbAuthenticator.setSelected(false);
		} else {
			this.cbbAuthenticator.setSelected(email.getIsAuthenticator());
		}
	}

	/**
	 * 填充数据
	 */
	private void fillData(FptEmailParamver email) {
		email.setShowName(tfshowName.getText());
		email.setMyEmailAdress(tfmyEmailAdress.getText());
		if (cbbtype.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbtype.getSelectedItem();
			String projectType = item.getCode();
			email.setType(projectType);
		} else {
			email.setType(null);
		}
		email.setPopServer(tfpopServer.getText());
		email.setSmptServer(tfsmtpServer.getText());
		email.setUserName(tfuserName.getText());
		email.setPassword(tfpassWord.getText());
		email.setSmtpport(tfSmtpport.getText());
		email.setPoppport(tfpopport.getText());
		email.setIsAuthenticator(this.cbbAuthenticator.isSelected());
	}

	private void saveData() {
		if (dataState == DataState.ADD) {
			FptEmailParamver email = new FptEmailParamver();
			fillData(email);
			this.fptManageAction.saveFptEmailParamver(new Request(
					CommonVars.getCurrUser()), email);
		} else if (dataState == DataState.EDIT) {
			FptEmailParamver email = fptManageAction
					.findFptEmailParamver(new Request(CommonVars.getCurrUser()));
			fillData(email);
			this.fptManageAction.saveFptEmailParamver(new Request(
					CommonVars.getCurrUser()), email);
		}
	}

	private boolean validateDataIsNull() {
		return false;
	}

	private void initComponents() {
		this.cbbtype.removeAllItems();
		this.cbbtype.addItem(new ItemProperty("0", "pop3"));
		this.cbbtype.addItem(new ItemProperty("1", "IMAP"));
		this.cbbtype.addItem(new ItemProperty("2", "HTTP"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbtype);
		this.cbbtype.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbtype.setSelectedIndex(-1);
	}

	/**
	 * This method initializes tfTransferFactoryBillNo1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfshowName() {
		if (tfshowName == null) {
			tfshowName = new JTextField();
			tfshowName.setBounds(new Rectangle(184, 11, 219, 22));
		}
		return tfshowName;
	}

	/**
	 * This method initializes tfpopServer
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfpopServer() {
		if (tfpopServer == null) {
			tfpopServer = new JTextField();
			tfpopServer.setBounds(new Rectangle(184, 99, 219, 22));
		}
		return tfpopServer;
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
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes btnAddTop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddTop() {
		if (btnAddTop == null) {
			btnAddTop = new JButton();
			btnAddTop.setText("新增");
			btnAddTop.setVisible(false);
			btnAddTop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.ADD;
					setState();
				}
			});
		}
		return btnAddTop;
	}

	/**
	 * This method initializes cbbtype
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbtype() {
		if (cbbtype == null) {
			cbbtype = new JComboBox();
			cbbtype.setBounds(new Rectangle(184, 67, 219, 29));
		}
		return cbbtype;
	}

	public FptEmailParamver getEmail() {
		return email;
	}

	public void setEmail(FptEmailParamver email) {
		this.email = email;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(25, 134, 151, 18));
			jLabel7.setText("pop端口号");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(25, 181, 151, 18));
			jLabel.setText("smpt端口号");
			jLabel1 = new JLabel();
			jLabel1.setText("密码");
			jLabel1.setBounds(new Rectangle(25, 235, 151, 19));
			jLabel194 = new JLabel();
			jLabel194.setText("显示名");
			jLabel194.setBounds(new Rectangle(25, 11, 151, 19));
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(25, 208, 151, 18));
			jLabel6.setText("帐户名");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(25, 158, 151, 18));
			jLabel5.setText("smtp发送邮件服务");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(25, 108, 151, 18));
			jLabel4.setText("接收(pop,IMAP,HTTP)服务");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(25, 74, 151, 18));
			jLabel3.setText("接收类型");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(25, 41, 151, 18));
			jLabel2.setText("电子邮箱地址");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(3, 42, 411, 229));
			jPanel.add(jLabel194, null);
			jPanel.add(getTfshowName(), null);
			jPanel.add(getTfmyEmailAdress(), null);
			jPanel.add(getCbbtype(), null);
			jPanel.add(getTfpopServer(), null);
			jPanel.add(getTfsmtpServer(), null);
			jPanel.add(getTfuserName(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfpassWord(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfSmtpport(), null);
			jPanel.add(getTfpopport(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getCbbAuthenticator(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfpassWord
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getTfpassWord() {
		if (tfpassWord == null) {
			tfpassWord = new JPasswordField();
			tfpassWord.setBounds(new Rectangle(184, 237, 219, 22));
		}
		return tfpassWord;
	}

	/**
	 * This method initializes tfSmtpport
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSmtpport() {
		if (tfSmtpport == null) {
			tfSmtpport = new JTextField();
			tfSmtpport.setBounds(new Rectangle(184, 176, 219, 22));
		}
		return tfSmtpport;
	}

	/**
	 * This method initializes tfpopport
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfpopport() {
		if (tfpopport == null) {
			tfpopport = new JTextField();
			tfpopport.setBounds(new Rectangle(184, 125, 219, 22));
		}
		return tfpopport;
	}

	/**
	 * This method initializes cbbAuthenticator
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbAuthenticator() {
		if (cbbAuthenticator == null) {
			cbbAuthenticator = new JCheckBox();
			cbbAuthenticator.setBounds(new Rectangle(130, 277, 174, 21));
			cbbAuthenticator.setText("我的服务器要求身份验证");
		}
		return cbbAuthenticator;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
