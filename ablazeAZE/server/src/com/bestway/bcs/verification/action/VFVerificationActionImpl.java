package com.bestway.bcs.verification.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.verification.entity.VFAnalyse;
import com.bestway.bcs.verification.entity.VFAttachmentManagement;
import com.bestway.bcs.verification.entity.VFBadExg;
import com.bestway.bcs.verification.entity.VFBadExgConvert;
import com.bestway.bcs.verification.entity.VFBadImg;
import com.bestway.bcs.verification.entity.VFBadStockAnalyse;
import com.bestway.bcs.verification.entity.VFBaseStockExg;
import com.bestway.bcs.verification.entity.VFBaseStockImg;
import com.bestway.bcs.verification.entity.VFCategory;
import com.bestway.bcs.verification.entity.VFCategoryAnalyse;
import com.bestway.bcs.verification.entity.VFCategoryBcsTenInnerMerge;
import com.bestway.bcs.verification.entity.VFContractAnalyse;
import com.bestway.bcs.verification.entity.VFCustomsCountExgConvert;
import com.bestway.bcs.verification.entity.VFCustomsExg;
import com.bestway.bcs.verification.entity.VFCustomsImg;
import com.bestway.bcs.verification.entity.VFFinishingExg;
import com.bestway.bcs.verification.entity.VFFinishingExgConvert;
import com.bestway.bcs.verification.entity.VFFinishingImg;
import com.bestway.bcs.verification.entity.VFFinishingStockAnalyse;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFSemiBadExg;
import com.bestway.bcs.verification.entity.VFSemiBadExgConvert;
import com.bestway.bcs.verification.entity.VFStockAnalyse;
import com.bestway.bcs.verification.entity.VFStockBuyImg;
import com.bestway.bcs.verification.entity.VFStockExg;
import com.bestway.bcs.verification.entity.VFStockExgConvert;
import com.bestway.bcs.verification.entity.VFStockImg;
import com.bestway.bcs.verification.entity.VFStockOutFactoryImg;
import com.bestway.bcs.verification.entity.VFStockOutSendAnalyse;
import com.bestway.bcs.verification.entity.VFStockOutSendExg;
import com.bestway.bcs.verification.entity.VFStockOutSendExgConvert;
import com.bestway.bcs.verification.entity.VFStockOutSendImg;
import com.bestway.bcs.verification.entity.VFStockOutSendSemiExg;
import com.bestway.bcs.verification.entity.VFStockOutSendSemiExgConvert;
import com.bestway.bcs.verification.entity.VFStockProcessImg;
import com.bestway.bcs.verification.entity.VFStockSemiExgHadStore;
import com.bestway.bcs.verification.entity.VFStockSemiExgHadStoreConvert;
import com.bestway.bcs.verification.entity.VFStockTravelingExg;
import com.bestway.bcs.verification.entity.VFStockTravelingExgConvert;
import com.bestway.bcs.verification.entity.VFStockTravelingImg;
import com.bestway.bcs.verification.entity.VFTransferDiffCount;
import com.bestway.bcs.verification.entity.VFTransferDiffExg;
import com.bestway.bcs.verification.entity.VFTransferDiffExgConvert;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExg;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExgResolve;
import com.bestway.bcs.verification.entity.VFTransferDiffHsImg;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.bcs.verification.logic.VFContractAnalyseLogic;
import com.bestway.bcs.verification.logic.VFFactoryAnalyseLogic;
import com.bestway.bcs.verification.logic.VFTransferAnalyseLogic;
import com.bestway.bcs.verification.logic.VFVerificationLogic;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

public class VFVerificationActionImpl implements VFVerificationAction {
	private VFVerificationLogic vfVerificationLogic;
	private VFContractAnalyseLogic vfContractAnalyseLogic;
	private VFFactoryAnalyseLogic vfFactoryAnalyseLogic;
	private VFTransferAnalyseLogic vfTransferAnalyseLogic;
	/**
	 * 根据批次判断是否存在数据
	 * @param request
	 * @param section
	 * @param entityClass
	 * @return
	 */
	public boolean isExistsBySection(Request request,VFSection section,Class<? extends BaseScmEntity> entityClass){
		return vfVerificationLogic.isExistsBySection(section,entityClass);
	}
	/**
	 * 获取并保存成品报关明细表
	 * @param request
	 * @param section 批次号
	 * @param isImgOrExg 料件true或成品false 
	 * @return
	 */
	public void gainVFCustomsExg(Request request,VFSection section){
		 vfContractAnalyseLogic.gainVFCustomsExg(section, false);
	}
	/**
	 * 获取并保存料件报关明细表
	 * @param request
	 * @param section 批次号
	 * @param isImgOrExg 料件true或成品false 
	 * @return
	 */
	public void gainVFCustomsImg(Request request,VFSection section){
		 vfContractAnalyseLogic.gainVFCustomsImg(section, true);
	}
	/**
	 *根据批次号分页查询查询
	 * @param section
	 *            批次号
	 * @param index
	 *            开始值
	 * @param length
	 *            长度
	 * @param property
	 *            对象属性
	 * @param value
	 *            对象值
	 * @param isLike
	 *            是否模糊
	* @param analyType 核查分析表类型
	 * @return
	 */
	public List<Object> findPageVFByVFSection(Request request,VFSection section, int index,
			int length, String property, Object value, boolean isLike,
			int analyType) {
		List customsList = vfContractAnalyseLogic.findPageVFByVFSection(section,
				index, length, property, value, isLike, analyType);
		return customsList;
	}
	 
	/**
	 * 批次设定
	 * 获得最大ID
	 */
	public List<Object> findMaxNO(Request request){
		return vfVerificationLogic.findMaxNO();
	}
	
	/**
	 * 批次设定
	 * 获得表数据
	 */
	public List<VFSection> findVFSectionList(Request request){
		return vfVerificationLogic.findVFSectionList();
	}
	
	
	/**
	 * 批次设定
	 * delete data by id
	 */
	public void deleteVFSection(Request request,VFSection vf){
		vfVerificationLogic.deleteVFSection(vf);
		return ;
	}
	
