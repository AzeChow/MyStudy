/*
 * Created on 2004-6-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import javax.swing.DefaultComboBoxModel;

/**
 * @author 001
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomBaseModel {
	private static CustomBaseModel	customBaseModel	= null;
	private static CustomBaseList	customBaseList	= null;

	public static CustomBaseModel getInstance() {
		if (customBaseModel == null) {
			customBaseModel = new CustomBaseModel();
			customBaseList = CustomBaseList.getInstance();
			initModels();
		}
		return customBaseModel;
	}

	private static DefaultComboBoxModel	investModeModel		= null;
	private static DefaultComboBoxModel	coTypeModel			= null;
	private static DefaultComboBoxModel	invClassModel		= null;
	private static DefaultComboBoxModel	machiningTypeModel	= null;
	private static DefaultComboBoxModel	dModeModel			= null;
//	private static DefaultComboBoxModel	briefModel			= null;
	private static DefaultComboBoxModel	countryModel		= null;
	private static DefaultComboBoxModel	customModel			= null;
	private static DefaultComboBoxModel	portInternalModel	= null;
	private static DefaultComboBoxModel	portLinModel		= null;
	private static DefaultComboBoxModel	preDockModel		= null;
	private static DefaultComboBoxModel	redDepModel			= null;
	private static DefaultComboBoxModel	saicCodeModel		= null;
	private static DefaultComboBoxModel	stsCodeModel		= null;
	private static DefaultComboBoxModel	taxCodeModel		= null;
	private static DefaultComboBoxModel	classifyModel		= null;
	private static DefaultComboBoxModel	complexModel		= null;
	private static DefaultComboBoxModel	licenseNoteModel	= null;
	private static DefaultComboBoxModel	contaModelModel		= null;
	private static DefaultComboBoxModel	contaSizeModel		= null;
	private static DefaultComboBoxModel	currModel			= null;
	private static DefaultComboBoxModel	licenseDocuModel	= null;
	private static DefaultComboBoxModel	payModeModel		= null;
	private static DefaultComboBoxModel	payTypeModel		= null;
	private static DefaultComboBoxModel	payerTypeModel		= null;
	private static DefaultComboBoxModel	srtJzxModel			= null;
	private static DefaultComboBoxModel	srtTjModel			= null;
	private static DefaultComboBoxModel	tradeModel			= null;
	private static DefaultComboBoxModel	transacModel		= null;
	private static DefaultComboBoxModel	transfModel			= null;
	private static DefaultComboBoxModel	unitModel			= null;
	private static DefaultComboBoxModel	useModel			= null;
	private static DefaultComboBoxModel	wrapModel			= null;
	private static DefaultComboBoxModel	levymodeModel		= null;
	private static DefaultComboBoxModel	balanceModeModel	= null; // 结汇方式
	private static DefaultComboBoxModel	fetchInModel		= null; // 引进方式
	private static DefaultComboBoxModel	districtModelModel	= null; // 地区代码
	private static DefaultComboBoxModel	customsbrokerModelModel	= null; // 报关行

	private static void initModels() {
		investModeModel = new DefaultComboBoxModel(customBaseList
				.getInvestModes().toArray());
		coTypeModel = new DefaultComboBoxModel(customBaseList.getCoTypes()
				.toArray());
		invClassModel = new DefaultComboBoxModel(customBaseList.getInvClasses()
				.toArray());
		machiningTypeModel = new DefaultComboBoxModel(customBaseList
				.getMachiningTypes().toArray());
		dModeModel = new DefaultComboBoxModel(customBaseList.getDModes()
				.toArray());
//		briefModel = new DefaultComboBoxModel(customBaseList.getBriefs()
//				.toArray());
		countryModel = new DefaultComboBoxModel(customBaseList.getCountrys()
				.toArray());
		customModel = new DefaultComboBoxModel(customBaseList.getCustoms()
				.toArray());
		portInternalModel = new DefaultComboBoxModel(customBaseList
				.getPortInternals().toArray());
		portLinModel = new DefaultComboBoxModel(customBaseList.getPortLins()
				.toArray());
		preDockModel = new DefaultComboBoxModel(customBaseList.getPreDocks()
				.toArray());
		redDepModel = new DefaultComboBoxModel(customBaseList.getRedDeps()
				.toArray());
		saicCodeModel = new DefaultComboBoxModel(customBaseList.getSaicCodes()
				.toArray());
		stsCodeModel = new DefaultComboBoxModel(customBaseList.getStsCodes()
				.toArray());
		taxCodeModel = new DefaultComboBoxModel(customBaseList.getTaxCodes()
				.toArray());
//		complexModel = new DefaultComboBoxModel(customBaseList.getComplex()
//				.toArray());
		licenseNoteModel = new DefaultComboBoxModel(customBaseList
				.getLicensenotes().toArray());
		contaModelModel = new DefaultComboBoxModel(customBaseList
				.getContaModels().toArray());
		contaSizeModel = new DefaultComboBoxModel(customBaseList
				.getContaSizes().toArray());
		currModel = new DefaultComboBoxModel(customBaseList.getCurrs()
				.toArray());
		licenseDocuModel = new DefaultComboBoxModel(customBaseList
				.getLicensedocus().toArray());
		payModeModel = new DefaultComboBoxModel(customBaseList.getPayModes()
				.toArray());
		payTypeModel = new DefaultComboBoxModel(customBaseList.getPayTypes()
				.toArray());
		payerTypeModel = new DefaultComboBoxModel(customBaseList
				.getPayerTypes().toArray());
		srtJzxModel = new DefaultComboBoxModel(customBaseList.getSrtJzxs()
				.toArray());
		srtTjModel = new DefaultComboBoxModel(customBaseList.getSrtTjs()
				.toArray());
		tradeModel = new DefaultComboBoxModel(customBaseList.getTrades()
				.toArray());
		transacModel = new DefaultComboBoxModel(customBaseList.getTransacs()
				.toArray());
		transfModel = new DefaultComboBoxModel(customBaseList.getTransfs()
				.toArray());
		unitModel = new DefaultComboBoxModel(customBaseList.getUnits()
				.toArray());
		useModel = new DefaultComboBoxModel(customBaseList.getUses().toArray());
		wrapModel = new DefaultComboBoxModel(customBaseList.getWraps()
				.toArray());
		levymodeModel = new DefaultComboBoxModel(customBaseList.getLevymode()
				.toArray());
		balanceModeModel = new DefaultComboBoxModel(customBaseList
				.getBalanceMode().toArray());
		fetchInModel = new DefaultComboBoxModel(customBaseList
				.getFetchInModel().toArray());
		districtModelModel = new DefaultComboBoxModel(customBaseList
				.getDistrict().toArray());
		customsbrokerModelModel = new DefaultComboBoxModel(customBaseList.
				getCustomsBbroker().toArray());
	}

	/**
	 * 结汇方式
	 */
	public DefaultComboBoxModel getBalanceModeModel() {
		return balanceModeModel;
	}

	public DefaultComboBoxModel getCoTypeModel() {
		return new DefaultComboBoxModel(customBaseList.getCoTypes().toArray());
	}

	public DefaultComboBoxModel getInvClassModel() {
		return new DefaultComboBoxModel(customBaseList.getInvClasses()
				.toArray());
	}

	public DefaultComboBoxModel getMachiningTypeModel() {
		return new DefaultComboBoxModel(customBaseList.getMachiningTypes()
				.toArray());
	}

	public DefaultComboBoxModel getDModeModel() {
		return new DefaultComboBoxModel(customBaseList.getDModes().toArray());
	}

