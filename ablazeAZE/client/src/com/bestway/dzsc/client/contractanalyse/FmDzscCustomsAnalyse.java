/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmDzscCustomsAnalyse extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private DzscAction contractAction = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton3 = null;

	private JRadioButton jRadioButton4 = null;

	private JRadioButton jRadioButton5 = null;

	private JRadioButton jRadioButton6 = null;

	private JRadioButton jRadioButton7 = null;

	private JRadioButton jRadioButton8 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="331,1"

	private JPanel jPanel = null;

	private PnImageBackground jPanel1 = null;

	private JRadioButton jRadioButton9 = null;

	private JRadioButton jRadioButton10 = null;



	/**
	 * This is the default constructor
	 */
	public FmDzscCustomsAnalyse() {
		super();
		initialize();
		contractAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		contractAction.checkDzscCustomsAnalyse(new Request(CommonVars
				.getCurrUser()));
//		// 初始化合同
//		List contracts = contractAction.findDzscEmsPorHeadExcu(new Request(
//				CommonVars.getCurrUser(), true));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("电子手册--报关分析");
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
	public JPanel getJPanel2() {
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
			jPanel2.setBounds(18, 20, 603, 116);
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(getJRadioButton2(), null);
			jPanel2.add(getJRadioButton3(), null);
			jPanel2.setOpaque(false);
			jPanel2.add(getJRadioButton10(), null);
			
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
			jPanel3.setBounds(18, 150, 603, 137);
			jPanel3.add(getJRadioButton4(), null);
			jPanel3.add(getJRadioButton5(), null);
			jPanel3.add(getJRadioButton6(), null);
			jPanel3.add(getJRadioButton7(), null);
			jPanel3.add(getJRadioButton8(), null);
			jPanel3.setOpaque(false);
			jPanel3.add(getJRadioButton9(), null);
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
			jRadioButton.setBounds(182, 27, 156, 21);
			jRadioButton.setOpaque(false);
			jRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jRadioButton.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					DgDzscMaterielAnalyse dg = new DgDzscMaterielAnalyse();
					dg.setVisible(true);
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
			jRadioButton1.setBounds(182, 53, 156, 21);
			jRadioButton1.setOpaque(false);
			jRadioButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton1.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscProductAnalyse dg = new DgDzscProductAnalyse();
							dg.setVisible(true);
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
			jRadioButton2.setBounds(351, 27, 156, 21);
			jRadioButton2.setOpaque(false);
			jRadioButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton2.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscStorageAnalyse dgStorageAnalyse = new DgDzscStorageAnalyse();
							dgStorageAnalyse.setVisible(true);
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
			jRadioButton3.setBounds(351, 53, 156, 21);
			jRadioButton3.setOpaque(false);
			jRadioButton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton3
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton3.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscWrapStat dg = new DgDzscWrapStat();
							dg.setVisible(true);
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
			jRadioButton4.setBounds(181, 28, 163, 20);
			jRadioButton4.setOpaque(false);
			jRadioButton4.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton4
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton4.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscMaterielExecuteAnalyse dg = new DgDzscMaterielExecuteAnalyse();
							dg.setVisible(true);
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
			jRadioButton5.setBounds(349, 28, 163, 20);
			jRadioButton5.setOpaque(false);
			jRadioButton5.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton5
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton5.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscProductExecuteAnalyse dg = new DgDzscProductExecuteAnalyse();
							dg.setVisible(true);
							jRadioButton5.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton6.setText("进口料件报关情况表");
			jRadioButton6.setBounds(181, 63, 163, 20);
			jRadioButton6.setOpaque(false);
			jRadioButton6.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton6
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton6.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscImpMaterialStatus dg = new DgDzscImpMaterialStatus();
							dg.setVisible(true);
							jRadioButton6.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton7.setText("出口成品报关情况表");
			jRadioButton7.setBounds(349, 63, 163, 20);
			jRadioButton7.setOpaque(false);
			jRadioButton7.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton7
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton7.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscExpProductStatus dg = new DgDzscExpProductStatus();
							dg.setVisible(true);
							jRadioButton7.setCursor(new Cursor(
									Cursor.HAND_CURSOR));
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
			jRadioButton8.setText("报关单预录入库查询");
			jRadioButton8.setBounds(181, 97, 163, 20);
			jRadioButton8.setOpaque(false);
			jRadioButton8.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton8
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jRadioButton8.setCursor(new Cursor(
									Cursor.WAIT_CURSOR));
							DgDzscPreCustomsDeclaration dg = new DgDzscPreCustomsDeclaration();
							dg.setVisible(true);
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
	public ButtonGroup getButtonGroup() {
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
			jLabel.setText("电子手册--报关分析");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
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
			jRadioButton9.setText("料件耗用明细(料号级)");
			jRadioButton9.setOpaque(false);
			jRadioButton9.setBounds(new Rectangle(349, 97, 171, 21));
			jRadioButton9
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jRadioButton9.setCursor(new Cursor(
							Cursor.WAIT_CURSOR));
					DgDzscFinishedProductExport dg = new DgDzscFinishedProductExport();
					dg.setVisible(true);
					jRadioButton9.setCursor(new Cursor(
							Cursor.HAND_CURSOR));
				}
			});
		}
		return jRadioButton9;
	}

	/**
	 * This method initializes jRadioButton31	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton10() {
		if (jRadioButton10 == null) {
			jRadioButton10 = new JRadioButton();
			jRadioButton10.setBounds(new Rectangle(182, 83, 157, 14));
			jRadioButton10.setOpaque(false);
			jRadioButton10.setText("进口料件备案统计表");
			jRadioButton10.setCursor(new Cursor(Cursor.HAND_CURSOR));
			jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscImpPORImgStatInfo dg = new DgDzscImpPORImgStatInfo();
					dg.setVisible(true);
				}
			});
		}
		return jRadioButton10;
	}


}