	/**
	 * 批次设定
	 * delete data by list
	 */
	public void deleteVFSectionByList(Request request,List <VFSection> list){
		vfVerificationLogic.deleteVFSectionByList(list);
	}
	
	
	/**
	 * 批次设定
	 * save data 
	 */
	public VFSection saveVFSection(Request request,VFSection vf){
		return vfVerificationLogic.saveVFSection(vf);
	}
	/**
	 *修改批次数据
	 * save data 
	 */
	public VFSection saveVFModifySection(Request request,VFSection vf){
		vfVerificationLogic.saveVFModifySection(vf);
		return vf;
	}
	/**
	 * 批次设定
	 * update data 
	 */
	public void updateVFSection(Request request,VFSection vf){
		vfVerificationLogic.updateVFSection(vf);
	}
	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	public <E extends BaseEntity> E update(E entity){
		return vfVerificationLogic.update(entity);
	}
	
	/**
	 * 更新短溢分析单价数据（同步关税、增值税栏位）
	 * @param analyse
	 * @return
	 */
	public VFAnalyse updateAnalysePrice(VFAnalyse analyse){
		return vfVerificationLogic.updateAnalysePrice(analyse);
	}
	
	/**
	 * 判断是否存在
	 */
	public List<VFSection> findExistsVFSection(Request request, VFSection vf) {
		// TODO Auto-generated method stub
		return vfVerificationLogic.findExistsVFSection(vf);
	}


	/**
	 * 对应关系数据
	 */
	public List<Object> findVFFactoryBcsInnerMergeList(Request request){
		return vfVerificationLogic.findVFFactoryBcsInnerMergeList();
	}
	
	/**
	 * 对应关系数据 外发成品
	 */
	public List<Object> findVFOutSendExgInBom(Request request){
		return vfVerificationLogic.findVFOutSendExgInBom();
	}
	
	/**
	 * 对应关系数据 外发成品 最大 BOM version
	 */
	public List<Object> findVFOutSendExgInMaxBom(Request request){
		return vfVerificationLogic.findVFOutSendExgInMaxBom();
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
	public List<BcsInnerMerge> findBcsInnerMerge(Request request, String materielType) {
		return vfVerificationLogic.findBcsInnerMerge(materielType);		
	}
	/**
	 * 查找 正在执行的合同料件,并从备案资料库带出归并序号
	 * 
	 *            请求控制
	 * @param emsNo 合同号
	 *            
	 * @return List 是Object[]型，存放合同备案料件资料
	 */
	public List<Object[]> findContractImgByEmsNo(Request request,List<String> emsNos){
		return vfVerificationLogic.findContractImgByEmsNo(emsNos);	
	}
	/**
	 * 查找 正在执行的合同成品,并从备案资料库带出归并序号
	 * 
	 *            请求控制
	 * @param emsNo 合同号
	 *            
	 * @return List 是Object[]型，存放合同备案成品资料
	 */
	public List<Object[]> findContractExgByEmsNo(Request request,List<String> emsNos){
		return vfVerificationLogic.findContractExgByEmsNo(emsNos);	
	}
	/**
	 * 外发库存
	 */
	@Override
	public List<VFStockOutSendExg> findVFStockOutSendExgs(Request request, VFSection vfSection) {
		return vfFactoryAnalyseLogic.findVFStockOutSendExgs(vfSection, null);
	}

	/**
	 * 外发库存
	 */
	@Override
	public List<VFStockOutSendExgConvert> findVFStockOutSendExgsConvert(Request request, VFSection vfSection, Integer seqNum) {
		return vfFactoryAnalyseLogic.findVFStockExgsConvert(vfSection, seqNum);
	}

	/**
	 * 厂外库存
	 */
	@Override
	public List<VFStockOutFactoryImg> findVFStockOutFactoryImgs(Request request, VFSection vfSection, Integer seqNum) {
		// vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.findVFStockOutFactoryImgs(vfSection, seqNum);
	}
	
	 /**
	  * 内购 
	  */
	@Override
	public List<VFStockBuyImg> findVFStockBuyImgs(Request request, VFSection vfSection, Integer seqNum) {
		// vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.findVFStockBuyImgs(vfSection, seqNum);
	}


	/**
	 * 在途
	 */
	@Override
	public List<VFStockTravelingImg> findVFStockTravelingImgs(Request request, VFSection vfSection, Integer seqNum) {
		// vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.findVFStockTravelingImgs(vfSection, seqNum);
	}

	
	//***********折算
	@Override
	public List<VFStockOutSendExgConvert> convertFactoryVFStockOutSendExgsConvert(Request request,
			VFSection vfSection) {
		//vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.convertFactoryVFStockOutSendExgsConvert(vfSection);
	}
	@Override
	public String convertHsVFStockOutSendExgsConvert(Request request,
			VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.convertHsVFStockOutSendExgsConvert(vfSection);
	}
	@Override
	public List<VFStockOutFactoryImg> convertVFStockOutFactoryImgs(Request request,
			VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.convertVFStockOutFactoryImgs(vfSection);
	}
	@Override
	public List<VFStockBuyImg> convertVFStockBuyImgs(Request request, VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.convertVFStockBuyImgs(vfSection);
	}
	@Override
	public List<VFStockTravelingImg> convertVFStockTravelingImgs(Request request,
			VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return vfFactoryAnalyseLogic.convertVFStockTravelingImgs(vfSection);
	}

	
	
	
	
	
	
	

	@Override
	public void saveVFStockOutSendExgs(Request request, VFSection vfSection,
			List<VFStockOutSendExg> list) {
		 vfFactoryAnalyseLogic.saveVFStockOutSendExgs(vfSection, list);
	}


	@Override
	public void saveVFStockOutSendExgsConvert(Request request, VFSection vfSection,
			List<VFStockOutSendExgConvert> list) {
		// vfFactoryAnalyseLogic
		vfFactoryAnalyseLogic.saveVFStockOutSendExgsConvert(vfSection, list);
	}
	

	@Override
	public void saveVFStockOutFactoryImgs(Request request, VFSection vfSection,
			List<VFStockOutFactoryImg> list) {
		// vfFactoryAnalyseLogic
		vfFactoryAnalyseLogic.saveVFStockOutFactoryImgs(vfSection, list);
		
	}



	@Override
	public void saveVFStockBuyImgs(Request request, VFSection vfSection,
			List<VFStockBuyImg> list) {
		// vfFactoryAnalyseLogic
		vfFactoryAnalyseLogic.saveVFStockBuyImgs(vfSection, list);
	}



	@Override
	public void saveVFStockTravelingImgs(Request request, VFSection vfSection,
			List<VFStockTravelingImg> list) {
		// vfFactoryAnalyseLogic
		vfFactoryAnalyseLogic.saveVFStockTravelingImgs(vfSection, list);
	}




	
	
	
	
	
	
	
	
	
	@Override
	public void deleteVFStockOutSendExgs(Request request, VFSection vfSection) {
		// vfFactoryAnalyseLogic
		vfFactoryAnalyseLogic.deleteVFStockOutSendExgs(request, vfSection);
	}

	@Override
	public void deleteVFStockOutSendExgsConvert(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockOutSendExgsConvert(request, vfSection);
	}

	@Override
	public void deleteVFStockOutFactoryImgs(Request request, VFSection vfSection) {
		 vfFactoryAnalyseLogic.deleteVFStockOutFactoryImgsConvert(request, vfSection);
		
	}


	@Override
	public void deleteVFStockBuyImgs(Request request, VFSection vfSection) {
		 vfFactoryAnalyseLogic.deleteVFStockBuyImgs(request, vfSection);
	}


	@Override
	public void deleteVFStockTravelingImgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockTravelingImgs(request, vfSection);
	}
	

	
	
	
	
	
	
	
	
	
	@Override
	public List<VFStockImg> findVFStockImgs(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockImgs(vfSection, seqNum);
	}
	
