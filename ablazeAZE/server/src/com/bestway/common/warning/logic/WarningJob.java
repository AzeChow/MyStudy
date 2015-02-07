package com.bestway.common.warning.logic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractcav.dao.ContractCavDao;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcus.checkcancel.dao.CheckCancelDao;
import com.bestway.bcus.checkcancel.entity.CancelCusHead;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.authority.dao.AclUserDao;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.FixType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.RrportDelcareType;
import com.bestway.common.warning.dao.WarningDao;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.common.warning.entity.WarningItemFlag;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fixtureonorder.dao.FixtureContractDao;

public class WarningJob implements Job {
	private static Log logger = LogFactory.getLog(WarningJob.class);

	private Map<String, StringBuffer> warningCache = WarningCache.getCache();

	private AclUserDao aclUserDao = null;

	private DzscDao dzscDao = null;

	private EmsEdiTrDao emsEdiTrDao = null;

	private WarningDao warningDao = null;

	private CheckCancelDao checkCancelDao = null;

	private ContractDao contractDao = null;

	private FixtureContractDao fixtureContractDao = null;

	private WarningItem warningItem = null;

	private ContractCavDao contractCavDao = null;

	/**
	 * Job 的实现
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//
		// stop scheduler in exception
		//
		JobDataMap jobDateMap = context.getJobDetail().getJobDataMap();
		Company company = (Company) jobDateMap.get("company");

		aclUserDao = (AclUserDao) jobDateMap.get("aclUserDao");
		warningItem = (WarningItem) jobDateMap.get("warningItem");
		dzscDao = (DzscDao) jobDateMap.get("dzscDao");
		emsEdiTrDao = (EmsEdiTrDao) jobDateMap.get("emsEdiTrDao");
		warningDao = (WarningDao) jobDateMap.get("warningDao");
		checkCancelDao = (CheckCancelDao) jobDateMap.get("checkCancelDao");
		contractDao = (ContractDao) jobDateMap.get("contractDao");
		fixtureContractDao = (FixtureContractDao) jobDateMap
				.get("fixtureContractDao");
		contractCavDao = (ContractCavDao) jobDateMap.get("contractCavDao");

		try {
			logger.info("[JBCUS WARNING START] " + company.getName()
					+ " 预警系统开始生成提示信息 .....");
			warning(company);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("[JBCUS WARNING START] " + company.getName()
				+ " 预警系统结束生成提示信息 .....");
	}

	/**
	 * 
	 * warning method 所有生成的提示信息在这里调用 ,尽量每个方法要求 try catch finally
	 * 在这里调用的方法不能用CommonUtils.getCompany() 方法来获得参数 StringBuffer stringBuffer
	 * =makeContractWarningInfo(Company company){...};
	 * 
	 */
	public void warning(Company company) {
		if (warningItem == null || warningItem.getWarningItemFlag() == null) {
			return;
		}
		//
		// 预警备选项目
		//
		int flag = warningItem.getWarningItemFlag().intValue();
		switch (flag) {
		case WarningItemFlag.DZSC_CONTRACT_AT_TERM:
			this.setDzscEmsPorHeadContractAtTermTip(company);
			break;
		case WarningItemFlag.DZSC_CHECKCANCEL_AT_TERM:
			this.setDzscEmsPorHeadChangeCancelAtTermTip(company);
			break;
		case WarningItemFlag.BCUS_CHECKCANCEL_AT_TERM:
			this.setEmsHeadH2kChangeCancelAtTermTip(company);
			break;
		case WarningItemFlag.BCUS_OUT_FACTORY_NOT_CUSTOM:
			this.setBcusOutFactoryCustom(ProjectType.BCUS, company, true);
			break;
		case WarningItemFlag.BCUS_OUT_CUSTOM_NOT_FACTORY:
			this.setBcusOutFactoryCustom(ProjectType.BCUS, company, false);
			break;
		case WarningItemFlag.BCS_OUT_FACTORY_NOT_CUSTOM:
			this.setBcusOutFactoryCustom(ProjectType.BCS, company, true);
			break;
		case WarningItemFlag.BCS_OUT_CUSTOM_NOT_FACTORY:
			this.setBcusOutFactoryCustom(ProjectType.BCS, company, false);
			break;
		case WarningItemFlag.DZSC_OUT_FACTORY_NOT_CUSTOM:
			this.setBcusOutFactoryCustom(ProjectType.DZSC, company, true);
			break;
		case WarningItemFlag.DZSC_OUT_CUSTOM_NOT_FACTORY:
			this.setBcusOutFactoryCustom(ProjectType.DZSC, company, false);
			break;
		case WarningItemFlag.DZSC_CONTRACT_MATERIEL:
			this.setDzscContractMateriel(company);
			break;
		case WarningItemFlag.BCS_CONTRACT_MATERIEL:
			this.setBcsContractMateriel(company);
			break;
		case WarningItemFlag.FIXTURE_CUSTOM_OVERSEE:
			this.setFixtureCustomOversee(company);
			break;
		case WarningItemFlag.FECAVBILL_NO_EXPORT_DRAWBACK_MATERIEL:
			this.setFecavbillNoExportDrawback(company);
			break;
		case WarningItemFlag.BCS_CONTRACT_AT_TERM:
			this.setBcsContractAtTermTip(company);
			break;
		case WarningItemFlag.BCS_CHECKCANCEL_AT_TERM:
			this.setBcsCheckCancelAtTermTip(company);
			break;
		case WarningItemFlag.BCUS_EmsHeadH2kVersion_MORE_540_DAYS:
			this.makeEmsHeadH2kVersionForbid(company);
			break;
		case WarningItemFlag.BCUS_Value_Added_Rate:
			this.valueAddedRateAlert(company);
			break;
		case WarningItemFlag.BCS_IE_REWORK_BACK:
			this
					.setIECustomDeclareBackReworkAtTermTip(ProjectType.BCS,
							company);
			break;
		case WarningItemFlag.BCUS_IE_REWORK_BACK:
			this.setIECustomDeclareBackReworkAtTermTip(ProjectType.BCUS,
					company);
			break;
		case WarningItemFlag.DZSC_IE_REWORK_BACK:
			this.setIECustomDeclareBackReworkAtTermTip(ProjectType.DZSC,
					company);
			break;
		default:
			break;
		}
	}

