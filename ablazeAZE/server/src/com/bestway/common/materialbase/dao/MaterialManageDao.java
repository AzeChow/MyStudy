package com.bestway.common.materialbase.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.invoice.entity.CasCondition;
import com.bestway.bcus.cas.invoice.entity.CasConvert;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.ConsignQueryCondition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.FixType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.CustomsUser;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.EnterpriseBomMaster;
import com.bestway.common.materialbase.entity.EnterpriseBomVersion;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ParaSet;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCocControl;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.common.materialbase.entity.UnitCollate;
import com.bestway.common.materialbase.entity.UnitWaste;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 工厂通用代码（方法）checked by lxr 2008-10-25
 * 
 * @author lxr
 */
@SuppressWarnings("unchecked")
public class MaterialManageDao extends BaseDao {

	/**
	 * 显示所有仓库设置数据
	 * 
	 * @return
	 */
	public List findWareSet() {
		return this
				.find("select a from WareSet a where a.company.id= ? order by a.code ",
						CommonUtils.getCompany().getId());
	}

	/**
	 * 查找客户供应商栏位控制参数
	 * 
	 * @param request
	 * @return
	 */
	public List findScmCocControl() {
		return this.find(
				"select a from ScmCocControl a where a.company.id= ?  ",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 显示所有仓库设置数据
	 * 
	 * @param company
	 * @return
	 */
	public List findWareSet(Company company) {
		return this
				.find("select a from WareSet a where a.company.id= ? order by a.code ",
						company.getId());
	}

	/**
	 * 显示所有计量单位数据
	 * 
	 * @return
	 */
	public List<CalUnit> findCalUnit() {
		return this.find("select a from CalUnit a where a.company.id= ?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 显示所有计量单位数据
	 * 
	 * @param company
	 * @return
	 */
	public List findCalUnit(Company company) {
		return this.find("select a from CalUnit a where a.company.id= ?",
				company.getId());
	}

	/**
	 * 显示所有币别数据
	 * 
	 * @return
	 */
	public List findCurrCode() {
		return this.find("select a from CurrCode a ");
	}

	/**
	 * 显示所有海关商品编码数据
	 * 
	 * @return
	 */
	public List findCustomsComplex() {
		return this.find("select a from CustomsComplex a");
	}

	/**
	 * 显示所有税制代码数据
	 * 
	 * @return
	 */
	public List findTaxaTion() {
		return this
				.find("select a from ShareCode a where a.tag= ? and a.company.id = ?",
						new Object[] { "taxation",
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示汇率所有数据
	 * 
	 * @return
	 */
	public List findCurrRate() {
		return this.find("select a from CurrRate a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找工厂司机信息
	 * 
	 * @return
	 */
	public List findMotorCode() {
		return this.find("select a from MotorCode a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示司机设置所有资料
	 * 
	 * @param index
	 * @param length
	 * @return
	 */
	public List findMotorCode(int index, int length) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from MotorCode a where a.company.id = ?";
		paramters.add(CommonUtils.getCompany().getId());
		hsql += " order by a.code asc ";

		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 显示报关员设置所有资料
	 * 
	 * @return
	 */
	public List findCustomsUser() {
		return this.find("select a from CustomsUser a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示物流通用代码所有资料
	 * 
	 * @return
	 */
	public List findParaSet() {
		return this.find("select a from ParaSet a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示物事业部所有资料
	 * 
	 * @return
	 */
	public List findProjectDept() {
		return this.find("select a from ProjectDept a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示计量单位资料所有资料
	 * 
	 * @return
	 */
	public List findUnitCollate() {
		return this.find("select a from UnitCollate a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示报关员电话号码
	 * 
	 * @param name
	 * @return
	 */
	public String findCustomsUserTel(String name) {
		if (name == null) {
			return null;
		}
		List list = this
				.find("select a.tel from CustomsUser a where a.company.id = ? and a.name = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								name.trim() });
		if (list != null && list.get(0) != null) {
			return String.valueOf(list.get(0));
		}
		return null;
	}

	/**
	 * 返回司机资料设置
	 * 
	 * @param homeCard
	 * @return MotorCode 司机资料
	 */

	public MotorCode findMotorCodeByCode(String homeCard) {
		List list = this
				.find("select a from MotorCode a where a.company.id = ? and a.homeCard = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								homeCard });
		if (list != null && list.size() > 0) {
			return (MotorCode) list.get(0);
		}
		return null;
	}

	/**
	 * 返回司机资料设置
	 * 
	 * @param complex
	 * @return MotorCode 司机资料
	 */

	public MotorCode findMotorCodeComplex(String complex) {
		List list = this
				.find("select a from MotorCode a where a.company.id = ? and a.complex = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								complex });
		if (list != null && list.size() > 0) {
			return (MotorCode) list.get(0);
		}
		return null;
	}

	/**
	 * 返回司机资料设置
	 * 
	 * @return complex 海关编码
	 */

	public List findMotorCodeComplex() {
		return this.find(
				"select a.complex from MotorCode a where a.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找本地币制所对应的汇率设置
	 * 
	 * @param currCode
	 *            本地币值代码
	 * @return 获取汇率
	 */
	public List findCurrRate(String currCode) {
		return this
				.find("select a from CurrRate a where a.company.id = ? and a.curr.code = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								currCode });
	}

	/**
	 * 根据两种比别，获取汇率
	 * 
	 * @param curr
	 *            本地币值
	 * @param curr1
	 *            外地币值
	 * @param createDate
	 *            兑取日期
	 * @return 获取汇率
	 */
	public List findExchangeRate(Curr curr, Curr curr1, Date createDate) {
		return this.find(
				"select a from CurrRate a where a.company.id= ? "
						+ " and a.curr.code=? and a.curr1.code=?"
						+ " and a.createDate<=?  order by a.createDate desc ",
				new Object[] { CommonUtils.getCompany().getId(),
						curr.getCode(), curr1.getCode(),
						CommonUtils.getEndDate(createDate) });
	}

	/**
	 * 根据两种比别，获取汇率
	 * 
	 * @param curr
	 *            本地币值
	 * @param curr1
	 *            外地币值
	 * @param createDate
	 *            兑取日期
	 * @return 获取汇率
	 */
	public Date findExchangeRateData(Curr curr, Curr curr1, Date createDate) {
		List list = this
				.find("select a from CurrRate a where a.company.id = ? "
						+ " and a.curr.code=? and a.curr1.code=?  and a.createDate >?"
						+ "order by a.createDate  ",
						new Object[] { CommonUtils.getCompany().getId(),
								curr.getCode(), curr1.getCode(), createDate });
		if (list.size() != 0) {
			CurrRate cr = (CurrRate) list.get(0);
			return cr.getCreateDate();
		} else {
			return new Date();
		}
	}

	/**
	 * 根据两种比别，获取汇率
	 * 
	 * @param curr1
	 *            外地币值
	 * @return 获取汇率
	 */
	public CurrRate findCurrRateByCurr1(Curr curr1) {
		if (curr1 == null) {
			return null;
		}
		List list = this
				.find("select a from CurrRate a where a.company.id = ? and a.curr1.code = ? and a.curr.code = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								curr1.getCode(), "502" });
		if (list != null && list.size() > 0) {
			return (CurrRate) list.get(0);
		}
		return null;
	}

	/**
	 * 显示所有报关常用物料主档数据
	 * 
	 * @return
	 */
	public List<Materiel> findMateriel() {
		return this.find("select a from Materiel a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 按物料名称查询
	 * 
	 * @return
	 */
	public List findMaterielByCode(String s) {

		return this
				.find("select a from Materiel a where a.company.id= ? and a.complex.code = ?",
						new Object[] { CommonUtils.getCompany().getId(), s });
	}

	/**
	 * 显示所有工厂物料主档资料数据
	 * 
	 * @param ptNo
	 *            料号
	 * @return EnterpriseMaterial 工厂物料主档
	 */
	public EnterpriseMaterial findEnterpriseMateriel(String ptNo) {
		List list = this.find("select a from EnterpriseMaterial a "
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " where a.ptNo = ? and a.company.id = ?", new Object[] {
				ptNo, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (EnterpriseMaterial) list.get(0);
		}
		return null;
	}

	/**
	 * 显示所有报关常用物料主档数据
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public Materiel findMateriel(String ptNo) {
		List list = this.find("select a from Materiel a "
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " where a.ptNo = ? and a.company.id = ?", new Object[] {
				ptNo, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (Materiel) list.get(0);
		}
		return null;
	}

	/**
	 * 显示所有报关常用物料主档数据
	 * 
	 * @param company
	 * @return
	 */
	public List findMateriel(Company company) {
		return this.find("select a from Materiel a where a.company.id= ?",
				new Object[] { company.getId() });
	}

	/**
	 * 物料主档显示所有数据来自物料主档的类别List
	 * 
	 * @param materielTypeList
	 * @return
	 */
	public List findMateriel(List materielTypeList) {
		if (materielTypeList == null || materielTypeList.size() <= 0) {
			return null;
		}
		String hsql = "select m from Materiel m"
				+ " left join fetch m.complex " + " left join fetch m.scmCoc "
				+ " where m.company.id= ? ";
		List parameters = new ArrayList(materielTypeList);
		for (int i = 0; i < materielTypeList.size(); i++) {
			if (i == 0) {
				hsql += " and ( m.scmCoi.coiProperty = ? ";
			} else {
				hsql += " or m.scmCoi.coiProperty = ? ";
			}
			if (i == materielTypeList.size() - 1) {
				hsql += " ) ";
			}
		}
		parameters.add(0, CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 物料主档显示所有数据来自物料主档的类别List
	 * 
	 * @param materielType
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findMateriel(String materielType, int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from Materiel as a"
				+ " left join fetch a.complex " + " left join fetch a.scmCoc"
				+ " left join fetch a.calUnit"
				+ " where a.scmCoi.code=? and a.company.id=? ";
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找物料主档来自类别-->进出口报关申请单
	 * 
	 * @param materielType
	 *            物料类别
	 * @return
	 */
	public List<Materiel> findMaterielByType(String materielType) {
		List<Materiel> list = null;
		list = this
				.find("select b from Materiel b left outer join fetch b.calUnit"
						+ " left join fetch b.complex "
						+ " where ( b.scmCoi.coiProperty=? or b.scmCoi.coiProperty=? ) "
						+ " and b.company.id=?", new Object[] { materielType,
						MaterielType.SEMI_FINISHED_PRODUCT,
						CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找物料主档来自类别-->进出口报关申请单
	 * 
	 * @param materielType
	 *            物料类别
	 * @return
	 */
	public List findMaterielByMaterielType(String materielType) {
		List list = null;
		list = this
				.find("select b from Materiel b "
						+ " left join fetch b.complex "
						+ " where ( b.scmCoi.coiProperty=? ) "
						+ " and b.company.id=?", new Object[] { materielType,
						CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找报关常用物料主档
	 * 
	 * @param scmCoiCode
	 *            类别代码
	 * @return
	 */
	public List findMaterielByScmCoi(String scmCoiCode) {
		List list = null;
		list = this.find("select b from Materiel b "
				+ " left join fetch b.complex "
				+ " where b.scmCoi.code=? and b.company.id=?", new Object[] {
				scmCoiCode, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找工厂物料主档来自类别
	 * 
	 * @param materielType
	 *            物料类别
	 * @return
	 */
	public List findEMaterielByMaterielType(String materielType) {
		List list = null;
		list = this
				.find("select b from EnterpriseMaterial b "
						+ " left join fetch b.complex "
						+ " where ( b.scmCoi.coiProperty=? ) "
						+ " and b.company.id=?", new Object[] { materielType,
						CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找工厂物料主档编码来自类别
	 * 
	 * @param materielType
	 *            物料类别
	 * @return
	 */
	public List findEMaterielPtNoByMaterielType(String materielType) {
		List list = null;
		list = this
				.find("select b.ptNo from EnterpriseMaterial b "
						+ " where b.scmCoi.coiProperty=? and b.company.id=?",
						new Object[] { materielType,
								CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找工厂物料主档编码来自类别
	 * 
	 * @param materielType
	 *            物料类别
	 * @return
	 */
	public List findEMaterielPtNo() {
		List list = null;
		list = this.find("select b.ptNo from EnterpriseMaterial b "
				+ " where b.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
		return list;
	}

	/**
	 * 查找仓库设置是否重复
	 * 
	 * @param code
	 *            代码
	 * @return
	 */
	public WareSet findWarerByCode(String code) {
		List result = this
				.find("select a from WareSet a where a.code = ? and a.company.id = ?",
						new Object[] { code, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (WareSet) result.get(0);
		else
			return null;
	}

	/**
	 * 查找仓库设置是否重复
	 * 
	 * @param name
	 *            名称
	 * @return
	 */
	public WareSet findWarerByName(String name) {
		List result = this.find(
				"select a from WareSet a where a.name = ? and a.company.id= ?",
				new Object[] { name, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (WareSet) result.get(0);
		else
			return null;
	}

	/**
	 * 查找计量单位是否重复
	 * 
	 * @param code
	 *            代码
	 * @return
	 */
	public CalUnit findUnitByCode(String code) {
		List result = this.find(
				"select a from CalUnit a where a.code = ? and a.company.id= ?",
				new Object[] { code, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (CalUnit) result.get(0);
		else
			return null;
	}

	/**
	 * 计量单位查找是否重复
	 * 
	 * @return
	 */
	public List<Unit> findUnit() {
		return this.find("select a from Unit a");
	}

	/**
	 * 计量单位查找是否重复
	 * 
	 * @param name
	 * @return
	 */
	public CalUnit findUnitByName(String name) {
		List result = this.find(
				"select a from CalUnit a where a.name = ? and a.company.id= ?",
				new Object[] { name, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (CalUnit) result.get(0);
		else
			return null;
	}

	/**
	 * 币别查找是否重复
	 * 
	 * @param code
	 * @return
	 */
	public CurrCode findCurrByCode(String code) {
		List result = this.find(
				"select a from CurrCode a where a.code = ? and a.company.id=?",
				new Object[] { code, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (CurrCode) result.get(0);
		else
			return null;
	}

	/**
	 * 币别查找是否重复
	 * 
	 * @param name
	 * @return
	 */
	public CurrCode findCurrByName(String name) {
		List result = this
				.find("select a from CurrCode a where a.name = ? and a.company.id= ?",
						new Object[] { name, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (CurrCode) result.get(0);
		else
			return null;
	}

	/**
	 * 税制代码查找是否重复
	 * 
	 * @param code
	 * @return
	 */
	public ShareCode findTaxaTionByCode(String code) {
		List result = this
				.find("select a from ShareCode a where a.tag = ? and a.code=? and a.company.id= ?",
						new Object[] { "taxation", code,
								CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ShareCode) result.get(0);
		else
			return null;
	}

	/**
	 * 税制代码查找是否重复
	 * 
	 * @param name
	 * @return
	 */
	public ShareCode findTaxaTionByName(String name) {
		List result = this
				.find("select a from ShareCode a where a.tag = ? and a.name=? and a.company.id= ?",
						new Object[] { "taxation", name,
								CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ShareCode) result.get(0);
		else
			return null;
	}

	/**
	 * 物料主档查找是否重复
	 * 
	 * @param ptNo
	 * @return
	 */
	public Materiel findMaterielByPtNo(String ptNo) {
		List result = this.find(
				"select a from Materiel a where a.ptNo=? and a.company.id= ?",
				new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (Materiel) result.get(0);
		else
			return null;
	}

	/**
	 * 物料主档查找是否重复
	 * 
	 * @param ptNo
	 * @return
	 */
	public List findMaterielPtNoByRange(String beginPtNo, String endPtNo) {
		List result = this
				.find("select a.ptNo from Materiel a where a.ptNo>=? and a.ptNo<=? and a.company.id= ?",
						new Object[] { beginPtNo, endPtNo,
								CommonUtils.getCompany().getId() });
		return result;
	}

	/**
	 * 仓库设置保存
	 * 
	 * @param ware
	 * @throws DataAccessException
	 */
	public void saveWare(WareSet ware) throws DataAccessException {
		this.saveOrUpdate(ware);
	}

	/**
	 * 保存客户供应商栏位控制参数
	 * 
	 * @param request
	 * @param scmCocControl
	 */
	public void saveScmCocControl(ScmCocControl scmCocControl) {
		this.saveOrUpdate(scmCocControl);
	}

	/**
	 * 计量单位保存
	 * 
	 * @param unit
	 * @throws DataAccessException
	 */
	public void saveUnit(CalUnit unit) throws DataAccessException {
		this.saveOrUpdate(unit);
	}

	/**
	 * 计量单位保存
	 * 
	 * @param unit
	 * @throws DataAccessException
	 */
	public void saveUnitCollate(UnitCollate unit) throws DataAccessException {
		this.saveOrUpdate(unit);
	}

	/**
	 * 报关员保存
	 * 
	 * @param obj
	 * @throws DataAccessException
	 */
	public void saveCustomsUser(CustomsUser obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	/**
	 * 事业部保存
	 * 
	 * @param obj
	 * @throws DataAccessException
	 */
	public void saveProjectDept(ProjectDept obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	/**
	 * 币别保存
	 * 
	 * @param curr
	 * @throws DataAccessException
	 */
	public void saveCurr(CurrCode curr) throws DataAccessException {
		this.saveOrUpdate(curr);
	}

	/**
	 * 汇率保存
	 * 
	 * @param currRate
	 * @throws DataAccessException
	 */
	public void saveCurrRate(CurrRate currRate) throws DataAccessException {
		this.saveOrUpdate(currRate);
	}

	/**
	 * 保存司机资料设置
	 * 
	 * @param motor
	 */
	public void saveMotorCode(MotorCode motor) {
		this.saveOrUpdate(motor);
	}

	/**
	 * 保存物流通用代码
	 * 
	 * @param obj
	 */
	public void saveParaSet(ParaSet obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 汇率删除
	 * 
	 * @param currRate
	 */
	public void deleteCurrRate(CurrRate currRate) {
		this.delete(currRate);
	}

	/**
	 * 公共代码保存
	 * 
	 * @param share
	 * @throws DataAccessException
	 */
	public void saveShare(ShareCode share) throws DataAccessException {
		this.saveOrUpdate(share);
	}

	/**
	 * 仓库设置删除
	 * 
	 * @param ware
	 */
	public void deleteWare(WareSet ware) {
		this.delete(ware);
	}

	/**
	 * 司机资料设置删除
	 * 
	 * @param motor
	 */
	public void deleteMotorCode(MotorCode motor) {
		this.delete(motor);
	}

	/**
	 * 报关员设置删除
	 * 
	 * @param motor
	 */
	public void deleteCustomsUser(CustomsUser motor) {
		this.delete(motor);
	}

	/**
	 * 物流通用代码删除
	 * 
	 * @param obj
	 */
	public void deleteParaSet(ParaSet obj) {
		this.delete(obj);
	}

	/**
	 * 事业部删除
	 * 
	 * @param obj
	 */
	public void deleteProjectDept(ProjectDept obj) {
		this.delete(obj);
	}

	/**
	 * 计量单位删除
	 * 
	 * @param obj
	 */
	public void deleteUnitCollate(UnitCollate obj) {
		this.delete(obj);
	}

	/**
	 * 计量单位删除
	 * 
	 * @param unit
	 */
	public void deleteUnit(CalUnit unit) {
		this.delete(unit);
	}

	/**
	 * 币别删除
	 * 
	 * @param curr
	 */
	public void deleteCurr(CurrCode curr) {
		this.delete(curr);
	}

	/**
	 * 公共代码删除
	 * 
	 * @param share
	 */
	public void deleteShare(ShareCode share) {
		this.delete(share);
	}

	/**
	 * 物料主档删除
	 * 
	 * @param materiel
	 */
	public void deleteMateriel(Materiel materiel) {
		this.delete(materiel);
	}

	public boolean findIsUsingMateriel(String tableName, Materiel materiel) {
		List list = this.find("select count(a.id) from " + tableName
				+ "  a where a.materiel = ? and a.company.id= ?", new Object[] {
				materiel, CommonUtils.getCompany().getId() });
		int count = Integer.parseInt(list.get(0).toString());
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回所有国家资料
	 * 
	 * @return
	 */
	public List findSysAreas() {
		return this.find("select a from SysArea a where a.company.id= ?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 返回所有国家资料
	 * 
	 * @param id
	 * @return
	 */
	public SysArea findSysAreaById(String id) {
		List result = this.find(
				"select a from SysArea a where a.id = ? and a.company.id= ?",
				new Object[] { id, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (SysArea) result.get(0);
		else
			return null;
	}

	/**
	 * 返回所有国家资料
	 * 
	 * @param code
	 * @return
	 */
	public SysArea findSysAreaByCode(String code) {
		List result = this.find(
				"select a from SysArea a where a.code = ? and a.company.id=?",
				new Object[] { code, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (SysArea) result.get(0);
		else
			return null;
	}

	/**
	 * 保存返回所有国家资料
	 * 
	 * @param area
	 */
	public void saveSysArea(SysArea area) {
		this.saveOrUpdate(area);
	}

	/**
	 * 国家资料删除
	 * 
	 * @param area
	 */
	public void deleteSysArea(SysArea area) {
		this.delete(area);
	}

	/**
	 * 返回所有物料类别
	 * 
	 * @return
	 */
	public List findScmCois() {
		return this
				.find("select a from ScmCoi a where a.company.id= ? order by a.coiProperty",
						CommonUtils.getCompany().getId());
	}

	/**
	 * 
	 * 返回所有物料类别
	 * 
	 * @param company
	 * @return
	 */
	public List findScmCois(Company company) {
		return this
				.find("select a from ScmCoi a where a.company.id= ? order by a.coiProperty",
						company.getId());
	}

	/**
	 * 
	 * 返回所有物料类别
	 * 
	 * @param id
	 * @return
	 */
	public ScmCoi findScmCoiById(String id) {
		List result = this
				.find("select a from ScmCoi a where a.id = ? and a.company.id =?  order by a.coiProperty",
						new Object[] { id, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ScmCoi) result.get(0);
		else
			return null;
	}

	/**
	 * 
	 * 返回所有物料类别
	 * 
	 * @param code
	 * @return
	 */
	public ScmCoi findScmCoiByCode(String code) {
		List result = this
				.find("select a from ScmCoi a where a.code = ? and a.company.id=? order by a.coiProperty",
						new Object[] { code, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ScmCoi) result.get(0);
		else
			return null;
	}

	/**
	 * 
	 * 返回所有计量单位
	 * 
	 * @param unitNode
	 * @return
	 */
	public CalUnit findCalUnitByUnitName(String unitNode) {
		CalUnit calUnit = null;
		List list = this
				.find("select a from CalUnit a where a.company.id= ? and a.unit.name=?",
						new Object[] { CommonUtils.getCompany().getId(),
								unitNode });
		if (list.size() > 0) {
			calUnit = (CalUnit) list.get(0);
		}
		return calUnit;
	}

	/**
	 * 返回所有计量单位
	 * 
	 * @param materelName
	 * @param customsName
	 * @return
	 */
	public List findCalUnitByUnitName(String materelName, String customsName) {
		return this
				.find("select a from CalUnit a where a.name=? and a.unit.name=? and a.company.id=?",
						new Object[] { materelName, customsName,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回所有计量单位
	 * 
	 * @param materelName
	 * @param customsName
	 * @return
	 */
	public List findNameOfUnitCollate() {
		return this
				.find("select a.unitName,a.unitName1,unitRate from UnitCollate a where  a.company.id=?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回所有物料类别
	 * 
	 * @param type
	 * @return
	 */
	public ScmCoi findScmCoiByType(String type) {
		List result = this
				.find("select a from ScmCoi a where a.coiProperty = ? and a.company.id=? order by a.coiProperty",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ScmCoi) result.get(0);
		else
			return null;
	}

	/**
	 * 保存物料类别
	 * 
	 * @param coi
	 */
	public void saveScmCoi(ScmCoi coi) {
		this.saveOrUpdate(coi);
	}

	/**
	 * 删除物料类别
	 * 
	 * @param coi
	 */
	public void deleteScmCoi(ScmCoi coi) {
		this.delete(coi);
	}

	/**
	 * 返回所有客户/供应商/合作伙伴资料
	 * 
	 * @return
	 */
	public List findScmCocs() {
		return this
				.find("select a from ScmCoc a left join fetch a.brief left join a.srcDistrict where a.company.id= ? "
						+ " order by a.code ", CommonUtils.getCompany().getId());
	}

	/**
	 * 返回所有供应商
	 * 
	 * @return
	 * @author 石小凯
	 */
	public List findScmCocsfacturerByXiaok() {
		return this
				.find("select a from ScmCoc a where a.isManufacturer =? and a.isTransferFactoryIn=? and a.company.id= ? "
						+ " order by a.code ", new Object[] {
						new Boolean(true), new Boolean(true),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回所有客户
	 * 
	 * @return
	 * @author 石小凯
	 */
	public List findScmCocscustomerByXiaok() {
		return this
				.find("select a from ScmCoc a where a.isCustomer=? and a.isTransferFactoryOut=? and a.company.id= ? "
						+ " order by a.code ", new Object[] {
						new Boolean(true), new Boolean(true),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回所有客户/供应商/合作伙伴资料
	 * 
	 * @param company
	 * @return
	 */
	public List findScmCocs(Company company) {
		return this.find("select a from ScmCoc a where a.company.id= ?",
				company.getId());
	}

	/**
	 * 查询非转厂供应商
	 * 
	 * @return
	 */
	public List findScmManufacturer() {
		return this
				.find("select a from ScmCoc a where a.isManufacturer =? and a.isTransferFactoryIn = ? "
						+ " and a.company.id = ?", new Object[] {
						new Boolean(true), new Boolean(false),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询非转厂客户
	 * 
	 * @return
	 */
	public List findScmManucustomer() {
		return this.find(
				"select a from ScmCoc a where a.isCustomer =? and a.isTransferFactoryOut =?  "
						+ " and a.company.id = ?", new Object[] {
						new Boolean(true), new Boolean(false),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询转厂进口供应商
	 * 
	 * @return
	 */
	public List findScmManuFactoryIn() {
		return this
				.find("select a from ScmCoc a where a.isManufacturer =? and a.isTransferFactoryIn = ? "
						+ " and a.company.id = ? ", new Object[] {
						new Boolean(true), new Boolean(true),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询转厂出口客户
	 * 
	 * @return
	 */
	public List findScmManuFactoryOut() {
		return this.find(
				"select a from ScmCoc a where a.isCustomer =?  and a.isTransferFactoryOut =? "
						+ " and a.company.id = ?", new Object[] {
						new Boolean(true), new Boolean(true),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询客户供应商
	 * 
	 * @param id
	 * @return
	 */
	public ScmCoc findScmCocById(String id) {
		List result = this.find(
				"select a from ScmCoc a where a.id = ? and a.company.id=?",
				new Object[] { id, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ScmCoc) result.get(0);
		else
			return null;
	}

	/**
	 * 查询客户供应商
	 * 
	 * @param code
	 * @return
	 */
	public ScmCoc findScmCocByCode(String code) {
		List result = this.find(
				"select a from ScmCoc a where a.code = ? and a.company.id=?",
				new Object[] { code, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ScmCoc) result.get(0);
		else
			return null;
	}

	/**
	 * 查询客户供应商
	 * 
	 * @param name
	 * @return
	 */
	public ScmCoc findScmCocByname(String name) {
		List result = this.find(
				"select a from ScmCoc a where a.name = ? and a.company.id=?",
				new Object[] { name, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (ScmCoc) result.get(0);
		else
			return null;
	}

	/**
	 * 保存客户供应商
	 * 
	 * @param coc
	 */
	public void saveScmCoc(ScmCoc coc) {
		this.saveOrUpdate(coc);
	}

	/**
	 * 检查有哪些报关单使用到了此客户
	 * 
	 * @param coc
	 */
	public List checkCoc(ScmCoc coc) {
		List list = this
				.find("select a.customsDeclarationCode from BcsCustomsDeclaration a	where a.customer.code = ? and a.company.id= ? ",
						new Object[] { coc.getCode(),
								CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 删除客户供应商
	 * 
	 * @param coc
	 */
	public void deleteScmCoc(ScmCoc coc) {
		this.delete(coc);
	}

	/**
	 * 保存单损耗
	 * 
	 * @param object
	 */
	public void saveUnitWaste(UnitWaste object) {
		this.saveOrUpdate(object);
	}

	/**
	 * 查询单损耗
	 * 
	 * @param exgNo
	 * @return
	 */
	public List findUnitWaste(String exgNo) {
		return this
				.find("select a from UnitWaste a where a.company.id=? and a.exgNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), exgNo });
	}

	/**
	 * 保存单损耗
	 * 
	 * @param object
	 */
	public void saveBomImgDetail(UnitWaste object) {
		this.saveOrUpdate(object);
	}

	/**
	 * 删除单损耗
	 * 
	 * @param object
	 */
	public void deleteUnitWaste(UnitWaste object) {
		this.delete(object);
	}

	/**
	 * 删除单损耗
	 * 
	 * @param object
	 */
	public void deleteBomImgDetail(UnitWaste object) {
		this.delete(object);
	}

	/**
	 * 删除所有单损耗
	 * 
	 * @param exgNo
	 */
	public void deleteAllBom(String exgNo) {
		this.deleteAll(findUnitWaste(exgNo));
	}

	/**
	 * 返回物流通用代码
	 * 
	 * @param type
	 * @param beginValue
	 * @return
	 */
	public String findParaSetBytype(int type, String beginValue) {
		List list = this
				.find("select a from ParaSet a where a.type = ? and a.beginValue = ? and a.company.id=?",
						new Object[] { type, beginValue,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return ((ParaSet) list.get(0)).getAfterValue();
		}
		return beginValue;
	}

	/**
	 * 查找不在工厂BOM管理父件、料件里的工厂物料主档资料
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialNotInMaster() {
		return this
				.find("select a from EnterpriseMaterial a left join fetch a.complex where a.company.id=? "
						+ " and a.ptNo not in (select b.ptNo from EnterpriseBomMaster b where b.company.id=? )"
						+ " and a.ptNo not in (select c.parentNo from EnterpriseBomManage c where c.company.id=?)"
						+ " and (a.scmCoi.coiProperty=? or a.scmCoi.coiProperty=?) ",
						new Object[] { CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(),
								MaterielType.FINISHED_PRODUCT,
								MaterielType.SEMI_FINISHED_PRODUCT });
	}

	/**
	 * 查找不在工厂BOM管理父件、料件里的工厂物料主档资料(料件)
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialNotInMasterMateriel() {
		return this
				.find("select a from EnterpriseMaterial a left join fetch a.complex where a.company.id=? "
						+ " and a.ptNo not in (select b.ptNo from EnterpriseBomMaster b where b.company.id=? )"
						+ " and a.ptNo not in (select c.parentNo from EnterpriseBomManage c where c.company.id=?)"
						+ " and (a.scmCoi.coiProperty=? or a.scmCoi.coiProperty=?) ",
						new Object[] { CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL,
								MaterielType.SEMI_FINISHED_PRODUCT });
	}

	/**
	 * 判断 工厂BOM管理 里是否完全没有版本，ture为是没有，false为存在有
	 * 
	 * @return boolean ture为是没有，false为存在有
	 */
	public boolean checkEnterpriseBomVersionCount() {
		List list = this.find(
				"select count(distinct a.versionNo) from EnterpriseBomManage a "
						+ " where a.company.id = ? group by a.parentNo ",
				new Object[] { CommonUtils.getCompany().getId() });
		for (int i = 0; i < list.size(); i++) {
			int count = Integer.parseInt(list.get(i).toString());
			if (count > 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 查找 工厂物料主档 资料，但是不在 工厂BOM管理 里由给定的父级料号和父件版本号所确定的子件的子级料号里（不是指定半成品的子件）
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialNotInDetail(String parentNo,
			Integer version) {
		return this
				.find("select a from EnterpriseMaterial a left join fetch a.complex where a.company.id=? "
						+ " and a.ptNo not in (select c.compentNo from EnterpriseBomManage c where c.company.id=?"
						+ " and c.parentNo=? and c.versionNo=? ) "
						+ " and (a.scmCoi.coiProperty=? or a.scmCoi.coiProperty=? )",
						new Object[] { CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(), parentNo,
								version.toString(), MaterielType.MATERIEL,
								MaterielType.SEMI_FINISHED_PRODUCT });
	}

	/**
	 * 查找工厂物料主档资料，但是在工厂BOM管理父件里已经存在
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
	 * @return List 是EnterpriseBomMaster型，工厂物料主档资料
	 */
	public List findEnterpriseBomMaster(int index, int length, String property,
			Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select a from EnterpriseBomMaster a ,EnterpriseMaterial as b "
				+ " where  b.ptNo=a.ptNo and b.company.id=? and a.company.id =? ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  b." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hql += " and  b." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return this.findPageList(hql, paramters.toArray(), index, length);
	}

	/**
	 * 根据料号查找工厂BOM管理父件资料，返回符合条件的第一条数据
	 * 
	 * @param ptNo
	 *            料号
	 * @return EnterpriseBomMaster 工厂BOM管理父件资料
	 */
	public EnterpriseBomMaster findEnterpriseBomMaster(String ptNo) {
		List list = this.find(
				" select a from EnterpriseBomMaster a where a.company.id=?"
						+ " and a.ptNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), ptNo });
		if (list.size() <= 0 || list.get(0) == null) {
			return null;
		} else {
			return (EnterpriseBomMaster) list.get(0);
		}
	}

	/**
	 * 根据料号查找工厂BOM管理成品，返回符合条件数据
	 * 
	 * @param ptNo
	 * @return
	 */
	public EnterpriseBomMaster findEnterpriseBomMasterByparentNo(String ptNo) {
		List list = this.find(
				" select a from EnterpriseBomMaster a where a.company.id=?"
						+ " and a.parentNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), ptNo });
		if (list.size() <= 0 || list.get(0) == null) {
			return null;
		} else {
			return (EnterpriseBomMaster) list.get(0);
		}
	}

	/**
	 * 根据父件料号查找工厂BOM管理版本资料
	 * 
	 * @param parentNo
	 *            父件料号
	 * @return List 是EnterpriseBomVersion型，工厂BOM管理版本资料
	 */
	public List findEnterpriseBomVersion(String parentNo) {
		return this.find(
				"select a from EnterpriseBomVersion a where a.company.id=?"
						+ " and a.parentNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), parentNo });
	}

	// /**
	// * 根据父件料号查找【工厂BOM管理版本资料】根据【料号】关联【工厂常用bom主档】
	// *
	// * @param parentNo
	// * 父件料号
	// * @return List 是EnterpriseBomVersion型，工厂BOM管理版本资料
	// */
	// public List findEnterpriseBomVersionAndEnterpriseMaterial(String
	// parentNo) {
	// return this.find(
	// "select a from EnterpriseBomVersion a,EnterpriseMaterial b where  a.company.id=?"
	// + " and a.parentNo=? ", new Object[] {
	// CommonUtils.getCompany().getId(), parentNo });
	// }

	/**
	 * 根据父件料号、父件版本号查找工厂BOM管理版本资料，返回符合条件的第一条数据
	 * 
	 * @param parentNo
	 *            父件料号
	 * @param version
	 *            父件版本号
	 * @return EnterpriseBomVersion 工厂BOM管理版本资料
	 */
	public EnterpriseBomVersion findEnterpriseBomVersion(String parentNo,
			Integer version) {
		List list = this.find(
				"select a from EnterpriseBomVersion a where a.company.id=?"
						+ " and a.parentNo=? and a.version=? ", new Object[] {
						CommonUtils.getCompany().getId(), parentNo, version });
		if (list.size() <= 0 || list.get(0) == null) {
			return null;
		} else {
			return (EnterpriseBomVersion) list.get(0);
		}
	}

	/**
	 * 根据父级料号从工厂BOM管理子件中查找版本号、生效日期、结束日期、净重
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return List 存放了版本号、生效日期、结束日期、净重
	 */
	public List findEnterpriseBomVers(String parentNo) {
		return this
				.find("select distinct a.versionNo,a.effectDate,a.endDate,a.memo,a.netWeight from EnterpriseBomManage a where a.company.id=?"
						+ " and a.parentNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), parentNo });
	}

	/**
	 * 根据父级料号从工厂BOM管理子件中查找版本号、生效日期、结束日期
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return List 存放了版本号、生效日期、结束日期
	 */
	public String findEnterpriseBomVersMemo(String parentNo, Integer versionNo) {
		List list = this.find(
				"select distinct a.memo from EnterpriseBomManage a where a.company.id=?"
						+ " and a.parentNo=? and a.versionNo=? ",
				new Object[] { CommonUtils.getCompany().getId(), parentNo,
						String.valueOf(versionNo) });
		if (list != null && list.size() > 0)
			return (String) list.get(0);
		else {
			return null;
		}

	}

	/**
	 * 返回工厂物料主档中的成品和半成品的料号、商品名称、商品规格和单位，但是要在工厂BOM管理里有子件的
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
	 * @return List 料号、商品名称、商品规格和单位
	 */
	public List findEnterpriseBomExg(int index, int length, String property,
			Object value, Boolean isLike) {// b.factoryName,b.factorySpec,b.calUnit.name,b.scmCoi.name
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select distinct b.ptNo "
				+ " from EnterpriseBomManage as a ,EnterpriseMaterial as b "
				+ " where b.ptNo=a.parentNo and b.company.id=? and a.company.id =? ";
		// + " and a.parentNo not in (select c.compentNo from
		// BomManage c where c.company.id=?)",
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  b." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hql += " and  b." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return this.findPageList(hql, paramters.toArray(), index, length);
	}

	/**
	 * 返回 工厂BOM管理 列表 （震兴） 关联查询
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
	 * @return List 料号、商品名称、商品规格和单位
	 */
	public List findEnterpriseBomList(int index, int length, String property,
			Object value, Boolean isLike) {// b.factoryName,b.factorySpec,b.calUnit.name,b.scmCoi.name
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select distinct b.ptNo "
				+ " from EnterpriseBomManage as a ,EnterpriseMaterial as b "
				+ " where b.ptNo=a.parentNo and b.company.id=? and a.company.id =? ";
		// + " and a.parentNo not in (select c.compentNo from
		// BomManage c where c.company.id=?)",
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  b." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hql += " and  b." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return this.findPageList(hql, paramters.toArray(), index, length);
	}

	/**
	 * 返回无对应
	 * 
	 * @return List 父级料号
	 */
	public List findMaxFormBomNoInMateri() {
		return this
				.find("select distinct a.parentNo from EnterpriseBomManage a where a.company.id = ?  and a.parentNo not in (select c.compentNo from BomManage c where c.company.id=a.company.id)"
						+ " and a.parentNo not in "
						+ "(select distinct b.parentNo from EnterpriseBomManage as b ,Materiel as c "
						+ "where c.ptNo=b.compentNo and c.company.id=a.company.id and b.company.id = a.company.id and b.parentNo not in (select d.compentNo from BomManage d where d.company.id=b.company.id))",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据父级料号、子件料号、版本号查找工厂BOM管理
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param versionNo
	 *            版本号
	 * @param compentNo
	 *            子件料号
	 * @return
	 */
	public EnterpriseBomManage findEnterpriseBomManageByParentVersionSub(
			String parentNo, String versionNo, String compentNo) {
		List list = this.find("select a from EnterpriseBomManage a where "
				+ " a.company.id = ?  " + " and a.parentNo=? "
				+ " and a.versionNo=? " + " and a.compentNo=? )", new Object[] {
				CommonUtils.getCompany().getId(), parentNo, versionNo,
				compentNo });
		if (list != null || list.size() > 0)
			return (EnterpriseBomManage) list.get(0);
		return null;
	}

	/**
	 * 根据分级料号返回工厂BOM管理里的子件和商品的工厂名称
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return List 工厂BOM管理里的子件、商品的工厂名称
	 */
	public List findEnterpriseBomDetailHasName(String parentNo) {
		return this
				.find("select a,b.factoryName from EnterpriseBomManage as a ,EnterpriseMaterial as b"
						+ " where a.company.id=? and a.parentNo=? and b.ptNo=a.compentNo",
						new Object[] { CommonUtils.getCompany().getId(),
								parentNo });

	}

	/**
	 * 根据查询返回子件料号 = compentNo，工厂BOM管理子件记录列表
	 * 
	 * @param compentNo
	 *            子件料号
	 * @return List 是EnterpriseBomManage型，工厂BOM管理里的子件
	 */
	public List findEnterpriseBomManageList(String compentNo) {
		return this.find("select a from EnterpriseBomManage as a "
				+ " where a.company.id=? and a.compentNo=?", new Object[] {
				CommonUtils.getCompany().getId(), compentNo });

	}

	/**
	 * 根据父级料号返回工厂BOM管理里的子件
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return List 是EnterpriseBomManage型，工厂BOM管理里的子件
	 */
	public List findEnterpriseBomDetail(String parentNo) {
		return this.find("select a from EnterpriseBomManage as a "
				+ " where a.company.id=? and a.parentNo=? "
				+ " order by a.versionNo,a.compentNo", new Object[] {
				CommonUtils.getCompany().getId(), parentNo });
	}

	/**
	 * 根据父级料号返回工厂所有BOM管理里的子件
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return List 是EnterpriseBomManage型，工厂BOM管理里的子件
	 */
	public List findAllEnterpriseBomDetail() {
		return this.find("select a from EnterpriseBomManage as a "
				+ " where a.company.id=? "
				+ " order by a.versionNo,a.compentNo",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据父级料号返回工厂BOM管理里的父级版本号
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return List 工厂BOM管理里的版本号
	 */
	public List findEnterpriseBomManageVersion(String parentNo) {
		return this.find(
				"select distinct a.versionNo from EnterpriseBomManage as a "
						+ " where a.company.id=? and a.parentNo=?",
				new Object[] { CommonUtils.getCompany().getId(), parentNo });

	}

	/**
	 * 返回所有的工厂BOM管理
	 * 
	 * @return List 工厂BOM管理
	 */
	public List findAllEnterpriseBomManage() {
		return this.find("select a from EnterpriseBomManage as a "
				+ " where a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });

	}

	/**
	 * 返回工厂BOM管理里的每条父级料件的料号和具有版本的个数
	 * 
	 * @return Map<String, Integer> key为料号，value为版本的个数
	 */
	public Map<String, List<Integer>> findAllEnterpriseBomVersionCount() {
		Map<String, List<Integer>> hm = new HashMap<String, List<Integer>>();
		List list = this.find(
				"select distinct a.parentNo,a.versionNo from EnterpriseBomManage as a "
						+ " where a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] == null || objs[1] == null) {
				continue;
			}
			String ptNo = objs[0].toString().trim();
			ptNo = CommonUtils.dbc2sbc(ptNo);
			Integer versionNo = Double.valueOf(objs[1].toString().trim())
					.intValue();
			List<Integer> lsVersionNo = hm.get(ptNo);
			if (lsVersionNo == null) {
				lsVersionNo = new ArrayList<Integer>();
				// System.out.println("EnterpriseBomManage.ptNo:" + ptNo
				// + "<> lsVersionNo:" + lsVersionNo);
				hm.put(ptNo, lsVersionNo);
			}
			lsVersionNo.add(versionNo);
		}
		return hm;
	}

	/**
	 * 根据父级料号、版本号子件的个数
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param version
	 *            父件版本号
	 * @return Integer 子件的个数
	 */
	public Integer findEnterpriseBomDetailCount(String parentNo, Integer version) {
		List list = this.find("select count(*) from EnterpriseBomManage as a "
				+ " where a.company.id=? and a.parentNo=?"
				+ " and a.versionNo=? ", new Object[] {
				CommonUtils.getCompany().getId(), parentNo,
				(version == null ? "" : version.toString()) });
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}

	}

	/**
	 * 返回子料（无对应）
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是EnterpriseBomManage型，工厂BOM管理里的子料（无对应）
	 */
	public List findBomDetailNotMateri(String ptNo) {
		return this
				.find("select a from EnterpriseBomManage a where a.company.id = ? and a.parentNo = ? "
						+ "and a.compentNo not in (select b.compentNo from EnterpriseBomManage as b,Materiel as c "
						+ "where c.ptNo=b.compentNo and c.company.id=a.company.id and b.company.id = a.company.id and b.parentNo=a.parentNo)",
						new Object[] { CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 保存工厂BOM管理父件
	 * 
	 * @param master
	 *            工厂BOM管理父件
	 */
	public void saveEnterpriseBomMaster(EnterpriseBomMaster master) {
		this.saveOrUpdate(master);
	}

	/**
	 * 删除工厂BOM管理父件
	 * 
	 * @param master
	 *            工厂BOM管理父件
	 */
	public void deleteEnterpriseBomMaster(EnterpriseBomMaster master) {
		this.delete(master);
	}

	/**
	 * 保存工厂BOM管理版本
	 * 
	 * @param version
	 *            父件版本号
	 */
	public void saveEnterpriseBomVersion(EnterpriseBomVersion version) {
		this.saveOrUpdate(version);
	}

	/**
	 * 删除工厂BOM管理版本
	 * 
	 * @param version
	 *            父件版本号
	 */
	public void deleteEnterpriseBomVersion(EnterpriseBomVersion version) {
		this.delete(version);
	}

	/**
	 * 根据父级料号删除工厂BOM管理版本
	 * 
	 * @param parentNo
	 *            父级料号
	 */
	public void deleteEnterpriseBomVersionByParent(String parentNo) {
		this.batchUpdateOrDelete("delete EnterpriseBomVersion "
				+ " where parentNo=? and company.id=?", new Object[] {
				parentNo, CommonUtils.getCompany().getId() });
	}

	/**
	 * 删除工厂BOM管理
	 */
	public void updateEnterpriseBomManageByptNo() {
		this.batchUpdateOrDelete(
				"update EnterpriseBomManage set unitDosage = (unitWare/(1.0-ware))"
						+ " where company.id=?", new Object[] { CommonUtils
						.getCompany().getId() });
	}

	/**
	 * 保存工厂BOM管理子件
	 * 
	 * @param detail
	 *            工厂BOM管理子件
	 */
	public void saveEnterpriseBomManage(EnterpriseBomManage detail) {
		this.saveOrUpdate(detail);
	}

	/**
	 * 删除工厂BOM管理子件
	 * 
	 * @param detail
	 *            工厂BOM管理子件
	 */
	public void deleteEnterpriseBomManage(EnterpriseBomManage detail) {
		this.delete(detail);
	}

	/**
	 * 根据父级料号删除工厂BOM管理子件
	 * 
	 * @param parentNo
	 *            父级料号
	 */
	public void deleteEnterpriseBomManageByParent(String parentNo) {
		this.batchUpdateOrDelete("delete EnterpriseBomManage "
				+ " where parentNo=? and company.id=?", new Object[] {
				parentNo, CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据父级料号、版本号删除工厂BOM管理版本
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param versionNo
	 *            版本号
	 */
	public void deleteEnterpriseBomManageByVersion(String parentNo,
			Integer versionNo) {
		this.batchUpdateOrDelete(
				"delete EnterpriseBomManage "
						+ " where parentNo=? and versionNo=? and company.id=?",
				new Object[] { parentNo,
						(versionNo == null ? "" : versionNo.toString()),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据父级料号、版本号返回工厂BOM管理子件
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param versionNo
	 *            版本号
	 * @return List 是EnterpriseBomManage型，工厂BOM管理子件
	 */
	public List findEnterpriseBomManageByVersion(String parentNo,
			Integer versionNo) {
		return this
				.find("select a from EnterpriseBomManage a"
						+ " where a.parentNo=? and a.versionNo=? and a.company.id=?",
						new Object[] {
								parentNo,
								(versionNo == null ? "" : versionNo.toString()),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据父级料号、临时版本资料更新工厂BOM管理子件
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param version
	 *            父件版本号
	 */
	public void updateEnterpriseBomManage(String parentNo,
			TempEntBomVersion version) {
		this.batchUpdateOrDelete(
				"update EnterpriseBomManage set effectDate=?,endDate=? "
						+ "where parentNo=? and versionNo=? and company=?",
				new Object[] {
						version.getBeginDate(),
						version.getEndDate(),
						parentNo,
						(version.getVersion() == null ? "" : version
								.getVersion().toString()),
						CommonUtils.getCompany() });
	}

	/**
	 * 更新工厂BOM管理子件的料号
	 * 
	 * @param newParentNo
	 *            新的子件料号
	 * @param oldParentNo
	 *            旧的子件料号
	 */
	public void updateEnterpriseBomManageCompentNo(String newParentNo,
			String oldParentNo) {
		this.batchUpdateOrDelete("update EnterpriseBomManage set compentNo=?"
				+ "where compentNo=? and company=?", new Object[] {
				newParentNo, oldParentNo, CommonUtils.getCompany() });
	}

	/**
	 * 更新工厂BOM管理父件的料号
	 * 
	 * @param newCompentNo
	 *            新的料号
	 * @param oldCompentNo
	 *            旧的料号
	 */
	public void updateEnterpriseBomManageParentNo(String newCompentNo,
			String oldCompentNo) {
		this.batchUpdateOrDelete("update EnterpriseBomManage set parentNo=?"
				+ "where parentNo=? and company=?", new Object[] {
				newCompentNo, oldCompentNo, CommonUtils.getCompany() });
	}

	/**
	 * 根据工厂物料反写电子化手册中的工厂与物料对应的折算系数
	 * 
	 * @param mt
	 */
	public void updateBcsInnerMergeUnitConvert(Materiel mt) {
		this.batchUpdateOrDelete(" update BcsInnerMerge set unitConvert=?"
				+ " where materiel.id=? and company.id=?",
				new Object[] { mt.getUnitConvert(), mt.getId(),
						CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据修改工厂与物料的对应更新单据中心的对应关系的折算系数
	 * 
	 * @param materielId
	 */
	public void updateFactoryAndFactualCustomsRalationUnitConvert(Materiel mt) {
		this.batchUpdateOrDelete(
				" update FactoryAndFactualCustomsRalation a "
						+ " set a.unitConvert =?  where  a.materiel.id=? and a.company.id=? ",
				new Object[] { mt.getUnitConvert(), mt.getId(),
						CommonUtils.getCompany().getId() });

		// List<Object> parameters = new ArrayList<Object>();
		// parameters.add(mt.getUnitConvert());
		// parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(mt.getId());
		// String hsql = " update FactualCustomsRalationUnitConvert a "
		// + " set a.unitConvert =?  where a.company.id=?  and a.materiel.id=?";
		// this.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	//
	// /**
	// * 查询工厂BOM管理最顶层的成品
	// *
	// * @return List 父级料号
	// */
	// public List findProductBomManage() {
	// String hsql = "select distinct a.parentNo from EnterpriseBomManage a "
	// + " where a.parentNo not in ( select b.compentNo from EnterpriseBomManage
	// b"
	// + " where b.company.id=? ) and a.company.id=? ";
	// return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
	// CommonUtils.getCompany().getId() });
	// }
	// /**
	// * 查询工厂BOM管理最顶层的成品或半成品
	// *
	// * @return List 父级料号
	// */
	// public List findAllProductBomManageParentNo() {
	// String hsql = "select distinct a.parentNo from EnterpriseBomManage a "
	// + " where a.company.id=? ";
	// return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
	// CommonUtils.getCompany().getId() });
	// }
	// /**
	// * 查询企业BOM最顶层的成品(并且没有在报关常用BOM中)
	// *
	// * @return
	// */
	// public List findTopProductBomManage() {
	// String hsql = " select b from EnterpriseMaterial b "
	// + " where b.ptNo in (select distinct d.parentNo from EnterpriseBomManage
	// d "
	// + " where not exists ( select c.compentNo from EnterpriseBomManage c"
	// + " where c.compentNo=d.parentNo and c.company.id=?)"
	// + " and not exists (select e.materiel.ptNo from MaterialBomMaster e"
	// + " where e.materiel.ptNo=d.parentNo and e.company.id=?)"
	// + " and d.company.id=? ) and b.company.id=? ";
	// return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
	// CommonUtils.getCompany().getId(),
	// CommonUtils.getCompany().getId(),
	// CommonUtils.getCompany().getId() });
	// }
	//
	/**
	 * 在工厂物料主档里查找成品或是委外的半成品
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档里成品或是委外的半成品
	 */
	public List findTopProductBomManage() {
		// String hsql = " select b from EnterpriseMaterial b "
		// + " where b.scmCoi.coiProperty in (?,?) "
		// + " and not exists(select c.materiel.ptNo from MaterialBomMaster c "
		// + " where b.ptNo = c.materiel.ptNo and c.company = ?) and b.company
		// =?";
		// return this.find(hsql, new Object[] { MaterielType.FINISHED_PRODUCT,
		// MaterielType.SEMI_FINISHED_PRODUCT, CommonUtils.getCompany(),
		// CommonUtils.getCompany()});
		String hsql = " select distinct b from EnterpriseBomManage a,EnterpriseMaterial b "
				+ " where a.parentNo=b.ptNo and "
				// +
				// " (b.scmCoi.coiProperty = ? or (b.scmCoi.coiProperty = ? and b.isOutsource=? ))"
				+ " (b.scmCoi.coiProperty = ? or b.scmCoi.coiProperty = ?)"// wss:2010.05.10加进了半成品
				+ " and a.company.id =? and b.company.id =? ";
		return this.find(hsql, new Object[] {
				MaterielType.FINISHED_PRODUCT,
				MaterielType.SEMI_FINISHED_PRODUCT,// new Boolean(true),
				CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找工厂物料主档资料,但要在报关常用工厂BOM成品里存在
	 * 
	 * @return List 是EnterpriseMaterial型，报关常用工厂BOM成品
	 */
	public List findEnterpriseMaterialInBomMaster() {
		String hsql = " select b from EnterpriseMaterial b,MaterialBomMaster c "
				+ " where b.ptNo = c.materiel.ptNo and c.company = ? and b.company =?";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				CommonUtils.getCompany() });
	}

	/**
	 * 根据版本号返回报关常用工厂BOM版本所对应的成品物料
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 报关常用工厂BOM版本所对应的成品物料
	 */
	public List findEnterpriseMaterialInBomMaster(Integer version) {
		String hsql = " select distinct c.master.materiel.ptNo from MaterialBomVersion c "
				+ " where c.company = ? and c.version=? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany(), version });
	}

	/**
	 * 返回报关常用工厂BOM版本
	 * 
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public List findExistMaterialBomVersion() {
		String hsql = " select distinct c.version from MaterialBomVersion c "
				+ " where c.company = ?  ";
		List list = this.find(hsql, new Object[] { CommonUtils.getCompany() });
		Collections.sort(list);
		return list;
	}

	/**
	 * 返回报关常用工厂BOM版本里的数目
	 * 
	 * @return int 报关常用工厂BOM版本里的数目
	 */
	public int findMaxMaterialBomVersion() {
		String hsql = " select max(c.version) from MaterialBomVersion c "
				+ " where c.company = ?  ";
		List list = this.find(hsql, new Object[] { CommonUtils.getCompany() });
		if (list.isEmpty() || list.get(0) == null) {
			return 0;
		} else {
			return Integer.valueOf(list.get(0).toString()) + 1;
		}
	}

	/**
	 * 在工厂BOM管理里根据父级料号查询对应成品的料件组成
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return List 是EnterpriseBomManage型，工厂BOM管理子件
	 */
	public List findChildBomByParent(String parentNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EnterpriseBomManage a where a.parentNo=? "
				+ " and a.company.id=? ";
		parameters.add(parentNo);
		parameters.add(CommonUtils.getCompany().getId());
		// if (effectDate != null) {
		// hsql += " and a.effectDate=? ";
		// parameters.add(effectDate);
		// } else {
		// hsql += " and a.effectDate is null ";
		// }
		//
		// if (endDate != null) {
		// hsql += " and a.endDate=? ";
		// parameters.add(endDate);
		// } else {
		// hsql += " and a.endDate is null ";
		// }
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 在工厂BOM管理里根据父级料号、版本号查询成品的料件组成
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param versionNo
	 *            版本号
	 * @return List 是EnterpriseBomManage型，工厂BOM管理子件
	 */
	public List findChildBomByParent(String parentNo, String versionNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from EnterpriseBomManage a where a.parentNo=? "
				+ " and a.versionNo=? and a.company.id=? ";
		parameters.add(parentNo);
		parameters.add(versionNo);
		parameters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据父级料号在工厂BOM管理里查找对应的子件料号
	 * 
	 * @param childNo
	 *            父级料号
	 * @return List 子件料号
	 */
	public List findChildParentBom(String childNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.compentNo"// a.parentNo
		// ,a.effectDate,a.endDate
				+ " from EnterpriseBomManage a where a.parentNo=? "
				+ " and a.company.id=? ";
		parameters.add(childNo);
		parameters.add(CommonUtils.getCompany().getId());
		// if (childEffectDate != null) {
		// hsql += " and a.effectDate=? ";
		// parameters.add(childEffectDate);
		// } else {
		// hsql += " and a.effectDate is null ";
		// }
		// if (childEndDate != null) {
		// hsql += " and a.endDate=? ";
		// parameters.add(childEndDate);
		// } else {
		// hsql += " and a.endDate is null ";
		// }
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据父级料号在工厂BOM管理里查找对应子件的个数
	 * 
	 * @param parentNo
	 *            父级料号
	 * @return int 子件的个数
	 */
	public int findChildBomCount(String parentNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select count(*) "// a.parentNo ,a.effectDate,a.endDate
				+ " from EnterpriseBomManage a where a.parentNo=? "
				+ " and a.company.id=? ";
		parameters.add(parentNo);
		parameters.add(CommonUtils.getCompany().getId());
		// if (childEffectDate != null) {
		// hsql += " and a.effectDate=? ";
		// parameters.add(childEffectDate);
		// } else {
		// hsql += " and a.effectDate is null ";
		// }
		// if (childEndDate != null) {
		// hsql += " and a.endDate=? ";
		// parameters.add(childEndDate);
		// } else {
		// hsql += " and a.endDate is null ";
		// }
		List list = this.find(hsql, parameters.toArray());
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return Integer.valueOf(list.get(0).toString());
		}
	}

	/**
	 * 抓取工厂物料主档资料，但是不在报关常用工厂物料里
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialForMaterial(String materielType) {
		List list = this.find("select a from EnterpriseMaterial a "
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " where a.scmCoi.code=? and a.company.id = ?"
				+ " and a.ptNo not in (select b.ptNo from Materiel b "
				+ " where b.scmCoi.code=? and b.company.id = ? )",
				new Object[] { materielType, CommonUtils.getCompany().getId(),
						materielType, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 根据物料类型抓取工厂物料主档资料
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialByType(String materielType) {
		List list = this.find("select a from EnterpriseMaterial a "
				+ " left join fetch a.complex left join fetch a.scmCoc "
				+ " where a.scmCoi.code=? and a.company.id = ?", new Object[] {
				materielType, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 根据物料类型抓取报关常用工厂物料资料
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是Materiel型，报关常用工厂物料资料
	 */
	public List findCustomsMaterialByType(String materielType) {
		List list = this.find("select b.ptNo from Materiel b "
				+ " where b.scmCoi.code=? and b.company.id = ? ", new Object[] {
				materielType, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询所有工厂物料主档资料
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List<EnterpriseMaterial> findEnterpriseMaterial() { // 物料主档显示所有数据
		return this.find(
				"select a from EnterpriseMaterial a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询所有工厂物料主档资料key
	 * 
	 * key = ptNo
	 * 
	 * @return List 是string型，工厂物料主档资料key
	 */
	public List<String> findEnterpriseMaterialKeys() { // 物料主档显示所有数据
		return this.findEnteriprisePtNo();
	}

	/**
	 * 根据物料类型抓取工厂物料主档资料
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialByType(List names,
			ConsignQueryCondition condition) { // 物料主档显示所有数据
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from EnterpriseMaterial a where a.company.id= ? and a.scmCoi.coiProperty in (";
		for (int i = 0, size = names.size(); i < size; i++) {
			if (i == 0) {
				hsql += "\'" + names.get(i) + "\'";
			} else {
				hsql += ",\'" + names.get(i) + "\'";
			}
		}
		hsql += ")";

		paramters.add(CommonUtils.getCompany().getId());
		if (condition != null && condition.getStartPartNo() != null
				&& condition.getEndPartNo() == null) {
			hsql += " and a.ptNo=?";
			paramters.add(condition.getStartPartNo());
		}
		if (condition != null && condition.getStartPartNo() != null
				&& condition.getEndPartNo() != null) {
			hsql += " and a.ptNo>=? and a.ptNo<=?";
			paramters.add(condition.getStartPartNo());
			paramters.add(condition.getEndPartNo());
		}
		if (condition != null && condition.getName() != null) {
			hsql += " and a.factoryName=?";
			paramters.add(condition.getName());
		}
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 根据料号抓取工厂物料主档资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return EnterpriseMaterial 工厂物料主档资料
	 */
	public EnterpriseMaterial findEnterpriseMaterial(String ptNo) {
		List list = this.find("select a from EnterpriseMaterial a "
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " where a.ptNo = ? and a.company.id = ?", new Object[] {
				ptNo, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (EnterpriseMaterial) list.get(0);
		}
		return null;
	}

	public List<EnterpriseMaterial> findEnterpriseMaterialList() {
		List list = this.find("select a from EnterpriseMaterial a "
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " where a.company.id = ?", new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 根据公司抓取工厂物料主档资料
	 * 
	 * @param company
	 *            公司
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterial(Company company) { // 物料主档显示所有数据
		return this.find(
				"select a from EnterpriseMaterial a where a.company.id= ?",
				new Object[] { company.getId() });
	}

	/**
	 * 工厂物料主档显示所有数据来自工厂物料主档的类别List
	 * 
	 * @param materielTypeList
	 *            物料类型List
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterial(List materielTypeList) {
		if (materielTypeList == null || materielTypeList.size() <= 0) {
			return null;
		}
		String hsql = "select m from EnterpriseMaterial m where m.company.id= ? ";
		List parameters = materielTypeList;
		for (int i = 0; i < materielTypeList.size(); i++) {
			if (i == 0) {
				hsql += " and ( m.scmCoi.coiProperty = ? ";
			} else {
				hsql += " or m.scmCoi.coiProperty = ? ";
			}
			if (i == materielTypeList.size() - 1) {
				hsql += " ) ";
			}
		}
		parameters.add(0, CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 根据条件抓取工厂物料主档资料
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterial(String materielType, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from EnterpriseMaterial as a"
				+ " left join fetch a.complex " + " left join fetch a.scmCoc"
				+ " left join fetch a.calUnit"
				+ " where a.scmCoi.code=? and a.company.id=? ";
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.createDate desc ";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据条件抓取工厂所有BOM
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findAllEnterpriseBomDetail(int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from EnterpriseBomManage as a"
				+ " where a.company.id=? ";
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据条件抓取工厂物料主档资料
	 * 
	 * @param materielType
	 *            物料类型
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
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public Long findEnterpriseMaterialCount(String materielType, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select count(a.id)  from EnterpriseMaterial as a"
				+ " where a.scmCoi.code=? and a.company.id=? ";
		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		List list = find(hsql, paramters.toArray());
		if (list != null && !list.isEmpty()) {
			return list.get(0) == null ? 0L : (Long) list.get(0);
		}
		return 0L;
	}

	/**
	 * 返回工厂BOM的总条数
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public Long findAllEnterpriseBomDetailCount(int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select count(a.id)  from EnterpriseBomManage as a"
				+ " where a.company.id=? ";
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		List list = find(hsql, paramters.toArray());
		if (list != null && !list.isEmpty()) {
			return list.get(0) == null ? 0L : (Long) list.get(0);
		}
		return 0L;
	}

	/**
	 * 查询料件和半成品
	 * 
	 * @return
	 */
	public List findEnterpriseMaterialMaterielAndProduct(int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "from EnterpriseMaterial a where a.company.id=? and a.scmCoi.coiProperty in (?,?)";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(MaterielType.MATERIEL);
		paramters.add(MaterielType.SEMI_FINISHED_PRODUCT);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add("%" + value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据id来查询工厂物料主档资料
	 * 
	 * @param id
	 *            工厂物料主档Id
	 * @return EnterpriseMaterial 工厂物料主档资料
	 */
	public EnterpriseMaterial findEnterpriseMaterialById(String id) { // 物料主档显示所有数据
		return (EnterpriseMaterial) this.load(EnterpriseMaterial.class, id);
	}

	/**
	 * 保存工厂物料主档资料资料
	 * 
	 * @param materiel
	 *            工厂物料主档
	 */
	public void saveEnterpriseMaterial(EnterpriseMaterial materiel) {
		this.saveOrUpdate(materiel); // 物料主档保存
	}

	/**
	 * 删除工厂物料主档资料
	 * 
	 * @param materiel
	 *            工厂物料主档
	 */
	public void deleteEnterpriseMaterial(EnterpriseMaterial materiel) { // 物料主档删除
		this.delete(materiel);
	}

	/**
	 * 根据物料类型查找工厂物料主档资料
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialByMaterielType(String materielType) {
		return this
				.find("select a from EnterpriseMaterial a where a.scmCoi.coiProperty = ? and a.company.id=? ",
						new Object[] { materielType,
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据料号查找工厂物料主档资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public EnterpriseMaterial findEnterpriseMaterialByPtNo(String ptNo) { // 物料主档查找是否重复
		List result = this
				.find("select a from EnterpriseMaterial a where a.ptNo=? and a.company.id= ?",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (EnterpriseMaterial) result.get(0);
		else
			return null;
	}

	/**
	 * 根据料号查找工厂物料主档资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public Map<String, EnterpriseMaterial> findEnterpriseMaterialPtNoMap() { // 物料主档查找是否重复
		Map<String, EnterpriseMaterial> map = new HashMap<String, EnterpriseMaterial>();
		List result = this
				.find(" select  a from EnterpriseMaterial a where   a.company.id= ? ",
						new Object[] { CommonUtils.getCompany().getId() });
		for (int i = 0; i < result.size(); i++) {
			EnterpriseMaterial data = (EnterpriseMaterial) result.get(i);
			String key = data.getPtNo() == null ? "" : data.getPtNo();
			map.put(key, data);
		}
		return map;
	}

	/**
	 * 根据料号查找报关常用工厂物料资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是Materiel型，报关常用工厂物料资料
	 */
	public Materiel findMaterialByPtNo(String ptNo) { // 物料主档查找是否重复
		List result = this.find(
				"select a from Materiel a where a.ptNo=? and a.company.id= ?",
				new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (Materiel) result.get(0);
		else
			return null;
	}

	/**
	 * 根据料号查找报关常用工厂物料资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是Materiel型，报关常用工厂物料资料
	 */
	public List findRepeatMaterialByPtNo(String ptNo) { // 物料主档查找是否重复
		List result = this.find("select a from Materiel a "
				+ " left join fetch a.complex "
				+ " where a.ptNo=? and a.company.id= ?", new Object[] { ptNo,
				CommonUtils.getCompany().getId() });
		return result;
	}

	/**
	 * 在报关常用工厂物料里重复的数量
	 * 
	 * @return List 料号、对应的个数
	 */
	public List findRepeatMaterial() { // 物料主档查找是否重复
		List result = this.find(
				"select ptNo,count(id) from Materiel where company=? "
						+ " group by ptNo having count(id)>1 "
						+ " order by ptNo ",
				new Object[] { CommonUtils.getCompany() });
		return result;
	}

	/**
	 * 根据Id查找报关常用工厂物料资料
	 * 
	 * @param id
	 *            报关常用工厂物料Id
	 * @return Materiel 报关常用工厂物料资料
	 */
	public Materiel findMaterielById(String id) {
		return (Materiel) this.load(Materiel.class, id);
	}

	/**
	 * 保存报关常用工厂物料资料
	 * 
	 * @param materiel
	 *            报关常用工厂物料资料
	 */
	public void saveMateriel(Materiel materiel) {
		this.saveOrUpdate(materiel); // 物料主档保存
	}

	public void saveEMateriel(EnterpriseMaterial materiel) {
		this.saveOrUpdate(materiel); // 物料主档保存
	}

	/**
	 * 根据条件查找报关常用工厂BOM成品资料
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
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public List findMaterielBomMaster(int index, int length, String property,
			Object value, Boolean isLike) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from MaterialBomMaster a left join fetch a.materiel "
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
	 * 查找报关常用工厂BOM里的半成品或成品资料
	 * 
	 * @param isHalf
	 *            判断是否半成品，true为是
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM里的半成品或成品资料
	 */
	public List findMaterielBomMaster(Boolean isHalf) {
		String type = null;
		if (!isHalf.booleanValue()) {
			type = MaterielType.FINISHED_PRODUCT;
		} else {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
		}
		return this
				.find("select a from MaterialBomMaster a  left join fetch a.materiel where a.company.id=? "
						+ "and a.materiel.scmCoi.coiProperty=?", new Object[] {
						CommonUtils.getCompany().getId(), type });
	}

	/**
	 * 根据料号查找报关常用工厂BOM成品的数量
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是型，
	 */
	public int findMaterielBomMasterCountByPtNo(String ptNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select count(*) from MaterialBomMaster a "
				+ " where a.company.id=? " + " and a.materiel.ptNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ptNo);
		List list = this.find(hql, parameters.toArray());
		if (list.size() <= 0) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString());
		}
	}

	/**
	 * 根据料号查找报关常用工厂BOM成品资料
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public MaterialBomMaster findMaterielBomMasterByPtNo(String ptNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from MaterialBomMaster a "
				+ " left join fetch a.materiel "
				+ " where a.company.id=? and a.materiel.ptNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ptNo);
		List list = this.find(hql, parameters.toArray());
		if (list.size() <= 0 || list.get(0) == null) {
			return null;
		} else {
			return (MaterialBomMaster) list.get(0);
		}
	}

	/**
	 * 根据料号查找报关常用工厂BOM成品资料列表
	 * 
	 * @param ptNos
	 *            料号
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public List<MaterialBomMaster> findMaterielBomMasterByPtNos(String... ptNos) {
		List<Object> parameters = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder(
				"select a from MaterialBomMaster a "
						+ " left join fetch a.materiel "
						+ " where a.company.id=? ");
		parameters.add(CommonUtils.getCompany().getId());

		if (ptNos != null && ptNos.length > 0) {
			hql.append("a.materiel.ptNo in (" + ptNos[0]);
			for (int i = 1; i < ptNos.length; i++) {
				hql.append("," + ptNos[i]);
			}
			hql.append(")");
		}

		return this.find(hql.toString(), parameters.toArray());
	}

	/**
	 * 根据报关常用工厂BOM版本查找报关常用工厂BOM里的料件
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List findMaterielBomDetail(MaterialBomVersion version) {
		return this.find("select a from MaterialBomDetail a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.version "
				+ " left join fetch a.version.master "
				+ " left join fetch a.version.master.materiel"
				+ " where a.version.id=?", new Object[] { version.getId() });
	}

	/**
	 * 根据版本号查找报关常用工厂BOM里的料件
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List findMaterielBomDetail(Integer version) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from MaterialBomDetail a where a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (version != null) {
			hql += " and a.version.version=? ";
			parameters.add(version);
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据报关常用工厂物料、版本号查找报关常用工厂BOM里的料件
	 * 
	 * @param pm
	 *            报关常用工厂物料
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List findMaterielBomDetail(Materiel pm, Integer version) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from MaterialBomDetail a where a.company.id=? "
				+ " and a.version.master.materiel=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(pm);
		if (version != null) {
			hql += " and a.version.version=? ";
			parameters.add(version);
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 根据报关常用工厂BOM成品查找报关常用工厂BOM里的料件
	 * 
	 * @param master
	 *            报关常用工厂BOM成品
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List findMaterielBomDetail(MaterialBomMaster master) {
		return this
				.find("select a from MaterialBomDetail a "
						+ " left join fetch a.materiel "
						+ " left join fetch a.version "
						+ " left join fetch a.version.master "
						+ " left join fetch a.version.master.materiel"
						+ " where a.company.id=? "
						+ " and a.version.master.id=? ", new Object[] {
						CommonUtils.getCompany().getId(), master.getId() });
	}

	/**
	 * 返回报关常用工厂BOM料件的数目
	 * 
	 * @return Integer 报关常用工厂BOM料件的数目
	 */
	public Integer findMaterielBomDetailCount() {
		String hsql = "select count(*) from MaterialBomDetail ";
		List list = super.find(hsql);
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return (Integer) list.get(0);
		}
	}

	/**
	 * 根据条件查找报关常用工厂BOM料件
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List<String> findMaterielBomDetailId(int index, int length) {
		String hsql = "select a.id from MaterialBomDetail a ";
		// System.out.println(hsql);
		// + " left join fetch a.version.master "
		// + " left join fetch a.version.master.materiel m2 left join fetch
		// m2.complex ";
		// + " where a.company.id=? ";
		// List<Object> parameters = new ArrayList<Object>();
		// parameters.add(CommonUtils.getCompany().getId());
		return super.findPageList(hsql, index, length);
	}

	/**
	 * 根据条件查找报关常用工厂BOM料件
	 * 
	 * @param beginId
	 *            开始Id
	 * @param endId
	 *            结束Id
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List findMaterielBomDetail(String beginId, String endId) {
		String hsql = "select a from MaterialBomDetail a"
				+ " left join fetch a.materiel m"
				+ " left join fetch m.complex c"
				+ " left join fetch a.version v "
				+ " left join fetch v.master master "
				+ " left join fetch master.materiel ma "
				+ " left join fetch ma.complex com where a.id >= ? and a.id<= ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(beginId);
		parameters.add(endId);
		return super.find(hsql, parameters.toArray());
	}

	/**
	 * 根据版本号、物料号码查找报关常用工厂BOM料件，但是要它的父件来自报关常用工厂物料
	 * 
	 * @param version
	 *            父件版本号
	 * @param ptPart
	 *            料号
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List<MaterialBomDetail> findMaterielBomDetail(Integer version,
			String ptPart) {
		String hsql = "select a from MaterialBomDetail a"
				+ " left join fetch a.materiel m"
				+ " left join fetch m.complex c"
				+ " left join fetch a.version v "
				+ " left join fetch v.master master "
				+ " left join fetch master.materiel ma "
				+ " left join fetch ma.complex com "
				+ " where ma.ptNo = ? and v.version=?  ";
		return this.find(hsql, new Object[] { ptPart, version });
	}

	/**
	 * 根据版本号Id查找报关常用工厂BOM料件资料
	 * 
	 * @param versionId
	 *            版本号Id
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List<MaterialBomDetail> findMaterielBomDetail(String versionId) {
		String hsql = "select a from MaterialBomDetail a "
				+ " left join fetch a.materiel m "
				+ " left join fetch m.complex c" + "where a.version.id = ? ";
		return this.find(hsql, new Object[] { versionId });
	}

	/**
	 * 查找报关常用工厂BOM料件资料，但父件与所给的报关常用工厂物料对应、版本号与所给的版本号对应、自己与所给的报关常用工厂物料对应
	 * 
	 * @param pm
	 *            父件对应的报关常用工厂物料
	 * @param cm
	 *            子件对应的报关常用工厂物料
	 * @param version
	 *            版本号
	 * @return MaterialBomDetail 报关常用工厂BOM料件，返回符合条件的第一条数据
	 */
	public MaterialBomDetail findMaterielBomDetail(Materiel pm, Materiel cm,
			Integer version) {
		String hsql = "select a from MaterialBomDetail a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.materiel.complex "
				+ " where a.materiel=? and a.version.version=? "
				+ " and a.version.master.materiel=?";
		List list = this.find(hsql, new Object[] { cm, version, pm });
		if (list.isEmpty()) {
			return null;
		} else {
			return (MaterialBomDetail) list.get(0);
		}
	}

	/**
	 * 根据一个或多个版本号查找报关常用工厂BOM料件资料版本号Id、单耗、单项用量、损耗和父件料号
	 * 
	 * @param pkNoList
	 *            版本号List
	 * @return List 报关常用工厂BOM料件资料版本号Id、单耗、单项用量、损耗和父件料号
	 */
	public List findMaterielBomDetail(List<String> pkNoList) {

		StringBuilder hsql = new StringBuilder(10000);
		hsql.append("select m.ptNo,a.version.id,a.unitWaste,a.unitUsed,a.waste "
				+ "from MaterialBomDetail a "
				+ "	left join a.materiel m "
				+ "where 1=1 ");

		int size = pkNoList.size();
		if (size > 0) {
			hsql.append("and a.version.id in ('" + pkNoList.get(0));

			for (int i = 1; i < size; i++) {
				hsql.append("','" + pkNoList.get(i));
			}
			hsql.append("') ");
		}

		System.out.println(hsql.toString());
		return this.findNoCache(hsql.toString(), new Object[] {});
	}

	/**
	 * 查找所有的报关常用工厂BOM料件明细
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	public List findMaterielBomDetail(int index, int length) {
		String hsql = "select a from MaterialBomDetail a"
				+ " left join fetch a.materiel m"
				+ " left join fetch m.complex c"
				+ " left join fetch a.version v "
				+ " left join fetch v.master master "
				+ " left join fetch master.materiel ma "
				+ " left join fetch ma.complex com ";

		// System.out.println(hsql);
		// + " left join fetch a.version.master "
		// + " left join fetch a.version.master.materiel m2 left join fetch
		// m2.complex ";
		// + " where a.company.id=? ";
		// List<Object> parameters = new ArrayList<Object>();
		// parameters.add(CommonUtils.getCompany().getId());
		return super.findPageList(hsql, index, length);
	}

	/**
	 * 保存报关常用工厂BOM成品
	 * 
	 * @param materielBomMaster
	 *            报关常用工厂BOM成品
	 */
	public void saveMaterielBomMaster(MaterialBomMaster materielBomMaster) {
		this.saveOrUpdate(materielBomMaster);
	}

	/**
	 * 删除报关常用工厂BOM成品
	 * 
	 * @param materielBomMaster
	 *            报关常用工厂BOM成品
	 */
	public void deleteMaterielBomMaster(MaterialBomMaster materielBomMaster) {
		this.delete(materielBomMaster);
	}

	/**
	 * 保存报关常用工厂BOM料件
	 * 
	 * @param materielBomDetail
	 *            报关常用工厂BOM料件
	 */
	public void saveMaterielBomDetail(MaterialBomDetail materielBomDetail) {
		this.saveOrUpdate(materielBomDetail);
	}

	/**
	 * 删除报关常用工厂BOM料件
	 * 
	 * @param materielBomDetail
	 *            报关常用工厂BOM料件
	 */
	public void deleteMaterielBomDetail(MaterialBomDetail materielBomDetail) {
		this.delete(materielBomDetail);
	}

	/**
	 * 根据版本号Id返回报关常用工厂BOM版本
	 * 
	 * @param id
	 *            版本号Id
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public MaterialBomVersion findMaterialBomVersionById(String id) {
		return (MaterialBomVersion) this.load(MaterialBomVersion.class, id);
	}

	/**
	 * 根据报关常用工厂BOM成品查找报关常用工厂BOM版本
	 * 
	 * @param master
	 *            报关常用工厂BOM成品
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public List findMaterielBomVersion(MaterialBomMaster master) {
		return this.find("select a from MaterialBomVersion a "
				+ " left join fetch a.master "
				+ " left join fetch a.master.materiel"
				+ " where a.master.id=? order by a.version ",
				new Object[] { master.getId() });
	}

	/**
	 * 根据料号查找报关常用工厂BOM版本
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public List findMaterielBomVersionByPtNo(String ptNo) {
		return this
				.find("select distinct a.version from MaterialBomVersion a "
						+ " where a.company.id=? and a.master.materiel.ptNo=? order by a.version ",
						new Object[] { CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 根据料号查找报关常用工厂BOM版本
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public List findMaterielBomVersionObjectByPtNo(String ptNo) {
		return this
				.find("select a from MaterialBomVersion a "
						+ " where a.company.id=? and a.master.materiel.ptNo=? order by a.version ",
						new Object[] { CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 根据料号查找（最大的）报关常用工厂BOM版本
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public MaterialBomVersion findMaxMaterielBomVersionByPtNo(String ptNo) {
		return (MaterialBomVersion) this
				.createQuery(
						"select a from MaterialBomVersion a where a.company.id=? and a.master.materiel.ptNo=? and a.version = "
								+ "(select max(a.version) from MaterialBomVersion a where a.company.id=? and a.master.materiel.ptNo=?)",
						new Object[] { CommonUtils.getCompany().getId(), ptNo,
								CommonUtils.getCompany().getId(), ptNo })
				.uniqueResult();
	}

	/**
	 * 统计报关常用工厂BOM版本对应的成品具有的版本号个数
	 * 
	 * @return Map<String, Integer> key为料号、value为版本号个数
	 */
	public Map<String, List<Integer>> findAllMaterielBomVersionCount() {
		Map<String, List<Integer>> hm = new HashMap<String, List<Integer>>();
		List list = this.find(
				"select distinct a.master.materiel.ptNo,a.version from MaterialBomVersion a "
						+ " where a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] == null || objs[1] == null) {
				continue;
			}
			String ptNo = objs[0].toString().trim();
			ptNo = CommonUtils.dbc2sbc(ptNo);
			Integer versionNo = Integer.parseInt(objs[1].toString().trim());
			List<Integer> lsVersionNo = hm.get(ptNo);
			if (lsVersionNo == null) {
				lsVersionNo = new ArrayList<Integer>();
				// System.out.println("MaterialBomVersion.ptNo:" + ptNo
				// + "<> lsVersionNo:" + lsVersionNo);
				hm.put(ptNo, lsVersionNo);
			}
			lsVersionNo.add(versionNo);
		}
		return hm;
	}

	/**
	 * 根据料号所对应的报关常用工厂BOM成品具有的最大版本号
	 * 
	 * @param ptNo
	 *            料号
	 * @return Integer 最大版本号
	 */
	public Integer findMaxMaterielBomVersion(String ptNo) {
		List list = this.find(
				"select max(a.version) from MaterialBomVersion a where a.company.id=? "
						+ "and a.master.materiel.ptNo=? ", new Object[] {
						CommonUtils.getCompany().getId(), ptNo });
		if (list.size() <= 0 || list.get(0) == null) {
			return 0;
		} else {
			return Integer.parseInt(list.get(0).toString()) + 1;
		}
	}

	/**
	 * 根据父级料号、父件版本号查找工厂BOM管理版本
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseBomVersion型，工厂BOM管理版本
	 */
	public List findEnterpriseBomVersionByVersion(String parentNo,
			Integer version) {
		return this.find(
				"select a from EnterpriseBomVersion a where a.company.id=? "
						+ " and a.parentNo=? and a.version=?  ", new Object[] {
						CommonUtils.getCompany().getId(), parentNo, version });
	}

	/**
	 * 根据报关常用工厂BOM成品、版本号查找报关常用工厂BOM版本
	 * 
	 * @param master
	 *            报关常用工厂BOM成品
	 * @param version
	 *            版本号
	 * @return List 是型，报关常用工厂BOM版本
	 */
	public List findMaterielBomVersionByVersion(MaterialBomMaster master,
			Integer version) {
		return this.find("select a from MaterialBomVersion a where  "
				+ "  a.master.id=? and a.version=?  ",
				new Object[] { master.getId(), version });
	}

	/**
	 * 根据报关常用工厂BOM成品料号、版本号查找报关常用工厂BOM版本
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseBomVersion型，工厂BOM管理版本
	 */
	public List findMaterielBomVersionByVersion(String parentNo, Integer version) {
		return this.find(
				"select a from MaterialBomVersion a where a.company.id=? "
						+ " and a.master.materiel.ptNo=? and a.version=?  ",
				new Object[] { CommonUtils.getCompany().getId(), parentNo,
						version });
	}

	/**
	 * 根据报关常用工厂物料、版本号查找报关常用工厂BOM版本
	 * 
	 * @param materiel
	 *            报关常用工厂物料
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public MaterialBomVersion findMaterielBomVersion(Materiel materiel,
			Integer version) {
		List list = this.find("select a from MaterialBomVersion a where  "
				+ "  a.master.materiel=? and a.version=?  ", new Object[] {
				materiel, version });
		if (list.isEmpty()) {
			return null;
		} else {
			return (MaterialBomVersion) list.get(0);
		}
	}

	/**
	 * 保存报关常用工厂BOM版本
	 * 
	 * @param materielBomVersion
	 *            报关常用工厂BOM版本
	 */
	public void saveMaterielBomVersion(MaterialBomVersion materielBomVersion) {
		this.saveOrUpdate(materielBomVersion);
	}

	/**
	 * 删除报关常用工厂BOM版本
	 * 
	 * @param materielBomVersion
	 *            报关常用工厂BOM版本
	 */
	public void deleteMaterielBomVersion(MaterialBomVersion materielBomVersion) {
		this.delete(materielBomVersion);
	}

	/**
	 * 根据报关常用工厂BOM料件Id返回报关常用工厂BOM料件
	 * 
	 * @param id
	 *            报关常用工厂BOM料件Id
	 * @return MaterialBomDetail 报关常用工厂BOM料件
	 */
	public MaterialBomDetail findMaterialBomDetailById(String id) {
		return (MaterialBomDetail) this.load(MaterialBomDetail.class, id);
	}

	/**
	 * 查找报关常用工厂物料里的成品资料，但是不在报关常用工厂BOM成品里查找
	 * 
	 * @return List 是Materiel型，报关常用工厂物料
	 */
	public List findNotInBomMasterMaterial() {
		return this.find(" select a from Materiel a left join fetch a.complex "
				+ " where a.id not in ( select b.materiel.id from "
				+ " MaterialBomMaster as b where b.company.id=? )"
				+ " and a.company.id=? and (a.scmCoi.coiProperty=? or "
				+ " (a.scmCoi.coiProperty=? and a.isOutsource=?))",
				new Object[] { CommonUtils.getCompany().getId(),
						CommonUtils.getCompany().getId(),
						MaterielType.FINISHED_PRODUCT,
						MaterielType.SEMI_FINISHED_PRODUCT, true });
	}

	/**
	 * 查找报关常用工厂物料里的成品资料，但是不在报关常用工厂BOM成品里查找
	 * 
	 * @return List 是Materiel型，报关常用工厂物料
	 */
	public List findNotInBomMasterMaterial2() {
		return this.find(" select a from Materiel a left join fetch a.complex "
				+ " where a.id not in ( select b.materiel.id from "
				+ " MaterialBomMaster as b where b.company.id=? )"
				+ " and a.company.id=? and (" + " (a.scmCoi.coiProperty=? ))",
				new Object[] { CommonUtils.getCompany().getId(),
						CommonUtils.getCompany().getId(),
						MaterielType.SEMI_FINISHED_PRODUCT });
	}// nd a.isOutsource=?

	/**
	 * 根据版本号查找报关常用工厂物料里的料件资料，但是不在报关常用工厂BOM料件里查找
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 是报关常用工厂物料型，Materiel
	 */
	public List findNotInBomDetailMaterial(MaterialBomVersion version) {
		return this.find(
				" select a from Materiel as a  left join fetch a.complex "
						+ "where a.id not in ( select b.materiel.id from "
						+ " MaterialBomDetail as b where b.company.id=? "
						+ " and b.version.id=? )"
						+ " and a.company.id=? and a.scmCoi.coiProperty=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						version.getId(), CommonUtils.getCompany().getId(),
						MaterielType.MATERIEL });
	}

	/**
	 * 根据版本号抓取不重复的报关常用工厂BOM版本id
	 * 
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	public List findMaterialBomVersionId(Integer version) {
		String hql = " select distinct a.id from MaterialBomVersion as a  "
				+ " where a.company.id=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		if (version != null) {
			hql += " and a.version=? ";
			parameters.add(version);
		}
		return this.findListNoCache(hql, parameters.toArray());
	}

	/**
	 * 根据报关常用工厂BOM版本Id批量更新报关常用工厂BOM料件的损耗率
	 * 
	 * @param versionId
	 *            版本号Id
	 * @param waste
	 *            损耗率
	 * @param isByUnitUsed
	 *            true为更新单耗，false为更新单项用量
	 */
	public void updateMaterialBomWaste(String versionId, Double waste,
			boolean isByUnitUsed) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "";
		if (isByUnitUsed) {
			parameters = new ArrayList<Object>();
			hql = " update MaterialBomDetail a "
					+ " set a.waste=?,a.unitWaste=(a.unitUsed*(1.0-?)) where a.company=? ";
			parameters.add(waste);
			parameters.add(waste);
			parameters.add(CommonUtils.getCompany());
		} else {
			parameters = new ArrayList<Object>();
			hql = " update MaterialBomDetail a "
					+ " set a.waste=?,a.unitUsed=(a.unitWaste/(1.0-?)) where a.company=? ";
			parameters.add(waste);
			parameters.add(waste);
			parameters.add(CommonUtils.getCompany());
		}
		if (versionId != null) {
			hql += " and a.version.id=? ";
			parameters.add(versionId);
		}
		this.batchUpdateOrDelete(hql, parameters.toArray());
	}

	/**
	 * 根据物料类型查找内部归并的四码
	 * 
	 * @param materialType
	 *            物料类型
	 * @return List 内部归并的四码
	 */
	public List findInnerMergeFourCodeByMaterialType(String materialType) {
		List list = this.find(
				" select distinct a.hsFourCode from InnerMergeData a "
						+ " where a.imrType=? and a.company.id=? "
						+ " order by a.hsFourCode ", new Object[] {
						materialType, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 根据四码查找内部归并里的物料对应的报关常用工厂物料
	 * 
	 * @param fourCode
	 *            四码
	 * @return List 内部归并里的物料对应的报关常用工厂物料
	 */
	public List findInnerMergeByFourNo(String fourCode) {
		List list = this.find(" select a.materiel from InnerMergeData a "
				+ " left join fetch a.materiel.complex "
				+ " where a.hsFourCode=? and a.company.id=? ", new Object[] {
				fourCode, CommonUtils.getCompany().getId() });
		return list;
	}

	public List findInnerMergedata() {
		List list = this.find(" select a.materiel from InnerMergeData a "
				+ " left join fetch a.materiel.complex "
				+ " where  a.company.id=? ", new Object[] { CommonUtils
				.getCompany().getId() });
		return list;
	}

	/**
	 * 根据物料类别属性查找物料类别
	 * 
	 * @param coiProperty
	 *            物料类别属性
	 * @return List 是ScmCoi型，物料类型
	 */
	public ScmCoi findScmCoiByProperty(String coiProperty) {
		List list = this.find("select a from ScmCoi a where a.coiProperty=? "
				+ " and a.company.id=? ", new Object[] { coiProperty,
				CommonUtils.getCompany().getId() });
		if (list.isEmpty()) {
			return null;
		} else {
			return (ScmCoi) list.get(0);
		}
	}

	/**
	 * 根据物料类别属性查找报关常用工厂物料
	 * 
	 * @param coiProperty
	 *            物料类别属性
	 * @return List 是Materiel型，报关常用工厂物料
	 */
	public List findMaterialByCoiProperty(String coiProperty) {
		List list = this.find(" select a from Materiel a "
				+ " left join fetch a.complex "
				+ " where a.scmCoi.coiProperty=? " + " and a.company.id=? ",
				new Object[] { coiProperty, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	public void saveValues(ParameterSet parameterSet) {
		this.saveOrUpdate(parameterSet);
	}

	/**
	 * 显示本币为美元的汇率表
	 * 
	 * @return
	 */
	public List findCurrRateByM() {
		return this.find("select a from CurrRate a where a.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示本币为美元的汇率表
	 * 
	 * @return
	 */
	public Double findCurrRateByMG() {
		List list = this
				.find("select a from CurrRate a where a.company.id = ? and a.curr.code = '502' and a.curr1.code = '110'",
						new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			CurrRate obj = (CurrRate) list.get(0);
			return Double.valueOf(obj.getRate() == null ? 0.0 : obj.getRate());
		}
		return Double.valueOf(1);
	}

	/**
	 * 查询三种模式报关单的数据
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param exchangeRateIsNull
	 * @return
	 */
	public List findAllCustoms(Date beginDate, Date endDate,
			Boolean exchangeRateIsNull) {
		List relist = new ArrayList();
		String[] strs = {
				" select a from CustomsDeclaration a where a.company.id = ? ",
				" select a from BcsCustomsDeclaration a where a.company.id = ? ",
				" select a from DzscCustomsDeclaration a where a.company.id = ? " };

		for (int i = 0; i < 3; i++) {
			List prara = new ArrayList();
			String hsql = strs[i];
			prara.add(CommonUtils.getCompany().getId());
			if (beginDate != null) {
				hsql += " and  a.declarationDate >=? ";
				prara.add(beginDate);
			}
			if (endDate != null) {
				hsql += "   and a.declarationDate <= ?     ";
				prara.add(endDate);
			}

			if (exchangeRateIsNull != null && exchangeRateIsNull) {
				hsql += "   and a.exchangeRate is  null   ";
			} else if (exchangeRateIsNull != null && !exchangeRateIsNull) {
				hsql += "   and a.exchangeRate is  not null   ";
			}
			relist.addAll(this.find(hsql, prara.toArray()));
		}
		return relist;
	}

	/**
	 * 显示所有合同数据
	 * 
	 * @param emsNo
	 * @return
	 */
	public List findAllEmsHeadH2k(String emsNo) {
		List lbcs = this
				.find("select a from Contract a where a.company.id = ?  and a.declareState=? and a.emsNo =? )",
						new Object[] { CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE, emsNo });
		if (lbcs.size() > 0) {
			return lbcs;
		}
		// -----------------------------------------------------------------------------------
		String isEmsH2kSend = this.getBpara(BcusParameter.EmsEdiH2kSend);
		if (isEmsH2kSend != null && "1".equals(isEmsH2kSend)) {
			List tlbcus = this
					.find("select a from EmsHeadH2k a where a.company.id = ?  and a.historyState= ? and a.emsNo =?  ",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false), emsNo });
			if (tlbcus.size() > 0) {
				return tlbcus;
			}

		} else {
			List lbcus = this
					.find("select a from EmsHeadH2k a where a.company.id = ?  and a.declareState=? and a.emsNo =? ",
							new Object[] { CommonUtils.getCompany().getId(),
									DeclareState.PROCESS_EXE, emsNo });
			if (lbcus.size() > 0) {
				return lbcus;
			}
		}
		// -----------------------------------------------------------------------------------
		List ldzsc = this
				.find("select a from DzscEmsPorHead a where a.company.id = ?   and a.declareState=? and a.emsNo =? ",
						new Object[] { CommonUtils.getCompany().getId(),
								DzscState.EXECUTE, emsNo });
		if (ldzsc.size() > 0) {
			return ldzsc;
		}
		// -----------------------------------------------------------------------------------
		return new ArrayList();

	}

	/**
	 * 显示本币为美元的汇率表
	 * 
	 * @param currcode
	 * @param curr
	 * @param date
	 * @return
	 */
	public Double findCurrRateByM(String currcode, String curr, Date date) {
		// List list = this
		// .find(
		// "select a from CurrRate a where a.company.id = ? "
		// + " and a.curr.code=? and a.curr1.code=? and a.createDate>= ?"
		// + "order by a.createDate desc ", new Object[] {
		// CommonUtils.getCompany().getId(), currcode,
		// curr, date });
		// if (list.size() != 0) {
		// CurrRate cr = (CurrRate) list.get(list.size() - 1);
		// return cr.getRate() == null ? 0.0 : cr.getRate();
		// } else {
		// List lists = this
		// .find(
		// "select a from CurrRate a where a.company.id = ? "
		// + " and a.curr.code=? and a.curr1.code=? and a.createDate < ?"
		// + "order by a.createDate desc ",
		// new Object[] { CommonUtils.getCompany().getId(),
		// currcode, curr, date });
		// if (lists.size() != 0) {
		// CurrRate cr = (CurrRate) lists.get(0);
		// return cr.getRate() == null ? 0.0 : cr.getRate();
		// } else {
		// List lit = this.find(
		// "select a from CurrRate a where a.company.id = ? "
		// + " and a.curr.code=? and a.curr1.code=? "
		// + "order by a.createDate desc ", new Object[] {
		// CommonUtils.getCompany().getId(), currcode,
		// curr });
		// if (lit.size() != 0) {
		// CurrRate cr = (CurrRate) lit.get(0);
		// return cr.getRate() == null ? 0.0 : cr.getRate();
		// } else {
		// return 0.0;
		// }
		// }
		//
		// }
		List list = this
				.find("select a from CurrRate a where a.company.id = ? "
						+ " and a.curr.code=? and a.curr1.code=?  and a.createDate<= ?"
						+ "order by a.createDate desc ",
						new Object[] { CommonUtils.getCompany().getId(),
								currcode, curr, date });
		if (list.size() != 0) {
			CurrRate cr = (CurrRate) list.get(0);
			// CurrRate cr = (CurrRate) list.get(list.size() - 1);
			return cr.getRate() == null ? 0.0 : cr.getRate();
		} else {
			return 0.0;
		}
	}

	/**
	 * 返回参数设定
	 * 
	 * @param type
	 * @return
	 */
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

	/**
	 * 返回合同或者电子帐册的所有数据
	 * 
	 * @return
	 */
	public List findAllEmsHeadH2k() {
		List lbcus = new ArrayList();
		List lbcs = this
				.find("select a from Contract a where a.company.id = ?  and a.declareState=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								DeclareState.PROCESS_EXE });

		String isEmsH2kSend = this.getBpara(BcusParameter.EmsEdiH2kSend);
		if (isEmsH2kSend != null && "1".equals(isEmsH2kSend)) {
			List tlbcus = this
					.find("select a from EmsHeadH2k a where a.company.id = ?  and a.historyState=? ",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false) });
			lbcus.add(tlbcus.get(0));
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

	/**
	 * 保存电子帐册报关单
	 * 
	 * @param obj
	 */
	public void saveCustoms(CustomsDeclaration obj) {
		this.saveOrUpdate(obj);
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
		String hsql = "select distinct a.materiel from MaterialBomDetail as a"
				+ " left join fetch a.materiel.complex "
				+ " left join fetch a.materiel.scmCoc"
				+ " left join fetch a.materiel.calUnit"
				+ " where a.materiel.company.id=? ";
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a.materiel." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a.materiel." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.materiel.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据了件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findMaterialBomByDetail(Materiel materiel) {
		return this.find("select a from MaterialBomDetail a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.materiel.complex "
				+ " left join fetch a.materiel.scmCoc"
				+ " left join fetch a.materiel.calUnit "
				+ " where a.company.id = ? and a.materiel.id=? ", new Object[] {
				CommonUtils.getCompany().getId(), materiel.getId() });
	}

	/**
	 * 根据 料号 查询BOM资料，引用该料号作为子件的 bom资料列表
	 * 
	 * @param ptNo
	 * @return
	 */
	public List<MaterialBomDetail> findMaterialBomDetailList(String ptNo) {
		return this.find("select a from MaterialBomDetail a "
				+ " left join fetch a.materiel "
				+ " where a.company.id = ? and a.materiel.id=? ", new Object[] {
				CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 查询工厂物料数据
	 * 
	 * @return
	 */
	public List<String> findMaterielNo() {
		return this.find(
				"select a.ptNo from EnterpriseMaterial a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 检查电子帐册归并关系是否禁用
	 * 
	 * @param ptNo
	 * @return
	 */
	public List findInnerMergeDataByptNoIsForbid(String ptNo) {
		return this.find("  select a from InnerMergeData a"
				+ " where a.company.id =? and  a.materiel.ptNo =?"
				+ " and a.isForbid=? ", new Object[] {
				CommonUtils.getCompany().getId(), ptNo, true });
	}

	/**
	 * 查询工厂物料数据
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findEnterpriseMaterialByptNo(String ptNo) {
		return this.find("  select a from EnterpriseMaterial a"
				+ " where a.company.id =? and  a.ptNo =? ", new Object[] {
				CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 查询报关常用物料数据
	 * 
	 * @param ptNo
	 *            料号
	 * @return
	 */
	public List findMaterialByptNo(String ptNo) {
		return this.find("  select a from Materiel a"
				+ " where a.company.id =? and  a.ptNo =? ", new Object[] {
				CommonUtils.getCompany().getId(), ptNo });
	}

	/**
	 * 查询BOM料件备案记录
	 * 
	 * @param versionApply
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetailApply，BOM料件备案记录
	 */
	public List findMaterialBomDetailApply(String ptNo, Integer versionNo) {
		String hql = "select a from MaterialBomDetailApply a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.versionApply "
				+ " left join fetch a.versionApply.bomMasterApply "
				+ " left join fetch a.versionApply.bomMasterApply.materiel "
				+ " where a.versionApply.bomMasterApply.materiel.ptNo=? "
				+ " and a.versionApply.version=? and a.company.id=? ";
		return this.find(hql, new Object[] { ptNo, versionNo,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据成品号查询BOM料件
	 * 
	 * @param ptno
	 * @return
	 */

	public List findMaterialBomByDetailByptno(String ptno, Integer version) {
		List list = this
				.find("select a from MaterialBomDetail a "
						+ "where a.version.master.materiel.ptNo = ? and a.company=? and a.version.version=?",
						new Object[] { ptno, CommonUtils.getCompany(), version });
		return list;
	}

	/**
	 * 根据成品号查询BOM料件
	 * 
	 * @param ptno
	 * @return
	 */
	public List findMaterialBomByDetailByptNoAndVersion(String ptno,
			Integer version) {

		String hsql = " select a from MaterialBomDetail a "
				+ " left join fetch a.version "
				+ " left join fetch a.materiel "
				+ " where a.version.master.materiel.ptNo = ? and a.company.id =? ";

		List list = new ArrayList();
		if (version != null) {
			hsql += " and a.version.version=? ";
			list = this.find(hsql, new Object[] { ptno,
					CommonUtils.getCompany().getId(), version });

		}

		else {
			hsql += " and a.version.version= (select max(c.version.version) from MaterialBomDetail c"
					+ " where c.version.master = a.version.master)";
			list = this.find(hsql, new Object[] { ptno,
					CommonUtils.getCompany().getId() });

		}

		return list;
	}

	/**
	 * 根据参数查找来料、三资设备是否存在 *
	 * 
	 * @param request
	 * @param emsNo
	 * @return
	 */
	public Boolean findAllFixByNo(String emsNo, Integer fixType) {
		if (fixType == FixType.SHANZHI) {
			List list = this.find("select a from DutyCertHead a "
					+ "where a.company.id=? and a.certNo=? ", new Object[] {
					CommonUtils.getCompany().getId(), emsNo });
			if (list.size() > 0)
				return true;
		} else {
			List list = this.find("select a from FixtureContract a "
					+ "where a.company.id=? and a.emsNo=? ", new Object[] {
					CommonUtils.getCompany().getId(), emsNo });
			if (list.size() > 0)
				return true;
		}
		return false;

	}

	/**
	 * ---------------以下为海关帐需要的方法,add by sls 2007/09/21
	 * 
	 * 查找报关常用物料里面的料号
	 */
	public List findPtNo() {
		return this.find(
				"select distinct a.ptNo from Materiel a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查询工厂物料并返回料号值
	 * 
	 * @return
	 */
	public List<String> findEnteriprisePtNo() {
		return this.find(
				"select a.ptNo from EnterpriseMaterial a where a.company= ? ",
				new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查询工厂计量单位并返回名称
	 * 
	 * @return
	 */
	public List findCalUnitName() {
		return this.find("select a.name from CalUnit a where a.company.id = ?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查询报关计量单位并返回名称
	 * 
	 * @return
	 */
	public List findUnitName() {
		return this.find("select a.name from Unit a ");
	}

	/**
	 * 查询报关计量单位返回所有值
	 * 
	 * @param sValue
	 * @return
	 */
	public Unit findUnitByNameFromUnit(String sValue) {
		if (sValue != null) {
			String hsql = "select a from Unit a where a.name=? and (a.isOut <> '1' or a.isOut is null)";
			List list = find(hsql, new Object[] { sValue });
			if (list != null && list.size() > 0) {
				return (Unit) list.get(0);
			}
		}
		return null;
	}

	/**
	 * 查询常用商品编码返回所有值
	 * 
	 * @param code
	 * @return
	 */
	public Complex findComplexByCode(String code) {
		List list = this.find("select a from Complex as a where a.code = ? ",
				new Object[] { code });
		if (list.size() > 0) {
			return (Complex) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * -------------- 以上为海关帐需要的方法,add by sls 2007/09/21
	 * 
	 * /** 获取是供应商
	 */
	public List findScmCocsIsWork() {
		return this.find(
				"select a from ScmCoc a where a.company.id=? and a.isWork=? ",
				new Object[] { CommonUtils.getCompany().getId(), true });
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
	 * 取得版本号
	 * 
	 * @param id
	 *            电子帐册成品id
	 * @return 没有被禁止的与指定的id匹配的电子帐册成品的版本号
	 */
	public List findVersionNo(Integer seqNum, Integer version) {
		if (getIsEmsH2kSend()) {
			return this.find(
					" select distinct a.emsHeadH2kVersion.version from EmsHeadH2kBom a  "
							+ " where a.emsHeadH2kVersion.version=?  "
							+ " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=?"
							+ " and a.sendState=?  ", new Object[] { version,
							seqNum, Integer.valueOf(SendState.SEND) });
		} else {
			return this.find(
					" select distinct a.emsHeadH2kVersion.version from EmsHeadH2kBom a  "
							+ " where a.emsHeadH2kVersion.version=? "
							+ " and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=?"
							+ " and a.modifyMark=? ", new Object[] { version,
							seqNum, ModifyMarkState.UNCHANGE });
		}

	}

	/**
	 * 查找商品版本是否禁用
	 * 
	 * @param type
	 *            物料类别
	 * @return
	 */
	public CommdityForbid findCommdityForbid(String type, String seqNum,
			String version) {
		List list = this.find("select distinct a from CommdityForbid a  "
				+ " where a.company.id= ?  and a.type=? "
				+ " and a.seqNum=? and a.version=? ", new Object[] {
				CommonUtils.getCompany().getId(), type, seqNum, version });
		if (!list.isEmpty())
			return (CommdityForbid) list.get(0);
		else
			return null;

	}

	/**
	 * 根据帐册序号返回限制商品
	 * 
	 * @param ImpExpType
	 * @param seqNum
	 * @return
	 */
	public RestirictCommodity findRerictCommodity(Boolean ImpExpType,
			String seqNum) {
		List list = null;
		if (ImpExpType) {
			list = this
					.find("select a from RestirictCommodity a where a.company.id=? and a.seqNum=? and a.types=?",
							new Object[] { CommonUtils.getCompany().getId(),
									seqNum, MaterielType.MATERIEL });
		} else {
			list = find(
					"select a from RestirictCommodity a where a.company.id=? and a.seqNum=? and a.types=? ",
					new Object[] { CommonUtils.getCompany().getId(), seqNum,
							MaterielType.FINISHED_PRODUCT });

		}
		if (!list.isEmpty())
			return (RestirictCommodity) list.get(0);
		else
			return null;
	}

	/**
	 * 返回报头单物料信息
	 * 
	 * @param isProduct
	 * @param seqNum
	 * @param CustomsDeclarationCommInfo
	 * @return
	 */
	public List findCustomsDeclarationCommInfo(Boolean isProduct,
			String seqNum,
			BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		hql = "select a from CustomsDeclarationCommInfo a where a.baseCustomsDeclaration.company.id=?  and a.commSerialNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Integer.valueOf(seqNum));

		if (isProduct) {
			hql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);

		} else {
			hql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);

		}
		if (CustomsDeclarationCommInfo != null) {
			hql += " and a.id<>? ";
			parameters.add(CustomsDeclarationCommInfo.getId());
		}
		return find(hql, parameters.toArray());
	}

	/**
	 * 返回报头单物料信息
	 * 
	 * @param isProduct
	 * @param seqNum
	 * @param CustomsDeclarationCommInfo
	 * @return
	 */
	public List findCustomsDeclarationCommInfos(Boolean isMaterial,
			List<Integer> seqNums,
			BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		hql = "select a.commSerialNo,count(a.commAmount) from CustomsDeclarationCommInfo a where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());

		if (isMaterial) {
			hql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);// DIRECT_IMPORT
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// TRANSFER_FACTORY_IMPORT
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);// BACK_MATERIEL_EXPORT
			// parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);//
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);// REMAIN_FORWARD_IMPORT
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);// MATERIAL_DOMESTIC_SALES
		} else {
			hql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);// DIRECT_EXPORT
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// TRANSFER_FACTORY_EXPORT
			parameters.add(ImpExpType.REWORK_EXPORT);// REWORK_EXPORT
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);// BACK_FACTORY_REWORK
			// parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);//

		}
		if (CustomsDeclarationCommInfo != null) {
			hql += " and a.id<>? ";
			parameters.add(CustomsDeclarationCommInfo.getId());
		}

		List rs = new ArrayList();
		List<Integer> tmpLs = seqNums;
		String ql;
		for (int i = 0; i < tmpLs.size(); i += 1000) {
			int max = i + 1000 > tmpLs.size() ? tmpLs.size() : i + 1000;
			ql = hql + " and  a.commSerialNo in ("
					+ StringUtils.join(tmpLs.subList(i, max).toArray(), ",")
					+ ") group by a.commSerialNo ";
			rs.addAll(find(ql, parameters.toArray()));
		}
		return rs;
	}

	/**
	 * 返回报头单归并前信息
	 * 
	 * @param isProduct
	 * @param seqNum
	 * @param types
	 * @return
	 */
	public Double findCustomsBeforeComInfo(Boolean isProduct, String seqNum,
			String types) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		List list = null;
		Integer typ = null;
		hql = "select sum(a.declaredAmount) from AtcMergeBeforeComInfo a where a.company.id=?  and a.serialNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Integer.valueOf(seqNum));
		// 如果是料件的话
		if (isProduct) {
			hql += " and a.afterComInfo.billList.impExpType in (?,?,?)";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		} else {// 如果是成品
			hql += " and a.afterComInfo.billList.impExpType in (?,?,?)";
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		list = find(hql, parameters.toArray());
		if (list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 根据加工公司名称查找海关注册公司
	 * 
	 * @param name
	 * @return
	 */
	public Brief findBrief(String name) {
		List list = this.find("select a from Brief a where a.name=? ",
				new Object[] { name });
		if (!list.isEmpty())
			return (Brief) list.get(0);
		else
			return null;
	}

	/**
	 * 查询工厂BOM管理的父级号料，子级号料与版本号
	 * 
	 * @return
	 */
	public List findEnterpriseBomManageptNoAndversion() {
		return this.find(
				"select a.parentNo,a.compentNo,a.versionNo from EnterpriseBomManage a "
						+ " where a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });
	}

	/**
	 * 获取电子帐册归并版本
	 * 
	 * @param request
	 * @return
	 */
	public List findVersion(AtcMergeBeforeComInfo beforeCommInfo) {
		// TODO Auto-generated method stub
		return this
				.find("select distinct a from EmsEdiMergerVersion a where a.company.id=? and a.emsEdiMergerBefore.ptNo=?"
						+ " and a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState=?"
						+ " and a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.modifyMark=?",
						new Object[] { CommonUtils.getCompany().getId(),
								beforeCommInfo.getMateriel().getPtNo(), "1",
								ModifyMarkState.UNCHANGE });
	}

	/**
	 * 根据料件查询工厂BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List<EnterpriseBomManage> findEnterpriseBomManageByMateriel(
			EnterpriseMaterial materiel) {
		// TODO Auto-generated method stub
		return this
				.find("from EnterpriseBomManage a where a.company.id=? and a.compentNo=? order by a.parentNo",
						new Object[] { CommonUtils.getCompany().getId(),
								materiel.getPtNo() });
	}

	/**
	 * 返回工厂一定范围里的料件
	 * 
	 * @param startPart
	 * @param endPart
	 * @param materielType
	 * @author 石小凯
	 * @return
	 */
	public List getResultMaterielAll(CasConvert casConvert) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from FactoryAndFactualCustomsRalation as a  "
				+ " where a.materiel.scmCoi.coiProperty=? and a.company.id=? ";
		paramters.add("2");
		paramters.add(CommonUtils.getCompany().getId());

		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 根据料件的料号查询单据中心的对应关系id数最大的
	 * 
	 * @param ptKeyNo
	 * @return
	 */
	public List getMaxUnitConvertFactoryAndFactualCustomsRalationByPtNo(
			String ptKeyNo) {
		List<Object> paramters = new ArrayList<Object>();// '0','0','2001',
		String hsql = "select a "
				+ "  from FactoryAndFactualCustomsRalation as a  "
				+ " left join a.statCusNameRelationHsn.cusUnit "
				+ " where a.materiel.scmCoi.coiProperty=? "
				+ " and a.company.id=? and a.materiel.ptNo=?  "
				+ " and a.id=(select max(b.id) from FactoryAndFactualCustomsRalation as b "
				+ " where b.materiel.scmCoi.coiProperty=?  and b.materiel.ptNo=? )";
		paramters.add("2");
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(ptKeyNo);
		paramters.add("2");
		paramters.add(ptKeyNo);
		return super.find(hsql, paramters.toArray());

	}

	/**
	 * 根据料件的料号查询单据中心的对应关系折算系数最大的
	 * 
	 * @param ptKeyNo
	 * @return
	 */
	public List getFactoryAndFactualCustomsRalationByPtNo(String ptKeyNo) {
		List<Object> paramters = new ArrayList<Object>();// '0','0','2001',
		String hsql = "select distinct a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec, a.statCusNameRelationHsn.cusUnit.name  "
				+ "  from FactoryAndFactualCustomsRalation as a  "
				+ " left join a.statCusNameRelationHsn.cusUnit "
				+ " where a.materiel.scmCoi.coiProperty=? "
				+ " and a.company.id=? and a.materiel.ptNo=?  ";
		paramters.add("2");
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(ptKeyNo);
		return super.find(hsql, paramters.toArray());

	}

	/**
	 * 根据料件的料号查询单据中心的对应关系id数最大的
	 * 
	 * @param ptKeyNo
	 * @return
	 */
	public List getMaxFactoryAndFactualCustomsRalationByPtNo(String ptKeyNo) {
		List<Object> paramters = new ArrayList<Object>();// '0','0','2001',
		// String hsql =
		// "select distinct a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec, a.statCusNameRelationHsn.cusUnit.name  "
		// + "  from FactoryAndFactualCustomsRalation as a  "
		// + " left join a.statCusNameRelationHsn.cusUnit "
		// + " where a.materiel.scmCoi.coiProperty=? "
		// + " and a.company.id=? and a.materiel.ptNo=?  "
		// +
		// " and a.id=(select max(b.id) from FactoryAndFactualCustomsRalation as b "
		// + " where b.materiel.scmCoi.coiProperty=?  and b.materiel.ptNo=? )";
		String hsql = "select distinct a.statCusNameRelationHsn.cusName,a.statCusNameRelationHsn.cusSpec, a.statCusNameRelationHsn.cusUnit.name  "
				+ "  from FactoryAndFactualCustomsRalation as a  "
				+ " left join a.statCusNameRelationHsn.cusUnit "
				+ " where a.materiel.scmCoi.coiProperty=? "
				+ " and a.company.id=? and a.materiel.ptNo=?  "
				+ " and a.id=(select max(b.id) from FactoryAndFactualCustomsRalation as b "
				+ " where b.materiel.scmCoi.coiProperty=?  and b.materiel.ptNo=? )";
		paramters.add("2");
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(ptKeyNo);
		paramters.add("2");
		paramters.add(ptKeyNo);
		return super.find(hsql, paramters.toArray());

	}

	/**
	 * 返回工厂一定范围里的料件
	 * 
	 * @param startPart
	 * @param endPart
	 * @param materielType
	 * @author 石小凯
	 * @return
	 */
	public List getResultMaterielFactory(CasConvert casConvert) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from FactoryAndFactualCustomsRalation as a  "
				+ " where a.materiel.scmCoi.coiProperty=? and a.company.id=? ";
		paramters.add("2");
		paramters.add(CommonUtils.getCompany().getId());
		if (!"".equals(casConvert.getStartPtNo())
				&& "".equals(casConvert.getEndPtNo())) {
			hsql += " and a.materiel.ptNo=? ";
			paramters.add(casConvert.getStartPtNo());
		}
		if (!"".equals(casConvert.getStartPtNo())
				&& !"".equals(casConvert.getEndPtNo())) {
			hsql += " and a.materiel.ptNo>=? and a.materiel.ptNo<=? ";
			paramters.add(casConvert.getStartPtNo());
			paramters.add(casConvert.getEndPtNo());
		}

		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 返回报关一定范围里的料件
	 * 
	 * @param startPart
	 * @param endPart
	 * @param materielType
	 * @author 石小凯
	 * @return
	 */
	public List getResultMaterielComplex(CasConvert casConvert) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from FactoryAndFactualCustomsRalation as a  "
				+ " where a.materiel.scmCoi.name=? and a.company.id=? ";
		paramters.add("料件");
		paramters.add(CommonUtils.getCompany().getId());
		if (!"".equals(casConvert.getStartHsPart())
				&& "".equals(casConvert.getEndHsPart())) {
			hsql += " and a.statCusNameRelationHsn.complex.code=? ";
			paramters.add(casConvert.getStartHsPart());
		}
		if (!"".equals(casConvert.getStartHsPart())
				&& !"".equals(casConvert.getEndHsPart())) {
			hsql += " and a.statCusNameRelationHsn.complex.code>=? and a.statCusNameRelationHsn.complex.code<=? ";
			paramters.add(casConvert.getStartHsPart());
			paramters.add(casConvert.getEndHsPart());
		}
		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 返回一定范围里的料件？？(选取 工厂资料 的筛选)
	 * 
	 * @param startPart
	 * @param endPart
	 * @param materielType
	 * @return
	 */
	public List getResultMaterielF(CasCondition casCondition) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from FactoryAndFactualCustomsRalation as a  "
				+ " where a.materiel.scmCoi.name=? and a.company.id=? ";
		paramters.add("料件");
		paramters.add(CommonUtils.getCompany().getId());
		if (!"".equals(casCondition.getStartPtPart())
				&& "".equals(casCondition.getEndPtPart())) {
			hsql += " and a.materiel.ptNo=? ";
			paramters.add(casCondition.getStartPtPart());
		}
		if (!"".equals(casCondition.getStartPtPart())
				&& !"".equals(casCondition.getEndPtPart())) {
			hsql += " and a.materiel.ptNo>=? and a.materiel.ptNo<=? ";
			paramters.add(casCondition.getStartPtPart());
			paramters.add(casCondition.getEndPtPart());
		}
		if (!"".equals(casCondition.getPtName())) {
			hsql += " and a.materiel.factoryName=? ";
			paramters.add(casCondition.getPtName());
		}
		if (!"".equals(casCondition.getPtSpec())) {
			hsql += " and a.materiel.factorySpec=? ";
			paramters.add(casCondition.getPtSpec());
		}
		// System.out.println("sql:"+hsql);
		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 返回一定范围里的料件 选取报关资料条件 筛选
	 * 
	 * @param startPart
	 * @param endPart
	 * @param materielType
	 * @return
	 */
	public List getResultMaterielC(CasCondition casCondition) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from FactoryAndFactualCustomsRalation as a  "
				+ " where a.materiel.scmCoi.name=? and a.company.id=? ";
		paramters.add("料件");
		paramters.add(CommonUtils.getCompany().getId());
		if (!"".equals(casCondition.getStartHsPart())
				&& "".equals(casCondition.getEndHsPart())) {
			hsql += " and a.statCusNameRelationHsn.complex.code=? ";
			paramters.add(casCondition.getStartHsPart());
		}
		if (!"".equals(casCondition.getStartHsPart())
				&& !"".equals(casCondition.getEndHsPart())) {
			hsql += " and a.statCusNameRelationHsn.complex.code>=? and a.statCusNameRelationHsn.complex.code<=? ";
			paramters.add(casCondition.getStartHsPart());
			paramters.add(casCondition.getEndHsPart());
		}
		if (!"".equals(casCondition.getHsName())) {
			hsql += " and a.statCusNameRelationHsn.cusName=? ";
			paramters.add(casCondition.getHsName());
		}
		if (!"".equals(casCondition.getHsSpec())) {
			hsql += " and a.statCusNameRelationHsn.cusSpec=? ";
			paramters.add(casCondition.getHsSpec());
		}
		// System.out.println("sql:"+hsql);
		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 依料件级条件查找 对应关系
	 * 
	 * @param conditions
	 * @return (公用方法，修改请通知)
	 */
	public List<FactoryAndFactualCustomsRalation> getSemiResultMateriel(
			List<Condition> conditions) {

		String hsql = "select a from FactoryAndFactualCustomsRalation as a  "
				+ " where a.company =?  "
				+ " and a.statCusNameRelationHsn.materielType = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		params.add(MaterielType.MATERIEL);
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
					hsql += " " + condition.getOperator() + " ";
				}
				if (condition.getValue() != null) {
					hsql += "? ";
					params.add(condition.getValue());
				}
				if (condition.getEndBracket() != null)
					hsql += condition.getEndBracket();
			}
		}

		hsql += " order by a.materiel.ptNo ";
		System.out.println("hsql=-----" + hsql);
		List result = this.find(hsql, params.toArray());
		return result;

	}

	/**
	 * 查找子级料号、父级料号不在物料主档里面的EnterpriseBomManage
	 * 
	 * @return
	 */
	public List checkEnterpriseBomManageIsRight(boolean isParent) {
		String hsql = null;
		if (isParent)
			hsql = "select a from  EnterpriseBomManage a where  a.parentNo not in("
					+ "select b.ptNo from EnterpriseMaterial b)";
		else
			hsql = "select a from  EnterpriseBomManage a where  a.compentNo not in("
					+ "select b.ptNo from EnterpriseMaterial b)";

		return this.find(hsql);
	}

	public List findlistByName(String tableName) {
		String hsql = " from " + tableName;
		return this.find(hsql);
	}

	/**
	 * 查找所有物料主档料号
	 */
	public List findAllPtNo() {
		String hsql = "select a.ptNo   from EnterpriseMaterial a";
		return this.find(hsql);
	}

	public List findMaterialBomVersionByPtNo(String ptNo) {
		String hsql = "select a  from MaterialBomVersion a where a.master.materiel.ptNo=? order by a.version";
		List<Object> params = new ArrayList<Object>();
		params.add(ptNo);

		return this.find(hsql, params.toArray());
	}

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersion(String ptNo, String cmpVersion) {
		List list = this.find(
				"select a.version from EmsEdiMergerVersion a where a.emsEdiMergerBefore.ptNo=?"
						+ "  and a.cmpVersion=? and a.company.id= ?",
				new Object[] { ptNo, cmpVersion,
						CommonUtils.getCompany().getId() });
		// =======当企业版本存在则返回海关版本
		if (list != null && list.size() > 0 && list.get(0) != null) {
			return (Integer) list.get(0);
		} else {
			return -1;
		}
		// else {
		// // ======当企业版本不存在则海关版本加1;
		// List listMaxVer = this
		// .find(
		// "select max(a.version) from EmsEdiMergerVersion a where a.emsEdiMergerBefore.ptNo=? and a.company.id= ?",
		// new Object[] { ptNo,
		// CommonUtils.getCompany().getId() });
		// if (listMaxVer != null && listMaxVer.size() > 0
		// && listMaxVer.get(0) != null) {
		// return (Integer) listMaxVer.get(0) + 1;
		// } else {
		// // ======当海关版本最大为空则企业版本为0
		// return 0;
		// }
		//
		// }
	}

	public List findPtNoAndSeqNumByMaterialId(List<String> idList) {

		if (idList == null && idList.size() == 0) {
			return null;
		}

		StringBuilder hql = new StringBuilder();
		hql.append(
				"select a.materiel.ptNo,a.hsAfterTenMemoNo from InnerMergeData a,EnterpriseMaterial b")
				.append(" where a.materiel.ptNo = b.ptNo and b.id in ('"
						+ idList.get(0));
		for (int i = 1; i < idList.size(); i++) {
			hql.append("','" + idList.get(i));
		}
		hql.append("')");

		return find(hql.toString());
	}

	/**
	 * 查询供应商
	 * 
	 * @return
	 */
	public List findScmCocIsManufacturer() {
		return this.find(
				"select a.id,a.code,a.name from ScmCoc a where a.isManufacturer =?  "
						+ " and a.company.id = ?", new Object[] {
						new Boolean(true), CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询客户
	 * 
	 * @return
	 */
	public List findScmCocIsCustomer() {
		return this.find(
				"select a.id,a.code,a.name from ScmCoc a where a.isCustomer =?  "
						+ " and a.company.id = ?", new Object[] {
						new Boolean(true), CommonUtils.getCompany().getId() });
	}

	public void updateMeterialInfo(List materielList) {
		this.batchSaveOrUpdate(materielList);
	}

	/**
	 * 显示所有工厂物料主档数据
	 * 
	 * @return
	 */
	public List<EnterpriseMaterial> findEnterpriseMaterials() {
		return this.find(
				"select a from EnterpriseMaterial a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 显示所有物料类别数据
	 * 
	 * @return
	 */
	public List<Materiel> findScmCoi() {
		return this.find("select a from ScmCoi a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 检查国内车牌是否存在
	 * @param homeCard
	 * @return
	 */
	public Boolean checkMotorCodeHomeCard(MotorCode motorCode){
		String hql = "select a.id from MotorCode a where (a.homeCard = ? or a.complex=?) and a.company.id= ?";
		List list = this.find(hql,new Object[]{motorCode.getHomeCard(),motorCode.getComplex(),CommonUtils.getCompany().getId()});
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
}
