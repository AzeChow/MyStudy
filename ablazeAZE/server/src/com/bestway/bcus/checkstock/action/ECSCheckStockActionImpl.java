package com.bestway.bcus.checkstock.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.entity.ECSAnalyType;
import com.bestway.bcus.checkstock.entity.ECSAnalyse;
import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.bcus.checkstock.entity.ECSBadImg;
import com.bestway.bcus.checkstock.entity.ECSBadStockResolve;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountExg;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountExgResolve;
import com.bestway.bcus.checkstock.entity.ECSCustomsCountImg;
import com.bestway.bcus.checkstock.entity.ECSDeclarationCommInfoExg;
import com.bestway.bcus.checkstock.entity.ECSDeclarationCommInfoImg;
import com.bestway.bcus.checkstock.entity.ECSFinishedExg;
import com.bestway.bcus.checkstock.entity.ECSFinishedExgResolve;
import com.bestway.bcus.checkstock.entity.ECSFinishingExg;
import com.bestway.bcus.checkstock.entity.ECSFinishingExgResolve;
import com.bestway.bcus.checkstock.entity.ECSFinishingImg;
import com.bestway.bcus.checkstock.entity.ECSFinishingStockAnalyse;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSSemiFinishedExg;
import com.bestway.bcus.checkstock.entity.ECSSemiFinishedExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStagnateExg;
import com.bestway.bcus.checkstock.entity.ECSStagnateExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStagnateImg;
import com.bestway.bcus.checkstock.entity.ECSStagnateStockAnalyse;
import com.bestway.bcus.checkstock.entity.ECSStockAnalyse;
import com.bestway.bcus.checkstock.entity.ECSStockBuyImg;
import com.bestway.bcus.checkstock.entity.ECSStockExg;
import com.bestway.bcus.checkstock.entity.ECSStockExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutFactoryImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendAnalyse;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExgPt;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExgPtResolve;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendSemiExgPt;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendSemiExgPtResolve;
import com.bestway.bcus.checkstock.entity.ECSStockProcessImg;
import com.bestway.bcus.checkstock.entity.ECSStockSemiExgPtHadStore;
import com.bestway.bcus.checkstock.entity.ECSStockSemiExgPtHadStoreResolve;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExg;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingImg;
import com.bestway.bcus.checkstock.entity.ECSTransferAnalyse;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffExg;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffExgResolve;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffImg;
import com.bestway.bcus.checkstock.entity.temp.MergeTemp;
import com.bestway.bcus.checkstock.logic.ECSCheckStockLogic;
import com.bestway.bcus.checkstock.logic.ECSContractAnalyseLogic;
import com.bestway.bcus.checkstock.logic.ECSFactoryAnalyseLogic;
import com.bestway.bcus.checkstock.logic.ECSTransferAnalyseLogic;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

public class ECSCheckStockActionImpl implements ECSCheckStockAction {
	private ECSContractAnalyseLogic ecsContractAnalyseLogic;
	private ECSFactoryAnalyseLogic ecsFactoryAnalyseLogic;
	private ECSTransferAnalyseLogic ecsTransferAnalyseLogic;
	private ECSCheckStockLogic ecsCheckStockLogic;

	/**
	 * 根据批次判断是否存在数据
	 * @param request
	 * @param section
	 * @param entityClass
	 * @return
	 */
	public boolean isExistsBySection(Request request,ECSSection section,Class<? extends BaseScmEntity> entityClass){
		return ecsCheckStockLogic.isExistsBySection(section,entityClass);
	}
/*******************************************盘点核查批次开始************************************/
	
	/**
	 * 查询所有盘点核查批次
	 * @param request
	 * @return
	 */
	public List<ECSSection> findEcsSection(Request request){
		return ecsCheckStockLogic.findEcsSectionByCancelOwnerHead(null); 
	}
	
	/**
	 * 查询流程
	 * @param request
	 * @return
	 */
	public List<Integer> findPrcess(Request request,ECSSection section){
		return ecsCheckStockLogic.findPrcess(section);
	}
	/**
	 * 根据自用核销表头查询账册盘点核查批次
	 * @param request 
	 * @param head 自用核销表头
	 * @return
	 */
	public List<ECSSection> findEcsSectionByCancelOwnerHead(Request request,CancelOwnerHead head){
		return ecsCheckStockLogic.findEcsSectionByCancelOwnerHead(head);
	}
	
	/**
	 * 保存账册盘点核查批次
	 * @param request
	 * @param section
	 * @return
	 */
	public ECSSection saveEcsSection(Request request,ECSSection section){
		return ecsCheckStockLogic.saveEcsSection(section);
	}
	
	/**
	 * 删除盘点核查批次信息
	 * @param request
	 * @param section 批次信息
	 */
	public void deleteECSSection(Request request, ECSSection section){
		ecsCheckStockLogic.deleteECSSection(section);
	}
	/**
	 * 查询料件情况统计表数据
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 备案序号
	 * @return
	 */
	public List<ECSCustomsCountImg> findECSCustomsImgBySection(Request request,ECSSection section,Integer seqNum){		
		return ecsContractAnalyseLogic.findECSCustomsImgBySection(section,seqNum);
	}
	
	/**
	 * 是否存在section批次下的盘点核查数据
	 * @param request
	 * @param section 批次
	 * @return
	 */
	public boolean isExistEcsCheckDataBySection(Request request,ECSSection section){
		return ecsCheckStockLogic.isExistEcsCheckDataBySection(section);
	}
	
