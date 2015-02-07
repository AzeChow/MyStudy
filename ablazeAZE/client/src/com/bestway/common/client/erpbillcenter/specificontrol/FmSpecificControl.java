/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.specificontrol;

/**
 * 单据对应 刘民添加部分注释 修改时间： 2008-10-11
 * 
 * @author 余鹏// change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 * 
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.authority.CasSpecifControlAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.specificontrol.entity.CustomsDeclarationCommInfoBillCorresponding;
import com.bestway.bcus.cas.specificontrol.entity.TempResult;
import com.bestway.bcus.client.cas.otherreport.FmCheckBalance;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.cas.specificontrol.DgBigBatchDeleteBill;
import com.bestway.bcus.client.cas.specificontrol.DgBillCorresByPk;
import com.bestway.bcus.client.cas.specificontrol.DgBillCorrespondingSearch;
import com.bestway.bcus.client.cas.specificontrol.DgBillMakeCustomNum;
import com.bestway.bcus.client.cas.specificontrol.DgEditBillPrice;
import com.bestway.bcus.client.cas.specificontrol.DgFinanceBatchWriteAccount;
import com.bestway.bcus.client.cas.specificontrol.DgHalfProductManager;
import com.bestway.bcus.client.cas.specificontrol.DgHandworkBatchCorresponding;
import com.bestway.bcus.client.cas.specificontrol.FmBillCorresponding;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.JFrameBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.beanutils.PropertyUtils;

import java.awt.Font;
import java.awt.Color;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmSpecificControl extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel2 = null;

	private JRadioButton rbEditBillPrice = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel5 = null;

	private JButton btnExit = null;

	private JRadioButton rbBigBatchDeleteBill = null;

	private JRadioButton rbReportBills = null;

	private JRadioButton rbFinanceBatchWriteAccount = null;

	private JPanel jPanel3 = null;

	private JRadioButton rbBillCorresponding = null;

	private JRadioButton rbHandworkBatchCorresponding = null;

	private JRadioButton rbBillCorrespondingSearch = null;

	private JRadioButton rbPkCorr = null;

	private JPanel jPanel4 = null;

	private JRadioButton rbBillMakeCustomNum = null;

	private JRadioButton rbHalfProductManager = null;

	private JRadioButton rbBatchUpdateHs = null;

	private JButton btnUpdate = null;

	/**
	 * 海关帐远程接口
	 */
	private CasAction casAction = null;

	/**
	 * This is the default constructor
	 */
	public FmSpecificControl() {
		super();
		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkByBrower(new Request(CommonVars
				.getCurrUser()));
		/**
		 * 初始化海关帐远程接口
		 */
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
		setState();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("单据中心特殊控制");
		this.setSize(730, 533);
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
			jPanel2.setOpaque(false);
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"单据",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									new java.awt.Font("Dialog",
											java.awt.Font.PLAIN, 12),
									java.awt.Color.black));
			jPanel2.setBounds(28, 20, 654, 108);
			jPanel2.add(getRbEditBillPrice(), null);
			jPanel2.add(getRbBigBatchDeleteBill(), null);
			jPanel2.add(getRbReportBills(), null);
			jPanel2.add(getRbFinanceBatchWriteAccount(), null);
			jPanel2.add(getRbBatchUpdateHs(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbEditBillPrice
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEditBillPrice() {
		if (rbEditBillPrice == null) {
			rbEditBillPrice = new JRadioButton();
			// rbEditBillPrice.setEnabled(false);
			rbEditBillPrice.setText("修改单据单价");
			rbEditBillPrice.setBounds(27, 47, 204, 20);
			rbEditBillPrice.setOpaque(false);
			rbEditBillPrice.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		return rbEditBillPrice;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			setAllRadioButtonProperty(this.jPanel1);
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
			jLabel.setText("单据中心特殊控制");
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
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
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJPanel2(), null);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.add(getJPanel5(), null);
			jPanel1.add(getBtnExit(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setOpaque(false);
			jPanel5.setBounds(14, 395, 682, 3);
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jPanel5;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(600, 411, 68, 26);
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
	 * This method initializes rbBigBatchDeleteBill
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBigBatchDeleteBill() {
		if (rbBigBatchDeleteBill == null) {
			rbBigBatchDeleteBill = new JRadioButton();
			rbBigBatchDeleteBill.setOpaque(false);
			rbBigBatchDeleteBill.setBounds(new Rectangle(27, 21, 204, 20));
			rbBigBatchDeleteBill.setText("大批量删除、回卷、生效单据");
		}
		return rbBigBatchDeleteBill;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {

		jPanel2.setBounds(28, this.jPanel1.getHeight() / 12, this.jPanel1
				.getWidth() - 28 - 28, this.jPanel1.getHeight() / 4);

		jPanel3.setBounds(28, this.jPanel1.getHeight() / 11 * 4, this.jPanel1
				.getWidth() - 28 - 28, this.jPanel1.getHeight() / 5);

		jPanel4.setBounds(28, this.jPanel1.getHeight() / 5 * 3, this.jPanel1
				.getWidth() - 28 - 28, this.jPanel1.getHeight() / 5);

		btnExit.setBounds(this.jPanel1.getWidth() - 92, this.jPanel1
				.getHeight()
				- this.btnExit.getHeight() - 10, 68, 26);
		jPanel5.setBounds(3, this.btnExit.getY() - 13,
				this.jPanel1.getWidth() - 6, 3);

	}

	/**
	 * 重画组件
	 */
	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
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
				buttonGroup.add(radioButton);
			} else {
				setAllRadioButtonProperty(temp);
			}
		}
	}

	/**
	 * 单击监听类
	 * 
	 * @author ls // change the template for this generated type comment go to
	 *         Window - Preferences - Java - Code Style - Code Templates
	 */
	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			JComponent component = (JComponent) e.getSource();
			component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			/**
			 * 1.修改单据单价 2.大批量删除和回卷单据 3.财务成批记帐 4.批量打印单据 5.单据对应 6.手工批量对应
			 * 7.报关单和单据对应查询 8.单据对应--PK单对应 9.生成单据的折算报关数量 10.半成品委外管理
			 * 11.批量修改单据料号所对应的报关商品资料
			 */
			CasSpecifControlAction casSpecifControlAction = (CasSpecifControlAction) CommonVars
					.getApplicationContext().getBean("casSpecifControlAction");
			try {
				if (rbEditBillPrice.isSelected()) { // 修改单据单价

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check3ByBrower(new Request(
							CommonVars.getCurrUser()));

					String flag = rbEditBillPrice.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgEditBillPrice();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbBigBatchDeleteBill.isSelected()) { // 大批量删除和回卷单据

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check1ByBrower(new Request(
							CommonVars.getCurrUser()));

					String flag = rbBigBatchDeleteBill.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgBigBatchDeleteBill();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbFinanceBatchWriteAccount.isSelected()) {// 财务成批记帐

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check2ByBrower(new Request(
							CommonVars.getCurrUser()));
					String flag = rbFinanceBatchWriteAccount.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgFinanceBatchWriteAccount();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbBillCorresponding.isSelected()) { // 单据对应
					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check4ByBrower(new Request(
							CommonVars.getCurrUser()));

					String flag = rbBillCorresponding.getActionCommand();
					JFrameBase fm = JFrameBaseHelper.getJFrameBaseByFlag(flag);
					if (fm == null) {
						fm = new FmBillCorresponding();
						JFrameBaseHelper.putJDialogBaseToFlag(flag, fm);
						fm.setVisible(true);
					} else {
						fm.setVisibleNoChange(true);
						JFrameBaseHelper.deiconify(fm);
					}

				} else if (rbHandworkBatchCorresponding.isSelected()) {// 手工批量对应
					//
					// check authority
					//
					
					String flag = rbHandworkBatchCorresponding
							.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgHandworkBatchCorresponding();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbBillCorrespondingSearch.isSelected()) {// 单据对应查询

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check7ByBrower(new Request(
							CommonVars.getCurrUser()));

					String flag = rbBillCorrespondingSearch.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgBillCorrespondingSearch();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbPkCorr.isSelected()) {// 单据对应(PK单对应)

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check4ByBrower(new Request(
							CommonVars.getCurrUser()));
					String flag = rbPkCorr.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgBillCorresByPk();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbBillMakeCustomNum.isSelected()) {// 生成单据的折算报关数量

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check8ByBrower(new Request(
							CommonVars.getCurrUser()));

					String flag = rbBillMakeCustomNum.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgBillMakeCustomNum();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}

				} else if (rbHalfProductManager.isSelected()) {// 半成品转料件

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.check9ByBrower(new Request(
							CommonVars.getCurrUser()));
					String flag = rbHalfProductManager.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgHalfProductManager();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				} else if (rbBatchUpdateHs.isSelected()) {// 批量修改料号对应的报关商品资料

					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
					.getApplicationContext().getBean(
							"erpBillCenterAuthority");
			erpBillCenterAuthority.check5ByBrower(new Request(
					CommonVars.getCurrUser()));


					String flag = rbBatchUpdateHs.getActionCommand();
					JDialogBase dialog = JDialogBaseHelper
							.getJDialogBaseByFlag(flag);
					if (dialog == null) {
						dialog = new DgBatchUpdateHs();
						JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
						dialog.setVisible(true);
					} else {
						dialog.setVisibleNoChange(true);
					}
				}

			} finally {
				component.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

		}
	}

	/**
	 * This method initializes rbReportBills
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbReportBills() {
		if (rbReportBills == null) {
			rbReportBills = new JRadioButton();
			rbReportBills.setBounds(new Rectangle(360, 47, 204, 20));
			rbReportBills.setText("批量打印单据");
			rbReportBills
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (rbReportBills.isSelected()) {
								DgBillReport dg = new DgBillReport();
								dg.setVisible(true);
							}
						}
					});
		}
		return rbReportBills;
	}

	/**
	 * This method initializes rbFinanceBatchWriteAccount
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbFinanceBatchWriteAccount() {
		if (rbFinanceBatchWriteAccount == null) {
			rbFinanceBatchWriteAccount = new JRadioButton();
			rbFinanceBatchWriteAccount
					.setBounds(new Rectangle(360, 21, 103, 26));
			rbFinanceBatchWriteAccount.setText("财务成批记帐");
			rbFinanceBatchWriteAccount.setOpaque(false);
		}
		return rbFinanceBatchWriteAccount;
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
			jPanel3.setBounds(new Rectangle(28, 163, 654, 94));
			jPanel3.setOpaque(false);
			jPanel3.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u5355\u636e\u5bf9\u5e94",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), Color.black));
			jPanel3.add(getRbBillCorresponding(), null);
			jPanel3.add(getRbHandworkBatchCorresponding(), null);
			jPanel3.add(getRbBillCorrespondingSearch(), null);
			jPanel3.add(getRbPkCorr(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes rbBillCorresponding
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBillCorresponding() {
		if (rbBillCorresponding == null) {
			rbBillCorresponding = new JRadioButton();
			rbBillCorresponding.setBounds(new Rectangle(28, 29, 102, 20));
			rbBillCorresponding.setOpaque(false);
			rbBillCorresponding.setText("单据对应");
			rbBillCorresponding.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		return rbBillCorresponding;
	}

	/**
	 * This method initializes rbHandworkBatchCorresponding
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbHandworkBatchCorresponding() {
		if (rbHandworkBatchCorresponding == null) {
			rbHandworkBatchCorresponding = new JRadioButton();
			rbHandworkBatchCorresponding.setBounds(new java.awt.Rectangle(360,
					30, 113, 20));
			rbHandworkBatchCorresponding.setOpaque(false);
			rbHandworkBatchCorresponding.setText("手工批量对应");
			rbHandworkBatchCorresponding.setCursor(new Cursor(
					Cursor.HAND_CURSOR));
		}
		return rbHandworkBatchCorresponding;
	}

	/**
	 * This method initializes rbBillCorrespondingSearch
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBillCorrespondingSearch() {
		if (rbBillCorrespondingSearch == null) {
			rbBillCorrespondingSearch = new JRadioButton();
			rbBillCorrespondingSearch.setBounds(new Rectangle(28, 57, 163, 20));
			rbBillCorrespondingSearch.setOpaque(false);
			rbBillCorrespondingSearch.setText("报关单和单据对应查询");
			rbBillCorrespondingSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		return rbBillCorrespondingSearch;
	}

	/**
	 * This method initializes rbPkCorr
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbPkCorr() {
		if (rbPkCorr == null) {
			rbPkCorr = new JRadioButton();
			rbPkCorr.setBounds(new java.awt.Rectangle(360, 58, 194, 21));
			rbPkCorr.setOpaque(false);
			rbPkCorr.setText("单据对应（PK单对应）");
			rbPkCorr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		return rbPkCorr;
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
			jPanel4.setBounds(new Rectangle(28, 290, 654, 89));
			jPanel4.setOpaque(false);
			jPanel4.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED), "\u5176\u5b83",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), null));
			jPanel4.add(getRbBillMakeCustomNum(), null);
			jPanel4.add(getRbHalfProductManager(), null);
			jPanel4.add(getBtnUpdate(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes rbBillMakeCustomNum
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBillMakeCustomNum() {
		if (rbBillMakeCustomNum == null) {
			rbBillMakeCustomNum = new JRadioButton();
			rbBillMakeCustomNum.setBounds(new Rectangle(28, 20, 171, 23));
			rbBillMakeCustomNum.setOpaque(false);
			rbBillMakeCustomNum.setText("生成单据的折算报关数量");
			rbBillMakeCustomNum.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		return rbBillMakeCustomNum;
	}

	/**
	 * This method initializes rbHalfProductManager
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbHalfProductManager() {
		if (rbHalfProductManager == null) {
			rbHalfProductManager = new JRadioButton();
			rbHalfProductManager.setBounds(new Rectangle(360, 20, 137, 23));
			rbHalfProductManager.setText("半成品委外管理");
		}
		return rbHalfProductManager;
	}

	/**
	 * 设置事件状态
	 * 
	 */
	private void setState() {
		if (CasCommonVars.getBillCorrespondingControl().getIsHandContrl() != null
				&& CasCommonVars.getBillCorrespondingControl()
						.getIsHandContrl() == true) {
			rbBillCorresponding.setEnabled(false);
			rbHandworkBatchCorresponding.setEnabled(true);
			rbPkCorr.setEnabled(true);
		} else {
			rbBillCorresponding.setEnabled(true);
			rbHandworkBatchCorresponding.setEnabled(false);
			rbPkCorr.setEnabled(false);
		}
	}

	/**
	 * This method initializes rbBatchUpdateHs
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBatchUpdateHs() {
		if (rbBatchUpdateHs == null) {
			rbBatchUpdateHs = new JRadioButton();
			rbBatchUpdateHs.setBounds(new Rectangle(27, 73, 270, 18));
			rbBatchUpdateHs.setOpaque(false);
			rbBatchUpdateHs.setText("批量修改\"工厂商品资料\"对应报关商品资料");
			rbBatchUpdateHs.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		return rbBatchUpdateHs;
	}

	/**
	 * This method initializes btnUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton();
			btnUpdate.setBounds(new Rectangle(34, 51, 141, 26));
			btnUpdate.setText("更新手册号至单据");

			String toolTipText = "因老程序【单据文本导入】时未将手册号导入,此按钮将会自动更新单据中的手册号\n"
					+ "(为避免出错，请在更新前备份数据库！)";

			btnUpdate.setToolTipText(toolTipText);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(FmSpecificControl.this,
							"此功能是将 【单据文本导入】的单据可能未填入手册号的情况,更新" + " \n"
									+ "相应单据,使其填入相应手册号,请谨慎使用!\n" + "\n 是否确认更新？",
							"注意 ！！！", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}

					new UpdateEmsNoThread().start();

				}
			});
		}
		return btnUpdate;
	}

	/**
	 * 更新手册号线程 （依照料号、报关编码、报关名称、报关规格、报关单位）
	 * 
	 * @author wss
	 */
	class UpdateEmsNoThread extends Thread {
		public void run() {

			try {
				List infos = new ArrayList();
				String info = "";
				long beginTime = System.currentTimeMillis();

				CommonStepProgress.showStepProgressDialog(null);
				CommonStepProgress.setStepMessage("系统正在进行更新，请稍后...");

				// wss****************************穿上方法是一起的*********************************************以下

				CommonStepProgress.setStepProgressMaximum(4);

				CommonStepProgress.setStepProgressValue(1);
				CommonStepProgress
						.setStepMessage("正在更新 【料件】类别的对应关系的手册号更新至相应单据....");
				casAction.updateBillDetailEmsNo(new Request(CommonVars
						.getCurrUser()), MaterielType.MATERIEL);

				CommonStepProgress.setStepProgressValue(2);
				CommonStepProgress
						.setStepMessage("正在更新 【成品】类别的对应关系的手册号更新至相应单据....");
				casAction.updateBillDetailEmsNo(new Request(CommonVars
						.getCurrUser()), MaterielType.FINISHED_PRODUCT);

				CommonStepProgress.setStepProgressValue(3);
				CommonStepProgress
						.setStepMessage("正在更新 【边角料】类别的对应关系的手册号更新至相应单据....");
				casAction.updateBillDetailEmsNo(new Request(CommonVars
						.getCurrUser()), MaterielType.REMAIN_MATERIEL);

				CommonStepProgress.setStepProgressValue(4);
				CommonStepProgress
						.setStepMessage("正在更新 【设备】类别的对应关系的手册号更新至相应单据....");
				casAction.updateBillDetailEmsNo(new Request(CommonVars
						.getCurrUser()), MaterielType.MACHINE);

				// wss******************************************************************************************************以上

				// //料件的
				// List listM = casAction.findStatCusNameRelationHsnInUsed(
				// new Request(CommonVars.getCurrUser(),true),
				// MaterielType.MATERIEL);
				//				
				// //成品的
				// List listF = casAction.findStatCusNameRelationHsnInUsed(
				// new Request(CommonVars.getCurrUser(),true),
				// MaterielType.FINISHED_PRODUCT);
				//				
				// //边角料
				// List listBJL = casAction.findStatCusNameRelationHsnInUsed(
				// new Request(CommonVars.getCurrUser(),true),
				// MaterielType.REMAIN_MATERIEL);
				//				
				// //设备
				// List listMachine =
				// casAction.findStatCusNameRelationHsnInUsed(
				// new Request(CommonVars.getCurrUser(),true),
				// MaterielType.MACHINE);
				//				
				//				
				//				
				// CommonStepProgress.setStepProgressMaximum(listM.size()
				// + listF.size()
				// + listBJL.size()
				// + listMachine.size());
				//				
				//				
				// //料件的
				// System.out.println("wss listM.size = " + listM.size());
				// for(int i=0;i<listM.size();i++){
				//					
				// StatCusNameRelationHsn s =
				// (StatCusNameRelationHsn)listM.get(i);
				// CommonStepProgress.setStepMessage(
				// "正在更新 【料件】类别的对应关系的手册号更新至相应单据，第" + i + " 条,共" + listM.size() +
				// "条" );
				// // + "\n其料号为：" + s.getComplex().getCode()
				// // + "\n报关名称为：" + s.getCusName()
				// // + "\n报关规格为：" + s.getCusSpec()
				// // + "\n报关单位为：" + s.getCusUnit().getName()
				// // + "\n手册号为：" + s.getEmsNo());
				//					
				// CommonStepProgress.setStepProgressValue(i + 1);
				//					
				// casAction.updateEmsNoToBillDetail(new
				// Request(CommonVars.getCurrUser()),s,MaterielType.MATERIEL);
				//					
				// }
				//				
				// //成品的
				// System.out.println("wss listF.size = " + listF.size());
				// for(int i=0;i<listF.size();i++){
				//					
				// StatCusNameRelationHsn s =
				// (StatCusNameRelationHsn)listF.get(i);
				// CommonStepProgress.setStepMessage(
				// "正在更新 【成品】类别的对应关系的手册号更新至相应单据，为第" + i + " 条,共" + listF.size()
				// + "条" );
				// // + "\n其料号为：" + s.getComplex().getCode()
				// // + "\n报关名称为：" + s.getCusName()
				// // + "\n报关规格为：" + s.getCusSpec()
				// // + "\n报关单位为：" + s.getCusUnit().getName()
				// // + "\n手册号为：" + s.getEmsNo());
				//					
				// CommonStepProgress.setStepProgressValue(i + 1);
				//					
				// casAction.updateEmsNoToBillDetail(new
				// Request(CommonVars.getCurrUser()),s,MaterielType.FINISHED_PRODUCT);
				//					
				// }
				//				
				// //边角料
				// System.out.println("wss listBJL.size = " + listBJL.size());
				// for(int i=0;i<listBJL.size();i++){
				//					
				// StatCusNameRelationHsn s =
				// (StatCusNameRelationHsn)listBJL.get(i);
				// CommonStepProgress.setStepMessage(
				// "正在更新 【边角料】类别的对应关系的手册号更新至相应单据，为第" + i + " 条,共" +
				// listBJL.size() + "条" );
				// // + "\n其料号为：" + s.getComplex().getCode()
				// // + "\n报关名称为：" + s.getCusName()
				// // + "\n报关规格为：" + s.getCusSpec()
				// // + "\n报关单位为：" + s.getCusUnit().getName()
				// // + "\n手册号为：" + s.getEmsNo());
				//					
				// CommonStepProgress.setStepProgressValue(i + 1);
				//					
				// casAction.updateEmsNoToBillDetail(new
				// Request(CommonVars.getCurrUser()),s,MaterielType.REMAIN_MATERIEL);
				//					
				// }
				//				
				// //设备
				// System.out.println("wss listMachine.size = " +
				// listMachine.size());
				// for(int i=0;i<listMachine.size();i++){
				//					
				// StatCusNameRelationHsn s =
				// (StatCusNameRelationHsn)listMachine.get(i);
				// CommonStepProgress.setStepMessage(
				// "正在更新 【设备】类别的对应关系的手册号更新至相应单据，为第" + i + " 条,共" +
				// listMachine.size() + "条" );
				// // + "\n其料号为：" + s.getComplex().getCode()
				// // + "\n报关名称为：" + s.getCusName()
				// // + "\n报关规格为：" + s.getCusSpec()
				// // + "\n报关单位为：" + s.getCusUnit().getName()
				// // + "\n手册号为：" + s.getEmsNo());
				//					
				// CommonStepProgress.setStepProgressValue(i + 1);
				//					
				// casAction.updateEmsNoToBillDetail(new
				// Request(CommonVars.getCurrUser()), s,MaterielType.MACHINE);
				//					
				// }

				CommonStepProgress.closeStepProgressDialog();

				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(FmSpecificControl.this, info,
						"提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmSpecificControl.this, "更新失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();

			}
		}
	}

}