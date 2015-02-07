package com.bestway.client.selfcheck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.bestway.common.MaterielType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmTransferAnalyse extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JPanel pnTitle = null;
	private JPanel pnMain = null;
	private JRadioButton rbSupplierReceive = null;
	private JRadioButton rbSupplierTransfer = null;
	private JRadioButton rbSupplierEnvelop = null;
	private JRadioButton rbSupplierBalanceAll = null;
	private JRadioButton rbSupplierBalanceDetail = null;
	private JRadioButton rbClientSend = null;
	private JRadioButton rbClientTransfer = null;
	private JRadioButton rbClientEnvelop = null;
	private JRadioButton rbClientBalanceAll = null;
	private JRadioButton rbClientBalanceDetail = null;
	private ButtonGroup buttonGroup = null;  //  @jve:decl-index=0:
	private JPanel pnBottom = null;
	private JButton btnExit = null;
	private JRadioButton rbClientConvert = null;
	private JPanel pnCover = null;
	private JPanel pnUp = null;
	private JPanel pnDown = null;
	/**
	 * This method initializes 
	 * 
	 */
	public FmTransferAnalyse() {
		super();
		getButtonGroup();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(800, 692));
        this.setContentPane(getJContentPane());
			
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPnTitle(), BorderLayout.NORTH);
			jContentPane.add(getPnMain(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes pnTitle	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnTitle() {
		if (pnTitle == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			pnTitle = new JPanel();
			pnTitle.setLayout(new BorderLayout());
			jLabel.setText("    转厂分析");
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
			jLabel2.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			pnTitle.add(jLabel, java.awt.BorderLayout.CENTER);
			pnTitle.add(jLabel1, java.awt.BorderLayout.EAST);
			pnTitle.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return pnTitle;
	}

	/**
	 * This method initializes pnMain	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.add(getPnBottom(), null);
			pnMain.add(getBtnExit(), null);
			pnMain.add(getPnCover(), null);
		}
		return pnMain;
	}

	/**
	 * This method initializes rbSupplierReceive	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbSupplierReceive() {
		if (rbSupplierReceive == null) {
			rbSupplierReceive = new JRadioButton();
			rbSupplierReceive.setText("收货明细表");
			String toolTipText="记录供应商送货的明细";
			rbSupplierReceive.setToolTipText(toolTipText);
			rbSupplierReceive.setBounds(new Rectangle(57, 22, 121, 21));
			rbSupplierReceive.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRecvSendDetailInfo dg = new DgRecvSendDetailInfo();
					dg.setMaterielType(MaterielType.MATERIEL);
					dg.setTitle("收货明细表");
					dg.setVisible(true);
				}
			});
		}
		return rbSupplierReceive;
	}

	/**
	 * This method initializes rbSupplierTransfer	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbSupplierTransfer() {
		if (rbSupplierTransfer == null) {
			rbSupplierTransfer = new JRadioButton();
			rbSupplierTransfer.setText("结转明细表");
			String toolTipText="记录供应商结转报关单的明细";
			rbSupplierTransfer.setToolTipText(toolTipText);
			rbSupplierTransfer.setBounds(new Rectangle(265, 22, 117, 21));
			rbSupplierTransfer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTransferDetailInfo dg = new DgTransferDetailInfo();
					dg.setIsImport(true);
					dg.setTitle("料件转厂结转明细表");
					dg.setVisible(true);
				}
			});
		}
		return rbSupplierTransfer;
	}

	/**
	 * This method initializes rbSupplierEnvelop	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbSupplierEnvelop() {
		if (rbSupplierEnvelop == null) {
			rbSupplierEnvelop = new JRadioButton();
			rbSupplierEnvelop.setText("(供货商)关封明细表");
			String toolTipText="记录供应商关封的明细";
			rbSupplierEnvelop.setToolTipText(toolTipText);
			rbSupplierEnvelop.setBounds(new Rectangle(456, 22, 151, 21));
			rbSupplierEnvelop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierEnvelopDetailInfo dg = new DgSupplierEnvelopDetailInfo();
					dg.setImport(true);
					dg.setVisible(true);
				}
			});
		}
		return rbSupplierEnvelop;
	}

	/**
	 * This method initializes rbSupplierBalanceAll	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbSupplierBalanceAll() {
		if (rbSupplierBalanceAll == null) {
			rbSupplierBalanceAll = new JRadioButton();
			rbSupplierBalanceAll.setText("结转差额总表");
			String toolTipText="按供应商分类计算关封、送货、结转之间的差异";
			rbSupplierBalanceAll.setToolTipText(toolTipText);
			rbSupplierBalanceAll.setBounds(new Rectangle(57, 56, 119, 21));
			rbSupplierBalanceAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierBalanceAllInfo dg = new DgSupplierBalanceAllInfo();
					dg.setTitle("(供应商)结转差额总表");
					dg.setIsM(true);
					dg.setVisible(true);
				}
			});
		}
		return rbSupplierBalanceAll;
	}

	/**
	 * This method initializes rbSupplierBalanceDetail	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */


	/**
	 * This method initializes rbClientSend	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbClientSend() {
		if (rbClientSend == null) {
			rbClientSend = new JRadioButton();
			rbClientSend.setText("送货明细表");
			String toolTipText="记录客户送货的明细";
			rbClientSend.setToolTipText(toolTipText);
			rbClientSend.setBounds(new Rectangle(59, 37, 109, 21));
			rbClientSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					DgClientSendDetailInfo dg = new DgClientSendDetailInfo();
//					dg.setVisible(true);
					
					DgRecvSendDetailInfo dg = new DgRecvSendDetailInfo();
					dg.setMaterielType(MaterielType.FINISHED_PRODUCT);
					dg.setTitle("送货明细表");
					dg.setVisible(true);
					
				}
			});
		}
		return rbClientSend;
	}

	/**
	 * This method initializes rbClientTransfer	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbClientTransfer() {
		if (rbClientTransfer == null) {
			rbClientTransfer = new JRadioButton();
			rbClientTransfer.setText("结转明细表");
			String toolTipText="记录客户结转报关单的明细";
			rbClientTransfer.setToolTipText(toolTipText);
			rbClientTransfer.setBounds(new Rectangle(268, 37, 102, 21));
			rbClientTransfer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTransferDetailInfo dg =  new DgTransferDetailInfo();
					dg.setIsImport(false);
					dg.setTitle("转厂出口结转明细表");
					dg.setVisible(true);
				}
			});
		}
		return rbClientTransfer;
	}

	/**
	 * This method initializes rbClientEnvelop	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbClientEnvelop() {
		if (rbClientEnvelop == null) {
			rbClientEnvelop = new JRadioButton();
			rbClientEnvelop.setText("(客户)关封明细表");
			String toolTipText="记录客户关封的明细";
			rbClientEnvelop.setToolTipText(toolTipText);
			rbClientEnvelop.setBounds(new Rectangle(463, 37, 149, 21));
			rbClientEnvelop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgClientEnvelopDetailInfo dg = new DgClientEnvelopDetailInfo();
					dg.setImport(false);
					dg.setVisible(true);
				}
			});
		}
		return rbClientEnvelop;
	}

	/**
	 * This method initializes rbClientBalanceAll	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbClientBalanceAll() {
		if (rbClientBalanceAll == null) {
			rbClientBalanceAll = new JRadioButton();
			rbClientBalanceAll.setText("结转差额总表");
			String toolTipText="按客户分类计算关封、送货、结转之间的差异";
			rbClientBalanceAll.setToolTipText(toolTipText);
			rbClientBalanceAll.setBounds(new Rectangle(59, 81, 108, 21));
			rbClientBalanceAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					DgClientBalanceAllInfo dg = new DgClientBalanceAllInfo();
//					dg.setVisible(true);
					DgSupplierBalanceAllInfo dg = new DgSupplierBalanceAllInfo();
					dg.setTitle("(客户)结转差额总表");
					dg.setIsM(false);
					dg.setVisible(true);
				}
			});
		}
		return rbClientBalanceAll;
	}

	/**
	 * This method initializes rbClientBalanceDetail	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbClientBalanceDetail() {
		if (rbClientBalanceDetail == null) {
			rbClientBalanceDetail = new JRadioButton();
			rbClientBalanceDetail.setText("结转差额明细表");
			String toolTipText="按商品分类计算每个客户的结转差额";
			rbClientBalanceDetail.setToolTipText(toolTipText);
			rbClientBalanceDetail.setBounds(new Rectangle(268, 81, 129, 21));
			rbClientBalanceDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					DgClientBalanceDetailInfo dg = new DgClientBalanceDetailInfo();
//					dg.setVisible(true);
					
					DgSupplierBalanceDetailInfo dg = new DgSupplierBalanceDetailInfo();
					dg.setTitle("(客户)结转差额分表");
					dg.setIsM(false);
					dg.setVisible(true);
				}
			});
		}
		return rbClientBalanceDetail;
	}

	/**
	 * This method initializes pnBottom	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnBottom() {
		if (pnBottom == null) {
			pnBottom = new JPanel();
			pnBottom.setBounds(new Rectangle(37, 550, 700, 3));
			pnBottom
			.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return pnBottom;
	}

	/**
	 * This method initializes btnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(644, 563, 68, 26);
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
	 * This method initializes rbClientConvert	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbClientConvert() {
		if (rbClientConvert == null) {
			rbClientConvert = new JRadioButton();
			rbClientConvert.setText("结转折料情况");
			String toolTipText="将成品结转差额折料成原材料的清单";
			rbClientConvert.setToolTipText(toolTipText);
			rbClientConvert.setBounds(new Rectangle(463, 81, 108, 21));
			rbClientConvert.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgClientConvertDetailInfo dg = new DgClientConvertDetailInfo();
					dg.setVisible(true);
				}
			});
		}
		return rbClientConvert;
	}
	
	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbSupplierReceive());
			buttonGroup.add(getRbSupplierTransfer());
			buttonGroup.add(getRbSupplierEnvelop());
			buttonGroup.add(getRbSupplierBalanceAll());
			buttonGroup.add(getRbSupplierBalanceDetail());
			buttonGroup.add(getRbClientSend());
			buttonGroup.add(getRbClientTransfer());
			buttonGroup.add(getRbClientEnvelop());
			buttonGroup.add(getRbClientBalanceAll());
			buttonGroup.add(getRbClientBalanceDetail());
			buttonGroup.add(getRbClientConvert());
		}
		return buttonGroup;
	}


	/**
	 * This method initializes pnCover	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnCover() {
		if (pnCover == null) {
			pnCover = new JPanel();
			pnCover.setLayout(null);
			pnCover.setBounds(new Rectangle(46, 49, 691, 347));
			pnCover.setBorder(
					BorderFactory.createTitledBorder(
									null,
									"结转情况",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, 
									new Font("Dialog",Font.BOLD, 14), 
									Color.blue));
			pnCover.add(getPnUp(), null);
			pnCover.add(getPnDown(), null);
		}
		return pnCover;
	}

	/**
	 * This method initializes pnUp	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnUp() {
		if (pnUp == null) {
			pnUp = new JPanel();
			pnUp.setLayout(null);
			pnUp.setBounds(new Rectangle(31, 45, 633, 91));
			pnUp.setBorder(
					BorderFactory.createTitledBorder(
									null, 
									"供应商收货情况表", 
									TitledBorder.DEFAULT_JUSTIFICATION, 
									TitledBorder.DEFAULT_POSITION, 
									new Font("Dialog", Font.BOLD, 12), 
									Color.blue));
			pnUp.add(getRbSupplierReceive(), null);
			pnUp.add(getRbSupplierTransfer(), null);
			pnUp.add(getRbSupplierEnvelop(), null);
			pnUp.add(getRbSupplierBalanceAll(), null);
			pnUp.add(getRbSupplierBalanceDetail(), null);
		}
		return pnUp;
	}

	/**
	 * This method initializes pnDown	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnDown() {
		if (pnDown == null) {
			pnDown = new JPanel();
			pnDown.setLayout(null);
			pnDown.setBounds(new Rectangle(29, 175, 637, 138));
			pnDown.setBorder(
					BorderFactory.createTitledBorder(
									null, 
									"客户送货情况", 
									TitledBorder.DEFAULT_JUSTIFICATION, 
									TitledBorder.DEFAULT_POSITION, 
									new Font("Dialog", Font.BOLD, 12), 
									Color.blue));
			pnDown.add(getRbClientSend(), null);
			pnDown.add(getRbClientTransfer(), null);
			pnDown.add(getRbClientEnvelop(), null);
			pnDown.add(getRbClientConvert(), null);
			pnDown.add(getRbClientBalanceDetail(), null);
			pnDown.add(getRbClientBalanceAll(), null);
		}
		return pnDown;
	}

	/**
	 * This method initializes rbSupplierBalanceDetail1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbSupplierBalanceDetail() {
		if (rbSupplierBalanceDetail == null) {
			rbSupplierBalanceDetail = new JRadioButton();
			rbSupplierBalanceDetail.setBounds(new Rectangle(265, 56, 128, 21));
			rbSupplierBalanceDetail.setText("结转差额明细表");
			String toolTipText="按商品分类计算每个供应商的结转差额";
			rbSupplierBalanceDetail.setToolTipText(toolTipText);
			rbSupplierBalanceDetail.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSupplierBalanceDetailInfo dg = new DgSupplierBalanceDetailInfo();
					dg.setTitle("(供应商)结转差额分表");
					dg.setIsM(true);
					dg.setVisible(true);
				}
			});
		}
		return rbSupplierBalanceDetail;
	}

}  //  @jve:decl-index=0:visual-constraint="24,16"