	private void makeEmsHeadH2kVersionForbid(Company company) {
		List emsList = emsEdiTrDao.findEmsHeadH2kInExecut(company);
		if (emsList.size() == 0) {
			return;
		}
		EmsHeadH2k head = (EmsHeadH2k) emsList.get(0);
		Double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();
		if (xDay == null || xDay.intValue() > 540 || xDay.intValue() < 0) {
			return;
		}
		// -----------------------------------------------------------------
		// 查找所有可能过期的
		List allList = emsEdiTrDao.findEmsHeadH2kBom(head, xDay.intValue(),
				company);
		if (allList.size() == 0) {
			return;
		}
		Map allBOMmap = new HashMap();
		for (int i = 0; i < allList.size(); i++) {
			Object[] ojs = (Object[]) allList.get(i);
			String key = (ojs[0] == null ? "" : ojs[0].toString())
					+ (ojs[1] == null ? "" : ojs[1].toString());
			allBOMmap.put(key, ojs[2]);
		}
		// -----------------------------------------------------------------
		List list = this.emsEdiTrDao.findCustomsDeclarationCommInfo(xDay
				.intValue(), company);
		for (int i = 0; i < list.size(); i++) {
			Object[] ojs = (Object[]) list.get(i);
			String key = (ojs[0] == null ? "" : ojs[0].toString())
					+ (ojs[1] == null ? "" : ojs[1].toString());
			if (allBOMmap.get(key) != null) {
				allBOMmap.remove(key);
			}
		}
		// ------------------------------------------------------------------------
		List flist = emsEdiTrDao.findEmsEdiForbid(company);
		for (int i = 0; i < flist.size(); i++) {
			Object[] ojs = (Object[]) flist.get(i);
			String key = (ojs[0] == null ? "" : ojs[0].toString())
					+ (ojs[1] == null ? "" : ojs[1].toString());
			if (allBOMmap.get(key) != null) {
				allBOMmap.remove(key);
			}
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("帐册中共有" + allBOMmap.values().size() + "项成品版本，已超过 "
				+ xDay.intValue() + "天没有使用。");
		if (xDay == 540) {
			stringBuffer.append("\n现已加入禁用商品之中！");
			List rlist = new ArrayList(allBOMmap.values());
			for (int i = 0; i < rlist.size(); i++) {
				EmsHeadH2kVersion version = (EmsHeadH2kVersion) rlist.get(i);
				CommdityForbid fd = new CommdityForbid();
				fd.setType(MaterielType.FINISHED_PRODUCT);
				fd
						.setComplex(version.getEmsHeadH2kExg().getComplex()
								.getCode());
				fd.setName(version.getEmsHeadH2kExg().getName());
				fd.setSpec(version.getEmsHeadH2kExg().getSpec());
				fd.setSeqNum(version.getEmsHeadH2kExg().getSeqNum().toString());
				fd
						.setVersion(version.getEmsHeadH2kExg().getVersion() == null ? ""
								: version.getEmsHeadH2kExg().getVersion()
										.toString());
				fd.setUnit(version.getEmsHeadH2kExg().getUnit() == null ? ""
						: version.getEmsHeadH2kExg().getUnit().toString());
				fd.setCompany(CommonUtils.getCompany());
				fd.setForbiddate(new Date());
				fd.setCreateDate(new Date());
				fd.setForbiduser(CommonUtils.getAclUser() == null ? null
						: CommonUtils.getAclUser().getName());
				fd.setCompany(company);
				this.emsEdiTrDao.saveOrUpdate(fd);
			}
		}
		makeWarningInfo(company, stringBuffer);
		// ------------------------------------------------------------------------
	}

	/** 电子手册合同到期 <= X 天提醒 */
	private void setDzscEmsPorHeadContractAtTermTip(Company company) {
		//
		// 在设定的后台运行时间内产生预警信息,合同到期之前进行系统提醒,
		// 电子手册合同到期提示的内容（例如)
		// [ 电子手册 合同手册号是[1234567890] 的合同到期天数 <= " + 5 ]
		// [ 电子手册 合同手册号是[1234567891] 的合同到期天数 <= " + 2 ]
		// ......
		//
		StringBuffer stringBuffer = new StringBuffer();

		double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();

		List dataSource = this.dzscDao.findDzscEmsPorHeadExecute(company);
		for (int i = 0; i < dataSource.size(); i++) {
			DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) dataSource.get(i);
			String emsNo = dzscEmsPorHead.getEmsNo();
			Date endDate = dzscEmsPorHead.getEndDate();
			Date currentDate = new Date();
			//
			// 当前时间在合同到期之前提醒,之后不提醒
			//
			if (currentDate.before(endDate)) { // true
				long diffMillis = endDate.getTime() - currentDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 小于 <= X Day 时
				//
				if (diffDays <= xDay || xDay == 0.0) {
					stringBuffer.append("电子手册 合同手册号是[" + emsNo + "] 的合同到期天数 = "
							+ diffDays + "\n");
				}
			}
		}
		makeWarningInfo(company, stringBuffer);
	}

