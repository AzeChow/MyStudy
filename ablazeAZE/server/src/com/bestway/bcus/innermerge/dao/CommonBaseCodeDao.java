package com.bestway.bcus.innermerge.dao;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.types.DataType;

import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.innermerge.entity.CustomInnerMergeCondition;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.InnerMergeDataOrder;
import com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.UnitCollate;
import com.bestway.jptds.client.common.CommonVars;
import com.lowagie.text.pdf.PRAcroForm;

public class CommonBaseCodeDao extends BaseDao {

	public List findCustomsComplex() {
		return this.find("select a from CustomsComplex a ");// 仓库设置显示所有数据
	}

	/**
	 * 物料主档显示所有数据来自物料主档的类别List
	 * 
	 * @param materielTypeList
	 * @return
	 */
	public List findMaterielNotInInner(List materielTypeList) {
		if (materielTypeList == null || materielTypeList.size() <= 0) {
			return null;
		}
		String hsql = "select m from Materiel m" + " left join fetch m.complex "
				+ " left join fetch m.scmCoc " + " where m.company.id= ? ";
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
		hsql = hsql + " and m.isNewMateriel = true ";// 导入新增得物料主档
		parameters.add(0, CommonUtils.getCompany().getId());
		return this.find(hsql, parameters.toArray());
	}

	public List findCurr() {
		return this.find("select a from Curr a ");
	}

	public List findMaterielAndHalfProductMap(Company company) {
		return this.find("select a from MaterielAndHalfProduct a where a.company.id= ?",
				company.getId());// 计量单位显示所有数据
	}

	/**
	 * 查询不在内部归并中存在的物料基本资料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterielNotInInnerMerge(String materielType) {
		return this.find("select a  from Materiel as a"
				+ " where a.company.id=? and a.scmCoi.coiProperty=? and"
				+ " a.id not in (select b.materiel.id from InnerMergeData as b "
				+ " where b.company.id=? and b.materiel is not null and b.imrType=?)",
				new Object[] { CommonUtils.getCompany().getId(), materielType,
						CommonUtils.getCompany().getId(), materielType });
	}

	public List findMateriel() { // 物料主档显示所有数据
		return this.find("select a from Materiel a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找物料主档来自类别-->备案 edit by xxm
	 */
	public List findMaterielByPutOnRecords(String materielType, String conditionSql,
			Object[] conditionParameter, EmsEdiMergerHead emsEdiMergerHead, EmsHeadH2k emsHeadH2k) {
		String hsql = null;
		if (materielType.equals(MaterielType.MATERIEL)) {
			hsql = "select m from Materiel m left outer join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where"
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo in (select e.materiel.ptNo from InnerMergeData e where e.hsAfterTenMemoNo in"
					+ " (select a.seqNum from EmsHeadH2kImg a where a.emsHeadH2k.id=?) and e.company.id = ?) ";
			/*
			 * hsql = "from Materiel m where " + " ( m.scmCoi.coiProperty=? or
			 * m.scmCoi.coiProperty=? ) " + " and m.company.id=? " + " and
			 * m.ptNo in (select e.ptNo from EmsEdiMergerImgBefore e where
			 * e.emsEdiMergerImgAfter.id in " + " (select a.id from
			 * EmsEdiMergerImgAfter a,EmsHeadH2kImg b where" + "
			 * a.emsEdiMergerHead.id = ? and a.seqNum=b.seqNum and
			 * b.emsHeadH2k.id=? ) and e.company.id = ? )";
			 */
		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql = "select m from Materiel m left outer join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where  "
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo in (select e.materiel.ptNo from InnerMergeData e where e.hsAfterTenMemoNo in"
					+ " (select a.seqNum from EmsHeadH2kExg a where a.emsHeadH2k.id=?) and e.company.id = ?) ";
			/*
			 * hsql = "from Materiel m where " + " ( m.scmCoi.coiProperty=? or
			 * m.scmCoi.coiProperty=? ) " + " and m.company.id=? " + " and
			 * m.ptNo in (select e.ptNo from EmsEdiMergerExgBefore e where
			 * e.emsEdiMergerExgAfter.id in " + " (select a.id from
			 * EmsEdiMergerExgAfter a,EmsHeadH2kExg b where" + "
			 * a.emsEdiMergerHead.id = ? and a.seqNum=b.seqNum and
			 * b.emsHeadH2k.id=? ) and e.company.id = ? )";
			 */
		}
		List<Object> paramterList = new ArrayList<Object>();
		paramterList.add(materielType);
		paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
		paramterList.add(CommonUtils.getCompany().getId());
		// paramterList.add(emsEdiMergerHead.getId());
		paramterList.add(emsHeadH2k.getId());
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
		Object[] paramters = paramterList.toArray();
		return this.find(hsql, paramters);
	}

	public List findInnerMergeData(String materielType) {
		return this
				.find(" select e.materiel.ptNo ,e.hsAfterTenMemoNo,e.hsAfterMaterielTenName,e.hsAfterMaterielTenSpec,c.name from InnerMergeData e "
						+ " left join  e.hsAfterMemoUnit c "
						+ " where e.company.id= ? and e.imrType=? and (e.isForbid is null or e.isForbid=?)",
						new Object[] { CommonUtils.getCompany().getId(), materielType,
								Boolean.valueOf(false) });

	}

	public List findEmsHeadH2kImgOrExg(EmsHeadH2k emsHeadH2k, String materielType) {
		String className = "";
		if (materielType.equals(MaterielType.MATERIEL)
				|| materielType.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			className = "EmsHeadH2kImg";
		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)
				|| materielType.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			className = "EmsHeadH2kExg";
		}
		if (className.equals("")) {
			return new ArrayList();
		}
		return this.find(" select a.seqNum from  " + className
				+ "  a where a.company.id=?  and a.emsHeadH2k.id=? ", new Object[] {
				CommonUtils.getCompany().getId(), emsHeadH2k.getId() });
	}

	public List findMateriel(String materielType) {
		return this.find("select m from Materiel m left outer join   m.complex"
				+ " left join  m.scmCoc" + " left join  m.calUnit" + " where  "
				+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)" + " and m.company.id=? ",
				new Object[] { materielType, MaterielType.SEMI_FINISHED_PRODUCT,
						CommonUtils.getCompany().getId() });
	}

