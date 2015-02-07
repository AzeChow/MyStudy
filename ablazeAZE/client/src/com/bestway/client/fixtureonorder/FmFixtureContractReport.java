/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fixtureonorder;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnImageBackground;
import com.bestway.common.Request;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmFixtureContractReport extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private PnImageBackground jPanel4 = null;

	private JPanel jPanel5 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="191,33"

	private JRadioButton jRadioButton11 = null;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	/**
	 * This is the default constructor
	 */
	public FmFixtureContractReport() {
		super();
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
		getButtonGroup();
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("设备报表");
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
			jPanel4 = new PnImageBackground();
			jPanel4.setLayout(null);
			jPanel4.add(getJPanel5(), null);
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
			jPanel5.setBounds(21, 88, 596, 173);
			jPanel5.setOpaque(false);
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
									"设备报表",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, java.awt.SystemColor.activeCaption));
			jPanel5.add(getJRadioButton(), null);
			jPanel5.add(getJRadioButton1(), null);
			jPanel5.add(getJRadioButton11(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(222, 11, 221, 23);
			jRadioButton.setText("设备报关单查询");
			jRadioButton.setOpaque(false);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkFixtureCustomsDeclarationStat(new Request(CommonVars.getCurrUser()));
					DgFixtureCustomsDeclarationStat dg = new DgFixtureCustomsDeclarationStat();
					dg.setVisible(true);
				}
			});
		}
		return jRadioButton;
	}

	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(222, 46, 221, 23);
			jRadioButton1.setText("设备报关登记表");
			jRadioButton1.setOpaque(false);
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction.checkFixtureContractItemsList(new Request(CommonVars.getCurrUser()));
							DgFixtureContractItemsList dg = new DgFixtureContractItemsList();
							dg.setVisible(true);
						}
					});
		}
		return jRadioButton1;
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
			buttonGroup.add(this.getJRadioButton11());

		}
		return buttonGroup;
	}

	/**
	 * This method initializes jRadioButton11
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton11() {
		if (jRadioButton11 == null) {
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setBounds(new Rectangle(222, 80, 150, 25));
			jRadioButton11.setText("设备位置查询");
			jRadioButton11.setOpaque(false);
			jRadioButton11
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkFixtureChangeLocationSearch(new Request(CommonVars.getCurrUser()));
					DgFixtureChangeLocationSearch dg = new DgFixtureChangeLocationSearch();
					dg.setVisible(true);
				}
			});
		}
		return jRadioButton11;
	}
} // @jve:decl-index=0:visual-constraint="38,72"
