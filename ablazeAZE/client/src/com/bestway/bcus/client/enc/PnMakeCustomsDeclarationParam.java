/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JPanelBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeCustomsDeclarationParam extends JPanelBase {

	private JComboBox cbbDomesticDestinationOrSource = null;

	private JComboBox cbbTransac = null;

	private JComboBox cbbWrapType = null;

	private JTextField tfConveyance = null;

	private JComboBox cbbCustomer = null;

	private JCalendarComboBox cbbImpExpDate = null;

	private JComboBox cbbCountryOfLoadingOrUnloading = null;

	private JComboBox cbbPortOfLoadingOrUnloading = null;

	private JComboBox cbbLevyKind = null;

	private JTextField tfLicense = null;

	private JCheckBox cbIsSpecialCustomsDeclaration = null;

	private MaterialManageAction materialManageAction = null;

	private CustomBaseAction customBaseAction = null;

	private MakeCusomsDeclarationParam makeCusomsDeclarationParam = null;

	private JFormattedTextField tfPerTax = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	/**
	 * This is the default constructor
	 */
	public PnMakeCustomsDeclarationParam() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		javax.swing.JLabel jLabel10 = new JLabel();
		javax.swing.JLabel jLabel9 = new JLabel();
		javax.swing.JLabel jLabel8 = new JLabel();
		javax.swing.JLabel jLabel7 = new JLabel();
		javax.swing.JLabel jLabel6 = new JLabel();
		javax.swing.JLabel jLabel5 = new JLabel();
		javax.swing.JLabel jLabel4 = new JLabel();
		javax.swing.JLabel jLabel3 = new JLabel();
		javax.swing.JLabel jLabel2 = new JLabel();
		javax.swing.JLabel jLabel1 = new JLabel();
		javax.swing.JLabel jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(500, 248);
		jLabel.setBounds(35, 25, 78, 21);
		jLabel.setText("征税比例%");
		jLabel1.setBounds(35, 53, 78, 21);
		jLabel1.setText("境内目的地");
		jLabel2.setBounds(35, 81, 78, 21);
		jLabel2.setText("成交方式");
		jLabel3.setBounds(35, 111, 78, 21);
		jLabel3.setText("包装种类");
		jLabel4.setBounds(35, 140, 76, 21);
		jLabel4.setText("车牌号码");
		jLabel5.setBounds(35, 172, 78, 21);
		jLabel5.setText("发/收货单位");
		jLabel6.setBounds(246, 25, 105, 21);
		jLabel6.setText("进口日期");
		jLabel7.setBounds(246, 53, 105, 21);
		jLabel7.setText("起运国（运抵国）");
		jLabel8.setBounds(246, 81, 105, 21);
		jLabel8.setText("装货港");
		jLabel9.setBounds(246, 111, 105, 21);
		jLabel9.setText("许可证号");
		jLabel10.setBounds(246, 140, 105, 21);
		jLabel10.setText("征免性质");
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		this.add(jLabel4, null);
		this.add(jLabel5, null);
		this.add(getCbbDomesticDestinationOrSource(), null);
		this.add(getCbbTransac(), null);
		this.add(getCbbWrapType(), null);
		this.add(getTfConveyance(), null);
		this.add(getCbbCustomer(), null);
		this.add(jLabel6, null);
		this.add(jLabel7, null);
		this.add(jLabel8, null);
		this.add(jLabel9, null);
		this.add(jLabel10, null);
		this.add(getCbbImpExpDate(), null);
		this.add(getCbbCountryOfLoadingOrUnloading(), null);
		this.add(getCbbPortOfLoadingOrUnloading(), null);
		this.add(getCbbLevyKind(), null);
		this.add(getTfLicense(), null);
		this.add(getCbIsSpecialCustomsDeclaration(), null);
		this.add(getTfPerTax(), null);
	}

	/**
	 * This method initializes cbbDomesticDestinationOrSource
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDomesticDestinationOrSource() {
		if (cbbDomesticDestinationOrSource == null) {
			cbbDomesticDestinationOrSource = new JComboBox();
			cbbDomesticDestinationOrSource.setBounds(114, 53, 127, 21);
			cbbDomesticDestinationOrSource.setEditable(true);
		}
		return cbbDomesticDestinationOrSource;
	}

	/**
	 * This method initializes cbbTransac
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.setBounds(114, 81, 127, 21);
			cbbTransac.setEditable(true);
		}
		return cbbTransac;
	}

	/**
	 * This method initializes cbbWrapType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(114, 111, 127, 21);
			cbbWrapType.setEditable(true);
		}
		return cbbWrapType;
	}

	/**
	 * This method initializes tfConveyance
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConveyance() {
		if (tfConveyance == null) {
			tfConveyance = new JTextField();
			tfConveyance.setBounds(113, 140, 127, 21);
		}
		return tfConveyance;
	}

	/**
	 * This method initializes cbbCustomer
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(113, 171, 356, 21);
			cbbCustomer.setEditable(true);
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes cbbImpExpDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbImpExpDate() {
		if (cbbImpExpDate == null) {
			cbbImpExpDate = new JCalendarComboBox();
			cbbImpExpDate.setBounds(356, 25, 112, 21);
		}
		return cbbImpExpDate;
	}

	/**
	 * This method initializes cbbCountryOfLoadingOrUnloading
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountryOfLoadingOrUnloading() {
		if (cbbCountryOfLoadingOrUnloading == null) {
			cbbCountryOfLoadingOrUnloading = new JComboBox();
			cbbCountryOfLoadingOrUnloading.setBounds(356, 53, 112, 21);
			cbbCountryOfLoadingOrUnloading.setEditable(true);
		}
		return cbbCountryOfLoadingOrUnloading;
	}

	/**
	 * This method initializes cbbPortOfLoadingOrUnloading
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPortOfLoadingOrUnloading() {
		if (cbbPortOfLoadingOrUnloading == null) {
			cbbPortOfLoadingOrUnloading = new JComboBox();
			cbbPortOfLoadingOrUnloading.setBounds(356, 81, 112, 21);
			cbbPortOfLoadingOrUnloading.setEditable(true);
		}
		return cbbPortOfLoadingOrUnloading;
	}

	/**
	 * This method initializes cbbLevyKind
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(356, 140, 112, 21);
			cbbLevyKind.setEditable(true);
		}
		return cbbLevyKind;
	}

	/**
	 * This method initializes tfLicense
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLicense() {
		if (tfLicense == null) {
			tfLicense = new JTextField();
			tfLicense.setBounds(356, 111, 112, 21);
		}
		return tfLicense;
	}

	/**
	 * This method initializes cbIsSpecialCustomsDeclaration
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsSpecialCustomsDeclaration() {
		if (cbIsSpecialCustomsDeclaration == null) {
			cbIsSpecialCustomsDeclaration = new JCheckBox();
			cbIsSpecialCustomsDeclaration.setBounds(35, 201, 212, 20);
			cbIsSpecialCustomsDeclaration.setText("生成特殊报关单");
		}
		return cbIsSpecialCustomsDeclaration;
	}

	private void initUIComponents() {
		// 初始化征免性质
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbLevyKind);
		this.cbbLevyKind
				.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbLevyKind.setSelectedIndex(-1);
		// 初始化起运国
		this.cbbCountryOfLoadingOrUnloading.setModel(CustomBaseModel
				.getInstance().getCountryModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCountryOfLoadingOrUnloading);
		this.cbbCountryOfLoadingOrUnloading.setRenderer(CustomBaseRender
				.getInstance().getRender());
		this.cbbCountryOfLoadingOrUnloading.setSelectedIndex(-1);
		// 初始化境内目的地
		this.cbbDomesticDestinationOrSource.setModel(CustomBaseModel
				.getInstance().getDistrictModelModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDomesticDestinationOrSource);
		this.cbbDomesticDestinationOrSource.setRenderer(CustomBaseRender
				.getInstance().getRender());
		this.cbbDomesticDestinationOrSource.setSelectedIndex(-1);
		// 初始化装货港
		this.cbbPortOfLoadingOrUnloading.setModel(CustomBaseModel.getInstance()
				.getPortLinModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPortOfLoadingOrUnloading);
		this.cbbPortOfLoadingOrUnloading.setRenderer(CustomBaseRender
				.getInstance().getRender());
		this.cbbPortOfLoadingOrUnloading.setSelectedIndex(-1);
		// 初始化成交方式
		this.cbbTransac.setModel(CustomBaseModel.getInstance()
				.getTransacModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransac);
		this.cbbTransac.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbTransac.setSelectedIndex(-1);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		this.cbbWrapType.setSelectedIndex(-1);

		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name");
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		this.cbbCustomer.setSelectedIndex(-1);
	}

	private void fillData() {

		this.makeCusomsDeclarationParam.setImpExpDate(this.cbbImpExpDate
				.getDate());

		this.makeCusomsDeclarationParam.setConveyance(this.tfConveyance
				.getText().trim());

		if (this.cbbLevyKind.getSelectedItem() != null) {
			this.makeCusomsDeclarationParam
					.setLevyKind((LevyKind) this.cbbLevyKind.getSelectedItem());
		}
		if (this.tfPerTax.getValue() != null) {
			this.makeCusomsDeclarationParam.setPerTax(Double
					.valueOf(this.tfPerTax.getValue().toString()));
		}
		this.makeCusomsDeclarationParam.setLicense(this.tfLicense.getText()
				.trim());
		if (this.cbbCountryOfLoadingOrUnloading.getSelectedItem() != null) {
			this.makeCusomsDeclarationParam
					.setCountryOfLoadingOrUnloading((Country) this.cbbCountryOfLoadingOrUnloading
							.getSelectedItem());
		}
		if (this.cbbDomesticDestinationOrSource.getSelectedItem() != null) {
			this.makeCusomsDeclarationParam
					.setDomesticDestinationOrSource((District) this.cbbDomesticDestinationOrSource
							.getSelectedItem());
		}
		if (this.cbbPortOfLoadingOrUnloading.getSelectedItem() != null) {
			this.makeCusomsDeclarationParam
					.setPortOfLoadingOrUnloading((PortLin) this.cbbPortOfLoadingOrUnloading
							.getSelectedItem());
		}
		if (this.cbbTransac.getSelectedItem() != null) {
			this.makeCusomsDeclarationParam
					.setTransac((Transac) this.cbbTransac.getSelectedItem());
		}

		if (this.cbbWrapType.getSelectedItem() != null) {
			this.makeCusomsDeclarationParam.setWrapType((Wrap) this.cbbWrapType
					.getSelectedItem());
		}

		this.makeCusomsDeclarationParam.setCustomer((ScmCoc) this.cbbCustomer
				.getSelectedItem());
	}

	/**
	 * This method initializes tfPerTax
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPerTax() {
		if (tfPerTax == null) {
			tfPerTax = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPerTax.setBounds(114, 24, 127, 23);
			tfPerTax.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfPerTax;
	}

	/**
	 * 返回报关清单转报关单的条件
	 */
	public MakeCusomsDeclarationParam getMakeCusomsDeclarationParam() {
		if (makeCusomsDeclarationParam == null) {
			makeCusomsDeclarationParam = new MakeCusomsDeclarationParam();
			fillData();
		}
		return makeCusomsDeclarationParam;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
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

} // @jve:decl-index=0:visual-constraint="10,10"

