/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractstat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import com.bestway.bcs.client.contractstat.FmContractReport;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.SystemColor;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmDzscContractReport extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JLabel jLabel = null;

	private JComboBox cbbContract = null;

	private DzscAction contractAction = null;

	private JButton jButton9 = null;

	private JPanel jPanel3 = null;

	private PnImageBackground jPanel4 = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel6 = null;

	private JPanel jPanel7 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton3 = null;

	private JRadioButton jRadioButton4 = null;

	private JRadioButton jRadioButton5 = null;

	private JRadioButton jRadioButton6 = null;

	private JRadioButton jRadioButton7 = null;

	private JRadioButton jRadioButton8 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="191,33"

	private JRadioButton jRadioButton9 = null;

	private JRadioButton jRadioButton10 = null;

	private JPanel jPanel = null;

	private JRadioButton jRadioButton12 = null;

	private JRadioButton jRadioButton13 = null;

	private JPanel jPanel1 = null;

	private JRadioButton jRadioButton41 = null;

	

	/**
	 * This is the default constructor
	 */
	public FmDzscContractReport() {
		super();
		getButtonGroup();
		initialize();
		contractAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction"); 
		contractAction.checkContractReport(new Request(
				CommonVars.getCurrUser()));
		// 初始化合同
		List contracts = contractAction.findDzscEmsPorHeadExcu(new Request(
				CommonVars.getCurrUser()));
		this.cbbContract.removeAllItems();
		if (contracts != null && contracts.size() > 0) {
			for (int i = 0; i < contracts.size(); i++) {
				this.cbbContract.addItem((DzscEmsPorHead) contracts.get(i));
			}
			this.cbbContract.setRenderer(CustomBaseRender.getInstance()
					.getRender("emsNo","ieContractNo" ,200, 200));
		}
		if (this.cbbContract.getItemCount() > 0) {
			this.cbbContract.setSelectedIndex(0);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("电子手册--合同统计报表");
		this.setSize(771, 580);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.CENTER);
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
			jLabel = new JLabel();
			jToolBar = new JToolBar();
			jLabel.setText("当前手册号  +  合同号");
			jToolBar.add(getJButton9());
			jToolBar.add(jLabel);
			jToolBar.add(getJPanel3());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
		}
		return cbbContract;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("a");
			jButton9.setVisible(false);
		}
		return jButton9;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.ipadx = 334;
			gridBagConstraints12.ipady = -5;
			gridBagConstraints12.insets = new java.awt.Insets(4, 5, 4, 30);
			jPanel3.add(getCbbContract(), gridBagConstraints12);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new PnImageBackground();
			jPanel4.setLayout(null);
			jPanel4.add(getJPanel5(), null);
			jPanel4.add(getJPanel6(), null);
			jPanel4.add(getJPanel7(), null);
			jPanel4.add(getJPanel(), null);
			jPanel4.add(getJPanel1(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(20, 28, 625, 97);
			jPanel5.setOpaque(false);
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"合同执行结果",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, java.awt.SystemColor.activeCaption));
			jPanel5.add(getJRadioButton(), null);
			jPanel5.add(getJRadioButton1(), null);
			jPanel5.add(getJRadioButton2(), null);
			jPanel5.add(getJRadioButton3(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jPanel6.setBounds(20, 140, 625, 91);
			jPanel6.setOpaque(false);
			jPanel6
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"合同执行进度表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, java.awt.SystemColor.activeCaption));
			jPanel6.add(getJRadioButton4(), null);
			jPanel6.add(getJRadioButton5(), null);
			jPanel6.add(getJRadioButton6(), null);
			jPanel6.add(getJRadioButton7(), null);
		}
		return jPanel6;
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
			jPanel7.setBounds(18, 435, 625, 56);
			jPanel7.setOpaque(false);
			jPanel7.setVisible(false);
			jPanel7
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"其他",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, java.awt.SystemColor.activeCaption));
			jPanel7.add(getJRadioButton8(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(164, 28, 148, 23);
			jRadioButton.setText("进口料件情况统计表");
			jRadioButton.setOpaque(false);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbContract.getItemCount() < 1) {
						JOptionPane.showMessageDialog(
								FmDzscContractReport.this, "系统没有正在执行的电子手册",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					if (cbbContract.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(
								FmDzscContractReport.this, "请选择电子手册", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					DgDzscImpMaterialStat dg = new DgDzscImpMaterialStat();
					dg.setContract((DzscEmsPorHead) cbbContract
							.getSelectedItem());
					dg.setVisible(true);
				}
			});
		}
		return jRadioButton;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(164, 57, 148, 23);
			jRadioButton1.setText("进口料件报关登记表");
			jRadioButton1.setOpaque(false);
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
//							DgDzscImpMaterialList dg = new DgDzscImpMaterialList();
//							dg.setContract((DzscEmsPorHead) cbbContract
//									.getSelectedItem());
							DgDzscImpMaterialListNew dg = new DgDzscImpMaterialListNew();
							dg.setTitle("进口料件报关登记表");
							dg.setPrintTitle("进口料件报关登记表");
							dg.setImport(true);
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(346, 28, 148, 23);
			jRadioButton2.setText("出口成品情况统计表");
			jRadioButton2.setOpaque(false);
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscExpProductStat dg = new DgDzscExpProductStat();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(346, 57, 148, 23);
			jRadioButton3.setText("出口成品报关登记表");
			jRadioButton3.setOpaque(false);
			jRadioButton3
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscImpMaterialListNew dg = new DgDzscImpMaterialListNew();
							dg.setTitle("出口成品报关登记表");
							dg.setPrintTitle("出口成品报关登记表");
							dg.setImport(false);
							dg.setVisible(true);
							
						

						}
					});
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setBounds(165, 30, 148, 21);
			jRadioButton4.setText("料件执行进度[总表]");
			jRadioButton4.setOpaque(false);
			jRadioButton4
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscImpMaterialSchedule dg = new DgDzscImpMaterialSchedule();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton4;
	}

	/**
	 * This method initializes jRadioButton5
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setBounds(165, 55, 148, 21);
			jRadioButton5.setText("料件执行进度[明细]");
			jRadioButton5.setOpaque(false);
			jRadioButton5
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscImpExpScheduleDetail dg = new DgDzscImpExpScheduleDetail();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setMaterial(true);
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton5;
	}

	/**
	 * This method initializes jRadioButton6
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton6() {
		if (jRadioButton6 == null) {
			jRadioButton6 = new JRadioButton();
			jRadioButton6.setBounds(347, 30, 148, 21);
			jRadioButton6.setText("成品执行进度[总表]");
			jRadioButton6.setOpaque(false);
			jRadioButton6
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscExpProductSchedule dg = new DgDzscExpProductSchedule();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton6;
	}

	/**
	 * This method initializes jRadioButton7
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton7() {
		if (jRadioButton7 == null) {
			jRadioButton7 = new JRadioButton();
			jRadioButton7.setBounds(347, 55, 148, 21);
			jRadioButton7.setText("成品执行进度[明细]");
			jRadioButton7.setOpaque(false);
			jRadioButton7
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscImpExpScheduleDetail dg = new DgDzscImpExpScheduleDetail();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setMaterial(false);
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton7;
	}

	/**
	 * This method initializes jRadioButton8
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton8() {
		if (jRadioButton8 == null) {
			jRadioButton8 = new JRadioButton();
			jRadioButton8.setBounds(168, 22, 172, 21);
			jRadioButton8.setText("核销单登记表");
			jRadioButton8.setOpaque(false);
			jRadioButton8
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscCancelAfterVerificationList dg = new DgDzscCancelAfterVerificationList();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton8;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
			buttonGroup.add(getJRadioButton2());
			buttonGroup.add(getJRadioButton3());
			buttonGroup.add(getJRadioButton4());
			buttonGroup.add(getJRadioButton5());
			buttonGroup.add(getJRadioButton6());
			buttonGroup.add(getJRadioButton7());
			buttonGroup.add(getJRadioButton8());
			buttonGroup.add(getJRadioButton9());
			buttonGroup.add(getJRadioButton10());
			buttonGroup.add(getJRadioButton12());
			buttonGroup.add(getJRadioButton13());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jRadioButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setText("进口料件清单明细表");
			jRadioButton9.setBounds(new java.awt.Rectangle(166, 19, 153, 24));
			jRadioButton9.setOpaque(false);
			jRadioButton9
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscBillListDeclaration dg = new DgDzscBillListDeclaration();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setTitle("进口料件清单明细表");
							dg.setMaterial(true);
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton9;
	}

	/**
	 * This method initializes jRadioButton10
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton10() {
		if (jRadioButton10 == null) {
			jRadioButton10 = new JRadioButton();
			jRadioButton10.setText("出口成品清单明细表");
			jRadioButton10.setBounds(new java.awt.Rectangle(166, 48, 147, 22));
			jRadioButton10.setOpaque(false);
			jRadioButton10
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscBillListDeclaration dg = new DgDzscBillListDeclaration();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setTitle("出口成品清单明细表");
							dg.setMaterial(false);
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton10;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new java.awt.Rectangle(20, 252, 625, 83));
			jPanel.setOpaque(false);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"报关清单执行结果",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									java.awt.SystemColor.activeCaption));
			jPanel.add(getJRadioButton12(), null);
			jPanel.add(getJRadioButton13(), null);
			jPanel.add(getJRadioButton9(), null);
			jPanel.add(getJRadioButton10(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton12
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton12() {
		if (jRadioButton12 == null) {
			jRadioButton12 = new JRadioButton();
			jRadioButton12.setBounds(new java.awt.Rectangle(348, 19, 182, 24));
			jRadioButton12.setText("进口料件清单情况统计表");
			jRadioButton12.setOpaque(false);
			jRadioButton12
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscBillListImpMaterialStat dg = new DgDzscBillListImpMaterialStat();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton12;
	}

	/**
	 * This method initializes jRadioButton13
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton13() {
		if (jRadioButton13 == null) {
			jRadioButton13 = new JRadioButton();
			jRadioButton13.setBounds(new java.awt.Rectangle(348, 48, 176, 22));
			jRadioButton13.setText("出口成品清单情况统计表");
			jRadioButton13.setOpaque(false);
			jRadioButton13
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbContract.getItemCount() < 1) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this,
										"系统没有正在执行的电子手册", "提示",
										JOptionPane.OK_OPTION);
								return;
							}
							if (cbbContract.getSelectedItem() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractReport.this, "请选择电子手册",
										"提示", JOptionPane.OK_OPTION);
								return;
							}
							DgDzscBillListExpProductStat dg = new DgDzscBillListExpProductStat();
							dg.setContract((DzscEmsPorHead) cbbContract
									.getSelectedItem());
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton13;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(21, 343, 624, 81));
			jPanel1.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"特殊报关单登记表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									java.awt.SystemColor.activeCaption));
			jPanel1.add(getJRadioButton41(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton41	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton41() {
		if (jRadioButton41 == null) {
			jRadioButton41 = new JRadioButton();
			jRadioButton41.setBounds(new Rectangle(161, 32, 183, 29));
			jRadioButton41.setText("特殊报关单登记表");
			jRadioButton41.setOpaque(false);
			jRadioButton41.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSpecialCustomsList dg = new DgSpecialCustomsList();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jRadioButton41;
	}

	
} // @jve:decl-index=0:visual-constraint="38,31"
