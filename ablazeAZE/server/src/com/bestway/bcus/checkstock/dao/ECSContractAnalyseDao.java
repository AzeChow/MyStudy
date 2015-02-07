package com.bestway.bcus.checkstock.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.CurrRate;
/**
 * 
 * @author chl
 */
public class ECSContractAnalyseDao extends BaseDao {
	
//	/**
//	 * 从自用核销报关单获得成品情况统计表数据
//	 * @param section 盘点核查批次
//	 * @param cancelTimes 核销次数
//	 * @param beginDate 报关单申报开始日期
//	 * @param endDate 报关单申报结束日期
//	 */
//	public List findECSCustomsCountExgFromCancel(ECSSection section){
//		List<Object> parameters = new ArrayList<Object>();
//		// 帐册编号 成品备案序号 版本号 成品名称 型号规格 计量单位 商品数量 报关单标志 出口类型
//		String hql = "select  a. baseCustomsDeclaration.emsHeadH2k,a.commSerialNo, a.version, a.commName,a.commSpec,a.unit.name, "
//				+ "  a.commAmount, a.baseCustomsDeclaration.impExpFlag,a.baseCustomsDeclaration.impExpType   "
//				+ " from CustomsDeclarationCommInfo  as a where a.company.id =? and a.baseCustomsDeclaration.impExpType in(?,?,?,?) " 
//				+ " and a.baseCustomsDeclaration.customsDeclarationCode "
//				+ "  in (select b.customNo from CancelOwnerCustomsDeclara as b where b.cancelHead.cancelTimes = ?";
//		parameters.add(CommonUtils.getCompany().getId());
//		parameters.add(ImpExpType.DIRECT_EXPORT);
//		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
//		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
//		parameters.add(ImpExpType.REWORK_EXPORT);
//		parameters.add(section.getCancelOwnerHead().getCancelTimes());
//		if (section.getBeginDate() != null) {
//			hql += " and  b.declareDate >= ?   ";
//			parameters.add(section.getBeginDate());
//		}
//		if (section.getEndDate() != null) {
//			hql += " and b.declareDate <= ?  ";
//			parameters.add(section.getEndDate());
//		}
//		hql += " ) ";
//		List reList = this.find(hql, parameters.toArray());
//		return reList;
//	}
	/**
	 * 获得成品统计表
	 * @param section
	 * @return
	 */
	public List  findECSCustomsCountExgBySection(ECSSection section,Integer seqNum){
		List<Object> parameters = new ArrayList<Object>();
		// 帐册编号 成品备案序号 版本号 成品名称 型号规格 计量单位 商品数量 报关单标志 出口类型
		String hql = "select  a from ECSCustomsCountExg  as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hql += " and  a.section.id = ? ";
			parameters.add(section.getId());
		}
		if(seqNum != null){
			hql += " and a.seqNum = ? ";
			parameters.add(seqNum);
		}
		hql += " order by a.seqNum ";
		return this.find(hql, parameters.toArray());
	}
	
	/**
	 * 获得成品统计表
	 * @param section
	 * @return
	 */
	public List  findECSCustomsCountExgResolveBySection(ECSSection section){
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select  a from ECSCustomsCountExgResolve  as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hql += " and  a.section.id = ? ";
			parameters.add(section.getId());
		}
		hql += " order by a.ecsCustomsCountExg.seqNum ,a.seqNum ";
		return this.find(hql, parameters.toArray());
	}
	
	/**
	 * 获得成品统计折料
	 * @param section
	 * @return
	 */
	public List  findECSCustomsCountExgResolveBySection(ECSSection section,Integer seqNum){
		List<Object> parameters = new ArrayList<Object>();
		//账册编号 备案序号 版本号 成品名称 成品规格， 料件序号 料件名称 料件规格 计量单位 单耗 损耗 总耗用 总出口 
		String hql = "select  a.emsNo,a.ecsCustomsCountExg.seqNum,a.ecsCustomsCountExg.version," +
				" a.ecsCustomsCountExg.commName,a.ecsCustomsCountExg.commSpec,a.seqNum," +
				" a.commName,a.commSpec,a.unit,a.unitWaste,a.waste,a.wasteAmount,a.ecsCustomsCountExg.totalExportAmount" +
				"  from ECSCustomsCountExgResolve  as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hql += " and  a.section.id = ?  ";
			parameters.add(section.getId());
		}
		if(seqNum != null){
			hql += " and a.seqNum = ? ";
			parameters.add(seqNum);
		}
		hql += " order by a.ecsCustomsCountExg.seqNum,a.seqNum";
		return this.find(hql, parameters.toArray());		
	}
	/**
	 * 根据批次号与成品统计表相关的BOM
	 * 
	 * @param countExgList
	 *           成品统计表
	 */
	public Iterator<Object[]> findEmsheadh2kBomCountExgBySection(ECSSection section){
		List<Object> parameters = new ArrayList<Object>();
		List reList = new ArrayList();
		if(section!=null){
			//帐册编号 成品情况统计 料件备案序号 料件名称 型号规格 单耗 损耗  计量单位
//			String hql = "select b.emsNo,b,a.seqNum,a.name,a.spec,a.unitWear,a.wear,a.unit.name  " +
//					"  from ECSCustomsCountExg b, EmsHeadH2kBom as a  where a.company.id =?  ";
//			parameters.add(CommonUtils.getCompany().getId());
//			hql += " and b.section.id=? and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=b.seqNum and a.emsHeadH2kVersion.version=b.version   ";
//			hql += "  order by b. version, b. seqNum";
//			parameters.add(section.getId());
//			reList = this.find(hql, parameters.toArray());
//			return reList;
			Query  query = getSession().createQuery("select b.emsNo,b,a.seqNum,a.name,a.spec,a.unitWear,a.wear,a.unit.name  " +
					" from ECSCustomsCountExg b, EmsHeadH2kBom as a  where a.company.id  =:companyId  and b.section.id =:sectionId "+
					" and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=b.seqNum " +
					" and a.emsHeadH2kVersion.version=b.version  order by b.version, b.seqNum");
			 query.setParameter("companyId",CommonUtils.getCompany().getId());   
			 query.setParameter("sectionId", section.getId());
			 final ScrollableResults results = query.setReadOnly(true).setCacheable(false).
						scroll(ScrollMode.FORWARD_ONLY);
			 return new Iterator<Object[]>() {
				public boolean hasNext() {
					return results.next();
				}
				public Object[] next() {
					return results.get();
				}				 	
				public void remove() {
					 throw new UnsupportedOperationException("remove");
				}
			};			
		}
		return IteratorUtils.emptyIterator();
	}
	
	/**
	 * 根据批次号与成品统计表统计相关的BOM数量
	 * 
	 * @param countExgList
	 *           成品统计表
	 */
	public int countEmsheadh2kBomCountExgBySection(ECSSection section){
		List<Object> parameters = new ArrayList<Object>();
		List reList = new ArrayList();
		ScrollableResults results = null;
		Integer num=0;
		if(section!=null){
			 Query  query = getSession().createQuery("select  count(*) " +
						" from ECSCustomsCountExg b, EmsHeadH2kBom as a  where a.company.id  =:companyId  and b.section.id =:sectionId "+
						" and a.emsHeadH2kVersion.emsHeadH2kExg.seqNum=b.seqNum " +
						" and a.emsHeadH2kVersion.version=b.version ");
				 query.setParameter("companyId",CommonUtils.getCompany().getId());   
				 query.setParameter("sectionId", section.getId());
				 results = query.setReadOnly(true).setCacheable(false).
							scroll(ScrollMode.FORWARD_ONLY);
				while(results.next()){
					num = (Integer)results.getLong(0).intValue(); 
				}
			return num;
		}
		return num;
	}
	
	
	public int deleteECSBySection(ECSSection section, String entityName) {
		return batchUpdateOrDelete("delete " + entityName + " v where v.section.id = ?", section.getId());
	}
	/**
	 * 查询盘点核查批次的料件报关明细数据 
	 * 使用申报日期+自用核销批次过滤报关单料件
	 * @param section
	 * @return List<[账册编号，进出口标记，进出口类型，备案号，申报日期，报关单号，计量单位(Unit)，商品名称，商品规格，商品数量]>
	 */
	public List<Object[]> findDeclarationMaterialByEcsSection(ECSSection section){		
		StringBuilder hql = new StringBuilder("select b.emsHeadH2k,c.commSerialNo,b.customsDeclarationCode,b.declarationDate," +
				"b.impExpType,b.tradeMode.name,c.unit.name,c.commName,c.commSpec,c.commAmount,c.commUnitPrice,c.commTotalPrice,b.currency.name,b.impExpFlag ");
		hql.append(" from CustomsDeclarationCommInfo c,CustomsDeclaration b ,CancelOwnerCustomsDeclara a ");
		hql.append(" where c.baseCustomsDeclaration.id=b.id and a.customNo=b.customsDeclarationCode ");
		hql.append(" and a.cancelHead.id=? and a.declareDate >= ? and a.declareDate <= ? and b.effective = ? ");
		//进口、出口
		hql.append(" and b.impExpFlag in (").append(ImpExpFlag.IMPORT).append(",").append(ImpExpFlag.EXPORT).append(") ");
		//料件进口、料件转厂、余料结转进口、余料结转出口、退料出口
		hql.append(" and b.impExpType in(").append(ImpExpType.DIRECT_IMPORT).append(",").
			append(ImpExpType.TRANSFER_FACTORY_IMPORT).append(",").append(ImpExpType.REMAIN_FORWARD_IMPORT).
			append(",").append(ImpExpType.REMAIN_FORWARD_EXPORT).append(",").append(ImpExpType.BACK_MATERIEL_EXPORT).append(") ");
		
		return find(hql.toString(), new Object[]{section.getCancelOwnerHead().getId(),section.getBeginDate(),section.getEndDate(),Boolean.TRUE});		
	}
	
	/**
	 * 查询美元转人民币的汇率
	 * @return
	 */
	public Double findCurrRate(){
		String hql = "select a from CurrRate a where a.company.id = ? "
								+ " and a.curr.code=? and a.curr1.code=? "
								+ "order by a.createDate desc ";
		List list = this.findListNoCache(hql, new Object[] {CommonUtils.getCompany().getId(),
				"142","502"} ,-1, 1);
		if (list.size() != 0) {
			CurrRate cr = (CurrRate) list.get(0);
			return cr.getRate() == null ? 0.0 : cr.getRate();
		} else {
			return 0.0;
		}
	}
	
	/**
	 * 查询盘点核查批次的成品报关明细数据 
	 * @param section 盘点核查批次
	 * @param cancelTimes 核销次数
	 * @param beginDate 报关单申报开始日期
	 * @param endDate 报关单申报结束日期
	 */
	public List findDeclarationProductByEcsSection(ECSSection section){
		List<Object> parameters = new ArrayList<Object>();
		// 帐册编号 成品备案序号 版本号 成品名称 型号规格 计量单位 商品数量 报关单标志 出口类型
		String hql =" select a.baseCustomsDeclaration.emsHeadH2k,a.commSerialNo,a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.declarationDate," +
				"a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.tradeMode.name,a.unit.name,a.commName,a.commSpec,a.commAmount,a.commUnitPrice,a.commTotalPrice,a.baseCustomsDeclaration.currency.name, " +
				"a.version,a.baseCustomsDeclaration.impExpFlag from CustomsDeclarationCommInfo  as a where a.company.id =? and a.baseCustomsDeclaration.impExpType in(?,?,?,?) " 
				+ " and a.baseCustomsDeclaration.customsDeclarationCode "
				+ "  in (select b.customNo from CancelOwnerCustomsDeclara as b where b.cancelHead.cancelTimes = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ImpExpType.DIRECT_EXPORT);
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		parameters.add(ImpExpType.REWORK_EXPORT);
		parameters.add(section.getCancelOwnerHead().getCancelTimes());
		if (section.getBeginDate() != null) {
			hql += " and  b.declareDate >= ?   ";
			parameters.add(section.getBeginDate());
		}
		if (section.getEndDate() != null) {
			hql += " and b.declareDate <= ?  ";
			parameters.add(section.getEndDate());
		}
		hql += " ) ";
		List reList = this.find(hql, parameters.toArray());
		return reList;
	}