	public List findImpExpCommodityInfoMId(String infoId) {
		return this.find(" select  m.id "
				+ " from ImpExpCommodityInfo c left join c.impExpRequestBill d "
				+ " left join c.materiel  m " + " where d.id = ?   and  c.company.id=? ",
				new Object[] { infoId, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找物料主档来自类别-->没有备案
	 */
	public List findMaterielByNotPutOnRecords(String materielType, String conditionSql,
			Object[] conditionParameter, EmsEdiMergerHead emsEdiMergerHead, EmsHeadH2k emsHeadH2k) {
		String hsql = null;
		if (materielType.equals(MaterielType.MATERIEL)) {
			hsql = "select m from Materiel m left outer join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where  "
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo not in (select e.materiel.ptNo from InnerMergeData e where e.hsAfterTenMemoNo in"
					+ " (select a.seqNum from EmsHeadH2kImg a where a.emsHeadH2k.id=?) and e.company.id = ?) ";
			/*
			 * hsql = "from Materiel m where " + " ( m.scmCoi.coiProperty=? or
			 * m.scmCoi.coiProperty=? ) " + " and m.company.id=? " + " and
			 * m.ptNo not in (select e.ptNo from EmsEdiMergerImgBefore e where
			 * e.emsEdiMergerImgAfter.id in " + " (select a.id from
			 * EmsEdiMergerImgAfter a,EmsHeadH2kImg b where" + "
			 * a.emsEdiMergerHead.id = ? and a.seqNum=b.seqNum and
			 * b.emsHeadH2k.id=? ) and e.company.id = ? )";
			 */
		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql = "select m from Materiel m left outer join fetch m.complex"
					+ " left join fetch m.scmCoc"
					+ " left join fetch m.calUnit"
					+ " where  "
					+ " (m.scmCoi.coiProperty=? or m.scmCoi.coiProperty=?)"
					+ " and m.company.id=? "
					+ " and m.ptNo not in (select e.materiel.ptNo from InnerMergeData e where e.hsAfterTenMemoNo in"
					+ " (select a.seqNum from EmsHeadH2kExg a where a.emsHeadH2k.id=?) and e.company.id = ?) ";
			/*
			 * hsql = "from Materiel m where " + " ( m.scmCoi.coiProperty=? or
			 * m.scmCoi.coiProperty=? ) " + " and m.company.id=? " + " and
			 * m.ptNo not in (select e.ptNo from EmsEdiMergerExgBefore e where
			 * e.emsEdiMergerExgAfter.id in " + " (select a.id from
			 * EmsEdiMergerExgAfter a,EmsHeadH2kExg b where" + "
			 * a.emsEdiMergerHead.id = ? and a.seqNum=b.seqNum and
			 * b.emsHeadH2k.id=? ) and e.company.id = ? )";
			 */
		}
		ArrayList<Object> paramterList = new ArrayList<Object>();
		paramterList.add(materielType);
		paramterList.add(MaterielType.SEMI_FINISHED_PRODUCT);
		paramterList.add(CommonUtils.getCompany().getId());
		// paramterList.add(emsEdiMergerHead.getId());
		paramterList.add(emsHeadH2k.getId());
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
		Object[] paramters = paramterList.toArray();
		return this.find(hsql, paramters);
	}

	public List findDistinctTenInner(String materielType) {
		return this
				.find("select distinct a.hsAfterTenMemoNo,a.hsAfterComplex.code,a.hsAfterMaterielTenName,a.hsAfterMaterielTenSpec,a.hsAfterMemoUnit.name,a.hsFourNo from InnerMergeData a "
						+ " left join a.hsAfterComplex "
						+ " left join a.hsAfterMemoUnit "
						+ "where a.imrType = ? and a.company.id = ? and a.hsAfterTenMemoNo is not null",
						new Object[] { materielType, CommonUtils.getCompany().getId() });
	}

	public List findDistinctFourceInner(String materielType) {
		return this.find(
				"select distinct a.hsFourNo,a.hsFourCode,a.hsFourMaterielName from InnerMergeData a "
						+ " where a.imrType = ? and a.company.id = ? and a.hsFourNo is not null",
				new Object[] { materielType, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找反向归并的物料是否存内部归并的物料
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataExistReverseMergeBeforeData(String type) {
		return this
				.find(" select a.id,a.materiel,a.hsAfterTenMemoNo from InnerMergeData as a left join a.materiel b "
						+ "  where a.imrType = ? and a.company.id=? and a.materiel is not null "
						+ " and b.ptNo not in(select b.ptNo from ReverseMergeBeforeData as a left join a.materiel b where a.reverseMergeTenData.reverseMergeFourData.imrType=? and a.company.id = ? ) order by b.ptNo",
						new Object[] { type, CommonUtils.getCompany().getId(), type,
								CommonUtils.getCompany().getId() });

	}

	/**
	 * 检查反向归并的10码是否存在归并关系中
	 */
	public List findInnerMergeDataExistReverseMergeTenData(String materielType) {
		return this
				.find("select distinct a.hsAfterTenMemoNo,a.hsAfterComplex.code,a.hsAfterMaterielTenName,a.hsAfterMaterielTenSpec,a.hsAfterMemoUnit.name,a.hsFourNo,a.hsFourCode,a.hsFourMaterielName from InnerMergeData a "
						+ " left join a.hsAfterComplex "
						+ " left join a.hsAfterMemoUnit "
						+ " where a.imrType = ? and a.company.id = ? and a.hsAfterTenMemoNo is not null"
						+ " and a.hsAfterTenMemoNo not in(select a.hsAfterTenMemoNo from ReverseMergeTenData as a where a.reverseMergeFourData.imrType=? and a.company.id = ? ) order by a.hsAfterTenMemoNo ",
						new Object[] { materielType, CommonUtils.getCompany().getId(),
								materielType, CommonUtils.getCompany().getId() });
	}

	/**
	 * 检查反向归并的4码是否存在归并关系中
	 */
	public List findInnerMergeDataExistReverseMergeFourData(String materielType) {
		return this
				.find("select distinct a.hsFourNo,a.hsFourCode,a.hsFourMaterielName from InnerMergeData a "
						+ " where a.imrType = ? and a.company.id = ? and a.hsFourNo is not null "
						+ "and a.hsFourNo not in(select a.hsFourNo from ReverseMergeFourData as a where a.imrType=? and a.company.id = ? ) order by a.hsFourNo",
						new Object[] { materielType, CommonUtils.getCompany().getId(),
								materielType, CommonUtils.getCompany().getId() });
	}

	/**
	 * 检测物料是否备案来自进出口商品信息
	 */
	public List checkMaterielPutOnRecordsByImpExpRequestCommodity(List dataSource,
			boolean isPutOnRecord) {
		List list = new ArrayList();
		String condition = "";
		for (int i = 0; i < dataSource.size(); i++) {
			ImpExpRequestBill data = (ImpExpRequestBill) dataSource.get(i);
			if (i == 0) {
				condition += " d.impExpRequestBill.id = ?  ";
			} else {
				condition += " or d.impExpRequestBill.id = ?  ";
			}
			list.add(data.getId());
		}
		list.add(CommonUtils.getCompany().getId());
		list.add(new Boolean(isPutOnRecord));
		return this.find("select d  from ImpExpCommodityInfo d " + "where (" + condition + " )"
				+ " and a.company.id=? and d.isPutOnRecord = ?", list.toArray());
	}

	/**
	 * 检测不在内部归并进出商品信息物料名称
	 */
	public List checkMaterielNotInMerge(List dataSource) {
		Object[] objs = new Object[dataSource.size() + 1];
		String condition = "";
		String suffix = "or ";
		for (int i = 0; i < dataSource.size(); i++) {
			ImpExpRequestBill data = (ImpExpRequestBill) dataSource.get(i);
			condition += " a.impExpRequestBill.id = ?  " + suffix;
			objs[i] = data.getId();
		}
		condition = condition.substring(0, condition.length() - suffix.length());
		objs[objs.length - 1] = CommonUtils.getCompany().getId();
		return this
				.find("select a.materiel from ImpExpCommodityInfo a left join fetch a.materiel.complex "
						+ " where a.materiel.id not in (select b.materiel.id from InnerMergeData b)  and ("
						+ condition + " )" + " and a.materiel.company.id=?", objs);
	}

	/*
	 * 内部归并
	 */
	public List findInnerMergeData() {
		return this.find("select a  from InnerMergeData as a " + " left join fetch a.materiel "
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit" + " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit" + " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit" + " where a.company.id= ?",
				CommonUtils.getCompany().getId());
	}

	/*
	 * 内部归并
	 */
	public List findMaterielFromInnerMergeData() {
		return this.find(" select a.materiel,a.hsAfterMemoUnit.name from InnerMergeData as a "
				+ " left join a.materiel " + " left join a.hsBeforeLegalUnit"
				+ " left join a.hsBeforeEnterpriseUnit" + " left join a.hsAfterComplex"
				+ " left join a.hsAfterMemoUnit" + " left join a.hsAfterLegalUnit"
				+ " left join a.hsAfterSecondLegalUnit" + " where a.company.id= ?", CommonUtils
				.getCompany().getId());
	}

	public List findDzscMaterielFromInnerMergeData() {
		return this.find(" select a.materiel,a.tenUnit.name from DzscInnerMergeData as a "
				+ " left join a.materiel " + " left join a.tenUnit" + " where a.company.id= ?",
				CommonUtils.getCompany().getId());
	}

	public InnerMergeData findInnerMergeDataById(String id) {
		List result = this.find(" select a from InnerMergeData as a left join fetch a.materiel "
				+ " where a.id = ? and a.company.id=?", new Object[] { id,
				CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (InnerMergeData) result.get(0);
		else
			return null;
	}

	public InnerMergeData findInnerMergeDataByMaterial(Materiel material) {
		List result = this.find(" select a from InnerMergeData as a left join fetch a.materiel "
				+ " where a.materiel.id = ? and a.company.id=?", new Object[] { material.getId(),
				CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (InnerMergeData) result.get(0);
		else
			return null;
	}

	public InnerMergeData findInnerMergeDataByPtNo(String ptNo) {
		List result = this.find(" select a from InnerMergeData as a left join fetch a.materiel "
				+ " where a.materiel.ptNo = ? and a.company.id=?", new Object[] { ptNo,
				CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (InnerMergeData) result.get(0);
		else
			return null;
	}

	public List findInnerMergeDataByPtNo1(String ptNo) {
		List result = this.find(" select a from InnerMergeData as a left join fetch a.materiel "
				+ " where a.materiel.ptNo = ? and a.company.id=?", new Object[] { ptNo,
				CommonUtils.getCompany().getId() });
		/*
		 * if (result.size() > 0) return result; else return "";
		 */
		return result;
	}

	public List findEmsEdiMergerImgAfter(EmsEdiMergerHead emsMerger, Integer seqNum) {
		System.out.println("emsMerger=" + emsMerger + "seqNum" + seqNum);
		return this
				.find("select a from EmsEdiMergerExgAfter a where a.emsEdiMergerHead.id=? and a.seqNum=? and a.company.id= ? order by a.seqNum",
						new Object[] { emsMerger.getId(), seqNum, CommonUtils.getCompany().getId() });
	}

	/**
	 * 海关帐表体新增
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataByType(String type) {
		return this.find(" select a from InnerMergeData as a left join fetch a.materiel "
				+ "  where a.imrType = ? and a.company.id=? ", new Object[] { type,
				CommonUtils.getCompany().getId() });

	}

	/**
	 * 查找内部归并的物料
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataByTypeMateriel(String type) {
		return this.find(" select a.id,a.materiel,a.hsAfterTenMemoNo from InnerMergeData as a  "
				+ "  where a.imrType = ? and a.company.id=? and a.hsAfterTenMemoNo is not null ",
				new Object[] { type, CommonUtils.getCompany().getId() });

	}

	/**
	 * 获得记录数据
	 * 
	 * @param type
	 * @return
	 */
	public int findInnerMergeDataCount(String type) {
		List list = this.find(" select count(a.id) from InnerMergeData as a "
				+ "  where a.imrType = ? and a.company.id=? ", new Object[] { type,
				CommonUtils.getCompany().getId() });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询十位 不为null的内部归并数据
	 * 
	 * @param type
	 * @return
	 */
	public List findTenIsnotNullInnerMergeDataByType(String type) {
		return this.find(" select a from InnerMergeData as a left join fetch a.materiel "
				+ "  where a.imrType = ? and a.company.id=? and a.hsAfterTenMemoNo is not null  "
				+ " order by a.hsAfterTenMemoNo asc", new Object[] { type,
				CommonUtils.getCompany().getId() });

	}

	/**
	 * 查询十位和4位编码不为null的内部归并数据
	 * 
	 * @param type
	 * @return
	 */
	public List findTenFourIsnotNullInnerMergeDataByType(String type) {
		return this
				.find(" select a from InnerMergeData as a left join fetch a.materiel "
						+ "  where a.imrType = ? and a.company.id=? and a.hsAfterTenMemoNo is not null and a.hsFourNo is not null "
						+ " order by a.hsFourNo asc,a.hsAfterTenMemoNo asc", new Object[] { type,
						CommonUtils.getCompany().getId() });

	}

	/**
	 * 查询十位编码为notnull和4位编码is null的内部归并数据
	 * 
	 * @param type
	 * @return
	 */
	public List findTenNotNullFourIsnullInnerMergeDataByType(String type) {
		return this
				.find(" select a from InnerMergeData as a left join fetch a.materiel "
						+ "  where a.imrType = ? and a.company.id=? and a.hsAfterTenMemoNo is not null and a.hsFourNo is null "
						+ " order by a.hsAfterTenMemoNo asc", new Object[] { type,
						CommonUtils.getCompany().getId() });

	}

	/**
	 * 查询十位和4位编码不为null的内部归并数据
	 * 
	 * @param type
	 * @return
	 */
	public List findTenFourIsNullInnerMergeDataByType(String type) {
		return this
				.find(" select a from InnerMergeData as a left join fetch a.materiel "
						+ "  where a.imrType = ? and a.company.id=? and a.hsAfterTenMemoNo is null and a.hsFourNo is null ",
						new Object[] { type, CommonUtils.getCompany().getId() });
	}

	public List findInnerMergeDataByTypeOrderby(String type) {
		return this.find(" select a from InnerMergeData as a left join fetch a.materiel "
				+ " where a.imrType = ? and a.company.id=? "
				+ " order by a.materiel.complex.code asc,"
				+ "a.hsAfterTenMemoNo desc,a.hsFourNo desc", new Object[] { type,
				CommonUtils.getCompany().getId() });
	}

	public int findTenInnerMergeNo(String type) {
		int no = 0;
		List list = this
				.find("select max(a.hsAfterTenMemoNo) from InnerMergeData a where a.imrType=? and  a.company.id=?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				no = 100000;
			}
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue();
		}
		if (no < 0) {
			no = 0;
		}
		return no;
	}

	public int findFourInnerMergeNo(String type) {
		int no = 0;
		List list = this
				.find("select max(a.hsFourNo) from InnerMergeData a where a.imrType=? and  a.company.id=?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				no = 100000;
			}
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue();
		}
		if (no < 0) {
			no = 0;
		}
		return no;
	}

	public void saveInnerMergeData(List<InnerMergeData> listInnerMergeData) {
		for (int i = 0; i < listInnerMergeData.size(); i++) {
			InnerMergeData data = listInnerMergeData.get(i);
			this.saveOrUpdate(data);
		}
		// for (InnerMergeData data : listInnerMergeData) {
		// this.saveOrUpdate(data);
		// }
	}

	public InnerMergeData saveInnerMergeData(InnerMergeData innerMergeData) {
		this.saveOrUpdate(innerMergeData);
		return innerMergeData;
	}

	public void deleteInnerMergeData(InnerMergeData innerMergeData) {
		this.delete(innerMergeData);
	}

	/***************************************************************************
	 * 经营范围--显示四码--来自内部归并
	 **************************************************************************/
	public List findInnerMergeDataByType4(String type, EmsEdiTrHead emsEdiTrHead, String helfType) { // 显示四码
		String tableName = "";
		if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) { // 获取半成品
			if (helfType.equals(MaterielType.MATERIEL)) {
				tableName = "EmsEdiTrImg";
			} else {
				tableName = "EmsEdiTrExg";
			}
		} else if (type.equals(MaterielType.MATERIEL)) {
			tableName = "EmsEdiTrImg";
		} else if (type.equals(MaterielType.FINISHED_PRODUCT)) {
			tableName = "EmsEdiTrExg";
		}
		return this
				.find("select distinct a.hsFourNo,a.hsFourCode,a.hsFourMaterielName"
						+ " from InnerMergeData a where a.imrType = ? and a.company.id=? and a.hsFourNo is not null"
						+ " and a.hsFourNo not in (select b.ptNo from " + tableName
						+ " b where b.emsEdiTrHead=?)", new Object[] { type,
						CommonUtils.getCompany().getId(), emsEdiTrHead });
	}

	/***************************************************************************
	 * 电子帐册使用--得到归并后料件成品--来自内部归并--通过经营范围四码筛选
	 **************************************************************************/
	public List findInnerMergeDataByType10ToEmsH2k(String type, EmsEdiTrHead emsEdiTrHead,
			EmsHeadH2k emsHeadH2k, String helfType) { // 显示十码
		String emsTrClass = "";
		String emsHeadClass = "";
		if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {// 半成品
			if (helfType.equals(MaterielType.MATERIEL)) {
				emsTrClass = "EmsEdiTrImg";
				emsHeadClass = "EmsHeadH2kImg";
			} else if (helfType.equals(MaterielType.FINISHED_PRODUCT)) {
				emsTrClass = "EmsEdiTrExg";
				emsHeadClass = "EmsHeadH2kExg";
			}
		} else if (type.equals(MaterielType.MATERIEL)) {// 料件
			emsTrClass = "EmsEdiTrImg";
			emsHeadClass = "EmsHeadH2kImg";
		} else if (type.equals(MaterielType.FINISHED_PRODUCT)) {// 成品
			emsTrClass = "EmsEdiTrExg";
			emsHeadClass = "EmsHeadH2kExg";
		}
		return this

				.find("select distinct a.hsAfterTenMemoNo,a.hsAfterComplex,a.hsAfterMaterielTenName,"
						+ "a.hsAfterMaterielTenSpec,a.hsAfterMemoUnit,a.hsAfterLegalUnit,a.hsAfterSecondLegalUnit,"
						+ " a.weigthUnitGene,a.isMainImg"
						+ " from InnerMergeData a "
						+ " left join a.hsAfterSecondLegalUnit"
						+ " left join a.hsAfterComplex"
						+ " left join a.hsAfterMemoUnit"
						+ " left join a.hsAfterLegalUnit"
						+ " where a.imrType = ? and a.company.id=?",
				// +
				// " and (a.isExistMerger = ? or a.isExistMerger is null)"
				// + " and  a.isExistMerger is null"
				// + " and a.hsFourNo in (select b.ptNo from "
				// + emsTrClass
				// + " b where b.emsEdiTrHead=? and b.modifyMark<>?)",
				// +
				// " and a.hsAfterTenMemoNo not in (select c.seqNum from "
				// + emsHeadClass + " c where c.emsHeadH2k=?)",
						new Object[] { type, CommonUtils.getCompany().getId() });
		// new Object[] { type, CommonUtils.getCompany().getId(),
		// emsEdiTrHead, ModifyMarkState.DELETED,
		// emsHeadH2k });
	}

	/***************************************************************************
	 * 归并关系使用--得到归并后料件成品--来自内部归并--通过经营范围四码筛选取掉禁用的
	 **************************************************************************/
	public List findInnerMergeDataByType10(String type, EmsEdiTrHead emsEdiTrHead,
			EmsEdiMergerHead emsEdiMergerHead, String helfType) { // 显示十码
		String emsTrClass = "";
		String emsMergerClass = "";
		if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			if (helfType.equals(MaterielType.MATERIEL)) {
				emsTrClass = "EmsEdiTrImg";
				emsMergerClass = "EmsEdiMergerImgAfter";
			} else if (helfType.equals(MaterielType.FINISHED_PRODUCT)) {
				emsTrClass = "EmsEdiTrExg";
				emsMergerClass = "EmsEdiMergerExgAfter";
			}
		} else if (type.equals(MaterielType.MATERIEL)) {
			emsTrClass = "EmsEdiTrImg";
			emsMergerClass = "EmsEdiMergerImgAfter";
		} else if (type.equals(MaterielType.FINISHED_PRODUCT)) {
			emsTrClass = "EmsEdiTrExg";
			emsMergerClass = "EmsEdiMergerExgAfter";
		}
		return this

				.find("select distinct a.hsAfterTenMemoNo,a.hsAfterComplex,a.hsAfterMaterielTenName,"
						+ "a.hsAfterMaterielTenSpec,a.hsAfterMemoUnit,a.hsAfterLegalUnit,a.hsAfterSecondLegalUnit,a.fristUnitRatio,a.secondUnitRatio, "
						+ " a.weigthUnitGene,a.isMainImg"
						+ " from InnerMergeData a "
						+ " left join a.hsAfterSecondLegalUnit"
						+ " left join a.hsAfterComplex"
						+ " left join a.hsAfterMemoUnit"
						+ " left join a.hsAfterLegalUnit"
						+ " where a.imrType = ? and a.company.id=? "
						+ " and a.hsFourNo in (select b.ptNo from "
						+ emsTrClass
						+ " b where b.emsEdiTrHead=? and b.modifyMark<>?)"
						+ " and a.hsAfterTenMemoNo not in (select c.seqNum from "
						+ emsMergerClass
						+ " c where c.emsEdiMergerHead=?)", new Object[] { type,
						CommonUtils.getCompany().getId(), emsEdiTrHead, ModifyMarkState.DELETED,
						emsEdiMergerHead });
	}

	/***************************************************************************
	 * 归并关系--根据归并后十码得到归并前料件--在内部归并表中
	 **************************************************************************/
	/*
	 * public List findInnerMergeDataAfterImgByType10(String type, Integer
	 * seqNum, EmsEdiMergerHead emsEdiMergerHead, int index, int length, String
	 * property, Object value, Boolean isLike) { List<Object> paramters = new
	 * ArrayList<Object>(); String hsql = "select a from InnerMergeData a where
	 * a.imrType=? and a.hsAfterTenMemoNo=? and a.company.id=? " + " and
	 * a.materiel.ptNo not in (select c.ptNo from EmsEdiMergerImgBefore c where
	 * c.seqNum=?" + " and c.emsEdiMergerImgAfter.emsEdiMergerHead=?)";
	 * paramters.add(type); paramters.add(seqNum);
	 * paramters.add(CommonUtils.getCompany().getId()); paramters.add(seqNum);
	 * paramters.add(emsEdiMergerHead); if (property != null &&
	 * !"".equals(property) && value != null) { if (isLike) { hsql += " and a."
	 * + property + " like ? "; paramters.add(value + "%"); } else { hsql += "
	 * and a." + property + " = ? "; paramters.add(value); } } return
	 * this.findPageList(hsql, paramters.toArray(), index, length); }
	 */

	/**
	 * 查找账册 归并关系归并前料件表
	 * 
	 * @author sxk
	 */
	public List findEmsEdiMergerImgBefore(EmsEdiMergerHead emsEdiMergerHead) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from EmsEdiMergerImgBefore a "
				+
				// " left outer join fetch a.emsEdiMergerImgAfter " +
				// " left outer join fetch a.emsEdiMergerImgAfter.emsEdiMergerHead "
				// +
				// " left outer join fetch a.complex " +
				// " left outer join fetch a.unit " +
				// " left outer join fetch a.curr " +
				" where a.company.id=? "
				+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.emsNo = ? order by a.ptNo";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId(), emsEdiMergerHead.getEmsNo() });
	}

	public List findEmsEdiMergerImgBeforeByPtNo(EmsEdiMergerHead emsEdiMergerHead, String ptNo) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from EmsEdiMergerImgBefore a  where a.company.id=? "
				+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.emsNo = ? " + " and a.ptNo =?";
		return this
				.find(hsql,
						new Object[] { CommonUtils.getCompany().getId(),
								emsEdiMergerHead.getEmsNo(), ptNo });
	}

	/***************************************************************************
	 * 归并关系--根据归并后十码得到归并前料件--在内部归并表中
	 **************************************************************************/
	public List findInnerMergeDataAfterImgByType10(String type, Integer seqNum,
			EmsEdiMergerHead emsEdiMergerHead, int index, int length, String property,
			Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from InnerMergeData a where a.imrType=? and a.hsAfterTenMemoNo=? "
				+ "  and a.company.id=? and (a.isForbid is null or a.isForbid=?) "
				+ " and (a.isExistMerger = ? or a.isExistMerger is null)";
		paramters.add(type);
		paramters.add(seqNum);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(new Boolean(false));
		paramters.add(new Boolean(false));
		if (property != null && "ptNo".equals(property)) {
			property = "materiel.ptNo";
		} else if (property != null && "name".equals(property)) {
			property = "materiel.factoryName";
		} else if (property != null && "spec".equals(property)) {
			property = "materiel.factorySpec";
		} else if (property != null && "seqNum".equals(property)) {
			property = "hsAfterTenMemoNo";
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
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/***************************************************************************
	 * 归并关系--根据归并后十码得到归并前成品--在内部归并表中
	 **************************************************************************/
	/*
	 * public List findInnerMergeDataAfterExgByType10(String type, Integer
	 * seqNum, EmsEdiMergerHead emsEdiMergerHead, int index, int length, String
	 * property, Object value, Boolean isLike) { List<Object> paramters = new
	 * ArrayList<Object>(); String hsql = "select a from InnerMergeData a where
	 * a.imrType=? and a.hsAfterTenMemoNo=? and a.company.id=? " + " and
	 * a.materiel.ptNo not in (select c.ptNo from EmsEdiMergerExgBefore c where
	 * c.seqNum=?" + " and c.emsEdiMergerExgAfter.emsEdiMergerHead=?)";
	 * paramters.add(type); paramters.add(seqNum);
	 * paramters.add(CommonUtils.getCompany().getId()); paramters.add(seqNum);
	 * paramters.add(emsEdiMergerHead); if (property != null &&
	 * !"".equals(property) && value != null) { if (isLike) { hsql += " and a."
	 * + property + " like ? "; paramters.add(value + "%"); } else { hsql += "
	 * and a." + property + " = ? "; paramters.add(value); } } return
	 * this.findPageList(hsql, paramters.toArray(), index, length); }
	 */

	/***************************************************************************
	 * 归并关系--根据归并后十码得到归并前成品--在内部归并表中
	 **************************************************************************/
	public List findInnerMergeDataAfterExgByType10(String type, Integer seqNum,
			EmsEdiMergerHead emsEdiMergerHead, int index, int length, String property,
			Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from InnerMergeData a where a.imrType=? and a.hsAfterTenMemoNo=?"
				+ "  and a.company.id=? and (a.isForbid is null or a.isForbid=?)"
				+ " and (a.isExistMerger = ? or a.isExistMerger is null)";
		paramters.add(type);
		paramters.add(seqNum);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(new Boolean(false));
		paramters.add(new Boolean(false));
		if (property != null && "ptNo".equals(property)) {
			property = "materiel.ptNo";
		} else if (property != null && "name".equals(property)) {
			property = "materiel.factoryName";
		} else if (property != null && "spec".equals(property)) {
			property = "materiel.factorySpec";
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
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	public void saveCustomInnerMergeCondition(CustomInnerMergeCondition condition) {
		this.saveOrUpdate(condition);
	}

	public void deleteCustomInnerMergeCondition(CustomInnerMergeCondition condition) {
		this.delete(condition);
	}

	public List findCustomInnerMergeConditionByType(String type) {
		return this
				.find("select a from CustomInnerMergeCondition a where a.materielType = ? and a.company.id=? ",
						new Object[] { type, CommonUtils.getCompany().getId() });
	}

	public List findCustomInnerMergeCondition() {
		return this.find("select a from CustomInnerMergeCondition a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public void deleteInnerMergeData(List selectedList) {
		for (int i = 0; i < selectedList.size(); i++) {
			this.delete(selectedList.get(i));
		}
	}

	// public List findMaterielByMaterielType(String materielType) {
	// return this
	// .find(
	// "select a from Materiel a where a.scmCoi.coiProperty = ? and
	// a.company.id=? ",
	// new Object[] { materielType,
	// CommonUtils.getCompany().getId() });
	// }

	// 显示
	public List findBomImg(String materielType) {
		return this.find("select a from BomImg a where a.company.id=? and a.materielType=?",
				new Object[] { CommonUtils.getCompany().getId(), materielType });
	}

	// 已备案的成品单耗,
	public List findBomAndInMerger(EmsEdiMergerHead head, String ptNo, String materielType) {
		return this
				.find("select a from BomImgDetail a where a.bomImg.ptNo=? and a.bomImg.materielType=? and a.company.id=? and "
						+ "a.ptNo in (select b.ptNo from EmsEdiMergerImgBefore b where b.emsEdiMergerImgAfter.emsEdiMergerHead = ?)",
						new Object[] { ptNo, materielType, CommonUtils.getCompany().getId(), head });
	}

	/**
	 * 查找反向归并4码数据
	 * 
	 * @return
	 */
	public List findReverseMergeFourData() {
		return this.find(" select a from ReverseMergeFourData as a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据物料类别查找反向归并4码数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List findReverseMergeFourDataByType(String materielType) {
		return this
				.find(" select a from ReverseMergeFourData as a where a.imrType=? and a.company.id=? order by a.hsFourNo ",
						new Object[] { materielType, CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据内部归并的4码备案号查询
	 * 
	 * @param materielType
	 * @return
	 */
	public ReverseMergeFourData findReverseMergeFourDataByHsFourNo(Integer hsFourNo,
			String hsFourCode, String hsFourSpec, String imrType) {
		String hsql = " select a from ReverseMergeFourData as a where a.company.id=? and a.imrType=? ";
		List<Object> list = new ArrayList<Object>();
		list.add(CommonUtils.getCompany().getId());
		list.add(imrType);
		if (hsFourNo != null) {
			hsql += " and a.hsFourNo=? ";
			list.add(hsFourNo);
		}
		if (hsFourCode != null && !"".equals(hsFourCode)) {
			hsql += " and a.hsFourCode=? ";
			list.add(hsFourCode);
		}
		if (hsFourSpec != null && !"".equals(hsFourSpec)) {
			hsql += " and a.hsFourMaterielName=?  ";
			list.add(hsFourSpec);
		}
		List ls = this.find(hsql, list.toArray());
		if (ls != null && ls.size() > 0) {
			return ((ReverseMergeFourData) ls.get(0));
		} else
			return null;
	}

	/**
	 * 根据内部归并的4码备案号查询
	 * 
	 * @param materielType
	 * @return
	 */
	public List findReverseMergeFourDataByHsFourNo1(Integer hsFourNO, String imrType) {
		return this
				.find(" select a from ReverseMergeFourData as a where a.hsFourNo=? and a.company.id=? and a.imrType=? order by a.hsFourNo ",
						new Object[] { hsFourNO, CommonUtils.getCompany().getId(), imrType });
	}

	/**
	 * 保存反向归并4码数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeFourData(ReverseMergeFourData data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 删除反向归并4码数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeFourData(ReverseMergeFourData data) {
		this.delete(data);
	}

	/**
	 * 根据凡响归并4码数据查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByFour(ReverseMergeFourData fourData) {
		return this
				.find("select a from ReverseMergeTenData as a"
						+ " where a.reverseMergeFourData.company.id=? and a.reverseMergeFourData.id=? order by a.hsAfterTenMemoNo",
						new Object[] { CommonUtils.getCompany().getId(), fourData.getId() });

	}

	/**
	 * 根据物料类别查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByType(String materielType) {
		return this
				.find("select a from ReverseMergeTenData as a"
						+ " where a.reverseMergeFourData.company.id=? and a.reverseMergeFourData.imrType=? order by a.hsAfterTenMemoNo ",
						new Object[] { CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 根据凡响归并4码数据查找反向归并10码数据的个数
	 * 
	 * @param fourData
	 * @return
	 */
	public int findReverseMergeTenDataCountByFour(ReverseMergeFourData fourData) {
		List list = this.find("select count(*) from ReverseMergeTenData as a"
				+ " where a.reverseMergeFourData.company.id=? and a.reverseMergeFourData.id=?",
				new Object[] { CommonUtils.getCompany().getId(), fourData.getId() });
		return ((Long) list.get(0)).intValue();
	}

	/**
	 * 保存反向归并10码数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeTenData(ReverseMergeTenData data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 删除反向归并10码数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeTenData(ReverseMergeTenData data) {
		this.delete(data);
	}

	/**
	 * 根据反向归并10码数据查找反向归并前数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeBeforeDataByTen(ReverseMergeTenData tenData) {
		return this.find("select a from ReverseMergeBeforeData as a left join fetch a.materiel "
				+ " where a.reverseMergeTenData.reverseMergeFourData.company.id=? "
				+ "and a.reverseMergeTenData.id=?", new Object[] {
				CommonUtils.getCompany().getId(), tenData.getId() });
	}

	/**
	 * 根据反向归并4码数据查找反向归并前数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeBeforeDataByFour(Integer fourData, String imrType) {
		return this
				.find("select a from ReverseMergeBeforeData as a left join fetch a.reverseMergeTenData "
						+ "left join fetch a.reverseMergeTenData.reverseMergeFourData "
						+ " where a.reverseMergeTenData.reverseMergeFourData.company.id=? "
						+ " and a.reverseMergeTenData.reverseMergeFourData.hsFourNo=? and a.reverseMergeTenData.reverseMergeFourData.imrType=? ",
						new Object[] { CommonUtils.getCompany().getId(), fourData, imrType });
	}

	/**
	 * 根据反向归并10码数据查找反向归并前数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeBeforeDataByTen(Integer tenData, String imrType) {
		return this
				.find("select a from ReverseMergeBeforeData as a left join fetch a.reverseMergeTenData "
						+ " where a.reverseMergeTenData.company.id=? "
						+ " and a.reverseMergeTenData.hsAfterTenMemoNo=? and a.reverseMergeTenData.reverseMergeFourData.imrType=? ",
						new Object[] { CommonUtils.getCompany().getId(), tenData, imrType });
	}

	/**
	 * 根据反向归并10码数据查找反向归并前数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByFour(Integer FourData, String imrType) {
		return this
				.find("select a from ReverseMergeTenData as a left join fetch a.reverseMergeFourData "
						+ " where a.reverseMergeFourData.company.id=? "
						+ " and a.reverseMergeFourData.hsFourNo=? and a.reverseMergeFourData.imrType=? ",
						new Object[] { CommonUtils.getCompany().getId(), FourData, imrType });
	}

	/**
	 * 根据反向归并10码数据查找反向归并前数据个数
	 * 
	 * @param fourData
	 * @return
	 */
	public int findReverseMergeBeforeDataCountByTen(ReverseMergeTenData tenData) {
		List list = this
				.find("select count(*) from ReverseMergeBeforeData as a  "
						+ " where a.reverseMergeTenData.reverseMergeFourData.company.id=? and a.reverseMergeTenData.id=?",
						new Object[] { CommonUtils.getCompany().getId(), tenData.getId() });
		return ((Long) list.get(0)).intValue();
	}

	/**
	 * 根据10商编找到法定单位
	 * 
	 * @param fourData
	 * @return
	 */
	public List findComplexLegalUnit(String code) {
		return this.find("select a from Complex a  where a.code=? ", new Object[] { code });
	}

	/**
	 * 根据内部归并数据查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeBeforeDataByInnerMergeData(InnerMergeData innerMergeData) {
		return this
				.find("select a from ReverseMergeBeforeData as a left join fetch a.materiel "
						+ " where a.reverseMergeTenData.reverseMergeFourData.company.id=? and a.innerMergeDataId=?",
						new Object[] { CommonUtils.getCompany().getId(), innerMergeData.getId() });
	}

	/**
	 * 根据物料类别查找反向归并物料数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeBeforeDataByMateriel(String imrType) {
		return this
				.find("select a from ReverseMergeBeforeData as a left join fetch a.materiel left join fetch a.materiel.scmCoc"
						+ " where a.reverseMergeTenData.reverseMergeFourData.company.id=? and a.materiel.scmCoi.coiProperty=?",
						new Object[] { CommonUtils.getCompany().getId(), imrType });
	}

	/**
	 * 根据物料类别查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public ReverseMergeTenData findReverseMergeTenDataByHsAfterTenMemoNo(Integer hsAfterTenMemoNo,
			String materielType) {
		List ls = this
				.find("select a from ReverseMergeTenData as a"
						+ " where a.reverseMergeFourData.company.id=? and a.reverseMergeFourData.imrType=? and a.hsAfterTenMemoNo=? order by a.hsAfterTenMemoNo   ",
						new Object[] { CommonUtils.getCompany().getId(), materielType,
								hsAfterTenMemoNo });
		if (ls != null && ls.size() > 0) {
			return (ReverseMergeTenData) ls.get(0);
		} else
			return null;
	}

	/**
	 * 根据物料类别查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByHsAfterTenMemoNo1(Integer hsAfterTenMemoNo,
			String materielType) {
		return this
				.find("select a from ReverseMergeTenData as a"
						+ " where a.reverseMergeFourData.company.id=? and a.reverseMergeFourData.imrType=? and a.hsAfterTenMemoNo=? order by a.hsAfterTenMemoNo   ",
						new Object[] { CommonUtils.getCompany().getId(), materielType,
								hsAfterTenMemoNo });
	}

	/**
	 * 保存反向归并10码数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeBeforeData(ReverseMergeBeforeData data) {
		this.saveOrUpdate(data);
	}

	/**
	 * 删除反向归并10码数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeBeforeData(ReverseMergeBeforeData data) {
		this.delete(data);
	}

	/**
	 * 查询未进行4码归并的内部归并数据
	 * 
	 * @param fourData
	 * @param materielType
	 * @return
	 */
	public List findTenDataNotFourMerge(ReverseMergeFourData fourData, String materielType) {
		return this.find(" select distinct a.hsAfterTenMemoNo,a.hsAfterComplex,"
				+ " a.hsAfterMaterielTenName,a.hsAfterMaterielTenSpec,a.hsAfterMemoUnit ,"
				+ " a.hsAfterLegalUnit,a.hsAfterSecondLegalUnit from InnerMergeData as a "
				+ " where a.company.id=? and a.hsAfterComplex.code like ? "
				+ " and a.imrType=? and a.hsFourNo is null", new Object[] {
				CommonUtils.getCompany().getId(), fourData.getHsFourCode() + "%", materielType });
	}

	/**
	 * 查询未进行10码归并的内部归并数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List findBeforeDataNotTenMerge(String materielType) {
		return this.find(" select a.id, a.materiel,a.hsBeforeLegalUnit,"
				+ " a.hsBeforeEnterpriseUnit from InnerMergeData as a " + " where a.company.id=? "
				+ " and a.imrType=? and a.hsAfterTenMemoNo is null ",
		// + " and a.materiel not in (select b.materiel from
		// ReverseMergeBeforeData as b "
		// + " where
		// b.reverseMergeTenData.reverseMergeFourData.company.id=?
		// )",
				new Object[] { CommonUtils.getCompany().getId(), materielType });
	}

	/**
	 * 根据种类，10位商品序号来查询内部归并数据
	 * 
	 * @param materielType
	 * @param tenMemoNo
	 * @return
	 */
	public List findInnerMergeDataByTypeAndTenMemoNo(String materielType, Integer tenMemoNo) {
		return this.find(" select a from InnerMergeData as a " + " left join fetch a.materiel "
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit" + " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit" + " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType=? and a.hsAfterTenMemoNo=? and a.company.id=? ", new Object[] {
				materielType, tenMemoNo, CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回反向归并10码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	public int findMaxReverseMergeTenNo(String type) {
		int no = 0;
		List list = this
				.find("select max(a.hsAfterTenMemoNo) from ReverseMergeTenData a "
						+ "where a.reverseMergeFourData.imrType=? and  a.reverseMergeFourData.company.id=?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
				no = 100000;
			}
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue();
		}
		if (no < 0) {
			no = 0;
		}
		return no;
	}

	/**
	 * 返回反向归并10码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	public int findMaxReverseMergeFourNo(String type) {
		int no = 0;
		List list = this
				.find("select max(a.hsFourNo) from ReverseMergeFourData a where a.imrType=? and  a.company.id=?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			// if (type.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			// no = 100000;
			// }
		} else {
			no = Integer.valueOf(list.get(0).toString()).intValue();
		}
		if (no < 0) {
			no = 0;
		}
		return no;
	}

	/**
	 * 查找所有前四码匹配的项目
	 * 
	 * @param fourCode
	 * @return
	 */

	public List findComplexByFourCode(String fourCode) {
		List list = this.find("select a from Complex a where a.code like ? ",
				new Object[] { fourCode + "%" });
		return list;
	}

	/**
	 * 判断内部归并数据是否做了经营范围的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public boolean findWhetherInnerMergeInEmsEdiTr(InnerMergeData innerMergeData) {
		List list = null;
		if (innerMergeData.getImrType().equals(MaterielType.MATERIEL)) {// 料件
			list = this.find("select a from EmsEdiTrImg as a"
					+ " where a.emsEdiTrHead.company.id=? and a.complex=? " + " and a.ptNo=? "
					+ " and a.emsEdiTrHead.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsFourCode(),
					innerMergeData.getHsFourNo(), new Boolean(false) });
		} else {
			list = this.find("select a from EmsEdiTrExg as a"
					+ " where a.emsEdiTrHead.company.id=? and a.complex=?" + " and a.ptNo=? "
					+ " and a.emsEdiTrHead.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsFourCode(),
					innerMergeData.getHsFourNo(), new Boolean(false) });
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断内部归并后数据是否做了电子备案的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public boolean findWhetherInnerMergeInEmsHeadH2k(InnerMergeData innerMergeData) {
		List list = null;
		if (innerMergeData.getImrType().equals(MaterielType.MATERIEL)) {
			list = this.find(" select a from EmsHeadH2kImg as a"
					+ " where a.emsHeadH2k.company.id=? and a.complex.code=?" + " and a.seqNum=? "
					+ " and a.emsHeadH2k.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsAfterComplex().getCode(),
					innerMergeData.getHsAfterTenMemoNo(), new Boolean(false) });
		} else {
			list = this.find(" select a from EmsHeadH2kExg as a"
					+ " where a.emsHeadH2k.company.id=? and a.complex.code=?" + " and a.seqNum=? "
					+ " and a.emsHeadH2k.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsAfterComplex().getCode(),
					innerMergeData.getHsAfterTenMemoNo(), new Boolean(false) });
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断反向归并数据是否做了电子备案的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public boolean findWhetherReverseMergeInEmsHeadH2k(ReverseMergeTenData reverseMergeTenData) {
		List list = null;
		if (reverseMergeTenData.getReverseMergeFourData().getImrType()
				.equals(MaterielType.MATERIEL)) {
			list = this.find(
					" select a from EmsHeadH2kImg as a"
							+ " where a.emsHeadH2k.company.id=? and a.complex.code=?"
							+ " and a.seqNum=? " + " and a.emsHeadH2k.historyState=?",
					new Object[] { CommonUtils.getCompany().getId(),
							reverseMergeTenData.getHsAfterComplex().getCode(),
							reverseMergeTenData.getHsAfterTenMemoNo(), new Boolean(false) });
		} else {
			list = this.find(
					" select a from EmsHeadH2kExg as a"
							+ " where a.emsHeadH2k.company.id=? and a.complex.code=?"
							+ " and a.seqNum=? " + " and a.emsHeadH2k.historyState=?",
					new Object[] { CommonUtils.getCompany().getId(),
							reverseMergeTenData.getHsAfterComplex().getCode(),
							reverseMergeTenData.getHsAfterTenMemoNo(), new Boolean(false) });
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isendtenInnerMerge(InnerMergeData innerMergeData) {
		List list = null;
		list = this.find(
				"select a  from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit"
						+ " where a.company.id= ? and a.hsAfterTenMemoNo = ? and a.imrType = ?",
				new Object[] { CommonUtils.getCompany().getId(),
						innerMergeData.getHsAfterTenMemoNo(), innerMergeData.getImrType() });
		if (list != null && list.size() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断内部归并后数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public boolean findWhetherInnerMergeInEmsEdiMergeAfter(InnerMergeData innerMergeData) {
		List list = null;
		if (innerMergeData.getImrType().equals(MaterielType.FINISHED_PRODUCT)) {
			list = this.find(" select a from EmsEdiMergerExgAfter as a"
					+ " where a.emsEdiMergerHead.company.id=? and a.complex.code=?"
					+ " and a.seqNum=? " + " and a.emsEdiMergerHead.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsAfterComplex().getCode(),
					innerMergeData.getHsAfterTenMemoNo(), new Boolean(false) });
		} else {
			list = this.find(" select a from EmsEdiMergerImgAfter as a"
					+ " where a.emsEdiMergerHead.company.id=? and a.complex.code=?"
					+ " and a.seqNum=? " + " and a.emsEdiMergerHead.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsAfterComplex().getCode(),
					innerMergeData.getHsAfterTenMemoNo(), new Boolean(false) });
		}
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断内部归并前数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public boolean findWhetherInnerMergeInEmsEdiMergeBefore(InnerMergeData innerMergeData) {
		List list = this.find(" select count(*) from EmsEdiMergerExgBefore as a"
				+ " where a.emsEdiMergerExgAfter.emsEdiMergerHead.company.id=? and a.ptNo=?"
				+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.historyState=?", new Object[] {
				CommonUtils.getCompany().getId(), innerMergeData.getMateriel().getPtNo(),
				new Boolean(false) });
		list.addAll(this

		.find(" select count(*) from EmsEdiMergerImgBefore as a"
				+ " where a.emsEdiMergerImgAfter.emsEdiMergerHead.company.id=? and a.ptNo=?"
				+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.historyState=?", new Object[] {
				CommonUtils.getCompany().getId(), innerMergeData.getMateriel().getPtNo(),
				new Boolean(false) }));
		if (((Long) list.get(0)).longValue() > 0 || ((Long) list.get(1)).longValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查找重排时,已存在的四位行号个数
	 * 
	 * @param materielType
	 * @param list
	 * @return
	 */
	public int findExistToFourCodeCount(String materielType, Set set) {
		String hsql = " select distinct a.hsFourNo from InnerMergeData as a "
				+ " where a.company.id=?  " + " and a.imrType=? ";
		String condition = "";
		List list = new ArrayList();
		list.add(CommonUtils.getCompany().getId());
		list.add(materielType);
		Object[] array = set.toArray();
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				condition += " and a.hsFourNo in (?";
			} else {
				condition += " ,? ";
			}
			if (i == array.length - 1) {
				condition += ")";
			}
			list.add(array[i]);
		}
		hsql += condition;
		List ls = this.find(hsql, list.toArray());
		if (ls == null || ls.size() <= 0) {
			return 0;
		} else {
			return ls.size();
		}
	}

	/**
	 * 查找重排时,已存在的十位行号个数
	 * 
	 * @param materielType
	 * @param list
	 * @return
	 */
	public int findExistToTenCodeCount(String materielType, Set set) {
		String hsql = " select distinct a.hsAfterTenMemoNo from InnerMergeData as a "
				+ " where a.company.id=?  " + " and a.imrType=? ";
		String condition = "";
		List<Object> list = new ArrayList<Object>();
		list.add(CommonUtils.getCompany().getId());
		list.add(materielType);
		Object[] array = set.toArray();
		for (int i = 0; i < array.length; i++) {
			if (i == 0) {
				condition += " and a.hsAfterTenMemoNo in (?";
			} else {
				condition += " ,? ";
			}
			if (i == array.length - 1) {
				condition += ")";
			}
			list.add(array[i]);
		}
		hsql += condition;
		List ls = this.find(hsql, list.toArray());
		if (ls == null || ls.size() <= 0) {
			return 0;
		} else {
			return ls.size();
		}
	}

	/**
	 * 查找内部归并数据块来自 两个四码归并序号之间
	 * 
	 * @param beginNo
	 * @param toNo
	 * @return
	 */
	public List findInnerMergeBlockByBetween(String materielType, int beginNo, int toNo) {
		return this.find(" select a from InnerMergeData as a "
				+ " where a.imrType=? and a.company.id=? and a.hsFourNo >= ?  and a.hsFourNo <= ?",
				new Object[] { materielType, CommonUtils.getCompany().getId(),
						new Integer(beginNo), new Integer(toNo) });
	}

	/**
	 * 查找内部归并数据块来自 四码归并序号
	 * 
	 * @param beginNo
	 * @param toNo
	 * @return
	 */
	public int findInnerMergeByFourNoCount(String materielType, int fourNo) {
		List list = this.find("select count(*) from InnerMergeData as a "
				+ " where a.imrType=? and a.company.id=? and a.hsFourNo = ? ", new Object[] {
				materielType, CommonUtils.getCompany().getId(), new Integer(fourNo) });
		if (list != null && !list.isEmpty()) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查找内部归并数据块来自 四码归并序号
	 * 
	 * @param beginNo
	 * @param toNo
	 * @return
	 */
	public List findInnerMergeByFourNo(String materielType, int fourNo) {
		return this.find(" select a from InnerMergeData as a " + " left join fetch a.materiel "
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit" + " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit" + " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType=? and a.company.id=? and a.hsFourNo = ? ", new Object[] {
				materielType, CommonUtils.getCompany().getId(), new Integer(fourNo) });
	}

	/**
	 * 判断料件经营范围的备案中是否有数据
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public boolean findHasEmsEdiTrExg() {
		List list = this.find("select count(*) from EmsEdiTrExg as a "
				+ " where a.emsEdiTrHead.company.id=? " + " and a.emsEdiTrHead.historyState=? ",
				new Object[] { CommonUtils.getCompany().getId(), new Boolean(false) });
		if (((Integer) list.get(0)).intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断成品经营范围的备案中是否有数据
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public boolean findHasEmsEdiTrImg() {
		List list = this.find("select count(*) from EmsEdiTrImg as a"
				+ " where a.emsEdiTrHead.company.id=? " + " and a.emsEdiTrHead.historyState=?",
				new Object[] { CommonUtils.getCompany().getId(), new Boolean(false) });
		if (((Integer) list.get(0)).intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List findMaterielWhereCp(String ptNo) { // 物料主档显示成品所有数据
		String hsql = "";
		if (ptNo.equals("")) {
			return this
					.find("select a from Materiel a where a.company.id= ? and a.scmCoi.coiProperty=?",
							new Object[] { CommonUtils.getCompany().getId(),
									MaterielType.FINISHED_PRODUCT });
		} else {
			return this
					.find("select a from Materiel a where a.company.id= ? and a.scmCoi.coiProperty=? and a.ptNo=?",
							new Object[] { CommonUtils.getCompany().getId(),
									MaterielType.FINISHED_PRODUCT, ptNo });
		}

	}

	/**
	 * 物流与归并对照表
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataToCollate(String type, String seqNum, String tenName) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.hsAfterTenMemoNo,a.hsAfterComplex.code,a.hsAfterMaterielTenName,a.hsAfterMaterielTenSpec,"
				+ " a.hsAfterMemoUnit.name from InnerMergeData as a "
				+ " left join  a.hsAfterComplex "
				+ " left join  a.hsAfterMemoUnit"
				+ " where a.imrType = ? and a.company.id=? ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (seqNum != null) {
			hsql += " and  a.hsAfterTenMemoNo = ?  ";
			paramters.add(Integer.valueOf(seqNum));
		}
		if (tenName != null) {
			hsql += " and  a.hsAfterMaterielTenName like ?  ";
			paramters.add("%" + tenName + "%");
		}
		hsql += " and a.hsAfterTenMemoNo is not null order by a.hsAfterTenMemoNo";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 修改内部归并的禁用
	 * 
	 * @param type
	 * @param ptNo
	 * @param isForbid
	 */
	public void updateInnerMergeDataIsForbid(String type, String materielId, boolean isForbid,
			String user) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " update InnerMergeData set isForbid=?  ";
		paramters.add(isForbid);
		if (isForbid) {
			hsql += " ,forbidDate=? ,forbidUser=? ";
		} else {
			hsql += " ,revertDate=? ,revertUser=? ";
		}
		paramters.add(new Date());
		paramters.add(user);
		hsql += " where imrType=? and company.id=? and materiel.id=? ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(materielId);
		this.batchUpdateOrDelete(hsql, paramters.toArray());
	}

	/**
	 * 查询全部禁用的归并关系
	 * 
	 * @param forBidBeginDate
	 * @param forBidEndData
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataIsForbid(Date forBidBeginDate, Date forBidEndData, String type,
			boolean isForbid) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel"
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit" + " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit" + " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType=? and a.company.id=? ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (!isForbid) {
			hsql += " and (a.isForbid=? or a.isForbid is null) ";
		} else {
			hsql += " and a.isForbid=? ";
		}
		paramters.add(isForbid);
		if (forBidBeginDate != null) {
			hsql += " and  a.forbidDate >=?  ";
			paramters.add(CommonUtils.getBeginDate(forBidBeginDate));
		}
		if (forBidEndData != null) {
			hsql += " and  a.forbidDate <=?  ";
			paramters.add(CommonUtils.getEndDate(forBidEndData));
		}
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 得到物料由十码
	 * 
	 * @param type
	 * @param tenNo
	 * @return
	 */
	public List findInnerMergeDataByTenNo(String type, String tenNo) {
		return this.find(" select a from InnerMergeData as a " + " left join fetch a.materiel"
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit" + " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit" + " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType=? and a.company.id=? and a.hsAfterTenMemoNo = ? ",
				new Object[] { type, CommonUtils.getCompany().getId(), new Integer(tenNo) });
	}

	// 得到物料由十码
	public List findInnerMergeDataByTenNoPtNo(String type, Integer tenNo, String ptNo) {
		return this
				.find(" select a from InnerMergeData as a "
						+ " left join fetch a.materiel"
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit"
						+ " where a.imrType=? and a.company.id=? and a.hsAfterTenMemoNo = ? and a.materiel.ptNo= ?",
						new Object[] { type, CommonUtils.getCompany().getId(), tenNo, ptNo });
	}
	
	public List findInnerMergeDataByTenNoPtNo0(String type, String ptNo) {
		return this
				.find(" select a from InnerMergeData as a "
						+ " left join fetch a.materiel"
						+ " left join fetch a.hsBeforeLegalUnit"
						+ " left join fetch a.hsBeforeEnterpriseUnit"
						+ " left join fetch a.hsAfterComplex"
						+ " left join fetch a.hsAfterMemoUnit"
						+ " left join fetch a.hsAfterLegalUnit"
						+ " left join fetch a.hsAfterSecondLegalUnit"
						+ " where a.imrType=? and a.company.id=? and a.materiel.ptNo= ?",
						new Object[] { type, CommonUtils.getCompany().getId(),ptNo });
	}

	private String fomatId(String id) {
		if (id == null) {
			return "";
		}
		return id;
	}

	// 显示pk单
	public List findPK() {
		return this.find("select a from ErpPk a where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
	}

	// 显示集装箱单
	public List findErpContainer() {
		return this.find("select a from ErpContainer a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	// 显示不重复的pk单据
	public List findDistinctPk() {
		return this.find("select distinct a.pkNo from ErpPk a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	// 显示pk单据由pk单号
	public List findpkForpkno(List list) {
		String sql = "select a from ErpPk a where a.company.id=? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany().getId());
		for (int i = 0; i < list.size(); i++) {
			String pkNo = list.get(i).toString();
			if (i == 0) {
				sql += " and a.pkNo = ?";
			} else {
				sql += " or a.pkNo = ?";
			}
			parameters.add(pkNo);
		}
		sql += " order by a.pkNo";
		return this.find(sql, parameters.toArray());

	}

	/**
	 * 查找不存在内部归并的物料来自料件类型
	 * 
	 * @param materielType
	 * @return
	 */
	// public List findMaterielNotInInnerMerge(String materielType, int index,
	// int length, String property, Object value, Boolean isLike) {
	// List<Object> paramters = new ArrayList<Object>();
	// String hsql = " from Materiel as a"
	// + " left join fetch a.complex "
	// + " left join fetch a.scmCoc"
	// + " left join fetch a.calUnit"
	// + " where a.scmCoi.coiProperty=? and a.company.id=? "
	// + " and a.id not in"
	// + "( select b.materiel.id from InnerMergeData b " +
	// " where b.imrType = ? and b.company.id=?) ";
	// paramters.add(materielType);
	// paramters.add(CommonUtils.getCompany().getId());
	// paramters.add(materielType);
	// paramters.add(CommonUtils.getCompany().getId());
	// if (property != null && !"".equals(property) && value != null) {
	// if (isLike) {
	// hsql += " and a." + property + " like ? ";
	// paramters.add(value + "%");
	// } else {
	// hsql += " and a." + property + " = ? ";
	// paramters.add(value);
	// }
	// }
	// hsql += " order by a.ptNo";
	// return this.findPageList(hsql, paramters.toArray(), index, length);
	// }
	/**
	 * 查找不存在内部归并的物料来自料件类型
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterielNotInInnerMerge(String materielType, int index, int length,
			String property, Object value, Boolean isLike, Boolean isNewMateriel) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from Materiel as a" + " left join fetch a.complex "
				+ " left join fetch a.scmCoc" + " left join fetch a.calUnit"
				+ " where a.scmCoi.coiProperty=? and a.company.id=? ";// 抓取新增得物料

		paramters.add(materielType);
		paramters.add(CommonUtils.getCompany().getId());
		if (isNewMateriel != null && isNewMateriel.equals(new Boolean(true))) {
			hsql += " and a.isNewMateriel = ? ";
			paramters.add(isNewMateriel);
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
		hsql += " order by a.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	public List findFactoryAndFactualCustomsRalation(String materielType) {
		return this.find("select distinct a.materiel.ptNo from FactoryAndFactualCustomsRalation a "
				+ "where a.statCusNameRelationHsn.materielType = ? and a.company.id=?",
				new Object[] { materielType, CommonUtils.getCompany().getId() });
	}

	/**
	 * 
	 * 
	 */
	public List findEmsEdiImgbefore(int index, int length, String property, Object value,
			Boolean isLike, Boolean isNewMateriel) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from EmsEdiMergerExgBom a,EmsEdiMergerVersion b,EmsEdiMergerExgBefore c "
				+ "where a.emsEdiMergerVersion =b.id and a.emsEdiMergerVersion.emsEdiMergerBefore=c.id"
				+ " and  a.company.id=? ";// 抓取新增得归
		paramters.add(CommonUtils.getCompany().getId());
		if (isNewMateriel != null && isNewMateriel.equals(new Boolean(true))) {
			hsql += " and a.isNewMateriel = ? ";
			paramters.add(isNewMateriel);
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
		hsql += " order by a.ptNo ";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	public List findEmsEdiBomImgbefore(int index, int length, String property, Object value,
			Boolean isLike, Boolean isNewMateriel) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.ptNo,a.name,a.spec,a.unit.name from EmsEdiMergerImgBefore a "
				+ " where  a.company.id=? ";// 抓取新增得归
		paramters.add(CommonUtils.getCompany().getId());
		if (isNewMateriel != null && isNewMateriel.equals(new Boolean(true))) {
			hsql += " and a.isNewMateriel = ? ";
			paramters.add(isNewMateriel);
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
		hsql += " order by a.ptNo ";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 获得内部归并分页的数据并排序
	 * 
	 * @param type
	 * @param index
	 * @param length
	 * @return
	 */
	public List findInnerMergeDataByType(String type, int index, int length, String property,
			Object value, boolean isLike, boolean isShowNoInner) {
		if (property != null && "isMerger".equals(property)) {
			property = "isExistMerger";
		}
		if (isShowNoInner) {
			List list = findInnerMergeDataByTypeNoTen(type, index, length, property, value, isLike);
			return list;
		} else {
			List returnList = new ArrayList();
			List listFourCode = new ArrayList();
			List listTenCode = new ArrayList();
			List listMateriel = new ArrayList();
			int hasFourCodeCount = 0;
			int hasTenCodeCount = 0;
			int fourRemainCount = 0;
			int tenRemainCount = 0;
			//
			// 找四码不为空的数据
			//
			List<Object> paramters = new ArrayList<Object>();

			String hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
					+ " left join fetch a.materiel.complex "
					+ " where a.imrType = ? and a.company.id=? " + " 	and a.hsFourNo is not null ";
			paramters.add(type);
			paramters.add(CommonUtils.getCompany().getId());
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  a." + property + " like '" + value + "%' ";
				} else {
					hsql += " and  a." + property + " = ?  ";                                                                                                          
					paramters.add(value);
				}
			}

			// if (0 == length) {
			// listFourCode = super.find(hsql, paramters.toArray());
			// } else {
			listFourCode = super.findPageList(hsql, paramters.toArray(), index, length);
			// }
			//
			// 获取四码的个数
			//
			hasFourCodeCount = this.findFourCodeCount(type, property, value, isLike);
			fourRemainCount = hasFourCodeCount % length;
			if (listFourCode.size() == 0) {
				//
				// 找四码为空十码不为空的数据
				//
				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is not null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				//
				// 当四码的大小等于0说明 如果 fourRemainCount > 0 的话肯定已经被用了一些数据了 index 应该等于
				//
				int tenCodeIndex = index - hasFourCodeCount;
				// if(0 == length){
				// listTenCode = super.find(hsql, paramters.toArray());
				// }else{

				listTenCode = super.findPageList(hsql, paramters.toArray(), tenCodeIndex, length);
				// }
				//
				// 获取十码的个数及余数
				//
				hasTenCodeCount = this.findTenCodeCount(type, property, value, isLike);
				//
				// 十码的余数=十码个数+四码的个数%lenght
				//
				tenRemainCount = (hasTenCodeCount + fourRemainCount) % length;

			} else if (listFourCode.size() < length) {
				//
				// 找四码为空十码不为空的数据
				//
				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is not null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				listTenCode = super.findPageList(hsql, paramters.toArray(), 0, length
						- (listFourCode.size()));
				//
				// 获取十码的个数及余数
				//
				hasTenCodeCount = this.findTenCodeCount(type, property, value, isLike);
				//
				// 十码的余数=十码个数+四码的个数%lenght
				//
				tenRemainCount = (hasTenCodeCount + fourRemainCount) % length;

			}
			if (listFourCode.size() + listTenCode.size() == 0) {
				//
				// 找四码和十码为空的数据
				//

				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				//
				// 当四码的大小等于0说明 如果 tenRemainCount > 0 的话肯定已经被用了一些数据了 index 应该等于
				//
				int materielIndex = 0;
				materielIndex = index - hasFourCodeCount - hasTenCodeCount;
				listMateriel = super.findPageList(hsql, paramters.toArray(), materielIndex, length);

			} else if (listFourCode.size() + listTenCode.size() < length) {
				//
				// 找四码和十码为空的数据
				//
				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				listMateriel = super.findPageList(hsql, paramters.toArray(), 0, length
						- (listFourCode.size() + listTenCode.size()));
			}
			returnList.addAll(listFourCode);
			returnList.addAll(listTenCode);
			returnList.addAll(listMateriel);
			return returnList;
		}
	}

	/**
	 * 获得内部归并分页的数据并排序
	 * 
	 * @param type
	 * @param index
	 * @param length
	 * @return
	 */
	public List findInnerMergeDataByTypeCount(String type, int index, int length, String property,
			Object value, boolean isLike, boolean isShowNoInner) {
		if (property != null && "isMerger".equals(property)) {
			property = "isExistMerger";
		}
		if (isShowNoInner) {
			List list = findInnerMergeDataByTypeNoTen(type, index, length, property, value, isLike);
			return list;
		} else {
			List returnList = new ArrayList();
			List listFourCode = new ArrayList();
			List listTenCode = new ArrayList();
			List listMateriel = new ArrayList();
			int hasFourCodeCount = 0;
			int hasTenCodeCount = 0;
			int fourRemainCount = 0;
			int tenRemainCount = 0;
			//
			// 找四码不为空的数据
			//
			List<Object> paramters = new ArrayList<Object>();

			String hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
					+ " left join fetch a.materiel.complex "
					+ " where a.imrType = ? and a.company.id=? " + " 	and a.hsFourNo is not null ";
			paramters.add(type);
			paramters.add(CommonUtils.getCompany().getId());
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  a." + property + " like '" + value + "%' ";
				} else {
					hsql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}

			if (0 == length) {
				listFourCode = super.find(hsql, paramters.toArray());
			} else {
				listFourCode = super.findPageList(hsql, paramters.toArray(), index, length);
			}
			//
			// 获取四码的个数
			//
			hasFourCodeCount = this.findFourCodeCount(type, property, value, isLike);
			
			if (0 == length) {
				fourRemainCount = hasFourCodeCount;
			} else {
				fourRemainCount = hasFourCodeCount % length;
			}
			
			if (listFourCode.size() == 0) {
				//
				// 找四码为空十码不为空的数据
				//
				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is not null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				//
				// 当四码的大小等于0说明 如果 fourRemainCount > 0 的话肯定已经被用了一些数据了 index 应该等于
				//
				int tenCodeIndex = index - hasFourCodeCount;
				if (0 == length) {
					listTenCode = super.find(hsql, paramters.toArray());
				} else {

					listTenCode = super.findPageList(hsql, paramters.toArray(), tenCodeIndex,
							length);
				}
				//
				// 获取十码的个数及余数
				//
				hasTenCodeCount = this.findTenCodeCount(type, property, value, isLike);
				//
				// 十码的余数=十码个数+四码的个数%lenght
				//
				if (0 == length) {
					tenRemainCount = (hasTenCodeCount + fourRemainCount);
				} else {
					tenRemainCount = (hasTenCodeCount + fourRemainCount) % length;
				}
			} else if (listFourCode.size() < length) {
				//
				// 找四码为空十码不为空的数据
				//
				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is not null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				if (0 == length) {
					listTenCode = super.find(hsql, paramters.toArray());
				} else {

					listTenCode = super.findPageList(hsql, paramters.toArray(), 0, length
							- (listFourCode.size()));
				}
				//
				// 获取十码的个数及余数
				//
				hasTenCodeCount = this.findTenCodeCount(type, property, value, isLike);
				//
				// 十码的余数=十码个数+四码的个数%lenght
				//
				
				if (0 == length) {
					tenRemainCount = (hasTenCodeCount + fourRemainCount);
				} else {
					tenRemainCount = (hasTenCodeCount + fourRemainCount) % length;
				}
				

			}
			if (listFourCode.size() + listTenCode.size() == 0) {
				//
				// 找四码和十码为空的数据
				//

				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				//
				// 当四码的大小等于0说明 如果 tenRemainCount > 0 的话肯定已经被用了一些数据了 index 应该等于
				//
				int materielIndex = 0;
				materielIndex = index - hasFourCodeCount - hasTenCodeCount;
				if (0 == length) {
					listMateriel = super.find(hsql, paramters.toArray());

				} else {

					listMateriel = super.findPageList(hsql, paramters.toArray(), materielIndex,
							length);
				}

			} else if (listFourCode.size() + listTenCode.size() < length) {
				//
				// 找四码和十码为空的数据
				//
				hsql = " select a from InnerMergeData as a " + " left join fetch a.materiel "
						+ " left join fetch a.materiel.complex "
						+ " where a.imrType = ? and a.company.id=?"
						+ " 	and a.hsAfterTenMemoNo is null and a.hsFourNo is null  ";
				paramters.clear();
				paramters.add(type);
				paramters.add(CommonUtils.getCompany().getId());
				if (property != null && !"".equals(property) && value != null) {
					if (isLike) {
						hsql += " and  a." + property + " like '" + value + "%' ";
					} else {
						hsql += " and  a." + property + " = ?  ";
						paramters.add(value);
					}
				}
				if (0 == length) {
					listMateriel = super.find(hsql, paramters.toArray());

				} else {

					listMateriel = super.findPageList(hsql, paramters.toArray(), 0, length
							- (listFourCode.size() + listTenCode.size()));
				}
			}
			returnList.addAll(listFourCode);
			returnList.addAll(listTenCode);
			returnList.addAll(listMateriel);
			return returnList;
		}
	}

	/**
	 * 未备案
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataByTypeNoMerger(String type) {

		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from InnerMergeData as a "
				 + " left join fetch a.materiel "
				// + " left join fetch a.hsBeforeLegalUnit"
				// + " left join fetch a.hsBeforeEnterpriseUnit"
				// + " left join fetch a.hsAfterComplex"
				// + " left join fetch a.hsAfterMemoUnit"
				// + " left join fetch a.hsAfterLegalUnit"
				// + " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType = ? and a.company.id=? "
				+ " and (a.isExistMerger = ? or a.isExistMerger is null) order by a.hsAfterTenMemoNo ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(false);
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 已备案
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataByTypeInMerger(String type) {

		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a  from InnerMergeData as a "
				// + " left join fetch a.materiel "
				// + " left join fetch a.hsBeforeLegalUnit"
				// + " left join fetch a.hsBeforeEnterpriseUnit"
				// + " left join fetch a.hsAfterComplex"
				// + " left join fetch a.hsAfterMemoUnit"
				// + " left join fetch a.hsAfterLegalUnit"
				// + " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType = ? and a.company.id=? "
				+ " and a.isExistMerger = ? order by a.hsAfterTenMemoNo";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(new Boolean(true));
		return this.find(hsql, paramters.toArray());
	}

	public List findInnerBySeqNum(String type, String seqNum) {
		return this.find("select a from InnerMergeData a " + "left join fetch a.materiel "
				+ "left join fetch a.materiel.complex " + "left join fetch a.hsAfterComplex "
				+ " where a.imrType = ? and a.company.id = ? and a.hsAfterTenMemoNo = ?",
				new Object[] { type, CommonUtils.getCompany().getId(), Integer.valueOf(seqNum) });
	}

	/**
	 * 分页查询未归并
	 * 
	 * @param type
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findInnerMergeDataByTypeNoTen(String type, int index, int length, String property,
			Object value, boolean isLike) {
		String hsql = "";
		List listMateriel = new ArrayList();
		List<Object> paramters = new ArrayList<Object>();
		hsql = "select a from InnerMergeData as a " + " left join fetch a.materiel "
				+ " left join fetch a.materiel.complex "
				+ " where a.imrType = ? and a.company.id=?"
				+ " 	and a.hsAfterTenMemoNo is null and a.hsFourNo is null  ";
		paramters.clear();
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%' ";
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		if (0 == length) {
			listMateriel = super.find(hsql, paramters.toArray());
		} else {
			listMateriel = super.findPageList(hsql, paramters.toArray(), index, length);
		}
		return listMateriel;
	}

	/**
	 * 分页查询未归并
	 * 
	 * @param type
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findInnerMergeDataByTypeNoTenCount(String type, int index, int length,
			String property, Object value, boolean isLike) {
		String hsql = "";
		List listMateriel = new ArrayList();
		List<Object> paramters = new ArrayList<Object>();
		hsql = "select a from InnerMergeData as a " + " left join fetch a.materiel "
				+ " left join fetch a.materiel.complex "
				+ " where a.imrType = ? and a.company.id=?"
				+ " 	and a.hsAfterTenMemoNo is null and a.hsFourNo is null  ";
		paramters.clear();
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%' ";
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		listMateriel = super.find(hsql, paramters.toArray());
		return listMateriel;
	}

	/**
	 * 查询未归并
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerByNoten(String type) {
		String hsql = "";
		List listMateriel = new ArrayList();
		List<Object> paramters = new ArrayList<Object>();
		hsql = "select a from InnerMergeData as a " + " left join fetch a.materiel "
				+ " left join fetch a.materiel.complex "
				+ " where a.imrType = ? and a.company.id=?"
				+ " 	and a.hsAfterTenMemoNo is null and a.hsFourNo is null  ";
		paramters.clear();
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 求当前条件下四码存在的个数
	 * 
	 * @param type
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	private int findFourCodeCount(String type, String property, Object value, boolean isLike) {
		int hasFourCodeCount = 0;
		//
		// 找四码不为空的数据
		//
		List<Object> paramters = new ArrayList<Object>();

		String hsql = "select count(*) from InnerMergeData as a "
				+ " where a.imrType = ? and a.company.id=? " + " 	and a.hsFourNo is not null ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%' ";
				// paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}

		//
		// 获取四码的个数
		//
		List listFourCodeCount = super.find(hsql, paramters.toArray());
		if (listFourCodeCount.size() > 0 && listFourCodeCount.get(0) != null) {
			hasFourCodeCount = Integer.parseInt(listFourCodeCount.get(0).toString());// (Integer)
																						// listFourCodeCount.get(0);
		}
		return hasFourCodeCount;
	}

	/**
	 * 求当前条件下十码存在的个数
	 * 
	 * @param type
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	private int findTenCodeCount(String type, String property, Object value, boolean isLike) {
		int hasTenCodeCount = 0;
		//
		// 找四码不为空的数据
		//
		List<Object> paramters = new ArrayList<Object>();

		//
		// 找四码为空十码不为空的数据
		//
		String hsql = "select count(*) from InnerMergeData as a "
				+ " where a.imrType = ? and a.company.id=?"
				+ " 	and a.hsAfterTenMemoNo is not null and a.hsFourNo is null  ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%' ";
				// paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		System.out.println("dsaaaaaaaaaaaaaaaa:" + hsql);
		//
		// 获取十码的个数及余数
		//
		List listTenCodeCount = super.find(hsql, paramters.toArray());
		if (listTenCodeCount.size() > 0 && listTenCodeCount.get(0) != null) {
			hasTenCodeCount = Integer.parseInt(listTenCodeCount.get(0).toString());// (Integer)
																					// listTenCodeCount.get(0);
		}
		return hasTenCodeCount;
	}

	public List findVersionByCpNo(String ptNo) {
		return this
				.find("select a from MaterialBomVersion a "
						+ " left outer join fetch a.master where a.master.materiel.ptNo = ? and a.company.id = ? order by a.version",
						new Object[] { ptNo, CommonUtils.getCompany().getId() });
	}

	public Date findMaxImportTimerByInnerMergeData(String type) {
		List list = this.find("select max(a.importTimer) from InnerMergeData a "
				+ " where a.imrType = ? and a.company.id=?", new Object[] { type,
				CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			return null;
		} else {
			Date updateDate = (Date) list.get(0);
			if (updateDate == null) {
				return null;
			}
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return java.sql.Date.valueOf(bartDateFormat.format(updateDate));
		}
	}

	public int findCountByInnerMergeData(String type) {
		List list = this.find("select count(a.id) from InnerMergeData a "
				+ " where a.imrType = ? and a.company.id=?", new Object[] { type,
				CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Integer.valueOf(list.get(0).toString()).intValue();
		}
	}

	public Materiel findMaterieByPtNo(String ptNo) {
		String hsql = " select a from Materiel as a" + " left join fetch a.complex "
				+ " left join fetch a.scmCoc" + " left join fetch a.calUnit"
				+ " where a.ptNo=? and a.company.id=? ";
		List list = this.find(hsql, new Object[] { ptNo, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (Materiel) list.get(0);
		}
		return null;
	}

	public Double findUnitRate(String unitName, String unitName1) {
		if (unitName == null || "".equals(unitName) || unitName1 == null || "".equals(unitName1)) {
			return null;
		}
		List list = this
				.find("select a from UnitCollate a where a.unitName = ? and a.unitName1 = ? and a.company.id = ?",
						new Object[] { unitName, unitName1, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return ((UnitCollate) list.get(0)).getUnitRate();
		}
		return null;
	}

	public Map findAllUnitRateMap() {
		Map map = new HashMap();
		List list = this.find("select a from UnitCollate a where  a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		for (int k = 0; k < list.size(); k++) {
			UnitCollate uc = (UnitCollate) list.get(k);
			String u = uc.getUnitName();
			String u1 = uc.getUnitName1();
			Double rate = uc.getUnitRate();
			String key = u + "+" + u1;
			map.put(key, rate);
		}
		return map;
	}

	public Date findLastUpdateDateByInnerMergeData(String type) {
		List list = this.find("select max(a.updateDate) from InnerMergeData a "
				+ " where a.imrType = ? and a.company.id=?", new Object[] { type,
				CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			return null;
		} else {
			Date updateDate = (Date) list.get(0);
			if (updateDate == null) {
				return null;
			}
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return java.sql.Date.valueOf(bartDateFormat.format(updateDate));
		}
	}

	public Date findLastInnestDateByInnerMergeData(String type) {
		List list = this.find("select max(a.importTimer) from InnerMergeData a "
				+ " where a.imrType = ? and a.company.id=?", new Object[] { type,
				CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null) {
			return null;
		} else {
			Date updateDate = (Date) list.get(0);
			if (updateDate == null) {
				return null;
			}
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return java.sql.Date.valueOf(bartDateFormat.format(updateDate));
		}
	}

	public List findUnit() {
		return this.find("select a from Unit a");
	}

	public List findInnerMergedataOrder() {
		return this.find("select a from InnerMergeDataOrder a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public void saveInnerOrder(InnerMergeDataOrder obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 根据类型查找归并关系管理的归并前资料
	 * 
	 * @param type
	 *            0为成品，2为料件
	 * @return
	 */
	public List findEmsEdiMergerExgImgBeforeData(String type) {
		String hsql;
		List<Object> paramters = new ArrayList<Object>();
		if (type.equals("0")) {
			hsql = "select a from EmsEdiMergerExgBefore a where a.company.id = ? "
					+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.declareState in (?,?)";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add("1");// 1，申请变更
			paramters.add("4");// 4:正在变更
		} else if (type.equals("2")) {
			hsql = "select a from EmsEdiMergerImgBefore a where a.company.id = ? "
					+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.declareState in (?,?)";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add("1");
			paramters.add("4");
		} else
			return new ArrayList();

		return find(hsql, paramters.toArray());
	}

	/**
	 * 根据类型查找归并关系管理的归并前资料
	 * 
	 * @param type
	 *            0为成品，2为料件
	 * @return
	 */
	public List findEmsEdiMergerExgImgAfterData(String type) {
		String hsql;
		List<Object> paramters = new ArrayList<Object>();
		if (type.equals("0")) {
			hsql = "select a from EmsEdiMergerExgAfter a where a.company.id = ? "
					+ " and a.emsEdiMergerHead.declareState in (?,?)";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add("1");
			paramters.add("4");
		} else if (type.equals("2")) {
			hsql = "select a from EmsEdiMergerImgAfter a where a.company.id = ? "
					+ " and a.emsEdiMergerHead.declareState in (?,?)";
			paramters.add(CommonUtils.getCompany().getId());
			paramters.add("1");
			paramters.add("4");
		} else
			return new ArrayList();

		return find(hsql, paramters.toArray());
	}

	/**
	 * 根据内部归并资料查找归并关系管理里的归并后资料
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findEmsEdiMergeAfterByInnerMerge(String materielType, InnerMergeData innerMergeData) {
		List list = null;
		if (materielType.equals("0")) {
			list = this.find(" select a from EmsEdiMergerExgAfter as a"
					+ " where a.company.id=? and a.complex.code=?" + " and a.seqNum=? "
					+ " and a.emsEdiMergerHead.declareState in (?,?) "
					+ " and a.emsEdiMergerHead.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsAfterComplex().getCode(),
					innerMergeData.getHsAfterTenMemoNo(), "1", "4", new Boolean(false) });

		} else if (materielType.equals("2")) {
			list = this.find(" select a from EmsEdiMergerImgAfter as a"
					+ " where a.company.id=? and a.complex.code=?" + " and a.seqNum=? "
					+ " and a.emsEdiMergerHead.declareState in (?,?) "
					+ " and a.emsEdiMergerHead.historyState=?", new Object[] {
					CommonUtils.getCompany().getId(), innerMergeData.getHsAfterComplex().getCode(),
					innerMergeData.getHsAfterTenMemoNo(), "1", "4", new Boolean(false) });
		}
		return list;
	}

	// 返回参数设定
	public String getBpara(int type) {
		List list = this.find(
				"select a from BcusParameter a where a.type = ? and a.company.id = ?",
				new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}

	// 得到物料由四码序号码
	public List findInnerMergeDataByFourNo(String type, String fourNo) {
		return this.find(" select a from InnerMergeData as a " + " left join fetch a.materiel"
				+ " left join fetch a.hsBeforeLegalUnit"
				+ " left join fetch a.hsBeforeEnterpriseUnit" + " left join fetch a.hsAfterComplex"
				+ " left join fetch a.hsAfterMemoUnit" + " left join fetch a.hsAfterLegalUnit"
				+ " left join fetch a.hsAfterSecondLegalUnit"
				+ " where a.imrType=? and a.company.id=? and a.hsFourNo = ? ", new Object[] { type,
				CommonUtils.getCompany().getId(), new Integer(fourNo) });
	}

	/**
	 * 得到物料由十码
	 * 
	 * @param type
	 * @param tenNo
	 * @return
	 */
	public List findInnerMergeDataByTenNoFromFile(String type, String tenNo) {
		return this.find(
				" select a.hsAfterComplex,a.hsAfterMaterielTenName,a.hsAfterMaterielTenSpec,a.hsAfterMemoUnit"
						+ "  from InnerMergeData as a "
						+ " where a.imrType=? and a.company.id=? and a.hsAfterTenMemoNo = ? ",
				new Object[] { type, CommonUtils.getCompany().getId(), new Integer(tenNo) });
	}

	/**
	 * 内部归并关系文本导入检查使用
	 * 
	 * @param type
	 * @param fourNo
	 * @return
	 */
	public List findInnerMergeDataByFourNoFromFile(String type, String fourNo) {
		return this.find(
				" select distinct a.hsFourCode,a.hsFourMaterielName from InnerMergeData as a "
						+ " where a.imrType=? and a.company.id=? and a.hsFourNo = ? ",
				new Object[] { type, CommonUtils.getCompany().getId(), new Integer(fourNo) });
	}

	public List findCheckupComplexByFlag(Integer impexpfalg) {
		List para = new ArrayList();
		String hsql = " select  a from CheckupComplex  a    where 1=1 ";
		if (impexpfalg != null) {
			hsql += " and a.impExpFlag = ? ";
			para.add(impexpfalg);
		}
		return this.find(hsql, para.toArray());
	}

	public List findComplexByFlag(Integer impexpfalg) {
		List para = new ArrayList();
		String hsql = " select  a.complex.code  from CheckupComplex  a    ";
		if (impexpfalg != null) {
			hsql += " where  a.impExpFlag = ? ";
			para.add(impexpfalg);
		}
		return this.find(hsql, para.toArray());
	}

	public String findCancelCusHeaddByDate(Date beginDate, Date endDate) {
		List para = new ArrayList();
		String hsql = " select  a.id  from CancelCusHead  a   where   "
				+ " a.endDate <= ?   and  a.company.id=?   order  by  a.endDate  DESC ";
		para.add(beginDate);
		para.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, para.toArray());
		if (list != null && (!list.isEmpty())) {
			return list.get(0).toString();
		}
		return "";
	}

	public Map findCancelImgByHeadId(String headIid) {
		Map map = new HashMap();
		String hsql = " select a.emsSeqNum, a.resultNum from CancelCusImgResult a "
				+ " where a.cancelHead.id=? ";
		List list = this.find(hsql, new Object[] { headIid });
		for (int i = 0; i < list.size(); i++) {
			Object[] result = (Object[]) list.get(i);
			map.put(result[0], result[1]);
		}
		return map;
	}

	public Map findCancelImgByHeadIdMoney(String headIid) {
		Map map = new HashMap();
		String hsql = " select a.emsSeqNum,sum(a.beginMoney) from CancelCusImgResult a "
				+ " where a.cancelHead.id=? group by a.emsSeqNum having sum(a.beginMoney)>0.0 ";
		List list = this.find(hsql, new Object[] { headIid });
		for (int i = 0; i < list.size(); i++) {
			Object[] result = (Object[]) list.get(i);
			if (Double.parseDouble(result[1].toString()) > 0.0) {
				map.put(Integer.parseInt(result[0].toString()),
						Double.parseDouble(result[1].toString()));
			}
		}
		return map;
	}

	public Map findCancelImgByHeadIdCmmAmount(String headIid) {
		Map map = new HashMap();
		String hsql = " select a.emsSeqNum,sum(a.beginNum) from CancelCusImgResult a "
				+ " where a.cancelHead.id=? group by a.emsSeqNum having sum(a.beginNum)>0.0";
		List list = this.find(hsql, new Object[] { headIid });
		for (int i = 0; i < list.size(); i++) {
			Object[] result = (Object[]) list.get(i);
			if (Double.parseDouble(result[1].toString()) > 0.0) {
				map.put(Integer.parseInt(result[0].toString()),
						Double.parseDouble(result[1].toString()));
			}
		}
		return map;
	}

	public List findInnerMergeInEmsEdiMergeOfUsed(Integer seqNum, String Type) {
		String hsql = "select a.id from CustomsDeclarationCommInfo a where a.commSerialNo=? "
				+ " and a.baseCustomsDeclaration.impExpType ";
		List<Object> parmaters = new ArrayList();
		parmaters.add(seqNum);
		if (Type.equals(MaterielType.MATERIEL)) {
			hsql += "in(?,?,?,?,?,?,?,?,?,?,?)";
			parmaters.add(ImpExpType.DIRECT_IMPORT);
			parmaters.add(1);
			parmaters.add(3);
			parmaters.add(6);
			parmaters.add(8);
			parmaters.add(9);
			parmaters.add(14);
			parmaters.add(17);
			parmaters.add(18);
			parmaters.add(19);
			parmaters.add(21);
		}
		if (Type.equals(MaterielType.FINISHED_PRODUCT)) {
			hsql += "in(?,?,?,?,?,?,?,?,?)";
			parmaters.add(2);
			parmaters.add(4);
			parmaters.add(5);
			parmaters.add(7);
			parmaters.add(10);
			parmaters.add(25);
			parmaters.add(26);
			parmaters.add(27);
			parmaters.add(28);
		}
		List list = this.find(hsql, parmaters.toArray());
		if (list == null)
			list = new ArrayList();
		return list;
	}

	public List findInnerMergeInEmsEdiMergeBeforeOfUsed(InnerMergeData data) {
		String hsql = "select a.id from AtcMergeBeforeComInfo a where a.materiel=? "
				+ " and a.afterComInfo.billList.impExpType ";
		List<Object> parmaters = new ArrayList();
		parmaters.add(data.getMateriel());
		if (data.getImrType().equals(MaterielType.MATERIEL)) {
			hsql += "in(?,?,?,?,?,?,?,?,?,?,?)";
			parmaters.add(ImpExpType.DIRECT_IMPORT);
			parmaters.add(1);
			parmaters.add(3);
			parmaters.add(6);
			parmaters.add(8);
			parmaters.add(9);
			parmaters.add(14);
			parmaters.add(17);
			parmaters.add(18);
			parmaters.add(19);
			parmaters.add(21);
		}
		if (data.getImrType().equals(MaterielType.FINISHED_PRODUCT)) {
			hsql += "in(?,?,?,?,?,?,?,?,?)";
			parmaters.add(2);
			parmaters.add(4);
			parmaters.add(5);
			parmaters.add(7);
			parmaters.add(10);
			parmaters.add(25);
			parmaters.add(26);
			parmaters.add(27);
			parmaters.add(28);
		}
		List list = this.find(hsql, parmaters.toArray());
		if (list == null)
			list = new ArrayList();
		return list;
	}
}