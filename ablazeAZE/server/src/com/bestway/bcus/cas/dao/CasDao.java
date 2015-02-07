/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BillFixingBase;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.CheckDetail;
import com.bestway.bcus.cas.entity.CheckMaster;
import com.bestway.bcus.cas.entity.DebtDetail;
import com.bestway.bcus.cas.entity.DebtMaster;
import com.bestway.bcus.cas.entity.ExgExportInfoBase;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.ImgOrgUseInfoBase;
import com.bestway.bcus.cas.entity.MarginDetail;
import com.bestway.bcus.cas.entity.MarginMaster;
import com.bestway.bcus.cas.entity.MoneyDetail;
import com.bestway.bcus.cas.entity.MoneyMaster;
import com.bestway.bcus.cas.entity.MonthStorage;
import com.bestway.bcus.cas.entity.MonthStorage2;
import com.bestway.bcus.cas.entity.StatCusNameRelation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.entity.TempObjectId;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.invoice.entity.TempEmsImg;
import com.bestway.bcus.cas.invoice.entity.TempSumInvoiceInfo;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.MakeImpExpRequestBill;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.common.BaseDao;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.ConsignQueryCondition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SBillType;
import com.bestway.common.dataimport.entity.ImportDataOrder;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.customs.common.entity.BaseEmsHead;

