/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.message.DgProcessCustomsMessageTCS;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.ContractSystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MessageHttpUtil;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.fpt.btplsinput.action.BtplsDownloadParaAction;
import com.bestway.common.fpt.btplsinput.entity.BtplsDownloadPara;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JRadioButton;

/**
 * by 2009-1-10 lm checked
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmFptParameterSet extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel contentPn = null;

	private JPanel pn = null;

	private JTextField tf = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;
	/** 状态 */
	private int dataState = DataState.BROWSE;

	private JPanel pn1 = null;

	private JLabel lb1 = null;

	private ManualDeclareAction manualDecleareAction = null;

	public FptManageAction fptManageAction = null;

	private FptMessageAction fptMessageAction = null;

	private JButton btnClose = null;

	private JPanel pn4 = null;

	private JLabel lb2 = null;

	private JCheckBox cbb = null;

	private JToolBar tb = null;

	private JPanel pn41 = null;

	private JPanel pn2 = null;

	private JLabel lb5 = null;

	private JLabel lb11 = null;

	private JLabel lb21 = null;

	private JLabel lb31 = null;

	private JLabel lb41 = null;

	private JTextField tfSendDir = null;

	private JTextField tfRecvDir = null;

	private JTextField tfFinallyDir = null;

	private JTextField tfLogDir = null;

	private JButton btnSendDir = null;

	private JButton btnRecvDir = null;

	private JButton btnFinallyDir = null;

	private JButton btnLogDir = null;

	private JPanel pn31 = null;

	private JTextField tfIdCard = null;

	private JLabel lb7 = null;

	private String tempDir = "./";

	private JComboBox cbbProjectType = null;

	private JLabel lb511 = null;

	private JButton btnFptAppHeadPara = null;

	private JLabel lb4111 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnAdd = null;

	private JButton btnUpdate = null;

	private JButton btnDel = null;

	private JScrollPane jScrollPane = null;

	private JTable tbDomain = null;

	private JTableListModel tableModel = null;

	private BtplsDownloadParaAction btplsDownloadParaAction = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JRadioButton jRadioButtonJbcs = null;

	private JRadioButton jRadioButtonJbcus = null;

	private JRadioButton jRadioButtonDzsc = null;

	private Integer contraceFrom = 0; // @jve:decl-index=0:

	private ContractSystemAction contractSystemAction = null;
	private CustomBaseAction customBaseAction;

	private JLabel jLabel5 = null;

	private JTextField tfRemoteHostICPwd = null;

	private JLabel jLabel6 = null;

	private JTextField tfRemoteHostAddress = null;

	private JLabel jLabel7 = null;

	private JTextField tfRemoteHostPort = null;

	private JButton btnSignTest = null;
	private JPanel panel;
	private JCheckBox cbbAppCurrentAmount;
	private JCheckBox cbbBillCurrentAmount;

	/**
	 * This is the default constructor
	 */
	public FmFptParameterSet() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		fptMessageAction = (FptMessageAction) CommonVars
				.getApplicationContext().getBean("fptMessageAction");
		fptMessageAction = (FptMessageAction) CommonVars
				.getApplicationContext().getBean("fptMessageAction");
		btplsDownloadParaAction = (BtplsDownloadParaAction) CommonVars
				.getApplicationContext().getBean("btplsDownloadParaAction");
		customBaseAction = (CustomBaseAction) CommonVars
		.getApplicationContext().getBean("customBaseAction");
		
		initialize();

	}

	/**
	 * this method is initializes this
	 */
	// private void initUIComponents() {
	// this.cbbProjectType.removeAllItems();
	// this.cbbProjectType.addItem(new ItemProperty(String
	// .valueOf(ProjectType.BCUS), "电子帐册"));
	// this.cbbProjectType.addItem(new ItemProperty(String
	// .valueOf(ProjectType.BCS), "电子化手册"));
	// this.cbbProjectType.addItem(new ItemProperty(String
	// .valueOf(ProjectType.DZSC), "电子手册"));
	// CustomBaseComboBoxEditor.getInstance()
	// .setComboBoxEditor(cbbProjectType);
	// this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
	// .getRender());
	// this.cbbProjectType.setSelectedIndex(-1);
	// }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("联网监管系统参数设置");
		this.setContentPane(getJTabbedPane());
		this.setSize(898, 731);
		manualDecleareAction.getBcusParaPurview(new Request(CommonVars
				.getCurrUser()));
		// initUIComponents();
		List list = new ArrayList();
		list = btplsDownloadParaAction.findAllBtplsDownloadPara();
		initDomainTable(list);
		fillWindow();
		dataState = DataState.BROWSE;
		setState();
	}

	/**
	 * this method is initializes this
	 */

	private void fillWindow() {

		String transferCode = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.TransferCode);
		tf.setText(transferCode);
		FptParameterSet parameterSet = fptManageAction
				.findTransParameterSet(new Request(CommonVars.getCurrUser(),
						true));

		String tomcatDir = fptManageAction.getTomcatDir();

		this.tfSendDir
				.setText(CommonUtils.isEmpty(parameterSet.getSendDir()) ? tomcatDir
						+ "\\fpt_mailbox\\send"
						: parameterSet.getSendDir());
		this.tfRecvDir
				.setText(CommonUtils.isEmpty(parameterSet.getRecvDir()) ? tomcatDir
						+ "\\fpt_mailbox\\riv"
						: parameterSet.getRecvDir());

		this.tfFinallyDir.setText(CommonUtils.isEmpty(parameterSet
				.getFinallyDir()) ? tomcatDir + "\\fpt_mailbox\\succ"
				: parameterSet.getFinallyDir());

		this.tfLogDir
				.setText(CommonUtils.isEmpty(parameterSet.getLogDir()) ? tomcatDir
						+ "\\fpt_mailbox\\log"
						: parameterSet.getLogDir());

		this.tfIdCard.setText(parameterSet.getIdCard());

		tfRemoteHostICPwd.setText(parameterSet.getRemoteHostICPwd());
		tfRemoteHostAddress.setText(parameterSet.getRemoteHostAddress());
		tfRemoteHostPort.setText(parameterSet.getRemoteHostPort());

