/*
 * Created on 2005-3-29
 *
 * ODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractexe.entity.ImpExpGoodsBill;
import com.bestway.bcs.contractexe.entity.MakeBcsCustomsDeclaration;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.UnitCollate;

/**
 * com.bestway.bcs.contractexe.dao.BcsImpExpRequestDao
 * 
 * @author Administrator
 */
public class BcsImpExpRequestDao extends BaseDao {

	/**
	 * 根据正在执行的合同备案物料和报关商品资料，查找物料与报关对应表里对应的报关常用物料（只限成品、料件、半成品）
	 * 
	 * @param materielType
	 *            物料类别
	 * @param conditionSql
	 *            附件的SQL语句
	 * @param conditionParameter
	 *            conditionSql对应的参数
	 * @param index
	 *            开始数据的下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            property对应的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterielPutOnRecordsByBcsRequestBill(String materielType,
			String conditionSql, Object[] conditionParameter, int index,
			int length, String property, Object value, Boolean isLike) {
		String hsql = null;
		if (materielType.equals(MaterielType.MATERIEL)) {
			hsql = "select m from Materiel m left join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where  "
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo in ("
					+ "					select e.materiel.ptNo from BcsInnerMerge e "
					+ "						where e.materielType = ? "
					+ "						and e.company.id = ?  "
					+ "				and e.bcsTenInnerMerge.complex.code in "
					+ "( select a.complex.code from ContractImg a where a.contract.declareState = ? and e.company.id = ? ) ) ";

		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql = "select m from Materiel m left join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where  "
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo in ("
					+ "                 select e.materiel.ptNo from BcsInnerMerge e "
					+ "                     where e.materielType = ? "
					+ "                     and e.company.id = ?  "
					+ "             and e.bcsTenInnerMerge.complex.code in "
					+ "( select a.complex.code from ContractExg a where a.contract.declareState = ? and e.company.id = ? ) ) ";

		}
		List<Object> paramterList = new ArrayList<Object>();
		paramterList.add(materielType);
		paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(materielType);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(DeclareState.PROCESS_EXE);
		paramterList.add(CommonUtils.getCompany().getId());

		if (hsql == null || hsql.equals("")) {
			return null;
		}
		if (conditionSql == null || conditionSql.trim().equals("")) {
			conditionSql = "";
		} else {
			hsql += " " + conditionSql;
			if (conditionParameter != null) {
				for (int i = 0; i < conditionParameter.length; i++) {
					paramterList.add(conditionParameter[i]);
				}
			}
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  m." + property + " like ?  ";
				paramterList.add(value + "%");
			} else {
				hsql += " and  m." + property + " = ?  ";
				paramterList.add(value);
			}
		}
		Object[] paramters = paramterList.toArray();
		return this.findPageList(hsql, paramters, index, length);
	}

	/**
	 * 根据正在执行的合同备案物料和报关商品资料，查找物料与报关对应表里对应的报关常用物料（只限成品、料件、半成品）
	 * 
	 * @param materielType
	 *            物料类别
	 * @param conditionSql
	 *            附件的SQL语句
	 * @param conditionParameter
	 *            conditionSql对应的参数
	 * @param index
	 *            开始数据的下标 开始数据的下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            property对应的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterielNotPutOnRecordsByBcsRequestBill(
			String materielType, String conditionSql,
			Object[] conditionParameter, int index, int length,
			String property, Object value, Boolean isLike) {
		String hsql = null;
		if (materielType.equals(MaterielType.MATERIEL)) {
			hsql = "select m from Materiel m left join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where  "
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo not in ("
					+ "                 select e.materiel.ptNo from BcsInnerMerge e "
					+ "                     where e.materielType = ? "
					+ "                     and e.company.id = ?  "
					+ "             and e.bcsTenInnerMerge.complex.code in "
					+ "( select a.complex.code from ContractImg a where a.contract.declareState = ? and e.company.id = ? ) ) ";

		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql = "select m from Materiel m left join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where  "
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo not in ("
					+ "                 select e.materiel.ptNo from BcsInnerMerge e "
					+ "                     where e.materielType = ? "
					+ "                     and e.company.id = ?  "
					+ "             and e.bcsTenInnerMerge.complex.code in "
					+ "( select a.complex.code from ContractExg a where a.contract.declareState = ? and e.company.id = ? ) ) ";

		}
		List<Object> paramterList = new ArrayList<Object>();
		paramterList.add(materielType);
		paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(materielType);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(DeclareState.PROCESS_EXE);
		paramterList.add(CommonUtils.getCompany().getId());

		if (hsql == null || hsql.equals("")) {
			return null;
		}
		if (conditionSql == null || conditionSql.trim().equals("")) {
			conditionSql = "";
		} else {
			hsql += " " + conditionSql;
			if (conditionParameter != null) {
				for (int i = 0; i < conditionParameter.length; i++) {
					paramterList.add(conditionParameter[i]);
				}
			}
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  m." + property + " like ?  ";
				paramterList.add(value + "%");
			} else {
				hsql += " and  m." + property + " = ?  ";
				paramterList.add(value);
			}
		}
		Object[] paramters = paramterList.toArray();
		return this.findPageList(hsql, paramters, index, length);
	}

	/**
	 * 根据物料类别查找报关常用物料-->进出口报关申请单
	 * 
	 * @param materielType
	 *            物料类别
	 * @param conditionSql
	 *            附件的SQL语句
	 * @param conditionParameter
	 *            conditionSql对应的参数
	 * @param index
	 *            开始数据的下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            property对应的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是型，
	 */
	public List findMaterielByType(String materielType, String conditionSql,
			Object[] conditionParameter, int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramterList = new ArrayList<Object>();
		String hsql = "select b from Materiel b left join fetch b.calUnit"
				+ " left join fetch b.complex "
				+ " where ( b.scmCoi.coiProperty=? or b.scmCoi.coiProperty=? ) "
				+ " and b.company.id=?";
		paramterList.add(materielType);
		paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
		paramterList.add(CommonUtils.getCompany().getId());

		if (hsql == null || hsql.equals("")) {
			return null;
		}
		if (conditionSql == null || conditionSql.trim().equals("")) {
			conditionSql = "";
		} else {
			hsql += " " + conditionSql;
			if (conditionParameter != null) {
				for (int i = 0; i < conditionParameter.length; i++) {
					paramterList.add(conditionParameter[i]);
				}
			}
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  b." + property + " like ?  ";
				paramterList.add(value + "%");
			} else {
				hsql += " and  b." + property + " = ?  ";
				paramterList.add(value);
			}
		}
		return this.findPageList(hsql, paramterList.toArray(), index, length);
	}
	/**
	 * 根据物料类别查找物料与报关对应表中的报关常用物料-->进出口报关申请单(联盛)
	 * 
	 * @param materielType
	 *            物料类别
	 * @param conditionSql
	 *            附件的SQL语句
	 * @param conditionParameter
	 *            conditionSql对应的参数
	 * @param index
	 *            开始数据的下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            property对应的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是型，
	 */
	public List findMaterielByTypeLS(String materielType, String conditionSql,
			Object[] conditionParameter, int index, int length,
			String property, Object value, Boolean isLike) {
		List<Object> paramterList = new ArrayList<Object>();
		String hsql = "select b.materiel from BcsInnerMerge b left join fetch b.materiel.calUnit"
				+ " left join fetch b.materiel.complex "
				+ " where ( b.materiel.scmCoi.coiProperty=? or b.materiel.scmCoi.coiProperty=? ) "
				+ " and b.materiel.company.id=?";
		paramterList.add(materielType);
		paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
		paramterList.add(CommonUtils.getCompany().getId());

		if (hsql == null || hsql.equals("")) {
			return null;
		}
		if (conditionSql == null || conditionSql.trim().equals("")) {
			conditionSql = "";
		} else {
			hsql += " " + conditionSql;
			if (conditionParameter != null) {
				for (int i = 0; i < conditionParameter.length; i++) {
					paramterList.add(conditionParameter[i]);
				}
			}
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  b.materiel." + property + " like ?  ";
				paramterList.add(value + "%");
			} else {
				hsql += " and  b.materiel." + property + " = ?  ";
				paramterList.add(value);
			}
		}
		return this.findPageList(hsql, paramterList.toArray(), index, length);
	}

	/**
	 * 保存申请单转报关单中间表信息
	 * 
	 * @param m
	 *            是MakeBcsCustomsDeclaration型，申请单转报关单中间表信息
	 */
	public void saveMakeBcsCustomsDeclaration(MakeBcsCustomsDeclaration m) {
		this.saveOrUpdate(m);
	}

	/**
	 * 删除申请单转报关单中间表信息
	 * 
	 * @param m
	 *            是MakeBcsCustomsDeclaration型，申请单转报关单中间表信息
	 */
	public void deleteMakeBcsCustomsDeclaration(MakeBcsCustomsDeclaration m) {
		this.delete(m);
	}

	/**
	 * 根据报关单商品信息查找申请单转报关单中间表信息
	 * 
	 * @param id
	 *            报关单表头Id
	 * @return List 是MakeBcsCustomsDeclaration型，申请单转报关单中间表信息
	 */
	public List findMakeBcsCustomsDeclarationByCommInfo(String id) {
		return this.find(
				"select m from MakeBcsCustomsDeclaration m where m.company.id = ? "
						+ " and m.bcsCustomsDeclarationCommInfo.id = ?   ",
				new Object[] { CommonUtils.getCompany().getId(), id });
	}

	/**
	 * 查找在物料与报关对应表对应在报关商品资料存在，且正在执行的合同备案料件
	 * 
	 * @param ptNo
	 *            料号
	 * @param contractId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同备案料件
	 */
	public List findContractImgByPtNo(String ptNo, String contractId) {
		List<Object> paramterList = new ArrayList<Object>();
		String hsql = "select a from ContractImg a "
				+ "	where a.contract.declareState = ? "
				+ "   and a.contract.id = ? "
				+ "   and a.company.id = ? and "
				+ "  a.credenceNo in (select d.bcsTenInnerMerge.seqNum from BcsInnerMerge d "
				+ "						where d.materielType = ?         "
				+ "						and d.company.id = ?           "
				+ "						and d.materiel.ptNo = ? ) ";
		paramterList.add(DeclareState.PROCESS_EXE);
		paramterList.add(contractId);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(MaterielType.MATERIEL);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(ptNo);
		return this.find(hsql, paramterList.toArray());
	}

	/**
	 * 查找在物料与报关对应表对应在报关商品资料存在，且正在执行的合同备案成品
	 * 
	 * @param ptNo
	 *            料号
	 * @param contractId
	 *            合同表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByPtNo(String ptNo, String contractId) {
		List<Object> paramterList = new ArrayList<Object>();
		String hsql = "select a from ContractExg a "
				+ " where a.contract.declareState = ? "
				+ "   and a.contract.id = ? "
				+ "   and a.company.id = ? and "
				+ "  a.credenceNo in (select d.bcsTenInnerMerge.seqNum from BcsInnerMerge d "
				+ "                     where d.materielType = ?         "
				+ "                     and d.company.id = ?           "
				+ "                     and d.materiel.ptNo = ? ) ";
		paramterList.add(DeclareState.PROCESS_EXE);
		paramterList.add(contractId);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(MaterielType.FINISHED_PRODUCT);
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(ptNo);
		return this.find(hsql, paramterList.toArray());
	}

	public void saveImpExpGoodsBillList(List list) {
		for (int i = 0; i < list.size(); i++) {
			
			ImpExpGoodsBill m = (ImpExpGoodsBill) list.get(i);
			m.setCompany(CommonUtils.getCompany());
			this.saveOrUpdate(m);
		}
	}

	public void saveImpExpGoodsBillList(ImpExpGoodsBill obj) {
		this.saveOrUpdate(obj);
	}

	public List findImpExpGoodsBillAll(Boolean isLj, Boolean isTcustoms,
			Date beginDate, Date endDate) {
		List<Object> paramterList = new ArrayList<Object>();
		String sql = "select a from ImpExpGoodsBill a where a.company.id = ? and a.isLj = ?";
		paramterList.add(CommonUtils.getCompany().getId());
		paramterList.add(isLj);
		if (isTcustoms != null) {
			sql += " and a.isTcustoms = ?";
			paramterList.add(isTcustoms);
		}
		if (beginDate != null) {
			sql += " and (a.importDate>=?) ";
			paramterList.add(beginDate);
		}
		if (endDate != null) {
			sql += " and (a.importDate<=?) ";
			paramterList.add(endDate);
		}
		return this.find(sql, paramterList.toArray());
	}

	public List findImpExpGoodsBill(Boolean isLj) {
		if (isLj){//进口
			return this.find(
					"select distinct a.customsNo,a.contractNo,a.krNo,a.catNo  from ImpExpGoodsBill a where a.company.id = ? and a.isLj = ?"
							+ " and a.isTcustoms = ?", new Object[] {
							CommonUtils.getCompany().getId(), isLj,
							new Boolean(false) });
		} else {//出口
			return this.find(
					"select distinct a.catNo,a.contractNo,a.wrapType,a.countryOfLoadingOrUnloading,a.tradeMode," +
					"a.memo1,a.memo2,a.customs,a.portLoad,a.authorizeFile,a.containerNum,a.conveyance,a.transferMode,a.domeConveyance," +
					"a.declarationCustoms  " +
					"from ImpExpGoodsBill a where a.company.id = ? and a.isLj = ?"
							+ " and a.isTcustoms = ?", new Object[] {
							CommonUtils.getCompany().getId(), isLj,
							new Boolean(false) });
		}
		
	}
	
	
	public List findDistinctImpExpGoodsBill() {
		return this
				.find(
						"select distinct a.krNo from ImpExpGoodsBill a where a.company.id = ?",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findImpExpGoodsBillByCustomsNoForImg(String customsNo,
			String contractNo, String kr, String catNo) {
		return this
				.find(
						"select a from ImpExpGoodsBill a where a.company.id = ? and a.customsNo = ? and a.isLj = ? and a.isTcustoms = ? "
								+ " and a.contractNo = ? and a.krNo = ? and a.catNo = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								customsNo, new Boolean(true), new Boolean(false),
								contractNo, kr, catNo });
	}

	
	public List findImpExpGoodsBillByCustomsNoForExg(String catNo,
			String contractNo, String countryOfLoadingOrUnloading,String wrapType,
			String tradeMode,String memo1,String memo2,String customs,String portLoad,String authorizeFile,
			String containerNum,String conveyance,String transferMode,String domeConveyance, String declarationCustoms) {
		return this
				.find(
						"select a from ImpExpGoodsBill a where a.company.id = ? and a.catNo = ? and  a.isLj = ? and a.isTcustoms = ? "
								+ " and a.contractNo = ? and a.countryOfLoadingOrUnloading = ? and a.wrapType = ? " +
										" and a.tradeMode = ?  and a.memo1 = ? and a.memo2 = ? and a.customs = ? and " +
										" a.portLoad = ? and a.authorizeFile = ? and a.containerNum = ? and a.conveyance = ? and a.transferMode = ? " +
										" and a.domeConveyance = ? and a.declarationCustoms = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
										catNo, new Boolean(false), new Boolean(false),
								contractNo, countryOfLoadingOrUnloading, wrapType,tradeMode,
								memo1,memo2,customs,portLoad,authorizeFile,containerNum,conveyance,transferMode,
								domeConveyance,declarationCustoms});
	}
	
	
	
	public Country findCountryByName(String sname) {
		List ls = this.find("select a from Country a where a.code = ?",
				new Object[] { sname });
		if (ls != null && ls.size() > 0) {
			return (Country) ls.get(0);
		}
		return null;
	}
	
	

	public Country findCountryByName11(String sname) {
		List ls = this.find("select a from Country a where a.name = ?",
				new Object[] { sname });
		if (ls != null && ls.size() > 0) {
			return (Country) ls.get(0);
		}
		return null;
	}
	
	
	public Customs findCustomsByName(String name){
		List ls = this.find("select a from Customs a where a.name = ?",
				new Object[] { name });
		if (ls != null && ls.size() > 0) {
			return (Customs) ls.get(0);
		}
		return null;
	}
	
	public Curr findCurrByCode(String code){
		List ls = this.find("select a from Curr a where a.code = ?",
				new Object[] { code });
		if (ls != null && ls.size() > 0) {
			return (Curr) ls.get(0);
		}
		return null;
	}

	public LevyKind findLevyKindByCode(String code){
		List ls = this.find("select a from LevyKind a where a.code = ?",
				new Object[] { code });
		if (ls != null && ls.size() > 0) {
			return (LevyKind) ls.get(0);
		}
		return null;
	}
	
	public LevyMode findLevyModeByCode(String code){
		List ls = this.find("select a from LevyMode a where a.code = ?",
				new Object[] { code });
		if (ls != null && ls.size() > 0) {
			return (LevyMode) ls.get(0);
		}
		return null;
	}
	
	public Transf findTransfByName(String name){
		List ls = this.find("select a from Transf a where a.name = ?",
				new Object[] { name });
		if (ls != null && ls.size() > 0) {
			return (Transf) ls.get(0);
		}
		return null;
	}
	
	public Trade findTradeByName(String name){
		List ls = this.find("select a from Trade a where a.name = ?",
				new Object[] { name });
		if (ls != null && ls.size() > 0) {
			return (Trade) ls.get(0);
		}
		return null;
	}
	
	public PortLin findPortLinyName(String name){
		List ls = this.find("select a from PortLin a where a.name = ?",
				new Object[] { name });
		if (ls != null && ls.size() > 0) {
			return (PortLin) ls.get(0);
		}
		return null;
	}
	
	
	public Wrap findWrapByName(String name){
		List ls = this.find("select a from Wrap a where a.name = ?",
				new Object[] { name });
		if (ls != null && ls.size() > 0) {
			return (Wrap) ls.get(0);
		}
		return null;
	}
	
	public Transf findTransf(String name){
		List ls = this.find("select a from Transf a where a.name = ?",
				new Object[] { name });
		if (ls != null && ls.size() > 0) {
			return (Transf) ls.get(0);
		}
		return null;
	}
	
	public Double findUnitRate(String unitName, String unitName1) {
		if (unitName == null || "".equals(unitName) || unitName1 == null
				|| "".equals(unitName1)) {
			return null;
		}
		List list = this
				.find(
						"select a from UnitCollate a where a.unitName = ? and a.unitName1 = ? and a.company.id = ?",
						new Object[] { unitName, unitName1,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return ((UnitCollate) list.get(0)).getUnitRate();
		}
		return null;
	}

	public void DeleteImpExpGoodsBillList(List list) {
		this.deleteAll(list);
	}

	public Contract findContractByEmsNo(String emsNo) {
		List list = this
				.find(
						"select a from Contract a where a.company= ? and a.emsNo= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(), emsNo,
								DeclareState.PROCESS_EXE });
		if (list != null && list.size() > 0) {
			return (Contract) list.get(0);
		}
		return null;
	}

	
	public Contract findContractByimpContractNo(String impContractNo) {
		List list = this
				.find(
						"select a from Contract a where a.company= ? and a.impContractNo= ? and a.declareState=? ",
						new Object[] { CommonUtils.getCompany(), impContractNo,
								DeclareState.PROCESS_EXE });
		if (list != null && list.size() > 0) {
			return (Contract) list.get(0);
		}
		return null;
	}
	
	
	public List findExeContrac() {
		List list = this.find(
				" select a  from Contract a  where a.company= ? and a.declareState=? "
						+ " order by a.destroyDate  ", new Object[] {
						CommonUtils.getCompany(), DeclareState.PROCESS_EXE });

		return list;
	}

	public List findContractImgExg(String cid, int impExpFlag) {
		List para = new ArrayList();
		String hsql = " select a.credenceNo ,a.seqNum from  ";
		if (impExpFlag == ImpExpFlag.IMPORT) {
			hsql += " ContractImg ";
		} else {
			hsql += " ContractExg ";
		}
		hsql += "  a where a.company= ?   and a.contract.id = ? ";
		para.add(CommonUtils.getCompany());
		para.add(cid);
		List list = this.find(hsql, para.toArray());
		return list;
	}

	public List findBcsDictPorImgExgByNo(String corrEmsNo, int impExpFlag) {

		String hsql = " select a.innerMergeSeqNum , a.seqNum  from ";
		if (impExpFlag == ImpExpFlag.IMPORT) {
			hsql += " BcsDictPorImg  ";
		} else {
			hsql += " BcsDictPorExg ";
		}
		hsql += " a  where a.company.id=? "
				+ "   and a.dictPorHead.dictPorEmsNo=? "
				+ "   and a.dictPorHead.declareState=? ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany().getId(),
				corrEmsNo, DeclareState.PROCESS_EXE });
	}
	

	public ScmCoc findScmCocByCode(String code){
		List ls = this.find("select a from ScmCoc a where a.code = ? and a.company.id = ?",
				new Object[]{code,CommonUtils.getCompany().getId()});
		if (ls != null && ls.size() > 0) {
			return (ScmCoc) ls.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ScmCoc> findScmCocByName(String name){
		List<ScmCoc> ls = this.find("select a from ScmCoc a where a.name = ? and a.company.id = ?",
				new Object[]{name,CommonUtils.getCompany().getId()});
		return ls;
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ScmCoc> findScmCocByNameAndType(String name, boolean isManufacturer,boolean isCustomer){
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from ScmCoc a left join a.brief where a.name = ? and a.company.id = ? ";
		parameters.add(name);
		parameters.add(CommonUtils.getCompany().getId());
		if(isManufacturer){
			hql = hql + " AND a.isManufacturer = ? ";
			parameters.add(true);
		}
		if(isCustomer){
			hql = hql + " AND a.isCustomer = ? ";
			parameters.add(true);
		}
		List<ScmCoc> ls = this.find(hql,parameters.toArray());
		return ls;
	}
	
	/**
	 * 根据编码查询海关注册公司
	 * @param code
	 * @return
	 */
	public List findBriefByCode(String code){
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from Brief a where a.code = ? ";
		parameters.add(code);		
		return this.find(hql,parameters.toArray());
	}
	
	/**
	 * 根据名称查询海关注册公司
	 * @param code
	 * @return
	 */
	public List findBriefByName(String name){
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from Brief a where a.name = ? ";
		parameters.add(name);		
		return this.find(hql,parameters.toArray());
	}
	
	/**
	 * 主管海关
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Customs> findCustomsList(String name) {
		String hql = "SELECT c FROM Customs c WHERE c.name = ? ";
		return this.find(hql,new Object[]{name});
	}
	
	/**
	 * 转入地
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<District> findDistrictList(String name) {
		String hql = "SELECT c FROM District c WHERE c.name = ? ";
		return this.find(hql,new Object[]{name});
	}
	
	/**
	 * 查找客户
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Brief> findBriefsList(String name) {
		String hql = "SELECT c FROM Brief c WHERE c.name = ? ";
		return this.find(hql,new Object[]{name});
	}
	
	
	/**取得物料与报关对应表中料件
	 * @param isMaterial 是否料件
	 * @return
	 */
	public List getTempMaterielByTypeBcs(
			boolean isMaterial){
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from BcsInnerMerge a " +
				"where a.company.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		if (isMaterial) {
			hsql += " and a.materielType in (?) ";
			parameters.add(MaterielType.MATERIEL);
		} else {
			hsql += " and a.materielType in (?) ";
			parameters.add(MaterielType.FINISHED_PRODUCT);
		}
		hsql += " order by a.materiel.ptNo";
		List list = this.find(hsql, parameters.toArray());
		return this.find(hsql, parameters.toArray());
	}
}
