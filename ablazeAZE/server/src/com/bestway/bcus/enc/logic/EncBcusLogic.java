package com.bestway.bcus.enc.logic;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.contractanalyse.entity.WrapStat;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillTemp1;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.enc.entity.CustomsMonthStatInfo;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.bcus.enc.entity.ImportApplyToCustomsBillListTempData;
import com.bestway.bcus.enc.entity.MakeApplyToCustoms;
import com.bestway.bcus.enc.entity.MakeListToCustoms;
import com.bestway.bcus.enc.entity.TempApplyToCustomsCheckup;
import com.bestway.bcus.enc.entity.TempContainerCode;
import com.bestway.bcus.enc.entity.TempCustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.TempCustomsInfo;
import com.bestway.bcus.enc.entity.TempExgUsedImgAmount;
import com.bestway.bcus.enc.entity.TempImpExpRequestBillForInput;
import com.bestway.bcus.enc.entity.TempImportApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.TempRequestTOApplyTOCustomsReport;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.dao.SysCodeDao;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.fpt.dao.FptManageDao;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;

/**
 * 电子帐册综合业务逻辑层 chcked by kcb at 2008/12/1
 */
@SuppressWarnings("unchecked")
public class EncBcusLogic {

	/**
	 * 转厂管理的Logic
	 */
	private FptManageDao fptManageDao = null;
	/**
	 * 现在不在使用--DAO
	 */
	private SysCodeDao sysCodeDao = null;

	/**
	 * 获取现在不在使用--DAO
	 */
	public SysCodeDao getSysCodeDao() {
		return sysCodeDao;
	}

	/**
	 * 设置现在不在使用--DAO
	 */
	public void setSysCodeDao(SysCodeDao sysCodeDao) {
		this.sysCodeDao = sysCodeDao;
	}

	/**
	 * 电子帐册Dao
	 */
	private EncDao encDao = null;

	/**
	 * 取得电子帐册Dao
	 * 
	 * @return 电子帐册Dao
	 */
	public EncDao getEncDao() {
		return encDao;
	}

	/**
	 * 设计电子帐册Dao
	 * 
	 * @param encDao
	 *            电子帐册Dao
	 */
	public void setEncDao(EncDao encDao) {
		this.encDao = encDao;
	}

