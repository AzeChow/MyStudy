package com.bestway.bcus.cas.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasCheckDao;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.CheckBalanceConvertMateriel;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.ImportExportInfo;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.TempBomRelation;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CarryBalance;
import com.bestway.bcus.cas.invoice.entity.CasCondition;
import com.bestway.bcus.cas.invoice.entity.CasConvert;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.cas.invoice.entity.CurrentBillDetailBom;
import com.bestway.bcus.cas.invoice.entity.LeftoverMaterielStatInfo;
import com.bestway.bcus.cas.invoice.entity.ProductBOMtoMateriel;
import com.bestway.bcus.cas.invoice.entity.SemiProductInfo;
import com.bestway.bcus.cas.invoice.entity.TempBomBillDetail;
import com.bestway.bcus.cas.invoice.entity.TempCheckBalance;
import com.bestway.bcus.cas.invoice.entity.TempCurrentBillDetailPanDian;
import com.bestway.bcus.cas.invoice.entity.TempCustomsDeclarCommInfo;
import com.bestway.bcus.cas.invoice.entity.TempThatDayBalance;
import com.bestway.bcus.cas.invoice.entity.TempTransferCustomsDeclarationCommInfo;
import com.bestway.bcus.cas.invoice.entity.TempTransferRecvSendDetailInfo;
import com.bestway.bcus.cas.invoice.entity.ToBomTempThatDayBalance;
import com.bestway.bcus.cas.invoice.entity.WorkOrderList;
import com.bestway.bcus.checkcancel.entity.TempDD;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.TempComplex;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CheckMyselfUntil;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SBillType;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * 海关帐 自我核查logic
 * 
 * @author chensir
 * 
 */
@SuppressWarnings({"unchecked","unused"})
public class CasCheckLogic {

	/**
	 * 海关帐Dao
	 */
	private CasDao casDao = null;

	/**
	 * 自我核查dao
	 */
	private CasCheckDao casCheckDao;

	private Hashtable hs = new Hashtable();

	private Hashtable timehs = new Hashtable();

	private Hashtable tranThs = new Hashtable();
	
	/** 为了每个公司只能有一个方法在同步运行,并且.. key = company id */
	private Map<String, Boolean>	runMap		= new Hashtable<String, Boolean>();
	
	/** 过滤条件 */
	private String[]				filterStrs	= new String[] { "废", "边角料" };
	
	/** 参数Map类,用于计算边角料时的  数据缓存 */
	static class ParameterMaps {

		/** key :报关名称+"/"+规格+"/"+单位名称  value:对应关系list,按料号排*/
		Map<String, List<FactoryAndFactualCustomsRalation>>	statCusNameRelationMtListMap	= new HashMap<String, List<FactoryAndFactualCustomsRalation>>();
		/** 出口成品损耗量映射 key:key :报关名称+"/"+规格+"/"+单位名称  value:（直接出口+结转出口）报关单折损耗*/
		Map<String, Double>							customsProductMapWaste			= new HashMap<String, Double>();
		/**已打税 key：报关名称+"/"+规格+"/"+单位名称  value:边角料已打税 报关单数量*/
		Map<String, Double>							alReadyTax			= new HashMap<String, Double>();

	}
	
	/**
	 * 工厂通用代码（方法）
	 */
	private MaterialManageDao materialManageDao = null;

	public CasDao getCasDao() {
		return casDao;
	}

	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	public CasCheckDao getCasCheckDao() {
		return casCheckDao;
	}

	public void setCasCheckDao(CasCheckDao casCheckDao) {
		this.casCheckDao = casCheckDao;
	}

	/**
	 * 获取查询结果料件集,用于对统计结果过滤
	 * 
	 * @return
	 */
	public Map getResultMaterielConvert(CasConvert casConvert) {
		// 判断料件条件是否都为空，空的话则直接跳出方法
		if (!casConvert.isCserch() && !casConvert.isFserch()) {
			System.out.println("条件为空");
			return null;
		}
		Map map = new HashMap();

		// 工厂级别条件查询
		List<Materiel> ms = materialManageDao
				.getResultMaterielFactory(casConvert);
		// 工厂map
		Map<String, String> fmap = new HashMap();
		for (Materiel m : ms) {
			fmap.put(getMaterielKey(m, null), "");
		}

		// 报关级别查询
		ms = materialManageDao.getResultMaterielComplex(casConvert);
		// 报关map
		Map<String, String> cmap = new HashMap();
		System.out.println("cmap.size()=" + cmap.size());
		for (Materiel m : ms) {
			cmap.put(getMaterielKey(m, null), "");
		}
		if (!casConvert.isCserch() && casConvert.isFserch()) {// 只存在工厂查询
			System.out.println("进入只存在工厂查询");
			return fmap;
		}
		if (casConvert.isCserch() && !casConvert.isFserch()) {// 只存在报关查询
			System.out.println("进入只存在报关查询");
			return cmap;
		}
		// 存在工厂，报关查询，求交集
		for (String key : fmap.keySet()) {
			if (cmap.containsKey(key)) {
				map.put(key, "");
			}
		}

		for (String key : cmap.keySet()) {
			if (fmap.containsKey(key)) {
				map.put(key, "");
			}
		}

		return map;
	}

	/**
	 * 获取查询结果料件集,用于对统计结果过滤
	 * 
	 * @return
	 * @author chensir
	 */
	public Map getResultMateriel(CasCondition casCondition) {
		if (!casCondition.isCserch() && !casCondition.isFserch()) {// 不存在任何查询条件，返回null
			return null;
		}
		Map map = new HashMap();
		// 工厂级别查询筛选
		List<Materiel> ms = materialManageDao.getResultMaterielF(casCondition);
		// 工厂map
		Map<String, String> fmap = new HashMap();
		for (Materiel m : ms) {
			fmap.put(getMaterielKey(m, null), "");
		}

		// 报关级别查询筛选
		ms = materialManageDao.getResultMaterielC(casCondition);
		// 报关map
		Map<String, String> cmap = new HashMap();
		for (Materiel m : ms) {
			cmap.put(getMaterielKey(m, null), "");
		}
		if (!casCondition.isCserch() && casCondition.isFserch()) {// 只存在工厂查询
			return fmap;
		}
		if (casCondition.isCserch() && !casCondition.isFserch()) {// 只存在报关查询
			return cmap;
		}

		// 存在工厂，报关查询，求交集
		for (String key : fmap.keySet()) {
			if (cmap.containsKey(key)) {
				map.put(key, "");
			}
		}
		for (String key : cmap.keySet()) {
			if (fmap.containsKey(key)) {
				map.put(key, "");
			}
		}

		return map;
	}

	/**
	 * 获取料件key
	 * 
	 * @param m
	 * @return
	 */
	public String getMaterielKey(Materiel m, BillDetail bill) {
		if (m != null) {
			return m.getPtNo() + "/" + m.getFactoryName() + "/"
					+ m.getFactorySpec();
		}
		if (bill != null) {
			return bill.getPtPart() + "/" + bill.getPtName() + "/"
					+ bill.getPtSpec();
		}
		return "";
	}

	/**
	 * 获取成品key
	 * 
	 * @author 石小凯
	 * @param m
	 * @return
	 */
	public String getXiaokMaterielKey(Materiel m, BillDetail bill) {
		if (m != null) {
			return m.getPtNo() + m.getFactoryName() + m.getFactorySpec();
		}
		if (bill != null) {
			return bill.getVersion().toString().trim() + bill.getPtName()
					+ bill.getPtSpec();
		}
		return "";
	}

	/**
	 * 生成查询条件,过滤成品查询条件
	 * 
	 * @param casCondition
	 * @return
	 */
	public List<Condition> getConditionsConvert(CasConvert casConvert,
			boolean isMaterie) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));

		if (isMaterie) {
			// 工厂料号不等于空,结束料号为空时
			if (!casConvert.getStartPtPart().equals("")
					&& casConvert.getEndPtPart().equals("")) {
				conditions.add(new Condition("and", null, "ptPart", "=",
						casConvert.getStartPtPart(), null));
			} else // 工厂料号不等于空,结束料号不为空时
			if (!casConvert.getStartPtPart().equals("")
					&& !casConvert.getEndPtPart().equals("")) {
				conditions.add(new Condition("and", "(", "ptPart", ">=",
						casConvert.getStartPtPart(), null));
				conditions.add(new Condition("and", null, "ptPart", "<=",
						casConvert.getEndPtPart(), ")"));
			}
			// 制单号
			if (!casConvert.getProductNo().equals("")) {
				conditions.add(new Condition("and", null, "productNo", "=",
						casConvert.getProductNo(), null));
			}
			// 报关名称
			if (!casConvert.getHsName().equals("")) {
				conditions.add(new Condition("and", null, "hsName", "=",
						casConvert.getHsName(), null));
			}
			// 工厂名称
			if (!casConvert.getPtName().equals("")) {
				conditions.add(new Condition("and", null, "ptName", "=",
						casConvert.getPtName(), null));
			}
			// 成品报关编号
			if (!casConvert.getComplexCode().equals("")) {
				conditions.add(new Condition("and", null, "complex.code", "=",
						casConvert.getComplexCode(), null));
			}
			// 开始日期
			if (casConvert.getStartDate() != null) {
				Date date = casConvert.getStartDate();
				conditions.add(new Condition("and", null,
						"billMaster.validDate", ">=", CommonUtils
								.getBeginDate(date), null));
			}
			// 结束日期
			if (casConvert.getEndDate() != null) {
				Date date = casConvert.getEndDate();
				conditions.add(new Condition("and", null,
						"billMaster.validDate", "<=", CommonUtils
								.getEndDate(date), null));
			}
		}
		return conditions;
	}

	public static Date getBeginDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		// calendar.set(Calendar.MINUTE, 0);
		// calendar.set(Calendar.SECOND, 0);
		// calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, -1);
		// return calendar.getTime();
		return getEndDate(calendar.getTime());
	}

	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		// calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 残次品折料新方法
	 * 
	 * @param casCondition
	 * @param billDetail
	 * @return
	 * @author xiaokai
	 */
	public List getCurrentBadProductBOM(CasConvert casConvert, String billDetail) {
		System.out.println("===========新方法里面=============");

		// 通过料件条件取出料件结果集
		Map<String, String> rsMap = getResultMaterielConvert(casConvert);

		// 用于缓存成品／半成品的BOM
		Map<String, List<MaterialBomDetail>> bomMap = new HashMap();

		String key = "";
		String leftOuter="";
		// 成品查询结果
		List<BillDetail> ms = null;
		// 返回折算最终结果
		List<CurrentBillDetailBom> billBoms = new ArrayList();
		// 组织成品查询条件
		List<Condition> conditions = getConditionsConvert(casConvert, true);

		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet left join fetch a.complex ";
		// 根据条件查找成品
		ms = casDao.commonSearch("", billDetailTableName, conditions, "", leftOuter);
		System.out.println("ms.size=" + ms.size());
		List<MaterialBomDetail> boms = null;
		Double totalDosage = null;
		Double ptTotal = null;
		List<MaterialBomDetail> bomList = null;
		// List<MaterialBomDetail> list=null;
		// 用于视别当前BOM是否要缓存
		boolean isSave = false;
		for (BillDetail bill : ms) {
			// 遍历成品折BOM
			key = getMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();
			}
			Double ptAmount = bill.getPtAmount();
			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {
					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());
					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());
					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());
					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());
					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());
					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());
					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());
					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());
					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());
					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());

					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();

					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusName());
						currentBillDetailBom.setMptDeSpec(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusSpec());
						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null != f.getUnitConvert()) {
							currentBillDetailBom.setPtTotal(f.getUnitConvert()
									* totalDosage);
						} else {
							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add(currentBillDetailBom);
				}
			}
		}
		return billBoms;
	}

	/**
	 * 外发加工折料新方法
	 * 
	 * @param casCondition
	 * @param billDetail
	 * @return
	 * @author xiaokai
	 */
	public List getCurrentOutSource(CasConvert casConvert, String billDetail) {
		System.out.println("===========新方法里面=============");

		// 通过料件条件取出料件结果集
		Map<String, String> rsMap = getResultMaterielConvert(casConvert);

		// 用于缓存成品／半成品的BOM
		Map<String, List<MaterialBomDetail>> bomMap = new HashMap<String, List<MaterialBomDetail>>();

		String key = "";
		String leftOuter="";
		// 成品查询结果
		List<BillDetail> ms = null;
		// 返回折算最终结果
		List<CurrentBillDetailBom> billBoms = new ArrayList<CurrentBillDetailBom>();
		// 组织成品查询条件
		List<Condition> conditions = getConditionsConvert(casConvert, true);

		// 统计半成品外发
		ms = new ArrayList<BillDetail>();

		String[] codes = null;
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType

				(MaterielType.SEMI_FINISHED_PRODUCT);
		codes = new String[] { "4104", "4103", "4106", "4006", "4004", "4003" };
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes,

				")"));// 查询半成品外发
		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet left join fetch a.complex ";
		ms = casDao.commonSearch("", billDetailTableName, conditions, "",leftOuter);
		conditions.remove(conditions.size() - 1);
		List<MaterialBomDetail> boms = null;
		Double totalDosage = null;
		Double ptTotal = null;
		// List<MaterialBomDetail> list=null;
		List<MaterialBomDetail> bomList = null;
		// List<MaterialBomDetail> list=null;
		// 用于视别当前BOM是否要缓存
		boolean isSave = false;
		for (BillDetail bill : ms) {
			// 遍历成品折BOM
			key = getMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();
			}
			Double ptAmount = bill.getPtAmount();
			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {
					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());
					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());
					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());
					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());
					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());
					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());
					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());
					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());
					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());
					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());

					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();

					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusName());
						currentBillDetailBom.setMptDeSpec(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusSpec());
						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null != f.getUnitConvert()) {
							currentBillDetailBom.setPtTotal(f.getUnitConvert()
									* totalDosage);
						} else {
							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add(currentBillDetailBom);

				}
			}
		}

		// 统计成品外发
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		codes = new String[] { "2113", "2114", "2017", "2015" };
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes, ")"));// 查询成品外发
		ms = casDao.commonSearch("", billDetailTableName, conditions, "",leftOuter);
		conditions.remove(conditions.size() - 1);
		for (BillDetail bill : ms) {
			// 取出成品的数量
			Double ptAmount = bill.getPtAmount();
			// 通过成品取得料件
			key = getMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();
			}

			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {
					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());
					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());
					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());
					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());
					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());
					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());
					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());
					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());
					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());
					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());

					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();

					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusName());
						currentBillDetailBom.setMptDeSpec(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusSpec());
						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null != f.getUnitConvert()) {
							currentBillDetailBom.setPtTotal(f.getUnitConvert()
									* totalDosage);
						} else {
							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add(currentBillDetailBom);
				}
			}
		}

		return billBoms;
	}

	/**
	 * 生成查询条件
	 * 
	 * @param casCondition
	 * @return
	 */
	public List<Condition> getConditions(CasCondition casCondition,
			boolean isMaterie) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));
		if (casCondition.getDate() != null) { // 开始日期
			Date date = CommonUtils.getEndDate(casCondition.getDate());
			Date startDate = CommonUtils.getBeginDate(new Date(date.getYear(),
					0, 1));
			conditions.add(new Condition("and", null, "billMaster.validDate",
					">=", startDate, null));
			conditions.add(new Condition("and", null, "billMaster.validDate",
					"<=", casCondition.getDate(), null));
		}
		if (isMaterie) {
			// 工厂料号不等于空,结束料号为空时
			if (!casCondition.getStartPtPart().equals("")
					&& casCondition.getEndPtPart().equals("")) {
				conditions.add(new Condition("and", null, "ptPart", "=",
						casCondition.getStartPtPart(), null));
			} else // 工厂料号不等于空,结束料号不为空时
			if (!casCondition.getStartPtPart().equals("")
					&& !casCondition.getEndPtPart().equals("")) {
				conditions.add(new Condition("and", "(", "ptPart", ">=",
						casCondition.getStartPtPart(), null));
				conditions.add(new Condition("and", null, "ptPart", "<=",
						casCondition.getEndPtPart(), ")"));
			}
			// 工厂名称
			if (!casCondition.getPtName().equals("")) {
				conditions.add(new Condition("and", null, "ptName", "=",
						casCondition.getPtName(), null));
			}
			// 工厂规格
			if (!casCondition.getPtSpec().equals("")) {
				conditions.add(new Condition("and", null, "ptSpec", "=",
						casCondition.getPtSpec(), null));
			}
		}
		return conditions;
	}

	/**
	 * 结查询结果进行过滤
	 * 
	 * @param list
	 * @param casCondition
	 * @return
	 */
	public List filterListConvert(List<BillDetail> list, CasConvert casConvert) {
		Map<String, BillDetail> mMap = new HashMap();
		// 返回结果
		List<BillDetail> rs = new ArrayList();
		String key = "";
		BillDetail temp = null;
		for (BillDetail bill : list) {
			key = getBillDetailKey(bill, casConvert.getIshsTaotal(), casConvert
					.getIsGroup());
			if ((temp = mMap.get(key)) == null) {
				mMap.put(key, bill);
			} else {
				temp.setPtAmount(CommonUtils.getDoubleExceptNull(temp
						.getPtAmount())
						+ CommonUtils.getDoubleExceptNull(bill.getPtAmount()));
				mMap.put(key, temp);
			}
		}
		list = new ArrayList(mMap.values());
		// 数据过滤
		if (casConvert.getIsShowLess()) {// 不存在key
			for (BillDetail bill : list) {
				if (bill.getPtAmount() < 0) {
					rs.add(bill);
				}
			}
			return rs;
		} else {
			return list;
		}
	}

	/**
	 * 结查询结果进行过滤
	 * 
	 * @param list
	 * @param casCondition
	 * @return
	 */
	public List filterList(List<TempThatDayBalance> list,
			CasCondition casCondition) {
		Map<String, TempThatDayBalance> mMap = new HashMap();
		// 返回结果
		List<TempThatDayBalance> rs = new ArrayList();
		String key = "";
		TempThatDayBalance temp = null;
		if (casCondition.getIsGroup() || casCondition.getIshsTaotal()) {
			for (TempThatDayBalance balance : list) {
				key = getBillDetailKeyGroup(balance, casCondition
						.getIshsTaotal(), casCondition.getIsGroup());
				if ((temp = mMap.get(key)) == null) {
					mMap.put(key, balance);
				} else {
					temp.setPtAmount(CommonUtils.getDoubleExceptNull(temp
							.getPtAmount())
							+ CommonUtils.getDoubleExceptNull(balance
									.getPtAmount()));
					temp.setHsAmount(CommonUtils.getDoubleExceptNull(temp
							.getHsAmount()
							+ CommonUtils.getDoubleExceptNull(balance
									.getHsAmount())));
					mMap.put(key, temp);
				}
			}
			list = new ArrayList(mMap.values());
		}
		// 数据过滤
		if (casCondition.getIsShowLess()) {// 不存在key
			for (TempThatDayBalance balance : list) {
				if (balance.getPtAmount() < 0) {
					rs.add(balance);
				}
			}
			return rs;
			
		} else {
			return list;
		}
	}

	/**
	 * 成品折料新方法
	 * 
	 * @param casCondition
	 * @param billDetail
	 * @return
	 * @author xiaokai
	 */
	public List getCurrentBillDetailBom(CasConvert casConvert, String billDetail) {
		System.out.println("===========新方法里面=============");
		// 用于缓存成品／半成品的BOM
		Map<String, List<MaterialBomDetail>> bomMap = new HashMap<String, List<MaterialBomDetail>>();

		// 通过料件条件取出料件结果集
		Map<String, String> rsMap = getResultMaterielConvert(casConvert);

		String leftOuter="";
		String key = "";
		// 成品查询结果
		List<BillDetail> ms = null;
		// 返回折算最终结果
		List<CurrentBillDetailBom> billBoms = new ArrayList<CurrentBillDetailBom>();
		// 组织成品查询条件
		List<Condition> conditions = getConditionsConvert(casConvert, true);

		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);

		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet left join fetch a.complex ";
		// 根据条件查找成品
		ms = casDao.commonSearch("", billDetailTableName, conditions, "",leftOuter);

		List<MaterialBomDetail> boms = null;
		Double totalDosage = null;
		Double ptTotal = null;
		List<MaterialBomDetail> bomList = null;
		// 用于视别当前BOM是否要缓存
		boolean isSave = false;
		// List<MaterialBomDetail> list=null;
		for (BillDetail bill : ms) {
			// 遍历成品折BOM
			// 检测bom缓存中是否存在此成品的bom
			key = getXiaokMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();

			}
			// 成品折料件
			Double ptAmount = bill.getPtAmount();
			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {

					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());
					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());
					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());
					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());
					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());
					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());
					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());
					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());
					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());
					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());

					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();

					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusName());
						currentBillDetailBom.setMptDeSpec(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusSpec());
						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null != f.getUnitConvert()) {
							currentBillDetailBom.setPtTotal(f.getUnitConvert()
									* totalDosage);
						} else {
							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add(currentBillDetailBom);
				}
			}
		}
		
		// 统计残次品外发
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
		String[] codes = new String[] { "5001" };
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes, ")"));// 查询成品外发
		
		conditions.add(new Condition("and", null, "note",
				"=", "成品", null));
		ms = casDao.commonSearch("", billDetailTableName, conditions, "",leftOuter);

		for (BillDetail bill : ms) {
			// 取出成品的数量
			Double ptAmount = bill.getPtAmount();
			// 通过成品取得料件
			key = getMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();
			}
			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {
					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();

					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());

					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());

					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());

					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());

					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());

					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());

					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());

					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());

					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());

					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());
					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();
					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f

						.getStatCusNameRelationHsn().getCusName());

						currentBillDetailBom.setMptDeSpec(f

						.getStatCusNameRelationHsn() == null ? "" : f

						.getStatCusNameRelationHsn().getCusSpec());

						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null !=

						f.getUnitConvert()) {

							currentBillDetailBom.setPtTotal(f.getUnitConvert()

							* totalDosage);
						} else {

							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add

					(currentBillDetailBom);
				}
			}
		}
		return billBoms;
	}

	/**
	 * 外发加工/在产品 残次品 当日结存表
	 * 
	 * @author chenSir
	 */
	// public List getCurrentBillDetail(CasCondition casCondition,
	// String balanceType) {
	// if(!("outSource".equals(balanceType)
	// || "currentTotal".equals(balanceType)
	// ||MaterielType.BAD_PRODUCT.equals(balanceType))){
	// return null;
	// }
	// // 当日结存map
	// Map<String, TempThatDayBalance > balanceMap = new HashMap();
	// // 依查询条件 筛选的 物料结果集
	// Map<String, String> rsMap = getResultMateriel(casCondition);
	// String key = "";
	// //用来缓存bom
	// Map<String,List<MaterialBomDetail>> bomMap = new HashMap<String,
	// List<MaterialBomDetail>>();
	//		
	// // 获取 单据体 结果
	// List<BillDetail> billList = new ArrayList();
	// // 单日结存 结果
	// List<TempThatDayBalance> balanceList = null;
	// List<TempThatDayBalance> rs = null;
	//		
	// // 第一:统计 料件部分
	// List<Condition> conditions = getConditions(casCondition, true);
	//			
	// if ("outSource".equals(balanceType)//1.外发加工
	// || "currentTotal".equals(balanceType)) {//2.在产品
	// billList = getBillDetailForBalance(balanceType, MaterielType.MATERIEL,
	// conditions);
	//			
	// } else if (MaterielType.BAD_PRODUCT.equals(balanceType)) {//3.残次品
	// billList = getBillDetailForBalance(balanceType, MaterielType.BAD_PRODUCT,
	// conditions);
	// }
	//		
	// for (BillDetail bill : billList) {
	// key = getKey(bill,casCondition.getIshsTaotal()
	// ,casCondition.getIsGroup());
	// if (rsMap == null || rsMap.containsKey(key)) {
	// int inOut = isAddOutAndBom(bill,balanceType);
	// if(inOut != 0){
	// transferBalanceFromBillDetail(balanceMap, bill, inOut,
	// balanceType,key,casCondition.getIshsTaotal());
	// }
	// }
	// }
	//		
	// // 第二步：统计 半成品部分
	// billList = new ArrayList();
	// conditions = getConditions(casCondition, false);
	// if ("outSource".equals(balanceType) //1.外发加工
	// || "currentTotal".equals(balanceType)) {//2.在产品
	// billList = getBillDetailForBalance(balanceType,
	// MaterielType.SEMI_FINISHED_PRODUCT, conditions);
	// }
	// circleBillDetailForBalance(balanceMap,billList,balanceType,rsMap,bomMap,casCondition);
	//		
	// // 第三步：统计 成品 部分
	// billList = new ArrayList();
	// if ("outSource".equals(balanceType) //1.外发加工
	// || "currentTotal".equals(balanceType)) {//2.在产品
	// billList = getBillDetailForBalance(balanceType,
	// MaterielType.FINISHED_PRODUCT, conditions);
	// } else if (MaterielType.BAD_PRODUCT.equals(balanceType)) {//3.残次品
	// billList = getBillDetailForBalance(balanceType, MaterielType.BAD_PRODUCT,
	// conditions);
	// }
	// circleBillDetailForBalance(balanceMap,billList,balanceType,rsMap,bomMap,casCondition);
	// // 第四步：统计残次品外发当日结存
	// if ("outSource".equals(balanceType)) {
	// billList = getBillDetailForBalance(balanceType, MaterielType.BAD_PRODUCT,
	// conditions);
	// }
	// circleBillDetailForBalance(balanceMap,billList,balanceType,rsMap,bomMap,casCondition);
	//
	// balanceList = new ArrayList(balanceMap.values());
	//		
	// //显示负数结存
	// if (casCondition.getIsShowLess()) {
	// for (TempThatDayBalance balance : balanceList) {
	// if (balance.getPtAmount() < 0) {
	// rs.add(balance);
	// }
	// }
	// return rs;
	// } else {
	// return balanceList;
	// }
	//		
	// }
	/**
	 * wss2010.05.06 外发加工/在产品 残次品 当日结存表 新写
	 */

	public List getCurrentBillDetailNew(List<Condition> conditions,
			String orderBy, String balanceType, Boolean ishsTaotal,
			Boolean isShowLess, Boolean isGroup) {
		if (!("outSource".equals(balanceType)
				|| "currentTotal".equals(balanceType) || MaterielType.BAD_PRODUCT
				.equals(balanceType))) {
			return null;
		}

		// 当日结存map
		Map<String, TempThatDayBalance> balanceMap = new HashMap();
		String key = "";
		// 用来缓存bom
		Map<String, List<MaterialBomDetail>> bomMap = new HashMap<String, List<MaterialBomDetail>>();
		// 用来缓存对应关系
		Map<String, FactoryAndFactualCustomsRalation> ffrMap = new HashMap<String, FactoryAndFactualCustomsRalation>();
		// 获取 单据体 结果
		List<BillDetail> billList = new ArrayList();
		// 单日结存 结果
		List<TempThatDayBalance> balanceList = new ArrayList<TempThatDayBalance>();
		List<TempThatDayBalance> rs = new ArrayList<TempThatDayBalance>();

		// 第一:统计 料件部分
		if ("outSource".equals(balanceType)// 1.外发加工
				|| "currentTotal".equals(balanceType)) {// 2.在产品
			billList = getBillDetailForBalance(balanceType,
					MaterielType.MATERIEL, conditions);

		} else if (MaterielType.BAD_PRODUCT.equals(balanceType)) {// 3.残次品
			billList = getBillDetailForBalance(balanceType,
					MaterielType.MATERIEL, conditions);
		}

		for (BillDetail bill : billList) {
			System.out
					.println("wss billNo:" + bill.getBillMaster().getBillNo());
			key = getBillNoBomKey(bill, ishsTaotal, isGroup, true);
			int inOut = isAddOutAndBom(bill, balanceType);
			if (inOut != 0) {
				transferBalanceFromBillDetail(balanceMap, bill, inOut,
						balanceType, key, ishsTaotal);
			}

		}

		// 第二步：统计 半成品部分
		billList = new ArrayList();
		if ("outSource".equals(balanceType) // 1.外发加工
				|| "currentTotal".equals(balanceType)) {// 2.在产品
			billList = getBillDetailForBalance(balanceType,
					MaterielType.SEMI_FINISHED_PRODUCT, conditions);
		}
		circleBillDetailForBalance(balanceMap, billList, balanceType, bomMap,
				ishsTaotal, isGroup, ffrMap);

		// 第三步：统计 成品 部分
		billList = new ArrayList();
		if ("outSource".equals(balanceType) // 1.外发加工
				|| "currentTotal".equals(balanceType)) {// 2.在产品
			billList = getBillDetailForBalance(balanceType,
					MaterielType.FINISHED_PRODUCT, conditions);
		} else if (MaterielType.BAD_PRODUCT.equals(balanceType)) {// 3.残次品
			billList = getBillDetailForBalance(balanceType,
					MaterielType.FINISHED_PRODUCT, conditions);
		}
		circleBillDetailForBalance(balanceMap, billList, balanceType, bomMap,
				ishsTaotal, isGroup, ffrMap);

		// 第四步：统计残次品外发当日结存
		billList = new ArrayList();
		if ("outSource".equals(balanceType)) {
			billList = getBillDetailForBalance(balanceType,
					MaterielType.BAD_PRODUCT, conditions);
		}
		circleBillDetailForBalance(balanceMap, billList, balanceType, bomMap,
				ishsTaotal, isGroup, ffrMap);

		balanceList = new ArrayList(balanceMap.values());

//		Collections.sort(balanceList, new MyComparator());// 排序

		// 显示负数结存
		if (isShowLess) {
			for (TempThatDayBalance balance : balanceList) {
				if (ishsTaotal ? (balance.getHsAmount()!=null && balance.getHsAmount() < 0) 
						: (balance.getPtAmount() != null && balance.getPtAmount() < 0)) {
					rs.add(balance);
				}
			}
			return rs;
		} else {
			return balanceList;
		}

	}
	
	
	/**
	 *  外发加工当日结存表(从单据来) 折料反查
	 * @author wss  2010.07.26 
	 */
	public List getCurrentBillDetailOutSource(List<Condition> conditions,
			String orderBy, String balanceType, Boolean ishsTaotal,
			Boolean isShowLess, Boolean isGroup,List<Condition> conditionsM,
			Boolean isFromCheck,List<Condition> conditionsCheck) {
		
		//统计结果
		Map<String, TempThatDayBalance> balanceMap = new HashMap();
		
		
		/****************外发加工当日结存从盘点抓取****************************/
		if(isFromCheck){
			
			conditionsCheck.add(new Condition("and", null, "kcType", "=", "4",null));
			
			//wss2010.08.16可以这样子加吗？
			String leftOuter = " left join fetch a.complex left join fetch a.wareSet  ";
			
			List<CheckBalanceConvertMateriel> listCheckM = casDao.commonSearch("", "CheckBalanceConvertMateriel",
					conditionsCheck, "", leftOuter);
			System.out.println("wss listCheckM.size: " + listCheckM.size());
			
			for(int i=0;i<listCheckM.size();i++){
				
				CheckBalanceConvertMateriel cb = listCheckM.get(i);
				
				String key = getCheckBalanceConvertMaterielKey(cb,ishsTaotal, isGroup);
				
				transferBalanceFromCheckBalanceConvertMateriel(balanceMap, cb,
						"outSource", key, ishsTaotal);
			}
			
		}
		/******************外发加工当日结存从单据抓*******************************/
		else{
			List<SemiProductInfo> result = new ArrayList<SemiProductInfo>();
	
			// 按料件条件集合conditionsMaterial找出料件集合
			boolean isAll = false;
			if (conditionsM.size() == 0) {
				isAll = true;
			}
	
			Map<String, String> mapMaterial = getResultMaterielKey(conditionsM);
	
			// 符合查询条件的key集合
			List<String> mList = null;
			if (mapMaterial != null) {
				mList = new ArrayList<String>(mapMaterial.values());
				System.out.println("wss 符合查询条件对应关系List.size()=" + mList.size());
			}
	
			// 按条件集合finishedConditions找出符合条件的半成品、成品、原料单据
			List<BillDetail> listMaterial = new ArrayList<BillDetail>();
			List<BillDetail> listSemi = new ArrayList<BillDetail>();
			List<BillDetail> listFinished = new ArrayList<BillDetail>();
	
			// 查外发加工的料件单据
			listMaterial = getBillDetailForBalance(balanceType,
					MaterielType.MATERIEL, conditions);
	
			// 查我发加工的半成品单据
			listSemi = getBillDetailForBalance(balanceType,
					MaterielType.SEMI_FINISHED_PRODUCT, conditions);
	
			// 查外发加工的成品单据
			listFinished = getBillDetailForBalance(balanceType,
					MaterielType.FINISHED_PRODUCT, conditions);
	
			// 表示要留下的 料件
			List<BillDetail> listAdd = new ArrayList<BillDetail>();
	
			// 表示要留下的成品、半成品 折料后 生成的盘点 + 料件盘点
			List<TempCurrentBillDetailPanDian> listDetailAll = new ArrayList<TempCurrentBillDetailPanDian>();
	
			System.out.println("wss 单据料件listMaterial.size=" + listMaterial.size());
			System.out.println("wss 单据成品listSemi.size=" + listSemi.size());
			System.out.println("wss 单据半成品listFinished.size=" + listFinished.size());
	
			// 过滤料件单据，留下符合查询条件
			for (int i = 0; i < listMaterial.size(); i++) {
				//1.如果查询条件为空，则全部加上
				BillDetail bill = listMaterial.get(i);
				if (mList == null) {
					listAdd.add(bill);
				}
				//2.如果编码为空，或者单位为空，直接跳过
				else if(bill.getComplex() == null || bill.getHsUnit() == null){
					continue;
				}
				else {
					for (String s : mList) {
						if (s
								.equals(bill.getPtPart() + "/" 
										+ bill.getComplex().getCode() + "/"
										+ bill.getHsName() + "/"
										+ bill.getHsSpec() + "/"
										+ bill.getHsUnit().getName())) {
							listAdd.add(bill);
							break;
						}
					}
				}
			}
			
			// 将纯料件的单据 转化为 当日结存信息
			if (listAdd.size() != 0) {
				for (int i = 0; i < listAdd.size(); i++) {
					BillDetail bill = listAdd.get(i);
					
					System.out.println("wss billType = " + bill.getBillMaster().getBillType().getCode());
					int inOut = isAddOutAndBom(bill, balanceType);
					String key = getBillNoBomKey(bill, ishsTaotal, isGroup, false);
					if (inOut != 0) {
						transferBalanceFromBillDetail(balanceMap, bill, inOut,
								balanceType, key, ishsTaotal);
					}
				}
			}
	
			// 半成品、成品一起折算
			listSemi.addAll(listFinished);
	
			// 临时BOM集合
			List<TempBomRelation> boms = null;
			String bomKey = null;
	
			// key为成品料号 value为对应的临时BOM集合
			Map<String, List<TempBomRelation>> bomMap = new HashMap<String, List<TempBomRelation>>();
	
			// 遍历要折料的成品、半成品单据
			for (BillDetail bill : listSemi) {
				bomKey = bill.getPtPart();
				boms = bomMap.get(bomKey);
				if (boms != null) {
				} else {
					boms = this.getBomRelation(bill, mapMaterial, isAll);
					bomMap.put(bomKey, boms);
				}
	
				System.out.println("wss billType = " + bill.getBillMaster().getBillType().getCode());
				System.out.println("wss boms.size = " + boms.size());
	
				// 将折后料件转化为当日结存信息
				for (TempBomRelation bom : boms) {
	
					int inOut = isAddOutAndBom(bill, balanceType);
					
					String key = getTempBomKey(bill, bom, ishsTaotal, isGroup);
					
					if (inOut != 0) {
						transferBalanceFromTempBom(balanceMap, bill, bom ,inOut, 
								balanceType,ishsTaotal,key);
					}
				}
			}
		}
		

		List<TempThatDayBalance> balanceList = new ArrayList(balanceMap.values());
		List<TempThatDayBalance> rs = new ArrayList(balanceMap.values());

//		Collections.sort(balanceList, new MyComparator());// 排序

		// 显示负数结存
		if (isShowLess) {
			for (TempThatDayBalance balance : balanceList) {
				if (ishsTaotal ? (balance.getHsAmount()!=null && balance.getHsAmount() < 0) 
						: (balance.getPtAmount() != null && balance.getPtAmount() < 0)) {
					rs.add(balance);
				}
			}
			return rs;
		} else {
			return balanceList;
		}

	}
	
	/**
	 * 获取
	 * @return
	 */
	public String getTempBomKey(BillDetail bill,TempBomRelation bom, Boolean ishsTaotal,Boolean isGroup){
		String key = "";

		// 料号+名称+规格
		String key1 = bom.getPtNo() + "/" + bom.getPtName()
				+ "/" + bom.getPtSpec();

		// 海关编码 +报关名称 +报关规格
		String key2 = bom.getComplex().getCode() + "/"
				+ bom.getHsName() + "/" + bom.getHsSpec();

		if (ishsTaotal) {
			key = key2;
		} else {
			key = key1 + "/" + key2;
		}
		if (isGroup) {
			key += "/" + bill.getWareSet().getCode();
		}
		return key;
	}


	/**
	 * 查找当日结存表所需的单据体
	 * 
	 * @param balanceType
	 *            结存类型
	 * @param billDetail
	 *            物料类型
	 * @param conditions
	 *            条件
	 * @return
	 * @author wss
	 */
	private List getBillDetailForBalance(String balanceType,
			String materielType, List<Condition> conditions) {
		String[] codes = null;
		List billList = new ArrayList();
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType.trim());
		if (billDetailTableName == null) {
			return billList;
		}
		// 在产品
		if ("currentTotal".equals(balanceType)) {
			if (MaterielType.MATERIEL.equals(materielType)) {// 料件部分
				codes = new String[] { "1101", "1002", "1006" };
			}
			if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {// 半成品部分
				codes = new String[] { "4002", "4003", "4004", "4006",
						"4102", "4103", "4104", "4106" };
			}
			if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {// 成品部分
				codes = new String[] { "2002","2103" };
			}
			if (MaterielType.BAD_PRODUCT.equals(materielType)) {// 残次品中半品部分
				codes = new String[] { "5001" };
				conditions.add(new Condition("and", null, "note", "=", "半成品",
						null));
			}
			
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", " in (", codes, ")"));
		}
		// 外发加工
		if ("outSource".equals(balanceType)) {
			if (MaterielType.BAD_PRODUCT.equals(materielType)) {
				conditions.add(new Condition("and", null,
						"billMaster.billType.code", "=", "5003", null));
			} else {
				if (MaterielType.MATERIEL.equals(materielType)) {// 料件部分
					codes = new String[] { "1012", "1013", "1014", "1017",
							"1018", "1110", "1112", "1113" };
				}
				if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {// 半成品部分
					codes = new String[] { "4003", "4004", "4006", "4103",
							"4104", "4106" };
				}
				if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {// 成品部分
					codes = new String[] { "2113", "2114", "2015", "2017" };
				}

				conditions.add(new Condition("and", null,
						"billMaster.billType.code", " in (", codes, ")"));
			}
		}
		// 残次品
		if (MaterielType.BAD_PRODUCT.equals(balanceType)) {
			// 这时表名为残次品的啦
			billDetailTableName = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);

			if (MaterielType.MATERIEL.equals(materielType)) {// 料件部分
				conditions.add(new Condition("and", null, "note", "=", "料件",
						null));
			}
			if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {// 成品部分
				conditions.add(new Condition("and", null, "note", "=", "成品",
						null));
			}
		}

		//wss2010.08.16可以这样子加吗？
		String leftOuter = " left join fetch a.complex left join fetch a.billMaster left join fetch a.wareSet  ";

		billList = casDao.commonSearch("", billDetailTableName, conditions, "",
				leftOuter);
		conditions.remove(conditions.size() - 1);
		
		//在产品中  的残次品（半成品） 时  要除去两个condition
		if("currentTotal".equals(balanceType) && MaterielType.BAD_PRODUCT.equals(materielType)){
			conditions.remove(conditions.size() - 1);
		}
		
		return billList;
	}

	/**
	 * 遍历所选单据体，通过bom得到当日结存
	 * 
	 * 
	 * @author wss
	 */
	private void circleBillDetailForBalance(
			Map<String, TempThatDayBalance> balanceMap,
			List<BillDetail> billDetailList, String balanceType,
			Map<String, List<MaterialBomDetail>> bomMap, boolean ishsTaotal,
			boolean isGroup,
			Map<String, FactoryAndFactualCustomsRalation> ffrMap) {
		for (BillDetail bill : billDetailList) {

			System.out
					.println("wss billNo:" + bill.getBillMaster().getBillNo());

			String bomKey = bill.getPtPart() + "/" + bill.getVersion();

			List<MaterialBomDetail> boms = bomMap.get(bomKey);
			if (boms == null) {
				boms = casDao.getProductBom(bill);
				bomMap.put(bomKey, boms);
			}

			for (MaterialBomDetail bom : boms) {
				String key = getBillBomKey(bom, bill, ishsTaotal, isGroup,
						ffrMap);
				if (key == null) {
					throw new RuntimeException("料号["
							+ bom.getMateriel().getPtNo() + "]未做完整对应关系！请检查！");
				}
				int inOut = isAddOutAndBom(bill, balanceType);
				if (inOut != 0) {
					transferBalanceFromBom(balanceMap, bill, bom, inOut,
							balanceType, key, ishsTaotal, ffrMap);
				}
			}
		}

	}
	
	/**
	 * 遍历所选单据体，通过TempBom得到当日结存
	 * 
	 * 
	 * @author wss
	 */
	private void transferBalanceFromTempBom(Map<String,TempThatDayBalance> balanceMap, 
											BillDetail bill, TempBomRelation bom,
											int inOut, String balanceType,
											Boolean ishsTaotal,
											String key){

		// 单据临时实体
		TempThatDayBalance balanceTemp = null;
		TempThatDayBalance newTemp = new TempThatDayBalance();

		newTemp.setPtAmount(inOut
				* CommonUtils.getDoubleExceptNull(bill.getPtAmount())
				* CommonUtils.getDoubleExceptNull(bom.getUnitUsed()));// 工厂数量 = 成品单据数量 * 单项用量

		newTemp.setUnitConvert(bom.getUnitConvert());// 折算系数就为对应关系中的折算系数

		newTemp.setHsAmount(CommonUtils.getDoubleExceptNull(newTemp
				.getPtAmount())
				* CommonUtils.getDoubleExceptNull(newTemp.getUnitConvert()));// 报关数量 = 工厂数量 * 折算比例


		System.out.println(" wss 开始折Bom的**************** ");
		System.out.println(" wss key : " + key);
		System.out.println(" wss newTemp.ptAmount = " + newTemp.getPtAmount());
		System.out.println(" wss newTemp.hsAmount = " + newTemp.getHsAmount());
		if ((balanceTemp = balanceMap.get(key)) == null) {

			if (!ishsTaotal) { // 如果以报关名称汇总则不用 企业内部物料资料
				newTemp.setPtPart(bom.getPtNo());
				newTemp.setPtName(bom.getPtName());
				newTemp.setPtSpec(bom.getPtSpec());
				newTemp.setPtUnit(bom.getPtUnit());// 工厂单位
			}
			newTemp.setComplex(bom.getComplex());
			newTemp.setHsName(bom.getHsName());
			newTemp.setHsSpec(bom.getHsSpec());
			newTemp.setHsUnit(bom.getHsUnit());

			newTemp.setWareSet(bill.getWareSet());

			balanceMap.put(key, newTemp);
		} else {
			balanceTemp.setPtAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getPtAmount())
					+ CommonUtils.getDoubleExceptNull(newTemp.getPtAmount()));
			balanceTemp.setHsAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getHsAmount())
					+ CommonUtils.getDoubleExceptNull(newTemp.getHsAmount()));

			System.out.println(" wss balanceTemp.ptAmount = "
					+ balanceTemp.getPtAmount());
			System.out.println(" wss balanceTemp.hsAmount = "
					+ balanceTemp.getHsAmount());

			balanceMap.put(key, balanceTemp);
		}
	}

	/**
	 * wss:根据查询条件，获得相应 的 key ,（要折料的那些）
	 * 
	 * @param billDetail
	 * @param ishsTaotal
	 *            是否报关名称汇总
	 * @param isGroup
	 *            是否按仓库分组
	 */
	private String getBillBomKey(MaterialBomDetail bom, BillDetail billDetail,
			boolean ishsTaotal, boolean isGroup,
			Map<String, FactoryAndFactualCustomsRalation> ffrMap) {
		String key = "";
		Materiel mt = bom.getMateriel();

		// 料号 + 名称 + 规格
		key = mt.getPtNo() + "/" + mt.getFactoryName() + "/"
				+ mt.getFactorySpec();

		if (ishsTaotal) {
			FactoryAndFactualCustomsRalation ffr = ffrMap.get(key);
			if (ffr == null) {
				ffr = casDao.findFactoryAndFactualCustomsRalationByM(bom
						.getMateriel());
				if (ffr == null) {
					throw new RuntimeException("料号["
							+ bom.getMateriel().getPtNo() + "]在对应关系中不存在!");
				}
				ffrMap.put(key, ffr);
			}

			StatCusNameRelationHsn scrh = ffr.getStatCusNameRelationHsn();
			if (scrh == null) {
				throw new RuntimeException("料号[" + bom.getMateriel().getPtNo()
						+ "]在对应关系中未做完整!");
			}

			// 海关编码 + 报关名称 + 报关规格
			key = scrh.getComplex().getCode() + "/" + scrh.getCusName() + "/"
					+ scrh.getCusSpec();
		}

		if (isGroup) {// 仓库
			key += "/" + billDetail.getWareSet().getCode();
		}
		return key;
	}

	/**
	 * bom 转化为 当日结存
	 * 
	 * @param balanceMap
	 * @param bill
	 * @param bom
	 * @param inOut
	 * @param billDetail
	 * @param key
	 * @author wss
	 */
	private void transferBalanceFromBom(
			Map<String, TempThatDayBalance> balanceMap, BillDetail bill,
			MaterialBomDetail bom, int inOut, String billDetail, String key,
			boolean ishsTaotal,
			Map<String, FactoryAndFactualCustomsRalation> ffrMap) {

		FactoryAndFactualCustomsRalation ffr = ffrMap.get(key);
		if (ffr == null) {
			ffr = casDao.findFactoryAndFactualCustomsRalationByM(bom
					.getMateriel());
			if (ffr == null) {
				throw new RuntimeException("料号[" + bom.getMateriel().getPtNo()
						+ "]在对应关系中不存在!");
			}
			ffrMap.put(key, ffr);
		}

		// 单据临时实体
		TempThatDayBalance balanceTemp = null;
		TempThatDayBalance newTemp = new TempThatDayBalance();

		newTemp.setPtAmount(inOut
				* CommonUtils.getDoubleExceptNull(bill.getPtAmount())
				* CommonUtils.getDoubleExceptNull(bom.getUnitUsed()));// 工厂数量 =
		// 单据数量
		// *
		// 单项用量

		newTemp.setUnitConvert(ffr.getUnitConvert());// 折算系数就为对应关系中的折算系数

		// newTemp.setUnitConvert(bom.getMateriel().getUnitConvert());//折算系数
		// 为报关常用工厂物料 中的折算系数
		newTemp.setHsAmount(CommonUtils.getDoubleExceptNull(newTemp
				.getPtAmount())
				* CommonUtils.getDoubleExceptNull(newTemp.getUnitConvert()));// 报关数量
		// =
		// 工厂数量*
		// 折算系数

		System.out.println(" wss key : " + key);
		System.out.println(" wss newTemp.ptAmount = " + newTemp.getPtAmount());
		System.out.println(" wss newTemp.hsAmount = " + newTemp.getHsAmount());
		if ((balanceTemp = balanceMap.get(key)) == null) {
			StatCusNameRelationHsn scrh = ffr.getStatCusNameRelationHsn();
			if (scrh == null) {
				throw new RuntimeException("料号[" + bom.getMateriel().getPtNo()
						+ "]在对应关系中未做完整!");
			}
			if (!ishsTaotal) { // 如果以报关名称汇总则不用 企业内部物料资料
				newTemp.setPtPart(bom.getMateriel().getPtNo());
				newTemp.setPtName(bom.getMateriel().getFactoryName());
				newTemp.setPtSpec(bom.getMateriel().getFactorySpec());
				newTemp.setPtUnit(bom.getMateriel().getCalUnit());// 工厂单位
			}
			newTemp.setComplex(scrh.getComplex());
			newTemp.setHsName(scrh.getCusName());
			newTemp.setHsSpec(scrh.getCusSpec());
			newTemp.setHsUnit(scrh.getCusUnit());

			newTemp.setWareSet(bill.getWareSet());

			balanceMap.put(key, newTemp);
		} else {
			balanceTemp.setPtAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getPtAmount())
					+ CommonUtils.getDoubleExceptNull(newTemp.getPtAmount()));
			balanceTemp.setHsAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getHsAmount())
					+ CommonUtils.getDoubleExceptNull(newTemp.getHsAmount()));

			System.out.println(" wss balanceTemp.ptAmount = "
					+ balanceTemp.getPtAmount());
			System.out.println(" wss balanceTemp.hsAmount = "
					+ balanceTemp.getHsAmount());

			balanceMap.put(key, balanceTemp);
		}
	}

	/**
	 * 根据单据类型判断折算bom加减 加为1 减为-1 不加不减为0
	 * 
	 * @return
	 * @author chensir wss修改
	 */
	private int isAddOutAndBom(BillDetail bill, String billDetail) {
		String billTypeCode = bill.getBillMaster().getBillType().getCode();
		if ("outSource".equals(billDetail)) {
			if ("1014".equals(billTypeCode)
					|| "1113".equals(billTypeCode)
					|| "1112".equals(billTypeCode)
					|| "1110".equals(billTypeCode)// 料件
					|| "4104".equals(billTypeCode)
					|| "4103".equals(billTypeCode)
					|| "4106".equals(billTypeCode)// 半成品
					|| "2113".equals(billTypeCode)
					|| "2114".equals(billTypeCode)// 成品
			) {
				return 1;// 加
			} else if ("1018".equals(billTypeCode)
					|| "1017".equals(billTypeCode)
					|| "1013".equals(billTypeCode)
					|| "1012".equals(billTypeCode)// 料件
					|| "4006".equals(billTypeCode)
					|| "4004".equals(billTypeCode)
					|| "4003".equals(billTypeCode)// 半成品
					|| "2015".equals(billTypeCode)
					|| "2017".equals(billTypeCode)// 成品
					|| "5003".equals(billTypeCode)) {
				return -1;// 减
			}
		} else if ("currentTotal".equals(billDetail)) {// 在产品，料件为+ ,成品为减,半成品看情况
			if ("1002".equals(billTypeCode) || "1101".equals(billTypeCode)
					|| "4006".equals(billTypeCode)
					|| "4004".equals(billTypeCode)
					|| "4003".equals(billTypeCode)
					|| "4005".equals(billTypeCode)
					|| "4002".equals(billTypeCode)
					|| "4008".equals(billTypeCode)
					|| "2103".equals(billTypeCode)) {
				return 1;// 加
			} else if ("2002".equals(billTypeCode)
					|| "2007".equals(billTypeCode)
					|| "2014".equals(billTypeCode)
					|| "2013".equals(billTypeCode)
					|| "2015".equals(billTypeCode)
					|| "1006".equals(billTypeCode)
					|| "4104".equals(billTypeCode)
					|| "4106".equals(billTypeCode)
					|| "4103".equals(billTypeCode)
					|| "4108".equals(billTypeCode)
					|| "4107".equals(billTypeCode)
					|| "4102".equals(billTypeCode)) {
				return -1;// 减
			}
		} else if (MaterielType.BAD_PRODUCT.equals(billDetail)) {// 残次品
			if ("5002".equals(billTypeCode) || "5001".equals(billTypeCode)
					|| "5003".equals(billTypeCode)
					|| "5004".equals(billTypeCode)
					|| "5005".equals(billTypeCode)) {
				return 1;// 加
			} else if ("5101".equals(billTypeCode)
					|| "5102".equals(billTypeCode)
					|| "5103".equals(billTypeCode)
					|| "5104".equals(billTypeCode)) {
				return -1;// 减
			}
		}
		return 0;
	}

	/**
	 * 料件，成品 当日结存表 2010.07.05 
	 * 
	 * 残次品也不用折料
	 * 
	 * 边角料
	 * 
	 * @author chensir
	 * @author chensir wss修改
	 */
	public List getCurrentBillDetail(List<Condition> conditions,
			String orderBy, String materielType, Boolean ishsTaotal,
			Boolean isShowLess, Boolean isGroup,Boolean isFromCheck,List<Condition> conditionsCheck) {
				
		// 单日结存分组记录map
		Map<String, TempThatDayBalance> balanceMap = new HashMap();
		
		// 数据过滤 记录返回结果list
		List<TempThatDayBalance> rs = new ArrayList();
		List<TempThatDayBalance> list = new ArrayList();
		
		/*
		 * ********原材料当日结存表 来自盘点***********************************
		 */
		if(isFromCheck){
			String kcType = "0";
			
			//原料件
			if(MaterielType.MATERIEL.equals(materielType)){
				kcType = "0";
			}
			//产成品
			else if(MaterielType.FINISHED_PRODUCT.equals(materielType)){
				kcType = "1";
			}
			//外发加工
			else if("outSource".equals(materielType)){
				kcType = "4";
			}
			//残次品
			else if(MaterielType.BAD_PRODUCT.equals(materielType)){
				kcType = "5";
			}
			//边角料
			else if(MaterielType.REMAIN_MATERIEL.equals(materielType)){
				kcType = "6";
			}
			
			conditionsCheck.add(new Condition("and", null, "kcType", "=", kcType,null));
			
			
			//wss2010.08.16可以这样子加吗？
			String leftOuter = " left join fetch a.complex  left join fetch a.wareSet  ";
			
			List<CheckBalance> listCheck = casDao.commonSearch("", "CheckBalance",
					conditionsCheck, "", leftOuter);
			System.out.println("wss materielType = " + materielType);
			System.out.println("wss listCheck.size: " + listCheck.size());
			
			for(int i=0;i<listCheck.size();i++){
				
				CheckBalance cb = listCheck.get(i);
				
				String key = getKeyFromCheckBalanceForCurrentBalance(cb,ishsTaotal, isGroup);
				
				transferBalanceFromCheckBalance(balanceMap, cb,
					materielType, key, ishsTaotal);
			}
			
			
		}
		
		
		/*
		 * ********原材料当日结存表 来自单据***********************************
		 */
		else{
			// 单据体表名
			String billDetailTableName = BillUtil
					.getBillDetailTableNameByMaterielType(materielType);
			
			//wss2010.08.16可以这样子加吗？
			String leftOuter = " left join fetch a.complex left join fetch a.billMaster left join fetch a.wareSet  ";
			
			// 查询符合条件的单据体
			List<BillDetail> billList = casDao.commonSearch("",
					billDetailTableName, conditions, "", leftOuter);
			
			
			if(MaterielType.MATERIEL.equals(materielType) 
					|| MaterielType.FINISHED_PRODUCT.equals(materielType)){
				billDetailTableName = BillUtil
							.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
				conditions.add(new Condition("and", null,"billMaster.billType.code", "=","5001", null));
				
				//料件要加上残次品入库（料件部分）
				if(MaterielType.MATERIEL.equals(materielType)){
					conditions.add(new Condition("and", null,"note", "=","料件", null));
				}
				
				//成品要加上残次品入库（成品部分）
				else{
					conditions.add(new Condition("and", null,"note", "=","成品", null));
				}
				
				
				List<BillDetail> billList5001 = casDao.commonSearch("",
						billDetailTableName, conditions, "", leftOuter);
				System.out.println("wss billList5001.size = " + billList5001.size());
				billList.addAll(billList5001);
			}
			
			System.out.println("wss billList.size = " + billList.size());


			// 分组用关键字
			String key = "";
			// 单据临时实体
			TempThatDayBalance balanceTemp = null;
			// 分组统计
			for (BillDetail bill : billList) {
				// key = getMaterielKey(null, bill);//key 为料号 + 工厂名称 + 工厂规格
				System.out.println("wss billNo: "
						+ bill.getBillMaster().getBillNo());

				key = getBillNoBomKey(bill, ishsTaotal, isGroup, false);// 根据条件获得key
                //getExgBillNoBomKey
				int inOut = isAddBillDetail(bill, materielType);// 判断是进还是出
				if (inOut != 0) {
					transferBalanceFromBillDetail(balanceMap, bill, inOut,
							materielType, key, ishsTaotal);
				}
			}
			
		}
		

		
		
		list = new ArrayList(balanceMap.values());

//		Collections.sort(list, new MyComparator());

		// 数据过滤
		if (isShowLess) {// 不存在key
			for (TempThatDayBalance balance : list) {
				if (ishsTaotal ? (balance.getHsAmount() != null && balance.getHsAmount() < 0 ) 
						: (balance.getPtAmount() != null && balance.getPtAmount() < 0 )) {
					rs.add(balance);
				}
			}
			return rs;
		} else {
			return list;
		}
	}
	
	/**
	 * 当日结存 从盘点CheckBalance抓数据时 获取 统计 key
	 * @return
	 * @author wss
	 */
	public String getKeyFromCheckBalanceForCurrentBalance(CheckBalance cb,Boolean ishsTaotal, Boolean isGroup){
		String key = "";

		// 料号+名称+规格
		String key1 = cb.getPtNo() + "/" + cb.getPtName()
				+ "/" + cb.getPtSpec();

		// 海关编码 +报关名称 +报关规格
		String key2 = cb.getComplex() == null ? "" : cb.getComplex().getCode() + "/"
				+ cb.getName() + "/" + cb.getSpec();

		if (ishsTaotal) {
			key = key2;
		} else {
			key = key1 + "/" + key2;
		}
		if (isGroup) {
			key += "/" + cb.getWareSet() == null ? "" : cb.getWareSet().getCode();
		}
		return key;
	}

	/**
	 * 比较器 用于当时结存表与 结存与盘点差额表进行排序 按 工厂料号 报关编码 仓库 排序
	 * 
	 * @author wss
	 */
	class MyComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			TempThatDayBalance t1 = (TempThatDayBalance) o1;
			TempThatDayBalance t2 = (TempThatDayBalance) o2;

			int ptP = (t1.getPtPart() == null ? "0" : t1.getPtPart())
					.compareTo((t2.getPtPart() == null ? "0" : t2.getPtPart()));// 工厂工厂料号
			int hsC;// 报关编码
			int wC;// 仓库编码
			if (ptP > 0) {
				return 1;
			} else if (ptP < 0) {
				return -1;
			} else if ((hsC = t1.getComplex().getCode().compareTo(
					t2.getComplex().getCode())) > 0) {
				return 1;
			} else if (hsC < 0) {
				return -1;
			} else if ((wC = t1.getWareSet().getCode().compareTo(// 仓库
					t2.getWareSet().getCode())) > 0) {
				return 1;
			} else if (wC < 0) {
				return -1;
			} else {
				return 0;
			}

		}
	}

	/**
	 * wss:根据查询条件，获得相应 的 key
	 * 
	 * @param billDetail
	 * @param ishsTaotal
	 *            是否报关名称汇总
	 * @param isGroup
	 *            是否按仓库分组
	 * @param isConvert
	 *            是否是转厂
	 */
	private String getBillNoBomKey(BillDetail billDetail, boolean ishsTaotal,
			boolean isGroup, boolean isConvert) {

		String key = "";

		// 料号+名称+规格
		String key1 = billDetail.getPtPart() + "/" + billDetail.getPtName()
				+ "/" + billDetail.getPtSpec();

		// 海关编码 +报关名称 +报关规格
		String key2 = billDetail.getComplex() == null ? "":billDetail.getComplex().getCode() + "/"
				+ billDetail.getHsName() + "/" + billDetail.getHsSpec();

		if (ishsTaotal) {
			key = key2;
		} else {
			// 如果不是半成品折料
			if (!isConvert) {
				key = key1 + "/" + key2;
			} else {
				key = key1;
			}

		}
		if (isGroup) {
			key += "/" + billDetail.getWareSet().getCode();
		}
		return key;
	}
	
	/**
	 * 成品获取Key
	 */
	private String getExgBillNoBomKey(BillDetail billDetail, boolean ishsTaotal,
			boolean isGroup, boolean isConvert) {

		String key = "";

		// 料号+名称+规格
		String key1 = billDetail.getPtPart() + "/" + billDetail.getPtName()
				+ "/" + billDetail.getPtSpec()+billDetail.getVersion();

		// 海关编码 +报关名称 +报关规格
		String key2 = billDetail.getComplex() == null ? "":billDetail.getComplex().getCode() + "/"
				+ billDetail.getHsName() + "/" + billDetail.getHsSpec();

		if (ishsTaotal) {
			key = key2;
		} else {
			// 如果不是半成品折料
			if (!isConvert) {
				key = key1 + "/" + key2;
			} else {
				key = key1;
			}

		}
		if (isGroup) {
			key += "/" + billDetail.getWareSet().getCode();
		}
		return key;
	}

	/**
	 * 单据体 转化为 当日结存
	 * 
	 * @param balanceMap
	 * @param bill
	 * @param inOut
	 * @param billDetail
	 * @param key
	 * @author wss
	 */
	private void transferBalanceFromBillDetail(
			Map<String, TempThatDayBalance> balanceMap, BillDetail bill,
			int inOut, String billDetail, String key, boolean ishsTaotal) {
		// 单据临时实体
		TempThatDayBalance balanceTemp = null;
		TempThatDayBalance balance = new TempThatDayBalance();

		balance.setPtAmount(inOut * (bill.getPtAmount() == null ? 0.0: bill.getPtAmount()));
		balance.setHsAmount(inOut * (bill.getHsAmount() == null ? 0.0: bill.getHsAmount()));

		System.out.println("wss 非折料的****************");
		System.out.println("wss key :" + key);
		System.out.println("wss ptAmount :" + balance.getPtAmount());
		System.out.println("wss hsAmount :" + balance.getHsAmount());
		if ((balanceTemp = balanceMap.get(key)) == null) {

			balance.setNote(bill.getNote());

			if (!ishsTaotal) {// 如果未选 按报关名称汇总
				balance.setPtPart(bill.getPtPart());
				balance.setPtName(bill.getPtName());
				balance.setPtSpec(bill.getPtSpec());
				balance.setPtUnit(bill.getPtUnit());
			}

			balance.setMaterialType(CheckMyselfUntil.getType(bill
					.getBillMaster().getBillType().getCode(), billDetail));
			balance.setHsName(bill.getHsName());
			balance.setHsSpec(bill.getHsSpec());
			balance.setHsUnit(bill.getHsUnit());
			balance.setComplex(bill.getComplex());
			balance.setWareSet(bill.getWareSet());
			balance.setVersion(bill.getVersion());
			
			balance.setUnitConvert(bill.getUnitConvert());//wss:2010.07.20加上折算比例
			
			System.out.println("wss bill.getUnitConvert = " + bill.getUnitConvert());

			// alang屏蔽
			// balance.setMaterialType1(getMaterielTypeString(bill.getBillMaster().getBillType().getBillType()));
			balanceMap.put(key, balance);

		} else {
			balanceTemp.setPtAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getPtAmount())
					+ CommonUtils.getDoubleExceptNull(balance.getPtAmount()));
			balanceTemp.setHsAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getHsAmount())
					+ CommonUtils.getDoubleExceptNull(balance.getHsAmount()));
			balanceMap.put(key, balanceTemp);
			System.out.println("wss balanceTemp.getPtAmount :"
					+ balanceTemp.getPtAmount());
//			System.out.println("wss balanceTemp.getHsAmount :"
//					+ balanceTemp.getHsAmount());
//			balanceMap.put(key, balanceTemp);
		}

	}
	/**
	 * 盘点CheckBalance 转化为 当日结存
	 * 
	 * @param balanceMap
	 * @param cb
	 * @param inOut
	 * @param materielType
	 * @param key
	 * @author wss
	 */
	private void transferBalanceFromCheckBalance(
			Map<String, TempThatDayBalance> balanceMap, CheckBalance cb,
			String materielType, String key, boolean ishsTaotal) {
		// 单据临时实体
		TempThatDayBalance balanceTemp = null;
		TempThatDayBalance balance = new TempThatDayBalance();

		balance.setPtAmount(cb.getCheckAmount());//盘点工厂数量
		balance.setHsAmount(cb.getHsAmount());//折算报关数量

		System.out.println("wss key :" + key);
		System.out.println("wss ptAmount :" + balance.getPtAmount());
		System.out.println("wss hsAmount :" + balance.getHsAmount());
		
		if ((balanceTemp = balanceMap.get(key)) == null) {

			balance.setNote(cb.getNote());

			if (!ishsTaotal) {// 如果未选 按报关名称汇总
				balance.setPtPart(cb.getPtNo());
				balance.setPtName(cb.getPtName());
				balance.setPtSpec(cb.getPtSpec());
				CalUnit ptUnit = new CalUnit();
				ptUnit.setName(cb.getPtUnitName());
				balance.setPtUnit(ptUnit);
			}

			balance.setMaterialType(materielType);
			balance.setHsName(cb.getName());
			balance.setHsSpec(cb.getSpec());
			Unit hsUnit = new Unit();
			hsUnit.setName(cb.getUnitName());
			balance.setHsUnit(hsUnit);
			balance.setComplex(cb.getComplex());
			balance.setWareSet(cb.getWareSet());
			
			balance.setUnitConvert(cb.getUnitConvert());//wss:2010.07.20加上折算比例
			
			System.out.println("wss bill.getUnitConvert = " + cb.getUnitConvert());

			// alang屏蔽
			// balance.setMaterialType1(getMaterielTypeString(bill.getBillMaster().getBillType().getBillType()));
			balanceMap.put(key, balance);

		} else {
			balanceTemp.setPtAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getPtAmount())
					+ CommonUtils.getDoubleExceptNull(balance.getPtAmount()));
			balanceTemp.setHsAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getHsAmount())
					+ CommonUtils.getDoubleExceptNull(balance.getHsAmount()));
			balanceMap.put(key, balanceTemp);
			System.out.println("wss balanceTemp.getPtAmount :"
					+ balanceTemp.getPtAmount());
			System.out.println("wss balanceTemp.getHsAmount :"
					+ balanceTemp.getHsAmount());
		}

	}

	
	/**
	 * 盘点折料CheckBalanceConvertMateriel 转化为 当日结存
	 * 
	 * @param balanceMap
	 * @param cb
	 * @param inOut
	 * @param materielType
	 * @param key
	 * @author wss2010.07.27
	 */
	private void transferBalanceFromCheckBalanceConvertMateriel(
			Map<String, TempThatDayBalance> balanceMap, CheckBalanceConvertMateriel cb,
			String materielType, String key, boolean ishsTaotal) {
		// 单据临时实体
		TempThatDayBalance balanceTemp = null;
		TempThatDayBalance balance = new TempThatDayBalance();

		balance.setPtAmount(cb.getCheckAmount());//盘点工厂数量
		balance.setHsAmount(cb.getHsAmount());//折算报关数量

		System.out.println("wss key :" + key);
		System.out.println("wss ptAmount :" + balance.getPtAmount());
		System.out.println("wss hsAmount :" + balance.getHsAmount());
		
		if ((balanceTemp = balanceMap.get(key)) == null) {

			balance.setNote(cb.getNote());

			if (!ishsTaotal) {// 如果未选 按报关名称汇总
				balance.setPtPart(cb.getPtNo());
				balance.setPtName(cb.getPtName());
				balance.setPtSpec(cb.getPtSpec());
				balance.setPtUnit(cb.getPtUnit());
			}

			balance.setMaterialType(materielType);
			balance.setHsName(cb.getName());
			balance.setHsSpec(cb.getSpec());

			balance.setHsUnit(cb.getHsUnit());
			balance.setComplex(cb.getComplex());
			balance.setWareSet(cb.getWareSet());
			
			balance.setUnitConvert(cb.getUnitConvert());//wss:2010.07.20加上折算比例
			
			System.out.println("wss bill.getUnitConvert = " + cb.getUnitConvert());

			// alang屏蔽
			// balance.setMaterialType1(getMaterielTypeString(bill.getBillMaster().getBillType().getBillType()));
			balanceMap.put(key, balance);

		} else {
			balanceTemp.setPtAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getPtAmount())
					+ CommonUtils.getDoubleExceptNull(balance.getPtAmount()));
			balanceTemp.setHsAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getHsAmount())
					+ CommonUtils.getDoubleExceptNull(balance.getHsAmount()));
			balanceMap.put(key, balanceTemp);
			System.out.println("wss balanceTemp.getPtAmount :"
					+ balanceTemp.getPtAmount());
			System.out.println("wss balanceTemp.getHsAmount :"
					+ balanceTemp.getHsAmount());
		}

	}
	/**
	 * 判断当前单据是进／销项 进返回1，出返回-1，否则为0
	 * 
	 * @param bill
	 * @param ishsTaotal
	 * @param isGroup
	 * @return
	 * @author chengsir wss修改
	 */
	private int isAddBillDetail(BillDetail bill, String billDetail) {
		String billTypeCode = bill.getBillMaster().getBillType().getCode();
		if (MaterielType.MATERIEL.equals(billDetail)) {// 料件
			if ("1001".equals(billTypeCode) || "1003".equals(billTypeCode)
					|| "1004".equals(billTypeCode)
					|| "1005".equals(billTypeCode)
					|| "1006".equals(billTypeCode)
					|| "1007".equals(billTypeCode)
					|| "1009".equals(billTypeCode)
					|| "1010".equals(billTypeCode)
					|| "1011".equals(billTypeCode)
					|| "1012".equals(billTypeCode)
					|| "1013".equals(billTypeCode)
					|| "1017".equals(billTypeCode)
					|| "1018".equals(billTypeCode)
			) {
				return 1;
			}
			if ("1101".equals(billTypeCode) || "1102".equals(billTypeCode)
					|| "1103".equals(billTypeCode)
					|| "1104".equals(billTypeCode)
					|| "1105".equals(billTypeCode)
					|| "1106".equals(billTypeCode)
					|| "1107".equals(billTypeCode)
					|| "1108".equals(billTypeCode)
					|| "1110".equals(billTypeCode)
					|| "1112".equals(billTypeCode)
					|| "1113".equals(billTypeCode)
					|| "1115".equals(billTypeCode)// 1115海关批准内销2010.06.29添加
					|| "5001".equals(billTypeCode)//残次品（料件部分）
			) {
				return -1;
			}
		} else if (MaterielType.FINISHED_PRODUCT.equals(billDetail)) { // 成品
			if ("2001".equals(billTypeCode) || "2002".equals(billTypeCode)
					|| "2003".equals(billTypeCode)
					|| "2004".equals(billTypeCode)
					|| "2005".equals(billTypeCode)
					|| "2008".equals(billTypeCode)
					|| "2009".equals(billTypeCode)
					|| "2015".equals(billTypeCode)
					|| "2017".equals(billTypeCode)) {
				return 1;
			}
			if ("2101".equals(billTypeCode) || "2102".equals(billTypeCode)
					|| "2103".equals(billTypeCode)
					|| "2104".equals(billTypeCode)
					|| "2105".equals(billTypeCode)
					|| "2106".equals(billTypeCode)
					|| "2107".equals(billTypeCode)
					|| "2108".equals(billTypeCode)
					|| "2109".equals(billTypeCode)
					|| "2113".equals(billTypeCode)
					|| "2114".equals(billTypeCode)
					|| "5001".equals(billTypeCode)//残次品（成品部分）

			) {
				return -1;
			}
		} else if (MaterielType.BAD_PRODUCT.equals(billDetail)) { // 残次品
			if ("5001".equals(billTypeCode) || "5002".equals(billTypeCode)
					|| "5003".equals(billTypeCode)
					|| "5005".equals(billTypeCode)) {
				return 1;
			}
			if ("5101".equals(billTypeCode) || "5102".equals(billTypeCode)
					|| "5103".equals(billTypeCode)) {
				return -1;
			}
		} else if (MaterielType.REMAIN_MATERIEL.equals(billDetail)) { // 边角料
			if ("6001".equals(billTypeCode) || "6002".equals(billTypeCode)
					|| "6003".equals(billTypeCode)
					|| "6004".equals(billTypeCode)
					|| "6005".equals(billTypeCode)) {
				return 1;
			}
			if ("6101".equals(billTypeCode) || "6102".equals(billTypeCode)
					|| "6103".equals(billTypeCode)
					|| "6104".equals(billTypeCode)
					|| "6105".equals(billTypeCode)) {
				return -1;
			}
		}
		return 0;
	}

	// getCurrentBillDetail方法获取key
	private String getBillDetailKey(BillDetail bill, Boolean ishsTaotal,
			Boolean isGroup) {
		String key = "";
		key = bill.getPtPart() + "/" + bill.getPtName();
		return key;
	}

	/**
	 * 如果选择了 报关名称汇总 或者 仓库分组 key的变化
	 * 
	 * @param balance
	 * @param ishsTaotal
	 * @param isGroup
	 * @return
	 * @author chengsir wss修改
	 */
	private String getBillDetailKeyGroup(TempThatDayBalance balance,
			Boolean ishsTaotal, Boolean isGroup) {
		String key = "";
		// 如果按报关名称汇总
		if (ishsTaotal) {
			key += balance.getHsName() == null ? "" : balance.getHsName();
		}
		// 如果按仓库分组
		if (isGroup) {
			if (!ishsTaotal) {// 如果按仓库分组但双不按报关名称汇总
				key = getBalanceTempKey(balance);
			}
			key += "/" + balance.getWareSet() == null ? "" : balance
					.getWareSet().getCode();
		}
		return key;
	}

	// 获取当日结存 key
	private String getBalanceTempKey(TempThatDayBalance balance) {
		return balance.getPtPart() + "/" + balance.getPtName() + "/"
				+ balance.getPtSpec();
	}

	// getCurrentBillDetailBom方法获取key
	private String getBillDetailBomKey(BillDetail bill, Boolean isptTaotal,
			Boolean ishsTaotal) {
		String key = "";
		if (isptTaotal) {
			key += bill.getPtName() == null ? "" : bill.getPtName();
		}
		if (ishsTaotal) {
			key += "/" + bill.getHsName() == null ? "" : bill.getHsName();
		}
		return key;
	}

	/**
	 * 结存与盘点差额表
	 * 
	 * @param request
	 * @param conditions条件
	 * @param orderBy排列
	 * @param billDetail物料类型
	 * @param ishsTaotal
	 * @param isShowLess
	 * @param isGroup分组
	 * @param date报表日期
	 * @return List<CurrentBillDetailPanDian>
	 * @author wss
	 */
	public List getCurrentBillDetailPanDian(List<Condition> conditionsBill,
			List<Condition> conditionsCheck, String orderBy, String billDetail,
			Boolean ishsTaotal, Boolean isShowLess, Boolean isGroup, Date date) {

		// 第一步：查询当日结存
		List<TempThatDayBalance> listBD = new ArrayList<TempThatDayBalance>();

		// 是不是要折料的
		boolean isConvert = true;

		if ("outSource".equals(billDetail)){
			listBD = getCurrentBillDetailNew(conditionsBill, orderBy, billDetail,
					ishsTaotal, false, isGroup);
			; // 在产品，外发加工，残次品(未按额外条件分组或筛选 )
		} else {
			listBD = getCurrentBillDetail(conditionsBill, orderBy, billDetail,
					ishsTaotal, false, isGroup,false,null); // 料件，成品(未按额外条件分组或筛选 )
			isConvert = false;
		}
		System.out.println("wss查询当日结存表有没有数据？？？为多少？？：" + listBD.size());

		// 第二步：查询盘点
		List<CheckBalance> listPD = getCurrentCheckBalance(conditionsCheck,
				billDetail, date);
		System.out.println("wss查询盘点有没有数据？？？为多少？？：" + listPD.size());

		// 第三步：用Map装下三种情况：1.有帐存，有盘点；2.有帐存，未盘点；3.未帐存，有盘点
		Map<String, TempCurrentBillDetailPanDian> hm = new HashMap();

		// 1.装下当时结存
		for (int i = 0; i < listBD.size(); i++) {
			TempCurrentBillDetailPanDian bdpd = new TempCurrentBillDetailPanDian();

			TempThatDayBalance balance = (TempThatDayBalance) listBD.get(i);

			bdpd.setPtNo(balance.getPtPart());
			bdpd.setPtName(balance.getPtName() == null ? "" : balance
					.getPtName());
			bdpd.setPtSpec((balance.getPtSpec() == null ? "" : balance
					.getPtSpec()));
			bdpd.setPtNameSpec(bdpd.getPtName() + "/" + bdpd.getPtSpec());

			bdpd.setPtUnitName(balance.getPtUnit() == null ? null : balance
					.getPtUnit().getName());
			bdpd.setPtAmount(balance.getPtAmount()); // 工厂帐存数量
			bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
					.getPtCheckAmount())
					- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount()));// 工厂数量差额
			bdpd.setComplex(balance.getComplex());
			bdpd.setHsName(balance.getHsName() == null ? "" : balance
					.getHsName());
			bdpd.setHsSpec(balance.getHsSpec() == null ? "" : balance
					.getHsSpec());
			bdpd.setHsNameSpec(bdpd.getHsName() + "/" + bdpd.getHsSpec());

			bdpd.setHsUnitName(balance.getHsUnit() == null ? null : balance
					.getHsUnit().getName());
			bdpd.setHsAmount(balance.getHsAmount()); // 报关帐存数量
			bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
					.getHsCheckAmount())
					- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount())); // 报关数量差额
			bdpd.setMaterialType(balance.getMaterialType());// 类型
			bdpd.setWareSet(balance.getWareSet());// 仓库

			String key = getPanDianBalanceKey(balance, ishsTaotal, isGroup,
					isConvert);// 根据查询情况获取key
			// String key = balance.getPtPart() + "/" +
			// balance.getWareSet().getName();// 工厂料号与仓库名字作为key

			System.out.println("wss key:" + key);
			// System.out.println("wss ptAmount:" + bdpd.getPtAmount());
			// System.out.println("wss hsAmount:" + bdpd.getHsAmount());
			hm.put(key, bdpd);
		}

		// 2.装下盘点
		for (int i = 0; i < listPD.size(); i++) {
			CheckBalance cb = listPD.get(i);
			String ptNo = cb.getPtNo() == null ? "" : cb.getPtNo();
			String wareSetName = cb.getWareSet() == null ? "" : (cb
					.getWareSet().getName() == null ? "" : cb.getWareSet()
					.getName());
			// String key = ptNo + "/" + wareSetName;
			String key = getPanDianCheckBalanceKey(cb, ishsTaotal, isGroup,
					isConvert);
			System.out.println("wss pandian key:" + key);
			TempCurrentBillDetailPanDian bdpd;

			// 如果有对应盘点
			if ((bdpd = hm.get(key)) != null) {
				bdpd.setPtCheckAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						+ CommonUtils.getDoubleExceptNull(cb.getCheckAmount()));// 工厂盘点数量
				
				bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount())); // 重算工厂数量差额
				
				bdpd.setHsCheckAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						+ CommonUtils.getDoubleExceptNull(cb.getHsAmount())); // 报关盘点数量
				
				bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount()));// 重算报关数量差额

			}
			// 只有盘点没结存
			else {
				bdpd = new TempCurrentBillDetailPanDian();

				bdpd.setPtNo(cb.getPtNo());//这个是一定不会为null

				bdpd.setPtName(cb.getPtName() == null ? "" : cb.getPtName());
				bdpd.setPtSpec(cb.getPtSpec() == null ? "" : cb.getPtSpec());
				bdpd.setPtNameSpec(bdpd.getPtName() + "/" + bdpd.getPtSpec()); // 工厂名称规格

				bdpd.setPtUnitName(cb.getPtUnitName());
				bdpd.setPtCheckAmount(cb.getCheckAmount()); // 工厂盘点数量
				bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount())); // 工厂数量差额

				bdpd.setHsName(cb.getName() == null ? "" : cb.getName());
				bdpd.setHsSpec(cb.getSpec() == null ? "" : cb.getSpec());

				bdpd.setHsNameSpec(bdpd.getHsName() + "/" + bdpd.getHsSpec());

				bdpd.setHsUnitName(cb.getUnitName());
				bdpd.setHsCheckAmount(cb.getHsAmount()); // 报关盘点数量
				bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount())); // 报关数量差额
				bdpd.setMaterialType(CheckMyselfUntil.getCheckBalanceType(cb)); // 类型
				bdpd.setWareSet(cb.getWareSet());// 仓库

				hm.put(key, bdpd);

			}
		}

		System.out.println("hm 中有值吗？ 大小为 = " + hm.size());

		List<TempCurrentBillDetailPanDian> resultList = new ArrayList(hm
				.values());
		List<TempCurrentBillDetailPanDian> rs = new ArrayList();

//		Comparator comp = new Comparator() {
//			public int compare(Object o1, Object o2) {
//				TempCurrentBillDetailPanDian t1 = (TempCurrentBillDetailPanDian) o1;
//				TempCurrentBillDetailPanDian t2 = (TempCurrentBillDetailPanDian) o2;
//
//				int ptP = (t1.getPtNo() == null ? "0" : t1.getPtNo())
//						.compareTo((t2.getPtNo() == null ? "0" : t2.getPtNo()));// 工厂工厂料号
//				int hsC;// 报关编码
//				int wC;// 仓库编码
//				if (ptP > 0) {
//					return 1;
//				} else if (ptP < 0) {
//					return -1;
//				} else if ((hsC = t1.getComplex().getCode().compareTo(
//						t2.getComplex().getCode())) > 0) {
//					return 1;
//				} else if (hsC < 0) {
//					return -1;
//				} else if ((wC = t1.getWareSet().getCode().compareTo(// 仓库
//						t2.getWareSet().getCode())) > 0) {
//					return 1;
//				} else if (wC < 0) {
//					return -1;
//				} else {
//					return 0;
//				}
//			}
//		};
//		Collections.sort(resultList, comp);暂时屏蔽先

		// 显示负数结存
		if (isShowLess) {
			for (TempCurrentBillDetailPanDian pd : resultList) {
				if (ishsTaotal ? (pd.getHsAmount() != null && pd.getHsAmount() < 0 ):
						(pd.getPtAmount() != null && pd.getPtAmount() < 0)) {
					rs.add(pd);
				}
			}
			return rs;
		} else {
			return resultList;
		}
	}

	
	/**
	 * 结存与盘点差额表 （外发加工部分）
	 * 
	 * @param request
	 * @param conditions条件
	 * @param orderBy排列
	 * @param billDetail物料类型
	 * @param ishsTaotal
	 * @param isShowLess
	 * @param isGroup分组
	 * @param date报表日期
	 * @return List<CurrentBillDetailPanDian>
	 * @author wss
	 */
	public List getOutSourcePanDian(List<Condition> conditionsBill,
			List<Condition> conditionsCheck, String orderBy, String billDetail,
			Boolean ishsTaotal, Boolean isShowLess, Boolean isGroup,
			List<Condition> conditionsMateriel, Date date) {

		// 第一步：查询外发当日结存
		List<TempThatDayBalance> listBD = new ArrayList<TempThatDayBalance>();
		
		listBD = getCurrentBillDetailOutSource(conditionsBill, 
														orderBy, 
														"outSource",
														ishsTaotal, 
														false, 
														isGroup,
														conditionsMateriel,
														false,
														null);
			
		System.out.println("wss 外发listBD.size ：" + listBD.size());
		

		// 第二步：查询外发盘点
		conditionsCheck.add(new Condition("and", null, "kcType", "=", "4",null));
		
		List<CheckBalanceConvertMateriel> listCheckM = casDao.commonSearch("", "CheckBalanceConvertMateriel",
																		conditionsCheck, "", "");
		System.out.println("wss 外发listCheckM.size: " + listCheckM.size());

		// 第三步：用Map装下三种情况：1.有帐存，有盘点；2.有帐存，未盘点；3.未帐存，有盘点
		Map<String, TempCurrentBillDetailPanDian> hm = new HashMap();

		// 1.装下当日结存
		for (int i = 0; i < listBD.size(); i++) {
			TempCurrentBillDetailPanDian bdpd = new TempCurrentBillDetailPanDian();

			TempThatDayBalance balance = (TempThatDayBalance) listBD.get(i);

			bdpd.setPtNo(balance.getPtPart());
			bdpd.setPtName(balance.getPtName() == null ? "" : balance
					.getPtName());
			bdpd.setPtSpec((balance.getPtSpec() == null ? "" : balance
					.getPtSpec()));
			bdpd.setPtNameSpec(bdpd.getPtName() + "/" + bdpd.getPtSpec());

			bdpd.setPtUnitName(balance.getPtUnit() == null ? null : balance
					.getPtUnit().getName());
			bdpd.setPtAmount(balance.getPtAmount()); // 工厂帐存数量
			bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
					.getPtCheckAmount())
					- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount()));// 工厂数量差额
			bdpd.setComplex(balance.getComplex());
			bdpd.setHsName(balance.getHsName() == null ? "" : balance
					.getHsName());
			bdpd.setHsSpec(balance.getHsSpec() == null ? "" : balance
					.getHsSpec());
			bdpd.setHsNameSpec(bdpd.getHsName() + "/" + bdpd.getHsSpec());

			bdpd.setHsUnitName(balance.getHsUnit() == null ? null : balance
					.getHsUnit().getName());
			bdpd.setHsAmount(balance.getHsAmount()); // 报关帐存数量
			bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
					.getHsCheckAmount())
					- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount())); // 报关数量差额
			bdpd.setMaterialType(balance.getMaterialType());// 类型
			bdpd.setWareSet(balance.getWareSet());// 仓库

			String key = getPanDianBalanceKey(balance, ishsTaotal, isGroup,
					false);// 根据查询情况获取key

			System.out.println("wss key:" + key);
			hm.put(key, bdpd);
		}

		// 2.装下盘点
		for (int i = 0; i < listCheckM.size(); i++) {
			CheckBalanceConvertMateriel cb = listCheckM.get(i);
			String ptNo = cb.getPtNo() == null ? "" : cb.getPtNo();
			String wareSetName = cb.getWareSet() == null ? "" : (cb
					.getWareSet().getName() == null ? "" : cb.getWareSet()
					.getName());
			
			String key = getCheckBalanceConvertMaterielKey(cb, ishsTaotal, isGroup);
			
			System.out.println("wss pandian key:" + key);
			TempCurrentBillDetailPanDian bdpd;

			// 如果有对应盘点
			if ((bdpd = hm.get(key)) != null) {
				bdpd.setPtCheckAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						+ CommonUtils.getDoubleExceptNull(cb.getCheckAmount()));// 工厂盘点数量
				
				bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount())); // 重算工厂数量差额
				
				bdpd.setHsCheckAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						+ CommonUtils.getDoubleExceptNull(cb.getHsAmount())); // 报关盘点数量
				
				bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount()));// 重算报关数量差额
				hm.put(key, bdpd);

			}
			// 只有盘点没结存
			else {
				bdpd = new TempCurrentBillDetailPanDian();

				bdpd.setPtNo(cb.getPtNo());//这个是一定不会为null

				bdpd.setPtName(cb.getPtName() == null ? "" : cb.getPtName());
				bdpd.setPtSpec(cb.getPtSpec() == null ? "" : cb.getPtSpec());
				bdpd.setPtNameSpec(bdpd.getPtName() + "/" + bdpd.getPtSpec()); // 工厂名称规格

				bdpd.setPtUnitName(cb.getPtUnit()== null ? "": cb.getPtUnit().getName());
				bdpd.setPtCheckAmount(cb.getCheckAmount()); // 工厂盘点数量
				bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount())); // 工厂数量差额

				bdpd.setHsName(cb.getName() == null ? "" : cb.getName());
				bdpd.setHsSpec(cb.getSpec() == null ? "" : cb.getSpec());

				bdpd.setHsNameSpec(bdpd.getHsName() + "/" + bdpd.getHsSpec());

				bdpd.setHsUnitName(cb.getHsUnit() == null ? "": cb.getHsUnit().getName());
				bdpd.setHsCheckAmount(cb.getHsAmount()); // 报关盘点数量
				bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount())); // 报关数量差额
				bdpd.setMaterialType("0".equals(cb.getLjCpMark()) ? "料件" 
												:"1".equals(cb.getLjCpMark()) ? "成品":"半成品"); // 类型
				bdpd.setWareSet(cb.getWareSet());// 仓库

				hm.put(key, bdpd);

			}
		}

		System.out.println("hm 中有值吗？ 大小为 = " + hm.size());

		List<TempCurrentBillDetailPanDian> resultList = new ArrayList(hm
				.values());
		List<TempCurrentBillDetailPanDian> rs = new ArrayList();

//		Comparator comp = new Comparator() {
//			public int compare(Object o1, Object o2) {
//				TempCurrentBillDetailPanDian t1 = (TempCurrentBillDetailPanDian) o1;
//				TempCurrentBillDetailPanDian t2 = (TempCurrentBillDetailPanDian) o2;
//
//				int ptP = (t1.getPtNo() == null ? "0" : t1.getPtNo())
//						.compareTo((t2.getPtNo() == null ? "0" : t2.getPtNo()));// 工厂工厂料号
//				int hsC;// 报关编码
//				int wC;// 仓库编码
//				if (ptP > 0) {
//					return 1;
//				} else if (ptP < 0) {
//					return -1;
//				} else if ((hsC = t1.getComplex().getCode().compareTo(
//						t2.getComplex().getCode())) > 0) {
//					return 1;
//				} else if (hsC < 0) {
//					return -1;
//				} else if ((wC = t1.getWareSet().getCode().compareTo(// 仓库
//						t2.getWareSet().getCode())) > 0) {
//					return 1;
//				} else if (wC < 0) {
//					return -1;
//				} else {
//					return 0;
//				}
//			}
//		};
//		Collections.sort(resultList, comp); 屏蔽先

		// 显示负数结存
		if (isShowLess) {
			for (TempCurrentBillDetailPanDian pd : resultList) {
				if (ishsTaotal ? (pd.getHsAmount() != null && pd.getHsAmount() < 0 ):
						(pd.getPtAmount() != null && pd.getPtAmount() < 0)) {
					rs.add(pd);
				}
			}
			return rs;
		} else {
			return resultList;
		}
	}
	/**
	 * 结存与盘点差额表 结存 key
	 * 
	 * @author wss
	 */
	private String getPanDianBalanceKey(TempThatDayBalance balance,
			boolean ishsTaotal, boolean isGroup, boolean isConvert) {

		// 工厂料号 + 名称 + 规格
		String key1 = balance.getPtPart() + "/" + balance.getPtName() + "/"
				+ balance.getPtSpec();

		// 报关编码 + 报关名称 + 报关规格
		String key2 = balance.getComplex() == null ? "": balance.getComplex().getCode() + "/"
				+ balance.getHsName() + "/" + balance.getHsSpec();

		String key = "";
		if (ishsTaotal) {
			key = key2;
		} else {
			key = key1 + (isConvert ? "" : "/" + key2);
		}

		if (isGroup) {
			key += "/" + balance.getWareSet().getCode();
		}
		return key;
	}

	/**
	 * 结存与盘点差额表 盘点 key
	 * 
	 * @author wss
	 */
	private String getPanDianCheckBalanceKey(CheckBalance cb,
			boolean ishsTaotal, boolean isGroup, boolean isConvert) {

		String key1 = cb.getPtNo() + "/" + cb.getPtName() + "/"
				+ cb.getPtSpec();
		String key2 = cb.getComplex() == null ? "": cb.getComplex().getCode() + "/" + cb.getName() + "/"
				+ cb.getSpec();
		String key = "";

		if (ishsTaotal) {
			key = key2;
		} else {
			key = key1 + (isConvert ? "" : "/" + key2);
		}
		if (isGroup) {
			key += "/" + cb.getWareSet().getCode();
		}
		return key;
	}

	// /**
	// * 盘点表依条件 分类
	// * @param pd
	// * @param ishsTaotal
	// * @param isGroup
	// * @author wss
	// * @return
	// */
	// private String getKeyForPanDian(TempCurrentBillDetailPanDian pd,boolean
	// ishsTaotal,boolean isGroup){
	// String key = "";
	// key = pd.getPtNo() + "/" + pd.getPtNameSpec();
	//		
	// if(ishsTaotal){
	// key = pd.getComplex().getCode() + "/" + pd.getHsNameSpec();
	// }
	// if(isGroup){
	// key += "/" + pd.getWareSet().getCode();
	// }
	// return key;
	// }
	//	

	/**
	 * 查询盘点
	 * 
	 * @param billDetail料件类型
	 * @param date报表日期
	 * @return
	 * @author wss
	 */
	public List getCurrentCheckBalance(List<Condition> conditions,
			String billDetail, Date date) {
		// 组织查询条件
		// 类型
		String type = "";
		if (MaterielType.MATERIEL.equals(billDetail)) {
			type = "0"; // 料件
		} else if (MaterielType.FINISHED_PRODUCT.equals(billDetail)) {
			type = "1"; // 成品
		} else if ("currentTotal".equals(billDetail)) {
//			type = "3"; // 在产品
			type = "2"; // 在产品
		} else if (MaterielType.BAD_PRODUCT.equals(billDetail)) {
			type = "5"; // 残次品
		} else if ("outSource".equals(billDetail)) {
			type = "4"; // 外发加工
		}

		// 类型
		conditions.add(new Condition("and", null, "kcType", "=", type, null));
		
//		if ("1".equals(type)) {// 产成品不能折料
//			conditions.add(new Condition("and", null, "ljCpMark", "=", "1",
//					null));
//		} else {// 其它必须折料
//			conditions.add(new Condition("and", null, "ljCpMark", "=", "0",
//					null));
//		}
		// // 时间
		// conditions
		// .add(new Condition("and", null, "checkDate", "=", date, null));

		// String CheckBalanceName = "CheckBalance";
		List<CheckBalance> list = casDao.commonSearch("", "CheckBalance",
				conditions, "", "");

		return list;
	}

	/**
	 * 收货、送货明细表
	 * 
	 * @param conditions
	 * @return
	 * @author wss
	 */
	public List getRecvOrSendDetail(List<Condition> conditions,
			String materialType, String billDetail) {
		List lsResult = new ArrayList();
		List list = this.findRecvSendDetailInfo(conditions, materialType,
				billDetail);

		for (int i = 0; i < list.size(); i++) {
			BillDetail bd = (BillDetail) list.get(i);
			TempTransferRecvSendDetailInfo tempInfo = new TempTransferRecvSendDetailInfo();
			tempInfo.setBillNo(bd.getBillMaster().getBillNo());
			tempInfo.setValidDate(bd.getBillMaster().getValidDate());
			tempInfo.setPtPart(bd.getPtPart());
			tempInfo.setPtName(bd.getPtName());
			tempInfo.setPtSpec(bd.getPtSpec());
			tempInfo.setPtUnit(bd.getPtUnit());
			tempInfo.setPoSoBillNo(bd.getOderBillNo());
			if (MaterielType.MATERIEL.equals(materialType)) {// 结转进口，结转料件退货单,已送货未结转期初单
				if ("1004".equals(bd.getBillMaster().getBillType().getCode())
						|| "1015".equals(bd.getBillMaster().getBillType()
								.getCode())) {
					tempInfo.setPtRecvAmount(bd.getPtAmount());
					tempInfo.setHsRecvAmount(bd.getHsAmount());
				} else if ("1106".equals(bd.getBillMaster().getBillType()
						.getCode())) {
					tempInfo.setPtBackAmount(bd.getPtAmount());
					tempInfo.setHsBackAmount(bd.getHsAmount());
				}
			} else {// 结转成品退货单，结转出口,已送货未结转期初单
				if ("2102".equals(bd.getBillMaster().getBillType().getCode())
						|| "2011".equals(bd.getBillMaster().getBillType()
								.getCode())) {
					tempInfo.setPtRecvAmount(bd.getPtAmount());
					tempInfo.setHsRecvAmount(bd.getHsAmount());
				} else if ("2009".equals(bd.getBillMaster().getBillType()
						.getCode())) {
					tempInfo.setPtBackAmount(bd.getPtAmount());
					tempInfo.setHsBackAmount(bd.getHsAmount());
				}
			}
			tempInfo.setComplex(bd.getComplex());
			tempInfo.setHsName(bd.getHsName());
			tempInfo.setHsSpec(bd.getPtSpec());
			tempInfo.setHsUnit(bd.getHsUnit());

			tempInfo.setEnvelopNo(bd.getBillMaster().getEnvelopNo());
			tempInfo.setCustomNo(bd.getCustomNo());

			lsResult.add(tempInfo);
		}
		
		Collections.sort(lsResult, new MyComparator()
		{

			public int compare(Object o1, Object o2) {
				TempTransferRecvSendDetailInfo t1 = (TempTransferRecvSendDetailInfo) o1;
				TempTransferRecvSendDetailInfo t2 = (TempTransferRecvSendDetailInfo) o2;
				Date d1 = t1.getValidDate();
				Date d2 = t2.getValidDate();
				
				if(d1 != null){
					if(d2 != null){
						return d1.compareTo(d2);
					}else{
						return 1;
					}
				}else{
					return -1;
				}
			}
		});
		
		return lsResult;
	}

	/**
	 * 查找收货\送货单据明细
	 * 
	 * @author wss
	 */
	public List findRecvSendDetailInfo(List<Condition> conditions,
			String materialType, String billDetail) {
		String[] billTypes = (MaterielType.MATERIEL.equals(materialType) ? new String[] {
				"1004", "1106", "1015" }// 结转进口、结转料件退货单、已收货未结转期初单
				: new String[] { "2009", "2102", "2011" });// 结转成品退货单、结转出口、已送货未结转期初单
		if (billTypes != null && billTypes.length > 0) {
			for (int i = 0; i < billTypes.length; i++) {
				if (i == 0) {
					// hsql += " and ( a.billMaster.billType.code = ? ";
					conditions
							.add(new Condition("and", "(",
									"billMaster.billType.code", "=",
									billTypes[i], null));
				} else if (i > 0 && i < billTypes.length - 1) {
					// hsql += " or  a.billMaster.billType.code = ? ";
					conditions
							.add(new Condition("or", null,
									"billMaster.billType.code", "=",
									billTypes[i], null));
				} else if (i == billTypes.length - 1) {
					// hsql += " or  a.billMaster.billType.code = ? ) ";
					conditions
							.add(new Condition("or", null,
									"billMaster.billType.code", "=",
									billTypes[i], ")"));
				}
				// parameters.add(billTypes[i]);
			}
		}

		List<BillDetail> isResult = casDao.commonSearch("", billDetail,
				conditions, "", "");

		return isResult;
	}

	/**
	 * 查询转厂报关的商品（三种手册通用）
	 * 
	 * @param isMaterial判断是否料件
	 *            ，true为料件
	 * @param lsContract是Contract型
	 *            ，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 * @author wss
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			List lsContract, int state, int currentType) {
		List<TempCustomsDeclarCommInfo> lsResult = new ArrayList<TempCustomsDeclarCommInfo>();

		// 查询相应合同转厂报关单的商品信息
		List list = this.casCheckDao.findCustomsDeclarationCommInfo(isMaterial,
				lsContract, state, currentType);
		System.out.println("list.size:" + list.size());

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			TempCustomsDeclarCommInfo commInfo = new TempCustomsDeclarCommInfo();
			commInfo.setSeqNum((Integer) objs[0]);
			if (objs[1] != null) {
				commInfo.setCode(objs[1].toString());
			}
			if (objs[2] != null) {
				commInfo.setName(objs[2].toString());
			}
			if (objs[3] != null) {
				commInfo.setSpec(objs[3].toString());
			}
			if (objs[4] != null) {
				commInfo.setUnitName(objs[4].toString());
			}
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 查询转厂报关相应的客户
	 * 
	 * @param isImport进口判断
	 *            ，true为进口
	 * @param lsContract是Contract型
	 *            ，合同备案表头
	 * @param state生效类型
	 * @return List 是customer型，存放了已报关的客户
	 * @author wss
	 */
	public List findCustomsDeclarationCustomer(boolean isImport,
			List lsContract, int state, int currentType) {
		return this.casCheckDao.findCustomsDeclarationCustomer(isImport,
				lsContract, state, currentType);
	}

	/**
	 * 进口料件结转明细isImport=true，出口成品结转明细表isImport=false
	 * 
	 * @param isMaterial判断是否料件
	 *            ，true为料件
	 * @param seqNum商品序号
	 * @param customer客户
	 * @param impExpType进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate结束日期
	 * @param contract合同备案表头
	 * @param state生效类型
	 * @return List 是TempTransferCustomsDeclarationCommInfo型，进出口转厂结转明细表
	 * @author wss
	 */
	public List findTransferCustomsDeclarationCommInfo(boolean isMaterial,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag, int currentType) {

		List<TempTransferCustomsDeclarationCommInfo> lsResult = new ArrayList<TempTransferCustomsDeclarationCommInfo>();

		List list = this.casCheckDao.findTransferCommInfoListContract(
				isMaterial, seqNum, code, name, customer, impExpType,
				beginDate, endDate, contracts, state, impExpFlag, currentType);
		System.out.println("list.size:" + list.size());

		Map<String, Double> map = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			TempTransferCustomsDeclarationCommInfo tempCommInfo = new TempTransferCustomsDeclarationCommInfo();
			Object[] objs = (Object[]) list.get(i);
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) objs[0];

			// 转厂出口
			if (!isMaterial) {
				// 电子化手册
				if (currentType == 0) {
					ContractExg exg = (ContractExg) objs[1];
					tempCommInfo
							.setProcessUnitPrice(exg.getProcessUnitPrice() == null ? 0.0
									: exg.getProcessUnitPrice());
					tempCommInfo
							.setContractUnitPrice(exg.getUnitPrice() == null ? 0.0
									: exg.getUnitPrice());
				}
				// 电子帐册
				if (currentType == 1) {
					EmsHeadH2kExg exg = (EmsHeadH2kExg) objs[1];
					// 电子帐册无加工单价
					// tempCommInfo
					// .setProcessUnitPrice(exg.getProcessUnitPrice() == null ?
					// 0.0
					// : exg.getProcessUnitPrice());
					tempCommInfo
							.setContractUnitPrice(exg.getDeclarePriceMo() == null ? 0.0
									: exg.getDeclarePriceMo());
				}
				// 电子手册
				if (currentType == 2) {
					DzscEmsExgBill exg = (DzscEmsExgBill) objs[1];
					tempCommInfo
							.setProcessUnitPrice(exg.getMachinPrice() == null ? 0.0
									: exg.getMachinPrice());
					tempCommInfo
							.setContractUnitPrice(exg.getPrice() == null ? 0.0
									: exg.getPrice());
				}

			}
			// 转厂进口
			else {
				// 电子化手册
				if (currentType == 0) {
					ContractImg img = (ContractImg) objs[1];
					tempCommInfo
							.setContractUnitPrice(img.getDeclarePrice() == null ? 0.0
									: img.getDeclarePrice());
				}
				// 电子帐册
				if (currentType == 1) {
					EmsHeadH2kImg img = (EmsHeadH2kImg) objs[1];
					tempCommInfo
							.setContractUnitPrice(img.getDeclarePrice() == null ? 0.0
									: img.getDeclarePrice());
				}
				// 电子手册
				if (currentType == 2) {
					DzscEmsImgBill img = (DzscEmsImgBill) objs[1];
					tempCommInfo
							.setContractUnitPrice(img.getPrice() == null ? 0.0
									: img.getPrice());
				}
			}

			try {
				PropertyUtils.copyProperties(tempCommInfo, commInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			double addupAmount = map.get(tempCommInfo.getCommSerialNo()
					.toString()) == null ? 0.0 : Double.parseDouble(map.get(
					tempCommInfo.getCommSerialNo().toString()).toString());
			Integer type = tempCommInfo.getBaseCustomsDeclaration()
					.getImpExpType();
			if (isMaterial) {
				addupAmount += (tempCommInfo.getCommAmount() == null ? 0.0
						: tempCommInfo.getCommAmount());

			} else {
				addupAmount -= (tempCommInfo.getCommAmount() == null ? 0.0
						: tempCommInfo.getCommAmount());
			}
			tempCommInfo.setCommAddUpAmount(addupAmount);

			// 币别？点解要这个？
			tempCommInfo.getBaseCustomsDeclaration().setCurrency(
					commInfo.getBaseCustomsDeclaration().getCurrency());
			// 为查算数量总计
			map.put(tempCommInfo.getCommSerialNo().toString(), addupAmount);
			lsResult.add(tempCommInfo);
		}
		return lsResult;
	}

	public void test() {
		List<BillDetail> list = casDao.commonSearch(
				"from BillDetailMateriel a", new Object[] {});
		for (BillDetail bill : list) {
			bill.setPtAmount(300d);
		}
	}

	/**
	 * 制单号耗用查询
	 * 
	 * @param conditions
	 * @param billDetail
	 * @param isFactoryName
	 * @param isCustomName
	 * @return hcl
	 */
	public List getWorkOrderBillDetail(List<Condition> conditionsBill,
			List<Condition> conditionsMaterial, String billDetail,
			boolean isFactoryName, boolean isCustomName) {
		// 按料件条件集合conditionsMaterial找出料件集合
		List<TempBomBillDetail> listAdd_ms = new ArrayList();
		boolean isAll = false;
		if (conditionsMaterial.size() == 0)
			isAll = true;
		Map<String, String> mapMaterial = getResultMaterielKey(conditionsMaterial);
		List<String> mList = null;
		if (mapMaterial != null) {
			mList = new ArrayList<String>(mapMaterial.values());
			System.out.println("mList.size()=" + mList.size());
		}

		// 找出所有原材料单据
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet left join fetch a.complex ";
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		String[] codes = new String[] { "1101", "1002", "1006" };
		conditionsBill.add(new Condition("and", null,
				"billMaster.billType.code", " in (", codes, ")"));
		List<BillDetail> list_ms = null;
		List<WorkOrderList> result = new ArrayList<WorkOrderList>();
		if (isFactoryName) {// 以后加此功能
			list_ms = casDao.commonSearch("", billDetailTableName,
					conditionsBill, "", leftOuter);

		} else {
			list_ms = casDao.commonSearch("", billDetailTableName,
					conditionsBill, "", leftOuter);

		}
		System.out.println("料件入库数：" + list_ms.size());
		// 判断料件
		if (list_ms != null) {
			for (int i = 0; i < list_ms.size(); i++) {
				if (mList == null) {
					listAdd_ms
							.add(getTempBomBillDetailMaterial(list_ms.get(i)));

				} else
					for (String s : mList) {
						System.out.println("keyy="+list_ms.get(i).getPtPart()+"/"
								+(list_ms.get(i).getComplex().getCode()==null?"":list_ms.get(i).getComplex().getCode())+"/"
								+ list_ms.get(i).getHsName()+"/"
								+ list_ms.get(i).getHsSpec()+"/"
								+list_ms.get(i).getHsUnit().getName());
						if (s.equals(list_ms.get(i).getPtPart()+"/"
								+(list_ms.get(i).getComplex().getCode()==null?"":list_ms.get(i).getComplex().getCode())+"/"
								+ list_ms.get(i).getHsName()+"/"
								+ list_ms.get(i).getHsSpec()+"/"
								+list_ms.get(i).getHsUnit().getName())) {
							listAdd_ms.add(getTempBomBillDetailMaterial(list_ms
									.get(i)));
						}
					}
			}
			for (TempBomBillDetail bill : listAdd_ms) {
				result.add(new WorkOrderList(bill));
			}
		}

		// 找所所有成品单据
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		conditionsBill.remove(conditionsBill.size() - 1);
		
		codes = new String[] { "2002", "2103" };
		conditionsBill.add(new Condition("and", null,
			"billMaster.billType.code", " in (", codes, ")"));
		list_ms = casDao.commonSearch("", billDetailTableName, conditionsBill,
				"", leftOuter);
		
		System.out.println("成品入库数：" + list_ms.size());
		// 找所所有半成品单据跟成品单据加一起
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
		conditionsBill.remove(conditionsBill.size() - 1);
		codes = new String[] { "4002", "4003", "4004", "4006", "4102",
				"4103", "4104", "4106", };
		conditionsBill.add(new Condition("and", null,
			"billMaster.billType.code", " in (", codes, ")"));
		list_ms.addAll(casDao.commonSearch("", billDetailTableName,
				conditionsBill, "", leftOuter));
		System.out.println("成品+半成品入库数：" + list_ms.size());

		// 半成品、成品一起折料
		List<TempBomBillDetail> listAdd = new ArrayList<TempBomBillDetail>();
		List<TempBomRelation> boms = null;
		String bomKey = null;
		Map<String, List<TempBomRelation>> bomMap = new HashMap<String, List<TempBomRelation>>();

		for (BillDetail bill : list_ms) {
			bomKey = bill.getPtPart();
			boms = bomMap.get(bomKey);
			if (boms != null) {
			} else {
				boms = this.getBomRelation(bill, mapMaterial, isAll);
				bomMap.put(bomKey, boms);
			}
			System.out.println("boms.size=" + boms.size());
			// 折料
			for (TempBomRelation bom : boms) {
				listAdd.add(setTempBomBillDetailFinished(bom, bill));
			}
		}

		BillDetail newBill = null;
		WorkOrderList wList = null;
		for (TempBomBillDetail bill : listAdd) {
			wList = new WorkOrderList(bill);
			result.add(wList);
		}
		System.out.println("总数：" + result.size());

		if (result.size() == 0)
			return new ArrayList();
		// LIST排序
		String s1 = null;
		String s2 = null;
		// 按制单号、料号、时间顺序排序的接口
		Comparator comp = new Comparator() {
			public int compare(Object o1, Object o2) {
				WorkOrderList p1 = (WorkOrderList) o1;
				WorkOrderList p2 = (WorkOrderList) o2;
				if (p1.getBill().getProductNo().compareTo(
						p2.getBill().getProductNo()) > 0)
					return 1;
				else if (p1.getBill().getProductNo().compareTo(
						p2.getBill().getProductNo()) < 0)
					return -1;
				else if (p1.getBill().getPtPart().compareTo(
						p2.getBill().getPtPart()) > 0)
					return 1;
				else if (p1.getBill().getPtPart().compareTo(
						p2.getBill().getPtPart()) < 0)
					return -1;
				else if (p1.getBill().getBillMaster().getValidDate().compareTo(
						p2.getBill().getBillMaster().getValidDate()) > 0)
					return 1;
				else if (p1.getBill().getBillMaster().getValidDate().compareTo(
						p2.getBill().getBillMaster().getValidDate()) < 0)
					return -1;
				else
					return 0;
				// return -1;
			}
		};
		Collections.sort(result, comp);
		double rsPtAmount = 0;
		double rsHsAmount = 0;
		WorkOrderList billList = result.get(0);
		for (int i = 0; i < result.size(); i++) {
			if ((!billList.getBill().getPtPart().equals(
					result.get(i).getBill().getPtPart()) && isFactoryName)
					|| ((!billList.getBill().getHsName().equals(
							result.get(i).getBill().getHsName()) && isCustomName))) {
				billList = result.get(i);
				rsHsAmount = 0;
				rsPtAmount = 0;
			}
			if (result.get(i).isIn()) {
				rsPtAmount += result.get(i).getPtIn() == null ? 0.0 : result
						.get(i).getPtIn();
				rsHsAmount += result.get(i).getHsIn() == null ? 0.0 : result
						.get(i).getHsIn();
			} else {
				rsPtAmount -= result.get(i).getPtOut() == null ? 0.0 : result
						.get(i).getPtOut();
				rsHsAmount -= result.get(i).getHsOut() == null ? 0.0 : result
						.get(i).getHsOut();
			}
			result.get(i).setPtAmount(rsPtAmount);
			result.get(i).setHsAmount(rsHsAmount);
		}

		return result;

	}

	/**
	 * 成品折算情况表
	 * 
	 * @param request
	 * @param conditions
	 * @param billDetail
	 * @param isptTaotal
	 * @param ishsTaotal
	 * @author 石小凯
	 * @return list<CurrentBillDetailBom>
	 */
	public List getCurrentBillDetailBom(List<Condition> conditions,
			String billDetail, Boolean isptTaotal, Boolean ishsTaotal,
			String PtNoBegin) {
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(billDetail);
		// 返回折算结果
		List<CurrentBillDetailBom> billBoms = new ArrayList();
		// 单据统计map
		Map<String, BillDetail> mapBill = new HashMap();
		// 查询单据
		List<BillDetail> list = casDao.commonSearch("", billDetailTableName,
				conditions, "", "");
		System.out.println("准备进行单据统计");
		System.out.println("成品数量:" + list.size());
		Iterator l = list.iterator();
		BillDetail b = null;
		while (l.hasNext()) {
			b = (BillDetail) l.next();
			System.out.println("成品名称：" + b.getPtName());
			System.out.println("" + b.getPtPart());
			System.out.println("" + b.getVersion());
		}
		List<MaterialBomDetail> boms = null;
		Double totalDosage = null;
		Double ptTotal = null;
		for (BillDetail bill : list) {
			// 取出成品的数量
			Double ptAmount = bill.getPtAmount();
			// 通过成品取得料件
			System.out.println("no:" + bill.getPtPart() + "  ver:"
					+ bill.getVersion());
			boms = casDao.getProductBom(bill);
			// 遍历料件..计算后加进最终LIST里
			System.out.println("料件对应成品料件数量：" + boms.size());
			for (MaterialBomDetail bom : boms) {
				System.out.println("进入料件LIST");

				if (null == bom.getUnitWaste()
						|| bom.getUnitWaste().doubleValue() == 0) {
					totalDosage = ptAmount;
				} else {
					totalDosage = bom.getUnitWaste() * ptAmount;
				}

				if (null == bom.getMateriel().getUnitConvert()
						|| bom.getMateriel().getUnitConvert().doubleValue() == 0) {
					ptTotal = totalDosage;
				} else {
					ptTotal = bom.getMateriel().getUnitConvert() * totalDosage;
				}

				System.out.println("总数量是：" + totalDosage);
				System.out.println("报关折算系数是："
						+ bom.getMateriel().getUnitConvert());
				System.out.println("报关总数量是:" + ptTotal);
				CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
				currentBillDetailBom.setValidDate(bill.getBillMaster()
						.getValidDate());
				currentBillDetailBom.setBillTypeName(bill.getBillMaster()
						.getBillType().getName());
				currentBillDetailBom
						.setBillNo(bill.getBillMaster().getBillNo());
				currentBillDetailBom.setPtPart(bill.getPtPart());
				currentBillDetailBom.setPtName(bill.getPtName());
				currentBillDetailBom.setPtSpec(bill.getPtSpec());
				currentBillDetailBom.setPtUnitName(bill.getPtUnit().getName());
				currentBillDetailBom.setPtAmount(bill.getPtAmount());
				currentBillDetailBom
						.setComplexCode(bill.getComplex().getCode());
				currentBillDetailBom.setHsName(bill.getHsName());
				currentBillDetailBom.setHsSpec(bill.getHsSpec());
				currentBillDetailBom.setHsUnitName(bill.getHsUnit().getName());
				currentBillDetailBom.setHsAmount(bill.getHsAmount());
				currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
				currentBillDetailBom.setFactoryName(bom.getMateriel()
						.getFactoryName());
				currentBillDetailBom.setFactorySpec(bom.getMateriel()
						.getFactorySpec());
				currentBillDetailBom.setCalUnitName(bom.getMateriel()
						.getCalUnit().getName());
				currentBillDetailBom.setMcomplexCode(bom.getMateriel()
						.getComplex().getCode());
				currentBillDetailBom.setMptName(bom.getMateriel().getPtName());
				currentBillDetailBom.setMptDeSpec(bom.getMateriel()
						.getPtDeSpec());
				currentBillDetailBom.setMptUnitName(bom.getMateriel()
						.getPtUnit().getName());
				currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
				currentBillDetailBom.setWaste(bom.getWaste());
				currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
				currentBillDetailBom.setTotalDosage(totalDosage);
				currentBillDetailBom.setUnitConvert(bom.getMateriel()
						.getUnitConvert());
				currentBillDetailBom.setPtTotal(ptTotal);
				currentBillDetailBom.setProductNo(bill.getProductNo());
				billBoms.add(currentBillDetailBom);
			}

		}

		// 统计折算信息
		return billBoms;
	}

	/**
	 * 半成品折料新方法
	 * 
	 * @param casCondition
	 * @param billDetail
	 * @return
	 * @author xiaokai
	 */
	public List getSemiCurrentBillDetailBom(CasConvert casConvert,
			String billDetail) {
		System.out.println("===========新方法里面=============");
		// 料件map
		Map<String, BillDetail> mMap = new HashMap();

		// 通过料件条件取出料件结果集
		Map<String, String> rsMap = getResultMaterielConvert(casConvert);

		// 用于缓存成品／半成品的BOM
		Map<String, List<MaterialBomDetail>> bomMap = new HashMap();
		BillDetail temp = null;
		String key = "";
		String ptPart = "";
		String leftOuter ="";
		// 成品查询结果
		List<BillDetail> ms = null;
		// 返回折算最终结果
		List<CurrentBillDetailBom> billBoms = new ArrayList();
		// 组织成品查询条件
		List<Condition> conditions = getConditionsConvert(casConvert, true);

		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType

				(MaterielType.FINISHED_PRODUCT);
		String[] codes = null;
		codes = new String[] { "2002", "2103", "2007", "2013" };
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes, ")"));
		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet left join fetch a.complex ";
		ms = casDao.commonSearch("", billDetailTableName, conditions, "", leftOuter);
		conditions.remove(conditions.size() - 1);
		List<MaterialBomDetail> boms = null;
		Double totalDosage = null;
		Double ptTotal = null;
		List<MaterialBomDetail> bomList = null;
		// 用于视别当前BOM是否要缓存
		boolean isSave = false;
		// List<MaterialBomDetail> list=null;
		for (BillDetail bill : ms) {
			// 遍历成品折BOM
			key = getMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();
			}
			Double ptAmount = bill.getPtAmount();
			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {

					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());
					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());
					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());
					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());
					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());
					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());
					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());
					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());
					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());
					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());

					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();

					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusName());
						currentBillDetailBom.setMptDeSpec(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusSpec());
						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null != f.getUnitConvert()) {
							currentBillDetailBom.setPtTotal(f.getUnitConvert()
									* totalDosage);
						} else {
							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add(currentBillDetailBom);
				}
			}
		}

		// 统计半成品
		billDetailTableName = BillUtil.getBillDetailTableNameByMaterielType

		(MaterielType.SEMI_FINISHED_PRODUCT);

		codes = new String[] { "4002", "4003", "4004", "4006",
				"4102", "4104", "4103", "4106" };
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes, ")"));
		ms = casDao.commonSearch("", billDetailTableName, conditions, "", leftOuter);
		for (BillDetail bill : ms) {
			// 取出成品的数量
			Double ptAmount = bill.getPtAmount();
			// 遍历成品折BOM
			key = getMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();
			}
			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {
					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());
					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());
					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());
					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());
					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());
					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());
					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());
					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());
					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());
					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());

					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();

					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusName());
						currentBillDetailBom.setMptDeSpec(f
								.getStatCusNameRelationHsn() == null ? "" : f
								.getStatCusNameRelationHsn().getCusSpec());
						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null != f.getUnitConvert()) {
							currentBillDetailBom.setPtTotal(f.getUnitConvert()
									* totalDosage);
						} else {
							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add(currentBillDetailBom);
				}
			}
		}
		// 统计残次品外发
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
		codes = new String[] { "5001" };
		conditions.add(new Condition("and", null, "billMaster.billType.code",
				" in (", codes, ")"));// 查询成品外发
		
		conditions.add(new Condition("and", null, "note",
				"=", "料件", null));
		ms = casDao.commonSearch("", billDetailTableName, conditions, "", leftOuter);

		for (BillDetail bill : ms) {
			// 取出成品的数量
			Double ptAmount = bill.getPtAmount();
			// 通过成品取得料件
			key = getMaterielKey(null, bill);
			bomList = bomMap.get(key);
			if (bomList != null) {
				boms = bomList;
				isSave = false;
			} else {
				boms = casDao.getProductBom(bill);
				bomMap.put(key, boms);
				isSave = true;
				bomList = new ArrayList();
			}
			for (MaterialBomDetail bom : boms) {
				key = getMaterielKey(bom.getMateriel(), null);
				if (rsMap == null || rsMap.containsKey(key)) {
					Double UnitUsed = CommonUtils.getDoubleExceptNull(bom
							.getUnitUsed());
					if (UnitUsed != 0) {
						totalDosage = UnitUsed * ptAmount;
					} else {
						totalDosage = ptAmount;
					}
					// 填充中间实体
					CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();

					currentBillDetailBom.setValidDate(bill.getBillMaster()
							.getValidDate());

					currentBillDetailBom.setBillTypeName(bill.getBillMaster()
							.getBillType() == null ? "" : bill.getBillMaster()
							.getBillType().getName());
					currentBillDetailBom.setBillNo(bill.getBillMaster()
							.getBillNo());
					currentBillDetailBom.setPtPart(bill.getPtPart());
					currentBillDetailBom.setPtName(bill.getPtName());
					currentBillDetailBom.setPtSpec(bill.getPtSpec());
					currentBillDetailBom
							.setPtUnitName(bill.getPtUnit() == null ? "" : bill
									.getPtUnit().getName());

					currentBillDetailBom.setPtAmount(bill.getPtAmount());
					currentBillDetailBom
							.setComplexCode(bill.getComplex() == null ? ""
									: bill.getComplex().getCode());

					currentBillDetailBom.setHsName(bill.getHsName());
					currentBillDetailBom.setHsSpec(bill.getHsSpec());
					currentBillDetailBom
							.setHsUnitName(bill.getHsUnit() == null ? "" : bill
									.getHsUnit().getName());

					currentBillDetailBom.setHsAmount(bill.getHsAmount());
					currentBillDetailBom.setPtNo(bom.getMateriel().getPtNo());
					currentBillDetailBom.setFactoryName(bom.getMateriel()
							.getFactoryName());

					currentBillDetailBom.setFactorySpec(bom.getMateriel()
							.getFactorySpec());

					currentBillDetailBom.setCalUnitName(bom.getMateriel()
							.getCalUnit() == null ? "" : bom.getMateriel()
							.getCalUnit().getName());

					currentBillDetailBom.setMcomplexCode(bom.getMateriel()
							.getComplex() == null ? "" : bom.getMateriel()
							.getComplex().getCode());

					currentBillDetailBom.setMptUnitName(bom.getMateriel()
							.getPtUnit() == null ? "" : bom.getMateriel()
							.getPtUnit().getName());

					currentBillDetailBom.setUnitWaste(bom.getUnitWaste());
					currentBillDetailBom.setWaste(bom.getWaste());
					currentBillDetailBom.setUnitUsed(bom.getUnitUsed());
					currentBillDetailBom.setTotalDosage(totalDosage);
					currentBillDetailBom.setProductNo(bill.getProductNo());
					Map<String, FactoryAndFactualCustomsRalation> fmapMap = casDao
							.getUnitConvert();
					FactoryAndFactualCustomsRalation f = fmapMap.get(bom
							.getMateriel().getPtNo());

					if (null != f) {
						currentBillDetailBom.setMptName(f
								.getStatCusNameRelationHsn() == null ? "" : f

						.getStatCusNameRelationHsn().getCusName());

						currentBillDetailBom.setMptDeSpec(f

						.getStatCusNameRelationHsn() == null ? "" : f

						.getStatCusNameRelationHsn().getCusSpec());

						currentBillDetailBom.setUnitConvert(f.getUnitConvert());
						if (null !=

						f.getUnitConvert()) {

							currentBillDetailBom.setPtTotal(f.getUnitConvert()

							* totalDosage);
						} else {

							currentBillDetailBom.setPtTotal(totalDosage);
						}
					}
					billBoms.add

					(currentBillDetailBom);
				}
			}
		}

		return billBoms;
	}

	/**
	 * 根据成品或半成品料号，版本号来获取BOM
	 * 
	 * @param productPtNo
	 * @param version
	 * @param listMaterielPtNo
	 * @return
	 */
	private List findMaterialBomDetail(String productPtNo, Integer version,
			List listMaterielPtNo) {
		List result = new ArrayList();
		List list = this.materialManageDao.findMaterialBomByDetailByptno(
				productPtNo, version);
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetail bomDetail = (MaterialBomDetail) list.get(i);
			if (listMaterielPtNo.size() > 0) {
				if (listMaterielPtNo
						.contains(bomDetail.getMateriel().getPtNo())) {
					result.add(bomDetail);
				}
			} else {
				result.add(bomDetail);
			}
		}
		return result;
	}

	/**
	 * 余鹏 报关级反查
	 * 
	 * @param beginProductPtNo
	 * @param endProductPtNo
	 * @param beginMaterialPtNo
	 * @param endMaterialPtNo
	 * @param beginProductComplexCode
	 * @param endProductComplexCode
	 * @param beginMaterialComplexCode
	 * @param endMaterialComplexCode
	 * @return
	 */
	public List getProductBillConvertMaterial(String beginProductPtNo,
			String endProductPtNo, String beginMaterialPtNo,
			String endMaterialPtNo, String beginProductComplexCode,
			String endProductComplexCode, String beginMaterialComplexCode,
			String endMaterialComplexCode) {

		List lsResult = new ArrayList();
		// 工厂级别的料号
		List<String> lsMaterielPtNo = new ArrayList<String>();
		Map<String, List<MaterialBomDetail>> mapBom = new HashMap<String, List<MaterialBomDetail>>();

		// 过滤后返回按条件的了料号
		if (beginMaterialPtNo != null
				&& !"".equalsIgnoreCase(beginMaterialPtNo.trim())) {
			if (endMaterialPtNo == null
					|| "".equalsIgnoreCase(endMaterialPtNo.trim())) {
				endMaterialPtNo = beginMaterialPtNo.trim();
			}
			lsMaterielPtNo = this.materialManageDao.findMaterielPtNoByRange(// 查询条件加规格，名称
					beginMaterialPtNo, endMaterialPtNo);
		} else if (beginMaterialComplexCode != null
				&& !"".equalsIgnoreCase(beginMaterialComplexCode.trim())) {// 当料件料号全部为空时
			if (endMaterialComplexCode == null
					|| "".equalsIgnoreCase(endMaterialComplexCode.trim())) {
				endMaterialComplexCode = beginMaterialComplexCode.trim();
			}
			lsMaterielPtNo = this.casCheckDao
					.findMaterialPtNoByComplexCodeRange(
							beginMaterialComplexCode, endMaterialComplexCode);
		}

		// 根据成品的货号，商品编码，名称来查询成品的所有单据。

		// 传进的条件集合
		List conditions = new ArrayList();
		// 传进的单据类型
		String type = null;

		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(type);
		// 查询单据
		List<BillDetail> list = casDao.commonSearch("", billDetailTableName,
				conditions, "", "");

		List<BillDetail> listBill = list;// this//查询成品，半成品
		Double totalDosage = null;
		Double ptTotal = null;
		for (int i = 0; i < listBill.size(); i++) {
			BillDetail billDetail = (BillDetail) listBill.get(i);

			// 成品的料号和版本号作为KEY
			String key = billDetail.getPtPart()
					+ "/"
					+ (billDetail.getVersion() == null ? 0 : billDetail
							.getVersion());
			List<MaterialBomDetail> listBomDetail = mapBom.get(key);
			if (listBomDetail == null) {
				listBomDetail = findMaterialBomDetail(billDetail.getPtPart(),
						(billDetail.getVersion() == null ? 0 : billDetail
								.getVersion()), lsMaterielPtNo);
				mapBom.put(key, listBomDetail);
			}
			// 取出成品的数量
			Double ptAmount = billDetail.getPtAmount();
			for (MaterialBomDetail bomDetail : listBomDetail) {// 生成最会结果
				if (null == bomDetail.getUnitWaste()
						|| bomDetail.getUnitWaste().doubleValue() == 0) {
					totalDosage = ptAmount;
				} else {
					totalDosage = bomDetail.getUnitWaste() * ptAmount;
				}

				if (null == bomDetail.getMateriel().getUnitConvert()
						|| bomDetail.getMateriel().getUnitConvert()
								.doubleValue() == 0) {
					ptTotal = totalDosage;
				} else {
					ptTotal = bomDetail.getMateriel().getUnitConvert()
							* totalDosage;
				}

				System.out.println("总数量是：" + totalDosage);
				System.out.println("报关折算系数是："
						+ bomDetail.getMateriel().getUnitConvert());
				System.out.println("报关总数量是:" + ptTotal);
				CurrentBillDetailBom currentBillDetailBom = new CurrentBillDetailBom();
				// currentBillDetailBom.setMaterialBomDetail(bomDetail);
				// currentBillDetailBom.setBillDetail(billDetail);
				// currentBillDetailBom.setTotalDosage(totalDosage);
				// currentBillDetailBom.setPtTotal(ptTotal);
				lsResult.add(currentBillDetailBom);
			}
		}

		return lsResult;
	}

	/**
	 * 在产品物料查询信息
	 * 
	 * @param materialType
	 *            物料类型
	 * @param conditionsBill
	 *            查询条件
	 * @return
	 * @author 黄创亮
	 */
	public List findImpExpInfos(boolean isWaiFa, String materialType,
			List<Condition> conditionsBill, List<Condition> conditionsMateriel,
			Boolean isSplitBomVersion) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(materialType);
		if (billDetailTableName == null) {
			return result;
		}
		// isWaiFa区分在产品还是外发加工
		if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materialType)) {
			result = findSemiImpExpInfos(isWaiFa, conditionsBill,
					conditionsMateriel, isSplitBomVersion);
			return result;
		}
		boolean isPtName = false;// 是否有按工厂名称查询
		boolean isHsName = false;// 是否有按报关名称查询
		boolean isBillType = false;
		if (conditionsBill != null) {
			for (Condition item : conditionsBill) {
				if (item.getFieldName().equals("ptName")) {
					isPtName = true;
				} else if (item.getFieldName().equals("hsName")) {
					isHsName = true;
				}
				if (item.getFieldName().equals("billMaster.billType.code")) {
					isBillType = true;
				}
			}
		}

		// 查符合条件的原料单据
		String[] codes = null;
		if (MaterielType.MATERIEL.equals(materialType)) {
			codes = new String[] { "1001", "1003", "1004", "1005", "1006",
					"1007", "1009", "1010", "1011", "1012", "1013", "1017",
					"1018", "1101", "1102", "1103", "1104", "1105", "1106",
					"1107", "1108", "1110", "1112", "1113", "1115", "5001" };
		} else if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {
			codes = new String[] { "2001", "2002", "2003", "2004", "2005",
					 "2008", "2009", "2010", "2013", "2015",
					"2017", "2101", "2102", "2103", "2104", "2105",
					"2106", "2107", "2108", "2109", "2113", "2114" };
		} else if (MaterielType.REMAIN_MATERIEL.equals(materialType)) {
			codes = new String[] { "6001", "6002", "6003", "6004", "6005",
					"6101", "6102", "6103", "6104", "6105" };
		} else if (MaterielType.BAD_PRODUCT.equals(materialType)) {
			codes = new String[] { "5001", "5002", "5003", "5005", "5101",
					"5102", "5103" };
		}
		String orderBy = "";
		String leftOuter = "";
		String groupBy = "";

		if (MaterielType.FINISHED_PRODUCT.equals(materialType)
				&& isSplitBomVersion != null
				&& isSplitBomVersion.booleanValue() == true) {
			isSplitBomVersion = true;
		} else {
			isSplitBomVersion = false;
		}

		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet left join fetch a.complex ";
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		List billDetails = null;
		conditionsBill.add(new Condition("and", null,
				"billMaster.billType.code", " in (", codes, ")"));
		billDetails = casDao.commonSearch("", billDetailTableName,
				conditionsBill, "",leftOuter);
		System.out.println("billDetails11111="+billDetails.size());
		System.out.println("materialType="+materialType);
		if(materialType.equals(MaterielType.MATERIEL)
				||materialType.equals(MaterielType.FINISHED_PRODUCT))  //料件、成品的多抓一次残次品仓“5001”单据
		{
			 billDetailTableName = BillUtil
			.getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
			 conditionsBill.remove(conditionsBill.size() - 1);     //去掉之前的单据
			 conditionsBill.add(new Condition("and", null,
						"billMaster.billType.code", "=", "5001",null ));
			 if(materialType.equals(MaterielType.MATERIEL))
			 conditionsBill.add(new Condition("and", null,
						"note", "=", "料件",null ));
			 else
				 conditionsBill.add(new Condition("and", null,
							"note", "=", "成品",null ));
			billDetails.addAll( casDao.commonSearch("", billDetailTableName,
					conditionsBill, "", leftOuter));
			System.out.println("billDetails22222="+billDetails.size());

		}
		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		// 排序，按料号、日期、进出仓
		Comparator comp = null;
		if (materialType.equals(MaterielType.MATERIEL)) // ==料件排序==
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					BillDetail p1 = (BillDetail) o1;
					BillDetail p2 = (BillDetail) o2;
					if (p1.getPtPart().compareTo(p2.getPtPart()) > 0)// 料号
						return 1;
					else if (p1.getPtPart().compareTo(p2.getPtPart()) < 0)
						return -1;
					else if (p1.getBillMaster().getValidDate().compareTo(// 时间
							p2.getBillMaster().getValidDate()) > 0)
						return 1;
					else if (p1

					.getBillMaster().getValidDate().compareTo(
							p2.getBillMaster().getValidDate()) < 0)
						return -1;
					else if (getInOrOutType("料件", p1.getBillMaster()
							.getBillType().getCode())// 进出仓类型
					> (getInOrOutType("料件", p2.getBillMaster().getBillType()
							.getCode())))
						return 1;
					else if (getInOrOutType("料件", p1.getBillMaster()
							.getBillType().getCode()) < (getInOrOutType("料件",
							p2.getBillMaster().getBillType().getCode())))
						return -1;
					return 0;
				}
			};
		else if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {// ==产成品==
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					BillDetail p1 = (BillDetail) o1;
					BillDetail p2 = (BillDetail) o2;
					if (p1.getPtPart().compareTo(p2.getPtPart()) > 0)// 料号
						return 1;
					else if (p1.getPtPart().compareTo(p2.getPtPart()) < 0)
						return -1;
					else if (p1.getBillMaster().getValidDate().compareTo(// 时间
							p2.getBillMaster().getValidDate()) > 0)
						return 1;
					else if (p1

					.getBillMaster().getValidDate().compareTo(
							p2.getBillMaster().getValidDate()) < 0)
						return -1;
					else if (getInOrOutType("产成品", p1.getBillMaster()
							.getBillType().getCode())// 进出仓类型
					> (getInOrOutType("产成品", p2.getBillMaster().getBillType()
							.getCode())))
						return 1;
					else if (getInOrOutType("产成品", p1.getBillMaster()
							.getBillType().getCode()) < (getInOrOutType("产成品",
							p2.getBillMaster().getBillType().getCode())))
						return -1;
					return 0;
				}
			};
		} else if (MaterielType.BAD_PRODUCT.equals(materialType)) {
			// ==残次品==
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					BillDetail p1 = (BillDetail) o1;
					BillDetail p2 = (BillDetail) o2;
					if (p1.getPtPart().compareTo(p2.getPtPart()) > 0)// 料号
						return 1;
					else if (p1.getPtPart().compareTo(p2.getPtPart()) < 0)
						return -1;
					else if (p1.getBillMaster().getValidDate().compareTo(// 时间
							p2.getBillMaster().getValidDate()) > 0)
						return 1;
					else if (p1

					.getBillMaster().getValidDate().compareTo(
							p2.getBillMaster().getValidDate()) < 0)
						return -1;
					else if (getInOrOutType("残次品", p1.getBillMaster()
							.getBillType().getCode())// 进出仓类型
					> (getInOrOutType("残次品", p2.getBillMaster().getBillType()
							.getCode())))
						return 1;
					else if (getInOrOutType("残次品", p1.getBillMaster()
							.getBillType().getCode()) < (getInOrOutType("残次品",
							p2.getBillMaster().getBillType().getCode())))
						return -1;
					return 0;
				}
			};
		} else if (MaterielType.REMAIN_MATERIEL.equals(materialType)) {
			// ==边角料==
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					BillDetail p1 = (BillDetail) o1;
					BillDetail p2 = (BillDetail) o2;
					if (p1.getPtPart().compareTo(p2.getPtPart()) > 0)// 料号
						return 1;
					else if (p1.getPtPart().compareTo(p2.getPtPart()) < 0)
						return -1;
					else if (p1.getBillMaster().getValidDate().compareTo(// 时间
							p2.getBillMaster().getValidDate()) > 0)
						return 1;
					else if (p1

					.getBillMaster().getValidDate().compareTo(
							p2.getBillMaster().getValidDate()) < 0)
						return -1;
					else if (getInOrOutType("边角料", p1.getBillMaster()
							.getBillType().getCode())// 进出仓类型
					> (getInOrOutType("边角料", p2.getBillMaster().getBillType()
							.getCode())))
						return 1;
					else if (getInOrOutType("边角料", p1.getBillMaster()
							.getBillType().getCode()) < (getInOrOutType("边角料",
							p2.getBillMaster().getBillType().getCode())))
						return -1;
					return 0;
				}
			};
		}
		Collections.sort(billDetails, comp);
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			String key = ptNo;
			System.out.println("key=:" + key);
			//
			// 成品分版本来统计
			//

			if (isSplitBomVersion) {
				int version = billDetail.getVersion() == null ? 0 : billDetail
						.getVersion();
				key += version;
			}

			if (!key.equalsIgnoreCase(oldKey) && !isHsName && !isPtName) { // 当排序后不相同的料号初始化为0,但要没有按工厂或报关名称进行查询
				rsPtAmount = 0;
				rsHsAmount = 0;
				// System.out.println("wareSetCode =" + wareSetCode +" ptNo==" +
				// ptNo);
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(billDetail,materialType);
			// System.out.println("importExportInfo.getBillType().getWareType().intValue()
			// == "+importExportInfo.getBillType().getWareType().intValue());
			if (importExportInfo.getIsIn()) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			// System.out.println("rsPtAmount =" + rsPtAmount +" rsHsAmount==" +
			// rsHsAmount);

			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);

			//
			// 成品分版本来统计 在报表中做分组条件
			//
			if (isSplitBomVersion) {
				int version = billDetail.getVersion() == null ? 0 : billDetail
						.getVersion();
				importExportInfo.setPtPart(ptNo + " (" + version + ")");
			}

			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
			System.out.println("oldKey= :" + oldKey);
		}
		return result;
	}

	/**
	 * 在产品+外发加工，物料查询
	 * 
	 * @param conditionsBill
	 * @param conditionsMaterial
	 * @param isSplitBomVersion
	 * @author 黄创亮
	 * @return
	 */
	private List findSemiImpExpInfos(

	boolean isWaiFa, List<Condition> conditionsBill,
			List<Condition> conditionsMaterial, Boolean isSplitBomVersion) {
		List<SemiProductInfo> result = new ArrayList<SemiProductInfo>();
		// 按料件条件集合conditionsMaterial找出料件集合
		boolean isAll = false;
		if (conditionsMaterial.size() == 0)
			isAll = true;
		Map<String, String> mapMaterial = getResultMaterielKey(conditionsMaterial);
		List<String> mList = null;
		if (mapMaterial != null) {
			mList = new ArrayList<String>(mapMaterial.values());
			System.out.println("mList.size()=" + mList.size());
		}

		// 按条件集合conditionsBill找出符合条件的半成品,成品、原料单据
		List<BillDetail> listMaterial = new ArrayList<BillDetail>();
		List<BillDetail> listAdd = new ArrayList<BillDetail>();
		List<BillDetail> listSemi = new ArrayList<BillDetail>();
		List<BillDetail> listFinished = new ArrayList<BillDetail>();
		List<BillDetail> listBad = new ArrayList<BillDetail>();
		List<BillDetail> listBadMaterial = new ArrayList<BillDetail>();

		List<BillDetail> listBadFinished = new ArrayList<BillDetail>();

		List<TempBomBillDetail> listFinishAdd = new ArrayList<TempBomBillDetail>();
		// 查符合条件的原料单据
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
		String[] codes = null;
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet left join fetch a.complex ";

		
		if (isWaiFa)
			codes = new String[] { "1012", "1013", "1014", "1017", "1018",
					"1110", "1112", "1113" };
		else
			codes = new String[] { "1101", "1002", "1006" };
		conditionsBill.add(new Condition("and", null,
				"billMaster.billType.code", " in (", codes, ")"));
		listMaterial = casDao.commonSearch("", billDetailTableName,
				conditionsBill, "", leftOuter);
		conditionsBill.remove(conditionsBill.size() - 1);
		System.out.println("listMaterial.size=" + listMaterial.size());
		// 查符合条件的半成品单据
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
		if (isWaiFa)
			codes = new String[] { "4003", "4004", "4006", "4103", "4104",
					"4106" };
		else
			codes = new String[] { "4002", "4003", "4004", "4006", "4102",
					"4103", "4104", "4106", };
		conditionsBill.add(new Condition("and", null,
				"billMaster.billType.code", " in (", codes, ")"));
		listSemi = casDao.commonSearch("", billDetailTableName, conditionsBill,
				"", leftOuter);
		conditionsBill.remove(conditionsBill.size() - 1);
		System.out.println("listSemi.size=" + listSemi.size());
		// 查符合条件的成品单据
		billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		if (isWaiFa)
			codes = new String[] { "2015", "2017", "2113", "2114" };
		else
			codes = new String[] { "2002", "2103" };
		conditionsBill.add(new Condition("and", null,
				"billMaster.billType.code", " in (", codes, ")"));
		listFinished = casDao.commonSearch("", billDetailTableName,
				conditionsBill, "", leftOuter);
		// 如果是在产品查询残次品
		 System.out.println("iswaiFa=" + isWaiFa);
		 if (!isWaiFa) {
		 billDetailTableName = BillUtil
		 .getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT);
		
		 conditionsBill.remove(conditionsBill.size() - 1);
		 
		 codes = new String[] { "5001" };
		 conditionsBill.add(new Condition("and", null,
		 "billMaster.billType.code", " in (", codes, ")"));
		 conditionsBill.add(new Condition("and", null,
					"note", "=", "半成品",null ));//半成品部分
		 listBad = casDao.commonSearch("", billDetailTableName,
		 conditionsBill, "", leftOuter);
		 System.out.println("残次品.size=" + listFinished.size());
		 Map<String, List> map = getisBadMaterialAndFinishied(listBad);
		 listBadMaterial = map.get("material");
		 listBadFinished = map.get("finishied");
		 System.out.println("外发material.size=" + listMaterial.size());
		 System.out.println("外发Finished.size=" + listFinished.size());
		
		 // 把料件的残次品加入到 料件表中
		 listMaterial.addAll(listBadMaterial);
		
		 // 把成品的残次品加入到成品半成品表中
		 listFinished.addAll(listBadFinished);
		
		 }
		System.out.println("listMaterial.size=" + listMaterial.size());
		System.out.println("listFinished.size=" + listFinished.size());
		// 判断料件
		for (int i = 0; i < listMaterial.size(); i++) {
			if (isAll) {
				listAdd.add(listMaterial.get(i));
			}else if(listMaterial.get(i).getComplex() == null 
					|| listMaterial.get(i).getHsUnit() == null ){
				continue;
			}
			else
				for (String s : mList) {
					if (s.equals(listMaterial.get(i).getPtPart() + "/"
							+ listMaterial.get(i).getComplex().getCode() + "/"
							+ listMaterial.get(i).getHsName() + "/"
							+ listMaterial.get(i).getHsSpec() + "/"
							+ listMaterial.get(i).getHsUnit().getName())) {
						listAdd.add(((BillDetail) listMaterial.get(i)));
					}
					// System.out.println("s="+s);
					// System.out.println("name="+listMaterial.get(i).getPtName());
					//
					// System.out.println(":::::"+listMaterial.get(i).getPtPart()+
					// "/"
					// +listMaterial.get(i).getComplex().getCode()+"/"
					// + listMaterial.get(i).getHsName()+"/"
					// + listMaterial.get(i).getHsSpec()+"/"
					// +listMaterial.get(i).getHsUnit().getName());
				}
		}

		// TODO
		// 查找BOM,折料，判断成品、半成品所折的料件是否跟条件里的料件一致
		listSemi.addAll(listFinished);
		List<TempBomRelation> boms = null;
		String bomKey = null;
		Map<String, List<TempBomRelation>> bomMap = new HashMap<String, List<TempBomRelation>>();

		for (BillDetail bill : listSemi) {
			bomKey = bill.getPtPart();
			boms = bomMap.get(bomKey);
			if (boms != null) {
			} else {
				boms = this.getBomRelation(bill, mapMaterial, isAll);
				bomMap.put(bomKey, boms);
			}
			System.out.println("boms.size=" + boms.size());
			for (TempBomRelation bom : boms) {
				listFinishAdd.add(setTempBomBillDetailFinished(bom, bill));
			}
		}

		// 转成TempBomBillDetail
		System.out.println("=====listAdd.size=" + listAdd.size());
		System.out.println("=====listFinishAdd.size=" + listFinishAdd.size());
		if (listAdd.size() != 0) {
			for (int i = 0; i < listAdd.size(); i++) {
				BillDetail bill = listAdd.get(i);
				listFinishAdd.add(getTempBomBillDetailMaterial(bill));
			}
		}

		// 按料号、(仓库)、日期排序
		Comparator comp = null;
		if (!isWaiFa) // 在产品
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					TempBomBillDetail p1 = (TempBomBillDetail) o1;
					TempBomBillDetail p2 = (TempBomBillDetail) o2;
					if (p1.getBill().getPtPart().compareTo(
							p2.getBill().getPtPart()) > 0)// 料号
						return 1;
					else if (p1.getBill().getPtPart().compareTo(
							p2.getBill().getPtPart()) < 0)
						return -1;
					else if (p1
							.getBill()
							.getBillMaster()
							.getValidDate()
							.compareTo(// 时间
									p2.getBill().getBillMaster().getValidDate()) > 0)
						return 1;
					else if (p1
							.getBill()
							.getBillMaster()
							.getValidDate()
							.compareTo(
									p2.getBill().getBillMaster().getValidDate()) < 0)
						return -1;
					else if (getInOrOutType("在产品", p1.getBill().getBillMaster()
							.getBillType().getCode())// 进出仓类型
					> (getInOrOutType("在产品", p2.getBill().getBillMaster()
							.getBillType().getCode())))
						return 1;
					else if (getInOrOutType("在产品", p1.getBill().getBillMaster()
							.getBillType().getCode()) < (getInOrOutType("在产品",
							p2.getBill().getBillMaster().getBillType()
									.getCode())))
						return -1;
					return 0;
				}
			};
		else
			// 外发
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					TempBomBillDetail p1 = (TempBomBillDetail) o1;
					TempBomBillDetail p2 = (TempBomBillDetail) o2;
					if (p1.getBill().getPtPart().compareTo(
							p2.getBill().getPtPart()) > 0)// 料号
						return 1;
					else if (p1.getBill().getPtPart().compareTo(
							p2.getBill().getPtPart()) < 0)
						return -1;
					else if (p1
							.getBill()
							.getBillMaster()
							.getValidDate()
							.compareTo(// 时间
									p2.getBill().getBillMaster().getValidDate()) > 0)
						return 1;
					else if (p1
							.getBill()
							.getBillMaster()
							.getValidDate()
							.compareTo(
									p2.getBill().getBillMaster().getValidDate()) < 0)
						return -1;
					else if (getInOrOutType("外发", p1.getBill().getBillMaster()
							.getBillType().getCode())// 进出仓类型
					> (getInOrOutType("外发", p2.getBill().getBillMaster()
							.getBillType().getCode())))
						return 1;
					else if (getInOrOutType("外发", p1.getBill().getBillMaster()
							.getBillType().getCode()) < (getInOrOutType("外发",
							p2.getBill().getBillMaster().getBillType()
									.getCode())))
						return -1;
					return 0;
				}
			};

		Collections.sort(listFinishAdd, comp);
		boolean isPtName = false;// 是否有按工厂名称查询
		boolean isHsName = false;// 是否有按报关名称查询
		boolean isBillType = false;
		if (conditionsBill != null) {
			for (Condition item : conditionsBill) {
				if (item.getFieldName().equals("ptName")) {
					isPtName = true;
				} else if (item.getFieldName().equals("hsName")) {
					isHsName = true;
				}
				if (item.getFieldName().equals("billMaster.billType.code")) {
					isBillType = true;
				}
			}
		}
		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		for (int i = 0; i < listFinishAdd.size(); i++) {
			TempBomBillDetail tempBomBill = (TempBomBillDetail) listFinishAdd
					.get(i);

			String ptNo = tempBomBill.getBill().getPtPart();
			String key = ptNo;
			//
			// 成品分版本来统计
			//
			if (isSplitBomVersion) {
				int version = tempBomBill.getBill().getVersion() == null ? 0
						: tempBomBill.getBill().getVersion();
				key += version;
			}
			if (!key.equalsIgnoreCase(oldKey) && !isHsName && !isPtName) { // 当排序后不相同的料号初始化为0,但要没有按工厂或报关名称进行查询
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			SemiProductInfo semiProductInfo = new SemiProductInfo(tempBomBill,
					isWaiFa);
			boolean isIn = false;
			if (isWaiFa) {
				if (semiProductInfo.getBillType().getCode().equals("1014")
						|| semiProductInfo.getBillType().getCode().equals(
								"1110")
						|| semiProductInfo.getBillType().getCode().equals(
								"1112")
						|| semiProductInfo.getBillType().getCode().equals(
								"1113")
						|| semiProductInfo.getBillType().getCode().equals(
								"4103")
						|| semiProductInfo.getBillType().getCode().equals(
								"4104")
						|| semiProductInfo.getBillType().getCode().equals(
								"4106")
						|| semiProductInfo.getBillType().getCode().equals(
								"2113")
						|| semiProductInfo.getBillType().getCode().equals(
								"2114"))
					isIn = true;
			} else {
				if (semiProductInfo.getBillType().getCode().equals("1002")
						|| semiProductInfo.getBillType().getCode().equals(
								"1101")
						|| semiProductInfo.getBillType().getCode().equals(
								"2103")
						|| semiProductInfo.getBillType().getCode().equals(
								"4002")
						|| semiProductInfo.getBillType().getCode().equals(
								"4003")
						|| semiProductInfo.getBillType().getCode().equals(
								"4004")
						|| semiProductInfo.getBillType().getCode().equals(
								"4005")
						|| semiProductInfo.getBillType().getCode().equals(
								"4006")
						|| semiProductInfo.getBillType().getCode().equals(
								"4008"))
					isIn = true;
			}
			if (isIn) {
				rsPtAmount += semiProductInfo.getImpPtAmount();
				rsHsAmount += semiProductInfo.getImpHsAmount();
			} else {
				rsPtAmount -= semiProductInfo.getExpPtAmount();
				rsHsAmount -= semiProductInfo.getExpHsAmount();
			}
			semiProductInfo.setRsPtAmount(rsPtAmount);
			semiProductInfo.setRsHsAmount(rsHsAmount);

			//
			// 成品分版本来统计 在报表中做分组条件
			//
			if (isSplitBomVersion) {
				int version = tempBomBill.getBill().getVersion() == null ? 0
						: tempBomBill.getBill().getVersion();
				semiProductInfo.setPtPart(ptNo + " (" + version + ")");
			}

			result.add(semiProductInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;

	}

	/**
	 * 进出仓类型
	 * 
	 * @param type物料类型
	 * @param code单据代码
	 * @return
	 */
	protected int getInOrOutType(String type, String code) {// 进仓为0，出仓为1
		if (type.equals("料件")) { // ---------------------料件
			if (code.equals("1001") || code.equals("1003")
					|| code.equals("1004") || code.equals("1005")
					|| code.equals("1006") || code.equals("1007")
					|| code.equals("1009") || code.equals("1010")
					|| code.equals("1011") || code.equals("1012")
					|| code.equals("1013") || code.equals("1017")
					|| code.equals("1018")) { // 进仓
				return 0;
			} else
				return 1;
		} else if (type.equals("-产成品")) { // -----------------------产成品
			if (code.equals("2001") || code.equals("2002")
					|| code.equals("2003") || code.equals("2004")
					|| code.equals("2005") || code.equals("2008")
					|| code.equals("2009") || code.equals("2015")
					|| code.equals("2017")

			) { // 进仓
				return 0;
			} else
				return 1;
		} else if (type.equals("在产品")) { // ------------------------在产品
			if (code.equals("1002") || code.equals("1101")
					|| code.equals("2103") || code.equals("4002")
					|| code.equals("4003") || code.equals("4004")
					|| code.equals("4006")) { // 进仓
				return 0;
			} else
				return 1;
		} else if (type.equals("残次品")) { // ------------------------残次品
			if (code.equals("5001") || code.equals("5002")
					|| code.equals("5003") || code.equals("5005")) { // 进仓
				return 0;
			} else
				return 1;
		} else if (type.equals("外发")) { // ------------------------外发加工
			if (code.equals("1014") || code.equals("1110")
					|| code.equals("1112") || code.equals("1113")
					|| code.equals("2113") || code.equals("2114")
					|| code.equals("4103") || code.equals("4104")
					|| code.equals("4106")) { // 进仓
				return 0;
			} else
				return 1;
		} else if (type.equals("边角料")) {
			if (code.equals("6001") || code.equals("6002")
					|| code.equals("6003") || code.equals("6004")
					|| code.equals("6005")) { // 进仓
				return 0;
			} else
				return 1;
		}
		return 2; // 错误类型
	}

	private TempBomBillDetail getTempBomBillDetailMaterial(BillDetail bill) {
		TempBomBillDetail t = new TempBomBillDetail();
		BillDetail b = new BillDetail();
		b.setPtPart(bill.getPtPart());
		b.setPtName(bill.getPtName());
		b.setPtSpec(bill.getPtSpec());
		b.setPtUnit(bill.getPtUnit());
		b.setComplex(bill.getComplex());
		b.setHsName(bill.getHsName());
		b.setHsSpec(bill.getHsSpec());
		b.setHsUnit(bill.getHsUnit());
		b.setPtAmount(bill.getPtAmount());
		b.setHsAmount(bill.getHsAmount());
		b.setBillMaster(bill.getBillMaster());
		b.setProductNo(bill.getProductNo());
		b.setUnitConvert(bill.getUnitConvert());
		b.setWareSet(bill.getWareSet());
		b.setUnitConvert(bill.getUnitConvert());

		t.setBill(b);

		t.setPtPart(bill.getPtPart());
		t.setPtName(bill.getPtName());
		t.setPtSpec(bill.getPtSpec());
		t.setPtUnit(bill.getPtUnit());
		t.setPtAmount(bill.getPtAmount());
		t.setUnitConvert(bill.getUnitConvert());
		if (bill.getBillMaster() != null
				&& bill.getBillMaster().getBillType() != null
				&& bill.getBillMaster().getBillType().getBillType() != null) {
			t.setMaterielType(getMaterielTypeString(bill.getBillMaster()
					.getBillType().getBillType()));
		}
		return t;
	}

	/**
	 * 辨别物料类型名称
	 * 
	 * @param billType
	 * @return
	 */
	public String getMaterielTypeString(int billType) {
		String materielTypeString = null;
		if (billType == SBillType.MATERIEL_IN
				|| billType == SBillType.MATERIEL_OUT) {
			materielTypeString = "料件";
		} else if (billType == SBillType.PRODUCE_IN
				|| billType == SBillType.PRODUCE_OUT) {
			materielTypeString = "成品";
		} else if (billType == SBillType.HALF_PRODUCE_IN
				|| billType == SBillType.HALF_PRODUCE_OUT
				|| billType == SBillType.HALF_PRODUCE_INOUT) {
			materielTypeString = "半成品";
		} else if (billType == SBillType.FIXTURE_IN
				|| billType == SBillType.FIXTURE_OUT) {
			materielTypeString = "设备";
		} else if (billType == SBillType.LEFTOVER_MATERIEL_IN
				|| billType == SBillType.LEFTOVER_MATERIEL_OUT
				|| billType == SBillType.LEFTOVER_MATERIEL_INOUT) {
			materielTypeString = "边角料";
		} else if (billType == SBillType.REMAIN_PRODUCE_IN
				|| billType == SBillType.REMAIN_PRODUCE_OUT
				|| billType == SBillType.REMAIN_PRODUCE_INOUT) {
			materielTypeString = "残次品";
		}
		return materielTypeString;
	}

	private TempBomBillDetail setTempBomBillDetailFinished(TempBomRelation bom,
			BillDetail sourceBill) {
		// 工厂料号、名称、规格、单位
		TempBomBillDetail tempBill = new TempBomBillDetail();
		BillDetail bill = new BillDetail();
		bill.setPtPart(bom.getPtNo());
		bill.setPtName(bom.getPtName());
		bill.setPtSpec(bom.getPtSpec());
		bill.setPtUnit(bom.getPtUnit());
		bill.setUnitConvert(bom.getUnitConvert());
		// 报关料号、名称、规格、单位
		// 抓取对应关系

		// FactoryAndFactualCustomsRalation
		// f=fmapMap.get(bom.getMateriel().getPtNo());
		bill.setComplex(bom.getComplex());
		bill.setHsName(bom.getHsName());
		bill.setHsSpec(bom.getHsSpec());
		bill.setHsUnit(bom.getHsUnit());
		tempBill.setUnitConvert(bom.getUnitConvert());
		// 工厂数量、报关数量
		// 折算工厂数量=成品工厂数量*（单耗/（1-损耗率））
		if (null != bom.getWaste() && null != bom.getUnitWaste()
				&& null != sourceBill.getPtAmount())
			bill.setPtAmount(sourceBill.getPtAmount()
					* (bom.getUnitWaste() / (1 - bom.getWaste())));
		else
			bill.setPtAmount(0.0);
		if (null != bom.getUnitUsed() && null != sourceBill.getPtAmount()
				&& bom.getUnitConvert() != null)
			bill.setHsAmount(sourceBill.getPtAmount() * bom.getUnitUsed()
					* bom.getUnitConvert());
		else
			bill.setHsAmount(0.0);

		// 其他
		bill.setBillMaster(sourceBill.getBillMaster());
		bill.setProductNo(sourceBill.getProductNo());
		bill.setWareSet(sourceBill.getWareSet());
		bill.setNote(sourceBill.getNote());
		tempBill.setBill(bill);
		tempBill.setPtPart(sourceBill.getPtPart());
		tempBill.setPtName(sourceBill.getPtName());
		tempBill.setPtUnit(sourceBill.getPtUnit());
		tempBill.setPtAmount(sourceBill.getPtAmount());
		tempBill.setPtSpec(sourceBill.getPtSpec());
		tempBill.setUnitUsed(bom.getUnitUsed());
		tempBill.setUnitWaste(bom.getUnitWaste());
		tempBill.setWareAmount(tempBill.getPtAmount() * bom.getUnitUsed());
		tempBill.setMaterielType(sourceBill.getNote());
		if (bill.getBillMaster() != null
				&& bill.getBillMaster().getBillType() != null
				&& bill.getBillMaster().getBillType().getBillType() != null) {
			tempBill.setMaterielType(getMaterielTypeString(bill.getBillMaster()
					.getBillType().getBillType()));
		}

		return tempBill;
	}

	// private TempBomBillDetail setTempBomBillDetailFinished(MaterialBomDetail
	// bom,
	// BillDetail sourceBill,
	// Map<String, FactoryAndFactualCustomsRalation> fmapMap) {
	// // 工厂料号、名称、规格、单位
	// TempBomBillDetail tempBill=new TempBomBillDetail();
	// BillDetail bill=new BillDetail();
	// bill.setPtPart(bom.getMateriel().getPtNo());
	// bill.setPtName(bom.getMateriel().getFactoryName());
	// bill.setPtSpec(bom.getMateriel().getFactorySpec());
	// bill.setPtUnit(bom.getMateriel().getCalUnit());
	// // 报关料号、名称、规格、单位
	// //抓取对应关系
	//	
	// FactoryAndFactualCustomsRalation
	// f=fmapMap.get(bom.getMateriel().getPtNo());
	// bill.setComplex(f.getStatCusNameRelationHsn().getComplex());
	// bill.setHsName(f.getStatCusNameRelationHsn().getCusName());
	// bill.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
	// bill.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());
	// // 工厂数量、报关数量
	// if (null!=bom.getUnitUsed()&&null!=sourceBill.getPtAmount())
	// bill.setPtAmount(sourceBill.getPtAmount() * bom.getUnitUsed());
	// else
	// bill.setPtAmount(0.0);
	// if (null!=bom.getUnitUsed()&&null!=sourceBill.getHsAmount())
	// bill.setHsAmount(sourceBill.getHsAmount() * bom.getUnitUsed());
	// else
	// bill.setHsAmount(0.0);
	//	
	// //其他
	// bill.setBillMaster(sourceBill.getBillMaster());
	// bill.setProductNo(sourceBill.getProductNo());
	// bill.setWareSet(sourceBill.getWareSet());
	// bill.setNote(sourceBill.getNote());
	// tempBill.setBill(bill);
	// tempBill.setPtPart(sourceBill.getPtPart());
	// tempBill.setPtName(sourceBill.getPtName());
	// tempBill.setPtUnit(sourceBill.getPtUnit());
	// tempBill.setPtAmount(sourceBill.getPtAmount());
	// tempBill.setPtSpec(sourceBill.getPtSpec());
	// tempBill.setUnitConvert(sourceBill.getUnitConvert());
	// return tempBill;
	// }

	/**
	 * 返回残次品单据料件，或成品 通过isMaterial
	 * 
	 * @param isMaterial
	 * @param listBad
	 * @return hcl
	 */
	private Map getisBadMaterialAndFinishied(List<BillDetail> listBad) {
		List<BillDetail> material = new ArrayList<BillDetail>();
		List<BillDetail> finishied = new ArrayList<BillDetail>();
		List<String> list = casDao.getAllMaterialPtNo();
		Map materialMap = new HashMap<String, String>();
		for (String ptNo : list) {
			materialMap.put(ptNo, ptNo);
		}
		for (BillDetail bill : listBad) {
			if (materialMap.get(bill.getPtPart()) != null)
				material.add(bill);
			else
				finishied.add(bill);
		}
		Map listMap = new HashMap<String, List<BillDetail>>();
		listMap.put("material", material);
		listMap.put("finishied", finishied);

		return listMap;
	}

	private BillDetail setBilldetail(MaterialBomDetail bom,
			BillDetail sourceBill,
			Map<String, FactoryAndFactualCustomsRalation> fmapMap) {
		// 工厂料号、名称、规格、单位
		BillDetail bill = new BillDetail();
		bill.setPtPart(bom.getMateriel().getPtNo());
		bill.setPtName(bom.getMateriel().getFactoryName());
		bill.setPtSpec(bom.getMateriel().getFactorySpec());
		bill.setPtUnit(bom.getMateriel().getCalUnit());
		// 报关料号、名称、规格、单位
		// 抓取对应关系

		FactoryAndFactualCustomsRalation f = fmapMap.get(bom.getMateriel()
				.getPtNo());
		bill.setComplex(f.getStatCusNameRelationHsn().getComplex());
		bill.setHsName(f.getStatCusNameRelationHsn().getCusName());
		bill.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
		bill.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());
		// 工厂数量、报关数量
		if (null != bom.getUnitUsed() && null != sourceBill.getPtAmount())
			bill.setPtAmount(sourceBill.getPtAmount() * bom.getUnitUsed());
		else
			bill.setPtAmount(0.0);
		if (null != bom.getUnitUsed() && null != sourceBill.getHsAmount())
			bill.setHsAmount(sourceBill.getHsAmount() * bom.getUnitUsed());
		else
			bill.setHsAmount(0.0);

		// 其他
		bill.setBillMaster(sourceBill.getBillMaster());
		bill.setProductNo(sourceBill.getProductNo());
		bill.setWareSet(sourceBill.getWareSet());
		bill.setNote(sourceBill.getNote());

		return bill;
	}

	/**
	 * 得到物料所需条件的料件key key=料号+商品编码+报关名称+报关规格+报关单位
	 * 
	 * @param conditionsMaterial
	 * 
	 * @author 黄创亮
	 * @return
	 */
	private Map<String, String> getResultMaterielKey(
			List<Condition> conditionsMaterial) {
		if (conditionsMaterial == null || conditionsMaterial.size() == 0) {
			return null;
		}
		Map map = new HashMap<String, String>();
		List<FactoryAndFactualCustomsRalation> ms = materialManageDao
				.getSemiResultMateriel(conditionsMaterial);
		System.out.println("ms.size()=" + ms.size());
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < ms.size(); i++) {
			String key = ms.get(i).getMateriel().getPtNo()
					+ "/"
					+ ms.get(i).getStatCusNameRelationHsn().getComplex()
							.getCode()
					+ "/"
					+ ms.get(i).getStatCusNameRelationHsn().getCusName()
					+ "/"
					+ ms.get(i).getStatCusNameRelationHsn().getCusSpec()
					+ "/"
					+ ms.get(i).getStatCusNameRelationHsn().getCusUnit()
							.getName();

			if (map.get(key) == null) {
				System.out.println("materailKey=" + key);
				map.put(key, key);
			}
		}

		return map;
	}

	/**
	 * 
	 * @param ptNo
	 * @param version
	 * @param mapMaterial
	 * @return
	 */
	private List<TempBomRelation> getBomRelation(BillDetail billDetail,
			Map<String, String> mapMaterial, boolean isAll) {
		List<TempBomRelation> listResult = new ArrayList<TempBomRelation>();
		List<MaterialBomDetail> listBom = materialManageDao
				.findMaterialBomByDetailByptNoAndVersion(
						billDetail.getPtPart(), billDetail.getVersion());//
		System.out.println("listBom111111111.size()=" + listBom.size());
		for (MaterialBomDetail bomDetail : listBom) {
			TempBomRelation tempBomRelation = null;
			List<FactoryAndFactualCustomsRalation> listRelation = this.casDao
					.findFactoryAndFactualCustomsRalation(bomDetail
							.getMateriel());
			System.out.println("listRelation.size()=" + listRelation.size());
			// FactoryAndFactualCustomsRalation c : listRelation
			for (int j = listRelation.size() - 1; j >= 0; j--) {
				FactoryAndFactualCustomsRalation c = (FactoryAndFactualCustomsRalation) listRelation
						.get(j);

				if (billDetail.getEmsNo() != null) {
					if (!billDetail.getEmsNo().equals(
							c.getStatCusNameRelationHsn().getEmsNo())) {
						continue;
					}
				}
				String key = c.getMateriel().getPtNo() + "/"
						+ c.getStatCusNameRelationHsn().getComplex().getCode()
						+ "/" + c.getStatCusNameRelationHsn().getCusName()
						+ "/" + c.getStatCusNameRelationHsn().getCusSpec()
						+ "/"
						+ c.getStatCusNameRelationHsn().getCusUnit().getName();
				System.out.println("key=" + key);
				if (isAll) {
					tempBomRelation = new TempBomRelation();
					tempBomRelation.setPtNo(c.getMateriel().getPtNo());
					tempBomRelation.setPtName(c.getMateriel().getFactoryName());
					tempBomRelation.setPtSpec(c.getMateriel().getFactorySpec());
					tempBomRelation.setPtUnit(c.getMateriel().getCalUnit());
					tempBomRelation.setComplex(c.getStatCusNameRelationHsn()
							.getComplex());
					tempBomRelation.setHsName(c.getStatCusNameRelationHsn()
							.getCusName());
					tempBomRelation.setHsSpec(c.getStatCusNameRelationHsn()
							.getCusSpec());
					tempBomRelation.setHsUnit(c.getStatCusNameRelationHsn()
							.getCusUnit());

					tempBomRelation.setUnitConvert(c.getUnitConvert());
					tempBomRelation.setUnitWaste(bomDetail.getUnitWaste());
					tempBomRelation.setWaste(bomDetail.getWaste());

					tempBomRelation.setUnitUsed(bomDetail.getUnitWaste()
							/ (1 - CommonUtils.getDoubleExceptNull(bomDetail.getWaste())));

					listResult.add(tempBomRelation);
					break;

				} else if (mapMaterial.get(key) != null) {
					tempBomRelation = new TempBomRelation();
					tempBomRelation.setPtNo(c.getMateriel().getPtNo());
					tempBomRelation.setPtName(c.getMateriel().getFactoryName());
					tempBomRelation.setPtSpec(c.getMateriel().getFactorySpec());
					tempBomRelation.setPtUnit(c.getMateriel().getCalUnit());
					tempBomRelation.setComplex(c.getStatCusNameRelationHsn()
							.getComplex());
					tempBomRelation.setHsName(c.getStatCusNameRelationHsn()
							.getCusName());
					tempBomRelation.setHsSpec(c.getStatCusNameRelationHsn()
							.getCusSpec());
					tempBomRelation.setHsUnit(c.getStatCusNameRelationHsn()
							.getCusUnit());
					tempBomRelation.setUnitUsed(bomDetail.getUnitUsed());
					tempBomRelation.setUnitConvert(c.getUnitConvert());
					tempBomRelation.setUnitWaste(bomDetail.getUnitWaste());
					tempBomRelation.setWaste(bomDetail.getWaste());

					listResult.add(tempBomRelation);
					break;

				}
			}
			// 当单据中有手册号，并且单据中的手册号和对应关系中的手册号没有一个相等的时候，默认取最后一笔。
			if (tempBomRelation == null) {
				for (int j = listRelation.size() - 1; j >= 0; j--) {
					FactoryAndFactualCustomsRalation c = (FactoryAndFactualCustomsRalation) listRelation
							.get(j);
					String key = c.getMateriel().getPtNo()
							+ "/"
							+ c.getStatCusNameRelationHsn().getComplex()
									.getCode()
							+ "/"
							+ c.getStatCusNameRelationHsn().getCusName()
							+ "/"
							+ c.getStatCusNameRelationHsn().getCusSpec()
							+ "/"
							+ c.getStatCusNameRelationHsn().getCusUnit()
									.getName();
					System.out.println("key=" + key);
					if (isAll) {
						tempBomRelation = new TempBomRelation();
						tempBomRelation.setPtNo(c.getMateriel().getPtNo());
						tempBomRelation.setPtName(c.getMateriel()
								.getFactoryName());
						tempBomRelation.setPtSpec(c.getMateriel()
								.getFactorySpec());
						tempBomRelation.setPtUnit(c.getMateriel().getCalUnit());
						tempBomRelation.setHsName(c.getStatCusNameRelationHsn()
								.getCusName());
						tempBomRelation.setHsSpec(c.getStatCusNameRelationHsn()
								.getCusSpec());
						tempBomRelation.setComplex(c
								.getStatCusNameRelationHsn().getComplex());
						tempBomRelation.setHsUnit(c.getStatCusNameRelationHsn()
								.getCusUnit());
						tempBomRelation.setUnitUsed(bomDetail.getUnitUsed());
						tempBomRelation.setUnitConvert(c.getUnitConvert());
						tempBomRelation.setUnitWaste(bomDetail.getUnitWaste());
						tempBomRelation.setWaste(bomDetail.getWaste());
						listResult.add(tempBomRelation);
						break;
					} else if (mapMaterial.get(key) != null) {
						tempBomRelation = new TempBomRelation();
						tempBomRelation.setPtNo(c.getMateriel().getPtNo());
						tempBomRelation.setPtName(c.getMateriel()
								.getFactoryName());
						tempBomRelation.setPtSpec(c.getMateriel()
								.getFactorySpec());
						tempBomRelation.setPtUnit(c.getMateriel().getCalUnit());
						tempBomRelation.setComplex(c
								.getStatCusNameRelationHsn().getComplex());
						tempBomRelation.setHsName(c.getStatCusNameRelationHsn()
								.getCusName());
						tempBomRelation.setHsSpec(c.getStatCusNameRelationHsn()
								.getCusSpec());
						tempBomRelation.setHsUnit(c.getStatCusNameRelationHsn()
								.getCusUnit());
						tempBomRelation.setUnitUsed(bomDetail.getUnitUsed());
						tempBomRelation.setUnitConvert(c.getUnitConvert());
						tempBomRelation.setUnitWaste(bomDetail.getUnitWaste());
						tempBomRelation.setWaste(bomDetail.getWaste());
						listResult.add(tempBomRelation);
						break;
					}
				}
			}
		}
		return listResult;
	}

	/**
	 * 查询物料信息，统计上期结存
	 * 
	 * @param materialType
	 *            物料类型
	 * @param conditions1
	 *            查询条件
	 * @author 黄创亮
	 * @return 按照查询条件查出委外的进出口商品信息
	 */
	public List findImpExpInfos(boolean isWaiFa, String materialType,
			List<Condition> conditions1, List<Condition> conditions2,
			Boolean isSplitBomVersion, Date beginDate) {
		List result = this.findImpExpInfos(isWaiFa, materialType, conditions1,
				conditions2, isSplitBomVersion);

		ArrayList list = new ArrayList();

		// /////////////////////////////
		// 利用 list 把排序后的 把相同料号
		// 相同仓库的记录,在指定的beginDate
		// 前的数据只加载最后一条
		// /////////////////////////////
		String oldKey = "";
		boolean isOnce = true;
		if (!materialType.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			for (int i = result.size() - 1; i >= 0; i--) {
				ImportExportInfo importExportInfo = (ImportExportInfo) result
						.get(i);
				String wareSetCode = importExportInfo.getWareSet() == null
						|| importExportInfo.getWareSet().getCode() == null ? ""
						: importExportInfo.getWareSet().getCode();
				String ptNo = importExportInfo.getPtPart();
				String key = wareSetCode + ptNo;
				Date currentDate = importExportInfo.getDate();
				//
				// init 第一个 oldKey 的值
				// 
				if (i == result.size() - 1) {
					//
					// currentDate >= beginDate ==
					// !currentDate.before(beginDate)
					//
					if (!currentDate.before(beginDate)) {
						list.add(0, importExportInfo);
					} else {
						//
						// 加入上期加存在此
						//
						addTotalFrontImportExportInfo(beginDate,
								importExportInfo);
						list.add(0, importExportInfo);
						isOnce = false;
					}
					oldKey = key;
					continue;
				}

				if (!key.equalsIgnoreCase(oldKey)) {
					isOnce = true;
					if (!currentDate.before(beginDate)) {
						list.add(0, importExportInfo);
					} else {
						addTotalFrontImportExportInfo(beginDate,
								importExportInfo);
						list.add(0, importExportInfo);
						isOnce = false;
					}
				} else if (key.equalsIgnoreCase(oldKey)
						&& !currentDate.before(beginDate)) {
					list.add(0, importExportInfo);
				} else if (key.equalsIgnoreCase(oldKey)
						&& currentDate.before(beginDate) && isOnce) {
					addTotalFrontImportExportInfo(beginDate, importExportInfo);
					list.add(0, importExportInfo);
					isOnce = false;
				}
				oldKey = key;
			}
		} else {
			for (int i = result.size() - 1; i >= 0; i--) {
				SemiProductInfo semiProductInfo = (SemiProductInfo) result
						.get(i);
				String wareSetCode = semiProductInfo.getWareSet() == null
						|| semiProductInfo.getWareSet().getCode() == null ? ""
						: semiProductInfo.getWareSet().getCode();
				String ptNo = semiProductInfo.getPtPart();
				String key = wareSetCode + ptNo;
				Date currentDate = semiProductInfo.getDate();
				//
				// init 第一个 oldKey 的值
				// 
				if (i == result.size() - 1) {
					//
					// currentDate >= beginDate ==
					// !currentDate.before(beginDate)
					//
					if (!currentDate.before(beginDate)) {
						list.add(0, semiProductInfo);
					} else {
						//
						// 加入上期加存在此
						//
						addTotalFrontSemiProductInfo(beginDate, semiProductInfo);
						list.add(0, semiProductInfo);
						isOnce = false;
					}
					oldKey = key;
					continue;
				}

				if (!key.equalsIgnoreCase(oldKey)) {
					isOnce = true;
					if (!currentDate.before(beginDate)) {
						list.add(0, semiProductInfo);
					} else {
						addTotalFrontSemiProductInfo(beginDate, semiProductInfo);
						list.add(0, semiProductInfo);
						isOnce = false;
					}
				} else if (key.equalsIgnoreCase(oldKey)
						&& !currentDate.before(beginDate)) {
					list.add(0, semiProductInfo);
				} else if (key.equalsIgnoreCase(oldKey)
						&& currentDate.before(beginDate) && isOnce) {
					addTotalFrontSemiProductInfo(beginDate, semiProductInfo);
					list.add(0, semiProductInfo);
					isOnce = false;
				}
				oldKey = key;
			}
		}
		return list;
	}

	/**
	 * 加入上期加存在此
	 * 
	 * @author 黄创亮
	 * */
	private void addTotalFrontImportExportInfo(Date beginDate,
			ImportExportInfo importExportInfo) {
		importExportInfo
				.setImpBillNo(ImportExportInfo.TOTAL_FRONT_AMOUNT_STRING);
		importExportInfo
				.setExpBillNo(ImportExportInfo.TOTAL_FRONT_AMOUNT_STRING);
		// 上期结存不显示报关单号和制单号
		importExportInfo.setCustomNo("");
		importExportInfo.setProductNo("");

		importExportInfo.setDate(beginDate);
		importExportInfo.setBillType(null);
		// 正数
		boolean isHsAmountSignless = importExportInfo.getRsHsAmount() > 0;
		importExportInfo.setExpHsAmount(isHsAmountSignless ? 0
				: importExportInfo.getRsHsAmount());
		importExportInfo.setImpHsAmount(!isHsAmountSignless ? 0
				: importExportInfo.getRsHsAmount());

		// 正数
		boolean isPtAmountSignless = importExportInfo.getRsPtAmount() > 0;
		importExportInfo.setExpPtAmount(isPtAmountSignless ? 0
				: importExportInfo.getRsPtAmount());
		importExportInfo.setImpPtAmount(!isPtAmountSignless ? 0
				: importExportInfo.getRsPtAmount());

	}

	/**
	 * 加入上期加存在此
	 * 
	 * @author 黄创亮
	 * */
	private void addTotalFrontSemiProductInfo(Date beginDate,
			SemiProductInfo semiProductInfo) {
		semiProductInfo
				.setImpBillNo(ImportExportInfo.TOTAL_FRONT_AMOUNT_STRING);
		semiProductInfo
				.setExpBillNo(ImportExportInfo.TOTAL_FRONT_AMOUNT_STRING);
		// 上期结存不显示报关单号和制单号
		semiProductInfo.setCustomNo("");
		semiProductInfo.setProductNo("");

		semiProductInfo.setDate(beginDate);
		semiProductInfo.setBillType(null);
		// 正数
		boolean isHsAmountSignless = semiProductInfo.getRsHsAmount() > 0;
		semiProductInfo.setExpHsAmount(isHsAmountSignless ? 0 : semiProductInfo
				.getRsHsAmount());
		semiProductInfo.setImpHsAmount(!isHsAmountSignless ? 0
				: semiProductInfo.getRsHsAmount());

		// 正数
		boolean isPtAmountSignless = semiProductInfo.getRsPtAmount() > 0;
		semiProductInfo.setExpPtAmount(isPtAmountSignless ? 0 : semiProductInfo
				.getRsPtAmount());
		semiProductInfo.setImpPtAmount(!isPtAmountSignless ? 0
				: semiProductInfo.getRsPtAmount());

	}

	/**
	 * 计算统计结转差额总表map
	 * 
	 * @param conditionBalance
	 * @return
	 * @author chenSir
	 */
	private Map<String, List<CarryBalance>> getCarryMap(
			ConditionBalance conditionBalance) {
		// 用于保存同一商品的四种统计
		Map<String, List<CarryBalance>> mapCarry = new HashMap();
		List<CarryBalance> carrys = null;
		CarryBalance carry = null;
		// 处理查询日期
		Date endDate = CommonUtils.getEndDate(conditionBalance.getDate());
		Date startDate = CommonUtils.getBeginDate(new Date(endDate.getYear(),
				0, 1));
		// 第一步: 查收收/送货（符合查询条件的单据，条件 不包括 商品名称商品规格，1月1日到所选日期）
		List<BillDetail> bills = this.casCheckDao.getBillDetail(
				conditionBalance, startDate, endDate);
		System.out.println("wss test bills.size:" + bills.size());
		// 对应关系Map
		Map<String, FactoryAndFactualCustomsRalation> ralationMap = new HashMap();
		// 对应关系
		FactoryAndFactualCustomsRalation ralation = null;
		String key = "";
		String code = "";
		int month = 0;
		for (BillDetail bill : bills) {
			code = bill.getBillMaster().getBillType().getCode();// 单据类型号码
			month = bill.getBillMaster().getValidDate().getMonth() + 1;// 单据体生效月
			key = getBillDetailKey(bill); // key 为单据体的工厂料号 + 工厂名称 + 工厂规格
			// System.out.println(" wss key:" + key);
			if (!ralationMap.containsKey(key)) {
				ralation = casCheckDao
						.getBillDetailFactoryAndFactualCustomsRalation(bill
								.getPtPart(), bill.getPtName(), bill
								.getPtSpec());// 可能为null哦
				ralationMap.put(key, ralation);// 将 单据体所代表工厂物料 与相应的 工厂和实际客户对应表
				// 建立关联
			} else {
				ralation = ralationMap.get(key);
			}
			// 排除 名称规格 与所选名称规则条件 不符的 ，则设置对应关系为null,不进行统计
			if (!"".equals(conditionBalance.getName())) {// 如果 商品名称 不为空
				if (ralation == null
						|| ralation.getStatCusNameRelationHsn() == null
						|| !conditionBalance.getName().equals(
								ralation.getStatCusNameRelationHsn()
										.getCusName())) {
					ralation = null;
					ralationMap.put(key, ralation);
				}
			}
			if (!"".equals(conditionBalance.getSpec())) {// 如果 商品规格 不为空
				if (ralation == null
						|| ralation.getStatCusNameRelationHsn() == null
						|| !conditionBalance.getSpec().equals(
								ralation.getStatCusNameRelationHsn()
										.getCusSpec())) {
					ralation = null;
					ralationMap.put(key, ralation);
				}
			}
			if (ralation != null) {// 进行统计（没有对应关系的不进行统计）
				key = getCustomsKey(bill, ralation); // 客户\供应商编码 + 商品编码 + 商品名称 +
				// 商品规格
				System.out.println("wss key :" + key);
				if (mapCarry.containsKey(key)) {
					carrys = mapCarry.get(key);
				} else {
					carrys = createCarrayList(bill.getBillMaster().getScmCoc(),
							ralation.getStatCusNameRelationHsn().getComplex()
									.getCode(), ralation
									.getStatCusNameRelationHsn().getCusName(),
							ralation.getStatCusNameRelationHsn().getCusSpec(),
							ralation.getStatCusNameRelationHsn().getCusUnit()
									.getName(), conditionBalance.isM());
					mapCarry.put(key, carrys);// 将 与单据体相应的 海关商品 与 相应的 结转差额总表 记录
					// 相关联
				}
				// 收/送货数量
				carry = carrys.get(0);

				System.out.println("wss code :" + code);
				System.out.println("wss bill.gethsAmount :"
						+ bill.getHsAmount());
				
				if ("1015".equals(code) || "2011".equals(code)) {// 期初单
					// 1015已收货未结转期初单
					// 2011已交货未结转期初单
					// carry.setAmountFirst(CommonUtils
					// .getDoubleExceptNull(carry.getAmountFirst())
					// + bill.getPtAmount()
					// * CommonUtils.getDoubleExceptNull(ralation
					// .getUnitConvert())); //为工厂和实际客户对应表中的 物料与实际报关的折算系数
					carry.setAmountFirst(CommonUtils.getDoubleExceptNull(carry
							.getAmountFirst())
							+ bill.getHsAmount()); // 为工厂和实际客户对应表中的 物料与实际报关的折算系数
				} else if ("1004".equals(code) || "2102".equals(code)) {// 加
					// 1004结转进口
					// 2102结转出口
					// setCarryAmountMonth(carry, month,
					// CommonUtils.getDoubleExceptNull(bill.getPtAmount())
					// *
					// CommonUtils.getDoubleExceptNull(ralation.getUnitConvert()));
					setCarryAmountMonth(carry, month, CommonUtils
							.getDoubleExceptNull(bill.getHsAmount()));
				} else if ("1106".equals(code) || "2009".equals(code)) {// 减
					// 1106结转料件退货单
					// 2009结转成品退货单
					// setCarryAmountMonth(carry, month, -1
					// * CommonUtils.getDoubleExceptNull(bill.getPtAmount())
					// *
					// CommonUtils.getDoubleExceptNull(ralation.getUnitConvert()));
					setCarryAmountMonth(carry, month, -1
							* CommonUtils.getDoubleExceptNull(bill
									.getHsAmount()));
				} else if ("1016".equals(code) || "2012".equals(code)) {// 加
					// 1016已结转未收货期初单
					// 2012已结转未交货期初单
					carry = carrys.get(1);// 结转数量
					// carry.setAmountFirst(CommonUtils
					// .getDoubleExceptNull(carry.getAmountFirst())
					// + bill.getPtAmount()
					// *
					// CommonUtils.getDoubleExceptNull(ralation.getUnitConvert()));
					carry.setAmountFirst(CommonUtils.getDoubleExceptNull(carry
							.getAmountFirst())
							+ bill.getHsAmount());
				}
			}
		}
		// 第二步 查结转报关单数量(包括三种监管方式)
		// 查询符合条件的单据体（1月1日 至 所选日期）
		List<BaseCustomsDeclarationCommInfo> cusInfos = casCheckDao
				.findCustomsDeclarationCommInfo(conditionBalance, startDate,
						endDate); // 料件转厂\转厂出口
		for (BaseCustomsDeclarationCommInfo info : cusInfos) {
			// 如果报关单没有客户/供应商 ，则不统计，跳过
			if (info.getBaseCustomsDeclaration().getCustomer() == null) {
				continue;
			}
			key = getCustomsKey(info);// 客户/供应商代码 + 商品编码 + 商品名称 + 商品规格
			System.out.println("统计KEY:" + key);
			month = info.getBaseCustomsDeclaration().getDeclarationDate()
					.getMonth() + 1; // 报关单生效当月
			if (mapCarry.containsKey(key)) {
				carrys = mapCarry.get(key);
			} else {
				carrys = createCarrayList(info.getBaseCustomsDeclaration()
						.getCustomer(), info.getComplex().getCode(), info
						.getCommName(), info.getCommSpec(), info.getUnit()
						.getName(), conditionBalance.isM());
				mapCarry.put(key, carrys);
			}
			carry = carrys.get(1);// 结转数量
			setCarryAmountMonth(carry, month, CommonUtils
					.getDoubleExceptNull(info.getCommAmount()));
		}
		// 第三步 查关封数量
		// 1.深加工结转明细
		List<FptAppItem> items = casCheckDao.findFptAppItem(conditionBalance,
				startDate, endDate);
		System.out.println(" wss 关封   深加工结存明细 items.size = " + items.size());
		for (FptAppItem item : items) {
			key = getCustomsKey(item);
			System.out.println("wss 关封 深加工结存明细 key:" + key);
			// 以转出（入）申报日期为准
			if (conditionBalance.isM()) {
				month = item.getFptAppHead().getInDate().getMonth() + 1;
			} else {
				month = item.getFptAppHead().getOutDate().getMonth() + 1;
			}

			if (mapCarry.containsKey(key)) {
				carrys = mapCarry.get(key);
			} else {
				carrys = createCarrayList(item.getFptAppHead().getScmCoc(),
						item.getCodeTs().getCode(), item.getName(), item
								.getSpec(), item.getUnit().getName(),
						conditionBalance.isM());
				mapCarry.put(key, carrys);
			}
			carry = carrys.get(3);// 深加工结转
			System.out.println("wss 关封 深加式结存明细 item.getQty =" + item.getQty());
			setCarryAmountMonth(carry, month, CommonUtils
					.getDoubleExceptNull(item.getQty()));// 当月结转数量
		}
		// 2.关封管理
		List<CustomsEnvelopCommodityInfo> infos = casCheckDao
				.findCustomsEnvelopCommodityInfo(conditionBalance, startDate,
						endDate);
		System.out.println(" wss 关封   关封管理 infos.size = " + infos.size());
		for (CustomsEnvelopCommodityInfo item : infos) {
			key = getCustomsKey(item);
			System.out.println("wss 关封 关封管理 key:" + key);
			// 以生效日期为准
			month = item.getCustomsEnvelopBill().getBeginAvailability()
					.getMonth() + 1;
			if (mapCarry.containsKey(key)) {
				carrys = mapCarry.get(key);
			} else {
				carrys = createCarrayList(item.getCustomsEnvelopBill()
						.getScmCoc(), item.getComplex().getCode(), item
						.getPtName(), item.getPtSpec(), item.getUnit()
						.getName(), conditionBalance.isM());
				mapCarry.put(key, carrys);
			}

			carry = carrys.get(3);// 转厂关封

			System.out.println("wss 关封 关封管理 item.getOwnerQuantity ="
					+ item.getOwnerQuantity());
			setCarryAmountMonth(carry, month, CommonUtils
					.getDoubleExceptNull(item.getOwnerQuantity()));
			System.out.println("wss now amount = " + carry.getAmountMonth4());
		}
		return mapCarry;
	}

	/**
	 * 统计结果第几月数量
	 * 
	 * @param carry
	 *            结果类型
	 * @param month
	 *            第几月
	 * @param amount
	 *            数量
	 * @param
	 * @author chenSir
	 */
	private void setCarryAmountMonth(CarryBalance carry, int month,
			Double amount) {
		switch (month) {
		case 1:
			carry.setAmountMonth1(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth1())
					+ amount);
			break;
		case 2:
			carry.setAmountMonth2(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth2())
					+ amount);
			break;
		case 3:
			carry.setAmountMonth3(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth3())
					+ amount);
			break;
		case 4:
			carry.setAmountMonth4(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth4())
					+ amount);
			break;
		case 5:
			carry.setAmountMonth5(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth5())
					+ amount);
			break;
		case 6:
			carry.setAmountMonth6(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth6())
					+ amount);
			break;
		case 7:
			carry.setAmountMonth7(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth7())
					+ amount);
			break;
		case 8:
			carry.setAmountMonth8(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth8())
					+ amount);
			break;
		case 9:
			carry.setAmountMonth9(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth9())
					+ amount);
			break;
		case 10:
			carry.setAmountMonth10(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth10())
					+ amount);
			break;
		case 11:
			carry.setAmountMonth11(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth11())
					+ amount);
			break;
		case 12:
			carry.setAmountMonth12(CommonUtils.getDoubleExceptNull(carry
					.getAmountMonth12())
					+ amount);
			break;
		default:
			return;
		}
	}

	/**
	 * 获取单据物料Key
	 * 
	 * @param bill
	 * @return
	 */
	public String getBillDetailKey(BillDetail bill) {
		return bill.getPtPart() + "/" + bill.getPtName() + "/"
				+ bill.getPtSpec();
	}

	/**
	 * 获取报关商品key
	 * 
	 * @return
	 */
	public String getCustomsKey(FactoryAndFactualCustomsRalation ralation,
			BaseCustomsDeclarationCommInfo info) {
		if (ralation != null) {
			return ralation.getStatCusNameRelationHsn().getComplex().getCode();
		}
		if (info != null) {
			return info.getComplex().getCode();
		}
		return "";
	}

	/**
	 * 创建一个商品的四种统计结果
	 * 
	 * @return
	 */
	private List<CarryBalance> createCarrayList(ScmCoc scmCoc, String code,
			String name, String spec, String unitName, boolean isM) {
		List<CarryBalance> list = new ArrayList();
		list.add(new CarryBalance(isM ? CarryBalance.SHOU_HUO
				: CarryBalance.SONG_HUO, scmCoc, code, name, spec, unitName));// 收/送货情况
		list.add(new CarryBalance(CarryBalance.JIE_ZHUAN, scmCoc, code, name,
				spec, unitName));// 结转情况
		list.add(new CarryBalance(CarryBalance.CHA_E, scmCoc, code, name, spec,
				unitName));// 差额情况
		list.add(new CarryBalance(CarryBalance.GUAN_FENG, scmCoc, code, name,
				spec, unitName));// 关封情况
		return list;
	}

	/**
	 * 结转折料查询
	 * 
	 * @param conditionBalance
	 * @param conditions
	 * @return 黄创亮
	 */
	public List getCurrentBalanceBOMInfo(ConditionBalance conditionBalance,
			List<Condition> conditions) {
		List list = null;
		list = getCurrentBalanceInfo(conditionBalance);
		return list;
	}

	/**
	 * 查询差额总表
	 * 
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
	public List getCurrentBalanceInfo(ConditionBalance conditionBalance) {
		// 最终返回结果
		List<CarryBalance> rs = new ArrayList();
		Map<String, List<CarryBalance>> mapCarry = getCarryMap(conditionBalance);
		List<CarryBalance> carrys = null;
		// 统计，排序
		CarryBalance first = null;
		CarryBalance second = null;
		CarryBalance third = null;
		CarryBalance fouth = null;
		int mouth = conditionBalance.getDate().getMonth() + 1;
		List<String> keySet = new ArrayList(mapCarry.keySet());
		Collections.sort(keySet);
		for (String keyS : keySet) {
			System.out.print("keys" + keyS);
			carrys = mapCarry.get(keyS);
			first = carrys.get(0);
			second = carrys.get(1);
			third = carrys.get(2);
			fouth = carrys.get(3);
			countResult(mouth, first, second, third, fouth);
			for (CarryBalance c : carrys) {
				rs.add(c);
			}
		}
		System.out.println("结果：" + rs.size());
		return rs;
	}
	
	/**
	 * 查询差额总表
	 * 
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
	public List getCurrentBalanceInfoNew(ConditionBalance conditionBalance) {
		// 最终返回结果
		List<CarryBalance> rs = new ArrayList();
		rs = casCheckDao.findCustomsEnvelopCommodityInfo(conditionBalance);
		System.out.println("结果：" + rs.size());
		return rs;
	}
	
	
	/**
	 * (海关帐)查询差额总表
	 * 
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
	public List getCasTransferfactoryDiffAllInfo(ConditionBalance conditionBalance) {
		// 最终返回结果
		List<CarryBalance> rs = new ArrayList();
		rs = casCheckDao.getCasTransferfactoryDiffAllInfo(conditionBalance);
		System.out.println("结果：" + rs.size());
		return rs;
	}
	
	/**
	 * 查询差额总表（台达）
	 * 
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
	public List getCurrentBalanceInfoTaiDa(ConditionBalance conditionBalance) {
		// 最终返回结果
		List<CarryBalance> rs = new ArrayList();
		rs = casCheckDao.findCustomsEnvelopCommodityInfoTaiDa(conditionBalance);
		return rs;
	}

	/**
	 * 查询差额分表
	 * 
	 * @param request
	 * @param casCheckAction
	 * @return
	 */
	public List getCurrentBalanceInfoTotal(ConditionBalance conditionBalance) {
		// 最终返回结果
		List<CarryBalance> rs = new ArrayList();
		Map<String, List<CarryBalance>> mapCarry = getCarryMap(conditionBalance);
		Map<String, List<CarryBalance>> rsMap = new HashMap();
		List<CarryBalance> carrys = null;
		List<CarryBalance> temps = null;
		String key = "";
		// 统计，排序
		CarryBalance first = null;
		CarryBalance second = null;
		CarryBalance third = null;
		CarryBalance fouth = null;
		int mouth = conditionBalance.getDate().getMonth() + 1;
		List<String> keySet = new ArrayList(mapCarry.keySet());
		for (String keyS : keySet) {
			System.out.print("keys" + keyS);
			carrys = mapCarry.get(keyS);// 一个string 对应 4 条carry
			first = carrys.get(0);
			second = carrys.get(1);
			third = carrys.get(2);
			fouth = carrys.get(3);
			countResult(mouth, first, second, third, fouth);
			key = getKeyTotal(keyS); // 取商品编码作为key
			System.out.println("key=" + key);
			temps = rsMap.get(key);
			if (temps == null) {
				temps = new ArrayList();
				temps.add(third); // 只要第3条数据（差额）
				rsMap.put(key, temps);
			} else {
				temps.add(third);// temps中装有与 商品编码key 相应的全部 差额carry,要的就是这个
			}
		}

		CarryBalance temp = null;
		for (String keyS : rsMap.keySet()) {
			temps = rsMap.get(keyS);
			temp = new CarryBalance();
			temp.setCustomerName("总计");
			for (CarryBalance c : temps) {
				rs.add(c);
				countTotal(mouth, temp, c);// 总计，该商品编码 相应的 所有供应商\客户 结转差额总额
			}
			rs.add(temp);
		}
		System.out.println("结果：" + rs.size());
		return rs;
	}

	// 统计结果
	private void countTotal(int mouth, CarryBalance old, CarryBalance now) {
		old.setName(now.getName());
		old.setSpec(now.getSpec());
		old.setUnitName(now.getUnitName());

		old.setAmountFirst(CommonUtils
				.getDoubleExceptNull(old.getAmountFirst())
				+ CommonUtils.getDoubleExceptNull(now.getAmountFirst()));
		old.setAmountMonth1(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth1())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth1()));
		if (mouth == 1) {
			return;
		}
		old.setAmountMonth2(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth2())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth2()));
		if (mouth == 2) {
			return;
		}
		old.setAmountMonth3(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth3())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth3()));
		if (mouth == 3) {
			return;
		}
		old.setAmountMonth4(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth4())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth4()));
		if (mouth == 4) {
			return;
		}
		old.setAmountMonth5(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth5())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth5()));
		if (mouth == 5) {
			return;
		}
		old.setAmountMonth6(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth6())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth6()));
		if (mouth == 6) {
			return;
		}
		old.setAmountMonth7(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth7())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth7()));
		if (mouth == 7) {
			return;
		}
		old.setAmountMonth8(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth8())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth8()));
		if (mouth == 8) {
			return;
		}
		old.setAmountMonth9(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth9())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth9()));
		if (mouth == 9) {
			return;
		}
		old.setAmountMonth10(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth10())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth10()));
		if (mouth == 10) {
			return;
		}
		old.setAmountMonth11(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth11())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth11()));
		if (mouth == 11) {
			return;
		}
		old.setAmountMonth12(CommonUtils.getDoubleExceptNull(old
				.getAmountMonth12())
				+ CommonUtils.getDoubleExceptNull(now.getAmountMonth12()));
	}

	/**
	 * 获取分表统计key,格式：客户code/商品code 我们取商品code
	 * 
	 * @param key
	 * @return
	 */
	private String getKeyTotal(String key) {
		if (key.indexOf("/") > 0) {
			return key.substring(key.indexOf("/") + 1, key.length());
		}
		return key;
	}

	/**
	 * 统计差额
	 * 
	 * @author chenSir
	 */
	private void countResult(int mouth, CarryBalance first,
			CarryBalance second, CarryBalance third, CarryBalance fouth) {

		// 1.统计每月差额数量/年总数量
		countMouth(mouth, first, second, third, fouth);
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth12()));

		// 2.third 差额 差额总值为 最后一个月的月度差额
		switch (mouth) {
		case 1:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth1()));//
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth1()));
			break;
		case 2:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth2()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth2()));
			break;
		case 3:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth3()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth3()));
			break;
		case 4:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth4()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth4()));
			break;
		case 5:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth5()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth5()));
			break;
		case 6:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth6()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth6()));
			break;
		case 7:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth7()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth7()));
			break;
		case 8:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth8()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth8()));
			break;
		case 9:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth9()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth9()));
			break;
		case 10:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth10()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth10()));
			break;
		case 11:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth11()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth11()));
			break;
		case 12:
			// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth12()));
			third.setTotal(CommonUtils.getDoubleExceptNull(third
					.getAmountMonth12()));
			break;
		default:
			break;
		}

		// 差额总数
		// first.setTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth12()));
		// second.setTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth12()));
		// third.setTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth12()));
		// fouth.setTotal(CommonUtils.getDoubleExceptNull(third.getAmountMonth12()));

		first.setTotal(CommonUtils.getDoubleExceptNull(third.getTotal()));
		second.setTotal(CommonUtils.getDoubleExceptNull(third.getTotal()));
		third.setTotal(CommonUtils.getDoubleExceptNull(third.getTotal()));
		fouth.setTotal(CommonUtils.getDoubleExceptNull(third.getTotal()));

		// 关封余量 (待定)
		// fouth.setRemain(CommonUtils.getDoubleExceptNull(first.getAmountFirst())
		// +CommonUtils.getDoubleExceptNull(first.getYearTotal())
		// -CommonUtils.getDoubleExceptNull(fouth.getAmountFirst())
		// -CommonUtils.getDoubleExceptNull(fouth.getYearTotal()));

		// fouth.setRemain(CommonUtils.getDoubleExceptNull(
		// CommonUtils.getDoubleExceptNull(first.getYearTotal())
		// - CommonUtils.getDoubleExceptNull(fouth.getYearTotal())));
		//    	
		// first.setRemain(CommonUtils.getDoubleExceptNull(fouth.getRemain()));
		// second.setRemain(CommonUtils.getDoubleExceptNull(fouth.getRemain()));
		// third.setRemain(CommonUtils.getDoubleExceptNull(fouth.getRemain()));

		// 日期
		if (fouth.getDate() != null) {
			fouth.setDate(CommonUtils.getDateAfterNDay(fouth.getDate(), 90));
		}
		first.setDate(fouth.getDate());
		second.setDate(fouth.getDate());
		third.setDate(fouth.getDate());
	}

	/**
	 * 统计每月差额数量/年总数量
	 * 
	 * @author chenSir
	 */
	private void countMouth(int mouth, CarryBalance first, CarryBalance second,
			CarryBalance third, CarryBalance fouth) {
		// first收\送货年度合计为 上月年度合计 + 本月收\送货数量
		// second结转年度合计为 上月年度合计 + 本月结转货数量
		// third差额 每月数量为 上月数量 + （first收送货数量 - second 转接数量）
		// third差额 年度合计为 上月年度合计+ 本月差额数量
		// fouth关封年度合计为 上月年度合计 + 本月关封数量

		third.setAmountFirst(CommonUtils.getDoubleExceptNull(first
				.getAmountFirst())
				- CommonUtils.getDoubleExceptNull(second.getAmountFirst()));// 计算差额期初
		// =
		// 送货期初
		// —
		// 结转期初
		// first.setYearTotal(CommonUtils.getDoubleExceptNull(first.getYearTotal())+CommonUtils.getDoubleExceptNull((first.getAmountFirst())));
		// second.setYearTotal(CommonUtils.getDoubleExceptNull(second.getYearTotal())+CommonUtils.getDoubleExceptNull((second.getAmountFirst())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountFirst())));
		// fouth.setYearTotal(CommonUtils.getDoubleExceptNull(fouth.getYearTotal())+CommonUtils.getDoubleExceptNull((fouth.getAmountFirst())));

		third.setAmountMonth1(CommonUtils.getDoubleExceptNull(third
				.getAmountFirst())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth1())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth1()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth1())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth1())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth1())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth1())));
		if (mouth == 1) {
			return;
		}

		third.setAmountMonth2(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth1())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth2())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth2()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth2())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth2())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth2())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth2())));
		if (mouth == 2) {
			return;
		}

		third.setAmountMonth3(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth2())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth3())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth3()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth3())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth3())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth3())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth3())));
		if (mouth == 3) {
			return;
		}

		third.setAmountMonth4(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth3())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth4())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth4()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth4())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth4())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth4())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth4())));
		if (mouth == 4) {
			return;
		}

		third.setAmountMonth5(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth4())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth5())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth5()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth5())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth5())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth5())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth5())));
		if (mouth == 5) {
			return;
		}

		third.setAmountMonth6(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth5())

				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth6())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth6()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth6())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth6())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth6())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth6())));
		if (mouth == 6) {
			return;
		}

		third.setAmountMonth7(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth6())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth7())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth7()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth7())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth7())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth7())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth7())));
		if (mouth == 7) {
			return;
		}

		third.setAmountMonth8(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth7())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth8())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth8()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth8())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth8())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth8())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth8())));
		if (mouth == 8) {
			return;
		}

		third.setAmountMonth9(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth8())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth9())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth9()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth9())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth9())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth9())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth9())));
		if (mouth == 9) {
			return;
		}

		third.setAmountMonth10(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth9())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth10())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth10()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth10())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth10())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth10())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth10())));
		if (mouth == 10) {
			return;
		}

		third.setAmountMonth11(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth10())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth11())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth11()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth11())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth11())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth1())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth11())));
		if (mouth == 11) {
			return;
		}

		third.setAmountMonth12(CommonUtils.getDoubleExceptNull(third
				.getAmountMonth11())
				+ CommonUtils.getDoubleExceptNull(first.getAmountMonth12())
				- CommonUtils.getDoubleExceptNull(second.getAmountMonth12()));
		first.setYearTotal(CommonUtils
				.getDoubleExceptNull(first.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((first.getAmountMonth12())));
		second.setYearTotal(CommonUtils.getDoubleExceptNull(second
				.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((second.getAmountMonth12())));
		// third.setYearTotal(CommonUtils.getDoubleExceptNull(third.getYearTotal())+CommonUtils.getDoubleExceptNull((third.getAmountMonth12())));
		fouth.setYearTotal(CommonUtils
				.getDoubleExceptNull(fouth.getYearTotal())
				+ CommonUtils.getDoubleExceptNull((fouth.getAmountMonth12())));

	}

	/**
	 * 获取报关商品key
	 * 
	 * @return
	 * @author chenSir
	 */
	public String getCustomsKey(BillDetail bill,
			FactoryAndFactualCustomsRalation ralation) {
		StatCusNameRelationHsn srh = ralation.getStatCusNameRelationHsn();
		return bill.getBillMaster().getScmCoc().getCode() + "/"
				+ srh.getComplex().getCode() + "/" + srh.getCusName() + "/"
				+ srh.getCusSpec();
	}

	/**
	 * 获取报关商品key 客户/供应商编码 + 报关商品编码
	 * 
	 * @return
	 */
	public String getCustomsKey(BaseCustomsDeclarationCommInfo info) {
		return info.getBaseCustomsDeclaration().getCustomer().getCode() + "/"
				+ info.getComplex().getCode() + "/" + info.getCommName() + "/"
				+ info.getCommSpec();
	}

	/**
	 * 获取报关商品key
	 * 
	 * @returnFptAppItem
	 * @author chenSir
	 */
	public String getCustomsKey(FptAppItem info) {
		System.out.print("wss 客户供应商代码：");
		System.out.println(info.getFptAppHead().getScmCoc().getCode() + "/");
		System.out.println("wss 商品信息：" + info.getCodeTs().getCode() + "/"
				+ info.getName() + "/" + info.getSpec());

		return info.getFptAppHead().getScmCoc().getCode() + "/"
				+ info.getCodeTs().getCode() + "/" + info.getName() + "/"
				+ info.getSpec();
	}

	/**
	 * 获取报关商品key
	 * 
	 * @returnFptAppItem
	 * @author chenSir
	 */
	public String getCustomsKey(CustomsEnvelopCommodityInfo info) {
		return info.getCustomsEnvelopBill().getScmCoc().getCode() + "/"
				+ info.getComplex().getCode() + "/" + info.getPtName() + "/"
				+ info.getPtSpec();
	}

	/**
	 * 结转折料查询
	 * 
	 * @param conditionBalance成品条件
	 * @param conditionsMaterial料件条件
	 * @param conditionsContract合同条件
	 * @param contractType
	 * @return 黄创亮
	 */

	public List getCurrentBalanceBOMInfo(ConditionBalance conditionBalance,
			List<Condition> conditionsMaterial,
			List<Condition> conditionsContract, int contractType) {
		// 抓取成品信息
		List<CarryBalance> listAll = null;
		List<CarryBalance> list = new ArrayList();
		listAll = getCurrentBalanceInfo(conditionBalance);
		System.out.println("listAll.size()=" + listAll.size());

		if (listAll == null)
			return new ArrayList();
		for (int i = 0; i < listAll.size(); i++) {
			if (i % 4 == 2) {
				list.add(listAll.get(i));
				System.out.println("getTotal()=" + listAll.get(i).getTotal());
			}
		}
		System.out.println("list.size()=" + list.size());

		// -------------------电子化手册
		if (contractType == 0) {
			List<ContractBom> contractBoms = new ArrayList<ContractBom>();
			List productBoms = new ArrayList<ProductBOMtoMateriel>();
			// 查找合同过滤的BOM
			contractBoms = casCheckDao.findContractBomByConditionsBCS(
					conditionsMaterial, conditionsContract, conditionBalance);

			System.out.println("contractBoms.size()=" + contractBoms.size());

			if (contractBoms == null)
				return new ArrayList();
			for (ContractBom contractBom : contractBoms) {
				for (int i = 0; i < list.size(); i++) {
					if ((list.get(i).getName() + list.get(i).getSpec())
							.equals(contractBom.getContractExg().getName()
									+ contractBom.getContractExg().getSpec())) {
						productBoms.add(setProductBomBCS(list.get(i),
								contractBom));
					}
				}
			}
			list = productBoms;
		}

		// -------------电子手册
		else if (contractType == 1) {
			List<DzscEmsBomBill> contractBoms = new ArrayList<DzscEmsBomBill>();
			List productBoms = new ArrayList<ProductBOMtoMateriel>();
			contractBoms = casCheckDao.findContractBomByConditionsZDSC(
					conditionsMaterial, conditionsContract);
			System.out.println("contractBoms.size()=" + contractBoms.size());
			if (contractBoms == null)
				return new ArrayList();
			for (DzscEmsBomBill contractBom : contractBoms) {
				for (int i = 0; i < list.size(); i++) {
					if ((list.get(i).getName() + list.get(i).getSpec())
							.equals(contractBom.getDzscEmsExgBill().getName()
									+ contractBom.getDzscEmsExgBill().getSpec()))
						productBoms.add(setProductBomDZSC(list.get(i),
								contractBom));
				}
			}
			list = productBoms;
		}

		// ------------电子帐册
		else if (contractType == 2) {
			List<EmsHeadH2kBom> contractBoms = new ArrayList<EmsHeadH2kBom>();
			List<EmsHeadH2kBom> addBoms = new ArrayList<EmsHeadH2kBom>();
			List productBoms = new ArrayList<ProductBOMtoMateriel>();
			contractBoms = casCheckDao.findContractBomByConditionsBCUS(
					conditionBalance, conditionsMaterial, conditionsContract);
			System.out.println("contractBoms.size()=" + contractBoms.size());
			if (contractBoms == null)
				return new ArrayList();
			Map<Integer, Integer> viersionMap = new HashMap<Integer, Integer>();
			Integer num = 0;
			Integer viersion = 0;
			Integer key = 0;
			Integer value = 0;
			// 查找版本最大的BOM的版本值放进MAP
			for (EmsHeadH2kBom bom : contractBoms) {
				key = bom.getEmsHeadH2kVersion().getEmsHeadH2kExg().getSeqNum();
				value = bom.getEmsHeadH2kVersion().getVersion();
				if (key != null)
					viersion = viersionMap.get(key);
				else
					continue;
				if (viersion == null) {
					viersionMap.put(key, value);
				} else if (value > viersion)
					viersionMap.put(key, value);
			}
			// 过滤BOM
			EmsHeadH2kBom bom = null;
			for (int i = 0; i < contractBoms.size(); i++) {
				bom = contractBoms.get(i);
				key = bom.getEmsHeadH2kVersion().getEmsHeadH2kExg().getSeqNum();
				value = bom.getEmsHeadH2kVersion().getVersion();
				if (value.equals(viersionMap.get(key))) {
					addBoms.add(bom);
				}
			}
			// 转化成中间类
			for (EmsHeadH2kBom contractBom : addBoms) {
				for (int i = 0; i < list.size(); i++) {
					if ((list.get(i).getName() + list.get(i).getSpec())
							.equals(contractBom.getEmsHeadH2kVersion()
									.getEmsHeadH2kExg().getName()
									+ contractBom.getEmsHeadH2kVersion()
											.getEmsHeadH2kExg().getSpec()))
						productBoms.add(setProductBomDZSC(list.get(i),
								contractBom));
				}
			}
			list = productBoms;

			list = productBoms;

		}

		return list;
	}

	/**
	 * 电子帐册转化折料中间类
	 * 
	 * @param carryBalance
	 * @param contractBom
	 * @return
	 */
	private Object setProductBomDZSC(CarryBalance carryBalance,
			EmsHeadH2kBom contractBom) {
		ProductBOMtoMateriel productBom = new ProductBOMtoMateriel();

		productBom.setCustomerName(carryBalance.getCustomerName());
		productBom.setProductName(carryBalance.getName());
		productBom.setProductSpec(carryBalance.getSpec());
		productBom.setProductUnitName(carryBalance.getUnitName());
		productBom.setProductAmount(carryBalance.getTotal());
		productBom.setMaterialName(contractBom.getName());
		productBom.setMaterialSpec(contractBom.getSpec());
		productBom.setMaterialUnitName(contractBom.getUnit().getName());
		productBom.setUnitWaste(contractBom.getUnitWear());
		productBom.setWaste(contractBom.getWear());
		productBom.setUnitDosage(contractBom.getUnitWear()
				/ (1 - contractBom.getWear()));
		if (productBom.getUnitDosage() != null
				&& productBom.getProductAmount() != null)
			productBom.setUnitAmout(productBom.getUnitDosage()
					* productBom.getProductAmount());
		productBom.setContractNo(contractBom.getEmsHeadH2kVersion()
				.getEmsHeadH2kExg().getEmsHeadH2k().getEmsNo());
		return productBom;
	}

	/**
	 * 电子手册转化折料中间类
	 * 
	 * @param carryBalance
	 * @param contractBom
	 * @return
	 */
	private Object setProductBomDZSC(CarryBalance carryBalance,
			DzscEmsBomBill contractBom) {
		ProductBOMtoMateriel productBom = new ProductBOMtoMateriel();

		productBom.setCustomerName(carryBalance.getCustomerName());
		productBom.setProductName(carryBalance.getName());
		productBom.setProductSpec(carryBalance.getSpec());
		productBom.setProductUnitName(carryBalance.getUnitName());
		productBom.setProductAmount(carryBalance.getTotal());
		productBom.setMaterialName(contractBom.getName());
		productBom.setMaterialSpec(contractBom.getSpec());
		productBom.setMaterialUnitName(contractBom.getUnit().getName());
		productBom.setUnitWaste(contractBom.getUnitWare());
		productBom.setWaste(contractBom.getWare());
		productBom.setUnitDosage(contractBom.getUnitDosage());
		if (productBom.getUnitDosage() != null
				&& productBom.getProductAmount() != null)
			productBom.setUnitAmout(contractBom.getUnitDosage()
					* productBom.getProductAmount());
		productBom.setContractNo(contractBom.getDzscEmsExgBill()
				.getDzscEmsPorHead().getIeContractNo());
		return productBom;
	}

	/**
	 * 电子化手册转化折料中间类
	 * 
	 * @param carryBalance
	 * @param contractBom
	 * @return
	 */
	private ProductBOMtoMateriel setProductBomBCS(CarryBalance carryBalance,
			ContractBom contractBom) {
		ProductBOMtoMateriel productBom = new ProductBOMtoMateriel();

		productBom.setCustomerName(carryBalance.getCustomerName());
		productBom.setProductName(carryBalance.getName());
		productBom.setProductSpec(carryBalance.getSpec());
		productBom.setProductUnitName(carryBalance.getUnitName());
		productBom.setProductAmount(carryBalance.getTotal());
		productBom.setMaterialName(contractBom.getName());
		productBom.setMaterialSpec(contractBom.getSpec());
		productBom.setMaterialUnitName(contractBom.getUnit().getName());
		productBom.setUnitWaste(contractBom.getUnitWaste());
		productBom.setWaste(contractBom.getWaste());
		productBom.setUnitDosage(contractBom.getUnitDosage());
		if (contractBom.getUnitDosage() != null
				&& productBom.getProductAmount() != null)
			productBom.setUnitAmout(contractBom.getUnitDosage()
					* productBom.getProductAmount());
		productBom.setContractNo(contractBom.getContractExg().getContract()
				.getImpContractNo());
		return productBom;
	}

	public List findMertailOrFinishedProductHs(String materiel, int type,
			String[] contracts) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = casCheckDao.findMertailOrFinishedProductHs(materiel,
				type, contracts);
		if (sourceList != null)
			for (int i = 0; i < sourceList.size(); i++) {
				Object[] objs = (Object[]) sourceList.get(i);
				if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
					continue;
				}
				TempObject temp = new TempObject();
				temp.setObject((String) objs[0]); // 名称
				temp.setObject1((String) objs[1]);// 规格
				temp.setObject2((Unit) objs[2]);// 单位

				list.add(temp);
			}
		return list;

	}

	public List<TempObject> findMertailOrFinishedProductSpecHs(String materiel,
			String product) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casCheckDao.findMertailOrFinishedProductHs(
				materiel, product);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * wss抄改
	 * 
	 * 把list转换为HashMap，list(o)为key,list(1)value
	 * 
	 * @param list
	 *            要转换的list
	 * @return HashMap
	 */
	private HashMap<String, Double> converListToHashTableBySecond(List list) {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String key = (objs[0] == null ? "" : objs[0].toString());
			Double value = objs[2] == null ? 0 : Double.parseDouble(objs[2]
					.toString());
			// System.out.println("::::::::" + key + "|||||||" + value);
			hm.put(key, value);
		}
		return hm;
	}

	/**
	 * wss:抄改 把list转换为HashMap，list(o)为key,list(1)value
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
	 * 合同分析（总的）
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            合同状态
	 * @param isCountInvoice
	 *            是否统计发票数量
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @author wss抄改
	 */

	public List<ImpMaterialStat> findImpMaterialStatByContracts(List contracts,
			Date beginDate, Date endDate, int state, boolean isCountInvoice,
			boolean isDetachCompute) {
		if (isDetachCompute) {
			System.out.println("分开统计工作");
			List<ImpMaterialStat> lsResult = new ArrayList<ImpMaterialStat>();
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
				List<ImpMaterialStat> lsData = findImpMaterialStat(contract,
						beginDate, endDate, state, isCountInvoice);
				for (ImpMaterialStat ims : lsData) {
					ims.setEmsNo(contract.getEmsNo());
					ims.setImpContractNo(contract.getImpContractNo());
					lsResult.add(ims);
				}
			}
			return lsResult;
		} else {
			if (contracts.size() == 1) {
				Contract contract = (Contract) contracts.get(0);
				List<ImpMaterialStat> lsData = findImpMaterialStatForOne(
						contract, beginDate, endDate, state, isCountInvoice);
				for (ImpMaterialStat ims : lsData) {
					ims.setEmsNo("");
				}
				return lsData;
			} else {
				Map map = new HashMap();
				for (int i = 0; i < contracts.size(); i++) {
					Contract contract = (Contract) contracts.get(i);
					List<ImpMaterialStat> lsData = findImpMaterialStat(
							contract, beginDate, endDate, state, isCountInvoice);
					for (ImpMaterialStat newData : lsData) {
						newData.setEmsNo("");
						// String key = (newData.getComplex().getCode().trim())
						// wss:2010.04.16修改
						String key = (newData.getComplex() == null ? ""
								: newData.getComplex().getCode() == null ? ""
										: newData.getComplex().getCode().trim())
								+ (newData.getCommName() == null ? "" : newData
										.getCommName().trim())
								+ (newData.getCommSpec() == null ? "" : newData
										.getCommSpec().trim())
								+ (newData.getUnit() == null ? "" : newData
										.getUnit().getName().trim());
						newData.setUnitPrice(null);
						newData.setCommodityInfoRemain(null);
						// newData.setCredenceNo(null);
						if (map.get(key) == null) {
							if (contracts.size() > 1) {
								newData.setSerialNo(null);
							}
							map.put(key, newData);
						} else {
							ImpMaterialStat oldData = (ImpMaterialStat) map
									.get(key);
							/**
							 * 未转报关单数量
							 */
							oldData
									.setNoTranCustomsNum((oldData
											.getNoTranCustomsNum() == null ? 0.0
											: oldData.getNoTranCustomsNum())
											+ (newData.getNoTranCustomsNum() == null ? 0.0
													: newData
															.getNoTranCustomsNum()));
							/**
							 * 余料进口
							 */
							oldData
									.setRemainImport((oldData.getRemainImport() == null ? 0.0
											: oldData.getRemainImport())
											+ (newData.getRemainImport() == null ? 0.0
													: newData.getRemainImport()));
							/**
							 * 余料转出
							 */
							oldData.setRemainForward((oldData
									.getRemainForward() == null ? 0.0 : oldData
									.getRemainForward())
									+ (newData.getRemainForward() == null ? 0.0
											: newData.getRemainForward()));
							/**
							 * 内销数量
							 */
							oldData
									.setInternalAmount((oldData
											.getInternalAmount() == null ? 0.0
											: oldData.getInternalAmount())
											+ (newData.getInternalAmount() == null ? 0.0
													: newData
															.getInternalAmount()));
							/**
							 * 料件退换进口数
							 */
							oldData
									.setExchangeImport((oldData
											.getExchangeImport() == null ? 0.0
											: oldData.getExchangeImport())
											+ (newData.getExchangeImport() == null ? 0.0
													: newData
															.getExchangeImport()));
							/**
							 * 边角料复出
							 */
							oldData
									.setLeftovermaterialExport((oldData
											.getLeftovermaterialExport() == null ? 0.0
											: oldData
													.getLeftovermaterialExport())
											+ (newData
													.getLeftovermaterialExport() == null ? 0.0
													: newData
															.getLeftovermaterialExport()));
							/**
							 * 边角料内销
							 */
							oldData
									.setLeftovermaterial((oldData
											.getLeftovermaterial() == null ? 0.0
											: oldData.getLeftovermaterial())
											+ (newData.getLeftovermaterial() == null ? 0.0
													: newData
															.getLeftovermaterial()));
							/**
							 * 总边角料内销
							 */
							oldData
									.setAllTotalleftovermaterial((oldData
											.getAllTotalleftovermaterial() == null ? 0.0
											: oldData
													.getAllTotalleftovermaterial())
											+ (newData
													.getAllTotalleftovermaterial() == null ? 0.0
													: newData
															.getAllTotalleftovermaterial()));
							/**
							 * 总边角料余量
							 */
							oldData
									.setLeftovermaterialremain((oldData
											.getLeftovermaterialremain() == null ? 0.0
											: oldData
													.getLeftovermaterialremain())
											+ (newData
													.getLeftovermaterialremain() == null ? 0.0
													: newData
															.getLeftovermaterialremain()));
							/**
							 * 成品使用金额
							 */
							oldData
									.setProductUseMoney((oldData
											.getProductUseMoney() == null ? 0.0
											: oldData.getProductUseMoney())
											+ (newData.getProductUseMoney() == null ? 0.0
													: newData
															.getProductUseMoney()));
							/**
							 * 关封余量
							 */
							oldData
									.setCustomsEnvelopRemain((oldData
											.getCustomsEnvelopRemain() == null ? 0.0
											: oldData.getCustomsEnvelopRemain())
											+ (newData
													.getCustomsEnvelopRemain() == null ? 0.0
													: newData
															.getCustomsEnvelopRemain()));
							/**
							 * 可直接进口量
							 */
							oldData
									.setCanDirectImportAmount((oldData
											.getCanDirectImportAmount() == null ? 0.0
											: oldData
													.getCanDirectImportAmount())
											+ (newData
													.getCanDirectImportAmount() == null ? 0.0
													: newData
															.getCanDirectImportAmount()));
							/**
							 * 总进口量
							 */
							oldData
									.setAllImpTotalAmont(oldData
											.getAllImpTotalAmont() == null ? 0.0
											: oldData.getAllImpTotalAmont()
													+ (newData
															.getAllImpTotalAmont() == null ? 0.0
															: newData
																	.getAllImpTotalAmont()));
							/**
							 * 合同定量
							 */
							oldData
									.setContractAmount(checkNullForDouble(oldData
											.getContractAmount())
											+ checkNullForDouble(newData
													.getContractAmount()));
							/**
							 * 总进口量
							 */
							oldData.setImpTotalAmont(checkNullForDouble(oldData
									.getImpTotalAmont()
									+ newData.getImpTotalAmont()));
							/**
							 * 本期总进口金额
							 */
							oldData.setImpTotalMoney(checkNullForDouble(oldData
									.getImpTotalMoney()
									+ newData.getImpTotalMoney()));
							/**
							 * 报关单进口量
							 */
							oldData.setImpCDAmount(checkNullForDouble(oldData
									.getImpCDAmount())
									+ checkNullForDouble(newData
											.getImpCDAmount()));
							/**
							 * 料件进口量
							 */
							oldData.setDirectImport(checkNullForDouble(oldData
									.getDirectImport())
									+ checkNullForDouble(newData
											.getDirectImport()));
							/**
							 * 转厂进口量
							 */
							oldData
									.setTransferFactoryImport(checkNullForDouble(oldData
											.getTransferFactoryImport())
											+ checkNullForDouble(newData
													.getTransferFactoryImport()));

							/**
							 * 退料退换量
							 */
							oldData
									.setBackMaterialExchange(checkNullForDouble(oldData
											.getBackMaterialExchange())
											+ checkNullForDouble(newData
													.getBackMaterialExchange()));
							/**
							 * 退料复出量
							 */
							oldData
									.setBackMaterialReturn(checkNullForDouble(oldData
											.getBackMaterialReturn())
											+ checkNullForDouble(newData
													.getBackMaterialReturn()));
							/**
							 * 退料出口量
							 */
							oldData
									.setBackMaterialExport(checkNullForDouble(oldData
											.getBackMaterialExport())
											+ checkNullForDouble(newData
													.getBackMaterialExport()));
							/**
							 * 成品使用量
							 */
							oldData.setProductUse(checkNullForDouble(oldData
									.getProductUse())
									+ checkNullForDouble(newData
											.getProductUse()));
							/**
							 * 余量
							 */
							oldData.setRemainAmount(checkNullForDouble(oldData
									.getRemainAmount())
									+ checkNullForDouble(newData
											.getRemainAmount()));
							/**
							 * 缺量
							 */
							oldData.setUllage(checkNullForDouble(oldData
									.getUllage())
									+ checkNullForDouble(newData.getUllage()));
							/**
							 * 库存
							 */
							oldData.setStockAmount(checkNullForDouble(oldData
									.getStockAmount())
									+ checkNullForDouble(newData
											.getStockAmount()));
							/**
							 * 可进口量
							 */
							oldData
									.setCanImportAmount(checkNullForDouble(oldData
											.getCanImportAmount())
											+ checkNullForDouble(newData
													.getCanImportAmount()));
							/**
							 * 余料金额
							 */
							oldData.setRemainMoney(checkNullForDouble(oldData
									.getRemainMoney())
									+ checkNullForDouble(newData
											.getRemainMoney()));
							/**
							 * 单价
							 */
							oldData.setUnitPrice(null);
						}
					}
				}
				List lsResult = new ArrayList(map.values());
				Collections.sort(lsResult);
				return lsResult;
			}
		}
	}

	/**
	 *合同分析（如果分开统计）
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	public List<ImpMaterialStat> findImpMaterialStat(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice) {
		List<ImpMaterialStat> lsResult = new ArrayList<ImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		// List<Integer> list = this.contractCavDao.findAllCommInfo(true, emsNo,
		// beginDate, endDate, state);
		List<ContractImg> list = this.casCheckDao
				.findContractImgByContractId(contract.getId()); // 该合同中料件list
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>(); // 序列号
		// 对应
		// 单项用量总数
		HashMap<String, Double> AllleftovermaterialUse = new HashMap<String, Double>();// 序列号
		// 对应
		// 损耗总数
		this.calContractImgUse(contract, hmImgUse, beginDate, endDate, state,
				AllleftovermaterialUse); // 计算成品所有的料件的耗用总量
		/**
		 * 关封余量-深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTableBySecond(this.casCheckDao
				.findFptAppItemCount(emsNo, true, beginDate, endDate));

		/**
		 * 可签关封
		 */
		HashMap<String, Double> hmCommodityInfoRemain = converListToHashTableBySecond(this.casCheckDao
				.findCommodityInfoRemain(emsNo, true, beginDate, endDate));

		/**
		 * 关封余量-转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTableBySecond(this.casCheckDao
				.findCustomsEnvelopCommInfoCount(emsNo, true, beginDate,
						endDate));
		List nset = new ArrayList(hmCustomsEnvelopTrans.keySet());
		for (int i = 0; i < nset.size(); i++) {
			String key = nset.get(i) == null ? "" : nset.get(i).toString();
			if (hmCustomsEnvelop.get(key) == null) {
				hmCustomsEnvelop.put(key == null ? "" : key.toString(),
						hmCustomsEnvelopTrans.get(key));
			} else {
				Double ss = hmCustomsEnvelop.get(key);
				Double dd = hmCustomsEnvelopTrans.get(key);
				hmCustomsEnvelop.put(key, (ss + dd));
			}
		}
		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmDirectImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 进口数量金额=进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmDirectImportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));

		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmTransferFactoryImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 转厂进口量金额=转厂进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmTransferFactoryImportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));

		/**
		 * 余料进口 (余料结转进口)
		 */
		HashMap<String, Double> hmRemainImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));// new String[] { "0657",
		// "0258" }
		/**
		 * 余料进口金额=余料进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmRemainImportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 余料出口 (余料结转出口)
		 */
		HashMap<String, Double> hmRemainForward = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
						beginDate, endDate, state));// new String[] {
		// "0657","0258" }

		/**
		 * 料件退换进口数
		 */
		HashMap<String, Double> hmExchangeImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT,
						new String[] { "0300", "0700" }, emsNo, beginDate,
						endDate, state));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmExchangeExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件退换出口数金额=料件退换出口数量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmExchangeExportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		 */
		HashMap<String, Double> hmBackMaterialReturn = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, beginDate, endDate, state));

		/**
		 * 退料出口量
		 */
		HashMap<String, Double> hmBackMaterialExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
						beginDate, endDate, state));

		/**
		 * 边角料内销海关批准内销--进出口报关单
		 */
		HashMap<String, Double> leftovermaterial = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0844", "0845" }, emsNo, beginDate, endDate,
						state));
		/**
		 * 边角料内销海关批准内销 --特殊报关单 edit by cjb 2009.11.30
		 */
		HashMap<String, Double> leftovermaterialSpecial = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.SPECIAL,
						ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES,
						new String[] { "0844", "0845" }, emsNo, beginDate,
						endDate, state));

		/**
		 * 边角料复出海关批准内销
		 */
		HashMap<String, Double> leftovermaterialExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0864",
								"0865" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件内销
		 */
		HashMap<String, Double> internalAmount = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0644", "0245" }, emsNo, beginDate, endDate,
						state));

		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmAllDirectImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, null, null,
						state));

		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmAllTransferFactoryImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo, null,
						null, state));
		/**
		 * 余料进口 (余料结转进口)
		 */
		HashMap<String, Double> hmAllRemainImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo, null,
						null, state));
		/**
		 * 未转报关单数量
		 */
		HashMap<String, Double> notranCustomsNum = converListToHashTable(this.casCheckDao
				.findNoTransCustomsNum(new Boolean(true), emsNo));

		// /**
		// * 余料出口 (余料结转出口)
		// */
		// HashMap<String, Double> hmAllRemainForward =
		// converListToHashTable(this.casCheckDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo, null,
		// null, state));// new String[] { "0657","0258" }

		/**
		 * 料件退换进口数
		 */
		HashMap<String, Double> hmAllExchangeImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT,
						new String[] { "0300", "0700" }, emsNo, null, null,
						state));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmAllExchangeExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, null, null, state));
		// /**
		// * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		// */
		// HashMap<String, Double> hmAllBackMaterialReturn =
		// converListToHashTable(this.casCheckDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
		// "0664" }, emsNo, null, null, state));
		// /**
		// * 退料出口量
		// */
		// HashMap<String, Double> hmAllBackMaterialExport =
		// converListToHashTable(this.casCheckDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo, null,
		// null, state));

		for (ContractImg contractImg : list) {
			if (contractImg != null && contractImg.getSeqNum() != null) {
				Integer commSerialNo = contractImg.getSeqNum();
				ImpMaterialStat impMaterialStat = new ImpMaterialStat();
				impMaterialStat.setSerialNo(contractImg.getSeqNum().toString());
				impMaterialStat.setComplex(contractImg.getComplex());
				impMaterialStat.setCommName(contractImg.getName());
				impMaterialStat.setCommSpec(contractImg.getSpec());
				impMaterialStat.setUnit(contractImg.getUnit());
				impMaterialStat.setUnitPrice(contractImg.getDeclarePrice());
				/**
				 * 未转报关单数量
				 */
				impMaterialStat.setNoTranCustomsNum(notranCustomsNum
						.get(commSerialNo.toString()));
				/**
				 * 合同定量
				 */
				impMaterialStat.setContractAmount(contractImg.getAmount());
				/**
				 * 料件进口量
				 */
				impMaterialStat.setDirectImport(hmDirectImport.get(commSerialNo
						.toString()));
				/**
				 * 总边角料
				 */
				impMaterialStat
						.setAllTotalleftovermaterial(AllleftovermaterialUse
								.get(commSerialNo == null ? "" : commSerialNo
										.toString()));
				/**
				 * 边角料内销
				 */
				impMaterialStat.setLeftovermaterial(CommonUtils
						.getDoubleExceptNull(leftovermaterial.get(commSerialNo
								.toString()))
						+ CommonUtils
								.getDoubleExceptNull(leftovermaterialSpecial
										.get(commSerialNo.toString())));
				/**
				 * 边角料复出
				 */
				impMaterialStat
						.setLeftovermaterialExport(leftovermaterialExport
								.get(commSerialNo.toString()));
				/**
				 * 料件内销
				 * 
				 */
				impMaterialStat.setInternalAmount(internalAmount
						.get(commSerialNo.toString()));
				/**
				 * 边角料余量
				 */
				double leftovermaterialremain = (CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getAllTotalleftovermaterial())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterial()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterialExport()));
				impMaterialStat
						.setLeftovermaterialremain(leftovermaterialremain);
				/**
				 * 转厂进口量
				 */
				impMaterialStat
						.setTransferFactoryImport(hmTransferFactoryImport
								.get(commSerialNo.toString()));

				/**
				 * 余料进口 (余料结转进口)
				 */
				impMaterialStat.setRemainImport(hmRemainImport.get(commSerialNo
						.toString()));

				/**
				 * 余料出口 (余料结转出口)
				 */
				impMaterialStat.setRemainForward(hmRemainForward
						.get(commSerialNo.toString()));

				/**
				 * 料件退换进口数
				 */
				impMaterialStat.setExchangeImport(hmExchangeImport
						.get(commSerialNo.toString()));

				/**
				 * 料件退换出口数
				 */
				impMaterialStat.setExchangeExport(hmExchangeExport
						.get(commSerialNo.toString()));

				/**
				 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
				 */
				impMaterialStat.setBackMaterialReturn(hmBackMaterialReturn
						.get(commSerialNo.toString()));
				/**
				 * 退料出口量
				 */
				impMaterialStat.setBackMaterialExport(hmBackMaterialExport
						.get(commSerialNo.toString()));
				Double allDirectImport = hmAllDirectImport.get(commSerialNo
						.toString());
				Double allRemainImport = hmAllRemainImport.get(commSerialNo
						.toString());
				Double allTransferFactoryImport = hmAllTransferFactoryImport
						.get(commSerialNo.toString());
				Double allExchangeExport = hmAllExchangeExport.get(commSerialNo
						.toString());

				Double DirectImportMoney = hmDirectImportMoney.get(commSerialNo
						.toString());
				Double RemainImportMoney = hmRemainImportMoney.get(commSerialNo
						.toString());
				Double TransferFactoryImportMoney = hmTransferFactoryImportMoney
						.get(commSerialNo.toString());
				Double ExchangeExportMoney = hmExchangeExportMoney
						.get(commSerialNo.toString());
				/**
				 * 进口总量=料件进口量+转厂进口量+料件退换进口量+余料进口-料件退换出口量(料件退换进口量已经包含在料件进口量中)
				 */
				impMaterialStat.setImpTotalAmont(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport())
						// + (impMaterialStat.getExchangeImport() == null ? 0.0
						// : impMaterialStat.getExchangeImport())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getExchangeExport()));
				/**
				 * 本期进口总金额
				 */
				impMaterialStat
						.setImpTotalMoney(CommonUtils
								.getDoubleExceptNull(DirectImportMoney)
								+ CommonUtils
										.getDoubleExceptNull(RemainImportMoney)
								+ CommonUtils
										.getDoubleExceptNull(TransferFactoryImportMoney)
								- CommonUtils
										.getDoubleExceptNull(ExchangeExportMoney));
				impMaterialStat.setAllImpTotalAmont(CommonUtils
						.getDoubleExceptNull(allDirectImport)
						+ CommonUtils.getDoubleExceptNull(allRemainImport)
						+ CommonUtils
								.getDoubleExceptNull(allTransferFactoryImport)
						// + CommonUtils.getDoubleExceptNull(allExchangeImport)
						- CommonUtils.getDoubleExceptNull(allExchangeExport));
				/**
				 * 成品使用量
				 */
				impMaterialStat.setProductUse(hmImgUse
						.get(commSerialNo == null ? "" : commSerialNo
								.toString()));
				// hmImgUse.remove(commSerialNo == null ? "" : commSerialNo
				// .toString());
				/**
				 * 成品使用金额
				 */
				impMaterialStat.setProductUseMoney((impMaterialStat
						.getProductUse() == null ? 0.0 : impMaterialStat
						.getProductUse())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				double remainAmount = (CommonUtils
						.getDoubleExceptNull(impMaterialStat.getImpTotalAmont())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialReturn())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getProductUse()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getInternalAmount()));
				/**
				 * 余量=进口总量-退运出口(复出)-成品使用量-内销数量
				 */
				impMaterialStat.setRemainAmount(remainAmount);
				// if (remainAmount > 0) {
				// // 余量=进口总量-成品使用量
				// impMaterialStat.setRemainAmount(remainAmount);
				// } else {
				// // 不足量=成品使用量-进口总量
				// impMaterialStat.setUllage(-remainAmount);
				// }
				/**
				 * 库存量=余量-余料结转出口
				 */
				impMaterialStat.setStockAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getRemainAmount())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainForward()));
				/**
				 * 可进口量 = 合同定量-总进口量
				 */
				impMaterialStat.setCanImportAmount((impMaterialStat
						.getContractAmount() == null ? 0.0 : impMaterialStat
						.getContractAmount())
						- (impMaterialStat.getImpTotalAmont() == null ? 0.0
								: impMaterialStat.getImpTotalAmont()));

				/**
				 * 比例=(可进口量/合同定量)*100
				 */
				impMaterialStat
						.setScale(((impMaterialStat.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount()) / (impMaterialStat
								.getContractAmount() == null ? 0.0
								: impMaterialStat.getContractAmount())) * 100);
				/**
				 * 余料金额=余量*单价
				 */
				impMaterialStat.setRemainMoney((impMaterialStat
						.getRemainAmount() == null ? 0.0 : impMaterialStat
						.getRemainAmount())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				impMaterialStat.setCountry(contractImg.getCountry());
				// impMaterialStat.setMaterialType(contractImg.getMaterialType());
				// impMaterialStat
				// .setSerialNo(contractImg.getCredenceNo() == null ? ""
				// : contractImg.getCredenceNo().toString());
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString());
				/**
				 * 关封余量=关封申请总量—转厂进口量
				 */
				impMaterialStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (impMaterialStat.getTransferFactoryImport() == null ? 0.0
										: impMaterialStat
												.getTransferFactoryImport()));
				/**
				 * 可签关封=料件合同定量-已签关封数量
				 */
				impMaterialStat.setCommodityInfoRemain(CommonUtils
						.getDoubleExceptNull(contractImg.getAmount())
						- CommonUtils.getDoubleExceptNull(hmCommodityInfoRemain
								.get(commSerialNo.toString())));
				/**
				 * 可直接进口量=可进口量-关封余量
				 */
				impMaterialStat
						.setCanDirectImportAmount((impMaterialStat
								.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount())
								- (impMaterialStat.getCustomsEnvelopRemain() == null ? 0.0
										: impMaterialStat
												.getCustomsEnvelopRemain()));
				/**
				 * 报关单进口量==料件进口量+转厂进口量
				 */
				impMaterialStat.setImpCDAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport()));
				/**
				 * 是否统计发票数量
				 */
				if (isCountInvoice) {
					System.out.println("this is test brgin");
					Double invoiceCount = this.casCheckDao
							.findCasInvoiceInfoNum(contract.getEmsNo(),
									commSerialNo);
					impMaterialStat.setStockAmount((impMaterialStat
							.getStockAmount() == null ? 0.0 : impMaterialStat
							.getStockAmount())
							+ invoiceCount);
					impMaterialStat.setRemainAmount((impMaterialStat
							.getRemainAmount() == null ? 0.0 : impMaterialStat
							.getRemainAmount()));
					// + invoiceCount);
					impMaterialStat.setInvoiceNum(invoiceCount);
				}
				impMaterialStat.setEmsNo(contract.getEmsNo());
				impMaterialStat.setCredenceNo(contractImg.getCredenceNo());// 凭证序号
				lsResult.add(impMaterialStat);
			}
		}
		Collections.sort(lsResult);
		return lsResult;
	}

	/**
	 * 合同分析（只有一个合同）
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @author wss抄改
	 */
	public List<ImpMaterialStat> findImpMaterialStatForOne(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice) {
		List<ImpMaterialStat> lsResult = new ArrayList<ImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		// List<Integer> list = this.casCheckDao.findAllCommInfo(true, emsNo,
		// beginDate, endDate, state);
		List<ContractImg> list = this.casCheckDao
				.findContractImgByContractId(contract.getId());
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		HashMap<String, Double> AllleftovermaterialUse = new HashMap<String, Double>();
		this.calContractImgUse(contract, hmImgUse, beginDate, endDate, state,
				AllleftovermaterialUse);
		/**
		 * 关封余量
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTableBySecond(this.casCheckDao
				.findFptAppItemCount(emsNo, true, beginDate, endDate));

		/**
		 * 可签关封
		 */
		HashMap<String, Double> hmCommodityInfoRemain = converListToHashTableBySecond(this.casCheckDao
				.findCommodityInfoRemain(emsNo, true, beginDate, endDate));
		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmDirectImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));

		/**
		 * 进口数量金额=进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmDirectImportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmTransferFactoryImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 转厂进口量金额=转厂进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmTransferFactoryImportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 余料进口 (余料结转进口)
		 */
		HashMap<String, Double> hmRemainImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));// new String[] { "0657",
		// "0258" }
		/**
		 * 余料进口金额=余料进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmRemainImportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 余料出口 (余料结转出口)
		 */
		HashMap<String, Double> hmRemainForward = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
						beginDate, endDate, state));// new String[] {
		// "0657","0258" }

		/**
		 * 料件退换进口数
		 */
		HashMap<String, Double> hmExchangeImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT,
						new String[] { "0300", "0700" }, emsNo, beginDate,
						endDate, state));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmExchangeExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件退换出口数金额=料件退换出口数量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmExchangeExportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		 */
		HashMap<String, Double> hmBackMaterialReturn = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, beginDate, endDate, state));

		/**
		 * 退料出口量
		 */
		HashMap<String, Double> hmBackMaterialExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
						beginDate, endDate, state));

		// HashMap<String, Double> hmAllImgUse = new HashMap<String, Double>();
		// this.calContractImgUse(contract, hmAllImgUse, null, null, state);
		// 关封余量
		// HashMap<String, Double> hmAllCustomsEnvelop =
		// converListToHashTable(this.casCheckDao
		// .findCustomsEnvelopCommInfoCount(emsNo, null,null));
		/**
		 * 边角料内销海关批准内销--进出口报关单
		 */
		HashMap<String, Double> leftovermaterial = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0844", "0845" }, emsNo, beginDate, endDate,
						state));
		/**
		 * 边角料内销海关批准内销--特殊报关单
		 */
		HashMap<String, Double> leftovermaterialSpecial = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.SPECIAL,
						ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES,
						new String[] { "0844", "0845" }, emsNo, beginDate,
						endDate, state));
		/**
		 * 边角料复出海关批准内销
		 */
		HashMap<String, Double> leftovermaterialExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0864",
								"0865" }, emsNo, beginDate, endDate, state));

		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmAllDirectImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, null, null,
						state));

		/**
		 * 料件内销
		 */
		HashMap<String, Double> internalAmount = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0644", "0245" }, emsNo, beginDate, endDate,
						state));

		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmAllTransferFactoryImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo, null,
						null, state));
		/**
		 * 余料进口 (余料结转进口)
		 */
		HashMap<String, Double> hmAllRemainImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo, null,
						null, state));
		/**
		 * 未转报关单数量
		 */
		HashMap<String, Double> notranCustomsNum = converListToHashTable(this.casCheckDao
				.findNoTransCustomsNum(new Boolean(true), emsNo));

		/**
		 * 余料出口 (余料结转出口)
		 */
		HashMap<String, Double> hmAllRemainForward = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo, null,
						null, state));// new String[] { "0657","0258" }

		/**
		 * 料件退换进口数
		 */
		HashMap<String, Double> hmAllExchangeImport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT,
						new String[] { "0300", "0700" }, emsNo, null, null,
						state));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmAllExchangeExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, null, null, state));

		// /**
		// * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		// */
		// HashMap<String, Double> hmAllBackMaterialReturn =
		// converListToHashTable(this.casCheckDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
		// "0664" }, emsNo, null, null, state));
		// /**
		// * 退料出口量
		// */
		// HashMap<String, Double> hmAllBackMaterialExport =
		// converListToHashTable(this.casCheckDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo, null,
		// null, state));

		for (ContractImg contractImg : list) {
			if (contractImg != null && contractImg.getSeqNum() != null) {
				Integer commSerialNo = contractImg.getSeqNum();
				ImpMaterialStat impMaterialStat = new ImpMaterialStat();
				impMaterialStat.setSerialNo(contractImg.getSeqNum().toString());
				impMaterialStat.setComplex(contractImg.getComplex());
				impMaterialStat.setCommName(contractImg.getName());
				impMaterialStat.setCommSpec(contractImg.getSpec());
				impMaterialStat.setUnit(contractImg.getUnit());
				impMaterialStat.setUnitPrice(contractImg.getDeclarePrice());
				/**
				 * 未转报关单数量
				 */
				impMaterialStat.setNoTranCustomsNum(notranCustomsNum
						.get(commSerialNo.toString()));
				/**
				 * 合同定量
				 */
				impMaterialStat.setContractAmount(contractImg.getAmount());
				/**
				 * 料件进口量
				 */
				impMaterialStat.setDirectImport(hmDirectImport.get(commSerialNo
						.toString()));
				/**
				 * 转厂进口量
				 */
				impMaterialStat
						.setTransferFactoryImport(hmTransferFactoryImport
								.get(commSerialNo.toString()));

				/**
				 * 余料进口 (余料结转进口)
				 */
				impMaterialStat.setRemainImport(hmRemainImport.get(commSerialNo
						.toString()));

				/**
				 * 余料出口 (余料结转出口)
				 */
				impMaterialStat.setRemainForward(hmRemainForward
						.get(commSerialNo.toString()));

				/**
				 * 料件退换进口数
				 */
				impMaterialStat.setExchangeImport(hmExchangeImport
						.get(commSerialNo.toString()));
				/**
				 * 料件内销
				 */
				impMaterialStat.setInternalAmount(internalAmount
						.get(commSerialNo.toString()));
				/**
				 * 料件退换出口数
				 */
				impMaterialStat.setExchangeExport(hmExchangeExport
						.get(commSerialNo.toString()));

				/**
				 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
				 */
				impMaterialStat.setBackMaterialReturn(hmBackMaterialReturn
						.get(commSerialNo.toString()));
				/**
				 * 总边角料
				 */
				impMaterialStat
						.setAllTotalleftovermaterial(AllleftovermaterialUse
								.get(commSerialNo == null ? "" : commSerialNo
										.toString()));
				/**
				 * 边角料内销
				 */
				impMaterialStat.setLeftovermaterial(CommonUtils
						.getDoubleExceptNull(leftovermaterial.get(commSerialNo
								.toString()))
						+ CommonUtils
								.getDoubleExceptNull(leftovermaterialSpecial
										.get(commSerialNo.toString())));
				/**
				 * 边角料复出
				 */
				impMaterialStat
						.setLeftovermaterialExport(leftovermaterialExport
								.get(commSerialNo.toString()));
				/**
				 * 边角料余量
				 */
				double leftovermaterialremain = (CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getAllTotalleftovermaterial())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterial()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterialExport()));
				impMaterialStat
						.setLeftovermaterialremain(leftovermaterialremain);
				/**
				 * 退料出口量
				 */
				impMaterialStat.setBackMaterialExport(hmBackMaterialExport
						.get(commSerialNo.toString()));
				Double allDirectImport = hmAllDirectImport.get(commSerialNo
						.toString());
				Double allRemainImport = hmAllRemainImport.get(commSerialNo
						.toString());
				Double allTransferFactoryImport = hmAllTransferFactoryImport
						.get(commSerialNo.toString());
				Double allExchangeExport = hmAllExchangeExport.get(commSerialNo
						.toString());

				Double DirectImportMoney = hmDirectImportMoney.get(commSerialNo
						.toString());
				Double RemainImportMoney = hmRemainImportMoney.get(commSerialNo
						.toString());
				Double TransferFactoryImportMoney = hmTransferFactoryImportMoney
						.get(commSerialNo.toString());
				Double ExchangeExportMoney = hmExchangeExportMoney
						.get(commSerialNo.toString());
				/**
				 * 本期进口总量=料件进口量+转厂进口量+余料进口-料件退换出口量(料件退换进口量已经包含在料件进口量中)
				 */
				impMaterialStat.setImpTotalAmont(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport())
						// + (impMaterialStat.getExchangeImport() == null ? 0.0
						// : impMaterialStat.getExchangeImport())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getExchangeExport()));
				/**
				 * 本期进口总金额
				 */
				impMaterialStat
						.setImpTotalMoney(CommonUtils
								.getDoubleExceptNull(DirectImportMoney)
								+ CommonUtils
										.getDoubleExceptNull(RemainImportMoney)
								+ CommonUtils
										.getDoubleExceptNull(TransferFactoryImportMoney)
								- CommonUtils
										.getDoubleExceptNull(ExchangeExportMoney));
				/**
				 * 总进口量＝合同的所有直接进口＋合同的所有余料进口＋合同的所有转厂进口－合同的所有料件退换出口数（除去时间段的）
				 */
				impMaterialStat.setAllImpTotalAmont(CommonUtils
						.getDoubleExceptNull(allDirectImport)
						+ CommonUtils.getDoubleExceptNull(allRemainImport)
						+ CommonUtils
								.getDoubleExceptNull(allTransferFactoryImport)
						// + CommonUtils.getDoubleExceptNull(allExchangeImport)
						- CommonUtils.getDoubleExceptNull(allExchangeExport));
				/**
				 * 成品使用量
				 */
				impMaterialStat.setProductUse(hmImgUse
						.get(commSerialNo == null ? "" : commSerialNo
								.toString()));
				// hmImgUse.remove(commSerialNo == null ? "" : commSerialNo
				// .toString());
				/**
				 * 成品使用金额
				 */
				impMaterialStat.setProductUseMoney((impMaterialStat
						.getProductUse() == null ? 0.0 : impMaterialStat
						.getProductUse())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				double remainAmount = (CommonUtils
						.getDoubleExceptNull(impMaterialStat.getImpTotalAmont())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialReturn())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getProductUse()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getInternalAmount()));
				/**
				 * 余量=进口总量-退运出口(复出)-成品使用量-料件内销
				 */
				impMaterialStat.setRemainAmount(remainAmount);
				// if (remainAmount > 0) {
				// // 余量=进口总量-成品使用量
				// impMaterialStat.setRemainAmount(remainAmount);
				// } else {
				// // 不足量=成品使用量-进口总量
				// impMaterialStat.setUllage(-remainAmount);
				// }
				/**
				 * 库存量=余量-余料结转出口
				 */
				impMaterialStat.setStockAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getRemainAmount())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainForward()));
				/**
				 * 可进口量 = 合同定量-总进口量
				 */
				impMaterialStat.setCanImportAmount((impMaterialStat
						.getContractAmount() == null ? 0.0 : impMaterialStat
						.getContractAmount())
						- (impMaterialStat.getImpTotalAmont() == null ? 0.0
								: impMaterialStat.getImpTotalAmont()));

				/**
				 * 比例
				 */
				impMaterialStat
						.setScale(((impMaterialStat.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount()) / (impMaterialStat
								.getContractAmount() == null ? 0.0
								: impMaterialStat.getContractAmount())) * 100);
				/**
				 * 余料金额(折算汇率的金额)
				 */
				// impMaterialStat.setRemainMoney((impMaterialStat
				// .getRemainAmount() == null ? 0.0 : impMaterialStat
				// .getRemainAmount())
				// * (impMaterialStat.getUnitPrice() == null ? 0.0
				// : impMaterialStat.getUnitPrice()));
				impMaterialStat.setRemainMoney(RemainImportMoney == null ? 0.0
						: RemainImportMoney);

				impMaterialStat.setCountry(contractImg.getCountry());
				// impMaterialStat.setMaterialType(contractImg.getMaterialType());
				// impMaterialStat
				// .setSerialNo(contractImg.getCredenceNo() == null ? ""
				// : contractImg.getCredenceNo().toString());
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString());
				/**
				 * 关封余量=关封申请总量—转厂进口量
				 */
				impMaterialStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (impMaterialStat.getTransferFactoryImport() == null ? 0.0
										: impMaterialStat
												.getTransferFactoryImport()));
				/**
				 * 可签关封=料件合同定量-已签关封数量
				 */
				impMaterialStat.setCommodityInfoRemain(CommonUtils
						.getDoubleExceptNull(contractImg.getAmount())
						- CommonUtils.getDoubleExceptNull(hmCommodityInfoRemain
								.get(commSerialNo.toString())));
				/**
				 * 可直接进口量=可进口量-关封余量
				 */
				impMaterialStat
						.setCanDirectImportAmount((impMaterialStat
								.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount())
								- (impMaterialStat.getCustomsEnvelopRemain() == null ? 0.0
										: impMaterialStat
												.getCustomsEnvelopRemain()));
				/**
				 * 报关单进口量==料件进口量+转厂进口量
				 */
				impMaterialStat.setImpCDAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport()));
				/**
				 * 是否统计发票数量
				 */
				if (isCountInvoice) {
					System.out.println("this is end");
					Double invoiceCount = this.casCheckDao
							.findCasInvoiceInfoNum(contract.getEmsNo(),
									commSerialNo);
					System.out.println("invoiceCount=" + invoiceCount);
					impMaterialStat.setStockAmount((impMaterialStat
							.getStockAmount() == null ? 0.0 : impMaterialStat
							.getStockAmount())
							+ invoiceCount);
					System.out.println("stockAmount="
							+ (impMaterialStat.getStockAmount() == null ? 0.0
									: impMaterialStat.getStockAmount())
							+ invoiceCount);
					impMaterialStat.setRemainAmount((impMaterialStat
							.getRemainAmount() == null ? 0.0 : impMaterialStat
							.getRemainAmount()));
					// + invoiceCount);
					impMaterialStat.setInvoiceNum(invoiceCount);
				}
				impMaterialStat.setEmsNo(contract.getEmsNo());
				impMaterialStat.setCredenceNo(contractImg.getCredenceNo());// 凭证序号
				lsResult.add(impMaterialStat);
			}
		}
		Collections.sort(lsResult);
		return lsResult;
	}

	/**
	 * 检查是否为空，如果是则返回0
	 * 
	 * @param dou
	 *            小数Double
	 * @return
	 */
	private Double checkNullForDouble(Double dou) {
		return dou == null ? 0.0 : dou;
	}

	/**
	 * 计算成品所有的料件的耗用总量
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param hmImgUse
	 *            合同备案BOM资料对应的数量
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @author wss抄改
	 */
	private void calContractImgUse(Contract contract,
			HashMap<String, Double> hmImgUse, Date beginDate, Date endDate,
			int state, HashMap<String, Double> AllleftovermaterialUse) {
		List<ExpProductStat> lsProductStat = this.findExpProductStat(contract,
				beginDate, endDate, state); // 出口成品情况统计表
		String declareState = "";
		for (ExpProductStat productStat : lsProductStat) {
			double exgAmount = CommonUtils.getDoubleExceptNull(productStat
					.getExpTotalAmont()); // 总出口数
			// System.out.println("sssssssssss:" +productStat.getCommName()+
			// exgAmount);
			if ("5".equals(contract.getDeclareState())) {
				declareState = DeclareState.CHANGING_CANCEL;
			} else if (!"".equals(contract.getDeclareState())
					&& !"5".equals(contract.getDeclareState())) {
				declareState = DeclareState.CHANGING_EXE;
			}
			List<ContractBom> list = this.casCheckDao.findBomByExg(contract
					.getEmsNo(), Integer.valueOf(productStat.getSerialNo()),
					declareState);
			for (ContractBom contractBom : list) {
				if (contractBom.getContractImgSeqNum() == null) {
					// System.out.println("eeeeeeeeeeeeeeeeeeee: is null");
					continue;
				}
				// double imgAmount = exgAmount
				// * CommonUtils.getDoubleExceptNull(contractBom
				// .getUnitDosage());
				double imgAmount = exgAmount // 单项用量总数
						* CommonUtils
								.getDoubleExceptNull(CommonUtils
										.getDoubleExceptNull(contractBom
												.getUnitWaste())
										/ (1 - CommonUtils
												.getDoubleExceptNull(contractBom
														.getWaste()) / 100.0));
				double hasAmount = CommonUtils
						.getDoubleExceptNull(hmImgUse
								.get(contractBom.getContractImgSeqNum() == null ? ""
										: contractBom.getContractImgSeqNum()
												.toString()));
				double haswear = CommonUtils //
						.getDoubleExceptNull(AllleftovermaterialUse
								.get(contractBom.getContractImgSeqNum() == null ? ""
										: contractBom.getContractImgSeqNum()
												.toString()));
				hmImgUse.put((contractBom.getContractImgSeqNum() == null ? ""
						: contractBom.getContractImgSeqNum().toString()),
						imgAmount + hasAmount);// 料件序列号 对应 该成品下的单项总用量 合计
				AllleftovermaterialUse // 损耗总数
						.put(
								(contractBom.getContractImgSeqNum() == null ? ""
										: contractBom.getContractImgSeqNum()
												.toString()),
								imgAmount
										* (CommonUtils
												.getDoubleExceptNull(contractBom
														.getWaste()) / 100.0)
										+ haswear);
			}
		}
	}

	/**
	 * wss：抄改 出口成品情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	public List<ExpProductStat> findExpProductStat(Contract contract,
			Date beginDate, Date endDate, int state) {
		List<ExpProductStat> lsResult = new ArrayList<ExpProductStat>();
		String emsNo = contract.getEmsNo();
		// List list = this.contractCavDao.findAllCommInfo(false, emsNo,
		// beginDate, endDate, state);
		List<ContractExg> list = this.casCheckDao
				.findContractExgByParentId(contract.getId()); // 查找合同成品list
		/**
		 * 关封余量－深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTableBySecond(this.casCheckDao
				.findFptAppItemCount(emsNo, false, beginDate, endDate));
		/**
		 * 关封余量-转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTableBySecond(this.casCheckDao
				.findCustomsEnvelopCommInfoCount(emsNo, false, beginDate,
						endDate));
		List nset = new ArrayList(hmCustomsEnvelopTrans.keySet());
		for (int i = 0; i < nset.size(); i++) {
			String key = nset.get(i) == null ? "" : nset.get(i).toString();
			if (hmCustomsEnvelop.get(key) == null) {
				hmCustomsEnvelop.put(key == null ? "" : key.toString(),
						hmCustomsEnvelopTrans.get(key));
			} else {
				Double ss = hmCustomsEnvelop.get(key);
				Double dd = hmCustomsEnvelopTrans.get(key);
				hmCustomsEnvelop.put(key, (ss + dd));
			}
		}
		/**
		 * 成品出口数量
		 */
		HashMap<String, Double> hmDirectExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.DIRECT_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 出口数量金额=出口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmDirectExportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.DIRECT_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 转厂出口数量
		 */
		HashMap<String, Double> hmTransferFactoryExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 转厂出口量金额=转厂出口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmTransferFactoryExportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 退厂返工数量
		 */
		HashMap<String, Double> hmBackFactoryRework = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 退厂返工金额=退厂返工数量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmBackFactoryReworkMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 返工复出数量
		 */
		HashMap<String, Double> hmReworkExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REWORK_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 返工复出金额=返工复量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmReworkExportMoney = converListToHashTable(this.casCheckDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.REWORK_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 成品出口数量
		 */
		HashMap<String, Double> hmAllDirectExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.DIRECT_EXPORT, null, emsNo, null, null,
						state));
		/**
		 * 转厂出口数量
		 */
		HashMap<String, Double> hmAllTransferFactoryExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo, null,
						null, state));
		/**
		 * 退厂返工数量
		 */
		HashMap<String, Double> hmAllBackFactoryRework = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, emsNo, null,
						null, state));
		/**
		 * 返工复出数量
		 */
		HashMap<String, Double> hmAllReworkExport = converListToHashTable(this.casCheckDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REWORK_EXPORT, null, emsNo, null, null,
						state));

		/**
		 * 未转报关单数量
		 */
		HashMap<String, Double> notranCustomsNum = converListToHashTable(this.casCheckDao
				.findNoTransCustomsNum(new Boolean(false), emsNo));

		for (ContractExg contractExg : list) {
			if (contractExg != null && contractExg.getSeqNum() != null) {
				Integer commSerialNo = contractExg.getSeqNum();
				ExpProductStat expProductStat = new ExpProductStat();
				expProductStat.setSerialNo(contractExg.getSeqNum().toString());
				expProductStat.setComplex(contractExg.getComplex());
				expProductStat.setCommName(contractExg.getName());
				expProductStat.setCommSpec(contractExg.getSpec());
				expProductStat.setUnit(contractExg.getUnit());
				expProductStat.setUnitPrice(contractExg.getUnitPrice());
				/**
				 * 未转报关单数量
				 */
				expProductStat.setNoTranCustomsNum(notranCustomsNum
						.get(commSerialNo.toString()));

				/**
				 * 合同定量
				 */
				expProductStat.setContractAmount(contractExg.getExportAmount());
				/**
				 * 成品出口
				 */
				expProductStat.setDirectExport(hmDirectExport.get(commSerialNo
						.toString()));
				/**
				 * 转厂出口
				 */
				expProductStat.setTransferFactoryExport(hmTransferFactoryExport
						.get(commSerialNo.toString()));
				/**
				 * 退厂返工数
				 */
				expProductStat.setBackFactoryRework(hmBackFactoryRework
						.get(commSerialNo.toString()));
				/**
				 * 返工复出数
				 */
				expProductStat.setReworkExport(hmReworkExport.get(commSerialNo
						.toString()));
				Double allDirectExport = hmAllDirectExport.get(commSerialNo
						.toString());
				Double allTransferFactoryExport = hmAllTransferFactoryExport
						.get(commSerialNo.toString());
				Double allBackFactoryRework = hmAllBackFactoryRework
						.get(commSerialNo.toString());
				Double allReworkExport = hmAllReworkExport.get(commSerialNo
						.toString());

				Double DirectExportMoney = hmDirectExportMoney.get(commSerialNo
						.toString());
				Double TransferFactoryExportMoney = hmTransferFactoryExportMoney
						.get(commSerialNo.toString());
				Double BackFactoryReworkMoney = hmBackFactoryReworkMoney
						.get(commSerialNo.toString());
				Double ReworkExportMoney = hmReworkExportMoney.get(commSerialNo
						.toString());
				CompanyOther other = CommonUtils.getOther();

				/**
				 * 本期总出口量=报关单直接出口量+转厂出口-退厂返工量+返工复出数
				 */
				expProductStat
						.setExpTotalAmont((expProductStat.getDirectExport() == null ? 0.0
								: expProductStat.getDirectExport())
								+ (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport())
								- (expProductStat.getBackFactoryRework() == null ? 0.0
										: expProductStat.getBackFactoryRework())
								+ (expProductStat.getReworkExport() == null ? 0.0
										: expProductStat.getReworkExport()));
				/**
				 * 本期总出口金额
				 */
				expProductStat
						.setExpTotalMoney(CommonUtils
								.getDoubleExceptNull(DirectExportMoney)
								+ CommonUtils
										.getDoubleExceptNull(TransferFactoryExportMoney)
								- CommonUtils
										.getDoubleExceptNull(BackFactoryReworkMoney)
								+ CommonUtils
										.getDoubleExceptNull(ReworkExportMoney));

				/**
				 * 总出口量
				 */
				expProductStat.setAllExpTotalAmont(CommonUtils
						.getDoubleExceptNull(allDirectExport)
						+ CommonUtils
								.getDoubleExceptNull(allTransferFactoryExport)
						- CommonUtils.getDoubleExceptNull(allBackFactoryRework)
						+ CommonUtils.getDoubleExceptNull(allReworkExport));
				// 比例=(总出口量/合同定量)*100
				if (expProductStat.getContractAmount() != null
						&& expProductStat.getContractAmount() != 0) {
					expProductStat
							.setScale(((expProductStat.getExpTotalAmont() == null ? 0.0
									: expProductStat.getExpTotalAmont()) / expProductStat
									.getContractAmount()) * 100);
				}
				// double canExportAmount =
				/**
				 * 可出口量 = 合同定量-本期总出口量
				 */
				expProductStat.setCanExportAmount((expProductStat
						.getContractAmount() == null ? 0.0 : expProductStat
						.getContractAmount())
						- (expProductStat.getExpTotalAmont() == null ? 0.0
								: expProductStat.getExpTotalAmont()));
				// if (canExportAmount >= 0) {
				// /**
				// * 可出口量 = 合同定量-报关单出口量
				// */
				// expProductStat.setCanExportAmount(canExportAmount);
				// } else {
				// /**
				// * 超量
				// */
				// expProductStat.setOverAmount(-canExportAmount);
				// }
				expProductStat.setProcessUnitPrice(contractExg
						.getProcessUnitPrice());
				expProductStat.setProcessTotalPrice(CommonUtils
						.getDoubleExceptNull(expProductStat.getExpTotalAmont())
						* CommonUtils.getDoubleExceptNull(expProductStat
								.getProcessUnitPrice()));
				expProductStat.setLegalAmount(contractExg.getLegalAmount());
				// expProductStat.setLegalUnit(contractExg.getComplex()
				// .getFirstUnit());
				// wss:2010.04.16修改
				expProductStat
						.setLegalUnit(contractExg.getComplex() == null ? null
								: contractExg.getComplex().getFirstUnit());
				expProductStat.setUnitWeight(contractExg.getUnitGrossWeight());
				expProductStat.setUnitGrossWeight(contractExg
						.getUnitGrossWeight());
				expProductStat.setUnitNetWeight(contractExg.getUnitNetWeight());
				expProductStat.setLevyMode(contractExg.getLevyMode());
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString());
				/**
				 * 关封余量=关封总量-转厂出口量
				 */
				expProductStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport()));

				/**
				 * 报关单出口量=成品出口量+转厂出口量
				 */
				expProductStat.setExpCDAmount(CommonUtils
						.getDoubleExceptNull(expProductStat.getDirectExport())
						+ CommonUtils.getDoubleExceptNull(expProductStat
								.getTransferFactoryExport()));

				/**
				 * 可直接出口量=合同定量-报关单出口量-关封余量
				 */
				expProductStat
						.setCanDirectExportAmount((expProductStat
								.getContractAmount() == null ? 0.0
								: expProductStat.getContractAmount())
								- (expProductStat.getExpCDAmount() == null ? 0.0
										: expProductStat.getExpCDAmount())
								- (expProductStat.getCustomsEnvelopRemain() == null ? 0.0
										: expProductStat
												.getCustomsEnvelopRemain()));

				expProductStat.setEmsNo(contract.getEmsNo());
				expProductStat.setCredenceNo(contractExg.getCredenceNo());
				lsResult.add(expProductStat);
			}
		}
		Collections.sort(lsResult);
		return lsResult;
	}

	/**
	 * wss：抄改 （用于电子帐册）
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

	/**
	 * wss：抄改（用于电子帐册）
	 * 
	 * 查询已报关的商品名称
	 * 
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfo(boolean isImport) {
		List list = casCheckDao.findCustomsDeclarationCommInfo(isImport);
		List lists = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] listObj = (Object[]) list.get(i);
			TempComplex obj = new TempComplex();
			String name = "";
			if (listObj[1] != null) {
				name += listObj[1].toString();
			}
			if (listObj[2] != null) {
				name += ("/" + listObj[2].toString());
			}
			obj.setCode(listObj[0].toString());// 序号
			obj.setName(name);// 名称/规格
			lists.add(obj);
		}
		return lists;
	}

	/**
	 * wss：抄改 查询已报关的客户
	 * 
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 以报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport) {
		return casCheckDao.findCustomsDeclarationCustomer(isImport);
	}

	/**
	 * wss:抄改
	 * 
	 * 不分事业部 进口料件情况统计表
	 * 
	 */
	public List findImpExpCommInfoUseNoDept(boolean isImport, Integer seqNum,
			String customer, String IEType, Date beginDate, Date endDate,
			boolean isDeclaration, int isEffect) {
		// 所有进口报关单
		List list = casCheckDao
				.findImpExpCommInfo(seqNum, customer, IEType, beginDate,
						endDate, false, isEffect, isDeclaration, false, null); // isDeclaration
		// :
		// 申报日期
		Hashtable ht = new Hashtable();
		List emslist = casCheckDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		findLjUseNum(hs, head, null, endDate, isEffect, isDeclaration, false,
				null);// 所有料件耗用量
		findLjUseNum(timehs, head, beginDate, endDate, isEffect, isDeclaration,
				false, null);// 本期料件耗用量
		findLjUseNumForTran(tranThs, head, beginDate, endDate, isEffect,
				isDeclaration, false, null);
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			Integer no = info.getCommSerialNo();
			System.out.println("**********************************" + no);
			String complex = null;
			if (info.getComplex() != null) {
				complex = info.getComplex().getCode();
			}
			String name = info.getCommName();
			String spec = info.getCommSpec();
			String unit = null;
			if (info.getUnit() != null) {
				unit = info.getUnit().getName();
			}
			Integer key = no;
			if (ht.get(key) != null) {
				temp = (BillTemp) ht.get(key);
			} else {
				temp = new BillTemp();
				temp.setBill1(String.valueOf(no));
				temp.setBill2(complex);
				temp.setBill3(name);
				temp.setBill9(spec);
				temp.setBill4(unit);
				ht.put(key, temp);
				Double allLjUse = Double.valueOf(0);
				Double allbjl = Double.valueOf(0);
				Double useNum = Double.valueOf(0);
				Double bjlNum = Double.valueOf(0);
				Double cpUseNum = Double.valueOf(0);
				Double tranUseNum = Double.valueOf(0);
				TempDD x = (TempDD) hs.get(String.valueOf(no));
				TempDD y = (TempDD) timehs.get(String.valueOf(no));
				TempDD z = (TempDD) tranThs.get(String.valueOf(no));
				if (x != null) {
					allLjUse = x.getAa();
					allbjl = x.getBb();
				}
				if (y != null) {
					useNum = y.getAa();
					bjlNum = y.getBb();
				}
				if (z != null) {
					cpUseNum = z.getAa();
					tranUseNum = z.getBb();
				}
				temp.setBillSum7(allLjUse);
				temp.setBillSum8(allbjl);// 边角料
				temp.setBillSum9(0 - allLjUse);
				temp.setBillSum13(useNum);// 本期成品使用量
				temp.setBillSum14(bjlNum);// 本期边角料
				temp.setBillSum16(cpUseNum);
				temp.setBillSum17(tranUseNum);
			}
			if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.DIRECT_IMPORT)) { // 直接进口-
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));

				temp.setBillSum2(fd(temp.getBillSum2())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						+ fd(info.getCommAmount()));

			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.TRANSFER_FACTORY_IMPORT)) { // 料件转厂
				temp.setBillSum5(fd(temp.getBillSum5())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				temp.setBillSum2(fd(temp.getBillSum2())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						+ fd(info.getCommAmount()));
			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.BACK_MATERIEL_EXPORT)) {// 退料出口
				temp.setBillSum6(fd(temp.getBillSum6())
						+ fd(info.getCommAmount()));
				temp.setBillSum2(fd(temp.getBillSum2())
						- fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						- fd(info.getCommAmount()));

			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.REMAIN_FORWARD_IMPORT)) {
				temp.setBillSum2(fd(temp.getBillSum2())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						+ fd(info.getCommAmount()));

				temp.setBillSum1(fd(temp.getBillSum1())
						+ fd(info.getCommAmount()));
			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.MATERIAL_DOMESTIC_SALES)) {
				temp.setBillSum18(fd(temp.getBillSum18())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						- fd(info.getCommAmount()));
			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.REMAIN_FORWARD_EXPORT)) {
				temp.setBillSum2(fd(temp.getBillSum2())
						- fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						- fd(info.getCommAmount()));
			}

			Double dollarUnitPrice = (info.getCommAmount() == null || (Double
					.valueOf(0)).equals(info.getCommAmount())) ? Double
					.valueOf(0) : (fd(info.getDollarTotalPrice()) / fd(info
					.getCommAmount()));
			Double billsum10 = (fd(temp.getBillSum4()) + fd(temp.getBillSum5())
					- fd(temp.getBillSum6()) - fd(temp.getBillSum7()))
					* dollarUnitPrice;
			temp.setBillSum10(billsum10);

			List listAmount = casCheckDao.findSumAmount(info.getCommSerialNo());
			Double ckAmount = getSumCkAmount(listAmount);
			temp.setBillSum11(ckAmount);
			temp.setBillSum12(fd(temp.getBillSum9()) - ckAmount);

		}
		// 查找电子帐册料件
		List emsImg = casCheckDao.findEmsEdiImg(seqNum);
		for (int j = 0; j < emsImg.size(); j++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) emsImg.get(j);
			Integer seqNum1 = img.getSeqNum();
			String complex1 = img.getComplex() == null ? "" : img.getComplex()
					.getCode();
			String name1 = img.getName();
			String spec1 = img.getSpec();
			String unit1 = img.getUnit() == null ? "" : img.getUnit().getName();
			Integer key1 = seqNum1;
			if (ht.get(key1) == null) {
				BillTemp temp = new BillTemp();
				temp.setBill1(String.valueOf(seqNum1));
				temp.setBill2(complex1);
				temp.setBill3(name1);
				temp.setBill9(spec1);
				temp.setBill4(unit1);
				ht.put(key1, temp);
				Double allLjUse = Double.valueOf(0);
				Double allbjl = Double.valueOf(0);
				Double useNum = Double.valueOf(0);
				Double bjlNum = Double.valueOf(0);
				Double cpUseNum = Double.valueOf(0);
				Double tranUseNum = Double.valueOf(0);
				TempDD x = (TempDD) hs.get(String.valueOf(img.getSeqNum()));
				TempDD y = (TempDD) timehs.get(String.valueOf(img.getSeqNum()));
				TempDD z = (TempDD) tranThs
						.get(String.valueOf(img.getSeqNum()));
				if (x != null) {
					allLjUse = x.getAa();
					allbjl = x.getBb();
				}
				if (y != null) {
					useNum = y.getAa();
					bjlNum = y.getBb();
				}
				if (z != null) {
					cpUseNum = z.getAa();
					tranUseNum = z.getBb();
				}
				temp.setBillSum13(useNum);// 本期成品耗用量
				temp.setBillSum7(allLjUse);// 成品耗用量
				temp.setBillSum8(allbjl);// 边角料
				temp.setBillSum14(bjlNum);// 本期边角料
				temp.setBillSum16(cpUseNum);
				temp.setBillSum17(tranUseNum);

				temp.setBillSum9(0 - useNum);
				List listAmount = casCheckDao.findSumAmount(img.getSeqNum());

				Double ckAmount = getSumCkAmount(listAmount);
				temp.setBillSum11(ckAmount);
				temp.setBillSum12(fd(temp.getBillSum9()) - ckAmount);
			}
		}

		ArrayList returnList = new ArrayList();
		TreeMap ts = new TreeMap();
		Enumeration e = ht.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			ts.put(new Integer(String.valueOf(key)), key);
		}
		Set st = ts.keySet();
		for (Iterator i = st.iterator(); i.hasNext();) {
			returnList.add(ht.get(ts.get(i.next())));
		}
		return returnList;
	}

	// --
	/**
	 * wss:抄改 （合同分析 电子帐册） 通过事业部过滤
	 */
	public void findLjUseNum(Hashtable hs, EmsHeadH2k head, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration, boolean isdept,
			String deptCode) {
		hs.clear();
		List list = casCheckDao.finLjUseNum(head, beginDate, endDate, isEffect,
				isDeclaration, isdept, deptCode);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String ljNo = String.valueOf(obj[2]);
			Double unitWear = fd((Double) obj[3]);
			Double wear = fd((Double) obj[4]);
			Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量
			Double fgfc = fd((Double) obj[5]);// 近工复出
			Double zcck = fd((Double) obj[6]);// 转厂出口
			Double zjck = fd((Double) obj[7]);// 直接出口
			Double tcfg = fd((Double) obj[8]);// 退厂返工

			Double useNum = fd(fgfc + zcck + zjck - tcfg);
			Double ljUseNum = useNum * unitUse;// 成品耗用量
			Double zchy = zcck * unitUse;// 转厂耗用量
			Double zjckhy = zjck * unitUse;// 直接出口耗用量
			Double bjlNum = ljUseNum * (wear * 0.01);// 边角料
			Double fgfcNum = fgfc * unitUse;
			Double tcfgNum = tcfg * unitUse;
			TempDD temp = null;
			if (hs.get(ljNo) == null) {
				temp = new TempDD();
				temp.setLjseqnum(ljNo);
				temp.setAa(ljUseNum);
				temp.setBb(bjlNum);
				temp.setZchy(zchy);
				temp.setZjckhy(zjckhy);
				temp.setTcfghy(tcfgNum);
				temp.setFgfchy(fgfcNum);
				hs.put(ljNo, temp);
			} else {
				temp = (TempDD) hs.get(ljNo);
				temp.setAa(fd(temp.getAa()) + ljUseNum);
				temp.setBb(fd(temp.getBb()) + bjlNum);
				temp.setZchy(fd(temp.getZchy()) + zchy);
				temp.setZjckhy(fd(temp.getZjckhy()) + zjckhy);
				temp.setTcfghy(fd(temp.getTcfghy()) + tcfgNum);
				temp.setFgfchy(fd(temp.getFgfchy()) + fgfcNum);
			}
		}
	}

	/**
	 * wss：抄改（合同分析 电子帐册）
	 * 
	 * @param hs
	 * @param head
	 * @param beginDate
	 * @param endDate
	 * @param isEffect
	 * @param isDeclaration
	 * @param isdept
	 * @param deptCode
	 */
	public void findLjUseNumForTran(Hashtable hs, EmsHeadH2k head,
			Date beginDate, Date endDate, int isEffect, boolean isDeclaration,
			boolean isdept, String deptCode) {
		hs.clear();
		List list = casCheckDao.finLjUseNumForTran(head, beginDate, endDate,
				isEffect, isDeclaration, isdept, deptCode);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String ljNo = String.valueOf(obj[2]);
			Double unitWear = fd((Double) obj[3]);
			Double wear = fd((Double) obj[4]);
			Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量

			Double useNum = fd((Double) obj[5]);
			Double tranNum = fd((Double) obj[6]);
			Double ljUseNum = useNum * unitUse;
			Double tranUseNum = tranNum * unitUse;
			TempDD temp = null;
			if (hs.get(ljNo) == null) {
				temp = new TempDD();
				temp.setLjseqnum(ljNo);
				temp.setAa(ljUseNum);
				temp.setBb(tranUseNum);
				hs.put(ljNo, temp);
			} else {
				temp = (TempDD) hs.get(ljNo);
				temp.setAa(fd(temp.getAa()) + ljUseNum);
				temp.setBb(fd(temp.getBb()) + tranUseNum);
			}
		}
	}

	private Double toD(Object obj) {
		if (obj == null) {
			return Double.valueOf(0);
		}
		return Double.valueOf(obj.toString());
	}

	/**
	 * wss:抄改 （合同分析 电子帐册） 得到仓库数据汇总
	 * 
	 * @param list
	 *            指定的对象
	 * @return 仓库数据的汇总
	 */
	public Double getSumCkAmount(List list) {
		Double sum = Double.valueOf(0);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Double amount = Double.valueOf(0);
			if (objs[0] != null) {
				amount = toD(objs[0]);
			}
			Double unitConvert = Double.valueOf(1);
			if (objs[1] != null) {
				unitConvert = toD(objs[1]);
			}
			if (unitConvert.equals(Double.valueOf(0))) {
				continue;
			}
			sum = (amount / unitConvert) + sum;
		}
		return sum;
	}

	/**
	 * wss：抄改 （合同分析 电子帐册） 不分事业部 成品执行情况统计表
	 * 
	 * @param isImport
	 * @param seqNum
	 * @param customer
	 * @param IEType
	 * @param beginDate
	 * @param endDate
	 * @param isDeclaration
	 * @param isEffect
	 * @return
	 */

	public List findImpExpCommInfoUseForExgNoDept(boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, int isEffect) {
		System.out.println("isImport->" + isImport);
		System.out.println("seqNum->" + seqNum);
		System.out.println("customer->" + customer);
		System.out.println("IEType->" + IEType);
		System.out.println("beginDate->" + beginDate);
		System.out.println("isDeclaration->" + isDeclaration);
		// 所有出口报关单
		List list = casCheckDao.findImpExpCommInfo(seqNum, customer, IEType,
				beginDate, endDate, true, isEffect, isDeclaration, false, null);// isDeclaration
		// : 是否申报
		Hashtable ht = new Hashtable();

		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			String no = String.valueOf(info.getCommSerialNo());
			System.out.println("s**********************************" + no);
			String complex = null;
			if (info.getComplex() != null) {
				complex = info.getComplex().getCode();
			}
			String name = info.getCommName();
			String spec = info.getCommSpec();
			String projectDeptName = info.getProjectDept() == null ? "" : info
					.getProjectDept().getName();
			String unit = null;
			if (info.getUnit() != null) {
				unit = info.getUnit().getName();
			}
			String version = info.getVersion();
			String key = no + "000" + (version == null ? "0" : version);

			if (ht.get(key) != null) {
				temp = (BillTemp) ht.get(key);
			} else {
				temp = new BillTemp();
				temp.setBill1(no);
				temp.setBill2(complex);
				temp.setBill3(name);
				temp.setBill9(spec);
				temp.setBill6(projectDeptName);
				ht.put(key, temp);
				temp.setBill4(unit);
				temp.setBill5(version);

			}
			Double money = fd(info.getCommAmount())
					* fd(info.getCommUnitPrice());
			temp.setBillSum2(fd(temp.getBillSum2()) + money);
			if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.DIRECT_EXPORT)) {
				temp.setBillSum5(fd(temp.getBillSum5())
						+ fd(info.getCommAmount()));
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.TRANSFER_FACTORY_EXPORT)) {
				temp.setBillSum6(fd(temp.getBillSum6())
						+ fd(info.getCommAmount()));
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.BACK_FACTORY_REWORK)) {
				temp.setBillSum7(fd(temp.getBillSum7())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						- fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType().equals(
					ImpExpType.REWORK_EXPORT)) {
				temp.setBillSum8(fd(temp.getBillSum8())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			}
		}
		List emsImg = casCheckDao.findEmsEdiExg(seqNum);
		for (int j = 0; j < emsImg.size(); j++) {
			EmsHeadH2kExg img = (EmsHeadH2kExg) emsImg.get(j);
			List vList = casCheckDao.findEmsVersion(img);
			for (int k = 0; k < vList.size(); k++) {
				EmsHeadH2kVersion version = (EmsHeadH2kVersion) vList.get(k);
				String vNo = String.valueOf(version.getVersion());
				String seqNum1 = String.valueOf(img.getSeqNum());

				String complex1 = img.getComplex() == null ? "" : img
						.getComplex().getCode();
				String name1 = img.getName();
				String spec1 = img.getSpec();
				String unit1 = img.getUnit() == null ? "" : img.getUnit()
						.getName();
				String key1 = seqNum1 + "000" + (vNo == null ? "0" : vNo);
				if (ht.get(key1) == null) {
					BillTemp temp = new BillTemp();
					temp.setBill1(seqNum1);
					temp.setBill2(complex1);
					temp.setBill3(name1);
					temp.setBill9(spec1);
					temp.setBill4(unit1);
					temp.setBill5(vNo);
					ht.put(key1, temp);
				}
			}

		}
		ArrayList returnList = new ArrayList();
		TreeMap ts = new TreeMap();
		Enumeration e = ht.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			ts.put(new Integer(String.valueOf(key)), key);
		}
		Set st = ts.keySet();
		for (Iterator i = st.iterator(); i.hasNext();) {
			returnList.add(ht.get(ts.get(i.next())));
		}
		return returnList;

	}

	/**
	 * 进口料件使用情况
	 * 
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            料件序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内符合条件的进口料件情况：同一个序号，可有多个事业部
	 * 
	 * @author wss抄改
	 */
	public List findImpExpCommInfoUseForDept(boolean isImport, Integer seqNum,
			String customer, String IEType, Date beginDate, Date endDate,
			boolean isDeclaration, String deptCode, int isEffect) { // --根据事业部来统计
		// boolean isEffect = true;
		List arrayList = new ArrayList();
		List list = casCheckDao.findEmsEdiImg(null);
		List deptList = casCheckDao.findAllDept(deptCode);// 获取所有事业部
		List emslist = casCheckDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		int size = deptList.size() + 1;
		for (int k = 0; k < size; k++) {// 循环事业部
			String projectDeptCode = null;
			String projectDeptName = null;
			if (k == (size - 1) && deptCode == null) {
				projectDeptCode = null;
				projectDeptName = null;
			} else if (k == (size - 1)) {
				continue;
			} else {
				ProjectDept pobj = (ProjectDept) deptList.get(k);
				if (pobj.getCode() == null || pobj.getCode().equals("")) {
					projectDeptCode = null;
					projectDeptName = null;
				} else {
					projectDeptCode = pobj.getCode();// 事业部代码
					projectDeptName = pobj.getName();

				}
			}
			findLjUseNum(hs, head, null, endDate, isEffect, isDeclaration,
					true, projectDeptCode);
			findLjUseNum(timehs, head, beginDate, endDate, isEffect,
					isDeclaration, true, projectDeptCode);

			findLjUseNumForTran(tranThs, head, beginDate, endDate, isEffect,
					isDeclaration, true, projectDeptCode);

			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
				Integer no = img.getSeqNum(); // 料件备案序号
				BillTemp temp = new BillTemp();
				temp.setBill1(String.valueOf(no));// 备案序号
				temp.setBill2(img.getComplex() == null ? null : img
						.getComplex().getCode());// 商品编码
				temp.setBill3(img.getName());
				temp.setBill9(img.getSpec());
				temp.setBill4(img.getUnit() == null ? "" : img.getUnit()
						.getName());

				Double allLjUse = Double.valueOf(0);
				Double allbjl = Double.valueOf(0);
				Double useNum = Double.valueOf(0);
				Double bjlNum = Double.valueOf(0);
				Double cpUseNum = Double.valueOf(0);
				Double tranUseNum = Double.valueOf(0);
				TempDD x = (TempDD) hs.get(String.valueOf(no));
				TempDD y = (TempDD) timehs.get(String.valueOf(no));
				TempDD z = (TempDD) tranThs.get(String.valueOf(no));
				if (x != null) {
					allLjUse = x.getAa();
					allbjl = x.getBb();
				}
				if (y != null) {
					useNum = y.getAa();
					bjlNum = y.getBb();
				}
				if (z != null) {
					cpUseNum = z.getAa();
					tranUseNum = z.getBb();
				}

				temp.setBillSum7(allLjUse);// 6.成品使用量
				temp.setBillSum8(allbjl);
				temp.setBillSum9(0 - allLjUse);
				temp.setBillSum13(useNum);
				temp.setBillSum14(bjlNum);
				temp.setBillSum16(cpUseNum);
				temp.setBillSum17(tranUseNum);
				temp.setBill5(projectDeptName);// 事业部

				Double lj = casCheckDao.getSumCommAmount(
						ImpExpType.DIRECT_IMPORT, beginDate, endDate, isEffect,// 直接进口
						null, no, isDeclaration, true, projectDeptCode);
				Double zj = casCheckDao.getSumCommAmount(
						ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);// 转厂

				Double stj = casCheckDao.getSumCommAmount(
						ImpExpType.BACK_MATERIEL_EXPORT, null, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);// 退料出口
				Double yljk = casCheckDao.getSumCommAmount(
						ImpExpType.REMAIN_FORWARD_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double ylck = casCheckDao.getSumCommAmount(
						ImpExpType.REMAIN_FORWARD_EXPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double nx = casCheckDao.getSumCommAmount(
						ImpExpType.MATERIAL_DOMESTIC_SALES, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);// 料件内销，海关批准内销
				temp.setBillSum4(lj);// 3.直接进口量
				temp.setBillSum5(zj);// 4.转厂进口量
				temp.setBillSum6(stj);// 5.退料出口量
				temp.setBillSum3(lj + zj);// "2.大单进口量=3+4"
				temp.setBillSum1(yljk);// 11.余料结转(进口)
				temp.setBillSum2(lj + zj + yljk - stj - ylck);// "1.总进口量=2+11+4-5-余料转出"
				temp.setBillSum9(lj + zj + yljk - stj - allLjUse - nx - ylck);// 8.余料情况=1-6
				temp.setBillSum18(nx);// 8.料件内销，海关批准内销

				Double averprice = casCheckDao.getImgAveragePrice(no,
						beginDate, endDate, new Boolean(true), false,
						isDeclaration, true, projectDeptCode); // 报关申报平均单价

				Double billsum10 = (fd(temp.getBillSum4())
						+ fd(temp.getBillSum5()) - fd(temp.getBillSum6()) - fd(temp
						.getBillSum7()))
						* averprice;
				temp.setBillSum10(billsum10);

				List listAmount = casCheckDao.findSumAmount(img.getSeqNum());
				Double ckAmount = getSumCkAmount(listAmount);
				temp.setBillSum11(ckAmount);
				temp.setBillSum12(fd(temp.getBillSum9()) - ckAmount);

				arrayList.add(temp);
			}
		}
		return arrayList;

	}

	/**
	 * 进口料件情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List
	 * @author wss抄改
	 */
	public List<DzscImpMaterialStat> findImpMaterialStatByDzscEmsPorHeads(
			List<DzscEmsPorHead> heads, Date beginDate, Date endDate,
			int state, boolean isDetachCompute) {
		// 如果分开统计
		if (isDetachCompute) {
			List<DzscImpMaterialStat> lsResult = new ArrayList<DzscImpMaterialStat>();
			for (int i = 0; i < heads.size(); i++) {
				DzscEmsPorHead head = (DzscEmsPorHead) heads.get(i);
				List<DzscImpMaterialStat> data = this.findImpMaterialStat(head,
						beginDate, endDate, state);
				for (DzscImpMaterialStat eps : data) {
					eps.setEmsNo(head.getEmsNo());
					eps.setIeContractNo(head.getIeContractNo());
					lsResult.add(eps);
				}
			}
			return lsResult;
		} else {
			// 如果只有一本合同
			if (heads.size() == 1) {
				DzscEmsPorHead head = (DzscEmsPorHead) heads.get(0);
				List<DzscImpMaterialStat> data = this.findImpMaterialStat(head,
						beginDate, endDate, state);
				for (DzscImpMaterialStat eps : data) {
					eps.setEmsNo("");
				}
				return data;

			}
			// 多本合同不分开统计
			else {
				Map map = new HashMap();
				for (int i = 0; i < heads.size(); i++) {
					DzscEmsPorHead head = (DzscEmsPorHead) heads.get(i);
					List<DzscImpMaterialStat> data = this.findImpMaterialStat(
							head, beginDate, endDate, state);
					for (DzscImpMaterialStat newData : data) {
						newData.setEmsNo("");
						String key = (newData.getComplex().trim())
								+ (newData.getCommName() == null ? "" : newData
										.getCommName().trim())
								+ (newData.getCommSpec() == null ? "" : newData
										.getCommSpec().trim())
								+ (newData.getUnit() == null ? "" : newData
										.getUnit().getName().trim());
						newData.setUnitPrice(null);
						// newData.setCredenceNo(null);
						if (map.get(key) == null) {
							if (heads.size() > 1) {
								newData.setSeqNum(null);
							}
							map.put(key, newData);
						} else {
							DzscImpMaterialStat oldData = (DzscImpMaterialStat) map
									.get(key);

							/**
							 * 合同定量
							 */
							oldData
									.setContractAmount(checkNullForDouble(oldData
											.getContractAmount())
											+ checkNullForDouble(newData
													.getContractAmount()));

							/**
							 * 料件进口量
							 */
							oldData.setDirectImport(checkNullForDouble(oldData
									.getDirectImport())
									+ checkNullForDouble(newData
											.getDirectImport()));

							/**
							 * 转厂进口量
							 */
							oldData
									.setTransferFactoryImport(checkNullForDouble(oldData
											.getTransferFactoryImport())
											+ checkNullForDouble(newData
													.getTransferFactoryImport()));

							/**
							 * 大单进口量
							 */
							oldData.setImpCDAmount(checkNullForDouble(oldData
									.getImpCDAmount())
									+ checkNullForDouble(newData
											.getImpCDAmount()));

							/**
							 * 退料出口量
							 */
							oldData
									.setBackMaterialExport(checkNullForDouble(oldData
											.getBackMaterialExport())
											+ checkNullForDouble(newData
													.getBackMaterialExport()));

							/**
							 * 退料退换量
							 */
							oldData
									.setBackMaterialExchange(checkNullForDouble(oldData
											.getBackMaterialExchange())
											+ checkNullForDouble(newData
													.getBackMaterialExchange()));
							/**
							 * 退料复出量
							 */
							oldData
									.setBackMaterialReturn(checkNullForDouble(oldData
											.getBackMaterialReturn())
											+ checkNullForDouble(newData
													.getBackMaterialReturn()));

							/**
							 * 余料进口
							 */
							oldData
									.setRemainImport((oldData.getRemainImport() == null ? 0.0
											: oldData.getRemainImport())
											+ (newData.getRemainImport() == null ? 0.0
													: newData.getRemainImport()));

							/**
							 * 余料转出
							 */
							oldData.setRemainForward((oldData
									.getRemainForward() == null ? 0.0 : oldData
									.getRemainForward())
									+ (newData.getRemainForward() == null ? 0.0
											: newData.getRemainForward()));

							/**
							 * 总进口量
							 */
							oldData.setImpTotalAmont(checkNullForDouble(oldData
									.getImpTotalAmont()
									+ newData.getImpTotalAmont()));

							/**
							 * 成品使用量
							 */
							oldData.setProductUse(checkNullForDouble(oldData
									.getProductUse())
									+ checkNullForDouble(newData
											.getProductUse()));
							/**
							 * 余量
							 */
							oldData.setRemainAmount(checkNullForDouble(oldData
									.getRemainAmount())
									+ checkNullForDouble(newData
											.getRemainAmount()));
							/**
							 * 缺量
							 */
							oldData.setUllage(checkNullForDouble(oldData
									.getUllage())
									+ checkNullForDouble(newData.getUllage()));

							/**
							 * 可进口量
							 */
							oldData
									.setCanImportAmount(checkNullForDouble(oldData
											.getCanImportAmount())
											+ checkNullForDouble(newData
													.getCanImportAmount()));

							/**
							 * 余料金额
							 */
							oldData.setRemainMoney(checkNullForDouble(oldData
									.getRemainMoney())
									+ checkNullForDouble(newData
											.getRemainMoney()));

							/**
							 * 关封余量
							 */
							oldData
									.setCustomsEnvelopRemain((oldData
											.getCustomsEnvelopRemain() == null ? 0.0
											: oldData.getCustomsEnvelopRemain())
											+ (newData
													.getCustomsEnvelopRemain() == null ? 0.0
													: newData
															.getCustomsEnvelopRemain()));

							/**
							 * 单价
							 */
							oldData.setUnitPrice(null);
						}
					}
				}
				// return new ArrayList(map.values());
				List lsResult = new ArrayList(map.values());
				Collections.sort(lsResult);
				return lsResult;
			}
		}

	}

	/**
	 * 进口料件报关情况统计表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * 
	 * @author wss抄改
	 */
	public List<DzscImpMaterialStat> findImpMaterialStat(
			DzscEmsPorHead contract, Date beginDate, Date endDate, int state) {
		List<DzscImpMaterialStat> lsResult = new ArrayList<DzscImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		/**
		 * 关封余量--深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTable(this.casCheckDao
				.findFptAppItemCount(emsNo, beginDate, endDate, true));
		/**
		 * 关封余量--转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTable(this.casCheckDao
				.findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
		List nset = new ArrayList(hmCustomsEnvelopTrans.keySet());
		for (int i = 0; i < nset.size(); i++) {
			String key = nset.get(i) == null ? "" : nset.get(i).toString();
			if (hmCustomsEnvelop.get(key) == null) {
				hmCustomsEnvelop.put(key == null ? "" : key.toString(),
						hmCustomsEnvelopTrans.get(key));
			} else {
				Double ss = hmCustomsEnvelop.get(key);
				Double dd = hmCustomsEnvelopTrans.get(key);
				hmCustomsEnvelop.put(key, (ss + dd));
			}
		}

		List<DzscEmsImgBill> list = this.casCheckDao
				.findDzscEmsImgBill(contract.getId());
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		this.calContractImgUse(contract, hmImgUse, beginDate, endDate);
		for (DzscEmsImgBill contractImg : list) {
			if (contractImg != null) {
				Integer commSerialNo = contractImg.getSeqNum();
				DzscImpMaterialStat impMaterialStat = new DzscImpMaterialStat();
				impMaterialStat.setSeqNum(contractImg.getSeqNum());
				if (contractImg.getComplex() != null) {
					impMaterialStat.setComplex(contractImg.getComplex()
							.getCode());
				}
				impMaterialStat.setCommName(contractImg.getName());
				impMaterialStat.setCommSpec(contractImg.getSpec());
				impMaterialStat.setUnit(contractImg.getUnit());
				impMaterialStat.setUnitPrice(contractImg.getPrice());
				impMaterialStat.setCredenceSerialNo(contractImg.getSeqNum());
				/**
				 * 合同定量
				 */
				impMaterialStat.setContractAmount(contractImg.getAmount());
				/**
				 * 料件进口量
				 */
				impMaterialStat.setDirectImport(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
								null, emsNo, beginDate, endDate, state));
				/**
				 * 转厂进口量
				 */
				impMaterialStat.setTransferFactoryImport(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.TRANSFER_FACTORY_IMPORT, null,
								emsNo, beginDate, endDate, state));
				/**
				 * 大单进口量
				 */
				impMaterialStat.setImpCDAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport()));
				/**
				 * 退料出口量
				 */
				impMaterialStat.setBackMaterialExport(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
								beginDate, endDate, state));
				/**
				 * 退料复出
				 */
				impMaterialStat.setBackMaterialReturn(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0265", "0664" }, emsNo, beginDate,
								endDate, state));
				/**
				 * 退料退换
				 */
				impMaterialStat.setBackMaterialExchange(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0300", "0700" }, emsNo, beginDate,
								endDate, state));

				/**
				 * 余料进口 (余料结转进口)
				 */
				impMaterialStat.setRemainImport(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
								beginDate, endDate, state));
				/**
				 * 余料出口 (余料结转出口)
				 */
				impMaterialStat.setRemainForward(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
								beginDate, endDate, state));

				/**
				 * 进口总量=直接进口量+转厂进口量+余料进口-退料退换(料件退换进口量已经包含在料件进口量中)
				 */
				impMaterialStat.setImpTotalAmont(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainImport())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialExchange()));
				/**
				 * 成品使用量
				 */
				impMaterialStat.setProductUse(CommonUtils
						.getDoubleExceptNull(hmImgUse
								.get(commSerialNo == null ? "" : commSerialNo
										.toString())));
				// hmImgUse.remove(commSerialNo == null ? "" : commSerialNo
				// .toString());
				/**
				 * 余量=进口总量-退运出口(复出)-成品使用量-余料结转出口
				 */
				double remainAmount = (CommonUtils
						.getDoubleExceptNull(impMaterialStat.getImpTotalAmont())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getProductUse())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialReturn()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat.getRemainForward()));
				// if (remainAmount > 0) {
				// /**
				// * 余量=进口总量-成品使用量
				// */
				impMaterialStat.setRemainAmount(remainAmount);

				/**
				 * 可进口量 = 合同定量-总进口量
				 */
				impMaterialStat.setCanImportAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getContractAmount())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getImpTotalAmont()));
				/**
				 * 比例
				 */
				impMaterialStat
						.setScale(CommonUtils
								.getDoubleByDigit(
										((impMaterialStat.getCanImportAmount() == null ? 0.0
												: impMaterialStat
														.getCanImportAmount()) / (impMaterialStat
												.getContractAmount() == null ? 1.0
												: impMaterialStat
														.getContractAmount())) * 100,
										2));
				/**
				 * 余料金额
				 */
				impMaterialStat.setRemainMoney((impMaterialStat
						.getRemainAmount() == null ? 0.0 : impMaterialStat
						.getRemainAmount())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString()
						+ (contractImg.getComplex() == null ? "" : contractImg
								.getComplex().getCode()));
				/**
				 * 关封余量=关封申请总量—转厂进口量
				 */
				impMaterialStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (impMaterialStat.getTransferFactoryImport() == null ? 0.0
										: impMaterialStat
												.getTransferFactoryImport()));
				impMaterialStat.setCountry(contractImg.getCountry());

				lsResult.add(impMaterialStat);
			}
		}

		return lsResult;
	}

	/**
	 * 计算成品所有的料件的耗用总量
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param hmImgUse
	 *            手册通过备案的BOM资料对应的数量
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 *@author wss抄改
	 */
	private void calContractImgUse(DzscEmsPorHead contract,
			HashMap<String, Double> hmImgUse, Date beginDate, Date endDate) {
		List<DzscExpProductStat> lsProductStat = this.findExpProductStat(
				contract, beginDate, endDate);
		for (DzscExpProductStat productStat : lsProductStat) {
			double exgAmount = CommonUtils.getDoubleExceptNull(productStat
					.getExpTotalAmont());
			List<DzscEmsBomBill> list = this.casCheckDao.findBomByExg(contract
					.getEmsNo(), productStat.getSeqNum());
			for (DzscEmsBomBill contractBom : list) {
				if (contractBom.getImgSeqNum() == null) {
					continue;
				}

				double imgAmount = exgAmount
						* CommonUtils.getDoubleExceptNull(CommonUtils
								.getDoubleExceptNull(contractBom.getUnitWare())
								/ (1 - CommonUtils
										.getDoubleExceptNull(contractBom
												.getWare()) / 100.0));

				double hasAmount = CommonUtils.getDoubleExceptNull(hmImgUse
						.get(contractBom.getImgSeqNum().toString()));
				hmImgUse.put(contractBom.getImgSeqNum().toString(), imgAmount
						+ hasAmount);
			}
		}
	}

	/**
	 * 出口成品报关情况统计表(电子手册）
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscExpProductStat型， 存放统计报表中的出口成品报关情况表资料
	 * 
	 * @author wss抄改
	 */
	public List<DzscExpProductStat> findExpProductStat(DzscEmsPorHead contract,
			Date beginDate, Date endDate) {
		List<DzscExpProductStat> lsResult = new ArrayList<DzscExpProductStat>();
		String emsNo = contract.getEmsNo();
		/**
		 * 关封余量－深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTable(this.casCheckDao
				.findFptAppItemCount(emsNo, beginDate, endDate, false));
		/**
		 * 关封余量－转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTable(this.casCheckDao
				.findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
		List nset = new ArrayList(hmCustomsEnvelopTrans.keySet());
		for (int i = 0; i < nset.size(); i++) {
			String key = nset.get(i) == null ? "" : nset.get(i).toString();
			if (hmCustomsEnvelop.get(key) == null) {
				hmCustomsEnvelop.put(key == null ? "" : key.toString(),
						hmCustomsEnvelopTrans.get(key));
			} else {
				Double ss = hmCustomsEnvelop.get(key);
				Double dd = hmCustomsEnvelopTrans.get(key);
				hmCustomsEnvelop.put(key, (ss + dd));
			}
		}
		// List list = this.dzscCavDao.findAllCommInfo(false, emsNo, beginDate,
		// endDate);
		List<DzscEmsExgBill> list = this.casCheckDao
				.findDzscEmsExgBill(contract);
		for (DzscEmsExgBill contractExg : list) {
			Integer commSerialNo = contractExg.getSeqNum();
			// DzscEmsExgBill contractExg =
			// this.dzscCavDao.findExgByCommSerialNo(
			// emsNo, commSerialNo);
			if (contractExg != null) {
				DzscExpProductStat expProductStat = new DzscExpProductStat();
				expProductStat.setSeqNum(contractExg.getSeqNum());
				expProductStat.setComplex(contractExg.getComplex().getCode());
				expProductStat.setCommName(contractExg.getName());
				expProductStat.setCommSpec(contractExg.getSpec());
				expProductStat.setUnit(contractExg.getUnit());
				expProductStat.setUnitPrice(contractExg.getPrice());
				/**
				 * 合同定量
				 */
				expProductStat.setContractAmount(contractExg.getAmount());
				/**
				 * 成品出口量
				 */
				expProductStat.setDirectExport(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT,
								null, emsNo, beginDate, endDate));
				/**
				 * 转厂出口
				 */
				expProductStat.setTransferFactoryExport(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.TRANSFER_FACTORY_EXPORT, null,
								emsNo, beginDate, endDate));
				/**
				 * 退厂返工数
				 */
				expProductStat.setBackFactoryRework(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.BACK_FACTORY_REWORK, null, emsNo,
								beginDate, endDate));
				/**
				 * 返工复出数
				 */
				expProductStat.setReworkExport(this.casCheckDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT,
								null, emsNo, beginDate, endDate));
				/**
				 * 总出口量=成品出口量+转厂出口量-退厂返工量+返工复出数
				 */
				expProductStat
						.setExpTotalAmont((expProductStat.getDirectExport() == null ? 0.0
								: expProductStat.getDirectExport())
								+ (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport())
								- (expProductStat.getBackFactoryRework() == null ? 0.0
										: expProductStat.getBackFactoryRework())
								+ (expProductStat.getReworkExport() == null ? 0.0
										: expProductStat.getReworkExport()));
				double canExportAmount = (expProductStat.getContractAmount() == null ? 0.0
						: expProductStat.getContractAmount())
						- (expProductStat.getExpTotalAmont() == null ? 0.0
								: expProductStat.getExpTotalAmont());
				if (canExportAmount > 0) {
					/**
					 * 可出口量 = 合同定量-总进口量
					 */
					expProductStat.setCanExportAmount(canExportAmount);
				} else {
					/**
					 * 超量
					 */
					expProductStat.setOverAmount(-canExportAmount);
				}
				/**
				 * 比例
				 */
				expProductStat
						.setScale(CommonUtils
								.getDoubleByDigit(
										((expProductStat.getCanExportAmount() == null ? 0.0
												: expProductStat
														.getCanExportAmount()) / (expProductStat
												.getContractAmount() == null ? 1.0
												: expProductStat
														.getContractAmount())) * 100,
										2));
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString()
						+ contractExg.getComplex().getCode());
				/**
				 * 关封余量=关封总量-转厂出口量
				 */
				expProductStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport()));
				expProductStat
						.setProcessUnitPrice(contractExg.getMachinPrice());
				expProductStat.setProcessTotalPrice(CommonUtils
						.getDoubleExceptNull(expProductStat
								.getProcessUnitPrice())
						* CommonUtils.getDoubleExceptNull(expProductStat
								.getExpTotalAmont()));
				expProductStat.setLegalAmount(contractExg.getAmount());
				expProductStat.setLegalUnit(contractExg.getUnit());
				expProductStat.setUnitWeight(contractExg.getUnitGrossWeight());
				expProductStat.setUnitGrossWeight(contractExg
						.getUnitGrossWeight());
				expProductStat.setUnitNetWeight(contractExg.getUnitNetWeight());
				expProductStat.setLevyMode(contractExg.getLevyMode());
				lsResult.add(expProductStat);
			}
		}
		return lsResult;
	}

	/**
	 * 产成品当日结存折料情况
	 * @param isBill 
	 * @param finishedConditionsBalance 
	 * 
	 * @param request
	 * @param list
	 *            需要折料的成品
	 * @return 2010-06-2hcl
	 */
	public List getCurrentBillDtailToBom(List<Condition> finishedCondition,
			List<Condition> materialCondition, List<Condition> finishedConditionsBalance, boolean isBill, String orderBy,
			String materielType, Boolean ishsTaotal, Boolean isShowLess,
			Boolean isGroup) {
		//是否查询盘点导入
		if(!isBill){
			// 获取导入的数据
			return getCheckBalanceConvertMateriel(materialCondition, finishedConditionsBalance);
			
		}
		
		// 按成品条件找出符合成品条件的单据
		// 过滤条件
		List<TempThatDayBalance> tempList = getCurrentBillDetail(
				finishedCondition, orderBy, MaterielType.FINISHED_PRODUCT,
				false, false, isGroup,false,null);
		List<BillDetail> billList = new ArrayList<BillDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BillDetail bill = getBillDetailByTempThatDayBalance(tempList.get(i));
			billList.add(bill);
		}
		// 按料件条件集合conditionsMaterial找出料件集合
		boolean isAll = false;
		if (materialCondition.size() == 0)
			isAll = true;
		Map<String, String> mapMaterial = getResultMaterielKey(materialCondition);
		List<String> mList = null;
		if (mapMaterial != null) {
			mList = new ArrayList<String>(mapMaterial.values());
			System.out.println("mList.size()=" + mList.size());
		}
		// 成品折料

		// 查找BOM,折料，判断成品、半成品所折的料件是否跟条件里的料件一致
		List<TempBomRelation> boms = null;
		String bomKey = null;
		Map<String, List<TempBomRelation>> bomMap = new HashMap<String, List<TempBomRelation>>();
		List<ToBomTempThatDayBalance> listAdd = new ArrayList<ToBomTempThatDayBalance>();
		for (BillDetail bill : billList) {
			bomKey = bill.getPtPart();
			boms = bomMap.get(bomKey);
			if (boms != null) {
			} else {
				boms = this.getBomRelation(bill, mapMaterial, isAll);
				bomMap.put(bomKey, boms);
			}
			System.out.println("boms.size=" + boms.size());
			for (TempBomRelation bom : boms) {
				listAdd.add(setToBomTempThatDayBalance(bom, bill));
			}
		}


		// 按成品料号，BOM报关名称排序
		Comparator comp = null;
		if (ishsTaotal)
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					ToBomTempThatDayBalance p1 = (ToBomTempThatDayBalance) o1;
					ToBomTempThatDayBalance p2 = (ToBomTempThatDayBalance) o2;
					if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return 1;
					else if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return -1;
					else if (p1.getHsName().compareTo(p2.getHsName()) > 0)
						return 1;
					else if (p1.getHsName().compareTo(p2.getHsName()) < 0)
						return -1;
					else
						return 0;
				}
			};
		else
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					ToBomTempThatDayBalance p1 = (ToBomTempThatDayBalance) o1;
					ToBomTempThatDayBalance p2 = (ToBomTempThatDayBalance) o2;
					if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return 1;
					else if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return -1;
					else
						return 0;
				}
			};
		Collections.sort(listAdd, comp);
		Map<String, Double> hsMap = new HashMap<String, Double>();
		String HsKey = "";
		Double value = null;
		for (int i = 0; i < listAdd.size(); i++) {
			ToBomTempThatDayBalance t = listAdd.get(i);
			HsKey = getHsKey(t);
			value = hsMap.get(HsKey);
			if (value == null) {
				value = t.getHsAmount();
				hsMap.put(HsKey, value);
			} else {
				value += t.getHsAmount();
				hsMap.put(HsKey, value);
			}
		}
		for (int i = 0; i < listAdd.size(); i++) {
			ToBomTempThatDayBalance t = listAdd.get(i);
			System.out.println("key=" + getHsKey(t));
			System.out.println("value=" + hsMap.get(getHsKey(t)));
			t.setHsAllAmount(hsMap.get(getHsKey(t)));
		}
		return listAdd;
	}

	private BillDetail getBillDetailByTempThatDayBalance(TempThatDayBalance temp) {
		BillDetail bill = new BillDetail();
		bill.setPtPart(temp.getPtPart());
		bill.setPtName(temp.getPtName());
		bill.setPtSpec(temp.getPtSpec());
		bill.setPtUnit(temp.getPtUnit());
		bill.setPtAmount(temp.getPtAmount());
		bill.setComplex(temp.getComplex());
		bill.setHsName(temp.getHsName());
		bill.setHsSpec(temp.getHsSpec());
		bill.setHsUnit(temp.getHsUnit());
		System.out.println("temp.unitConvert="+temp.getUnitConvert());
		bill.setUnitConvert(temp.getUnitConvert());
		bill.setHsAmount(temp.getHsAmount());
		bill.setWareSet(temp.getWareSet());
		bill.setVersion(temp.getVersion());
		bill.setNote(temp.getNote());
		return bill;
	}

	private String getHsKey(ToBomTempThatDayBalance t) {
		return t.getHsName() + "/" + (t.getHsSpec()==null?"":t.getHsSpec()) + "/"
				+ (t.getHsUnit()==null?"":t.getHsUnit().getName());
	}
	private String getHsKey(CheckBalanceConvertMateriel t) {
		return t.getName() + "/" + (t.getSpec()==null?"":t.getSpec())+ "/"
				+ (t.getHsUnit()==null?"":t.getHsUnit().getName());
	}
	private String getHsKeyTempBomBillDetail(TempBomBillDetail t) {
		return t.getBill().getHsName() + "/" + t.getBill().getHsSpec() + "/"
				+ t.getBill().getHsUnit().getName();
	}

	private ToBomTempThatDayBalance setToBomTempThatDayBalance(
			TempBomRelation bom, BillDetail sourceBill) {
		ToBomTempThatDayBalance newbill = new ToBomTempThatDayBalance();
		// 成品信息

		TempThatDayBalance t = new TempThatDayBalance();
		t.setPtPart(sourceBill.getPtPart());
		t.setPtName(sourceBill.getPtName());
		t.setPtSpec(sourceBill.getPtSpec());
		t.setPtUnit(sourceBill.getPtUnit());
		t.setPtAmount(sourceBill.getPtAmount());
		t.setComplex(sourceBill.getComplex());
		t.setHsName(sourceBill.getHsName());
		t.setHsSpec(sourceBill.getHsSpec());
		t.setHsUnit(sourceBill.getHsUnit());
		t.setHsAmount(sourceBill.getHsAmount());
		System.out.println("sourceBill.unitConvert="+sourceBill.getUnitConvert());
		System.out.println("sourceBill.getHsAmount="+sourceBill.getHsAmount());

		t.setUnitConvert(sourceBill.getUnitConvert());
		t.setWareSet(sourceBill.getWareSet());
		t.setMaterialType(sourceBill.getNote());// 物料类别
		newbill.setProduct(t);
		// 工厂料号、单位
		newbill.setPtPart(bom.getPtNo());
		newbill.setPtUnit(bom.getPtUnit());
		newbill.setUnitUsed(bom.getUnitUsed());
		newbill.setUnitWaste(bom.getUnitWaste());
		// 抓对应关系的报关级信息
		newbill.setHsName(bom.getHsName());
		newbill.setHsSpec(bom.getHsSpec());
		newbill.setHsUnit(bom.getHsUnit());
		newbill.setUnitConvert(bom.getUnitConvert());
		// 工厂数量、报关数量
		// 折算工厂数量=成品工厂数量*（单耗/（1-损耗率））
		if (null != bom.getWaste() && null != bom.getUnitWaste()
				&& null != sourceBill.getPtAmount())
			newbill.setWareAmount(sourceBill.getPtAmount()
					* (bom.getUnitWaste() / (1 - bom.getWaste())));
		else
			newbill.setWareAmount(0.0);
		if (null != newbill.getWareAmount() && null != newbill.getUnitConvert())
			newbill.setHsAmount(newbill.getWareAmount()
					* newbill.getUnitConvert());
		else
			newbill.setHsAmount(0.0);

		return newbill;
	}

	private String getTempKey(TempThatDayBalance bill) {
		return bill.getPtPart() + "/" + bill.getPtName() + "/"
				+ bill.getPtSpec();
	}

	/**
	 * 在产品当日结存折料情况
	 * 
	 * @param request
	 * @param list
	 *            需要折料的成品
	 * @return 2010-07-05hcl 
	 * wss添加来自单据部分
	 */
	public List getSemiBillDtailToBom(List<Condition> finishedConditionsBalance,
			List<Condition> finishedConditionsBill,
			List<Condition> materialConditions, String orderBy,
			String materielType, boolean ishsTaotal, boolean isShowLess,
			boolean isWareSet, boolean isFromBill) {
		// 如果从盘点抓
		if (!isFromBill) {
			// 获取导入的数据
			return getCheckBalanceConvertMateriel(materialConditions, finishedConditionsBalance);
		}
		
		List<TempBomBillDetail> result = new ArrayList<TempBomBillDetail>();
		List<TempThatDayBalance> result2;

		// 按料件条件集合conditionsMaterial找出料件集合
		boolean isAll = false;
		if (materialConditions.size() == 0) {
			isAll = true;
		}

		Map<String, String> mapMaterial = getResultMaterielKey(materialConditions);
		List<String> mList = null;

		if (mapMaterial != null) {
			mList = new ArrayList<String>(mapMaterial.values());
			System.out.println("mList.size()=" + mList.size());
		}

		List<BillDetail> billListAll = new ArrayList<BillDetail>();// 过滤后的料件

		List<BillDetail> materialBillList = new ArrayList<BillDetail>();// 料件
		
		List<BillDetail> semiOrfinishedBillList = new ArrayList<BillDetail>();// 半成品或成品

		

		// 从单据抓
			// 查在产品的料件单据
			materialBillList = getBillDetailForBalance("currentTotal",
					MaterielType.MATERIEL, finishedConditionsBill);

			// 查在产品的半成品单据
			semiOrfinishedBillList = getBillDetailForBalance("currentTotal",
					MaterielType.SEMI_FINISHED_PRODUCT, finishedConditionsBill);

			// 查在产品的成品单据
			List listFinished = getBillDetailForBalance("currentTotal",
					MaterielType.FINISHED_PRODUCT, finishedConditionsBill);

			//要折的放一起
			semiOrfinishedBillList.addAll(listFinished);
		

		System.out
				.println("materialBillList.size()=" + materialBillList.size());
		System.out.println("semiOrfinishedBillList.size()="
				+ semiOrfinishedBillList.size());

		// 判断料件
		for (int i = 0; i < materialBillList.size(); i++) {
			BillDetail bill = materialBillList.get(i);

			//全部加上
			if (isAll) {
				billListAll.add(bill);
			}
			
			//2.报关编码为空 或者报关单位为空，则跳过
			else if(bill.getComplex() == null || bill.getHsUnit() == null){
				continue;
			}
			
			//3.依查询条件过滤
			else{
				for (String s : mList) {
					if (s
							.equals(bill.getPtPart() + "/" 
									+ bill.getComplex().getCode() + "/"
									+ bill.getHsName() + "/"
									+ bill.getHsSpec() + "/"
									+ bill.getHsUnit().getName())) {
						billListAll.add(bill);
						break;
					}
				}
			}
				
		}

		// 查找BOM,折料，判断成品、半成品所折的料件是否跟条件里的料件一致
		List<TempBomRelation> boms = null;
		String bomKey = null;
		Map<String, List<TempBomRelation>> bomMap = new HashMap<String, List<TempBomRelation>>();

		Map<String, TempThatDayBalance> balanceMap = new HashMap<String, TempThatDayBalance>();

		// 遍历成品、半成品单据，将符合条件的转化为TempBomBillDetail或Temp盘点
		for (BillDetail bill : semiOrfinishedBillList) {
			bomKey = bill.getPtPart();
			System.out.println("bomKey=" + bomKey);
			boms = bomMap.get(bomKey);
			if (boms != null) {
			} else {
				boms = this.getBomRelation(bill, mapMaterial, isAll);
				bomMap.put(bomKey, boms);
			}
			System.out.println("boms.size=" + boms.size());
			for (TempBomRelation bom : boms) {
				if (isFromBill) {

					int inOut = isAddOutAndBom(bill, "currentTotal");
					if (inOut != 0) {
						transferBalanceFromTempBom(balanceMap, bill, bom,
								inOut, ishsTaotal, isWareSet);
					}

				} else {
					result.add(setTempBomBillDetailFinished(bom, bill));
				}
			}
		}

		// 转成TempBomBillDetail
		System.out.println("=====AllBillList.size=" + billListAll.size());
		System.out.println("=====result.size=" + result.size());

		// 遍历料件单据，转化为TempBomBillDetail 或者 temp盘点
		if (billListAll.size() != 0) {
			for (int i = 0; i < billListAll.size(); i++) {
				BillDetail bill = billListAll.get(i);

				// 来算单据
				if (isFromBill) {
					int inOut = isAddOutAndBom(bill, "currentTotal");

					if (inOut != 0) {
						String key = getBillNoBomKey(bill, ishsTaotal,
								isWareSet, false);
						transferBalanceFromBillDetail(balanceMap, bill, inOut,
								"currentTotal", key, ishsTaotal);

					}
				}

				// 来自盘点
			}
		}

		if (isFromBill) {
			result2 = new ArrayList<TempThatDayBalance>(balanceMap.values());

			System.out.println("wss result2.size = " + result2.size());
			List rs = new ArrayList();

			// 显示负数结存
			if (isShowLess) {
				for (TempThatDayBalance balance : result2) {
					if (ishsTaotal ? (balance.getHsAmount()!= null && balance.getHsAmount()< 0) 
							: (balance.getPtAmount()!= null && balance.getPtAmount() < 0)) {
						rs.add(balance);
					}
				}
				return rs;
			} else {
				return result2;
			}

		}

		else {
			Map<String, Double> hsMap = new HashMap<String, Double>();
			String HsKey = "";
			Double value = null;
			for (int i = 0; i < result.size(); i++) {
				TempBomBillDetail t = result.get(i);
				HsKey = getHsKeyTempBomBillDetail(t);
				value = hsMap.get(HsKey);
				if (value == null) {
					value = t.getBill().getHsAmount();
					hsMap.put(HsKey, value);
				} else {
					value += t.getBill().getHsAmount();
					hsMap.put(HsKey, value);
				}
			}
			for (int i = 0; i < result.size(); i++) {
				TempBomBillDetail t = result.get(i);
			System.out.println("key=" + getHsKeyTempBomBillDetail(t));
			System.out.println("value=" + hsMap.get(getHsKeyTempBomBillDetail(t)));
			t.setHsAllAmount(hsMap.get(getHsKeyTempBomBillDetail(t)));
		}
			return result;
		}
	}


	private BillDetail getBillDetailByCheckBalance(CheckBalance c) {
		BillDetail bill = new BillDetail();

		bill.setPtPart(c.getPtNo());

		System.out.println("c.getPtNo()" + c.getPtNo());

		bill.setPtName(c.getPtName());

		System.out.println("c.getPtName()" + c.getPtName());

		bill.setPtSpec(c.getPtSpec());

		System.out.println("c.getPtSpec()" + c.getPtSpec());

		CalUnit unit = new CalUnit();

		unit.setName(c.getPtUnitName());
		bill.setPtUnit(unit);
		bill.setPtAmount(c.getCheckAmount());

		bill.setComplex(c.getComplex());
		bill.setHsName(c.getName());
		bill.setHsSpec(c.getSpec());

		Unit hsUnit = new Unit();
		hsUnit.setName(c.getUnitName());

		bill.setHsUnit(hsUnit);

		bill.setHsAmount(c.getHsAmount());
		bill.setHsVersion(Integer.getInteger(c.getBomVersion()));
		bill.setWareSet(c.getWareSet());
		bill.setUnitConvert(c.getUnitConvert());
		if (("2").equals(c.getLjCpMark())) {
			bill.setNote("半成品");
		} else if (("1").equals(c.getLjCpMark())) {
			bill.setNote("成品");
		}
		if (("0").equals(c.getLjCpMark())) {
			bill.setNote("料件");
		}

		return bill;
	}

	/**
	 *从盘点表中获取在产品当日结存
	 * 
	 * @return
	 * @author wss 2010.07.05
	 */
	public List getCurrentCheckBalance(List<Condition> conditionsCheck,
			List<Condition> conditionsBill, String orderBy, boolean isGroup,
			boolean isFromCheck, boolean isHsTaotal, boolean isShowLess) {

		// 从盘点抓数据
		if (isFromCheck) {
			// 类型
			conditionsCheck.add(new Condition("and", null, "kcType", "=", "2",//wss改"3"为"2"
					null));

			//wss2010.08.16可以这样子加吗？
			String leftOuter = " left join fetch a.complex left join fetch a.wareSet  ";
			
			List<CheckBalance> list = casDao.commonSearch("", "CheckBalance",
					conditionsCheck, "", leftOuter);
			System.out.println("wss list.size: " + list.size());

			Map<String, TempCheckBalance> map = new HashMap<String, TempCheckBalance>();

			for (int i = 0; i < list.size(); i++) {
				CheckBalance c = (CheckBalance) list.get(i);

				// String key = c.getPtNo() + "/" + c.getPtName() + "/" +
				// c.getPtSpec();
				String key = c.getPtNo();// 应该只有料号

				if (isGroup) {
					key += "/" + c.getWareSet().getCode();
				}

				TempCheckBalance temp;
				if ((temp = map.get(key)) == null) {

					temp = new TempCheckBalance();
					temp.setPtNo(c.getPtNo());
//					Materiel m = this.casDao.findMaterielByPtNo(c.getPtNo());
//					System.out.println("wss PtNo:" + c.getPtNo());
//					if (m != null) {
//
//						temp.setPtName(m.getFactoryName());// 新加
//						temp.setPtSpec(m.getFactorySpec());// 新加
//						temp.setPtUnitName(m.getCalUnit() == null ? "" : m
//								.getCalUnit().getName());
//
//					} else {
//						temp.setNote("报关常用物料中不存在此料号");
//					}

					temp.setPtName(c.getPtName());
					temp.setPtSpec(c.getPtSpec());
					temp.setCheckAmount(c.getCheckAmount());
					temp.setUnitName(c.getUnitName());
					temp.setWareSet(c.getWareSet());
					map.put(key, temp);
				} else {
					temp.setCheckAmount(temp.getCheckAmount()
							+ c.getCheckAmount());
				}
			}

			List returnList = new ArrayList(map.values());

			return returnList;
		}

		// 从单据抓数据
		else {

			List<BillDetail> listMaterial = new ArrayList<BillDetail>();
			List<BillDetail> listSemi = new ArrayList<BillDetail>();
			List<BillDetail> listFinished = new ArrayList<BillDetail>();

			// 查在产品的料件单据
			listMaterial = getBillDetailForBalance("currentTotal",
					MaterielType.MATERIEL, conditionsBill);

			// 查在产品的半成品单据
			listSemi = getBillDetailForBalance("currentTotal",
					MaterielType.SEMI_FINISHED_PRODUCT, conditionsBill);

			// 查在产品的成品单据
			listFinished = getBillDetailForBalance("currentTotal",
					MaterielType.FINISHED_PRODUCT, conditionsBill);

			listFinished.addAll(listMaterial);
			listFinished.addAll(listSemi);

			//
			Map<String, TempThatDayBalance> balanceMap = new HashMap<String, TempThatDayBalance>();

			// 单据临时实体
			TempThatDayBalance balanceTemp = null;

			// 分组统计
			for (BillDetail bill : listFinished) {
				// key = getMaterielKey(null, bill);//key 为料号 + 工厂名称 + 工厂规格

				String key = getBillNoBomKey(bill, isHsTaotal, isGroup, false);// 根据条件获得key

				int inOut = isAddOutAndBom(bill, "currentTotal");
				if (inOut != 0) {
					transferBalanceFromBillDetail(balanceMap, bill, inOut,
							"currentTotal", key, isHsTaotal);
				}
			}
			List<TempThatDayBalance> list = new ArrayList(balanceMap.values());
			List<TempThatDayBalance> rs = new ArrayList<TempThatDayBalance>();

			// 数据过滤
			if (isShowLess) {// 不存在key
				for (TempThatDayBalance balance : list) {
					if ((isHsTaotal ? balance.getHsAmount() : balance
							.getPtAmount()) < 0) {
						rs.add(balance);
					}
				}
				return rs;
			} else {
				return list;
			}
		}

	}

	/**
	 * 在产品当日结存与盘点差额表
	 * 
	 * @param request
	 * @param finishedConditions
	 * @param materialConditions
	 * @param orderBy
	 * @param materielType
	 * @param ishsTaotal
	 * @param isShowLess
	 * @param isGroup
	 * @return
	 * @author wss
	 */
	public List getCurrentDifferent(List<Condition> finishedConditions,
			List<Condition> materialConditionsCheck,
			List<Condition> materialConditions, String orderBy,
			String materielType, Boolean ishsTaotal, Boolean isShowLess,
			Boolean isGroup) {
		// 1.按半成品条件找出符合条件的单据

		// 2.按料件条件进行过滤

		// 3.按半成品条件找出符合条件的盘点

		// 4.按料件条件进行过滤

		// 5.两个list进行对比得出结果

		List<SemiProductInfo> result = new ArrayList<SemiProductInfo>();

		// 按料件条件集合conditionsMaterial找出料件集合
		boolean isAll = false;
		if (materialConditions.size() == 0) {
			isAll = true;
		}

		Map<String, String> mapMaterial = getResultMaterielKey(materialConditions);

		// 符合查询条件的key集合
		List<String> mList = null;
		if (mapMaterial != null) {
			mList = new ArrayList<String>(mapMaterial.values());
			System.out.println("wss 符合查询条件对应关系List.size()=" + mList.size());
		}

		// 按条件集合finishedConditions找出符合条件的半成品、成品、原料单据

		List<BillDetail> listMaterial = new ArrayList<BillDetail>();
		List<BillDetail> listSemi = new ArrayList<BillDetail>();
		List<BillDetail> listBadSemi = new ArrayList<BillDetail>();
		List<BillDetail> listFinished = new ArrayList<BillDetail>();

		System.out
				.println("***********开始单据的************************************");

		// 查在产品的料件单据
		listMaterial = getBillDetailForBalance("currentTotal",
				MaterielType.MATERIEL, finishedConditions);

		// 查在产品的半成品单据
		listSemi = getBillDetailForBalance("currentTotal",
				MaterielType.SEMI_FINISHED_PRODUCT, finishedConditions);
		
		//wss:2010.08.16残次品入库5001（半成品部分）
		listBadSemi = getBillDetailForBalance("currentTotal",
				MaterielType.BAD_PRODUCT, finishedConditions);


		// 查在产品的成品单据
		listFinished = getBillDetailForBalance("currentTotal",
				MaterielType.FINISHED_PRODUCT, finishedConditions);

		// 表示要留下的 料件
		List<BillDetail> listAdd = new ArrayList<BillDetail>();

		// 表示要留下的成品、半成品 折料后 生成的盘点 + 料件盘点
		List<TempCurrentBillDetailPanDian> listDetailAll = new ArrayList<TempCurrentBillDetailPanDian>();

		System.out.println("wss 单据料件listMaterial.size=" + listMaterial.size());
		System.out.println("wss 单据成品listSemi.size=" + listSemi.size());
		System.out.println("wss 单据成品listBadSemi.size=" + listBadSemi.size());
		System.out.println("wss 单据半成品listFinished.size=" + listFinished.size());

		// 过滤料件单据，留下符合查询条件
		for (int i = 0; i < listMaterial.size(); i++) {
			//1.如果查询条件为空，则全部加上
			BillDetail bill = listMaterial.get(i);
			if (mList == null) {
				listAdd.add(bill);
			}
			//2.如果编码为空，或者单位为空，直接跳过
			else if(bill.getComplex() == null || bill.getHsUnit() == null){
				continue;
			}
			else {
				for (String s : mList) {
					if (s
							.equals(bill.getPtPart() + "/" 
									+ bill.getComplex().getCode() + "/"
									+ bill.getHsName() + "/"
									+ bill.getHsSpec() + "/"
									+ bill.getHsUnit().getName())) {
						listAdd.add(bill);
						break;
					}
				}
			}
		}

		// 半成品、成品一起折算
		listSemi.addAll(listFinished);
		listSemi.addAll(listBadSemi);

		// 临时BOM集合
		List<TempBomRelation> boms = null;
		String bomKey = null;

		// key为成品料号 value为对应的临时BOM集合
		Map<String, List<TempBomRelation>> bomMap = new HashMap<String, List<TempBomRelation>>();

		// 遍历要折料的成品、半成品单据
		for (BillDetail bill : listSemi) {
			bomKey = bill.getPtPart();
			boms = bomMap.get(bomKey);
			if (boms != null) {
			} else {
				boms = this.getBomRelation(bill, mapMaterial, isAll);
				bomMap.put(bomKey, boms);
			}

			System.out.println("wss boms.size = " + boms.size());

			// 将折后料件转化为盘点信息
			for (TempBomRelation bom : boms) {

				int inOut = isAddOutAndBom(bill, "currentTotal");
				if (inOut != 0) {
					listDetailAll.add(setTempCurrentBillDetailPanDianFinished(
							bom, bill, inOut));
				}
			}
		}

		System.out.println("wss 要折的 listDetailAll.size = "
				+ listDetailAll.size());

		// 将纯料件的单据 转化为 盘点信息
		if (listAdd.size() != 0) {
			for (int i = 0; i < listAdd.size(); i++) {
				BillDetail bill = listAdd.get(i);


				int inOut = isAddOutAndBom(bill, "currentTotal");

				if (inOut != 0) {
					listDetailAll.add(getTempCurrentBillDetailPanDianMaterial(
							bill, inOut));
				}
			}
		}

		System.out.println("wss 再加上料件的 listDetailAll.size = "
				+ listDetailAll.size());

		// ********************************************************************************
		// ****************************开始折盘点*******************************************

//		System.out
//				.println("***********开始盘点的************************************");
//		List<CheckBalance> listMaterialCheck = new ArrayList<CheckBalance>();
//		List<CheckBalance> listSemiCheck = new ArrayList<CheckBalance>();
//		List<CheckBalance> listFinishedCheck = new ArrayList<CheckBalance>();
//
//		finishedConditionsCheck.add(new Condition("and", null, "kcType", "=",
//				"3", null));// 在产品库存
//
//		finishedConditionsCheck.add(new Condition("and", null, "ljCpMark", "=",
//				"0", null));
//
//		// 料件
//		listMaterialCheck = casDao.commonSearch("", "CheckBalance",
//				finishedConditionsCheck, "", "");
//
//		finishedConditionsCheck.remove(finishedConditionsCheck.size() - 1);
//		finishedConditionsCheck.add(new Condition("and", null, "ljCpMark", "=",
//				"1", null));
//
//		// 成品
//		listFinishedCheck = casDao.commonSearch("", "CheckBalance",
//				finishedConditionsCheck, "", "");
//
//		finishedConditionsCheck.remove(finishedConditionsCheck.size() - 1);
//		finishedConditionsCheck.add(new Condition("and", null, "ljCpMark", "=",
//				"2", null));
//
//		// 半成品
//		listSemiCheck = casDao.commonSearch("", "CheckBalance",
//				finishedConditionsCheck, "", "");
//
//		System.out.println("wss listMaterialCheck.size="
//				+ listMaterialCheck.size());
//		System.out.println("wss listSemiCheck.size=" + listSemiCheck.size());
//		System.out.println("wss listFinishedCheck.size="
//				+ listFinishedCheck.size());
//
//		// 表示要留下的盘点 料件
//		List<CheckBalance> listCheckAll = new ArrayList<CheckBalance>();
//
//		// 遍历 盘点料件 ，判断是否符合查询条件
//		for (int i = 0; i < listMaterialCheck.size(); i++) {
//
//			// 将料件资料完善了一下
//			CheckBalance c = (CheckBalance) listMaterialCheck.get(i);
//			FactoryAndFactualCustomsRalation srh = this.casDao
//					.findFactualCustoms(c.getPtNo(), null);
//
//			if (srh != null && srh.getStatCusNameRelationHsn() != null) {
//				if (srh.getMateriel() != null) {
//					c.setPtName(srh.getMateriel().getFactoryName());// 工厂名称
//					c.setPtSpec(srh.getMateriel().getFactorySpec());// 工厂规格
//					c.setPtUnitName(srh.getMateriel().getCalUnit() == null ? ""
//							: srh.getMateriel().getCalUnit().getName());// 工厂单位
//				}
//				c.setComplex(srh.getStatCusNameRelationHsn().getComplex());// 报关编码
//				c.setName(srh.getStatCusNameRelationHsn().getCusName());// 报关名称
//				c.setSpec(srh.getStatCusNameRelationHsn().getCusSpec());// 报关规格
//				c
//						.setUnitName(srh.getStatCusNameRelationHsn()
//								.getCusUnit() == null ? "" : srh
//								.getStatCusNameRelationHsn().getCusUnit()
//								.getName());// 报关单位
//
//				c.setUnitConvert(srh.getUnitConvert() == null ? 0.0 : srh
//						.getUnitConvert());// 折算比例
//
//				c.setHsAmount(CommonUtils.getDoubleExceptNull(c
//						.getCheckAmount())
//						* CommonUtils.getDoubleExceptNull(c.getUnitConvert()));// 折算报关数量
//				c.setNote("");
//			} else {
//				c.setNote("此料号还没有做对应关系");
//			}
//
//			//1.如果查询条件为空，则全部加上
//			if (mList == null) {
//				listCheckAll.add(c);
//			} 
//			//2.如果编码为空，直接跳过
//			else if(c.getComplex() == null){
//				continue;
//			}
//			else {
//				for (String s : mList) {
//					if (s.equals(c.getPtNo() + "/" 
//							+ c.getComplex().getCode()+ "/" 
//							+ c.getName() + "/" 
//							+ c.getSpec() + "/"
//							+ c.getUnitName())) {
//						listCheckAll.add(c);
//						break;
//					}
//				}
//			}
//		}
//
//		System.out
//				.println("wss 料件的 listCheckAll.size = " + listCheckAll.size());
//
//		// 半成品、成品一起折算
//		listSemiCheck.addAll(listFinishedCheck);
//
//		// 临时BOM集合
//		List<TempBomRelation> bomsCheck = null;
//		String bomKeyCheck = null;
//
//		// key为成品料号 value为对应的临时BOM集合
//		Map<String, List<TempBomRelation>> bomMapCheck = new HashMap<String, List<TempBomRelation>>();
//
//		// 遍历要折料的成品、半成品
//		for (CheckBalance c : listSemiCheck) {
//			bomKey = c.getPtNo();
//			boms = bomMap.get(bomKey);
//			if (boms != null) {
//			} else {
//				BillDetail bill = getBillDetailByCheckBalance(c);
//				boms = this.getBomRelation(bill, mapMaterial, isAll);
//				bomMap.put(bomKey, boms);
//			}
//
//			System.out.println("wss bom.size = " + boms.size());
//			for (TempBomRelation bom : boms) {
//
//				// 加进到料件中
//				listCheckAll.add(setCheckBalanceConvertFinished(bom, c));
//			}
//		}
//
//		System.out.println("wss 加上要折的listCheckAll.size=" + listCheckAll.size());
		
		System.out.println("***********开始盘点的**直接抓折算后的****************");
		List<CheckBalanceConvertMateriel> listCheckMaterial = new ArrayList<CheckBalanceConvertMateriel>();
		
		
		// 盘点折的料件
		List<CheckBalanceConvertMateriel> listMaterielCheck = new ArrayList<CheckBalanceConvertMateriel>();
		
		//wss2010.08.16可以这样子加吗？
		String leftOuter = " left join fetch a.complex left join fetch a.wareSet  ";
		
		listMaterielCheck = casDao.commonSearch("", "CheckBalanceConvertMateriel",
				materialConditionsCheck, "", leftOuter);
		
		System.out.println("wss listMaterielCheck.size " + listMaterielCheck.size());

		// ****************************************************************************
		// ******************************进行对比***************************************
		Map<String, TempCurrentBillDetailPanDian> hm = new HashMap();

		// 1.装下当日结存
		for (int i = 0; i < listDetailAll.size(); i++) {

			TempCurrentBillDetailPanDian bdpd = (TempCurrentBillDetailPanDian) listDetailAll
					.get(i);

			TempCurrentBillDetailPanDian temp;

			String key = getPandianKey(bdpd, ishsTaotal, isGroup);// 根据查询情况获取key

			if ((temp = hm.get(key)) == null) {
				hm.put(key, bdpd);
			} else {

				temp.setPtAmount(CommonUtils.getDoubleExceptNull(temp
						.getPtAmount())
						+ CommonUtils.getDoubleExceptNull(bdpd.getPtAmount())); // 工厂帐存数量

				temp.setPtCheckAmount(CommonUtils.getDoubleExceptNull(temp
						.getPtCheckAmount())
						+ CommonUtils.getDoubleExceptNull(bdpd
								.getPtCheckAmount())); // 工厂盘点数量

				temp.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(temp
						.getPtCheckAmount())
						- CommonUtils.getDoubleExceptNull(temp.getPtAmount()));// 工厂数量差额

				temp.setHsAmount(CommonUtils.getDoubleExceptNull(temp
						.getHsAmount())// 报关帐存数量
						+ CommonUtils.getDoubleExceptNull(bdpd.getHsAmount()));

				temp.setHsCheckAmount(CommonUtils.getDoubleExceptNull(temp
						.getHsCheckAmount())// 报关盘点数量
						+ CommonUtils.getDoubleExceptNull(bdpd
								.getHsCheckAmount()));

				temp.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(temp
						.getHsCheckAmount())
						- CommonUtils.getDoubleExceptNull(temp.getHsAmount())); // 报关数量差额
				hm.put(key,temp);
			}

		}

		// 2.装下盘点
		for (int i = 0; i < listMaterielCheck.size(); i++) {
			CheckBalanceConvertMateriel cb = listMaterielCheck.get(i);
			String ptNo = cb.getPtNo() == null ? "" : cb.getPtNo();
			String wareSetName = cb.getWareSet() == null ? "" : (cb
					.getWareSet().getName() == null ? "" : cb.getWareSet()
					.getName());
			// String key = ptNo + "/" + wareSetName;
//			String key = getCheckBalanceKey(cb, ishsTaotal, isGroup);
			String key = getCheckBalanceConvertMaterielKey(cb, ishsTaotal, isGroup);


			System.out.println("wss pandian key:" + key);
			TempCurrentBillDetailPanDian bdpd;

			// 如果有对应盘点
			if ((bdpd = hm.get(key)) != null) {
				bdpd.setPtCheckAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						+ CommonUtils.getDoubleExceptNull(cb.getCheckAmount()));// 工厂盘点数量

				bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount())); // 重算工厂数量差额

				bdpd.setHsCheckAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						+ CommonUtils.getDoubleExceptNull(cb.getHsAmount())); // 报关盘点数量

				bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount()));// 重算报关数量差额

			}
			// 只有盘点没结存
			else {
				bdpd = new TempCurrentBillDetailPanDian();

				bdpd.setPtNo(cb.getPtNo());

				bdpd.setPtName(cb.getPtName() == null ? "" : cb.getPtName());
				bdpd.setPtSpec(cb.getPtSpec() == null ? "" : cb.getPtSpec());

				bdpd.setPtNameSpec(bdpd.getPtName() + "/" + bdpd.getPtSpec()); // 工厂名称规格

				bdpd.setPtUnitName(cb.getPtUnit() == null ? "":cb.getPtUnit().getName());

				bdpd.setPtCheckAmount(cb.getCheckAmount()); // 工厂盘点数量

				bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getPtCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount())); // 工厂数量差额

				bdpd.setComplex(cb.getComplex());

				bdpd.setHsName(cb.getName() == null ? "" : cb.getName());
				bdpd.setHsSpec(cb.getSpec() == null ? "" : cb.getSpec());

				bdpd.setHsNameSpec(bdpd.getHsName() + "/" + bdpd.getHsSpec());

				bdpd.setHsUnitName(cb.getHsUnit() == null ? "" : cb.getHsUnit().getName());

				bdpd.setHsCheckAmount(cb.getHsAmount()); // 报关盘点数量

				bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
						.getHsCheckAmount())
						- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount())); // 报关数量差额
//				bdpd.setMaterialType(CheckMyselfUntil.getCheckBalanceType(cb)); // 类型
				bdpd.setWareSet(cb.getWareSet());// 仓库

				hm.put(key, bdpd);

			}
		}

		System.out.println("hm 中有值吗？ 大小为 = " + hm.size());

		List<TempCurrentBillDetailPanDian> resultList = new ArrayList(hm
				.values());
		List<TempCurrentBillDetailPanDian> rs = new ArrayList();

		// Comparator comp = new Comparator() {
		// public int compare(Object o1, Object o2) {
		// TempCurrentBillDetailPanDian t1 = (TempCurrentBillDetailPanDian) o1;
		// TempCurrentBillDetailPanDian t2 = (TempCurrentBillDetailPanDian) o2;
		//
		// int ptP = (t1.getPtNo() == null ? "0" : t1.getPtNo())
		// .compareTo((t2.getPtNo() == null ? "0" : t2.getPtNo()));// 工厂工厂料号
		// int hsC;// 报关编码
		// int wC;// 仓库编码
		// if (ptP > 0) {
		// return 1;
		// } else if (ptP < 0) {
		// return -1;
		// } else if ((hsC = t1.getComplex().getCode().compareTo(
		// t2.getComplex().getCode())) > 0) {
		// return 1;
		// } else if (hsC < 0) {
		// return -1;
		// } else if ((wC = t1.getWareSet().getCode().compareTo(// 仓库
		// t2.getWareSet().getCode())) > 0) {
		// return 1;
		// } else if (wC < 0) {
		// return -1;
		// } else {
		// return 0;
		// }
		// }
		// };
		//		
		// Collections.sort(resultList, comp);

		// 显示负数结存
		if (isShowLess) {
			for (TempCurrentBillDetailPanDian pd : resultList) {
				//数量不为null且数量小于0
				if (ishsTaotal ? (pd.getHsAmount() != null && pd.getHsAmount() < 0 )
						:(pd.getPtAmount() != null && pd.getPtAmount() < 0)) {
					rs.add(pd);
				}
			}
			return rs;
		} else {
			return resultList;
		}

	}

	/**
	 * 将单据体及Bom转化为盘点差额
	 * 
	 * @param bom
	 * @param sourceBill
	 * @return
	 * @author wss
	 */
	private TempCurrentBillDetailPanDian setTempCurrentBillDetailPanDianFinished(
			TempBomRelation bom, BillDetail sourceBill, int inOut) {

		TempCurrentBillDetailPanDian bdpd = new TempCurrentBillDetailPanDian();

		if (bom.getUnitUsed() != null  &&sourceBill.getPtAmount() != null  ) {

			bdpd.setPtAmount(inOut * (sourceBill.getPtAmount()== null ? 0.0 : sourceBill.getPtAmount())
					* bom.getUnitUsed());// 工厂数量
		} else {
			bdpd.setPtAmount(0.0);
		}

		bdpd.setPtNo(bom.getPtNo());
		bdpd.setPtName(bom.getPtName());
		bdpd.setPtSpec(bom.getPtSpec());
		bdpd.setPtNameSpec(bdpd.getPtName() + "/" + bdpd.getPtSpec());
		bdpd.setPtUnitName(bom.getPtUnit() == null ? null : bom.getPtUnit()
				.getName());

		bdpd.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
				.getPtCheckAmount())
				- CommonUtils.getDoubleExceptNull(bdpd.getPtAmount()));// 工厂数量差额

		bdpd.setComplex(bom.getComplex());

		bdpd.setHsName(bom.getHsName());
		bdpd.setHsSpec(bom.getHsSpec());
		bdpd.setHsNameSpec(bdpd.getHsName() + "/" + bdpd.getHsSpec());

		bdpd.setHsUnitName(bom.getHsUnit() == null ? null : bom.getHsUnit()
				.getName());

		bdpd.setHsAmount(bdpd.getPtAmount()
				* CommonUtils.getDoubleExceptNull(bom.getUnitConvert())); // 报关数量

		bdpd.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(bdpd
				.getHsCheckAmount())
				- CommonUtils.getDoubleExceptNull(bdpd.getHsAmount())); // 报关数量差额

		bdpd.setMaterialType("currentTotal");// 类型
		bdpd.setWareSet(sourceBill.getWareSet());// 仓库
		
		System.out.println("wss ptAmount:" + bdpd.getPtAmount());
		System.out.println("wss ptCheckAmount:" + bdpd.getPtCheckAmount());
		System.out.println("wss ptBalanceAmount:" + bdpd.getPtBalanceAmount());

		System.out.println("wss hsAmount:" + bdpd.getHsAmount());
		System.out.println("wss hsCheckAmount:" + bdpd.getHsCheckAmount());
		System.out.println("wss hsBalanceAmount:" + bdpd.getHsBalanceAmount());


		return bdpd;

	}

	/**
	 * 将纯料件单据转化为盘点信息
	 * 
	 * @param bill
	 * @return
	 * @author wss
	 */
	private TempCurrentBillDetailPanDian getTempCurrentBillDetailPanDianMaterial(
			BillDetail bill, int inOut) {
		TempCurrentBillDetailPanDian b = new TempCurrentBillDetailPanDian();

		b.setPtAmount(inOut * (bill.getPtAmount() == null ? 0.0 :bill.getPtAmount()));// 工厂数量

		b.setPtNo(bill.getPtPart());
		b.setPtName(bill.getPtName());
		b.setPtSpec(bill.getPtSpec());
		b.setPtNameSpec(b.getPtName() + "/" + b.getPtSpec());
		b.setPtUnitName(bill.getPtUnit().getName());

		b.setComplex(bill.getComplex());
		b.setHsName(bill.getHsName());
		b.setHsSpec(bill.getHsSpec());

		b.setHsNameSpec(b.getHsName() + "/" + b.getHsSpec());

		b.setHsUnitName(bill.getHsUnit().getName());

		b.setPtBalanceAmount(CommonUtils.getDoubleExceptNull(b
				.getPtCheckAmount())
				- CommonUtils.getDoubleExceptNull(b.getPtAmount()));// 工厂数量差额

		b.setHsAmount(inOut * (bill.getHsAmount() == null ? 0.0:bill.getHsAmount()));// 折算报关数量

		b.setHsBalanceAmount(CommonUtils.getDoubleExceptNull(
				b.getHsCheckAmount())
				- CommonUtils.getDoubleExceptNull(b.getHsAmount())); // 报关数量差额

		b.setMaterialType("currentTotal");// 类型

		b.setWareSet(bill.getWareSet());
		
		System.out.println("wss ptAmount:" + b.getPtAmount());
		System.out.println("wss ptCheckAmount:" + b.getPtCheckAmount());
		System.out.println("wss ptBalanceAmount:" + b.getPtBalanceAmount());

		System.out.println("wss hsAmount:" + b.getHsAmount());
		System.out.println("wss hsCheckAmount:" + b.getHsCheckAmount());
		System.out.println("wss hsBalanceAmount:" + b.getHsBalanceAmount());

		return b;
	}

	/**
	 * 将临时BOM转化为CheckBalance
	 * 
	 * @param bom
	 * @param c
	 * @param ishsTaotal
	 * @return
	 * @author wss
	 */
	public CheckBalance setCheckBalanceConvertFinished(TempBomRelation bom,
			CheckBalance oldc) {
		CheckBalance c = new CheckBalance();
		c.setLjCpMark("0");// 已经变成料件了

		c.setComplex(bom.getComplex());
		c.setName(bom.getHsName());
		c.setSpec(bom.getHsSpec());
		c.setUnitName(bom.getHsUnit().getName());

		c.setCheckAmount(oldc.getCheckAmount() * (bom.getUnitUsed() == null ? 0.0:bom.getUnitUsed()));// 工厂盘点数量

		c.setPtNo(bom.getPtNo());
		c.setPtName(bom.getPtName());
		c.setPtSpec(bom.getPtSpec());
		c.setPtUnitName(bom.getPtUnit().getName());

		c.setHsAmount(c.getCheckAmount() * (bom.getUnitConvert() == null ? 0.0:bom.getUnitConvert()));// 盘点数量折算报关数量

		c.setWareSet(oldc.getWareSet());

		return c;
	}

	//	
	// public String getBillTempBomKey(TempBomRelation bom,BillDetail
	// bill,boolean ishsTaotal,boolean isGroup){
	// String key = "";
	//
	// // 料号 + 名称 + 规格
	// key = bom.getPtNo() + "/" + bom.getPtName() + "/"+ bom.getPtSpec();
	//
	// if (ishsTaotal) {
	//
	// // 海关编码 + 报关名称 + 报关规格
	// key = bom.getComplex().getCode() + "/" + bom.getHsName() + "/"
	// + bom.getHsSpec();
	// }
	//
	// if (isGroup) {// 仓库
	// key += "/" + bill.getWareSet().getCode();
	// }
	//		
	// return key;
	// }

	/**
	 * 半成品结存与盘点差额表 盘点
	 * 
	 * @author wss
	 */
	public String getPandianKey(TempCurrentBillDetailPanDian pd,
			boolean ishsTaotal, boolean isGroup) {
		String key1 = pd.getPtNo() + "/" + pd.getPtName() + "/"
				+ pd.getPtSpec();

		String key2 = pd.getComplex() == null ? "" : pd.getComplex().getCode()
				+ "/" + pd.getHsName() + "/" + pd.getHsSpec();

		String key = "";

		if (ishsTaotal) {
			key = key2;
		} else {
			key = key1 + "/" + key2;
		}
		if (isGroup) {
			key += "/" + pd.getWareSet() == null ? "" : pd.getWareSet()
					.getCode();
		}
		return key;
	}

	/**
	 * 半成品结存与盘点差额表 盘点 key2
	 * 
	 * @author wss
	 */
	private String getCheckBalanceConvertMaterielKey(CheckBalanceConvertMateriel cb, boolean ishsTaotal,
			boolean isGroup) {

		String key1 = cb.getPtNo() + "/" + cb.getPtName() + "/"
				+ cb.getPtSpec();
		String key2 = cb.getComplex() == null ? "" : cb.getComplex().getCode()
				+ "/" + cb.getName() + "/" + cb.getSpec();
		String key = "";

		if (ishsTaotal) {
			key = key2;
		} else {
			key = key1 + "/" + key2;
		}
		if (isGroup) {
			key += "/" + cb.getWareSet() == null ? "" : cb.getWareSet()
					.getCode();
		}
		return key;
	}

	/**
	 * 残次品当日结存折料情况
	 * 
	 * @param finishedConditions
	 * @param materialConditions
	 * @param finishedConditionsBalance 
	 * @param isCheckBalance 
	 * @param orderBy
	 * @param materielType
	 * @param ishsTaotal
	 * @param isShowLess
	 * @param isGroup
	 * @return 2010-07-06 hcl
	 */
	public List getBadProductBillDtailToBom(List<Condition> finishedConditions,
			List<Condition> materialConditions, List<Condition> finishedConditionsBalance, boolean isCheckBalance, String orderBy,
			String materielType, boolean ishsTaotal, boolean isShowLess,
			boolean isGroup) {
		if(isCheckBalance)
			return getCheckBalanceConvertMateriel(materialConditions,finishedConditionsBalance);
		List<TempThatDayBalance> tempList = getCurrentBillDetail(
				finishedConditions, orderBy, MaterielType.BAD_PRODUCT, false,
				false, isGroup,false,null);
		List<BillDetail> billList = new ArrayList<BillDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BillDetail bill = getBillDetailByTempThatDayBalance(tempList.get(i));
			billList.add(bill);
		}

		List<BillDetail> materialList = new ArrayList<BillDetail>();
		List<BillDetail> semiList = new ArrayList<BillDetail>();
		List<BillDetail> listMaterialAdd = new ArrayList<BillDetail>();

		for (int i = 0; i < billList.size(); i++) {
			
			if (billList.get(i).getNote().equals("料件")) {
				materialList.add(billList.get(i));
			} else
				semiList.add(billList.get(i));

		}

		// 按料件条件集合conditionsMaterial找出料件集合
		boolean isAll = false;
		if (materialConditions.size() == 0)
			isAll = true;
		Map<String, String> mapMaterial = getResultMaterielKey(materialConditions);
		List<String> mList = null;
		if (mapMaterial != null) {
			mList = new ArrayList<String>(mapMaterial.values());
			System.out.println("mList.size()=" + mList.size());
		}

		System.out.println("materialList.size=" + materialList.size());
		System.out.println("semiList.size=" + semiList.size());
		// 判断料件
		for (int i = 0; i < materialList.size(); i++) {
			if (isAll) {
				listMaterialAdd.add(materialList.get(i));
			
			}else if(materialList.get(i).getComplex() == null 
					|| materialList.get(i).getHsUnit() == null ){
				continue;
			}
			
			else
				for (String s : mList) {
					if (s.equals(materialList.get(i).getPtPart() + "/"
							+ materialList.get(i).getComplex().getCode() + "/"
							+ materialList.get(i).getHsName() + "/"
							+ materialList.get(i).getHsSpec() + "/"
							+ materialList.get(i).getHsUnit().getName())) {
						listMaterialAdd.add(((BillDetail) materialList.get(i)));
					}
				}
		}

		// 成品折料
		// 查找BOM,折料，判断成品、半成品所折的料件是否跟条件里的料件一致
		List<TempBomRelation> boms = null;
		String bomKey = null;
		Map<String, List<TempBomRelation>> bomMap = new HashMap<String, List<TempBomRelation>>();
		List<TempBomBillDetail> listFinishAdd = new ArrayList<TempBomBillDetail>();
		List<ToBomTempThatDayBalance> listAdd = new ArrayList<ToBomTempThatDayBalance>();
		for (BillDetail bill : billList) {
			bomKey = bill.getPtPart();
			boms = bomMap.get(bomKey);
			if (boms != null) {
			} else {
				boms = this.getBomRelation(bill, mapMaterial, isAll);
				bomMap.put(bomKey, boms);
			}
			System.out.println("boms.size=" + boms.size());
			for (TempBomRelation bom : boms) {
				listAdd.add(setToBomTempThatDayBalance(bom, bill));
			}
		}

		// 将料件转换后加入到结果中
		if (listMaterialAdd.size() != 0) {
			for (int i = 0; i < listMaterialAdd.size(); i++) {
				BillDetail bill = listMaterialAdd.get(i);
				listAdd.add(setToBomTempThatDayBalance(bill));
			}
		}

		// 按成品料号，BOM报关名称排序
		Comparator comp = null;
		if (ishsTaotal)
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					ToBomTempThatDayBalance p1 = (ToBomTempThatDayBalance) o1;
					ToBomTempThatDayBalance p2 = (ToBomTempThatDayBalance) o2;
					if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return 1;
					else if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return -1;
					else if (p1.getHsName().compareTo(p2.getHsName()) > 0)
						return 1;
					else if (p1.getHsName().compareTo(p2.getHsName()) < 0)
						return -1;
					else
						return 0;
				}
			};
		else
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					ToBomTempThatDayBalance p1 = (ToBomTempThatDayBalance) o1;
					ToBomTempThatDayBalance p2 = (ToBomTempThatDayBalance) o2;
					if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return 1;
					else if (p1.getProduct().getPtPart().compareTo(
							p2.getProduct().getPtPart()) > 0)
						return -1;
					else
						return 0;
				}
			};
		Collections.sort(listAdd, comp);
		Map<String, Double> hsMap = new HashMap<String, Double>();
		String HsKey = "";
		Double value = null;
		for (int i = 0; i < listAdd.size(); i++) {
			ToBomTempThatDayBalance t = listAdd.get(i);
			HsKey = getHsKey(t);
			value = hsMap.get(HsKey);
			if (value == null) {
				value = t.getHsAmount();
				hsMap.put(HsKey, value);
			} else {
				value += t.getHsAmount();
				hsMap.put(HsKey, value);
			}
		}
		for (int i = 0; i < listAdd.size(); i++) {
			ToBomTempThatDayBalance t = listAdd.get(i);
			System.out.println("key=" + getHsKey(t));
			System.out.println("value=" + hsMap.get(getHsKey(t)));
			t.setHsAllAmount(hsMap.get(getHsKey(t)));
		}
		return listAdd;
	}

	private List getCheckBalanceConvertMateriel(
			List<Condition> materialConditions,
			List<Condition> finishedConditionsBalance) {
		
		List list=casCheckDao.getCheckBalanceConvertMateriel(finishedConditionsBalance,materialConditions,"order by name,spec,hsUnit");
		System.out.println("list.size="+list.size());
		Map<String, Double> hsMap = new HashMap<String, Double>();
		String HsKey = "";
		Double value = null;
		for (int i = 0; i < list.size(); i++) {
			CheckBalanceConvertMateriel t = (CheckBalanceConvertMateriel)list.get(i);
			HsKey = getHsKey(t);
			value = hsMap.get(HsKey);
			if (value == null) {
				value = t.getHsAmount();
				hsMap.put(HsKey, value);
			} else {
				value += t.getHsAmount();
				hsMap.put(HsKey, value);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			CheckBalanceConvertMateriel t = (CheckBalanceConvertMateriel)list.get(i);
			System.out.println("key=" + getHsKey(t));
			System.out.println("value=" + hsMap.get(getHsKey(t)));
			t.setTempDouble(hsMap.get(getHsKey(t)));
		}
		return list;
	}

	private ToBomTempThatDayBalance setToBomTempThatDayBalance(BillDetail bill) {
		ToBomTempThatDayBalance t = new ToBomTempThatDayBalance();
		TempThatDayBalance p = new TempThatDayBalance();
		p.setPtPart(bill.getPtPart());
		p.setPtSpec(bill.getPtSpec());
		p.setPtName(bill.getPtName());
		p.setPtUnit(bill.getPtUnit());
		p.setHsName(bill.getHsName());
		p.setHsSpec(bill.getHsSpec());
		p.setHsUnit(bill.getHsUnit());
		p.setHsAmount(bill.getHsAmount());
		p.setPtAmount(bill.getPtAmount());
		p.setUnitConvert(bill.getUnitConvert());
		p.setWareSet(bill.getWareSet());
		
		t.setProduct(p);
		t.setPtPart(bill.getPtPart());
		t.setPtUnit(bill.getPtUnit());
		t.setHsName(bill.getHsName());
		t.setHsSpec(bill.getHsSpec());
		t.setHsUnit(bill.getHsUnit());
		t.setHsAmount(bill.getHsAmount());
		t.setHsAllAmount(bill.getHsAmount());
		t.setWareAmount(bill.getPtAmount());
		t.setUnitConvert(bill.getUnitConvert());
		return t;
	}

	private TempBomRelation getMaterialRelation(BillDetail bill,
			Map<String, String> mapMaterial, boolean isAll) {
		TempBomRelation temp = null;
		List<MaterialBomDetail> listBom = materialManageDao
				.findMaterialBomByDetailByptno(bill.getPtPart(), bill
						.getVersion());//
		System.out.println("listBom111111111.size()=" + listBom.size());
		MaterialBomDetail bomDetail = new MaterialBomDetail();
		if (listBom.size() != 0)
			bomDetail = listBom.get(0);
		List<FactoryAndFactualCustomsRalation> listRelation = this.casDao
				.findFactoryAndFactualCustomsRalation(bomDetail.getMateriel());
		System.out.println("listRelation.size()=" + listRelation.size());
		FactoryAndFactualCustomsRalation c = (FactoryAndFactualCustomsRalation) listRelation
				.get(0);
		String key = c.getMateriel().getPtNo() + "/"
				+ c.getStatCusNameRelationHsn().getComplex().getCode() + "/"
				+ c.getStatCusNameRelationHsn().getCusName() + "/"
				+ c.getStatCusNameRelationHsn().getCusSpec() + "/"
				+ c.getStatCusNameRelationHsn().getCusUnit().getName();
		System.out.println("key=" + key);
		if (isAll) {
			temp = new TempBomRelation();
			temp.setPtNo(c.getMateriel().getPtNo());
			temp.setPtName(c.getMateriel().getFactoryName());
			temp.setPtSpec(c.getMateriel().getFactorySpec());
			temp.setPtUnit(c.getMateriel().getCalUnit());
			temp.setHsName(c.getStatCusNameRelationHsn().getCusName());
			temp.setHsSpec(c.getStatCusNameRelationHsn().getCusSpec());
			temp.setHsUnit(c.getStatCusNameRelationHsn().getCusUnit());
			temp.setUnitUsed(bomDetail.getUnitUsed());
			temp.setUnitConvert(c.getUnitConvert());
			temp.setUnitWaste(bomDetail.getUnitWaste());
			temp.setWaste(bomDetail.getWaste());
		}
		if (mapMaterial.get(key) != null) {
			temp = new TempBomRelation();
			temp = new TempBomRelation();
			temp.setPtNo(c.getMateriel().getPtNo());
			temp.setPtName(c.getMateriel().getFactoryName());
			temp.setPtSpec(c.getMateriel().getFactorySpec());
			temp.setPtUnit(c.getMateriel().getCalUnit());
			temp.setComplex(c.getStatCusNameRelationHsn().getComplex());
			temp.setHsName(c.getStatCusNameRelationHsn().getCusName());
			temp.setHsSpec(c.getStatCusNameRelationHsn().getCusSpec());
			temp.setHsUnit(c.getStatCusNameRelationHsn().getCusUnit());
			temp.setUnitUsed(bomDetail.getUnitUsed());
			temp.setUnitConvert(c.getUnitConvert());
			temp.setUnitWaste(bomDetail.getUnitWaste());
			temp.setWaste(bomDetail.getWaste());
		}

		return temp;
	}

	/**
	 * 将Tempbom 转化为 当日结存
	 * 
	 * @param balanceMap
	 * @param bill
	 * @param bom
	 * @param inOut
	 * @param billDetail
	 * @param key
	 * @author wss
	 */
	private void transferBalanceFromTempBom(
			Map<String, TempThatDayBalance> balanceMap, BillDetail bill,
			TempBomRelation bom, int inOut, boolean ishsTaotal, boolean isGroup) {

		// 单据临时实体
		TempThatDayBalance balanceTemp = null;
		TempThatDayBalance newTemp = new TempThatDayBalance();

		newTemp.setPtAmount(inOut
				* CommonUtils.getDoubleExceptNull(bill.getPtAmount())
				* CommonUtils.getDoubleExceptNull(bom.getUnitUsed()));// 工厂数量 =
		// 单据数量
		// *
		// 单项用量

		newTemp.setUnitConvert(bom.getUnitConvert());// 折算系数就为对应关系中的折算系数

		// 为报关常用工厂物料 中的折算系数
		newTemp.setHsAmount(CommonUtils.getDoubleExceptNull(newTemp
				.getPtAmount())
				* CommonUtils.getDoubleExceptNull(newTemp.getUnitConvert()));// 报关数量
		// =
		// 工厂数量
		// *
		// 折算系数
		String key = getBillTempBomKey(bom, bill, ishsTaotal, isGroup);

		System.out.println(" wss key : " + key);
		System.out.println(" wss newTemp.ptAmount = " + newTemp.getPtAmount());
		System.out.println(" wss newTemp.hsAmount = " + newTemp.getHsAmount());
		if ((balanceTemp = balanceMap.get(key)) == null) {
			if (!ishsTaotal) { // 如果以报关名称汇总则不用 企业内部物料资料
				newTemp.setPtPart(bom.getPtNo());
				newTemp.setPtName(bom.getPtName());
				newTemp.setPtSpec(bom.getPtSpec());
				newTemp.setPtUnit(bom.getPtUnit());// 工厂单位
			}
			newTemp.setComplex(bom.getComplex());
			newTemp.setHsName(bom.getHsName());
			newTemp.setHsSpec(bom.getHsSpec());
			newTemp.setHsUnit(bom.getHsUnit());

			newTemp.setWareSet(bill.getWareSet());

			balanceMap.put(key, newTemp);
		} else {
			balanceTemp.setPtAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getPtAmount())
					+ CommonUtils.getDoubleExceptNull(newTemp.getPtAmount()));
			balanceTemp.setHsAmount(CommonUtils.getDoubleExceptNull(balanceTemp
					.getHsAmount())
					+ CommonUtils.getDoubleExceptNull(newTemp.getHsAmount()));

			System.out.println(" wss balanceTemp.ptAmount = "
					+ balanceTemp.getPtAmount());
			System.out.println(" wss balanceTemp.hsAmount = "
					+ balanceTemp.getHsAmount());

			balanceMap.put(key, balanceTemp);
		}
	}

	/**
	 * 在产品当日结存billdetail 与bom 的key
	 * 
	 * @param billDetail
	 * @param ishsTaotal
	 *            是否报关名称汇总
	 * @param isGroup
	 *            是否按仓库分组
	 */
	private String getBillTempBomKey(TempBomRelation bom,
			BillDetail billDetail, boolean ishsTaotal, boolean isGroup) {

		String key = "";

		// 料号 + 名称 + 规格
		String key1 = bom.getPtNo() + "/" + bom.getPtName() + "/"
				+ bom.getPtSpec();
		
		// 海关编码 + 报关名称 + 报关规格
		String key2 = bom.getComplex().getCode() + "/" + bom.getHsName() + "/"
				+ bom.getHsSpec();

		if (ishsTaotal) {
			key = key2;
		} else {
			key = key1 + "/" + key2;
		}

		if (isGroup) {// 仓库
			key += "/" + billDetail.getWareSet().getCode();
		}
		return key;
	}

	
	/**
	 * 自我核查 获得边角料查询报表内容
	 * 
	 * @param request 请求控制
	 * @return 边角料状况查询报表
	 * @author wss
	 */
	public List<LeftoverMaterielStatInfo> findLeftoverMaterielStatInfo() {
		return this.casCheckDao.findLeftoverMaterielStatInfo();
	}
	
	/**
	 * 查找并计算边角料(按报关名称分时间段进行查询)
	 * 
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param projectTypeParam 模块类型参数(是来自bcs还是bcus......)
	 * @param isCancelBefore 是否核销前        
	 * @return 边角料计算结果
	 */
	public List<LeftoverMaterielStatInfo> findCalLeftoverMateriel(
			Date beginDate, Date endDate, ProjectTypeParam projectTypeParam, String taskId,
			Boolean isCancelBefore) {
		String companyId = CommonUtils.getCompany().getId();
		Boolean isRun = runMap.get(companyId);
		if (isRun != null && isRun.booleanValue() == true) {
			throw new RuntimeException("计算边角料正在进行中请稍后再运行 ！！");
		}
		runMap.put(companyId, true);
		try {

			//用于缓存数据的Maps
			ParameterMaps parameterMaps = new ParameterMaps();

			//显示条
			ProgressInfo progressInfo = ProcExeProgress.getInstance()
					.getProgressInfo(taskId);
			String clientTipMessage = "***********计算边角料 开始查询初始化大类与实际报关 对应关系... !!*****";
			System.out.println("wss " + clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);


			clientTipMessage = "***********计算边角料 开始查询海关商品大类对应... !!********";
			System.out.println("wss " + clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			this.initStatCusNameRelationMap(parameterMaps);
			
			/**
			 * 计算实际的报关折耗用
			 */
			if (projectTypeParam.getIsBcus() == true) {//（电子帐册）
				
				//核销前打税
				if(isCancelBefore){
					
					clientTipMessage = "计算边角料 初始化 BCUS 报关成品(直接出口+结转出口)的合计BOM Waster耗用... !!";
					progressInfo.setHintMessage(clientTipMessage);
	
					this.initCustomsProductMapWasterByBcus(beginDate, endDate,
							parameterMaps, progressInfo);
				}
				//已打税(联网监管只有核销后打税)
				clientTipMessage = "计算边角料   初始化 已打税... !!";
				progressInfo.setHintMessage(clientTipMessage);
				this.initLeftOverMaterielAlreadyTaxBcus(beginDate,endDate,
											parameterMaps,progressInfo);
				
				
			}
			if (projectTypeParam.getIsBcs() == true) {//电子化手册
				
				//核销前打税
				if(isCancelBefore){
					clientTipMessage = "计算边角料 初始化 BCS 报关成品(直接出口+结转出口)的合计BOM Waster耗用... !!";
					progressInfo.setHintMessage(clientTipMessage);
	
					this.initCustomsProductMapWasterByBCS(beginDate, endDate,
							parameterMaps, progressInfo);
				}
				//已打税
				clientTipMessage = "计算边角料   初始化 已打税... !!";
				progressInfo.setHintMessage(clientTipMessage);
				this.initLeftOverMaterielAlreadyTaxBcs(beginDate,endDate,
											parameterMaps,progressInfo,isCancelBefore);
				
			}
			if (projectTypeParam.getIsDzsc() == true) {//电子手册
				
				//核销前打税
				if(isCancelBefore){
					clientTipMessage = "计算边角料 初始化 DZSC 报关成品(直接出口+结转出口)的合计BOM Waster耗用... !!";
					progressInfo.setHintMessage(clientTipMessage);
					this.initCustomsProductMapWasterByDZSC(beginDate, endDate,
							parameterMaps, progressInfo);
				}
				//已打税
				clientTipMessage = "计算边角料   初始化 已打税... !!";
				progressInfo.setHintMessage(clientTipMessage);
				this.initLeftOverMaterielAlreadyTaxDzsc(beginDate,endDate,
											parameterMaps,progressInfo,isCancelBefore);
				
			}

			clientTipMessage = "开始计算边角料 ... !!";
			progressInfo.setHintMessage(clientTipMessage);

			List<LeftoverMaterielStatInfo> resultList = getResult(beginDate,
					endDate, parameterMaps, progressInfo,isCancelBefore);

			/**
			 * 开始删除历史记录
			 */
			clientTipMessage = "计算边角料 开始批量删除历史记录... !!";
			System.out.println("wss " + clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			this.casCheckDao.deleteLeftoverMaterielStatInfo();

			for (int i = 0, n = resultList.size(); i < n; i++) {
				LeftoverMaterielStatInfo item = (LeftoverMaterielStatInfo) resultList
						.get(i);
				item.setCompany(CommonUtils.getCompany());
				this.casDao.getHibernateTemplate().save(item);
			}
			return resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			runMap.put(companyId, false);
		}
	}
	
	/**
	 * 边角料已打税 报关单数量 Bcs电子化手册
	 * @param beginDate
	 * @param endDate
	 * @param parameterMaps
	 * @param progressInfo
	 * @param isCancelBefore
	 * @author wss
	 */
	public void initLeftOverMaterielAlreadyTaxBcs(Date beginDate,Date endDate,
			ParameterMaps parameterMaps,ProgressInfo progressInfo,Boolean isCancelBefore){
		
		List<Object> parameters = new ArrayList<Object>();

		String selects = " select a.commName,a.commSpec,a.unit.name,sum(a.commAmount) "
			+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration, "
			+ " Contract contract " 
			+ " where a.baseCustomsDeclaration.emsHeadH2k = contract.emsNo "
			+ " and a.baseCustomsDeclaration.effective = ? "
			+ " and a.baseCustomsDeclaration.declarationDate >= ? "
			+ " and a.baseCustomsDeclaration.declarationDate <= ? "
			+ " and a.baseCustomsDeclaration.impExpType in (?) "
			+ " and a.baseCustomsDeclaration.company.id = ? ";
			if(isCancelBefore){
				selects += " and contract.declareState = ? ";
			}else{
				selects += " and contract.declareState in(?,?) ";
			}
			selects += " group by a.commName,a.commSpec,a.unit.name ";

		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);//边角料内销
		parameters.add(CommonUtils.getCompany().getId());
		if(!isCancelBefore){
			parameters.add(DeclareState.PROCESS_EXE);//正在执行
		}
		parameters.add(DeclareState.CHANGING_CANCEL);//已核销

		//边角料
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询   边角料内销报关单数量  的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		System.out.println("wss " + clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口+返工复出-退厂返工
		//
		Map<String, Double> alReadyTax = parameterMaps.alReadyTax; // 边角料内销映射

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);

				String hsName = objs[0] == null ? "" : (String) objs[0];
				String hsSpec = objs[1] == null ? "" : (String) objs[1];
				String hsUnitName = objs[2] == null ? "" : (String) objs[2];
				Double hsAmount = objs[3] == null ? 0.0 : (Double) objs[3];
				String key = hsName + "/" + hsSpec + "/" + hsUnitName;
				if(alReadyTax.get(key) == null){
					alReadyTax.put(key, hsAmount);
				}else{
					alReadyTax.put(key, alReadyTax.get(key) + hsAmount);
				}
		}
	}
	
	
	/**
	 * 边角料已打税 报关单数量 Dzsc
	 * @param beginDate
	 * @param endDate
	 * @param parameterMaps
	 * @param progressInfo
	 * @param isCancelBefore
	 * @author wss
	 */
	public void initLeftOverMaterielAlreadyTaxDzsc(Date beginDate,Date endDate,
			ParameterMaps parameterMaps,ProgressInfo progressInfo,Boolean isCancelBefore){
		
		List<Object> parameters = new ArrayList<Object>();

		String selects = " select a.commName,a.commSpec,a.unit.name,sum(a.commAmount) "
			+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration, "
			+ " DzscEmsPorHead contract " 
			+ " where a.baseCustomsDeclaration.emsHeadH2k = contract.emsNo "
			+ " and a.baseCustomsDeclaration.effective = ? "
			+ " and a.baseCustomsDeclaration.declarationDate >= ? "
			+ " and a.baseCustomsDeclaration.declarationDate <= ? "
			+ " and a.baseCustomsDeclaration.impExpType in (?) "
			+ " and a.baseCustomsDeclaration.company.id = ? ";
			if(isCancelBefore){
				selects += " and contract.declareState = ? ";
			}else{
				selects += " and contract.declareState in(?,?) ";
			}
			selects += " group by a.commName,a.commSpec,a.unit.name ";

		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);//边角料内销
		parameters.add(CommonUtils.getCompany().getId());
		if(!isCancelBefore){
			parameters.add(DeclareState.PROCESS_EXE);//正在执行
		}
		parameters.add(DeclareState.CHANGING_CANCEL);//已核销

		//边角料
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询   边角料内销报关单数量  的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		System.out.println("wss " + clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口+返工复出-退厂返工
		//
		Map<String, Double> alReadyTax = parameterMaps.alReadyTax; // 边角料内销映射

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);

				String hsName = objs[0] == null ? "" : (String) objs[0];
				String hsSpec = objs[1] == null ? "" : (String) objs[1];
				String hsUnitName = objs[2] == null ? "" : (String) objs[2];
				Double hsAmount = objs[3] == null ? 0.0 : (Double) objs[3];
				String key = hsName + "/" + hsSpec + "/" + hsUnitName;
				if(alReadyTax.get(key) == null){
					alReadyTax.put(key, hsAmount);
				}else{
					alReadyTax.put(key, alReadyTax.get(key) + hsAmount);
				}
		}
	}
	
	
	/**
	 * 边角料已打税 报关单数量 bcus（联网监管只有核销后打税）
	 * @param beginDate
	 * @param endDate
	 * @param parameterMaps
	 * @param progressInfo
	 * @param isCancelBefore
	 * @author wss
	 */
	public void initLeftOverMaterielAlreadyTaxBcus(Date beginDate,Date endDate,
			ParameterMaps parameterMaps,ProgressInfo progressInfo){
		
		List<Object> parameters = new ArrayList<Object>();

		String selects = " select a.commName,a.commSpec,a.unit.name,sum(a.commAmount) "
			+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration, "
			+ " EmsHeadH2k contract " 
			+ " where a.baseCustomsDeclaration.emsHeadH2k = contract.emsNo "
			+ " and a.baseCustomsDeclaration.effective = ? "
			+ " and a.baseCustomsDeclaration.declarationDate >= ? "
			+ " and a.baseCustomsDeclaration.declarationDate <= ? "
			+ " and a.baseCustomsDeclaration.impExpType in (?) "
			+ " and a.baseCustomsDeclaration.company.id = ? "
			+ " and contract.declareState = ? "
		    + " group by a.commName,a.commSpec,a.unit.name ";

		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);//边角料内销
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);//正在执行

		//边角料
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询   边角料内销报关单数量  的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		System.out.println("wss " + clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口+返工复出-退厂返工
		//
		Map<String, Double> alReadyTax = parameterMaps.alReadyTax; // 边角料内销映射

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);

				String hsName = objs[0] == null ? "" : (String) objs[0];
				String hsSpec = objs[1] == null ? "" : (String) objs[1];
				String hsUnitName = objs[2] == null ? "" : (String) objs[2];
				Double hsAmount = objs[3] == null ? 0.0 : (Double) objs[3];
				String key = hsName + "/" + hsSpec + "/" + hsUnitName;
				if(alReadyTax.get(key) == null){
					alReadyTax.put(key, hsAmount);
				}else{
					alReadyTax.put(key, alReadyTax.get(key) + hsAmount);
				}
		}
	}
	
	/**
	 * 初始化 statCusNameRelationMtListMap 和 statCusNameRelationMtValueMap 对应表
	 * @author wss
	 */
	private void initStatCusNameRelationMap(ParameterMaps parameterMaps) {
		
		/** key :报关名称+"/"+规格+"/"+单位名称  value:对应关系list,按料号排*/
		Map<String, List<FactoryAndFactualCustomsRalation>> statCusNameRelationMtListMap 
															= parameterMaps.statCusNameRelationMtListMap;

		List<FactoryAndFactualCustomsRalation> listExistStatCusNameRelationMt = this.casDao
				.findStatCusNameRelationMt(MaterielType.MATERIEL);
		for (int i = 0; i < listExistStatCusNameRelationMt.size(); i++) {
			FactoryAndFactualCustomsRalation mt = listExistStatCusNameRelationMt.get(i);
			Materiel materiel = mt.getMateriel();
			StatCusNameRelationHsn sr = mt.getStatCusNameRelationHsn();
			//
			// keyList = 报关名称+"/"+规格+"/"+单位名称
			//
			String hsName = sr.getCusName() == null ? "" : sr.getCusName();
			String hsSpec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
			String unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName();
			unitName = unitName == null ? "" : unitName;
			String tenKey = hsName + "/" + hsSpec + "/" + unitName;

			if (statCusNameRelationMtListMap.get(tenKey) == null) {
				List<FactoryAndFactualCustomsRalation> tempList = new ArrayList<FactoryAndFactualCustomsRalation>();
				tempList.add(mt);
				statCusNameRelationMtListMap.put(tenKey, tempList);
			} else {
				List<FactoryAndFactualCustomsRalation> tempList = statCusNameRelationMtListMap
						.get(tenKey);
				tempList.add(mt);
			}
		}
	}
	
	
	/**
	 * 初始化报关成品来电子帐册customsProductMap
	 * 
	 * @param beginDate 开始日期
	 * @param endDate 截止日期
	 * @author wss
	 */
	private void initCustomsProductMapWasterByBcus(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();

		String selects = " select sum(a.commAmount),v.id "
				+ " from CustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",EmsHeadH2kExg exg left join exg.emsHeadH2k "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.version = v.version  "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.emsHeadH2k.declareState = ? "
//				+ " and exg.emsHeadH2k.historyState = ? "
				+ " group by v.id "
				+ " order by v.id "; // order by 确保 bom id 只查询一次

		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
//		parameters.add(false);

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询所有 [联网监管] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		System.out.println(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		
		// init 直接出口+结转出口
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste; // 出口成品损耗量映射

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[1]; // version guid

			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);

			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "边角料计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				System.out.println("wss " + clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casCheckDao
						.findEmsHeadH2kBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				System.out.println("开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (emsHeadH2kBomList.size() <= 0) {
					System.out.println(" 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				//
				// 循环刚才临时保存的成品数据 --> 并拆料件
				//
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
//					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[1]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						System.out.println("边角料计算 Bcus Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];
						//
						// key=名称+"/"+规格+"/"+单位
						//                    
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;
						//
						// 损耗
						//
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						//
						// 损耗在电子帐册中是以百分之多小来存储的整数
						//
						waste = waste * 0.01;
						//
						// 单耗
						//
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 单项用量 = 单耗 / (1-损耗);
						//
						Double unitUsed = unitWear / (1 - waste);
						//
						// 成品单耗损
						//
						Double productMaterialWaste = tempHsAmount * unitUsed
								* waste;
						if (customsProductMapWaste.get(key) == null) {
							customsProductMapWaste.put(key,
										productMaterialWaste);
						} else {
							customsProductMapWaste.put(key,
										customsProductMapWaste.get(key)
												+ productMaterialWaste);
						}

					}
				}
				tempList.clear();

			}
		}
	}
	
	
	/**
	 * 初始化报关成品来自纸质手册customsProductMap
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	private void initCustomsProductMapWasterByBCS(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();

		String selects = " select sum(a.commAmount),exg.id "
				+ " from BcsCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
				+ ",ContractExg exg left join exg.contract "
				+ " where a.commSerialNo = exg.seqNum "
				+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.contract.emsNo "
				+ " and a.baseCustomsDeclaration.effective = ? "
				+ " and a.baseCustomsDeclaration.declarationDate >= ? "
				+ " and a.baseCustomsDeclaration.declarationDate <= ? "
				+ " and a.baseCustomsDeclaration.impExpType in (?,?)  "
				+ " and a.baseCustomsDeclaration.company.id = ? "
				+ " and exg.contract.declareState=? "
//				+ " and exg.contract.isCancel=? "
				+ " group by exg.id "
				+ " order by exg.id ";// order by 确保 exg id 只查询一次

		parameters.add(Boolean.valueOf(true));//报关单生效
		parameters.add(beginDate);//申报日期
		parameters.add(endDate);//申报日期
		parameters.add(ImpExpType.DIRECT_EXPORT);//成品出口
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);//转厂出口
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);//正在执行
//		parameters.add(false);

		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		
		
		String clientTipMessage = "边角料计算 开始查询所有 [无纸通关] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		System.out.println("wss " + clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口
		//
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste; // 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			System.out.println("****wss 数量：" + (Double)objs[0]);
			String versionId = (String) objs[1]; // version guid

			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);

			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "边角料计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				System.out.println("wss " + clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casCheckDao
						.findContractBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				System.out.println("wss " + "开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (emsHeadH2kBomList.size() <= 0) {
					System.out.println("wss " + " 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				//
				// 循环刚才临时保存的成品数据 --> 并拆料件
				//
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
//					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[1]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						System.out.println("wss" + "边角料计算 Bcs Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];
						//
						// key=名称+"/"+规格+"/"+单位
						//                    
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;
						//
						// 损耗
						//
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						waste = waste * 0.01;
						//
						// 单耗
						//
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 单项用量 = 单耗 / (1-损耗);
						//
						Double unitUsed = unitWear / (1 - waste);
						//
						// 成品单耗损
						//
						Double productMaterialWaste = tempHsAmount * unitUsed
								* waste;
						
						System.out.println("***********************************");
						System.out.println("wss key:" + key);
						System.out.println("wss hsAmount:" + tempHsAmount);
						System.out.println("wss waste:" + waste);
						System.out.println("wss unitWear:" + unitWear);
						System.out.println("wss unitUsed:" + unitUsed);
						System.out.println("wss productMaterialWaste:" + productMaterialWaste);


						if (customsProductMapWaste.get(key) == null) {
							customsProductMapWaste.put(key,
									productMaterialWaste);
						} else {
							customsProductMapWaste.put(key,
									customsProductMapWaste.get(key)
											+ productMaterialWaste);
						}
					}
				}
				tempList.clear();
			}
		}
	}

	/**
	 * 初始化报关成品来DZSC customsProductMap
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	private void initCustomsProductMapWasterByDZSC(Date beginDate, Date endDate,
			ParameterMaps parameterMaps, ProgressInfo progressInfo) {

		List<Object> parameters = new ArrayList<Object>();
		
		String selects = " select sum(a.commAmount),exg.id "
			+ " from DzscCustomsDeclarationCommInfo a left join a.baseCustomsDeclaration "
			+ ",DzscEmsExgBill exg left join exg.dzscEmsPorHead "
			+ " where a.commSerialNo = exg.seqNum "
			+ " and a.baseCustomsDeclaration.emsHeadH2k = exg.dzscEmsPorHead.emsNo "
			+ " and a.baseCustomsDeclaration.effective = ? "
			+ " and a.baseCustomsDeclaration.declarationDate >= ? "
			+ " and a.baseCustomsDeclaration.declarationDate <= ? "
			+ " and a.baseCustomsDeclaration.impExpType in (?,?)  "
			+ " and a.baseCustomsDeclaration.company.id = ? "
			+ " and exg.dzscEmsPorHead.declareState=? "
//			+ " and exg.contract.isCancel=? "
			+ " group by exg.id "
			+ " order by exg.id ";// order by 确保 exg id 只查询一次
		parameters.add(Boolean.valueOf(true));
		parameters.add(beginDate);
		parameters.add(endDate);
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
//		parameters.add(false);
//		parameters.add(true);
	
		// 成品
		long beginTime = System.currentTimeMillis();
		List list = casDao.commonSearch(selects, parameters.toArray());
		String clientTipMessage = "边角料计算 开始查询所有 [无纸通关] 报关成品 的共用时间: "
				+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
		System.out.println("wss " + clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		//
		// init 直接出口+结转出口+返工复出-退厂返工
		//
		Map<String, Double> customsProductMapWaste = parameterMaps.customsProductMapWaste; // 报关成品

		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;

		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[1]; // version guid

			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);

			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {
				beginTime = System.currentTimeMillis();
				clientTipMessage = "边角料计算 报关成品总记录 " + size
						+ " 条 正在(折成料件用量)计算前 " + (i + 1) + " 条记录";
				System.out.println("wss " + clientTipMessage);

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List emsHeadH2kBomList = this.casCheckDao
						.findDzscEmsBomBill(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				System.out.println("wss " + "开始查询 " + page + " 个版本时间 "
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ");

				if (emsHeadH2kBomList.size() <= 0) {
					System.out.println("wss " + " 对应的BOM明细为空!! ");
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = emsHeadH2kBomList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) emsHeadH2kBomList.get(j);
					String tempVersionId = (String) bomObjs[5];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}

				//
				// 循环刚才临时保存的成品数据 --> 并拆料件
				//
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double tempHsAmount = objs[0] == null ? 0.0
							: (Double) objs[0]; // 报关成品数量
//					Integer typeCode = (Integer) objs[1]; // 类型代码
					String tempVersionId = (String) objs[1]; // version guid
					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);

					if (tempObjes == null) {
						System.out.println("wss " + "边角料计算 Bcs Bom 明细为空  !! == "
								+ tempVersionId);
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						String hsName = bomObjs[0] == null ? ""
								: (String) bomObjs[0];
						String hsSpec = bomObjs[1] == null ? ""
								: (String) bomObjs[1];
						String hsUnitName = bomObjs[2] == null ? ""
								: (String) bomObjs[2];
						//
						// key=名称+"/"+规格+"/"+单位
						//                    
						String key = hsName + "/" + hsSpec + "/" + hsUnitName;
						//
						// 损耗
						//
						Double waste = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						waste = waste * 0.01;
						//
						// 单耗
						//
						Double unitWear = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];
						//
						// 单项用量 = 单耗 / (1-损耗);
						//
						Double unitUsed = unitWear / (1 - waste);
						//
						// 成品单耗损
						//
						Double productMaterialWaste = tempHsAmount * unitUsed
								* waste;

						if (customsProductMapWaste.get(key) == null) {
							customsProductMapWaste.put(key,
									productMaterialWaste);
						} else {
							customsProductMapWaste.put(key,
									customsProductMapWaste.get(key)
											+ productMaterialWaste);
						}
					}
				}
				tempList.clear();
			}
		}
	}
	/**
	 * 获得边角料计算结果集
	 * 
	 * @return 边角料计算结果
	 * @author wss
	 */
	private List<LeftoverMaterielStatInfo> getResult(Date beginDate,
			Date endDate, ParameterMaps parameterMaps, ProgressInfo progressInfo,Boolean isCancelBefore) {
		List<LeftoverMaterielStatInfo> resultList = new ArrayList<LeftoverMaterielStatInfo>();
		HashMap<String, LeftoverMaterielStatInfo> resultHash = new HashMap<String, LeftoverMaterielStatInfo>();

//		List<Condition> conditions = new ArrayList<Condition>();
//
//		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
//				Boolean.valueOf(true), null));
//		conditions.add(new Condition("and", null, "hsName is not null ", null,
//				null, null));
//		conditions.add(new Condition("and", "(", "billMaster.billType.code",
//				"=", "6001", null));
//		conditions.add(new Condition("or", null, "billMaster.billType.code",
//				"=", "6003", null));
//		conditions.add(new Condition("or", null, "billMaster.billType.code",
//				"=", "6004", null));
//		conditions.add(new Condition("or", null, "billMaster.billType.code",
//				"=", "6101", null));
//		conditions.add(new Condition("or", null, "billMaster.billType.code",
//				"=", "6102", null));
//		conditions.add(new Condition("or", null, "billMaster.billType.code",
//				"=", "6104", ")"));
//		conditions.add(new Condition("and", null, " billMaster.validDate ",
//				">=", beginDate, null));
//		conditions.add(new Condition("and", null, " billMaster.validDate ",
//				"<=", endDate, null));
//
//		String billDetailRemainMateriel = BillUtil
//				.getBillDetailTableNameByMaterielType(MaterielType.REMAIN_MATERIEL);
//
//		String selects = "select sum(a.hsAmount),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.complex.code  ";
//		String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name,a.complex.code ";
//		String leftOuter = " left join a.billMaster left join a.billMaster.billType left join a.hsUnit left join a.complex  ";
//
//		// 边角料
//		List listBillDetailMateriel = casDao.commonSearch(selects,
//				billDetailRemainMateriel, conditions, groupBy, leftOuter);
		
		
		//使用原生sql 查询所有的边角料单据
		
		String billDetailLeftOver = "BDLMateriel"; // 边角料单据体
		String billMasterLeftOver = "BMLMateriel"; // 边角料单据头
		
		int beginYear = beginDate.getYear() + 1900;
		int endYear = endDate.getYear()+ 1900;
		System.out.println("beginYear:" + beginYear + "endYear:" + endYear);

		//wss:转化为原生sql查询
		String selects = " select cbillType.code billTypeCode,ecomplex.code hsCode,abillDetail.hsName, " 
						+ " abillDetail.hsSpec,dhsUnit.name,sum(abillDetail.hsAmount) aHsAmount ";
		String where = " where abillDetail.billMasterId = bbillMaster.id " 
							+ " and bbillMaster.billTypeId = cbillType.id "
								+ " and abillDetail.hsUnitId = dhsUnit.code " 
								+ " and abillDetail.complexid = ecomplex.id " 
									+ " and bbillMaster.validDate >= ? " 
										+ " and bbillMaster.validDate <= ? " 
											+ " and bbillMaster.isValid = ?  " 
												+ " and abillDetail.hsName is not null  ";
		
		String groupBy = " group by cbillType.code,ecomplex.code,abillDetail.hsName," 
															+ " abillDetail.hsSpec,dhsUnit.name ";
		//返回类型边角料
		Map backTypeMap = new HashMap<String, NullableType>();
		backTypeMap.put("billTypeCode",Hibernate.STRING);
		backTypeMap.put("hsCode",Hibernate.STRING);
		backTypeMap.put("hsName", Hibernate.STRING);
		backTypeMap.put("hsSpec", Hibernate.STRING);
		backTypeMap.put("name", Hibernate.STRING);
		backTypeMap.put("aHsAmount", Hibernate.DOUBLE);
		
		//传入参数
		List objParams = new ArrayList();
		objParams.add(CommonUtils.getBeginDate(beginDate));
		objParams.add(CommonUtils.getEndDate(endDate));
		objParams.add(1);		
		
		List listAll = new ArrayList();
		List listBillDetailLeftOver = new ArrayList();
		String billTypeTable = "billtype";
		String hsUnitTable = "unit";
		String complexTable = "complex";
		
		for(int i=beginYear ; i<=endYear ; i++){
			String detailTable = billDetailLeftOver.trim() + i;
			String masterTable = billMasterLeftOver.trim() + i;
			
			System.out.println("now wss: detailTable = " + detailTable);
			System.out.println("now wss: masterTable = " + masterTable);
			
			String from = " from  " + detailTable  + " abillDetail, " 
									+ masterTable + " bbillMaster, "
									 + billTypeTable + " cbillType, "
									  + hsUnitTable + " dhsUnit, "
									  + complexTable + " ecomplex ";

			String sql = selects + from + where + groupBy ;
			
			System.out.println("wss sql = " + sql);
			
			try{
				
				listBillDetailLeftOver = casDao.findBySql(sql, backTypeMap, objParams);
				if(listBillDetailLeftOver == null || listBillDetailLeftOver.size()== 0){
					throw new Exception("查询结果为null 或没有数据");
				}
				listAll.addAll(listBillDetailLeftOver);
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("可能没有这一年的数据，或是有其它sql查询问题！！！wss");
				continue;
			}
		}
		
		

		int size = listAll.size();
		for (int i = 0; i < size; i++) {

			Object[] objs = (Object[]) listAll.get(i);
//			
//			System.out.println("0:" + objs[0].toString());
//			System.out.println("1:" + objs[1].toString());
//			System.out.println("2:" + objs[2].toString());
//			System.out.println("3:" + objs[3].toString());
//			System.out.println("4:" + objs[4].toString());
//			System.out.println("5:" + objs[5].toString());
			
			String typeCode = objs[4] == null ? "" : (String) objs[4];
			String hsCode = objs[2] == null ? "" : (String) objs[2];
			String hsName = objs[5] == null ? "" : (String) objs[5];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[1] == null ? "" : (String) objs[1];
			Double hsAmount = objs[0] == null ? 0.0 : (Double) objs[0];
			

			// key=名称+"/"+规格+"/"+单位
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			System.out.println("wss key:" + key);
			LeftoverMaterielStatInfo item = null;

			if (resultHash.get(key) != null) {
				item = (LeftoverMaterielStatInfo) resultHash.get(key);
			} else {
				item = new LeftoverMaterielStatInfo();
				item.setHsName(hsName);
				item.setHsSpec(hsSpec);
				item.setHsUnitName(hsUnitName);
				item.setComplex(hsCode);
				resultHash.put(key, item);
			}

			if (typeCode.equals("6001")
					|| typeCode.equals("6002")
					|| typeCode.equals("6003")
					|| typeCode.equals("6004")
					|| typeCode.equals("6005")
					|| typeCode.equals("6006")
					|| typeCode.equals("6007")) {
				item.setF1(CommonUtils.getDoubleExceptNull(item.getF1()) + hsAmount);
			} else if (typeCode.equals("6101")
					|| typeCode.equals("6102")
					|| typeCode.equals("6103")
					|| typeCode.equals("6104")
					|| typeCode.equals("6105")
					|| typeCode.equals("6106")) {
				item.setF2(CommonUtils.getDoubleExceptNull(item.getF2()) + hsAmount);
			}
			//已打税未出库期初单
			if(isCancelBefore){
				if(typeCode.equals("6007")){
					item.setF7(CommonUtils.getDoubleExceptNull(item.getF7()) + hsAmount);
					System.out.println("wss 6007已打税未出库期初单hsAmount ：" + hsAmount);
				}
			}
			if(typeCode.equals("6007")){
				item.setF6(CommonUtils.getDoubleExceptNull(item.getF6()) + hsAmount);
				System.out.println("wss 6007已打税未出库期初单hsAmount ：" + hsAmount);
			}
			
		}

		Iterator<LeftoverMaterielStatInfo> iterator = resultHash.values()
				.iterator();
		while (iterator.hasNext()) {
			LeftoverMaterielStatInfo item = iterator.next();
			
//			String hsName = this.filterLeftoverName(item.getHsName());
			
			String hsName = item.getHsName();
			String hsSpec = item.getHsSpec();
			String hsUnitName = item.getHsUnitName();
			
			//key
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if(isCancelBefore){
				//成品出库折损耗
				item.setF4(CommonUtils.getDoubleExceptNull(item.getF4())
					         + CommonUtils.getDoubleExceptNull(parameterMaps.customsProductMapWaste.get(key)));
			}
			//已打税
			item.setF5(CommonUtils.getDoubleExceptNull(item.getF5())
			         + CommonUtils.getDoubleExceptNull(parameterMaps.alReadyTax.get(key)));

			resultList.add(item);
		}
		return resultList;
		
	}
	
	/**
	 * 过滤是 "废","边角料" 报关大类名字
	 * 
	 * @param hsName 报关名称
	 * @return 根据报关名称过滤边角料名称
	 */
	private String filterLeftoverName(String hsName) {
		for (int j = 0; j < filterStrs.length; j++) {
			String repalceStr = filterStrs[j];
			hsName = hsName.replaceFirst(repalceStr, "").trim();
		}
		return hsName;
	}
	
}
