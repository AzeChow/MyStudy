package com.bestway.bcs.verification.dao;

import java.util.List;

import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffExgConvert;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.common.BaseDao;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

/**
 * 结转分析数据操作类
 * @author xc
 * @version 1.0
 * @since 2013-09-05
 */
@SuppressWarnings("unchecked")
public class VFTransferAnalyseDao extends BaseDao {

	
	/**
	 * 根据版本查询BOM版本
	 * @param ptNo
	 * @param bomVersion
	 * @return
	 */
	public MaterialBomVersion findMaterialBomVersionByVersion(String ptNo,Integer bomVersion){
		String hql = "select a from MaterialBomVersion a where a.master.materiel.ptNo = ? and a.version = ? and a.company = ? order by a.endDate desc";
		List<MaterialBomVersion> vs = super.findPageList(hql,new Object[]{ptNo,bomVersion,CommonUtils.getCompany()}, 0, 1);		
		return vs == null || vs.size() < 1 ? null : vs.get(0); 
	}
	/**
	 * 获取最后一个版本的Bom
	 * @param ptNo
	 * @return
	 */
	public MaterialBomVersion findMaterialBomVersionByLasterVersion(String ptNo){
		String hql = "select a from MaterialBomVersion a where a.master.materiel.ptNo = ? and a.company = ?  order by a.version desc";
		List<MaterialBomVersion> vs = super.findPageList(hql,new Object[]{ptNo,CommonUtils.getCompany()}, 0, 1);		
		return vs == null || vs.size() < 1 ? null : vs.get(0);
	}
	/**
	 * 根据Bom版本查找Bom料件
	 * @param version
	 * @return
	 */
	public List<MaterialBomDetail> getMaterialBomDetailByVersion(MaterialBomVersion version){
		String hql = "select a from MaterialBomDetail a join fetch a.materiel where a.version = ? and a.company = ? ";
		return super.find(hql,new Object[]{version,CommonUtils.getCompany()});
	}
	/**
	 * 根据section(批次）查询已折算报关信息的成品结转报关信息
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffExgConvert> findHadConvertVFTransferDiffExgConvertByVFSection(VFSection section) {
		String hql = "select a from VFTransferDiffExgConvert a where a.section=? and a.mergerNo is not null order by serialNumber";
		return super.find(hql, section);
	}
	/**
	 * 根据section(批次）查询已折算报关信息的料件结转报关信息
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffImg> findHadConvertVFTransferDiffImgByVFsection(VFSection section) {
		String hql = "select a from VFTransferDiffImg a where a.section=? and a.mergerNo is not null order by serialNumber";
		return super.find(hql, section);
	}
	/**
	 * 查询深加工结转收货数据
	 * @param section
	 * @return
	 */
	public List<Object[]> findInFptBillItem(VFSection section){
		String hql= " select a,c.innerMergeSeqNum from FptBillItem a,ContractImg b,BcsDictPorImg c" +
				"  where a.inEmsNo=b.contract.emsNo and a.trGno=b.seqNum and b.contract.declareState=? " +
				" and b.contract.corrEmsNo=c.dictPorHead.dictPorEmsNo and b.credenceNo=c.seqNum and c.dictPorHead.declareState=? " +
				" and a.fptBillHead.receiveDate<=? " +
				" and a.fptBillHead.appState=? and a.billSort=a.fptBillHead.billSort "+ 
				" and a.fptBillHead.billSort=? and a.company.id=? ";
		return super.find(hql, new Object[]{DeclareState.PROCESS_EXE,DeclareState.PROCESS_EXE,CommonUtils.getEndDate(section.getEndDate()),
							DeclareState.PROCESS_EXE,FptInOutFlag.IN,CommonUtils.getCompany().getId()});
	}
	/**
	 * 查询深加工结转发货数据
	 * @param section
	 * @return
	 */
	public List<FptBillItem> findOutFptBillItem(VFSection section){
		String hql = "select a from FptBillItem a,Contract b where a.fptBillHead.outEmsNo=b.emsNo and b.declareState=? " 
					+ " and a.fptBillHead.issueDate<=? " 
					+ " and a.fptBillHead.appState=? and a.billSort=a.fptBillHead.billSort "
					+ " and a.fptBillHead.billSort= ? "
					+ " and a.company.id =? ";
		return super.find(hql, new Object[]{DeclareState.PROCESS_EXE,CommonUtils.getEndDate(section.getEndDate()),
				DeclareState.PROCESS_EXE,FptInOutFlag.OUT,CommonUtils.getCompany().getId()});
	}
	/**
	 * 获取结转进口报关单项目
	 * @param section
	 * @return
	 */
	public List<Object[]> findImpFptBcsCustomsDeclarationCommInfo(VFSection section){
		String hql= " select a,c.innerMergeSeqNum from BcsCustomsDeclarationCommInfo a,ContractImg b,BcsDictPorImg c " +
				" where a.baseCustomsDeclaration.emsHeadH2k=b.contract.emsNo and a.commSerialNo = b.seqNum and b.contract.declareState=? " +
				" and b.contract.corrEmsNo=c.dictPorHead.dictPorEmsNo and b.credenceNo=c.seqNum and c.dictPorHead.declareState=? " +
				" and a.baseCustomsDeclaration.impExpDate<=? " +
				" and a.baseCustomsDeclaration.effective=? " +
				" and a.baseCustomsDeclaration.impExpFlag=? " +
				" and a.baseCustomsDeclaration.impExpType=? " +
				" and a.company.id=? ";
		return super.find(hql, new Object[]{DeclareState.PROCESS_EXE,DeclareState.PROCESS_EXE,
						CommonUtils.getEndDate(section.getEndDate()),Boolean.TRUE,
						ImpExpFlag.IMPORT,ImpExpType.TRANSFER_FACTORY_IMPORT,CommonUtils.getCompany().getId()});
	}
	
