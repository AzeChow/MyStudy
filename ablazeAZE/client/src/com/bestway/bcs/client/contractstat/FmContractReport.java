/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractstat;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contractstat.action.ContractStatAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmContractReport extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private ContractAction contractAction = null;
	private ContractStatAction contractStatAction=null;

	private PnImageBackground jPanel4 = null;

	private JPanel jPanel5 = null;

	private JPanel jPanel7 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private JRadioButton jRadioButton2 = null;

	private JRadioButton jRadioButton3 = null;

	private JRadioButton jRadioButton8 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="191,33"

	private JRadioButton jRadioButton4 = null;

	private JRadioButton rbBillConvertDeclaration = null;
	private JPanel jPanel = null;

	private JRadioButton rbIEBill = null;

	private JRadioButton jRadioButton5 = null;

	private JRadioButton jRadioButton51 = null;

	/**
	 * This is the default constructor
	 */
	public FmContractReport() {
		super();
		getButtonGroup();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		contractStatAction = (ContractStatAction) CommonVars.getApplicationContext()
		.getBean("contractStatAction");
		contractStatAction.checkAuthority(new Request(CommonVars.getCurrUser()));
		// 初始化合同
//		List contracts = contractAction.findContractByProcessExe(new Request(
//				CommonVars.getCurrUser()));
//		this.cbbContract.removeAllItems();
//		if (contracts != null && contracts.size() > 0) {
//			for (int i = 0; i < contracts.size(); i++) {
//				this.cbbContract.addItem((Contract) contracts.get(i));
//			}
//			this.cbbContract.setRenderer(CustomBaseRender.getInstance()
//					.getRender("impContractNo", "emsNo", 200, 200)
//					.setForegroundColor(java.awt.Color.red));
//		}
//		if (this.cbbContract.getItemCount() > 0) {
//			cbbContract.setSelectedIndex(0);
//		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("合同统计报表");
		this.setSize(651, 512);
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
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jLabel.setText("统计报表");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel.setBackground(java.awt.Color.white);
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel2.setText("");
			jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.WEST);
			jPanel.add(jLabel2, java.awt.BorderLayout.EAST);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new PnImageBackground();
			jPanel4.setLayout(null);
			jPanel4.add(getJPanel5(), null);
			jPanel4.add(getJPanel7(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(21, 88, 596, 263);
			jPanel5.setOpaque(false);
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"合同执行结果",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, java.awt.SystemColor.activeCaption));
			jPanel5.add(getJRadioButton(), null);
			jPanel5.add(getJRadioButton1(), null);
			jPanel5.add(getJRadioButton2(), null);
			jPanel5.add(getJRadioButton3(), null);
			jPanel5.add(getJRadioButton4(), null);
			jPanel5.add(getRbBillConvertDeclaration(), null);
			jPanel5.add(getRbIEBill(), null);
			jPanel5.add(getJRadioButton5(), null);
			jPanel5.add(getJRadioButton51(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jPanel7.setBounds(19, 373, 599, 54);
			jPanel7.setOpaque(false);
			jPanel7.setVisible(false);
			jPanel7
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"其他",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, java.awt.SystemColor.activeCaption));
			jPanel7.add(getJRadioButton8(), null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(355, 17, 166, 23);
			jRadioButton.setText("进口料件情况统计表");
			jRadioButton.setOpaque(false);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImpMaterialStat dg = new DgImpMaterialStat();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jRadioButton;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(94, 22, 166, 23);
			jRadioButton1.setText("进口料件报关登记表");
			jRadioButton1.setOpaque(false);
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgImpMaterialList dg = new DgImpMaterialList();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(355, 55, 166, 23);
			jRadioButton2.setText("出口成品情况统计表");
			jRadioButton2.setOpaque(false);
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgExpProductStat dg = new DgExpProductStat();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton3() {
		if (jRadioButton3 == null) {
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setBounds(94, 53, 166, 23);
			jRadioButton3.setText("出口成品报关登记表");
			jRadioButton3.setOpaque(false);
			jRadioButton3
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgExpProductList dg = new DgExpProductList();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jRadioButton8
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton8() {
		if (jRadioButton8 == null) {
			jRadioButton8 = new JRadioButton();
			jRadioButton8.setBounds(226, 43, 172, 21);
			jRadioButton8.setText("核销单登记表");
			jRadioButton8.setOpaque(false);
			jRadioButton8
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
//							if (cbbContract.getItemCount() < 1) {
//								JOptionPane.showMessageDialog(
//										FmContractReport.this, "系统没有正在执行的合同",
//										"提示", JOptionPane.OK_OPTION);
//								return;
//							}
//							if (cbbContract.getSelectedItem() == null) {
//								JOptionPane.showMessageDialog(
//										FmContractReport.this, "请选择合同", "提示",
//										JOptionPane.OK_OPTION);
//								return;
//							}
							DgCancelAfterVerificationList dg = new DgCancelAfterVerificationList();
							dg.setVisible(true);

						}
					});
		}
		return jRadioButton8;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButton());
			buttonGroup.add(getJRadioButton1());
			buttonGroup.add(getJRadioButton2());
			buttonGroup.add(getJRadioButton3());
			buttonGroup.add(getJRadioButton4());
			buttonGroup.add(getRbBillConvertDeclaration());
			buttonGroup.add(getRbIEBill());
			buttonGroup.add(getJRadioButton5());
			buttonGroup.add(getJRadioButton51());
			// buttonGroup.add(getJRadioButton5());
			// buttonGroup.add(getJRadioButton6());
			// buttonGroup.add(getJRadioButton7());
