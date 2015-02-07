/*
 * Created on 2004-7-29
 *getImg
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.logic;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillMasterMateriel;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.CarryForwardInfo;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.cas.entity.CheckBalanceConvertMateriel;
import com.bestway.bcus.cas.entity.CheckBalanceOfCustom;
import com.bestway.bcus.cas.entity.ConsignQueryInfo;
import com.bestway.bcus.cas.entity.DebtDetail;
import com.bestway.bcus.cas.entity.DebtMaster;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.ImportExportInfo;
import com.bestway.bcus.cas.entity.MarginDetail;
import com.bestway.bcus.cas.entity.MarginMaster;
import com.bestway.bcus.cas.entity.MoneyDetail;
import com.bestway.bcus.cas.entity.MoneyMaster;
import com.bestway.bcus.cas.entity.MonthStorage;
import com.bestway.bcus.cas.entity.MonthStorage2;
import com.bestway.bcus.cas.entity.StatCusNameRelation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.StoreInfo;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.cas.entity.TempDetailInvoice;
import com.bestway.bcus.cas.entity.TempMonthStorage;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.entity.TempRelationCommInfo;
import com.bestway.bcus.cas.entity.TempStatCusNameRelation;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.invoice.entity.InvoiceAndBillCorresponding;
import com.bestway.bcus.cas.invoice.entity.TempEmsImg;
import com.bestway.bcus.cas.invoice.entity.TempSumInvoiceInfo;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BswBeansUtils;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.ConsignQueryCondition;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SBillType;
import com.bestway.common.constant.WareType;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.customs.common.entity.BaseEmsHead;

/**
 * 海关帐logic类，用来写复杂的业务逻辑方法 2009年10月15日 贺巍补充注释
 * 
 * @author ?
 * 
 */
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
public class CasLogic {
	private static final Log logger = LogFactory.getLog(BalanceInfoLogic.class);
	/**
	 * 海关帐Dao
	 */
	private CasDao casDao = null;
	/** 物料管理Dao */
	private MaterialManageDao materialManageDao = null;

	/**
	 * 取得海关帐Dao
	 * 
	 * @return 海关帐Dao
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设计海关帐Dao
	 * 
	 * @param casDao
	 *            海关帐Dao
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/** 保存FactoryAndFactualCustomsRalation */
	public void saveFactoryAndFactualCustomsRalation(
			FactoryAndFactualCustomsRalation ffc, String mType,
			Boolean isUpdateMateriel, Boolean isUpdateHsNum, String oldEmsNo) {
		casDao.saveFactoryAndFactualCustomsRalation(ffc, mType,
				isUpdateMateriel, isUpdateHsNum, oldEmsNo);
	}

	/** 保存FactoryAndFactualCustomsRalation */
	public void saveFactoryAndFactualCustomsRalation(Boolean isWriteBackM,
			FactoryAndFactualCustomsRalation ffc) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date createDate = (java.sql.Date.valueOf(bartDateFormat
				.format(new Date())));// 修改时间
		ffc.setCreateDate(createDate);
		casDao.saveFactoryAndFactualCustomsRalation(isWriteBackM, ffc);
	}

	/**
	 * 单据中心右键 修改企业物料
	 * 
	 * @param ffc
	 *            修改后的对应关系
	 * @param materielType
	 *            对应关系 物料类型
	 * @param isUpdateDetail
	 *            是否更新单据
	 * @param isUpdateHsNum
	 *            是否更新单据的折算报关数量
	 * 
	 * @author wss
	 */
	public void modifyMaterielForFactoryAndFactualCustomsRalation(
			FactoryAndFactualCustomsRalation ffc, String materielType,
			String updateType) {
		// 1.更新工厂物料
		List<EnterpriseMaterial> enterpriseList = this.casDao
				.findEnterpriseMaterialByMaterielId(ffc.getMateriel());
		System.out.println("wss : 正在返写工厂物料");
		for (EnterpriseMaterial enterpriseMaterial : enterpriseList) {
			enterpriseMaterial.setPtPrice(ffc.getMateriel().getPtPrice());
			enterpriseMaterial.setCalUnit(ffc.getMateriel().getCalUnit());
			enterpriseMaterial.setPtNetWeight(ffc.getMateriel()
					.getPtNetWeight());

			// 反写工厂物料
			this.casDao.saveOrUpdate(enterpriseMaterial);
		}

		// 2.更新单据
		if (CommonUtils.notEmpty(updateType)) {
			String billDetailTableName = BillUtil
					.getBillDetailTableNameByMaterielType(materielType);
			List paras = new ArrayList();
			String hsql = "select a from " + billDetailTableName + " as a "
					+ " where a.ptPart = ? " + " and a.company= ? and ( 1=2";

			paras.add(ffc.getMateriel().getPtNo());
			paras.add(CommonUtils.getCompany());

			// 查询未生效的单据
			if (updateType.contains("0")) {
				hsql += " or a.billMaster.isValid = false";
			}

			// 查询已生效未记账单据
			if (updateType.contains("1")) {
				hsql += " or (a.billMaster.isValid = true and (a.billMaster.keepAccounts = false or a.billMaster.keepAccounts is null))";
			}

			// 查询已记账单据
			if (updateType.contains("2")) {
				hsql += " or a.billMaster.keepAccounts = true";
			}
			hsql += " )";

			if (CommonUtils.notEmpty(ffc.getMateriel().getFactoryName())) {
				hsql += " and a.ptName=? ";
				paras.add(ffc.getMateriel().getFactoryName());
			}

			if (CommonUtils.notEmpty(ffc.getMateriel().getFactorySpec())) {
				hsql += " and a.ptSpec=? ";
				paras.add(ffc.getMateriel().getFactorySpec());
			}

			if (CommonUtils
					.notEmpty(ffc.getStatCusNameRelationHsn().getEmsNo())) {
				hsql += " and a.emsNo=? ";
				paras.add(ffc.getStatCusNameRelationHsn().getEmsNo());
			}

			List<BillDetail> billDetailList = this.casDao.find(hsql,
					paras.toArray());
			System.out.println("wss: hsql:" + hsql);
			System.out.println("wss:  billDetailList.size()="
					+ billDetailList.size());

			for (BillDetail billDetail : billDetailList) {
				billDetail.setPtPrice(ffc.getMateriel().getPtPrice());
				billDetail.setPtUnit(ffc.getMateriel().getCalUnit());
				if (billDetail.getPtAmount() == null) {
					billDetail.setPtAmount(new Double("0.0"));
				}
				billDetail.setNetWeight(CommonUtils.getDoubleExceptNull(ffc
						.getMateriel().getPtNetWeight())
						* CommonUtils.getDoubleExceptNull(billDetail
								.getPtAmount()));
				System.out.println("wss 正在更新的单据号为：" + billDetail.getBillNo());
				this.casDao.saveOrUpdate(billDetail);
			}

			paras.clear();

			// 更改折算系数的情况
			hsql = "select a from " + billDetailTableName + " as a "
					+ " where a.ptPart = ? " + " and a.company= ? and ( 1=2";

			paras.add(ffc.getMateriel().getPtNo());
			paras.add(CommonUtils.getCompany());

			// 查询未生效的单据
			if (updateType.contains("0")) {
				hsql += " or a.billMaster.isValid = false";
			}

			// 查询已生效未记账单据
			if (updateType.contains("1")) {
				hsql += " or (a.billMaster.isValid = true and (a.billMaster.keepAccounts = false or a.billMaster.keepAccounts is null))";
			}

			// 查询已记账单据
			if (updateType.contains("2")) {
				hsql += " or a.billMaster.keepAccounts = true";
			}
			hsql += " )";

			if (CommonUtils.notEmpty(ffc.getMateriel().getFactoryName())) {
				hsql += " and a.ptName=? ";
				paras.add(ffc.getMateriel().getFactoryName());
			}

			if (CommonUtils.notEmpty(ffc.getMateriel().getFactorySpec())) {
				hsql += " and a.ptSpec=? ";
				paras.add(ffc.getMateriel().getFactorySpec());
			}

			if (CommonUtils
					.notEmpty(ffc.getStatCusNameRelationHsn().getEmsNo())) {
				hsql += " and a.emsNo=? ";
				paras.add(ffc.getStatCusNameRelationHsn().getEmsNo());
			}

			if (ffc.getStatCusNameRelationHsn().getComplex() != null) {
				hsql += " and a.complex=? ";
				paras.add(ffc.getStatCusNameRelationHsn().getComplex());
			}

			if (CommonUtils.notEmpty(ffc.getStatCusNameRelationHsn()
					.getCusName())) {
				hsql += " and a.hsName=? ";
				paras.add(ffc.getStatCusNameRelationHsn().getCusName());
			}

			if (CommonUtils.notEmpty(ffc.getStatCusNameRelationHsn()
					.getCusSpec())) {
				hsql += " and a.hsSpec=? ";
				paras.add(ffc.getStatCusNameRelationHsn().getCusSpec());
			}

			if (ffc.getStatCusNameRelationHsn().getCusUnit() != null) {
				hsql += " and a.hsUnit=? ";
				paras.add(ffc.getStatCusNameRelationHsn().getCusUnit());
			}

			billDetailList = this.casDao.find(hsql, paras.toArray());
			System.out.println("hcl: hsql:" + hsql);
			System.out.println("hcl:  billDetailList.size()="
					+ billDetailList.size());

			for (BillDetail billDetail : billDetailList) {
				if (billDetail.getPtAmount() == null) {
					billDetail.setPtAmount(new Double("0.0"));
				}
				billDetail.setHsPrice(CommonUtils
						.getDoubleExceptNull(billDetail.getPtPrice())
						/ ((ffc.getUnitConvert() == null || ffc
								.getUnitConvert() == 0) ? 1 : ffc
								.getUnitConvert()));
				if (billDetail.getPtPrice() == null) {
					billDetail.setPtPrice(new Double("0.0"));
				}

				if (billDetail.getPtAmount() == null) {
					billDetail.setPtAmount(new Double("0.0"));
				}

				// 更新单据中的 折算报关数量
				Double money = 0.0;
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

				// 更新单据中的 海关资料
				if (ffc.getStatCusNameRelationHsn() != null) {
					StatCusNameRelationHsn hsInfo = ffc
							.getStatCusNameRelationHsn();
					billDetail.setComplex(hsInfo.getComplex());
					billDetail.setHsName(hsInfo.getCusName());
					billDetail.setHsSpec(hsInfo.getCusSpec());
					billDetail.setHsUnit(hsInfo.getCusUnit());
				}
				System.out.println("hcl 正在更新的单据号为：" + billDetail.getBillNo());
				this.casDao.saveOrUpdate(billDetail);

			}
		}

		// 3.更新报关常用工厂物料
		this.casDao.saveOrUpdate(ffc.getMateriel());

		// 4.更新此对应关系
		this.casDao.saveOrUpdate(ffc);

	}

	/**
	 * 单据中心右键 修改企业物料(品基)
	 * 
	 * @param ffc
	 *            修改后的对应关系
	 * @param materielType
	 *            对应关系 物料类型
	 */
	public int modifyMaterielForFactoryAndFactualCustomsRalationPJ(
			String materielType) {
		int count = 0;
		// 1.查询a.ptName != null的单据
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		List paras = new ArrayList();
		String hsql = "select a from "
				+ billDetailTableName
				+ " as a "
				+ " where a.company= ?  and (a.hsName is null or a.hsAmount is null)";

		paras.add(CommonUtils.getCompany());
		List<BillDetail> billDetailList = this.casDao.find(hsql,
				paras.toArray());

		System.out.println("hsql:" + hsql);
		System.out.println("billDetailList.size()=" + billDetailList.size());

		// 2.查询对应关系
		Set<String> ptNoList = new HashSet<String>();
		for (int i = 0; i < billDetailList.size(); i++) {
			ptNoList.add(billDetailList.get(i).getPtPart());
		}
		List<FactoryAndFactualCustomsRalation> ffcList = casDao
				.findFactoryAndFactualCustomsRalation(ptNoList);
		Map<String, FactoryAndFactualCustomsRalation> ffcMap = new HashMap<String, FactoryAndFactualCustomsRalation>(
				ffcList.size());
		// key = ptNo + / + ScmCoc.id(料号 + /+ 客户供应商id)
		String key = "";
		for (FactoryAndFactualCustomsRalation ffcr : ffcList) {
			key = ffcr.getMateriel().getPtNo()
					+ "/"
					+ (ffcr.getScmCoc() == null ? "" : ffcr.getScmCoc().getId());
			ffcMap.put(key, ffcr);
		}

		// 3.更新单据
		// 3.1 根据单据中的 key(料号 + /+ 客户供应商id) 取 对应关系
		// 如果 对应关系 不为空 更新单据
		// 如果 对应关系 为空 执行 3.2
		// 3.2 根据单据中的 key(料号 + /) 取 对应关系
		// 如果 对应关系 不为空 更新单据
		// 如果 对应关系 为空 跳过
		key = "";
		FactoryAndFactualCustomsRalation ffc = null;
		for (BillDetail billDetail : billDetailList) {

			// key = bill.ptPart + / + bill.billMaster.ScmCoc.id(料号 + /+
			// 客户供应商id)
			key = billDetail.getPtPart()
					+ "/"
					+ (billDetail.getBillMaster().getScmCoc() == null ? ""
							: billDetail.getBillMaster().getScmCoc().getId());
			ffc = ffcMap.get(key);

			if (ffc == null) {
				key = billDetail.getPtPart() + "/";
				ffc = ffcMap.get(key);

				if (ffc == null) {

					continue;
				}
			}

			// 执行更新
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
			if (billDetail.getPtAmount() == null) {
				billDetail.setPtAmount(new Double("0.0"));
			}
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

			// 更新单据中的 折算报关数量
			Double money = 0.0;
			money = Double.parseDouble(billDetail.getPtPrice().toString())
					* Double.parseDouble(billDetail.getPtAmount().toString());
			billDetail.setUnitConvert(CommonUtils.getDoubleExceptNull(ffc
					.getUnitConvert()));
			billDetail.setMoney(money);
			billDetail.setHsAmount(CommonUtils.getDoubleExceptNull(billDetail
					.getPtAmount())
					* CommonUtils.getDoubleExceptNull(ffc.getUnitConvert()));

			// 更新单据中的 海关资料
			if (ffc.getStatCusNameRelationHsn() != null) {
				StatCusNameRelationHsn hsInfo = ffc.getStatCusNameRelationHsn();
				billDetail.setComplex(hsInfo.getComplex());
				billDetail.setHsName(hsInfo.getCusName());
				billDetail.setHsSpec(hsInfo.getCusSpec());
				billDetail.setHsUnit(hsInfo.getCusUnit());
			}
			// 更新统计
			count++;

		}

		// 批量更新单据
		this.casDao.batchSaveOrUpdate(billDetailList);

		return count;
	}

	/**
	 * 单据中心 更新报关资料
	 * 
	 * @param ffc
	 *            新的对应关系
	 * @param materielType
	 *            对应关系物料类型
	 * @param isUpdateDetail
	 *            是否更新单据
	 * @param isNoChangeHsCode
	 *            没有变更报关编码
	 * @param oldMOldParameter
	 *            最旧的物料资料
	 * @param oldCusOldParameter
	 *            最旧的报关资料
	 * @param newCusOldParameter
	 *            变更的报关编码原先的属性
	 * 
	 * @author wss:2010.06.25
	 */
	public void modifyCustomForFactoryAndFactualCustomsRalation(
			FactoryAndFactualCustomsRalation ffc, String materielType,
			Boolean isUpdateDetail, Boolean isChangeHsCode,
			String[] oldMOldParameter, String[] oldCusOldParameter,
			String[] newCusOldParameter) {

		// 1.更新单据
		if (isUpdateDetail) {
			String billDetailTableName = BillUtil
					.getBillDetailTableNameByMaterielType(materielType);
			// 装要更新报关单价、报关数量、折算比例的
			List<BillDetail> billDetailList = new ArrayList<BillDetail>();
			// 装不用更新报关单价、报关数量、折算比例的
			List<BillDetail> billDetailList2 = new ArrayList<BillDetail>();

			// 没有变换报关资料，还是那个，只是属性被修改了
			if (!isChangeHsCode) {

				// 找到含此报关资料单据 ，再更新
				String hsql = "select a from "
						+ billDetailTableName
						+ " as a "
						+ " left join fetch a.complex "
						+ " where a.complex.code = ? and a.company= ? and a.hsName=? and a.hsSpec=? "
						+ " and a.emsNo=? ";

				System.out.println("***wss oldCusOldParameter:"
						+ oldCusOldParameter[0] + oldCusOldParameter[1]
						+ oldCusOldParameter[2] + oldCusOldParameter[3]);
				billDetailList = this.casDao.find(hsql, new Object[] {
						oldCusOldParameter[0], CommonUtils.getCompany(),
						oldCusOldParameter[1], oldCusOldParameter[2],
						oldCusOldParameter[3] });
				System.out.println("wss 报关资料     没有    更换");
				System.out.println("wss hsql = " + hsql);
				System.out.println("wss:  billDetailList.size()="
						+ billDetailList.size());
			}

			// 变换了报关资料
			else {

				// 1找到含此工厂资料的单据，再更新
				String hsql = " select a from "
						+ billDetailTableName
						+ " as a "
						+ " left join fetch a.complex "
						+ "  where a.ptPart = ? and a.company= ? and a.ptName=? and a.ptSpec=? "
						+ " and a.emsNo=? ";
				System.out.println("****wss oldMOldParameter:"
						+ oldMOldParameter[0] + oldMOldParameter[1]
						+ oldMOldParameter[2] + oldMOldParameter[3]);
				billDetailList = this.casDao.find(hsql, new Object[] {
						oldMOldParameter[0], CommonUtils.getCompany(),
						oldMOldParameter[1], oldMOldParameter[2],
						oldMOldParameter[3] });
				;
				System.out.println("wss 报关资料       有   更换");
				System.out.println("wss hsql = " + hsql);
				System.out.println("wss:  billDetailList1.size()="
						+ billDetailList.size());

				// 2.找到含此报关资料的单据，再更新
				String hsql2 = " select a from "
						+ billDetailTableName
						+ " as a "
						+ " left join fetch a.complex "
						+ " where a.complex.code = ? and a.company= ? and a.hsName=? and a.hsSpec=? "
						+ " and a.emsNo=? ";
				System.out.println("****wss newCusOldParameter:"
						+ newCusOldParameter[0] + newCusOldParameter[1]
						+ newCusOldParameter[2] + newCusOldParameter[3]);
				billDetailList2 = this.casDao.find(hsql2, new Object[] {
						newCusOldParameter[0], CommonUtils.getCompany(),
						newCusOldParameter[1], newCusOldParameter[2],
						newCusOldParameter[3] });
				System.out.println("wss hsql2 = " + hsql2);
				System.out.println("wss:  billDetailList2.size()="
						+ billDetailList2.size());

			}

			// 更新相应的报关数量及报关单价
			for (BillDetail billDetail : billDetailList) {
				billDetail.setPtPrice(ffc.getMateriel().getPtPrice());
				billDetail.setPtUnit(ffc.getMateriel().getCalUnit());

				// 更新折算报关数量
				if (billDetail.getPtAmount() == null) {
					billDetail.setPtAmount(new Double("0.0"));
				}

				billDetail
						.setHsAmount(CommonUtils.getDoubleExceptNull(billDetail
								.getPtAmount())
								* CommonUtils.getDoubleExceptNull(ffc
										.getUnitConvert()));

				// 更新报关单价关单价
				billDetail.setHsPrice(CommonUtils
						.getDoubleExceptNull(billDetail.getPtPrice())
						/ ((ffc.getUnitConvert() == null || ffc
								.getUnitConvert() == 0) ? 1 : ffc
								.getUnitConvert()));

				if (billDetail.getPtPrice() == null) {
					billDetail.setPtPrice(new Double("0.0"));
				}

				if (billDetail.getPtAmount() == null) {
					billDetail.setPtAmount(new Double("0.0"));
				}

				// 更新 折算比例
				billDetail.setUnitConvert(CommonUtils.getDoubleExceptNull(ffc
						.getUnitConvert()));

			}

			billDetailList.addAll(billDetailList2);

			// 更新相关单据的报关资料
			for (BillDetail billDetail : billDetailList) {

				// 更新单据中的 海关资料
				if (ffc.getStatCusNameRelationHsn() != null) {
					StatCusNameRelationHsn hsInfo = ffc
							.getStatCusNameRelationHsn();
					billDetail.setComplex(hsInfo.getComplex());
					billDetail.setHsName(hsInfo.getCusName());
					billDetail.setHsSpec(hsInfo.getCusSpec());
					billDetail.setHsUnit(hsInfo.getCusUnit());
					billDetail.setEmsNo(hsInfo.getEmsNo());
				}

				System.out.println("wss 正在更新报关资料 其单据号为："
						+ billDetail.getBillMaster().getBillNo());
				this.casDao.saveOrUpdate(billDetail);
			}

		}
		// 4.更新此对应关系
		this.casDao.saveOrUpdate(ffc);
	}

	/**
	 * 委外进出仓帐查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryInfo(ConsignQueryCondition condition) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(condition.getStartDate());
		startDate.set(Calendar.HOUR, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		// 返回结果定义
		List<ConsignQueryInfo> result = new ArrayList<ConsignQueryInfo>();
		// 获取类型为料件，半成品的工厂物料主档 (子件 ====== 料件，半成品)
		List<String> names = new ArrayList<String>();
		names.add("1");// 1
		if (condition.getIsComputeBan()) {
			names.add("2");// 2
		}
		List<EnterpriseMaterial> ems = materialManageDao
				.findEnterpriseMaterialByType(names, condition);
		System.out.println("测试：ems size=" + ems.size());
		// 统计每个子件的外发情况
		for (EnterpriseMaterial em : ems) {
			System.out.println("-----------------------------------------");
			System.out.println("料号：" + em.getPtNo());
			// 料件结存
			Double jieYu = 0d;
			// 父件
			Map<String, Double> bomMap = new HashMap<String, Double>();
			// 存储查询单据
			List<BillDetail> bills = new ArrayList<BillDetail>();
			// 查询料件出入仓单据,进仓：外发加工出库,外发返修出库
			int[] codesM = { 1110, 1112 };
			List<BillDetail> listM = casDao.findBillDetailByptPart(
					em.getPtNo(), SBillType.MATERIEL_INOUT, codesM, condition);
			bills.addAll(listM);
			// //查询成品出入仓单据,进仓：外发加工车间入库
			// int[] codesP = {2013};
			// List<BillDetail> listP =
			// casDao.findBillDetailByptPart(SBillType.PRODUCE_INOUT, codesP);
			// bills.addAll(listP);
			// 查询半成品出入仓单据,进仓：委外加工出库,外发加工返修半成品,外发加工领料
			int[] codesH = { 4103, 4104, 4105 };
			List<BillDetail> listH = casDao.findBillDetailByptPart(
					em.getPtNo(), SBillType.HALF_PRODUCE_INOUT, codesH,
					condition);
			bills.addAll(listH);
			System.out.println("测试：bills size=" + bills.size());
			// 查询对应的父件，以及父件对应的单据
			List<EnterpriseBomManage> boms = casDao.findParentNoByPentNo(em
					.getPtNo());
			System.out.println("测试：boms size=" + boms.size());
			if (boms != null && boms.size() > 0) {
				for (EnterpriseBomManage bom : boms) {
					bomMap.put(bom.getParentNo(), bom.getUnitWare());
					int[] codesMO = { 1012, 1013 };
					List<BillDetail> listMO = casDao.findBillDetailByptPart(
							bom.getParentNo(), SBillType.MATERIEL_INOUT,
							codesMO, condition);
					bills.addAll(listMO);
					// 查询成品出入仓单据,进仓：外发加工车间入库
					int[] codesPO = { 2013 };
					List<BillDetail> listPO = casDao.findBillDetailByptPart(
							bom.getParentNo(), SBillType.PRODUCE_INOUT,
							codesPO, condition);
					bills.addAll(listPO);
					// 查询半成品出入仓单据,进仓：委外加工出库,外发加工返修半成品,外发加工领料
					int[] codesHO = { 4003 };
					List<BillDetail> listHO = casDao.findBillDetailByptPart(
							bom.getParentNo(), SBillType.HALF_PRODUCE_INOUT,
							codesHO, condition);
					bills.addAll(listHO);
				}
			}
			System.out.println("测试：bills size=" + bills.size());
			// 按日期排序
			if (bills == null || bills.size() == 0) {
				continue;
			}
			Collections.sort(bills, new Comparator<BillDetail>() {
				public int compare(BillDetail arg0, BillDetail arg1) {
					// TODO Auto-generated method stub
					return arg0.getBillMaster().getValidDate()
							.compareTo(arg1.getBillMaster().getValidDate());
				}
			});
			// 统计
			// 上期结存
			ConsignQueryInfo lastInfo = new ConsignQueryInfo();
			boolean isAdd = true;
			for (BillDetail b : bills) {
				Calendar billDate = Calendar.getInstance();
				billDate.setTime(b.getBillMaster().getValidDate());
				billDate.set(Calendar.HOUR, 0);
				billDate.set(Calendar.MINUTE, 0);
				billDate.set(Calendar.SECOND, 0);
				billDate.set(Calendar.MILLISECOND, 0);
				System.out.println("是否统计：" + condition.getIsCompute());
				System.out.println("是否大开：" + billDate.before(startDate));
				if (condition.getIsCompute() && billDate.before(startDate)) {// 统计上期结存
					System.out.println("统计上期结存");
					isAdd = false;
					lastInfo.setPtPart(em.getPtNo());
					lastInfo.setPtName(em.getFactoryName());
					lastInfo.setPtSpec(em.getFactorySpec());
					// lastInfo.setInOutDate(b.getBillMaster().getValidDate());
					lastInfo.setBillType("上期结存");

					// 进仓单据
					if (WareType.WARE_OUT == b.getBillMaster().getBillType()
							.getWareType()) {
						lastInfo.setNumOfIn(CommonUtils
								.getDoubleExceptNull(lastInfo.getNumOfIn())
								+ CommonUtils.getDoubleExceptNull(b
										.getPtAmount()));
						lastInfo.setJieCun(CommonUtils
								.getDoubleExceptNull(lastInfo.getJieCun())
								+ CommonUtils.getDoubleExceptNull(b
										.getPtAmount()));
					} else {
						// lastInfo.setNumOfOut(CommonUtils
						// .getDoubleExceptNull(lastInfo.getNumOfOut())
						// + CommonUtils.getDoubleExceptNull(b
						// .getPtAmount()));
						lastInfo.setNumOfZheSuan(CommonUtils
								.getDoubleExceptNull(lastInfo.getNumOfZheSuan())
								+ CommonUtils.getDoubleExceptNull(b
										.getPtAmount())
								* (bomMap.get(b.getPtPart()) == null ? 1
										: bomMap.get(b.getPtPart())));// 折算数量
						lastInfo.setJieCun(CommonUtils
								.getDoubleExceptNull(lastInfo.getJieCun())
								- CommonUtils.getDoubleExceptNull(b
										.getPtAmount())
								* (bomMap.get(b.getPtPart()) == null ? 1
										: bomMap.get(b.getPtPart())));
					}
					continue;
				}
				if (!isAdd) {
					result.add(lastInfo);
					isAdd = true;
				}
				ConsignQueryInfo info = new ConsignQueryInfo();
				info.setPtPart(em.getPtNo());
				info.setPtName(em.getFactoryName());
				info.setPtSpec(em.getFactorySpec());
				info.setInOutDate(b.getBillMaster().getValidDate());
				info.setBillType(b.getBillMaster().getBillType().getName());

				// 进仓单据
				if (WareType.WARE_OUT == b.getBillMaster().getBillType()
						.getWareType()) {
					// 凭证号
					info.setPingZhenNoOfIn(b.getBillMaster().getBillNo());
					info.setNumOfIn(CommonUtils.getDoubleExceptNull(b
							.getPtAmount()));
					jieYu += info.getNumOfIn();
				} else {// 出仓单据
					info.setPingZhenNoOfOut(b.getBillMaster().getBillNo());
					info.setPtPartOfOut(b.getPtPart());// 出仓料号
					info.setNumOfOut(CommonUtils.getDoubleExceptNull(b
							.getPtAmount()));
					info.setNumOfZheSuan(CommonUtils.getDoubleExceptNull(b
							.getPtAmount())
							* (bomMap.get(b.getPtPart()) == null ? 1 : bomMap
									.get(b.getPtPart())));// 折算数量
					jieYu -= info.getNumOfZheSuan();
				}
				info.setJieCun(jieYu);
				info.setZhiDanHao(b.getProductNo());
				info.setCustomerName(b.getBillMaster().getScmCoc() == null ? ""
						: b.getBillMaster().getScmCoc().getName());
				info.setPtNo(b.getSeqNum() == null ? "" : b.getSeqNum()
						.toString());
				info.setUnitName(b.getPtUnit() == null ? "" : b.getPtUnit()
						.getName());
				info.setStoreName(b.getWareSet() == null ? "" : b.getWareSet()
						.getName());
				// info.setMemo(b.get)
				result.add(info);
			}
			if (!isAdd) {
				result.add(lastInfo);
				isAdd = true;
			}
		}
		return result;
	}

	/*
	 * //code="4106" name="外发加工半成品出库" conditions.add(new Condition("or", null,
	 * "billMaster.billType.code", "=", "4106", null)); //code="4004"
	 * name="外发加工半成品退回" conditions.add(new Condition("or", null,
	 * "billMaster.billType.code", "=", "4004", null)); //code="2112"
	 * name="外发加工成品出库" conditions.add(new Condition("or", null,
	 * "billMaster.billType.code", "=", "2112", null)); //code="2017"
	 * name="外发加工成品退回" conditions.add(new Condition("or", null,
	 * "billMaster.billType.code", "=", "2017", null));
	 */

	/**
	 * 委外进出仓帐查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQuery(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();

		// // code="1110" name="外发加工出库"
		// conditions.add(new Condition("and", "(", "billMaster.billType.code",
		// "=", "1110", null));
		// // code="1112" name="外发加工返修出库"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1112", null));

		// code="1113" name="外发加工料件出库"
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1113", null));
		// code="1017" name="外发加工料件退回"
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1017", null));

		// // code="1012" name="外发加工退回料件"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1012", null));
		// // code="1013" name="外发加工返回料件"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1013", null));
		// // code="1014" name="委外期初单"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1014", ")"));

		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailMateriel",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("1113")
			// || typeCode.trim().equals("1112")
			// || typeCode.trim().equals("1014")
			) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外进出仓帐料件查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaImg(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();

		// code="1113" name="外发加工料件出库"
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1113", null));
		// // code="1112" name="外发加工返修出库"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1112", null));

		// code="1113" name="外发加工料件出库"
		// conditions.add(new Condition("and", "(", "billMaster.billType.code",
		// "=", "1113", null));
		// // code="1017" name="外发加工料件退回"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1017", null));

		// // code="1012" name="外发加工退回料件"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1012", null));
		// // code="1013" name="外发加工返回料件"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1013", null));
		// code="1017" name="外发加工料件退回"
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1017", ")"));

		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailMateriel",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();

			// 计算结存: 出仓 - 进仓
			if (typeCode.trim().equals("1113")// 1113外发加工料件出库
			) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else { // 1017外发加工退件退回
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外进出仓帐成品查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaExg(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2114", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2017", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//

		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();

			// 计算结存数量 出仓 - 进仓
			if (typeCode.trim().equals("2114")// 2114外发加工成品出库
			) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else { // 2017外发加工成品退回
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外进出仓帐半成品查询
	 * 
	 * @param conditionss
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaHalfExg(List<Condition> conditionss) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditionss.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "4106", null));
		conditionss.add(new Condition("or", null, "billMaster.billType.code",
				"=", "4004", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//

		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailHalfProduct",
				conditionss, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();

			// 计算结存：出仓 - 进仓
			if (typeCode.trim().equals("4106")// 4106外发加工半成品出库
			) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else { // 4004外发加工半成品退回
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	public List getResultWaiFa(List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultImg = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultExg = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultHalfExg = new ArrayList<ImportExportInfo>();

		resultImg = this.findConsignQueryWaiFaImg(condition);// 委外进出仓帐 料件 查询
		result.addAll(resultImg);

		resultExg = this.findConsignQueryWaiFaExg(conditions);// 委外进出仓帐 成品 查询
		result.addAll(resultExg);

		resultHalfExg = this.findConsignQueryWaiFaHalfExg(conditionss);// 委外进出仓帐
		// 半成品
		// 查询
		result.addAll(resultHalfExg);

		System.out.print("result.size()=" + result.size());
		return result;
	}

	/**
	 * 委外进出仓帐料件收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaImgBack(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1018", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1112", ")"));

		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailMateriel",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("1112")) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外进出仓帐半成品收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaExgBack(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "4006", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "4104", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailHalfProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("4104")) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外进出仓帐成品收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaHalfExgBack(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2015", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2113", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//

		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		//
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("2113")) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外进出仓帐边角料收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaLeftoverBack(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "6005", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "6105", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("",
				"BillDetailLeftoverMateriel", conditions, "", leftOuter,
				orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		//
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("6105")) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外进出仓帐残次品收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaRemainProductBack(
			List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "5003", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "5103", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailRemainProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		//
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("5103")) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	public List getResultWaiFaBack(List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss,
			List<Condition> tiaojian, List<Condition> tiaojian2) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultImgBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultExgBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultHaflExgBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultLeftoverBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultRemainProductBack = new ArrayList<ImportExportInfo>();

		resultImgBack = this.findConsignQueryWaiFaImgBack(condition);// 委外进出仓帐料件收回查询
		result.addAll(resultImgBack);

		resultHaflExgBack = this.findConsignQueryWaiFaHalfExgBack(conditionss);// 委外进出仓帐成品收回查询
		result.addAll(resultHaflExgBack);

		resultExgBack = this.findConsignQueryWaiFaExgBack(conditions);// 委外进出仓帐半成品收回查询
		result.addAll(resultExgBack);

		resultLeftoverBack = this.findConsignQueryWaiFaLeftoverBack(tiaojian);// 委外进出仓帐边角料收回查询
		result.addAll(resultLeftoverBack);

		resultRemainProductBack = this
				.findConsignQueryWaiFaRemainProductBack(tiaojian2);// 委外进出仓帐残次品收回查询
		result.addAll(resultRemainProductBack);

		return result;
	}

	/**
	 * 委外受托加工受过进出仓账料件查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaImgTrusteeInOut(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();

		// code="1113" name="外发加工料件出库"
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1008", null));
		// // code="1112" name="外发加工返修出库"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1112", null));

		// code="1113" name="外发加工料件出库"
		// conditions.add(new Condition("and", "(", "billMaster.billType.code",
		// "=", "1113", null));
		// // code="1017" name="外发加工料件退回"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1017", null));

		// // code="1012" name="外发加工退回料件"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1012", null));
		// // code="1013" name="外发加工返回料件"
		// conditions.add(new Condition("or", null, "billMaster.billType.code",
		// "=", "1013", null));
		// code="1017" name="外发加工料件退回"
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1111", ")"));

		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailMateriel",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();

			if (typeCode.trim().equals("1111")
			// || typeCode.trim().equals("1112")
			// || typeCode.trim().equals("1014")
			) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外受托加工受过进出仓账成品查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaExgTrusteeInOut(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2014", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2111", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//

		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("2111")
			// || typeCode.trim().equals("1112")
			// || typeCode.trim().equals("1014")
			) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 委外受托加工受过进出仓账半成品查询
	 * 
	 * @param conditionss
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaHalfExgTrusteeInOut(
			List<Condition> conditionss) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditionss.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "4005", null));
		conditionss.add(new Condition("or", null, "billMaster.billType.code",
				"=", "4107", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//

		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailHalfProduct",
				conditionss, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("4107")
			// || typeCode.trim().equals("1112")
			// || typeCode.trim().equals("1014")
			) {
				rsPtAmount -= importExportInfo.getImpPtAmount();
				rsHsAmount -= importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount += importExportInfo.getExpPtAmount();
				rsHsAmount += importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	public List getResultWaiFaTrusteeInOut(List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultImg = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultExg = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultHalfExg = new ArrayList<ImportExportInfo>();

		resultImg = this.findConsignQueryWaiFaImgTrusteeInOut(condition);// 委外受托加工受过进出仓账料件查询
		result.addAll(resultImg);

		resultExg = this.findConsignQueryWaiFaExgTrusteeInOut(conditions);// 委外受托加工受过进出仓账成品查询
		result.addAll(resultExg);

		resultHalfExg = this
				.findConsignQueryWaiFaHalfExgTrusteeInOut(conditionss);// 委外受托加工受过进出仓账半成品查询
		result.addAll(resultHalfExg);

		System.out.print("result.size()=" + result.size());
		return result;
	}

	/**
	 * 受托返回进出仓帐料件收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaImgTrusteeInOutBack(
			List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "1019", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "1114", ")"));

		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailMateriel",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();

			if (typeCode.trim().equals("1114")) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 受托返回进出仓帐成品收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaExgTrusteeInOutBack(
			List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "2112", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "2016", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();

			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//

			if (typeCode.trim().equals("2112")) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 受托返回进出仓帐半成品收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaHalfExgTrusteeInOutBack(
			List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "4008", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "4108", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//

		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailHalfProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();

			if (typeCode.trim().equals("4108")) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 受托返回进出仓帐边角料收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaLeftoverTrusteeInOutBack(
			List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "6006", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "6106", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("",
				"BillDetailLeftoverMateriel", conditions, "", leftOuter,
				orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("6106")) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/**
	 * 受托返回进出仓帐残次品收回查询
	 * 
	 * @param conditions
	 *            查询条件
	 * @return 委外的进出口商品信息
	 */
	public List findConsignQueryWaiFaRemainProductTrusteeInOutBack(
			List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"=", "5004", null));
		conditions.add(new Condition("or", null, "billMaster.billType.code",
				"=", "5104", ")"));
		//
		// 按料号和日期进行排序,进入仓进行排序(先进后出)
		//
		String orderBy = "order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code DESC ";
		String leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";
		List billDetails = casDao.commonSearch("", "BillDetailRemainProduct",
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";

		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptNo = billDetail.getPtPart();
			// String key = ptNo;
			String key = ptNo;
			if (!key.equalsIgnoreCase(oldKey)) { // 当排序后不相同的料号初始化为0
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(
					billDetail, true);
			// importExportInfo.setWareSet(wareSet);
			String typeCode = billDetail.getBillMaster().getBillType()
					.getCode().trim();
			//
			// code="1110" name="外发加工出库"
			//
			if (typeCode.trim().equals("5103")) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	public List getResultWaiFaTrusteeInOutBack(List<Condition> conditions,
			List<Condition> condition, List<Condition> conditionss,
			List<Condition> tiaojian, List<Condition> tiaojian2) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultImgBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultExgBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultHaflExgBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultLeftoverBack = new ArrayList<ImportExportInfo>();
		List<ImportExportInfo> resultRemainProductBack = new ArrayList<ImportExportInfo>();

		resultImgBack = this
				.findConsignQueryWaiFaImgTrusteeInOutBack(condition);// 受托返回进出仓帐料件收回查询
		result.addAll(resultImgBack);

		resultExgBack = this
				.findConsignQueryWaiFaExgTrusteeInOutBack(conditions);// 受托返回进出仓帐成品收回查询
		result.addAll(resultExgBack);

		resultHaflExgBack = this
				.findConsignQueryWaiFaHalfExgTrusteeInOutBack(conditionss);// 受托返回进出仓帐半成品收回查询
		result.addAll(resultHaflExgBack);

		resultRemainProductBack = this
				.findConsignQueryWaiFaRemainProductBack(tiaojian2);// 委外进出仓帐残次品收回查询
		result.addAll(resultRemainProductBack);

		resultLeftoverBack = this
				.findConsignQueryWaiFaLeftoverTrusteeInOutBack(tiaojian);// 受托返回进出仓帐边角料收回查询
		result.addAll(resultLeftoverBack);

		return result;
	}

	/**
	 * 查询进出口商品信息
	 * 
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出委外的进出口商品信息
	 */
	public List findImpExpInfos(String materialType,
			List<Condition> conditions, Boolean isSplitBomVersion,
			Date beginDate, Boolean isWareSet) {
		Map<String, String> mapRsAmountJY = new HashMap<String, String>();
		List<ImportExportInfo> result = this.calcImpExpInfos(materialType,
				conditions, isSplitBomVersion, isWareSet, beginDate,
				mapRsAmountJY);

		ArrayList<ImportExportInfo> list = new ArrayList<ImportExportInfo>();

		// /////////////////////////////
		// 利用 list 把排序后的 把相同料号
		// 相同仓库的记录,在指定的beginDate
		// 前的数据只加载最后一条
		// /////////////////////////////
		String oldKey = "";
		boolean isOnce = true;

		// 从尾到头遍历
		for (int i = result.size() - 1; i >= 0; i--) {
			ImportExportInfo importExportInfo = result.get(i);
			String wareSetCode = importExportInfo.getWareSet() == null ? ""
					: importExportInfo.getWareSet().getCode() == null ? ""
							: importExportInfo.getWareSet().getCode();

			String ptNo = importExportInfo.getPtPart();
			String ptName = importExportInfo.getPtName();
			String ptSpec = importExportInfo.getPtSpec();

			// String key = wareSetCode + ptNo;
			String key = ptNo + "/" + ptName + "/" + ptSpec;
			if (isWareSet) {
				key += "/" + wareSetCode;
			}
			if (isSplitBomVersion) {
				int version = importExportInfo.getVersion() == null ? 0
						: importExportInfo.getVersion();
				key += "/" + version;
			}

			Date currentDate = importExportInfo.getDate();

			//
			// init 第一个 oldKey 的值
			//
			if (i == result.size() - 1) {
				//
				// 如果在开始日期后边
				if (!currentDate.before(beginDate)) {
					list.add(0, importExportInfo);
				}
				// 日期在前面
				else {
					//
					// 加入上期加存在此
					//
					addTotalFrontImportExportInfo(beginDate, importExportInfo,
							mapRsAmountJY, key);
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
					addTotalFrontImportExportInfo(beginDate, importExportInfo,
							mapRsAmountJY, key);
					list.add(0, importExportInfo);
					isOnce = false;
				}
			} else if (key.equalsIgnoreCase(oldKey)
					&& !currentDate.before(beginDate)) {
				list.add(0, importExportInfo);
			} else if (key.equalsIgnoreCase(oldKey)
					&& currentDate.before(beginDate) && isOnce) {
				addTotalFrontImportExportInfo(beginDate, importExportInfo,
						mapRsAmountJY, key);
				list.add(0, importExportInfo);
				isOnce = false;
			}
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		mapRsAmountJY.clear();
		return list;
	}

	/**
	 * 查询进出口商品信息
	 * 
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出委外的进出口商品信息
	 */
	public List calcImpExpInfos(String materialType,
			List<Condition> conditions, Boolean isSplitBomVersion,
			Boolean isWareSet, Date beginDate, Map<String, String> mapAmountJY) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(materialType);
		if (billDetailTableName == null) {
			return result;
		}
		boolean isPtName = false;// 是否有按工厂名称查询
		boolean isHsName = false;// 是否有按报关名称查询
		boolean isBillType = false;
		if (conditions != null) {
			for (Condition item : conditions) {
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
		if (MaterielType.MATERIEL.equals(materialType)) {
			System.out.println("materialType=" + materialType);
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1002", ")")); // 不统计在产品起初单
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1015", ")")); // 已收货未结转期初单
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1016", ")")); // 已结转未收货期初单

		} else if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "2011", ")")); // 已交货未结转期初单
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "2012", ")")); // 已交货未结转期初单
		}

		String orderBy = "";
		String leftOuter = "";

		if (MaterielType.FINISHED_PRODUCT.equals(materialType)
				&& isSplitBomVersion != null
				&& isSplitBomVersion.booleanValue() == true) {
			isSplitBomVersion = true;
		} else {
			isSplitBomVersion = false;
		}

		//
		// 成品分版本来统计
		//
		if (isSplitBomVersion) {
			if (isHsName || isPtName) {
				// 按仓库分组应该他仓库分类，再生效日期分类
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC ,a.ptPart ASC,a.version ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC";
				} else {
					orderBy = " order by a.ptPart ASC,a.version ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
				}

			} else {
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC,a.ptPart ASC,a.version ASC,  a.billMaster.validDate ASC,a.billMaster.billType.code ASC ";
				} else {
					orderBy = " order by a.ptPart ASC,a.version ASC, a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
				}
			}

		} else {
			if (isHsName || isPtName) {
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC,a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC ";
				} else {
					orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";

				}

			} else {
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC,a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC ";
				} else {
					orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
				}

			}
		}
		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";

		List billDetails = casDao.commonSearch("", billDetailTableName,
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		double rsPtAmountJY = 0;// 上期结余
		double rsHsAmountJY = 0;// 上期结余海关
		String sqjcKey = "";// 上期结存key
		String oldKey = "";
		String wareSetKey = "";// 仓库分组key
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);

			String wareSetCode = billDetail.getWareSet() == null ? ""
					: billDetail.getWareSet().getCode();
			String ptNo = billDetail.getPtPart();
			String ptName = billDetail.getPtName();
			String ptSpec = billDetail.getPtSpec();
			int version = billDetail.getVersion() == null ? 0 : billDetail
					.getVersion();
			// wss2010.08.25:key应该改为 料号+名称+规格
			String key = ptNo + "/" + ptName + "/" + ptSpec;
			if (isWareSet) {
				key += "/" + wareSetCode;
			}
			// 成品分版本来统计
			if (isSplitBomVersion) {
				key += "/" + version;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(billDetail);
			// Date currentDate = importExportInfo.getDate();
			// 当排序后另外的物料的结存数量应该初始化为0,但要没有按工厂或报关名称进行查询

			if (!key.equalsIgnoreCase(oldKey)) {
				if (!isHsName && !isPtName) {// 当按料件查询时
					rsPtAmount = 0;
					rsHsAmount = 0;
				} else if (isHsName || isPtName) {// 当按报表名称查询时
					rsPtAmountJY = 0;
					rsHsAmountJY = 0;
				}
				if (isWareSet && !wareSetKey.equalsIgnoreCase(wareSetCode)) {// 当按仓库进行分组时
					rsPtAmount = 0;
					rsHsAmount = 0;
				}
			}

			// System.out.println("key==" + key + "==wareType="
			// + importExportInfo.getBillType().getWareType().intValue()
			// + "==ptNo=" + ptNo + " (" + version + ")==Imp="
			// + importExportInfo.getImpPtAmount() + "Exp=="
			// + importExportInfo.getExpPtAmount() + "==data="
			// + importExportInfo.getDate());
			if (importExportInfo.getBillType().getWareType().intValue() == 1) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
				rsPtAmountJY += importExportInfo.getImpPtAmount();
				rsHsAmountJY += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
				rsPtAmountJY -= importExportInfo.getExpPtAmount();
				rsHsAmountJY -= importExportInfo.getExpHsAmount();
			}
			// System.out.println("==rsPtAmount=" + rsPtAmount + " rsHsAmount="
			// + rsHsAmount);
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);

			// 成品要version呀
			if (isSplitBomVersion) {
				importExportInfo.setVersion(version);
			}
			// 成品分版本来统计 在报表中做分组条件
			if (isSplitBomVersion) {
				importExportInfo.setPtPart(ptNo + " (" + version + ")");
			}
			result.add(importExportInfo);
			sqjcKey = importExportInfo.getPtPart() + "/" + ptName + "/"
					+ ptSpec;
			if (isWareSet) {
				sqjcKey += "/" + wareSetCode;
				wareSetKey = wareSetCode;
			}
			// 成品分版本来统计
			if (isSplitBomVersion) {
				sqjcKey += "/" + version;
			}
			if (importExportInfo.getDate().before(beginDate)) {
				if (!isHsName && !isPtName) {
					mapAmountJY.put(sqjcKey, rsPtAmount + "/" + rsHsAmount);
				} else {
					mapAmountJY.put(sqjcKey, rsPtAmountJY + "/" + rsHsAmountJY);
				}
			}
			//
			// 获得最后一个
			//
			oldKey = key;
		}
		return result;
	}

	/** 加入上期加存在此 */
	private void addTotalFrontImportExportInfo(Date beginDate,
			ImportExportInfo importExportInfo,
			Map<String, String> mapRsAmountJY, String key) {

		importExportInfo
				.setImpBillNo(ImportExportInfo.TOTAL_FRONT_AMOUNT_STRING);
		importExportInfo
				.setExpBillNo(ImportExportInfo.TOTAL_FRONT_AMOUNT_STRING);
		// 上期结存不显示报关单号和制单号
		importExportInfo.setCustomNo("");
		importExportInfo.setProductNo("");

		importExportInfo.setDate(beginDate);
		importExportInfo.setBillType(null);

		String pt = mapRsAmountJY.get(key) == null ? null : mapRsAmountJY
				.get(key).toString().split("/")[0];
		String hs = mapRsAmountJY.get(key) == null ? null : mapRsAmountJY
				.get(key).toString().split("/")[1];
		// 正数
		Double rsHsAmount = hs == null ? 0.0 : Double.valueOf(hs);
		Double rsPtAmount = pt == null ? 0.0 : Double.valueOf(pt);
		// System.out.println("===currentDate==" + rsHsAmount + "===beginDate=="
		// + rsPtAmount + " data==" + importExportInfo.getDate()
		// + "==key==" + key);
		boolean isHsAmountSignless = rsHsAmount > 0;
		importExportInfo.setExpHsAmount(isHsAmountSignless ? 0 : -rsHsAmount);// 当结余小于0为负数时写入出仓时为正
		importExportInfo.setImpHsAmount(!isHsAmountSignless ? 0 : rsHsAmount);

		// 正数
		boolean isPtAmountSignless = rsPtAmount > 0;
		importExportInfo.setExpPtAmount(isPtAmountSignless ? 0 : -rsPtAmount);// 当结余小于0为负数时写入出仓时为正
		importExportInfo.setImpPtAmount(!isPtAmountSignless ? 0 : rsPtAmount);

		// // 正数
		// boolean isHsAmountSignless = importExportInfo.getRsHsAmount() > 0;
		// importExportInfo.setExpHsAmount(isHsAmountSignless ? 0
		// : -importExportInfo.getRsHsAmount());// 当结余小于0为负数时写入出仓时为正
		// importExportInfo.setImpHsAmount(!isHsAmountSignless ? 0
		// : importExportInfo.getRsHsAmount());
		//
		// // 正数
		// boolean isPtAmountSignless = importExportInfo.getRsPtAmount() > 0;
		// importExportInfo.setExpPtAmount(isPtAmountSignless ? 0
		// : -importExportInfo.getRsPtAmount());// 当结余小于0为负数时写入出仓时为正
		// importExportInfo.setImpPtAmount(!isPtAmountSignless ? 0
		// : importExportInfo.getRsPtAmount());

	}

	/**
	 * 查询进出口商品信息
	 * 
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 按照查询条件查出委外的进出口商品信息
	 */
	public List findImpExpInfos(String materialType,
			List<Condition> conditions, Boolean isSplitBomVersion,
			Boolean isWareSet) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(materialType);
		System.out.println("billDetailTableName=" + billDetailTableName);
		if (billDetailTableName == null) {
			return result;
		}
		boolean isPtName = false;// 是否有按工厂名称查询
		boolean isHsName = false;// 是否有按报关名称查询
		boolean isBillType = false;
		if (conditions != null) {
			for (Condition item : conditions) {
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
		if (MaterielType.MATERIEL.equals(materialType)) {
			System.out.println("materialType=" + materialType);
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1002", ")")); // 不统计在产品起初单
			//
			// 所有外发加工可以在这里统计
			//
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1012", ")")); // 外发加工出库
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1013", ")")); // 外发加工退回料件
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1110", ")")); // 外发加工出库
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1112", ")")); // 外发加工返修出库
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1014", ")")); // 委外期初单
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1015", ")")); // 已收货未结转期初单
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1016", ")")); // 已结转未收货期初单

		} else if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "2011", ")")); // 已交货未结转期初单
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "2012", ")")); // 已交货未结转期初单
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1015", ")")); //
			// 已收货未结转期初单（这个明显是错的）
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1016", ")")); //
			// 已结转未收货期初单（这个明显是错的）
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

		//
		// 成品分版本来统计
		//
		if (isSplitBomVersion) {
			if (isHsName || isPtName) {
				// orderBy =
				// " order by
				// a.billMaster.validDate,a.ptPart,a.version,a.wareSet.code,a.billMaster.billType.code
				// ";
				// 按仓库分组应该他仓库分类，再生效日期分类
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC ,a.ptPart ASC,a.version ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC";
				} else {
					orderBy = " order by a.ptPart ASC,a.version ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
				}

			} else {
				// orderBy =
				// " order by a.ptPart ASC,a.version ASC, a.wareSet.code
				// ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC
				// ";
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC,a.ptPart ASC,a.version ASC,  a.billMaster.validDate ASC,a.billMaster.billType.code ASC ";
				} else {
					orderBy = " order by a.ptPart ASC,a.version ASC, a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
				}
			}

		} else {
			if (isHsName || isPtName) {
				// orderBy =
				// " order by
				// a.billMaster.validDate,a.ptPart,a.wareSet.code,a.billMaster.billType.code
				// ";
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC,a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC ";
				} else {
					orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";

				}

			} else {
				// orderBy =
				// " order by a.ptPart ASC,a.wareSet.code
				// ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC
				// ";
				if (isWareSet) {
					orderBy = " order by a.wareSet.code ASC,a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC ";
				} else {
					orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
				}

			}
		}
		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";

		List billDetails = casDao.commonSearch("", billDetailTableName,
				conditions, "", leftOuter, orderBy);

		// 残次品参与统计
		// if (MaterielType.MATERIEL.equals(materialType)) {
		// String tableName = BillUtil
		// .getBillDetailTableNameByMaterielType(MaterielType.BAD_PRODUCT); //
		// 残次品
		// if (isBillType == false) {
		// Condition condition = new Condition("and", "(",
		// "billMaster.billType.code", "=", "5101", ")");
		//
		// Condition condition2 = new Condition("and", "(", "note", "=",
		// "料件", null); // note == 料件
		// Condition condition3 = new Condition("or", null,
		// "note is null or a.note = '' ", null, null, ")");
		//
		// conditions.add(condition); // 残次品出库 5101",
		// conditions.add(condition2);
		// conditions.add(condition3);
		// List billDetails2 = casDao.commonSearch("", tableName,
		// conditions, "", leftOuter, orderBy);
		// billDetails.addAll(billDetails2);
		// }
		// }

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		String wareSetKey = "";// 仓库分组key
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);

			String wareSetCode = billDetail.getWareSet() == null ? ""
					: billDetail.getWareSet().getCode();
			String ptNo = billDetail.getPtPart();
			String ptName = billDetail.getPtName();
			String ptSpec = billDetail.getPtSpec();
			int version = billDetail.getVersion() == null ? 0 : billDetail
					.getVersion();

			// wss2010.08.25:key应该改为 料号+名称+规格
			//hwy20150126因企业在中途有可能修改物料的名称或者规格，导致结存统计时出现问题,所以key改为料号即可
