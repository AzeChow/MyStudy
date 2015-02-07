/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.Rectangle;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JPanelBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class PnMakeFptBillListParam extends JPanelBase {

	private JComboBox cbbDomesticDestinationOrSource = null;

	private JComboBox cbbTransac = null;

	private JComboBox cbbWrapType = null;

//	private JTextField tfConveyance = null;

	private JCalendarComboBox cbbDeclarationDate = null;

	private JComboBox cbbCountryOfLoadingOrUnloading = null;

	private JComboBox cbbPortOfLoadingOrUnloading = null;

//	private JComboBox cbbLevyKind = null;

	private JTextField tfLicense = null;

	private FptManageAction fptManageAction = null;
	private CustomBaseAction customBaseAction = null;

	private MakeCusomsDeclarationParam makeCusomsDeclarationParam = null; // @jve:decl-index=0:

	private JFormattedTextField tfPerTax = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JLabel jLabel5 = null;

	private List parentHead = null; // @jve:decl-index=0: // @jve:decl-index=0:
	private String paramScoId="";  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public PnMakeFptBillListParam() {
		super();
		initialize();
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		
	}
	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			initUIComponents();
			showInitData();
		}
		super.setVisible(isFlag);
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
		javax.swing.JLabel jLabel4 = new JLabel();
		javax.swing.JLabel jLabel3 = new JLabel();
		javax.swing.JLabel jLabel2 = new JLabel();
		javax.swing.JLabel jLabel1 = new JLabel();
		javax.swing.JLabel jLabel = new JLabel();
		this.setLayout(null);
		this.setSize(544, 248);// 654, 352
		jLabel.setBounds(59, 56, 77, 20);
		jLabel.setText("征税比例%");
		jLabel1.setBounds(59, 84, 77, 20);
		jLabel1.setText("境内目的地");
		jLabel2.setBounds(59, 112, 77, 20);
		jLabel2.setText("成交方式");
		jLabel3.setBounds(59, 142, 77, 20);
		jLabel3.setText("包装种类");
		jLabel4.setBounds(59, 171, 77, 20);
		jLabel4.setText("车牌号码");
		jLabel6.setBounds(283, 56, 105, 21);
		jLabel6.setText("申报日期");
		jLabel7.setBounds(283, 84, 105, 21);
		jLabel7.setText("起运国（运抵国）");
		jLabel8.setBounds(283, 112, 105, 21);
		jLabel8.setText("装货港");
		jLabel9.setBounds(283, 142, 105, 21);
		jLabel9.setText("许可证号");
		jLabel10.setBounds(283, 171, 105, 21);
		jLabel10.setText("征免性质");
		this.add(jLabel, null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(jLabel3, null);
		//this.add(jLabel4, null);
		this.add(getCbbDomesticDestinationOrSource(), null);
		this.add(getCbbTransac(), null);
		this.add(getCbbWrapType(), null);
		//this.add(getTfConveyance(), null);
		this.add(jLabel6, null);
		this.add(jLabel7, null);
		this.add(jLabel8, null);
		this.add(jLabel9, null);
		//this.add(jLabel10, null);
		this.add(getCbbDeclarationDate(), null);
		this.add(getCbbCountryOfLoadingOrUnloading(), null);
		this.add(getCbbPortOfLoadingOrUnloading(), null);
		//this.add(getCbbLevyKind(), null);
		this.add(getTfLicense(), null);
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
			cbbDomesticDestinationOrSource.setBounds(140, 84, 127, 21);
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
			cbbTransac.setBounds(140, 112, 127, 21);
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
			cbbWrapType.setBounds(140, 142, 127, 21);
			cbbWrapType.setEditable(true);
		}
		return cbbWrapType;
	}

	/**
	 * This method initializes tfConveyance
	 * 
	 * @return javax.swing.JTextField
	 */
