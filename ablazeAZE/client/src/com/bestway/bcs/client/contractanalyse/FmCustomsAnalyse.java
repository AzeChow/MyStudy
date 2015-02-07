/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contractanalyse.action.ContractAnalyseAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * hj
 * 
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmCustomsAnalyse extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private ContractAction contractAction = null;
	private ContractAnalyseAction contractAnalyseAction = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JRadioButton jRadioButton2 = null;
	private JRadioButton jRadioButton3 = null;
	private JRadioButton jRadioButton4 = null;
	private JRadioButton jRadioButton5 = null;
	private JRadioButton jRadioButton8 = null;
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="331,1"
	private JPanel jPanel = null;
	private PnImageBackground jPanel1 = null;
	private JRadioButton jRadioButton9 = null;
	private JRadioButton jRadioButton10 = null;
	private JRadioButton jRadioButton11 = null;
	private JRadioButton jRadioButton6 = null;

	/**
	 * This is the default constructor
	 */
	public FmCustomsAnalyse() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		contractAnalyseAction = (ContractAnalyseAction) CommonVars
				.getApplicationContext().getBean("contractAnalyseAction");
		contractAnalyseAction.checkAuthority(new Request(CommonVars
				.getCurrUser()));
		// 初始化合同
		List contracts = contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser()));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关分析");
		this.setSize(654, 504);
		this.setContentPane(getJContentPane());
		this.getButtonGroup();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"合同执行分析",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									new java.awt.Color(0, 51, 102)));
			jPanel2.setBounds(18, 58, 603, 123);
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(getJRadioButton2(), null);
			jPanel2.add(getJRadioButton3(), null);
			jPanel2.setOpaque(false);
			jPanel2.add(getJRadioButton10(), null);
			jPanel2.add(getJRadioButton11(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"合同执行情况",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.BOLD, 12),
									new java.awt.Color(0, 51, 102)));
			jPanel3.setBounds(19, 206, 603, 197);
			jPanel3.add(getJRadioButton4(), null);
			jPanel3.add(getJRadioButton5(), null);
			jPanel3.add(getJRadioButton8(), null);
			jPanel3.setOpaque(false);
			jPanel3.add(getJRadioButton9(), null);
			jPanel3.add(getJRadioButton6(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("料件分析");
			jRadioButton.setBounds(219, 19, 156, 21);
			jRadioButton.setOpaque(false);
			jRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jRadioButton.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					DgMaterielAnalyse dg = new DgMaterielAnalyse();
					ShowFormControl.showForm(dg);
					jRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			});
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
			jRadioButton1.setText("成品分析");
			jRadioButton1.setBounds(372, 19, 156, 21);
			jRadioButton1.setOpaque(false);
			jRadioButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton1.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgProductAnalyse dg = new DgProductAnalyse();
							ShowFormControl.showForm(dg);
							jRadioButton1.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton2.setText("库存分析");
			jRadioButton2.setBounds(219, 83, 156, 21);
			jRadioButton2.setOpaque(false);
			jRadioButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton2.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgStorageAnalyse dgStorageAnalyse = new DgStorageAnalyse();
							ShowFormControl.showForm(dgStorageAnalyse);
							jRadioButton2.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton3.setText("进口包装统计");
			jRadioButton3.setBounds(372, 83, 156, 21);
			jRadioButton3.setOpaque(false);
			jRadioButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton3
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton3.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgWrapStat dg = new DgWrapStat();
							ShowFormControl.showForm(dg);
							jRadioButton3.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton4.setText("料件执行情况分析");
			jRadioButton4.setBounds(217, 21, 163, 20);
			jRadioButton4.setOpaque(false);
			jRadioButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton4
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton4.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgMaterielExecuteAnalyse dg = new DgMaterielExecuteAnalyse();
							ShowFormControl.showForm(dg);
							jRadioButton4.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton5.setText("成品执行情况分析");
			jRadioButton5.setBounds(217, 49, 163, 20);
			jRadioButton5.setOpaque(false);
			jRadioButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton5
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton5.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgProductExecuteAnalyse dg = new DgProductExecuteAnalyse();
							ShowFormControl.showForm(dg);
							jRadioButton5.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
						}
					});
		}
		return jRadioButton5;
	}

	/**
	 * This method initializes jRadioButton8
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton8() {
		if (jRadioButton8 == null) {
			jRadioButton8 = new JRadioButton();
			jRadioButton8.setText("报关单预录入库查询");
			jRadioButton8.setBounds(217, 77, 163, 20);
			jRadioButton8.setOpaque(false);
			jRadioButton8.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton8
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton8.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgPreCustomsDeclaration dg = new DgPreCustomsDeclaration();
							ShowFormControl.showForm(dg);
							jRadioButton8.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			// buttonGroup.add(getJRadioButton7());
			buttonGroup.add(getJRadioButton8());
			buttonGroup.add(getJRadioButton9());
			buttonGroup.add(getJRadioButton10());
			buttonGroup.add(getJRadioButton11());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("报关分析");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel.setBackground(java.awt.Color.white);
			jLabel1.setText("");
			jLabel1
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel2.setText("");
			jLabel2.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.WEST);
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
			jPanel1 = new PnImageBackground();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel2(), null);
			jPanel1.add(getJPanel3(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton9
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton9() {
		if (jRadioButton9 == null) {
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setBounds(new java.awt.Rectangle(217, 105, 151, 24));
			jRadioButton9.setOpaque(false);
			jRadioButton9.setText("各合同执行状况表");
			jRadioButton9.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton9
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton9.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgContractAnalyseStat dg = new DgContractAnalyseStat();
							ShowFormControl.showForm(dg);
							jRadioButton9.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton10.setText("进口报关单列表");
			jRadioButton10.setBounds(new java.awt.Rectangle(219, 49, 131, 23));
			jRadioButton10.setOpaque(false);
			jRadioButton10
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgImpCustomsDeclarationList dg = new DgImpCustomsDeclarationList();
							dg.setImpExpFlag(ImpExpFlag.IMPORT);
							dg.setTitle("进口报关单列表");
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton10;
	}

	/**
	 * This method initializes jRadioButton11
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton11() {
		if (jRadioButton11 == null) {
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setText("出口报关单列表");
			jRadioButton11.setBounds(new java.awt.Rectangle(372, 49, 131, 21));
			jRadioButton11.setOpaque(false);
			jRadioButton11
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgExpCustomsDeclarationList dg = new DgExpCustomsDeclarationList();
							dg.setImpExpFlag(ImpExpFlag.EXPORT);
							dg.setTitle("出口报关单列表");
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton11;
	}

	/**
	 * This method initializes jRadioButton6
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton6() {
		if (jRadioButton6 == null) {
			jRadioButton6 = new JRadioButton();
			jRadioButton6.setBounds(new Rectangle(217, 133, 178, 24));
			jRadioButton6.setText("料件耗用明细（编码级）");
			jRadioButton6.setOpaque(false);
			jRadioButton6
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// jRadioButton6.setCursor(new Cursor(
							// Cursor.WAIT_CURSOR));
							DgFinishedProductExport dg = new DgFinishedProductExport();
							ShowFormControl.showForm(dg);
							// jRadioButton9.setCursor(new Cursor(
							// Cursor.HAND_CURSOR));
						}
					});
		}
		return jRadioButton6;

	}
} // @jve:decl-index=0:visual-constraint="96,11"
