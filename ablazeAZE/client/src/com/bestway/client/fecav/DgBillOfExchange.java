package com.bestway.client.fecav;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.Request;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.BillOfExchange;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgBillOfExchange extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JTextField tfCode = null;

	private JTextField tfBank = null;

	private JTextField tfBankAccounts = null;

	private JCalendarComboBox cbbEndDate = null;

	private JCustomFormattedTextField tfExchangeRate = null;

	private JCustomFormattedTextField tfCanBrikeMoney = null;

	private JCustomFormattedTextField tfExchangeMoney = null;

	private JComboBox cbbCurr = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private FecavAction fecavAction = null;

	private BillOfExchange billOfExchange = null;  //  @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private boolean isOk = false;

	public boolean isOk() {
		return isOk;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public BillOfExchange getBillOfExchange() {
		return billOfExchange;
	}

	public void setBillOfExchange(BillOfExchange billOfExchange) {
		this.billOfExchange = billOfExchange;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBillOfExchange() {
		super();
		initialize();
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initUIComponents();
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(346,286));
		this.setTitle("银行汇票录入");
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(47,148,66,18));
			jLabel7.setText("结汇金额");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(47,173,66,18));
			jLabel6.setText("可冲销金额");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(39,254,66,18));
			jLabel5.setText("汇率");
			jLabel5.setVisible(false);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(46, 97, 66, 18));
			jLabel4.setText("银行帐号");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(46, 47, 66, 18));
			jLabel3.setText("结汇日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(46, 122, 66, 18));
			jLabel2.setText("币别");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(46, 72, 66, 18));
			jLabel1.setText("结汇银行");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(46, 22, 66, 18));
			jLabel.setText("汇票号码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTfCode(), null);
			jContentPane.add(getTfBank(), null);
			jContentPane.add(getTfBankAccounts(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getTfExchangeRate(), null);
			jContentPane.add(getTfCanBrikeMoney(), null);
			jContentPane.add(getTfExchangeMoney(), null);
			jContentPane.add(getCbbCurr(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(50,209,77,23));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					if (dataState == DataState.ADD) {
						if ("".equals(tfCode.getText().trim())) {
							JOptionPane.showMessageDialog(
									DgBillOfExchange.this, "汇票编号不能为空", "提示",
									JOptionPane.OK_OPTION);
							return;
						}
						if (fecavAction.findBillOfExchangeCountByCode(
								new Request(CommonVars.getCurrUser()), tfCode
										.getText().trim()) > 0) {
							JOptionPane.showMessageDialog(
									DgBillOfExchange.this, "汇票编号不能重复,请重新输入",
									"提示", JOptionPane.OK_OPTION);
							return;
						}
						billOfExchange = new BillOfExchange();
					}
					fillData();
					billOfExchange = fecavAction.saveBillOfExchange(
							new Request(CommonVars.getCurrUser()),
							billOfExchange);
					isOk = true;
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
			btnCancel.setBounds(new java.awt.Rectangle(194,209,77,23));
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
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new java.awt.Rectangle(116, 22, 169, 22));
		}
		return tfCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBank() {
		if (tfBank == null) {
			tfBank = new JTextField();
			tfBank.setBounds(new java.awt.Rectangle(116, 72, 169, 22));
		}
		return tfBank;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBankAccounts() {
		if (tfBankAccounts == null) {
			tfBankAccounts = new JTextField();
			tfBankAccounts.setBounds(new java.awt.Rectangle(116, 97, 169, 22));
		}
		return tfBankAccounts;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new java.awt.Rectangle(116, 47, 169, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfExchangeRate() {
		if (tfExchangeRate == null) {
			tfExchangeRate = new JCustomFormattedTextField();
			tfExchangeRate.setVisible(false);
			tfExchangeRate.setBounds(new java.awt.Rectangle(109,254,169,22));
			tfExchangeRate.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfExchangeRate;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfCanBrikeMoney() {
		if (tfCanBrikeMoney == null) {
			tfCanBrikeMoney = new JCustomFormattedTextField();
			tfCanBrikeMoney
					.setBounds(new java.awt.Rectangle(117,173,169,22));
			tfCanBrikeMoney.setEditable(false);
			tfCanBrikeMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfCanBrikeMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfExchangeMoney() {
		if (tfExchangeMoney == null) {
			tfExchangeMoney = new JCustomFormattedTextField();
			tfExchangeMoney
					.setBounds(new java.awt.Rectangle(117,148,169,22));
			tfExchangeMoney.setFormatterFactory(getDefaultFormatterFactory());
			tfExchangeMoney
					.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
						public void propertyChange(java.beans.PropertyChangeEvent e) {
							if ((e.getPropertyName().equals("value"))) {
								if(tfExchangeMoney.getValue() != null)
									tfCanBrikeMoney.setValue(tfExchangeMoney.getValue()); 
							}
						}
					});
		}
		return tfExchangeMoney;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new java.awt.Rectangle(116, 122, 169, 22));
		}
		return cbbCurr;
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
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
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

	private void initUIComponents() {
		// 初始化货币
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCurr);
		this.cbbCurr.setSelectedIndex(-1);
	}

	private void showData() {
		if (this.billOfExchange == null) {
			return;
		}
		this.tfCode.setText(billOfExchange.getCode());
		this.cbbEndDate.setValue(billOfExchange.getEndDate());
		this.tfBank.setText(billOfExchange.getBank());
		this.tfBankAccounts.setText(billOfExchange.getBankAccounts());
		this.cbbCurr.setSelectedItem(billOfExchange.getCurr());
		this.tfExchangeRate.setValue(billOfExchange.getExchangeRate());
		this.tfExchangeMoney.setValue(billOfExchange.getExchangeMoney());
		this.tfCanBrikeMoney.setValue(billOfExchange.getCanStrikeMoney());
	}

	private void fillData() {
		if (this.billOfExchange == null) {
			return;
		}
		billOfExchange.setCode(this.tfCode.getText());
		billOfExchange.setEndDate(this.cbbEndDate.getDate());
		billOfExchange.setBank(this.tfBank.getText());
		billOfExchange.setBankAccounts(this.tfBankAccounts.getText());
		billOfExchange.setCurr((Curr) this.cbbCurr.getSelectedItem());
		billOfExchange
				.setExchangeRate(this.tfExchangeRate.getValue() == null ? 0.0
						: Double.parseDouble(this.tfExchangeRate.getValue()
								.toString()));
		billOfExchange
				.setExchangeMoney(this.tfExchangeMoney.getValue() == null ? 0.0
						: Double.parseDouble(this.tfExchangeMoney.getValue()
								.toString()));
		billOfExchange
				.setCanStrikeMoney(this.tfCanBrikeMoney.getValue() == null ? 0.0
						: Double.parseDouble(this.tfCanBrikeMoney.getValue()
								.toString()));
		if(dataState==DataState.ADD){
			billOfExchange.setOperator(CommonVars.getCurrUser().getUserName());
			billOfExchange.setOperateDate(new Date());
		}
	}

	private void setState() {
		if (this.billOfExchange == null) {
			return;
		}
		this.tfCode.setEditable(dataState == DataState.ADD);
		List list=new ArrayList();
		list.add(billOfExchange);
		boolean isBillOfExchangeIsUer=fecavAction.isBillOfExchangeIsUer(list);
		tfExchangeMoney.setEditable(!isBillOfExchangeIsUer);
		cbbCurr.setEnabled(!isBillOfExchangeIsUer);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