//	/**
//	 * 查询盘点核查批次的报关料件数据 
//	 * 使用申报日期+自用核销批次过滤报关单料件
//	 * @param section
//	 * @return List<[账册编号，进出口标记，进出口类型，备案号（归并序号），计量单位(Unit)，商品名称，商品规格，商品数量]>
//	 */
//	public List<Object[]> findCustomsDeclarationMaterialByEcsSection(ECSSection section){		
//		StringBuilder hql = new StringBuilder("select b.emsHeadH2k,b.impExpFlag,b.impExpType,c.commSerialNo,c.unit.name,c.commName,c.commSpec,sum(c.commAmount) ");
//		hql.append(" from CustomsDeclarationCommInfo c,CustomsDeclaration b ,CancelOwnerCustomsDeclara a ");
//		hql.append(" where c.baseCustomsDeclaration.id=b.id and a.customNo=b.customsDeclarationCode ");
//		hql.append(" and a.cancelHead.id=? and a.declareDate >= ? and a.declareDate <= ? and b.effective = ? ");
//		//进口、出口
//		hql.append(" and b.impExpFlag in (").append(ImpExpFlag.IMPORT).append(",").append(ImpExpFlag.EXPORT).append(") ");
//		//料件进口、料件转厂、余料结转进口、余料结转出口、退料出口
//		hql.append(" and b.impExpType in(").append(ImpExpType.DIRECT_IMPORT).append(",").
//			append(ImpExpType.TRANSFER_FACTORY_IMPORT).append(",").append(ImpExpType.REMAIN_FORWARD_IMPORT).
//			append(",").append(ImpExpType.REMAIN_FORWARD_EXPORT).append(",").append(ImpExpType.BACK_MATERIEL_EXPORT).append(") ");
//		
//		hql.append(" group by b.emsHeadH2k,b.impExpFlag,b.impExpType,c.commSerialNo,c.unit.name,c.commName,c.commSpec ");
//		return find(hql.toString(), new Object[]{section.getCancelOwnerHead().getId(),section.getBeginDate(),section.getEndDate(),Boolean.TRUE});		
//	}
	/**
	 * 查询自用核销物料期初 
	 * @param head 自用核销表头
	 * @return List<[备案号（归并序号），计量单位(Unit)，商品数量]>
	 */
	public List<Object[]> findCancelOwnerMaterialBeginNum(CancelOwnerHead head){
		String hql = "select a.cancelHead.emsNo,a.emsSeqNum,a.name,a.spec,a.unit,a.beginNum from CancelOwnerImgResult a where a.cancelHead.id = ? ";
		return find(hql,head.getId());
	}
	/**
	 * 判断是否存在某批次的资料
	 * 
	 * @param section
	 *            批次
	 * @param entityName
	 *            表名
	 * @return
	 */
	public boolean isExistECSBySection(ECSSection section,String entityName) {
		boolean isExist = false;
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select count(*) from " + entityName + " as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hql += " and a.section.id = ?";
			parameters.add(section.getId());
		}
		List reList =  this.find(hql, parameters.toArray());
		if(reList!=null){
			Long count = (Long)reList.get(0);
			if(count>=Long.valueOf(1)){
				isExist = true;
			}
		}
		return isExist;
	}
	/**
	 * 获得账册分析表
	 * @param section
	 * @return
	 */
	public List  findECSContractAnalyseBySection(ECSSection section,Integer seqNum){
		List<Object> parameters = new ArrayList<Object>();
		//帐册编号 备案序号 料件名称 型号规格 计量单位 进口数量 成品耗用 结余数量
		String hql = "select  a.emsNo,a.seqNum,a.commName,a.commSpec,a.unit,a.importAmount," +
				" a.wasteAmount,a.resultAmount  from ECSContractAnalyse  as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hql += " and  a.section.id = ?  ";
			parameters.add(section.getId());
		}		
		if(seqNum !=null){
			hql+= " and a.seqNum = ? ";
			parameters.add(seqNum);
		}
		hql += " order by a.seqNum ";
		List reList = this.find(hql, parameters.toArray());
		return reList;
	}
	/***
	 * 获得电子账册管理料件
	 * @return
	 */
	public List findEmsHeadH2kImg(){
		List<Object> parameters = new ArrayList<Object>();
		//帐册编号 备案序号 料件名称 型号规格 计量单位
		String hql = "select  a.emsHeadH2k.emsNo,a.seqNum,a.name,a.spec,a.unit.name " +
				" from EmsHeadH2kImg  as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		hql += " order by a.seqNum ";
		List reList = this.find(hql, parameters.toArray());
		return reList;
	}
	/**
	 * 根据批次号分页查询
	 * @param section 批次号
	 * @param index 开始值
	 * @param length 长度
	 * @param property 对象属性
	 * @param value 对象值
	 * @param isLike 是否模糊
	 * @return
	 */
	public List<Object> findPageECSBySection(ECSSection section,int index,
			int length, String property, Object value, boolean isLike,String table){
				List<Object> paramters = new ArrayList<Object>();
				String hsql = "select a from "+table+" as a where a.company.id= ? ";
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
				hsql += " order  by  a.seqNum ";
				System.out.println("--:" + hsql);
				List customsList = super.findPageList(hsql, paramters.toArray(), index, length);
				return customsList;
	}
	
}
