package com.bestway.bcus.checkstock.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.CacheMode;
import org.hibernate.Session;

import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.bcus.checkstock.entity.ECSBadStockResolve;
import com.bestway.bcus.checkstock.entity.ECSBaseStockItem;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.temp.BadBomTemp;
import com.bestway.bcus.checkstock.entity.temp.BomTemp;
import com.bestway.bcus.checkstock.entity.temp.MergeTemp;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
/**
 * 
 * @author chl
 *
 */
@SuppressWarnings("unchecked")
public class ECSCheckStockDao extends BaseDao {
	
	/******************************************* 查询归并关系 开始************************************/
	/**
	 * 根据指定料号列表 查询【料件】归并关系(工厂部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergePtPartImg() {
		String hql = "select new com.bestway.bcus.checkstock.entity.temp.MergeTemp"
				+ "	(a.ptNo, a.name, a.spec, a.unit.name)"
				+ "	from EmsEdiMergerImgBefore a, Materiel m"
				+ " where a.ptNo = m.ptNo and a.modifyMark = ? and a.company.id = ? ";
		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据指定料号列表 查询【成品】归并关系(工厂部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergePtPartExg() {
		String hql = "select new com.bestway.bcus.checkstock.entity.temp.MergeTemp"
				+ "	(a.ptNo, a.name, a.spec, a.unit.name)"
				+ "	from EmsEdiMergerExgBefore a, Materiel m"
				+ " where a.ptNo = m.ptNo and a.modifyMark = ? and a.company.id = ? ";
		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				CommonUtils.getCompany().getId() });
	}
	
	
	/**
	 * 根据指定料号列表 查询【料件】归并关系(报关部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergeHsPartImg() {
		String hql = "select new com.bestway.bcus.checkstock.entity.temp.MergeTemp"
				+ "	(a.ptNo, a.emsEdiMergerImgAfter.seqNum, a.emsEdiMergerImgAfter.name, "
				+ "	a.emsEdiMergerImgAfter.spec, a.emsEdiMergerImgAfter.unit.name, m.unitConvert)"
				+ "	from EmsEdiMergerImgBefore a, Materiel m"
				+ " where a.ptNo = m.ptNo and a.modifyMark = ? and a.company.id = ? ";
		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 根据指定料号列表 查询【成品】归并关系(报关部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergeHsPartExg() {
		String hql = "select new com.bestway.bcus.checkstock.entity.temp.MergeTemp"
				+ "	(a.ptNo, a.emsEdiMergerExgAfter.seqNum, a.emsEdiMergerExgAfter.name, "
				+ "	a.emsEdiMergerExgAfter.spec, a.emsEdiMergerExgAfter.unit.name, m.unitConvert)"
				+ "	from EmsEdiMergerExgBefore a, Materiel m"
				+ " where a.ptNo = m.ptNo and a.modifyMark = ? and a.company.id = ? ";
		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				CommonUtils.getCompany().getId() });
	}
	/*******************************************查询归并关系结束************************************/
	
	
	/*******************************************盘点核查批次开始************************************/
	/**
	 * 根据自用核销表头查询账册盘点核查批次
	 * @param request 
	 * @param head 自用核销表头
	 * @return
	 */
	public List<ECSSection> findEcsSection(CancelOwnerHead head) {
		String hql = " from ECSSection s join fetch s.cancelOwnerHead where s.company = ? ";
		if(head == null){
			return super.find(hql+" order by s.cancelOwnerHead.cancelTimes desc,s.verificationNo desc", new Object[]{CommonUtils.getCompany()});
		}else{
			hql += " and s.cancelOwnerHead = ? order by s.cancelOwnerHead.cancelTimes desc,s.verificationNo desc";
			return super.find(hql, new Object[]{CommonUtils.getCompany(),head});
		}
	}
	/**
	 * 根据自用核销表头查询账册盘点核查最大批次号
	 * @param head
	 * @return 当不存在数据时返回0，否则返回最大批次号
	 */
	public int getMaxVerificationNo(CancelOwnerHead head){
		String hql = "select max(s.verificationNo) from ECSSection  s where s.company=? and s.cancelOwnerHead=?";
		List<Number> ls = find(hql,new Object[]{CommonUtils.getCompany(),head});
		if(ls == null || ls.isEmpty()){
			return 0;
		}else{
			Number n = ls.get(0);
			return n == null ? 0 : n.intValue();
		}
	}
	/*******************************************盘点核查批次结束************************************/
		
