package com.bestway.bcus.client.manualdeclare;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.ui.winuicontrol.JPanelBase;
import javax.swing.JLabel;

public class PnBatchUpdateComplex extends JPanelBase {

	private ButtonGroup buttonGroupType = null; // @jve:decl-index=0:
	private ButtonGroup buttonGroupMetraiel = null; // @jve:decl-index=0:

	private JPanel jPanel1 = null;

	private JRadioButton rbEms = null;

	private JRadioButton rbMerger = null;

	private JPanel jPanel2 = null;

	private MakeCusomsDeclarationParam para = null; // @jve:decl-index=0:

	private JRadioButton rbInternalMerger = null;

	private JRadioButton rbImg = null;

	private JRadioButton rbExg = null;

	// =================================
	public String  emsType = "0"; // @jve:decl-index=0:
	public Boolean isMaterial = false;  //  @jve:decl-index=0:
	private JLabel jLabel = null;

	// ===========================
	/**
	 * This is the default constructor
	 */
	public PnBatchUpdateComplex() {
		super();
		initialize();
		getButtonGroupType();
		getButtonGroupMetraiel();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 0;
		gridBagConstraints21.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints21.insets = new Insets(0, 60, 0, 0);
		gridBagConstraints21.gridy = 3;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints11.insets = new Insets(0, 60, 0, 0);
		gridBagConstraints11.gridy = 2;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.insets = new Insets(4, 52, 5, 54);
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.ipadx = 547;
		gridBagConstraints2.ipady = 150;
		gridBagConstraints2.gridx = 0;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.insets = new Insets(14, 52, 4, 54);
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.ipadx = 547;
		gridBagConstraints1.ipady = 66;
		gridBagConstraints1.gridx = 0;
		this.setLayout(new GridBagLayout());
		this.setSize(697, 332);

		this.add(getJPanel1(), gridBagConstraints1);
		this.add(getJPanel2(), gridBagConstraints2);
	}

	public void setVisible(boolean isFalg) {
		if (isFalg == true) {
		}
		super.setVisible(isFalg);
	}

	private ButtonGroup getButtonGroupType() {
		if (buttonGroupType == null) {
			buttonGroupType = new ButtonGroup();
			buttonGroupType.add(getRbEms());
			buttonGroupType.add(getRbMerger());
			buttonGroupType.add(getRbInternalMerger());
		}
		return buttonGroupType;
	}
	private ButtonGroup getButtonGroupMetraiel() {
		if (buttonGroupMetraiel == null) {
			buttonGroupMetraiel = new ButtonGroup();
			buttonGroupMetraiel.add(getRbImg());
			buttonGroupMetraiel.add(getRbExg());
		}
		return buttonGroupMetraiel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									javax.swing.BorderFactory
											.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
									"请选择要修改的物料类型",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			jPanel1.setLayout(null);
			jPanel1.add(getRbImg(), null);
			jPanel1.add(getRbExg(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes rbEms
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEms() {
		if (rbEms == null) {
			rbEms = new JRadioButton();
			rbEms.setSelected(true);
			rbEms.setText("电子帐册");
			rbEms.setBounds(new Rectangle(73, 78, 107, 21));
			rbEms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							emsType="0";
						}
					});
		}
		return rbEms;
	}

	/**
	 * This method initializes rbMerger
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMerger() {
		if (rbMerger == null) {
			rbMerger = new JRadioButton();
			rbMerger.setText("归并关系");
			rbMerger.setBounds(new Rectangle(233, 79, 97, 21));
			rbMerger
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							emsType="1";
						}
					});
		}
		return rbMerger;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(78, 31, 416, 18));
			jLabel.setText("批量修改商品编码只针对分批申报或不是分批申报的正在执行的状态");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED),
					"\u8bf7\u9009\u62e9\u4fee\u6539\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel2.add(getRbEms(), null);
			jPanel2.add(getRbMerger(), null);
			jPanel2.add(getRbInternalMerger(), null);
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbInternalMerger
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbInternalMerger() {
		if (rbInternalMerger == null) {
			rbInternalMerger = new JRadioButton();
			rbInternalMerger.setText("内部归并关系");
			rbInternalMerger.setBounds(new Rectangle(380, 78, 123, 21));
			rbInternalMerger.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					emsType="2";
				}
			});
		}
		return rbInternalMerger;
	}

	/**
	 * This method initializes rbImg
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImg() {
		if (rbImg == null) {
			rbImg = new JRadioButton();
			rbImg.setBounds(new Rectangle(152, 26, 98, 21));
			rbImg.setText("料件");
			rbImg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isMaterial=true;
				}
			});
		}
		return rbImg;
	}

	/**
	 * This method initializes rbExg
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbExg() {
		if (rbExg == null) {
			rbExg = new JRadioButton();
			rbExg.setBounds(new Rectangle(343, 26, 61, 21));
			rbExg.setText("成品");
			rbExg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isMaterial=false;
				}
			});
		}
		return rbExg;
	}

	public Boolean checkSelectType(){
		if(!rbEms.isSelected()&& !rbMerger.isSelected()&& !rbInternalMerger.isSelected()){
			return true;
		}
		return false;
	}
	public Boolean checkSelectMateriel(){
		if(!rbImg.isSelected()&& !rbExg.isSelected()){
			return true;
		}
		return false;
	}
}  //  @jve:decl-index=0:visual-constraint="-342,78"
