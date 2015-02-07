package com.bestway.bcs.verification.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.verification.entity.VFContractAnalyse;
import com.bestway.bcs.verification.entity.VFCustomsCountExg;
import com.bestway.bcs.verification.entity.VFCustomsCountExgConvert;
import com.bestway.bcs.verification.entity.VFCustomsCountImg;
import com.bestway.bcs.verification.entity.VFCustomsExg;
import com.bestway.bcs.verification.entity.VFCustomsImg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.CurrRate;
/**
 * 
 * @author chl
 */
@SuppressWarnings("rawtypes")
public class VFContractAnalyseDao extends BaseDao {
	
	/**
	 * 查询汇率
	 * @return
	 */
	public List findCurrRate(){
		String hql = "select a from CurrRate a where a.company.id = ? order by a.createDate desc ";
		List list = this.find(hql, new Object[] {CommonUtils.getCompany().getId()});
		return list;
	}
	
	/**
	 * 根据批次号获得来自报关单明细的资料
	 * @param section 批次
	 * @param isImgOrExg 料件true或成品false 
	 * @return
	 */
	public List findCustomsDeclarationComminfoBySection(VFSection section,boolean isImgOrExg){
		List<Object> parameters = new ArrayList<Object>();
		String hql ="";
		if(isImgOrExg){//料件  
			//备案资料库备案序号  手册号  序号   报关单号 申报日期  商品名称 
			//商品规格 商品数量  商品单价 单位  总金额 进出口类型 贸易方式 企业名称 
			hql = "select b.seqNum,a.baseCustomsDeclaration.emsHeadH2k,a.commSerialNo," +
					" a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.declarationDate," +
					" a.commName,a.commSpec,a.commAmount,a.commUnitPrice,a.unit.name,a.commTotalPrice, " +
					" a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.tradeMode.name,a.company.name, " +
					" b.contract.curr.code from BcsCustomsDeclarationCommInfo as a,ContractImg as b" +
					" where a.company.id=? and a.baseCustomsDeclaration.emsHeadH2k = b.contract.emsNo" +
					" and a.commSerialNo=b.seqNum and b.contract.declareState = ? " +
					" and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(DeclareState.PROCESS_EXE);
			parameters.add(Boolean.TRUE);
			// 报关单类型==1(出口)进出口类型:进出口类型==余料结转出口||退运出口
				hql += " and ((a.baseCustomsDeclaration.impExpFlag = ? and a.baseCustomsDeclaration.impExpType in (?,?))";
				parameters.add(ImpExpFlag.EXPORT);
				parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
				parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
				
			 // 报关单类型==0(进口)，进出口类型==料件进口||结转进口||海关批准内销||余料结转进口
				hql += " or (a.baseCustomsDeclaration.impExpFlag = ? and a.baseCustomsDeclaration.impExpType in(?,?,?,?))) ";
				parameters.add(ImpExpFlag.IMPORT);
				parameters.add(ImpExpType.DIRECT_IMPORT);
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
				parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
				parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
				System.out.println("HSQL!!!!!!：" + hql);

		}else{
			hql = "select b.seqNum,a.baseCustomsDeclaration.emsHeadH2k,a.commSerialNo," +
					" a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.declarationDate," +
					" a.commName,a.commSpec,a.commAmount,a.commUnitPrice,a.unit.name,a.commTotalPrice, " +
					" a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.tradeMode.name,a.company.name, " +
					" b.contract.curr.code from BcsCustomsDeclarationCommInfo as a,ContractExg as b " +
					" where a.company.id=? and a.baseCustomsDeclaration.emsHeadH2k = b.contract.emsNo" +
					" and a.commSerialNo=b.seqNum and b.contract.declareState = ? " +
					" and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(DeclareState.PROCESS_EXE);
			parameters.add(Boolean.TRUE);
			// 报关单类型==1(出口)进出口类型:进出口类型==成品出口||转厂出口||返工复出
				hql += " and ((a.baseCustomsDeclaration.impExpFlag = ? and a.baseCustomsDeclaration.impExpType in (?,?,?)) ";
				parameters.add(ImpExpFlag.EXPORT);
				parameters.add(ImpExpType.DIRECT_EXPORT);
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
				parameters.add(ImpExpType.REWORK_EXPORT);
			 // 报关单类型==0(进口)，进出口类型==退厂返工
				hql += " or (a.baseCustomsDeclaration.impExpFlag = ? and a.baseCustomsDeclaration.impExpType in(?))) ";
				parameters.add(ImpExpFlag.IMPORT);
				parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		if(section!=null){
			hql +="and a.baseCustomsDeclaration.declarationDate <= ?  ";
			parameters.add(section.getEndDate());
		}
		hql += " order by a.commSerialNo";
		return this.find(hql, parameters.toArray());
	}
	/**
	 * 查询备案资料库和正在执行的通关手册
	 */
	/**
	 * 查找关联的料件
	 * @param section 批次
	 * @param isImgOrExg 料件true或成品false 
	 * @return
	 */
	public List finddcimg(boolean isImgOrExg) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "";
		if (isImgOrExg) {// 料件
			// 备案资料库归并序号 手册号 料件序号
			hql = "select a.innerMergeSeqNum,b.contract.emsNo,b.seqNum from BcsDictPorImg as a,ContractImg as b "
					+ " where a.dictPorHead.dictPorEmsNo = b.contract.corrEmsNo "
					+ " and a.seqNum=b.credenceNo and a.company.id=?  and b.contract.declareState = ? and a.dictPorHead.declareState = ?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(DeclareState.PROCESS_EXE);
			parameters.add(DeclareState.PROCESS_EXE);
			System.out.println("HSQL!!!!!!：" + hql);

		} else {
			hql = "select a.innerMergeSeqNum,b.contract.emsNo,b.seqNum from BcsDictPorExg as a,ContractExg as b "
					+ " where a.dictPorHead.dictPorEmsNo = b.contract.corrEmsNo "
					+ " and a.seqNum=b.credenceNo and a.company.id=?  and b.contract.declareState = ? and a.dictPorHead.declareState = ?";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(DeclareState.PROCESS_EXE);
			parameters.add(DeclareState.PROCESS_EXE);
			System.out.println("HSQL!!!!!!：" + hql);
		}
		hql += " order by a.innerMergeSeqNum";
		return this.find(hql, parameters.toArray());
	}
	
