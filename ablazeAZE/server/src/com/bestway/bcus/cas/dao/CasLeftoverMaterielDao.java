/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BalanceInfo2;
import com.bestway.bcus.cas.entity.LeftoverMateriel;
import com.bestway.bcus.cas.entity.LeftoverMaterielBalanceInfo;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class CasLeftoverMaterielDao extends BaseDao {

	/**
	 * 获得边角料所有的数据
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
	 * @return 所有边角料数据
	 */
	public List<LeftoverMateriel> findLeftoverMateriel(int index, int length,
			String property, Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from LeftoverMateriel "
				+ " as a  where  a.company= ? ";

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
		return super.findListNoCache(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 获得边角料所对应的料件数据
	 * 
	 * @param leftoverMateriel
	 * @return 指定的边角料对应的料件数据
	 */
	public List<LeftoverMateriel> findLMateriel(
			LeftoverMateriel leftoverMateriel) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from LMateriel "
				+ " as a  where  a.company = ? and a.leftoverMateriel= ? ";

		parameters.add(CommonUtils.getCompany());
		parameters.add(leftoverMateriel);

		return super.findListNoCache(hsql, parameters.toArray());
	}

	/**
	 * 获得边角料所有的数据
	 * 
	 * @return 所有边角料数据
	 */
	public List<LeftoverMateriel> findLeftoverMateriel() {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a  from LeftoverMateriel "
				+ " as a  where  a.company= ? ";

		parameters.add(CommonUtils.getCompany());

		return super.findListNoCache(hsql, parameters.toArray());
	}

	/**
	 * 获得边角料数据来自物料主档
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
	 * @return 来自物料主档的边角料数据
	 */
	public List findMateriel(int index, int length, String property,
			Object value, boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.ptNo,a.factoryName.a.factorySpec  from Materiel a left join fetch a.scmCoi "
				+ "  where  a.company= ?  and a.scmCoi.coiProperty = ?  ";

		parameters.add(CommonUtils.getCompany());
		parameters.add(MaterielType.REMAIN_MATERIEL);

		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				parameters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				parameters.add(value);
			}
		}
		return super.findListNoCache(hsql, parameters.toArray(), index, length);
	}

	/**
	 * 保存边角料查询报表
	 * 
	 * @param obj
	 *            边角料平衡表
	 */
	public void saveLeftoverMaterielBalanceInfo(LeftoverMaterielBalanceInfo obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 获得边角料查询报表内容
	 * 
	 * @return 边角料查询报表
	 */
	public List<LeftoverMaterielBalanceInfo> findLeftoverMaterielBalanceInfo() {
		String hsql = "select a from LeftoverMaterielBalanceInfo as a where a.company.id = ? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除边角料平衡表内容
	 * 
	 * @param list
	 *            边角料平衡表
	 */
	public void deleteLeftoverMaterielBalanceInfo(
			List<LeftoverMaterielBalanceInfo> list) {
		this.deleteAll(list);
	}

	/**
	 * 批量删除边角料平衡表内容
	 */
	public void deleteLeftoverMaterielBalanceInfo() {
		this
				.batchUpdateOrDelete(
						"delete from LeftoverMaterielBalanceInfo m where m.company.id = ? ",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找电子手册的所有版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的电子手册的所有版本
	 */
	public List findEmsHeadH2kBomDetail(List<String> pkNoList) {
		String hsql = "select a.name,a.spec,a.unit.name,a.wear ,a.unitWear,a.emsHeadH2kVersion.id "
				+ "from EmsHeadH2kBom a left join a.unit where ( a.wear is not null and a.wear >= 0 ) ";
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and (  a.emsHeadH2kVersion.id = ?  ";
			} else {
				hsql += " or  a.emsHeadH2kVersion.id = ?   ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(pkNoList.get(i));// 料号
		}
		if (parameters.size() <= 0) {
			return new ArrayList();
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找纸质手册的所有版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 */
	public List findContractBomDetail(List<String> pkNoList) {
		String hsql = "select a.name,a.spec,a.unit.name,a.waste ,a.unitWaste,a.contractExg.id "
				+ "from ContractBom a left join a.unit where (2>1) ";// (
		// a.waste
		// is
		// not
		// null
		// and
		// a.waste
		// > 0 )
		// ";//edit
		// by
		// xxm
		// 2007-12-3
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and (  a.contractExg.id = ?  ";
			} else {
				hsql += " or  a.contractExg.id = ?   ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(pkNoList.get(i));// 料号
		}
		if (parameters.size() <= 0) {
			return new ArrayList();
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找纸质手册的所有版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 */
	public List findDzscEmsBomBill(List<String> pkNoList) {
		String hsql = "select a.name,a.spec,a.unit.name,a.ware ,a.unitWare,a.dzscEmsExgBill.id "
				+ "from DzscEmsBomBill a left join a.unit where (1=1) ";// (
		// a.waste
		// is
		// not
		// null
		// and
		// a.waste
		// > 0 )
		// ";//edit
		// by
		// xxm
		// 2007-12-3
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and (  a.dzscEmsExgBill.id = ?  ";
			} else {
				hsql += " or  a.dzscEmsExgBill.id = ?   ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(pkNoList.get(i));// 料号
		}
		if (parameters.size() <= 0) {
			return new ArrayList();
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找纸质手册的所有版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 */
	public List findDzscEmsBomBill(String exgName, String exgSpec,
			String exgUnitName, String cotractId) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.name,a.spec,a.unit.name,a.ware ,a.unitWare "
				+ "from DzscEmsBomBill a " + " left join a.dzscEmsExgBill exg "
				+ " left join exg.dzscEmsPorHead  h "
				+ " left join a.unit where  a.company.id = ? and h.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cotractId);

		if (exgName != null && !exgName.equals("")) {
			hsql += " and exg.name = ? ";
			parameters.add(exgName);
		} else {
			hsql += " and (exg.name is null or exg.name = '') ";
		}

		if (exgSpec != null && !exgSpec.equals("")) {
			hsql += " and exg.spec = ? ";
			parameters.add(exgSpec);
		} else {
			hsql += " and (exg.spec is null or exg.spec = '') ";
		}

		if (exgUnitName != null && !exgUnitName.equals("")) {
			hsql += " and exg.unit.name = ? ";
			parameters.add(exgUnitName);
		} else {
			hsql += " and (exg.unit.name is null or exg.unit.name = '') ";
		}

		return this.findNoCache(hsql, parameters.toArray());
	}

	public List findDzscEmsExgBill() {
		String hsql = "select exg.name,exg.spec,exg.unit.name,h.beginDate,h.id "
				+ "from DzscEmsExgBill exg "
				+ " left join exg.dzscEmsPorHead  h "
				+ " left join exg.unit where  h.declareState=? and exg.company.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(DzscState.EXECUTE);
		parameters.add(CommonUtils.getCompany().getId());
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找纸质手册的所有版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 */
	public List findDzscEmsPorHeads() {
		String hsql = "from DzscEmsPorHead a where a.declareState=? and a.company.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(DzscState.EXECUTE);
		parameters.add(CommonUtils.getCompany().getId());
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * BalanceInfo
	 * 
	 * @param obj
	 */
	public void saveBalanceInfo(BalanceInfo obj) {
		this.saveOrUpdate(obj);
	}

	public List<BalanceInfo> findBalanceInfo() {
		String hsql = "select a from BalanceInfo as a where a.company.id = ? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public void deleteBalanceInfo(List<BalanceInfo> list) {
		this.deleteAll(list);
	}

	public void deleteBalanceInfo() {
		this.batchUpdateOrDelete(
				"delete from BalanceInfo m where m.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public List<BalanceInfo2> findBalanceInfo2() {
		String hsql = "select a from BalanceInfo2 as a where a.company.id = ? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * BalanceInfo
	 * 
	 * @param obj
	 */
	public void saveBalanceInfo2(BalanceInfo2 obj) {
		this.saveOrUpdate(obj);
	}

	public List findBcsEmsExgBill() {
		String hsql = "select exg.name,exg.spec,exg.unit.name,h.beginDate,h.id "
				+ "from ContractExg exg "
				+ " left join exg.contract h "
				+ " left join exg.unit where  h.declareState=? and exg.company.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(CommonUtils.getCompany().getId());
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找正在执行的电子帐册
	 */
	public List findBcusEmsExgBill() {
		String hsql = "select exg.name,exg.spec,exg.unit.name,h.beginDate,h.id "
				+ "from EmsHeadH2kExg exg "
				+ " left join exg.emsHeadH2k h "
				+ " left join exg.unit where h.declareState=? and exg.company.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(CommonUtils.getCompany().getId());
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找正在执行的电子帐册
	 */
	public List findExingBcusEmsExgBill() {
		String hsql = "select a.name,a.spec,a.unit.name,a.id from EmsHeadH2kExg a "
				+ " left join a.emsHeadH2k h left join a.unit "
				+ " where h.declareState=? and h.historyState=? and h.company.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(DeclareState.PROCESS_EXE);
		parameters.add(false);
		parameters.add(CommonUtils.getCompany().getId());
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找纸质手册的所有版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 */
	public List findContractBom(String exgName, String exgSpec,
			String exgUnitName, String cotractId) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.name,a.spec,a.unit.name,a.waste ,a.unitWaste "
				+ "from ContractBom a " + " left join a.contractExg exg "
				+ " left join exg.contract  h "
				+ " left join a.unit where  a.company.id = ? and h.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cotractId);

		if (exgName != null && !exgName.equals("")) {
			hsql += " and exg.name = ? ";
			parameters.add(exgName);
		} else {
			hsql += " and (exg.name is null or exg.name = '') ";
		}

		if (exgSpec != null && !exgSpec.equals("")) {
			hsql += " and exg.spec = ? ";
			parameters.add(exgSpec);
		} else {
			hsql += " and (exg.spec is null or exg.spec = '') ";
		}

		if (exgUnitName != null && !exgUnitName.equals("")) {
			hsql += " and exg.unit.name = ? ";
			parameters.add(exgUnitName);
		} else {
			hsql += " and (exg.unit.name is null or exg.unit.name = '') ";
		}

		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找电子帐册的所有版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 */
	public List findBcusEmsBomBill(String exgName, String exgSpec,
			String exgUnitName, String cotractId) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.name,a.spec,a.unit.name,a.wear ,a.unitWear "
				+ "from EmsHeadH2kBom a "
				+ " left join a.emsHeadH2kVersion.emsHeadH2kExg exg "
				+ " left join exg.emsHeadH2k h "
				+ " left join a.unit where  a.company.id = ? and h.id = ? ";

		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(cotractId);

		if (exgName != null && !exgName.equals("")) {
			hsql += " and exg.name = ? ";
			parameters.add(exgName);
		} else {
			hsql += " and (exg.name is null or exg.name = '') ";
		}

		if (exgSpec != null && !exgSpec.equals("")) {
			hsql += " and exg.spec = ? ";
			parameters.add(exgSpec);
		} else {
			hsql += " and (exg.spec is null or exg.spec = '') ";
		}

		if (exgUnitName != null && !exgUnitName.equals("")) {
			hsql += " and exg.unit.name = ? ";
			parameters.add(exgUnitName);
		} else {
			hsql += " and (exg.unit.name is null or exg.unit.name = '') ";
		}

		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找电子帐册的最大版本
	 * 
	 * @param pkNoList
	 *            料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 */
	public List findBcusEmsBomBill(String exgId) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a.name,a.spec,a.unit.name,a.wear,a.unitWear "
				+ " from EmsHeadH2kBom a "
				+ " left join a.emsHeadH2kVersion.emsHeadH2kExg exg "
				+ " left join a.unit where exg.id = ? "
				+ " and a.emsHeadH2kVersion.version="
				+ " (select max(version) from EmsHeadH2kVersion where emsHeadH2kExg.id=?)";
		parameters.add(exgId);
		parameters.add(exgId);
		return this.findNoCache(hsql, parameters.toArray());
	}

}
