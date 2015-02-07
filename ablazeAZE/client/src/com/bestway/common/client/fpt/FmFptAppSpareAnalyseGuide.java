package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.cas.authority.CasOtherReportAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmFptAppSpareAnalyseGuide extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

//	private JRadioButton rbBalanceInfo = null;

	private JPanel jPanel2 = null;

	private ButtonGroup group = new ButtonGroup();

	private JPanel PnBottom = null;

	private JButton btnExit = null;

	private CasOtherReportAction casOtherReportAction = null;
	
	private FptManageAction fptManageAction=null;

	private JRadioButton rbBalanceInfo2 = null;

	private JPanel jPanel21 = null;

//	private JRadioButton rbBalanceInfo1 = null;

//	private JRadioButton rbBalanceInfo21 = null;

	private JRadioButton rbBalanceInfo211 = null;
	
	private JRadioButton radioFptApplySurplus = null;
	
	private JRadioButton radioFptApplyDifference = null;

	/**
	 * This is the default constructor
	 */
	public FmFptAppSpareAnalyseGuide() {
		super();
		casOtherReportAction = (CasOtherReportAction) CommonVars
				.getApplicationContext().getBean("casOtherReportAction");
		fptManageAction=(FptManageAction) CommonVars
		.getApplicationContext().getBean("fptManageAction");
		//获得进入权限
		fptManageAction.permissionCheckGetGuide(new Request(CommonVars.getCurrUser()));
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
		this.setTitle("统计报表");
		this.setSize(710, 517);
		this.setContentPane(getJContentPane());
		this
		.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

			@Override
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				if (!LicenseClient
						.getInstance(
								CommonVars.getCurrUser().getCompany()
										.getName())
						.checkFptManagePermisson()) {
					JOptionPane.showMessageDialog(FmFptAppSpareAnalyseGuide.this,
							"没有使用申请单余量分析报表的权限，如果需要请联系百思维！");
					doDefaultCloseAction();
				}
			}
		});
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
			jLabel.setText("申请单余量分析报表");
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
			jPanel1.add(getJPanel21(), null);
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
//	private JRadioButton getJRadioButton() {
//		if (rbBalanceInfo == null) {
//			rbBalanceInfo = new JRadioButton();
//			rbBalanceInfo.setText("转厂申请表余量分析 ");
//			rbBalanceInfo.setBounds(151, 17, 367, 26);
//		}
//		return rbBalanceInfo;
//	}

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
			jPanel2.setBounds(22, 22, 638, 143);
			jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "\u7533\u8bf7\u5355\u5206\u6790\u62a5\u8868", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), Color.black));
//			jPanel2.add(getJRadioButton(), null);
//			jPanel2.add(getRbBalanceInfo2(), null);
			jPanel2.add(getRadioFptApplySurplus());
			jPanel2.add(getRadioFptApplyDifference());
			jPanel2.add(getRadioFptApplyDifference());
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
//		jPanel2.setBounds(18, 12, this.jPanel1.getWidth() - 608 - 608,
//				this.jPanel1.getHeight() - 98);
//		jPanel21.setBounds(28, 22, this.jPanel1.getWidth() - 18 - 18,
//				this.jPanel1.getHeight() - 98);
	}

	@Override
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
//				if (rbBalanceInfo.isSelected()) { // bcs,dzsc 申请表余量分析
//					FmFptAppSpareAnalyse fm = new FmFptAppSpareAnalyse();
//					ShowFormControl.showForm(fm);					
//				} else if (rbBalanceInfo2.isSelected()) { // bcus 申请表余量分析
//					FmFptAppSpareAnalyseDetail fm = new FmFptAppSpareAnalyseDetail();
//					ShowFormControl.showForm(fm);
//				}else 
				if (radioFptApplySurplus.isSelected()){// 转厂申请表收发货余量分析hyq
					FmFptApplySurplus fm = new FmFptApplySurplus();
					ShowFormControl.showForm(fm);
				}else if(radioFptApplyDifference.isSelected()){// 深加工结转手册余量分析hyq
					FmFptApplyDifference fm  = new FmFptApplyDifference();
					ShowFormControl.showForm(fm);
				}
