package com.bestway.common.client.erpbill;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.InitialFocusSetter;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;

public class DgCustomOrderDetail extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField fbom = null; // 物料号码

	private JComboBox cbbCalUnit = null;// 单位

	private JFormattedTextField tfUnitPrice = null;// 单价

	private JFormattedTextField tfAmount = null;// 数量

	private JCustomFormattedTextField tfTotalPrice = null;// 总金额

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private int dataState = DataState.BROWSE;

	private JTextField fname = null;// 物料名称

	private JTextField fgg = null;// 物料规格

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JCustomFormattedTextField tfbgAmount = null;// 折算报关数量

	private OrderCommonAction orderCommonAction = null;

	private JTableListModel tableModelDetail;

	private JComboBox fcurr = null;// 币别

	private JCalendarComboBox salesDate = null;// 交货日期

	private JLabel jLabel10 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private boolean isAdd = false;

	private CustomOrderDetail currOrderdetail = null; // @jve:decl-index=0:

	private CustomOrder customOrder = null;

	private Double unitConvert = 1.0; // @jve:decl-index=0:

	private JLabel jLabel11 = null;
	private double rate = 1.0;

	private Customparames customparames = null;
	private int decimalSize = 5;

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
		this.fcurr.setEnabled(this.dataState == DataState.EDIT);
		this.tfUnitPrice.setEnabled(this.dataState == DataState.EDIT);
		this.tfAmount.setEnabled(this.dataState == DataState.EDIT);
		this.tfbgAmount.setEnabled(this.dataState == DataState.EDIT);
		this.tfTotalPrice.setEnabled(this.dataState == DataState.EDIT);
		this.btnOk.setEnabled(this.dataState == DataState.EDIT);
		this.salesDate.setEnabled(this.dataState == DataState.EDIT);
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgCustomOrderDetail(CustomOrder customOrder,
			JTableListModel tableModel,String title) {
		super();
		this.customOrder = customOrder;
		this.tableModelDetail = tableModel;
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		// wss:控制数量的小数保留位数，默认为5位
		Customparames customparames = orderCommonAction
				.findCustomparames(new Request(CommonVars.getCurrUser()));
		if (customparames != null) {
			decimalSize = customparames.getDecimalSize();
		}
		initialize();
		this.setTitle(title);
		// this.tfUnitPrice.setText(arg0);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(371, 391));
		this.setContentPane(getJContentPane());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//		this.setTitle("客户订单管理");
		initUIComponents();
	}

	public void setVisible(boolean b) {
		if (b) {
			ShowData();
			setState();
		}
		super.setVisible(b);
	}

	
	private void initUIComponents() {

		List unitlist = orderCommonAction.findunit(new Request(CommonVars
				.getCurrUser()));
		this.cbbCalUnit.removeAll();
		this.cbbCalUnit.setModel(new DefaultComboBoxModel(unitlist.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCalUnit, "code", "name", 170);
		this.cbbCalUnit.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));

//		List currlist = orderCommonAction.findcurr(new Request(CommonVars
//				.getCurrUser()));
		this.fcurr.removeAll();
		// 初始化币制
		this.fcurr.setModel(CustomBaseModel.getInstance().getCurrModel());
