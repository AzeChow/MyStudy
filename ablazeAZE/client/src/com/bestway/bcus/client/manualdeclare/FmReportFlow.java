/**
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.client.enc.FmExportCustomsDeclaration;
import com.bestway.bcus.client.enc.FmImpExpRequestBill;
import com.bestway.bcus.client.enc.FmImportCustomsDeclaration;
import com.bestway.bcus.client.enc.report.DgExgWearDetail;
import com.bestway.bcus.client.enc.report.DgExpCustomsBill;
import com.bestway.bcus.client.enc.report.DgImgBalanceTotal;
import com.bestway.bcus.client.enc.report.DgImpCustomsBill;
import com.bestway.client.custom.common.DgBaseImportCustomsDeclaration;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import com.progress.message.jbBundle;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmReportFlow extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JPanel jPanel = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JButton jButton41 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private JButton jButton51 = null;

	private JButton jButton7 = null;

	private FlowLine flowLine111121 = null;

	private FlowLine flowLine1111211 = null;

	private FlowLine flowLine1111212 = null;

	private FlowLine flowLine1111213 = null;

	private FlowLine flowLine1111214 = null;

	private FlowLine flowLine1111215 = null;

	private FlowLine flowLine1111216 = null;

	private FlowLine flowLine1111217 = null;

	private FlowLine flowLine1111218 = null;

	private FlowLine flowLine11111 = null;

	private FlowButton jButton8 = null;

	private FlowButton jButton9 = null;

	private FlowButton jButton10 = null;

	private FlowLine flowLine11112111 = null;

	private FlowLine flowLine11112112 = null;

	private FlowButton jButton511 = null;

	private FlowButton jButton71 = null;

	private FlowLine flowLine11112161 = null;

	private FlowLine flowLine11112162 = null;

	private FlowButton jButton21 = null;

	private FlowLine flowLine111111 = null;

	private FlowLine flowLine11112181 = null;


	/**
	 * This is the default constructor
	 */
	public FmReportFlow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("统计报表打印");
		this.setSize(1148, 551);
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
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jPanel4.add(getJPanel(), BorderLayout.CENTER);
		}
		return jPanel4;
	}
	
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/hint.gif")));
			jLabel = new JLabel();
			jLabel.setText("穿透式报表流程");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 24));
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			flowLine11112181 = new FlowLine();
			flowLine11112181.setBounds(new Rectangle(1002, 152, 14, 74));
			flowLine11112181.setArrowDirection(3);
			flowLine111111 = new FlowLine();
			flowLine111111.setBounds(new Rectangle(899, 145, 111, 14));
			flowLine111111.setOnlyDrawLine(true);
			flowLine111111.setArrowDirection(0);
			flowLine11112162 = new FlowLine();
			flowLine11112162.setBounds(new Rectangle(872, 283, 10, 47));
			flowLine11112162.setArrowDirection(3);
			flowLine11112161 = new FlowLine();
			flowLine11112161.setBounds(new Rectangle(758, 283, 10, 47));
			flowLine11112161.setArrowDirection(3);
			flowLine11112112 = new FlowLine();
			flowLine11112112.setBounds(new Rectangle(805, 178, 10, 47));
			flowLine11112112.setArrowDirection(3);
			flowLine11112111 = new FlowLine();
			flowLine11112111.setBounds(new Rectangle(805, 74, 10, 47));
			flowLine11112111.setArrowDirection(3);
			flowLine11111 = new FlowLine();
			flowLine11111.setBounds(new Rectangle(50, 143, 80, 16));
			flowLine11111.setOnlyDrawLine(true);
			flowLine11111.setArrowDirection(0);
			flowLine1111218 = new FlowLine();
			flowLine1111218.setBounds(new Rectangle(44, 151, 10, 75));
			flowLine1111218.setArrowDirection(3);
			flowLine1111217 = new FlowLine();
			flowLine1111217.setBounds(new Rectangle(515, 285, 10, 46));
			flowLine1111217.setArrowDirection(3);
			flowLine1111216 = new FlowLine();
			flowLine1111216.setBounds(new Rectangle(410, 285, 10, 46));
			flowLine1111216.setArrowDirection(3);
			flowLine1111215 = new FlowLine();
			flowLine1111215.setBounds(new Rectangle(236, 285, 10, 45));
			flowLine1111215.setArrowDirection(3);
			flowLine1111214 = new FlowLine();
			flowLine1111214.setBounds(new Rectangle(142, 286, 10, 45));
			flowLine1111214.setArrowDirection(3);
			flowLine1111213 = new FlowLine();
			flowLine1111213.setBounds(new Rectangle(446, 180, 10, 46));
			flowLine1111213.setArrowDirection(3);
			flowLine1111212 = new FlowLine();
			flowLine1111212.setBounds(new Rectangle(212, 179, 10, 46));
			flowLine1111212.setArrowDirection(3);
			flowLine1111211 = new FlowLine();
			flowLine1111211.setBounds(new Rectangle(399, 75, 8, 45));
			flowLine1111211.setArrowDirection(3);
			flowLine111121 = new FlowLine();
			flowLine111121.setBounds(new Rectangle(269, 75, 10, 47));
			flowLine111121.setArrowDirection(3);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJButton4(), null);
			jPanel.add(getJButton41(), null);
			jPanel.add(getJButton5(), null);
			jPanel.add(getJButton6(), null);
			jPanel.add(getJButton51(), null);
			jPanel.add(getJButton7(), null);
			jPanel.add(flowLine111121, null);
			jPanel.add(flowLine1111211, null);
			jPanel.add(flowLine1111212, null);
			jPanel.add(flowLine1111213, null);
			jPanel.add(flowLine1111214, null);
			jPanel.add(flowLine1111215, null);
			jPanel.add(flowLine1111216, null);
			jPanel.add(flowLine1111217, null);
			jPanel.add(flowLine1111218, null);
			jPanel.add(flowLine11111, null);
			jPanel.add(getJButton8(), null);
			jPanel.add(getJButton9(), null);
			jPanel.add(getJButton10(), null);
			jPanel.add(flowLine11112111, null);
			jPanel.add(flowLine11112112, null);
			jPanel.add(getJButton511(), null);
			jPanel.add(getJButton71(), null);
			jPanel.add(flowLine11112161, null);
			jPanel.add(flowLine11112162, null);
			jPanel.add(getJButton21(), null);
			jPanel.add(flowLine111111, null);
			jPanel.add(flowLine11112181, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new FlowButton();
			jButton.setBounds(new Rectangle(250, 15, 180, 60));
			jButton.setText("料件进出平衡状况汇总表");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImgBalanceTotal dg = new DgImgBalanceTotal();
					ShowFormControl.showForm(dg);
				}
			});