	/**
	 * 把日期型转换为字符型
	 * 
	 * @param date
	 *            日期
	 * @return 转换为字符型的日期
	 */
	public String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(date);
	}

	/**
	 * 如果为空，返回零
	 * 
	 * @param d
	 * @return
	 */
	private Double fd(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	// /**
	// * 取得成品单耗情况来自序号
	// *
	// * @param seqNum
	// * 料件序号
	// * @return 成品的单耗
	// */
	// public List getExgUnitWearBySeqNum(String allSeqNum, Date beginDate,
	// Date endDate, boolean isTotal, String deptId) {
	// if (isTotal) {
	// List list = findExgUnitWearBySeqNumIsTotal(allSeqNum, beginDate,
	// endDate, deptId);
	// return list;
	// } else {
	// List list = findExgUnitWearBySeqNumNoTotal(allSeqNum, beginDate,
	// endDate, deptId);
	// return list;
	// }
	// }

	/**
	 * 根据报关单时间，及商品序号统计料件总耗用量
	 */
	public List findExgUnitWearBySeqNumIsTotal(BillTemp1 temp, Date beginDate,
			Date endDate, String deptId) {
		List<BillTemp> tempList = new ArrayList<BillTemp>();
		String reportDecimalLength = this.encDao
				.getBpara(BcusParameter.ReportDecimalLength);
		int len = 2;
		if (reportDecimalLength != null && !reportDecimalLength.equals("")) {
			len = Double.valueOf(reportDecimalLength).intValue();
		}
		// String[] yy = allSeqNum.split(",");
		// for (int k = 0; k < yy.length; k++) {
		// Integer seqNum = Integer.valueOf(yy[k]);
		// List list = null; // encDao.findBomBySeqNum(yy);// 根据料件序号找到BOM
		// for (int i = 0; i < list.size(); i++) {
		// Object[] obj = (Object[]) list.get(i);

		Integer CpseqNum = Integer.valueOf(temp.getBill1());// 成品序号
		// String name = (String) obj[1];
		// String spec = (String) obj[2];
		Double unitwear = temp.getBillSum1();
		Double wear = temp.getBillSum2();
		String version = temp.getBill6();// 版本
		// String ljName = (String) obj[6];
		List customslist = encDao.findImpExpCommInfoListById(CpseqNum,
				beginDate, endDate, version, deptId, null);
		// if (deptId == null || deptId.equals("")) {
		// customslist = encDao.findImpExpCommInfoListById(false, CpseqNum,
		// null, null, beginDate, endDate, version, true, null, 0);
		// } else {
		// customslist = encDao.findImpExpCommInfoListById(false, CpseqNum,
		// null, null, beginDate, endDate, version, true, deptId, 0);
		// }
		BillTemp bill = new BillTemp();
		bill.setBill8(String.valueOf(CpseqNum)); // 成品序号
		bill.setBill7(temp.getBill2()); // 成品名称
		bill.setBill11(temp.getBill3());// 成品规格
		bill.setBill6(String.valueOf(version)); // 版本号
		bill.setBill5(temp.getBill5()); // 料件序号
		bill.setBill4(temp.getBill4());// 料件名称
		bill.setBillSum1(unitwear);// 料件单耗
		bill.setBillSum2(wear); // 料件损耗
		Double allnum = Double.valueOf(0);
		Double allBillSum = Double.valueOf(0);
		double twear = wear == null ? 0.0 : wear;
		double tunitwear = unitwear == null ? 0.0 : unitwear;
		double dyyl = 1 - twear / 100;
		for (int j = 0; j < customslist.size(); j++) {
			BaseCustomsDeclaration customs = (BaseCustomsDeclaration) (((CustomsDeclarationCommInfo) customslist
					.get(j)).getBaseCustomsDeclaration());

			Double num = ((CustomsDeclarationCommInfo) customslist.get(j))
					.getCommAmount() == null ? 0.0
					: ((CustomsDeclarationCommInfo) customslist.get(j))
							.getCommAmount();
			if (customs.getImpExpType() == ImpExpType.BACK_FACTORY_REWORK) {
				num = -num;
			}

			allnum = allnum + num;
			if (dyyl != 0) {
				allBillSum += fd(num) * (tunitwear / dyyl);
			}
		}
		bill.setBillSum4(fd(allnum));// 出口量
		bill.setBillSum19(CommonUtils.getDoubleByDigit(allBillSum, len));// 总耗用量
		tempList.add(bill);
		// }
		// }
		return tempList;
	}

	/**
	 * 根据报关单时间，及商品序号查找成品单耗
	 */
	public List findExgUnitWearBySeqNumNoTotal(BillTemp1 temp, Date beginDate,
			Date endDate, String deptId, Integer impExpType) {
		List<BillTemp> tempList = new ArrayList<BillTemp>();
		List customslist = encDao.findImpExpCommInfoListById(
				Integer.valueOf(temp.getBill1()), beginDate, endDate,
				temp.getBill6(), deptId, impExpType);// new
		
		if(customslist.size()==0){
			BillTemp bill = new BillTemp();
			bill.setBill8(temp.getBill1()); // 成品序号
			bill.setBill7(temp.getBill2()); // 成品名称
			bill.setBill11(temp.getBill3());// 成品规格
			bill.setBill4(temp.getBill4());// 料件名称
			bill.setBill5(temp.getBill5()); // 料件序号
			bill.setBill6(temp.getBill6()); // 版本号
			Double unitwear = temp.getBillSum1();
			Double wear = temp.getBillSum2();
			bill.setBillSum1(unitwear);// 料件单耗
			bill.setBillSum2(wear);// 料件损耗
			tempList.add(bill);
			return tempList;
		}
		
		for (int j = 0; j < customslist.size(); j++) {
			BillTemp bill = new BillTemp();
			BaseCustomsDeclaration customs = (BaseCustomsDeclaration) (((CustomsDeclarationCommInfo) customslist
					.get(j)).getBaseCustomsDeclaration());
			String customsNo = customs.getCustomsDeclarationCode(); // 报关单号
			String impExpDate = dateToString(customs.getImpExpDate()); // 进出口日期
			Double num = ((CustomsDeclarationCommInfo) customslist.get(j))
					.getCommAmount();
			if (customs.getImpExpType() == ImpExpType.BACK_FACTORY_REWORK) {
				num = -num;
			}
			bill.setBill10(customsNo); // 报关单号
			bill.setBill1(String.valueOf(customs.getSerialNumber()));
			bill.setBill9(impExpDate); // 进出口日期
			bill.setBill2(customs.getTradeMode() == null ? null : customs
					.getTradeMode().getName());// 贸易方式
			bill.setBill3(String.valueOf(customs.getImpExpType()));
			bill.setBill8(temp.getBill1()); // 成品序号
			bill.setBill7(temp.getBill2()); // 成品名称
			bill.setBill11(temp.getBill3());// 成品规格
			bill.setBill4(temp.getBill4());// 料件名称
			bill.setBill5(temp.getBill5()); // 料件序号
			bill.setBill6(temp.getBill6()); // 版本号
			Double unitwear = temp.getBillSum1();
			Double wear = temp.getBillSum2();
			bill.setBillSum1(unitwear);// 料件单耗
			bill.setBillSum2(wear);// 料件损耗
			Double cdm = fd(unitwear) / (1 - (fd(wear) * 0.01));
			bill.setBillSum3(fd(num) * cdm);// 出口耗用量
			bill.setBillSum4(fd(num));// 出口量
			tempList.add(bill);
		}
		return tempList;
	}

	/**
	 * 取得报关单的总金额 净重 毛重
	 * 
	 * @param list
	 *            报关单
	 * @return 报关单的总金额 净重 毛重
	 */
	public List getTotal(List list) {
		TempCustomsInfo dd = new TempCustomsInfo();
		CustomsDeclarationCommInfo obj = new CustomsDeclarationCommInfo();
		Hashtable hs = new Hashtable();
		Double netweight = Double.valueOf(0);
		Double gossweight = Double.valueOf(0);
		Double money = Double.valueOf(0);
		for (int i = 0; i < list.size(); i++) {
			TempCustomsInfo objj = (TempCustomsInfo) list.get(i);
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) objj
					.getInfo();
			netweight = netweight + fd(info.getNetWeight());
			gossweight = gossweight + fd(info.getGrossWeight());
			money = money + fd(info.getCommTotalPrice());
			if (hs.get(info.getBaseCustomsDeclaration().getSerialNumber()) == null) {
				hs.put(info.getBaseCustomsDeclaration().getSerialNumber(), info
						.getBaseCustomsDeclaration().getSerialNumber());
			}
		}
		obj.setCommTotalPrice(money);
		obj.setNetWeight(netweight);
		obj.setGrossWeight(gossweight);
		List hslist = new Vector(hs.values());
		obj.setCommName("报关单份数:"
				+ (hslist == null ? "0" : String.valueOf(hslist.size())));
		list.add(new Vector());
		dd.setInfo(obj);
		list.add(dd);
		return list;
	}

	/**
	 * 根据报关单时间，查找报关单集中箱号
	 */
	public List findCustomsDeclarationContainer(Date beginDate, Date endDate,
			int index, int length, String property, Object value,
			Boolean isLike, Hashtable billhs) {
		Hashtable hs = new Hashtable();
		Map map = new HashMap();
		List list = this.encDao.findCustomsDeclarationContainer(beginDate,
				endDate, index, length, property, value, isLike);
		List allCustomsDeclaretions = this.encDao
				.findCustomsDeclarationByMateriel(beginDate, endDate);
		List arrayList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {

			CustomsDeclarationContainer obj = (CustomsDeclarationContainer) list
					.get(i);
			CustomsDeclaration customs = (CustomsDeclaration) obj
					.getBaseCustomsDeclaration();
			TempContainerCode temp = new TempContainerCode();
			temp.setContainerCode(obj.getContainerCode());
			temp.setCustoms(customs);
			// if (billhs.get(customs) != null) {
			// continue;
			// }
			map.put(customs, customs);
			arrayList.add(temp);
		}
		for (int i = 0; i < allCustomsDeclaretions.size(); i++) {
			CustomsDeclaration customs = (CustomsDeclaration) allCustomsDeclaretions
					.get(i);
			if (map.get(customs) == null) {
				TempContainerCode temp = new TempContainerCode();
				temp.setContainerCode("");
				temp.setCustoms(customs);
				// if (billhs.get(customs) != null) {
				// continue;
				// }
				arrayList.add(temp);
			}
		}
		return arrayList;
	}

	/**
	 * 精确的日期
	 * 
	 * @param date1
	 *            日期
	 * @return 精确的日期
	 */
	public String dateToDate(Date date1) {
		if (date1 == null) {
			return null;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str1 = formater.format(date1);
		return str1;
	}

	/**
	 * 进口料件单重差异稽核表
	 */
	public List accountImpUnitWeightCheck(List list) {
		Hashtable ljNohs = new Hashtable();
		List billList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempContainerCode temp = (TempContainerCode) list.get(i);
			CustomsDeclaration customs = temp.getCustoms();
			String containerCode = temp.getContainerCode();// 集装箱号
			System.out.println("集装箱号:" + containerCode);
			List makelisttocustoms = this.encDao.findMakeListToCustoms(customs);
			for (int j = 0; j < makelisttocustoms.size(); j++) {
				MakeListToCustoms makeobj = (MakeListToCustoms) makelisttocustoms
						.get(j);
				CustomsDeclarationCommInfo info = makeobj.getCustomsInfo();
				AtcMergeAfterComInfo afterInfo = makeobj
						.getAtcMergeAfterComInfo();
				List beforeList = this.encDao.findMakeApplyToCustoms(afterInfo);
				for (int k = 0; k < beforeList.size(); k++) {
					BillTemp bill = new BillTemp();
					ImpExpCommodityInfo cominfo = (ImpExpCommodityInfo) beforeList
							.get(k);
					Materiel m = cominfo.getMateriel();
					String key = m.getPtNo() + "/"
							+ String.valueOf(info.getCommSerialNo());
					if (ljNohs.get(key) != null) {
						continue;
					}
					bill.setBill1(m.getPtNo()); // 料号
					bill.setBill2(m.getFactoryName());// 名称
					bill.setBill3(m.getFactorySpec());// 规格
					bill.setBill4(m.getCalUnit() == null ? "" : m.getCalUnit()
							.getName());// 单位
					bill.setBill5(info.getCommName());
					bill.setBill6(dateToDate(info.getBaseCustomsDeclaration()
							.getDeclarationDate()));
					bill.setBill7(info.getBaseCustomsDeclaration()
							.getWlserialNumber());
					bill.setBill8(info.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode());
					bill.setBill9(containerCode);//
					bill.setBillSum1(cominfo.getQuantity());// 数量
					bill.setBillSum2(cominfo.getQuantity() == null ? 0.0
							: ((cominfo.getNetWeight() == null ? 0.0 : cominfo
									.getNetWeight()) / cominfo.getQuantity()));
					if (m.getCalWeightUnit() != null
							&& m.getCalWeightUnit().getName() != null
							&& m.getCalWeightUnit().getName().equals("克")) {
						bill.setBillSum3(m.getPtNetWeight() / 1000);
					} else {
						bill.setBillSum3(m.getPtNetWeight());
					}
					bill.setBillSum4(fd(bill.getBillSum3())
							- fd(bill.getBillSum2()));
					bill.setBillSum5(cominfo.getQuantity() == null ? 0.0
							: ((cominfo.getGrossWeight() == null ? 0.0
									: cominfo.getGrossWeight()) / cominfo
									.getQuantity()));
					if (m.getCalWeightUnit() != null
							&& m.getCalWeightUnit().getName() != null
							&& m.getCalWeightUnit().getName().equals("克")) {
						bill.setBillSum6(m.getPtOutWeight() / 1000);
					} else {
						bill.setBillSum6(m.getPtOutWeight());
					}
					bill.setBillSum7(fd(bill.getBillSum6())
							- fd(bill.getBillSum5()));

					bill.setBillSum8(fd(cominfo.getQuantity())
							* fd(bill.getBillSum4()));
					bill.setBillSum9(fd(cominfo.getQuantity())
							* fd(bill.getBillSum7()));
					billList.add(bill);
					ljNohs.put(key, key);
				}
			}
			// Inner From
			List customsFromMaterielList = this.encDao
					.findCustomsFromMateriel(customs);
			for (int h = 0; h < customsFromMaterielList.size(); h++) {
				BillTemp bill = new BillTemp();
				Object[] objs = (Object[]) customsFromMaterielList.get(h);
				Materiel m = (Materiel) objs[0];
				CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) objs[1];
				String key = m.getPtNo() + "/"
						+ String.valueOf(info.getCommSerialNo());
				if (ljNohs.get(key) != null) {
					continue;
				}
				bill.setBill1(m.getPtNo()); // 料号
				bill.setBill2(m.getFactoryName());// 名称
				bill.setBill3(m.getFactorySpec());// 规格
				bill.setBill4(m.getCalUnit() == null ? "" : m.getCalUnit()
						.getName());// 单位
				bill.setBill5(info.getCommName());
				bill.setBill6(dateToDate(info.getBaseCustomsDeclaration()
						.getDeclarationDate()));
				bill.setBill7(info.getBaseCustomsDeclaration()
						.getWlserialNumber());
				bill.setBill8(info.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode());
				bill.setBill9(containerCode);//
				bill.setBillSum1(info.getCommAmount());
				bill.setBillSum2(info.getNetWeight());
				bill.setBillSum3(m.getPtNetWeight());
				bill.setBillSum4(fd(bill.getBillSum3())
						- fd(bill.getBillSum2()));
				bill.setBillSum5(info.getGrossWeight());
				bill.setBillSum6(m.getPtOutWeight());
				bill.setBillSum7(fd(bill.getBillSum6())
						- fd(bill.getBillSum5()));
				bill.setBillSum8(fd(bill.getBillSum1())
						* fd(bill.getBillSum4()));
				bill.setBillSum9(fd(bill.getBillSum1())
						* fd(bill.getBillSum7()));
				billList.add(bill);
				ljNohs.put(key, key);
			}
		}
		return billList;
	}

	/**
	 * 流水帐报表新增份数栏位
	 */
	public List findImpExpCommInfo(Integer seqNum, String customer,
			String iEType, Date beginDate, Date endDate, Date impexpBeginDate,
			Date impexpEndDate, boolean isProduct, Boolean iseffective,
			String commName, Trade tradeMode, String customsNo,
			String projectDeptName) {
		List list = this.encDao.findImpExpCommInfoOther(seqNum, customer,
				iEType, beginDate, endDate, impexpBeginDate, impexpEndDate,
				isProduct, iseffective, commName, tradeMode, customsNo,
				projectDeptName, false, null);
		Hashtable hs = new Hashtable();
		List arrayList = new ArrayList();
		Integer piece = 0;
		for (int i = 0; i < list.size(); i++) {
			TempCustomsInfo info = new TempCustomsInfo();
			CustomsDeclarationCommInfo customsInfo = (CustomsDeclarationCommInfo) list
					.get(i);
			info.setInfo(customsInfo);
			if (hs.get(customsInfo.getBaseCustomsDeclaration()
					.getSerialNumber()) == null) {
				piece = piece + 1;
				hs.put(customsInfo.getBaseCustomsDeclaration()
						.getSerialNumber(), customsInfo
						.getBaseCustomsDeclaration().getSerialNumber());
				info.setCustomsPiece(piece);
			}
			arrayList.add(info);
		}
		return arrayList;
	}

	/**
	 * 小数四舍五入
	 * 
	 * @param unroundedValue
	 * @return
	 */
	public Double forNum(double unroundedValue) {
		int digits = 6;
		int x = 1;
		for (int i = 0; i < digits; i++) {
			x = x * 10;
		}
		double intPortion = Math.floor(unroundedValue);
		double unroundedDecimalPortion = (unroundedValue - intPortion);
		double roundedDecimalPortion = Math.round(unroundedDecimalPortion * x);
		roundedDecimalPortion = roundedDecimalPortion / x;
		double total = intPortion + roundedDecimalPortion;
		return new Double(total);
	}

	/**
	 * 内部商品转报关单删除
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill3(
			BaseCustomsFromMateriel fromMateriel,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill) {
		if (exgbill != null) {// 出口
			BaseCustomsDeclarationCommInfo existcommInfo = this.encDao
					.findBaseCustomsDeclarationCommInfoBySequm(
							exgbill.getSeqNum(),
							customsDeclaration,
							(exgbill.getVersion() == null ? null : String
									.valueOf(exgbill.getVersion())), null);
			if (existcommInfo != null) {
				existcommInfo.setCommAmount(existcommInfo.getCommAmount()
						- fromMateriel.getBgCommAmount());
				existcommInfo.setFirstAmount(existcommInfo.getFirstAmount()
						- fromMateriel.getBgFirstAmount());
				existcommInfo.setSecondAmount(existcommInfo.getSecondAmount()
						- fromMateriel.getBgSecondAmount());
				existcommInfo.setNetWeight(existcommInfo.getNetWeight()
						- fromMateriel.getBgNetWeight());
				existcommInfo.setGrossWeight(existcommInfo.getGrossWeight()
						- fromMateriel.getBgGrossWeight());
				existcommInfo.setPieces(existcommInfo.getPieces()
						- fromMateriel.getBgPieces());
				double price = 0;
				if (existcommInfo.getCommUnitPrice() != null) {
					price = existcommInfo.getCommUnitPrice().doubleValue();
				}
				existcommInfo.setCommTotalPrice(forNum(price
						* existcommInfo.getCommAmount()));

				this.encDao.saveCustomsDeclarationCommInfo(existcommInfo);
				this.encDao.deletefromMateriel(fromMateriel);
			}
		} else {// 进口
			BaseCustomsDeclarationCommInfo existcommInfo = this.encDao
					.findBaseCustomsDeclarationCommInfoBySequm(
							imgbill.getSeqNum(), customsDeclaration, null, null);
			if (existcommInfo != null) {
				existcommInfo.setCommAmount(existcommInfo.getCommAmount()
						- fromMateriel.getBgCommAmount());
				existcommInfo.setFirstAmount(existcommInfo.getFirstAmount()
						- fromMateriel.getBgFirstAmount());
				existcommInfo.setSecondAmount(existcommInfo.getSecondAmount()
						- fromMateriel.getBgSecondAmount());
				existcommInfo.setNetWeight(existcommInfo.getNetWeight()
						- fromMateriel.getBgNetWeight());
				existcommInfo.setGrossWeight(existcommInfo.getGrossWeight()
						- fromMateriel.getBgGrossWeight());
				existcommInfo.setPieces(existcommInfo.getPieces()
						- fromMateriel.getBgPieces());
				double price = 0;
				if (existcommInfo.getCommUnitPrice() != null) {
					price = existcommInfo.getCommUnitPrice().doubleValue();
				}
				existcommInfo.setCommTotalPrice(forNum(price
						* existcommInfo.getCommAmount()));
				this.encDao.saveCustomsDeclarationCommInfo(existcommInfo);
				this.encDao.deletefromMateriel(fromMateriel);
			}
		}
		return null;
	}

	/**
	 * 删除单据号
	 * 
	 * @param allListNo
	 *            所有单据号
	 * @param listNo
	 *            单据号
	 * @return 单据号
	 */
	private String deleListNo(String allListNo, String listNo) {
		if (allListNo == null || "".equals(allListNo)) {
			return "";
		}
		String[] yy = allListNo.split(",");
		String newListNo = "";
		for (int i = 0; i < yy.length; i++) {
			if (i == 0) {
				if (yy[i].equals(listNo)) {
					continue;
				} else {
					newListNo = yy[0];
				}
			} else {
				if (yy[i].equals(listNo)) {
					continue;
				}
				if (newListNo.equals("")) {
					newListNo = yy[i];
				} else {
					newListNo += "," + yy[i];
				}
			}
		}
		return newListNo;
	}

	/**
	 * 删除清单归并后
	 */
	public void deleteAtcMergeAfterComInfo(AtcMergeAfterComInfo afterInfo) {
		List list = this.encDao.findAtcMergeBeforeComInfoByAfterID(afterInfo);
		for (int i = 0; i < list.size(); i++) {
			AtcMergeBeforeComInfo beforeInfo = (AtcMergeBeforeComInfo) list
					.get(i);
			List ls = this.encDao
					.findMakeApplyToCustomsByatcMergeBeforeComInfo(beforeInfo
							.getId());
			for (int j = 0; j < ls.size(); j++) {
				// 保存申请单体
				MakeApplyToCustoms apply = (MakeApplyToCustoms) ls.get(j);
				ImpExpCommodityInfo comInfo = apply.getImpExpCommodityInfo();
				comInfo.setIsTransferCustomsBill(new Boolean(false));
				this.encDao.saveImpExpComInfo(comInfo);
				// 保存申请单头
				ImpExpRequestBill bill = comInfo.getImpExpRequestBill();
				bill.setIsCustomsBill(new Boolean(false));
				bill.setToCustomCount(bill.getToCustomCount() == null ? 0
						: (bill.getToCustomCount() - 1));
				bill.setAllBillNo(deleListNo(bill.getAllBillNo(), afterInfo
						.getBillList().getListNo()));
				this.encDao.saveImpExpRequestBill(bill);
			}
			// 删除中间表
			if (ls.size() > 0) {
				this.encDao.deleteAll(ls);
			}
		}
		// 删除归并前
		if (list.size() > 0) {
			this.encDao.deleteAll(list);
		}
		// 删除归并后
		this.encDao.deleteAtcMergeAfterComInfo(afterInfo);
	}

	/**
	 * 删除报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	public void deleteApplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		List list = this.encDao.findAtcMergeAfterComInfoNotToCustoms(
				applyToCustomsBillList, null);
		for (int i = 0; i < list.size(); i++) {
			AtcMergeAfterComInfo afterInfo = (AtcMergeAfterComInfo) list.get(i);
			deleteAtcMergeAfterComInfo(afterInfo);
		}
		this.encDao.deleteApplyToCustomsBillList(applyToCustomsBillList);

	}

	/**
	 * 申请单转报关单查看--删除
	 */
	public void deleteImpExpCommodityInfo(BaseCustomsDeclarationCommInfo info,
			ImpExpCommodityInfo t, boolean isDelete) {
		boolean isImportGoods = true;
		if (info.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.IMPORT) {
			isImportGoods = true;
		} else {
			isImportGoods = false;
		}
		if (isImportGoods) {// 料件
			t.setTotalnetweight((t == null ? 0 : fd(t.getNetWeight())
					.doubleValue()));
		} else {
			if (isDelete) {
				t.setTotalnetweight((t == null ? 0 : fd(t.getNetWeight())
						.doubleValue())
						* (t == null ? 0 : fd(t.getQuantity()).doubleValue()));
			} else {
				t.setTotalnetweight((t == null ? 0 : fd(t.getNetWeight())
						.doubleValue())
						* (t == null ? 0 : fd(t.getTempAmount()).doubleValue()));
			}
		}
		String ptNo = null;
		if (t.getMateriel() == null) {
			System.out.println("-- 物料主档中不存在该料号:" + t.getPtNo());
			return;
		} else {
			ptNo = t.getMateriel().getPtNo();
		}

		Double bgCommonAmount = Double.valueOf(0);
		Double bgNetWeight = Double.valueOf(0);
		Double bgGrossWeight = Double.valueOf(0);
		Double bgFirstAmount = Double.valueOf(0);
		Double bgSecondAmount = Double.valueOf(0);
		Integer bgPieces = 0;
		// 查找物料对应十码
		if (isImportGoods == true) {// 料件 不检查版本号
			InnerMergeData data = fptManageDao.findInnerMergerByPtNo(ptNo,
					MaterielType.MATERIEL);
			if (data == null) {
				System.out.println("-- 所对应十码在内部归并关系中不存在！   料号:" + ptNo);
				return;
			}
			Integer tenSeqNum = data.getHsAfterTenMemoNo(); // 十码序号
			EmsHeadH2kImg img = this.encDao.getEmsHeadImg(tenSeqNum);
			double amount = 0.0;
			Double firstamount = Double.valueOf(0);
			Double secondamount = Double.valueOf(0);
			if (img.getUnit() != null
					&& (img.getUnit().getName().equals("千克") || img.getUnit()
							.getName().equals("公斤"))) {
				amount = (t.getTotalnetweight() == null ? 0.0 : t
						.getTotalnetweight());// /注：明门提出(数量=净重(此净重为总净重))
			} else {
				amount = (t.getQuantity() == null ? 0.0 : t.getQuantity());
			}
			if (img.getComplex().getFirstUnit() != null
					&& img.getComplex().getFirstUnit().getName().trim()
							.equals("米")) {
				firstamount = ((t.getQuantity() * 0.9144));
			} else if (img.getComplex().getFirstUnit() != null
					&& (img.getComplex().getFirstUnit().getName().trim()
							.equals("千克") || img.getComplex().getFirstUnit()
							.getName().trim().equals("公斤"))) {
				firstamount = ((amount));
			}
			if (img.getComplex().getSecondUnit() != null
					&& img.getComplex().getSecondUnit().getName().trim()
							.equals("米")) {
				secondamount = ((t.getQuantity() * 0.9144));
			} else if (img.getComplex().getSecondUnit() != null
					&& (img.getComplex().getSecondUnit().getName().trim()
							.equals("千克") || img.getComplex().getSecondUnit()
							.getName().trim().equals("公斤"))) {
				secondamount = ((amount));
			}
			bgCommonAmount = forInterNum(amount);
			bgFirstAmount = forInterNum(firstamount);
			bgSecondAmount = forInterNum(secondamount);
			bgNetWeight = forInterNum((t.getNetWeight() == null ? 0.0 : t
					.getNetWeight()));
			bgGrossWeight = forInterNum((t.getGrossWeight() == null ? 0.0 : t
					.getGrossWeight()));
			bgPieces = t.getPiece() == null ? 0 : t.getPiece().intValue();

		} else { // 成品
			InnerMergeData data = fptManageDao.findInnerMergerByPtNo(ptNo,
					MaterielType.FINISHED_PRODUCT);
			if (data == null) {
				System.out.println("-- 所对应十码在内部归并关系中不存在！   料号:" + ptNo);
				return;
			}
			Integer tenSeqNum = data.getHsAfterTenMemoNo(); // 十码序号
			Integer version = (info.getVersion() == null || "".equals(info
					.getVersion())) ? 0 : Integer.valueOf(info.getVersion());// 版本号
			EmsHeadH2kVersion versionObj = this.encDao.findEmsHeadH2kVersion(
					tenSeqNum, version);
			if (versionObj == null) {
				return;
			}
			EmsHeadH2kExg exg = versionObj.getEmsHeadH2kExg();
			CompanyOther other = CommonUtils.getOther();
			double amount = 0.0;
			double grossWeight = 0.0;
			double netWeight = 0.0;
			Double netWeightParameter = Double.valueOf(0);
			if (other != null) {
				netWeightParameter = (other.getWeightPara() == null) ? Double
						.valueOf(0) : other.getWeightPara();
			}
			if (exg.getUnit() != null
					&& (exg.getUnit().getName().trim().equals("公斤") || exg
							.getUnit().getName().trim().equals("千克"))) {

				amount = (t.getTotalnetweight() == null ? 0.0 // mergeAfterComInfo:由pK单汇总得到申报数量
						: t.getTotalnetweight()) * netWeightParameter;

				//
				// 合同比例(合同单位毛重/合同单位净重)
				//
				double contractRate = 0.0;
				double _grossWeight = versionObj.getUnitGrossWeight() == null ? 0.0
						: versionObj.getUnitGrossWeight();
				double _netWeight = versionObj.getUnitNetWeight() == null ? 0.0
						: versionObj.getUnitNetWeight();
				if (_grossWeight != 0.0 || _netWeight != 0.0) {
					contractRate = _grossWeight / _netWeight;
				}
				netWeight = (t.getTotalnetweight() == null ? 0.0 : t
						.getTotalnetweight()) * netWeightParameter;
				// 申报单位净重 = 申报数量 * ERP净重 * 重量比例
				// 申报单位毛重 = 申报单位净重 * 合同比例(合同单位毛重/合同单位净重)
				grossWeight = netWeight * contractRate;

			} else {// 个
				amount = t.getQuantity() == null ? 0.0 : t.getQuantity();
				grossWeight = amount
						* (versionObj.getUnitGrossWeight() == null ? 0.0
								: versionObj.getUnitGrossWeight());
				netWeight = amount
						* (versionObj.getUnitNetWeight() == null ? 0.0
								: versionObj.getUnitNetWeight());

			}
			bgCommonAmount = forInterNum(amount);
			bgFirstAmount = forInterNum(amount);
			bgNetWeight = forInterNum(netWeight);
			bgGrossWeight = forInterNum(grossWeight);
			bgPieces = forInterNum(
					t.getQuantity() == null ? 0.0 : t.getQuantity()).intValue();

		}

		info.setCommAmount(fd(info.getCommAmount()) - fd(bgCommonAmount));
		info.setFirstAmount(fd(info.getFirstAmount()) - fd(bgFirstAmount));
		info.setSecondAmount(fd(info.getSecondAmount()) - fd(bgSecondAmount));
		info.setNetWeight(fd(info.getNetWeight()) - fd(bgNetWeight));
		info.setGrossWeight(fd(info.getGrossWeight()) - fd(bgGrossWeight));
		info.setPieces((info.getPieces() == null ? 0 : info.getPieces())
				- (bgPieces == null ? 0 : bgPieces));
		info.setCommTotalPrice(forNum(info.getCommAmount()
				* fd(info.getCommUnitPrice())));
		this.encDao.saveCustomsDeclarationCommInfo(info);
		if (isDelete) {
			// 保存申请单体
			t.setIsTransferCustomsBill(new Boolean(false));
			this.encDao.saveImpExpComInfo(t);
			// 保存申请单头
			ImpExpRequestBill bill = t.getImpExpRequestBill();
			bill.setIsCustomsBill(new Boolean(false));
			bill.setToCustomCount(bill.getToCustomCount() == null ? 0 : (bill
					.getToCustomCount() - 1));
			this.encDao.saveImpExpRequestBill(bill);
			this.encDao.DeleteMakeApplyToCustomsByImpExp(t);
		}
	}

	/**
	 * 把Integer型转换为Double型
	 * 
	 * @param shuliang
	 *            要转换为Double型的数
	 * @return 转换为Double的Integer数据
	 */
	private Double forInterNum(Double shuliang) {
		if (shuliang == null) {
			return shuliang;
		}
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(0, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}

	/**
	 * 获取转厂管理DAO
	 * 
	 * @return
	 */
	public FptManageDao getFptManageDao() {
		return fptManageDao;
	}

	/**
	 * 设置转厂管理DAO
	 * 
	 * @return
	 */
	public void setFptManageDao(FptManageDao transferFactoryManageDao) {
		this.fptManageDao = transferFactoryManageDao;
	}

	/**
	 * 新增清单归并前
	 * 
	 * @return
	 */
	public List newBillBefore(EmsHeadH2kImg img, EmsHeadH2kExg exg,
			Materiel mt, ApplyToCustomsBillList head) {
		List list = new ArrayList();
		AtcMergeAfterComInfo after = null;
		Integer seqNum = null;
		if (img != null) {
			seqNum = img.getSeqNum();
		} else {
			seqNum = exg.getSeqNum();
		}
		// after = encDao.findAtcMergeAfterComInfoBySeqNum(head, String
		// .valueOf(seqNum));//kcb修改
		if (after == null) {
			after = new AtcMergeAfterComInfo();
			after.setBillList(head);
			after.setCompany(CommonUtils.getCompany());
			if (img != null) {
				after.setEmsSerialNo(img.getSeqNum());
				after.setComplex(img.getComplex());
				after.setComplexName(img.getName());
				after.setComplexSpec(img.getSpec());
				after.setUnit(img.getUnit());
				after.setLegalUnit(img.getComplex().getFirstUnit());
				after.setSecondLegalUnit(img.getComplex().getSecondUnit());
				after.setVersion("0");
			} else {
				after.setEmsSerialNo(exg.getSeqNum());
				after.setComplex(exg.getComplex());
				after.setComplexName(exg.getName());
				after.setComplexSpec(exg.getSpec());
				after.setUnit(exg.getUnit());
				after.setLegalUnit(exg.getComplex().getFirstUnit());
				after.setSecondLegalUnit(exg.getComplex().getSecondUnit());
				after.setVersion(String.valueOf(exg.getVersion()));
			}
			after.setIsTransferCustomsBill(new Boolean(false));
			encDao.saveAtcMergeAfterComInfo(after);
		}
		AtcMergeBeforeComInfo before = new AtcMergeBeforeComInfo();
		//
		// 清单参数设置
		//
		ApplyCustomBillParameter a = this.sysCodeDao
				.findApplyCustomBillParameter();
		if (a != null) {
			if (before.getCurrency() == null) {
				before.setCurrency(a.getCurr());
			}
			if (before.getSalesCountry() == null) {
				before.setSalesCountry(a.getCountry());
			}
			if (before.getUsesCode() == null) {
				before.setUsesCode(a.getUses());
			}
			if (before.getLevyMode() == null) {
				before.setLevyMode(a.getLevyMode());
			}
		}

		before.setAfterComInfo(after);
		Integer serialNo = this.encDao
				.findMaxAtcMergeAfterComInfoByListID(head);// 得到最大序号
		before.setSerialNo(serialNo);// 序号
		before.setMateriel(mt);// 物料
		if (after.getVersion() != null && !"".equals(after.getVersion().trim())) {
			before.setVersion(Integer.valueOf(after.getVersion()));// 版本
		} else {
			before.setVersion(0);
		}
		encDao.saveAtcMergeBeforeComInfo(before);

		head.setMaterialNum(head.getMaterialNum() == null ? 1 : head
				.getMaterialNum() + 1);
		encDao.saveApplyToCustomsBillList(head);
		list.add(head);
		list.add(after);
		return list;
	}

	/**
	 * 新增清单归并前来自内部商品
	 */
	public List newBillBeforeFromMateriel(AtcMergeBeforeComInfo before,
			EmsHeadH2kImg img, EmsHeadH2kExg exg, ApplyToCustomsBillList head) {

		List list = new ArrayList();
		AtcMergeAfterComInfo after = null;
		Integer seqNum = null;
		if (img != null) {
			seqNum = img.getSeqNum();
		} else {
			seqNum = exg.getSeqNum();
		}
		after = encDao.findAtcMergeAfterComInfoBySeqNum(head,
				String.valueOf(seqNum));
		if (after == null) {
			after = new AtcMergeAfterComInfo();
			after.setBillList(head);
			after.setCompany(CommonUtils.getCompany());
			if (img != null) {
				after.setEmsSerialNo(img.getSeqNum());
				after.setComplex(img.getComplex());
				after.setComplexName(img.getName());
				after.setComplexSpec(img.getSpec());
				after.setUnit(img.getUnit());
				after.setLegalUnit(img.getComplex().getFirstUnit());
				after.setSecondLegalUnit(img.getComplex().getSecondUnit());
				after.setVersion("0");
			} else {
				after.setEmsSerialNo(exg.getSeqNum());
				after.setComplex(exg.getComplex());
				after.setComplexName(exg.getName());
				after.setComplexSpec(exg.getSpec());
				after.setUnit(exg.getUnit());
				after.setLegalUnit(exg.getComplex().getFirstUnit());
				after.setSecondLegalUnit(exg.getComplex().getSecondUnit());
				after.setVersion(String.valueOf(exg.getVersion()));
			}
			after.setIsTransferCustomsBill(new Boolean(false));
			encDao.saveAtcMergeAfterComInfo(after);
		}

		//
		// 清单参数设置
		//
		ApplyCustomBillParameter a = this.sysCodeDao
				.findApplyCustomBillParameter();
		if (a != null) {
			if (before.getCurrency() == null) {
				before.setCurrency(a.getCurr());
			}
			if (before.getSalesCountry() == null) {
				before.setSalesCountry(a.getCountry());
			}
			if (before.getUsesCode() == null) {
				before.setUsesCode(a.getUses());
			}
			if (before.getLevyMode() == null) {
				before.setLevyMode(a.getLevyMode());
			}
		}

		before.setAfterComInfo(after);
		Integer serialNo = this.encDao
				.findMaxAtcMergeAfterComInfoByListID(head);// 得到最大序号
		before.setSerialNo(serialNo);// 序号

		if (img != null) {// 进口
			Double declaraprice = CommonUtils.getEmsImgPrice(img);
			before.setDeclaredPrice(declaraprice);
		} else {
			EmsHeadH2kVersion versionObj = encDao.findEmsHeadH2kVersion(
					exg.getSeqNum(), exg.getVersion());
			Double declareprice = CommonUtils.getEmsExgPrice(versionObj);
			before.setDeclaredPrice(declareprice);
		}

		encDao.saveAtcMergeBeforeComInfo(before);

		head.setMaterialNum(head.getMaterialNum() == null ? 1 : head
				.getMaterialNum() + 1);
		encDao.saveApplyToCustomsBillList(head);
		list.add(head);
		list.add(after);
		return list;
	}

	/**
	 * 料号级 料件耗用明细表
	 */
	public List getLjUseDetail(List billlist, Date beginDate, Date endDate,
			EmsEdiMergerHead head, String version, boolean isEmsOrBillList,
			String deptId) {
		List tempList = new ArrayList();
		for (int k = 0; k < billlist.size(); k++) {
			BillTemp obj = (BillTemp) billlist.get(k);

			String ptNo = obj.getBill1();
			Integer exgSeqNum = Integer.valueOf(obj.getBill11());
			String exgvesion = obj.getBill7();
			Double unitWaste = Double.valueOf(obj.getBill12());
			Double waste = Double.valueOf(obj.getBill13());
			String exgPtNo = obj.getBill4();
			Integer imgSeqNum = null;
			List lsImgBefore = this.encDao.findEmsEdiMergerImgBeforeByGNo(ptNo);
			if (lsImgBefore.size() > 0 && lsImgBefore.get(0) != null) {
				imgSeqNum = (Integer) lsImgBefore.get(0);
			}

			List customslist = null;
			Double allnum = Double.valueOf(0);
			if (isEmsOrBillList) {// 出口数量来源于电子帐册,因为成品是一对一
				customslist = encDao.findEmsComminfo(exgSeqNum, beginDate,
						endDate, exgvesion, deptId);
				for (int j = 0; j < customslist.size(); j++) {
					Double num = ((CustomsDeclarationCommInfo) customslist
							.get(j)).getCommAmount();
					allnum = CommonUtils.getDoubleExceptNull(allnum)
							+ CommonUtils.getDoubleExceptNull(num);
				}
			} else {// 出口数量来源于报关清单的数量.因为成品不是一对一
				customslist = encDao.findBillListComminfo(exgPtNo, exgSeqNum,
						beginDate, endDate, exgvesion, deptId);
				for (int j = 0; j < customslist.size(); j++) {
					Double num = ((AtcMergeBeforeComInfo) customslist.get(j))
							.getDeclaredAmount();
					allnum = CommonUtils.getDoubleExceptNull(allnum)
							+ CommonUtils.getDoubleExceptNull(num);
				}
			}
			BillTemp bill = new BillTemp();
			bill.setBill10(String.valueOf(imgSeqNum));// 料件备案序号
			bill.setBill1(ptNo); // 料号
			bill.setBill2(obj.getBill2()); // 名称
			bill.setBill3(obj.getBill3()); // 规格
			bill.setBill11(String.valueOf(exgSeqNum));// 成品备案序号
			bill.setBill4(obj.getBill4());
			bill.setBill5(obj.getBill5());
			bill.setBill6(obj.getBill6());
			bill.setBill7(exgvesion);
			
			
			if(obj.getBill12()!=null&&!"".equals(obj.getBill12())){
				Double bill12 = Double.valueOf(obj.getBill12());
				if(bill12==0){
					bill.setBill12(CommonUtils.getString(obj.getBill12()));
				}else{
					String bd = new BigDecimal(bill12).setScale(9,BigDecimal.ROUND_HALF_UP)
							.stripTrailingZeros().toPlainString();
					bill.setBill12(bd);
				}
			}
			if(obj.getBill13()!=null&&!"".equals(obj.getBill13())){
				Double bill13 = Double.valueOf(obj.getBill13());
				if(bill13==0){
					bill.setBill13(CommonUtils.getString(obj.getBill13()));
				}else{
					String bd = new BigDecimal(bill13).setScale(9,BigDecimal.ROUND_HALF_UP)
							.stripTrailingZeros().toPlainString();
					bill.setBill13(bd);
				}
			}
			
			bill.setBillSum4(fd(allnum)); // 出口量
			Double cdm = fd(unitWaste) / (1 - (fd(waste) * 0.01));
			bill.setBillSum3(fd(allnum) * cdm);// 出口耗用量
			tempList.add(bill);
		}

		return tempList;
	}

	/**
	 * 重新组合
	 */
	public void addBillToBill(AtcMergeAfterComInfo after, String billNo) {
		ApplyToCustomsBillList oldHead = after.getBillList(); // 旧的清单头
		List listhead = encDao.findApplyToCustomsBillList(billNo);
		ApplyToCustomsBillList head = (ApplyToCustomsBillList) listhead.get(0);// 新的清单头
		after.setBillList(head);
		encDao.saveAtcMergeAfterComInfo(after);
		List listbefore = encDao.findAtcMergeBeforeComInfoByAfterID(after);
		if (listbefore != null && listbefore.size() > 0) {
			int x = listbefore.size();

			head.setMaterialNum(head.getMaterialNum() == null ? 0 : (head
					.getMaterialNum() + Integer.valueOf(x)));
			encDao.saveApplyToCustomsBillList(head); // 保存新的清单头
			oldHead.setMaterialNum(oldHead.getMaterialNum() == null ? 0
					: (oldHead.getMaterialNum() - Integer.valueOf(x)));
			encDao.saveApplyToCustomsBillList(oldHead);// 保存旧的清单头
		}
	}

	/**
	 * 重新排序
	 */
	public void billSort(String billNo) {
		List list = encDao.findAllAtcMergerBeforeComInfo(billNo);
		for (int i = 0; i < list.size(); i++) {
			AtcMergeBeforeComInfo before = (AtcMergeBeforeComInfo) list.get(i);
			before.setSerialNo(i + 1);
			encDao.saveAtcMergeBeforeComInfo(before);
		}
	}

	/**
	 * 保存进出口申请单
	 */
	public void saveImpExpRequestBills(List impExpRequestBills) {
		this.encDao.saveImpExpRequestBills(impExpRequestBills);
	}

	/**
	 * 保存申请单表头及表体,文本导入时使用.
	 */
	public List saveImpExpRequestBillAndImpExpCommodityInfo(List list,
			Boolean isMergeCommInfo, Boolean isSumMoney) {
		Map<String, ImpExpRequestBill> mapHead = new HashMap<String, ImpExpRequestBill>();
		Map<String, ImpExpCommodityInfo> mapDetail = new HashMap<String, ImpExpCommodityInfo>();
		Map<String, Integer> mapCount = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			TempImpExpRequestBillForInput input = (TempImpExpRequestBillForInput) list
					.get(i);
			ImpExpRequestBill impExpRequestBill = input.getBillHead();
			ImpExpCommodityInfo commInfo = input.getCommInfo();
			String key = impExpRequestBill.getBillNo()
					+ (impExpRequestBill.getScmCoc() == null ? ""
							: impExpRequestBill.getScmCoc().getCode());
			Integer count = mapCount.get(key);
			if (count == null) {
				count = this.encDao.findImpExpRequestBillCountByBillNo(
						impExpRequestBill.getBillNo(),
						impExpRequestBill.getScmCoc());
				mapCount.put(key, count);
			}
			if (count > 0) {
				continue;
			}
			if (mapHead.get(key) == null) {
				if (impExpRequestBill.getBeginAvailability() != null) {
					impExpRequestBill.setCreateDate(impExpRequestBill
							.getBeginAvailability());
				} else {
					impExpRequestBill.setCreateDate(new Date());
				}

				this.encDao.saveImpExpRequestBill(impExpRequestBill);
				mapHead.put(key, impExpRequestBill);
			} else {
				impExpRequestBill = (ImpExpRequestBill) mapHead.get(key);
			}
			// 将相同料号,产销国,海关版本的商品数量、净重、毛重、件数、单价累计
			if (isMergeCommInfo) {
				String commInfoKey = key
						+ commInfo.getMateriel().getPtNo()
						+ "/"
						+ (commInfo.getCountry() == null ? "" : commInfo
								.getCountry().getCode())
						+ "/"
						+ (commInfo.getVersion() == null
								|| "".equals(commInfo.getVersion()) ? ""
								: commInfo.getVersion());
				if (mapDetail.get(commInfoKey) == null) {
					ImpExpRequestBill bill = mapHead.get(key);
					bill.setItemCount(bill.getItemCount() == null ? 1 : bill
							.getItemCount() + 1);
					commInfo.setImpExpRequestBill(bill);
					this.encDao.saveImpExpComInfo(commInfo);
					mapDetail.put(commInfoKey, commInfo);
				} else {
					ImpExpCommodityInfo oldCommInfo = mapDetail
							.get(commInfoKey);
					oldCommInfo.setQuantity(CommonUtils
							.getDoubleExceptNull(oldCommInfo.getQuantity())
							+ CommonUtils.getDoubleExceptNull(commInfo
									.getQuantity()));// 数量
					oldCommInfo.setGrossWeight(CommonUtils
							.getDoubleExceptNull(oldCommInfo.getGrossWeight())
							+ CommonUtils.getDoubleExceptNull(commInfo
									.getGrossWeight()));// 毛重

					oldCommInfo.setNetWeight(CommonUtils
							.getDoubleExceptNull(oldCommInfo.getNetWeight())
							+ CommonUtils.getDoubleExceptNull(commInfo
									.getNetWeight()));// 净重
					oldCommInfo.setPiece(CommonUtils
							.getIntegerExceptNull(oldCommInfo.getPiece())
							+ CommonUtils.getIntegerExceptNull(commInfo
									.getPiece()));

					//2014-3-17 根据系统参数设置判断金额计算
					if (Boolean.TRUE.equals(isSumMoney)) {
						oldCommInfo.setAmountPrice(CommonUtils
								.getDoubleExceptNull(oldCommInfo.getQuantity())
								* CommonUtils.getDoubleExceptNull(oldCommInfo
										.getUnitPrice()));
					} else {
						oldCommInfo.setAmountPrice(CommonUtils
								.getDoubleExceptNull(oldCommInfo
										.getAmountPrice())
								+ CommonUtils.getDoubleExceptNull(oldCommInfo
										.getAmountPrice()));
					}
					this.encDao.saveImpExpComInfo(oldCommInfo);

				}
			} else {
				ImpExpRequestBill bill = mapHead.get(key);
				bill.setItemCount(bill.getItemCount() == null ? 1 : bill
						.getItemCount() + 1);
				commInfo.setImpExpRequestBill(bill);
				this.encDao.saveImpExpComInfo(commInfo);
			}
		}
		List lsResult = new ArrayList();
		lsResult.addAll(mapHead.values());
		return lsResult;
	}

	/**
	 * 根据报关单时间统计成品总数量
	 */
	private Map<String, Double> calExgAmount(String emsNo, Date beginDate,
			Date endDate, Boolean isEffective,Integer[] exgSeqNums) {
		Map<String, Double> map = new HashMap<String, Double>();
		List lsCommInfo = this.encDao.findCommInfoVersionTotalAmount(emsNo,
				new Integer[] { ImpExpType.DIRECT_EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT,
						ImpExpType.REWORK_EXPORT }, beginDate, endDate, null,
				isEffective,exgSeqNums);
		for (int i = 0; i < lsCommInfo.size(); i++) {
			Object[] objs = (Object[]) lsCommInfo.get(i);
			String key = ((objs[0] == null ? "" : objs[0].toString()) + "/" + (objs[1] == null ? ""
					: objs[1].toString()));
			Double value = (objs[2] == null ? 0.0 : Double.valueOf(objs[2]
					.toString()));
			map.put(key, value);
			// System.out
			// .println("key:" + key + " value:" + value);
		}
		lsCommInfo = this.encDao.findCommInfoVersionTotalAmount(emsNo,
				new Integer[] { ImpExpType.BACK_FACTORY_REWORK }, beginDate,
				endDate, null, isEffective,exgSeqNums);
		for (int i = 0; i < lsCommInfo.size(); i++) {
			Object[] objs = (Object[]) lsCommInfo.get(i);
			String key = ((objs[0] == null ? "" : objs[0].toString()) + "/" + (objs[1] == null ? ""
					: objs[1].toString()));
			Double value = (objs[2] == null ? 0.0 : Double.valueOf(objs[2]
					.toString()));
			map.put(key, CommonUtils.getDoubleExceptNull(map.get(key)) - value);
			// System.out.println("---------3:key:" + key + " value:"
			// + (-value));
		}
		return map;
	}

	// private Map<String, Double> calExgMoney(String emsNo, Date beginDate,
	// Date endDate) {
	// Map<String, Double> map = new HashMap<String, Double>();
	// List lsCommInfo = this.encDao.findCommInfoVersionTotalMoney(emsNo,
	// new Integer[] { ImpExpType.DIRECT_EXPORT,
	// ImpExpType.TRANSFER_FACTORY_EXPORT,
	// ImpExpType.REWORK_EXPORT }, beginDate, endDate, null);
	// for (int i = 0; i < lsCommInfo.size(); i++) {
	// Object[] objs = (Object[]) lsCommInfo.get(i);
	// String key = ((objs[0] == null ? "" : objs[0].toString()) + (objs[1] ==
	// null ? ""
	// : objs[1].toString()));
	// Double value = (objs[2] == null ? 0.0 : Double.valueOf(objs[2]
	// .toString()));
	// map.put(key, value);
	// // System.out
	// // .println("---------2:key:" + key + " value:" + value);
	// }
	// lsCommInfo = this.encDao.findCommInfoVersionTotalMoney(emsNo,
	// new Integer[] { ImpExpType.BACK_FACTORY_REWORK }, beginDate,
	// endDate, null);
	// for (int i = 0; i < lsCommInfo.size(); i++) {
	// Object[] objs = (Object[]) lsCommInfo.get(i);
	// String key = ((objs[0] == null ? "" : objs[0].toString()) + (objs[1] ==
	// null ? ""
	// : objs[1].toString()));
	// Double value = (objs[2] == null ? 0.0 : Double.valueOf(objs[2]
	// .toString()));
	// map.put(key, CommonUtils.getDoubleExceptNull(map.get(key)) - value);
	// // System.out.println("---------3:key:" + key + " value:"
	// // + (-value));
	// }
	// return map;
	// }

	/**
	 * 出口成品耗用料件统计表
	 * 
	 * @param emsHeadH2k
	 * @return
	 */
	public List findExgUsedImgAmountInfo(EmsHeadH2k emsHeadH2k,
			Integer[] exgSeqNums, Date beginDate, Date endDate,
			Integer impExpType, boolean isPrintZeroTotalUsed,
			Boolean isEffective) {
		List lsResult = new ArrayList();
		Map<String, Double> map = new HashMap<String, Double>();
		List lsCommInfo = new ArrayList();
		if (impExpType != null) {
			lsCommInfo = this.encDao.findCommInfoVersionTotalAmount(
					emsHeadH2k.getEmsNo(), new Integer[] { impExpType },
					beginDate, endDate, null, isEffective,exgSeqNums);
			for (int i = 0; i < lsCommInfo.size(); i++) {
				Object[] objs = (Object[]) lsCommInfo.get(i);
				String key = ((objs[0] == null ? "" : objs[0].toString()) + "/" + (objs[1] == null ? ""
						: objs[1].toString()));
				Double value = (objs[2] == null ? 0.0 : Double.valueOf(objs[2]
						.toString()));
				map.put(key, value);
				// System.out
				// .println("---------1:key:" + key + " value:" + value);
			}
		} else {
			map = this.calExgAmount(emsHeadH2k.getEmsNo(), beginDate, endDate,
					isEffective,exgSeqNums);
		}

		// /////////////////////yp 2013//11/18加入
//		if (exgSeqNums == null || exgSeqNums.length == 0) {
		List exgSeqNumList=new ArrayList();
//			Set exgSeqNumSet = new HashSet();
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next().toString();
				String[] temps = key.split("/");
				Integer[] ints=new Integer[2];
				if (temps.length > 0 && temps[0] != null
						&& !"".equals(temps[0].toString().trim())) {
					if (NumberUtils.isNumber(temps[0].toString().trim())) {
//						exgSeqNumSet.add(Integer.valueOf(temps[0].toString()
//								.trim()));
						ints[0]=Integer.valueOf(temps[0].toString()
								.trim());
					}
				}
				if (temps.length > 0 && temps[1] != null
						&& !"".equals(temps[1].toString().trim())) {
					if (NumberUtils.isNumber(temps[1].toString().trim())) {
//						exgSeqNumSet.add(Integer.valueOf(temps[0].toString()
//								.trim()));
						ints[1]=Integer.valueOf(temps[1].toString()
								.trim());
					}
				}
				exgSeqNumList.add(ints);
			}
