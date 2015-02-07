/*
 * Created on 2005-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempStatCusNameRelation;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgMtConversionRate extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfMaterialCode = null;

	private JLabel jLabel1 = null;

	private JTextField tfMaterialName = null;

	private JTextField tfMaterialSpec = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JCustomFormattedTextField tfConversionRate = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JLabel jLabel4 = null;

	private JTextField tfMaterialUnit = null;

	private JLabel jLabel5 = null;

	private JTextField tfRelationUnit = null;

	private TempStatCusNameRelation tempStatCusNameRelation = null;

	private CasAction casAction = null;
	
	private boolean isOk =false;
	
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:
	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}
	
	/**
	 * @return Returns the tempStatCusNameRelation.
	 */
	public TempStatCusNameRelation getTempStatCusNameRelation() {
		return tempStatCusNameRelation;
	}

	/**
	 * @param tempStatCusNameRelation
	 *            The tempStatCusNameRelation to set.
	 */
	public void setTempStatCusNameRelation(
			TempStatCusNameRelation tempStatCusNameRelation) {
		this.tempStatCusNameRelation = tempStatCusNameRelation;
	}

	/**
	 * This is the default constructor
	 */
	public DgMtConversionRate() {
		super();
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("工厂物料和大类之间单位换算率修改 ");
		this.setSize(394, 295);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(56, 30, 75, 23);
			jLabel.setText("物料编号");
			jLabel1.setBounds(56, 58, 75, 23);
			jLabel1.setText("物料名称");
			jLabel2.setBounds(56, 86, 75, 23);
			jLabel2.setText("物料规格");
			jLabel3.setBounds(56, 174, 75, 23);
			jLabel3.setText("单位换算率");
			jLabel4.setBounds(56, 116, 75, 23);
			jLabel4.setText("物料单位");
			jLabel5.setBounds(56, 145, 75, 23);
			jLabel5.setText("大类单位");
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfMaterialCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfMaterialName(), null);
			jContentPane.add(getTfMaterialSpec(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfConversionRate(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfMaterialUnit(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfRelationUnit(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialCode() {
		if (tfMaterialCode == null) {
			tfMaterialCode = new JTextField();
			tfMaterialCode.setBounds(132, 30, 192, 23);
			tfMaterialCode.setEditable(false);
			tfMaterialCode.setBackground(java.awt.Color.white);
		}
		return tfMaterialCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialName() {
		if (tfMaterialName == null) {
			tfMaterialName = new JTextField();
			tfMaterialName.setBounds(132, 58, 192, 23);
			tfMaterialName.setEditable(false);
			tfMaterialName.setBackground(java.awt.Color.white);
		}
		return tfMaterialName;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialSpec() {
		if (tfMaterialSpec == null) {
			tfMaterialSpec = new JTextField();
			tfMaterialSpec.setBounds(132, 86, 192, 23);
			tfMaterialSpec.setEditable(false);
			tfMaterialSpec.setBackground(java.awt.Color.white);
		}
		return tfMaterialSpec;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfConversionRate() {
		if (tfConversionRate == null) {
			tfConversionRate = new JCustomFormattedTextField();
			tfConversionRate.setBounds(132, 174, 192, 23);
			tfConversionRate.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfConversionRate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(176, 213, 69, 26);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tempStatCusNameRelation == null) {
						return;
					}
					fillData();
					casAction
							.saveStatCusNameRelationMt(new Request(CommonVars
									.getCurrUser()), tempStatCusNameRelation
									.getStatCusNameRelationMt());
					isOk=true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(253, 213, 69, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialUnit() {
		if (tfMaterialUnit == null) {
			tfMaterialUnit = new JTextField();
			tfMaterialUnit.setBounds(132, 116, 192, 23);
			tfMaterialUnit.setEditable(false);
			tfMaterialUnit.setBackground(java.awt.Color.white);
		}
		return tfMaterialUnit;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRelationUnit() {
		if (tfRelationUnit == null) {
			tfRelationUnit = new JTextField();
			tfRelationUnit.setBounds(132, 145, 192, 23);
			tfRelationUnit.setEditable(false);
			tfRelationUnit.setBackground(java.awt.Color.white);
		}
		return tfRelationUnit;
	}

	private void fillData() {
		if (this.tempStatCusNameRelation == null) {
			return;
		}
		if (this.tfConversionRate.getValue() == null) {
			this.tempStatCusNameRelation.getStatCusNameRelationMt()
					.setUnitConvert(0.0);
		} else {
			this.tempStatCusNameRelation.getStatCusNameRelationMt()
					.setUnitConvert(
							Double.parseDouble(this.tfConversionRate.getValue()
									.toString()));
		}
	}

	private void showData() {
		if (this.tempStatCusNameRelation == null) {
			return;
		}
		if (this.tempStatCusNameRelation.getStatCusNameRelationMt()
				.getMateriel() != null) {
			this.tfMaterialCode.setText(this.tempStatCusNameRelation
					.getStatCusNameRelationMt().getMateriel().getPtNo());
			this.tfMaterialName.setText(this.tempStatCusNameRelation
					.getStatCusNameRelationMt().getMateriel().getFactoryName());
			if (this.tempStatCusNameRelation.getStatCusNameRelationMt()
					.getMateriel().getFactorySpec() != null) {
				this.tfMaterialSpec.setText(this.tempStatCusNameRelation
						.getStatCusNameRelationMt().getMateriel().getFactorySpec());
			} else {
				this.tfMaterialSpec.setText("");
			}
			if (this.tempStatCusNameRelation.getStatCusNameRelationMt()
					.getMateriel().getCalUnit() != null
					&&this.tempStatCusNameRelation.getStatCusNameRelationMt()
					.getMateriel().getCalUnit().getUnit()!=null
					&& this.tempStatCusNameRelation.getStatCusNameRelationMt()
							.getMateriel().getCalUnit().getUnit().getName() != null) {
				this.tfMaterialUnit.setText(this.tempStatCusNameRelation.getStatCusNameRelationMt()
						.getMateriel().getCalUnit().getUnit().getName());
			} else {
				this.tfMaterialUnit.setText("");
			}
		} else {
			this.tfMaterialCode.setText("");
			this.tfMaterialName.setText("");
			this.tfMaterialSpec.setText("");
			this.tfMaterialUnit.setText("");
		}

		if (this.tempStatCusNameRelation.getStatCusNameRelation() != null
				&& this.tempStatCusNameRelation.getStatCusNameRelation()
						.getCusUnit() != null
				&& this.tempStatCusNameRelation.getStatCusNameRelation()
						.getCusUnit().getName() != null) {
			this.tfRelationUnit.setText(this.tempStatCusNameRelation
					.getStatCusNameRelation().getCusUnit().getName());
		} else {
			this.tfRelationUnit.setText("");
		}
		this.tfConversionRate.setValue(this.tempStatCusNameRelation
				.getStatCusNameRelationMt().getUnitConvert());
	}
	/**
	 * This method initializes defaultFormatterFactory	
	 * 	
	 * @return javax.swing.text.DefaultFormatterFactory	
	 */    
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}
	/**
	 * This method initializes numberFormatter	
	 * 	
	 * @return javax.swing.text.NumberFormatter	
	 */    
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}
  } // @jve:decl-index=0:visual-constraint="10,10"
