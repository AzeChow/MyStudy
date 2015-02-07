package com.bestway.bcus.cas.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeDataOrder;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.bill.entity.CasInnerMergeDataOrder;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.specificontrol.entity.CustomsDeclarationCommInfoBillCorresponding;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoBase;
import com.bestway.bcus.cas.specificontrol.entity.TempBillCorrespondingSearch;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 海关帐特殊控制 Dao
 * @author luosheng 2009/09/07
 */
public class CasSpecificontrolDao extends BaseDao {

	/**
	 * 显示单据资料主表来自类型
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型的单据资料主表
	 */
	public List findBillMaster(Integer impExpType) {
		List<Object> parameters = new ArrayList<Object>();
		String billMaster = BillUtil
				.getBillMasterTableNameByImpExpType(impExpType);
		String hsql = "select a from " + billMaster
				+ " a where a.company.id= ?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpType != null && impExpType != -1) {
			hsql += "and a.billType.code = ? ";
			parameters.add(getBillCode(impExpType));
		}
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 显示单据资料主表来自类型
	 * 
	 * @param list
	 *            单据表表头
	 * @return 根据指定表头的单据类型显示资料单据
	 */
	public List findBillDetail(List<BillMaster> list) {
		if (list == null || list.isEmpty()) {
			return new ArrayList();
		}
		BillMaster bm = list.get(0);
		int billType = bm.getBillType().getBillType();
		String billDetail = BillUtil.getBillDetailTableNameByBillType(billType);

		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from " + billDetail
				+ " a where a.company.id= ?  ";
		parameters.add(CommonUtils.getCompany().getId());

		for (int i = 0; i < list.size(); i++) {
			BillMaster billMaster = list.get(i);
			if (i == 0) {
				hsql += " and (a.billMaster.id = ? ";
			} else {
				hsql += " or a.billMaster.id = ? ";
			}
			if (i == list.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(billMaster.getId());
		}
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 查找单据的对应的单据明细(生效的记录)
	 * 
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            结束日期
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户
	 * @return 符合条件的生效的单据对应的单据明细
	 */
	public List<BillDetail> findBillDetail(Integer impExpType, ScmCoc scmCoc,
			Date beginData, Date endData, String name, String spec) {
		List<Object> parameters = new ArrayList<Object>();

		String billDetail = BillUtil
				.getBillDetailTableNameByImpExpType(impExpType);

		String hsql = "select a from "
				+ billDetail
				+ " a left join fetch a.billMaster left join fetch a.billMaster.billType where "
				+ "  a.company.id= ?          "
				+ " and a.billMaster.isValid = ? "
				+ " and ( a.hsAmount is not null ) "
				//
				// 是否对应(这里获得未对应的记录)
				//
				+ " and ( (a.customNo is null or a.customNo = '') or a.hsAmount-a.customNum>0 ) ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		if (impExpType != null && impExpType != -1) {
			hsql += "and a.billMaster.billType.code = ? ";
			parameters.add(getBillCode(impExpType));
		}
		if (scmCoc != null) {
			hsql += "and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginData != null) {
			hsql += "and a.billMaster.validDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.billMaster.validDate <= ? ";
			parameters.add(endData);
		}
		if (name != null && !"".equals(name)) {
			hsql += "and a.hsName = ? ";
			parameters.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql += "and a.hsSpec = ? ";
			parameters.add(spec);
		}
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 查找单据的对应的单据明细(生效的记录)
	 * 
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            结束日期
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户
	 * @return 符合条件的生效的单据对应的单据明细 (这是结转的对应) - 结转退货 == 料件类型
	 */
	public List<BillDetail> findBillDetailByCarryForwardMateriel(
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData,
			String name, String spec, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();

		String billDetail = BillUtil
				.getBillDetailTableNameByImpExpType(impExpType);

		String hsql = "select a from " + billDetail
				+ " a left join fetch a.billMaster "
				+ "   left join fetch a.billMaster.billType "
				+ "   left join fetch a.billMaster.scmCoc where "
				+ "  a.company.id= ?          "
				+ " and a.billMaster.isValid = ? "
				+ " and ( a.hsAmount is not null ) "
				//
				// 是否对应(这里获得未对应的记录)
				//
				+ " and ( (a.customNo is null or a.customNo = '') or a.hsAmount-a.customNum>0 ) ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		if (impExpType != null && impExpType != -1) {
			hsql += "and ( a.billMaster.billType.code = ? or   a.billMaster.billType.code = ? ) ";
			parameters.add("1004");
			parameters.add("1106");
		}
		if (scmCoc != null) {
			hsql += "and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginData != null) {
			hsql += "and a.billMaster.validDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.billMaster.validDate <= ? ";
			parameters.add(endData);
		}
		if (isLike == false) {
			if (name != null && !"".equals(name)) {
				hsql += "and a.hsName = ? ";
				parameters.add(name);
			}
			if (spec != null && !"".equals(spec)) {
				hsql += "and a.hsSpec = ? ";
				parameters.add(spec);
			}
		} else {
			if (name != null && !"".equals(name)) {
				hsql += "and a.hsName like ? ";
				parameters.add(name + "%");
			}
			if (spec != null && !"".equals(spec)) {
				hsql += "and a.hsSpec like ? ";
				parameters.add(spec + "%");
			}
		}
		//
		// billtype code 结转进口 1004 结转料件退货单 1106 要把结转料件退货排前面
		//
		String orderBy = " order by a.ptPart ASC, a.billMaster.scmCoc ASC ,"
				+ " a.billMaster.validDate DESC,"
				+ " a.billMaster.billType.code DESC ";

		hsql += orderBy;
		return super.find(hsql, parameters.toArray());
	}

	/***
	 * 获得单据对应资料
	 * 
	 * @param isTransfer
	 *            是否转厂包括结转退货单
	 * @param impExpType
	 * @param ptNoList
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<BillDetail> findBillDetailByBillCorresponding(
			boolean isTransfer, Integer impExpType, List<String> ptNoList,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<BillDetail> returnlist = new ArrayList<BillDetail>();
		List<String> pkNoListByPage = new ArrayList<String>();
		for (int i = 0; i < ptNoList.size(); i++) {
			pkNoListByPage.add(ptNoList.get(i));
			//
			// 100个参数查询一次
			//
			if ((i != 0 && (i % 100) == 0) || i == ptNoList.size() - 1) {
				// if ((i % 100) == 0 || i == ptNoList.size() - 1) {
				logger.info("第一 " + i + "个 料号查询 --- ");
				List<BillDetail> listProduct = this
						.findBillDetailByByBillCorrespondingByPage(isTransfer,
								impExpType, pkNoListByPage, scmCoc, beginDate,
								endDate);
				returnlist.addAll(listProduct);
				pkNoListByPage.clear();
			}
		}
		return returnlist;
	}

	/**
	 * 获得单据对应资料
	 * 
	 * @param isTransfer
	 *            是否转厂包括结转退货单
	 * @param isTransfer
	 * @param impExpType
	 * @param ptNoList
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<BillDetail> findBillDetailByByBillCorrespondingByPage(
			boolean isTransfer, Integer impExpType, List<String> ptNoList,
			ScmCoc scmCoc, Date beginDate, Date endDate) {

		String billDetail = BillUtil
				.getBillDetailTableNameByImpExpType(impExpType);
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select a from " + billDetail
				+ " a left join fetch a.billMaster  b"
				+ "   left join fetch a.billMaster.scmCoc c "
				+ " where  a.company.id=?  ";

		parameters.add(CommonUtils.getCompany().getId());

		for (int i = 0; i < ptNoList.size(); i++) {
			if (i == 0) {
				hsql += " and ( a.ptPart=?  ";
			} else {
				hsql += " or  a.ptPart=?  ";
			}
			if (i == ptNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(ptNoList.get(i));// 料号
		}

		hsql += " and a.billMaster.isValid = ? "
				+ " and ( a.hsAmount is not null ) "
				+ " and ( a.hsAmount-isNull(a.customNum,0)>0 ) ";
		parameters.add(true);

		if (scmCoc != null) {
			hsql += " and  b.scmCoc.id=?  ";
			parameters.add(scmCoc.getId());
		}
		//
		// 是否转厂包括结转退货单
		//
		if (isTransfer) {
			if (impExpType != null && impExpType != -1) {
				if (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType.intValue()) {
					//
					// 1004 结转进口
					// 1106 结转料件退货单
					//
					hsql += "and ( a.billMaster.billType.code = ? or  a.billMaster.billType.code = ? " +
							"or  a.billMaster.billType.code = ? or  a.billMaster.billType.code = ? ) ";
					parameters.add("1004");
					parameters.add("1106");
					
					parameters.add("1015");
					parameters.add("1016");

					logger.info("1106 结转料件退货单 ");
				} else if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType
						.intValue()) { // 成品 出口
					//
					// 2102 结转出口
					// 2009 结转成品退货单
					//
					hsql += "and ( a.billMaster.billType.code = ? or   a.billMaster.billType.code = ? " +
							"or   a.billMaster.billType.code = ? or   a.billMaster.billType.code = ? ) ";
					parameters.add("2102");
					parameters.add("2009");
					
					parameters.add("2011");
					parameters.add("2012");

					logger.info("2009 结转成品退货单 ");
				}
			}
		} else {
			if (impExpType != null && impExpType != -1) {
				
				if(impExpType==ImpExpType.TRANSFER_FACTORY_IMPORT){//结转进口（1015+）（1016-）
					hsql+="and (a.billMaster.billType.code = ? or a.billMaster.billType.code = ?" +
							"or a.billMaster.billType.code = ? )";
					parameters.add("1004");
					parameters.add("1015");
					parameters.add("1016");
				}else if(impExpType==ImpExpType.TRANSFER_FACTORY_EXPORT){//结转进口（2011+）（2012-）
					hsql+="and (a.billMaster.billType.code = ? or a.billMaster.billType.code = ?" +
					"or a.billMaster.billType.code = ? )";
					parameters.add("2102");
					parameters.add("2011");
					parameters.add("2012");
				}else{
					hsql += "and a.billMaster.billType.code = ? ";
					parameters.add(getBillCode(impExpType));
				}
			}
		}
		if (beginDate != null) {
			hsql += " and b.validDate >= ?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.validDate <= ?";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		//
		// billtype code 结转进口 1004 结转料件退货单 1106 要把结转料件退货排前面
		//
		String orderBy = " order by a.ptPart ASC, a.billMaster.scmCoc ASC ,"
				+ " a.billMaster.validDate DESC,"
				+ " a.billMaster.billType.code DESC ";
		//
		// billtype code 2102 结转出口 , 2009 结转成品退货单
		//
		if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType.intValue()) {
			orderBy = " order by a.ptPart ASC, a.billMaster.scmCoc ASC ,"
					+ " a.billMaster.validDate DESC,"
					+ " a.billMaster.billType.code ASC ";
		}
		hsql += orderBy;

		logger.info(hsql);

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 结转成品退货单
	 * 
	 * @param impExpType
	 * @param scmCoc
	 * @param beginData
	 * @param endData
	 * @param name
	 * @param spec
	 * @param isLike
	 * @return
	 */
	public List<BillDetail> findBillDetailByCarryForwardProduct(
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData,
			String name, String spec, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();

		String billDetail = BillUtil
				.getBillDetailTableNameByImpExpType(impExpType);

		String hsql = "select a from " + billDetail
				+ " a left join fetch a.billMaster "
				+ "   left join fetch a.billMaster.billType "
				+ "   left join fetch a.billMaster.scmCoc where "
				+ "  a.company.id= ?          "
				+ " and a.billMaster.isValid = ? "
				+ " and ( a.hsAmount is not null ) "
				//
				// 是否对应(这里获得未对应的记录)
				//
				+ " and ( (a.customNo is null or a.customNo = '') or a.hsAmount-a.customNum>0 ) ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		//
		// 2102 结转出口
		// 2009 结转成品退货单
		//
		if (impExpType != null && impExpType != -1) {
			hsql += "and ( a.billMaster.billType.code = ? or   a.billMaster.billType.code = ? ) ";
			parameters.add("2102");
			parameters.add("2009");
		}
		if (scmCoc != null) {
			hsql += "and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginData != null) {
			hsql += "and a.billMaster.validDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.billMaster.validDate <= ? ";
			parameters.add(endData);
		}
		if (isLike == false) {
			if (name != null && !"".equals(name)) {
				hsql += "and a.hsName = ? ";
				parameters.add(name);
			}
			if (spec != null && !"".equals(spec)) {
				hsql += "and a.hsSpec = ? ";
				parameters.add(spec);
			}
		} else {
			if (name != null && !"".equals(name)) {
				hsql += "and a.hsName like ? ";
				parameters.add(name + "%");
			}
			if (spec != null && !"".equals(spec)) {
				hsql += "and a.hsSpec like ? ";
				parameters.add(spec + "%");
			}
		}
		//
		// billtype code 2102 结转出口 , 2009 结转成品退货单
		//
		String orderBy = " order by a.ptPart ASC, a.billMaster.scmCoc ASC ,"
				+ " a.billMaster.validDate DESC,"
				+ " a.billMaster.billType.code ASC ";

		hsql += orderBy;
		return super.find(hsql, parameters.toArray());
	}

	// /**
	// * 获得单据对应资料
	// *
	// * @param isTransfer
	// * 是否转厂包括结转退货单
	// * @param isTransfer
	// * @param impExpType
	// * @param ptNoList
	// * @param scmCoc
	// * @param beginDate
	// * @param endDate
	// * @return
	// */
	// public List<BillDetail> findBillDetailByByBillCorrespondingByPage(
	// boolean isTransfer, Integer impExpType, List<String> ptNoList,
	// ScmCoc scmCoc, Date beginDate, Date endDate) {
	//
	// String billDetail = BillUtil
	// .getBillDetailTableNameByImpExpType(impExpType);
	// List<Object> parameters = new ArrayList<Object>();
	//
	// String hsql = "select a from " + billDetail
	// + " a left join fetch a.billMaster  b"
	// + "   left join fetch a.billMaster.scmCoc c "
	// + " where  a.company.id=?  ";
	//
	// parameters.add(CommonUtils.getCompany().getId());
	//
	// for (int i = 0; i < ptNoList.size(); i++) {
	// if (i == 0) {
	// hsql += " and ( a.ptPart=?  ";
	// } else {
	// hsql += " or  a.ptPart=?  ";
	// }
	// if (i == ptNoList.size() - 1) {
	// hsql += " ) ";
	// }
	// parameters.add(ptNoList.get(i));// 料号
	// }
	//
	// hsql += " and a.billMaster.isValid = ? "
	// + " and ( a.hsAmount is not null ) "
	// + " and ( a.hsAmount-isNull(a.customNum,0)>0 ) ";
	// parameters.add(true);
	//
	// if (scmCoc != null) {
	// hsql += " and  b.scmCoc.id=?  ";
	// parameters.add(scmCoc.getId());
	// }
	// //
	// // 是否转厂包括结转退货单
	// //
	// if (isTransfer) {
	// if (impExpType != null && impExpType != -1) {
	// if (ImpExpType.TRANSFER_FACTORY_IMPORT == impExpType.intValue()) {
	// //
	// // 1004 结转进口
	// // 1106 结转料件退货单
	// //
	// hsql +=
	// "and ( a.billMaster.billType.code = ? or  a.billMaster.billType.code = ? ) ";
	// parameters.add("1004");
	// parameters.add("1106");
	//
	// logger.info("1106 结转料件退货单 ");
	// } else if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType
	// .intValue()) { // 成品 出口
	// //
	// // 2102 结转出口
	// // 2009 结转成品退货单
	// //
	// hsql +=
	// "and ( a.billMaster.billType.code = ? or   a.billMaster.billType.code = ? ) ";
	// parameters.add("2102");
	// parameters.add("2009");
	//
	// logger.info("2009 结转成品退货单 ");
	// }
	// }
	// } else {
	// if (impExpType != null && impExpType != -1) {
	// hsql += "and a.billMaster.billType.code = ? ";
	// parameters.add(getBillCode(impExpType));
	// }
	// }
	// if (beginDate != null) {
	// hsql += " and b.validDate >= ?";
	// parameters.add(CommonUtils.getBeginDate(beginDate));
	// }
	// if (endDate != null) {
	// hsql += " and b.validDate <= ?";
	// parameters.add(CommonUtils.getEndDate(endDate));
	// }
	// //
	// // billtype code 结转进口 1004 结转料件退货单 1106 要把结转料件退货排前面
	// //
	// String orderBy = " order by a.ptPart ASC, a.billMaster.scmCoc ASC ,"
	// + " a.billMaster.validDate DESC,"
	// + " a.billMaster.billType.code DESC ";
	// //
	// // billtype code 2102 结转出口 , 2009 结转成品退货单
	// //
	// if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType.intValue()) {
	// orderBy = " order by a.ptPart ASC, a.billMaster.scmCoc ASC ,"
	// + " a.billMaster.validDate DESC,"
	// + " a.billMaster.billType.code ASC ";
	// }
	// hsql += orderBy;
	//
	// logger.info(hsql);
	//
	// return this.find(hsql, parameters.toArray());
	// }

	/**
	 * 查找单据的对应的单据明细(生效的记录)
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            结束日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @return 条件全不为空的单据对应的单据明细
	 */
	public List<BillDetail> findBillDetailByLike(Integer impExpType,
			ScmCoc scmCoc, Date beginData, Date endData, String name,
			String spec) {
		List<Object> parameters = new ArrayList<Object>();

		String billDetail = BillUtil
				.getBillDetailTableNameByImpExpType(impExpType);

		String hsql = "select a from "
				+ billDetail
				+ " a left join fetch a.billMaster left join fetch a.billMaster.billType where "
				+ "  a.company.id= ?          "
				+ " and a.billMaster.isValid = ? "
				+ " and ( a.hsAmount is not null ) "
				//
				// 是否对应(这里获得未对应的记录)
				//
				+ " and ( (a.customNo is null or a.customNo = '') or a.hsAmount-a.customNum>0 ) ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		if (impExpType != null && impExpType != -1) {
			hsql += "and a.billMaster.billType.code = ? ";
			parameters.add(getBillCode(impExpType));
		}
		if (scmCoc != null) {
			hsql += "and a.billMaster.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginData != null) {
			hsql += "and a.billMaster.validDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.billMaster.validDate <= ? ";
			parameters.add(endData);
		}
		if (name != null && !"".equals(name)) {
			hsql += "and a.hsName like ? ";
			parameters.add(name + "%");
		}
		if (spec != null && !"".equals(spec)) {
			hsql += "and a.hsSpec like ? ";
			parameters.add(spec + "%");
		}
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 获得单据编号
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 与进出口类型相对应的单据的单据编号
	 */
	private String getBillCode(Integer impExpType) {
		String temp = null;
		int i = impExpType;
		switch (i) {
		case ImpExpType.DIRECT_IMPORT:
			temp = "1003";// 直接进口：1003-----料件进口
			break;
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
			temp = "1004";// // 结转进口：1004----料件转厂
			break;
		case ImpExpType.MATERIAL_EXCHANGE:
			temp = "1102";// 料件退换
			break;
		case ImpExpType.MATERIAL_REOUT:
			temp = "1103";// 料件复出
			break;
		case ImpExpType.BACK_FACTORY_REWORK:
			temp = "2003";// 退厂返工
			break;
		case ImpExpType.DIRECT_EXPORT:
			temp = "2101";// 直接出口
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
			temp = "2102";// 结转出口
			break;
		case ImpExpType.REWORK_EXPORT:
			temp = "2104";// 返工复出
			break;
		}
		return temp;
	}

	/**
	 * 查询报关单商品信息来自bcs或bcus 进出口类型 客户 有效期 名称 规格
	 * 
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @param projectType
	 *            模块类型
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户
	 * @param beginData
	 *            开始申报日期
	 * @param endData
	 *            截止日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @return 根据单据类型获取报关单数据---料件
	 */
	public List findCustomsDeclarationCommInfo(Boolean isLike, int projectType,
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData,
			String name, String spec) {
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);// 获取（料件/成品/半成品）
		String hsql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hsql = " select distinct a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hsql = " select distinct a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hsql = " select distinct a from DzscCustomsDeclarationCommInfo a  ";
			break;
		}
		hsql += // ",FactoryAndFactualCustomsRalation s left join fetch
		// s.statCusNameRelationHsn "
		// + " where a.commName = s.statCusNameRelationHsn.cusName "
		// + " and ( a.commSpec=s.statCusNameRelationHsn.cusSpec or ((
		// a.commSpec is null or a.commSpec = '') " +
		// "and (s.statCusNameRelationHsn.cusSpec is null or
		// s.statCusNameRelationHsn.cusSpec = '') )) "
		// + " and a.complex = s.statCusNameRelationHsn.complex "
		// //+ " and ( a.baseCustomsDeclaration.emsHeadH2k = s.emsNo or (
		// a.baseCustomsDeclaration.emsHeadH2k is null and s.emsNo is null )) "
		// + " and ( a.unit=s.statCusNameRelationHsn.cusUnit or ( a.unit is null
		// and s.statCusNameRelationHsn.cusUnit is null )) "
		// + // 这一段是关联到
		// // StatCusNameRelationHsn 的条件
		//
		// " and s.statCusNameRelationHsn.materielType = ? " // 对应关系类别
		" left join fetch a.baseCustomsDeclaration "
				+ " where a.baseCustomsDeclaration.company.id = ? and "
				+ "  a.baseCustomsDeclaration.effective = ? and "
				+ "  a.baseCustomsDeclaration.impExpFlag != ?  ";
		// parameters.add(materielType);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(ImpExpFlag.SPECIAL);
		if (impExpType != null && impExpType != -1) {
			if (impExpType == ImpExpType.MATERIAL_EXCHANGE) {// 料件退换
				hsql += " and a.baseCustomsDeclaration.impExpType = ? and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
				hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
				parameters.add("0300"); // 来料料件退换
				parameters.add("0700"); // 进料料件退换
			} else if (impExpType == ImpExpType.MATERIAL_REOUT) {// 料件复出
				hsql += " and a.baseCustomsDeclaration.impExpType = ? and ( a.baseCustomsDeclaration.tradeMode.code = ? ";
				hsql += " or a.baseCustomsDeclaration.tradeMode.code = ? )";
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// 退料出口
				parameters.add("0265"); // 来料料件复出
				parameters.add("0664"); // 进料料件复出
			} else {
				hsql += "and a.baseCustomsDeclaration.impExpType = ? ";
				parameters.add(impExpType);
			}
		}
		if (scmCoc != null) {
			hsql += "and a.baseCustomsDeclaration.customer.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginData != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.baseCustomsDeclaration.declarationDate <= ? ";
			parameters.add(endData);
		}
		//
		// 当对应表中大类对应的十码为空时,查询返回
		//
		if (isLike == null || isLike == false) {
			if (name != null && !"".equals(name)) {
				hsql += " and a.cusName = ? ";
				parameters.add(name);
			}
			//
			// 当对应表中大类对应的十码为空时,查询返回
			//
			if (spec != null && !"".equals(spec)) {
				hsql += " and a.cusSpec = ? ";
				parameters.add(spec);
			}
		} else {
			if (name != null && !"".equals(name)) {
				hsql += " and a.commName like ? ";
				parameters.add(name + "%");
			}
			//
			// 当对应表中大类对应的十码为空时,查询返回
			//
			if (spec != null && !"".equals(spec)) {
				hsql += " and a.commSpec like ? ";
				parameters.add(spec + "%");
			}
			// else {
			// hsql += " and ( a.commSpec is null or  a.commSpec = '' )  ";
			// }
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查找报关单商品信息与海关帐单据的对应
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户
	 * @param beginData
	 *            开始日期
	 * @param endData
	 *            结束日期
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @return 符合指定的条件的报关单商品信息与海关帐单据的对应
	 */
	public List findCustomsDeclarationCommInfoBillCorresponding(
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfoBillCorresponding a "
				+ "	where a.company.id = ?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpType != null && impExpType != -1) {
			hsql += "and a.impExpType = ? ";
			parameters.add(impExpType);
		}
		if (scmCoc != null) {
			hsql += "and a.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginData != null) {
			hsql += "and a.impExpDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.impExpDate <= ? ";
			parameters.add(endData);
		}
		// if (name != null && !"".equals(name)) {
		// hsql += "and a.commName = ? ";
		// parameters.add(name);
		// }
		// if (spec != null && !"".equals(spec)) {
		// hsql += "and a.commSpec = ? ";
		// parameters.add(spec);
		// }
		return this.find(hsql, parameters.toArray());
	}

	
	
	
	
	
	