//			if(exgSeqNumSet.size()>0){
//				exgSeqNums=new Integer[exgSeqNumSet.size()];
//				List listTemp=new ArrayList();
//				listTemp.addAll(exgSeqNumSet);
//				for(int i=0;i<listTemp.size();i++){
//					exgSeqNums[i]=(Integer)listTemp.get(i);
//				}
//			}
			System.out.println("-----------------exgSeqNums length:"
					+ exgSeqNumList.size());
//		}
		if (exgSeqNumList.size() == 0) {
			return new ArrayList();
		}
		// /////////////////////
		List lsBom=new ArrayList();
		List lsTemp=new ArrayList();
		for(int i=0;i<exgSeqNumList.size();i++){			
			lsTemp.add(exgSeqNumList.get(i));
			if(lsTemp.size()>=900){
				lsBom.addAll(this.encDao.findEmsHeadH2kBomByExgSeqNumAndVersion(emsHeadH2k, lsTemp));
				lsTemp.clear();
			}
		}
		if(lsTemp.size()>0){
			lsBom.addAll(this.encDao.findEmsHeadH2kBomByExgSeqNumAndVersion(emsHeadH2k, lsTemp));
		}

		List newLsBom = new ArrayList();
		// 当勾选了不显示耗用为0的料件时，当成品的某个版本出口数量为0时，不显示这条记录
		if (isPrintZeroTotalUsed) {
			for (int i = 0; i < lsBom.size(); i++) {
				EmsHeadH2kBom bom = (EmsHeadH2kBom) lsBom.get(i);
				String seqNum = bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
						.getSeqNum() == null ? "" : bom.getEmsHeadH2kVersion()
						.getEmsHeadH2kExg().getSeqNum().toString();
				String version = bom.getEmsHeadH2kVersion().getVersion() == null ? ""
						: bom.getEmsHeadH2kVersion().getVersion().toString();
				String key = seqNum + "/" + version;
				Double exgAmount = 0.0;
				exgAmount = CommonUtils.getDoubleExceptNull(map.get(key));
				if (exgAmount != 0.0) {
					newLsBom.add(bom);
				}
			}
			lsBom.clear();
			lsBom = newLsBom;
		}

		for (int i = 0; i < lsBom.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) lsBom.get(i);
			TempExgUsedImgAmount temp = new TempExgUsedImgAmount();
			temp.setExgSeqNum(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getSeqNum());
			temp.setExgComplex(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getComplex().getCode());
			temp.setExgName(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getName());
			temp.setExgSpec(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getSpec());
			temp.setExgUnit(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getUnit().getName());
			temp.setVersion(bom.getEmsHeadH2kVersion().getVersion());
			temp.setImgSeqNum(bom.getSeqNum());
			temp.setImgComplex(bom.getComplex() == null ? "" : bom.getComplex()
					.getCode());
			temp.setImgName(bom.getName());
			temp.setImgSpec(bom.getSpec());
			temp.setImgUnit(bom.getUnit() == null ? "" : bom.getUnit()
					.getName());
			String key = ((temp.getExgSeqNum() == null ? "" : temp
					.getExgSeqNum().toString()) + "/" + (temp.getVersion() == null ? ""
					: temp.getVersion().toString()));
			temp.setExgAmount(CommonUtils.getDoubleExceptNull(map.get(key)));
			temp.setUnitUsed(CommonUtils.getDoubleByDigit(
					CommonUtils.getDoubleExceptNull(bom.getUnitWear())
							* temp.getExgAmount(), 5));
			temp.setWaste(CommonUtils.getDoubleByDigit(
					(CommonUtils.getDoubleExceptNull(bom.getWear()) / 100.0)
							* (temp.getExgAmount() * (CommonUtils
									.getDoubleExceptNull(bom.getUnitWear()) / (1 - CommonUtils
									.getDoubleExceptNull(bom.getWear()) / 100.0))),
					5));//
			temp.setTotalUsed(temp.getUnitUsed() + temp.getWaste());
			temp.setWasteNew(bom.getWear());
			temp.setUnitWearNew(bom.getUnitWear());
			lsResult.add(temp);
		}
		return lsResult;
	}

	/**
	 * 初始化日期
	 */
	private Date[] getBeginEndDateByTerm(String term) {
		Date[] dates = new Date[2];
		int year = Integer.parseInt(term.substring(0, 4));
		int month = Integer.parseInt(term.substring(5)) - 1;
		GregorianCalendar calendar = new GregorianCalendar(year, month, 1);
		dates[0] = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		dates[1] = calendar.getTime();
		return dates;
	}

	/**
	 * 把list转换为HashMap，list(o)为key,list(1)value
	 * 
	 * @param list
	 *            要转换的list
	 * @return HashMap
	 */
	private HashMap<String, Double> converListToHashTable(List list) {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String key = (objs[0] == null ? "" : objs[0].toString());
			Double value = objs[1] == null ? 0 : Double.parseDouble(objs[1]
					.toString());
			// System.out.println("::::::::" + key + "|||||||" + value);
			hm.put(key, value);
		}
		return hm;
	}

	/**
	 * 找出帐册最早的月结期间
	 * 
	 * @param emsHeadH2k
	 */
	public String findInitCustomsMonthTerm(EmsHeadH2k emsHeadH2k) {
		Date date = this.encDao.findFirstDayFromCustomsDeclaration(emsHeadH2k
				.getEmsNo());
		if (date != null) {
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			return format.format(date).substring(0, 7);
		}
		return null;
	}

	/**
	 * 转换日期
	 */
	private String[] getMonthTermsFromBeginToEndTerm(String beginTerm,
			String endTerm) {
		int year = Integer.parseInt(beginTerm.substring(0, 4));
		int month = Integer.parseInt(beginTerm.substring(5)) - 1;
		int diff = 2020 - year;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i <= diff; i++) {
			for (int j = month; j <= 12; j++) {
				String smonth = String.valueOf(j);
				if (smonth.length() == 1) {
					smonth = ("0" + smonth);
				}
				String term = (year + i + "/" + smonth);
				if (smonth.equals("00")) {
					smonth = "12";
					term = (year - 1 + i + "/" + smonth);
				}
				if (term.compareTo(endTerm) <= 0) {
					list.add(term);
				} else {
					break;
				}
			}
			month = 1;
		}
		String[] terms = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			terms[i] = list.get(i);
		}
		return terms;
	}

	/**
	 * 月报表统计
	 */
	public void statCustomsInfoByMonth(EmsHeadH2k emsHeadH2k, String preTerm,
			String beginTerm, String endTerm) {
		if (beginTerm.compareTo(endTerm) > 0) {
			return;
		}
		String[] terms = getMonthTermsFromBeginToEndTerm(beginTerm, endTerm);
		String previousTerm, currentTerm;
		for (int i = 0; i < terms.length; i++) {
			if (i == 0) {
				previousTerm = preTerm;
			} else {
				previousTerm = terms[i - 1];
			}
			currentTerm = terms[i];
			// System.out.println("----------------------");
			// System.out.println("previousTerm--"
			// + (previousTerm == null ? "" : previousTerm));
			// System.out.println("currentTerm"
			// + (currentTerm == null ? "" : currentTerm));
			// System.out.println("----------------------");
			if ((previousTerm == null ? "" : previousTerm)
					.equals(currentTerm == null ? "" : currentTerm)) {
				continue;
			}
			statCustomsInfoByMonth(emsHeadH2k, previousTerm, currentTerm);
		}
	}

	/**
	 * 进口料件月结
	 * 
	 * @param emsHeadH2k
	 * @param preTerm
	 * @param currentTerm
	 */
	private void statCustomsInfoByMonth(EmsHeadH2k emsHeadH2k, String preTerm,
			String currentTerm) {
		String emsNo = emsHeadH2k.getEmsNo();
		Date[] beginEndDate = getBeginEndDateByTerm(currentTerm);
		Date beginDate = beginEndDate[0];
		Date endDate = beginEndDate[1];
		Map<Integer, CustomsMonthStatInfo> preMap = new HashMap<Integer, CustomsMonthStatInfo>();
		if (preTerm != null && !"".equals(preTerm)) {
			List lsPre = this.encDao.findCustomsMonthStatInfoByTerm(emsNo,
					preTerm);
			if (lsPre.size() == 0) {
				throw new RuntimeException("帐册" + emsHeadH2k.getEmsNo() + "在期间"
						+ preTerm + "没有做月结");
			}
			for (int i = 0; i < lsPre.size(); i++) {
				CustomsMonthStatInfo preStatInfo = (CustomsMonthStatInfo) lsPre
						.get(i);
				preMap.put(preStatInfo.getSeqNum(), preStatInfo);
			}
		}
		this.encDao.deleteCustomsMonthStatTerm(emsNo, currentTerm);
		/**
		 * 料件进口数量（直接进口，转厂进口，余料进口）
		 */
		HashMap<String, Double> hmImgImportAmount = converListToHashTable(this.encDao
				.findCommInfoTotalAmount(emsNo, new Integer[] {
						ImpExpType.DIRECT_IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT }, beginDate, endDate,
						null));
		/**
		 * 料件退还出口数量
		 */
		HashMap<String, Double> hmImgExportAmount = converListToHashTable(this.encDao
				.findCommInfoTotalAmount(emsNo, new Integer[] {
						ImpExpType.BACK_MATERIEL_EXPORT,
						ImpExpType.MATERIAL_REOUT,
						ImpExpType.REMAIN_FORWARD_EXPORT }, beginDate, endDate,
						null));

		/**
		 * 料件进口金额（直接进口，转厂进口，余料进口）
		 */
		HashMap<String, Double> hmImgImportMoney = converListToHashTable(this.encDao
				.findCommInfoTotalMoney(emsNo, new Integer[] {
						ImpExpType.DIRECT_IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT }, beginDate, endDate,
						null));
		/**
		 * 料件退还出口金额
		 */
		HashMap<String, Double> hmImgExportMoney = converListToHashTable(this.encDao
				.findCommInfoTotalMoney(emsNo, new Integer[] {
						ImpExpType.BACK_MATERIEL_EXPORT,
						ImpExpType.MATERIAL_REOUT,
						ImpExpType.REMAIN_FORWARD_EXPORT }, beginDate, endDate,
						null));

		Map<String, Double> hmExgAmount = this.calExgAmount(emsNo, beginDate,
				endDate, true,null);
		// Map<String, Double> hmExgMoney = this.calExgAmount(emsNo, beginDate,
		// endDate);
		Map<Integer, Double> hmExgUsedAmount = new HashMap<Integer, Double>();
		// Map<Integer, Double> hmExgUsedMoney = new HashMap<Integer, Double>();
		List lsBom = this.encDao.findEmsHeadH2kBom(emsHeadH2k, null);
		for (int i = 0; i < lsBom.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) lsBom.get(i);
			String key = ((bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getSeqNum() == null ? "" : bom.getEmsHeadH2kVersion()
					.getEmsHeadH2kExg().getSeqNum().toString())
					+ "/" + (bom.getEmsHeadH2kVersion().getVersion() == null ? ""
					: bom.getEmsHeadH2kVersion().getVersion().toString()));
			double exgAmount = CommonUtils.getDoubleExceptNull(hmExgAmount
					.get(key));
			// 料件耗用量 = sum(料件a数量=成品A数量*(a的单耗/(1-a损耗/100))) ?
			// 料件耗用量 = sum(料件a数量=成品A数量*(a的单耗/(1-a损耗))) ?

			double imgAmount = CommonUtils
					.getDoubleByDigit(
							exgAmount
									* (CommonUtils.getDoubleExceptNull(bom
											.getUnitWear()) / (1 - CommonUtils
											.getDoubleExceptNull(bom.getWear()) / 100.0)),
							5);
			double hasImgAmount = CommonUtils
					.getDoubleExceptNull(hmExgUsedAmount.get(bom.getSeqNum()));
			hmExgUsedAmount.put(bom.getSeqNum(), imgAmount + hasImgAmount);

			// double exgMoney = CommonUtils.getDoubleExceptNull(hmExgMoney
			// .get(key));
			// double imgMoney = CommonUtils
			// .getDoubleByDigit(
			// exgMoney
			// * (CommonUtils.getDoubleExceptNull(bom
			// .getUnitWear()) / (1 - CommonUtils
			// .getDoubleExceptNull(bom.getWear()) / 100.0)),
			// 5);
			// double hasImgMoney =
			// CommonUtils.getDoubleExceptNull(hmExgUsedMoney
			// .get(bom.getSeqNum()));
			// hmExgUsedMoney.put(bom.getSeqNum(), imgMoney + hasImgMoney);
		}
		List lsImg = this.encDao.findEmsHeadH2kImg(emsNo);

		for (int i = 0; i < lsImg.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) lsImg.get(i);
			CustomsMonthStatInfo preInfo = preMap.get(img.getSeqNum());
			CustomsMonthStatInfo statInfo = new CustomsMonthStatInfo();
			statInfo.setEmsNo(emsHeadH2k.getEmsNo());
			statInfo.setStatTerm(currentTerm);
			statInfo.setSeqNum(img.getSeqNum());
			statInfo.setComplex(img.getComplex());
			statInfo.setName(img.getName());
			statInfo.setSpec(img.getSpec());
			statInfo.setUnit(img.getUnit());
			if (preInfo != null) {
				statInfo.setPreAmount(preInfo.getEndAmount());
				statInfo.setPreMoney(preInfo.getEndMoney());
			}
			statInfo.setCurrentImpAmount(CommonUtils
					.getDoubleExceptNull(hmImgImportAmount.get(img.getSeqNum()
							.toString()))
					- CommonUtils.getDoubleExceptNull(hmImgExportAmount.get(img
							.getSeqNum().toString())));
			//
			statInfo.setCurrentUsedAmount(CommonUtils
					.getDoubleExceptNull(hmExgUsedAmount.get(img.getSeqNum())));
			statInfo.setCurrentAmount(CommonUtils.getDoubleExceptNull(statInfo
					.getCurrentImpAmount())
					- CommonUtils.getDoubleExceptNull(statInfo
							.getCurrentUsedAmount()));

			statInfo.setCurrentImpMoney(CommonUtils
					.getDoubleExceptNull(hmImgImportMoney.get(img.getSeqNum()
							.toString()))
					- CommonUtils.getDoubleExceptNull(hmImgExportMoney.get(img
							.getSeqNum().toString())));
			// statInfo.setCurrentUsedMoney(CommonUtils
			// .getDoubleExceptNull(hmExgUsedMoney.get(img.getSeqNum())));
			// statInfo.setCurrentMoney(CommonUtils.getDoubleExceptNull(statInfo
			// .getCurrentImpMoney())
			// - CommonUtils.getDoubleExceptNull(statInfo
			// .getCurrentUsedMoney()));

			double money = CommonUtils.getDoubleExceptNull(statInfo
					.getCurrentImpMoney())
					+ CommonUtils.getDoubleExceptNull(statInfo.getPreMoney());
			double amount = CommonUtils.getDoubleExceptNull(statInfo
					.getCurrentImpAmount())
					+ CommonUtils.getDoubleExceptNull(statInfo.getPreAmount());
			if (amount == 0.0) {
				statInfo.setAverageUnitPrice(0.0);
			} else {
				statInfo.setAverageUnitPrice(CommonUtils.getDoubleByDigit(money
						/ amount, 5));
			}
			statInfo.setEndAmount(amount
					- CommonUtils.getDoubleExceptNull(statInfo
							.getCurrentUsedAmount()));
			statInfo.setEndMoney(CommonUtils.getDoubleExceptNull(statInfo
					.getEndAmount())
					* CommonUtils.getDoubleExceptNull(statInfo
							.getAverageUnitPrice()));
			this.encDao.saveOrUpdate(statInfo);
		}
	}

	/**
	 * 出口成品耗用料件统计月报表
	 * 
	 * @param emsHeadH2k
	 * @return
	 */
	public List findExgUsedImgMonthAmountInfo(EmsHeadH2k emsHeadH2k,
			Integer[] exgSeqNums, String term, Integer impExpType) {
		List lsResult = new ArrayList();
		Map<String, Double> map = new HashMap<String, Double>();
		Date[] beginEndDate = getBeginEndDateByTerm(term);
		Date beginDate = beginEndDate[0];
		Date endDate = beginEndDate[1];
		List lsCommInfo = new ArrayList();
		if (impExpType != null) {
			lsCommInfo = this.encDao.findCommInfoVersionTotalAmount(
					emsHeadH2k.getEmsNo(), new Integer[] { impExpType },
					beginDate, endDate, null, true,null);
			for (int i = 0; i < lsCommInfo.size(); i++) {
				Object[] objs = (Object[]) lsCommInfo.get(i);
				String key = ((objs[0] == null ? "" : objs[0].toString()) + (objs[1] == null ? ""
						: objs[1].toString()));
				Double value = (objs[2] == null ? 0.0 : Double.valueOf(objs[2]
						.toString()));
				map.put(key, value);
				// System.out
				// .println("---------1:key:" + key + " value:" + value);
			}
		} else {
			map = this.calExgAmount(emsHeadH2k.getEmsNo(), beginDate, endDate,
					true,null);
		}
		Map<Integer, CustomsMonthStatInfo> monthMap = new HashMap<Integer, CustomsMonthStatInfo>();
		if (term != null && !"".equals(term)) {
			List lsPre = this.encDao.findCustomsMonthStatInfoByTerm(
					emsHeadH2k.getEmsNo(), term);
			// if (lsPre.size() == 0) {
			// throw new RuntimeException("帐册" + emsHeadH2k.getEmsNo() + "在期间"
			// + preTerm + "没有做月结");
			// }
			for (int i = 0; i < lsPre.size(); i++) {
				CustomsMonthStatInfo preStatInfo = (CustomsMonthStatInfo) lsPre
						.get(i);
				monthMap.put(preStatInfo.getSeqNum(), preStatInfo);
			}
		}
		List lsBom = this.encDao.findEmsHeadH2kBom(emsHeadH2k, exgSeqNums);
		for (int i = 0; i < lsBom.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) lsBom.get(i);
			if (bom.getName() != null && bom.getName().indexOf("国内购买") >= 0) {
				continue;
			}
			TempExgUsedImgAmount temp = new TempExgUsedImgAmount();
			CustomsMonthStatInfo statInfo = monthMap.get(bom.getSeqNum());
			temp.setExgSeqNum(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getSeqNum());
			temp.setExgComplex(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getComplex().getCode());
			temp.setExgName(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getName());
			temp.setExgSpec(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getSpec());
			temp.setExgUnit(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
					.getUnit().getName());
			temp.setVersion(bom.getEmsHeadH2kVersion().getVersion());
			temp.setImgSeqNum(bom.getSeqNum());
			temp.setImgComplex(bom.getComplex().getCode());
			temp.setImgName(bom.getName());
			temp.setImgSpec(bom.getSpec());
			temp.setImgUnit(bom.getUnit().getName());
			String key = ((temp.getExgSeqNum() == null ? "" : temp
					.getExgSeqNum().toString()) + (temp.getVersion() == null ? ""
					: temp.getVersion().toString()));
			temp.setExgAmount(CommonUtils.getDoubleExceptNull(map.get(key)));
			temp.setUnitUsed(CommonUtils.getDoubleByDigit(
					CommonUtils.getDoubleExceptNull(bom.getUnitWear())
							* temp.getExgAmount(), 5));
			temp.setWaste(CommonUtils.getDoubleByDigit(
					(CommonUtils.getDoubleExceptNull(bom.getWear()) / 100.0)
							* (temp.getExgAmount() * (CommonUtils
									.getDoubleExceptNull(bom.getUnitWear()) / (1 - CommonUtils
									.getDoubleExceptNull(bom.getWear()) / 100.0))),
					5));
			temp.setTotalUsed(temp.getUnitUsed() + temp.getWaste());
			if (statInfo != null) {
				temp.setUnitPrice(CommonUtils.getDoubleExceptNull(statInfo
						.getAverageUnitPrice()));
				temp.setTotalMoney(CommonUtils.getDoubleExceptNull(temp
						.getUnitPrice())
						* CommonUtils.getDoubleExceptNull(temp.getTotalUsed()));
			}
			lsResult.add(temp);
		}
		return lsResult;
	}

	/**
	 * 状态转换
	 * 
	 * @return
	 */
	private Map getListstateMap() {
		HashMap map = new HashMap();
		map.put("草稿", ApplyToCustomsBillList.DRAFT);
		map.put("已生效", ApplyToCustomsBillList.Effect);
		map.put("已经申报", ApplyToCustomsBillList.ALREADY_SEND);
		map.put("审批通过", ApplyToCustomsBillList.PASSED);
		return map;
	}

	/**
	 * 检查导入到清单的数据
	 * 
	 * @param list
	 *            在文件获取数据后的List
	 * @param importApplyProperty
	 *            参数和列顺序
	 * @return
	 */
	public List checkImportFileData(List list,
			ImportApplyCustomsProperty importApplyProperty) {
		ArrayList ls = new ArrayList();
		ImportApplyToCustomsBillListTempData fileData = null;
		importApplyProperty = new ImportApplyCustomsProperty();
		System.out.println("start");
		// -------------------单头
		// 监管方式Map
		Map tradeModeMap = getObjectMapByTableName("Trade");
		// 申报地海关Map
		Map declareCustomMap = getObjectMapByTableName("Customs");
		// 运输方式Map
		Map transportModeMap = getObjectMapByTableName("Transf");
		// 申报单位Map
		Map declareCompanyMap = getObjectMapByTableName("Company");
		// 进/出口岸Map
		Map entrancePortMap = getObjectMapByTableName("Customs");
		// 清单状态
		Map listStateMap = getListstateMap();

		// -------------------归并前
		// 事业部Map
		Map beforeProjectDeptMap = getObjectMapByTableName("ProjectDept");
		// 币别Map
		Map beforeCurrencyMap = getObjectMapByTableName("Curr");
		// 产销国(地区)Map
		Map beforeSalesCountryMap = getObjectMapByTableName("Country");
		// 征免方式Map
		Map beforeLevyModelMap = getObjectMapByTableName("LevyMode");
		// 用途Map
		Map beforeUsessMap = getObjectMapByTableName("Uses");

		Map impExpTypeMap = getImpExpTypeMap();// 进出口类型Map

		for (int i = 0; i < list.size(); i++) {
			fileData = (ImportApplyToCustomsBillListTempData) list.get(i);
			// -------------------单头

			// 判断 进出口类型
			System.out.println("importApplyProperty.getCbbImpExpType()");
			Integer impExpType = (Integer) impExpTypeMap.get(fileData
					.getHeadImpExpType());
			if (impExpType == null) {
				String s = "没有 \"" + fileData.getHeadImpExpType()
						+ "\" 的进出口类型;;应该为中文汉字，如：料件进口等";
				fileData.setErrinfo(fileData.getErrinfo() + s);
			}

			// 判断 监管方式
			System.out.println("importApplyProperty.getCbbTradeMode()");
			if (tradeModeMap.get(fileData.getHeadTradeMode()) == null) {
				if (importApplyProperty.getCbbTradeModeCodeName() == 0) {
					String s = "单头－监管方式中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "单头－监管方式中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 申报地海关
			System.out.println("importApplyProperty.getCbbDeclareCustom()");

			if (declareCustomMap.get(fileData.getHeadDeclareCustom()) == null) {
				if (importApplyProperty.getCbbDeclareCustomCodeName() == 0) {
					String s = "单头－申报地海关中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "单头－申报地海关中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 运输方式
			System.out.println("importApplyProperty.getCbbTransportMode()");

			if (transportModeMap.get(fileData.getHeadTransportMode()) == null) {
				if (importApplyProperty.getCbbTransportModeCodeName() == 0) {
					String s = "单头－运输方式中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "单头－运输方式中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 申报单位
			System.out.println("importApplyProperty.getCbbDeclareCompany()");
			if (declareCompanyMap.get(fileData.getHeadDeclareCompany()) == null) {
				System.out
						.println("fileData.getHeadDeclareCompany()-----------"
								+ fileData.getHeadDeclareCompany() + "testName");
				if (importApplyProperty.getCbbDeclareCompanyCodeName() == 0) {
					String s = "单头－申报单位中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "单头－申报单位中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 进/出口岸
			System.out.println("importApplyProperty.getCbbEntrancePort()");

			if (entrancePortMap.get(fileData.getHeadEntrancePort()) == null) {
				if (importApplyProperty.getCbbEntrancePortCodeName() == 0) {
					String s = "单头－进/出口岸中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "单头－进/出口岸中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 电子帐册编号
			System.out.println("importApplyProperty.getCbbEmsNo()");

			String[] property = new String[] { "emsNo" };
			String[] value = new String[] { fileData.getHeadEmsNo() };
			List list1 = encDao.findObjectByTableNames("EmsHeadH2k", property,
					value, true);
			if (list1.isEmpty()) {
				String s = "电子帐册管理中不存在这样的帐册编号资料;";
				fileData.setErrinfo(fileData.getErrinfo() + s);
			}

			// 判断 清单状态
			Integer listState = (Integer) listStateMap.get(fileData
					.getHeadListState());
			if (listState == null) {
				String s = "没有 \"" + fileData.getHeadListState()
						+ "\" 的清单状态;应该为中文汉字，如：草稿等";
				fileData.setErrinfo(fileData.getErrinfo() + s);
			}
			// -------------------归并前
			// 判断 事业部
			System.out.println("importApplyProperty.getCbbBeforeProjectDept()");

			if (beforeProjectDeptMap.get(fileData.getBeforeProjectDept()) == null) {
				if (importApplyProperty.getCbbBeforeProjectDeptCodeName() == 0) {
					String s = "归并前－事业部中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "归并前－事业部中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 币别
			System.out.println("importApplyProperty.getCbbBeforeCurrency()");

			if (beforeCurrencyMap.get(fileData.getBeforeCurrency()) == null) {
				if (importApplyProperty.getCbbBeforeCurrencyCodeName() == 0) {
					String s = "归并前－币别中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "归并前－币别中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

		}

		// 判断 产销国(地区)
		System.out.println("importApplyProperty.getCbbBeforeSalesCountry()");
		if (importApplyProperty.getCbbBeforeSalesCountry() != 0) {

			if (beforeSalesCountryMap.get(fileData.getBeforeSalesCountry()) == null) {
				if (importApplyProperty.getCbbBeforeSalesCountryCodeName() == 0) {
					String s = "归并前－产销国中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "归并前－产销国中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

		}

		// 判断 征免方式
		System.out.println("importApplyProperty.getCbbBeforeLevyModel()");
		if (importApplyProperty.getCbbBeforeLevyModel() != 0) {

			if (beforeLevyModelMap.get(fileData.getBeforeLevyModel()) == null) {
				if (importApplyProperty.getCbbBeforeLevyModelCodeName() == 0) {
					String s = "归并前－征免方式中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "归并前－征免方式中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 用途
			System.out.println("importApplyProperty.getCbbBeforeUsess()");

			if (beforeUsessMap.get(fileData.getBeforeUsess()) == null) {
				if (importApplyProperty.getCbbBeforeUsessCodeName() == 0) {
					String s = "归并前－用途中不存在这样的编码;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				} else {
					String s = "归并前－用途中不存在这样的名称;";
					fileData.setErrinfo(fileData.getErrinfo() + s);
				}
			}

			// 判断 商品货号资料
			System.out
					.println("importApplyProperty.getCbbBeforeMaterielPtNo()");

			Integer impExpType = (Integer) impExpTypeMap.get(fileData
					.getHeadImpExpType());
			System.out.println("impExpType------------------" + impExpType);
			if (impExpType != null) {

				// 判断归并前商号
				String tableName = "";
				String property = "";
				String name = "";
				if (impExpType == 0 || impExpType == 1 || impExpType == 3
						|| impExpType == 6 || impExpType == 8
						|| impExpType == 9) {
					tableName = "EmsEdiMergerImgBefore";
					property = "emsEdiMergerImgAfter";
					name = "料件";
				} else {
					tableName = "EmsEdiMergerExgBefore";
					property = "emsEdiMergerExgAfter";
					name = "成品";
				}

				List list1 = encDao.findBeforeMaterielPtNo(
						fileData.getBeforeMaterielPtNo(), tableName, null,
						property);
				if (list1.isEmpty()) {
					String s = "归并关系管理中的归并前" + name + "资料不存在这样的货号资料;";
					fileData.setErrinfo(fileData.getErrinfo() + s);

				} else {

					// 判断归并前商号对应的归并后序号是否在归并关系管理里存在
					System.out
							.println("importApplyProperty.getCbbAfterEmsSerialNo()------------------"
									+ importApplyProperty
											.getCbbAfterEmsSerialNo());
					if (importApplyProperty.getCbbAfterEmsSerialNo() != 0) {
						list1 = encDao
								.findBeforeMaterielPtNo(fileData
										.getBeforeMaterielPtNo(), tableName,
										Integer.valueOf(fileData
												.getAfterEmsSerialNo()),
										property);
						if (list1.isEmpty()) {
							String s = name + "商品货号在归并关系管理里找不到相对应的归并序号;";
							fileData.setErrinfo(fileData.getErrinfo() + s);
						} else {
							// 判断归并前商号对应的归并后序号是否在电子帐册管理里存在
							EmsHeadH2kExg emsHeadH2kExg;
							EmsHeadH2kImg emsHeadH2kImg;
							InnerMergeData innerMergeData = (InnerMergeData) list1
									.get(0);

							if (impExpType == 0 || impExpType == 1
									|| impExpType == 3 || impExpType == 6
									|| impExpType == 8 || impExpType == 9) {
								emsHeadH2kImg = encDao
										.getEmsHeadImg(innerMergeData
												.getHsAfterTenMemoNo());
								if (emsHeadH2kImg == null) {
									String s = "这样的归并序号不可用,请检查电子帐册管理是分段备案还是非分段备案,或者电子帐册管理备案的料件表中不存在这样的归并序号资料;";
									fileData.setErrinfo(fileData.getErrinfo()
											+ s);
								} else {
									if (emsHeadH2kImg.getModifyMark().equals(
											"2")
											|| emsHeadH2kImg.getModifyMark()
													.equals("3")) {
										String s = "归并序号对应的电子帐册管理备案料件在\"已删除\"或\"新增\"状态,不可用;";
										fileData.setErrinfo(fileData
												.getErrinfo() + s);
									}
								}
							} else {
								emsHeadH2kExg = encDao
										.getEmsHeadExg(innerMergeData
												.getHsAfterTenMemoNo());
								if (emsHeadH2kExg == null) {
									String s = "这样的归并序号不可用,请检查电子帐册管理是分段备案还是非分段备案,或者电子帐册管理备案的成品表中不存在这样的归并序号资料;";
									fileData.setErrinfo(fileData.getErrinfo()
											+ s);
								} else {
									if (emsHeadH2kExg.getModifyMark().equals(
											"2")
											|| emsHeadH2kExg.getModifyMark()
													.equals("3")) {
										String s = "归并序号对应的电子帐册管理备案成品不在备案状态;";
										fileData.setErrinfo(fileData
												.getErrinfo() + s);
									}
								}
							}

						}
					}
				}
			}

			// if (!fileData.getErrorReason().equals(""))
			ls.add(fileData);
			System.out.println("end");
		}
		System.out.println("CheckEnd");
		return ls;
	}

	/**
	 * 根据tableName，查找map
	 * 
	 * @param tableName
	 *            要找的实体类
	 * @param codeOrName
	 *            0时用code作为键,1时用name作为键
	 * @return
	 */
	private Map getObjectMapByTableName(String tableName) {
		Map map = new HashMap();
		List list = encDao.findObjectByTableNames(tableName, null, null, false);
		if (list.isEmpty())
			return map;
		CustomBaseEntity customBaseEntity = null;
		for (int i = 0; i < list.size(); i++) {
			if (tableName.equals("Trade"))
				customBaseEntity = (Trade) list.get(i);
			else if (tableName.equals("Customs")) {
				customBaseEntity = (Customs) list.get(i);
			} else if (tableName.equals("Transf")) {
				customBaseEntity = (Transf) list.get(i);
			} else if (tableName.equals("Company")) {
				Company company = (Company) list.get(i);
				if (map.get(company.getName()) == null) {
					map.put(company.getName(), company);
				}
				return map;
			} else if (tableName.equals("ProjectDept")) {
				ProjectDept projectDept = (ProjectDept) list.get(i);
				if (map.get(projectDept.getName()) == null) {
					map.put(projectDept.getName(), projectDept);
				}
			} else if (tableName.equals("Curr")) {
				customBaseEntity = (Curr) list.get(i);
			} else if (tableName.equals("Country")) {
				customBaseEntity = (Country) list.get(i);
			} else if (tableName.equals("LevyMode")) {
				customBaseEntity = (LevyMode) list.get(i);
			} else if (tableName.equals("ScmCoc")) {
				ScmCoc scmCoc = (ScmCoc) list.get(i);
				if (map.get(scmCoc.getCode()) == null) {
					map.put(scmCoc.getCode(), scmCoc);
				}
			} else {
				customBaseEntity = (Uses) list.get(i);
			}

			if (customBaseEntity != null
					&& map.get(customBaseEntity.getName()) == null) {
				map.put(customBaseEntity.getName(), customBaseEntity);
			}
		}
		return map;
	}

	/**
	 * 导入文件来自文件
	 * 
	 * @param list
	 */
	public void importDataFromFile(List ls,
			ImportApplyCustomsProperty importApplyProperty,
			boolean cbIsOverwrite) {
		Map headMap = new HashMap();// 用来存放新建的单头
		Map afterMap = new HashMap();// 用来存放新建的归并后
		Map beforMap = new HashMap();// 用来存放新建的归并前
		// -------------------单头
		// 监管方式Map
		Map tradeModeMap = getObjectMapByTableName("Trade");
		// 申报地海关Map
		Map declareCustomMap = getObjectMapByTableName("Customs");
		// 运输方式Map
		Map transportModeMap = getObjectMapByTableName("Transf");
		// 申报单位Map
		Map declareCompanyMap = getObjectMapByTableName("Company");
		// 进/出口岸Map
		Map entrancePortMap = getObjectMapByTableName("Customs");
		// 客户（供应商）
		Map headCustomsMap = getObjectMapByTableName("ScmCoc");
		// System.out.println("==headCustomsMap=="+headCustomsMap.size());

		Map impExpTypeMap = getImpExpTypeMap();// 进出口类型Map
		TempImportApplyToCustomsBillList data = null;
		String stringKey = "";

		// 首先把同料号同版本的资料进行合并
		List list = tempDataBeforeCombination(ls);
		// 是否覆盖导入
		if (cbIsOverwrite) {
			for (int i = 0; i < list.size(); i++) {
				stringKey = ((TempImportApplyToCustomsBillList) list.get(i))
						.getHeadBillListNo()
						+ "/"
						+ ((TempImportApplyToCustomsBillList) list.get(i))
								.getHeadEmsNo()
						+ "/"
						+ ((TempImportApplyToCustomsBillList) list.get(i))
								.getBeforeMaterielPtNo()
						+ "/"
						+ ((TempImportApplyToCustomsBillList) list.get(i))
								.getBeforeSalesCountry();
				// 先获取已存在的表头数据
				List lsBillHead = this.encDao
						.findApplyToCustomsBillListId(stringKey);
				for (int ii = 0; ii < lsBillHead.size(); ii++) {
					ApplyToCustomsBillList billhead = (ApplyToCustomsBillList) lsBillHead
							.get(ii);
					headMap.put(
							billhead.getListNo() + "/"
									+ billhead.getEmsHeadH2k(), billhead);
				}
				// 获取已存在的归并前表体数据
				List lsBillBeforDetail = this.encDao
						.findAtcMergeBeforeComInfoId(stringKey);
				for (int j = 0; j < lsBillBeforDetail.size(); j++) {
					AtcMergeBeforeComInfo billDetail = (AtcMergeBeforeComInfo) lsBillBeforDetail
							.get(j);
					beforMap.put(billDetail.getAfterComInfo().getBillList()
							.getListNo()
							+ "/"
							+ billDetail.getAfterComInfo().getBillList()
									.getEmsHeadH2k()
							+ "/"
							+ billDetail.getMateriel().getPtNo()
							+ "/"
							+ billDetail.getSalesCountry().getName(),
							billDetail);
				}
				// 获取已存在的归并后表体数据
				List lsBillAfterDetail = this.encDao
						.findAtcMergeAfterComInfoId(stringKey);
				for (int m = 0; m < lsBillAfterDetail.size(); m++) {
					AtcMergeAfterComInfo billDetail = (AtcMergeAfterComInfo) lsBillAfterDetail
							.get(m);
					afterMap.put(
							billDetail.getBillList().getListNo()
									+ "/"
									+ billDetail.getBillList().getEmsHeadH2k()
									+ "/"
									+ billDetail.getEmsSerialNo()
									+ "/"
									+ (billDetail.getVersion() == null ? ""
											: billDetail.getVersion()),
							billDetail);
					billDetail.setDeclaredAmount(0.0);
					billDetail.setLegalAmount(0.0);
					billDetail.setSecondLegalAmount(0.0);
					billDetail.setGrossWeight(0.0);
					billDetail.setNetWeight(0.0);
					billDetail.setPiece(0);
					billDetail.setTotalPrice(0.0);
					billDetail.setWorkUsd(0.0);
				}

			}
		}
		for (int i = 0; i < list.size(); i++) {
			data = (TempImportApplyToCustomsBillList) list.get(i);
			String headKey = data.getHeadBillListNo() + "/"
					+ data.getHeadEmsNo();
			// ------------- 建立表头
			if (headMap.get(headKey) == null) {
				ApplyToCustomsBillList applyToCustomsBillList = new ApplyToCustomsBillList();
				applyToCustomsBillList.setCompany((Company) CommonUtils
						.getCompany());// 公司
				applyToCustomsBillList.setTradeCode(((Company) CommonUtils
						.getCompany()).getBuCode());// 经营单位代码
				applyToCustomsBillList.setTradeName(((Company) CommonUtils
						.getCompany()).getBuName());// 经营单位名称
				Integer impExpType = (Integer) impExpTypeMap.get(data
						.getHeadImpExpType());
				applyToCustomsBillList.setImpExpType(impExpType);// 进出口类型
				// 清单编号
				applyToCustomsBillList.setListNo(data.getHeadBillListNo());// 清单编号
				// 电子帐册编号
				applyToCustomsBillList.setEmsHeadH2k(data.getHeadEmsNo());// 电子帐册编号
				// // 进出口标志
				if (impExpType == 0 || impExpType == 1 || impExpType == 2
						|| impExpType == 3 || impExpType == 8
						|| impExpType == 9 || impExpType == 17) {// ||
																	// impExpType
					applyToCustomsBillList.setImpExpFlag(0);// 进出口标志,进
				} else {
					applyToCustomsBillList.setImpExpFlag(1);// 进出口标志,出
				}
				if (impExpType == 0 || impExpType == 1 || impExpType == 3
						|| impExpType == 8 || impExpType == 6
						|| impExpType == 9) {
					applyToCustomsBillList.setMaterielProductFlag(2);// 料件成品标志,料件
				} else {
					applyToCustomsBillList.setMaterielProductFlag(0);// 料件成品标志,成品
				}
				// 录入员
				applyToCustomsBillList.setCreatedUser(CommonUtils.getAclUser());//
				// 录入员
				String[] property = new String[] { "code" };
				Object[] value = new Object[] { ((Company) CommonUtils
						.getCompany()).getBuCode() };
				List list1 = encDao.findObjectByTableNames("Brief", property,
						value, false);
				applyToCustomsBillList.setCreatedCompany((Brief) list1.get(0));//
				// 申报地海关
				applyToCustomsBillList.setDeclareCIQ((Customs) declareCustomMap
						.get(data.getHeadDeclareCustom()));
				// 监管方式
				applyToCustomsBillList.setTradeMode((Trade) tradeModeMap
						.get(data.getHeadTradeMode()));
				// 运输方式
				applyToCustomsBillList
						.setTransportMode((Transf) transportModeMap.get(data
								.getHeadTransportMode()));
				// 申报单位
				applyToCustomsBillList
						.setDeclarationCompany((Company) declareCompanyMap
								.get(data.getHeadDeclareCompany()));
				// 进/出口岸1
				applyToCustomsBillList.setImpExpCIQ((Customs) entrancePortMap
						.get(data.getHeadEntrancePort()));
				// 客户、供应商
				// System.out.println("===data.getHeadCustoms()=="+data.getHeadCustoms()
				// +"=="+headCustomsMap.get(data.getHeadCustoms()));
				applyToCustomsBillList.setScmCoc((ScmCoc) headCustomsMap
						.get(data.getHeadCustoms()));
				// 清单统一编号
				applyToCustomsBillList
						.setListUniteNo(data.getHeadUnifiedCode());
				// 清单状态
				if (data.getHeadListState() == null)
					applyToCustomsBillList
							.setListState(ApplyToCustomsBillList.DRAFT);
				else if (data.getHeadListState().equals("草稿")
						|| data.getHeadListState().equals("草稿(正在录入)"))
					applyToCustomsBillList
							.setListState(ApplyToCustomsBillList.DRAFT);
				else if (data.getHeadListState().equals("已经申报"))
					applyToCustomsBillList
							.setListState(ApplyToCustomsBillList.ALREADY_SEND);
				else if (data.getHeadListState().equals("审批通过"))
					applyToCustomsBillList
							.setListState(ApplyToCustomsBillList.PASSED);
				else if (data.getHeadListState().equals("生效"))
					applyToCustomsBillList
							.setListState(ApplyToCustomsBillList.Effect);

				// 清单申报日期
				String a = data.getHeadListDeclareDate();
				if (a != null) {
					String[] s = null;
					if (a.contains("-"))
						s = a.split("-");
					else if (a.contains("/"))
						s = a.split("/");
					else if (a.contains("\\"))
						s = a.split("\\");
					else if (a.contains("."))
						s = a.split(".");
					Calendar calendar = Calendar.getInstance();
					calendar.set(Integer.valueOf(s[0]),
							Integer.valueOf(s[1]) - 1, Integer.valueOf(s[2]));
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					applyToCustomsBillList.setListDeclareDate(calendar
							.getTime());
				}
				// 备注
				applyToCustomsBillList.setMemos(data.getHeadMemo());
				applyToCustomsBillList.setCreatedDate(new Date());
				headMap.put(applyToCustomsBillList.getListNo() + "/"
						+ applyToCustomsBillList.getEmsHeadH2k(),
						applyToCustomsBillList);
				applyToCustomsBillList = (ApplyToCustomsBillList) encDao
						.saveAndReturnObject(applyToCustomsBillList);
				// ------------- 归并后、归并前
				createAtcMergeAfterComInfo(afterMap, applyToCustomsBillList,
						data, cbIsOverwrite, beforMap);
			} else {
				ApplyToCustomsBillList applyToCustomsBillList = (ApplyToCustomsBillList) headMap
						.get(headKey);
				// 如果是覆盖导入清单表头
				if (cbIsOverwrite) {
					billListcbIsOverwrite(stringKey, data,
							applyToCustomsBillList);
				}
				// ------------- 归并后、归并前
				createAtcMergeAfterComInfo(afterMap, applyToCustomsBillList,
						data, cbIsOverwrite, beforMap);
			}
		}

	}

	/**
	 * 导入前先检查数据是否合法
	 * 
	 * @param mergerversionObj
	 * @param versionObj
	 * @param list
	 * @return
	 */

	public List tempDataCheck(ImportApplyCustomsProperty importApplyProperty,
			List list) {
		for (int i = 0; i < list.size(); i++) {
			ImportApplyToCustomsBillListTempData data = (ImportApplyToCustomsBillListTempData) list
					.get(i);

			List listInner = encDao.findImpExpCommodtyInfoByMateriel(data
					.getBeforeMaterielPtNo());

			if (listInner == null || listInner.size() <= 0) {
				String err = "该料号在内部归并不存在！";
				data.setErrinfo(data.getErrinfo() + err);
				continue;
			} else {
				InnerMergeData innerMergeData = (InnerMergeData) listInner
						.get(0);
				if (!innerMergeData.getMateriel().getScmCoi().getCoiProperty()
						.equals(MaterielType.FINISHED_PRODUCT)
						&& data.getBeforeWorkUsd() != null
						&& !"".equals(data.getBeforeWorkUsd())) {
					String err = "该料号[" + data.getBeforeMaterielPtNo()
							+ "]不属于出口，不能填写加工费总价";
					data.setErrinfo(data.getErrinfo() + err);
				}
				if (innerMergeData.getIsForbid() != null
						&& innerMergeData.getIsForbid()) {
					String err = "该料号[" + data.getBeforeMaterielPtNo()
							+ "]已经禁用，不能导入";
					data.setErrinfo(data.getErrinfo() + err);
				}

			}
			// 归并前－ 版本号
			Integer version = (data.getBeforeVersion() == null || ""
					.equals(data.getBeforeVersion().trim())) ? null : Integer
					.valueOf(data.getBeforeVersion());
			if (version != null) {
				// 归并前－商品货号
				String beforeMaterielPtNo = (data.getBeforeMaterielPtNo() == null
						|| "".equals(data.getBeforeMaterielPtNo()) ? "" : data
						.getBeforeMaterielPtNo());
				// 归并关系版本--归并前BOM
				EmsEdiMergerVersion mergerversionObj = this.encDao
						.findEmsEdiMergerVersion(version, beforeMaterielPtNo);
				if (mergerversionObj == null) {
					String err = "该料号[" + data.getBeforeMaterielPtNo() + "]版本["
							+ version + "]在归并前不存在！";
					data.setErrinfo(data.getErrinfo() + err);
				} else {
					int exgSeqNum = mergerversionObj.getEmsEdiMergerBefore()
							.getSeqNum();
					// 电子帐册历版本--电子帐册BOM
					EmsHeadH2kVersion versionObj = this.encDao
							.findEmsHeadH2kVersion(exgSeqNum, version);
					// 如果version在归并前BOM、电子帐册帐册帐册BOM都不存在就不导入
					if (versionObj == null) {
						String err = "该料号[" + data.getBeforeMaterielPtNo()
								+ "]对应的归并序号[" + exgSeqNum + "]成品版本[" + version
								+ "]在电子帐册中不存在！";
						data.setErrinfo(data.getErrinfo() + err);
					}
				}
			}
		}
		return list;

	}

	/**
	 * 首先把相同料号的资料进行合并
	 */
	private List tempDataBeforeCombination(List list) {
		Map headMap = new HashMap();
		ImportApplyToCustomsBillListTempData data = null;
		TempImportApplyToCustomsBillList temp = null;
		List overlist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			data = (ImportApplyToCustomsBillListTempData) list.get(i);

			// 以清单编号+帐册号+料号＋产销国（原产国）+版本号为key

			String headKey = data.getHeadBillListNo()
					+ "/"
					+ data.getHeadEmsNo()
					+ "/"
					+ data.getBeforeMaterielPtNo()
					+ "/"
					+ data.getBeforeSalesCountry()
					+ "/"
					+ (data.getBeforeVersion() == null ? "" : data
							.getBeforeVersion().trim());
			System.out.println("====headKey===" + headKey);
			if (headMap.get(headKey) == null) {
				temp = new TempImportApplyToCustomsBillList();
				// ---------表头
				temp.setHeadImpExpType(data.getHeadImpExpType());// 进出口类型
				temp.setHeadBillListNo(data.getHeadBillListNo());// 清单编号
				temp.setHeadEmsNo(data.getHeadEmsNo());// 电子帐册编号
				// 申报地海关
				temp.setHeadDeclareCustom(data.getHeadDeclareCustom());
				// 监管方式
				temp.setHeadTradeMode(data.getHeadTradeMode());
				// 运输方式
				temp.setHeadTransportMode(data.getHeadTransportMode());
				// 申报单位
				temp.setHeadDeclareCompany(data.getHeadDeclareCompany());
				// 进/出口岸
				temp.setHeadEntrancePort(data.getHeadEntrancePort());
				// 备注
				temp.setHeadMemo(data.getHeadMemo());
				// 客户/供应商
				temp.setHeadCustoms(data.getHeadCustoms());
				// ----------表体

				// 料号
				temp.setBeforeMaterielPtNo(data.getBeforeMaterielPtNo());
				// 事业部
				temp.setBeforeProjectDept(data.getBeforeProjectDept());
				// 币别
				temp.setBeforeCurrency(data.getBeforeCurrency());
				// 产销国(地区)
				temp.setBeforeSalesCountry(data.getBeforeSalesCountry());
				// 征免方式
				temp.setBeforeLevyModel(data.getBeforeLevyModel());
				// 用途
				temp.setBeforeUsess(data.getBeforeUsess());
				// 归并前－申报数量
				temp.setBeforeDeclaredAmount(data.getBeforeDeclaredAmount());
				// 归并前－总价
				temp.setBeforeTotalPrice(data.getBeforeTotalPrice());
				// 归并前－加工费总价
				temp.setBeforeWorkUsd(data.getBeforeWorkUsd());
				// 归并前－法定数量
				temp.setBeforeLegalAmount(data.getBeforeLegalAmount());
				// 归并前－第二法定数量
				temp.setBeforeSecondLegalAmount(data
						.getBeforeSecondLegalAmount());
				// 归并前－企业申报单价
				temp.setBeforeDeclaredPrice(data.getBeforeDeclaredPrice());
				// 归并前－版本号
				temp.setBeforeVersion(data.getBeforeVersion());
				// 归并前－毛重
				temp.setBeforeGrossWeight(data.getBeforeGrossWeight());
				// 归并前－净重
				temp.setBeforeNetWeight(data.getBeforeNetWeight());
				// 归并前－件数
				temp.setBeforePeice(data.getBeforePeice());
				// 归并前－箱号
				temp.setBeforeBoxNo(data.getBeforeBoxNo());
				// 归并前－备注
				temp.setBeforeMemos(data.getBeforeMemos());
				// 归并前－扩展备注
				temp.setBeforeExtendMemo(data.getBeforeExtendMemo());
				headMap.put(
						temp.getHeadBillListNo()
								+ "/"
								+ temp.getHeadEmsNo()
								+ "/"
								+ temp.getBeforeMaterielPtNo()
								+ "/"
								+ temp.getBeforeSalesCountry()
								+ "/"
								+ (data.getBeforeVersion() == null ? "" : data
										.getBeforeVersion().trim()), temp);
				overlist.add(temp);
			} else {
				temp = (TempImportApplyToCustomsBillList) headMap.get(headKey);
				// 归并前－申报数量
				Double amount = (data.getBeforeDeclaredAmount() == null
						|| "".equals(data.getBeforeDeclaredAmount()) ? 0.0
						: Double.valueOf(data.getBeforeDeclaredAmount()))
						+ (temp.getBeforeDeclaredAmount() == null
								|| "".equals(temp.getBeforeDeclaredAmount()) ? 0.0
								: Double.valueOf(temp.getBeforeDeclaredAmount()));
				temp.setBeforeDeclaredAmount(String.valueOf(amount));

				// 归并前－总价
				Double totalPrice = (data.getBeforeTotalPrice() == null
						|| "".equals(data.getBeforeTotalPrice()) ? 0.0 : Double
						.valueOf(data.getBeforeTotalPrice()))
						+ (temp.getBeforeTotalPrice() == null
								|| "".equals(temp.getBeforeTotalPrice()) ? 0.0
								: Double.valueOf(temp.getBeforeTotalPrice()));
				temp.setBeforeTotalPrice(String.valueOf(totalPrice));

				// 归并前－加工费总价
				Double workUsd = (data.getBeforeWorkUsd() == null
						|| "".equals(data.getBeforeWorkUsd()) ? 0.0 : Double
						.valueOf(data.getBeforeWorkUsd()))
						+ (temp.getBeforeWorkUsd() == null
								|| "".equals(temp.getBeforeWorkUsd()) ? 0.0
								: Double.valueOf(temp.getBeforeWorkUsd()));
				temp.setBeforeWorkUsd(String.valueOf(workUsd));
				// 归并前－法定数量
				Double legalAmount = (data.getBeforeLegalAmount() == null
						|| "".equals(data.getBeforeLegalAmount()) ? 0.0
						: Double.valueOf(data.getBeforeLegalAmount()))
						+ (temp.getBeforeLegalAmount() == null
								|| "".equals(temp.getBeforeLegalAmount()) ? 0.0
								: Double.valueOf(temp.getBeforeLegalAmount()));
				temp.setBeforeLegalAmount(String.valueOf(legalAmount));
				// 归并前－第二法定数量
				Double secondLegaiAmount = (data.getBeforeSecondLegalAmount() == null
						|| "".equals(data.getBeforeSecondLegalAmount()) ? 0.0
						: Double.valueOf(data.getBeforeSecondLegalAmount()))
						+ (temp.getBeforeSecondLegalAmount() == null
								|| "".equals(temp.getBeforeSecondLegalAmount()) ? 0.0
								: Double.valueOf(temp
										.getBeforeSecondLegalAmount()));
				temp.setBeforeSecondLegalAmount(String
						.valueOf(secondLegaiAmount));
				// 归并前－毛重
				Double grossWeight = (data.getBeforeGrossWeight() == null
						|| "".equals(data.getBeforeGrossWeight()) ? 0.0
						: Double.valueOf(data.getBeforeGrossWeight()))
						+ (temp.getBeforeGrossWeight() == null
								|| "".equals(temp.getBeforeGrossWeight()) ? 0.0
								: Double.valueOf(temp.getBeforeGrossWeight()));
				temp.setBeforeGrossWeight(String.valueOf(grossWeight));

				Double netWeight = (data.getBeforeNetWeight() == null
						|| "".equals(data.getBeforeNetWeight()) ? 0.0 : Double
						.valueOf(data.getBeforeNetWeight()))
						+ (temp.getBeforeNetWeight() == null
								|| "".equals(temp.getBeforeNetWeight()) ? 0.0
								: Double.valueOf(temp.getBeforeNetWeight()));
				// 归并前－净重
				temp.setBeforeNetWeight(String.valueOf(netWeight));
				// 归并前－件数
				Integer peice = (data.getBeforePeice() == null
						|| "".equals(data.getBeforePeice()) ? 0 : Integer
						.valueOf(data.getBeforePeice()))
						+ (temp.getBeforePeice() == null
								|| "".equals(temp.getBeforePeice()) ? 0
								: Integer.valueOf(temp.getBeforePeice()));

				temp.setBeforePeice(String.valueOf(peice));

				String boxNo = temp.getBeforeBoxNo();// 箱数
				if (boxNo != null && !"".equals(boxNo)) {
					if (data.getBeforeBoxNo() != null
							&& !"".equals(data.getBeforeBoxNo())) {
						temp.setBeforeBoxNo(boxNo + "," + data.getBeforeBoxNo());
					} else {
						temp.setBeforeBoxNo(boxNo);
					}
				} else {
					temp.setBeforeBoxNo(data.getBeforeBoxNo());
				}

			}
		}
		return overlist;
	}

	/**
	 * 覆盖导入清单表头
	 */
	private void billListcbIsOverwrite(String stringKey,
			ImportApplyToCustomsBillListTempData data,
			ApplyToCustomsBillList applyToCustomsBillList) {
		// 监管方式Map
		Map tradeModeMap = getObjectMapByTableName("Trade");
		// 申报地海关Map
		Map declareCustomMap = getObjectMapByTableName("Customs");
		// 运输方式Map
		Map transportModeMap = getObjectMapByTableName("Transf");
		// 申报单位Map
		Map declareCompanyMap = getObjectMapByTableName("Company");
		// 进/出口岸Map
		Map entrancePortMap = getObjectMapByTableName("Customs");
		// 客户（供应商）
		Map headCustomsMap = getObjectMapByTableName("ScmCoc");
		// 进出口类型Map
		Map impExpTypeMap = getImpExpTypeMap();
		Integer impExpType = (Integer) impExpTypeMap.get(data
				.getHeadImpExpType());
		// 进出口类型
		applyToCustomsBillList.setImpExpType(impExpType);
		// 申报地海关
		applyToCustomsBillList.setDeclareCIQ((Customs) declareCustomMap
				.get(data.getHeadDeclareCustom()));
		// 监管方式
		applyToCustomsBillList.setTradeMode((Trade) tradeModeMap.get(data
				.getHeadTradeMode()));
		// 运输方式
		applyToCustomsBillList.setTransportMode((Transf) transportModeMap
				.get(data.getHeadTransportMode()));
		// 进/出口岸
		applyToCustomsBillList.setImpExpCIQ((Customs) entrancePortMap.get(data
				.getHeadEntrancePort()));
		// 客户、供应商
		applyToCustomsBillList.setScmCoc((ScmCoc) headCustomsMap.get(data
				.getHeadCustoms()));
		// 清单状态
		if (data.getHeadListState() == null)
			applyToCustomsBillList.setListState(ApplyToCustomsBillList.DRAFT);
		else if (data.getHeadListState().equals("草稿")
				|| data.getHeadListState().equals("草稿(正在录入)"))
			applyToCustomsBillList.setListState(ApplyToCustomsBillList.DRAFT);
		else if (data.getHeadListState().equals("已经申报"))
			applyToCustomsBillList
					.setListState(ApplyToCustomsBillList.ALREADY_SEND);
		else if (data.getHeadListState().equals("审批通过"))
			applyToCustomsBillList.setListState(ApplyToCustomsBillList.PASSED);
		else if (data.getHeadListState().equals("生效"))
			applyToCustomsBillList.setListState(ApplyToCustomsBillList.Effect);

		// 清单申报日期
		String a = data.getHeadListDeclareDate();
		if (a != null) {
			String[] s = null;
			if (a.contains("-"))
				s = a.split("-");
			else if (a.contains("/"))
				s = a.split("/");
			else if (a.contains("\\"))
				s = a.split("\\");
			else if (a.contains("."))
				s = a.split(".");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Integer.valueOf(s[0]), Integer.valueOf(s[1]) - 1,
					Integer.valueOf(s[2]));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			applyToCustomsBillList.setListDeclareDate(calendar.getTime());
		}

		// 备注
		applyToCustomsBillList.setMemos(data.getHeadMemo());
		applyToCustomsBillList.setCreatedDate(new Date());
		applyToCustomsBillList = (ApplyToCustomsBillList) encDao
				.saveAndReturnObject(applyToCustomsBillList);
	}

	/**
	 * 获取进出口类型
	 * 
	 * @return
	 */
	private Map getImpExpTypeMap() {
		HashMap map = new HashMap();
		map.put("料件进口", ImpExpType.DIRECT_IMPORT);
		map.put("直接进口", ImpExpType.DIRECT_IMPORT);
		map.put("料件转厂", ImpExpType.TRANSFER_FACTORY_IMPORT);
		map.put("转厂进口", ImpExpType.TRANSFER_FACTORY_IMPORT);
		map.put("退厂返工", ImpExpType.BACK_FACTORY_REWORK);
		map.put("一般贸易进口", ImpExpType.GENERAL_TRADE_IMPORT);
		map.put("进料成品退换", ImpExpType.IMPORT_EXG_BACK);
		map.put("修理物品", ImpExpType.IMPORT_REPAIR_MATERIAL);

		map.put("成品出口", ImpExpType.DIRECT_EXPORT);
		map.put("直接出口", ImpExpType.DIRECT_EXPORT);

		map.put("转厂出口", ImpExpType.TRANSFER_FACTORY_EXPORT);
		map.put("退料出口", ImpExpType.BACK_MATERIEL_EXPORT);
		map.put("返工复出", ImpExpType.REWORK_EXPORT);
		map.put("边角料退港", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		map.put("边角料内销", ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
		map.put("一般贸易出口", ImpExpType.GENERAL_TRADE_EXPORT);
		map.put("修理物品复出", ImpExpType.EXPORT_MATERIAL_REBACK);
		map.put("进料成品退换复出", ImpExpType.EXPORT_EXG_REBACK);
		map.put("料件内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		return map;
	}

	/**
	 * 断判导入资料中清单编号是否重复
	 * 
	 * @param value
	 * @return
	 */
	public boolean checkBillListNoOverlap(String value) {

		List list = this.encDao.findApplyToCustomsBillList(value);
		for (int i = 0; i < list.size(); i++) {
			ApplyToCustomsBillList hd = (ApplyToCustomsBillList) list.get(i);
			if (value.equals(hd.getListNo())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 导入归并前的资料
	 */
	private void inputBeforeCommInfo(TempImportApplyToCustomsBillList data,
			AtcMergeBeforeComInfo atcMergeBeforeComInfo,
			ApplyToCustomsBillList applyToCustomsBillList, Map afterMap,
			InnerMergeData innerMergeData, boolean cbIsOverwrite, Map beforMap) {
		// 事业部Map-------------------归并前用到的map
		Map beforeProjectDeptMap = getObjectMapByTableName("ProjectDept");
		// 币别Map-------------------归并前用到的map
		Map beforeCurrencyMap = getObjectMapByTableName("Curr");
		// 产销国(地区)Map-------------------归并前用到的map
		Map beforeSalesCountryMap = getObjectMapByTableName("Country");
		// 征免方式Map-------------------归并前用到的map
		Map beforeLevyModelMap = getObjectMapByTableName("LevyMode");
		// 用途Map-------------------归并前用到的map
		Map beforeUsessMap = getObjectMapByTableName("Uses");
		Integer sTotalPrice = 3;
		Integer sAamount = 3;
		Integer sPrice = 3;
		if (innerMergeData.getMateriel() != null) {
			atcMergeBeforeComInfo.setMateriel(innerMergeData.getMateriel());
		}
		// 事业部
		atcMergeBeforeComInfo.setProjectDept((ProjectDept) beforeProjectDeptMap
				.get(data.getBeforeProjectDept()));
		// 币别
		atcMergeBeforeComInfo.setCurrency((Curr) beforeCurrencyMap.get(data
				.getBeforeCurrency()));
		// 产销国(地区)
		atcMergeBeforeComInfo.setSalesCountry((Country) beforeSalesCountryMap
				.get(data.getBeforeSalesCountry()));
		// 征免方式
		atcMergeBeforeComInfo.setLevyMode((LevyMode) beforeLevyModelMap
				.get(data.getBeforeLevyModel()));
		// 用途
		atcMergeBeforeComInfo.setUsesCode((Uses) beforeUsessMap.get(data
				.getBeforeUsess()));

		// 归并前－对应报关单商品号
		atcMergeBeforeComInfo.setCustomsNo(data.getBeforeCustomsNo());
		// 归并前－法定数量
		if (data.getBeforeLegalAmount() != null
				&& !"".equals(data.getBeforeLegalAmount())) {
			atcMergeBeforeComInfo.setLegalAmount(Double.valueOf(data
					.getBeforeLegalAmount()));
		} else {
			atcMergeBeforeComInfo.setLegalAmount(0.0);
		}
		// 归并前——第二法定数量
		if (data.getBeforeSecondLegalAmount() != null
				&& !"".equals(data.getBeforeSecondLegalAmount())) {
			atcMergeBeforeComInfo.setSecondLegalAmount(Double.valueOf(data
					.getBeforeSecondLegalAmount()));
		} else {
			atcMergeBeforeComInfo.setSecondLegalAmount(0.0);
		}
		// 归并前－海关版本号
		atcMergeBeforeComInfo.setVersion((data.getBeforeVersion() == null || ""
				.equals(data.getBeforeVersion().trim())) ? null : Integer
				.valueOf(data.getBeforeVersion().trim()));
		// 归并前－企业版本号
		atcMergeBeforeComInfo
				.setCmpVersion((data.getBeforeCmpVersion() == null || ""
						.equals(data.getBeforeCmpVersion().trim())) ? "" : data
						.getBeforeCmpVersion().trim());
		// 归并前－毛重
		if (data.getBeforeGrossWeight() != null
				&& !"".equals(data.getBeforeGrossWeight())) {
			atcMergeBeforeComInfo.setGrossWeight(Double.valueOf(data
					.getBeforeGrossWeight()));
		} else {
			atcMergeBeforeComInfo.setGrossWeight(0.0);
		}

		// 归并前－净重
		if (data.getBeforeNetWeight() != null
				&& !"".equals(data.getBeforeNetWeight())) {
			atcMergeBeforeComInfo.setNetWeight(Double.valueOf(data
					.getBeforeNetWeight()));
		} else {
			atcMergeBeforeComInfo.setNetWeight(0.0);
		}

		// 归并前－件数
		if (data.getBeforePeice() != null && !"".equals(data.getBeforePeice())) {
			atcMergeBeforeComInfo.setPiece(Integer.valueOf(data
					.getBeforePeice()));
		} else {
			atcMergeBeforeComInfo.setPiece(0);
		}
		// 归并前－箱号
		atcMergeBeforeComInfo.setBoxNo(data.getBeforeBoxNo());

		// 归并前－备注
		atcMergeBeforeComInfo.setMemos(data.getBeforeMemos());

		// 归并前－扩展备注
		atcMergeBeforeComInfo.setExtendMemo(data.getBeforeExtendMemo());
		// ======== 获取参数设置小数位
		String cTotalPrice = (String) encDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_TOTALPRICE);
		if (cTotalPrice == null || cTotalPrice.equals("")) {
			sTotalPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sTotalPrice = Integer.parseInt(cTotalPrice);
		}
		String cAamount = (String) encDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_AMOUNT);
		if (cAamount == null) {
			sAamount = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sAamount = Integer.parseInt(cAamount);
		}
		String cPrice = (String) encDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_PRICE);
		if (cPrice == null) {
			sPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sPrice = Integer.parseInt(cPrice);
		}
		// 归并前－申报数量
		if (data.getBeforeDeclaredAmount() != null
				&& !"".equals(data.getBeforeDeclaredAmount())) {
			atcMergeBeforeComInfo.setDeclaredAmount(CommonUtils
					.getDoubleByDigit(
							Double.valueOf(data.getBeforeDeclaredAmount()),
							sAamount));
		} else {
			atcMergeBeforeComInfo.setDeclaredAmount(0.0);
		}
		// 归并前－企业申报单价
		if (data.getBeforeDeclaredPrice() != null
				&& !"".equals(data.getBeforeDeclaredPrice())) {

			atcMergeBeforeComInfo.setDeclaredPrice(CommonUtils
					.getDoubleByDigit(
							Double.valueOf(data.getBeforeDeclaredPrice()),
							sPrice));
		} else {
			atcMergeBeforeComInfo.setDeclaredPrice(0.0);
		}
		// 归并前－加工费总价
		if (data.getBeforeWorkUsd() != null
				&& !"".equals(data.getBeforeWorkUsd())) {
			atcMergeBeforeComInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
					Double.valueOf(data.getBeforeWorkUsd()), sTotalPrice));
		} else {
			atcMergeBeforeComInfo.setWorkUsd(0.0);
		}

		// 计算归并前总价
		if (data.getBeforeTotalPrice() != null
				&& !"".equals(data.getBeforeTotalPrice().trim())
				&& !"0".equals(data.getBeforeTotalPrice())
				&& !"0.0".equals(data.getBeforeTotalPrice())) {
			atcMergeBeforeComInfo.setTotalPrice(CommonUtils.getDoubleByDigit(
					Double.valueOf(data.getBeforeTotalPrice()), sTotalPrice));
		} else {
			atcMergeBeforeComInfo
					.setTotalPrice(CommonUtils.getDoubleByDigit(
							(atcMergeBeforeComInfo.getDeclaredPrice() == null ? 0
									: atcMergeBeforeComInfo.getDeclaredPrice())
									* (atcMergeBeforeComInfo
											.getDeclaredAmount() == null ? 0
											: atcMergeBeforeComInfo
													.getDeclaredAmount()),
							sTotalPrice));
		}
		// ======取得参数设置中的清单设置
		ApplyCustomBillParameter a = this.sysCodeDao
				.findApplyCustomBillParameter();
		if (a != null) {
			if (atcMergeBeforeComInfo.getCurrency() == null) {
				atcMergeBeforeComInfo.setCurrency(a.getCurr());
			}
			if (atcMergeBeforeComInfo.getSalesCountry() == null) {
				atcMergeBeforeComInfo.setSalesCountry(a.getCountry());
			}
			if (atcMergeBeforeComInfo.getUsesCode() == null) {
				atcMergeBeforeComInfo.setUsesCode(a.getUses());
			}
			if (atcMergeBeforeComInfo.getLevyMode() == null) {
				atcMergeBeforeComInfo.setLevyMode(a.getLevyMode());
			}
		}
		encDao.saveOrUpdate(atcMergeBeforeComInfo);

		// ----------建立归并后的资料
		inputAfterComInfo(applyToCustomsBillList, atcMergeBeforeComInfo,
				innerMergeData, afterMap, data, cbIsOverwrite);
	}

	/**
	 * 导入归并后的资料
	 */
	private void inputAfterComInfo(
			ApplyToCustomsBillList applyToCustomsBillList,
			AtcMergeBeforeComInfo atcMergeBeforeComInfo,
			InnerMergeData innerMergeData, Map afterMap,
			TempImportApplyToCustomsBillList data, boolean cbIsOverwrite) {
		AtcMergeAfterComInfo atcMergeAfterComInfo = null;
		Integer sTotalPrice = 3;
		Integer sAamount = 3;
		Integer sPrice = 3;
		double legalUnitGene = 0.0;
		double legalUnit2Gene = 0.0;
		// 获取参数设置小数位
		String cTotalPrice = (String) encDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_TOTALPRICE);
		if (cTotalPrice == null || cTotalPrice.equals("")) {
			sTotalPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sTotalPrice = Integer.parseInt(cTotalPrice);
		}
		String cAamount = (String) encDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_AMOUNT);
		if (cAamount == null) {
			sAamount = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sAamount = Integer.parseInt(cAamount);
		}
		String cPrice = (String) encDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_PRICE);
		if (cPrice == null) {
			sPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sPrice = Integer.parseInt(cPrice);
		}
		// ------------------------------归并后
		String afterKey = data.getHeadBillListNo()
				+ "/"
				+ data.getHeadEmsNo()
				+ "/"
				+ innerMergeData.getHsAfterTenMemoNo()
				+ "/"
				+ data.getBeforeSalesCountry()
				+ "/"
				+ (data.getBeforeVersion() == null ? "" : data
						.getBeforeVersion());
		if (afterMap.get(afterKey) == null) {
			atcMergeAfterComInfo = new AtcMergeAfterComInfo();
			atcMergeAfterComInfo.setBillList(applyToCustomsBillList);// 表头
			atcMergeAfterComInfo.setCompany((Company) CommonUtils.getCompany());// 公司

			// 根据商品货号查找归并后序号
			EmsHeadH2kImg h2kImg = null;
			EmsHeadH2kExg h2kExg = null;
			if (applyToCustomsBillList.getMaterielProductFlag() == 2) {// 2为料件
				h2kImg = encDao.getEmsHeadImg(innerMergeData
						.getHsAfterTenMemoNo());
				if (h2kImg == null)
					return;
				atcMergeAfterComInfo.setCompany(h2kImg.getCompany());
				atcMergeAfterComInfo.setComplex(h2kImg.getComplex());
				atcMergeAfterComInfo.setComplexName(h2kImg.getName());
				atcMergeAfterComInfo.setComplexSpec(h2kImg.getSpec());
				atcMergeAfterComInfo.setUnit(h2kImg.getUnit());
				atcMergeAfterComInfo.setLegalUnit(h2kImg.getComplex()
						.getFirstUnit());
				atcMergeAfterComInfo.setSecondLegalUnit(h2kImg.getComplex()
						.getSecondUnit());
				atcMergeAfterComInfo.setEmsSerialNo(h2kImg.getSeqNum());// 归并序号
				legalUnitGene = h2kImg.getLegalUnitGene() == null ? 0.0
						: h2kImg.getLegalUnitGene();
				legalUnit2Gene = h2kImg.getLegalUnit2Gene() == null ? 0.0
						: h2kImg.getLegalUnit2Gene();
			} else {// 成品
				h2kExg = encDao.getEmsHeadExg(innerMergeData
						.getHsAfterTenMemoNo());
				if (h2kExg == null)
					return;
				atcMergeAfterComInfo.setComplex(h2kExg.getComplex());
				atcMergeAfterComInfo.setComplexName(h2kExg.getName());
				atcMergeAfterComInfo.setComplexSpec(h2kExg.getSpec());
				atcMergeAfterComInfo.setUnit(h2kExg.getUnit());
				atcMergeAfterComInfo.setVersion(String.valueOf(h2kExg
						.getVersion()));
				atcMergeAfterComInfo.setLegalUnit(h2kExg.getComplex()
						.getFirstUnit());
				atcMergeAfterComInfo.setSecondLegalUnit(h2kExg.getComplex()
						.getSecondUnit());
				atcMergeAfterComInfo.setEmsSerialNo(h2kExg.getSeqNum());// 归并序号
				// 归并前－版本号
				atcMergeAfterComInfo
						.setVersion(data.getBeforeVersion() == null ? null
								: data.getBeforeVersion());
				legalUnitGene = h2kExg.getLegalUnitGene() == null ? 0.0
						: h2kExg.getLegalUnitGene();
				legalUnit2Gene = h2kExg.getLegalUnit2Gene() == null ? 0.0
						: h2kExg.getLegalUnit2Gene();
			}

			// 计算归并后总价
			atcMergeAfterComInfo.setTotalPrice(CommonUtils.getDoubleByDigit(
					atcMergeBeforeComInfo.getTotalPrice(), sTotalPrice));

			// 计算归并后加工费
			atcMergeAfterComInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
					atcMergeBeforeComInfo.getWorkUsd(), sTotalPrice));

			// 计算归并后数量
			atcMergeAfterComInfo
					.setDeclaredAmount(CommonUtils.getDoubleByDigit(
							atcMergeBeforeComInfo.getDeclaredAmount(), sAamount));
			// 计算归并后单价
			atcMergeAfterComInfo.setPrice(CommonUtils.getDoubleByDigit(
					atcMergeBeforeComInfo.getDeclaredPrice(), sPrice));

			// 计算归并后的件数
			atcMergeAfterComInfo
					.setPiece(atcMergeBeforeComInfo.getPiece() == null ? 0
							: atcMergeBeforeComInfo.getPiece());
			// 计算归并后的箱号
			atcMergeAfterComInfo
					.setBoxNo(atcMergeBeforeComInfo.getBoxNo() == null ? ""
							: atcMergeBeforeComInfo.getBoxNo());

			// 计算归并后单重
			atcMergeAfterComInfo.setNetWeight((atcMergeBeforeComInfo
					.getNetWeight() == null ? 0 : atcMergeBeforeComInfo
					.getNetWeight()));
			// 计算归并后毛重
			atcMergeAfterComInfo.setGrossWeight((atcMergeBeforeComInfo
					.getGrossWeight() == null ? 0 : atcMergeBeforeComInfo
					.getGrossWeight()));
			// 原产国
			atcMergeAfterComInfo.setCountry(atcMergeBeforeComInfo
					.getSalesCountry());
			// 事业部
			atcMergeAfterComInfo.setProjectDept(atcMergeBeforeComInfo
					.getProjectDept());
			//
			// 计算第一法定数量、第二法定数量:
			// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
			// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
			// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
			// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
			// if (atcMergeBeforeComInfo.getAfterComInfo() != null) {
			// System.out.println("ptno"
			// + atcMergeBeforeComInfo.getMateriel().getPtNo()
			// + "==BeforeComInfo="
			// + atcMergeBeforeComInfo.getLegalAmount());
			if (atcMergeBeforeComInfo.getLegalAmount() == null
					|| atcMergeBeforeComInfo.getLegalAmount().doubleValue() == Double
							.valueOf(0)) {
				Unit firunit = atcMergeAfterComInfo.getLegalUnit();
				// System.out.println("==firunit=" + firunit);
				if (firunit != null) {
					String unitName = atcMergeAfterComInfo.getUnit() == null ? ""
							: atcMergeAfterComInfo.getUnit().getName();
					String firstUnitName = firunit.getName();
					// System.out.println("==unitName=" + unitName);
					if (unitName.equals(firstUnitName)) {// 条件1
						atcMergeBeforeComInfo.setLegalAmount(CommonUtils
								.getDoubleExceptNull(atcMergeBeforeComInfo
										.getDeclaredAmount()));
					} else if ("千克".equals(firstUnitName)) {// 条件2
						atcMergeBeforeComInfo.setLegalAmount(CommonUtils
								.getDoubleExceptNull(atcMergeBeforeComInfo
										.getNetWeight()));
					} else if (getUnitRateMap().get(
							unitName + "+" + firstUnitName) != null) {// 条件3
						Double unitRate = Double
								.parseDouble(getUnitRateMap().get(
										unitName + "+" + firstUnitName)
										.toString());
						atcMergeBeforeComInfo
								.setLegalAmount(CommonUtils.getDoubleByDigit(
										atcMergeAfterComInfo
												.getDeclaredAmount()
												* CommonUtils
														.getDoubleExceptNull(unitRate),
										sAamount));

					} else {// 条件4
						atcMergeBeforeComInfo
								.setLegalAmount(CommonUtils.getDoubleByDigit(
										CommonUtils
												.getDoubleExceptNull(atcMergeAfterComInfo
														.getDeclaredAmount()
														* legalUnitGene),
										sAamount));
					}
				}
				// }
				// 法定单位是千克的，法定数量可直接抓取净重
				if (atcMergeBeforeComInfo.getSecondLegalAmount() == null
						|| atcMergeBeforeComInfo.getSecondLegalAmount()
								.doubleValue() == Double.valueOf(0)) {
					Unit secunit = atcMergeAfterComInfo.getSecondLegalUnit();
					if (secunit != null) {
						String unitName = atcMergeAfterComInfo.getUnit() == null ? ""
								: atcMergeAfterComInfo.getUnit().getName();
						String secondUnitName = secunit.getName();
						if (unitName.equals(secondUnitName)) {
							atcMergeBeforeComInfo
									.setSecondLegalAmount(atcMergeBeforeComInfo
											.getDeclaredAmount() == null ? 0.0
											: atcMergeBeforeComInfo
													.getDeclaredAmount());
						} else if ("千克".equals(secondUnitName)) {
							atcMergeBeforeComInfo
									.setSecondLegalAmount(atcMergeBeforeComInfo
											.getNetWeight() == null ? 0.0
											: atcMergeBeforeComInfo
													.getNetWeight());
						} else if (getUnitRateMap().get(
								unitName + "+" + secondUnitName) != null) {
							Double unitRate = Double
									.parseDouble(getUnitRateMap().get(
											unitName + "+" + secondUnitName)
											.toString());
							atcMergeBeforeComInfo
									.setSecondLegalAmount(CommonUtils.getDoubleByDigit(
											atcMergeAfterComInfo
													.getDeclaredAmount()
													* CommonUtils
															.getDoubleExceptNull(unitRate),
											sAamount));
						} else {
							atcMergeBeforeComInfo
									.setSecondLegalAmount(CommonUtils.getDoubleByDigit(
											CommonUtils.getDoubleExceptNull(atcMergeAfterComInfo
													.getDeclaredAmount()
													* legalUnit2Gene), sAamount));
						}
					}
				}
			}
			// 计算归并后第一法定数量
			atcMergeAfterComInfo.setLegalAmount((atcMergeBeforeComInfo
					.getLegalAmount() == null ? 0 : atcMergeBeforeComInfo
					.getLegalAmount()));
			// 计算归并后第二法定数量
			atcMergeAfterComInfo.setSecondLegalAmount((atcMergeBeforeComInfo
					.getSecondLegalAmount() == null ? 0 : atcMergeBeforeComInfo
					.getSecondLegalAmount()));

			atcMergeAfterComInfo = (AtcMergeAfterComInfo) encDao
					.saveAndReturnObject(atcMergeAfterComInfo);
			atcMergeBeforeComInfo.setAfterComInfo(atcMergeAfterComInfo);// 设置对应的归并后
			// key==清单编号+帐册号+序号+产销国+版本
			afterMap.put(
					atcMergeAfterComInfo.getBillList().getListNo()
							+ "/"
							+ atcMergeAfterComInfo.getBillList()
									.getEmsHeadH2k()
							+ "/"
							+ atcMergeAfterComInfo.getEmsSerialNo()
							+ "/"
							+ data.getBeforeSalesCountry()
							+ "/"
							+ (data.getBeforeVersion() == null ? "" : data
									.getBeforeVersion()), atcMergeAfterComInfo);
			encDao.saveOrUpdate(atcMergeBeforeComInfo);
		} else {
			// -------当有合并时
			atcMergeAfterComInfo = (AtcMergeAfterComInfo) afterMap
					.get(afterKey);
			// 计算归并后数量
			if (atcMergeBeforeComInfo.getDeclaredAmount() != null) {
				atcMergeAfterComInfo
						.setDeclaredAmount(CommonUtils.getDoubleByDigit(
								atcMergeBeforeComInfo.getDeclaredAmount()
										+ atcMergeAfterComInfo
												.getDeclaredAmount(), sAamount));
			}
			// 计算归并后总价
			if (atcMergeBeforeComInfo.getTotalPrice() != null) {
				atcMergeAfterComInfo.setTotalPrice(CommonUtils
						.getDoubleByDigit(atcMergeBeforeComInfo.getTotalPrice()
								+ atcMergeAfterComInfo.getTotalPrice(),
								sTotalPrice));
			}
			if (atcMergeBeforeComInfo.getWorkUsd() != null) {
				// 计算归并后加工费
				atcMergeAfterComInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
						atcMergeBeforeComInfo.getWorkUsd()
								+ atcMergeAfterComInfo.getWorkUsd(),
						sTotalPrice));
			}
			// 计算归并后毛重
			if (atcMergeBeforeComInfo.getGrossWeight() != null) {
				atcMergeAfterComInfo.setGrossWeight(atcMergeBeforeComInfo
						.getGrossWeight()
						+ atcMergeAfterComInfo.getGrossWeight());
			}
			// 计算归并后单重
			if (atcMergeBeforeComInfo.getNetWeight() != null) {
				atcMergeAfterComInfo.setNetWeight(atcMergeBeforeComInfo
						.getNetWeight() + atcMergeAfterComInfo.getNetWeight());
			}
			// 件数
			if (atcMergeBeforeComInfo.getPiece() != null) {
				atcMergeAfterComInfo.setPiece(atcMergeBeforeComInfo.getPiece()
						+ atcMergeAfterComInfo.getPiece());
			}
			String boxNo = atcMergeAfterComInfo.getBoxNo();// 箱号
			String newBoxNo = "";
			if (boxNo != null && !"".equals(boxNo)) {
				newBoxNo = getNotExistBoxNo(boxNo,
						atcMergeBeforeComInfo.getBoxNo());
			} else {
				newBoxNo = atcMergeBeforeComInfo.getBoxNo();
			}
			atcMergeAfterComInfo.setBoxNo(newBoxNo);
			//
			// 计算第一法定数量、第二法定数量:
			// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
			// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
			// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
			// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
			// if (atcMergeBeforeComInfo.getAfterComInfo() != null) {
			if (atcMergeBeforeComInfo.getLegalAmount() == null
					|| atcMergeBeforeComInfo.getLegalAmount().doubleValue() == Double
							.valueOf(0)) {
				Unit firunit = atcMergeAfterComInfo.getLegalUnit();
				if (firunit != null) {
					String unitName = atcMergeAfterComInfo.getUnit() == null ? ""
							: atcMergeAfterComInfo.getUnit().getName();
					String firstUnitName = firunit.getName();
					if (unitName.equals(firstUnitName)) {// 条件1
						atcMergeBeforeComInfo.setLegalAmount(CommonUtils
								.getDoubleExceptNull(atcMergeBeforeComInfo
										.getDeclaredAmount()));
					} else if ("千克".equals(firstUnitName)) {// 条件2
						atcMergeBeforeComInfo.setLegalAmount(CommonUtils
								.getDoubleExceptNull(atcMergeBeforeComInfo
										.getNetWeight()));
					} else if (getUnitRateMap().get(
							unitName + "+" + firstUnitName) != null) {// 条件3
						Double unitRate = Double
								.parseDouble(getUnitRateMap().get(
										unitName + "+" + firstUnitName)
										.toString());
						atcMergeBeforeComInfo
								.setLegalAmount(CommonUtils.getDoubleByDigit(
										atcMergeAfterComInfo
												.getDeclaredAmount()
												* CommonUtils
														.getDoubleExceptNull(unitRate),
										sAamount));

					} else {// 条件4
						atcMergeBeforeComInfo
								.setLegalAmount(CommonUtils.getDoubleByDigit(
										CommonUtils
												.getDoubleExceptNull(atcMergeAfterComInfo
														.getDeclaredAmount()
														* legalUnitGene),
										sAamount));
					}
				}
			}
			// 法定单位是千克的，法定数量可直接抓取净重
			if (atcMergeBeforeComInfo.getSecondLegalAmount() == null
					|| atcMergeBeforeComInfo.getSecondLegalAmount()
							.doubleValue() == Double.valueOf(0)) {
				Unit secunit = atcMergeAfterComInfo.getSecondLegalUnit();
				if (secunit != null) {
					String unitName = atcMergeAfterComInfo.getUnit() == null ? ""
							: atcMergeAfterComInfo.getUnit().getName();
					String secondUnitName = secunit.getName();
					if (unitName.equals(secondUnitName)) {
						atcMergeBeforeComInfo
								.setSecondLegalAmount(atcMergeBeforeComInfo
										.getDeclaredAmount() == null ? 0.0
										: atcMergeBeforeComInfo
												.getDeclaredAmount());
					} else if ("千克".equals(secondUnitName)) {
						atcMergeBeforeComInfo
								.setSecondLegalAmount(atcMergeBeforeComInfo
										.getNetWeight() == null ? 0.0
										: atcMergeBeforeComInfo.getNetWeight());
					} else if (getUnitRateMap().get(
							unitName + "+" + secondUnitName) != null) {
						Double unitRate = Double.parseDouble(getUnitRateMap()
								.get(unitName + "+" + secondUnitName)
								.toString());
						atcMergeBeforeComInfo
								.setSecondLegalAmount(CommonUtils.getDoubleByDigit(
										atcMergeAfterComInfo
												.getDeclaredAmount()
												* CommonUtils
														.getDoubleExceptNull(unitRate),
										sAamount));
					} else {
						atcMergeBeforeComInfo
								.setSecondLegalAmount(CommonUtils.getDoubleByDigit(
										CommonUtils
												.getDoubleExceptNull(atcMergeAfterComInfo
														.getDeclaredAmount()
														* legalUnit2Gene),
										sAamount));
					}
				}
			}
			// }
			// 计算归并后第一法定数量
			if (atcMergeBeforeComInfo.getLegalAmount() != null) {
				atcMergeAfterComInfo.setLegalAmount(atcMergeBeforeComInfo
						.getLegalAmount()
						+ atcMergeAfterComInfo.getLegalAmount());
			}
			// 计算归并后第二法定数量
			if (atcMergeBeforeComInfo.getSecondLegalAmount() != null) {
				atcMergeAfterComInfo.setSecondLegalAmount(atcMergeBeforeComInfo
						.getSecondLegalAmount()
						+ atcMergeAfterComInfo.getSecondLegalAmount());
			}
			atcMergeAfterComInfo = (AtcMergeAfterComInfo) encDao
					.saveAndReturnObject(atcMergeAfterComInfo);
			atcMergeBeforeComInfo.setAfterComInfo(atcMergeAfterComInfo);// 设置对应的归并后商品信息
			encDao.saveOrUpdate(atcMergeBeforeComInfo);
		}
	}

	/**
	 * 获得计量单位的比例
	 * 
	 * @return
	 */
	public Map getUnitRateMap() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("克+千克", 0.001);
		map.put("个+千个", 0.001);
		map.put("支+千支", 0.001);
		map.put("块+千块", 0.001);
		map.put("米+千米", 0.001);
		map.put("千克+克", 1000.0);
		map.put("千个+个", 1000.0);
		map.put("千支+支", 1000.0);
		map.put("千块+块", 1000.0);
		map.put("千米+米", 1000.0);
		return map;
	}

	/**
	 * 创建归并后资料
	 * 
	 * @param map
	 * @param applyToCustomsBillList
	 * @param data
	 * @return
	 */
	private void createAtcMergeAfterComInfo(Map afterMap,
			ApplyToCustomsBillList applyToCustomsBillList,
			TempImportApplyToCustomsBillList data, boolean cbIsOverwrite,
			Map beforMap) {
		String[] property = null;// 查询数据时要用到的条件属性
		Object[] value = null;// 查询数据时要用到的条件属性值
		String tableName = "";// 要查询的实体类,用String形式表示
		List listData;// 用来放查到的数据list;

		// 根据导入归并前数据判断对应的资料是否在归并关系管理、内部归并、报关物料主档里都存在
		String para = "";
		if (applyToCustomsBillList.getMaterielProductFlag() == 2) {// 2为料件
			tableName = "EmsEdiMergerImgBefore";
			para = "emsEdiMergerImgAfter";

		} else {
			tableName = "EmsEdiMergerExgBefore";
			para = "emsEdiMergerExgAfter";
		}
		// 在归并关系管理、内部归并、报关物料主档里都存在的List
		listData = encDao.findBeforeMaterielPtNo(data.getBeforeMaterielPtNo(),
				tableName, null, para);

		// 如果没有对应的归并前商品货号就返回
		if (listData.isEmpty() || listData.size() < 0) {
			throw new RuntimeException("该料号[" + data.getBeforeMaterielPtNo()
					+ "]没有在归并关系中备案！");
		}
		// 查到的内部归并资料
		InnerMergeData innerMergeData = (InnerMergeData) listData.get(0);
		// －－－－－－－建立归并前资料
		String key = data.getHeadBillListNo() + "/" + data.getHeadEmsNo() + "/"
				+ innerMergeData.getMateriel().getPtNo() + "/"
				+ data.getBeforeSalesCountry();
		if (beforMap.get(key) == null) {
			AtcMergeBeforeComInfo atcMergeBeforeComInfo = new AtcMergeBeforeComInfo();
			atcMergeBeforeComInfo
					.setCompany((Company) CommonUtils.getCompany());// 公司
			// -------插入清单表体的内容
			inputBeforeCommInfo(data, atcMergeBeforeComInfo,
					applyToCustomsBillList, afterMap, innerMergeData,
					cbIsOverwrite, beforMap);
			beforMap.put(atcMergeBeforeComInfo.getAfterComInfo().getBillList()
					.getListNo()
					+ "/"
					+ atcMergeBeforeComInfo.getAfterComInfo().getBillList()
							.getEmsHeadH2k()
					+ "/"
					+ atcMergeBeforeComInfo.getMateriel().getPtNo()
					+ "/"
					+ atcMergeBeforeComInfo.getSalesCountry(),
					atcMergeBeforeComInfo);

			// 查找最大的商品序号
			Integer maxBeforeSerialNo = encDao
					.getMaxBeforeSerialNo(atcMergeBeforeComInfo
							.getAfterComInfo());
			atcMergeBeforeComInfo.setSerialNo(maxBeforeSerialNo);// 商品序号
			// 设置对应的归并后
			encDao.saveOrUpdate(atcMergeBeforeComInfo);
		} else {
			if (cbIsOverwrite) {
				AtcMergeBeforeComInfo atcMergeBeforeComInfo = (AtcMergeBeforeComInfo) beforMap
						.get(key);
				// -------插入清单表体的内容
				inputBeforeCommInfo(data, atcMergeBeforeComInfo,
						applyToCustomsBillList, afterMap, innerMergeData,
						cbIsOverwrite, beforMap);
				beforMap.put(atcMergeBeforeComInfo.getAfterComInfo()
						.getBillList().getListNo()
						+ "/"
						+ atcMergeBeforeComInfo.getAfterComInfo().getBillList()
								.getEmsHeadH2k()
						+ "/"
						+ atcMergeBeforeComInfo.getMateriel().getPtNo()
						+ "/"
						+ atcMergeBeforeComInfo.getSalesCountry(),
						atcMergeBeforeComInfo);
			}
		}
		// 更新单头商品数量
		applyToCustomsBillList.setMaterialNum((applyToCustomsBillList
				.getMaterialNum() == null ? 0 : applyToCustomsBillList
				.getMaterialNum()) + 1);
		applyToCustomsBillList = (ApplyToCustomsBillList) encDao
				.saveAndReturnObject(applyToCustomsBillList);// 表头
	}

	/**
	 * 查找清单
	 */
	public List getApplyReportTOCustomsReport(String impExpFlagCode,
			String billTypeCode, Date beginDate, Date endDate,
			boolean IsListUnitNo) {
		List arrayList = new ArrayList();
		List list = encDao.findAtcMergeBeforeComInfoBySomePara(impExpFlagCode,
				billTypeCode, beginDate, endDate);
		if (list.isEmpty())
			return arrayList;
		for (int i = 0; i < list.size(); i++) {
			AtcMergeBeforeComInfo atcMergeBeforeComInfo = (AtcMergeBeforeComInfo) list
					.get(i);
			TempRequestTOApplyTOCustomsReport tempRequestTOApplyTOCustomsReport = new TempRequestTOApplyTOCustomsReport();
			atcMergeBeforeComInfo.setExtendMemo(atcMergeBeforeComInfo
					.getMateriel().getFactoryName()
					+ "/"
					+ atcMergeBeforeComInfo.getMateriel().getFactorySpec());// 品名规格

			tempRequestTOApplyTOCustomsReport
					.setAtcMergeBeforeComInfo(atcMergeBeforeComInfo);

			CustomsDeclarationCommInfo customsCommInfo = null;

			if (IsListUnitNo) {
				// 按qp下载统一编号查询
				tempRequestTOApplyTOCustomsReport
						.setCustomsCommInfo(arryCommInfo(atcMergeBeforeComInfo));

			} else {
				// 按清单转报关单查询
				customsCommInfo = (CustomsDeclarationCommInfo) encDao
						.getCustomsFromMakeListToCustoms(atcMergeBeforeComInfo
								.getAfterComInfo().getId());
				if (customsCommInfo != null) {
					tempRequestTOApplyTOCustomsReport
							.setCustomsCommInfo(customsCommInfo);
				}
			}
			arrayList.add(tempRequestTOApplyTOCustomsReport);
		}
		return arrayList;
	}

	/**
	 * 判断箱号是否存在
	 * 
	 * @param allbillNo
	 *            所有的箱号
	 * @param newbillNo
	 *            新箱号
	 * @return 若存在为true 否则为false
	 */
	public String getNotExistBoxNo(String allbillNo, String newbillNo) {
		String newBoxNo = allbillNo;
		if (newbillNo == null || "".equals(newbillNo)) {
			return newBoxNo;
		}
		String[] yy = newbillNo.split(",");
		for (int i = 0; i < yy.length; i++) {
			if (allbillNo.contains(yy[i])) {
				continue;
			}
			newBoxNo += "," + yy[i];
		}
		return newBoxNo;
	}

	/**
	 * 根据条件获取大小清单资料
	 * 
	 * @param request
	 * @param impExpFlagCode
	 *            进出类型
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            生效开始日期
	 * @param endDate
	 *            生效结束日期
	 * @param IsListUnitNo
	 *            是否按统一编号查询，因为有些企业走QP，通过QP下载报关单
	 * @return
	 */
	public List getRequestTOApplyTOCustomsReport(String impExpFlagCode,
			String billTypeCode, ImpExpRequestBill impExpRequestBill,
			String bomNo, ScmCoc scmCoc, Date beginDate, Date endDate,
			boolean IsListUnitNo, String customDeclareCode, String commSerial) {
		List arrayList = new ArrayList();
		List list = encDao.findImpExpCommodityInfoBySomePara(impExpFlagCode,
				billTypeCode, impExpRequestBill, bomNo, scmCoc, beginDate,
				endDate);
		if (list.isEmpty())
			return arrayList;
		// 是否对比报关单号码,过滤报关单
		boolean isEquals = false;
		if (customDeclareCode != null && !"".equals(customDeclareCode)) {
			isEquals = true;
		}
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) list
					.get(i);
			// System.out.println("申请单序号："+impExpCommodityInfo.getSeqNum());
			AtcMergeBeforeComInfo atcMergeBeforeComInfo = encDao
					.getMergeBeforeComInfoFromMakeApplyToCustoms(
							impExpCommodityInfo.getId(), commSerial);

			if (atcMergeBeforeComInfo == null)
				continue;
			TempRequestTOApplyTOCustomsReport tempRequestTOApplyTOCustomsReport = new TempRequestTOApplyTOCustomsReport();

			impExpCommodityInfo.setExtendMemo(impExpCommodityInfo.getMateriel()
					.getFactoryName()
					+ "/"
					+ impExpCommodityInfo.getMateriel().getFactorySpec());// 品名规格

			tempRequestTOApplyTOCustomsReport
					.setImpExpCommodityInfo(impExpCommodityInfo);

			atcMergeBeforeComInfo.setExtendMemo(atcMergeBeforeComInfo
					.getMateriel().getFactoryName()
					+ "/"
					+ atcMergeBeforeComInfo.getMateriel().getFactorySpec());// 品名规格

			tempRequestTOApplyTOCustomsReport
					.setAtcMergeBeforeComInfo(atcMergeBeforeComInfo);

			CustomsDeclarationCommInfo customsCommInfo = null;

			if (IsListUnitNo) {
				// 按qp下载统一编号查询
				tempRequestTOApplyTOCustomsReport
						.setCustomsCommInfo(arryCommInfo(atcMergeBeforeComInfo));

			} else {
				// 按清单转报关单查询
				customsCommInfo = (CustomsDeclarationCommInfo) encDao
						.getCustomsFromMakeListToCustoms(atcMergeBeforeComInfo
								.getAfterComInfo().getId());
				if (customsCommInfo != null) {
					tempRequestTOApplyTOCustomsReport
							.setCustomsCommInfo(customsCommInfo);
				}
			}

			if (isEquals) {
				if (tempRequestTOApplyTOCustomsReport.getCustomsCommInfo() != null) {
					if (tempRequestTOApplyTOCustomsReport.getCustomsCommInfo()
							.getBaseCustomsDeclaration() != null) {
						if (customDeclareCode
								.equals(tempRequestTOApplyTOCustomsReport
										.getCustomsCommInfo()
										.getBaseCustomsDeclaration()
										.getCustomsDeclarationCode())) {
							arrayList.add(tempRequestTOApplyTOCustomsReport);
						}
					}
				}
			} else {
				arrayList.add(tempRequestTOApplyTOCustomsReport);
			}
		}
		return arrayList;
	}

	/**
	 * 排序单位
	 * 
	 * @param atcMergeBeforeComInfo
	 * @return
	 */
	private CustomsDeclarationCommInfo arryCommInfo(
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		CustomsDeclarationCommInfo comminfo = null;

		if (atcMergeBeforeComInfo != null) {
			if (atcMergeBeforeComInfo.getAfterComInfo().getBillList()
					.getListUniteNo() != null
					&& !atcMergeBeforeComInfo.getAfterComInfo().getBillList()
							.getListUniteNo().equals("")) {
				List listCustomcomminfo = encDao
						.getListUnitNoCustomsFromMakeListToCustoms(
								atcMergeBeforeComInfo.getAfterComInfo()
										.getBillList().getListUniteNo(),
								atcMergeBeforeComInfo.getAfterComInfo()
										.getEmsSerialNo());
				for (int j = 0; j < listCustomcomminfo.size(); j++) {
					CustomsDeclarationCommInfo arryCommInfo = (CustomsDeclarationCommInfo) listCustomcomminfo
							.get(j);
					comminfo = arryCommInfo;
				}
			}
		}
		return (CustomsDeclarationCommInfo) comminfo;
	}

	/**
	 * 检查清单
	 * 
	 * @param list
	 *            清单表头list
	 * @return
	 */
	public List applyToCustomsCheckup(List list) {
		List arrayList = new ArrayList();
		for (int j = 0; j < list.size(); j++) {
			int k = 0;
			ApplyToCustomsBillList head = (ApplyToCustomsBillList) list.get(j);
			StringBuffer headErr = new StringBuffer("");

			// 表头检查
			if (head.getImpExpType() == null)// 进出口类型
				headErr.append("进出口类型为空;");
			if (head.getEmsHeadH2k() == null || head.getEmsHeadH2k().equals(""))// 电子帐册号码
				headErr.append("电子帐册号码为空;");
			if (head.getTradeCode() == null || head.getTradeCode().equals(""))// 经营单位编码
				headErr.append("经营单位编码为空;");
			if (head.getTradeName() == null || head.getTradeName().equals(""))// 经营单位名称
				headErr.append("经营单位名称为空;");
			if (head.getListNo() == null || head.getTradeName().equals(""))// 清单编号
				headErr.append("清单编号为空;");
			if (head.getListUniteNo() == null
					|| head.getListUniteNo().equals(""))// 清单统一编号
				headErr.append("清单统一编号为空;");
			// if (head.getListDeclareDate() == null)// 清单申报日期
			// headErr.append("清单申报日期为空;");
			// if (head.getMaterialNum() == null)// 商品项数
			// headErr.append("商品项数为空;");
			if (head.getMaterielProductFlag() == null)// 料件成品标志
				headErr.append("料件成品标志为空;");
			if (head.getImpExpCIQ() == null)// 进出口岸
				headErr.append("进出口岸为空;");
			if (head.getDeclareCIQ() == null)// 申报地海关
				headErr.append("申报地海关为空;");
			if (head.getTransportMode() == null)// 运输方式
				headErr.append("运输方式为空;");
			if (head.getTradeMode() == null)// 监管方式
				headErr.append("监管方式为空;");
			if (head.getCreatedDate() == null)// 录入日期
				headErr.append("录入日期为空;");
			if (head.getCreatedUser() == null)// 录入员
				headErr.append("录入员为空;");
			if (head.getCreatedCompany() == null)// 录入单位
				headErr.append("录入单位为空;");
			if (head.getDeclarationCompany() == null)// 申报单位
				headErr.append("申报单位为空;");
			if (head.getMemos() == null || head.getMemos().equals(""))// 备注
				headErr.append("备注为空;");

			List list1 = encDao.findAtcMergeBeforeComInfoByListID(head);
			for (int i = 0; i < list1.size(); i++) {
				String errstr = "";
				AtcMergeBeforeComInfo info = (AtcMergeBeforeComInfo) list1
						.get(i);
				if (info.getCurrency() == null) {
					errstr += "币制不可为空;";
				}
				if (info.getDeclaredAmount() == null
						|| Double.valueOf(0).equals(info.getDeclaredAmount())) {
					errstr += "申报数量不可为空;";
				}
				Unit firstUnit = info.getAfterComInfo().getComplex()
						.getFirstUnit();
				Double legalAmount = info.getLegalAmount();
				if (firstUnit != null
						&& (legalAmount == null || Double.valueOf(0).equals(
								legalAmount))) {
					errstr += "法定数量不可为空;";
				}
				Double declaredPrice = info.getDeclaredPrice();
				if (declaredPrice == null
						|| Double.valueOf(0).equals(declaredPrice)) {
					errstr += "申报单价不可为空;";
				}
				Country salesCountry = info.getSalesCountry();
				if (salesCountry == null) {
					errstr += "产销国不可为空;";
				}
				Uses usesCode = info.getUsesCode();
				if (usesCode == null) {
					errstr += "用途代码不可为空;";
				}
				LevyMode levyMode = info.getLevyMode();
				if (levyMode == null) {
					errstr += "征免方式不可为空;";
				}
				if (!errstr.equals("")) {
					k++;
					StringBuffer bodyErr = new StringBuffer("");
					bodyErr.append("序号" + String.valueOf(info.getSerialNo())
							+ " :" + errstr + "\n");
					TempApplyToCustomsCheckup tempApplyToCustomsCheckup = new TempApplyToCustomsCheckup();
					tempApplyToCustomsCheckup.setListNo(head.getListNo());
					tempApplyToCustomsCheckup.setHeadErr(headErr.toString());
					tempApplyToCustomsCheckup.setBodyErr(bodyErr.toString());
					arrayList.add(tempApplyToCustomsCheckup);
				}
			}
			if (!headErr.toString().equals("") && list1.isEmpty()) {
				TempApplyToCustomsCheckup tempApplyToCustomsCheckup = new TempApplyToCustomsCheckup();
				tempApplyToCustomsCheckup.setListNo(head.getListNo());
				tempApplyToCustomsCheckup.setHeadErr(headErr.toString());
				tempApplyToCustomsCheckup.setBodyErr("没有归并前数据");
				arrayList.add(tempApplyToCustomsCheckup);
			} else if (!headErr.toString().equals("") && k == 0) {
				TempApplyToCustomsCheckup tempApplyToCustomsCheckup = new TempApplyToCustomsCheckup();
				tempApplyToCustomsCheckup.setListNo(head.getListNo());
				tempApplyToCustomsCheckup.setHeadErr(headErr.toString());
				tempApplyToCustomsCheckup.setBodyErr("");
				arrayList.add(tempApplyToCustomsCheckup);
			}
		}
		return arrayList;
	}

	/**
	 * 转抄申请单资料
	 * 
	 * @param impExpRequestBill
	 *            要转抄的申请单表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @param billNo
	 *            单据号
	 * @return 新的申请单表头
	 */
	public ImpExpRequestBill copyImpExpRequestBillAndCommodityInfo(
			ImpExpRequestBill impExpRequestBill, Boolean copyInfo, String billNo) {
		ImpExpRequestBill newImpExpRequestBill = new ImpExpRequestBill();
		newImpExpRequestBill.setCompany(impExpRequestBill.getCompany());
		newImpExpRequestBill.setBillType(impExpRequestBill.getBillType());// 单据类型
		newImpExpRequestBill.setBillNo(billNo);// 单据号
		newImpExpRequestBill.setItemCount(0);// 项目总数
		newImpExpRequestBill.setMaterielProductFlag(impExpRequestBill
				.getMaterielProductFlag());// 料件成品标识
		newImpExpRequestBill.setConveyance(impExpRequestBill.getConveyance());// 运输工具
		newImpExpRequestBill.setImputDate(new Date());// 录入时间
		newImpExpRequestBill.setBeginAvailability(new Date());// 生效时间
		newImpExpRequestBill.setWareSet(impExpRequestBill.getWareSet());// 仓库对象类
		newImpExpRequestBill.setScmCoc(impExpRequestBill.getScmCoc());// 客户供应商
		newImpExpRequestBill.setContainerCode(impExpRequestBill
				.getContainerCode());// 集装箱号码
		newImpExpRequestBill.setIePort(impExpRequestBill.getIePort());// 进出口岸
		newImpExpRequestBill.setCatNo(impExpRequestBill.getCatNo());// 车牌号码
		List arrayList = new ArrayList();

		if (copyInfo) {
			newImpExpRequestBill.setItemCount(impExpRequestBill.getItemCount());// 项目总数
			List list = encDao.findImpExpCommodityInfo(impExpRequestBill
					.getId());
			for (int i = 0; i < list.size(); i++) {
				ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) list
						.get(i);
				ImpExpCommodityInfo newImpExpCommodityInfo = (ImpExpCommodityInfo) impExpCommodityInfo
						.clone();
				newImpExpCommodityInfo.setId(null);
				newImpExpCommodityInfo
						.setImpExpRequestBill(newImpExpRequestBill);
				newImpExpCommodityInfo.setIsTransferCustomsBill(false);// 已转报关清单或已转报关单

				arrayList.add(newImpExpCommodityInfo);
			}
		}
		encDao.saveImpExpRequestBill(newImpExpRequestBill);
		if (!arrayList.isEmpty())
			encDao.saveImpExpCommodityInfo(arrayList);

		return newImpExpRequestBill;

	}

	/**
	 * 转抄清单资料
	 * 
	 * @param applyToCustomsBillList
	 *            要转抄的清单表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @return 新的清单表头
	 */
	public ApplyToCustomsBillList copyApplyToCustomsBillListAndCommInfo(
			ApplyToCustomsBillList applyToCustomsBillList, Boolean copyInfo) {
		ApplyToCustomsBillList newApplyToCustomsBillList = (ApplyToCustomsBillList) applyToCustomsBillList
				.clone();

		newApplyToCustomsBillList.setId(null);
		newApplyToCustomsBillList.setListState(ApplyToCustomsBillList.DRAFT);// 清单状态_为草稿型
		newApplyToCustomsBillList.setEffectstate(false);// 已转报关单
		newApplyToCustomsBillList.setListNo(encDao
				.getApplyToCustomsBillListMaxNo());// 清单号码
		newApplyToCustomsBillList.setListUniteNo(null);// 统一编号

		newApplyToCustomsBillList.setListQpBillNo(null);// 清单统一编号

		newApplyToCustomsBillList.setListDeclareDate(null);// 清单申报日期
		newApplyToCustomsBillList.setCustomsDeclarationDate(null);// 报关单申报日期
		newApplyToCustomsBillList.setCustomsDeclarationCode(null);// 报关单流水编号
		newApplyToCustomsBillList.setCustomsEnvelopBillNo(null);// 关封号
		newApplyToCustomsBillList.setCreatedDate(new Date());// 录入日期

		List afterArrayList = new ArrayList();
		List beforeArrayList = new ArrayList();
		if (copyInfo) {
			List afterList = encDao
					.findAtcMergeAfterComInfoByListID(applyToCustomsBillList);// 归并后资料
			for (int i = 0; i < afterList.size(); i++) {// 归并后资料
				AtcMergeAfterComInfo atcMergeAfterComInfo = (AtcMergeAfterComInfo) afterList
						.get(i);
				AtcMergeAfterComInfo newAtcMergeAfterComInfo = (AtcMergeAfterComInfo) atcMergeAfterComInfo
						.clone();
				newAtcMergeAfterComInfo.setBillList(newApplyToCustomsBillList);
				newAtcMergeAfterComInfo.setId(null);
				newAtcMergeAfterComInfo.setIsTransferCustomsBill(false);// 是否已转报关单

				afterArrayList.add(newAtcMergeAfterComInfo);

				List beforeList = encDao
						.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfo);// 归并前资料
				for (int j = 0; j < beforeList.size(); j++) {// 归并前资料
					AtcMergeBeforeComInfo atcMergeBeforeComInfo = (AtcMergeBeforeComInfo) beforeList
							.get(j);
					AtcMergeBeforeComInfo newAtcMergeBeforeComInfo = (AtcMergeBeforeComInfo) atcMergeBeforeComInfo
							.clone();
					newAtcMergeBeforeComInfo
							.setAfterComInfo(newAtcMergeAfterComInfo);
					newAtcMergeBeforeComInfo.setId(null);
					newAtcMergeBeforeComInfo.setCustomsNo(null);// 报关单号

					beforeArrayList.add(newAtcMergeBeforeComInfo);
				}
			}

		}

		encDao.saveApplyToCustomsBillList(newApplyToCustomsBillList);

		if (!afterArrayList.isEmpty()) {
			for (int i = 0; i < afterArrayList.size(); i++) {// 归并后资料
				AtcMergeAfterComInfo atcMergeAfterComInfo = (AtcMergeAfterComInfo) afterArrayList
						.get(i);
				encDao.saveAtcMergeAfterComInfo(atcMergeAfterComInfo);
			}
		}

		if (!beforeArrayList.isEmpty()) {
			for (int i = 0; i < beforeArrayList.size(); i++) {// 归并前资料
				AtcMergeBeforeComInfo atcMergeBeforeComInfo = (AtcMergeBeforeComInfo) beforeArrayList
						.get(i);
				encDao.saveAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
			}
		}

		return newApplyToCustomsBillList;
	}

	/**
	 * 进出口包装统计
	 * 
	 * @param wrap
	 *            包装种类
	 * @param impExpFlag
	 *            进出口标志
	 * @param effective
	 *            是否生效
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findWrapStat(Wrap wrap, Integer impExpFlag, Boolean effective,
			Date beginDate, Date endDate) {
		List list = encDao.findWrapStat(wrap, impExpFlag, effective, beginDate,
				endDate);
		List<WrapStat> lsResult = new ArrayList<WrapStat>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			WrapStat wrapStat = new WrapStat();
			if (objs[0] != null) {
				wrapStat.setWrapName(objs[0].toString());
			}
			if (objs[1] != null) {
				wrapStat.setWrapWeight(Double.parseDouble(objs[1].toString()));
			}
			wrapStat.setWrapUnit("千克");
			lsResult.add(wrapStat);
		}
		return lsResult;

	}

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBatchUpdateComplex(String emsType, Boolean isMaterial) {
		List list = new ArrayList();
		List result = new ArrayList();
		if ("0".equals(emsType)) {// 电子帐册
			list = encDao.findEdiH2k(isMaterial);
		} else if ("1".equals(emsType)) {// 归并关系
			list = encDao.findEdiMergerAfter(isMaterial);
		} else if ("2".equals(emsType)) {// 内部归并
			list = encDao.findInnerMergeDataComplex(isMaterial);
		}
		TempCustomsDeclarationCommInfo commInfo = new TempCustomsDeclarationCommInfo();
		System.out.println("==liset==" + list.size());
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			commInfo = new TempCustomsDeclarationCommInfo();
			if (objs[0] != null) {
				commInfo.setEmsSerialNo(objs[0].toString());
			}
			if (objs[1] != null) {
				commInfo.setComplex((Complex) objs[1]);
			}
			if (objs[3] != null) {
				commInfo.setLegalUnit((Unit) objs[3]);
			}
			if (objs[4] != null) {
				commInfo.setLegalUnit2((Unit) objs[4]);
			}
			if (objs[5] != null) {
				commInfo.setName(objs[5].toString());
			}
			result.add(commInfo);

		}
		System.out.println("==result==" + result.size());
		return result;
	}

	/**
	 * 更新电子帐册的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllEmsComplex(Boolean isMaterial, Complex complex,
			Integer seqNum, boolean isSendData) {
		encDao.updateAllEmsComplex(isMaterial, complex, seqNum, isSendData);
	}

	/**
	 * 更新所有归并关系的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllEdiMergerAfterComplex(Boolean isMaterial,
			Complex complex, Complex old, Integer seqNum, boolean isSendData) {
		encDao.updateAllEdiMergerAfterComplex(isMaterial, complex, old, seqNum,
				isSendData);
	}

	/**
	 * 更新所有内部归并的商品编码
	 * 
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllInnerMergeDataComplex(Boolean isMaterial,
			Complex complex, Integer seqNum) {
		encDao.updateAllInnerMergeDataComplex(isMaterial, complex, seqNum);
	}

	/**
	 * 根据报关单物料信息获取进出口申请单表体资料
	 * 
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType
	 *            单据类型
	 * @return
	 */
	public List findTempImpExpCommodityInfoBycustomsInfo(
			CustomsDeclarationCommInfo customsDeclarationCommInfo,
			Integer billType) {
		List<ImpExpCommodityInfo> impExpCommodityInfos = new ArrayList<ImpExpCommodityInfo>();
		// 根据报关单商品信息获得申请单商品信息中的归并后商品信息(一对多)
		List<AtcMergeAfterComInfo> atcMergeAfterComInfos = encDao
				.findMakeListToCustomsBycustomsInfo(customsDeclarationCommInfo);
		for (int m = 0; m < atcMergeAfterComInfos.size(); m++) {
			// 获得归并前商品信息（一对多）
			List<AtcMergeBeforeComInfo> atcMergeBeforeComInfos = encDao
					.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfos
							.get(m));
			for (int i = 0; i < atcMergeBeforeComInfos.size(); i++) {
				AtcMergeBeforeComInfo atcMergeBeforeComInfo = (AtcMergeBeforeComInfo) atcMergeBeforeComInfos
						.get(i);
				// 通过归并前的商品信息获得申请单的商品信息（一对多）
				List paraList = encDao
						.findImpExpCommodityInfoByAtcMergeBeforeComInfo(
								atcMergeBeforeComInfo, billType);
				for (int j = 0; j < paraList.size(); j++) {
					impExpCommodityInfos.add((ImpExpCommodityInfo) paraList
							.get(j));
				}
			}
		}
		return impExpCommodityInfos;
	}
	
	
	/**
	 * 根据报关单物料信息获取进出口申请单表体资料
	 * 
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType
	 *            单据类型
	 * @return
	 */
	public List findTempImpExpCommodityInfoBycustomsInfo(
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo,
			Integer billType) {
		List<ImpExpCommodityInfo> impExpCommodityInfos = encDao.findMakeListToCustomsBycustomsInfo(bcsCustomsDeclarationCommInfo);
		return impExpCommodityInfos;
	}
	/**
	 * 根据报关单物料信息获得归并前商品信息
	 * 
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType
	 *            单据类型
	 * @return
	 */
	public List findAtcMergeBeforeComInfoByCustomsDeclarationCommInfo(
			CustomsDeclarationCommInfo customsDeclarationCommInfo,
			Integer billType) {
		List<AtcMergeBeforeComInfo> atcMergeBeforeComInfoList = new ArrayList<AtcMergeBeforeComInfo>();
		// 根据报关单商品信息获得报关清单归并后商品信息(一对多)
		List<AtcMergeAfterComInfo> atcMergeAfterComInfos = encDao
				.findMakeListToCustomsBycustomsInfo(customsDeclarationCommInfo);
		for (int m = 0; m < atcMergeAfterComInfos.size(); m++) {
			// 获得归并前商品信息（一对多）
			List<AtcMergeBeforeComInfo> atcMergeBeforeComInfos = encDao
					.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfos
							.get(m));
			if (atcMergeBeforeComInfos != null
					&& atcMergeBeforeComInfos.size() > 0) {
				atcMergeBeforeComInfoList.addAll(atcMergeBeforeComInfos);
			}
		}
		return atcMergeBeforeComInfoList;
	}
	
	/**
	 * 查询料件耗用明细
	 * @param request
	 * @param beginDate 报关单开始日期
	 * @param endDate 报关单结束日期
	 * @param projectDept 事业部
	 * @param imgSeqNums 料件序号
	 * @param isTotal 是否统计
	 * @return
	 */
	public List findImgConsumption(Date beginDate,Date endDate,ProjectDept projectDept,Integer[] imgSeqNums,Integer impExpType,Boolean isTotal){
		List<BillTemp> returnList = new ArrayList<BillTemp>();
		List list = new ArrayList();		
		if(isTotal){
			list = encDao.findTotalImgConsumption(beginDate,endDate,projectDept,imgSeqNums,impExpType);
		}else{
			list = encDao.findImgConsumption(beginDate,endDate,projectDept,imgSeqNums,impExpType);
		}
		
		String reportDecimalLength = this.encDao
				.getBpara(BcusParameter.ReportDecimalLength);
		int len = 2;
		if (reportDecimalLength != null && !"".equals(reportDecimalLength.trim())) {
			len = Double.valueOf(reportDecimalLength).intValue();
		}
		
		for (int i = 0; i < list.size(); i++) {
			Object[] objects = (Object[])list.get(i);
			BillTemp bill = new BillTemp();
			bill.setBill5(objects[0]==null?"":objects[0].toString());//料件序号
			bill.setBill4(objects[1]==null?"":objects[1].toString());//料件名称
			bill.setBill8(objects[2]==null?"":objects[2].toString());//成品序号
			bill.setBill7(objects[3]==null?"":objects[3].toString());//成品名称
			bill.setBill11(objects[4]==null?"":objects[4].toString());//成品规格
			bill.setBill6(objects[5]==null?"":objects[5].toString());//版本号
			bill.setBillSum1(objects[6]==null?0.0:Double.parseDouble(objects[6].toString()));//单耗
			bill.setBillSum2(objects[7]==null?0.0:Double.parseDouble(objects[7].toString()));//损耗
			bill.setBillSum4(objects[8]==null?0.0:Double.parseDouble(objects[8].toString()));//出口量
			
			double d = 0.0;//出口耗用量=出口量*(单耗/(1-损耗))
			if(bill.getBillSum4()!=null&&bill.getBillSum1()!=null&&bill.getBillSum2()!=null){
				d = bill.getBillSum4()*(bill.getBillSum1()/(1-bill.getBillSum2()/100));
			}
			
			if(!isTotal){//不统计时
				bill.setBillSum3(CommonUtils.getDoubleByDigit(d, len));//出口耗用量
				bill.setBill1(objects[9]==null?"":objects[9].toString());//报关单流水号
				bill.setBill10(objects[10]==null?"":objects[10].toString());//报关单号
				bill.setBill3(objects[11]==null?"":objects[11].toString());//进出口类型
				
				//当进出口类型为退厂返工时，值以负数形式显示
				if (Integer.parseInt(objects[11].toString()) == ImpExpType.BACK_FACTORY_REWORK) {
					bill.setBillSum4(bill.getBillSum4()==null?0:-bill.getBillSum4());//出口量
					bill.setBillSum3(bill.getBillSum3()==null?0:-bill.getBillSum3());//出口耗用量
				}
				
				if (objects[12]!=null&&!"".equals(objects[12].toString())) {
					SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd");
					try {
						//进出口日期
						bill.setBill9(formt.format(formt.parse(objects[12].toString())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				bill.setBill2(objects[13]==null?"":objects[13].toString());//贸易方式
			}else{
				bill.setBillSum19(CommonUtils.getDoubleByDigit(d, len));//出口耗用量
			}
			returnList.add(bill);
		}
		return returnList;
	}
}
