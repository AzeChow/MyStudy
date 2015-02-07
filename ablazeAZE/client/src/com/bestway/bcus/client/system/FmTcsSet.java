package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.action.TcsParametersAction;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.bcus.system.entity.TcsLinkMan;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ParameterType;
import com.bestway.customs.common.entity.TCSCommonCode;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JCheckBox;

import org.apache.commons.net.ftp.FTPClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class FmTcsSet extends JDialogBase {

	private JPanel contextPane = null;
	private JTabbedPane tabMain = null;
	private JPanel pnParam = null;
	private JToolBar tbTop = null;
	private JPanel pnLinkMan = null;
	private JLabel lbSenderId = null;
	private JTextField tfSenderId = null;
	private JLabel lbSenderAddress = null;
	private JTextField tfSenderAddress = null;
	private JLabel lbReceiverId = null;
	private JTextField tfReceiverId = null;
	private JLabel lbReceiverAddress = null;
	private JTextField tfReceiverAddress = null;
	private JLabel lbUserId = null;
	private JTextField tfUserId = null;
	private JLabel lbUserPrivateKey = null;
	private JTextField tfUserPrivateKey = null;
	private JLabel lbBtNo = null;
	private JTextField tfBtNo = null;
	private JLabel lbActionId = null;
	private JTextField tfActionId = null;
	private JButton btnOk = null;
	private JButton btnClose = null;
	private JLabel lbDept = null;
	private JTextField tfName = null;
	private JLabel lbDuty = null;
	private JTextField tfDept = null;
	private JLabel lbPhone = null;
	private JTextField tfDuty = null;
	private JLabel lbMobile = null;
	private JTextField tfPhone = null;
	private JLabel lbFax = null;
	private JTextField tfMobile = null;
	private JLabel lbAddress = null;
	private JTextField tfFax = null;
	private JLabel lbName = null;
	private JTextField tfAddress = null;
	private JLabel lbZipCode = null;
	private JTextField tfZipCode = null;
	private JLabel lbEmail = null;
	private JTextField tfEmail = null;
	private JLabel lbImCode = null;
	private JTextField tfImCode = null;
	private JLabel lbImType = null;
	private JComboBox tfImType = null;
	private JButton btnEdit = null;

	private TcsParametersAction tcsParametersAction = null;
	private SystemAction systemAction = null;
	private TestSwingWorker tsw = null;

	private boolean linkManEnable = false;
	private boolean paramsEnable = false;
	private boolean ftpEnable = false;
	private boolean pathEnable = false;
	private boolean otherEnable = false;

	private JLabel lbIcCardNo = null;
	private JTextField tfIcCardNo = null;
	private JLabel lbCertificateNo = null;
	private JTextField tfCertificateNo = null;
	private JLabel lbOperatorName = null;
	private JTextField tfOperatorName = null;
	private JLabel lbOrganizationCode = null;
	private JTextField tfOrganizationCode = null;
	private JLabel lbFtpAddress = null;
	private JLabel lbFtpName = null;
	private JLabel lbFtpPwd = null;
	private JPanel pnEdiPath = null;
	private JLabel lbSendPath = null;
	private JLabel lbRecvPath = null;
	private JLabel lbFinallyPath = null;
	private JLabel lbLogPath = null;
	private JTextField tfSendPath = null;
	private JTextField tfRecvPath = null;
	private JTextField tfFinallyPath = null;
	private JTextField tfLogPath = null;
	private JButton btnSendPath = null;
	private JButton btnRecvPath = null;
	private JButton btnFinallyPath = null;
	private JButton btnLogPath = null;
	private JButton btnNntAndProt = null;

	/**
	 * This is the default constructor
	 */
	public FmTcsSet() {
		super();
		tcsParametersAction = (TcsParametersAction) CommonVars
				.getApplicationContext().getBean("tcsParametersAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		initialize();
		// 监听窗体关闭事件，当窗体关闭时，关闭 测试网络连接是否连通的TestSwingWorker线程
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeTestSwingWorker();
			}
		});
	}

	public void setVisible(boolean b) {
		initData();
		initLinkManEditState(linkManEnable);
		initParamEditState(paramsEnable);
//		initFtpEditState(ftpEnable);
		initEdiPathState(pathEnable);
		initOtherState(otherEnable);
		btnEdit.setEnabled(true);
		btnOk.setEnabled(false);
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(796, 505);
		this.setResizable(false);
		this.setContentPane(getContextPane());
		this.setTitle("EDI接口参数设置");
	}

	/**
	 * This method initializes contextPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getContextPane() {
		if (contextPane == null) {
			contextPane = new JPanel();
			contextPane.setLayout(new BorderLayout());
			contextPane.add(getTbTop(), BorderLayout.NORTH);
			contextPane.add(getTabMain(), BorderLayout.CENTER);

		}
		return contextPane;
	}

	/**
	 * This method initializes tabMain
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabMain() {
		if (tabMain == null) {
			tabMain = new JTabbedPane();
			tabMain.addTab("用戶参数设置", null, getPnParam(), null);
			tabMain.addTab("发送联系人信息设置", null, getPnLinkMan(), null);
			tabMain.addTab("EDI路径设置", null, getPnEdiPath(), null);
			tabMain.addTab("其它参数", null, getPnOther(), null);
		}
		return tabMain;
	}

	private JToolBar getTbTop() {
		if (tbTop == null) {
			tbTop = new JToolBar();
			tbTop.add(getBtnEdit());
			tbTop.add(getBtnOk());
			tbTop.add(getBtnClose());
		}
		return tbTop;
	}

	/**
	 * This method initializes pnParam
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnParam() {
		if (pnParam == null) {
			lbOrganizationCode = new JLabel();
			lbOrganizationCode.setBounds(new Rectangle(31, 340, 120, 18));
			lbOrganizationCode.setHorizontalAlignment(SwingConstants.RIGHT);
			lbOrganizationCode.setText("组织机构代码：");
			lbOperatorName = new JLabel();
			lbOperatorName.setBounds(new Rectangle(409, 340, 120, 18));
			lbOperatorName.setHorizontalAlignment(SwingConstants.RIGHT);
			lbOperatorName.setText("操作员卡的姓名：");
			lbCertificateNo = new JLabel();
			lbCertificateNo.setBounds(new Rectangle(409, 280, 120, 18));
			lbCertificateNo.setHorizontalAlignment(SwingConstants.RIGHT);
			lbCertificateNo.setText("操作员卡的证书号：");
			lbIcCardNo = new JLabel();
			lbIcCardNo.setBounds(new Rectangle(31, 280, 120, 18));
			lbIcCardNo.setHorizontalTextPosition(SwingConstants.TRAILING);
			lbIcCardNo.setHorizontalAlignment(SwingConstants.RIGHT);
			lbIcCardNo.setText("录入员IC卡号：");
			lbActionId = new JLabel();
			lbActionId.setBounds(new Rectangle(409, 220, 120, 18));
			lbActionId.setHorizontalAlignment(SwingConstants.RIGHT);
			lbActionId.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbActionId.setText("业务流程活动编号：");
			lbBtNo = new JLabel();
			lbBtNo.setBounds(new Rectangle(409, 160, 120, 18));
			lbBtNo.setHorizontalAlignment(SwingConstants.RIGHT);
			lbBtNo.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbBtNo.setText("企业业务流程编号：");
			lbUserPrivateKey = new JLabel();
			lbUserPrivateKey.setBounds(new Rectangle(409, 100, 120, 18));
			lbUserPrivateKey.setHorizontalAlignment(SwingConstants.RIGHT);
			lbUserPrivateKey.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbUserPrivateKey.setText("平台用户私密：");
			lbUserId = new JLabel();
			lbUserId.setBounds(new Rectangle(409, 40, 120, 18));
			lbUserId.setHorizontalAlignment(SwingConstants.RIGHT);
			lbUserId.setText("平台用户编号：");
			lbReceiverAddress = new JLabel();
			lbReceiverAddress.setBounds(new Rectangle(31, 220, 120, 18));
			lbReceiverAddress.setHorizontalAlignment(SwingConstants.RIGHT);
			lbReceiverAddress.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbReceiverAddress.setText("报文接收者地址：");
			lbReceiverId = new JLabel();
			lbReceiverId.setBounds(new Rectangle(31, 160, 120, 18));
			lbReceiverId.setHorizontalAlignment(SwingConstants.RIGHT);
			lbReceiverId.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbReceiverId.setText("报文接收者编号：");
			lbSenderAddress = new JLabel();
			lbSenderAddress.setBounds(new Rectangle(31, 100, 120, 18));
			lbSenderAddress.setHorizontalAlignment(SwingConstants.RIGHT);
			lbSenderAddress.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbSenderAddress.setText("报文发送者地址：");
			lbSenderId = new JLabel();
			lbSenderId.setText("报文发送者编号：");
			lbSenderId.setVerticalTextPosition(SwingConstants.BOTTOM);
			lbSenderId.setHorizontalAlignment(SwingConstants.RIGHT);
			lbSenderId.setBounds(new Rectangle(31, 40, 120, 18));
			pnParam = new JPanel();
			pnParam.setLayout(null);
			pnParam.add(lbSenderId, null);
			pnParam.add(getTfSenderId(), null);
			pnParam.add(lbSenderAddress, null);
			pnParam.add(getTfSenderAddress(), null);
			pnParam.add(lbReceiverId, null);
			pnParam.add(getTfReceiverId(), null);
			pnParam.add(lbReceiverAddress, null);
			pnParam.add(getTfReceiverAddress(), null);
			pnParam.add(lbUserId, null);
			pnParam.add(getTfUserId(), null);
			pnParam.add(lbUserPrivateKey, null);
			pnParam.add(getTfUserPrivateKey(), null);
			pnParam.add(lbBtNo, null);
			pnParam.add(getTfBtNo(), null);
			pnParam.add(lbActionId, null);
			pnParam.add(getTfActionId(), null);
			pnParam.add(lbIcCardNo, null);
			pnParam.add(getTfIcCardNo(), null);
			pnParam.add(lbCertificateNo, null);
			pnParam.add(getTfCertificateNo(), null);
			pnParam.add(lbOperatorName, null);
			pnParam.add(getTfOperatorName(), null);
			pnParam.add(lbOrganizationCode, null);
			pnParam.add(getTfOrganizationCode(), null);
		}
		return pnParam;
	}

	/**
	 * This method initializes pnLinkMan
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnLinkMan() {
		if (pnLinkMan == null) {

			lbImType = new JLabel();
			lbImType.setText("IM类型：");
			lbImType.setHorizontalAlignment(SwingConstants.LEFT);
			lbImType.setBounds(new Rectangle(600, 250, 49, 22));
			lbImType.setName("lbImType");
			lbImCode = new JLabel();
			lbImCode.setText("IM编号：");
			lbImCode.setHorizontalAlignment(SwingConstants.LEFT);
			lbImCode.setBounds(new Rectangle(428, 250, 61, 22));
			lbImCode.setName("lbImCode");
			lbEmail = new JLabel();
			lbEmail.setText("EMail：");
			lbEmail.setHorizontalAlignment(SwingConstants.LEFT);
			lbEmail.setBounds(new Rectangle(67, 250, 61, 22));
			lbEmail.setName("lbEmail");
			lbZipCode = new JLabel();
			lbZipCode.setText("邮编：");
			lbZipCode.setHorizontalAlignment(SwingConstants.LEFT);
			lbZipCode.setBounds(new Rectangle(428, 200, 61, 22));
			lbZipCode.setName("lbZipCode");
			lbAddress = new JLabel();
			lbAddress.setText("地址：");
			lbAddress.setHorizontalAlignment(SwingConstants.LEFT);
			lbAddress.setBounds(new Rectangle(67, 200, 61, 22));
			lbAddress.setName("lbAddress");
			lbFax = new JLabel();
			lbFax.setText("传真：");
			lbFax.setHorizontalAlignment(SwingConstants.LEFT);
			lbFax.setBounds(new Rectangle(428, 150, 61, 22));
			lbFax.setName("lbFax");
			lbMobile = new JLabel();
			lbMobile.setText("手机：");
			lbMobile.setHorizontalAlignment(SwingConstants.LEFT);
			lbMobile.setBounds(new Rectangle(67, 150, 61, 22));
			lbMobile.setName("lbMobile");
			lbPhone = new JLabel();
			lbPhone.setText("电话：");
			lbPhone.setHorizontalAlignment(SwingConstants.LEFT);
			lbPhone.setBounds(new Rectangle(428, 100, 61, 22));
			lbPhone.setName("lbPhone");
			lbDuty = new JLabel();
			lbDuty.setText("职务：");
			lbDuty.setHorizontalAlignment(SwingConstants.LEFT);
			lbDuty.setBounds(new Rectangle(67, 100, 61, 22));
			lbDuty.setName("lbDuty");
			lbDept = new JLabel();
			lbDept.setText("部门：");
			lbDept.setHorizontalAlignment(SwingConstants.LEFT);
			lbDept.setBounds(new Rectangle(428, 50, 61, 22));
			lbDept.setName("lbDept");
			lbName = new JLabel();
			lbName.setText("联系人：");
			lbName.setHorizontalAlignment(SwingConstants.LEFT);
			lbName.setBounds(new Rectangle(67, 50, 61, 22));
			lbName.setName("lbName");
			pnLinkMan = new JPanel();
			pnLinkMan.setLayout(null);
			pnLinkMan.add(lbName, null);
			pnLinkMan.add(getTfName(), null);
			pnLinkMan.add(lbDept, null);
			pnLinkMan.add(getTfDept(), null);
			pnLinkMan.add(lbDuty, null);
			pnLinkMan.add(getTfDuty(), null);
			pnLinkMan.add(lbPhone, null);
			pnLinkMan.add(getTfPhone(), null);
			pnLinkMan.add(lbMobile, null);
			pnLinkMan.add(getTfMobile(), null);
			pnLinkMan.add(lbFax, null);
			pnLinkMan.add(getTfFax(), null);
			pnLinkMan.add(lbAddress, null);
			pnLinkMan.add(getTfAddress(), null);
			pnLinkMan.add(lbZipCode, null);
			pnLinkMan.add(getTfZipCode(), null);
			pnLinkMan.add(lbEmail, null);
			pnLinkMan.add(getTfEmail(), null);
			pnLinkMan.add(lbImCode, null);
			pnLinkMan.add(getTfImCode(), null);
			pnLinkMan.add(lbImType, null);
			pnLinkMan.add(getTfImType(), null);
		}
		return pnLinkMan;
	}

	/**
	 * This method initializes tfSenderId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSenderId() {
		if (tfSenderId == null) {
			tfSenderId = new JTextField();
			tfSenderId.setBounds(new Rectangle(166, 39, 200, 22));
		}
		return tfSenderId;
	}

	/**
	 * This method initializes tfSenderAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSenderAddress() {
		if (tfSenderAddress == null) {
			tfSenderAddress = new JTextField();
			tfSenderAddress.setBounds(new Rectangle(166, 99, 200, 22));
		}
		return tfSenderAddress;
	}

	/**
	 * This method initializes tfReceiverId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReceiverId() {
		if (tfReceiverId == null) {
			tfReceiverId = new JTextField();
			tfReceiverId.setBounds(new Rectangle(166, 159, 200, 22));
		}
		return tfReceiverId;
	}

	/**
	 * This method initializes tfReceiverAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReceiverAddress() {
		if (tfReceiverAddress == null) {
			tfReceiverAddress = new JTextField();
			tfReceiverAddress.setBounds(new Rectangle(166, 219, 200, 22));
		}
		return tfReceiverAddress;
	}

	/**
	 * This method initializes tfUserId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUserId() {
		if (tfUserId == null) {
			tfUserId = new JTextField();
			tfUserId.setBounds(new Rectangle(546, 39, 200, 22));
		}
		return tfUserId;
	}

	/**
	 * This method initializes tfUserPrivateKey
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUserPrivateKey() {
		if (tfUserPrivateKey == null) {
			tfUserPrivateKey = new JTextField();
			tfUserPrivateKey.setBounds(new Rectangle(546, 99, 200, 22));
		}
		return tfUserPrivateKey;
	}

	/**
	 * This method initializes tfBtNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBtNo() {
		if (tfBtNo == null) {
			tfBtNo = new JTextField();
			tfBtNo.setBounds(new Rectangle(546, 159, 200, 22));
		}
		return tfBtNo;
	}

	/**
	 * This method initializes tfActionId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfActionId() {
		if (tfActionId == null) {
			tfActionId = new JTextField();
			tfActionId.setBounds(new Rectangle(546, 219, 200, 22));
		}
		return tfActionId;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(266, 350, 99, 33));
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new SaveParams().start();
					initParamEditState(false);
					initLinkManEditState(false);
//					initFtpEditState(false);
					initEdiPathState(false);
					initOtherState(false);
					btnOk.setEnabled(false);
					btnEdit.setEnabled(true);
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(426, 350, 99, 33));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
					closeTestSwingWorker();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setName("tfName");
			tfName.setBounds(new Rectangle(148, 50, 188, 22));
		}
		return tfName;
	}

	/**
	 * This method initializes tfDept
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDept() {
		if (tfDept == null) {
			tfDept = new JTextField();
			tfDept.setName("tfDept");
			tfDept.setBounds(new Rectangle(512, 50, 188, 22));
		}
		return tfDept;
	}

	/**
	 * This method initializes tfDuty
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDuty() {
		if (tfDuty == null) {
			tfDuty = new JTextField();
			tfDuty.setName("tfDuty");
			tfDuty.setBounds(new Rectangle(148, 100, 188, 22));
		}
		return tfDuty;
	}

	/**
	 * This method initializes tfPhone
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPhone() {
		if (tfPhone == null) {
			tfPhone = new JTextField();
			tfPhone.setName("tfPhone");
			tfPhone.setBounds(new Rectangle(512, 100, 188, 22));
		}
		return tfPhone;
	}

	/**
	 * This method initializes tfMobile
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMobile() {
		if (tfMobile == null) {
			tfMobile = new JTextField();
			tfMobile.setName("tfMobile");
			tfMobile.setBounds(new Rectangle(148, 150, 188, 22));
		}
		return tfMobile;
	}

	/**
	 * This method initializes tfFax
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFax() {
		if (tfFax == null) {
			tfFax = new JTextField();
			tfFax.setName("tfFax");
			tfFax.setBounds(new Rectangle(512, 150, 188, 22));
		}
		return tfFax;
	}

	/**
	 * This method initializes tfAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setName("tfAddress");
			tfAddress.setBounds(new Rectangle(148, 200, 188, 22));
		}
		return tfAddress;
	}

	/**
	 * This method initializes tfZipCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfZipCode() {
		if (tfZipCode == null) {
			tfZipCode = new JTextField();
			tfZipCode.setName("tfZipCode");
			tfZipCode.setBounds(new Rectangle(512, 200, 188, 22));
		}
		return tfZipCode;
	}

	/**
	 * This method initializes tfEmail
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setName("tfEmail");
			tfEmail.setBounds(new Rectangle(148, 250, 188, 22));
		}
		return tfEmail;
	}

	/**
	 * This method initializes tfImCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImCode() {
		if (tfImCode == null) {
			tfImCode = new JTextField();
			tfImCode.setName("tfImCode");
			tfImCode.setBounds(new Rectangle(512, 250, 85, 22));
		}
		return tfImCode;
	}

	/**
	 * This method initializes tfImType
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getTfImType() {
		if (tfImType == null) {
			tfImType = new JComboBox();
			tfImType.setName("tfImType");
			tfImType.setBounds(new Rectangle(650, 250, 50, 22));
			tfImType.addItem(new ItemProperty(TCSCommonCode.Im_qq,
					TCSCommonCode.getImType(TCSCommonCode.Im_qq)));
			tfImType.addItem(new ItemProperty(TCSCommonCode.Im_msn,
					TCSCommonCode.getImType(TCSCommonCode.Im_msn)));
		}
		return tfImType;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(266, 350, 99, 33));
			btnEdit.setText("编辑");

			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initParamEditState(true);
					initLinkManEditState(true);
//					initFtpEditState(true);
					initEdiPathState(true);
					initOtherState(true);
					btnEdit.setEnabled(false);
					btnOk.setEnabled(true);
				}
			});
		}
		return btnEdit;
	}

	class SaveParams extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog(FmTcsSet.this);
				CommonProgress.setMessage("系统正保存，请稍后...");
				tcsParametersAction.saveParameters(
						new Request(CommonVars.getCurrUser()), getParams());
				tcsParametersAction.saveLinkMan(
						new Request(CommonVars.getCurrUser()), getLinkMan());
				if (checkNull()) {
					return;
				}
				if (checkRepeat()) {
					return;
				}

				systemAction.createDir(new Request(CommonVars.getCurrUser()),
						false, (tfSendPath.getText().trim()), tfRecvPath
								.getText().trim(), tfFinallyPath.getText()
								.trim(), tfLogPath.getText().trim(), null);
				systemAction.saveDir(new Request(CommonVars.getCurrUser()),
						tfSendPath.getText().trim(), tfRecvPath.getText()
								.trim(), tfFinallyPath.getText().trim(),
						tfLogPath.getText().trim(), null);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(FmTcsSet.this, "保存参数出错",
						"保存参数出错", 1);
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	private TcsLinkMan getLinkMan() {
		TcsLinkMan linkMan = new TcsLinkMan();

		linkMan.setAddress(tfAddress.getText());
		linkMan.setDepartment(tfDept.getText());
		linkMan.setDuty(tfDuty.getText());
		linkMan.setEmail(tfEmail.getText());
		linkMan.setFax(tfFax.getText());
		linkMan.setImCode(tfImCode.getText());
		linkMan.setImType(((ItemProperty) (tfImType.getSelectedItem()))
				.getCode());
		linkMan.setMobile(tfMobile.getText());
		linkMan.setName(tfName.getText());
		linkMan.setTelephone(tfPhone.getText());
		linkMan.setZipCode(tfZipCode.getText());

		return linkMan;
	}

	@SuppressWarnings("deprecation")
	private TcsParameters getParams() {
		TcsParameters parameters = new TcsParameters();

		parameters.setActionId(tfActionId.getText());
		parameters.setBpNo(tfBtNo.getText());
		parameters.setReceiverAddress(tfReceiverAddress.getText());
		parameters.setReceiverId(tfReceiverId.getText());
		parameters.setSenderAddress(tfSenderAddress.getText());
		parameters.setSenderId(tfSenderId.getText());
		parameters.setUserId(tfUserId.getText());
		parameters.setUserPrivateKey(tfUserPrivateKey.getText());
		parameters.setIcCardNo(tfIcCardNo.getText());
		parameters.setCertificateNo(tfCertificateNo.getText());
		parameters.setOganizationCode(tfOrganizationCode.getText());
		parameters.setOperatorName(tfOperatorName.getText());

//		parameters.setFtpAddress(tfFtpAddress.getText());
//		parameters.setFtpName(tfFtpName.getText());
//		parameters.setFtpPwd(tfFtpPwd.getText());

		parameters.setSendNote(cbSendNote.isSelected());

		parameters.setBtplsAddress(tfBtplsAddress.getText());
		parameters
				.setBtplsPort(CommonUtils.isEmpty(tfBtplsPort.getText()) ? null
						: Integer.parseInt(tfBtplsPort.getText()));
		return parameters;
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		TcsParameters p = tcsParametersAction.getTcsParameters(new Request(
				CommonVars.getCurrUser()));
		TcsLinkMan l = tcsParametersAction.getTcsLinkMan(new Request(CommonVars
				.getCurrUser()));
		// company = CommonVars.get

		if (p != null) {
			// 参数
			tfActionId.setText(p.getActionId());
			tfBtNo.setText(p.getBpNo());
			tfReceiverAddress.setText(p.getReceiverAddress());
			tfReceiverId.setText(p.getReceiverId());
			tfSenderAddress.setText(p.getSenderAddress());
			tfSenderId.setText(p.getSenderId());
			tfUserId.setText(p.getUserId());
			tfUserPrivateKey.setText(p.getUserPrivateKey());
			tfIcCardNo.setText(p.getIcCardNo());
			tfCertificateNo.setText(p.getCertificateNo());
			tfOperatorName.setText(p.getOperatorName());
			tfOrganizationCode.setText(p.getOganizationCode());

//			// ftp设置
//			tfFtpAddress.setText(p.getFtpAddress());
//			tfFtpName.setText(p.getFtpName());
//			tfFtpPwd.setText(p.getFtpPwd());

			// 其它tcs参数
			cbSendNote.setSelected(Boolean.TRUE.equals(p.getSendNote()));

			tfBtplsAddress.setText(p.getBtplsAddress());
			tfBtplsPort.setText(p.getBtplsPort() == null ? "" : p
					.getBtplsPort() + "");
		}

		if (l != null) {
			// 联系人
			tfAddress.setText(l.getAddress());
			tfDept.setText(l.getDepartment());
			tfDuty.setText(l.getDuty());
			tfEmail.setText(l.getEmail());
			tfFax.setText(l.getFax());
			tfImCode.setText(l.getImCode());
			tfImType.setSelectedItem(new ItemProperty(l.getImType(),
					TCSCommonCode.getImType(l.getImType())));
			tfMobile.setText(l.getMobile());
			tfName.setText(l.getName());
			tfPhone.setText(l.getTelephone());
			tfZipCode.setText(l.getZipCode());
		}

		{
			List list = null;
			ParameterSet para = null;

			list = systemAction.findnameValues(
					new Request(CommonVars.getCurrUser()),
					ParameterType.sendDir);
			if (list != null && list.size() > 0) {
				para = (ParameterSet) list.get(0);
			}
			tfSendPath.setText(para.getNameValues());

			list = systemAction.findnameValues(
					new Request(CommonVars.getCurrUser()),
					ParameterType.recvDir);
			if (list != null && list.size() > 0) {
				para = (ParameterSet) list.get(0);
			}
			tfRecvPath.setText(para.getNameValues());

			list = systemAction.findnameValues(
					new Request(CommonVars.getCurrUser()),
					ParameterType.finallyDir);
			if (list != null && list.size() > 0) {
				para = (ParameterSet) list.get(0);
			}
			tfFinallyPath.setText(para.getNameValues());

			list = systemAction
					.findnameValues(new Request(CommonVars.getCurrUser()),
							ParameterType.logDir);
			if (list != null && list.size() > 0) {
				para = (ParameterSet) list.get(0);
			}
			tfLogPath.setText(para.getNameValues());
		}
	}

	private void initParamEditState(boolean state) {
		tfActionId.setEnabled(state);
		tfBtNo.setEnabled(state);
		tfReceiverAddress.setEnabled(state);
		tfReceiverId.setEnabled(state);
		tfSenderAddress.setEnabled(state);
		tfSenderId.setEnabled(state);
		tfUserId.setEnabled(state);
		tfUserPrivateKey.setEnabled(state);
		tfIcCardNo.setEnabled(state);
		tfCertificateNo.setEnabled(state);
		tfOperatorName.setEnabled(state);
		tfOrganizationCode.setEnabled(state);
	}

	private void initLinkManEditState(boolean state) {
		tfAddress.setEnabled(state);
		tfDept.setEnabled(state);
		tfDuty.setEnabled(state);
		tfEmail.setEnabled(state);
		tfFax.setEnabled(state);
		tfImCode.setEnabled(state);
		tfImType.setEnabled(state);
		tfMobile.setEnabled(state);
		tfName.setEnabled(state);
		tfPhone.setEnabled(state);
		tfZipCode.setEnabled(state);
	}

//	public void initFtpEditState(boolean state) {
//		tfFtpAddress.setEnabled(state);
//		tfFtpName.setEnabled(state);
//		tfFtpPwd.setEnabled(state);
//	}

	public void initEdiPathState(boolean state) {
		tfSendPath.setEnabled(state);
		tfRecvPath.setEnabled(state);
		tfFinallyPath.setEnabled(state);
		tfLogPath.setEnabled(state);
	}

	public void initOtherState(boolean state) {
		cbSendNote.setEnabled(state);
		tfBtplsAddress.setEnabled(state);
		tfBtplsPort.setEnabled(state);
	}

	public boolean isLinkManEnable() {
		return linkManEnable;
	}

	public void setLinkManEnable(boolean linkManEnable) {
		this.linkManEnable = linkManEnable;
	}

	public boolean isParamsEnable() {
		return paramsEnable;
	}

	public void setParamsEnable(boolean paramsEnable) {
		this.paramsEnable = paramsEnable;
	}

	/**
	 * This method initializes tfIcCardNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfIcCardNo() {
		if (tfIcCardNo == null) {
			tfIcCardNo = new JTextField();
			tfIcCardNo.setBounds(new Rectangle(166, 279, 200, 22));
		}
		return tfIcCardNo;
	}

	/**
	 * This method initializes tfCertificateNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCertificateNo() {
		if (tfCertificateNo == null) {
			tfCertificateNo = new JTextField();
			tfCertificateNo.setBounds(new Rectangle(546, 279, 200, 22));
		}
		return tfCertificateNo;
	}

	/**
	 * This method initializes tfOperatorName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOperatorName() {
		if (tfOperatorName == null) {
			tfOperatorName = new JTextField();
			tfOperatorName.setBounds(new Rectangle(546, 339, 200, 22));
		}
		return tfOperatorName;
	}

	/**
	 * This method initializes tfOrganizationCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOrganizationCode() {
		if (tfOrganizationCode == null) {
			tfOrganizationCode = new JTextField();
			tfOrganizationCode.setBounds(new Rectangle(166, 339, 200, 22));
		}
		return tfOrganizationCode;
	}

	/**
	 * This method initializes pnEdiPath
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnEdiPath() {
		if (pnEdiPath == null) {
			lbLogPath = new JLabel();
			lbLogPath.setBounds(new Rectangle(100, 250, 120, 22));
			lbLogPath.setText("日志所在路径：");
			lbFinallyPath = new JLabel();
			lbFinallyPath.setBounds(new Rectangle(100, 190, 120, 22));
			lbFinallyPath.setText("处理完的存放路径：");
			lbRecvPath = new JLabel();
			lbRecvPath.setBounds(new Rectangle(100, 130, 120, 22));
			lbRecvPath.setText("回执存放路径：");
			lbSendPath = new JLabel();
			lbSendPath.setBounds(new Rectangle(100, 70, 120, 22));
			lbSendPath.setText("报文发送路径：");
			pnEdiPath = new JPanel();
			pnEdiPath.setLayout(null);
			pnEdiPath.add(lbSendPath, null);
			pnEdiPath.add(lbRecvPath, null);
			pnEdiPath.add(lbFinallyPath, null);
			pnEdiPath.add(lbLogPath, null);
			pnEdiPath.add(getTfSendPath(), null);
			pnEdiPath.add(getTfRecvPath(), null);
			pnEdiPath.add(getTfFinallyPath(), null);
			pnEdiPath.add(getTfLogPath(), null);
			pnEdiPath.add(getBtnSendPath(), null);
			pnEdiPath.add(getBtnRecvPath(), null);
			pnEdiPath.add(getBtnFinallyPath(), null);
			pnEdiPath.add(getBtnLogPath(), null);
		}
		return pnEdiPath;
	}

	/**
	 * This method initializes tfSendPath
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSendPath() {
		if (tfSendPath == null) {
			tfSendPath = new JTextField();
			tfSendPath.setBounds(new Rectangle(230, 70, 400, 22));
		}
		return tfSendPath;
	}

	/**
	 * This method initializes tfRecvPath
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRecvPath() {
		if (tfRecvPath == null) {
			tfRecvPath = new JTextField();
			tfRecvPath.setBounds(new Rectangle(230, 130, 400, 22));
		}
		return tfRecvPath;
	}

	/**
	 * This method initializes tfFinallyPath
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinallyPath() {
		if (tfFinallyPath == null) {
			tfFinallyPath = new JTextField();
			tfFinallyPath.setBounds(new Rectangle(230, 190, 400, 22));
		}
		return tfFinallyPath;
	}

	/**
	 * This method initializes tfLogPath
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLogPath() {
		if (tfLogPath == null) {
			tfLogPath = new JTextField();
			tfLogPath.setBounds(new Rectangle(230, 250, 400, 22));
		}
		return tfLogPath;
	}

	private boolean checkNull() {
		if (tfSendPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "报文发送路径不能为空！", "提示！", 2);
			return true;
		}
		if (tfRecvPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "回执存放路径不能为空！", "提示！", 2);
			return true;
		}
		if (tfFinallyPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "处理完的回执存放路径不能为空！", "提示！", 2);
			return true;
		}
		if (tfLogPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "日志路径不能为空！", "提示！", 2);
			return true;
		}

		return false;

	}

	private boolean checkRepeat() {
		if (tfSendPath.getText().equals(tfRecvPath.getText())) {
			JOptionPane.showMessageDialog(this, "注意：报文发送路径不能与回执存放路径相同！", "提示！",
					2);
			return true;
		}
		if (tfSendPath.getText().equals(tfFinallyPath.getText())) {
			JOptionPane.showMessageDialog(this, "注意：报文发送路径不能与处理完的回执存放路径相同！",
					"提示！", 2);
			return true;
		}
		if (tfRecvPath.getText().equals(tfFinallyPath.getText())) {
			JOptionPane.showMessageDialog(this, "注意：回执存放路径不能与处理完的回执存放路径相同！",
					"提示！", 2);
			return true;
		}
		return false;
	}

	/**
	 * This method initializes btnSendPath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendPath() {
		if (btnSendPath == null) {
			btnSendPath = new JButton();
			btnSendPath.setBounds(new Rectangle(630, 70, 22, 22));
			btnSendPath.setText("...");
			btnSendPath.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfSendPath);
				}
			});
		}
		return btnSendPath;
	}

	/**
	 * This method initializes btnRecvPath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRecvPath() {
		if (btnRecvPath == null) {
			btnRecvPath = new JButton();
			btnRecvPath.setBounds(new Rectangle(630, 130, 22, 22));
			btnRecvPath.setText("...");
			btnRecvPath.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfRecvPath);
				}
			});
		}
		return btnRecvPath;
	}

	/**
	 * This method initializes btnFinallyPath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinallyPath() {
		if (btnFinallyPath == null) {
			btnFinallyPath = new JButton();
			btnFinallyPath.setBounds(new Rectangle(630, 190, 22, 22));
			btnFinallyPath.setText("...");
			btnFinallyPath
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDir(tfFinallyPath);
						}
					});
		}
		return btnFinallyPath;
	}

	/**
	 * This method initializes btnLogPath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLogPath() {
		if (btnLogPath == null) {
			btnLogPath = new JButton();
			btnLogPath.setBounds(new Rectangle(630, 250, 22, 22));
			btnLogPath.setText("...");
			btnLogPath.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfLogPath);
				}
			});
		}
		return btnLogPath;
	}

	private void setDir(JTextField jtf) {
		if (jtf.getText() != null && !jtf.getText().equals("")) {
			File file = new File(jtf.getText());
			if (file.exists())
				setTempDir(jtf.getText());
		}
		JFileChooser fileChooser = new JFileChooser(getTempDir());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int state = fileChooser.showDialog(FmTcsSet.this, "选择路径");
		if (state == JFileChooser.APPROVE_OPTION) {
			fileChooser.getSelectedFile();
			File f = fileChooser.getSelectedFile();
			jtf.setText(f.getPath());
			setTempDir(jtf.getText());
		}
	}

	private String tempDir;
	private JPanel pnOther = null;
	private JCheckBox cbSendNote = null;
	private JLabel lbSendNot = null;
	private JLabel lbBtplsAddress = null;
	private JLabel lbBtplsPort = null;
	private JTextField tfBtplsAddress = null;
	private JTextField tfBtplsPort = null;

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnOther() {
		if (pnOther == null) {
			lbBtplsPort = new JLabel();
			lbBtplsPort.setBounds(new Rectangle(105, 150, 65, 18));
			lbBtplsPort.setText("平台端口：");
			lbBtplsAddress = new JLabel();
			lbBtplsAddress.setBounds(new Rectangle(105, 100, 65, 18));
			lbBtplsAddress.setText("平台地址：");
			lbSendNot = new JLabel();
			lbSendNot.setBounds(new Rectangle(130, 50, 129, 20));
			lbSendNot.setText("是否发送报关单附页");
			pnOther = new JPanel();
			pnOther.setLayout(null);
			pnOther.add(getCbSendNote(), null);
			pnOther.add(lbSendNot, null);
			pnOther.add(lbBtplsAddress, null);
			pnOther.add(lbBtplsPort, null);
			pnOther.add(getTfBtplsAddress(), null);
			pnOther.add(getTfBtplsPort(), null);

			btnNntAndProt = new JButton();
			btnNntAndProt.setText("网络连接测试");
			btnNntAndProt.setBounds(100, 191, 116, 29);
			btnNntAndProt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String ip = tfBtplsAddress.getText().trim();
						String stu = tfBtplsPort.getText().trim();
						if (ip != null && !ip.isEmpty() && stu != null
								&& !stu.isEmpty()) {
							int prot = Integer.parseInt(tfBtplsPort.getText()
									.trim());
							if (65536 > prot && prot > 0) {
								btnNntAndProt.setText("正在测试中,请稍后...");
								btnNntAndProt.setEnabled(false);
								// TestSwingWorker线程
								tsw = new TestSwingWorker();
								tsw.execute();
							} else {
								JOptionPane.showConfirmDialog(null,
										"输入的端口号必须是数字,且必须大于0小于65536", "提示信息",
										JOptionPane.CLOSED_OPTION, 1);
							}
						} else {
							JOptionPane.showConfirmDialog(null,
									"ip地址和端口号都是必填项", "提示信息",
									JOptionPane.CLOSED_OPTION, 0);
						}
					} catch (NumberFormatException nfe) {
						JOptionPane.showConfirmDialog(null, "输入的必须是数字", "提示信息",
								JOptionPane.CLOSED_OPTION, 0);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showConfirmDialog(null, "测试连接发生异常，异常信息为："
								+ e1.getMessage(), "提示信息",
								JOptionPane.CLOSED_OPTION, 0);
					}
				}
			});
			pnOther.add(btnNntAndProt);
		}
		return pnOther;
	}

	/**
	 * The method according to the IP address and port to test network
	 * connectivity
	 * 
	 */
	public class TestSwingWorker extends SwingWorker<String, Object> {

		@Override
		protected String doInBackground() throws Exception {
			String stu = null;
			FTPClient ftp = new FTPClient();
			try {
				ftp.setDefaultPort(Integer.parseInt(tfBtplsPort.getText()
						.trim()));
				ftp.connect(tfBtplsAddress.getText().trim());
				
				stu = "loginSucceed";
			} catch (SocketException e) {
				stu = "SocketException";
			} catch (IOException e) {
				stu = "IOException";
			}
			return stu;
		}

		@Override
		protected void done() {
			try {
				if (get() != null) {
					if (get().equals("loginSucceed")) {
						JOptionPane.showConfirmDialog(null, "连接成功", "提示信息",
								JOptionPane.CLOSED_OPTION, 1);
					} else if (get().equals("SocketException")) {
						JOptionPane.showMessageDialog(FmTcsSet.this,
								"网络连接失败,发生异常,在底层协议中存在错误," + "请检查ip和端口", "提示信息",
								0);
					} else {
						JOptionPane.showMessageDialog(FmTcsSet.this,
								"网络连接失败,发生异常,失败或中断的 I/O 操作生成的异常," + "请检查ip和端口",
								"提示信息", 0);
					}
				} else {
					JOptionPane.showMessageDialog(FmTcsSet.this, "网络连接信息为空",
							"提示信息", 0);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(FmTcsSet.this, "测试网络连接是否连通,网络连接失败", "提示信息",
						0);
			} finally {
				btnNntAndProt.setText("测试网络连接是否连通");
				btnNntAndProt.setEnabled(true);
			}
		}
	}

	/**
	 * 点击关闭按钮时，关闭测试网络连接是否连通的TestSwingWorker线程
	 */
	public void closeTestSwingWorker() {
		if (null != tsw) {
			if (!tsw.isDone()) {
				try {
					tsw.cancel(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(FmTcsSet.this,
							"网络测试关闭失败" + e.getMessage(), "提示信息", 0);
				}
			}
		}
	}

	/**
	 * This method initializes cbSendNote
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbSendNote() {
		if (cbSendNote == null) {
			cbSendNote = new JCheckBox();
			cbSendNote.setBounds(new Rectangle(100, 50, 21, 21));
		}
		return cbSendNote;
	}

	/**
	 * This method initializes tfBtplsAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBtplsAddress() {
		if (tfBtplsAddress == null) {
			tfBtplsAddress = new JTextField();
			tfBtplsAddress.setBounds(new Rectangle(194, 98, 450, 22));
		}
		return tfBtplsAddress;
	}

	/**
	 * This method initializes tfBtplsPort
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBtplsPort() {
		if (tfBtplsPort == null) {
			tfBtplsPort = new JTextField();
			tfBtplsPort.setBounds(new Rectangle(194, 148, 450, 22));
		}
		return tfBtplsPort;
	}
}
