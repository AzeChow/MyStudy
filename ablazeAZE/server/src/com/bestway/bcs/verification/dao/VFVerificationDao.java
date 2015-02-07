package com.bestway.bcs.verification.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Session;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.verification.entity.VFCategory;
import com.bestway.bcs.verification.entity.VFCategoryBcsTenInnerMerge;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.temp.BOMTemp;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseDao;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

@SuppressWarnings("unchecked")
/**
 * 
 * @author chl
 *
 */
public class VFVerificationDao extends BaseDao {

	/**
	 * 批次设定 获得最大ID
	 */

	public List<Object> findMaxNO() {

		return find(
				"SELECT MAX(a.verificationNo) FROM VFSection a where a.company.id = ? ",
				CommonUtils.getCompany().getId());

	}

	/**
	 * 批次设定 获得表数据
	 */
	public List<VFSection> findVFSectionList() {
		return find(
				"SELECT a FROM VFSection a where a.company.id = ?  ORDER BY a.verificationNo",
				CommonUtils.getCompany().getId());
	}

	
	/**
	 * 批次设定 获得表数据
	 */
	public List<VFSection> findExistsVFSection(VFSection vfSection) {
		String hql = "SELECT a FROM VFSection a where a.company.id = ? AND a.endDate = ? ORDER BY a.verificationNo";
		return find(hql,new Object[]{CommonUtils.getCompany().getId(),vfSection.getEndDate()});
	}
	/**
	 * 批次设定 delete data by id
	 */
	public void deleteVFSection(VFSection vf) {
		delete(vf);
	}

	/**
	 * 批次设定 save data
	 */
	public void saveVFSection(VFSection vf) {
		saveOrUpdate(vf);
	}

	public void saveVFModifySection(VFSection vf){
		saveOrUpdate(vf);
	}
	
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockImg【 对应关系数据】 料件 对应 海关
	 * 
	 * @param request
	 * @param vf
	 * @return
	 */
	public List<Object> findVFfactionBcsInnerMergeList() {
		String hql = "SELECT mat.ptNo, mat.factoryName, mat.factorySpec, un.name ,a.bcsTenInnerMerge"
				+ " FROM BcsInnerMerge AS a "
				+ " LEFT JOIN a.materiel AS mat "
				+ " LEFT JOIN mat.calUnit un "
				+ " LEFT JOIN a.company AS com "
				+ " WHERE com.id = ? AND a.materielType =?";
		return find(hql, new Object[] { CommonUtils.getCompany().getId(),
				MaterielType.MATERIEL });
	}

	/**
	 * 查询工厂 成品BOM 表 成品对应 BOM 中的成品
	 * 
	 * @return
	 */
	public List<Object> findVFOutSendExgInBom() {
		String hql = " SELECT mat.ptNo ,m.version, mat.factoryName, mat.factorySpec, un.name "
				+ " FROM MaterialBomVersion m "
				+ " LEFT JOIN m.master AS ms "
				+ " LEFT JOIN m.company AS cp "
				+ " LEFT JOIN ms.materiel AS mat "
				+ " LEFT JOIN mat.calUnit un " + " WHERE m.company.id = ? ";
		return find(hql, CommonUtils.getCompany().getId());
	}

