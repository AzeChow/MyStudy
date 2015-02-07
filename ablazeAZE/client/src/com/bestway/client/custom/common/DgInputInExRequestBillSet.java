package com.bestway.client.custom.common;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.InputInExRequestBillOrder;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Color;

public class DgInputInExRequestBillSet extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel1 = null;

	private JComboBox jComboBox1 = null;

	private JLabel jLabel2 = null;

	private JComboBox jComboBox2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	public Hashtable hs = null;

	private boolean isOk = true;

	private EncAction encAction = null;

	private InputInExRequestBillOrder data = null;

	private String bestwaydata = "D:/bestwaydata"; // 导入总路径

	private String uploaddata = "D:/bestwaydata/uploaddata";// 导入来源路径

	private String uploaderr = "D:/bestwaydata/uploaderr";// 导入失败路径

	private String uploaddatabak = "D:/bestwaydata/uploaddatabak";// 导入成功路径

	private JCheckBox jCheckBox = null;

	private JButton jButton3 = null;

	private int state = DataState.BROWSE;

	private JCheckBox jCheckBox1 = null;

	private JLabel jLabel6 = null;

	private JComboBox cbbIeport = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JButton jButton2 = null;

	private JLabel jLabel9 = null;

	private JComboBox cbbTransferMode = null;

	/**
	 * This is the default constructor
	 */
	public DgInputInExRequestBillSet() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(399, 282);
		this.setContentPane(getJContentPane());
		this.setTitle("申请单文本导入参数设置");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				setpath();
				List list = encAction
						.findInputInExRequestBillOrder(new Request(CommonVars
								.getCurrUser()));
				if (list.size() == 0) {
					return;
				}
				data = (InputInExRequestBillOrder) list.get(0);
				jComboBox.setSelectedItem(data.getScmCocCodeName());// 客户/供应商
				jComboBox1.setSelectedItem(data.getCountryCodeName());// 产销国
				jComboBox2.setSelectedItem(data.getCurencyCodeName());// 币制
				cbbIeport.setSelectedItem(data.getIeportCodeName());// 进出口岸
				cbbTransferMode.setSelectedItem(data.getTransferModeCodeName());//运输方式
				if (data.getIsvalid() != null && data.getIsvalid().equals("1")) {
					jCheckBox.setSelected(true);
				} else {
					jCheckBox.setSelected(false);
				}

				if (data.getIsdjnore() != null
						&& data.getIsdjnore().equals("1")) {
					jCheckBox1.setSelected(true);
				} else {
					jCheckBox1.setSelected(false);
				}

				setState();
			}
		});
	}

	private void setState() {
		jComboBox.setEnabled(state == DataState.EDIT);
		jComboBox1.setEnabled(state == DataState.EDIT);
		jComboBox2.setEnabled(state == DataState.EDIT);
		jCheckBox.setEnabled(state == DataState.EDIT);
		jCheckBox1.setEnabled(state == DataState.EDIT);
//		jButton2.setEnabled(state == DataState.EDIT);
		jButton.setEnabled(state == DataState.EDIT);
		jButton3.setEnabled(state != DataState.EDIT);
		cbbIeport.setEnabled(state == DataState.EDIT);
		cbbTransferMode.setEnabled(state == DataState.EDIT);
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new Rectangle(25, 71,70, 22));
			jLabel9.setText("运输方式");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(25, 177, 340, 18));
			jLabel5.setText("JLabel");
			jLabel5.setForeground(Color.blue);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(25, 150, 340, 18));
			jLabel4.setText("JLabel");
			jLabel4.setForeground(Color.blue);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(25, 124, 340, 18));
			jLabel3.setText("JLabel");
			jLabel3.setForeground(Color.blue);
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(215, 16, 52, 22));
			jLabel6.setText("进出口岸");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(215, 44, 52, 22));
			jLabel2.setText("币    制");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(25, 44, 70, 22));
			jLabel1.setText("产  销  国");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(25, 16, 73, 22));
			jLabel.setText("客户/供应商");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJComboBox1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJComboBox2(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJCheckBox1(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getCbbIeport(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(getJComboBox11(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.addItem("");
			jComboBox.addItem("代码");
			jComboBox.addItem("名称");
			jComboBox.setBounds(new Rectangle(97, 16, 97, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.addItem("");
			jComboBox1.addItem("代码");
			jComboBox1.addItem("名称");
			jComboBox1.setBounds(new Rectangle(96, 44, 97, 22));
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.addItem("");
			jComboBox2.addItem("代码");
			jComboBox2.addItem("名称");
			jComboBox2.setBounds(new Rectangle(266, 44, 99, 22));
		}
		return jComboBox2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(205, 209, 68, 25));
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (data == null) {
						data = new InputInExRequestBillOrder();
					}
					data
							.setScmCocCodeName(jComboBox.getSelectedItem() == null ? ""
									: jComboBox.getSelectedItem().toString());
					data
							.setCountryCodeName(jComboBox1.getSelectedItem() == null ? ""
									: jComboBox1.getSelectedItem().toString());
					data
							.setCurencyCodeName(jComboBox2.getSelectedItem() == null ? ""
									: jComboBox2.getSelectedItem().toString());
					data
							.setIeportCodeName(cbbIeport.getSelectedItem() == null ? ""
									: cbbIeport.getSelectedItem().toString());
					data
					.setTransferModeCodeName(cbbTransferMode.getSelectedItem() == null ? ""
							: cbbTransferMode.getSelectedItem().toString());
					if (jCheckBox.isSelected()) {
						data.setIsvalid("1");
					} else {
						data.setIsvalid("0");
					}

					if (jCheckBox1.isSelected()) {
						data.setIsdjnore("1");
					} else {
						data.setIsdjnore("0");
					}

					encAction.saveInputInExRequestBillOrder(new Request(
							CommonVars.getCurrUser()), data);
					state = DataState.BROWSE;
					setState();
					JOptionPane.showMessageDialog(
							DgInputInExRequestBillSet.this, "保存完毕！", "提示", 2);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(284, 209, 68, 25));
			jButton1.setText("退出");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputInExRequestBillSet.this.dispose();
				}
			});
		}
		return jButton1;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public void setHs(Hashtable hs) {
		this.hs = hs;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(22, 93, 153, 22));
			jCheckBox.setText("导入完毕默认是否生效");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(124, 209, 68, 25));
			jButton3.setText("修改");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					state = DataState.EDIT;
					setState();

				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (jCheckBox1 == null) {
			jCheckBox1 = new JCheckBox();
			jCheckBox1.setBounds(new Rectangle(210, 93, 153, 22));
			jCheckBox1.setText("是否允许单据号重复");
		}
		return jCheckBox1;
	}

	/**
	 * This method initializes cbbIeport
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIeport() {
		if (cbbIeport == null) {
			cbbIeport = new JComboBox();
			cbbIeport.addItem("");
			cbbIeport.addItem("代码");
			cbbIeport.addItem("名称");
			cbbIeport.setBounds(new Rectangle(266, 16, 99, 22));
		}
		return cbbIeport;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(25, 209, 88, 25));
			jButton2.setText("\u8def\u5f84\u751f\u6210");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							DgInputInExRequestBillSet.this, "是否确定要生成文本导入相关路径？",
							"确认", 0) != 0) {
						return;
					}

					File bestwaydatadir = new File(bestwaydata);
					if (!bestwaydatadir.exists()) {
						bestwaydatadir.mkdir();
					} else {
						JOptionPane.showMessageDialog(
								DgInputInExRequestBillSet.this, "导入总路径："
										+ bestwaydata + " 已经存在！", "提示", 2);
					}

					File uploaddatadir = new File(uploaddata);
					if (!uploaddatadir.exists()) {
						uploaddatadir.mkdir();
					} else {
						JOptionPane.showMessageDialog(
								DgInputInExRequestBillSet.this, "导入来源路径："
										+ uploaddata + " 已经存在！", "提示", 2);
					}

					File uploaddatabakdir = new File(uploaddatabak);
					if (!uploaddatabakdir.exists()) {
						uploaddatabakdir.mkdir();
					} else {
						JOptionPane.showMessageDialog(
								DgInputInExRequestBillSet.this, "导入成功路径："
										+ uploaddatabak + " 已经存在！", "提示", 2);
					}

					File uploaderrdir = new File(uploaderr);
					if (!uploaderrdir.exists()) {
						uploaderrdir.mkdir();
					} else {
						JOptionPane.showMessageDialog(
								DgInputInExRequestBillSet.this, "导入失败路径："
										+ uploaderr + " 已经存在！", "提示", 2);
					}

					JOptionPane.showMessageDialog(
							DgInputInExRequestBillSet.this, "路径信息说明：\n\n"
									+ "导入总路径：" + bestwaydata + "\n" + "导入来源路径："
									+ uploaddata + "\n" + "导入成功路径："
									+ uploaddatabak + "\n" + "导入失败路径："
									+ uploaderr, "提示", 2);
					setpath();
				}
			});
		}
		return jButton2;
	}
	private void setpath() {
		File uploaddatadir = new File(uploaddata);
		if (!uploaddatadir.exists()) {
			jLabel3.setText("导入来源路径：暂无设置");
		} else {
			jLabel3.setText("导入来源路径：" + uploaddata);
		}

		File uploaddatabakdir = new File(uploaddatabak);
		if (!uploaddatabakdir.exists()) {
			jLabel4.setText("导入成功路径：暂无设置");
		} else {
			jLabel4.setText("导入成功路径：" + uploaddatabak);
		}

		File uploaderrdir = new File(uploaderr);
		if (!uploaderrdir.exists()) {
			jLabel5.setText("导入失败路径：暂无设置");
		} else {
			jLabel5.setText("导入失败路径：" + uploaderr);
		}
	}

	/**
	 * This method initializes jComboBox11	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox11() {
		if (cbbTransferMode == null) {
			cbbTransferMode = new JComboBox();
			cbbTransferMode.setBounds(new Rectangle(97, 71, 97, 22));
			cbbTransferMode.addItem("代码");
			cbbTransferMode.addItem("名称");
		}
		return cbbTransferMode;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
