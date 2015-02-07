/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.UnitCollate;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administratorf
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgUnitCollate extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private JTextField jtcode = null;

	private JFormattedTextField jFormattedTextField = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JButton jButton3 = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="111,249"

	private JMenuItem jMenuItem = null; // @jve:decl-index=0:visual-constraint="485,215"

	private JTextArea jTextArea = null;

	private JButton jButton2 = null;
	private UnitCollate unit = null;

	/**
	 * This is the default constructor
	 */
	public DgUnitCollate() {
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
		this.setSize(362, 259);
		this.setTitle("计量单位对照编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						unit = (UnitCollate) tableModel.getCurrentRow();
					}
					fillWindow();
				} else {
					DgUnitCollate.this.jFormattedTextField.setText("1");
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("单位(1)");
			jLabel.setBounds(42, 24, 92, 23);
			jLabel2.setText("单位(2)");
			jLabel2.setBounds(42, 50, 89, 23);
			jLabel3.setBounds(41, 86, 96, 20);
			jLabel3.setText("单位对照比例因子");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJtcode(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJFormattedTextField(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJTextArea(), null);
			jPanel2.add(getJButton2(), null);
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
			jtname.setBounds(141, 52, 147, 22);
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
			jButton.setBounds(82, 187, 76, 23);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isAdd == true) {
						addData();
						clearData();
					} else {
						modifyData();
						DgUnitCollate.this.dispose();
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
			jButton1.setBounds(195, 187, 76, 23);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgUnitCollate.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (unit != null) {
			this.jtcode.setText(unit.getUnitName());
			this.jtname.setText(unit.getUnitName1());
			this.jFormattedTextField.setText(doubleToStr(unit.getUnitRate()));
		}
	}

	private void fillCalUnit(UnitCollate unit) {
		unit.setUnitName(this.jtcode.getText().trim());
		unit.setUnitName1(this.jtname.getText().trim());
		unit.setUnitRate(strToDouble(this.jFormattedTextField.getText()));
		unit.setCompany(CommonVars.getCurrUser().getCompany());
	}

	private void clearData() {
		this.jtname.setText("");
		this.jtcode.setText("");
		this.jFormattedTextField.setText("1");
	}



	private void addData() {
		UnitCollate unit = new UnitCollate();
		fillCalUnit(unit);

		unit = materialManageAction.saveUnitCollate(
				new Request(CommonVars.getCurrUser()), unit);
		tableModel.addRow(unit);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillCalUnit(unit);
		unit = materialManageAction.saveUnitCollate(
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
			jFormattedTextField.setBounds(140, 86, 173, 22);
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
						getJtcode().setText(c.getName());
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
			jTextArea.setBounds(new java.awt.Rectangle(30,119,302,51));
			jTextArea.setEditable(false);
			jTextArea.setBackground(new java.awt.Color(238,238,238));
			jTextArea.setForeground(java.awt.Color.blue);
			jTextArea.setText("* 比例因子=单位(2)数量/单位(1)数量  \n  如:单位(1)为个 单位(2)为千个\n  如果1个等于0.001千个  那么 比例因子=0.001/1=0.001");
		}
		return jTextArea;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(287,52,26, 22));
			jButton2.setText("...");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Unit c = (Unit) CommonQuery.getInstance().getCustomUnit();
					if (c != null) {
						getJtname().setText(c.getName());
					}
				}
			});
		}
		return jButton2;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