	/**
	 * 查询工厂 成品Max BOM 表
	 * 
	 * @return
	 */
	public List<Object> findVFOutSendExgInMaxBom() {
		String hql = "SELECT matl.ptNo, Max(m.version) "
				+ " FROM MaterialBomVersion m " + " LEFT JOIN m.master ms "
				+ " LEFT JOIN ms.materiel matl " + " WHERE m.company.id = ? "
				+ " GROUP BY matl.ptNo ";
		return find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
//	 * 查找 正在执行的合同料件,并从备案资料库带出归并序号
	 * 
	 *            请求控制
	 * @param emsNo 合同号
	 *            
	 * @return List 是Object[]型，存放合同备案料件资料
	 */
	public List<Object[]> findContractImgByEmsNo(List<String> emsNos){
		StringBuilder hsql = new StringBuilder();
		hsql.append(" select a,b.innerMergeSeqNum from ContractImg a,BcsDictPorImg as b where a.company= ? ");
		hsql.append( " and a.contract.declareState = ? ");
		if (emsNos != null&&emsNos.size()>0) {
			hsql.append( " and a.contract.emsNo in ( "+"'"+emsNos.get(0)+"'");
			for (int i = 1; i < emsNos.size(); i++) {
				hsql.append("," +"'"+ emsNos.get(i)+"'");
			}
			hsql.append(")");
		}
		hsql.append(" and a.credenceNo = b.seqNum and a.contract.corrEmsNo = b.dictPorHead.dictPorEmsNo and b.dictPorHead.declareState = ? ");
		hsql.append( " order by a.credenceNo asc ");
		System.out.println("--------------"+hsql.toString());
		return this.findNoCache(hsql.toString(), new Object[] { CommonUtils.getCompany(),
				DeclareState.PROCESS_EXE,DeclareState.PROCESS_EXE});
	}
	/**
//	 * 查找 正在执行的合同成品,并从备案资料库带出归并序号
	 * 
	 *            请求控制
	 * @param emsNo 合同号
	 *            
	 * @return List 是Object[]型，存放合同备案料件资料
	 */
	public List<Object[]> findContractExgByEmsNo(List emsNos){
		StringBuilder hsql = new StringBuilder();
		hsql.append(" select a,b.innerMergeSeqNum from ContractExg a,BcsDictPorExg as b where a.company= ? ");
		hsql.append( " and a.contract.declareState = ? ");
		if (emsNos != null&&emsNos.size()>0) {
			hsql.append( " and a.contract.emsNo in ( "+"'"+emsNos.get(0)+"'");
			for (int i = 1; i < emsNos.size(); i++) {
				hsql.append("," +"'"+ emsNos.get(i)+"'");
			}
			hsql.append(")");
		}
		hsql.append(" and a.credenceNo = b.seqNum and a.contract.corrEmsNo = b.dictPorHead.dictPorEmsNo and b.dictPorHead.declareState = ? ");
		hsql.append( " order by a.credenceNo asc ");
		return this.findNoCache(hsql.toString(), new Object[] { CommonUtils.getCompany(),
				DeclareState.PROCESS_EXE,DeclareState.PROCESS_EXE});
		
	}
	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMerge(String materielType) {
		String hsql = "select a from BcsInnerMerge a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.bcsTenInnerMerge "
				+ "  where a.company= ? and a.materielType =? "
				+ "   order by a.isUsing,a.bcsTenInnerMerge.seqNum asc";
		return this.findNoCache(hsql, new Object[] { CommonUtils.getCompany(),
				materielType });
	}
	
	/**
	 * 根据 物料类型 查找物料与报关对应表中的报关商品资料
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 报关商品资料
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMerge(String materielType) {
		String hsql = "select a.bcsTenInnerMerge from BcsInnerMerge a "
				+ " where a.company = ? and a.materielType = ? "
				+ " order by a.isUsing, a.bcsTenInnerMerge.seqNum asc";
		return this.findNoCache(hsql, new Object[] { CommonUtils.getCompany(),
				materielType });
	}

	/**
	 * 根据  查找【料件】报关商品资料
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 报关商品资料
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMerge() {
		String hsql = "select a from BcsTenInnerMerge a "
				+ " where a.company = ? and a.scmCoi = 2 ";
		return this.find(hsql, new Object[] { CommonUtils.getCompany()});
	}
	
	/**
	 * 根据公司，查询工厂 成品BOM 表,并加载对应的料件
	 * 
	 * @return
	 */
	public List<MaterialBomVersion> findMaterialBomVersions() {
		return find(
				"SELECT m FROM MaterialBomVersion m join fetch m.master join fetch m.master.materiel WHERE m.company.id = ? order by m.version desc",
				CommonUtils.getCompany().getId());
	}

	public List findBuyIsCount(String sectionid) {
		return find(
				"SELECT a.buyIsCount FROM VFSection a  WHERE a.company.id = ? and a.id = ?",
				new Object[] { CommonUtils.getCompany().getId(),
						sectionid });
	}
	
	public List findMaxBuyIsCount() {
		return find(
				"SELECT a FROM VFSection a  WHERE a.company.id = ? order by a.verificationNo desc",
				CommonUtils.getCompany().getId());
	}
	/**
	 * 根据指定料号列表 查询 bom。
	 * 
	 * @param ptNoSet
	 * @return
	 */
	public List<MaterialBomDetail> findMaterialBomDetailByPtNoSet(
			Set<String> ptNoSet) {
		List<MaterialBomDetail> returnList = new ArrayList<MaterialBomDetail>();
		String hqlHead = "select a from MaterialBomDetail a "
				+ " 	left join fetch a.version v"
				+ "		left join fetch v.master m" + " where a.company.id = ? ";

		StringBuilder hql = new StringBuilder(hqlHead);

		if (ptNoSet != null && !ptNoSet.isEmpty()) {
			Iterator<String> it = ptNoSet.iterator();
			hql.append(" and m.materiel.ptNo in ('" + it.next());
			int i = 1;
			while (it.hasNext()) {
				hql.append("','" + it.next());

				if (i % 1000 == 0) {
					hql.append("')");

					returnList.addAll(find(hql.toString(),
							new Object[] { CommonUtils.getCompany().getId() }));

					hql.setLength(0);

					if (it.hasNext()) {
						hql.append(hqlHead + " and m.materiel.ptNo in ('"
								+ it.next());
					}
				}
			}

			if (hql.length() > 0) {
				hql.append("')");
				returnList.addAll(find(hql.toString(),
						new Object[] { CommonUtils.getCompany().getId() }));
			}
		}

		return returnList;
	}

	/**
	 * 根据指定料号列表 查询 bom。
	 * 
	 * @param ptNoSet
	 * @return
	 */
	public List<BOMTemp> findMaterialBomDetailByPtNoSet(VFSection section,
			String clazz) {
		// String hql = "select a from " + clazz + " c, MaterialBomDetail a "
		// + " 	left join fetch a.version v "
		// + "		left join fetch v.master m "
		// + " 	left join fetch m.materiel mt "
		// + " where c.section.id = ? and c.ptNo = mt.ptNo "
		// + " 	and c.version = v.version and a.company.id = ? ";
		//
		//
		// return find(hql, new Object[] { section.getId(),
		// CommonUtils.getCompany().getId() });
		String hql = "	select new com.bestway.bcs.verification.entity.temp.BOMTemp("
				+ " 	a.version.master.materiel.ptNo, a.version.version, "
				+ " 	a.materiel.ptNo, a.unitWaste, a.waste, a.unitUsed,"
				+ " 	a.materiel.factoryName, a.materiel.factorySpec, a.materiel.calUnit.name"
				+ " )"
				+ " from MaterialBomDetail a "
				+ " where a.company.id = ? ";

		return find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据指定料号列表 查询 bom。
	 * 
	 * @param ptNoSet
	 * @return
	 */
	public List<MaterialBomDetail> findMaterialBomDetailByBomVers(
			Collection<MaterialBomVersion> versions) {
		List<MaterialBomDetail> returnList = new ArrayList<MaterialBomDetail>();
		String hqlHead = "select a from MaterialBomDetail a join fetch a.materiel join fetch a.version v join fetch v.master m join fetch m.materiel  where a.company.id = ? ";

		StringBuilder hql = new StringBuilder(hqlHead);

		if (versions != null && !versions.isEmpty()) {
			Iterator<MaterialBomVersion> it = versions.iterator();
			hql.append(" and v.id in ('" + it.next().getId());
			int i = 1;
			while (it.hasNext()) {
				hql.append("','" + it.next().getId());

				if (i % 1000 == 0) {
					hql.append("')");

					returnList.addAll(find(hql.toString(),
							new Object[] { CommonUtils.getCompany().getId() }));

					hql.setLength(0);

					if (it.hasNext()) {
						hql.append(hqlHead + " and v.id in ('"
								+ it.next().getId());
					}
				}
			}

			if (hql.length() > 0) {
				hql.append("')");
				returnList.addAll(find(hql.toString(),
						new Object[] { CommonUtils.getCompany().getId() }));
			}
		}

		return returnList;
	}

	public int deleteByVFSection(VFSection vfSection, String entityName) {
		return batchUpdateOrDelete("delete " + entityName
				+ " v where v.section.id = ?", vfSection.getId());
	}
	
	public List findConvertVFStockOutSendImgs(VFSection section,Class clazz){
		String hql = "	select a.mergerNo,a.hsName,a.hsSpec,a.hsUnit,sum(a.hsAmount)  "
				+ " from "+clazz.getName()+" a "
				+ " where a.company.id = ?  and a.section.id = ? group by a.mergerNo,a.hsName,a.hsSpec,a.hsUnit ";
		return find(hql, new Object[] { CommonUtils.getCompany().getId() ,section.getId()});
	}
	
	public List findConvertVFStockImgs(VFSection section,Class clazz){
		String hql = "	select a.mergerNo,a.hsName,a.hsSpec,a.hsUnit,sum(a.hsAmount)  "
				+ " from "+clazz.getName()+" a "
				+ " where a.company.id = ?  and a.section.id = ? group by a.mergerNo,a.hsName,a.hsSpec,a.hsUnit ";
		return find(hql, new Object[] { CommonUtils.getCompany().getId() ,section.getId()});
	}
	
	public <T extends BaseScmEntity> List<T> findByVFSection(VFSection vfSection, String entityName, Integer seqNum,String orderby) {
		String seqNumStr = "";

		if (seqNum != null) {
			seqNumStr = " and v.mergerNo = " + seqNum+" ";
		}

		return findNoCache("select v from " + entityName
				+ " v join fetch v.section where v.section.id = ? " + seqNumStr
				+ orderby,
				new Object[] { vfSection.getId() });
	}
	public <T extends BaseScmEntity> List<T> findByVFSection(
			VFSection vfSection, String entityName, Integer seqNum) {
		String seqNumStr = "";

		if (seqNum != null) {
			seqNumStr = " and v.mergerNo = " + seqNum;
		}

		return findNoCache("select v from " + entityName
				+ " v join fetch v.section where v.section.id = ?" + seqNumStr
				+ " order by v.serialNumber ",
				new Object[] { vfSection.getId() });
	}
	
	public <T extends BaseScmEntity> List<T> findByVFSection(
			VFSection vfSection, String entityName) {
		return this.findByVFSection(vfSection, entityName, null);
	}

	public boolean isExistByVFSection(VFSection vfSection, String entityName) {
		List list = find("select count(v.id) from " + entityName
				+ " v join v.section where v.section.id = ? ",
				vfSection.getId());
		if (list != null && !list.isEmpty()) {
			Number n = (Number) list.get(0);
			return (n == null ? false : n.intValue() > 0);
		}
		return false;
	}

	public <T extends BaseScmEntity> void batchSaveVFEnity(List<T> list) {
		Session s = getSession();

		s.setCacheMode(CacheMode.IGNORE);
		int size = list.size();
		int m = 0;
		T obj = null;
		for (int i = 0; i < size; i++, m++) {

			if (m == 1000) {
				s.flush();// 将一级缓存中的数据同步到数据库中
				// s.clear();// 清空一级缓存中的数据，这样不至于造成内存溢出
				m = 0;
			}
			obj = list.get(i);
			obj.setCompany(CommonUtils.getCompany());
			s.save(obj);
		}
		s.flush();
		s.clear();
		System.gc();

		// batchSaveOrUpdate(list);
	}

	public <T extends BaseScmEntity> void batchUpdateVFEnity(List<T> list) {
		Session s = getSession();

		s.setCacheMode(CacheMode.IGNORE);
		int size = list.size();
		int m = 0;
		T obj = null;
		for (int i = 0; i < size; i++, m++) {
			if (m == 1000) {
				s.flush();// 将一级缓存中的数据同步到数据库中
				// s.clear();// 清空一级缓存中的数据，这样不至于造成内存溢出
				m = 0;
			}
			obj = list.get(i);
			s.update(obj);
		}
		s.flush();
		s.clear();
		System.gc();

		// batchSaveOrUpdate(list);
	}

	public Long countByVFSection(VFSection section, String entityName,
			String property, Object value, boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select count(*) from " + entityName
				+ " a  where a.company.id = ? ";
		paramters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hsql += " and a.section.id = ?";
			paramters.add(section.getId());
		}
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%'";
			} else {
				hsql += " and  a." + property + " like '" + value + "'";
			}
		}
		System.out.println("--:" + hsql);
		List list = find(hsql, paramters.toArray());
		Long count = (Long) list.get(0);
		if (count == null) {
			count = (long) 0;
		}
		return count;
	}

	/**
	 * 查询正在执行合同的料件
	 * 
	 * @return
	 */
	public List<ContractImg> findContractImgInPROCESS_EXE() {
		List<ContractImg> contractImgs = null;
		String hql = "select i from ContractImg i where i.contract.declareState = ? and i.company.id = ?";
		contractImgs = find(hql, new Object[] { DeclareState.PROCESS_EXE,
				CommonUtils.getCompany().getId() });

		return contractImgs;
	}

	public <T extends BaseScmEntity> void batchSaveOrUpdateDirect(List<T> ls) {
		if (ls != null && !ls.isEmpty()) {
			Request request = CommonUtils.getRequest();
			ProgressInfo info = null;
			if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
				info = ProcExeProgress.getInstance().getProgressInfo(
						request.getTaskId());
				info.setMethodName("正在保存数据，共" + ls.size() + "条");
				info.setTotalNum(ls.size() / 100);
			}
			// Session s = getSession();
			// s.setCacheMode(CacheMode.IGNORE);
			int size = ls.size();
			int m = 0;
			T obj = null;
			for (int i = 0; i < size; i++, m++) {
				if (m == 10000) {
					getHibernateTemplate().flush();// 将一级缓存中的数据同步到数据库中
					// s.clear();// 清空一级缓存中的数据，这样不至于造成内存溢出
					m = 0;
				}
				if (info != null && (i - 100) % 100 == 0) {
					info.setCurrentNum(info.getCurrentNum() + 1);
				}
				obj = ls.get(i);
				obj.setCompany(CommonUtils.getCompany());
				getHibernateTemplate().saveOrUpdate(obj);
			}
			System.gc();
		}
	}

	/**
	 * 将数据从托管状态转换成离线状态
	 * 
	 * @param obj
	 */
	public <T extends BaseEntity> void evict(T obj) {
		getHibernateTemplate().evict(obj);
	}
	
	/**
	 * 查询未归类商品编码
	 * @param index 
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerNotInCategory(int index, int length, String property,Object value, boolean isLike){
		String hsql = "select distinct a from VFCategoryBcsTenInnerMerge b right join b.bcsTenInnerMerge a , BcsInnerMerge c";
		hsql += " where b.id is null and c.bcsTenInnerMerge.id = a.id and c.materielType="+MaterielType.MATERIEL +" and a.company = ? ";	
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%'";
			} else {
				hsql += " and  a." + property + " like '" + value + "'";
			}
		}
		return findPageList(hsql,CommonUtils.getCompany(), index, length);
	}
	
	/**
	 * 查询大类名称列表
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<VFCategory> findVFCategory(int index, int length, String property,Object value, boolean isLike) {
		String hsql = "select a from VFCategory a where a.company = ? ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%'";
			} else {
				hsql += " and  a." + property + " like '" + value + "'";
			}
		}
		hsql += " order by a.seqNum ";
		return findPageList(hsql,CommonUtils.getCompany(), index, length);
	}
	
	public boolean isExistsCategoryBcsTenInnerMerge(VFCategory category){
		List<Object> paramters  =  new ArrayList<Object>();
		String hql = " select count(a.id) from VFCategoryBcsTenInnerMerge a ";
		if (category != null) {
			hql += " where  a.category.id = ?";
			paramters.add(category.getId());
		}
		List<?> ls = find(hql,paramters.toArray());
		if(ls != null && !ls.isEmpty()){
			Number n = (Number) ls.get(0);
			return (n == null ? false : n.intValue() > 0);
		}
		return false;
	}
	
	public void deleteCategor(VFCategory c){
		if(c !=null){
		String hql = "delete from VFCategory where id=? ";
		super.batchUpdateOrDelete(hql,c.getId());
		}
		
	}
	/**
	 * 查询大类报关商品对应关系表
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> findVFCategoryBcsTenInnerMerge() {
		String hql = "from VFCategoryBcsTenInnerMerge a left join fetch a.category left join fetch a.bcsTenInnerMerge where a.company = ? ";				
		return find(hql,CommonUtils.getCompany());
	}
	/**
	 * 查询大类报关商品对应关系表
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> findVFCategoryBcsTenInnerMerge(int index, int length, String property,Object value, boolean isLike) {
		String hsql = "from VFCategoryBcsTenInnerMerge a left join fetch a.category left join fetch a.bcsTenInnerMerge where a.company = ? ";
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like '" + value + "%'";
			} else {
				hsql += " and  a." + property + " like '" + value + "'";
			}
		}
		return findPageList(hsql,CommonUtils.getCompany(), index, length);
	}
	/**
	 * 获取大类最大序号
	 * @return
	 */
	public Integer getMaxSeqNumOfVFCategory() {
		String hql = "select max(a.seqNum) from VFCategory a where a.company=? ";
		List ls = find(hql,CommonUtils.getCompany());
		if(ls == null || ls.isEmpty())
			return 0;
		Number n = (Number)ls.get(0); 
		return n == null ? 0 : n.intValue();
	}
	
	public Complex findComplex(String hsCode){
		String jpql = "from Complex where code=?";
		List<Complex> list = super.find(jpql,hsCode);  
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 查询所有盘点核查批次
	 * 
	 * @param isExist 是否在附件管理中，已经存在
	 * @return
	 */
	public List<VFSection> findAttachmentSection(Boolean isExist){
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select s from VFSection s where s.company = ? ";
		paramters.add(CommonUtils.getCompany());
		if(isExist!=null&&isExist==true){
			hql +=" and s.isExist = ? ";
			paramters.add(true);
		}else{
			hql +=" and (s.isExist = ? or s.isExist is null)";
			paramters.add(false);
		}
		hql +=" order by s.verificationNo desc ";
		return find(hql,paramters.toArray());
	}
	
	/**
	 * 查询核资料附件
	 * @param section
	 * @param boo
	 * @return
	 */
	public List findVFAttachmentManagement(VFSection section,Boolean boo){
		List<Object> paramters = new ArrayList<Object>();
		String hql = " select a from VFAttachmentManagement a ";
		hql += " where a.company.id = ? ";
		paramters.add(CommonUtils.getCompany().getId());
		if(section!=null){
			hql+=" and a.section = ?";
			paramters.add(section);
		}
		if(boo){
			hql+=" and a.parentId is null ";
		}else{
			hql+=" and a.parentId is not null ";
		}
		List list = find(hql, paramters.toArray());
		return list;
	}
	
	public List findTableSize(VFSection ecsSection, String entityName,Boolean isNotNull){
		String hql = "select count(e.id) from " + entityName + " e where e.section.id = ?";
		if(isNotNull!=null&&isNotNull){
			hql +=" and e.mergerNo is not null";
		}
		return super.find(hql,ecsSection.getId());
	}
	
	/**
	 * 根据{fieldName}和{fieldValue}抓取 实体 {entityClassName}的对象。此实体对象用公司区分
	 * 
	 * @param entityClassName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public List findEntitys(String entityClassName, String fieldName,
			Object fieldValue) {
		List list = this.find("select a from " + entityClassName + " as a "
				+ "where a.company= ?  and a." + fieldName + "= ?",
				new Object[] { CommonUtils.getCompany(), fieldValue });
		return list;
	}
}
