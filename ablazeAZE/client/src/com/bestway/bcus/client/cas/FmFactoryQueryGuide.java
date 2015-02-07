/*
 * Created on 2004-9-17
 *
 * //
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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import com.bestway.bcus.cas.authority.CasFactoryReportAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 工厂资料查询统计主界面 贺巍于2009年7月7日添加注释
 * 
 * @author Administrator
 */
public class FmFactoryQueryGuide extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel pnTitle = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnMain = null;

	private JPanel pnMainTwo = null;

	private JPanel pnSubOne = null;

	private JPanel pnSubTwo = null;

	private JRadioButton rbMaterielSumReport = null;

	private JRadioButton rbMaterielStorageSumReport = null;

	private JRadioButton rbMaterielMonthReport = null;

	private ButtonGroup group = new ButtonGroup();

	private JRadioButton rbProductSumReport = null;

	private JRadioButton rbProductStorageSumReport = null;

	private JRadioButton rbProductMonthReport = null;

	private JPanel pnSubThree = null;

	private JPanel pnSubFour = null;

	private JRadioButton rbLeftoverMaterialSumReport = null;

	private JRadioButton rbBadProductSum = null;

	private JRadioButton rbHalfProductDetail = null;

	private JRadioButton rbHalfProductStore = null;

	private JRadioButton rbHalfProductMonthReport = null;

	private JRadioButton rbLeftoverMaterialStorageSum = null;

	private JRadioButton rbBadProductStorage = null;

	private JRadioButton rbFixingDetail = null;

	private JRadioButton rbFixingStore = null;

	private CasFactoryReportAction casFactoryReportAction = null;

	private JRadioButton rbMaterielMonthReport1 = null;

	private JRadioButton rbProductMonthReport1 = null;

	/**
	 * 构造函数 This is the default constructor
	 */
	public FmFactoryQueryGuide() {
		super();
		casFactoryReportAction = (CasFactoryReportAction) CommonVars
				.getApplicationContext().getBean("casFactoryReportAction");
		initialize();
	}

	/**
	 * 设置是否可视
	 */
	public void setVisible(boolean b) {
		if (b) {
			setAllRadioButtonProperty(jContentPane);
		}
		super.setVisible(b);
	}

	/**
	 * 初始化组建大小以及标题 This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("工厂资料查询统计");
		this.setSize(742, 509);
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
			jContentPane.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 18));
			jContentPane.add(getPnTitle(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jLabel.setText("工厂资料查询统计");
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			pnTitle.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pnTitle.setBackground(java.awt.Color.white);
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel2.setText("");
			jLabel2.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			pnTitle.add(jLabel, java.awt.BorderLayout.CENTER);
			pnTitle.add(jLabel1, java.awt.BorderLayout.WEST);
			pnTitle.add(jLabel2, java.awt.BorderLayout.EAST);
		}
		return pnTitle;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();

			jTabbedPane.addTab("料件成品统计表", null, getPnMain(), null);
			jTabbedPane.addTab("其它统计报表", null, getPnMainTwo(), null);
		}
		return jTabbedPane;
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
		}
		return pnMain;
	}

	/**
	 * 
	 * This method initializes pnMainTwo
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnMainTwo() {
		if (pnMainTwo == null) {
			pnMainTwo = new JPanel();
			pnMainTwo.setLayout(null);
			pnMainTwo.add(getPnSubThree(), null);
			pnMainTwo.add(getPnSubFour(), null);
		}
		return pnMainTwo;
	}

	/**
	 * This method initializes pnSubOne
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnSubOne() {
		if (pnSubOne == null) {
			pnSubOne = new JPanel();
			pnSubOne.setLayout(null);
			pnSubOne.setBounds(28, 28, 650, 148);
			pnSubOne.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
					"料件",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					java.awt.Color.black));
			pnSubOne.add(getRbMaterielSumReport(), null);
			pnSubOne.add(getRbMaterielStorageSumReport(), null);
			pnSubOne.add(getRbMaterielMonthReport(), null);
			pnSubOne.add(getRbMaterielMonthReport1(), null);
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
			pnSubTwo.setBounds(28, 205, 650, 151);
			pnSubTwo.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
					"成品",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
					java.awt.Color.black));
			pnSubTwo.add(getRbProductSumReport(), null);
			pnSubTwo.add(getRbProductStorageSumReport(), null);
			pnSubTwo.add(getRbProductMonthReport(), null);
			pnSubTwo.add(getRbProductMonthReport1(), null);
		}
		return pnSubTwo;
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
	private JRadioButton getRbMaterielSumReport() {
		if (rbMaterielSumReport == null) {
			rbMaterielSumReport = new JRadioButton();
			rbMaterielSumReport.setBounds(126, 46, 149, 21);
			rbMaterielSumReport.setText("料件出入库明细帐");
		}
		return rbMaterielSumReport;
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
	private JRadioButton getRbMaterielStorageSumReport() {
		if (rbMaterielStorageSumReport == null) {
			rbMaterielStorageSumReport = new JRadioButton();
			rbMaterielStorageSumReport.setText("料件仓库库存情况表");
			rbMaterielStorageSumReport.setBounds(new java.awt.Rectangle(126,
					82, 168, 21));
		}
		return rbMaterielStorageSumReport;
	}

	/**
	 * 
	 * This method initializes jRadioButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbMaterielMonthReport() {
		if (rbMaterielMonthReport == null) {
			rbMaterielMonthReport = new JRadioButton();
			rbMaterielMonthReport.setText("材料月报表");
			rbMaterielMonthReport.setBounds(new Rectangle(387, 82, 193, 21));
		}
		return rbMaterielMonthReport;
	}

	/**
	 * 
	 * This method initializes jRadioButton12
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbProductSumReport() {
		if (rbProductSumReport == null) {
			rbProductSumReport = new JRadioButton();
			rbProductSumReport.setText("成品出入库明细帐");
			rbProductSumReport.setBounds(new java.awt.Rectangle(124, 40, 129,
					26));
		}
		return rbProductSumReport;
	}

	/**
	 * 
	 * This method initializes jRadioButton17
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbProductStorageSumReport() {
		if (rbProductStorageSumReport == null) {
			rbProductStorageSumReport = new JRadioButton();
			rbProductStorageSumReport.setText("成品仓库库存情况统计表");
			rbProductStorageSumReport.setBounds(new java.awt.Rectangle(124, 77,
					168, 26));
		}
		return rbProductStorageSumReport;
	}

	/**
	 * 
	 * This method initializes jRadioButton19
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbProductMonthReport() {
		if (rbProductMonthReport == null) {
			rbProductMonthReport = new JRadioButton();
			rbProductMonthReport.setText("成品月报表");
			rbProductMonthReport.setBounds(new java.awt.Rectangle(385, 77, 90,
					26));
		}
		return rbProductMonthReport;
	}

	/**
	 * 
	 * This method initializes pnSubThree
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnSubThree() {
		if (pnSubThree == null) {
			pnSubThree = new JPanel();
			pnSubThree.setLayout(null);
			pnSubThree.setBounds(28, 28, 650, 148);
			// jPanel10.setBounds(35, 31, 648, 152);
			pnSubThree
					.setBorder(javax.swing.BorderFactory.createTitledBorder(
							javax.swing.BorderFactory
									.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
							"边角料、残次品",
							javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
							javax.swing.border.TitledBorder.DEFAULT_POSITION,
							new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
							java.awt.Color.black));
			pnSubThree.add(getRbLeftoverMaterialSumReport(), null);
			pnSubThree.add(getRbLeftoverMaterialStorageSum(), null);
			pnSubThree.add(getRbBadProductSum(), null);
			pnSubThree.add(getRbBadProductStorage(), null);
		}
		return pnSubThree;
	}

	/**
	 * 
	 * This method initializes pnSubFour
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnSubFour() {
		if (pnSubFour == null) {
			pnSubFour = new JPanel();
			pnSubFour.setLayout(null);
			pnSubFour.setBounds(28, 205, 650, 151);
			pnSubFour
					.setBorder(javax.swing.BorderFactory.createTitledBorder(
							javax.swing.BorderFactory
									.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
							"半成品、设备",
							javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
							javax.swing.border.TitledBorder.DEFAULT_POSITION,
							new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
							java.awt.Color.black));
			pnSubFour.add(getRbHalfProductDetail(), null);
			pnSubFour.add(getRbHalfProductStore(), null);
			pnSubFour.add(getRbHalfProductMonthReport(), null);
			pnSubFour.add(getRbFixingDetail(), null);
			pnSubFour.add(getRbFixingStore(), null);
		}
		return pnSubFour;
	}

	/**
	 * 
	 * This method initializes jRadioButton23
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbLeftoverMaterialSumReport() {
		if (rbLeftoverMaterialSumReport == null) {
			rbLeftoverMaterialSumReport = new JRadioButton();
			rbLeftoverMaterialSumReport.setBounds(126, 46, 146, 21);
			rbLeftoverMaterialSumReport.setText("边角料出入库明细帐");
		}
		return rbLeftoverMaterialSumReport;
	}

	/**
	 * 
	 * This method initializes jRadioButton26
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbBadProductSum() {
		if (rbBadProductSum == null) {
			rbBadProductSum = new JRadioButton();
			rbBadProductSum.setBounds(new java.awt.Rectangle(387, 46, 160, 21));
			rbBadProductSum.setText("残次品出入库明细帐");
		}
		return rbBadProductSum;
	}

	/**
	 * 
	 * This method initializes jRadioButton29
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbHalfProductDetail() {
		if (rbHalfProductDetail == null) {
			rbHalfProductDetail = new JRadioButton();
			rbHalfProductDetail.setBounds(126, 27, 150, 21);
			rbHalfProductDetail.setText("半成品出入库明细帐");
		}
		return rbHalfProductDetail;
	}

	/**
	 * 
	 * This method initializes jRadioButton30
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbHalfProductStore() {
		if (rbHalfProductStore == null) {
			rbHalfProductStore = new JRadioButton();
			rbHalfProductStore.setBounds(126, 59, 161, 21);
			rbHalfProductStore.setText("半成品库存情况统计表");
		}
		return rbHalfProductStore;
	}

	/**
	 * 
	 * This method initializes jRadioButton31
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbHalfProductMonthReport() {
		if (rbHalfProductMonthReport == null) {
			rbHalfProductMonthReport = new JRadioButton();
			rbHalfProductMonthReport.setBounds(126, 90, 115, 21);
			rbHalfProductMonthReport.setText("半成品月报表");
		}
		return rbHalfProductMonthReport;
	}

	/**
	 * 
	 * This method initializes jRadioButton32
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbLeftoverMaterialStorageSum() {
		if (rbLeftoverMaterialStorageSum == null) {
			rbLeftoverMaterialStorageSum = new JRadioButton();
			rbLeftoverMaterialStorageSum.setText("边角料仓库库存情况统计表");
			rbLeftoverMaterialStorageSum.setBounds(new java.awt.Rectangle(126,
					82, 189, 21));
		}
		return rbLeftoverMaterialStorageSum;
	}

	/**
	 * 
	 * This method initializes jRadioButton34
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbBadProductStorage() {
		if (rbBadProductStorage == null) {
			rbBadProductStorage = new JRadioButton();
			rbBadProductStorage.setText("残次品仓库库存查询报表");
			rbBadProductStorage.setBounds(new java.awt.Rectangle(387, 82, 178,
					21));
		}
		return rbBadProductStorage;
	}

	/**
	 * 
	 * This method initializes jRadioButton36
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbFixingDetail() {
		if (rbFixingDetail == null) {
			rbFixingDetail = new JRadioButton();
			rbFixingDetail.setText("设备出入库明细帐");
			rbFixingDetail.setBounds(new java.awt.Rectangle(387, 27, 138, 21));
		}
		return rbFixingDetail;
	}

	/**
	 * 
	 * This method initializes jRadioButton37
	 * 
	 * 
	 * 
	 * @return javax.swing.JRadioButton
	 * 
	 */
	private JRadioButton getRbFixingStore() {
		if (rbFixingStore == null) {
			rbFixingStore = new JRadioButton();
			rbFixingStore.setText("设备库存情况统计表");
			rbFixingStore.setBounds(new java.awt.Rectangle(387, 59, 150, 21));
		}
		return rbFixingStore;
	}

	/**
	 * 内部类，用于扩展组件功能
	 * 
	 * @author ？
	 */
	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JComponent component = (JComponent) e.getSource();
			component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {
				//
				// 料件
				//
				if (rbMaterielSumReport.isSelected()) {
					//
					// 检查授权
					//
					casFactoryReportAction
							.checkAuthorityMaterielSumReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbMaterielSumReport.getActionCommand();

					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);

					if (dialog == null) {

						DgFactoryQuery dgFactoryQuery = new DgFactoryQuery();

						dgFactoryQuery.setMaterielType(MaterielType.MATERIEL);

						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryQuery);

						dgFactoryQuery.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbMaterielStorageSumReport.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityMaterielStorageSumReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbMaterielStorageSumReport.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactorySum dgFactorySum = new DgFactorySum();
						dgFactorySum.setMaterielType(MaterielType.MATERIEL);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactorySum);
						dgFactorySum.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbMaterielMonthReport.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityMaterielMonthReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbMaterielMonthReport.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryMonth dgFactoryMonth = new DgFactoryMonth(
								MaterielType.MATERIEL);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryMonth);
						dgFactoryMonth.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbMaterielMonthReport1.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityMaterielMonthReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbMaterielMonthReport1.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryMonth2 dgFactoryMonth = new DgFactoryMonth2(
								MaterielType.MATERIEL);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryMonth);
						dgFactoryMonth.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				}
				//
				// 成品
				//
				else if (rbProductSumReport.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityProductSumReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbProductSumReport.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryQuery dgFactoryQuery = new DgFactoryQuery();
						dgFactoryQuery
								.setMaterielType(MaterielType.FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryQuery);
						dgFactoryQuery.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbProductStorageSumReport.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityProductStorageSumReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbProductStorageSumReport.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactorySum dgFactorySum = new DgFactorySum();
						dgFactorySum
								.setMaterielType(MaterielType.FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactorySum);
						dgFactorySum.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbProductMonthReport.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityProductMonthReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbProductMonthReport.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryMonth dgFactoryMonth = new DgFactoryMonth(
								MaterielType.FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryMonth);
						dgFactoryMonth.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbProductMonthReport1.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityProductMonthReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbProductMonthReport1.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryMonth2 dgFactoryMonth = new DgFactoryMonth2(
								MaterielType.FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryMonth);
						dgFactoryMonth.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				}

				//
				// 半成品
				//
				else if (rbHalfProductDetail.isSelected()) {

					//
					// 浏览半成品出入库明细帐权限
					//
					casFactoryReportAction
							.checkAuthorityHalfProductDetailByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbHalfProductDetail.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryQuery dgFactoryQuery = new DgFactoryQuery();
						dgFactoryQuery
								.setMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryQuery);
						dgFactoryQuery.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbHalfProductStore.isSelected()) {

					//
					// 浏览半成品库存情况统计表权限
					//
					casFactoryReportAction
							.checkAuthorityHalfProductStoreByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbHalfProductStore.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactorySum dgFactorySum = new DgFactorySum();
						dgFactorySum
								.setMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactorySum);
						dgFactorySum.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbHalfProductMonthReport.isSelected()) {

					//
					// 浏览半成品月报表 权限
					//
					casFactoryReportAction
							.checkAuthorityHalfProductMonthReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbHalfProductMonthReport.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryMonth dgFactoryMonth = new DgFactoryMonth(
								MaterielType.SEMI_FINISHED_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryMonth);
						dgFactoryMonth.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				}
				//
				// 边角料
				//
				else if (rbLeftoverMaterialSumReport.isSelected()) {

					//
					// check authority
					//
					casFactoryReportAction
							.checkAuthorityLeftoverMaterialSumReportByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbLeftoverMaterialSumReport
							.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryQuery dgFactoryQuery = new DgFactoryQuery();
						dgFactoryQuery
								.setMaterielType(MaterielType.REMAIN_MATERIEL);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryQuery);
						dgFactoryQuery.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbLeftoverMaterialStorageSum.isSelected()) {

					//
					// 浏览边角料库存情况统计总表 check authority
					//
					casFactoryReportAction
							.checkAuthorityLeftoverMaterialStorageSumByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbLeftoverMaterialStorageSum
							.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactorySum dgFactorySum = new DgFactorySum();
						dgFactorySum
								.setMaterielType(MaterielType.REMAIN_MATERIEL);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactorySum);
						dgFactorySum.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				}
				//
				// 残次品
				//
				else if (rbBadProductSum.isSelected()) {

					//
					// 浏览残次品出入库明细帐总表 check authority
					//
					casFactoryReportAction
							.checkAuthorityBadProductSumByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbBadProductSum.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryQuery dgFactoryQuery = new DgFactoryQuery();
						dgFactoryQuery
								.setMaterielType(MaterielType.BAD_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryQuery);
						dgFactoryQuery.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbBadProductStorage.isSelected()) {

					//
					// 浏览残次品库存情况统计总表 check authority
					//
					casFactoryReportAction
							.checkAuthorityBadProductStorageByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbBadProductStorage.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactorySum dgFactorySum = new DgFactorySum();
						dgFactorySum.setMaterielType(MaterielType.BAD_PRODUCT);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactorySum);
						dgFactorySum.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				}
				//
				// 设备
				//
				else if (rbFixingDetail.isSelected()) {

					//
					// 浏览设备出入库明细帐 check authority
					//
					casFactoryReportAction
							.checkAuthorityFixingDetailByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbFixingDetail.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactoryQuery dgFactoryQuery = new DgFactoryQuery();
						dgFactoryQuery.setMaterielType(MaterielType.MACHINE);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactoryQuery);
						dgFactoryQuery.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbFixingStore.isSelected()) {

					//
					// 浏览设备库存情况统计表 check authority
					//
					casFactoryReportAction
							.checkAuthorityFixingStoreByBrowse(new Request(
									CommonVars.getCurrUser()));

					String flag = rbFixingStore.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						DgFactorySum dgFactorySum = new DgFactorySum();
						dgFactorySum.setMaterielType(MaterielType.MACHINE);
						JDialogBaseHelper.putJDialogBaseToFlag(flag,
								dgFactorySum);
						dgFactorySum.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

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
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		int width = pnMainTwo.getWidth();

		// 面板一
		Double y4 = pnSubOne.getBounds().getY();
		Double y5 = pnSubTwo.getBounds().getY();
		pnSubOne.setBounds(28, y4.intValue(), width - 28 - 28,
				pnSubOne.getHeight());
		pnSubTwo.setBounds(28, y5.intValue(), width - 28 - 28,
				pnSubTwo.getHeight());

		// // 面板二
		// Double y7 = jPanel7.getBounds().getY();
		// Double y8 = jPanel8.getBounds().getY();
		// jPanel7.setBounds(28, y7.intValue(), width - 28 - 28, jPanel7
		// .getHeight());
		// jPanel8.setBounds(28, y8.intValue(), width - 28 - 28, jPanel8
		// .getHeight());

		// 面板三
		pnSubThree.setBounds(28, pnSubThree.getY(), width - 28 - 28,
				pnSubThree.getHeight());

		pnSubFour.setBounds(28, pnSubFour.getY(), width - 28 - 28,
				pnSubFour.getHeight());
	}

	/**
	 * 重绘组件
	 */
	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * This method initializes rbMaterielMonthReport1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterielMonthReport1() {
		if (rbMaterielMonthReport1 == null) {
			rbMaterielMonthReport1 = new JRadioButton();
			rbMaterielMonthReport1.setBounds(new Rectangle(387, 48, 199, 23));
			rbMaterielMonthReport1.setText("材料月报表   (按报关级别统计)");
		}
		return rbMaterielMonthReport1;
	}

	/**
	 * This method initializes rbProductMonthReport1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductMonthReport1() {
		if (rbProductMonthReport1 == null) {
			rbProductMonthReport1 = new JRadioButton();
			rbProductMonthReport1.setBounds(new Rectangle(383, 44, 201, 25));
			rbProductMonthReport1.setText("成品月报表   (按报关级别统计)");
		}
		return rbProductMonthReport1;
	}

}