	/**
	 * 是否存在section(批次)的料件明细表数据
	 * @param request
	 * @param section 批次信息
	 */
	public boolean isExistECSDeclarationCommInfoImgBySection(Request request,ECSSection section){
		return ecsContractAnalyseLogic.isExistECSDeclarationCommInfoImgBySection(section);
	}
	/**
	 * 是否存在section(批次)的成品明细表数据
	 * @param request
	 * @param section 批次信息
	 */
	public boolean isExistECSDeclarationCommInfoExgBySection(Request request,ECSSection section){
		return ecsContractAnalyseLogic.isExistECSDeclarationCommInfoExgBySection(section);
	}
	/**
	 * 是否存在section(批次)的料件情况统计表数据
	 * @param request
	 * @param section 批次信息
	 */
	public boolean isExistECSCustomsImgBySection(Request request,ECSSection section){
		return ecsContractAnalyseLogic.isExistECSCustomsImgBySection(section);
	}
	/**
	 * 删除section批次的料件明细数据
	 * @param request
	 * @param section 批次信息
	 */
	public void deleteECSDeclarationCommInfoBySection(Request request,
			ECSSection section) {
		ecsContractAnalyseLogic.deleteECSDeclarationCommInfoImgBySection(section);	
	}
	/**
	 * 删除section批次的成品明细数据
	 * @param request
	 * @param section 批次信息
	 */
	public void deleteECSDeclarationCommExgInfoBySection(Request request,
			ECSSection section) {
		ecsContractAnalyseLogic.deleteECSDeclarationCommInfoExgBySection(section);	
	}
	/**
	 * 删除section批次的料件情况统计表数据
	 * @param request
	 * @param section 批次信息
	 */
	public void deleteECSCustomsImgBySection(Request request, ECSSection section){
		ecsContractAnalyseLogic.deleteECSCustomsImgBySection(section);
	}
	/**
	 * 获取预报核料件报关单数据
	 */
	public List<ECSDeclarationCommInfoImg> finddeclarationCommInfoImg(Request request , ECSSection section){
		return ecsContractAnalyseLogic.finddeclarationCommInfoImg(section);
	}
	/**
	 * 获取预报核成品报关单数据
	 */
	public List<ECSDeclarationCommInfoExg> finddeclarationCommInfoExg(Request request , ECSSection section){
		return ecsContractAnalyseLogic.finddDeclarationCommInfoExg(section);
	}
	/**
	 * 汇总统计section批次的料件情况统计数据
	 * @param request
	 * @param section 批次信息
	 */
	public List<ECSCustomsCountImg> computeECSCustomsImgBySection(Request req,ECSSection section){
		return ecsContractAnalyseLogic.computeECSCustomsImgBySection(section);		
	}
	/*******************************************盘点核查批次结束************************************/
	/**
	 * @param ecsContractAnalyseLogic
	 *            the ecsContractAnalyseLogic to set
	 */
	public void setEcsContractAnalyseLogic(
			ECSContractAnalyseLogic ecsContractAnalyseLogic) {
		this.ecsContractAnalyseLogic = ecsContractAnalyseLogic;
	}

	/**
	 * @param ecsFactoryAnalyseLogic
	 *            the ecsFactoryAnalyseLogic to set
	 */
	public void setEcsFactoryAnalyseLogic(
			ECSFactoryAnalyseLogic ecsFactoryAnalyseLogic) {
		this.ecsFactoryAnalyseLogic = ecsFactoryAnalyseLogic;
	}

	/**
	 * @param ecsTransferAnalyseLogic
	 *            the ecsTransferAnalyseLogic to set
	 */
	public void setEcsTransferAnalyseLogic(
			ECSTransferAnalyseLogic ecsTransferAnalyseLogic) {
		this.ecsTransferAnalyseLogic = ecsTransferAnalyseLogic;
	}

	/**
	 * @param ecsCheckStockLogic
	 *            the ecsCheckStockLogic to set
	 */
	public void setEcsCheckStockLogic(ECSCheckStockLogic ecsCheckStockLogic) {
		this.ecsCheckStockLogic = ecsCheckStockLogic;
	}

	/******************************************* 成品情况统计，折料开始 ************************************/
	/**
	 * 获得并保存成品情况统计表数据
	 * 
	 * @param section
	 *            盘点核查批次
	 * @param cancelTimes
	 *            核销次数
	 * @param beginDate
	 *            报关单申报开始日期
	 * @param endDate
	 *            报关单申报结束日期
	 */
	public void gainECSCustomsCountExg(Request request, ECSSection section) {
		this.ecsContractAnalyseLogic.gainECSCustomsCountExg(section);
	}

	/**
	 * 获得成品统计表
	 * 
	 * @param section
	 * @return
	 */
	public List findECSCustomsCountExgBySection(Request request,ECSSection section,Integer seqNum) {
		return this.ecsContractAnalyseLogic.findECSCustomsCountExgBySection(section,seqNum);
	}

	/**
	 * 删除成品统计表
	 * 
	 * @param section
	 * @return
	 */
	public int delectECSCustomsCountExgBySection(Request request,
			ECSSection section) {
		int num = this.ecsContractAnalyseLogic.delectVFBySection(section,
				ECSAnalyType.ECSCUSTOMS_COUNTEXG);
		return num;
	}

	/**
	 * 折算成品并保存到成品折料统计表
	 * 
	 * @param countExgList
	 *            成品统计表
	 */
	public void convertCustomsCountExgToImg(Request request, ECSSection section) {		
		this.ecsContractAnalyseLogic.convertCustomsCountExgToImg(section);
		
	}
	
	public void findEmsheadh2kBomCountExgBySection(Request request,ECSSection section){
		long t = System.currentTimeMillis();
		List<Object[]> tmpLs = this.ecsContractAnalyseLogic.findEmsheadh2kBomCountExgBySection(section);
		System.out.println("查询耗时:"+((System.currentTimeMillis()-t)/1000d));
	}

	/**
	 * 删除成品折料统计表
	 * 
	 * @param section
	 * @return
	 */
	public int delectECSCustomsCountExgResolveBySection(Request request,
			ECSSection section) {
		int num = this.ecsContractAnalyseLogic.delectVFBySection(section,
				ECSAnalyType.ECSCUSTOMS_COUNTEXG_CONVERT);
		return num;
	}
	/**
	 * 获得成品折料统计表
	 * 
	 * @param section
	 * @return
	 */
	public List findECSCustomsCountExgResolveBySection(Request request, ECSSection section,Integer seqNum){
		return ecsContractAnalyseLogic.findECSCustomsCountExgResolveBySection(section,seqNum);
	}
	/**
	 * 判断该批次的成品折料是否存在
	 */
	public  boolean isExistECSCountExgResolveBySection(Request request, ECSSection section){
		return ecsContractAnalyseLogic.isExistVFBySection(section,ECSAnalyType.ECSCUSTOMS_COUNTEXG_CONVERT);
	}
	/**
	 * 判断该批次的成品统计是否存在
	 */
	public boolean isExistECSCountExgBySection(Request request, ECSSection section){
		return ecsContractAnalyseLogic.isExistVFBySection(section,ECSAnalyType.ECSCUSTOMS_COUNTEXG);
	}

