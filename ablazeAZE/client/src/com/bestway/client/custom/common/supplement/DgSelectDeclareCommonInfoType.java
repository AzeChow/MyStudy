package com.bestway.client.custom.common.supplement;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.constant.RepDeclarationType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTextField;

public class DgSelectDeclareCommonInfoType extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JComboBox cbSupType = null;
	private JLabel jLabel3 = null;
	private JButton btnPrev = null;
	private JButton btnNext = null;
	private Integer supplmentType;
	private BaseCustomsDeclaration baseCustomsDeclaration;
	private BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo;
	public Integer result = Integer.valueOf(0);  //  @jve:decl-index=0:
	private JTextField tfSerialNumber = null;
	private String supType =null;  //  @jve:decl-index=0:
	private JTextField tfcommSerialNo = null;
	private JComboBox cbSupplementType = null;
	public String getSupType() {
		return supType;
	}

	public void setSupType(String supType) {
		this.supType = supType;
	}

	/**
	 * This is the default constructor
	 */
	public DgSelectDeclareCommonInfoType(Integer supplmentType, BaseCustomsDeclaration baseCustomsDeclaration,BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo) {
		super();
		this.supplmentType = supplmentType;
		this.baseCustomsDeclaration =baseCustomsDeclaration;
		this.baseCustomsDeclarationCommInfo = baseCustomsDeclarationCommInfo;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(397, 225);
		this.setContentPane(getJContentPane());
		this.setTitle("选择商品申报补充类型");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
			}
		});
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				result = Integer.valueOf(0);
			}
		});
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(60, 110, 97, 18));
			jLabel3.setText("申报补充类型：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(60, 67, 86, 18));
			jLabel2.setText("补充报关类型：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(219, 30, 61, 18));
			jLabel1.setText("备案序号：");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(60, 30, 93, 18));
			jLabel.setText("报关单流水号：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbSupType(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getBtnPrev(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(getTfSerialNumber(), null);
			jContentPane.add(getTfcommSerialNo(), null);
			jContentPane.add(getCbSupplementType(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cbSupType	
	 * 	
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbSupType() {
		if (cbSupType == null) {
			cbSupType = new JComboBox();
			cbSupType.setBounds(new Rectangle(164, 108, 130, 22));
			cbSupType.addItem(new ItemProperty(
					RepDeclarationType.SupTypePrice,
					RepDeclarationType.getSupType(RepDeclarationType.SupTypePrice)		
			));
			cbSupType.addItem(new ItemProperty(
					RepDeclarationType.SupTypeMerger,
					RepDeclarationType.getSupType(RepDeclarationType.SupTypeMerger)		
			));
			cbSupType.addItem(new ItemProperty(
					RepDeclarationType.SupTypeOrigin,
					RepDeclarationType.getSupType(RepDeclarationType.SupTypeOrigin)		
			));
			if(supplmentType != null) {
				cbSupType.setSelectedItem(new ItemProperty(supplmentType
						.toString(), SupplmentType
						.getSupplmentTypeDesc(supplmentType)));
			} else {
				cbSupType.setSelectedItem(new ItemProperty(
						SupplmentType.PASSIVITY_SYPPLMENT.toString(),
						SupplmentType
								.getSupplmentTypeDesc(SupplmentType.PASSIVITY_SYPPLMENT)));
			}
			//cbSupType.setSelectedIndex(supplmentType);
			setSupType(((ItemProperty)(cbSupType.getSelectedItem())).getCode());
			cbSupType
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setSupType(((ItemProperty)(cbSupType.getSelectedItem())).getCode());
				}
			});
		}
		return cbSupType;
	}

	/**
	 * This method initializes btnPrev	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton();
			btnPrev.setBounds(new Rectangle(100, 150, 80, 20));
			btnPrev.setText("上一步");
			btnPrev.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
					result = -1;
				}
			});
		}
		return btnPrev;
	}

	/**
	 * This method initializes btnNext	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(210, 150, 80, 20));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					result = 1;
					dispose();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes tfSerialNumber	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfSerialNumber() {
		if (tfSerialNumber == null) {
			tfSerialNumber = new JTextField();
			tfSerialNumber.setEnabled(false);
			tfSerialNumber.setText(baseCustomsDeclaration.getSerialNumber().toString());
			tfSerialNumber.setBounds(new Rectangle(149, 28, 69, 22));
		}
		return tfSerialNumber;
	}

	/**
	 * This method initializes tfcommSerialNo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfcommSerialNo() {
		if (tfcommSerialNo == null) {
			tfcommSerialNo = new JTextField();
			tfcommSerialNo.setText(baseCustomsDeclarationCommInfo.getCommSerialNo().toString());
			tfcommSerialNo.setEnabled(false);
			tfcommSerialNo.setBounds(new Rectangle(286, 28, 69, 22));
		}
		return tfcommSerialNo;
	}

	/**
	 * This method initializes cbSupplementType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbSupplementType() {
		if (cbSupplementType == null) {
			cbSupplementType = new JComboBox();
			cbSupplementType.setEnabled(false);
			cbSupplementType.setBounds(new Rectangle(160, 66, 130, 22));
			cbSupplementType
					.addItem(new ItemProperty(
							SupplmentType.INITIATIVE_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.INITIATIVE_SYPPLMENT)));
			cbSupplementType
					.addItem(new ItemProperty(
							SupplmentType.PASSIVITY_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.PASSIVITY_SYPPLMENT)));
			cbSupplementType.setSelectedIndex(0);
			if(supplmentType != null) {
				cbSupplementType.setSelectedItem(new ItemProperty(supplmentType
						.toString(), SupplmentType
						.getSupplmentTypeDesc(supplmentType)));
			} else {
				cbSupplementType.setSelectedItem(new ItemProperty(
						SupplmentType.PASSIVITY_SYPPLMENT.toString(),
						SupplmentType
								.getSupplmentTypeDesc(SupplmentType.PASSIVITY_SYPPLMENT)));
			}
		}
		return cbSupplementType;
	}
	


}  //  @jve:decl-index=0:visual-constraint="170,103"
