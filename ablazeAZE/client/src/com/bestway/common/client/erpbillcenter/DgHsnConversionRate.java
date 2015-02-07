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
public class DgHsnConversionRate extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JTextField tfHsnCode = null;

	private JLabel jLabel1 = null;

	private JTextField tfHsnName = null;

	private JTextField tfHsnSpec = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JCustomFormattedTextField tfConversionRate = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JLabel jLabel4 = null;

	private JTextField tfHsnUnit = null;

	private JLabel jLabel5 = null;

	private JTextField tfRelationUnit = null;

	private TempStatCusNameRelation tempStatCusNameRelation = null;

	private CasAction casAction = null;

	private JLabel jLabel6 = null;

	private JTextField tfCommSerialNo = null;
	
	private boolean isOk =false;
	
	private DefaultFormatterFactory defaultFormatterFactory = null;   //  @jve:decl-index=0:
	private NumberFormatter numberFormatter = null;   //  @jve:decl-index=0:

	private JLabel jLabel7 = null;
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
	public DgHsnConversionRate() {
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
		this.setTitle("大类与报关之间单位换算率修改 ");
		this.setSize(428, 314);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(57,229,110,22));
			jLabel7.setText("换算率=大类/报关");
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(56, 57, 75, 23);
			jLabel.setText("商品编号");
			jLabel1.setBounds(56, 85, 75, 23);
			jLabel1.setText("商品名称");
			jLabel2.setBounds(56, 113, 75, 23);
			jLabel2.setText("商品规格");
			jLabel3.setBounds(56, 201, 75, 23);
			jLabel3.setText("单位换算率");
			jLabel4.setBounds(56, 143, 75, 23);
			jLabel4.setText("商品单位");
			jLabel5.setBounds(56, 172, 75, 23);
			jLabel5.setText("大类单位");
			jLabel6.setBounds(56, 29, 75, 23);
			jLabel6.setText("商品序号");
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfHsnCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfHsnName(), null);
			jContentPane.add(getTfHsnSpec(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getTfConversionRate(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfHsnUnit(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfRelationUnit(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getTfCommSerialNo(), null);
			jContentPane.add(jLabel7, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsnCode() {
		if (tfHsnCode == null) {
			tfHsnCode = new JTextField();
			tfHsnCode.setBounds(132, 57, 226, 23);
			tfHsnCode.setEditable(false);
			tfHsnCode.setBackground(java.awt.Color.white);
		}
		return tfHsnCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsnName() {
		if (tfHsnName == null) {
			tfHsnName = new JTextField();
			tfHsnName.setBounds(132, 85, 226, 23);
			tfHsnName.setEditable(false);
			tfHsnName.setBackground(java.awt.Color.white);
		}
		return tfHsnName;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHsnSpec() {
		if (tfHsnSpec == null) {
			tfHsnSpec = new JTextField();
			tfHsnSpec.setBounds(132, 113, 226, 23);
			tfHsnSpec.setEditable(false);
			tfHsnSpec.setBackground(java.awt.Color.white);
		}
		return tfHsnSpec;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfConversionRate() {
		if (tfConversionRate == null) {
			tfConversionRate = new JCustomFormattedTextField();
			tfConversionRate.setBounds(132, 201, 226, 23);
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
			btnOk.setBounds(218, 234, 60, 26);
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tempStatCusNameRelation == null) {
						return;
					}
					fillData();
                    casAction.saveStatCusNameRelationHsn(new Request(CommonVars
							.getCurrUser()), tempStatCusNameRelation
							.getStatCusNameRelationHsn());
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
			btnCancel.setBounds(288, 234, 60, 26);
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
	private JTextField getTfHsnUnit() {
		if (tfHsnUnit == null) {
			tfHsnUnit = new JTextField();
			tfHsnUnit.setBounds(132, 143, 226, 23);
			tfHsnUnit.setEditable(false);
			tfHsnUnit.setBackground(java.awt.Color.white);
		}
		return tfHsnUnit;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRelationUnit() {
		if (tfRelationUnit == null) {
			tfRelationUnit = new JTextField();
			tfRelationUnit.setBounds(132, 172, 226, 23);
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
			this.tempStatCusNameRelation.getStatCusNameRelationHsn()
					.setUnitConvert(0.0);
		} else {
			this.tempStatCusNameRelation.getStatCusNameRelationHsn()
					.setUnitConvert(
							Double.parseDouble(this.tfConversionRate.getValue()
									.toString()));
		}
	}

	private void showData() {
		if (this.tempStatCusNameRelation == null) {
			return;
		}
		this.tfCommSerialNo.setText(this.tempStatCusNameRelation
				.getStatCusNameRelationHsn().getSeqNum().toString());

		if (this.tempStatCusNameRelation.getStatCusNameRelationHsn()
				.getComplex()!= null) {
			this.tfHsnCode.setText(this.tempStatCusNameRelation
					.getStatCusNameRelationHsn().getComplex().getCode());
		} else {
			this.tfHsnCode.setText("");
		}
		if (this.tempStatCusNameRelation.getStatCusNameRelationHsn()
				.getCusName() != null) {
			this.tfHsnName.setText(this.tempStatCusNameRelation
					.getStatCusNameRelationHsn().getCusName());
		} else {
			this.tfHsnName.setText("");
		}
		if (this.tempStatCusNameRelation.getStatCusNameRelationHsn()
				.getCusSpec() != null) {

			this.tfHsnSpec.setText(this.tempStatCusNameRelation
					.getStatCusNameRelationHsn().getCusSpec());
		} else {
			this.tfHsnSpec.setText("");
		}
		if (this.tempStatCusNameRelation.getStatCusNameRelationHsn()
				.getCusUnit() != null) {
			this.tfHsnUnit.setText(this.tempStatCusNameRelation
					.getStatCusNameRelationHsn().getCusUnit().getName());
		} else {
			this.tfHsnUnit.setText("");
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
				.getStatCusNameRelationHsn().getUnitConvert());
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommSerialNo() {
		if (tfCommSerialNo == null) {
			tfCommSerialNo = new JTextField();
			tfCommSerialNo.setBounds(132, 29, 226, 23);
			tfCommSerialNo.setEditable(false);
			tfCommSerialNo.setBackground(java.awt.Color.white);
		}
		return tfCommSerialNo;
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
