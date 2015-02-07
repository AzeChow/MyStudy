/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCalUnit extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private CalUnit unit = null; // 企业

	private Unit customunit = null; // 海关  //  @jve:decl-index=0:

	private JTextField jtcustomUnit = null;

	private JButton jButton2 = null;

	private JTextField jtcode = null;

	private JFormattedTextField jFormattedTextField = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JButton jButton3 = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="111,249"

	private JMenuItem jMenuItem = null; // @jve:decl-index=0:visual-constraint="485,215"

	private JTextArea jTextArea = null;

	/**
	 * This is the default constructor
	 */
	public DgCalUnit() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(366, 188);
		this.setTitle("计量单位编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						unit = (CalUnit) tableModel.getCurrentRow();
						customunit = unit.getUnit();
					}
					fillWindow();
				} else {
					DgCalUnit.this.jFormattedTextField.setText("1");
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel3 = new JLabel();
			jLabel3.setVisible(false);
			javax.swing.JLabel jLabel1 = new JLabel();
			jLabel1.setVisible(false);

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("工厂单位编码");
			jLabel.setBounds(42, 24, 92, 23);
			jLabel2.setText("工厂单位名称");
			jLabel2.setBounds(42, 50, 89, 23);
			jLabel1.setText("报关单位资料");
			jLabel1.setBounds(43, 82, 82, 22);
			jLabel3.setBounds(43, 110, 96, 20);
			jLabel3.setText("单位对照比例因子");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJTextField3(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJtcode(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJFormattedTextField(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJTextArea(), null);
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
			jtname.setBounds(141, 52, 171, 22);
		}
		return jtname;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定(S)");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_S);
			jButton.setActionCommand("取消(X)");
			jButton.setBounds(79, 99, 76, 23);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					if (isAdd == true) {
						addData();
						clearData();
					} else {
						modifyData();
						DgCalUnit.this.dispose();
					}

				}
			});

		}
		return jButton;
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
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消(x)");
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_X);
			jButton1.setActionCommand("取消(X)");
			jButton1.setBounds(192, 99, 76, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCalUnit.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (unit != null) {
			this.jtcode.setText(unit.getCode());
			this.jtname.setText(unit.getName());
			this.jFormattedTextField.setText(doubleToStr(unit.getScale()));
			if (this.customunit != null) {
				this.jtcustomUnit.setText(customunit.getName());
			} else {
				this.jtcustomUnit.setText("");
			}
		}
	}

	private void fillCalUnit(CalUnit unit) {
		unit.setCode(this.jtcode.getText().trim());
		unit.setName(this.jtname.getText().trim());
		unit.setScale(strToDouble(this.jFormattedTextField.getText()));
		unit.setCompany(CommonVars.getCurrUser().getCompany());
		if (customunit != null) {
			unit.setUnit(customunit);
		}
	}

	private void clearData() {
		this.jtname.setText("");
		this.jtcode.setText("");
		this.jFormattedTextField.setText("1");
		this.jtcustomUnit.setText("");
		this.customunit = null;
	}

	private boolean checkNull() {
		if (this.jtcode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "单位编码不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.jtname.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "单位名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		/*if (this.jFormattedTextField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "单位比例因子不能为空,请输入!", "提示!", 1);
			return true;
		}*/
		return false;
	}

	private boolean checkMultiple() {
		CalUnit unitcode = materialManageAction.findUnitByCode(new Request(
				CommonVars.getCurrUser()), this.jtcode.getText().trim());
		if (unitcode != null) {
			if (!isAdd) {
				if (!unitcode.getId().equals(unit.getId())) {
					JOptionPane.showMessageDialog(this, "单位编码不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane
						.showMessageDialog(this, "单位编码不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		/*
		 * CalUnit unitname = commonBaseCodeObj.findUnitByName(new Request(
		 * CommonVars.getCurrUser()), this.jtname.getText() .trim()); if
		 * (unitname != null) { if (!isAdd) { if
		 * (!unitname.getId().equals(unit.getId())) {
		 * JOptionPane.showMessageDialog(this, "单位名称不能重复,请重新输入!", "提示!", 1);
		 * return true; } } else { JOptionPane .showMessageDialog(this,
		 * "单位名称不能重复,请重新输入!", "提示!", 1); return true; } }
		 */
		return false;
	}

	private void addData() {
		CalUnit unit = new CalUnit();
		fillCalUnit(unit);

		unit = materialManageAction.saveUnit(
				new Request(CommonVars.getCurrUser()), unit);
		tableModel.addRow(unit);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillCalUnit(unit);
		unit = materialManageAction.saveUnit(
				new Request(CommonVars.getCurrUser()), unit);
		tableModel.updateRow(unit);

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
	 * @return Returns the unit.
	 */
	public CalUnit getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            The unit to set.
	 */
	public void setUnit(CalUnit unit) {
		this.unit = unit;
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
			jtcustomUnit.setVisible(false);
			jtcustomUnit.setBounds(142, 80, 144, 22);

		}
		return jtcustomUnit;
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

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("···");
			jButton2.setBounds(286, 79, 25, 23);
			jButton2.setVisible(false);
			jButton2.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Unit c = (Unit) CommonQuery.getInstance().getCustomUnit();
					if (c != null) {
						customunit = c;
						getJTextField3().setText(c.getName());
	
					}

				}
			});

		}
		return jButton2;
	}

	/**
	 * @return Returns the customunit.
	 */
	public Unit getCustomunit() {
		return customunit;
	}

	/**
	 * @param customunit
	 *            The customunit to set.
	 */
	public void setCustomunit(Unit customunit) {
		this.customunit = customunit;
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
			jtcode.setBounds(142, 24, 146, 22);
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
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			jFormattedTextField.setBounds(142, 110, 169, 22);
			jFormattedTextField.setVisible(false);
			jFormattedTextField
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return jFormattedTextField;
	}

	/**
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 * 
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 * 
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(286, 23, 26, 22);
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					Unit c = (Unit) CommonQuery.getInstance().getCustomUnit();
					if (c != null) {
						customunit=c;
						getJtcode().setText(c.getCode());
						getJtname().setText(c.getName());
					}
				}
			});
		}
		return jButton3;
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
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.add(getJMenuItem());
		}
		return jPopupMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setSize(72, 19);
			jMenuItem.setText("xxx");
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setEditable(false);
			jTextArea.setBackground(new java.awt.Color(238,238,238));
			jTextArea.setForeground(java.awt.Color.blue);
			jTextArea.setBounds(new java.awt.Rectangle(191,144,233,54));
			String str = "* 比例因子=报关单位数量/工厂单位数量  \n"
					+ "  如:报关单位为公斤 工厂单位为个\n  如果1公斤等于5个  那么 比例因子=1/5=0.2";
			jTextArea.setText(str);
			jTextArea.setVisible(false);
		}
		return jTextArea;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
