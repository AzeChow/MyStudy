/*
 * Created on 2004-6-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.custombase.action.CustomBaseAction;

/**
 * @author wp // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomBaseList implements Runnable {
	private static CustomBaseList customBase = null;

	private List defauleList = new ArrayList();

	private CustomBaseList() {
		super();
	}

	public static CustomBaseList getInstance() {
		if (customBase == null) {
			customBase = new CustomBaseList();
		}
		return customBase;
	}

	private List investModes = defauleList;

	private List coTypes = defauleList;

	private List invClasses = defauleList;

	private List machiningTypes = defauleList;

	private List dModes = defauleList;

	private List countrys = defauleList;

	private List customs = defauleList;

	private List portInternals = defauleList;

	private List portLins = defauleList;

	private List preDocks = defauleList;

	private List redDeps = defauleList;

	private List saicCodes = defauleList;

	private List stsCodes = defauleList;

	private List taxCodes = defauleList;

	// private List Complexes = defauleList;

	private List licensenotes = defauleList;

	private List contaModels = defauleList;

	private List contaSizes = defauleList;

	private List currs = defauleList;

	private List licensedocus = defauleList;

	private List payModes = defauleList;

	private List payTypes = defauleList;

	private List payerTypes = defauleList;

	private List srtJzxs = defauleList;

	private List srtTjs = defauleList;

	private List trades = defauleList;

	private List transacs = defauleList;

	private List transfs = defauleList;

	private List units = defauleList;

	private List gbtobigs = defauleList;

	private List uses = defauleList;

	private List levymode = defauleList;

	private List levyKind = defauleList;

	private List wraps = defauleList;

	private List district = defauleList;

	private List balanceMode = defauleList; // 结汇方式

	private List bargainType = defauleList; // 合同类型

	// private List briefs = defauleList;

	private List fetchInModel = defauleList; // 引进方式

	private List deduc = defauleList; // 核扣方式

	private List customsBbroker = defauleList; // 报关行

	private List bList = defauleList;
	
	private List brokerCorps = defauleList;
	
	
	public List getBrokerCorps() {
		return brokerCorps = customBaseAction.findBrokerCorp("", "");
	}

	public void setBrokerCorps(List brokerCorps) {
		this.brokerCorps = brokerCorps;
	}

	public List getbList() {
		return bList;
	}

	public void setbList(List bList) {
		this.bList = bList;
	}

	private CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
			.getApplicationContext().getBean("customBaseAction");

	private void initData() { // 由run方法来调用

		gbtobigs = customBaseAction.findGbtobig("", "");

		investModes = customBaseAction.findInvestMode("", "");

		district = customBaseAction.findDistrict("", "");
		coTypes = customBaseAction.findCoType("", "");
		invClasses = customBaseAction.findInvClass("", "");
		machiningTypes = customBaseAction.findMachiningType("", "");
		dModes = customBaseAction.findDMode("", "");
		countrys = customBaseAction.findCountry("", "");
		customs = customBaseAction.findCustoms("", "");
		portInternals = customBaseAction.findPortInternal("", "");
		portLins = customBaseAction.findPortLin("", "");
		preDocks = customBaseAction.findPreDock("", "");
		redDeps = customBaseAction.findRedDep("", "");
		saicCodes = customBaseAction.findSaicCode("", "");
		stsCodes = customBaseAction.findStsCode("", "");
		taxCodes = customBaseAction.findTaxCode("", "");
		licensenotes = customBaseAction.findLicensenote("", "");
		contaModels = customBaseAction.findContaModel("", "");
		contaSizes = customBaseAction.findContaSize("", "");
		currs = customBaseAction.findCurr("", "");
		licensedocus = customBaseAction.findLicensedocu("", "");
		payModes = customBaseAction.findPayMode("", "");
		payTypes = customBaseAction.findPayType("", "");
		payerTypes = customBaseAction.findPayerType("", "");
		srtJzxs = customBaseAction.findSrtJzx("", "");
		srtTjs = customBaseAction.findSrtTj("", "");
		trades = customBaseAction.findTrade("", "");
		transacs = customBaseAction.findTransac("", "");
		transfs = customBaseAction.findTransf("", "");
		units = customBaseAction.findUnit("", "");
		uses = customBaseAction.findUses("", "");
		levymode = customBaseAction.findLevyMode("", "");
		levyKind = customBaseAction.findLevyKind("", "");
		wraps = customBaseAction.findWrap("", "");
		// Complexes = customBaseAction.findComplex("", "");
		balanceMode = customBaseAction.findBalanceMode("", "");
		bargainType = customBaseAction.findBargainType("", "");
		// briefs = customBaseAction.findBrief("", "");
		fetchInModel = customBaseAction.findFetchInMode("", "");
		deduc = customBaseAction.findDeduc("", "");
		customsBbroker = customBaseAction.findCustomsBroker("", "");
//		brokerCorps = customBaseAction.findBrokerCorp("", "");
//		bList = customBaseAction.findPageBriefList(new Request(CommonVars.getCurrUser()), 0, 100);
		// bList = customBaseAction.findPageBriefList(new
		// Request(CommonVars.getCurrUser()), 0, 100);
	}

	/**
	 * 结汇方式
	 */
	public List getBalanceMode() {
		return balanceMode;
	}

	// /**
	// * @return Returns the briefs.
	// */
	// public List getBriefs() {
	// return briefs;
	// }

	public List getContaModels() {
		return contaModels;
	}

	/**
	 * @return Returns the contaSizes.
	 */
	public List getContaSizes() {
		return contaSizes;
	}

	/**
	 * @return Returns the coTypes.
	 */
	public List getCoTypes() {
		return coTypes;
	}

	/**
	 * @return Returns the countrys.
	 */
	public List getCountrys() {
		return countrys;
	}

	/**
	 * @return Returns the currs.
	 */
	public List getCurrs() {
		return currs;
	}

	/**
	 * @return Returns the customs.
	 */
	public List getCustoms() {
		return customs;
	}

	/**
	 * @return Returns the dModes.
	 */
	public List getDModes() {
		return dModes;
	}

	/**
	 * @return Returns the invClasses.
	 */
	public List getInvClasses() {
		return invClasses;
	}

	/**
	 * @return Returns the investModes.
	 */
	public List getInvestModes() {
		return investModes;
	}

	/**
	 * @return Returns the licensedocus.
	 */
	public List getLicensedocus() {
		return licensedocus;
	}

	/**
	 * @return Returns the licensenotes.
	 */
	public List getLicensenotes() {
		return licensenotes;
	}

	/**
	 * @return Returns the machiningTypes.
	 */
	public List getMachiningTypes() {
		return machiningTypes;
	}

	/**
	 * @return Returns the payerTypes.
	 */
	public List getPayerTypes() {
		return payerTypes;
	}

	/**
	 * @return Returns the payModes.
	 */
	public List getPayModes() {
		return payModes;
	}

	/**
	 * @return Returns the payTypes.
	 */
	public List getPayTypes() {
		return payTypes;
	}

	/**
	 * @return Returns the portInternals.
	 */
	public List getPortInternals() {
		return portInternals;
	}

	/**
	 * @return Returns the portLins.
	 */
	public List getPortLins() {
		return portLins;
	}

	/**
	 * @return Returns the preDocks.
	 */
	public List getPreDocks() {
		return preDocks;
	}

	/**
	 * @return Returns the redDeps.
	 */
	public List getRedDeps() {
		return redDeps;
	}

	/**
	 * @return Returns the saicCodes.
	 */
	public List getSaicCodes() {
		return saicCodes;
	}

	/**
	 * @return Returns the srtJzxs.
	 */
	public List getSrtJzxs() {
		return srtJzxs;
	}

	/**
	 * @return Returns the srtTjs.
	 */
	public List getSrtTjs() {
		return srtTjs;
	}

	/**
	 * @return Returns the stsCodes.
	 */
	public List getStsCodes() {
		return stsCodes;
	}

	/**
	 * @return Returns the taxCodes.
	 */
	public List getTaxCodes() {
		return taxCodes;
	}

	/**
	 * @return Returns the trades.
	 */
	public List getTrades() {
		return trades;
	}

	/**
	 * @return Returns the transacs.
	 */
	public List getTransacs() {
		return transacs;
	}

	/**
	 * @return Returns the transfs.
	 */
	public List getTransfs() {
		return transfs;
	}

	/**
	 * @return Returns the units.
	 */
	public List getUnits() {
		return units;
	}

	/**
	 * @return Returns the uses.
	 */
	public List getUses() {
		return uses;
	}

	/**
	 * @return Returns the wraps.
	 */
	public List getWraps() {
		return wraps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		initData();
	}

	/**
	 * @return Returns the district.
	 */
	public List getDistrict() {
		return district;
	}

	/**
	 * @return Returns the levymode.
	 */
	public List getLevymode() {
		return levymode;
	}

	/**
	 * @param units
	 *            The units to set.
	 */
	public void setUnits(List units) {
		this.units = units;
	}

	/**
	 * @return Returns the levyKind.
	 */
	public List getLevyKind() {
		return levyKind;
	}

	public List getGbtobigs() {
		return gbtobigs;
	}

	/**
	 * @return Returns the bargainType.
	 */
	public List getBargainType() {
		return bargainType;
	}

	public List getFetchInModel() {
		return fetchInModel;
	}

	public void setFetchInModel(List fetchInModel) {
		this.fetchInModel = fetchInModel;
	}

	public List getDeduc() {
		return this.deduc;
	}

	public List getCustomsBbroker() {
		return this.customsBbroker;
	}

	/**
	 * 查找国别地区
	 * 
	 * @return Returns the countrys.
	 */
	// public synchronized List getWarps() {
	// if (wraps.isEmpty()) {
	// wraps = customBaseAction.findAllData(Wrap.class.getSimpleName());
	// }
	// return wraps;
	// }
}