package com.bestway.owp.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpBillAndBillDetail;
import com.bestway.owp.entity.OwpBomDetail;
import com.bestway.owp.entity.OwpBomMaster;
import com.bestway.owp.entity.OwpBomVersion;
import com.bestway.owp.entity.OwpBillSendHead;
import com.bestway.owp.entity.TempOwpStockAll;
import com.bestway.owp.entity.TempReturnToBom;

/**
 * 外发加工Bom与统计分析dao类
 * 
 * @author hcl check by hcl 2010-10-07
 */
public class OwpAnalyDao extends BaseDao {
	/**
	 * 根据条件查找外发加工BOM成品资料
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是MaterialBomMaster型，外发加工BOM成品
	 */
	public List findMaterielBomMaster(int index, int length, String property,
			Object value, Boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from OwpBomMaster a  "
				+ " where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  a." + property + " like ?  ";
				parameters.add("%" + value + "%");// wss:2010.05.12value前加%
			} else {
				hql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return this.findPageList(hql, parameters.toArray(), index, length);
	}

	/**
	 * 根据外发加工BOM成品查找外发加工BOM版本
	 * 
	 * @param master
	 *            外发加工BOM成品
	 * @return List 是MaterialBomVersion型，外发加工BOM版本
	 */
	public List findMaterielBomVersion(OwpBomMaster master) {
		return this.find("select a from OwpBomVersion a "
				+ " where a.parent=? order by a.version ",
				new Object[] { master });
	}

	/**
	 * 根据外发加工BOM版本查找外发加工BOM里的料件
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomDetail型，外发加工BOM料件
	 */
	public List findMaterielBomDetail(OwpBomVersion version) {
		return this.find(
				"select a from OwpBomDetail a " + " where a.version=?",
				new Object[] { version });
	}

	/**
	 * 查找外发加工物料里的成品资料，但是不在外发加工BOM成品里查找
	 * 
	 * @return List 是Materiel型，外发加工物料
	 */
	public List findBomMaster() {
		return this
				.find(
						" select a from EnterpriseMaterial a left join fetch a.complex "
								+ " left join fetch a.calUnit  left join fetch a.ptUnit"
								+ " where a.ptNo not in ( select b.ptNo from "
								+ " OwpBomMaster as b where b.company.id=? )"
								+ " and a.company.id=? and  a.scmCoi.coiProperty in(?,?,?)",
						new Object[] { CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(), "0", "2", "1" });
	}

	/**
	 * 根据外发加工BOM成品、版本号查找外发加工BOM版本
	 * 
	 * @param master
	 *            外发加工BOM成品
	 * @param version
	 *            版本号
	 * @return List 是型，外发加工BOM版本
	 */
	public List findOwpBomVersionByVersion(OwpBomMaster master, Integer version) {
		return this.find("select a from OwpBomVersion a where  "
				+ "  a.parent=? and a.version=?  ", new Object[] { master,
				version });
	}

	/**
	 * 根据版本号查找物料里主档的资料，但是不在外发加工BOM料件里查找(以父级料号、父级版本过滤)
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 是物料主档型
	 */
	public List findNotInBomDetailMaterial(OwpBomVersion version) {
		return this
				.find(
						" select a from EnterpriseMaterial as a  left join fetch a.complex "
								+ "where a.ptNo not in ( select b.compentNo from "
								+ " OwpBomDetail as b where b.company.id=? "
								+ " and b.version=? )"
								+ " and a.company.id=? and a.scmCoi.coiProperty in(?,?) ",
						new Object[] { CommonUtils.getCompany().getId(),
								version, CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL,
								MaterielType.SEMI_FINISHED_PRODUCT });
	}

	/**
	 * 根据外发加工BOM父件料号查找子件
	 * 
	 * @param Master
	 * @return
	 */
	public List findOwpBomDetail(OwpBomMaster master) {
		return this.find(" select a from OwpBomDetail  a "
				+ "where a.company.id=? and a.version.parent=?  ",
				new Object[] { CommonUtils.getCompany().getId(), master });
	}

	/**
	 * 根据外发加工BOM版本查找子件
	 * 
	 * @param version
	 * @return
	 */
	public List findOwpBomDetail(OwpBomVersion version) {
		return this.find(" select a from OwpBomDetail  a "
				+ "where a.company.id=? and a.version=? ", new Object[] {
				CommonUtils.getCompany().getId(), version });
	}