	/**
	 * 根据批次号分页查询成品统计
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
	 * @return
	 */
	public List<Object> findPageECSCountExgBySection(Request request,ECSSection section, int index,
			int length, String property, Object value, boolean isLike){
		return ecsContractAnalyseLogic.findPageECSBySection(section, index, length, property, value, isLike, ECSAnalyType.ECSCUSTOMS_COUNTEXG);
	}
	/**
	 * 根据批次号分页查询成品折料
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
	 * @return
	 */
	public List<Object> findPageECSCountExgResolveBySection(Request request,ECSSection section, int index,
			int length, String property, Object value, boolean isLike){
		List countExgResolveList = ecsContractAnalyseLogic.findPageECSBySection(section, index, 
				length, property, value, isLike, ECSAnalyType.ECSCUSTOMS_COUNTEXG_CONVERT);
		return countExgResolveList;
	}
	
	/******************************************* 成品情况统计，折料结束 ************************************/

	/******************************************* 账册分析开始 ************************************/
	/**
	 * 获得账册分析
	 * @param section
	 * @return
	 */
	public List findECSContractAnalyseBySection(Request request,ECSSection section,Integer seqNum) {
		return ecsContractAnalyseLogic.findECSContractAnalyseBySection(section,seqNum);
	}
	/**
	 * 查询获得料件情况统计表,成品折料料件数据,并保存到账册分析表
	 * 
	 * @param section
	 *            批次
	 */
	public void gainEmsHeadH2kAnalyse(Request request,ECSSection section){
		ecsContractAnalyseLogic.gainEmsHeadH2kAnalyse(section);
	}
	/**
	 * 是否存在该批次的核查分析
	 * @param request
	 * @param section
	 * @return
	 */
	public boolean isExistECSContractAnalyseBySection(Request request, ECSSection section){
		return 	 ecsContractAnalyseLogic.isExistVFBySection(section,ECSAnalyType.ECSCUSTOMS_ANALYSE);
	}
	/**
	 * 删除 该批次的账册分析表
	 * 
	 * @param section
	 * @return
	 */
	public int delectECSContractAnalyseBySection(Request request,
			ECSSection section) {
		int num = this.ecsContractAnalyseLogic.delectVFBySection(section,
				ECSAnalyType.ECSCUSTOMS_ANALYSE);
		return num;
	}
	/******************************************* 账册分析结束 ************************************/

	/******************************************* 查询归并关系 开始 ************************************/
	
	/**
	 * 根据指定料号列表 查询【料件】归并关系(工厂部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergePtPartImg(Request request) {
		return ecsCheckStockLogic.findAllEdiMergePtPartImg();
	}

	/**
	 * 根据指定料号列表 查询【成品】归并关系(工厂部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergePtPartExg(Request request) {
		return ecsCheckStockLogic.findAllEdiMergePtPartExg();
	}

	/**
	 * 根据指定料号列表 查询【料件】归并关系(报关部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergeHsPartImg(Request request) {
		return ecsCheckStockLogic.findAllEdiMergeHsPartImg();
	}

	/**
	 * 根据指定料号列表 查询【成品】归并关系(报关部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergeHsPartExg(Request request) {
		return ecsCheckStockLogic.findAllEdiMergeHsPartExg();
	}

	/******************************************* 查询归并关系 结束 ************************************/

