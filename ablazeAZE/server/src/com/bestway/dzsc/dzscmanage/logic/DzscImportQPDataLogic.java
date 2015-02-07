package com.bestway.dzsc.dzscmanage.logic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DzscManageType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.LimitFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialExg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorMaterialImg;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.dzsc.qp.action.DzscQpServiceClient;
import com.bestway.dzsc.qp.entity.DzscQPEmsPorMaterialExg;
import com.bestway.dzsc.qp.entity.DzscQPEmsPorMaterialImg;
import com.google.gson.Gson;

public class DzscImportQPDataLogic {

	private DzscDao dzscDao = null;

	public DzscDao getDzscDao() {
		return dzscDao;
	}

	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	/**
	 * 从QP中查询电子手册合同备案表头
	 * 
	 * @return
	 */
	public List findDzscQPEmsPorWjHead() {
		DzscParameterSet paraSet = dzscDao.findDzscParameterSet();
		String machCode = ((Company) CommonUtils.getCompany()).getCode();
		if (machCode == null || "".equals(machCode.trim())) {
			throw new RuntimeException("公司的加工单位编码为空");
		}
		return DzscQpServiceClient.getInstance(paraSet).findDzscQPEmsPorWjHead(
				paraSet.getRemoteHostICPwd(), machCode, true);
	}

	/**
	 * 从QP中查询电子手册通关备案表头
	 * 
	 * @return
	 */
	public List findDzscQPEmsPorBillHead() {
		DzscParameterSet paraSet = dzscDao.findDzscParameterSet();
		String machCode = ((Company) CommonUtils.getCompany()).getCode();
		if (machCode == null || "".equals(machCode.trim())) {
			throw new RuntimeException("公司的加工单位编码为空");
		}
		return DzscQpServiceClient.getInstance(paraSet).findDzscQPEmsPorHead(
				paraSet.getRemoteHostICPwd(), machCode, true);
	}