//	public DefaultComboBoxModel getBriefModel() {
//		return new DefaultComboBoxModel(customBaseList.getBriefs().toArray());
//	}

	public DefaultComboBoxModel getCountryModel() {
		return new DefaultComboBoxModel(customBaseList.getCountrys().toArray());
	}

	public DefaultComboBoxModel getCustomModel() {
		return new DefaultComboBoxModel(customBaseList.getCustoms().toArray());
	}

	public DefaultComboBoxModel getPortInternalModel() {
		return new DefaultComboBoxModel(customBaseList.getPortInternals()
				.toArray());
	}

	public DefaultComboBoxModel getPortLinModel() {
		return new DefaultComboBoxModel(customBaseList.getPortLins().toArray());
	}

	public DefaultComboBoxModel getPreDockModel() {
		return new DefaultComboBoxModel(customBaseList.getPreDocks().toArray());
	}

	public DefaultComboBoxModel getRedDepModel() {
		return new DefaultComboBoxModel(customBaseList.getRedDeps().toArray());
	}

	public DefaultComboBoxModel getSaicCodeModel() {
		return new DefaultComboBoxModel(customBaseList.getSaicCodes().toArray());
	}

	public DefaultComboBoxModel getStsCodeModel() {
		return new DefaultComboBoxModel(customBaseList.getStsCodes().toArray());
	}

	public DefaultComboBoxModel getTaxCodeModel() {
		return new DefaultComboBoxModel(customBaseList.getTaxCodes().toArray());
	}

