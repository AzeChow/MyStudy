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
import com.bestway.bcs.verification.entity.VFTransferDiffExg;
import com.bestway.bcs.verification.entity.VFTransferDiffExgConvert;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExg;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExgResolve;
import com.bestway.bcs.verification.entity.VFTransferDiffHsImg;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
public interface VFVerificationAction {
	
	/**
	 * 根据批次判断是否存在数据
	 * @param request
	 * @param section
	 * @param entityClass
	 * @return
	 */
	boolean isExistsBySection(Request request,VFSection section,Class<? extends BaseScmEntity> entityClass);
	/**
	 * 批次设定
	 * 获得最大ID
	 */
	List<Object> findMaxNO(Request request);
	
	/**
	 * 批次设定
	 * 获得表数据
	 */
	List<VFSection> findVFSectionList(Request request);
	
	
	/**
	 * 批次设定
	 * delete data by 
	 */
	void deleteVFSection(Request request,VFSection vf);
	
	/**
	 * 批次设定
	 * delete data by list
	 */
	void deleteVFSectionByList(Request request,List <VFSection> list);
	
	
	/**
	 * 批次设定
	 * save data 
	 */
	VFSection saveVFSection(Request request,VFSection vf);
	
	
	/**
	 * 修改批次数据
	 * save data 
	 */
	VFSection saveVFModifySection(Request request,VFSection vf);
	
	/**
	 * 批次设定
	 * save data 
	 */
	void updateVFSection(Request request,VFSection vf);
	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	<E extends BaseEntity> E update(E entity);
	/**
	 * 更新短溢分析单价数据（同步关税、增值税栏位）
	 * @param analyse
	 * @return
	 */
	VFAnalyse updateAnalysePrice(VFAnalyse analyse);
	
	/**
	 * 批次是否存在
	 * find data 
	 */
	List<VFSection> findExistsVFSection(Request request,VFSection vf);
	
	/**
	 * 查询  vfSection 【核查批次】vfstockDenImg【对应关系】
	 * @param request
	 */
	List<Object> findVFFactoryBcsInnerMergeList(Request request);
	
	/**
	 * 查询  vfSection 【核查批次】vfstockDenImg【外发成品存在BOM】
	 * @param request
	 */
	List<Object> findVFOutSendExgInBom(Request request);
	
	/**
	 * 查询  vfSection 【核查批次】vfstockDenImg【最大version BOM 对应的 PtNo】
	 * @param request
	 */
	List<Object> findVFOutSendExgInMaxBom(Request request);
	
	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	List<BcsInnerMerge> findBcsInnerMerge(Request request, String materielType);
	
	/**
//	 * 查找 正在执行的合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo 合同号
	 *            
	 * @return List 是ContractImg型，存放合同备案料件资料
	 */
	public List<Object[]> findContractImgByEmsNo(Request request,List<String> emsNos);
	/**
//	 * 查找 正在执行的合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo 合同号
	 *            
	 * @return List 是ContractExg型，存放合同备案成品
	 */
	public List<Object[]> findContractExgByEmsNo(Request request,List<String> emsNos);
	
	
	/**
	 * 查询  vfSection 【核查批次】vfstockDenImg【外发库存】
	 * @param request
	 * @param vfSection
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFStockOutSendExg> findVFStockOutSendExgs(Request request, VFSection vfSection);
	
	/**
	 * 查询  vfSection 【核查批次】vfstockDenImg【外发库存】
	 * @param request
	 * @param vfSection
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFStockOutSendExgConvert> findVFStockOutSendExgsConvert(Request request, VFSection vfSection, Integer seqNum);
	/**
	 * 折算
	 * @param request
	 * @param vfSection
	 * @return
	 */
	List<VFStockOutSendExgConvert> convertFactoryVFStockOutSendExgsConvert(Request request, VFSection vfSection);
	/**
	 * 折算
	 * @param request
	 * @param vfSection
	 * @return
	 */
	String convertHsVFStockOutSendExgsConvert(Request request, VFSection vfSection);
	/**
	 * 查询 vfSection【核查批次】VFStockOutFactoryImg 【厂外库存】
	 * @param request
	 * @param vfSection
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFStockOutFactoryImg> findVFStockOutFactoryImgs(Request request, VFSection vfSection, Integer seqNum);
	/**
	 * 折算
	 * @param request
	 * @param vfSection
	 * @return
	 */
	List<VFStockOutFactoryImg> convertVFStockOutFactoryImgs(Request request, VFSection vfSection);
	
