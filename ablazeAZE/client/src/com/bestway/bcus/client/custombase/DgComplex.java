package com.bestway.bcus.client.custombase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgComplex extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel lbCode = null;

	private JLabel lbName = null;

	private JLabel lbFirstUnit = null;

	private JLabel lbSecondUnit = null;

	private JLabel lbNote = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JTextField tfCode = null;

	private JTextField tfName = null;

	private JComboBox cbbFirstUnit = null;

	private JComboBox cbbSecondUnit = null;

	private JTextArea tfNote = null;

	private boolean isOk = false;

	private Complex complex = null;

	private JScrollPane jScrollPane = null;

	private CustomBaseAction customBaseAction = null;

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private ButtonGroup buttonGroup = new ButtonGroup(); // @jve:decl-index=0:

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgComplex() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initialize();
		initUIComponents();
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(544, 264));
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("商品编码资料编辑");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(132, 21, 338, 18));
			jLabel1.setText("如：备案资料、报关单模块等。请谨慎使用此功能");
			jLabel1.setForeground(Color.BLUE);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(95, 3, 399, 18));
			jLabel.setText("注意：修改以下内容将会更改所有用到商品编码的模块");
			jLabel.setForeground(Color.BLUE);
			lbNote = new JLabel();
			lbNote.setBounds(new Rectangle(14, 103, 38, 18));
			lbNote.setText("备注");
			lbSecondUnit = new JLabel();
			lbSecondUnit.setBounds(new Rectangle(271, 64, 76, 18));
			lbSecondUnit.setText("第二法定单位");
			lbFirstUnit = new JLabel();
			lbFirstUnit.setBounds(new Rectangle(12, 66, 74, 18));
			lbFirstUnit.setText("第一法定单位");
			lbName = new JLabel();
			lbName.setBounds(new Rectangle(270, 37, 75, 18));
			lbName.setText("商品名称");
			lbCode = new JLabel();
			lbCode.setBounds(new Rectangle(11, 42, 66, 18));
			lbCode.setText("商品编码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbCode, null);
			jContentPane.add(lbName, null);
			jContentPane.add(lbFirstUnit, null);
			jContentPane.add(lbSecondUnit, null);
			jContentPane.add(lbNote, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTfCode(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(getCbbFirstUnit(), null);
			jContentPane.add(getCbbSecondUnit(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(189, 188, 80, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgComplex.this,
							"修改此内容将会更改所有用到商品编码的模块如：备案资料、报关单等，确定要修改吗？", "提示！",
							JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					fillData();
					/*
					 * if (customBaseAction .checkComplexCode(tfCode.getText()
					 * == null ? "" : tfCode.getText())) {
					 * JOptionPane.showMessageDialog(DgComplex.this,
					 * "   所填商品编码已存在！", "提示！", JOptionPane.WARNING_MESSAGE);
					 * return; }
					 */
					customBaseAction.saveComplex(complex);
					isOk = true;
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(299, 188, 79, 23));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isOk = false;
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new Rectangle(89, 43, 156, 22));
			// tfCode.setEditable(false);
		}
		return tfCode;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(357, 38, 150, 22));
		}
		return tfName;
	}

	/**
	 * This method initializes cbbFirstUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFirstUnit() {
		if (cbbFirstUnit == null) {
			cbbFirstUnit = new JComboBox();
			cbbFirstUnit.setBounds(new Rectangle(89, 70, 156, 22));
		}
		return cbbFirstUnit;
	}

	/**
	 * This method initializes cbbSecondUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSecondUnit() {
		if (cbbSecondUnit == null) {
			cbbSecondUnit = new JComboBox();
			cbbSecondUnit.setBounds(new Rectangle(357, 64, 150, 22));
		}
		return cbbSecondUnit;
	}

	/**
	 * This method initializes tfNote
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextArea();
		}
		return tfNote;
	}

	private void initUIComponents() {
		// 初始化第一单位
		this.cbbFirstUnit
				.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbFirstUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFirstUnit);
		// 初始化第二单位
		this.cbbSecondUnit.setModel(CustomBaseModel.getInstance()
				.getUnitModel());
		this.cbbSecondUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbSecondUnit);

	}

	// 填充动态ComboBox
	public void intoComboBox(List list, JComboBox jComboBox) {
		jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		jComboBox.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"code", "name");
		jComboBox.setSelectedIndex(-1);
	}

	private void showData() {
		if (complex == null) {
			return;
		}
		this.tfCode.setText(complex.getCode());
		this.tfName.setText(complex.getName());

		this.tfNote.setText(complex.getNote());
		this.cbbFirstUnit.setSelectedItem(complex.getFirstUnit());
		this.cbbSecondUnit.setSelectedItem(complex.getSecondUnit());
		// jComboBox.setSelectedItem(complex.getComUnit());
		// if (complex.getScmCoi()!= null){
		// jComboBox2.setSelectedIndex(ItemProperty.getIndexByCode(complex.getScmCoi(),
		// jComboBox2));
		// } else {
		// jComboBox2.setSelectedIndex(-1);
		// }
		// if (complex.getType()!=null){
		// jComboBox4.setSelectedIndex(ItemProperty.getIndexByCode(complex.getType(),
		// jComboBox4));
		// } else {
		// jComboBox4.setSelectedIndex(-1);
		// }
		// jFormattedTextField.setText(doubleToStr(complex.getUnitWeight()));
		// jFormattedTextField1.setText(doubleToStr(complex.getPrice()));
		// jFormattedTextField2.setText(doubleToStr(complex.getMachPrice()));
		// jComboBox1.setSelectedItem(complex.getCurr());
		// jComboBox3.setSelectedItem(complex.getCountry());
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void fillData() {
		if (complex == null) {
			return;
		}
		this.complex.setCode(this.tfCode.getText());
		this.complex.setName(this.tfName.getText());
		this.complex.setNote(this.tfNote.getText());
		this.complex.setFirstUnit((Unit) this.cbbFirstUnit.getSelectedItem());
		this.complex.setSecondUnit((Unit) this.cbbSecondUnit.getSelectedItem());
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(89, 98, 423, 65));
			jScrollPane.setViewportView(getTfNote());
		}
		return jScrollPane;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
