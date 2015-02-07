/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.finance;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.authority.CasFinanceReportAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Font;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmFinanceQueryGuide extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbMaterielDetail = null;

	private JRadioButton rbProductDetail = null;

	private JRadioButton rbFinanceDebt = null;

	private JRadioButton rbFinanceMargin = null;

	private JRadioButton rbLeftoverMaterialDetail = null;

	private JRadioButton rbBadProductDetail = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JRadioButton rbFinanceSellSum = null;

	private JRadioButton rbFinanceStockSum = null;

	private JRadioButton rbFinanceCheck = null;

	private JRadioButton rbFinanceMoneyFlux = null;

	private ButtonGroup group = new ButtonGroup();

	private JPanel PnBottom = null;

	private JButton btnExit = null;

	private CasFinanceReportAction casFinanceReportAction = null;

	/**
	 * This is the default constructor
	 */
	public FmFinanceQueryGuide() {
		super();
		casFinanceReportAction = (CasFinanceReportAction) CommonVars
				.getApplicationContext().getBean("casFinanceReportAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("海关帐财务统计报表");
		this.setSize(722, 482);
		this.setContentPane(getJContentPane());
		group.add(rbMaterielDetail);
		group.add(rbProductDetail);
		group.add(rbFinanceDebt);
		group.add(rbFinanceMargin);
		group.add(rbLeftoverMaterialDetail);
		group.add(rbBadProductDetail);
		group.add(rbFinanceSellSum);
		group.add(rbFinanceStockSum);
		group.add(rbFinanceCheck);
		group.add(rbFinanceMoneyFlux);
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jLabel.setText("海关帐财务统计报表");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			jPanel.setBackground(java.awt.Color.white);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel
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
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel2(), null);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getPnBottom(), null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
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
		if (rbMaterielDetail == null) {
			rbMaterielDetail = new JRadioButton();
			rbMaterielDetail.setText("原材料明细帐");
			rbMaterielDetail.setBounds(121, 29, 113, 21);
			rbMaterielDetail.addActionListener(new RadioActionListner());
		}
		return rbMaterielDetail;
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
		if (rbProductDetail == null) {
			rbProductDetail = new JRadioButton();
			rbProductDetail.setText("产成品明细帐");
			rbProductDetail.setBounds(121, 59, 106, 21);
			rbProductDetail.addActionListener(new RadioActionListner());
		}
		return rbProductDetail;
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
		if (rbLeftoverMaterialDetail == null) {
			rbLeftoverMaterialDetail = new JRadioButton();
			rbLeftoverMaterialDetail.setText("边角料明细帐");
			rbLeftoverMaterialDetail.setBounds(390, 29, 112, 21);
			rbLeftoverMaterialDetail
					.addActionListener(new RadioActionListner());
		}
		return rbLeftoverMaterialDetail;
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
		if (rbBadProductDetail == null) {
			rbBadProductDetail = new JRadioButton();
			rbBadProductDetail.setText("残次品明细帐");
			rbBadProductDetail.setBounds(390, 59, 111, 21);
			rbBadProductDetail.addActionListener(new RadioActionListner());
		}
		return rbBadProductDetail;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(28, 28, 615, 104);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"海关帐财务统计报表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									java.awt.Color.black));
			jPanel2.add(getJRadioButton(), null);
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(getJRadioButton4(), null);
			jPanel2.add(getJRadioButton5(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(28, 164, 615, 154);
			jPanel3
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel3.add(getJRadioButton8(), null);
			jPanel3.add(getJRadioButton9(), null);
			jPanel3.add(getJRadioButton10(), null);
			jPanel3.add(getJRadioButton11(), null);
			jPanel3.add(getJRadioButton2(), null);
			jPanel3.add(getJRadioButton3(), null);
		}
		return jPanel3;
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
		if (rbFinanceSellSum == null) {
			rbFinanceSellSum = new JRadioButton();
			rbFinanceSellSum.setBounds(121, 23, 118, 21);
			rbFinanceSellSum.setText("销售收入统计表");
			rbFinanceSellSum.addActionListener(new RadioActionListner());
		}
		return rbFinanceSellSum;
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
		if (rbFinanceStockSum == null) {
			rbFinanceStockSum = new JRadioButton();
			rbFinanceStockSum.setBounds(121, 51, 90, 21);
			rbFinanceStockSum.setText("存货统计表");
			rbFinanceStockSum.addActionListener(new RadioActionListner());
		}
		return rbFinanceStockSum;
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
		if (rbFinanceCheck == null) {
			rbFinanceCheck = new JRadioButton();
			rbFinanceCheck.setBounds(121, 79, 71, 21);
			rbFinanceCheck.setText("盘点表");
			rbFinanceCheck.addActionListener(new RadioActionListner());
		}
		return rbFinanceCheck;
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
	private JRadioButton getJRadioButton11() {
		if (rbFinanceMoneyFlux == null) {
			rbFinanceMoneyFlux = new JRadioButton();
			rbFinanceMoneyFlux.setBounds(121, 107, 101, 21);
			rbFinanceMoneyFlux.setText("现金流量表");
			rbFinanceMoneyFlux.addActionListener(new RadioActionListner());
		}
		return rbFinanceMoneyFlux;
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
		if (rbFinanceDebt == null) {
			rbFinanceDebt = new JRadioButton();
			rbFinanceDebt.setBounds(390, 23, 94, 21);
			rbFinanceDebt.setText("资产负债表");
			rbFinanceDebt.addActionListener(new RadioActionListner());
		}
		return rbFinanceDebt;
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
		if (rbFinanceMargin == null) {
			rbFinanceMargin = new JRadioButton();
			rbFinanceMargin.setBounds(390, 51, 73, 21);
			rbFinanceMargin.setText("利润表");
			rbFinanceMargin.addActionListener(new RadioActionListner());
		}
		return rbFinanceMargin;
	}

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JComponent component = (JComponent) e.getSource();
			component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {

				if (rbMaterielDetail.isSelected()) {// 原材料明细帐 1
					//
					// check authority
					//
					casFinanceReportAction.check1ByBrower(new Request(
							CommonVars.getCurrUser()));

					showForm(1);
				} else if (rbLeftoverMaterialDetail.isSelected()) {// 边角料明细帐 2
					//
					// check authority
					//
					casFinanceReportAction.check2ByBrower(new Request(
							CommonVars.getCurrUser()));

					showForm(3);
				} else if (rbProductDetail.isSelected()) {// 产成品明细帐 3
					//
					// check authority
					//
					casFinanceReportAction.check3ByBrower(new Request(
							CommonVars.getCurrUser()));

					showForm(2);
				} else if (rbBadProductDetail.isSelected()) { // 残次品明细帐 4
					//
					// check authority
					//
					casFinanceReportAction.check4ByBrower(new Request(
							CommonVars.getCurrUser()));

					showForm(4);
				} else if (rbFinanceSellSum.isSelected()) { // 销售收入统计表 5
					//
					// check authority
					//
					casFinanceReportAction.check5ByBrower(new Request(
							CommonVars.getCurrUser()));

					DgFinanceSellSum dg = new DgFinanceSellSum();
					dg.setVisible(true);
				} else if (rbFinanceDebt.isSelected()) {// 资产负债表 6
					//
					// check authority
					//
					casFinanceReportAction.check6ByBrower(new Request(
							CommonVars.getCurrUser()));

					DgFinanceDebt dg = new DgFinanceDebt();
					dg.setVisible(true);
				} else if (rbFinanceStockSum.isSelected()) { // 存货统计表 7
					//
					// check authority
					//
					casFinanceReportAction.check7ByBrower(new Request(
							CommonVars.getCurrUser()));

					DgFinanceStockSum dg = new DgFinanceStockSum();
					dg.setVisible(true);
				} else if (rbFinanceMargin.isSelected()) { // 利润表 8
					//
					// check authority
					//
					casFinanceReportAction.check8ByBrower(new Request(
							CommonVars.getCurrUser()));

					DgFinanceMargin dg = new DgFinanceMargin();
					dg.setVisible(true);
				} else if (rbFinanceCheck.isSelected()) { // 盘点表 9
					//
					// check authority
					//
					casFinanceReportAction.check9ByBrower(new Request(
							CommonVars.getCurrUser()));

					DgFinanceCheck dg = new DgFinanceCheck();
					dg.setVisible(true);
				} else if (rbFinanceMoneyFlux.isSelected()) { // 现金流量表 10
					//
					// check authority
					//
					casFinanceReportAction.check10ByBrower(new Request(
							CommonVars.getCurrUser()));

					DgFinanceMoneyFlux dg = new DgFinanceMoneyFlux();
					dg.setVisible(true);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			} finally {
				component.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	private void showForm(int titleName) {
		DgFinanceQuery dgFinanceQuery = new DgFinanceQuery();
		dgFinanceQuery.setTitleName(titleName);
		dgFinanceQuery.setVisible(true);
		// JDialogBase dialog =
		// JDialogBaseHelper.getJDialogBaseByFlag(DgFinanceQuery.class);
		// if(dialog == null){
		// dialog = new DgFinanceQuery();
		// dialog.setTitleName(titleName);;
		// JDialogBaseHelper.putJDialogBaseToFlag(dialog);
		// }
		// dialog.setVisible(true);
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBottom() {
		if (PnBottom == null) {
			PnBottom = new JPanel();
			PnBottom.setBounds(17, 341, 678, 3);
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
			btnExit.setBounds(602, 354, 68, 26);
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
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		PnBottom.setBounds(3, this.jPanel1.getHeight() - 50, this.jPanel1
				.getWidth() - 6, 3);
		// // jPanel5.setBounds(28, this.jPanel1.getHeight() - 50,
		// this.jPanel1.getWidth() - 28 -28, 3);
		btnExit.setBounds(this.jPanel1.getWidth() - 92, this.jPanel1
				.getHeight() - 40, 68, 26);
		//
		jPanel2.setBounds(28, 28, this.jPanel1.getWidth() - 28 - 28, 104);
		jPanel3.setBounds(28, 164, this.jPanel1.getWidth() - 28 - 28, 154);
	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

}
