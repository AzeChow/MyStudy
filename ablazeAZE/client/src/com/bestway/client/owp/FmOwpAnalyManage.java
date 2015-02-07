/*
 * Created on 2010-9-06
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.owp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.cas.authority.CasCustomReportAction;
import com.bestway.bcus.client.cas.DgConsignQuery;
import com.bestway.bcus.client.cas.DgImpExpQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 外发加工统计分析主界面
 * 
 * @author hcl
 * check by hcl 2010-10-08
 * 
 */
public class FmOwpAnalyManage extends JInternalFrameBase {
	/**
	 * 主界面面板
	 */
	private javax.swing.JPanel pnThis = null;
	/**
	 * 标题面板
	 */
	private JPanel pnTitle = null;
	/**
	 * 主要显示面板
	 */
	private JPanel pnMain = null;

	/**
	 * 退出按钮
	 */
	private JButton btnExit = null;
	/**
	 * 明细表面板
	 */
	private JPanel pnDetail = null;
	/**
	 * 库存表面板
	 */
	private JPanel pnStock = null;
	/**
	 * 返回折料面板
	 */
	private JPanel pnReturnBom = null;
	/**
	 * 集合面板
	 */
	private JPanel pnMAll = null;
	/**
	 * 按钮组
	 */
	private ButtonGroup group = null; // @jve:decl-index=0:
	/**
	 * 申请外发加工发货明细表按钮
	 */
	private JRadioButton rbOwpApplyOutGoods = null;
	/**
	 * 申请外发加工收货明细表按钮
	 */
	private JRadioButton rbOwpApplyInGoods = null;
	/**
	 * 外发加工发货明细表按钮
	 */
	private JRadioButton rbOwpOutGoods = null;
	/**
	 * 外发加工收货明细表按钮
	 */
	private JRadioButton rbOwpInGoods = null;
	/**
	 *外发加工库存汇总表按钮
	 */
	private JRadioButton rbOwpStock = null;
	/**
	 *返回折料表按钮
	 */
	private JRadioButton rbReturnToBom = null;

	/**
	 * 构造函数 
	 */
	public FmOwpAnalyManage() {
		super();
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * 初始化方法  
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("统计分析");
		this.setSize(811, 637);
		this.setVisible(true);
		this.setContentPane(getPnThis());
		group = new ButtonGroup();
		group.add(rbOwpApplyInGoods);
		group.add(rbOwpApplyOutGoods);
		group.add(rbOwpInGoods);
		group.add(rbOwpOutGoods);
		group.add(rbOwpStock);
		group.add(rbReturnToBom);

	}