	/**
	 * 查询  vfSection 【核查批次】VFStockBuyImg【内购料件库存】
	 * @param request
	 * @param vfSection
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFStockBuyImg> findVFStockBuyImgs(Request request, VFSection vfSection, Integer seqNum);
	/**
	 * 折算
	 * @param request
	 * @param vfSection
	 * @return
	 */
	List<VFStockBuyImg> convertVFStockBuyImgs(Request request, VFSection vfSection);
	
	/**
	 * 查询  vfSection 【核查批次】VFStockBuyImg【在途料件库存】
	 * @param request
	 * @param vfSection
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFStockTravelingImg> findVFStockTravelingImgs(Request request, VFSection vfSection, Integer seqNum);
	/**
	 * 折算
	 * @param request
	 * @param vfSection
	 * @return
	 */
	List<VFStockTravelingImg> convertVFStockTravelingImgs(Request request, VFSection vfSection);
		
	//
	/**
	 * 保存  vfSection 【核查批次】vfstockDenImg【外发库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void saveVFStockOutSendExgs(Request request, VFSection vfSection,List<VFStockOutSendExg> list);  //VFStockOutSendExg
	
	
	/**
	 * 保存  vfSection 【核查批次】vfstockDenImg【外发库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void saveVFStockOutSendExgsConvert(Request request, VFSection vfSection,List<VFStockOutSendExgConvert> list);
	
	
	/**
	 * 保存 vfSection【核查批次】VFStockOutFactoryImg 【厂外库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void saveVFStockOutFactoryImgs(Request request, VFSection vfSection,List<VFStockOutFactoryImg> list);
	
	
	/**
	 * 保存  vfSection 【核查批次】VFStockBuyImg【内购料件库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void saveVFStockBuyImgs(Request request, VFSection vfSection,List<VFStockBuyImg> list);
	
	
	/**
	 * 保存  vfSection 【核查批次】VFStockBuyImg【在途料件库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void saveVFStockTravelingImgs(Request request, VFSection vfSection,List<VFStockTravelingImg> list);		
	
	
	
	
	
	
	
	
	
	
	
	//*****//
	/**
	 * 删除  vfSection 【核查批次】vfstockDenImg【外发库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockOutSendExgs(Request request, VFSection vfSection);
	
	/**
	 * 删除  vfSection 【核查批次】vfstockDenImg【外发库存折算后】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockOutSendExgsConvert(Request request, VFSection vfSection);
	
	
	/**
	 * 删除 vfSection【核查批次】VFStockOutFactoryImg 【厂外库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockOutFactoryImgs(Request request, VFSection vfSection);
	
	
	/**
	 * 删除  vfSection 【核查批次】VFStockBuyImg【内购料件库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockBuyImgs(Request request, VFSection vfSection);
	
	
	/**
	 * 删除  vfSection 【核查批次】VFStockBuyImg【在途料件库存】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockTravelingImgs(Request request, VFSection vfSection);
	
	
	
	
	
	// ------------------------- 工厂分析接口 开始---------------------- // 
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockImg【料件库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockImg> findVFStockImgs(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutSendImg【外发库存原材料】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockOutSendImg> findVFStockOutSendImgs(Request request, VFSection vfSection, Integer seqNum);
	
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockExg【成品库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockExg> findVFStockExgs(Request request, VFSection vfSection);
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingExg【在途库存成品库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockTravelingExg> findVFStockTravelingExgs(Request request, VFSection vfSection);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutSendSemiExg【外发库存半成品】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockOutSendSemiExg> findVFStockOutSendSemiExgs(Request request, VFSection vfSection);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockSemiExgHadStore【半成品库存（已入库）】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockSemiExgHadStore> findVFStockSemiExgHadStore(Request request, VFSection vfSection);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockExgConvert【成品折算数据】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockExgConvert> findVFStockExgConverts(Request request, VFSection vfSection, Integer seqNum);
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingExgConvert【在途库存成品折算数据】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockTravelingExgConvert> findVFStockTravelingExgConverts(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutSendSemiExg【外发库存半成品存折算料件转换报关数据】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockOutSendSemiExgConvert> findVFStockOutSendSemiExgConverts(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockSemiExgPtHadStoreConvert【半成品库存（已入库）折算数据】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockSemiExgHadStoreConvert> findVFStockSemiExgHadStoreConverts(Request request, VFSection vfSection, Integer seqNum);
	
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockProcessImg【半成品库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockProcessImg> findVFStockProcessImgs(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutFactoryImg【厂外库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockOutFactoryImg> findVFStockProcessImgsout(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockBuyImg【内购库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockBuyImg> findVFStockProcessImgsin(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingImg【在途料件库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockTravelingImg> findVFStockProcessImgslu(Request request, VFSection vfSection, Integer seqNum);
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingImg【外发料件库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFStockOutSendImg> findVFStockProcessImgsoutsend(Request request, VFSection vfSection, Integer seqNum);
	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingImg【在制品料件库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFFinishingImg> findVFStockProcessImgsing(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFBadImg【残次品料件库存】 
	 * @param request
	 * @param vfSection
	 * @param Integer seqNum
	 * @return
	 */
	List<VFBadImg> findVFStockProcessImgsbad(Request request, VFSection vfSection, Integer seqNum);
	
