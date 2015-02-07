package com.bestway.common.erpbill.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderBom;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.CustomOrderExg;
import com.bestway.common.erpbill.entity.CustomOrderImg;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbill.entity.OrderDateIndex;
import com.bestway.common.erpbill.entity.TempCustomOrder;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;

public class OrderCommonDao extends BaseDao {

	/**
	 * 保存订单体
	 * 
	 * @param orderDetail
	 */
	public void saveCustomOrderDetail(CustomOrderDetail orderDetail) {
		this.saveOrUpdate(orderDetail);

	}

	public Customparames findCustomparames() {
		Customparames parames = null;
		List list = this.find(
				"select a from Customparames a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() != 0) {
			parames = (Customparames) list.get(0);
		}
		return parames;
	}

	public void deleteCustomOrdeDetail(CustomOrderDetail orderDetail) {
		this.delete(orderDetail);
	}

	public void delectCustomOrderDetail(CustomOrder customOrder) {

		this.batchUpdateOrDelete(" delete from CustomOrderDetail "
				+ "a where a.customOrder.id = ? and a.company.id= ? ",
				new Object[] { customOrder.getId(),
						CommonUtils.getCompany().getId() });

		delectCustomOrder(customOrder);
	}

	/**
	 * 删除转报关的资料
	 * 
	 * @param customOrder
	 */
	public void delCustomOrderAll(CustomOrder customOrder) {

		// this
		// .batchUpdateOrDelete(
		// "delete from CustomOrderBom a " +
		// " where a.customOrderExg.customOrderDetail.customOrder = ? "+
		// " and a.company.id = ? ",
		// new Object[] {customOrder ,CommonUtils.getCompany().getId() });

		this.batchUpdateOrDelete(" delete from CustomOrderImg a "
				+ " where a.customOrderDetail.customOrder = ? "
				+ " and a.company.id= ? ", new Object[] { customOrder,
				CommonUtils.getCompany().getId() });

		// this.batchUpdateOrDelete(" delete from CustomOrderExg a " +
		// " where a.customOrderDetail.customOrder = ? " +
		// " and a.company.id= ? ", new Object[] { customOrder,
		// CommonUtils.getCompany().getId() });

	}

	/**
	 * 删除转报关的资料
	 * 
	 * @param customOrderDetail
	 */
	public void delCustomOrderAll(CustomOrderDetail customOrderDetail) {
		String id = customOrderDetail.getId();
		this
				.batchUpdateOrDelete(
						" delete from CustomOrderBom "
								+ " a left join a.customOrderExg b left join b.customOrderDetail c "
								+ " where c.id = ? " + " and a.company.id= ? ",
						new Object[] { id, CommonUtils.getCompany().getId() });
		this.batchUpdateOrDelete(" delete from CustomOrderImg "
				+ " a left join a.customOrderDetail b "
				+ " where b.id = ? and a.company.id= ? ", new Object[] { id,
				CommonUtils.getCompany().getId() });
		this
				.batchUpdateOrDelete(
						" delete from CustomOrderExg "
								+ " a left join a.customOrderExg b left join b.customOrderDetail c "
								+ " where c.id = ? and a.company.id= ? ",
						new Object[] { id, CommonUtils.getCompany().getId() });

	}

	/**
	 * 删除已转报关的订单明细资料
	 * 
	 * @param customOrder
	 */
	public void delCustomOrderDetail(CustomOrderDetail customOrderDetail) {
		this.batchUpdateOrDelete(" delete from CustomOrderBom "
				+ "a where a.customOrderDetail = ?" + " and a.company.id= ? ",
				new Object[] { customOrderDetail,
						CommonUtils.getCompany().getId() });
		this.batchUpdateOrDelete(" delete from CustomOrderImg "
				+ "a where a.customOrderDetail = ? and a.company.id= ? ",
				new Object[] { customOrderDetail,
						CommonUtils.getCompany().getId() });
		this.batchUpdateOrDelete(" delete from CustomOrderExg "
				+ "a where a.customOrderDetail = ? and a.company.id= ? ",
				new Object[] { customOrderDetail,
						CommonUtils.getCompany().getId() });
		this.delete(customOrderDetail);

	}

	public void delectCustomOrder(CustomOrder customOrder) {

		this.delete(customOrder);

	}

