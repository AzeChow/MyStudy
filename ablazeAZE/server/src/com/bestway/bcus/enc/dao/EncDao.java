package com.bestway.bcus.enc.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.enc.entity.ExpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.bcus.enc.entity.InputInExRequestBillOrder;
import com.bestway.bcus.enc.entity.MakeApplyToCustoms;
import com.bestway.bcus.enc.entity.MakeImpExpRequestBill;
import com.bestway.bcus.enc.entity.MakeListToCustoms;
import com.bestway.bcus.enc.entity.XinpuReport;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.BcusParameterSet;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SendState;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

@SuppressWarnings("unchecked")
public class EncDao extends BaseEncDao {
	/**
	 * 查找所有进出口申请单AtcMergeBeforeComInfo
	 * 
	 * @return 进出口申请单内容
	 */
	public List findImpExpRequestBill() {
		return this.find(
				"select a from ImpExpRequestBill as a where a.company.id=?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查找进出口申请单
	 * 
	 * @return 进出口申请单内容
	 */
	public ImpExpRequestBill getImpExpRequestBill(String billNo) {
		List list = this
				.find("select a from ImpExpRequestBill as a where a.company.id=? and a.billNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), billNo });
		if (list != null && list.size() > 0) {
			return (ImpExpRequestBill) list.get(0);
		}

		return null;
	}

	/**
	 * 显示当前公司
	 * 
	 * @return 当前公司
	 */
	public Company findCurrCompany() {
		List list = this.find("from Company a where a.id = ?",
				new Object[] { CommonUtils.getCompany().getId() }); // 显示
		if (list != null && list.size() > 0) {
			return (Company) list.get(0);
		}
		return null;
	}

	/**
	 * 查询BCUS参数
	 * 
	 * @param parameter
	 */
	public BcusParameterSet findBcusParameterSet() {
		List list = this.find(
				"select a from BcusParameterSet a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BcusParameterSet) list.get(0);
		} else {
			BcusParameterSet parameterSet = new BcusParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 查找所有进出口申请单来自进出口申请单类型
	 * 
	 * @param type
	 *            单据类型
	 * @param isCustomsBill
	 *            已全转报关清单或全转已转报关单（当全部转完为True）
	 * @param billNo
	 *            单据号
	 * @param beginDate
	 *            生效日期
	 * @param endDate
	 *            有效期结束日期
	 * @param customer
	 *            客户供应商
	 * @return 与条件匹配的所有进出口申请单
	 */
	public List findImpExpRequestBillByType(int type, Boolean isCustomsBill,
			String billNo, Date beginDate, Date endDate, String customer) {
		String hssql = "select a from ImpExpRequestBill as a where a.billType = ? and a.company.id=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(Integer.valueOf(type));
		parameters.add(CommonUtils.getCompany().getId());
		if (isCustomsBill != null) {
			hssql += " and a.isCustomsBill=? ";
			parameters.add(isCustomsBill);
		}
		if (billNo != null && !billNo.equals("")) {
			hssql += " and a.billNo=? ";
			parameters.add(billNo);
		}
		if (beginDate != null) {
			hssql += " and (a.beginAvailability>=?  or a.beginAvailability is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hssql += " and (a.beginAvailability<=?  or a.beginAvailability is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (customer != null && !customer.equals("")) {
			hssql += " and a.scmCoc.id=? ";
			parameters.add(customer);
		}
		return this.find(hssql, parameters.toArray());

	}

	/**
	 * 查找进出口申请单来自进出口申请单类型
	 * 
	 * @param billNo
	 *            单据号
	 */
	public List findImpExpRequestBillByBillNo(String billNo, Integer billType) {
		String hssql = "select a from ImpExpRequestBill a"
				+ " where a.company.id=? and a.billNo=?  and a.billType=?";

		return this.find(hssql, new Object[] {
				CommonUtils.getCompany().getId(), billNo, billType });

	}

	/**
	 * 查找进出口申请单来自id
	 * 
	 * @param id
	 */
	public ImpExpRequestBill findImpExpRequestBillById(String id) {
		String hssql = "select a from ImpExpRequestBill a"
				+ " where a.company.id=? and a.id=?";

		return (ImpExpRequestBill) this.createQuery(hssql,
				new Object[] { CommonUtils.getCompany().getId(), id })
				.uniqueResult();

	}

	/**
	 * 根据已转清单号码查找进出口申请单
	 * 
	 * @param allBillNo
	 * @param billType
	 * @return
	 */
	public List findImpExpRequestBillByAllBillNo(String allBillNo,
			Integer billType) {
		String hssql = "select a from ImpExpRequestBill a"
				+ " where a.company.id=? and a.allBillNo like ?  and a.billType=?";
		if (allBillNo != null) {
			allBillNo = "%" + allBillNo + "%";
		} else {
			allBillNo = "%";
		}
		return this.find(hssql, new Object[] {
				CommonUtils.getCompany().getId(), allBillNo, billType });

	}

	/**
	 * 根据报关单表头ImpExpRequestBill查找清单中商品信息
	 * 
	 * @param allBillNo
	 * @param billType
	 * @return
	 */
	public List findTempImpExpCommodityInfoByBillListId(
			ImpExpRequestBill impExpRequestBill, Integer billType) {
		String hssql = "select a from ImpExpCommodityInfo a"
				+ " where a.company.id=? and a.impExpRequestBill.allBillNo like ?  and a.impExpRequestBill.billType=?";
		String allBillNo = impExpRequestBill.getAllBillNo();
		if (allBillNo != null) {
			allBillNo = "%" + allBillNo + "%";
		} else {
			allBillNo = "%";
		}
		return this.find(hssql, new Object[] {
				CommonUtils.getCompany().getId(), allBillNo, billType });

	}

	// /**
	// * 根据进出口申请单号码和客户/供应商 查找进出口申请单个数
	// *
	// * @param billNo
	// * 单据号
	// */
	// public int findImpExpRequestBillCountByBillNo(String billNo, ScmCoc
	// scmCoc) {
	// String hssql = "select count(a.id) from ImpExpRequestBill a"
	// + " where a.company.id=? and a.billNo=? and a.scmCoc.id=?";
	// List list = this.find(hssql, new Object[] {
	// CommonUtils.getCompany().getId(), billNo, scmCoc.getId() });
	// if (list.isEmpty()) {
	// return 0;
	// } else if (list.get(0) != null) {
	// return Integer.parseInt(list.get(0).toString().trim());
	// }
	// return 0;
	// }

	/**
	 * 根据进出口申请单号码和客户/供应商 查找进出口申请单个数
	 * 
	 * @param billNo
	 *            单据号
	 */
	public int findImpExpRequestBillCountByBillNo(String billNo, ScmCoc scmCoc) {
		List para = new ArrayList();
		String hssql = "select count(a.id) from ImpExpRequestBill a"
				+ " where a.company.id=? and a.billNo=?  ";
		para.add(CommonUtils.getCompany().getId());
		para.add(billNo);
		if (scmCoc != null) {
			hssql += "   and a.scmCoc.id=? ";
			para.add(scmCoc.getId());
		}
		List list = this.find(hssql, para.toArray());
		if (list.isEmpty()) {
			return 0;
		} else if (list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString().trim());
		}
		return 0;
	}

	/**
	 * 查找没有报关的归并后商品信息来自申请单
	 * 
	 * @param billList
	 *            申请单
	 * @return 已转报关单的归并后商品信息
	 */
	public List findAtcMergeAfterComInfoNotToCustoms(
			ApplyToCustomsBillList billList, Boolean isTransferCustomsBill) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from AtcMergeAfterComInfo a where a.billList.id = ? ";
		parameters.add(billList.getId());
		if (isTransferCustomsBill != null) {
			hsql += " and a.isTransferCustomsBill = ?";
			parameters.add(isTransferCustomsBill);
		}
		return this.find(hsql, parameters.toArray());
	}

	public List findAtcMergeAfterComInfoByBillNo(String listNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from AtcMergeAfterComInfo a where a.billList.listNo = ? ";
		parameters.add(listNo);
		return this.find(hsql, parameters.toArray());
	}

	public List findAtcMergeAfterComInfoByParents(List listNo) {
		List result = new ArrayList();
		for (int k = 0; k < listNo.size(); k++) {
			ApplyToCustomsBillList ap = (ApplyToCustomsBillList) listNo.get(k);
			List<Object> parameters = new ArrayList<Object>();
			String hsql = "select a from AtcMergeAfterComInfo a where a.billList.id = ? ";
			// + " and ( a.isTransferCustomsBill is null or
			// a.isTransferCustomsBill= ? )";
			parameters.add(ap.getId());
			// parameters.add(new Boolean(false));
			result.addAll(this.find(hsql, parameters.toArray()));
		}
		return result;
	}

	private boolean getIsMergerSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsSEND,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询归并后商品信息
	 * 
	 * @return 归并后的商品信息
	 */
	public List findAtcMergeAfterComInfoNot() {
		return this.find("select a from AtcMergeAfterComInfo a");
	}

	/**
	 * 查询所有申请单
	 * 
	 * @param state
	 *            清单状态
	 * @param type
	 *            进出口类型
	 * @param flat
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 与条件匹配的申请单
	 */
	public List findApplyBillList(Integer state, Integer type, Integer flat,
			Date beginDate, Date endDate) {
		String hssql = "select a from ApplyToCustomsBillList a where a.impExpFlag=? and a.company.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(Integer.valueOf(flat));
		parameters.add(CommonUtils.getCompany().getId());
		if (state != null) {
			hssql += " and a.listState=? ";
			parameters.add(state);
		}
		if (type != null) {
			hssql += "and a.impExpType=? ";
			parameters.add(type);
		}
		if (beginDate != null) {
			hssql += " and (a.createdDate>=?  or a.createdDate is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hssql += " and (a.createdDate<=?  or a.createdDate is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hssql, parameters.toArray());
	}

	/**
	 * 查询申请单
	 * 
	 * @param billNo
	 *            清单状态
	 * @param flat
	 *            进出口标志
	 * @return
	 */
	public ApplyToCustomsBillList getApplyBillList(String billNo, int flag) {
		String hql = "select a from ApplyToCustomsBillList a where a.company.id = ? and a.listNo = ?";
		List list = find(hql, new Object[] { CommonUtils.getCompany().getId(),
				billNo });
		if (list != null && list.size() > 0) {
			return (ApplyToCustomsBillList) list.get(0);
		}
		return null;
	}

	public List findApplyBillListeffectstate(boolean state, Integer type,
			Integer flat, Date beginDate, Date endDate) {
		String hssql = "select a from ApplyToCustomsBillList a where a.impExpFlag=? and a.company.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(Integer.valueOf(flat));
		parameters.add(CommonUtils.getCompany().getId());
		// if (state != null) {
		hssql += " and a.effectstate=? ";
		parameters.add(state);
		// }
		if (type != null) {
			hssql += "and a.impExpType=? ";
			parameters.add(type);
		}
		if (beginDate != null) {
			hssql += " and (a.createdDate>=?  or a.createdDate is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hssql += " and (a.createdDate<=?  or a.createdDate is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return this.find(hssql, parameters.toArray());
	}

	/**
	 * 显示所有申请单来自单据类型
	 * 
	 * @param type
	 *            单据类型
	 * @return 与指定的单据类型匹配的进出口申请单
	 */
	public List findAllImpExpRequestBillByType(int type) {
		return this
				.find("select a from ImpExpRequestBill as a where a.billType = ? and a.company.id=?",
						new Object[] { Integer.valueOf(type),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找所有进出口申请单来自报关清单ID
	 * 
	 * @param applyToCustomsBillId
	 *            报关清单id
	 * @return 进出口申请单
	 */
	public List findImpExpRequestBillByApplyToCustomsBillId(
			String applyToCustomsBillId) {
		return this
				.find("select a from ImpExpRequestBill as a where a.applyToCustomsBillId = ? and a.company.id=?",
						new Object[] { applyToCustomsBillId,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找所有可以生成报关清单的数据来自进出口申请单(ATC)
	 * 
	 * @param type
	 *            单据类型
	 * @return 有效且已全转报关清单或全转已转报关单
	 */
	public List findImpExpRequestBillToATCByType(int type) {
		return this
				.find("select a from ImpExpRequestBill as a where a.billType = ? and a.isAvailability=? and a.isCustomsBill=? and a.company.id=?",
						new Object[] { Integer.valueOf(type),
								Boolean.valueOf(true), Boolean.valueOf(false),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得最大的单据号来自进出口申请单表
	 * 
	 * @param strtoday
	 *            单据号
	 * @return 与指定单据号匹配的单据中最大的单据号
	 */
	public List getMaxBillNoByType(String strtoday) {
		return this.find(
				"select max(a.billNo) from ImpExpRequestBill as a where a.company.id=?"
						+ " and a.billNo like ? ", new Object[] {
						CommonUtils.getCompany().getId(), strtoday + "%" });
	}

	/**
	 * 获得最大的单据号来自公司
	 * 
	 * @param company
	 *            公司
	 * @return 指定经营单位的最大的单据号
	 */
	public List getMaxBillNo(Company company) {
		return this
				.find("select max(a.billNo) from ImpExpRequestBill as a where a.company.id=?",
						new Object[] { company.getId() });
	}

	/**
	 * 删除进出口申请单数据
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单据
	 */
	public void deleteImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.delete(impExpRequestBill);
	}

	/**
	 * 保存进出口申请单数据
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单据
	 */
	public void saveImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.saveOrUpdate(impExpRequestBill);
	}

	/**
	 * 保存进出口申请单数据
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单据
	 */
	public void saveImpExpRequestBills(List impExpRequestBills) {
		for (int i = 0; i < impExpRequestBills.size(); i++) {
			ImpExpRequestBill data = (ImpExpRequestBill) impExpRequestBills
					.get(i);
			this.saveOrUpdate(data);
		}
	}

	/**
	 * 删除进出口商品信息数据
	 * 
	 * @param imp
	 *            进出口商品信息
	 */
	public void deleteImpExpCommodityInfo(ImpExpCommodityInfo imp) {
		this.delete(imp);
	}

	/**
	 * 保存进出口商品信息数据
	 * 
	 * @param list
	 *            进出口商品信息列表
	 */
	public void saveImpExpCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo data = (ImpExpCommodityInfo) list.get(i);
			this.saveOrUpdate(data);
		}
	}

	public void saveImpExpComInfo(ImpExpCommodityInfo info) {
		this.saveOrUpdate(info);
	}

	/**
	 * 获得当前进出口的商品信息的个数
	 * 
	 * @param parentId
	 *            进出口申请单id
	 * @return 进出口商品信息的个数
	 */
	public int findImpExpCommodityInfoCount(String parentId) {
		List list = this.find("select count(*) from ImpExpCommodityInfo b  "
				+ " where b.impExpRequestBill.id = ? ",
				new Object[] { parentId });
		if (list == null || list.size() <= 0) {
			return 0;
		}
		return Integer.parseInt(list.get(0).toString());
	}

	public List findImpExpCommodityInfo(String parentId, String ptNo,
			String countryCode) {
		List list = new ArrayList();
		if (countryCode == null) {
			list = this.find("select b from ImpExpCommodityInfo b  "
					+ " where b.impExpRequestBill.id = ?"
					+ "  and b.materiel.ptNo=?   and b.country is null ",
					new Object[] { parentId, ptNo });
		} else {
			list = this.find("select b from ImpExpCommodityInfo b  "
					+ " where b.impExpRequestBill.id = ?"
					+ "  and b.materiel.ptNo=?   and b.country.code =? ",
					new Object[] { parentId, ptNo, countryCode });
		}
		return list;
	}

	/**
	 * 获得报关清单个数
	 * 
	 * @param parentId
	 *            进出口申请单id
	 * @return 已转报关清单或已转报关单的个数
	 */
	public int findImpExpInfoToCustomCount(String parentId) {
		List list = this
				.find("select count(*) from ImpExpCommodityInfo b  "
						+ " where b.impExpRequestBill.id = ? and b.isTransferCustomsBill = ?",
						new Object[] { parentId, Boolean.valueOf(true) });
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		}
		return Integer.valueOf(list.get(0).toString());
	}

	/**
	 * 获得商品信息加载子表的记录
	 * 
	 * @return 进出口商品信息
	 */
	public List findImpExpCommodityInfo() {
		return this.find("select b from ImpExpCommodityInfo b "
				+ " left outer join fetch b.materiel"
				+ " where b.impExpRequestBill.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据料号取得商品信息
	 */
	public List findImpExpCommodtyInfoByMateriel(String ptNo) {
		return this.find("select b from InnerMergeData b "
				+ " where b.company.id = ? " + " and b.materiel.ptNo = ?",
				new Object[] { CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 获得商品信息加载子表的记录
	 * 
	 * @param parentId
	 *            进出口申请单据id
	 * @return 与指定的单据id匹配的进出口商品信息
	 */
	public List findImpExpCommodityInfo(String parentId) {
		return this.find("select b from ImpExpCommodityInfo b "
				+ " left outer join fetch b.materiel"
				+ " where b.impExpRequestBill.id = ? ",
				new Object[] { parentId });
	}

	/**
	 * 有数据转报关清单在进出口申请单中
	 * 
	 * @param c
	 *            进出口申请单据
	 * @return 有数据转报关清单的个数
	 */
	public boolean hasDataTransferApplyToCustomsBillByImpExpRequestBill(
			ImpExpRequestBill c) {
		List list = this
				.find("select count(*) from MakeApplyToCustoms m where m.company.id = ? "
						+ " and m.impExpCommodityInfo.impExpRequestBill.id = ?   ",
						new Object[] { CommonUtils.getCompany().getId(),
								c.getId() });
		if (list == null || list.size() <= 0) {
			return false;
		}
		if (((Integer) list.get(0)).intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得内部归并数据来自进出口商品信息
	 * 
	 * @param parentId
	 *            进出口申请单据id
	 * @return 与指定的进出口申请单据id匹配的按十位备案序号升序排列的内部归并数据
	 */
	public List findInnerMergeDataByImpExpCommodityInfo(String parentId) {
		return this
				.find("select a from InnerMergeData a left outer join fetch a.materiel b , ImpExpCommodityInfo c "
						+ "where  a.materiel.id=c.materiel.id and c.impExpRequestBill.id = ? and a.company.id = ? order by a.hsAfterTenMemoNo",
						new Object[] { parentId,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 获得进出口商品信息来自十码内部归并
	 * 
	 * @param innerMergeData
	 *            内部归并数据
	 * @param parentId
	 *            进出口申请单id
	 * @return 符合指定条件的进出口商品信息
	 */
	public List findImpExpCommodityInfoByInnerMergeDataAfter(
			InnerMergeData innerMergeData, String parentId) {
		return this
				.find("select a from ImpExpCommodityInfo a left outer join fetch a.materiel b,InnerMergeData c where"
						+ " c.materiel.id = a.materiel.id and c.hsAfterTenMemoNo = ? and a.impExpRequestBill.id = ? and a.materiel.company.id = ? ",
						new Object[] { innerMergeData.getHsAfterTenMemoNo(),
								parentId, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找所有进出报关清单
	 * 
	 * @return 报关清单
	 */
	public List findApplyToCustomsBillList() {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.company.id=?",
						CommonUtils.getCompany().getId());
	}

	/**
	 * 按报关清单编号查询报关清单
	 * 
	 * @param billNo
	 * @return
	 */
	public List findApplyToCustomsBillList(String billNo) {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.company.id=? and a.listNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), billNo });
	}

	/**
	 * 根据清单类型和清单状态 查找报关清单.
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param billState
	 *            单据状态
	 * @return 与指定的单据类型和状态匹配的报关清单
	 */
	public List findApplyToCustomsBillListByTypeAndState(int impExpType,
			int billState) {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.impExpType = ? and a.listState = ? and a.company.id=?",
						new Object[] { Integer.valueOf(impExpType),
								Integer.valueOf(billState),
								CommonUtils.getCompany().getId() });
	}

	public List findApplyToCustomsBillListByFlatAndState(int impExpFlag,
			int billState) {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.impExpFlag = ? and a.listState = ? and a.company.id=?",
						new Object[] { Integer.valueOf(impExpFlag),
								Integer.valueOf(billState),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找报关清单来自进出口标志
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 与指定的进出口标志符合的报关清单
	 */
	public List findApplyToCustomsBillListByType(int impExpFlag,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from ApplyToCustomsBillList as a where a.impExpFlag = ? and a.company.id=? ";
		parameters.add(Integer.valueOf(impExpFlag));
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hql += " and a.createdDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.createdDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hql += " order by  a.createdDate Desc,a.listNo Desc";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找报关清单来自进出口标志
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 与指定的进出口标志符合的报关清单
	 */
	public List findApplyToCustomsBillListByTypeBoToCustoms(int impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = " select  distinct  b from  AtcMergeAfterComInfo  a   left join "
				+ "  a.billList as b  where b.impExpFlag = ? "
				+ "and b.company.id=?  and b.listState not in ('0')  and b.effectstate=?";
		// + " and (a.isTransferCustomsBill is null or a.isTransferCustomsBill
		// =? )";
		parameters.add(Integer.valueOf(impExpFlag));
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(false);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据ID查找报关清单.
	 * 
	 * @param Id
	 *            报关清单id
	 * @return 符合指定id的报关清单
	 */
	public List findApplyToCustomsBillListById(String Id) {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.id = ? and a.company.id=?",
						new Object[] { Id, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找已转报关单的报关清单
	 * 
	 * @return 已转报关单的报关清单
	 */
	public List findBillListMakedCustomsDeclaration() {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.effectstate = ? and a.company.id=?",
						new Object[] { true,
								// Integer.valueOf(ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找未转报关单的报关清单.
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 未转报关单的报关清单
	 */
	public List findBillListNoMakeCustomsDeclaration(int impExpFlag) {
		return this
				.find("select a from ApplyToCustomsBillList as a where a.effectstate != ? and a.company.id=? and a.impExpFlag = ?",
						new Object[] { true,
								// Integer.valueOf(ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION),
								CommonUtils.getCompany().getId(), impExpFlag });
	}

	/**
	 * 取得报关清单最大的编号
	 * 
	 * @return 报关清单最大的编号(日期+序号)
	 */
	public String getApplyToCustomsBillListMaxNo() {
		String yearmonthday = "";
		String serialNo = "";
		Calendar calendar = Calendar.getInstance();
		yearmonthday = organizeStr(calendar.get(Calendar.YEAR), 4);
		yearmonthday += organizeStr(calendar.get(Calendar.MONTH) + 1, 2);
		yearmonthday += organizeStr(calendar.get(Calendar.DAY_OF_MONTH), 2);
		List list = this.find(
				"select max(a.listNo) from ApplyToCustomsBillList as a "
						+ " where a.listNo like ?", new Object[] { yearmonthday
						+ "%" });
		if (list != null && list.size() < 1) {
			serialNo = "0001";
		} else if (list != null && list.get(0) == null) {
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
	 * 取得申请单商品项数
	 * 
	 * @return 取得申请单商品项数
	 */
	public int getApplyToCustomsBillListSumNo(String id) {

		List list = this.find(" select count(a) from ImpExpRequestBill as a "
				+ " where a.id= ?", new Object[] { id });
		if (list != null && list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 删除报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	public void deleteApplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		this.delete(applyToCustomsBillList);
	}

	/**
	 * 保存报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	public void saveApplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		this.saveOrUpdate(applyToCustomsBillList);
	}

	/**
	 * 保存报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 保存更新后的报关清单
	 */
	public ApplyToCustomsBillList saveBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		this.saveOrUpdate(applyToCustomsBillList);
		return applyToCustomsBillList;
	}

	public EmsHeadH2kImg findEmsHeadH2kImgBySeqNum(EmsHeadH2k head,
			Integer seqNum) {
		if (getIsEmsH2kSend()) {
			List list = this
					.find("select a from EmsHeadH2kImg a where a.emsHeadH2k.id = ? and a.seqNum = ? and a.company.id = ? and a.sendState = ?",
							new Object[] { head.getId(), seqNum,
									CommonUtils.getCompany().getId(),
									Integer.valueOf(SendState.SEND) });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kImg) list.get(0);
			}
			return null;
		} else {
			List list = this
					.find("select a from EmsHeadH2kImg a where a.emsHeadH2k.id = ? and a.seqNum = ? and a.company.id = ?",
							new Object[] { head.getId(), seqNum,
									CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kImg) list.get(0);
			}
			return null;
		}

	}

	public EmsHeadH2kVersion findEmsHeadH2kExgBySeqNum(EmsHeadH2k head,
			Integer seqNum, Integer version) {
		List list = this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.emsHeadH2k.id = ? and a.emsHeadH2kExg.seqNum = ? and a.emsHeadH2kExg.company.id = ?"
						+ " and a.version = ?", new Object[] { head.getId(),
						seqNum, CommonUtils.getCompany().getId(), version });
		if (list != null && list.size() > 0) {
			return (EmsHeadH2kVersion) list.get(0);
		}
		return null;
	}

	public List findEmsHeadH2kVersionAll(EmsHeadH2k head, Integer seqNum,
			Integer ver) {
		if (getIsEmsH2kSend()) {
			return this.find(
					"select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.emsHeadH2k.id = ? "
							+ " and a.emsHeadH2kExg.seqNum = ? "
							+ " and a.emsHeadH2kExg.company.id = ? "
							+ " and a.emsHeadH2kExg.sendState = ?  "
							+ " and a.version=? ",
					new Object[] { head.getId(), seqNum,
							CommonUtils.getCompany().getId(),
							Integer.valueOf(SendState.SEND), ver });
		} else {
			return this.find(
					"select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.emsHeadH2k.id = ? "
							+ " and a.emsHeadH2kExg.seqNum = ? "
							+ " and a.emsHeadH2kExg.company.id = ? "
							+ " and a.emsHeadH2kExg.modifyMark = ?"
							+ "  and a.version=? ", new Object[] {
							head.getId(), seqNum,
							CommonUtils.getCompany().getId(),
							ModifyMarkState.UNCHANGE, ver });
		}
	}

	public List findEmsHeadH2kBom(EmsHeadH2kVersion vesion) {
		if (getIsEmsH2kSend()) {
			return this.find(
					"select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.id = ? "
							+ " and a.company.id = ?" + " and a.sendState=? ",
					new Object[] { vesion.getId(),
							CommonUtils.getCompany().getId(),
							Integer.valueOf(SendState.SEND) });
		} else {
			return this.find(
					"select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.id = ? "
							+ " and a.company.id = ?" + " and a.modifyMark=? ",
					new Object[] { vesion.getId(),
							CommonUtils.getCompany().getId(),
							ModifyMarkState.UNCHANGE });
		}

	}

	/**
	 * 获取归并关系BOM中最大版本号
	 */
	public Integer findMergerBomlVersioId(String ptNo, List forbidList) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (getIsEmsH2kSend()) {
			hsql = "select Max(a.emsEdiMergerVersion.version) from EmsEdiMergerExgBom a "
					+ " where a.sendState=? "
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo =? and a.company=?  ";
			parameters.add(Integer.valueOf(SendState.SEND));
		} else {
			hsql = "select Max(a.emsEdiMergerVersion.version) from EmsEdiMergerExgBom a "
					+ " where  a.modifyMark=? "
					+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo =? and a.company=? ";
			parameters.add(ModifyMarkState.UNCHANGE);
		}
		parameters.add(ptNo);
		parameters.add(CommonUtils.getCompany());
		if (forbidList != null && forbidList.size() > 0) {
			hsql += " and ( ";
			for (int j = 0; j < forbidList.size(); j++) {
				CommdityForbid cf = (CommdityForbid) forbidList.get(j);
				if (j == forbidList.size() - 1) {
					hsql += "  a.emsEdiMergerVersion.version!=? ";
				} else {
					hsql += "  a.emsEdiMergerVersion.version!=? and ";
				}
				parameters.add(Integer.valueOf(cf.getVersion()));
			}
			hsql += " ) ";
		}
		// System.out.println("===hsql==" + hsql);
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0) {
			return (Integer) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存中间表
	 * 
	 * @param obj
	 *            生成报关清单
	 */
	public void saveMakeListToCustoms(MakeListToCustoms obj) {
		obj.setUpdateDate(new Date());// 修改时间
		this.saveOrUpdate(obj);
	}

	/**
	 * 根据清单编号查询归并前商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 与指定清单编号匹配的归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByListID(
			ApplyToCustomsBillList applyToCustomsBillList) {
		return this
				.find("select a from AtcMergeBeforeComInfo as a left outer join fetch a.materiel where a.afterComInfo.billList.id = ? ",
						new Object[] { applyToCustomsBillList.getId() });
	}

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @return 与指定的归并后商品信息id匹配的归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByAfterID(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		return this
				.find("select a from AtcMergeBeforeComInfo as a left outer join fetch a.materiel "
						+ "where a.afterComInfo.id = ? order by a.serialNo,a.afterComInfo.emsSerialNo asc",
						new Object[] { atcMergeAfterComInfo.getId() });
	}

	public List findAllAtcMergerBeforeComInfo(ApplyToCustomsBillList billList) {
		return this
				.find("select a from AtcMergeBeforeComInfo as a left outer join fetch a.materiel "
						+ "where a.afterComInfo.billList.id = ? order by a.serialNo asc",// order
																							// by
																							// a.afterComInfo.emsSerialNo,a.afterComInfo.country.code
																							// asc
						new Object[] { billList.getId() });
	}

	// 重新排序
	public List findAllAtcMergerBeforeComInfo(String billNo) {
		return this
				.find("select a from AtcMergeBeforeComInfo as a left outer join fetch a.materiel where a.afterComInfo.billList.listNo = ? "
						+ "  order by a.serialNo,a.afterComInfo.emsSerialNo asc",
						new Object[] { billNo });
	}

	public List findBillNoOfImpExpRequestBill() {
		return this.find(
				" select  distinct  a.billNo from  ImpExpRequestBill a  "
						+ "  where  a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfo
	 *            报关清单归并前商品信息
	 */
	public void deleteAtcMergeBeforeComInfo(
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		this.delete(atcMergeBeforeComInfo);
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfo
	 *            报关清单归并前商品信息
	 */
	public void saveAtcMergeBeforeComInfo(
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		this.saveOrUpdate(atcMergeBeforeComInfo);
	}

	/**
	 * 根据清单编号查询归并后商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 与指定的清单编号匹配的归并后商品信息
	 */
	public List findAtcMergeAfterComInfoByListID(
			ApplyToCustomsBillList applyToCustomsBillList) {
		return this
				.find("select a from AtcMergeAfterComInfo as a where a.billList.id = ?",
						new Object[] { applyToCustomsBillList.getId() });
	}

	public Integer findMaxAtcMergeAfterComInfoByListID(
			ApplyToCustomsBillList applyToCustomsBillList) {
		List list = this
				.find("select max(a.serialNo) from AtcMergeBeforeComInfo as a"
						+ " where a.afterComInfo.billList.id = ? and  a.serialNo  is not null ",
						new Object[] { applyToCustomsBillList.getId() });
		if (list != null && list.get(0) != null) {
			return ((Integer) list.get(0)) + 1;
		} else {
			return 1;
		}
	}

	/**
	 * 根据清单 帐册序号 商品编码 查询归并后商品信息
	 * 
	 * @param billList
	 *            报关清单
	 * @param emsSerialNo
	 *            帐册序号
	 * @param complexCode
	 *            商品编码
	 * @return 符合指定条件的归并后商品信息
	 */
	public List findAtcMergeAfterComInfo(ApplyToCustomsBillList billList,
			String emsSerialNo, String complexCode) {
		return this
				.find("select a from AtcMergeAfterComInfo as a where a.billList.id=? and a.emsSerialNo = ? and a.complex.code=?",
						new Object[] { billList.getId(),
								Integer.valueOf(emsSerialNo), complexCode });
	}

	public AtcMergeAfterComInfo findAtcMergeAfterComInfoBySeqNum(
			ApplyToCustomsBillList billList, String emsSerialNo) {
		List list = this
				.find("select a from AtcMergeAfterComInfo as a where a.billList.id=? and a.emsSerialNo = ? and a.company.id = ?",
						new Object[] { billList.getId(),
								Integer.valueOf(emsSerialNo),
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (AtcMergeAfterComInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 删除报关清单归并后商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 */
	public void deleteAtcMergeAfterComInfo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		this.delete(atcMergeAfterComInfo);
	}

	/**
	 * 保存报关清单归并后商品信息 atcMergeAfterComInfo 归并后商品信息
	 */
	public void saveAtcMergeAfterComInfo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		this.saveOrUpdate(atcMergeAfterComInfo);
	}

	public AtcMergeAfterComInfo saveAtcMergeAfterComInfo1(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		this.saveOrUpdate(atcMergeAfterComInfo);
		return atcMergeAfterComInfo;
	}

	/**
	 * 取得成品版本号来自成品序号
	 * 
	 * @param seqNum
	 *            成品序号
	 * @return 与序号匹配的成品的版本号
	 */
	public List getExgVersion(String seqNum) {
		EmsHeadH2kExg emsHeadH2kExg = getEmsHeadH2kExgBySeqNum(seqNum);
		if (emsHeadH2kExg == null) {
			return null;
		}
		return this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id = ?",
						new Object[] { emsHeadH2kExg.getId() });
	}

	/**
	 * 查询归并关系归并前BOM版本信息
	 * 
	 * @param version
	 * @return
	 */
	public EmsEdiMergerVersion findEmsEdiMergerVersion(Integer version,
			String beforeMaterielPtNo) {
		List list = this
				.find(" select a from EmsEdiMergerVersion a "
						+ " where a.version = ? and a.emsEdiMergerBefore.ptNo=? and a.company.id = ? ",
						new Object[] { version, beforeMaterielPtNo,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (EmsEdiMergerVersion) list.get(0);
		}
		return null;
	}

	/**
	 * 取得成品来自成品序号
	 * 
	 * @param seqNum
	 *            成品序号
	 * @return 与指定序号匹配的执行状态的成品
	 */
	public EmsHeadH2kExg getEmsHeadH2kExgBySeqNum(String seqNum) {
		List list = this
				.find("select a from EmsHeadH2kExg a where a.emsHeadH2k.historyState=? and a.emsHeadH2k.declareState=?"
						+ " and a.seqNum=? and a.company.id = ?",
						new Object[] { new Boolean(false),
								DeclareState.PROCESS_EXE,
								Integer.valueOf(seqNum),
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (EmsHeadH2kExg) list.get(0);
		}
		return null;
	}

	/**
	 * 获取报关清单归并后商品的数目，用来更新报关清单的项数。
	 * 
	 * @param billList
	 *            报关清单
	 * @return 报关清单归并后的商品项数
	 */
	public int getAtcMergeAfterCommonNum(ApplyToCustomsBillList billList) {
		int num = 0;
		List list = this
				.find("select count(*) from AtcMergeAfterComInfo as a where a.billList.id = ? ",
						new Object[] { billList.getId() });
		if (list.size() > 0) {
			if (list.get(0) != null) {
				num = Integer.parseInt(list.get(0).toString());
			}
		}
		return num;
	}

	/**
	 * 查询内部归并成品/料件信息(如果在报关清单中已存在的话，将其过滤)
	 * 
	 * @param isProduct
	 *            是否是成品
	 * @param billList
	 *            报关清单
	 * @return 过滤后的内部归并成品/料件信息
	 */
	public List findTempCommInfoFromInnerMergeForAtc(boolean isProduct,
			ApplyToCustomsBillList billList) {
		return this
				.find("  select b.hsAfterTenMemoNo,b.materiel,b.hsAfterComplex,b.hsAfterMemoUnit,b.hsAfterLegalUnit,b.hsAfterSecondLegalUnit,b.hsAfterMaterielTenName,b.hsAfterMaterielTenSpec "
						+ " from InnerMergeData as b  left outer join b.hsAfterSecondLegalUnit "
						+ " where b.imrType=? "
						+ " and b.materiel.ptNo not in (select d.materiel.ptNo from AtcMergeBeforeComInfo as d where d.afterComInfo.billList.id=?"
						+ " and d.afterComInfo.billList.company.id=? )"
						+ " and b.company.id=? "
						+ " order by b.hsAfterTenMemoNo,b.hsAfterComplex.code,b.materiel.ptNo",
						new Object[] {
								isProduct ? MaterielType.FINISHED_PRODUCT
										: MaterielType.MATERIEL,
								billList.getId(),
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询归并关系备案成品信息(如果在报关清单中已存在的话，将其过滤)
	 * 
	 * @param declareState
	 *            申报状态
	 * @param billList
	 *            报关清单
	 * @return 过滤后的备案成品信息
	 */
	public List findDeclaredProductInfo(String declareState,
			ApplyToCustomsBillList billList) {
		return this
				.find("  select b.seqNum,a,b.complex,b.unit,b.complex.firstUnit,b.complex.secondUnit,b.name,b.spec "
						+ " from Materiel as a , EmsEdiMergerExgAfter as b  left outer join b.complex.secondUnit,EmsEdiMergerExgBefore c "
						+ " where a.ptNo=c.ptNo and b.id=c.emsEdiMergerExgAfter and  b.emsEdiMergerHead.declareState=? "
						+ " and a.ptNo not in (select d.materiel.ptNo from AtcMergeBeforeComInfo as d where d.afterComInfo.billList=?"
						+ " and d.afterComInfo.billList.company.id=? )"
						+ " and b.emsEdiMergerHead.historyState=? "
						+ " and b.emsEdiMergerHead.company.id=? "
						+ " and a.company.id=?"
						+ " order by b.seqNum,b.complex.code,a.ptNo ",
						new Object[] { declareState, billList.getId(),
								CommonUtils.getCompany().getId(),
								new Boolean(false),
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询归并关系备案料件信息(如果在报关清单中已存在的话，将其过滤)
	 * 
	 * @param declareState
	 *            申报状态
	 * @param billList
	 *            报关清单
	 * @return 过滤后的备案的料件信息
	 */
	public List findDeclaredMaterielInfo(String declareState,
			ApplyToCustomsBillList billList) {
		return this
				.find("  select b.seqNum,a,b.complex,b.unit,b.complex.firstUnit,b.complex.secondUnit,b.name,b.spec "
						+ " from Materiel as a , EmsEdiMergerImgAfter as b  left outer join b.complex.secondUnit,EmsEdiMergerImgBefore c "
						+ " where a.ptNo=c.ptNo and b.id=c.emsEdiMergerImgAfter and  b.emsEdiMergerHead.declareState=? "
						+ " and a.ptNo not in (select d.materiel.ptNo from AtcMergeBeforeComInfo as d where d.afterComInfo.billList.id=?"
						+ " and d.afterComInfo.billList.company.id=?)"
						+ " and b.emsEdiMergerHead.historyState=?"
						+ " and b.emsEdiMergerHead.company.id=?"
						+ " and a.company.id=?"
						+ "  order by b.seqNum,b.complex.code,a.ptNo",
						new Object[] { declareState, billList.getId(),
								CommonUtils.getCompany().getId(),
								new Boolean(false),
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得料件信息来自料件序号
	 * 
	 * @param seqNum
	 *            料件序号
	 * @return 与指定的料件序号匹配的正在执行的料件信息
	 */
	public EmsHeadH2kImg getEmsHeadImg(Integer seqNum) {
		if (getIsEmsH2kSend()) {
			List list = this.find("select a from EmsHeadH2kImg a "
					+ " where a.sendState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.seqNum = ? " + " and a.emsHeadH2k.company.id=?"
					+ " order by a.seqNum",
					new Object[] { Integer.valueOf(SendState.SEND),
							new Boolean(false), seqNum,
							CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kImg) list.get(0);
			}
			return null;
		} else {
			List list = this.find("select a from EmsHeadH2kImg a "
					+ " where a.emsHeadH2k.declareState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.seqNum = ? " + " and a.emsHeadH2k.company.id=?"
					+ " order by a.seqNum", new Object[] {
					DeclareState.PROCESS_EXE, new Boolean(false), seqNum,
					CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kImg) list.get(0);
			}
			return null;
		}
	}

	// 计算料件平均单价
	public Double getImgAveragePrice(Integer seqNum, Date beginDate,
			Date endDate, Boolean isEffect, boolean isProduct,
			boolean isDeclaration, boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice)/sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.company.id = ? "
				+ " and a.commSerialNo = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}

		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 取得电子帐册成品信息来自成品序号
	 * 
	 * @param seqNum
	 *            成品序号
	 * @return 与指定序号匹配的正在执行状态的电子帐册成品信息
	 */
	public EmsHeadH2kExg getEmsHeadExg(Integer seqNum) {
		if (getIsEmsH2kSend()) {
			List list = this.find("select a from EmsHeadH2kExg a "
					+ " where a.sendState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.seqNum = ? " + " and a.emsHeadH2k.company.id=?"
					+ " order by a.seqNum",
					new Object[] { Integer.valueOf(SendState.SEND),
							new Boolean(false), seqNum,
							CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kExg) list.get(0);
			}
			return null;
		} else {
			List list = this.find("select a from EmsHeadH2kExg a "
					+ " where a.emsHeadH2k.declareState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.seqNum = ? " + " and a.emsHeadH2k.company.id=?"
					+ " order by a.seqNum", new Object[] {
					DeclareState.PROCESS_EXE, new Boolean(false), seqNum,
					CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kExg) list.get(0);
			}
			return null;
		}
	}

	/**
	 * 获取已经存在的清单表头资料
	 */
	public List findApplyToCustomsBillListId(String dataString) {
		String[] strArray = dataString.split("/");
		String listNo = strArray.length < 1 ? "" : strArray[0].trim();
		String emsHeadH2k = strArray.length < 2 ? "" : strArray[1].trim();
		return this.find("select a from ApplyToCustomsBillList a "
				+ " where a.listNo=? " + " and a.emsHeadH2k= ? "
				+ " and a.company.id=? and a.listState=? ", new Object[] {
				listNo, emsHeadH2k, CommonUtils.getCompany().getId(),
				ApplyToCustomsBillList.DRAFT });
	}

	/**
	 * 获取已存在的表体数据
	 */
	public List findAtcMergeBeforeComInfoId(String dataString) {
		String[] strArray = dataString.split("/");
		String listNo = strArray.length < 1 ? "" : strArray[0].trim();
		String emsHeadH2k = strArray.length < 2 ? "" : strArray[1].trim();
		String ptNo = strArray.length < 3 ? "" : strArray[2].trim();
		String cuontry = strArray.length < 4 ? "" : strArray[3].trim();
		return this
				.find("select a from AtcMergeBeforeComInfo a "
						+ " left join a.afterComInfo b"
						+ " left join a.materiel c "
						+ " where  b.billList.listNo=? "
						+ " and b.billList.emsHeadH2k= ? "
						+ " and c.ptNo=? "
						+ " and a.company.id=? and b.billList.listState=? and a.salesCountry.name=?"
						+ " order by c.ptNo ", new Object[] { listNo,
						emsHeadH2k, ptNo, CommonUtils.getCompany().getId(),
						ApplyToCustomsBillList.DRAFT, cuontry });
	}

	/**
	 * 获取已存在的表体数据
	 */
	public List findAtcMergeAfterComInfoId(String dataString) {
		String[] strArray = dataString.split("/");
		String listNo = strArray.length < 1 ? "" : strArray[0].trim();
		String emsHeadH2k = strArray.length < 2 ? "" : strArray[1].trim();
		return this
				.find("select a from AtcMergeAfterComInfo a where a.billList.listNo=?"
						+ " and a.billList.emsHeadH2k= ? and a.company.id=? and a.billList.listState=?",
						new Object[] { listNo, emsHeadH2k,
								CommonUtils.getCompany().getId(),
								ApplyToCustomsBillList.DRAFT });
	}

	/**
	 * 查询电子帐册备案成品信息(注意：商品禁用也使用到)
	 * 
	 * @param declareState
	 *            申报状态
	 * @param customsDeclaration
	 *            报关商品信息
	 * @return 与指定条件匹配的电子帐册备案成品信息
	 */
	public List findEdiProductInfo(String declareState) {
		if (getIsEmsH2kSend()) {
			return this
					.find(" select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.id,a.legalUnitGene ,a.legalUnit2Gene  from EmsHeadH2kExg as a "
							+ " left outer join a.complex.secondUnit "
							+ " left outer join a.complex "
							+ " left outer join a.unit "
							+ " left outer join a.complex.firstUnit "
							+ " where a.sendState=? "
							+ " and a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ " order by a.seqNum",
							new Object[] { Integer.valueOf(SendState.SEND),
									new Boolean(false),
									CommonUtils.getCompany().getId() });
		} else {
			return this
					.find(" select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.id ,a.legalUnitGene ,a.legalUnit2Gene  from EmsHeadH2kExg as a "
							+ " left outer join a.complex.secondUnit "
							+ " left outer join a.complex "
							+ " left outer join a.unit "
							+ " left outer join a.complex.firstUnit "
							+ " where a.emsHeadH2k.declareState=? "
							+ " and a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ " order by a.seqNum", new Object[] {
							declareState, new Boolean(false),
							CommonUtils.getCompany().getId() });
		}
	}

	public List findEdiProductInfo(String declareState,
			CustomsDeclaration customsDeclaration, String sfield, Object svalues) {
		if (getIsEmsH2kSend()) {
			List<Object> para = new ArrayList<Object>();
			String hsql = " select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.id ,a.legalUnitGene,a.legalUnit2Gene from EmsHeadH2kExg as a "
					+ " left outer join a.complex.secondUnit "
					+ " left outer join a.complex "
					+ " left outer join a.unit "
					+ " left outer join a.complex.firstUnit "
					+ " where a.sendState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.emsHeadH2k.company.id=? ";
			para.add(Integer.valueOf(SendState.SEND));
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (sfield != null && sfield.equals("seqNum")) {
				hsql += " and a." + sfield + " = ? ";
				para.add(svalues);
			} else {
				hsql += " and a." + sfield + " like ? ";
				para.add("%" + svalues + "%");
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());

		} else {
			List<Object> para = new ArrayList<Object>();
			String hsql = " select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.id ,a.legalUnit2Gene ,a.legalUnit2Gene from EmsHeadH2kExg as a "
					+ " left outer join a.complex.secondUnit "
					+ " left outer join a.complex "
					+ " left outer join a.unit "
					+ " left outer join a.complex.firstUnit "
					+ " where a.emsHeadH2k.declareState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.emsHeadH2k.company.id=? ";
			para.add(declareState);
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (sfield != null && sfield.equals("seqNum")) {
				hsql += " and a." + sfield + " = ? ";
				para.add(svalues);
			} else {
				hsql += " and a." + sfield + " like ? ";
				para.add("%" + svalues + "%");
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());
		}
	}

	/**
	 * 取得版本号
	 * 
	 * @param id
	 *            电子帐册成品id
	 * @return 没有被禁止的与指定的id匹配的电子帐册成品的版本号
	 */
	public List findVersionNo(String id) {
		// return this.find(
		// "select a.version from EmsHeadH2kVersion a where a.emsHeadH2kExg.id=?
		// "
		// + " and (a.isForbid is null or a.isForbid = ?) ",
		// new Object[] { id, new Boolean(false) });
		// System.out.println("===id=" + id);
		if (getIsEmsH2kSend()) {
			return this
					.find(" select distinct a.emsHeadH2kVersion.version from EmsHeadH2kBom a  "
							+ " where a.emsHeadH2kVersion.emsHeadH2kExg.id=? "
							+ " and a.sendState=? and (a.emsHeadH2kVersion.isForbid is null or a.emsHeadH2kVersion.isForbid = ?)  ",
							new Object[] { id, Integer.valueOf(SendState.SEND),
									new Boolean(false) });
		} else {
			return this
					.find(" select distinct a.emsHeadH2kVersion.version from EmsHeadH2kBom a  "
							+ " where a.emsHeadH2kVersion.emsHeadH2kExg.id=? "
							+ " and a.modifyMark=?  and (a.emsHeadH2kVersion.isForbid is null or a.emsHeadH2kVersion.isForbid = ?)  ",
							new Object[] { id, ModifyMarkState.UNCHANGE,
									new Boolean(false) });
		}

	}

	/**
	 * 取得版本号
	 * 
	 * @param id
	 *            电子帐册成品id
	 * @return 被禁止的与指定的id匹配的电子帐册成品的版本号
	 */
	public List findVersion(String id) {
		// return this.find(
		// "select a.version from EmsHeadH2kVersion a where a.emsHeadH2kExg.id=?
		// "
		// + " and (a.isForbid is null or a.isForbid = ?) ",
		// new Object[] { id, new Boolean(false) });
		// System.out.println("===id=" + id);
		if (getIsEmsH2kSend()) {
			return this
					.find(" select distinct a.emsHeadH2kVersion.version from EmsHeadH2kBom a  "
							+ " where a.emsHeadH2kVersion.emsHeadH2kExg.id=? "
							+ " and a.sendState=? and (a.emsHeadH2kVersion.isForbid is null or a.emsHeadH2kVersion.isForbid = ?)  ",
							new Object[] { id, Integer.valueOf(SendState.SEND),
									new Boolean(true) });
		} else {
			return this
					.find(" select distinct a.emsHeadH2kVersion.version from EmsHeadH2kBom a  "
							+ " where a.emsHeadH2kVersion.emsHeadH2kExg.id=? "
							+ " and a.modifyMark=?  and (a.emsHeadH2kVersion.isForbid is null or a.emsHeadH2kVersion.isForbid = ?)  ",
							new Object[] { id, ModifyMarkState.UNCHANGE,
									new Boolean(true) });
		}

	}

	/**
	 * 电子帐册是否分批发送
	 * 
	 * @return
	 */

	private boolean getIsEmsH2kSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsEdiH2kSend,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询电子帐册备案料件信息(注意：商品禁用也使用到)
	 * 
	 * @param declareState
	 *            申报状态
	 * @param customsDeclaration
	 *            报关单商品信息
	 * @return 没有被禁止的电子帐册备案料件信息
	 */
	public List findEdiMaterielInfo(String declareState) {
		if (getIsEmsH2kSend()) {
			return this
					.find(" select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.detailNote,a.legalUnitGene ,a.legalUnit2Gene  from EmsHeadH2kImg as a "
							+ " left outer join  a.complex "
							+ " left outer join  a.unit "
							+ " left outer join  a.complex.firstUnit "
							+ " left outer join  a.complex.secondUnit "
							+ " where a.sendState=? "
							+ " and a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ " and (a.isForbid is null or a.isForbid = ?)"
							+ "  order by a.seqNum",
							new Object[] { Integer.valueOf(SendState.SEND),
									new Boolean(false),
									CommonUtils.getCompany().getId(),
									new Boolean(false) });
		} else {
			return this
					.find(" select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.detailNote ,a.legalUnitGene ,a.legalUnit2Gene  from EmsHeadH2kImg as a "
							+ " left outer join  a.complex "
							+ " left outer join  a.unit "
							+ " left outer join  a.complex.firstUnit "
							+ " left outer join  a.complex.secondUnit "
							+ " where a.emsHeadH2k.declareState=? "
							+ " and a.emsHeadH2k.historyState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ " and (a.isForbid is null or a.isForbid = ?)"
							+ "  order by a.seqNum", new Object[] {
							declareState, new Boolean(false),
							CommonUtils.getCompany().getId(),
							new Boolean(false) });
		}

	}

	public List findEdiMaterielInfo(String declareState,
			CustomsDeclaration customsDeclaration, String sfield, Object svalues) {
		if (getIsEmsH2kSend()) {
			List<Object> para = new ArrayList<Object>();
			String hsql = " select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.detailNote, a.legalUnitGene,a.legalUnit2Gene from EmsHeadH2kImg as a "
					+ " left outer join  a.complex "
					+ " left outer join  a.unit "
					+ " left outer join  a.complex.firstUnit "
					+ " left outer join  a.complex.secondUnit "
					+ " where a.sendState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.emsHeadH2k.company.id=? "
					+ " and (a.isForbid is null or a.isForbid = ?)";
			para.add(Integer.valueOf(SendState.SEND));
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			para.add(new Boolean(false));
			if (sfield != null && sfield.equals("seqNum")) {
				hsql += " and a." + sfield + " = ? ";
				para.add(svalues);
			} else {
				hsql += " and a." + sfield + " like ? ";
				para.add("%" + svalues + "%");
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());
		} else {
			List<Object> para = new ArrayList<Object>();
			String hsql = " select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name,a.spec,a.detailNote ,a.legalUnit2Gene ,a.legalUnit2Gene from EmsHeadH2kImg as a "
					+ " left outer join  a.complex "
					+ " left outer join  a.unit "
					+ " left outer join  a.complex.firstUnit "
					+ " left outer join  a.complex.secondUnit "
					+ " where a.emsHeadH2k.declareState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.emsHeadH2k.company.id=? "
					+ " and (a.isForbid is null or a.isForbid = ?)";
			para.add(declareState);
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			para.add(new Boolean(false));
			if (sfield != null && sfield.equals("seqNum")) {
				hsql += " and a." + sfield + " = ? ";
				para.add(svalues);
			} else {
				hsql += " and a." + sfield + " like ? ";
				para.add("%" + svalues + "%");
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());
		}
	}

	/**
	 * 根据报关清单取得报关单
	 * 
	 * @param billList
	 *            报关清单
	 * @return 与指定的报关清单匹配的报关单信息
	 */
	public List findCustomsDeclarationByBillList(ApplyToCustomsBillList billList) {
		return this
				.find("select a  from CustomsDeclaration as a where a.billListId=? and a.company.id=?",
						new Object[] { billList.getListNo(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据报关清单取得清单和报关单商品信息的差异
	 * 
	 * @param billList
	 *            报关清单
	 * @return 清单和报关单商品信息
	 */
	public List findDiffrenceAnalyseCommInfo(ApplyToCustomsBillList billList) {
		return this
				.find(" select a,b from AtcMergeAfterComInfo a,CustomsDeclarationCommInfo b"
						+ " where a.emsSerialNo=b.commSerialNo "
						+ " and a.company.id = ? and a.complex=b.complex and a.billList.id=? and b.baseCustomsDeclaration.billListId=?",
						new Object[] { CommonUtils.getCompany().getId(),
								billList.getId(), billList.getListNo() });
	}

	/**
	 * 查询报关单信息来自预录入号
	 * 
	 * @param preCustomsDeclarationCode
	 *            预录入号
	 * @param projectType
	 *            模块信息
	 * @return 与指定的预录入号匹配的报关单信息
	 */
	public List findCustomsDeclarationbyYlrh(String preCustomsDeclarationCode,
			int projectType) {
		System.out.println("--projectType:" + projectType);
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration a  ";
			break;
		default:
			hql = "select a  from CustomsDeclaration a ";
			break;
		}
		hql += " where a.preCustomsDeclarationCode=? and a.company.id=?"
				+ " order by a.serialNumber ";
		return this.find(hql, new Object[] { preCustomsDeclarationCode,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询报关单信息来自统一编号
	 * 
	 * @param uniteCode
	 *            统一编号
	 * @param projectType
	 *            模块信息
	 * @return 与指定的统一编号匹配的报关单信息
	 */
	public List findCustomsDeclarationbyUniteCode(String uniteCode,
			int projectType) {
		System.out.println("--projectType:" + projectType);
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration a  ";
			break;
		default:
			hql = "select a  from CustomsDeclaration a ";
			break;
		}
		hql += " where a.unificationCode=? and a.company.id=?"
				+ " order by a.serialNumber ";
		return this.find(hql, new Object[] { uniteCode,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找进口类型退港申请书来自进出口单据Id
	 * 
	 * @param impExpRequestBillId
	 *            进出口申请单id
	 * @return 与指定的进出口申请单据匹配的进口类型的退港申请书
	 */
	public ImpBackPortRequestBook findImpBackPortRequestBookById(
			String impExpRequestBillId) {
		List list = this.find("select a from ImpBackPortRequestBook as a "
				+ " where a.impExpRequestBill.id = ? and a.company.id=?",
				new Object[] { impExpRequestBillId,
						CommonUtils.getCompany().getId() });
		if (list == null || list.size() <= 0) {
			return null;
		}
		return (ImpBackPortRequestBook) list.get(0);
	}

	/**
	 * 查找出口类型退港申请书来自进出口单据Id
	 * 
	 * @param impExpRequestBillId
	 *            进出口申请单id
	 * @return 与指定的进出口申请单id匹配的出口类型的退港申请书
	 */
	public ExpBackPortRequestBook findExpBackPortRequestBookById(
			String impExpRequestBillId) {
		List list = this.find("select a from ExpBackPortRequestBook as a "
				+ " where a.impExpRequestBill.id = ? and a.company.id=?",
				new Object[] { impExpRequestBillId,
						CommonUtils.getCompany().getId() });
		if (list == null || list.size() <= 0) {
			return null;
		}
		return (ExpBackPortRequestBook) list.get(0);
	}

	/**
	 * 保存进口类型的退港申请书
	 * 
	 * @param impBackPortRequestBook
	 *            进口类型的退港申请书
	 */
	public void saveImpBackPortRequestBook(
			ImpBackPortRequestBook impBackPortRequestBook) {
		this.saveOrUpdate(impBackPortRequestBook);
	}

	/**
	 * 删除出口类型的退港申请书
	 * 
	 * @param expBackPortRequestBook
	 *            出口类型的退港申请书
	 */
	public void deleteExpBackPortRequestBook(
			ExpBackPortRequestBook expBackPortRequestBook) {
		this.delete(expBackPortRequestBook);
	}

	/**
	 * 删除进口类型的退港申请书
	 * 
	 * @param impBackPortRequestBook
	 *            进口类型的退港申请书
	 */
	public void deleteImpBackPortRequestBook(
			ImpBackPortRequestBook impBackPortRequestBook) {
		this.delete(impBackPortRequestBook);
	}

	/**
	 * 保存出口类型的退港申请书
	 * 
	 * @param expBackPortRequestBook
	 *            出口类型的退港申请书
	 */
	public void saveExpBackPortRequestBook(
			ExpBackPortRequestBook expBackPortRequestBook) {
		this.saveOrUpdate(expBackPortRequestBook);
	}

	/**
	 * 查找进出口商品信息来自是否备案并以父类ID过滤
	 * 
	 * @param parentId
	 *            进出口申请单据id
	 * @return 与进出口申请单匹配且备案的进出口商品信息
	 */
	public List findImpExpCommodityInfoByPutOnRecord(String parentId) {
		return this
				.find("select b from ImpExpCommodityInfo b left outer join fetch b.materiel"
						+ " where b.impExpRequestBill.id = ? and b.isPutOnRecord = ? ",
						new Object[] { parentId, new Boolean(true) });
	}

	public List findImpExpCommodityInfoByisTransferCustomsBill(
			ImpExpRequestBill head) {
		return this
				.find("select a from ImpExpCommodityInfo a left outer join fetch a.materiel "
						+ " where a.impExpRequestBill.id = ? and a.isTransferCustomsBill = ? ",
						new Object[] { head.getId(), new Boolean(false) });
	}

	/**
	 * 查找由转厂单据生成关封申请单的中间表,来自关封申请单据商品信息
	 * 
	 * @param atcMergeBeforeComInfoId
	 *            归并前的商品信息id
	 * @return 关封申请单的中间表信息
	 */
	public List findMakeApplyToCustomsByatcMergeBeforeComInfo(
			String atcMergeBeforeComInfoId) {
		return this.find(
				"select m from MakeApplyToCustoms m where m.company.id = ? "
						+ " and m.atcMergeBeforeComInfo.id = ?   ",
				new Object[] { CommonUtils.getCompany().getId(),
						atcMergeBeforeComInfoId });
	}

	/**
	 * 保存由进出口单据生成报关清单的中间表
	 * 
	 * @param list
	 *            报关清单的中间表信息
	 */
	public void saveMakeApplyToCustoms(List list) {
		for (int i = 0; i < list.size(); i++) {
			MakeApplyToCustoms m = (MakeApplyToCustoms) list.get(i);
			this.saveOrUpdate(m);
		}
	}

	/**
	 * 删除由进出口单据生成报关清单的中间表
	 * 
	 * @param m
	 *            报关清单的中间表
	 */
	public void deleteMakeApplyToCustoms(MakeApplyToCustoms m) {
		this.delete(m);
	}

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型匹配生效且存在未转关封单据的进出口申请单
	 */
	public List findImpExpRequestBillByImpExpTypeToATC(int impExpType) {
		return this
				.find("select a from ImpExpRequestBill as a "
						+ "where a.billType = ? "
						+ "and a.company.id=? "
						+ "and a.isAvailability=? "
						+ "and (a.isCustomsBill=? or a.isCustomsBill is null)  order by a.catNo,a.billNo",
						new Object[] { new Integer(impExpType),
								CommonUtils.getCompany().getId(),
								new Boolean(true), new Boolean(false), });
	}

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型匹配生效且存在未转关封单据的进出口申请单
	 */
	public List findImpExpRequestBillByImpExpTypeToATC(
			Date beginAvailabilityDate, Date endAvailabilityDate,
			String billNo, int impExpType) {
		List<Object> para = new ArrayList<Object>();
		String hsql = "select a from ImpExpRequestBill as a "
				+ "where a.billType = ? " + "and a.company.id=? "
				+ "and a.isAvailability=? "
				+ "and (a.isCustomsBill=? or a.isCustomsBill is null) ";

		para.add(new Integer(impExpType));
		para.add(CommonUtils.getCompany().getId());
		para.add(new Boolean(true));
		para.add(new Boolean(false));
		if (beginAvailabilityDate != null) {
			hsql += " and  a.beginAvailability>=?";
			para.add(CommonUtils.getBeginDate(beginAvailabilityDate));
		}
		if (endAvailabilityDate != null) {
			hsql += "  and a.beginAvailability<=?";
			para.add(CommonUtils.getEndDate(endAvailabilityDate));
		}
		if (billNo != null && !"".equals(billNo)) {
			hsql += " and  a.billNo like ?  ";
			para.add("%" + billNo + "%");
		}
		hsql += " order by a.catNo,a.billNo ";
		return this.find(hsql, para.toArray());
	}

	/**
	 * 查找由海关帐到进出口申请单的中间表,来自进出口商品信息
	 * 
	 * @param impExpCommodityInfoId
	 *            进出口商品信息id
	 * @return 与指定的进出口商品信息id匹配的进出口申请单的中间表
	 */
	public List findMakeImpExpRequestBillByImpExpCommodityInfo(
			String impExpCommodityInfoId) {
		return this.find(
				"select m from MakeImpExpRequestBill m where m.company.id = ? "
						+ " and m.iecId = ?   ",
				new Object[] { CommonUtils.getCompany().getId(),
						impExpCommodityInfoId });
	}

	/**
	 * 保存由进出口单据生成报关清单的中间表
	 * 
	 * @param list
	 *            进出口单据生成报关清单中间表
	 */
	public void saveMakeImpExpRequestBill(List list) {
		for (int i = 0; i < list.size(); i++) {
			MakeImpExpRequestBill m = (MakeImpExpRequestBill) list.get(i);
			this.saveOrUpdate(m);
		}
	}

	/**
	 * 删除由进出口单据生成报关清单的中间表
	 * 
	 * @param m
	 *            进出口单据生成报关清单中间表
	 */
	public void deleteMakeImpExpRequestBill(MakeImpExpRequestBill m) {
		this.delete(m);
	}

	/**
	 * 抓取报关单的出口成品资料
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 在指定的日期内所有的报关单的成品资料
	 */
	public List findTotalExportCommInfo(Date beginDate, Date endDate) {
		String hsql = " select a.baseCustomsDeclaration.emsHeadH2k,a.commSerialNo,sum(a.commAmount) from CustomsDeclarationCommInfo as a"
				+ " where 2>1 ";
		List<Object> parameters = new ArrayList<Object>();
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?,?,?,?) "
				+ " and a.baseCustomsDeclaration.company.id=? "
				+ " group by a.baseCustomsDeclaration.emsHeadH2k,a.commSerialNo ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
		parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
		parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);
		parameters.add(ImpExpType.BACK_PORT_REPAIR);

		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据成品序号，和电子帐册号码 查找成品耗用
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param commSerialNo
	 *            成品序号
	 * @return 与指定的成品序号 帐册编号匹配的正在执行的电子帐册bom中成品的耗用
	 */
	public List findBomByCommSerialNo(String emsNo, String commSerialNo) {
		String hsql = "select a  from EmsHeadH2kBom as a where a.emsHeadH2kVersion.version=? "
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? "
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.company.id=? "
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState=? "
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState=? "
				+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.emsNo=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(new Integer(0));
		parameters.add(commSerialNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(new Boolean(false));
		parameters.add(emsNo);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 取得单位对照比例因子
	 * 
	 * @param ptUnitCode
	 *            计量单位代码
	 * @return 与指定的计量单位代码匹配的单位对照比例因子
	 */
	public Double getUnitScale(String ptUnitCode) {
		if (ptUnitCode == null) {
			return Double.valueOf(1);
		}
		List list = this
				.find("select a from CalUnit a where a.code = ? and a.company.id = ?",
						new Object[] { ptUnitCode,
								CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return ((CalUnit) list.get(0)).getScale();
		}
		return Double.valueOf(1);
	}

	/**
	 * 返回载货清单成品列表
	 * 
	 * @return 载货清单成品列表
	 */
	public List findMakeListExgList() {
		return this.find(
				"select a from MakeListExgList a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 判断单据号是否重复
	 * 
	 * @param data
	 *            进出口申请单
	 * @return 单据号如果重复返回true 否则返回false
	 */
	public boolean isReMerger(ImpExpRequestBill data) {

		List<Object> parameters = new ArrayList<Object>();
		// String hql = "select a from ImpExpRequestBill a where a.billType = ?
		// and a.company.id=? and a.billNo =?";
		// parameters.add(data.getBillType());
		// 单据号在进出口都是唯一
		String hql = "select a from ImpExpRequestBill a where a.company.id=? and a.billNo =?";

		parameters.add(CommonUtils.getCompany().getId());

		parameters.add(data.getBillNo());

		if (data.getId() != null && !"".equals(data.getId().trim())) {

			hql += " and a.id <> ?";

			parameters.add(data.getId());

		}

		List list = this.find(hql, parameters.toArray());

		if (list.size() > 0) {

			return true;
		}
		return false;
	}

	/**
	 * 帐册进出平衡 料件 （改后）
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param iseffective
	 *            是否生效
	 * @return 在有效期内生效的料件进口料件转厂退厂返工的资料
	 */
	public List findImpExpCommInfo(Date beginDate, Date endDate,
			Boolean iseffective) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (iseffective.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(iseffective);
		}
		hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
		parameters.add(ImpExpType.DIRECT_IMPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		hsql += " order by a.baseCustomsDeclaration.serialNumber,a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 进出口报关单信息
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param impExpFlag
	 *            进出口标志
	 * @return 与指定的进出口标志匹配在有效期内生效的进出口报关单信息
	 */
	public List findImpExpCustomsDeclaration(Date beginDate, Date endDate,
			Integer impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String sql = "select a from CustomsDeclaration as a where a.company.id=? and a.effective = ? and a.impExpFlag = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(impExpFlag.intValue());
		if (beginDate != null) {
			sql += " and a.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			sql += " and a.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		sql += " order by a.serialNumber";
		return this.find(sql, parameters.toArray());
	}

	/**
	 * 查询进出口报关单信息
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param impExpFlag
	 *            进出口标志
	 * @return 进出口报关单信息
	 */
	public List findImpExpCommInfo(Date beginDate, Date endDate,
			Integer impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (impExpFlag.equals(ImpExpFlag.IMPORT)) {
			hsql += "and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpFlag.equals(ImpExpFlag.EXPORT)) {
			hsql += "and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		// if (iseffective.equals(new Boolean(false))){
		// hsql += " and a.baseCustomsDeclaration.effective = ? ";
		// parameters.add(iseffective);
		// } else {
		// hsql += " and (a.baseCustomsDeclaration.effective = ? or
		// a.baseCustomsDeclaration.effective is null) ";
		// parameters.add(iseffective);
		// }
		hsql += " order by a.baseCustomsDeclaration.serialNumber,a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得进出口报关单商品信息
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isProduct
	 *            是否是成品
	 * @param iseffective
	 *            是否生效
	 * @return 符合指定条件生效的报关单商品信息
	 */
	public List findImpExpCommInfoSpiecal(Integer seqNum, String customer,
			String iEType, Date beginDate, Date endDate, boolean isProduct,
			int iseffective, boolean isDeclaration, boolean isDept,
			List deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		StringBuilder hsql = new StringBuilder("select" + " a.serialNumber"
				+ " a.commSerialNo" + " a.commName" + " a.complex"
				+ " a.commSpec" + " a.unit" + " a.declarationCompany"
				+ " a.projectDept" + " a.commSerialNo" + " a.commAmount"
				+ " a.baseCustomsDeclaration" + " a.serialNumber"
				+ " a.commTotalPrice" + " from CustomsDeclarationCommInfo as a");
		hsql.append(" left outer join fetch a.baseCustomsDeclaration ");
		hsql.append(" left outer join fetch a.complex ");
		hsql.append(" left outer join fetch a.legalUnit ");
		hsql.append(" left outer join fetch a.secondLegalUnit ");
		hsql.append(" left outer join fetch a.country ");
		hsql.append(" left outer join fetch a.uses ");
		hsql.append(" left outer join fetch a.levyMode ");
		hsql.append(" left outer join fetch a.projectDept ");
		hsql.append(" left outer join fetch a.unit ");
		hsql.append(" where a.baseCustomsDeclaration.company.id=? ");
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql.append(" and a.commSerialNo=? ");
			parameters.add(seqNum);
		}
		if (customer != null && !customer.trim().equals("")) {// 客户供应商
			hsql.append(" and a.baseCustomsDeclaration.customer.id=? ");
			parameters.add(customer);
		}
		if (iEType != null && !iEType.trim().equals("")) {
			hsql.append(" and a.baseCustomsDeclaration.impExpType=? ");
			parameters.add(Integer.valueOf(iEType));
		}
		if (!isProduct) {// 料件
			hsql.append(" and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ");
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {// 成品
			hsql.append(" and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ");
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);

		}
		if (beginDate != null && isDeclaration) { // 申报日期
			hsql.append(" and a.baseCustomsDeclaration.declarationDate>=? ");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate<=? ");
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql.append(" and a.baseCustomsDeclaration.impExpDate>=? ");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql.append(" and a.baseCustomsDeclaration.impExpDate<=? ");
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (iseffective == CustomsDeclarationState.EFFECTIVED) {
			hsql.append(" and a.baseCustomsDeclaration.effective = ? ");
			parameters.add(true);
		} else if (iseffective == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql.append(" and (a.baseCustomsDeclaration.effective = ?  or a.baseCustomsDeclaration.effective is null) ");
			parameters.add(false);
		}

		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql.append(" and a.projectDept.code in(");
			for (int i = 0; i < deptCode.size(); i++) {
				if (deptCode.size() - 1 == i)
					hsql.append("?");
				else
					hsql.append("?,");
				parameters.add((String) deptCode.get(i));
			}
			hsql.append(") ");
		} else if (isDept && (deptCode == null)) {
			hsql.append(" and a.projectDept is null ");

		}

		hsql.append(" order by a.commSerialNo");
		System.out.println(hsql);

		return this.find(hsql.toString(), parameters.toArray());
	}

	/**
	 * 获得进出口报关单商品信息
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isProduct
	 *            是否是成品
	 * @param iseffective
	 *            是否生效
	 * @return 符合指定条件生效的报关单商品信息
	 */
	public List findImpExpCommInfo(Integer seqNum, String customer,
			String iEType, Date beginDate, Date endDate, boolean isProduct,
			int iseffective, boolean isDeclaration, boolean isDept,
			List deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.baseCustomsDeclaration "
				+ " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.projectDept "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (customer != null && !customer.trim().equals("")) {// 客户供应商
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (iEType != null && !iEType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(iEType));
		}
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);

		}
		if (beginDate != null && isDeclaration) { // 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (iseffective == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (iseffective == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?  or a.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}

		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code in(";
			for (int i = 0; i < deptCode.size(); i++) {
				if (deptCode.size() - 1 == i)
					hsql += "?";
				else
					hsql += "?,";
				parameters.add((String) deptCode.get(i));
			}
			hsql += ") ";
		} else if (isDept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";

		}

		hsql += " order by a.commSerialNo";
		System.out.println(hsql);

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获得进出口报关单商品信息
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isProduct
	 *            是否是成品
	 * @param iseffective
	 *            是否生效
	 * @return 符合指定条件生效的报关单商品信息 与findImpExpCommInfo()方法不一在可传入多个事业部条件
	 */
	public List findImpExpCommInfoMore(Integer seqNum, String customer,
			String iEType, Date beginDate, Date endDate, boolean isProduct,
			int iseffective, boolean isDeclaration, boolean isDept,
			String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.baseCustomsDeclaration "
				+ " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.projectDept "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (customer != null && !customer.trim().equals("")) {// 客户供应商
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (iEType != null && !iEType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(iEType));
		}
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);

		}
		if (beginDate != null && isDeclaration) { // 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (iseffective == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (iseffective == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?  or a.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}

		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ?";
			parameters.add(deptCode);
		} else if (isDept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}

		hsql += " order by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据类型得到明细
	 * 
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param iseffective
	 *            是否生效
	 * @return 在有效期内与指定的类型匹配生效的进出口商品明细
	 */
	public List findImpExpCommInfoByType(int iEType, Date beginDate,
			Date endDate, Boolean iseffective) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.baseCustomsDeclaration.impExpType=? ";
		parameters.add(Integer.valueOf(iEType));

		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (iseffective.equals(new Boolean(true))) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(iseffective);
		} else {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null) ";
			parameters.add(iseffective);
		}
		hsql += " order by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询进出口报关单商品信息
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isProduct
	 *            是否是成品
	 * @param iseffective
	 *            是否生效
	 * @param commName
	 *            商品名称
	 * @param tradeMode
	 *            贸易方式
	 * @param customsNo
	 *            报关单号
	 * @return 符合指定条件的进出口报关单商品信息
	 */
	public List findImpExpCommInfoOther(Integer seqNum, String customer,
			String iEType, Date declaraBeginDate, Date declaraEndDate,
			Date impexpBeginDate, Date impexpEndDate, boolean isProduct,
			Boolean iseffective, String commName, Trade tradeMode,
			String customsNo, String projectDeptName, boolean isDept,
			String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.projectDept "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (projectDeptName != null && !projectDeptName.equals("")) { // 事业部
			hsql += " and a.projectDept.name = ? ";
			parameters.add(projectDeptName);
		}
		if (commName != null && !commName.equals("")) {// 商品名称
			hsql += " and a.commName = ? ";
			parameters.add(commName);
		}
		if (tradeMode != null) { // 贸易方式
			hsql += " and a.baseCustomsDeclaration.tradeMode.code=? ";
			parameters.add(tradeMode.getCode());
		}
		if (customsNo != null && !customsNo.equals("")) { // 报关单号
			hsql += " and a.baseCustomsDeclaration.customsDeclarationCode=? ";
			parameters.add(customsNo);
		}
		if (customer != null && !customer.equals("")) {// 客户供应商
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (iEType != null && !iEType.equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(iEType));
		}
		// hwy2012-11-12
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		if (declaraBeginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(declaraBeginDate));
		}
		if (declaraEndDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(declaraEndDate));
		}

		if (impexpBeginDate != null) {
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(impexpBeginDate));
		}
		if (impexpEndDate != null) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(impexpEndDate));
		}

		if (iseffective.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(iseffective);
		}
		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ?";
			parameters.add(deptCode);
		} else if (isDept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}

		hsql += " order by a.baseCustomsDeclaration.serialNumber, a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	// /**
	// *查询报关单资料Dj
	// */
	// private String[] split(String sourceStr, String matchStr) {//字符串分离
	// if (sourceStr == null) {
	// return new String[0];
	// }
	// List<String> list = new ArrayList<String>();
	// while (sourceStr.indexOf(matchStr) != -1) {
	// String tempS = sourceStr.substring(0, sourceStr.indexOf(matchStr));
	// sourceStr = sourceStr.substring(sourceStr.indexOf(matchStr)
	// + matchStr.length(), sourceStr.length());
	// if ("".equals(tempS)) {
	// continue;
	// }
	// list.add(tempS);
	// }
	// list.add(sourceStr);
	// String[] str = new String[list.size()];
	// for (int i = 0; i < list.size(); i++) {
	// str[i] = list.get(i);
	// }
	// return str;
	// }
	// public List findImpExpComminfoDj(Integer type,Trade tradeMode,Integer
	// commName,Date beginDate,Date endDate,boolean iseffective
	// ,Map paraMap){
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql = "select a from CustomsDeclarationCommInfo as a";
	// hsql += " left outer join fetch a.unit";
	// hsql += " where a.baseCustomsDeclaration.company.id = ? ";
	// parameters.add(CommonUtils.getCompany().getId());
	// if(tradeMode != null){
	// hsql +=" and a.baseCustomsDeclaration.tradeMode = ?";
	// parameters.add(tradeMode);
	// }
	// if(commName != null){
	// hsql += " and a.commSerialNo = ?";
	// parameters.add(commName);
	// }
	// if(beginDate != null){
	// hsql +=" and a.baseCustomsDeclaration.declarationDate >= ?";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if(endDate != null){
	// hsql +=" and a.baseCustomsDeclaration.declarationDate <= ?";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// if(type != null){
	// hsql +=" and a.baseCustomsDeclaration.impExpFlag = ?";
	// parameters.add(type);
	// }
	// String customsDeclarationCode = (String) (paraMap
	// .get("customsDeclarationCode") == null ? "" : paraMap
	// .get("customsDeclarationCode"));// 报关单号
	// if (!customsDeclarationCode.trim().equals("")) {
	// String[] preCodes = null;// =
	// String tempPrecode = customsDeclarationCode.trim();
	// preCodes = tempPrecode.split(",");
	// preCodes = split(customsDeclarationCode, ",");
	// hsql += " and  a.baseCustomsDeclaration.customsDeclarationCode  in ( ";
	// for (int k = 0; k < preCodes.length; k++) {
	// if (k == preCodes.length - 1) {
	// hsql += " ? ) ";
	// } else {
	// hsql += " ? , ";
	// }
	// parameters.add(preCodes[k].trim());
	// System.out.println(preCodes[k].trim());
	// }
	//
	// }
	// hsql
	// +="order by a.baseCustomsDeclaration.customsDeclarationCode,a.commName";//原本在逻辑层中进行排序处理
	// return this.find(hsql,parameters.toArray());
	// }
	/**
	 * 电子帐册料件
	 * 
	 * @return 正在执行的电子帐册料件
	 */
	public List findEmsEdiImg(Integer seqNum) {
		if (getIsEmsH2kSend()) {
			List<Object> para = new ArrayList<Object>();
			String hsql = "select a from EmsHeadH2kImg a where a.sendState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(Integer.valueOf(SendState.SEND));
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());
		} else {
			List<Object> para = new ArrayList<Object>();
			String hsql = "select a from EmsHeadH2kImg a where a.emsHeadH2k.declareState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(DeclareState.PROCESS_EXE);
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());
		}
	}

	/**
	 * 查询电子帐册成品信息
	 * 
	 * @return 正在执行的电子帐册成品信息
	 */
	public List findEmsEdiExg(Integer seqNum) {
		if (getIsEmsH2kSend()) {
			List<Object> para = new ArrayList<Object>();
			String hsql = "from EmsHeadH2kExg a where a.sendState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(Integer.valueOf(SendState.SEND));
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			return this.find(hsql, para.toArray());
		} else {
			List<Object> para = new ArrayList<Object>();
			String hsql = "from EmsHeadH2kExg a where a.emsHeadH2k.declareState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(DeclareState.PROCESS_EXE);
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			return this.find(hsql, para.toArray());
		}
	}

	/**
	 * 查询电子帐册版本信息
	 * 
	 * @param obj
	 *            电子帐册成品
	 * @return 指定的电子帐册成品中的版本信息
	 */
	public List findEmsVersion(EmsHeadH2kExg obj) {
		return this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id =? and a.company= ?",
						new Object[] { obj.getId(), CommonUtils.getCompany() });
	}

	/**
	 * 出口数量来源于报关单
	 * 
	 */

	public List findEmsComminfo(Integer seqNum, Date beginDate, Date endDate,
			String version, String deptId) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (version != null && !version.equals("")) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}
		if (beginDate != null) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (deptId != null && !"".equals(deptId.trim())) {
			hsql += " and a.projectDept.id=?";
			parameters.add(deptId);
		}
		System.out.println("----sql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 出口数量来源于报关清单
	 */
	public List findBillListComminfo(String ptNo, Integer exgSeqNum,
			Date beginDate, Date endDate, String version, String deptId) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from AtcMergeBeforeComInfo as a";
		hsql += " where a.afterComInfo.billList.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.afterComInfo.billList.impExpType in (?,?,?,?,?,?) ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT); // 返工复出
		parameters.add(ImpExpType.BACK_FACTORY_REWORK); // 退厂返工
		parameters.add(ImpExpType.GENERAL_TRADE_EXPORT); // 一般贸易出口
		parameters.add(ImpExpType.EXPORT_MATERIAL_REBACK); // 修理物品复出
		if (ptNo != null && !"".equals(ptNo)) {
			hsql += " and a.materiel.ptNo=? ";
			parameters.add(ptNo);
		}
		if (exgSeqNum != null) {
			hsql += " and a.afterComInfo.emsSerialNo=? ";
			parameters.add(exgSeqNum);
		}
		if (version != null && !version.equals("")) {
			hsql += " and a.version=? ";
			parameters.add(Integer.valueOf(version));
		}
		if (beginDate != null) {// 清单录入日期
			hsql += " and a.afterComInfo.billList.createdDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (deptId != null && !"".equals(deptId.trim())) {
			hsql += " and a.projectDept.id=?";
			parameters.add(deptId);
		}
		System.out.println("----sql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询进出口报关单明细
	 * 
	 * @param isImport
	 *            是否是料件
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * 
	 * @param version版本
	 * 
	 * @param isDeclaration
	 *            是否按日期查询
	 * @param isDept
	 *            是否按部门查询
	 * @param deptCode
	 *            部门ID号
	 * @param isEffect
	 *            是否生效
	 * 
	 * @return 符合条件的进出口报关明细
	 */
	public List findImpExpCommInfoList(boolean isImport, Integer seqNum,
			String customer, String impExpType, Date beginDate, Date endDate,
			String version, boolean isDeclaration, boolean isDept,
			List deptCode, int isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.projectDept "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (version != null && !version.equals("")) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}

		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code in(";
			for (int i = 0; i < deptCode.size(); i++) {
				if (deptCode.size() - 1 == i)
					hsql += "?";
				else
					hsql += "?,";
				parameters.add((String) deptCode.get(i));
			}
			hsql += ") ";
		} else if (isDept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";

		}

		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {// 结转日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		/*
		 * hsql += " and a.baseCustomsDeclaration.effective = ? ";
		 * parameters.add(Boolean.valueOf(true));
		 */

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		hsql += " and a.baseCustomsDeclaration.impExpFlag != ?  ";
		parameters.add(ImpExpFlag.SPECIAL);

		// hsql += " order by
		// a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.customsDeclarationCode,a.commSerialNo
		// ";
		System.out.println("----sql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询进出口报关单明细
	 * 
	 * @param isImport
	 *            是否是料件
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param version
	 *            版本
	 * @return 符合条件的进出口报关明细
	 */
	public List findSpecialImpExpCommInfoList(boolean isImport, Integer seqNum,
			String customer, String impExpType, Date beginDate, Date endDate,
			String version, boolean isDeclaration, boolean isDept,
			List deptCode, int isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.projectDept "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.baseCustomsDeclaration.impExpFlag = ?  ";
		parameters.add(ImpExpFlag.SPECIAL);

		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (version != null && !version.equals("")) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}

		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code in (";
			for (int i = 0; i < deptCode.size(); i++) {
				if (i == deptCode.size() - 1)
					hsql += "?";
				else
					hsql += "?,";
				parameters.add((String) deptCode.get(i));
			}
			hsql += ")";
		} else if (isDept && (deptCode == null || deptCode.size() == 0)) {
			hsql += " and a.projectDept is null ";
		}

		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {// 结转日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		/*
		 * hsql += " and a.baseCustomsDeclaration.effective = ? ";
		 * parameters.add(Boolean.valueOf(true));
		 */

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}

		// hsql += " order by
		// a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.customsDeclarationCode,a.commSerialNo
		// ";
		System.out.println("----sql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询进出口报关单明细
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param version
	 *            版本
	 * @param deptId
	 *            部门ID号
	 * 
	 * @return 符合条件的进出口报关明细
	 */
	public List findImpExpCommInfoListById(Integer seqNum, Date beginDate,
			Date endDate, String version, String deptId, Integer impExpType) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left  join fetch a.complex "
				+ " left  join fetch a.legalUnit "
				+ " left  join fetch a.secondLegalUnit "
				+ " left  join fetch a.country " + " left  join fetch a.uses "
				+ " left  join fetch a.levyMode "
				+ " left  join fetch a.projectDept "
				+ " left  join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (version != null && !version.equals("")) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}

		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType = ?";
			parameters.add(impExpType);
		}

		// 事业部
		if (deptId != null) { // 区分事业部: A
			hsql += " and a.projectDept.id = ?";
			parameters.add(deptId);
		}
		// if (isDept && deptId != null) { // 区分事业部: A
		// hsql += " and a.projectDept.id = ?";
		// parameters.add(deptId);
		// } else if (isDept && (deptId == null)) {
		// hsql += " and a.projectDept is null ";
		// }

		// if (customer != null && !customer.trim().equals("")) {
		// hsql += " and a.baseCustomsDeclaration.customer.id=? ";
		// parameters.add(customer);
		// }
		// if (impExpType != null && !impExpType.trim().equals("")) {
		// hsql += " and a.baseCustomsDeclaration.impExpType=? ";
		// parameters.add(Integer.valueOf(impExpType));
		// }
		if (beginDate != null) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		// if (beginDate != null && !isDeclaration) {// 结转日期
		// hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null && !isDeclaration) {
		// hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }

		hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
		parameters.add(true);

		System.out.println("----sql:" + hsql);
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 查询进出口报关单明细
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param version
	 *            版本
	 * @param deptId
	 *            部门ID号
	 * 
	 * @return 符合条件的进出口报关明细
	 */
	public List findImpExpCommInfoListById0(Integer seqNum, Date beginDate,
			Date endDate, String version, String deptId, Integer impExpType) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left  join fetch a.complex "
				+ " left  join fetch a.legalUnit "
				+ " left  join fetch a.secondLegalUnit "
				+ " left  join fetch a.country " + " left  join fetch a.uses "
				+ " left  join fetch a.levyMode "
				+ " left  join fetch a.projectDept "
				+ " left  join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		// if (version != null && !version.equals("")) {
		// hsql += " and a.version=? ";
		// parameters.add(version);
		// }

		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType = ?";
			parameters.add(impExpType);
		}

		// 事业部
		if (deptId != null) { // 区分事业部: A
			hsql += " and a.projectDept.id = ?";
			parameters.add(deptId);
		}
		// if (isDept && deptId != null) { // 区分事业部: A
		// hsql += " and a.projectDept.id = ?";
		// parameters.add(deptId);
		// } else if (isDept && (deptId == null)) {
		// hsql += " and a.projectDept is null ";
		// }

		// if (customer != null && !customer.trim().equals("")) {
		// hsql += " and a.baseCustomsDeclaration.customer.id=? ";
		// parameters.add(customer);
		// }
		// if (impExpType != null && !impExpType.trim().equals("")) {
		// hsql += " and a.baseCustomsDeclaration.impExpType=? ";
		// parameters.add(Integer.valueOf(impExpType));
		// }
		if (beginDate != null) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		// if (beginDate != null && !isDeclaration) {// 结转日期
		// hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
		// parameters.add(CommonUtils.getBeginDate(beginDate));
		// }
		// if (endDate != null && !isDeclaration) {
		// hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
		// parameters.add(CommonUtils.getEndDate(endDate));
		// }

		hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
		parameters.add(true);

		System.out.println("----sql:" + hsql);

		// String hql = "select count(*) from CustomsDeclarationCommInfo";
		// Query query = createQuery(hql);
		// Integer lsResultSize = ((Long)query.uniqueResult()).intValue();
		// Integer allCount = lsResultSize;
		// Integer allPage = 0;
		// List findByPageList = null;
		// if (allCount > 0) {
		// allPage = allCount % 10000 == 0 ? allCount / 100 : (allCount / 10000
		// + 1);
		//
		// }
		// for(int i = 1; i <= allPage; i++){
		// findByPageList = queryListObjectAllForPage(hsql,10000,i);
		// }
		return this.find(hsql, parameters.toArray());
		// return findByPageList;
	}

	// //分页查询
	// public List<Object> queryListObjectAllForPage(String queryString,int
	// pageSize,int page){
	// Session session =
	// this.getHibernateTemplate().getSessionFactory().openSession();
	// Query query = session.createQuery(queryString);
	// query.setFirstResult((page-1)*pageSize);
	// query.setMaxResults(pageSize);
	// List<Object> list= (List<Object>)query.list();
	// session.close();
	// return list;
	// }

	/**
	 * 查询进出口报关单明细
	 * 
	 * @param isImport
	 *            是否是料件
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param version
	 *            版本
	 * @return 符合条件的进出口报关明细
	 */
	public List findSpecialImpExpCommInfoListById(boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, String version, boolean isDeclaration,
			boolean isDept, String deptId, int isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.projectDept "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.baseCustomsDeclaration.impExpFlag = ?  ";
		parameters.add(ImpExpFlag.SPECIAL);

		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (version != null && !version.equals("")) {
			hsql += " and a.version=? ";
			parameters.add(version);
		}

		// 事业部
		if (isDept && deptId != null) { // 区分事业部: A
			hsql += " and a.projectDept.id = ?";
			parameters.add(deptId);
		} else if (isDept && (deptId == null)) {
			hsql += " and a.projectDept is null ";
		}

		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && !isDeclaration) {// 结转日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		/*
		 * hsql += " and a.baseCustomsDeclaration.effective = ? ";
		 * parameters.add(Boolean.valueOf(true));
		 */

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}

		// hsql += " order by
		// a.baseCustomsDeclaration.declarationDate,a.baseCustomsDeclaration.customsDeclarationCode,a.commSerialNo
		// ";
		System.out.println("----sql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isImport
	 *            是否是料件
	 * @return 生效的进出口报关商品
	 */
	public List findCustomsDeclarationCommInfo(boolean isImport) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commSerialNo,a.commName,a.commSpec,a.baseCustomsDeclaration.effective from CustomsDeclarationCommInfo as a ";
		hsql += " left outer join a.complex ";
		hsql += " where ";
		if (isImport) {// 料件
			hsql += "  a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			// ---
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		} else {// 成品
			hsql += "  a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		hsql += " and a.commSerialNo is not null and a.baseCustomsDeclaration.company.id=? and a.baseCustomsDeclaration.cancel = ? order by a.commSerialNo";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Boolean.valueOf(false));
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的商品来自进出口标志
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 在有效期内符合指定的进出口标志且已经生效的报关商品信息
	 */
	public List findCustomsDeclarationCommInfo(Integer impExpFlag,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commSerialNo,a.commName from CustomsDeclarationCommInfo as a ";
		hsql += " left outer join a.complex ";
		hsql += " where ";
		if (impExpFlag.equals(ImpExpFlag.IMPORT)) {// 料件
			hsql += "  a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.MATERIAL_EXCHANGE);
			parameters.add(ImpExpType.MATERIAL_REOUT);
		}
		if (impExpFlag.equals(ImpExpFlag.EXPORT)) {// 成品
			hsql += "  a.baseCustomsDeclaration.impExpType in (?,?) ";
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		// if (impExpType != null && !impExpType.trim().equals("")) {
		// hsql += " and a.baseCustomsDeclaration.impExpType=? ";
		// parameters.add(impExpType);
		// }
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " and a.baseCustomsDeclaration.company.id=? and a.baseCustomsDeclaration.effective = ? order by a.commSerialNo";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Boolean.valueOf(true));
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isImport
	 *            是否是料件
	 * @return 所有报关的报关单中的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from CustomsDeclaration as a where  "
				+ " a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {// 料件
			hsql += " and  a.impExpType in (?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		} else {// 成品
			hsql += " and  a.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		return this.find(hsql, parameters.toArray());
	}

	/*
	 * and" + " b.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState = ?
	 * and b.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState = ?
	 */

	/**
	 * 查询成品信息
	 * 
	 * @param seqNum
	 *            成品序号
	 * @return 与指定的序号匹配且正在执行的成品信息
	 */
	public List findExgByImg(String seqNum) {
		return this
				.find("select a from EmsHeadH2kExg a where a.emsHeadH2k.historyState=? and a.emsHeadH2k.declareState=? and a.company.id=? and a.id in "
						+ "(select b.emsHeadH2kVersion.emsHeadH2kExg.id from EmsHeadH2kBom b where b.seqNum = ? and b.emsHeadH2kVersion.emsHeadH2kExg.company.id = ?)",
						new Object[] { new Boolean(false),
								DeclareState.PROCESS_EXE,
								CommonUtils.getCompany().getId(), seqNum,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询所有单耗
	 * 
	 * @param seqNum
	 *            料件序号
	 * @return 正在执行状态的成品的料件耗用情况
	 */
	public List findBomBySeqNum(Integer[] imgSeqNum) {
		String hsql = "";
		List<Object> parameters = new ArrayList<Object>();
		if (getIsEmsH2kSend()) {
			hsql = "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.emsHeadH2kExg.name,"
					+ " a.emsHeadH2kVersion.emsHeadH2kExg.spec,a.unitWear,a.wear,a.emsHeadH2kVersion.version,a.name,a.seqNum "
					+ " from EmsHeadH2kBom a left join a.emsHeadH2kVersion b "
					+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState=? "
					+ " and a.sendState=? and a.company.id=? ";
			// parameters.add(Integer.valueOf(seqNum));
			parameters.add(new Boolean(false));
			parameters.add(Integer.valueOf(SendState.SEND));
			parameters.add(CommonUtils.getCompany().getId());
			if (imgSeqNum != null && imgSeqNum.length > 0) {
				hsql += " and ( ";
				for (int i = 0; i < imgSeqNum.length; i++) {
					if (i == imgSeqNum.length - 1) {
						hsql += " a.seqNum = ?  ";
					} else {
						hsql += " a.seqNum = ?  or ";
					}
					parameters.add(Integer.valueOf(imgSeqNum[i]));
				}
				hsql += " ) ";
			}
			hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.seqNum,a.emsHeadH2kVersion.version";
			return this.find(hsql, parameters.toArray());
		} else {
			hsql = "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.emsHeadH2kExg.name,"
					+ " a.emsHeadH2kVersion.emsHeadH2kExg.spec,a.unitWear,a.wear,a.emsHeadH2kVersion.version,a.name,a.seqNum "
					+ " from EmsHeadH2kBom a  left join a.emsHeadH2kVersion b "
					+ " where  a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState=? "
					+ "and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState=? and a.company.id=? ";
			// parameters.add(Integer.valueOf(seqNum));
			parameters.add(new Boolean(false));
			parameters.add(DeclareState.PROCESS_EXE);
			parameters.add(CommonUtils.getCompany().getId());
			if (imgSeqNum != null && imgSeqNum.length > 0) {
				hsql += " and ( ";
				for (int i = 0; i < imgSeqNum.length; i++) {
					if (i == imgSeqNum.length - 1) {
						hsql += " a.seqNum = ?  ";
					} else {
						hsql += " a.seqNum = ?  or ";
					}
					parameters.add(Integer.valueOf(imgSeqNum[i]));
				}
				hsql += " ) ";
			}
			hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.seqNum,a.emsHeadH2kVersion.version";
			return this.find(hsql, parameters.toArray());
		}
	}

	/**
	 * 查询成品的总数量
	 * 
	 * @param seqNum
	 *            成品序号
	 * @param version
	 *            版本
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 符合指定条件的成品的总数量
	 */
	public Double findCommAmount(Integer seqNum, String version,
			Date beginDate, Date endDate, Boolean isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ "where a.baseCustomsDeclaration.impExpType in (?,?,?) and a.baseCustomsDeclaration.company.id = ? "
				+ " and a.version=? and a.commSerialNo = ?";
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(version);
		parameters.add(seqNum);
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += "and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}

		return Double.valueOf(0);
	}

	/**
	 * 取得退厂返工的成品的总数量
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param version
	 *            版本
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 符合指定条件的退厂返工的成品的总数量
	 */
	public Double findCommAmount2(Integer seqNum, String version,
			Date beginDate, Date endDate, Boolean isEffect) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ "where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? "
				+ "and a.version=? and a.commSerialNo = ?";
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(version);
		parameters.add(seqNum);
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 计算单损耗
	 * 
	 * @param exg
	 *            成品信息
	 * @param seqNum
	 *            成品序号
	 * @return 指定的成品耗用料件的情况
	 */
	public List jisuanCm(EmsHeadH2kExg exg, String seqNum) {
		return this
				.find("select a.unitWear,a.wear from EmsHeadH2kBom a"
						+ " where a.emsHeadH2kVersion.emsHeadH2kExg.id = ? and a.seqNum = ? ",
						new Object[] { exg.getId(), seqNum });
	}

	/*
	 * public EmsHeadH2kVersion findEmsHeadH2kVersion(EmsHeadH2kExg exg, Integer
	 * version) { List list = this .find("select a from EmsHeadH2kVersion a" + "
	 * where a.emsHeadH2kExg.id = ? and a.version = ? ", new Object[] {
	 * exg.getId(), version }); if (list != null && list.size()>0){ return
	 * (EmsHeadH2kVersion) list.get(0); } return null; }
	 */

	/**
	 * 查询成品的单个耗用
	 * 
	 * @param emsH2k
	 *            电子帐册表体
	 * @param seqNum
	 *            成品序号
	 * @param version
	 *            版本
	 * @return 指定条件匹配的单个成品的耗用
	 */
	public List findUnitNum(EmsHeadH2k emsH2k, Integer seqNum, Integer version) {
		return this
				.find("select a.unitWear,a.wear,a.seqNum from EmsHeadH2kBom a"
						+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = ? "
						+ " and a.emsHeadH2kVersion.version = ?", new Object[] {
						emsH2k.getId(), seqNum, version });
	}

	public EmsHeadH2kVersion findEmsHeadH2kVersion(Integer seqNum,
			Integer version) {
		if (getIsEmsH2kSend()) {
			List list = this
					.find("select a from EmsHeadH2kVersion a"
							+ " where a.emsHeadH2kExg.seqNum = ? "
							+ " and a.version = ? "
							+ " and a.emsHeadH2kExg.emsHeadH2k.company.id=? "
							+ " and a.emsHeadH2kExg.sendState=? "
							+ " and a.emsHeadH2kExg.emsHeadH2k.historyState=? ",
							new Object[] { seqNum, version,
									CommonUtils.getCompany().getId(),
									Integer.valueOf(SendState.SEND),
									new Boolean(false) });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kVersion) list.get(0);
			}
			return null;
		} else {
			List list = this.find("select a from EmsHeadH2kVersion a"
					+ " where a.emsHeadH2kExg.seqNum = ? "
					+ " and a.version = ? "
					+ " and a.emsHeadH2kExg.emsHeadH2k.company.id=? "
					+ " and a.emsHeadH2kExg.emsHeadH2k.declareState=? "
					+ " and a.emsHeadH2kExg.emsHeadH2k.historyState=? ",
					new Object[] { seqNum, version,
							CommonUtils.getCompany().getId(),
							DeclareState.PROCESS_EXE, new Boolean(false) });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2kVersion) list.get(0);
			}
			return null;
		}
	}

	/**
	 * 查询耗用料件的总数量
	 * 
	 * @param tenSeqNum
	 *            十位备案序号
	 * @return 与十位备案序号匹配的成品耗用料件的总数量
	 */
	public List findSumAmount(Integer tenSeqNum) {
		return this
				.find("select a.materiel.amount,a.materiel.unitConvert from InnerMergeData a "
						+ "where a.hsAfterTenMemoNo=? and a.company.id=? and a.imrType=?",
						new Object[] { tenSeqNum,
								CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL });
	}

	/**
	 * 查询耗用料件的总数量(汇总)
	 * 
	 * @param tenSeqNum
	 *            十位备案序号
	 * @return 与十位备案序号匹配的成品耗用料件的总数量
	 */
	public List findSumAmount() {
		return this
				.find("select a.hsAfterTenMemoNo, sum(a.materiel.amount/a.materiel.unitConvert) from InnerMergeData a"
						+ " where a.company.id=? and a.imrType=?"
						+ " group by a.hsAfterTenMemoNo",
						new Object[] { CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL });
	}

	/**
	 * 查询工厂的物料耗用来自十位备案序号
	 * 
	 * @param tenSeqNum
	 *            十位备案序号
	 * @return 符合条件的工厂物料的耗用相关情况
	 */
	public List findMaterielAmountBySeqNum(Integer tenSeqNum) {
		return this
				.find("select a.ptNo,a.factoryName,a.factorySpec,a.calUnit.name,a.unitConvert,a.amount from Materiel a"
						+ " left outer join  a.calUnit "
						+ " where a.id in (select b.materiel.id from InnerMergeData b where b.hsAfterTenMemoNo=? and b.company.id=? and b.imrType=?)"
						+ " and a.company.id=? and a.scmCoi.coiProperty=?",
						new Object[] { tenSeqNum,
								CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL,
								CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL });
	}

	/**
	 * 保存报关商品信息
	 * 
	 * @param obj
	 *            报关商品信息
	 */
	public void SaveCustomsFromMateriel(CustomsFromMateriel obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存其他参数设置资料
	 * 
	 * @return CompanyOther 其他参数设置资料
	 */
	public CompanyOther saveCommonUtilsOther() {
		CompanyOther other = super.findCompanyOther();
		CommonUtils.setOther(other);
		return other;
	}

	/**
	 * 查询集装箱号来自报关清单
	 * 
	 * @param billList
	 *            报关清单
	 * @return 指定报关清单中的集装箱号
	 */
	public List findjzno(ApplyToCustomsBillList billList) {
		return this
				.find("select distinct a.impExpCommodityInfo.impExpRequestBill.containerCode from MakeApplyToCustoms a "
						+ " where a.atcMergeBeforeComInfo.afterComInfo.billList.id = ? and a.company.id = ?",
						new Object[] { billList.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得电子帐册号
	 * 
	 * @return 正在执行状态的电子帐册号
	 */
	public EmsHeadH2k getEmsNo() {
		if (getIsEmsH2kSend()) {
			List list = this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.historyState=?",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false) });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2k) list.get(0);
			}
			return null;
		} else {
			List list = this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.historyState=? and a.declareState = ?",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false),
									DeclareState.PROCESS_EXE });
			if (list != null && list.size() > 0) {
				return (EmsHeadH2k) list.get(0);
			}
			return null;
		}
	}

	/**
	 * 取得报关单总金额
	 * 
	 * @param type
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @param tradeName
	 *            贸易方式
	 * @return 符合条件的生效的报关单总金额
	 */
	public Double getSumMoney(int type, Date beginDate, Date endDate,
			int isEffect, String tradeName, Integer seqNum,
			boolean isDeclaration, boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice) from CustomsDeclarationCommInfo a "
				+ "where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? ";
		parameters.add(type);
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		if (tradeName != null && !"".equals(tradeName)) {
			hsql += " and a.baseCustomsDeclaration.tradeMode.name = ?";
			parameters.add(tradeName);
		}
		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ?";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}

		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double getSumCommAmount(int type, Date beginDate, Date endDate,
			int isEffect, String tradeName, Integer seqNum,
			boolean isDeclaration, boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ "where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? ";
		parameters.add(type);
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		if (tradeName != null && !"".equals(tradeName)) {
			hsql += " and a.baseCustomsDeclaration.tradeMode.name = ?";
			parameters.add(tradeName);
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ?";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}
		// 贸易方式
		// if (tradeCodes != null && tradeCodes.length > 0) {
		// hsql += " and ( ";
		// for (int i = 0; i < tradeCodes.length; i++) {
		// if (i == tradeCodes.length - 1) {
		// hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
		// } else {
		// hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
		// }
		// parameters.add(tradeCodes[i]);
		// }
		// hsql += " ) ";
		// }
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public Double getSumCommAmountForInnerUsed(int type, Date beginDate,
			Date endDate, int isEffect, String tradeName, Integer seqNum,
			boolean isDeclaration, boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ "where  a.baseCustomsDeclaration.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		// if (tradeName != null && !"".equals(tradeName)) {
		// hsql += " and a.baseCustomsDeclaration.tradeMode.name = ?";
		// parameters.add(tradeName);
		// }
		hsql += " and a.baseCustomsDeclaration.tradeMode.code in (?,? )";
		parameters.add("0245");
		parameters.add("0644");
		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ?";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}

		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	public List<Object[]> countSeqNumImport(Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration, boolean isDept,
			String deptCode, boolean isCountPrice) {
		List<Object> parameters = new ArrayList<Object>();
		StringBuilder hsql = new StringBuilder();

		if (isCountPrice) {
			hsql.append("select a.commSerialNo, sum(a.dollarTotalPrice)/sum(a.commAmount)"
					+ " from CustomsDeclarationCommInfo a"
					+ " where a.baseCustomsDeclaration.company.id = ?");
		} else {
			hsql.append("select a.commSerialNo, a.baseCustomsDeclaration.impExpType,"
					+ " sum(a.commAmount), sum(a.dollarTotalPrice)"
					+ " from CustomsDeclarationCommInfo a"
					+ " where a.baseCustomsDeclaration.company.id = ?");
		}

		parameters.add(CommonUtils.getCompany().getId());

		{
			hsql.append(" and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?)");
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		}

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql.append(" and (a.baseCustomsDeclaration.effective = ?)");
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql.append(" and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )");
			parameters.add(false);
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql.append(" and a.baseCustomsDeclaration.impExpDate>=?");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql.append(" and a.baseCustomsDeclaration.impExpDate<=?");
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql.append(" and a.baseCustomsDeclaration.declarationDate>=?");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql.append(" and a.baseCustomsDeclaration.declarationDate<=?");
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql.append(" and a.projectDept.code = ?");
			parameters.add(deptCode);
		} else if (isDept && (deptCode == null)) {
			hsql.append(" and a.projectDept is null");
		}
		if (isCountPrice) {
			hsql.append(" group by a.commSerialNo");
		} else {
			hsql.append(" group by a.commSerialNo, a.baseCustomsDeclaration.impExpType");
		}

		List list = this.find(hsql.toString(), parameters.toArray());
		return list;
	}

	public List<Object[]> findPriceAndAmount(Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration, boolean isDept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		StringBuilder hsql = new StringBuilder();
		// （金额 = 期初金额+直接进口金额+结转进口金额+海关批准内销金额-退料出口金额）（数量
		// =期初数量+直接进口数量+结转进口数量+海关批准内销数量-退料出口数量）
		hsql.append("select a.commSerialNo, sum(case c.impExpType "
				+ "when 0 then a.dollarTotalPrice "
				+ "when 1 then a.dollarTotalPrice "
				+ "when 21 then a.dollarTotalPrice "
				+ "when 17 then a.dollarTotalPrice "
				+ "else 0 - a.dollarTotalPrice " + "end) a1,"
				+ "sum(case c.impExpType " + "when 0 then a.commAmount "
				+ "when 1 then a.commAmount " + "when 21 then a.commAmount "
				+ "when 17 then a.commAmount " + "else 0 - a.commAmount "
				+ "end) a2 ");
		hsql.append(" from CustomsDeclarationCommInfo a"
				+ " left join customsDeclaration  c on a.customsDeclaration = c.id"
				+ " left join company  com on c.coid = com.id"
				+ " left join projectDept  p on a.projectDept = p.id"
				+ " where com.id = ?");

		parameters.add(CommonUtils.getCompany().getId());

		{
			hsql.append(" and c.impExpType in (?,?,?,?,?)");
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		}

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql.append(" and (c.effective = ?)");
			parameters.add(1);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql.append(" and (c.effective = ? or c.effective is null )");
			parameters.add(0);
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql.append(" and c.impExpDate>=?");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql.append(" and c.impExpDate<=?");
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql.append(" and c.declarationDate>=?");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql.append(" and c.declarationDate<=?");
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql.append(" and p.code = ?");
			parameters.add(deptCode);
		} else if (isDept && (deptCode == null)) {
			hsql.append(" and p is null");
		}
		hsql.append(" group by a.commSerialNo");

		// List list = this.find(hsql.toString(), parameters.toArray());
		List list = this.findListBySQL(hsql.toString(), null,
				parameters.toArray(), -1, -1);
		return list;
	}

	/*
	 *//**
	 * 取得报关单总金额
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @param tradeName
	 *            贸易方式
	 * @return 在有效期内与指定的贸易方式匹配生效的报关单的总金额
	 */
	/*
	 * public Double getSumMoneyByImpExpDate(Date beginDate, Date endDate,
	 * Boolean isEffect,String tradeName) { List<Object> parameters = new
	 * ArrayList<Object>(); String hsql = "select sum(a.dollarTotalPrice) from
	 * CustomsDeclarationCommInfo a " + "where
	 * a.baseCustomsDeclaration.company.id = ? ";
	 * parameters.add(CommonUtils.getCompany().getId()); if (isEffect != null &&
	 * isEffect.equals(new Boolean(true))) { hsql += " and
	 * (a.baseCustomsDeclaration.effective = ?) "; parameters.add(isEffect); }
	 * if (tradeName != null && !"".equals(tradeName)){ hsql +=" and
	 * a.baseCustomsDeclaration.tradeMode.name = ?"; parameters.add(tradeName);
	 * } if (beginDate != null) { hsql += " and
	 * a.baseCustomsDeclaration.impExpDate>=? "; parameters.add(beginDate); } if
	 * (endDate != null) { hsql +=
	 * " and a.baseCustomsDeclaration.impExpDate<=? "; parameters.add(endDate);
	 * } List list = this.find(hsql, parameters.toArray()); if (list != null &&
	 * list.get(0) != null) { return (Double) list.get(0); } return
	 * Double.valueOf(0); }
	 */

	public Double getSumMoneyByDeclaretionDate(Date beginDate, Date endDate,
			Boolean isEffect, String tradeName, Integer impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice) from CustomsDeclarationCommInfo a "
				+ "where a.baseCustomsDeclaration.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (tradeName != null && !"".equals(tradeName)) {
			hsql += " and a.baseCustomsDeclaration.tradeMode.name = ?";
			parameters.add(tradeName);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag = ? ";
			parameters.add(impExpFlag.intValue());
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 取得报关单总金额
	 * 
	 * @param type
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param impExpFlag
	 *            进出口标志
	 * @return 在有效期内与指定进出口类型匹配的进出口报关单总金额
	 */
	public Double getSumMoney(int type, Date beginDate, Date endDate,
			Integer impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice) from CustomsDeclarationCommInfo a "
				+ "where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? "
				+ "and a.baseCustomsDeclaration.impExpFlag= ? ";
		parameters.add(type);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(impExpFlag);
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 查询电子帐册成品信息来自成品序号
	 * 
	 * @param emsHead
	 *            电子帐册表头
	 * @param seqNum
	 *            成品序号
	 * @return 与成品序号匹配的电子帐册成品信息
	 */
	public EmsHeadH2kExg getEmsHeadH2kExgBySeqNum(EmsHeadH2k emsHead,
			String seqNum) {
		List list = this
				.find("select a from EmsHeadH2kExg a where a.emsHeadH2k.id = ? and a.seqNum = ?",
						new Object[] { emsHead.getId(), Integer.valueOf(seqNum) });
		if (list != null && list.size() > 0) {
			return (EmsHeadH2kExg) list.get(0);
		}
		return null;
	}

	/**
	 * 取得总金额
	 * 
	 * @param obj
	 *            报关单内容
	 * @return 指定报关单的总金额
	 */
	public List getTotalMoney(CustomsDeclaration obj) {
		return this.find(
				"select a from CustomsDeclarationCommInfo a where a.company.id = ? and "
						+ " a.baseCustomsDeclaration.id = ?", new Object[] {
						CommonUtils.getCompany().getId(), obj.getId() });
		// if (list.size() > 0 && list.get(0) != null) {
		// return (Double) list.get(0);
		// }
		// return Double.valueOf(0);
	}

	/**
	 * 报关单的总份数
	 * 
	 * @param obj
	 *            报关单内容
	 * @return 指定的报关商品信息匹配的报关单的总份数
	 */
	public Integer getTotalNum(CustomsDeclaration obj) {
		List list = this.find(
				"select count(*) from CustomsDeclarationCommInfo a where a.company.id = ? and "
						+ " a.baseCustomsDeclaration.id = ?", new Object[] {
						CommonUtils.getCompany().getId(), obj.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询物料内容来自集装箱
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 生效的进出口申请单
	 */
	public List findMaterielFromContainer(int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from ImpExpRequestBill a  "
				+ " where a.company.id = ? and a.isAvailability = ? ";// 抓取所有
		// Edit
		// by
		// xxm
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(true);

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.billNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 取得归并前的商品信息
	 * 
	 * @param impExpCommodityInfo
	 *            进出口商品信息
	 * @return 归并前的商品信息
	 */
	public AtcMergeBeforeComInfo getAtcMergeBeforeComInfoByMaterielInfo(
			ImpExpCommodityInfo impExpCommodityInfo) {
		List list = this
				.find("select a.atcMergeBeforeComInfo from MakeApplyToCustoms a where a.company.id = ? "
						+ " and a.impExpCommodityInfo.id = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								impExpCommodityInfo.getId() });
		if (list != null && list.size() > 0) {
			return (AtcMergeBeforeComInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 取得报关商品信息来自归并后的商品信息
	 * 
	 * @param obj
	 *            归并后的商品信息
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 在有效期内报关商品信息
	 */
	public CustomsDeclarationCommInfo getCustomsDeclarationCommInfoByBefo(
			AtcMergeAfterComInfo obj, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.customsInfo from MakeListToCustoms a where a.company.id = ?"
				+ " and a.atcMergeAfterComInfo.id = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(obj.getId());
		if (beginDate != null) {
			hsql += " and a.customsInfo.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.customsInfo.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.size() > 0) {
			return (CustomsDeclarationCommInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 查询电子帐册Bom信息
	 * 
	 * @return 电子帐册Bom信息
	 */
	public List findEmsHeadH2kBom() {
		List list = this.find(
				" select a from EmsHeadH2kBom a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询电子帐册表头的版本信息
	 * 
	 * @return 电子帐册版本信息
	 */
	public List findEmsHeadH2kVersion() {
		List list = this.find(
				" select a from EmsHeadH2kVersion a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询电子帐册的成品内容
	 * 
	 * @return 电子帐册的成品内容
	 */
	public List findEmsHeadH2kExg() {
		List list = this.find(" select a from EmsHeadH2kExg a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询电子帐册的料件内容
	 * 
	 * @return 电子帐册的成品内容
	 */
	public List findEmsHeadH2kImg(String emsNo) {
		if (getIsEmsH2kSend()) {
			List list = this.find("select a from EmsHeadH2kImg a "
					+ " where a.sendState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.emsHeadH2k.emsNo = ? "
					+ " and a.emsHeadH2k.company.id=?" + " order by a.seqNum",
					new Object[] { Integer.valueOf(SendState.SEND),
							new Boolean(false), emsNo,
							CommonUtils.getCompany().getId() });
			return list;
		} else {
			List list = this.find("select a from EmsHeadH2kImg a "
					+ " where a.emsHeadH2k.declareState=? "
					+ " and a.emsHeadH2k.historyState=? "
					+ " and a.emsHeadH2k.emsNo = ? "
					+ " and a.emsHeadH2k.company.id=?" + " order by a.seqNum",
					new Object[] { DeclareState.PROCESS_EXE,
							new Boolean(false), emsNo,
							CommonUtils.getCompany().getId() });
			return list;
		}
	}

	/**
	 * 查询电子帐册的表头信息
	 * 
	 * @return 电子帐册的表头信息
	 */
	public List findEmsHeadH2k() {
		List list = this.find(" select a from EmsHeadH2k a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询报关单商品信息
	 * 
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfo() {
		List list = this.find(
				" select a from CustomsDeclarationCommInfo a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	public void savaimpExpCommodityInfo(ImpExpCommodityInfo obj) {
		this.saveOrUpdate(obj);
	}

	public List findCustomsDeclarationByMateriel(Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclaration a "
				+ " where   a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.emsHeadH2k ,a.serialNumber ";
		return this.find(hsql, parameters.toArray());
	}

	public List findCustomsDeclarationContainer(Date beginDate, Date endDate,
			int index, int length, String property, Object value, Boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationContainer a"
				+ " where a.baseCustomsDeclaration.impExpType = ? and a.company.id = ? ";
		parameters.add(ImpExpType.DIRECT_IMPORT);
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		hsql += " order by a.containerCode";
		return this.findPageList(hsql, parameters.toArray(), index, length);
	}

	public List findMakeListToCustoms(CustomsDeclaration customs) {
		return this
				.find("select a from MakeListToCustoms a where a.customsInfo.baseCustomsDeclaration.id = ? and a.company.id = ?",
						new Object[] { customs.getId(),
								CommonUtils.getCompany().getId() });
	}

	public List findMakeApplyToCustoms(AtcMergeAfterComInfo afterInfo) {
		return this
				.find("select a.impExpCommodityInfo from MakeApplyToCustoms a where a.atcMergeBeforeComInfo.afterComInfo.id = ? "
						+ " and a.company.id = ?",
						new Object[] { afterInfo.getId(),
								CommonUtils.getCompany().getId() });
	}

	public List findCustomsFromMateriel(CustomsDeclaration customs) {
		return this
				.find("select a.materiel,b from CustomsFromMateriel a,CustomsDeclarationCommInfo b where b.id=a.infoid and "
						+ " b.baseCustomsDeclaration.id = ? and a.materiel.id is not null and a.company.id = ?",
						new Object[] { customs.getId(),
								CommonUtils.getCompany().getId() });
	}

	// 报文使用
	public List findApplyToCustomsBillListToBaowen(ApplyToCustomsBillList head) {
		return this
				.find("select a from AtcMergeBeforeComInfo a where a.afterComInfo.billList.id=? and a.company.id= ?  "
						+ " order by a.serialNo ",// "
						// a.afterComInfo.emsSerialNo
						// ,
						// a.salesCountry.code
						// ",
						new Object[] { head.getId(),
								CommonUtils.getCompany().getId() });
	}

	public List findEmsEdiMergerExgBomByBom(EmsEdiMergerHead head, String ptNo,
			String version) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.emsEdiMergerVersion.emsEdiMergerBefore.seqNum,a.emsEdiMergerVersion.version,a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo,"
				+ "a.emsEdiMergerVersion.emsEdiMergerBefore.name,a.emsEdiMergerVersion.emsEdiMergerBefore.spec,"
				+ " a.unitWaste,a.waste from EmsEdiMergerExgBom a where a.ptNo = ? "
				+ " and a.emsEdiMergerVersion.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.id = ? "
				+ " and a.modifyMark in('0','1') ";
		parameters.add(ptNo);
		parameters.add(head.getId());

		if (version != null) {
			hsql += " and a.emsEdiMergerVersion.version=?  ";
			parameters.add(Integer.valueOf(version));
		}

		if (getIsMergerSend()) {
			hsql += " and a.sendState = ? ";
			parameters.add(Integer.valueOf(SendState.SEND));
		}
		hsql += " order by a.emsEdiMergerVersion.emsEdiMergerBefore.ptNo, a.emsEdiMergerVersion.version";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找账册成品的单耗
	 * 
	 * @param head
	 * @param imgSeqNum
	 * @return
	 */
	public List<Object[]> findEmsHeadH2kBomUnitWear(EmsHeadH2k head,
			Integer imgSeqNum) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as cpNum,"
				+ " a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,a.unitWear as unitWear, a.wear as wear"
				+ " from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";

		parameters.add(head.getId());
		if (getIsEmsH2kSend()) {
			hsql += " and a.sendState = ? ";
			parameters.add(Integer.valueOf(SendState.SEND));
		}
		if (imgSeqNum != null) {
			hsql += " and a.seqNum = ? ";
			parameters.add(imgSeqNum);
		}
		List<Object[]> list = find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 根据报关单类型统计数量
	 * 
	 * @return
	 */
	public List findCustomsDeclarationCommInfoAmount(EmsHeadH2k head,
			Date beginDate, Date endDate, int isEffect, boolean isDeclaration,
			boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select b.baseCustomsDeclaration.impExpType,b.commSerialNo,b.version,sum(b.commAmount) from CustomsDeclarationCommInfo b "
				+ " where b.baseCustomsDeclaration.company.id= ? "
				+ " and b.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and b.baseCustomsDeclaration.impExpType in (?,?,?,?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(head.getEmsNo());
		parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
		parameters.add(ImpExpType.DIRECT_EXPORT);// 直接出口
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and b.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (b.baseCustomsDeclaration.effective = ?  "
					+ " or b.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and b.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and b.projectDept is null ";
		}
		hsql += " group by b.baseCustomsDeclaration.impExpType,b.commSerialNo,b.version";
		List list = find(hsql, parameters.toArray());
		return list;
	}

	// public List finLjUseNum(EmsHeadH2k head, Date beginDate, Date endDate,
	// int isEffect, boolean isDeclaration, boolean isdept,
	// String deptCode, Integer imgSeqNum) {// --事业部代码
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql =
	// "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as
	// cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
	// + " a.unitWear as unitWear, a.wear as wear,"
	// // -----------------------------------------------------------------返工复出
	// + " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
	// + " where b.baseCustomsDeclaration.impExpType in (?) ";
	// parameters.add(ImpExpType.REWORK_EXPORT);
	//
	// if (isEffect == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and b.baseCustomsDeclaration.effective = ? ";
	// parameters.add(true);
	// } else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql += " and (b.baseCustomsDeclaration.effective = ? "
	// + " or b.baseCustomsDeclaration.effective is null) ";
	// parameters.add(false);
	// }
	// if (beginDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	//
	// if (beginDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// // 事业部
	// if (isdept && deptCode != null) { // 区分事业部: A
	// hsql += " and b.projectDept.code = ? ";
	// parameters.add(deptCode);
	// } else if (isdept && (deptCode == null)) {
	// hsql += " and b.projectDept is null ";
	// }
	//
	// hsql +=
	// " and b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum "
	// + "and b.version = a.emsHeadH2kVersion.version ) as aa,"
	// // -----------------------------------------------------------------转厂出口
	// + " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
	// + " where b.baseCustomsDeclaration.impExpType in (?) ";
	//
	// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
	// if (isEffect == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and b.baseCustomsDeclaration.effective = ? ";
	// parameters.add(true);
	// } else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql += " and (b.baseCustomsDeclaration.effective = ? "
	// + " or b.baseCustomsDeclaration.effective is null) ";
	// parameters.add(false);
	// }
	// if (beginDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	//
	// if (beginDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// // 事业部
	// if (isdept && deptCode != null) { // 区分事业部: A
	// hsql += " and b.projectDept.code = ? ";
	// parameters.add(deptCode);
	// } else if (isdept && (deptCode == null)) {
	// hsql += " and b.projectDept is null ";
	// }
	//
	// hsql +=
	// " and b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum "
	// + " and b.version = a.emsHeadH2kVersion.version ) as ff,"
	//
	// // ------------------------------------------------------------------直接出口
	// + " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
	// + " where b.baseCustomsDeclaration.impExpType in (?) ";
	// parameters.add(ImpExpType.DIRECT_EXPORT);
	//
	// if (isEffect == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and b.baseCustomsDeclaration.effective = ? ";
	// parameters.add(true);
	// } else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql += " and (b.baseCustomsDeclaration.effective = ? "
	// + " or b.baseCustomsDeclaration.effective is null) ";
	// parameters.add(false);
	// }
	// if (beginDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	//
	// if (beginDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// // 事业部
	// if (isdept && deptCode != null) { // 区分事业部: A
	// hsql += " and b.projectDept.code = ? ";
	// parameters.add(deptCode);
	// } else if (isdept && (deptCode == null)) {
	// hsql += " and b.projectDept is null ";
	// }
	//
	// hsql +=
	// " and b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum "
	// + " and b.version = a.emsHeadH2kVersion.version ) as tt,"
	//
	// // --------------------------------------------------------退厂返工
	// + " (select sum(c.commAmount) from CustomsDeclarationCommInfo c "
	// + " where c.baseCustomsDeclaration.impExpType in (?) ";
	// parameters.add(ImpExpType.BACK_FACTORY_REWORK);
	// if (isEffect == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and (c.baseCustomsDeclaration.effective = ?) ";
	// parameters.add(true);
	// } else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql +=
	// " and (c.baseCustomsDeclaration.effective = ? or
	// c.baseCustomsDeclaration.effective is null ) ";
	// parameters.add(false);
	// }
	// if (beginDate != null && !isDeclaration) {// 结关日期
	// hsql += " and c.baseCustomsDeclaration.impExpDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && !isDeclaration) {
	// hsql += " and c.baseCustomsDeclaration.impExpDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	//
	// if (beginDate != null && isDeclaration) {// 申报日期
	// hsql += " and c.baseCustomsDeclaration.declarationDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && isDeclaration) {
	// hsql += " and c.baseCustomsDeclaration.declarationDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// // 事业部
	// if (isdept && deptCode != null) { // 区分事业部: A
	// hsql += " and c.projectDept.code = ? ";
	// parameters.add(deptCode);
	// } else if (isdept && (deptCode == null)) {
	// hsql += " and c.projectDept is null ";
	// }
	//
	// hsql +=
	// " and c.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum "
	// + " and c.version = a.emsHeadH2kVersion.version ) as cc "
	// //
	// -----------------------------------------------------------------------------------
	// +
	// " from EmsHeadH2kBom a where
	// a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
	// parameters.add(head.getId());
	// if (getIsEmsH2kSend()) {
	// hsql += " and a.sendState = ? ";
	// parameters.add(Integer.valueOf(SendState.SEND));
	// }
	// if (imgSeqNum != null) {
	// hsql += " and a.seqNum = ? ";
	// parameters.add(imgSeqNum);
	// }
	// hsql +=
	// " order by
	// a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
	// List list = find(hsql, parameters.toArray());
	// return list;
	// }

	/**
	 * 电子帐册财务报表
	 */
	public List findCustomsDeclarationCommInfoAsFinancial(Date beginDate,
			Date endDate, String mark) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if ("0".equals(mark)) { // 进口财务报表
			hsql = "select b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ " d.code, c.currSymb,b.baseCustomsDeclaration.impExpDate,"
					+ " sum(b.commAmount),sum(b.commTotalPrice),b.serialNumber,b.commName,b.unit.name,"
					+ " b.baseCustomsDeclaration.currency.name "
					+ " from CustomsDeclarationCommInfo b "
					+ " left join b.baseCustomsDeclaration.currency c "
					+ " left join b.complex d "
					+ " where b.baseCustomsDeclaration.company.id= ? and b.baseCustomsDeclaration.effective = ? "
					+ " and b.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?)";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(Boolean.valueOf(true));
			parameters.add(ImpExpType.DIRECT_IMPORT);// 料件进口
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);// 余料转入
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// 海关批准内销
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);// 余料结转出口
		} else if ("1".equals(mark)) {// 出口财务报表
			hsql = "select b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ "b.baseCustomsDeclaration.impExpDate,b.baseCustomsDeclaration.authorizeFile, "
					+ " d.code,b.commName,f.name,c.currSymb, b.baseCustomsDeclaration.invoiceCode,"
					+ " b.baseCustomsDeclaration.impExpDate,sum(b.commAmount),sum(b.commTotalPrice),b.serialNumber,"
					+ " x.name,b.baseCustomsDeclaration.conveyance,b.baseCustomsDeclaration.billOfLading "
					+ " from CustomsDeclarationCommInfo b "
					+ " left join b.baseCustomsDeclaration.currency c "
					+ " left join b.complex d "
					+ " left join b.unit f "
					+ " left join b.baseCustomsDeclaration.transferMode x "
					+ " where b.baseCustomsDeclaration.company.id= ? and b.baseCustomsDeclaration.effective = ? "
					+ " and b.baseCustomsDeclaration.impExpType in (?,?,?)";// 4成品出口，5返工复出，2退厂返工
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(Boolean.valueOf(true));
			parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
			parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
		} else if ("2".equals(mark)) {// 转厂财务报表
			hsql = "select b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ "b.baseCustomsDeclaration.impExpDate,b.baseCustomsDeclaration.authorizeFile, "
					+ " d.code,b.commName,f.name,c.currSymb, b.baseCustomsDeclaration.invoiceCode,"
					+ " b.baseCustomsDeclaration.impExpDate,sum(b.commAmount),sum(b.commTotalPrice),b.serialNumber"
					+ " from CustomsDeclarationCommInfo b "
					+ " left join b.baseCustomsDeclaration.currency c "
					+ " left join b.complex d "
					+ " left join b.unit f "
					+ " where b.baseCustomsDeclaration.company.id= ? and b.baseCustomsDeclaration.effective = ? "
					+ " and b.baseCustomsDeclaration.impExpType in (?)";// 转厂出口
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(Boolean.valueOf(true));
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
		} else if ("3".equals(mark.substring(0, 1))) {
			hsql = "select b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ "b.baseCustomsDeclaration.impExpDate,b.baseCustomsDeclaration.authorizeFile, "
					+ " d.code,b.commName,f.name,c.currSymb, b.baseCustomsDeclaration.invoiceCode,"
					+ " b.baseCustomsDeclaration.impExpDate,sum(b.commAmount),sum(b.commTotalPrice),b.serialNumber,"
					+ " x.name,b.baseCustomsDeclaration.conveyance,b.baseCustomsDeclaration.billOfLading, "
					+ " c.name,c.code" // adds
					+ " from CustomsDeclarationCommInfo b "
					+ " left join b.baseCustomsDeclaration.currency c "
					+ " left join b.complex d "
					+ " left join b.unit f "
					+ " left join b.baseCustomsDeclaration.transferMode x "
					+ " where b.baseCustomsDeclaration.company.id= ? and b.baseCustomsDeclaration.effective = ? "
					+ " and b.baseCustomsDeclaration.impExpType in (?,?,?)";// 4成品出口，5返工复出，2退厂返工
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(Boolean.valueOf(true));
			parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
			parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
		}

		if (beginDate != null) {
			hsql += " and b.baseCustomsDeclaration.declarationDate >=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.baseCustomsDeclaration.declarationDate <=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if ("0".equals(mark)) { // 进口财务报表
			hsql += " group by b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ " b.complex.code,b.baseCustomsDeclaration.currency.currSymb,b.baseCustomsDeclaration.impExpDate,b.serialNumber,"
					+ " b.commName,b.unit.name,b.baseCustomsDeclaration.currency.name ";
		} else if ("1".equals(mark)) {
			hsql += " group by b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ " b.baseCustomsDeclaration.impExpDate,b.baseCustomsDeclaration.authorizeFile, "
					+ " b.complex.code,b.commName,b.unit.name, b.baseCustomsDeclaration.currency.currSymb, b.baseCustomsDeclaration.invoiceCode,"
					+ " b.baseCustomsDeclaration.impExpDate,b.serialNumber, "
					+ " b.baseCustomsDeclaration.transferMode.name, "
					+ " b.baseCustomsDeclaration.conveyance,b.baseCustomsDeclaration.billOfLading ";
		} else if ("2".equals(mark)) {
			hsql += " group by b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ " b.baseCustomsDeclaration.impExpDate,b.baseCustomsDeclaration.authorizeFile,"
					+ " d.code,b.commName,f.name,c.currSymb, b.baseCustomsDeclaration.invoiceCode,"
					+ " b.baseCustomsDeclaration.impExpDate,b.serialNumber";
		} else if ("3".equals(mark)) {
			hsql += " group by b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.customsDeclarationCode,"
					+ " b.baseCustomsDeclaration.impExpDate,b.baseCustomsDeclaration.authorizeFile, "
					+ " b.complex.code,b.commName,b.unit.name, b.baseCustomsDeclaration.currency.currSymb, b.baseCustomsDeclaration.invoiceCode,"
					+ " c.name,c.code , " // adds
					+ " b.baseCustomsDeclaration.impExpDate,b.serialNumber, "
					+ " b.baseCustomsDeclaration.transferMode.name, "
					+ " b.baseCustomsDeclaration.conveyance,b.baseCustomsDeclaration.billOfLading ";
		}
		hsql += " order by b.baseCustomsDeclaration.impExpDate,b.serialNumber ";

		System.out.println("  >>>>>>>>>>>>>>>>>>  hsql" + hsql);
		List list = find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 技嘉出口商品编码库
	 * 
	 * @param code
	 * @return
	 */
	public List findExItemCode(String code) {
		List parameters = new ArrayList();
		String hsql = " select a from ExItemCode a where a.code = ? ";
		parameters.add(code);
		return find(hsql, parameters.toArray());
	}

	/**
	 * 汇率计算 根据日期区间
	 */
	public List findCustomsDeclarationCommInfoAsFinancialCurrRate(
			Date beginDate, Date endDate, String curr, String curr1) {

		String hsql = " select a.rate " + "from CurrRate a "
				+ "where a.createDate >= ? " + "and a.createDate <= ? "
				+ "and a.curr.code = ? " + "and a.curr1.code = ? "
				+ "and a.company.id = ? ";

		List parameters = new ArrayList();
		parameters.add(CommonUtils.getBeginDate(beginDate));
		parameters.add(CommonUtils.getBeginDate(endDate));
		parameters.add(curr);
		parameters.add(curr1);
		parameters.add(CommonUtils.getCompany().getId());

		return find(hsql, parameters.toArray());
	}

	/**
	 * 根据报关单类型统计转厂数量
	 * 
	 * @return
	 */
	public List findCustomsDeclarationCommInfoTransAmount(EmsHeadH2k head,
			Date beginDate, Date endDate, int isEffect, boolean isDeclaration,
			boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select b.baseCustomsDeclaration.impExpType,b.commSerialNo,b.version,sum(b.commAmount) from CustomsDeclarationCommInfo b "
				+ " where b.baseCustomsDeclaration.company.id= ? "
				+ " and b.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and b.baseCustomsDeclaration.impExpType in (?,?)";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(head.getEmsNo());
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
		parameters.add(ImpExpType.DIRECT_EXPORT);// 直接出口
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and b.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (b.baseCustomsDeclaration.effective = ?  "
					+ " or b.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		if (beginDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and b.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and b.projectDept is null ";
		}
		hsql += " group by b.baseCustomsDeclaration.impExpType,b.commSerialNo,b.version";
		List list = find(hsql, parameters.toArray());
		return list;
	}

	// public List finLjUseNumForTran(EmsHeadH2k head, Date beginDate,
	// Date endDate, int isEffect, boolean isDeclaration, boolean isdept,
	// String deptCode, Integer imgSeqNum) {// --事业部代码
	// List<Object> parameters = new ArrayList<Object>();
	// String hsql =
	// "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as
	// cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
	// + " a.unitWear as unitWear, a.wear as wear,"
	// + " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
	// + " where b.baseCustomsDeclaration.impExpType in (?) ";
	// parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
	//
	// if (isEffect == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and (b.baseCustomsDeclaration.effective = ?) ";
	// parameters.add(true);
	// } else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql +=
	// " and (b.baseCustomsDeclaration.effective = ? or
	// b.baseCustomsDeclaration.effective is null ) ";
	// parameters.add(false);
	// }
	// if (beginDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && !isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	//
	// if (beginDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && isDeclaration) {
	// hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// // 事业部
	// if (isdept && deptCode != null) { // 区分事业部: A
	// hsql += " and b.projectDept.code = ? ";
	// parameters.add(deptCode);
	// } else if (isdept && (deptCode == null)) {
	// hsql += " and b.projectDept is null ";
	// }
	//
	// hsql +=
	// " and b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum and
	// b.version = a.emsHeadH2kVersion.version ) as aa,"
	// + " (select sum(c.commAmount) from CustomsDeclarationCommInfo c "
	// + " where c.baseCustomsDeclaration.impExpType in (?) ";
	// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
	//
	// if (isEffect == CustomsDeclarationState.EFFECTIVED) {
	// hsql += " and (c.baseCustomsDeclaration.effective = ?) ";
	// parameters.add(true);
	// } else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
	// hsql +=
	// " and (c.baseCustomsDeclaration.effective = ? or
	// c.baseCustomsDeclaration.effective is null ) ";
	// parameters.add(false);
	// }
	// if (beginDate != null && !isDeclaration) {// 结关日期
	// hsql += " and c.baseCustomsDeclaration.impExpDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && !isDeclaration) {
	// hsql += " and c.baseCustomsDeclaration.impExpDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	//
	// if (beginDate != null && isDeclaration) {// 申报日期
	// hsql += " and c.baseCustomsDeclaration.declarationDate>=? ";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null && isDeclaration) {
	// hsql += " and c.baseCustomsDeclaration.declarationDate<=? ";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// // 事业部
	// if (isdept && deptCode != null) { // 区分事业部: A
	// hsql += " and c.projectDept.code = ? ";
	// parameters.add(deptCode);
	// } else if (isdept && (deptCode == null)) {
	// hsql += " and c.projectDept is null ";
	// }
	//
	// hsql +=
	// " and c.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum and
	// c.version = a.emsHeadH2kVersion.version ) as cc "
	// +
	// " from EmsHeadH2kBom a where
	// a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
	// parameters.add(head.getId());
	// if (getIsEmsH2kSend()) {
	// hsql += " and a.sendState = ? ";
	// parameters.add(Integer.valueOf(SendState.SEND));
	// }
	// if (imgSeqNum != null) {
	// hsql += " and a.seqNum = ? ";
	// parameters.add(imgSeqNum);
	// }
	// hsql +=
	// " order by
	// a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
	// List list = find(hsql, parameters.toArray());
	// return list;
	// }

	public List findImpExpCommodityInfoByMaterielptNo(int billType,
			String billNo, String ptNo) {
		if (billNo == null || ptNo == null) {
			return new ArrayList();
		}
		String hsql = "select a  from  ImpExpCommodityInfo a  "
				+ "where a.company.id=? and a.materiel.ptNo=? "
				+ " and a.impExpRequestBill.billType=?"
				+ "  and a.impExpRequestBill.billNo=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				ptNo, billType, billNo });
	}

	public List findInputInExRequestBillOrder() {
		return this
				.find("select a from InputInExRequestBillOrder a where a.company.id=?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	public void deleteInputInExRequestBillOrder() {
		List<InputInExRequestBillOrder> list = findInputInExRequestBillOrder();
		for (InputInExRequestBillOrder data : list) {
			if (data == null) {
				continue;
			}
			this.delete(data);
		}
	}

	/**
	 * 查询单个事业部
	 * 
	 * @param deptCode
	 * @return
	 */
	public List findAllDept(String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from ProjectDept a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (deptCode != null && !deptCode.equals("")) {
			hsql += " and a.code = ? ";
			parameters.add(deptCode);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询多个事业部
	 * 
	 * @param deptCode
	 * @return hcl
	 */
	public List findAllDeptMore(List deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from ProjectDept a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		// 事业部
		if (deptCode != null && deptCode.size() != 0) { // 区分事业部: A
			hsql += " and a.code in (";
			for (int i = 0; i < deptCode.size(); i++) {
				if (i == deptCode.size() - 1)
					hsql += "?";
				else
					hsql += "?,";
				parameters.add((String) deptCode.get(i));
			}
			hsql += ")";
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示电子帐册所有Bom
	 */
	public List findEmsHeadH2kBom(EmsHeadH2k emsHeadH2k, Integer[] exgSeqNums) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = " select a from EmsHeadH2kBom a "
				+ " left outer join fetch a.emsHeadH2kVersion "
				+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
				+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? "
				+ " and a.company.id= ? ";
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (exgSeqNums != null && exgSeqNums.length > 0) {
			hql += " and ( ";
			for (int i = 0; i < exgSeqNums.length; i++) {
				if (i == exgSeqNums.length - 1) {
					hql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? ";
				} else {
					hql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? or ";
				}
				parameters.add(exgSeqNums[i]);
			}
			hql += " ) ";
		}
		hql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,"
				+ "a.emsHeadH2kVersion.version,a.seqNum ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 显示电子帐册所有Bom
	 */
	public List findEmsHeadH2kBomByExgSeqNumAndVersion(EmsHeadH2k emsHeadH2k,
			List<Integer[]> exgSeqNums) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = " select a from EmsHeadH2kBom a "
				+ " left outer join fetch a.emsHeadH2kVersion "
				+ " left outer join fetch a.emsHeadH2kVersion.emsHeadH2kExg "
				+ " where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id=? "
				+ " and a.company.id= ? ";
		parameters.add(emsHeadH2k.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (exgSeqNums != null && exgSeqNums.size() > 0) {
			hql += " and ( ";
			for (int i = 0; i < exgSeqNums.size(); i++) {
				if (i == exgSeqNums.size() - 1) {
					hql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? and a.emsHeadH2kVersion.version=? ";
				} else {
					hql += " a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=?  and a.emsHeadH2kVersion.version=?  or ";
				}
				parameters.add(exgSeqNums.get(i)[0]);
				parameters.add(exgSeqNums.get(i)[1]);
			}
			hql += " ) ";
		}
		hql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,"
				+ "a.emsHeadH2kVersion.version,a.seqNum ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询报关单中某项商品序号、商品编码对应的数量总量
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffective
	 *            生效类型
	 * @return List 存放了商品序号、商品编码和对应的数量总量
	 */
	public List findCommInfoVersionTotalAmount(String emsNo,
			Integer[] impExpTypes, Date beginDate, Date endDate,
			String[] tradeCodes, Boolean isEffective, Integer[] exgSeqNums) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo,a.version,sum(a.commAmount) "
				+ " from  CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		if (isEffective != null) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(isEffective);
		}
		// if (impExpTypes!= null&&impExpTypes.length>0) {
		// hsql += " and a.baseCustomsDeclaration.impExpType=?";
		// parameters.add(impExpType);
		// }
		if (exgSeqNums != null && exgSeqNums.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < exgSeqNums.length; i++) {
				if (i == exgSeqNums.length - 1) {
					hsql += " a.commSerialNo=? ";
				} else {
					hsql += " a.commSerialNo=? or ";
				}
				parameters.add(exgSeqNums[i]);
			}
			hsql += " ) ";
		}
		if (impExpTypes != null && impExpTypes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == impExpTypes.length - 1) {
					hsql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.impExpType=? or ";
				}
				parameters.add(impExpTypes[i]);
			}
			hsql += " ) ";
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo,a.version";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询报关单中某项商品序号、商品编码对应的数量总量
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 存放了商品序号、商品编码和对应的数量总量
	 */
	public List findCommInfoTotalAmount(String emsNo, Integer[] impExpTypes,
			Date beginDate, Date endDate, String[] tradeCodes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo,sum(a.commAmount) "
				+ " from  CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		// if (impExpTypes!= null&&impExpTypes.length>0) {
		// hsql += " and a.baseCustomsDeclaration.impExpType=?";
		// parameters.add(impExpType);
		// }
		if (impExpTypes != null && impExpTypes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == impExpTypes.length - 1) {
					hsql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.impExpType=? or ";
				}
				parameters.add(impExpTypes[i]);
			}
			hsql += " ) ";
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询报关单中某项商品序号、商品编码对应的金额总量
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 存放了商品序号、商品编码和对应的金额总量
	 */
	public List findCommInfoVersionTotalMoney(String emsNo,
			Integer[] impExpTypes, Date beginDate, Date endDate,
			String[] tradeCodes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo,a.version,sum(a.commTotalPrice) "
				+ " from  CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		// if (impExpTypes!= null&&impExpTypes.length>0) {
		// hsql += " and a.baseCustomsDeclaration.impExpType=?";
		// parameters.add(impExpType);
		// }
		if (impExpTypes != null && impExpTypes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == impExpTypes.length - 1) {
					hsql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.impExpType=? or ";
				}
				parameters.add(impExpTypes[i]);
			}
			hsql += " ) ";
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo,a.version";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询报关单中某项商品序号、商品编码对应的金额总量
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 存放了商品序号、商品编码和对应的金额总量
	 */
	public List findCommInfoTotalMoney(String emsNo, Integer[] impExpTypes,
			Date beginDate, Date endDate, String[] tradeCodes) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo,sum(a.commTotalPrice) "
				+ " from  CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		// if (impExpTypes!= null&&impExpTypes.length>0) {
		// hsql += " and a.baseCustomsDeclaration.impExpType=?";
		// parameters.add(impExpType);
		// }
		if (impExpTypes != null && impExpTypes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < impExpTypes.length; i++) {
				if (i == impExpTypes.length - 1) {
					hsql += " a.baseCustomsDeclaration.impExpType=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.impExpType=? or ";
				}
				parameters.add(impExpTypes[i]);
			}
			hsql += " ) ";
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询帐册月结信息
	 * 
	 * @param term
	 * @return
	 */
	public List findCustomsMonthStatInfoByTerm(String emsNo, String term) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsMonthStatInfo a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.emsNo = ? ";
			parameters.add(emsNo);
		}
		if (term != null && !term.equals("")) {
			hsql += " and a.statTerm = ? ";
			parameters.add(term);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询帐册月结信息
	 * 
	 * @param term
	 * @return
	 */
	public List findCustomsMonthStatTerm(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.statTerm from CustomsMonthStatInfo a"
				+ " where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and a.emsNo = ? ";
			parameters.add(emsNo);
		}
		return this.find(hsql, parameters.toArray());
	}

	public Date findFirstDayFromCustomsDeclaration(String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select MIN(a.declarationDate) "
				+ " from  CustomsDeclaration as a where a.company.id=? "
				+ " and a.effective=? and a.emsHeadH2k=? "
				+ " and a.declarationDate is not null";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		List list = this.find(hsql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return (Date) list.get(0);
		}
		return null;
	}

	/**
	 * 删除帐册月结信息
	 * 
	 * @param term
	 * @return
	 */
	public void deleteCustomsMonthStatTerm(String emsNo, String term) {
		List<List<Object>> parameters = new ArrayList<List<Object>>();
		List<Object> parameter = new ArrayList<Object>();
		String hsql = "delete customsmonthstatinfo where coid= ? ";
		parameter.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.equals("")) {
			hsql += " and emsNo = ? ";
			parameter.add(emsNo);
		}
		if (term != null && !term.equals("")) {
			hsql += " and statTerm >= ? ";
			parameter.add(term);
		}
		parameters.add(parameter);
		this.batchUpdateOrDeleteInSql(hsql, parameters, 50);
	}

	/**
	 * 更新电子帐册中的申请单物料的版本号，为最大的版本号
	 */
	public void updateMaxVersion() {
		String sql1 = "update impexpcommodityinfo set extendmemo1  =  a.hsaftertenmemono "
				+ " from innermergedata a where a.materielid = impexpcommodityinfo.materielid "
				+ " and exists (select 1 from impexprequestbill where billtype in ('2','4','5','7') "
				+ " and impexprequestbill.id = impexpcommodityinfo.impexprequestbillid) "
				+ " and (impexpcommodityinfo.version is null or isnull(impexpcommodityinfo.version,'') = '')";
		this.batchUpdateOrDeleteInSql(sql1, new ArrayList(), 0);
		String sql2 = "update impexpcommodityinfo set  version  =  isNull(b.maxVersion,'0') from "
				+ " (select max(a.version) as maxVersion,c.seqnum from emsheadh2kversion a left join "
				+ " emsheadh2kexg c on c.id = a.emsheadh2kexg where isnull(a.isforbid,'') = '' "
				+ " group by c.seqnum) as b where b.seqnum = impexpcommodityinfo.extendmemo1 "
				+ " and exists (select 1 from impexprequestbill where billtype in ('2','4','5','7') "
				+ " and impexprequestbill.id = impexpcommodityinfo.impexprequestbillid) "
				+ " and (impexpcommodityinfo.version is null or  isnull(impexpommodityinfo.version,'') = '')";
		this.batchUpdateOrDeleteInSql(sql2, new ArrayList(), 0);
	}

	/**
	 * 获得报关清单导入的属性设置
	 * 
	 */
	public List findImportApplyCustomsProperty() {
		List parameters = new ArrayList();
		String hsql = " select a " + " from  ImportApplyCustomsProperty a ";
		// parameters.add(CommonUtils.getCompany().getId());

		return this.find(hsql);
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的料件
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kByPtNoFromMerger(String ptNo) {
		List list = this
				.find(" select a from EmsHeadH2kImg a"
						+ " where a.seqNum = (select distinct b.seqNum from EmsEdiMergerImgAfter b "
						+ " where b.seqNum = (select c.seqNum from EmsEdiMergerImgBefore c"
						+ " where c.ptNo = ? " + " and c.modifyMark = '0')) "
						+ " and a.modifyMark = '0' " + " and a.company.id=? ",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });

		return list;
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kExgByPtNoFromMerger(String ptNo) {
		List list = this
				.find(" select a from EmsHeadH2kExg a"
						+ " where a.seqNum = (select distinct b.seqNum from EmsEdiMergerExgAfter b "
						+ " where b.seqNum = (select c.seqNum from EmsEdiMergerExgBefore c"
						+ " where c.ptNo = ? " + " and c.modifyMark = '0')) "
						+ " and a.modifyMark = '0' " + " and a.company.id=? ",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });

		return list;
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kBomByPtNoFromMerger(String ptNo, Integer version) {
		List list = this
				.find(" select a from EmsHeadH2kBom a"
						+ " where a.company.id=? and a.emsHeadH2kVersion.version=? and (a.modifyMark=1 or a.modifyMark=3) "
						+ " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = "
						+ " (select distinct b.seqNum from EmsEdiMergerExgAfter b "
						+ " where b.seqNum = (select seqNum from EmsEdiMergerExgBefore c "
						+ " where c.ptNo = ?) " + " and b.modifyMark = '0') ",
						new Object[] { CommonUtils.getCompany().getId(),
								version, ptNo });
		return list;
	}

	/**
	 * 保存报关清单导入的属性设置
	 * 
	 */
	public void saveImportApplyCustomsProperty(
			ImportApplyCustomsProperty importApplyProperty) {
		this.saveOrUpdate(importApplyProperty);
	}

	/**
	 * 检查导入的货号资料是否在归并关系管理归并前、内部归并、报关常用工厂物料里都存在。
	 * 
	 * @param ptNo
	 *            要检查的货号
	 * @param tableName
	 *            归并关系管理归并前table
	 * @return
	 */
	public List findBeforeMaterielPtNo(String ptNo, String tableName,
			Integer seqNum, String property) {
		List parameters = new ArrayList();
		String hsql = "";
		if (getIsEmsH2kSend()) {
			hsql = "select distinct a from InnerMergeData a," + tableName
					+ " b " + " where a.materiel.ptNo=? and a.company.id=? "
					+ " and a.isExistMerger=? and b." + property
					+ ".seqNum=a.hsAfterTenMemoNo and b." + property
					+ ".emsEdiMergerHead.historyState=? and b.sendState=? "
					+ " and b.company.id=a.company.id ";
			parameters.add(ptNo);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(true);
			parameters.add(false);
			parameters.add(Integer.valueOf(SendState.SEND));
		} else {
			hsql = "select distinct a from InnerMergeData a,"
					+ tableName
					+ " b "
					+ " where a.materiel.ptNo=? and a.company.id=? "
					+ " and a.isExistMerger=? and b."
					+ property
					+ ".seqNum=a.hsAfterTenMemoNo and b."
					+ property
					+ ".emsEdiMergerHead.historyState=? and b."
					+ property
					+ ".emsEdiMergerHead.declareState=? and b.company.id=a.company.id ";
			parameters.add(ptNo);
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(true);
			parameters.add(false);
			parameters.add(DeclareState.PROCESS_EXE);
		}
		if (seqNum != null) {
			hsql += " and a.hsAfterTenMemoNo=? ";
			parameters.add(seqNum);
		}
		System.out.println("hsql=========" + hsql);
		return find(hsql, parameters.toArray());
	}

	/**
	 * 根据tableName、属性、是否有Company来查找数据,
	 * 
	 * @param tableName
	 *            实体类
	 * @param property
	 *            属性
	 * @param value
	 *            属性值
	 * @param hasCompany
	 *            是否有company栏位
	 * @return
	 */
	public List findObjectByTableNames(String tableName, String[] property,
			Object[] value, boolean hasCompany) {
		List parameters = new ArrayList();
		String hsql = " select a from " + tableName + " a ";
		if (property != null) {
			hsql += " where ";
			for (int i = 0; i < property.length; i++) {
				if (i == 0)
					hsql += " a." + property[i] + "=? ";
				else {
					hsql += " and a." + property[i] + "=? ";
				}
				parameters.add(value[i]);
			}
		}
		if (hasCompany) {
			if (hsql.contains("where"))
				hsql += " and a.company.id=? ";
			else
				hsql += " where a.company.id=? ";
			parameters.add(CommonUtils.getCompany().getId());
		}
		if (parameters.isEmpty()) {
			return this.find(hsql);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 保存后返回obj
	 * 
	 * @param obj
	 * @return
	 */
	public Object saveAndReturnObject(Object obj) {
		this.saveOrUpdate(obj);
		return obj;
	}

	/**
	 * 返回归并前的最大商品序号
	 * 
	 * @param applyToCustomsBillList
	 *            表头
	 * @return
	 */
	public Integer getMaxBeforeSerialNo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		List parameters = new ArrayList();
		String hsql = " select a from AtcMergeBeforeComInfo a where a.afterComInfo.billList.id=? "
				+ " and a.company.id=? ";
		parameters.add(atcMergeAfterComInfo.getBillList().getId());
		parameters.add(CommonUtils.getCompany().getId());
		List list = find(hsql, parameters.toArray());
		return list.size();

	}

	/**
	 * 根据进出类型、单据类型,查找已转清单的申请单头资料
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @return
	 */
	public List findImpExpRequestBillByPara(String impExpFlagCode,
			String billTypeCode) {
		List parameters = new ArrayList();
		String hsql = " select a from ImpExpRequestBill a where "
				+ " a.isAvailability=? and a.isCustomsBill=? and a.company.id=? ";
		parameters.add(Boolean.valueOf(true));
		parameters.add(Boolean.valueOf(true));
		parameters.add(CommonUtils.getCompany().getId());
		if (billTypeCode != null) {
			hsql += " and a.billType=? ";
			parameters.add(Integer.valueOf(billTypeCode));
		}
		if (billTypeCode == null && impExpFlagCode != null) {
			if (impExpFlagCode.equals(MaterielType.FINISHED_PRODUCT)) {// 出口
				hsql += " and a.billType in (?,?,?,?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
				parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
				parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);// 边角料退港
				parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);// 边角料内销
				parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);// 一般贸易出口
				parameters.add(ImpExpType.EXPORT_MATERIAL_REBACK);// 修理物品复出
				parameters.add(ImpExpType.EXPORT_EXG_REBACK);// 进料成品退换复出
			} else if (impExpFlagCode.equals(MaterielType.MATERIEL)) {// 进口
				hsql += " and a.billType in (?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_IMPORT);// 料件进口
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
				parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
				parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);// 进料成品退换
				parameters.add(ImpExpType.IMPORT_EXG_BACK);// 料件进口
				parameters.add(ImpExpType.IMPORT_REPAIR_MATERIAL);// 修理物品
			}
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据进出类型、单据类型,查找客户/供应商/合作伙伴资料
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @return
	 */
	public List findScmCocsByPara(String impExpFlagCode, String billTypeCode) {
		List parameters = new ArrayList();
		String hsql = " select a from ScmCoc a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpFlagCode != null) {
			if (impExpFlagCode.equals("0")) {// 出口
				if (billTypeCode != null
						&& (billTypeCode.equals("6")
								|| billTypeCode.equals("8")
								|| billTypeCode.equals("9")
								|| billTypeCode.equals("27") || billTypeCode
									.equals("28"))) {// 6为退料出口
					hsql += " and a.isManufacturer=? ";
					parameters.add(true);
				} else {
					hsql += " and a.isCustomer=? ";
					parameters.add(true);
				}

			} else if (impExpFlagCode.equals("2")) {// 进口
				if (billTypeCode != null && billTypeCode.equals("2")) {// 2为退厂返工
					hsql += " and a.isCustomer=? ";
					parameters.add(true);
				} else {
					hsql += " and a.isManufacturer=? ";
					parameters.add(true);
				}

			}
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找所有清单转报关单的数据
	 * 
	 * @param atcMergeAfterComInfoId
	 *            清单归并前Id
	 * @return
	 */
	public BaseCustomsDeclarationCommInfo getCustomsFromMakeListToCustoms(
			String atcMergeAfterComInfoId) {
		List parameters = new ArrayList();
		String hsql = " select a from MakeListToCustoms a "
				+ " where a.company.id = ? and a.atcMergeAfterComInfo.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(atcMergeAfterComInfoId);
		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		return ((MakeListToCustoms) list.get(0)).getCustomsInfo();
	}

	/**
	 * 查找所有qp下载报关单的数据
	 * 
	 * @param ApplyToCustomsBillList
	 *            .ListUnitNo 清单中的ListUnitNo
	 * @return
	 */
	public List getListUnitNoCustomsFromMakeListToCustoms(
			String atcMergeAfterComInfoId, Integer EmsNo) {
		List parameters = new ArrayList();
		String hsql = " select a from CustomsDeclarationCommInfo a "
				+ " where a.company.id = ? and (baseCustomsDeclaration.unificationCode=? and baseCustomsDeclaration.unificationCode is not null) "
				+ " and (a.commSerialNo=?) and (a.baseCustomsDeclaration.effective=? )";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(atcMergeAfterComInfoId);
		parameters.add(EmsNo);
		parameters.add(true);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找所有申请单转清单的数据
	 * 
	 * @param impExpCommodityInfoId
	 *            申请单表体ID
	 * @return
	 */
	public AtcMergeBeforeComInfo getMergeBeforeComInfoFromMakeApplyToCustoms(
			String impExpCommodityInfoId, String commSerial) {
		List parameters = new ArrayList();
		String hsql = " select a from MakeApplyToCustoms a "
				+ " where a.company.id = ? and a.impExpCommodityInfo.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(impExpCommodityInfoId);
		if (commSerial != null && !"".equals(commSerial)) {
			hsql += " and a.atcMergeBeforeComInfo.afterComInfo.emsSerialNo = ?";
			parameters.add(Integer.parseInt(commSerial));
		}

		List list = find(hsql, parameters.toArray());
		if (list.isEmpty())
			return null;
		System.out.println("@@@@==@@@@" + hsql + "@@@"
				+ CommonUtils.getCompany().getId() + "@@@"
				+ impExpCommodityInfoId);
		return ((MakeApplyToCustoms) list.get(0)).getAtcMergeBeforeComInfo();

	}

	/**
	 * 根据进出类型、单据类型,查找申请单表体
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            申请单单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            开始生效日期
	 * @param endDate
	 *            结束生效日期
	 * @return
	 */
	public List findImpExpCommodityInfoBySomePara(String impExpFlagCode,
			String billTypeCode, ImpExpRequestBill impExpRequestBill,
			String bomNo, ScmCoc scmCoc, Date beginDate, Date endDate) {
		List parameters = new ArrayList();
		String hsql = " select a from ImpExpCommodityInfo a left join a.impExpRequestBill b "
				+ " left join a.materiel c " + " where a.company.id= ? "// and
				+ " and b.isAvailability=? and b.isCustomsBill=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(true);
		if (billTypeCode == null && impExpFlagCode != null) {
			if (impExpFlagCode.equals(MaterielType.FINISHED_PRODUCT)) {// 出口
				hsql += " and b.billType in (?,?,?,?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
				parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
				parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);// 边角料退港
				parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);// 边角料内销
				parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);// 一般贸易出口
				parameters.add(ImpExpType.EXPORT_MATERIAL_REBACK);// 修理物品复出
				parameters.add(ImpExpType.EXPORT_EXG_REBACK);// 进料成品退换复出
			} else if (impExpFlagCode.equals(MaterielType.MATERIEL)) {// 进口
				hsql += " and b.billType in (?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_IMPORT);// 料件进口
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
				parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
				parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);// 进料成品退换
				parameters.add(ImpExpType.IMPORT_EXG_BACK);// 料件进口
				parameters.add(ImpExpType.IMPORT_REPAIR_MATERIAL);// 修理物品
			}
		}
		if (billTypeCode != null) {
			hsql += " and b.billType=? ";
			parameters.add(Integer.valueOf(billTypeCode));
		}

		if (impExpRequestBill != null) {
			hsql += " and b.billNo=? ";
			parameters.add(impExpRequestBill.getBillNo());
		}

		if (bomNo != null && !bomNo.equals("")) {
			hsql += " and c.ptNo=? ";
			parameters.add(bomNo);
		}

		if (scmCoc != null) {
			hsql += " and b.scmCoc.name=? ";
			parameters.add(scmCoc.getName());
		}

		if (beginDate != null) {
			hsql += " and b.beginAvailability>? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.beginAvailability<? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		hsql += " order by a.impExpRequestBill.billNo ";

		System.out
				.println("hsql---------------findImpExpCommodityInfoBySomePara---------------------"
						+ hsql);
		return this.find(hsql, parameters.toArray());
	}

	public List findAtcMergeBeforeComInfoBySomePara(String impExpFlagCode,
			String billTypeCode, Date beginDate, Date endDate) {
		List parameters = new ArrayList();
		String hsql = " select distinct a from AtcMergeBeforeComInfo a  "
				+ " where a.company.id= ?   ";// and
		// a.afterComInfo.billList.effectstate<>
		// ?
		parameters.add(CommonUtils.getCompany().getId());
		if (billTypeCode == null && impExpFlagCode != null) {
			if (impExpFlagCode.equals(MaterielType.FINISHED_PRODUCT)) {// 出口
				hsql += " and a.afterComInfo.billList.impExpType in (?,?,?,?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
				parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
				parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);// 边角料退港
				parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);// 边角料内销
				parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);// 一般贸易出口
				parameters.add(ImpExpType.EXPORT_MATERIAL_REBACK);// 修理物品复出
				parameters.add(ImpExpType.EXPORT_EXG_REBACK);// 进料成品退换复出
			} else if (impExpFlagCode.equals(MaterielType.MATERIEL)) {// 进口
				hsql += " and a.afterComInfo.billList.impExpType in (?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_IMPORT);// 料件进口
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
				parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
				parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);// 进料成品退换
				parameters.add(ImpExpType.IMPORT_EXG_BACK);// 料件进口
				parameters.add(ImpExpType.IMPORT_REPAIR_MATERIAL);// 修理物品
			}
		}
		if (billTypeCode != null) {
			hsql += " and a.afterComInfo.billList.impExpType=? ";
			parameters.add(Integer.valueOf(billTypeCode));
		}
		if (beginDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate>? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.afterComInfo.billList.createdDate<? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		// hsql += " order by
		// a.afterComInfo.billList.listNo,a.afterComInfo.emsSerialNo ";
		// System.out.println( hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据进出类型、单据类型,查找表体名称、表体料号的申请单表体
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            申请单单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            开始生效日期
	 * @param endDate
	 *            结束生效日期
	 * @return
	 */
	public List getDistinctImpExpCommodityInfoByName(String impExpFlagCode,
			String billTypeCode, ImpExpRequestBill impExpRequestBill,
			String bomNo, ScmCoc scmCoc, Date beginDate, Date endDate) {
		List parameters = new ArrayList();
		String hsql = " select distinct a.materiel.ptNo,a.materiel.factoryName from ImpExpCommodityInfo a "
				+ " where a.company.id= ? and a.isPutOnRecord=? and a.isTransferCustomsBill=? "
				+ " and a.impExpRequestBill.isAvailability=? and a.impExpRequestBill.isCustomsBill=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(true);
		parameters.add(true);
		parameters.add(true);
		if (billTypeCode == null && impExpFlagCode != null) {
			if (impExpFlagCode.equals(MaterielType.FINISHED_PRODUCT)) {// 出口
				hsql += " and a.impExpRequestBill.billType in (?,?,?,?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
				parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
				parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);// 边角料退港
				parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);// 边角料内销
				parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);// 一般贸易出口
				parameters.add(ImpExpType.EXPORT_MATERIAL_REBACK);// 修理物品复出
				parameters.add(ImpExpType.EXPORT_EXG_REBACK);// 进料成品退换复出
			} else if (impExpFlagCode.equals(MaterielType.MATERIEL)) {// 进口
				hsql += " and a.impExpRequestBill.billType in (?,?,?,?,?,?) ";
				parameters.add(ImpExpType.DIRECT_IMPORT);// 料件进口
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂
				parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
				parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);// 进料成品退换
				parameters.add(ImpExpType.IMPORT_EXG_BACK);// 料件进口
				parameters.add(ImpExpType.IMPORT_REPAIR_MATERIAL);// 修理物品
			}
		}
		if (billTypeCode != null) {
			hsql += " and a.impExpRequestBill.billType=? ";
			parameters.add(Integer.valueOf(billTypeCode));
		}

		if (impExpRequestBill != null) {
			hsql += " and a.impExpRequestBill.billNo=? ";
			parameters.add(impExpRequestBill.getBillNo());
		}

		if (bomNo != null && !bomNo.equals("")) {
			hsql += " and a.materiel.ptNo=? ";
			parameters.add(bomNo);
		}

		if (scmCoc != null) {
			hsql += " and a.impExpRequestBill.scmCoc.name=? ";
			parameters.add(scmCoc.getName());
		}

		if (beginDate != null) {
			hsql += " and a.impExpRequestBill.beginAvailability>? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.impExpRequestBill.beginAvailability<? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}

		System.out
				.println("hsql---------------findImpExpCommodityInfoBySomePara---------------------"
						+ hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据单据类型、录入日期查找申请单
	 * 
	 * @param type
	 *            单据类型
	 * @param beginImputDate
	 *            开始录入日期
	 * @param endImputDate
	 *            结束录入日期
	 * @return 与条件匹配的所有进出口申请单
	 */
	public List findImpExpRequestBillByTypeAndImputDate(int type,
			Date beginImputDate, Date endImputDate) {
		String hssql = " select a from ImpExpRequestBill a where a.billType = ? and a.company.id=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(Integer.valueOf(type));
		parameters.add(CommonUtils.getCompany().getId());
		if (beginImputDate != null) {
			hssql += " and (a.imputDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginImputDate));
		}
		if (endImputDate != null) {
			if (hssql.contains("("))
				hssql += " and a.imputDate<=? ";
			else
				hssql += " and (a.imputDate<=? ";
			parameters.add(CommonUtils.getEndDate(endImputDate));
		}
		if (hssql.contains("("))
			hssql += " or a.imputDate is null) ";
		else
			hssql += " and a.imputDate is null ";

		hssql += " order by a.imputDate Desc ";
		return this.find(hssql, parameters.toArray());
	}

	/**
	 * 查询归并中的设备资料
	 * 
	 * @param projectType
	 *            模块信息
	 * @return 查询归并中的设备资料
	 */
	public List findFixturInnerDate(int projectType, int index, int length,
			String property, Object value, boolean isLike, boolean isImport) {
		String hql = "";
		List paramters = new ArrayList();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select  distinct b,a.hsAfterTenMemoNo,a.hsAfterMaterielTenName ,a.hsAfterMaterielTenSpec,c "
					+ " from InnerMergeData a "
					+ " left join a.hsAfterComplex b "
					+ " left join a.hsAfterComplex.firstUnit d "
					+ " left join a.hsAfterComplex.secondUnit e "
					+ " left join a.hsAfterLegalUnit c "
					+ " where a.imrType in (?,?)  "
					+ " and a.hsAfterTenMemoNo is not null and a.materiel is not null ";
			if (isImport) {
				paramters.add(MaterielType.MATERIEL);
			} else {
				paramters.add(MaterielType.FINISHED_PRODUCT);
			}
			paramters.add(MaterielType.MACHINE);
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hql += " and  a." + property + " like ?  ";
					paramters.add(value + "%");
				} else {
					hql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
			break;
		case ProjectType.BCS:
			hql = "select  distinct b,a.seqNum,a.name ,a.spec,c "
					+ " from BcsTenInnerMerge a " + " left join a.complex b "
					+ " left join a.complex.firstUnit d "
					+ " left join a.complex.secondUnit e "
					+ " left join a.comUnit c " + " where a.scmCoi in (?,?)  "
					+ " and a.seqNum is not null and a.scmCoi is not null ";
			if (isImport) {
				paramters.add(MaterielType.MATERIEL);
			} else {
				paramters.add(MaterielType.FINISHED_PRODUCT);
			}
			paramters.add(MaterielType.MACHINE);
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hql += " and  a." + property + " like ?  ";
					paramters.add(value + "%");
				} else {
					hql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
			break;
		case ProjectType.DZSC:
			hql = "select distinct b, f.tenSeqNum,f.tenPtName ,f.tenPtSpec,c "
					+ " from DzscInnerMergeData a "
					+ " left join a.dzscTenInnerMerge f "
					+ " left join a.dzscTenInnerMerge.tenComplex b "
					+ " left join a.dzscTenInnerMerge.tenComplex.firstUnit d "
					+ " left join a.dzscTenInnerMerge.tenComplex.secondUnit e "
					+ " left join a.dzscTenInnerMerge.tenUnit c "
					+ " where a.imrType in (?,?) "
					+ " and a.dzscTenInnerMerge.tenSeqNum is not null and a.materiel is not null ";
			if (isImport) {
				paramters.add(MaterielType.MATERIEL);
			} else {
				paramters.add(MaterielType.FINISHED_PRODUCT);
			}
			paramters.add(MaterielType.MACHINE);
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hql += " and  a." + property + " like ?  ";
					paramters.add(value + "%");
				} else {
					hql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
			break;
		default:
			hql = "select  distinct b.code,a.hsAfterTenMemoNo,a.hsAfterMaterielTenName ,"
					+ " a.hsAfterMaterielTenSpec,c.name,d.name,e.name from InnerMergeData a "
					+ " left join a.hsAfterComplex b "
					+ " left join a.hsAfterComplex.firstUnit d "
					+ " left join a.hsAfterComplex.secondUnit e "
					+ " left join a.hsAfterLegalUnit c "
					+ " where a.imrType in (?,?)  "
					+ " and a.hsAfterTenMemoNo is not null and a.materiel is not null ";
			if (isImport) {
				paramters.add(MaterielType.MATERIEL);
			} else {
				paramters.add(MaterielType.FINISHED_PRODUCT);
			}
			paramters.add(MaterielType.MACHINE);
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hql += " and  a." + property + " like ?  ";
					paramters.add(value + "%");
				} else {
					hql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
			break;
		}
		hql += " and a.company.id=? ";
		paramters.add(CommonUtils.getCompany().getId());
		System.out.println("hql----fix-----------------" + hql);
		return super.findPageList(hql, paramters.toArray(), index, length);
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
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.wrapType.name,sum(a.grossWeight-a.netWeight) from "
				+ "CustomsDeclaration as a where a.company.id=? and a.wrapType is not null ";
		parameters.add(CommonUtils.getCompany().getId());
		if (wrap != null) {
			hsql += " and a.wrapType.code=? ";
			parameters.add(wrap.getCode());
		}
		if (impExpFlag != null) {
			hsql += " and a.impExpFlag=? ";
			parameters.add(impExpFlag);
		}

		if (beginDate != null) {
			hsql += " and a.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (effective != null) {
			hsql += " and a.effective=?";
			parameters.add(effective);
		}
		hsql += " group by a.wrapType.name";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据报关单商品信息获得报关清单归并后商品信息
	 * 
	 * @param customsInfo
	 * @return
	 */
	public List findMakeListToCustomsBycustomsInfo(
			CustomsDeclarationCommInfo customsInfo) {
		String hsql = "select a.atcMergeAfterComInfo from MakeListToCustoms a "
				+ " where a.customsInfo=?  and a.company.id= ? ";
		return this.find(hsql, new Object[] { customsInfo,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据报关单商品信息获得报关清单商品信息
	 * 
	 * @param customsInfo
	 * @return
	 */
	public List findMakeListToCustomsBycustomsInfo(
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo) {
		String hsql = "select a.impExpCommodityInfo from MakeBcsCustomsDeclaration a "
				+ " where a.bcsCustomsDeclarationCommInfo = ?  and a.company.id= ? ";
		return this.find(hsql, new Object[] { bcsCustomsDeclarationCommInfo,
				CommonUtils.getCompany().getId() });
	}

	public List findCheckupComplexByFlag(Integer impexpfalg) {
		List para = new ArrayList();
		String hsql = " select  a.complex.code from CheckupComplex  a    where 1=1 ";
		if (impexpfalg != null) {
			hsql += " and a.impExpFlag = ? ";
			para.add(impexpfalg);
		}
		return this.find(hsql, para.toArray());
	}

	public List findEmsEdiExgBomVersion() {
		return this
				.find(" select distinct a.version from EmsEdiMergerVersion a  where a.company.id=? order by a.version ",
						CommonUtils.getCompany().getId());
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

	public XinpuReport getXinpuReport() {
		List list = this.find("from XinpuReport");
		if (list != null && list.size() > 0) {
			return (XinpuReport) list.get(0);
		}
		return null;
	}

	public void saveXinpuReport(XinpuReport xinpuReport) {
		this.saveOrUpdate(xinpuReport);
	}

	/**
	 * 按帐册编号、料件序号查找料件
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            料件序号
	 * @return
	 */
	public EmsHeadH2kImg findEmsHeadh2kImgByEmsNoSeqNum(String emsNo,
			Integer seqNum) {
		List list = this.find(
				"select a from EmsHeadH2kImg a where a.emsHeadH2k.emsNo = ? "
						+ " and a.emsHeadH2k.declareState=?"
						+ " and a.seqNum=? and a.emsHeadH2k.company.id=? ",
				new Object[] { emsNo, DeclareState.PROCESS_EXE,
						Integer.valueOf(seqNum),
						CommonUtils.getCompany().getId() });
		if (list.size() <= 0) {
			System.out.println("img=null");
			return null;
		} else {
			return (EmsHeadH2kImg) list.get(0);
		}
	}

	/**
	 * 查询合同成品 来自帐册编号、成品序号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是EmsHeadH2kExg型，合同成品
	 */
	public EmsHeadH2kExg findEmsHeadh2kExgByEmsNoSeqNum(String emsNo,
			Integer seqNum) {
		List list = this.find(
				"select c from EmsHeadH2kExg c where c.emsHeadH2k.emsNo=? "
						+ " and c.emsHeadH2k.declareState=? "
						+ " and c.seqNum=? and c.emsHeadH2k.company.id=? ",
				new Object[] { emsNo, DeclareState.PROCESS_EXE,
						Integer.valueOf(seqNum),
						CommonUtils.getCompany().getId() });
		if (list.size() <= 0) {
			return null;
		} else {
			return (EmsHeadH2kExg) list.get(0);
		}
	}

	/**
	 * 查询报关单中某项商品序号、商品编码对应的数量总量
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 存放了商品序号、商品编码和对应的数量总量
	 */
	public List findCommInfoTotalAmount(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo, sum(a.commAmount) "
				+ " from  CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		System.out.println("hsql=" + hsql);
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  CustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查找合同BOM 来自 料件凭证号
	 * 
	 * @param EmsHeadH2kImg
	 *            合同料件
	 * @return List 是EmsHeadH2kBom型，合同BOM
	 */
	public List findEmsHeadH2kBomByImgSeqNum(EmsHeadH2kImg img) {
		/**
		 * 编码相同,名称,规格,单位相同,单重 == key
		 */
		return this
				.find("select a from EmsHeadH2kBom a where a.seqNum = ? "
						+ "and a.company= ? and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? "
						+ " order by a.seqNum ", new Object[] {
						img.getSeqNum(), CommonUtils.getCompany(),
						img.getEmsHeadH2k().getId() });
	}

	/**
	 * 取得大批量修改商品编的内部归并资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List findInnerMergeDataComplex(Boolean isMaterial) {
		List parameters = new ArrayList();
		String hsql = "";
		if (isMaterial) {// 料件
			hsql = "select distinct a.hsAfterTenMemoNo,a.hsAfterComplex,a.hsAfterMemoUnit,a.hsAfterLegalUnit,a.hsAfterSecondLegalUnit,a.hsAfterMaterielTenName "
					+ " from InnerMergeData a "
					+ " left join a.hsAfterComplex "
					+ " left join a.hsAfterMemoUnit"
					+ " left join a.hsAfterLegalUnit"
					+ " left join a.hsAfterSecondLegalUnit"
					+ " where  a.imrType=? and a.company.id=?";
			parameters.add("2");
			parameters.add(CommonUtils.getCompany().getId());
		} else {// 成品
			hsql = "select distinct a.hsAfterTenMemoNo,a.hsAfterComplex,a.hsAfterMemoUnit,a.hsAfterLegalUnit,a.hsAfterSecondLegalUnit,a.hsAfterMaterielTenName "
					+ "from InnerMergeData a"
					+ " left join a.hsAfterComplex "
					+ " left join a.hsAfterMemoUnit"
					+ " left join a.hsAfterLegalUnit"
					+ " left join a.hsAfterSecondLegalUnit"
					+ " where  a.imrType=? and a.company.id=? ";
			parameters.add("0");
			parameters.add(CommonUtils.getCompany().getId());
		}
		return find(hsql, parameters.toArray());
	}

	/**
	 * 查询电子帐册备案料件信息(注意：商品禁用也使用到)
	 * 
	 * @param declareState
	 *            申报状态
	 * @param customsDeclaration
	 *            报关单商品信息
	 * @return 没有被禁止的电子帐册备案料件信息
	 */
	public List findEdiH2k(Boolean isMaterial) {
		String tableName = "";
		if (isMaterial) {
			tableName = " EmsHeadH2kImg ";
		} else {
			tableName = " EmsHeadH2kExg ";
		}
		if (getIsEmsH2kSend()) {
			return this
					.find(" select distinct a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name from "
							+ tableName
							+ " as a "
							+ " left outer join  a.complex "
							+ " left outer join  a.unit "
							+ " left outer join  a.complex.firstUnit "
							+ " left outer join  a.complex.secondUnit "
							+ " where  a.emsHeadH2k.company.id=? "
							+ "  order by a.seqNum ",
							new Object[] { CommonUtils.getCompany().getId() });
		} else {
			return this
					.find(" select distinct a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name from "
							+ tableName
							+ "  as a "
							+ " left outer join  a.complex "
							+ " left outer join  a.unit "
							+ " left outer join  a.complex.firstUnit "
							+ " left outer join  a.complex.secondUnit "
							+ " where a.emsHeadH2k.declareState=? "
							+ " and a.emsHeadH2k.company.id=? "
							+ "  order by a.seqNum", new Object[] {
							DeclareState.PROCESS_EXE,
							CommonUtils.getCompany().getId() });
		}

	}

	/**
	 * 取得大批量修改商品编的料件归并关系资料
	 * 
	 * @param declareState
	 * @return
	 */
	public List findEdiMergerAfter(Boolean isMaterial) {
		String tableName = "";
		if (isMaterial) {
			tableName = " EmsEdiMergerImgAfter ";
		} else {
			tableName = " EmsEdiMergerExgAfter ";
		}
		if (getIsEmsH2kSend()) {
			return this
					.find(" select distinct a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name  from "
							+ tableName
							+ " as a "
							+ " left join a.complex "
							+ " left join a.unit"
							+ " left join a.complex.firstUnit"
							+ " left join a.complex.secondUnit"
							+ " where a.emsEdiMergerHead.company.id=? "
							+ "  order by a.seqNum", new Object[] { CommonUtils
							.getCompany().getId() });
		} else {
			return this
					.find(" select distinct a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.complex.secondUnit,a.name  from "
							+ tableName
							+ " as a "
							+ " left join a.complex "
							+ " left join a.unit"
							+ " left join a.complex.firstUnit"
							+ " left join a.complex.secondUnit"
							+ " where a.emsEdiMergerHead.declareState=? "
							+ " and a.emsEdiMergerHead.company.id=? "
							+ "  order by a.seqNum", new Object[] {
							DeclareState.PROCESS_EXE,
							CommonUtils.getCompany().getId() });
		}

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
		String tableName = "";
		if (isMaterial) {
			tableName = " EmsHeadH2kImg ";
		} else {
			tableName = " EmsHeadH2kExg ";
		}
		String hsql = "update " + tableName + " a set a.complex.id = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(complex.getId());

		String hql = "update EmsHeadH2kBom a set a.complex.id = ?";
		List<Object> paras = new ArrayList<Object>();
		paras.add(complex.getId());

		if (isSendData) {
			if (getIsEmsH2kSend()) {
				hsql += ", a.modifyMark=?  , a.sendState=? ";
				parameters.add(ModifyMarkState.MODIFIED);
				parameters.add(Integer.valueOf(SendState.WAIT_SEND));

				// hql += ", a.modifyMark=?  , a.sendState=? ";
				// paras.add(ModifyMarkState.MODIFIED);
				// paras.add(Integer.valueOf(SendState.WAIT_SEND));
			} else {
				// hsql += ", a.modifyMark=? ";
				// parameters.add(ModifyMarkState.MODIFIED);
			}
		}
		if (getIsEmsH2kSend()) {
			hsql += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>?  and a.sendState =? and a.modifyMark =? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(seqNum);
			parameters.add(complex.getId());
			parameters.add(Integer.valueOf(SendState.SEND));
			parameters.add(ModifyMarkState.UNCHANGE);

			hql += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>?  and a.sendState =? and a.modifyMark =? ";
			paras.add(CommonUtils.getCompany().getId());
			paras.add(seqNum);
			paras.add(complex.getId());
			paras.add(Integer.valueOf(SendState.SEND));
			paras.add(ModifyMarkState.UNCHANGE);
		} else {
			hsql += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>?  and a.modifyMark =? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(seqNum);
			parameters.add(complex.getId());
			parameters.add(ModifyMarkState.UNCHANGE);

			hql += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>?  and a.modifyMark =? ";
			paras.add(CommonUtils.getCompany().getId());
			paras.add(seqNum);
			paras.add(complex.getId());
			paras.add(ModifyMarkState.UNCHANGE);
		}

		this.batchUpdateOrDelete(hsql, parameters.toArray());

		if (isMaterial) {
			this.batchUpdateOrDelete(hql, paras.toArray());
		}
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
		String tableNameAfter = "";
		String tableNameBefore = "";
		if (isMaterial) {
			tableNameAfter = " EmsEdiMergerImgAfter ";
			tableNameBefore = " EmsEdiMergerImgBefore ";
		} else {
			tableNameAfter = " EmsEdiMergerExgAfter ";
			tableNameBefore = " EmsEdiMergerExgBefore ";
		}
		String hsqlAfter = "update " + tableNameAfter
				+ " a set a.complex.id = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(complex.getId());
		if (isSendData) {
			if (getIsEmsH2kSend()) {
				hsqlAfter += ", a.modifyMark=?  , a.sendState=? ";
				parameters.add(ModifyMarkState.MODIFIED);
				parameters.add(Integer.valueOf(SendState.WAIT_SEND));
			} else {
				// hsqlAfter += ", a.modifyMark=? ";
				// parameters.add(ModifyMarkState.MODIFIED);
			}
		}
		if (getIsEmsH2kSend()) {
			hsqlAfter += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>? and a.sendState =? and a.modifyMark =? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(seqNum);
			parameters.add(complex.getId());
			parameters.add(Integer.valueOf(SendState.SEND));
			parameters.add(ModifyMarkState.UNCHANGE);
		} else {
			hsqlAfter += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>? and a.modifyMark =? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(seqNum);
			parameters.add(complex.getId());
			parameters.add(ModifyMarkState.UNCHANGE);
		}
		this.batchUpdateOrDelete(hsqlAfter, parameters.toArray());
		// =================归并前================
		parameters.clear();
		String hsqlBefore = "update " + tableNameBefore
				+ " a set a.complex.id = ?";
		parameters.add(complex.getId());
		if (isSendData) {
			if (getIsEmsH2kSend()) {
				hsqlBefore += ", a.modifyMark=?  , a.sendState=? ";
				parameters.add(ModifyMarkState.MODIFIED);
				parameters.add(Integer.valueOf(SendState.WAIT_SEND));
			} else {
				// hsqlBefore += ", a.modifyMark=? ";
				// parameters.add(ModifyMarkState.MODIFIED);
			}
		}
		if (getIsEmsH2kSend()) {
			hsqlBefore += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>? and a.sendState =? and a.modifyMark =?  ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(seqNum);
			parameters.add(complex.getId());
			parameters.add(Integer.valueOf(SendState.SEND));
			parameters.add(ModifyMarkState.UNCHANGE);

		} else {
			hsqlBefore += " where   a.company.id = ? and a.seqNum=? and a.complex.id <>? and a.modifyMark =?  ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(seqNum);
			parameters.add(complex.getId());
			parameters.add(ModifyMarkState.UNCHANGE);
		}
		this.batchUpdateOrDelete(hsqlBefore, parameters.toArray());

		/*----------------归并前bom-------------------*/

		// parameters.clear();
		/*
		 * 2014-1-2升级时，客服小丽告知归并前BOM均不需要修改修改标志。因为编码变更不需重新发送BOM
		 */
		// hsqlBefore = "update EmsEdiMergerExgBom a set a.complex.id = ?";
		// ;
		// parameters.add(complex.getId());

		// if (isSendData) {
		// if (getIsEmsH2kSend()) {
		// hsqlBefore += ", a.modifyMark=?  , a.sendState=? ";
		// parameters.add(ModifyMarkState.MODIFIED);
		// parameters.add(Integer.valueOf(SendState.WAIT_SEND));
		// } else {
		// // hsqlBefore += ", a.modifyMark=? ";
		// // parameters.add(ModifyMarkState.MODIFIED);
		// }
		// }
		// if (getIsEmsH2kSend()) {
		// hsqlBefore +=
		// " where a.company.id = ? and a.complex.id = ? and a.sendState =? and a.modifyMark =?  ";
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(old.getId());
		// parameters.add(Integer.valueOf(SendState.SEND));
		// parameters.add(ModifyMarkState.UNCHANGE);
		//
		// } else {
		// hsqlBefore +=
		// " where a.company.id = ? and a.complex.id = ? and a.modifyMark =?  ";
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(old.getId());
		// parameters.add(ModifyMarkState.UNCHANGE);
		// }
		// this.batchUpdateOrDelete(hsqlBefore, parameters.toArray());
	}

	/**
	 * 更新所有内部归并的商品编码
	 * 
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllInnerMergeDataComplex(Boolean isMaterial,
			Complex complex, Integer seqNum) {
		String hsql = "update InnerMergeData a set a.hsAfterComplex.id = ?"
				+ " where a.hsAfterTenMemoNo=? and a.hsAfterComplex.id <>? and a.company.id = ?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(complex.getId());
		parameters.add(seqNum);
		parameters.add(complex.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (isMaterial) {// 料件
			hsql += " and  a.imrType=?";
			parameters.add("2");
		} else {// 成品
			hsql += " and  a.imrType=?";
			parameters.add("0");
		}
		this.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 通过报关单获得进出口类型为直接出口的申请单商品信息
	 * 
	 * @param customsDeclaration
	 */
	public List findMakelisttocustoms(CustomsDeclaration customsDeclaration) {
		String hsql = "select a.atcmergeaftercominfo from MakeListToCustoms a "
				+ "left join Customsdeclarationcomminfo b on a.customsinfo = b.id"
				+ "where b.customsDeclaration=?  and a.company.id= ? ";
		return this.find(hsql, new Object[] { customsDeclaration.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据条件（进出口类型，账册号，开始时间，结束时间,自选条件）取得报关单BY 申报日期
	 * 
	 * @param impExpFlag
	 *            进出口标志，进口为true，出口为false。
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param property
	 *            自选条件名
	 * @param value
	 *            自选条件值
	 * @return 在有效期内与指定的手册号匹配的进口报关单
	 */
	public List findCustomsDeclaration(boolean impExpFlag, String emsNo,
			Date beginDate, Date endDate, String property, Object value) {
		StringBuilder hql = new StringBuilder(300);
		List<Object> parameters = new ArrayList<Object>();
		hql.append(" select a from CustomsDeclaration as a where a.impExpFlag=? and a.company.id=? ");

		if (impExpFlag) {
			parameters.add(Integer.valueOf(ImpExpFlag.IMPORT));
		} else {
			parameters.add(Integer.valueOf(ImpExpFlag.EXPORT));
		}
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql.append(" and a.emsHeadH2k=? ");
			parameters.add(emsNo);
		}
		// 自选条件
		if (CommonUtils.notEmpty(property) && value != null) {
			hql.append(" and a." + property + "=? ");
			parameters.add(value);
		}

		if (beginDate != null) {
			hql.append(" and (a.declarationDate>=?  or a.declarationDate is null) ");
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql.append(" and (a.declarationDate<=?  or a.declarationDate is null) ");
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (beginDate != null || endDate != null) {
			hql.append(" order by a.serialNumber,a.declarationDate desc");
		}

		return this.find(hql.toString(), parameters.toArray());
	}

	/**
	 * 查找报关单商品的版本号，为最大的版本号
	 * 
	 */
	public Integer findMaxVersionCustoms(BaseCustomsDeclarationCommInfo info) {
		String hql = "select max(a.emsHeadH2kVersion.version) as maxVersion from  EmsHeadH2kBom a"
				+ " where a.company.id=? and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = ? "
				+ " and a.sendState='2'  and (a.emsHeadH2kVersion.isForbid='0' or a.emsHeadH2kVersion.isForbid is null)"
				+ "group by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(info.getCommSerialNo());
		List versions = this.find(hql.toString(), parameters.toArray());
		if (versions.size() > 0) {
			return Integer.valueOf(versions.get(0).toString());
		}
		return null;
	}

	public List findMotorCode(String billOfLading) {
		String hql = "select m  from MotorCode m where homeCard = '"
				+ billOfLading + "'";
		return this.find(hql.toString());
	}

	/**
	 * 查询料件耗用明细
	 * 
	 * @param request
	 * @param beginDate
	 *            报关单开始日期
	 * @param endDate
	 *            报关单结束日期
	 * @param projectDept
	 *            事业部
	 * @param imgSeqNums
	 *            料件序号
	 * @return
	 */
	public List findImgConsumption(Date beginDate, Date endDate,
			ProjectDept projectDept, Integer[] imgSeqNums, Integer impExpType) {
		ArrayList<Object> parameters = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.seqNum ,a.name,a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,");
		sb.append(" a.emsHeadH2kVersion.emsHeadH2kExg.name,a.emsHeadH2kVersion.emsHeadH2kExg.spec,");
		sb.append(" a.emsHeadH2kVersion.version,a.unitWear,a.wear,b.commAmount,b.baseCustomsDeclaration.serialNumber,");
		sb.append(" b.baseCustomsDeclaration.customsDeclarationCode,b.baseCustomsDeclaration.impExpType,");
		sb.append(" b.baseCustomsDeclaration.impExpDate,b.baseCustomsDeclaration.tradeMode.tradeFname");
		sb.append(" from EmsHeadH2kBom a ,CustomsDeclarationCommInfo b ");
		sb.append(" left outer join a.emsHeadH2kVersion ");
		sb.append(" left outer join b.baseCustomsDeclaration ");
		sb.append(" where a.emsHeadH2kVersion.emsHeadH2kExg.seqNum = b.commSerialNo ");
		sb.append(" and a.emsHeadH2kVersion.version = b.version ");
		sb.append(" and a.company.id= ? ");
		parameters.add(CommonUtils.getCompany().getId());

		sb.append(" and b.baseCustomsDeclaration.effective = ? ");
		parameters.add(true);

		sb.append(" and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState=? ");
		if (getIsEmsH2kSend()) {
			parameters.add(new Boolean(false));
			sb.append(" and a.sendState=? ");
			parameters.add(Integer.valueOf(SendState.SEND));
		} else {
			parameters.add(new Boolean(false));
			sb.append(" and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState=? ");
			parameters.add(DeclareState.PROCESS_EXE);
		}

		if (impExpType != null) {
			sb.append(" and b.baseCustomsDeclaration.impExpType = ? ");
			parameters.add(impExpType);
		} else {
			sb.append(" and b.baseCustomsDeclaration.impExpType in (?,?,?,?) ");
			parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
			parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
		}

		if (beginDate != null) {
			sb.append(" and b.baseCustomsDeclaration.declarationDate >= ? ");
			parameters.add(beginDate);
		}

		if (endDate != null) {
			sb.append(" and b.baseCustomsDeclaration.declarationDate <= ? ");
			parameters.add(endDate);
		}

		if (imgSeqNums != null && imgSeqNums.length > 0) {
			sb.append(" and a.seqNum in (" + StringUtils.join(imgSeqNums, ",")
					+ ")");
		}

		if (projectDept != null) {
			sb.append(" and b.projectDept.id = ? ");
			parameters.add(projectDept.getId());
		}

		sb.append(" order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.seqNum,a.emsHeadH2kVersion.version,b.baseCustomsDeclaration.serialNumber");

		System.out.println(sb.toString());

		return this.find(sb.toString(), parameters.toArray());
	}

	/**
	 * 统计查询料件耗用明细
	 * 
	 * @param request
	 * @param beginDate
	 *            报关单开始日期
	 * @param endDate
	 *            报关单结束日期
	 * @param projectDept
	 *            事业部
	 * @param imgSeqNums
	 *            料件序号
	 * @return
	 */
	public List findTotalImgConsumption(Date beginDate, Date endDate,
			ProjectDept projectDept, Integer[] imgSeqNums, Integer impExpType) {
		ArrayList<Object> parameters = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.seqNum ,a.name,a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,");
		sb.append(" a.emsHeadH2kVersion.emsHeadH2kExg.name,a.emsHeadH2kVersion.emsHeadH2kExg.spec,");
		sb.append(" a.emsHeadH2kVersion.version,a.unitWear,a.wear,");
		sb.append(" sum(case ");
		sb.append(" when b.baseCustomsDeclaration.impExpType = ? ");
		sb.append(" then b.commAmount ");
		sb.append(" when b.baseCustomsDeclaration.impExpType = ? ");
		sb.append(" then b.commAmount ");
		sb.append(" when b.baseCustomsDeclaration.impExpType = ? ");
		sb.append(" then -b.commAmount ");
		sb.append(" when b.baseCustomsDeclaration.impExpType = ? ");
		sb.append(" then b.commAmount ");
		sb.append("  end)");
		sb.append(" from EmsHeadH2kBom a ,CustomsDeclarationCommInfo b ");
		sb.append(" left outer join b.baseCustomsDeclaration ");
		sb.append(" left outer join a.emsHeadH2kVersion ");
		sb.append(" where  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum ");
		sb.append(" and b.version = a.emsHeadH2kVersion.version ");
		sb.append(" and a.company.id= ? ");

		parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
		parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出

		parameters.add(CommonUtils.getCompany().getId());

		sb.append(" and b.baseCustomsDeclaration.effective = ? ");
		parameters.add(true);

		sb.append(" and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState=? ");
		parameters.add(new Boolean(false));
		if (getIsEmsH2kSend()) {
			sb.append(" and a.sendState=? ");
			parameters.add(Integer.valueOf(SendState.SEND));
		} else {
			sb.append(" and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState=? ");
			parameters.add(DeclareState.PROCESS_EXE);
		}

		if (impExpType != null) {
			sb.append(" and b.baseCustomsDeclaration.impExpType = ? ");
			parameters.add(impExpType);
		} else {
			sb.append(" and b.baseCustomsDeclaration.impExpType in (?,?,?,?) ");
			parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);// 退厂返工
			parameters.add(ImpExpType.REWORK_EXPORT);// 返工复出
		}

		if (beginDate != null) {
			sb.append(" and b.baseCustomsDeclaration.declarationDate >= ? ");
			parameters.add(beginDate);
		}

		if (endDate != null) {
			sb.append(" and b.baseCustomsDeclaration.declarationDate <= ? ");
			parameters.add(endDate);
		}

		if (imgSeqNums != null && imgSeqNums.length > 0) {
			sb.append(" and a.seqNum in (" + StringUtils.join(imgSeqNums, ",")
					+ ")");
		}

		if (projectDept != null) {
			sb.append(" and b.projectDept.id = ? ");
			parameters.add(projectDept.getId());
		}

		sb.append(" group by a.seqNum ,a.name,a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,");
		sb.append(" a.emsHeadH2kVersion.emsHeadH2kExg.name,a.emsHeadH2kVersion.emsHeadH2kExg.spec,");
		sb.append(" a.emsHeadH2kVersion.version,a.unitWear,a.wear ");
		sb.append(" order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.seqNum,a.emsHeadH2kVersion.version");

		return this.find(sb.toString(), parameters.toArray());
	}
}