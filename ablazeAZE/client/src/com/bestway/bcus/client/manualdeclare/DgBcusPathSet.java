package com.bestway.bcus.client.manualdeclare;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.BcusParameterSet;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.Request;
import com.bestway.common.constant.ParameterType;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Label;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class DgBcusPathSet extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	public JLabel jLabel5 = null;

	public JLabel jLabel6 = null;

	public JLabel jLabel7 = null;

	public JLabel jLabel8 = null;

	public JLabel jLabel9 = null;

	public JLabel jLabel10 = null;

	public JLabel jLabel11 = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JTextField jTextField5 = null;

	private JTextField jTextField6 = null;

	private JTextField jTextField7 = null;

	private JLabel jLabel12 = null;

	public JLabel jLabel13 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JButton jButton2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JCheckBox jCheckBox = null;

	private ButtonGroup group = new ButtonGroup();

	private int dataState = DataState.BROWSE;

	private SystemAction systemAction = null;

	private EncAction encAction = null;

	private String emsFrom = "1"; // @jve:decl-index=0:

	public JLabel jLabel14 = null;

	private JTextField tfRecvBillDir = null;

	private JButton jButton32 = null;

	private JButton jButton321 = null;

	private JButton jButton322 = null;

	private JButton jButton3221 = null;

	private JButton jButton33 = null;

	private JButton jButton34 = null;

	private JButton jButton35 = null;

	private JButton jButton36 = null;

	private JButton jButton37 = null;

	private String tempDir = "./"; // @jve:decl-index=0:

	private JCheckBox cbbH2kMergerModifyNo = null;

	private JLabel jLabel15 = null;

	private JTextField tfRemoteHostAddress = null;

	private JTextField tfRemoteHostPort = null;

	private JLabel jLabel16 = null;

	private JTextField tfRemoteHostICPwd = null;

	private JLabel jLabel19 = null;

	private JCheckBox cbbH2kTrInput = null;

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * This is the default constructor
	 */
	public DgBcusPathSet() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		initialize();
		if (CommonVars.getIsBigToGBK() == 1) {
			this.jCheckBox.setSelected(true);
		} else {
			this.jCheckBox.setSelected(false);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(720, 488);
		this.setContentPane(getJContentPane());
		this.setTitle("其他设置");
		fillWindow();
		dataState = DataState.BROWSE;
		setState();
		group.add(jRadioButton);
		group.add(jRadioButton1);
		group.add(jRadioButton2);

	}

	private void fillWindow() {
		List list = null;
		ParameterSet para = null;

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.sendDir);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}
		jTextField.setText(para.getNameValues());

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.recvDir);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}
		jTextField1.setText(para.getNameValues());

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.recvBillDir);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}
		tfRecvBillDir.setText(para.getNameValues());

		list = systemAction
				.findnameValues(new Request(CommonVars.getCurrUser()),
						ParameterType.finallyDir);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}
		jTextField2.setText(para.getNameValues());

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.logDir);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}
		jTextField3.setText(para.getNameValues());

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.emsFrom);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
		}
		if (para.getNameValues().equals("1")) {
			this.jRadioButton.setSelected(true);
		} else if (para.getNameValues().equals("2")) {
			this.jRadioButton1.setSelected(true);
		} else if (para.getNameValues().equals("3")) {
			this.jRadioButton2.setSelected(true);
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.h2kMergerModifyNo);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
			if (para.getNameValues().equals("0")) {
				this.cbbH2kMergerModifyNo.setSelected(false);
				CommonVars.setH2kMergerModifyNo(false);
			} else if (para.getNameValues().equals("1")) {
				this.cbbH2kMergerModifyNo.setSelected(true);
				CommonVars.setH2kMergerModifyNo(true);
			}
		} else {
			CommonVars.setH2kMergerModifyNo(false);
			this.cbbH2kMergerModifyNo.setSelected(false);
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.H2K_TR_INPUT);
		if (list != null && list.size() > 0) {
			para = (ParameterSet) list.get(0);
			this.cbbH2kTrInput
					.setSelected("1".equals(para.getNameValues()) ? true
							: false);
		} else {
			this.cbbH2kTrInput.setSelected(false);
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.txtInput);
		if (list != null && !list.isEmpty()) {
			para = (ParameterSet) list.get(0);
			jTextField4.setText(para.getNameValues());
		}
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.txtInputFinally);
		if (list != null && !list.isEmpty()) {
			para = (ParameterSet) list.get(0);
			jTextField5.setText(para.getNameValues());
		}
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.txtInputFail);
		if (list != null && !list.isEmpty()) {
			para = (ParameterSet) list.get(0);
			jTextField6.setText(para.getNameValues());
		}
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.txtlog);
		if (list != null && !list.isEmpty()) {
			para = (ParameterSet) list.get(0);
			jTextField7.setText(para.getNameValues());
		}
		BcusParameterSet parameterSet = encAction
				.findBcusParameterSet(new Request(CommonVars.getCurrUser(),
						true));
		this.tfRemoteHostAddress.setText(parameterSet.getRemoteHostAddress());
		this.tfRemoteHostPort.setText(parameterSet.getRemoteHostPort());
		this.tfRemoteHostICPwd.setText(parameterSet.getRemoteHostICPwd());
	}

	private void setState() {
		this.jTextField.setEditable(!(dataState == DataState.BROWSE));
		this.jTextField1.setEditable(!(dataState == DataState.BROWSE));
		this.jTextField2.setEditable(!(dataState == DataState.BROWSE));
		this.jTextField3.setEditable(!(dataState == DataState.BROWSE));
		this.jTextField4.setEditable(!(dataState == DataState.BROWSE));
		this.jTextField5.setEditable(!(dataState == DataState.BROWSE));
		this.jTextField6.setEditable(!(dataState == DataState.BROWSE));
		this.jTextField7.setEditable(!(dataState == DataState.BROWSE));
		this.tfRecvBillDir.setEditable(!(dataState == DataState.BROWSE));

		this.jRadioButton.setEnabled(!(dataState == DataState.BROWSE));
		this.jRadioButton1.setEnabled(!(dataState == DataState.BROWSE));
		this.jRadioButton2.setEnabled(!(dataState == DataState.BROWSE));

		this.jButton.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton2.setEnabled(dataState == DataState.BROWSE);
		jCheckBox.setEnabled(!(dataState == DataState.BROWSE));
		cbbH2kMergerModifyNo.setEnabled(!(dataState == DataState.BROWSE));
		cbbH2kTrInput.setEnabled(!(dataState == DataState.BROWSE));
		// 选择路径按钮
		this.jButton32.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton321.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton322.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton3221.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton33.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton34.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton35.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton36.setEnabled(!(dataState == DataState.BROWSE));
		this.jButton37.setEnabled(!(dataState == DataState.BROWSE));

		this.tfRemoteHostAddress.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostPort.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostICPwd.setEditable(dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(359, 232, 84, 18));
			jLabel19.setText("\u5361\u5bc6\u7801");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(590, 205, 29, 23));
			jLabel16.setText("端口");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(358, 206, 94, 23));
			jLabel15.setText("远程服务地址");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(7, 202, 96, 25));
			jLabel14.setText("清单回执存放路径");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(11, 253, 684, 26));
			jLabel13.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/steup3.jpg")));
			jLabel13.setText("");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(12, 18, 686, 26));
			jLabel12.setText("");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(358, 180, 59, 18));
			jLabel11.setText("存放路径");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(358, 163, 93, 18));
			jLabel10.setText("导入文本日志");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(358, 138, 69, 18));
			jLabel9.setText("存放路径");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(358, 121, 93, 18));
			jLabel8.setText("导入失败的文本");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(358, 97, 60, 18));
			jLabel7.setText("存放路径");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(358, 79, 93, 18));
			jLabel6.setText("导入成功的文本");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(358, 50, 93, 22));
			jLabel5.setText("文本导入路径");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(13, 169, 88, 18));
			jLabel4.setText("日志所在路径");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(13, 141, 59, 18));
			jLabel3.setText("存放路径");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(13, 123, 89, 18));
			jLabel2.setText("处理完的路径");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(13, 84, 88, 18));
			jLabel12.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/steup1.jpg")));
			jLabel1.setText("回执存放路径");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(13, 50, 89, 22));
			jLabel.setText("报文发送路径");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(getJTextField7(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(getJRadioButton1(), null);
			jContentPane.add(getJRadioButton2(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getTfRecvBillDir(), null);
			jContentPane.add(getJButton32(), null);
			jContentPane.add(getJButton321(), null);
			jContentPane.add(getJButton322(), null);
			jContentPane.add(getJButton3221(), null);
			jContentPane.add(getJButton33(), null);
			jContentPane.add(getJButton34(), null);
			jContentPane.add(getJButton35(), null);
			jContentPane.add(getJButton36(), null);
			jContentPane.add(getJButton37(), null);
			jContentPane.add(getCbbH2kMergerModifyNo(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getTfRemoteHostAddress(), null);
			jContentPane.add(getTfRemoteHostPort(), null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(getTfRemoteHostICPwd(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(getCbbH2kTrInput(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(101, 51, 212, 22));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(101, 84, 212, 22));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(101, 124, 212, 22));
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(new Rectangle(101, 168, 212, 22));
		}
		return jTextField3;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(new Rectangle(451, 51, 212, 22));
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(new java.awt.Rectangle(451, 84, 212, 22));
		}
		return jTextField5;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(new java.awt.Rectangle(451, 124, 212, 22));
		}
		return jTextField6;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(new Rectangle(451, 168, 212, 22));
		}
		return jTextField7;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(38, 286, 177, 21));
			jRadioButton.setText("归并关系备案(正在执行)");
			jRadioButton.addActionListener(new RadioActionListner());
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(257, 285, 163, 21));
			jRadioButton1.setText("归并关系备案(申请备案)");
			jRadioButton1.addActionListener(new RadioActionListner());
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new Rectangle(468, 287, 181, 21));
			jRadioButton2.setText("企业物料归并关系");
			jRadioButton2.addActionListener(new RadioActionListner());
		}
		return jRadioButton2;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (jRadioButton.isSelected()) {
				DgBcusPathSet.this.setEmsFrom("1");
			} else if (jRadioButton1.isSelected()) {
				DgBcusPathSet.this.setEmsFrom("2");
			} else if (jRadioButton2.isSelected()) {
				DgBcusPathSet.this.setEmsFrom("3");
			}
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(192, 411, 85, 26));
			jButton2.setText("修改");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(294, 411, 85, 26));
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkRepeat()) {
						return;
					}

					systemAction.createDir(
							new Request(CommonVars.getCurrUser()), false,
							(jTextField.getText().trim()), jTextField1
									.getText().trim(), jTextField2.getText()
									.trim(), jTextField3.getText().trim(),
							tfRecvBillDir.getText().trim());
					systemAction.saveDir(new Request(CommonVars.getCurrUser()),
							jTextField.getText().trim(), jTextField1.getText()
									.trim(), jTextField2.getText().trim(),
							jTextField3.getText().trim(), tfRecvBillDir
									.getText().trim());

					saveValues(ParameterType.emsFrom, DgBcusPathSet.this
							.getEmsFrom(), "1");// 保存电子帐册选项

					List list = systemAction.findnameValues(new Request(
							CommonVars.getCurrUser()),
							ParameterType.h2kMergerModifyNo);
					if (list == null || list.size() == 0) {
						ParameterSet pa = new ParameterSet();
						pa.setType(ParameterType.h2kMergerModifyNo);
						pa.setNameValues("0");
						systemAction.saveValues1(new Request(CommonVars
								.getCurrUser()), pa);
					}
					list = systemAction.findnameValues(new Request(CommonVars
							.getCurrUser()), ParameterType.h2kMergerModifyNo);
					ParameterSet para = (ParameterSet) list.get(0);
					if (cbbH2kMergerModifyNo.isSelected()) {
						para.setNameValues("1");
						CommonVars.setH2kMergerModifyNo(true);
					} else {
						para.setNameValues("0");
						CommonVars.setH2kMergerModifyNo(false);
					}
					systemAction.saveValues1(new Request(CommonVars
							.getCurrUser()), para);
					list = systemAction.findnameValues(new Request(CommonVars
							.getCurrUser()), ParameterType.H2K_TR_INPUT);
					para = (ParameterSet) list.get(0);
					para.setNameValues(cbbH2kTrInput.isSelected() ? "1" : "0");
					systemAction.saveValues1(new Request(CommonVars
							.getCurrUser()), para);

					saveTxtDir(); // 保存文本
					int isbigtogbk = 0; // 输出报文是否繁转简
					if (jCheckBox.isSelected()) {
						isbigtogbk = 1;
					}
					saveIsBigToGBK(ParameterType.isBigToGBK, String
							.valueOf(isbigtogbk));

					BcusParameterSet parameterSet = encAction
							.findBcusParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					parameterSet.setRemoteHostAddress(tfRemoteHostAddress
							.getText().trim());
					parameterSet.setRemoteHostPort(tfRemoteHostPort.getText()
							.trim());
					parameterSet.setRemoteHostICPwd(tfRemoteHostICPwd.getText()
							.trim());
					encAction.saveBcusParameterSet(new Request(CommonVars
							.getCurrUser(), true), parameterSet);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return jButton;
	}

	private void saveTxtDir() {
		systemAction.saveTxtDir(new Request(CommonVars.getCurrUser()),
				this.jTextField4.getText(), this.jTextField5.getText(),
				this.jTextField6.getText(), this.jTextField7.getText());
	}

	private void saveValues(int parameterType, String values, String flat) {
		List list = systemAction.findnameValues(new Request(CommonVars
				.getCurrUser()), parameterType);
		ParameterSet para = (ParameterSet) list.get(0);
		para.setNameValues(values);
		if (flat.equals("0")) {

		} else if (flat.equals("1")) {
			CommonVars.setEmsFrom(values);
		} else if (flat.equals("2")) {
			CommonVars.setContractFrom(values);
		}
		systemAction.saveValues1(new Request(CommonVars.getCurrUser()), para);

	}

	private void saveIsBigToGBK(int parameterType, String values) {
		List list = systemAction.findnameValues(new Request(CommonVars
				.getCurrUser()), parameterType);
		ParameterSet para = (ParameterSet) list.get(0);
		para.setNameValues(values);
		CommonVars.setIsBigToGBK(Integer.parseInt(values));
		systemAction.saveValues1(new Request(CommonVars.getCurrUser()), para);
		systemAction.saveisBigToGbk(new Request(CommonVars.getCurrUser()),
				values);
		System.out.println("保存繁转简值：" + values);
	}

	private boolean checkNull() {
		if (jTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "报文发送路径不能为空！", "提示！", 2);
			return true;
		}
		if (jTextField1.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "回执存放路径不能为空！", "提示！", 2);
			return true;
		}
		if (this.tfRecvBillDir.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "清单回执存放路径不能为空！", "提示！", 2);
			return true;
		}
		if (jTextField2.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "处理完的回执存放路径不能为空！", "提示！", 2);
			return true;
		}
		if (jTextField3.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "日志路径不能为空！", "提示！", 2);
			return true;
		}

		return false;

	}

	private boolean checkRepeat() {
		if (jTextField.getText().equals(jTextField1.getText())) {
			JOptionPane.showMessageDialog(this, "注意：报文发送路径不能与回执存放路径相同！", "提示！",
					2);
			return true;
		}
		if (jTextField.getText().equals(jTextField2.getText())) {
			JOptionPane.showMessageDialog(this, "注意：报文发送路径不能与处理完的回执存放路径相同！",
					"提示！", 2);
			return true;
		}
		if (jTextField1.getText().equals(jTextField2.getText())) {
			JOptionPane.showMessageDialog(this, "注意：回执存放路径不能与处理完的回执存放路径相同！",
					"提示！", 2);
			return true;
		}
		return false;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(396, 411, 85, 26));
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(38, 321, 228, 21));
			jCheckBox.setText("帐册及报关单报文输出是否作繁转简");
		}
		return jCheckBox;
	}

	public String getEmsFrom() {
		return emsFrom;
	}

	public void setEmsFrom(String emsFrom) {
		this.emsFrom = emsFrom;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getTfRecvBillDir() {
		if (tfRecvBillDir == null) {
			tfRecvBillDir = new JTextField();
			tfRecvBillDir.setBounds(new Rectangle(101, 205, 212, 22));
		}
		return tfRecvBillDir;
	}

	/**
	 * This method initializes jButton32
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton32() {
		if (jButton32 == null) {
			jButton32 = new JButton();
			jButton32.setBounds(new Rectangle(312, 51, 22, 22));
			jButton32.setText("...");
			jButton32.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField);
				}
			});
		}
		return jButton32;
	}

	/**
	 * This method initializes jButton321
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton321() {
		if (jButton321 == null) {
			jButton321 = new JButton();
			jButton321.setBounds(new Rectangle(312, 84, 22, 22));
			jButton321.setText("...");
			jButton321.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField1);
				}
			});
		}
		return jButton321;
	}

	/**
	 * This method initializes jButton322
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton322() {
		if (jButton322 == null) {
			jButton322 = new JButton();
			jButton322.setBounds(new Rectangle(312, 124, 22, 22));
			jButton322.setText("...");
			jButton322.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField2);
				}
			});
		}
		return jButton322;
	}

	/**
	 * This method initializes jButton3221
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3221() {
		if (jButton3221 == null) {
			jButton3221 = new JButton();
			jButton3221.setBounds(new Rectangle(312, 168, 22, 22));
			jButton3221.setText("...");
			jButton3221.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField3);
				}
			});
		}
		return jButton3221;
	}

	/**
	 * This method initializes jButton33
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton33() {
		if (jButton33 == null) {
			jButton33 = new JButton();
			jButton33.setBounds(new Rectangle(312, 205, 22, 22));
			jButton33.setText("...");
			jButton33.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfRecvBillDir);
				}
			});
		}
		return jButton33;
	}

	/**
	 * This method initializes jButton34
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton34() {
		if (jButton34 == null) {
			jButton34 = new JButton();
			jButton34.setBounds(new Rectangle(662, 51, 22, 22));
			jButton34.setText("...");
			jButton34.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField4);
				}
			});
		}
		return jButton34;
	}

	/**
	 * This method initializes jButton35
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton35() {
		if (jButton35 == null) {
			jButton35 = new JButton();
			jButton35.setBounds(new Rectangle(662, 84, 22, 22));
			jButton35.setText("...");
			jButton35.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField5);
				}
			});
		}
		return jButton35;
	}

	/**
	 * This method initializes jButton36
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton36() {
		if (jButton36 == null) {
			jButton36 = new JButton();
			jButton36.setBounds(new Rectangle(662, 124, 22, 22));
			jButton36.setText("...");
			jButton36.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField6);
				}
			});
		}
		return jButton36;
	}

	/**
	 * This method initializes jButton37
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getJButton37() {
		if (jButton37 == null) {
			jButton37 = new JButton();
			jButton37.setBounds(new Rectangle(662, 168, 22, 22));
			jButton37.setText("...");
			jButton37.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(jTextField7);
				}
			});
		}
		return jButton37;
	}

	private void setDir(JTextField jtf) {
		if (jtf.getText() != null && !jtf.getText().equals("")) {
			File file = new File(jtf.getText());
			if (file.exists())
				setTempDir(jtf.getText());
		}
		JFileChooser fileChooser = new JFileChooser(getTempDir());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int state = fileChooser.showDialog(DgBcusPathSet.this, "选择路径");
		if (state == JFileChooser.APPROVE_OPTION) {
			fileChooser.getSelectedFile();
			File f = fileChooser.getSelectedFile();
			jtf.setText(f.getPath());
			setTempDir(jtf.getText());
		}
	}

	/**
	 * This method initializes cbbH2kMergerModifyNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbH2kMergerModifyNo() {
		if (cbbH2kMergerModifyNo == null) {
			cbbH2kMergerModifyNo = new JCheckBox();
			cbbH2kMergerModifyNo.setBounds(new Rectangle(348, 321, 225, 21));
			cbbH2kMergerModifyNo.setText("修改经营范围与归并关系的归并序号");
		}
		return cbbH2kMergerModifyNo;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostAddress() {
		if (tfRemoteHostAddress == null) {
			tfRemoteHostAddress = new JTextField();
			tfRemoteHostAddress.setBounds(new Rectangle(451, 206, 132, 22));
		}
		return tfRemoteHostAddress;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostPort() {
		if (tfRemoteHostPort == null) {
			tfRemoteHostPort = new JTextField();
			tfRemoteHostPort.setBounds(new Rectangle(619, 204, 66, 24));
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

	/**
	 * This method initializes tfRemoteHostICPwd
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostICPwd() {
		if (tfRemoteHostICPwd == null) {
			tfRemoteHostICPwd = new JTextField();
			tfRemoteHostICPwd.setBounds(new Rectangle(451, 230, 132, 24));
		}
		return tfRemoteHostICPwd;
	}

	/**
	 * This method initializes cbbH2kTrInput
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbH2kTrInput() {
		if (cbbH2kTrInput == null) {
			cbbH2kTrInput = new JCheckBox();
			cbbH2kTrInput.setText("经营范围手工模式(当打√后，增加经营范围需手工输入)");
			cbbH2kTrInput.setBounds(new Rectangle(38, 365, 392, 21));
		}
		return cbbH2kTrInput;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