	/**
	 * 查找报关单商品信息与海关帐单据的对应
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param scmCoc
	 *            客户
	 * @param beginData
	 *            期初数
	 * @param endData
	 *            期末数
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @return 条件全不为空的报关单商品信息与海关帐单据的对应
	 */
	public List findCustomsDeclarationCommInfoBillCorrespondingByLike(
			Integer impExpType, ScmCoc scmCoc, Date beginData, Date endData,
			String name, String spec) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfoBillCorresponding a "
				+ " where a.company.id = ?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpType != null && impExpType != -1) {
			hsql += "and a.impExpType = ? ";
			parameters.add(impExpType);
		}
		if (scmCoc != null) {
			hsql += "and a.scmCoc.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (beginData != null) {
			hsql += "and a.impExpDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.impExpDate <= ? ";
			parameters.add(endData);
		}
		if (name != null && !"".equals(name)) {
			hsql += "and a.commName like ? ";
			parameters.add(name + "%");
		}
		if (spec != null && !"".equals(spec)) {
			hsql += "and a.commSpec like ? ";
			parameters.add(spec + "%");
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 保存报关单商品信息与海关帐单据的对应
	 * 
	 * @param c
	 *            报关单商品信息与海关帐单据的对应
	 */
	public void saveCustomsDeclarationCommInfoBillCorresponding(
			CustomsDeclarationCommInfoBillCorresponding c) {
		super.saveOrUpdate(c);
	}

	/**
	 * 查找对应表商品大类名称 注意：必须Distinct 因为名称有可能会重复。
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与物料类型匹配的商品大类名称
	 */
	public List findStatCusNameRelationName(String materielType) {
		List<Object[]> list = super
				.find(
						"select distinct a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec from FactoryAndFactualCustomsRalation a "
								+ "	where a.company.id = ? and a.statCusNameRelationHsn.materielType = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								materielType });
		// List returnList = new ArrayList();
		// for (Object[] item : list) {
		// String tmp = "";
		// if (item[0] != null)
		// tmp = (String) item[0];
		// if (item[1] != null)
		// tmp = tmp + "/" + (String) item[1];
		// returnList.add(tmp);
		// }
		return list;
	}
    /**
     * 查找客户列表
     */ 
	public List findIsCustomer() {
		List<Object[]> list = super
				.find("select a  from ScmCoc a  where a.company.id = ? and isCustomer='1'",
						new Object[] { CommonUtils.getCompany().getId()
								 });

		return list;
	}
	/**
	 * 查找供应商列表
	 */
	public List findIsManufacturer(){
		List<Object[]> list = super
		.find("select a  from ScmCoc a  where a.company.id = ? and isManufacturer='1'",
				new Object[] { CommonUtils.getCompany().getId()
						 });
		return list;
	}
	/**
	 * 查找对应表商品大类名称 注意：必须Distinct 因为名称有可能会重复。
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 与物料类型匹配的商品大类名称
	 */
	public List findStatCusNameRelationName2(String materielType) {
		return super
				.find(
						"select distinct a.statCusNameRelationHsn.cusName from FactoryAndFactualCustomsRalation a "
								+ "	where a.company.id = ? and a.statCusNameRelationHsn.materielType = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								materielType });

	}

	/**
	 * 根据大类规格查找相对应的报关规格
	 * 
	 * @param materielType
	 *            物料类型
	 * @param cusName
	 *            报关名称
	 * @return 与大类规格对应的报关规格
	 */
	public List findStatCusNameRelationSpec(String materielType, String cusName) {
		return super
				.find(
						"select distinct a.cusSpec from StatCusNameRelationHsn a "
								+ "	where a.cusName = ? and a.company.id = ? and a.materielType = ?",
						new Object[] { cusName,
								CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 查找报关商品信息，来自对应的大类信息
	 * 
	 * @param complexCode
	 *            商品编码
	 * @param name
	 *            名称
	 * @param spec
	 *            规格
	 * @param materielType
	 *            物料类型
	 * @return 大类信息对应的报关商品信息
	 */
	public List findStatCusNameRelationHsn(String complexCode, String name,
			String spec, String materielType) {
		return super.find("select a  from StatCusNameRelationHsn a "
				+ "	where a.company.id = ? "
				+ "	and a.statCusNameRelation.complex.code = ? "
				+ " and a.statCusNameRelation.cusSpec = ? "
				+ " and a.statCusNameRelation.cusName = ? "
				+ " and a.statCusNameRelation.materielType = ? ", new Object[] {
				CommonUtils.getCompany().getId(), complexCode, spec, name,
				materielType });
	}

	/**
	 * 查找合同协议号
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param projectType
	 *            模块信息
	 * @return 联网监管或纸质手册生效的报关信息中的合同协议号
	 */
	public List findEmsNo(int projectType) {
		String hsql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hsql = "select a.emsNo from EmsHeadH2k a where a.company.id= ? and a.declareState=? and a.historyState=?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(DeclareState.PROCESS_EXE);
			parameters.add(false);
			break;
		case ProjectType.BCS:
			hsql = "select a.emsNo from Contract a where a.company= ? and ( a.isCancel=?  or a.declareState=? ) ";
			parameters.add(CommonUtils.getCompany());
			parameters.add(true);
			parameters.add(DeclareState.PROCESS_EXE);
			break;
		case ProjectType.DZSC:
			hsql = "select a.emsNo from DzscEmsPorHead a where a.company.id= ? and a.declareState = ?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(DeclareState.PROCESS_EXE);
			break;
		}
		return super.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找报关单号
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param projectType
	 *            模块
	 * @return 联网监管或纸质手册的生效的特殊报关单中报关单号
	 */
	public List findCustomsDeclarationCode(Integer impExpType, int projectType,
			String emsNo) {
		String hsql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hsql = " select a.customsDeclarationCode from CustomsDeclaration a ";
			break;
		case ProjectType.BCS:
			hsql = "select a.customsDeclarationCode  from BcsCustomsDeclaration a  ";
			break;
		case ProjectType.DZSC:
			hsql = "select a.customsDeclarationCode  from DzscCustomsDeclaration a ";
			break;
		}

		hsql += " where a.company.id = ? and " + "  a.effective = ? and "
				+ "  a.impExpFlag != ?  and a.impExpType = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(ImpExpFlag.SPECIAL);
		parameters.add(impExpType);
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hsql += " and a.emsHeadH2k = ?  ";
			parameters.add(emsNo);
		}
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 查找单据是否记帐
	 * 
	 * @param materielType
	 *            物料类型
	 * @param isEffective
	 *            是否生效
	 * @return 已记帐的单据
	 */
	public List findBillMaster(String materielType, boolean isEffective) {
		String tableName = BillUtil
				.getBillMasterTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		return this
				.find(
						"select a from "
								+ tableName
								+ " a where a.company= ? and a.keepAccounts = ? and a.isValid = ?  ",
						new Object[] { CommonUtils.getCompany(), isEffective,
								true });
	}

	/**
	 * 根据物料类型查找单据
	 * 
	 * @param materieltype
	 *            物料类型
	 * @return 与指定的物料类型匹配并且没有记帐的单据
	 */
	public List findBillMasterWithMaterielType(String materieltype) {
		List<Object> parameters = new ArrayList<Object>();
		String billMaster = BillUtil
				.getBillMasterTableNameByMaterielType(materieltype);
		parameters.add(CommonUtils.getCompany());
		parameters.add(false);
		final String hsql = "select bm from "
				+ billMaster
				+ " as bm "
				+ " where bm.company = ? and ( bm.keepAccounts is null or bm.keepAccounts=? )";
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 执行 回卷操作, 把更新不成功的对象返回 更新不成功的对象,全部成功返回size为0的List.
	 * 
	 * @param billMasterList
	 *            单据头
	 */
	public void rollbackBillMasterAll(List<BillMaster> billMasterList) {
		for (int i = 0, n = billMasterList.size(); i < n; i++) {
			BillMaster billMaster = billMasterList.get(i);
			billMaster.setIsValid(false);
			super.saveOrUpdate(billMaster);
		}
	}

	/**
	 * 保存全部单据 更新不成功的对象,全部成功返回size为0的List
	 * 
	 * @param billMasterList
	 *            单据头
	 */
	public void saveBillMasterAll(List<BillMaster> billMasterList) {
		for (int i = 0, n = billMasterList.size(); i < n; i++) {
			BillMaster billMaster = billMasterList.get(i);
			billMaster.setIsValid(true);
			super.saveOrUpdate(billMaster);
		}
	}

	/**
	 * 查找所有仓库的信息
	 * 
	 * @return 仓库信息
	 */
	public List<WareSet> findWareSet() {
		String hsql = "select wareset from WareSet";
		return super.find(hsql);
	}

	/**
	 * 查找所有工厂名称信息
	 * 
	 * @param kindCode
	 *            物料类别属性
	 * @return 工厂名称信息
	 */
	public List findFactoryNames(String kindCode) {
		return super.find(
				"select m from Materiel as m where m.scmCoi.coiProperty = ?",
				new Object[] { kindCode });
	}

	/**
	 * 查询单据头来自单据明细
	 * 
	 * @param materielType
	 *            商口类型
	 * @param billtype
	 *            单据类型
	 * @param client
	 *            客户名称
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            截止日期
	 * @return 与指定条件匹配的单据头
	 */
	public List<BillMaster> findBillMasterFromBillDetail(String materielType,
			String billtype, String client, Date startdate, Date enddate) {
		List<BillMaster> listOneMostlyBillType = new ArrayList<BillMaster>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();

		String tableName = BillUtil
				.getBillMasterTableNameByMaterielTypeName(materielType);
		hsql.append(" select a from " + tableName
				+ " a left join fetch a.billType b "
				+ " left join fetch a.scmCoc c ");
		hsql.append(" where a.company = ? ");
		params.add(CommonUtils.getCompany());
		if (billtype != null) {
			hsql.append(" and a.billType.name=? ");
			params.add(billtype);
		}
		if (client != null) {
			hsql.append(" and a.scmCoc.name=? ");
			params.add(client);
		}
		if (startdate != null) {
			hsql.append(" and a.validDate>=? ");
			params.add(startdate);
		}
		if (enddate != null) {
			hsql.append(" and a.validDate<=? ");
			params.add(enddate);
		}
		hsql.append(" order by a.billNo asc ");
		listOneMostlyBillType = find(hsql.toString(), params.toArray());
		return listOneMostlyBillType;
	}

	/**
	 * 获得料号和折算比(ptNo,UnitConvert) 来自 工厂物料(StatCusNameRelationMt)
	 * 
	 * @param materielType
	 *            大类对应类别
	 * @param company
	 *            公司
	 * @return 与指定公司类别匹配的工厂物料的料号 折算比
	 */
	public List findPtNoAndUnitConvert(String materielType, Company company) {
		String hsql = "select a.materiel.ptNo , a.unitConvert ,"
				+ "   a.statCusNameRelation "
				+ "  from StatCusNameRelationMt a left join a.materiel m  "
				+ " where a.statCusNameRelation.company = ? and a.statCusNameRelation.materielType= ? "
				+ " order by a.materiel.ptNo";
		return this.find(hsql, new Object[] { company, materielType });
	}

	/**
	 * 获得料号和折算比(ptNo,UnitConvert) 来自 工厂物料(materiel)
	 * 
	 * @param materielType
	 *            大类对应类别
	 * @param company
	 *            公司
	 * @return 与指定公司类别匹配的工厂物料的料号 折算比
	 */
	public List findPtNoAndUnitConvertFromRelation(String materielType,
			Company company) {
		String hsql = "select a.statCusNameRelationHsn,a.materiel.ptNo,a.unitConvert "
				+ "from FactoryAndFactualCustomsRalation a "
				+ "where a.company = ? and a.statCusNameRelationHsn.materielType= ? "
				+ "order by a.materiel.ptNo";
		return this.find(hsql, new Object[] { company, materielType });
	}

	/**
	 * 查询工厂物料内容
	 * 
	 * @param materielType
	 *            大类对应类别
	 * @param company
	 *            公司
	 * @return 与商品大类关联的按料号排序的工厂物料
	 */
	public List findStatCusNameRelationMt(String materielType, Company company) {
		String hsql = "select a"
				+ "  from StatCusNameRelationMt a left join fetch a.materiel m left join fetch a.statCusNameRelation "
				+ " where a.statCusNameRelation.company = ? and a.statCusNameRelation.materielType= ? "
				+ " order by a.materiel.ptNo";
		return this.find(hsql, new Object[] { company, materielType });
	}

	/**
	 * 从对应关系中查询工厂物料内容
	 * 
	 * @param materielType
	 *            对应关系类别
	 * @param company
	 *            公司
	 * @return 与对应关系关联的按料号排序的工厂物料
	 */
	public List findMaterielFromRalation(String materielType, Company company) {
		String hsql = "select a "
				+ "from FactoryAndFactualCustomsRalation a "
				+ "where a.company = ? and a.statCusNameRelationHsn.materielType= ? "
				+ "order by a.materiel.ptNo";
		return this.find(hsql, new Object[] { company, materielType });
	}

	/**
	 * 保存已对应单
	 * 
	 * @param base
	 */
	public void saveMakeBillCorrespondingInfo(MakeBillCorrespondingInfoBase base) {
		super.saveOrUpdate(base);
	}

	/**
	 * 删除对应关系
	 * 
	 * @param base
	 */
	public void deleteMakeBillCorrespondingInfo(
			MakeBillCorrespondingInfoBase base) {
		super.delete(base);
	}

	/**
	 * 获取已对应的单据
	 * 
	 * @param impExpType
	 * @param scmCoc
	 * @param beginData
	 * @param endData
	 * @param name
	 * @param spec
	 * @return
	 */
	public List findMakeBillCorrespondingInfo(Integer impExpType,
			ScmCoc scmCoc, Date beginData, Date endData, String name) {
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);// 获取（料件/成品/半成品）
		String tableName = BillUtil
				.getMakeBillCorrespondingInfoTableName(materielType);

		List<Object> parameters = new ArrayList<Object>();

		String hsql = " select a from  " + tableName + "  a "
				+ " where a.company.id = ?  ";

		parameters.add(CommonUtils.getCompany().getId());

		if (impExpType != null && impExpType != -1) {
			hsql += "and a.impExpType = ? ";
			parameters.add(impExpType);
		}
		if (beginData != null) {
			hsql += "and a.validDate >= ? ";
			parameters.add(beginData);
		}
		if (endData != null) {
			hsql += "and a.validDate <= ? ";
			parameters.add(endData);
		}
		if (scmCoc != null) {
			hsql += "and a.scmCoc = ? ";
			parameters.add(scmCoc);
		}
		// if (name != null && !"".equals(name)) {
		// hsql += " and a.commName = ? ";
		// parameters.add(name);
		// }
		// //
		// // 当对应表中大类对应的十码为空时,查询返回
		// //
		// if (spec != null && !"".equals(spec)) {
		// hsql += " and a.commSpec = ? ";
		// parameters.add(spec);
		// } else {
		// hsql += " and ( a.commSpec is null or a.commSpec = '' ) ";
		// }
		return this.find(hsql, parameters.toArray());
	}



	/**
	 * 查询已对应的单据与报关单信息
	 * 
	 * @param temp
	 * @return
	 */
	public List findMakeBillCorrespondingInfo(TempBillCorrespondingSearch temp) {

		Integer impExpType = temp.getImpExpType();
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);// 获取（料件/成品/半成品）
		String tableName = BillUtil
				.getMakeBillCorrespondingInfoTableName(materielType);

		List<Object> parameters = new ArrayList<Object>();

		String hsql = " select a from  " + tableName + "  a "
				+ " where a.company.id = ?  ";

		parameters.add(CommonUtils.getCompany().getId());

		if (impExpType != null && impExpType != -1) {
			hsql += "and a.impExpType = ? ";
			parameters.add(impExpType);
		}
		Date beginData = temp.getBeginDate();
		if (beginData != null) {
			hsql += "and a.validDate >= ? ";
			parameters.add(beginData);
		}
		Date endDate = temp.getEndDate();
		if (endDate != null) {
			hsql += "and a.validDate <= ? ";
			parameters.add(endDate);
		}
		ScmCoc scmCoc = temp.getScmCoc();
		if (scmCoc != null) {
			hsql += "and a.scmCoc = ? ";
			parameters.add(scmCoc);
		}
		String commName = temp.getCommName();
		if (commName != null && !"".equals(commName)) {
			hsql += " and a.commName = ? ";
			parameters.add(commName);
		}
		String commSpec = temp.getCommSpec();
		//
		// 当对应表中大类对应的十码为空时,查询返回
		//
		if (commSpec != null && !"".equals(commSpec)) {
			hsql += " and a.commSpec = ? ";
			parameters.add(commSpec);
		}
		// } else {
		// hsql += " and ( a.commSpec is null or a.commSpec = '' ) ";
		// }

		String ptPart = temp.getPtPart();
		String endPtPart = temp.getEndPtPart();
		// 工厂料号不等于空,结束料号为空时
		if (ptPart != null && !ptPart.equals("")
				&& (endPtPart == null || endPtPart.equals(""))) {
			hsql += " and a.ptPart = ? ";
			parameters.add(ptPart);
		} else if (ptPart != null && !ptPart.equals("")// 工厂料号不等于空,结束料号不为空时
				&& endPtPart != null && !endPtPart.equals("")) {
			hsql += " and ( a.ptPart >= ? and a.ptPart <= ? ) ";
			parameters.add(ptPart);
			parameters.add(endPtPart);
		}

		String ptName = temp.getPtName();
		if (ptName != null && !"".equals(ptName)) {
			hsql += " and a.ptName = ? ";
			parameters.add(ptName);
		}

		String customsDeclarationCode = temp.getCustomsDeclarationCode();
		if (customsDeclarationCode != null
				&& !"".equals(customsDeclarationCode)) {
			hsql += " and a.customsDeclarationCode = ? ";
			parameters.add(customsDeclarationCode);
		}

		String emsHeadH2k = temp.getEmsHeadH2k();
		if (emsHeadH2k != null && !"".equals(emsHeadH2k)) {
			hsql += " and a.emsHeadH2k = ? ";
			parameters.add(emsHeadH2k);
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示单据资料明细表来自Id和类型
	 * 
	 * @param id
	 *            明细表id
	 * @param sBillType
	 *            单据类型
	 * @return 与指定表名对应的单据明细
	 */
	public BillDetail findBillDetailById(String id, int impExpType) {

		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return null;
		}
		String hsql = "select a  from " + tableName
				+ " as a  where a.company= ? and a.id =? ";

		List list = this.find(hsql,
				new Object[] { CommonUtils.getCompany(), id });
		if (list != null && !list.isEmpty()) {
			return (BillDetail) list.get(0);
		}
		return null;

	}

	/**
	 * 获取唯一料件列表
	 * 
	 * @param materielType
	 * @param name
	 * @param spec
	 * @param beginData
	 * @param endData
	 * @return
	 */
	public List findMaterielptNo(String materielType, String name, String spec,
			Date beginData, Date endData) {
		String hsql = " select distinct c.ptNo from FactoryAndFactualCustomsRalation a "
				+ " left join  a.statCusNameRelationHsn b  "
				+ " left join a.materiel c where a.company.id=? "
				+ "and b.cusName = ? and  b.cusSpec = ?  and b.materielType = ? ";
		List praralist = new ArrayList();
		praralist.add(CommonUtils.getCompany().getId());
		praralist.add(name);
		praralist.add(spec);
		praralist.add(materielType);
		if (beginData != null) {
			hsql += " and   b.emsBeginDate >= ?  ";
			praralist.add(beginData);
		}
		if (endData != null) {
			hsql += " and   b.emsBeginDate <= ?  ";
			praralist.add(endData);
		}
		List list = this.find(hsql, praralist.toArray());
		return list;
	}

	/**
	 * 获取唯一料件列表
	 * 
	 * @param materielType
	 * @param name
	 * @param spec
	 * @param beginData
	 * @param endData
	 * @return
	 */
	public List<Object[]> findMaterielptNoTwo(String materielType,
			String nameSpec, Date beginData, Date endData) {
		String hsql = " select distinct c.ptNo,b.cusName,b.cusSpec from FactoryAndFactualCustomsRalation a "
				+ " left join  a.statCusNameRelationHsn b  "
				+ " left join a.materiel c where a.company.id=? "
				+ "and b.materielType = ? ";
		List praralist = new ArrayList();
		praralist.add(CommonUtils.getCompany().getId());
		// praralist.add(name);
		// praralist.add(spec);
		praralist.add(materielType);
		if (beginData != null) {
			hsql += " and   b.emsBeginDate >= ?  ";
			praralist.add(beginData);
		}
		if (endData != null) {
			hsql += " and   b.emsBeginDate <= ?  ";
			praralist.add(endData);
		}
		List<Object[]> list = this.find(hsql, praralist.toArray());
		return list;
	}

	/**
	 * 获取报关单与单据对应信息
	 * 
	 * @param customsDeclarationId
	 * @param customsDeclarationCommInfoId
	 * @return
	 */
	public CustomsDeclarationCommInfoBillCorresponding findCustomsDeclarationCommInfoBillCorresponding(
			String customsDeclarationId, String customsDeclarationCommInfoId) {

		String hsql = "select a  from CustomsDeclarationCommInfoBillCorresponding "
				+ "as a  where a.company= ? and a.customsDeclarationId =?  and a.customsDeclarationCommInfoId = ? ";

		List list = this.find(hsql, new Object[] { CommonUtils.getCompany(),
				customsDeclarationId, customsDeclarationCommInfoId });
		if (list != null && !list.isEmpty()) {
			return (CustomsDeclarationCommInfoBillCorresponding) list.get(0);
		}
		return null;

	}

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	public CasInnerMergeDataOrder findCasInnerMergeDataOrder() {
		List list = this.find(" select a from CasInnerMergeDataOrder as a "
				+ "  where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (CasInnerMergeDataOrder) list.get(0);
		} else {
			CasInnerMergeDataOrder order = new CasInnerMergeDataOrder();
			this.saveOrUpdate(order);
			return order;
		}
	}

	/**
	 * 保存 CasInnerMergeDataOrder
	 * 
	 * @param order
	 * @return
	 */
	public CasInnerMergeDataOrder saveCasInnerMergeDataOrder(
			CasInnerMergeDataOrder order) {
		this.saveOrUpdate(order);
		return order;
	}

}
