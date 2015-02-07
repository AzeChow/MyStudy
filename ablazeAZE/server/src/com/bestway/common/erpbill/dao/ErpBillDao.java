package com.bestway.common.erpbill.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

public class ErpBillDao extends BaseDao {

	public CustomOrder saveCustomOrder(CustomOrder order) {
		this.saveOrUpdate(order);
		return order;
	}

	public void deleteCustomOrder(CustomOrder order) {
		this.delete(order);
	}

	public List findCustomOrder() {
		return this.find("select a from CustomOrder a"
				+ " where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	/**
	 * 通过客户名称查寻订单
	 * 
	 * @param customer
	 * @return
	 */
	public List findCustomOrderByCustomer(ScmCoc customer) {
		return this.find("select a from CustomOrder a"
				+ " where a.company.id=? and a.ScmCoc.code=? ", new Object[] {
				CommonUtils.getCompany().getId(), customer.getCode() });
	}

	public CustomOrder findCustomOrderBybillCode(String billcode) {
		List result = this.find("select a from CustomOrder a"
				+ " where a.company.id=? and a.billCode=? ", new Object[] {
				CommonUtils.getCompany().getId(), billcode });

		if (result.size() > 0) {
			return (CustomOrder) result.get(0);
		} else {

			return null;
		}
	}

	public int findCustomOrderMaxImportcount(String billcode) {
		List result = this.find(
				"select ISNULL(Max(a.importcount),0) from CustomOrder a"
						+ " where a.company.id=? and a.billCode=? ",
				new Object[] { CommonUtils.getCompany().getId(), billcode });
		if (result.size() > 0) {
			return (Integer) result.get(0);
		} else {

			return 0;
		}
	}

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
	 * 保存订单体
	 * 
	 * @param orderDetail
	 */
	public CustomOrderDetail saveCustomOrderDetail(CustomOrderDetail orderDetail) {
		this.saveOrUpdate(orderDetail);
		return orderDetail;
	}

	public void deleteCustomOrdeDetail(CustomOrderDetail orderDetail) {
		this.delete(orderDetail);
	}

	public void deleteCustomOrdeDetail(CustomOrder order) {
		this.batchUpdateOrDelete(
				"delete from CustomOrderDetail a where a.customOrder.id=?",
				new Object[] { order.getId() });
	}

	public List findCustomOrderDetail(CustomOrder order) {
		return this
				.find("select a from CustomOrderDetail a"
						+ " where a.company.id=? and a.customOrder.id=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								order.getId() });
	}

	public CustomOrderDetail findCustomOrderDetailByPtno(CustomOrder order,
			String ptno) {
		List result = this
				.find(
						"select a from CustomOrderDetail a"
								+ " where a.company.id=? and a.customOrder.id=? and a.materiel.ptNo=?",
						new Object[] { CommonUtils.getCompany().getId(),
								order.getId(), ptno });
		if (result.size() > 0) {
			return (CustomOrderDetail) result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存参数设置
	 * 
	 * @param value
	 */
	public void saveparames(Customparames value) {
		this.saveOrUpdate(value);
	}

	/**
	 * 查询参数设置
	 * 
	 * @return
	 */
	public Customparames findparames() {
		List list = this.find("select a from Customparames a");
		if (list.size() > 0) {
			return (Customparames) list.get(0);
		} else {
			return null;
		}
	}

	public List findBillMaster(Map map) {
		String hsql = "";
		List prop = new ArrayList();
		// --------------------------------------------------------------
		String materielType = (String) map.get("materielType");
		String tableName = BillUtil
				.getBillDetailTableNameByMaterielType(materielType);
		hsql += " select a  from  " + tableName
				+ "  a left join  a.billMaster b  where  a.company.id = ? ";
		prop.add(CommonUtils.getCompany().getId());
		// --------------------------------------------------------------
		Object factoryName = map.get("factoryName");
		if (factoryName != null) {
			String name = (String) factoryName;
			hsql += " and a.ptName = ? ";
			prop.add(name);
		}
		// --------------------------------------------------------------
		Object codeWarSet = map.get("codeWarSet");
		if (codeWarSet != null) {
			WareSet wset = (WareSet) codeWarSet;
			hsql += " and a.wareSet = ? ";
			prop.add(wset);
		}
		// --------------------------------------------------------------
		Object codeCustomer = map.get("codeCustomer");
		if (codeCustomer != null) {
			ScmCoc su = (ScmCoc) codeCustomer;
			hsql += " and b.scmCoc = ? ";
			prop.add(su);
		}
		// --------------------------------------------------------------
		Object codeBillType = map.get("codeBillType");
		if (codeBillType != null) {
			String type = (String) codeBillType;
			hsql += " and b.billType.code = ? ";
			prop.add(type);
		}
		// --------------------------------------------------------------
		Object begin = map.get("begin");
		if (begin != null) {
			Date be = (Date) begin;
			hsql += " and b.validDate >= ? ";
			prop.add(be);
		}
		// --------------------------------------------------------------
		Object end = map.get("end");
		if (end != null) {
			Date en = (Date) end;
			hsql += " and b.validDate <= ? ";
			prop.add(en);
		}
		// --------------------------------------------------------------
		Object keepAccounts = map.get("keepAccounts");
		if (keepAccounts != null) {
			Boolean keep = (Boolean) keepAccounts;
			if (keep) {
				hsql += " and b.keepAccounts = ? ";
			} else {
				hsql += " and (b.keepAccounts = ? or  b.keepAccounts is null) ";
			}
			prop.add(keep);
		}
		// --------------------------------------------------------------
		Object isValid = map.get("isValid");
		if (isValid != null) {
			Boolean va = (Boolean) isValid;
			if (va) {
				hsql += " and b.isValid = ? ";
			} else {
				hsql += " and (b.isValid = ? or b.isValid  is null) ";
			}
			prop.add(va);
		}
		List list = this.find(hsql, prop.toArray());
		return list;
	}

	/**
	 * 根据参数查询订单明细的个数
	 * 
	 * @param isShowTransfer
	 *            是否显示转厂
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            结束日期
	 * @param customer
	 *            客户供应商
	 * @param billCode
	 *            订单号
	 * @param ptNo
	 *            BOM号
	 * @return
	 */
	public Integer findCustomOrderDetailCount(Boolean isShowTransfer,
			Date startdate, Date enddate, ScmCoc customer, String billCode,
			String ptNo) {
		List parames = new ArrayList();
		String hsql = "select Count(a)" + " from CustomOrderDetail a "
				+ " Where  a.company.id =? and a.customOrder.ifok=? ";

		parames.add(CommonUtils.getCompany().getId());
		parames.add(true);

		if (isShowTransfer == true) {
			hsql += " and a.ifzc=? ";
			parames.add(true);
		}

		if (startdate != null) {
			hsql += "  and  a.salesdate >=? ";
			parames.add(startdate);
		}

		if (enddate != null) {
			hsql += "  and  a.salesdate <=? ";
			parames.add(enddate);
		}

		if (customer != null) {
			hsql += " and a.customOrder.customer=? ";
			parames.add(customer);
		}

		if (billCode != null && !billCode.equals("")) {
			hsql += " and a.customOrder.billCode=? ";
			parames.add(billCode);
		}

		if (ptNo != null && !ptNo.equals("")) {
			hsql += " and a.materiel.ptNo=? ";
			parames.add(ptNo);
		}

		hsql += " Group by a.customOrder.billCode,a.materiel.ptNo,a.salesdate,a.materiel.factoryName,a.version "
				+ "  Order by count(a) desc";

		List list = find(hsql, parames.toArray());
		if (list.size() <= 0)
			return 0;
		else
			return ((Long) list.get(0)).intValue();
	}

	/**
	 * 根据参数查询订单明细
	 * 
	 * @param isShowTransfer
	 *            是否显示转厂
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            结束日期
	 * @param customer
	 *            客户供应商
	 * @param billCode
	 *            订单号
	 * @param ptNo
	 *            BOM号
	 * @return
	 */
	public List findSomeObjectInCustomOrderDetail(Boolean isShowTransfer,
			Date startdate, Date enddate, ScmCoc customer, String billCode,
			String ptNo) {
		List parames = new ArrayList();
		String hsql = "select a " + " from CustomOrderDetail a "
				+ " Where  a.company.id =? and a.customOrder.ifok=? ";

		parames.add(CommonUtils.getCompany().getId());
		parames.add(true);

		if (isShowTransfer == true) {
			hsql += " and a.ifzc=? ";
			parames.add(true);
		}

		if (startdate != null) {
			hsql += "  and  a.salesdate >=? ";
			parames.add(startdate);
		}

		if (enddate != null) {
			hsql += "  and  a.salesdate <=? ";
			parames.add(enddate);
		}

		if (customer != null) {
			hsql += " and a.customOrder.customer=? ";
			parames.add(customer);
		}

		if (billCode != null && !billCode.equals("")) {
			hsql += " and a.customOrder.billCode=? ";
			parames.add(billCode);
		}

		if (ptNo != null && !ptNo.equals("")) {
			hsql += " and a.materiel.ptNo=? ";
			parames.add(ptNo);
		}

		hsql += " order by a.customOrder.billCode,a.salesdate,a.materiel.ptNo,a.materiel.factoryName,a.version,a.customOrder.importcount";

		return find(hsql, parames.toArray());

	}

	/**
	 * 根据报关常用工厂物料，查找报关常用工厂BOM成品
	 * 
	 * @param materiel
	 *            报关常用工厂物料
	 * @return
	 */
	public List findMaterialBomMasterByMateriel(Materiel materiel) {
		return find(
				"select a from MaterialBomMaster a where a.company.id=? and a.materiel=? ",
				new Object[] { CommonUtils.getCompany().getId(), materiel });
	}

	/**
	 * 根据报关常用工厂BOM成品和版本号，查找报关常用工厂BOM版本
	 * 
	 * @param materiel
	 *            报关常用工厂物料
	 * @return
	 */
	public MaterialBomVersion findMaterialBomVersionByMaster(
			MaterialBomMaster bomMaster, Integer version) {
		List list = find(
				"select a from MaterialBomVersion a where a.company.id=? and a.master=? and a.version=? ",
				new Object[] { CommonUtils.getCompany().getId(), bomMaster,
						version });
		if (list.size() <= 0)
			return null;
		else
			return (MaterialBomVersion) list.get(0);
	}

	/**
	 * 根据报关常用工厂BOM成品和版本号，查找报关常用工厂BOM版本
	 * 
	 * @param materiel
	 *            报关常用工厂物料
	 * @return
	 */
	public List findMaterialBomDetailByPar(Materiel materiel, Integer version) {
		List parames = new ArrayList();
		String hsql = "select a from MaterialBomDetail a "
				+ " left join fetch a.version left join fetch a.version.master "
				+ " where a.company.id=? " + " and a.version.version=? "
				+ " and a.version.master.materiel=? ";
		parames.add(CommonUtils.getCompany().getId());
		parames.add(version);
		parames.add(materiel);
		return find(hsql, parames.toArray());

	}

}