	/** 电子手册合同核销到期 <= X 天提醒 */
	private void setDzscEmsPorHeadChangeCancelAtTermTip(Company company) {
		//
		// 在设定的后台运行时间内产生预警信息,合同核销到期之前进行系统提醒,
		// 电子手册合同核销到期提示的内容（例如)
		// [ 电子手册 合同手册号是[1234567890] 的合同核销到期天数 <= " + 7 ]
		// [ 电子手册 合同手册号是[1234567891] 的合同核销到期天数 <= " + 5 ]
		// ......
		//
		StringBuffer stringBuffer = new StringBuffer();

		double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();
		List dataSource = this.dzscDao.findDzscEmsPorHeadExecute(company);
		for (int i = 0; i < dataSource.size(); i++) {
			DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) dataSource.get(i);
			String emsNo = dzscEmsPorHead.getEmsNo();
			Date destroyDate = dzscEmsPorHead.getDestroyDate();
			Date currentDate = new Date();
			//
			// 当前时间在合同核销到期之前提醒,之后不提醒
			//
			if (currentDate.before(destroyDate)) { // true
				long diffMillis = destroyDate.getTime() - currentDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 小于 <= X Day 时
				//
				if (diffDays <= xDay || xDay == 0.0) {
					stringBuffer.append("电子手册 合同手册号是[" + emsNo
							+ "] 的合同核销到期天数 = " + diffDays + "\n");
				}
			}
		}
		makeWarningInfo(company, stringBuffer);
	}

	/** 电子帐册核销到期 <= X 天提醒 */
	private void setEmsHeadH2kChangeCancelAtTermTip(Company company) {
		//
		// 在设定的后台运行时间内产生预警信息,电子帐册核销到期之前进行系统提醒,
		// 电子帐册核销到期提示的内容（例如)
		// [ 电子帐册 帐册号是[1234567890] 的滚动核销到期天数 <= " + 7 ]
		//
		StringBuffer stringBuffer = new StringBuffer();
		Double xDay = warningItem.getAmount();
		//
		// 获得正在执行的最大的一个正式报核
		//
		List<CancelCusHead> cancelCusHeadList = checkCancelDao
				.findCancelCusHead(company, RrportDelcareType.DELCARE);
		int cancelTimes = 0;
		CancelCusHead lastlyCancelCusHead = null;
		for (int i = 0; i < cancelCusHeadList.size(); i++) {
			CancelCusHead cancelCusHead = cancelCusHeadList.get(i);
			int temp = 0;
			try {
				temp = Integer
						.parseInt(cancelCusHead.getCancelTimes() == null ? "0"
								: cancelCusHead.getCancelTimes());
			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
			if (cancelTimes < temp) {
				cancelTimes = temp;
				lastlyCancelCusHead = cancelCusHead;
			}
		}
		//
		// 如果存在不存在最后一次正式报核记录
		//
		Date beginDate = null; // 核销开始日期
		String emsNo = "";
		if (lastlyCancelCusHead == null) {
			List dataSource = this.emsEdiTrDao
					.findEmsHeadH2kInExecuting(company);
			if (dataSource.size() > 0) {
				EmsHeadH2k emsHeadH2k = (EmsHeadH2k) dataSource.get(0);
				emsNo = emsHeadH2k.getEmsNo();
				beginDate = emsHeadH2k.getBeginDate();
			}
		} else {
			//
			// 正式报核结束日期即核销开始日期
			//
			beginDate = lastlyCancelCusHead.getEndDate();
			emsNo = lastlyCancelCusHead.getEmsNo();
		}

		if (beginDate != null) {
			//
			// 核销期 = 核销期开始 + 180 天
			//
			Calendar beginCalendar = Calendar.getInstance();
			beginCalendar.setTime(beginDate);
			Calendar destroyCalendar = (Calendar) beginCalendar.clone();
			destroyCalendar.add(Calendar.MONTH, 6);

			Date destroyDate = destroyCalendar.getTime();
			Date currentDate = new Date();
			//
			// 当前时间在合同核销到期之前提醒,之后不提醒
			//
			if (currentDate.before(destroyDate)) { // true
				long diffMillis = destroyDate.getTime() - currentDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 小于 <= X Day 时
				//
				if (diffDays <= xDay) {
					stringBuffer.append("电子帐册 帐册号是[" + emsNo + "] 的滚动核销到期天数 = "
							+ diffDays + "\n");
				}
			}
			//
			// 生成预警信息
			//
			makeWarningInfo(company, stringBuffer);
		}
	}

	/** 电子帐册 已送未转,已转未送 提醒(成品) */
	private void setBcusOutFactoryCustom(int projectType, Company company,
			boolean isTransfer) {
		//
		// 获得电子帐册已生效的,申报时间少于当前日期的报表单明细
		// 与转厂单据中已生效的,生效日期小于当前日期的已结转数量
		// 按 报关名称+规格+单位+客户 进行的对比(进行预警)
		// 例如:
		// [ 电子帐册 [明人公司/电脑/一般规格/台] 已送未转 == 80" ]
		// ......
		//
		StringBuffer stringBuffer = new StringBuffer();
		Double xAmount = warningItem.getAmount();
		//
		// 以 报关名+称规格+单位+客户 为key的 map
		//
		Map<String, Double> carryForwardByCustomerMap = new HashMap<String, Double>();
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		String selects = "select sum(a.commAmount),a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.name  ";
		String groupBy = "group by a.commName,a.commSpec, a.unit.name,a.baseCustomsDeclaration.customer.name ";
		String leftOuter = " left join a.unit left join a.baseCustomsDeclaration.customer ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.TRANSFER_FACTORY_EXPORT, null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpDate", "<=", new Date(), null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));// 生效
		// conditions.add(new Condition("and", "(",
		// "baseCustomsDeclaration.tradeMode.code", "=", "0255", null));// 来料深加工
		// conditions.add(new Condition("or", null,
		// "baseCustomsDeclaration.tradeMode.code", "=", "0654", ")"));// 进料深加工

		List listCustomsDeclarationCommInfo = new ArrayList();
		String projectName = "电子帐册";
		if (ProjectType.BCUS == projectType) {

			listCustomsDeclarationCommInfo = warningDao.commonSearch(selects,
					"CustomsDeclarationCommInfo", conditions, groupBy,
					leftOuter, company);
			projectName = "电子帐册";

		} else if (ProjectType.DZSC == projectType) {

			listCustomsDeclarationCommInfo = warningDao.commonSearch(selects,
					"DzscCustomsDeclarationCommInfo", conditions, groupBy,
					leftOuter, company);
			projectName = "电子手册";

		} else if (ProjectType.BCS == projectType) {

			listCustomsDeclarationCommInfo = warningDao.commonSearch(selects,
					"BcsCustomsDeclarationCommInfo", conditions, groupBy,
					leftOuter, company);
			projectName = "纸质合同";

		}
		makeMap(carryForwardByCustomerMap, listCustomsDeclarationCommInfo);

		//
		// 以 报关名+称规格+单位+客户 为key的 map
		//
		Map<String, Double> transferFactoryCommodityInfoMap = new HashMap<String, Double>();
		//
		// 以名称+规格+单位,客户来分组进行统计
		//
		selects = "select sum(a.transFactAmount),a.commName,a.commSpec, a.unit.name,a.transferFactoryBill.scmCoc.name  ";
		groupBy = "group by a.commName,a.commSpec, a.unit.name,a.transferFactoryBill.scmCoc.name ";
		leftOuter = " left join a.unit left join a.transferFactoryBill.scmCoc ";
		List<Condition> conditionTransfer = new ArrayList<Condition>();
		conditionTransfer.add(new Condition("and", null,
				"transferFactoryBill.isImpExpGoods", "=", false, null));
		conditionTransfer
				.add(new Condition("and", null,
						"transferFactoryBill.beginAvailability", "<=",
						new Date(), null));
		conditionTransfer.add(new Condition("and", null,
				"transferFactoryBill.isAvailability", "=", true, null));// 生效

		List listTransferFactoryCommodityInfo = warningDao.commonSearch(
				selects, "TransferFactoryCommodityInfo", conditionTransfer,
				groupBy, leftOuter, company);
		makeMap(transferFactoryCommodityInfoMap,
				listTransferFactoryCommodityInfo);
		//
		// isTransfer == true ------- 已送未转
		//
		if (isTransfer == true) {
			String[] keys = transferFactoryCommodityInfoMap.keySet().toArray(
					new String[0]);
			for (String key : keys) {
				Double alreadTransferAmount = transferFactoryCommodityInfoMap
						.get(key);
				Double alreadCustomsAmount = carryForwardByCustomerMap.get(key) == null ? 0.0
						: carryForwardByCustomerMap.get(key);
				//
				// 已送未转 > xAmount
				//
				double diffAmount = alreadTransferAmount - alreadCustomsAmount;
				if (diffAmount >= xAmount) {
					stringBuffer.append(projectName + " : [  " + key
							+ "  ] 已送未转 == " + diffAmount + "\n");
				}
			}
		} else {// isTransfer == false ------- 已转未送
			String[] keys = carryForwardByCustomerMap.keySet().toArray(
					new String[0]);
			for (String key : keys) {
				Double alreadCustomsAmount = carryForwardByCustomerMap.get(key);
				Double alreadTransferAmount = transferFactoryCommodityInfoMap
						.get(key) == null ? 0.0
						: transferFactoryCommodityInfoMap.get(key);
				//
				// 已转未送 > xAmount
				//
				double diffAmount = alreadCustomsAmount - alreadTransferAmount;
				if (diffAmount >= xAmount) {
					stringBuffer.append(projectName + " : [  " + key
							+ "  ] 已转未送 == " + diffAmount + "\n");
				}
			}
		}
		//
		// 生成预警信息
		//
		makeWarningInfo(company, stringBuffer);
	}

	/**
	 * 生成结转 map
	 * 
	 * @param carryForwardByCustomerMap
	 * @param list
	 */
	private void makeMap(Map<String, Double> carryForwardByCustomerMap,
			List list) {
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			// 以 报关名称规格+单位为key的
			Double commAmount = (Double) objs[0] == null ? 0.0
					: (Double) objs[0];
			String name = (String) objs[1] == null ? "" : (String) objs[1];
			String spec = (String) objs[2] == null ? "" : (String) objs[2];
			String unitName = (String) objs[3] == null ? "" : (String) objs[3];
			String customerCode = (String) objs[4] == null ? ""
					: (String) objs[4];
			String key = name + "/" + spec + "/" + unitName;
			String hasCustomerKey = customerCode + "/" + key;
			if (carryForwardByCustomerMap.get(hasCustomerKey) == null) {
				carryForwardByCustomerMap.put(hasCustomerKey, commAmount);
			} else {
				Double commA = carryForwardByCustomerMap.get(key);
				commA += commAmount;
				carryForwardByCustomerMap.put(key, commA);
			}
		}
	}

	private void setDzscContractMateriel(Company company) {
		//
		// 获得备案的合同成品与料件平衡差值,进行预警
		// 例如:
		// [ 电子手册 手册号 [1234567890] 的料件为 [数码相机配件/X规格/个] 的数量备案差 = 1580 ]
		// .....
		//
		StringBuffer stringBuffer = new StringBuffer();
		Double xAmount = warningItem.getAmount();

		List dataSource = this.dzscDao.findDzscEmsPorHeadApplyOrChange(company);
		for (int i = 0; i < dataSource.size(); i++) {
			DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) dataSource.get(i);
			String emsNo = dzscEmsPorHead.getEmsNo();
			if (emsNo == null || emsNo.trim().equals("")) {
				continue;
			}

			List dzscEmsBomBillList = this.dzscDao
					.findDzscEmsBomBill(dzscEmsPorHead.getId());
			List dzscEmsImgBillList = this.dzscDao
					.findDzscEmsImgBill(dzscEmsPorHead.getId());
			//
			// key = 料件总表序号 imgSeqNum
			//
			Map<Integer, Double> dzscEmsBomBillMap = new HashMap<Integer, Double>();
			for (int j = 0; j < dzscEmsBomBillList.size(); j++) {
				DzscEmsBomBill dzscEmsBomBill = (DzscEmsBomBill) dzscEmsBomBillList
						.get(j);

				double exgAmount = dzscEmsBomBill.getDzscEmsExgBill()
						.getAmount() == null ? 0.0 : dzscEmsBomBill
						.getDzscEmsExgBill().getAmount();
				double unitDosage = dzscEmsBomBill.getUnitDosage() == null ? 0.0
						: dzscEmsBomBill.getUnitDosage();
				double imgAmount = exgAmount * unitDosage;

				Integer key = dzscEmsBomBill.getImgSeqNum();
				if (key == null) {
					continue;
				}
				if (dzscEmsBomBillMap.get(key) == null) {
					dzscEmsBomBillMap.put(key, imgAmount);
				} else {
					dzscEmsBomBillMap.put(key, dzscEmsBomBillMap.get(key)
							+ imgAmount);
				}
			}

			for (int j = 0; j < dzscEmsImgBillList.size(); j++) {
				DzscEmsImgBill dzscEmsImgBill = (DzscEmsImgBill) dzscEmsImgBillList
						.get(j);

				String name = dzscEmsImgBill.getName();
				String spec = dzscEmsImgBill.getSpec() == null
						|| "".equals(dzscEmsImgBill.getSpec()) ? "" : "/"
						+ dzscEmsImgBill.getSpec();
				String unitName = dzscEmsImgBill.getUnit() == null ? "" : "/"
						+ dzscEmsImgBill.getUnit().getName();

				double imgAmount = dzscEmsImgBill.getAmount() == null ? 0.0
						: dzscEmsImgBill.getAmount();
				Integer seqNum = dzscEmsImgBill.getSeqNum();
				if (seqNum == null) {
					continue;
				}
				Double bomAmount = dzscEmsBomBillMap.get(seqNum);
				bomAmount = bomAmount == null ? 0.0 : bomAmount;

				//
				// 电子手册合同料件 >= -X 时提醒
				//
				double diffAmount = bomAmount - imgAmount;
				if (diffAmount >= xAmount) {
					//
					// 电子手册 手册号 [1234567890] 的料件为 [数码相机配件/X规格/个] 的数量备案差 = 1580
					//
					stringBuffer.append("电子手册 手册号 [" + emsNo + "] 的料件为 [ "
							+ name + spec + unitName + " ] 的数量备案差数 = "
							+ diffAmount + " \n");
				}
			}
		}
		//
		// 生成预警信息
		//
		makeWarningInfo(company, stringBuffer);
	}

	private void setBcsContractMateriel(Company company) {
		//
		// 获得备案的合同成品与料件平衡差值,进行预警
		// 例如:
		// [ 电子手册 手册号 [1234567890] 的料件为 [数码相机配件/X规格/个] 的数量备案差 = 1580 ]
		// .....
		//
		StringBuffer stringBuffer = new StringBuffer();
		Double xAmount = warningItem.getAmount();

		List dataSource = this.contractDao.findContractByProcessExe(company);
		for (int i = 0; i < dataSource.size(); i++) {
			Contract contract = (Contract) dataSource.get(i);
			String emsNo = contract.getEmsNo();

			List contractBomList = this.contractDao
					.findContractBomByContractParentId(contract.getId());
			List contractImgList = this.contractDao
					.findContractImgByContractId(contract.getId());
			//
			// key = 料件总表序号 imgSeqNum
			//
			Map<Integer, Double> contractBomMap = new HashMap<Integer, Double>();
			for (int j = 0; j < contractBomList.size(); j++) {
				ContractBom contractBom = (ContractBom) contractBomList.get(j);

				double exgAmount = contractBom.getContractExg()
						.getExportAmount() == null ? 0.0 : contractBom
						.getContractExg().getExportAmount();
				double unitDosage = contractBom.getUnitDosage() == null ? 0.0
						: contractBom.getUnitDosage();
				double imgAmount = exgAmount * unitDosage;

				Integer key = contractBom.getContractImgSeqNum();
				if (contractBomMap.get(key) == null) {
					contractBomMap.put(key, imgAmount);
				} else {
					contractBomMap
							.put(key, contractBomMap.get(key) + imgAmount);
				}
			}

			for (int j = 0; j < contractImgList.size(); j++) {
				ContractImg contractImg = (ContractImg) contractImgList.get(j);

				String name = contractImg.getName();
				String spec = contractImg.getSpec() == null
						|| "".equals(contractImg.getSpec()) ? "" : "/"
						+ contractImg.getSpec();
				String unitName = contractImg.getUnit() == null ? "" : "/"
						+ contractImg.getUnit().getName();

				double imgAmount = contractImg.getAmount() == null ? 0.0
						: contractImg.getAmount();
				Integer seqNum = contractImg.getSeqNum();
				double bomAmount = contractBomMap.get(seqNum);
				//
				// 电子手册合同料件 >= -X 时提醒
				//
				double diffAmount = bomAmount - imgAmount;
				if (diffAmount >= xAmount) {
					//
					// 电子手册 手册号 [1234567890] 的料件为 [数码相机配件/X规格/个] 的数量备案差 = 1580
					//
					stringBuffer.append("纸质合同 手册号 [" + emsNo + "] 的料件为 [ "
							+ name + spec + unitName + " ] 的数量备案差数 = "
							+ diffAmount + " \n");
				}
			}
		}
		//
		// 生成预警信息
		//
		makeWarningInfo(company, stringBuffer);
	}

	/** 设备监管到期前天数小于或等于 X 天的时候提醒 */
	private void setFixtureCustomOversee(Company company) {
		//
		// 在设定的后台运行时间内产生预警信息,设备监管到期之前进行系统提醒,
		// 设备监管到期提示的内容（例如)
		// [ 设备类型[三资/来料] 备案号[123456]报关单号[123456789] 的设备监管到期天数 <= " + 5 ]
		// ......
		//
		StringBuffer stringBuffer = new StringBuffer();

		double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();
		List dataSource = this.fixtureContractDao
				.findFixtureCustomsDeclaration("CustomsDeclaration", company);
		List list = this.fixtureContractDao.findFixtureCustomsDeclaration(
				"BcsCustomsDeclaration", company);
		if (list != null && list.size() > 0)
			dataSource.add(list);
		list = this.fixtureContractDao.findFixtureCustomsDeclaration(
				"DzscCustomsDeclaration", company);
		if (list != null && list.size() > 0)
			dataSource.add(list);
		for (int i = 0; i < dataSource.size(); i++) {
			BaseCustomsDeclaration customsDeclaration = (BaseCustomsDeclaration) dataSource
					.get(i);
			String emsNo = customsDeclaration.getEmsHeadH2k();
			String declarationCode = customsDeclaration
					.getCustomsDeclarationCode();
			Integer fixType = customsDeclaration.getFixType();
			Date endDate = customsDeclaration.getDeclarationDate();
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(endDate);
			beginCal.add(Calendar.YEAR, 5);
			endDate = beginCal.getTime();
			Date currentDate = new Date();
			//
			// 当前时间在合同到期之前提醒,之后不提醒
			//

			if (currentDate.before(endDate)) { // true
				long diffMillis = endDate.getTime() - currentDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 小于 <= X Day 时
				//
				if (fixType == FixType.SHANZHI) {
					String type = "三资设备";
					if (diffDays <= xDay || xDay == 0.0) {
						stringBuffer.append(i + ". 设备[" + type + "] 报关单["
								+ declarationCode + "] 中的设备监管到期天数 = "
								+ diffDays + "\n");
					}
				} else if (fixType == FixType.LAILIAO) {
					String type = "来料设备";
					if (diffDays <= xDay || xDay == 0.0) {
						stringBuffer.append(i + ". 设备[" + type + "] 报关单["
								+ declarationCode + "] 中的设备监管到期天数 = "
								+ diffDays + "\n");
					}
				}

			}
		}
		System.out.println("stringBuffer---------------------------------"
				+ stringBuffer);
		makeWarningInfo(company, stringBuffer);
	}

	/** 出口报关单大于或等于 X 天还没有收到出口退税联时提醒 */
	private void setFecavbillNoExportDrawback(Company company) {
		//
		// 在设定的后台运行时间内产生预警信息,设备监管到期之前进行系统提醒,
		// 监管到期提示的内容（例如)
		// [ 外汇核销管理--核销单使用中 核销单号[123456]报关单号[123456789] 的核销单没收到退税联的天数 >= " + 75
		// ]
		// ......
		//
		StringBuffer stringBuffer = new StringBuffer();

		Double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();
		List dataSource = this.dzscDao.findFecavBill(company);

		System.out.println("dataSource.size()----------------"
				+ dataSource.size());
		DecimalFormat myformat = new DecimalFormat("##");
		System.out.println("xDay----------------" + myformat.format(xDay));

		stringBuffer.append(" 外汇核销管理--核销单使用中 没收到退税联的天数 >= "
				+ myformat.format(xDay) + ":\n");
		for (int i = 0; i < dataSource.size(); i++) {
			FecavBill fecavBill = (FecavBill) dataSource.get(i);
			String code = fecavBill.getCode();// 核销单号
			String dustomsDeclarationCode = fecavBill
					.getCustomsDeclarationCode();// 报关单号
			Date declareDate = fecavBill.getDeclareDate();// 申报日期
			double totalPrice = fecavBill.getTotalPrice();// 申报日期
			Date currentDate = new Date();
			//
			// 当前时间在合同到期之前提醒,之后不提醒
			//

			if (currentDate.after(declareDate)) { // true
				long diffMillis = currentDate.getTime() - declareDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 大于 >= X Day 时
				//

				if (diffDays >= xDay) {
					System.out.println("diffDays >= xDay----------------"
							+ (diffDays >= xDay));
					// stringBuffer.append((i+1)+". 外汇核销管理--核销单使用中 核销单号[" + code
					// + "] 报关单号["
					// + dustomsDeclarationCode + "] 金额["
					// + totalPrice+"] 的核销单没收到退税联的天数 >= "
					// + diffDays + "\n");
					stringBuffer.append((i + 1) + ". 核销单号[" + code + "] 报关单号["
							+ dustomsDeclarationCode + "] 金额[" + totalPrice
							+ "]\n");
				}
			}
		}
		System.out.println("stringBuffer---------------------------------"
				+ stringBuffer);
		makeWarningInfo(company, stringBuffer);
	}

	/** 生成预警信息为当前公司的每一个用户 */
	private void makeWarningInfo(Company company, StringBuffer stringBuffer) {
		if (stringBuffer.toString().trim().equals("")) {
			return;
		}
		//
		// 让第个用户有一个预警提示信息
		//
		String companyId = company.getId();
		List list = aclUserDao.findUsers(company);
		for (int j = 0; j < list.size(); j++) {
			AclUser aclUser = (AclUser) list.get(j);
			//
			// key = company + user name + warning No.
			//
			String key = companyId + aclUser.getName() + warningItem.getId();
			warningCache.put(key, stringBuffer);
		}
		//
		// 存入文件
		//
		WarningCache.store();
	}

	/** 电子化手册合同到期 <= X 天提醒 */
	private void setBcsContractAtTermTip(Company company) {
		//
		// 在设定的后台运行时间内产生预警信息,合同到期之前进行系统提醒,
		// 电子化手册合同到期提示的内容（例如)
		// [ 电子化手册 合同手册号是[1234567890] 的合同到期天数 <= " + 5 ]
		// [ 电子化手册 合同手册号是[1234567891] 的合同到期天数 <= " + 2 ]
		// ......
		//
		StringBuffer stringBuffer = new StringBuffer();

		double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();

		List dataSource = contractDao.findContract(company);
		for (int i = 0; i < dataSource.size(); i++) {
			Contract contract = (Contract) dataSource.get(i);
			String emsNo = contract.getEmsNo();
			Date endDate = contract.getEndDate();
			Date currentDate = new Date();
			//
			// 当前时间在合同到期之前提醒,之后不提醒
			//
			if (currentDate.before(endDate)) { // true
				long diffMillis = endDate.getTime() - currentDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 小于 <= X Day 时
				//
				if (diffDays <= xDay || xDay == 0.0) {
					stringBuffer.append("电子化手册 合同手册号是[" + emsNo
							+ "] 的合同到期天数 = " + diffDays + "\n");
				}
			}
		}
		makeWarningInfo(company, stringBuffer);
	}

	/** 电子化手册合同核销到期 <= X 天提醒 */
	private void setBcsCheckCancelAtTermTip(Company company) {
		//
		// 在设定的后台运行时间内产生预警信息,合同核销到期之前进行系统提醒,
		// 电子化手册合同核销到期提示的内容（例如)
		// [ 电子化手册 合同手册号是[1234567890] 的合同核销到期天数 <= " + 7 ]
		// [ 电子化手册 合同手册号是[1234567891] 的合同核销到期天数 <= " + 5 ]
		// ......
		//
		StringBuffer stringBuffer = new StringBuffer();

		double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();
		List dataSource = this.contractDao.findContract(company);
		for (int i = 0; i < dataSource.size(); i++) {
			Contract contract = (Contract) dataSource.get(i);
			String emsNo = contract.getEmsNo();
			Date destroyDate = contract.getDestroyDate();
			Date currentDate = new Date();
			//
			// 当前时间在合同核销到期之前提醒,之后不提醒
			//
			if (currentDate.before(destroyDate)) { // true
				long diffMillis = destroyDate.getTime() - currentDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 小于 <= X Day 时
				//
				if (diffDays <= xDay || xDay == 0.0) {
					stringBuffer.append("电子化手册 合同手册号是[" + emsNo
							+ "] 的合同核销到期天数 = " + diffDays + "\n");
				}
			}
		}
		makeWarningInfo(company, stringBuffer);
	}

	/**
	 * 合同增值率低于系统设置增值率就报警
	 * 
	 * @param company
	 */
	private void valueAddedRateAlert(Company company) {
		StringBuffer stringBuffer = new StringBuffer();
		double xAmount = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();
		List dataSource = this.contractDao
				.findContractEmsNOByProcessExe(company);
		for (int i = 0; i < dataSource.size(); i++) {
			System.out.println("dataSource.size()=" + dataSource.size());
			String emsNo = (String) dataSource.get(i);
			List list = this.contractCavDao.findContractCavByEmsNo(emsNo);
			for (int j = 0; j < list.size(); j++) {
				System.out.println("list.size()=" + list.size());

				ContractCav contractCav = (ContractCav) list.get(j);

				System.out.println("contractCav.getEmsNo()="
						+ contractCav.getEmsNo());

				if (contractCav.getValueAddedRate() != null
						&& contractCav.getValueAddedRate() < xAmount) {
					stringBuffer.append("电子化手册 合同手册号是[" + emsNo + "] 的增值率 = "
							+ contractCav.getValueAddedRate() + "已经低于系统设置增值率"
							+ "\n");
				}
			}

		}
		makeWarningInfo(company, stringBuffer);
	}

	/** 进出口报关单退厂返工与返工复出条件提醒1:退厂返工的数量和金额与返工复出的一致2:返工复出<=退厂返工+X天时候提醒 */
	private void setIECustomDeclareBackReworkAtTermTip(int projectType,
			Company company) {
		//
		// 设定预警进出口报关单退厂返工与返工复出的条件
		// 条件1：退厂返工的数量和金额与返工复出的一致
		// 条件2：返工复出<=退厂返工+X天时候提醒
		//
		StringBuffer stringBuffer = new StringBuffer();

		double xDay = warningItem.getAmount() == null ? 0.0 : warningItem
				.getAmount();

		//
		// 以备案序号+申报日期来分组进行统计
		//
		String selects = "select a.commSerialNo,b.declarationDate,sum(a.commAmount),sum(a.commTotalPrice),b.emsHeadH2k   ";
		String groupBy = "group by a.commSerialNo,b.declarationDate,b.emsHeadH2k  ";
		String leftOuter = "  left join a.baseCustomsDeclaration b";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.BACK_FACTORY_REWORK, null));// 退厂返工
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.declarationDate", "<=", new Date(),
				null));
		conditions.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));// 生效

		List<Condition> conditions1 = new ArrayList<Condition>();
		conditions1.add(new Condition("and", null,
				"baseCustomsDeclaration.impExpType", "=",
				ImpExpType.REWORK_EXPORT, null));// 返工复出
		conditions1.add(new Condition("and", null,
				"baseCustomsDeclaration.declarationDate", "<=", new Date(),
				null));
		conditions1.add(new Condition("and", null,
				"baseCustomsDeclaration.effective", "=", true, null));// 生效

		List listBackCustomsDeclarationCommInfo = new ArrayList();
		List listReWorkCustomsDeclarationCommInfo = new ArrayList();
		// String projectName = "电子帐册";
		if (ProjectType.BCUS == projectType) {

			listBackCustomsDeclarationCommInfo = warningDao.commonSearch(
					selects, "CustomsDeclarationCommInfo", conditions, groupBy,
					leftOuter, company);
			listReWorkCustomsDeclarationCommInfo = warningDao.commonSearch(
					selects, "CustomsDeclarationCommInfo", conditions1,
					groupBy, leftOuter, company);
			// projectName = "电子帐册";

		} else if (ProjectType.DZSC == projectType) {

			listBackCustomsDeclarationCommInfo = warningDao.commonSearch(
					selects, "DzscCustomsDeclarationCommInfo", conditions,
					groupBy, leftOuter, company);
			listReWorkCustomsDeclarationCommInfo = warningDao.commonSearch(
					selects, "DzscCustomsDeclarationCommInfo", conditions1,
					groupBy, leftOuter, company);
			// projectName = "电子手册";

		} else if (ProjectType.BCS == projectType) {

			listBackCustomsDeclarationCommInfo = warningDao.commonSearch(
					selects, "BcsCustomsDeclarationCommInfo", conditions,
					groupBy, leftOuter, company);
			listReWorkCustomsDeclarationCommInfo = warningDao.commonSearch(
					selects, "BcsCustomsDeclarationCommInfo", conditions1,
					groupBy, leftOuter, company);
			// projectName = "电子化手册";

		}
		//
		// 缓存返工复出的资料
		//
		Map<Object, String> reWorkMap = new HashMap<Object, String>();
		for (int i = 0; i < listReWorkCustomsDeclarationCommInfo.size(); i++) {
			Object[] object = (Object[]) listReWorkCustomsDeclarationCommInfo
					.get(i);
			reWorkMap.put(object[0] + "/" + object[4], object[1] + "/"
					+ object[2] + "/" + object[3]);// 申报日期 +数量+金额
		}
		//
		// 开始检查条件
		//
		int seqNum=1;
		for (int i = 0; i < listBackCustomsDeclarationCommInfo.size(); i++) {
			Object[] object = (Object[]) listBackCustomsDeclarationCommInfo
					.get(i);
			Integer commSerialNo = object[0] == null ? null
					: (Integer) object[0];
			Date declareDate = object[1] == null ? null : (Date) object[1];
			Double commAmount = object[2] == null ? 0.0 : (Double) object[2];
			Double commTotalPrice = object[3] == null ? 0.0
					: (Double) object[3];
			String emsHeadH2k = object[4] == null ? "" : (String) object[4];
			Date currentDate = new Date();// 系统当前的日期
			if (reWorkMap.get(commSerialNo + "/" + emsHeadH2k) != null) {
				continue;
			}
			//
			// 当没有返工复出时
			//
			if (currentDate.after(declareDate)) { // true
				long diffMillis = currentDate.getTime() - declareDate.getTime();
				long diffDays = diffMillis / (24 * 60 * 60 * 1000); // 7
				//
				// 大于 >= X Day 时
				//
				if (diffDays > xDay) {
					
					System.out.println("diffDays >= xDay----------------"
							+ (diffDays >= xDay));
					stringBuffer.append((seqNum) + ".合同手册号[" + emsHeadH2k
							+ "]退厂返工备案号[" + commSerialNo + "] 于" + "["
							+ declareDate + "]已申报,申报的数量[" + commAmount + "]金额["
							+ commTotalPrice + "]未及时返工复出\n");
					seqNum++;
				}
			}

		}
		makeWarningInfo(company, stringBuffer);
	}

}
