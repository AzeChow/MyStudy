/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscFactoryCustoms extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private JTextField jtcustomUnit = null;

	private JTextField jtcode = null;

	private JTextField jFormattedTextField = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JLabel jLabel4 = null;

	private JTextField jTextField = null;

	private JLabel jLabel5 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel6 = null;

	private JTextField jTextField2 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JLabel jLabel12 = null;

	private JTextField jFormattedTextField1 = null;

	private DzscInnerMergeData data;

	private Complex afterComplex = null; // 商品编码

	private Unit afterMemoUnit = null; // 申报计量单位

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton btnOk = null;

	private DzscInnerMergeAction dzscInnerMergeAction = null;

	/**
	 * This is the default constructor
	 */
	public DgDzscFactoryCustoms() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		dzscInnerMergeAction = (DzscInnerMergeAction) CommonVars
				.getApplicationContext().getBean("dzscInnerMergeAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(478, 335);
		this.setTitle("报关/工厂资料编辑");
	}

	public void setVisible(boolean isFlag) {
		if (tableModel.getCurrentRow() != null) {
			data = (DzscInnerMergeData) tableModel.getCurrentRow();
		}
		fillWindow();
		super.setVisible(isFlag);

	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();

			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("物料编码");
			jLabel.setBounds(32, 42, 52, 23);
			jLabel2.setText("物料名称");
			jLabel2.setBounds(243, 42, 57, 23);
			jLabel1.setText("物料单位");
			jLabel1.setBounds(32, 74, 52, 22);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 106;
			gridBagConstraints2.insets = new java.awt.Insets(25, 1, 5, 44);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 9;
			gridBagConstraints3.ipady = 5;
			gridBagConstraints3.insets = new java.awt.Insets(5, 42, 6, 25);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.ipadx = 105;
			gridBagConstraints4.insets = new java.awt.Insets(7, 1, 5, 45);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.ipadx = 13;
			gridBagConstraints5.ipady = -2;
			gridBagConstraints5.insets = new java.awt.Insets(8, 48, 18, 5);
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.ipadx = 12;
			gridBagConstraints6.ipady = -3;
			gridBagConstraints6.insets = new java.awt.Insets(9, 37, 18, 0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.ipadx = 10;
			gridBagConstraints7.ipady = 4;
			gridBagConstraints7.insets = new java.awt.Insets(6, 41, 8, 1);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.ipadx = 104;
			gridBagConstraints8.insets = new java.awt.Insets(5, 2, 9, 45);
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = -18;
			gridBagConstraints9.ipady = -7;
			gridBagConstraints9.insets = new java.awt.Insets(5, 1, 10, 22);
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.ipadx = 104;
			gridBagConstraints10.insets = new java.awt.Insets(23, 1, 7, 46);
			jLabel3.setBounds(32, 104, 53, 20);
			jLabel3.setText("单位折算");
			jLabel4.setBounds(32, 160, 53, 23);
			jLabel4.setText("报关编码");
			jLabel5.setBounds(243, 72, 52, 22);
			jLabel5.setText("物料规格");
			jLabel6.setBounds(32, 190, 51, 23);
			jLabel6.setText("报关单位");
			jLabel7.setBounds(243, 161, 57, 22);
			jLabel7.setText("报关名称");
			jLabel8.setBounds(15, 236, 200, 21);
			jLabel8.setText("注：单位折算=报关数量/工厂数量");
			jLabel8.setForeground(new java.awt.Color(51, 0, 255));
			jLabel9.setBounds(15, 15, 88, 23);
			jLabel9.setText("工厂物料资料");
			jLabel9.setForeground(java.awt.Color.red);
			jLabel10.setBounds(15, 131, 82, 24);
			jLabel10.setText("报关商品资料");
			jLabel10.setForeground(java.awt.Color.red);
			jLabel11.setBounds(243, 189, 56, 23);
			jLabel11.setText("报关规格");
			jLabel12.setBounds(243, 102, 55, 21);
			jLabel12.setText("单重");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJTextField3(), null);
			jPanel2.add(getJtcode(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJFormattedTextField(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getJTextField22(), null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(jLabel8, null);
			jPanel2.add(jLabel9, null);
			jPanel2.add(jLabel10, null);
			jPanel2.add(jLabel11, null);
			jPanel2.add(getJTextField32(), null);
			jPanel2.add(getJTextField4(), null);
			jPanel2.add(jLabel12, null);
			jPanel2.add(getJFormattedTextField1(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJButton4(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField2() {
		if (jtname == null) {
			jtname = new JTextField();
			jtname.setEditable(false);
			jtname.setBounds(305, 43, 137, 22);
		}
		return jtname;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton1() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setBounds(282, 265, 70, 23);
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgDzscFactoryCustoms.this.dispose();

				}

			});

		}
		return btnCancel;
	}

	private void fillWindow() {
		this.afterComplex = data.getDzscTenInnerMerge().getTenComplex();
		this.afterMemoUnit = data.getDzscTenInnerMerge().getTenUnit();
		jtcode.setText(data.getMateriel().getPtNo());
		jtname.setText(data.getMateriel().getFactoryName());
		jtcustomUnit.setText(data.getMateriel().getCalUnit() == null ? ""
				: data.getMateriel().getCalUnit().getName());
		tfSpec.setText(data.getMateriel().getFactorySpec());
		jTextField.setText(data.getDzscTenInnerMerge().getTenComplex()
				.getCode());
		jTextField3.setText(data.getDzscTenInnerMerge().getTenPtName());
		jTextField2.setText(data.getDzscTenInnerMerge().getTenUnit().getName());
		jTextField4.setText(data.getDzscTenInnerMerge().getTenPtSpec());
		this.jFormattedTextField.setText(data.getUnitConvert() == null ? ""
				: ((Double) data.getUnitConvert()).toString());
		jFormattedTextField1
				.setText(data.getMateriel().getPtNetWeight() == null ? ""
						: ((Double) data.getMateriel().getPtNetWeight())
								.toString());
	}

	private boolean checkData() {
		boolean is = true;
		Double dou = null;
		Double dou1 = null;
		try {
			dou = new Double(jFormattedTextField.getText());

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "单位折算填写错误，只能是数字!", "提示！",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		try {
			dou1 = new Double(jFormattedTextField1.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "单重填写错误，只能是数字!", "提示！",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return is;
	}

	private DzscInnerMergeData fillData() {
		DzscInnerMergeData dimd = (DzscInnerMergeData) this.tableModel
				.getCurrentRow();
		dimd.setUnitConvert(new Double(jFormattedTextField.getText()));
		dimd.getMateriel().setPtNetWeight(
				new Double(jFormattedTextField1.getText()));
		// dimd.setTenPtName(jTextField3.getText() == null ? "" : jTextField3
		// .getText());
		// dimd.setTenPtSpec(jTextField4.getText() == null ? "" : jTextField4
		// .getText());
		// dimd.setTenComplex(this.afterComplex);
		// dimd.setTenUnit(this.afterMemoUnit);
		return dimd;
	}

	private void clearData() {
		this.jtname.setText("");
		this.jtcode.setText("");
		jTextField.setText("");
		this.jFormattedTextField.setText("1");
		this.jtcustomUnit.setText("");
		this.tfSpec.setText("");
		this.jTextField2.setText("");
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * 
	 * This method initializes jtcustomUnit
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField3() {
		if (jtcustomUnit == null) {
			jtcustomUnit = new JTextField();
			jtcustomUnit.setEditable(false);
			jtcustomUnit.setBounds(91, 73, 125, 22);

		}
		return jtcustomUnit;
	}

	/**
	 * 
	 * This method initializes jtcode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJtcode() {
		if (jtcode == null) {
			jtcode = new JTextField();
			jtcode.setEditable(false);
			jtcode.setBounds(91, 43, 126, 22);
		}
		return jtcode;
	}

	/**
	 * 
	 * This method initializes jFormattedTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 * 
	 */
	private JTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new JTextField();
			jFormattedTextField.setBounds(91, 103, 125, 22);
		}
		return jFormattedTextField;
	}

	/**
	 * @return Returns the jtname.
	 */
	public JTextField getJtname() {
		return jtname;
	}

	/**
	 * @param jtname
	 *            The jtname to set.
	 */
	public void setJtname(JTextField jtname) {
		this.jtname = jtname;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jTextField.setEditable(false);
			jTextField.setBounds(91, 161, 124, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setEditable(false);
			tfSpec.setBounds(305, 73, 136, 22);
		}
		return tfSpec;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField22() {
		if (jTextField2 == null) {
			jTextField2 = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jTextField2.setEditable(false);
			jTextField2.setBounds(91, 190, 124, 22);
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField32() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			// jTextField3.setEditable(false);
			jTextField3.setBounds(305, 160, 137, 22);
		}
		return jTextField3;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			// jTextField4.setEditable(false);
			jTextField4.setBounds(305, 190, 137, 22);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField getJFormattedTextField1() {
		if (jFormattedTextField1 == null) {
			jFormattedTextField1 = new JTextField();
			jFormattedTextField1.setBounds(305, 102, 137, 22);
		}
		return jFormattedTextField1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(212, 161, 28, 22));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex complex = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (complex != null) {
						afterComplex = complex;
						jTextField.setText(complex.getCode());
						jTextField3.setText(complex.getName());
					}
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
			jButton3 = new JButton();
			jButton3.setBounds(new java.awt.Rectangle(214, 189, 27, 23));
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Unit c = (Unit) CommonQuery.getInstance().getCustomUnit();
					if (c != null) {
						afterMemoUnit = c;
						jTextField2.setText(c.getName());
					}
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
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(106, 265, 70, 23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkData()) {
						return;
					}
					DzscInnerMergeData dim = fillData();
					Materiel materiel = dim.getMateriel();
					materiel = materialManageAction.saveMateriel(new Request(
							CommonVars.getCurrUser()), materiel);
					dzscInnerMergeAction
							.saveDzscInnerMergeDataInFactoryAndCustoms(
									new Request(CommonVars.getCurrUser()), dim);
					DgDzscFactoryCustoms.this.tableModel.updateRow(dim);
					DgDzscFactoryCustoms.this.dispose();
				}
			});
		}
		return btnOk;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
