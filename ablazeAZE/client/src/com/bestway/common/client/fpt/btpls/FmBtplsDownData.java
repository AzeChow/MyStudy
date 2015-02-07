package com.bestway.common.client.fpt.btpls;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;

public class FmBtplsDownData extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private FptManageAction fptManageAction = null;
	private JRadioButton jRadioButton1 = null;
	private JRadioButton jRadioButton2 = null;
	private JRadioButton jRadioButton3 = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;

	/**
	 * This method initializes 
	 * 
	 */
	public FmBtplsDownData() {
		super();
		fptManageAction = (FptManageAction) CommonVars
		.getApplicationContext().getBean("fptManageAction");
		fptManageAction.permissionCheckBtplsDownData(new Request(CommonVars.getCurrUser()));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("深加工结转平台数据下载");
        this.setSize(new Dimension(710, 517));
        this.setContentPane(getJContentPane());
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.setFont(new java.awt.Font("Dialog",
					java.awt.Font.PLAIN, 18));
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			jContentPane.add(getJPanel2(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setText("进出口报关单");
			jRadioButton1.setBounds(new Rectangle(121, 25, 103, 26));
			jRadioButton1
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBtpltsCustomsDeclarationDownData fm = new FmBtpltsCustomsDeclarationDownData();
					ShowFormControl.showForm(fm);
					jRadioButton2.setSelected(false);
					jRadioButton3.setSelected(false);
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
			jRadioButton2.setText("结转申请表");
			jRadioButton2.setBounds(new Rectangle(121, 67, 103, 26));
			jRadioButton2
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBtplsFptAppDownData fm = new FmBtplsFptAppDownData();
					ShowFormControl.showForm(fm);
					jRadioButton1.setSelected(false);
					jRadioButton3.setSelected(false);
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
			jRadioButton3.setText("收发货单据表");
			jRadioButton3.setBounds(new Rectangle(121, 109, 103, 26));
			jRadioButton3
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmTransferFactoryBillDownData fm = new FmTransferFactoryBillDownData();
					ShowFormControl.showForm(fm);
					jRadioButton1.setSelected(false);
					jRadioButton2.setSelected(false);
				}
			});
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jLabel.setText("数据下载");
			jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
			jLabel.setForeground(new java.awt.Color(255, 153, 0));
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1
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
			jPanel1.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel1, java.awt.BorderLayout.EAST);
			jPanel1.add(jLabel2, java.awt.BorderLayout.WEST);
		}
		return jPanel1;
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
			jPanel2.add(getJRadioButton1(), null);
			jPanel2.add(getJRadioButton2(), null);
			jPanel2.add(getJRadioButton3(), null);
		}
		return jPanel2;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
