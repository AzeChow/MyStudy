/**
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.bcus.client.enc.report.DgExchangeExp;
import com.bestway.bcus.client.enc.report.DgExchangeImp;
import com.bestway.bcus.client.enc.report.DgExpCustomsBill;
import com.bestway.bcus.manualdeclarereport.action.ManualDeclareReportAction;
import com.bestway.client.windows.control.ShowFormControl;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 电子帐册财务统计报表
 * @author Administrator
 *
 */
public class FmFinancialReportQuery extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private ContractAction contractAction = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JRadioButton rbIFinancial = null;

	private JRadioButton rbEFinancial = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:

	private JRadioButton rbTrunFinancial = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private PnImageBackground jPanel2 = null;

	private JLabel jLabel1 = null;

	private ManualDeclareReportAction manualDeclareReportAction = null;
  
	/**
	 * This is the default constructor
	 */
	public FmFinancialReportQuery() {
		super();
		getButtonGroup();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		manualDeclareReportAction = (ManualDeclareReportAction) CommonVars
				.getApplicationContext().getBean("manualDeclareReportAction");
		manualDeclareReportAction.checkFinancialReportQueryAuthority(new Request(CommonVars
				.getCurrUser()));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("免抵退税管理报表");
		this.setSize(761, 485);
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
			jPanel4.add(getJPanel2(), java.awt.BorderLayout.CENTER);
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
			jPanel5.setOpaque(false);
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(
					javax.swing.BorderFactory.createLineBorder(
							java.awt.SystemColor.inactiveCaption, 1), "财务报表情况",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.SystemColor.activeCaption));
			jPanel5.setBounds(new Rectangle(100, 83, 514, 214));
			jPanel5.add(getRbIFinancial(), null);
			jPanel5.add(getRbEFinancial(), null);
			jPanel5.add(getRbTrunFinancial(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes rbIFinancial
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIFinancial() {
		if (rbIFinancial == null) {
			rbIFinancial = new JRadioButton();
			rbIFinancial.setBounds(180, 27, 154, 23);
			rbIFinancial.setText("进口财务报表");
			rbIFinancial.setOpaque(false);
			rbIFinancial
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.financialImpReport(new Request(
									CommonVars.getCurrUser()));
							DgImpFinancialRecord dg = new DgImpFinancialRecord();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return rbIFinancial;
	}

	/**
	 * This method initializes rbEFinancial
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEFinancial() {
		if (rbEFinancial == null) {
			rbEFinancial = new JRadioButton();
			rbEFinancial.setBounds(180, 86, 154, 23);
			rbEFinancial.setText("出口财务报表");
			rbEFinancial.setOpaque(false);
			rbEFinancial
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.financialExgReport(new Request(
									CommonVars.getCurrUser()));
							DgExpFinancialRecord dg = new DgExpFinancialRecord();
							ShowFormControl.showForm(dg);

						}
					});
		}
		return rbEFinancial;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(getRbIFinancial());
			buttonGroup.add(getRbEFinancial());
			buttonGroup.add(getRbTrunFinancial());
		}
		return buttonGroup;
	}


	/**
	 * This method initializes rbTrunFinancial
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTrunFinancial() {
		if (rbTrunFinancial == null) {
			rbTrunFinancial = new JRadioButton();
			rbTrunFinancial.setText("转厂出口财务报表");
			rbTrunFinancial.setBounds(new Rectangle(180, 142, 154, 23));
			rbTrunFinancial.setOpaque(false);
			rbTrunFinancial
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							manualDeclareReportAction.financialTrunReport(new Request(
									CommonVars.getCurrUser()));
							DgTrunFinancialRecord dg = new DgTrunFinancialRecord();
							ShowFormControl.showForm(dg);
						}
					});
		}
		return rbTrunFinancial;
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
			jLabel.setText(" 免抵退税管理报表");
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
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new PnImageBackground();
			jPanel2.setLayout(null);
			jPanel2.add(getJPanel5(), null);
		}
		return jPanel2;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
