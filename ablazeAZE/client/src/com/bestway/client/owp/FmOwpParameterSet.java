/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.owp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.cspcard.entity.ICCardInfo;
import com.bestway.owp.action.OwpMessageAction;
import com.bestway.owp.entity.OwpParameterSet;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * by 2009-1-10 lm checked
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmOwpParameterSet extends JInternalFrameBase {

	private javax.swing.JPanel contentPn = null;

	private JPanel pn = null;

	private JButton btnSave = null;

	private JButton btnEdit = null;
	
	private int dataState = DataState.BROWSE;

	private JPanel pn1 = null;

	private OwpMessageAction owpMessageAction = null;

	private JButton btnClose = null;

	private JPanel pn4 = null;

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

	private JRadioButton rbFromCard = null;

	private JRadioButton rbFromManual = null;

	private JTabbedPane tabPn = null;

	private JPanel pn51 = null;

	private JLabel lb51 = null;

	private JLabel lb6 = null;

	private JTextField tfSeqNo = null;

	private JTextField tfPwd = null;

	private JCheckBox cbRemoteSignData = null;

	private JTextField tfRemoteHostAddress = null;

	private JButton btnReadCard = null;

	private JPanel pn61 = null;

	private JTextField tfIdCard = null;

	private JLabel lb411 = null;

	private JLabel lb7 = null;

	private String tempDir = "./";

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private ButtonGroup buttonGroup1 = null; // @jve:decl-index=0:

	private JComboBox cbbProjectType = null;

	private JLabel lb511 = null;

	private JCheckBox cbPort9097Open = null;

	private JPanel pn3 = null;

	private JRadioButton rbNormal = null;

	private JRadioButton rbEnhance = null;

	private JPanel pn5 = null;

	private JLabel lb3 = null;

	private JLabel lb4 = null;

	private JTextField tfRemoteHostPort = null;

	/**
	 * This is the default constructor
	 */
	public FmOwpParameterSet() {
		super();
//		this.fptManageAction = (FptManageAction) CommonVars
//				.getApplicationContext().getBean("fptManageAction");
		owpMessageAction = (OwpMessageAction) CommonVars
				.getApplicationContext().getBean("owpMessageAction");
		initialize();

	}
	/**
	 * this method is initializes this
	 */
	private void initUIComponents() {
		this.cbbProjectType.removeAllItems();
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCUS), "电子帐册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.BCS), "电子化手册"));
		this.cbbProjectType.addItem(new ItemProperty(String
				.valueOf(ProjectType.DZSC), "电子手册"));
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(cbbProjectType);
		this.cbbProjectType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbProjectType.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("联网监管系统参数设置");
		this.setSize(895, 649);
		this.setContentPane(getContentPn());
		initUIComponents();
		fillWindow();
		dataState = DataState.BROWSE;
		setState();
		getButtonGroup();//给JRadioButton添加分组
		getButtonGroup1();//给JRadioButton添加分组

	}
	/**
	 * this method is initializes this
	 */

	private void fillWindow() {
		OwpParameterSet parameterSet = (OwpParameterSet)owpMessageAction
				.findCspMessageDirSet(new Request(CommonVars.getCurrUser(),
						true));
		this.tfSendDir.setText(parameterSet.getSendDir());
		this.tfRecvDir.setText(parameterSet.getRecvDir());
		this.tfFinallyDir.setText(parameterSet.getFinallyDir());
		this.tfLogDir.setText(parameterSet.getLogDir());
		if (parameterSet.getReadFromCard() != null
				&& parameterSet.getReadFromCard()) {
			this.rbFromCard.setSelected(true);
		} else {
			this.rbFromManual.setSelected(true);
		}
		this.cbRemoteSignData
				.setSelected(parameterSet.getRemoteSignData() != null
						&& parameterSet.getRemoteSignData());
		if (parameterSet.getRemoteDxpMail() != null
				&& parameterSet.getRemoteDxpMail()) {
			this.rbEnhance.setSelected(true);
		} else {
			this.rbNormal.setSelected(true);
		}
		this.cbPort9097Open
				.setSelected(parameterSet.getPort9097IsOpen() != null
						&& parameterSet.getPort9097IsOpen());

		this.tfRemoteHostAddress.setText(parameterSet.getRemoteHostAddress());
		this.tfRemoteHostPort.setText(parameterSet.getRemoteHostPort());
		this.tfSeqNo.setText(parameterSet.getSeqNo());
		this.tfPwd.setText(parameterSet.getPwd());
		this.tfIdCard.setText(parameterSet.getIdCard());		
		if (parameterSet.getProjectType() != null) {
			this.cbbProjectType.setSelectedIndex(ItemProperty.getIndexByCode(
					parameterSet.getProjectType().toString(),
					this.cbbProjectType));
		} else {
			this.cbbProjectType.setSelectedIndex(-1);
		}
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
			contentPn.setBorder(javax.swing.BorderFactory
					.createCompoundBorder(null, null));
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
			pn = new JPanel();
			pn.setLayout(null);
			pn.setForeground(new java.awt.Color(51, 0, 255));
			pn.add(getPn41(), null);
		}
		return pn;
	}

	/**
	 * 棿?路径是否丿?
	 * @return
	 */

	private boolean checkPathIsDuplicate() {
		if (!"".equals(this.tfSendDir.getText())
				&& !"".equals(this.tfRecvDir.getText())
				&& this.tfSendDir.getText().equals(this.tfRecvDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文发送路径和接收路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfSendDir.getText())
				&& !"".equals(this.tfFinallyDir.getText())
				&& this.tfSendDir.getText().equals(this.tfFinallyDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文发送路径和处理完成的回执存放路径不能一样", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfSendDir.getText())
				&& !"".equals(this.tfLogDir.getText())
				&& this.tfSendDir.getText().equals(this.tfLogDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文发送路径和日志存放路径不能一样！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (!"".equals(this.tfRecvDir.getText())
				&& !"".equals(this.tfFinallyDir.getText())
				&& this.tfRecvDir.getText().equals(this.tfFinallyDir.getText())) {
			JOptionPane.showMessageDialog(this, "报文接收和处理完成的回执存放路径不能一样", "提示",
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
			JOptionPane.showMessageDialog(this, "处理完成的回执存放和日志存放路径不能一样", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbRemoteSignData.isSelected()
				&& this.tfRemoteHostAddress.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(FmOwpParameterSet.this,
					"请输入远程加签服务器地址", "提示", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if (this.cbRemoteSignData.isSelected()
				&& this.tfRemoteHostPort.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(FmOwpParameterSet.this,
					"请输入远程加签服务器端口", "提示", JOptionPane.ERROR_MESSAGE);
			return true;
		}
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
					if (checkPathIsDuplicate()) {
						return;
					}
					OwpParameterSet parameterSet = (OwpParameterSet)owpMessageAction
							.findCspMessageDirSet(new Request(CommonVars
									.getCurrUser(), true));
					parameterSet.setSendDir(tfSendDir.getText().trim());
					parameterSet.setRecvDir(tfRecvDir.getText().trim());
					parameterSet.setFinallyDir(tfFinallyDir.getText().trim());
					parameterSet.setLogDir(tfLogDir.getText().trim());
					parameterSet.setSeqNo(tfSeqNo.getText().trim());
					parameterSet.setPwd(tfPwd.getText().trim());
					parameterSet.setReadFromCard(rbFromCard.isSelected());
					parameterSet.setIdCard(tfIdCard.getText().trim());
					parameterSet.setRemoteSignData(cbRemoteSignData
							.isSelected());
					parameterSet.setRemoteDxpMail(rbEnhance.isSelected());
					parameterSet.setPort9097IsOpen(cbPort9097Open.isSelected());

					parameterSet.setRemoteHostAddress(tfRemoteHostAddress
							.getText());
					parameterSet.setRemoteHostPort(tfRemoteHostPort
							.getText());
					if (cbbProjectType.getSelectedItem() != null) {
						ItemProperty item = (ItemProperty) cbbProjectType
								.getSelectedItem();
						int projectType = Integer.parseInt(item.getCode());
						parameterSet.setProjectType(projectType);
					} else {
						parameterSet.setProjectType(null);
					}
					owpMessageAction.saveCspMessageDirSet(new Request(
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
	 *设置组键的状态，默认为浏览状态?
	 * 
	 */
	private void setState() {
		
		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);

		this.rbFromCard.setEnabled(dataState != DataState.BROWSE);
		this.rbFromManual.setEnabled(dataState != DataState.BROWSE);

		this.tfFinallyDir.setEditable(dataState != DataState.BROWSE);

		this.tfLogDir.setEditable(dataState != DataState.BROWSE);
		this.tfRecvDir.setEditable(dataState != DataState.BROWSE);
		this.tfSendDir.setEditable(dataState != DataState.BROWSE);

		this.tfIdCard.setEditable(dataState != DataState.BROWSE);
		this.tfPwd.setEditable(dataState != DataState.BROWSE);
		this.tfSeqNo.setEditable(dataState != DataState.BROWSE);
		this.btnFinallyDir.setEnabled(dataState != DataState.BROWSE);
		this.btnLogDir.setEnabled(dataState != DataState.BROWSE);
		this.btnRecvDir.setEnabled(dataState != DataState.BROWSE);
		this.btnSendDir.setEnabled(dataState != DataState.BROWSE);
		this.cbRemoteSignData.setEnabled(dataState != DataState.BROWSE);
		this.tfRemoteHostAddress.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostPort.setEditable(dataState != DataState.BROWSE);
		this.rbNormal.setEnabled(this.cbRemoteSignData.isSelected()
				&& dataState != DataState.BROWSE);
		this.rbEnhance.setEnabled(this.cbRemoteSignData.isSelected()
				&& dataState != DataState.BROWSE);
		this.cbPort9097Open.setEnabled(this.rbEnhance.isSelected()
				&& dataState != DataState.BROWSE);

		this.cbbProjectType.setEnabled(dataState != DataState.BROWSE);
		this.btnReadCard.setEnabled(dataState == DataState.BROWSE);
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
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("转厂管理参数设置");
			jLabel18
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			pn1
					.setBorder(javax.swing.BorderFactory
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
					FmOwpParameterSet.this.doDefaultCloseAction();
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
			pn4.add(getBtnEdit(), null);
			pn4.add(getBtnSave(), null);
			pn4.add(getBtnClose(), null);
		}
		return pn4;
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
			lb511.setBounds(new Rectangle(109, 458, 61, 24));
			lb511.setText("\u9879\u76ee\u7c7b\u578b");
			lb511.setForeground(Color.blue);
			lb7 = new JLabel();
			lb7.setBounds(new Rectangle(76, 7, 397, 20));
			lb7
					.setText("\u6ce8\u610f:\u6240\u6709\u8def\u5f84\u5747\u6307\u5728\u670d\u52a1\u5668\u7aef\u7684\u8def\u5f84,\u8bf7\u5148\u5728\u670d\u52a1\u5668\u7aef\u8fdb\u884c\u8bbe\u7f6e.");
			lb7.setForeground(Color.RED);
			pn41 = new JPanel();
			pn41.setLayout(null);
			pn41.setBounds(new Rectangle(24, 15, 751, 503));
			pn41.add(getPn2(), null);
			pn41.add(getPn31(), null);
			pn41.add(lb7, null);
			pn41.add(getCbbProjectType(), null);
			pn41.add(lb511, null);
			pn41.add(getPn5(), null);
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
			pn2.setBounds(new Rectangle(52, 33, 596, 141));
			pn2.setBorder(BorderFactory.createTitledBorder(null,
					"\u8def\u5f84\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
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
			pn31 = new JPanel();
			pn31.setLayout(null);
			pn31.setBounds(new Rectangle(52, 234, 596, 208));
			pn31
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u751f\u6210\u62a5\u6587\u7b7e\u540d\u4fe1\u606f\u7684\u57fa\u672c\u914d\u7f6e",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									Color.blue));
			pn31.add(getRbFromCard(), null);
			pn31.add(getRbFromManual(), null);
			pn31.add(getTabPn(), null);
		}
		return pn31;
	}

	/**
	 * This method initializes rbFromCard
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbFromCard() {
		if (rbFromCard == null) {
			rbFromCard = new JRadioButton();
			rbFromCard.setBounds(new Rectangle(55, 23, 207, 26));
			rbFromCard
					.setText("\u4eceIC\u5361\u91cc\u8bfb\u53d6\u7b7e\u540d\u7528\u57fa\u672c\u4fe1\u606f");
			rbFromCard.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						tabPn
								.setSelectedIndex(rbFromCard.isSelected() ? 0
										: 1);
					}
				}
			});

		}
		return rbFromCard;
	}

	/**
	 * This method initializes rbFromManual
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbFromManual() {
		if (rbFromManual == null) {
			rbFromManual = new JRadioButton();
			rbFromManual.setBounds(new Rectangle(268, 22, 173, 26));
			rbFromManual
					.setText("\u624b\u5de5\u914d\u7f6e\u7b7e\u540d\u7528\u57fa\u672c\u4fe1\u606f");
			rbFromManual.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						tabPn
								.setSelectedIndex(rbFromCard.isSelected() ? 0
										: 1);
					}
				}
			});
		}
		return rbFromManual;
	}

	/**
	 * This method initializes tabPn
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabPn() {
		if (tabPn == null) {
			tabPn = new JTabbedPane();
			tabPn.setBounds(new Rectangle(55, 47, 414, 153));
			tabPn.setEnabled(false);
			tabPn
					.addTab(
							"\u4eceIC\u5361\u91cc\u8bfb\u53d6\u7b7e\u540d\u7528\u57fa\u672c\u4fe1\u606f\u8bbe\u7f6e",
							null, getPn51(), null);
			tabPn
					.addTab(
							"\u624b\u5de5\u914d\u7f6e\u7b7e\u540d\u7528\u57fa\u672c\u4fe1\u606f\u8bbe\u7f6e",
							null, getPn61(), null);
		}
		return tabPn;
	}

	/**
	 * This method initializes pn51
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn51() {
		if (pn51 == null) {
			lb6 = new JLabel();
			lb6.setBounds(new Rectangle(13, 33, 66, 22));
			lb6.setText("\u8bfb\u5361\u5bc6\u7801");
			lb51 = new JLabel();
			lb51.setBounds(new Rectangle(13, 7, 66, 22));
			lb51.setText("\u8bfb\u5361\u552f\u4e00\u53f7");
			pn51 = new JPanel();
			pn51.setLayout(null);
			pn51.add(lb51, null);
			pn51.add(lb6, null);
			pn51.add(getTfSeqNo(), null);
			pn51.add(getTfPwd(), null);
			pn51.add(getCbRemoteSignData(), null);
			pn51.add(getBtnReadCard(), null);
			pn51.add(getCbPort9097Open(), null);
			pn51.add(getPn3(), null);
		}
		return pn51;
	}

	/**
	 * This method initializes tfSeqNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNo() {
		if (tfSeqNo == null) {
			tfSeqNo = new JTextField();
			tfSeqNo.setBounds(new Rectangle(79, 6, 312, 24));
		}
		return tfSeqNo;
	}

	/**
	 * This method initializes tfPwd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPwd() {
		if (tfPwd == null) {
			tfPwd = new JTextField();
			tfPwd.setBounds(new Rectangle(79, 33, 312, 24));
		}
		return tfPwd;
	}

	/**
	 * This method initializes cbRemoteSignData
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbRemoteSignData() {
		if (cbRemoteSignData == null) {
			cbRemoteSignData = new JCheckBox();
			cbRemoteSignData.setBounds(new Rectangle(72, 61, 79, 22));
			cbRemoteSignData.setText("\u8fdc\u7a0b\u52a0\u7b7e");
			cbRemoteSignData.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setState();
					// System.out.println("itemStateChanged()");
				}
			});
		}
		return cbRemoteSignData;
	}

	/**
	 * This method initializes tfRemoteHostAddress
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostAddress() {
		if (tfRemoteHostAddress == null) {
			tfRemoteHostAddress = new JTextField();
			tfRemoteHostAddress.setBounds(new Rectangle(98, 17, 197, 25));
		}
		return tfRemoteHostAddress;
	}

	/**
	 * This method initializes btnReadCard
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReadCard() {
		if (btnReadCard == null) {
			btnReadCard = new JButton();
			btnReadCard.setBounds(new Rectangle(303, 64, 87, 22));
			btnReadCard.setText("\u8bfb\u5361\u6d4b\u8bd5");
			btnReadCard.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ICCardInfo cardInfo = owpMessageAction.getICCardInfo(
							new Request(CommonVars.getCurrUser()), tfSeqNo
									.getText().trim(), tfPwd.getText().trim());
					if (cardInfo != null) {
						StringBuffer sb = new StringBuffer();
						// sb.append("------------------"+i);
						sb.append("卡号-------------" + cardInfo.getIcCode()
								+ "\n");
						sb.append("证书-------------" + cardInfo.getCertSeqNo()
								+ "\n");
						sb.append("申请者名秿-------" + cardInfo.getApplier()
								+ "\n");
						sb.append("单位名称----------" + cardInfo.getTradeName()
								+ "\n");
						sb.append("单位类型代码-------" + cardInfo.getTradeType()
								+ "\n");
						sb.append("单位代码-----------" + cardInfo.getTradeCode()
								+ "\n");
						sb.append("数字签名-----------"
								+ cardInfo.getSignData().substring(0, 10)
								+ "\n");
						// System.out.println(sb.toString());
						JOptionPane.showMessageDialog(FmOwpParameterSet.this,
								sb.toString(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(FmOwpParameterSet.this,
								"没有读到卡信息","提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnReadCard;
	}

	/**
	 * This method initializes pn61
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn61() {
		if (pn61 == null) {
			lb411 = new JLabel();
			lb411.setBounds(new Rectangle(13, 19, 66, 22));
			lb411.setText("\u64cd\u4f5c\u5458\u5361\u53f7");
			pn61 = new JPanel();
			pn61.setLayout(null);
			pn61.add(getTfIdCard(), null);
			pn61.add(lb411, null);
		}
		return pn61;
	}

	/**
	 * This method initializes tfIdCard
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfIdCard() {
		if (tfIdCard == null) {
			tfIdCard = new JTextField();
			tfIdCard.setBounds(new Rectangle(79, 19, 313, 23));
		}
		return tfIdCard;
	}
	/**
	 * 设置路径
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
		int state = fileChooser.showDialog(FmOwpParameterSet.this, "选择路径");
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
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbFromCard());
			buttonGroup.add(this.getRbFromManual());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes buttonGroup1
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(this.getRbNormal());
			buttonGroup1.add(this.getRbEnhance());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes cbbProjectType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbProjectType() {
		if (cbbProjectType == null) {
			cbbProjectType = new JComboBox();
			cbbProjectType.setBounds(new Rectangle(174, 458, 304, 24));
		}
		return cbbProjectType;
	}

	/**
	 * This method initializes cbRemoteSignData1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPort9097Open() {
		if (cbPort9097Open == null) {
			cbPort9097Open = new JCheckBox();
			cbPort9097Open.setBounds(new Rectangle(154, 94, 124, 21));
			cbPort9097Open.setText("9097端口已开通");
		}
		return cbPort9097Open;
	}

	/**
	 * This method initializes pn3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn3() {
		if (pn3 == null) {
			pn3 = new JPanel();
			pn3.setLayout(null);
			pn3.setBounds(new Rectangle(153, 59, 146, 29));
			pn3.setBorder(BorderFactory.createTitledBorder(null, "",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			pn3.add(getRbNormal(), null);
			pn3.add(getRbEnhance(), null);
		}
		return pn3;
	}

	/**
	 * This method initializes rbNormal
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNormal() {
		if (rbNormal == null) {
			rbNormal = new JRadioButton();
			rbNormal.setBounds(new Rectangle(3, 5, 70, 21));
			rbNormal.setText("普通");
			rbNormal.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					cbPort9097Open.setSelected(false);
					setState();
					// System.out.println("itemStateChanged()");
				}
			});

		}
		return rbNormal;
	}

	/**
	 * This method initializes rbEnhance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEnhance() {
		if (rbEnhance == null) {
			rbEnhance = new JRadioButton();
			rbEnhance.setBounds(new Rectangle(73, 4, 70, 22));
			rbEnhance.setText("加强版");
			rbEnhance.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setState();
					// System.out.println("itemStateChanged()");
				}
			});
		}
		return rbEnhance;
	}

	/**
	 * This method initializes pn5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPn5() {
		if (pn5 == null) {
			lb4 = new JLabel();
			lb4.setBounds(new Rectangle(314, 18, 29, 20));
			lb4.setText("端口");
			lb3 = new JLabel();
			lb3.setBounds(new Rectangle(62, 18, 34, 22));
			lb3.setText("地址");
			pn5 = new JPanel();
			pn5.setLayout(null);
			pn5.setBounds(new Rectangle(52, 181, 596, 46));
			pn5.setBorder(BorderFactory.createTitledBorder(null, "\u8fdc\u7a0b\u670d\u52a1\u5730\u5740", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), Color.blue));
			pn5.add(getTfRemoteHostAddress(), null);
			pn5.add(lb3, null);
			pn5.add(lb4, null);
			pn5.add(getTfRemoteHostPort(), null);
		}
		return pn5;
	}

	/**
	 * This method initializes tfRemoteHostPort	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRemoteHostPort() {
		if (tfRemoteHostPort == null) {
			tfRemoteHostPort = new JTextField();
			tfRemoteHostPort.setBounds(new Rectangle(349, 16, 113, 24));
			tfRemoteHostPort.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					for (int i = 0; i < str.length(); i++) {
						if (str.charAt(i) < '0' || str.charAt(i) > '9') {
							return;
						}
					}
					super.insertString(offs, str, a);
				}
			});
		}
		return tfRemoteHostPort;
	}

} // @jve:decl-index=0:visual-constraint="10,16"
