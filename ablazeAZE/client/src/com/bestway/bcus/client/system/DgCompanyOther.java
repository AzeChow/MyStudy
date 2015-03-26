/*
 * Created on 2005-8-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.manualdeclare.DgBcusPathSet;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.HttpMsgTransType;
import com.bestway.common.constant.ParameterType;
import com.bestway.pis.common.HttpClientInvoker;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author wp // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgCompanyOther extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnEdit = null;

	private JButton btnSave = null;

	private JButton jButton2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JComboBox cbbCommonCurr = null;

	private JLabel jLabel1 = null;

	private JTextField jTextField = null;

	private JLabel jLabel2 = null;

	private JTextField jTextField1 = null;

	private JLabel jLabel3 = null;

	private JTextField jTextField2 = null;

	private JLabel jLabel4 = null;

	private JComboBox jComboBox1 = null;

	private JTextField jTextField3 = null;

	private JLabel jLabel6 = null;

	private JComboBox jComboBox2 = null;

	private JTextField jTextField4 = null;

	private JComboBox jComboBox3 = null;

	private JLabel jLabel8 = null;

	private JComboBox jComboBox4 = null;

	private JLabel jLabel9 = null;

	private JComboBox jComboBox5 = null;

	private JLabel jLabel10 = null;

	private JComboBox jComboBox6 = null;

	private JLabel jLabel11 = null;

	private JComboBox jComboBox7 = null;

	private JComboBox jComboBox8 = null;

	private JComboBox jComboBox9 = null;

	private JLabel jLabel13 = null;

	private CompanyOther companyOther = null; // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private SystemAction systemAction = null;

	private JLabel jLabel46 = null;

	private JComboBox cbbCommonUnit = null;

	private JFormattedTextField jFormattedTextField = null;

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private JCheckBox jCheckBox = null;

	private JLabel jLabel50 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel52 = null;

	private JLabel jLabel53 = null;

	private JComboBox jComboBox42 = null;

	private JComboBox jComboBox43 = null;

	private JComboBox jComboBox44 = null;

	private JComboBox jComboBox45 = null;

	private JLabel jLabel54 = null;

	private JLabel jLabel55 = null;

	// private JLabel jLabel58 = null;

	private JPanel pnOther = null;

	private JCheckBox cbIsCustomAmountOut = null;

	private JCheckBox cbChauffeur = null;

	private JLabel jLabel59 = null;

	private JTextField tfPreCustomsDeclarationCodes = null;

	private ManualDeclareAction manualDecleareAction = null;
	private ButtonGroup bgbus = null; // @jve:decl-index=0:
	private JButton btnTestFpt = null;

	/**
	 * This is the default constructor
	 */
	public DgCompanyOther() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();

	}

	/**
	 * This method initializes jPanel81
	 * 
	 * @return javax.swing.JPanel
	 */

	private ButtonGroup getBgbcs() {
		if (bgbus == null) {
			bgbus = new ButtonGroup();
			bgbus.add(getCbbTenInnerMerger());
			bgbus.add(getCbbComplex());
		}
		return bgbus;
	}

	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(6);
			decimalFormat1.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}

	public Double strToDouble(String value) { // 转换strToDouble 填充数据
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

	public String doubleToStr(Double value) { // 转换doubleToStr 取数据
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

	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	private void setState() {
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));

		cbbCommonCurr.setEnabled(dataState != DataState.BROWSE);
		jTextField.setEditable(!(dataState == DataState.BROWSE));
		jTextField1.setEditable(!(dataState == DataState.BROWSE));
		jTextField2.setEditable(!(dataState == DataState.BROWSE));
		jComboBox1.setEnabled(!(dataState == DataState.BROWSE));
		jTextField3.setEditable(!(dataState == DataState.BROWSE));
		jComboBox2.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox3.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox4.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox5.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox6.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox7.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox8.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox9.setEnabled(!(dataState == DataState.BROWSE));
		jTextField4.setEditable(!(dataState == DataState.BROWSE));
		cbbCommonUnit.setEnabled(!(dataState == DataState.BROWSE));
		tfSendCustoms.setEditable(!(dataState == DataState.BROWSE));
		tfInvoiceAddress.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox10.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox11.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox12.setEnabled(!(dataState == DataState.BROWSE));
		jComboBoxSQcustomPriceNum.setEnabled(!(dataState == DataState.BROWSE));
		jComboBoxsSQcustomAmountNum
				.setEnabled(!(dataState == DataState.BROWSE));
		jComboBoxSQcustomTotalNum.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox42.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox43.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox44.setEnabled(!(dataState == DataState.BROWSE));
		jComboBox45.setEnabled(!(dataState == DataState.BROWSE));
		cbChauffeur.setEnabled(dataState != DataState.BROWSE);
		jFormattedTextField.setEditable(!(dataState == DataState.BROWSE));
		this.tfPreCustomsDeclarationCodes
				.setEditable(!(dataState == DataState.BROWSE));
		this.tfSendCustoms.setEditable(!(dataState == DataState.BROWSE));
		this.tfTransportTool.setEditable(!(dataState == DataState.BROWSE));
		this.jCheckBox.setEnabled(!(dataState == DataState.BROWSE));
		this.cbCalcWeight.setEnabled(!(dataState == DataState.BROWSE));
		this.cbAutoWeightRound.setEnabled(cbCalcWeight.isSelected()
				&& !(dataState == DataState.BROWSE));
		this.jCheckBox5.setEnabled(!(dataState == DataState.BROWSE));
		this.jCheckBox6.setEnabled(!(dataState == DataState.BROWSE));
		this.jCheckBox61.setEnabled(!(dataState == DataState.BROWSE));
		this.jCheckBoxSumMoney.setEnabled(!(dataState == DataState.BROWSE));
		this.jCheckBox7.setEnabled(!(dataState == DataState.BROWSE));
		this.cbIsSpecification.setEnabled(!(dataState == DataState.BROWSE));
		this.cbIsExportPackinglistOrInvoice
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbIsTransadd.setEnabled(!(dataState == DataState.BROWSE));

		this.jCheckBox8.setEnabled(!(dataState == DataState.BROWSE));
		this.jCheckBox2.setEnabled(!(dataState == DataState.BROWSE));
		this.jCheckBox4.setEnabled(!(dataState == DataState.BROWSE));
		this.cbPrintAll.setEnabled(!(dataState == DataState.BROWSE));
		this.tfPageSize.setEditable(dataState != DataState.BROWSE);
		this.tfBomSize.setEditable(dataState != DataState.BROWSE);
		this.cbbMouth.setEditable(dataState != DataState.BROWSE);
		jCheckBox3.setEnabled(!(dataState == DataState.BROWSE));
		// jLabel58.setEnabled(!(dataState == DataState.BROWSE));
		cbIsCustomAmountOut.setEnabled(!(dataState == DataState.BROWSE));

		cbbCurr.setEnabled(!(dataState == DataState.BROWSE));
		cbbUser.setEnabled(!(dataState == DataState.BROWSE));
		cbbCountry.setEnabled(!(dataState == DataState.BROWSE));
		cbbLevyMode.setEnabled(!(dataState == DataState.BROWSE));
		this.cbIsCustomAutoAttachedBill
				.setEnabled(!(dataState == DataState.BROWSE));
		this.cbIsAllowBGDDetailExceed20
				.setEnabled(dataState != DataState.BROWSE);
		this.cbIsSaveCustomsEnvelopBillNo
				.setEnabled(dataState != DataState.BROWSE);
		cbIssendMail.setEnabled(!(dataState == DataState.BROWSE));
		tfSendMail.setEditable(!(dataState == DataState.BROWSE));
		cbbTenInnerMerger.setEnabled(dataState != DataState.BROWSE);
		cbbComplex.setEnabled(dataState != DataState.BROWSE);
		// rbFtpPASV.setEnabled(dataState != DataState.BROWSE);
		// rbFtpPORT.setEnabled(dataState != DataState.BROWSE);
		// tfFtpTimeOut.setEditable(dataState != DataState.BROWSE);
		tfHttpAddress.setEditable(dataState != DataState.BROWSE);
		tfPisAddress.setEditable(dataState != DataState.BROWSE);
		tfPisPort.setEditable(dataState != DataState.BROWSE);
		tfHttpTcsUserName.setEditable(dataState != DataState.BROWSE);
		tfHttpTcsPsw.setEditable(dataState != DataState.BROWSE);
		tfHttpFptUserName.setEditable(dataState != DataState.BROWSE);
		tfHttpFptPsw.setEditable(dataState != DataState.BROWSE);
		tfHttpProxyAddress.setEditable(dataState != DataState.BROWSE);
		tfHttpProxyUser.setEditable(dataState != DataState.BROWSE);
		tfHttpProxyPort.setEditable(dataState != DataState.BROWSE);
		tfHttpProxyPsw.setEditable(dataState != DataState.BROWSE);
	}

	public void setVisible(boolean b) {
		if (b) {
			initUI();
			if (companyOther != null) {
				fillWondow();
			}
			showData();
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(635, 691);
		this.setContentPane(getJContentPane());
		this.setTitle("其他参数设置");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

			}
		});

	}

	private void initUI() {
		List list = null;
		list = CustomBaseList.getInstance().getCurrs();
		intoComboBox(list, cbbCommonCurr);
		intoComboBox(list, cbbCurr);
		list = CustomBaseList.getInstance().getCustoms();
		intoComboBox(list, jComboBox1);
		intoComboBox(list, jComboBox2);
		intoComboBox(list, jComboBox42);
		intoComboBox(list, jComboBox43);
		intoComboBox(list, jComboBox44);
		intoComboBox(list, jComboBox45);
		list = CustomBaseList.getInstance().getBargainType();
		intoComboBox(list, jComboBox3);
		list = CustomBaseList.getInstance().getTrades();
		intoComboBox(list, jComboBox4);
		list = CustomBaseList.getInstance().getLevyKind();
		intoComboBox(list, jComboBox5);

		list = systemAction.findCompanies();
		this.cbbCommonUnit.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbCommonUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCommonUnit, "code", "name", 270);

		// 合同性质
		jComboBox6.removeAllItems();
		jComboBox6.addItem(new ItemProperty(
				ContractKind.FOREIGN_CAPITAL_SELL_PRODUCT, "外资内销产品"));
		jComboBox6.addItem(new ItemProperty(ContractKind.COME_MATERIEL_PROCESS,
				"来料加工"));
		jComboBox6.addItem(new ItemProperty(
				ContractKind.IMPORT_MATERIEL_PROCESS, "进料加工"));
		jComboBox6.addItem(new ItemProperty(
				ContractKind.FOREIGN_CAPITAL_MACHINE, "外资设备"));
		jComboBox6.addItem(new ItemProperty(ContractKind.BONDEN_WAREHOUSE,
				"保税仓"));
		jComboBox6.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox6);

		list = CustomBaseList.getInstance().getCountrys();
		intoComboBox(list, jComboBox7);
		intoComboBox(list, jComboBox8);
		intoComboBox(list, cbbCountry);
		list = CustomBaseList.getInstance().getTransacs();
		intoComboBox(list, jComboBox9);
		list = CustomBaseList.getInstance().getUses();
		intoComboBox(list, cbbUser);
		list = CustomBaseList.getInstance().getLevymode();
		intoComboBox(list, cbbLevyMode);

		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfPageSize, 0);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfBomSize, 0);
		// CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField5,
		// 0);
		// CustomFormattedTextFieldUtils.setFormatterFactory(this.jTextField6,
		// 0);
		getBgbcs();
		// 初始化发运港
		/**
		 * 2015-1-19当报关单（打印）默认为空时，打印的出口发票发运港为进出口岸
		 */
		// tfSendCustoms.setEnabled(false);
	}

	private void fillWondow() {

		// rbFtpPASV.setSelected(companyOther.getIsFtpPASV()==null?true:companyOther.getIsFtpPASV());
		// rbFtpPORT.setSelected(!rbFtpPASV.isSelected());
		// tfFtpTimeOut.setText(companyOther.getFtpTimeOut()==null?"":companyOther.getFtpTimeOut().toString());
		tfHttpAddress.setText(companyOther.getHttpAddress());
		tfPisAddress.setText(companyOther.getPisAddress());
		tfPisPort.setText(companyOther.getPisPort() == null ? "" : companyOther
				.getPisPort().toString());

		tfHttpTcsUserName.setText(companyOther.getHttpTcsUserName());
		tfHttpTcsPsw.setText(companyOther.getHttpTcsPwd());
		tfHttpFptUserName.setText(companyOther.getHttpFptUserName());
		tfHttpFptPsw.setText(companyOther.getHttpFptPwd());
		tfHttpProxyAddress.setText(companyOther.getProxyAddress());
		tfHttpProxyUser.setText(companyOther.getProxyUserName());
		tfHttpProxyPort.setText(null == companyOther.getProxyPort() ? null
				: companyOther.getProxyPort().toString());
		tfHttpProxyPsw.setText(companyOther.getProxyPwd());
		cbbCommonUnit.setSelectedItem(companyOther.getDeclarationCompany());
		jComboBox10.setSelectedIndex(companyOther.getCustomPriceNum());
		jComboBox11.setSelectedIndex(companyOther.getCustomTotalNum());
		jComboBox12.setSelectedIndex(companyOther.getCustomAmountNum());
		jComboBoxSQcustomTotalNum.setSelectedIndex(companyOther
				.getCustomTotalNumSq());
		jComboBoxsSQcustomAmountNum.setSelectedIndex(companyOther
				.getCustomAmountNumSq());
		jComboBoxSQcustomPriceNum.setSelectedIndex(companyOther
				.getCustomPriceNumSq());
		jComboBox1.setSelectedItem(companyOther.getCustomNo());
		jTextField3.setText(companyOther.getSancEmsNo());
		jComboBox2.setSelectedItem(companyOther.getIePort1());
		jComboBox42.setSelectedItem(companyOther.getIePort2());
		jComboBox43.setSelectedItem(companyOther.getIePort3());
		jComboBox44.setSelectedItem(companyOther.getIePort4());
		jComboBox45.setSelectedItem(companyOther.getIePort5());
		jComboBox3.setSelectedItem(companyOther.getBargainType());
		jComboBox4.setSelectedItem(companyOther.getTrade());
		jComboBox5.setSelectedItem(companyOther.getLevyKind());
		jFormattedTextField.setText(doubleToStr(companyOther.getWeightPara()));

		// 合同性质
		if (companyOther.getBargainKind() != null) {
			jComboBox6.setSelectedIndex(ItemProperty.getIndexByCode(
					companyOther.getBargainKind(), jComboBox6));
		} else {
			jComboBox6.setSelectedIndex(-1);
		}

		jComboBox7.setSelectedItem(companyOther.getCountry());
		jComboBox8.setSelectedItem(companyOther.getTradeCountry());
		jComboBox9.setSelectedItem(companyOther.getTransac());

		cbbCommonCurr.setSelectedItem(companyOther.getCommonCurr());
		jTextField.setText(companyOther.getCancelDay());
		jTextField1.setText(companyOther.getExitDay());
		jTextField2.setText(companyOther.getSimpName());

		jTextField4.setText(companyOther.getNote());
		/**
		 * 申请单转报关单是否自动带出客户
		 */
		if (this.companyOther.getIsAutoAmount() != null
				&& this.companyOther.getIsAutoAmount()
						.equals(new Boolean(true))) {
			this.jCheckBox.setSelected(true);
		} else {
			this.jCheckBox.setSelected(false);
		}

		/**
		 * 报关单总件数，净重毛重是否由商品信息自动计算出来
		 */
		if (this.companyOther.getIsAutoWeight() != null
				&& this.companyOther.getIsAutoWeight()
						.equals(new Boolean(true))) {
			this.cbCalcWeight.setSelected(true);
		} else {
			this.cbCalcWeight.setSelected(false);
		}

		if (this.companyOther.getIsAutoWeightRound() != null
				&& this.companyOther.getIsAutoWeightRound()) {
			this.cbAutoWeightRound.setSelected(true);
		} else {
			this.cbAutoWeightRound.setSelected(false);
		}

		if (this.companyOther.getIsAutoGetDjNo() != null
				&& this.companyOther.getIsAutoGetDjNo().equals(
						new Boolean(true))) {
			this.jCheckBox5.setSelected(true);
		} else {
			this.jCheckBox5.setSelected(false);
		}
		if (this.companyOther.getIsAutoshowThousandthsign() != null
				&& this.companyOther.getIsAutoshowThousandthsign().equals(
						new Boolean(true))) {
			this.jCheckBox6.setSelected(true);
		} else {
			this.jCheckBox6.setSelected(false);
		}
		if (this.companyOther.getIsFilter() != null
				&& this.companyOther.getIsFilter().equals(new Boolean(true))) {
			this.jCheckBox61.setSelected(true);
		} else {
			this.jCheckBox61.setSelected(false);
		}
		if (this.companyOther.getIsSumMoney() != null
				&& this.companyOther.getIsSumMoney().equals(new Boolean(true))) {
			this.jCheckBoxSumMoney.setSelected(true);
		} else {
			this.jCheckBoxSumMoney.setSelected(false);
		}

		if (this.companyOther.getIsCludeReturn() != null
				&& this.companyOther.getIsCludeReturn().equals(
						new Boolean(true))) {
			this.jCheckBox7.setSelected(true);
		} else {
			this.jCheckBox7.setSelected(false);
		}

		if (this.companyOther.getIsSpecification() != null
				&& this.companyOther.getIsSpecification().equals(
						new Boolean(true))) {
			this.cbIsSpecification.setSelected(true);
		} else {
			this.cbIsSpecification.setSelected(false);
		}

		if (this.companyOther.getIsExportPackinglistOrInvoice() != null
				&& this.companyOther.getIsExportPackinglistOrInvoice().equals(
						new Boolean(true))) {
			this.cbIsExportPackinglistOrInvoice.setSelected(true);
		} else {
			this.cbIsExportPackinglistOrInvoice.setSelected(false);
		}

		if (this.companyOther.getIsTransitadd() != null
				&& this.companyOther.getIsTransitadd()
						.equals(new Boolean(true))) {
			this.cbIsTransadd.setSelected(true);
		} else {
			this.cbIsTransadd.setSelected(false);
		}
		if (this.companyOther.getIsCheckWeight() != null
				&& this.companyOther.getIsCheckWeight().equals(
						new Boolean(true))) {
			this.jCheckBox8.setSelected(true);
		} else {
			this.jCheckBox8.setSelected(false);
		}

		if (this.companyOther.getIsReConveyance() != null
				&& this.companyOther.getIsReConveyance().equals(
						new Boolean(true))) {
			this.cbChauffeur.setSelected(true);
		} else {
			this.cbChauffeur.setSelected(false);
		}
		/**
		 * 报关单自动带证件代码
		 */
		if (this.companyOther.getIsCustomAutoAttachedBill() != null
				&& this.companyOther.getIsCustomAutoAttachedBill().equals(
						new Boolean(true))) {
			this.cbIsCustomAutoAttachedBill.setSelected(true);
		} else {
			this.cbIsCustomAutoAttachedBill.setSelected(false);
		}
		if (this.companyOther.getIsSendMail() != null
				&& this.companyOther.getIsSendMail().equals(true)) {
			this.cbIssendMail.setSelected(true);
		} else {
			this.cbIssendMail.setSelected(false);
		}

		if (this.companyOther.getSendCustoms() != null) {
			this.tfSendCustoms.setText(this.companyOther.getSendCustoms());
		} else {
			this.tfSendCustoms.setText("东莞");
		}
		if (this.companyOther.getInvoiceAddress() != null) {
			this.tfInvoiceAddress
					.setText(this.companyOther.getInvoiceAddress());
		} else {
			this.tfInvoiceAddress.setText("太平");
		}

		this.tfSendMail.setText(this.companyOther.getSendMail());
		this.jCheckBox4
				.setSelected(this.companyOther.getIsManualPreCode() == null ? false
						: this.companyOther.getIsManualPreCode());
		this.cbPrintAll
				.setSelected(this.companyOther.getPrintAll() == null ? false
						: this.companyOther.getPrintAll());

		this.tfPreCustomsDeclarationCodes.setText(this.companyOther
				.getPreCustomsDeclarationCode());
		this.tfTransportTool
				.setText(this.companyOther.getTransportTool() == null ? ""
						: this.companyOther.getTransportTool().toString());
		this.cbbMouth.setSelectedItem(this.companyOther.getMonth());
		this.tfPageSize.setValue(this.companyOther.getPageSize());
		this.tfBomSize.setValue(this.companyOther.getBomChangeBit());
		this.cbIsCustomAmountOut.setSelected(CommonVars.getIsCustomAmountOut());

		// this.jTextField5.setValue(this.companyOther.getContractBit());
		// this.jTextField6.setValue(this.companyOther.getReportBit());

		// 报关单保存是否同时检查
		String customsSavaCheck = manualDecleareAction.getBpara(new Request(
				CommonVars.getCurrUser()), BcusParameter.CUSTOMS_SAVA_CHECK);
		if (customsSavaCheck != null && "1".equals(customsSavaCheck)) {
			this.jCheckBox2.setSelected(true);
		} else {
			this.jCheckBox2.setSelected(false);
		}

		// 境外运输工具是否自动带出境内运输工具编号
		String jwjn = manualDecleareAction.getBpara(
				new Request(CommonVars.getCurrUser()),
				BcusParameter.CUSTOMS_JW_JN);
		if (jwjn != null && "1".equals(jwjn)) {
			this.jCheckBox3.setSelected(true);
		} else {
			this.jCheckBox3.setSelected(false);
		}

		if (this.companyOther.getIsAllowBGDDetailExceed20() != null
				&& this.companyOther.getIsAllowBGDDetailExceed20().equals(true)) {
			this.cbIsAllowBGDDetailExceed20.setSelected(true);
		} else {
			this.cbIsAllowBGDDetailExceed20.setSelected(false);
		}
		// 进出口报关单转厂业务是否强制打印关封号
		this.cbIsSaveCustomsEnvelopBillNo.setSelected(this.companyOther
				.getIsSaveCustomsEnvelopBillNo() == null ? false
				: this.companyOther.getIsSaveCustomsEnvelopBillNo());

		//
		// 特殊报关单来源方式
		//
		List list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.SpecialCustoms);
		if (list != null && list.size() > 0) {
			ParameterSet para = (ParameterSet) list.get(0);
			if (para.getNameValues().equals("0")) {
				this.cbbTenInnerMerger.setSelected(true);
			} else if (para.getNameValues().equals("1")) {
				this.cbbComplex.setSelected(true);
			}
		} else {
			this.cbbTenInnerMerger.setSelected(false);
			this.cbbComplex.setSelected(false);
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
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
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
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(60, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
					if (!"".equals(tfPreCustomsDeclarationCodes.getText()
							.trim())) {
						if (tfPreCustomsDeclarationCodes.getText().length() != 6) {
							JOptionPane.showMessageDialog(DgCompanyOther.this,
									"你输入的预录入号不是6位", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						try {
							Integer.parseInt(tfPreCustomsDeclarationCodes
									.getText().trim());
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(DgCompanyOther.this,
									"你输入的预录入号不是数字形式", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					if (!"".equals(tfTransportTool.getText().trim())) {
						try {
							Integer i = Integer.parseInt(tfTransportTool
									.getText().trim());
							if (i < 0 || i > 25) {
								JOptionPane.showMessageDialog(
										DgCompanyOther.this, "运输工具额外位数只能取0到25",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(DgCompanyOther.this,
									"你输入的运输工具额外位数不是整数形式", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					int oldCode = -1;
					if (companyOther != null
							&& companyOther.getPreCustomsDeclarationCode() != null
							&& !"".equals(companyOther
									.getPreCustomsDeclarationCode())) {
						oldCode = Integer.parseInt(companyOther
								.getPreCustomsDeclarationCode());
					}
					try {
						Integer.parseInt(tfPreCustomsDeclarationCodes.getText()
								.trim());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgCompanyOther.this,
								"你输入的预录入号不是数字形式", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int newCode = Integer.parseInt(tfPreCustomsDeclarationCodes
							.getText().trim());
					if (newCode < oldCode) {
						if (JOptionPane.showConfirmDialog(DgCompanyOther.this,
								"你输入的预录入号小于系统最大的预录入号，可能造成报关单预录入号的重复，你确定保存吗?",
								"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
					}
					fillData();

					companyOther = systemAction.saveCompanyOther(new Request(
							CommonVars.getCurrUser()), companyOther);
					CompanyOther other = systemAction
							.saveCommonUtilsOther(new Request(CommonVars
									.getCurrUser()));// 保存到服务变量中

					CommonVars.setOther(other); // 保存到变量中
					saveIsCustomAmountOut();
					// 报关单保存是否同时检查
					if (jCheckBox2.isSelected()) {
						manualDecleareAction.saveBpara(
								new Request(CommonVars.getCurrUser()),
								BcusParameter.CUSTOMS_SAVA_CHECK, "1");
					} else {
						manualDecleareAction.saveBpara(
								new Request(CommonVars.getCurrUser()),
								BcusParameter.CUSTOMS_SAVA_CHECK, "0");
					}

					if (jCheckBox3.isSelected()) {
						manualDecleareAction.saveBpara(
								new Request(CommonVars.getCurrUser()),
								BcusParameter.CUSTOMS_JW_JN, "1");
					} else {
						manualDecleareAction.saveBpara(
								new Request(CommonVars.getCurrUser()),
								BcusParameter.CUSTOMS_JW_JN, "0");
					}

					dataState = DataState.BROWSE;
					setState();
					if (!companyOther.equals(null)) {
						JOptionPane
								.showMessageDialog(DgCompanyOther.this,
										"参数保存完毕", "提示",
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			});
		}
		return btnSave;
	}

	private void saveIsCustomAmountOut() {
		List list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.CUSTOM_AMOUNT_OUT);
		ParameterSet para = null;
		if (list != null && !list.isEmpty()) {
			para = (ParameterSet) list.get(0);
		} else {
			para = new ParameterSet();
		}
		para.setNameValues(Boolean.valueOf(cbIsCustomAmountOut.isSelected())
				.toString());
		para.setType(ParameterType.CUSTOM_AMOUNT_OUT);
		para.setCompany(CommonVars.getCurrUser().getCompany());
		systemAction.saveValues(new Request(CommonVars.getCurrUser()), para);
		CommonVars.setIsCustomAmountOut(cbIsCustomAmountOut.isSelected());// type=17
	}

	private void fillData() {
		companyOther.setCommonCurr((Curr) cbbCommonCurr.getSelectedItem());
		companyOther.setCustomNo((Customs) jComboBox1.getSelectedItem());
		// companyOther.setWeightPara(strToDouble(jFormattedTextField.getText()));
		companyOther.setCancelDay(jTextField.getText());
		companyOther.setExitDay(jTextField1.getText());
		companyOther.setSimpName(jTextField2.getText());
		// companyOther.setSancEmsNo(jTextField3.getText());
		companyOther.setIePort1((Customs) jComboBox2.getSelectedItem());
		companyOther.setIePort2((Customs) jComboBox42.getSelectedItem());
		companyOther.setIePort3((Customs) jComboBox43.getSelectedItem());
		companyOther.setIePort4((Customs) jComboBox44.getSelectedItem());
		companyOther.setIePort5((Customs) jComboBox45.getSelectedItem());
		companyOther.setDeclarationCompany((Company) cbbCommonUnit
				.getSelectedItem());
		companyOther.setCustomPriceNum((Integer) this.jComboBox10
				.getSelectedItem());
		companyOther.setCustomTotalNum((Integer) this.jComboBox11
				.getSelectedItem());
		companyOther.setCustomAmountNum((Integer) this.jComboBox12
				.getSelectedItem());
		companyOther
				.setCustomPriceNumSq((Integer) this.jComboBoxSQcustomPriceNum
						.getSelectedItem());
		companyOther
				.setCustomAmountNumSq((Integer) this.jComboBoxsSQcustomAmountNum
						.getSelectedItem());
		companyOther
				.setCustomTotalNumSq((Integer) this.jComboBoxSQcustomTotalNum
						.getSelectedItem());
		// companyOther.setBargainType((BargainType)
		// jComboBox3.getSelectedItem());
		companyOther.setLevyKind((LevyKind) jComboBox5.getSelectedItem());
		companyOther.setTrade((Trade) jComboBox4.getSelectedItem());
		// obj.setPreCustomsDeclarationCode(tfPreCustomsDeclarationCode.getText()
		// .trim());
		companyOther.setPreCustomsDeclarationCode(tfPreCustomsDeclarationCodes
				.getText().trim());
		companyOther.setSendCustoms(tfSendCustoms.getText());
		companyOther.setInvoiceAddress(tfInvoiceAddress.getText());
		if (tfTransportTool.getText() != null
				&& !tfTransportTool.getText().equals(""))
			companyOther.setTransportTool(Integer.parseInt(tfTransportTool
					.getText() == null ? "0" : tfTransportTool.getText()));

		// if (jComboBox6.getSelectedItem() != null) {
		// companyOther.setBargainKind(((ItemProperty) jComboBox6
		// .getSelectedItem()).getCode());
		// } else {
		// companyOther.setBargainKind(null);
		// }

		companyOther.setCountry((Country) jComboBox7.getSelectedItem());
		companyOther.setTradeCountry((Country) jComboBox8.getSelectedItem());
		companyOther.setTransac((Transac) jComboBox9.getSelectedItem());
		companyOther.setNote(jTextField4.getText());

		if (this.jCheckBox.isSelected()) {
			companyOther.setIsAutoAmount(new Boolean(true));
		} else {
			companyOther.setIsAutoAmount(new Boolean(false));
		}

		if (this.cbCalcWeight.isSelected()) {
			companyOther.setIsAutoWeight(new Boolean(true));
		} else {
			companyOther.setIsAutoWeight(new Boolean(false));
		}

		companyOther.setIsAutoWeightRound(this.cbAutoWeightRound.isSelected());

		if (this.jCheckBox5.isSelected()) {
			companyOther.setIsAutoGetDjNo(new Boolean(true));
		} else {
			companyOther.setIsAutoGetDjNo(new Boolean(false));
		}
		if (this.jCheckBox8.isSelected()) {
			companyOther.setIsCheckWeight(new Boolean(true));
		} else {
			companyOther.setIsCheckWeight(new Boolean(false));
		}
		if (this.jCheckBox6.isSelected()) {
			companyOther.setIsAutoshowThousandthsign(new Boolean(true));
		} else {
			companyOther.setIsAutoshowThousandthsign(new Boolean(false));
		}
		if (this.jCheckBox61.isSelected()) {
			companyOther.setIsFilter(new Boolean(true));
		} else {
			companyOther.setIsFilter(new Boolean(false));
		}
		if (this.jCheckBoxSumMoney.isSelected()) {
			companyOther.setIsSumMoney(new Boolean(true));
		} else {
			companyOther.setIsSumMoney(new Boolean(false));
		}
		if (this.jCheckBox7.isSelected()) {
			companyOther.setIsCludeReturn(new Boolean(true));
		} else {
			companyOther.setIsCludeReturn(new Boolean(false));
		}

		if (this.cbIsSpecification.isSelected()) {
			companyOther.setIsSpecification(new Boolean(true));
		} else {
			companyOther.setIsSpecification(new Boolean(false));
		}

		if (this.cbIsExportPackinglistOrInvoice.isSelected()) {
			companyOther.setIsExportPackinglistOrInvoice(new Boolean(true));
		} else {
			companyOther.setIsExportPackinglistOrInvoice(new Boolean(false));
		}

		if (this.cbIsTransadd.isSelected()) {
			companyOther.setIsTransitadd(new Boolean(true));
		} else {
			companyOther.setIsTransitadd(new Boolean(false));
		}
		if (this.cbChauffeur.isSelected()) {
			companyOther.setIsReConveyance(new Boolean(true));
		} else {
			companyOther.setIsReConveyance(new Boolean(false));
		}

		if (this.cbIsCustomAutoAttachedBill.isSelected()) {
			companyOther.setIsCustomAutoAttachedBill(new Boolean(true));
		} else {
			companyOther.setIsCustomAutoAttachedBill(new Boolean(false));
		}
		if (this.cbIssendMail.isSelected()) {
			companyOther.setIsSendMail(true);
		} else {
			companyOther.setIsSendMail(false);
		}
		companyOther.setSendMail(tfSendMail.getText().trim());
		// if (this.cbChauffeur.isSelected()) {
		// companyOther.setIsReConveyance(new Boolean(true));
		// } else {
		// companyOther.setIsReConveyance(new Boolean(false));
		// }
		companyOther.setIsManualPreCode(this.jCheckBox4.isSelected());
		companyOther.setPrintAll(this.cbPrintAll.isSelected());
		companyOther.setPageSize(tfPageSize.getValue() == null ? 0 : Integer
				.valueOf(tfPageSize.getValue().toString()));
		companyOther.setBomChangeBit(tfBomSize.getValue() == null ? 0 : Integer
				.valueOf(tfBomSize.getValue().toString()));
		companyOther.setMonth(this.cbbMouth.getSelectedItem().toString());
		companyOther
				.setIsAllowBGDDetailExceed20(this.cbIsAllowBGDDetailExceed20
						.isSelected());
		companyOther
				.setIsSaveCustomsEnvelopBillNo(this.cbIsSaveCustomsEnvelopBillNo
						.isSelected());
		// companyOther.setContractBit(jTextField5.getValue() == null ? 0 :
		// Integer
		// .valueOf(jTextField5.getValue().toString()));
		// companyOther.setReportBit(jTextField6.getValue() == null ? 0 :
		// Integer
		// .valueOf(jTextField6.getValue().toString()));
		// if(RegexUtil.checkInteger(tfFtpTimeOut.getText())){
		// companyOther.setFtpTimeOut(Integer.parseInt(tfFtpTimeOut.getText()));
		// }
		// companyOther.setIsFtpPASV(rbFtpPASV.isSelected());

		companyOther.setHttpAddress(tfHttpAddress.getText());
		companyOther.setPisAddress(tfPisAddress.getText());
		companyOther.setPisPort((tfPisPort.getText() == null || ""
				.equals(tfPisPort.getText().trim())) ? null : Integer
				.parseInt(tfPisPort.getText()));
		companyOther.setHttpTcsUserName(tfHttpTcsUserName.getText());
		companyOther.setHttpTcsPwd(tfHttpTcsPsw.getText());
		companyOther.setHttpFptUserName(tfHttpFptUserName.getText());
		companyOther.setHttpFptPwd(tfHttpFptPsw.getText());
		companyOther.setProxyAddress(tfHttpProxyAddress.getText());
		companyOther.setProxyUserName(tfHttpProxyUser.getText());
		String prot = tfHttpProxyPort.getText().trim();
		companyOther.setProxyPort(Integer.parseInt(null == prot
				|| "".equals(prot) ? "0" : prot));
		companyOther.setProxyPwd(tfHttpProxyPsw.getText());

	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("合同参数", null, getJPanel1(), null);
			jTabbedPane.addTab("清单参数", null, getPnApplyCustomsBill(), null);
			jTabbedPane.addTab("数据导入接口参数", null, getJPanel2(), null);
			jTabbedPane.addTab("报关单(申请单)参数", null, getPnOther(), null);
			jTabbedPane.addTab("基本参数", null, getJPanel(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {

							} else if (jTabbedPane.getSelectedIndex() == 1) {

							} else {
							}
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel20 = new JLabel();
			jLabel20.setBounds(31, 179, 378, 21);
			jLabel20.setText("工厂BOM、报关常用工厂BOM中的单耗、损耗、单项用量小数位保留：");
			jLabel18 = new JLabel();
			jLabel18.setBounds(31, 142, 167, 21);
			jLabel18.setText("出口收汇核销单--预计收款日期:");
			jLabel16 = new JLabel();
			jLabel16.setBounds(358, 108, 12, 18);
			jLabel16.setText("项");
			jLabel15 = new JLabel();
			jLabel15.setBounds(31, 108, 228, 21);
			jLabel15.setText("系统在分页显示数据时，每页显示的项数是");
			jLabel171 = new JLabel();
			jLabel171.setBounds(280, 69, 84, 23);
			jLabel171.setText("（全系统适用）");
			jLabel17 = new JLabel();
			jLabel17.setBounds(275, 29, 89, 22);
			jLabel17.setText("（全系统适用）");
			jLabel55 = new JLabel();
			jLabel55.setBounds(399, 16, 123, 21);
			jLabel55.setVisible(false);
			jLabel55.setText("（提示料件退换倒计天数）");
			jLabel54 = new JLabel();
			jLabel54.setBounds(415, 163, 158, 21);
			jLabel54.setVisible(false);
			jLabel54.setText("（合同有效期倒计天数，全系统适用）");
			jLabel46 = new JLabel();
			jLabel46.setBounds(31, 69, 75, 21);
			jLabel3 = new JLabel();
			jLabel3.setBounds(422, 126, 77, 21);
			jLabel3.setVisible(false);
			jLabel2 = new JLabel();
			jLabel2.setBounds(420, 89, 85, 21);
			jLabel2.setVisible(false);
			jLabel1 = new JLabel();
			jLabel1.setBounds(407, 57, 85, 23);
			jLabel1.setVisible(false);
			jLabel = new JLabel();
			jLabel.setBounds(31, 26, 61, 21);
			jPanel = new JPanel();
			jLabel.setText("常用币制");
			jLabel1.setText("核销提示天数");
			jLabel2.setText("退换提示天数");
			jLabel3.setText("企业简称");
			jLabel46.setText("申报单位");
			jPanel.setLayout(null);
			jPanel.add(jLabel);
			jPanel.add(getCbbCommonCurr());
			jPanel.add(jLabel1);
			jPanel.add(getJTextField());
			jPanel.add(jLabel2);
			jPanel.add(getJTextField1());
			jPanel.add(jLabel3);
			jPanel.add(getJTextField2());
			jPanel.add(jLabel46);
			jPanel.add(getCbbCommonUnit());
			jPanel.add(getJCheckBox());
			jPanel.add(jLabel54);
			jPanel.add(jLabel55);
			jPanel.add(jLabel17);
			jPanel.add(jLabel171);
			jPanel.add(getJCheckBox5());
			jPanel.add(getJCheckBox6());
			jPanel.add(jLabel15);
			jPanel.add(getTfPageSize());
			jPanel.add(jLabel16);
			jPanel.add(jLabel18);
			jPanel.add(getCbbMouth());
			jPanel.add(jLabel20);
			jPanel.add(getTfBomSize());
			jPanel.add(getJCheckBox61());
			jPanel.add(getJCheckBoxSumMoney());
			jPanel.add(getPanelHttp());
			jPanel.add(getPanelProxy());
			jPanel.add(getPanel());

		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel53 = new JLabel();
			jLabel53.setBounds(new Rectangle(18, 139, 67, 22));
			jLabel53.setText("进出口岸5");
			jLabel52 = new JLabel();
			jLabel52.setBounds(new Rectangle(301, 99, 66, 21));
			jLabel52.setText("进出口岸4");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(18, 97, 70, 23));
			jLabel51.setText("进出口岸3");
			jLabel50 = new JLabel();
			jLabel50.setBounds(new Rectangle(301, 63, 66, 21));
			jLabel50.setText("进出口岸2");
			jLabel13 = new JLabel();
			// jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			// jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			// jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel4.setText("申报关区号");
			jLabel4.setBounds(18, 23, 75, 23);
			// jLabel5.setBounds(310, 24, 45, 21);
			// jLabel5.setText("批文号");
			jLabel6.setBounds(18, 62, 70, 22);
			jLabel6.setText("进出口岸1");
			// jLabel7.setBounds(310, 119, 57, 24);
			// jLabel7.setText("合同类型");
			jLabel8.setBounds(301, 23, 67, 23);
			jLabel8.setText("贸易方式");
			jLabel9.setBounds(301, 138, 66, 23);
			jLabel9.setText("征免性质");
			jLabel10.setBounds(301, 173, 69, 25);
			jLabel10.setText("贸易国别");
			jLabel11.setBounds(18, 175, 67, 23);
			jLabel11.setText("消费国");
			// jLabel12.setBounds(25, 189, 57, 24);
			// jLabel12.setText("合同性质");
			jLabel13.setBounds(18, 215, 70, 24);
			jLabel13.setText("成交方式");
			// jLabel14.setBounds(25, 303, 59, 25);
			// jLabel14.setText("合同备注");
			// jLabel47.setBounds(25, 262, 59, 24);
			// jLabel47.setText("重量参数");
			jPanel1.add(jLabel4, null);
			jPanel1.add(getJComboBox1(), null);
			// jPanel1.add(jLabel5, null);
			jPanel1.add(getJTextField3(), null);
			jPanel1.add(jLabel6, null);
			jPanel1.add(getJComboBox2(), null);
			// jPanel1.add(jLabel7, null);
			jPanel1.add(getJTextField4(), null);
			jPanel1.add(getJComboBox3(), null);
			jPanel1.add(jLabel8, null);
			jPanel1.add(getJComboBox4(), null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(getJComboBox5(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getJComboBox6(), null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(getJComboBox7(), null);
			// jPanel1.add(jLabel12, null);
			jPanel1.add(getJComboBox8(), null);
			jPanel1.add(getJComboBox9(), null);
			jPanel1.add(jLabel13, null);
			// jPanel1.add(jLabel14, null);
			// jPanel1.add(jLabel47, null);
			jPanel1.add(getJFormattedTextField(), null);
			jPanel1.add(jLabel50, null);
			jPanel1.add(jLabel51, null);
			jPanel1.add(jLabel52, null);
			jPanel1.add(jLabel53, null);
			jPanel1.add(getJComboBox42(), null);
			jPanel1.add(getJComboBox43(), null);
			jPanel1.add(getJComboBox44(), null);
			jPanel1.add(getJComboBox45(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbCommonCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommonCurr() {
		if (cbbCommonCurr == null) {
			cbbCommonCurr = new JComboBox();
			cbbCommonCurr.setBounds(115, 25, 157, 25);
		}
		return cbbCommonCurr;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(115, 74, 142, 22);
			jTextField.setVisible(false);
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
			jTextField1.setBounds(115, 116, 142, 22);
			jTextField1.setVisible(false);
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(102, 131, 85, 22);
			jTextField2.setVisible(false);
		}
		return jTextField2;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(117, 24, 138, 22);
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(382, 24, 138, 22);
			jTextField3.setVisible(false);
		}
		return jTextField3;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(117, 62, 138, 22);
		}
		return jComboBox2;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(107, 304, 413, 24);
			jTextField4.setVisible(false);
		}
		return jTextField4;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setBounds(382, 120, 138, 22);
			jComboBox3.setVisible(false);
		}
		return jComboBox3;
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox();
			jComboBox4.setBounds(389, 24, 138, 22);
		}
		return jComboBox4;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox();
			jComboBox5.setBounds(389, 139, 138, 22);
		}
		return jComboBox5;
	}

	/**
	 * This method initializes jComboBox6
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox6() {
		if (jComboBox6 == null) {
			jComboBox6 = new JComboBox();
			jComboBox6.setBounds(107, 189, 138, 22);
			jComboBox6.setVisible(false);
		}
		return jComboBox6;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(117, 176, 138, 22);
		}
		return jComboBox7;
	}

	/**
	 * This method initializes jComboBox8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox8() {
		if (jComboBox8 == null) {
			jComboBox8 = new JComboBox();
			jComboBox8.setBounds(389, 176, 138, 22);
		}
		return jComboBox8;
	}

	/**
	 * This method initializes jComboBox9
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox9() {
		if (jComboBox9 == null) {
			jComboBox9 = new JComboBox();
			jComboBox9.setBounds(117, 216, 138, 22);
		}
		return jComboBox9;
	}

	/**
	 * @return Returns the obj.
	 */
	public CompanyOther getCompanyOther() {
		return companyOther;
	}

	/**
	 * @param obj
	 *            The obj to set.
	 */
	public void setCompanyOther(CompanyOther obj) {
		this.companyOther = obj;
	}

	// 填充动态ComboBox
	public void intoComboBox(List list, JComboBox jComboBox) {
		jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
		jComboBox.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"code", "name");
		jComboBox.setSelectedIndex(-1);
	}

	/**
	 * This method initializes cbbCommonUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCommonUnit() {
		if (cbbCommonUnit == null) {
			cbbCommonUnit = new JComboBox();
			cbbCommonUnit.setBounds(115, 69, 165, 25);
		}
		return cbbCommonUnit;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField() {
		if (jFormattedTextField == null) {
			jFormattedTextField = new JFormattedTextField();
			jFormattedTextField
					.setFormatterFactory(getDefaultFormatterFactory());
			jFormattedTextField.setBounds(107, 264, 141, 23);
			jFormattedTextField.setVisible(false);
		}
		return jFormattedTextField;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(31, 210, 573, 21);
			jCheckBox.setForeground(java.awt.Color.red);
			jCheckBox.setText("1，申请单转报关单是否自动带出客户，手工输入客户报关单是否自动带出客户设置报关单默认信息；");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jComboBox42
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox42() {
		if (jComboBox42 == null) {
			jComboBox42 = new JComboBox();
			jComboBox42.setBounds(new Rectangle(389, 62, 137, 22));
		}
		return jComboBox42;
	}

	/**
	 * This method initializes jComboBox43
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox43() {
		if (jComboBox43 == null) {
			jComboBox43 = new JComboBox();
			jComboBox43.setBounds(new Rectangle(117, 98, 138, 22));
		}
		return jComboBox43;
	}

	/**
	 * This method initializes jComboBox44
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox44() {
		if (jComboBox44 == null) {
			jComboBox44 = new JComboBox();
			jComboBox44.setBounds(new Rectangle(389, 96, 136, 24));
		}
		return jComboBox44;
	}

	/**
	 * This method initializes jComboBox45
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox45() {
		if (jComboBox45 == null) {
			jComboBox45 = new JComboBox();
			jComboBox45.setBounds(new Rectangle(117, 139, 140, 22));
		}
		return jComboBox45;
	}

	/**
	 * This method initializes pnOther
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnOther() {
		if (pnOther == null) {
			jLabel19111 = new JLabel();
			jLabel19111.setBounds(new Rectangle(416, 44, 132, 18));
			jLabel19111.setText("申请单商品总价小数位：");
			jLabel1911 = new JLabel();
			jLabel1911.setBounds(new Rectangle(208, 44, 132, 18));
			jLabel1911.setText("申请单商品数量小数位：");
			jLabel191 = new JLabel();
			jLabel191.setBounds(new Rectangle(15, 44, 132, 18));
			jLabel191.setText("申请单商品单价小数位：");
			jLabel56 = new JLabel();
			jLabel56.setBounds(new Rectangle(49, 182, 136, 20));
			jLabel56.setText("报关单(打印)默认发运港");
			jLabelAmount = new JLabel();
			jLabelAmount
					.setText("<html><body>报关单商品数量小数位：</br><span style=color:blue>(报文发送数量最多允许4位)</span></body></html>");
			jLabelAmount.setBounds(new Rectangle(206, 4, 147, 35));
			jLabel22 = new JLabel();
			jLabel22.setText("报关单商品总价小数位：");
			jLabel22.setBounds(new Rectangle(415, 7, 132, 26));
			jLabel19 = new JLabel();
			jLabel19.setText("报关单商品单价小数位：");
			jLabel19.setBounds(new Rectangle(16, 7, 132, 26));
			lbTransportTool1 = new JLabel();
			lbTransportTool1.setBounds(new Rectangle(367, 126, 146, 20));
			lbTransportTool1.setText("（不包括@）最大不超过25");
			lbTransportTool = new JLabel();
			lbTransportTool.setBounds(new Rectangle(49, 126, 136, 20));
			lbTransportTool.setText("报关单运输工具额外位数");
			jLabel59 = new JLabel();
			jLabel59.setBounds(new Rectangle(49, 153, 136, 20));
			jLabel59.setText("报关单起始预录入号");
			pnOther = new JPanel();
			pnOther.setLayout(null);
			pnOther.add(getCbIsCustomAmountOut(), null);
			pnOther.add(getCbChauffeur(), null);
			pnOther.add(jLabel59, null);
			pnOther.add(getTfPreCustomsDeclarationCodes(), null);
			pnOther.add(getJCheckBox2(), null);
			pnOther.add(getJCheckBox3(), null);
			pnOther.add(getJCheckBox4(), null);
			pnOther.add(getJButton3(), null);
			pnOther.add(getBtnEdiParaSet(), null);
			pnOther.add(getCbIsCustomAutoAttachedBill(), null);
			pnOther.add(getCbPrintAll(), null);
			pnOther.add(getCbIsAllowBGDDetailExceed20(), null);
			pnOther.add(lbTransportTool, null);
			pnOther.add(getTfTransport(), null);
			pnOther.add(lbTransportTool1, null);
			pnOther.add(getCbCalcWeight(), null);
			pnOther.add(getJCheckBox7(), null);
			pnOther.add(getCbIsSpecification(), null);
			pnOther.add(getCbIsExportPackinglistOrInvoice(), null);
			pnOther.add(getJCheckBox8(), null);
			pnOther.add(jLabel19, null);
			pnOther.add(getJComboBox10(), null);
			pnOther.add(jLabel22, null);
			pnOther.add(getJComboBox11(), null);
			pnOther.add(jLabelAmount, null);
			pnOther.add(getJComboBox12(), null);
			pnOther.add(getCbAutoWeightRound(), null);
			pnOther.add(getJPanel81(), null);
			pnOther.add(jLabel56, null);
			pnOther.add(getTfSendCustoms(), null);
			pnOther.add(getCbIsSaveCustomsEnvelopBillNo(), null);
			pnOther.add(jLabel191, null);
			pnOther.add(getJComboBoxSQcustomTotalNum(), null);
			pnOther.add(jLabel1911, null);
			pnOther.add(getJComboBoxsSQcustomAmountNum(), null);
			pnOther.add(jLabel19111, null);
			pnOther.add(getJComboBoxSQcustomPriceNum(), null);
			pnOther.add(getCbIsTransadd(), null);
			pnOther.add(getLabel_7());
			pnOther.add(getTfInvoiceAddress());
		}
		return pnOther;
	}

	/**
	 * This method initializes cbBigNumber
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCustomAmountOut() {
		if (cbIsCustomAmountOut == null) {
			cbIsCustomAmountOut = new JCheckBox();
			cbIsCustomAmountOut.setBounds(new Rectangle(45, 235, 189, 20));
			cbIsCustomAmountOut.setText("报关单允许输入超量数据");
		}
		return cbIsCustomAmountOut;
	}

	/**
	 * This method initializes cbChauffeur
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbChauffeur() {
		if (cbChauffeur == null) {
			cbChauffeur = new JCheckBox();
			cbChauffeur.setBounds(new Rectangle(45, 285, 162, 20));
			cbChauffeur.setText("司机纸号允许重复输入");
		}
		return cbChauffeur;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPreCustomsDeclarationCodes() {
		if (tfPreCustomsDeclarationCodes == null) {
			tfPreCustomsDeclarationCodes = new JTextField();
			tfPreCustomsDeclarationCodes.setBounds(new Rectangle(205, 153, 163,
					20));
		}
		return tfPreCustomsDeclarationCodes;
	}

	private JPanel pnApplyCustomsBill = null;

	private JLabel jLabel57 = null;

	private JLabel jLabel60 = null;

	private JLabel jLabel61 = null;

	private JLabel jLabel62 = null;

	private JComboBox cbbCurr = null;

	private JComboBox cbbLevyMode = null;

	private JComboBox cbbCountry = null;

	private JComboBox cbbUser = null;

	private ApplyCustomBillParameter applyCustomBillParameter = null;

	private JCheckBox jCheckBox2 = null;

	private JCheckBox jCheckBox3 = null;

	private JCheckBox cbCalcWeight = null;

	private JLabel jLabel15 = null;

	private JFormattedTextField tfPageSize = null;

	private JLabel jLabel16 = null;

	private JCheckBox jCheckBox4 = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel171 = null;

	private JCheckBox jCheckBox5 = null;

	private JButton jButton3 = null;

	private JButton btnEdiParaSet = null;

	private JCheckBox jCheckBox6 = null;

	private JCheckBox cbIsCustomAutoAttachedBill = null;

	private JCheckBox cbPrintAll = null;

	private JLabel jLabel18 = null;

	private JComboBox cbbMouth = null;

	private JLabel jLabel19 = null;

	private JComboBox jComboBox10 = null;

	private JComboBox jComboBox11 = null;

	private JComboBox jComboBox12 = null;

	private JComboBox jComboBoxSQcustomTotalNum = null;

	private JComboBox jComboBoxsSQcustomAmountNum = null;

	private JComboBox jComboBoxSQcustomPriceNum = null;

	private JLabel jLabel20 = null;

	private JFormattedTextField tfBomSize = null;

	private JCheckBox jCheckBox7 = null;

	private JCheckBox cbIsSpecification = null;

	private JCheckBox cbIsExportPackinglistOrInvoice = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JCheckBox cbIssendMail = null;

	private JLabel jLabel21 = null;

	private JTextField tfSendMail = null;

	private JLabel jLabel22 = null;

	private JLabel jLabelAmount = null;

	private JCheckBox cbIsAllowBGDDetailExceed20 = null;

	private JCheckBox jCheckBox8 = null;

	private JLabel lbTransportTool = null;

	private JTextField tfTransportTool = null;

	private JLabel lbTransportTool1 = null;

	private JCheckBox cbAutoWeightRound = null;

	private JPanel jPanel81 = null;

	private JRadioButton cbbTenInnerMerger = null;

	private JRadioButton cbbComplex = null;

	private JLabel jLabel56 = null;

	private JTextField tfSendCustoms = null;

	private JCheckBox cbIsSaveCustomsEnvelopBillNo = null;

	private JCheckBox jCheckBox61 = null;

	private JCheckBox jCheckBoxSumMoney = null;

	private JLabel jLabel191 = null;

	private JLabel jLabel1911 = null;

	private JLabel jLabel19111 = null;
	private JPanel panelHttp;
	private JPanel panelProxy;
	private JTextField tfHttpAddress;
	private JTextField tfHttpTcsUserName;
	private JTextField tfHttpProxyAddress;
	private JTextField tfHttpProxyUser;
	private JTextField tfHttpProxyPsw;
	private JTextField tfHttpProxyPort;
	private JButton btnTestTcs;
	private JTextField tfHttpFptUserName;
	private JPasswordField tfHttpFptPsw;
	private JPasswordField tfHttpTcsPsw;
	private JLabel label_5;
	private JTextField tfPisPort;

	private JCheckBox cbIsTransadd = null;
	private JPanel panel;
	private JTextField tfPisAddress;
	private JLabel label_6;
	private JButton btnPisTest;
	private JLabel label_7;
	private JTextField tfInvoiceAddress;

	/**
	 * This method initializes pnApplyCustomsBill
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnApplyCustomsBill() {
		if (pnApplyCustomsBill == null) {
			jLabel62 = new JLabel();
			jLabel62.setBounds(new Rectangle(248, 70, 99, 22));
			jLabel62.setText("用途代码");
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(248, 28, 99, 22));
			jLabel61.setText("征免性质");
			jLabel60 = new JLabel();
			jLabel60.setBounds(new Rectangle(33, 70, 86, 22));
			jLabel60.setText("产销国（地区）");
			jLabel57 = new JLabel();
			jLabel57.setBounds(new Rectangle(34, 28, 85, 22));
			jLabel57.setText("币制");
			pnApplyCustomsBill = new JPanel();
			pnApplyCustomsBill.setLayout(null);
			pnApplyCustomsBill.add(jLabel57, null);
			pnApplyCustomsBill.add(jLabel60, null);
			pnApplyCustomsBill.add(jLabel61, null);
			pnApplyCustomsBill.add(jLabel62, null);
			pnApplyCustomsBill.add(getCbbCurr(), null);
			pnApplyCustomsBill.add(getCbbLevyMode(), null);
			pnApplyCustomsBill.add(getCbbCountry(), null);
			pnApplyCustomsBill.add(getCbbUser(), null);
		}
		return pnApplyCustomsBill;
	}

	/**
	 * This method initializes cbbCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new Rectangle(121, 29, 119, 21));
		}
		return cbbCurr;
	}

	/**
	 * This method initializes cbbLevyKind
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyMode() {
		if (cbbLevyMode == null) {
			cbbLevyMode = new JComboBox();
			cbbLevyMode.setBounds(new Rectangle(350, 29, 117, 21));
		}
		return cbbLevyMode;
	}

	/**
	 * This method initializes cbbCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(new Rectangle(121, 70, 119, 21));
		}
		return cbbCountry;
	}

	/**
	 * This method initializes cbbUser
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUser() {
		if (cbbUser == null) {
			cbbUser = new JComboBox();
			cbbUser.setBounds(new Rectangle(350, 70, 117, 21));
		}
		return cbbUser;
	}

	public ApplyCustomBillParameter getApplyCustomBillParameter() {
		return applyCustomBillParameter;
	}

	public void setApplyCustomBillParameter(
			ApplyCustomBillParameter applyCustomBillParameter) {
		this.applyCustomBillParameter = applyCustomBillParameter;
	}

	/** 显示数据 */
	private void showData() {
		this.cbbCurr.setSelectedItem(this.applyCustomBillParameter.getCurr());
		this.cbbCountry.setSelectedItem(this.applyCustomBillParameter
				.getCountry());
		this.cbbLevyMode.setSelectedItem(this.applyCustomBillParameter
				.getLevyMode());
		this.cbbUser.setSelectedItem(this.applyCustomBillParameter.getUses());
		System.out.println("this.companyOther.getIsSumMoney()"
				+ this.companyOther.getIsSumMoney());
		if (this.companyOther.getIsSumMoney() == null
				|| this.companyOther.getIsSumMoney() == true) {
			this.jCheckBoxSumMoney.setSelected(true);
		} else {
			this.jCheckBoxSumMoney.setSelected(false);
		}

	}

	/** fill data */
	private void fillData(ApplyCustomBillParameter a) {
		a.setCurr((Curr) this.cbbCurr.getSelectedItem());
		a.setCountry((Country) this.cbbCountry.getSelectedItem());
		a.setLevyMode((LevyMode) this.cbbLevyMode.getSelectedItem());
		a.setUses((Uses) this.cbbUser.getSelectedItem());
	}

	private void saveData() {
		fillData(applyCustomBillParameter);
		applyCustomBillParameter = this.systemAction
				.saveApplyCustomBillParameter(
						new Request(CommonVars.getCurrUser()),
						this.applyCustomBillParameter);

		//
		// 特殊报关单的来源
		//
		List list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.SpecialCustoms);
		if (list != null && list.size() > 0) {
			ParameterSet para = (ParameterSet) list.get(0);
			String values = (cbbTenInnerMerger.isSelected() ? "0" : "1");
			para.setNameValues(values);
			CommonVars.setSpecialCustoms(values);
			systemAction
					.saveValues(new Request(CommonVars.getCurrUser()), para);
		} else {
			ParameterSet pa = new ParameterSet();
			String values = (cbbTenInnerMerger.isSelected() ? "0" : "1");
			pa.setType(ParameterType.SpecialCustoms);
			pa.setNameValues(values);
			CommonVars.setSpecialCustoms(values);
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), pa);
		}

	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox2() {
		if (jCheckBox2 == null) {
			jCheckBox2 = new JCheckBox();
			jCheckBox2.setBounds(new Rectangle(45, 260, 189, 20));
			jCheckBox2.setText("报关单保存是否同时检查");
		}
		return jCheckBox2;
	}

	/**
	 * This method initializes jCheckBox3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox3() {
		if (jCheckBox3 == null) {
			jCheckBox3 = new JCheckBox();
			jCheckBox3.setBounds(new Rectangle(45, 385, 377, 20));
			jCheckBox3.setText("报关单转关附加输入境外运输工具是否自动带出境内运输工具");
		}
		return jCheckBox3;
	}

	/**
	 * This method initializes cbCalcWeight
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCalcWeight() {
		if (cbCalcWeight == null) {
			cbCalcWeight = new JCheckBox();
			cbCalcWeight.setBounds(new Rectangle(45, 310, 316, 20));
			cbCalcWeight.setText("报关单总件数，净重毛重是否由商品信息自动计算出来");
			cbCalcWeight.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbCalcWeight.isSelected()) {
						cbAutoWeightRound.setEnabled(true);
					} else {
						cbAutoWeightRound.setSelected(false);
						cbAutoWeightRound.setEnabled(false);
					}
				}
			});
		}
		return cbCalcWeight;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPageSize() {
		if (tfPageSize == null) {
			tfPageSize = new JFormattedTextField();
			tfPageSize.setBounds(265, 106, 90, 25);
		}
		return tfPageSize;
	}

	/**
	 * This method initializes jCheckBox4
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox4() {
		if (jCheckBox4 == null) {
			jCheckBox4 = new JCheckBox();
			jCheckBox4.setBounds(new Rectangle(356, 285, 204, 20));
			jCheckBox4.setText("报关单手动输入预录入号");
		}
		return jCheckBox4;
	}

	/**
	 * This method initializes jCheckBox5
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox5() {
		if (jCheckBox5 == null) {
			jCheckBox5 = new JCheckBox();
			jCheckBox5.setBounds(31, 256, 235, 21);
			jCheckBox5.setForeground(java.awt.Color.red);
			jCheckBox5.setText("2，新增申请单是否自动产生单据号码；");
		}
		return jCheckBox5;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(144, 516, 146, 25));
			jButton3.setText("自定义检查保存选项");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCommonCheckNull dg = new DgCommonCheckNull();
					dg.setVisible(true);
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes btnEdiParaSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdiParaSet() {
		if (btnEdiParaSet == null) {
			btnEdiParaSet = new JButton();
			btnEdiParaSet.setBounds(new Rectangle(291, 516, 113, 25));
			btnEdiParaSet.setText("EDI路径设置");
			btnEdiParaSet
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgBcusPathSet dg = new DgBcusPathSet() {
								@Override
								public void setVisible(boolean f) {
									this.setResizable(false);
									this.setTitle("EDI路径设置");
									this.setSize(358, 278);
									this.jLabel5.setVisible(false);
									this.jLabel6.setVisible(false);
									this.jLabel7.setVisible(false);
									this.jLabel5.setVisible(false);
									this.jLabel8.setVisible(false);
									this.jLabel9.setVisible(false);
									this.jLabel10.setVisible(false);
									this.jLabel11.setVisible(false);
									this.jLabel14.setVisible(false);
									this.getTfRecvBillDir().setVisible(false);
									this.getJTextField4().setVisible(false);
									this.getJTextField5().setVisible(false);
									this.getJTextField6().setVisible(false);
									this.getJTextField7().setVisible(false);
									this.getJButton34().setVisible(false);
									this.getJButton35().setVisible(false);
									this.getJButton36().setVisible(false);
									this.getJButton37().setVisible(false);
									this.jLabel13.setVisible(false);
									this.getJRadioButton().setVisible(false);
									this.getJRadioButton1().setVisible(false);
									this.getJRadioButton2().setVisible(false);
									this.getJCheckBox().setVisible(false);
									this.getJButton33().setVisible(false);
									this.getJButton2().setBounds(
											new Rectangle(25, 208, 85, 26));
									this.getJButton().setBounds(
											new Rectangle(127, 208, 85, 26));
									this.getJButton1().setBounds(
											new Rectangle(new Rectangle(229,
													208, 85, 26)));
									super.setVisible(true);

								}

							};
							dg.setVisible(true);
						}
					});
		}
		return btnEdiParaSet;
	}

	/**
	 * This method initializes jCheckBox6
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox6() {
		if (jCheckBox6 == null) {
			jCheckBox6 = new JCheckBox();
			jCheckBox6.setBounds(31, 233, 221, 21);
			jCheckBox6.setText("3，各报表数据以千分号来显示；");
			jCheckBox6.setForeground(java.awt.Color.red);
		}
		return jCheckBox6;
	}

	/**
	 * This method initializes cbIsCustomAutoAttachedBill
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCustomAutoAttachedBill() {
		if (cbIsCustomAutoAttachedBill == null) {
			cbIsCustomAutoAttachedBill = new JCheckBox();
			cbIsCustomAutoAttachedBill.setBounds(new Rectangle(356, 235, 204,
					20));
			cbIsCustomAutoAttachedBill.setText("报关单自动带监管证件代码");
		}
		return cbIsCustomAutoAttachedBill;
	}

	/**
	 * This method initializes cbPrintAll
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbPrintAll() {
		if (cbPrintAll == null) {
			cbPrintAll = new JCheckBox();
			cbPrintAll.setBounds(new Rectangle(356, 260, 204, 20));
			cbPrintAll.setText("报关单随附单据是否打印全称");
		}
		return cbPrintAll;
	}

	/**
	 * This method initializes cbbMouth
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMouth() {
		if (cbbMouth == null) {
			cbbMouth = new JComboBox();
			cbbMouth.setBounds(199, 138, 168, 25);
			cbbMouth.addItem("30");
			cbbMouth.addItem("60");
			cbbMouth.addItem("90");
		}
		return cbbMouth;
	}

	/**
	 * This method initializes jComboBox10
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox10() {
		if (jComboBox10 == null) {
			Integer[] num = { 0, 1, 2, 3, 4 };
			jComboBox10 = new JComboBox(num);
			jComboBox10.setBounds(new Rectangle(145, 6, 60, 26));
		}
		return jComboBox10;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox11() {
		if (jComboBox11 == null) {
			Integer[] num = { 0, 1, 2 };
			jComboBox11 = new JComboBox(num);
			jComboBox11.setBounds(new Rectangle(550, 6, 60, 26));
		}
		return jComboBox11;
	}

	/**
	 * This method initializes jComboBox12
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox12() {
		if (jComboBox12 == null) {
			Integer[] num = { 0, 1, 2, 3, 4, 5 };
			jComboBox12 = new JComboBox(num);
			jComboBox12.setBounds(new Rectangle(350, 6, 60, 26));
		}
		return jComboBox12;
	}

	private JComboBox getJComboBoxSQcustomTotalNum() {
		if (jComboBoxSQcustomTotalNum == null) {
			Integer[] num = { 0, 1, 2, 3, 4 };
			jComboBoxSQcustomTotalNum = new JComboBox(num);
			jComboBoxSQcustomTotalNum.setBounds(new Rectangle(550, 30, 60, 30));
		}
		return jComboBoxSQcustomTotalNum;
	}

	private JComboBox getJComboBoxsSQcustomAmountNum() {
		if (jComboBoxsSQcustomAmountNum == null) {
			Integer[] num = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			jComboBoxsSQcustomAmountNum = new JComboBox(num);
			jComboBoxsSQcustomAmountNum
					.setBounds(new Rectangle(350, 30, 60, 30));
		}
		return jComboBoxsSQcustomAmountNum;
	}

	private JComboBox getJComboBoxSQcustomPriceNum() {
		if (jComboBoxSQcustomPriceNum == null) {
			Integer[] num = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
			jComboBoxSQcustomPriceNum = new JComboBox(num);
			jComboBoxSQcustomPriceNum.setBounds(new Rectangle(145, 30, 60, 30));
		}
		return jComboBoxSQcustomPriceNum;
	}

	/**
	 * This method initializes tfBomSize
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfBomSize() {
		if (tfBomSize == null) {
			tfBomSize = new JFormattedTextField();
			tfBomSize.setBounds(410, 177, 69, 25);
		}
		return tfBomSize;
	}

	/**
	 * This method initializes jCheckBox7
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox7() {
		if (jCheckBox7 == null) {
			jCheckBox7 = new JCheckBox();
			jCheckBox7.setText("直接、转厂出口报关单合同余量包含退厂返工-返工复出");
			jCheckBox7.setBounds(new Rectangle(45, 410, 359, 20));
		}
		return jCheckBox7;
	}

	/**
	 * This method initializes cbIsSpecification
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsSpecification() {
		if (cbIsSpecification == null) {
			cbIsSpecification = new JCheckBox();
			cbIsSpecification.setText("报关单打印取规范申报规格");
			cbIsSpecification.setBounds(new Rectangle(45, 432, 359, 20));
		}
		return cbIsSpecification;
	}

	private JCheckBox getCbIsExportPackinglistOrInvoice() {
		if (cbIsExportPackinglistOrInvoice == null) {
			cbIsExportPackinglistOrInvoice = new JCheckBox();
			cbIsExportPackinglistOrInvoice
					.setText("【出口装箱单(包装种类在明细及商品编码)/出口加工发票】打印显示购货单位/发货单位");
			cbIsExportPackinglistOrInvoice.setBounds(new Rectangle(45, 454,
					559, 20));
		}
		return cbIsExportPackinglistOrInvoice;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getJPanel3(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(20, 44, 52, 24));
			jLabel21.setText("收件箱：");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBounds(new Rectangle(10, 7, 546, 77));
			jPanel3.add(getCbIssendMail(), null);
			jPanel3.add(jLabel21, null);
			jPanel3.add(getTfSendMail(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes cbIssendMail
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIssendMail() {
		if (cbIssendMail == null) {
			cbIssendMail = new JCheckBox();
			cbIssendMail.setBounds(new Rectangle(19, 5, 192, 28));
			cbIssendMail.setText("数据导入失败，发送邮件");
		}
		return cbIssendMail;
	}

	/**
	 * This method initializes tfMail
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSendMail() {
		if (tfSendMail == null) {
			tfSendMail = new JTextField();
			tfSendMail.setBounds(new Rectangle(75, 44, 152, 25));
		}
		return tfSendMail;
	}

	/**
	 * This method initializes jCheckBox8
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsAllowBGDDetailExceed20() {
		if (cbIsAllowBGDDetailExceed20 == null) {
			cbIsAllowBGDDetailExceed20 = new JCheckBox();
			cbIsAllowBGDDetailExceed20.setBounds(new Rectangle(356, 310, 189,
					20));
			cbIsAllowBGDDetailExceed20.setText("报关单明细允许超过20项");
		}
		return cbIsAllowBGDDetailExceed20;
	}

	/**
	 * This method initializes jCheckBox8
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox8() {
		if (jCheckBox8 == null) {
			jCheckBox8 = new JCheckBox();
			jCheckBox8.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBox8.setMnemonic(KeyEvent.VK_UNDEFINED);
			jCheckBox8.setBounds(new Rectangle(45, 360, 375, 20));
			jCheckBox8.setText("检查报关单中的表头和表体的总数量，净重、毛重是否一致");
		}
		return jCheckBox8;
	}

	/**
	 * This method initializes tfTransport
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTransport() {
		if (tfTransportTool == null) {
			tfTransportTool = new JTextField();
			tfTransportTool.setBounds(new Rectangle(205, 126, 163, 20));
		}
		return tfTransportTool;
	}

	/**
	 * This method initializes cbAutoWeightRound
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAutoWeightRound() {
		if (cbAutoWeightRound == null) {
			cbAutoWeightRound = new JCheckBox();
			cbAutoWeightRound.setBounds(new Rectangle(45, 335, 309, 20));
			cbAutoWeightRound.setText("报关单表头总净重、总毛重小数位是否取整");
			cbAutoWeightRound.setEnabled(false);
		}
		return cbAutoWeightRound;
	}

	/**
	 * This method initializes jPanel81
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel81() {
		if (jPanel81 == null) {
			jPanel81 = new JPanel();
			jPanel81.setLayout(null);
			jPanel81.setBounds(new Rectangle(11, 71, 594, 48));
			jPanel81.setBorder(BorderFactory
					.createTitledBorder(
							null,
							"\u7279\u6b8a\u62a5\u5173\u5355\u5546\u54c1\u8d44\u6599\u6765\u6e90\u8bbe\u5b9a",
							TitledBorder.DEFAULT_JUSTIFICATION,
							TitledBorder.DEFAULT_POSITION, new Font("Dialog",
									Font.BOLD, 12), Color.blue));
			jPanel81.add(getCbbTenInnerMerger(), null);
			jPanel81.add(getCbbComplex(), null);
		}
		return jPanel81;
	}

	/**
	 * This method initializes cbbTenInnerMerger
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbTenInnerMerger() {
		if (cbbTenInnerMerger == null) {
			cbbTenInnerMerger = new JRadioButton();
			cbbTenInnerMerger.setBounds(new Rectangle(125, 26, 166, 21));
			cbbTenInnerMerger.setText("归并关系(报关商品)资料");
		}
		return cbbTenInnerMerger;
	}

	/**
	 * This method initializes cbbComplex
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getCbbComplex() {
		if (cbbComplex == null) {
			cbbComplex = new JRadioButton();
			cbbComplex.setBounds(new Rectangle(366, 26, 125, 21));
			cbbComplex.setText("自用商品编码");
		}
		return cbbComplex;
	}

	/**
	 * This method initializes tfSendCustoms
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JTextField getTfSendCustoms() {
		if (tfSendCustoms == null) {
			tfSendCustoms = new JTextField();
			tfSendCustoms.setBounds(new Rectangle(205, 180, 163, 25));
		}
		return tfSendCustoms;
	}

	/**
	 * This method initializes cbIsSaveCustomsEnvelopBillNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsSaveCustomsEnvelopBillNo() {
		if (cbIsSaveCustomsEnvelopBillNo == null) {
			cbIsSaveCustomsEnvelopBillNo = new JCheckBox();
			cbIsSaveCustomsEnvelopBillNo.setBounds(new Rectangle(356, 335, 254,
					20));
			cbIsSaveCustomsEnvelopBillNo.setText("进出口报关单转厂业务是否强制检查关封号");
		}
		return cbIsSaveCustomsEnvelopBillNo;
	}

	/**
	 * This method initializes jCheckBox61
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox61() {
		if (jCheckBox61 == null) {
			jCheckBox61 = new JCheckBox();
			jCheckBox61.setBounds(275, 233, 200, 21);
			jCheckBox61.setText("4、申请单表体新增时商品过滤");
			jCheckBox61.setSelected(true);
			jCheckBox61.setForeground(Color.red);
		}
		return jCheckBox61;
	}

	/**
	 * This method initializes jCheckBoxSumMoney
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBoxSumMoney() {
		if (jCheckBoxSumMoney == null) {
			jCheckBoxSumMoney = new JCheckBox();
			jCheckBoxSumMoney.setBounds(275, 256, 211, 21);
			jCheckBoxSumMoney.setText("5、申请单文本导入默认计算总金额");
			jCheckBoxSumMoney.setForeground(Color.red);
			jCheckBoxSumMoney.setVisible(true);
		}
		return jCheckBoxSumMoney;
	}

	private JPanel getPanelHttp() {
		if (panelHttp == null) {
			panelHttp = new JPanel();
			panelHttp.setBounds(31, 296, 574, 130);
			panelHttp.setBorder(new TitledBorder(UIManager
					.getBorder("TitledBorder.border"),
					"\u62A5\u6587\u53C2\u6570\u8BBE\u7F6E",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelHttp.setLayout(null);

			tfHttpAddress = new JTextField();
			tfHttpAddress.setBounds(56, 25, 171, 21);
			panelHttp.add(tfHttpAddress);
			tfHttpAddress.setColumns(10);

			JLabel label = new JLabel("服务器：");
			label.setBounds(10, 28, 48, 15);
			panelHttp.add(label);

			JPanel panelTcs = new JPanel();
			panelTcs.setBorder(new TitledBorder(UIManager
					.getBorder("TitledBorder.border"),
					"\u62A5\u5173\u5355\u62A5\u6587\u4F20\u8F93",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelTcs.setBounds(237, 10, 327, 51);
			panelHttp.add(panelTcs);
			panelTcs.setLayout(null);

			JLabel label_1 = new JLabel("用户名：");
			label_1.setBounds(20, 26, 48, 15);
			panelTcs.add(label_1);

			tfHttpTcsUserName = new JTextField();
			tfHttpTcsUserName.setBounds(66, 23, 66, 21);
			panelTcs.add(tfHttpTcsUserName);
			tfHttpTcsUserName.setColumns(10);

			JLabel label_2 = new JLabel("密码：");
			label_2.setBounds(143, 26, 36, 15);
			panelTcs.add(label_2);
			panelTcs.add(getBtnTcs());

			tfHttpTcsPsw = new JPasswordField();
			tfHttpTcsPsw.setBounds(177, 23, 66, 21);
			panelTcs.add(tfHttpTcsPsw);

			JPanel panelFpt = new JPanel();
			panelFpt.setBorder(new TitledBorder(UIManager
					.getBorder("TitledBorder.border"),
					"\u6DF1\u52A0\u5DE5\u62A5\u6587\u4F20\u8F93",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelFpt.setBounds(237, 71, 328, 51);
			panelHttp.add(panelFpt);
			panelFpt.setLayout(null);

			JLabel label_3 = new JLabel("用户名：");
			label_3.setBounds(20, 26, 48, 15);
			panelFpt.add(label_3);

			tfHttpFptUserName = new JTextField();
			tfHttpFptUserName.setBounds(66, 23, 66, 21);
			tfHttpFptUserName.setColumns(10);
			panelFpt.add(tfHttpFptUserName);

			JLabel label_4 = new JLabel("密码：");
			label_4.setBounds(143, 26, 36, 15);
			panelFpt.add(label_4);

			panelFpt.add(getBtnFpt());

			tfHttpFptPsw = new JPasswordField();
			tfHttpFptPsw.setBounds(177, 23, 66, 21);
			panelFpt.add(tfHttpFptPsw);
		}
		return panelHttp;
	}

	private JPanel getPanelProxy() {
		if (panelProxy == null) {
			panelProxy = new JPanel();
			panelProxy.setLayout(null);
			panelProxy.setBorder(new TitledBorder(UIManager
					.getBorder("TitledBorder.border"),
					"HTTP \u4EE3\u7406\u8BBE\u7F6E", TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			panelProxy.setBounds(31, 430, 573, 71);
			jPanel.add(panelProxy);

			tfHttpProxyAddress = new JTextField();
			tfHttpProxyAddress.setColumns(10);
			tfHttpProxyAddress.setBounds(56, 27, 171, 21);
			panelProxy.add(tfHttpProxyAddress);

			tfHttpProxyUser = new JTextField();
			tfHttpProxyUser.setColumns(10);
			tfHttpProxyUser.setBounds(360, 27, 81, 21);
			panelProxy.add(tfHttpProxyUser);

			tfHttpProxyPsw = new JTextField();
			tfHttpProxyPsw.setColumns(10);
			tfHttpProxyPsw.setBounds(482, 27, 81, 21);
			panelProxy.add(tfHttpProxyPsw);

			tfHttpProxyPort = new JTextField();
			tfHttpProxyPort.setBounds(271, 27, 36, 21);
			panelProxy.add(tfHttpProxyPort);
			tfHttpProxyPort.setColumns(10);

			JLabel label = new JLabel("服务器：");
			label.setBounds(10, 30, 48, 15);
			panelProxy.add(label);

			JLabel label_1 = new JLabel("用户名：");
			label_1.setBounds(315, 30, 48, 15);
			panelProxy.add(label_1);

			JLabel label_2 = new JLabel("密码：");
			label_2.setBounds(447, 30, 36, 15);
			panelProxy.add(label_2);

			JLabel label_3 = new JLabel("端口：");
			label_3.setBounds(237, 30, 36, 15);
			panelProxy.add(label_3);

		}
		return panelProxy;
	}

	private JButton getBtnTcs() {
		if (btnTestTcs == null) {
			btnTestTcs = new JButton("测试");
			btnTestTcs.setBounds(253, 22, 64, 23);
			btnTestTcs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						boolean isConn = systemAction.httpTestConnect(
								new Request(CommonVars.getCurrUser(), true),
								HttpMsgTransType.TCS);
						if (isConn) {
							JOptionPane.showMessageDialog(DgCompanyOther.this,
									"HTTP网络畅通", "提示！",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						} else {
							JOptionPane.showMessageDialog(DgCompanyOther.this,
									"网络不通,请检查互联网是否畅通.", "提示！",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgCompanyOther.this,
								"网络不通,详细信息：" + ex.getMessage(), "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});

		}
		return btnTestTcs;
	}

	private JButton getBtnFpt() {
		if (btnTestFpt == null) {
			btnTestFpt = new JButton("测试");
			btnTestFpt.setBounds(253, 22, 65, 23);
			btnTestFpt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						boolean isConn = systemAction.httpTestConnect(
								new Request(CommonVars.getCurrUser(), true),
								HttpMsgTransType.FPT);
						if (isConn) {
							JOptionPane.showMessageDialog(DgCompanyOther.this,
									"HTTP网络畅通", "提示！",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						} else {
							JOptionPane.showMessageDialog(DgCompanyOther.this,
									"网络不通,请检查互联网是否畅通.", "提示！",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgCompanyOther.this,
								"网络不通,详细信息：" + ex.getMessage(), "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});

		}
		return btnTestFpt;
	}

	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel("端口：");
			label_5.setBounds(237, 30, 36, 15);
		}
		return label_5;
	}

	private JTextField getTfPisPort() {

		if (tfPisPort == null) {
			tfPisPort = new JTextField();
			tfPisPort.setBounds(269, 27, 36, 21);
			tfPisPort.setColumns(10);
		}
		return tfPisPort;
	}

	/**
	 * This method initializes cbIsTransadd
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTransadd() {
		if (cbIsTransadd == null) {
			cbIsTransadd = new JCheckBox();
			cbIsTransadd.setBounds(new Rectangle(45, 471, 455, 26));
			cbIsTransadd.setText("打印报关单不显示【转关附加】信息和税费征收情况");
		}
		return cbIsTransadd;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.setBorder(new TitledBorder(UIManager
					.getBorder("TitledBorder.border"), "百思维运维服务器配置",
					TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(31, 511, 573, 71);
			panel.add(getTfPisAddress());
			panel.add(getLabel_6());
			panel.add(getBtnPisTest());
			panel.add(getLabel_5());
			panel.add(getTfPisPort());
		}
		return panel;
	}

	private JTextField getTfPisAddress() {
		if (tfPisAddress == null) {
			tfPisAddress = new JTextField();
			tfPisAddress.setColumns(10);
			tfPisAddress.setBounds(56, 27, 171, 21);
		}
		return tfPisAddress;
	}

	private JLabel getLabel_6() {
		if (label_6 == null) {
			label_6 = new JLabel("服务器：");
			label_6.setBounds(10, 30, 48, 15);
		}
		return label_6;
	}

	private JButton getBtnPisTest() {
		if (btnPisTest == null) {
			btnPisTest = new JButton("测试");
			btnPisTest.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					/****************** 地址获取端口出现异常 **************************/
					/*
					 * tfPisPort 没有获取 text 导致直接打印变量地址
					 * 
					 * 修改 tfPisPort.getText() 获取字符串
					 */
					String urlAddress = "http://" + tfPisAddress.getText()
							+ ":" + tfPisPort.getText()
							+ "/bcgs-war/GetEspBrokerCorpServlet";

					System.out.println("urlAddress = " + urlAddress);

					HttpClientInvoker clientInvoker = new HttpClientInvoker();
					Map<String, String> params = new HashMap();
					try {
						String resultData = clientInvoker.executeMethod(
								urlAddress, params, null);
						JOptionPane.showMessageDialog(DgCompanyOther.this,
								"测试成功");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(DgCompanyOther.this,
								"连接失败", "提示", JOptionPane.INFORMATION_MESSAGE);
						e1.printStackTrace();
					}
				}
			});
			btnPisTest.setBounds(490, 22, 65, 23);
		}
		return btnPisTest;
	}

	private JLabel getLabel_7() {
		if (label_7 == null) {
			label_7 = new JLabel();
			label_7.setText("发票（太平）默认地址为：");
			label_7.setBounds(49, 211, 156, 20);
		}
		return label_7;
	}

	private JTextField getTfInvoiceAddress() {
		if (tfInvoiceAddress == null) {
			tfInvoiceAddress = new JTextField();
			tfInvoiceAddress.setBounds(205, 210, 163, 25);
		}
		return tfInvoiceAddress;
	}
}