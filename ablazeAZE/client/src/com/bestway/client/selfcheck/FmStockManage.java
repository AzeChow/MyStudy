/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

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
 * 
 */
public class FmStockManage extends JInternalFrameBase {
	private javax.swing.JPanel jContentPane = null;

	private JPanel pnTitle = null;

	private JPanel pnMain = null;

	private JButton btnExit = null;

	private JPanel pnSubOne1 = null;

	private JRadioButton rbMaterialInOut = null;

	private JRadioButton rbMaterialThatDayBalance = null;

	private JRadioButton rbMaterialBalanceDifferent = null;

	private JPanel pnProduct = null;

	private JRadioButton rbSemiMaterialInOut = null;

	private JRadioButton rbSemiThatDayBalance = null;

	private JRadioButton rbSemiBalanceDifferent = null;

	private JPanel pnSemi = null;

	private JRadioButton rbProductInOut = null;

	private JRadioButton rbProductThatDayBalance = null;

	private JRadioButton rbProductBalanceDifferent = null;

	private JPanel pnBadProduct = null;

	private JRadioButton rbBadProductInOut = null;

	private JRadioButton rbBadProductThayDayBalance = null;

	private JRadioButton rbBadProductBalanceDiffernt = null;

	private JPanel pnMaterial = null;

	private JPanel pnOutSource = null;

	private JRadioButton rbOutSourceInOut = null;

	private JRadioButton rbOutSourceThatDayBalance = null;

	private JRadioButton rbOutSourceBalanceDifferent = null;

	private JRadioButton rbSemiConvert = null;

	private JRadioButton rbDocumentBOM = null;

	private JRadioButton rbProductBOM = null;

	private JRadioButton rbOutSourceConvert = null;

	private JRadioButton rbBadProcutBOM = null;

	private ButtonGroup group = null;  //  @jve:decl-index=0:

	private CasCustomReportAction casCustomReportAction = null;// @jve:decl-index=0:

	private JRadioButton rbProductThatDayBalanceBom = null;

	private JRadioButton rbBadProductThayDayBalanceBom = null;

	private JRadioButton rbsemiDocumentBOM = null;

	private JPanel pnRemain = null;

	private JRadioButton rbRemainInOut = null;

	private JRadioButton rbRemainThatDayBalance = null;

	private JRadioButton rbRemainBalanceDifferent = null;