	/**
	 * 根据批次号删除报关单明细的资料
	 * 
	 * @param section
	 *            批次
	 * @param isImgOrExg
	 *            料件true或成品false
	 * @return
	 */
	public int delectVFCustomsListBySection(VFSection section,
			boolean isImgOrExg) {
		String table = "VFCustomsImg";
		if (isImgOrExg) {
			table = "VFCustomsImg";
		} else {
			table = "VFCustomsExg";
		}
		List<Object> parameters = new ArrayList<Object>();
		String hql = "delete from " + table + " as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hql += " and a.section.id = ?";
			parameters.add(section.getId());
		}
		return this.batchUpdateOrDelete(hql, parameters.toArray());
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
	public boolean isExistVFBySection(VFSection section,String entityName) {
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
	 * 根据批次号分页查询查询
	 * @param section 批次号
	 * @param index 开始值
	 * @param length 长度
	 * @param property 对象属性
	 * @param value 对象值
	 * @param isLike 是否模糊
	 * @param analyType 核查分析表类型
	 * @return
	 */
	public List findPageVFByVFSection(VFSection section,int index,
			int length, String property, Object value, boolean isLike,String entityName){
				List<Object> paramters = new ArrayList<Object>();
				String hsql = "select a from "+entityName+" as a where a.company.id= ? ";
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
				List vfcustomsList = super.findPageList(hsql, paramters.toArray(), index, length);
				return vfcustomsList;
	}
	
	/**
	 * 获得成品数据统计表中的备案序号，对于的合同成品BOM
	 * 
	 * @param seqNumList
	 * @param emsNolist
	 * @return
	 */
	public List getContractBomByCountExg(List seqNumList, List emsNolist) {
		List<Object> paramters = new ArrayList<Object>();
		StringBuilder hsql = new StringBuilder();
		hsql.append("select a,b.innerMergeSeqNum from ContractBom as a,BcsDictPorImg as b  where a.company.id= ? ");
		paramters.add(CommonUtils.getCompany().getId());
		hsql.append( " and a.contractExg.contract.declareState =? " );
		paramters.add(DeclareState.PROCESS_EXE);
		hsql.append( " and b.dictPorHead.declareState =? " );
		paramters.add(DeclareState.PROCESS_EXE);
		if (emsNolist != null&&emsNolist.size()>0) {
			hsql.append( " and a.contractExg.contract.emsNo in ( "+"'"+emsNolist.get(0)+"'");
			for (int i = 1; i < emsNolist.size(); i++) {
				hsql.append("," +"'"+ emsNolist.get(i)+"'");
			}
			hsql.append(")");
		}
		if (seqNumList != null&&seqNumList.size()>0) {
			hsql.append( " and a.contractExg.seqNum in ( "+seqNumList.get(0));
			for (int i = 1; i < seqNumList.size(); i++) {
				hsql.append("," + seqNumList.get(i));
			}
			hsql.append(")");
		}
		hsql.append(" and a.imgCredenceNo = b.seqNum  and " +
				" a.contractExg.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo ");
		hsql.append(" order by a.contractImgSeqNum ");
		List reList =  this.find(hsql.toString(), paramters.toArray());
		System.out.println("hsqlhsql" + hsql);
		return reList;
	}
	/**
	 * 根据批次号、料件归并序号成品折料统计表
	 * 
	 * @param section
	 *            批次
	 * @param mergerNo
	 *           料件归并序号
	 */
	public List<VFCustomsCountExgConvert> findConvertCustomsCountExg(VFSection section,Integer mergerNo){
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from VFCustomsCountExgConvert as a where a.company.id =? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (section != null) {
			hql += " and a.section.id = ?";
			parameters.add(section.getId());
		}
		if (mergerNo != null) {
			hql += " and a.mergerNo = ?";
			parameters.add(mergerNo);
		}
		List reList =  this.find(hql, parameters.toArray());
		return reList;
	}
	/**
	 * 根据批次号查找成品报关数据统计表
	 * @param vfSection
	 * @return
	 */
	public  List<VFCustomsCountExg> findVFCustomsCountExgBySection(VFSection section) {
		
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from VFCustomsCountExg a join " +
				" fetch a.section  where a.company.id =? and a.section.id = ? order by a.seqNum ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(section.getId());
		return this.find(hql, parameters.toArray());
	
	}
	/**
	 * 根据批次号查找料件报关数据统计表
	 * @param vfSection
	 * @return
	 */
	public  List<VFCustomsCountImg> findVFCustomsCountImgBySection(VFSection section) {
		
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from VFCustomsCountImg a join " +
				" fetch a.section  where a.company.id =? and a.section.id = ? order by a.seqNum,a.mergerNo";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(section.getId());
		return this.find(hql, parameters.toArray());
	
	}
	/**
	 * 根据批次号查找合同数据分析
	 * @param vfSection
	 * @return
	 */
	public List<VFContractAnalyse> findVFContractAnalyseBySection(VFSection section,Integer mergerNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from VFContractAnalyse a join " +
				" fetch a.section  where a.company.id =? and a.section.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(section.getId());
		if (mergerNo != null) {
			hql += " and a.mergerNo = ?";
			parameters.add(mergerNo);
		}
		return this.find(hql+"  order by a.mergerNo,a.serialNumber ", parameters.toArray());
	}
	/**
	 * 根据批次号分页查询成品报关明细数据
	 * @param section 批次号
	 * @param index 开始值
	 * @param length 长度
	 * @param property 对象属性
	 * @param value 对象值
	 * @param isLike 是否模糊
	 * @return
	 */
	public List<VFCustomsExg> findPageVFCustomsExgBySection(VFSection section,int index,
			int length, String property, Object value, boolean isLike){
				List<Object> paramters = new ArrayList<Object>();
				String hsql = "select a from VFCustomsExg as a where a.company.id= ? ";
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
				hsql += " order by a.seqNum,a.mergerNo ";
				System.out.println("--:" + hsql);
				List vfcustomsList = super.findPageList(hsql, paramters.toArray(), index, length);
				return vfcustomsList;
	}
	/**
	 * 根据批次号分页查询料件报关明细数据
	 * @param section 批次号
	 * @param index 开始值
	 * @param length 长度
	 * @param property 对象属性
	 * @param value 对象值
	 * @param isLike 是否模糊
	 * @return
	 */
	public List<VFCustomsImg> findPageVFCustomsImgBySection(VFSection section,int index,
			int length, String property, Object value, boolean isLike){
				List<Object> paramters = new ArrayList<Object>();
				String hsql = "select a from VFCustomsImg as a where a.company.id= ? ";
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
				hsql += " order by a.seqNum,a.mergerNo ";
				System.out.println("--:" + hsql);
				List vfcustomsList = super.findPageList(hsql, paramters.toArray(), index, length);
				return vfcustomsList;
	}
	/**
	 * 根据批次号分页查询成品报关数据统计折料表
	 * @param section 批次号
	 * @param index 开始值
	 * @param length 长度
	 * @param property 对象属性
	 * @param value 对象值
	 * @param isLike 是否模糊
	 * @return
	 */
	public List<VFCustomsCountExgConvert> findPageVFCustomsCountExgConvertSection(VFSection section,int index,
			int length, String property, Object value, boolean isLike){
				List<Object> paramters = new ArrayList<Object>();
				String hsql = "select a from VFCustomsCountExgConvert as a where a.company.id= ? ";
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
				hsql += " order by a.seqNum,a.mergerNo ";
				System.out.println("--:" + hsql);
				List vfcustomsList = super.findPageList(hsql, paramters.toArray(), index, length);
				return vfcustomsList;
	}
	/**
	 * 查询料件报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param seqNum 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	public List<VFCustomsImg> findVFCustomsImg(VFSection section, String emsNo,Integer seqNum, Integer impExpType) {
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select a from VFCustomsImg a where a.company.id = ?";
		paramters.add(CommonUtils.getCompany().getId());
		if(section != null){
			hql += " and a.section.id = ? ";
			paramters.add(section.getId());
		}
		if(StringUtils.isNotBlank(emsNo)){
			hql += " and a.emsNo = ? ";
			paramters.add(emsNo);	
		}
		if(seqNum != null){
			hql += " and a.seqNum = ? ";
			paramters.add(seqNum);
		}
		if(impExpType != null){
			hql += " and a.impExpType = ? ";
			paramters.add(impExpType);
		}
		return super.find(hql, paramters.toArray());
	}
	
	
	/**
	 * 查询成品报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param seqNum 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	public List<VFCustomsExg> findVFCustomsExg(VFSection section, String emsNo,Integer seqNum){
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select a from VFCustomsExg a where a.company.id = ?";
		paramters.add(CommonUtils.getCompany().getId());
		if(section != null){
			hql += " and a.section.id = ? ";
			paramters.add(section.getId());
		}
		if(StringUtils.isNotBlank(emsNo)){
			hql += " and a.emsNo = ? ";
			paramters.add(emsNo);	
		}
		if(seqNum != null){
			hql += " and a.seqNum = ? ";
			paramters.add(seqNum);
		}
		return super.find(hql, paramters.toArray());
	}
	/**
	 * 统计某批次料件数据统计料件进口数
	 * @param section
	 * @return
	 */
	public List findSumImportImgByVFSection(VFSection section) {
		String hql = "select a.emsNo,a.mergerNo,sum(a.impTotalAmont),sum(a.impTotalMoney) from VFCustomsCountImg a  where a.section.id = ?" ;
		return super.find(hql + " group by a.emsNo,a.mergerNo ", section.getId());
	}
	/**
	 * 统计某批次成品折算统计出口数
	 * @param section
	 * @return
	 */
	public List findSumWasteQtyByVFSection(VFSection section) {
		String hql = "select a.emsNo,a.mergerNo,sum(a.wasteAmount) from VFCustomsCountExgConvert a  where a.section.id = ?" ;
		return super.find(hql + " group by a.emsNo,a.mergerNo ", section.getId());
	}
//	/**
//	 * 备案资料库资料
//	 * @return
//	 */
//	public List findBcsTenInnerMerge(){
//		List<Object> paramters = new ArrayList<Object>();
//		String hql = "select a.innerMergeSeqNum,a.commName,a.commSpec,a.comUnit.name from BcsDictPorImg a  where a.company.id  = ? order by a.innerMergeSeqNum  " ;
//		paramters.add(CommonUtils.getCompany().getId());
//		return super.find(hql, paramters.toArray());
//	}
	/**
	 * 报关商品资料
	 * @return
	 */
	public List findBcsTenInnerMerge(){
		List<Object> paramters = new ArrayList<Object>();
		String hql = "select a.seqNum,a.name,a.spec,a.comUnit.name from BcsTenInnerMerge a  where a.company.id  = ?  and a.scmCoi = 2 order by a.seqNum  " ;
		paramters.add(CommonUtils.getCompany().getId());
		return super.find(hql, paramters.toArray());
	}
}
