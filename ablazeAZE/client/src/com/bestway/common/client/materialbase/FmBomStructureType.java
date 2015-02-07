package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.Request;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;

/**
 * 企业BOM管理方式的设置
 * 
 * @author Administrator 
 * 修改:陈井彬 Date:2008.9.6
 */
public class FmBomStructureType extends JInternalFrameBase {

	private JPanel jContentPane = null;

	/**
	 * 选项:以BOM成品版本号来区分不同版本
	 */
	private JRadioButton rbtnBomVersion = null;

	/**
	 * 选项:无成品版本号,以成品开始日期失效期来区分不同的BOM版本
	 */
	private JRadioButton rbnDate = null;

	/**
	 * 选项:无成品版本号,BOM中存在最新的半成品和料件的耗用情况
	 */
	private JRadioButton rbtnBom = null;

	/**
	 * 保存按钮
	 */
	private JButton btnSave = null;

	/**
	 * 取消按钮
	 */
	private JButton btnUndo = null;

	/**
	 * 修改按钮
	 */
	private JButton btnEdit = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JPanel jPanel1 = null;

	private JSeparator jSeparator = null;

	/**
	 * 系统参数业务逻辑操作接口,远程逻辑方法调用
	 */
	private SystemAction systemAction = null;

	/**
	 * 料件参数业务逻辑操作接口,远程逻辑方法调用
	 */
	private MaterialManageAction materialManageAction = null;

	/**
	 * 界面状态设置
	 */
	private int dataState = DataState.BROWSE;

	/**
	 * 按钮组合
	 */
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="731,102"

	private JLabel jLabel3 = null;

	public FmBomStructureType() {
		super();
		initialize();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");//调用服务器远程服务
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		materialManageAction.checkBomStructureTypeAuthority(new Request(CommonVars
				.getCurrUser()));
	}