//			buttonGroup.add(getJRadioButton8());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jRadioButton4	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton4() {
		if (jRadioButton4 == null) {
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setBounds(new Rectangle(94, 86, 166, 23));
			jRadioButton4.setText("特殊报关登记表");
			jRadioButton4.setOpaque(false);
			jRadioButton4
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgSpecialCustomsList dg = new DgSpecialCustomsList();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return jRadioButton4;
	}

	/**
	 * This method initializes rbBillConvertDeclaration	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbBillConvertDeclaration() {
		if (rbBillConvertDeclaration == null) {
			rbBillConvertDeclaration = new JRadioButton();
			rbBillConvertDeclaration.setBounds(new Rectangle(94, 117, 196, 21));
			rbBillConvertDeclaration.setText("申请单→报关单进出口明细表");
			rbBillConvertDeclaration.setOpaque(false);
			rbBillConvertDeclaration.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillToCustomsList dg = new DgBillToCustomsList();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return rbBillConvertDeclaration;
	}

	/**
	 * This method initializes rbIEBill	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRbIEBill() {
		if (rbIEBill == null) {
			rbIEBill = new JRadioButton();
			rbIEBill.setBounds(new Rectangle(94, 145, 182, 21));
			rbIEBill.setText("进出口申请单明细表");
			rbIEBill.setOpaque(false);
			rbIEBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillList dg = new DgBillList();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return rbIEBill;
	}

	/**
	 * This method initializes jRadioButton5	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setOpaque(false);
			jRadioButton5.setText("统计进出口报关单总值" +"\n"+
					"(东莞寮步外经办用)");
			jRadioButton5.setBounds(new Rectangle(94, 177, 276, 21));
			jRadioButton5.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e){
					DgIoMoney dg = new DgIoMoney();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jRadioButton5;
	}

	/**
	 * This method initializes jRadioButton51	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton51() {
		if (jRadioButton51 == null) {
			jRadioButton51 = new JRadioButton();
			jRadioButton51.setBounds(new Rectangle(94, 206, 267, 26));
			jRadioButton51.setText("报关行申报登记表");
			jRadioButton51.setOpaque(false);
			jRadioButton51.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent e){
					DgCustomsBrokersList dg = new DgCustomsBrokersList();
					ShowFormControl.showForm(dg);
				}
			});
		}
		return jRadioButton51;
	}
} // @jve:decl-index=0:visual-constraint="38,72"