//			String key = ptNo + "/" + ptName + "/" + ptSpec;
			String key = ptNo ;
			if (isWareSet) {
				key += "/" + wareSetCode;
			}
			// 成品分版本来统计
			if (isSplitBomVersion) {
				key += "/" + version;
			}

			// 当排序后另外的物料的结存数量应该初始化为0,但要没有按工厂或报关名称进行查询
			if (!key.equalsIgnoreCase(oldKey)) {
				if (!isHsName && !isPtName) {
					rsPtAmount = 0;
					rsHsAmount = 0;
				}
				System.out.println("==="
						+ !wareSetKey.equalsIgnoreCase(wareSetCode)
						+ " wareSetKey=" + wareSetCode);
				if (isWareSet && !wareSetKey.equalsIgnoreCase(wareSetCode)) {// 当按仓库进行分组时
					System.out.println("=======初始化为0");
					rsPtAmount = 0;
					rsHsAmount = 0;
				}
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(billDetail);
			// System.out.println("key==" + key + "==wareType="
			// + importExportInfo.getBillType().getWareType().intValue()
			// + "==ptNo=" + ptNo + " (" + version + ")==Imp="
			// + importExportInfo.getImpPtAmount() + "Exp=="
			// + importExportInfo.getExpPtAmount() + "==data="
			// + importExportInfo.getDate());

			if (importExportInfo.getBillType().getWareType().intValue() == 1) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}
			// System.out.println("==rsPtAmount=" + rsPtAmount + " rsHsAmount="
			// + rsHsAmount);
			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);

			// 成品要version呀
			if (isSplitBomVersion) {
				importExportInfo.setVersion(version);
			}

			// 成品分版本来统计 在报表中做分组条件
			if (isSplitBomVersion) {
				importExportInfo.setPtPart(ptNo + " (" + version + ")");
			}

			if (isWareSet) {
				wareSetKey = wareSetCode;
			}
			result.add(importExportInfo);
			//
			// 获得最后一个
			//
			oldKey = key;
			// System.out.println("oldKey= :" + oldKey);
		}
		return result;
	}

	// /** 查询进出口商品信息 */
	// public List findImpExpInfosGetExpDj(String materialType,
	// List<Condition> conditions, List<Condition> conditions1,
	// Boolean isSplitBomVersion) {
	// BillType type = this.casDao.findBillTypeByCode("1113");
	// /**
	// * 先清空国内购买出库资料
	// */
	// this.casDao.DeleteAllBillDetail(type);
	// this.casDao.DeleteAllBillMaster(type);
	//
	// List ls = findImpExpInfos(materialType, conditions, isSplitBomVersion,
	// false);// wss2010.08.27暂时仓库分组为false
	// ImportExportInfo oldinfo = null;
	// String oldKey = "";
	// for (int i = 0; i < ls.size(); i++) {
	// ImportExportInfo info = (ImportExportInfo) ls.get(i);
	// String wareSetCode = info.getWareSet() == null ? "" : info
	// .getWareSet().getCode();
	// String ptNo = info.getPtPart() == null ? "" : info.getPtPart();
	// if (i == 0) {
	// oldKey = wareSetCode + ptNo;
	// }
	// String key = wareSetCode + ptNo;
	// if (!key.equalsIgnoreCase(oldKey)) {
	// savaBill(oldinfo, type);
	// }
	// if (i == (ls.size() - 1)) {
	// savaBill(info, type);
	// }
	// oldinfo = info;
	// oldKey = key;
	// }
	// return findImpExpInfos(materialType, conditions1, isSplitBomVersion,
	// false);// wss2010.08.27暂时仓库分组为false
	// }

	/** 保存单据 */
	// private void savaBill(ImportExportInfo oldinfo, BillType type) {
	// BillMaster master = new BillMasterMateriel();
	// master.setBillNo(oldinfo.getImpBillNo() == null ? oldinfo
	// .getExpBillNo() : oldinfo.getImpBillNo());
	// master.setBillType(type);
	// master.setValidDate(oldinfo.getDate());
	// master.setCompany(CommonUtils.getCompany());
	// master.setIsValid(new Boolean(true));
	// master.setScmCoc(oldinfo.getScmCoc());
	// this.casDao.saveBillMaster(master);
	//
	// BillDetail detail = new BillDetailMateriel();
	// detail.setBillMaster(master);
	// detail.setCompany(CommonUtils.getCompany());
	// detail.setPtPart(oldinfo.getPtPart());
	// detail.setPtName(oldinfo.getPtName());
	// detail.setPtSpec(oldinfo.getPtSpec());
	// detail.setPtUnit((oldinfo.getPtUnit() == null || oldinfo.getPtUnit()
	// .getId() == null) ? null : oldinfo.getPtUnit());
	// detail.setPtAmount(oldinfo.getRsPtAmount());
	// detail.setHsAmount(oldinfo.getRsHsAmount());
	// detail.setWareSet((oldinfo.getWareSet() == null || oldinfo.getWareSet()
	// .getId() == null) ? null : oldinfo.getWareSet());
	// detail.setHsName(oldinfo.getHsName());
	// detail.setHsSpec(oldinfo.getHsSpec());
	// detail.setHsUnit((oldinfo.getHsUnit() == null || oldinfo.getHsUnit()
	// .getCode() == null) ? null : oldinfo.getHsUnit());
	// this.casDao.saveBillDetail(detail);
	// }
	/**
	 * 查询结转的商品信息
	 * 
	 * @param materialType
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @return 经过结转过滤条件过滤后的结转商品信息
	 */
	public List findCarryForwardInfos(String materialType, List conditions) {
		List<CarryForwardInfo> result = new ArrayList<CarryForwardInfo>();
		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(materialType);
		if (billDetail == null) {
			return result;
		}

		List billDetails = casDao.commonSearch("", billDetail, conditions, "",
				"");
		double accHsAmount = 0;
		double accCarryForwardAmount = 0;
		for (int i = 0; i < billDetails.size(); i++) {
			CarryForwardInfo carryForwardInfo = new CarryForwardInfo(
					(BillDetail) billDetails.get(i));
			accHsAmount += (carryForwardInfo.getHsAmount() == null) ? 0
					: carryForwardInfo.getHsAmount().doubleValue();
			// accCarryForwardAmount += carryForwardInfo.getCarryForwardAmount()
			// .doubleValue();(wss2010.08.31改成如下)
			accCarryForwardAmount += carryForwardInfo.getCarryForwardAmount() == null ? 0.0
					: carryForwardInfo.getCarryForwardAmount().doubleValue();

			carryForwardInfo.setAccAmount(Double.valueOf(accHsAmount));
			carryForwardInfo.setAccCarryForwardAmount(Double
					.valueOf(accCarryForwardAmount));

			// double temp = carryForwardInfo.getHsAmount().doubleValue()
			// -
			// carryForwardInfo.getCarryForwardAmount().doubleValue();(wss2010.08.31改成如下)

			double temp = (carryForwardInfo.getHsAmount() == null ? 0.0
					: carryForwardInfo.getHsAmount())
					- (carryForwardInfo.getCarryForwardAmount() == null ? 0.0
							: carryForwardInfo.getCarryForwardAmount());

			if (temp > 0) {
				carryForwardInfo.setUnCarryForwardAmount(Double.valueOf(temp));
			} else if (temp < 0) {
				carryForwardInfo.setExceedCarryForwardAmount(Double
						.valueOf(-temp));
			}

			result.add(carryForwardInfo);
		}
		return result;
	}

	/**
	 * 料件，成品，设备库存情况统计表。
	 * 
	 * @param materialType
	 *            物料类型
	 * @param endDate
	 *            截止日期
	 * @param beginPtNo
	 *            开始料号
	 * @param endPtNo
	 *            截止料号
	 * @return 库存统计结果
	 */
	public List<StoreInfo> findStoreInfos(String materialType, Date beginDate,
			Date endDate, String beginPtNo, String endPtNo) {
		List<StoreInfo> result = new ArrayList<StoreInfo>();
		String tablaName = BillUtil
				.getBillDetailTableNameByMaterielType(materialType);
		if (tablaName == null) {
			return result;
		}
		List<Condition> conditions = new ArrayList<Condition>();

		if (beginDate == null) {
			beginDate = CommonUtils.getBeginDate(
					Integer.valueOf(CommonUtils.getYear()), 0, 1);
		}
		if (endDate == null) {
			endDate = CommonUtils.getEndDate(
					Integer.valueOf(CommonUtils.getYear()), 11, 31);
		}

		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		// 工厂料号不等于空,结束料号为空时
		if (beginPtNo != null && !beginPtNo.equals("")
				&& (endPtNo == null || endPtNo.equals(""))) {
			conditions.add(new Condition("and", null, "ptPart", "=", beginPtNo,
					null));
		} else if (beginPtNo != null && !beginPtNo.equals("")// 工厂料号不等于空,结束料号不为空时
				&& endPtNo != null && !endPtNo.equals("")) {
			conditions.add(new Condition("and", "(", "ptPart", ">=", beginPtNo,
					null));
			conditions.add(new Condition("and", null, "ptPart", "<=", endPtNo,
					")"));
		}

		List billDetails = casDao.commonSearch("", tablaName, conditions, "",
				"");
		Hashtable<String, StoreInfo> storeInfos = new Hashtable<String, StoreInfo>();
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String ptPart = billDetail.getPtPart();
			String storeId = billDetail.getWareSet() == null ? "" : billDetail
					.getWareSet().getId();
			String key = ptPart + storeId;
			double ptAmount = billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount();

			if (storeInfos.get(key) == null) {
				StoreInfo storeInfo = new StoreInfo();
				storeInfo.setWareSet(billDetail.getWareSet());
				storeInfo.setHsName(billDetail.getHsName());
				storeInfo.setComplex(billDetail.getComplex() == null ? ""
						: billDetail.getComplex().getCode());
				storeInfo.setHsSpec(billDetail.getHsSpec());
				storeInfo.setPtPart(billDetail.getPtPart());
				storeInfo.setUnit(billDetail.getPtUnit());
				storeInfo.setTodayAmount(ptAmount);
				if (!DateUtils.truncate(
						billDetail.getBillMaster().getValidDate(),
						Calendar.DATE).equals(
						DateUtils.truncate(endDate, Calendar.DATE))) { // 日期不为今天
					storeInfo.setYestdayAmount(ptAmount);
				}
				storeInfos.put(key, storeInfo);
			} else {
				StoreInfo storeInfo = (StoreInfo) storeInfos.get(key);
				storeInfo.setTodayAmount(storeInfo.getTodayAmount() + ptAmount);
				if (!DateUtils.truncate(
						billDetail.getBillMaster().getValidDate(),
						Calendar.DATE).equals(
						DateUtils.truncate(endDate, Calendar.DATE))) { // 日期不为今天
					storeInfo.setYestdayAmount(storeInfo.getYestdayAmount()
							+ ptAmount);
				}
			}
		}
		result.addAll(storeInfos.values());
		return result;
	}

	/**
	 * 增加现金流量表体
	 * 
	 * @param moneyMaster
	 *            现金流量表头
	 */
	public void moneyDetailadd(MoneyMaster moneyMaster) {
		addMoney(moneyMaster, "一,经营活动产生的现金流量", "", "支付的其他与投资活动有关的现金", "25",
				"1,不涉及现金收支的投资和筹资活动", "");
		addMoney(moneyMaster, "销售商品、提供劳务收到的现金", "1", "现金流出小计", "26",
				"以固定资产偿还债务", "44");
		addMoney(moneyMaster, "收到的租金", "2", "投资活动产生的现金流量净额", "27", "以投资偿还债务",
				"45");
		addMoney(moneyMaster, "收到的增值税销项税额和退回的增值税款", "3", "三、筹办活动产生的现金流量", "",
				"以固定资产进行投资", "46");
		addMoney(moneyMaster, "收到的除增值税以外的其它税费返还", "4", "吸收权益性投资所收到的现金", "28",
				"以存货偿还债务", "47");
		addMoney(moneyMaster, "收到的其它与经营活动有关的现金", "5", "发行债券所收到的现金", "29",
				"2,将净利润调节为经营活动的现金流量", "");
		addMoney(moneyMaster, "现金流入小计", "6", "借款所收到的现金", "30", "净利润", "48");
		addMoney(moneyMaster, "购买商品、接受劳务支付的现金", "7", "收到的其他与筹资活动有关的现金", "31",
				"加：计提的坏帐准备或转销的坏帐", "49");
		addMoney(moneyMaster, "经营租赁所支付的现金", "8", "现金流入小计", "32", "固定资产折旧", "50");
		addMoney(moneyMaster, "支付给职工以及为职工支付的现金", "9", "偿还债务所支付的现金", "33",
				"无形资产摊销", "51");
		addMoney(moneyMaster, "支付的增值税款", "10", "发生筹资费用所支付的现金", "34",
				"外置固定资产、无形资产和其他长期资产的损失(减：收益)", "52");
		addMoney(moneyMaster, "支付的所得税款", "11", "分配股利或利润所支付的现金", "35",
				"固定资产报废损失", "53");
		addMoney(moneyMaster, "支付的除增值税、所得税以外的其他税费", "12", "偿付利息所支付的现金", "36",
				"财务费用", "54");
		addMoney(moneyMaster, "支付的其他与经营活动有关的现金", "13", "融资租赁所支付的现金", "37",
				"投资损失（减：收益）", "55");
		addMoney(moneyMaster, "现金流出小计", "14", "减少注册资本所支付的现金", "38",
				"递延税款贷项（减：借项）", "56");
		addMoney(moneyMaster, "经营活动产生的现金流量净额", "15", "支付的其他与筹资活动有关的现金", "39",
				"存货的减少（减：增加）", "57");
		addMoney(moneyMaster, "二,投资活动产生的现金流量", "", "现金流出小计", "40",
				"经营性应收项目的减少（减：增加）", "58");
		addMoney(moneyMaster, "收回投资所收到的现金", "16", "筹资活动产生的现金流量净额", "41",
				"经营性应付项目的增加（减：减少）", "59");
		addMoney(moneyMaster, "分得股利或利润所收到的现金", "17", "四、汇率变动对现金的影响", "42",
				"增值税增加净额（减：减少）", "60");
		addMoney(moneyMaster, "取得债券利息收入所收到的现金", "18", "五、现金及现金等价物净增加额", "43",
				"其他", "61");
		addMoney(moneyMaster, "处置固定资产、无形资产和其他长期资产而收到的现金净额", "19", "", "",
				"经营活动产生的现金流量净额", "62");
		addMoney(moneyMaster, "收到的其他与投资活动有关的现金", "20", "", "",
				"3,现金及现金等价物净增加情况", "");
		addMoney(moneyMaster, "现金流入小计", "21", "", "", "现金的期末余额", "63");
		addMoney(moneyMaster, "购建固定资产、无形资产和其他长期资产所支付的现金", "22", "", "",
				"减：现金的期初余额", "64");
		addMoney(moneyMaster, "权益性投资所支付的现金", "23", "", "", "加：现金等价物的期末余额", "65");
		addMoney(moneyMaster, "债权性投资所支付的现金", "24", "", "", "减：现金等价物的期初余额", "66");
		addMoney(moneyMaster, "", "", "", "", "现金及现金等价物净增加额", "67");

	}

	/**
	 * 增加现金流量表
	 * 
	 * @param moneyMaster
	 *            现金流量表头
	 * @param item1
	 *            项目1
	 * @param times1
	 *            行次1
	 * @param item2
	 *            项目2
	 * @param times2
	 *            行次2
	 * @param item3
	 *            项目3
	 * @param times3
	 *            行次3
	 */
	private void addMoney(MoneyMaster moneyMaster, String item1, String times1,
			String item2, String times2, String item3, String times3) {
		MoneyDetail moneyDetail = new MoneyDetail();
		moneyDetail.setMoneyMaster(moneyMaster);
		moneyDetail.setItem1(item1);
		moneyDetail.setItem2(item2);
		moneyDetail.setItem3(item3);
		moneyDetail.setTimes1(times1);
		moneyDetail.setTimes2(times2);
		moneyDetail.setTimes3(times3);
		moneyDetail.setCompany(CommonUtils.getCompany());
		casDao.saveMoneyDetail(moneyDetail);
	}

	/**
	 * 增加资产负债表
	 * 
	 * @param debtMaster
	 *            资产负债表头
	 */
	public void DebtDetailadd(DebtMaster debtMaster) {
		addDebt(debtMaster, "流动资产：", "", "流动负债：", "");
		addDebt(debtMaster, "现金", "1", "短期借款", "42");
		addDebt(debtMaster, "银行存款", "2", "应付票据", "43");
		addDebt(debtMaster, "", "3", "应付帐款", "44");
		addDebt(debtMaster, "有价证券", "4", "应付工资", "45");
		addDebt(debtMaster, "应收票据", "5", "应交税金", "46");
		addDebt(debtMaster, "应收帐款", "6", "应付股利", "47");
		addDebt(debtMaster, "减：坏帐准备", "7", "预收货款", "48");
		addDebt(debtMaster, "预付货款", "8", "", "49");
		addDebt(debtMaster, "", "9", "其它应付款", "50");
		addDebt(debtMaster, "其它应收款", "10", "预提费用", "51");
		addDebt(debtMaster, "待摊费用", "11", "职工奖励及福利基金", "52");
		addDebt(debtMaster, "存货", "12", "一年内到期的长期负债", "53");
		addDebt(debtMaster, "减：存货变现损失准备", "13", "其它流动负债", "54");
		addDebt(debtMaster, "", "14", "流动负债合计", "55");
		addDebt(debtMaster, "一年内到期的长期投资", "15", "", "");
		addDebt(debtMaster, "其它流动资产", "16", "长期负债：", "");
		addDebt(debtMaster, "流动资产合计", "17", "长期借款", "56");
		addDebt(debtMaster, "长期投资：", "", "应付公司债", "57");
		addDebt(debtMaster, "长期投资", "18", "应付公司债溢价折价", "58");
		addDebt(debtMaster, "", "19", "一年以上的应付款项", "59");
		addDebt(debtMaster, "一年以上的应收款项", "20", "长期负债合计", "60");
		addDebt(debtMaster, "在建工程:", "", "其他负债:", "");
		addDebt(debtMaster, "固定资产原价", "21", "筹建期间汇兑收益", "61");
		addDebt(debtMaster, "减：累计折旧", "22", "递延投资收益", "62");
		addDebt(debtMaster, "固定资产净值", "23", "递延税款贷项", "63");
		addDebt(debtMaster, "", "24", "其他递延贷项", "64");
		addDebt(debtMaster, "固定资产清理", "27", "待转销汇兑收益", "65");
		addDebt(debtMaster, "在建工程", "28", "其他负债合计", "66");
		addDebt(debtMaster, "无形资产:", "", "负债合计", "67");
		addDebt(debtMaster, "场地使用权", "29", "所有者权益：", "");
		addDebt(debtMaster, "其他资产:", "", "实收资本", "68");
		addDebt(debtMaster, "工业产权及专有技术", "30", "其中：中方投资", "69");
		addDebt(debtMaster, "其他无形资产", "31", "   外方投资", "70");
		addDebt(debtMaster, "无形资产合计", "32", "减：已归还投资", "71");
		addDebt(debtMaster, "开办费", "33", "资本公积", "72");
		addDebt(debtMaster, "筹建期间汇兑损失", "34", "", "73");
		addDebt(debtMaster, "递延投资损失", "35", "储备基金", "74");
		addDebt(debtMaster, "递延税款借项", "36", "企业发展基金", "75");
		addDebt(debtMaster, "其他递延支出", "37", "利润归还投资", "76");
		addDebt(debtMaster, "待转销汇兑损失", "38", "本年利润", "77");
		addDebt(debtMaster, "", "39", "未分配利润", "78");
		addDebt(debtMaster, "其他资产合计", "40", "", "79");
		addDebt(debtMaster, "资产总计", "41", "所有者权益合计", "80");
		addDebt(debtMaster, "", "", "负债及所有者权益合计", "81");

	}

	/**
	 * 增加利润表体
	 * 
	 * @param marginMaster
	 *            利润表头
	 */
	public void MarginDetailadd(MarginMaster marginMaster) {
		addMargin(marginMaster, "产品销售收入", "1");
		addMargin(marginMaster, "其中：出口产品销售收入", "2");
		addMargin(marginMaster, "减：销售折扣与折让", "3");
		addMargin(marginMaster, "产品销售净额", "4");
		addMargin(marginMaster, "减：产品销售税金", "5");
		addMargin(marginMaster, "   产品销售成本", "6");
		addMargin(marginMaster, "其中：出口产品销售成本", "7");
		addMargin(marginMaster, "产品销售毛利", "8");
		addMargin(marginMaster, "减：销售费用", "9");
		addMargin(marginMaster, "   管理费用", "10");
		addMargin(marginMaster, "   财务费用", "11");
		addMargin(marginMaster, "其中：利息支出(减利息收入)", "12");
		addMargin(marginMaster, "      汇兑损失(减汇兑收益)", "13");
		addMargin(marginMaster, "产品销售利润", "14");
		addMargin(marginMaster, "加:其它业务利润", "15");
		addMargin(marginMaster, "营业利润", "16");
		addMargin(marginMaster, "加：投资收益", "17");
		addMargin(marginMaster, "   营业外收入", "18");
		addMargin(marginMaster, "减：营业外支出", "19");
		addMargin(marginMaster, "加：以前年度损益调整", "20");
		addMargin(marginMaster, "利润总额", "21");
		addMargin(marginMaster, "减：所得税", "22");
		addMargin(marginMaster, "净利润", "23");
	}

	/**
	 * 增加利润表
	 * 
	 * @param marginMaster
	 *            利润表头
	 * @param item
	 *            项目
	 * @param times
	 *            行次
	 */
	private void addMargin(MarginMaster marginMaster, String item, String times) {
		MarginDetail marginDetail = new MarginDetail();
		marginDetail.setMarginMaster(marginMaster);
		marginDetail.setItem(item);
		marginDetail.setTimes(times);
		marginDetail.setCompany(CommonUtils.getCompany());
		casDao.saveMarginDetail(marginDetail);
	}

	/**
	 * 增加资产负债表
	 * 
	 * @param debtMaster
	 *            资产负债表头
	 * @param item1
	 *            项目1
	 * @param times1
	 *            行次1
	 * @param item2
	 *            项目2
	 * @param times2
	 *            行次2
	 */
	private void addDebt(DebtMaster debtMaster, String item1, String times1,
			String item2, String times2) {
		DebtDetail debtDetail = new DebtDetail();
		debtDetail.setDebtMaster(debtMaster);
		debtDetail.setItem1(item1);
		debtDetail.setItem2(item2);
		debtDetail.setTimes1(times1);
		debtDetail.setTimes2(times2);
		debtDetail.setCompany(CommonUtils.getCompany());
		casDao.saveDebtDetail(debtDetail);
	}

	/**
	 * 获取单行的现金数
	 * 
	 * @param moneyMaster
	 *            现金流量表头
	 * @param times
	 *            行次
	 * @return 单行的现金数值
	 */
	private double getVa(MoneyMaster moneyMaster, String times) {
		List list = null;
		double moneyValues = 0;
		list = casDao.findMoneyByTimes(moneyMaster, times);
		if (list.get(0) != null) {
			moneyValues = Double.parseDouble(list.get(0).toString());
		}
		return moneyValues;
	}

	/**
	 * 现金流量计算
	 * 
	 * @param moneyMaster
	 *            现金流量表头
	 * @param times
	 *            行次
	 */
	public void account(MoneyMaster moneyMaster, String times) {
		double values = 0;
		double values1 = 0;
		if (Integer.parseInt(times) <= 5) {
			values = getVa(moneyMaster, "1") + getVa(moneyMaster, "2")
					+ getVa(moneyMaster, "3") + getVa(moneyMaster, "4")
					+ getVa(moneyMaster, "5");
			editAccount(moneyMaster, "6", Double.valueOf(values));
			values1 = values - getVa(moneyMaster, "14"); // 15=6-14
			editAccount(moneyMaster, "15", Double.valueOf(values1));
		} else if (Integer.parseInt(times) <= 13
				&& Integer.parseInt(times) >= 7) {
			values = getVa(moneyMaster, "7") + getVa(moneyMaster, "8")
					+ getVa(moneyMaster, "9") + getVa(moneyMaster, "10")
					+ getVa(moneyMaster, "11") + getVa(moneyMaster, "12")
					+ getVa(moneyMaster, "13");
			editAccount(moneyMaster, "14", Double.valueOf(values));
			values1 = getVa(moneyMaster, "6") - values; // 15=6-14
			editAccount(moneyMaster, "15", Double.valueOf(values1));
		} else if (Integer.parseInt(times) <= 20
				&& Integer.parseInt(times) >= 16) {
			values = getVa(moneyMaster, "16") + getVa(moneyMaster, "17")
					+ getVa(moneyMaster, "18") + getVa(moneyMaster, "19")
					+ getVa(moneyMaster, "20");
			editAccount(moneyMaster, "21", Double.valueOf(values));
			values1 = values - getVa(moneyMaster, "26");// 27=21-26
			editAccount(moneyMaster, "27", Double.valueOf(values1));
		} else if (Integer.parseInt(times) <= 25
				&& Integer.parseInt(times) >= 22) {
			values = getVa(moneyMaster, "22") + getVa(moneyMaster, "23")
					+ getVa(moneyMaster, "24") + getVa(moneyMaster, "25");
			editAccount(moneyMaster, "26", Double.valueOf(values));
			values1 = getVa(moneyMaster, "21") - values;// 27=21-26
			editAccount(moneyMaster, "27", Double.valueOf(values1));
		} else if (Integer.parseInt(times) <= 31
				&& Integer.parseInt(times) >= 28) {
			values = getVa(moneyMaster, "28") + getVa(moneyMaster, "29")
					+ getVa(moneyMaster, "30") + getVa(moneyMaster, "31");
			editAccount(moneyMaster, "32", Double.valueOf(values));
			values1 = values - getVa(moneyMaster, "40");// 41=32-40
			editAccount(moneyMaster, "41", Double.valueOf(values1));
		} else if (Integer.parseInt(times) <= 39
				&& Integer.parseInt(times) >= 33) {
			values = getVa(moneyMaster, "33") + getVa(moneyMaster, "34")
					+ getVa(moneyMaster, "35") + getVa(moneyMaster, "36")
					+ getVa(moneyMaster, "37") + getVa(moneyMaster, "38")
					+ getVa(moneyMaster, "39");
			editAccount(moneyMaster, "40", Double.valueOf(values));
			values1 = getVa(moneyMaster, "32") - values;// 41=32-40
			editAccount(moneyMaster, "41", Double.valueOf(values1));
		}
	}

	/**
	 * 现金流量计算
	 * 
	 * @param moneyMaster
	 *            现金流量表头
	 * @param times
	 *            行次
	 */
	private void editAccount(MoneyMaster moneyMaster, String times,
			Double values) {
		List list = null;
		list = casDao.findMoneyByTimes2(moneyMaster, times);
		MoneyDetail money = (MoneyDetail) list.get(0);
		if (Integer.parseInt(times) <= 24) {
			money.setMoney1(values);
		} else if (Integer.parseInt(times) <= 43
				&& Integer.parseInt(times) >= 25) {
			money.setMoney2(values);
		} else if (Integer.parseInt(times) >= 44) {
			money.setMoney3(values);
		}
		casDao.saveMoneyDetail(money);
	}

	/**
	 * 利润表计算
	 * 
	 * @param marginMaster
	 *            利润表表头
	 */
	public void marginAccount(MarginMaster marginMaster) {
		double values4 = 0;
		double values8 = 0;
		double values14 = 0;
		double values16 = 0;
		double values21 = 0;
		double values23 = 0;
		values4 = getMVa(marginMaster, "1") - getMVa(marginMaster, "3"); // 4=1-3
		editMAccount(marginMaster, "4", Double.valueOf(values4));
		values8 = values4 - getMVa(marginMaster, "5")
				- getMVa(marginMaster, "6");// 8=4-5-6
		editMAccount(marginMaster, "8", Double.valueOf(values8));
		values14 = values8 - getMVa(marginMaster, "9")
				- getMVa(marginMaster, "10") - getMVa(marginMaster, "11");// 14=8-9-10-11
		editMAccount(marginMaster, "14", Double.valueOf(values14));
		values16 = values14 + getMVa(marginMaster, "15");// 16=14+15
		editMAccount(marginMaster, "16", Double.valueOf(values16));
		values21 = values16 + getMVa(marginMaster, "17")
				+ getMVa(marginMaster, "18") - getMVa(marginMaster, "19")
				+ getMVa(marginMaster, "20");
		editMAccount(marginMaster, "21", Double.valueOf(values21));
		values23 = values21 - getMVa(marginMaster, "22"); // 23=21-22
	}

	/**
	 * 获得单行利润表中的金额
	 * 
	 * @param marginMaster
	 *            利润表头
	 * @param times
	 *            行次
	 * @return 单行利润表中的金额
	 */
	private double getMVa(MarginMaster marginMaster, String times) {
		List list = null;
		double marginValues = 0;
		list = casDao.findMarginByTimes(marginMaster, times);
		if (list.get(0) != null) {
			marginValues = Double.parseDouble(list.get(0).toString());
		}
		return marginValues;
	}

	/**
	 * 修改利润表统计中的内容
	 * 
	 * @param marginMaster
	 *            利润表头
	 * @param times
	 *            行次
	 * @param values
	 *            金额
	 */
	private void editMAccount(MarginMaster marginMaster, String times,
			Double values) {
		List list = null;
		list = casDao.findMarginByTimes2(marginMaster, times);
		MarginDetail margin = (MarginDetail) list.get(0);
		margin.setThisCount(values);
		casDao.saveMarginDetail(margin);
	}

	/**
	 * 资产负债统计情况
	 * 
	 * @param debtMaster
	 *            资产负债表头
	 * @param times
	 *            行次
	 * @param beginEnd
	 *            期初或期末数
	 */
	public void debtAccount(DebtMaster debtMaster, String times, String beginEnd) {
		double values17 = 0;
		double values40 = 0;
		double values41 = 0;
		double values23 = 0;
		double values32 = 0;
		double values55 = 0;
		double values60 = 0;
		double values66 = 0;
		double values67 = 0;
		double values68 = 0;
		double values80 = 0;
		double values81 = 0;
		if (Integer.parseInt(times) <= 41) {
			for (int i = 1; i <= 16; i++) {
				values17 = values17
						+ getDVa(debtMaster, String.valueOf(i), beginEnd);
			}
			editDAccount(debtMaster, "17", Double.valueOf(values17), beginEnd);
			values23 = getDVa(debtMaster, "21", beginEnd)
					- getDVa(debtMaster, "22", beginEnd);
			editDAccount(debtMaster, "23", Double.valueOf(values23), beginEnd);
			values32 = getDVa(debtMaster, "29", beginEnd)
					+ getDVa(debtMaster, "30", beginEnd)
					+ getDVa(debtMaster, "31", beginEnd);
			editDAccount(debtMaster, "32", Double.valueOf(values32), beginEnd);
			values40 = getDVa(debtMaster, "33", beginEnd)
					+ getDVa(debtMaster, "34", beginEnd)
					+ getDVa(debtMaster, "35", beginEnd)
					+ getDVa(debtMaster, "36", beginEnd)
					+ getDVa(debtMaster, "37", beginEnd)
					+ getDVa(debtMaster, "38", beginEnd)
					+ getDVa(debtMaster, "39", beginEnd);
			editDAccount(debtMaster, "40", Double.valueOf(values40), beginEnd);
			values41 = values40 + values17 + getDVa(debtMaster, "18", beginEnd)
					+ getDVa(debtMaster, "19", beginEnd)
					+ getDVa(debtMaster, "20", beginEnd) + values23
					+ getDVa(debtMaster, "24", beginEnd)
					+ getDVa(debtMaster, "27", beginEnd)
					+ getDVa(debtMaster, "28", beginEnd) + values32;
			editDAccount(debtMaster, "41", Double.valueOf(values41), beginEnd);
		}
		if (Integer.parseInt(times) >= 42 && Integer.parseInt(times) <= 54) {
			for (int i = 42; i <= 54; i++) {
				values55 = values55
						+ getDVa(debtMaster, String.valueOf(i), beginEnd);
			}
			editDAccount(debtMaster, "55", Double.valueOf(values55), beginEnd);
		}
		if (Integer.parseInt(times) >= 56 && Integer.parseInt(times) <= 67) {
			values60 = getDVa(debtMaster, "56", beginEnd)
					+ getDVa(debtMaster, "57", beginEnd)
					+ getDVa(debtMaster, "58", beginEnd)
					+ getDVa(debtMaster, "59", beginEnd);
			editDAccount(debtMaster, "60", Double.valueOf(values60), beginEnd);
			values66 = getDVa(debtMaster, "61", beginEnd)
					+ getDVa(debtMaster, "62", beginEnd)
					+ getDVa(debtMaster, "63", beginEnd)
					+ getDVa(debtMaster, "64", beginEnd)
					+ getDVa(debtMaster, "65", beginEnd);
			editDAccount(debtMaster, "66", Double.valueOf(values66), beginEnd);
			values67 = getDVa(debtMaster, "55", beginEnd) + values60 + values66;
			editDAccount(debtMaster, "67", Double.valueOf(values67), beginEnd);
		}
		if (Integer.parseInt(times) >= 68 && Integer.parseInt(times) <= 81) {
			values68 = getDVa(debtMaster, "69", beginEnd)
					+ getDVa(debtMaster, "70", beginEnd)
					- getDVa(debtMaster, "71", beginEnd);
			editDAccount(debtMaster, "68", Double.valueOf(values68), beginEnd);
			values80 = values68 + getDVa(debtMaster, "72", beginEnd)
					+ getDVa(debtMaster, "73", beginEnd)
					+ getDVa(debtMaster, "74", beginEnd)
					+ getDVa(debtMaster, "75", beginEnd)
					+ getDVa(debtMaster, "76", beginEnd)
					+ getDVa(debtMaster, "77", beginEnd)
					+ getDVa(debtMaster, "78", beginEnd)
					+ getDVa(debtMaster, "79", beginEnd);
			editDAccount(debtMaster, "80", Double.valueOf(values80), beginEnd);
			values81 = getDVa(debtMaster, "67", beginEnd) + values80;
			editDAccount(debtMaster, "81", Double.valueOf(values81), beginEnd);
		}
	}

	/**
	 * 取得资产负债表中每行的金额
	 * 
	 * @param debtMaster
	 *            资产负债表头
	 * @param times
	 *            行次
	 * @param beginEnd
	 *            期初或期末数
	 * @return 与指定的表头行次期初或期末数匹配的金额
	 */
	private double getDVa(DebtMaster debtMaster, String times, String beginEnd) {
		List list = null;
		double debtValues = 0;
		list = casDao.findDebtByTimes(debtMaster, times, beginEnd);
		if (list.get(0) != null) {
			debtValues = Double.parseDouble(list.get(0).toString());
		}
		return debtValues;
	}

	/**
	 * 修改资产负债统计内容
	 * 
	 * @param debtMaster
	 *            资产负债表头
	 * @param times
	 *            行次
	 * @param values
	 *            期初数或期末数
	 * @param beginEnd
	 *            行次数值
	 */
	private void editDAccount(DebtMaster debtMaster, String times,
			Double values, String beginEnd) {
		List list = null;
		list = casDao.findDebtByTimes2(debtMaster, times);
		DebtDetail debt = (DebtDetail) list.get(0);
		if (Integer.parseInt(times) <= 41) {
			if (beginEnd.equals("1")) {
				debt.setBeginValue1(values);
			} else {
				debt.setBeginValue2(values);
			}

		} else if (Integer.parseInt(times) >= 42) {
			if (beginEnd.equals("1")) {
				debt.setBeginValue2(values);
			} else {
				debt.setEndValue2(values);
			}

		}
		casDao.saveDebtDetail(debt);
	}

	/**
	 * 保存大类内容
	 * 
	 * @param statCusNameRelation
	 *            大类内容
	 * @return 临时大类内容
	 */
	public List<TempStatCusNameRelation> saveStatCusNameRelation(
			StatCusNameRelation statCusNameRelation) {
		this.casDao.saveStatCusNameRelation(statCusNameRelation);
		List<TempStatCusNameRelation> lsResult = new ArrayList<TempStatCusNameRelation>();
		combineRelation(lsResult, statCusNameRelation);
		return lsResult;
	}

	/**
	 * 删除资料主表
	 * 
	 * @param statCusNameRelation
	 *            大类内容资料主表
	 */
	public void deleteStatCusNameRelation(
			StatCusNameRelation statCusNameRelation) {
		if (statCusNameRelation == null) {
			return;
		}
		this.casDao.deleteAll(this.casDao
				.findStatCusNameRelationHsn(statCusNameRelation));
		//
		// 注意这里
		//
		// this.casDao.deleteAll(this.casDao
		// .findStatCusNameRelationMt(statCusNameRelation));
		this.casDao.deleteStatCusNameRelation(statCusNameRelation);
	}

	/**
	 * 删除资料主表
	 * 
	 * @param selectedRows
	 *            选中的大类内容
	 */
	public void deleteStatCusNameRelation(
			List<TempStatCusNameRelation> selectedRows) {
		for (int i = 0, n = selectedRows.size(); i < n; i++) {
			StatCusNameRelation statCusNameRelation = selectedRows.get(i)
					.getStatCusNameRelation();
			this.casDao.deleteStatCusNameRelationHsn(statCusNameRelation);
			this.casDao.deleteStatCusNameRelationMt(statCusNameRelation);
			this.casDao.deleteStatCusNameRelation(statCusNameRelation);
		}
	}

	/**
	 * 保存资料副表 实际报关商品
	 * 
	 * @param statCusNameRelation
	 *            大类内容 资料主表
	 * @param lsTempCommInfo
	 *            临时的商品信息
	 * @return 与商品大类相关联的实际报关商品
	 */
	public List<TempStatCusNameRelation> saveStatCusNameRelationHsn(
			StatCusNameRelation statCusNameRelation, List lsTempCommInfo) {
		for (int i = 0; i < lsTempCommInfo.size(); i++) {
			TempRelationCommInfo commInfo = (TempRelationCommInfo) lsTempCommInfo
					.get(i);
			StatCusNameRelationHsn hsn = new StatCusNameRelationHsn();
			// hsn.setStatCusNameRelation(statCusNameRelation);
			hsn.setSeqNum(commInfo.getSeqNum());
			hsn.setComplex(commInfo.getComplex());
			hsn.setCusName(commInfo.getCusName());
			hsn.setCusSpec(commInfo.getCusSpec());
			hsn.setCusUnit(commInfo.getCusUnit());
			hsn.setCompany(CommonUtils.getCompany());
			this.casDao.saveStatCusNameRelationHsn(hsn);
		}
		List<TempStatCusNameRelation> lsResult = new ArrayList<TempStatCusNameRelation>();
		combineRelation(lsResult, statCusNameRelation);
		return lsResult;
	}

	/**
	 * * 保存资料副表 工厂料件
	 * 
	 * @param statCusNameRelation
	 *            商品大类 资料主表
	 * @param lsTempCommInfo
	 *            临时的商品信息 企业物料
	 * @return 与商品大类相关联的工厂料件
	 */
	public List<TempStatCusNameRelation> saveStatCusNameRelationMt(
			StatCusNameRelation statCusNameRelation, List lsTempCommInfo) {
		for (int i = 0; i < lsTempCommInfo.size(); i++) {
			Materiel materiel = (Materiel) lsTempCommInfo.get(i);
			StatCusNameRelationMt mt = new StatCusNameRelationMt();
			mt.setStatCusNameRelation(statCusNameRelation);
			mt.setMateriel(materiel);
			this.casDao.saveStatCusNameRelationMt(mt);
		}
		List<TempStatCusNameRelation> lsResult = new ArrayList<TempStatCusNameRelation>();
		combineRelation(lsResult, statCusNameRelation);
		return lsResult;
	}

	/**
	 * 删除资料副表 实际报关商品
	 * 
	 * @param lsRelation
	 *            临时关系对照表
	 * @return 实际报关商品
	 */
	public List<TempStatCusNameRelation> deleteStatCusNameRelationHsn(
			List lsRelation) {
		StatCusNameRelation statCusNameRelation = null;
		for (int i = 0; i < lsRelation.size(); i++) {
			TempStatCusNameRelation relation = (TempStatCusNameRelation) lsRelation
					.get(i);
			if (i == 0) {
				statCusNameRelation = relation.getStatCusNameRelation();
			}
			StatCusNameRelationHsn hsn = relation.getStatCusNameRelationHsn();
			this.casDao.deleteStatCusNameRelationHsn(hsn);
		}
		List<TempStatCusNameRelation> lsResult = new ArrayList<TempStatCusNameRelation>();
		combineRelation(lsResult, statCusNameRelation);
		return lsResult;
	}

	/**
	 * * 删除资料副表 工厂料件
	 * 
	 * @param lsRelation
	 *            临时商品大类关系对照表
	 * @return 工厂物料
	 */
	public List<TempStatCusNameRelation> deleteStatCusNameRelationMt(
			List lsRelation) {
		StatCusNameRelation statCusNameRelation = null;
		for (int i = 0; i < lsRelation.size(); i++) {
			TempStatCusNameRelation relation = (TempStatCusNameRelation) lsRelation
					.get(i);
			if (i == 0) {
				statCusNameRelation = relation.getStatCusNameRelation();
			}
			StatCusNameRelationMt mt = relation.getStatCusNameRelationMt();
			this.casDao.deleteStatCusNameRelationMt(mt);
		}
		List<TempStatCusNameRelation> lsResult = new ArrayList<TempStatCusNameRelation>();
		combineRelation(lsResult, statCusNameRelation);
		return lsResult;
	}

	/**
	 * 抓取对照表主表
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return 与指定物料类型匹配的商品大类
	 */
	public List<TempStatCusNameRelation> findStatCusNameRelation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {

		List<TempStatCusNameRelation> lsResult = new ArrayList<TempStatCusNameRelation>();

		HashMap<String, List<StatCusNameRelationMt>> mapRelationMt = new HashMap<String, List<StatCusNameRelationMt>>();
		HashMap<String, List<StatCusNameRelationHsn>> mapRelationHsn = new HashMap<String, List<StatCusNameRelationHsn>>();
		HashMap<String, StatCusNameRelation> mapRelation = new HashMap<String, StatCusNameRelation>();
		List<StatCusNameRelationMt> lsRelationMt = this.casDao
				.findStatCusNameRelationMt(materielType, index, length,
						property, value, isLike);
		//
		// 生成 mapRelation and mapRelationMt
		//
		for (StatCusNameRelationMt relationMt : lsRelationMt) {
			StatCusNameRelation sr = relationMt.getStatCusNameRelation();
			mapRelation.put(sr.getId(), sr);
			if (!mapRelationMt.containsKey(sr.getId())) {
				List<StatCusNameRelationMt> mtList = new ArrayList<StatCusNameRelationMt>();
				mtList.add(relationMt);
				mapRelationMt.put(sr.getId(), mtList);
			} else {
				List<StatCusNameRelationMt> mtList = mapRelationMt.get(sr
						.getId());
				mtList.add(relationMt);
			}
		}
		//
		// 生成 大类为条件的查询报关单对象
		//

		//
		// 可以进行 ArrayList sort()
		//
		Iterator<StatCusNameRelation> iterator = mapRelation.values()
				.iterator();
		List<StatCusNameRelationHsn> lsRelationHsn = this.casDao
				.findStatCusNameRelationHsn(materielType, iterator);
		//
		// 生成 mapRelationHsn
		//
		for (StatCusNameRelationHsn relationHsn : lsRelationHsn) {
			//
			// 罗盛 2007/7/20 在这里屏蔽了这段代码 注意了
			//
			// StatCusNameRelation sr = relationHsn.getStatCusNameRelation();
			// if (!mapRelationHsn.containsKey(sr.getId())) {
			// List<StatCusNameRelationHsn> hsnList = new
			// ArrayList<StatCusNameRelationHsn>();
			// hsnList.add(relationHsn);
			// mapRelationHsn.put(sr.getId(), hsnList);
			// } else {
			// List<StatCusNameRelationHsn> hsnList = mapRelationHsn.get(sr
			// .getId());
			// hsnList.add(relationHsn);
			// }
		}
		iterator = mapRelation.values().iterator();
		while (iterator.hasNext()) {
			StatCusNameRelation relation = (StatCusNameRelation) iterator
					.next();
			combineRelation(lsResult, relation, mapRelationMt, mapRelationHsn);
		}

		// List<StatCusNameRelation> lsRelation = this.casDao
		// .findTempStatCusNameRelation(materielType, index, length,
		// property, value, isLike);
		// for (StatCusNameRelation relation : lsRelation) {
		// //
		// // 建index 解决下面效率问题
		// //
		// combineRelation(lsResult, relation);
		// }
		return lsResult;
	}

	/**
	 * 整合商品大类对照关系
	 * 
	 * @param lsResult
	 *            商品大类对照关系
	 * @param relation
	 *            商品大类
	 * @param mapRelationMt
	 *            映射企业物料
	 * @param mapRelationHsn
	 *            映射实际报关商品
	 */
	private void combineRelation(List<TempStatCusNameRelation> lsResult,
			StatCusNameRelation relation,
			HashMap<String, List<StatCusNameRelationMt>> mapRelationMt,
			HashMap<String, List<StatCusNameRelationHsn>> mapRelationHsn) {
		List<StatCusNameRelationMt> lsMaterial = mapRelationMt.get(relation
				.getId());
		List<StatCusNameRelationHsn> lsComplex = mapRelationHsn.get(relation
				.getId());
		String relationCode = relation.getComplex().getCode();
		int materialSize = lsMaterial.size();
		int complexSize = lsComplex.size();
		System.out.println(relationCode + "::::::::materialSize::::"
				+ materialSize);
		System.out.println(relationCode + "::::::::complexSize::::"
				+ complexSize);
		if (complexSize != 0 && materialSize != 0) {
			if (materialSize >= complexSize) {
				int ys = materialSize % complexSize;
				int sh = materialSize / complexSize;
				System.out.println(relationCode + "::::::::余数::::" + ys);
				System.out.println(relationCode + "::::::::商::::" + sh);
				int i = 0, j = 0;
				for (StatCusNameRelationMt mt : lsMaterial) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationMt(mt);
					i++;
					if (ys == 0) {
						if (i < sh) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
						}
						if (i == sh) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
							i = 0;
							j++;
						}
					} else {
						if (i < sh + 1) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
						}
						if (i == sh + 1) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
							i = 0;
							j++;
						}
					}
					System.out
							.println(relationCode
									+ "::::::::"
									+ temp.getStatCusNameRelationMt()
											.getMateriel().getPtNo()
									+ ":::::::::::::"
									+ (temp.getStatCusNameRelationHsn()
											.getComplex() == null ? "" : temp
											.getStatCusNameRelationHsn()
											.getComplex().getCode()));
					lsResult.add(temp);
				}
			} else {
				int ys = complexSize % materialSize;
				int sh = complexSize / materialSize;
				int i = 0, j = 0;
				for (StatCusNameRelationHsn hs : lsComplex) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationHsn(hs);
					i++;
					if (ys == 0) {
						if (i < sh) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
						}
						if (i == sh) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
							i = 0;
							j++;
						}
					} else {
						if (i < sh + 1) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
						}
						if (i == sh + 1) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
							i = 0;
							j++;
						}
					}
					System.out
							.println(relationCode
									+ "::::::::"
									+ temp.getStatCusNameRelationMt()
											.getMateriel().getPtNo()
									+ ":::::::::::::"
									+ (temp.getStatCusNameRelationHsn()
											.getComplex() == null ? "" : temp
											.getStatCusNameRelationHsn()
											.getComplex().getCode()));
					lsResult.add(temp);
				}
			}
		} else {
			if (complexSize == 0 && materialSize == 0) {
				TempStatCusNameRelation temp = new TempStatCusNameRelation();
				temp.setStatCusNameRelation(relation);
				lsResult.add(temp);
			} else if (complexSize == 0) {
				for (StatCusNameRelationMt mt : lsMaterial) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationMt(mt);
					lsResult.add(temp);
					System.out.println(relationCode
							+ "::::::::"
							+ temp.getStatCusNameRelationMt().getMateriel()
									.getPtNo() + "::::::::::::: NULL");
				}
			} else if (materialSize == 0) {
				for (StatCusNameRelationHsn hs : lsComplex) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationHsn(hs);
					lsResult.add(temp);
					System.out
							.println(relationCode
									+ "::::::::NULL:::::::::::::"
									+ (temp.getStatCusNameRelationHsn()
											.getComplex() == null ? "" : temp
											.getStatCusNameRelationHsn()
											.getComplex().getCode()));
				}
			}
		}
	}

	/**
	 * 抓取对照表主表
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 与物料类型匹配的对照主表
	 */
	public List findStatCusNameRelation(String materialType) {
		List<TempStatCusNameRelation> lsResult = new ArrayList<TempStatCusNameRelation>();
		List<StatCusNameRelation> lsRelation = new ArrayList<StatCusNameRelation>();
		lsRelation.addAll(this.casDao
				.findTempStatCusNameRelation1(materialType));
		lsRelation.addAll(this.casDao
				.findTempStatCusNameRelation2(materialType));
		lsRelation.addAll(this.casDao
				.findTempStatCusNameRelation3(materialType));
		lsRelation.addAll(this.casDao
				.findTempStatCusNameRelation4(materialType));
		for (StatCusNameRelation relation : lsRelation) {
			combineRelation(lsResult, relation);
		}
		return lsResult;
	}

	/**
	 * 整合商品大类对照关系表
	 * 
	 * @param lsResult
	 *            临时商品大类关系对照主表
	 * @param relation
	 *            商品大类内容
	 */
	private void combineRelation(List<TempStatCusNameRelation> lsResult,
			StatCusNameRelation relation) {
		List<StatCusNameRelationMt> lsMaterial = new ArrayList<StatCusNameRelationMt>();

		// this.casDao
		// .findStatCusNameRelationMt(relation);
		List<StatCusNameRelationHsn> lsComplex = this.casDao
				.findStatCusNameRelationHsn(relation);
		String relationCode = relation.getComplex().getCode();
		int materialSize = lsMaterial.size();
		int complexSize = lsComplex.size();
		System.out.println(relationCode + "::::::::materialSize::::"
				+ materialSize);
		System.out.println(relationCode + "::::::::complexSize::::"
				+ complexSize);
		if (complexSize != 0 && materialSize != 0) {
			if (materialSize >= complexSize) {
				int ys = materialSize % complexSize;
				int sh = materialSize / complexSize;
				System.out.println(relationCode + "::::::::余数::::" + ys);
				System.out.println(relationCode + "::::::::商::::" + sh);
				int i = 0, j = 0;
				for (StatCusNameRelationMt mt : lsMaterial) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationMt(mt);
					i++;
					if (ys == 0) {
						if (i < sh) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
						}
						if (i == sh) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
							i = 0;
							j++;
						}
					} else {
						if (i < sh + 1) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
						}
						if (i == sh + 1) {
							temp.setStatCusNameRelationHsn(lsComplex.get(j));
							i = 0;
							j++;
						}
					}
					System.out
							.println(relationCode
									+ "::::::::"
									+ temp.getStatCusNameRelationMt()
											.getMateriel().getPtNo()
									+ ":::::::::::::"
									+ (temp.getStatCusNameRelationHsn()
											.getComplex() == null ? "" : temp
											.getStatCusNameRelationHsn()
											.getComplex().getCode()));
					lsResult.add(temp);
				}
			} else {
				int ys = complexSize % materialSize;
				int sh = complexSize / materialSize;
				int i = 0, j = 0;
				for (StatCusNameRelationHsn hs : lsComplex) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationHsn(hs);
					i++;
					if (ys == 0) {
						if (i < sh) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
						}
						if (i == sh) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
							i = 0;
							j++;
						}
					} else {
						if (i < sh + 1) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
						}
						if (i == sh + 1) {
							temp.setStatCusNameRelationMt(lsMaterial.get(j));
							i = 0;
							j++;
						}
					}
					System.out
							.println(relationCode
									+ "::::::::"
									+ temp.getStatCusNameRelationMt()
											.getMateriel().getPtNo()
									+ ":::::::::::::"
									+ (temp.getStatCusNameRelationHsn()
											.getComplex() == null ? "" : temp
											.getStatCusNameRelationHsn()
											.getComplex().getCode()));
					lsResult.add(temp);
				}
			}
		} else {
			if (complexSize == 0 && materialSize == 0) {
				TempStatCusNameRelation temp = new TempStatCusNameRelation();
				temp.setStatCusNameRelation(relation);
				lsResult.add(temp);
			} else if (complexSize == 0) {
				for (StatCusNameRelationMt mt : lsMaterial) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationMt(mt);
					lsResult.add(temp);
					System.out.println(relationCode
							+ "::::::::"
							+ temp.getStatCusNameRelationMt().getMateriel()
									.getPtNo() + "::::::::::::: NULL");
				}
			} else if (materialSize == 0) {
				for (StatCusNameRelationHsn hs : lsComplex) {
					TempStatCusNameRelation temp = new TempStatCusNameRelation();
					temp.setStatCusNameRelation(relation);
					temp.setStatCusNameRelationHsn(hs);
					lsResult.add(temp);
					System.out
							.println(relationCode
									+ "::::::::NULL:::::::::::::"
									+ (temp.getStatCusNameRelationHsn()
											.getComplex() == null ? "" : temp
											.getStatCusNameRelationHsn()
											.getComplex().getCode()));
				}
			}
		}
	}

	/**
	 * 新增对照关系时，选择物料
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 与商品大类关联的物料内容
	 */
	public List<Materiel> findMaterialForRelation(String materialType) {
		return this.casDao.findMaterialForRelation(materialType);
	}

	/**
	 * 新增对照关系时，选择报关商品
	 * 
	 * @param materialType
	 *            物料类型
	 * @return 与商品大类关联的实际报关商品内容
	 */
	public List findComplexForRelation(String materialType) {
		List lsResult = new ArrayList();
		List lsKey = new ArrayList();
		List lsTemp = new ArrayList();
		lsTemp = this.casDao.findComplexForRelation(ProjectType.BCUS,
				materialType);
		filterDupleCommInfo(lsResult, lsKey, lsTemp);
		lsTemp = this.casDao.findComplexForRelation(ProjectType.BCS,
				materialType);
		filterDupleCommInfo(lsResult, lsKey, lsTemp);
		filterExistCommInfo(lsResult, materialType);
		return lsResult;
	}

	/**
	 * 去掉重复的记录
	 * 
	 * @param lsResult
	 * @param lsKey
	 *            序号+商品编码
	 * @param lsTemp
	 *            临时数组
	 */
	private void filterDupleCommInfo(List lsResult, List lsKey, List lsTemp) {
		for (int i = 0; i < lsTemp.size(); i++) {
			Object[] objs = (Object[]) lsTemp.get(i);
			String commSerialNo = "";
			String complexCode = "";
			Integer commSerialNoInt = (Integer) objs[0];
			Complex complex = (Complex) objs[1];
			if (commSerialNoInt != null) {
				commSerialNo = commSerialNoInt.toString();
			}
			if (complex != null) {
				complexCode = complex.getCode();
			}
			if ((commSerialNo + complexCode).equals("")) {
				continue;
			}
			if (lsKey.contains(commSerialNo + complexCode)) {
				continue;
			}
			TempRelationCommInfo commInfo = new TempRelationCommInfo();
			commInfo.setSeqNum(commSerialNoInt);
			commInfo.setComplex(complex);
			if (objs[2] != null) {
				commInfo.setCusName(objs[2].toString());
			}
			if (objs[3] != null) {
				commInfo.setCusSpec(objs[3].toString());
			}
			commInfo.setCusUnit((Unit) objs[4]);
			lsResult.add(commInfo);
			lsKey.add(commSerialNo + complexCode);
		}
	}

	/**
	 * 将已经在对应表中存在的商品信息过滤掉
	 * 
	 * @param lsResult
	 *            对照关系主表公用内容
	 * @param materialType
	 *            物料类型
	 */
	private void filterExistCommInfo(List lsResult, String materialType) {
		List<StatCusNameRelationHsn> lsTemp = this.casDao
				.findStatCusNameRelationHsn(materialType);
		List<Object> lsKey = new ArrayList<Object>();

		for (StatCusNameRelationHsn hsn : lsTemp) {
			lsKey.add(hsn.getSeqNum().toString().trim()
					+ hsn.getComplex().getCode().trim());
		}
		for (int i = lsResult.size() - 1; i >= 0; i--) {
			TempRelationCommInfo commInfo = (TempRelationCommInfo) lsResult
					.get(i);
			if (checkExist(lsKey, commInfo.getSeqNum().toString().trim()
					+ commInfo.getComplex().getCode().trim())) {
				lsResult.remove(commInfo);
			}
		}
	}

	/**
	 * 判断obj在lsKey中是否存在
	 * 
	 * @param lsKey
	 * @param obj
	 * @return
	 */
	private boolean checkExist(List lsKey, Object obj) {
		return lsKey.contains(obj);
	}

	/**
	 * 当删除物料或者报关商品的时候,检查所选择的资料是否属于同一个大类,如果true,允许删除, 否则不能删除
	 * 
	 * @param list
	 *            临时商品对照关系表
	 * @return 如果true,允许删除, 否则不能删除
	 */
	public boolean checkIsSameRelation(List<TempStatCusNameRelation> list) {
		boolean isSame = true;
		StatCusNameRelation s = list.get(0).getStatCusNameRelation();
		for (TempStatCusNameRelation temp : list) {
			if (!s.equals(temp.getStatCusNameRelation())) {
				isSame = false;
				break;
			}
		}
		return isSame;
	}

	/**
	 * 当删除修改物料的时候,检查所选择的资料是否属于同一个物料,如果true,允许修改, 否则不能修改
	 * 
	 * @param list
	 *            对照关系表中的物料
	 * @return true,允许修改, 否则不能修改
	 */
	public boolean checkIsSameMt(List<TempStatCusNameRelation> list) {
		boolean isSame = true;
		StatCusNameRelationMt s = list.get(0).getStatCusNameRelationMt();
		for (TempStatCusNameRelation temp : list) {
			if (!s.equals(temp.getStatCusNameRelationMt())) {
				isSame = false;
				break;
			}
		}
		return isSame;
	}

	/**
	 * 当删除修改报关商品的时候,检查所选择的资料是否属于同一个报关商品,如果true,允许修改, 否则不能修改
	 * 
	 * @param list
	 *            对照关系中的实际报关商品
	 * @return true,允许修改, 否则不能修改
	 */
	public boolean checkIsSameHsn(List<TempStatCusNameRelation> list) {
		boolean isSame = true;
		StatCusNameRelationHsn s = list.get(0).getStatCusNameRelationHsn();
		for (TempStatCusNameRelation temp : list) {
			if (!s.equals(temp.getStatCusNameRelationHsn())) {
				isSame = false;
				break;
			}
		}
		return isSame;
	}

	/**
	 * 转换双精度
	 * 
	 * @param dou
	 *            双精度型
	 * @return 双精度
	 */
	public Double formatDouble(Double dou) {
		if (dou != null) {
			return dou;
		} else {
			return new Double(0);
		}
	}

	/**
	 * 取得海关帐单据的入出汇总
	 * 
	 * @param intOutFlat
	 *            物料类型
	 * @param conditions
	 *            查询条件
	 * @param conditions1
	 *            查询条件
	 * @return 单据明细
	 */
	public List getCasSum(String materielType, List<Condition> conditions,
			boolean showZeroStore, boolean showVersion) {
		String className = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);// getIntOutFlat()
		// 是 成品 并且选了 分版本统计
		boolean isProductAndshowVerion = MaterielType.FINISHED_PRODUCT
				.equals(materielType) && (showVersion == true);

		String inOutSelects = "select a.ptPart,a.ptName,a.ptSpec,a.ptUnit.name,a.wareSet.name,a.hsUnit.name,a.hsName,a.hsSpec,a.complex.code,a.billMaster.billType.wareType,sum(a.ptAmount),sum(a.hsAmount)"
				+ (isProductAndshowVerion == true ? ",a.version " : "");// wss2010.06.02成品还须分版本
		String leftOuter = " left join a.complex left join  a.ptUnit	left join a.wareSet left join  a.hsUnit  left join  a.billMaster.billType ";
		String inOutGroupBy = "group by a.ptPart,a.ptName,a.ptSpec,a.ptUnit.name,a.wareSet.name,a.hsUnit.name,a.hsName,a.hsSpec,a.complex.code ,a.billMaster.billType.wareType "
				+ (isProductAndshowVerion == true ? ",a.version " : "");// wss2010.06.02成品还须分版本
		if (MaterielType.MATERIEL.equals(materielType)) {
			// conditions.add(new Condition("and", "(",
			// "billMaster.billType.code", "!=", "1013", ")")); // 外发加工返回料件
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1014", ")")); // 委外期初单
			// 1014
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1015", ")")); // 已收货未结转期初单

			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1016", ")")); // 已结转未收货期初单
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "1002", ")")); // 在产品期初单
		}
		if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "2011", ")")); // 已交货未结转期初单

			conditions.add(new Condition("and", "(",
					"billMaster.billType.code", "!=", "2012", ")")); // 已结转未交货期初单
		}
		List list = casDao.commonSearch(inOutSelects, className, conditions,
				inOutGroupBy, leftOuter);

		Map<String, BillTemp> resultMap = new HashMap<String, BillTemp>();

		for (int i = 0, n = list.size(); i < n; i++) {
			Object[] obj = (Object[]) list.get(i);
			String ptPart = (String) (obj[0]);
			String wareSetName = (String) (obj[4]) == null ? ""
					: (String) (obj[4]);

			String hsName = (String) (obj[6]) == null ? "" : (String) (obj[6]);

			String hsSpec = (String) (obj[7]) == null ? "" : (String) (obj[7]);

			Integer wareType = (Integer) (obj[9]);

			String key = ptPart + wareSetName + hsName + hsSpec
					+ (isProductAndshowVerion == true ? (Integer) obj[12] : "");

			Double sumPtAmount = 0.0;
			Double sumHsAmount = 0.0;
			if (wareType.intValue() == 1) {// import storehouse
				sumPtAmount = formatDouble((Double) (obj[10]));
				sumHsAmount = formatDouble((Double) (obj[11]));
			} else if (wareType.intValue() == 2) {// export storehouse
				sumPtAmount = -formatDouble((Double) (obj[10]));
				sumHsAmount = -formatDouble((Double) (obj[11]));
			}

			if (!resultMap.containsKey(key)) {
				BillTemp temp = new BillTemp();
				// wss2010.06.02成品还须分版本
				temp.setBill1((String) (obj[0])
						+ (isProductAndshowVerion == true ? "("
								+ (Integer) obj[12] + ")" : ""));
				temp.setBill2((String) (obj[1]));
				temp.setBill3((String) (obj[2]));
				temp.setBill4((String) (obj[3]));
				temp.setBill5((String) (obj[4]));
				temp.setBill6((String) (obj[5]));
				temp.setBill7((String) (obj[6]));
				temp.setBill8((String) (obj[7]));
				temp.setBill9((String) (obj[8])); // 商品编码
				temp.setBillSum1(sumPtAmount);
				temp.setBillSum2(sumHsAmount);
				resultMap.put(key, temp);
			} else {
				BillTemp temp = resultMap.get(key);
				temp.setBillSum1(temp.getBillSum1() + sumPtAmount);
				temp.setBillSum2(temp.getBillSum2() + sumHsAmount);
			}
		}
		List<BillTemp> returnList = new ArrayList<BillTemp>();

		if (showZeroStore == false) {
			Iterator<BillTemp> iterator = resultMap.values().iterator();
			while (iterator.hasNext()) {
				BillTemp temp = iterator.next();
				if (temp.getBillSum1().doubleValue() != 0.0) {
					returnList.add(temp);
				}
			}
		} else {
			returnList.addAll(resultMap.values());
		}

		return returnList;
	}

	/**
	 * 取得本月数量
	 * 
	 * @param materielType
	 *            物料类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isRefresh
	 *            重新计算本月前的库存数量(这里只计算当月的前一个月的库存数量)
	 * @return 返回本月数量
	 */
	public List getMonth(String materielType, Date beginDate, Date endDate,
			boolean isRefresh) {

		/** 成品,料件,半成品 * */

		/**
		 * **查找本月单据
		 */
		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		int month = endDate.getMonth() + 1;
		String inOutSelects = "select a.ptPart,a.ptName,a.ptSpec,a.billMaster.billType.wareType,sum(a.ptAmount),sum(a.netWeight)";
		String inOutGroupBy = "group by a.ptPart,a.ptName,a.ptSpec,a.billMaster.billType.wareType ";
		String leftOuter = " left join  a.billMaster left join  a.billMaster.billType ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));

		// 料件 应该去除 以下单据
		if (MaterielType.MATERIEL.equals(materielType)) {
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1013", null));// 1013
			// 外发加工返回料件
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1014", null));// 1014
			// 委外期初单
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1015", null));// 1015
			// 已收货未结转期初单
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1016", null));// 1016
			// 已结转未收货期初单

			if (month == 1) {// 如果是一月份,则应该把初期单除开。应该体现在上月结存中。
				conditions.add(new Condition("and", null,
						"billMaster.billType.code", "!=", "1001", null));// 1001
				// 料件期初单
				conditions.add(new Condition("and", null,
						"billMaster.billType.code", "!=", "1002", null));// 1002
				// 在产品期初单
			}
		}

		// 成品 应该去除 以下单据
		else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "2011", null));// 2011
			// 已交货未结转期初单
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "2012", null));// 2012
			// 已结转未交货期初单

			if (month == 1) {// 如果是一月份,则应该把初期单除开。应该体现在上月结存中。
				conditions.add(new Condition("and", null,
						"billMaster.billType.code", "!=", "2001", null));// 2001
				// 成品期初单
			}
		}

		// 查询分组后的结果集(再按 料号 （ 进 - 出 ）进行汇总)
		List result = casDao.commonSearch(inOutSelects, billDetail, conditions,
				inOutGroupBy, leftOuter);

		List<TempMonthStorage> returnList = new ArrayList<TempMonthStorage>();

		Map<String, TempMonthStorage> resultMap = new HashMap<String, TempMonthStorage>();

		for (int i = 0, n = result.size(); i < n; i++) {
			Object[] obj = (Object[]) result.get(i);
			String key = (String) (obj[0]); // 料号
			Integer wareType = (Integer) (obj[3]); // 进出仓

			if (!resultMap.containsKey(key)) {
				TempMonthStorage temp = new TempMonthStorage();
				temp.setPtPart(key);// 料号
				temp.setPtName((String) (obj[1]));// 名称
				temp.setPtSpec((String) (obj[2]));// 规格

				if (wareType.intValue() == 1) {// import storehouse
					temp.setImportPtAmount(formatDouble((Double) (obj[4])));
					temp.setImportNetWeight(formatDouble((Double) (obj[5])));
				} else if (wareType.intValue() == 2) {// export storehouse
					temp.setExportPtAmount(formatDouble((Double) (obj[4])));
					temp.setExportNetWeight(formatDouble((Double) (obj[5])));
				}
				resultMap.put(key, temp);
			} else {
				TempMonthStorage temp = resultMap.get(key);
				if (wareType.intValue() == 1) {// import storehouse
					temp.setImportPtAmount(temp.getImportPtAmount()
							+ formatDouble((Double) (obj[4])));
					temp.setImportNetWeight(temp.getImportNetWeight()
							+ formatDouble((Double) (obj[5])));
				} else if (wareType.intValue() == 2) {// export storehouse
					temp.setExportPtAmount(temp.getExportPtAmount()
							+ formatDouble((Double) (obj[4])));
					temp.setExportNetWeight(temp.getExportNetWeight()
							+ formatDouble((Double) (obj[5])));
				}
			}
		}

		/**
		 * **第一月份不用计算上月结存，期初单就是上月结存
		 */
		if (month == 1) {
			String inOutSelects1 = "select a.ptPart,a.ptName,a.ptSpec,a.billMaster.billType.wareType,sum(a.ptAmount),sum(a.netWeight)";
			String inOutGroupBy1 = "group by a.ptPart,a.ptName,a.ptSpec,a.billMaster.billType.wareType ";
			String leftOuter1 = " left join  a.billMaster left join  a.billMaster.billType ";
			List<Condition> conditions1 = new ArrayList<Condition>();
			conditions1.add(new Condition("and", null, "billMaster.isValid",
					"=", new Boolean(true), null));
			conditions1.add(new Condition("and", null, "billMaster.validDate",
					">=", beginDate, null));
			conditions1.add(new Condition("and", null, "billMaster.validDate",
					"<=", endDate, null));
			if (MaterielType.MATERIEL.equals(materielType)) {
				conditions1.add(new Condition("and", null,
						"billMaster.billType.code", " =", "1001", null));// 1001料件期初单
			} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
				conditions1.add(new Condition(" and  ", null,
						"billMaster.billType.code", " =", "2001", null));// 2001成品期初单
			}
			List result1 = casDao.commonSearch(inOutSelects1, billDetail,
					conditions1, inOutGroupBy1, leftOuter1);

			for (int i = 0, n = result1.size(); i < n; i++) {
				Object[] obj = (Object[]) result1.get(i);
				String key = (String) (obj[0]); // 料号
				Integer wareType = (Integer) (obj[3]); // 进出仓

				if (!resultMap.containsKey(key)) {
					TempMonthStorage temp = new TempMonthStorage();
					temp.setPtPart(key);// 料号
					temp.setPtName((String) (obj[1]));// 名称
					temp.setPtSpec((String) (obj[2]));// 规格

					if (wareType.intValue() == 1) {// import storehouse
						temp.setFrontMonthPtAmount(formatDouble((Double) (obj[4])));
						temp.setFrontMonthNetWeight(formatDouble((Double) (obj[5])));
					}
					resultMap.put(key, temp);
				} else {
					TempMonthStorage temp = resultMap.get(key);
					if (wareType.intValue() == 1) {// import storehouse
						temp.setFrontMonthPtAmount(temp.getFrontMonthPtAmount()
								+ formatDouble((Double) (obj[4])));
						temp.setFrontMonthNetWeight(temp
								.getFrontMonthNetWeight()
								+ formatDouble((Double) (obj[5])));
					}
				}
			}

			// 是本月的最后一天，则需要保存
			if (isRefresh == true) {
				batchSaveMonthStorage(materielType, returnList, resultMap,
						month);
			} else {
				returnList.addAll(resultMap.values());
			}
			return returnList;
		}

		/**
		 * **** 如果不是一月份 查找数据库中已有的上月份结存记录
		 */
		int frontMonth = month - 1;

		/**
		 * **** 因为是按月份从小到大来遍历计算的，所以上月一定是计算好了的
		 */
		List<MonthStorage> frontMonthList = this.casDao
				.findMonthStorageByMonth(materielType, frontMonth);

		for (int i = 0, n = frontMonthList.size(); i < n; i++) {
			MonthStorage monthStorage = frontMonthList.get(i);
			String key = monthStorage.getPtPart();

			// 不存在本月的物料新增到结果集中
			if (!resultMap.containsKey(key)) {
				TempMonthStorage tempMonthStorage = new TempMonthStorage();
				tempMonthStorage.setPtPart(key);
				tempMonthStorage.setPtName(monthStorage.getPtName());
				tempMonthStorage.setPtSpec(monthStorage.getPtSpec());
				tempMonthStorage.setFrontMonthPtAmount(monthStorage
						.getPtAmount());
				tempMonthStorage.setFrontMonthNetWeight(monthStorage
						.getNetWeight());
				resultMap.put(key, tempMonthStorage);
			} else {
				TempMonthStorage tempMonthStorage = resultMap.get(key);
				tempMonthStorage.setFrontMonthPtAmount(monthStorage
						.getPtAmount());
				tempMonthStorage.setFrontMonthNetWeight(monthStorage
						.getNetWeight());
			}
		}

		// 是本月的最后一天
		if (isRefresh == true) {
			batchSaveMonthStorage(materielType, returnList, resultMap, month);
		} else {
			returnList.addAll(resultMap.values());
		}
		return returnList;
	}

	/**
	 * (notExistList ==null) 要全部重新生成以前月的记录 (notExistList !=null) 生成不存在的月报表
	 * 
	 * @param materielType
	 * @param notExistList
	 * @param beginDate
	 * @param endDate
	 * @param isRefresh
	 * @return
	 */
	public List getAllMonth(String materielType, List<Integer> notExistList,
			Date beginDate, Date endDate, boolean isRefresh) {
		int currentMonth = endDate.getMonth();
		int cenrentYear = endDate.getYear() + 1900;

		// 遍历 当前月 前面的所有月份
		for (int i = 0; i < currentMonth; i++) {
			//
			// 这里无须关心效率,因为个数很小
			//
			// 如果此月已经有记录了，就不生成记录
			if (notExistList != null && !notExistList.contains(i + 1)) {
				continue;
			}

			// 月份第一天
			Calendar beginCalendar = Calendar.getInstance();
			beginCalendar.set(Calendar.YEAR, cenrentYear);
			beginCalendar.set(Calendar.MONTH, i);
			beginCalendar.set(Calendar.DAY_OF_MONTH, 1);

			// 月份最后一天
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.set(Calendar.YEAR, cenrentYear);
			endCalendar.set(Calendar.MONTH, i);
			//
			// 这一句是为了获得当前月的最大值
			//
			endCalendar.set(Calendar.DAY_OF_MONTH, 1);
			endCalendar.set(Calendar.DAY_OF_MONTH,
					endCalendar.getActualMaximum(Calendar.DATE));
			//
			// 全部进行保存
			//
			getMonth(materielType, beginCalendar.getTime(),
					endCalendar.getTime(), true);

			// System.out.println((beginCalendar.get(Calendar.MONTH)+1) + " " +
			// (endCalendar.get(Calendar.MONTH)+1));
		}
		//
		// 返回当前月的记录
		//
		// System.out.println((beginDate.getMonth()+1) + " ---- " +
		// (endDate.getMonth()+1));
		return getMonth(materielType, beginDate, endDate, isRefresh);
	}

	/**
	 * 批量保存当前月的库存记录
	 * 
	 * @param materielType
	 *            物料类型
	 * @param returnList
	 *            本月库存数量
	 * @param resultMap
	 *            本月库存数量(map)
	 * @param month
	 *            月数
	 */
	private void batchSaveMonthStorage(String materielType,
			List<TempMonthStorage> returnList,
			Map<String, TempMonthStorage> resultMap, int month) {
		//
		// 如果这个查询的结束日期是本月的最后一个日期,就要先删本月的记录,保存本月结存记录
		//
		this.casDao.batchDeleteMonthStorage(materielType, month);

		List<MonthStorage> batchList = new ArrayList<MonthStorage>();
		int batchSize = 100;
		int i = 0;

		Iterator<TempMonthStorage> iterator = resultMap.values().iterator();
		while (iterator.hasNext()) {
			TempMonthStorage temp = iterator.next();
			MonthStorage monthStorage = new MonthStorage();
			monthStorage.setCompany(CommonUtils.getCompany());
			monthStorage.setMaterielType(materielType);
			monthStorage.setMonth(month);
			monthStorage.setPtPart(temp.getPtPart());
			monthStorage.setPtName(temp.getPtName());
			monthStorage.setPtSpec(temp.getPtSpec());
			monthStorage.setPtAmount(temp.getCurrentMonthPtAmount());
			monthStorage.setNetWeight(temp.getCurrentMonthNetWeight());
			if (i % batchSize == 0 && i != 0) {
				this.casDao.batchSaveMonthStorage(batchList);
				batchList.clear();
			}
			batchList.add(monthStorage);
			returnList.add(temp);
			i++;
		}
		if (batchList.size() > 0) {
			this.casDao.batchSaveMonthStorage(batchList);
			batchList.clear();
		}
	}

	/**
	 * 查询所有当点月存在的
	 * 
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            本月
	 * @return 当前月存在的
	 */
	public List<Integer> findFrontMonthIsNotExist(String materielType,
			int currentMonth) {
		List<Integer> existList = this.casDao.findFrontMonthIsExist(
				materielType, currentMonth);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < existList.size(); i++) {
			map.put(existList.get(i), existList.get(i));
		}
		List<Integer> returnList = new ArrayList<Integer>();
		for (int i = 1; i < currentMonth; i++) {// 12 个月
			if (map.get(i) == null) {
				returnList.add(i);
			}
		}
		return returnList;
	}

	/**
	 * ------------------------------------ 报关信息月报表
	 * --------------------------------------
	 */
	/**
	 * 取得本月数量
	 * 
	 * @param materielType
	 *            物料类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isRefresh
	 *            重新计算本月前的库存数量(这里只计算当月的前一个月的库存数量)
	 * @return 返回本月数量
	 */
	public List getMonth2(String materielType, Date beginDate, Date endDate,
			boolean isRefresh) {
		//
		// 成品,料件,半成品
		//
		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);

		System.out.println("billDetail=" + billDetail);

		int month = endDate.getMonth() + 1;
		String inOutSelects = "select a.hsUnit.name,a.hsName,a.hsSpec,a.billMaster.billType.wareType,sum(a.hsAmount),sum(a.netWeight)";
		String inOutGroupBy = "group by a.hsUnit.name,a.hsName,a.hsSpec,a.billMaster.billType.wareType ";
		String leftOuter = " left join  a.billMaster left join  a.billMaster.billType left join a.hsUnit ";
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				new Boolean(true), null));
		conditions.add(new Condition("and", null, "billMaster.validDate", ">=",
				beginDate, null));
		conditions.add(new Condition("and", null, "billMaster.validDate", "<=",
				endDate, null));
		conditions.add(new Condition("and", null, "hsUnit is not null", null,
				null, null));
		//
		// 去掉外发加工返回料件(由于这个是半成品折算回来的)
		//
		// code="1013" name="外发加工返回料件"
		if (MaterielType.MATERIEL.equals(materielType)) {
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1013", null));
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1014", null));
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1015", null));
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "1016", null));

			if (month == 1) {// 如果是一月份,则应该把初期单除开。应该体现在上月结存中。
				conditions.add(new Condition("and", null,
						"billMaster.billType.code", "!=", "1001", null));

				conditions.add(new Condition("and", null,
						"billMaster.billType.code", "!=", "1002", null));
			}
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "2011", null));
			conditions.add(new Condition("and", null,
					"billMaster.billType.code", "!=", "2012", null));
			if (month == 1) {// 如果是一月份,则应该把初期单除开。应该体现在上月结存中。
				conditions.add(new Condition("and", null,
						"billMaster.billType.code", "!=", "2001", null));
			}
		}

		//
		// 查询分组后的结果集(再按进出进行汇总)
		//
		List result = casDao.commonSearch(inOutSelects, billDetail, conditions,
				inOutGroupBy, leftOuter);

		List<TempMonthStorage> returnList = new ArrayList<TempMonthStorage>();

		Map<String, TempMonthStorage> resultMap = new HashMap<String, TempMonthStorage>();

		for (int i = 0, n = result.size(); i < n; i++) {
			//
			// 报关名称 +"/"+ 规格 +"/"+ 单位名称
			//
			Object[] obj = (Object[]) result.get(i);
			String hsName = obj[1] == null ? "" : (String) (obj[1]); // 报关名称
			String hsSpec = obj[2] == null ? "" : (String) (obj[2]); // 报关规格
			String hsUnitName = obj[0] == null ? "" : (String) (obj[0]); // 报关名称

			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			// String key = hsName + hsSpec + hsUnitName;
			Integer wareType = (Integer) (obj[3]); // 进出仓

			if (!resultMap.containsKey(key)) {
				TempMonthStorage temp = new TempMonthStorage();
				temp.setPtPart(hsUnitName);// 料号
				temp.setPtName(hsName);// 名称
				temp.setPtSpec(hsSpec);// 规格

				if (wareType.intValue() == 1) {// import storehouse
					temp.setImportPtAmount(formatDouble((Double) (obj[4])));
					temp.setImportNetWeight(formatDouble((Double) (obj[5])));
				} else if (wareType.intValue() == 2) {// export storehouse
					temp.setExportPtAmount(formatDouble((Double) (obj[4])));
					temp.setExportNetWeight(formatDouble((Double) (obj[5])));
				}
				resultMap.put(key, temp);
			} else {
				TempMonthStorage temp = resultMap.get(key);
				if (wareType.intValue() == 1) {// import storehouse
					temp.setImportPtAmount(temp.getImportPtAmount()
							+ formatDouble((Double) (obj[4])));
					temp.setImportNetWeight(temp.getImportNetWeight()
							+ formatDouble((Double) (obj[5])));
				} else if (wareType.intValue() == 2) {// export storehouse
					temp.setExportPtAmount(temp.getExportPtAmount()
							+ formatDouble((Double) (obj[4])));
					temp.setExportNetWeight(temp.getExportNetWeight()
							+ formatDouble((Double) (obj[5])));
				}
			}
		}
		//
		// 如果是第一个月,那么不用计算上月期初
		//
		if (month == 1) {
			String inOutSelects1 = "select a.hsUnit.name,a.hsName,a.hsSpec,a.billMaster.billType.wareType,sum(a.hsAmount),sum(a.netWeight)";
			String inOutGroupBy1 = "group by a.hsUnit.name,a.hsName,a.hsSpec,a.billMaster.billType.wareType ";
			String leftOuter1 = " left join  a.billMaster left join  a.billMaster.billType left join a.hsUnit ";
			List<Condition> conditions1 = new ArrayList<Condition>();
			conditions1.add(new Condition("and", null, "billMaster.isValid",
					"=", new Boolean(true), null));
			conditions1.add(new Condition("and", null, "billMaster.validDate",
					">=", beginDate, null));
			conditions1.add(new Condition("and", null, "billMaster.validDate",
					"<=", endDate, null));
			conditions1.add(new Condition("and", null, "hsUnit is not null",
					null, null, null));

			if (MaterielType.MATERIEL.equals(materielType)) {
				conditions1.add(new Condition("and", null,
						"billMaster.billType.code", " =", "1001", null));// 条件，只查找料件期初单
			} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
				conditions1.add(new Condition(" and  ", null,
						"billMaster.billType.code", " =", "2001", null));// 条件，或者成品期初单
			}
			List result1 = casDao.commonSearch(inOutSelects1, billDetail,
					conditions1, inOutGroupBy1, leftOuter1);

			for (int i = 0, n = result1.size(); i < n; i++) {
				//
				// 报关名称 +"/"+ 规格 +"/"+ 单位名称
				//
				Object[] obj = (Object[]) result1.get(i);
				String hsName = obj[1] == null ? "" : (String) (obj[1]); // 报关名称
				String hsSpec = obj[2] == null ? "" : (String) (obj[2]); // 报关规格
				String hsUnitName = obj[0] == null ? "" : (String) (obj[0]); // 报关名称

				String key = hsName + "/" + hsSpec + "/" + hsUnitName;
				// String key = hsName + hsSpec + hsUnitName;
				Integer wareType = (Integer) (obj[3]); // 进出仓

				if (!resultMap.containsKey(key)) {
					TempMonthStorage temp = new TempMonthStorage();
					temp.setPtPart(hsUnitName);// 料号
					temp.setPtName(hsName);// 名称
					temp.setPtSpec(hsSpec);// 规格

					if (wareType.intValue() == 1) {// import storehouse
						temp.setFrontMonthPtAmount(formatDouble((Double) (obj[4])));
						temp.setFrontMonthNetWeight(formatDouble((Double) (obj[5])));
					}
					resultMap.put(key, temp);
				} else {
					TempMonthStorage temp = resultMap.get(key);
					if (wareType.intValue() == 1) {// import storehouse
						temp.setFrontMonthPtAmount(temp.getFrontMonthPtAmount()
								+ formatDouble((Double) (obj[4])));
						temp.setFrontMonthNetWeight(temp
								.getFrontMonthNetWeight()
								+ formatDouble((Double) (obj[5])));
					}
				}
			}
			//
			// 是本月的最后一天
			//
			if (isRefresh == true) {
				batchSaveMonthStorage2(materielType, returnList, resultMap,
						month);
			} else {
				returnList.addAll(resultMap.values());
			}
			return returnList;
		}
		//
		// 查找数据库中已有的上月份结存记录
		//
		int frontMonth = month - 1;
		//
		// key = 料号
		//
		List<MonthStorage2> frontMonthList = this.casDao
				.findMonthStorageByMonth2(materielType, frontMonth);
		for (int i = 0, n = frontMonthList.size(); i < n; i++) {
			MonthStorage2 monthStorage = frontMonthList.get(i);
			String hsName = monthStorage.getPtName() == null ? ""
					: monthStorage.getPtName(); // 报关名称
			String hsSpec = monthStorage.getPtSpec() == null ? ""
					: monthStorage.getPtSpec(); // 报关规格
			String hsUnitName = monthStorage.getUnitName() == null ? ""
					: monthStorage.getUnitName(); // 报关名称

			String key = hsName + "/" + hsSpec + "/" + hsUnitName;

			System.out.println("hsUnitName=" + hsUnitName);
			System.out.println("monthStorage.getPtName()="
					+ monthStorage.getPtName());
			System.out.println("monthStorage.getPtSpec()="
					+ monthStorage.getPtSpec());
			// String key = hsName + hsSpec + hsUnitName;
			//
			// 不存在本月的物料新增到结果集中
			//
			if (!resultMap.containsKey(key)) {
				TempMonthStorage tempMonthStorage = new TempMonthStorage();
				tempMonthStorage.setPtPart(hsUnitName);
				tempMonthStorage.setPtName(monthStorage.getPtName());
				tempMonthStorage.setPtSpec(monthStorage.getPtSpec());

				tempMonthStorage.setFrontMonthPtAmount(monthStorage
						.getPtAmount());
				tempMonthStorage.setFrontMonthNetWeight(monthStorage
						.getNetWeight());
				resultMap.put(key, tempMonthStorage);
			} else {
				TempMonthStorage tempMonthStorage = resultMap.get(key);
				tempMonthStorage.setFrontMonthPtAmount(monthStorage
						.getPtAmount());
				tempMonthStorage.setFrontMonthNetWeight(monthStorage
						.getNetWeight());
			}
		}
		//
		// 是本月的最后一天
		//
		if (isRefresh == true) {
			batchSaveMonthStorage2(materielType, returnList, resultMap, month);
		} else {
			returnList.addAll(resultMap.values());
		}
		return returnList;
	}

	/**
	 * (notExistList ==null) 要全部重新生成以前月的记录 (notExistList !=null) 生成不存在的月报表
	 * 
	 * @param materielType
	 * @param notExistList
	 * @param beginDate
	 * @param endDate
	 * @param isRefresh
	 * @return
	 */
	public List getAllMonth2(String materielType, List<Integer> notExistList,
			Date beginDate, Date endDate, boolean isRefresh) {
		int currentMonth = endDate.getMonth();
		int cenrentYear = endDate.getYear() + 1900;

		for (int i = 0; i < currentMonth; i++) {
			//
			// 这里无须关心效率,因为个数很小
			//
			if (notExistList != null && !notExistList.contains(i + 1)) {
				continue;
			}
			Calendar beginCalendar = Calendar.getInstance();
			beginCalendar.set(Calendar.YEAR, cenrentYear);
			beginCalendar.set(Calendar.MONTH, i);
			beginCalendar.set(Calendar.DAY_OF_MONTH, 1);

			Calendar endCalendar = Calendar.getInstance();
			endCalendar.set(Calendar.YEAR, cenrentYear);
			endCalendar.set(Calendar.MONTH, i);
			//
			// 这一句是为了获得当前月的最大值
			//
			endCalendar.set(Calendar.DAY_OF_MONTH, 1);
			endCalendar.set(Calendar.DAY_OF_MONTH,
					endCalendar.getActualMaximum(Calendar.DATE));
			//
			// 全部进行保存
			//
			getMonth2(materielType, beginCalendar.getTime(),
					endCalendar.getTime(), true);

			// System.out.println((beginCalendar.get(Calendar.MONTH)+1) + " " +
			// (endCalendar.get(Calendar.MONTH)+1));
		}
		//
		// 返回当前月的记录
		//
		// System.out.println((beginDate.getMonth()+1) + " ---- " +
		// (endDate.getMonth()+1));
		return getMonth2(materielType, beginDate, endDate, isRefresh);
	}

	/**
	 * 批量保存当前月的库存记录
	 * 
	 * @param materielType
	 *            物料类型
	 * @param returnList
	 *            本月库存数量
	 * @param resultMap
	 *            本月库存数量(map)
	 * @param month
	 *            月数
	 */
	private void batchSaveMonthStorage2(String materielType,
			List<TempMonthStorage> returnList,
			Map<String, TempMonthStorage> resultMap, int month) {
		//
		// 如果这个查询的结束日期是本月的最后一个日期,就要先删本月的记录,保存本月结存记录
		//
		this.casDao.batchDeleteMonthStorage2(materielType, month);

		List<MonthStorage2> batchList = new ArrayList<MonthStorage2>();
		int batchSize = 100;
		int i = 0;

		Iterator<TempMonthStorage> iterator = resultMap.values().iterator();
		while (iterator.hasNext()) {
			TempMonthStorage temp = iterator.next();
			MonthStorage2 monthStorage = new MonthStorage2();
			monthStorage.setCompany(CommonUtils.getCompany());
			monthStorage.setMaterielType(materielType);
			monthStorage.setMonth(month);
			monthStorage.setUnitName(temp.getPtPart());
			monthStorage.setPtName(temp.getPtName());
			monthStorage.setPtSpec(temp.getPtSpec());
			monthStorage.setPtAmount(temp.getCurrentMonthPtAmount());
			monthStorage.setNetWeight(temp.getCurrentMonthNetWeight());
			if (i % batchSize == 0 && i != 0) {
				this.casDao.batchSaveMonthStorage2(batchList);
				batchList.clear();
			}
			batchList.add(monthStorage);
			returnList.add(temp);
			i++;
		}
		if (batchList.size() > 0) {
			this.casDao.batchSaveMonthStorage2(batchList);
			batchList.clear();
		}
	}

	/**
	 * 查询所有当点月存在的
	 * 
	 * @param materielType
	 *            物料类型
	 * @param currentMonth
	 *            本月
	 * @return 当前月存在的
	 */
	public List<Integer> findFrontMonthIsNotExist2(String materielType,
			int currentMonth) {
		List<Integer> existList = this.casDao.findFrontMonthIsExist2(
				materielType, currentMonth);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < existList.size(); i++) {
			map.put(existList.get(i), existList.get(i));
		}
		List<Integer> returnList = new ArrayList<Integer>();
		for (int i = 1; i < currentMonth; i++) {// 12 个月
			if (map.get(i) == null) {
				returnList.add(i);
			}
		}
		return returnList;
	}

	/**
	 * 转换日期行到字符型 date->字符型"yyyy-MM-dd"
	 * 
	 * @param date
	 *            日期型
	 * @return 字符型的日期型
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(date);
	}

	/** 查询明细 */
	public List findBillDetailByMaterielType(String proNo, String materielType,
			Date begin, Date end, ScmCoc scmcoc) {
		String billDetail = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		List<BillDetail> productIn = this.casDao
				.findBillDetailByBillTypeAndMaterielType(proNo, billDetail,
						begin, end, scmcoc);
		Map<String, TempBillDetail> map = new HashMap();
		for (BillDetail data : productIn) {
			String key = (data.getProductNo() == null ? "" : data
					.getProductNo()) + (data.getPtPart());
			if (key.trim().equals("") || data.getBillMaster() == null
					|| data.getBillMaster().getBillType() == null) {
				continue;
			}
			Integer bllType = data.getBillMaster().getBillType().getBillType();
			if (bllType == null) {
				continue;
			}
			TempBillDetail temp = null;
			if (map.get(key) == null) {
				temp = new TempBillDetail();
				temp.setBillDetail(data);
				if (bllType == 1 || bllType == 3) {
					temp.setInCount(data.getPtAmount() == null ? 0.0 : data
							.getPtAmount());
					map.put(key, temp);
				} else if (bllType == 2 || bllType == 4) {
					temp.setOutCount(data.getPtAmount() == null ? 0.0 : data
							.getPtAmount());
					map.put(key, temp);
				}

			} else {
				temp = map.get(key);
				if (bllType == 1 || bllType == 3) {
					temp.setInCount((temp.getInCount() == null ? 0.0 : temp
							.getInCount())
							+ (data.getPtAmount() == null ? 0.0 : data
									.getPtAmount()));
					// .getPtAmount()));
				} else if (bllType == 2 || bllType == 4) {
					temp.setOutCount((temp.getOutCount() == null ? 0.0 : temp
							.getOutCount())
							+ (data.getPtAmount() == null ? 0.0 : data
									.getPtAmount()));
				}
			}

		}
		return new ArrayList(map.values());
	}

	/**
	 * 国内购买原材料统计明细表
	 */
	public List findChinBuyMaterielReport(String emsNo, ScmCoc scmCoc,
			String hsName) {
		List allList = new ArrayList();
		List inlist = casDao.findInvoiceAndBillCorresponding();

		List mylist = casDao.findCasInvoiceInfoId(true);
		List cancelList = new ArrayList();
		for (int i = 0; i < inlist.size(); i++) {
			InvoiceAndBillCorresponding iv = (InvoiceAndBillCorresponding) inlist
					.get(i);
			if (mylist.contains(iv.getInvoiceInfokey())) {
				cancelList.add(iv.getBillDetailkey());
			}

		}
		List billList = this.casDao.findBillDetailChinBuy(scmCoc, hsName);
		for (int i = 0; i < billList.size(); i++) {
			TempDetailInvoice tempObj = new TempDetailInvoice();
			BillDetailMateriel de = (BillDetailMateriel) billList.get(i);
			tempObj.setBillDetail(de);
			if (cancelList.contains(de.getId())) {
				tempObj.setIscancel(true);
			} else {
				tempObj.setIscancel(false);
			}
			allList.add(tempObj);
		}

		return allList;
	}

	/** 发票取消 */
	public List invoiceCancel(List ls, BaseEmsHead head) {

		String title = "以下发票在帐册"
				+ (head.getEmsNo() == null ? "" : head.getEmsNo()) + "中未能找到\n";
		String errorstr = title;
		List allList = new ArrayList();
		for (int i = 0; i < ls.size(); i++) {
			CasInvoiceInfo info = (CasInvoiceInfo) ls.get(i);
			CasInvoice inco = info.getCasInvoice();
			List list = this.casDao.findBaseEmsImgseqNum(info.getCuName(), info
					.getComplex().getCode(), head);
			if (list.size() > 0) {
				Integer seqNum = Integer.parseInt(list.get(0).toString());
				info.setCancelEmsSeqNum(seqNum);
				info.setCanceled(true);
				inco.setEmsNo(head.getEmsNo());
				this.casDao.saveOrUpdate(inco);
				this.casDao.saveOrUpdate(info);
				allList.add(info);
			} else {
				errorstr += "发票号: "
						+ (inco.getInvoiceNo() == null ? " " : inco
								.getInvoiceNo())
						+ "  发票名称: "
						+ (info.getCuName() == null ? "" : info.getCuName())
						+ "  发票对应编码 : "
						+ (info.getComplex() == null ? "" : info.getComplex()
								.getCode()) + "\n";
			}
		}
		if (!errorstr.equals(title)) {
			throw new RuntimeException(errorstr);
		}
		return allList;
	}

	/** 发票取消 */
	public List invoiceCancel(List ls, Integer seqNum, BaseEmsHead head) {
		List allList = new ArrayList();
		for (int i = 0; i < ls.size(); i++) {
			CasInvoiceInfo info = (CasInvoiceInfo) ls.get(i);
			CasInvoice inco = info.getCasInvoice();
			info.setCancelEmsSeqNum(seqNum);
			info.setCanceled(true);
			inco.setEmsNo(head.getEmsNo());
			this.casDao.saveOrUpdate(inco);
			this.casDao.saveOrUpdate(info);
			allList.add(inco);
		}
		return allList;
	}

	/** 发票重置 */
	public List invoiceRCancel(List ls, BaseEmsHead head) {
		List allList = new ArrayList();
		for (int i = 0; i < ls.size(); i++) {
			CasInvoiceInfo info = (CasInvoiceInfo) ls.get(i);
			if (info.getCanceled() == null || !info.getCanceled()) {
				continue;
			}
			CasInvoice hd = info.getCasInvoice();
			info.setCanceled(false);
			hd.setEmsNo(null);
			info.setCancelEmsSeqNum(null);
			info.setCanceledNum(0.0);
			this.casDao.saveOrUpdate(info);
			this.casDao.saveOrUpdate(hd);
			allList.add(info);
		}
		return allList;
	}

	/**
	 * 取得导入文件中名称/规格/单位，及统计其是否有重复
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, Integer> getMaterielCount(List list) {
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			BalanceInfo fileData = (BalanceInfo) list.get(i);
			String NSU = (fileData.getName() == null ? "" : fileData.getName())
					+ (fileData.getSpec() == null ? "" : fileData.getSpec())
					+ (fileData.getUnitName() == null ? "" : fileData
							.getUnitName());
			if (hm.get(NSU) == null) {
				hm.put(NSU, 1);
			} else {
				hm.put(NSU, Integer.valueOf(hm.get(NSU).toString()) + 1);
			}
		}
		return hm;
	}

	/**
	 * 取得导入文件中盘点日期/料号，及统计其是否有重复(盘点平衡表)
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, Integer> getMaterielCountInCheckBalance(List list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			CheckBalance fileData = (CheckBalance) list.get(i);
			String matchStr = (fileData.getCheckDate() == null ? "" : sdf
					.format(fileData.getCheckDate()))// 时间
					+ (fileData.getPtNo() == null ? "" : fileData.getPtNo()
							.trim())// 料号
					+ (fileData.getBomVersion() == null ? "" : fileData
							.getBomVersion().trim())// bom版本
					+ ((fileData.getWareSet() == null || fileData.getWareSet()
							.getName() == null) ? "" : fileData.getWareSet()
							.getName().trim())// 仓库
					+ (fileData.getLjCpMark() == null ? "" : fileData
							.getLjCpMark().trim())// 料件成品标记
					+ (fileData.getKcType() == null ? "" : fileData.getKcType()
							.trim());// 库存类别

			if (hm.get(matchStr) == null) {
				hm.put(matchStr, 1);
			} else {
				hm.put(matchStr,
						Integer.valueOf(hm.get(matchStr).toString()) + 1);
			}
		}
		return hm;
	}

	/**
	 * 导入文件是检查
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @return
	 */
	public List checkFileData(List list, Hashtable ht, String taskId) {
		ArrayList<BalanceInfo> ls = new ArrayList<BalanceInfo>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		// calUnitMap = null;
		// unitMap = null;
		// complexMap = null;
		// cMap = null;
		// getCustomsComplex();
		BalanceInfo fileData = null;
		int[] invalidationColNo;
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在检验文件资料");
		}
		Map<String, Integer> hmMaterielCount = getMaterielCount(list);
		List exsitNSUList = casDao.findBalanceInfoNameSpecUnitName();
		for (int i = 0; i < list.size(); i++) {
			temp.clear();
			fileData = (BalanceInfo) list.get(i);
			String NSU = (fileData.getName() == null ? "" : fileData.getName())
					+ (fileData.getSpec() == null ? "" : fileData.getSpec())
					+ (fileData.getUnitName() == null ? "" : fileData
							.getUnitName());
			if (Integer.parseInt(ht.get(Integer.valueOf(1)).toString()) > -1) {
				if (exsitNSUList.contains(NSU.trim())) {
					temp.add(Integer.valueOf(1));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "此名称/规格/单位在短溢表中已存在;");
				} else if (hmMaterielCount.get(NSU.trim()) != null
						&& hmMaterielCount.get(NSU.trim()) > 1) {
					temp.add(Integer.valueOf(1));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "此名称/规格/单位重复;");
				}
			}
			if (temp.size() > 0) {
				invalidationColNo = new int[temp.size()];
				for (int j = 0; j < temp.size(); j++) {
					invalidationColNo[j] = Integer.parseInt(temp.get(j)
							.toString());
				}
				fileData.setInvalidationColNo(invalidationColNo);
				ls.add(fileData);
			}
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
		return ls;
	}

	/**
	 * 导入文件是检查(盘点平衡表)
	 * 
	 * @param list
	 *            //导入的数据
	 * @param ht
	 *            columnNo为栏位对应HashTable
	 * @param taskId
	 * @return
	 */
	public List checkFileDataForCheckBalance(List list, Hashtable ht,
			String taskId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<CheckBalance> ls = new ArrayList<CheckBalance>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		CheckBalance fileData = null;
		int[] invalidationColNo;
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在检验文件资料");
		}
		// 取得导入文件中盘点日期/料号，及统计其是否有重复(盘点平衡表)
		Map<String, Integer> hmMaterielCount = getMaterielCountInCheckBalance(list);
		// 查找盘点平衡表中存在的盘点时间/工厂料号,用于数据导入时检查,避免重复导入
		List exsitList = casDao.findCheckBalanceTimeAndPtNo();
		for (int i = 0; i < list.size(); i++) {
			temp.clear();
			fileData = (CheckBalance) list.get(i);// 待导入的数据
			// 干掉料件的BOM号
			if ("0".equals(fileData.getLjCpMark())) {
				fileData.setBomVersion("");
			}
			String matchStr = (fileData.getCheckDate() == null ? "" : sdf
					.format(fileData.getCheckDate()))// 时间
					+ (fileData.getPtNo() == null ? "" : fileData.getPtNo()
							.trim())// 料号
					+ ((fileData.getWareSet() == null || fileData.getWareSet()
							.getName() == null) ? "" : fileData.getWareSet()
							.getName().trim())// 仓库
					+ (fileData.getLjCpMark() == null ? "" : fileData
							.getLjCpMark().trim())// 料件成品标记
					+ (fileData.getKcType() == null ? "" : fileData.getKcType()
							.trim());// 库存类别
			if (Integer.parseInt(ht.get(Integer.valueOf(1)).toString()) > -1
					&& Integer.parseInt(ht.get(Integer.valueOf(2)).toString()) > -1) {
				if (exsitList != null && exsitList.contains(matchStr.trim())) {// 判断待插入在数据中是否有重复
					temp.add(Integer.valueOf(1));
					fileData.setErrorReason((fileData.getErrorReason() == null ? ""
							: fileData.getErrorReason())
							+ "此盘点平衡表中已存在;");
				} else if (hmMaterielCount.get(matchStr.trim()) != null
						&& hmMaterielCount.get(matchStr.trim()) > 1) {// 导入数据中盘点时间
					// +料号重复
					temp.add(Integer.valueOf(1));
					fileData.setErrorReason((fileData.getErrorReason() == null ? ""
							: fileData.getErrorReason())
							+ "此盘点平衡表重复;");
				}
			}
			if (Integer.parseInt(ht.get(Integer.valueOf(0)).toString()) > -1) {// 盘点时间对应DgCheckBalanceCorrespondFile:271
				if (fileData.getCheckDate() == null) {// 盘点时间不能为空
					temp.add(Integer.valueOf(0));
					fileData.setErrorReason((fileData.getErrorReason() == null ? ""
							: fileData.getErrorReason())
							+ "此盘点时间格式不正确;");
				}
			}
			if (Integer.parseInt(ht.get(Integer.valueOf(2)).toString()) > -1) {
				if (fileData.getWareSet() == null) {// 仓库不能为空
					temp.add(Integer.valueOf(2));
					fileData.setErrorReason((fileData.getErrorReason() == null ? ""
							: fileData.getErrorReason())
							+ "此仓库名称在系统中不存在，请先在系统中添加;");
				}
			}
			if (Integer.parseInt(ht.get(Integer.valueOf(6)).toString()) > -1) {
				if (fileData.getBomVersion() != null
						&& !"".equals(fileData.getBomVersion())) {// Bom版本是否正确
					if (!fileData.getBomVersion().matches("\\d*")) {
						temp.add(Integer.valueOf(6));
						fileData.setErrorReason((fileData.getErrorReason() == null ? ""
								: fileData.getErrorReason())
								+ "此成品的bom版本号格式不正确，bom版本号为大于零的整数;");
					}
				}
			}

			if (temp.size() > 0) {
				invalidationColNo = new int[temp.size()];
				for (int j = 0; j < temp.size(); j++) {
					invalidationColNo[j] = Integer.parseInt(temp.get(j)
							.toString());
				}
				fileData.setInvalidationColNo(invalidationColNo);
				ls.add(fileData);
			}
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
		return ls;
	}

	/**
	 * 自动获取CheckBalance对应的报关资料并计算折算报关资料
	 * 
	 * @return
	 * @author wss
	 */
	public List calculateCheckBalance(List list, String taskId) {

		if (list == null || list.size() <= 0) {
			return new ArrayList();
		}

		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);

		String clientTipMessage = "折算报关数量，初始化料号与报关常用工厂物料对应关系...";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		/**
		 * 料号对应Materiel 缓存
		 */
		Map<String, Materiel> mapPtNoM = this.findPtNoAndM();

		clientTipMessage = "折算报关数量，初始化料号与对应关系...";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		/**
		 * 料号对应FactoryAndFactualCustomsRalation 缓存
		 */
		Map<String, FactoryAndFactualCustomsRalation> mapPtNoFFC = this
				.findPtNoAndFFC(null);

		System.out.println("wss list.size = " + list.size());

		for (int i = 0; i < list.size(); i++) {
			CheckBalance item = (CheckBalance) list.get(i);

			clientTipMessage = "折算报关数量，正在进行第 " + (i + 1) + "条料号为"
					+ item.getPtNo() + "的折算";
			logger.info(clientTipMessage);
			progressInfo.setHintMessage(clientTipMessage);

			// 如果是半成品，则信息应该从报关常用工厂物料抓取
			if ("2".equals(item.getLjCpMark().trim())) {

				Materiel m = mapPtNoM.get(item.getPtNo());

				item = fillCheckBalanceUseMateriel(item, m);// 填充相应资料

				// this.casDao.saveCheckBalance(item);
			} else {
				FactoryAndFactualCustomsRalation srh = mapPtNoFFC.get(item
						.getPtNo());

				fillCheckBalanceByFactoryAndFactualCustomsRalation(item, srh);
				;// 填充相应的资料

				// this.casDao.saveOrUpdate(item);
			}

		}
		// mapPtNoM.clear();
		// mapPtNoFFC.clear();
		clientTipMessage = "正在进行事务提交，可能花费的时间较长...";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		System.out.println("wss 运行完毕！事务提交开始时间：" + System.currentTimeMillis());
		return list;
	}

	/**
	 * 根据Materiel填充资料CheckBalance
	 * 
	 * @return CheckBalance
	 * @author wss
	 */
	private CheckBalance fillCheckBalanceUseMateriel(CheckBalance item,
			Materiel m) {

		if (m != null) {
			item.setPtName(m.getFactoryName());
			item.setPtSpec(m.getFactorySpec());
			item.setPtUnit(m.getCalUnit());// wss新加
			item.setPtUnitName(m.getCalUnit() == null ? "" : m.getCalUnit()
					.getName());
			item.setComplex(m.getComplex());// 新加

			item.setName(m.getPtName());
			item.setSpec(m.getPtSpec());
			item.setHsUnit(m.getPtUnit());
			item.setUnitName(m.getPtUnit() == null ? "" : m.getPtUnit()
					.getName());

			item.setUnitConvert(m.getUnitConvert() == null ? 0.0 : m
					.getUnitConvert());
			item.setHsAmount(IsNullDouble(item.getCheckAmount())
					* IsNullDouble(item.getUnitConvert()));
			item.setNote("");

		} else {
			item.setNote("报关常用物料中不存在此料号");
		}
		this.casDao.saveOrUpdate(item);

		return item;
	}

	/**
	 * 根据FactoryAndFactualCustomsRalation填充资料CheckBalance
	 * 
	 * @return CheckBalance
	 * @author wss
	 */
	private void fillCheckBalanceByFactoryAndFactualCustomsRalation(
			CheckBalance item, FactoryAndFactualCustomsRalation srh) {
		if (srh != null && srh.getStatCusNameRelationHsn() != null) {
			if (srh.getMateriel() != null) {
				item.setPtName(srh.getMateriel().getFactoryName());
				item.setPtSpec(srh.getMateriel().getFactorySpec());
				item.setPtUnit(srh.getMateriel().getCalUnit());// wss新加
				item.setPtUnitName(srh.getMateriel().getCalUnit() == null ? ""
						: srh.getMateriel().getCalUnit().getName());
			}
			// System.out.println("wss complex.code = " +
			// srh.getStatCusNameRelationHsn().getComplex().getCode());
			item.setComplex(srh.getStatCusNameRelationHsn().getComplex());
			// System.out.println("wss complex.code = " +
			// item.getComplex().getCode());

			item.setName(srh.getStatCusNameRelationHsn().getCusName());
			item.setSpec(srh.getStatCusNameRelationHsn().getCusSpec());
			item.setHsUnit(srh.getStatCusNameRelationHsn().getCusUnit());// wss新加
			item.setUnitName(srh.getStatCusNameRelationHsn().getCusUnit() == null ? ""
					: srh.getStatCusNameRelationHsn().getCusUnit().getName());
			item.setUnitConvert(srh.getUnitConvert() == null ? 0.0 : srh
					.getUnitConvert());
			item.setHsAmount(IsNullDouble(item.getCheckAmount())
					* IsNullDouble(item.getUnitConvert()));
			item.setNote("");
			// System.out.println("wss 从对应关系中填充完毕");
		} else {
			item.setNote("此料号还没有做对应关系");
		}

		this.casDao.saveOrUpdate(item);
	}

	/**
	 * 根据Materiel填充资料CheckBalance
	 * 
	 * @return CheckBalance
	 * @author wss2010.10.14
	 */
	private void fillAndSaveCheckBalanceByMateriel(CheckBalance item) {
		// 如果事先有获取报关资料，就算了
		// 为什么不可以重新计算
		// if (item.getName() != null) {
		// return;
		// }

		long b = System.currentTimeMillis();
		Materiel m = this.casDao.findMaterielByPtNo(item.getPtNo());

		long e = System.currentTimeMillis();

		System.out.println("-----抓取相应Materiel时间：" + (e - b) + " ms");

		b = System.currentTimeMillis();
		if (m != null) {
			item.setPtName(m.getFactoryName());
			item.setPtSpec(m.getFactorySpec());
			item.setPtUnit(m.getCalUnit());// wss新加
			item.setPtUnitName(m.getCalUnit() == null ? "" : m.getCalUnit()
					.getName());
			item.setComplex(m.getComplex());// 新加

			item.setName(m.getPtName());
			item.setSpec(m.getPtSpec());
			item.setHsUnit(m.getPtUnit());
			item.setUnitName(m.getPtUnit() == null ? "" : m.getPtUnit()
					.getName());

			item.setUnitConvert(m.getUnitConvert() == null ? 0.0 : m
					.getUnitConvert());
			item.setHsAmount(IsNullDouble(item.getCheckAmount())
					* IsNullDouble(item.getUnitConvert()));
			item.setNote("");

		} else {
			item.setNote("报关常用物料中不存在此料号");
		}
		this.casDao.saveOrUpdate(item);

		e = System.currentTimeMillis();
		System.out.println("-----保存CheckBalance时间：" + (e - b) + " ms");

	}

	/**
	 * 盘点如果是已经为料件了，则CheckBalanceConvertMateriel为复制
	 * 
	 * @param item
	 * @author wss2010.10.14
	 */
	public void createAndSaveCheckBalanceConvertMaterielByCheckBalance(
			CheckBalance item) {
		CheckBalanceConvertMateriel cb = new CheckBalanceConvertMateriel();

		List alist = new ArrayList();
		alist.add("fatherCheckBalance");
		try {
			BswBeansUtils.copyProperties(cb, item, alist);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cb.setLjCpMark("0");// 物料类型
		cb.setId(null);
		cb.setFatherCheckBalance(item);// 父盘点
		this.casDao.saveOrUpdateNoCache(cb);
	}

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 料件折算 one by one
	 * 
	 * @return
	 * @author wss
	 */
	public void fillCheckBalanceHsInfoOneByOne(CheckBalance item) {

		// 如果是半成品，通过Materiel填充资料
		if ("2".equals(item.getLjCpMark())) {// 代表半成品

			fillAndSaveCheckBalanceByMateriel(item);// 从填充资料
		}
		// 否则通过对应关系填充资料
		else {

			// 对应关系
			fillAndSaveCheckBalanceOneByOne(item);// 填充资料
		}

		// 如果是料件还需要复制到CheckBalanceConvertMateriel
		if ("0".equals(item.getLjCpMark())) {// 0代表料件
			createAndSaveCheckBalanceConvertMaterielByCheckBalance(item);
		}
	}

	/**
	 * 料号 对应 物料信息
	 * 
	 * @return
	 * @author wss2010.09.16
	 */
	public Map<String, Materiel> findPtNoAndM() {
		Map map = new HashMap();
		List list = this.casDao.findMaterielAll();
		System.out.println("wss relist.size = " + list.size());

		for (int i = 0; i < list.size(); i++) {
			Materiel m = (Materiel) list.get(i);
			map.put(m.getPtNo(), m);
		}
		return map;

	}

	/**
	 * 料号 对应 物料信息
	 * 
	 * @return
	 * @author wss2010.09.16
	 */
	public Map<String, FactoryAndFactualCustomsRalation> findPtNoAndFFC(
			String materielType) {
		Map map = new HashMap();
		List list = this.casDao
				.findFactoryAndFactualCustomsRalationByMaterielType(materielType);
		System.out.println("wss relist.size = " + list.size());

		for (int i = 0; i < list.size(); i++) {
			FactoryAndFactualCustomsRalation f = (FactoryAndFactualCustomsRalation) list
					.get(i);
			map.put(f.getMateriel().getPtNo(), f);
		}
		return map;
	}

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 料件折算 CheckBalanceBom(盘点折算BOM）
	 * 
	 * @return
	 * @author wss2010.07.21
	 */
	public List calculateCheckBalanceConvertMateriel(List list) {

		/**
		 * 料号对应 FactoryAndFactualCustomsRalation 缓存 全部都是料件的
		 */
		Map<String, FactoryAndFactualCustomsRalation> mpaPtNoFFC = this
				.findPtNoAndFFC(MaterielType.MATERIEL);

		for (int i = 0; i < list.size(); i++) {
			CheckBalanceConvertMateriel item = (CheckBalanceConvertMateriel) list
					.get(i);
			// 工厂和实际客户对应表

			FactoryAndFactualCustomsRalation srh = mpaPtNoFFC.get(item
					.getPtNo());

			if (srh != null && srh.getStatCusNameRelationHsn() != null) {
				if (srh.getMateriel() != null) {
					item.setPtName(srh.getMateriel().getFactoryName());// 新加
					item.setPtSpec(srh.getMateriel().getFactorySpec());// 新加
					item.setPtUnit(srh.getMateriel().getCalUnit());
				}
				item.setComplex(srh.getStatCusNameRelationHsn().getComplex());// 新加
				item.setName(srh.getStatCusNameRelationHsn().getCusName());
				item.setSpec(srh.getStatCusNameRelationHsn().getCusSpec());
				item.setHsUnit(srh.getStatCusNameRelationHsn().getCusUnit());
				item.setUnitConvert(srh.getUnitConvert() == null ? 0.0 : srh
						.getUnitConvert());
				item.setHsAmount(IsNullDouble(item.getCheckAmount())
						* IsNullDouble(item.getUnitConvert()));
				item.setNote("");
			} else {
				item.setNote("此料号还没有做对应关系");
			}
			this.casDao.saveOrUpdate(item);
		}
		// return;
		return list;
	}

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 料件折算 One By One CheckBalanceBom(盘点折算BOM）
	 * 
	 * @return
	 * @author wss
	 */
	public CheckBalanceConvertMateriel calculateCheckBalanceConvertMateriel(
			Map<String, FactoryAndFactualCustomsRalation> mapPtNoFFC,
			CheckBalanceConvertMateriel ccm) {

		FactoryAndFactualCustomsRalation srh = mapPtNoFFC.get(ccm.getPtNo());

		if (srh != null && srh.getStatCusNameRelationHsn() != null) {
			if (srh.getMateriel() != null) {
				ccm.setPtName(srh.getMateriel().getFactoryName());
				ccm.setPtSpec(srh.getMateriel().getFactorySpec());
				ccm.setPtUnit(srh.getMateriel().getCalUnit());
			}
			ccm.setComplex(srh.getStatCusNameRelationHsn().getComplex());
			ccm.setName(srh.getStatCusNameRelationHsn().getCusName());
			ccm.setSpec(srh.getStatCusNameRelationHsn().getCusSpec());
			ccm.setHsUnit(srh.getStatCusNameRelationHsn().getCusUnit());
			ccm.setUnitConvert(srh.getUnitConvert() == null ? 0.0 : srh
					.getUnitConvert());
			ccm.setHsAmount(IsNullDouble(ccm.getCheckAmount())
					* IsNullDouble(ccm.getUnitConvert()));
			ccm.setNote("");
			System.out.println("wss 从对应关系中填充完毕");

		} else {
			ccm.setNote("此料号还没有做对应关系");
		}

		return ccm;
	}

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 料件折算 One By One CheckBalanceBom(盘点折算BOM）
	 * 
	 * @return
	 * @author wss
	 */
	public CheckBalanceConvertMateriel fillAndSaveCheckBalanceConvertMaterielOneByOne(
			CheckBalanceConvertMateriel ccm) {

		FactoryAndFactualCustomsRalation srh = this.casDao
				.findFactoryAndFactualCustomsRalationByPtNo(ccm.getPtNo());

		if (srh != null && srh.getStatCusNameRelationHsn() != null) {
			if (srh.getMateriel() != null) {
				ccm.setPtName(srh.getMateriel().getFactoryName());
				ccm.setPtSpec(srh.getMateriel().getFactorySpec());
				ccm.setPtUnit(srh.getMateriel().getCalUnit());
			}
			ccm.setComplex(srh.getStatCusNameRelationHsn().getComplex());
			ccm.setName(srh.getStatCusNameRelationHsn().getCusName());
			ccm.setSpec(srh.getStatCusNameRelationHsn().getCusSpec());
			ccm.setHsUnit(srh.getStatCusNameRelationHsn().getCusUnit());
			ccm.setUnitConvert(srh.getUnitConvert() == null ? 0.0 : srh
					.getUnitConvert());
			ccm.setHsAmount(IsNullDouble(ccm.getCheckAmount())
					* IsNullDouble(ccm.getUnitConvert()));
			ccm.setNote("");
		} else {
			ccm.setNote("此料号还没有做对应关系");
		}

		this.casDao.saveOrUpdate(ccm);
		System.out.println("填充对应的报关资料");
		return ccm;
	}

	/**
	 * 自动获取对应的报关资料并计算，然后再保存在盘点平衡表中 料件折算 One By One CheckBalanceBom(盘点折算BOM）
	 * 
	 * @return
	 * @author wss
	 */
	public void fillAndSaveCheckBalanceOneByOne(CheckBalance c) {

		// 如果事先有获取报关资料，就免了
		// 为什么不可以重新计算
		// if (c.getComplex() != null) {
		// return;
		// }

		Long b = System.currentTimeMillis();
		FactoryAndFactualCustomsRalation srh = this.casDao
				.findFactoryAndFactualCustomsRalationByPtNo(c.getPtNo());
		Long e = System.currentTimeMillis();
		System.out.println("wss 查找对应关系的时间为：" + (e - b) + "ms");

		b = System.currentTimeMillis();
		if (srh != null && srh.getStatCusNameRelationHsn() != null) {
			if (srh.getMateriel() != null) {
				c.setPtName(srh.getMateriel().getFactoryName());
				c.setPtSpec(srh.getMateriel().getFactorySpec());
				c.setPtUnit(srh.getMateriel().getCalUnit());
			}
			c.setComplex(srh.getStatCusNameRelationHsn().getComplex());
			c.setName(srh.getStatCusNameRelationHsn().getCusName());
			c.setSpec(srh.getStatCusNameRelationHsn().getCusSpec());
			c.setHsUnit(srh.getStatCusNameRelationHsn().getCusUnit());
			c.setUnitConvert(srh.getUnitConvert() == null ? 0.0 : srh
					.getUnitConvert());
			c.setHsAmount(IsNullDouble(c.getCheckAmount())
					* IsNullDouble(c.getUnitConvert()));
			c.setNote("");
		} else {
			c.setNote("此料号还没有做对应关系");
		}

		this.casDao.saveOrUpdate(c);
		e = System.currentTimeMillis();
		System.out.println("wss 填充并保存Checkbalance的时间为：" + (e - b) + "ms");
	}

	/**
	 * 删除盘点平衡表一条记录
	 * 
	 * @param List
	 *            <CheckBalance>
	 * @author wss
	 */
	public void deleteCheckBalance(List<CheckBalance> ls) {
		for (int i = 0; i < ls.size(); i++) {
			CheckBalance cb = (CheckBalance) ls.get(i);
			this.deleteCheckBalance(cb);
		}
	}

	/**
	 * 删除盘点平衡表一条记录
	 * 
	 * @param checkBalance
	 */
	public void deleteCheckBalance(CheckBalance c) {
		// wss:2010.07.20要先删除它的折BOM资料
		// if (!"0".equals(c.getLjCpMark())) {
		casDao.deleteCheckBalanceConvertMateriel(c);
		// }
		casDao.delete(c);
	}

	// /**
	// * 删除盘点平衡表一条折BOM记录
	// *
	// * @param checkBalance
	// *
	// * @author wss
	// */
	// public void deleteCheckBalanceBom(CheckBalanceConvertMateriel
	// checkBalanceBom) {
	// casDao.delete(checkBalanceBom);
	// }

	/** 检查平行表 */
	public void cpZsuanLj() {
		// key--时间+料号+物料标记+库存类别,检查平衡表
		Map<String, CheckBalance> billDetailCpTranMap = new HashMap<String, CheckBalance>();// 料号，耗用量（成品数量
		// *
		// 料件单位用量）
		// 获取物料类型为 成品 、半成品 、残次品 的且对应报关常用工厂对应BOM版本,检查平衡表
		List list = this.casDao.findCheckBalanceCp(null, null);
		List<Object[]> tempList = new ArrayList<Object[]>();
		Map<String, String> versionIdMap = new HashMap<String, String>();
		int page = 20;
		for (int i = 0, size = list.size(); i < size; i++) {
			Object[] objs = (Object[]) list.get(i);
			String versionId = (String) objs[3]; // 报关常用工厂BOM版本id

			if (versionId == null) {
				continue;
			}
			//
			// 为了去掉重复的 versionId
			//
			versionIdMap.put(versionId, versionId);
			tempList.add(objs);

			if ((i != 0 && (versionIdMap.size() % page) == 0) || i == size - 1) {

				List<String> versionIdList = new ArrayList<String>();
				versionIdList.addAll(versionIdMap.values());
				List materialBomDetailList = this.materialManageDao
						.findMaterielBomDetail(versionIdList);
				versionIdList.clear();
				versionIdMap.clear();

				if (materialBomDetailList.size() <= 0) {
					tempList.clear();
					continue;
				}

				//
				// 获取成品版本号对应的料件明细
				//
				Map<String, List<Object[]>> bomObjectMap = new HashMap<String, List<Object[]>>();
				for (int j = 0, n = materialBomDetailList.size(); j < n; j++) {
					Object[] bomObjs = (Object[]) materialBomDetailList.get(j);
					String tempVersionId = (String) bomObjs[1];
					if (bomObjectMap.get(tempVersionId) == null) {
						List<Object[]> temp = new ArrayList<Object[]>();
						temp.add(bomObjs);
						bomObjectMap.put(tempVersionId, temp);
					} else {
						List<Object[]> temp = bomObjectMap.get(tempVersionId);
						temp.add(bomObjs);
					}
				}
				for (int j = 0, n = tempList.size(); j < n; j++) {
					objs = (Object[]) tempList.get(j);
					Double ptAmount = objs[0] == null ? 0.0 : (Double) objs[0]; // 工厂数量
					Date date = (Date) objs[1]; // 盘点日期
					String wareSet = (String) objs[2];// 仓库
					String tempVersionId = (String) objs[3]; // version guid
					String kcType = (String) objs[4];// 库存类别

					List<Object[]> tempObjes = bomObjectMap.get(tempVersionId);
					if (tempObjes == null) {
						continue;
					}

					for (int k = 0, tempObjesSize = tempObjes.size(); k < tempObjesSize; k++) {
						Object[] bomObjs = tempObjes.get(k);
						//
						// 料件料号为 key
						//
						String ptNo = (String) bomObjs[0];
						//
						// 成品单耗
						//
						Double unitWaste = bomObjs[2] == null ? 0.0
								: (Double) bomObjs[2];
						//
						// 成品单 单项用量
						//
						Double unitUsed = bomObjs[3] == null ? 0.0
								: (Double) bomObjs[3];
						//
						// 损耗
						//
						Double waste = bomObjs[4] == null ? 0.0
								: (Double) bomObjs[4];

						//
						// 成品单耗用量
						//
						Double productMaterialUnitWaste = ptAmount * unitUsed;

						String ptNoKey = String.valueOf(date) + "/" + wareSet
								+ "/" + ptNo + "/" + kcType;
						if (billDetailCpTranMap.get(ptNoKey) == null) {
							CheckBalance obj = new CheckBalance();
							obj.setPtNo(ptNo);
							obj.setCheckDate(date);
							obj.setNote(wareSet);
							obj.setKcType(kcType);
							obj.setCheckAmount(productMaterialUnitWaste);
							billDetailCpTranMap.put(ptNoKey, obj);
						} else {
							CheckBalance obj = billDetailCpTranMap.get(ptNoKey);
							Double tempAmout = obj.getCheckAmount();
							tempAmout += productMaterialUnitWaste;
							obj.setCheckAmount(tempAmout);
							billDetailCpTranMap.put(ptNoKey, obj);
						}
					}
				}
				tempList.clear();
			}
		}

		this.casDao.deleteCheckBalanceCp();// 删除成品、半成品、残次品 盘点平衡信息

		List sls = new Vector(billDetailCpTranMap.values());
		for (int i = 0; i < sls.size(); i++) {
			CheckBalance obj = (CheckBalance) sls.get(i);
			WareSet ws = this.materialManageDao.findWarerByCode(obj.getNote());
			obj.setWareSet(ws);
			obj.setNote("");
			obj.setCompany(CommonUtils.getCompany());
			obj.setLjCpMark("0");
			this.casDao.saveOrUpdate(obj);
		}
	}

	/**
	 * 盘点表折料
	 * 
	 * @author wss
	 */
	public void convertCheckBalance() {

		// 0.先删除原先的
		// this.casDao.deleteCheckBalanceConvertM();

		// 1.获取物料类型为 料件的 CheckBalance
		List<CheckBalance> listM = this.casDao.findCheckBalanceM(null, null);
		System.out.println("wss listM.size = " + listM.size());

		for (int i = 0; i < listM.size(); i++) {
			CheckBalance c = listM.get(i);
			CheckBalanceConvertMateriel cb = new CheckBalanceConvertMateriel();
			cb.setLjCpMark("0");// 物料类型
			cb.setPtNo(c.getPtNo());// 料号
			cb.setCheckDate(c.getCheckDate());// 盘点日期
			cb.setNote(c.getNote());// 备注
			cb.setKcType(c.getKcType());// 库存类型
			cb.setWareSet(c.getWareSet());// 仓库
			cb.setCheckAmount(c.getCheckAmount());// 盘点数量
			cb.setFatherCheckBalance(c);// 父盘点
			cb.setCompany(CommonUtils.getCompany());// 公司
			this.casDao.saveOrUpdate(cb);
		}

		// 2.获取物料类型为成品、半成品的CheckBalance
		List<CheckBalance> listP = this.casDao.findCheckBalanceP(null, null);
		System.out.println("wss listP.size = " + listP.size());

		for (int i = 0; i < listP.size(); i++) {
			CheckBalance c = listP.get(i);

			// 找到BOM
			List<MaterialBomDetail> listBom = materialManageDao
					.findMaterialBomByDetailByptNoAndVersion(
							c.getPtNo(),
							(c.getBomVersion() == null || "".equals(c
									.getBomVersion())) ? null : Integer
									.parseInt(c.getBomVersion()));

			System.out.println("wss listBom.size " + listBom.size());

			// 遍历BOM
			for (int j = 0; j < listBom.size(); j++) {
				MaterialBomDetail bom = listBom.get(j);

				// 料件料号为 key
				String ptNo = bom.getMateriel().getPtNo();
				System.out.println("wss bom.ptNo = " + ptNo);

				// 成品单耗
				Double unitWaste = bom.getUnitWaste();

				// 成品单 单项用量
				Double unitUsed = bom.getUnitWaste() / (1 - bom.getWaste());

				// 损耗
				Double waste = bom.getWaste();

				// 成品单耗用量
				Double productMaterialUnitWaste = c.getCheckAmount() * unitUsed;

				CheckBalanceConvertMateriel cb = new CheckBalanceConvertMateriel();
				cb.setLjCpMark("0");// 物料类型
				cb.setPtNo(ptNo);// 料号
				cb.setCheckDate(c.getCheckDate());// 盘点时间
				cb.setNote(c.getNote());// 备注
				cb.setKcType(c.getKcType());// 库存类别
				cb.setWareSet(c.getWareSet());// 仓库
				cb.setCheckAmount(productMaterialUnitWaste);// 盘点数量
				cb.setFatherCheckBalance(c);// 父盘点
				cb.setCompany(CommonUtils.getCompany());
				cb.setUnitWaste(unitWaste);// 单耗
				cb.setWaste(waste);// 损耗
				cb.setUnitUsed(unitUsed);// 单项用量
				this.casDao.saveOrUpdate(cb);
			}
		}
	}

	/** 查询数据 */
	public List findImgFromBaseEmsHead(BaseEmsHead data, CasInvoice head) {
		List list = casDao.findFromBaseEmsHeadImg(data);
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			TempEmsImg temp = (TempEmsImg) list.get(i);
			String key = (temp.getCusName() == null ? "" : temp.getCusName())
					+ "/"
					+ (temp.getCusSpec() == null ? "" : temp.getCusSpec())
					+ "/"
					+ (temp.getProjectName() == null ? "" : temp
							.getProjectName())
					+ "/"
					+ (temp.getComplex() == null ? "" : temp.getComplex()
							.getCode()) + "/"
					+ (temp.getSeqNum() == null ? "" : temp.getSeqNum()) + "/"
					+ (temp.getEmsNo() == null ? "" : temp.getEmsNo());
			map.put(key, temp);
		}
		// List dlist = casDao.findCasInvoiceInfo(head, null);
		// for (int i = 0; i < dlist.size(); i++) {
		// CasInvoiceInfo info = (CasInvoiceInfo) dlist.get(i);
		// String key = (info.getCuName() == null ? "" : info.getCuName())
		// + "/"
		// + (info.getCuSpec() == null ? "" : info.getCuSpec())
		// + "/"
		// + (info.getProjectName() == null ? "" : info
		// .getProjectName())
		// + "/"
		// + (info.getComplex() == null ? "" : info.getComplex()
		// .getCode()) + "/"
		// + (info.getSeqNum() == null ? "" : info.getSeqNum());
		// map.remove(key);
		// }

		return new ArrayList(map.values());

	}

	/**
	 * 发票管理中开票与核销的对照关系
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List SumInvoiceAndCancel(String emsNo, ScmCoc scmcoc, String hsName) {
		List list = new ArrayList();
		List<TempSumInvoiceInfo> invoiceList = this.casDao.SumInvoiceInfo(
				emsNo, scmcoc, hsName);
		// System.out.println("invoiceList="+invoiceList);
		List<TempSumInvoiceInfo> cancelList = this.casDao.SumInvoiceInfoCancel(
				emsNo, scmcoc, hsName);
		// System.out.println("cancelList="+cancelList);
		for (TempSumInvoiceInfo item : invoiceList) {
			BillTemp tmp = new BillTemp();
			tmp.setBill1(item.getCustomer());// 客户名称
			tmp.setBill2(item.getCuName());// 报关商品名称
			tmp.setBill3(item.getUnit());// 报关商品单位名称
			tmp.setBillSum1(item.getSumAmount());// 已开票数量
			tmp.setBillSum2(item.getSumWeight());// 已开票重量
			tmp.setBillSum3(item.getSumMoney());// 已开票金额
			for (TempSumInvoiceInfo cancel : cancelList) {
				if (item.getCuName().trim()
						.equalsIgnoreCase(cancel.getCuName().trim())) {
					if (item.getUnit().trim()
							.equalsIgnoreCase(cancel.getUnit().trim())) {
						tmp.setBillSum4(cancel.getSumAmount());// 已核销数
						tmp.setBillSum5(cancel.getSumWeight());// 已核销重量
						tmp.setBillSum6(cancel.getSumMoney());// 已核销金额
						break;
					}
				}
			}
			tmp.setBillSum4(IsNullDouble(tmp.getBillSum4()));
			tmp.setBillSum5(IsNullDouble(tmp.getBillSum5()));
			tmp.setBillSum6(IsNullDouble(tmp.getBillSum6()));

			tmp.setBillSum7(IsNullDouble(tmp.getBillSum1())
					- IsNullDouble(tmp.getBillSum4()));// 未核销数
			tmp.setBillSum8(IsNullDouble(tmp.getBillSum2())
					- IsNullDouble(tmp.getBillSum5()));// 未核销重量
			tmp.setBillSum9(IsNullDouble(tmp.getBillSum3())
					- IsNullDouble(tmp.getBillSum6()));// 未核销金额
			list.add(tmp);
		}
		return list;
	}

	/** check is null by Double para */
	private Double IsNullDouble(Double value) {
		return value == null ? Double.valueOf(0.00) : value;
	}

	/**
	 * 根据条件查找发票与单据的对应关系
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List findInvoiceAndBillCorresponding(String emsNo, ScmCoc scmcoc,
			String hsName) {
		List list = new ArrayList();
		List<Object[]> srclist = this.casDao.findInvoiceAndBillCorresponding(
				emsNo, scmcoc, hsName);
		System.out.println("this is srclist.size()=" + srclist.size());
		if (srclist != null && srclist.size() != 0) {
			for (Object[] item : srclist) {
				BillTemp tmp = new BillTemp();
				tmp.setBill1((String) item[0]);// 厂商名称
				tmp.setBill2((String) item[1]);// 报关名称
				tmp.setBill3((String) item[2]);// 报关单位
				tmp.setBillSum1(IsNullDouble((Double) item[3]));// 送货数量
				tmp.setBillSum2(IsNullDouble((Double) item[4]));// 送货重量
				tmp.setBillSum3(IsNullDouble((Double) item[5]));// 开票数量
				tmp.setBillSum4(IsNullDouble((Double) item[6]));// 开票重量
				tmp.setBillSum5(IsNullDouble((Double) item[7])
						* tmp.getBillSum4());// 开票金额
				tmp.setBillSum6(tmp.getBillSum1() - tmp.getBillSum3());// 未开票数量
				tmp.setBillSum7(tmp.getBillSum2() - tmp.getBillSum4());// 未开票重量

				System.out
						.println("厂商名称+报关名称+报关单位+送货数量+送货重量+开票数量+开票重量+开票金额+未开票数量+未开票重量="
								+ ((String) item[0])
								+ "/"
								+ ((String) item[1])
								+ "/"
								+ ((String) item[2])
								+ "/"
								+ ((Double) item[3])
								+ "/"
								+ ((Double) item[4])
								+ "/"
								+ ((Double) item[5])
								+ "/"
								+ ((Double) item[6])
								+ "/"
								+ (((Double) item[7]) * tmp.getBillSum4())
								+ "/"
								+ (((Double) item[3]) - ((Double) item[5]))
								+ "/"
								+ (((Double) item[4]) - ((Double) item[6])));
				list.add(tmp);
			}
		}
		return list;

	}

	/**
	 * 统计单据中发货数量
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List SumBillNum(String emsNo, ScmCoc scmcoc, String hsName) {
		List list = new ArrayList();
		List<Object[]> srclist = this.casDao.SumBillNum(emsNo, scmcoc, hsName);
		for (Object[] item : srclist) {
			BillTemp tmp = new BillTemp();
			tmp.setBill1((String) item[0]);// 厂商名称
			tmp.setBill2((String) item[1]);// 报关名称
			tmp.setBill3((String) item[2]);// 报关单位
			tmp.setBillSum1(IsNullDouble((Double) item[3]));// 送货数量
			tmp.setBillSum2(IsNullDouble((Double) item[4]));// 送货重量
			tmp.setBillSum3(0.00);
			tmp.setBillSum4(0.00);
			tmp.setBillSum5(0.00);
			tmp.setBillSum6(tmp.getBillSum1());
			tmp.setBillSum7(tmp.getBillSum2());
			list.add(tmp);
		}
		return list;
	}

	/**
	 * 统计发票中的开票数量
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List SumInvoiceNum(String emsNo, ScmCoc scmcoc, String hsName) {
		List list = new ArrayList();
		List<TempSumInvoiceInfo> srclist = this.casDao.SumInvoiceInfo(emsNo,
				scmcoc, hsName);
		for (TempSumInvoiceInfo item : srclist) {
			BillTemp tmp = new BillTemp();
			tmp.setBill1(item.getCustomer());// 厂商名称
			tmp.setBill2(item.getCuName());// 报关名称
			tmp.setBill3(item.getUnit());// 报关单位
			tmp.setBillSum1(0.00);
			tmp.setBillSum2(0.00);
			tmp.setBillSum3(IsNullDouble(item.getSumAmount()));// 开票数量
			tmp.setBillSum4(IsNullDouble(item.getSumWeight()));// 开票重量
			tmp.setBillSum5(IsNullDouble(item.getSumMoney()));// 开票金额
			tmp.setBillSum6(0.00);
			tmp.setBillSum7(0.00);
			list.add(tmp);
		}
		return list;
	}

	/**
	 * 发货与发票开票关系列表
	 * 
	 * @param emsNo
	 * @param scmcoc
	 * @param hsName
	 * @return
	 */
	public List findSumBillAndInvoice(String emsNo, ScmCoc scmcoc, String hsName) {
		List<BillTemp> srclist = this.findInvoiceAndBillCorresponding(emsNo,
				scmcoc, hsName);// 对应列表
		// List<BillTemp> blist = this.SumBillNum(emsNo, scmcoc, hsName);// 单据
		// List<BillTemp> ilist = this.SumInvoiceNum(emsNo, scmcoc, hsName);//
		// 发票
		// if (srclist != null && srclist.size() != 0) {
		// for (int i = 0; i < srclist.size(); i++) {
		// BillTemp item = (BillTemp) srclist.get(i);
		// if (blist != null && blist.size() != 0) {
		// for (BillTemp bill : blist) {// 添加已收货未开票的情况
		// if (item.getBill1().equalsIgnoreCase(bill.getBill1())
		// && item.getBill2().equalsIgnoreCase(
		// bill.getBill2())
		// && item.getBill3().equalsIgnoreCase(
		// bill.getBill3())
		// && bill.getBillSum1() > item.getBillSum1()) {
		// BillTemp billTmp = new BillTemp();
		// billTmp.setBill1(item.getBill1());
		// billTmp.setBill2(item.getBill2());
		// billTmp.setBill3(item.getBill3());
		// billTmp.setBillSum1(bill.getBillSum1()
		// - item.getBillSum1());
		// billTmp.setBillSum2(bill.getBillSum2()
		// - item.getBillSum2());
		// billTmp.setBillSum3(0.00);
		// billTmp.setBillSum4(0.00);
		// billTmp.setBillSum5(0.00);
		// billTmp.setBillSum6(billTmp.getBillSum1());
		// billTmp.setBillSum7(billTmp.getBillSum2());
		// srclist.add(i + 1, billTmp);
		// i++;
		// blist.remove(bill);
		// break;
		// }
		// }
		// }
		// if (ilist != null && ilist.size() != 0) {
		// for (BillTemp invoice : ilist) {// 添加未收货已开票的情况
		// if (item.getBill1()
		// .equalsIgnoreCase(invoice.getBill1())
		// && item.getBill2().equalsIgnoreCase(
		// invoice.getBill2())
		// && item.getBill3().equalsIgnoreCase(
		// invoice.getBill3())
		// && invoice.getBillSum1() > item.getBillSum1()) {
		// BillTemp billTmp = new BillTemp();
		// billTmp.setBill1(item.getBill1());
		// billTmp.setBill2(item.getBill2());
		// billTmp.setBill3(item.getBill3());
		// billTmp.setBillSum1(0.00);
		// billTmp.setBillSum2(0.00);
		// billTmp.setBillSum3(invoice.getBillSum3()
		// - item.getBillSum3());
		// billTmp.setBillSum4(invoice.getBillSum4()
		// - item.getBillSum4());
		// billTmp.setBillSum5(invoice.getBillSum5()
		// - item.getBillSum5());
		// billTmp.setBillSum6(0.00);
		// billTmp.setBillSum7(0.00);
		// srclist.add(i + 1, billTmp);
		// i++;
		// ilist.remove(invoice);
		// break;
		// }
		// }
		// }
		// }
		// if (blist != null && blist.size() != 0) {// 有部分报关商品未做对应（单据）
		// for (BillTemp item : blist) {
		// srclist.add(item);
		// }
		// }
		// if (ilist != null && blist.size() != 0) {// 有部分报关商品未做对应（发票）
		// for (BillTemp item : ilist) {
		// srclist.add(item);
		// }
		// }
		// } else {// 用户没有做单据对应
		// srclist = new ArrayList();
		// if (blist != null && blist.size() != 0) {
		// for (BillTemp item : blist) {
		// srclist.add(item);
		// }
		// }
		// if (ilist != null && ilist.size() != 0) {
		// for (BillTemp item : ilist) {
		// srclist.add(item);
		// }
		// }
		// }
		return srclist;
	}

	/** getMaterialManageDao */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/** setMaterialManageDao */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/**
	 * 转抄单据
	 * 
	 * @param list
	 * @return
	 */
	public List<BillMaster> copyBillMaster(List<BillMaster> list) {
		List<BillMaster> lsResult = new ArrayList<BillMaster>();
		try {
			for (int i = 0; i < list.size(); i++) {
				BillMaster oldB = (BillMaster) list.get(i);
				BillMaster newB = (BillMaster) BeanUtils.cloneBean(oldB);
				// BillMaster newB = new BillMaster();
				newB.setId(null);
				newB.setBillNo(null);
				newB.setIsFlag(false);
				newB.setIsValid(false);
				newB.setKeepAccounts(false);
				this.casDao.saveBillMaster(newB);
				List billDetailList = this.casDao.findBillDetail(oldB);
				for (int j = 0; j < billDetailList.size(); j++) {
					BillDetail b = (BillDetail) BeanUtils
							.cloneBean(billDetailList.get(j));
					BillDetail billDetail = (BillDetail) (billDetailList.get(j));
					String ptPart = billDetail.getPtPart();
					b.setBillMaster(newB);
					b.setId(null);
					b.setBillNo(null);
					b.setPtPart(ptPart);
					b.setCustomNo(null);
					b.setCustomNum(null);
					b.setIsImpExpRequestBill(false);
					b.setIsTransferFactoryBill(false);
					this.casDao.saveBillDetail(b);
				}
				lsResult.add(newB);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	/**
	 * 保存所有单据明细
	 * 
	 * @param billMaster
	 * @param propertyName
	 *            in BillDetail 制单号 String "productNo" , 仓库 WareSet "wareSet"
	 * @param propertyValue
	 *            into BillDetail value
	 * @return
	 */
	public List<BillDetail> saveAllBillDetailByMaster(BillMaster billMaster,
			String propertyName, Object propertyValue) {
		List<BillDetail> lsResult = new ArrayList<BillDetail>();
		try {
			List billDetailList = this.casDao.findBillDetail(billMaster);
			for (int j = 0; j < billDetailList.size(); j++) {
				BillDetail b = (BillDetail) billDetailList.get(j);
				BeanUtils.setProperty(b, propertyName, propertyValue);
				this.casDao.saveBillDetail(b);
				lsResult.add(b);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	// /**
	// * 查找送货转厂耗料情况
	// *
	// * @param request
	// * @param balanceInfo
	// * @return
	// */
	// public List findDeliveryToPlantDzsc(BalanceInfo balanceInfo, Date date) {
	// // 传递过来用来作为查询条件的料件名称
	// String ptName = balanceInfo.getName() == null ? "" : balanceInfo
	// .getName();
	// // 传递过来用来作为查询条件的料件规格
	// String ptSpce = balanceInfo.getSpec() == null ? "" : balanceInfo
	// .getSpec();
	// // 传递过来用来作为查询条件的料件的单位
	// String unit = balanceInfo.getUnitName() == null ? "" : balanceInfo
	// .getUnitName();
	// Hashtable<String, TempDeliveryToPlant> diffirentMap = new
	// Hashtable<String, TempDeliveryToPlant>();
	// String myKey = balanceInfo.getKey();
	// System.out.println("myKey=" + myKey);
	// String key = ptName + "/" + ptSpce + "/" + unit;
	// // System.out.println("ptName1=" + ptName);
	// // System.out.println("ptSpce1=" + ptSpce);
	// // System.out.println("unit1=" + unit);
	// // System.out.println("key=" + key);
	// List resultList = new ArrayList();
	// List list = this.casDao.findDeliveryToPlant(ptName, ptSpce, unit, date);
	// // System.out.println("list.size()=" + list.size());
	// for (int i = 0; i < list.size(); i++) {
	// // 临时实体类
	// TempDeliveryToPlant temp = new TempDeliveryToPlant();
	// String name = "";
	// String spce = "";
	// String unitName = "";
	// double carryOverExportDeclarationsAmount = 0.0;
	//
	// String billName2009 = "";
	// String billSpce2009 = "";
	// String billUnitName2009 = "";
	// double carriedOverExportBillsAmount2009 = 0.0;
	//
	// String billName2011 = "";
	// String billSpce2011 = "";
	// String billUnitName2011 = "";
	// double carriedOverExportBillsAmount2011 = 0.0;
	//
	// String billName2012 = "";
	// String billSpce2012 = "";
	// String billUnitName2012 = "";
	// double carriedOverExportBillsAmount2012 = 0.0;
	//
	// String billName2102 = "";
	// String billSpce2102 = "";
	// String billUnitName2102 = "";
	// double carriedOverExportBillsAmount2102 = 0.0;
	// Object[] objs = (Object[]) list.get(i);
	// if (objs[0] != null) {
	// name = (String) objs[0];
	// }
	// if (objs[1] != null) {
	// spce = (String) objs[1];
	// }
	// if (objs[2] != null) {
	// unitName = (String) objs[2];
	// }
	// if (objs[3] != null) {
	// carryOverExportDeclarationsAmount = (Double) objs[3];
	// }
	// // System.out.println("name=" + name);
	// // System.out.println("spce=" + spce);
	// // System.out.println("unitName=" + unitName);
	// // System.out.println("carryOverExportDeclarationsAmount="
	// // + carryOverExportDeclarationsAmount);
	// List fromBillType2009List = this.casDao.findAmountFromBill(name,
	// spce, unitName, date, "2009");//结转成品退货单
	// // System.out.println("fromBillType2009List.size()="
	// // + fromBillType2009List.size());
	// if (fromBillType2009List.size() > 0) {
	// for (int j = 0; j < fromBillType2009List.size(); j++) {
	// Object[] objsBill2009 = (Object[]) fromBillType2009List
	// .get(j);
	// if (objsBill2009[0] != null) {
	// billName2009 = (String) objsBill2009[0];
	// }
	// if (objsBill2009[1] != null) {
	// billSpce2009 = (String) objsBill2009[1];
	// }
	// if (objsBill2009[2] != null) {
	// billUnitName2009 = (String) objsBill2009[2];
	// }
	// if (objsBill2009[3] != null) {
	// carriedOverExportBillsAmount2009 = (Double) objsBill2009[3];
	// }
	// }
	// // System.out.println("carriedOverExportBillsAmount2009="
	// // + carriedOverExportBillsAmount2009);
	// }
	//
	// List fromBillType2011List = this.casDao.findAmountFromBill(name,
	// spce, unitName, date, "2011");//已交货未结转期初单
	// // System.out.println("fromBillType2011List.size()="
	// // + fromBillType2011List.size());
	// if (fromBillType2011List.size() > 0) {
	// for (int t = 0; t < fromBillType2011List.size(); t++) {
	// Object[] objsBill2011 = (Object[]) fromBillType2011List
	// .get(t);
	// if (objsBill2011[0] != null) {
	// billName2011 = (String) objsBill2011[0];
	// }
	// if (objsBill2011[1] != null) {
	// billSpce2011 = (String) objsBill2011[1];
	// }
	// if (objsBill2011[2] != null) {
	// billUnitName2011 = (String) objsBill2011[2];
	// }
	// if (objsBill2011[3] != null) {
	// carriedOverExportBillsAmount2011 = (Double) objsBill2011[3];
	// }
	// }
	// // System.out.println("carriedOverExportBillsAmount2011="
	// // + carriedOverExportBillsAmount2011);
	// }
	// List fromBillType2012List = this.casDao.findAmountFromBill(name,
	// spce, unitName, date, "2012");//已结转未交货期初单
	// // System.out.println("fromBillType2012List.size()="
	// // + fromBillType2012List.size());
	// if (fromBillType2012List.size() > 0) {
	// for (int t = 0; t < fromBillType2012List.size(); t++) {
	// Object[] objsBill2012 = (Object[]) fromBillType2012List
	// .get(t);
	// if (objsBill2012[0] != null) {
	// billName2012 = (String) objsBill2012[0];
	// }
	// if (objsBill2012[1] != null) {
	// billSpce2012 = (String) objsBill2012[1];
	// }
	// if (objsBill2012[2] != null) {
	// billUnitName2012 = (String) objsBill2012[2];
	// }
	// if (objsBill2012[3] != null) {
	// carriedOverExportBillsAmount2012 = (Double) objsBill2012[3];
	// }
	// }
	// // System.out.println("carriedOverExportBillsAmount2012="
	// // + carriedOverExportBillsAmount2012);
	// }
	// List fromBillType2102List = this.casDao.findAmountFromBill(name,
	// spce, unitName, date, "2102");//结转出口
	// // System.out.println("fromBillType2102List.size()="
	// // + fromBillType2102List.size());
	// if (fromBillType2102List.size() > 0) {
	// for (int t = 0; t < fromBillType2102List.size(); t++) {
	// Object[] objsBill2102 = (Object[]) fromBillType2102List
	// .get(t);
	// if (objsBill2102[0] != null) {
	// billName2102 = (String) objsBill2102[0];
	// }
	// if (objsBill2102[1] != null) {
	// billSpce2102 = (String) objsBill2102[1];
	// }
	// if (objsBill2102[2] != null) {
	// billUnitName2102 = (String) objsBill2102[2];
	// }
	// if (objsBill2102[3] != null) {
	// carriedOverExportBillsAmount2102 = (Double) objsBill2102[3];
	// }
	// }
	// // System.out.println("carriedOverExportBillsAmount2102="
	// // + carriedOverExportBillsAmount2102);
	// }
	// String key1 = name + "/" + spce + "/" + unitName;
	// // System.out.println("key1=" + key1);
	// // 结转出口单据数量：上述名称规格中己生效的（结转出口-结转成品退货单+已结转未交货期初单-已交货未结转期初单）
	// double result = carriedOverExportBillsAmount2102
	// - carriedOverExportBillsAmount2009
	// - carriedOverExportBillsAmount2012
	// + carriedOverExportBillsAmount2011;
	// // System.out.println("result=" + result);
	// // 成品名称
	// temp.setPtName(name);
	// // 成品规格
	// temp.setPtSpec(spce);
	// // 成品单位
	// temp.setPtUnitName(unitName);
	// // 设置结转出口单据数量
	// temp
	// .setCarryOverExportDeclarationsAmount(carryOverExportDeclarationsAmount);
	// temp.setCarriedOverExportBillsAmount(result);
	// // 结转出口报关单数量*合同单耗/（1-损耗）折原料
	// Double carryOverExportDeclarationRawMaterials = 0.0;
	// // 结转出口单据折原料：结转出口单据数量*合同单耗/（1-损耗）
	// Double carriedOverExportBillsRawMaterials = 0.0;
	// // 转收送货折原料差异：结转出口报关单折原料-结转出口单据折原料
	// Double carryOverSendReceiveGoodsDifference = 0.0;
	//
	// // // 查询出来的最大（最接近当前得）开始生效日期
	// // Date maxBeginDate = this.casDao.findBeginDateFromDjt(name, spce,
	// // unitName);
	// String maxEmsNo = this.casDao.findMaxEmsNoFromDjt(name, spce,
	// unitName);
	// // System.out.println("maxEmsNo=" + maxEmsNo);
	//
	// // 单耗
	// Double unitWare = 0.0;
	// // 损耗
	// Double ware = 0.0;
	// // 合同号码
	// String emsNo = "";
	// // 查询出来的单耗，损耗，以及合同号
	// if (maxEmsNo != null) {
	// List unitWareList = this.casDao
	// .findUnitWareAndWareFromHtBomByMaxEmsNo(ptName, ptSpce,
	// unit, name, spce, unitName, maxEmsNo);
	// // System.out.println("unitWareList.size()=" +
	// // unitWareList.size());
	// if (unitWareList.size() > 0) {
	// for (int t = 0; t < unitWareList.size(); t++) {
	// Object[] unitWareObjs = (Object[]) unitWareList.get(0);
	// if (unitWareObjs[0] != null) {
	// unitWare = (Double) unitWareObjs[0];
	// // System.out.println("unitWare=" + unitWare);
	// }
	// if (unitWareObjs[1] != null) {
	// ware = ((Double) unitWareObjs[1]) / 100;
	// // System.out.println("ware=" + ware);
	// }
	// if (unitWareObjs[2] != null) {
	// emsNo = (String) unitWareObjs[2];
	// // System.out.println("emsNo=" + emsNo);
	// }
	// }
	// }
	// }
	//
	// carryOverExportDeclarationRawMaterials =
	// (carryOverExportDeclarationsAmount * unitWare)
	// / (1 - ware);
	// carriedOverExportBillsRawMaterials = (result * unitWare)
	// / (1 - ware);
	// carryOverSendReceiveGoodsDifference = carriedOverExportBillsRawMaterials
	// - carryOverExportDeclarationRawMaterials;
	// temp.setContractConsumption(unitWare);
	// temp.setContracLoss(ware);
	// temp.setConrtact(maxEmsNo);
	// temp
	// .setCarryOverExportDeclarationRawMaterials(carryOverExportDeclarationRawMaterials);
	// temp
	// .setCarriedOverExportBillsRawMaterials(carriedOverExportBillsRawMaterials);
	// temp
	// .setCarryOverSendReceiveGoodsDifference(carryOverSendReceiveGoodsDifference);
	// if (diffirentMap.get(key1) == null) {
	// diffirentMap.put(key1, temp);
	// }
	//
	// // resultList.add(temp);
	// }
	// // /结转出口
	// List listFromBill = this.casDao.findDeliveryToPlantFromBillDzsc("2102",
	// ptName, ptSpce, unit, date);
	// for (int j = 0; j < listFromBill.size(); j++) {
	// TempDeliveryToPlant tempBill = new TempDeliveryToPlant();
	// String name = "";
	// String spce = "";
	// String unitName = "";
	// Double amountBill = 0.0;
	// double carryOverExportDeclarationsAmount = 0.0;
	// Object[] objs = (Object[]) listFromBill.get(j);
	// if (objs[0] != null) {
	// name = (String) objs[0];
	// }
	// if (objs[1] != null) {
	// spce = (String) objs[1];
	// }
	// if (objs[2] != null) {
	// unitName = (String) objs[2];
	// }
	// if (objs[3] != null) {
	// amountBill = (Double) objs[3];
	// }
	// String keys = name + "/" + spce + "/" + unitName;
	// // 合同号码
	// String emsNo = this.casDao
	// .findMaxEmsNoFromDjt(name, spce, unitName);
	// List listWare = this.casDao.findUnitWareAndWareFromHtBomByMaxEmsNo(
	// ptName, ptSpce, unit, name, spce, unitName, emsNo);
	// // 单耗
	// Double unitWare = 0.0;
	// // 损耗
	// Double ware = 0.0;
	// // 结转出口报关单数量*合同单耗/（1-损耗）折原料
	// Double carryOverExportDeclarationRawMaterials = 0.0;
	// // 结转出口单据折原料：结转出口单据数量*合同单耗/（1-损耗）
	// Double carriedOverExportBillsRawMaterials = 0.0;
	// // 转收送货折原料差异：结转出口报关单折原料-结转出口单据折原料
	// Double carryOverSendReceiveGoodsDifference = 0.0;
	// if (listWare.size() > 0) {
	// for (int t = 0; t < listWare.size(); t++) {
	// Object[] unitWareObjs = (Object[]) listWare.get(0);
	// if (unitWareObjs[0] != null) {
	// unitWare = (Double) unitWareObjs[0];
	// // System.out.println("unitWare=" + unitWare);
	// }
	// if (unitWareObjs[1] != null) {
	// ware = ((Double) unitWareObjs[1]) / 100;
	// // System.out.println("ware=" + ware);
	// }
	// }
	// }
	// carryOverExportDeclarationRawMaterials =
	// (carryOverExportDeclarationsAmount * unitWare)
	// / (1 - ware);
	// carriedOverExportBillsRawMaterials = (amountBill * unitWare)
	// / (1 - ware);
	// carryOverSendReceiveGoodsDifference = carriedOverExportBillsRawMaterials
	// - carryOverExportDeclarationRawMaterials;
	// tempBill.setPtName(name);
	// tempBill.setPtSpec(spce);
	// tempBill.setPtUnitName(unitName);
	// tempBill.setContractConsumption(unitWare);
	// tempBill.setContracLoss(ware);
	// tempBill.setCarriedOverExportBillsAmount(amountBill);
	// tempBill.setConrtact(emsNo);
	// tempBill
	// .setCarryOverExportDeclarationRawMaterials(carryOverExportDeclarationRawMaterials);
	// tempBill
	// .setCarriedOverExportBillsRawMaterials(carriedOverExportBillsRawMaterials);
	// tempBill
	// .setCarryOverSendReceiveGoodsDifference(carryOverSendReceiveGoodsDifference);
	// if (diffirentMap.get(keys) == null) {
	// diffirentMap.put(keys, tempBill);
	// }
	// }
	// resultList.addAll(diffirentMap.values());
	// // System.out.println("resultList.size()=" + resultList.size());
	// return resultList;
	// }

	// /**
	// * 查找送货转厂耗料情况(电子化手册)
	// *
	// * @param request
	// * @param balanceInfo
	// * @return
	// */
	// public List findDeliveryToPlantBcs(BalanceInfo balanceInfo, Date date) {
	// // 传递过来用来作为查询条件的料件名称
	// String ptName = balanceInfo.getName() == null ? "" : balanceInfo
	// .getName();
	// // 传递过来用来作为查询条件的料件规格
	// String ptSpce = balanceInfo.getSpec() == null ? "" : balanceInfo
	// .getSpec();
	// // 传递过来用来作为查询条件的料件的单位
	// String unit = balanceInfo.getUnitName() == null ? "" : balanceInfo
	// .getUnitName();
	// Hashtable<String, TempDeliveryToPlant> diffirentMap = new
	// Hashtable<String, TempDeliveryToPlant>();
	// String imgKey = ptName + "/" + ptSpce + "/" + unit;
	// List resultList = new ArrayList();
	// List list = this.casDao.findDeliveryToPlantDZHSC(ptName, ptSpce, unit,
	// date);
	// for (int i = 0; i < list.size(); i++) {
	// // 临时实体类
	// TempDeliveryToPlant temp = new TempDeliveryToPlant();
	// String name = "";
	// String spce = "";
	// String unitName = "";
	// double carryOverExportDeclarationsAmount = 0.0;
	// String customer = "";
	//
	// String billName2009 = "";
	// String billSpce2009 = "";
	// String billUnitName2009 = "";
	// double carriedOverExportBillsAmount2009 = 0.0;
	//
	// String billName2011 = "";
	// String billSpce2011 = "";
	// String billUnitName2011 = "";
	// double carriedOverExportBillsAmount2011 = 0.0;
	//
	// String billName2012 = "";
	// String billSpce2012 = "";
	// String billUnitName2012 = "";
	// double carriedOverExportBillsAmount2012 = 0.0;
	//
	// String billName2102 = "";
	// String billSpce2102 = "";
	// String billUnitName2102 = "";
	// double carriedOverExportBillsAmount2102 = 0.0;
	//
	// Object[] objs = (Object[]) list.get(i);
	// if (objs[0] != null) {
	// name = (String) objs[0];
	// }
	// if (objs[1] != null) {
	// spce = (String) objs[1];
	// }
	// if (objs[2] != null) {
	// unitName = (String) objs[2];
	// }
	// if (objs[3] != null) {
	// carryOverExportDeclarationsAmount = (Double) objs[3];
	// }
	// if (objs[4] != null) {
	// customer = (String) objs[4];
	// }
	// String key = name + "/" + spce + "/" + unitName + "/" + customer;
	//
	// List fromBillType2009List = this.casDao.findBcsAmountFromBill(key,
	// date, "2009");//结转成品退货单
	//
	// if (fromBillType2009List.size() > 0) {
	// for (int j = 0; j < fromBillType2009List.size(); j++) {
	// Object[] objsBill2009 = (Object[]) fromBillType2009List
	// .get(j);
	// if (objsBill2009[0] != null) {
	// billName2009 = (String) objsBill2009[0];
	// }
	// if (objsBill2009[1] != null) {
	// billSpce2009 = (String) objsBill2009[1];
	// }
	// if (objsBill2009[2] != null) {
	// billUnitName2009 = (String) objsBill2009[2];
	// }
	// if (objsBill2009[3] != null) {
	// carriedOverExportBillsAmount2009 = (Double) objsBill2009[3];
	// }
	// }
	// }
	//
	// List fromBillType2011List = this.casDao.findBcsAmountFromBill(key,
	// date, "2011");//已交货未结转期初单
	// if (fromBillType2011List.size() > 0) {
	// for (int t = 0; t < fromBillType2011List.size(); t++) {
	// Object[] objsBill2011 = (Object[]) fromBillType2011List
	// .get(t);
	// if (objsBill2011[0] != null) {
	// billName2011 = (String) objsBill2011[0];
	// }
	// if (objsBill2011[1] != null) {
	// billSpce2011 = (String) objsBill2011[1];
	// }
	// if (objsBill2011[2] != null) {
	// billUnitName2011 = (String) objsBill2011[2];
	// }
	// if (objsBill2011[3] != null) {
	// carriedOverExportBillsAmount2011 = (Double) objsBill2011[3];
	// }
	// }
	// }
	// List fromBillType2012List = this.casDao.findBcsAmountFromBill(key,
	// date, "2012");//已结转未交货期初单
	// if (fromBillType2012List.size() > 0) {
	// for (int t = 0; t < fromBillType2012List.size(); t++) {
	// Object[] objsBill2012 = (Object[]) fromBillType2012List
	// .get(t);
	// if (objsBill2012[0] != null) {
	// billName2012 = (String) objsBill2012[0];
	// }
	// if (objsBill2012[1] != null) {
	// billSpce2012 = (String) objsBill2012[1];
	// }
	// if (objsBill2012[2] != null) {
	// billUnitName2012 = (String) objsBill2012[2];
	// }
	// if (objsBill2012[3] != null) {
	// carriedOverExportBillsAmount2012 = (Double) objsBill2012[3];
	// }
	// }
	// }
	// List fromBillType2102List = this.casDao.findBcsAmountFromBill(key,
	// date, "2102");//结转出口
	// if (fromBillType2102List.size() > 0) {
	// for (int t = 0; t < fromBillType2102List.size(); t++) {
	// Object[] objsBill2102 = (Object[]) fromBillType2102List
	// .get(t);
	// if (objsBill2102[0] != null) {
	// billName2102 = (String) objsBill2102[0];
	// }
	// if (objsBill2102[1] != null) {
	// billSpce2102 = (String) objsBill2102[1];
	// }
	// if (objsBill2102[2] != null) {
	// billUnitName2102 = (String) objsBill2102[2];
	// }
	// if (objsBill2102[3] != null) {
	// carriedOverExportBillsAmount2102 = (Double) objsBill2102[3];
	// }
	// }
	// }
	//
	// String exgKey = name + "/" + spce + "/" + unitName;
	// // 结转出口单据数量：上述名称规格中己生效的（结转出口-结转成品退货单+已结转未交货期初单-已交货未结转期初单）
	// //已交货未结转期初单//已结转未交货期初单
	// double result = carriedOverExportBillsAmount2102
	// - carriedOverExportBillsAmount2009
	// - carriedOverExportBillsAmount2012
	// + carriedOverExportBillsAmount2011;
	// // 成品名称
	// temp.setPtName(name);
	// // 成品规格
	// temp.setPtSpec(spce);
	// // 成品单位
	// temp.setPtUnitName(unitName);
	// // 设置结转出口单据数量
	// temp
	// .setCarryOverExportDeclarationsAmount(carryOverExportDeclarationsAmount);
	// temp.setCarriedOverExportBillsAmount(result);
	// // 结转出口报关单数量*合同单耗/（1-损耗）折原料
	// Double carryOverExportDeclarationRawMaterials = 0.0;
	// // 结转出口单据折原料：结转出口单据数量*合同单耗/（1-损耗）
	// Double carriedOverExportBillsRawMaterials = 0.0;
	// // 转收送货折原料差异：结转出口报关单折原料-结转出口单据折原料
	// Double carryOverSendReceiveGoodsDifference = 0.0;
	// // // 查询出来的最大（最接近当前得）开始生效日期
	// String maxImpContract = this.casDao.findMaxImpContractNoFromBcs(
	// exgKey, imgKey);
	// // 单耗
	// Double unitWare = 0.0;
	// // 损耗
	// Double ware = 0.0;
	// // 合同号码
	// String emsNo = "";
	// // 查询出来的单耗，损耗，以及合同号
	// if (maxImpContract != null) {
	// List unitWareList = this.casDao
	// .findUnitWareAndWareFromHtBomByMaxEmsNoDZHSC(ptName,
	// ptSpce, unit, name, spce, unitName,
	// maxImpContract);
	// if (unitWareList.size() > 0) {
	// for (int t = 0; t < unitWareList.size(); t++) {
	// Object[] unitWareObjs = (Object[]) unitWareList.get(0);
	// if (unitWareObjs[0] != null) {
	// unitWare = (Double) unitWareObjs[0];
	// }
	// if (unitWareObjs[1] != null) {
	// ware = ((Double) unitWareObjs[1]) / 100;
	// }
	// if (unitWareObjs[2] != null) {
	// emsNo = (String) unitWareObjs[2];
	// }
	// }
	// }
	// }
	// carryOverExportDeclarationRawMaterials =
	// (carryOverExportDeclarationsAmount * unitWare)
	// / (1 - ware);
	// carriedOverExportBillsRawMaterials = (result * unitWare)
	// / (1 - ware);
	// carryOverSendReceiveGoodsDifference = carriedOverExportBillsRawMaterials
	// - carryOverExportDeclarationRawMaterials;
	// temp.setContractConsumption(unitWare);
	// temp.setContracLoss(ware);
	// temp.setConrtact(maxImpContract);
	// temp.setCustomer(customer);
	// temp
	// .setCarryOverExportDeclarationRawMaterials(carryOverExportDeclarationRawMaterials);
	// temp
	// .setCarriedOverExportBillsRawMaterials(carriedOverExportBillsRawMaterials);
	// temp
	// .setCarryOverSendReceiveGoodsDifference(carryOverSendReceiveGoodsDifference);
	// if (diffirentMap.get(key) == null) {
	// diffirentMap.put(key, temp);
	// }
	//
	// // resultList.add(temp);
	// }
	// //结转出口
	// List listFromBill = this.casDao.findDeliveryToPlantFromBillBcs(
	// "2102", ptName, ptSpce, unit, date);
	// for (int j = 0; j < listFromBill.size(); j++) {
	// TempDeliveryToPlant tempBill = new TempDeliveryToPlant();
	// String name = "";
	// String spce = "";
	// String unitName = "";
	// String customer = "";
	// Double amountBill = 0.0;
	// double carryOverExportDeclarationsAmount = 0.0;
	// Object[] objs = (Object[]) listFromBill.get(j);
	// if (objs[0] != null) {
	// name = (String) objs[0];
	// }
	// if (objs[1] != null) {
	// spce = (String) objs[1];
	// }
	// if (objs[2] != null) {
	// unitName = (String) objs[2];
	// }
	// if (objs[3] != null) {
	// amountBill = (Double) objs[3];
	// }
	// if (objs[4] != null) {
	// customer = (String) objs[4];
	// }
	// String keys = name + "/" + spce + "/" + unitName + "/" + customer;
	// String exgKey = name + "/" + spce + "/" + unitName;
	// // 合同号码
	// String impContractNo = this.casDao.findMaxImpContractNoFromBcs(
	// exgKey, imgKey);
	// List listWare = this.casDao
	// .findUnitWareAndWareFromHtBomByMaxEmsNoDZHSC(ptName,
	// ptSpce, unit, name, spce, unitName, impContractNo);
	// // 单耗
	// Double unitWare = 0.0;
	// // 损耗
	// Double ware = 0.0;
	// // 结转出口报关单数量*合同单耗/（1-损耗）折原料
	// Double carryOverExportDeclarationRawMaterials = 0.0;
	// // 结转出口单据折原料：结转出口单据数量*合同单耗/（1-损耗）
	// Double carriedOverExportBillsRawMaterials = 0.0;
	// // 转收送货折原料差异：结转出口报关单折原料-结转出口单据折原料
	// Double carryOverSendReceiveGoodsDifference = 0.0;
	// if (listWare.size() > 0) {
	// for (int t = 0; t < listWare.size(); t++) {
	// Object[] unitWareObjs = (Object[]) listWare.get(0);
	// if (unitWareObjs[0] != null) {
	// unitWare = (Double) unitWareObjs[0];
	// }
	// if (unitWareObjs[1] != null) {
	// ware = ((Double) unitWareObjs[1]) / 100;
	// }
	// }
	// }
	// carryOverExportDeclarationRawMaterials =
	// (carryOverExportDeclarationsAmount * unitWare)
	// / (1 - ware);
	// carriedOverExportBillsRawMaterials = (amountBill * unitWare)
	// / (1 - ware);
	// carryOverSendReceiveGoodsDifference = carriedOverExportBillsRawMaterials
	// - carryOverExportDeclarationRawMaterials;
	// tempBill.setPtName(name);
	// tempBill.setPtSpec(spce);
	// tempBill.setPtUnitName(unitName);
	// tempBill.setContractConsumption(unitWare);
	// tempBill.setContracLoss(ware);
	// tempBill.setCarriedOverExportBillsAmount(amountBill);
	// tempBill.setConrtact(impContractNo);
	// tempBill.setCustomer(customer);
	// tempBill
	// .setCarryOverExportDeclarationRawMaterials(carryOverExportDeclarationRawMaterials);
	// tempBill
	// .setCarriedOverExportBillsRawMaterials(carriedOverExportBillsRawMaterials);
	// tempBill
	// .setCarryOverSendReceiveGoodsDifference(carryOverSendReceiveGoodsDifference);
	// if (diffirentMap.get(keys) == null) {
	// diffirentMap.put(keys, tempBill);
	// }
	// }
	// resultList.addAll(diffirentMap.values());
	// return resultList;
	// }

	/**
	 * 转换在产品期初单（半成品折过来）
	 * 
	 * @author wss
	 */
	public void convertToInitFromSemiProduct() {

		String year = CommonUtils.getYear();// 年份为当年
		String billNo = year + "01010002";// 单据号
		// 1.删除原有的
		if (this.isExistInitBillMasterMadeBySemiProduct()) {
			this.casDao.deleteInitBillMadeBySemiProduct(billNo);
		}
		// 2.获取半成品（入仓 - 出仓）单据体
		List<BillDetail> ls = this.getBillDetailFromSeimProduct();
		// 3.根据bom折算在产品期初单
		List<BillDetail> list = this.converToInitBillWithBom(ls);

		// 4.生成在产品期初单
		this.makeInitBillFromSemiProduct(list, billNo);

	}

	/**
	 * 判断是否有 在产品期初单（半成品折过来的）
	 * 
	 * @author wss
	 */
	public boolean isExistInitBillMasterMadeBySemiProduct() {
		String year = CommonUtils.getYear();// 年份为当年
		String billNo = year + "01010002";// 单据号
		// 1.获取在产品期初单（属于半成品期初单折过来的）
		List ls = casDao.findInitBillMasterMadeBySemiProduct(billNo);
		// 2.判断是否存在
		if (ls == null || ls.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断当前半成品单据是否已经折算为在产品期初单
	 * 
	 * @author wss2010.10.08
	 */
	public boolean isAlreadyConvertToInit(String billNo) {
		// 1.获取在产品期初单（属于半成品单据折算过来的）
		List ls = casDao.findInitBillMasterMadeBySemiProduct(billNo);
		// 2.判断是否存在
		if (ls == null || ls.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 生成在产品期初单
	 * 
	 * @param list
	 * @author wss
	 */
	private void makeInitBillFromSemiProduct(List<BillDetail> list,
			String billNo) {
		if (list != null && list.size() > 0) {
			// String year = CommonUtils.getYear();//年份为当年
			String type = MaterielType.MATERIEL;// 类型在料件
			String billDetail = " BillDetailMateriel ";// 单据体表名

			// 1.将料号 与 工厂和实际客户对应表 作对应
			List<FactoryAndFactualCustomsRalation> flist = this.casDao
					.findFactoryAndFactualCustomsRalation(type);

			HashMap<String, FactoryAndFactualCustomsRalation> fMap = new HashMap();
			for (FactoryAndFactualCustomsRalation f : flist) {
				if (fMap.get(f.getMateriel().getPtNo()) == null) {
					fMap.put(f.getMateriel().getPtNo(), f);
				}
			}

			// 2.日期为当年1月1日
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(CommonUtils.getYear()));
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date beginDate = calendar.getTime();

			// 3.生成单据头
			BillMaster billMaster = new BillMasterMateriel();

			billMaster.setBillNo(billNo);
			billMaster.setBillType(this.casDao.findBillTypeByCode("1002"));
			billMaster.setCompany(CommonUtils.getCompany());
			billMaster.setIsValid(true);
			billMaster.setValidDate(beginDate);
			billMaster.setCreateDate(new Date());
			billMaster.setNotice("折算来自半成品");
			this.casDao.saveOrUpdate(billMaster);

			Map<String, WareSet> wareSetMap = new HashMap<String, WareSet>();
			List<WareSet> wareSet = this.casDao.findWareSet();
			for (WareSet w : wareSet) {
				wareSetMap.put(w.getCode(), w);// 仓库
			}

			// 4.生成单据明细
			for (BillDetail item : list) {
				BillDetail b = new BillDetailMateriel();

				BillDetail temp = item;
				b.setBillMaster(billMaster);// 单据头
				b.setPtPart(temp.getPtPart());// 料号
				b.setBillNo(billMaster.getBillNo());// 单据号
				b.setPtAmount((temp == null || temp.getPtAmount() == null) ? 0.0
						: temp.getPtAmount());// 工厂数量
				b.setWareSet(temp.getWareSet());// 仓库
				b.setVersion(temp.getVersion());// 版本
				FactoryAndFactualCustomsRalation f = fMap.get(b.getPtPart());
				if (f != null) {
					b.setPtName(f.getMateriel().getFactoryName());// 工厂名称
					b.setPtSpec(f.getMateriel().getFactorySpec());// 工厂规格
					b.setPtUnit(f.getMateriel().getCalUnit());// 工厂单位

					b.setNetWeight(f.getMateriel().getPtNetWeight() == null ? 0.0
							: f.getMateriel().getPtNetWeight()
									* b.getPtAmount());// 净重

					b.setPtPrice(f.getMateriel().getPtPrice());// 工厂单价

					b.setMoney(b.getPtPrice() == null ? 0.0 : b.getPtAmount()
							* b.getPtPrice());// 金额

					b.setComplex(f.getStatCusNameRelationHsn().getComplex());// 报关编码
					b.setHsName(f.getStatCusNameRelationHsn().getCusName());// 报关名称
					b.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());// 报关规格
					b.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());// 海关单位
					b.setHsAmount((b.getPtAmount() == null ? 0.0 : b
							.getPtAmount())
							* (f.getUnitConvert() != null ? f.getUnitConvert()
									: 0.0));// 报关数量
				} else {
					b.setNote("没有相应的对应关系");
				}
				this.casDao.saveOrUpdate(b);

			}
		}
	}

	/**
	 * 获取半成品（入仓 - 出仓）单据体统计
	 * 
	 * @author wss
	 */
	public List<BillDetail> getBillDetailFromSeimProduct() {
		HashMap<String, BillDetail> f1Map = new HashMap();

		// 1.单据体日期为：01.01--12.31
		Date beginDate = CommonUtils.getBeginDate(
				Integer.valueOf(CommonUtils.getYear()), 0, 1);
		Date endDate = CommonUtils.getEndDate(
				Integer.valueOf(CommonUtils.getYear()), 11, 31);

		List<Condition> conditions = new ArrayList<Condition>();

		// 2.表为：半成品明细表
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);//

		String selects = " select sum(a.ptAmount),a.billMaster.billType.code,a.ptPart,a.wareSet.code,a.version from "
				+ tableName;
		String where = "a where a.billMaster.validDate >= ? "
				+ " and a.billMaster.validDate <= ? "
				+ " and a.billMaster.isValid = ? " + " and a.ptAmount != ? "
				+ " and a.hsName is not null ";
		List parameters = new ArrayList();
		parameters.add(beginDate);// ?1日期
		parameters.add(endDate);// ?2日期
		parameters.add(new Boolean(true));// ?3是否生效
		parameters.add(0.0);// ?4数量不为0

		// 注意：Hibernate目前不会扩展group的实体
		String groupBy = " group by a.billMaster.billType.code,a.ptPart,a.wareSet.code,a.version ";

		System.out.println("hsql:" + selects + where + groupBy);
		List<Object[]> listBillDetail = casDao.find(selects + where + groupBy,
				parameters.toArray());

		// 3.以 料号 + 仓库 + 版本号 作为key 进行统计
		for (Object[] item : listBillDetail) {
			Double ptAmount = (Double) item[0] == null ? 0.0 : (Double) item[0];
			String typeCode = (String) item[1];
			String ptNo = (String) item[2];
			String wareSetCode = (String) item[3];
			WareSet wareSet = this.casDao.findWareSetByCode(wareSetCode);
			Integer version = item[4] == null ? 0 : (Integer) item[4];

			// key 为 料号 + 仓库 + 版本号
			String key = ptNo + "/" + wareSet.getCode() + "/"
					+ version.toString();

			if (ptNo != null && !ptNo.equals("")) {
				if (typeCode.equals("4001") // 半成品入库
						|| typeCode.equals("4002")// 半成品盘盈单
						|| typeCode.equals("4003")// 委外加工入库
						|| typeCode.equals("4004")// 外发加工半成品退回
						|| typeCode.equals("4005")// 受托加工半成品入库
						|| typeCode.equals("4006")// 外发加工半成品入库
						|| typeCode.equals("4008")// 受托加工半成品返修
				) { // 入库
					if (f1Map.get(key) == null) {
						BillDetail temp = new BillDetail();
						temp.setPtAmount(ptAmount);
						temp.setPtPart(ptNo);
						temp.setWareSet(wareSet);
						temp.setVersion(version);
						f1Map.put(key, temp);
					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() + ptAmount);
					}
				}
				if (typeCode.equals("4101") // 半成品出库
						|| typeCode.equals("4102")// 半成品盘亏单
						|| typeCode.equals("4103")// 委外加工出库
						|| typeCode.equals("4104")// 外发加工半成品返修
						|| typeCode.equals("4105")// 外发加工领料
						|| typeCode.equals("4106")// 外发加工半成品出库
						|| typeCode.equals("4107")// 受托加工半成品退回
						|| typeCode.equals("4108")// 受托加工半成品出库
				) { // 出库
					if (f1Map.get(key) == null) {
						BillDetail temp = new BillDetail();
						temp.setPtAmount(-ptAmount);
						temp.setPtPart(ptNo);
						temp.setWareSet(wareSet);
						temp.setVersion(version);
						f1Map.put(key, temp);
					} else {
						f1Map.get(key).setPtAmount(
								f1Map.get(key).getPtAmount() - ptAmount);
					}
				}
			}
		}

		// 4.去除为0的数据
		List<BillDetail> list = new ArrayList<BillDetail>(f1Map.values());
		List<BillDetail> ls = new ArrayList<BillDetail>();
		for (BillDetail b : list) {
			if (!b.getPtAmount().equals(0.0) || b.getPtAmount() != 0.0) {
				ls.add(b);
			}
		}
		return ls;
	}

	/**
	 * 将半成品单据依bom折 在产品期初单
	 * 
	 * @param ls
	 * @return
	 * @author wss2010.10.08有修改
	 */
	public List<BillDetail> converToInitBillWithBom(List<BillDetail> ls) {
		Map<String, BillDetail> mapBillD = new HashMap<String, BillDetail>();

		List<Map<String, List<Object[]>>> listMaps = this
				.findPtNoVersionAndBomMap();

		// 有版本号时，取相对应版本号的bom
		Map<String, List<Object[]>> mapVersion = new HashMap<String, List<Object[]>>();
		mapVersion = listMaps.get(0);
		// 没有版本号时，取最大版本号的bom
		Map<String, List<Object[]>> mapNoVersion = new HashMap<String, List<Object[]>>();
		mapNoVersion = listMaps.get(1);

		for (BillDetail bill : ls) {
			System.out.println("wss bill.ptNo = " + bill.getPtPart());
			// 1.查找相应的Bom
			// List <MaterialBomDetail> listBom =
			// this.casDao.getProductBom(bill);
			String versionkey = bill.getPtPart() + "/" + bill.getVersion();
			List<Object[]> listBom = mapVersion.get(versionkey);
			if (listBom == null) {
				if ((listBom = mapNoVersion.get(bill.getPtPart())) == null) {
					continue;
				}
			}
			System.out.println("wss listBom.size = " + listBom.size());

			// 2.遍历 依 料号 + 仓库 + 版本 作为key进行统计

			for (int j = 0; j < listBom.size(); j++) {

				Object[] objs = listBom.get(j);

				String ptNo = objs[0] == null ? "" : (String) objs[0];
				Double unitWaste = objs[1] == null ? 0.0 : (Double) objs[1];
				Double unitUsed = objs[2] == null ? 0.0 : (Double) objs[2];
				Double waste = objs[3] == null ? 0.0 : (Double) objs[3];

				// 重新算单项用量
				unitUsed = unitWaste / (1 - waste);

				// key = 料号 + 仓库 + 版本
				String key = ptNo + "/" + bill.getWareSet().getCode() + "/"
						+ bill.getVersion().toString();

				System.out.println("wss key = " + key);

				BillDetail temp;

				if ((temp = mapBillD.get(key)) == null) {
					temp = new BillDetail();
					temp.setPtPart(ptNo);
					temp.setWareSet(bill.getWareSet());
					temp.setVersion(bill.getVersion());
					temp.setPtAmount(unitUsed * bill.getPtAmount());// 单项用量 * 数量
					mapBillD.put(key, temp);
				} else {
					temp.setPtAmount(temp.getPtAmount() + unitUsed
							* bill.getPtAmount());
					mapBillD.put(key, temp);
				}
			}
		}
		List<BillDetail> list = new ArrayList<BillDetail>(mapBillD.values());
		List<BillDetail> listB = new ArrayList<BillDetail>();
		// 3.去除为0的数据
		for (BillDetail b : list) {
			if (!b.getPtAmount().equals(0.0) || b.getPtAmount() != 0.0) {
				listB.add(b);
			}
		}
		return listB;
	}

	/**
	 * 查询CheckBalance
	 * 
	 * @author wss
	 */
	public List findCheckBalance(List<Condition> conditions) {
		List<CheckBalance> checkList = casDao.commonSearch("", "CheckBalance",
				conditions, "", "");
		System.out.println("wss checkList.size " + checkList.size());
		return checkList;
	}

	/**
	 * 分页查询CheckBalance
	 * 
	 * @return
	 * @author wss2010.10.14
	 */
	public List findCheckBalance(int index, int length, Date beginDate,
			Date endDate, String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes) {

		List parameters = new ArrayList();
		String hsql = "select a from CheckBalance a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and a.ptNo = ?";
			parameters.add(ptNo);
		}
		if (wlType != null && !"".equals(wlType)) {
			hsql += " and a.ljCpMark = ? ";
			parameters.add(wlType);
		}

		// 仓库
		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and ( a.wareSet.code = ? ";
			} else {
				hsql += " or  a.wareSet.code = ? ";
			}

			parameters.add(wareSetCode);

			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		// 排序 降序
		hsql += "order by a.id desc";

		System.out.println(" wss hsql = " + hsql);

		return casDao.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 查询CheckBalanceConvertMateriel
	 * 
	 * @author wss
	 */
	public List findCheckBalanceConvertM(List<Condition> conditions) {
		List<CheckBalanceConvertMateriel> checkConvertMaterielList = casDao
				.commonSearch("", "CheckBalanceConvertMateriel", conditions,
						"", "");
		System.out.println("wss checkConvertMaterielList.size "
				+ checkConvertMaterielList.size());
		return checkConvertMaterielList;

	}

	/**
	 * 分页查询CheckBalanceConvertMateriel
	 * 
	 * @return
	 * @author wss2010.10.14
	 */
	public List findCheckBalanceConvertMateriel(int index, int length,
			Date beginDate, Date endDate, String kcType, String ptNo,
			String wlType, List<String> lsWareSetCodes) {

		List parameters = new ArrayList();
		String hsql = "select a from CheckBalanceConvertMateriel a "
				+ " left join fetch a.fatherCheckBalance " + " where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);

		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);

		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and a.ptNo = ? ";
			parameters.add(ptNo);
		}
		if (wlType != null && !"".equals(wlType)) {
			hsql += " and a.ljCpMark = ? ";
			parameters.add(wlType);
		}

		// 仓库
		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and ( a.wareSet.code = ? ";
			} else {
				hsql += " or  a.wareSet.code = ? ";
			}

			parameters.add(wareSetCode);
			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		// 降序 排序
		hsql += "order by a.id desc";

		System.out.println(" wss hsql = " + hsql);

		return casDao.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 查询CheckBalance数量
	 * 
	 * @author wss2010.10.14
	 */
	public int findCheckBalanceCount(Date beginDate, Date endDate,
			String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes) {

		List parameters = new ArrayList();
		String hsql = "select count(a.id) from CheckBalance a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and a.ptNo = ?";
			parameters.add(ptNo);
		}
		if (wlType != null && !"".equals(wlType)) {
			hsql += " and a.ljCpMark = ? ";
			parameters.add(wlType);
		}

		// 仓库
		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and ( a.wareSet.code = ? ";
			} else {
				hsql += " or  a.wareSet.code = ? ";
			}

			parameters.add(wareSetCode);
			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		System.out.println(" wss hsql = " + hsql);

		List list = casDao.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询CheckBalanceConvertMateriel数量
	 * 
	 * @author wss2010.10.14
	 */
	public int findCheckBalanceConvertMaterielCount(Date beginDate,
			Date endDate, String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes) {
		List parameters = new ArrayList();
		String hsql = "select count(a.id) from CheckBalanceConvertMateriel a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and a.ptNo = ?";
			parameters.add(ptNo);
		}
		if (wlType != null && !"".equals(wlType)) {
			hsql += " and a.ljCpMark = ? ";
			parameters.add(wlType);
		}

		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and ( a.wareSet.code = ? ";
			} else {
				hsql += " or  a.wareSet.code = ? ";
			}

			parameters.add(wareSetCode);
			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		System.out.println(" wss hsql = " + hsql);
		List list = casDao.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 成品折算料件（CheckBalance)
	 * 
	 * @return
	 * @author wss
	 */
	public void convertCheckBalance(List list, String taskId,
			List<Condition> conditions) {
		// List<CheckBalanceConvertMateriel> listResult = new
		// ArrayList<CheckBalanceConvertMateriel>();

		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);
		String clientTipMessage = "开始初始化对应关系映射...";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);
		// 对应关系
		Map<String, FactoryAndFactualCustomsRalation> mapPtNoFFC = this
				.findPtNoAndFFC(MaterielType.MATERIEL);

		// //Bom资料
		// clientTipMessage = "开始初始化Bom资料...";
		// logger.info(clientTipMessage);
		// progressInfo.setHintMessage(clientTipMessage);

		// List<Map<String,List<Object[]>>> listMaps =
		// this.findPtNoVersionAndBomMap();
		//
		// //有版本号时，取相对应版本号的bom
		// Map<String,List<Object[]>>mapVersion = new HashMap<String,
		// List<Object[]>>();
		// // mapVersion = listMaps.get(0);
		// //没有版本号时，取最大版本号的bom
		// Map<String,List<Object[]>>mapNoVersion = new HashMap<String,
		// List<Object[]>>();
		// // mapNoVersion = listMaps.get(1);
		//

		System.out.println("wss 全部的list.size = " + list.size());

		clientTipMessage = "开始删除原先可能折算过的... !!";
		logger.info(clientTipMessage);
		progressInfo.setHintMessage(clientTipMessage);

		// 0.先删除原先折算的
		this.casDao.deleteAll(this.findCheckBalanceConvertM(conditions));

		CheckBalanceConvertMateriel cb;
		CheckBalance c;
		for (int i = 0; i < list.size(); i++) {

			c = (CheckBalance) list.get(i);

			// 1.将要折的与不要折的分开计算
			System.out.println("wss cb.getLjCpMark " + c.getLjCpMark());

			if ("0".equals(c.getLjCpMark())) {

				clientTipMessage = "正在复制第 " + (i + 1) + "条料号为" + c.getPtNo()
						+ "的数据";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				cb = new CheckBalanceConvertMateriel();
				cb.setLjCpMark("0");// 物料类型
				cb.setPtNo(c.getPtNo());// 料号
				cb.setCheckDate(c.getCheckDate());// 盘点日期
				cb.setNote(c.getNote());// 备注
				cb.setKcType(c.getKcType());// 库存类型
				cb.setWareSet(c.getWareSet());// 仓库
				cb.setCheckAmount(c.getCheckAmount());// 盘点数量
				cb.setFatherCheckBalance(c);// 父盘点
				cb.setCompany(CommonUtils.getCompany());// 公司

				// 如果发现未执行 【2.折算报关数量】,则要折算报关数量
				if (c.getName() == null) {
					System.out.println("wss 事先没有折算报关数量");
					// 获取报关资料
					calculateCheckBalanceConvertMateriel(mapPtNoFFC, cb);
				}

				// 否则表示已经折算过，直接Copy过来
				else {
					System.out.println("wss 事先有折算报关数量,直接copy的");

					cb.setPtName(c.getPtName());
					cb.setPtSpec(c.getPtSpec());
					cb.setPtUnit(c.getPtUnit());
					cb.setComplex(c.getComplex());
					cb.setName(c.getName());
					cb.setSpec(c.getSpec());
					cb.setHsUnit(c.getHsUnit());
					cb.setUnitConvert(c.getUnitConvert());
					cb.setHsAmount(c.getHsAmount());
					cb.setNote("");
				}

				this.casDao.saveOrUpdate(cb);
				// listResult.add(cb);

			} else {

				clientTipMessage = "正在折算第 " + i + "条料号为" + c.getPtNo() + "的数据";
				logger.info(clientTipMessage);
				progressInfo.setHintMessage(clientTipMessage);

				// 找到BOM资料
				List<Object[]> listBom = new ArrayList<Object[]>();

				// String key = c.getPtNo() + "/" + c.getBomVersion();
				//
				// System.out.println("wss key:" + key);

				if (c.getBomVersion() == null || "".equals(c.getBomVersion())) {
					// listBom =
					// materialManageDao.findMaterialBomByDetailByptNoAndVersion(
					// c.getPtNo(), null);
					listBom = this.findMaterielBomDetail(c.getPtNo(), null);
				} else {
					// listBom =
					// materialManageDao.findMaterialBomByDetailByptNoAndVersion(
					// c.getPtNo(), Integer.parseInt(c.getBomVersion()));
					listBom = this.findMaterielBomDetail(c.getPtNo(),
							Integer.parseInt(c.getBomVersion()));
				}

				// if((listBom = mapVersion.get(key)) == null){
				// listBom = mapNoVersion.get(c.getPtNo());
				// }
				if (listBom != null) {
					System.out.println("wss listBom.size " + listBom.size());
					// 遍历BOM
					for (int j = 0; j < listBom.size(); j++) {
						// MaterialBomDetail bom = listBom.get(j);
						Object[] objs = listBom.get(j);
						//
						// // 料件料号为 key
						// String ptNo = bom.getMateriel().getPtNo();
						// System.out.println("wss bom.ptNo = " + ptNo);
						//
						// // 成品单耗
						// Double unitWaste = bom.getUnitWaste()== null ? 0.0
						// :bom.getUnitWaste();
						//
						// // 成品单 单项用量
						// Double unitUsed = bom.getUnitWaste()/(1 -
						// (bom.getWaste() == null ? 0.0:bom.getWaste()));
						//
						// // 损耗
						// Double waste = bom.getWaste();

						String ptNo = objs[0] == null ? "" : (String) objs[0];
						Double unitWaste = objs[1] == null ? 0.0
								: (Double) objs[1];
						// Double unitUsed = objs[2] == null ?
						// 0.0:(Double)objs[2];
						Double waste = objs[2] == null ? 0.0 : (Double) objs[2];

						// 重新算单项用量
						Double unitUsed = unitWaste / (1 - waste);

						// 成品单耗用量
						Double productMaterialUnitWaste = c.getCheckAmount()
								* unitUsed;

						cb = new CheckBalanceConvertMateriel();
						cb.setLjCpMark("0");// 物料类型
						cb.setPtNo(ptNo);// 料号
						cb.setCheckDate(c.getCheckDate());// 盘点时间
						cb.setNote(c.getNote());// 备注
						cb.setKcType(c.getKcType());// 库存类别
						cb.setWareSet(c.getWareSet());// 仓库
						cb.setCheckAmount(productMaterialUnitWaste);// 盘点数量
						cb.setFatherCheckBalance(c);// 父盘点
						cb.setCompany(CommonUtils.getCompany());
						cb.setUnitWaste(unitWaste);// 单耗
						cb.setWaste(waste);// 损耗
						cb.setUnitUsed(unitUsed);// 单项用量

						// 折算报关数量
						calculateCheckBalanceConvertMateriel(mapPtNoFFC, cb);

						this.casDao.saveOrUpdate(cb);
						// listResult.add(cb);
					}
				}

			}
		}

		return;

		// return listResult;
	}

	/**
	 * 成品折算料件（CheckBalance to CheckBalanceConvertmateriel) One by One
	 * 
	 * @return
	 * @author wss
	 */
	public void convertCheckBalanceToBomOneByOne(CheckBalance c) {

		// 找到BOM
		List<MaterialBomDetail> listBom = new ArrayList<MaterialBomDetail>();

		// 下面的方法简直太idiot了！

		if (c.getBomVersion() == null || "".equals(c.getBomVersion())) {
			listBom = materialManageDao
					.findMaterialBomByDetailByptNoAndVersion(c.getPtNo(), null);
			// wss2010.09.11换成下面的
			// listBom = this.findMaterielBomDetail(c.getPtNo(), null);

		} else {
			listBom = materialManageDao
					.findMaterialBomByDetailByptNoAndVersion(c.getPtNo(),
							Integer.parseInt(c.getBomVersion()));

			// //wss2010.09.11换成下面的
			// listBom = this.findMaterielBomDetail(
			// c.getPtNo(), Integer.parseInt(c.getBomVersion()));
		}

		System.out.println("wss listBom.size " + listBom.size());

		// 遍历BOM
		for (int j = 0; j < listBom.size(); j++) {
			MaterialBomDetail bom = listBom.get(j);

			// 料件料号为 key
			String ptNo = bom.getMateriel().getPtNo();
			System.out.println("wss bom.ptNo = " + ptNo);

			// 成品单耗
			Double unitWaste = bom.getUnitWaste() == null ? 0.0 : bom
					.getUnitWaste();

			// 成品单 单项用量
			Double unitUsed = bom.getUnitWaste()
					/ (1 - (bom.getWaste() == null ? 0.0 : bom.getWaste()));

			// 损耗
			Double waste = bom.getWaste();

			// //wss2010.09.11换成下面的
			//
			// Object[] objs = (Object[])listBom.get(j);
			//
			// // 料件料号为 key
			// String ptNo = objs[0] == null ? "":(String)objs[0];
			// System.out.println("wss bom.ptNo = " + ptNo);
			//
			// // 成品单耗
			// Double unitWaste = objs[1] == null ? 0.0:(Double)objs[1];
			//
			// // 损耗
			// Double waste = objs[2] == null ? 0.0:(Double)objs[2];
			//
			// // 成品单 单项用量
			// Double unitUsed = unitWaste/(1 - waste);

			// 成品单耗用量
			Double productMaterialUnitWaste = c.getCheckAmount() * unitUsed;

			CheckBalanceConvertMateriel cb = new CheckBalanceConvertMateriel();
			cb.setLjCpMark("0");// 物料类型
			cb.setPtNo(ptNo);// 料号
			cb.setCheckDate(c.getCheckDate());// 盘点时间
			cb.setNote(c.getNote());// 备注
			cb.setKcType(c.getKcType());// 库存类别
			cb.setWareSet(c.getWareSet());// 仓库
			cb.setCheckAmount(productMaterialUnitWaste);// 盘点数量
			cb.setFatherCheckBalance(c);// 父盘点
			cb.setCompany(CommonUtils.getCompany());
			cb.setUnitWaste(unitWaste);// 单耗
			cb.setWaste(waste);// 损耗
			cb.setUnitUsed(unitUsed);// 单项用量

			fillAndSaveCheckBalanceConvertMaterielOneByOne(cb);

		}
	}

	/**
	 * 根据父料号，版本号，查找BOM
	 * 
	 * @return List 报关常用工厂BOM料件资料版本号Id、单耗、单项用量、损耗和父件料号
	 * @author wss
	 */
	public List findMaterielBomDetail(String ptNo, Integer version) {

		String hsql = " select a.materiel.ptNo,a.unitWaste,a.waste"
				+ " from MaterialBomDetail a "
				// +
				// " left join a.materiel left join a.version left join
				// a.version.master left join a.version.master.materiel "
				+ " where a.version.master.materiel.ptNo = ? and a.company.id =? ";

		List list = new ArrayList();
		if (version != null) {
			hsql += " and a.version.version=? ";
			list = this.materialManageDao.find(hsql, new Object[] { ptNo,
					CommonUtils.getCompany().getId(), version });

		}

		else {
			hsql += " and a.version.version= (select max(c.version.version) from MaterialBomDetail c"
					+ " where c.version.master = a.version.master)";
			list = this.materialManageDao.find(hsql, new Object[] { ptNo,
					CommonUtils.getCompany().getId() });

		}

		return list;

	}

	/**
	 * 根据客户查找 结转申请单(关封)号
	 * 
	 * @author wss
	 */
	public List findEnvelopNoByScmCoc(String billCode, ScmCoc scmCoc) {
		Boolean isM = true;
		if ("2102".equals(billCode)) {
			isM = false;
		}
		/**
		 * 获得关封单据
		 */
		List ls1 = new ArrayList();
		String hsql1 = " select a.customsEnvelopBillNo,a.beginAvailability,a.scmCoc.name "
				+ " from CustomsEnvelopBill as a "
				+ "	where a.isAvailability =?"
				+ " and a.isImpExpGoods = ? "
				+ " and a.company.id=? "
				+ " and (a.isEndCase=? or a.isEndCase is null)";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(true);
		parameters.add(isM);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(false);

		if (scmCoc != null) {
			hsql1 += " and a.scmCoc=? ";
			parameters.add(scmCoc);
		}

		System.out.println("wss hsql1 :" + hsql1);
		ls1 = this.casDao.find(hsql1, parameters.toArray());

		/**
		 * 获取转厂申请
		 */

		List ls2 = new ArrayList();

		String dateStr = " a.inDate,";
		if (!isM) {
			dateStr = " a.outDate,";
		}

		String hsql2 = " select a.appNo," + dateStr + " a.scmCoc.name "
				+ " from FptAppHead a " + " where a.declareState = ? "
				+ " and a.inOutFlag = ? " + " and a.company.id = ? ";
		List<Object> parameters2 = new ArrayList<Object>();

		parameters2.add(DeclareState.PROCESS_EXE);
		parameters2.add(isM ? "1" : "0");
		parameters2.add(CommonUtils.getCompany().getId());

		if (scmCoc != null) {
			hsql2 += " and a.scmCoc = ? ";
			parameters2.add(scmCoc);
		}

		System.out.println("wss hsql2 :" + hsql2);
		ls2 = this.casDao.find(hsql2, parameters2.toArray());

		ls1.addAll(ls2);

		List list = new ArrayList();

		for (int i = 0; i < ls1.size(); i++) {
			Object[] objs = (Object[]) ls1.get(i);
			TempObject temp = new TempObject();
			temp.setObject((String) objs[0]);
			temp.setObject1((Date) objs[1]);
			temp.setObject2((String) objs[2]);

			list.add(temp);
		}

		return list;
	}

	/**
	 * 将对应关系中的手册事情更新至相应单据中
	 * 
	 * @param ffc
	 * @return
	 * @author wss2010.09.24
	 */
	public void updateEmsNoToBillDetail(StatCusNameRelationHsn s, String type) {
		System.out.println("type = " + type);
		String[] billName;
		if (MaterielType.MATERIEL.equals(type)) {
			billName = new String[] { " BillDetailMateriel ",// 料件
					" BillDetailRemainProduct " };// 残次品

		} else if (MaterielType.FINISHED_PRODUCT.equals(type)) {
			billName = new String[] { " BillDetailProduct ",// 成品
					" BillDetailRemainProduct " };// 残次品

		} else if (MaterielType.REMAIN_MATERIEL.equals(type)) {
			billName = new String[] { " BillDetailLeftoverMateriel " };// 边角料

		} else {
			billName = new String[] { " BillDetailFixture " };// 设备
		}

		List<Object> parameters = new ArrayList<Object>();

		String hsql = "";

		System.out.println("wss 报关资料为：" + s.getComplex() == null ? "" : s
				.getComplex().getCode()
				+ "/"
				+ (s.getCusName() == null ? "" : s.getCusName())
				+ "/"
				+ (s.getCusSpec() == null ? "" : s.getCusSpec())
				+ "/"
				+ (s.getCusUnit() == null ? "" : s.getCusUnit().getName())
				+ "/" + (s.getEmsNo() == null ? "" : s.getEmsNo()));

		for (int i = 0; i < billName.length; i++) {

			parameters.clear();

			hsql = " update " + billName[i] + " a  set a.emsNo = ? "
					+ " where a.complex = ? ";// 报关编码

			parameters.add(s.getEmsNo());
			parameters.add(s.getComplex());

			// 报关名称
			if (s.getCusName() == null || "".equals(s.getCusName())) {
				hsql += " and (a.hsName = ? ";
				hsql += " or a.hsName = ? )";
				parameters.add(null);
				parameters.add("");

			} else {
				hsql += " and a.hsName = ? ";
				parameters.add(s.getCusName());

			}

			// 报关规格
			if (s.getCusSpec() == null || "".equals(s.getCusSpec())) {
				hsql += " and (a.hsSpec = ? ";
				hsql += " or a.hsSpec = ? )";
				parameters.add(null);
				parameters.add("");

			} else {
				hsql += " and a.hsSpec = ? ";
				parameters.add(s.getCusSpec());

			}

			// 报关单位
			hsql += " and a.hsUnit = ? ";
			parameters.add(s.getCusUnit());

			// 只更新手册号为空的，或为null的
			hsql += " and ( a.emsNo is null ";
			hsql += " or a.emsNo = ? )";
			parameters.add("");

			System.out.println("wss hsql = " + hsql);

			int j = this.casDao.batchUpdateOrDelete(hsql, parameters.toArray());

			System.out.println("wss 受影响的条数是 ：" + j);
		}
	}

	/**
	 * 将对应关系中的手册事情更新至相应单据中
	 * 
	 * @author wss2010.10.21用来测试
	 */
	public void updateBillDetailEmsNo(String materielType) {

		String[] billName;

		if (materielType == null) {
			billName = new String[] { " BillDetailMateriel ",// 料件
					" BillDetailRemainProduct ",// 残次品
					" BillDetailProduct ",// 成品
					" BillDetailRemainProduct ",// 残次品
					" BillDetailLeftoverMateriel ",// 边角料
					" BillDetailFixture " };// 设备
		} else if (MaterielType.MATERIEL.equals(materielType)) {
			billName = new String[] { " BillDetailMateriel ",// 料件
					" BillDetailRemainProduct " };// 残次品
		} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
			billName = new String[] { " BillDetailProduct ",// 成品
					" BillDetailRemainProduct " };// 残次品

		} else if (MaterielType.REMAIN_MATERIEL.equals(materielType)) {
			billName = new String[] { " BillDetailLeftoverMateriel " };// 边角料

		} else {
			billName = new String[] { " BillDetailFixture " };// 设备
		}

		List<StatCusNameRelationHsn> lsS = casDao
				.findStatCusNameRelationHsnInUsed(null);

		List<Object> parameters = new ArrayList<Object>();

		String hsql = "";

		for (int i = 0; i < billName.length; i++) {

			for (int j = 0; j < lsS.size(); j++) {

				StatCusNameRelationHsn s = lsS.get(j);

				System.out
						.println("wss 报关资料为：" + s.getComplex() == null ? ""
								: s.getComplex().getCode()
										+ "/"
										+ (s.getCusName() == null ? "" : s
												.getCusName())
										+ "/"
										+ (s.getCusSpec() == null ? "" : s
												.getCusSpec())
										+ "/"
										+ (s.getCusUnit() == null ? "" : s
												.getCusUnit().getName())
										+ "/"
										+ (s.getEmsNo() == null ? "" : s
												.getEmsNo()));

				parameters.clear();

				hsql = " update " + billName[i] + " a  set a.emsNo = ? "
						+ " where a.complex = ? ";// 报关编码

				parameters.add(s.getEmsNo());
				parameters.add(s.getComplex());

				// 报关名称
				if (s.getCusName() == null || "".equals(s.getCusName())) {
					hsql += " and (a.hsName = ? ";
					hsql += " or a.hsName = ? )";
					parameters.add(null);
					parameters.add("");

				} else {
					hsql += " and a.hsName = ? ";
					parameters.add(s.getCusName());

				}

				// 报关规格
				if (s.getCusSpec() == null || "".equals(s.getCusSpec())) {
					hsql += " and (a.hsSpec = ? ";
					hsql += " or a.hsSpec = ? )";
					parameters.add(null);
					parameters.add("");

				} else {
					hsql += " and a.hsSpec = ? ";
					parameters.add(s.getCusSpec());

				}

				// 报关单位
				hsql += " and a.hsUnit = ? ";
				parameters.add(s.getCusUnit());

				// 只更新手册号为空的，或为null的
				hsql += " and ( a.emsNo is null ";
				hsql += " or a.emsNo = ? )";
				parameters.add("");

				System.out.println("wss hsql = " + hsql);

				int k = this.casDao.batchUpdateOrDelete(hsql,
						parameters.toArray());

				System.out.println("wss 受影响的条数是 ：" + k);
			}

		}
	}

	/**
	 * 检查单据,返回有问题的单据
	 * 
	 * @param billCategory
	 *            进出仓类别
	 * @param billType
	 *            单据类型
	 * @author wss2010.09.24
	 */
	public List<BillDetail> checkBillDetail(int billCategory, BillType billType) {
		List<BillDetail> listError = new ArrayList<BillDetail>();

		// 半成品的不作检查
		if (SBillType.HALF_PRODUCE_IN == billCategory
				|| SBillType.HALF_PRODUCE_OUT == billCategory
				|| SBillType.HALF_PRODUCE_INOUT == billCategory) {
			return listError;
		}

		Map<String, String> mapFFC = this.findFFCMap();

		String billTableName = BillUtil
				.getBillDetailTableNameByBillType(billCategory);

		List<BillDetail> listBill = new ArrayList<BillDetail>();

		String hsql = " select a from " + billTableName

		+ " a left join fetch a.billMaster "
				+ " left join fetch a.billMaster.billType "
				+ " where a.company.id = ? "
				+ " and a.billMaster.billType.code not in(?,?,?,?,?)";

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("1001");
		parameters.add("1002");
		parameters.add("2001");
		parameters.add("5002");
		parameters.add("6003");

		if (billType != null) {
			hsql += " and a.billMaster.billType = ? ";
			parameters.add(billType);
		}

		System.out.println("wss hsql:" + hsql);
		listBill = casDao.find(hsql, parameters.toArray());

		for (int i = 0; i < listBill.size(); i++) {
			BillDetail bill = (BillDetail) listBill.get(i);

			// 半成品不作检查
			if ("半成品".equals(bill.getNote())) {
				continue;
			}

			// key:料号 + 报关编码 + 报关名称 + 报关规格 + 报关单位 + 手册号
			String key = (bill.getPtPart() == null ? "" : bill.getPtPart()
					.trim())
					+ "/"
					+ (bill.getComplex() == null ? "" : bill.getComplex()
							.getCode())
					+ "/"
					+ (bill.getHsName() == null ? "" : bill.getHsName().trim())
					+ "/"
					+ (bill.getHsSpec() == null ? "" : bill.getHsSpec().trim())
					+ "/"
					+ (bill.getHsUnit() == null ? "" : bill.getHsUnit()
							.getName().trim())
					+ "/"
					+ (bill.getEmsNo() == null ? "" : bill.getEmsNo().trim());

			if (mapFFC.get(key) == null) {
				System.out.println("wss 发现没有匹配的 bill key:" + key);
				listError.add(bill);
			}
		}

		return listError;
	}

	/**
	 * 检查所有单据,返回有问题的单据
	 * 
	 * @author wss2010.09.24
	 */
	public List<BillDetail> checkAllBillDetail() {
		List<BillDetail> listError = new ArrayList<BillDetail>();

		Map<String, String> mapFFC = this.findFFCMap();

		List<BillDetail> listBill = new ArrayList<BillDetail>();
		String[] tables = { " BillDetailMateriel ", // 料件
				" BillDetailProduct ", // 成品
				" BillDetailRemainProduct ", // 残次品
				" BillDetailLeftoverMateriel ", // 边角料
				" BillDetailFixture " }; // 设备

		for (int i = 0; i < tables.length; i++) {
			String hsql = " select a from " + tables[i] + " a "
					+ " left join fetch a.billMaster "
					+ " left join fetch a.billMaster.billType "
					+ " where a.company.id = ? "
					+ " and a.billMaster.billType.code not in(?,?,?,?,?)";// 所有期初单不作检查
			System.out.println("wss hsql:" + hsql);
			listBill.addAll(casDao.find(hsql, new Object[] {
					CommonUtils.getCompany().getId(), "1001", "1002", "2001",
					"5002", "6003" }));

		}

		for (int i = 0; i < listBill.size(); i++) {
			BillDetail b = (BillDetail) listBill.get(i);

			// 半成品不作检查
			if ("半成品".equals(b.getNote())) {
				continue;
			}

			// key:料号 + 报关编码 + 报关名称 + 报关规格 + 报关单位 + 手册号
			String key = (b.getPtPart() == null ? "" : b.getPtPart().trim())
					+ "/"
					+ (b.getComplex() == null ? "" : b.getComplex().getCode())
					+ "/"
					+ (b.getHsName() == null ? "" : b.getHsName().trim())
					+ "/"
					+ (b.getHsSpec() == null ? "" : b.getHsSpec().trim())
					+ "/"
					+ (b.getHsUnit() == null ? "" : b.getHsUnit().getName()
							.trim()) + "/"
					+ (b.getEmsNo() == null ? "" : b.getEmsNo().trim());

			if (mapFFC.get(key) == null) {
				listError.add(b);
			}
		}

		return listError;
	}

	/**
	 * 对应关系映射
	 * 
	 * @param billCategory
	 * @return
	 * @author wss2010.09.24
	 */
	public Map<String, String> findFFCMap() {

		Map<String, String> mapFFC = new HashMap<String, String>();
		List<FactoryAndFactualCustomsRalation> ls = casDao
				.findFactoryAndFactualCustomsRalationByMaterielType(null);

		for (int i = 0; i < ls.size(); i++) {
			FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ls
					.get(i);
			Materiel m = ffc.getMateriel();
			if (ffc.getMateriel() == null) {
				continue;
			}
			String ptNo = ffc.getMateriel().getPtNo().trim();

			StatCusNameRelationHsn s = ffc.getStatCusNameRelationHsn();
			if (s == null) {
				continue;
			}
			String hsCode = s.getComplex() == null ? "" : s.getComplex()
					.getCode();

			String hsName = s.getCusName() == null ? "" : s.getCusName().trim();
			String hsSpec = s.getCusSpec() == null ? "" : s.getCusSpec().trim();
			String hsUnit = s.getCusUnit() == null ? "" : s.getCusUnit()
					.getName().trim();
			String emsNo = s.getEmsNo() == null ? "" : s.getEmsNo().trim();

			if (ptNo == null || "".equals(ptNo) || "".equals(hsCode)) {
				continue;
			}

			// key = 料号 + 报关编码 + 报关名称 + 报关规格 + 报关单位 + 手册号
			String key = ptNo + "/" + hsCode + "/" + hsName + "/" + hsSpec
					+ "/" + hsUnit + "/" + emsNo;
			Double unitConvert = ffc.getUnitConvert() == null ? 0.0 : ffc
					.getUnitConvert();

			mapFFC.put(key, key);
		}

		return mapFFC;

	}

	/**
	 * 料号+版本号 对应 bom
	 * 
	 * @return
	 * @author wss2010.09.30
	 */
	public List<Map<String, List<Object[]>>> findPtNoVersionAndBomMap() {
		// wss 新增残次品（成品部分）
		// String hsql = " select a.materiel.ptNo,v.version,v.id "
		// + " from MaterialBomMaster a ,"
		// + " MaterialBomVersion v "
		// + " left join fetch a.materiel "
		// + " left join fetch v.master"
		// + " where a.id = v.master.id "
		// + " group by a.materiel.ptNo,v.version,v.id ";

		String hsql = " select a.master.materiel.ptNo,a.version,a.id "
				+ " from MaterialBomVersion a "
				// + " left join fetch a.master "
				// + " left join fetch a.master.materiel"
				+ " group by a.master.materiel.ptNo,a.version,a.id ";

		List<Object[]> ls = this.casDao.find(hsql);
		System.out.println("wss hsql = " + hsql);
		Map<String, List<Object[]>> map = new HashMap<String, List<Object[]>>();
		Map<String, List<Object[]>> mapNoVersion = new HashMap<String, List<Object[]>>();

		for (int i = 0; i < ls.size(); i++) {
			Object[] objs = ls.get(i);
			String ptNo = objs[0] == null ? "" : (String) objs[0];
			Integer version = objs[1] == null ? 0 : (Integer) objs[1];
			String versionId = objs[2] == null ? "" : (String) objs[2];
			List lsBom = this.findMaterielBomDetail(versionId);

			String key = ptNo + "/" + version;

			map.put(key, lsBom);
			mapNoVersion.put(ptNo, lsBom);
		}
		List<Map<String, List<Object[]>>> list = new ArrayList<Map<String, List<Object[]>>>();
		list.add(map);
		list.add(mapNoVersion);

		return list;

	}

	/**
	 * 根据bom版本号id查找bom资料
	 * 
	 * @param versionId
	 * @author wss2010.09.30
	 */
	public List findMaterielBomDetail(String versionId) {

		String hsql = " select a.materiel.ptNo,a.unitWaste,a.unitUsed,a.waste "
				+ " from MaterialBomDetail a " + " left join a.materiel "
				+ " left join a.version " + " where a.version.id = ? ";

		return casDao.findNoCache(hsql, new Object[] { versionId });
	}

	/**
	 * 折算在产品期初单 (将半成品期初单)
	 * 
	 * @return 当前单据头
	 * @author wss 2010.10.08
	 */
	public BillMaster convertToInitFromSemiProductInit(BillMaster billMaster) {

		String billNo = billMaster.getNotice() == null ? "" : billMaster
				.getNotice().trim();

		// 1.删除原有的
		if ((!"".equals(billNo)) && isAlreadyConvertToInit(billNo)) {
			this.casDao.deleteInitBillMadeBySemiProduct(billNo);
		}

		// 2.获取单据体
		List<BillDetail> ls = this.casDao
				.getBillDetail4009ByBillMasterId(billMaster.getId());
		if (ls != null) {
			System.out.println("wss ls.size = " + ls.size());
		}

		// 3.根据bom折算在产品期初单
		List<BillDetail> list = this.converToInitBillWithBom(ls);

		// 4.生成在产品期初单
		billNo = this.casDao.getBillNo(1);
		System.out.println("wss billNo = " + billNo);
		this.makeInitBillFromSemiProduct(list, billNo);

		billMaster.setNotice(billNo);
		this.casDao.saveOrUpdate(billMaster);
		return billMaster;
	}

	/**
	 * 删除相应条件下的条件下的盘点平衡表
	 * 
	 * @param List
	 *            <CheckBalance>
	 * @author wss2010.10.18
	 */
	public void deleteAllCheckBalance(Date beginDate, Date endDate,
			String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes) {

		// 先删除折算真实的数据
		deleteAllCheckBalanceConvertMateriel(beginDate, endDate, kcType, ptNo,
				wlType, lsWareSetCodes);
		List parameters = new ArrayList();
		String hsql = " delete from CheckBalance a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);

		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);

		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and a.ptNo = ? ";
			parameters.add(ptNo);
		}
		if (wlType != null && !"".equals(wlType)) {
			hsql += " and a.ljCpMark = ? ";
			parameters.add(wlType);
		}

		// 仓库
		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and a.wareSet.id in ( select w.id from WareSet w where 2>1 ";
				hsql += " and w.code = ? ";
			} else {
				hsql += " or  w.code = ? ";
			}

			parameters.add(wareSetCode);
			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		System.out.println("wss hsql = " + hsql);
		this.casDao.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 删除相应条件下的条件下的盘点平衡表
	 * 
	 * @param List
	 *            <CheckBalanceConvertMateriel>
	 * @author wss2010.10.18
	 */
	public void deleteAllCheckBalanceConvertMateriel(Date beginDate,
			Date endDate, String kcType, String ptNo, String wlType,
			List<String> lsWareSetCodes) {
		List parameters = new ArrayList();
		String hsql = " delete from CheckBalanceConvertMateriel a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);

		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);

		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and a.ptNo = ? ";
			parameters.add(ptNo);
		}
		if (wlType != null && !"".equals(wlType)) {
			hsql += " and a.ljCpMark = ? ";
			parameters.add(wlType);
		}

		// 仓库
		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and a.wareSet.id in ( select w.id from WareSet w where 2>1 ";
				hsql += " and w.code = ? ";
			} else {
				hsql += " or  w.code = ? ";
			}

			parameters.add(wareSetCode);
			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		System.out.println("wss hsql = " + hsql);
		this.casDao.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 分页折算CheckBalance报关数量（获取报关资料）
	 * 
	 * @return
	 * @author wss2010.10.15只是用来实验的,lyh2012.12.07进行修改
	 */
	public void fillCheckBalanceHsInfoPerPage(List list) {

		List listAdd = new ArrayList();
		List listSubAdd = new ArrayList();

		CheckBalance item = null;
		Map mapKey = new HashMap();

		for (int i = 0; i < list.size(); i++) {
			item = (CheckBalance) list.get(i);
			mapKey.put(
					item.getLjCpMark()
							+ "@"
							+ item.getPtNo()
							+ "@"
							+ item.getKcType()
							+ "@"
							+ (item.getWareSet() == null ? "" : item
									.getWareSet().getCode()), item);// key=料件/成品标记+料号+库存类别+仓库名称
		}

		Set s = mapKey.entrySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			item = (CheckBalance) ((Map.Entry) it.next()).getValue();
			String valueKey = item.getLjCpMark();
			String valuePtNo = item.getPtNo();
			if ("2".equals(valueKey)) { // 半成品
				// long h = System.currentTimeMillis();
				Materiel m = this.casDao.findMaterielByPtNo(valuePtNo);
				// long j = System.currentTimeMillis();
				// System.out.print("====j-h==" + (j - h) / 1000 + "\n");
				if (m != null) {
					item.setPtName(m.getFactoryName());
					item.setPtSpec(m.getFactorySpec());
					item.setPtUnit(m.getCalUnit());// wss新加
					item.setPtUnitName(m.getCalUnit() == null ? "" : m
							.getCalUnit().getName());
					item.setComplex(m.getComplex());// 新加

					item.setName(m.getPtName());
					item.setSpec(m.getPtSpec());
					item.setHsUnit(m.getPtUnit());
					item.setUnitName(m.getPtUnit() == null ? "" : m.getPtUnit()
							.getName());

					item.setUnitConvert(m.getUnitConvert() == null ? 0.0 : m
							.getUnitConvert());
					item.setHsAmount(IsNullDouble(item.getCheckAmount())
							* IsNullDouble(item.getUnitConvert()));
					item.setNote("");

				} else {
					item.setNote("报关常用物料中不存在此料号");
				}

			}
			if ("0".equals(valueKey)) {// 料件
				// 1.报关商品名称,报关商品单位存在，按折算报关数量，折算报关比计算
				if (item.getName() != null && !"".equals(item.getName())
						&& item.getHsUnit() != null) {

				} else {// 2.按工厂与实际报关对应表计算
					FactoryAndFactualCustomsRalation srh = this.casDao
							.findFactoryAndFactualCustomsRalationByPtNo(valuePtNo);
					if (srh != null && srh.getStatCusNameRelationHsn() != null) {
						if (srh.getMateriel() != null) {
							item.setPtName(srh.getMateriel().getFactoryName());
							item.setPtSpec(srh.getMateriel().getFactorySpec());
							item.setPtUnit(srh.getMateriel().getCalUnit());
						}
						item.setComplex(srh.getStatCusNameRelationHsn()
								.getComplex());
						item.setName(srh.getStatCusNameRelationHsn()
								.getCusName());
						item.setSpec(srh.getStatCusNameRelationHsn()
								.getCusSpec());
						item.setHsUnit(srh.getStatCusNameRelationHsn()
								.getCusUnit());
						item.setUnitConvert(srh.getUnitConvert() == null ? 0.0
								: srh.getUnitConvert());
						item.setHsAmount(IsNullDouble(item.getCheckAmount())
								* IsNullDouble(item.getUnitConvert()));
						item.setNote("");
					} else {
						item.setNote("此料号还没有做对应关系");
					}
				}
			} else {
				FactoryAndFactualCustomsRalation srh = this.casDao
						.findFactoryAndFactualCustomsRalationByPtNo(valuePtNo);
				if (srh != null && srh.getStatCusNameRelationHsn() != null) {
					if (srh.getMateriel() != null) {
						item.setPtName(srh.getMateriel().getFactoryName());
						item.setPtSpec(srh.getMateriel().getFactorySpec());
						item.setPtUnit(srh.getMateriel().getCalUnit());
					}
					item.setComplex(srh.getStatCusNameRelationHsn()
							.getComplex());
					item.setName(srh.getStatCusNameRelationHsn().getCusName());
					item.setSpec(srh.getStatCusNameRelationHsn().getCusSpec());
					item.setHsUnit(srh.getStatCusNameRelationHsn().getCusUnit());
					item.setUnitConvert(srh.getUnitConvert() == null ? 0.0
							: srh.getUnitConvert());
					item.setHsAmount(IsNullDouble(item.getCheckAmount())
							* IsNullDouble(item.getUnitConvert()));
					item.setNote("");
				} else {
					item.setNote("此料号还没有做对应关系");
				}
			}

			listAdd.add(item);
			// 如果是料件还需要复制到CheckBalanceConvertMateriel
			if (!"0".equals(valueKey)) {// 0代表料件
				continue;
			} else {
				// ////// 复制到 CheckBalanceConvertMateriel 平衡表折料记录//////

				CheckBalanceConvertMateriel cb = new CheckBalanceConvertMateriel();

				List alist = new ArrayList();
				alist.add("fatherCheckBalance");
				try {
					BswBeansUtils.copyProperties(cb, item, alist);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cb.setLjCpMark("0");// 物料类型
				cb.setId(null);
				cb.setFatherCheckBalance(item);// 父盘点

				listSubAdd.add(cb);
			}
		}
		long c = System.currentTimeMillis();
		System.out.println(listAdd.size());

		// 批量保存盘点平衡表导入数据
		if (!listAdd.isEmpty()) {
			this.casDao.batchSaveCheckBalance(listAdd);
		}

		// 批量 平衡表折料记录
		if (!listSubAdd.isEmpty()) {
			this.casDao.batchSaveOrUpdate(listSubAdd);
		}

		long d = System.currentTimeMillis();
		System.out.print("====d-c==" + (d - c) / 1000 + "\n");

	}

	/**
	 * 分页折BOM CheckBalance
	 * 
	 * @return
	 * @author wss2010.10.20只是用来实验的
	 */
	public void convertCheckBalanceToBomPerPage(int index, int length,
			Request request, Date beginDate, Date endDate, String kcType,
			String ptNo, String wlType, List<String> lsWareSetCodes) {
		List list = findCheckBalance(index, length, beginDate, endDate, kcType,
				ptNo, wlType, lsWareSetCodes);
		System.out.println("list.size =" + list.size());
		CheckBalance item;
		for (int i = 0; i < list.size(); i++) {
			item = (CheckBalance) list.get(i);
			if ("0".equals(item.getLjCpMark())) {
				continue;
			}
			System.out.println("wss 正在进行的料号是" + item.getPtNo());
			System.out.println("wss ljcpMark = " + item.getLjCpMark());
			convertCheckBalanceToBomOneByOne(item);

		}
	}

	/**
	 * 获取相应条件下的 CheckBalanceConvertMateriel 数量
	 * 
	 * @param request
	 * @param beginDate
	 * @param endDate
	 * @param fatherKcType
	 * @param fatherPtNo
	 * @param fatherWlType
	 * @return
	 * @author wss2010.10.20
	 */
	public int getBalanceCheckConvertMaterielCount(Date beginDate,
			Date endDate, String fatherKcType, String fatherPtNo,
			String fatherWlType, List<String> lsWareSetCodes) {
		List parameters = new ArrayList();
		String hsql = "select count(a.id) from CheckBalanceConvertMateriel a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}

		// 父盘点信息
		if (fatherKcType != null && !"".equals(fatherKcType)) {
			hsql += " and a.fatherCheckBalance.kcType = ? ";
			parameters.add(fatherKcType);
		}
		if (fatherPtNo != null && !"".equals(fatherPtNo)) {
			hsql += " and a.fatherCheckBalance.ptNo = ?";
			parameters.add(fatherPtNo);
		}
		if (fatherWlType != null && !"".equals(fatherWlType)) {
			hsql += " and a.fatherCheckBalance.ljCpMark = ? ";
			parameters.add(fatherWlType);
		} else {
			hsql += " and (a.fatherCheckBalance.ljCpMark = ? ";
			hsql += " or a.fatherCheckBalance.ljCpMark = ? )";
			parameters.add("1");
			parameters.add("2");
		}

		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and ( a.wareSet.code = ? ";
			} else {
				hsql += " or  a.wareSet.code = ? ";
			}

			parameters.add(wareSetCode);
			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		System.out.println(" wss hsql = " + hsql);
		List list = casDao.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 删除通过折BOM折算的数据
	 * 
	 * @param request
	 * @param beginDate
	 * @param endDate
	 * @param fatherKcType
	 * @param fatherPtNo
	 * @param fatherWlType
	 * @author wss2010.10.20
	 */
	public void deleteCheckBalanceConvertM(Date beginDate, Date endDate,
			String fatherKcType, String fatherPtNo, String fatherWlType,
			List<String> lsWareSetCodes) {
		List parameters = new ArrayList();
		String hsql = " delete from CheckBalanceConvertMateriel a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}

		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}

		hsql += " and a.fatherCheckBalance.id in (select id from CheckBalance c where 2>1 ";

		if (fatherKcType != null && !"".equals(fatherKcType)) {
			hsql += " and  c.kcType = ? ";
			parameters.add(fatherKcType);
		}

		if (fatherPtNo != null && !"".equals(fatherPtNo)) {
			hsql += " and  c.ptNo = ? ";
			parameters.add(fatherPtNo);
		}

		if (fatherWlType != null && !"".equals(fatherWlType)) {
			hsql += " and  c.ljCpMark = ? ";
			parameters.add(fatherWlType);
		} else {
			hsql += " and  c.ljCpMark != ? ";
			parameters.add("0");
		}
		hsql += ")";

		for (int j = 0; j < lsWareSetCodes.size(); j++) {
			String wareSetCode = lsWareSetCodes.get(j);
			if (j == 0) {
				hsql += " and a.wareSet.id in ( select w.id from WareSet w where 2>1 ";
				hsql += " and w.code = ? ";
			} else {
				hsql += " or  w.code = ? ";
			}

			parameters.add(wareSetCode);
			if (j == lsWareSetCodes.size() - 1) {
				hsql += " )";
			}
		}

		System.out.println("wss hsql = " + hsql);
		this.casDao.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 查找在对应关系中有用到的报关资料
	 * 
	 * @param materielType
	 * @return
	 * @author wss2010.10.20
	 */
	public List findStatCusNameRelationHsnInUsed(String materielType) {
		List ls = new ArrayList();
		ls = casDao.findStatCusNameRelationHsnInUsed(materielType);
		System.out.println("wss list.size = " + ls.size());
		return ls;
	}

	/**
	 * 根据单据类型查找单据体
	 * 
	 * @param billType
	 *            单据类型
	 * @return
	 * @author wss
	 */
	public List findBillDetailByBillType(String billType) {
		return this.casDao.find(" select a from BillDetailMateriel a "
				+ " where a.billMaster.billType.code = ? "
				+ " and a.company.id = ? ", new Object[] { billType,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示 国内购买 进出口商品信息(统计上期结存)
	 * 
	 * @param request发送请求
	 * @param conditions查询条件
	 * @param beginDate
	 *            开始日期
	 * @return 按照查询条件查出进出口商品信息
	 * @author wss2010.11.23改写
	 */
	public List findImpExpInfosOfInnerBuy(List<Condition> conditions,
			Date beginDate) {
		Map<String, String> mapRsAmountJY = new HashMap<String, String>();
		List<ImportExportInfo> result = this.calcImpExpInfosOfInnerBuy(
				conditions, beginDate, mapRsAmountJY);

		ArrayList<ImportExportInfo> list = new ArrayList<ImportExportInfo>();

		String oldKey = "";
		boolean isOnce = true;

		// 从尾到头遍历
		for (int i = result.size() - 1; i >= 0; i--) {
			ImportExportInfo importExportInfo = result.get(i);

			String ptNo = importExportInfo.getPtPart();
			String ptName = importExportInfo.getPtName();
			String ptSpec = importExportInfo.getPtSpec();

			String key = ptNo + "/" + ptName + "/" + ptSpec;

			Date currentDate = importExportInfo.getDate();

			// init 第一个 oldKey 的值
			if (i == result.size() - 1) {
				// 如果在开始日期后边
				if (!currentDate.before(beginDate)) {
					list.add(0, importExportInfo);
				}
				// 日期在前面
				else {
					// 加入上期加存在此
					addTotalFrontImportExportInfo(beginDate, importExportInfo,
							mapRsAmountJY, key);
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
					addTotalFrontImportExportInfo(beginDate, importExportInfo,
							mapRsAmountJY, key);
					list.add(0, importExportInfo);
					isOnce = false;
				}
			} else if (key.equalsIgnoreCase(oldKey)
					&& !currentDate.before(beginDate)) {
				list.add(0, importExportInfo);
			} else if (key.equalsIgnoreCase(oldKey)
					&& currentDate.before(beginDate) && isOnce) {
				addTotalFrontImportExportInfo(beginDate, importExportInfo,
						mapRsAmountJY, key);
				list.add(0, importExportInfo);
				isOnce = false;
			}
			// 获得最后一个
			oldKey = key;
		}
		mapRsAmountJY.clear();
		return list;
	}

	/**
	 * 显示 国内购买上期结余 进出信息
	 * 
	 * @param request发送请求
	 * @param conditions查询条件
	 * @param beginDate
	 *            开始日期
	 * @return 按照查询条件查出进出口商品信息
	 * @author wss2010.11.23改写
	 */
	public List calcImpExpInfosOfInnerBuy(List<Condition> conditions,
			Date beginDate, Map<String, String> mapAmountJY) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

		System.out.println("billDetailTableName=" + billDetailTableName);
		if (billDetailTableName == null) {
			return result;
		}
		boolean isPtName = false;// 是否有按工厂名称查询
		boolean isHsName = false;// 是否有按报关名称查询
		boolean isBillType = false;
		if (conditions != null) {
			for (Condition item : conditions) {
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

		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1002", ")")); // 不统计1002在产品起初单
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1003", ")")); // 不统计1003直接进口
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1004", ")")); // 不统计1004结转进口
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1015", ")")); // 不统计1015已收货未结转期初单
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1016", ")")); // 不统计1016已结转未收货期初单

		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1102", ")")); // 不统计1102料件退换
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1103", ")")); // 不统计1103料件复出
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1106", ")")); // 不统计1106结转料件退货单
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1114", ")")); // 不统计1114受托加工料件出库

		String orderBy = "";
		String leftOuter = "";
		String groupBy = "";

		if (isHsName || isPtName) {
			orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
		} else {
			orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
		}

		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";

		List billDetails = casDao.commonSearch("", billDetailTableName,
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		double rsPtAmountJY = 0;// 上期结余
		double rsHsAmountJY = 0;// 上期结余海关
		String oldKey = "";
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String typeCode = billDetail.getBillMaster() == null ? ""
					: billDetail.getBillMaster().getBillType() == null ? ""
							: billDetail.getBillMaster().getBillType()
									.getCode();
			String wareProperty = billDetail.getWareSet() == null ? "0"
					: billDetail.getWareSet().getWareProperty() == null ? "0"
							: billDetail.getWareSet().getWareProperty();

			// 1005国内购买不区分仓库属性，其它类型的只抓仓库为非保税的
			if (!"1005".equals(typeCode) && "0".equals(wareProperty)) {
				continue;
			}

			String ptNo = billDetail.getPtPart();
			String ptName = billDetail.getPtName();
			String ptSpec = billDetail.getPtSpec();

			// wss2010.08.25:key应该改为 料号+名称+规格
			String key = ptNo + "/" + ptName + "/" + ptSpec;

			ImportExportInfo importExportInfo = new ImportExportInfo(billDetail);
			// 当排序后另外的物料的结存数量应该初始化为0,但要没有按工厂或报关名称进行查询
			if (!key.equalsIgnoreCase(oldKey)) {
				if (!isHsName && !isPtName) {
					rsPtAmount = 0;
					rsHsAmount = 0;
				} else {
					rsPtAmountJY = 0;
					rsHsAmountJY = 0;
				}
			}

			if (importExportInfo.getBillType().getWareType().intValue() == 1) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
				rsPtAmountJY += importExportInfo.getImpPtAmount();
				rsHsAmountJY += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
				rsPtAmountJY -= importExportInfo.getExpPtAmount();
				rsHsAmountJY -= importExportInfo.getExpHsAmount();
			}

			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);

			result.add(importExportInfo);
			if (importExportInfo.getDate().before(beginDate)) {
				if (!isHsName && !isPtName) {
					mapAmountJY.put(key, rsPtAmount + "/" + rsHsAmount);
				} else {
					mapAmountJY.put(key, rsPtAmountJY + "/" + rsHsAmountJY);
				}
			}
			// 获得最后一个
			oldKey = key;
		}
		return result;

	}

	/**
	 * 显示 国内购买 进出信息
	 * 
	 * @param request发送请求
	 * @param conditions查询条件
	 * @param beginDate
	 *            开始日期
	 * @return 按照查询条件查出进出口商品信息
	 * @author wss2010.11.23改写
	 */
	public List findImpExpInfosOfInnerBuy(List<Condition> conditions) {
		List<ImportExportInfo> result = new ArrayList<ImportExportInfo>();
		String billDetailTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

		System.out.println("billDetailTableName=" + billDetailTableName);
		if (billDetailTableName == null) {
			return result;
		}
		boolean isPtName = false;// 是否有按工厂名称查询
		boolean isHsName = false;// 是否有按报关名称查询
		boolean isBillType = false;
		if (conditions != null) {
			for (Condition item : conditions) {
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

		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1002", ")")); // 不统计1002在产品起初单
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1003", ")")); // 不统计1003直接进口
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1004", ")")); // 不统计1004结转进口
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1015", ")")); // 不统计1015已收货未结转期初单
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1016", ")")); // 不统计1016已结转未收货期初单

		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1102", ")")); // 不统计1102料件退换
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1103", ")")); // 不统计1103料件复出
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1106", ")")); // 不统计1106结转料件退货单
		conditions.add(new Condition("and", "(", "billMaster.billType.code",
				"!=", "1114", ")")); // 不统计1114受托加工料件出库

		String orderBy = "";
		String leftOuter = "";
		String groupBy = "";

		if (isHsName || isPtName) {
			orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
		} else {
			orderBy = " order by a.ptPart ASC,a.billMaster.validDate ASC,a.billMaster.billType.code ASC,a.wareSet.code ASC ";
		}

		leftOuter = " left join fetch a.billMaster left join fetch a.wareSet  ";

		List billDetails = casDao.commonSearch("", billDetailTableName,
				conditions, "", leftOuter, orderBy);

		double rsPtAmount = 0;
		double rsHsAmount = 0;
		String oldKey = "";
		for (int i = 0; i < billDetails.size(); i++) {
			BillDetail billDetail = (BillDetail) billDetails.get(i);
			String typeCode = billDetail.getBillMaster() == null ? ""
					: billDetail.getBillMaster().getBillType() == null ? ""
							: billDetail.getBillMaster().getBillType()
									.getCode();
			String wareProperty = billDetail.getWareSet() == null ? "0"
					: billDetail.getWareSet().getWareProperty() == null ? "0"
							: billDetail.getWareSet().getWareProperty();

			// 1005国内购买不区分仓库属性，其它类型的只抓仓库为非保税的
			if (!"1005".equals(typeCode) && "0".equals(wareProperty)) {
				continue;
			}

			String ptNo = billDetail.getPtPart();
			String ptName = billDetail.getPtName();
			String ptSpec = billDetail.getPtSpec();

			// wss2010.08.25:key应该改为 料号+名称+规格
			String key = ptNo + "/" + ptName + "/" + ptSpec;

			// 当排序后另外的物料的结存数量应该初始化为0,但要没有按工厂或报关名称进行查询
			if (!key.equalsIgnoreCase(oldKey) && !isHsName && !isPtName) {
				rsPtAmount = 0;
				rsHsAmount = 0;
			}
			ImportExportInfo importExportInfo = new ImportExportInfo(billDetail);
			if (importExportInfo.getBillType().getWareType().intValue() == 1) {
				rsPtAmount += importExportInfo.getImpPtAmount();
				rsHsAmount += importExportInfo.getImpHsAmount();
			} else {
				rsPtAmount -= importExportInfo.getExpPtAmount();
				rsHsAmount -= importExportInfo.getExpHsAmount();
			}

			importExportInfo.setRsPtAmount(rsPtAmount);
			importExportInfo.setRsHsAmount(rsHsAmount);

			result.add(importExportInfo);

			// 获得最后一个
			oldKey = key;
			System.out.println("oldKey= :" + oldKey);
		}
		return result;

	}

	/**
	 * 分页查询CheckBalanceOfCustom
	 * 
	 * @return
	 * @author hcl 2011-03-14
	 */
	public List findCheckBalanceOfCustom(int index, int length, Date beginDate,
			Date endDate, String kcType, String name, String spec,
			String unitName) {

		List parameters = new ArrayList();
		String hsql = "select a from CheckBalanceOfCustom a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name = ?";
			parameters.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql += " and a.spec = ? ";
			parameters.add(spec);
		}
		if (unitName != null && !"".equals(unitName)) {
			hsql += " and a.unitName = ? ";
			parameters.add(unitName);
		}

		System.out.println(" hcl hsql = " + hsql);
		return casDao.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 查询CheckBalanceOfCustom数量
	 * 
	 * @author hcl
	 */
	public int findCheckBalanceCountOfCustom(Date beginDate, Date endDate,
			String kcType, String name, String spec, String unitName) {

		List parameters = new ArrayList();
		String hsql = "select count(a.id) from CheckBalanceOfCustom a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);
		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name = ?";
			parameters.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql += " and a.spec = ? ";
			parameters.add(spec);
		}
		if (unitName != null && !"".equals(unitName)) {
			hsql += " and a.unitName = ? ";
			parameters.add(unitName);
		}
		System.out.println(" hcl hsql = " + hsql);
		List list = casDao.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 删除盘点平衡表记录
	 * 
	 * @param List
	 *            <CheckBalance>
	 * @author hcl
	 */
	public void deleteCheckBalanceOfCustom(List<CheckBalanceOfCustom> ls) {
		for (int i = 0; i < ls.size(); i++) {
			CheckBalanceOfCustom cb = (CheckBalanceOfCustom) ls.get(i);
			this.deleteCheckBalanceOfCustom(cb);
		}
	}

	/**
	 * 删除盘点平衡表一条记录
	 * 
	 * @param checkBalance
	 */
	public void deleteCheckBalanceOfCustom(CheckBalanceOfCustom c) {
		casDao.delete(c);
	}

	/**
	 * 删除相应条件下的条件下的盘点平衡表
	 * 
	 * @param List
	 *            <CheckBalance>
	 * @author wss2010.10.18
	 */
	public void deleteAllCheckBalanceOfCustom(Date beginDate, Date endDate,
			String kcType, String name, String spec, String unitName) {

		List parameters = new ArrayList();
		String hsql = " delete from CheckBalanceOfCustom a where 2>1 ";
		if (beginDate != null) {
			hsql += " and a.checkDate >= ? ";
			parameters.add(beginDate);

		}
		if (endDate != null) {
			hsql += " and a.checkDate <= ? ";
			parameters.add(endDate);

		}
		if (kcType != null && !"".equals(kcType)) {
			hsql += " and a.kcType = ? ";
			parameters.add(kcType);
		}
		if (name != null && !"".equals(name)) {
			hsql += " and a.name = ? ";
			parameters.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql += " and a.spec = ? ";
			parameters.add(spec);
		}
		if (unitName != null && !"".equals(unitName)) {
			hsql += " and a.unitName = ? ";
			parameters.add(unitName);
		}

		System.out.println("hcl hsql = " + hsql);
		this.casDao.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 获取三种报关模式的料件Map（名称+规格+单位）
	 * 
	 * @return
	 */
	public HashMap getCheckMap() {
		String hsql = "select   distinct a.name+'/'+a.spec+'/'+a.unit.name from ContractImg a ";
		List list = casDao.find(hsql);
		hsql = " select distinct a.name+'/'+a.spec+'/'+a.unit.name from DzscEmsImgBill a";
		list.addAll(casDao.find(hsql));
		hsql = " select distinct a.name+'/'+a.spec+'/'+a.unit.name from EmsHeadH2kImg a";
		list.addAll(casDao.find(hsql));
		HashMap resultMap = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			String key = (String) list.get(i);
			resultMap.put(key, key);
		}
		return resultMap;

	}

	/**
	 * 查找StatCusNameRelationHsn的名称和规格组成的key
	 * 
	 * @param materielType
	 *            物料编码 materielType 取值：
	 * 
	 *            成品 FINISHED_PRODUCT="0";
	 * 
	 *            半成品 SEMI_FINISHED_PRODUCT="1";
	 * 
	 *            材料--主料 MATERIEL="2";
	 * 
	 * @return
	 */
	public List findStatCusNameRelationHsnNameAndSpec(String materielType) {
		List<TempObject> resultList = new ArrayList<TempObject>();
		List<Object[]> list = casDao
				.findStatCusNameRelationHsnNameAndSpec(materielType);
		TempObject temp = null;
		for (int i = 0; i < list.size(); i++) {
			temp = new TempObject();
			temp.setObject(list.get(i)[0]);
			temp.setObject1(list.get(i)[1]);
			resultList.add(temp);
		}
		return resultList;
	}

}
