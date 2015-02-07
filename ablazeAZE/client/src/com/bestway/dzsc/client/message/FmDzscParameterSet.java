package com.bestway.dzsc.client.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Element;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.cspcard.entity.ICCardInfo;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.RegexUtil;

public class FmDzscParameterSet extends JInternalFrameBase {

	private JPanel jPanel = null; // @jve:decl-index=0:visual-constraint="347,483"

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JButton btnSave = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JTextField tfSendDir = null;

	private JTextField tfRecvDir = null;

	private JTextField tfFinallyDir = null;

	private JTextField tfLogDir = null;

	private JButton btnSendDir = null;

	private JButton btnRecvDir = null;

	private JButton btnFinallyDir = null;

	private JButton btnLogDir = null;

	private JButton btnClose = null;

	private JButton btnEdit = null;

	private DzscMessageAction dzscMessageAction = null;

	private int dataState = DataState.BROWSE;

	private JPanel jContentPane = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private ButtonGroup bg = null; // @jve:decl-index=0:

	private JLabel jLabel19 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanelBtn = null;

	private JPanel jPanel4 = null;

	private JCheckBox cbUpdateEmsExgBomWriteBackImg = null;

	private JCheckBox cbUpdateEmsBomWriteBackExg = null;

	private JTextField tfIdCard = null;

	private JPanel jPanel7 = null;

	private JTextField tfLoadXmlDir = null;

	private JButton btnLoadXmlDir = null;

	private String tempDir = "./";

	private JPanel jPanel9 = null;

	private JLabel jLabel7 = null;

	private JFormattedTextField tfReportDecimalLength = null;

	private JLabel jLabel9 = null;

	private JPanel jPanel911 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JTextField tfRemoteHostAddress = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel8 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel10 = null;

	private JTextField tfRemoteHostPort = null;

	private JPanel jPanel11 = null;

	private JCheckBox cbIsPrintToolCode = null;

	private JCheckBox cbIsPrintNo = null;

	private JPanel jPanel12 = null;

	private JLabel jLabel11 = null;

	private JFormattedTextField tfPrice = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JFormattedTextField tfAmount = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JFormattedTextField tfMoney = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel191 = null;

	private JTextField tfRemoteHostICPwd = null;

	private JCheckBox cbUpdateEmsImgWriteBackExg = null;

