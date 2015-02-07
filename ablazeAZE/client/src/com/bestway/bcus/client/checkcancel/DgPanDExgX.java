/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.checkcancel.entity.EmsPdExg;
import com.bestway.bcus.checkcancel.entity.EmsPdExgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.bestway.ui.winuicontrol.JCustomFormattedTextField;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgPanDExgX extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField3 = null;

	private JTextField jTextField4 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private CheckCancelAction checkCancelAction = null;

	private JTableListModel tableModel = null;

	private EmsPdExgBg img = null; // 料件

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JFormattedTextField tfTotalNum = null;
	private JTextField tfVerson = null;
	private JFormattedTextField tfAllotNum = null;

	private JLabel jLabel101 = null;

	private JFormattedTextField tfConvertNUm = null;

	/**
	 * This is the default constructor
	 */
	public DgPanDExgX() {
		super();
		initialize();
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(427, 261);
		this.setTitle("编辑盘点成品数据");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (tableModel.getCurrentRow() != null) {
					img = (EmsPdExgBg) tableModel.getCurrentRow();
					fillWindowImg();
				}
			}
		});
	}

	private void fillWindowImg() {
		jTextField1.setText(String.valueOf(img.getSeqNum()));
		jTextField3.setText(img.getName());
		jTextField4.setText(img.getSpec());
		tfConvertNUm.setText(doubleToStr(img.getConvertNUm()));
		tfAllotNum.setText(doubleToStr(img.getAllotNum()));
		tfVerson.setText(img.getVersionNo());
		tfTotalNum.setText(doubleToStr(img.getTotalNum()));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(15, 142, 48, 18));
			jLabel101.setText("折算系数");
			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("备案序号");
			jLabel.setBounds(14, 23, 69, 18);
			jLabel3.setBounds(14, 49, 72, 17);
			jLabel3.setText("报关品名");
			jLabel4.setBounds(14, 74, 53, 20);
			jLabel4.setText("型号规格");
			jLabel10.setBounds(217, 104, 59, 21);
			jLabel10.setText("分配数量");
			jLabel12.setBounds(218, 139, 54, 21);
			jLabel12.setText("总数量");
			jLabel7.setBounds(14, 105, 43, 19);
			jLabel7.setText("版本号");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getTfTotalNum(), null);
			jContentPane.add(getTfVerson(), null);
			jContentPane.add(getTfAllotNum(), null);
			jContentPane.add(jLabel101, null);
			jContentPane.add(getTfConvertNUm(), null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(89, 23, 116, 22);
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(89, 49, 199, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(89, 74, 198, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
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
			jButton.setBounds(122, 186, 70, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillDataImg(img);
					img = checkCancelAction.SaveEmsPdExgBg(new Request(
							CommonVars.getCurrUser()), img);
					tableModel.updateRow(img);
					DgPanDExgX.this.dispose();
				}
			});

		}
		return jButton;
	}

	public void fillDataImg(EmsPdExgBg img) { // 保存
		img.setAllotNum(strToDouble(tfAllotNum.getText()));
		img.setVersionNo(tfVerson.getText());
		img.setTotalNum(strToDouble(tfTotalNum.getText()));
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
			jButton1.setBounds(229, 187, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgPanDExgX.this.dispose();
				}
			});

		}
		return jButton1;
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
	 * 
	 * This method initializes tfTotalNum
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalNum() {
		if (tfTotalNum == null) {
			tfTotalNum = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTotalNum.setBounds(293, 138, 116, 22);
			tfTotalNum.setFormatterFactory(getDefaultFormatterFactory());
			tfTotalNum.setEditable(false);
		}
		return tfTotalNum;
	}

	/**
	 * This method initializes tfVerson
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfVerson() {
		if (tfVerson == null) {
			tfVerson = new JTextField();
			tfVerson.setBounds(89, 103, 116, 22);
			tfVerson.setEditable(false);
		}
		return tfVerson;
	}

	/**
	 * This method initializes tfAllotNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAllotNum() {
		if (tfAllotNum == null) {
			tfAllotNum = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAllotNum.setBounds(292, 103, 116, 22);
			tfAllotNum.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							tfTotalNum.setValue(getTotalNum());
						}

					});
		}
		return tfAllotNum;
	}

	/**
	 * 计算总数量
	 * 
	 * @return
	 */
	protected Double getTotalNum() {
		double allotNum = 0;
		double convertNum = 0;
		if (this.tfAllotNum.getText() != null
				&& !this.tfAllotNum.getText().equals("")) {
			allotNum = Double.parseDouble(this.tfAllotNum.getText().toString());
		}
		if (this.tfConvertNUm.getText() != null
				&& !this.tfConvertNUm.getText().equals("")) {
			convertNum = Double.parseDouble(this.tfConvertNUm.getText()
					.toString());
		}
		BigDecimal bd = new BigDecimal(allotNum * convertNum);
		String totalPrice = bd.setScale(8, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalPrice);
	}

	/**
	 * This method initializes tfConvertNUm
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfConvertNUm() {
		if (tfConvertNUm == null) {
			tfConvertNUm = new JCustomFormattedTextField();
			tfConvertNUm.setBounds(new Rectangle(89, 138, 117, 22));
			tfConvertNUm.addPropertyChangeListener("value",
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							tfTotalNum.setValue(getTotalNum());
						}

					});
		}
		return tfConvertNUm;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
