package com.bestway.bcus.client.custombase;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 计量单位编辑编辑窗口
 * 
 * @author lm
 * 
 */
public class DgUnits extends JDialogBase {

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	private JLabel lbCode = null;
	private JLabel lbName = null;
	private JLabel lbUnitRatio = null;
	private JTextField tfCode = null;
	private JTextField tfName = null;
	private JTextField tfUnitRatio = null;
	private JCheckBox cbIsInt = null;
	private boolean isAdd = true;
	private JTableListModel tableModel = null;
	private Unit unit = null;
	private CustomBaseAction customBaseAction = null;
	private NumberFormatter numberFormatter = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgUnits() {
		super();
		initialize();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(353, 266));
		this.setContentPane(getJPanel());
		this.setTitle("计量单位编辑");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

			}
		});

	}

	/**
	 * 初始化组键及其状态
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					unit = (Unit) tableModel.getCurrentRow();
				}
				fillWindow();// 初始化数据
			} else {
				DgUnits.this.cbIsInt.setSelected(false);
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * 初始化数据
	 */
	private void fillWindow() {
		if (unit != null) {
			this.tfCode.setText(unit.getCode());
			this.tfName.setText(unit.getName());
			this.tfUnitRatio.setText(doubleToStr(unit.getUnitRatio()));
			this.cbIsInt.setSelected(unit.getIsMustInt() == null ? false : unit
					.getIsMustInt());
		}
	}

	/**
	 * 转换strToDouble 填充数据
	 * 
	 * @param value
	 * @return
	 */

	private Double strToDouble(String value) {
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

	/**
	 * 转换doubleToStr 取数据
	 * 
	 * @param value
	 * @return
	 */

	private String doubleToStr(Double value) {
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

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(999);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * 填充数据
	 */
	private void fillData(Unit un) {
		un.setCode(tfCode.getText());
		un.setName(tfName.getText());
		un.setUnitRatio(strToDouble(this.tfUnitRatio.getText()));
		Boolean isInt = false;
		if (this.cbIsInt.isSelected()) {
			isInt = true;
		}
		un.setIsMustInt(isInt);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbUnitRatio = new JLabel();
			lbUnitRatio.setBounds(new Rectangle(13, 105, 75, 28));
			lbUnitRatio.setText("比例因子");
			lbName = new JLabel();
			lbName.setBounds(new Rectangle(13, 62, 75, 28));
			lbName.setText("名称");
			lbCode = new JLabel();
			lbCode.setBounds(new Rectangle(14, 16, 75, 28));
			lbCode.setText("代码");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(28, 12, 290, 174));
			jPanel1.add(lbCode, null);
			jPanel1.add(lbName, null);
			jPanel1.add(lbUnitRatio, null);
			jPanel1.add(getTfCode(), null);
			jPanel1.add(getTfName(), null);
			jPanel1.add(getTfUnitRatio(), null);
			jPanel1.add(getCbIsInt(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new Rectangle(85, 197, 64, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isAdd == true) {
						Unit units = new Unit();
						fillData(units);
						units = customBaseAction.SaveUnit(units);
						tableModel.addRow(units);
						clearData();
					} else {
						fillData(unit);
						unit = customBaseAction.SaveUnit(unit);
						tableModel.updateRow(unit);
						DgUnits.this.dispose();
					}
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
			btnCancel.setBounds(new Rectangle(188, 197, 64, 24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgUnits.this.dispose();
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
			tfCode.setBounds(new Rectangle(89, 15, 175, 28));
			tfCode.setEditable(false);
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
			tfName.setBounds(new Rectangle(89, 63, 175, 28));
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes tfUnitRatio
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnitRatio() {
		if (tfUnitRatio == null) {
			tfUnitRatio = new JTextField();
			tfUnitRatio.setBounds(new Rectangle(89, 106, 175, 28));
			tfUnitRatio.setEditable(false);
		}
		return tfUnitRatio;
	}

	/**
	 * This method initializes cbIsInt
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsInt() {
		if (cbIsInt == null) {
			cbIsInt = new JCheckBox();
			cbIsInt.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			cbIsInt.setText("是否必须整数");
			cbIsInt.setBounds(new Rectangle(8, 139, 111, 28));
		}
		return cbIsInt;
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
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
	 */
	private void clearData() {
		this.tfCode.setText("");
		this.tfName.setText("");
		this.tfUnitRatio.setText("");

	}

} // @jve:decl-index=0:visual-constraint="10,10"
