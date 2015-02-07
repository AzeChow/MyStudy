/*
 * Created on 2005-3-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DutyRateType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.LimitFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscCommMoneyAmountInfo;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsImgBill;
import com.bestway.ui.message.DgMessage;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Point;

/**
 * 成品修改
 * 
 * lm checked by 2009-12-4
 * 
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgDzscEmsPorExgBill extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel lbEditExg = null;

	private JLabel lbComplex = null;

	private JLabel lbName = null;

	private JLabel lbSpec = null;

	private JLabel lbUnitPrice = null;

	private JLabel lbAmount = null;

	private JLabel lbUnitGrossWeight = null;

	private JLabel lbProductCountry = null;

	private JLabel lbUnit = null;

	private JLabel lbUnitNetWeight = null;

	private JLabel lbSupplement = null;

	private JLabel lbTaxFreeMode = null;

	private JLabel lbFilingNo = null;

	private JTextField tfName = null;

	private JTextField tfSpec = null;

	private JFormattedTextField tfUnitPrice = null;

	private JFormattedTextField tfUnitGrossWeight = null;

	private JFormattedTextField tfAmount = null;

	private JComboBox cbbCountry = null;

	private JFormattedTextField tfUnitNetWeight = null;

	private JTextField tfNote = null;

	private JComboBox cbbLevyMode = null;

	private JTextField tfSeqNum = null;

	private JButton btnOk = null;

	private JButton btnExit = null;
	/** 电子手册Action接口类 */
	private DzscAction dzscAction = null;
	/** JBCUS系统所扩展的JTableModel 通过List数据源来显示&绑定数据 */
	private JTableListModel tableModel = null;
	/** 存放手册通关备案的成品资料 */
	private DzscEmsExgBill exg = null;
	/** 存放自用商品编码资料 */
	private Complex complex = null;

	private JLabel lbRemark = null;

	private JTextField tfComplex = null;

	private JButton btnComplex = null;

	private JLabel lbMachinPrice = null;

	private JFormattedTextField tfMachinPrice = null;

	private JLabel lbImgMoney = null;

	private JFormattedTextField tfImgMoney = null;
	/** 临时实体类 */
	private TempDzscCommMoneyAmountInfo tempDzscCommMoneyAmountInfo = null; // @jve:decl-index=0:
	/** 数据状态 */
	private int dataState = DataState.BROWSE;

	/**
	 * 电子手册参数设置
	 */
	private DzscParameterSet parameterSet = null;

	/**
	 * 记录修改前数量
	 */
	private double oldAmount;
	
	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This is the default constructor
	 */
	public DgDzscEmsPorExgBill() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		parameterSet = dzscAction.findDzscParameterSet(new Request(CommonVars
				.getCurrUser(), true));
		initialize();
	}

	/**
	 * 设置组件的状态
	 */
	private void setState() {
		exg = (DzscEmsExgBill) tableModel.getCurrentRow();
		String declareState = exg.getDzscEmsPorHead().getDeclareState();
		this.btnComplex.setEnabled(this.dataState != DataState.BROWSE);
		this.tfName.setEditable(!DzscState.CHANGE.equals(declareState)
				&& this.dataState != DataState.BROWSE);
		this.tfSpec.setEditable(this.dataState != DataState.BROWSE);
		this.tfName
				.setEditable((exg.getDzscEmsPorHead().getCopEntNo() == null || ""
						.equals(exg.getDzscEmsPorHead().getCopEntNo().trim()))
						&& this.dataState != DataState.BROWSE);
		this.tfSpec
				.setEditable((exg.getDzscEmsPorHead().getCopEntNo() == null || ""
						.equals(exg.getDzscEmsPorHead().getCopEntNo().trim()))
						&& this.dataState != DataState.BROWSE);
		this.cbbUnit
				.setEnabled((exg.getDzscEmsPorHead().getCopEntNo() == null || ""
						.equals(exg.getDzscEmsPorHead().getCopEntNo().trim()))
						&& this.dataState != DataState.BROWSE);
		this.cbbDutyRate.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnOk.setEnabled(this.dataState != DataState.BROWSE);
		// this.btnComplex.setEnabled(this.dataState != DataState.BROWSE );
		// this.tfName .setEditable( this.dataState != DataState.BROWSE);
		// this.tfSpec .setEditable( this.dataState != DataState.BROWSE);
		this.tfAmount.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitPrice.setEditable(this.dataState != DataState.BROWSE);
		this.tfMachinPrice.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitGrossWeight.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitNetWeight.setEditable(this.dataState != DataState.BROWSE);
		this.tfLegalUnitGene.setEditable(this.dataState != DataState.BROWSE);
		this.tfLegalUnit2Gene.setEditable(this.dataState != DataState.BROWSE);
		this.cbbCountry.setEnabled(this.dataState != DataState.BROWSE);
		this.tfImgMoney.setEditable(this.dataState != DataState.BROWSE);
		this.cbbLevyMode.setEnabled(this.dataState != DataState.BROWSE);
		this.tfNote.setEditable(this.dataState != DataState.BROWSE);

		this.lbExpCanMoney
				.setVisible(exg.getDzscEmsPorHead().getCorrEmsNo() != null
						&& !"".equals(exg.getDzscEmsPorHead().getCorrEmsNo()
								.trim()));
		this.lbCanExpMoney
				.setVisible(exg.getDzscEmsPorHead().getCorrEmsNo() != null
						&& !"".equals(exg.getDzscEmsPorHead().getCorrEmsNo()
								.trim()));

	}

	/**
	 * 显示窗口
	 */
	public void setVisible(boolean b) {
		if (b) {
			if (tableModel.getCurrentRow() != null) {
				exg = (DzscEmsExgBill) tableModel.getCurrentRow();
				initUI();
				this.oldAmount = exg.getAmount()==null?0:exg.getAmount();
				initUI();				
				showData();
				setState();
			}
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(533, 411);
		this.setContentPane(getJContentPane());
		this.setTitle("成品修改");
	}

	/**
	 * 填充动态ComboBox
	 * 
	 * @param list
	 * @param jComboBox
	 */
	public static void intoComboBoxModel(List list, JComboBox jComboBox) {
		jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		jComboBox.setRenderer(CustomBaseRender.getInstance().getRender("name",
				"code", 100, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"name", "code");
		jComboBox.setSelectedIndex(-1);
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		List list = null;
		// 单位
		list = CustomBaseList.getInstance().getUnits();
		DzscClientLogic.intoComboBox(list, cbbUnit);
		// 原产地
		list = CustomBaseList.getInstance().getCountrys();
		DzscClientLogic.intoComboBox(list, cbbCountry);
		// 免税方式
		list = CustomBaseList.getInstance().getLevymode();
		DzscClientLogic.intoComboBox(list, cbbLevyMode);
		int countBit = parameterSet.getCountBit() == null ? 5 : parameterSet
				.getCountBit();
		int priceBit = parameterSet.getPriceBit() == null ? 5 : parameterSet
				.getPriceBit();
		int moneyBit = parameterSet.getMoneyBit() == null ? 5 : parameterSet
				.getMoneyBit();

		// 数量
		CustomFormattedTextFieldUtils.setFormatterFactory(tfAmount, countBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfAmount,
				new AutoCalcListener() {
					public void run() {
						tfMachinMoney.setValue(getMachinMoney());
						tfMoney.setValue(getTotalPrice());
					}
				});
		// 加工费单价
		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfMachinPrice, 5);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfMachinPrice,
				new AutoCalcListener() {
					public void run() {
						tfUnitPrice.setValue(getUnitPrice());
						tfMachinMoney.setValue(getMachinMoney());
						tfMoney.setValue(getTotalPrice());
					}
				});
		// 原料费
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfImgMoney, 5);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfImgMoney,
				new AutoCalcListener() {
					public void run() {
						tfUnitPrice.setValue(getUnitPrice());
						tfMoney.setValue(getTotalPrice());
					}
				});
		// 单价
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice,
				priceBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfUnitPrice,
				new AutoCalcListener() {
					public void run() {
						tfMoney.setValue(getTotalPrice());
					}
				});
		// 加工费总价
		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfMachinMoney, 5);
		// 金额
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfMoney,
				moneyBit);
		// 法定单位比例因子
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfLegalUnitGene,
				9);
		// 第二法定单位比例因子
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfLegalUnit2Gene, 9);
		// 单位重量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitNetWeight,
				5);
		// 申报状态
		cbbDutyRate.removeAllItems();
		cbbDutyRate.addItem(new ItemProperty(DutyRateType.NO_DECLARE, "企业不申报"));
		cbbDutyRate.addItem(new ItemProperty(DutyRateType.DECLARE, "企业申报"));
		cbbDutyRate.addItem(new ItemProperty(DutyRateType.CHECK_AND_RATIFY,
				"已核定"));
		cbbDutyRate.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbDutyRate);
	}

	/**
	 * 数据显示
	 */
	private void showData() {
		exg = (DzscEmsExgBill) tableModel.getCurrentRow();
		this.complex = exg.getComplex();
		tfName.setText(exg.getName());
		tfSpec.setText(exg.getSpec());
		tfAmount.setValue(exg.getAmount());
		cbbUnit.setSelectedItem(exg.getUnit());
		tfLegalUnit.setText(exg.getComplex().getFirstUnit() == null ? "" : exg
				.getComplex().getFirstUnit().getName());
		tfLegalUnit2.setText(exg.getComplex().getSecondUnit() == null ? ""
				: exg.getComplex().getSecondUnit().getName());
		tfUnitPrice.setValue(exg.getPrice());
		tfMachinPrice.setValue(exg.getMachinPrice());
		tfUnitNetWeight.setValue(exg.getUnitNetWeight());
		tfUnitGrossWeight.setValue(exg.getUnitGrossWeight());
		cbbCountry.setSelectedItem(exg.getCountry());
		tfImgMoney.setValue(exg.getImgMoney());
		cbbLevyMode.setSelectedItem(exg.getLevyMode());
		tfSeqNum.setText(String.valueOf(exg.getSeqNum()));
		tfNote.setText(exg.getNote());
		tfComplex.setText(exg.getComplex().getCode());
		tfLegalUnitGene.setValue(exg.getLegalUnitGene());
		tfLegalUnit2Gene.setValue(exg.getLegalUnit2Gene());
		tfMachinMoney.setValue(exg.getMachinMoney());
		tfMoney.setValue(exg.getMoney());
		// 申报状态
		if (exg.getDutyRate() != null) {
			Integer dutyRate = exg.getDutyRate().intValue();
			cbbDutyRate.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(dutyRate), cbbDutyRate));// TODO No1
		} else {
			cbbDutyRate.setSelectedIndex(ItemProperty.getIndexByCode("1",
					cbbDutyRate));
		}
		this.tempDzscCommMoneyAmountInfo = dzscAction
				.findDzscCurrentEmsCommInfo(new Request(CommonVars
						.getCurrUser()), exg.getDzscEmsPorHead(), false, exg
						.getSeqNum());
		this.lbCanExpMoney.setText(CommonVars
				.formatDoubleToString(tempDzscCommMoneyAmountInfo
						.getCanPutOnRecordsMoney())
				+ " HKD");
		this.lbCDAmount.setText(CommonVars
				.formatDoubleToString(tempDzscCommMoneyAmountInfo
						.getCustomsDeclarationAmount()));

	}

	/**
	 * 设置料件总单价
	 */
	private Double getUnitPrice() {
		double unitPrice = objToDouble(this.tfMachinPrice.getValue())
				+ objToDouble(this.tfImgMoney.getValue());
		return CommonVars.getDoubleByDigit(unitPrice, 5);
	}

	/**
	 * 设置成品总金额
	 */
	private Double getTotalPrice() {
		try {
			int moneyBit = parameterSet.getMoneyBit() == null ? 5
					: parameterSet.getMoneyBit();
			double amount = Double
					.parseDouble(this.tfAmount.getValue() == null ? "0"
							: this.tfAmount.getValue().toString())
					* Double.parseDouble(tfUnitPrice.getValue() == null ? "0"
							: tfUnitPrice.getValue().toString());
			BigDecimal bd = new BigDecimal(amount);
			return bd.setScale(moneyBit, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} catch (Exception ex) {
		}
		return 0.0;
		// double amount = objToDouble(this.tfAmount.getValue())
		// * objToDouble(tfUnitPrice.getValue());
		// return CommonVars.getDoubleByDigit(amount, 5);
	}

	/**
	 * 设置成品总加工费
	 */
	private Double getMachinMoney() {
		double amount = objToDouble(this.tfAmount.getValue())
				* objToDouble(this.tfMachinPrice.getValue());
		return CommonVars.getDoubleByDigit(amount, 5);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			lbDutyRate = new JLabel();
			lbDutyRate.setText("申报状态");
			lbDutyRate.setLocation(new Point(30, 283));
			lbDutyRate.setSize(new Dimension(60, 18));
			lbDutyRate.setForeground(java.awt.Color.blue);
			lbLegalUnit2 = new JLabel();
			lbLegalUnit2.setBounds(new Rectangle(30, 232, 74, 19));
			lbLegalUnit2.setText("第二法定单位");
			lbLegalUnit = new JLabel();
			lbLegalUnit.setBounds(new Rectangle(30, 207, 69, 22));
			lbLegalUnit.setText("法定单位");
			lbTotalMoney = new JLabel();
			lbTotalMoney.setBounds(new Rectangle(266, 130, 54, 19));
			lbTotalMoney.setText("总金额");
			lbMachinMoney = new JLabel();
			lbMachinMoney.setBounds(new Rectangle(266, 256, 63, 24));
			lbMachinMoney.setText("加工费总价");
			lbLegalUnit2Gene = new JLabel();
			lbLegalUnit2Gene.setBounds(new Rectangle(266, 230, 121, 23));
			lbLegalUnit2Gene.setText("第二法定单位比例因子");
			lbLegalUnitGene = new JLabel();
			lbLegalUnitGene.setBounds(new Rectangle(266, 204, 96, 23));
			lbLegalUnitGene.setText("法定单位比例因子");
			lbCanExpMoney = new JLabel();
			lbCanExpMoney.setBounds(new Rectangle(116, 309, 134, 20));
			lbCanExpMoney.setForeground(java.awt.Color.blue);
			lbCanExpMoney.setText("");
			lbCDAmount = new JLabel();
			lbCDAmount.setBounds(new Rectangle(335, 283, 150, 20));
			lbCDAmount.setForeground(java.awt.Color.blue);
			lbCDAmount.setText("");
			lbDeclarationAmount = new JLabel();
			lbDeclarationAmount.setBounds(new Rectangle(266, 283, 74, 20));
			lbDeclarationAmount.setForeground(java.awt.Color.blue);
			lbDeclarationAmount.setText("报关单数量：");
			lbExpCanMoney = new JLabel();
			lbExpCanMoney.setForeground(java.awt.Color.blue);
			lbExpCanMoney.setSize(new Dimension(85, 20));
			lbExpCanMoney.setLocation(new Point(30, 309));
			lbExpCanMoney.setText("成品出口余额：");
			lbImgMoney = new JLabel();
			lbMachinPrice = new JLabel();
			lbRemark = new JLabel();
			lbFilingNo = new JLabel();
			lbTaxFreeMode = new JLabel();
			lbSupplement = new JLabel();
			lbUnitNetWeight = new JLabel();
			lbUnit = new JLabel();
			lbProductCountry = new JLabel();
			lbUnitGrossWeight = new JLabel();
			lbAmount = new JLabel();
			lbUnitPrice = new JLabel();
			lbSpec = new JLabel();
			lbName = new JLabel();
			lbComplex = new JLabel();
			lbEditExg = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			lbEditExg.setBounds(12, 6, 135, 22);
			lbEditExg.setText("修改出口成品内容");
			lbEditExg.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
					14));
			lbEditExg.setForeground(java.awt.Color.blue);
			lbComplex.setBounds(266, 32, 56, 22);
			lbComplex.setText("商品编码");
			lbComplex.setForeground(java.awt.Color.blue);
			lbName.setBounds(30, 57, 62, 21);
			lbName.setText("商品名称");
			lbName.setForeground(java.awt.Color.blue);
			lbSpec.setBounds(266, 57, 54, 21);
			lbSpec.setText("型号规格");
			lbUnitPrice.setBounds(30, 131, 28, 20);
			lbUnitPrice.setText("单价");
			lbAmount.setBounds(30, 81, 27, 19);
			lbAmount.setForeground(java.awt.Color.blue);
			lbAmount.setText("数量");
			lbUnitGrossWeight.setBounds(266, 153, 53, 20);
			lbUnitGrossWeight.setText("单位毛重");
			lbProductCountry.setBounds(30, 180, 44, 22);
			lbProductCountry.setText("消费国");
			lbUnit.setBounds(266, 81, 31, 20);
			lbUnit.setText("单位");
			lbUnit.setForeground(java.awt.Color.blue);
			lbUnitNetWeight.setBounds(30, 155, 57, 22);
			lbUnitNetWeight.setText("单位净重");
			lbSupplement.setBounds(30, 255, 59, 24);
			lbSupplement.setText("补充说明");
			lbTaxFreeMode.setBounds(266, 177, 57, 23);
			lbTaxFreeMode.setText("征免方式");
			lbFilingNo.setBounds(30, 32, 53, 22);
			lbFilingNo.setText("备案序号");
			lbRemark.setBounds(266, 309, 131, 20);
			lbRemark.setText("(注：蓝色字体为必填项)");
			lbRemark.setForeground(java.awt.Color.blue);
			lbMachinPrice.setBounds(30, 106, 62, 21);
			// lbMachinPrice.setForeground(java.awt.Color.blue);
			lbMachinPrice.setText("加工费单价");
			lbImgMoney.setBounds(266, 105, 42, 20);
			lbImgMoney.setText("原料费");
			jContentPane.add(lbEditExg, null);
			jContentPane.add(lbComplex, null);
			jContentPane.add(lbName, null);
			jContentPane.add(lbSpec, null);
			jContentPane.add(lbUnitPrice, null);
			jContentPane.add(lbAmount, null);
			jContentPane.add(lbUnitGrossWeight, null);
			jContentPane.add(lbProductCountry, null);
			jContentPane.add(lbUnit, null);
			jContentPane.add(lbUnitNetWeight, null);
			jContentPane.add(lbSupplement, null);
			jContentPane.add(lbTaxFreeMode, null);
			jContentPane.add(lbFilingNo, null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(getTfUnitGrossWeight(), null);
			jContentPane.add(getTfAmount(), null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(getTfUnitNetWeight(), null);
			jContentPane.add(getTfNote(), null);
			jContentPane.add(getCbbLevyMode(), null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(lbExpCanMoney, null);
			jContentPane.add(lbDeclarationAmount, null);
			jContentPane.add(lbCDAmount, null);
			jContentPane.add(lbCanExpMoney, null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(lbRemark, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(lbMachinPrice, null);
			jContentPane.add(getTfMachinPrice(), null);
			jContentPane.add(lbImgMoney, null);
			jContentPane.add(getTfImgMoney(), null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(lbLegalUnitGene, null);
			jContentPane.add(lbLegalUnit2Gene, null);
			jContentPane.add(getTfLegalUnitGene(), null);
			jContentPane.add(getTfLegalUnit2Gene(), null);
			jContentPane.add(lbMachinMoney, null);
			jContentPane.add(getTfMachinMoney(), null);
			jContentPane.add(getTfMoney(), null);
			jContentPane.add(lbTotalMoney, null);
			jContentPane.add(lbLegalUnit, null);
			jContentPane.add(getTfLegalUnit(), null);
			jContentPane.add(lbLegalUnit2, null);
			jContentPane.add(getTfLegalUnit2(), null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(lbDutyRate, null);
			jContentPane.add(getCbbDutyRate(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.tfName
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(91, 57, 159, 22);
		}
		return tfName;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(321, 57, 164, 22);
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setEditable(true);
			tfUnitPrice.setBounds(91, 130, 159, 22);
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes tfUnitGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitGrossWeight() {
		if (tfUnitGrossWeight == null) {
			tfUnitGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitGrossWeight.setBounds(321, 153, 164, 22);
		}
		return tfUnitGrossWeight;
	}

	/**
	 * This method initializes tfAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAmount.setBounds(91, 81, 159, 22);
			tfAmount.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					//检查数量,数量改小的时候，提示耗用信息
					double amount = (tfAmount.getValue() == null ? 0.0 : Double
							.parseDouble(tfAmount.getValue().toString()));
					if(amount<oldAmount){
						List<TempDzscEmsImgBill> imgs = dzscAction.processImgAmountFromExg(new Request(CommonVars.getCurrUser()), exg);
						if(imgs!=null && imgs.size()>=1){
							DgDzscEmsImgInfo dgDzscEmsImgInfo = new DgDzscEmsImgInfo(imgs);
							dgDzscEmsImgInfo.setVisible(true);
						}
						oldAmount = amount;
					}
				}
			});
		}
		return tfAmount;
	}

	/**
	 * This method initializes cbbCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(91, 180, 159, 23);
		}
		return cbbCountry;
	}

	/**
	 * This method initializes tfUnitNetWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitNetWeight() {
		if (tfUnitNetWeight == null) {
			tfUnitNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitNetWeight.setBounds(91, 155, 159, 22);
		}
		return tfUnitNetWeight;
	}

	/**
	 * This method initializes tfNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(91, 256, 159, 22);
		}
		return tfNote;
	}

	/**
	 * This method initializes cbbLevyMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyMode() {
		if (cbbLevyMode == null) {
			cbbLevyMode = new JComboBox();
			cbbLevyMode.setBounds(321, 178, 164, 22);
		}
		return cbbLevyMode;
	}

	/**
	 * This method initializes tfSeqNum
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(91, 32, 159, 22);
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(81, 337, 67, 24);
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkData()) {
						return;
					}
					fillData(exg);
					List list = dzscAction.saveDzscEmsExgBill(new Request(CommonVars
							.getCurrUser()), exg);
					exg = (DzscEmsExgBill)list.get(0);
					tableModel.updateRow(exg);
					dataState = DataState.BROWSE;
					setState();
					String str = (String)list.get(1);
					if(str!=null && !"".equals(str)){
						DgMessage message = new DgMessage("提示", str);
					}
					// DgDzscEmsPorExgBill.this.dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * 数据检查
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (tfComplex.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "商品编码不能为空！", "提示", 2);
			return true;
		}
		if (tfName.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "商品名称不能为空！", "提示", 2);
			return true;
		}
		if (cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不能为空！", "提示", 2);
			return true;
		}
		// if (cbbCountry.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "消费国不能为空！", "提示", 2);
		// return true;
		// }
		// if (cbbLevyMode.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "增减免税方式不能为空！", "提示", 2);
		// return true;
		// }
		if (tfComplex.getText().trim().length() == 4) {
			JOptionPane.showMessageDialog(this, "请输入一个完整的商品编码！", "提示", 2);
			return true;
		}
		if (this.tfAmount.getValue() == null) {
			JOptionPane.showMessageDialog(this, "数量不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (validate(this.tfAmount.getValue().toString().trim(),
				parameterSet.getCountBit())) {
			JOptionPane.showMessageDialog(this,
					"数量小数位不能超过海关允许最大小数位（海关允许最大值为５）!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		double amount = (this.tfAmount.getValue() == null ? 0.0 : Double
				.parseDouble(this.tfAmount.getValue().toString()));
		double cdAmont = (this.tempDzscCommMoneyAmountInfo
				.getCustomsDeclarationAmount() == null ? 0.0
				: this.tempDzscCommMoneyAmountInfo
						.getCustomsDeclarationAmount());
		if (exg.getDzscEmsPorHead().getCorrEmsNo() != null
				&& !"".equals(exg.getDzscEmsPorHead().getCorrEmsNo().trim())) {
			double money = objToDouble(tfAmount.getValue())
					* objToDouble(tfUnitPrice.getValue());
			double canMoney = (this.tempDzscCommMoneyAmountInfo
					.getCanPutOnRecordsMoney() == null ? 0.0
					: this.tempDzscCommMoneyAmountInfo
							.getCanPutOnRecordsMoney());
			if (money > canMoney) {
				JOptionPane.showMessageDialog(this, "当前商品通关备案总值: "
						+ CommonVars.formatDoubleToString(money) + "; "
						+ "大于成品出口备案允许余额: "
						+ CommonVars.formatDoubleToString(canMoney) + "; 超额: "
						+ CommonVars.formatDoubleToString(money - canMoney)
						+ "; ", "提示", 2);
				return true;
			}
		}
		if (amount < cdAmont) {
			JOptionPane.showMessageDialog(this, "当前商品通关备案数量: "
					+ CommonVars.formatDoubleToString(amount) + "; "
					+ "小于成品出口报关单数量: "
					+ CommonVars.formatDoubleToString(cdAmont) + "; 不足额: "
					+ CommonVars.formatDoubleToString(cdAmont - amount) + "; ",
					"提示", 2);
			return true;
		}
		return false;
	}

	/**
	 * 判断输入数值的小数是否与参数设定中小数位一致
	 */
	private boolean validate(String text, Integer bitValue) {
		if (bitValue == null) {
			bitValue = 5;
		} else if (bitValue == 0) {
			bitValue++;
		}
		Double value = Double.valueOf(text);
		DecimalFormat format = new DecimalFormat();
		format.setGroupingSize(999);
		format.setMaximumFractionDigits(999);
		String test = format.format(value);
		String[] strs = test.split("\\.");// 正则表达式
		// BigDecimal bd=new BigDecimal(String.valueOf(value.doubleValue()));
		// int count1 = test.length();
		// int count2 = test.indexOf(".") + 1;
		// int margin = count1 - count2;
		if (strs.length >= 2) {
			int margin = strs[1].length();
			if (margin > bitValue.intValue()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 填值
	 * 
	 * @param exg
	 */
	private void fillData(DzscEmsExgBill exg) {
		exg.setComplex(this.complex);
		exg.setName(tfName.getText());
		exg.setSpec(tfSpec.getText());
		exg.setUnit((Unit) cbbUnit.getSelectedItem());
		exg.setAmount(objToDouble(tfAmount.getValue()));
		exg.setPrice(objToDouble(tfUnitPrice.getValue()));
		exg.setMachinPrice(objToDouble(tfMachinPrice.getValue()));
		// exg.setMachinMoney(CommonVars.formatDouble(exg.getMachinPrice())
		// * CommonVars.formatDouble(exg.getAmount()));
		// exg.setMoney(CommonVars.formatDouble(exg.getPrice())
		// * CommonVars.formatDouble(exg.getAmount()));
		exg.setMachinMoney(objToDouble(tfMachinMoney.getValue()));
		exg.setMoney(objToDouble(tfMoney.getValue()));
		exg.setUnitNetWeight(objToDouble(tfUnitNetWeight.getValue()));
		exg.setUnitGrossWeight(objToDouble(tfUnitGrossWeight.getValue()));
		exg.setLegalUnitGene(objToDouble(tfLegalUnitGene.getValue()));
		exg.setLegalUnit2Gene(objToDouble(tfLegalUnit2Gene.getValue()));
		exg.setCountry((Country) cbbCountry.getSelectedItem());
		exg.setImgMoney(objToDouble(tfImgMoney.getValue()));
		exg.setNote(tfNote.getText());

		// 申报状态
		exg.setDutyRate(Double.valueOf(((ItemProperty) cbbDutyRate
				.getSelectedItem()).getCode()));
		exg.setLevyMode((LevyMode) cbbLevyMode.getSelectedItem());
		if (!ModifyMarkState.ADDED.equals(exg.getModifyMark())) {
			exg.setModifyMark(ModifyMarkState.MODIFIED);
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(377, 337, 67, 24);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPorExgBill.this.dispose();
				}
			});
		}
		return btnExit;
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
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setEditable(false);
			tfComplex.setBounds(322, 32, 141, 22);
		}
		return tfComplex;
	}

	/**
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(463, 32, 22, 22);
			btnComplex.setText("...");
			btnComplex.setVisible(true);
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex obj = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (obj != null) {
						complex = obj;
						tfComplex.setText(complex.getCode());
						tfLegalUnit.setText(complex.getFirstUnit().getName());
						tfLegalUnit2
								.setText(complex.getSecondUnit() == null ? ""
										: complex.getSecondUnit().getName());
						DzscEmsExgBill exg = (DzscEmsExgBill) tableModel
								.getCurrentRow();
						String modifyMark = exg.getModifyMark();
						if (ModifyMarkState.ADDED.equals(modifyMark)) {
							tfName.setText(complex.getName());
						}
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes tfMachinPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMachinPrice() {
		if (tfMachinPrice == null) {
			tfMachinPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMachinPrice.setBounds(91, 106, 159, 22);
		}
		return tfMachinPrice;
	}

	/**
	 * This method initializes tfImgMoney
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfImgMoney() {
		if (tfImgMoney == null) {
			tfImgMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfImgMoney.setBounds(321, 105, 164, 22);
		}
		return tfImgMoney;
	}

	private JLabel lbExpCanMoney = null;

	private JLabel lbDeclarationAmount = null;

	private JLabel lbCDAmount = null;

	private JLabel lbCanExpMoney = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JLabel lbLegalUnitGene = null;

	private JLabel lbLegalUnit2Gene = null;

	private JFormattedTextField tfLegalUnitGene = null;

	private JFormattedTextField tfLegalUnit2Gene = null;

	private JLabel lbMachinMoney = null;

	private JFormattedTextField tfMachinMoney = null;

	private JFormattedTextField tfMoney = null;

	private JLabel lbTotalMoney = null;

	private JLabel lbLegalUnit = null;

	private JTextField tfLegalUnit = null;

	private JLabel lbLegalUnit2 = null;

	private JTextField tfLegalUnit2 = null;

	private JComboBox cbbUnit = null;

	private JLabel lbDutyRate = null;

	private JComboBox cbbDutyRate = null;

	/**
	 * 转换strToDouble 填充数据
	 */
	public Double objToDouble(Object value) {
		return (value == null ? 0.0 : Double.parseDouble(value.toString()));
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new Rectangle(179, 337, 67, 24));
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkData()) {
						return;
					}
					fillData(exg);
					List list = dzscAction.saveDzscEmsExgBill(new Request(CommonVars
							.getCurrUser()), exg);
					exg = (DzscEmsExgBill)list.get(0);
					tableModel.updateRow(exg);
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
					String str = (String)list.get(1);
					if(str!=null && !"".equals(str)){
						DgMessage message = new DgMessage("提示", str);
					}
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(280, 337, 67, 24));
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkData()) {
						return;
					}
					fillData(exg);
					List list  = dzscAction.saveDzscEmsExgBill(new Request(CommonVars
							.getCurrUser()), exg);
					exg = (DzscEmsExgBill)list.get(0);
					tableModel.updateRow(exg);
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					showData();
					dataState = DataState.EDIT;
					setState();
					String str = (String)list.get(1);
					if(str!=null && !"".equals(str)){
						DgMessage message = new DgMessage("提示", str);
					}
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes tfLegalUnitGene
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalUnitGene() {
		if (tfLegalUnitGene == null) {
			tfLegalUnitGene = new JFormattedTextField();
			tfLegalUnitGene.setBounds(new Rectangle(361, 205, 124, 24));
		}
		return tfLegalUnitGene;
	}

	/**
	 * This method initializes tfLegalUnit2Gene
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalUnit2Gene() {
		if (tfLegalUnit2Gene == null) {
			tfLegalUnit2Gene = new JFormattedTextField();
			tfLegalUnit2Gene.setBounds(new Rectangle(384, 230, 101, 24));
		}
		return tfLegalUnit2Gene;
	}

	/**
	 * This method initializes tfMachinMoney
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMachinMoney() {
		if (tfMachinMoney == null) {
			tfMachinMoney = new JFormattedTextField();
			tfMachinMoney.setBounds(new Rectangle(326, 256, 159, 22));
			tfMachinMoney.setEditable(false);
		}
		return tfMachinMoney;
	}

	/**
	 * This method initializes tfMoney
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMoney() {
		if (tfMoney == null) {
			tfMoney = new JFormattedTextField();
			tfMoney.setBounds(new Rectangle(321, 128, 164, 22));
			tfMoney.setEditable(false);
		}
		return tfMoney;
	}

	/**
	 * This method initializes tfLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(new Rectangle(101, 205, 149, 23));
			tfLegalUnit.setEditable(false);
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes tfLegalUnit2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit2() {
		if (tfLegalUnit2 == null) {
			tfLegalUnit2 = new JTextField();
			tfLegalUnit2.setBounds(new Rectangle(105, 231, 145, 23));
			tfLegalUnit2.setEditable(false);
		}
		return tfLegalUnit2;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new Rectangle(321, 80, 164, 22));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes cbbDutyRate
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDutyRate() {
		if (cbbDutyRate == null) {
			cbbDutyRate = new JComboBox();
			cbbDutyRate.setBounds(new Rectangle(90, 283, 160, 23));
		}
		return cbbDutyRate;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