//	private JTextField getTfConveyance() {
//		if (tfConveyance == null) {
//			tfConveyance = new JTextField();
//			tfConveyance.setBounds(140, 171, 127, 21);
//		}
//		return tfConveyance;
//	}

	/**
	 * This method initializes cbbImpExpDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDeclarationDate() {
		if (cbbDeclarationDate == null) {
			cbbDeclarationDate = new JCalendarComboBox();
			cbbDeclarationDate.setBounds(399, 56, 112, 21);
		}
		return cbbDeclarationDate;
	}

	/**
	 * This method initializes cbbCountryOfLoadingOrUnloading
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountryOfLoadingOrUnloading() {
		if (cbbCountryOfLoadingOrUnloading == null) {
			cbbCountryOfLoadingOrUnloading = new JComboBox();
			cbbCountryOfLoadingOrUnloading.setBounds(399, 84, 112, 21);
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
			cbbPortOfLoadingOrUnloading.setBounds(399, 112, 112, 21);
			cbbPortOfLoadingOrUnloading.setEditable(true);
		}
		return cbbPortOfLoadingOrUnloading;
	}

	/**
	 * This method initializes cbbLevyKind
	 * 
	 * @return javax.swing.JComboBox
	 */
//	private JComboBox getCbbLevyKind() {
//		if (cbbLevyKind == null) {
//			cbbLevyKind = new JComboBox();
//			cbbLevyKind.setBounds(399, 171, 112, 21);
//			cbbLevyKind.setEditable(true);
//		}
//		return cbbLevyKind;
//	}

	/**
	 * This method initializes tfLicense
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLicense() {
		if (tfLicense == null) {
			tfLicense = new JTextField();
			tfLicense.setBounds(399, 142, 112, 21);
		}
		return tfLicense;
	}

	private void initUIComponents() {
		// 初始化征免性质
//		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
//				.getLevyKindModel());
//		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
//				this.cbbLevyKind);
//		this.cbbLevyKind
//				.setRenderer(CustomBaseRender.getInstance().getRender());
//		this.cbbLevyKind.setSelectedIndex(-1);
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
		this.cbbDomesticDestinationOrSource.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDomesticDestinationOrSource);
		// this.cbbDomesticDestinationOrSource.setSelectedIndex(-1);
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
//		//初始化客户
//		for (int i = 0; i < parentHead.size(); i++) {
//			FptBillHead fp= (FptBillHead) parentHead.get(i);
//			if(fp.getCustomer()!=null){
//				paramScoId=fp.getCustomer().getId();
//			}
//			
//		}
		List list = fptManageAction.findScmManuFactory(new Request(
				CommonVars.getCurrUser(), true),paramScoId);
		
//		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
//		cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender("name",
//				"name", 200, 0));
	}

	private void showInitData() {
//		 if (cbbCustomer != null) {
//			this.cbbCountryOfLoadingOrUnloading.setSelectedItem(cbbCustomer
//					.getCountry());
//			this.cbbPortOfLoadingOrUnloading.setSelectedItem(cbbCustomer
//					.getPortLin());
//			this.cbbWrapType.setSelectedItem(cbbCustomer.getWarp());
//		}

		
	}

	private void fillData() {

		this.makeCusomsDeclarationParam
				.setDelcarationDate(this.cbbDeclarationDate.getDate());

//		this.makeCusomsDeclarationParam.setConveyance(this.tfConveyance
//				.getText().trim());

//		if (this.cbbLevyKind.getSelectedItem() != null) {
//			this.makeCusomsDeclarationParam
//					.setLevyKind((LevyKind) this.cbbLevyKind.getSelectedItem());
//		}
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
//		 this.makeCusomsDeclarationParam.setCustomer((ScmCoc)cbbCustomer.getSelectedItem());
//		 this.makeCusomsDeclarationParam
//		 .setSpecialCustomsDeclaration(this.cbIsSpecialCustomsDeclaration
//		 .isSelected());
	}

	/**
	 * This method initializes tfPerTax
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPerTax() {
		if (tfPerTax == null) {
			tfPerTax = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPerTax.setBounds(140, 55, 127, 23);
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

	public List getParentHead() {
		return parentHead;
	}

	public void setParentHead(List parentHead) {
		this.parentHead = parentHead;
	}

} // @jve:decl-index=0:visual-constraint="48,17"