	private JLabel jLabel411 = null;
	private JLabel label;
	private JFormattedTextField tfUnitWaste;
	private JLabel label_1;
	private JLabel label_2;
	private JFormattedTextField tfWaste;
	private JLabel label_3;
	private JLabel label_4;
	private JFormattedTextField tfTotalAmount;
	private JLabel label_5;

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	/**
	 * This method initializes
	 * 
	 */
	public FmDzscParameterSet() {
		super();
		initialize();
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		DzscParameterSet dirSet = dzscMessageAction
				.findDzscMessageDirSet1(new Request(CommonVars.getCurrUser()));
		showData(dirSet);
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(778, 515));
		this.setContentPane(getJContentPane());
		getBg();
		this.setTitle("参数设置 ");

	}

	// protected void paintComponent(Graphics g) {
	// // jSeparator .setBounds(3, this.jPanel.getHeight() - 50, this.jPanel
	// // .getWidth() - 6, 3);
	// // // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
	// // this.jPanel1.getWidth() - 28 -28, 3);
	// btnReadCard.setBounds(this.jPanel.getWidth() - 532, this.jPanel
	// .getHeight() - 40, 108, 26);
	// btnEdit.setBounds(this.jPanel.getWidth() - 392,
	// this.jPanel.getHeight() - 40, 68, 26);
	// btnOk.setBounds(this.jPanel.getWidth() - 292,
	// this.jPanel.getHeight() - 40, 68, 26);
	// btnClose.setBounds(this.jPanel.getWidth() - 192, this.jPanel
	// .getHeight() - 40, 68, 26);
	// super.paintComponent(g);
	// }

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("\u65e5\u5fd7\u6240\u5728\u8def\u5f84");
			jLabel4.setBounds(new Rectangle(53, 108, 112, 19));
			jLabel3 = new JLabel();
			jLabel3.setText("\u5b58\u653e\u8def\u5f84");
			jLabel3.setBounds(new Rectangle(53, 87, 112, 18));
			jLabel2 = new JLabel();
			jLabel2.setText("\u5904\u7406\u5b8c\u7684\u56de\u6267");
			jLabel2.setBounds(new Rectangle(53, 70, 112, 18));
			jLabel1 = new JLabel();
			jLabel1.setText("回执存放路径");
			jLabel1.setBounds(new Rectangle(53, 52, 112, 17));
			jLabel = new JLabel();
			jLabel.setText("报文发送路径");
			jLabel.setBounds(new Rectangle(53, 25, 112, 22));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setVisible(true);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getJPanel9(), null);
			jPanel.add(getJPanel911(), null);
			jPanel.add(getJPanel7(), null);
			jPanel.add(getJPanel8(), null);
			jPanel.add(getJPanel11(), null);
			jPanel.add(getJPanel12(), null);
			getJPanel911();
			// jPanel.add(getJPanel911(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnReadCard
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(510, 444, 78, 27));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscParameterSet dirSet = dzscMessageAction
							.findDzscMessageDirSet(new Request(CommonVars
									.getCurrUser()));
					if (checkNull()) {
						return;
					}
					if (tfReportDecimalLength.getValue() == null
							|| Double
									.valueOf(
											tfReportDecimalLength.getValue()
													.toString()).intValue() == 0) {
						JOptionPane
								.showMessageDialog(FmDzscParameterSet.this,
										"请输入要保留的小数位位数", "提示",
										JOptionPane.ERROR_MESSAGE);
						return;

					}
					fillData(dirSet);
					dzscMessageAction.saveCspMessageDirSet(new Request(
							CommonVars.getCurrUser()), dirSet);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 
	 * 检查是否为空
	 */
	private boolean checkNull() {
		if (this.tfRemoteHostAddress.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(FmDzscParameterSet.this,
					"请输入远程加签服务器地址", "提示", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if (this.tfRemoteHostPort.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(FmDzscParameterSet.this,
					"请输入远程加签服务器端口", "提示", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if (!"".equals(tfPrice.getText().trim())) {
			if (Integer.valueOf(tfPrice.getText()) >= 6) {
				JOptionPane.showMessageDialog(this,
						"单价小数位不能超过海关允许的最大小数位(海关允许的最大小数位为５)！", "提示！", 0);
				return true;
			}
			try {
				Integer.parseInt(tfPrice.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的单价小数位不是数值形式！", "提示！",
						0);
				return true;
			}
		}
		if (!"".equals(tfAmount.getText().trim())) {
			if (Integer.valueOf(tfAmount.getText()) >= 6) {
				JOptionPane.showMessageDialog(this,
						"数量小数位不能超过海关允许的最大小数位(海关允许的最大小数位为５)！", "提示！", 0);
				return true;
			}
			try {
				Integer.parseInt(tfAmount.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的数量小数位不是数值形式！", "提示！",
						0);
				return true;
			}
		}
		if (!"".equals(tfMoney.getText().trim())) {
			if (Integer.valueOf(tfMoney.getText()) >= 6) {
				JOptionPane.showMessageDialog(this,
						"金额小数位不能超过海关允许的最大小数位(海关允许的最大小数位为５)！", "提示！", 0);
				return true;
			}
			try {
				Integer.parseInt(tfMoney.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "您输入的金额小数位不是数值形式！", "提示！",
						0);
				return true;
			}
		}
		return false;
	}

	private void showData(DzscParameterSet parameter) {
		if (parameter == null) {
			return;
		}
		this.tfSendDir.setText(parameter.getSendDir());
		this.tfRecvDir.setText(parameter.getRecvDir());
		this.tfFinallyDir.setText(parameter.getFinallyDir());
		this.tfLogDir.setText(parameter.getLogDir());
		
		this.tfIdCard.setText(parameter.getIdCard());
		if (parameter.getUpdateEmsBomWriteBackExg() != null
				&& parameter.getUpdateEmsBomWriteBackExg()) {
			this.cbUpdateEmsBomWriteBackExg.setSelected(true);
		} else {
			this.cbUpdateEmsBomWriteBackExg.setSelected(false);
		}
		if (parameter.getUpdateEmsExgBomWriteBackImg() != null
				&& parameter.getUpdateEmsExgBomWriteBackImg()) {
			this.cbUpdateEmsExgBomWriteBackImg.setSelected(true);
		} else {
			this.cbUpdateEmsExgBomWriteBackImg.setSelected(false);
		}
		if(parameter.getUpdateEmsImgWriteBackExg()!=null&&
				parameter.getUpdateEmsImgWriteBackExg()){
			this.cbUpdateEmsImgWriteBackExg.setSelected(true);
		}else{
			this.cbUpdateEmsImgWriteBackExg.setSelected(false);
		}
		this.tfLoadXmlDir.setText(parameter.getLoadQPDataXmlDir());

		if (parameter != null && parameter.getReportDecimalLength() != null) {
			this.tfReportDecimalLength.setValue(parameter
					.getReportDecimalLength());
		} else {
			this.tfReportDecimalLength.setValue(2);
		}
		if (parameter != null && ("1".equals(parameter.getManageType()))) {
			getJRadioButton().setSelected(true);
		} else {
			getJRadioButton1().setSelected(true);
		}
		

		this.tfRemoteHostAddress.setText(parameter.getRemoteHostAddress());
		this.tfRemoteHostPort.setText(parameter.getRemoteHostPort());
		this.tfRemoteHostICPwd.setText(parameter.getRemoteHostICPwd());
		// if (parameter.getDzscManageType() == null
		// || parameter.getDzscManageType() == DzscManageType.MATERIAL) {
		// this.rbMaterial.setSelected(true);
		// } else {
		// this.rbComplex.setSelected(true);
		// }\
		this.cbIsPrintNo.setSelected(parameter.getIsPrintNo());
		this.cbIsPrintToolCode.setSelected(parameter.getIsPrintToolCode());
		if (parameter != null && parameter.getReportDecimalLength() != null) {
			this.tfAmount.setValue(parameter.getCountBit());
		} else {
			this.tfAmount.setValue(5);
		}
		if (parameter != null && parameter.getReportDecimalLength() != null) {
			this.tfMoney.setValue(parameter.getMoneyBit());
		} else {
			this.tfMoney.setValue(5);
		}
		if (parameter != null && parameter.getReportDecimalLength() != null) {
			this.tfPrice.setValue(parameter.getPriceBit());
		} else {
			this.tfPrice.setValue(5);
		}
		
		if (parameter != null && parameter.getUnitWaste() != null) {
			this.tfUnitWaste.setValue(parameter.getUnitWaste());
		}
		
		if (parameter != null && parameter.getWaste() != null) {
			this.tfWaste.setValue(parameter.getWaste());
		}
		
		if (parameter != null && parameter.getTotalAmount() != null) {
			this.tfTotalAmount.setValue(parameter.getTotalAmount());
		}
	}

	private void fillData(DzscParameterSet parameter) {
		if (parameter == null) {
			return;
		}
		parameter.setSendDir(this.tfSendDir.getText().trim());
		parameter.setRecvDir(this.tfRecvDir.getText().trim());
		parameter.setFinallyDir(this.tfFinallyDir.getText().trim());
		parameter.setLogDir(this.tfLogDir.getText().trim());
	
		parameter.setIdCard(this.tfIdCard.getText().trim());

		parameter.setUpdateEmsBomWriteBackExg(this.cbUpdateEmsBomWriteBackExg
				.isSelected());
		parameter
				.setUpdateEmsExgBomWriteBackImg(this.cbUpdateEmsExgBomWriteBackImg
						.isSelected());
		parameter.setUpdateEmsImgWriteBackExg(this.cbUpdateEmsImgWriteBackExg
				.isSelected());
		
		parameter.setLoadQPDataXmlDir(this.tfLoadXmlDir.getText().trim());

		parameter.setReportDecimalLength(Double.valueOf(
				tfReportDecimalLength.getValue().toString()).intValue());
		parameter.setManageType(getJRadioButton().isSelected() ? "1" : "0");
	

		parameter.setRemoteHostAddress(tfRemoteHostAddress.getText());
		parameter.setRemoteHostPort(tfRemoteHostPort.getText());
		parameter.setRemoteHostICPwd(tfRemoteHostICPwd.getText().trim());

		parameter.setIsPrintToolCode(cbIsPrintToolCode.isSelected());
		parameter.setIsPrintNo(cbIsPrintNo.isSelected());
		parameter.setCountBit(tfAmount.getValue() == null ? 5 : Integer
				.valueOf(tfAmount.getValue().toString().trim()));
		parameter.setMoneyBit(tfMoney.getValue() == null ? 5 : Integer
				.valueOf(tfMoney.getValue().toString().trim()));
		parameter.setPriceBit(tfPrice.getValue() == null ? 5 : Integer
				.valueOf(tfPrice.getValue().toString().trim()));
		// if (this.rbMaterial.isSelected()) {
		// parameter.setDzscManageType(DzscManageType.MATERIAL);
		// } else {
		// parameter.setDzscManageType(DzscManageType.COMPLEX);
		// }
		
		parameter.setUnitWaste(stringToInteger(tfUnitWaste.getText()));
		parameter.setWaste(stringToInteger(tfWaste.getText()));
		parameter.setTotalAmount(stringToInteger(tfTotalAmount.getText()));
	}

	private Integer stringToInteger(String stu){
		if(stu==null){
			return null;
		}else{
			try {
				Integer len = Integer.parseInt(stu.trim());
				return len;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSendDir() {
		if (tfSendDir == null) {
			tfSendDir = new JTextField();
			tfSendDir.setBounds(new Rectangle(175, 21, 164, 23));
		}
		return tfSendDir;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRecvDir() {
		if (tfRecvDir == null) {
			tfRecvDir = new JTextField();
			tfRecvDir.setBounds(new Rectangle(175, 49, 164, 23));
		}
		return tfRecvDir;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFinallyDir() {
		if (tfFinallyDir == null) {
			tfFinallyDir = new JTextField();
			tfFinallyDir.setBounds(new Rectangle(175, 78, 164, 23));
		}
		return tfFinallyDir;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLogDir() {
		if (tfLogDir == null) {
			tfLogDir = new JTextField();
			tfLogDir.setBounds(new Rectangle(175, 106, 164, 23));
		}
		return tfLogDir;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSendDir() {
		if (btnSendDir == null) {
			btnSendDir = new JButton();
			btnSendDir.setText("...");
			btnSendDir.setBounds(new Rectangle(339, 21, 23, 23));
			btnSendDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfSendDir);
					// JFileChooser fileChooser = new JFileChooser("./");
					// fileChooser
					// .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					// int state =
					// fileChooser.showDialog(FmDzscParameterSet.this,
					// "选择路径");
					// if (state == JFileChooser.APPROVE_OPTION) {
					// fileChooser.getSelectedFile();
					// File f = fileChooser.getSelectedFile();
					// tfSendDir.setText(f.getPath());
					// }
				}
			});
		}
		return btnSendDir;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRecvDir() {
		if (btnRecvDir == null) {
			btnRecvDir = new JButton();
			btnRecvDir.setText("...");
			btnRecvDir.setBounds(new Rectangle(339, 50, 23, 23));
			btnRecvDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfRecvDir);
					// JFileChooser fileChooser = new JFileChooser("./");
					// fileChooser
					// .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					// int state =
					// fileChooser.showDialog(FmDzscParameterSet.this,
					// "选择路径");
					// if (state == JFileChooser.APPROVE_OPTION) {
					// fileChooser.getSelectedFile();
					// File f = fileChooser.getSelectedFile();
					// tfRecvDir.setText(f.getPath());
					// }
				}
			});
		}
		return btnRecvDir;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinallyDir() {
		if (btnFinallyDir == null) {
			btnFinallyDir = new JButton();
			btnFinallyDir.setText("...");
			btnFinallyDir.setBounds(new Rectangle(339, 77, 23, 23));
			btnFinallyDir
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDir(tfFinallyDir);
							// JFileChooser fileChooser = new
							// JFileChooser("./");
							// fileChooser
							// .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							// int state = fileChooser.showDialog(
							// FmDzscParameterSet.this, "选择路径");
							// if (state == JFileChooser.APPROVE_OPTION) {
							// fileChooser.getSelectedFile();
							// File f = fileChooser.getSelectedFile();
							// tfFinallyDir.setText(f.getPath());
							// }
						}
					});
		}
		return btnFinallyDir;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLogDir() {
		if (btnLogDir == null) {
			btnLogDir = new JButton();
			btnLogDir.setText("...");
			btnLogDir.setBounds(new Rectangle(339, 106, 23, 23));
			btnLogDir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDir(tfLogDir);
					// JFileChooser fileChooser = new JFileChooser("./");
					// fileChooser
					// .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					// int state =
					// fileChooser.showDialog(FmDzscParameterSet.this,
					// "选择路径");
					// if (state == JFileChooser.APPROVE_OPTION) {
					// fileChooser.getSelectedFile();
					// File f = fileChooser.getSelectedFile();
					// tfLogDir.setText(f.getPath());
					// }
				}
			});
		}
		return btnLogDir;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(618, 444, 78, 27));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmDzscParameterSet.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(403, 444, 78, 27));
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
					if (tfSendDir.getText().trim().equals("")) {
						tfSendDir
								.setText("D:\\Project\\Delivery\\Company\\Xml");
					}
					if (tfRecvDir.getText().trim().equals("")) {
						tfRecvDir
								.setText("D:\\Project\\Delivery\\Customs\\Rec");
					}
					if (tfFinallyDir.getText().trim().equals("")) {
						tfFinallyDir
								.setText("D:\\Project\\Delivery\\Customs\\Finally");
					}
				}
			});
		}
		return btnEdit;
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
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel19 = new JLabel();
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel19.setText("");
			jLabel18 = new JLabel();
			jLabel18.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setText("电子手册系统参数设置");
			jLabel18.setForeground(new Color(255, 153, 0));
			jLabel17 = new JLabel();
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel17.setText("");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBackground(Color.white);
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel1.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel1.add(jLabel18, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel1;
	}

	private void setState() {
		this.tfSendDir.setEditable(dataState != DataState.BROWSE);
		this.tfRecvDir.setEditable(dataState != DataState.BROWSE);
		this.tfFinallyDir.setEditable(dataState != DataState.BROWSE);
		this.tfLogDir.setEditable(dataState != DataState.BROWSE);
		

		this.btnSendDir.setEnabled(dataState != DataState.BROWSE);
		this.btnRecvDir.setEnabled(dataState != DataState.BROWSE);
		this.btnFinallyDir.setEnabled(dataState != DataState.BROWSE);
		this.btnLogDir.setEnabled(dataState != DataState.BROWSE);

		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.cbUpdateEmsBomWriteBackExg
				.setEnabled(dataState != DataState.BROWSE);
		this.cbUpdateEmsExgBomWriteBackImg
				.setEnabled(dataState != DataState.BROWSE);
		this.cbUpdateEmsImgWriteBackExg
		.setEnabled(dataState != DataState.BROWSE);
		this.tfLoadXmlDir.setEditable(dataState != DataState.BROWSE);
		this.btnLoadXmlDir.setEnabled(dataState != DataState.BROWSE);
		this.tfReportDecimalLength.setEnabled(dataState != DataState.BROWSE);
		getJRadioButton().setEnabled(dataState != DataState.BROWSE);
		getJRadioButton1().setEnabled(dataState != DataState.BROWSE);
		this.tfRemoteHostAddress.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostPort.setEditable(dataState != DataState.BROWSE);
		this.tfRemoteHostICPwd.setEditable(dataState != DataState.BROWSE);
		this.tfIdCard.setEditable(dataState != DataState.BROWSE);
		this.cbIsPrintNo.setEnabled(dataState != DataState.BROWSE);
		this.cbIsPrintToolCode.setEnabled(dataState != DataState.BROWSE);
		this.tfPrice.setEnabled(dataState != DataState.BROWSE);
		this.tfAmount.setEnabled(dataState != DataState.BROWSE);
		this.tfMoney.setEnabled(dataState != DataState.BROWSE);
		this.tfUnitWaste.setEnabled(dataState != DataState.BROWSE);
		this.tfWaste.setEnabled(dataState != DataState.BROWSE);
		this.tfTotalAmount.setEnabled(dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(7, -1, 448, 145));
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"\u8def\u5f84\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getTfSendDir(), null);
			jPanel2.add(getTfRecvDir(), null);
			jPanel2.add(getTfFinallyDir(), null);
			jPanel2.add(getTfLogDir(), null);
			jPanel2.add(getBtnSendDir(), null);
			jPanel2.add(getBtnRecvDir(), null);
			jPanel2.add(getBtnFinallyDir(), null);
			jPanel2.add(getBtnLogDir(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(467, 211, 297, 93));
			jPanel4
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u901a\u5173\u5907\u6848\u6570\u636e\u53cd\u5199\u8bbe\u7f6e",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.BOLD, 12),
									new Color(51, 51, 51)));
			jPanel4.add(getCbUpdateEmsExgBomWriteBackImg(), null);
			jPanel4.add(getCbUpdateEmsBomWriteBackExg(), null);
			jPanel4.add(getCbUpdateEmsImgWriteBackExg(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes cbUpdateEmsExgBomWriteBackImg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUpdateEmsExgBomWriteBackImg() {
		if (cbUpdateEmsExgBomWriteBackImg == null) {
			cbUpdateEmsExgBomWriteBackImg = new JCheckBox();
			cbUpdateEmsExgBomWriteBackImg.setBounds(new Rectangle(24, 19, 206, 22));
			cbUpdateEmsExgBomWriteBackImg.setText("修改成品或单耗反写料件数量");
		}
		return cbUpdateEmsExgBomWriteBackImg;
	}

	/**
	 * This method initializes cbUpdateEmsBomWriteBackExg
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUpdateEmsBomWriteBackExg() {
		if (cbUpdateEmsBomWriteBackExg == null) {
			cbUpdateEmsBomWriteBackExg = new JCheckBox();
			cbUpdateEmsBomWriteBackExg
					.setBounds(new Rectangle(24, 39, 258, 22));
			cbUpdateEmsBomWriteBackExg.setText("修改单耗反写成品单价");
		}
		return cbUpdateEmsBomWriteBackExg;
	}

	/**
	 * This method initializes tfIdCard
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfIdCard() {
		if (tfIdCard == null) {
			tfIdCard = new JTextField();
			tfIdCard.setBounds(new Rectangle(94, 54, 313, 23));
		}
		return tfIdCard;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jPanel7.setBorder(BorderFactory.createTitledBorder(null,
					"QP\u6570\u636e\u5bfc\u5165\u8def\u5f84\u8bbe\u5b9a",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel7.setBounds(new Rectangle(8, 148, 447, 54));
			jPanel7.add(getTfLoadXmlDir(), null);
			jPanel7.add(getBtnLoadXmlDir(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLoadXmlDir() {
		if (tfLoadXmlDir == null) {
			tfLoadXmlDir = new JTextField();
			tfLoadXmlDir.setBounds(new Rectangle(32, 21, 363, 21));
		}
		return tfLoadXmlDir;
	}

	/**
	 * This method initializes btnSendDir1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnLoadXmlDir() {
		if (btnLoadXmlDir == null) {
			btnLoadXmlDir = new JButton();
			btnLoadXmlDir.setBounds(new Rectangle(395, 20, 21, 21));
			btnLoadXmlDir.setText("...");
			btnLoadXmlDir
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDir(tfLoadXmlDir);
							// JFileChooser fileChooser = new
							// JFileChooser("./");
							// fileChooser
							// .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							// int state = fileChooser.showDialog(
							// FmDzscParameterSet.this, "选择路径");
							// if (state == JFileChooser.APPROVE_OPTION) {
							// fileChooser.getSelectedFile();
							// File f = fileChooser.getSelectedFile();
							// tfLoadXmlDir.setText(f.getPath());
							// }
						}
					});
		}
		return btnLoadXmlDir;
	}

	private void setDir(JTextField jtf) {
		if (jtf.getText() != null && !"".equals(jtf.getText())) {
			File file = new File(jtf.getText());
			if (file.exists())
				setTempDir(jtf.getText());
		}
		JFileChooser fileChooser = new JFileChooser(getTempDir());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int state = fileChooser.showDialog(FmDzscParameterSet.this, "选择路径");
		if (state == JFileChooser.APPROVE_OPTION) {
			fileChooser.getSelectedFile();
			File f = fileChooser.getSelectedFile();
			jtf.setText(f.getPath());
			setTempDir(jtf.getText());
		}
	}

	public JPanel getJPanelBtn() {
		if (jPanelBtn == null) {
			jPanelBtn = new JPanel();
			jPanelBtn.add(getBtnEdit(), null);
			jPanelBtn.add(getBtnOk(), null);
			jPanelBtn.add(getBtnClose(), null);
		}
		return jPanelBtn;
	}

	/**
	 * This method initializes jPanel81
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(114, 21, 45, 23));
			jLabel9.setText("位小数");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(13, 21, 55, 23));
			jLabel7.setText("最多保留:");
			jPanel9 = new JPanel();
			jPanel9.setLayout(null);
			jPanel9.setBounds(new Rectangle(467, 11, 297, 47));
			jPanel9.setBorder(BorderFactory.createTitledBorder(null,
					"报表数量、金额栏位小数位控制", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel9.add(jLabel7, null);
			jPanel9.add(getTfReportDecimalLength(), null);
			jPanel9.add(jLabel9, null);
		}
		return jPanel9;
	}

	/**
	 * This method initializes tfLoadXmlDir1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfReportDecimalLength() {
		if (tfReportDecimalLength == null) {
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			tfReportDecimalLength = new JFormattedTextField();
			tfReportDecimalLength.setBounds(new Rectangle(68, 21, 46, 23));
			tfReportDecimalLength.setFormatterFactory(defaultFormatterFactory);
		}
		return tfReportDecimalLength;
	}

	/**
	 * This method initializes jPanel911
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel911() {
		if (jPanel911 == null) {
			jPanel911 = new JPanel();
			jPanel911.setLayout(null);
			jPanel911.setBorder(BorderFactory.createTitledBorder(null,
					"\u8f6f\u4ef6\u7ba1\u7406\u529f\u80fd\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, Color.blue));
			jPanel911.setBounds(new Rectangle(9, 308, 444, 84));
			jPanel911.add(getJRadioButton(), null);
			jPanel911.add(getJRadioButton1(), null);
		}
		return jPanel911;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(10, 25, 227, 19));
			jRadioButton
					.setText("\u4ec5\u7528\u4e8e\u5173\u52a1\u7ba1\u7406\u4e0d\u5411\u6d77\u5173\u7533\u62a5\u6570\u636e");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(10, 53, 227, 19));
			jRadioButton1.setSelected(true);
			jRadioButton1
					.setText("\u65e2\u7528\u4e8e\u5173\u52a1\u7ba1\u7406\u4e5f\u5411\u6d77\u5173\u7533\u62a5\u6570\u636e");
		}
		return jRadioButton1;
	}

	private ButtonGroup getBg() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getJRadioButton());
			bg.add(getJRadioButton1());
		}
		return bg;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostAddress() {
		if (tfRemoteHostAddress == null) {
			tfRemoteHostAddress = new JTextField();
			tfRemoteHostAddress.setBounds(new Rectangle(58, 18, 118, 24));
		}
		return tfRemoteHostAddress;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanelBtn());
		}
		return jToolBar;
	}


	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jLabel411 = new JLabel();
			jLabel411.setBounds(new Rectangle(23, 54, 65, 23));
			jLabel411.setText("操作员卡号");
			jLabel191 = new JLabel();
			jLabel191.setBounds(new Rectangle(271, 18, 43, 24));
			jLabel191.setText("\u5361\u5bc6\u7801");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(181, 18, 35, 24));
			jLabel10.setText("端口");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(27, 18, 30, 24));
			jLabel8.setText("地址");
			jPanel8 = new JPanel();
			jPanel8.setLayout(null);
			jPanel8.setBounds(new Rectangle(8, 211, 446, 88));
			jPanel8.setBorder(BorderFactory.createTitledBorder(null,
					"\u8fdc\u7a0b\u670d\u52a1\u5730\u5740",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel8.add(getTfRemoteHostAddress(), null);
			jPanel8.add(jLabel8, null);
			jPanel8.add(jLabel10, null);
			jPanel8.add(getTfRemoteHostPort(), null);
			jPanel8.add(jLabel191, null);
			jPanel8.add(getTfRemoteHostICPwd(), null);
			jPanel8.add(getTfIdCard(), null);
			jPanel8.add(jLabel411, null);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRemoteHostPort() {
		if (tfRemoteHostPort == null) {
			tfRemoteHostPort = new JTextField();
			tfRemoteHostPort.setBounds(new Rectangle(217, 18, 52, 24));
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
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.setBounds(new Rectangle(464, 307, 297, 84));
			jPanel11.setBorder(BorderFactory.createTitledBorder("电子手册报关单参数"));
			jPanel11.add(getCbIsPrintToolCode(), null);
			jPanel11.add(getCbIsPrintNo(), null);
		}
		return jPanel11;
	}

	/**
	 * This method initializes cbIsPrintToolCode
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsPrintToolCode() {
		if (cbIsPrintToolCode == null) {
			cbIsPrintToolCode = new JCheckBox();
			cbIsPrintToolCode.setBounds(new Rectangle(19, 23, 190, 28));
			cbIsPrintToolCode.setText("是否打印运输工具代码");
		}
		return cbIsPrintToolCode;
	}

	/**
	 * This method initializes cbIsPrintNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsPrintNo() {
		if (cbIsPrintNo == null) {
			cbIsPrintNo = new JCheckBox();
			cbIsPrintNo.setBounds(new Rectangle(19, 53, 208, 26));
			cbIsPrintNo.setText("是否在运输工具中打印航次");
		}
		return cbIsPrintNo;
	}

	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel12 == null) {
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(161, 56, 38, 18));
			jLabel16.setText("位");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(14, 55, 102, 18));
			jLabel15.setText("金额小数位保留");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(161, 37, 38, 18));
			jLabel14.setText("位");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new Rectangle(14, 36, 102, 18));
			jLabel13.setText("数量小数位保留");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(161, 18, 38, 18));
			jLabel12.setText("位");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(14, 17, 102, 18));
			jLabel11.setText("单价小数位保留");
			jPanel12 = new JPanel();
			jPanel12.setLayout(null);
			jPanel12.setBounds(new Rectangle(466, 62, 297, 139));
			jPanel12.setBorder(BorderFactory
					.createTitledBorder("电子手册通关备案小数位设置"));
			jPanel12.add(jLabel11, null);
			jPanel12.add(getTfPrice(), null);
			jPanel12.add(jLabel12, null);
			jPanel12.add(jLabel13, null);
			jPanel12.add(getTfAmount(), null);
			jPanel12.add(jLabel14, null);
			jPanel12.add(jLabel15, null);
			jPanel12.add(getTfMoney(), null);
			jPanel12.add(jLabel16, null);
			jPanel12.add(getLabel());
			jPanel12.add(getTfUnitWaste());
			jPanel12.add(getLabel_1());
			jPanel12.add(getLabel_2());
			jPanel12.add(getTfWaste());
			jPanel12.add(getLabel_3());
			jPanel12.add(getLabel_4());
			jPanel12.add(getTfTotalAmount());
			jPanel12.add(getLabel_5());
		}
		return jPanel12;
	}

	/**
	 * This method initializes tfPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfPrice() {
		if (tfPrice == null) {
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			tfPrice = new JFormattedTextField();
			tfPrice.setBounds(new Rectangle(120, 18, 38, 18));
			tfPrice.setFormatterFactory(defaultFormatterFactory);
		}
		return tfPrice;
	}

	/**
	 * This method initializes tfAmount
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new JFormattedTextField();
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			tfAmount.setBounds(new Rectangle(120, 37, 38, 18));
			tfAmount.setFormatterFactory(defaultFormatterFactory);
		}
		return tfAmount;
	}

	/**
	 * This method initializes tfMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfMoney() {
		if (tfMoney == null) {
			tfMoney = new JFormattedTextField();
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			tfMoney.setBounds(new Rectangle(120, 56, 38, 18));
			tfMoney.setFormatterFactory(defaultFormatterFactory);
		}
		return tfMoney;
	}

	/**
	 * This method initializes tfRemoteHostICPwd	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfRemoteHostICPwd() {
		if (tfRemoteHostICPwd == null) {
			tfRemoteHostICPwd = new JTextField();
			tfRemoteHostICPwd.setBounds(new Rectangle(312, 18, 95, 24));
		}
		return tfRemoteHostICPwd;
	}

	/**
	 * This method initializes cbUpdateEmsImgWriteBackExg	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbUpdateEmsImgWriteBackExg() {
		if (cbUpdateEmsImgWriteBackExg == null) {
			cbUpdateEmsImgWriteBackExg = new JCheckBox();
			cbUpdateEmsImgWriteBackExg.setBounds(new Rectangle(24, 60, 257, 26));
			cbUpdateEmsImgWriteBackExg.setText("修改料件反写成品(单价/金额)、单耗");
		}
		return cbUpdateEmsImgWriteBackExg;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("单耗小数位保留");
			label.setBounds(new Rectangle(14, 49, 102, 18));
			label.setBounds(14, 74, 102, 18);
		}
		return label;
	}
	private JFormattedTextField getTfUnitWaste() {
		if (tfUnitWaste == null) {
			tfUnitWaste = new JFormattedTextField();
			tfUnitWaste.setEnabled(false);
			tfUnitWaste.setBounds(new Rectangle(120, 49, 38, 18));
			tfUnitWaste.setBounds(120, 75, 38, 18);
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			tfUnitWaste.setFormatterFactory(defaultFormatterFactory);
		}
		return tfUnitWaste;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel();
			label_1.setText("位");
			label_1.setBounds(new Rectangle(161, 49, 38, 18));
			label_1.setBounds(161, 75, 38, 18);
		}
		return label_1;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel();
			label_2.setText("损耗量小数位保留");
			label_2.setBounds(new Rectangle(14, 49, 102, 18));
			label_2.setBounds(14, 93, 102, 18);
		}
		return label_2;
	}
	private JFormattedTextField getTfWaste() {
		if (tfWaste == null) {
			tfWaste = new JFormattedTextField();
			tfWaste.setEnabled(false);
			tfWaste.setBounds(new Rectangle(120, 49, 38, 18));
			tfWaste.setBounds(120, 94, 38, 18);
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			tfWaste.setFormatterFactory(defaultFormatterFactory);
		}
		return tfWaste;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel();
			label_3.setText("位");
			label_3.setBounds(new Rectangle(161, 49, 38, 18));
			label_3.setBounds(161, 94, 38, 18);
		}
		return label_3;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel();
			label_4.setText("总用量小数位保留");
			label_4.setBounds(new Rectangle(14, 49, 102, 18));
			label_4.setBounds(14, 112, 102, 18);
		}
		return label_4;
	}
	private JFormattedTextField getTfTotalAmount() {
		if (tfTotalAmount == null) {
			tfTotalAmount = new JFormattedTextField();
			tfTotalAmount.setEnabled(false);
			tfTotalAmount.setBounds(new Rectangle(120, 49, 38, 18));
			tfTotalAmount.setBounds(120, 113, 38, 18);
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(new NumberFormatter());
			tfTotalAmount.setFormatterFactory(defaultFormatterFactory);
		}
		return tfTotalAmount;
	}
	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel();
			label_5.setText("位");
			label_5.setBounds(new Rectangle(161, 49, 38, 18));
			label_5.setBounds(161, 113, 38, 18);
		}
		return label_5;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
