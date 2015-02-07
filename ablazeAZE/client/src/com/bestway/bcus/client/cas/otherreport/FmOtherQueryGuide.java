/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

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

import com.bestway.bcus.cas.authority.CasOtherReportAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmOtherQueryGuide extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private ButtonGroup group = new ButtonGroup();  //  @jve:decl-index=0:

	private JPanel PnBottom = null;

	private JButton btnExit = null;

	private JRadioButton rbLeftoverMateriel = null;

	private CasOtherReportAction casOtherReportAction = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton rbTransFactCompareInfoOnDay = null;

	private JRadioButton rbTransFactRecvSendDetailInfo = null;

	private JRadioButton rbTransFactCompareInfoOnMonth = null;

	private JRadioButton rbAllTransFactDiffInfo = null;

	private JRadioButton rbFixtureNotInTaxation = null;

	private JRadioButton rbFixtureInTaxation = null;

	private JRadioButton rbMaterielStorageInfo = null;

	private JRadioButton rbVenderDiff = null;

	private JRadioButton rbCustomDiff = null;

	/**
	 * This is the default constructor
	 */
	public FmOtherQueryGuide() {
		super();
		casOtherReportAction = (CasOtherReportAction) CommonVars
				.getApplicationContext().getBean("casOtherReportAction");
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setAllRadioButtonProperty(this.jContentPane);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("其它统计报表");
		this.setSize(710, 517);
		this.setContentPane(getJContentPane());
		initFixComponents();
	}

	private void initFixComponents() {
		String code = ((Company) CommonVars.getCurrUser().getCompany())
				.getCode();// 加工单位代码
		char[] a = new String(code.getBytes(), 0, code.length()).toCharArray();
		if (a[5] >= 'A' && a[5] <= 'z' && a[6] >= 'A' && a[6] <= 'z') {// 当公司的加工单位编码的第六和第七位为字母为来料公司否则为三资
			this.rbFixtureNotInTaxation.setText("不作价设备清单");
		} else {
			this.rbFixtureNotInTaxation.setText("减免税设备清单");
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
			jLabel.setText("其它统计报表");
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
			jPanel1.add(getPnBottom(), null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
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
			jPanel2.setBounds(22, 22, 638, 323);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"其它统计报表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									java.awt.Color.black));
			jPanel2.add(getRbLeftoverMateriel(), null);
			jPanel2.add(getJRadioButton2(), null);
			jPanel2.add(getRbTransFactCompareInfoOnDay(), null);
			jPanel2.add(getRbTransFactRecvSendDetailInfo(), null);
			jPanel2.add(getRbTransFactCompareInfoOnMonth(), null);
			jPanel2.add(getRbAllTransFactDiffInfo(), null);
			jPanel2.add(getRbFixtureNotInTaxation(), null);
			jPanel2.add(getRbFixtureInTaxation(), null);
			jPanel2.add(getRbMaterielStorageInfo(), null);
			jPanel2.add(getRbVenderDiff(), null);
			jPanel2.add(getJRadioButton22(), null);
		}
		return jPanel2;
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
		jPanel2.setBounds(28, 22, this.jPanel1.getWidth() - 28 - 28,
				this.jPanel1.getHeight() - 98);
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
	 * This method initializes rb55
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbLeftoverMateriel() {
		if (rbLeftoverMateriel == null) {
			rbLeftoverMateriel = new JRadioButton();
			rbLeftoverMateriel.setBounds(new Rectangle(61, 57, 131, 26));
			rbLeftoverMateriel.setText("边角料查询报表");
		}
		return rbLeftoverMateriel;
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

	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JComponent component = (JComponent) e.getSource();
			component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {

				if (rbLeftoverMateriel.isSelected()) {
					//
					// check authority
					//
					casOtherReportAction
							.checkCalLeftoverMaterielByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbLeftoverMateriel.getActionCommand();
					JDialogBase d = JDialogBaseHelper.getJDialogBaseByFlag(flag);
					if (d == null) {
						d = new FmLeftoverMaterielStat();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, d);
						d.setVisible(true);					
					} else {
						d.setVisibleNoChange(true);
					}
					d.toFront();
					
					
				} else if (jRadioButton.isSelected()) {
					FmProductNoQuery fp = new FmProductNoQuery();
					fp.setVisible(true);
				} else if (rbTransFactRecvSendDetailInfo.isSelected()) {// 收／送货明细表
					casOtherReportAction.checkMiRecvSendDetailInfo(new Request(
							CommonVars.getCurrUser()));
					String flag = rbTransFactRecvSendDetailInfo
							.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgTransFactRecvSendDetailInfo();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
					dialog.toFront();

				} else if (rbTransFactCompareInfoOnDay.isSelected()) {// 转厂差额明细表
					//
					// check authority
					//
					// casOtherReportAction
					// .checkAuthorityTranFactoryMarginByBrowse(new Request(
					// CommonVars.getCurrUser()));

					String flag = rbTransFactCompareInfoOnDay
							.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgTransFactCompareInfoOnDay();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
					dialog.toFront();
				} else if (rbTransFactCompareInfoOnMonth.isSelected()) {// 结转对照表
					casOtherReportAction.checkTransFactCompareInfo(new Request(
							CommonVars.getCurrUser()));
					String flag = rbTransFactCompareInfoOnMonth
							.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgTransFactCompareInfoOnMonth();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
					dialog.toFront();
				} else if (rbAllTransFactDiffInfo.isSelected()) {// 转厂差额总表
					//
					// check authority
					//
					// casOtherReportAction
					// .checkAuthorityTranFactoryMarginByBrowse(new Request(
					// CommonVars.getCurrUser()));
					String flag = rbAllTransFactDiffInfo.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgAllTransFactDiffInfo();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
					dialog.toFront();
				} else if (rbFixtureInTaxation.isSelected()) {// 非保税设备清单
					casOtherReportAction.checkFixtureInTaxation(new Request(
							CommonVars.getCurrUser()));
					String flag = rbFixtureInTaxation.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgFixtureInTaxation();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
					dialog.toFront();
				} else if (rbFixtureNotInTaxation.isSelected()) {// 保税设备清单
					casOtherReportAction.checkFixtureNotInTaxation(new Request(
							CommonVars.getCurrUser()));

					String flag = rbFixtureNotInTaxation.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgFixtureNotInTaxation();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
					dialog.toFront();
				} else if (rbMaterielStorageInfo.isSelected()) {// 车间材料库存统计表
					casOtherReportAction.checkFixtureNotInTaxation(new Request(
							CommonVars.getCurrUser()));

					String flag = rbMaterielStorageInfo.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgMaterielStorageInfo();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
					dialog.toFront();
				} else if (rbVenderDiff.isSelected()) {//供应商转厂差额总表
					DgTransferfactoryDiffAllReport dg = new DgTransferfactoryDiffAllReport();
					dg.setTitle("供应商转厂差额总表");
					dg.setIsM(true);
					dg.setVisible(true);
				} else if (rbCustomDiff.isSelected()) {//客户转厂差额总表
					DgTransferfactoryDiffAllReport dg = new DgTransferfactoryDiffAllReport();
					dg.setTitle("客户转厂差额总表");
					dg.setIsM(false);
					dg.setVisible(true);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex.getMessage());
			} finally {
				component.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(61, 83, 131, 26));
			jRadioButton.setText("制单号查询报表");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes rbTranFactoryMargin
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransFactCompareInfoOnDay() {
		if (rbTransFactCompareInfoOnDay == null) {
			rbTransFactCompareInfoOnDay = new JRadioButton();
			rbTransFactCompareInfoOnDay.setBounds(new Rectangle(61, 136, 131, 26));
			rbTransFactCompareInfoOnDay.setText("转厂差额明细表");
		}
		return rbTransFactCompareInfoOnDay;
	}

	/**
	 * This method initializes rbTranFactoryMargin1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransFactRecvSendDetailInfo() {
		if (rbTransFactRecvSendDetailInfo == null) {
			rbTransFactRecvSendDetailInfo = new JRadioButton();
			rbTransFactRecvSendDetailInfo.setBounds(new Rectangle(61, 111, 131, 23));
			rbTransFactRecvSendDetailInfo.setText("收/送货明细表");
		}
		return rbTransFactRecvSendDetailInfo;
	}

	/**
	 * This method initializes rbTranFactoryMargin1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransFactCompareInfoOnMonth() {
		if (rbTransFactCompareInfoOnMonth == null) {
			rbTransFactCompareInfoOnMonth = new JRadioButton();
			rbTransFactCompareInfoOnMonth.setBounds(new Rectangle(61, 164, 131, 23));
			rbTransFactCompareInfoOnMonth.setText("结转对照表");
		}
		return rbTransFactCompareInfoOnMonth;
	}

	/**
	 * This method initializes rbTranFactoryMargin11
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbAllTransFactDiffInfo() {
		if (rbAllTransFactDiffInfo == null) {
			rbAllTransFactDiffInfo = new JRadioButton();
			rbAllTransFactDiffInfo.setBounds(new Rectangle(61, 188, 131, 23));
			rbAllTransFactDiffInfo.setText("转厂差额总表");
		}
		return rbAllTransFactDiffInfo;
	}

	/**
	 * This method initializes rbBalanceInfo1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbFixtureNotInTaxation() {
		if (rbFixtureNotInTaxation == null) {
			rbFixtureNotInTaxation = new JRadioButton();
			rbFixtureNotInTaxation.setBounds(new Rectangle(61, 32, 186, 23));
			rbFixtureNotInTaxation.setText("减免税设备清单");
		}
		return rbFixtureNotInTaxation;
	}

	/**
	 * This method initializes rbBalanceInfo3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbFixtureInTaxation() {
		if (rbFixtureInTaxation == null) {
			rbFixtureInTaxation = new JRadioButton();
			rbFixtureInTaxation.setBounds(new Rectangle(309, 32, 288, 23));
			rbFixtureInTaxation.setText("征税设备清单(已解除监管)");
		}
		return rbFixtureInTaxation;
	}

	/**
	 * This method initializes rbMaterielStorageInfo	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbMaterielStorageInfo() {
		if (rbMaterielStorageInfo == null) {
			rbMaterielStorageInfo = new JRadioButton();
			rbMaterielStorageInfo.setBounds(new Rectangle(61, 214, 172, 25));
			rbMaterielStorageInfo.setText("车间材料库存统计表");
		}
		return rbMaterielStorageInfo;
	}

	/**
	 * This method initializes rbVenderDiff	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbVenderDiff() {
		if (rbVenderDiff == null) {
			rbVenderDiff = new JRadioButton();
			rbVenderDiff.setBounds(new Rectangle(309, 57, 200, 21));
			rbVenderDiff.setText("供应商转厂差额总表");
		}
		return rbVenderDiff;
	}

	/**
	 * This method initializes jRadioButton2	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton22() {
		if (rbCustomDiff == null) {
			rbCustomDiff = new JRadioButton();
			rbCustomDiff.setBounds(new Rectangle(309, 83, 200, 21));
			rbCustomDiff.setText("客户转厂差额总表");
		}
		return rbCustomDiff;
	}

}
