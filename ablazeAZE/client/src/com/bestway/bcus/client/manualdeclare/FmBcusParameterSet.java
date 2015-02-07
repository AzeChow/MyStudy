/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.RegexUtil;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;

/**
 * 帐子帐册系统参数设置
 * 
 * Checked by lxr
 * 
 * @author lxr
 * 
 */
public class FmBcusParameterSet extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JButton btnSave = null;
	private JButton btnClose = null;
	private JButton btnEdit = null;
	private JButton btnOtherSet = null;
	private JButton btnPathSet = null;

	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel4 = null;

	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabel11 = null;
	private JLabel jLabel12 = null;
	private JLabel jLabel13 = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JLabel jLabel17 = null;
	private JLabel jLabel121 = null;
	private JLabel jLabel51 = null;
	private JLabel jLabel511 = null;
	private JLabel jLabel71 = null;
	private JLabel jLabel91 = null;
	private JCheckBox cbAutoRound = null;
	private JCheckBox cbMergerBatchDeclare = null;
	private JCheckBox cbSearch = null;
	private JCheckBox cbEmsBatchDeclare = null;
	private JCheckBox cbDefault = null;
	private JCheckBox cbMergerEditModifyMark = null;
	private JCheckBox cbEmsEditModifyMark = null;
	private JCheckBox cbAutoMergerBefor = null;
	private JCheckBox cbPrint = null;
	private JCheckBox cbMateriSoure = null;
	private JCheckBox cbMergerMaxVer = null;

	private JTextField tfRatio = null;
	private JTextField tfBGDLoadDir = null;
	private JTextField tfPriceDecimal = null;
	// private JTextField tfUnitWaste = null;
	private JFormattedTextField tfReportDecimalLength = null;
	/**
	 * 存放系统参数设置Entity
	 */
	private CompanyOther obj = null; // @jve:decl-index=0:
	/**
	 * 数据状态
	 */
	private int dataState = DataState.BROWSE;

	private SystemAction systemAction = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JLabel jLabel131 = null;

	private JPanel jPanel81 = null;

	private JRadioButton cbbPriceByMonth = null;
	private JRadioButton cbbPriceByTotal = null;

	private ButtonGroup bgbus = null; // @jve:decl-index=0:

	private JLabel jLabel132 = null;

	private JCheckBox cbAmountControl = null;

	private JLabel jLabel31 = null;

	private JLabel jLabel7112 = null;

	private JTextField tfReportDecimalLengthOfQinDan = null;

	private JLabel jLabel71112 = null;

	private JTextField tfReportDecimalLengthOfQinDanM = null;

	private JLabel jLabel711111 = null;

	private JTextField tfReportDecimalLengthOfQinDanA = null;

	private JComboBox tfUnitWaste = null;

	private JComboBox tfWaste = null;

	private JLabel jLabel16 = null;

	private JCheckBox cbImpTotalMoney = null;
	private JTextField tfControlPrice;
	private JLabel lbControlPrice;
	private JLabel lblNewLabel;
	private JCheckBox cbCavIsInclude;
	private JLabel label;
	private JLabel label_1;
	private JCheckBox cbResult;
	private JCheckBox checkBoxprcie;

	/**
	 * This is the default constructor
	 */
	public FmBcusParameterSet() {
		super();
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		initialize();
		getBgbcs();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("电子帐册系统参数设置");
		this.setSize(809, 657);
		this.setContentPane(getJContentPane());
		manualDecleareAction.getBcusParaPurview(new Request(CommonVars
				.getCurrUser()));
		getCompanyOther();
		showData();
		dataState = DataState.BROWSE;
		setState();

	}

	private ButtonGroup getBgbcs() {
		if (bgbus == null) {
			bgbus = new ButtonGroup();
			bgbus.add(getCbbPriceByMonth());
			bgbus.add(getCbbPriceByTotal());
		}
		return bgbus;
	}

	/**
	 * 显示数据
	 */
	private void showData() {

		String unitwear = manualDecleareAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EMSBOM_UNITWEAR_DIGITS);
		tfUnitWaste
				.setSelectedIndex(unitwear == null || "".equals(unitwear) ? 0
						: (Integer.valueOf(unitwear) > 9 ? 9 : Integer
								.valueOf(unitwear)));

		String incrementRate = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.INCREMENT_RATE);
		tfRatio.setText(incrementRate);

		String emsbomprice = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.EMS_BOM_PRICE);
		tfPriceDecimal.setText(emsbomprice == null ? "2" : emsbomprice);

		String wear = manualDecleareAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EMSBOM_WEAR_DIGITS);
		tfWaste.setSelectedIndex(wear == null || "".equals(wear) ? 0 : (Integer
				.valueOf(wear)>5?5:Integer
						.valueOf(wear)));

		String bgdLoadDir = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.LOAD_QP_BGD_DIR);
		tfBGDLoadDir.setText(bgdLoadDir);

		String customsForInt = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.CUSTOMS_FORINT);
		if (customsForInt != null && "1".equals(customsForInt)) {
			this.cbAutoRound.setSelected(true);
		} else {
			this.cbAutoRound.setSelected(false);
		}

		String isSend = manualDecleareAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.EmsSEND);
		if (isSend != null && "1".equals(isSend)) {
			this.cbMergerBatchDeclare.setSelected(true);
		} else {
			this.cbMergerBatchDeclare.setSelected(false);
		}

		String isSendSign = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.EmsSEND_Sign);
		if (isSendSign != null && "1".equals(isSendSign)) {
			this.cbMergerEditModifyMark.setSelected(true);
		} else {
			this.cbMergerEditModifyMark.setSelected(false);
		}
		String isImgImpTotalMoney = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.ImgImpTotalMoney);
		if (isImgImpTotalMoney != null && "1".equals(isImgImpTotalMoney)) {
			this.cbImpTotalMoney.setSelected(true);
		} else {
			this.cbImpTotalMoney.setSelected(false);
		}
		String isQuery = manualDecleareAction.getBpara(new Request(CommonVars
				.getCurrUser()), BcusParameter.BGD_ISQUERY);
		if (isQuery != null && "1".equals(isQuery)) {
			this.cbSearch.setSelected(true);
		} else {
			this.cbSearch.setSelected(false);
		}

		String isEmsH2kSend = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.EmsEdiH2kSend);
		if (isEmsH2kSend != null && "1".equals(isEmsH2kSend)) {
			this.cbEmsBatchDeclare.setSelected(true);
		} else {
			this.cbEmsBatchDeclare.setSelected(false);
		}

		String isEmsH2kSendSign = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.EmsEdiH2kSend_Sign);
		if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)) {
			this.cbEmsEditModifyMark.setSelected(true);
		} else {
			this.cbEmsEditModifyMark.setSelected(false);
		}

		String isDefaultPrice = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.DefaultPrice);
		if (isDefaultPrice != null && "1".equals(isDefaultPrice)) {
			this.cbDefault.setSelected(true);
		} else {
			this.cbDefault.setSelected(false);
		}

		if (obj != null
				&& this.obj.getIsAutoNewMergerBefore() != null
				&& this.obj.getIsAutoNewMergerBefore()
						.equals(new Boolean(true))) {
			this.cbAutoMergerBefor.setSelected(true);
		} else {
			this.cbAutoMergerBefor.setSelected(false);
		}

		String isPrintCustomWithVersion = manualDecleareAction.getBpara(
				new Request(CommonVars.getCurrUser()),
				BcusParameter.PrintCustomWithVersion);
		if (isPrintCustomWithVersion != null
				&& "1".equals(isPrintCustomWithVersion)) {
			this.cbPrint.setSelected(true);
		} else {
			this.cbPrint.setSelected(false);
		}

		String isEmsBomMaterilSoure = manualDecleareAction.getBpara(
				new Request(CommonVars.getCurrUser()),
				BcusParameter.EMS_BOM_MaterilSoure);
		if (isEmsBomMaterilSoure != null && "1".equals(isEmsBomMaterilSoure)) {
			this.cbMateriSoure.setSelected(true);
		} else {
			this.cbMateriSoure.setSelected(false);
		}

		String isEmsCancelCusPrice = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.Ems_CancelCus_Price);
		if (isEmsCancelCusPrice != null && "0".equals(isEmsCancelCusPrice)) {
			this.cbbPriceByMonth.setSelected(true);
		} else if (isEmsCancelCusPrice != null
				&& "1".equals(isEmsCancelCusPrice)) {
			this.cbbPriceByTotal.setSelected(true);
		}

		String isMergerBomMaxVer = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.MERGER_BOM_MaxVer);
		if (isMergerBomMaxVer != null && "1".equals(isMergerBomMaxVer)) {
			this.cbMergerMaxVer.setSelected(true);
		} else {
			this.cbMergerMaxVer.setSelected(false);
		}
		String isAmountControl = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.Ems_Amount_Control);
		if (isAmountControl != null && "1".equals(isAmountControl)) {
			this.cbAmountControl.setSelected(true);
		} else {
			this.cbAmountControl.setSelected(false);
		}
		String reportDecimalLength = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.ReportDecimalLength);
		if (reportDecimalLength != null) {
			tfReportDecimalLength.setValue(Double.valueOf(reportDecimalLength));
		} else {
			tfReportDecimalLength.setValue(2);
		}

		// String app_bill_price = manualDecleareAction.getBpara(new Request(
		// CommonVars.getCurrUser()), BcusParameter.APLLY_TO_BILL_PRICE);
		// getTfPriceDecimalLength()
		// .setText(
		// (app_bill_price == null || "".equals(app_bill_price)) ?
		// BcusParameter.APLLY_TO_BILL_DEFAULT
		// : app_bill_price);
		//
		// String app_bill_amount = manualDecleareAction.getBpara(new Request(
		// CommonVars.getCurrUser()), BcusParameter.APLLY_TO_BILL_AMOUNT);
		// getTfAmountDecimalLength()
		// .setText(
		// (app_bill_amount == null || "".equals(app_bill_amount)) ?
		// BcusParameter.APLLY_TO_BILL_DEFAULT
		// : app_bill_amount);
		//
		// String app_bill_totalPrice = manualDecleareAction.getBpara(new
		// Request(
		// CommonVars.getCurrUser()),
		// BcusParameter.APLLY_TO_BILL_TOTALPRICE);
		//
		// getTfTotalDecimalLength()
		// .setText(
		// (app_bill_totalPrice == null || ""
		// .equals(app_bill_totalPrice)) ? BcusParameter.APLLY_TO_BILL_DEFAULT
		// : app_bill_totalPrice);
		String bill_amount = manualDecleareAction
				.getBpara(new Request(CommonVars.getCurrUser()),
						BcusParameter.BILL_TO_CONTROL_AMOUNT);
		getTfReportDecimalLengthOfQinDanA()
				.setText(
						(bill_amount == null || "".equals(bill_amount)) ? BcusParameter.APLLY_TO_BILL_DEFAULT
								: bill_amount);
		String bill_price = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.BILL_TO_CONTROL_PRICE);
		getTfReportDecimalLengthOfQinDan()
				.setText(
						(bill_price == null || "".equals(bill_price)) ? BcusParameter.APLLY_TO_BILL_DEFAULT
								: bill_price);
		String bill_totalPrice = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()),
				BcusParameter.BILL_TO_CONTROL_TOTALPRICE);

		getTfReportDecimalLengthOfQinDanM()
				.setText(
						(bill_totalPrice == null || "".equals(bill_totalPrice)) ? BcusParameter.APLLY_TO_BILL_DEFAULT
								: bill_totalPrice);
		String maxPrice = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()),
				BcusParameter.BILL_CONTROL_PRICE);
		tfControlPrice.setText(maxPrice);
		
		
		String cavIsIncludeNonBonde = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()),
				BcusParameter.CAV_REAL_NUM_INCLUDE_NONBONDE);
		if("1".equals(cavIsIncludeNonBonde)) {
			cbCavIsInclude.setSelected(true);
		} else {
			cbCavIsInclude.setSelected(false);
		}
		
		String result = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()),
				BcusParameter.RESULT);
		if(result==null){
			this.cbResult.setSelected(false);
		}else{
			if("1".equals(result)){
				this.cbResult.setSelected(true);
			}else{
				this.cbResult.setSelected(false);
			}
		}
		String premiumprice = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()),
				BcusParameter.Premium_Price);
		if(premiumprice==null){
			this.checkBoxprcie.setSelected(false);
		}else{
			if("1".equals(premiumprice)){
				this.checkBoxprcie.setSelected(true);
			}else{
				this.checkBoxprcie.setSelected(false);
			}
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(javax.swing.BorderFactory
					.createCompoundBorder(null, null));
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel4(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel16 = new JLabel();
			jLabel16.setText("19，核销表头本期进口总金额是否扣减退料出口金额和内销金额");
			jLabel16.setLocation(new Point(401, 485));
			jLabel16.setForeground(Color.blue);
			jLabel16.setSize(new Dimension(388, 14));
			jLabel711111 = new JLabel();
			jLabel711111.setBounds(new Rectangle(611, 323, 33, 16));
			jLabel711111.setText("\u6570\u91cf:");
			jLabel71112 = new JLabel();
			jLabel71112.setBounds(new Rectangle(520, 323, 33, 18));
			jLabel71112.setText("\u91d1\u989d:");
			jLabel7112 = new JLabel();
			jLabel7112.setBounds(new Rectangle(419, 321, 35, 21));
			jLabel7112.setText("\u5355\u4ef7:");
			jLabel31 = new JLabel();
			jLabel31.setBounds(new Rectangle(399, 293, 406, 18));
			jLabel31.setText("15，申请单转清单、清单导入、清单手工录入时相关栏位小数位控制");
			jLabel31.setForeground(new java.awt.Color(51, 0, 255));
			jLabel132 = new JLabel();
			jLabel132.setText("18，帐册退换、返工、复出是否对数量进行控制");
			jLabel132.setSize(new Dimension(319, 18));
			jLabel132.setLocation(new Point(401, 451));
			jLabel132.setForeground(Color.blue);
			jLabel131 = new JLabel();
			jLabel131.setText("16，申请单转清单是否默认归并关系BOM的最大版本号");
			jLabel131.setSize(new Dimension(321, 18));
			jLabel131.setLocation(new Point(400, 352));
			jLabel131.setForeground(Color.blue);
			jLabel511 = new JLabel();
			jLabel511.setText("7，归并关系BOM来源于报关常用BOM是否转国内购料");
			jLabel511.setSize(new Dimension(311, 18));
			jLabel511.setLocation(new Point(40, 288));
			jLabel511.setForeground(Color.blue);
			jLabel15 = new JLabel();
			jLabel15.setText("位小数");
			jLabel15.setLocation(new Point(542, 419));
			jLabel15.setSize(new Dimension(52, 20));
			jLabel14 = new JLabel();
			jLabel14.setText("单价最多保留");
			jLabel14.setLocation(new Point(421, 419));
			jLabel14.setSize(new Dimension(82, 21));
			jLabel13 = new JLabel();
			jLabel13.setForeground(Color.blue);
			jLabel13.setSize(new Dimension(314, 21));
			jLabel13.setLocation(new Point(401, 394));
			jLabel13.setText("17，帐册成品单价小数位控制");
			jLabel91 = new JLabel();
			jLabel91.setText("位小数");
			jLabel91.setLocation(new Point(528, 258));
			jLabel91.setSize(new Dimension(57, 21));
			jLabel71 = new JLabel();
			jLabel71.setText("最多保留:");
			jLabel71.setLocation(new Point(417, 258));
			jLabel71.setSize(new Dimension(57, 21));
			jLabel17 = new JLabel();
			jLabel17.setText("14，报表数量、金额栏位小数位控制");
			jLabel17.setSize(new Dimension(318, 22));
			jLabel17.setLocation(new Point(400, 236));
			jLabel17.setForeground(Color.blue);
			jLabel51 = new JLabel();
			jLabel51.setText("6，非套打出口报关单时，是否打印版本栏位");
			jLabel51.setSize(new Dimension(296, 22));
			jLabel51.setLocation(new Point(40, 233));
			jLabel51.setForeground(Color.blue);
			jLabel121 = new JLabel();
			jLabel121.setText("13，报关单导入路径设置");
			jLabel121.setSize(new Dimension(318, 22));
			jLabel121.setLocation(new Point(400, 187));
			jLabel121.setForeground(Color.blue);
			jLabel12 = new JLabel();
			jLabel12.setForeground(java.awt.Color.blue);
			jLabel12.setSize(new Dimension(318, 22));
			jLabel12.setLocation(new Point(400, 147));
			jLabel12.setText("12，新增电子帐册归并关系是否自动带出归并前");
			jLabel11 = new JLabel();
			jLabel11.setText("增值比率");
			jLabel11.setLocation(new Point(418, 125));
			jLabel11.setSize(new Dimension(58, 21));
			jLabel10 = new JLabel();
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel10.setSize(new Dimension(318, 22));
			jLabel10.setLocation(new Point(400, 103));
			jLabel10.setText("11，增值比率设定");
			jLabel9 = new JLabel();
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel9.setSize(new Dimension(318, 22));
			jLabel9.setLocation(new Point(400, 63));
			jLabel9.setText("10，申请单转报关清单是否默认电子帐册工厂单价");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(418, 40, 266, 21));
			jLabel8.setText("未申报： ○     准备申报： √      已申报： ●");
			jLabel7 = new JLabel();
			jLabel7.setForeground(java.awt.Color.blue);
			jLabel7.setSize(new Dimension(318, 22));
			jLabel7.setLocation(new Point(400, 19));
			jLabel7.setText("9，归并关系及电子帐册申报状态表示");
			jLabel6 = new JLabel();
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel6.setSize(new Dimension(282, 18));
			jLabel6.setLocation(new Point(40, 149));
			jLabel6.setText("4，电子帐册是否分批申报海关");
			jLabel5 = new JLabel();
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setSize(new Dimension(279, 23));
			jLabel5.setLocation(new Point(40, 200));
			jLabel5.setText("5，报关单是否查询后再新增");
			jLabel4 = new JLabel();
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setSize(new Dimension(280, 21));
			jLabel4.setLocation(new Point(40, 95));
			jLabel4.setText("3，电子帐册归并关系是否分批申报海关");
			jLabel3 = new JLabel();
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setSize(new Dimension(282, 21));
			jLabel3.setLocation(new Point(40, 64));
			jLabel3.setText("2，报关单商品信息数量取整");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(184, 33, 36, 18));
			jLabel2.setText("损耗");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(77, 33, 34, 18));
			jLabel1.setText("单耗");
			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setText("1，电子帐册BOM转换小数位控制(归并关系BOM导入)");
			jLabel.setLocation(new Point(40, 2));
			jLabel.setSize(new Dimension(306, 21));
			jLabel.setForeground(java.awt.Color.blue);
			jPanel.setForeground(new java.awt.Color(51, 0, 255));
			jPanel.add(jLabel, null);
			// jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJCheckBox1(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJCheckBox2(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getJCheckBox3(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(jLabel9, null);
			jPanel.add(getJCheckBox4(), null);
			jPanel.add(getJCheckBox5(), null);
			jPanel.add(getJCheckBox6(), null);
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(getJCheckBox7(), null);
			jPanel.add(jLabel121, null);
			jPanel.add(getTfBGDLoadDir(), null);
			jPanel.add(getJButton32(), null);
			jPanel.add(jLabel51, null);
			jPanel.add(getJCheckBox21(), null);
			jPanel.add(jLabel17, null);
			jPanel.add(getTfReportDecimalLength(), null);
			jPanel.add(jLabel71, null);
			jPanel.add(jLabel91, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel14, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel511, null);
			jPanel.add(getJCheckBox211(), null);
			jPanel.add(jLabel131, null);
			jPanel.add(getCbMergerMaxVer(), null);
			jPanel.add(getJPanel81(), null);
			jPanel.add(jLabel132, null);
			jPanel.add(getCbAmountControl(), null);
			jPanel.add(jLabel31, null);
			jPanel.add(jLabel7112, null);
			jPanel.add(getTfReportDecimalLengthOfQinDan(), null);
			jPanel.add(jLabel71112, null);
			jPanel.add(getTfReportDecimalLengthOfQinDanM(), null);
			jPanel.add(jLabel711111, null);
			jPanel.add(getTfReportDecimalLengthOfQinDanA(), null);
			jPanel.add(getTfUnitWaste(), null);
			jPanel.add(getTfWaste(), null);
			jPanel.add(jLabel16, null);
			jPanel.add(getCbImpTotalMoney(), null);
			
			lbControlPrice = new JLabel("21，申请单转清单商品限制值");
			lbControlPrice.setVisible(false);
			lbControlPrice.setForeground(Color.BLUE);
			lbControlPrice.setBounds(40, 453, 158, 15);
			jPanel.add(lbControlPrice);
			
			tfControlPrice = new JTextField();
			tfControlPrice.setEditable(false);
			tfControlPrice.setVisible(false);
			tfControlPrice.setBounds(204, 450, 66, 21);
			jPanel.add(tfControlPrice);
			tfControlPrice.setColumns(10);
			jPanel.add(getLblNewLabel());
			jPanel.add(getCbCavIsInclude());
			jPanel.add(getLabel());
			jPanel.add(getCbResult());
			

			jPanel.add(getLabel_1());
			jPanel.add(getCheckBoxprice());
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	// private JTextField getJTextField() {
	// if ( == null) {
	// tfUnitWaste = new JTextField();
	// tfUnitWaste.setBounds(123, 47, 64, 22);
	// }
	// return tfUnitWaste;
	// }
	/**
	 * 
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					String isSend = manualDecleareAction.getBpara(new Request(
							CommonVars.getCurrUser()), BcusParameter.EmsSEND);
					if (isSend != null) {
						if ((isSend.equals("1") ? true : false) != FmBcusParameterSet.this.cbMergerBatchDeclare
								.isSelected()) {
							if (JOptionPane.showConfirmDialog(
									FmBcusParameterSet.this,
									"您确定要修改第 3 点中的'分批申报'吗?", "提示",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
								FmBcusParameterSet.this.cbMergerBatchDeclare
										.setSelected(isSend.equals("1") ? true
												: false);
							}
						}
					}

					String isSendSign = manualDecleareAction.getBpara(
							new Request(CommonVars.getCurrUser()),
							BcusParameter.EmsSEND_Sign);
					if (isSendSign != null) {
						if ((isSendSign.equals("1") ? true : false) != FmBcusParameterSet.this.cbMergerEditModifyMark
								.isSelected()) {
							if (JOptionPane.showConfirmDialog(
									FmBcusParameterSet.this,
									"您确定要修改第 3 点中的'是否同时修改申报标记'吗?", "提示",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
								FmBcusParameterSet.this.cbMergerEditModifyMark
										.setSelected(isSendSign.equals("1") ? true
												: false);
							}
						}
					}

					String isEmsH2kSend = manualDecleareAction.getBpara(
							new Request(CommonVars.getCurrUser()),
							BcusParameter.EmsEdiH2kSend);
					if (isEmsH2kSend != null) {
						if ((isEmsH2kSend.equals("1") ? true : false) != FmBcusParameterSet.this.cbEmsBatchDeclare
								.isSelected()) {
							if (JOptionPane.showConfirmDialog(
									FmBcusParameterSet.this,
									"您确定要修改第 4 点中的'分批申报'吗?", "提示",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
								FmBcusParameterSet.this.cbEmsBatchDeclare
										.setSelected(isEmsH2kSend.equals("1") ? true
												: false);
							}
						}
					}

					String isEmsH2kSendSign = manualDecleareAction.getBpara(
							new Request(CommonVars.getCurrUser()),
							BcusParameter.EmsEdiH2kSend_Sign);
					if (isEmsH2kSendSign != null) {
						if ((isSendSign.equals("1") ? true : false) != FmBcusParameterSet.this.cbEmsEditModifyMark
								.isSelected()) {
							if (JOptionPane.showConfirmDialog(
									FmBcusParameterSet.this,
									"您确定要修改第 4 点中的'是否同时修改申报标记'吗?", "提示",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
								FmBcusParameterSet.this.cbEmsEditModifyMark
										.setSelected(isEmsH2kSendSign
												.equals("1") ? true : false);
							}
						}
					}

					manualDecleareAction.saveBpara(new Request(CommonVars
							.getCurrUser()),
							BcusParameter.EMSBOM_UNITWEAR_DIGITS, tfUnitWaste
									.getSelectedItem().toString());
					manualDecleareAction.saveBpara(new Request(CommonVars
							.getCurrUser()), BcusParameter.EMSBOM_WEAR_DIGITS,
							tfWaste.getSelectedItem().toString());
					manualDecleareAction.saveBpara(new Request(CommonVars
							.getCurrUser()), BcusParameter.INCREMENT_RATE,
							tfRatio.getText());

					if (cbAutoRound.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.CUSTOMS_FORINT,
								"1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.CUSTOMS_FORINT,
								"0");
					}

					if (cbMergerBatchDeclare.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.EmsSEND, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.EmsSEND, "0");
					}

					if (cbSearch.isSelected()) {
						manualDecleareAction
								.saveBpara(
										new Request(CommonVars.getCurrUser()),
										BcusParameter.BGD_ISQUERY, "1");
					} else {
						manualDecleareAction
								.saveBpara(
										new Request(CommonVars.getCurrUser()),
										BcusParameter.BGD_ISQUERY, "0");
					}

					if (cbEmsBatchDeclare.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.EmsEdiH2kSend,
								"1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.EmsEdiH2kSend,
								"0");
					}

					if (cbDefault.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.DefaultPrice,
								"1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.DefaultPrice,
								"0");
					}

					if (cbMergerEditModifyMark.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.EmsSEND_Sign,
								"1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()), BcusParameter.EmsSEND_Sign,
								"0");
					}

					if (cbEmsEditModifyMark.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.EmsEdiH2kSend_Sign, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.EmsEdiH2kSend_Sign, "0");
					}

					if (cbPrint.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.PrintCustomWithVersion, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.PrintCustomWithVersion, "0");
					}

					if (cbMateriSoure.isSelected()) {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.EMS_BOM_MaterilSoure, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.EMS_BOM_MaterilSoure, "0");
					}
					
					if (CommonUtils.notEmpty(tfControlPrice.getText())) {
						if(RegexUtil.checkFloat(tfControlPrice.getText())) {
							manualDecleareAction.saveBpara(new Request(CommonVars
									.getCurrUser()),
									BcusParameter.BILL_CONTROL_PRICE, tfControlPrice.getText());
						} else {
							
						}
					} 

					if (cbbPriceByMonth.isSelected()) {
						// 为“0”代表是按每月平均单价计算
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.Ems_CancelCus_Price, "0");
					} else if (cbbPriceByTotal.isSelected()) {
						// 为“1”代表是按总平均单价计算
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.Ems_CancelCus_Price, "1");
					}
					if (cbMergerMaxVer.isSelected()) { // 16
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.MERGER_BOM_MaxVer, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.MERGER_BOM_MaxVer, "0");
					}
					if (cbAmountControl.isSelected()) { // 18
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.Ems_Amount_Control, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.Ems_Amount_Control, "0");
					}
					if (cbImpTotalMoney.isSelected()) { // 19
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.ImgImpTotalMoney, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.ImgImpTotalMoney, "0");
					}
					if (tfReportDecimalLength.getValue() == null
							|| Double
									.valueOf(
											tfReportDecimalLength.getValue()
													.toString()).intValue() == 0) {
						JOptionPane
								.showMessageDialog(FmBcusParameterSet.this,
										"请输入要保留的小数位位数", "提示",
										JOptionPane.ERROR_MESSAGE);
						return;

					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.ReportDecimalLength, String
										.valueOf(Double.valueOf(
												tfReportDecimalLength
														.getValue().toString())
												.intValue()));
					}
					manualDecleareAction.saveBpara(new Request(CommonVars
							.getCurrUser()), BcusParameter.LOAD_QP_BGD_DIR,
							tfBGDLoadDir.getText().trim());

					// 申请单转清单，小数位设置
					saveApllyToBillValues();

					if (tfPriceDecimal.getText() == null
							|| tfPriceDecimal.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(FmBcusParameterSet.this,
								"第14项，请输入要保留的小数位位数!", "提示！",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						try {
							new Integer(tfPriceDecimal.getText().trim());
							manualDecleareAction.saveBpara(new Request(
									CommonVars.getCurrUser()),
									BcusParameter.EMS_BOM_PRICE, tfPriceDecimal
											.getText().trim());
						} catch (Exception ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(
									FmBcusParameterSet.this, "第14项，请输入整数!",
									"提示！", JOptionPane.ERROR_MESSAGE);
							return;
						}

					}
					
					if (cbCavIsInclude.isSelected()) { // 32
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.CAV_REAL_NUM_INCLUDE_NONBONDE, "1");
					} else {
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.CAV_REAL_NUM_INCLUDE_NONBONDE, "0");
					}

					if(cbResult.isSelected()){
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.RESULT, "1");
					}else{
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.RESULT, "0");
					}
					if(checkBoxprcie.isSelected()){
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.Premium_Price, "1");
					}else{
						manualDecleareAction.saveBpara(new Request(CommonVars
								.getCurrUser()),
								BcusParameter.Premium_Price, "0");
					}
					if (obj == null) {
						obj = new CompanyOther();
					}
					if (cbAutoMergerBefor.isSelected()) {
						obj.setIsAutoNewMergerBefore(new Boolean(true));
					} else {
						obj.setIsAutoNewMergerBefore(new Boolean(false));
					}
					// 保存到系统参数
					systemAction.saveCompanyOther(new Request(CommonVars
							.getCurrUser()), obj);
					getCompanyOther();
					// 保存到服务变量中
					CompanyOther other = systemAction
							.saveCommonUtilsOther(new Request(CommonVars
									.getCurrUser()));
					// 保存到变量中
					CommonVars.setOther(other);

					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 保存申请单转清单，小数位设置 清单小数位控制
	 */
	private void saveApllyToBillValues() {

		if (checkApllyToBillValues()) {
			// manualDecleareAction
			// .saveBpara(
			// new Request(CommonVars.getCurrUser()),
			// BcusParameter.APLLY_TO_BILL_AMOUNT,
			// getTfAmountDecimalLength().getText() == null ?
			// BcusParameter.APLLY_TO_BILL_DEFAULT
			// : getTfAmountDecimalLength().getText());
			// manualDecleareAction
			// .saveBpara(
			// new Request(CommonVars.getCurrUser()),
			// BcusParameter.APLLY_TO_BILL_PRICE,
			// getTfPriceDecimalLength().getText() == null ?
			// BcusParameter.APLLY_TO_BILL_DEFAULT
			// : getTfPriceDecimalLength().getText());
			// manualDecleareAction
			// .saveBpara(
			// new Request(CommonVars.getCurrUser()),
			// BcusParameter.APLLY_TO_BILL_TOTALPRICE,
			// getTfTotalDecimalLength().getText() == null ?
			// BcusParameter.APLLY_TO_BILL_DEFAULT
			// : getTfTotalDecimalLength().getText());
			// 清单
			manualDecleareAction
					.saveBpara(
							new Request(CommonVars.getCurrUser()),
							BcusParameter.BILL_TO_CONTROL_AMOUNT,
							getTfReportDecimalLengthOfQinDanA().getText() == null ? BcusParameter.APLLY_TO_BILL_DEFAULT
									: getTfReportDecimalLengthOfQinDanA()
											.getText());
			manualDecleareAction
					.saveBpara(
							new Request(CommonVars.getCurrUser()),
							BcusParameter.BILL_TO_CONTROL_PRICE,
							getTfReportDecimalLengthOfQinDan().getText() == null ? BcusParameter.APLLY_TO_BILL_DEFAULT
									: getTfReportDecimalLengthOfQinDan()
											.getText());
			manualDecleareAction
					.saveBpara(
							new Request(CommonVars.getCurrUser()),
							BcusParameter.BILL_TO_CONTROL_TOTALPRICE,
							getTfReportDecimalLengthOfQinDanM().getText() == null ? BcusParameter.APLLY_TO_BILL_DEFAULT
									: getTfReportDecimalLengthOfQinDanM()
											.getText());
		}
	}

	/**
	 * 检查单价，总价，数量是否为有效的数字
	 * 
	 * @return
	 */
	private boolean checkApllyToBillValues() {
		Integer price = null;
		Integer totalPrice = null;
		Integer amount = null;
		Integer billPrice = null;
		Integer billTotalPrice = null;
		Integer billAmount = null;
		// try {
		// price = Integer
		// .parseInt(getTfPriceDecimalLength().getText() == null ? "3"
		// : getTfPriceDecimalLength().getText());
		//
		// } catch (Exception e) {
		// JOptionPane
		// .showMessageDialog(FmBcusParameterSet.this,
		// "第15项设置未能保存，单价不是有效的数字！", "提示！",
		// JOptionPane.WARNING_MESSAGE);
		// getTfPriceDecimalLength().setText(
		// BcusParameter.APLLY_TO_BILL_DEFAULT);
		// return false;
		// }
		//
		// try {
		// totalPrice = Integer.parseInt(getTfTotalDecimalLength()
		// .getText() == null ? "3" : getTfTotalDecimalLength()
		// .getText());
		// } catch (Exception e) {
		// JOptionPane
		// .showMessageDialog(FmBcusParameterSet.this,
		// "第15项设置未能保存，金额不是有效的数字！", "提示！",
		// JOptionPane.WARNING_MESSAGE);
		// getTfTotalDecimalLength().setText(
		// BcusParameter.APLLY_TO_BILL_DEFAULT);
		// return false;
		// }
		// try {
		// amount = Integer
		// .parseInt(getTfAmountDecimalLength().getText() == null ? "3"
		// : getTfAmountDecimalLength().getText());
		// } catch (Exception e) {
		// JOptionPane
		// .showMessageDialog(FmBcusParameterSet.this,
		// "第15项设置未能保存，数量不是有效的数字！", "提示！",
		// JOptionPane.WARNING_MESSAGE);
		// getTfAmountDecimalLength().setText(
		// BcusParameter.APLLY_TO_BILL_DEFAULT);
		// return false;
		// }
		//
		// if (5 < price || price < 0) {
		// JOptionPane.showMessageDialog(FmBcusParameterSet.this,
		// "第15项设置未能保存，单价不是有效的数字,必需在海关归定:0--5之间！", "提示！",
		// JOptionPane.WARNING_MESSAGE);
		// getTfPriceDecimalLength().setText(
		// BcusParameter.APLLY_TO_BILL_DEFAULT);
		// return false;
		// }
		// if (5 < totalPrice || totalPrice < 0) {
		// JOptionPane.showMessageDialog(FmBcusParameterSet.this,
		// "第15项设置未能保存，金额价不是有效的数字,必需在海关归定:0--5之间！", "提示！",
		// JOptionPane.WARNING_MESSAGE);
		// getTfTotalDecimalLength().setText(
		// BcusParameter.APLLY_TO_BILL_DEFAULT);
		// return false;
		// }
		// if (5 < amount || amount < 0) {
		// JOptionPane.showMessageDialog(FmBcusParameterSet.this,
		// "第15项设置未能保存，数量不是有效的数字,必需在海关归定:0--5之间！", "提示！",
		// JOptionPane.WARNING_MESSAGE);
		// getTfAmountDecimalLength().setText(
		// BcusParameter.APLLY_TO_BILL_DEFAULT);
		// return false;
		// }
		// 清单
		try {
			billAmount = Integer.parseInt(getTfReportDecimalLengthOfQinDanA()
					.getText() == null ? "3"
					: getTfReportDecimalLengthOfQinDanA().getText());

		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(FmBcusParameterSet.this,
							"第16项设置未能保存，数量不是有效的数字！", "提示！",
							JOptionPane.WARNING_MESSAGE);
			getTfReportDecimalLengthOfQinDanA().setText(
					BcusParameter.APLLY_TO_BILL_DEFAULT);
			return false;
		}
		try {
			billPrice = Integer.parseInt(getTfReportDecimalLengthOfQinDan()
					.getText() == null ? "3"
					: getTfReportDecimalLengthOfQinDan().getText());

		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(FmBcusParameterSet.this,
							"第16项设置未能保存，单价不是有效的数字！", "提示！",
							JOptionPane.WARNING_MESSAGE);
			getTfReportDecimalLengthOfQinDan().setText(
					BcusParameter.APLLY_TO_BILL_DEFAULT);
			return false;
		}
		try {
			billTotalPrice = Integer
					.parseInt(getTfReportDecimalLengthOfQinDanM().getText() == null ? "3"
							: getTfReportDecimalLengthOfQinDanM().getText());

		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(FmBcusParameterSet.this,
							"第16项设置未能保存，金额不是有效的数字！", "提示！",
							JOptionPane.WARNING_MESSAGE);
			getTfReportDecimalLengthOfQinDanM().setText(
					BcusParameter.APLLY_TO_BILL_DEFAULT);
			return false;
		}

		if (5 < billPrice || billPrice < 0) {
			JOptionPane.showMessageDialog(FmBcusParameterSet.this,
					"第16项设置未能保存，单价不是有效的数字,必需在海关归定:0--5之间！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			getTfReportDecimalLengthOfQinDan().setText(
					BcusParameter.APLLY_TO_BILL_DEFAULT);
			return false;
		}
		if (5 < billTotalPrice || billTotalPrice < 0) {
			JOptionPane.showMessageDialog(FmBcusParameterSet.this,
					"第16项设置未能保存，金额价不是有效的数字,必需在海关归定:0--5之间！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			getTfReportDecimalLengthOfQinDanM().setText(
					BcusParameter.APLLY_TO_BILL_DEFAULT);
			return false;
		}
		if (5 < billAmount || billAmount < 0) {
			JOptionPane.showMessageDialog(FmBcusParameterSet.this,
					"第16项设置未能保存，数量不是有效的数字,必需在海关归定:0--5之间！", "提示！",
					JOptionPane.WARNING_MESSAGE);
			getTfReportDecimalLengthOfQinDanA().setText(
					BcusParameter.APLLY_TO_BILL_DEFAULT);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton2() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 各栏位状态设置
	 */
	private void setState() {
		boolean editAble = !(dataState == DataState.BROWSE);
		
		tfRatio.setEditable(editAble);
		tfPriceDecimal.setEditable(editAble);
		this.tfUnitWaste.setEnabled(editAble);
		this.tfWaste.setEnabled(editAble);
		this.cbAutoRound.setEnabled(editAble);
		this.cbMergerBatchDeclare.setEnabled(editAble);
		this.cbSearch.setEnabled(editAble);
		this.cbEmsBatchDeclare.setEnabled(editAble);
		this.cbDefault.setEnabled(editAble);
		this.cbMergerEditModifyMark
				.setEnabled(editAble);
		this.cbEmsEditModifyMark.setEnabled(editAble);
		cbAutoMergerBefor.setEnabled(editAble);
		this.tfBGDLoadDir.setEditable(editAble);
		this.btnPathSet.setEnabled(editAble);
		this.btnEdit.setEnabled(!editAble);
		this.btnSave.setEnabled(editAble);
		this.cbPrint.setEnabled(editAble);
		this.cbMateriSoure.setEnabled(editAble);
		this.cbMergerMaxVer.setEnabled(editAble);
		this.cbAmountControl.setEnabled(editAble);
		this.tfReportDecimalLength
				.setEditable(editAble);
		this.cbbPriceByMonth.setEnabled(editAble);
		this.cbbPriceByTotal.setEnabled(editAble);
		this.cbImpTotalMoney.setEnabled(editAble);
		// getTfPriceDecimalLength().setEditable(editAble);
		// getTfTotalDecimalLength().setEditable(editAble);
		// getTfAmountDecimalLength().setEditable(dataState !=
		// DataState.BROWSE);
		getTfReportDecimalLengthOfQinDan().setEditable(
				editAble);
		getTfReportDecimalLengthOfQinDanM().setEditable(
				editAble);
		getTfReportDecimalLengthOfQinDanA().setEditable(
				editAble);
		this.cbCavIsInclude.setEnabled(editAble);
		this.tfControlPrice.setEditable(editAble);
		this.cbResult.setEnabled(editAble);
		this.checkBoxprcie.setEnabled(editAble);
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel18 = new JLabel();
			javax.swing.JLabel jLabel19 = new JLabel();
			jPanel1.setLayout(new BorderLayout());
			jLabel17.setText("");
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("电子帐册参数设置");
			jLabel18.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel1.add(jLabel18, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBcusParameterSet.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (cbAutoRound == null) {
			cbAutoRound = new JCheckBox();
			cbAutoRound.setBounds(new Rectangle(204, 64, 106, 21));
			cbAutoRound.setText("是否自动取整");
		}
		return cbAutoRound;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox1() {
		if (cbMergerBatchDeclare == null) {
			cbMergerBatchDeclare = new JCheckBox();
			cbMergerBatchDeclare.setBounds(new Rectangle(77, 122, 81, 21));
			cbMergerBatchDeclare.setText("分批申报");
		}
		return cbMergerBatchDeclare;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox2() {
		if (cbSearch == null) {
			cbSearch = new JCheckBox();
			cbSearch.setBounds(new Rectangle(204, 201, 95, 21));
			cbSearch.setText("是否查询");
		}
		return cbSearch;
	}

	/**
	 * This method initializes jCheckBox3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox3() {
		if (cbEmsBatchDeclare == null) {
			cbEmsBatchDeclare = new JCheckBox();
			cbEmsBatchDeclare
					.setBounds(new Rectangle(77, 173, 83, 21));
			cbEmsBatchDeclare.setText("分批申报");
		}
		return cbEmsBatchDeclare;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBounds(new Rectangle(382, 4, 8, 539));
			jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.lightGray, 5));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setVgap(0);
			jPanel4 = new JPanel();
			jPanel4.setLayout(flowLayout);
			jPanel4
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel4.add(getJButton2(), null);
			jPanel4.add(getJButton(), null);
			jPanel4.add(getJButton1(), null);
			jPanel4.add(getJButton31(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jCheckBox4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox4() {
		if (cbDefault == null) {
			cbDefault = new JCheckBox();
			cbDefault.setText("是否默认");
			cbDefault.setLocation(new Point(418, 85));
			cbDefault.setSize(new Dimension(79, 21));
		}
		return cbDefault;
	}

	/**
	 * This method initializes jCheckBox5
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox5() {
		if (cbMergerEditModifyMark == null) {
			cbMergerEditModifyMark = new JCheckBox();
			cbMergerEditModifyMark.setBounds(new Rectangle(157, 122, 209, 21));
			cbMergerEditModifyMark.setText("是否同时修改申报标记");
		}
		return cbMergerEditModifyMark;
	}

	/**
	 * This method initializes jCheckBox6
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox6() {
		if (cbEmsEditModifyMark == null) {
			cbEmsEditModifyMark = new JCheckBox();
			cbEmsEditModifyMark.setBounds(new Rectangle(157, 173, 158, 21));
			cbEmsEditModifyMark.setText("是否同时修改申报标记");
		}
		return cbEmsEditModifyMark;
	}

	/**
	 * This method initializes jButton31
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton31() {
		if (btnOtherSet == null) {
			btnOtherSet = new JButton();
			btnOtherSet.setText("其他参数设置");
			btnOtherSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBcusPathSet dg = new DgBcusPathSet();
					dg.setVisible(true);
				}
			});
		}
		return btnOtherSet;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (tfRatio == null) {
			tfRatio = new JTextField();
			tfRatio.setSize(new Dimension(84, 21));
			tfRatio.setLocation(new Point(483, 125));
		}
		return tfRatio;
	}

	/**
	 * This method initializes jCheckBox7
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox7() {
		if (cbAutoMergerBefor == null) {
			cbAutoMergerBefor = new JCheckBox();
			cbAutoMergerBefor.setText("自动带出");
			cbAutoMergerBefor.setLocation(new Point(417, 167));
			cbAutoMergerBefor.setSize(new Dimension(77, 19));
		}
		return cbAutoMergerBefor;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBGDLoadDir() {
		if (tfBGDLoadDir == null) {
			tfBGDLoadDir = new JTextField();
			tfBGDLoadDir.setSize(new Dimension(169, 24));
			tfBGDLoadDir.setLocation(new Point(417, 212));
		}
		return tfBGDLoadDir;
	}

	/**
	 * This method initializes jButton32
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton32() {
		if (btnPathSet == null) {
			btnPathSet = new JButton();
			btnPathSet.setText("...");
			btnPathSet.setLocation(new Point(586, 212));
			btnPathSet.setSize(new Dimension(24, 23));
			btnPathSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String dir = "";
					if (tfBGDLoadDir.getText() != null
							&& !tfBGDLoadDir.getText().equals("")) {
						dir = tfBGDLoadDir.getText();
					} else {
						dir = "./";
					}
					JFileChooser fileChooser = new JFileChooser(dir);
					fileChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int state = fileChooser.showDialog(FmBcusParameterSet.this,
							"选择路径");
					if (state == JFileChooser.APPROVE_OPTION) {
						fileChooser.getSelectedFile();
						File f = fileChooser.getSelectedFile();
						tfBGDLoadDir.setText(f.getPath());
					}
				}
			});
		}
		return btnPathSet;
	}

	/**
	 * 获得参数设置表值
	 */
	private void getCompanyOther() {
		List list = systemAction.findCompanyOtherBcus(new Request(CommonVars
				.getCurrUser()));
		if (list != null && list.size() > 0) {
			obj = (CompanyOther) list.get(0);
		}
	}

	/**
	 * This method initializes jCheckBox21
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox21() {
		if (cbPrint == null) {
			cbPrint = new JCheckBox();
			cbPrint.setBounds(new Rectangle(77, 261, 113, 21));
			cbPrint.setText("是否打印");
		}
		return cbPrint;
	}

	/**
	 * This method initializes tfReportDecimalLength
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfReportDecimalLength() {
		if (tfReportDecimalLength == null) {
			tfReportDecimalLength = new JFormattedTextField();
			tfReportDecimalLength.setSize(new Dimension(46, 21));
			tfReportDecimalLength.setLocation(new Point(481, 258));
		}
		return tfReportDecimalLength;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (tfPriceDecimal == null) {
			tfPriceDecimal = new JTextField();
			tfPriceDecimal.setText("2");
			tfPriceDecimal.setLocation(new Point(504, 419));
			tfPriceDecimal.setSize(new Dimension(38, 23));
		}
		return tfPriceDecimal;
	}

	/**
	 * This method initializes jCheckBox211
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox211() {
		if (cbMateriSoure == null) {
			cbMateriSoure = new JCheckBox();
			cbMateriSoure.setBounds(new Rectangle(77, 312, 77, 26));
			cbMateriSoure.setText("是与否");
		}
		return cbMateriSoure;
	}

	/**
	 * This method initializes cbMateriSoure1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMergerMaxVer() {
		if (cbMergerMaxVer == null) {
			cbMergerMaxVer = new JCheckBox();
			cbMergerMaxVer.setText("是与否");
			cbMergerMaxVer.setLocation(new Point(452, 374));
			cbMergerMaxVer.setSize(new Dimension(64, 23));
		}
		return cbMergerMaxVer;
	}

	/**
	 * This method initializes jPanel81
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel81() {
		if (jPanel81 == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "8\uff0c\u5e10\u518c\u62a5\u6838\u51fa\u53e3\u8017\u6599\u5355\u4ef7\u6765\u6e90", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), Color.blue);
			
			jPanel81 = new JPanel();
			jPanel81.setLayout(null);
			jPanel81.setSize(new Dimension(320, 54));
			jPanel81.setLocation(new Point(40, 350));
			jPanel81.setBorder(titledBorder);
			jPanel81.add(getCbbPriceByMonth(), null);
			jPanel81.add(getCbbPriceByTotal(), null);
		}
		return jPanel81;
	}

	/**
	 * This method initializes cbbPriceByMonth
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbPriceByMonth() {
		if (cbbPriceByMonth == null) {
			cbbPriceByMonth = new JRadioButton();
			cbbPriceByMonth.setText("报核周期每月平均单价");
			cbbPriceByMonth.setBounds(new Rectangle(6, 27, 159, 21));
		}
		return cbbPriceByMonth;
	}

	/**
	 * This method initializes cbbPriceByTotal
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbPriceByTotal() {
		if (cbbPriceByTotal == null) {
			cbbPriceByTotal = new JRadioButton();
			cbbPriceByTotal.setSelected(true);
			cbbPriceByTotal.setBounds(new Rectangle(168, 26, 146, 21));
			cbbPriceByTotal.setText("报核周期总平均单价");
		}
		return cbbPriceByTotal;
	}

	/**
	 * This method initializes cbAmountControl
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAmountControl() {
		if (cbAmountControl == null) {
			cbAmountControl = new JCheckBox();
			cbAmountControl.setBounds(new Rectangle(447, 466, 64, 19));
			cbAmountControl.setText("\u662f\u4e0e\u5426");
		}
		return cbAmountControl;
	}

	/**
	 * 报关清单小数位单价
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReportDecimalLengthOfQinDan() {
		if (tfReportDecimalLengthOfQinDan == null) {
			tfReportDecimalLengthOfQinDan = new JTextField();
			tfReportDecimalLengthOfQinDan.setBounds(new Rectangle(456, 321, 38,
					20));
			tfReportDecimalLengthOfQinDan.setText("3");
		}
		return tfReportDecimalLengthOfQinDan;
	}

	/**
	 * 报关清单小数位——金额
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReportDecimalLengthOfQinDanM() {
		if (tfReportDecimalLengthOfQinDanM == null) {
			tfReportDecimalLengthOfQinDanM = new JTextField();
			tfReportDecimalLengthOfQinDanM.setBounds(new Rectangle(557, 323,
					38, 20));
			tfReportDecimalLengthOfQinDanM.setText("3");
		}
		return tfReportDecimalLengthOfQinDanM;
	}

	/**
	 * 报关清单小数位——数量
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfReportDecimalLengthOfQinDanA() {
		if (tfReportDecimalLengthOfQinDanA == null) {
			tfReportDecimalLengthOfQinDanA = new JTextField();
			tfReportDecimalLengthOfQinDanA.setBounds(new Rectangle(651, 322,
					38, 20));
			tfReportDecimalLengthOfQinDanA.setText("3");
		}
		return tfReportDecimalLengthOfQinDanA;
	}

	/**
	 * This method initializes tfUnitWaste
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTfUnitWaste() {
		if (tfUnitWaste == null) {
			Integer[] num = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			tfUnitWaste = new JComboBox(num);
			tfUnitWaste.setBounds(new Rectangle(109, 31, 74, 23));
		}
		return tfUnitWaste;
	}

	/**
	 * This method initializes tfWaste
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTfWaste() {
		if (tfWaste == null) {
			Integer[] num = { 0, 1, 2, 3, 4, 5 };
			tfWaste = new JComboBox(num);
			tfWaste.setBounds(new Rectangle(215, 31, 67, 23));
		}
		return tfWaste;
	}

	/**
	 * This method initializes cbImpTotalMoney	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbImpTotalMoney() {
		if (cbImpTotalMoney == null) {
			cbImpTotalMoney = new JCheckBox();
			cbImpTotalMoney.setText("是与否");
			cbImpTotalMoney.setLocation(new Point(447, 500));
			cbImpTotalMoney.setSize(new Dimension(83, 21));
		}
		return cbImpTotalMoney;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("20，核销时实际剩余数量包含非保税数量");
			lblNewLabel.setForeground(Color.BLUE);
			lblNewLabel.setBounds(40, 420, 230, 18);
		}
		return lblNewLabel;
	}
	private JCheckBox getCbCavIsInclude() {
		if (cbCavIsInclude == null) {
			cbCavIsInclude = new JCheckBox("是否包含");
			cbCavIsInclude.setBounds(273, 418, 73, 23);
			cbCavIsInclude.setEnabled(false);
		}
		return cbCavIsInclude;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("22，正式报核,处理回执回写结余数、结余金额");
			label.setForeground(Color.BLUE);
			label.setBounds(40, 487, 282, 18);
		}
		return label;
	}
	private JCheckBox getCbResult() {
		if (cbResult == null) {
			cbResult = new JCheckBox("是与否");
			cbResult.setEnabled(false);
			cbResult.setBounds(297, 487, 73, 23);
		}
		return cbResult;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("23，正式报核表头进出口总金额包含报关单头运保费");
			label_1.setForeground(Color.BLUE);
			label_1.setBounds(40, 525, 282, 18);
		}
		return label_1;
	}
	
	private JCheckBox getCheckBoxprice() {
		if (checkBoxprcie == null) {
			checkBoxprcie = new JCheckBox("是与否");
			checkBoxprcie.setEnabled(false);
			checkBoxprcie.setBounds(317, 525, 64, 23);
		}
		return checkBoxprcie;
	}
} // @jve:decl-index=0:visual-constraint="13,3"