	public <T extends BaseScmEntity> List<T> findByECSSection(ECSSection ecsSection, String entityName,Integer seqNum) {
		String hql = "select e from " + entityName + " e join fetch e.section where e.section.id = ?";
		if(seqNum != null){
			hql += " and e.seqNum = " + seqNum;
		}
		return super.find(hql + " order by e.serialNumber ", ecsSection.getId());
	}
	
	public <T extends BaseScmEntity> List<T> findByECSSectionAndVersion(ECSSection ecsSection, String entityName,Integer seqNum,String version) {
		String hql = "select e from " + entityName + " e join fetch e.section where e.section.id = ?";
		if(seqNum != null){
			hql += " and e.seqNum = " + seqNum;
		}
		if(version != null){
			hql += "and e.version = " + version;
		}
		return super.find(hql + " order by e.serialNumber ", ecsSection.getId());
	}
	
	public List findTableSize(ECSSection ecsSection, String entityName,Boolean isNotNull){
		String hql = "select count(e.id) from " + entityName + " e where e.section.id = ?";
		if(isNotNull!=null&&isNotNull){
			hql +=" and e.seqNum is not null";
		}
		return super.find(hql,ecsSection.getId());
	}
	/**
	 * 核销表头
	 */
	public CancelOwnerHead findCancelOwnerHead(String cancelTimes){
		String hql = "select a from CancelOwnerHead a where a.company=? and a.cancelTimes = ?";
		List list = super.find(hql,new Object[]{CommonUtils.getCompany(),cancelTimes});
		if(list.size()>0){
			return (CancelOwnerHead)list.get(0);
		}else{
			return null;
		}
	}
	public <T extends BaseScmEntity> List<T> findByECSSection(ECSSection ecsSection, String entityName) {
		return this.findByECSSection(ecsSection, entityName,null);
	}
	
	public int deleteByECSSection(ECSSection ecsSection, String entityName) {
		return batchUpdateOrDelete("delete from " + entityName + " where section=? ",ecsSection);
	}
	
	public List findSumImportQtyByECSSection(ECSSection ecsSection) {
		String hql = "select a.emsNo,a.seqNum,sum(a.totalImportAmount) from ECSCustomsCountImg a  where a.section.id = ?" ;
		return super.find(hql + " group by a.emsNo,a.seqNum ", ecsSection.getId());
	}
	
	public List findSumWasteQtyByECSSection(ECSSection ecsSection) {
		String hql = "select a.emsNo,a.seqNum,sum(a.wasteAmount) from ECSCustomsCountExgResolve a  where a.section.id = ?" ;
		return super.find(hql + " group by a.emsNo,a.seqNum ", ecsSection.getId());
	}
	
	public boolean isExistByECSSection(ECSSection section,String entityName){
		 List<Number> ls = super.find("select count(a.id) from " + entityName + " a join a.section where a.section = ? ", section);
		 if(ls != null && !ls.isEmpty()){
			 Number n = ls.get(0);
			 return n == null ? Boolean.FALSE : n.intValue() > 0;
		 }		 
		 return Boolean.FALSE;
	}
	
	public <T extends ECSBaseStockItem> List<T> findECSBaseStockItemByECSSection(ECSSection section, Class<T> clazz) {
		String hql = "select "
				+ "new com.bestway.bcus.checkstock.entity.ECSBaseStockItem('', a.seqNum, a.hsName, a.hsSpec, a.hsUnit, a.hsAmount) "
				+ "from " + clazz.getName() + " a "
				+ "where a.section.id = ?";
		
		return super.find(hql, new Object[]{section.getId()});
	}
	
	
	public <T extends BaseScmEntity> void batchSaveECSEnity(List<T> list) {		
		Session s = getSession();
		s.setCacheMode(CacheMode.IGNORE);
		
		int size = list.size();
		int m = 0;
		T obj = null;
		for (int i = 0; i < size; i++, m++) {
			if (m == 1000) {
				s.flush();// 将一级缓存中的数据同步到数据库中
				s.clear();// 清空一级缓存中的数据，这样不至于造成内存溢出
				m = 0;
			}
			obj = list.get(i);
			obj.setCompany(CommonUtils.getCompany());
			s.save(obj);
		}
		s.flush();
		s.clear();
		System.gc();
	
	}
	
	public <T extends BaseScmEntity> void batchUpdateECSEnity(List<T> list) {		
		Session s = getSession();

		s.setCacheMode(CacheMode.IGNORE);
		int size = list.size();
		int m = 0;
		T obj = null;
		for (int i = 0; i < size; i++, m++) {
			if (m == 1000) {
				s.flush();// 将一级缓存中的数据同步到数据库中
				//s.clear();// 清空一级缓存中的数据，这样不至于造成内存溢出
				m = 0;
			}
			obj = list.get(i);
			s.update(obj);
		}
		s.flush();
		s.clear();
		System.gc();
	
	}
	