	/**
	 * 获取结转出口报关单项目
	 * @param section
	 * @return
	 */
	public List<BcsCustomsDeclarationCommInfo> findExpFptBcsCustomsDeclarationCommInfo(VFSection section){
		String hql= " select a from BcsCustomsDeclarationCommInfo a,Contract b " +
				" where a.baseCustomsDeclaration.emsHeadH2k=b.emsNo and b.declareState=? " +
				" and a.baseCustomsDeclaration.impExpDate<=? " + 
				" and a.baseCustomsDeclaration.effective=?" +
				" and a.baseCustomsDeclaration.impExpFlag=? " +
				" and a.baseCustomsDeclaration.impExpType=? " +
				" and a.company.id =? ";
		
		return super.find(hql, new Object[]{DeclareState.PROCESS_EXE,CommonUtils.getEndDate(section.getEndDate()),Boolean.TRUE,
				ImpExpFlag.EXPORT,ImpExpType.TRANSFER_FACTORY_EXPORT,CommonUtils.getCompany().getId()});
	}
	/**
	 * 获取合同成品单耗表
	 * @return
	 */
	public List<Object[]> findProcessExeContractBom(){
		String hql="select a.contractExg.contract.emsNo,a.contractExg.seqNum,a from ContractBom a where a.contractExg.contract.declareState=? ";		
		return super.find(hql,new Object[]{DeclareState.PROCESS_EXE});
	}
	/**
	 * 获取合同手册料件归并序号
	 * @return
	 */
	public List<Object[]> findMergerNoBySeqNum(){		
		String hql = "select a.contract.emsNo,a.seqNum,b.innerMergeSeqNum from ContractImg a,BcsDictPorImg b " +
				" where a.contract.corrEmsNo=b.dictPorHead.dictPorEmsNo and a.credenceNo=b.seqNum " +
				" and a.contract.declareState=? and b.dictPorHead.declareState=? ";
		return super.find(hql,new Object[]{DeclareState.PROCESS_EXE,DeclareState.PROCESS_EXE});
	}
	public List<VFTransferDiffExgConvert> findVFTransferDiffExgConverts(VFSection section, Integer seqNum) {
			String seqNumStr = "";
			if (seqNum != null) {
				seqNumStr = " and v.mergerNo = " + seqNum;
			}
			return findNoCache("select v from VFTransferDiffExgConvert v join fetch v.exg join fetch v.section where v.section.id = ?" + seqNumStr
					+ " order by v.serialNumber ",
					new Object[] { section.getId() });
	}
}
