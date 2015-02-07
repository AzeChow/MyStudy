package com.bestway.customs.common.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationContainer;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsExg;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.BaseEmsImg;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationContainer;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.google.gson.Gson;

/**
 * 报关单导入逻辑
 * 
 * @author Administrator
 * 
 */
public class EncImportLogic {

	private BaseEncDao baseEncDao = null;

	public BaseEncDao getBaseEncDao() {
		return baseEncDao;
	}

	public void setBaseEncDao(BaseEncDao baseEncDao) {
		this.baseEncDao = baseEncDao;
	}

	// /**
	// * 判断是进口报关单还是出口报关单
	// *
	// * @param json
	// */
	// public void chooseImportOrExport(String json) {
	// Gson gson = new Gson();
	// List list = (List) gson.fromJson(json, List.class);
	// for (int i = 0; i < list.size(); i++) {
	// Map map = (Map) list.get(i);
	// Map<String, String> bgdhead = (Map<String, String>) map
	// .get("报关单表头");
	// if (null != bgdhead.get("起运国") && !bgdhead.get("起运国").isEmpty()) {
	// importQp(json);
	// }
	// if (bgdhead.get("运抵国").isEmpty() && bgdhead.get("起运国").isEmpty()) {
	// throw new RuntimeException("运抵国或起运国不能为空");
	// }
	// }
	// for (int i = 0; i < list.size(); i++) {
	// Map map = (Map) list.get(i);
	// Map<String, String> bgdhead = (Map<String, String>) map
	// .get("报关单表头");
	// if (null != bgdhead.get("运抵国") && !bgdhead.get("运抵国").isEmpty()) {
	// importBcsExportQp(json);
	// }
	// if (bgdhead.get("运抵国").isEmpty() && bgdhead.get("起运国").isEmpty()) {
	// throw new RuntimeException("运抵国或起运国不能为空");
	// }
	// }
	// }

	/**
	 * 从QP导入进口报关单
	 * 
	 * @param json
	 * @param impExpFlag
	 * @param projectType
	 */
	public void importQp(String json, Integer impExpFlag, Integer projectType) {
		if (json == null || "".equals(json.trim())) {
			throw new RuntimeException("进口报关单内容为空");
		}

		if (projectType != null) {
			this.baseEncDao.setProjectType(projectType);
		}

		Map<String, Complex> ComplexMap = initCustomsBase("Complex", "code");
		Map<String, CustomsComplex> CustomsComplexMap = initCustomsBase(
				"CustomsComplex", "code");
		Map<String, Unit> MapUnit = initCustomsBase("Unit", "name");
		Map<String, LevyMode> mapLevyMode = initCustomsBase("LevyMode", "name");
		Gson gson = new Gson();
		Map map = (Map) gson.fromJson(json, Map.class);
		Map<String, String> bgdhead = (Map<String, String>) map.get("报关单表头");// 报关单表头
		BaseCustomsDeclaration head = this.importbgdhead(bgdhead, impExpFlag,
				projectType);
		List<Map<String, String>> SFDZ = (List<Map<String, String>>) map
				.get("随附单证");// 随附单证
		if (SFDZ != null && SFDZ.size() > 0) {
			this.importbgdsfdz(head, SFDZ);
		}
		List<Map<String, String>> bgdItems = (List<Map<String, String>>) map
				.get("报关单表体");// 报关单表体
		Map<Integer, String> mapSpec = getCommSpec(projectType, head);
		for (int j = 0; j < bgdItems.size(); j++) {
			BaseCustomsDeclarationCommInfo bgdBaseCustomsDeclarationCommInfo = null;
			Map<String, String> bgdItem = bgdItems.get(j);
			if (projectType == ProjectType.BCUS) {
				bgdBaseCustomsDeclarationCommInfo = new CustomsDeclarationCommInfo();
			} else if (projectType == ProjectType.BCS) {
				bgdBaseCustomsDeclarationCommInfo = new BcsCustomsDeclarationCommInfo();
			} else if (projectType == ProjectType.DZSC) {
				bgdBaseCustomsDeclarationCommInfo = new DzscCustomsDeclarationCommInfo();

			}
			this.importbgditems(bgdItem, ComplexMap, CustomsComplexMap,
					bgdBaseCustomsDeclarationCommInfo, head, MapUnit,
					mapLevyMode, mapSpec);
		}
		if (bgdItems != null && bgdItems.size() > 0) {
			Map<String, String> bgdItem = bgdItems.get(0);
			head.setCurrency((Curr) this.baseEncDao.findCustomBaseEntity(
					"Curr", "name", bgdItem.get("币制").trim()));
			this.baseEncDao.saveOrUpdate(head);
		}
		List<Map<String, String>> JZXH = (List<Map<String, String>>) map
				.get("集装箱号");// 集装箱号
		if (JZXH == null) {
			JZXH = new ArrayList();
		}
		for (int k = 0; k < JZXH.size(); k++) {
			BaseCustomsDeclarationContainer bgdBaseCustomsDeclarationContainer = null;
			Map<String, String> jzxh = JZXH.get(k);
			if (projectType == ProjectType.BCUS) {
				bgdBaseCustomsDeclarationContainer = new CustomsDeclarationContainer();
			} else if (projectType == ProjectType.BCS) {
				bgdBaseCustomsDeclarationContainer = new BcsCustomsDeclarationContainer();
			} else if (projectType == ProjectType.DZSC) {
				bgdBaseCustomsDeclarationContainer = new DzscCustomsDeclarationContainer();
			}
			this.importbgdjzxh(jzxh, bgdBaseCustomsDeclarationContainer, head);
		}

	}