//		this.tfAddress.setText(parameterSet.getFtpAddress());
//		this.tfPort.setText(parameterSet.getFtpPort() == null ? ""
//				: (parameterSet.getFtpPort() + ""));
//		this.tfUserName.setText(parameterSet.getFtpUserName());
//		this.tfPassword.setText(parameterSet.getFtpPassword());
		
		if (parameterSet == null
				|| parameterSet.getControlAppCurrentAmount() == null) {
			this.cbbAppCurrentAmount.setSelected(true);
		} else {
			this.cbbAppCurrentAmount.setSelected(parameterSet.getControlAppCurrentAmount());
		}
		if (parameterSet == null
				|| parameterSet.getControlBillCurrentAmount() == null) {
			this.cbbBillCurrentAmount.setSelected(true);
		} else {
			this.cbbBillCurrentAmount.setSelected(parameterSet.getControlBillCurrentAmount());
		}
		
		if (parameterSet == null
				|| parameterSet.getCustomsEnvelopUsedMultiple() == null) {
			this.cbb.setSelected(false);
		} else {
			this.cbb.setSelected(parameterSet.getCustomsEnvelopUsedMultiple());
		}

		contraceFrom = parameterSet.getProjectType();

		init(contraceFrom);

		// if(parameterSet == null){
		// if(parameterSet.getProjectType() == 0){
		// this.jRadioButtonJbcus.setSelected(true);
		// }if(parameterSet.getProjectType() == 1){
		// this.jRadioButtonJbcs.setSelected(true);
		// }if(parameterSet.getProjectType() == 2){
		// this.jRadioButtonDzsc.setSelected(true);
		// }
		// }else{
		// this.jRadioButtonDzsc.setSelected(false);
		// this.jRadioButtonJbcus.setSelected(false);
		// this.jRadioButtonJbcs.setSelected(false);
		// }

		// if (parameterSet.getProjectType() != null) {
		// this.cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(
		// parameterSet.getProjectType().toString(),
		// this.cbbProjectType));
		// } else {
		// this.cbbProjectType.setSelectedIndex(-1);
		// }
	}

	public void init(Integer contraceFrom) {
		if (contraceFrom == 0) {
			this.jRadioButtonJbcus.setSelected(true);
		} else if (contraceFrom == 1) {
			this.jRadioButtonJbcs.setSelected(true);
		} else if (contraceFrom == 3) {
			this.jRadioButtonDzsc.setSelected(true);
		}
		System.out.print("xxx" + contraceFrom);
	}

	/**
	 * This method initializes contentPn
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getContentPn() {
		if (contentPn == null) {
			contentPn = new javax.swing.JPanel();
			contentPn.setLayout(new BorderLayout());
			contentPn.setBorder(javax.swing.BorderFactory.createCompoundBorder(
					null, null));
			contentPn.add(getPn(), java.awt.BorderLayout.CENTER);
			contentPn.add(getPn1(), java.awt.BorderLayout.NORTH);
			contentPn.add(getTb(), BorderLayout.SOUTH);
		}
		return contentPn;
	}

	/**
	 * 
	 * This method initializes pn
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPn() {
		if (pn == null) {
			lb2 = new JLabel();
			lb2.setBounds(new Rectangle(354, 20, 68, 23));
			lb2.setText("2，同一关封是否可以使用于多个报关单");
			lb2.setForeground(Color.blue);
			lb2.setVisible(false);
			lb1 = new JLabel();
			lb1.setBounds(new Rectangle(124, 22, 34, 18));
			lb1.setText("编号");
			lb1.setVisible(false);
			javax.swing.JLabel jLabel = new JLabel();

			pn = new JPanel();
			pn.setLayout(null);
			jLabel.setText("1，关封申请表编号");
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setBounds(39, 18, 76, 21);
			jLabel.setVisible(false);
			pn.setForeground(new java.awt.Color(51, 0, 255));
			pn.add(jLabel, null);
			pn.add(getTf(), null);
			pn.add(lb1, null);
			pn.add(lb2, null);
			pn.add(getCbb(), null);
			pn.add(getPn41(), null);
		}
		return pn;
	}

	/**
	 * 
	 * This method initializes tf
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTf() {
		if (tf == null) {
			tf = new JTextField();
			tf.setBounds(164, 22, 178, 22);
			tf.setVisible(false);
		}
		return tf;
	}

	/**
	 * 检查路径是否一致
	 * 
	 * @return
	 */

	private boolean checkPathIsDuplicate() {
		if (!"".equals(this.tfSendDir.getText())
				&& !"".equals(this.tfRecvDir.getText())
				&& this.tfSendDir.getText().equals(this.tfRecvDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文发送和接收路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfSendDir.getText())
				&& !"".equals(this.tfFinallyDir.getText())
				&& this.tfSendDir.getText().equals(this.tfFinallyDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文发送和处理完成的回执存放路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfSendDir.getText())
				&& !"".equals(this.tfLogDir.getText())
				&& this.tfSendDir.getText().equals(this.tfLogDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文发送和日志存放路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfRecvDir.getText())
				&& !"".equals(this.tfFinallyDir.getText())
				&& this.tfRecvDir.getText().equals(this.tfFinallyDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文接收和处理完成的回执存放路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfRecvDir.getText())
				&& !"".equals(this.tfLogDir.getText())
				&& this.tfRecvDir.getText().equals(this.tfLogDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文接收和日志存放路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfFinallyDir.getText())
				&& !"".equals(this.tfLogDir.getText())
				&& this.tfFinallyDir.getText().equals(this.tfLogDir.getText())) {
			JOptionPane.showMessageDialog(this, "处理完成的回执存放和日志存放路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
//		if ("".equals(this.tfPort.getText()) | this.tfPort.getText() == null) {
//			JOptionPane.showMessageDialog(this, "请填写端口", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//			return true;
//		}
		return false;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					manualDecleareAction.saveBpara(
							new Request(CommonVars.getCurrUser()),
							BcusParameter.TransferCode, tf.getText());
					if (checkPathIsDuplicate()) {
						return;
					}
					FptParameterSet parameterSet = fptManageAction
							.findTransParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					parameterSet.setCustomsEnvelopUsedMultiple(cbb.isSelected());
					parameterSet.setSendDir(tfSendDir.getText().trim());
					parameterSet.setRecvDir(tfRecvDir.getText().trim());
					parameterSet.setFinallyDir(tfFinallyDir.getText().trim());
					parameterSet.setLogDir(tfLogDir.getText().trim());
					parameterSet.setIdCard(tfIdCard.getText().trim());
					parameterSet.setRemoteHostICPwd(tfRemoteHostICPwd.getText()
							.trim());
					parameterSet.setRemoteHostAddress(tfRemoteHostAddress
							.getText().trim());
					parameterSet.setRemoteHostPort(tfRemoteHostPort.getText()
							.trim());

//					parameterSet.setFtpAddress(tfAddress.getText());
//					parameterSet.setFtpPort(tfPort.getText() == null ? null
//							: Integer.parseInt(tfPort.getText().trim()));
//					parameterSet.setFtpUserName(tfUserName.getText().trim());
//					parameterSet.setFtpPassword(tfPassword.getText().trim());

					// 保存手册老远来源选项
					// ParameterSet para = null;
					// List list = contractSystemAction.findnameValues(
					// new Request(CommonVars.getCurrUser()),
					// ParameterType.contractFrom);
					// para = (ParameterSet) list.get(0);
					// String values = (jRadioButtonJbcus.isSelected() ? "0"
					// : (jRadioButtonJbcs.isSelected() ? "1" : "2"));
					// para.setNameValues(values);
					// CommonVars.setContractFrom(values);
					// contractSystemAction.saveValues(new Request(CommonVars
					// .getCurrUser()), para);
					// list = contractSystemAction.findnameValues(new Request(
					// CommonVars.getCurrUser()),
					// ParameterType.isVailCustomsOther);
					// para = (ParameterSet) list.get(0);
					// para.setNameValues(values);

					parameterSet.setProjectType(contraceFrom);
					parameterSet.setControlAppCurrentAmount(cbbAppCurrentAmount.isSelected());
					parameterSet.setControlBillCurrentAmount(cbbBillCurrentAmount.isSelected());
					// if (cbbProjectType.getSelectedItem() != null) {
					// ItemProperty item = (ItemProperty) cbbProjectType
					// .getSelectedItem();
					// int projectType = Integer.parseInt(item.getCode());
					// parameterSet.setProjectType(projectType);
					// } else {
					// parameterSet.setProjectType(null);
					// }
					fptManageAction.saveTransParameterSet(new Request(
							CommonVars.getCurrUser(), true), parameterSet);
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
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

	/*
	 * 设置组键的状态，默认为浏览状态。
	 */
	private void setState() {

		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);

		cbb.setEnabled(dataState != DataState.BROWSE);

		this.tfFinallyDir.setEditable(dataState != DataState.BROWSE);

		this.tfLogDir.setEditable(dataState != DataState.BROWSE);
		this.tfRecvDir.setEditable(dataState != DataState.BROWSE);
		this.tfSendDir.setEditable(dataState != DataState.BROWSE);

		this.tfIdCard.setEditable(dataState != DataState.BROWSE);
		this.btnFinallyDir.setEnabled(dataState != DataState.BROWSE);
		this.btnLogDir.setEnabled(dataState != DataState.BROWSE);
		this.btnRecvDir.setEnabled(dataState != DataState.BROWSE);
		this.btnSendDir.setEnabled(dataState != DataState.BROWSE);
		this.tf.setEditable(!(dataState == DataState.BROWSE));
		// this.cbbProjectType.setEnabled(dataState != DataState.BROWSE);
		this.jRadioButtonDzsc.setEnabled(dataState != DataState.BROWSE);
		this.jRadioButtonJbcus.setEnabled(dataState != DataState.BROWSE);
		this.jRadioButtonJbcs.setEnabled(dataState != DataState.BROWSE);
		this.cbbAppCurrentAmount.setEnabled(dataState != DataState.BROWSE);
		this.cbbBillCurrentAmount.setEnabled(dataState != DataState.BROWSE);
		this.tfRemoteHostICPwd.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostAddress.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostPort.setEditable(dataState != DataState.BROWSE);
	}

	/**
	 * 
	 * This method initializes pn1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			pn1.setLayout(new BorderLayout());
			jLabel17.setText("");
			jLabel17.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("转厂管理参数设置");
			jLabel18.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			pn1.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pn1.setBackground(java.awt.Color.white);
			pn1.add(jLabel17, java.awt.BorderLayout.WEST);
			pn1.add(jLabel18, java.awt.BorderLayout.CENTER);
			pn1.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return pn1;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFptParameterSet.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes pn4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn4() {
		if (pn4 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.CENTER);
			pn4 = new JPanel();
			pn4.setLayout(flowLayout);
			pn4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			pn4.add(getBtnFptAppHeadPara(), null);
			pn4.add(getBtnEdit(), null);
			pn4.add(getBtnSave(), null);
			pn4.add(getBtnClose(), null);
		}
		return pn4;
	}

	/**
	 * This method initializes cbb
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbb() {
		if (cbb == null) {
			cbb = new JCheckBox();
			cbb.setBounds(new Rectangle(424, 18, 230, 27));
			cbb.setText("可以使用于多个报关单");
			cbb.setVisible(false);
		}
		return cbb;
	}

	/**
	 * This method initializes tb
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTb() {
		if (tb == null) {
			tb = new JToolBar();
			tb.add(getPn4());
		}
		return tb;
	}

	/**
	 * This method initializes pn41
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn41() {
		if (pn41 == null) {
			lb511 = new JLabel();
			lb511.setBounds(new Rectangle(59, 22, 81, 17));
			lb511.setText("手册类型:");
			lb511.setFont(new Font("Dialog", Font.BOLD, 13));
			lb511.setForeground(Color.blue);
			lb7 = new JLabel();
			lb7.setBounds(new Rectangle(49, 0, 397, 20));
			lb7.setText("\u6ce8\u610f:\u6240\u6709\u8def\u5f84\u5747\u6307\u5728\u670d\u52a1\u5668\u7aef\u7684\u8def\u5f84,\u8bf7\u5148\u5728\u670d\u52a1\u5668\u7aef\u8fdb\u884c\u8bbe\u7f6e.");
			lb7.setForeground(Color.RED);
			pn41 = new JPanel();
			pn41.setLayout(null);
			pn41.setBounds(new Rectangle(24, 0, 751, 572));
			pn41.add(lb7, null);
			// pn41.add(getCbbProjectType(), null);
			pn41.add(lb511, null);
			pn41.add(getJRadioButton(), null);
			pn41.add(getJRadioButtonJbcus1(), null);
			pn41.add(getJRadioButtonJbcus12(), null);
			pn41.add(getPn31(), null);
			pn41.add(getPn2(), null);
			pn41.add(getPanel());
		}
		return pn41;
	}

	/**
	 * This method initializes pn2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			lb41 = new JLabel();
			lb41.setBounds(new Rectangle(55, 113, 86, 19));
			lb41.setText("\u65e5\u5fd7\u6240\u5728\u8def\u5f84");
			lb31 = new JLabel();
			lb31.setBounds(new Rectangle(55, 92, 86, 18));
			lb31.setText("\u5b58\u653e\u8def\u5f84");
			lb21 = new JLabel();
			lb21.setBounds(new Rectangle(55, 75, 86, 18));
			lb21.setText("\u5904\u7406\u5b8c\u7684\u56de\u6267");
			lb11 = new JLabel();
			lb11.setBounds(new Rectangle(55, 51, 86, 17));
			lb11.setText("\u56de\u6267\u5b58\u653e\u8def\u5f84");
			lb5 = new JLabel();
			lb5.setBounds(new Rectangle(55, 22, 86, 22));
			lb5.setText("\u62a5\u6587\u53d1\u9001\u8def\u5f84");
			pn2 = new JPanel();
			pn2.setLayout(null);
			pn2.setBorder(BorderFactory.createTitledBorder(null,
					"\u8def\u5f84\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pn2.setBounds(new Rectangle(49, 44, 596, 141));
			pn2.add(lb5, null);
			pn2.add(lb11, null);
			pn2.add(lb21, null);
			pn2.add(lb31, null);
			pn2.add(lb41, null);
			pn2.add(getTfSendDir(), null);
			pn2.add(getTfRecvDir(), null);
			pn2.add(getTfFinallyDir(), null);
			pn2.add(getTfLogDir(), null);
			pn2.add(getBtnSendDir(), null);
			pn2.add(getBtnRecvDir(), null);
			pn2.add(getBtnFinallyDir(), null);
			pn2.add(getBtnLogDir(), null);
		}
		return pn2;
	}

	/**
	 * This method initializes tfSendDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSendDir() {
		if (tfSendDir == null) {
			tfSendDir = new JTextField();
			tfSendDir.setBounds(new Rectangle(141, 23, 304, 23));
		}
		return tfSendDir;
	}

	/**
	 * This method initializes tfRecvDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRecvDir() {
		if (tfRecvDir == null) {
			tfRecvDir = new JTextField();
			tfRecvDir.setBounds(new Rectangle(141, 51, 304, 23));
		}
		return tfRecvDir;
	}

	/**
	 * This method initializes tfFinallyDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinallyDir() {
		if (tfFinallyDir == null) {
			tfFinallyDir = new JTextField();
			tfFinallyDir.setBounds(new Rectangle(141, 80, 304, 23));
		}
		return tfFinallyDir;
	}

	/**
	 * This method initializes tfLogDir
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLogDir() {
		if (tfLogDir == null) {
			tfLogDir = new JTextField();
			tfLogDir.setBounds(new Rectangle(141, 111, 304, 23));
		}
		return tfLogDir;
	}

	/**
	 * This method initializes btnSendDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendDir() {
		if (btnSendDir == null) {
			btnSendDir = new JButton();
			btnSendDir.setBounds(new Rectangle(445, 23, 23, 23));
			btnSendDir.setText("...");
			btnSendDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfSendDir);
				}
			});
		}
		return btnSendDir;
	}

	/**
	 * This method initializes btnRecvDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRecvDir() {
		if (btnRecvDir == null) {
			btnRecvDir = new JButton();
			btnRecvDir.setBounds(new Rectangle(445, 51, 23, 22));
			btnRecvDir.setText("...");
			btnRecvDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfRecvDir);
				}
			});
		}
		return btnRecvDir;
	}

	/**
	 * This method initializes btnFinallyDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinallyDir() {
		if (btnFinallyDir == null) {
			btnFinallyDir = new JButton();
			btnFinallyDir.setBounds(new Rectangle(445, 80, 23, 22));
			btnFinallyDir.setText("...");
			btnFinallyDir
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDir(tfFinallyDir);
						}
					});
		}
		return btnFinallyDir;
	}

	/**
	 * This method initializes btnLogDir
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLogDir() {
		if (btnLogDir == null) {
			btnLogDir = new JButton();
			btnLogDir.setBounds(new Rectangle(445, 111, 23, 23));
			btnLogDir.setText("...");
			btnLogDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfLogDir);
				}
			});
		}
		return btnLogDir;
	}

	/**
	 * This method initializes pn31
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn31() {
		if (pn31 == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(54, 106, 83, 20));
			jLabel7.setText("端口");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(54, 80, 83, 21));
			jLabel6.setText("远程地址");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(54, 58, 83, 19));
			jLabel5.setText("密码");
			lb4111 = new JLabel();
			lb4111.setBounds(new Rectangle(54, 28, 83, 23));
			lb4111.setText("操作员卡号");
			pn31 = new JPanel();
			pn31.setLayout(null);
			pn31.setBorder(BorderFactory.createTitledBorder(null, "海关申报参数设置",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pn31.setBounds(new Rectangle(49, 186, 596, 132));
			pn31.add(getTfIdCard(), null);
			pn31.add(lb4111, null);
			pn31.add(jLabel5, null);
			pn31.add(getTfRemoteHostICPwd(), null);
			pn31.add(jLabel6, null);
			pn31.add(getTfRemoteHostAddress(), null);
			pn31.add(jLabel7, null);
			pn31.add(getTfRemoteHostPort(), null);
			pn31.add(getBtnSignTest(), null);
		}
		return pn31;
	}

	/**
	 * This method initializes tfIdCard
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfIdCard() {
		if (tfIdCard == null) {
			tfIdCard = new JTextField();
			tfIdCard.setBounds(new Rectangle(144, 28, 309, 23));
		}
		return tfIdCard;
	}

	/**
	 * 设置路径
	 * 
	 * @param tf
	 */
	private void setDir(JTextField tf) {
		if (tf.getText() != null && !"".equals(tf.getText())) {
			File file = new File(tf.getText());
			if (file.exists())
				setTempDir(tf.getText());
		}
		JFileChooser fileChooser = new JFileChooser(getTempDir());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int state = fileChooser.showDialog(FmFptParameterSet.this, "选择路径");
		if (state == JFileChooser.APPROVE_OPTION) {
			fileChooser.getSelectedFile();
			File f = fileChooser.getSelectedFile();
			tf.setText(f.getPath());
			setTempDir(tf.getText());
		}
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * This method initializes cbbProjectType
	 * 
	 * @return javax.swing.JComboBox
	 */
	// private JComboBox getCbbProjectType() {
	// if (cbbProjectType == null) {
	// cbbProjectType = new JComboBox();
	// cbbProjectType.setBounds(new Rectangle(193, 275, 304, 24));
	// }
	// return cbbProjectType;
	// }

	/**
	 * This method initializes btnFptAppHeadPara
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFptAppHeadPara() {
		if (btnFptAppHeadPara == null) {
			btnFptAppHeadPara = new JButton();
			btnFptAppHeadPara.setText("申请表参数设置");
			btnFptAppHeadPara.setForeground(Color.blue);
			btnFptAppHeadPara
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgFptAppHeadByCompany dg = new DgFptAppHeadByCompany();
							dg.setVisible(true);
						}
					});
		}
		return btnFptAppHeadPara;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("参数设置", null, getContentPn(), null);
			jTabbedPane.addTab("深加工平台下载参数设置", null, getJPanel(), null);
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnUpdate());
			jJToolBarBar.add(getBtnDel());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(80, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DgBtplsDowloadPara dg = new DgBtplsDowloadPara();
					dg.setDataState(DataState.ADD);
					dg.setVisible(true);
					if (dg.isOk()) {
						List list = btplsDownloadParaAction
								.findAllBtplsDownloadPara();
						if (list != null) {
							initDomainTable(list);
						}
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes btnUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton();
			btnUpdate.setText("修改");
			btnUpdate.setPreferredSize(new Dimension(80, 30));
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmFptParameterSet.this,
								"请选择修改的数据", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					BtplsDownloadPara btplsDownloadPara = (BtplsDownloadPara) tableModel
							.getCurrentRow();
					DgBtplsDowloadPara dg = new DgBtplsDowloadPara();
					dg.setBtplsDowloadPara(btplsDownloadPara);
					dg.setVisible(true);
					List list = btplsDownloadParaAction
							.findAllBtplsDownloadPara();
					if (list != null) {
						initDomainTable(list);
					}
				}

			});
		}
		return btnUpdate;
	}

	/**
	 * This method initializes btnDel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("删除");
			btnDel.setPreferredSize(new Dimension(80, 30));
			btnDel.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmFptParameterSet.this,
								"请选择要删除的数据", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					BtplsDownloadPara DownloadPara = (BtplsDownloadPara) tableModel
							.getCurrentRow();
					if (DownloadPara != null) {
						if (JOptionPane.showConfirmDialog(
								FmFptParameterSet.this, "你确定要删除吗？", "提示！",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							btplsDownloadParaAction
									.deleteBtplsDownloadPara(DownloadPara);
							tableModel.deleteRow(DownloadPara);
						}
					}
				}
			});
		}
		return btnDel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbDomain());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbDomain
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDomain() {
		if (tbDomain == null) {
			tbDomain = new JTable();
		}

		return tbDomain;
	}

	/**
	 * 初始化域名表
	 * 
	 * @param list
	 */
	private void initDomainTable(List list) {
		tableModel = new JTableListModel(tbDomain, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("海关编码", "scmCoc.code", 100));
						list.add(addColumn("海关名称", "scmCoc.name", 250));
						list.add(addColumn("IP地址", "ipAddre", 150));
						list.add(addColumn("端口", "port", 50));
						return list;
					}
				});
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButtonJbcs == null) {
			jRadioButtonJbcs = new JRadioButton();
			jRadioButtonJbcs.setBounds(new Rectangle(142, 20, 90, 19));
			jRadioButtonJbcs.setSelected(true);
			jRadioButtonJbcs.setText("电子化手册");
			jRadioButtonJbcs.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						contraceFrom = 1;
						jRadioButtonJbcus.setSelected(false);
						jRadioButtonDzsc.setSelected(false);

					}
				}
			});
		}
		return jRadioButtonJbcs;
	}

	/**
	 * This method initializes jRadioButtonJbcus1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonJbcus1() {
		if (jRadioButtonJbcus == null) {
			jRadioButtonJbcus = new JRadioButton();
			jRadioButtonJbcus.setBounds(new Rectangle(249, 20, 77, 19));
			jRadioButtonJbcus.setText("电子账册");
			jRadioButtonJbcus
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
								contraceFrom = 0;
								jRadioButtonJbcs.setSelected(false);
								jRadioButtonDzsc.setSelected(false);
							}
						}
					});
		}
		return jRadioButtonJbcus;
	}

	/**
	 * This method initializes jRadioButtonJbcus1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonJbcus12() {
		if (jRadioButtonDzsc == null) {
			jRadioButtonDzsc = new JRadioButton();
			jRadioButtonDzsc.setBounds(new Rectangle(350, 20, 77, 19));
			jRadioButtonDzsc.setText("电子手册");
			jRadioButtonDzsc.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						contraceFrom = 3;
						jRadioButtonJbcus.setSelected(false);
						jRadioButtonJbcs.setSelected(false);
					}
				}
			});
		}
		return jRadioButtonDzsc;
	}

	/**
	 * @param contraceFrom
	 *            The contraceFrom to set.
	 */
	// private class RadioActionListner1 implements ActionListener {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (jRadioButtonJbcs.isSelected()) {
	// FmFptParameterSet.this.setContraceFrom(1);
	// } else if (jRadioButtonJbcus.isSelected()) {
	// FmFptParameterSet.this.setContraceFrom(0);
	// } else if (jRadioButtonDzsc.isSelected()) {
	// FmFptParameterSet.this.setContraceFrom(3);
	// }
	// }
	// }

	/**
	 * This method initializes tfRemoteHostICPwd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostICPwd() {
		if (tfRemoteHostICPwd == null) {
			tfRemoteHostICPwd = new JTextField();
			tfRemoteHostICPwd.setBounds(new Rectangle(144, 53, 309, 22));
		}
		return tfRemoteHostICPwd;
	}

	/**
	 * This method initializes tfRemoteHostAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostAddress() {
		if (tfRemoteHostAddress == null) {
			tfRemoteHostAddress = new JTextField();
			tfRemoteHostAddress.setBounds(new Rectangle(144, 79, 309, 23));
		}
		return tfRemoteHostAddress;
	}

	/**
	 * This method initializes tfRemoteHostPort
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostPort() {
		if (tfRemoteHostPort == null) {
			tfRemoteHostPort = new JTextField();
			tfRemoteHostPort.setBounds(new Rectangle(144, 105, 309, 22));
		}
		return tfRemoteHostPort;
	}

	/**
	 * This method initializes btnSignTest
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSignTest() {
		if (btnSignTest == null) {
			btnSignTest = new JButton();
			btnSignTest.setBounds(new Rectangle(462, 29, 122, 28));
			btnSignTest.setText("读卡测试");
			btnSignTest.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						String signInfo = fptMessageAction.testReadCard(
								new Request(CommonVars.getCurrUser()), "读卡加签测试");
						JOptionPane.showMessageDialog(FmFptParameterSet.this,
								"读卡测试成功!加签结果为" + signInfo.trim(), "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmFptParameterSet.this,
								"读卡测试失败!失败原因为：" + ex.getMessage(), "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnSignTest;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "\u6570\u91CF\u63A7\u5236", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
			panel.setBounds(49, 340, 596, 50);
			panel.setLayout(null);
			panel.add(getCbbAppCurrentAmount());
			panel.add(getCbbBillCurrentAmount());
		}
		return panel;
	}
	private JCheckBox getCbbAppCurrentAmount() {
		if (cbbAppCurrentAmount == null) {
			cbbAppCurrentAmount = new JCheckBox("是否控制申请单当前余量");
			cbbAppCurrentAmount.setSelected(true);
			cbbAppCurrentAmount.setBounds(52, 21, 157, 23);
		}
		return cbbAppCurrentAmount;
	}
	private JCheckBox getCbbBillCurrentAmount() {
		if (cbbBillCurrentAmount == null) {
			cbbBillCurrentAmount = new JCheckBox("是否控制收发货当前余量");
			cbbBillCurrentAmount.setSelected(true);
			cbbBillCurrentAmount.setBounds(257, 21, 168, 23);
		}
		return cbbBillCurrentAmount;
	}
} // @jve:decl-index=0:visual-constraint="10,16"