	/**
	 * 查询BOM中的料件
	 * 
	 * @param materielType
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findMaterialBomMateriel(int index, int length, String property,
			Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select  a from OwpBomDetail as a"
				+ " where a.company.id=?";
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
		hsql += " order by a.compentNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findMaterialBomByDetail(OwpBomDetail materiel) {
		return this.find("select a from OwpBomDetail a "
				+ " where a.company.id = ? and a.compentNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), materiel.getCompentNo() });
	}

	/**
	 * 按条件查找发货申请表
	 * 
	 * @param conditions
	 * @return
	 */
	public List findOwpApplyOutGoods(List<Condition> conditions) {
		String hsql = "select a from OwpAppSendItem a where a.company.id=? ";
		// String
		// hql="select a.listNo,a.complex.name,a.hsName,a.hsSpec.name,a.hsUnit.name,a.qty, "
		// +
		// "(select sum(b.qty) from OwpBillSendItem b where b.head.appNo=? group by b.listNo),a.qty-count(b.qty) from OwpAppSendItem a where a.company.id=? ";

		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany().getId());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					hsql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					hsql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					hsql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					hsql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								hsql += "?";
							} else {
								hsql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						hsql += "? ";
						params.add(condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					hsql += condition.getEndBracket();
			}
		}
		System.out.println("hsql==" + hsql);
		return this.find(hsql, params.toArray());
	}

	/**
	 * 按条件查找发货单
	 * 
	 * @param conditions
	 * @return
	 */
	public List findOwpOutGoods(List<Condition> conditions) {
		String hsql = "select a from OwpBillSendItem a where a.company.id=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany().getId());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					hsql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					hsql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					hsql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					hsql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								hsql += "?";
							} else {
								hsql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						hsql += "? ";
						params.add(condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					hsql += condition.getEndBracket();
			}
		}
		hsql += "order by a.head.collectDate,listNo ";
		System.out.println("hsql==" + hsql);
		return this.find(hsql, params.toArray());
	}

	/**
	 * 按条件查找收货申请表
	 * 
	 * @param conditions
	 * @return
	 */
	public List findOwpApplyInGoods(List<Condition> conditions) {
		String hsql = "select a from OwpAppRecvItem a where a.company.id=? ";
		// String
		// hql="select a.listNo,a.complex.name,a.hsName,a.hsSpec.name,a.hsUnit.name,a.qty, "
		// +
		// "(select sum(b.qty) from OwpBillSendItem b where b.head.appNo=? group by b.listNo),a.qty-count(b.qty) from OwpAppSendItem a where a.company.id=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany().getId());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					hsql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					hsql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					hsql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					hsql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								hsql += "?";
							} else {
								hsql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						hsql += "? ";
						params.add(condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					hsql += condition.getEndBracket();
			}
		}
		System.out.println("hsql==" + hsql);
		return this.find(hsql, params.toArray());
	}

	/**
	 * 按条件查找收货单
	 * 
	 * @param conditions
	 * @return
	 */
	public List findOwpInGoods(List<Condition> conditions) {
		String hsql = "select a from OwpBillRecvItem a where a.company.id=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany().getId());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					hsql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					hsql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					hsql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					hsql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								hsql += "?";
							} else {
								hsql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						hsql += "? ";
						params.add(condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					hsql += condition.getEndBracket();
			}
		}
		hsql += " order by a.head.collectDate,listNo ";
		System.out.println("hsql==" + hsql);
		return this.find(hsql, params.toArray());
	}

	/**
	 * 查找所有申请表 hcl
	 * 
	 */
	public List findAllApplyNo() {
		return this.find("select a from OwpAppHead a where a.declareState='3'");
	}

	/**
	 * 
	 * 根据所传入条件及表明，查找所需数据
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
	 * 根据单据查找BOM的明细表
	 * 
	 * @param bill
	 * @return
	 */
	public List getBomByBillDetail(BillDetail bill) {
		String hsql = "select a from OwpBomDetail a where a.version.parent.ptNo=? ";
		return this.find(hsql, bill.getPtPart());
	}

	/**
	 * 按照中间表的单据ID查找单据中心的单据
	 * 
	 * @param rerationList
	 * @return
	 */
	public List findBilldetail(List rerationList) {

		List result = new ArrayList();
		for (int i = 0; i < rerationList.size(); i++) {
			List<Object> params = new ArrayList<Object>();
			OwpBillAndBillDetail bill = (OwpBillAndBillDetail) rerationList
					.get(i);
			String Type = String.valueOf(bill.getProduceType());
			String tableName = BillUtil
					.getBillDetailTableNameByMaterielType(Type);
			String hsql = "select a from " + tableName + " a where a.id=?";
			params.add(bill.getBill());
			result.addAll(this.find(hsql, params.toArray()));

		}
		return result;
	}

	/**
	 * 查找所有的bom
	 * 
	 * @return
	 */
	public List findAllBomDetail() {
		String hsql = "select a from OwpBomDetail a  left outer join fetch  a.version  left outer join fetch  a.version.parent ";
		return this.find(hsql);
	}

	/**
	 * 按照临时返回表查找对应关系(返回折料)
	 * 
	 * @param list
	 * @return
	 */
	public List findFactoryAndFactualCustomsRalationMaterial(List list) {
		String hsql = "select a from FactoryAndFactualCustomsRalation as a  "
				+ " where a.company =?  ";
		List params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		for (int i = 0; i < list.size(); i++) {
			TempReturnToBom temp = (TempReturnToBom) list.get(i);
			if (i == 0)
				hsql += " and a.materiel.ptNo in(? ";
			else
				hsql += ",?";
			if (i == list.size() - 1)
				hsql += ")";
			params.add(temp.getPtNo());
		}
		return this.find(hsql, params.toArray());
	}

	/**
	 * 按照临时返回表查找对应关系(库存汇总)
	 * 
	 * @param list
	 * @return
	 */
	public List findFactoryAndFactualCustomsRalationMaterialOfStockAll(List list) {
		String hsql = "select a from FactoryAndFactualCustomsRalation as a  "
				+ " where a.company =?  ";
		List params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		for (int i = 0; i < list.size(); i++) {
			TempOwpStockAll temp = (TempOwpStockAll) list.get(i);
			if (i == 0)
				hsql += " and a.materiel.ptNo in(? ";
			else
				hsql += ",?";
			if (i == list.size() - 1)
				hsql += ")";
			params.add(temp.getPtNo());
		}
		return this.find(hsql, params.toArray());
	}

	/**
	 * 根据对应关系查找外发加工申请单
	 * 
	 * @param f
	 * @return
	 */
	public List findTempReturnToBomResultHsInfo(
			FactoryAndFactualCustomsRalation f) {
		String hsql = "select a from  OwpAppSendItem a where a.complex=? and a.hsName=? and a.hsSpec=? "
				+ "and a.hsUnit=?";
		List params = new ArrayList<Object>();
		params.add(f.getStatCusNameRelationHsn().getComplex());
		params.add(f.getStatCusNameRelationHsn().getCusName());
		params.add(f.getStatCusNameRelationHsn().getCusSpec());
		params.add(f.getStatCusNameRelationHsn().getCusUnit());
		return this.find(hsql, params.toArray());
	}

	/**
	 * 按照单据体查找对应关系
	 * 
	 * @param billList
	 * @return
	 */
	public List findFactoryAndFactualCustomsRalationFinished(List billList) {
		String hsql = "select a from FactoryAndFactualCustomsRalation as a  "
				+ " where a.company =?  ";
		List params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		for (int i = 0; i < billList.size(); i++) {
			BillDetail bill = (BillDetail) billList.get(i);
			if (i == 0)
				hsql += " and a.materiel.ptNo in(? ";
			else
				hsql += ",?";
			if (i == billList.size() - 1)
				hsql += ")";
			params.add(bill.getPtPart());
		}
		return this.find(hsql, params.toArray());
	}

	/**
	 * 根据工厂级料号查找对应关系
	 * 
	 * @param compentNo
	 * @return
	 */
	public List findFactoryAndFactualCustomsRalationMaterial(String compentNo) {
		String hsql = "select a from FactoryAndFactualCustomsRalation as a  "
				+ " where a.company =? and a.materiel.ptNo=? ";
		List params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		params.add(compentNo);
		return this.find(hsql, params.toArray());
	}

}
