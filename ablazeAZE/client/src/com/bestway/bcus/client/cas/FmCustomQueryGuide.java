/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.authority.CasCustomReportAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 海关帐统计报表主界面 贺巍于2009年7月7日添加注释
 */
public class FmCustomQueryGuide extends JInternalFrameBase {
	private javax.swing.JPanel jContentPane = null;

	private JPanel pnTitle = null;

	private JPanel pnMain = null;

	private JRadioButton rbMaterielInOut = null;

	private JRadioButton rbProductInOut = null;

	private JRadioButton rbFixingInOut = null;

	private JRadioButton rbLeftoverMaterialInOut = null;

	private JRadioButton rbBadProductInOut = null;

	private JRadioButton rbTransferFactoryInOut = null;

	private JRadioButton rbOutSourceInOut = null;

	private JPanel pnSubOne = null;

	private JPanel pnSubTwo = null;

	private JRadioButton rbCustomMateriel = null;

	private JRadioButton rbCustomProduct = null;

	private JRadioButton rbCustomFixing = null;

	// private JRadioButton rbCustomSource = null;
	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:

	private JPanel PnBottom = null;

	private JButton btnExit = null;

	private CasCustomReportAction casCustomReportAction = null;

	private JRadioButton rbInlandBuyInOut = null;

	private JRadioButton rbOutSourceInOutBack = null;

	private JRadioButton rbTrusteeInOut = null;

	private JRadioButton rbTrusteeInOutBack = null;

