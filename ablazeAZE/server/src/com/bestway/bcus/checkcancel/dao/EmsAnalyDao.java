/*
 * Created on 2004-7-29
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.impl.SessionImpl;

import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsBgExg;
import com.bestway.bcus.checkcancel.entity.EmsBgExgBg;
import com.bestway.bcus.checkcancel.entity.EmsBgImg;
import com.bestway.bcus.checkcancel.entity.EmsBgTotal;
import com.bestway.bcus.checkcancel.entity.EmsCy;
import com.bestway.bcus.checkcancel.entity.EmsPdExg;
import com.bestway.bcus.checkcancel.entity.EmsPdExgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdExgImgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdImg;
import com.bestway.bcus.checkcancel.entity.EmsPdImgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdTotal;
import com.bestway.bcus.checkcancel.entity.EmsTransFactory;
import com.bestway.bcus.checkcancel.entity.EmsTransFactoryBg;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.SendState;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */

@SuppressWarnings({"unchecked", "rawtypes"})
public class EmsAnalyDao extends BaseDao {

	// -------------------------帐帐分析-------------------------------------------//
	/**
	 * 返回帐分析表头
	 */
	public List findEmsAnalyHead() {
		return this.find("select a from EmsAnalyHead a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回核查参数设定
	 * 
	 * @return
	 */
	public List findCheckParameter() {
		return this.find(
				"select a from CheckParameter a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 得到流水号
	 */
	public Integer getNum(String className, String seqNum) {
		int num = 1;
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a  where a.company.id =?", new Object[] { CommonUtils
				.getCompany().getId() });
		if (list.get(0) != null) {
			num = (Integer) list.get(0);
			num = Integer.valueOf(num) + 1;
		}
		return num;
	}

	/**
	 * 保存核销头
	 */
	public void SaveEmsAnalyHead(EmsAnalyHead head) {
		this.saveOrUpdate(head);
	}

	/**
	 * 保存核查参数设定
	 */
	public void SaveCheckParameter(CheckParameter head) {
		this.saveOrUpdate(head);
	}

	/**
	 * 返回盘点料件
	 */
	public List findEmsPdImg(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsPdImg a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回盘点报关料件
	 */
	public List findEmsPdImgBg(EmsAnalyHead head) {
		return this.find("select a from EmsPdImgBg a where a.head.id = ?",
				new Object[] { head.getId() });
	}

	/**
	 * 返回所有盘点报关料件
	 */
	public List<EmsPdImgBg> findEmsPdImgBgAll(EmsAnalyHead head) {
		return this.find("select a from EmsPdImgBg a where a.head.id = ?",
				new Object[] { head.getId() });
	}

	/**
	 * 返回所有盘点报关成品
	 */
	public List findEmsPdExgBgAll(EmsAnalyHead head) {
		return this.find("select a from EmsPdExgBg a where a.head.id = ?",
				new Object[] { head.getId() });
	}

	/**
	 * 返回盘点成品
	 */
	public List findEmsPdExg(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsPdExg a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 查看所有盘点成品未归并
	 * 
	 * @param head
	 * @return
	 */
	public List findAllEmsPdExgNoEms(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsPdExg a where  not exists (select b from InnerMergeData b where a.ptNo=b.materiel.ptNo and a.company.id = ? )"
								+ "  and  a.company.id = ? and a.head.id = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 返回盘点报关成品
	 */
	public List findEmsPdExgBg(EmsAnalyHead head) {
		return this.find("select a from EmsPdExgBg a where a.head.id = ?",
				new Object[] { head.getId() });
	}

	/**
	 * 返回盘点报关折料料件
	 * 
	 */
	public List findEmsExgImgBg(EmsAnalyHead head) {
		return this.find("select a from EmsPdExgImgBg a where a.head.id = ?",
				new Object[] { head.getId() });
	}

	/**
	 * 返回盘点总表
	 */
	public List<EmsPdTotal> findEmsPdTotal(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsPdTotal a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回差异分析
	 */
	public List findEmsCy(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsCy a where a.company.id = ? and a.head.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回报关--进口料件
	 */
	public List<EmsBgImg> findEmsBgImg(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsBgImg a where a.company.id = ? and a.head.id = ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}
	/**
	 * 返回报告单价格，时间早的排前面，排除退厂返工
	 * @param head
	 * @return
	 */
	public List<Object[]> findPriceFromCustomsDeclaration(EmsAnalyHead head){
		
		String hql = "select a.commSerialNo, a.commUnitPrice, max(a.baseCustomsDeclaration.impExpDate) from CustomsDeclarationCommInfo a" +
				" where a.baseCustomsDeclaration.impExpFlag = ?" +
				" and a.commUnitPrice != null" +
				" and a.company.id = ? and a.baseCustomsDeclaration.impExpDate >= ?" +
				" and a.baseCustomsDeclaration.impExpDate <= ?" +
				" group by a.commSerialNo, a.commUnitPrice";
		
		return this.find(hql, new Object[]{ImpExpFlag.IMPORT, CommonUtils.getCompany().getId(), head.getBeginDate(), head.getEndDate()});
	}
	

	/**
	 * 返回报关--出口成品
	 */
	public List<EmsBgExg> findEmsBgExg(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsBgExg a where a.company.id = ? and a.head.id = ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回报关--出口成品
	 */
	public List<EmsBgExg> findEmsBgExgByHeadIdOrBgExgId(EmsAnalyHead head,
			String emsBgExgId) {
		return this
				.find(
						"select a from EmsBgExg a where a.company.id = ? and a.head.id = ? and a.id=? ",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId(), emsBgExgId });
	}

	public List<Object[]> findEmsHeadH2kBom(EmsAnalyHead head){
		StringBuffer sb = new StringBuffer();
		List<Object> parameters = new ArrayList<Object>();
		sb.append(" select a,b.seqNum,b.name,b.spec,b.unit.code,b.unitWear,b.wear,a.totalNum ");
		sb.append(" from EmsBgExg a ,EmsHeadH2kBom b ");
		sb.append(" where a.seqNum = b.emsHeadH2kVersion.emsHeadH2kExg.seqNum ");
		sb.append(" and a.versionNo = b.emsHeadH2kVersion.version ");
		sb.append(" and a.head.emsNo = ? ");
		sb.append(" and a.company.id = ? ");
		
		parameters.add(head.getEmsNo());
		parameters.add(CommonUtils.getCompany().getId());
		
		return this.find(sb.toString(),parameters.toArray());
	} 
	
	public List<Unit> findUnit(){
		String hql = "select a from Unit a ";
		return this.find(hql);
	}
	
	/**
	 * 返回报关--出口成品折算料件
	 */
	public List findEmsBgExgBg(EmsAnalyHead head, String exgId) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from EmsBgExgBg a where a.company.id = ? and a.head.id = ? ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(head.getId());
		if (exgId != null && !"".equals(exgId)) {
			hsql += " and a.exg.id=?  ";
			paramters.add(exgId);
		}
		hsql += " order by a.seqNum ";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 返回报关--所有出口成品折算料件
	 */
	public List findEmsBgExgBgAll(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsBgExgBg a where a.company.id = ? and a.head.id = ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 返回报关--总表
	 */
	public List<EmsBgTotal> findEmsBgTotal(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsBgTotal a where a.company.id = ? and a.head.id = ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 获得物料主档
	 */
	public List findMateriel(EmsAnalyHead head, String type) {
		String tableName = null;
		if (type.equals(MaterielType.MATERIEL)) {
			tableName = "EmsPdImg";
		} else {
			tableName = "EmsPdExg";
		}
		return this.find(
				"select a from Materiel a where a.company.id = ? and a.scmCoi.coiProperty = ?"
						+ " and a.ptNo not in (select b.ptNo from " + tableName
						+ " b where b.head.id = ?)", new Object[] {
						CommonUtils.getCompany().getId(), type, head.getId() });
	}

	/**
	 * 保存盘点料件
	 */
	public void SaveEmsPdImg(EmsPdImg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存盘点成品
	 */
	public void SaveEmsPdExg(EmsPdExg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 组织HQL报表条件公共查询
	 * 
	 * @param className
	 * @param conditions
	 * @return
	 */
	public List commonSearch(String selects, String className, List conditions, // 不包含公司
			String groupBy) {
		String sql = " select a from " + className + " a where (1=1) ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		}
		List params = new Vector();
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null)
					sql += " " + condition.getLogic() + "  ";
				if (condition.getBeginBracket() != null)
					sql += condition.getBeginBracket();
				if (condition.getFieldName() != null)
					sql += "a." + condition.getFieldName();
				if (condition.getValue() != null)
					sql += condition.getOperator();
				sql += "? ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
				params.add(condition.getValue());
			}
		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}

		List result = this.find(sql, params.toArray());

		return result;
	}

	/**
	 * 删除报关料件进口
	 */
	public void deleteEmsBgImgAll(EmsAnalyHead head) {
		this.deleteAll(findEmsBgImg(head));
	}

	/**
	 * 删除报关成品出口
	 */
	public void deleteEmsBgExgAll(EmsAnalyHead head) {
		this.deleteAll(findEmsBgExg(head));
	}

	public List<EmsBgExgBg> findEmsBgExgImgAll(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsBgExgBg a where a.company.id = ? and a.exg.head.id = ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 删除报关成品折料
	 */
	public void deleteEmsBgExgImgAll(EmsAnalyHead head, String emsBgExgId,
			boolean isAllOrSinlg) {
		
		if (isAllOrSinlg) {
			SessionImpl s = (SessionImpl) getSession();
			String d = s.getFactory().getDialect().toString();
			
			// "org.hibernate.dialect.SQLServerDialect"
			if(d.contains("SQLServerDialect")) {
				String sql = "delete EmsBgExgBg where head = '" + head.getId() + "'";
				Connection c = getSession().connection();
				try {
					Statement stat = c.createStatement();
					int i = stat.executeUpdate(sql);
					System.out.println("删除" + i + "条EmsBgExgBg记录");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			String hql = "delete EmsBgExgBg a where a.head.id = '" + head.getId() + "'";
			this.batchUpdateOrDelete(hql);
		} else {
			this.deleteAll(findEmsBgExgBg(head, emsBgExgId));
		}
	}
	
	/**
	 * 保存报关料件
	 */
	public void SaveEmsBgImg(EmsBgImg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存报关成品
	 */
	public void SaveEmsBgExg(EmsBgExg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 成品折料
	 */
	public List<EmsHeadH2kBom> findBgExgImgFormEms(EmsBgExg exg) {
		// a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.declareState = ? and
		// DeclareState.PROCESS_EXE,
		return this
				.find(
						"select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.version =? and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? and "
								+ "  a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState = ?"
								+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.company.id = ?",
						new Object[] { Integer.valueOf(exg.getVersionNo()),
								exg.getSeqNum(), new Boolean(false),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 盘点成品折料
	 */
	public List findPdExgImgBgFormEms(EmsPdExgBg exg) {
		return this
				.find(
						"select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.version =? and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? and "
								+ " a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState = ?"
								+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.company.id = ? ",
						new Object[] { Integer.valueOf(exg.getVersionNo()),
								exg.getSeqNum(), new Boolean(false),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 盘点成品折料取最大版本进行折料
	 */
	public List findPdExgImgBgFormMaxVersionEms(EmsPdExgBg exg) {
		return this
				.find(
						"select a from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=? and "
								+ " a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.historyState = ?"
								+ " and a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.company.id = ?"
								+ " and a.emsHeadH2kVersion.version in(select max(a.version) from EmsHeadH2kVersion a "
								+ "  where a.emsHeadH2kExg.seqNum=? and a.emsHeadH2kExg.emsHeadH2k.company.id = ?) ",
						new Object[] { exg.getSeqNum(), new Boolean(false),
								CommonUtils.getCompany().getId(),
								exg.getSeqNum(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存报关成品折算料件
	 */
	public void SaveEmsBgExgImg(EmsBgExgBg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存报关总和
	 */
	public void SaveEmsBgTotal(EmsBgTotal obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 删除报关进出口总表
	 */
	public void deleteEmsBgTotal(EmsAnalyHead head) {
		this.deleteAll(findEmsBgTotal(head));
	}

	/**
	 * 盘点得到报关料件来自内部归并
	 */
	public List findBgImgFromInnerMerger(String type, String ptNo,
			String h2kType) {
		if ("EXG".endsWith(h2kType)) {
			return this
					.find(
							"select a,b "
									+ " from InnerMergeData a,EmsHeadH2kVersion b "
									+ " where a.hsAfterTenMemoNo=b.emsHeadH2kExg.seqNum and a.hsAfterMaterielTenName=b.emsHeadH2kExg.name"
									+ " and a.company.id = ? and b.company.id=? and a.materiel.scmCoi.coiProperty = ? and "
									+ " a.materiel.ptNo = ? and  (b.isForbid is null or b.isForbid=?) ",
							new Object[] { CommonUtils.getCompany().getId(),
									CommonUtils.getCompany().getId(), type,
									ptNo, Boolean.valueOf(false) });
		} else {
			return this
					.find(
							" select a,b "
									+ " from InnerMergeData a,EmsHeadH2kImg b "
									+ " where a.hsAfterTenMemoNo=b.seqNum and a.hsAfterMaterielTenName=b.name"
									+ " and a.company.id = ? and b.company.id=? and a.materiel.scmCoi.coiProperty = ? and "
									+ " a.materiel.ptNo = ?", new Object[] {
									CommonUtils.getCompany().getId(),
									CommonUtils.getCompany().getId(), type,
									ptNo });
		}
	}
	
	
	/**
	 * 得到报关料件来自内部归并
	 */
	public List<InnerMergeData> findMaterialInnerMergerByPtNo(Set<String> ptNoSet, EmsHeadH2k emsHeadH2k) {
		if(ptNoSet == null || ptNoSet.size() == 0) {
			return null;
		}
		
		List<InnerMergeData> list = new ArrayList<InnerMergeData>();
		
		StringBuilder hql = new StringBuilder();
		String sql = "select a"
				+ " from InnerMergeData a,EmsHeadH2kImg b "
				+ " where a.hsAfterTenMemoNo=b.seqNum"
				+ " and a.company.id = ? and b.company.id=? and a.materiel.scmCoi.coiProperty = ?"
				+ " and b.emsHeadH2k.id = ?"
				+ " and a.materiel.ptNo in ('";
		hql.append(sql);
		
		int i = 0;
		for (String ptNo : ptNoSet) {
			i++;
			hql.append("','" + ptNo);
			// 1000 个料号查询一次，避免hql语句过长报错
			if(i % 1000 == 0 || i == ptNoSet.size()) {
				hql.append("')");
				list.addAll(find(hql.toString(), new Object[] {
					CommonUtils.getCompany().getId(), CommonUtils.getCompany().getId(), 
					MaterielType.MATERIEL, emsHeadH2k.getId()}));
				hql.setLength(0);
				hql.append(sql);
			}
		}
		
		return list;
	}
	
	/**
	 * 盘点得到报关料件来自内部归并
	 */
	public List<InnerMergeData> findBgImgFromInnerMerger(String type, Set<String> ptNoSet) {
		
		StringBuilder hql = new StringBuilder();
		hql
			.append("select a from InnerMergeData a,EmsHeadH2kImg b")
			.append(" where a.hsAfterTenMemoNo=b.seqNum and a.hsAfterMaterielTenName=b.name")
			.append(" and a.company.id = ? and b.company.id=? and a.materiel.scmCoi.coiProperty = ?");
		
		
		if(ptNoSet != null && ptNoSet.size() > 0) {
			hql.append(" and a.materiel.ptNo in (");
			for (String ptNo : ptNoSet) {
				hql.append("'" + ptNo + "',");
			}
			hql.append("'')");
		}
		
		
		return this
					.find(hql.toString(),
							new Object[] { CommonUtils.getCompany().getId(),
									CommonUtils.getCompany().getId(), type});
	}

	/**
	 * 删除盘点料件报关
	 */
	public void deletePdImgBg(EmsAnalyHead head) {
		// this.deleteAll(findEmsPdImgBgAll(head));
		this.batchUpdateOrDelete(
				"delete from EmsPdImgBg a where a.head.id = ? ",
				new Object[] { head.getId() });
	}

	/**
	 * 删除盘点成品报关
	 */
	public void deletePdExgBg(EmsAnalyHead head) {
//		this.deleteAll(findEmsPdExgBgAll(head));
		this.batchUpdateOrDelete(
				"delete from EmsPdExgBg a where a.head.id = ? ",
				new Object[] { head.getId() });
	}

	/**
	 * 保存报关料件
	 */
	public void SaveEmsPdImgBg(EmsPdImgBg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存报关成品
	 */
	public void SaveEmsPdExgBg(EmsPdExgBg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 返回所有折料料件
	 * 
	 * @param exg
	 * @return
	 */
	public List<EmsPdExgImgBg> findEmsPdExgImgBg(EmsAnalyHead head) {
		return this
				.find(
						"select a from EmsPdExgImgBg a where a.head.id = ? order by a.seqNum",
						new Object[] { head.getId() });
	}

	/**
	 * 删除折料成品料件
	 */
	public void deleteEmsPdExgImgBgAll(EmsAnalyHead head) {
		this.batchUpdateOrDelete(
				"delete from EmsPdExgImgBg a where a.head.id = ? ",
				new Object[] { head.getId() });
	}

	/**
	 * 保存盘点成品折料
	 */
	public void SaveEmsPdExgImgBg(EmsPdExgImgBg obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存盘点分析总表
	 */
	public void SaveEmsPdTotal(EmsPdTotal obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 清空盘点分析总表
	 */
	public void deleteEmsPdTotalAll(EmsAnalyHead head) {
		this.deleteAll(findEmsPdTotal(head));
	}

	/**
	 * 清空差异分析总表
	 */
	public void deleteEmsCyAll(EmsAnalyHead head) {
		this.deleteAll(findEmsCy(head));
	}

	/**
	 * 保存差异
	 */
	public void SaveEmsCy(EmsCy obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 删除盘点料件
	 */
	public void deleteEmsPdImgAll(EmsAnalyHead head) {
		this.deleteAll(findEmsPdImg(head));
	}

	/**
	 * 删除盘点成品
	 */
	public void deleteEmsPdExgAll(EmsAnalyHead head) {
		this.deleteAll(findEmsPdExg(head));
	}

	/**
	 * 删除核销期表头
	 */
	public void deleteEmsAnalyHead(EmsAnalyHead obj) {
		this.delete(obj);
	}

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

	public List findAllMateriel() {
		List list = null;
		list = this.find("select b from Materiel b "
				+ " left join fetch b.complex " + " where b.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	private boolean getIsMergerSend() {
		List list = this
				.find(
						"select a from BcusParameter a where a.type = ? and a.company.id = ?",
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
	 * 根据物料主档料号查找归并关系归并前是否存在
	 * 
	 * @return
	 */
	public List findEmsEdiMergerImgBefore() {
		if (getIsMergerSend()) {
			List list = this
					.find(
							"select a from EmsEdiMergerImgBefore a where "
									+ " a.emsEdiMergerImgAfter.emsEdiMergerHead.historyState=? "
									+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.company.id=? and a.sendState = ?",
							new Object[] { new Boolean(false),
									CommonUtils.getCompany().getId(),
									Integer.valueOf(SendState.SEND) });
			return list;
		} else {
			List list = this
					.find(
							"select a from EmsEdiMergerImgBefore a where "
									+ "  a.emsEdiMergerImgAfter.emsEdiMergerHead.historyState=?"
									+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.company.id=?",
							new Object[] { new Boolean(false),
									CommonUtils.getCompany().getId() });
			return list;
		}
	}

	public List findEmsEdiMergerExgBefore() {
		if (getIsMergerSend()) {
			List list = this
					.find(
							"select a from EmsEdiMergerVersion a where "
									+ " a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState=? "
									+ " and a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.company.id=? and a.emsEdiMergerBefore.sendState = ?",
							new Object[] { new Boolean(false),
									CommonUtils.getCompany().getId(),
									Integer.valueOf(SendState.SEND) });
			return list;
		} else {
			List list = this// a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.declareState=?
					.find(
							"select a from EmsEdiMergerVersion a  where "
									+ "  a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.historyState=?"
									+ " and a.emsEdiMergerBefore.emsEdiMergerExgAfter.emsEdiMergerHead.company.id=?",
							new Object[] { new Boolean(false),
									CommonUtils.getCompany().getId() });
			return list;
		}
	}

	public List<EmsTransFactory> findEmsTransFactory(EmsAnalyHead head) {
		StringBuilder hql = new StringBuilder();
		hql.append("select a from EmsTransFactory a where a.company.id = ? and a.head.id = ?");
		
		return find(hql.toString(), new Object[]{CommonUtils.getCompany().getId(), head.getId()});
	}
	
	public List<EmsTransFactoryBg> findEmsTransFactoryBg(EmsAnalyHead head) {
		StringBuilder hql = new StringBuilder();
		hql.append("select a from EmsTransFactoryBg a where a.company.id = ? and a.head.id = ?");
		
		return find(hql.toString(), new Object[]{CommonUtils.getCompany().getId(), head.getId()});
	}
}