	/**
	 * 获取pnThis面板
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getPnThis() {
		if (pnThis == null) {
			pnThis = new javax.swing.JPanel();
			pnThis.setLayout(new java.awt.BorderLayout());
			pnThis
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							18));
			pnThis.add(getPnTitle(), java.awt.BorderLayout.NORTH);
			pnThis.add(getPnMain(), java.awt.BorderLayout.CENTER);
		}
		return pnThis;
	}

	/**
	 * 
	 * 获取pnTitle面板
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnTitle() {
		if (pnTitle == null) {
			javax.swing.JLabel lb1 = new JLabel();

			javax.swing.JLabel lb2 = new JLabel();

			javax.swing.JLabel lb3 = new JLabel();

			pnTitle = new JPanel();
			pnTitle.setLayout(new BorderLayout());
			lb3.setText("统计分析");
			lb3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			lb3.setForeground(new java.awt.Color(255, 153, 0));
			pnTitle.setBackground(java.awt.Color.white);
			pnTitle
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pnTitle
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							18));
			lb2.setText("");
			lb2.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			lb1.setText("");
			lb1
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			pnTitle.add(lb3, java.awt.BorderLayout.CENTER);
			pnTitle.add(lb2, java.awt.BorderLayout.EAST);
			pnTitle.add(lb1, java.awt.BorderLayout.WEST);
		}
		return pnTitle;
	}

	/**
	 * 
	 * 
	 * 获取pnMain面板
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(getBtnExit(), null);
			pnMain.add(getPnMAll(), null);
		}
		return pnMain;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		btnExit.setBounds(this.pnMain.getWidth() - 92,
				this.pnMain.getHeight() - 40, 68, 26);
		pnMAll.setBounds(10, 10, this.pnMain.getWidth() - 80, this.pnMain
				.getHeight() - 60);
		pnDetail.setBounds(35, 20, this.pnMain.getWidth() - 120, 90);
		pnStock.setBounds(35, 120, this.pnMain.getWidth() - 120, 90);
		pnReturnBom.setBounds(35, 230, this.pnMain.getWidth() - 120, 90);

	}
	/**
	 * 绘制组件方法
	 */
	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * 获取关闭按钮
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setLocation(new Point(650, 470));
			btnExit.setSize(new Dimension(68, 26));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 *获取pnDetail面板
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnDetail() {
		if (pnDetail == null) {
			pnDetail = new JPanel();
			pnDetail.setLayout(null);
			pnDetail.setBorder(BorderFactory.createTitledBorder(null,
					"\u660e\u7ec6\u8868", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnDetail.setLocation(new Point(10, 28));
			pnDetail.setSize(new Dimension(650, 80));
			pnDetail.add(getRbOwpApplyOutGoods(), null);
			pnDetail.add(getRbOwpApplyInGoods(), null);
			pnDetail.add(getRbOwpOutGoods(), null);
			pnDetail.add(getRbOwpInGoods(), null);
		}
		return pnDetail;
	}

	/**
	 * 获取pnStock面板
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnStock() {
		if (pnStock == null) {
			pnStock = new JPanel();
			pnStock.setLayout(null);
			pnStock.setBorder(BorderFactory.createTitledBorder(null,
					"\u5e93\u5b58\u8868", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnStock.setSize(new Dimension(650, 84));
			pnStock.setLocation(new Point(9, 137));
			pnStock.add(getRbOwpStock(), null);
		}
		return pnStock;
	}

	/**
	 * 获取pnReturnBom面板
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnReturnBom() {
		if (pnReturnBom == null) {
			pnReturnBom = new JPanel();
			pnReturnBom.setLayout(null);
			pnReturnBom.setBorder(BorderFactory.createTitledBorder(null,
					"\u6298\u6599\u60c5\u51b5\u8868",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnReturnBom.setLocation(new Point(11, 225));
			pnReturnBom.setSize(new Dimension(650, 110));
			pnReturnBom.add(getRbReturnToBom(), null);
		}
		return pnReturnBom;
	}

	/**
	 * 获取pnMAll面板
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMAll() {
		if (pnMAll == null) {
			pnMAll = new JPanel();
			pnMAll.setLayout(null);
			pnMAll.setBorder(BorderFactory.createTitledBorder(null,
					"\u7edf\u8ba1\u62a5\u8868",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 14), Color.blue));
			pnMAll.setLocation(new Point(30, 30));
			pnMAll.setSize(new Dimension(743, 492));
			pnMAll.add(getPnDetail(), null);
			pnMAll.add(getPnStock(), null);
			pnMAll.add(getPnReturnBom(), null);
		}
		return pnMAll;
	}

	/**
	 * 获取申请外发加工发货明细表按钮
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOwpApplyOutGoods() {
		if (rbOwpApplyOutGoods == null) {
			rbOwpApplyOutGoods = new JRadioButton();
			rbOwpApplyOutGoods.setBounds(new Rectangle(22, 26, 183, 21));
			rbOwpApplyOutGoods.setText("申请外发加工发货明细表");
			rbOwpApplyOutGoods
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgOwpApplyOutGoods dg = new DgOwpApplyOutGoods();
							dg.setVisible(true);
						}
					});
		}
		return rbOwpApplyOutGoods;
	}

	/**
	 * 获取申请外发加工收货明细表按钮
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOwpApplyInGoods() {
		if (rbOwpApplyInGoods == null) {
			rbOwpApplyInGoods = new JRadioButton();
			rbOwpApplyInGoods.setBounds(new Rectangle(356, 26, 183, 21));
			rbOwpApplyInGoods.setText("申请外发加工收货明细表");
			rbOwpApplyInGoods
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgOwpApplyInGoods dg = new DgOwpApplyInGoods();
							dg.setVisible(true);
						}
					});
		}
		return rbOwpApplyInGoods;
	}

	/**
	 * 获取外发加工发货明细表按钮
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOwpOutGoods() {
		if (rbOwpOutGoods == null) {
			rbOwpOutGoods = new JRadioButton();
			rbOwpOutGoods.setBounds(new Rectangle(22, 52, 183, 21));
			rbOwpOutGoods.setText("外发加工发货明细表");
			rbOwpOutGoods
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgOwpOutGoods dg = new DgOwpOutGoods();
							dg.setVisible(true);
						}
					});
		}
		return rbOwpOutGoods;
	}

	/**
	 * 获取外发加工收货明细表
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOwpInGoods() {
		if (rbOwpInGoods == null) {
			rbOwpInGoods = new JRadioButton();
			rbOwpInGoods.setBounds(new Rectangle(356, 52, 183, 21));
			rbOwpInGoods.setText("外发加工收货明细表");
			rbOwpInGoods.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpInGoods dg = new DgOwpInGoods();
					dg.setVisible(true);
				}
			});
		}
		return rbOwpInGoods;
	}

	/**
	 * 获取外发加工库存汇总表
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOwpStock() {
		if (rbOwpStock == null) {
			rbOwpStock = new JRadioButton();
			rbOwpStock.setText("外发加工库存汇总表（按工厂单据统计）");
			rbOwpStock.setLocation(new Point(22, 37));
			rbOwpStock.setSize(new Dimension(319, 21));
			rbOwpStock.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpStock dg = new DgOwpStock();
					dg.setVisible(true);
				}
			});
		}
		return rbOwpStock;
	}

	/**
	 * 获取返回折料情况表按钮
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbReturnToBom() {
		if (rbReturnToBom == null) {
			rbReturnToBom = new JRadioButton();
			rbReturnToBom.setText("返回折料情况表（按工厂单据统计）");
			rbReturnToBom.setLocation(new Point(22, 40));
			rbReturnToBom.setSize(new Dimension(255, 21));
			rbReturnToBom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgOwpReturnToBom dg = new DgOwpReturnToBom();
							dg.setVisible(true);
						}
					});
		}
		return rbReturnToBom;
	}
} // @jve:decl-index=0:visual-constraint="10,10"