//	public DefaultComboBoxModel getComplexModel() {
//		return new DefaultComboBoxModel(customBaseList.getComplex().toArray());
//	}

	public DefaultComboBoxModel getLicensenoteModel() {
		return new DefaultComboBoxModel(customBaseList.getLicensenotes()
				.toArray());
	}

	public DefaultComboBoxModel getContaModelModel() {
		return new DefaultComboBoxModel(customBaseList.getContaModels()
				.toArray());
	}

	public DefaultComboBoxModel getContaSizeModel() {
		return new DefaultComboBoxModel(customBaseList.getContaSizes()
				.toArray());
	}

	public DefaultComboBoxModel getCurrModel() {
		return new DefaultComboBoxModel(customBaseList.getCurrs().toArray());
	}

	public DefaultComboBoxModel getLicenseDocuModel() {
		return new DefaultComboBoxModel(customBaseList.getLicensedocus()
				.toArray());
	}

	public DefaultComboBoxModel getPayModeModel() {
		return new DefaultComboBoxModel(customBaseList.getPayModes().toArray());
	}

	public DefaultComboBoxModel getPayTypeModel() {
		return new DefaultComboBoxModel(customBaseList.getPayTypes().toArray());
	}

	public DefaultComboBoxModel getPayerTypeModel() {
		return new DefaultComboBoxModel(customBaseList.getPayerTypes()
				.toArray());
	}

	public DefaultComboBoxModel getSrtJzxModel() {
		return new DefaultComboBoxModel(customBaseList.getSrtJzxs().toArray());
	}

	public DefaultComboBoxModel getSrtTjModel() {
		return new DefaultComboBoxModel(customBaseList.getSrtTjs().toArray());
	}

	public DefaultComboBoxModel getTradeModel() {
		return new DefaultComboBoxModel(customBaseList.getTrades().toArray());
	}

	public DefaultComboBoxModel getTransacModel() {
		return new DefaultComboBoxModel(customBaseList.getTransacs().toArray());
	}

	public DefaultComboBoxModel getTransfModel() {
		return new DefaultComboBoxModel(customBaseList.getTransfs().toArray());
	}

	public DefaultComboBoxModel getUnitModel() {
		return new DefaultComboBoxModel(customBaseList.getUnits().toArray());
	}

	public DefaultComboBoxModel getUseModel() {
		return new DefaultComboBoxModel(customBaseList.getUses().toArray());
	}

	public DefaultComboBoxModel getInvestModeModel() {
		return new DefaultComboBoxModel(customBaseList.getInvestModes()
				.toArray());
	}

	public DefaultComboBoxModel getLevymodeModel() {
		return new DefaultComboBoxModel(customBaseList.getLevymode().toArray());
	}

	public DefaultComboBoxModel getLevyKindModel() {
		return new DefaultComboBoxModel(customBaseList.getLevyKind().toArray());
	}

	public DefaultComboBoxModel getDistrictModelModel() {
		return new DefaultComboBoxModel(customBaseList.getDistrict().toArray());
	}
	public DefaultComboBoxModel getWrapModelModel() {
		return new DefaultComboBoxModel(customBaseList.getWraps().toArray());
	}
	public DefaultComboBoxModel getcustomsbrokerModel() {
		return new DefaultComboBoxModel(customBaseList.getCustomsBbroker().toArray());
	}
	public DefaultComboBoxModel getFetchInModel() {
		return fetchInModel;
	}

}