	public List findCustomOrder() {
		return this.find("select a from CustomOrder a"
				+ " where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	public List findCustomOrderDetail(String billCode) {
		return this.find("select a from CustomOrderDetail a"
				+ " where a.company.id=? and a.customOrder.billCode=?",
				new Object[] { CommonUtils.getCompany().getId(), billCode });
	}

	public CustomOrderDetail findCustomOrderDetailByOrderERPId(String orderERPId) {
		List list = this.find("select a from CustomOrderDetail a"
				+ " where a.company.id=? and a.orderERPId=?", new Object[] {
				CommonUtils.getCompany().getId(), orderERPId });
		if (list.size() > 0 && list.get(0) != null) {
			return (CustomOrderDetail) list.get(0);
		} else {

			return null;
		}
	}

	/**
	 * 获取归并关系资料
	 * 
	 * @param ptNo
	 * @param materieltype
	 * @return
	 */
	public BcsInnerMerge findBcsInnerMerge(String ptNo, String materieltype) {
		BcsInnerMerge bcsInnerMerge = null;
		List list = this.find("select a from BcsInnerMerge a where a.materiel.ptNo = ? and a.materielType =? and a.company=? and a.isUsing = ? ",
						new Object[] { ptNo, materieltype,CommonUtils.getCompany(),Boolean.TRUE});
		if (list.size() > 0) {			
			bcsInnerMerge = (BcsInnerMerge) list.get(0);			
		}
		return bcsInnerMerge;
	}

	/**
	 * 归并序号是否在备案资料库中存在
	 * 
	 * @param ptNo
	 * @param materieltype
	 * @return
	 */
	public BcsDictPorExg findBcsDictPorExg(Integer seqNum) {
		BcsDictPorExg bcsDictPorexg = null;
		List list = this
				.find(
						"select a from BcsDictPorExg a where a.innerMergeSeqNum=? and a.company=? and a.modifyMark<>?",
						new Object[] { seqNum, CommonUtils.getCompany(),
								ModifyMarkState.ADDED });
		if (list.size() > 0) {
			bcsDictPorexg = (BcsDictPorExg) list.get(0);
		}
		return bcsDictPorexg;
	}

	/**
	 * 归并序号是否在备案资料库中存在
	 * 
	 * @param ptNo
	 * @param materieltype
	 * @return
	 */
	public BcsDictPorImg findBcsDictPorImg(Integer seqNum) {
		BcsDictPorImg bcsDictPorimg = null;
		List list = this
				.find(
						"select a from BcsDictPorImg a where a.innerMergeSeqNum=? and a.company=? and a.modifyMark<>?",
						new Object[] { seqNum, CommonUtils.getCompany(),
								ModifyMarkState.ADDED });
		if (list.size() > 0) {
			bcsDictPorimg = (BcsDictPorImg) list.get(0);
		}
		return bcsDictPorimg;
	}

	/**
	 * 取得最大版本
	 */
	public Integer findMaterialBomDetailVersioId(String ptNo) {
		List list = this.find("select Max(b.version) from MaterialBomDetail a "
				+ " left join a.version b "
				+ " where  b.master.materiel.ptNo =? and a.company=?",
				new Object[] { ptNo, CommonUtils.getCompany() });
		if (list.size() > 0) {
			return (Integer) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取归并关系资料中的十位编码
	 * 
	 * @param ptNo
	 * @param materieltype
	 * @return
	 */
	public Complex findBcsInnerMergeComplex(String ptNo, String materieltype) {
		Complex complex = null;
		List list = this
				.find(
						"select a.bcsTenInnerMerge.complex from BcsInnerMerge a where a.materiel.ptNo = ? and a.materielType =? and a.company=?",
						new Object[] { ptNo, materieltype,
								CommonUtils.getCompany() });
		if (list.size() > 0) {
			complex = (Complex) list.get(0);
		}
		return complex;
	}

	/**
	 * 保存定单成品
	 * 
	 * @param customOrderExg
	 */
	public void saveCustomOrderExg(CustomOrderExg customOrderExg) {
		this.saveOrUpdate(customOrderExg);
	}

	/**
	 * 根据成品料号查询报关常用工厂BOM料件资料
	 * 
	 * @param ptNo
	 * @return
	 */
	public List findMaterialBomDetail(String ptNo, Integer type) {

		/**
		 * type ==1 为 电子手册 type == 2 or 3为 纸质手册
		 */
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		parameters.add(ptNo);
		parameters.add(CommonUtils.getCompany());

		if (type == 1) {
			hsql = "select a from MaterialBomDetailApply a where "
					+ "a.versionApply.bomMasterApply.materiel.ptNo=? and a.company=?";

		} else if (type == 2 || type == 3) {
			hsql = "select a from MaterialBomDetail a where "
					+ "a.version.master.materiel.ptNo=? and a.company=?";
		}

		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 保存定单BOM
	 * 
	 * @param customOrderExg
	 */

	public void saveCustomOrderBom(CustomOrderBom customOrderBom) {
		this.saveOrUpdate(customOrderBom);
	}

	/**
	 * 查询定单报关成品BOM
	 * 
	 * @param customOrder
	 */
	public List findCustomOrderBom(CustomOrder customOrder) {
		Integer type = customOrder.getCustomType();
		List list = null;
		list = this
				.find(
						"select a from CustomOrderBom a "
								+ "where a.customOrderExg.customOrderDetail.customOrder.id=? "
								+ " and a.customOrderExg.customOrderDetail.customOrder.customType=? and a.company=?",
						new Object[] { customOrder.getId(), type,
								CommonUtils.getCompany() });
		return list;
	}

	/**
	 * 查询定单报关成品BOM
	 * 
	 * @param customOrder
	 */
	public List findCustomOrderBom(CustomOrder customOrder,
			CustomOrderExg customOrderExg) {
		Integer type = customOrder.getCustomType();
		List list = null;
		list = this
				.find(
						"select a from CustomOrderBom a left join a.customOrderExg "
								+ "where a.customOrderExg.customOrderDetail.customOrder.id=? and"
								+ " a.customOrderExg.customOrderDetail.customOrder.customType=? and a.customOrderExg =? and a.company=?",
						new Object[] { customOrder.getId(), type,
								customOrderExg, CommonUtils.getCompany() });
		return list;
	}

	/**
	 * 查询定单报关成品
	 * 
	 * @param customOrder
	 */
	public CustomOrderExg findCustomOrderExg(CustomOrderDetail customOrderDetail) {
		Integer type = customOrderDetail.getCustomOrder().getCustomType();
		String id = customOrderDetail.getId();
		List list = this
				.find(
						"select a from CustomOrderExg a where a.customOrderDetail.id=? and a.customOrderDetail.customOrder.customType=? and a.company=?",
						new Object[] { id, type, CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			return (CustomOrderExg) list.get(0);
		} else {

			return null;
		}
	}

	/**
	 * 获得当前进出口的商品信息的个数
	 * 
	 * @param parentId
	 *            进出口申请单id
	 * @return 进出口商品信息的个数
	 */
	public int findCustomOrderDetailCount(String parentId) {
		List list = this.find("select count(*) from CustomOrderDetail b  "
				+ " where b.customOrder.id = ? ", new Object[] { parentId });
		if (list == null || list.size() <= 0) {
			return 0;
		}
		return Integer.parseInt(list.get(0).toString());
	}

	/**
	 * 查询定单报关成品
	 * 
	 * @param customOrder
	 */
	public List findCustomOrderExg(CustomOrder customOrder) {
		Integer type = customOrder.getCustomType();
		List list = this
				.find(
						"select a from CustomOrderExg a where a.customOrderDetail.customOrder=? and a.customOrderDetail.customOrder.customType=? and a.company=?",
						new Object[] { customOrder, type,
								CommonUtils.getCompany() });
		return list;
	}

	public List findFactoryExg(Complex complex, String tenName, String tenSpec,
			String tenUnit, Integer type) {
		/**
		 * type ==1 为 电子手册 type == 2 or 3为 纸质手册
		 */
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		parameters.add(complex);
		parameters.add(tenName);
		parameters.add(tenSpec);
		parameters.add(tenUnit);
		parameters.add(CommonUtils.getCompany());
		if (type == 1) {
			hsql = "select a.materiel from DzscInnerMergeData a where a.dzscTenInnerMerge.tenComplex=? and"
					+ " a.dzscTenInnerMerge.tenPtName=? and a.dzscTenInnerMerge.tenPtSpec"
					+ " and a.dzscTenInnerMerge.tenUnit.name=? and a.company=?";

		} else if (type == 2 || type == 3) {
			hsql = "select a.materiel from BcsInnerMerge a where a.bcsTenInnerMerge.complex=? and"
					+ " a.bcsTenInnerMerge.name=? and a.bcsTenInnerMerge.spec"
					+ " and a.bcsTenInnerMerge.comUnit.name=? and a.company=?";
		}

		return this.find(hsql, parameters.toArray());

	}

	public void saveCustomOrderImg(CustomOrderImg customOrderImg) {
		this.saveOrUpdate(customOrderImg);
	}

	public List findCustomOrderByType(Integer type) {
		List list = this
				.find(
						"select a from CustomOrder a where a.customType=? and a.ifok = ? and a.company=?",
						new Object[] { type, true, CommonUtils.getCompany() });
		return list;
	}

	public List findCustomOrder(Integer type) {
		List list = this
				.find(
						"select a from CustomOrder a where a.customType=? and a.company=?",
						new Object[] { type, CommonUtils.getCompany() });
		return list;
	}

	/**
	 * 查询定单报关料件
	 * 
	 * @param customOrder
	 */
	public List findCustomOrderImg(CustomOrder customOrder) {
		Integer type = customOrder.getCustomType();
		List list = this
				.find(
						"select a from CustomOrderImg a where a.customOrder=? and a.customOrder.customType=? and a.company=?",
						new Object[] { customOrder, type,
								CommonUtils.getCompany() });
		return list;
	}

	/**
	 * 删除定单报关成品
	 * 
	 * @param customOrderExg
	 */
	public void delCustomsOrderExg(CustomOrderExg customOrderExg) {
		this.delete(customOrderExg);
	}

	/**
	 * 删除定单报关料件
	 * 
	 * @param customOrderImg
	 */
	public void delCustomsOrderImg(CustomOrderImg customOrderImg) {
		this.delete(customOrderImg);
	}

	/**
	 * 根据订单删除订单报关料件
	 * 
	 * @param customOrderImg
	 */
	public void delCustomsOrderImg(CustomOrder customOrder) {
		this.batchUpdateOrDelete(" delete from CustomOrderImg "
				+ "a where a.customOrder = ? and a.company.id= ? ",
				new Object[] { customOrder, CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除定单报关ＢＯＭ
	 * 
	 * @param customOrderBom
	 */
	public void delCustomsOrderBom(CustomOrderBom customOrderBom) {
		this.delete(customOrderBom);
	}

	/**
	 * 根据订单号删除定单报关ＢＯＭ
	 * 
	 * @param customOrderBom
	 */
	public void delCustomsOrderBom(CustomOrder customOrder) {
		this
				.batchUpdateOrDelete(
						" delete from CustomOrderBom "
								+ "a where a.customOrderExg.customOrderDetail.customOrder = ? and a.company.id= ? ",
						new Object[] { customOrder,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 
	 */
	public List findListBcsInnerMerge(String ptNo, String materieltype,
			Integer type) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql1 = "";
		String hsql = "";
		String table = "";
		parameters.add(ptNo);
		parameters.add(CommonUtils.getCompany());
		parameters.add(materieltype);
		if (type == 1) {
			table = "DzscInnerMergeData";
			hsql1 = " and a.imrType =? ";

		} else if (type == 2 || type == 3) {
			table = "BcsInnerMerge";
			hsql1 = " and a.materielType =? ";
		}
		hsql = "select a from " + table
				+ " a where a.materiel.ptNo=? and a.company=? " + hsql1;
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 根据成品料号查询报关常用工厂BOM成品资料
	 * 
	 * @param ptNo
	 * @return
	 */
	public List findMaterialBomMaster(String ptNo, Integer type) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql1 = "";
		String hsql = "";
		String table = "";
		parameters.add(ptNo);
		parameters.add(CommonUtils.getCompany());
		if (type == 1) {
			table = "MaterialBomMasterApply";
			hsql1 = " and  a.stateMark=? ";
			parameters.add("2");
		} else if (type == 2 || type == 3) {
			table = "MaterialBomMaster";
		}
		hsql = "select a from " + table
				+ " a where a.materiel.ptNo=? and a.company=? " + hsql1;
		return this.find(hsql, parameters.toArray());

	}

	/**
	 * 电子手册的归并关系
	 * 
	 * @param ptNo
	 * @param materieltype
	 * @return
	 */
	public Complex findDzscInnerMergeDataComplex(String ptNo,
			String materieltype) {
		Complex complex = null;
		List list = this
				.find(
						"select a.dzscTenInnerMerge.tenComplex from DzscInnerMergeData a where a.materiel.ptNo = ? and a.imrType =? and a.company=?",
						new Object[] { ptNo, materieltype,
								CommonUtils.getCompany() });
		if (list.size() > 0) {
			complex = (Complex) list.get(0);
		}
		return complex;
	}

	/**
	 * 电子手册的归并关系中的十位编码
	 * 
	 * @param ptNo
	 * @param materieltype
	 * @return
	 */
	public DzscInnerMergeData findDzscInnerMergeData(String ptNo,
			String materieltype) {
		DzscInnerMergeData dzscInnerMergeData = null;
		List list = this
				.find(
						"select a from DzscInnerMergeData a where a.materiel.ptNo = ? and a.imrType =? and a.company=?",
						new Object[] { ptNo, materieltype,
								CommonUtils.getCompany() });
		if (list.size() > 0) {
			dzscInnerMergeData = (DzscInnerMergeData) list.get(0);
		}
		return dzscInnerMergeData;
	}

	public CustomOrder findCustomOrder(String billCode) {
		CustomOrder customOrder = null;
		List list = this.find("select a from CustomOrder a"
				+ " where a.billCode = ? and a.company.id=? ", new Object[] {
				billCode, CommonUtils.getCompany().getId() });
		if (list.size() != 0) {
			customOrder = (CustomOrder) list.get(0);
		}
		return customOrder;
	}

	public List findCustomOrderByType(Integer type, Boolean isTransfer) {
		List list = this
				.find(
						"select a from CustomOrder a where a.customType=? and a.ifzc=? and a.ifok = ? and a.company=?",
						new Object[] { type, isTransfer, true,
								CommonUtils.getCompany() });
		return list;
	}

	public List findOrders(String type, ScmCoc scmcoc, Date dateBegin,
			Date dateEnd, String[] orderCode, Boolean ifzc, Integer customType,
			String[] strsGroupingColumn) {
		List<TempCustomOrder> result = new ArrayList<TempCustomOrder>();
		List<Object> parameters = new ArrayList<Object>();
		String talbe = "";
		List<String> groupingColumn = new ArrayList<String>();
		String groupBy = " group by a.complex.code,a.name,a.spec,a.unit.name ";
		String leftjoin = " left join a.complex left join a.unit ";
		String condition = "";
		if ("0".equals(type)) {
			talbe = "CustomOrderExg";
			for (int i = 0; i < strsGroupingColumn.length; i++) {
				if (!"".equals(strsGroupingColumn[i])) {
					if ("送货日期".equals(strsGroupingColumn[i].trim())) {
						groupingColumn.add("a.customOrderDetail.salesdate");
					} else if ("料号".equals(strsGroupingColumn[i].trim())) {
						groupingColumn.add("a.customOrderDetail.materiel.ptNo");
					} else if ("客户".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderDetail.customOrder.customer.name");
					}
				}
			}
			condition = " and a.customOrderDetail.customOrder.customType = ?";

		} else if ("2".equals(type)) {
			talbe = "CustomOrderBom";
			for (int i = 0; i < strsGroupingColumn.length; i++) {
				if (!"".equals(strsGroupingColumn[i])) {
					if ("送货日期".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderExg.customOrderDetail.salesdate");
					} else if ("料号".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderExg.customOrderDetail.materiel.ptNo");
					} else if ("客户".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderExg.customOrderDetail.customOrder.customer.name");
					}
				}
			}
			condition = " and a.customOrderExg.customOrderDetail.customOrder.customType = ?";
		}
		String column = "";
		String group = "";
		for (int i = 0; i < groupingColumn.size(); i++) {
			String comma = ",";
			column += comma + groupingColumn.get(i).trim();
			group += comma + groupingColumn.get(i).trim();

		}
		groupBy += group;
		String hsql = " select a.complex.code,a.name,a.spec,a.unit.name,"
				+ "sum(a.amount),sum(a.notContractNum),sum(a.contractNum),"
				+ "sum(a.notTransNum),sum(a.transNum)"
				+ column
				+ "  from "
				+ talbe
				+ " a "
				+ leftjoin
				+ " where a.customOrderDetail.customOrder.ifzc =? and a.company.id= ?"
				+ condition;
		parameters.add(true);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(customType);
		/**
		 * "0"成品
		 */

		if ("0".equals(type)) {
			if (orderCode.length == 1 && !"".equals(orderCode[0])) {
				hsql += " and  a.customOrderDetail.customOrder.billCode  in ( ? ) ";
				parameters.add(orderCode[0]);
			}
			if (orderCode.length > 1) {
				hsql += " and  a.customOrderDetail.customOrder.billCode  in ( ";
				for (int k = 0; k < orderCode.length; k++) {
					if (k == orderCode.length - 1) {
						hsql += " ? ) ";
					} else {
						hsql += " ? , ";
					}
					parameters.add(orderCode[k]);
				}
			}
			if (scmcoc != null) {
				hsql += " and a.customOrderDetail.customOrder.customer =? ";
				parameters.add(scmcoc);
			}
			if (dateBegin != null) {
				hsql += " and a.customOrderDetail.salesdate >=? ";
				parameters.add(dateBegin);
			}
			if (dateEnd != null) {
				hsql += " and a.customOrderDetail.salesdate <=? ";
				parameters.add(dateEnd);
			}
			hsql += groupBy;
		} else if ("2".equals(type)) {
			if (orderCode.length == 1 && !"".equals(orderCode[0])) {
				hsql += " and  a.customOrderExg.customOrderDetail.customOrder.billCode  in ( ? ) ";
				parameters.add(orderCode[0]);
			}
			if (orderCode.length > 1) {
				hsql += " and  a.customOrderExg.customOrderDetail.customOrder.billCode  in ( ";
				for (int k = 0; k < orderCode.length; k++) {
					if (k == orderCode.length - 1) {
						hsql += " ? ) ";
					} else {
						hsql += " ? , ";
					}
					parameters.add(orderCode[k]);
				}
			}
			if (scmcoc != null) {
				hsql += " and a.customOrderExg.customOrderDetail.customOrder.customer =? ";
				parameters.add(scmcoc);
			}
			if (dateBegin != null) {
				hsql += " and a.customOrderExg.customOrderDetail.salesdate >=? ";
				parameters.add(dateBegin);
			}
			if (dateEnd != null) {
				hsql += " and a.customOrderExg.customOrderDetail.salesdate <=? ";
				parameters.add(dateEnd);
			}
			hsql += groupBy;
		}
		List list = this.find(hsql, parameters.toArray());
		int index = strsGroupingColumn.length;
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			TempCustomOrder tempcustomOrder = new TempCustomOrder();
			String code = obj[0] == null ? "" : (String) obj[0];
			String name = obj[1] == null ? "" : (String) obj[1];
			String spec = obj[2] == null ? "" : (String) obj[2];
			String unit = obj[3] == null ? "" : (String) obj[3];
			Double amount = obj[4] == null ? 0.0 : (Double) obj[4];
			Double notContractNum = obj[5] == null ? 0.0 : (Double) obj[5];
			Double contractNum = obj[6] == null ? 0.0 : (Double) obj[6];
			Double notTransNum = obj[7] == null ? 0.0 : (Double) obj[7];
			Double transNum = obj[8] == null ? 0.0 : (Double) obj[8];
			for (int j = 0; j < index; j++) {
				if (j == 0) {
					if ("送货日期".equals(strsGroupingColumn[j].trim())) {
						Date salesdate = (Date) obj[9];
						tempcustomOrder.setSalesdate(salesdate);
					} else if ("料号".equals(strsGroupingColumn[j].trim())) {
						String ptNo = obj[9] == null ? "" : (String) obj[9];
						tempcustomOrder.setPtNo(ptNo);
					} else if ("客户".equals(strsGroupingColumn[j].trim())) {
						String customer = obj[9] == null ? "" : (String) obj[9];
						tempcustomOrder.setCustomer(customer);
					}

				} else if (j == 1) {
					if ("送货日期".equals(strsGroupingColumn[j].trim())) {
						Date salesdate = (Date) obj[10];
						tempcustomOrder.setSalesdate(salesdate);
					} else if ("料号".equals(strsGroupingColumn[j].trim())) {
						String ptNo = obj[10] == null ? "" : (String) obj[10];
						tempcustomOrder.setPtNo(ptNo);
					} else if ("客户".equals(strsGroupingColumn[j].trim())) {
						String customer = obj[10] == null ? ""
								: (String) obj[10];
						tempcustomOrder.setCustomer(customer);
					}
				} else if (j == 2) {
					if ("送货日期".equals(strsGroupingColumn[j].trim())) {
						Date salesdate = (Date) obj[11];
						tempcustomOrder.setSalesdate(salesdate);
					} else if ("料号".equals(strsGroupingColumn[j].trim())) {
						String ptNo = obj[11] == null ? "" : (String) obj[11];
						tempcustomOrder.setPtNo(ptNo);
					} else if ("客户".equals(strsGroupingColumn[j].trim())) {
						String customer = obj[11] == null ? ""
								: (String) obj[11];
						tempcustomOrder.setCustomer(customer);
					}
				}
			}
			tempcustomOrder.setCode(code);
			tempcustomOrder.setName(name);
			tempcustomOrder.setSpec(spec);
			tempcustomOrder.setUnit(unit);
			tempcustomOrder.setAmount(amount);
			tempcustomOrder.setNotContractNum(notContractNum);
			tempcustomOrder.setNotTransNum(notTransNum);
			tempcustomOrder.setContractNum(contractNum);
			tempcustomOrder.setTransNum(transNum);
			result.add(tempcustomOrder);
		}

		return result;

	}

	public List findOrders(String type, ScmCoc scmcoc, Date dateBegin,
			Date dateEnd, String[] orderCode, Integer customType,
			String[] strsGroupingColumn) {
		List<TempCustomOrder> result = new ArrayList<TempCustomOrder>();
		List<Object> parameters = new ArrayList<Object>();
		String talbe = "";
		List<String> groupingColumn = new ArrayList<String>();
		String groupBy = " group by a.complex.code,a.name,a.spec,a.unit.name ";
		String leftjoin = " left join a.complex left join a.unit ";
		String condition = "";
		/**
		 * "0"成品
		 */

		if ("0".equals(type)) {
			talbe = "CustomOrderExg";
			for (int i = 0; i < strsGroupingColumn.length; i++) {
				if (!"".equals(strsGroupingColumn[i])) {
					if ("送货日期".equals(strsGroupingColumn[i].trim())) {
						groupingColumn.add("a.customOrderDetail.salesdate");
					} else if ("料号".equals(strsGroupingColumn[i].trim())) {
						groupingColumn.add("a.customOrderDetail.materiel.ptNo");
					} else if ("客户".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderDetail.customOrder.customer.name");
					}
				}
			}
			condition = " and a.customOrderDetail.customOrder.customType = ?";

		} else if ("2".equals(type)) {
			talbe = "CustomOrderBom";
			for (int i = 0; i < strsGroupingColumn.length; i++) {
				if (!"".equals(strsGroupingColumn[i])) {
					if ("送货日期".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderExg.customOrderDetail.salesdate");
					} else if ("料号".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderExg.customOrderDetail.materiel.ptNo");
					} else if ("客户".equals(strsGroupingColumn[i].trim())) {
						groupingColumn
								.add("a.customOrderExg.customOrderDetail.customOrder.customer.name");
					}
				}
			}
			condition = " and a.customOrderExg.customOrderDetail.customOrder.customType = ?";

		}

		String column = "";
		String group = "";
		for (int i = 0; i < groupingColumn.size(); i++) {
			String comma = ",";
			column += comma + groupingColumn.get(i).trim();
			group += comma + groupingColumn.get(i).trim();

		}
		groupBy += group;
		String hsql = " select a.complex.code,a.name,a.spec,a.unit.name,"
				+ "sum(a.amount),sum(a.notContractNum),sum(a.contractNum),"
				+ "sum(a.notTransNum),sum(a.transNum)"
				+ column
				+ "  from "
				+ talbe
				+ " a "
				+ leftjoin
				+ " where a.customOrderDetail.customOrder.ifzc =? and a.company.id= ?"
				+ condition;
		parameters.add(false);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(customType);

		/**
		 * "0"成品
		 */

		if ("0".equals(type)) {
			if (orderCode.length == 1 && !"".equals(orderCode[0])) {
				hsql += " and  a.customOrderDetail.customOrder.billCode  in ( ? ) ";
				parameters.add(orderCode[0]);
			}
			if (orderCode.length > 1) {
				hsql += " and  a.customOrderDetail.customOrder.billCode  in ( ";
				for (int k = 0; k < orderCode.length; k++) {
					if (k == orderCode.length - 1) {
						hsql += " ? ) ";
					} else {
						hsql += " ? , ";
					}
					parameters.add(orderCode[k]);
				}
			}
			if (scmcoc != null) {
				hsql += " and a.customOrderDetail.customOrder.customer =? ";
				parameters.add(scmcoc);
			}
			if (dateBegin != null) {
				hsql += " and a.customOrderDetail.salesdate >=? ";
				parameters.add(dateBegin);
			}
			if (dateEnd != null) {
				hsql += " and a.customOrderDetail.salesdate <=? ";
				parameters.add(dateEnd);
			}
			hsql += groupBy;
		} else if ("2".equals(type)) {
			if (orderCode.length == 1 && !"".equals(orderCode[0])) {
				hsql += " and  a.customOrderExg.customOrderDetail.customOrder.billCode  in ( ? ) ";
				parameters.add(orderCode[0]);
			}
			if (orderCode.length > 1) {
				hsql += " and  a.customOrderExg.customOrderDetail.customOrder.billCode  in ( ";
				for (int k = 0; k < orderCode.length; k++) {
					if (k == orderCode.length - 1) {
						hsql += " ? ) ";
					} else {
						hsql += " ? , ";
					}
					parameters.add(orderCode[k]);
				}
			}
			if (scmcoc != null) {
				hsql += " and a.customOrderExg.customOrderDetail.customOrder.customer =? ";
				parameters.add(scmcoc);
			}
			if (dateBegin != null) {
				hsql += " and a.customOrderExg.customOrderDetail.salesdate >=? ";
				parameters.add(dateBegin);
			}
			if (dateEnd != null) {
				hsql += " and a.customOrderExg.customOrderDetail.salesdate <=? ";
				parameters.add(dateEnd);
			}
			hsql += groupBy;
		}
		List list = this.find(hsql, parameters.toArray());
		int index = strsGroupingColumn.length;
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			TempCustomOrder tempcustomOrder = new TempCustomOrder();
			String code = obj[0] == null ? "" : (String) obj[0];
			String name = obj[1] == null ? "" : (String) obj[1];
			String spec = obj[2] == null ? "" : (String) obj[2];
			String unit = obj[3] == null ? "" : (String) obj[3];
			Double amount = obj[4] == null ? 0.0 : (Double) obj[4];
			Double notContractNum = obj[5] == null ? 0.0 : (Double) obj[5];
			Double contractNum = obj[6] == null ? 0.0 : (Double) obj[6];
			Double notTransNum = obj[7] == null ? 0.0 : (Double) obj[7];
			Double transNum = obj[8] == null ? 0.0 : (Double) obj[8];
			for (int j = 0; j < index; j++) {
				if (j == 0) {
					if ("送货日期".equals(strsGroupingColumn[j].trim())) {
						Date salesdate = (Date) obj[9];
						tempcustomOrder.setSalesdate(salesdate);
					} else if ("料号".equals(strsGroupingColumn[j].trim())) {
						String ptNo = obj[9] == null ? "" : (String) obj[9];
						tempcustomOrder.setPtNo(ptNo);
					} else if ("客户".equals(strsGroupingColumn[j].trim())) {
						String customer = obj[9] == null ? "" : (String) obj[9];
						tempcustomOrder.setCustomer(customer);
					}

				} else if (j == 1) {
					if ("送货日期".equals(strsGroupingColumn[j].trim())) {
						Date salesdate = (Date) obj[10];
						tempcustomOrder.setSalesdate(salesdate);
					} else if ("料号".equals(strsGroupingColumn[j].trim())) {
						String ptNo = obj[10] == null ? "" : (String) obj[10];
						tempcustomOrder.setPtNo(ptNo);
					} else if ("客户".equals(strsGroupingColumn[j].trim())) {
						String customer = obj[10] == null ? ""
								: (String) obj[10];
						tempcustomOrder.setCustomer(customer);
					}
				} else if (j == 2) {
					if ("送货日期".equals(strsGroupingColumn[j].trim())) {
						Date salesdate = (Date) obj[11];
						tempcustomOrder.setSalesdate(salesdate);
					} else if ("料号".equals(strsGroupingColumn[j].trim())) {
						String ptNo = obj[11] == null ? "" : (String) obj[11];
						tempcustomOrder.setPtNo(ptNo);
					} else if ("客户".equals(strsGroupingColumn[j].trim())) {
						String customer = obj[11] == null ? ""
								: (String) obj[11];
						tempcustomOrder.setCustomer(customer);
					}
				}
			}
			tempcustomOrder.setCode(code);
			tempcustomOrder.setName(name);
			tempcustomOrder.setSpec(spec);
			tempcustomOrder.setUnit(unit);
			tempcustomOrder.setAmount(amount);
			tempcustomOrder.setNotContractNum(notContractNum);
			tempcustomOrder.setNotTransNum(notTransNum);
			tempcustomOrder.setContractNum(contractNum);
			tempcustomOrder.setTransNum(transNum);
			result.add(tempcustomOrder);
		}

		return result;
		// return this.find(hsql, parameters.toArray());

	}

	public List findCustomOrder(Integer type, ScmCoc scmCoc, Date beginDate,
			Date endDate, String[] orderCode, Boolean isTransfer) {

		List<Object> parameters = new ArrayList<Object>();

		String hsql = " select a from CustomOrder"
				+ " a "
				+ " where a.company.id= ? and a.ifok =? and a.ifzc =? and a.customType=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(true);
		parameters.add(isTransfer);
		parameters.add(type);
		if (orderCode.length == 1 && !"".equals(orderCode[0])) {
			hsql += " and  a.billCode  in ( ? ) ";
			parameters.add(orderCode[0]);
		}
		if (orderCode.length > 1) {
			hsql += " and  a.billCode  in ( ";
			for (int k = 0; k < orderCode.length; k++) {
				if (k == orderCode.length - 1) {
					hsql += " ? ) ";
				} else {
					hsql += " ? , ";
				}
				parameters.add(orderCode[k]);
			}
		}

		if (scmCoc != null) {
			hsql += " and a.customer =? ";
			parameters.add(scmCoc);
		}
		if (beginDate != null) {
			hsql += " and a.orderDate >=? ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hsql += " and a.orderDate <=? ";
			parameters.add(endDate);
		}

		return this.find(hsql, parameters.toArray());

	}

	public List findReport(String orderCode, String className) {
		String hsql = "";
		if ("CustomOrderExg".equals(className)) {
			hsql = " a.customOrderDetail.customOrder.billCode=? ";
		} else if ("CustomOrderBom".equals(className)) {
			hsql = " a.customOrderExg.customOrderDetail.customOrder.billCode=? ";
		} else if ("CustomOrderImg".equals(className)) {
			hsql = " a.customOrder.billCode=? ";
		}
		return this.find("select a from " + className + " a where " + hsql
				+ " and a.company=?", new Object[] { orderCode,
				CommonUtils.getCompany() });

	}

	/**
	 * 
	 * @param Startdate
	 * @param enddate
	 * @return
	 */
	public List findCustomOrder(Date Startdate, Date enddate) {
		List prop = new ArrayList();
		String sql = "select a from CustomOrder a " + " where a.company.id=? ";
		prop.add(CommonUtils.getCompany().getId());
		if (Startdate != null) {
			sql += " and a.orderDate >=? ";
			prop.add(Startdate);
		}
		if (enddate != null) {
			sql += " and a.orderDate <=? ";
			prop.add(enddate);
		}
		List result = this.find(sql, prop.toArray());
		return result;
	}

	/**
	 * 
	 * @param billcode
	 * @return
	 */
	public Integer findCustomOrderMaxImportcount(String billcode) {
		List result = this.find(
				" select Max(a.importcount) from CustomOrder a "
						+ " where a.company.id=? and a.billCode=? ",
				new Object[] { CommonUtils.getCompany().getId(), billcode });
		if (result.size() > 0 && result.get(0) != null) {
			return (Integer) result.get(0);
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Customparames saveCustomparames(Customparames value) {
		this.saveOrUpdate(value);
		return value;
	}

	public List getDzscCustomBomExg() {
		return this.find("select a from DzscCustomsBomExg a"
				+ " where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	public List getDzscCustomsBomDetail(DzscCustomsBomExg dzscCustomsBomExg) {
		return this.find("select a from DzscCustomsBomDetail a"
				+ " where a.company.id=? and a.dzscCustomsBomExg=? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询定单导入文件栏位对应次序
	 * 
	 * @param ifok
	 * @return
	 */
	public OrderDateIndex findOrderDateIndex() {
		List list = this.find("select a from OrderDateIndex a"
				+ " where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (OrderDateIndex) list.get(0);
		} else {
			OrderDateIndex order = new OrderDateIndex();
			this.saveOrUpdate(order);
			return order;
		}
	}

	public List findCustomOrder(Integer type, Boolean ifOk) {
		List list = this
				.find(
						"select a from CustomOrder a where a.customType=? and a.ifok = ? and a.company=?",
						new Object[] { type, ifOk, CommonUtils.getCompany() });
		return list;
	}

	public List findCustomOrder(Integer type, List list) {
		String sql = "";
		if (list != null && list.size() > 0) {
			sql += " and ( ";
			String comma = " or ";
			for (int i = 0; i < list.size(); i++) {
				String orderCode = list.get(i).toString().trim();
				sql += " a.billCode = '" + orderCode + "'";
				if (i == list.size() - 1) {
					comma = ") ";
				}
				sql += comma;
			}
		}
		List result = this.find(
				"select a from CustomOrder a where a.customType=? and a.company=? "
						+ sql, new Object[] { type, CommonUtils.getCompany() });
		return result;
	}

	public CustomOrder findCustomOrderbyBillCode(String billCode, Boolean ifOk) {
		List list = this
				.find(
						"select a from CustomOrder a where a.billCode=? and a.ifok = ? and a.company=?",
						new Object[] { billCode, true, CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			return (CustomOrder) list.get(0);
		} else {

			return null;
		}
	}

	public List findCustomOrderDetail(Integer type, Boolean ifOk) {
		List list = this
				.find(
						"select a from CustomOrderDetail a where a.customOrder.customType=? and a.customOrder.ifok = ? and a.company=?",
						new Object[] { type, ifOk, CommonUtils.getCompany() });
		return list;
	}

	public List findCustomOrderDetail(Integer type, List list) {
		String sql = "";
		if (list != null && list.size() > 0) {
			sql += " and ( ";
			String comma = " or ";
			for (int i = 0; i < list.size(); i++) {
				String orderCode = list.get(i).toString().trim();
				sql += " a.customOrder.billCode = '" + orderCode + "'";
				if (i == list.size() - 1) {
					comma = ") ";
				}
				sql += comma;
			}
		}
		List result = this
				.find(
						"select a from CustomOrderDetail a where a.customOrder.customType=? and a.company=?"
								+ sql, new Object[] { type,
								CommonUtils.getCompany() });
		return result;
	}

	public ScmCoc findScmCoc(String name) {
		List list = this.find("select a from ScmCoc a where a.name=?",
				new Object[] { name });
		if (list.size() > 0 && list.get(0) != null) {
			return (ScmCoc) list.get(0);
		} else {
			return null;
		}
	}
	
	
	public Integer getCustomOrderDetailForToContract(CustomOrder head) {
		List list = this
				.find(
						"select count(a.id) from CustomOrderDetail a where "
								+ "a.customOrder.id = ? and a.ifcustomer = ? ",
						new Object[] { head.getId(), new Boolean(true) });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}
	

	public CustomOrder saveCustomOrder(CustomOrder customOrder) {
		this.saveOrUpdate(customOrder);
		return customOrder;
	}

	public Materiel findMateriel(String ptNo) {
		List list = this
				.find(
						"select a from Materiel a left join a.calUnit left join a.ptUnit where a.ptNo=? and a.company=?",
						new Object[] { ptNo, CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			return (Materiel) list.get(0);
		} else {

			return null;
		}
	}

	public CalUnit findCalUnit(String unit) {
		List list = this.find("select a from CalUnit a where a.name=?",
				new Object[] { unit });
		if (list.size() > 0 && list.get(0) != null) {
			return (CalUnit) list.get(0);
		} else {

			return null;
		}
	}

	public Curr findCurr(String curr) {
		List list = this.find("select a from Curr a where a.name=?",
				new Object[] { curr });
		if (list.size() > 0 && list.get(0) != null) {
			return (Curr) list.get(0);
		} else {

			return null;
		}
	}

	public void saveOrderDateIndex(OrderDateIndex orderDateIndex) {
		this.saveOrUpdate(orderDateIndex);
	}

	public List findMateriel() {
		return this
				.find(
						"select a from Materiel a left join a.calUnit left join a.ptUnit where a.company=?",
						new Object[] { CommonUtils.getCompany() });
	}

	public List findDzscCustomsBomDetail(DzscCustomsBomExg dzscCustomsBomExg) {
		return this.find(
				"select a from DzscCustomsBomDetail a left join a.dzscCustomsBomExg where "
						+ "a.dzscCustomsBomExg=? and a.company=?",
				new Object[] { dzscCustomsBomExg, CommonUtils.getCompany() });
	}

	public List findDzscCustomsBomDetail(String complexName, String tenName,
			String tenSpec, String unitName) {
		String hsql = "";
		if (complexName == null) {
			hsql += " a.dzscCustomsBomExg.complex.name = null and ";
		} else {
			hsql += " a.dzscCustomsBomExg.complex.name = " + "'" + complexName
					+ "' and ";
		}
		if (tenName == null) {
			hsql += " a.dzscCustomsBomExg.name = null and ";
		} else {
			hsql += " a.dzscCustomsBomExg.name = " + "'" + tenName + "' and ";
		}
		if (tenSpec == null) {
			hsql += " a.dzscCustomsBomExg.spec = null and ";
		} else {
			hsql += " a.dzscCustomsBomExg.spec = " + "'" + tenSpec + "' and ";
		}
		if (unitName == null) {
			hsql += " a.dzscCustomsBomExg.unit.name = null and ";
		} else {
			hsql += " a.dzscCustomsBomExg.unit.name = " + "'" + unitName
					+ "' and ";
		}
		return this.find(
				"select a from DzscCustomsBomDetail a left join a.dzscCustomsBomExg where "
						+ hsql + "a.company=?", new Object[] { CommonUtils
						.getCompany() });
	}

	public List findDzscInnerMergeData(int index, int length, String property,
			Object value, boolean isLike) {

		List<Object> paramters = new ArrayList<Object>();

		String hsql = " select a from DzscInnerMergeData a left join fetch a.materiel "
				+ " left join fetch a.dzscTenInnerMerge "
				+ " left join fetch a.dzscTenInnerMerge.dzscFourInnerMerge "
				+ " where a.imrType = ? and a.company.id=? and a.isChange=?";
		paramters.add("0");
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(false);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and a." + property + " like ? ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and a." + property + " = ? ";
				paramters.add(value);
			}
		}
		hsql += " order by a.dzscTenInnerMerge.dzscFourInnerMerge.fourSeqNum desc,"
				+ " a.dzscTenInnerMerge.tenSeqNum desc";
		long beginTime = System.currentTimeMillis();
		List list = super
				.findPageList(hsql, paramters.toArray(), index, length);
		long endTime = System.currentTimeMillis();
		System.out.println(":::::::::::::::total time:" + (endTime - beginTime)
				/ 1000);
		return list;

	}

	public List findBcsInnerMergea(int index, int length, String property,
			Object value, boolean isLike, int orderType) {

		List listTenCode = new ArrayList();
		/**
		 * 找十码不为空的数据
		 */
		List<Object> paramters = new ArrayList<Object>();

		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company.id= ? "
				// + " and a.materielType=? "
				+ "	  and   a.bcsTenInnerMerge.seqNum is not null ";
		// and a.bcsTenInnerMerge.seqNum != ?
		paramters.add(CommonUtils.getCompany().getId());
		// paramters.add("0");
		// paramters.add("");
		if (orderType == 0) {
			hsql += " and  a.materielType=? ";
			paramters.add("0");
		} else if (orderType == 1) {
			hsql += " and  a.materielType=? ";
			paramters.add("2");
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		listTenCode = super.findPageList(hsql, paramters.toArray(), index,
				length);
		return listTenCode;
	}

	public List<CustomOrder> findOrderMaster(int index, int length,
			String property, Object value, boolean isLike) {

		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from CustomOrder a where  a.company=? ";
		paramters.add(CommonUtils.getCompany());
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

	public CustomOrderDetail findCustomOrderDetail(String scmCocName,
			String billCode, Boolean type, String ptNo) {
		List list = this
				.find(
						"select a from CustomOrderDetail a where a.customOrder.customer.code=?"
								+ " and a.customOrder.billCode and a.customOrder.customType=?"
								+ " and a.materiel.ptNo = ? and a.company=?",
						new Object[] { scmCocName, billCode, type, ptNo,
								CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			return (CustomOrderDetail) list.get(0);
		} else {

			return null;
		}
	}

	public List findCustomOrderDetail(CustomOrder customOrder) {
		return this.find(
				"select a from CustomOrderDetail a where a.customOrder = ? "
						+ " and a.company=? ", new Object[] { customOrder,
						CommonUtils.getCompany() });

	}

	public CustomOrderBom findCustomOrderBom(CustomOrderDetail orderdetail) {
		List list = this.find(
				"select a from CustomOrderBom a where a.customOrderExg.customOrderDetail = ? "
						+ " and a.company=?", new Object[] { orderdetail,
						CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			return (CustomOrderBom) list.get(0);
		} else {

			return null;
		}
	}

	public Double getUnitConvert(String ptNo) {
		List list = this.find("select a from Materiel a where a.ptNo = ? "
				+ " and a.company=?", new Object[] { ptNo,
				CommonUtils.getCompany() });
		if (list.size() > 0 && list.get(0) != null) {
			Materiel materiel = (Materiel) list.get(0);
			Double unitConvert = (materiel.getUnitConvert() == null || materiel
					.getUnitConvert() == 0.0) ? 1.0 : materiel.getUnitConvert();
			return unitConvert;
		} else {

			return 1.0;
		}
	}

	/**
	 * 查询未转合同的订单表头
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderHeadByNotChangeToContract(Integer orderType) {
		return this
				.find(
						"select distinct b from CustomOrderDetail a  left join a.customOrder b "
								+ " where a.company.id=? and (a.ifcustomer = ? or a.ifcustomer is null ) and b.ifok=? and b.ordertype=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								new Boolean(false), new Boolean(true),
								orderType });
	}

	/**
	 * 查询未转合同的订单表体
	 * 
	 * @param customOrder
	 * @return
	 */
	public List findCustomOrderDetailByNotChangeToContract(
			CustomOrder customOrder) {
		return this
				.find(
						"select a from CustomOrderDetail a "
								+ " where a.company.id=? and a.customOrder.id=? and a.customOrder.ifok=? and (a.ifcustomer = ? or a.ifcustomer is null ) ",
						new Object[] { CommonUtils.getCompany().getId(),
								customOrder.getId(), new Boolean(true),
								new Boolean(false) });
	}

	public List findContractByProcessExe(Integer customType) {
		if (customType > 1) {
			return this
					.find(
							"select a from Contract a where a.company= ? and ( a.isCancel=? ) and (a.declareState=? or a.declareState=?)",
							new Object[] { CommonUtils.getCompany(),
									new Boolean(false),
									DeclareState.CHANGING_EXE,
									DeclareState.APPLY_POR });
		} else if (customType == 1) {
			return this
					.find(
							"select a from DzscEmsPorHead a where a.company= ? and (a.declareState=? or a.declareState=?)",
							new Object[] { CommonUtils.getCompany(),
									DzscState.CHANGE, DzscState.APPLY });

		} else {
			return null;
		}

	}

	// public List findContractExgByContractId(String parentId) {
	// return this.find("select a from ContractExg a where a.contract.id = ? "
	// + " order by a.seqNum ", new Object[] { parentId });
	// }
	/**
	 * 查找合同料件 来自 帐册编号
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByEmsNo(String emsNo, Integer customType) {
		if (customType > 1) {
			return this
					.find(
							"select a from ContractExg a where a.contract.copEmsNo = ? "
									+ " and ( a.contract.isCancel=? ) and (a.contract.declareState=? or a.contract.declareState=?) "
									+ " and a.contract.company.id=? ",
							new Object[] { emsNo, false,
									DeclareState.CHANGING_EXE,
									DeclareState.APPLY_POR,
									CommonUtils.getCompany().getId() });
		} else if (customType == 1) {
			return this
					.find(
							"select a from DzscEmsExgBill a where a.dzscEmsPorHead.copTrNo = ? "
									+ " and (a.dzscEmsPorHead.declareState=? or a.dzscEmsPorHead.declareState=?)"
									+ " and a.dzscEmsPorHead.company.id=? ",
							new Object[] { emsNo, DzscState.CHANGE,
									DzscState.APPLY,
									CommonUtils.getCompany().getId() });
		} else {
			return null;
		}

	}
	public Double StateContractBomAmount(Integer customType, Integer seqNum,
			String id) {
		Double amount = 0.0;
		if (customType > 1) {
			List list = this
					.find(
							"select sum(a.amount) from ContractBom a left join a.contractExg where a.contractImgSeqNum = ? "
									+ " and a.contractExg.contract.id =? and a.company.id = ? ",
							new Object[] { seqNum, id,
									CommonUtils.getCompany().getId() });
			amount = Double
					.valueOf((list.get(0) == null || "".equals(list.get(0)) ? "0.0"
							: list.get(0).toString()));

		} else if (customType == 1) {
			List list = this
					.find(
							"select sum(a.amount) from DzscEmsBomBill a where a.imgSeqNum = ? "
									+ " and a.dzscEmsExgBill.dzscEmsPorHead.id =? and a.company.id = ? ",
							new Object[] { seqNum, id,
									CommonUtils.getCompany().getId() });
			amount = Double
					.valueOf((list.get(0) == null || "".equals(list.get(0)) ? "0.0"
							: list.get(0).toString()));

		}
		return amount;
	}
	/**
	 * 查找条件是申报状态的合同
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是Contract型，合同备案表头
	 */
	public Contract findContractByEmsNo(String emsNo) {
		List list = this
				.find(
						"select a from Contract a where a.company= ? and a.copEmsNo= ? and (a.declareState=? or a.declareState=?)",
						new Object[] { CommonUtils.getCompany(), emsNo,
								DeclareState.CHANGING_EXE,
								DeclareState.APPLY_POR });
		if (list != null && list.size() > 0) {
			return (Contract) list.get(0);
		}
		return null;
	}

	/**
	 * 查找条件是申报状态的合同
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @return List 是Contract型，合同备案表头
	 */
	public DzscEmsPorHead findDzscEmsPorHeadByEmsNo(String emsNo) {
		List list = this
				.find(
						"select a from DzscEmsPorHead a where a.company= ? and a.copTrNo= ? and a.declareState=? or a.declareState=? ",
						new Object[] { CommonUtils.getCompany(), emsNo,
								DzscState.CHANGE, DzscState.APPLY });
		if (list != null && list.size() > 0) {
			return (DzscEmsPorHead) list.get(0);
		}
		return null;
	}

	public List findMaterielBtAllInnerMeger(String materielType) {
		List prara = new ArrayList();
		prara.add(CommonUtils.getCompany().getId());
		prara.add(materielType);
		String bcshsql = " select distinct a.materiel from  BcsInnerMerge  a where a.company.id=? "
				+ " and a.materielType=? ";

		List bsclist = this.find(bcshsql, prara.toArray());
		// -----------------------------------------------------------------------
		String bsuchsql = " select distinct a.materiel from  InnerMergeData  a where a.company.id=? "
				+ " and a.imrType=? ";
		List bsuclist = this.find(bsuchsql, prara.toArray());
		// -----------------------------------------------------------------------
		String dzschsql = " select distinct a.materiel from  DzscInnerMergeData  a where a.company.id=? "
				+ " and a.imrType=? ";
		List dzsclist = this.find(dzschsql, prara.toArray());
		// -----------------------------------------------------------------------
		Set set = new HashSet();
		set.addAll(bsclist);
		set.addAll(bsuclist);
		set.addAll(dzsclist);
		return new ArrayList(set);
	}
	
	public List findAllCustomOrderDetail() {
		return this.find(
				"select a from CustomOrderDetail a where a.company=? ", new Object[] { 
						CommonUtils.getCompany() });

	}
}
