package com.bestway.client.fecav;

import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgBillOfExchangeQuery extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTextField tfCode = null;

	private JTextField tfOperator = null;

	private JCalendarComboBox ccbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private FecavAction fecavAction = null;

	private List resultList = null;

	public boolean isOk = false;

	/**
	 * This method initializes
	 * 
	 */
	public DgBillOfExchangeQuery() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
	}

	public void setVisible(boolean is) {
		super.setVisible(is);

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(332, 226));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("银行汇票查询");

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(25, 117, 62, 24));
			jLabel3.setText("                到");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(25, 83, 62, 24));
			jLabel2.setText("结汇日期从");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(25, 48, 62, 24));
			jLabel1.setText("        操作员");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(25, 13, 62, 24));
			jLabel.setText("    汇票号码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJCalendarComboBox(), null);
			jContentPane.add(getJCalendarComboBox1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(56, 151, 64, 24));
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					resultList = startQuery();
					isOk = true;
					DgBillOfExchangeQuery.this.dispose();
				}
			});
		}
		return jButton;
	}

	private List startQuery() {
		String code = this.tfCode.getText();
		String operator = this.tfOperator.getText();
		Date begin = this.ccbBeginDate.getDate();
		Date end = this.cbbEndDate.getDate();
		return this.fecavAction.findBillOfExchange(new Request(CommonVars
				.getCurrUser()), code, operator, begin, end);

	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(176, 152, 64, 24));
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillOfExchangeQuery.this.dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new java.awt.Rectangle(117, 13, 164, 24));
		}
		return tfCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (tfOperator == null) {
			tfOperator = new JTextField();
			tfOperator.setBounds(new java.awt.Rectangle(117, 48, 164, 24));
		}
		return tfOperator;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (ccbBeginDate == null) {
			ccbBeginDate = new JCalendarComboBox();
			ccbBeginDate.setBounds(new java.awt.Rectangle(117, 83, 164, 24));
			ccbBeginDate.setDate(null);
		}
		return ccbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new java.awt.Rectangle(117, 117, 164, 24));
			cbbEndDate.setDate(null);
		}
		return cbbEndDate;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

} // @jve:decl-index=0:visual-constraint="34,10"
