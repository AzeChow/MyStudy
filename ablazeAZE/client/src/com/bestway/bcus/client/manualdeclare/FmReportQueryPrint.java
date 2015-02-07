/**
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.bestway.bcs.client.contractstat.DgCustomsBrokersList;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.bcus.client.enc.DgCustomsMonthStatInfo;
import com.bestway.bcus.client.enc.DgExgUsedImgAmountInfo;
import com.bestway.bcus.client.enc.report.DgEmsExpExg;
import com.bestway.bcus.client.enc.report.DgEmsImpExpBalance;
import com.bestway.bcus.client.enc.report.DgEmsWholeImpExpMoneyTotal;
import com.bestway.bcus.client.enc.report.DgExchangeExp;
import com.bestway.bcus.client.enc.report.DgExchangeImp;
import com.bestway.bcus.client.enc.report.DgExgWearDetail;
import com.bestway.bcus.client.enc.report.DgExpCustomsBill;
import com.bestway.bcus.client.enc.report.DgImgBalanceTotal;
import com.bestway.bcus.client.enc.report.DgImgExgRecord;
import com.bestway.bcus.client.enc.report.DgImgUnitWeightDiff;
import com.bestway.bcus.client.enc.report.DgImgWearDetail;
import com.bestway.bcus.client.enc.report.DgImpCustomsBill;
import com.bestway.bcus.manualdeclarereport.action.ManualDeclareReportAction;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JButton;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmReportQueryPrint extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private ContractAction contractAction = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton3 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private ButtonGroup buttonGroup1 = null;

	private JRadioButton jRadioButton4 = null;

	private JRadioButton jRadioButton5 = null;

	private JRadioButton jRadioButton6 = null;

	private JRadioButton jRadioButton7 = null;

	private JRadioButton jRadioButton8 = null;

	private JRadioButton jRadioButton9 = null;

	private JRadioButton jRadioButton10 = null;

	private JPanel jPanel = null;

	private JRadioButton jRadioButton11 = null;

	private JRadioButton jRadioButton12 = null;

	private JRadioButton jRadioButton13 = null;

	private JRadioButton jRadioButton14 = null;

	private JRadioButton jRadioButton15 = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private PnImageBackground jPanel2 = null;

	private JLabel jLabel1 = null;

	private ManualDeclareReportAction manualDeclareReportAction = null;

	private JRadioButton jRadioButton16 = null;

	private JRadioButton jRadioButton17 = null;

	private JRadioButton jRadioButton18 = null;

	private JRadioButton jRadioButton19 = null;

	private JRadioButton jRadioButton20 = null;

	private JRadioButton jRadioButton21 = null;

	private JRadioButton rbtRequestTOApplyTOCustomsReport = null;

	private JRadioButton specialCustomsDeclarationList = null;

	private JRadioButton rbtApplyReportTOCustomsReport = null;

	private JPanel jPanel3 = null;

	private JButton jButton = null;

	private JRadioButton customsbrokerList = null;

	/**
	 * This is the default constructor
	 */
	public FmReportQueryPrint() {
		super();
		getButtonGroup();
		getButtonGroup1();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		manualDeclareReportAction = (ManualDeclareReportAction) CommonVars
				.getApplicationContext().getBean("manualDeclareReportAction");
		manualDeclareReportAction.checkReportQueryPrintAuthority(new Request(CommonVars
				.getCurrUser()));
		/*
		 * // 初始化合同 List contracts=contractAction.findContractByProcessExe(new
		 * Request( CommonVars.getCurrUser()));
		 */
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("统计报表打印");
		this.setSize(835, 551);
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
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jPanel4.add(getJPanel2(), java.awt.BorderLayout.CENTER);
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
			jPanel5.setOpaque(false);
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(
							java.awt.SystemColor.inactiveCaption, 1), "帐册执行情况",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.SystemColor.activeCaption));
			jPanel5.setBounds(new Rectangle(24, 31, 717, 200));
			jPanel5.add(getJRadioButton(), null);
			jPanel5.add(getJRadioButton1(), null);
			jPanel5.add(getJRadioButton2(), null);
			jPanel5.add(getJRadioButton3(), null);
			jPanel5.add(getJRadioButton15(), null);
			jPanel5.add(getJRadioButton4(), null);
			jPanel5.add(getJRadioButton5(), null);
			jPanel5.add(getJRadioButton12(), null);
			jPanel5.add(getJRadioButton11(), null);
			jPanel5.add(getJRadioButton14(), null);
			jPanel5.add(getJRadioButton13(), null);
			jPanel5.add(getJRadioButton16(), null);
			jPanel5.add(getJRadioButton19(), null);
			jPanel5.add(getJRadioButton20(), null);
			jPanel5.add(getSpecialCustomsDeclarationList(), null);
			jPanel5.add(getcustomsbrokerList(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(45, 27, 163, 23);
			jRadioButton.setText("进口料件情况统计表");
			jRadioButton.setOpaque(false);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					manualDeclareReportAction.report1(new Request(CommonVars
							.getCurrUser()));
					DgImpUseRecord dg = new DgImpUseRecord();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jRadioButton;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(45, 59, 151, 23);
			jRadioButton1.setText("进口料件报关登记表");
			jRadioButton1.setOpaque(false);
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report3(new Request(
									CommonVars.getCurrUser()));
							DgImpCustomsRecord dg = new DgImpCustomsRecord();
							ShowFormControl.showForm(dg);
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
			jRadioButton2.setBounds(260, 26, 154, 23);
			jRadioButton2.setText("出口成品情况统计表");
			jRadioButton2.setOpaque(false);
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report2(new Request(
									CommonVars.getCurrUser()));
							DgExpUseRecord dg = new DgExpUseRecord();
							ShowFormControl.showForm(dg);

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
			jRadioButton3.setBounds(260, 59, 154, 23);
			jRadioButton3.setText("出口成品报关登记表");
			jRadioButton3.setOpaque(false);
			jRadioButton3
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report4(new Request(
									CommonVars.getCurrUser()));
							DgExpCustomsRecord dg = new DgExpCustomsRecord();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return jRadioButton3;
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
			buttonGroup.add(getJRadioButton11());
			buttonGroup.add(getJRadioButton12());
			buttonGroup.add(getJRadioButton15());
			buttonGroup.add(getJRadioButton13());
			buttonGroup.add(getJRadioButton14());
			buttonGroup.add(getJRadioButton16());
			buttonGroup.add(getJRadioButton19());
			buttonGroup.add(getJRadioButton20());
			buttonGroup.add(getSpecialCustomsDeclarationList());
		}
		return buttonGroup;
	}

	private ButtonGroup getButtonGroup1() {
		if (buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton6());
			buttonGroup1.add(getJRadioButton8());
			buttonGroup1.add(getJRadioButton7());
			buttonGroup1.add(getJRadioButton9());
			buttonGroup1.add(getJRadioButton10());
			buttonGroup1.add(getJRadioButton17());
			buttonGroup1.add(getJRadioButton18());
			buttonGroup1.add(getRbtRequestTOApplyTOCustomsReport());
			buttonGroup1.add(getJRadioButton21());
			buttonGroup1.add(getRbtApplyReportTOCustomsReport());
		}
		return buttonGroup1;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setText("料件流水帐");
			jRadioButton4.setBounds(new Rectangle(480, 26, 90, 26));
			jRadioButton4.setOpaque(false);
			jRadioButton4
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report5(new Request(
									CommonVars.getCurrUser()));
							DgImgExgRecord dg = new DgImgExgRecord();
							dg.setImg(true);
							ShowFormControl.showForm(dg);
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
			jRadioButton5.setText("成品流水帐");
			jRadioButton5.setBounds(new Rectangle(574, 26, 90, 26));
			jRadioButton5.setOpaque(false);
			jRadioButton5
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report6(new Request(
									CommonVars.getCurrUser()));
							DgImgExgRecord dg = new DgImgExgRecord();
							dg.setImg(false);
							ShowFormControl.showForm(dg);
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
			jRadioButton6.setOpaque(false);
			jRadioButton6.setText("料件进出平衡状况汇总表");
			jRadioButton6.setBounds(45, 29, 178, 19);
			jRadioButton6
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report12(new Request(
									CommonVars.getCurrUser()));
							DgImgBalanceTotal dg = new DgImgBalanceTotal();
							ShowFormControl.showForm(dg);
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
			jRadioButton7.setOpaque(false);
			jRadioButton7.setText("帐册出口成品状况表");
			jRadioButton7.setBounds(45, 55, 165, 22);
			jRadioButton7
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report14(new Request(
									CommonVars.getCurrUser()));
							DgEmsExpExg dg = new DgEmsExpExg();
							ShowFormControl.showForm(dg);
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
			jRadioButton8.setOpaque(false);
			jRadioButton8.setText("进口料件单重差异稽核表");
			jRadioButton8.setBounds(260, 28, 185, 17);
			jRadioButton8
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report13(new Request(
									CommonVars.getCurrUser()));
							DgImgUnitWeightDiff dg = new DgImgUnitWeightDiff();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton8;
	}

	/**
	 * This method initializes jRadioButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setText("帐册进出口量平衡状况表");
			jRadioButton9.setOpaque(false);
			jRadioButton9.setBounds(260, 54, 173, 21);
			jRadioButton9
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report15(new Request(
									CommonVars.getCurrUser()));
							DgEmsImpExpBalance dg = new DgEmsImpExpBalance();
							ShowFormControl.showForm(dg);
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
			jRadioButton10.setOpaque(false);
			jRadioButton10.setText("帐册总体进出金额状况统计表");
			jRadioButton10.setBounds(45, 85, 202, 21);
			jRadioButton10
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report16(new Request(
									CommonVars.getCurrUser()));
							DgEmsWholeImpExpMoneyTotal dg = new DgEmsWholeImpExpMoneyTotal();
							ShowFormControl.showForm(dg);
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
			jPanel.setOpaque(false);
			jPanel.setLayout(null);
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(
							java.awt.SystemColor.inactiveCaption, 1), "帐册统计分析",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.blue));
			jPanel.setBounds(new Rectangle(25, 240, 717, 151));
			jPanel.add(getJRadioButton6(), null);
			jPanel.add(getJRadioButton8(), null);
			jPanel.add(getJRadioButton7(), null);
			jPanel.add(getJRadioButton9(), null);
			jPanel.add(getJRadioButton10(), null);
			jPanel.add(getJRadioButton17(), null);
			jPanel.add(getJRadioButton18(), null);

			jPanel.add(getJRadioButton21(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jRadioButton11
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton11() {
		if (jRadioButton11 == null) {
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setText("出口报关清单");
			jRadioButton11.setBounds(new java.awt.Rectangle(260, 91, 111, 21));
			jRadioButton11.setOpaque(false);
			jRadioButton11
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report8(new Request(
									CommonVars.getCurrUser()));
							DgExpCustomsBill dg = new DgExpCustomsBill();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton11;
	}

	/**
	 * This method initializes jRadioButton12
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton12() {
		if (jRadioButton12 == null) {
			jRadioButton12 = new JRadioButton();
			jRadioButton12.setText("进口报关清单");
			jRadioButton12.setBounds(new java.awt.Rectangle(45, 91, 104, 21));
			jRadioButton12.setOpaque(false);
			jRadioButton12
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report7(new Request(
									CommonVars.getCurrUser()));
							DgImpCustomsBill dg = new DgImpCustomsBill();
							ShowFormControl.showForm(dg);
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
			jRadioButton13.setText("应退换进口料件");
			jRadioButton13.setBounds(new java.awt.Rectangle(51, 114, 132, 21));
			jRadioButton13.setOpaque(false);
			jRadioButton13.setVisible(false);
			jRadioButton13
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							manualDeclareReportAction.report10(new Request(
//									CommonVars.getCurrUser()));
							DgExchangeImp dg = new DgExchangeImp();
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton13;
	}

	/**
	 * This method initializes jRadioButton14
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton14() {
		if (jRadioButton14 == null) {
			jRadioButton14 = new JRadioButton();
			jRadioButton14.setText("应退换出口成品");
			jRadioButton14.setBounds(new java.awt.Rectangle(277, 113, 126, 21));
			jRadioButton14.setOpaque(false);
			jRadioButton14.setVisible(false);
			jRadioButton14
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							manualDeclareReportAction.report11(new Request(
//									CommonVars.getCurrUser()));
							DgExchangeExp dg = new DgExchangeExp();
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton14;
	}

	/**
	 * This method initializes jRadioButton15
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton15() {
		if (jRadioButton15 == null) {
			jRadioButton15 = new JRadioButton();
			jRadioButton15.setOpaque(false);
			jRadioButton15.setBounds(480, 60, 151, 21);
			jRadioButton15.setText("料件耗用明细(帐册级)");
			jRadioButton15
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report9(new Request(
									CommonVars.getCurrUser()));
							DgExgWearDetail dg = new DgExgWearDetail();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return jRadioButton15;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/hint.gif")));
			jLabel = new JLabel();
			jLabel.setText(" 帐册统计报表");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 24));
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new PnImageBackground();
			jPanel2.setLayout(null);
			jPanel2.add(getJPanel5(), null);
			jPanel2.add(getJPanel(), null);
			jPanel2.add(getJPanel3(), null);
			jPanel2.add(getJButton(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jRadioButton16
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton16() {
		if (jRadioButton16 == null) {
			jRadioButton16 = new JRadioButton();
			jRadioButton16.setBounds(new Rectangle(480, 92, 151, 21));
			jRadioButton16.setText("料件耗用明细(料号级)");
			jRadioButton16.setOpaque(false);
			jRadioButton16
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report9_1(new Request(
									CommonVars.getCurrUser()));
							DgImgWearDetail dg = new DgImgWearDetail();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton16;
	}

	/**
	 * This method initializes jRadioButton17
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton17() {
		if (jRadioButton17 == null) {
			jRadioButton17 = new JRadioButton();
			jRadioButton17.setBounds(new Rectangle(260, 83, 189, 21));
			jRadioButton17.setText("出口成品耗用料件统计表");
			jRadioButton17.setOpaque(false);
			jRadioButton17
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report19(new Request(
									CommonVars.getCurrUser()));
							DgExgUsedImgAmountInfo dg = new DgExgUsedImgAmountInfo();
							ShowFormControl.showForm(dg);
						}
					});
			
		}
		return jRadioButton17;
	}

	/**
	 * This method initializes jRadioButton171
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton18() {
		if (jRadioButton18 == null) {
			jRadioButton18 = new JRadioButton();
			jRadioButton18.setBounds(new Rectangle(481, 54, 191, 24));
			jRadioButton18.setText("进口料件耗用月报表");
			jRadioButton18.setOpaque(false);
			jRadioButton18
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report11(new Request(
									CommonVars.getCurrUser()));
							DgCustomsMonthStatInfo dg = new DgCustomsMonthStatInfo();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton18;
	}

	/**
	 * This method initializes jRadioButton19
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton19() {
		if (jRadioButton19 == null) {
			jRadioButton19 = new JRadioButton();
			jRadioButton19.setBounds(new Rectangle(45, 123, 215, 21));
			jRadioButton19.setText("进口料件清单情况统计表(料号级)");
			jRadioButton19.setOpaque(false);
			jRadioButton19
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.imreport17(new Request(
									CommonVars.getCurrUser()));
							DgEmsBillListImpMaterialStat dg = new DgEmsBillListImpMaterialStat();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return jRadioButton19;
	}

	/**
	 * This method initializes jRadioButton20
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton20() {
		if (jRadioButton20 == null) {
			jRadioButton20 = new JRadioButton();
			jRadioButton20.setBounds(new Rectangle(260, 123, 215, 21));
			jRadioButton20.setText("出口成品清单情况统计表(料号级)");
			jRadioButton20.setOpaque(false);
			jRadioButton20
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.exreport18(new Request(
									CommonVars.getCurrUser()));
							DgEmsBillListExpProductStat dg = new DgEmsBillListExpProductStat();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton20;
	}

	/**
	 * This method initializes jRadioButton171
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbtRequestTOApplyTOCustomsReport() {
		if (rbtRequestTOApplyTOCustomsReport == null) {
			rbtRequestTOApplyTOCustomsReport = new JRadioButton();
			rbtRequestTOApplyTOCustomsReport.setText("大小清单查询");
			rbtRequestTOApplyTOCustomsReport.setBounds(new Rectangle(64, 33, 103, 26));
			rbtRequestTOApplyTOCustomsReport.setOpaque(false);
			rbtRequestTOApplyTOCustomsReport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction
									.requestTOApplyTOCustomsReport(new Request(
											CommonVars.getCurrUser()));
							DgRequestTOApplyTOCustomsReport dg = new DgRequestTOApplyTOCustomsReport();
							ShowFormControl.showForm(dg);
						}
					});

		}
		return rbtRequestTOApplyTOCustomsReport;
	}

	/**
	 * This method initializes jRadioButton21
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton21() {
		if (jRadioButton21 == null) {
			jRadioButton21 = new JRadioButton();
			jRadioButton21.setBounds(new Rectangle(480, 28, 154, 17));
			jRadioButton21.setText("进/出包装统计");
			jRadioButton21.setOpaque(false);
			jRadioButton21
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report20(new Request(
									CommonVars.getCurrUser()));
							DgBcusWrapStat dg = new DgBcusWrapStat();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton21;
	}

	/**
	 * This method initializes specialCustomsDeclarationList
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getSpecialCustomsDeclarationList() {
		if (specialCustomsDeclarationList == null) {
			specialCustomsDeclarationList = new JRadioButton();
			specialCustomsDeclarationList.setBounds(new Rectangle(480, 125, 168, 23));
			specialCustomsDeclarationList.setText("特殊报关单商品列表");
			specialCustomsDeclarationList.setOpaque(false);
			specialCustomsDeclarationList
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.report10(new Request(
									CommonVars.getCurrUser()));
							DgspecialCustomsRecord dg = new DgspecialCustomsRecord();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return specialCustomsDeclarationList;
	}

	/**
	 * This method initializes rbtApplyReportTOCustomsReport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbtApplyReportTOCustomsReport() {
		if (rbtApplyReportTOCustomsReport == null) {
			rbtApplyReportTOCustomsReport = new JRadioButton();
			rbtApplyReportTOCustomsReport.setText("清单与报关单对应表");
			rbtApplyReportTOCustomsReport.setBounds(new Rectangle(273, 33, 142, 26));
			rbtApplyReportTOCustomsReport.setOpaque(false);
			
			rbtApplyReportTOCustomsReport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction
									.ApplyReportTOCustomsReport(new Request(
											CommonVars.getCurrUser()));
							DgApplyReportTOCustomsReport dg = new DgApplyReportTOCustomsReport();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return rbtApplyReportTOCustomsReport;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setOpaque(false);
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(24, 397, 717, 76));
			jPanel3.setBorder(BorderFactory.createTitledBorder(null, "\u7533\u8bf7\u5355-\u6e05\u5355-\u62a5\u5173\u5355\u5206\u6790", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(0, 51, 255)));
			jPanel3.add(getRbtRequestTOApplyTOCustomsReport(), null);
			jPanel3.add(getRbtApplyReportTOCustomsReport(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(25, 5, 166, 21));
			jButton.setText("显示穿透式报表流程");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmReportFlow flow = new FmReportFlow();
					ShowFormControl.showForm(flow);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes specialCustomsDeclarationList1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getcustomsbrokerList() {
		if (customsbrokerList == null) {
			customsbrokerList = new JRadioButton();
			customsbrokerList.setBounds(new Rectangle(45, 151, 142, 26));
			customsbrokerList.setText("报关行申报登记表");
			customsbrokerList.setOpaque(false);
			customsbrokerList.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsBrokersList dg = new DgCustomsBrokersList();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return customsbrokerList;
	}
}  