/**
 * 海关张DAO类
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class CasDao extends BaseDao {
	/**
	 * 显示单据类型
	 * 
	 * @param billCategory
	 *            单据类型
	 * @return list 返回单据类型
	 */

	public List findBillType(int billCategory) {
		if (billCategory == SBillType.HALF_PRODUCE_INOUT) {
			return this
					.find("select a from BillType a where (a.billType= ? or a.billType=?)",
							new Object[] { SBillType.HALF_PRODUCE_IN,
									SBillType.HALF_PRODUCE_OUT });
		} else if (billCategory == SBillType.REMAIN_PRODUCE_INOUT) {
			return this
					.find("select a from BillType a where (a.billType= ? or a.billType=?)",
							new Object[] { SBillType.REMAIN_PRODUCE_IN,
									SBillType.REMAIN_PRODUCE_OUT });
		} else if (billCategory == SBillType.LEFTOVER_MATERIEL_INOUT) {
			return this
					.find("select a from BillType a where (a.billType= ? or a.billType=?)",
							new Object[] { SBillType.LEFTOVER_MATERIEL_IN,
									SBillType.LEFTOVER_MATERIEL_OUT });
		} else if (billCategory == SBillType.MATERIEL_INOUT) {
			// wss2010.10.26不显示1020国内购买期初单;lxr2011.08.17何文欲的单放开1020
			return this
					.find("select a from BillType a where ( a.billType= ? or a.billType= ? )",
					// + " and a.code != ? ",
							new Object[] { SBillType.MATERIEL_IN,
									SBillType.MATERIEL_OUT });// ,"1020"
		} else if (billCategory == SBillType.PRODUCE_INOUT) {
			return this
					.find("select a from BillType a where (a.billType= ? or a.billType=?)",
							new Object[] { SBillType.PRODUCE_IN,
									SBillType.PRODUCE_OUT });
		} else if (billCategory == SBillType.FIXTURE_INOUT) {
			return this
					.find("select a from BillType a where (a.billType= ? or a.billType=?)",
							new Object[] { SBillType.FIXTURE_IN,
									SBillType.FIXTURE_OUT });
		} else {
			System.out.print("----billCategory----" + billCategory + " \n");
			// wss2010.10.26不显示1020国内购买期初单;lxr2011.08.17何文欲的单放开1020
			return this.find("select a from BillType a where  a.billType= ?",
			// + " and a.code != ? ",//wss2010.10.26不显示1020国内购买期初单
					new Object[] { billCategory });// ,"1020"
		}
	}

	public List findBillTypeByMaterielType(String materielType) {
		if (materielType.equals(MaterielType.MATERIEL)) {
			return this
					.find("select a from BillType a where  a.produceType = ? or (code = '5101') ", // 残次品出库
							// 5101",
							new Object[] { Integer.valueOf(materielType) });
		}
		return this.find("select a from BillType a where  a.produceType = ?",
				new Object[] { Integer.valueOf(materielType) });
	}

	public List findBillType() {
		return this.find("select a from BillType a");
	}

	/**
	 * 显示单据物料类型
	 * 
	 * @param produceType
	 *            物料类型
	 * @return 根据参数类型返回相对应的单据类型
	 */

	public List findBillMaterielType(String produceType) {
		if (produceType.equals(MaterielType.MATERIEL)) {
			return this
					.find("select a from BillType a where  ( a.produceType=?  and code not in ('1015','1016','2011','2012'))"
							+ " or (code = '5101') ", // 残次品出库 5101
							new Object[] { Integer.valueOf(produceType) });
		}
		return this
				.find("select a from BillType a where a.produceType=?  and code not in ('1015','1016','2011','2012')",
						new Object[] { Integer.valueOf(produceType) });
	}

	/**
	 * 保存单据类型
	 * 
	 * @param billType
	 *            单据类型
	 */
	public void saveBillType(BillType billType) {
		this.saveOrUpdate(billType);
	}

	/**
	 * 删除单据类型
	 * 
	 * @param billType
	 *            单据类型
	 */
	public void deleteBillType(BillType billType) {
		this.delete(billType);
	}

	/**
	 * 保存单据资料明细表
	 * 
	 * @param billDetail
	 *            单据明细
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveBillDetail(BillDetail billDetail)
			throws DataAccessException {
		this.saveOrUpdate(billDetail);
	}

	/**
	 * 判断单据是否被引用
	 * 
	 * @param billId
	 *            单据ID
	 * @return
	 * @author sxk
	 */
	public List findOwpBillAndBillDetail(String billId) {
		return this.find(
				"select a from OwpBillAndBillDetail a where a.company.id=? "
						+ " and a.bill=? ", new Object[] {
						CommonUtils.getCompany().getId(), billId });
	}

	/**
	 * 查询该单据表头下的单据表体
	 * 
	 * @param billMaster
	 *            单据表头
	 * @return
	 * @author sxk
	 */
	public List findBillDetailByMasterId(BillMaster billMaster) {
		int billType = billMaster.getBillType().getBillType();
		String tableName = BillUtil.getBillDetailTableNameByBillType(billType);

		String hsql = " select b from " + tableName
				+ " b where b.billMaster.company.id = ? "
				+ "and  b.billMaster.id = ? ";
		List parametersList = new ArrayList();
		parametersList.add(CommonUtils.getCompany().getId());
		parametersList.add(billMaster.getId());

		return this.find(hsql, parametersList.toArray());
	}

	/**
	 * 保存单据资料明细表
	 * 
	 * @param billDetailList
	 *            单据明细
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveBillDetail(List<BillDetail> billDetailList)
			throws DataAccessException {
		for (BillDetail bd : billDetailList) {
			this.saveOrUpdate(bd);
		}
	}

	/**
	 * 删除单据明细表
	 * 
	 * @param billDetail
	 *            单据明细
	 */
	public void deleteBillDetail(BillDetail billDetail) {
		this.delete(billDetail);
	}

	/**
	 * 删除全部单据明细表
	 * 
	 * @param listBillDetail
	 *            单据明细资料
	 */
	public void deleteBillDetail(List<BillDetail> listBillDetail) {
		this.deleteAll(listBillDetail);
	}

	/**
	 * 检查是否重复
	 * 
	 * @param code
	 *            单据类型代码
	 * @return 单据类型匹配的单据类型代码
	 */
	public BillType findBillTypeByCode(String code) {
		List result = this.find("select a from BillType a where a.code=?",
				new Object[] { code });
		if (result.size() > 0)
			return (BillType) result.get(0);
		else
			return null;
	}

	public List findBillTypeByFactoryName(String factoryName) {
		if (factoryName == null || factoryName.trim().equals("")) {
			return new ArrayList();
		}
		List result = this.find(
				"select a from BillType a where a.factoryBillName=?  ",
				new Object[] { factoryName });

		return result;
	}

	/**
	 * 单据类型名称 与 单据类型代码 关联
	 */
	public Hashtable findCodeAndFactryNameByName(String typename) {
		Hashtable map = new Hashtable();
		Integer type = null;
		Integer type1 = null;
		List parametersList = new ArrayList();

		String hsql = "select a from BillType a  where 1=1 ";
		if (typename != null && typename.equals("料件")) {
			type = SBillType.MATERIEL_IN;
			type1 = SBillType.MATERIEL_OUT;
			hsql += " and a.billType in(?,?) ";
			parametersList.add(type);
			parametersList.add(type1);
		} else if (typename != null && typename.equals("成品")) {
			type = SBillType.PRODUCE_IN;
			type1 = SBillType.PRODUCE_OUT;
			hsql += " and a.billType in(?,?) ";
			parametersList.add(type);
			parametersList.add(type1);
		} else if (typename != null && typename.equals("设备")) {
			type = SBillType.FIXTURE_IN;
			type1 = SBillType.FIXTURE_OUT;
			hsql += " and a.billType in(?,?) ";
			parametersList.add(type);
			parametersList.add(type1);
		} else if (typename != null && typename.equals("半成品")) {
			type = SBillType.HALF_PRODUCE_IN;
			type1 = SBillType.HALF_PRODUCE_OUT;
			hsql += " and a.billType in(?,?) ";
			parametersList.add(type);
			parametersList.add(type1);
		}
		// else if (typename != null && typename.equals("残次品(料件)")) {
		// type = SBillType.REMAIN_PRODUCE_IN;
		// hsql+=" and a.billType in(?) ";
		// parametersList.add(type);
		// } else if (typename != null && typename.equals("残次品(成品)")) {
		// type = SBillType.REMAIN_PRODUCE_OUT;
		// hsql+=" and a.billType in(?) ";
		// parametersList.add(type);
		// } else if (typename != null && typename.equals("残次品(半成品)")) {
		// type = SBillType.REMAIN_PRODUCE_IN;
		// type1 = SBillType.REMAIN_PRODUCE_OUT;
		// hsql+=" and a.billType in(?,?) ";
		// parametersList.add(type);
		// parametersList.add(type1);
		// }
		else if (typename != null && typename.equals("边角料")) {
			type = SBillType.LEFTOVER_MATERIEL_IN;
			type1 = SBillType.LEFTOVER_MATERIEL_OUT;
			hsql += " and a.billType in(?,?) ";
			parametersList.add(type);
			parametersList.add(type1);
		}
		List<BillType> result = this.find(hsql, parametersList.toArray());
		for (int i = 0; i < result.size(); i++) {
			BillType billType = (BillType) result.get(i);
			String factoryBillName = billType.getFactoryBillName();
			if (factoryBillName == null || "".equals(factoryBillName.trim())) {
				factoryBillName = billType.getName();
			}
			String key = factoryBillName;
			if (key == null) {
				continue;
			}
			map.put(key, billType.getCode());
		}
		return map;
	}

	/**
	 * 从单据类型表里获取物料代码和物料的工厂名称
	 * 
	 * @return 用物料代码做key对应物料工厂名称的hashtable
	 */
	public Hashtable findCodeAndFactryNameFromBillType() {
		Hashtable map = new Hashtable();
		List<BillType> result = this.find("select a from BillType a  ");
		for (int i = 0; i < result.size(); i++) {
			BillType billType = (BillType) result.get(i);
			String factoryBillName = billType.getFactoryBillName();
			if (factoryBillName == null || "".equals(factoryBillName.trim())) {
				factoryBillName = billType.getName();
			}
			String key = factoryBillName;
			if (key == null) {
				continue;
			}
			map.put(key, billType.getCode());
		}
		return map;
	}

	public List findComplex() {
		String hsql = "select a from Complex a";
		return this.find(hsql);
	}

	/**
	 * 得到单据号
	 * 
	 * @param sBillType
	 *            单据类型
	 * @return 生效日期+序列号
	 */
	public String getBillNo(int sBillType) {
		String yearmonthday = "";
		String serialNo = "";
		Calendar calendar = Calendar.getInstance();
		yearmonthday = CommonUtils.getYear();
		yearmonthday += organizeStr(calendar.get(Calendar.MONTH) + 1, 2);
		yearmonthday += organizeStr(calendar.get(Calendar.DAY_OF_MONTH), 2);

		String tableName = BillUtil
				.getBillMasterTableNameByBillType2(sBillType);
		if (tableName == null) {
			return null;
		}
		String hsql = " select max(a.billNo) from " + tableName
				+ " as a  where a.billNo like ? and a.company= ? ";

		List list = this.find(hsql, new Object[] { yearmonthday + "%",
				CommonUtils.getCompany() });
		if (list.size() < 1) {
			serialNo = "0001";
		} else if (list.get(0) == null) {
			serialNo = "0001";
		} else {
			String temp = list.get(0).toString();
			if (temp.trim().equals("")) {
				serialNo = "0001";
			} else {
				int n = Integer.parseInt(temp.substring(temp.length() - 4,
						temp.length())) + 1;
				serialNo = organizeStr(n, 4);
			}
		}
		return yearmonthday + serialNo;
	}

	/**
	 * 保存修改企业物料(不反算)
	 * 
	 * @param request
	 * @param ffc
	 */
	public void saveFactoryAndFactualCustomsRalation(Boolean isWriteBack,
			FactoryAndFactualCustomsRalation ffc) {
		if (isWriteBack) {
			this.saveOrUpdate(ffc.getMateriel());
		}

		this.saveOrUpdate(ffc);
	}

	/**
	 * 增加、修改对应关系(反算工厂物料主档和单据)
	 * 
	 * @param request
	 * @param ffc
	 *            (wss2010.06.21整改)
	 */
	public void saveFactoryAndFactualCustomsRalation(
			FactoryAndFactualCustomsRalation ffc, String materielType,
			Boolean isUpdateMateriel, Boolean isUpdateHsNum, String oldEmsNo) {

		// 如果是修改了物料资料,须反写工厂物料
		if (isUpdateMateriel) {
			// 工厂物料
			List<EnterpriseMaterial> enterpriseList = findEnterpriseMaterialByMaterielId(ffc
					.getMateriel());

			System.out.println(" wss enterpriseList.size()="
					+ enterpriseList.size());

			for (EnterpriseMaterial enterpriseMaterial : enterpriseList) {
				enterpriseMaterial.setPtPrice(ffc.getMateriel().getPtPrice());
				enterpriseMaterial.setCalUnit(ffc.getMateriel().getCalUnit());
				enterpriseMaterial.setPtNetWeight(ffc.getMateriel()
						.getPtNetWeight());

				// 反写工厂物料
				this.saveOrUpdate(enterpriseMaterial);
			}
		}

		// 单据体表名称
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		// System.out.println("billDetailTableName=" + billDetailTableName);

		// String hsql = "select a from "
		// + billDetailTableName
		// + " as a "
		// +
		// "  where a.ptPart = ? and a.company= ? and a.hsName=? and a.hsSpec=?";
		// List<BillDetail> billDetailList = this.find(hsql, new Object[] {
		// enterpriseMaterial.getPtNo(),
		// CommonUtils.getCompany(),
		// ffc.getStatCusNameRelationHsn() == null ? "" : ffc
		// .getStatCusNameRelationHsn().getCusName(),
		// ffc.getStatCusNameRelationHsn() == null ? "" : ffc
		// .getStatCusNameRelationHsn().getCusSpec() });
		//
		// wss:2010.04.26修改（新改的）
		String hsql = "select a from "
				+ billDetailTableName
				+ " as a "
				+ "  where a.ptPart = ? and a.company= ? and a.ptName=? and a.ptSpec=? "
				+ " and a.emsNo=? ";// wss2010.06.18加上手册号
		List<BillDetail> billDetailList = this.find(hsql, new Object[] {
				ffc.getMateriel().getPtNo(), CommonUtils.getCompany(),
				ffc.getMateriel().getFactoryName(),
				ffc.getMateriel().getFactorySpec(), oldEmsNo });

		System.out.println("wss:  billDetailList.size()="
				+ billDetailList.size());

		for (BillDetail billDetail : billDetailList) {
			billDetail.setPtPrice(ffc.getMateriel().getPtPrice());
			billDetail.setPtUnit(ffc.getMateriel().getCalUnit());
			if (billDetail.getPtAmount() == null) {
				billDetail.setPtAmount(new Double("0.0"));
			}
			billDetail
					.setNetWeight(CommonUtils.getDoubleExceptNull(ffc
							.getMateriel().getPtNetWeight())
							* CommonUtils.getDoubleExceptNull(billDetail
									.getPtAmount()));
			billDetail
					.setHsPrice(CommonUtils.getDoubleExceptNull(billDetail
							.getPtPrice())
							/ ((ffc.getUnitConvert() == null || ffc
									.getUnitConvert() == 0) ? 1 : ffc
									.getUnitConvert()));
			if (billDetail.getPtPrice() == null) {
				billDetail.setPtPrice(new Double("0.0"));
			}

			if (billDetail.getPtAmount() == null) {
				billDetail.setPtAmount(new Double("0.0"));
			}

			// 更新单据中的折算报关数量
			if (isUpdateHsNum) {
				Double money = 0.0;
				Double amount = 0.0;
				money = Double.parseDouble(billDetail.getPtPrice().toString())
						* Double.parseDouble(billDetail.getPtAmount()
								.toString());
				billDetail.setUnitConvert(CommonUtils.getDoubleExceptNull(ffc
						.getUnitConvert()));
				billDetail.setMoney(money);
				billDetail
						.setHsAmount(CommonUtils.getDoubleExceptNull(billDetail
								.getPtAmount())
								* CommonUtils.getDoubleExceptNull(ffc
										.getUnitConvert()));
			}

			// 否则是更新海关资料

			if (ffc.getStatCusNameRelationHsn() != null) {
				StatCusNameRelationHsn hsInfo = ffc.getStatCusNameRelationHsn();
				billDetail.setComplex(hsInfo.getComplex());
				billDetail.setHsName(hsInfo.getCusName());
				billDetail.setHsSpec(hsInfo.getCusSpec());
				billDetail.setHsUnit(hsInfo.getCusUnit());
			}

			this.saveOrUpdate(billDetail);
		}

		this.saveOrUpdate(ffc.getMateriel());

		this.saveOrUpdate(ffc);
	}

	/**
	 * 通过报关物料查找工厂物料
	 * 
	 * @param materiel
	 * @return
	 */
	public List findEnterpriseMaterialByMaterielId(Materiel materiel) {
		List list = this
				.find("select a from EnterpriseMaterial a where a.ptNo=? and a.company.id =?",
						new Object[] { materiel.getPtNo(),
								CommonUtils.getCompany().getId() });
		return list;

	}

	/**
	 * 把整型转换为字符型
	 * 
	 * @param m
	 *            数值
	 * @param length
	 *            长度
	 * @return s 字符型
	 */
	private String organizeStr(int m, int length) {
		String s = String.valueOf(m);
		int n = length - s.length();
		for (int i = 0; i < n; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 获得分页 List 来自带多个?来代替名字参数的 hsql 语句
	 * 
	 * @param hsql
	 *            hibernate查询语句
	 * @param objParams
	 *            实体类参数
	 * @return 获得分页 List 来自带多个?来代替名字参数的 hsql 语句
	 */
	public List commonSearch(String hsql, Object[] objParams) {
		return super.find(hsql, objParams);
	}

	/**
	 * 组织HQL报表条件公共查询
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @param leftOuter
	 *            左连接内容
	 * @param orderBy
	 *            排序规则
	 * @return 查询结果a
	 */
	public List commonSearch(String selects, String className, List conditions,
			String groupBy, String leftOuter, String orderBy) {
		if (leftOuter == null) {
			leftOuter = "";
		}

		String sql = "";
		sql = " from " + className + " a " + leftOuter + " where a.company =? ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
			System.out.println("then 1 this is sql=" + sql);
		} else {// selects == null
			sql = " select a from " + className + " a " + leftOuter
					+ " where a.company =? ";
			System.out.println("then 2 this is sql=" + sql);
		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += " " + condition.getOperator() + " ";
				}
				if (condition.getValue() != null) {
					sql += "? ";
					params.add(condition.getValue());
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
		if (orderBy != null && !orderBy.equals("")) {
			sql = sql + " " + orderBy;
		}
		System.out.println("=====search sql:" + sql);
		return this.find(sql, params.toArray());
	}

	public List findBillDetailByBillTypeAndMaterielType(String proNo,
			String billDetail, Date begin, Date end, ScmCoc scmcoc) {
		List para = new ArrayList();
		String hsql = "  select a from  " + billDetail
				+ " a  left join a.billMaster b  where a.company.id  =? ";
		para.add(CommonUtils.getCompany().getId());
		if (begin != null) {
			hsql += " and b.validDate >= ?   ";
			para.add(begin);
		}
		if (end != null) {
			hsql += " and b.validDate <= ?   ";
			para.add(end);
		}
		if (proNo != null && (!proNo.equals(""))) {
			hsql += " and a.productNo = ?   ";
			para.add(proNo);
		}
		if (scmcoc != null) {
			hsql += " and b.scmCoc.id = ? ";
			para.add(scmcoc.getId());
		}
		hsql += " and b.isValid = ? ";
		para.add(true);
		return this.find(hsql, para.toArray());
	}

	/**
	 * 组织HQL报表条件公共查询,(这个好的东西也要学习.要引用.罗盛)
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @param leftOuter
	 *            左连接内容
	 * @return 查询结果
	 */
	public List commonSearchPageList(int index, int length, String selects,
			String className, List conditions, String groupBy, String leftOuter) {
		if (leftOuter == null) {
			leftOuter = "";
		}
		String sql = "";
		sql = " from " + className + " a " + leftOuter + " where a.company =? ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null
			sql = " select a from " + className + " a " + leftOuter
					+ " where a.company =? ";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								sql += "?";
							} else {
								sql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						sql += "? ";
						params.add(condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
		System.out.println("---------------sql:" + sql);
		List result = this.findPageList(sql, params.toArray(), index, length);
		return result;
	}

	/**
	 * 统计公共查询的笔数
	 * 
	 * @param selects
	 * @param className
	 * @param conditions
	 * @param groupBy
	 * @param leftOuter
	 * @return
	 */
	public int countCommonSearchPage(String selects, String className,
			List conditions, String groupBy, String leftOuter) {
		if (leftOuter == null) {
			leftOuter = "";
		}
		String sql = "";
		sql = " from " + className + " a " + leftOuter
				+ " where a.company.id =? ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {
			sql = " select count(a.id) from " + className + " a " + leftOuter
					+ " where a.company.id =? ";
		}
		// hwy2012-10-31

		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany().getId());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								sql += "?";
							} else {
								sql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						sql += "? ";
						params.add(condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		// if (groupBy != null && !groupBy.equals("")) {
		// sql = sql + " " + groupBy;
		// }
		List list = this.find(sql, params.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 组织HQL报表条件公共查询,(这个好的东西也要学习.要引用.罗盛)
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @param leftOuter
	 *            左连接内容
	 * @return 查询结果
	 */
	public List commonSearch(String selects, String className, List conditions,
			String groupBy, String leftOuter) {
		if (leftOuter == null) {
			leftOuter = "";
		}
		String sql = "";
		sql = " from " + className + " a " + leftOuter + " where a.company =? ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null
			sql = " select a from " + className + " a " + leftOuter
					+ " where a.company =? ";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								sql += "?";
							} else {
								sql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						sql += "? ";
						params.add(condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
		System.out.println("---------------sql:" + sql);
		List result = this.find(sql, params.toArray());
		return result;
	}

	/**
	 * 根据开始日期和结束日期查询所有转厂单据
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public List findBillDetailByNameSpecUnit(Date begin, Date end) {
		StringBuilder hsql = new StringBuilder();
		hsql.append(" select")
				.append(" sum(m.amount), a.hsName, a.hsSpec, a.hsUnit.name, a.billMaster.scmCoc.name,"
						+ " a.billMaster.billType.code, a.version, m.emsHeadH2k")
				.append(" from")
				.append(" BillDetailProduct a ,MakeBillCorrespondingInfoByProduct m ")
				.append(" where")
				.append(" a.id = m.billDetailId and a.company.id = ? and a.billMaster.isValid = true"
						+ " and a.hsName is not null and a.billMaster.billType.code in ('2009','2102','2011','2012')"
						+ " and a.billMaster.validDate >= ? and a.billMaster.validDate <= ?")
				.append(" group by")
				.append(" a.hsName,a.hsSpec, a.hsUnit.name, a.billMaster.billType.code,"
						+ " a.billMaster.scmCoc.name, a.version, m.emsHeadH2k");

		return this.find(hsql.toString(), new Object[] {
				CommonUtils.getCompany().getId(), begin, end });
	}

	/**
	 * 没有公司的HQL报表条件公共查询
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @return 查询结果
	 */
	public List noCompanyConditionCommonSearch(String selects,
			String className, List conditions, String groupBy) {
		String sql = "";
		sql = " from " + className + " a where 1 = 1 ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {
			sql = "select a  from " + className + " a where 1 = 1 ";
		}
		List<Object> params = new ArrayList<Object>();
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null)
					sql += " " + condition.getLogic() + "  ";
				if (condition.getBeginBracket() != null)
					sql += condition.getBeginBracket();
				if (condition.getFieldName() != null)
					sql += "a." + condition.getFieldName();
				if (condition.getValue() != null)
					sql += condition.getOperator();
				sql += "? ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
				params.add(condition.getValue());
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}

		List result = this.find(sql, params.toArray());

		return result;
	}

	/**
	 * 组织公共查询
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @return 查询结果
	 */
	public List noWhereCommonSearch(String selects, String className,
			List conditions, String groupBy) {
		String sql = " select a from " + className + " a ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		}
		List<Object> params = new ArrayList<Object>();
		// params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null)
					sql += " " + condition.getLogic() + "  ";
				if (condition.getBeginBracket() != null)
					sql += condition.getBeginBracket();
				if (condition.getFieldName() != null)
					sql += "a." + condition.getFieldName();
				if (condition.getValue() != null)
					sql += condition.getOperator();
				sql += "? ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
				params.add(condition.getValue());
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}

		List result = this.find(sql, params.toArray());

		return result;
	}

	/**
	 * 显示临时表
	 * 
	 * @return 临时报表
	 */
	public List findBillTemp() {
		return this.find("select a from BillTemp a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 取得所有加工贸易生产设备
	 * 
	 * @return 生产设备
	 */
	public List findBillFixingThing() {
		return this.find("select a from BillFixingThing a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 取得所有加工贸易生产设备
	 * 
	 * @return 生产设备
	 */
	public List findBillFixing() {
		return this.find("select a from BillFixing a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 删除当前公司的临时单据
	 */
	public void deleteBillTemp() {
		this.deleteAll(findBillTemp());
	}

	/**
	 * 保存临时表
	 * 
	 * @param billTemp
	 *            临时报表
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveBillTemp(BillTemp billTemp) throws DataAccessException {
		this.saveOrUpdate(billTemp);

	}

	/**
	 * 保存设备明细
	 * 
	 * @param bill
	 *            设备明细
	 */
	public void saveBillFixingBase(BillFixingBase bill) {
		this.saveOrUpdate(bill);
	}

	/**
	 * 删除所有设备明细
	 * 
	 * @param bill
	 *            设备明细
	 */
	public void deleteBillFixingBase(List<BillFixingBase> bill) {
		this.deleteAll(bill);
	}

	/**
	 * 返回盘点表头
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 返回所有没有作废的盘点表头
	 */
	public List findCheckMaster(String materielType) {
		return this
				.find("select a from CheckMaster a where a.company= ? and a.masterielType = ? and a.isDelete=?",
						new Object[] { CommonUtils.getCompany(),
								Integer.valueOf(materielType),
								new Boolean(false) });
	}

	/**
	 * 保存盘点表头
	 * 
	 * @param checkMaster
	 *            盘点表表头
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveCheckMaster(CheckMaster checkMaster)
			throws DataAccessException {
		this.saveOrUpdate(checkMaster);
	}

	/**
	 * 取得所有序号
	 * 
	 * @param className
	 *            类名
	 * @return 返回序号 从1开始
	 */
	public String getNum(String className) {
		String num = "1";
		List list = this.find("select max(a.num) from " + className + " as a "
				+ " where a.company= ?",
				new Object[] { CommonUtils.getCompany() });
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 返回盘点表体
	 * 
	 * @param checkMaster
	 *            盘点表头
	 * @return 返回盘点表体
	 */
	public List findCheckDetail(CheckMaster checkMaster) {
		return this
				.find("select a from CheckDetail a where a.company= ? and a.checkMaster = ?",
						new Object[] { CommonUtils.getCompany(), checkMaster });
	}

	/**
	 * 保存盘点表体
	 * 
	 * @param checkDetail
	 *            盘点表体
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveCheckDetail(CheckDetail checkDetail)
			throws DataAccessException {
		this.saveOrUpdate(checkDetail);
	}

	/**
	 * 删除盘点表明细
	 * 
	 * @param checkMaster
	 *            盘点表表头
	 */
	public void deleteCheckDetail(CheckMaster checkMaster) {
		this.deleteAll(findCheckDetail(checkMaster));
	}

	/**
	 * 返回现金流量表头
	 * 
	 * @return 没有作废的现金流量表头
	 */
	public List findMoneyMaster() {
		return this
				.find("select a from MoneyMaster a where a.company= ? and a.isDelete=?",
						new Object[] { CommonUtils.getCompany(),
								new Boolean(false) });
	}

	/**
	 * 保存现金流量表头
	 * 
	 * @param moneyMaster
	 *            现金流量表表头
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveMoneyMaster(MoneyMaster moneyMaster)
			throws DataAccessException {
		this.saveOrUpdate(moneyMaster);
	}

	/**
	 * 返回资产负债表头
	 * 
	 * @return 没有作废的资产负债表表头
	 */
	public List findDebtMaster() {
		return this
				.find("select a from DebtMaster a where a.company= ? and a.isDelete=?",
						new Object[] { CommonUtils.getCompany(),
								new Boolean(false) });
	}

	/**
	 * 保存资产负债表头
	 * 
	 * @param debtMaster
	 *            资产负债表表头
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveDebtMaster(DebtMaster debtMaster)
			throws DataAccessException {
		this.saveOrUpdate(debtMaster);
	}

	/**
	 * 返回利润表头
	 * 
	 * @return 没有作废的利润表表头
	 */
	public List findMarginMaster() {
		return this
				.find("select a from MarginMaster a where a.company= ? and a.isDelete=?",
						new Object[] { CommonUtils.getCompany(),
								new Boolean(false) });
	}

	/**
	 * 保存利润表头
	 * 
	 * @param marginMaster
	 *            利润表表头
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveMarginMaster(MarginMaster marginMaster)
			throws DataAccessException {
		this.saveOrUpdate(marginMaster);
	}

	/**
	 * 返回现金流量表体
	 * 
	 * @param moneyMaster
	 *            现金流量表表头
	 * @return 与表头内容相匹配的现金流量表表体
	 */
	public List findMoneyDetail(MoneyMaster moneyMaster) {
		return this
				.find("select a from MoneyDetail a where a.company= ? and a.moneyMaster = ?",
						new Object[] { CommonUtils.getCompany(), moneyMaster });
	}

	/**
	 * 返回资产负债表体
	 * 
	 * @param debtMaster
	 *            资产负债表表头
	 * @return 与资产负债表表头对应的表体
	 */
	public List findDebtDetail(DebtMaster debtMaster) {
		return this
				.find("select a from DebtDetail a where a.company= ? and a.debtMaster = ?",
						new Object[] { CommonUtils.getCompany(), debtMaster });
	}

	/**
	 * 返回利润表体
	 * 
	 * @param marginMaster
	 *            利润表表头
	 * @return 与利润表表头对应的表体
	 */
	public List findMarginDetail(MarginMaster marginMaster) {
		return this
				.find("select a from MarginDetail a where a.company= ? and a.marginMaster = ?",
						new Object[] { CommonUtils.getCompany(), marginMaster });
	}

	/**
	 * 保存现金流量表体
	 * 
	 * @param moneyDetail
	 *            现金流量表表体
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveMoneyDetail(MoneyDetail moneyDetail)
			throws DataAccessException {
		this.saveOrUpdate(moneyDetail);
	}

	/**
	 * 保存资产负债表体
	 * 
	 * @param debtDetail
	 *            资产负债表表体
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveDebtDetail(DebtDetail debtDetail)
			throws DataAccessException {
		this.saveOrUpdate(debtDetail);
	}

	/**
	 * 保存利润表体
	 * 
	 * @param marginDetail
	 *            利润表表体
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveMarginDetail(MarginDetail marginDetail)
			throws DataAccessException {
		this.saveOrUpdate(marginDetail);
	}

	/**
	 * 根据行次返回一行(现金流量)
	 * 
	 * @param moneyMaster
	 *            现金流量表表头
	 * @param times
	 *            行次
	 * @return 与表头和行次相对应的现金流量
	 */
	public List findMoneyByTimes(MoneyMaster moneyMaster, String times) {
		String tiemFlat = "";
		String moneyFlat = "";
		if (Integer.parseInt(times) <= 24) {
			tiemFlat = "times1";
			moneyFlat = "money1";
		} else if (Integer.parseInt(times) <= 43
				&& Integer.parseInt(times) >= 25) {
			tiemFlat = "times2";
			moneyFlat = "money2";
		} else if (Integer.parseInt(times) >= 44) {
			tiemFlat = "times3";
			moneyFlat = "money3";
		}
		return this
				.find("select a."
						+ moneyFlat
						+ " from MoneyDetail a where a.company= ? and a.moneyMaster = ? and a."
						+ tiemFlat + "=?",
						new Object[] { CommonUtils.getCompany(), moneyMaster,
								times });
	}

	/**
	 * 根据行次返回一行（对象）
	 * 
	 * @param moneyMaster
	 *            现金流量表头
	 * @param times
	 *            行次
	 * @return 与表头,行次对应的现金流量
	 */
	public List findMoneyByTimes2(MoneyMaster moneyMaster, String times) {
		String tiemFlat = "";
		if (Integer.parseInt(times) <= 24) {
			tiemFlat = "times1";
		} else if (Integer.parseInt(times) <= 43
				&& Integer.parseInt(times) >= 25) {
			tiemFlat = "times2";
		} else if (Integer.parseInt(times) >= 44) {
			tiemFlat = "times3";
		}
		return this.find(
				"select a from MoneyDetail a where a.company= ? and a.moneyMaster = ? and a."
						+ tiemFlat + "=?",
				new Object[] { CommonUtils.getCompany(), moneyMaster, times });
	}

	/**
	 * 根据行次返回一行(利润)
	 * 
	 * @param marginMaster
	 *            利润表头
	 * @param times
	 *            行次
	 * @return 与利润表,行次对应的本期数
	 */
	public List findMarginByTimes(MarginMaster marginMaster, String times) {
		return this
				.find("select a.thisCount from MarginDetail a where a.company= ? and a.marginMaster = ? and a.times=?",
						new Object[] { CommonUtils.getCompany(), marginMaster,
								times });
	}

	public List findMarginByTimes2(MarginMaster marginMaster, String times) {
		return this
				.find("select a from MarginDetail a where a.company= ? and a.marginMaster = ? and a.times=?",
						new Object[] { CommonUtils.getCompany(), marginMaster,
								times });
	}

	/**
	 * 根据行次返回一行(资产负债)
	 * 
	 * @param debtMaster
	 *            资产负债表头
	 * @param times
	 *            行次
	 * @param beginEnd
	 *            期初或期末数
	 * @return 与行次, 期初或末数相匹配的资产负债表
	 */
	public List findDebtByTimes(DebtMaster debtMaster, String times,
			String beginEnd) {
		String tiemFlat = "";
		String debtFlat = "";
		if (Integer.parseInt(times) <= 41) {
			tiemFlat = "times1";
			if (beginEnd.equals("1")) {
				debtFlat = "beginValue1";
			} else {
				debtFlat = "endValue1";
			}
		} else if (Integer.parseInt(times) >= 42) {
			tiemFlat = "times2";
			if (beginEnd.equals("1")) {
				debtFlat = "beginValue2";
			} else {
				debtFlat = "endValue2";
			}
		}
		return this
				.find("select a."
						+ debtFlat
						+ " from DebtDetail a where a.company= ? and a.debtMaster = ? and a."
						+ tiemFlat + "=?",
						new Object[] { CommonUtils.getCompany(), debtMaster,
								times });
	}

	/**
	 * 根据行次返回一行（对象）
	 * 
	 * @param debtMaster
	 *            资产负债表头
	 * @param times
	 *            行次
	 * @return 与资产负债表头,行次对应的资产负债表数据
	 */
	public List findDebtByTimes2(DebtMaster debtMaster, String times) {
		String tiemFlat = "";
		if (Integer.parseInt(times) <= 41) {
			tiemFlat = "times1";
		} else if (Integer.parseInt(times) >= 42) {
			tiemFlat = "times2";
		}
		return this.find(
				"select a from DebtDetail a where a.company= ? and a.debtMaster = ? and a."
						+ tiemFlat + "=?",
				new Object[] { CommonUtils.getCompany(), debtMaster, times });
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 TFB == transfer factory bill 转厂单据
	 * 
	 * @param scmCocId
	 *            客户id
	 * @param sBillType
	 *            单据类型
	 * @param billCode
	 *            单据类型下的单据代号
	 * @return 不是全部转厂的单据商品商品信息
	 */
	public List findBillMasterIsAvailabilityToTFB(String scmCocId,
			int sBillType, String billCode) {
		String billMaster = BillUtil
				.getBillMasterTableNameByBillType(sBillType);
		if (billMaster == null) {
			return new ArrayList();
		}
		// System.out.println("billMaster------------" + billMaster);
		// System.out.println("scmCocId------------" + scmCocId);
		// System.out.println("billCode------------" + billCode);
		String hsql = "select a from "
				+ billMaster
				+ " a where a.isValid= ? and "
				+ " a.company.id= ? and a.scmCoc.id = ? and a.billType.code = ? "
				+ " and a.id not in ( select b.billMasterId "
				+ " from MakeTransferFactoryBill b "
				+ " where b.company.id=? ) ";
		return this.find(hsql, new Object[] { true,
				CommonUtils.getCompany().getId(), scmCocId, billCode,
				CommonUtils.getCompany().getId() });

	}

	/**
	 * 根据已选商品单据获得其所有未转结转单的商品信息
	 * 
	 * @param list
	 *            临时单据明细
	 * @return 已选商品单据获得其所有未转结转单的商品信息
	 */
	public List findBillDetailByParent(List list) {
		if (list == null || list.isEmpty()) {
			return new ArrayList();
		}
		int billType = ((TempBillMaster) list.get(0)).getBillMaster()
				.getBillType().getBillType();
		String tableName = BillUtil.getBillDetailTableNameByBillType(billType);
		if (tableName == null) {
			return new ArrayList();
		}

		String hsql = " select b from "
				+ tableName
				+ " b where b.billMaster.company.id = ? "
				+ "and  ( b.isTransferFactoryBill = ? or b.isTransferFactoryBill is null )";
		String condition = "";
		List parametersList = new ArrayList();
		parametersList.add(CommonUtils.getCompany().getId());
		parametersList.add(new Boolean(false));
		for (int i = 0; i < list.size(); i++) {
			BillMaster billMaster = ((TempBillMaster) list.get(i))
					.getBillMaster();
			if (i == 0) {
				condition += " b.billMaster.id = ? ";
			} else {
				condition += " or b.billMaster.id = ? ";
			}
			parametersList.add(billMaster.getId());
		}
		if (!condition.equals("")) {
			hsql += " and (" + condition + " )";
		}
		return this.find(hsql, parametersList.toArray());
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 IXRB == Import Exprot Request Bill 进出口申请单
	 * 
	 * @param sBillType
	 *            单据类型
	 * @param billCode
	 *            单据类型下的单据代号
	 * @return 单据中商品信息不是全部转为真的单据记录
	 */
	public List findBillMasterIsAvailabilityToIXRB(int sBillType,
			String billCode) {
		String billMaster = BillUtil
				.getBillMasterTableNameByBillType(sBillType);
		if (billMaster == null) {
			return new ArrayList();
		}
		String billDetail = BillUtil
				.getBillDetailTableNameByBillType(sBillType);
		if (billDetail == null) {
			return new ArrayList();
		}

		String hsql = "select b from "
				+ billMaster
				+ " b left join fetch b.billType a where "
				+ "b.company.id= ? "
				+ " and a.code = ?  "
				+ "and b.id in "
				+ "(select c.id from "
				+ billDetail
				+ " d join d.billMaster c "
				+ "where d.isImpExpRequestBill = ? or d.isImpExpRequestBill is null )";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				billCode, new Boolean(false) });
	}

	/**
	 * 根据已选商品单据获得其所有未转进出口的商品信息 IXRB == Import Exprot Request Bill 进出口申请单
	 * 
	 * @param list
	 *            临时单据表头
	 * @return 已选商品单据获得其所有未转进出口的商品信息
	 */
	public List findBillDetailByParentToIMXP(List list) {

		if (list == null || list.isEmpty()) {
			return new ArrayList();
		}
		int billType = ((TempBillMaster) list.get(0)).getBillMaster()
				.getBillType().getBillType();
		String billDetail = BillUtil.getBillDetailTableNameByBillType(billType);
		if (billDetail == null) {
			return new ArrayList();
		}

		String hsql = "select b  from "
				+ billDetail
				+ " b where b.company.id = ? "
				+ "and  ( b.isImpExpRequestBill = ? or b.isImpExpRequestBill is null )";
		String condition = "";
		List<Object> parametersList = new ArrayList<Object>();
		parametersList.add(CommonUtils.getCompany().getId());
		parametersList.add(new Boolean(false));
		for (int i = 0; i < list.size(); i++) {
			BillMaster billMaster = ((TempBillMaster) list.get(i))
					.getBillMaster();
			if (i == 0) {
				condition += " b.billMaster.id = ? ";
			} else {
				condition += " or b.billMaster.id = ? ";
			}
			parametersList.add(billMaster.getId());
		}
		if (!condition.equals("")) {
			hsql += " and (" + condition + " )";
		}
		return this.find(hsql, parametersList.toArray());
	}

	/**
	 * 有数据已转进出口申请单来自海关账单据头
	 * 
	 * @param b
	 *            单据表头
	 * @return 所有已转进出口申请单
	 */
	public boolean hasDataTransferImpExpRequestBillByBillMaster(BillMaster b) {

		String billDetail = BillUtil.getBillDetailTableNameByBillType(b
				.getBillType().getBillType());
		if (billDetail == null) {
			return false;
		}

		List list = this.find("select count(*) from " + billDetail + " b  "
				+ "where b.company.id = ? and b.isImpExpRequestBill = ? "
				+ "  and b.billMaster.id = ? ",
				new Object[] { CommonUtils.getCompany().getId(),
						new Boolean(true), b.getId() });
		if (list == null || list.size() <= 0) {
			return false;
		}
		if (((Long) list.get(0)).longValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 有数据已转结转单单据来自海关账单据体
	 * 
	 * @param b
	 *            单据头
	 * @return 所有已转结转单据单单据
	 */
	public boolean hasDataTransferTransferFactoryBillByBillMaster(BillMaster b) {
		String billDetail = BillUtil.getBillDetailTableNameByBillType(b
				.getBillType().getBillType());
		if (billDetail == null) {
			return false;
		}

		List list = this.find("select count(*) from " + billDetail + " b  "
				+ "where b.company.id = ? and b.isTransferFactoryBill = ? "
				+ "  and b.billMaster.id = ? ",
				new Object[] { CommonUtils.getCompany().getId(),
						new Boolean(true), b.getId() });
		if (list == null || list.size() <= 0) {
			return false;
		}
		if (((Long) list.get(0)).longValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 显示资料主表 统计报关名称
	 * 
	 * @return 工厂大类内容
	 */
	public List findStatCusNameRelation() {

		return this.find(
				"select a from StatCusNameRelation a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示资料主表 统计报关名称
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 与指定物料类型相对应的资料主表
	 */
	public List findStatCusNameRelation(String materialType) {

		return this.find(
				"select a from StatCusNameRelation a where a.company.id= ? "
						+ " and a.materielType=? ", new Object[] {
						CommonUtils.getCompany().getId(), materialType });
	}

	/**
	 * 显示资料主表 统计报关名称
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 与指定物料类型相对应的资料主表
	 */
	public List<FactoryAndFactualCustomsRalation> findStatCusNameRelation(
			String materialType, ImgOrgUseInfoBase imgOrgUseInfoBase) {
		/*
		 * 查询出以实际报关资料为key的（cusName，cusSpec，cusUnitName） 对应关系中的料号
		 */
		String hsql = "select a.materiel.ptNo from FactoryAndFactualCustomsRalation a where a.company.id= ? "
				+ " and a.statCusNameRelationHsn.materielType=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(materialType);

		String cusName = imgOrgUseInfoBase.getPtName();
		String cusSpec = imgOrgUseInfoBase.getPtSpec();
		String cusUnitName = imgOrgUseInfoBase.getPtUnitName();

		if (cusName != null) {
			hsql += " and a.statCusNameRelationHsn.cusName = ? ";
			parameters.add(cusName);
		} else {
			hsql += " and a.statCusNameRelationHsn.cusName is null ";
		}
		if (cusSpec != null && !"".equalsIgnoreCase(cusSpec)) {
			hsql += " and a.statCusNameRelationHsn.cusSpec = ? ";
			parameters.add(cusSpec);
		} else {
			hsql += " and ( a.statCusNameRelationHsn.cusSpec is null or a.statCusNameRelationHsn.cusSpec = '')  ";
		}

		if (cusUnitName != null && !"".equalsIgnoreCase(cusUnitName)) {
			hsql += " and a.statCusNameRelationHsn.cusUnit.name = ? ";
			parameters.add(cusUnitName);
		} else {
			hsql += " and a.statCusNameRelationHsn.cusUnit is null ";
		}
		List<String> ptNoList = this.find(hsql, parameters.toArray());

		/*
		 * 查询 在料号列表中的 对应关系
		 */

		StringBuilder hql = new StringBuilder(
				"select a from FactoryAndFactualCustomsRalation a "
						+ "left join fetch a.materiel " + "where 1=1 ");
		if (ptNoList != null && ptNoList.size() > 0) {
			hql.append("and a.materiel.ptNo in ('" + ptNoList.get(0));
			for (int i = 1, size = ptNoList.size(); i < size; i++) {
				hql.append("','" + ptNoList.get(i));
			}
			hql.append("') ");
		}

		hql.append("order by a.id");

		List<FactoryAndFactualCustomsRalation> list = this.find(hql.toString());

		return list;
	}

	/**
	 * 保存资料主表 统计报关名称
	 * 
	 * @param statCusNameRelation
	 *            资料主表内容
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveStatCusNameRelation(StatCusNameRelation statCusNameRelation)
			throws DataAccessException {
		this.saveOrUpdate(statCusNameRelation);
	}

	/**
	 * 删除资料主表 统计报关名称
	 * 
	 * @param statCusNameRelation
	 *            资料主表
	 */
	public void deleteStatCusNameRelation(
			StatCusNameRelation statCusNameRelation) {
		this.delete(statCusNameRelation);
	}

	/**
	 * 显示资料副表来自料件类型 工厂料件
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与资料主表对应的按工厂料号排序的工厂料件
	 */
	public List<FactoryAndFactualCustomsRalation> findStatCusNameRelationMt(
			String materielType) {
		return this
				.find("select a from FactoryAndFactualCustomsRalation a left join fetch a.materiel m "
						+ " left join fetch a.statCusNameRelationHsn "
						+ " where a.company.id= ? "
						+ " and a.statCusNameRelationHsn.materielType = ? "
						+ " order by a.id", new Object[] {
						CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 显示实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与主表对应的实际报关商品
	 */
	public List findStatCusNameRelationHsn(String materielType) {
		return this
				.find("select a from StatCusNameRelationHsn a left join fetch a.statCusNameRelation "
						+ " where a.statCusNameRelation.company.id= ? and a.statCusNameRelation.materielType= ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								materielType });
	}

	public List findTempNofromInvoiceMaster(String tempNo) {
		String hsql = "select a from CasInvoice a where a.tempNo = ?";
		return this.find(hsql, tempNo);
	}

	/**
	 * 返回比较KEY，从对应关系报关资料中
	 * 
	 * @param materielType
	 * @return
	 */
	public List findKeyFromStatCusNameRelationHsn(String materielType,
			Boolean isOneToMany) {
		String hsql = "";
		if (isOneToMany) {
			// hsql = "select distinct
			// a.cusName+a.cusSpec+a.cusUnit.name+a.emsNo from
			// StatCusNameRelationHsn a ";
			hsql = "select distinct a.cusName,a.cusSpec,a.cusUnit.name,a.emsNo from StatCusNameRelationHsn a left join a.cusUnit";
		} else {
			// hsql = "select distinct a.cusName+a.cusSpec+a.cusUnit.name from
			// StatCusNameRelationHsn a ";
			hsql = "select distinct a.cusName,a.cusSpec,a.cusUnit.name from StatCusNameRelationHsn a left join a.cusUnit";
		}
		hsql = hsql + " where a.company.id= ? and a.materielType= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(materielType);
		List<Object> list = this.find(hsql, parameters.toArray());
		List returnlist = new ArrayList();
		/*
		 * for (Object item : list) { String value = (String) item; if (value !=
		 * null) { value = value.replaceAll(" ", "");
		 * returnlist.add(value.trim()); } }
		 */
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String allstr = "";
			if (obj[0] != null) {
				allstr += obj[0].toString();
			}
			if (obj[1] != null) {
				allstr += obj[1].toString();
			}
			if (obj[2] != null) {
				allstr += obj[2].toString();
			}
			if (isOneToMany) {
				if (obj[3] != null) {
					allstr += obj[3].toString();
				}
			}
			allstr = allstr.replaceAll(" ", "");
			returnlist.add(allstr.trim());
		}
		return returnlist;
	}

	/**
	 * 返回比较KEY，从对应关系报关资料中
	 * 
	 * @param materielType
	 * @return
	 */
	public Map<String, StatCusNameRelationHsn> findKeyFromStatCusNameRelationHsn2(
			String materielType, Boolean isOneToMany) {
		String hsql = "select a from StatCusNameRelationHsn a left join a.cusUnit where a.company.id= ? and a.materielType= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(materielType);
		List<StatCusNameRelationHsn> list = this.find(hsql,
				parameters.toArray());
		Map<String, StatCusNameRelationHsn> returnMap = new HashMap<String, StatCusNameRelationHsn>();

		for (StatCusNameRelationHsn sr : list) {
			String name = sr.getCusName() == null ? "" : sr.getCusName();
			String spec = sr.getCusSpec() == null ? "" : sr.getCusSpec();
			String unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName();
			String emsNo = sr.getEmsNo() == null ? "" : sr.getEmsNo();
			String key = name + "," + spec + "," + unitName;
			if (isOneToMany) {
				key = key + "," + emsNo;
			}
			key = key.replaceAll(" ", "");
			returnMap.put(key, sr);
		}

		return returnMap;
	}

	/**
	 * 从条件中查找StatCusNameRelationHsn对象，使用于对应关系导入
	 * 
	 * @param cusName
	 * @param cusSpec
	 * @param cusUnitName
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public StatCusNameRelationHsn findStatCusNameRelationHsnByCondition(
			String cusName, String cusSpec, String cusUnitName, String emsNo,
			Boolean isOneToMany, String materielType) {
		List<Object> parameters = new ArrayList<Object>();
		/*
		 * String hsql = "select a from StatCusNameRelationHsn a " + "where
		 * a.cusName = ? and a.cusSpec = ? and a.cusUnit.name = ? "; if
		 * (isOneToMany) { hsql = hsql + "and a.emsNo = ? and a.company.id= ?";
		 * } else { hsql = hsql + "and a.company.id= ?"; }
		 * parameters.add(cusName); parameters.add(cusSpec);
		 * parameters.add(cusUnitName); <<<<<<< .mine if(isOneToMany){ =======
		 * if (isOneToMany) >>>>>>> .r10936 parameters.add(emsNo); }
		 * parameters.add(CommonUtils.getCompany().getId());
		 * List<StatCusNameRelationHsn> list = this.find(hsql, parameters
		 * .toArray()); if (list != null && list.size() != 0) { return
		 * list.get(0); } else { return null; }
		 */
		String hsql = "select a from StatCusNameRelationHsn a where a.company.id = ?";
		parameters.add(CommonUtils.getCompany().getId());
		if (cusName != null && !cusName.equals("")) {
			hsql += " and a.cusName = ? ";
			parameters.add(cusName);
		}
		if (cusSpec != null && !cusSpec.equals("")) {
			hsql += " and a.cusSpec = ? ";
			parameters.add(cusSpec);
		}
		if (cusUnitName != null && !cusUnitName.equals("")) {
			hsql += " and a.cusUnit.name =? ";
			parameters.add(cusUnitName);
		}
		if (isOneToMany && emsNo != null && !emsNo.equals("")) {
			hsql += " and a.emsNo = ?";
			parameters.add(emsNo);
		}
		if (materielType != null && !materielType.equals("")) {
			hsql += " and a.materielType =? ";
			parameters.add(materielType);
		}
		List<StatCusNameRelationHsn> list = this.find(hsql,
				parameters.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	/**
	 * 查找StatCusNameRelationHsn的KEY
	 * 
	 * @param materielType
	 * @return
	 */
	public List findKeyFromStatCusNameRelationHsn(String materielType) {
		List<Object[]> list = new ArrayList();
		List returnList = new ArrayList();
		String hsql = "select distinct a.complex.code,a.cusName,a.cusSpec,a.cusUnit.name,a.emsNo from StatCusNameRelationHsn a "
				+ "where a.company.id = ? and a.materielType = ?";
		list = this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				materielType });
		for (Object[] item : list) {
			String joinStr = "";
			// if (item[0] != null)
			joinStr += (item[0] == null ? "" : item[0].toString());
			// if (item[1] != null)
			joinStr += item[1] == null ? "" : item[1].toString();
			// if (item[2] != null)
			joinStr += item[2] == null ? "" : item[2].toString();
			// if (item[3] != null)
			joinStr += item[3] == null ? "" : item[3].toString();
			// if (item[4] != null)
			joinStr += item[4] == null ? "" : item[4].toString();
			returnList.add(joinStr);
		}
		return returnList;
	}

	/**
	 * 查找StatCusNameRelationHsn的名称和规格组成的key
	 * 
	 * @return
	 */
	public List findStatCusNameRelationHsnNameAndSpec(String materielType) {
		List<Object[]> list = new ArrayList();
		String hsql = "select distinct a.cusName,a.cusSpec from StatCusNameRelationHsn a "
				+ "where a.company.id = ? and a.materielType = ?";
		list = this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				materielType });
		return list;
	}

	/***************************************************************************
	 * * 保存资料副表 工厂料件
	 * 
	 * @param statCusNameRelationMt
	 *            工厂料件
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveStatCusNameRelationMt(
			StatCusNameRelationMt statCusNameRelationMt)
			throws DataAccessException {
		this.saveOrUpdate(statCusNameRelationMt);
	}

	/**
	 * 删除资料副表 工厂料件
	 * 
	 * @param statCusNameRelationMt
	 *            工厂料件
	 */
	public void deleteStatCusNameRelationMt(
			StatCusNameRelationMt statCusNameRelationMt) {
		this.delete(statCusNameRelationMt);
	}

	/**
	 * 显示资料副表 实际报关商品
	 * 
	 * @param statCusNameRelation
	 *            资料主表商品大类
	 * @return 与指定的商品大类对应的按序号排序的实际报关商品内容
	 */
	public List findStatCusNameRelationHsn(
			StatCusNameRelation statCusNameRelation) {

		return this
				.find("select a from StatCusNameRelationHsn a where a.statCusNameRelation.company.id= ? and a.statCusNameRelation.id= ? "
						+ " order by a.seqNum ",
						new Object[] { CommonUtils.getCompany().getId(),
								String.valueOf(statCusNameRelation.getId()) });
	}

	/**
	 * 保存资料副表 实际报关商品
	 * 
	 * @param statCusNameRelationHsn
	 *            实际报关商品
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveStatCusNameRelationHsn(
			StatCusNameRelationHsn statCusNameRelationHsn)
			throws DataAccessException {
		this.saveOrUpdate(statCusNameRelationHsn);
	}

	/**
	 * 删除资料副表 实际报关商品
	 * 
	 * @param statCusNameRelationHsn
	 *            实际报关商品
	 */
	public void deleteStatCusNameRelationHsn(
			StatCusNameRelationHsn statCusNameRelationHsn) {
		this.delete(statCusNameRelationHsn);
	}

	/**
	 * 新增对照关系时，选择物料
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 与指定物料类型对应的并且不在工厂物料中存在的物料
	 */
	public List findMaterialForRelation(String materialType) {
		String hsql = " select a from Materiel as a "
				+ " where a.company.id=? and a.scmCoi.coiProperty=? "
				+ " and a.ptNo not in (select b.materiel.ptNo from StatCusNameRelationMt as b"
				+ " where b.statCusNameRelation.company.id=? "
				+ " and b.statCusNameRelation.materielType=? )";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				materialType, CommonUtils.getCompany().getId(), materialType });
	}

	/**
	 * 新增商品大类时,进行大类编码查询
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 不在商品大类中存在的商品编码
	 */
	public List findComplex(String materialType) {
		String hql = "select a from Complex as a where a.code not in "
				+ " ( select distinct b.complex.code from StatCusNameRelation as b where b.materielType=?"
				+ " and b.company.id=? ) ";
		return this.find(hql, new Object[] { materialType,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据编码查询
	 * 
	 * @param code
	 *            编码
	 * @return 商品编码信息
	 */
	public List findComplexByCode(String code) {
		String hql = "select a from Complex as a where a.code = ? ";
		return this.find(hql, new Object[] { code });
	}

	/**
	 * 新增对照关系时，选择报关商品
	 * 
	 * @param projectType
	 *            模块类型
	 * @param materialType
	 *            物料类型
	 * @return 联网监管,纸质手册中的报关商品
	 */
	public List findComplexForRelation(int projectType, String materialType) {
		String hsql = "select a.commSerialNo,a.complex,a.commName,a.commSpec,a.unit ";
		List<Object> parameters = new ArrayList<Object>();
		if (projectType == ProjectType.BCUS) {
			hsql += " select a from CustomsDeclarationCommInfo as a ";
		} else if (projectType == ProjectType.BCS) {
			hsql += " select a from BcsCustomsDeclarationCommInfo as a ";
		}
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (materialType.equals(MaterielType.MATERIEL)) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);// 直接进口
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 转厂进口
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
		} else if (materialType.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);// 直接进口
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂进口
			parameters.add(ImpExpType.DIRECT_IMPORT);// 直接出口
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 转厂出口
			// return new ArrayList();
		} else if (materialType.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);// 直接出口
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
		} else if (materialType.equals(MaterielType.MACHINE)) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.EQUIPMENT_IMPORT);// 设备进口
			parameters.add(ImpExpType.BACK_PORT_REPAIR);// 设备退港
		} else if (materialType.equals(MaterielType.REMAIN_MATERIEL)) {
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);// 边角料退港
			parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);// 边角料内销
		} else if (materialType.equals(MaterielType.BAD_PRODUCT)) { // 残次品
			// hsql += " and a.baseCustomsDeclaration.impExpType in (?,?) ";
			// parameters.add(ImpExpType.EQUIPMENT_IMPORT);//设备进口
			// parameters.add(ImpExpType.BACK_PORT_REPAIR);//设备退港
			return new ArrayList();
		}
		hsql += " and a.commSerialNo is not null ";
		// hsql += " and a.commSerialNo not in (select b.seqNum from
		// StatCusNameRelationHsn as b"
		// + " where b.statCusNameRelation.company.id=? "
		// + " and b.statCusNameRelation.materielType=? )"
		// + " and a.commSerialNo is not null ";
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(materialType);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 分页查询查询所有的临时资料主表
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 分页查询所有的临时资料主表
	 */
	public List<StatCusNameRelation> findTempStatCusNameRelation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from StatCusNameRelation as a"
				+ " where  a.materielType=? and a.company.id=? ";

		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}

		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 分页查询所有的工厂物料
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有的工厂物料
	 */
	public List<StatCusNameRelationMt> findStatCusNameRelationMt(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from StatCusNameRelationMt as a  "
				+ "  left join fetch a.statCusNameRelation s left join fetch a.materiel "
				+ " where  s.materielType=? and s.company.id=? ";

		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}

		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 分页查询所有的物料与报关资料对照关系表
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 物料和物料与报关商品折算比的list
	 */
	public List<TempObject> findFactoryAndFactualCustomsRalation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel,a.unitConvert from FactoryAndFactualCustomsRalation as a  "
				+ " where a.statCusNameRelationHsn.materielType=? and a.company.id=? ";

		if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)
				|| MaterielType.BAD_PRODUCT.equals(materielType)) {

			hsql = " select distinct a,a.unitConvert from Materiel a where  a.company= ? and a.scmCoi.coiProperty = ? ";
			paramters.add(CommonUtils.getCompany());
			// paramters.add("半成品");
			paramters.add(materielType);
		} else {
			paramters.add(materielType);
			paramters.add(CommonUtils.getCompany().getId());
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}
		List<Object[]> tmp = super.findPageList(hsql, paramters.toArray(),
				index, length);
		List<TempObject> tempObject = new ArrayList();
		for (Object[] item : tmp) {
			TempObject temp = new TempObject();
			temp.setObject(item[0]);
			temp.setObject1(item[1]);
			tempObject.add(temp);
		}

		return tempObject;
	}

	/**
	 * 根据工厂料号查找对应关系
	 * 
	 * @param ptNo
	 * @return
	 * @author wss
	 */
	public FactoryAndFactualCustomsRalation findFactoryAndFactualCustomsRalationByM(
			Materiel m) {
		String hsql = " select distinct a from FactoryAndFactualCustomsRalation a "
				+ " left join fetch a.materiel " + " where a.company.id=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		if (m != null) {
			hsql += " and a.materiel.id = ? ";
			paramters.add(m.getId());
		}
		List list = super.find(hsql, paramters.toArray());
		if (list != null && list.size() != 0) {
			return (FactoryAndFactualCustomsRalation) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据工厂料号查找对应关系
	 * 
	 * @param ptNo
	 * @return
	 * @author wss
	 */
	public List findFactoryAndFactualCustomsRalation(Materiel m) {
		String hsql = " select distinct a from FactoryAndFactualCustomsRalation a "
				+ " left join fetch a.materiel " + " where a.company.id=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		if (m != null) {
			hsql += " and a.materiel.id = ? ";
			paramters.add(m.getId());
		}
		List list = super.find(hsql, paramters.toArray());
		return list;
	}

	/**
	 * 查找对应关系
	 * 
	 * @param ptNo
	 * @return
	 */
	public List findFactoryAndFactualCustomsRalationForBill(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		String hsql = "select distinct a from FactoryAndFactualCustomsRalation a "
				+ "left join fetch a.statCusNameRelationHsn b "
				+ "left join fetch a.materiel c "
				+ "where b.materielType = ? and a.company.id=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}

		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	public List<Double> findFactoryAndFactualCustomsRalationMaterielForNetWeight(
			String materielType, String MaterielPtNo) {
		String hsql = "select distinct a.materiel.ptNetWeight from FactoryAndFactualCustomsRalation a "
				+ "left join  a.statCusNameRelationHsn b "
				+ "left join  a.materiel c "
				+ "where b.materielType = ? and a.company.id=?  and c.ptNo=?";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(MaterielPtNo);
		List list = this.find(hsql, paramters.toArray());
		return list;

	}

	/**
	 * 分页查询工厂物料根据大类所对应的十码资料
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 大类所对应的十码资料
	 */
	public List<StatCusNameRelationHsn> findStatCusNameRelationHsn(String ptNo,
			int index, int length, String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from StatCusNameRelationHsn a  "
				+ "where a.statCusNameRelation.id = "
				+ "(select a.statCusNameRelation.id from StatCusNameRelationMt a "
				+ "where a.materiel.ptNo = ?) and a.company.id=? ";

		paramters.add(ptNo);
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}

		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 分页查询工厂物料根据物料与报关商品对照表
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<FactoryAndFactualCustomsRalation> findFactualCustoms(
			String ptNo, int index, int length, String property, Object value,
			boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a.statCusNameRelationHsn.id"
				+ " from FactoryAndFactualCustomsRalation a"
				+ " where a.materiel.ptNo = ? and a.company.id=?"
				+ " group by a.statCusNameRelationHsn.id";

		paramters.add(ptNo);
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}

		List<Object> list = super.findPageList(hsql, paramters.toArray(),
				index, length);
		// 从找出的列表中再去FactualCustoms表中查找符合要求的记录
		List<FactoryAndFactualCustomsRalation> returnList = new ArrayList();
		for (Object item : list) {
			List tmp = findFactualCustoms(item);
			returnList.addAll(tmp);
		}
		// 检查是否为电子账册和成品，如果是，查找版本号，并将查找到的记录加在列表尾
		int size = returnList.size();
		for (int i = 0; i < size; i++) {
			FactoryAndFactualCustomsRalation fc = returnList.get(i);
			List tmp = getVersionFromFactualCustoms(fc
					.getStatCusNameRelationHsn());
			if (tmp.size() > 1) {
				returnList.remove(fc);
				i--;
				size--;
				returnList.addAll(tmp);
			}
		}
		return returnList;
	}

	/**
	 * 
	 * @param ptNo
	 *            工厂料号
	 * @param compareDate
	 *            单据有效日期，在要在手册开始与结束日期之间并最接近结束日期的那个十码资料
	 * @return 返回十码资料
	 */
	public StatCusNameRelationHsn findStatCusNameRelationHsn(String ptNo,
			Date compareDate) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from StatCusNameRelationHsn a  "
				+ "where a.statCusNameRelation.id = "
				+ "(select a.statCusNameRelation.id from StatCusNameRelationMt a "
				+ "where a.materiel.ptNo = ?) and a.company.id=? ";

		paramters.add(ptNo);
		paramters.add(CommonUtils.getCompany().getId());
		List<StatCusNameRelationHsn> list = super.find(hsql,
				paramters.toArray());
		if (list != null && list.size() != 0) {
			TreeMap<Long, StatCusNameRelationHsn> map = new TreeMap<Long, StatCusNameRelationHsn>(
					new Comparator() {
						public int compare(Object arg0, Object arg1) {
							//
							Long i = (Long) arg0;
							Long j = (Long) arg1;
							return i.intValue() - j.intValue();
						}
					});
			long i = 0;
			for (StatCusNameRelationHsn item : list) {
				if (item.getEmsEndDate() != null) {
					if ((i = compareDate.getTime()
							- item.getEmsEndDate().getTime()) > 0) {
						map.put(i, item);
					}
				}
			}
			if (!map.isEmpty()) {
				long first = map.firstKey();
				return map.get(first);
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	/**
	 * 
	 * @param ptNo
	 *            工厂料号
	 * @param compareDate
	 *            单据有效日期，在要在手册开始与结束日期之间并最接近结束日期的那个报关商品资料
	 * @return 返回报关商品资料
	 */
	public FactoryAndFactualCustomsRalation findFactualCustoms(String ptNo,
			Date compareDate) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from FactoryAndFactualCustomsRalation a  "
				+ "where a.materiel.ptNo = ? and a.company.id=? ";

		paramters.add(ptNo);
		paramters.add(CommonUtils.getCompany().getId());
		List<FactoryAndFactualCustomsRalation> list = super.find(hsql,
				paramters.toArray());
		// // 从找出的列表中再去FactualCustoms表中查找符合要求的记录
		// List<StatCusNameRelationHsn> returnList = new ArrayList();
		// if (list != null && list.size() != 0) {
		// for (FactoryAndFactualCustomsRalation item : list) {
		// if (item.getStatCusNameRelationHsn() != null) {
		// List tmp = findFactualCustoms(item
		// .getStatCusNameRelationHsn());
		// returnList.addAll(tmp);
		// }
		// }
		// }
		if (list != null && list.size() != 0) {
			TreeMap<Long, FactoryAndFactualCustomsRalation> map = new TreeMap<Long, FactoryAndFactualCustomsRalation>(
					new Comparator() {// 比较器
						public int compare(Object arg0, Object arg1) {
							//
							Long i = (Long) arg0;
							Long j = (Long) arg1;
							return i.intValue() - j.intValue();
						}
					});
			long i = 0;
			if (compareDate != null) {
				for (FactoryAndFactualCustomsRalation item : list) {// 比较时间
					if (item.getStatCusNameRelationHsn().getEmsEndDate() != null) {
						if ((i = item.getStatCusNameRelationHsn()
								.getEmsEndDate().getTime()
								- compareDate.getTime()) >= 0
								&& (compareDate.getTime() - (item
										.getStatCusNameRelationHsn()
										.getEmsBeginDate() == null ? 0l : item
										.getStatCusNameRelationHsn()
										.getEmsBeginDate().getTime())) >= 0) {
							map.put(i, item);
						}
					}
				}
			}
			if (!map.isEmpty()) {// 如果MAP不为空，则存在最近的日期记录，然后返回此记录
				long first = map.firstKey();
				FactoryAndFactualCustomsRalation fc = map.get(first);
				// List<StatCusNameRelationHsn> tmp =
				// getVersionFromFactualCustoms(fc);
				return fc;
			} else {// 如果为空，则不存在符合要求的日期记录，例如说电子账册，都是没有结束日期的，则返回列表中第一条记录
				// for (FactoryAndFactualCustomsRalation item : list)
				// if (item.getMateriel().getPtNo().equals(ptNo))
				// return item.getStatCusNameRelationHsn();
				return list.get(0);

			}
		} else {
			return null;
		}

	}

	/**
	 * 如果是电子手册和成品，则查找其对应的版本号，再根据版本号复制多条FactualCustoms
	 * 
	 * @param statCusNameRelationHsn
	 * @return
	 */
	public List<StatCusNameRelationHsn> getVersionFromFactualCustoms(
			StatCusNameRelationHsn statCusNameRelationHsn) {
		List<StatCusNameRelationHsn> tmp = new ArrayList();
		if (statCusNameRelationHsn.getProjectType() == ProjectType.BCUS
				&& statCusNameRelationHsn.getMaterielType().equals(
						MaterielType.FINISHED_PRODUCT)) {
			// 如果是电子账册和成品
			List<Object> paramters = new ArrayList<Object>();
			String hsql = "select a from EmsHeadH2kVersion a "
					+ "where a.emsHeadH2kExg.emsHeadH2k.emsNo = ? "
					+ "and a.emsHeadH2kExg.seqNum = ? "
					+ "and a.emsHeadH2kExg.emsHeadH2k.historyState = ? "
					+ "and a.company.id = ?";
			paramters.add(statCusNameRelationHsn.getEmsNo());
			paramters.add(statCusNameRelationHsn.getSeqNum());
			paramters.add(new Boolean(false));
			paramters.add(CommonUtils.getCompany().getId());
			List<EmsHeadH2kVersion> list = super
					.find(hsql, paramters.toArray());
			if (list != null && list.size() > 0) {
				StatCusNameRelationHsn fc = null;
				for (EmsHeadH2kVersion item : list) {
					fc = (StatCusNameRelationHsn) statCusNameRelationHsn
							.clone();
					fc.setVersion(item.getVersion());
					tmp.add(fc);
				}
			} else {
				tmp.add(statCusNameRelationHsn);
			}
		} else {
			tmp.add(statCusNameRelationHsn);
		}
		return tmp;
	}

	/**
	 * 将物料所对应的报关商品在FactualCustoms表中找出同商品编码，同物料类别，同emsNo 和不同序号的报关商品记录
	 * 
	 * @param statCusNameRelationHsn
	 * @return
	 */
	public List<FactoryAndFactualCustomsRalation> findFactualCustoms(Object objs) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from FactoryAndFactualCustomsRalation a"
				+ " where a.statCusNameRelationHsn.id = ?"
				+ " and a.company.id = ? ";
		paramters.add(objs);
		paramters.add(CommonUtils.getCompany().getId());
		return super.find(hsql, paramters.toArray());
	}

	/**
	 * String hsql = " from Materiel as a " + " where a.company.id=? and
	 * a.scmCoi.coiProperty=? " + " and a.ptNo not in (select b.materiel.ptNo
	 * from StatCusNameRelationMt as b" + " where
	 * b.statCusNameRelation.company.id=? " + " and
	 * b.statCusNameRelation.materielType=? )"; return this.find(hsql, new
	 * Object[] { CommonUtils.getCompany().getId(), materialType,
	 * CommonUtils.getCompany().getId(), materialType });
	 */
	/**
	 * 所有的临时资料主表来自物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的临时资料主表
	 */
	public List findTempStatCusNameRelation1(String materielType) {
		String hql = "select a from StatCusNameRelation as a"
				+ " where a.id in (select b.statCusNameRelation.id from StatCusNameRelationMt as b"
				+ " where b.statCusNameRelation.company.id=? and b.statCusNameRelation.materielType=? )"
				+ " and a.id in (select c.statCusNameRelation.id from StatCusNameRelationHsn as c"
				+ " where c.statCusNameRelation.company.id=? and c.statCusNameRelation.materielType=?)"
				+ " and a.company.id=? and a.materielType=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				materielType, CommonUtils.getCompany().getId(), materielType,
				CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 所有的临时资料主表来自物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的临时资料主表
	 */
	public List findTempStatCusNameRelation2(String materielType) {
		String hql = "select a from StatCusNameRelation as a"
				+ " where a.id in (select b.statCusNameRelation.id from StatCusNameRelationMt as b"
				+ " where b.statCusNameRelation.company.id=? and b.statCusNameRelation.materielType=? )"
				+ " and a.id not in (select c.statCusNameRelation.id from StatCusNameRelationHsn as c"
				+ " where c.statCusNameRelation.company.id=? and c.statCusNameRelation.materielType=?)"
				+ " and a.company.id=? and a.materielType=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				materielType, CommonUtils.getCompany().getId(), materielType,
				CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 所有的临时资料主表来自物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的临时资料主表
	 */
	public List findTempStatCusNameRelation3(String materielType) {
		String hql = "select a from StatCusNameRelation as a"
				+ " where a.id not in (select b.statCusNameRelation.id from StatCusNameRelationMt as b"
				+ " where b.statCusNameRelation.company.id=? and b.statCusNameRelation.materielType=? )"
				+ " and a.id in (select c.statCusNameRelation.id from StatCusNameRelationHsn as c"
				+ " where c.statCusNameRelation.company.id=? and c.statCusNameRelation.materielType=?)"
				+ " and a.company.id=? and a.materielType=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				materielType, CommonUtils.getCompany().getId(), materielType,
				CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 所有的临时资料主表来自物料类型
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与指定的物料类型匹配的临时资料主表
	 */
	public List findTempStatCusNameRelation4(String materielType) {
		String hql = "select a from StatCusNameRelation as a"
				+ " where a.id not in (select b.statCusNameRelation.id from StatCusNameRelationMt as b"
				+ " where b.statCusNameRelation.company.id=? and b.statCusNameRelation.materielType=? )"
				+ " and a.id not in (select c.statCusNameRelation.id from StatCusNameRelationHsn as c"
				+ " where c.statCusNameRelation.company.id=? and c.statCusNameRelation.materielType=?)"
				+ " and a.company.id=? and a.materielType=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				materielType, CommonUtils.getCompany().getId(), materielType,
				CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 获得正在执行的电子手册,并且十码不为空的记录
	 * 
	 * @param type
	 *            物料类型
	 * @return 正在执行的并且十吗不为空的电子手册
	 */
	public List findDzscInnerMergeDataByType(String type) {
		return this
				.find(" select a from DzscInnerMergeData a left join fetch a.materiel"
						+ " left join fetch a.dzscTenInnerMerge "
						+ " where a.imrType = ? and a.company.id=? and a.stateMark = ? "
						+ " and a.dzscTenInnerMerge.tenComplex is not null ",
						new Object[] { type, CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
	}

	/**
	 * 查找实际报关商品来自物料类型 资料主表
	 * 
	 * @param materielType
	 *            物料类型
	 * @param iterator
	 *            资料主表的遍历
	 * @return 与物料类型匹配的实际报关商品
	 */
	public List<StatCusNameRelationHsn> findStatCusNameRelationHsn(
			String materielType, Iterator<StatCusNameRelation> iterator) {
		List<Object> paramters = new ArrayList<Object>();
		List temps = new ArrayList();
		String hsql = "select a from StatCusNameRelationHsn as a  "
				+ "  left join fetch a.statCusNameRelation s ";
		Long uuid = UUID.randomUUID().getLeastSignificantBits();
		if (iterator != null) {
			while (iterator.hasNext()) {
				StatCusNameRelation sr = (StatCusNameRelation) iterator.next();
				TempObjectId temp = new TempObjectId();
				temp.setUuid(uuid);
				temp.setObjId(sr.getId());
				this.saveOrUpdateNoCache(temp);
				temps.add(temp);
			}
			List klist = this.find(" select a.objId from TempObjectId a");
			hsql += " where 1=1 and s.id in ( select a.objId from TempObjectId a "
					+ " where a.uuid= ? ) ";
			paramters.add(uuid);
		}
		// hsql += " where 1=1 ";
		// if (iterator != null) {
		// int i = 0;
		// while (iterator.hasNext()) {
		// StatCusNameRelation sr = (StatCusNameRelation) iterator.next();
		// if (i == 0) {
		// hsql += " and ( s.id = ? ";
		// paramters.add(sr.getId());
		// } else {
		// hsql += " or s.id = ? ";
		// paramters.add(sr.getId());
		// }
		// i++;
		// }
		// if (i > 0) {
		// hsql += " ) ";
		// }
		// }
		hsql += " and s.materielType=? and s.company.id=? ";
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		List relist = this.find(hsql, paramters.toArray());
		this.batchUpdateOrDelete(" delete  TempObjectId  a  where a.uuid=  ? ",
				new Object[] { uuid });
		return relist;
	}

	/**
	 * 获得自定义统计资料(料件统计报表)
	 * 
	 * @param year
	 *            截止日期
	 * @return 加工贸易原材料来源与使用情况报表 自用资料
	 */
	public List findImgOrgUseInfos(String year) {
		return this
				.find("select a from ImgOrgUseInfo a where a.year=? and a.company = ? order by a.money desc",
						new Object[] { year, CommonUtils.getCompany() });
	}

	/**
	 * 获得海关资料(料件统计报表)
	 * 
	 * @param year
	 *            截止日期
	 * @return 加工贸易原材料来源与使用情况报表 海关资料
	 */
	public List findImgOrgUseInfoCustoms(String year) {
		return this
				.find("select a from ImgOrgUseInfoCustom a where a.year=? and a.company = ? order by a.money desc",
						new Object[] { year, CommonUtils.getCompany() });
	}

	/**
	 * 成品统计报表(自定义)
	 * 
	 * @param year
	 *            截止日期
	 * @return 加工贸易产品流向情况表 自用资料
	 */
	public List findExgExportInfos(String year) {
		return this
				.find("select a from ExgExportInfo a where a.year=? and a.company = ?",
						new Object[] { year, CommonUtils.getCompany() });
	}

	/**
	 * 成品统计报表(自定义)
	 * 
	 * @param year
	 *            截止日期
	 * @return 加工贸易产品流向情况表 海关资料
	 */
	public List findExgExportInfoCustoms(String year) {
		return this
				.find("select a from ExgExportInfoCustom a where a.year=? and a.company = ?",
						new Object[] { year, CommonUtils.getCompany() });
	}

	/**
	 * 保存海关统计报表(成品)
	 * 
	 * @param exgExportInfoBase
	 *            加工贸易产品流向情况表
	 */
	public void saveExgExportInfoBase(ExgExportInfoBase exgExportInfoBase) {
		this.saveOrUpdate(exgExportInfoBase);
	}

	/**
	 * 删除所有成品统计报表(海关)
	 * 
	 * @param year
	 *            截止日期
	 */
	public void deleteExgExportInfoCustom(String year) {
		this.batchUpdateOrDelete(
				"delete from ExgExportInfoCustom m where m.company.id = ? and m.year=? ",
				new Object[] { CommonUtils.getCompany().getId(), year });
	}

	/**
	 * 删除所有自定义统计资料(料件统计报表)
	 * 
	 * @param year
	 *            截止日期
	 */
	public void deleteImgOrgUseInfoCustom(String year) {
		this.batchUpdateOrDelete(
				"delete from ImgOrgUseInfoCustom m where m.company.id = ? and m.year=? ",
				new Object[] { CommonUtils.getCompany().getId(), year });
	}

	/**
	 * 删除所有海关设备
	 * 
	 */
	public void deleteBillFixingThing() {
		this.batchUpdateOrDelete(
				"delete from BillFixingThing m where m.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除所有海关设备
	 * 
	 */
	public void deleteBillFixing() {
		this.batchUpdateOrDelete(
				"delete from BillFixing m where m.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除所有海关统计报表(成品)
	 * 
	 */
	public void deleteExgExportInfo(String year) {
		this.batchUpdateOrDelete(
				"delete from ExgExportInfo m where m.company.id = ? and m.year=? ",
				new Object[] { CommonUtils.getCompany().getId(), year });
	}

	/**
	 * 删除所有海关统计资料(料件统计报表)
	 * 
	 * @param year
	 *            截止日期
	 */
	public void deleteImgOrgUseInfo(String year) {
		this.batchUpdateOrDelete(
				"delete from ImgOrgUseInfo m where m.company.id = ? and m.year=? ",
				new Object[] { CommonUtils.getCompany().getId(), year });
	}

	/**
	 * 分页查询所有自定义统计报表(成品)
	 * 
	 * @param index
	 * @param length
	 * @param year
	 * @return 加工贸易企业成品流向情况表
	 */
	public List findExgExportInfo(int index, int length, String year) {
		String hsql = "select a  from ExgExportInfo "
				+ " as a  where  a.company.id= ? and a.year=?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(year);
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 分页查询所有自定义统计资料(料件统计报表)
	 * 
	 * @param index
	 * @param length
	 * @param year
	 * @return 加工贸易企业原材料来源与使用情况表
	 */
	public List findImgOrgUseInfo(int index, int length, String year) {
		String hsql = "select a  from ImgOrgUseInfo "
				+ " as a  where  a.company.id= ? and a.year=?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(year);
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 分页查询查询所有海关统计报表(成品)
	 * 
	 * @param index
	 * @param length
	 * @param year
	 *            截止日期
	 * @return 加工贸易企业成品流向情况表 海关
	 */
	public List findExgExportInfoCustom(int index, int length, String year) {
		String hsql = "select a  from ExgExportInfoCustom "
				+ " as a  where  a.company.id= ? and a.year=?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(year);
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 分页查询所有海关统计资料(料件统计报表)
	 * 
	 * @param index
	 * @param length
	 * @param year
	 *            截止日期
	 * @return 加工贸易企业原材料来源与使用情况表 海关
	 */
	public List findImgOrgUseInfoCustom(int index, int length, String year) {
		String hsql = "select a  from ImgOrgUseInfoCustom "
				+ " as a  where  a.company.id= ? and a.year=?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(year);
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 分页查询所有设备
	 * 
	 * @param index
	 * @param length
	 * @return 加工贸易生产设备情况表
	 */
	public List findBillFixingThing(int index, int length) {
		String hsql = "select a  from BillFixingThing "
				+ " as a  where  a.company.id= ?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 分页查询所有设备
	 * 
	 * @param index
	 * @param length
	 * @return 加工贸易生产设备情况表
	 */
	public List findBillFixing(int index, int length) {
		String hsql = "select a  from BillFixing "
				+ " as a  where  a.company.id= ?   ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 保存单据对应的物料的单价
	 * 
	 * @param billDetail
	 *            单据明细
	 */
	public void saveOneBillPrice(BillDetail billDetail) {
		this.saveOrUpdate(billDetail);
	}

	/**
	 * 保存所有单据中的包含这个物料的单价
	 * 
	 * @param billDetail
	 *            单据明细
	 * @param materielType
	 *            物料类型
	 */
	public void saveAllBillPrice(BillDetail billDetail, String materielType) {
		String hsql;
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielTypeName(materielType);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(billDetail.getHsPrice());
		parameters.add(billDetail.getPtPrice());
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billDetail.getPtName());
		parameters.add(billDetail.getPtSpec());
		parameters.add(billDetail.getPtUnit());
		parameters.add(billDetail.getPtPart());
		// hsql="update "+ tableName +" as a " +
		// " set a.hsPrice =? " +
		// " ,a.ptPrice=? " +
		// " where a.company=? " +
		// " and a.ptName =? " +
		// " and a.ptSpec =? " +
		// " and a.ptUnit.name =? "+
		// " and a.ptPart =? " ;
		hsql = "update " + tableName + " set hsPrice =? " + " ,ptPrice=? "
				+ " where company.id=? " + " and ptName =? "
				+ " and ptSpec =? " + " and ptUnit =? " + " and ptPart =? ";
		this.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 删除所有实际报关商品单位折算比是空或者为0的记录
	 * 
	 * @param materielType
	 *            物料类型
	 */
	public void deleteStatCusNameRelationHsn(String materielType) {

		this.batchUpdateOrDelete(
				"delete from StatCusNameRelationHsn m where m.company.id = ?"
						+ "  and m.statCusNameRelation.id in (select s.id from  StatCusNameRelation s where s.materielType=?)  "
						+ " and ( m.unitConvert is null or m.unitConvert = ? ) ",
				new Object[] { CommonUtils.getCompany().getId(), materielType,
						0.0 });
	}

	/**
	 * 删除所有工厂物料单位折算比是空或者为0的记录
	 * 
	 * @param materielType
	 *            物料类型
	 */
	public void deleteStatCusNameRelationMt(String materielType) {
		this.batchUpdateOrDelete(
				"delete from StatCusNameRelationMt m where m.company.id = ?"
						+ "  and m.statCusNameRelation.id in (select s.id from  StatCusNameRelation s where s.materielType=?)  "
						+ " and ( m.unitConvert is null or m.unitConvert = ? ) ",
				new Object[] { CommonUtils.getCompany().getId(), materielType,
						0.0 });
	}

	/**
	 * 删除所有实际报关商品单位折算比是空或者为0的记录
	 * 
	 * @param statCusNameRelation
	 *            商品大类
	 */
	public void deleteStatCusNameRelationHsn(
			StatCusNameRelation statCusNameRelation) {
		this.batchUpdateOrDelete(
				"delete from StatCusNameRelationHsn m where m.company.id = ?"
						+ " and m.statCusNameRelation = ? ", new Object[] {
						CommonUtils.getCompany().getId(), statCusNameRelation });
	}

	/**
	 * 删除所有工厂物料单位折算比是空或者为0的记录
	 * 
	 * @param statCusNameRelation
	 *            工厂物料
	 */
	public void deleteStatCusNameRelationMt(
			StatCusNameRelation statCusNameRelation) {
		this.batchUpdateOrDelete(
				"delete from StatCusNameRelationMt m where m.company.id = ?"
						+ " and m.statCusNameRelation = ? ", new Object[] {
						CommonUtils.getCompany().getId(), statCusNameRelation });
	}

	/**
	 * 保存加工贸易原材料来源与使用情况表
	 * 
	 * @param base
	 *            加工贸易原材料来源与使用情况表
	 */
	public void saveImgOrgUseInfoBase(ImgOrgUseInfoBase base) {
		super.saveOrUpdate(base);
	}

	/**
	 * 查找所有不在实际报关商品中存在的商品大类
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与物料类型匹配符合条件的商品大类
	 */
	public List findStatCusNameRelationNotInMtHsn(String materielType) {
		String hql = "select a from StatCusNameRelation as a"
				+ " where (a.id not in (select b.statCusNameRelation.id from StatCusNameRelationMt as b"
				+ " where b.statCusNameRelation.company.id=? and b.statCusNameRelation.materielType=? )"
				+ " or a.id not in (select c.statCusNameRelation.id from StatCusNameRelationHsn as c"
				+ " where c.statCusNameRelation.company.id=? and c.statCusNameRelation.materielType=?))"
				+ " and a.company.id=? and a.materielType=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				materielType, CommonUtils.getCompany().getId(), materielType,
				CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 跟据料号和类型获得相应的折算系数
	 * 
	 * @param materielType
	 *            物料类型
	 * @param ptNo
	 *            料号
	 * @return 折算系数
	 */
	public Double getUnitConvertByPtNo(String materielType, String ptNo) {
		String hql = "select a.unitConvert from FactoryAndFactualCustomsRalation a "
				+ "where a.materiel.ptNo =  ? "
				+ "and a.company = ? "
				+ " and a.statCusNameRelationHsn.materielType=? ";
		List list = this.find(hql,
				new Object[] { ptNo, CommonUtils.getCompany(), materielType });
		if (list != null && list.size() > 0) {
			return (Double) list.get(0);
		}
		return null;
	}

	/**
	 * 获得月度结存
	 * 
	 * @param materielType
	 *            物料类型
	 * @param month
	 *            月份
	 * @return 与物料类型匹配的指定月份的月度结存
	 */
	public List findMonthStorageByMonth(String materielType, int month) {
		String hql = "select a from MonthStorage a "
				+ "where a.materielType =  ? and a.company = ? "
				+ " and a.month =? ";
		List list = this.findNoCache(hql, new Object[] { materielType,
				CommonUtils.getCompany(), month });
		return list;
	}

	/**
	 * 查询所有是存在
	 * 
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            当前月
	 * @return 以前各月的结存情况
	 */
	public List<Integer> findFrontMonthIsExist(String materielType,
			int currentMonth) {
		String hql = "select distinct a.month from MonthStorage a "
				+ "where a.materielType =  ? and a.company = ? "
				+ " and a.month < ? ";
		List list = this.findNoCache(hql, new Object[] { materielType,
				CommonUtils.getCompany(), currentMonth });
		return list;
	}

	/**
	 * 批量保存月度结存
	 * 
	 * @param list
	 *            月度结存
	 */
	public void batchSaveMonthStorage(List<MonthStorage> list) {
		this.batchSaveOrUpdate(list);
	}

	/**
	 * 批量删除 月度结存
	 * 
	 * @param materielType
	 *            物料类型
	 * @param month
	 *            月份
	 */
	public void batchDeleteMonthStorage(String materielType, int month) {
		this.batchUpdateOrDelete(
				"delete from MonthStorage m where m.company.id = ? and m.materielType =  ? and m.month = ? ",
				new Object[] { CommonUtils.getCompany().getId(), materielType,
						month });
	}

	/**
	 * 获得月度结存 ---- 按报关的来导入月度
	 * 
	 * @param materielType
	 *            物料类型
	 * @param month
	 *            月份
	 * @return 与物料类型匹配的指定月份的月度结存
	 */
	public List findMonthStorageByMonth2(String materielType, int month) {
		String hql = "select a from MonthStorage2 a "
				+ "where a.materielType =  ? and a.company = ? "
				+ " and a.month =? ";
		List list = this.findNoCache(hql, new Object[] { materielType,
				CommonUtils.getCompany(), month });
		return list;
	}

	/**
	 * 查询所有是存在
	 * 
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            当前月
	 * @return 以前各月的结存情况
	 */
	public List<Integer> findFrontMonthIsExist2(String materielType,
			int currentMonth) {
		String hql = "select distinct a.month from MonthStorage2 a "
				+ "where a.materielType =  ? and a.company = ? "
				+ " and a.month < ? ";
		List list = this.findNoCache(hql, new Object[] { materielType,
				CommonUtils.getCompany(), currentMonth });
		return list;
	}

	/**
	 * 批量保存月度结存
	 * 
	 * @param list
	 *            月度结存
	 */
	public void batchSaveMonthStorage2(List<MonthStorage2> list) {
		this.batchSaveOrUpdate(list);
	}

	/**
	 * 批量删除 月度结存
	 * 
	 * @param materielType
	 *            物料类型
	 * @param month
	 *            月份
	 */
	public void batchDeleteMonthStorage2(String materielType, int month) {
		this.batchUpdateOrDelete(
				"delete from MonthStorage2 m where m.company.id = ? and m.materielType =  ? and m.month = ? ",
				new Object[] { CommonUtils.getCompany().getId(), materielType,
						month });
	}

	public List findScmcoc() {
		return this.find("select a from ScmCoc a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findWareSet() {
		return this.find("select a from WareSet a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findCasMasterMateriel(String classtable) {
		return this.find("select a from " + classtable
				+ " a where a.company.id = ? order by a.id desc",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findAllMateriel(String scmCoi) {
		return this.find("select a from Materiel a where a.company.id = ? and "
				+ "(a.scmCoi.coiProperty = ? )", new Object[] {
				CommonUtils.getCompany().getId(), scmCoi });
	}

	/**
	 * 根据物料类别找到相关的物料信息 （此方法针对除半成品外的其它类型）
	 * 
	 * @param typename
	 * @return wss:第二步map有调用
	 */
	public List findPtNoAndCodeInStatCusNameRelation(String typename) {
		String type = "";
		// String type2 = "";//wss 主要用于残次品(半成品)导入时用

		// 半成品没有对应关系的
		if ("残次品(半成品)".equals(typename) || "半成品".equals(typename)) {
			return new ArrayList();
		}

		if ("料件".equals(typename)) {
			type = MaterielType.MATERIEL;
		} else if ("成品".equals(typename)) {
			type = MaterielType.FINISHED_PRODUCT;
		} else if ("设备".equals(typename)) {
			type = MaterielType.MACHINE;
		} else if ("残次品(料件)".equals(typename)) {
			type = MaterielType.MATERIEL;
		} else if ("残次品(成品)".equals(typename)) {
			type = MaterielType.FINISHED_PRODUCT;
		} else if ("边角料".equals(typename)) {
			type = MaterielType.REMAIN_MATERIEL;
		}

		//
		// //wss:2010.04.27修改（半成品较特殊-->报关常用工厂物料）
		// //料号、报关商品编码、报关规格、报关单位、折算比例、工厂单价、报关名称、净重
		// if("半成品".equals(typename)){
		// return this.find(
		// " select distinct a.ptNo,a.complex.id,"
		// + " a.ptSpec,a.ptUnit,a.unitConvert,"
		// + " a.ptPrice,a.ptName ,a.ptNetWeight "
		// + " from Materiel a "
		// // + " left join fetch a.complex left join fetch a.ptUnit "
		// + " where a.company.id = ? and a.scmCoi.coiProperty = ? ",
		// new Object[] { CommonUtils.getCompany().getId(), type});
		// }
		//
		// //如果是残次品（半成品），报关常用工厂物料中的半成品、残次品
		// //料号、报关商品编码、报关规格、报关单位、折算比例、工厂单价、报关名称、净重
		// if("残次品(半成品)".equals(typename)){
		// return this.find(
		// " select distinct a.ptNo,a.complex.id,"
		// + " a.ptSpec,a.ptUnit,a.unitConvert,"
		// + " a.ptPrice,a.ptName ,a.ptNetWeight "
		// + " from Materiel a "
		// // + " left join fetch a.complex left join fetch a.ptUnit "
		// + " where a.company.id = ? "
		// + " and ( a.scmCoi.coiProperty = ? or a.scmCoi.coiProperty = ? ) ",
		// new Object[] { CommonUtils.getCompany().getId(), type, type2});
		// }

		// 查找对应关系
		// //料号、报关商品编码、报关名称、报关规格、报关单位、折算比例、工厂单价、净重、手册号
		return this
				.find(" select distinct a.materiel.ptNo,"
						+ " a.statCusNameRelationHsn.complex.id,"
						+ " a.statCusNameRelationHsn.cusName ,"
						+ " a.statCusNameRelationHsn.cusSpec,"
						+ " a.statCusNameRelationHsn.cusUnit,"
						+ " a.unitConvert,"
						+ " a.materiel.ptPrice,"
						+ " a.materiel.ptNetWeight,"
						+ " a.statCusNameRelationHsn.emsNo,a.createDate "
						+ " from FactoryAndFactualCustomsRalation a "
						+ " where a.company.id = ? and a.statCusNameRelationHsn.materielType = ? "
						+ " order by a.createDate desc, a.unitConvert desc ",
						new Object[] { CommonUtils.getCompany().getId(), type });

	}

	/**
	 * 根据物料类别找到相关的物料信息 （此方法针对半成品）
	 * 
	 * @param typename
	 * @return wss:2010.09.28
	 */
	public List findPtNoAndCodeInMateriel(String typename) {

		// 这里针对半成品
		if (!("残次品(半成品)".equals(typename) || "半成品".equals(typename))) {
			return null;
		}

		String type = "";
		String type2 = "";// wss 主要用于残次品(半成品)导入时用

		if ("半成品".equals(typename)) {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
		} else {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
			type2 = MaterielType.BAD_PRODUCT;
		}

		// 料号、报关商品编码、报关名称、报关规格、报关单位、折算比例、工厂单价、净重
		if ("半成品".equals(typename)) {
			return this.find(" select distinct a.ptNo," + " a.complex.id,"
					+ " a.ptName," + " a.ptSpec," + " a.ptUnit,"
					+ " a.unitConvert," + " a.ptPrice," + " a.ptNetWeight "
					+ " from Materiel a "
					+ " where a.company.id = ? and a.scmCoi.coiProperty = ? ",
					new Object[] { CommonUtils.getCompany().getId(), type });
		}

		// 料号、报关商品编码、报关名称、报关规格、报关单位、折算比例、工厂单价、净重
		else {
			return this
					.find(" select distinct a.ptNo,"
							+ " a.complex.id,"
							+ " a.ptName,"
							+ " a.ptSpec,"
							+ " a.ptUnit,"
							+ " a.unitConvert,"
							+ " a.ptPrice,"
							+ " a.ptNetWeight "
							+ " from Materiel a "
							+ " where a.company.id = ? "
							+ " and ( a.scmCoi.coiProperty = ? or a.scmCoi.coiProperty = ? ) ",
							new Object[] { CommonUtils.getCompany().getId(),
									type, type2 });
		}

	}

	/**
	 * 查找唯一的 FactoryAndFactualCustomsRalation 名称规格单位
	 * 
	 * @param typename
	 * @return 第二步hsMap有调用
	 */
	public List findDistinctFFCByPtNoNameSpecUnit(String typename) {
		if (("半成品".equals(typename) || "残次品(半成品)".equals(typename))) {
			return new ArrayList();
		}

		String type = "";
		// String type2 = "";
		if ("料件".equals(typename)) {
			type = MaterielType.MATERIEL;
		} else if ("成品".equals(typename)) {
			type = MaterielType.FINISHED_PRODUCT;
		} else if ("设备".equals(typename)) {
			type = MaterielType.MACHINE;
		}
		// else if ("半成品".equals(typename)) {
		// type = MaterielType.SEMI_FINISHED_PRODUCT;
		// }
		else if ("残次品(料件)".equals(typename)) {
			type = MaterielType.MATERIEL;
		} else if ("残次品(成品)".equals(typename)) {
			type = MaterielType.FINISHED_PRODUCT;

			// //wss2010.07.12残次品(半成品)源自 报关常用工厂物料半成品、残次品
			// } else if ("残次品(半成品)".equals(typename)) {
			// type = MaterielType.SEMI_FINISHED_PRODUCT;
			// type2 = MaterielType.BAD_PRODUCT;
			//
		} else if ("边角料".equals(typename)) {
			type = MaterielType.REMAIN_MATERIEL;
		}

		// //wss:2010.04.27修改：（半成品特殊--->报关常用工厂物料）
		// //料号、报关名称、报关规格、报关单位
		// if("半成品".equals(typename)){
		// return this
		// .find(
		// " select distinct a.ptNo,a.ptName, "
		// + " a.ptSpec,a.ptUnit.name "
		// + " from Materiel a "
		// + " where a.company.id = ? and a.scmCoi.coiProperty = ? "
		// + " and a.ptUnit is not null ",
		// new Object[] { CommonUtils.getCompany().getId(), type});
		// }
		//
		// //wss2010.07.12残次品（半成品）报关常用工厂物料（半成品、残次品）
		// //料号、报关名称、报关规格、报关单位
		// if("残次品(半成品)".equals(typename)){
		// return this
		// .find(
		// " select distinct a.ptNo,a.ptName, " //料号，海关商品名称
		// + " a.ptSpec,a.ptUnit.name " //海关规格 ，海关单位名称
		// + " from Materiel a "
		// + " where a.company.id = ? "
		// + " and ( a.scmCoi.coiProperty = ? or a.scmCoi.coiProperty = ? )"
		// + " and a.ptUnit is not null ",
		// new Object[] { CommonUtils.getCompany().getId(), type,type2});
		// }

		// 对应关系中的报关资料
		// 料号、报关名称、报关规格、报关单位、手册号
		return this.find(" select distinct a.materiel.ptNo,"
				+ " a.statCusNameRelationHsn.cusName, "
				+ " a.statCusNameRelationHsn.cusSpec,"
				+ " a.statCusNameRelationHsn.cusUnit.name,"
				+ " a.statCusNameRelationHsn.emsNo "
				+ " from FactoryAndFactualCustomsRalation a "
				+ " where a.company.id = ? "
				+ " and a.statCusNameRelationHsn.materielType = ? "
				+ " and a.statCusNameRelationHsn.cusUnit is not null ",
				new Object[] { CommonUtils.getCompany().getId(), type });

	}

	/**
	 * 查找唯一的 Mateiel 报关名称 、报关规格 、报关单位
	 * 
	 * @param typename
	 * @return
	 * @author wss2010.09.28
	 */
	public List findDistinctMaterielByPtNoNameSpecUnit(String typename) {
		if (!("半成品".equals(typename) || "残次品(半成品)".equals(typename))) {
			return new ArrayList();
		}
		String type = "";
		String type2 = "";
		if ("半成品".equals(typename)) {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
		} else {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
			type2 = MaterielType.BAD_PRODUCT;
		}

		// 料号、报关名称、报关规格、报关单位
		if ("半成品".equals(typename)) {
			return this.find(" select distinct a.ptNo," + " a.ptName, "
					+ " a.ptSpec "
					// 2010-12-22屏蔽 + " a.ptUnit.name "
					+ " from Materiel a " + " where a.company.id = ? "
					+ " and a.scmCoi.coiProperty = ? ",
			// 2010-12-22屏蔽 + " and a.ptUnit is not null ",
					new Object[] { CommonUtils.getCompany().getId(), type });
		} else {
			return this
					.find(" select distinct a.ptNo,"
							+ " a.ptName, "
							+ " a.ptSpec "
							// 2010-12-22屏蔽 + " a.ptUnit.name "
							+ " from Materiel a "
							+ " where a.company.id = ? "
							+ " and ( a.scmCoi.coiProperty = ? or a.scmCoi.coiProperty = ? )",
					// 2010-12-22屏蔽 + " and a.ptUnit is not null ",
							new Object[] { CommonUtils.getCompany().getId(),
									type, type2 });
		}

	}

	/**
	 * 根据billType与billNo查找出单据主表
	 * 
	 * @param billType
	 *            --单据类型
	 * @param billNo
	 *            --单据号
	 * @return
	 */
	public BillMaster findBillMasterByBillTypeAndBillNo(BillType billType,
			String billNo) {
		String tableName = BillUtil.getBillDetailTableNameByBillType(billType
				.getBillType());
		if (tableName == null) {
			return null;
		} else {
			tableName = tableName.replace("Detail", "Master");
		}
		String hsql = " select a from " + tableName + " a where "
				+ "a.billType = ? and a.billNo = ? ";
		List list = this.find(hsql, new Object[] { billType, billNo });
		if (list.size() == 0) {
			return null;
		} else {
			return (BillMaster) list.get(0);
		}
	}

	/**
	 * 根据申请单表体id查找出单据转申请单中间表MakeImpExpRequestBill
	 * 
	 * @param iecId
	 *            --申请单表体id
	 * @return
	 */
	public MakeImpExpRequestBill findMakeImpExpRequestBillByIEId(String iecId) {
		String hsql = " select a from MakeImpExpRequestBill a where "
				+ "a.iecId = ?";
		List list = this.find(hsql, new Object[] { iecId });
		if (list.size() == 0) {
			return null;
		} else {
			return (MakeImpExpRequestBill) list.get(0);
		}
	}

	/**
	 * 根据billMaster查找出从表中的数据(根据主表查找从表)
	 * 
	 * @param billMaster
	 *            --从表中关联主表的字段
	 * @return
	 */
	public List findBillDetail(BillMaster billMaster) {
		String tableName = BillUtil.getBillDetailTableNameByBillType(billMaster
				.getBillType().getBillType());
		if (tableName == null) {
			return null;
		}
		String hsql = " select a from " + tableName + " a where "
				+ " a.billMaster = ? ";
		return this.find(hsql, new Object[] { billMaster });
	}

	public List findFactualCustoms(String materielType, int projectType) {
		String hsql = "  select a from  StatCusNameRelationHsn a where a.company.id=?  "
				+ " and   a.materielType= ?  and a.projectType=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				materielType, projectType });

	}

	public List findFactoryAndFactualCustomsRalation(
			StatCusNameRelationHsn statCusNameRelationHsn) {
		String hsql = "  select a from  FactoryAndFactualCustomsRalation a where a.company.id=?  "
				+ " and   a.statCusNameRelationHsn.id=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				statCusNameRelationHsn.getId() });

	}

	public List findFactoryAndFactualCustomsRalation(String materielType) {
		String hsql = "  select a from  FactoryAndFactualCustomsRalation a "
				+ "  left join  fetch a.materiel left join     fetch  a.statCusNameRelationHsn   "
				+ "      where a.company.id=?  "
				+ " and a.statCusNameRelationHsn.materielType=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				materielType });

	}

	public List findFactoryAndFactualCustomsRalation(String materielType,
			int projectType) {
		String hsql = "  select a from  FactoryAndFactualCustomsRalation a where a.company.id=?  "
				+ " and   a.statCusNameRelationHsn.materielType=? and a.statCusNameRelationHsn.projectType=?";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				materielType, projectType });

	}

	public List findFactualCustoms(String materielType) {
		String hsql = "  select a from  StatCusNameRelationHsn a where a.company.id=?  "
				+ " and   a.materielType=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				materielType });

	}

	public void saveBillMaster(BillMaster billMaster)
			throws DataAccessException {
		this.saveOrUpdate(billMaster);
	}

	public void DeleteAllBillMaster(BillType billType) {

		String hsql = "select a  from  BillMasterMateriel"
				+ " as a  where  a.billType.id =? and a.company.id= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(billType == null ? "" : billType.getId());
		parameters.add(CommonUtils.getCompany().getId());
		List ls = this.find(hsql, parameters.toArray());
		this.deleteAll(ls);
	}

	public void DeleteAllBillDetail(BillType billType) {
		String hsql = "select a from BillDetailMateriel"
				+ " as a where a.billMaster.billType.id = ? and a.company.id = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(billType == null ? "" : billType.getId());
		parameters.add(CommonUtils.getCompany().getId());
		List ls = this.find(hsql, parameters.toArray());
		this.deleteAll(ls);
	}

	/**
	 * 查询所有发票 by 手册号
	 * 
	 * @param emsNo
	 * @return
	 */
	public List findCasInvoice(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CasInvoice a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.emsNo = ? ";
			parameters.add(emsNo);
		}
		return this.find(hsql, parameters.toArray());
	}

	public List findBillDetailChinBuy(ScmCoc scmcoc, String hsName) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BillDetailMateriel a where a.billMaster.billType.code  in (?,?,?) and a.company.id = ? "
				+ "  and a.billMaster.isValid = ? ";
		parameters.add("1005");
		parameters.add("1113");
		parameters.add("1017");
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		if (scmcoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.hsName = ? ";
			parameters.add(hsName);
		}
		hsql += " order by a.billMaster.validDate,a.billMaster.billType.code,a.ptPart";
		return this.find(hsql, parameters.toArray());
	}

	public List findCasInvoiceInfo(String emsNo, ScmCoc scmcoc, String hsName) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CasInvoiceInfo a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (scmcoc != null) {
			hsql += " and a.casInvoice.customer.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.casInvoice.emsNo = ?";
			parameters.add(emsNo);
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.cuName = ?";
			parameters.add(hsName);
		}
		hsql += " order by a.casInvoice.customer.name,a.cuName,a.casInvoice.validDate";
		return this.find(hsql, parameters.toArray());
	}

	public List findCasInvoiceInfo(String emsNo, ScmCoc scmcoc, String hsName,
			Date beginDate, Date endDate, boolean isCancel) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CasInvoiceInfo a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (scmcoc != null) {
			hsql += " and a.casInvoice.customer.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.casInvoice.emsNo = ?";
			parameters.add(emsNo);
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.cuName = ?";
			parameters.add(hsName);
		}
		if (beginDate != null) {
			hsql += " and a.casInvoice.validDate >= ?";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.casInvoice.validDate < ?";
			parameters.add(endDate);
		}
		if (isCancel) {
			hsql += " and a.canceled=?   ";
			parameters.add(new Boolean(true));
		} else {
			hsql += " and a.canceled=? or a.canceled is null ";
			parameters.add(new Boolean(false));
		}
		hsql += " order by a.casInvoice.validDate";
		return this.find(hsql, parameters.toArray());
	}

	public List findCasInvoiceInfoCancel(String emsNo, ScmCoc scmcoc,
			String hsName) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CasInvoiceInfo a where a.company.id = ? and a.casInvoice.canceled = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(false);
		if (scmcoc != null) {
			hsql += " and a.casInvoice.customer.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.casInvoice.emsNo = ?";
			parameters.add(emsNo);
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.cuName = ?";
			parameters.add(hsName);
		}
		hsql += " order by a.casInvoice.emsNo,a.casInvoice.customer.name,a.cuName,a.casInvoice.validDate";
		return this.find(hsql, parameters.toArray());

	}

	public List findCasInvoice() {
		return this.find("  select a from  CasInvoice a  where a.company.id=?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 根据条件查找发票与单据的对应关系
	 * 
	 * @param contractNo
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List<Object[]> findInvoiceAndBillCorresponding(String emsNo,
			ScmCoc scmcoc, String hsName) {
		List<Object> parameters = new ArrayList<Object>();
		String join = "";
		if (scmcoc != null)
			join = "a.customer.name,";
		String hsql = "select a.customer.name,a.cusName,"
				+ "a.cusUnit.name,sum(a.invoiceNum),sum(a.billDetailNum), "
				+ "sum(invoiceWeight),sum(billWeight),a.invoicePrice "
				+ " from InvoiceAndBillCorresponding a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (scmcoc != null) {
			hsql += " and a.customer.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.emsNo = ?";
			parameters.add(emsNo);
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.cusName = ?";
			parameters.add(hsName);
		}
		hsql += " group by a.customer.name,a.cusName,a.cusUnit.name,a.invoicePrice ";
		List<Object[]> returnlist = this.find(hsql, parameters.toArray());
		return returnlist;
	}

	/**
	 * 统计该手册号关联的单据中所有的入库数量
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List<Object[]> SumBillNum(String emsNo, ScmCoc scmcoc, String hsName) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.billMaster.scmCoc.name,a.hsName,a.hsUnit.name,"
				+ "sum(a.hsAmount),sum(a.netWeight) from BillDetailMateriel a "
				+ "where a.billMaster.billType.code  in (?,?,?) and a.company.id = ? "
				+ "and a.billMaster.isValid = ? ";
		parameters.add("1005");
		parameters.add("1113");
		parameters.add("1017");
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		if (scmcoc != null) {
			hsql += " and a.billMaster.scmCoc.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.emsNo = ?";
			parameters.add(emsNo);
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.hsName = ? ";
			parameters.add(hsName);
		}
		hsql += " group by a.billMaster.scmCoc.name,a.hsName,a.hsUnit.name";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据条件开票的数量
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List SumInvoiceInfo(String emsNo, ScmCoc scmcoc, String hsName) {
		System.out.println("emsNo=" + emsNo);
		System.out.println("scmcoc=" + scmcoc);
		System.out.println("hsName=" + hsName);
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select "
				+ "a.casInvoice.customer.name,a.cuName,a.unit.name,sum(a.amount),sum(a.totalWeight),sum(a.totalMoney) "
				+ "from CasInvoiceInfo a where a.company.id = ?";
		parameters.add(CommonUtils.getCompany().getId());
		if (scmcoc != null) {
			hsql += " and a.casInvoice.customer.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.casInvoice.emsNo = ?";
			parameters.add(emsNo);
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.cuName = ?";
			parameters.add(hsName);
		}
		hsql += " group by a.casInvoice.customer.name,a.cuName,a.unit.name ";

		ArrayList<TempSumInvoiceInfo> returnlist = new ArrayList();
		ArrayList<Object[]> list = new ArrayList();
		list = (ArrayList) this.find(hsql, parameters.toArray());
		for (Object[] object : list) {
			TempSumInvoiceInfo tmp = new TempSumInvoiceInfo();

			tmp.setCustomer((String) object[0]);
			tmp.setCuName((String) object[1]);
			tmp.setUnit((String) object[2]);
			tmp.setSumAmount((Double) object[3]);
			tmp.setSumWeight((Double) object[4]);
			tmp.setSumMoney((Double) object[5]);
			returnlist.add(tmp);
		}
		return returnlist;
	}

	/**
	 * 根据条件查找统计发票核销的数量
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List SumInvoiceInfoCancel(String emsNo, ScmCoc scmcoc, String hsName) {
		List<Object> parameters = new ArrayList<Object>();
		String join = "";
		String hsql = "select "
				+ "a.casInvoice.customer.name,a.cuName,a.unit.name,sum(a.amount),sum(a.totalWeight),sum(a.totalMoney) "
				+ "from CasInvoiceInfo a where a.company.id = ?"
				+ " and a.canceled = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		if (scmcoc != null) {
			hsql += " and a.casInvoice.customer.id = ?";
			parameters.add(scmcoc.getId());
		}
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.casInvoice.emsNo = ?";
			parameters.add(emsNo);
		}
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.cuName = ?";
			parameters.add(hsName);
		}
		hsql += " group by a.casInvoice.customer.name,a.cuName,a.unit.name ";

		ArrayList<TempSumInvoiceInfo> returnlist = new ArrayList();
		ArrayList<Object[]> list = new ArrayList();
		list = (ArrayList) this.find(hsql, parameters.toArray());
		for (Object[] object : list) {
			TempSumInvoiceInfo tmp = new TempSumInvoiceInfo();

			tmp.setCustomer((String) object[0]);
			tmp.setCuName((String) object[1]);
			tmp.setUnit((String) object[2]);
			tmp.setSumAmount((Double) object[3]);
			tmp.setSumWeight((Double) object[4]);
			tmp.setSumMoney((Double) object[5]);
			returnlist.add(tmp);
		}
		return returnlist;
	}

	public List findAllEmsHeadH2k() {
		List lbcus = new ArrayList();
		List lbcs = this
				.find("select a from Contract a where a.company.id = ?  and a.declareState=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE });

		String isEmsH2kSend = this.getBpara(BcusParameter.EmsEdiH2kSend);
		if (isEmsH2kSend != null && "1".equals(isEmsH2kSend)) {
			List tlbcus = this
					.find("select a from EmsHeadH2k a where a.company.id = ?  and a.declareState=? ",
							new Object[] { CommonUtils.getCompany().getId(),
									DeclareState.CHANGING_EXE });
			if (tlbcus.size() != 0) {
				lbcus.add(tlbcus.get(0));
			}
		} else {
			lbcus = this
					.find("select a from EmsHeadH2k a where a.company.id = ?  and a.declareState=? ",
							new Object[] { CommonUtils.getCompany().getId(),
									DeclareState.PROCESS_EXE });
		}
		List ldzsc = this
				.find("select a from DzscEmsPorHead a where a.company.id = ?   and a.declareState=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE });
		List relist = new ArrayList();
		relist.addAll(lbcus);
		relist.addAll(lbcs);
		relist.addAll(ldzsc);
		return relist;

	}

	// 发票来源于各模块内部归并
	public List findImgFromBaseEmsHead(BaseEmsHead data) {
		List rlist = new ArrayList();
		String sqlBCS = "  select distinct a.complex, a.name ,a.spec, a.comUnit ,a.seqNum "
				+ " from  BcsInnerMerge m left join m.bcsTenInnerMerge  a   "
				+ " where a.company.id=?  and  m.materielType= ? ";
		String sqlBCUS = "  select distinct a.hsAfterComplex , a.hsAfterMaterielTenName, a.hsAfterMaterielTenSpec, "
				+ " a.hsAfterMemoUnit, a.hsAfterTenMemoNo from  InnerMergeData  a  "
				+ " where a.company.id=? and a.imrType= ?  ";
		String sqlDZSC = "  select distinct a.tenComplex, a.tenPtName,a.tenPtSpec, a.tenUnit , a.tenSeqNum  from  "
				+ "   DzscInnerMergeData  m left join m.dzscTenInnerMerge  a "
				+ " where a.company.id=?  and m.imrType=? ";
		List bcslist = this.find(sqlBCS, new Object[] {
				CommonUtils.getCompany().getId(), MaterielType.MATERIEL });
		List bcuslist = this.find(sqlBCUS, new Object[] {
				CommonUtils.getCompany().getId(), MaterielType.MATERIEL });
		List dzsclist = this.find(sqlDZSC, new Object[] {
				CommonUtils.getCompany().getId(), MaterielType.MATERIEL });

		for (int i = 0; i < bcslist.size(); i++) {
			Object[] objs = (Object[]) bcslist.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setComplex((Complex) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setCusSpec((String) objs[2]);
			eimg.setUnit((Unit) objs[3]);
			eimg.setSeqNum((Integer) objs[4]);
			eimg.setProjectType(ProjectType.BCS);
			rlist.add(eimg);
		}
		for (int i = 0; i < bcuslist.size(); i++) {
			Object[] objs = (Object[]) bcuslist.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setComplex((Complex) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setCusSpec((String) objs[2]);
			eimg.setUnit((Unit) objs[3]);
			eimg.setSeqNum((Integer) objs[4]);
			eimg.setProjectType(ProjectType.BCUS);
			rlist.add(eimg);
		}
		for (int i = 0; i < dzsclist.size(); i++) {
			Object[] objs = (Object[]) dzsclist.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setComplex((Complex) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setCusSpec((String) objs[2]);
			eimg.setUnit((Unit) objs[3]);
			eimg.setSeqNum((Integer) objs[4]);
			eimg.setProjectType(ProjectType.DZSC);
			rlist.add(eimg);
		}
		return rlist;
	}

	// 发票来源于各模块合同料件
	public List findFromBaseEmsHeadImg(BaseEmsHead data) {
		List rlist = new ArrayList();
		// --------------------------------------------------------------------
		List pbcsList = new ArrayList();
		String sqlBCS = "  select distinct a.complex, a.name ,a.spec, a.unit ,a.seqNum ,a.contract.emsNo "
				+ " from  ContractImg  a    where a.company.id=?  ";
		pbcsList.add(CommonUtils.getCompany().getId());
		if (data != null) {
			sqlBCS += " and  a.contract.id = ?  ";
			pbcsList.add(data.getId());
		}
		// --------------------------------------------------------------------
		List pbcusList = new ArrayList();
		String sqlBCUS = "  select distinct a.complex , a.name, a.spec, "
				+ " a.unit, a.seqNum , a.emsHeadH2k.emsNo from  EmsHeadH2kImg  a  "
				+ " where a.company.id=?   ";
		pbcusList.add(CommonUtils.getCompany().getId());
		if (data != null) {
			sqlBCUS += " and  a.emsHeadH2k.id = ?  ";
			pbcusList.add(data.getId());
		}
		// --------------------------------------------------------------------
		List pdzscList = new ArrayList();
		String sqlDZSC = "  select distinct a.complex, a.name, a.spec, a.unit , a.seqNum "
				+ " ,a.dzscEmsPorHead.emsNo "
				+ "from    DzscEmsImgBill   a   where a.company.id=?  ";
		pdzscList.add(CommonUtils.getCompany().getId());
		if (data != null) {
			sqlDZSC += " and  a.dzscEmsPorHead.id = ?  ";
			pdzscList.add(data.getId());
		}
		// --------------------------------------------------------------------
		List bcslist = this.find(sqlBCS, pbcsList.toArray());
		List bcuslist = this.find(sqlBCUS, pbcusList.toArray());
		List dzsclist = this.find(sqlDZSC, pdzscList.toArray());

		for (int i = 0; i < bcslist.size(); i++) {
			Object[] objs = (Object[]) bcslist.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setComplex((Complex) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setCusSpec((String) objs[2]);
			eimg.setUnit((Unit) objs[3]);
			eimg.setSeqNum((Integer) objs[4]);
			eimg.setEmsNo((String) objs[5]);
			eimg.setProjectType(ProjectType.BCS);
			rlist.add(eimg);
		}
		for (int i = 0; i < bcuslist.size(); i++) {
			Object[] objs = (Object[]) bcuslist.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setComplex((Complex) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setCusSpec((String) objs[2]);
			eimg.setUnit((Unit) objs[3]);
			eimg.setSeqNum((Integer) objs[4]);
			eimg.setEmsNo((String) objs[5]);
			eimg.setProjectType(ProjectType.BCUS);
			rlist.add(eimg);
		}
		for (int i = 0; i < dzsclist.size(); i++) {
			Object[] objs = (Object[]) dzsclist.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setComplex((Complex) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setCusSpec((String) objs[2]);
			eimg.setUnit((Unit) objs[3]);
			eimg.setSeqNum((Integer) objs[4]);
			eimg.setEmsNo((String) objs[5]);
			eimg.setProjectType(ProjectType.DZSC);
			rlist.add(eimg);
		}
		return rlist;
	}

	// 返回参数设定
	public String getBpara(int type) {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}

	public void saveCasInvoice(CasInvoice obj) {
		this.saveOrUpdate(obj);
	}

	public List findDistinctContract() {
		return this
				.find("select distinct a.contractNo from CasInvoice a where a.company.id = ?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findDistinctEmsNo() {
		return this
				.find("select distinct a.emsNo from CasInvoice a where a.company.id = ?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找短溢表中存在的名称/规格/单位,用于数据导入时检查,避免重复导入
	 * 
	 * @return
	 */
	public List findBalanceInfoNameSpecUnitName() {
		return this.find(
				"select distinct name+spec+unitName from BalanceInfo a "
						+ " where a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });
	}

	/**
	 * 查找盘点平衡表中存在的盘点时间/工厂料号,用于数据导入时检查,避免重复导入
	 * 
	 * @return
	 */
	public List findCheckBalanceTimeAndPtNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object[]> src = this
				.find("select distinct checkDate,ptNo,wareSet.name,ljCpMark,kcType from CheckBalance a "
						+ " where a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });
		List list = new ArrayList();
		if (src != null && src.size() != 0) {
			for (Object[] item : src) {
				String matchStr = sdf.format((Date) item[0]) + (String) item[1]
						+ (String) item[2] + (String) item[3]
						+ (String) item[4];
				list.add(matchStr);
			}
		}
		return list;
	}

	public List findCasInvoiceInfo(CasInvoice head, Boolean canceled) {
		if (head == null) {
			return new ArrayList();
		}
		List para = new ArrayList();
		String hsql = " select a   from CasInvoiceInfo a "
				+ " where a.company.id=?   and a.casInvoice.id=? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(head.getId());
		if (canceled != null) {
			if (canceled) {
				hsql += "  and  a.canceled=? ";
				para.add(new Boolean(true));
			} else {
				hsql += "  and  ( a.canceled=? or a.canceled is null )";
				para.add(new Boolean(false));
			}
		}
		return this.find(hsql, para.toArray());
	}

	public List findCasInvoiceInfoById(String id) {

		return this.find("select a from CasInvoiceInfo  a  where a.id =?  ",
				new Object[] { id });
	}

	public List findBillDetailMaterielById(String id) {
		return this.find(
				" select a from  BillDetailMateriel a where a.id =?  ",
				new Object[] { id });
	}

	public List findBillDetailProductById(String id) {
		return this.find(" select a from  BillDetailProduct a where a.id =?  ",
				new Object[] { id });
	}

	public List findCasInvoiceInfoId(Boolean canceled) {
		List para = new ArrayList();
		String hsql = " select a.id   from CasInvoiceInfo a "
				+ " where a.company.id=?   ";
		para.add(CommonUtils.getCompany().getId());
		if (canceled != null) {
			if (canceled) {
				hsql += "  and  a.canceled=? ";
				para.add(new Boolean(true));
			} else {
				hsql += "  and  ( a.canceled=? or a.canceled is null )";
				para.add(new Boolean(false));
			}
		}
		return this.find(hsql, para.toArray());
	}

	public List findInvoiceAndBillCorresponding() {
		return this
				.find(" select a from InvoiceAndBillCorresponding a  where a.company.id=?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findCasInvoiceByNo(String no) {
		return this.find("select a   from CasInvoice a "
				+ " where a.company.id=?   and a.invoiceNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), no });
	}

	public List findBillDetailMaterielByNameAndScmCoc(String id, String name,
			ScmCoc scmCoc) {
		List para = new ArrayList();
		String hsql = "  select a   from BillDetailMateriel a   ";
		if (name != null && !name.equals("")) {
			hsql += "  left join  a.complex ";
		}
		hsql += "  where a.company.id=?   ";
		para.add(CommonUtils.getCompany().getId());
		if (name != null && !name.equals("")) {
			hsql += "  and a.complex.code =? ";
			para.add(name);
		}
		hsql += " and a.billMaster.scmCoc.id= ?  "
				+ "and a.billMaster.billType.code in (?,?,?,?,? )  "
				+ " and  (a.customNum < a.ptAmount or  a.customNum is null)"
				+ "  and  a.billMaster.isValid= ?  and a.id  not in"
				+ " ( select distinct b.billDetailkey from InvoiceAndBillCorresponding b "
				+ " where b.invoiceInfokey=? )";

		para.add(scmCoc.getId());
		para.add("1005");
		para.add("1017");
		para.add("1113");
		para.add("1114");
		para.add("1107");
		para.add(new Boolean(true));
		para.add(id);
		return this.find(hsql, para.toArray());
	}

	public List findInvoiceAndBillCorrespondingByCasInvoiceInfo(
			CasInvoiceInfo info) {
		return this
				.find(" select a   from InvoiceAndBillCorresponding  a "
						+ " where a.company.id=?   and a.invoiceInfokey=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								info.getId() });
	}

	public List findInvoiceAndBillCorrespondingCusName(ScmCoc p1) {
		List para = new ArrayList();
		String hsql = " select   distinct a.cusName   from InvoiceAndBillCorresponding  a "
				+ " where a.company.id= ?    ";
		para.add(CommonUtils.getCompany().getId());
		if (p1 != null) {
			hsql += "  and a.customer.id=? ";
			para.add(p1.getId());
		}
		return this.find(hsql, para.toArray());
	}

	public List findInvoiceInfoCusName(ScmCoc p1) {
		List para = new ArrayList();
		String hsql = " select   distinct a.cuName   from CasInvoiceInfo  a "
				+ "left join a.casInvoice b   where a.company.id= ?    ";
		para.add(CommonUtils.getCompany().getId());
		if (p1 != null) {
			hsql += "  and b.customer.id=? ";
			para.add(p1.getId());
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 获取报关单中的保税设备
	 * 
	 * @param stateCode
	 *            状态,000为无限制,001为未解除监管,002为解除监管
	 * @param beginDate
	 *            开始申报日期
	 * @param endDate
	 *            结束申报日期
	 * @return List
	 */
	public List findFixtureInCustomSCommInfo(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		List arrayList = new ArrayList();
		String table = "";
		for (int i = 0; i < 3; i++) {
			parameters.clear();
			if (i == 0)
				table = "CustomsDeclarationCommInfo";// 电子帐册
			else if (i == 1)
				table = "DzscCustomsDeclarationCommInfo";// 电子手册
			else
				table = "BcsCustomsDeclarationCommInfo";// 纸质手册
			String hsql = "select a from " + table + " a where a.company.id=? "
					+ "  and a.baseCustomsDeclaration.effective=? "
					+ " and a.baseCustomsDeclaration.impExpType in (?,?,?) ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(true);
			parameters.add(ImpExpType.EQUIPMENT_IMPORT);// 设备进口
			parameters.add(ImpExpType.BACK_PORT_REPAIR);// 退港维修
			parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);// 设备退港

			if (beginDate != null) {
				hsql += " and a.baseCustomsDeclaration.impExpDate>= ? ";
				parameters.add(CommonUtils.getBeginDate(beginDate));
			}

			if (endDate != null) {
				hsql += " and a.baseCustomsDeclaration.impExpDate<= ? ";
				parameters.add(CommonUtils.getEndDate(endDate));
			}

			List list = this.find(hsql, parameters.toArray());
			if (!list.isEmpty())
				arrayList.addAll(list);
		}
		return arrayList;

	}

	/**
	 * 获取单据中心中的设备资料，要与报关有关联
	 * 
	 * @return List
	 */
	public List findBillDetailFixture() {
		List<Object> parameters = new ArrayList<Object>();
		List arrayList = new ArrayList();
		String hsql = "select a from BillDetailFixture a where a.company.id=? "
				+ " and a.billMaster.isValid=? "
				+ " and a.billMaster.billType.billType in (?,?) "
				+ " and a.customNo is not null and a.hsName is not null "
				+ " and a.complex is not null and a.emsNo is not null ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(SBillType.FIXTURE_IN);
		parameters.add(SBillType.FIXTURE_OUT);

		System.out.println("hsql-----------findBillDetailFixture()----------"
				+ hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获取获取国内购买的单据
	 * 
	 * @return List
	 */
	public List findCountryBuyBillDetailFixture(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		List arrayList = new ArrayList();
		String hsql = "select a from BillDetailFixture a where a.company.id=? "
				+ " and a.billMaster.isValid=? "
				+ " and a.billMaster.billType.billType in (?,?) "
				+ " and a.billMaster.billType.name=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(SBillType.FIXTURE_IN);
		parameters.add(SBillType.FIXTURE_OUT);
		parameters.add("设备国内采购");
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate>= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}

		if (endDate != null) {
			hsql += " and a.billMaster.validDate<= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找盘点平衡表中所有数据
	 * 
	 * @return
	 * @author wss
	 */
	public List findCheckBalanceConvertMateriel(Date beginDate, Date endDate) {
		System.out.println("beginDate=" + beginDate);
		System.out.println("endDate=" + endDate);
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from CheckBalanceConvertMateriel a "
				+ " where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		System.out.println("wss hsql =" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * wss：2010.04.28用于返回所有的 盘点平衡表
	 * 
	 * @return
	 */
	public List findCheckBalanceAll() {
		return this.find(
				"select a from CheckBalance a where a.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 通过料号找到报关常用工厂物料 wss：2010.04.28（用于盘点平衡表的 半产品 ）
	 * 
	 * @param ptNo
	 * @return
	 */
	public Materiel findMaterielByPtNo(String ptNo) {

		List<Materiel> list = new ArrayList<Materiel>();
		list = this.find(" select a from Materiel a " + " where a.company = ? "
				+ " and a.ptNo = ? ", new Object[] { CommonUtils.getCompany(),
				ptNo });

		if (list != null && list.size() != 0) {
			return (Materiel) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * wss：2010.09.146用于返回所有的 Materiel
	 * 
	 * @return
	 * @author wss
	 */
	public List findMaterielAll() {
		return this.find("select a from Materiel a where a.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * wss：2010.09.146用于返回所有的 FFC(对应关系)
	 * 
	 * @return
	 */
	public List findFFCAll() {
		return this
				.find("select a from FactoryAndFactualCustomsRalation a where a.company.id = ? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 成品 、半成品
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCheckBalanceCp(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.checkAmount),a.checkDate,a.wareSet.code,v.id,a.kcType from CheckBalance a,MaterialBomVersion v "
				+ " left join a.wareSet "
				+ " where a.ptNo = v.master.materiel.ptNo "
				+ " and v.version = (case when isnull(a.bomVersion,'')=''  then 0 else a.bomVersion END) "
				+ " and a.company.id = ? and (a.ljCpMark = '1'  or  a.ljCpMark = '2' )";// 物料类型
		// 为成品、半成品的
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}

		hsql += " group by a.checkDate,a.wareSet.code,v.id,kcType  order by v.id ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 料件
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author wss2010.07.19
	 */
	public List findCheckBalanceM(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from CheckBalance a "
				+ " where a.company.id = ? and a.ljCpMark = '0'";// 物料类型 为料件
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}

		hsql += " order by a.checkDate,a.ptNo ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 成品 、半成品
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author wss
	 */
	public List findCheckBalanceP(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CheckBalance a "
				+ " where a.company.id = ? and (a.ljCpMark = '1'  or  a.ljCpMark = '2' )";// 物料类型
		// 为成品、半成品的
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}

		hsql += " order by a.checkDate,a.wareSet.code,a.kcType ";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 删除成品 、半成品、残次品 wss:2010.05.09将半成品也加入
	 */
	public void deleteCheckBalanceCp() {
		List ls = this
				.find("select a from CheckBalance a where a.company.id = ? and (a.ljCpMark = '1' or a.ljCpMark = '2')",
						new Object[] { CommonUtils.getCompany().getId() });
		this.deleteAll(ls);
	}

	/**
	 * 删除CheckBalanceBom wss:2010.07.20
	 */
	public void deleteCheckBalanceConvertMateriel(String checkBalanceId) {
		// List ls = this
		// .find(
		// "select a from CheckBalanceConvertMateriel a where a.company.id = ? and a.fatherCheckBalance.id = ? ",
		// new Object[] { CommonUtils.getCompany().getId(), checkBalanceId});
		// this.deleteAll(ls);

		// wss这样会不会快一点？
		this.batchUpdateOrDelete(
				" delete from CheckBalanceConvertMateriel a where a.company.id = ? and a.fatherCheckBalance.id = ? ",
				new Object[] { CommonUtils.getCompany().getId(), checkBalanceId });

	}

	/**
	 * 批量保存盘点平衡表导入数据
	 * 
	 * @param list盘点平衡表导入数据
	 * @author wss
	 */
	public void batchSaveCheckBalance(List<CheckBalance> list) {
		this.batchSaveOrUpdate(list);
	}

	/**
	 * 批量保存对应关系表
	 * 
	 * @param list
	 *            从文件导入的对应关系数据
	 */
	/*
	 * public void batchSaveFactoryAndFactualCustomsRalation(
	 * List<FactoryAndFactualCustomsRalation> list, boolean isCover) { for (int
	 * i=0;i<list.size();i++){ FactoryAndFactualCustomsRalation obj =
	 * (FactoryAndFactualCustomsRalation) list.get(i); Materiel mt =
	 * obj.getMateriel(); if (mt != null && mt.getId() == null){
	 * this.saveOrUpdateNoCache(mt); } else if (mt != null && isCover){
	 * this.saveOrUpdateNoCache(mt); } this.saveOrUpdateNoCache(obj); }
	 * //this.batchSaveOrUpdate(list); }
	 */

	/**
	 * 保存盘点平衡表
	 * 
	 * @param checkBalance
	 *            单条盘点平衡表数据
	 */
	public void saveCheckBalance(CheckBalance checkBalance) {
		this.saveOrUpdate(checkBalance);
	}

	/**
	 * 删除CheckBalanceConvertMateriel
	 * 
	 * @param checkBalance
	 * @author wss
	 */
	public void deleteCheckBalanceConvertMateriel(CheckBalance checkBalance) {
		String hsql = " select a  from  CheckBalanceConvertMateriel as a "
				+ " left join fetch a.fatherCheckBalance "
				+ " where a.company= ? " + " and a.fatherCheckBalance = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(checkBalance);
		List ls = this.find(hsql, parameters.toArray());
		this.deleteAll(ls);
	}

	/**
	 * 分页查询盘点平衡表
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<CheckBalance> findCheckBalance(int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from CheckBalance a where a.company.id = ? ";
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}

		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	// /**
	// * 分页查询盘点平衡表
	// *
	// * @param index
	// * 数据的开始下标
	// * @param length
	// * 数据长度
	// * @param property
	// * 属性
	// * @param value
	// * 属性的值
	// * @param isLike
	// * 查找时用“Like”还是“＝”
	// * @return
	// *
	// * @author wss2010.07.19
	// */
	// public List<CheckBalance> findCheckBalanceBom(int index, int length,
	// String property, Object value, boolean isLike) {
	// List<Object> paramters = new ArrayList<Object>();
	// String hsql = "select a from CheckBalanceBom a where a.company.id = ? ";
	// paramters.add(CommonUtils.getCompany().getId());
	//
	// if (property != null && !"".equals(property) && value != null) {
	// if (isLike) {
	// hsql += " and  " + property + " like ?  ";
	// paramters.add(value + "%");
	// } else {
	// hsql += " and  " + property + " = ?  ";
	// paramters.add(value);
	// }
	// }
	//
	// return super.findPageList(hsql, paramters.toArray(), index, length);
	// }

	/**
	 * 查询归并中的设备资料
	 * 
	 * @param projectType
	 *            模块信息
	 * @return 查询归并中的设备资料
	 */
	public List findFixturInnerDate(int projectType) {
		System.out.println("--projectType:" + projectType);
		String hql = "";
		List paramters = new ArrayList();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from InnerMergeData a where a.imrType=? "
					+ " and a.hsAfterTenMemoNo is not null and a.materiel is not null ";
			paramters.add(MaterielType.MACHINE);

			break;
		case ProjectType.BCS:
			hql = "select a from BcsInnerMerge a where a.materielType=? "
					+ " and a.bcsTenInnerMerge.seqNum is not null and a.materiel is not null ";
			paramters.add(MaterielType.MACHINE);

			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscInnerMergeData a where a.imrType=? "
					+ " and a.dzscTenInnerMerge.tenSeqNum is not null and a.materiel is not null ";
			paramters.add(MaterielType.MACHINE);

			break;
		default:
			hql = "select a  from InnerMergeData a where a.imrType=? "
					+ " and a.hsAfterTenMemoNo is not null and a.materiel is not null ";
			paramters.add(MaterielType.MACHINE);
			break;
		}
		hql += " and a.company.id=? ";
		paramters.add(CommonUtils.getCompany().getId());
		System.out.println("hql----fix-----------------" + hql);
		return find(hql, paramters.toArray());
	}

	/**
	 * 根据名称查找报关单位资料
	 * 
	 * @param unitName
	 *            报关单位名称
	 * @return
	 */
	public Unit findUnitByName(String unitName) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from Unit a where a.name=? ";
		paramters.add(unitName);
		List list = find(hsql, paramters.toArray());
		if (list.isEmpty())
			return null;
		return (Unit) list.get(0);
	}

	/**
	 * 根据编码查找报关单位资料
	 * 
	 * @param code报关单位编码
	 * @return
	 * @author wss
	 */
	public Unit findUnitByCode(String code) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from Unit a where a.code=? ";
		paramters.add(code);
		List list = find(hsql, paramters.toArray());
		if (list.isEmpty())
			return null;
		return (Unit) list.get(0);
	}

	public List findUnit() {
		String hsql = "select a from Unit a";
		return this.find(hsql);
	}

	/**
	 * 检查各个模块的特殊报关单头中的合同协议栏位是否存在给定的协议号
	 * 
	 * @param request
	 * @param emsNo
	 *            要判断的协议好
	 * @param projectType
	 *            模块类型
	 * @return
	 */
	public Boolean isExitSpecialCustomsContractNo(String emsNo,
			Integer projectType) {
		System.out.println("--projectType:" + projectType);
		List paramters = new ArrayList();
		String table = "CustomsDeclaration";
		if (projectType == ProjectType.BCS) {
			table = "BcsCustomsDeclaration";
		} else if (projectType == ProjectType.DZSC) {
			table = "DzscCustomsDeclaration";
		}
		String hsql = "select a from " + table + " a where a.company.id=? "
				+ "  and a.effective=? and a.contract=? "
				+ " and a.impExpType in (?,?,?) ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(true);
		paramters.add(emsNo);
		paramters.add(ImpExpType.EQUIPMENT_IMPORT);
		paramters.add(ImpExpType.BACK_PORT_REPAIR);
		paramters.add(ImpExpType.EQUIPMENT_BACK_PORT);

		System.out.println("hsql----fix-----------------" + hsql);
		List list = find(hsql, paramters.toArray());
		if (list.isEmpty())
			return false;
		else
			return true;

	}

	/**
	 * 根据给定的合同协议，查在各个模块的特殊报关单头中资料
	 * 
	 * @param emsNo
	 *            合同协议
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List getSpecialCustomsContractNo(String emsNo, int index,
			int length, String property, Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		List arrayList = new ArrayList();
		String table = "";
		for (int i = 0; i < 3; i++) {
			parameters.clear();
			if (i == 0)
				table = "CustomsDeclaration";// 电子帐册
			else if (i == 1)
				table = "DzscCustomsDeclaration";// 电子手册
			else
				table = "BcsCustomsDeclaration";// 纸质手册
			String hsql = "select a from " + table + " a where a.company.id=? "
					+ "  and a.effective=? and a.contract=? "
					+ " and a.impExpType in (?,?,?) ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(true);
			parameters.add(emsNo);
			parameters.add(ImpExpType.EQUIPMENT_IMPORT);
			parameters.add(ImpExpType.BACK_PORT_REPAIR);
			parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);

			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  " + property + " like ?  ";
					parameters.add(value + "%");
				} else {
					hsql += " and  " + property + " = ?  ";
					parameters.add(value);
				}
			}

			List list = this.find(hsql, parameters.toArray());
			if (!list.isEmpty())
				arrayList.addAll(list);
		}
		return arrayList;

	}

	public List findBaseEmsImgseqNum(String name, String complexCode,
			BaseEmsHead head) {
		List rlist = new ArrayList();
		String hsql = " select a.seqNum from  ContractImg a  where a.company.id=? "
				+ " and a.contract.id=?   and a.name like ? and a.complex.code=? ";
		String hsql1 = "select a.seqNum from  EmsHeadH2kImg a  where a.company.id=?  "
				+ " and a.emsHeadH2k.id=?  and a.name like ?  and a.complex.code=? ";
		String hsql2 = "select a.seqNum from  DzscEmsImgBill a  where a.company.id=? "
				+ " and a.dzscEmsPorHead.id=?  and a.name like ?  and a.complex.code=? ";
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), head.getId(),
				"%" + name + "%", complexCode });
		if (list.size() > 0) {
			rlist.addAll(list);
			return rlist;
		}

		List list1 = this.find(hsql1, new Object[] {
				CommonUtils.getCompany().getId(), head.getId(),
				"%" + name + "%", complexCode });
		if (list1.size() > 0) {
			rlist.addAll(list1);
			return rlist;
		}

		List list2 = this.find(hsql2, new Object[] {
				CommonUtils.getCompany().getId(), head.getId(),
				"%" + name + "%", complexCode });
		if (list2.size() > 0) {
			rlist.addAll(list2);
			return rlist;
		}
		return rlist;
	}

	public List findBaseEmsImg(BaseEmsHead head) {
		List rlist = new ArrayList();
		String hsql = " select a.seqNum,a.name,a.complex from  ContractImg a  where a.company.id=? "
				+ " and a.contract.declareState=?  and a.contract.id=? ";
		String hsql1 = "select a.seqNum ,a.name,a.complex from  EmsHeadH2kImg a  where a.company.id=?  "
				+ " and a.emsHeadH2k.declareState=?  and a.emsHeadH2k.id=?  ";
		String hsql2 = "select a.seqNum,a.name,a.complex from  DzscEmsImgBill a  where a.company.id=? "
				+ " and a.dzscEmsPorHead.declareState=?   and a.dzscEmsPorHead.id=?";
		List list = this.find(hsql, new Object[] {
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE,
				head.getId() });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setSeqNum((Integer) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setComplex((Complex) objs[2]);
			rlist.add(eimg);
		}
		List list1 = this.find(hsql1, new Object[] {
				CommonUtils.getCompany().getId(), DeclareState.PROCESS_EXE,
				head.getId() });
		for (int i = 0; i < list1.size(); i++) {
			Object[] objs = (Object[]) list1.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setSeqNum((Integer) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setComplex((Complex) objs[2]);
			rlist.add(eimg);
		}
		List list2 = this.find(hsql2,
				new Object[] { CommonUtils.getCompany().getId(),
						DzscState.EXECUTE, head.getId() });
		for (int i = 0; i < list2.size(); i++) {
			Object[] objs = (Object[]) list2.get(i);
			TempEmsImg eimg = new TempEmsImg();
			eimg.setSeqNum((Integer) objs[0]);
			eimg.setCusName((String) objs[1]);
			eimg.setComplex((Complex) objs[2]);
			rlist.add(eimg);
		}
		return rlist;
	}

	/**
	 * 查找国内购买发票号码
	 * 
	 * @return
	 */
	public List findInvoiceNo() {
		String hsql = "select a.invoiceNo from CasInvoice a where a.company.id=? ";
		return this.find(hsql, CommonUtils.getCompany().getId());
	}

	/**
	 * 获得在单据中客户供应商 转厂单据
	 * 
	 * @param sBillType
	 *            单据类型
	 * @param billCode
	 *            单据类型下的单据代号
	 */
	public List findScmCocToFpt(String billCode) {
		int sBillType = SBillType.MATERIEL_IN;
		// 1004:结转进口
		// 1106:结转料件退货单
		// 2102:结转出口
		// 2009:结转成品退货单
		if ("1004".equals(billCode)) {
			sBillType = SBillType.MATERIEL_IN;
		} else if ("1106".equals(billCode)) {
			sBillType = SBillType.MATERIEL_OUT;
		} else if ("2102".equals(billCode)) {
			sBillType = SBillType.PRODUCE_OUT;
		} else if ("2009".equals(billCode)) {
			sBillType = SBillType.PRODUCE_IN;
		}
		String billMaster = BillUtil
				.getBillMasterTableNameByBillType(sBillType);
		if (billMaster == null) {
			return new ArrayList();
		}
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.scmCoc from " + billMaster
				+ " a where a.isValid= ? " + " and a.company.id= ? "
				+ " and a.billType.code = ? ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billCode);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Fpt == 转厂单据
	 * 
	 * @param scmCocId
	 *            客户id
	 * @param sBillType
	 *            单据类型
	 * @param billCode
	 *            单据类型下的单据代号
	 * @return 不是全部转厂的单据商品商品信息
	 */
	public List findBillMasterIsAvailabilityToFpt(String scmCocId,
			String billCode, String envelopNo, Date beginDate, Date endDate) {
		int sBillType = SBillType.MATERIEL_IN;
		// 1004:结转进口
		// 1106:结转料件退货单
		// 2102:结转出口
		// 2009:结转成品退货单
		if ("1004".equals(billCode)) {
			sBillType = SBillType.MATERIEL_IN;
		} else if ("1106".equals(billCode)) {
			sBillType = SBillType.MATERIEL_OUT;
		} else if ("2102".equals(billCode)) {
			sBillType = SBillType.PRODUCE_OUT;
		} else if ("2009".equals(billCode)) {
			sBillType = SBillType.PRODUCE_IN;
		}
		// String billMaster = BillUtil
		// .getBillMasterTableNameByBillType(sBillType);
		String billDetail = BillUtil
				.getBillDetailTableNameByBillType(sBillType);
		if (billDetail == null) {
			return new ArrayList();
		}
		// System.out.println("billMaster------------" + billMaster);
		// System.out.println("scmCocId------------" + scmCocId);
		// System.out.println("billCode------------" + billCode);
		// String hsql = "select a from "
		// + billMaster
		// + " a where a.isValid= ? and "
		// + " a.company.id= ? and a.scmCoc.id = ? and a.billType.code = ? "
		// + " and a.envelopNo=? "
		// + " and a.id not in ( select b.billMasterId "
		// + " from MakeFptBill b "
		// + " where b.company.id=? ) ";
		// return this.find(hsql, new Object[] { true,
		// CommonUtils.getCompany().getId(), scmCocId, billCode,
		// envelopNo, CommonUtils.getCompany().getId() });
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.billMaster from "
				+ billDetail
				+ " a where a.billMaster.isValid= ? "
				+ " and a.billMaster.company.id= ? "
				+ " and a.billMaster.scmCoc.id = ?"
				+ " and a.billMaster.billType.code = ? "
				+ " and (a.isTransferFactoryBill=? or a.isTransferFactoryBill is null) ";
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(scmCocId);
		parameters.add(billCode);
		parameters.add(false);
		if (envelopNo != null && !"".equals(envelopNo.trim())) {
			hsql += " and ( a.billMaster.envelopNo=? "
					+ " or  a.billMaster.envelopNo=? "
					+ " or a.billMaster.envelopNo is null )";
			parameters.add(envelopNo.trim());
			parameters.add("");
		} else {
			hsql += " and (a.billMaster.envelopNo=? "
					+ " or a.billMaster.envelopNo is null )";
			parameters.add("");
		}
		if (beginDate != null) {
			hsql += " and a.billMaster.validDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.billMaster.validDate<=?";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}

		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 查找导入文件列顺序
	 * 
	 * @param tableName
	 * @return
	 */
	public List<ImportDataOrder> findImportDataOrder(String tableName) {
		String hsql = "select a from ImportDataOrder a "
				+ "where tableName = ?";
		return this.find(hsql, tableName);
	}

	/**
	 * 保存导入文件列顺序
	 * 
	 * @param list
	 */
	public void batchUpdateDataOrder(List<ImportDataOrder> list) {
		this.batchSaveOrUpdate(list);
	}

	/**
	 * 批量保存实际报关资料
	 * 
	 * @param list
	 */
	public void batchUpdateStatCusNameRelationHsn(
			List<StatCusNameRelationHsn> list) {
		this.batchSaveOrUpdate(list);
	}

	/**
	 * 分页查询实际报关商品
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<StatCusNameRelationHsn> findStatCusNameRelationHsn(int index,
			int length, String property, Object value, boolean isLike,
			String materielType) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from StatCusNameRelationHsn a  "
				+ "where a.materielType = ? and a.company.id=? ";

		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}

		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 分页查询对应关系
	 * 
	 * @param ptNo
	 *            物料号
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return
	 */
	public List<FactoryAndFactualCustomsRalation> findFactoryAndFactualCustomsRalation(
			int index, int length, String property, Object value,
			boolean isLike, String materielType) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from FactoryAndFactualCustomsRalation a  "
				+ "left join fetch a.statCusNameRelationHsn b "
				+ "left join fetch a.materiel c "
				+ "where a.statCusNameRelationHsn.materielType = ? and a.company.id=? ";

		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				paramters.add(value);
			}
		}

		return super.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 删除实际报关资料
	 * 
	 * @param list
	 */
	public void deleteStatCusNameRelationHsns(List<StatCusNameRelationHsn> list) {
		super.deleteAll(list);
	}

	/**
	 * 根据表头Id返回单据表头
	 * 
	 * @param id
	 * @param billType
	 * @return
	 */
	public BillMaster findBillMaster(String id, int billType) {
		String tableName = BillUtil.getBillMasterTableNameByBillType(billType);
		String hsql = "select a from " + tableName + " a where a.id = ?";
		List list = this.find(hsql, id);
		if (list != null && list.size() != 0) {
			return (BillMaster) list.get(0);
		} else {
			return null;
		}
	}

	// 本代码汇率用于海关帐中转为人民币使用
	// wss:2010.04.33修改
	public Double findCurrRateByM(String currcode, Date validDate) // 显示本币为美元的汇率表
	{
		List list = this.find(
				"select a from CurrRate a where a.company.id = ? "
						+ " and a.curr1.code=? and a.curr.code=? "
						+ " and a.createDate <= ? " // wss:2010.04.30新加
						// 报关单生效日期前的
						+ "order by a.createDate desc", // 用时间降序排列
				new Object[] { CommonUtils.getCompany().getId(), currcode,
						"142", validDate });
		if (list.size() != 0) {
			CurrRate cr = (CurrRate) list.get(0);
			System.out.println("wss cr.getRate = " + cr.getRate());
			return cr.getRate() == null ? 1.0 : cr.getRate();
		} else {
			return 1.0;// 没有的话就返回1
		}

	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 TFB == transfer factory bill 转厂单据
	 * 
	 * @param scmCocId
	 *            客户id
	 * @param sBillType
	 *            单据类型
	 * @param billCode
	 *            单据类型下的单据代号
	 * @param begin
	 *            单据开始日期
	 * @param end
	 *            单据结束日期
	 * @return 不是全部转厂的单据商品商品信息
	 */
	public List findBillMasterIsAvailabilityToTFB(String scmCocId,
			int sBillType, String billCode, String envelopNo, Date begin,
			Date end) {
		String billMaster = BillUtil
				.getBillMasterTableNameByBillType(sBillType);
		if (billMaster == null) {
			return new ArrayList();
		}
		List params = new ArrayList();
		params.add(true);
		params.add(CommonUtils.getCompany().getId());
		params.add(billCode);
		params.add(begin);
		params.add(end);
		params.add(CommonUtils.getCompany().getId());
		String hsql = "select a from " + billMaster
				+ " a where a.isValid= ? and "
				+ " a.company.id= ? and a.billType.code = ? "
				+ " and a.validDate >= ?" + " and a.validDate <= ?"
				+ " and a.id not in ( select b.billMasterId "
				+ " from MakeTransferFactoryBill b "
				+ " where b.company.id=? ) ";
		if (CommonUtils.notEmpty(envelopNo)) {
			params.add(envelopNo);
			hsql += " and a.envelopNo=? ";
		}
		if (CommonUtils.notEmpty(scmCocId)) {
			params.add(scmCocId);
			hsql += " and a.scmCoc.id = ?";
		}

		return this.find(hsql, params.toArray());
	}

	/**
	 * 删除期初单据
	 * 
	 * @param year
	 *            截止日期
	 */
	public void deleteBillByInit(String typeCode, String billNo) {
		String billMaster = " BillMasterMateriel ";
		String billDetail = " BillDetailMateriel ";
		if ("1001".equals(typeCode) || "1002".equals(typeCode)
				|| "1020".equals(typeCode)) {// 料件
			billMaster = " BillMasterMateriel ";
			billDetail = " BillDetailMateriel ";
		} else if ("2001".equals(typeCode)) {// 成品
			billMaster = " BillMasterProduct ";
			billDetail = " BillDetailProduct ";
		} else if ("4009".equals(typeCode)) {// 半成品
			billMaster = " BillMasterHalfProduct ";
			billDetail = " BillDetailHalfProduct ";
		} else if ("5002".equals(typeCode)) {// 残次品
			billMaster = " BillMasterRemainProduct ";
			billDetail = " BillDetailRemainProduct ";
		} else if ("6003".equals(typeCode)) {// 边角料
			billMaster = " BillMasterLeftoverMateriel ";
			billDetail = " BillDetailLeftoverMateriel ";
		}
		this.batchUpdateOrDelete("delete from " + billDetail
				+ " m where m.company.id = ?" + "  and m.billMaster.id in "
				+ "(select a.id from " + billMaster
				+ " a where a.billNo = ? and a.billType.code = ? ) ",
				new Object[] { CommonUtils.getCompany().getId(), billNo,
						typeCode });
		this.batchUpdateOrDelete("delete from " + billMaster
				+ " m where m.company.id = ? and m.billNo = ? "
				+ "  and m.billType.id in "
				+ "(select a.id from BillType a  where a.code = ? ) ",
				new Object[] { CommonUtils.getCompany().getId(), billNo,
						typeCode });
	}

	/**
	 * 删除所有海关统计资料(料件统计报表)
	 * 
	 * @param year
	 *            截止日期
	 */
	public void deleteBillInit(String year) {
		this.batchUpdateOrDelete(
				"delete from BillInit m where m.company.id = ? and m.year=? ",
				new Object[] { CommonUtils.getCompany().getId(), year });
	}

	/**
	 * 删除所有海关统计资料(料件统计报表)
	 * 
	 * @param year
	 *            截止日期
	 */
	public List findBillInit(String year, String typeCode) {
		return this
				.find("from BillInit m where m.company.id = ? and m.year=? and m.typeCode = ? ",
						new Object[] { CommonUtils.getCompany().getId(), year,
								typeCode });
	}

	/**
	 * 查找单据名称规格单位名和商品总数量
	 */
	public List findDeliveryToPlantFromBillDzsc(String billCode, String ptName,
			String ptSpec, String unit, Date date) {
		String key = ptName + "/" + ptSpec + "/" + unit;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select djt.hsName,djt.hsSpec,djt.hsUnit.name,sum(djt.hsAmount) from BillDetailProduct djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.billMaster.billType.code=? "
				+ "and djt.billMaster.isValid=? "
				+ "and djt.billMaster.validDate >=? "
				+ "and djt.billMaster.validDate <=? "
				+ "and djt.hsName||'/'||djt.hsSpec||'/'||djt.hsUnit.name in "
				+ "(select distinct  htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name "
				+ "from DzscEmsBomBill htbom where htbom.dzscEmsExgBill.dzscEmsPorHead.declareState in(?) "
				+ "and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=?) "
				+ "group by djt.hsName,djt.hsSpec,djt.hsUnit.name";
		System.out.println("hsql=" + hsql);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billCode);
		parameters.add(true);
		// 处理时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date endDate = cal.getTime();
		int value = cal.getActualMinimum(Calendar.MONTH);
		cal.set(Calendar.MONTH, value);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = cal.getTime();

		parameters.add(CommonUtils.getBeginDate(beginDate));

		parameters.add(CommonUtils.getEndDate(date));
		// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(DzscState.EXECUTE);
		// parameters.add(CommonUtils.getEndDate(date));
		// parameters.add(DzscState.CHECK_CANCEL);
		parameters.add(key);
		// parameters.add(ptName);
		// parameters.add(ptSpec);
		// parameters.add(unit);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找单据名称规格单位名和商品总数量
	 */
	public List findDeliveryToPlantFromBillBcs(String billCode, String ptName,
			String ptSpec, String unit, Date date) {
		String key = ptName + "/" + ptSpec + "/" + unit;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select djt.hsName,djt.hsSpec,djt.hsUnit.name,sum(djt.hsAmount),djt.billMaster.scmCoc.name from BillDetailProduct djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.billMaster.billType.code=? "
				+ "and djt.billMaster.isValid=? "
				+ "and djt.billMaster.validDate >=? "
				+ "and djt.billMaster.validDate <=? "
				+ "and djt.hsName||'/'||djt.hsSpec||'/'||djt.hsUnit.name in "
				+ "(select distinct htbom.contractExg.name||'/'||htbom.contractExg.spec||'/'||htbom.contractExg.unit.name "
				+ "from ContractBom htbom where htbom.contractExg.contract.declareState in(?) "
				+ "and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=?) "
				+ "group by djt.hsName,djt.hsSpec,djt.hsUnit.name,djt.billMaster.scmCoc.name";
		System.out.println("hsql=" + hsql);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(billCode);
		parameters.add(true);
		// 处理时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date endDate = cal.getTime();
		int value = cal.getActualMinimum(Calendar.MONTH);
		cal.set(Calendar.MONTH, value);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = cal.getTime();

		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(date));
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(key);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找出口报关单名称规格单位名和商品总数量
	 */
	public List findDeliveryToPlant(String ptName, String ptSpec, String unit,
			Date date) {
		String key = ptName + "/" + ptSpec + "/" + unit;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select bgdt.commName,bgdt.commSpec,bgdt.unit.name,sum(bgdt.commAmount) from DzscCustomsDeclarationCommInfo bgdt "
				+ "where bgdt.baseCustomsDeclaration.company.id=? "
				+ "and bgdt.baseCustomsDeclaration.effective=? "
				+ "and bgdt.baseCustomsDeclaration.impExpType=? "
				+ "and bgdt.baseCustomsDeclaration.impExpDate >=? "
				+ "and bgdt.baseCustomsDeclaration.impExpDate <=? "
				+ "and bgdt.commName||'/'||bgdt.commSpec||'/'||bgdt.unit.name in "
				+ "(select distinct htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name from "
				+ "DzscEmsBomBill htbom where (htbom.dzscEmsExgBill.dzscEmsPorHead.declareState =? "
				+ "or (htbom.dzscEmsExgBill.dzscEmsPorHead.declareState =? and htbom.dzscEmsExgBill.dzscEmsPorHead.endDate<=?)) "
				+ "and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=?) "
				// + "and htbom.name=? "
				// + "and htbom.spec=? "
				// + "and htbom.unit.name=? "
				// +
				// "and htbom.dzscEmsExgBill.dzscEmsPorHead.declareState in(?,?) "
				// +
				// "and (htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name)="
				// + "(bgdt.commName||'/'||bgdt.commSpec||'/'||bgdt.unit.name) "
				// + "and htbom.dzscEmsExgBill.name=bgdt.commName "
				// + "and htbom.dzscEmsExgBill.spec=bgdt.commSpec "
				// + "and htbom.dzscEmsExgBill.unit.name=bgdt.unit.name "
				+ "group by bgdt.commName,bgdt.commSpec,bgdt.unit.name";
		System.out.println("hsql=" + hsql);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		// 处理时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date endDate = cal.getTime();
		int value = cal.getActualMinimum(Calendar.MONTH);
		cal.set(Calendar.MONTH, value);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = cal.getTime();

		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(DzscState.EXECUTE);
		parameters.add(DzscState.CHECK_CANCEL);
		parameters.add(CommonUtils.getEndDate(endDate));
		parameters.add(key);
		// parameters.add(ptName);
		// parameters.add(ptSpec);
		// parameters.add(unit);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找出口报关单名称规格单位名和商品总数量
	 */
	public List findDeliveryToPlantDZHSC(String ptName, String ptSpec,
			String unit, Date date) {
		String key = ptName + "/" + ptSpec + "/" + unit;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select bgdt.commName,bgdt.commSpec,bgdt.unit.name,sum(bgdt.commAmount),bgdt.baseCustomsDeclaration.customer.name from BcsCustomsDeclarationCommInfo bgdt "
				+ "where bgdt.baseCustomsDeclaration.company.id=? "
				+ "and bgdt.baseCustomsDeclaration.effective=? "
				+ "and bgdt.baseCustomsDeclaration.impExpType=? "
				+ "and bgdt.baseCustomsDeclaration.impExpDate >=? "
				+ "and bgdt.baseCustomsDeclaration.impExpDate <=? "
				+ "and bgdt.commName||'/'||bgdt.commSpec||'/'||bgdt.unit.name in "
				+ "(select distinct htbom.contractExg.name||'/'||htbom.contractExg.spec||'/'||htbom.contractExg.unit.name from "
				+ "ContractBom htbom where (htbom.contractExg.contract.declareState =? "
				+ "or (htbom.contractExg.contract.declareState =? and htbom.contractExg.contract.endDate<=?)) "
				+ "and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=?) "
				+ "group by bgdt.commName,bgdt.commSpec,bgdt.unit.name,bgdt.baseCustomsDeclaration.customer.name";
		System.out.println("hsql=" + hsql);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		// 处理时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date endDate = cal.getTime();
		int value = cal.getActualMinimum(Calendar.MONTH);
		cal.set(Calendar.MONTH, value);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = cal.getTime();

		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(date));
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(DeclareState.CHANGING_CANCEL);
		parameters.add(CommonUtils.getEndDate(date));
		parameters.add(key);
		// parameters.add(ptName);
		// parameters.add(ptSpec);
		// parameters.add(unit);
		return this.find(hsql, parameters.toArray());
	}

	// select bgdt.commName,bgdt.commSpec,bgdt.unit.name,sum(bgdt.commAmount)
	// from DzscCustomsDeclarationCommInfo bgdt
	// where bgdt.baseCustomsDeclaration.effective=true
	// and bgdt.baseCustomsDeclaration.impExpType=5
	// and bgdt.commName||'/'||bgdt.commSpec||'/'||bgdt.unit.name in
	// (select distinct
	// htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name
	// from DzscEmsBomBill htbom where
	// htbom.dzscEmsExgBill.dzscEmsPorHead.declareState in(2,4)
	// and
	// htbom.name||'/'||htbom.spec||'/'||htbom.unit.name='电镀锌铁片或电镀锌钢片/宽<600MM/厚0.15-3MM/有镀层/千克')
	// group by bgdt.commName,bgdt.commSpec,bgdt.unit.name
	//
	/**
	 * 根据单据类型查找出单具体的单据数量
	 * 
	 * @param ptName
	 * @param ptSpec
	 * @param unitName
	 * @param date
	 * @param billType
	 * @return
	 */
	public List findBcsAmountFromBill(String key, Date date, String billCode) {
		// String key = ptName + "/" + ptSpec + "/" + unitName;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select djt.hsName,djt.hsSpec,djt.hsUnit.name,sum(djt.hsAmount) from BillDetailProduct djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.hsName||'/'||djt.hsSpec||'/'||djt.hsUnit.name||'/'||djt.billMaster.scmCoc.name =? "
				+ "and djt.billMaster.billType.code=? "
				+ "and djt.billMaster.validDate>=? "
				+ "and djt.billMaster.validDate<=? "
				+ "and djt.billMaster.isValid=? "
				+ "group by djt.hsName,djt.hsSpec,djt.hsUnit.name";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(key);
		parameters.add(billCode);

		// 处理时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date endDate = cal.getTime();
		int value = cal.getActualMinimum(Calendar.MONTH);
		cal.set(Calendar.MONTH, value);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = cal.getTime();

		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(date));
		parameters.add(true);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据单据类型查找出单具体的单据数量
	 * 
	 * @param ptName
	 * @param ptSpec
	 * @param unitName
	 * @param date
	 * @param billType
	 * @return
	 */
	public List findAmountFromBill(String ptName, String ptSpec,
			String unitName, Date date, String billCode) {
		String key = ptName + "/" + ptSpec + "/" + unitName;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select djt.hsName,djt.hsSpec,djt.hsUnit.name,sum(djt.hsAmount) from BillDetailProduct djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.hsName||'/'||djt.hsSpec||'/'||djt.hsUnit.name =? "
				+ "and djt.billMaster.billType.code=? "
				+ "and djt.billMaster.validDate>=? "
				+ "and djt.billMaster.validDate<=? "
				+ "and djt.billMaster.isValid=? "
				+ "group by djt.hsName,djt.hsSpec,djt.hsUnit.name";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(key);
		parameters.add(billCode);

		// 处理时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date endDate = cal.getTime();
		int value = cal.getActualMinimum(Calendar.MONTH);
		cal.set(Calendar.MONTH, value);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginDate = cal.getTime();

		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getEndDate(date));
		parameters.add(true);
		return this.find(hsql, parameters.toArray());
	}

	// /**
	// * 根据料件的名称，规格，单位名称查找出所对应成品的合同表头中的最大开始日期
	// *
	// * @param ptName
	// * @param ptSpec
	// * @param unitName
	// * @return
	// */
	// public Date findBeginDateFromDjt(String ptName, String ptSpec,
	// String unitName) {
	// String key = ptName + "/" + ptSpec + "/" + unitName;
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql =
	// "select max(htbom.dzscEmsExgBill.dzscEmsPorHead.beginDate) from DzscEmsBomBill htbom "
	// + "where htbom.dzscEmsExgBill.dzscEmsPorHead.company.id=? "
	// +
	// "and htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name=? ";
	// // + "and htbom.name=? "
	// // + "and htbom.spec=? "
	// // + "and htbom.unit.name=? ";
	// parameters.add(CommonUtils.getCompany().getId());
	// parameters.add(key);
	// // parameters.add(ptName);
	// // parameters.add(ptSpec);
	// // parameters.add(unitName);
	// List list = this.find(hsql, parameters.toArray());
	// System.out.println("dateList.size()=" + list.size());
	// Date maxBeginDate = (Date) list.get(0);
	// return maxBeginDate;
	// }

	/**
	 * 根据料件的名称，规格，单位名称查找出所对应成品的合同表头中的最大合同号
	 * 
	 * @param ptName
	 * @param ptSpec
	 * @param unitName
	 * @return
	 */
	public String findMaxEmsNoFromDjt(String ptName, String ptSpec,
			String unitName) {
		String key = ptName + "/" + ptSpec + "/" + unitName;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct htbom.dzscEmsExgBill.dzscEmsPorHead.emsNo,htbom.dzscEmsExgBill.dzscEmsPorHead.beginDate from DzscEmsBomBill htbom "
				+ "where htbom.dzscEmsExgBill.dzscEmsPorHead.company.id=? "
				+ "and htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name=? "
				+ " order by htbom.dzscEmsExgBill.dzscEmsPorHead.beginDate desc";
		// + "and htbom.name=? "
		// + "and htbom.spec=? "
		// + "and htbom.unit.name=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(key);
		// parameters.add(ptName);
		// parameters.add(ptSpec);
		// parameters.add(unitName);
		List list = this.find(hsql, parameters.toArray());
		if (list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			if (objs[0] != null) {
				return objs[0].toString().trim();
			}
		}
		return null;
	}

	/**
	 * 根据料件的名称，规格，单位名称查找出所对应成品的合同表头中的最大合同号(电子化手册)
	 * 
	 * @param ptName
	 * @param ptSpec
	 * @param unitName
	 * @return
	 */
	/**
	 * 通过最大的开始日期在合同中找出单耗和损耗
	 */
	public String findMaxImpContractNoFromDzsc(String exgKey, String imgKey) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select htbom.dzscEmsExgBill.dzscEmsPorHead.ieContractNo from DzscEmsBomBill htbom "
				+ " where htbom.dzscEmsExgBill.dzscEmsPorHead.company.id=? "
				+ " and htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name=? "
				+ " and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=? "
				+ " and htbom.dzscEmsExgBill.dzscEmsPorHead.declareState in(?) "
				+ " order by htbom.dzscEmsExgBill.dzscEmsPorHead.ieContractNo  desc";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(exgKey);
		parameters.add(imgKey);
		parameters.add(DzscState.EXECUTE);
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0) == null ? null : list.get(0).toString();
		}
		return null;
	}

	/**
	 * 根据料件的名称，规格，单位名称查找出所对应成品的合同表头中的最大合同号(电子化手册)
	 * 
	 * @param ptName
	 * @param ptSpec
	 * @param unitName
	 * @return
	 */
	public String findMaxImpContractNoFromBcs(String exgKey) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct htbom.contractExg.contract.impContractNo from ContractBom htbom "
				+ " where htbom.contractExg.contract.company.id=?"
				+ " and htbom.contractExg.contract.declareState= ?  "
				+ " and htbom.contractExg.name||'/'||htbom.contractExg.spec||'/'||htbom.contractExg.unit.name=?"
				// + " and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=? "
				+ " order by htbom.contractExg.contract.impContractNo desc";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(exgKey);
		// parameters.add(imgKey);
		System.out.println("hsqlF7" + hsql);
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0) == null ? null : list.get(0).toString();
		}
		return null;
	}

	/**
	 * 2014-3-19根据成品的名称，规格，单位名称查找出所对应成品的合同表头中的最大合同号(电子化手册)
	 * 
	 * @param ptName
	 * @param ptSpec
	 * @param unitName
	 * @return
	 */
	public String findMaxExgContractNoFromBcs(String exgKey) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct htbom.contractExg.contract.impContractNo from ContractBom htbom "
				+ " where htbom.contractExg.contract.company.id=?"
				+ " and htbom.contractExg.contract.declareState= ?  "
				+ " and htbom.contractExg.name||'/'||htbom.contractExg.spec||'/'||htbom.contractExg.unit.name=?"
				// + " and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=? "
				+ " order by htbom.contractExg.contract.impContractNo desc";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(exgKey);
		// parameters.add(imgKey);
		System.out.println("hsqlF7" + hsql);
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0) == null ? null : list.get(0).toString();
		}
		return null;
	}

	/**
	 * 通过最大的开始日期在合同中找出单耗和损耗
	 */
	public List findUnitWareAndWareFromHtBomByMaxEmsNo(String ptName,
			String ptSpec, String unitName, String exgName, String exgSpce,
			String exgUnit, String emsNo) {
		String key1 = ptName + "/" + ptSpec + "/" + unitName;
		String key2 = exgName + "/" + exgSpce + "/" + exgUnit;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select htbom.unitWare,htbom.ware,htbom.dzscEmsExgBill.dzscEmsPorHead.emsNo from DzscEmsBomBill htbom "
				+ "where htbom.dzscEmsExgBill.dzscEmsPorHead.company.id=? "
				+ "and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=? "
				+ "and htbom.dzscEmsExgBill.name||'/'||htbom.dzscEmsExgBill.spec||'/'||htbom.dzscEmsExgBill.unit.name=? "
				+ "and htbom.dzscEmsExgBill.dzscEmsPorHead.declareState in(?) "
				+ "and htbom.dzscEmsExgBill.dzscEmsPorHead.emsNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(key1);
		parameters.add(key2);
		parameters.add(DzscState.EXECUTE);
		parameters.add(emsNo);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 通过最大的开始日期在合同中找出单耗和损耗(电子化手册)
	 */
	public List findUnitWareAndWareFromHtBomByMaxEmsNoDZHSC(String ptName,
			String ptSpec, String unitName, String exgName, String exgSpce,
			String exgUnit, String impContractNo) {
		String key1 = ptName + "/" + ptSpec + "/" + unitName;
		// System.out.println("ssskey1" + key1);
		String key2 = exgName + "/" + exgSpce + "/" + exgUnit;
		// System.out.println("ssskey2" + key2);
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select htbom.unitWaste,htbom.waste,htbom.contractExg.contract.emsNo from ContractBom htbom "
				+ "where htbom.contractExg.contract.company.id=? "
				+ "and htbom.name||'/'||htbom.spec||'/'||htbom.unit.name=? "
				// + "and htbom.name=? "
				// + "and htbom.spec=? "
				// + "and htbom.unit.name=? "
				+ "and htbom.contractExg.name||'/'||htbom.contractExg.spec||'/'||htbom.contractExg.unit.name=? "
				// + "and htbom.dzscEmsExgBill.name=? "
				// + "and htbom.dzscEmsExgBill.spec=? "
				// + "and htbom.dzscEmsExgBill.unit.name=? "declareState
				+ "and htbom.contractExg.contract.declareState in(?) "
				+ "and htbom.contractExg.contract.impContractNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(key1);
		parameters.add(key2);
		// parameters.add(ptName);
		// parameters.add(ptSpec);
		// parameters.add(unitName);
		// parameters.add(exgName);
		// parameters.add(exgSpce);
		// parameters.add(exgUnit);
		parameters.add(DeclareState.PROCESS_EXE);
		// parameters.add(DzscState.CHECK_CANCEL);
		parameters.add(impContractNo);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * ptNo:料号 sBillType:仓库类型 codes : 单据类型
	 * 
	 * 根据工厂料号查询外发进仓单据
	 * 
	 * @return
	 */
	public List<BillDetail> findBillDetailByptPart(String ptNo, int sBillType,
			int[] codes, ConsignQueryCondition condition) {
		String tableName = BillUtil.getBillDetailTableNameByBillType(sBillType);
		if (tableName == null) {
			return new ArrayList<BillDetail>();
		}
		String hsql = " select a from " + tableName
				+ " as a  where a.ptPart=? and a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(ptNo);
		parameters.add(CommonUtils.getCompany());
		if (codes.length > 0) {
			hsql += "and a.billMaster.billType.code in (";
			for (int i = 0, size = codes.length; i < size; i++) {
				if (i == 0) {
					hsql += codes[i];
				} else {
					hsql += "," + codes[i];
				}
			}
			hsql += ")";
		}
		if (condition != null) {
			// 客户
			if (condition.getScmCoc() != null) {
				hsql += " and a.billMaster.scmCoc.code=?";
				parameters.add(condition.getScmCoc().getCode());
			}
			// 仓库
			if (condition.getWareSet() != null) {
				hsql += " and a.wareSet.code=?";
				parameters.add(condition.getWareSet().getCode());
			}
			// 时间
			if (condition.getIsCompute() && condition.getEndDate() != null) {
				hsql += " and a.billMaster.validDate<=?";
				parameters.add(condition.getEndDate());
			} else {
				if (condition.getStartDate() != null
						&& condition.getEndDate() == null) {
					hsql += " and a.billMaster.validDate>=?";
					parameters.add(condition.getStartDate());
				}
				if (condition.getStartDate() == null
						&& condition.getEndDate() != null) {
					hsql += " and a.billMaster.validDate<=?";
					parameters.add(condition.getEndDate());
				}
				if (condition.getStartDate() != null
						&& condition.getEndDate() != null) {
					hsql += " and a.billMaster.validDate>=? and a.billMaster.validDate<=?";
					parameters.add(condition.getStartDate());
					parameters.add(condition.getEndDate());
				}
			}
		}
		System.out.println("hsql=" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询子件对应的父件
	 * 
	 * @param ptNo
	 * @return
	 */
	public List findParentNoByPentNo(String ptNo) {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(ptNo);
		parameters.add(CommonUtils.getCompany());
		return this
				.find("select distinct a from EnterpriseBomManage a where a.compentNo=? and a.company= ?",
						parameters.toArray());
	}

	/**
	 * 料件进口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesMaterielIn(String ptNumber, WareSet wareSet,
			Date vaillDate, Boolean isValid) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(djt.ptAmount),djt.ptPart,djt.wareSet.name from BillDetailMateriel djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.billMaster.billType.name in(?,?,?,?,?,?,?,?,?,?,?,?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("料件期初单");
		parameters.add("直接进口");
		parameters.add("结转进口");
		parameters.add("国内购买");
		parameters.add("车间返回");
		parameters.add("料件盘盈单");
		parameters.add("受托加工进库");
		parameters.add("其他来源");
		parameters.add("料件转仓入库");
		parameters.add("海关征税进口");
		parameters.add("外发加工退回料件");
		parameters.add("外发加工返回料件");
		if (isValid == true) {
			hsql += "and djt.billMaster.isValid=? ";
			parameters.add(isValid);
		}
		if (ptNumber != null && !ptNumber.equals("")) {
			hsql += "and djt.ptPart=? ";
			parameters.add(ptNumber);
		}
		if (wareSet != null) {
			hsql += "and djt.wareSet=? ";
			parameters.add(wareSet);
		}
		if (vaillDate != null) {
			hsql += "and djt.billMaster.validDate<=? ";
			parameters.add(vaillDate);
		}
		hsql += "group by djt.ptPart,djt.wareSet.name";
		System.out.println("hsql=" + hsql);
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			if (objs[0] != null) {
				return Double.valueOf(objs[0].toString());
			}
		}
		return null;
	}

	/**
	 * 料件出口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesMaterielOut(String ptNumber, WareSet wareSet,
			Date vaillDate, Boolean isValid) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(djt.ptAmount),djt.ptPart,djt.wareSet.name from BillDetailMateriel djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.billMaster.billType.name in(?,?,?,?,?,?,?,?,?,?,?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("车间领用");
		parameters.add("料件退换");
		parameters.add("料件复出");
		parameters.add("料件盘亏单");
		parameters.add("料件转仓出库");
		parameters.add("结转料件退货单");
		parameters.add("其他料件退货单");
		parameters.add("其他使用");
		parameters.add("受托加工领用");
		parameters.add("外发加工出库");
		parameters.add("受托加工退回料件");
		if (isValid == true) {
			hsql += "and djt.billMaster.isValid=? ";
			parameters.add(isValid);
		}
		if (ptNumber != null && !ptNumber.equals("")) {
			hsql += "and djt.ptPart=? ";
			parameters.add(ptNumber);
		}
		if (wareSet != null) {
			hsql += "and djt.wareSet=? ";
			parameters.add(wareSet);
		}
		if (vaillDate != null) {
			hsql += "and djt.billMaster.validDate<=? ";
			parameters.add(vaillDate);
		}
		hsql += "group by djt.ptPart,djt.wareSet.name";
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			if (objs[0] != null) {
				return Double.valueOf(objs[0].toString());
			}
		}
		return null;
	}

	/**
	 * 成品进口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesProduceIn(String ptNumber, WareSet wareSet,
			Date vaillDate, Boolean isValid) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(djt.ptAmount),djt.ptPart,djt.wareSet.name from BillDetailProduct djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.billMaster.billType.name in(?,?,?,?,?,?,?,?,?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("成品期初单");
		parameters.add("车间入库");
		parameters.add("退厂返工");
		parameters.add("成品盘盈单");
		parameters.add("成品转仓入库");
		parameters.add("受托加工车间入库");
		parameters.add("其他成品退货单");
		parameters.add("结转成品退货单");
		parameters.add("受托加工退回成品");
		if (isValid == true) {
			hsql += "and djt.billMaster.isValid=? ";
			parameters.add(isValid);
		}
		if (ptNumber != null && !ptNumber.equals("")) {
			hsql += "and djt.ptPart=? ";
			parameters.add(ptNumber);
		}
		if (wareSet != null) {
			hsql += "and djt.wareSet=? ";
			parameters.add(wareSet);
		}
		if (vaillDate != null) {
			hsql += "and djt.billMaster.validDate<=? ";
			parameters.add(vaillDate);
		}
		hsql += "group by djt.ptPart,djt.wareSet.name";
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			if (objs[0] != null) {
				return Double.valueOf(objs[0].toString());
			}
		}
		return null;
	}

	/**
	 * 成品出口的工厂总数量
	 * 
	 * @param ptNumber
	 * @param wareSet
	 * @param vaillDate
	 * @return
	 */
	public Double findWarehousesProduceOut(String ptNumber, WareSet wareSet,
			Date vaillDate, Boolean isValid) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(djt.ptAmount),djt.ptPart,djt.wareSet.name from BillDetailProduct djt "
				+ "where djt.billMaster.company.id=? "
				+ "and djt.billMaster.billType.name in(?,?,?,?,?,?,?,?,?,?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("直接出口");
		parameters.add("结转出口");
		parameters.add("返回车间");
		parameters.add("返工复出");
		parameters.add("成品盘亏单");
		parameters.add("海关批准内销");
		parameters.add("其他内销");
		parameters.add("其他处理");
		parameters.add("成品转仓出库");
		parameters.add("受托加工返回");
		if (isValid == true) {
			hsql += "and djt.billMaster.isValid=? ";
			parameters.add(isValid);
		}
		if (ptNumber != null && !ptNumber.equals("")) {
			hsql += "and djt.ptPart=? ";
			parameters.add(ptNumber);
		}
		if (wareSet != null) {
			hsql += "and djt.wareSet=? ";
			parameters.add(wareSet);
		}
		if (vaillDate != null) {
			hsql += "and djt.billMaster.validDate<=? ";
			parameters.add(vaillDate);
		}
		hsql += "group by djt.ptPart,djt.wareSet.name";
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			Object[] objs = (Object[]) list.get(0);
			if (objs[0] != null) {
				return Double.valueOf(objs[0].toString());
			}
		}
		return null;
	}

	// public List findBillTypeByFactoryName(String factoryName) {
	// if (factoryName == null || factoryName.trim().equals("")) {
	// return new ArrayList();
	// }
	// List result = this.find(
	// "select a from BillType a where a.factoryBillName=?  ",
	// new Object[] { factoryName });
	//
	// return result;
	// }

	public List findBomByCode(String PtPart) {
		if (PtPart == null || PtPart.trim().equals("")) {
			return new ArrayList();
		}
		List result = this
				.find("select a from BcsCustomsBomDetail a where a.bcsCustomsBomExg.complex.code=?",
						new Object[] { PtPart });
		return result;
	}

	/**
	 * 返回成品单据对应的BOM
	 * 
	 * @return
	 */
	public List<MaterialBomDetail> getProductBom(BillDetail bill) {
		return this
				.find("select a from MaterialBomDetail a "
						+ " left outer join fetch a.version "
						+ " left outer join fetch a.materiel "
						+ " where a.company.id=? and a.version.master.materiel.ptNo=? and a.version.version=?",
						new Object[] { CommonUtils.getCompany().getId(),
								bill.getPtPart(), bill.getVersion() });

	}

	/**
	 * 返回料件
	 */
	/**
	 * 返回成品单据对应的BOM
	 * 
	 * @return
	 */
	public List<String> getMaterialBomProduct(String startPart, String endPart,
			String code) {
		return this
				.find("select distinct a.version.master.materiel.ptNo from MaterialBomDetail a"
						+ " where a.company.id=? and a.ptNo>=? and a.ptNo<=? and a.version.master.materiel.scmCoi.code=?",
						new Object[] { CommonUtils.getCompany().getId(),
								startPart, endPart, code });
	}

	/**
	 * 返回料件对应的报关资料
	 * 
	 * @author 石小凯
	 */
	public List<FactoryAndFactualCustomsRalation> getHsMaterialBom(
			MaterialBomDetail bom) {
		return this
				.find("select a from FactoryAndFactualCustomsRalation a"
						+ " where a.company.id=? and a.materiel.ptNo=? and a.materiel.ptVersion=?",
						new Object[] { CommonUtils.getCompany().getId(),
								bom.getMateriel().getPtNo(),
								bom.getMateriel().getPtVersion() });
	}

	/**
	 * wss:2010.04.22试使用
	 * 
	 * @return 设备使用情况
	 */
	public List findBySql(String sql, Map backTypeMap, List objParams) {

		System.out.println("wss 测试 有没有执行到casDao 中的 findBySql方法？");

		return this
				.findListBySQL(sql, backTypeMap, objParams.toArray(), -1, -1);
	}

	/**
	 * @author 石小凯
	 * @return 返回料件报关对应信息：名称、规格、单项用量
	 */
	public Map<String, FactoryAndFactualCustomsRalation> getUnitConvert() {
		Map<String, FactoryAndFactualCustomsRalation> map = new HashMap<String, FactoryAndFactualCustomsRalation>();
		List<FactoryAndFactualCustomsRalation> list = this.find(
				" from FactoryAndFactualCustomsRalation as a"
						+ " where a.company.id=?", new Object[] { CommonUtils
						.getCompany().getId() });
		System.out.println("list.size()=" + list.size());
		for (int i = 0; i < list.size(); i++) {
			FactoryAndFactualCustomsRalation ralation = (FactoryAndFactualCustomsRalation) list
					.get(i);
			String key = ralation.getMateriel().getPtNo() == null ? ""
					: ralation.getMateriel().getPtNo();
			map.put(key, ralation);
		}
		return map;
	}

	/**
	 * 返回所有料件料号
	 * 
	 * @return hcl
	 */
	public List getAllMaterialPtNo() {
		return this.find("select distinct a.ptNo from Materiel a");
	}

	/**
	 * 返回在产品期初单（折算来自关成品的）
	 * 
	 * @author wss
	 */
	public List findInitBillMasterMadeBySemiProduct(String billNo) {
		String hsql = " from BillMasterMateriel a where a.company.id = ? "
				+ " and a.billType.code = ? " + " and a.billNo = ? "
				+ " and a.notice = ? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				"1002", billNo, "折算来自半成品" });// 1002在产品期初单
	}

	/**
	 * 删除原有的 在产品期初单（折算来自半成品的）
	 * 
	 * @author wss
	 */
	public void deleteInitBillMadeBySemiProduct(String billNo) {
		String typeCode = "1002";
		String notice = "折算来自半成品";
		// 1.删单据体
		this.batchUpdateOrDelete(
				"delete from BillDetailMateriel a where a.company.id = ? "
						+ " and a.billMaster.id in "
						+ " (select b.id from BillMasterMateriel b "
						+ " where b.billType.id in "
						+ " (select c.id from BillType c where c.code = ? ) "
						+ " and b.billNo = ? " + " and b.notice = ? ) ",
				new Object[] { CommonUtils.getCompany().getId(), typeCode,
						billNo, notice });
		// 2.删单据头
		this.batchUpdateOrDelete(
				"delete from BillMasterMateriel a where a.company.id = ? "
						+ " and a.billType.id in "
						+ " (select b.id from BillType b where b.code = ? )"
						+ " and a.billNo = ? " + " and a.notice = ? ",
				new Object[] { CommonUtils.getCompany().getId(), typeCode,
						billNo, notice });

	}

	/**
	 * 根据号码查仓库
	 * 
	 * @return 仓库
	 * @author wss
	 */
	public WareSet findWareSetByCode(String code) {
		List ls = this
				.find("select a from WareSet a where a.company.id = ? and a.code = ?",
						new Object[] { CommonUtils.getCompany().getId(), code });
		if (ls != null && ls.size() > 0) {
			return (WareSet) ls.get(0);
		}
		return null;
	}

	/**
	 * 根据类型查找Materiel
	 */
	public List<Materiel> findMaterielByScmCoiCode(String code) {

		String hsql = " select a from Materiel as a "
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " left join fetch a.calUnit "
				+ " where a.scmCoi.coiProperty = ? and a.company.id = ? "
				+ " order by a.ptNo";
		List<Object> paramters = new ArrayList<Object>();

		paramters.add(code);
		paramters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 查找所有的对应关系
	 * 
	 * @return
	 * @author wss
	 */
	public List<FactoryAndFactualCustomsRalation> findAllFactoryAndFactualCustomsRalation() {
		List<FactoryAndFactualCustomsRalation> list = this
				.find(" from FactoryAndFactualCustomsRalation as a where a.company.id=?",
						new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找所有转厂单据
	 */
	public List findTransFactoryBill() {
		List list = this.find(
				" from TransferFactoryBill as a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 根据单据号检查单据号是否存在重复
	 * 
	 * @param FactoryBillNo
	 * @return
	 */
	public List checkMulpTransFactoryBillFactoryBillNo(String factoryBillNo) {
		return this
				.find("select a from TransferFactoryBill a "
						+ " where a.company.id = ? and a.transferFactoryBillNo=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								factoryBillNo });
	}

	/**
	 * 获得关封单据所有数据
	 */
	public List findCustomsEnvelopBill(boolean isImpExpGoods) {

		String hsql = " select a from CustomsEnvelopBill as a where a.company.id=? "
				+ " and a.isImpExpGoods=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(isImpExpGoods);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找对应关系(按工厂料号排序)
	 * 
	 * @param materielType物料类型
	 * @return
	 * @author wss2010.09.29
	 */
	public List<FactoryAndFactualCustomsRalation> findFactoryAndFactualCustomsRalationByMaterielType(
			String materielType) {
		String hsql = " select a from FactoryAndFactualCustomsRalation a "
				+ " left join fetch a.materiel m "
				+ " left join fetch a.statCusNameRelationHsn "
				+ " where a.company.id= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (materielType != null) {
			hsql += " and a.statCusNameRelationHsn.materielType = ? ";
			parameters.add(materielType);
		}
		hsql += " order by a.materiel.ptNo";

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据料号查找对应关系
	 * 
	 * @return
	 * @author wss
	 */
	public FactoryAndFactualCustomsRalation findFactoryAndFactualCustomsRalationByPtNo(
			String ptNo) {
		List<FactoryAndFactualCustomsRalation> list = this
				.find(" select a from FactoryAndFactualCustomsRalation  a "
						+ " left join fetch a.materiel "
						+ " left join fetch a.statCusNameRelationHsn"
						+ " where a.company = ? " + " and a.materiel.ptNo = ? ",
						new Object[] { CommonUtils.getCompany(), ptNo });
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取半成品期初单4009单据体
	 * 
	 * @param masterId
	 * @author wss2010.10.08
	 */
	public List<BillDetail> getBillDetail4009ByBillMasterId(String masterId) {
		return this.find(" from  BillDetailHalfProduct a"
				+ " left join fetch a.billMaster "
				+ " where a.billMaster.id = ? " + " and a.company.id = ? ",
				new Object[] { masterId, CommonUtils.getCompany().getId() });

	}

	/**
	 * 查找对应关系中的报关资料
	 * 
	 * @param materielType
	 * @return
	 * @author wss2010.10.20
	 */
	public List findStatCusNameRelationHsnInUsed(String materielType) {
		String hsql = " select distinct a.statCusNameRelationHsn from  FactoryAndFactualCustomsRalation a "
				+ " where a.company.id= ?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (materielType != null) {
			hsql += " and a.statCusNameRelationHsn.materielType= ? ";
			parameters.add(materielType);
		}

		System.out.println("wss hsql = " + hsql);
		return this.find(hsql, parameters.toArray());

	}

	public List findMaterielByFactoryAndFactualCustomsRalation(
			FactoryAndFactualCustomsRalation old) {
		String hsql = "select a from FactoryAndFactualCustomsRalation a where a.materiel.ptNo=? and "
				+ " a.statCusNameRelationHsn.complex.code=? and a.statCusNameRelationHsn.cusName=? "
				+ " and a.statCusNameRelationHsn.cusSpec=? and a.statCusNameRelationHsn.cusUnit.name=? "
				+ " and a.statCusNameRelationHsn.emsNo=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(old.getMateriel().getPtNo());
		parameters.add(old.getStatCusNameRelationHsn().getComplex().getCode());
		parameters.add(old.getStatCusNameRelationHsn().getCusName());
		parameters.add(old.getStatCusNameRelationHsn().getCusSpec());
		parameters.add(old.getStatCusNameRelationHsn().getCusUnit().getName());
		parameters.add(old.getStatCusNameRelationHsn().getEmsNo());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询所有【工厂和实际客户对应】的【料号+报告编码+报关名称+报关规格+报关单位+手册号】组成的key值，去除重复key
	 * 
	 * @return
	 */
	public Set<String> findAllFactoryAndFactualCustomsRalationKey() {
		String hsql = "select a.materiel.ptNo,a.statCusNameRelationHsn.complex.code,"
				+ " a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec,"
				+ " a.statCusNameRelationHsn.cusUnit.name,a.statCusNameRelationHsn.emsNo"
				+ " from FactoryAndFactualCustomsRalation a "
				+ " where a.company.id = ? "
				+ " group by a.materiel.ptNo,"
				+ " a.statCusNameRelationHsn.complex.code, a.statCusNameRelationHsn.cusName,"
				+ " a.statCusNameRelationHsn.cusSpec, a.statCusNameRelationHsn.cusUnit.name,"
				+ " a.statCusNameRelationHsn.emsNo";
		List<Object[]> list = this.find(hsql, new Object[] { CommonUtils
				.getCompany().getId() });

		// 把list转化为key的set以便查找比对
		int size = list == null ? 0 : list.size();
		Set<String> rs = new HashSet<String>(size);
		String key = null;
		Object[] os = null;
		for (int i = 0; i < size; i++) {
			os = list.get(i);
			key = (os[0] == null ? "" : os[0].toString()) + ","
					+ (os[1] == null ? "" : os[1].toString()) + ","
					+ (os[2] == null ? "" : os[2].toString()) + ","
					+ (os[3] == null ? "" : os[3].toString()) + ","
					+ (os[4] == null ? "" : os[4].toString()) + ","
					+ (os[5] == null ? "" : os[5].toString());
			rs.add(key);
		}

		return rs;
	}

	/**
	 * 查询所有【工厂和实际客户对应】的【料号+报告编码+报关名称+报关规格+报关单位+手册号】组成的key值，去除重复key
	 * 
	 * @return
	 */
	public Set<String> findAllFactoryAndFactualCustomsRalationKeyNoEmsNo() {
		String hsql = "select a.materiel.ptNo,a.statCusNameRelationHsn.complex.code,"
				+ " a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec,"
				+ " a.statCusNameRelationHsn.cusUnit.name,a.statCusNameRelationHsn.emsNo"
				+ " from FactoryAndFactualCustomsRalation a "
				+ " where a.company.id = ? "
				+ " group by a.materiel.ptNo,"
				+ " a.statCusNameRelationHsn.complex.code, a.statCusNameRelationHsn.cusName,"
				+ " a.statCusNameRelationHsn.cusSpec, a.statCusNameRelationHsn.cusUnit.name,"
				+ " a.statCusNameRelationHsn.emsNo";
		List<Object[]> list = this.find(hsql, new Object[] { CommonUtils
				.getCompany().getId() });

		// 把list转化为key的set以便查找比对
		int size = list == null ? 0 : list.size();
		Set<String> rs = new HashSet<String>(size);
		String key = null;
		Object[] os = null;
		for (int i = 0; i < size; i++) {
			os = list.get(i);
			key = (os[0] == null ? "" : os[0].toString()) + ","
					+ (os[1] == null ? "" : os[1].toString()) + ","
					+ (os[2] == null ? "" : os[2].toString()) + ","
					+ (os[3] == null ? "" : os[3].toString()) + ","
					+ (os[4] == null ? "" : os[4].toString());
			rs.add(key);
		}

		return rs;
	}

	/**
	 * 查询所有【工厂和实际客户对应】的【料号+报告编码+报关名称+报关规格+报关单位+手册号】组成的key值，去除重复key （品基）
	 * 
	 * @return
	 */
	public Set<String> findAllFactoryAndFactualCustomsRalationKeyPJ() {
		String hsql = "select a.materiel.ptNo,a.statCusNameRelationHsn.complex.code,"
				+ " a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec,"
				+ " a.statCusNameRelationHsn.cusUnit.name,a.statCusNameRelationHsn.emsNo,"
				+ " a.scmCoc.id"
				+ " from FactoryAndFactualCustomsRalation a "
				+ " where a.company.id = ? "
				+ " group by a.materiel.ptNo,"
				+ " a.statCusNameRelationHsn.complex.code, a.statCusNameRelationHsn.cusName,"
				+ " a.statCusNameRelationHsn.cusSpec, a.statCusNameRelationHsn.cusUnit.name,"
				+ " a.statCusNameRelationHsn.emsNo,a.scmCoc.id";
		List<Object[]> list = this.find(hsql, new Object[] { CommonUtils
				.getCompany().getId() });

		// 把list转化为key的set以便查找比对
		int size = list == null ? 0 : list.size();
		Set<String> rs = new HashSet<String>(size);
		String key = null;
		Object[] os = null;
		for (int i = 0; i < size; i++) {
			os = list.get(i);
			key = (os[0] == null ? "" : os[0].toString()) + ","
					+ (os[1] == null ? "" : os[1].toString()) + ","
					+ (os[2] == null ? "" : os[2].toString()) + ","
					+ (os[3] == null ? "" : os[3].toString()) + ","
					+ (os[4] == null ? "" : os[4].toString()) + ","
					+ (os[5] == null ? "" : os[5].toString()) + ","
					+ (os[6] == null ? "" : os[6].toString());
			rs.add(key);
		}

		return rs;
	}

	public List<FactoryAndFactualCustomsRalation> findFactoryAndFactualCustomsRalation(
			Set<String> ptNoList) {
		StringBuilder hsql = new StringBuilder(
				"  select a from  FactoryAndFactualCustomsRalation a "
						+ "  left join  fetch a.materiel left join     fetch  a.statCusNameRelationHsn   "
						+ "      where a.company.id=?  ");

		if (ptNoList != null && ptNoList.size() > 0) {
			hsql.append(" and a.materiel.ptNo in ('00000000000000000000");
			for (String ptNo : ptNoList) {
				hsql.append("','" + ptNo);
			}

			hsql.append("')");
		}

		return this.find(hsql.toString(), new Object[] { CommonUtils
				.getCompany().getId() });

	}

	/**
	 * 批量修改不用cache
	 */
	public int batchSaveOrUpdate(final List list, final ProgressInfo info) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						int size = list.size();
						logger.info("开始批量保存!一共" + size + "记录。");
						session.setCacheMode(CacheMode.IGNORE);
						int m = 0;
						List tlsit = new ArrayList();
						for (int i = 0; i < size; i++, m++) {
							if (m == 1000) {
								session.flush();
								session.clear();
								for (int j = 0; j < tlsit.size(); j++) {
									session.evict(tlsit.get(j));// 从缓存中删除，为了节省内存
								}
								tlsit.clear();
								m = 0;
								System.gc();
							}
							Object obj = list.get(i);
							if (obj instanceof BaseScmEntity) {
								if (((BaseScmEntity) obj).getCompany() == null) {
									((BaseScmEntity) obj)
											.setCompany(CommonUtils
													.getCompany());
								}
							}
							session.saveOrUpdate(obj);
							tlsit.add(obj);

						}
						session.flush();
						session.clear();
						for (int j = 0; j < tlsit.size(); j++) {
							session.evict(tlsit.get(j));// 从缓存中删除，为了节省内存
						}
						tlsit.clear();
						m = 0;
						System.gc();
						logger.info("批量保存完成!");
						info.setCurrentNum(size);
						return size;
					}
				});
	}
}