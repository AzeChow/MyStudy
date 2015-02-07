package com.bestway.bcus.client.manualdeclare;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.InitialFocusSetter;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.DgBillDetail;
import com.bestway.common.client.erpbillcenter.DgBillMaster;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;

public class DgRestirictCommodity extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTextField jTextField5 = null;
	private JLabel jLabel10 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JCustomFormattedTextField jFormattedTextField = null;
	private JLabel jLabel7 = null;
	private JTextField jTextField2 = null;
	private JTextField jTextField4 = null;
	private JTextField jTextField6 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private ManualDeclareAction manualdeclearAction = null;
	private JTableListModel tableModel = null;
	private RestirictCommodity restirictCommodity = null; // @jve:decl-index=0:
	private Integer rowCount = null;
	private Integer totalCount = null;
	private int dataState = DataState.EDIT;
	private NumberFormatter numberFormatter = null;
	private boolean isMaterial = true;

	public boolean isMaterial() {
		return isMaterial;
	}

	public void setMaterial(boolean isMaterial) {
		this.isMaterial = isMaterial;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgRestirictCommodity() {
		super();
		initialize();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		List<Object> components = new ArrayList<Object>();
		components.add(this.jFormattedTextField);
		components.add(null);
		components.add(this.jButton2);
		components.add(this.jButton3);
		this.setComponentFocusList(components);
	}

	private void setAllKeyListener(Component component) {
		if (!(component instanceof Container)) {
			return;
		}
		Container container = (Container) component;
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component temp = container.getComponent(i);
			temp.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_S) {
						saveData();
					}
					if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
						DgRestirictCommodity.this.dispose();
					}
				}
			});
			setAllKeyListener(temp);
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(450, 251));
		this.setTitle("限制类商品维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
					totalCount = tableModel.getRowCount();
					rowCount = tableModel.getCurrRowCount();
					restirictCommodity = (RestirictCommodity) tableModel
							.getObjectByRow(rowCount);
					fillWindow();
					setState();
				}
				// TODO Auto-generated Event stub windowOpened()
			}
		});

	}

	private void preData() {
		RollSave();
		dataState = DataState.EDIT;
		rowCount--;
		restirictCommodity = (RestirictCommodity) tableModel
				.getObjectByRow(rowCount);
		fillWindow();
		setState();
		KeyAdapterControl.setAddListener(true);
		getJFormattedTextField().requestFocus();
	}

	private void RollSave() {
		if (dataState == DataState.EDIT) {
			fillData(restirictCommodity);
			restirictCommodity = manualdeclearAction.saveRestirictCommodity(
					new Request(CommonVars.getCurrUser()), restirictCommodity);
			tableModel.updateRow(restirictCommodity);
		}
	}

	public void fillData(RestirictCommodity restirictCommodity) { // 保存
		restirictCommodity
				.setAmount(strToDouble(jFormattedTextField.getText()));

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

	private void setState() {
		jButton.setEnabled(rowCount > 0);
		jButton3.setEnabled(rowCount < totalCount - 1);
		jButton4.setEnabled(dataState == DataState.BROWSE); // 修改
		jButton2.setEnabled(dataState != DataState.BROWSE); // 保存
		jButton1.setEnabled(dataState != DataState.BROWSE); // 取消
		jFormattedTextField.setEditable(dataState != DataState.BROWSE);

	}

	private void fillWindow() {
		if (!isMaterial) {
			jTextField2.setText(String.valueOf(restirictCommodity.getSeqNum()));
			jTextField.setVisible(false);
			jLabel.setVisible(false);
		} else {
			jTextField2.setVisible(false);
			jLabel7.setVisible(false);
		}
		jTextField.setText(restirictCommodity.getSeqNum());
		if (restirictCommodity.getComplex() != null)
			jTextField1.setText(restirictCommodity.getComplex());
		else
			jTextField1.setText("");
		jTextField4.setText(restirictCommodity.getName());
		jTextField6.setText(restirictCommodity.getSpec());
		jFormattedTextField
				.setText(doubleToStr(restirictCommodity.getAmount()));

		if (restirictCommodity.getUnit() != null)
			jTextField5.setText(restirictCommodity.getUnit());
		else
			jTextField5.setText("");

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

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setGroupingSize(0);
			decimalFormat1.setMaximumFractionDigits(9);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(17, 19, 54, 21));
			jLabel7.setText("成品序号");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(17, 77, 60, 21));
			jLabel10.setText("商品名称");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(228, 103, 61, 18));
			jLabel5.setText("申报单位");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(228, 77, 53, 20));
			jLabel4.setText("型号规格");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(17, 106, 54, 20));
			jLabel2.setText("数量");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(228, 50, 60, 17));
			jLabel1.setText("商品编码");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(17, 48, 53, 18));
			jLabel.setText("料件序号");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJFormattedTextField(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJButton4(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(86, 48, 123, 22));
			jTextField.setEditable(false);
			jTextField.setForeground(Color.red);
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(308, 49, 120, 22));
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(new java.awt.Rectangle(308, 102, 121, 22));
			jTextField5.setEditable(false);
		}
		return jTextField5;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(179, 151, 70, 25));
			jButton.setText("上一条");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					preData();   // TODO Auto-generated Event stub
					// actionPerformed()
				}
			});
		}
		return jButton;
	}

	private void saveData() {
		fillData(restirictCommodity);
		restirictCommodity = manualdeclearAction.saveRestirictCommodity(
				new Request(CommonVars.getCurrUser()), restirictCommodity);
		tableModel.updateRow(restirictCommodity);
		dataState = DataState.BROWSE;
		setState();
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(331, 152, 69, 25));
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgRestirictCommodity.this.dispose();// TODO Auto-generated
														// Event stub
														// actionPerformed()
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			DecimalFormat decimalFormat1 = new DecimalFormat();
			decimalFormat1.setMaximumFractionDigits(9);
			decimalFormat1.setGroupingSize(0);
			NumberFormatter numberFormatter = new NumberFormatter();
			numberFormatter.setFormat(decimalFormat1);
			DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(numberFormatter);
			defaultFormatterFactory.setDisplayFormatter(numberFormatter);
			jFormattedTextField = new JCustomFormattedTextField();
			jFormattedTextField.setBounds(new java.awt.Rectangle(86, 106, 124,
					22));
			jFormattedTextField.setFormatterFactory(defaultFormatterFactory);
		}
		return jFormattedTextField;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(new java.awt.Rectangle(86, 19, 123, 22));
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(new java.awt.Rectangle(86, 77, 124, 22));
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(new java.awt.Rectangle(308, 76, 121, 22));
			jTextField6.setEditable(false);
		}
		return jTextField6;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(29, 152, 70, 25));
			jButton2.setText("保存");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();	// TODO Auto-generated Event stub
					// actionPerformed()
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
			jButton3.setBounds(new Rectangle(252, 152, 75, 25));
			jButton3.setText("下一条");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					nextData(); // TODO Auto-generated Event stub
					// actionPerformed()
				}
			});
		}
		return jButton3;
	}

	private void nextData() {
		RollSave();
		dataState = DataState.EDIT;
		rowCount++;
		restirictCommodity = (RestirictCommodity) tableModel
				.getObjectByRow(rowCount);
		fillWindow();
		setState();
		KeyAdapterControl.setAddListener(true);
		getJFormattedTextField().requestFocus();
		InitialFocusSetter.setInitialFocus(this, jFormattedTextField);
		
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(105, 152, 70, 25));
			jButton4.setText("修改");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;// TODO Auto-generated Event
					// stub actionPerformed()
					setState();
				}
			});
		}
		return jButton4;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