//				else if (rbBalanceInfo1.isSelected()) { 
//					FmFptStatusStat fm = new FmFptStatusStat();
//					ShowFormControl.showForm(fm);						
//				} 
//				else if (rbBalanceInfo21.isSelected()) { 
//					FmFptImpExpStat fm = new FmFptImpExpStat();
//					ShowFormControl.showForm(fm);							
//				} 
				else if (rbBalanceInfo211.isSelected()){
					FmFptDetailStat fm = new FmFptDetailStat();
					ShowFormControl.showForm(fm);	
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
	 * This method initializes rbBalanceInfo2
	 * 
	 * @return javax.swing.JRadioButton
	 */
//	private JRadioButton getRbBalanceInfo2() {
//		if (rbBalanceInfo2 == null) {
//			rbBalanceInfo2 = new JRadioButton();
//			rbBalanceInfo2.setBounds(new Rectangle(151, 45, 378, 26));
//			rbBalanceInfo2.setText("转厂申请表余量明细分析");
//		}
//		return rbBalanceInfo2;
//	}

	/**
	 * This method initializes jPanel21	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			jPanel21 = new JPanel();
			jPanel21.setLayout(null);
			jPanel21.setBounds(new Rectangle(22, 175, 637, 128));
			jPanel21.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "转厂分析报表", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), Color.black));
//			jPanel21.add(getRbBalanceInfo1(), null);
//			jPanel21.add(getRbBalanceInfo21(), null);
			jPanel21.add(getRbBalanceInfo211(), null);
		}
		return jPanel21;
	}

	/**
	 * This method initializes rbBalanceInfo1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
//	private JRadioButton getRbBalanceInfo1() {
//		if (rbBalanceInfo1 == null) {
//			rbBalanceInfo1 = new JRadioButton();
//			rbBalanceInfo1.setBounds(new Rectangle(150, 17, 367, 26));
//			rbBalanceInfo1.setText("转厂进出货状况表");
//		}
//		return rbBalanceInfo1;
//	}

	/**
	 * This method initializes rbBalanceInfo21	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
//	private JRadioButton getRbBalanceInfo21() {
//		if (rbBalanceInfo21 == null) {
//			rbBalanceInfo21 = new JRadioButton();
//			rbBalanceInfo21.setBounds(new Rectangle(150, 73, 378, 26));
//			rbBalanceInfo21.setText("结转报关进出货明细表");
//		}
//		return rbBalanceInfo21;
//	}

	/**
	 * This method initializes rbBalanceInfo211	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbBalanceInfo211() {
		if (rbBalanceInfo211 == null) {
			rbBalanceInfo211 = new JRadioButton();
			rbBalanceInfo211.setBounds(new Rectangle(151, 20, 172, 26));
			rbBalanceInfo211.setText("深加工结转进出货明细表");
		}
		return rbBalanceInfo211;
	}

	private JRadioButton getRadioFptApplySurplus() {
		if (radioFptApplySurplus == null) {
			radioFptApplySurplus = new JRadioButton();
			radioFptApplySurplus.setText("深加工结转申请表收发货余量分析");
			radioFptApplySurplus.setBounds(new Rectangle(151, 45, 378, 26));
			radioFptApplySurplus.setBounds(151, 20, 378, 26);
		}
		return radioFptApplySurplus;
	}
	
	private JRadioButton getRadioFptApplyDifference() {
		if (radioFptApplyDifference == null) {
			radioFptApplyDifference = new JRadioButton();
			radioFptApplyDifference.setText("深加工结转申请表余量分析");
			radioFptApplyDifference.setBounds(new Rectangle(151, 45, 378, 26));
			radioFptApplyDifference.setBounds(151, 48, 378, 26);
		}
		return radioFptApplyDifference;
	}
}