	/**
	 * 界面初始化 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(705, 388));
		this.setTitle("企业BOM管理方式设定");
		this.setHelpId("baseCode");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
		//内部窗体监听方法
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					@Override
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						setState();
						showData();
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
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (rbtnBomVersion == null) {
			rbtnBomVersion = new JRadioButton();
			rbtnBomVersion.setText("以BOM成品版本号来区分不同的版本");
			rbtnBomVersion.setSelected(false);
			rbtnBomVersion.setBounds(new Rectangle(156, 50, 445, 26));
		}
		return rbtnBomVersion;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (rbnDate == null) {
			rbnDate = new JRadioButton();
			rbnDate.setText("无成品版本号，以成品开始日期失效日期来区分不同的BOM版本");
			rbnDate.setBounds(new java.awt.Rectangle(156, 96, 416, 26));
		}
		return rbnDate;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (rbtnBom == null) {
			rbtnBom = new JRadioButton();
			rbtnBom.setText("无成品版本号，BOM中存在最新的半成品和料件的耗用情况");
			rbtnBom.setSelected(false);
			rbtnBom.setBounds(new java.awt.Rectangle(156, 144, 380, 26));
		}
		return rbtnBom;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setBounds(new java.awt.Rectangle(469, 270, 60, 28));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbtnBom.isSelected()) {
						if (!materialManageAction
								.checkEnterpriseBomVersionCount(new Request(
										CommonVars.getCurrUser(), true))) {
							JOptionPane.showMessageDialog(
									FmBomStructureType.this,
									"现在工厂原始BOM中有多个版本，所以你不能选择没有版本号这种方式！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					ParameterSet para = null;
					List list = systemAction.findnameValues(new Request(
							CommonVars.getCurrUser()),
							ParameterType.bomStructureType);
					if (list.isEmpty()) {
						para = new ParameterSet();
						para.setType(ParameterType.bomStructureType);
					} else {
						para = (ParameterSet) list.get(0);
					}
					String value = para.getNameValues();
					String oldTypeSpec = "";
					if ("0".equals(value)) {
						oldTypeSpec = "以BOM成品版本号来区分不同的版本";
					} else if ("1".equals(value)) {
						oldTypeSpec = "无成品版本号，以成品开始日期失效日期来区分不同的BOM版本";
					} else if ("2".equals(value)) {
						oldTypeSpec = "无成品版本号，BOM中存在最新的半成品和料件的耗用情况";
					}
					String newTypeSpec = "";
					if (rbtnBomVersion.isSelected()) {
						newTypeSpec = "以BOM成品版本号来区分不同的版本";
						value = "0";
					} else if (rbnDate.isSelected()) {
						newTypeSpec = "无成品版本号，以成品开始日期失效日期来区分不同的BOM版本";
						value = "1";
					} else if (rbtnBom.isSelected()) {
						newTypeSpec = "无成品版本号，BOM中存在最新的半成品和料件的耗用情况";
						value = "2";
					} else {
						JOptionPane.showMessageDialog(FmBomStructureType.this,
								"请选择BOM管理方式！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (para.getNameValues() != null
							&& !value.equals(para.getNameValues())) {
						if (JOptionPane.showConfirmDialog(
								FmBomStructureType.this, "系统原来的选项是：\n"
										+ oldTypeSpec + "\n系统新的选项是：\n"
										+ newTypeSpec + "\n确定要做此改变吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					}
					para.setNameValues(value);
					CommonVars.setBomStructureType(Integer.valueOf(value));
					materialManageAction.saveValues(new Request(CommonVars
							.getCurrUser()), para);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.setBounds(new java.awt.Rectangle(555, 270, 60, 28));
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setBounds(new java.awt.Rectangle(383, 270, 60, 28));
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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel1 = new JLabel();
			jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel1.setText("企业BOM管理方式设定");
			jLabel1.setForeground(new Color(255, 153, 0));
			jLabel = new JLabel();
			jLabel
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel.setText("");
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBackground(Color.white);
			jPanel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel.add(jLabel, java.awt.BorderLayout.WEST);
			jPanel.add(jLabel1, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel2, java.awt.BorderLayout.EAST);
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(178, 168, 369, 21));
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setText("企业BOM不以版本号和日期来区分版本，永远只存在一个版本");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJRadioButton(), null);
			jPanel1.add(getJRadioButton1(), null);
			jPanel1.add(getJRadioButton2(), null);
			jPanel1.add(getBtnEdit(), null);
			jPanel1.add(getBtnSave(), null);
			jPanel1.add(getBtnUndo(), null);
			jPanel1.add(getJSeparator(), null);
			jPanel1.add(jLabel3, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jSeparator
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
			jSeparator.setBounds(new java.awt.Rectangle(27, 256, 640, 9));
		}
		return jSeparator;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbtnBomVersion);
			buttonGroup.add(this.rbnDate);
			buttonGroup.add(this.rbtnBom);
		}
		return buttonGroup;
	}

	/**
	 * 根据保存数据显示参数设置
	 */
	private void showData() {
		ParameterSet para = null;
		List list = systemAction.findnameValues(new Request(CommonVars
				.getCurrUser(), true), ParameterType.bomStructureType);
		this.rbtnBomVersion.setSelected(false);
		this.rbnDate.setSelected(false);
		this.rbtnBom.setSelected(false);
		if (!list.isEmpty()) {
			para = (ParameterSet) list.get(0);
			String value = para.getNameValues();
			if ("0".equals(value)) {
				this.rbtnBomVersion.setSelected(true);
			} else if ("1".equals(value)) {
				this.rbnDate.setSelected(true);
			} else if ("2".equals(value)) {
				this.rbtnBom.setSelected(true);
			}
		}
	}

	/*
	 * 根据界面状态设置界面组件的状态
	 */
	private void setState() {
		this.rbtnBomVersion.setEnabled(!(dataState == DataState.BROWSE));
		this.rbnDate.setEnabled(!(dataState == DataState.BROWSE));
		this.rbtnBom.setEnabled(!(dataState == DataState.BROWSE));
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.btnUndo.setEnabled(!(dataState == DataState.BROWSE));
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JInternalFrame#paintComponent(java.awt.Graphics)
	 * 绘画界面组件
	 */
	@Override
	protected void paintComponent(Graphics g) {
		jSeparator.setBounds(3, this.jPanel1.getHeight() - 50, this.jPanel1
				.getWidth() - 6, 3);
		// // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
		// this.jPanel1.getWidth() - 28 -28, 3);
		btnEdit.setBounds(this.jPanel1.getWidth() - 392, this.jPanel1
				.getHeight() - 40, 68, 26);
		btnSave.setBounds(this.jPanel1.getWidth() - 292, this.jPanel1
				.getHeight() - 40, 68, 26);
		btnUndo.setBounds(this.jPanel1.getWidth() - 192, this.jPanel1
				.getHeight() - 40, 68, 26);
		super.paintComponent(g);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