	/**
	 * 构造函数 This is the default constructor
	 */
	public FmCustomQueryGuide() {
		super();
		casCustomReportAction = (CasCustomReportAction) CommonVars
				.getApplicationContext().getBean("casCustomReportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * 初始化方法 This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("海关帐统计报表");
		this.setSize(710, 517);
		this.setContentPane(getJContentPane());
		this.setAllRadioButtonProperty(jContentPane);
	}

	/**
	 * 设置所有的RadioButton的属性和事件
	 * 
	 * @param component
	 */
	private void setAllRadioButtonProperty(Component component) {
		if (!(component instanceof Container)) {
			return;
		}
		Container container = (Container) component;
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component temp = container.getComponent(i);
			if (temp instanceof JRadioButton) {
				UUID uuid = UUID.randomUUID();
				final String flag = uuid.toString();
				JRadioButton radioButton = (JRadioButton) temp;
				radioButton.setOpaque(false);
				radioButton.setActionCommand(flag);
				radioButton.addActionListener(new RadioActionListner());
				group.add(radioButton);
			} else {
				setAllRadioButtonProperty(temp);
			}
		}
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
			jContentPane.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 18));
			jContentPane.add(getPnTitle(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getPnMain(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes pnTitle
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnTitle() {
		if (pnTitle == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			pnTitle = new JPanel();
			pnTitle.setLayout(new BorderLayout());
			jLabel.setText("海关帐统计报表");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			pnTitle.setBackground(java.awt.Color.white);
			pnTitle
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pnTitle
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							18));
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel2.setText("");
			jLabel2
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			pnTitle.add(jLabel, java.awt.BorderLayout.CENTER);
			pnTitle.add(jLabel1, java.awt.BorderLayout.EAST);
			pnTitle.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return pnTitle;
	}

	/**
	 * 
	 * This method initializes pnMain
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(getPnSubOne(), null);
			pnMain.add(getPnSubTwo(), null);
			pnMain.add(getPnBottom(), null);
			pnMain.add(getBtnExit(), null);
		}
		return pnMain;
	}

	/**
	 * 
	 * This method initializes jRadioButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton() {
		if (rbMaterielInOut == null) {
			rbMaterielInOut = new JRadioButton();
			rbMaterielInOut.setText("原料进出仓帐");
			rbMaterielInOut.setBounds(58, 59, 115, 26);
		}
		return rbMaterielInOut;
	}

	/**
	 * 
	 * This method initializes jRadioButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton1() {
		if (rbProductInOut == null) {
			rbProductInOut = new JRadioButton();
			rbProductInOut.setText("成品进出仓帐");
			rbProductInOut.setBounds(58, 32, 136, 21);
		}
		return rbProductInOut;
	}

	/**
	 * 
	 * This method initializes jRadioButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton2() {
		if (rbFixingInOut == null) {
			rbFixingInOut = new JRadioButton();
			rbFixingInOut.setText("设备进出仓帐");
			rbFixingInOut.setBounds(58, 88, 168, 21);
		}
		return rbFixingInOut;
	}

	/**
	 * 
	 * This method initializes jRadioButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton3() {
		if (rbLeftoverMaterialInOut == null) {
			rbLeftoverMaterialInOut = new JRadioButton();
			rbLeftoverMaterialInOut.setText("边角料进出仓帐");
			rbLeftoverMaterialInOut.setBounds(58, 119, 192, 21);
		}
		return rbLeftoverMaterialInOut;
	}

	/**
	 * 
	 * This method initializes jRadioButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton4() {
		if (rbBadProductInOut == null) {
			rbBadProductInOut = new JRadioButton();
			rbBadProductInOut.setText("残次品进出仓帐");
			rbBadProductInOut.setBounds(227, 29, 147, 26);
		}
		return rbBadProductInOut;
	}

	/**
	 * 
	 * This method initializes jRadioButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton5() {
		if (rbTransferFactoryInOut == null) {
			rbTransferFactoryInOut = new JRadioButton();
			rbTransferFactoryInOut.setText("结转明细帐");
			rbTransferFactoryInOut.setBounds(227, 61, 147, 26);
		}
		return rbTransferFactoryInOut;
	}

	/**
	 * 
	 * This method initializes jRadioButton6
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton6() {
		if (rbOutSourceInOut == null) {
			rbOutSourceInOut = new JRadioButton();
			rbOutSourceInOut.setText("委外加工外发进出仓帐");
			rbOutSourceInOut.setBounds(429, 29, 174, 21);
		}
		return rbOutSourceInOut;
	}

	/**
	 * 
	 * This method initializes pnSubOne
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnSubOne() {
		if (pnSubOne == null) {
			pnSubOne = new JPanel();
			pnSubOne.setLayout(null);
			pnSubOne.setBounds(32, 23, 615, 167);
			pnSubOne
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"海关帐统计报表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									java.awt.Color.black));
			pnSubOne.add(getJRadioButton(), null);
			pnSubOne.add(getJRadioButton22(), null);
			pnSubOne.add(getRbInlandBuy(), null);
			pnSubOne.add(getJRadioButton1(), null);
			pnSubOne.add(getJRadioButton12(), null);
			pnSubOne.add(getJRadioButton2(), null);
			pnSubOne.add(getJRadioButton7(), null);
			pnSubOne.add(getJRadioButton3(), null);
			pnSubOne.add(getJRadioButton4(), null);
			pnSubOne.add(getJRadioButton5(), null);
			pnSubOne.add(getJRadioButton6(), null);
		}
		return pnSubOne;
	}

	/**
	 * 
	 * This method initializes pnSubTwo
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnSubTwo() {
		if (pnSubTwo == null) {
			pnSubTwo = new JPanel();
			pnSubTwo.setLayout(null);
			pnSubTwo.setBounds(28, 202, 615, 154);
			pnSubTwo
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"海关帐年审报表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			pnSubTwo.add(getJRadioButton8(), null);
			pnSubTwo.add(getJRadioButton9(), null);
			pnSubTwo.add(getJRadioButton10(), null);
			// jPanel3.add(getJRadioButton11(), null);
		}
		return pnSubTwo;
	}

	/**
	 * 
	 * This method initializes jRadioButton8
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton8() {
		if (rbCustomMateriel == null) {
			rbCustomMateriel = new JRadioButton();
			rbCustomMateriel.setBounds(59, 38, 230, 21);
			rbCustomMateriel.setText("加工贸易原材料来源与使用情况表");
		}
		return rbCustomMateriel;
	}

	/**
	 * 
	 * This method initializes jRadioButton9
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton9() {
		if (rbCustomProduct == null) {
			rbCustomProduct = new JRadioButton();
			rbCustomProduct.setBounds(59, 62, 199, 25);
			rbCustomProduct.setText("加工贸易产品流向情况表");
		}
		return rbCustomProduct;
	}

	/**
	 * 
	 * This method initializes jRadioButton10
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getJRadioButton10() {
		if (rbCustomFixing == null) {
			rbCustomFixing = new JRadioButton();
			rbCustomFixing.setBounds(59, 89, 244, 21);
			rbCustomFixing.setText("加工贸易生产设备使用情况表");
		}
		return rbCustomFixing;
	}

	/**
	 * 
	 * This method initializes jRadioButton11
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	// private JRadioButton getJRadioButton11() {
	// if (rbCustomSource == null) {
	// rbCustomSource = new JRadioButton();
	// rbCustomSource.setBounds(58, 91, 213, 21);
	// rbCustomSource.setText("料件，成品，设备库存情况统计表");
	// }
	// return rbCustomSource;
	// }

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		PnBottom.setBounds(3, this.pnMain.getHeight() - 50, this.pnMain
				.getWidth() - 6, 3);
		// // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
		// this.jPanel1.getWidth() - 28 -28, 3);
		btnExit.setBounds(this.pnMain.getWidth() - 92,
				this.pnMain.getHeight() - 40, 68, 26);
		//
		pnSubOne.setBounds(28, 22, this.pnMain.getWidth() - 28 - 28, 167);
		pnSubTwo.setBounds(28, 202, this.pnMain.getWidth() - 28 - 28, 154);
	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBottom() {
		if (PnBottom == null) {
			PnBottom = new JPanel();
			PnBottom.setBounds(24, 384, 667, 3);
			PnBottom
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return PnBottom;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(560, 394, 68, 26);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 内部类用来扩展组件功能
	 * 
	 * @author ？
	 * 
	 */
	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JComponent component = (JComponent) e.getSource();
			component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {

				if (rbMaterielInOut.isSelected()) { // 原材料进出仓帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityMaterielInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbMaterielInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgImpExpQuery(MaterielType.MATERIEL);
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbProductInOut.isSelected()) {// 成品进出口仓帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityProductInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbProductInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgImpExpQuery(
								MaterielType.FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbFixingInOut.isSelected()) {// 设备进出口仓帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityFixingInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbFixingInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgImpExpQuery(MaterielType.MACHINE);
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbLeftoverMaterialInOut.isSelected()) {// 边角料进出口仓帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityLeftoverMaterialInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbLeftoverMaterialInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgImpExpQuery(MaterielType.REMAIN_MATERIEL);
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbBadProductInOut.isSelected()) {// 残次品进出口仓帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityBadProductInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbBadProductInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgImpExpQuery(MaterielType.BAD_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbTransferFactoryInOut.isSelected()) {// 结转明细帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityTransferFactoryInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbTransferFactoryInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgCarryForwardQuery();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbOutSourceInOut.isSelected()) {// 委托发外加工帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityOutSourceInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbOutSourceInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgConsignQuery();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbOutSourceInOutBack.isSelected()) {// 委托发外加工帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityOutSourceInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbOutSourceInOutBack.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgConsignQueryBack();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbTrusteeInOut.isSelected()) {// 委托发外加工帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityOutSourceInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbTrusteeInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgTrusteeInOut();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbTrusteeInOutBack.isSelected()) {// 委托发外加工帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityOutSourceInOutByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbTrusteeInOutBack.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgTrusteeInOutBack();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbInlandBuyInOut.isSelected()) {// 国内购买进出仓帐
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityCustomFixingByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbInlandBuyInOut.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgImpExpChinBuyQuery();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbCustomMateriel.isSelected()) {// 加工贸易原材料来源与使用情况表
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityCustomMaterielByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbCustomMateriel.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgImgOrgUseInfo();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbCustomProduct.isSelected()) {// 加工贸易产品流向情况表
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityCustomProductByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbCustomProduct.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgExgExportInfo();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbCustomFixing.isSelected()) {// 加工贸易生产设备使用情况表
					//
					// check authority
					//
					casCustomReportAction
							.checkAuthorityCustomFixingByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbCustomFixing.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgCustomFixingThing();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				}

				// else if (rbCustomSource.isSelected()) { // 料件，成品，设备库存情况统计表
				// //
				// // check authority
				// //
				// casCustomReportAction
				// .checkAuthorityCustomSourceByBrowse(new Request(
				// CommonVars.getCurrUser()));
				//
				// String flag = rbCustomSource.getActionCommand();
				// JDialogBase dialog = JDialogBaseHelper
				// .getJDialogBaseByFlag(flag);
				// if (dialog == null) {
				// dialog = new DgStoreInfo();
				// JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
				// dialog.setVisible(true);
				// } else {
				// dialog.setVisibleNoChange(true);
				// }
				//
				// }
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			} finally {
				component.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	/**
	 * This method initializes rbInlandBuy
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbInlandBuy() {
		if (rbInlandBuyInOut == null) {
			rbInlandBuyInOut = new JRadioButton();
			rbInlandBuyInOut.setBounds(new Rectangle(227, 90, 147, 26));
			rbInlandBuyInOut.setText("国内购买进出仓帐");
		}
		return rbInlandBuyInOut;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton7() {
		if (rbOutSourceInOutBack == null) {
			rbOutSourceInOutBack = new JRadioButton();
			rbOutSourceInOutBack.setBounds(new Rectangle(429, 59, 168, 26));
			rbOutSourceInOutBack.setText("委外加工收回进出仓账");
		}
		return rbOutSourceInOutBack;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton12() {
		if (rbTrusteeInOut == null) {
			rbTrusteeInOut = new JRadioButton();
			rbTrusteeInOut.setBounds(new Rectangle(429, 93, 168, 26));
			rbTrusteeInOut.setText("受托加工受托进出仓帐");
		}
		return rbTrusteeInOut;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton22() {
		if (rbTrusteeInOutBack == null) {
			rbTrusteeInOutBack = new JRadioButton();
			rbTrusteeInOutBack.setBounds(new Rectangle(429, 122, 168, 26));
			rbTrusteeInOutBack.setText("受托加工返回进出仓帐");
		}
		return rbTrusteeInOutBack;
	}
}