	/**
	 * 从QP中导入电子手册通关备案
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importDzscEmsPorBillFromQP(String emsPorWjContent) {
		// for (int i = 0; i < listPtsEmsHead.size(); i++) {
		// DzscQPEmsPorBillHead dzscQPEmsPorHead = (DzscQPEmsPorBillHead)
		// listPtsEmsHead
		// .get(i);
		// String tradeCode = dzscQPEmsPorHead.getTradeCode();// 经营单位
		// String copEmsNo = dzscQPEmsPorHead.getCopTrNo();// 内部编号
		// DzscParameterSet paraSet = dzscDao.findDzscParameterSet();
		// TempQPEmsPorData tempQPEmsPorData = DzscQpServiceClient
		// .getInstance(paraSet).findTempQPEmsPorData(
		// paraSet.getRemoteHostICPwd(), tradeCode, copEmsNo,
		// true);// 根据 经营单位 ，内部编号 抓取单据资料
		// importDzscEmsPorBill(tempQPEmsPorData, isOverWrite);
		// }

		importDzscEmsPorBill(emsPorWjContent);
	}

	/**
	 * 从QP中导入电子手册通关备案
	 * 
	 * @param tempQPEmsPorData
	 */
	private void importDzscEmsPorBill(String emsPorWjContent) {
		long beginTime = System.currentTimeMillis();
		if (emsPorWjContent == null || "".equals(emsPorWjContent.trim())) {
			throw new RuntimeException("合同备案内容为空");
		}
		Map<String, Complex> complexMap = initCustomsBase("Complex", "code");
//		Map<String, DzscEmsExgBill> dzscEmsExgBillMap = initCustomsBase("DzscEmsExgBill", "code");
		Map<String, CustomsComplex> customsComplexMap = initCustomsBase(
				"CustomsComplex", "code");
		Map<String, Unit> mapUnit = initCustomsBase("Unit", "name");
		Map<String, LevyMode> mapLevyMode = initCustomsBase("LevyMode", "name");
		
		Map<String, Country> mapCountry = initCustomsBase("Country", "name");
		Gson gson = new Gson();
		Map headMap = (Map) gson.fromJson(emsPorWjContent, Map.class);
		Map<String, String> hthead = (Map) headMap.get("手册表头");// 表头
		List htchenping = (List) headMap.get("手册成品");// 成品
		List htdanhao = (List) headMap.get("手册单耗");// 单耗
		List htliaojian = (List) headMap.get("手册料件");// 合同料件
		List htkouan = (List) headMap.get("进出口岸");// 口岸

		if (hthead == null) {
			throw new RuntimeException("从远程服务中抓取的通关备案表头为空");
		}
		String emsNo = hthead.get("电子手册编号");
		if (emsNo == null||"".equals(emsNo.trim())) {
			throw new RuntimeException("从远程服务中抓取的通关备案的手册号为空");
		}
		Map<String,String> map = getDzscEmsExgBill(emsNo);
		DzscEmsPorHead dzscEmsPorHead = this.dzscDao
				.findExingDzscEmsPorHeadByEmsNo(emsNo);
		Date beginDate = null;
		//删除通关备案
		if(dzscEmsPorHead!=null){
			beginDate = dzscEmsPorHead.getBeginDate();
			deleteDzscEmsPorHead(dzscEmsPorHead);
		}
		System.out.println("准备数据用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		beginTime = System.currentTimeMillis();
		dzscEmsPorHead = this.importDzscEmsPorBillHead(hthead, htkouan,beginDate);
		System.out.println("导入表头用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		if(htliaojian!=null){
			beginTime = System.currentTimeMillis();
			this.importDzscEmsImgBill(htliaojian, dzscEmsPorHead, complexMap,
					customsComplexMap, mapUnit,mapCountry,mapLevyMode);
			System.out.println("导入料件用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		}
		if(htchenping!=null){
			beginTime = System.currentTimeMillis();
			this.importDzscEmsExgBill(map,htchenping, dzscEmsPorHead, complexMap,
					customsComplexMap, mapUnit,mapCountry,mapLevyMode);
			System.out.println("导入成品用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		}
		if(htdanhao!=null){
			beginTime = System.currentTimeMillis();
			this.importDzscEmsBomBill(htdanhao, dzscEmsPorHead);
			System.out.println("导入成品用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		}
		// if (tempQPEmsPorData.getPtsEmsListImg() != null) {
		// this.importDzscEmsPorMaterialImg(tempQPEmsPorData
		// .getPtsEmsListImg(), dzscEmsPorHead);
		// }
		// if (tempQPEmsPorData.getPtsEmsListExg() != null) {
		// this.importDzscEmsPorMaterialExg(tempQPEmsPorData
		// .getPtsEmsListExg(), dzscEmsPorHead);
		// }
	}
	private void deleteDzscEmsPorHead(DzscEmsPorHead head){
		this.dzscDao.deleteDzscEmsBomBill(head);
		this.dzscDao.deleteDzscEmsExgBill(head);
		this.dzscDao.deleteDzscEmsImgBill(head);
		this.dzscDao.deleteDzscEmsPorMaterialImg(head);
		this.dzscDao.deleteDzscEmsPorMaterialExg(head);
		this.dzscDao.deleteDzscEmsPorHead(head);
	}

	/**
	 * 根据商品编码编号获取自用商品编码对象，如果自用商品编码不存在，则从海关商品编码抓取，生成自用商品编码
	 * 
	 * @param complexCode
	 * @return
	 */
//	private Complex getComplex(String complexCode) {
//		Complex complex = (Complex) this.dzscDao.findCustomBaseEntity(
//				"Complex", "code", complexCode);
//		if (complex != null) {
//			return complex;
//		} else {
//			CustomsComplex customsComplex = (CustomsComplex) this.dzscDao
//					.findCustomBaseEntity("CustomsComplex", "code", complexCode);
//			if (customsComplex != null) {
//				complex = new Complex();
//				complex.setId(customsComplex.getCode());
//				complex.setCode(customsComplex.getCode());
//				complex.setName(customsComplex.getName());
//				complex.setIsOut(customsComplex.getIsOut());
//				complex.setNote(customsComplex.getNote());
//				complex.setFirstUnit(customsComplex.getFirstUnit());
//				complex.setSecondUnit(customsComplex.getSecondUnit());
//				complex.setLowrate(customsComplex.getLowrate());
//				complex.setHighrate(customsComplex.getHighrate());
//				this.dzscDao.saveOrUpdate(complex);
//				return complex;
//			} else {
//				return null;
//			}
//		}
//	}

	/**
	 * 导入通关备案表头
	 * 
	 * @param ptsEmsHead
	 */
	private DzscEmsPorHead importDzscEmsPorBillHead(Map<String, String> hthead,
			List htkouan,Date beginDate) {
		DzscEmsPorHead dzscEmsPorHead = new DzscEmsPorHead();
		dzscEmsPorHead.setSeqNum(Integer.parseInt(this.dzscDao.getNum(
				"DzscEmsPorHead", "seqNum")));
		String emsNo = hthead.get("电子手册编号").trim();
		if (emsNo != null && !emsNo.trim().isEmpty()) {
			dzscEmsPorHead.setEmsNo(emsNo);
		}
		// dzscEmsPorHead.setSancEmsNo(hthead.get("批准文号"));
		if (hthead.get("经营单位") != null && !hthead.get("经营单位").trim().isEmpty()) {
			dzscEmsPorHead.setTradeCode(hthead.get("经营单位"));
		}
		if (hthead.get("经营单位名称") != null
				&& !hthead.get("经营单位名称").trim().isEmpty()) {
			dzscEmsPorHead.setTradeName(hthead.get("经营单位名称"));
		}

		// dzscEmsPorHead.setMachCode(hthead.get("电子手册编号"));//-------------
		// dzscEmsPorHead.setMachName(hthead.get("电子手册编号"));//----------------
		if(beginDate!=null){
			dzscEmsPorHead.setBeginDate(beginDate);
		}else{
			if (hthead.get("录入日期") != null && !hthead.get("录入日期").trim().isEmpty()) {
				dzscEmsPorHead.setBeginDate(CommonUtils.convertStrToDate(hthead
						.get("录入日期").substring(0, 4)
						+ "-"
						+ hthead.get("录入日期").substring(4, 6)
						+ "-"
						+ hthead.get("录入日期").substring(6,
								hthead.get("录入日期").length())));
			}
		}
		
		if (hthead.get("有效日期") != null && !hthead.get("有效日期").trim().isEmpty()) {
			dzscEmsPorHead.setEndDate(CommonUtils.convertStrToDate(hthead.get(
					"有效日期").substring(0, 4)
					+ "-"
					+ hthead.get("有效日期").substring(4, 6)
					+ "-"
					+ hthead.get("有效日期").substring(6,
							hthead.get("有效日期").length())));
		}
		if (hthead.get("外商公司") != null && !hthead.get("外商公司").trim().isEmpty()) {
			dzscEmsPorHead.setOutTradeCo(hthead.get("外商公司"));
		}

		// dzscEmsPorHead.setLevyMode((LevyMode) this.dzscDao
		// .findCustomBaseEntity("LevyMode", "code",
		// hthead.get("电子手册编号")));//----------
		if (hthead.get("征免性质") != null && !hthead.get("征免性质").trim().isEmpty()) {
			dzscEmsPorHead.setLevyKind((LevyKind) this.dzscDao
					.findCustomBaseEntity("LevyKind", "name", hthead
							.get("征免性质").trim()));
		}
		if (hthead.get("保税方式") != null && !hthead.get("保税方式").trim().isEmpty()) {
			dzscEmsPorHead.setPayMode((PayMode) this.dzscDao
					.findCustomBaseEntity("PayMode", "name", hthead.get("保税方式")
							.trim()));
		}
		if (hthead.get("加工种类").trim() != null
				&& !hthead.get("加工种类").trim().isEmpty()) {
			dzscEmsPorHead.setMachiningType((MachiningType) this.dzscDao
					.findCustomBaseEntity("MachiningType", "name",
							hthead.get("加工种类").trim()));
		}
		if (hthead.get("批准文号") != null && !hthead.get("批准文号").trim().isEmpty()) {
			dzscEmsPorHead.setEmsApprNo(hthead.get("批准文号"));
		}
		if (hthead.get("收货地区").trim() != null
				&& !hthead.get("收货地区").trim().isEmpty()) {
			dzscEmsPorHead.setReceiveArea((District) this.dzscDao
					.findCustomBaseEntity("District", "name", hthead
							.get("收货地区").trim()));
		}
		// dzscEmsPorHead.setInvestMode((InvestMode) this.dzscDao
		// .findCustomBaseEntity("InvestMode", "code",
		// hthead.get("电子手册编号")));//-------------
		// dzscEmsPorHead.setFetchInMode((FetchInMode) this.dzscDao
		// .findCustomBaseEntity("FetchInMode", "code",
		// hthead.get("电子手册编号")));//-----------
		if (hthead.get("内销比") != null && !hthead.get("内销比").trim().isEmpty()) {
			dzscEmsPorHead.setInSaleRate(Double.valueOf(hthead.get("内销比")));
		}
		if (hthead.get("申报日期") != null && !hthead.get("申报日期").trim().isEmpty()) {
			// 申报时间
			dzscEmsPorHead.setDeclareDate(CommonUtils.convertStrToDate(hthead
					.get("申报日期").substring(0, 4)
					+ "-"
					+ hthead.get("申报日期").substring(4, 6)
					+ "-"
					+ hthead.get("申报日期").substring(6,
							hthead.get("申报日期").length())));
		}
		// 原料项目个数
		if (hthead.get("进口货物项数") != null
				&& !hthead.get("进口货物项数").trim().isEmpty()) {
			dzscEmsPorHead.setMaterielItemCount(Integer.parseInt(hthead
					.get("进口货物项数")));// ----------
		}
		// dzscEmsPorHead.setMachineCount(Integer.parseInt(hthead.get("本次进口总额")));//------
		if (hthead.get("出口货物项数") != null
				&& !hthead.get("出口货物项数").trim().isEmpty()) {
			dzscEmsPorHead.setProductItemCount(Integer.parseInt(hthead
					.get("出口货物项数")));// ----------
		}
		if (hthead.get("管理对象") != null && !hthead.get("管理对象").trim().isEmpty()) {
			if (hthead.get("管理对象").contains("经营单位")) {
				dzscEmsPorHead.setManageObject("0");
			}
			if (hthead.get("管理对象").contains("加工单位")) {
				dzscEmsPorHead.setManageObject("1");
			}
		}
		if (hthead.get("限制类标志") != null
				&& !hthead.get("限制类标志").trim().isEmpty()) {
			String limitFlag = null;
			if ("调整前旧手册".endsWith(hthead.get("限制类标志").trim())) {
				limitFlag = "1";
			} else if ("调整后新手册".endsWith(hthead.get("限制类标志").trim())) {
				limitFlag = "2";
			} else if ("台账专用手册".endsWith(hthead.get("限制类标志").trim())) {
				limitFlag = "3";
			}
			if (limitFlag != null) {
				dzscEmsPorHead.setLimitFlag(limitFlag);
			}
		}

		if (dzscEmsPorHead.getLimitFlag() == null
				|| "".equals(dzscEmsPorHead.getLimitFlag().trim())) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String beginTime = dateFormat.format(dzscEmsPorHead.getBeginDate());
			if (beginTime.compareTo("2008-08-23") < 0) {
				dzscEmsPorHead.setLimitFlag(LimitFlag.ADJUST_BEFORE_EMS);
			} else {
				dzscEmsPorHead.setLimitFlag(LimitFlag.ADJUST_AFTER_EMS);
			}
		}
		String equipMode = null;
		if ("备案".equals(hthead.get("单耗申报环节"))) {
			equipMode = "1";
		} else if ("出口前".equals(hthead.get("单耗申报环节"))) {
			equipMode = "2";
		} else if ("报核前".equals(hthead.get("单耗申报环节"))) {
			equipMode = "3";
		}
		if (equipMode != null) {
			dzscEmsPorHead.setEquipMode(equipMode);
		}
		if (hthead.get("主管海关") != null && !hthead.get("主管海关").trim().isEmpty()) {
			dzscEmsPorHead
					.setCustomNo((Customs) this.dzscDao.findCustomBaseEntity(
							"Customs", "name", hthead.get("主管海关")));
		}
		if (hthead.get("主管外经贸") != null
				&& !hthead.get("主管外经贸").trim().isEmpty()) {
			dzscEmsPorHead
					.setRedDep((RedDep) this.dzscDao.findCustomBaseEntity(
							"RedDep", "name", hthead.get("主管外经贸")));
		}
		// if(hthead.get("手册类型")!=null&&!hthead.get("手册类型").trim().isEmpty()){
		// dzscEmsPorHead.setEmsType(hthead.get("手册类型"));
		// }
		if (hthead.get("手册类型") != null && !hthead.get("手册类型").trim().isEmpty()) {
			String emsType = null;
			if ("加工贸易来料手册".equals(hthead.get("手册类型"))) {
				emsType = "B";
			} else if ("加工贸易进料手册".equals(hthead.get("手册类型"))) {
				emsType = "C";
			} else if ("加工贸易设备手册".equals(hthead.get("手册类型"))) {
				emsType = "T";
			}
			if (emsType != null) {
				dzscEmsPorHead.setEmsType(emsType);
			}
		}
		if (hthead.get("企业内部编号") != null
				&& !hthead.get("企业内部编号").trim().isEmpty()) {
			dzscEmsPorHead.setCopTrNo(hthead.get("企业内部编号"));
		}
		// if(htkouan.size()>0){
		// Map<String,String> mapIeport1 = (Map)htkouan.get(0);
		// if(mapIeport1!=null&&mapIeport1.get("进出口岸代码")!=null&&!mapIeport1.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorHead.setIePort1((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport1.get("进出口岸代码")));
		// }
		// }
		// if(htkouan.size()>1){
		// Map<String,String> mapIeport2 = (Map)htkouan.get(1);
		// if(mapIeport2!=null&&mapIeport2.get("进出口岸代码")!=null&&!mapIeport2.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorHead.setIePort2((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport2.get("进出口岸代码")));
		// }
		// }
		// if(htkouan.size()>2){
		// Map<String,String> mapIeport3 = (Map)htkouan.get(2);
		// if(mapIeport3!=null&&mapIeport3.get("进出口岸代码")!=null&&!mapIeport3.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorHead.setIePort3((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport3.get("进出口岸代码")));
		// }
		// }
		// if(htkouan.size()>3){
		// Map<String,String> mapIeport4 = (Map)htkouan.get(3);
		// if(mapIeport4!=null&&mapIeport4.get("进出口岸代码")!=null&&!mapIeport4.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorHead.setIePort4((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport4.get("进出口岸代码")));
		// }
		// }
		// 执行方法
		try {
			for (int i = 0; i < htkouan.size(); i++) {
				Map<String, String> mapIeport = (Map) htkouan.get(i);
				if (mapIeport != null && mapIeport.get("进出口岸代码") != null
						&& !mapIeport.get("进出口岸代码").trim().isEmpty()) {
					Customs customs = (Customs) this.dzscDao
							.findCustomBaseEntity("Customs", "code",
									mapIeport.get("进出口岸代码"));
					Method method = dzscEmsPorHead.getClass().getMethod(
							"setIePort" + (i + 1), Customs.class);
					method.invoke(dzscEmsPorHead, new Object[] { customs });
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		// dzscEmsPorHead.setDeferDate(CommonUtils.convertStrToDate(hthead.get("电子手册编号")));//-----
//		if (dzscEmsPorHead.getEndDate() != null) {
//			long destroyDate = dzscEmsPorHead.getEndDate().getTime()
//					+ (30 * 24 * 60 * 60 * 1000L);
//			dzscEmsPorHead.setDestroyDate(new Date(destroyDate));// 核销日期
//		}
		if (hthead.get("贸易方式") != null && !hthead.get("贸易方式").trim().isEmpty()) {
			dzscEmsPorHead.setTrade((Trade) this.dzscDao.findCustomBaseEntity(
					"Trade", "name", hthead.get("贸易方式")));
		}
		// dzscEmsPorHead.setTradeCountry((Country) this.dzscDao
		// .findCustomBaseEntity("Country", "code",
		// hthead.get("电子手册编号")));//--------
		// dzscEmsPorHead.setEnterpriseAddress(hthead.get("电子手册编号"));//---------
		// dzscEmsPorHead.setLinkMan(hthead.get("电子手册编号"));//---------------
		if (hthead.get("协议号") != null && !hthead.get("协议号").trim().isEmpty()) {
			dzscEmsPorHead.setAgreementNo(hthead.get("协议号"));
		}
		if (hthead.get("进口合同") != null && !hthead.get("进口合同").trim().isEmpty()) {
			dzscEmsPorHead.setIeContractNo(hthead.get("进口合同"));
		}
		if (hthead.get("出口合同") != null
				&& !hthead.get("出口合同").trim().isEmpty()) {
			dzscEmsPorHead.setImContractNo(hthead.get("出口合同"));
		}
		if (hthead.get("备案进口总额") != null
				&& !hthead.get("备案进口总额").trim().isEmpty()) {
			dzscEmsPorHead.setImgAmount(Double.valueOf(hthead.get("备案进口总额")));// --
		}
		if (hthead.get("备案出口总额") != null
				&& !hthead.get("备案出口总额").trim().isEmpty()) {
			dzscEmsPorHead.setExgAmount(Double.valueOf(hthead.get("备案出口总额")));// --
		}
		if (hthead.get("进口币制") != null && !hthead.get("进口币制").trim().isEmpty()) {
			dzscEmsPorHead.setCurr((Curr) this.dzscDao.findCustomBaseEntity(
					"Curr", "name", hthead.get("进口币制")));// ------------币制
		}
		// dzscEmsPorHead.setWardshipRate(Double.valueOf(hthead.get("本次进口总额")));//---------
		// dzscEmsPorHead.setWardshipFee(Double.valueOf(hthead.get("本次进口总额")));//------------
		if (hthead.get("成交方式") != null && !hthead.get("成交方式").trim().isEmpty()) {
			dzscEmsPorHead.setTransac((Transac) this.dzscDao
					.findCustomBaseEntity("Transac", "name", hthead.get("成交方式")
							.trim()));
		}
		if (hthead.get("备注") != null && !hthead.get("备注").trim().isEmpty()) {
			dzscEmsPorHead.setNote(hthead.get("备注"));
		}
		// dzscEmsPorHead.setCopEntNo(hthead.get("电子手册编号"));//-------------
		// dzscEmsPorHead.setLastEmsNo(hthead.get("电子手册编号"));//-----------
		// dzscEmsPorHead.setCorrEmsNo(hthead.get("电子手册编号"));//-------------
		// 录入员代号
		// dzscEmsPorHead.setInputER(hthead.get("电子手册编号"));//------------
		dzscEmsPorHead.setMachCode(((Company)CommonUtils.getCompany()).getCode());
		dzscEmsPorHead.setMachName(((Company)CommonUtils.getCompany()).getName());
		dzscEmsPorHead.setContactTel(((Company)CommonUtils.getCompany()).getTel());
		dzscEmsPorHead.setDeclareState(DzscState.EXECUTE);
		// dzscEmsPorHead.setDeclareState(DzscState.ORIGINAL);
		dzscEmsPorHead.setModifyMark(ModifyMarkState.UNCHANGE);

		dzscEmsPorHead
				.setEnterpriseAddress(((Company) CommonUtils.getCompany())
						.getAddress());
		dzscEmsPorHead.setLinkMan(((Company) CommonUtils.getCompany())
				.getLinkman());
		// dzscEmsPorHead.setContactTel(((Company)CommonUtils.getCompany()).get());
		dzscEmsPorHead.setIsImportFromQP(true);
		if (dzscEmsPorHead.getSaveDate() == null) {
			dzscEmsPorHead.setSaveDate(dzscEmsPorHead.getBeginDate());
		}
		this.dzscDao.saveDzscEmsPorHead(dzscEmsPorHead);
		return dzscEmsPorHead;
	}

	private Map initCustomsBase(String entityClassName, String keyFieldName) {
		Map resultMap = new HashMap();
		List list = this.dzscDao.findCustomBaseEntityList(entityClassName);
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
	
	public Map<String,String> getDzscEmsExgBill(String emsNo){
		Map<String,String> map = new HashMap<String,String>();
		List list = dzscDao.getDzscEmsExgBill(emsNo);
		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[])list.get(i);
			if(object!=null&&object[0]!=null&&object[1]!=null){
				map.put(object[0].toString(), object[1].toString());
			}
		}
		return map;
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
				this.dzscDao.saveOrUpdate(complex);
				complexMap.put(complexCode, complex);
				return complex;
			} else {
				return null;
			}
		}
	}

	/**
	 * 导入通关备案料件
	 * 
	 * @param ptsEmsListAImg
	 * @param dzscEmsPorHead
	 */
	private void importDzscEmsImgBill(List htliaojian,
			DzscEmsPorHead dzscEmsPorHead, Map<String, Complex> complexMap,
			Map<String, CustomsComplex> customsComplexMap,
			Map<String, Unit> mapUnit,Map<String, Country> mapCountry,
			Map<String, LevyMode> mapLevyMode) {
		String exceptionMessger = null;
		List listImgBill = new ArrayList();
		for (int i = 0; i < htliaojian.size(); i++) {

			Map<String, String> mapImg = (Map) htliaojian.get(i);

			DzscEmsImgBill imgBill = new DzscEmsImgBill();
			imgBill.setDzscEmsPorHead(dzscEmsPorHead);
			if (mapImg.get("料件序号") != null
					&& !mapImg.get("料件序号").trim().isEmpty()) {
				imgBill.setSeqNum(Integer.parseInt(mapImg.get("料件序号")));// ------------
			}
			// imgBill.setTenSeqNum(qpImgBill.getTenSeqNum());//------
			if (mapImg.get("商品编号") != null && mapImg.get("附加编号") != null
					&& !mapImg.get("商品编号").trim().isEmpty()
					&& !mapImg.get("附加编号").trim().isEmpty()) {
				Complex complex = getComplexByCode(complexMap,
						customsComplexMap, mapImg.get("商品编号").trim()
								+ mapImg.get("附加编号").trim());
				if(complex==null){
					exceptionMessger += exceptionMessger==null?mapImg.get("商品编号").trim()
							+ mapImg.get("附加编号").trim():","+mapImg.get("商品编号").trim()
							+ mapImg.get("附加编号").trim();
				}
				imgBill.setComplex(complex);
			}
			if (mapImg.get("商品名称") != null
					&& !mapImg.get("商品名称").trim().isEmpty()) {
				imgBill.setName(mapImg.get("商品名称"));
			}
			if (mapImg.get("规格型号") != null
					&& !mapImg.get("规格型号").trim().isEmpty()) {
				imgBill.setSpec(mapImg.get("规格型号"));
			}
			if (mapImg.get("申报单价") != null
					&& !mapImg.get("申报单价").trim().isEmpty()) {
				imgBill.setPrice(Double.valueOf(mapImg.get("申报单价")));
			}
			if (mapImg.get("申报数量") != null
					&& !mapImg.get("申报数量").trim().isEmpty()) {
				imgBill.setAmount(Double.valueOf(mapImg.get("申报数量")));
			}
			if (mapImg.get("总价") != null && !mapImg.get("总价").trim().isEmpty()) {
				imgBill.setMoney(Double.valueOf(mapImg.get("总价")));
			}
			if (mapImg.get("申报计量单位") != null
					&& !mapImg.get("申报计量单位").trim().isEmpty()) {
				if (mapUnit.get(mapImg.get("申报计量单位").trim()) != null) {
					imgBill.setUnit(mapUnit.get(mapImg.get("申报计量单位").trim()));
				}

			}
			if(mapImg.get("产销国") != null
					&& !mapImg.get("产销国").trim().isEmpty()){
				if(mapCountry.get(mapImg.get("产销国"))!=null){
					imgBill.setCountry(mapCountry.get(mapImg.get("产销国")));
				}
			}
			
			if(mapImg.get("征免方式") != null
					&& !mapImg.get("征免方式").trim().isEmpty()){
				if(mapLevyMode.get(mapImg.get("征免方式"))!=null){
					imgBill.setLevyMode(mapLevyMode.get(mapImg.get("征免方式")));
				}
			}
			
			// imgBill.setUnitWeight(qpImgBill.getUnitWeight());//-------------
			// imgBill.setWeight(qpImgBill.getWeight());
			// imgBill.setCountry((Country) this.dzscDao.findCustomBaseEntity(
			// "Country", "code", qpImgBill.getCountryCode()));
			// imgBill.setLevyMode((LevyMode) this.dzscDao.findCustomBaseEntity(
			// "LevyMode", "code", qpImgBill.getLevyModeCode()));
			// if (imgBill.getLevyMode() == null) {
			// imgBill.setLevyMode((LevyMode) this.dzscDao
			// .findCustomBaseEntity("LevyMode", "code", "3"));
			// }
			// imgBill.setNote(qpImgBill.getNote());
			// imgBill.setLegalUnitGene(qpImgBill.getLegalUnitGene());
			// imgBill.setLegalUnit2Gene(qpImgBill.getLegalUnit2Gene());
			// imgBill.setWeigthUnitGene(qpImgBill.getWeigthUnitGene());
			// imgBill.setDutyRate(qpImgBill.getDutyRate());
			// imgBill.setDutyRatio(qpImgBill.getDutyRatio());//征税比例
			// imgBill.setDetailNote(qpImgBill.getDetailNote());
			imgBill.setModifyMark(ModifyMarkState.UNCHANGE);
			listImgBill.add(imgBill);
			
		}
		if(exceptionMessger!=null){
			throw new RuntimeException("以下商品编码找不到"+exceptionMessger);
		}else{
			this.dzscDao.saveDzscEmsImgBill(listImgBill);
		}
	}

	/**
	 * 导入通关备案成品
	 * 
	 * @param ptsEmsListAExg
	 * @param dzscEmsPorHead
	 */
	private void importDzscEmsExgBill(Map<String,String> map,List htchenping,
			DzscEmsPorHead dzscEmsPorHead, Map<String, Complex> complexMap,
			Map<String, CustomsComplex> customsComplexMap,
			Map<String, Unit> mapUnit,Map<String, Country> mapCountry,
			Map<String, LevyMode> mapLevyMode) {
		String exceptionMessger = null;
		List listExgBill = new ArrayList();
		for (int i = 0; i < htchenping.size(); i++) {
			// DzscQPEmsExgBill qpExgBill = (DzscQPEmsExgBill)
			// htchenping.get(i);//-------------
			Map<String, String> mapExg = (Map) htchenping.get(i);
			DzscEmsExgBill exgBill = new DzscEmsExgBill();
			exgBill.setDzscEmsPorHead(dzscEmsPorHead);

			if (mapExg.get("成品序号") != null
					&& !mapExg.get("成品序号").trim().isEmpty()) {
				exgBill.setSeqNum(Integer.parseInt(mapExg.get("成品序号")));
			}
			// exgBill.setTenSeqNum(qpExgBill.getTenSeqNum());
			if (mapExg.get("商品编号") != null && mapExg.get("附加编号") != null
					&& !mapExg.get("商品编号").trim().isEmpty()
					&& !mapExg.get("附加编号").trim().isEmpty()) {
				Complex complex = getComplexByCode(complexMap,
						customsComplexMap, mapExg.get("商品编号").trim()
								+ mapExg.get("附加编号").trim());
				if(complex==null){
					exceptionMessger += exceptionMessger==null?mapExg.get("商品编号").trim()
							+ mapExg.get("附加编号").trim():","+mapExg.get("商品编号").trim()
							+ mapExg.get("附加编号").trim();
				}
				exgBill.setComplex(complex);

			}
			if (mapExg.get("商品名称") != null
					&& !mapExg.get("商品名称").trim().isEmpty()) {
				exgBill.setName(mapExg.get("商品名称"));
			}
			if (mapExg.get("规格型号") != null
					&& !mapExg.get("规格型号").trim().isEmpty()) {
				exgBill.setSpec(mapExg.get("规格型号"));
			}
			if (mapExg.get("申报单价") != null
					&& !mapExg.get("申报单价").trim().isEmpty()) {
				exgBill.setPrice(Double.valueOf(mapExg.get("申报单价")));
			}
			if (mapExg.get("申报数量") != null
					&& !mapExg.get("申报数量").trim().isEmpty()) {
				exgBill.setAmount(Double.valueOf(mapExg.get("申报数量")));
			}
			if (mapExg.get("总价") != null && !mapExg.get("总价").trim().isEmpty()) {
				exgBill.setMoney(Double.valueOf(mapExg.get("总价")));
			}
			if (mapExg.get("申报计量单位") != null
					&& !mapExg.get("申报计量单位").trim().isEmpty()) {
				if (mapUnit.get(mapExg.get("申报计量单位").trim()) != null) {
					exgBill.setUnit(mapUnit.get(mapExg.get("申报计量单位").trim()));
				}
			}
			// exgBill.setCountry((Country) this.dzscDao.findCustomBaseEntity(
			// "Country", "code", qpExgBill.getCountryCode()));
			// exgBill.setLevyMode((LevyMode) this.dzscDao.findCustomBaseEntity(
			// "LevyMode", "code", qpExgBill.getLevyModeCode()));
			if (exgBill.getLevyMode() == null) {
				exgBill.setLevyMode((LevyMode) this.dzscDao
						.findCustomBaseEntity("LevyMode", "code", "3"));
			}
			if(map!=null&&map.get(mapExg.get("成品序号"))!=null
					&&!map.get(mapExg.get("成品序号")).trim().isEmpty()){
				exgBill.setMachinPrice(Double.valueOf(map.get(mapExg.get("成品序号").toString())));
			}
			if(mapExg.get("产销国") != null
					&& !mapExg.get("产销国").trim().isEmpty()){
				if(mapCountry.get(mapExg.get("产销国"))!=null){
					exgBill.setCountry(mapCountry.get(mapExg.get("产销国")));
				}
			}
			if(mapExg.get("征免方式") != null
					&& !mapExg.get("征免方式").trim().isEmpty()){
				if(mapLevyMode.get(mapExg.get("征免方式"))!=null){
					exgBill.setLevyMode(mapLevyMode.get(mapExg.get("征免方式")));
				}
			}
			// exgBill.setNote(qpExgBill.getNote());
			// exgBill.setLegalUnitGene(qpExgBill.getLegalUnitGene());
			// exgBill.setLegalUnit2Gene(qpExgBill.getLegalUnit2Gene());
			// exgBill.setWeigthUnitGene(qpExgBill.getWeigthUnitGene());
			// exgBill.setDutyRate(qpExgBill.getDutyRate());
			// exgBill.setDutyRatio(qpExgBill.getDutyRatio());
			exgBill.setModifyMark(ModifyMarkState.UNCHANGE);
			listExgBill.add(exgBill);
//			this.dzscDao.saveDzscEmsExgBill(exgBill);
		}
		if(exceptionMessger!=null){
			throw new RuntimeException("以下商品编码找不到"+exceptionMessger);
		}else{
			this.dzscDao.saveDzscEmsExgBill(listExgBill);
		}
	}

	/**
	 * 导入通关备案单耗
	 * 
	 * @param ptsEmsListAExg
	 * @param dzscEmsPorHead
	 */
	private void importDzscEmsBomBill(List htdanhao,
			DzscEmsPorHead dzscEmsPorHead) {
		Map<Integer, DzscEmsExgBill> exgBillMap = new HashMap<Integer, DzscEmsExgBill>();
		List list = this.dzscDao.findDzscEmsExgBill(dzscEmsPorHead);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgBill imgBill = (DzscEmsExgBill) list.get(i);
			exgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		Map<Integer, DzscEmsImgBill> imgBillMap = new HashMap<Integer, DzscEmsImgBill>();
		list = this.dzscDao.findDzscEmsImgBill(dzscEmsPorHead);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgBill imgBill = (DzscEmsImgBill) list.get(i);
			imgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < htdanhao.size(); i++) {
			// DzscQPEmsBomBill qpBomBill = (DzscQPEmsBomBill)
			// htdanhao.get(i);//---------
			Map<String, String> mapBom = (Map) htdanhao.get(i);
			// DzscEmsExgBill exgBill = exgBillMap.get(mapBom.getExgSeqNum());
			// DzscEmsImgBill imgBill = imgBillMap.get(mapBom.getImgSeqNum());
			// DzscEmsBomBill bomBill = bomBillMap.get(mapBom.getExgSeqNum()
			// + "-" + qpBomBill.getImgSeqNum());
			DzscEmsExgBill exgBill = exgBillMap.get(Integer.parseInt(mapBom
					.get("成品序号").trim()));
			DzscEmsImgBill imgBill = imgBillMap.get(Integer.parseInt(mapBom
					.get("料件序号").trim()));
			// DzscEmsBomBill bomBill =
			// bomBillMap.get(Integer.parseInt(mapBom.get("成品序号").trim())+
			// "-" + Integer.parseInt(mapBom.get("料件序号").trim()));
			DzscEmsBomBill bomBill = new DzscEmsBomBill();
			if (exgBill != null) {
				bomBill.setDzscEmsExgBill(exgBill);
			} else {
				continue;
			}
			System.out.println("料件序号==" + mapBom.get("料件序号"));
			if (mapBom.get("料件序号") != null
					&& !mapBom.get("料件序号").trim().isEmpty()) {
				bomBill.setImgSeqNum(Integer.parseInt(mapBom.get("料件序号")));
			}
			if (imgBill != null) {
				bomBill.setComplex(imgBill.getComplex());
				bomBill.setName(imgBill.getName());
				bomBill.setSpec(imgBill.getSpec());
				bomBill.setPrice(imgBill.getPrice());
			}
			if (mapBom.get("非保税料比例") != null
					&& !mapBom.get("非保税料比例").trim().isEmpty()) {
				bomBill.setNonMnlRatio(Double.valueOf(mapBom.get("非保税料比例")
						.trim()));
			}
			if (mapBom.get("单耗") != null && !mapBom.get("单耗").trim().isEmpty()) {
				bomBill.setUnitWare(Double.valueOf(mapBom.get("单耗").trim()));
			}
			if (mapBom.get("损耗率") != null
					&& !mapBom.get("损耗率").trim().isEmpty()) {
				bomBill.setWare(Double.valueOf(mapBom.get("损耗率").trim()));
			}
			Double totalExgAmount = 0.0;
			if (exgBill != null && exgBill.getAmount() != null) {
				totalExgAmount = CommonUtils.getDoubleExceptNull(exgBill
						.getAmount());
			}

			Double unitDosage = 0.0;
			if (bomBill.getWare() != null && bomBill.getWare() != 0.0) {
				unitDosage = CommonUtils
						.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(bomBill
										.getUnitWare())
										/ (1.0 - CommonUtils
												.getDoubleExceptNull(bomBill
														.getWare()) / 100.0), 5);
			}

			bomBill.setUnitDosage(unitDosage);
			bomBill.setAmount(CommonUtils.getDoubleByDigit(totalExgAmount
					* unitDosage, 6));
			if (bomBill.getPrice() != null) {
				bomBill.setMoney(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(bomBill.getPrice())
								* CommonUtils.getDoubleExceptNull(bomBill
										.getAmount()), 6));
			}
			if (imgBill != null && imgBill.getUnit() != null) {
				bomBill.setUnit(imgBill.getUnit());
			}
			if (imgBill != null && imgBill.getCountry() != null) {
				bomBill.setCountry(imgBill.getCountry());
			}
			bomBill.setModifyMark(ModifyMarkState.UNCHANGE);
			this.dzscDao.saveDzscEmsBomBill(bomBill);
		}
	}

	/**
	 * 导入通关备案归并前料件
	 * 
	 * @param ptsEmsListAExg
	 * @param dzscEmsPorHead
	 */
	private void importDzscEmsPorMaterialImg(List ptsEmsListImg,
			DzscEmsPorHead dzscEmsPorHead) {
		Map<String, DzscEmsPorMaterialImg> materialImgMap = new HashMap<String, DzscEmsPorMaterialImg>();
		List list = this.dzscDao.findDzscEmsPorMaterialImg(dzscEmsPorHead);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialImg materialImg = (DzscEmsPorMaterialImg) list
					.get(i);
			materialImgMap.put(materialImg.getMateriel().getPtNo() + "-"
					+ materialImg.getDzscEmsImgBill().getSeqNum(), materialImg);
		}
		Map<Integer, DzscEmsImgBill> imgBillMap = new HashMap<Integer, DzscEmsImgBill>();
		list = this.dzscDao.findDzscEmsImgBill(dzscEmsPorHead);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgBill imgBill = (DzscEmsImgBill) list.get(i);
			imgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ptsEmsListImg.size(); i++) {
			DzscQPEmsPorMaterialImg qpMaterialImg = (DzscQPEmsPorMaterialImg) ptsEmsListImg
					.get(i);
			DzscEmsPorMaterialImg materialImg = materialImgMap
					.get(qpMaterialImg.getMaterielPtNo() + "-"
							+ qpMaterialImg.getImgBillSeqNum());
			if (materialImg == null) {
				materialImg = new DzscEmsPorMaterialImg();
				materialImg.setDzscEmsPorHead(dzscEmsPorHead);
			}
			materialImg.setMateriel((Materiel) this.dzscDao.findBaseScmEntity(
					"Materiel", "ptNo", qpMaterialImg.getMaterielPtNo()));
			materialImg.setDzscEmsImgBill(imgBillMap.get(qpMaterialImg
					.getImgBillSeqNum()));
			materialImg.setModifyMark(ModifyMarkState.UNCHANGE);
			this.dzscDao.saveOrUpdate(materialImg);
		}
	}

	/**
	 * 导入通关备案归并前成品
	 * 
	 * @param ptsEmsListAExg
	 * @param dzscEmsPorHead
	 */
	private void importDzscEmsPorMaterialExg(List ptsEmsListExg,
			DzscEmsPorHead dzscEmsPorHead) {
		Map<String, DzscEmsPorMaterialExg> materialExgMap = new HashMap<String, DzscEmsPorMaterialExg>();
		List list = this.dzscDao.findDzscEmsPorMaterialExg(dzscEmsPorHead);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsPorMaterialExg materialExg = (DzscEmsPorMaterialExg) list
					.get(i);
			materialExgMap.put(materialExg.getMateriel().getPtNo() + "-"
					+ materialExg.getDzscEmsExgBill().getSeqNum(), materialExg);
		}
		Map<Integer, DzscEmsExgBill> exgBillMap = new HashMap<Integer, DzscEmsExgBill>();
		list = this.dzscDao.findDzscEmsExgBill(dzscEmsPorHead);
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgBill exgBill = (DzscEmsExgBill) list.get(i);
			exgBillMap.put(exgBill.getSeqNum(), exgBill);
		}
		for (int i = 0; i < ptsEmsListExg.size(); i++) {
			DzscQPEmsPorMaterialExg qpMaterialExg = (DzscQPEmsPorMaterialExg) ptsEmsListExg
					.get(i);
			DzscEmsPorMaterialExg materialExg = materialExgMap
					.get(qpMaterialExg.getMaterielPtNo() + "-"
							+ qpMaterialExg.getExgBillSeqNum());
			if (materialExg == null) {
				materialExg = new DzscEmsPorMaterialExg();
				materialExg.setDzscEmsPorHead(dzscEmsPorHead);
			}
			materialExg.setMateriel((Materiel) this.dzscDao.findBaseScmEntity(
					"Materiel", "ptNo", qpMaterialExg.getMaterielPtNo()));
			materialExg.setDzscEmsExgBill(exgBillMap.get(qpMaterialExg
					.getExgBillSeqNum()));
			materialExg.setModifyMark(ModifyMarkState.UNCHANGE);
			this.dzscDao.saveOrUpdate(materialExg);
		}
	}

	/**
	 * 从QP中导入电子手册合同备案
	 * 
	 * @param tempQPEmsPorData
	 */
	public void importDzscEmsPorWjFromQP(String emsPorWjContent) {
		// for (int i = 0; i < listPtsEmsWjHead.size(); i++) {
		// DzscQPEmsPorWjHead dzscQPEmsPorHead = (DzscQPEmsPorWjHead)
		// listPtsEmsWjHead
		// .get(i);
		// String tradeCode = dzscQPEmsPorHead.getTradeCode();// 经营单位
		// String copEmsNo = dzscQPEmsPorHead.getCopTrNo();// 内部编号
		// DzscParameterSet paraSet = dzscDao.findDzscParameterSet();
		// TempQPEmsTrData tempQPEmsTrData = DzscQpServiceClient.getInstance(
		// paraSet).findTempQPEmsTrData(paraSet.getRemoteHostICPwd(),
		// tradeCode, copEmsNo, true);// 根据
		// // 经营单位
		// // ，内部编号
		// // 抓取单据资料
		importDzscEmsPorWj(emsPorWjContent);
		// }
	}

	/**
	 * 从QP中导入电子手册合同备案
	 * 
	 * @param tempQPEmsTrData
	 */
	private void importDzscEmsPorWj(String emsPorWjContent) {
		long beginTime = System.currentTimeMillis();
		if (emsPorWjContent == null || "".equals(emsPorWjContent.trim())) {
			throw new RuntimeException("合同备案内容为空");
		}

		Map<String, Complex> complexMap = initCustomsBase("Complex", "code");
		Map<String, CustomsComplex> customsComplexMap = initCustomsBase(
				"CustomsComplex", "code");
		Map<String, Unit> mapUnit = initCustomsBase("Unit", "name");

		Gson gson = new Gson();
		Map headMap = (Map) gson.fromJson(emsPorWjContent, Map.class);
		Map<String, String> mapHead = (Map) headMap.get("合同表头");// 合同表头
		List imgList = (List) headMap.get("合同料件");// 合同料件
		List htkouan = (List) headMap.get("进出口岸");// 口岸
		List exgList = (List) headMap.get("合同成品");// 合同成品
		
		if (mapHead == null) {
			throw new RuntimeException("从远程服务中抓取的通关备案表头为空");
		}
		
		String emsNo = mapHead.get("电子手册编号");
		if (emsNo == null || "".equals(emsNo.trim())) {
			throw new RuntimeException("从远程服务中抓取的合同备案的手册号为空");
		}
		DzscEmsPorWjHead dzscEmsPorWjHead = this.dzscDao
				.findExingDzscEmsPorWjHeadByEmsNo(emsNo);
		Date date = null;
		if (dzscEmsPorWjHead != null) {
			date = dzscEmsPorWjHead.getBeginDate();
			//删除成品和料件
			this.dzscDao.deleteAllEmsPorWj(dzscEmsPorWjHead);
			//删除表头
			this.dzscDao.deleteDzscEmsPorWjHead(dzscEmsPorWjHead);
		}
		System.out.println("准备数据用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		beginTime = System.currentTimeMillis();
		dzscEmsPorWjHead = this.importDzscEmsPorWjHead(mapHead, htkouan,date);
		System.out.println("导入表头用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		beginTime = System.currentTimeMillis();
//		if (tempQPEmsTrData.getPtsTrListImg() != null) {
//			this.importDzscEmsImgWj(tempQPEmsTrData.getPtsTrListImg(),
//					dzscEmsPorWjHead);
//		}
		if(imgList!=null){
			this.importDzscEmsImgWj(imgList, dzscEmsPorWjHead, complexMap,
					customsComplexMap, mapUnit);
			System.out.println("导入料件用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
			beginTime = System.currentTimeMillis();
		}
		// if (tempQPEmsTrData.getPtsTrListExg() != null) {
		// this.importDzscEmsExgWj(tempQPEmsTrData.getPtsTrListExg(),
		// dzscEmsPorWjHead);
		// }
		if(exgList!=null){
			this.importDzscEmsExgWj(exgList, dzscEmsPorWjHead, complexMap,
					customsComplexMap, mapUnit);
			System.out.println("导入成品用时："+(System.currentTimeMillis()-beginTime)/1000+"秒");
		}
	}

	/**
	 * 导入合同备案表头
	 * 
	 * @param ptsEmsHead
	 */
	private DzscEmsPorWjHead importDzscEmsPorWjHead(
			Map<String, String> mapHead, List htkouan,Date date) {
		
		DzscEmsPorWjHead dzscEmsPorWjHead = new DzscEmsPorWjHead();
		dzscEmsPorWjHead.setSeqNum(Integer.parseInt(this.dzscDao.getNum(
				"DzscEmsPorWjHead", "seqNum")));
		String emsNo = (String) mapHead.get("电子手册编号");
		if (emsNo != null) {
			dzscEmsPorWjHead.setEmsNo(emsNo);
		}
		if (mapHead.get("批准文号") != null
				&& !mapHead.get("批准文号").trim().isEmpty()) {
			dzscEmsPorWjHead.setSancEmsNo(mapHead.get("批准文号"));
		}
		if (mapHead.get("经营单位") != null
				&& !mapHead.get("经营单位").trim().isEmpty()) {
			dzscEmsPorWjHead.setTradeCode(mapHead.get("经营单位"));
		}
		if (mapHead.get("经营单位名称") != null
				&& !mapHead.get("经营单位名称").trim().isEmpty()) {
			dzscEmsPorWjHead.setTradeName(mapHead.get("经营单位名称"));
		}
		if (mapHead.get("经营单位") != null
				&& !mapHead.get("经营单位").trim().isEmpty()) {
			dzscEmsPorWjHead.setMachCode(mapHead.get("经营单位"));
		}
		if (mapHead.get("经营单位名称") != null
				&& !mapHead.get("经营单位名称").trim().isEmpty()) {
			dzscEmsPorWjHead.setMachName(mapHead.get("经营单位名称"));
		}
		if(date!=null){
			dzscEmsPorWjHead.setBeginDate(date);
		}else{
			if (mapHead.get("录入日期") != null
					&& !mapHead.get("录入日期").trim().isEmpty()) {
				dzscEmsPorWjHead.setBeginDate(CommonUtils.convertStrToDate(mapHead
						.get("录入日期").substring(0, 4)
						+ "-"
						+ mapHead.get("录入日期").substring(4, 6)
						+ "-"
						+ mapHead.get("录入日期").substring(6,
								mapHead.get("录入日期").length())));
			}
		}
		if (mapHead.get("有效期限") != null
				&& !mapHead.get("有效期限").trim().isEmpty()) {
			dzscEmsPorWjHead.setEndDate(CommonUtils.convertStrToDate(mapHead
					.get("有效期限").substring(0, 4)
					+ "-"
					+ mapHead.get("有效期限").substring(4, 6)
					+ "-"
					+ mapHead.get("有效期限").substring(6,
							mapHead.get("有效期限").length())));
		}
		if (mapHead.get("外商公司") != null
				&& !mapHead.get("外商公司").trim().isEmpty()) {
			dzscEmsPorWjHead.setOutTradeCo(mapHead.get("外商公司"));
		}
		// dzscEmsPorWjHead.setLevyMode((LevyMode) this.dzscDao
		// .findCustomBaseEntity("LevyMode", "code", mapHead.get("批准文号")));
		if (mapHead.get("征免性质") != null
				&& !mapHead.get("征免性质").trim().isEmpty()) {
			dzscEmsPorWjHead.setLevyKind((LevyKind) this.dzscDao
					.findCustomBaseEntity("LevyKind", "name",
							mapHead.get("征免性质")));
		}
		if (mapHead.get("保税方式") != null
				&& !mapHead.get("保税方式").trim().isEmpty()) {
			dzscEmsPorWjHead.setPayMode((PayMode) this.dzscDao
					.findCustomBaseEntity("PayMode", "name",
							mapHead.get("保税方式")));
		}
		if (mapHead.get("加工种类") != null
				&& !mapHead.get("加工种类").trim().isEmpty()) {
			dzscEmsPorWjHead.setMachiningType((MachiningType) this.dzscDao
					.findCustomBaseEntity("MachiningType", "name",
							mapHead.get("加工种类")));
		}
		if (mapHead.get("协议号") != null && !mapHead.get("协议号").trim().isEmpty()) {
			dzscEmsPorWjHead.setEmsApprNo(mapHead.get("协议号"));
		}
		if (mapHead.get("收货地区") != null
				&& !mapHead.get("收货地区").trim().isEmpty()) {
			dzscEmsPorWjHead.setReceiveArea((District) this.dzscDao
					.findCustomBaseEntity("District", "name",
							mapHead.get("收货地区")));
		}
		// dzscEmsPorWjHead.setInvestMode((InvestMode) this.dzscDao
		// .findCustomBaseEntity("InvestMode", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setFetchInMode((FetchInMode) this.dzscDao
		// .findCustomBaseEntity("FetchInMode", "code",mapHead.get("")));
		if (mapHead.get("内销比") != null && !mapHead.get("内销比").trim().isEmpty()) {
			dzscEmsPorWjHead.setInSaleRate(Double.valueOf(mapHead.get("内销比")));
		}
		if (mapHead.get("申报日期") != null
				&& !mapHead.get("申报日期").trim().isEmpty()) {
			dzscEmsPorWjHead.setDeclareDate(CommonUtils
					.convertStrToDate(mapHead.get("申报日期").substring(0, 4)
							+ "-"
							+ mapHead.get("申报日期").substring(4, 6)
							+ "-"
							+ mapHead.get("申报日期").substring(6,
									mapHead.get("申报日期").length())));
		}
		// if(mapHead.get("进口货物项数")!=null&&!mapHead.get("进口货物项数").trim().isEmpty()){
		// dzscEmsPorWjHead.setMaterielItemCount(Integer.parseInt(mapHead.get("进口货物项数")));
		// }//--------------------------

		// dzscEmsPorWjHead.setMachineCount(10);
		// if(mapHead.get("出口货物项数")!=null&&!mapHead.get("出口货物项数").trim().isEmpty()){
		// dzscEmsPorWjHead.setProductItemCount(Integer.parseInt(mapHead.get("出口货物项数")));
		// }//---------------------------

		if (mapHead.get("管理对象") != null
				&& !mapHead.get("管理对象").trim().isEmpty()) {
			if (mapHead.get("管理对象").contains("经营单位")) {
				dzscEmsPorWjHead.setManageObject("0");
			}
			if (mapHead.get("管理对象").contains("加工单位")) {
				dzscEmsPorWjHead.setManageObject("1");
			}
		}
		if (mapHead.get("限制类标志") != null
				&& !mapHead.get("限制类标志").trim().isEmpty()) {
			String limitFlag = "";
			if (mapHead.get("限制类标志").contains("调整前旧手册")) {
				limitFlag = "1";
			} else if (mapHead.get("限制类标志").contains("调整后新手册")) {
				limitFlag = "2";
			} else if (mapHead.get("限制类标志").contains("台账专用手册")) {
				limitFlag = "3";
			}
			if (limitFlag != null) {
				dzscEmsPorWjHead.setLimitFlag(limitFlag);
			}
		}
		if (dzscEmsPorWjHead.getLimitFlag() == null
				|| "".equals(dzscEmsPorWjHead.getLimitFlag().trim())) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String beginDate = dateFormat.format(dzscEmsPorWjHead
					.getBeginDate());
			if (beginDate.compareTo("2008-08-23") < 0) {
				dzscEmsPorWjHead.setLimitFlag(LimitFlag.ADJUST_BEFORE_EMS);
			} else {
				dzscEmsPorWjHead.setLimitFlag(LimitFlag.ADJUST_AFTER_EMS);
			}
		}
		if (mapHead.get("单耗申报环节") != null
				&& !mapHead.get("单耗申报环节").trim().isEmpty()) {
			dzscEmsPorWjHead.setEquipMode(mapHead.get("单耗申报环节"));
		}
		if (mapHead.get("主管海关") != null
				&& !mapHead.get("主管海关").trim().isEmpty()) {
			dzscEmsPorWjHead.setCustomNo((Customs) this.dzscDao
					.findCustomBaseEntity("Customs", "name",
							mapHead.get("主管海关")));
		}
		if (mapHead.get("主管外经贸部门") != null
				&& !mapHead.get("主管外经贸部门").trim().isEmpty()) {
			dzscEmsPorWjHead.setRedDep((RedDep) this.dzscDao
					.findCustomBaseEntity("RedDep", "name",
							mapHead.get("主管外经贸部门")));
		}
		if (mapHead.get("手册类型") != null
				&& !mapHead.get("手册类型").trim().isEmpty()) {
			String emsType = null;
			if ("来料加工合同手册".equals(mapHead.get("手册类型"))) {
				emsType = "X";
			} else if ("进料加工合同手册".equals(mapHead.get("手册类型"))) {
				emsType = "Y";
			}
			if (emsType != null) {
				dzscEmsPorWjHead.setEmsType(emsType);
			}
		}
		if (mapHead.get("企业内部编号") != null
				&& !mapHead.get("企业内部编号").trim().isEmpty()) {
			dzscEmsPorWjHead.setCopTrNo(mapHead.get("企业内部编号"));
		}
		// Map<String,String> mapIeport1 = (Map)htkouan.get(0);
		// Map<String,String> mapIeport2 = (Map)htkouan.get(1);
		// Map<String,String> mapIeport3 = (Map)htkouan.get(2);
		// Map<String,String> mapIeport4 = (Map)htkouan.get(3);
		// if(mapIeport1!=null&&mapIeport1.get("进出口岸代码")!=null&&!mapIeport1.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorWjHead.setIePort1((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport1.get("进出口岸代码")));
		// }
		// if(mapIeport2!=null&&mapIeport2.get("进出口岸代码")!=null&&!mapIeport2.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorWjHead.setIePort2((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport2.get("进出口岸代码")));
		// }
		// if(mapIeport3!=null&&mapIeport3.get("进出口岸代码")!=null&&!mapIeport3.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorWjHead.setIePort3((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport3.get("进出口岸代码")));
		// }
		// if(mapIeport4!=null&&mapIeport4.get("进出口岸代码")!=null&&!mapIeport4.get("进出口岸代码").trim().isEmpty()){
		// dzscEmsPorWjHead.setIePort4((Customs)
		// this.dzscDao.findCustomBaseEntity(
		// "Customs", "code", mapIeport4.get("进出口岸代码")));
		// }

		// 执行方法
		try {
			for (int i = 0; i < htkouan.size(); i++) {
				Map<String, String> mapIeport = (Map) htkouan.get(i);
				if (mapIeport != null && mapIeport.get("进出口岸代码") != null
						&& !mapIeport.get("进出口岸代码").trim().isEmpty()) {
					Customs customs = (Customs) this.dzscDao
							.findCustomBaseEntity("Customs", "code",
									mapIeport.get("进出口岸代码"));
					Method method = dzscEmsPorWjHead.getClass().getMethod(
							"setIePort" + (i + 1), Customs.class);
					method.invoke(dzscEmsPorWjHead, new Object[] { customs });
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		// dzscEmsPorWjHead.setIePort1((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "name", mapHead.get("进出口岸")));
		// dzscEmsPorWjHead.setIePort2((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort3((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort4((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort5((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort6((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort7((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort8((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort9((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setIePort10((Customs) this.dzscDao
		// .findCustomBaseEntity("Customs", "code", mapHead.get("批准文号")));
		// dzscEmsPorWjHead.setDeferDate(CommonUtils
		// .convertStrToDate(mapHead.get("批准文号")));
		// dzscEmsPorWjHead.getDeclareDate().
//		if (dzscEmsPorWjHead.getEndDate() != null) {
//			dzscEmsPorWjHead.setDestroyDate(new Date(dzscEmsPorWjHead
//					.getEndDate().getTime() + (30 * 24 * 60 * 60 * 1000L)));
//		}
		if (mapHead.get("贸易方式") != null
				&& !mapHead.get("贸易方式").trim().isEmpty()) {
			dzscEmsPorWjHead
					.setTrade((Trade) this.dzscDao.findCustomBaseEntity(
							"Trade", "name", mapHead.get("贸易方式")));
		}
		if (mapHead.get("批准文号") != null
				&& !mapHead.get("批准文号").trim().isEmpty()) {
			dzscEmsPorWjHead.setTradeCountry((Country) this.dzscDao
					.findCustomBaseEntity("Country", "name",
							mapHead.get("批准文号")));
		}
		// dzscEmsPorWjHead.setEnterpriseAddress(mapHead.get("批准文号"));//-------------
		// dzscEmsPorWjHead.setLinkMan(mapHead.get("批准文号"));//-------------
		dzscEmsPorWjHead.setMachCode(((Company)CommonUtils.getCompany()).getCode());
		dzscEmsPorWjHead.setMachName(((Company)CommonUtils.getCompany()).getName());
		dzscEmsPorWjHead.setContactTel(((Company)CommonUtils.getCompany()).getTel());
		if (mapHead.get("协议号") != null && !mapHead.get("协议号").trim().isEmpty()) {
			dzscEmsPorWjHead.setAgreementNo(mapHead.get("协议号"));
		}
		if (mapHead.get("进口合同") != null
				&& !mapHead.get("进口合同").trim().isEmpty()) {
			dzscEmsPorWjHead.setIeContractNo(mapHead.get("进口合同"));
		}
		if (mapHead.get("出口合同") != null
				&& !mapHead.get("出口合同").trim().isEmpty()) {
			dzscEmsPorWjHead.setImContractNo(mapHead.get("出口合同"));
		}
		if (mapHead.get("备案进口总额") != null
				&& !mapHead.get("备案进口总额").trim().isEmpty()) {
			dzscEmsPorWjHead
					.setImgAmount(Double.valueOf(mapHead.get("备案进口总额")));
		}
		if (mapHead.get("备案出口总额") != null
				&& !mapHead.get("备案出口总额").trim().isEmpty()) {
			dzscEmsPorWjHead
					.setExgAmount(Double.valueOf(mapHead.get("备案出口总额")));
		}
		if (mapHead.get("出口币制") != null
				&& !mapHead.get("出口币制").trim().isEmpty()) {
			dzscEmsPorWjHead.setCurr((Curr) this.dzscDao.findCustomBaseEntity(
					"Curr", "name", mapHead.get("出口币制")));
		}
		// dzscEmsPorWjHead.setWardshipRate(1.0);//-------------
		// dzscEmsPorWjHead.setWardshipFee(1.0);//-------------
		if (mapHead.get("成交方式") != null
				&& !mapHead.get("成交方式").trim().isEmpty()) {
			dzscEmsPorWjHead.setTransac((Transac) this.dzscDao
					.findCustomBaseEntity("Transac", "name",
							mapHead.get("成交方式")));
		}
		if (mapHead.get("备注") != null && !mapHead.get("备注").trim().isEmpty()) {
			dzscEmsPorWjHead.setNote(mapHead.get("备注"));
		}
		// 对应上本帐册号
		// dzscEmsPorWjHead.setLastEmsNo(mapHead.get("批准文号"));//-------------
		// 其他手册号
		dzscEmsPorWjHead.setCorrEmsNo(emsNo);
		// 录入员代号
		// dzscEmsPorWjHead.setInputER(mapHead.get("批准文号"));//-------------
		dzscEmsPorWjHead.setDzscManageType(DzscManageType.COMPLEX);
		dzscEmsPorWjHead.setDeclareState(DzscState.EXECUTE);
		// dzscEmsPorWjHead.setDeclareState(DzscState.ORIGINAL);
		dzscEmsPorWjHead.setModifyMark(ModifyMarkState.UNCHANGE);

		dzscEmsPorWjHead.setEnterpriseAddress(((Company) CommonUtils
				.getCompany()).getAddress());
		dzscEmsPorWjHead.setLinkMan(((Company) CommonUtils.getCompany())
				.getLinkman());
		// dzscEmsPorWjHead.setContactTel(((Company)CommonUtils.getCompany()).getLinkman());
		dzscEmsPorWjHead.setIsImportFromQP(true);
		if (dzscEmsPorWjHead.getSaveDate() == null) {
			dzscEmsPorWjHead.setSaveDate(dzscEmsPorWjHead.getBeginDate());
		}
		this.dzscDao.saveOrUpdate(dzscEmsPorWjHead);
		return dzscEmsPorWjHead;
	}

	/**
	 * 导入合同备案料件
	 * 
	 * @param ptsTrListImg
	 * @param dzscEmsPorWjHead
	 */
	private void importDzscEmsImgWj(List ptsTrListImg,
			DzscEmsPorWjHead dzscEmsPorWjHead, Map<String, Complex> complexMap,
			Map<String, CustomsComplex> customsComplexMap,
			Map<String, Unit> mapUnit) {
		Map<Integer, DzscEmsImgWj> imgBillMap = new HashMap<Integer, DzscEmsImgWj>();
		List list = this.dzscDao.findDzscEmsImgWjByParentId(dzscEmsPorWjHead
				.getId());
		for (int i = 0; i < list.size(); i++) {
			DzscEmsImgWj imgBill = (DzscEmsImgWj) list.get(i);
			imgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ptsTrListImg.size(); i++) {
			Map<String, String> qpImgBill = (Map) ptsTrListImg.get(i);
			String seqNum = (String) qpImgBill.get("料件序号");
			Integer imgSeqNum = Integer.valueOf(seqNum);
			DzscEmsImgWj imgBill = imgBillMap.get(seqNum);// qpImgBill.getSeqNum()
			if (imgBill == null) {
				imgBill = new DzscEmsImgWj();
				imgBill.setDzscEmsPorWjHead(dzscEmsPorWjHead);
			}
			if (imgSeqNum != null) {
				imgBill.setSeqNum(imgSeqNum);
			}
			Complex complex = null;
			if (qpImgBill.get("商品编号") != null && qpImgBill.get("附加编码") != null
					&& !qpImgBill.get("商品编号").trim().isEmpty()
					&& !qpImgBill.get("附加编码").trim().isEmpty()) {
				complex = getComplexByCode(complexMap,
						customsComplexMap, qpImgBill.get("商品编号").trim()
								+ qpImgBill.get("附加编码").trim());
				if(complex!=null){
					System.out.println(complex.getCode());
					imgBill.setFourComplex(complex.getCode());
				}
			}

			if (qpImgBill.get("商品名称") != null
					&& !qpImgBill.get("商品名称").trim().isEmpty()) {
				imgBill.setFourName(qpImgBill.get("商品名称"));
			}
			if (qpImgBill.get("规格型号") != null
					&& !qpImgBill.get("规格型号").trim().isEmpty()) {
				imgBill.setFourSpec(qpImgBill.get("规格型号"));
			}
			if (qpImgBill.get("计量单位") != null
					&& !qpImgBill.get("计量单位").trim().isEmpty()) {
				if (mapUnit.get(qpImgBill.get("计量单位").trim()) != null) {
					imgBill.setFourUnit(mapUnit.get(qpImgBill.get("计量单位").trim()));
				}
			}
			if (complex != null) {
				imgBill.setFirstUnit(complex.getFirstUnit());
				imgBill.setSecondUnit(complex.getSecondUnit());
			}

			imgBill.setModifyMark(ModifyMarkState.UNCHANGE);
			this.dzscDao.saveDzscEmsImgWj(imgBill);
		}
	}

	/**
	 * 导入合同备案成品
	 * 
	 * @param ptsTrListExg
	 * @param dzscEmsPorWjHead
	 */
	private void importDzscEmsExgWj(List ptsTrListExg,
			DzscEmsPorWjHead dzscEmsPorWjHead, Map<String, Complex> complexMap,
			Map<String, CustomsComplex> customsComplexMap,
			Map<String, Unit> mapUnit) {
		Map<Integer, DzscEmsExgWj> imgBillMap = new HashMap<Integer, DzscEmsExgWj>();
		List list = this.dzscDao.findDzscEmsExgWjByParentId(dzscEmsPorWjHead
				.getId());
		for (int i = 0; i < list.size(); i++) {
			DzscEmsExgWj imgBill = (DzscEmsExgWj) list.get(i);
			imgBillMap.put(imgBill.getSeqNum(), imgBill);
		}
		for (int i = 0; i < ptsTrListExg.size(); i++) {
			Map<String, String> map = (Map) ptsTrListExg.get(i);
			DzscEmsExgWj exgBill = imgBillMap.get(map.get("成品序号"));
			if (exgBill == null) {
				exgBill = new DzscEmsExgWj();
				exgBill.setDzscEmsPorWjHead(dzscEmsPorWjHead);
			}
			if (map.get("成品序号") != null && !map.get("成品序号").trim().isEmpty()) {
				exgBill.setSeqNum(Integer.parseInt(map.get("成品序号")));
			}
			Complex complex = null;
			if (map.get("商品编号") != null && map.get("附加编码") != null
					&& !map.get("商品编号").trim().isEmpty()
					&& !map.get("附加编码").trim().isEmpty()) {
				complex = getComplexByCode(complexMap,
						customsComplexMap, map.get("商品编号").trim()
								+ map.get("附加编码").trim());
				if(complex!=null){
					exgBill.setFourComplex(complex.getCode());
				}
			}

			if (map.get("商品名称") != null && !map.get("商品名称").trim().isEmpty()) {
				exgBill.setFourName(map.get("商品名称"));
			}
			if (map.get("规格型号") != null && !map.get("规格型号").trim().isEmpty()) {
				exgBill.setFourSpec(map.get("规格型号"));
			}
			if (map.get("计量单位") != null
					&& !map.get("计量单位").trim().isEmpty()) {
				if (mapUnit.get(map.get("计量单位").trim()) != null) {
					exgBill.setFourUnit(mapUnit.get(map.get("计量单位").trim()));
				}
			}
			if (complex != null) {
				exgBill.setFirstUnit(complex.getFirstUnit());
				exgBill.setSecondUnit(complex.getSecondUnit());
			}
			exgBill.setModifyMark(ModifyMarkState.UNCHANGE);
			this.dzscDao.saveDzscEmsExgWj(exgBill);
		}
	}
	// /**
	// * 从QP中导入电子手册合同备案
	// *
	// * @param tempQPEmsPorData
	// */
	// public void importDzscEmsPorWjFromQP(List listPtsEmsWjHead,
	// boolean isOverWrite) {
	// for (int i = 0; i < listPtsEmsWjHead.size(); i++) {
	// DzscQPEmsPorWjHead dzscQPEmsPorHead = (DzscQPEmsPorWjHead)
	// listPtsEmsWjHead
	// .get(i);
	// String tradeCode = dzscQPEmsPorHead.getTradeCode();// 经营单位
	// String copEmsNo = dzscQPEmsPorHead.getCopTrNo();// 内部编号
	// DzscParameterSet paraSet = dzscDao.findDzscParameterSet();
	// TempQPEmsTrData tempQPEmsTrData = DzscQpServiceClient.getInstance(
	// paraSet).findTempQPEmsTrData(paraSet.getRemoteHostICPwd(),
	// tradeCode, copEmsNo, true);// 根据
	// // 经营单位
	// // ，内部编号
	// // 抓取单据资料
	// importDzscEmsPorWj(tempQPEmsTrData, isOverWrite);
	// }
	// }
	//
	// /**
	// * 从QP中导入电子手册合同备案
	// *
	// * @param tempQPEmsTrData
	// */
	// private void importDzscEmsPorWj(TempQPEmsTrData tempQPEmsTrData,
	// boolean isOverWrite) {
	// if (tempQPEmsTrData.getPtsTrHead() == null) {
	// throw new RuntimeException("从远程服务中抓取的合同备案表头为空");
	// }
	// String emsNo = tempQPEmsTrData.getPtsTrHead().getEmsNo().trim();
	// DzscEmsPorWjHead dzscEmsPorWjHead = this.dzscDao
	// .findExingDzscEmsPorWjHeadByEmsNo(emsNo);
	// if (dzscEmsPorWjHead != null && !isOverWrite) {
	// return;
	// }
	// dzscEmsPorWjHead = this.importDzscEmsPorWjHead(tempQPEmsTrData
	// .getPtsTrHead());
	// if (tempQPEmsTrData.getPtsTrListImg() != null) {
	// this.importDzscEmsImgWj(tempQPEmsTrData.getPtsTrListImg(),
	// dzscEmsPorWjHead);
	// }
	// if (tempQPEmsTrData.getPtsTrListExg() != null) {
	// this.importDzscEmsExgWj(tempQPEmsTrData.getPtsTrListExg(),
	// dzscEmsPorWjHead);
	// }
	// }
	//
	// /**
	// * 导入合同备案表头
	// *
	// * @param ptsEmsHead
	// */
	// private DzscEmsPorWjHead importDzscEmsPorWjHead(
	// DzscQPEmsPorWjHead tempPtsEmsWjHead) {
	// if (tempPtsEmsWjHead.getEmsNo() == null
	// || "".equals(tempPtsEmsWjHead.getEmsNo().trim())) {
	// throw new RuntimeException("从远程服务中抓取的通关备案"
	// + tempPtsEmsWjHead.getCopTrNo() + "的手册号为空");
	// }
	// String emsNo = tempPtsEmsWjHead.getEmsNo().trim();
	// DzscEmsPorWjHead dzscEmsPorWjHead = this.dzscDao
	// .findExingDzscEmsPorWjHeadByEmsNo(emsNo);
	// if (dzscEmsPorWjHead == null) {
	// dzscEmsPorWjHead = new DzscEmsPorWjHead();
	// dzscEmsPorWjHead.setSeqNum(Integer.parseInt(this.dzscDao.getNum(
	// "DzscEmsPorWjHead", "seqNum")));
	// }
	// dzscEmsPorWjHead.setEmsNo(tempPtsEmsWjHead.getEmsNo());
	// dzscEmsPorWjHead.setSancEmsNo(tempPtsEmsWjHead.getSancEmsNo());
	// dzscEmsPorWjHead.setTradeCode(tempPtsEmsWjHead.getTradeCode());
	// dzscEmsPorWjHead.setTradeName(tempPtsEmsWjHead.getTradeName());
	// dzscEmsPorWjHead.setMachCode(tempPtsEmsWjHead.getMachCode());
	// dzscEmsPorWjHead.setMachName(tempPtsEmsWjHead.getMachName());
	// dzscEmsPorWjHead.setBeginDate(CommonUtils
	// .convertStrToDate(tempPtsEmsWjHead.getBeginDate()));
	// dzscEmsPorWjHead.setEndDate(CommonUtils
	// .convertStrToDate(tempPtsEmsWjHead.getEndDate()));
	// dzscEmsPorWjHead.setOutTradeCo(tempPtsEmsWjHead.getOutTradeCo());
	// dzscEmsPorWjHead.setLevyMode((LevyMode) this.dzscDao
	// .findCustomBaseEntity("LevyMode", "code", tempPtsEmsWjHead
	// .getLevyModeCode()));
	// dzscEmsPorWjHead.setLevyKind((LevyKind) this.dzscDao
	// .findCustomBaseEntity("LevyKind", "code", tempPtsEmsWjHead
	// .getLevyKindCode()));
	// dzscEmsPorWjHead.setPayMode((PayMode) this.dzscDao
	// .findCustomBaseEntity("PayMode", "code", tempPtsEmsWjHead
	// .getPayModeCode()));
	// dzscEmsPorWjHead.setMachiningType((MachiningType) this.dzscDao
	// .findCustomBaseEntity("MachiningType", "code", tempPtsEmsWjHead
	// .getMachiningTypeCode()));
	// dzscEmsPorWjHead.setEmsApprNo(tempPtsEmsWjHead.getEmsApprNo());
	// dzscEmsPorWjHead.setReceiveArea((District) this.dzscDao
	// .findCustomBaseEntity("District", "code", tempPtsEmsWjHead
	// .getReceiveAreaCode()));
	// dzscEmsPorWjHead.setInvestMode((InvestMode) this.dzscDao
	// .findCustomBaseEntity("InvestMode", "code", tempPtsEmsWjHead
	// .getInvestModeCode()));
	// dzscEmsPorWjHead.setFetchInMode((FetchInMode) this.dzscDao
	// .findCustomBaseEntity("FetchInMode", "code", tempPtsEmsWjHead
	// .getFetchInModeCode()));
	// dzscEmsPorWjHead.setInSaleRate(tempPtsEmsWjHead.getInSaleRate());
	// dzscEmsPorWjHead.setDeclareDate(CommonUtils
	// .convertStrToDate(tempPtsEmsWjHead.getDeclareDate()));
	// dzscEmsPorWjHead.setMaterielItemCount(tempPtsEmsWjHead
	// .getMaterielItemCount());
	// dzscEmsPorWjHead.setMachineCount(tempPtsEmsWjHead.getMachineCount());
	// dzscEmsPorWjHead.setProductItemCount(tempPtsEmsWjHead
	// .getProductItemCount());
	// dzscEmsPorWjHead.setManageObject(tempPtsEmsWjHead.getManageObject());
	// dzscEmsPorWjHead.setLimitFlag(tempPtsEmsWjHead.getLimitFlag());
	// if (dzscEmsPorWjHead.getLimitFlag() == null
	// || "".equals(dzscEmsPorWjHead.getLimitFlag().trim())) {
	// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// String beginDate = dateFormat.format(dzscEmsPorWjHead
	// .getBeginDate());
	// if (beginDate.compareTo("2008-08-23") < 0) {
	// dzscEmsPorWjHead.setLimitFlag(LimitFlag.ADJUST_BEFORE_EMS);
	// } else {
	// dzscEmsPorWjHead.setLimitFlag(LimitFlag.ADJUST_AFTER_EMS);
	// }
	// }
	// dzscEmsPorWjHead.setEquipMode(tempPtsEmsWjHead.getEquipMode());
	// dzscEmsPorWjHead.setCustomNo((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getCustomNoCode()));
	// dzscEmsPorWjHead.setRedDep((RedDep) this.dzscDao.findCustomBaseEntity(
	// "RedDep", "code", tempPtsEmsWjHead.getRedDepCode()));
	// dzscEmsPorWjHead.setEmsType(tempPtsEmsWjHead.getEmsType());
	// dzscEmsPorWjHead.setCopTrNo(tempPtsEmsWjHead.getCopTrNo());
	// dzscEmsPorWjHead.setIePort1((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort1Code()));
	// dzscEmsPorWjHead.setIePort2((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort2Code()));
	// dzscEmsPorWjHead.setIePort3((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort3Code()));
	// dzscEmsPorWjHead.setIePort4((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort4Code()));
	// dzscEmsPorWjHead.setIePort5((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort5Code()));
	// dzscEmsPorWjHead.setIePort6((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort6Code()));
	// dzscEmsPorWjHead.setIePort7((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort7Code()));
	// dzscEmsPorWjHead.setIePort8((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort8Code()));
	// dzscEmsPorWjHead.setIePort9((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort9Code()));
	// dzscEmsPorWjHead.setIePort10((Customs) this.dzscDao
	// .findCustomBaseEntity("Customs", "code", tempPtsEmsWjHead
	// .getIePort10Code()));
	// dzscEmsPorWjHead.setDeferDate(CommonUtils
	// .convertStrToDate(tempPtsEmsWjHead.getDeferDate()));
	// dzscEmsPorWjHead.setDestroyDate(CommonUtils
	// .convertStrToDate(tempPtsEmsWjHead.getDestroyDate()));
	// dzscEmsPorWjHead.setTrade((Trade) this.dzscDao.findCustomBaseEntity(
	// "Trade", "code", tempPtsEmsWjHead.getTradeModeCode()));
	// dzscEmsPorWjHead.setTradeCountry((Country) this.dzscDao
	// .findCustomBaseEntity("Country", "code", tempPtsEmsWjHead
	// .getTradeCountryCode()));
	// dzscEmsPorWjHead.setEnterpriseAddress(tempPtsEmsWjHead
	// .getEnterpriseAddress());
	// dzscEmsPorWjHead.setLinkMan(tempPtsEmsWjHead.getLinkMan());
	// dzscEmsPorWjHead.setContactTel(tempPtsEmsWjHead.getContactTel());
	// dzscEmsPorWjHead.setAgreementNo(tempPtsEmsWjHead.getAgreementNo());
	// dzscEmsPorWjHead.setIeContractNo(tempPtsEmsWjHead.getIeContractNo());
	// dzscEmsPorWjHead.setImContractNo(tempPtsEmsWjHead.getImContractNo());
	// dzscEmsPorWjHead.setImgAmount(tempPtsEmsWjHead.getImgAmount());
	// dzscEmsPorWjHead.setExgAmount(tempPtsEmsWjHead.getExgAmount());
	// dzscEmsPorWjHead.setCurr((Curr) this.dzscDao.findCustomBaseEntity(
	// "Curr", "code", tempPtsEmsWjHead.getCurrCode()));
	// dzscEmsPorWjHead.setWardshipRate(tempPtsEmsWjHead.getWardshipRate());
	// dzscEmsPorWjHead.setWardshipFee(tempPtsEmsWjHead.getWardshipFee());
	// dzscEmsPorWjHead.setTransac((Transac) this.dzscDao
	// .findCustomBaseEntity("Transac", "code", tempPtsEmsWjHead
	// .getTransacCode()));
	// dzscEmsPorWjHead.setNote(tempPtsEmsWjHead.getNote());
	// dzscEmsPorWjHead.setLastEmsNo(tempPtsEmsWjHead.getLastEmsNo());
	// dzscEmsPorWjHead.setCorrEmsNo(tempPtsEmsWjHead.getCorrEmsNo());
	// dzscEmsPorWjHead.setInputER(tempPtsEmsWjHead.getInputER());
	// dzscEmsPorWjHead.setDzscManageType(DzscManageType.COMPLEX);
	// dzscEmsPorWjHead.setDeclareState(DzscState.EXECUTE);
	// dzscEmsPorWjHead.setModifyMark(ModifyMarkState.UNCHANGE);
	//
	// dzscEmsPorWjHead.setEnterpriseAddress(((Company) CommonUtils
	// .getCompany()).getAddress());
	// dzscEmsPorWjHead.setLinkMan(((Company) CommonUtils.getCompany())
	// .getLinkman());
	// //
	// dzscEmsPorWjHead.setContactTel(((Company)CommonUtils.getCompany()).getLinkman());
	// dzscEmsPorWjHead.setIsImportFromQP(true);
	// if(dzscEmsPorWjHead.getSaveDate()==null){
	// dzscEmsPorWjHead.setSaveDate(dzscEmsPorWjHead.getBeginDate());
	// }
	// this.dzscDao.saveOrUpdate(dzscEmsPorWjHead);
	// return dzscEmsPorWjHead;
	// }
	//
	// /**
	// * 导入合同备案料件
	// *
	// * @param ptsTrListImg
	// * @param dzscEmsPorWjHead
	// */
	// private void importDzscEmsImgWj(List ptsTrListImg,
	// DzscEmsPorWjHead dzscEmsPorWjHead) {
	// Map<Integer, DzscEmsImgWj> imgBillMap = new HashMap<Integer,
	// DzscEmsImgWj>();
	// List list = this.dzscDao.findDzscEmsImgWjByParentId(dzscEmsPorWjHead
	// .getId());
	// for (int i = 0; i < list.size(); i++) {
	// DzscEmsImgWj imgBill = (DzscEmsImgWj) list.get(i);
	// imgBillMap.put(imgBill.getSeqNum(), imgBill);
	// }
	// for (int i = 0; i < ptsTrListImg.size(); i++) {
	// DzscQPEmsImgWj qpImgBill = (DzscQPEmsImgWj) ptsTrListImg.get(i);
	// DzscEmsImgWj imgBill = imgBillMap.get(qpImgBill.getSeqNum());
	// if (imgBill == null) {
	// imgBill = new DzscEmsImgWj();
	// imgBill.setDzscEmsPorWjHead(dzscEmsPorWjHead);
	// }
	// imgBill.setSeqNum(qpImgBill.getSeqNum());
	// imgBill.setFourSeqNum(qpImgBill.getFourSeqNum());
	// imgBill.setFourComplex(qpImgBill.getFourComplexCode());
	// imgBill.setFourName(qpImgBill.getFourName());
	// imgBill.setFourSpec(qpImgBill.getFourSpec());
	// imgBill.setFourUnit((Unit) this.dzscDao.findCustomBaseEntity(
	// "Unit", "code", qpImgBill.getFourUnitCode()));
	// Unit unit1 = (Unit) this.dzscDao.findCustomBaseEntity("Unit",
	// "code", qpImgBill.getFirstUnitCode());
	// Unit unit2 = (Unit) this.dzscDao.findCustomBaseEntity("Unit",
	// "code", qpImgBill.getSecondUnitCode());
	// Complex complex = getComplex(qpImgBill.getFourComplexCode());
	// if (unit1 == null) {
	// unit1 = (complex == null ? null : complex.getFirstUnit());
	// }
	// imgBill.setFirstUnit(unit1);
	// if (unit2 == null) {
	// unit1 = (complex == null ? null : complex.getSecondUnit());
	// }
	// imgBill.setSecondUnit(unit2);
	// imgBill.setModifyMark(ModifyMarkState.UNCHANGE);
	// this.dzscDao.saveDzscEmsImgWj(imgBill);
	// }
	// }
	//
	// /**
	// * 导入合同备案成品
	// *
	// * @param ptsTrListExg
	// * @param dzscEmsPorWjHead
	// */
	// private void importDzscEmsExgWj(List ptsTrListExg,
	// DzscEmsPorWjHead dzscEmsPorWjHead) {
	// Map<Integer, DzscEmsExgWj> imgBillMap = new HashMap<Integer,
	// DzscEmsExgWj>();
	// List list = this.dzscDao.findDzscEmsExgWjByParentId(dzscEmsPorWjHead
	// .getId());
	// for (int i = 0; i < list.size(); i++) {
	// DzscEmsExgWj imgBill = (DzscEmsExgWj) list.get(i);
	// imgBillMap.put(imgBill.getSeqNum(), imgBill);
	// }
	// for (int i = 0; i < ptsTrListExg.size(); i++) {
	// DzscQPEmsExgWj qpExgBill = (DzscQPEmsExgWj) ptsTrListExg.get(i);
	// DzscEmsExgWj exgBill = imgBillMap.get(qpExgBill.getSeqNum());
	// if (exgBill == null) {
	// exgBill = new DzscEmsExgWj();
	// exgBill.setDzscEmsPorWjHead(dzscEmsPorWjHead);
	// }
	// exgBill.setSeqNum(qpExgBill.getSeqNum());
	// exgBill.setFourSeqNum(qpExgBill.getFourSeqNum());
	// exgBill.setFourComplex(qpExgBill.getFourComplexCode());
	// exgBill.setFourName(qpExgBill.getFourName());
	// exgBill.setFourSpec(qpExgBill.getFourSpec());
	// exgBill.setFourUnit((Unit) this.dzscDao.findCustomBaseEntity(
	// "Unit", "code", qpExgBill.getFourUnitCode()));
	// Complex complex = getComplex(qpExgBill.getFourComplexCode());
	// Unit unit1 = (Unit) this.dzscDao.findCustomBaseEntity("Unit",
	// "code", qpExgBill.getFirstUnitCode());
	// if (unit1 == null) {
	// unit1 = (complex == null ? null : complex.getFirstUnit());
	// }
	// exgBill.setFirstUnit(unit1);
	// Unit unit2 = (Unit) this.dzscDao.findCustomBaseEntity("Unit",
	// "code", qpExgBill.getSecondUnitCode());
	// if (unit2 == null) {
	// unit2 = (complex == null ? null : complex.getSecondUnit());
	// ;
	// }
	// exgBill.setSecondUnit(unit2);
	// exgBill.setModifyMark(ModifyMarkState.UNCHANGE);
	// this.dzscDao.saveDzscEmsExgWj(exgBill);
	// }
	// }

}