	/**
	 * 保存 vfStockImgs【料件库存】在 vfSection【核查批次】下
	 * @param request
	 * @param vfSection
	 * @param vfStockImgs
	 * @return
	 */
	void saveVFStockImgs(Request request, VFSection vfSection, List<VFBaseStockImg> vFBaseStockImg);
	
	
	/**
	 * 保存 vfStockExgs【成品库存】在 vfSection【核查批次】下
	 * @param request
	 * @param vfSection
	 * @param vfStockExgs
	 * @return
	 */
	void saveVFStockExgs(Request request, VFSection vfSection, List<VFBaseStockExg> vFBaseStockExg);
	
	/**
	 * 保存 VFStockSemiExgHadStore【半成品库存（已入库）】在 vfSection【核查批次】下
	 * @param request
	 * @param vfSection
	 * @param vfStockExgs
	 * @return
	 */
	void saveVFStockSemiExgHadStore(Request request, VFSection vfSection, List<VFStockSemiExgHadStore> vFStockSemiExgPtHadStore);
	
	
	/**
	 * 保存 vfStockExgConverts【成品折算数据】在 vfSection【核查批次】下
	 * @param request
	 * @param vfSection
	 * @param vfStockExgConverts
	 * @return
	 */
	void saveVFStockExgConverts(Request request, VFSection vfSection, List<VFStockExgConvert> vfStockExgConverts);
	
	
	/**
	 * 保存 vfStockProcessImgs【半成品库存】在 vfSection【核查批次】下
	 * @param request
	 * @param vfSection
	 * @param vfStockProcessImgs
	 * @return
	 */
	void saveVFStockProcessImgs(Request request, VFSection vfSection, List<VFStockProcessImg> vfStockProcessImgs);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockImg【料件库存】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockImgs(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockOutSendImg【外发库存原材料】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockOutSendImgs(Request request, VFSection vfSection);
	
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockExg【成品库存】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockExgs(Request request, VFSection vfSection);
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockExg【在途库存成品库存】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockTravelingExgs(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockOutSendSemiExg【外发库存半成品】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockOutSendSemiExgs(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockSemiExgHadStore【半成品库存（已入库）】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockSemiExgHadStores(Request request, VFSection vfSection);
	
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockExgConvert【成品折算数据】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockExgConverts(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockTravelingExgConvert【在途库存成品折算数据】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockTravelingExgConverts(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockOutSendSemiExgConvert【外发库存半成品折算数据】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockOutSendSemiExgConverts(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockSemiExgPtHadStoreConvert【半成品库存（已入库）】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockSemiExgHadStoreConverts(Request request, VFSection vfSection);
	
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockProcessImg【半成品库存】 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	void deleteVFStockProcessImgs(Request request, VFSection vfSection);	
	
	
	/**
	 * 汇总section【批次】下所有工厂数据
	 * @param request
	 * @param section
	 * @return
	 */
	List<VFStockAnalyse> stockAnalyse(Request request, VFSection section);
	
	/**
	 * 汇总section【批次】外发库数据
	 * @param request
	 * @param section
	 * @return
	 */
	List<VFStockOutSendAnalyse> stockVFStockOutSendAnalyse(Request request, VFSection section);
	/**
	 * 查找section【批次】下库存分析数据
	 * @param request
	 * @param section
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFStockAnalyse> findVFStockAnalyse(Request request, VFSection section, Integer seqNum);
	/**
	 * 查找section【批次】下外发库存汇总
	 * @param request
	 * @param section
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFStockOutSendAnalyse> findVFStockOutSendAnalyse(Request request, VFSection section, Integer seqNum);
	/**
	 * 删除section【批次】下库存分析汇总数据
	 * @param request
	 * @param section
	 */
	void deleteVFStockAnalyse(Request request, VFSection section);
	
	
	
	/**
	 * 删除section【批次】下外发库存汇总
	 * @param request
	 * @param section
	 */
	void deleteVFStockOutSendAnalyse(Request request, VFSection section);
	
	/**
	 * 删除section【批次】下核查分析汇总数据
	 * @param request
	 * @param section
	 */
	void deleteVFAnalyse(Request request, VFSection section);
	
	/**
	 * 库存料件 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public String convertImgConverts(Request request, VFSection vfSection,String table);
	
	/**
	 * 外发库存原材料 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendImg> convertVFStockOutSendImgs(Request request, VFSection vfSection);
	
	/**
	 * 折算报关数量
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExgConvert> convertVFStockExgConverts(Request request,VFSection vfSection);
	/**
	 * 【成品属性库存】折算报关数量,折算时，把对应关系存在的品名折算，没有做对应关系的提示出来
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockExgConvertsEms(Request request,VFSection vfSection);
	/**
	 * 【在途库存成品】折算报关数量,折算时，把对应关系存在的品名折算，没有做对应关系的提示出来
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockTravelingExgConvertsEms(Request request,VFSection vfSection);
	
	/**
	 * 【外发库存半成品】折算报关数量,折算时，把对应关系存在的品名折算，没有做对应关系的提示出来
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockOutSendSemiExgConvertsEms(Request request,VFSection vfSection);
	
	/**
	 * 【半成品库存（已入库）】折算报关数量,折算时，把对应关系存在的品名折算，没有做对应关系的提示出来
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockSemiExgHadStoreConvertEms(Request request,VFSection vfSection);
	
	
	/**
	 * 在产品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockProcessImg> convertVFStockProcessImgs(Request request, VFSection vfSection);
	
	
	/**
	 * 产品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExgConvert> resolveVFStockExgs(Request request, VFSection vfSection);
	/**
	 * 在途库存成品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingExgConvert> resolveVFStockTravelingExgs(Request request, VFSection vfSection);
	
	/**
	 * 外发库存半成品 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendSemiExgConvert> resolveVFStockOutSendSemiExgs(Request request, VFSection vfSection);
	
	/**
	 * 半成品库存（已入库） 转 报关料件
	 * @param vfSection
	 * @return
	 */
	public List<VFStockSemiExgHadStoreConvert> resolveVFStockSemiExgHadStoreConverts(Request request, VFSection vfSection);
	
	/**
	 * 短溢分析查看历史
	 * @param vfSection
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFAnalyse> findVFAnalyses(Request request,VFSection vfSection, Integer seqNum);
	
	/**
	 * 短溢分析
	 * @param vfSection
	 * @return
	 */
	public List<VFAnalyse> verificationAnalyses(Request request,VFSection vfSection);
	
	/**
	 * 是否内购库存参与短溢分析
	 * @param request
	 * @return
	 */
	public List findBuyIsCount(Request request, String sectionid);
	public List findMaxBuyIsCount(Request request);
	/**
	 * 大类短溢分析查看历史
	 * @param vfSection
	 * @param seqNum
	 * @return
	 */
	public List<VFCategoryAnalyse> findVFCategoryAnalyses(Request request,VFSection vfSection, Integer seqNum);
	/**
	 * 大类短溢分析
	 * @param request
	 * @param vfsection
	 * @return
	 */
	public List<VFCategoryAnalyse> categoryAnalyses(Request request,VFSection vfsection);
	
	/**
	 * 删除section【批次】下核查分析汇总数据
	 * @param request
	 * @param section
	 */
	void deleteVFCategoryAnalyse(Request request, VFSection section);
	//
	//
	// ------------------------- 工厂分析接口 结束---------------------- // 


	// ------------------------- 合同分析接口 开始---------------------- // 
	/**
	 * 获取并保存成品报关明细表
	 * @param request
	 * @param section 批次号
	 * @return
	 */
	public void gainVFCustomsExg(Request request,VFSection section);
	/**
	 * 获取并保存料件报关明细表
	 * @param request
	 * @param section 批次号
	 * @return
	 */
	public void  gainVFCustomsImg(Request request,VFSection section);
	
	/**
	 * 根据批次号分页查询查询
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
			int analyType);
	
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
			Object value, boolean isLike,int analyType);
	
	/**
	 * 折算成品并保存到成品折料统计表
	 * 
	 * @param section
	 *            批次
	 */
	public void convertCustomsCountExgToImg(Request request,VFSection section);
	
	/**
	 * 根据批次号、料件归并序号成品折料统计表
	 * 
	 * @param section
	 *            批次
	 * @param mergerNo
	 *           料件归并序号
	 */
	public List<VFCustomsCountExgConvert> findConvertCustomsCountExg(Request request,VFSection section,Integer mergerNo);
	
	/**
	 * 查询并保存到合同分析表
	 * 
	 * @param section
	 *            批次
	 */
	public void gainContractAnalyse(Request request,VFSection section);
	
	
	// ------------------------- 合同分析接口 结束---------------------- // 
	
	// --------------------------结转分析接口 开始-----------------------//
	/**
	 * 判断vfsection（批次）中是否存在结转成品信息
	 * @param request
	 * @param vfSection
	 * @return
	 */
	boolean isExistVFTransferDiffExgs(Request request,VFSection vfSection);
	/**
	 * 判断vfsection（批次）中是否存在结转成品信息(编码级)
	 * @param request
	 * @param vfSection
	 * @return
	 */
	boolean isExistVFTransferDiffHsExgs(Request request,VFSection vfSection);
	/**
	 * 判断vfsection（批次）中是否存在结转料件信息
	 * @param request
	 * @param vfSection
	 * @return
	 */
	boolean isExistVFTransferDiffImgs(Request request,VFSection vfSection);	
	/**
	 * 判断vfsection（批次）中是否存在结转料件差额(编码级)信息
	 * @param request
	 * @param vfSection
	 * @return
	 */
	boolean isExistVFTransferDiffHsImgs(Request request,VFSection vfSection);	
	/**
	 * 判断vfsection（批次）中是否存在结转差额汇总数据
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffCounts(Request request,VFSection vfSection);
	/**
	 * 批量保存  一批次的 结转成品差额表
	 * @param vfSection 批次
	 * @param request
	 * @param exgLs
	 */
	void saveVFTransferDiffExgs(Request request,VFSection vfSection, List<VFTransferDiffExg> exgLs);
	/**
	 * 批量保存  一批次的 结转成品差额表(编码级)
	 * @param vfSection 批次
	 * @param request
	 * @param hsExgLs
	 */
	void saveVFTransferDiffHsExgs(Request request,VFSection vfSection, List<VFTransferDiffHsExg> hsExgLs);
	/**
	 * 批量保存 一批次的结转料件差额表
	 * @param vfSection 批次
	 * @param request
	 * @param imgLs
	 */
	void saveVFTransferDiffImgs(Request request, VFSection vfSection,List<VFTransferDiffImg> imgLs);
	
	/**
	 * 批量保存 结转料件差额表(编码级)
	 * @param request
	 * @param imgLs
	 */
    void saveVFTransferDiffHsImgs(Request request,VFSection vfSection,  List<VFTransferDiffHsImg> hsImgLs);
	/**
	 * 根据section(批次)查询结转成品差额表信息
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFTransferDiffExg> findVFTransferDiffExgs(Request request, VFSection section);
	/**
	 * 根据section(批次)查询结转成品差额表信息(编码级)
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFTransferDiffHsExg> findVFTransferDiffHsExgs(Request request, VFSection section);
	/**
	 * 根据section(批次)查询结转成品差额折料转换报关数据信息
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFTransferDiffExgConvert> findVFTransferDiffExgConverts(Request request, VFSection section,Integer seqNum);
	/**
	 * 根据section(批次)查询结转成品分解料件表(编码级)
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFTransferDiffHsExgResolve> findVFTransferDiffHsExgResolves(Request request, VFSection section,Integer seqNum);
	/**
	 * 根据section(批次)查询结转料件差额表信息
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFTransferDiffImg> findVFTransferDiffImgs(Request request, VFSection section,Integer seqNum);
	/**
	 * 根据section(批次)查询结转料件差额表信息(编码级)
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFTransferDiffHsImg> findVFTransferDiffHsImgs(Request request, VFSection section,Integer seqNum);
	
	/**
	 * 根据section(批次)批量删除结成品差额表  
	 * @param request
	 * @param section 批次信息
	 */
	void deleteVFTransferDiffExgs(Request request,VFSection section);
	/**
	 * 根据section(批次)批量删除结成品差额表  (编码级)
	 * @param request
	 * @param section 批次信息
	 */
	void deleteVFTransferDiffHsExgs(Request request,VFSection section);
	/**
	 * 根据section(批次)批量删除结转料件差额表  
	 * @param request
	 * @param section 批次信息
	 */
	void deleteVFTransferDiffImgs(Request request,VFSection section);	
	
	/**
	 * 根据section(批次)批量删除结转料件差额表 (编码级)
	 * @param request
	 * @param section 批次信息
	 */
	void deleteVFTransferDiffHsImgs(Request request,VFSection section);	
	/**
	 * 根据section批次 将该批次的结转成品拆分成料件
	 * @param request
	 * @param section 批次
	 * @return
	 */
	List<VFTransferDiffExgConvert> unpickExgToImg(Request request,VFSection section);
	/**
	 * 根据section(批次)将 成品拆分料件 转成品拆分料件报关料件
	 * @param request
	 * @param section
	 * @return
	 */
	String convertVFTransferDiffExgConvertToCustoms(Request request,VFSection section);
	/**
	 *  根据section(批次)将 结转料件转成结转料件报关料件
	 * @param request
	 * @param section
	 * @return
	 */
	List<VFTransferDiffImg> convertVFTransferDiffImgToCustoms(Request request,VFSection section);
	
	/**
	 * 获得并保存料件报关数据统计表
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public void gainVFCustomsCountImg(Request request,VFSection section);
	/**
	 * 获得并保存成品报关数据统计表
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public void gainVFCustomsCountExg(Request request,VFSection section);
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
	public List<Object> findVFBySection(Request request,VFSection section,int analyType,Integer mergerNo);
	
	
	/**
	 *  获得某个批次号合同数据分析数据表
	 * 
	 * @param section
	 *            批次
	 *   @param  mergerNo 归并序号 
	 * @return
	 */
	public List<VFContractAnalyse> findVFContractAnalyseVFBySection(Request request,VFSection section,Integer mergerNo);
	/**
	 * 根据批次号删除数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public int delectVFBySection(Request request,VFSection section,int analyType);
	/**
	 * 判断是否存在某批次的数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public boolean isExistVFBySection(Request request,VFSection section,int analyType);
	/**
	 *  根据section(批次)查询结转汇总信息
	 * @param request
	 * @param section 批次
	 * @param seqNums 归并序号
	 * @return
	 */
	List findVFTransferDiffCount(Request request, VFSection section,Integer seqNums);
	/**
	 * 根据section(批次) 计算成品结转、料件结转报关料件 汇总信息
	 * @param request
	 * @param section 批次
	 * @return
	 */
	List cacuVFTransferDiffCount(Request request, VFSection section);
	/**
	 * 根据section(批次)删除汇总信息
	 * @param request
	 * @param section 批次
	 */
	void deleteVFTransferDiffCount(Request request, VFSection section);

	// --------------------------结转分析接口 结束-----------------------//
	
	
	/**
	 * 查询所有的报关工厂常用bom版本
	 * @param request
	 * @return
	 */
	List<MaterialBomVersion> findMaterialBomVersions(Request request);

	/**
	 * 获取成品对应bom版本列表
	 * @return List<PtNo(料号)#BOM版本号>
	 */
	public Map<String,MaterialBomVersion> getMaterielMasterBomVersion(Request request);
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
	List<BcsTenInnerMerge> findBcsTenInnerNotInCategory(Request request,int index, int length, String property,Object value, boolean isLike);
	/**
	 * 添加报关商品到大类中
	 * @param request
	 * @param list
	 * @return
	 */
	List<VFCategoryBcsTenInnerMerge> addBcsTenInnerMergeToCategory(Request request,List<BcsTenInnerMerge> list);
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
	List findVFCategory(Request request, int index, int length,String property, Object value, boolean isLike);
	/**
	 * 查询大类报关商品对应关系表
	 * @param request
	 * @return
	 */
	List<VFCategoryBcsTenInnerMerge> findVFCategoryBcsTenInnerMerge(Request request, int index, int length,String property, Object value, boolean isLike);
	/**
	 * 删除大类对应关系表
	 * @param sels
	 */
	void deleteVFCategoryBcsTenInnerMerge(Request request,List<VFCategoryBcsTenInnerMerge> sels);
	/**
	 * 撤销大类报关对应关系
	 * @param request
	 * @param sels
	 */
	List<VFCategoryBcsTenInnerMerge> undoVFCategoryBcsTenInnerMerge(Request request,List<VFCategoryBcsTenInnerMerge> sels);
	/**
	 * 添加补充大类对应关系
	 * @param categoryInnerMerges 大类对应关系
	 * @param category 大类
	 * @param isAdd 是否新增大类
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> addCategoryToBcsTenInnerMergeCategory(Request request,List<VFCategoryBcsTenInnerMerge> categoryInnerMerges,VFCategory category,boolean isAdd);
	/**
	 * 保存大类
	 * @param category
	 * @return
	 */
	public VFCategory saveVFCategory(Request request,VFCategory category);
	/**
	 * 查询料件报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param credenceNo 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	List<VFCustomsImg> findVFCustomsImg(Request request,VFSection section, String emsNo,Integer credenceNo, Integer impExpType);
	/**
	 * 查询成品报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param credenceNo 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	List<VFCustomsExg> findVFCustomsExg(Request request, VFSection section, String emsNo,Integer credenceNo);

	/**
	 * 从深加工结转收货单获取报关编码级结转料件
	 * @param section
	 * @return
	 */
	public List<VFTransferDiffHsImg> generateTransferDiffHsImgHsFormFpt(Request request,VFSection section);
	
	/**
	 * 从深加工结转发货单获取报关编码级结转结转成品
	 * @param section
	 * @return
	 */
	public List<VFTransferDiffHsExg> generateTransferDiffHsExgsFormFpt(Request request,VFSection section);
	/**
	 * 编码级结转结转成品折算料件
	 * @param section
	 * @return
	 */
	public List<VFTransferDiffHsExgResolve> resolveTransferDiffHsExgs(Request request,
			VFSection section);
	/**
	 * 汇总section【批次】下所有在制品汇总数据
	 * @param request
	 * @param section
	 * @return
	 */
	List<VFFinishingStockAnalyse> finishingStockAnalyse(Request request, VFSection section);
	/**
	 * 删除section【批次】下在制品库存分析汇总数据
	 * @param request
	 * @param section
	 */
	void deleteVFFinishingStockAnalyse(Request request, VFSection section);
	/**
	 * 在制品成品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	List<VFFinishingExgConvert> resolveVFFinishingExgs(Request request,
			VFSection vfSection);
	
	/**
	 * 残次品半成品库存 转 报关料件
	 * @param vfSection
	 * @return
	 */
	List<VFSemiBadExgConvert> resolveVFSemiBadExgs(Request request,
			VFSection vfSection);
	
	/**
	 * 【在制品成品属性库存】折算报关数量
	 * @param vfSection
	 * @return
	 */
	String convertVFFinishingExgConvertsEms(Request request, VFSection vfSection);
	
	/**
	 * 【残次品半成品属性库存】折算报关数量
	 * @param vfSection
	 * @return
	 */
	String convertVFSemiBadExgConvertsEms(Request request, VFSection vfSection);
	
	/**
	 * 在制品原材料转 报关料件
	 * @param vfSection
	 * @return
	 */
	List<VFFinishingImg> convertVFFinishingImgs(Request request,
			VFSection vfSection);
	
	/**
	 * 残次品原材料转 报关料件
	 * @param vfSection
	 * @return
	 */
	List<VFBadImg> convertVFBadImgs(Request request,
			VFSection vfSection);
	
	/**
	 * 查找section【批次】下在制品库存分析数据
	 * @param section
	 * @return
	 */
	List<VFFinishingStockAnalyse> findVFFinishingStockAnalyse(Request request,
			VFSection section, Integer seqNum);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFFinishingExgConvert【在制品成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	void deleteVFFinishingExgConverts(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFSemiBadExgConvert【残次品半成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	void deleteVFSemiBadExgConverts(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFFinishingImg【在制品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	void deleteVFFinishingImgs(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFBadImg【残次品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	void deleteVFBadImgs(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFFinishingExg【在制品成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	void deleteVFFinishingExgs(Request request, VFSection vfSection);
	
	/**
	 * 删除 vfSection【核查批次】下的所有 VFSemiBadExg【残次品半成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	void deleteVFSemiBadExgs(Request request, VFSection vfSection);
	
	/**
	 * 保存 vfFinishingImg【在制品原材料】在 vfSection【核查批次】下
	 * 
	 * @param vfSection
	 * @param vfStockImgs
	 * @return
	 */
	void saveVFFinishingImgs(Request request, VFSection vfSection,
			List<VFFinishingImg> vfFinishingImgs);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFFinishingExgConvert【在制品成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	List<VFFinishingExgConvert> findVFFinishingExgConverts(Request request,
			VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFSemiBadExgConvert【残次品半成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	List<VFSemiBadExgConvert> findVFSemiBadExgConverts(Request request,
			VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFFinishingExg【在制品成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	List<VFFinishingExg> findVFFinishingExgs(Request request,
			VFSection vfSection);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFSemiBadExg【残次品半成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	List<VFSemiBadExg> findVFSemiBadExgs(Request request,
			VFSection vfSection);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFFinishingImg【在制品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	List<VFFinishingImg> findVFFinishingImgs(Request request,
			VFSection vfSection, Integer seqNum);
	
	/**
	 * 查询 vfSection【核查批次】下的所有 VFBadImg【残次品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	List<VFBadImg> findVFBadImgs(Request request,
			VFSection vfSection, Integer seqNum);
	/**
	 * 查询 vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * @param request
	 * @param vfSection
	 * @return
	 */
	List<VFAttachmentManagement> findVFAttachmentManagement(Request request,VFSection vfSection);
	
	/**
	 * 保存vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * @param request
	 * @param attachment
	 */
	void saveVFAttachmentManagements(Request request,List<VFAttachmentManagement> attachment);
	
	/**
	 * 保存vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * @param request
	 * @param attachment
	 */
	void saveVFAttachmentManagement(Request request,VFAttachmentManagement attachment);
	
	/**
	 * 上传核查批次附件
	 * @param request  批次
	 * @param file  附件
	 * @param fileName  附件名称
	 * @param fileType  附件类型
	 * @return
	 */
	void upLoadAttachment(Request request,byte[] fileContent,String fileName);
	
	/**
	 * 下载核查批次附件
	 * @param request  批次
	 * @param file  附件
	 * @param fileName  附件名称
	 * @param fileType  附件类型
	 * @return
	 */
	byte[] downLoadAttachment(Request request,String fileName);
	
	/**
	 * 删除核查批次附件
	 * @param request
	 * @param fileName
	 * @param fileId
	 */
	void deleteAttachment(Request request,VFAttachmentManagement attachment);
	/**
	 * 删除核查批次附件
	 * @param request
	 * @param fileName
	 * @param fileId
	 */
	void deleteAttachmentAll(Request request,VFSection section);
	
	/**
	 * 保存模板
	 * @param request
	 * @param section
	 */
	void saveVFAttachmentTemplate(Request request,VFSection section);
	
	/**
	 * 查询在附件管理批次
	 * @param request
	 * @param boo true：已经存在，false：不存在
	 * @return
	 */
	List<VFSection> findAttachmentSection(Request request,Boolean boo);
	
	/**
	 * 查询操作进度
	 * @param request
	 * @param section
	 * @return
	 */
	List<Integer> findVFSectionByProcess(Request request,VFSection section);
	
	/////////////////////////////
	/**
	* 删除 vfSection【核查批次】下的所有 VFBadExg【残次品成品库存】
	* 
	* @param vfSection
	* @return
	*/
	void deleteVFBadExgs(Request request, VFSection vfSection);
	/**
	* 查询 vfSection【核查批次】下的所有 VFBadExg【残次品成品库存】
	* 
	* @param vfSection
	* @return
	*/
	List<VFBadExg> findVFBadExgs(Request request,
	VFSection vfSection);
	/**
	* 查询 vfSection【核查批次】下的所有 VFBadExgConvert【残次品成品折算数据】
	* 
	* @param vfSection
	* @return
	*/
	List<VFBadExgConvert> findVFBadExgConverts(Request request,
	VFSection vfSection, Integer seqNum);
	/**
	* 残次品成品库存 转 报关料件
	* @param vfSection
	* @return
	*/
	List<VFBadExgConvert> resolveVFBadExgs(Request request,
	VFSection vfSection);
	
	/**
	* 删除section【批次】下残次品库存汇总
	* @param request
	* @param section
	*/
	void deleteVFBadStockAnalyse(Request request, VFSection section);
	/**
	* 查找section【批次】下残次品库存汇总
	* @param request
	* @param section
	* @param seqNum 归并序号
	* @return
	*/
	/**
	 * 删除 vfSection【核查批次】下的所有 VFBadExgConvert【残次品成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	void deleteVFBadExgConverts(Request request, VFSection vfSection);
	/**
	 * 【残次品成品属性库存】折算报关数量
	 * @param vfSection
	 * @return
	 */
	String convertVFBadExgConvertsEms(Request request, VFSection vfSection);
	/**
	 * 查找section【批次】下残次品库存汇总
	 * @param request
	 * @param section
	 * @param seqNum 归并序号
	 * @return
	 */
	List<VFBadStockAnalyse> findVFBadAnalyse(Request request, VFSection section, Integer seqNum);
	/**
	 * 汇总section【批次】下所有在制品汇总数据
	 * @param request
	 * @param section
	 * @return
	 */
	List<VFBadStockAnalyse> badVFStockAnalyse(Request request, VFSection section);
	
	/**
	 * 保存成品
	 * @param request
	 * @param baseStockExg
	 */
	public void saveVFBaseStockExg(Request request,VFBaseStockExg baseStockExg);
	
	/**
	 * 保存料件
	 * @param request
	 * @param baseStockImg
	 */
	public void saveVFBaseStockImg(Request request,VFBaseStockImg baseStockImg);
	
	/**
	 * 删除成品
	 * @param request
	 * @param baseStockExg
	 */
	public void deleteVFBaseStockExgs(Request request,List<VFBaseStockExg> baseStockExgs);
	
	/**
	 * 删除料件
	 * @param request
	 * @param baseStockImg
	 */
	public void deleteVFBaseStockImgs(Request request,List<VFBaseStockImg> baseStockImgs);
	/////////////////////////////
}