//		this.fcurr.setModel(new DefaultComboBoxModel(currlist.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.fcurr,
				"code", "name", 170);
		this.fcurr.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name", 100, 170));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(40, 7, 260, 24));
			jLabel11.setForeground(Color.BLUE);
			jLabel11.setText("报关与工厂折算比为：" + rate);
			jLabel10 = new JLabel();
			jLabel10.setBounds(new Rectangle(39, 122, 65, 18));
			jLabel10.setText("交货日期");
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(39, 258, 74, 21));
			jLabel8.setText("折算报关数量");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(39, 90, 69, 21));
			jLabel7.setText("物料规格");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(39, 64, 65, 21));
			jLabel6.setText("物料名称");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(39, 289, 66, 21));
			jLabel5.setText("总金额");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(39, 232, 66, 21));
			jLabel4.setText("数量");
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(39, 204, 66, 21));
			jLabel3.setText("单价");
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(39, 149, 66, 21));
			jLabel2.setText("单位 ");
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(39, 178, 66, 21));
			jLabel1.setText("币别");
			jLabel1.setForeground(java.awt.Color.blue);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(39, 38, 66, 21));
			jLabel.setText("物料号码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getFbom(), null);
			jContentPane.add(getCbbCalUnit(), null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(getTfAmount(), null);
			jContentPane.add(getTfTotalPrice(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getFname(), null);
			jContentPane.add(getFgg(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getTfbgAmount(), null);
			jContentPane.add(getFcurr(), null);
			jContentPane.add(getSalesDate(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel11, null);
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
			btnOk.setBounds(new Rectangle(43, 321, 60, 25));
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!checkNull()) {
						return;
					}
					saveData();
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * 
	 * 检查是否为空
	 */
	private boolean checkNull() {
		if (this.salesDate.getDate() == null) {
			JOptionPane.showMessageDialog(this, "交货日期不能为空！", "提示！",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (DgCustomOrderDetail.this.fcurr.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "币别不能为空！", "提示！",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.tfUnitPrice.getValue() == null) {
			JOptionPane.showMessageDialog(this, "单价不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.tfAmount.getValue() == null) {
			JOptionPane.showMessageDialog(this, "数量不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(238, 321, 60, 25));
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
	private JTextField getFbom() {
		if (fbom == null) {
			fbom = new JTextField();
			fbom.setBounds(new Rectangle(110, 39, 199, 24));
			fbom.setEditable(false);
		}
		return fbom;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCalUnit() {
		if (cbbCalUnit == null) {
			cbbCalUnit = new JComboBox();
			cbbCalUnit.setBounds(new Rectangle(111, 148, 199, 24));
		}
		return cbbCalUnit;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new JFormattedTextField();
			// tfUnitPrice = new
			// com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(new Rectangle(109, 205, 199, 24));
			// tfUnitPrice.setValue(new Double(0));
			tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new JCustomFormattedTextField();
			tfAmount.setBounds(new Rectangle(110, 232, 199, 24));
			tfAmount.setFormatterFactory(getDefaultFormatterFactory());
			tfAmount.getDocument().addDocumentListener(new DocumentListener() {

				public void insertUpdate(DocumentEvent e) {
					sumbgAmount();
					sumofTotalprice();
				}

				public void removeUpdate(DocumentEvent e) {
					sumbgAmount();
					sumofTotalprice();
				}

				public void changedUpdate(DocumentEvent e) {
					sumbgAmount();
					sumofTotalprice();
				}
			});

		}
		return tfAmount;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new JCustomFormattedTextField();
			tfTotalPrice.setBounds(new Rectangle(111, 286, 199, 24));
			tfTotalPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfTotalPrice.setEditable(false);
		}
		return tfTotalPrice;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
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
			DecimalFormat decimalFormat = new DecimalFormat();

			decimalFormat.setMaximumFractionDigits(decimalSize);
			// 默认五舍六入，改成四舍五入
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * This method initializes fname
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFname() {
		if (fname == null) {
			fname = new JTextField();
			fname.setBounds(new Rectangle(110, 66, 198, 24));
			fname.setEditable(false);
		}
		return fname;
	}

	/**
	 * This method initializes fgg
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFgg() {
		if (fgg == null) {
			fgg = new JTextField();
			fgg.setBounds(new Rectangle(110, 91, 198, 24));
			fgg.setEditable(false);
		}
		return fgg;
	}

	/**
	 * This method initializes tfbgAmount
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfbgAmount() {
		if (tfbgAmount == null) {
			DefaultFormatterFactory defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setNullFormatter(new NumberFormatter());
			defaultFormatterFactory1.setEditFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDisplayFormatter(new NumberFormatter());
			defaultFormatterFactory1.setDefaultFormatter(new NumberFormatter());
			tfbgAmount = new JCustomFormattedTextField();
			tfbgAmount.setBounds(new Rectangle(111, 259, 199, 24));
			// tfbgAmount.setFormatterFactory(defaultFormatterFactory1);
			tfbgAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfbgAmount;
	}

	private void sumbgAmount() {
		String ptNo = fbom.getText().trim();
		// unitConvert = orderCommonAction.getUnitConvert(new Request(CommonVars
		// .getCurrUser()), ptNo);
		unitConvert = rate;
		System.out.println(unitConvert);
		double str_Amount = tfAmount.getValue() == null ? 0 : Double
				.parseDouble(tfAmount.getValue().toString());

		this.tfbgAmount.setValue(unitConvert * str_Amount);
		// this.tfUnitPrice.setValue(unitConvert);
	}

	public void setTableModelDetail(JTableListModel tableModelDetail) {
		this.tableModelDetail = tableModelDetail;
	}

	/**
	 * This method initializes fcurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getFcurr() {
		if (fcurr == null) {
			fcurr = new JComboBox();
			fcurr.setLocation(new Point(111, 177));
			fcurr.setSize(new Dimension(198, 24));
		}
		return fcurr;
	}

	/**
	 * 计算总金额
	 */
	private void sumofTotalprice() {

		double str_price = tfUnitPrice.getValue() == null ? 0 : Double
				.parseDouble(tfUnitPrice.getValue().toString());
		double str_Amount = tfAmount.getValue() == null ? 0 : Double
				.parseDouble(tfAmount.getValue().toString());
		Double dou_total = str_price * str_Amount;
		this.tfTotalPrice.setValue(dou_total);
		// this.tfUnitPrice.setValue(str_price);
	}

	class DocumentListenerAdapter implements DocumentListener {
		public void insertUpdate(DocumentEvent e) {
			sumofTotalprice();
		}

		public void removeUpdate(DocumentEvent e) {
			sumofTotalprice();
		}

		public void changedUpdate(DocumentEvent e) {
			sumofTotalprice();
		}
	}

	/**
	 * This method initializes salesDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getSalesDate() {
		if (salesDate == null) {
			salesDate = new JCalendarComboBox();
			salesDate.setBounds(new Rectangle(110, 120, 199, 24));
		}
		return salesDate;
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Integer strToInteger(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				return null;
			}
			getNumberFormatter().setValueClass(Integer.class);
			return (Integer) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(108, 321, 60, 25));
			jButton.setText("上笔");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModelDetail.hasPreviousRow()) {
						jButton.setEnabled(false);
						jButton1.setEnabled(true);
					}
					/*
					 * if(!tableModelDetail.previousRow()) {
					 * jButton.setEnabled(false); jButton.setEnabled(true); }
					 */
					else {
						jButton.setEnabled(true);
						jButton1.setEnabled(true);
					}

					// setFirst();
					// saveData();
					tableModelDetail.previousRow();
					ShowData();
					setState();
					saveData();

					// setVisible(tableModelDetail.hasPreviousRow());

					// setVisible(tableModelDetail.hasPreviousRow());
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(173, 321, 60, 25));
			jButton1.setText("下笔");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModelDetail.hasNextRow()) {
						jButton1.setEnabled(false);
						jButton.setEnabled(true);
					} else {
						jButton1.setEnabled(true);
						jButton.setEnabled(true);
					}

					// saveData();
					tableModelDetail.nextRow();
					ShowData();
					setState();
					saveData();
				}
			});
		}
		return jButton1;
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
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	/**
	 * @param currBillDetail
	 *            The currBillDetail to set.
	 */
	public void setCurrOrderDetail(CustomOrderDetail orderdetail) {
		this.currOrderdetail = orderdetail;
		fillWindows(currOrderdetail);
	}

	/**
	 * 填充对象
	 * 
	 * @param billDetail
	 */
	private void ShowData() {
		currOrderdetail = (CustomOrderDetail) tableModelDetail.getCurrentRow();
		// rate = unitConvert = orderCommonAction.getUnitConvert(new Request(
		// CommonVars.getCurrUser()), currOrderdetail.getMateriel()
		// .getPtNo());
		rate = this.currOrderdetail.getUnitConvert();
		jLabel11.setText("报关与工厂折算比为：" + rate);
		this.fbom.setText(currOrderdetail.getMateriel().getPtNo());
		this.fname.setText(currOrderdetail.getMateriel().getFactoryName());
		this.fgg.setText(currOrderdetail.getMateriel().getFactorySpec());
		this.tfAmount.setValue(currOrderdetail.getAmount());
		// this.tfUnitPrice.setValue(currOrderdetail.getMateriel().getPtPrice());
		if (this.currOrderdetail.getUnitPrice() != null) {
			this.tfUnitPrice.setValue(Double.valueOf(currOrderdetail
					.getUnitPrice()));
		} else {
			this.tfUnitPrice.setValue(new Double(0));
		}
		this.tfTotalPrice.setValue(currOrderdetail.getTotalPrice());
		this.tfbgAmount.setValue(currOrderdetail.getBgamount());
		if (currOrderdetail.getCalUnit() == null) {
			this.cbbCalUnit.setSelectedItem(null);
		} else {
			this.cbbCalUnit.setSelectedItem(currOrderdetail.getCalUnit());
		}

		this.salesDate.setValue(currOrderdetail.getSalesDate());
		if (currOrderdetail.getCurr() == null) {
			this.fcurr.setSelectedItem(null);

		} else {
			this.fcurr.setSelectedItem(currOrderdetail.getCurr());
		}
	}

	private void fillWindows(CustomOrderDetail orderdetail) {
		this.fbom.setText(orderdetail.getMateriel().getPtNo());
		this.fname.setText(orderdetail.getMateriel().getFactoryName());
		this.fgg.setText(orderdetail.getMateriel().getFactorySpec());
		this.tfAmount.setValue(orderdetail.getAmount());
		// this.tfUnitPrice.setValue(orderdetail.getMateriel().getPtPrice());
		this.tfTotalPrice.setValue(orderdetail.getTotalPrice());
		this.tfbgAmount.setValue(orderdetail.getBgamount());
		if (orderdetail.getCalUnit() == null) {
			this.cbbCalUnit.setSelectedItem(null);
		} else {
			this.cbbCalUnit.setSelectedItem(orderdetail.getCalUnit());
		}

		this.salesDate.setValue(orderdetail.getSalesDate());
		if (orderdetail.getCurr() == null) {
			this.fcurr.setSelectedItem(null);

		} else {
			this.fcurr.setSelectedItem(orderdetail.getCurr());
		}
	}

	/**
	 * 显示数据
	 * 
	 */

	private void saveData() {
		/*
		 * if(checkNull()) { return; }
		 */
		modifyBillDetail();
	}

	private void setState() {
		this.jButton.setEnabled(tableModelDetail.hasPreviousRow());
		this.jButton1.setEnabled(tableModelDetail.hasNextRow());
	}


	/**
	 * 修改对象
	 * 
	 */
	private void modifyBillDetail() {
		fillBillDetailFromWindow(currOrderdetail);
		currOrderdetail = orderCommonAction.saveorderdetail(new Request(
				CommonVars.getCurrUser()), currOrderdetail);
		tableModelDetail.updateRow(currOrderdetail);
	}

	/**
	 * 传入的对象是一个能过InnerMergeData对象填充过的对象
	 * 
	 * @param billDetail
	 * @return
	 */
	private CustomOrderDetail fillBillDetailFromWindow(
			CustomOrderDetail orderDetail) {
		
		if (this.fcurr.getSelectedItem() != null) {
			orderDetail.setCurr((Curr) fcurr.getSelectedItem());
		}
		if (tfAmount.getText() != null && !"".equals(tfAmount.getText())) {
			orderDetail.setAmount(strToDouble(tfAmount.getText().trim()));// 原为getValue().toString()
		} else {
			orderDetail.setAmount(Double.valueOf(0.0));
		}
		if (this.cbbCalUnit.getSelectedItem() != null) {
			orderDetail.setCalUnit((CalUnit) this.cbbCalUnit.getSelectedItem());
		}

		if (tfbgAmount.getText() != null && !"".equals(tfbgAmount.getText())) {
			orderDetail.setBgamount(strToDouble(tfbgAmount.getText().trim()));// 原为getValue().toString()

		} else {
			orderDetail.setBgamount(Double.valueOf(0.0));
		}
		// orderDetail.setUnitConvert(unitConvert);
		if (tfUnitPrice.getText() != null && !"".equals(tfUnitPrice.getText())) {
			orderDetail.setUnitPrice(strToDouble(tfUnitPrice.getText().trim()));// 原为getValue().toString()
		} else {
			orderDetail.setUnitPrice(Double.valueOf(0.0));
		}

		if (tfTotalPrice.getText() != null
				&& !"".equals(tfTotalPrice.getText())) {
			orderDetail
					.setTotalPrice(strToDouble(tfTotalPrice.getText().trim()));// 原为getValue().toString()
		} else {
			orderDetail.setTotalPrice(Double.valueOf(0.0));
		}
		orderDetail.setUnitConvert(unitConvert);
		orderDetail.setSalesDate(this.salesDate.getDate());
		Double bgAmount = orderDetail.getBgamount() == null ? 0.0 : orderDetail
				.getBgamount();
		// * unitConvert;
		Double contractNum = orderDetail.getContractNum() == null ? 0.0
				: orderDetail.getContractNum();
		orderDetail.setContractNum(contractNum);
		if (bgAmount.doubleValue() >= contractNum.doubleValue()) {
			orderDetail.setNotContractNum(bgAmount - contractNum);
		} else {
			orderDetail.setNotContractNum(0.0);
		}

		Double transNum = orderDetail.getTransNum() == null ? 0.0 : orderDetail
				.getTransNum();
		orderDetail.setContractNum(transNum);
		if (contractNum.doubleValue() >= transNum.doubleValue()
				&& orderDetail.getCustomOrder().getIfzc()) {
			orderDetail.setNotTransNum(contractNum - transNum);
		} else {
			orderDetail.setNotTransNum(0.0);
		}
		return orderDetail;

	}

	/**
	 * @param currBillDetail
	 *            The currBillDetail to set.
	 */
	public void setCurrBillDetail(CustomOrderDetail orderdetail) {
		this.currOrderdetail = orderdetail;
		fillWindows(currOrderdetail);
	}

	private void setFirst() {
		if (tableModelDetail.getCurrentRow() == null) {
			return;
		}
		initialize();

		//
		// 设置窗口显示的焦点定位,必须在 visible 前加入
		// Set component with initial focus
		// must be done before the frame is made visible
		//
		InitialFocusSetter.setInitialFocus(this, salesDate);
		initUIComponents();
		setAllKeyListener(jContentPane);
		this.setAdd(false);
		this.setCurrOrderDetail((CustomOrderDetail) tableModelDetail
				.getCurrentRow());
	}

	/**
	 * 设置所有的RadioButton的属性和事件
	 * 
	 * @param component
	 */
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
						DgCustomOrderDetail.this.dispose();
					}
				}
			});
			setAllKeyListener(temp);
		}
	}
} // @jve:decl-index=0:visual-constraint="10,-24"