	// ------------------------- 工厂分析接口 开始---------------------- //
	/**
	 * 批量保存【内购库存料件】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockBuyImgs(Request request, ECSSection section,
			List<ECSStockBuyImg> list) {
		ecsFactoryAnalyseLogic.saveECSStockBuyImgs(section, list);
	}

	/**
	 * 批量保存【成品库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockExgs(Request request, ECSSection section,
			List<ECSStockExg> list) {
		ecsFactoryAnalyseLogic.saveECSStockExgs(section, list);
	}

	/**
	 * 批量保存【成品库存折算料件转换报关数据】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockExgResolves(Request request,ECSSection section,
			List<ECSStockExgResolve> list) {
		ecsFactoryAnalyseLogic.saveECSStockExgResolves(section, list);
	}
	
	/**
	 * 批量保存【料件库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockImgs(Request request, ECSSection section,
			List<ECSStockImg> list) {
		ecsFactoryAnalyseLogic.saveECSStockImgs(section, list);
	}

	/**
	 * 批量保存【厂外存放料件】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutFactoryImgs(Request request, ECSSection section,
			List<ECSStockOutFactoryImg> list) {
		ecsFactoryAnalyseLogic.saveECSStockOutFactoryImgs(section, list);
	}

	/**
	 * 批量保存【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutSendExgs(Request request, ECSSection section,
			List<ECSStockOutSendExg> list) {
		ecsFactoryAnalyseLogic.saveECSStockOutSendExgs(section, list);
	}

	/**
	 * 批量保存【在产品库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockProcessImgs(Request request, ECSSection section,
			List<ECSStockProcessImg> list) {
		ecsFactoryAnalyseLogic.saveECSStockProcessImgs(section, list);
	}

	/**
	 * 批量保存【在途库存料件】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockTravelingImgs(Request request, ECSSection section,
			List<ECSStockTravelingImg> list) {
		ecsFactoryAnalyseLogic.saveECSStockTravelingImgs(section, list);
	}

	/**
	 * 批量保存【在途库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockTravelingExgs(Request request, ECSSection section,
			List<ECSStockTravelingExg> list){
		ecsFactoryAnalyseLogic.saveECSStockTravelingExgs(section, list);
	}
	/**
	 * 批量保存【在途成品库存折算料件转换报关数据】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockTravelingExgResolves(Request request, ECSSection section,
			List<ECSStockTravelingExgResolve> list){
		ecsFactoryAnalyseLogic.saveECSStockTravelingExgResolves(section, list);
	}
	/**
	 * 批量保存【残次品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSBadImgs(Request request, ECSSection section,
			List<ECSBadImg> list){
		this.ecsFactoryAnalyseLogic.saveECSBadImgs(section, list);
	}
	/**
	 * 批量保存【残次品库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSFinishedExg(Request request, ECSSection section,
			List<ECSFinishedExg> list){
		this.ecsFactoryAnalyseLogic.saveECSBaseStockExg(section, list);
	}
	/**
	 * 批量保存【在制品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSFinishingImgs(Request request, ECSSection section,
			List<ECSFinishingImg> list){
		this.ecsFactoryAnalyseLogic.saveECSFinishingImgs(section, list);
	}
	/**
	 * 批量保存【呆滞品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStagnateImgs(Request request, ECSSection section,
			List<ECSStagnateImg> list){
		this.ecsFactoryAnalyseLogic.saveECSStagnateImgs(section, list);
	}
	/**
	 * 批量保存【在制品库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSFinishingExgs(Request request, ECSSection section,
			List<ECSFinishingExg> list){
		this.ecsFactoryAnalyseLogic.saveECSBaseStockExg(section, list);
	}
	/**
	 * 批量保存【呆滞品库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStagnateExgs(Request request, ECSSection section,
			List<ECSStagnateExg> list){
		this.ecsFactoryAnalyseLogic.saveECSBaseStockExg(section, list);
	}
	/**
	 * 批量保存【外发库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutSendImgs(Request request, ECSSection section,
			List<ECSStockOutSendImg> list){
		this.ecsFactoryAnalyseLogic.saveECSStockOutSendImgs(section, list);
	}
	
	/**
	 * 批量保存【外发库存半成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutSendSemiExgPts(Request request, ECSSection section,
			List<ECSStockOutSendSemiExgPt> list){
		this.ecsFactoryAnalyseLogic.saveECSBaseStockExg(section, list);
	}
	/**
	 * 批量保存【外发库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutSendExgPts(Request request, ECSSection section,
			List<ECSStockOutSendExgPt> list){
		this.ecsFactoryAnalyseLogic.saveECSBaseStockExg(section, list);
	}
	/**
	 * 批量保存【残次品库存半成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSSemiFinishedExg(Request request, ECSSection section,
			List<ECSSemiFinishedExg> list){
		this.ecsFactoryAnalyseLogic.saveECSBaseStockExg(section, list);
	}
	/**
	 * 半成品库存（已入库）
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSStockSemiExgPtHadStores(Request request, ECSSection section,
			List<ECSStockSemiExgPtHadStore> list){
		this.ecsFactoryAnalyseLogic.saveECSBaseStockExg(section, list);
	}
	/**
	 * 转换 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingImg> convertECSStockTravelingImgs(
			Request request, ECSSection section) {
		return ecsFactoryAnalyseLogic.convertECSStockTravelingImgs(section);
	}

	/**
	 * 转换 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockBuyImg> convertECSStockBuyImgs(Request request,
			ECSSection section) {
		return ecsFactoryAnalyseLogic.convertECSStockBuyImgs(section);
	}

	/**
	 * 转换 指定批次 section 下的【成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockExg> convertECSStockExgs(Request request,
			ECSSection section) {
		return ecsFactoryAnalyseLogic.convertECSStockExgs(section);
	}

	/**
	 * 转换 指定批次 section 下的【在途成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingExg> convertECSStockTravelingExgs(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSStockTravelingExgs(section);
	}
	
	/**
	 * 转换 指定批次 section 下的【料件库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockImg> convertECSStockImgs(Request request,
			ECSSection section) {
		return ecsFactoryAnalyseLogic.convertECSStockImgs(section);
	}

	/**
	 * 转换 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutFactoryImg> convertECSStockOutFactoryImgs(
			Request request, ECSSection section) {
		return ecsFactoryAnalyseLogic.convertECSStockOutFactoryImgs(section);
	}

	/**
	 * 转换 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendExg> convertECSStockOutSendExgs(Request request,
			ECSSection section) {
		return ecsFactoryAnalyseLogic.convertECSStockOutSendExgs(section);
	}

	/**
	 * 转换 指定批次 section 下的【在产品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockProcessImg> convertECSStockProcessImgs(Request request,
			ECSSection section) {
		return ecsFactoryAnalyseLogic.convertECSStockProcessImgs(section);
	}
	/**
	 * 转换 指定批次 section 下的【残次品原材料库存】。
	 * @param request
	 * @param section
	 */
	public List<ECSBadImg> convertECSBadImg(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSBadImg(section);
	}
	