	/**
	 * 导入进口报关单表头
	 * 
	 * @param bgdhead
	 * @param impExpFlag
	 * @param projectType
	 * @return
	 */
	private BaseCustomsDeclaration importbgdhead(Map<String, String> bgdhead,
			Integer impExpFlag, Integer projectType) {
		// initTrademap();
		BaseCustomsDeclaration bgdBaseCustomsDeclaration = null;
		String ContractName = "";
		if (projectType == ProjectType.BCUS) {
			bgdBaseCustomsDeclaration = new CustomsDeclaration();
			ContractName = "EmsHeadH2k";
		} else if (projectType == ProjectType.BCS) {
			bgdBaseCustomsDeclaration = new BcsCustomsDeclaration();
			ContractName = "Contract";
		} else if (projectType == ProjectType.DZSC) {
			ContractName = "DzscEmsPorHead";
			bgdBaseCustomsDeclaration = new DzscCustomsDeclaration();
		}
		bgdBaseCustomsDeclaration.setImpExpFlag(impExpFlag);

		if (bgdhead == null) {
			throw new RuntimeException("从远程服务中抓取的报关单表头为空");
		}
		String value = bgdhead.get("备案号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			BaseEmsHead contract = (BaseEmsHead) this.baseEncDao
					.findCustomBaseEntity(ContractName, "emsNo", value);// 手册号
			if (contract != null) {
				bgdBaseCustomsDeclaration.setEmsHeadH2k(value);
			} else {
				throw new RuntimeException("系统中不存在" + value + "备案号!");
			}
		} else {
			impExpFlag = ImpExpFlag.SPECIAL;
			bgdBaseCustomsDeclaration.setImpExpFlag(ImpExpFlag.SPECIAL);
			// throw new RuntimeException("报关单号为:" + value + "的备案号为空!");
		}
		value = bgdhead.get("征税比例");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setPerTax(Double.valueOf(value));
		}
		value = bgdhead.get("报关单类型");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setTcsEntryType(value);
		}
		value = bgdhead.get("经营单位编码");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setTradeCode(value);
		}
		value = bgdhead.get("备注");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setMemos(value);
		}
		value = bgdhead.get("随附单证");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setAttachedBill(value);
		}
		value = bgdhead.get("集装箱号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setContainerNum(value);
		}
		value = bgdhead.get("净重");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setNetWeight(Double.valueOf(value));
		}
		value = bgdhead.get("毛重");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setGrossWeight(Double.valueOf(value));
		}
		value = bgdhead.get("包装种类");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setWrapType((Wrap) this.baseEncDao
					.findCustomBaseEntity("Wrap", "name", value));
		}
		value = bgdhead.get("件数");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setCommodityNum(Integer.parseInt(value));
		}
		value = bgdhead.get("成交方式");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setTransac((Transac) this.baseEncDao
					.findCustomBaseEntity("Transac", "name", value));
		}
		value = bgdhead.get("监管方式");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setTradeMode((Trade) this.baseEncDao
					.findCustomBaseEntity("Trade", "name", value));
			if (value.equals("一般贸易")) {
				value = value + "进口";
			}
			bgdBaseCustomsDeclaration.setImpExpType(MapTrade.getImpExpType(
					value, impExpFlag));
		}
		value = bgdhead.get("批准文号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setAuthorizeFile(value);
		}
		value = bgdhead.get("装货港");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			PortLin portLin = (PortLin) this.baseEncDao.findCustomBaseEntity(
					"PortLin", "name", value);
			bgdBaseCustomsDeclaration.setPortOfLoadingOrUnloading(portLin);
		}
		value = bgdhead.get("境内目的地");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration
					.setDomesticDestinationOrSource((District) this.baseEncDao
							.findCustomBaseEntity("District", "name", value));
		}
		value = bgdhead.get("启运国");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration
					.setCountryOfLoadingOrUnloading((Country) this.baseEncDao
							.findCustomBaseEntity("Country", "name", value));
		}
		value = bgdhead.get("征免性质");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setLevyKind((LevyKind) this.baseEncDao
					.findCustomBaseEntity("LevyKind", "name", value));
		}
		value = bgdhead.get("提运单号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setBillOfLading(value);
		}
		value = bgdhead.get("运输工具名称");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setConveyance(value);
		}
		value = bgdhead.get("运输方式");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setTransferMode((Transf) this.baseEncDao
					.findCustomBaseEntity("Transf", "name", value));
		}
		if (bgdhead.get("申报日期") != null
				&& !bgdhead.get("申报日期").trim().isEmpty()) {
			bgdBaseCustomsDeclaration.setDeclarationDate(CommonUtils
					.convertStrToDate(bgdhead.get("申报日期").substring(0, 4)
							+ "-"
							+ bgdhead.get("申报日期").substring(4, 6)
							+ "-"
							+ bgdhead.get("申报日期").substring(6,
									bgdhead.get("申报日期").length())));
		}
		if (bgdhead.get("进出口日期") != null
				&& !bgdhead.get("进出口日期").trim().isEmpty()) {
			bgdBaseCustomsDeclaration.setImpExpDate(CommonUtils
					.convertStrToDate(bgdhead.get("进出口日期").substring(0, 4)
							+ "-"
							+ bgdhead.get("进出口日期").substring(4, 6)
							+ "-"
							+ bgdhead.get("进出口日期").substring(6,
									bgdhead.get("进出口日期").length())));
		}
		value = bgdhead.get("合同协议号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setContract(value);
		}
		value = bgdhead.get("进出口口岸");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setCustoms((Customs) this.baseEncDao
					.findCustomBaseEntity("Customs", "name", value));
		}
		value = bgdhead.get("预录入编号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setPreCustomsDeclarationCode(value);
		}
		value = bgdhead.get("统一编号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setUnificationCode(value);
		}
		value = bgdhead.get("许可证编号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setLicense(value);
		}
		value = bgdhead.get("关联报关单");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setRelativeId(value);
		}
		value = bgdhead.get("报关员");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setCustomser(value);
		}

		if (bgdhead.get("杂费3") != null && bgdhead.get("杂费2") != null
				&& !bgdhead.get("杂费3").trim().isEmpty()
				&& !bgdhead.get("杂费2").trim().isEmpty()) {
			if (bgdhead.get("杂费3").equals("率")) {
				bgdBaseCustomsDeclaration
						.setIncidentalExpensesType(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_RATE);
				bgdBaseCustomsDeclaration.setIncidentalExpenses(Double
						.valueOf(bgdhead.get("杂费2")));
			} else if (bgdhead.get("杂费3").equals("总价")
					&& bgdhead.get("杂费1") != null
					&& !bgdhead.get("杂费1").trim().isEmpty()) {
				bgdBaseCustomsDeclaration
						.setIncidentalExpensesType(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_TOTALPRICE);
				bgdBaseCustomsDeclaration.setIncidentalExpenses(Double
						.valueOf(bgdhead.get("杂费2")));
				bgdBaseCustomsDeclaration.setOtherCurr((Curr) this.baseEncDao
						.findCustomBaseEntity("Curr", "name", bgdhead
								.get("杂费3").trim()));
			}
		}
		if (bgdhead.get("保费3") != null && bgdhead.get("保费2") != null
				&& !bgdhead.get("保费3").trim().isEmpty()
				&& !bgdhead.get("保费2").trim().isEmpty()) {
			if (bgdhead.get("保费3").equals("率")) {
				bgdBaseCustomsDeclaration
						.setInsuranceType(BaseCustomsDeclaration.INSURANCE_RATE);
				bgdBaseCustomsDeclaration.setInsurance(Double.valueOf(bgdhead
						.get("保费2")));
			} else if (bgdhead.get("保费3").equals("总价")
					&& bgdhead.get("保费1") != null
					&& !bgdhead.get("保费1").trim().isEmpty()) {
				bgdBaseCustomsDeclaration
						.setInsuranceType(BaseCustomsDeclaration.INSURANCE_TOTALPRICE);
				bgdBaseCustomsDeclaration.setInsurance(Double.valueOf(bgdhead
						.get("保费2")));
				bgdBaseCustomsDeclaration.setInsurCurr((Curr) this.baseEncDao
						.findCustomBaseEntity("Curr", "name", bgdhead
								.get("保费3").trim()));
			}
		}
		if (bgdhead.get("运费3") != null && bgdhead.get("运费2") != null
				&& !bgdhead.get("运费3").trim().isEmpty()
				&& !bgdhead.get("运费2").trim().isEmpty()) {
			if (bgdhead.get("运费3").equals("率")) {
				bgdBaseCustomsDeclaration
						.setFreightageType(BaseCustomsDeclaration.FREIGHT_RATE);
				bgdBaseCustomsDeclaration.setFreightage(Double.valueOf(bgdhead
						.get("运费2")));
			} else if (bgdhead.get("运费3").equals("单价")
					&& bgdhead.get("运费1") != null
					&& !bgdhead.get("运费1").trim().isEmpty()) {
				bgdBaseCustomsDeclaration
						.setFreightageType(BaseCustomsDeclaration.FREIGHT_UNITPRICE);
				bgdBaseCustomsDeclaration.setFreightage(Double.valueOf(bgdhead
						.get("运费2")));
				bgdBaseCustomsDeclaration.setFeeCurr((Curr) this.baseEncDao
						.findCustomBaseEntity("Curr", "name", bgdhead
								.get("运费3").trim()));
			} else if (bgdhead.get("运费3").equals("总价")
					&& bgdhead.get("运费1") != null
					&& !bgdhead.get("运费1").trim().isEmpty()) {
				bgdBaseCustomsDeclaration
						.setFreightageType(BaseCustomsDeclaration.FREIGHT_TOTALPRICE);
				bgdBaseCustomsDeclaration.setFreightage(Double.valueOf(bgdhead
						.get("运费2")));
				bgdBaseCustomsDeclaration.setFeeCurr((Curr) this.baseEncDao
						.findCustomBaseEntity("Curr", "name", bgdhead
								.get("运费3").trim()));
			}

		}
		value = bgdhead.get("收货单位编码");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setMachCode(value);
		}

		// 收货单位如果是个空值那么就直接取 当前公司的名字作为收货单位
		value = bgdhead.get("收货单位名称");
		value = (value == null ? CommonUtils.getCompany().getName() : value
				.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setMachName(value);
		}
		// if (bgdhead.get("经营单位3") != null
		// && !bgdhead.get("经营单位3").trim().isEmpty()) {
		// bgdBaseCustomsDeclaration.setTradeCode(bgdhead.get("经营单位3"));
		// }
		value = bgdhead.get("经营单位名称");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setTradeName(value);
		}
		Company declarationCompany = null;
		if (bgdhead.get("申报单位编码") != null
				&& !bgdhead.get("申报单位编码").trim().isEmpty()) {
			List<Company> list = this.baseEncDao.findCompany(bgdhead
					.get("申报单位编码"));
			if (list.size() > 0) {
				declarationCompany = list.get(0);
				bgdBaseCustomsDeclaration
						.setDeclarationCompany(declarationCompany);
			}
			// bgdBaseCustomsDeclaration.getDeclarationCompany().setBuCode(
			// bgdhead.get("申报单位名称"));
		}
		// if (bgdhead.get("申报单位编码") != null
		// && !bgdhead.get("申报单位编码").trim().isEmpty()) {
		// this.baseEncDao.findCompany(bgdhead.get("申报单位编码"));
		// bgdBaseCustomsDeclaration.getDeclarationCompany().setBuName(
		// bgdhead.get("申报单位编码"));
		// }
		value = bgdhead.get("海关编号");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration.setCustomsDeclarationCode(value);
		}

		value = bgdhead.get("报送海关");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			bgdBaseCustomsDeclaration
					.setDeclarationCustoms((Customs) this.baseEncDao
							.findCustomBaseEntity("Customs", "name", value));
		}
		if (bgdhead.get("航次号") != null && !bgdhead.get("航次号").trim().isEmpty()) {
			bgdBaseCustomsDeclaration.setVoyageNo(bgdhead.get("航次号").trim());
		}
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>" +
		// bgdBaseCustomsDeclaration);
		bgdBaseCustomsDeclaration.setSerialNumber(this.baseEncDao
				.getCustomsDeclarationSerialNumber(
						bgdBaseCustomsDeclaration.getImpExpFlag(),
						bgdBaseCustomsDeclaration.getEmsHeadH2k()));
		this.baseEncDao.saveCustomsDeclaration(bgdBaseCustomsDeclaration);
		return bgdBaseCustomsDeclaration;
	}

	/**
	 * 导入进口报关单表体
	 * 
	 * @param bgditem
	 * @param bcsComplexMap
	 * @param bcsCustomsComplexMap
	 * @param info
	 * @param head
	 */

	private void importbgditems(Map<String, String> bgditem,
			Map<String, Complex> bcsComplexMap,
			Map<String, CustomsComplex> bcsCustomsComplexMap,
			BaseCustomsDeclarationCommInfo info, BaseCustomsDeclaration head,
			Map<String, Unit> mapUnit, Map<String, LevyMode> mapLevyMode,
			Map<Integer, String> mapSpec) {
		String exceptionMessger = null;
		info.setBaseCustomsDeclaration(head);
		String value = bgditem.get("商品名称");
		value = (value == null ? "" : value.trim());
		if (!value.isEmpty()) {
			info.setCommName(bgditem.get("商品名称").toString());
		}
		if (bgditem.get("原产地") != null
				&& !bgditem.get("原产地").toString().trim().isEmpty()) {
			info.setCountry((Country) this.baseEncDao.findCustomBaseEntity(
					"Country", "name", bgditem.get("原产地").toString().trim()));
		}
		if (bgditem.get("用途") != null
				&& !bgditem.get("用途").toString().trim().isEmpty()) {
			info.setUses((Uses) this.baseEncDao.findCustomBaseEntity("Uses",
					"name", bgditem.get("用途").toString().trim()));
		}
		if (bgditem.get("货号") != null
				&& !bgditem.get("货号").toString().trim().isEmpty()) {
			info.setMaterielNo(bgditem.get("货号").toString());
		}
		if (bgditem.get("版本号") != null
				&& !bgditem.get("版本号").toString().trim().isEmpty()) {
			info.setVersion(bgditem.get("版本号").toString());
		}
		if (bgditem.get("法定单位") != null
				&& !bgditem.get("法定单位").toString().trim().isEmpty()) {
			info.setLegalUnit(mapUnit.get(bgditem.get("法定单位").trim()));
		}
		if (bgditem.get("备案序号") != null
				&& !bgditem.get("备案序号").toString().trim().isEmpty()) {
			info.setCommSerialNo(Integer.parseInt(bgditem.get("备案序号")
					.toString()));
		}
		if (bgditem.get("规格型号") != null
				&& !bgditem.get("规格型号").toString().trim().isEmpty()) {
			info.setDeclareSpec(bgditem.get("规格型号").toString());
		}
		if (info.getCommSerialNo() != null
				&& mapSpec.get(info.getCommSerialNo()) != null) {
			info.setCommSpec(mapSpec.get(info.getCommSerialNo()));
		}

		if (bgditem.get("商品编号") != null && bgditem.get("附加编号") != null
				&& !bgditem.get("商品编号").toString().trim().isEmpty()
				&& !bgditem.get("附加编号").toString().trim().isEmpty()) {
			Complex complex = getComplexByCode(bcsComplexMap,
					bcsCustomsComplexMap, bgditem.get("商品编号").toString().trim()
							+ bgditem.get("附加编号").toString().trim());
			if (complex == null) {
				exceptionMessger += exceptionMessger == null ? bgditem
						.get("商品编号").toString().trim()
						+ bgditem.get("附加编号").toString().trim() : ","
						+ bgditem.get("商品编号").toString().trim()
						+ bgditem.get("附加编号").toString().trim();
			}
			info.setComplex(complex);

		}
		if (bgditem.get("第二单位") != null
				&& !bgditem.get("第二单位").toString().trim().isEmpty()) {
			info.setSecondLegalUnit(mapUnit.get(bgditem.get("第二单位").trim()));
		}
		if (bgditem.get("第二数量") != null
				&& !bgditem.get("第二数量").trim().isEmpty()) {
			info.setSecondAmount(Double.valueOf(bgditem.get("第二数量")));
		}
		if (bgditem.get("法定数量") != null
				&& !bgditem.get("法定数量").trim().isEmpty()) {
			info.setFirstAmount(Double.valueOf(bgditem.get("法定数量")));
		}
		if (bgditem.get("成交总价") != null
				&& !bgditem.get("成交总价").trim().isEmpty()) {
			info.setCommTotalPrice(Double.valueOf(bgditem.get("成交总价")));
		}
		if (bgditem.get("成交单价") != null
				&& !bgditem.get("成交单价").trim().isEmpty()) {
			info.setCommUnitPrice(Double.valueOf(bgditem.get("成交单价")));
		}
		if (bgditem.get("成交单位") != null
				&& !bgditem.get("成交单位").toString().trim().isEmpty()) {
			info.setUnit(mapUnit.get(bgditem.get("成交单位").trim()));
		}
		if (bgditem.get("成交数量") != null
				&& !bgditem.get("成交数量").trim().isEmpty()) {
			info.setCommAmount(Double.valueOf(bgditem.get("成交数量")));
		}
		if (bgditem.get("征免") != null && !bgditem.get("征免").trim().isEmpty()) {
			info.setLevyMode(mapLevyMode.get(bgditem.get("征免").trim()));
		}
		info.setSerialNumber(this.baseEncDao
				.getCustomsDeclarationCommInfoSerialNumber(info
						.getBaseCustomsDeclaration().getId()));
		this.baseEncDao.saveCustomsDeclarationCommInfo(info);
	}

	/**
	 * 导入进口报关单随附单证
	 * 
	 * @param baseCustomsDeclaration
	 * @param sfdz
	 */
	private void importbgdsfdz(BaseCustomsDeclaration baseCustomsDeclaration,
			List<Map<String, String>> sfdz) {
		StringBuffer codes = new StringBuffer();
		if (sfdz == null || sfdz.size() == 0 || sfdz.get(0) == null) {
			baseCustomsDeclaration.setCertificateCode("");
		} else {
			Map<String, String> mapImg = (Map) sfdz.get(0);
			boolean has = false;
			if (mapImg.get("代码") != null && !mapImg.get("代码").trim().isEmpty()) {
				codes.append(mapImg.get("代码").trim() + ":");
				has = true;
			}
			if (mapImg.get("随附单证编号") != null
					&& !mapImg.get("随附单证编号").trim().isEmpty()) {
				codes.append(mapImg.get("随附单证编号").trim());
				has = true;
			}
			if (has) {
				codes.append(";");
			}
			baseCustomsDeclaration.setCertificateCode(codes.toString().trim());
		}
		this.baseEncDao.saveCustomsDeclaration(baseCustomsDeclaration);
	}

	/**
	 * 导入进口报关单集装箱号
	 * 
	 * @param jzxh
	 * @param bgdBaseCustomsDeclarationContainer
	 * @param head
	 */

	private void importbgdjzxh(Map<String, String> jzxh,
			BaseCustomsDeclarationContainer bgdBaseCustomsDeclarationContainer,
			BaseCustomsDeclaration head) {
		bgdBaseCustomsDeclarationContainer.setBaseCustomsDeclaration(head);

		if (jzxh.get("集装箱号") != null && !jzxh.get("集装箱号").trim().isEmpty()) {
			bgdBaseCustomsDeclarationContainer.setContainerCode(jzxh
					.get("集装箱号"));
		}
		if (jzxh.get("规格") != null && !jzxh.get("规格").trim().isEmpty()) {
			if (jzxh.get("规格").equals("L")) {
				bgdBaseCustomsDeclarationContainer
						.setSrtJzx((SrtJzx) this.baseEncDao
								.findCustomBaseEntity("SrtJzx", "name", jzxh
										.get("规格").trim()));
			}
			if (jzxh.get("规格").equals("S")) {
				bgdBaseCustomsDeclarationContainer
						.setSrtJzx((SrtJzx) this.baseEncDao
								.findCustomBaseEntity("SrtJzx", "name", jzxh
										.get("规格").trim()));
			}
		}
		this.baseEncDao
				.saveCustomsDeclarationContainer(bgdBaseCustomsDeclarationContainer);

	}

	private Map initCustomsBase(String entityClassName, String keyFieldName) {
		Map resultMap = new HashMap();
		List list = this.baseEncDao.findCustomBaseEntityList(entityClassName);
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			String key = null;
			try {
				key = (String) PropertyUtils.getProperty(obj, keyFieldName);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (key != null) {
				resultMap.put(key, obj);

			}
		}
		return resultMap;
	}

	private Complex getComplexByCode(Map<String, Complex> complexMap,
			Map<String, CustomsComplex> customsComplexMap, String complexCode) {
		Complex complex = complexMap.get(complexCode);
		if (complex != null) {
			return complex;
		} else {
			CustomsComplex customsComplex = customsComplexMap.get(complexCode);
			if (customsComplex != null) {
				complex = new Complex();
				try {
					PropertyUtils.copyProperties(complex, customsComplex);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				complex.setId(customsComplex.getCode());
				this.baseEncDao.saveOrUpdate(complex);
				complexMap.put(complexCode, complex);
				return complex;
			} else {
				return null;
			}
		}
	}

	/**
	 * 从QP 导入出口报关单
	 * 
	 * @param json
	 * @return
	 */
	public List importExportQp(String json, Integer impExpFlag,
			Integer projectType) {
		if (projectType != null) {
			this.baseEncDao.setProjectType(projectType);
		}

		if (json == null || "".equals(json.trim())) {
			throw new RuntimeException("出口报关单内容为空");
		}
		Map<String, Complex> complexMap = initCustomsBase("Complex", "code");
		Map<String, CustomsComplex> customsComplexMap = initCustomsBase(
				"CustomsComplex", "code");
		Map<String, Unit> mapUnit = initCustomsBase("Unit", "name");
		Map<String, LevyMode> mapLevyMode = initCustomsBase("LevyMode", "name");
		Gson gson = new Gson();
		Map map = (Map) gson.fromJson(json, Map.class);
		Map<String, String> bgdhead = (Map<String, String>) map.get("报关单表头");// 报关单表头
		BaseCustomsDeclaration head = this.importExportbgdHead(bgdhead,
				impExpFlag, projectType);

		List<Map<String, String>> bgdItems = (List<Map<String, String>>) map
				.get("报关单表体");// 报关单表体
		Map<Integer, String> mapSpec = getCommSpec(projectType, head);
		for (int j = 0; j < bgdItems.size(); j++) {
			BaseCustomsDeclarationCommInfo bgdBaseExportCustomsDeclarationCommInfo = null;
			Map<String, String> bgdItem = bgdItems.get(j);
			if (projectType == ProjectType.BCUS) {
				bgdBaseExportCustomsDeclarationCommInfo = new CustomsDeclarationCommInfo();
			} else if (projectType == ProjectType.BCS) {
				bgdBaseExportCustomsDeclarationCommInfo = new BcsCustomsDeclarationCommInfo();
			} else if (projectType == ProjectType.DZSC) {
				bgdBaseExportCustomsDeclarationCommInfo = new DzscCustomsDeclarationCommInfo();
			}
			this.importExportbgdItems(bgdItem, complexMap, customsComplexMap,
					bgdBaseExportCustomsDeclarationCommInfo, head, mapUnit,
					mapLevyMode, mapSpec);
		}

		if (bgdItems != null && bgdItems.size() > 0) {
			Map<String, String> bgdItem = bgdItems.get(0);
			head.setCurrency((Curr) this.baseEncDao.findCustomBaseEntity(
					"Curr", "name", bgdItem.get("币制").trim()));
			this.baseEncDao.saveOrUpdate(head);
		}

		List<Map<String, String>> SFDZ = (List<Map<String, String>>) map
				.get("随附单证");// 随附单证
		if (SFDZ != null && SFDZ.size() > 0) {
			this.importExportbgdsfdz(head, SFDZ);
		}

		List<Map<String, String>> JZXH = (List<Map<String, String>>) map
				.get("集装箱号");// 集装箱号
		if (JZXH != null) {
			for (int k = 0; k < JZXH.size(); k++) {
				BaseCustomsDeclarationContainer bgdBaseExportCustomsDeclarationContainer = null;
				Map<String, String> jzxh = JZXH.get(k);
				if (projectType == ProjectType.BCUS) {
					bgdBaseExportCustomsDeclarationContainer = new CustomsDeclarationContainer();
				} else if (projectType == ProjectType.BCS) {
					bgdBaseExportCustomsDeclarationContainer = new BcsCustomsDeclarationContainer();
				} else if (projectType == ProjectType.DZSC) {
					bgdBaseExportCustomsDeclarationContainer = new DzscCustomsDeclarationContainer();
				}
				this.importExportbgdjzxh(jzxh,
						bgdBaseExportCustomsDeclarationContainer, head);
			}
		}
		return null;
	}

	/**
	 * 导入出口报关单表头
	 * 
	 * @param bgdhead
	 * @return
	 */
	private BaseCustomsDeclaration importExportbgdHead(
			Map<String, String> bgdhead, Integer impExpFlag, Integer projectType) {
		BaseCustomsDeclaration bgdBaseExportCustomsDeclaration = null;
		String ContractName = "";
		if (projectType == ProjectType.BCUS) {
			bgdBaseExportCustomsDeclaration = new CustomsDeclaration();
			ContractName = "EmsHeadH2k";
		} else if (projectType == ProjectType.BCS) {
			bgdBaseExportCustomsDeclaration = new BcsCustomsDeclaration();
			ContractName = "Contract";
		} else if (projectType == ProjectType.DZSC) {
			ContractName = "DzscEmsPorHead";
			bgdBaseExportCustomsDeclaration = new DzscCustomsDeclaration();
		}
		bgdBaseExportCustomsDeclaration.setImpExpFlag(impExpFlag);
		if (bgdhead == null) {
			throw new RuntimeException("从远程服务中抓取的报关单表头为空");
		}
		String emsHeadH2k = bgdhead.get("备案号");
		if (emsHeadH2k != null && !emsHeadH2k.trim().isEmpty()) {
			BaseEmsHead contract = (BaseEmsHead) this.baseEncDao
					.findCustomBaseEntity(ContractName, "emsNo", emsHeadH2k);
			if (contract != null) {
				bgdBaseExportCustomsDeclaration.setEmsHeadH2k(emsHeadH2k);
			} else {
				throw new RuntimeException("系统中不存在" + emsHeadH2k + "备案号");
			}
		} else {
			impExpFlag = ImpExpFlag.SPECIAL;
			bgdBaseExportCustomsDeclaration.setImpExpFlag(ImpExpFlag.SPECIAL);
			// throw new RuntimeException("备案号为空!");
		}
		if (bgdhead.get("报关单类型") != null
				&& !bgdhead.get("报关单类型").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setTcsEntryType(bgdhead
					.get("报关单类型"));
		}
		if (bgdhead.get("征税比例") != null
				&& !bgdhead.get("征税比例").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setPerTax(Double.valueOf(bgdhead
					.get("征税比例")));
		}
		if (bgdhead.get("经营单位") != null
				&& !bgdhead.get("经营单位").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setTradeCode(bgdhead.get("经营单位"));
		}
		if (bgdhead.get("备注") != null && !bgdhead.get("备注").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setMemos(bgdhead.get("备注"));
		}
		if (bgdhead.get("随附单证") != null
				&& !bgdhead.get("随附单证").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setAttachedBill(bgdhead.get("随附单证"));
		}
		if (bgdhead.get("集装箱号") != null
				&& !bgdhead.get("集装箱号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setContainerNum(bgdhead.get("集装箱号"));
		}
		if (bgdhead.get("净重") != null && !bgdhead.get("净重").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setNetWeight(Double.valueOf(bgdhead
					.get("净重")));
		}
		if (bgdhead.get("毛重") != null && !bgdhead.get("毛重").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setGrossWeight(Double
					.valueOf(bgdhead.get("毛重")));
		}
		if (bgdhead.get("监管方式") != null
				&& !bgdhead.get("监管方式").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setTradeMode((Trade) this.baseEncDao.findCustomBaseEntity(
							"Trade", "name", bgdhead.get("监管方式").trim()));
			String impExpType = bgdhead.get("监管方式");
			if (impExpType.equals("一般贸易")) {
				impExpType = impExpType + "出口";
			}
			bgdBaseExportCustomsDeclaration.setImpExpType(MapTrade
					.getImpExpType(impExpType, impExpFlag));
		}
		if (bgdhead.get("杂费3") != null && bgdhead.get("杂费2") != null
				&& !bgdhead.get("杂费3").trim().isEmpty()
				&& !bgdhead.get("杂费2").trim().isEmpty()) {
			if (bgdhead.get("杂费3").equals("率")) {
				bgdBaseExportCustomsDeclaration
						.setIncidentalExpensesType(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_RATE);
				bgdBaseExportCustomsDeclaration.setIncidentalExpenses(Double
						.valueOf(bgdhead.get("杂费2")));
			} else if (bgdhead.get("杂费3").equals("总价")
					&& bgdhead.get("杂费1") != null
					&& !bgdhead.get("杂费1").trim().isEmpty()) {
				bgdBaseExportCustomsDeclaration
						.setIncidentalExpensesType(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_TOTALPRICE);
				bgdBaseExportCustomsDeclaration.setIncidentalExpenses(Double
						.valueOf(bgdhead.get("杂费2")));
				bgdBaseExportCustomsDeclaration
						.setOtherCurr((Curr) this.baseEncDao
								.findCustomBaseEntity("Curr", "name", bgdhead
										.get("杂费3").trim()));
			}
		}
		if (bgdhead.get("保费3") != null && bgdhead.get("保费2") != null
				&& !bgdhead.get("保费3").trim().isEmpty()
				&& !bgdhead.get("保费2").trim().isEmpty()) {
			if (bgdhead.get("保费3").equals("率")) {
				bgdBaseExportCustomsDeclaration
						.setInsuranceType(BaseCustomsDeclaration.INSURANCE_RATE);
				bgdBaseExportCustomsDeclaration.setInsurance(Double
						.valueOf(bgdhead.get("保费2")));
			} else if (bgdhead.get("保费3").equals("总价")
					&& bgdhead.get("保费1") != null
					&& !bgdhead.get("保费1").trim().isEmpty()) {
				bgdBaseExportCustomsDeclaration
						.setInsuranceType(BaseCustomsDeclaration.INSURANCE_TOTALPRICE);
				bgdBaseExportCustomsDeclaration.setInsurance(Double
						.valueOf(bgdhead.get("保费2")));
				bgdBaseExportCustomsDeclaration
						.setInsurCurr((Curr) this.baseEncDao
								.findCustomBaseEntity("Curr", "name", bgdhead
										.get("保费3").trim()));
			}
		}
		if (bgdhead.get("运费3") != null && bgdhead.get("运费2") != null
				&& !bgdhead.get("运费3").trim().isEmpty()
				&& !bgdhead.get("运费2").trim().isEmpty()) {
			if (bgdhead.get("运费3").equals("率")) {
				bgdBaseExportCustomsDeclaration
						.setFreightageType(BaseCustomsDeclaration.FREIGHT_RATE);
				bgdBaseExportCustomsDeclaration.setFreightage(Double
						.valueOf(bgdhead.get("运费2")));
			} else if (bgdhead.get("运费3").equals("单价")
					&& bgdhead.get("运费1") != null
					&& !bgdhead.get("运费1").trim().isEmpty()) {
				bgdBaseExportCustomsDeclaration
						.setFreightageType(BaseCustomsDeclaration.FREIGHT_UNITPRICE);
				bgdBaseExportCustomsDeclaration.setFreightage(Double
						.valueOf(bgdhead.get("运费2")));
				bgdBaseExportCustomsDeclaration
						.setFeeCurr((Curr) this.baseEncDao
								.findCustomBaseEntity("Curr", "name", bgdhead
										.get("运费3").trim()));
			} else if (bgdhead.get("运费3").equals("总价")
					&& bgdhead.get("运费1") != null
					&& !bgdhead.get("运费1").trim().isEmpty()) {
				bgdBaseExportCustomsDeclaration
						.setFreightageType(BaseCustomsDeclaration.FREIGHT_TOTALPRICE);
				bgdBaseExportCustomsDeclaration.setFreightage(Double
						.valueOf(bgdhead.get("运费2")));
				bgdBaseExportCustomsDeclaration
						.setFeeCurr((Curr) this.baseEncDao
								.findCustomBaseEntity("Curr", "name", bgdhead
										.get("运费3").trim()));
			}
		}
		if (bgdhead.get("包装种类") != null
				&& !bgdhead.get("包装种类").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setWrapType((Wrap) this.baseEncDao
					.findCustomBaseEntity("Wrap", "name", bgdhead.get("包装种类")
							.trim()));
		}
		if (bgdhead.get("件数") != null && !bgdhead.get("件数").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setCommodityNum(Integer
					.parseInt(bgdhead.get("件数")));
		}
		if (bgdhead.get("成交方式") != null
				&& !bgdhead.get("成交方式").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setTransac((Transac) this.baseEncDao.findCustomBaseEntity(
							"Transac", "name", bgdhead.get("成交方式").trim()));
		}
		if (bgdhead.get("监管方式") != null
				&& !bgdhead.get("监管方式").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setTradeMode((Trade) this.baseEncDao.findCustomBaseEntity(
							"Trade", "name", bgdhead.get("监管方式").trim()));
		}
		if (bgdhead.get("批准文号") != null
				&& !bgdhead.get("批准文号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setAuthorizeFile(bgdhead
					.get("批准文号"));
		}
		if (bgdhead.get("指运港") != null && !bgdhead.get("指运港").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setPortOfLoadingOrUnloading((PortLin) this.baseEncDao
							.findCustomBaseEntity("PortLin", "name", bgdhead
									.get("指运港").trim()));
		}
		if (bgdhead.get("境内货源地") != null
				&& !bgdhead.get("境内货源地").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setDomesticDestinationOrSource((District) this.baseEncDao
							.findCustomBaseEntity("District", "name", bgdhead
									.get("境内货源地").trim()));
		}
		if (bgdhead.get("运抵国") != null && !bgdhead.get("运抵国").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setCountryOfLoadingOrUnloading((Country) this.baseEncDao
							.findCustomBaseEntity("Country", "name", bgdhead
									.get("运抵国").trim()));
		}
		if (bgdhead.get("征免性质") != null
				&& !bgdhead.get("征免性质").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setLevyKind((LevyKind) this.baseEncDao
							.findCustomBaseEntity("LevyKind", "name", bgdhead
									.get("征免性质").trim()));
		}
		if (bgdhead.get("提运单号") != null
				&& !bgdhead.get("提运单号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setBillOfLading(bgdhead.get("提运单号"));
		}
		Company declarationCompany = null;
		if (bgdhead.get("申报单位编码") != null
				&& !bgdhead.get("申报单位编码").trim().isEmpty()) {
			List<Company> list = this.baseEncDao.findCompany(bgdhead
					.get("申报单位编码"));
			// if (null != list || !list.isEmpty() || list.get(0) != null) {
			if (list.size() > 0) {
				declarationCompany = list.get(0);
				bgdBaseExportCustomsDeclaration
						.setDeclarationCompany(declarationCompany);
			}
		}
		if (bgdhead.get("运输工具名称") != null
				&& !bgdhead.get("运输工具名称").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setConveyance(bgdhead.get("运输工具名称"));
		}
		if (bgdhead.get("收货单位编码") != null
				&& !bgdhead.get("收货单位编码").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setMachCode(bgdhead.get("收货单位编码"));
		}
		if (bgdhead.get("收货单位名称") != null
				&& !bgdhead.get("收货单位名称").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setMachName(bgdhead.get("收货单位名称"));
		}
		if (bgdhead.get("运输方式") != null
				&& !bgdhead.get("运输方式").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setTransferMode((Transf) this.baseEncDao
							.findCustomBaseEntity("Transf", "name", bgdhead
									.get("运输方式").trim()));
		}
		if (bgdhead.get("经营单位名称") != null
				&& !bgdhead.get("经营单位名称").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setTradeName(bgdhead.get("经营单位名称"));
		}
		if (bgdhead.get("申报日期") != null
				&& !bgdhead.get("申报日期").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setDeclarationDate(CommonUtils
					.convertStrToDate(bgdhead.get("申报日期").substring(0, 4)
							+ "-"
							+ bgdhead.get("申报日期").substring(4, 6)
							+ "-"
							+ bgdhead.get("申报日期").substring(6,
									bgdhead.get("申报日期").length())));
		}
		if (bgdhead.get("进出口日期") != null
				&& !bgdhead.get("进出口日期").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setImpExpDate(CommonUtils
					.convertStrToDate(bgdhead.get("进出口日期").substring(0, 4)
							+ "-"
							+ bgdhead.get("进出口日期").substring(4, 6)
							+ "-"
							+ bgdhead.get("进出口日期").substring(6,
									bgdhead.get("进出口日期").length())));
		}
		if (bgdhead.get("合同协议号") != null
				&& !bgdhead.get("合同协议号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setContract(bgdhead.get("合同协议号"));
		}
		if (bgdhead.get("进出口口岸") != null
				&& !bgdhead.get("进出口口岸").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setCustoms((Customs) this.baseEncDao.findCustomBaseEntity(
							"Customs", "name", bgdhead.get("进出口口岸").trim()));
		}
		if (bgdhead.get("海关编号") != null
				&& !bgdhead.get("海关编号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setCustomsDeclarationCode(bgdhead
					.get("海关编号"));
		}
		if (bgdhead.get("预录入编号") != null
				&& !bgdhead.get("预录入编号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setPreCustomsDeclarationCode(bgdhead.get("预录入编号"));
		}
		if (bgdhead.get("统一编号") != null
				&& !bgdhead.get("统一编号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setUnificationCode(bgdhead
					.get("统一编号"));
		}
		if (bgdhead.get("结汇方式") != null
				&& !bgdhead.get("结汇方式").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setBalanceMode((BalanceMode) this.baseEncDao
							.findCustomBaseEntity("BalanceMode", "name",
									bgdhead.get("结汇方式").trim()));
		}
		if (bgdhead.get("许可证号") != null
				&& !bgdhead.get("许可证号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setLicense(bgdhead.get("许可证号"));
		}
		if (bgdhead.get("关联报关单") != null
				&& !bgdhead.get("关联报关单").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setRelativeId(bgdhead.get("关联报关单"));
		}
		if (bgdhead.get("报关员") != null && !bgdhead.get("报关员").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setCustomser(bgdhead.get("报关员"));
		}
		if (bgdhead.get("报送海关") != null
				&& !bgdhead.get("报送海关").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration
					.setDeclarationCustoms((Customs) this.baseEncDao
							.findCustomBaseEntity("Customs", "name", bgdhead
									.get("报送海关").trim()));
		}
		if (bgdhead.get("航次号") != null && !bgdhead.get("航次号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclaration.setVoyageNo(bgdhead.get("航次号")
					.trim());
		}

		bgdBaseExportCustomsDeclaration.setSerialNumber(this.baseEncDao
				.getCustomsDeclarationSerialNumber(
						bgdBaseExportCustomsDeclaration.getImpExpFlag(),
						bgdBaseExportCustomsDeclaration.getEmsHeadH2k()));
		this.baseEncDao.saveCustomsDeclaration(bgdBaseExportCustomsDeclaration);
		return bgdBaseExportCustomsDeclaration;
	}

	private void importExportbgdItems(Map<String, String> bgditem,
			Map<String, Complex> complexMap,
			Map<String, CustomsComplex> customsComplexMap,
			BaseCustomsDeclarationCommInfo info, BaseCustomsDeclaration head,
			Map<String, Unit> mapUnit, Map<String, LevyMode> mapLevyMode,
			Map<Integer, String> mapSpec) {
		String exceptionMessger = null;
		info.setBaseCustomsDeclaration(head);
		if (bgditem.get("商品名称") != null
				&& !bgditem.get("商品名称").toString().trim().isEmpty()) {
			info.setCommName(bgditem.get("商品名称").toString());
		}
		if (bgditem.get("目的地") != null
				&& !bgditem.get("目的地").toString().trim().isEmpty()) {
			info.setCountry((Country) this.baseEncDao.findCustomBaseEntity(
					"Country", "name", bgditem.get("目的地").toString().trim()));
		}
		if (bgditem.get("用途") != null
				&& !bgditem.get("用途").toString().trim().isEmpty()) {
			info.setUses((Uses) this.baseEncDao.findCustomBaseEntity("Uses",
					"name", bgditem.get("用途").toString().trim()));
		}
		if (bgditem.get("货号") != null
				&& !bgditem.get("货号").toString().trim().isEmpty()) {
			info.setMaterielNo(bgditem.get("货号").toString());
		}
		if (bgditem.get("版本号") != null
				&& !bgditem.get("版本号").toString().trim().isEmpty()) {
			info.setVersion(bgditem.get("版本号").toString());
		}
		if (bgditem.get("法定单位") != null
				&& !bgditem.get("法定单位").toString().trim().isEmpty()) {
			info.setLegalUnit(mapUnit.get(bgditem.get("法定单位").trim()));
		}

		if (bgditem.get("备案序号") != null
				&& !bgditem.get("备案序号").toString().trim().isEmpty()) {
			info.setCommSerialNo(Integer.parseInt(bgditem.get("备案序号")
					.toString()));
		}
		if (bgditem.get("规格型号") != null
				&& !bgditem.get("规格型号").toString().trim().isEmpty()) {
			info.setDeclareSpec(bgditem.get("规格型号").toString());
		}
		if (info.getCommSerialNo() != null
				&& mapSpec.get(info.getCommSerialNo()) != null) {
			info.setCommSpec(mapSpec.get(info.getCommSerialNo()));
		}

		if (bgditem.get("商品编号") != null && bgditem.get("附加编号") != null
				&& !bgditem.get("商品编号").toString().trim().isEmpty()
				&& !bgditem.get("附加编号").toString().trim().isEmpty()) {
			Complex complex = getComplexByCode(complexMap, customsComplexMap,
					bgditem.get("商品编号").toString().trim()
							+ bgditem.get("附加编号").toString().trim());
			if (complex == null) {
				exceptionMessger += exceptionMessger == null ? bgditem
						.get("商品编号").toString().trim()
						+ bgditem.get("附加编号").toString().trim() : ","
						+ bgditem.get("商品编号").toString().trim()
						+ bgditem.get("附加编号").toString().trim();
			}
			info.setComplex(complex);
		}
		if (bgditem.get("工缴费") != null && !bgditem.get("工缴费").trim().isEmpty()) {
			info.setWorkUsd(Double.valueOf(bgditem.get("工缴费")));
		}
		if (bgditem.get("第二单位") != null
				&& !bgditem.get("第二单位").toString().trim().isEmpty()) {
			info.setSecondLegalUnit(mapUnit.get(bgditem.get("第二单位").trim()));
		}
		if (bgditem.get("第二数量") != null
				&& !bgditem.get("第二数量").trim().isEmpty()) {
			info.setSecondAmount(Double.valueOf(bgditem.get("第二数量")));
		}
		if (bgditem.get("法定数量") != null
				&& !bgditem.get("法定数量").trim().isEmpty()) {
			info.setFirstAmount(Double.valueOf(bgditem.get("法定数量")));
		}
		if (bgditem.get("成交总价") != null
				&& !bgditem.get("成交总价").trim().isEmpty()) {
			info.setCommTotalPrice(Double.valueOf(bgditem.get("成交总价")));
		}
		if (bgditem.get("成交单价") != null
				&& !bgditem.get("成交单价").trim().isEmpty()) {
			info.setCommUnitPrice(Double.valueOf(bgditem.get("成交单价")));
		}
		if (bgditem.get("成交单位") != null
				&& !bgditem.get("成交单位").toString().trim().isEmpty()) {
			info.setUnit(mapUnit.get(bgditem.get("成交单位").trim()));
		}
		if (bgditem.get("成交数量") != null
				&& !bgditem.get("成交数量").trim().isEmpty()) {
			info.setCommAmount(Double.valueOf(bgditem.get("成交数量")));
		}
		if (bgditem.get("征免") != null && !bgditem.get("征免").trim().isEmpty()) {
			info.setLevyMode(mapLevyMode.get(bgditem.get("征免").trim()));
		}
		this.baseEncDao.saveCustomsDeclarationCommInfo(info);
		info.setSerialNumber(this.baseEncDao
				.getCustomsDeclarationCommInfoSerialNumber(info
						.getBaseCustomsDeclaration().getId()));
	}

	/**
	 * 导入出口报关单随附单证
	 * 
	 * @param sfdz
	 */

	private void importExportbgdsfdz(
			BaseCustomsDeclaration baseExportCustomsDeclaration,
			List<Map<String, String>> sfdz) {
		StringBuffer codes = new StringBuffer();
		if (sfdz.size() <= 0 || null == sfdz.get(0)) {
			// throw new RuntimeException("随附单证为空");
		} else {
			Map<String, String> mapImg = (Map) sfdz.get(0);
			boolean has = false;
			if (mapImg.get("代码") != null && !mapImg.get("代码").trim().isEmpty()) {
				codes.append(mapImg.get("代码").trim() + ":");
				has = true;
			}
			if (mapImg.get("随附单证编号") != null
					&& !mapImg.get("随附单证编号").trim().isEmpty()) {
				codes.append(mapImg.get("随附单证编号").trim());
				has = true;
			}
			if (has) {
				codes.append(";");
			}
			baseExportCustomsDeclaration.setCertificateCode(codes.toString()
					.trim());
			this.baseEncDao
					.saveCustomsDeclaration(baseExportCustomsDeclaration);
		}
	}

	/**
	 * 导入进口报关单集装箱号
	 * 
	 * @param jzxh
	 * @param SrtJzx
	 */

	private void importExportbgdjzxh(
			Map<String, String> jzxh,
			BaseCustomsDeclarationContainer bgdBaseExportCustomsDeclarationContainer,
			BaseCustomsDeclaration head) {
		bgdBaseExportCustomsDeclarationContainer
				.setBaseCustomsDeclaration(head);
		if (jzxh.get("集装箱号") != null && !jzxh.get("集装箱号").trim().isEmpty()) {
			bgdBaseExportCustomsDeclarationContainer.setContainerCode(jzxh
					.get("集装箱号"));
		}
		if (jzxh.get("规格") != null && !jzxh.get("规格").trim().isEmpty()) {
			if (jzxh.get("规格").equals("L")) {
				bgdBaseExportCustomsDeclarationContainer
						.setSrtJzx((SrtJzx) this.baseEncDao
								.findCustomBaseEntity("SrtJzx", "name", jzxh
										.get("规格").trim()));
			}
			if (jzxh.get("规格").equals("S")) {
				bgdBaseExportCustomsDeclarationContainer
						.setSrtJzx((SrtJzx) this.baseEncDao
								.findCustomBaseEntity("SrtJzx", "name", jzxh
										.get("规格").trim()));
			}
		}

		this.baseEncDao
				.saveCustomsDeclarationContainer(bgdBaseExportCustomsDeclarationContainer);
	}

	public Object transferImportDeclaration(String execResult,
			Integer impExpFlag, Integer projectType) {
		importQp(execResult, impExpFlag, projectType);
		return null;
	}

	public Object transferExportDeclaration(String execResult,
			Integer impExpFlag, Integer projectType) {
		importExportQp(execResult, impExpFlag, projectType);
		return null;
	}

	public List findCustomsDeclaration(Integer projectType, Date date,
			Integer impExpFlag) {
		return baseEncDao.findCustomsDeclaration(projectType, date, impExpFlag);
	}

	public List<BaseCustomsDeclaration> findCustomsDeclaration(
			Integer impExpFlag, Date beginDate, Date endDate,
			String customsDecCode, Integer projectType, String emsNo,
			List impExpTypeList) {
		return baseEncDao.findCustomsDeclaration(impExpFlag, beginDate,
				endDate, customsDecCode, projectType, emsNo, impExpTypeList);
	}

	public List<BaseCustomsDeclaration> findCustomsDeclaration(
			Integer impExpFlag, Date beginDate, Date endDate,
			String customsDecCode, Integer projectType, String emsNo) {
		return baseEncDao.findCustomsDeclaration(impExpFlag, beginDate,
				endDate, customsDecCode, projectType, emsNo);
	}

	public List findAllCustomsDeclaration(Integer projectType,
			Integer impExpFlag) {
		return baseEncDao.findAllCustomsDeclaration(projectType, impExpFlag);
	}

	public Map<Integer, String> getCommSpec(Integer projectType,
			BaseCustomsDeclaration bgdhead) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List list = baseEncDao.findContractHead(projectType, bgdhead);
		if (list != null && list.size() > 0) {
			BaseEmsHead baseContractHead = (BaseEmsHead) list.get(0);// 获取通关备案表头
			boolean boo = isMaterial(bgdhead.getImpExpType());// 判断是料件还是成品：true
																// 为料件 false 为成品
			List ls = null;
			if (boo) {
				ls = baseEncDao.findBaseEmsImg(projectType, baseContractHead);// 获取料架数据
			} else {
				ls = baseEncDao.findBaseEmsExg(projectType, baseContractHead);// 获取成品数据
			}
			System.out.println((boo == true ? "料件数量：" : "成品数量：") + ls.size());
			for (int i = 0; i < ls.size(); i++) {
				Integer key = null;
				String value = null;
				if (boo) {
					if (ls.get(i) instanceof BaseEmsImg) {
						BaseEmsImg baseEmsImg = (BaseEmsImg) ls.get(i);
						key = baseEmsImg.getSeqNum();
						value = baseEmsImg.getSpec();
					} else {
						DzscEmsImgBill emsImg = (DzscEmsImgBill) ls.get(i);
						key = emsImg.getSeqNum();
						value = emsImg.getSpec();
					}
				} else {
					if (ls.get(i) instanceof BaseEmsExg) {
						BaseEmsExg baseEmsExg = (BaseEmsExg) ls.get(i);
						key = baseEmsExg.getSeqNum();
						value = baseEmsExg.getSpec();
					} else {
						DzscEmsExgBill emsExg = (DzscEmsExgBill) ls.get(i);
						key = emsExg.getSeqNum();
						value = emsExg.getSpec();
					}
				}
				if (map.get(key) == null) {
					map.put(key, value);
				}
			}
		}

		return map;
	}

	/**
	 * 是料件还是成品
	 * 
	 * @param impExpType
	 * @return
	 */
	public boolean isMaterial(int impExpType) {
		boolean isMaterial = false;
		int materielType = getMaterielTypeByBillType(impExpType);
		if (materielType == Integer.parseInt(MaterielType.MATERIEL)) {
			isMaterial = true;
		} else if (materielType == Integer
				.parseInt(MaterielType.FINISHED_PRODUCT)) {
			isMaterial = false;
		}
		return isMaterial;
	}

	/**
	 * 获得料件成品标识来自进出口申请单类型
	 */
	public int getMaterielTypeByBillType(int billType) {
		int temp = Integer.parseInt(MaterielType.MATERIEL);
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
		case ImpExpType.REMAIN_FORWARD_EXPORT:
			temp = Integer.parseInt(MaterielType.MATERIEL);
			break;
		case ImpExpType.BACK_FACTORY_REWORK:
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.GENERAL_TRADE_EXPORT:
			temp = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			break;
		}
		return temp;
	}
}