	public <T extends BaseScmEntity> void batchSaveOrUpdateDirect(List<T> ls){		
		this.batchSaveOrUpdateDirect(ls,null);
	}
	
	public <T extends BaseScmEntity> void batchSaveOrUpdateDirect(List<T> ls,ProgressInfo info){		
		if(ls != null && !ls.isEmpty()){			
//			Request request = CommonUtils.getRequest();
//			ProgressInfo info = null;
//			if(request != null && StringUtils.isNotBlank(request.getTaskId())){
//				info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
//				info.setMethodName("正在保存数据，共"+ls.size()+"条");
//				info.setTotalNum(ls.size()/100);
//				info.setCurrentNum(0);
//			}
			if(info!=null){
				info.setMethodName("正在保存数据，共"+ls.size()+"条");
				info.setTotalNum(ls.size()/100);
				info.setCurrentNum(0);
			}
			
			Session s = getSession();
			s.setCacheMode(CacheMode.IGNORE);			
			int size = ls.size();
			int m = 0;
			T obj = null;
			for (int i = 0; i < size; i++, m++) {
				if (m == 1000) {
					s.flush();// 将一级缓存中的数据同步到数据库中
					s.clear();// 清空一级缓存中的数据，这样不至于造成内存溢出
					m = 0;
				}
				obj = ls.get(i);
				obj.setCompany(CommonUtils.getCompany());
				s.saveOrUpdate(obj);
				if(info != null && i > 0 && i % 100 ==0){
					info.setCurrentNum(info.getCurrentNum()+1);
				}
			}
			s.flush();
			s.clear();
			System.gc();					
		}
	}
	
	
	/**
	 * 获得当前帐册号
	 * @return
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		// 确定帐册
		List<EmsHeadH2k> head2kList = find(
				"select a from EmsHeadH2k a where a.company.id= ? and a.historyState=? order by a.modifyTimes DESC",
				new Object[] { CommonUtils.getCompany().getId(),
						new Boolean(false) });
		EmsHeadH2k emsHeadH2k = null;
		if(head2kList.size() > 1) {
			for (int i = 0; i < head2kList.size(); i++) {
				if(DeclareState.CHANGING_EXE.equals(head2kList.get(i).getDeclareState())) {
					emsHeadH2k = head2kList.get(i);
					break;
				}
			}
			if(emsHeadH2k == null) {
				emsHeadH2k = head2kList.get(0);
			}
			
		} else {
			emsHeadH2k = head2kList.get(0);
		}
		
		return emsHeadH2k;
	}
	
	
	/**
	 * 根据指定备案序号和版本列表 查询 bom。
	 * @param seqNumVersionMap
	 * @return
	 */
	public List<BomTemp> findEmsHeadH2kBomBySection(
			ECSSection section, String clazz) {
		StringBuilder hql = new StringBuilder();
		hql.append(
				"select distinct new com.bestway.bcus.checkstock.entity.temp.BomTemp("
						+ "e.seqNum, e.emsNo, "
						+ "b.seqNum, b.name, b.spec, b.unit.name, b.unitWear, b.wear,b.emsHeadH2kVersion.version) ")
		.append("from " + clazz + " e, EmsHeadH2kBom b ")
		.append("where e.section.id = ? ")
		.append("	and e.seqNum = b.emsHeadH2kVersion.emsHeadH2kExg.seqNum ")
		.append("	and e.version = b.emsHeadH2kVersion.version ")
		.append("	and b.emsHeadH2kVersion.emsHeadH2kExg.modifyMark = ? ")
		.append("	and e.company.id = ? ");
		System.out.println("hqlhql" + hql);
		return find(hql.toString(), new Object[] { section.getId(),
				ModifyMarkState.UNCHANGE, CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 根据指定料号列表查询 bom。
	 * 
	 * @param ptNoSet
	 * @return
	 */
	public List<BadBomTemp> findMaterialBomDetailByPtNoSet(ECSSection section,
			String clazz) {
		String hql = "	select new com.bestway.bcus.checkstock.entity.temp.BadBomTemp("
				+ " 	a.version.master.materiel.ptNo, a.version.version, "
				+ " 	a.materiel.ptNo, a.unitWaste, a.waste, a.unitUsed,"
				+ " 	a.materiel.factoryName, a.materiel.factorySpec, a.materiel.calUnit.name"
				+ " )"
				+ " from MaterialBomDetail a "
				+ " where a.company.id = ? ";

		return find(hql, new Object[] { CommonUtils.getCompany().getId() });
	}
	
	
	/**
	 * 查询 所有成品的最大bom版本。(归并前成品关联电子账册bom版本)
	 * @return
	 */
	public List<Object[]> findMaxVersion() {
		StringBuilder hql = new StringBuilder();
		hql.append(
						"select e.ptNo, max(v.version)")
				.append("from EmsHeadH2kVersion v, EmsEdiMergerExgBefore e ")
				.append("where v.emsHeadH2kExg.seqNum = e.emsEdiMergerExgAfter.seqNum ")
				.append("	and v.emsHeadH2kExg.modifyMark = ? and e.modifyMark = ? ")
				.append("	and v.company.id = ? and e.company.id = ? ")
				.append("group by e.ptNo ");
		
		
		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				ModifyMarkState.UNCHANGE, CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId() });
	}
	
	
	/**
	 * 查询 所有成品的bom版本(归并前成品关联电子账册bom版本)。
	 * 
	 * @return
	 */
	public List<Object[]> findAllVersion() {
		StringBuilder hql = new StringBuilder();
		hql.append(
						"select e.ptNo, v.version ")
				.append("from EmsHeadH2kVersion v, EmsEdiMergerExgBefore e ")
				.append("where v.emsHeadH2kExg.seqNum = e.emsEdiMergerExgAfter.seqNum ")
				.append("	and v.emsHeadH2kExg.modifyMark = ? and e.modifyMark = ? ")
				.append("	and v.company.id = ? and e.company.id = ? ");

		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				ModifyMarkState.UNCHANGE, CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 返回报关单价格，时间早的排前面，排除退厂返工
	 * 
	 * @param head
	 * @return
	 */
	public List<Object[]> findPriceFromCustomsDeclaration(ECSSection section) {

		String hql = "select a.commSerialNo, a.commUnitPrice, max(a.baseCustomsDeclaration.impExpDate) from CustomsDeclarationCommInfo a"
				+ " where a.baseCustomsDeclaration.impExpFlag = ?"
				+ " and a.baseCustomsDeclaration.effective = ?"
				+ " and a.commUnitPrice != null"
				+ " and a.company.id = ? and a.baseCustomsDeclaration.impExpDate >= ?"
				+ " and a.baseCustomsDeclaration.impExpDate <= ?"
				+ " group by a.commSerialNo, a.commUnitPrice, a.baseCustomsDeclaration.impExpDate"
				+ " order by a.baseCustomsDeclaration.impExpDate";

		return this.find(hql,
				new Object[] { ImpExpFlag.IMPORT, Boolean.TRUE,
						CommonUtils.getCompany().getId(), section.getBeginDate(),
						section.getEndDate() });
	}
	
	
	/**
	 * 查询 所有电子账册成品的bom版本。
	 * 
	 * @return
	 */
	public List<Object[]> findAllEmsVersion() {
		StringBuilder hql = new StringBuilder();
		hql.append(
						"select v1.emsHeadH2kExg.seqNum, v1.version ")
				.append("from EmsHeadH2kVersion v1 ")
				.append("where v1.emsHeadH2kExg.modifyMark = ? ")
				.append("	and v1.company.id = ? ");

		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 查询 所有电子账册成品的最大bom版本。
	 * @return
	 */
	public List<Object[]> findMaxEmsVersion() {
		StringBuilder hql = new StringBuilder();
		hql.append(
						"select v1.emsHeadH2kExg.seqNum, max(v1.version) ")
				.append("from EmsHeadH2kVersion v1 ")
				.append("where v1.emsHeadH2kExg.modifyMark = ? ")
				.append("	and v1.company.id = ? ")
				.append("group by v1.emsHeadH2kExg.seqNum ");

		
		return find(hql.toString(), new Object[] { ModifyMarkState.UNCHANGE,
				CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 显示电子帐册料件
	 * 
	 * @param emsNo
	 *            电子帐册表头
	 * @return
	 */
	public List<EmsHeadH2kImg> findEmsHeadH2kImg(String emsNo) {
		return this
				.find("select a from EmsHeadH2kImg a where a.modifyMark = ? and a.emsHeadH2k.emsNo=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE, emsNo,
								CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 显示电子帐册成品
	 * 
	 * @param emsNo
	 *            电子帐册表头
	 * @return
	 */
	public List<EmsHeadH2kExg> findEmsHeadH2kExg(String emsNo) {
		return this
				.find("select a from EmsHeadH2kExg a where a.modifyMark = ? and a.emsHeadH2k.emsNo=? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE, emsNo,
								CommonUtils.getCompany().getId() });
	}
	/**
	 * 查询优惠税率
	 * @param emsNo 电子账册编码
	 * @param seqNum 备案序号
	 * @return
	 */
	public Double findComplexLowrate(String emsNo, Integer seqNum) {
		List list = this.find("select a.complex.lowrate from EmsHeadH2kImg a where a.modifyMark = ? and " +
						" a.emsHeadH2k.emsNo=?  and a.seqNum = ? and a.company.id= ? order by a.seqNum",
						new Object[] { ModifyMarkState.UNCHANGE, emsNo,seqNum,CommonUtils.getCompany().getId() });
		if(list != null && !list.isEmpty()){
			String s = (String)list.get(0);			
			return NumberUtils.isNumber(s) ? NumberUtils.toDouble(s) : 0d;
		}
		return 0d;
	}
	/**
	 * 获取自用核销的上一周
	 * @param cancelOwnerHead
	 * @return
	 */
	public CancelOwnerHead getLastCanceOwnerlHead(CancelOwnerHead cancelOwnerHead) {
		String hql = "select a from CancelOwnerHead a where a.endDate < ? and a.company.id = ? order by a.endDate desc ";
		List list = findPageList(hql,new Object[]{cancelOwnerHead.getEndDate(),CommonUtils.getCompany().getId()}, 0, 1);
		if(list != null && !list.isEmpty()){
			return (CancelOwnerHead)list.get(0);
		}
		return null;
	}
	/**
	 * 根据自用核销获取核销料件平均单价
	 * @param head
	 * @return
	 */
	public List<Object[]> getCancelOwnerHeadAvgPrice(CancelOwnerHead head){
		String hql = "select a.emsSeqNum,a.averagePrice from  CancelOwnerImgResult a where a.cancelHead = ? ";
		return find(hql,head);
	}
	
	/**
	 * 合计折算报关数量
	 * @param section
	 * @param clazz 残次品库原材料，成品折料，半成品折料的
	 */
	public List convertECSStockBySeqNum(ECSSection section,Class clazz){
		String hql = "	select a.seqNum,a.hsName,a.hsSpec,a.hsUnit,sum(a.hsAmount)  "
				+ " from "+clazz.getName()+" a "
				+ " where a.company.id = ?  and a.section.id = ? group by a.seqNum,a.hsName,a.hsSpec,a.hsUnit ";
		return find(hql, new Object[] { CommonUtils.getCompany().getId() ,section.getId()});
	}
	
	public Long countByECSSection(ECSSection section, String entityName,
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
				hsql += " and  a." + property + " like ? ";
				paramters.add(value+"%");
			} else {
				hsql += " and  a." + property + " = ? ";
				paramters.add(value);
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
	 * 查询核资料附件
	 * @param section
	 * @param boo
	 * @return
	 */
	public List findECSAttachmentManagement(ECSSection section,Boolean boo){
		List<Object> paramters = new ArrayList<Object>();
		String hql = " select a from ECSAttachmentManagement a ";
		hql += " where a.company.id = ? ";
		paramters.add(CommonUtils.getCompany().getId());
		if(section!=null){
			hql+=" and a.ecssection = ?";
			paramters.add(section);
		}
		if(boo){
			hql+=" and a.parentId is null ";
		}else{
			hql+=" and a.parentId is not null ";
		}
		hql +="order by a.provideUnit";
		List list = find(hql, paramters.toArray());
		return list;
	}
	
	public void deleteECSAttachmentManagement(String id){
		List<Object> paramters = new ArrayList<Object>();
		String hql = "delete from ECSAttachmentManagement a where a.id = ? or parentId= ?";
		paramters.add(id);
		paramters.add(id);
		batchUpdateOrDelete(hql,paramters.toArray());
	}
	
	/**
	 * 查询所有盘点核查批次
	 * 
	 * @param isExist 是否在附件管理中，已经存在
	 * @return
	 */
	public List<ECSSection> findAttachmentSection(Boolean isExist){
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select s from ECSSection s join fetch s.cancelOwnerHead " +
				"where s.company = ? ";
		paramters.add(CommonUtils.getCompany());
		if(isExist!=null&&isExist==true){
			hql +=" and s.isExist = ? ";
			paramters.add(true);
		}else{
			hql +=" and (s.isExist = ? or s.isExist is null)";
			paramters.add(false);
		}
		hql +=" order by s.verificationNo desc ,s.cancelOwnerHead.cancelTimes desc ";
		return find(hql,paramters.toArray());
	}
}