//			jButton.addMouseListener(new java.awt.event.MouseAdapter() {   
//				public void mouseExited(java.awt.event.MouseEvent e) {    
//				}
//				public void mouseEntered(java.awt.event.MouseEvent e) {
//					System.out.println(jButton.getForeground());
//					((JButton)e.getComponent()).setForeground(Color.RED);
//				}
//			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new FlowButton();
			jButton1.setBounds(new Rectangle(130, 120, 180, 60));
			jButton1.setText("进口料件报关登记表");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImpCustomsRecord dg = new DgImpCustomsRecord();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new FlowButton();
			jButton2.setBounds(new Rectangle(10, 225, 100, 60));
			jButton2.setText("进口报关单");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmImportCustomsDeclaration dg = new FmImportCustomsDeclaration();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new FlowButton();
			jButton3.setBounds(new Rectangle(370, 120, 180, 60));
			jButton3.setText("料件耗用明细(帐册级)");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExgWearDetail dg = new DgExgWearDetail();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new FlowButton();
			jButton4.setBounds(new Rectangle(130, 225, 180, 60));
			jButton4.setText("进口大小清单差异分析");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRequestTOApplyTOCustomsReport dg = new DgRequestTOApplyTOCustomsReport();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton41	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton41() {
		if (jButton41 == null) {
			jButton41 = new FlowButton();
			jButton41.setBounds(new Rectangle(370, 225, 180, 60));
			jButton41.setText("出口大小清单差异分析");
			jButton41.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRequestTOApplyTOCustomsReport dg = new DgRequestTOApplyTOCustomsReport();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton41;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new FlowButton();
			jButton5.setBounds(new Rectangle(80, 330, 100, 60));
			jButton5.setText("清单");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImpCustomsBill dg = new DgImpCustomsBill();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton5;
	}

	/**
	 * This method initializes jButton6	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new FlowButton();
			jButton6.setBounds(new Rectangle(210, 330, 100, 60));
			jButton6.setText("申请单");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmImpExpRequestBill fm = new FmImpExpRequestBill();
					ShowFormControl.showForm(fm);
				}
			});
		}
		return jButton6;
	}

	/**
	 * This method initializes jButton51	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton51() {
		if (jButton51 == null) {
			jButton51 = new FlowButton();
			jButton51.setBounds(new Rectangle(370, 330, 100, 60));
			jButton51.setText("清单");
			jButton51.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExpCustomsBill dg = new DgExpCustomsBill();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton51;
	}

	/**
	 * This method initializes jButton7	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new FlowButton();
			jButton7.setBounds(new Rectangle(500, 330, 100, 60));
			jButton7.setText("申请单");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmImpExpRequestBill fm = new FmImpExpRequestBill();
					ShowFormControl.showForm(fm);
				}
			});
		}
		return jButton7;
	}

	/**
	 * This method initializes jButton8	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new FlowButton();
			jButton8.setBounds(new Rectangle(720, 15, 180, 60));
			jButton8.setText("出口成品情况统计表");
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExpUseRecord dg = new DgExpUseRecord();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton8;
	}

	/**
	 * This method initializes jButton9	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new FlowButton();
			jButton9.setBounds(new Rectangle(720, 120, 180, 60));
			jButton9.setText("出口成品报关登记表");
			jButton9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExpCustomsRecord dg = new DgExpCustomsRecord();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton9;
	}

	/**
	 * This method initializes jButton10	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new FlowButton();
			jButton10.setBounds(new Rectangle(720, 225, 180, 60));
			jButton10.setText("出口大小清单差异分析");
			jButton10.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRequestTOApplyTOCustomsReport dg = new DgRequestTOApplyTOCustomsReport();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton10;
	}

	/**
	 * This method initializes jButton511	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getJButton511() {
		if (jButton511 == null) {
			jButton511 = new FlowButton();
			jButton511.setBounds(new Rectangle(720, 330, 100, 60));
			jButton511.setText("清单");
			jButton511.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgExpCustomsBill dg = new DgExpCustomsBill();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jButton511;
	}

	/**
	 * This method initializes jButton71	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getJButton71() {
		if (jButton71 == null) {
			jButton71 = new FlowButton();
			jButton71.setBounds(new Rectangle(850, 330, 100, 60));
			jButton71.setText("申请单");
			jButton71.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmImpExpRequestBill fm = new FmImpExpRequestBill();
					ShowFormControl.showForm(fm);
				}
			});
		}
		return jButton71;
	}

	/**
	 * This method initializes jButton21	
	 * 	
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton	
	 */
	private FlowButton getJButton21() {
		if (jButton21 == null) {
			jButton21 = new FlowButton();
			jButton21.setBounds(new Rectangle(950, 225, 100, 60));
			jButton21.setText("出口报关单");
			jButton21.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmExportCustomsDeclaration fm = new FmExportCustomsDeclaration();
					ShowFormControl.showForm(fm);
				}
			});
		}
		return jButton21;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"  
