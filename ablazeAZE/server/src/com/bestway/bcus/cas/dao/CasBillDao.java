/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillTempMaster;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempContainer;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * @author luosheng 海关帐单据 DAO 2009/08/10
 */
public class CasBillDao extends BaseDao {

	/**
	 * 检查转厂单据中进出货单据导入的手册号与备案号
	 * 
	 * @param customsEnvelopBillNo
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */
	public List checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum(
			String customsEnvelopBillNo, String emsNo, Integer seqNum) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from CustomsEnvelopCommodityInfo a "
				+ " where a.customsEnvelopBill.customsEnvelopBillNo=? "
				+ " and a.company.id= ? ";
		paramters.add(customsEnvelopBillNo);
		paramters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !"".equals(emsNo)) {
			hsql += " and a.emsNo=?  ";
			paramters.add(emsNo);
		}
		if (seqNum != null && !"".equals(seqNum)) {
			hsql += " and a.seqNum=?  ";
			paramters.add(seqNum);
		}
		System.out.println("hsql==" + hsql + "=customsEnvelopBillNo="
				+ customsEnvelopBillNo + " emsNo=" + emsNo + " seqNum="
				+ seqNum);
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 返回详细的报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @return list 报关商品名称,规格列表
	 */
	public List findDistinctHsName(String materielType) {

		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a.hsName,a.hsSpec from " + tableName
				+ " as a " + "  where a.company= ? and a.billMaster.isValid=? ";

		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				new Boolean(true) });
	}

	/**
	 * 按单据名称返回整个临时单据
	 * 
	 * @param mostlyBillTypeName
	 *            接近单据类型名称
	 * @param billTypeName
	 *            单据类型名称
	 * @return list 临时单据明细
	 */
	public List findBillTempMasterByBillTypeName(String mostlyBillTypeName,
			String billTypeName, Date beginDate, Date endDate) {
		BillMaster billMaster;
		List listA = new ArrayList();
		List p = new ArrayList();
		List list;
		String tableName = BillUtil
				.getBillMasterTableNameByMaterielTypeName(mostlyBillTypeName);
		String hsql = "select a from " + tableName + " as a "
				+ " left join fetch a.billType " + " left join fetch a.scmCoc "
				+ " where a.billType.name=? " + " and a.company=? ";
		p.add(billTypeName);
		p.add(CommonUtils.getCompany());
		if (beginDate != null && endDate == null) {
			hsql += " and a.validDate>=? ";
			p.add(beginDate);
		} else if (beginDate == null && endDate != null) {
			hsql += " and a.validDate<=? ";
			p.add(endDate);
		} else if (beginDate != null && endDate != null) {
			hsql += " and a.validDate>=? and a.validDate<=? ";
			p.add(beginDate);
			p.add(endDate);
		}
		// String hsql = "select a.billMaster.billNo " +
		// " a.billMaster.billType.name " +
		// " a.billMaster.validDate " +
		// " a.billMaster.scmCoc.name " +
		// " a.wareSet.name " +
		// " a.note " +
		// " from "+tableName+"as a " +
		// " left join fetch a.billMaster " +
		// " left join fetch a.billMaster.billType " +
		// " where a.billMaster.billType.name='" + billTypeName +
		// " ' and a.billMaster.company='"+CommonUtils.getCompany().getId()+"' "
		// ;
		// // + "group by a.billMaster.id";
		list = this.find(hsql, p.toArray());
		for (int i = 0; i < list.size(); i++) {
			billMaster = (BillMaster) list.get(i);
			BillTempMaster listBillTempDetail = new BillTempMaster();
			listBillTempDetail.setBillMaster(billMaster);
			listBillTempDetail.setIsSelected(false);
			listA.add(listBillTempDetail);
		}
		return listA;
	}

	/**
	 * 按单据临时列表的单据表头的单据类型查找单据明细
	 * 
	 * @param list
	 *            临时单据表头
	 * @param mostlyBillTypeName
	 *            接近单据类型名称
	 * @return listBillDetail 指定表明对应的单据
	 */
	public List findBillDetailByBillTypeName(List list,
			String mostlyBillTypeName) {
		List listTempBillDetail = new ArrayList();
		List listBillDetail = new ArrayList();
		String hsql;
		BillMaster billMaster;
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielTypeName(mostlyBillTypeName);
		for (int i = 0; i < list.size(); i++) {
			listTempBillDetail = null;
			hsql = null;
			billMaster = ((BillTempMaster) list.get(i)).getBillMaster();
			hsql = "select a from " + tableName + " as a "
					+ " left join fetch a.billMaster "
					+ " left join fetch a.billMaster.billType "
					+ " left join fetch a.complex "
					+ " left join fetch a.ptUnit " + " where a.billMaster=? "
					+ " and a.company=? ";
			listTempBillDetail = this.find(hsql, new Object[] { billMaster,
					CommonUtils.getCompany() });
			if (listTempBillDetail != null)
				for (int j = 0; j < listTempBillDetail.size(); j++) {
					listBillDetail.add((BillDetail) listTempBillDetail.get(j));
				}
		}
		return listBillDetail;

	}

	/**
	 * 返回distinct工厂商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 已经生效的工厂商品名称
	 */
	public List findDistinctPtName(String materielType) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);

		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a.ptName,a.ptSpec from " + tableName
				+ " as a " + "  where a.company= ? and a.billMaster.isValid=? ";

		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				new Boolean(true) });
	}

	/**
	 * 返回详细的 工厂商品（已记帐）
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 已经记帐的商品名称,规格
	 */
	public List findDistinctPtNameAndWrite(String materielType) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a.ptName,a.ptSpec from " + tableName
				+ " as a "
				+ "  where a.company= ? and a.billMaster.keepAccounts=? ";

		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				new Boolean(true) });

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
	public BillDetail findBillDetailById(String id, int sBillType) {

		String tableName = BillUtil.getBillDetailTableNameByBillType(sBillType);
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
	 * 显示单据资料明细表来自表头id和单据类型
	 * 
	 * @param masterId
	 *            表头id
	 * @param sBillType
	 *            单据类型
	 * @return 工厂单据明细
	 */
	public List<BillDetail> findBillDetail(String masterId, int sBillType) {

		String tableName = BillUtil.getBillDetailTableNameByBillType(sBillType);
		if (tableName == null) {
			return new ArrayList<BillDetail>();
		}
		String hsql = " select a from " + tableName
				+ " as a  where  a.billMaster.id =? and a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(masterId);
		parameters.add(CommonUtils.getCompany());
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 显示单据资料表头来自单据类型
	 * 
	 * @param billType
	 *            单据类型
	 * @return list 与指定单据类型相匹配表名的表头
	 */
	public List<BillMaster> findBillMaster(BillType billType) {

		int sBillType = billType.getBillType();

		String tableName = BillUtil.getBillMasterTableNameByBillType(sBillType);
		if (tableName == null) {
			return new ArrayList<BillMaster>();
		}
		String hsql = "select a  from " + tableName
				+ " as a  where  a.billType.id =? and a.company= ? ";

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(billType.getId());
		parameters.add(CommonUtils.getCompany());
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 显示常用物料主档
	 * 
	 * @param billType
	 *            单据类型
	 * @return list 与指定单据类型相匹配表名的表头
	 */
	public List findMateriel() {
		return this.find("select a.ptNo from Materiel a where a.company= ?",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 显示单据资料表头来自类型 属性 实体值等
	 * 
	 * @param billType
	 *            单据类型
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
	 * @return 单据表头资料
	 */
	public List<BillMaster> findBillMaster(BillType billType, int index,
			int length, String property, Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		int sBillType = billType.getBillType();

		String tableName = BillUtil.getBillMasterTableNameByBillType(sBillType);
		if (tableName == null) {
			return new ArrayList<BillMaster>();
		}
		String hsql = "select a  from " + tableName
				+ " as a  where  a.billType.id =? and a.company= ? ";

		parameters.add(billType.getId());
		parameters.add(CommonUtils.getCompany());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		System.out.println("wss tableName = " + tableName);
		System.out.println("wss hsql====================" + hsql);
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 查询工厂单据
	 * @param billType
	 * @param billNo
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<BillMaster> findBillMaster(BillType billType, String billNo,
			String scmCoc, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		int sBillType = billType.getBillType();

		String tableName = BillUtil.getBillMasterTableNameByBillType(sBillType);
		if (tableName == null) {
			return new ArrayList<BillMaster>();
		}
		String hsql = "select a  from " + tableName
				+ " as a  where  a.billType.id =? and a.company= ? ";

		parameters.add(billType.getId());
		parameters.add(CommonUtils.getCompany());
		if (billNo != null && !"".equals(billNo)) {
			hsql += " and a.billNo=? ";
			parameters.add(billNo);
		}
		if (scmCoc != null && !"".equals(scmCoc)) {
			hsql += " and a.scmCoc.code=? ";
			parameters.add(scmCoc);
		}
		if (beginDate != null) {
			hsql += " and a.validDate >=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.validDate <=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 显示单据资料来自类型 是否生效等等
	 * 
	 * @param billType
	 *            单据类型
	 * @param isEffect
	 *            是否生效
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
	 * @return 与单据类型匹配的符合条件的单据资料
	 */
	public List<BillMaster> findBillMaster(BillType billType, boolean isEffect,
			int index, int length, String property, Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		int sBillType = billType.getBillType();

		String tableName = BillUtil.getBillMasterTableNameByBillType(sBillType);
		if (tableName == null) {
			return new ArrayList<BillMaster>();
		}
		String hsql = "";
		if (isEffect == false) {
			hsql = "select a  from "
					+ tableName
					+ " as a  where  a.billType.id =? and a.company= ? and ( a.isValid = ? or a.isValid is null ) ";
		} else {
			hsql = "select a  from "
					+ tableName
					+ " as a  where  a.billType.id =? and a.company= ? and a.isValid = ? ";
		}
		parameters.add(billType.getId());
		parameters.add(CommonUtils.getCompany());
		parameters.add(isEffect);

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 删除工厂明细资料
	 * 
	 * @param billMaster
	 *            单据表头
	 */
	public void deleteBillDetailByMaster(BillMaster billMaster) {
		int sBillType = billMaster.getBillType().getBillType();
		String tableName = BillUtil.getBillDetailTableNameByBillType(sBillType);
		this.batchUpdateOrDelete(" delete from " + tableName
				+ " a where a.billMaster = ? ", billMaster);
	}

	/**
	 * 通过表头删除表体
	 * 
	 * @param billMaster
	 *            单据表头
	 */
	public void deleteBillMaster(BillMaster billMaster) {
		this.deleteBillDetailByMaster(billMaster);
		this.delete(billMaster);
	}

	/**
	 * 保存单据资料主表
	 * 
	 * @param billMaster
	 *            资料主表
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveBillMaster(BillMaster billMaster)
			throws DataAccessException {
		this.saveOrUpdate(billMaster);
	}

	/**
	 * 保存单据明细
	 * 
	 * @param bill
	 *            单据
	 */
	public void saveBillDetail(BillDetail bill) {
		this.saveOrUpdate(bill);
	}

	/**
	 * 显示指定大类的商品明细
	 * 
	 * @param billCategory
	 *            单据大类，如料件入仓，料件出仓等
	 * @return 指定大类匹配的商品明细
	 */
	public List findBillDetails(int billCategory) {

		String tableName = BillUtil
				.getBillDetailTableNameByBillType(billCategory);
		if (tableName == null) {
			return new ArrayList<BillDetail>();
		}
		String hsql = " select a from " + tableName
				+ " as a  where  a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 显示指定大类的商品明细
	 * 
	 * @param materielType
	 *            物料类别
	 * @param index
	 * @param length
	 * @param typeCode
	 *            类型代码
	 * @return 与指定物料类别 类型代码匹配的大类商品明细
	 */
	public List findBillDetails(String materielType, int index, int length,
			String typeCode) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList<BillDetail>();
		}
		String hsql = " select a from " + tableName
				+ " as a where a.billMaster.billType.code = ?";
		return this
				.findPageList(hsql, new Object[] { typeCode }, index, length);
	}

	/**
	 * 查找所有的单据明细
	 * 
	 * @param materielType
	 *            单据明细
	 * @param index
	 * @param length
	 * @param isAllData
	 *            是否是全部数据
	 * @return 按id排序的符合条件的单据明细
	 */
	public List<String> findBillDetails(String materielType, int index,
			int length, boolean isAllData) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList<String>();
		}
		String hsql = "";
		if (isAllData == true) {
			hsql = "select a.id from " + tableName + " " + " a  order by a.id ";
		} else {
			hsql = "select a.id from "
					+ tableName
					+ " a "
					+ " where a.hsAmount is null or a.hsAmount <= 0.0  order by a.id ";
		}
		return super.findListNoCache(hsql, index, length);
	}

	/**
	 * 查找所有的单据明细
	 * 
	 * @param materielType
	 *            单据类型
	 * @param beginId
	 *            单据的起始id
	 * @param endId
	 *            单据的最后一个id
	 * @return 与单据类型匹配在有效期的单据明细
	 */
	public List findBillDetails(String materielType, String beginId,
			String endId) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList<BillDetail>();
		}
		String hsql = "from " + tableName + " "
				+ " a  where a.id >= ? and a.id<= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(beginId);
		parameters.add(endId);
		return super.find(hsql, parameters.toArray());
	}

	// /**
	// * 显示指定大类的商品明细
	// *
	// * @param materielType
	// * 物料类别
	// *
	// * @return
	// */
	// public List findBillDetails(String materielType, List<String> pkNoList,
	// Company company) {
	// String tableName = BillUtil
	// .getBillDetailTableNameByMaterielType(materielType);
	// if (tableName == null) {
	// return new ArrayList<BillDetail>();
	// }
	// String hsql = "from " + tableName + " " + " a where "
	// + " a.billMaster.company.id = ? ";
	// List parameters = new ArrayList<Object>();
	// parameters.add(CommonUtils.getCompany().getId());
	// for (int i = 0; i < pkNoList.size(); i++) {
	// if (i == 0) {
	// hsql += " and ( ( a.id=? ) ";
	// } else {
	// hsql += " or ( a.id=? ) ";
	// }
	// if (i == pkNoList.size() - 1) {
	// hsql += " ) ";
	// }
	// parameters.add(pkNoList.get(i));// pk号
	// }
	// return this.find(hsql, parameters.toArray());
	// }
	// /**
	// * 显示指定大类的商品明细
	// *
	// * @param materielType
	// * 物料类别
	// *
	// * @return
	// */
	// public List findBillDetails(String materielType, List<String> pkNoList,
	// Company company) {
	// String tableName = BillUtil
	// .getBillDetailTableNameByMaterielType(materielType);
	// if (tableName == null) {
	// return new ArrayList<BillDetail>();
	// }
	// String hsql = "from " + tableName + " " + " a where "
	// + " a.billMaster.company.id = ? ";
	// List parameters = new ArrayList<Object>();
	// parameters.add(CommonUtils.getCompany().getId());
	// for (int i = 0; i < pkNoList.size(); i++) {
	// if (i == 0) {
	// hsql += " and ( ( a.id=? ) ";
	// } else {
	// hsql += " or ( a.id=? ) ";
	// }
	// if (i == pkNoList.size() - 1) {
	// hsql += " ) ";
	// }
	// parameters.add(pkNoList.get(i));// pk号
	// }
	// return this.find(hsql, parameters.toArray());
	// }
	// /**
	// * 显示指定大类的商品明细
	// *
	// * @param materielType
	// * 物料类别
	// *
	// * @return
	// */
	// public List findBillDetails(String materielType, List<String> pkNoList,
	// Company company) {
	// String tableName = BillUtil
	// .getBillDetailTableNameByMaterielType(materielType);
	// if (tableName == null) {
	// return new ArrayList<BillDetail>();
	// }
	// String hsql = "from " + tableName + " " + " a where "
	// + " a.billMaster.company.id = ? ";
	// List parameters = new ArrayList<Object>();
	// parameters.add(CommonUtils.getCompany().getId());
	// for (int i = 0; i < pkNoList.size(); i++) {
	// if (i == 0) {
	// hsql += " and ( ( a.id=? ) ";
	// } else {
	// hsql += " or ( a.id=? ) ";
	// }
	// if (i == pkNoList.size() - 1) {
	// hsql += " ) ";
	// }
	// parameters.add(pkNoList.get(i));// pk号
	// }
	// return this.find(hsql, parameters.toArray());
	// }
	// /**
	// * 显示指定大类的商品明细
	// *
	// * @param materielType
	// * 物料类别
	// *
	// * @return
	// */
	// public List findBillDetails(String materielType, List<String> pkNoList,
	// Company company) {
	// String tableName = BillUtil
	// .getBillDetailTableNameByMaterielType(materielType);
	// if (tableName == null) {
	// return new ArrayList<BillDetail>();
	// }
	// String hsql = "from " + tableName + " " + " a where "
	// + " a.billMaster.company.id = ? ";
	// List parameters = new ArrayList<Object>();
	// parameters.add(CommonUtils.getCompany().getId());
	// for (int i = 0; i < pkNoList.size(); i++) {
	// if (i == 0) {
	// hsql += " and ( ( a.id=? ) ";
	// } else {
	// hsql += " or ( a.id=? ) ";
	// }
	// if (i == pkNoList.size() - 1) {
	// hsql += " ) ";
	// }
	// parameters.add(pkNoList.get(i));// pk号
	// }
	// return this.find(hsql, parameters.toArray());
	// }

	/**
	 * 显示单据的个数
	 * 
	 * @param materielType
	 *            物料类别
	 * @return 与物料类别匹配的单据个数
	 */
	public Integer findBillDetailCount(String materielType) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return 0;
		}

		String hsql = " select count(*) from " + tableName + " as a"
				+ "  where a.company= ? ";
		List list = this.find(hsql, new Object[] { CommonUtils.getCompany() });
		System.out.println(hsql + "   " + CommonUtils.getCompany());
		if (list != null && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 显示指定单据的商品明细
	 * 
	 * @param materielType
	 *            单据类型
	 * @param billCode
	 *            单据号码
	 * @param index
	 * @param length
	 * @return 与指定的单据号单据类型匹配的单据的商品明细
	 */
	public List findBillDetails(String materielType, String billCode,
			int index, int length) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList<BillDetail>();
		}
		String hsql = " select a from "
				+ tableName
				+ " as a"
				+ "  where a.company= ? and ( a.billMaster.isFlag = ? or a.billMaster.isFlag is null )  and a.billMaster.billType.code = ? ";
		return this.findPageList(hsql, new Object[] { CommonUtils.getCompany(),
				false, billCode }, index, length);
	}

	/**
	 * 返回客户
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 单据头中的客户名称
	 */
	public List findScmCocFromBillMaster(String materielType) {
		String tableName = BillUtil
				.getBillMasterTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a.scmCoc from " + tableName + " as a "
				+ "  where a.company= ? ";

		return this.find(hsql, new Object[] { CommonUtils.getCompany() });

	}

	/**
	 * 返回仓库
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 单据表头的仓库
	 */
	public List findWareSetFromBillMaster(String materielType) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a.wareSet from " + tableName + " as a "
				+ "  where a.company= ? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 根据条件查询单据信息,适用于批量修改料号对应的报关商品信息
	 * 
	 * @param materielType
	 * @param beginDate
	 * @param endDate
	 * @param ptNo
	 * @param scmCoc
	 * @return
	 */
	public List findBillInfo(String materielType, Date beginDate, Date endDate,
			String ptNo, ScmCoc scmCoc, String nameStyle) {
		String detailName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		System.out.println("tablename is " + detailName);
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select a from " + detailName + " a "
				+ "left join fetch a.billMaster b "
				+ "left join fetch a.billMaster.scmCoc c "
				+ "where a.company = ? ";
		parameters.add(CommonUtils.getCompany());
		if (beginDate != null) {
			hsql += "  and b.validDate >=?  ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += "  and b.validDate <=?  ";
			parameters.add(endDate);
		}
		if (scmCoc != null) {
			hsql += "  and c.id = ? ";
			parameters.add(scmCoc.getId());
		}
		if (ptNo != null && !ptNo.equals("")) {
			hsql += "  and a.ptPart = ? ";
			parameters.add(ptNo);
		}
		if (CommonUtils.notEmpty(nameStyle)) {
			hsql += " and a.hsName||'/'||a.hsSpec = ?";
			parameters.add(nameStyle);
		}
		
		System.out.println("hsql!!!!!!!!!!!!!!!!! " + hsql);
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 返回工厂商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @return 指定物料类型对应单据中的工厂商品
	 */
	public List findGoodsFromBillMaster(String materielType) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a from Materiel a ," + tableName
				+ "  b " + " where a.ptNo = b.ptPart and b.company = ? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 返回工厂商品名称
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return 与物料类型匹配的单据明细中的商品名称
	 */
	public List findPtNameFromBillDetail(String materielType, int index,
			int length, String property, Object value, boolean isLike) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a.ptName  from " + tableName
				+ " as a  where  a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回工厂商品规格
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return 与物料类型匹配的单据明细中的商品名称
	 */
	public List findPtSpecFromBillDetail(String materielType, int index,
			int length, String property, Object value, boolean isLike,
			String ptName) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select distinct a.ptSpec  from " + tableName
				+ " as a  where  a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		if (ptName != null && !ptName.equals("")) {
			hsql += " and a.ptName= ? ";
			parameters.add(ptName);
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
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 获得料件名称列表来自发票
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findPtNameFromInvoice(int index, int length, String property,
			Object value, boolean isLike) {
		String hsql = "select distinct a.cuName  from CasInvoiceInfo "
				+ " as a  where  a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回指定仓库的进出单据明细记录
	 * 
	 * @param materielType
	 *            物料类型
	 * @param wareSet
	 *            仓库
	 * @return 根据仓库找出对应单据的商品内容
	 */
	public List findGoodsFromBillMasterByWareSet(String materielType,
			WareSet wareSet) {
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		if (tableName == null) {
			return new ArrayList();
		}
		String hsql = "select a from " + tableName + " as a where a.company=? "
				+ " and a.wareSet = ?";

		return this.find(hsql,
				new Object[] { CommonUtils.getCompany(), wareSet });
	}

	/**
	 * 取得料件直接进口类型
	 * 
	 * @return 单据类型为直接进口的单据明细
	 */
	public List findLjZJJK() {
		return this
				.find(
						"from BillDetailMateriel a where a.billMaster.company.id = ? and a.billMaster.billType.code=? ",
						new Object[] { CommonUtils.getCompany().getId(), "1003" });
	}

	/**
	 * 取得进出口商品信息
	 * 
	 * @param ptNo
	 *            料号
	 * @param pkNo
	 *            pk单号
	 * @return 单据类型为直接出口的进出口商品信息
	 */
	public ImpExpCommodityInfo findImpExpCommodityInfo(String ptNo, String pkNo) {
		List list = this
				.find(
						"from ImpExpCommodityInfo a where a.impExpRequestBill.billType = ? and a.company.id = ? "
								+ " and a.materiel.ptNo = ? and a.billNo = ?",
						new Object[] { ImpExpType.DIRECT_EXPORT,
								CommonUtils.getCompany().getId(), ptNo, pkNo });
		if (list != null && list.size() > 0) {
			return (ImpExpCommodityInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param f
	 *            报关清单归并后商品信息
	 * @return 报关单商品信息
	 */
	public CustomsDeclarationCommInfo findCusInfo(AtcMergeAfterComInfo f) {
		List list = this
				.find(
						"select a.customsInfo from MakeListToCustoms a where a.atcMergeAfterComInfo.id = ? and a.company.id = ?",
						new Object[] { f.getId(),
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (CustomsDeclarationCommInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 取得申请单商品信息
	 * 
	 * @return 进出口类型为直接出口的申请单商品信息
	 */
	public List findMakeApplyToCustomsByType() {
		return this
				.find(
						"from MakeApplyToCustoms a where a.company.id=? and a.impExpCommodityInfo.impExpRequestBill.billType = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								ImpExpType.DIRECT_EXPORT });
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param cNo
	 *            报关单流水号
	 * @param ptNo
	 *            工厂料号
	 * @return 物料类型为料件 序号在归并后的十码中存在的报关商品信息
	 */
	public CustomsDeclarationCommInfo findCustomsByInner(String cNo, String ptNo) {
		List list = this
				.find(
						"from CustomsDeclarationCommInfo a where a.company.id = ? and a.baseCustomsDeclaration.customsDeclarationCode =? and a.commSerialNo in "
								+ "(select b.hsAfterTenMemoNo from InnerMergeData b where b.company.id = ? and b.materiel.ptNo = ? and b.imrType = ?)",
						new Object[] { CommonUtils.getCompany().getId(), cNo,
								CommonUtils.getCompany().getId(), ptNo,
								MaterielType.MATERIEL });
		if (list != null && list.size() > 0) {
			return (CustomsDeclarationCommInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 通过类型返回料件单据明细
	 * 
	 * @return 单据类型代码为1003的料件单据明细
	 */
	public List findMaterielDetailByType() {
		return this
				.find(
						"from BillDetailMateriel a where a.billMaster.billType.code = ? and a.company.id = ?",
						new Object[] { "1003", CommonUtils.getCompany().getId() });
	}

	/**
	 * 通过类型返回成品单据明细
	 * 
	 * @return 单据类型代码为2101的成品单据明细
	 */
	public List findBillDetailProductByType() {
		return this
				.find(
						"from BillDetailProduct a where a.billMaster.billType.code = ? and a.company.id = ?",
						new Object[] { "2101", CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回报关商品名称
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return 与指定物料类型匹配的实际报关商品中的报关名称
	 */
	public List findHsNameFromStatCusNameRelationHsn(String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		String hsql = "select distinct a.cusName,a.cusSpec from StatCusNameRelation as a "
				+ "  where  a.company= ? and a.materielType = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(materielType);
		System.out.println("materielType==" + materielType);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回报关商品名称
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return 与指定物料类型匹配的实际报关商品中的报关名称
	 */
	public List findHsNameFromFactoryAndFactualCustomsRalation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		String hsql = null;
		if (materielType.equals("outSource")) {
			System.out.println("1111111111111111111");
			hsql = "select distinct a.statCusNameRelationHsn.cusName,"
					+ "a.statCusNameRelationHsn.cusSpec, "
					+ "a.statCusNameRelationHsn.cusUnit "
					+ ",a.statCusNameRelationHsn.complex.code "
					+ "from FactoryAndFactualCustomsRalation as a "
					+ "  where  a.company= ? or a.statCusNameRelationHsn.materielType = 0 "
					+ "or a.statCusNameRelationHsn.materielType = 1"
					+ "or a.statCusNameRelationHsn.materielType = 5";
		} else if (materielType.equals("1")) {
			System.out.println("22222222222");
			hsql = "select distinct a.statCusNameRelationHsn.cusName,"
					+

					"a.statCusNameRelationHsn.cusSpec, "
					+ "a.statCusNameRelationHsn.cusUnit "
					+ ",a.statCusNameRelationHsn.complex.code "
					+ "from FactoryAndFactualCustomsRalation as a "
					+ "  where  a.company= ? or a.statCusNameRelationHsn.materielType = 0 "
					+ "or a.statCusNameRelationHsn.materielType = 1";
		} else {
			hsql = "select distinct a.statCusNameRelationHsn.cusName,"
					+

					"a.statCusNameRelationHsn.cusSpec, "
					+ "a.statCusNameRelationHsn.cusUnit "
					+ ",a.statCusNameRelationHsn.complex.code "
					+ "from FactoryAndFactualCustomsRalation as a "
					+ "  where  a.company= ? and a.statCusNameRelationHsn.materielType = ? ";
			parameters.add(materielType);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 查询转厂关封单据中的商品.
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return 实际报关商品名称
	 */
	public List<TempObject> findHsNameFromTransferFactoryBill(boolean isImp,
			int index, int length, String property, Object value, boolean isLike) {
		StringBuilder hsql = new StringBuilder(200);
		List<Object> parameters = new ArrayList<Object>();

		hsql
				.append("select distinct a.ptName,a.complex.code,a.ptSpec,a.unit.name from CustomsEnvelopCommodityInfo a where a.company = ?");
		parameters.add(CommonUtils.getCompany());
		hsql.append(" and a.customsEnvelopBill.isImpExpGoods = ?");
		parameters.add(isImp);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql.append(" and  " + property + " like ?");
				parameters.add(value + "%");
			} else {
				hsql.append(" and  " + property + " = ?");
				parameters.add(value);
			}
		}
		return createQuery(hsql.toString(), parameters.toArray()).list();
	}

	public List findBillDetailProductNo(String materielType, int index,
			int length, String property, Object value, boolean isLike) {
		String hsql = "select distinct a.billNo from BillMasterProduct as a where  a.company= ?";

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(materielType);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回报关商品规格
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @return 与指定物料类型匹配的实际报关商品中的报关规格
	 */
	public List findHsSpecFromStatCusNameRelationHsn(String materielType,
			String hsName, int index, int length, String property,
			Object value, boolean isLike) {
		String hsql = "select distinct a.cusSpec  from StatCusNameRelation as a "
				+ "  where  a.company= ? and a.materielType = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(materielType);
		System.out.println("materielType==" + materielType);
		if (hsName != null && !hsName.equals("")) {
			hsql += " and  a.cusName like ?  ";
			parameters.add(hsName + "%");
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
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回报关商品名称规格来自关封
	 * 
	 * @param cusName
	 *            客户名称
	 * @param hsName
	 *            报关商品名称
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
	 * @return 商品大类报关规格
	 */
	public List findHsNameFromCustomsEnvelopCommodityInfo(String cusName,
			String ptName, int index, int length, String property,
			Object value, boolean isImp, boolean isName, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		StringBuilder hsql = new StringBuilder("select distinct "
				+ (isName ? "a.ptName" : "a.ptSpec")
				+ " from CustomsEnvelopCommodityInfo as a "
				+ " where a.customsEnvelopBill.scmCoc.name like ?");
		parameters.add(cusName + "%");

		hsql.append(" and a.customsEnvelopBill.isImpExpGoods = ?");
		parameters.add(isImp);
		if (ptName != null && !ptName.equals("")) {
			hsql.append(" and  a.ptName like ?  ");
			parameters.add(ptName + "%");
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql.append(" and  a." + property + " like ?  ");
				parameters.add(value + "%");
			} else {
				hsql.append(" and  a." + property + " = ?  ");
				parameters.add(value);
			}
		}
		return super.findPageList(hsql.toString(), parameters.toArray(), index,
				length);
	}

	/**
	 * 返回报关商品规格
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @return 与指定物料类型匹配的实际报关商品中的报关规格
	 */
	public List findHsSpecFromFactoryAndFactualCustomsRalation(
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike) {

		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		if (materielType.equals("SEMI_FINISHED_PRODUCT")) {
			hsql = "select distinct a.statCusNameRelationHsn.cusSpec from FactoryAndFactualCustomsRalation as a "
					+ "  where a.company= ? and a.statCusNameRelationHsn.materielType = 0 "
					+ "or a.statCusNameRelationHsn.materielType = 1"
					+ "or a.statCusNameRelationHsn.materielType = 5 ";
		} else if (materielType.equals("OutSource")) {
			hsql = "select distinct a.statCusNameRelationHsn.cusSpec from FactoryAndFactualCustomsRalation as a "
					+ "  where a.company= ? and a.statCusNameRelationHsn.materielType = 0 "
					+ "or a.statCusNameRelationHsn.materielType = 1"
					+ "or a.statCusNameRelationHsn.materielType = 5 ";
		} else {
			hsql = "select distinct a.statCusNameRelationHsn.cusSpec from FactoryAndFactualCustomsRalation as a "
					+ "  where a.company= ? and a.statCusNameRelationHsn.materielType = ? ";
			parameters.add(materielType);
		}

		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.statCusNameRelationHsn.cusName like ?  ";
			parameters.add(hsName + "%");
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回"在产品"报关规格
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @author 石小凯
	 * @return 与指定物料类型匹配的实际报关商品中的报关规格
	 */
	public List findHalfProductHsSpec(String materielType, String hsName,
			int index, int length, String property, Object value, boolean isLike) {

		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		hsql = "select distinct a.hsSpec  from  BillDetailHalfProduct  as a where a.company =? ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回"产成品"报关规格
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @author 石小凯
	 * @return 与指定物料类型匹配的实际报关商品中的报关规格
	 */
	public List findDetailProductHsSpec(String materielType, String hsName,
			int index, int length, String property, Object value, boolean isLike) {

		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		hsql = "select distinct a.hsSpec  from  BillDetailProduct  as a where a.company =? ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回"产成品"报关名字
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @author 石小凯
	 * @return 与指定物料类型匹配的实际报关商品中的报关规格
	 */
	public List findDetailProductHsName(String materielType, String hsName,
			int index, int length, String property, Object value, boolean isLike) {

		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		hsql = "select distinct a.hsName,a.complex.code   from  BillDetailProduct  as a where a.company =? ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回"半成品"报关名字
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @author 石小凯
	 * @return 与指定物料类型匹配的实际报关商品中的报关规格
	 */
	public List findDetailMaterielHsName(String materielType, String hsName,
			int index, int length, String property, Object value, boolean isLike) {

		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		hsql = "select distinct a.hsName ,a.complex.code from  BillDetailHalfProduct  as a where a.company =? ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回"残次品"报关名字
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @author 石小凯
	 * @return 与指定物料类型匹配的实际报关商品中的报关规格
	 */
	public List findDetailRemainProductHsName(String materielType,
			String hsName, int index, int length, String property,
			Object value, boolean isLike) {

		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		hsql = "select distinct a.hsName,a.complex.code   from  BillDetailRemainProduct  as a where a.company =? ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回报关商品单位
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
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
	 * @return 与指定物料类型匹配的实际报关商品中的报关单位
	 */
	public List findHsUnitFromFactoryAndFactualCustomsRalation(
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike) {
		String hsql = "select distinct a.statCusNameRelationHsn.cusUnit from FactoryAndFactualCustomsRalation as a "
				+ "  where a.company= ? and a.statCusNameRelationHsn.materielType = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(materielType);
		System.out.println("materielType==" + materielType);
		if (hsName != null && !hsName.equals("")) {
			hsql += " and a.statCusNameRelationHsn.cusName like ?  ";
			parameters.add(hsName + "%");
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回工厂商品名称
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
	 * @return 与指定物料类型匹配的工厂物料中的商品名称
	 */
	public List findPtNameFromStatCusNameRelationMt(String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		String hsql = "select distinct m.factoryName  from StatCusNameRelationMt as s "
				+ "  left join s.statCusNameRelation ss "
				+ " left join s.materiel m "
				+ "   where  ss.company= ? and ss.materielType = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(materielType);
		System.out.println("materielType==" + materielType);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  s." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  s." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回工厂商品名称
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
	 * @return 与指定物料类型匹配的工厂物料中的商品名称
	 */
	public List findPtNameFromFactoryAndFactualCustomsRalation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel.factoryName from FactoryAndFactualCustomsRalation as a "
				+ "   where  a.company= ? and a.statCusNameRelationHsn.materielType = ? ";
		if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
			hsql = "select distinct a.factoryName from Materiel a where  a.company= ? and a.scmCoi.name = ?";
			parameters.add(CommonUtils.getCompany());
			parameters.add("半成品");
		} else {
			parameters.add(CommonUtils.getCompany());
			parameters.add(materielType);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回对应关系
	 * 
	 * @param ptNos
	 *            多个料号逗号分开
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
	 * @return 与指定物料类型匹配的工厂物料中的商品名称
	 */
	public List findHsByPtNo(String ptNos, int index, int length,
			String property, Object value, boolean isLike) {
		String hsql = "select distinct a from FactoryAndFactualCustomsRalation as a "
				+ "left join fetch a.materiel b"
				+ "   where  a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		
		// 循环加入料号的查询条件语句
		if(CommonUtils.notEmpty(ptNos)) {
			String[] ptNoArr = ptNos.split(",");
			hsql += " and (1=2";
			for (int i = 0; i < ptNoArr.length; i++) {
				hsql += " or b.ptNo = ?";
				parameters.add(ptNoArr[i]);
			}
			hsql += ")";
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回工厂商品名称
	 * 
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂商品名称
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
	 * @return 与指定物料类型匹配的工厂物料中的商品规格
	 */
	public List findPtSpecFromStatCusNameRelationMt(String materielType,
			String ptName, int index, int length, String property,
			Object value, boolean isLike) {
		String hsql = "select distinct m.factorySpec  from StatCusNameRelationMt as s "
				+ "  left join  s.statCusNameRelation ss "
				+ " left join s.materiel m "
				+ "   where  ss.company= ? and ss.materielType = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(materielType);
		System.out.println("materielType==" + materielType);
		if (ptName != null && !ptName.equals("")) {
			hsql += " and  m.factoryName like ?  ";
			parameters.add(ptName + "%");
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  s." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  s." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回工厂商品规格
	 * 
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂商品名称
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
	 * @return 与指定物料类型匹配的工厂物料中的商品规格
	 */
	public List findPtSpecFromFactoryAndFactualCustomsRalation(
			String materielType, String ptName, int index, int length,
			String property, Object value, boolean isLike) {
		String hsql = "select distinct a.materiel.factorySpec from FactoryAndFactualCustomsRalation as a "
				+ "   where  a.company= ? and a.statCusNameRelationHsn.materielType = ? ";
		List<Object> parameters = new ArrayList<Object>();
		if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
			hsql = "select distinct a.factorySpec from Materiel a where  a.company= ? and a.scmCoi.name = ?";
			parameters.add(CommonUtils.getCompany());
			parameters.add("半成品");
		} else {
			parameters.add(CommonUtils.getCompany());
			parameters.add(materielType);
		}
		if (ptName != null && !ptName.equals("")) {
			if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
				hsql += " and  a.factoryName like ?  ";
			} else {
				hsql += " and  a.materiel.factoryName like ?  ";
			}
			parameters.add(ptName + "%");
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 返回工厂商品名称
	 * 
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂商品名称
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
	 * @author 石小凯
	 * @return 与指定物料类型匹配的工厂物料中的商品规格
	 */
	public List findPtSpecFrom(String materielType, String ptName, int index,
			int length, String property, Object value, boolean isLike) {

		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		hsql = "select distinct a.ptSpec  from  BillDetailProduct  as a where a.company =? ";
		if (materielType.equals("SEMI_FINISHED_PRODUCT")) {
			hsql = "select distinct a.ptSpec  from  BillDetailHalfProduct  as a where a.company =? ";
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 检查单据导入期初单时,报关资料是否在对应关系存在
	 * 
	 * @param materielType
	 * @return
	 */
	public Map<String, Object[]> findHsKeyForBill(String materielType) {
		String type = "";
		if ("料件".equals(materielType) || "残次品(料件)".equals(materielType)) {
			type = MaterielType.MATERIEL;
		} else if ("成品".equals(materielType) || "残次品(成品)".equals(materielType)) {
			type = MaterielType.FINISHED_PRODUCT;
		} else if ("设备".equals(materielType)) {
			type = MaterielType.MACHINE;
		} else if ("半成品".equals(materielType)
				|| "残次品(半成品)".equals(materielType)) {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
		}
		// else if ("残次品".equals(materielType)) {
		// type = MaterielType.BAD_PRODUCT;
		// }
		else if ("边角料".equals(materielType)) {
			type = MaterielType.REMAIN_MATERIEL;
		}

		String hsql = "select distinct a.statCusNameRelationHsn.complex.id,a.statCusNameRelationHsn.cusName,"
				+ "a.statCusNameRelationHsn.cusSpec,a.statCusNameRelationHsn.cusUnit "
				+ "a.statCusNameRelationHsn.emsNo "
				+ // wss2010.09.14新增手册号
				"from FactoryAndFactualCustomsRalation a "
				+ "where a.statCusNameRelationHsn.materielType = ? and "
				+ "a.company= ? ";

		// wss:2010.04.27修改：（半成品特殊--> 报关常用工厂物料）
		if (MaterielType.SEMI_FINISHED_PRODUCT.equals(type)) {
			hsql = "select distinct a.complex.id,a.ptName" // 料号，海关商品名称
					+ "a.ptSpec,a.ptUnit," // 海关规格，海关单位
					+ "from Materiel a "
					+ "where  a.scmCoi.name = ? and "
					+ " a.company= ? ";
			// type = materielType;//这个也要变一下哦 ,点解？
		}

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(type);
		parameters.add(CommonUtils.getCompany());
		List<Object[]> list = super.find(hsql, parameters.toArray());
		Map<String, Object[]> map = new HashMap();
		for (Object[] item : list) {
			Unit unit = (Unit) item[3];
			String key = (String) item[1] + "/" + (String) item[2] + "/"
					+ (unit == null ? "" : unit.getName());
			if (!MaterielType.SEMI_FINISHED_PRODUCT.equals(type)) {
				key += "/" + (String) item[4];
			}

			map.put(key.trim(), item);
		}
		return map;
	}

	/**
	 * 查找出对应关系中一对多的料号
	 * 
	 * @param materielType
	 * @return
	 */
	public List findOneToMorePtNo(String materielType) {
		String hsql = "select distinct a.materiel.ptNo,a.statCusNameRelationHsn.cusName,"
				+ "a.statCusNameRelationHsn.cusSpec,a.statCusNameRelationHsn.cusUnit.name "
				+ "from FactoryAndFactualCustomsRalation a "
				+ "where a.statCusNameRelationHsn.materielType = ? and "
				+ "a.company= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(materielType);
		parameters.add(CommonUtils.getCompany());
		List<Object[]> list = super.find(hsql, parameters.toArray());
		List returnlist = new ArrayList();
		int i = 0;
		String ptNo = "";
		for (Object[] item : list) {
			if (i == 0) {
				ptNo = (String) item[0];
			} else {
				String tmp = (String) item[0];
				if (tmp.equalsIgnoreCase(ptNo)) {
					returnlist.add(ptNo);
				} else {
					ptNo = tmp;
				}

			}
			i++;
		}
		return returnlist;
	}

	/**
	 * 获得pk转报关单的中间信息记录 (直接出口成品)
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 在有效期内与进出口类型匹配的pk转生效报关单的中间信息记录
	 */
	public List findMiddleInfoByPKToCustomType(int impExpType, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select a.impExpCommodityInfo ,b.customsInfo "
				+ "  from MakeApplyToCustoms a,MakeListToCustoms b"
				+ "  where a.company.id=? "
				+ "        and a.atcMergeBeforeComInfo.afterComInfo = b.atcMergeAfterComInfo "
				+ "        and a.impExpCommodityInfo.impExpRequestBill.billType = ? "
				+ "        and b.customsInfo.baseCustomsDeclaration.effective = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(impExpType);
		parameters.add(true);
		if (beginDate != null) {
			hsql += "  and b.updateDate >=?  ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += "  and b.updateDate <=?  ";
			parameters.add(endDate);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询成品单据明细
	 * 
	 * @param pkNoList
	 *            pk单号组
	 * @param ptNoList
	 *            料号组
	 * @return 直接出口单据明细
	 */
	public List findBillDetailProductbyPk(List<String> pkNoList,
			List<String> ptNoList) {
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "from BillDetailProduct " + " a where "
				+ " a.billMaster.company.id = ? "
				+ " and a.billMaster.billType.code=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("2101");// 直接出口
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and ( ( a.ptPart=? and  a.note=? ) ";
			} else {
				hsql += " or ( a.ptPart=? and  a.note=? ) ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(ptNoList.get(i));// 料号
			parameters.add(pkNoList.get(i));// pk号
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 通过装箱单号查询料件单据
	 * 
	 * @param tempContainerList
	 *            所有的装箱单号
	 * @return 与指定的装箱单的料号报关单号相关联 单据类型为1003的料件单据
	 */
	public List findBillDetailMaterielByContainer(
			List<TempContainer> tempContainerList) {
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "from BillDetailMateriel " + " a where "
				+ " a.billMaster.company.id = ? "
				+ " and a.billMaster.billType.code=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add("1003");// 直接进口
		for (int i = 0; i < tempContainerList.size(); i++) {
			TempContainer tempContainer = tempContainerList.get(i);
			if (i == 0) {
				hsql += " and ( ( a.ptPart=? and  a.note=? ) ";
			} else {
				hsql += " or ( a.ptPart=? and  a.note=? ) ";
			}
			if (i == tempContainerList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(tempContainer.getPtNo());// 料号
			parameters.add(tempContainer.getCustomsDeclarationNo());// 报关单号
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 通过装箱单号查询报关单
	 * 
	 * @param tempContainerList
	 *            所有的装箱单号
	 * @return 查询与指定的装箱单号匹配的报关单
	 */
	public List<CustomsDeclarationContainer> findCustomsDeclarationByContainer(
			List<TempContainer> tempContainerList) {
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select b from CustomsDeclarationContainer b left join b.baseCustomsDeclaration a where "
				+ " a.company.id = ? and a.impExpType=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ImpExpType.DIRECT_IMPORT);// 直接进口
		for (int i = 0; i < tempContainerList.size(); i++) {
			TempContainer tempContainer = tempContainerList.get(i);
			if (i == 0) {
				hsql += " and ( b.containerCode = ? ";
			} else {
				hsql += " or b.containerCode = ? ";
			}
			if (i == tempContainerList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(tempContainer.getContainerNo());// 集装箱号
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 获取工厂物料料号所对应的Bom明细
	 * 
	 * @param ptNoList
	 *            所有的料号
	 * @return 指定工厂料号对应的bom明细
	 */
	public List<MaterialBomDetail> findMaterielBomDetail(List<String> ptNoList) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from MaterialBomDetail a  "
				+ " left join fetch a.materiel m "
				+ " left join fetch m.complex c where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());

		for (int i = 0, n = ptNoList.size(); i < n; i++) {

			if (i == 0) {
				hsql += " and (a.version.master.materiel.ptNo = ? ";
			} else {
				hsql += " or a.version.master.materiel.ptNo = ? ";
			}
			if (i == n - 1) {
				hsql += " )";
			}
			parameters.add(ptNoList.get(i));
		}
		if (ptNoList.size() == 0) {
			return new ArrayList();
		}
		return super.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 保存生效单据
	 * 
	 * @param billType
	 *            单据类型
	 * @param isEffect
	 *            是否生效
	 */
	public void saveBillMasterEffect(BillType billType, boolean isEffect) {
		int sBillType = billType.getBillType();
		String tableName = BillUtil.getBillMasterTableNameByBillType(sBillType);

		String hsql = "update " + tableName
				+ " set isValid = ? where billType.id =? and company.id = ?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(isEffect);
		parameters.add(billType.getId());
		parameters.add(CommonUtils.getCompany().getId());
		this.batchUpdateOrDelete(hsql, parameters.toArray());

	}

	// /**
	// * 显示指定大类的商品明细
	// *
	// * @param materielType
	// * 物料类别
	// *
	// * @return
	// */
	// public List findBillDetails(String materielType, List<String> pkNoList,
	// Company company) {
	// String tableName = BillUtil
	// .getBillDetailTableNameByMaterielType(materielType);
	// if (tableName == null) {
	// return new ArrayList<BillDetail>();
	// }
	// String hsql = "from " + tableName + " " + " a where "
	// + " a.billMaster.company.id = ? ";
	// List parameters = new ArrayList<Object>();
	// parameters.add(CommonUtils.getCompany().getId());
	// for (int i = 0; i < pkNoList.size(); i++) {
	// if (i == 0) {
	// hsql += " and ( ( a.id=? ) ";
	// } else {
	// hsql += " or ( a.id=? ) ";
	// }
	// if (i == pkNoList.size() - 1) {
	// hsql += " ) ";
	// }
	// parameters.add(pkNoList.get(i));// pk号
	// }
	// return this.find(hsql, parameters.toArray());
	// }

	/**
	 * 批量生成报关数量
	 * 
	 * @param materielType
	 *            物料类型
	 * @param listStatM
	 *            企业物料
	 */
	public void makeHsAmountByBatch(String materielType,
			List<StatCusNameRelationMt> listStatM, boolean isUpdateHsAmount) {

		String tableName = BillUtil.getDBTableNameByMaterielType(materielType);
		if (tableName == null) {
			return;
		}

		List<List<Object>> parameters = new ArrayList<List<Object>>();

		for (int i = 0; i < listStatM.size(); i++) {
			StatCusNameRelationMt statM = listStatM.get(i);
			// 以料号为key的 hashMap
			String ptNo = statM.getMateriel().getPtNo();
			// 以折算报关系数为 value 的 hashMap
			Double unitConvert = statM.getUnitConvert();
			if (ptNo == null || "".equals(ptNo)) {
				logger.info("Jbcus 生成单据的折算报关数量中料号 == " + ptNo);
				continue;
			}
			// if (unitConvert == null || unitConvert <= 0) {
			// continue;
			// }
			// 报关大类对象
			StatCusNameRelation stat = statM.getStatCusNameRelation();
			String coid = CommonUtils.getCompany().getId();

			List<Object> parameter = new ArrayList<Object>();
			parameter.add(unitConvert == null ? 0.0 : unitConvert);
			parameter.add(stat.getCusName());
			parameter.add(stat.getCusSpec());
			parameter.add(stat.getComplex() == null ? "" : stat.getComplex()
					.getId());
			parameter.add(stat.getCusUnit() == null ? "" : stat.getCusUnit()
					.getCode());
			parameter.add(coid);
			parameter.add(ptNo);
			parameters.add(parameter);
		}
		String hsql = "";
		if (isUpdateHsAmount == true) {
			hsql = "update "
					+ tableName
					+ " set hsAmount = ptAmount * ? ,"
					+ "    hsName = ?,"
					+ "    hsSpec = ?,"
					+ "    complexid =? ,hsunitid = ?  where coid = ? and ptPart = ? ";
		} else {
			hsql = "update "
					+ tableName
					+ " set hsAmount = ptAmount * ? ,"
					+ "    hsName = ?,"
					+ "    hsSpec = ?,"
					+ "    complexid =? ,hsunitid = ?  where coid = ? and ptPart = ? and ( hsAmount<= 0.0 or hsAmount is null) ";
		}
		this.batchUpdateOrDeleteInSql(hsql, parameters, 50);
	}

	/**
	 * 根据对应关系批量生成报关数量
	 * 
	 * @param materielType
	 *            物料类型
	 * @param listStatM
	 *            企业物料
	 */
	public void makeHsAmountByBatchFromRelation(String materielType,
			List<FactoryAndFactualCustomsRalation> listRelation,
			boolean isUpdateHsAmount) {

		String tableName = BillUtil.getDBTableNameByMaterielType(materielType);
		if (tableName == null) {
			return;
		}

		List<List<Object>> parameters = new ArrayList<List<Object>>();

		for (int i = 0; i < listRelation.size(); i++) {
			FactoryAndFactualCustomsRalation relation = listRelation.get(i);
			// 以料号为key的 hashMap
			String ptNo = relation.getMateriel().getPtNo();
			// 以折算报关系数为 value 的 hashMap
			Double unitConvert = relation.getUnitConvert();
			if (ptNo == null || "".equals(ptNo)) {
				logger.info("Jbcus 生成单据的折算报关数量中料号 == " + ptNo);
				continue;
			}
			// if (unitConvert == null || unitConvert <= 0) {
			// continue;
			// }
			// 报关大类对象
			StatCusNameRelationHsn stat = relation.getStatCusNameRelationHsn();
			String coid = CommonUtils.getCompany().getId();

			List<Object> parameter = new ArrayList<Object>();
			parameter.add(unitConvert == null ? 0.0 : unitConvert);
			parameter.add(stat.getCusName());
			parameter.add(stat.getCusSpec());
			parameter.add(stat.getComplex() == null ? "" : stat.getComplex()
					.getId());
			parameter.add(stat.getCusUnit() == null ? "" : stat.getCusUnit()
					.getCode());
			parameter.add(coid);
			parameter.add(ptNo);
			parameters.add(parameter);
		}
		String hsql = "";
		if (isUpdateHsAmount == true) {
			hsql = "update "
					+ tableName
					+ " set hsAmount = ptAmount * ? ,"
					+ "    hsName = ?,"
					+ "    hsSpec = ?,"
					+ "    complexid =? ,hsunitid = ?  where coid = ? and ptPart = ? ";
		} else {
			hsql = "update "
					+ tableName
					+ " set hsAmount = ptAmount * ? ,"
					+ "    hsName = ?,"
					+ "    hsSpec = ?,"
					+ "    complexid =? ,hsunitid = ?  where coid = ? and ptPart = ? and ( hsAmount<= 0.0 or hsAmount is null) ";
		}
		this.batchUpdateOrDeleteInSql(hsql, parameters, 50);
	}

	/**
	 * 是否存在单据号
	 */
	public boolean isExistBillByBillNo(BillMaster billMaster) {
		String tableName = billMaster.getClass().getName();
		List list = new ArrayList();
		if (billMaster.getId() == null || billMaster.getId().equals("")) {
			list = this.find("select a from " + tableName
					+ " a where a.company= ? and a.billNo= ?  ", new Object[] {
					CommonUtils.getCompany(), billMaster.getBillNo() });
		} else {
			list = this.find("select a from " + tableName
					+ " a where a.company= ? and a.billNo= ? "
					+ " and a.id<>? ", new Object[] { CommonUtils.getCompany(),
					billMaster.getBillNo(), billMaster.getId() });
		}
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获得成品的归并前数量
	 * 
	 * @param billDetail
	 * @param billTypes
	 * @param productNo
	 * @return
	 */
	public Double getPtAmountByproductNo(BillDetail billDetail, List billTypes,
			String productNo) {

		Double ptAmount = 0.0;
		String tableName = billDetail.getClass().getName();
		String sqlCase = "";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());

		String sign = "";
		String mark = ",";
		for (int i = 0; i < billTypes.size(); i++) {
			BillType billType = (BillType) billTypes.get(i);
			parameters.add(billType.getCode());
			if (i == billTypes.size() - 1) {
				mark = "";
			}
			sign += "?" + mark;
		}
		String hsql = "select sum(a.ptAmount) from " + tableName + " a"
				+ " where a.company.id=? and a.billMaster.billType.code in ("
				+ sign + ") and a.productNo = ? group by a.productNo ";

		parameters.add(productNo);
		List list = this.find(hsql, parameters.toArray());
		if (!list.isEmpty()) {
			Double count = Double.valueOf(list.get(0).toString());
			if (count > 0) {
				ptAmount = count;
			}
		}

		return ptAmount;

	}

	/**
	 * 查找海关帐基本单据项目设定
	 * 
	 * @return 海关帐单据项目设定 (单据是否允许重复 双击是否修改还是流览.......)
	 */
	public CasBillOption findCasBillOption() {

		List list = this.find(
				"select a from CasBillOption a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
		if (list.size() > 0) {
			return (CasBillOption) list.get(0);
		}
		return null;
	}

	/**
	 * 返回报关商品名称
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return 与指定物料类型匹配的实际报关商品中的报关名称
	 * 
	 * @author wss
	 */
	public List findHsFromFactoryAndFactualCustomsRalation(String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		String hsql = null;

		System.out.println("wss 在选择报关资料");

		if (MaterielType.SEMI_FINISHED_PRODUCT == materielType
				|| MaterielType.BAD_PRODUCT == materielType) {

			hsql = " select distinct a.ptName, " + " a.ptSpec, "
					+ " a.ptUnit, " + " a..complex.code "
					+ " from Materiel as a "
					+ "  where  a.company= ? and a.scmCoi.coiProperty = ? ";
			parameters.add(materielType);
		} else {
			hsql = " select distinct a.statCusNameRelationHsn.cusName, "
					+ " a.statCusNameRelationHsn.cusSpec, "
					+ " a.statCusNameRelationHsn.cusUnit, "
					+ " a.statCusNameRelationHsn.complex.code "
					+ " from FactoryAndFactualCustomsRalation as a  "
					+ " where  a.company= ? and a.statCusNameRelationHsn.materielType = ? ";
			parameters.add(materielType);
		}

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  " + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  " + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findPageList(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 删除在产品期初 单，要反写半成品期初单的备注号
	 * 
	 * @author wss2010.10.08
	 */
	public void updateBillMaster4009(String billNo) {
		String hsql = " update BillMasterHalfProduct a " + " set a.notice = ? "
				+ " where a.company.id = ? " + " and a.billType.id = ? "
				+ " and a.notice = ? ";

		this.batchUpdateOrDelete(hsql, new Object[] { "",
				CommonUtils.getCompany().getId(),
				"4028813402cd8ff40102cdfe479d003d",// wss:这个就是4009半成品期初单单据类型id,参考billType.xml
				billNo });
	}

}