	@Override
	public List<VFStockOutSendImg> findVFStockOutSendImgs(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockOutSendImgs(vfSection, seqNum);
	}


	@Override
	public List<VFStockExg> findVFStockExgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.findVFStockExgs(vfSection, null);
	}
	@Override
	public List<VFStockTravelingExg> findVFStockTravelingExgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.findVFStockTravelingExgs(vfSection, null);
	}
	
	@Override
	public List<VFStockOutSendSemiExg> findVFStockOutSendSemiExgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.findVFStockOutSendSemiExgs(vfSection, null);
	}

	@Override
	public List<VFStockSemiExgHadStore> findVFStockSemiExgHadStore(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.findVFStockSemiExgHadStore(vfSection, null);
	}
	
	@Override
	public List<VFStockExgConvert> findVFStockExgConverts(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockExgConverts(vfSection, seqNum);
	}
	@Override
	public List<VFStockTravelingExgConvert> findVFStockTravelingExgConverts(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockTravelingExgConverts(vfSection, seqNum);
	}
	
	@Override
	public List<VFStockOutSendSemiExgConvert> findVFStockOutSendSemiExgConverts(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockOutSendSemiExgConverts(vfSection, seqNum);
	}

	@Override
	public List<VFStockSemiExgHadStoreConvert> findVFStockSemiExgHadStoreConverts(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockSemiExgHadStoreConverts(vfSection, seqNum);
	}

	@Override
	public List<VFStockProcessImg> findVFStockProcessImgs(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockProcessImgs(vfSection, seqNum);
	}
	@Override
	public List<VFStockOutFactoryImg> findVFStockProcessImgsout(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockProcessImgsout(vfSection, seqNum);
	}
	
	@Override
	public List<VFStockBuyImg> findVFStockProcessImgsin(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockProcessImgsin(vfSection, seqNum);
	}
	@Override
	public List<VFStockTravelingImg> findVFStockProcessImgslu(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockProcessImgslu(vfSection, seqNum);
	}
	@Override
	public List<VFStockOutSendImg> findVFStockProcessImgsoutsend(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockProcessImgsoutsend(vfSection, seqNum);
	}
	
	@Override
	public List<VFFinishingImg> findVFStockProcessImgsing(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockProcessImging(vfSection, seqNum);
	}
	
	@Override
	public List<VFBadImg> findVFStockProcessImgsbad(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFStockProcessImgbad(vfSection, seqNum);
	}
	@Override
	public void saveVFStockImgs(Request request, VFSection vfSection, List<VFBaseStockImg> vFBaseStockImg) {
		
		vfFactoryAnalyseLogic.saveVFStockImgs(vfSection, vFBaseStockImg);
	}


	@Override
	public void saveVFStockExgs(Request request, VFSection vfSection, List<VFBaseStockExg> vFBaseStockExg) {
		
		vfFactoryAnalyseLogic.saveVFStockExgs(vfSection, vFBaseStockExg);
	}
	
	@Override
	public void saveVFStockSemiExgHadStore(Request request, VFSection vfSection, List<VFStockSemiExgHadStore> vFStockSemiExgPtHadStore){	
		vfFactoryAnalyseLogic.saveVFStockSemiExgHadStore(vfSection, vFStockSemiExgPtHadStore);
	}



	@Override
	public void saveVFStockExgConverts(Request request, VFSection vfSection,
			List<VFStockExgConvert> vfStockExgConverts) {
		
		vfFactoryAnalyseLogic.saveVFStockExgConverts(vfSection, vfStockExgConverts);
	}



	@Override
	public void saveVFStockProcessImgs(Request request, VFSection vfSection,
			List<VFStockProcessImg> vfStockProcessImgs) {
		
		vfFactoryAnalyseLogic.saveVFStockProcessImgs(vfSection, vfStockProcessImgs);
	}


	@Override
	public void deleteVFStockImgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockImgs(vfSection);
	}
	
	@Override
	public void deleteVFStockOutSendImgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockOutSendImgs(vfSection);
	}



	@Override
	public void deleteVFStockExgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockExgs(vfSection);
	}
	@Override
	public void deleteVFStockTravelingExgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockTravelingExgs(vfSection);
	}
	
	@Override
	public void deleteVFStockOutSendSemiExgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockOutSendSemiExgs(vfSection);
	}
	
	@Override
	public void deleteVFStockSemiExgHadStores(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockSemiExgHadStores(vfSection);
	}



	@Override
	public void deleteVFStockExgConverts(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockExgConverts(vfSection);
	}
	@Override
	public void deleteVFStockTravelingExgConverts(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockTravelingExgConverts(vfSection);
	}
	
	@Override
	public void deleteVFStockOutSendSemiExgConverts(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockOutSendSemiExgConverts(vfSection);
	}
	
	@Override
	public void deleteVFStockSemiExgHadStoreConverts(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockSemiExgHadStoreConverts(vfSection);
	}



	@Override
	public void deleteVFStockProcessImgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFStockProcessImgs(vfSection);
	}

	
	/**
	 * 汇总section【批次】下所有工厂数据
	 * @param request
	 * @param section
	 * @return
	 */
	public List<VFStockAnalyse> stockAnalyse(Request request, VFSection section){
		return vfFactoryAnalyseLogic.stockAnalyse(section);
	}
	/**
	 * 汇总section【批次】外发库数据
	 * @param request
	 * @param section
	 * @return
	 */
	public List<VFStockOutSendAnalyse> stockVFStockOutSendAnalyse(Request request, VFSection section){
		return vfFactoryAnalyseLogic.stockVFStockOutSendAnalyse(section);
	}
	/**
	 * 查找section【批次】下库存分析数据
	 * @param request
	 * @param section
	 * @return
	 */
	public  List<VFStockAnalyse> findVFStockAnalyse(Request request, VFSection section, Integer seqNum){
		return vfFactoryAnalyseLogic.findVFStockAnalyse(section, seqNum);
	}
	/**
	 * 查找section【批次】下外发库存汇总
	 * @param request
	 * @param section
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFStockOutSendAnalyse> findVFStockOutSendAnalyse(Request request, VFSection section, Integer seqNum){
		return vfFactoryAnalyseLogic.findVFStockOutSendAnalyse(section, seqNum);
	}
	/**
	 * 删除section【批次】下汇总数据
	 * @param request
	 * @param section
	 */
	public  void deleteVFStockAnalyse(Request request, VFSection section){
		vfFactoryAnalyseLogic.deleteVFStockAnalyse(section);
	}
	
	/**
	 * 删除section【批次】外发库存汇总
	 * @param request
	 * @param section
	 */
	public  void deleteVFStockOutSendAnalyse(Request request, VFSection section){
		vfFactoryAnalyseLogic.deleteVFStockOutSendAnalyse(section);
	}

	// --------------------------结转分析接口 开始-----------------------//
	/**
	 * 批量保存 结转成品差额表
	 * @param request
	 * @param exgLs
	 */
	public void saveVFTransferDiffExgs(Request request,VFSection vfSection,  List<VFTransferDiffExg> exgLs){
		vfTransferAnalyseLogic.saveVFTransferDiffExgs(vfSection,exgLs);
	}
	/**
	 * 批量保存 结转成品差额表(编码级)
	 * @param request
	 * @param hsExgLs
	 */
	public void saveVFTransferDiffHsExgs(Request request,VFSection vfSection,  List<VFTransferDiffHsExg> hsExgLs){
		vfTransferAnalyseLogic.saveVFTransferDiffHsExgs(vfSection,hsExgLs);
	}
	/**
	 * 批量保存 结转料件差额表
	 * @param request
	 * @param imgLs
	 */
	public void saveVFTransferDiffImgs(Request request,VFSection vfSection,  List<VFTransferDiffImg> imgLs){
		vfTransferAnalyseLogic.saveVFTransferDiffImgs(vfSection,imgLs);		
	}
	/**
	 * 批量保存 结转料件差额表(编码级)
	 * @param request
	 * @param imgLs
	 */
	public void saveVFTransferDiffHsImgs(Request request,VFSection vfSection,  List<VFTransferDiffHsImg> hsImgLs){
		vfTransferAnalyseLogic.saveVFTransferDiffHsImgs(vfSection,hsImgLs);		
	}
	
	/**
	 * 根据section(批次)查询结转成品差额表信息
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffExg> findVFTransferDiffExgs(Request request, VFSection section){
		return vfTransferAnalyseLogic.findVFTransferDiffExgs(section);
	}
	/**
	 * 根据section(批次)查询结转成品差额表信息(编码级)
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffHsExg> findVFTransferDiffHsExgs(Request request, VFSection section){
		return vfTransferAnalyseLogic.findVFTransferDiffHsExgs(section);
	}
	/**
	 * 根据section(批次)查询结转成品差额折料转换报关数据信息
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffExgConvert> findVFTransferDiffExgConverts(Request request, VFSection section,Integer seqNum){
		return vfTransferAnalyseLogic.findVFTransferDiffExgConverts(section,seqNum);
	}
	
	/**
	 * 根据section(批次)查询结转成品分解料件表(编码级)
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffHsExgResolve> findVFTransferDiffHsExgResolves(Request request, VFSection section,Integer seqNum){
		return vfTransferAnalyseLogic.findVFTransferDiffHsExgResolves(section,seqNum);
	}
	
	/**
	 * 根据section(批次)查询结转料件差额表信息
	 * @param request
	 * @param section 批次信息
	 * @return
	 */
	public List<VFTransferDiffImg> findVFTransferDiffImgs(Request request, VFSection section,Integer seqNum){
		return vfTransferAnalyseLogic.findVFTransferDiffImgs(section,seqNum);
	}
	
	/**
	 * 根据section(批次)查询结转料件差额表信息(编码级)
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffHsImg> findVFTransferDiffHsImgs(Request request, VFSection section,Integer seqNum){
		return vfTransferAnalyseLogic.findVFTransferDiffHsImgs(section,seqNum);
	}
	
	/**
	 * 根据section(批次)批量删除结成品差额表  
	 * @param request
	 * @param section 批次信息
	 */
	public void deleteVFTransferDiffExgs(Request request,VFSection section){
		vfTransferAnalyseLogic.deleteVFTransferDiffExgs(section);
	}
	/**
	 * 根据section(批次)批量删除结成品差额表  (编码级)
	 * @param request
	 * @param section 批次信息
	 */
	public void deleteVFTransferDiffHsExgs(Request request,VFSection section){
		vfTransferAnalyseLogic.deleteVFTransferDiffHsExgs(section);
	}
	/**
	 * 根据section(批次)批量删除结转料件差额表  
	 * @param request
	 * @param section 批次信息
	 */
	public  void deleteVFTransferDiffImgs(Request request,VFSection section){
		vfTransferAnalyseLogic.deleteVFTransferDiffImgs(section);
	}
	/**
	 * 根据section(批次)批量删除结转料件差额表 (编码级)
	 * @param request
	 * @param section 批次信息
	 */
	public void deleteVFTransferDiffHsImgs(Request request,VFSection section){
		vfTransferAnalyseLogic.deleteVFTransferDiffHsImgs(section);
	}
	/**
	 * 根据section批次 将该批次的结转成品拆分成料件
	 * @param request
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffExgConvert> unpickExgToImg(Request request,VFSection section){
		return vfTransferAnalyseLogic.unpickExgToImg(section);
	}	
	
	/**
	 * 根据section(批次)将 成品拆分料件 转成品拆分料件报关料件
	 * @param request
	 * @param section
	 * @return
	 */
	public String convertVFTransferDiffExgConvertToCustoms(Request request,VFSection section){
		return vfTransferAnalyseLogic.convertVFTransferDiffExgConverts(section);
	}
	/**
	 *  根据section(批次)将 结转料件转成结转料件报关料件
	 * @param request
	 * @param section
	 * @return
	 */
	public  List<VFTransferDiffImg> convertVFTransferDiffImgToCustoms(Request request,VFSection section){
		return vfTransferAnalyseLogic.convertVFTransferDiffImgs(section);		
	}
	
	/**
	 *  根据section(批次)查询结转汇总信息
	 * @param request
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffCount> findVFTransferDiffCount(Request request, VFSection section,Integer seqNums){
		return vfTransferAnalyseLogic.findVFTransferDiffCount(section,seqNums);
	}
	/**
	 * 根据section(批次) 计算成品结转、料件结转报关料件 汇总信息
	 * @param request
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffCount> cacuVFTransferDiffCount(Request request, VFSection section){
		return vfTransferAnalyseLogic.cacuVFTransferDiffCount(section);
	}
	/**
	 * 根据section(批次)删除汇总信息
	 * @param request
	 * @param section 批次
	 */
	public void deleteVFTransferDiffCount(Request request, VFSection section){
		vfTransferAnalyseLogic.deleteVFTransferDiffCount(section);
	}
	
	/**
	 * 判断vfsection（批次）中是否存在结转成品信息
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffExgs(Request request,VFSection vfSection){
		return vfTransferAnalyseLogic.isExistVFTransferDiffExgs(vfSection);
	}
	/**
	 * 判断vfsection（批次）中是否存在结转成品信息(编码级)
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffHsExgs(Request request,VFSection vfSection){
		return vfTransferAnalyseLogic.isExistVFTransferDiffHsExgs(vfSection);
	}
	
	
	/**
	 * 判断vfsection（批次）中是否存在结转料件信息
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffImgs(Request request,VFSection vfSection){
		return vfTransferAnalyseLogic.isExistVFTransferDiffImgs(vfSection);
	}
	/**
	 * 判断vfsection（批次）中是否存在结转料件差额(编码级)信息
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffHsImgs(Request request,VFSection vfSection){
		return vfTransferAnalyseLogic.isExistVFTransferDiffHsImgs(vfSection);
	}
	
	/**
	 * 判断vfsection（批次）中是否存在结转差额汇总数据
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffCounts(Request request,VFSection vfSection){
		return vfTransferAnalyseLogic.isExistVFTransferDiffCounts(vfSection);
	}
	// --------------------------结转分析接口 结束-----------------------//	


	/**
	 * @param vfVerificationLogic the vfVerificationLogic to set
	 */
	public void setVfVerificationLogic(VFVerificationLogic vfVerificationLogic) {
		this.vfVerificationLogic = vfVerificationLogic;
	}
	/**
	 * @param vfContractAnalyseLogic the vfContractAnalyseLogic to set
	 */
	public void setVfContractAnalyseLogic(
			VFContractAnalyseLogic vfContractAnalyseLogic) {
		this.vfContractAnalyseLogic = vfContractAnalyseLogic;
	}
	/**
	 * @param vfFactoryAnalyseLogic the vfFactoryAnalyseLogic to set
	 */
	public void setVfFactoryAnalyseLogic(VFFactoryAnalyseLogic vfFactoryAnalyseLogic) {
		this.vfFactoryAnalyseLogic = vfFactoryAnalyseLogic;
	}
	/**
	 * @param vfTransferAnalyseLogic the vfTransferAnalyseLogic to set
	 */
	public void setVfTransferAnalyseLogic(
			VFTransferAnalyseLogic vfTransferAnalyseLogic) {
		this.vfTransferAnalyseLogic = vfTransferAnalyseLogic;
	}

	/**
	 * t统计某批次的报关单明细的资料的笔数
	 * 
	 * @param section
	 *            批次
	 * @param isImgOrExg
	 *            料件true或成品false
	 * @return
	 */
	public long countVFCustomsImgOrExgBySection(Request request,VFSection section,String property, 
			Object value, boolean isLike,int analyType){
		return vfContractAnalyseLogic.countVFCustomsImgOrExgBySection(section, property, 
				 value,  isLike, analyType);
	}
	/**
	 * 获得并保存料件报关数据统计表
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public void gainVFCustomsCountImg(Request request,VFSection section){
		 vfContractAnalyseLogic.gainVFCustomsCountImg(section);
	}

	/**
	 *  获得某个批次号的数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *           报表类型
	 *   @param  mergerNo 归并序号 
	 * @return
	 */
	public List<Object> findVFBySection(Request request,VFSection section,int analyType,Integer mergerNo){
		return vfContractAnalyseLogic.findVFBySection(
				section, analyType,mergerNo);
	}
	/**
	 *  获得某个批次号合同数据分析数据表
	 * 
	 * @param section
	 *            批次
	 *   @param  mergerNo 归并序号 
	 * @return
	 */
	public List<VFContractAnalyse> findVFContractAnalyseVFBySection(Request request,VFSection section,Integer mergerNo){
		return vfContractAnalyseLogic.findVFContractAnalyseVFBySection(section,mergerNo);
	}
	/**
	 * 根据批次号删除数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public int delectVFBySection(Request request,VFSection section,
			int analyType) {
		return vfContractAnalyseLogic.delectVFBySection(section, analyType);
	}
	/**
	 * 判断是否存在某批次的数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public boolean isExistVFBySection(Request request,VFSection section,
			int analyType){
		return vfContractAnalyseLogic.isExistVFBySection(section, analyType);
	}

	/**
	 * 获得并保存成品报关数据统计表
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public void gainVFCustomsCountExg(Request request,VFSection section){
		vfContractAnalyseLogic.gainVFCustomsCountExg(section);
	}

	/**
	 * 库存料件 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockImg> convertVFStockImgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.convertVFStockImgs(vfSection);
	}
	
	/**
	 * 外发库存原材料 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendImg> convertVFStockOutSendImgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.convertVFStockOutSendImgs(vfSection);
	}
	
	/**
	 * 折算报关数量
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExgConvert> convertVFStockExgConverts(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFStockExgConverts(vfSection);
	}
	/**
	 * 【成品属性库存】折算报关数量
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockExgConvertsEms(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFStockExgConvertsEms(vfSection);
	}
	/**
	 * 【料件库存属性】折算报关数量
	 * @param vfSection
	 * @return
	 */
	public String convertImgConverts(Request request,VFSection vfSection,String table){
		return vfFactoryAnalyseLogic.convertImgConverts(vfSection,table);
	}
	/**
	 * 【在途库存成品】折算报关数量
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockTravelingExgConvertsEms(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFStockTravelingExgConvertEms(vfSection);
	}
	/**
	 * 【外发库存半成品】折算报关数量,折算时，把对应关系存在的品名折算，没有做对应关系的提示出来
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockOutSendSemiExgConvertsEms(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFStockOutSendSemiExgConvertsEms(vfSection);
	}
	
	/**
	 * 【半成品库存（已入库）】折算报关数量
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockSemiExgHadStoreConvertEms(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFStockSemiExgHadStoreConvertEms(vfSection);
	}
	/**
	 * 在产品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockProcessImg> convertVFStockProcessImgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.convertVFStockProcessImgs(vfSection);
	}
	
	/**
	 * 产品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExgConvert> resolveVFStockExgs(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.resolveVFStockExgs(vfSection);
	}
	
	/**
	 * 在途库存成品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingExgConvert> resolveVFStockTravelingExgs(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.resolveVFStockTravelingExgs(vfSection);
	}
	
	/**
	 * 外发库存半成品 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendSemiExgConvert> resolveVFStockOutSendSemiExgs(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.resolveVFStockOutSendSemiExgs(vfSection);
	}
	
	/**
	 * 半成品库存（已入库） 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockSemiExgHadStoreConvert> resolveVFStockSemiExgHadStoreConverts(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.resolveVFStockSemiExgHadStoreConverts(vfSection);
	}
	/**
	 * 折算成品并保存到成品折料统计表
	 * 
	 * @param section
	 *            批次
	 */
	public void convertCustomsCountExgToImg(Request request,VFSection section){
		vfContractAnalyseLogic.convertCustomsCountExgToImg(section);
	}
	/**
	 * 根据批次号、料件归并序号成品折料统计表
	 * 
	 * @param section
	 *            批次
	 * @param mergerNo
	 *           料件归并序号
	 */
	public List<VFCustomsCountExgConvert> findConvertCustomsCountExg(Request request,VFSection section,Integer mergerNo){
		return vfContractAnalyseLogic.findConvertCustomsCountExg(section, mergerNo);
	}
	/**
	 * 短溢分析查看历史
	 * @param vfSection
	 * @return
	 */
	public List<VFAnalyse> findVFAnalyses(Request request,VFSection vfSection, Integer seqNum){
		return vfVerificationLogic.findVFAnalyses(vfSection,seqNum);
	}
	/**
	 * 短溢分析
	 * @param vfSection
	 * @return
	 */
	public List<VFAnalyse> verificationAnalyses(Request request,VFSection vfSection){
		return vfVerificationLogic.verificationAnalyse(vfSection);
	}
	
	public List findBuyIsCount(Request request, String sectionid){
		return vfVerificationLogic.findBuyIsCount(sectionid);
	}
	
	public List findMaxBuyIsCount(Request request){
		return vfVerificationLogic.findMaxBuyIsCount();
	}
	/**
	 * 大类短溢分析查看历史
	 * @param vfSection
	 * @param seqNum
	 * @return
	 */
	public List<VFCategoryAnalyse> findVFCategoryAnalyses(Request request,VFSection vfSection, Integer seqNum){
		return vfVerificationLogic.findVFCategoryAnalyses(vfSection, seqNum);
	}
	/**
	 * 大类短溢分析
	 * @param request
	 * @param vfsection
	 * @return
	 */
	public List<VFCategoryAnalyse> categoryAnalyses(Request request,VFSection vfsection){
		return vfVerificationLogic.categoryAnalyse(vfsection);
	}
	
	/**
	 * 删除section【批次】下核查分析汇总数据
	 * @param request
	 * @param section
	 */
	public void deleteVFCategoryAnalyse(Request request, VFSection section){
		vfVerificationLogic.deleteVFCategoryAnalyse(section);
	}
	/**
	 * 查询并保存到合同分析表
	 * 
	 * @param section
	 *            批次
	 */
	public void gainContractAnalyse(Request request,VFSection section){
		vfContractAnalyseLogic.gainContractAnalyse(section);
	}
	/**
	 * 获取成品对应bom版本列表
	 * @return List<PtNo(料号)#BOM版本号>
	 */
	public Map<String,MaterialBomVersion> getMaterielMasterBomVersion(Request request){
		return vfVerificationLogic.getMaterielMasterBomVersion();
	}
	
	
	/**
	 * 查询所有的报关工厂常用bom版本
	 * @param request
	 * @return
	 */
	public List<MaterialBomVersion> findMaterialBomVersions(Request request) {
		return vfVerificationLogic.findMaterialBomVersions();
	}
	
	/**
	 * 删除section【批次】下核查分析汇总数据
	 * @param request
	 * @param section
	 */
	public void deleteVFAnalyse(Request request, VFSection section) {
		vfVerificationLogic.deleteVFAnalyse(section);
	}
	
	/**
	 * 查询未归类商品编码
	 * @param request
	 * @param index 
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerNotInCategory(Request request,int index, int length, String property,Object value, boolean isLike){
		return vfVerificationLogic.findBcsTenInnerNotInCategory(index,length,property,value,isLike);
	}
	
	/**
	 * 查询大类名称列表
	 * @param request
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findVFCategory(Request request, int index, int length,String property, Object value, boolean isLike){
		return vfVerificationLogic.findVFCategory(index,length,property,value,isLike);
	}	
	
	/**
	 * 添加报关商品到大类中
	 * @param request
	 * @param list
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> addBcsTenInnerMergeToCategory(Request request,List<BcsTenInnerMerge> list){		
		return vfVerificationLogic.addBcsTenInnerMergeToCategory(list);
	}
	/**
	 * 查询大类报关商品对应关系表
	 * @param request
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> findVFCategoryBcsTenInnerMerge(Request request, int index, int length,String property, Object value, boolean isLike){
		return vfVerificationLogic.findVFCategoryBcsTenInnerMerge(index,length,property,value,isLike);
	}
	
	/**
	 * 删除大类对应关系表
	 * @param sels
	 */
	public void deleteVFCategoryBcsTenInnerMerge(Request request,List<VFCategoryBcsTenInnerMerge> sels){
		vfVerificationLogic.deleteVFCategoryBcsTenInnerMerge(sels);
	}
	/**
	 * 撤销大类报关对应关系
	 * @param sels
	 */
	public List<VFCategoryBcsTenInnerMerge> undoVFCategoryBcsTenInnerMerge(Request request,List<VFCategoryBcsTenInnerMerge> sels){
		return vfVerificationLogic.undoVFCategoryBcsTenInnerMerge(sels);
	}
	
	/**
	 * 添加补充大类对应关系
	 * @param categoryInnerMerges 大类对应关系
	 * @param category 大类
	 * @param isAdd 是否新增大类
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> addCategoryToBcsTenInnerMergeCategory(Request request,List<VFCategoryBcsTenInnerMerge> categoryInnerMerges,VFCategory category,boolean isAdd){		
		return vfVerificationLogic.addCategoryToBcsTenInnerMergeCategory(categoryInnerMerges, category, isAdd);
	}
	/**
	 * 保存大类
	 * @param category
	 * @return
	 */
	public VFCategory saveVFCategory(Request request,VFCategory category){
		return vfVerificationLogic.saveVFCategory(category);
	}
	
	/**
	 * 查询料件报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param seqNum 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	public List<VFCustomsImg> findVFCustomsImg(Request request,VFSection section, String emsNo,Integer seqNum, Integer impExpType){
		return vfContractAnalyseLogic.findVFCustomsImg(section,emsNo,seqNum,impExpType);
	}
	/**
	 * 查询成品报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param seqNum 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	public List<VFCustomsExg> findVFCustomsExg(Request request, VFSection section, String emsNo,Integer seqNum){
		return vfContractAnalyseLogic.findVFCustomsExg(section,emsNo,seqNum); 
	}
	
	/**
	 * 从深加工结转收货单获取报关编码级结转料件
	 * @param section
	 * @return
	 */
	public List<VFTransferDiffHsImg> generateTransferDiffHsImgHsFormFpt(Request request,VFSection section){
		return vfTransferAnalyseLogic.generateTransferDiffHsImgHsFormFpt(section);
	}
	
	/**
	 * 从深加工结转发货单获取报关编码级结转结转成品
	 * @param section
	 * @return
	 */
	public List<VFTransferDiffHsExg> generateTransferDiffHsExgsFormFpt(Request request,VFSection section){
		return vfTransferAnalyseLogic.generateTransferDiffHsExgsFormFpt(section);
	}
	/**
	 * 编码级结转结转成品折算料件
	 * @param section
	 * @return
	 */
	public List<VFTransferDiffHsExgResolve> resolveTransferDiffHsExgs(Request request,
			VFSection section){
		return vfTransferAnalyseLogic.resolveTransferDiffHsExgs(section);
	}
	
	/**
	 * 汇总section【批次】下所有在制品汇总数据
	 * @param request
	 * @param section
	 * @return
	 */
	public List<VFFinishingStockAnalyse> finishingStockAnalyse(Request request, VFSection section){
		return vfFactoryAnalyseLogic.finishingStockAnalyse(section);
	}
	/**
	 * 删除section【批次】下在制品汇总数据
	 * @param request
	 * @param section
	 */
	public  void deleteVFFinishingStockAnalyse(Request request, VFSection section){
		vfFactoryAnalyseLogic.deleteVFFinishingStockAnalyse(section);
	}
	public List<VFFinishingImg> findVFFinishingImgs(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFFinishingImgs(vfSection, seqNum);
	}
	
    public List<VFBadImg> findVFBadImgs(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFBadImgs(vfSection, seqNum);
	}

    public List<VFFinishingExg> findVFFinishingExgs(Request request, VFSection vfSection) {
	
    	return vfFactoryAnalyseLogic.findVFFinishingExgs(vfSection, null);
    }

	public List<VFSemiBadExg> findVFSemiBadExgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.findVFSemiBadExgs(vfSection, null);
	}
	
	public List<VFFinishingExgConvert> findVFFinishingExgConverts(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFFinishingExgConverts(vfSection, seqNum);
	}
	
    public List<VFSemiBadExgConvert> findVFSemiBadExgConverts(Request request, VFSection vfSection, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFSemiBadExgConverts(vfSection, seqNum);
	}
	
	public void saveVFFinishingImgs(Request request, VFSection vfSection,
			List<VFFinishingImg> vfFinishingImgs) {
		vfFactoryAnalyseLogic.saveVFFinishingImgs(vfSection, vfFinishingImgs);
		
	}

	public void deleteVFFinishingExgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFFinishingExgs(vfSection);
	}
	
	public void deleteVFSemiBadExgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFSemiBadExgs(vfSection);
	}
	
	public void deleteVFBadExgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFBadExgs(vfSection);
	}

	public void deleteVFFinishingImgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFFinishingImgs(vfSection);
	}
	
	public void deleteVFBadImgs(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFBadImgs(vfSection);
	}
	
	public void deleteVFFinishingExgConverts(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFFinishingExgConverts(vfSection);
	}
	
	public void deleteVFSemiBadExgConverts(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFSemiBadExgConverts(vfSection);
	}
	
	public void deleteVFBadExgConverts(Request request, VFSection vfSection) {
		vfFactoryAnalyseLogic.deleteVFBadExgConverts(vfSection);
	}
	
	public List<VFFinishingStockAnalyse> findVFFinishingStockAnalyse(
			Request request, VFSection section, Integer seqNum) {
		
		return vfFactoryAnalyseLogic.findVFFinishingStockAnalyse(section, seqNum);
	}
	
	/**
	 * 在制品原材料转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingImg> convertVFFinishingImgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.convertVFFinishingImgs(vfSection);
	}
	
	/**
	 * 残次品原材料转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFBadImg> convertVFBadImgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.convertVFBadImgs(vfSection);
	}
	
	/**
	 * 【在制品成品属性库存】折算报关数量
	 * @param vfSection
	 * @return
	 */
	public String convertVFFinishingExgConvertsEms(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFFinishingExgConvertsEms(vfSection);
	}
	
	/**
	 * 【残次品半成品属性库存】折算报关数量
	 * @param vfSection
	 * @return
	 */
	public String convertVFSemiBadExgConvertsEms(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFSemiBadExgConvertsEms(vfSection);
	}

	/**
	 * 在制品成品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingExgConvert> resolveVFFinishingExgs(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.resolveVFFinishingExgs(vfSection);
	}
	
	/**
	 * 残次品半成品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFSemiBadExgConvert> resolveVFSemiBadExgs(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.resolveVFSemiBadExgs(vfSection);
	}
	/**
	 * 查询 vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public List<VFAttachmentManagement> findVFAttachmentManagement(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.findVFAttachmentManagement(vfSection);
	}
	
	/**
	 * 保存vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * @param request
	 * @param attachment
	 */
	public void saveVFAttachmentManagements(Request request,List<VFAttachmentManagement> attachment){
		vfFactoryAnalyseLogic.saveVFAttachmentManagements(attachment);
	}
	/**
	 * 保存vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * @param request
	 * @param attachment
	 */
	public void saveVFAttachmentManagement(Request request,VFAttachmentManagement attachment){
		vfFactoryAnalyseLogic.saveVFAttachmentManagements(attachment);
	}
	
	/**
	 * 上传核查批次附件
	 * @param request  批次
	 * @param file  附件
	 * @param fileType  附件类型
	 * @return
	 */
	 
	public void upLoadAttachment(Request request,byte[] fileContent,String fileName){
		vfFactoryAnalyseLogic.upLoadAttachment(fileContent, fileName);
	}
	
	/**
	 * 下载核查批次附件
	 * @param request  
	 * @param fileName
	 * @return
	 */
	 
	public byte[] downLoadAttachment(Request request,String fileName){
		return vfFactoryAnalyseLogic.downLoadAttachment(fileName);
	}
	
	/**
	 * 删除核查批次料附件
	 */
	public void deleteAttachment(Request request,VFAttachmentManagement attachment){
		String fileName = attachment.getAttachmentName();
		String fileId = attachment.getId();
		vfFactoryAnalyseLogic.deleteAttachment(fileName, attachment);
	}
	/**
	 * 删除核查批次料附件
	 */
	public void deleteAttachmentAll(Request request,VFSection section){
		vfFactoryAnalyseLogic.deleteAttachmentAll(section);
	}
	
	/**
	 * 保存模板
	 * @param request
	 * @param section
	 */
	public void saveVFAttachmentTemplate(Request request,VFSection section){
		vfFactoryAnalyseLogic.saveVFAttachmentTemplate(section);
	}
	
	/**
	 * 查询在附件管理批次
	 * @param request
	 * @param boo true：已经存在，false：不存在
	 * @return
	 */
	public List<VFSection> findAttachmentSection(Request request,Boolean boo){
		return vfFactoryAnalyseLogic.findAttachmentSection(boo);
	}

	/////////////////////////////////
	public List<VFBadExg> findVFBadExgs(Request request, VFSection vfSection) {
		
		return vfFactoryAnalyseLogic.findVFBadExgs(vfSection, null);
	}
    public List<VFBadExgConvert> findVFBadExgConverts(Request request, VFSection vfSection, Integer seqNum) {
			
		return vfFactoryAnalyseLogic.findVFBadExgConverts(vfSection, seqNum);
	}
    /**
	 * 残次品成品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFBadExgConvert> resolveVFBadExgs(Request request, VFSection vfSection){
		return vfFactoryAnalyseLogic.resolveVFBadExgs(vfSection);
	}
	/**
	 * 【残次品成品属性库存】折算报关数量
	 * @param vfSection
	 * @return
	 */
	public String convertVFBadExgConvertsEms(Request request,VFSection vfSection){
		return vfFactoryAnalyseLogic.convertVFBadExgConvertsEms(vfSection);
	}
	/**
	 * 删除section【批次】外发库存汇总
	 * @param request
	 * @param section
	 */
	public  void deleteVFBadStockAnalyse(Request request, VFSection section){
		vfFactoryAnalyseLogic.deleteVFBadStockAnalyse(section);
	}
	/**
	 * 查找section【批次】下残次品库存汇总
	 * @param request
	 * @param section
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFBadStockAnalyse> findVFBadAnalyse(Request request, VFSection section, Integer seqNum){
		return vfFactoryAnalyseLogic.findVFBadAnalyse(section, seqNum);
	}
	/**
	 * 汇总section【批次】下所有在制品汇总数据
	 * @param request
	 * @param section
	 * @return
	 */
	public List<VFBadStockAnalyse> badVFStockAnalyse(Request request, VFSection section){
		return vfFactoryAnalyseLogic.badVFStockAnalyse(section);
	}
	
	/////////////////////////////////

	
	/**
	 * 查询操作进度
	 * @param request
	 * @param section
	 * @return
	 */
	public List<Integer> findVFSectionByProcess(Request request,VFSection section){
		return vfFactoryAnalyseLogic.findVFSectionByProcess(section);
	}

	/**
	 * 保存成品
	 * @param request
	 * @param baseStockExg
	 */
	public void saveVFBaseStockExg(Request request,VFBaseStockExg baseStockExg){
		vfVerificationLogic.saveVFBaseStockExg(baseStockExg);
	}
	
	/**
	 * 保存料件
	 * @param request
	 * @param baseStockImg
	 */
	public void saveVFBaseStockImg(Request request,VFBaseStockImg baseStockImg){
		vfVerificationLogic.saveVFBaseStockImg(baseStockImg);
	}
	
	/**
	 * 删除成品
	 * @param request
	 * @param baseStockExg
	 */
	public void deleteVFBaseStockExgs(Request request,List<VFBaseStockExg> baseStockExgs){
		vfVerificationLogic.deleteVFBaseStockExgs(baseStockExgs);
	}
	
	/**
	 * 删除料件
	 * @param request
	 * @param baseStockImg
	 */
	public void deleteVFBaseStockImgs(Request request,List<VFBaseStockImg> baseStockImgs){
		vfVerificationLogic.deleteVFBaseStockImgs(baseStockImgs);
	}
}