	/**
	 * 构造函数 This is the default constructor
	 */
	public FmStockManage() {
		super();
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * 初始化方法 This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("库存分析");
		this.setSize(811, 637);
		this.setVisible(true);
		this.setContentPane(getJContentPane());
		group = new ButtonGroup();
		group.add(rbMaterialInOut);
		group.add(rbMaterialThatDayBalance);
		group.add(rbMaterialBalanceDifferent);
		group.add(rbSemiThatDayBalance);
		group.add(rbSemiBalanceDifferent);
		group.add(rbSemiMaterialInOut);
		group.add(rbSemiConvert);
		group.add(rbDocumentBOM);
		group.add(rbProductInOut);
		group.add(rbProductThatDayBalance);
		group.add(rbProductBalanceDifferent);
		group.add(rbProductBOM);
		group.add(rbBadProductInOut);
		group.add(rbBadProductThayDayBalance);
		group.add(rbBadProductBalanceDiffernt);
		group.add(rbBadProcutBOM);
		group.add(rbOutSourceInOut);
		group.add(rbOutSourceThatDayBalance);
		group.add(rbOutSourceBalanceDifferent);
		group.add(rbOutSourceConvert);
		group.add(rbProductThatDayBalanceBom);
		group.add(rbBadProductThayDayBalanceBom);
		group.add(rbsemiDocumentBOM);
		group.add(rbRemainInOut);
		group.add(rbRemainBalanceDifferent);
		group.add(rbRemainThatDayBalance);
		group.add(rbRemainInOut);
		casCustomReportAction = (CasCustomReportAction) CommonVars
				.getApplicationContext().getBean("casCustomReportAction");

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
			jLabel.setText("库存分析");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
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
			pnMain.add(getBtnExit(), null);
			pnMain.add(getPnMaterial(), null);
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
		//
		pnMaterial.setBounds(10, 10, this.pnMain.getWidth() - 80, this.pnMain
				.getHeight() - 60);
		pnSubOne1.setBounds(35, 20, this.pnMain.getWidth() - 120, 60);
		pnProduct.setBounds(35, 80, this.pnMain.getWidth() - 120, 90);
		pnSemi.setBounds(35, 170, this.pnMain.getWidth() - 120, 90);
		pnBadProduct.setBounds(35, 260, this.pnMain.getWidth() - 120, 90);
		pnOutSource.setBounds(35, 350, this.pnMain.getWidth() - 120, 90);
		pnRemain.setBounds(35, 450, this.pnMain.getWidth() - 120, 60);

	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * This method initializes jButton
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
	 * This method initializes pnSubOne1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSubOne1() {
		if (pnSubOne1 == null) {
			pnSubOne1 = new JPanel();
			pnSubOne1.setLayout(null);
			pnSubOne1.setBorder(BorderFactory.createTitledBorder(null,
					"原材料存货情况",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnSubOne1.setLocation(new Point(10, 20));
			pnSubOne1.setSize(new Dimension(650, 50));
			pnSubOne1.add(getRbMaterialInOut(), null);
			pnSubOne1.add(getRbMaterialThatDayBalance(), null);
			pnSubOne1.add(getRbMaterialBalanceDifferent(), null);
		}
		return pnSubOne1;
	}

	/**
	 * This method initializes rbMaterialInOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterialInOut() {
		if (rbMaterialInOut == null) {
			rbMaterialInOut = new JRadioButton();
			rbMaterialInOut.setText("原材料物料帐");
			rbMaterialInOut.setLocation(new Point(20, 22));
			rbMaterialInOut.setSize(new Dimension(200, 20));
			String toolTipText="记录原材料仓物流的流水帐";
			rbMaterialInOut.setToolTipText(toolTipText);
			rbMaterialInOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
						DgCheckSelfImpExpQuery dialog = new DgCheckSelfImpExpQuery();
						dialog.setTitle("原材料物料帐");
						dialog.setMaterialType(MaterielType.MATERIEL);
						dialog.setVisible(true);
				}
			});
			
		}
		return rbMaterialInOut;
	}


	/**
	 * This method initializes rbMaterialThatDayBalance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterialThatDayBalance() {
		if (rbMaterialThatDayBalance == null) {
			rbMaterialThatDayBalance = new JRadioButton();
			rbMaterialThatDayBalance.setText("原材料当日结存表");
			String toolTipText="汇总原材料仓物料结存数据";
			rbMaterialThatDayBalance.setToolTipText(toolTipText);
			rbMaterialThatDayBalance.setSize(new Dimension(200, 20));
			rbMaterialThatDayBalance.setLocation(new Point(250, 22));
			rbMaterialThatDayBalance.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMaterialThatDayBalance dg=new DgMaterialThatDayBalance();
					dg.setTitle("原材料当日结存表");
					dg.setMaterielType(MaterielType.MATERIEL);
					dg.setVisible(true);
				}
			});
		}
		return rbMaterialThatDayBalance;
	}

	/**
	 * This method initializes rbMaterialBalanceDifferent
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterialBalanceDifferent() {
		if (rbMaterialBalanceDifferent == null) {
			rbMaterialBalanceDifferent = new JRadioButton();
			rbMaterialBalanceDifferent.setText("原材料结存与盘点差额表");
			String toolTipText="通过导入原材料盘点与原材料物料帐进行对比";
			rbMaterialBalanceDifferent.setToolTipText(toolTipText);
			rbMaterialBalanceDifferent.setSize(new Dimension(200, 20));
			rbMaterialBalanceDifferent.setLocation(new Point(450, 22));
			rbMaterialBalanceDifferent
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgMaterialDifferent dg=new DgMaterialDifferent();
							dg.setTitle("原材料结存与盘点差额表");
							dg.setMaterielType(MaterielType.MATERIEL);
							dg.setVisible(true);
							}
					});
		}
		return rbMaterialBalanceDifferent;
	}

	/**
	 * This method initializes pnProduct
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnProduct() {
		if (pnProduct == null) {
			pnProduct = new JPanel();
			pnProduct.setLayout(null);
			pnProduct.setBorder(BorderFactory.createTitledBorder(null,
					"在产品存货情况",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnProduct.setSize(new Dimension(650, 73));
			pnProduct.setLocation(new Point(10, 75));
			pnProduct.add(getRbSemiThatDayBalance(), null);
			pnProduct.add(getRbSemiBalanceDifferent(), null);
			pnProduct.add(getRbSemiMaterialInOut(), null);
			pnProduct.add(getRbSemiConvert(), null);
			pnProduct.add(getRbDocumentBOM(), null);
			pnProduct.add(getRbsemiDocumentBOM(), null);
		}
		return pnProduct;
	}

	/**
	 * This method initializes rbSemiMaterialInOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSemiMaterialInOut() {
		if (rbSemiMaterialInOut == null) {
			rbSemiMaterialInOut = new JRadioButton();
			String toolTipText="记录车间物流的流水帐";
			rbSemiMaterialInOut.setToolTipText(toolTipText);
			rbSemiMaterialInOut.setText("在产品物料帐");
			rbSemiMaterialInOut.setLocation(new Point(20, 22));
			rbSemiMaterialInOut.setSize(new Dimension(200, 20));
			rbSemiMaterialInOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckSelfImpExpQuery dg=new DgCheckSelfImpExpQuery();
					dg.setTitle("在产品物料帐");
					dg.setMaterialType(MaterielType.SEMI_FINISHED_PRODUCT);
					dg.setVisible(true);
				}
				
				
			});
		}
		return rbSemiMaterialInOut;
	}

	/**
	 * This method initializes rbSemiThatDayBalance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSemiThatDayBalance() {
		if (rbSemiThatDayBalance == null) {
			rbSemiThatDayBalance = new JRadioButton();
			rbSemiThatDayBalance.setText("在产品当日结存表");
			String toolTipText="汇总车间物料的结存数据";
			rbSemiThatDayBalance.setToolTipText(toolTipText);
			rbSemiThatDayBalance.setSize(new Dimension(200, 20));
			rbSemiThatDayBalance.setLocation(new Point(250, 22));
			rbSemiThatDayBalance.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					DgSemiThatDayBlance dg= new DgSemiThatDayBlance();
//					dg.setMaterielType("currentTotal");
//					dg.setTitle("在产品当日结存表");
//					dg.setVisible(true);
					
//					DgMaterialThatDayBalance dg=new DgMaterialThatDayBalance();
//					dg.setTitle("在产品当日结存表");
//					dg.setMaterielType("currentTotal");
//					dg.setVisible(true);
					
					DgCurrentThatDayBalance dg = new DgCurrentThatDayBalance();
					dg.setVisible(true);
				}
			});
		}
		return rbSemiThatDayBalance;
	}

	/**
	 * This method initializes rbSemiBalanceDifferent
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSemiBalanceDifferent() {
		if (rbSemiBalanceDifferent == null) {
			rbSemiBalanceDifferent = new JRadioButton();
			rbSemiBalanceDifferent.setText("在产品结存与盘点差额表");
			String toolTipText="记录在产品单据折成原材料形态记帐的过程";
			rbSemiBalanceDifferent.setToolTipText(toolTipText);
			rbSemiBalanceDifferent.setSize(new Dimension(200, 20));
			rbSemiBalanceDifferent.setLocation(new Point(450, 22));
			rbSemiBalanceDifferent.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					DgSemiDifferent dg=new DgSemiDifferent();
//					dg.setTitle("在产品结存与盘点差额表");
//					dg.setVisible(true);
					
//					DgMaterialDifferent dg=new DgMaterialDifferent();
//					dg.setTitle("在产品结存与盘点差额表");
//					dg.setMaterielType("currentTotal");
//					dg.setVisible(true);
					
					DgCurrentDifferent dg = new DgCurrentDifferent();
					dg.setVisible(true);
//					
					}
			});
		}
		return rbSemiBalanceDifferent;
	}

	/**
	 * This method initializes pnSemi
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnSemi() {
		if (pnSemi == null) {
			pnSemi = new JPanel();
			pnSemi.setLayout(null);
			pnSemi.setBorder(BorderFactory.createTitledBorder(null,
					"产成品存货情况",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnSemi.setLocation(new Point(10, 150));
			pnSemi.setSize(new Dimension(650, 74));
			pnSemi.add(getRbProductInOut(), null);
			pnSemi.add(getRbProductThatDayBalance(), null);
			pnSemi.add(getRbProductBalanceDifferent(), null);
			pnSemi.add(getRbProductBOM(), null);
			pnSemi.add(getRbProductThatDayBalanceBom(), null);
		}
		return pnSemi;
	}

	/**
	 * This method initializes rbProductInOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductInOut() {
		if (rbProductInOut == null) {
			rbProductInOut = new JRadioButton();
			rbProductInOut.setText("产成品物料帐");
			String toolTipText="记录成品仓物流的流水帐";
			rbProductInOut.setToolTipText(toolTipText);
			rbProductInOut.setLocation(new Point(20, 22));
			rbProductInOut.setSize(new Dimension(200, 20));
			rbProductInOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					casCustomReportAction
					.checkAuthorityProductInOutByBrowse(new Request(
							CommonVars.getCurrUser()));

			String flag = rbProductInOut.getActionCommand();
			JDialogBase dialog = JDialogBaseHelper
					.getJDialogBaseByFlag(flag);
			if (dialog == null) {
				dialog = new DgCheckSelfImpExpQuery();
				
				
				dialog.setTitle("产成品物料帐");
				((DgCheckSelfImpExpQuery)dialog).setMaterialType(MaterielType.FINISHED_PRODUCT);
				JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
				dialog.setVisible(true);
			} else {
				dialog.setVisibleNoChange(true);
			}

				}
			});
		}
		return rbProductInOut;
	}

	/**
	 * This method initializes rbProductThatDayBalance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductThatDayBalance() {
		if (rbProductThatDayBalance == null) {
			rbProductThatDayBalance = new JRadioButton();
			rbProductThatDayBalance.setText("产成品当日结存表");
			String toolTipText="汇总成品仓库成品结存数据";
			rbProductThatDayBalance.setToolTipText(toolTipText);
			rbProductThatDayBalance.setSize(new Dimension(200, 20));
			rbProductThatDayBalance.setLocation(new Point(250, 22));
			rbProductThatDayBalance.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					DgProductThatDayBlance dg=new DgProductThatDayBlance();
//					dg.setTitle("产成品当日结存表");
//					dg.setVisible(true);
					
					DgMaterialThatDayBalance dg=new DgMaterialThatDayBalance();
					dg.setTitle("产成品当日结存表");
					dg.setMaterielType(MaterielType.FINISHED_PRODUCT);
					dg.setVisible(true);
				}
			});
		}
		return rbProductThatDayBalance;
	}

	/**
	 * This method initializes rbProductBalanceDifferent
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductBalanceDifferent() {
		if (rbProductBalanceDifferent == null) {
			rbProductBalanceDifferent = new JRadioButton();
			rbProductBalanceDifferent.setText("产成品结存与盘点差额表");
			String toolTipText="通过导入成品盘点与物料帐进行对比";
			rbProductBalanceDifferent.setToolTipText(toolTipText);
			rbProductBalanceDifferent.setSize(new Dimension(200, 20));
			rbProductBalanceDifferent.setLocation(new Point(450, 22));
			rbProductBalanceDifferent
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							//DgProductDifferent dg=new DgProductDifferent();
							//dg.setTitle("产成品结存与盘点差额表");
							//dg.setVisible(true);
							
							
							DgMaterialDifferent dg=new DgMaterialDifferent();
							dg.setTitle("产成品结存与盘点差额表");
							dg.setMaterielType(MaterielType.FINISHED_PRODUCT);
							dg.setVisible(true);
						}
					});
		}
		return rbProductBalanceDifferent;
	}

	/**
	 * This method initializes pnBadProduct
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnBadProduct() {
		if (pnBadProduct == null) {
			pnBadProduct = new JPanel();
			pnBadProduct.setLayout(null);
			pnBadProduct.setBorder(BorderFactory.createTitledBorder(null,
					"残次品存货情况",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnBadProduct.setLocation(new Point(10, 300));
			pnBadProduct.setSize(new Dimension(650, 75));
			pnBadProduct.add(getRbBadProductInOut(), null);
			pnBadProduct.add(getRbBadProductThayDayBalance(), null);
			pnBadProduct.add(getRbBadProductBalanceDiffernt(), null);
			pnBadProduct.add(getRbBadProcutBOM(), null);
			pnBadProduct.add(getRbBadProductThayDayBalanceBom(), null);
		}
		return pnBadProduct;
	}

	/**
	 * This method initializes rbBadProductInOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBadProductInOut() {
		if (rbBadProductInOut == null) {
			rbBadProductInOut = new JRadioButton();
			rbBadProductInOut.setText("残次品物料帐");
			String toolTipText="记录残次品物流的流水帐";
			rbBadProductInOut.setToolTipText(toolTipText);
			rbBadProductInOut.setLocation(new Point(20, 22));
			rbBadProductInOut.setSize(new Dimension(200, 20));
			rbBadProductInOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckSelfImpExpQuery dg=new DgCheckSelfImpExpQuery();
					dg.setTitle("残次品物料帐");
					dg.setMaterialType(MaterielType.BAD_PRODUCT);
					dg.setWaiFa(true);
					dg.setVisible(true);}});
		}
		return rbBadProductInOut;
	}

	/**
	 * This method initializes rbBadProductThayDayBalance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBadProductThayDayBalance() {
		if (rbBadProductThayDayBalance == null) {
			rbBadProductThayDayBalance = new JRadioButton();
			rbBadProductThayDayBalance.setText("残次品当日结存表");
			String toolTipText="汇总残次品仓结存的数据";
			rbBadProductThayDayBalance.setToolTipText(toolTipText);
			rbBadProductThayDayBalance.setSize(new Dimension(200, 20));
			rbBadProductThayDayBalance.setLocation(new Point(250, 22));
			rbBadProductThayDayBalance
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							DgBadproductThatDayBlance dg=new DgBadproductThatDayBlance();
//							dg.setTitle("残次品当日结存表");
//							dg.setVisible(true);
							
							DgMaterialThatDayBalance dg=new DgMaterialThatDayBalance();
							dg.setTitle("残次品当日结存表");
							dg.setMaterielType(MaterielType.BAD_PRODUCT);
							dg.setVisible(true);
						}
					});
		}
		return rbBadProductThayDayBalance;
	}

	/**
	 * This method initializes rbBadProductBalanceDiffernt
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBadProductBalanceDiffernt() {
		if (rbBadProductBalanceDiffernt == null) {
			rbBadProductBalanceDiffernt = new JRadioButton();
			rbBadProductBalanceDiffernt.setText("残次品结存与盘点差额表");
			String toolTipText="通过导入残次品盘点与物料帐进行对比";
			rbBadProductBalanceDiffernt.setToolTipText(toolTipText);
			rbBadProductBalanceDiffernt.setSize(new Dimension(200, 20));
			rbBadProductBalanceDiffernt.setLocation(new Point(450, 22));
			rbBadProductBalanceDiffernt
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							//DgBadproductDifferent dg=new DgBadproductDifferent();
							//dg.setTitle("残次品结存与盘点差额表");
							//dg.setVisible(true);
							
							DgMaterialDifferent dg=new DgMaterialDifferent();
							dg.setTitle("残次品结存与盘点差额表");
							dg.setMaterielType(MaterielType.BAD_PRODUCT);
							dg.setVisible(true);
							
						}
					});
		}
		return rbBadProductBalanceDiffernt;
	}

	/**
	 * This method initializes pnMaterial
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnMaterial() {
		if (pnMaterial == null) {
			pnMaterial = new JPanel();
			pnMaterial.setLayout(null);
			pnMaterial.setBorder(BorderFactory.createTitledBorder(null,
					"\u5e93\u5b58\u60c5\u51b5",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 14), Color.blue));
			pnMaterial.setLocation(new Point(30, 30));
			pnMaterial.setSize(new Dimension(743, 466));
			pnMaterial.add(getPnSubOne1(), null);
			pnMaterial.add(getPnProduct(), null);
			pnMaterial.add(getPnSemi(), null);
			pnMaterial.add(getPnBadProduct(), null);
			pnMaterial.add(getPnOutSource(), null);
			pnMaterial.add(getPnRemain(), null);
		}
		return pnMaterial;
	}

	/**
	 * This method initializes pnOutSource
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnOutSource() {
		if (pnOutSource == null) {
			pnOutSource = new JPanel();
			pnOutSource.setLayout(null);
			pnOutSource.setBorder(BorderFactory.createTitledBorder(null,
					"外发加工存货情况",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), Color.blue));
			pnOutSource.setSize(new Dimension(650, 60));
			pnOutSource.setLocation(new Point(10, 230));
			pnOutSource.add(getRbOutSourceInOut(), null);
			pnOutSource.add(getRbOutSourceThatDayBalance(), null);
			pnOutSource.add(getRbOutSourceBalanceDifferent(), null);
			pnOutSource.add(getRbOutSourceConvert(), null);
		}
		return pnOutSource;
	}

	/**
	 * This method initializes rbOutSourceInOut
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOutSourceInOut() {
		if (rbOutSourceInOut == null) {
			rbOutSourceInOut = new JRadioButton();
			rbOutSourceInOut.setText("外发加工物料帐");
			String toolTipText="记录外发加工物流的流水帐";
			rbOutSourceInOut.setToolTipText(toolTipText);
			rbOutSourceInOut.setLocation(new Point(20, 22));
			rbOutSourceInOut.setSize(new Dimension(200, 20));
			rbOutSourceInOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckSelfImpExpQuery dg=new DgCheckSelfImpExpQuery();
					dg.setTitle("外发加工物料帐");
					dg.lbCutomsName.setText("产商名称");
					dg.setMaterialType(MaterielType.SEMI_FINISHED_PRODUCT);
					dg.setWaiFa(true);
					dg.setVisible(true);
				}
			});
		}
		return rbOutSourceInOut;
	}

	/**
	 * This method initializes rbOutSourceThatDayBalance
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOutSourceThatDayBalance() {
		if (rbOutSourceThatDayBalance == null) {
			rbOutSourceThatDayBalance = new JRadioButton();
			rbOutSourceThatDayBalance.setText("外发加工当日结存表");
			String toolTipText="汇总外发加工物料的结存数据";
			rbOutSourceThatDayBalance.setToolTipText(toolTipText);
			rbOutSourceThatDayBalance.setSize(new Dimension(200, 20));
			rbOutSourceThatDayBalance.setLocation(new Point(250, 22));
			rbOutSourceThatDayBalance
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							DgOutsourceThatDayBlance dg =new DgOutsourceThatDayBlance();
//							dg.setTitle("外发加工当日结存表");
//						//	dg.setMaterielType("outSource");//外发加工
//							dg.setVisible(true);
							
							DgMaterialThatDayBalance dg=new DgMaterialThatDayBalance();
							dg.setTitle("外发加工当日结存表");
							dg.setMaterielType("outSource");//外发加工
							dg.setVisible(true);
						}
					});
		}
		return rbOutSourceThatDayBalance;
	}

	/**
	 * This method initializes rbOutSourceBalanceDifferent
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOutSourceBalanceDifferent() {
		if (rbOutSourceBalanceDifferent == null) {
			rbOutSourceBalanceDifferent = new JRadioButton();
			rbOutSourceBalanceDifferent.setText("外发加工结存与盘点差额表");
			String toolTipText="通过导入外发加工盘点与物料帐进行对比";
			rbOutSourceBalanceDifferent.setToolTipText(toolTipText);
			rbOutSourceBalanceDifferent.setSize(new Dimension(200, 20));
			rbOutSourceBalanceDifferent.setLocation(new Point(450, 22));
			rbOutSourceBalanceDifferent
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							DgOutsourceDifferent dg=new DgOutsourceDifferent();
//							dg.setTitle("外发加工结存与盘点差额表");
//							dg.setVisible(true);
							
							DgMaterialDifferent dg=new DgMaterialDifferent();
							dg.setTitle("外发加工结存与盘点差额表");
							dg.setMaterielType("outSource");
							dg.setVisible(true);
						}
					});
		}
		return rbOutSourceBalanceDifferent;
	}

	/**
	 * This method initializes rbSemiConvert
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSemiConvert() {
		if (rbSemiConvert == null) {
			rbSemiConvert = new JRadioButton();
			rbSemiConvert.setText("在产品折料情况（折单据）");
			String toolTipText="记录在产品单据折成原材料形态记帐的过程";
			rbSemiConvert.setToolTipText(toolTipText);
			rbSemiConvert.setLocation(new Point(20, 46));
			rbSemiConvert.setSize(new Dimension(200, 20));
			rbSemiConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSemiConvert dgSemiConvert = new DgSemiConvert();
					String SEMI_FINISHED_PRODUCT=MaterielType.SEMI_FINISHED_PRODUCT;
					dgSemiConvert.setMaterielType(SEMI_FINISHED_PRODUCT);
					dgSemiConvert.setTitle("在产品折料情况表");
					dgSemiConvert.setVisible(true);
				}
			});
		}
		return rbSemiConvert;
	}

	/**
	 * This method initializes rbDocumentBOM
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDocumentBOM() {
		if (rbDocumentBOM == null) {
			rbDocumentBOM = new JRadioButton();
			rbDocumentBOM.setText("制单耗用情况表");
			String toolTipText="反映每张工单的领用及使用情况";
			rbDocumentBOM.setToolTipText(toolTipText);
			rbDocumentBOM.setSize(new Dimension(124, 20));
			rbDocumentBOM.setLocation(new Point(451, 46));
			rbDocumentBOM.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgWorkOrderBOM dg = new DgWorkOrderBOM();
					dg.setTitle("制单耗用情况表");
					dg.setVisible(true);
				}
			});
		}
		return rbDocumentBOM;
	}

	/**
	 * This method initializes rbProductBOM
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProductBOM() {
		if (rbProductBOM == null) {
			rbProductBOM = new JRadioButton();
			rbProductBOM.setText("产成品折料情况（折单据）");
			String toolTipText="记录成品单据折成原材料形态记帐的过程";
			rbProductBOM.setToolTipText(toolTipText);
			rbProductBOM.setLocation(new Point(20, 46));
			rbProductBOM.setSize(new Dimension(200, 20));
			rbProductBOM.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgProductConvert chanPinZheLiao = new DgProductConvert();
					String FINISHED_PRODUCT=MaterielType.FINISHED_PRODUCT;
					chanPinZheLiao.setMaterielType(FINISHED_PRODUCT);
					chanPinZheLiao.setTitle("产成品折料情况表");
					chanPinZheLiao.setVisible(true);
				}
			});
		}
		return rbProductBOM;
	}

	/**
	 * This method initializes rbOutSourceConvert
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbOutSourceConvert() {
		if (rbOutSourceConvert == null) {
			rbOutSourceConvert = new JRadioButton();
			rbOutSourceConvert.setText("外发加工折料情况（折单据）");
			String toolTipText="记录外发加工单据折算成原材料的记帐过程";
			rbOutSourceConvert.setToolTipText(toolTipText);
			rbOutSourceConvert.setLocation(new Point(20, 46));
			rbOutSourceConvert.setSize(new Dimension(201, 21));
			rbOutSourceConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOutSourceConvert dg =new DgOutSourceConvert();
					String FINISHED_PRODUCT=MaterielType.FINISHED_PRODUCT;
					dg.setMaterielType(FINISHED_PRODUCT);
					dg.setTitle("外发加工折料情况");
					dg.setVisible(true);
				}
			});
		}
		return rbOutSourceConvert;
	}

	/**
	 * This method initializes rbBadProcutBOM
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbBadProcutBOM() {
		if (rbBadProcutBOM == null) {
			rbBadProcutBOM = new JRadioButton();
			rbBadProcutBOM.setText("残次品折料情况（折单据）");
			String toolTipText="将残次品折算成原材料的清单";
			rbBadProcutBOM.setToolTipText(toolTipText);
			rbBadProcutBOM.setSize(new Dimension(200, 20));
			rbBadProcutBOM.setLocation(new Point(20, 46));
			rbBadProcutBOM.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBadProductBOM dg = new DgBadProductBOM();
					String BAD_PRODUCT=MaterielType.BAD_PRODUCT;
					dg.setMaterielType(BAD_PRODUCT);
					dg.setTitle("残次品折料情况表");
					dg.setVisible(true);
				}
			});
		}
		return rbBadProcutBOM;
	}

	/**
	 * This method initializes rbProductThatDayBalanceBom	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbProductThatDayBalanceBom() {
		if (rbProductThatDayBalanceBom == null) {
			rbProductThatDayBalanceBom = new JRadioButton();
			rbProductThatDayBalanceBom.setBounds(new Rectangle(250, 46, 200, 20));
			rbProductThatDayBalanceBom.setText("产成品结存折料表（折盘点）");
			String toolTipText="将成品仓成品折算成原材料的清单";
			rbProductThatDayBalanceBom.setToolTipText(toolTipText);
			rbProductThatDayBalanceBom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							
							DgMaterialThatDayBalanceToBom dg=new DgMaterialThatDayBalanceToBom();
							dg.setMaterielType(MaterielType.FINISHED_PRODUCT);
							dg.setVisible(true);
						}
					});
		}
		return rbProductThatDayBalanceBom;
	}

	/**
	 * This method initializes rbBadProductThayDayBalanceBom	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbBadProductThayDayBalanceBom() {
		if (rbBadProductThayDayBalanceBom == null) {
			rbBadProductThayDayBalanceBom = new JRadioButton();
			rbBadProductThayDayBalanceBom.setBounds(new Rectangle(250, 45, 218, 26));
			rbBadProductThayDayBalanceBom.setText("残次品结存折料表（折盘点）");
			String toolTipText="将残次品仓结存折算成原材料的清单";
			rbBadProductThayDayBalanceBom.setToolTipText(toolTipText);
			rbBadProductThayDayBalanceBom
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBadProductThatDayBalanceToBom dg=new DgBadProductThatDayBalanceToBom();
					dg.setVisible(true);
				}
			});
		}
		return rbBadProductThayDayBalanceBom;
	}

	/**
	 * This method initializes rbsemiDocumentBOM	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbsemiDocumentBOM() {
		if (rbsemiDocumentBOM == null) {
			rbsemiDocumentBOM = new JRadioButton();
			rbsemiDocumentBOM.setBounds(new Rectangle(250, 45, 197, 26));
			rbsemiDocumentBOM.setText("在产品结存折料表（折盘点）");
			String toolTipText="将车间物料折算成原材料的清单";
			rbsemiDocumentBOM.setToolTipText(toolTipText);
			rbsemiDocumentBOM.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCurrentThatDayBalanceToBom dg = new DgCurrentThatDayBalanceToBom();
					dg.setVisible(true);
				}
			});
		}
		return rbsemiDocumentBOM;
	}

	/**
	 * This method initializes pnRemain	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnRemain() {
		if (pnRemain == null) {
			pnRemain = new JPanel();
			pnRemain.setLayout(null);
			pnRemain.setLocation(new Point(13, 387));
			pnRemain.setSize(new Dimension(650, 53));
			pnRemain.setBorder(BorderFactory.createTitledBorder(null, "\u8fb9\u89d2\u6599\u5b58\u8d27\u60c5\u51b5", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), Color.blue));
			pnRemain.add(getRbRemainlInOut(), null);
			pnRemain.add(getRbRemainThatDayBalance(), null);
			pnRemain.add(getRbRemainBalanceDifferent(), null);
		}
		return pnRemain;
	}

	/**
	 * This method initializes rbRemainlInOut	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbRemainlInOut() {
		if (rbRemainInOut == null) {
			rbRemainInOut = new JRadioButton();
			rbRemainInOut.setLocation(new Point(20, 22));
			rbRemainInOut.setText("边角料物料帐");
			String toolTipText="记录边角料物流的流水帐";
			rbRemainInOut.setToolTipText(toolTipText);
			rbRemainInOut.setSize(new Dimension(200, 20));
			rbRemainInOut.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
						DgCheckSelfImpExpQuery dialog = new DgCheckSelfImpExpQuery();
						dialog.setTitle("边角料物料帐");
						dialog.setMaterialType(MaterielType.REMAIN_MATERIEL);
						dialog.setVisible(true);
				}
			});
		}
		return rbRemainInOut;
	}

	/**
	 * This method initializes rbRemainThatDayBalance	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbRemainThatDayBalance() {
		if (rbRemainThatDayBalance == null) {
			rbRemainThatDayBalance = new JRadioButton();
			rbRemainThatDayBalance.setLocation(new Point(250, 22));
			rbRemainThatDayBalance.setText("边角料当日结存");
			String toolTipText="汇总边角料物料的结存数据";
			rbRemainThatDayBalance.setToolTipText(toolTipText);
			rbRemainThatDayBalance.setSize(new Dimension(200, 20));
			rbRemainThatDayBalance.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMaterialThatDayBalance dg=new DgMaterialThatDayBalance();
					dg.setTitle("边角料当日结存表");
					dg.setMaterielType(MaterielType.REMAIN_MATERIEL);
					dg.setVisible(true);
				}
			});
			
		}
		return rbRemainThatDayBalance;
	}

	/**
	 * This method initializes rbRemainBalanceDifferent	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbRemainBalanceDifferent() {
		if (rbRemainBalanceDifferent == null) {
			rbRemainBalanceDifferent = new JRadioButton();
			rbRemainBalanceDifferent.setLocation(new Point(450, 22));
			rbRemainBalanceDifferent.setText("边角料结存对比表");
			String toolTipText="计算边角料征税及对比情况";
			rbRemainBalanceDifferent.setToolTipText(toolTipText);
			rbRemainBalanceDifferent.setSize(new Dimension(200, 20));
			rbRemainBalanceDifferent.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgLeftoverMaterielStat d = new DgLeftoverMaterielStat();
					d.setVisible(true);
				}
			});
		}
		return rbRemainBalanceDifferent;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"