	/**
	 *转换 指定批次 section 下的【残次品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSFinishedExgResolve> convertECSFinishedExgs(Request request,
			ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSFinishedExgs(section);
	}
	/**
	 * 转换 指定批次 section 下的【残次品库存半成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSSemiFinishedExgResolve> convertECSSemiFinishedExgs(Request request,
			ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSSemiFinishedExgs(section);
	}
	/**
	 * 转换 指定批次 section 下的【呆滞品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStagnateImg> convertECSStagnateImg(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSStagnateImg(section);
	}
	/**
	 * 转换 指定批次 section 下的【在制品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSFinishingImg> convertECSFinishingImg(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSFinishingImg(section);
	}
	/**
	 * 转换 指定批次 section 下的【在制品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSFinishingExgResolve> convertECSFinishingExgs(Request request,
			ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSFinishingExgs(section);
	}
	/**
	 * 转换 指定批次 section 下的【呆滞品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStagnateExgResolve> convertECSStagnateExgs(Request request,
			ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSStagnateExgs(section);
	}
	/**
	 * 转换 指定批次 section 下的【外发原材料库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendImg> convertECSStockOutSendImgs(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSStockOutSendImgs(section);
	}
	
	/**
	 *转换 指定批次 section 下的【外发品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendExgPtResolve> convertECSStockOutSendExgPts(Request request,
			   
			ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSStockOutSendExgPts(section);
	}
	/**
	 * 转换 指定批次 section 下的【外发品库存半成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendSemiExgPtResolve> convertECSStockOutSendSemiExgPts(Request request,
			ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSStockOutSendSemiExgPts(section);
	}
	
	/**
	 * 转换 指定批次 section 下的【半成品库存（已入库）加工折料表】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockSemiExgPtHadStoreResolve> convertECSStockSemiExgPtHadStoreResolves(Request request,
			ECSSection section){
		return ecsFactoryAnalyseLogic.convertECSStockSemiExgPtHadStoreResolves(section);
	}
	
	/**
	 * 计算指定批次 section 下的【工厂库存汇总表】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockAnalyse> stockAnalyse(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.stockAnalyse(section);
	}
	
	/**
	 * 分解 指定批次 section 下的【成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockExgResolve> resolveECSStockExgResolves(Request request,
			ECSSection section) {
		return ecsFactoryAnalyseLogic.resolveECSStockExgResolves(section);
	}

	/**
	 * 分解 指定批次 section 下的【在途成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingExgResolve> resolveECSStockTravelingExgResolves(Request request,
			ECSSection section){
		return ecsFactoryAnalyseLogic.resolveECSStockTravelingExgResolves(section);
	}
	
	/**
	 * 分解 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendExgResolve> resolveECSStockOutSendExgResolves(
			Request request, ECSSection section) {
		return ecsFactoryAnalyseLogic
				.resolveECSStockOutSendExgResolves(section);
	}
	/**
	 * 分解 指定批次 section 下的【残次品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */ 
	public List<ECSFinishedExgResolve> resolveECSFinishedExgResolves(Request request,
			ECSSection section) {
		return ecsFactoryAnalyseLogic.resolveECSFinishedExgResolves(section);
	}
	/**
	 * 分解 指定批次 section 下的【残次品库存半成品】转料件。
	 * 
	 * @param section
	 */
	public List<ECSSemiFinishedExgResolve> resolveECSSemiFinishedExgResolves(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.resolveECSSemiFinishedExgResolves(section);
	}
	/**
	 * 分解 指定批次 section 下的【外发库存成品】转料件。
	 * 
	 * @param section
	 */
	public List<ECSStockOutSendExgPtResolve> resolveECSStockOutSendExgPtResolves(Request request, ECSSection section){
			return ecsFactoryAnalyseLogic.resolveECSStockOutSendExgPtResolves(section);
	 }
	 /**
		 * 分解 指定批次 section 下的【外发库存半成品】转料件。
		 * 
		 * @param section
		 */
	public List<ECSStockOutSendSemiExgPtResolve> resolveECSStockOutSendSemiExgPtResolves(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.resolveECSStockOutSendSemiExgPtResolves(section);
	}
	/**
	 * 分解 指定批次 section 下的【在制品库存成品】转料件。
	 * 
	 * @param section
	 */
	public List<ECSFinishingExgResolve> resolveECSFinishingExgResolves(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.resolveECSFinishingExgResolves(section);
	 }
	/**
	 * 分解 指定批次 section 下的【呆滞品库存成品】转料件。
	 * 
	 * @param section
	 */
	public List<ECSStagnateExgResolve> resolveECSStagnateExgResolves(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.resolveECSStagnateExgResolves(section);
	 }
	 /**
		 * 分解 指定批次 section 下的【半成品库存（已入库）加工折料表】转料件。
		 * 
		 * @param section
		 */
	public List<ECSStockSemiExgPtHadStoreResolve> resolveECSStockSemiExgPtHadStoreResolves(Request request, ECSSection section){
		return ecsFactoryAnalyseLogic.resolveECSStockSemiExgPtHadStoreResolves(section);
	}
	/**
	 * 查询 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingImg> findECSStockTravelingImgs(
			Request request, ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockTravelingImgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public List<ECSStockBuyImg> findECSStockBuyImgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockBuyImgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockExg> findECSStockExgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockExgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【在途成品】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingExg> findECSStockTravelingExgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockTravelingExgs(section,seqNum);
	}

	
	/**
	 * 查询 指定批次 section 下的【料件库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockImg> findECSStockImgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockImgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutFactoryImg> findECSStockOutFactoryImgs(
			Request request, ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockOutFactoryImgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendExg> findECSStockOutSendExgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockOutSendExgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【在产品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockProcessImg> findECSStockProcessImgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockProcessImgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【工厂库存汇总表】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockAnalyse> findECSStockAnalyse(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockAnalyse(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【残次品原材料库存】。
	 * @param request
	 * @param section
	 */
	public List<ECSBadImg> findECSBadImg(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSBadImg(section, seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【残次品半成品库存】。
	 * @param request
	 * @param section
	 */
	public List<ECSSemiFinishedExg> findECSSemiFinishedExg(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSSemiFinishedExg(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【残次品半成品折算成单耗】。
	 * @param request
	 * @param section
	 *  @param seqNum 料件备案序号
	 */
	public List<ECSSemiFinishedExgResolve> findECSSemiFinishedExgResolve(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSSemiFinishedExgResolve(section, seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【残次品成品库存】。
	 * @param request
	 * @param section
	 */
	public List<ECSFinishedExg> findECSFinishedExg(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSFinishedExg(section, seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【残次品成品折算成品单耗】。
	 * @param request
	 * @param section
	 */
	public List<ECSFinishedExgResolve> findECSFinishedExgResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSFinishedExgResolve(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【残次品库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSBadStockResolve> findECSBadStockResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSBadStockResolve(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发库存原材料】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendImg> findECSStockOutSendImgs(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockOutSendImgs(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发库存半成品】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendSemiExgPt> findECSStockOutSendSemiExgPts(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockOutSendSemiExgPts(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发库存半成品折算成单耗】。
	 * @param request
	 * @param section
	 *  @param seqNum 料件备案序号
	 */
	public List<ECSStockOutSendSemiExgPtResolve> findECSStockOutSendSemiExgPtResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockOutSendSemiExgPtResolves(section, seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【外发库存成品】。
	 * @param request
	 * @param section
	 * 
	 */
	public List<ECSStockOutSendExgPt> findECSStockOutSendExgPts(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockOutSendExgPts(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发库存成品折算成品单耗】。
	 * @param request
	 * @param section
	 * @param seqNum 料件备案序号
	 */
	public List<ECSStockOutSendExgPtResolve> findECSStockOutSendExgPtResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockOutSendExgPtResolves(section, seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【外发库存库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendAnalyse> findECSStockOutSendAnalyses(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockOutSendAnalyse(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【在制品原材料库存】。
	 * @param request
	 * @param section
	 */
	public List<ECSFinishingImg> findECSFinishingImgs(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSFinishingImgs(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【在制品成品库存】。
	 * @param request
	 * @param section
	 * 
	 */
	public List<ECSFinishingExg> findECSFinishingExgs(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSFinishingExgs(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【呆滞品成品库存】。
	 * @param request
	 * @param section
	 * 
	 */
	public List<ECSStagnateExg> findECSStagnateExgs(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStagnateExgs(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【在制品成品库存折算成品单耗】。】。
	 * @param request
	 * @param section
	 * 
	 */
	public List<ECSFinishingExgResolve> findECSFinishingExgResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSFinishingExgResolves(section, seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【在制品库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSFinishingStockAnalyse> findECSFinishingStockResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSFinishingStockResolves(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【呆滞品原材料库存】。
	 * @param request
	 * @param section
	 */
	public List<ECSStagnateImg> findECSStagnateImgs(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStagnateImgs(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【呆滞品成品库存折算成品单耗】。】。
	 * @param request
	 * @param section
	 * 
	 */
	public List<ECSStagnateExgResolve> findECSStagnateExgResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStagnateExgResolves(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【呆滞品库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSStagnateStockAnalyse> findECSStagnateStockResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStagnateStockResolves(section, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【成品库存折料数据】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockExgResolve> findECSStockExgResolves(Request request,
			ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockExgResolves(section,seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【在途成品库存折算料件】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingExgResolve> findECSStockTravelingExgResolves(Request request, ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseLogic.findECSStockTravelingExgResolves(section,seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发成品库存折料数据】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendExgResolve> findECSStockOutSendExgResolves(
			Request request, ECSSection section,Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockOutSendExgResolves(section,seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【半成品库存（已入库)】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockSemiExgPtHadStore> findECSStockSemiExgPtHadStores(
			Request request, ECSSection section, Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockSemiExgPtHadStores(section,seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【半成品库存（已入库)加工折料表】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockSemiExgPtHadStoreResolve> findECSStockSemiExgPtHadStoreResolves(
			Request request, ECSSection section, Integer seqNum) {
		return ecsFactoryAnalyseLogic.findECSStockSemiExgPtHadStoreResolves(section,seqNum);
	}

	/**
	 * 删除 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSStockTravelingImgs(Request request, ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSStockTravelingImgs(section);
	}

	/**
	 * 删除 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSStockBuyImgs(Request request, ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSStockBuyImgs(section);
	}

	/**
	 * 删除 指定批次 section 下的【成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSStockExgs(Request request, ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSStockExgs(section);
	}

	/**
	 * 删除 指定批次 section 下的【在途成品】。
	 * 
	 * @param section
	 */
	public void deleteECSStockTravelingExgs(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSStockTravelingExgs(section);
	}
	/**
	 * 删除 指定批次 section 下的【料件库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSStockImgs(Request request, ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSStockImgs(section);
	}

	/**
	 * 删除 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutFactoryImgs(Request request, ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSStockOutFactoryImgs(section);
	}

	/**
	 * 删除 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendExgs(Request request, ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSStockOutSendExgs(section);
	}

	/**
	 * 删除 指定批次 section 下的【在产品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSStockProcessImgs(Request request, ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSStockProcessImgs(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【工厂库存汇总表】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockAnalyse(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSStockAnalyse(section);
	}
	/**
	 * 删除 指定批次 section 下的【残次品原材料库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSBadImg(Request request,
			ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSBadImg(section);
	}

	/**
	 * 删除 指定批次 section 下的【残次品半成品库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSSemiFinishedExg(
			Request request, ECSSection section) {
		// 先删除残次品半成品折单耗
		ecsFactoryAnalyseLogic.deleteECSSemiFinishedExgResolve(section);
		// 在删除残次品半成品库存
		ecsFactoryAnalyseLogic.deleteECSSemiFinishedExg(section);
	}

	/**
	 * 删除 指定批次 section 下的【残次品成品库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishedExg(Request request,
			ECSSection section) {
		//先删除残次品成品折单耗
		ecsFactoryAnalyseLogic.deleteECSFinishedExgResolve(section);
		//在删除残次品成品库存
		ecsFactoryAnalyseLogic.deleteECSFinishedExg(section);
	}

	/**
	 * 删除 指定批次 section 下的【残次品库存汇总】。
	 * @param request
	 * @param section
	 */
	public void deleteECSBadStockResolve(Request request,
			ECSSection section) {
		ecsFactoryAnalyseLogic.deleteECSBadStockResolve(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【外发库存原材料】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendImgs(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSStockOutSendImgs(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【外发库存半成品】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendSemiExgPts(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSStockOutSendSemiExgPts(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【外发库存成品】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendExgPts(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSStockOutSendExgPts(section);
	}
	
	
	/**
	 * 删除 指定批次 section 下的【外发库存汇总】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendAnalyses(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSStockOutSendAnalyses(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【在制品原材料库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishingImg(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSFinishingImg(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【在制品成品库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishingExg(Request request, ECSSection section){
		//先删除在制品成品折单耗
		ecsFactoryAnalyseLogic.deleteECSFinishingExgResolve(section);
		 
		ecsFactoryAnalyseLogic.deleteECSFinishingExg(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【在制品库汇总】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishingStockResolve(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSFinishingExg(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【半成品库存（已入库)】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockSemiExgPtHadStores(Request request, ECSSection section){
		//先删除半成品库存折单耗
		ecsFactoryAnalyseLogic.deleteECSStockSemiExgPtHadStoreResolve(section);
		ecsFactoryAnalyseLogic.deleteECSStockSemiExgPtHadStore(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【呆滞品原材料库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStagnateImg(Request request, ECSSection section){
		ecsFactoryAnalyseLogic.deleteECSStagnateImg(section);
	}
	
	/**
	 * 删除 指定批次 section 下的【呆滞品成品库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStagnateExg(Request request, ECSSection section){
		//先删除在制品成品折单耗
		ecsFactoryAnalyseLogic.deleteECSStagnateExgResolve(section);
		 
		ecsFactoryAnalyseLogic.deleteECSStagnateExg(section);
	}
	
	//deleteECSStockAnalyse
	// ------------------------- 工厂分析接口 结束---------------------- //

	// ------------------------- 结转差额接口 开始---------------------- //
	/**
	 * 转换 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSTransferDiffExg> convertECSTransferDiffExgs(Request request,
			ECSSection section) {
		return ecsTransferAnalyseLogic.convertECSTransferDiffExgs(section);
	}

	/**
	 * 转换 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSTransferDiffImg> convertECSTransferDiffImgs(Request request,
			ECSSection section) {
		return ecsTransferAnalyseLogic.convertECSTransferDiffImgs(section);
	}

	/**
	 * 分解 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSTransferDiffExgResolve> resolveECSTransferDiffExgResolves(
			Request request, ECSSection section) {
		return ecsTransferAnalyseLogic
				.resolveECSTransferDiffExgResolves(section);
	}

	/**
	 * 批量保存【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSTransferDiffExgs(Request request, ECSSection section,
			List<ECSTransferDiffExg> list) {
		ecsTransferAnalyseLogic.saveECSTransferDiffExgs(section, list);
	}

	/**
	 * 批量保存【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	public void saveECSTransferDiffImgs(Request request, ECSSection section,
			List<ECSTransferDiffImg> list) {
		ecsTransferAnalyseLogic.saveECSTransferDiffImgs(section, list);
	}

	/**
	 * 查询 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSTransferDiffExg> findECSTransferDiffExgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsTransferAnalyseLogic.findECSTransferDiffExgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSTransferDiffImg> findECSTransferDiffImgs(Request request,
			ECSSection section,Integer seqNum) {
		return ecsTransferAnalyseLogic.findECSTransferDiffImgs(section,seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【结转成品差额折料数据】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSTransferDiffExgResolve> findECSTransferDiffExgResolves(
			Request request, ECSSection section,Integer seqNum) {
		return ecsTransferAnalyseLogic.findECSTransferDiffExgResolves(section,seqNum);
	}
	
	
	/**
	 * 查询 指定批次 section 下的【结转库存分析数据】。
	 * @param request
	 * @param section
	 */
	public List<ECSTransferAnalyse> findECSTransferAnalyses(Request request, ECSSection section,Integer seqNum) {
		return ecsTransferAnalyseLogic.findECSTransferAnalyses(section,seqNum);
	}

	/**
	 * 删除 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSTransferDiffExgs(Request request, ECSSection section) {
		ecsTransferAnalyseLogic.deleteECSTransferDiffExgs(section);
	}

	/**
	 * 删除 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteECSTransferDiffImgs(Request request, ECSSection section) {
		ecsTransferAnalyseLogic.deleteECSTransferDiffImgs(section);
	}
	
	
	/**
	 * 删除 指定批次 section 下的【结转库存分析数据】。
	 * @param request
	 * @param section
	 */
	public void deleteECSTransferAnalyses(Request request, ECSSection section) {
		ecsTransferAnalyseLogic.deleteECSTransferAnalyses(section);
	}
	
	
	/**
	 * 结转库存数据分析
	 * @param request
	 * @param section
	 * @return
	 */
	public List<ECSTransferAnalyse> transferAnalyse(Request request, ECSSection section) {
		return ecsTransferAnalyseLogic.transferAnalyse(section);
	}

	/**
	 * 从深加工结转获取报关级结转料件数据
	 * @param req
	 * @param section
	 * @return
	 */
	public List<ECSTransferDiffExg> generateTransferDiffExgHsFormFpt(Request req,ECSSection section){
		return ecsTransferAnalyseLogic.generateTransferDiffHsExgsFormFpt(section);
	}
	/**
	 * 从深加工结转获取报关级结转场成品数据
	 * @param req
	 * @param section
	 * @return
	 */
	public List<ECSTransferDiffImg> generateTransferDiffImgHsFormFpt(Request req,ECSSection section){
		return ecsTransferAnalyseLogic.generateTransferDiffHsImgFormFpt(section);
	}
	// ------------------------- 结转差额接口 结束---------------------- //
	
	
	
	
	// ------------------------- 短溢分析接口 开始---------------------- //
	/**
	 * 查询 指定批次 section 下的【短溢分析数据】。
	 * @param request
	 * @param section
	 */
	public List<ECSAnalyse> findECSAnalyses(Request request, ECSSection section,Integer seqNum) {
		return ecsCheckStockLogic.findECSAnalyses(section,seqNum);
	}

	/**
	 * 删除 指定批次 section 下的【短溢分析数据】。
	 * @param request
	 * @param section
	 */
	public void deleteECSAnalyses(Request request, ECSSection section) {
		ecsCheckStockLogic.deleteECSAnalyses(section);
	}

	/**
	 * 短溢分析
	 * @param request
	 * @param section
	 * @return
	 */
	public List<ECSAnalyse> ecsAnalyse(Request request, ECSSection section) {
		return ecsCheckStockLogic.ecsAnalyse(section);
	}
	
	/**
	 * 更新短益分析单价
	 * @param a
	 * @return
	 */
	public ECSAnalyse updateAnalysePrice(Request request,ECSAnalyse a){
		return ecsCheckStockLogic.updateAnalysePrice(a);
	}
	
	// ------------------------- 短溢分析接口 结束---------------------- //
	
	
	
	// ------------------------- 其他查询接口 开始---------------------- //
	/**
	 * 查询 所有成品的最大bom版本。
	 * @return
	 */
	public List<Object[]> findMaxVersion(Request request) {
		return ecsCheckStockLogic.findMaxVersion();
	}
	
	
	/**
	 * 查询 所有成品的bom版本。
	 * @return
	 */
	public List<Object[]> findAllVersion(Request request) {
		return ecsCheckStockLogic.findAllVersion();
	}
	@Override
	public List<EmsHeadH2kImg> findEmsHeadH2kImg(Request request,
			ECSSection section) {
		return ecsCheckStockLogic.findEmsHeadH2kImg(section);
	}
	@Override
	public List<EmsHeadH2kExg> findEmsHeadH2kExg(Request request,
			ECSSection section) {
		return ecsCheckStockLogic.findEmsHeadH2kExg(section);
	}
	@Override
	public List<Object[]> findAllEmsVersion(Request request) {
		return ecsCheckStockLogic.findAllEmsVersion();
	}
	@Override
	public List<Object[]> findMaxEmsVersion(Request request) {
		return ecsCheckStockLogic.findMaxEmsVersion();
	}

	/**
	 * 查询所有的报关工厂常用bom版本
	 * @param request
	 * @return
	 */
	public List<MaterialBomVersion> findMaterialBomVersions(Request request) {
		return this.ecsFactoryAnalyseLogic.findMaterialBomVersions();
	}
	/**
	 * 计算残次品汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	public List<ECSBadStockResolve> convertECSBadStockResolves(Request request,ECSSection section){
		return this.ecsFactoryAnalyseLogic.convertECSBadStockResolve(section);
	}
	/**
	 * 计算在制品汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	public List<ECSFinishingStockAnalyse> convertECSFinishingStockResolves(Request request,ECSSection section){
		return this.ecsFactoryAnalyseLogic.convertECSFinishingStockResolve(section);
	}
	/**
	 * 计算呆滞品汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	public List<ECSStagnateStockAnalyse> convertECSStagnateStockResolves(Request request,ECSSection section){
		return this.ecsFactoryAnalyseLogic.convertECSStagnateStockResolve(section);
	}
	
	/**
	 * 计算外发库存汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	public List<ECSStockOutSendAnalyse> convertECSStockOutSendAnalyses(Request request,ECSSection section){
		return this.ecsFactoryAnalyseLogic.convertECSStockOutSendAnalyses(section);
	}
	
	/**
	 * t统计某批次的成品统计折料表的笔数
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public long countECSCustomsCountExgResolveByECSSection(Request request,ECSSection section,String property, 
			Object value, boolean isLike) {
		return this.ecsContractAnalyseLogic.countByECSSection(section,ECSCustomsCountExgResolve.class.getName(),property,value,isLike);
	}
	/**
	 * 统计某批次的成品统计表的笔数
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public long countECSCustomsCountExgByECSSection(Request request,
			ECSSection section, String property, Object value, boolean isLike){
		return this.ecsContractAnalyseLogic.countByECSSection(section,ECSCustomsCountExg.class.getName(),property,value,isLike);

	}

	/**
	 * 上传盘点核资料附件
	 * @param request  批次
	 * @param file  附件
	 * @param fileType  附件类型
	 * @return
	 */
	 
	public void upLoadAttachment(Request request,byte[] fileContent,String fileName){
		ecsCheckStockLogic.upLoadAttachment(fileContent, fileName);
	}
	
	/**
	 * 修改盘点核资料附件
	 * @param request
	 * @param ecsattachmentManagement
	 */
	public void updateAttachment(Request request,ECSAttachmentManagement ecsattachmentManagement){
		ecsCheckStockLogic.upLoadAttachment(ecsattachmentManagement);
	}
	
	
	/**
	 * 查询盘点核资料附件
	 * @param request
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	public List findECSAttachmentManagement(Request request,ECSSection section){
		return ecsCheckStockLogic.findECSAttachmentManagement(section);
	}
	/**
	 * 下载盘点核资料附件
	 * @param request  
	 * @param fileName
	 * @return
	 */
	 
	public byte[] downLoadAttachment(Request request,String fileName){
		return ecsCheckStockLogic.downLoadAttachment(fileName);
	}
	
	/**
	 * 删除盘点核资料附件
	 */
	public void deleteAttachment(Request request,ECSAttachmentManagement attachment){
		String fileName = attachment.getAttachmentName();
		String fileId = attachment.getId();
		ecsCheckStockLogic.deleteAttachment(fileName, fileId);
	}
	
	/**
	 * 删除盘点核资料附件
	 * @param request
	 * @param section
	 */
	public void deleteAttachment(Request request,ECSSection section){
		ecsCheckStockLogic.deleteAttachment(section);
	}
	
	/**
	 * 查询所有盘点核查批次
	 * 
	 * @param request
	 * @param isExist 是否在附件管理中，已经存在
	 * @return
	 */
	public List<ECSSection> findAttachmentSection(Request request,Boolean isExist){
		return ecsCheckStockLogic.findAttachmentSection(isExist);
	}
	
	/**
	 * 保存核查批次模板
	 * @param request
	 * @param section
	 */
	public void saveECSAttachmentTemplate(Request request,ECSSection section){
		ecsCheckStockLogic.saveECSAttachmentTemplate(section);
	}
	
	/**
	 * 保存附件信息
	 * @param request
	 * @param section
	 * @param attachment
	 */
	public void saveECSAttachment(Request request,ECSAttachmentManagement attachment){
		ecsCheckStockLogic.saveECSAttachment(attachment);
	}
	
	/**
	 * 保存附件信息
	 * @param request
	 * @param section
	 * @param attachment
	 */
	public void saveECSAttachment(Request request,List<ECSAttachmentManagement> attachment){
		ecsCheckStockLogic.saveECSAttachment(attachment);
	}
	
	// ------------------------- 其他查询接口 结束---------------------- //
	/**
	 * 查询料件明细表数据
	 */
	public List<ECSDeclarationCommInfoImg> findECSDeclarationImgBySection(
			Request request, ECSSection section, Integer seqNum) {
		return ecsContractAnalyseLogic.findECSDeclarationCommInfoImgBySection(section,seqNum);
	}
	/**
	 * 查询成品明细表数据
	 */
	public List<ECSDeclarationCommInfoExg> findECSDeclarationExgBySection(
			Request request, ECSSection section, Integer seqNum) {
		return ecsContractAnalyseLogic.findECSDeclarationCommInfoExgBySection(section,seqNum);
	}
	
	/**
	 * 根据版本号和备案序号查询成品明细表数据
	 */
	public List<ECSDeclarationCommInfoExg> findECSDeclarationExgBySectionAndVersion(
			Request request, ECSSection section, Integer seqNum,String version) {
		return ecsContractAnalyseLogic.findECSDeclarationCommInfoExgBySectionAndVersion(section,seqNum,version);
	}

	/**
	 * 核销表头
	 */
	public CancelOwnerHead findCancelOwnerHead(Request request,String cancelTimes){
		return ecsContractAnalyseLogic.findCancelOwnerHead(cancelTimes);
	}

}
