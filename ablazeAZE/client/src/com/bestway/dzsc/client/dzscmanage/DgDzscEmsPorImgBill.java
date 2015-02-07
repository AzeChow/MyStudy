/*
 * Created on 2005-3-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.TempDzscCommMoneyAmountInfo;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.SwingConstants;

/**
 * 料件修改
 * 
 * lm modify by 2009-12-4
 * 
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgDzscEmsPorImgBill extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel lbImg = null;

	private JLabel lbComplex = null;

	private JLabel lbName = null;

	private JLabel lbSpec = null;

	private JLabel lbPrice = null;

	private JLabel lbAmount = null;

	private JLabel lbMoney = null;

	private JLabel lbCountry = null;

	private JLabel lbUnit = null;

	private JLabel lbUnitWeight = null;

	private JLabel lbNote = null;

	private JLabel lbLevyMode = null;

	private JLabel lbSeqNum = null;

	private JTextField tfName = null;

	private JTextField tfSpec = null;

	private JFormattedTextField tfPrice = null;

	private JFormattedTextField tfMoney = null;

	private JFormattedTextField tfAmount = null;

	private JComboBox cbbCountry = null;

	private JFormattedTextField tfUnitNetWeight = null;

	private JTextField tfNote = null;

	private JComboBox cbbLevyMode = null;

	private JTextField tfSeqNum = null;

	private JLabel lbExgList = null;

	private JTable tbExgList = null;

	private JScrollPane jScrollPane = null;

	private JButton btnOk = null;

	private JButton btnExit = null;
	/** 存放自用商品编码资料 */
	private Complex complex = null;
	/** 电子手册Action接口类 */
	private DzscAction dzscAction = null;
	/** JBCUS系统所扩展的JTableModel 通过List数据源来显示&绑定数据 */
	private JTableListModel tableModel = null;
	/** 存放电子手册通关备案里的料件资料 */
	private DzscEmsImgBill img = null;
	/** 存放电子手册通关备案里的表头资料 */
	private DzscEmsPorHead head = null;

	private JLabel lbRemark = null;

	private JTextField tfComplex = null;

	private JButton btnComplex = null;
	/** JBCUS系统所扩展的JTableModel 通过List数据源来显示&绑定数据 */
	private JTableListModel tableModelExg = null;

	private JLabel lbCanImpMoney = null;
	/** 临时实体类 */
	private TempDzscCommMoneyAmountInfo tempDzscCommMoneyAmountInfo = null; // @jve:decl-index=0:

	private JLabel lbCDAmount = null;

	private JLabel lbDeclarationAmount = null;

	private JLabel lbImpCanMoney = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;
	/** 数据显示状态:0 为浏览状态 */
	private int dataState = DataState.BROWSE;

	private JLabel lbDetailSpec = null;

	private JTextField tfDetailSpec = null;

	private JLabel lbLegalUnitGene = null;

	private JLabel lbLegalUnit2Gene = null;

	private JFormattedTextField tfLegalUnitGene = null;

	private JFormattedTextField tfLegalUnit2Gene = null;

	private JLabel lbLegalUnit = null;

	private JLabel lbLegalUnit2 = null;

	private JTextField tfLegalUnit = null;

	private JTextField tfLegalUnit2 = null;

	private JComboBox cbbUnit = null;

	private JLabel lbDutyRate = null;

	private JFormattedTextField tfDutyRate = null;

	private JLabel lbPercentsign = null;

	/**
	 * 电子手册参数设置
	 */
	private DzscParameterSet parameterSet = null;

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
	public DgDzscEmsPorImgBill() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		parameterSet = dzscAction.findDzscParameterSet(new Request(CommonVars
				.getCurrUser(), true));
		initialize();
	}

	/**
	 * 转换strToDouble 填充数据
	 * 
	 * @param value
	 * @return
	 */
	public Double objToDouble(Object value) {
		return (value == null ? 0.0 : Double.parseDouble(value.toString()));
	}

	/**
	 * 显示窗口
	 */
	public void setVisible(boolean b) {
		if (b) {
			if (tableModel.getCurrentRow() != null) {
				img = (DzscEmsImgBill) tableModel.getCurrentRow();
				initUI();
				showData();
				setState();
				tableModelExg = DzscClientLogic.initTableImgBillForExg(
						tableModelExg, tbExgList, img);
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
		this.setSize(574, 518);
		this.setContentPane(getJContentPane());
		this.setTitle("料件修改");
	}

	/**
	 * 填充动态ComboBox
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
						tfMoney.setValue(getTotalPrice());
					}
				});

		// 单价
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfPrice,
				priceBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfPrice,
				new AutoCalcListener() {
					public void run() {
						tfMoney.setValue(getTotalPrice());
					}
				});
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
		// 非保税料件比例
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfDutyRate, 5);
	}

	/**
	 * 显示数据
	 */
	private void showData() {
		img = (DzscEmsImgBill) tableModel.getCurrentRow();
		this.complex = img.getComplex();
		tfName.setText(img.getName());
		tfSpec.setText(img.getSpec());
		tfDetailSpec.setText(img.getDetailNote());// 详细型号规格
		tfPrice.setValue(img.getPrice());
		tfAmount.setValue(img.getAmount());
		tfMoney.setValue(img.getMoney());
		cbbCountry.setSelectedItem(img.getCountry());
		cbbUnit.setSelectedItem(img.getUnit());
		tfLegalUnit.setText(img.getComplex().getFirstUnit() == null ? "" : img
				.getComplex().getFirstUnit().getName());
		tfLegalUnit2.setText(img.getComplex().getSecondUnit() == null ? ""
				: img.getComplex().getSecondUnit().getName());
		tfUnitNetWeight.setValue(img.getUnitWeight());
		tfNote.setText(img.getNote());
		cbbLevyMode.setSelectedItem(img.getLevyMode());
		tfSeqNum.setText(String.valueOf(img.getSeqNum()));
		tfComplex.setText(img.getComplex().getCode());
		tfLegalUnitGene.setValue(img.getLegalUnitGene());
		tfLegalUnit2Gene.setValue(img.getLegalUnit2Gene());
		this.tempDzscCommMoneyAmountInfo = dzscAction
				.findDzscCurrentEmsCommInfo(new Request(CommonVars
						.getCurrUser()), head, true, img.getSeqNum());
		this.lbCanImpMoney.setText(CommonVars
				.formatDoubleToString(tempDzscCommMoneyAmountInfo
						.getCanPutOnRecordsMoney())
				+ " HKD");
		this.lbCDAmount.setText(CommonVars
				.formatDoubleToString(tempDzscCommMoneyAmountInfo
						.getCustomsDeclarationAmount()));
		if (img.getDutyRate() != null) {
			this.tfDutyRate.setValue(img.getDutyRate());
		} else {
			tfDutyRate.setValue(new Double(0));
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			lbPercentsign = new JLabel();
			lbPercentsign.setText("%");
			lbPercentsign.setSize(new Dimension(11, 22));
			lbPercentsign.setLocation(new Point(516, 177));
			lbPercentsign.setHorizontalAlignment(SwingConstants.CENTER);
			lbDutyRate = new JLabel();
			lbDutyRate.setBounds(new Rectangle(368, 177, 84, 21));
			lbDutyRate.setText("征税比例");
//			lbDutyRate.setForeground(java.awt.Color.blue);
			lbLegalUnit2 = new JLabel();
			lbLegalUnit2.setBounds(new Rectangle(35, 177, 77, 21));
			lbLegalUnit2.setText("第二法定单位");
			lbLegalUnit = new JLabel();
			lbLegalUnit.setBounds(new Rectangle(35, 154, 54, 21));
			lbLegalUnit.setText("法定单位");
			lbLegalUnit2Gene = new JLabel();
			lbLegalUnit2Gene.setBounds(new Rectangle(173, 177, 121, 21));
			lbLegalUnit2Gene.setText("第二法定单位比例因子");
			lbLegalUnitGene = new JLabel();
			lbLegalUnitGene.setBounds(new Rectangle(280, 153, 99, 22));
			lbLegalUnitGene.setText("法定单位比例因子");
			lbDetailSpec = new JLabel();
			lbDetailSpec.setBounds(new Rectangle(35, 223, 77, 21));
			lbDetailSpec.setText("详细型号规格");
			lbCDAmount = new JLabel();
			lbCDAmount.setBounds(new Rectangle(372, 406, 156, 17));
			lbCDAmount.setForeground(java.awt.Color.blue);
			lbCDAmount.setText("");
			lbCanImpMoney = new JLabel();
			lbImpCanMoney = new JLabel();
			lbImpCanMoney.setBounds(new Rectangle(30, 406, 85, 17));
			lbImpCanMoney.setText("\u6599\u4ef6\u8fdb\u53e3\u4f59\u989d\uff1a");
			lbImpCanMoney.setForeground(Color.blue);
			lbDeclarationAmount = new JLabel();
			lbDeclarationAmount.setBounds(new Rectangle(296, 406, 75, 17));
			lbDeclarationAmount.setText("\u62a5\u5173\u5355\u6570\u91cf:");
			lbDeclarationAmount.setForeground(Color.blue);
			lbRemark = new JLabel();
			lbExgList = new JLabel();
			lbSeqNum = new JLabel();
			lbLevyMode = new JLabel();
			lbNote = new JLabel();
			lbUnitWeight = new JLabel();
			lbUnitWeight.setVisible(false);
			lbUnit = new JLabel();
			lbCountry = new JLabel();
			lbMoney = new JLabel();
			lbAmount = new JLabel();
			lbPrice = new JLabel();
			lbSpec = new JLabel();
			lbName = new JLabel();
			lbComplex = new JLabel();
			lbImg = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			lbImg.setBounds(11, 12, 65, 22);
			lbImg.setText("料件表");
			lbImg.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			lbImg.setForeground(java.awt.Color.blue);
			lbComplex.setBounds(280, 34, 56, 20);
			lbComplex.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			lbComplex.setText("商品编码");
			lbComplex.setForeground(java.awt.Color.blue);
			lbName.setBounds(35, 57, 54, 21);
			lbName.setText("商品名称");
			lbName.setForeground(java.awt.Color.blue);
			lbSpec.setBounds(280, 57, 56, 21);
			lbSpec.setText("型号规格");
			lbPrice.setBounds(35, 106, 28, 20);
			lbPrice.setText("单价");
			lbAmount.setBounds(35, 82, 29, 19);
			lbAmount.setForeground(java.awt.Color.blue);
			lbAmount.setText("数量");
			lbMoney.setBounds(280, 105, 39, 20);
			lbMoney.setText("金额");
			lbCountry.setBounds(280, 129, 42, 22);
			lbCountry.setText("原产地");
			lbUnit.setBounds(280, 82, 25, 20);
			lbUnit.setText("单位");
			lbUnit.setForeground(java.awt.Color.blue);
			lbUnitWeight.setBounds(177, 250, 88, 22);
			lbUnitWeight.setText("单位重量(公斤)");
			lbNote.setBounds(35, 200, 77, 18);
			lbNote.setText("说      明");
			lbLevyMode.setBounds(35, 129, 53, 23);
			lbLevyMode.setText("征免方式");
			lbSeqNum.setBounds(35, 34, 54, 21);
			lbSeqNum.setForeground(java.awt.Color.blue);
			lbSeqNum.setText("备案序号");
			lbExgList.setBounds(11, 250, 162, 24);
			lbExgList.setText("使用该料件的成品列表");
			lbExgList.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
					14));
			lbExgList.setForeground(java.awt.Color.blue);
			lbRemark.setBounds(397, 250, 131, 20);
			lbRemark.setText("(注：蓝色字体为必填项)");
			lbRemark.setForeground(java.awt.Color.blue);
			lbCanImpMoney.setBounds(114, 406, 177, 17);
			lbCanImpMoney.setText("");
			lbCanImpMoney.setForeground(java.awt.Color.blue);
			jContentPane.add(lbImg, null);
			jContentPane.add(lbComplex, null);
			jContentPane.add(lbName, null);
			jContentPane.add(lbSpec, null);
			jContentPane.add(lbPrice, null);
			jContentPane.add(lbAmount, null);
			jContentPane.add(lbMoney, null);
			jContentPane.add(lbCountry, null);
			jContentPane.add(lbUnit, null);
			jContentPane.add(lbUnitWeight, null);
			jContentPane.add(lbNote, null);
			jContentPane.add(lbLevyMode, null);
			jContentPane.add(lbSeqNum, null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(getTfPrice(), null);
			jContentPane.add(getTfMoney(), null);
			jContentPane.add(getTfAmount(), null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(getTfUnitNetWeight(), null);
			jContentPane.add(getTfNote(), null);
			jContentPane.add(getCbbLevyMode(), null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(lbExgList, null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(lbRemark, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(lbCanImpMoney, null);
			jContentPane.add(lbDeclarationAmount, null);
			jContentPane.add(lbImpCanMoney, null);
			jContentPane.add(lbCDAmount, null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(lbDetailSpec, null);
			jContentPane.add(getTfDetailSpec(), null);
			jContentPane.add(lbLegalUnitGene, null);
			jContentPane.add(lbLegalUnit2Gene, null);
			jContentPane.add(getTfLegalUnitGene(), null);
			jContentPane.add(getTfLegalUnit2Gene(), null);
			jContentPane.add(lbLegalUnit, null);
			jContentPane.add(lbLegalUnit2, null);
			jContentPane.add(getTfLegalUnit(), null);
			jContentPane.add(getTfLegalUnit2(), null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(lbDutyRate, null);
			jContentPane.add(getTfDutyRate(), null);
			jContentPane.add(lbPercentsign, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(89, 58, 174, 22);
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
			tfSpec.setBounds(337, 58, 187, 22);
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPrice() {
		if (tfPrice == null) {
			tfPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPrice.setBounds(89, 106, 174, 22);
			tfPrice.setValue(new Double(0));
		}
		return tfPrice;
	}

	/**
	 * This method initializes tfMoney
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMoney() {
		if (tfMoney == null) {
			tfMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMoney.setValue(new Double(0));
			tfMoney.setEditable(false);
			tfMoney.setBounds(337, 105, 187, 22);
		}
		return tfMoney;
	}

	/**
	 * This method initializes tfAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAmount.setBounds(89, 82, 174, 22);
			tfAmount.setValue(new Double(0));
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
			cbbCountry.setBounds(337, 128, 187, 23);
		}
		return cbbCountry;
	}

	/**
	 * This method initializes tfUnitNetWeight
	 * 
	 * @return javax.swinedTextField
	 */
	private JFormattedTextField getTfUnitNetWeight() {
		if (tfUnitNetWeight == null) {
			tfUnitNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			// tfUnitNetWeight.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitNetWeight.setEditable(false);
			tfUnitNetWeight.setVisible(false);
			tfUnitNetWeight.setBounds(264, 250, 122, 22);
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
			tfNote.setSize(new Dimension(414, 22));
			tfNote.setLocation(new Point(113, 200));
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
			cbbLevyMode.setBounds(89, 129, 173, 22);
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
			tfSeqNum.setBounds(89, 34, 174, 22);
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes tbExgList
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExgList() {
		if (tbExgList == null) {
			tbExgList = new JTable();
		}
		return tbExgList;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(29, 278, 502, 124);
			jScrollPane.setViewportView(getTbExgList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(73, 429, 60, 27);
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkData()) {
						return;
					}
					fillData(img);
					Long date1=System.currentTimeMillis();
					img = dzscAction.onlySaveDzscEmsImgBill(new Request(CommonVars
							.getCurrUser()), img);
					Long date2=System.currentTimeMillis();
					System.out.println("data2-date1="+(date2-date1));
					tableModel.updateRow(img);
					
					if (tbExgList.getCellEditor() != null) {
						tbExgList.getCellEditor().stopCellEditing();
					}
					List list = tableModelExg.getList();
					Long date3=System.currentTimeMillis();
					System.out.println("data3-date2="+(date3-date2));
					for (int i = 0; i < list.size(); i++) {
						DzscEmsBomBill bom = (DzscEmsBomBill) list.get(i);
						bom.setName(img.getName());
						bom.setSpec(img.getSpec());
						bom.setComplex(img.getComplex());
						bom.setUnit(img.getUnit());
						bom.setPrice(img.getPrice());
						bom.setMoney(bom.getAmount()*bom.getPrice());
						tableModelExg.updateRow(bom);
						 dzscAction.saveImgToDzscEmsBomBill(new Request(
									CommonVars.getCurrUser()), bom);
					}
					
					
					Long date4=System.currentTimeMillis();
					System.out.println("data4-date3="+(date4-date3));

					DgDzscEmsPorImgBill.this.dataState = DataState.BROWSE;
					setState();
					
					
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
		// JOptionPane.showMessageDialog(this, "原产地不能为空！", "提示", 2);
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
		if (img.getDzscEmsPorHead().getCorrEmsNo() != null
				&& !"".equals(img.getDzscEmsPorHead().getCorrEmsNo().trim())) {
			double money = (this.tfMoney.getValue() == null ? 0.0 : Double
					.parseDouble(this.tfMoney.getValue().toString()));
			double canMoney = (this.tempDzscCommMoneyAmountInfo
					.getCanPutOnRecordsMoney() == null ? 0.0
					: this.tempDzscCommMoneyAmountInfo
							.getCanPutOnRecordsMoney());
			if (money > canMoney) {
				JOptionPane.showMessageDialog(this, "当前商品通关备案总值: "
						+ CommonVars.formatDoubleToString(money) + "; "
						+ "大于料件进口备案允许余额: "
						+ CommonVars.formatDoubleToString(canMoney) + "; 超额: "
						+ CommonVars.formatDoubleToString(money - canMoney)
						+ "; ", "提示", 2);
				return true;
			}
		}
		if (amount < cdAmont) {
			JOptionPane.showMessageDialog(this, "当前商品通关备案数量: "
					+ CommonVars.formatDoubleToString(amount) + "; "
					+ "小于料件进口报关单数量: "
					+ CommonVars.formatDoubleToString(cdAmont) + "; 不足额: "
					+ CommonVars.formatDoubleToString(cdAmont - amount) + "; ",
					"提示", 2);
			return true;
		}
		if (((Number) this.tfDutyRate.getValue()).doubleValue() > 99
				|| ((Number) this.tfDutyRate.getValue()).doubleValue() < 0) {
			JOptionPane.showMessageDialog(this, "非保税料件比例范围大于等于0且小于等于99", "警告！",
					JOptionPane.INFORMATION_MESSAGE);
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
	 * @param img
	 */
	private void fillData(DzscEmsImgBill img) {
		img.setComplex(this.complex);
		img.setName(tfName.getText());
		img.setSpec(tfSpec.getText());
		img.setUnit((Unit) cbbUnit.getSelectedItem());
		img.setDetailNote(tfDetailSpec.getText()); // 详细型号规格
		img.setPrice(objToDouble(tfPrice.getValue()));
		img.setMoney(objToDouble(tfMoney.getValue()));
		img.setAmount(objToDouble(tfAmount.getValue()));
		img.setCountry((Country) cbbCountry.getSelectedItem());
		img.setUnitWeight(objToDouble(tfUnitNetWeight.getValue()));
		img.setNote(tfNote.getText());
		img.setLevyMode((LevyMode) cbbLevyMode.getSelectedItem());
		img.setLegalUnitGene(objToDouble(tfLegalUnitGene.getValue()));
		img.setLegalUnit2Gene(objToDouble(tfLegalUnit2Gene.getValue()));
		if (!ModifyMarkState.ADDED.equals(img.getModifyMark())) {
			img.setModifyMark(ModifyMarkState.MODIFIED);
		}
		// 非保税料件比例
		this.img.setDutyRate(Double.valueOf(this.tfDutyRate.getValue()
				.toString()));
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(372, 429, 60, 27);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPorImgBill.this.dispose();
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
			tfComplex.setBounds(337, 34, 164, 22);
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
			btnComplex.setBounds(501, 34, 21, 22);
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
						DzscEmsImgBill img = (DzscEmsImgBill) tableModel
								.getCurrentRow();
						String modifyMark = img.getModifyMark();
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
	 * 设置料件总金额
	 */
	private Double getTotalPrice() {
		try {
			int moneyBit = parameterSet.getMoneyBit() == null ? 5
					: parameterSet.getMoneyBit();
			double amount = Double
					.parseDouble(this.tfAmount.getValue() == null ? "0"
							: this.tfAmount.getValue().toString())
					* Double.parseDouble(tfPrice.getValue() == null ? "0"
							: tfPrice.getValue().toString());
			BigDecimal bd = new BigDecimal(amount);
			return bd.setScale(moneyBit, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
		} catch (Exception ex) {
		}
		return 0.0;
		// double amount = objToDouble(this.tfAmount.getValue())
		// * objToDouble(tfPrice.getValue());
		// return CommonVars.getDoubleByDigit(amount, 5);
	}

	/**
	 * @param head
	 *            The head to set.
	 */
	public void setHead(DzscEmsPorHead head) {
		this.head = head;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new Rectangle(171, 429, 60, 27));
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkData()) {
						return;
					}
					DgDzscEmsPorImgBill.this.dataState = DataState.BROWSE;
					fillData(img);
					img = dzscAction.saveDzscEmsImgBill(new Request(CommonVars
							.getCurrUser()), img);
					tableModel.updateRow(img);
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
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * 设置组件的状态
	 */
	private void setState() {
		img = (DzscEmsImgBill) tableModel.getCurrentRow();
		String declareState = img.getDzscEmsPorHead().getDeclareState();
		this.btnComplex.setEnabled(this.dataState != DataState.BROWSE);
		this.tfName.setEditable(!DzscState.CHANGE.equals(declareState)
				&& this.dataState != DataState.BROWSE);
		this.tfSpec.setEditable(this.dataState != DataState.BROWSE);
		this.tfName
				.setEditable((img.getDzscEmsPorHead().getCopEntNo() == null || ""
						.equals(img.getDzscEmsPorHead().getCopEntNo().trim()))
						&& this.dataState != DataState.BROWSE);
		this.tfSpec
				.setEditable((img.getDzscEmsPorHead().getCopEntNo() == null || ""
						.equals(img.getDzscEmsPorHead().getCopEntNo().trim()))
						&& this.dataState != DataState.BROWSE);
		this.cbbUnit
				.setEnabled((img.getDzscEmsPorHead().getCopEntNo() == null || ""
						.equals(img.getDzscEmsPorHead().getCopEntNo().trim()))
						&& this.dataState != DataState.BROWSE);

		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		tableModelExg = DzscClientLogic.initTableImgBillForExg(tableModelExg,
				tbExgList, img);

		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnOk.setEnabled(this.dataState != DataState.BROWSE);
		// this.getBtnComplex().setEnabled( this.dataState != DataState.BROWSE);
		// this.tfName.setEditable( this.dataState != DataState.BROWSE);
		// this.tfSpec.setEditable( this.dataState != DataState.BROWSE);
		this.cbbCountry.setEnabled(this.dataState != DataState.BROWSE);
		this.tfAmount.setEditable(this.dataState != DataState.BROWSE);
		// this.tfUnit .setEditable( this.dataState != DataState.BROWSE);
		this.tfPrice.setEditable(this.dataState != DataState.BROWSE);
		this.cbbLevyMode.setEnabled(this.dataState != DataState.BROWSE);
		this.tfNote.setEditable(this.dataState != DataState.BROWSE);
		tfDetailSpec.setEditable(this.dataState != DataState.BROWSE);
		this.tfLegalUnitGene.setEditable(this.dataState != DataState.BROWSE);
		this.tfLegalUnit2Gene.setEditable(this.dataState != DataState.BROWSE);
		this.lbImpCanMoney
				.setVisible(img.getDzscEmsPorHead().getCorrEmsNo() != null
						&& !"".equals(img.getDzscEmsPorHead().getCorrEmsNo()
								.trim()));
		this.lbCanImpMoney
				.setVisible(img.getDzscEmsPorHead().getCorrEmsNo() != null
						&& !"".equals(img.getDzscEmsPorHead().getCorrEmsNo()
								.trim()));
		this.tfDutyRate.setEditable(this.dataState != DataState.BROWSE);
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new Rectangle(271, 429, 60, 27));
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkData()) {
						return;
					}
					DgDzscEmsPorImgBill.this.dataState = DataState.BROWSE;
					fillData(img);
					img = dzscAction.saveDzscEmsImgBill(new Request(CommonVars
							.getCurrUser()), img);
					tableModel.updateRow(img);
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
				}
			});

		}
		return btnNext;
	}

	/**
	 * This method initializes tfDetailSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDetailSpec() {
		if (tfDetailSpec == null) {
			tfDetailSpec = new JTextField();
			tfDetailSpec.setBounds(new Rectangle(113, 223, 414, 22));
		}
		return tfDetailSpec;
	}

	/**
	 * This method initializes tfLegalUnitGene
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalUnitGene() {
		if (tfLegalUnitGene == null) {
			tfLegalUnitGene = new JFormattedTextField();
			tfLegalUnitGene.setBounds(new Rectangle(382, 152, 144, 22));
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
			tfLegalUnit2Gene.setBounds(new Rectangle(295, 177, 71, 22));
		}
		return tfLegalUnit2Gene;
	}

	/**
	 * This method initializes tfLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(new Rectangle(89, 153, 172, 22));
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
			tfLegalUnit2.setBounds(new Rectangle(113, 177, 58, 22));
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
			cbbUnit.setBounds(new Rectangle(337, 81, 187, 23));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes tfDutyRate
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDutyRate() {
		if (tfDutyRate == null) {
			tfDutyRate = new JFormattedTextField();
			tfDutyRate.setEditable(false);
			tfDutyRate.setSize(new Dimension(64, 22));
			tfDutyRate.setLocation(new Point(453, 177));
		}
		return tfDutyRate;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
