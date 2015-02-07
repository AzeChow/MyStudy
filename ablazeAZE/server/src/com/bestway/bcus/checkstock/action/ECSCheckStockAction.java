package com.bestway.bcus.checkstock.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.entity.ECSAnalyse;
import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.bcus.checkstock.entity.ECSBadImg;
import com.bestway.bcus.checkstock.entity.ECSBadStockResolve;
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
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

public interface ECSCheckStockAction {
	/**
	 * 根据批次判断是否存在数据
	 * @param request
	 * @param section
	 * @param entityClass
	 * @return
	 */
	boolean isExistsBySection(Request request,ECSSection section,Class<? extends BaseScmEntity> entityClass);
	
	/******************************************* 盘点核查批次开始 ************************************/	
	/**
	 * 查询所有盘点核查批次
	 * 
	 * @param request
	 * @return
	 */
	List<ECSSection> findEcsSection(Request request);

	/**
	 * 查询流程
	 * @param request
	 * @return
	 */
	List<Integer> findPrcess(Request request,ECSSection section);
	
	/**
	 * 根据自用核销表头查询账册盘点核查批次
	 * 
	 * @param request
	 * @param head
	 *            自用核销表头
	 * @return 当head为时，返回搜友的核查批次
	 */
	List<ECSSection> findEcsSectionByCancelOwnerHead(Request request,
			CancelOwnerHead head);

	/**
	 * 保存账册盘点核查批次
	 * 
	 * @param request
	 * @param section
	 *            盘点核查批次
	 * @return
	 */
	ECSSection saveEcsSection(Request request, ECSSection section);

	/**
	 * 删除盘点核查批次信息
	 * 
	 * @param request
	 * @param section
	 *            批次信息
	 */
	void deleteECSSection(Request request, ECSSection section);

	/**
	 * 查询料件明细表数据
	 */

	List<ECSDeclarationCommInfoImg> findECSDeclarationImgBySection(Request request,ECSSection section, Integer seqNum);

	/**
	 * 查询成品明细表数据
	 */

	List<ECSDeclarationCommInfoExg> findECSDeclarationExgBySection(Request request,ECSSection section, Integer seqNum);
	
	/**
	 * 根据版本号和备案序号查询成品明细表数据
	 */

	List<ECSDeclarationCommInfoExg> findECSDeclarationExgBySectionAndVersion(Request request,ECSSection section, Integer seqNum,String version);
	/**
	 * 查询料件情况统计表数据
	 * 
	 * @param request
	 * @param section 批次信息
	 * @param seqNum 备案序号
	 * @return
	 */
	List<ECSCustomsCountImg> findECSCustomsImgBySection(Request request,ECSSection section,Integer seqNum);
	/**
	 * 是否存在section批次下的盘点核查数据
	 * @param request
	 * @param section 批次
	 * @return
	 */
	boolean isExistEcsCheckDataBySection(Request request,ECSSection section);
	/**
	 * 是否存在section(批次)的料件明细表数据
	 * 
	 * @param request
	 * @param section
	 *            批次信息
	 */
	boolean isExistECSDeclarationCommInfoImgBySection(Request request, ECSSection section);
	/**
	 * 是否存在section(批次)的成品明细表数据
	 * 
	 * @param request
	 * @param section
	 *            批次信息
	 */
	boolean isExistECSDeclarationCommInfoExgBySection(Request request, ECSSection section);
	/**
	 * 是否存在section(批次)的料件情况统计表数据
	 * 
	 * @param request
	 * @param section
	 *            批次信息
	 */
	boolean isExistECSCustomsImgBySection(Request request, ECSSection section);
	/**
	 * 删除section批次的料件情况统计表数据
	 * @param request
	 * @param section 批次信息
	 */
	void deleteECSCustomsImgBySection(Request request, ECSSection section);
	/**
	 * 删除section批次的料件明细表数据
	 * @param request
	 * @param section 批次信息
	 */
	void deleteECSDeclarationCommInfoBySection(Request request,
			ECSSection section);
	/**
	 * 删除section批次的成品明细表数据
	 * @param request
	 * @param section 批次信息
	 */
	void deleteECSDeclarationCommExgInfoBySection(Request request,
			ECSSection section);
	/**
	 * 汇总统计section批次的料件情况统计数据
	 * @param request
	 * @param section 批次信息
	 */
	List<ECSCustomsCountImg> computeECSCustomsImgBySection(Request req,ECSSection section);
	
	/**
	 * 获取预报核料件报关单数据
	 */
	List<ECSDeclarationCommInfoImg> finddeclarationCommInfoImg(Request request,ECSSection section);
	/**
	 * 获取预报核成品报关单数据
	 */
	List<ECSDeclarationCommInfoExg> finddeclarationCommInfoExg(Request request,ECSSection section);
	/******************************************* 盘点核查批次结束 ************************************/

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
	void gainECSCustomsCountExg(Request request, ECSSection section);

	/**
	 * 获得成品统计表
	 * 
	 * @param section
	 * @param seqNum 备案序号
	 * @return
	 */
	List findECSCustomsCountExgBySection(Request request, ECSSection section,Integer seqNum);

	/**
	 * 删除成品统计表
	 * 
	 * @param section
	 * @return
	 */
	int delectECSCustomsCountExgBySection(Request request, ECSSection section);

	/**
	 * 折算成品并保存到成品折料统计表
	 * 
	 * @param countExgList
	 *            成品统计表
	 */
	void convertCustomsCountExgToImg(Request request, ECSSection section);
	
	public void findEmsheadh2kBomCountExgBySection(Request request,ECSSection section);

	/**
	 * 删除成品折料统计表
	 * 
	 * @param section
	 * @return
	 */
	int delectECSCustomsCountExgResolveBySection(Request request,
			ECSSection section);
	/**
	 * 获得成品折料统计表
	 * 
	 * @param section
	 * @return
	 */
	List findECSCustomsCountExgResolveBySection(Request request, ECSSection section,Integer seqNum);
	
	boolean isExistECSCountExgResolveBySection(Request request, ECSSection section);
	
	boolean isExistECSCountExgBySection(Request request, ECSSection section);
	
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
			int length, String property, Object value, boolean isLike);
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
			int length, String property, Object value, boolean isLike);

	/******************************************* 成品情况统计，折料结束 ************************************/

	/******************************************* 账册分析开始 ************************************/
	/**
	 * 获得账册分析
	 * @param section
	 * @return
	 */
	public List findECSContractAnalyseBySection(Request request,ECSSection section,Integer seqNum) ;
	/**
	 * 查询获得料件情况统计表,成品折料料件数据,并保存到账册分析表
	 * 
	 * @param section
	 *            批次
	 */
	public void gainEmsHeadH2kAnalyse(Request request,ECSSection section);
	/**
	 * 是否存在该批次的账册分析
	 * @param section
	 * @return
	 */
	boolean isExistECSContractAnalyseBySection(Request request, ECSSection section);
	/**
	 * 删除 该批次的账册分析表
	 * 
	 * @param section
	 * @return
	 */
	public int delectECSContractAnalyseBySection(Request request,
			ECSSection section) ;

	/******************************************* 账册分析结束 ************************************/

	/******************************************* 查询归并关系 开始 ************************************/	
	/**
	 * 根据指定料号列表 查询【料件】归并关系(工厂部分信息)。
	 * 
	 * @param request
	 * @return
	 */
	List<MergeTemp> findAllEdiMergePtPartImg(Request request);
	

	/**
	 * 根据指定料号列表 查询【成品】归并关系(工厂部分信息)。
	 * 
	 * @param request
	 * @return
	 */
	List<MergeTemp> findAllEdiMergePtPartExg(Request request);

	/**
	 * 根据指定料号列表 查询【料件】归并关系(报关部分信息)。
	 * 
	 * @param request
	 * @return
	 */
	List<MergeTemp> findAllEdiMergeHsPartImg(Request request);

	/**
	 * 根据指定料号列表 查询【成品】归并关系(报关部分信息)。
	 * 
	 * @param request
	 * @return
	 */
	List<MergeTemp> findAllEdiMergeHsPartExg(Request request);

	/******************************************* 查询归并关系结束 ************************************/
	
	// ------------------------- 工厂分析接口 开始---------------------- //
	/**
	 * 批量保存【内购库存料件】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockBuyImgs(Request request, ECSSection section,
			List<ECSStockBuyImg> list);

	/**
	 * 批量保存【成品库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockExgs(Request request, ECSSection section,
			List<ECSStockExg> list);
	/**
	 * 批量保存【成品库存折算料件转换报关数据】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockExgResolves(Request request,ECSSection section,
			List<ECSStockExgResolve> list) ;

	/**
	 * 批量保存【料件库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockImgs(Request request, ECSSection section,
			List<ECSStockImg> list);

	/**
	 * 批量保存【厂外存放料件】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockOutFactoryImgs(Request request, ECSSection section,
			List<ECSStockOutFactoryImg> list);

	/**
	 * 批量保存【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockOutSendExgs(Request request, ECSSection section,
			List<ECSStockOutSendExg> list);

	/**
	 * 批量保存【在产品库存】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockProcessImgs(Request request, ECSSection section,
			List<ECSStockProcessImg> list);

	/**
	 * 批量保存【在途库存料件】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockTravelingImgs(Request request, ECSSection section,
			List<ECSStockTravelingImg> list);
	/**
	 * 批量保存【在途库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockTravelingExgs(Request request, ECSSection section,
			List<ECSStockTravelingExg> list);
	/**
	 * 批量保存【在途成品库存折算料件转换报关数据】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockTravelingExgResolves(Request request, ECSSection section,
			List<ECSStockTravelingExgResolve> list);
	
	/**
	 * 批量保存【残次品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSBadImgs(Request request, ECSSection section,
			List<ECSBadImg> list);
	
	/**
	 * 批量保存【残次品库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSFinishedExg(Request request, ECSSection section,
			List<ECSFinishedExg> list);
	
	/**
	 * 批量保存【残次品库存半成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSSemiFinishedExg(Request request, ECSSection section,
			List<ECSSemiFinishedExg> list);
	/**
	 * 批量保存【在制品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSFinishingImgs(Request request, ECSSection section,
			List<ECSFinishingImg> list);
	/**
	 * 批量保存【呆滞品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStagnateImgs(Request request, ECSSection section,
			List<ECSStagnateImg> list);
	/**
	 * 批量保存【在制品库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSFinishingExgs(Request request, ECSSection section,
			List<ECSFinishingExg> list);
	
	/**
	 * 批量保存【呆滞品库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStagnateExgs(Request request, ECSSection section,
			List<ECSStagnateExg> list);
	
	/**
	 * 批量保存【外发库存原材料】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockOutSendImgs(Request request, ECSSection section,
			List<ECSStockOutSendImg> list);
	
	/**
	 * 批量保存【外发库存半成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockOutSendSemiExgPts(Request request, ECSSection section,
			List<ECSStockOutSendSemiExgPt> list);
	
	/**
	 * 批量保存【外发库存成品】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockOutSendExgPts(Request request, ECSSection section,
			List<ECSStockOutSendExgPt> list);
	
	/**
	 * 半成品库存（已入库）
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSStockSemiExgPtHadStores(Request request, ECSSection section,
			List<ECSStockSemiExgPtHadStore> list);
	/**
	 * 转换 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockTravelingImg> convertECSStockTravelingImgs(Request request,
			ECSSection section);

	/**
	 * 转换 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockBuyImg> convertECSStockBuyImgs(Request request,
			ECSSection section);

	/**
	 * 转换 指定批次 section 下的【成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockExg> convertECSStockExgs(Request request, ECSSection section);
	/**
	 * 转换 指定批次 section 下的【在途成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockTravelingExg> convertECSStockTravelingExgs(Request request, ECSSection section);

	/**
	 * 转换 指定批次 section 下的【料件库存】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockImg> convertECSStockImgs(Request request, ECSSection section);

	/**
	 * 转换 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockOutFactoryImg> convertECSStockOutFactoryImgs(Request request,
			ECSSection section);

	/**
	 * 转换 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendExg> convertECSStockOutSendExgs(Request request,
			ECSSection section);

	/**
	 * 转换 指定批次 section 下的【在产品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockProcessImg> convertECSStockProcessImgs(Request request,
			ECSSection section);
	
	/**
	 * 转换 指定批次 section 下的【残次品原材料库存】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSBadImg> convertECSBadImg(Request request, ECSSection section);

	/**
	 * 转换 指定批次 section 下的【残次品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSFinishedExgResolve> convertECSFinishedExgs(Request request,
			ECSSection section);
	/**
	 * 转换 指定批次 section 下的【残次品库存半成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSSemiFinishedExgResolve> convertECSSemiFinishedExgs(Request request,
			ECSSection section);
	
	/**
	 * 转换 指定批次 section 下的【在制品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSFinishingImg> convertECSFinishingImg(Request request, ECSSection section);
	
	/**
	 * 转换 指定批次 section 下的【呆滞品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStagnateImg> convertECSStagnateImg(Request request, ECSSection section);
	
	/**
	 * 转换 指定批次 section 下的【在制品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSFinishingExgResolve> convertECSFinishingExgs(Request request,
			ECSSection section);
	
	/**
	 * 转换 指定批次 section 下的【呆滞品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStagnateExgResolve> convertECSStagnateExgs(Request request,
			ECSSection section);
	
	/**
	 * 转换 指定批次 section 下的【外发原材料库存】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendImg> convertECSStockOutSendImgs(Request request, ECSSection section);

	/**
	 * 转换 指定批次 section 下的【外发库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendExgPtResolve> convertECSStockOutSendExgPts(Request request,
			ECSSection section);
	/**
	 * 转换 指定批次 section 下的【外发库存半成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendSemiExgPtResolve> convertECSStockOutSendSemiExgPts(Request request,
			ECSSection section);
	
	/**
	 * 转换 指定批次 section 下的【半成品库存（已入库）加工折料表】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockSemiExgPtHadStoreResolve> convertECSStockSemiExgPtHadStoreResolves(Request request,
			ECSSection section);
	
	/**
	 * 分解 指定批次 section 下的【成品库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStockExgResolve> resolveECSStockExgResolves(Request request,
			ECSSection section);
	
	/**
	 * 分解 指定批次 section 下的【在途成品】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockTravelingExgResolve> resolveECSStockTravelingExgResolves(Request request,
			ECSSection section);

	/**
	 * 分解 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendExgResolve> resolveECSStockOutSendExgResolves(
			Request request, ECSSection section);
	
	/**
	 * 查询 指定批次 section 下的【在途库存料件】。
	 * @param request
	 * @param section
	 */
	List<ECSStockTravelingImg> findECSStockTravelingImgs(Request request, 
			ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【内购库存料件】。
	 * @param request
	 * @param section
	 * @param list
	 */
	List<ECSStockBuyImg> findECSStockBuyImgs(Request request, ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【成品库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStockExg> findECSStockExgs(Request request, ECSSection section,Integer seqNum);
	/**
	 * 查询 指定批次 section 下的【在途成品】。
	 * @param request
	 * @param section
	 */
	List<ECSStockTravelingExg> findECSStockTravelingExgs(Request request, ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【料件库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStockImg> findECSStockImgs(Request request, ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【厂外存放料件】。
	 * @param request
	 * @param section
	 */
	List<ECSStockOutFactoryImg> findECSStockOutFactoryImgs(
			Request request, ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【外发成品库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendExg> findECSStockOutSendExgs(Request request, ECSSection section,Integer seqNum);
	/**
	 * 查询 指定批次 section 下的【在产品库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStockProcessImg> findECSStockProcessImgs(Request request, ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【成品库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStockExgResolve> findECSStockExgResolves(Request request, ECSSection section,Integer seqNum);
	/**
	 * 查询 指定批次 section 下的【在途成品库存折算料件】。
	 * @param request
	 * @param section
	 */
	List<ECSStockTravelingExgResolve> findECSStockTravelingExgResolves(Request request, ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【外发成品库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendExgResolve> findECSStockOutSendExgResolves(
			Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【工厂库存汇总表】。
	 * @param request
	 * @param section
	 */
	List<ECSStockAnalyse> findECSStockAnalyse(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【残次品原材料库存】。
	 * @param request
	 * @param section
	 */
	List<ECSBadImg> findECSBadImg(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【残次品半成品库存】。
	 * @param request
	 * @param section
	 */
	List<ECSSemiFinishedExg> findECSSemiFinishedExg(Request request, ECSSection section,Integer seqNum);
	/**
	 * 查询 指定批次 section 下的【残次品库存半成品折算成单耗】。
	 * @param request
	 * @param section
	 *  @param seqNum 料件备案序号
	 */
	List<ECSSemiFinishedExgResolve> findECSSemiFinishedExgResolve(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【残次品成品库存】。
	 * @param request
	 * @param section
	 * 
	 */
	List<ECSFinishedExg> findECSFinishedExg(Request request, ECSSection section,Integer seqNum);
	/**
	 * 查询 指定批次 section 下的【残次品库存成品折算成品单耗】。
	 * @param request
	 * @param section
	 * @param seqNum 料件备案序号
	 */
	List<ECSFinishedExgResolve> findECSFinishedExgResolves(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【残次品库存汇总】。
	 * @param request
	 * @param section
	 */
	List<ECSBadStockResolve> findECSBadStockResolves(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【外发库存原材料】。
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendImg> findECSStockOutSendImgs(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【外发库存半成品】。
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendSemiExgPt> findECSStockOutSendSemiExgPts(Request request, ECSSection section,Integer seqNum);
	/**
	 * 查询 指定批次 section 下的【外发库存半成品折算成单耗】。
	 * @param request
	 * @param section
	 *  @param seqNum 料件备案序号
	 */
	List<ECSStockOutSendSemiExgPtResolve> findECSStockOutSendSemiExgPtResolves(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【外发库存成品】。
	 * @param request
	 * @param section
	 * 
	 */
	List<ECSStockOutSendExgPt> findECSStockOutSendExgPts(Request request, ECSSection section,Integer seqNum);
	/**
	 * 查询 指定批次 section 下的【外发库存成品折算成品单耗】。
	 * @param request
	 * @param section
	 * @param seqNum 料件备案序号
	 */
	List<ECSStockOutSendExgPtResolve> findECSStockOutSendExgPtResolves(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【外发库存库存汇总】。
	 * @param request
	 * @param section
	 */
	List<ECSStockOutSendAnalyse> findECSStockOutSendAnalyses(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【在制品原材料库存】。
	 * @param request
	 * @param section
	 */
	List<ECSFinishingImg> findECSFinishingImgs(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【在制品成品库存】。
	 * @param request
	 * @param section
	 * 
	 */
	List<ECSFinishingExg> findECSFinishingExgs(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【在制品成品库存折算成品单耗】。】。
	 * @param request
	 * @param section
	 * 
	 */
	List<ECSFinishingExgResolve> findECSFinishingExgResolves(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【在制品库存汇总】。
	 * @param request
	 * @param section
	 */
	List<ECSFinishingStockAnalyse> findECSFinishingStockResolves(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【呆滞品原材料库存】。
	 * @param request
	 * @param section
	 */
	List<ECSStagnateImg> findECSStagnateImgs(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【呆滞品成品库存】。
	 * @param request
	 * @param section
	 * 
	 */
	List<ECSStagnateExg> findECSStagnateExgs(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【呆滞品成品库存折算成品单耗】。】。
	 * @param request
	 * @param section
	 * 
	 */
	List<ECSStagnateExgResolve> findECSStagnateExgResolves(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【呆滞品库存汇总】。
	 * @param request
	 * @param section
	 */
	List<ECSStagnateStockAnalyse> findECSStagnateStockResolves(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 查询 指定批次 section 下的【半成品库存（已入库)】。
	 * @param request
	 * @param section
	 */
	List<ECSStockSemiExgPtHadStore> findECSStockSemiExgPtHadStores(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【半成品库存（已入库)加工折料表】。
	 * @param request
	 * @param section
	 */
	List<ECSStockSemiExgPtHadStoreResolve> findECSStockSemiExgPtHadStoreResolves(Request request, ECSSection section,Integer seqNum);
	
	
	/**
	 * 计算 指定批次 section 下的【工厂库存汇总表】。
	 * @param request
	 * @param section
	 */
	List<ECSStockAnalyse> stockAnalyse(Request request, ECSSection section);
	
	/**
	 * 查询 指定批次 section 下的【工厂库存汇总表】。
	 * @param request
	 * @param section
	 */
	void deleteECSStockAnalyse(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param section
	 */
	void deleteECSStockTravelingImgs(Request request, ECSSection section);

	/**
	 * 删除 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param section
	 */
	void deleteECSStockBuyImgs(Request request, ECSSection section);

	/**
	 * 删除 指定批次 section 下的【成品库存】。
	 * 
	 * @param section
	 */
	void deleteECSStockExgs(Request request, ECSSection section);
	/**
	 * 删除 指定批次 section 下的【在途成品】。
	 * 
	 * @param section
	 */
	void deleteECSStockTravelingExgs(Request request, ECSSection section);

	/**
	 * 删除 指定批次 section 下的【料件库存】。
	 * 
	 * @param section
	 */
	void deleteECSStockImgs(Request request, ECSSection section);

	/**
	 * 删除 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param section
	 */
	void deleteECSStockOutFactoryImgs(Request request, ECSSection section);

	/**
	 * 删除 指定批次 section 下的【外发成品库存折料数据】。
	 * 
	 * @param section
	 */
	void deleteECSStockOutSendExgs(Request request, ECSSection section);

	/**
	 * 删除 指定批次 section 下的【在产品库存折料数据】。
	 * 
	 * @param section
	 */
	void deleteECSStockProcessImgs(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【残次品原材料库存】。
	 * @param request
	 * @param section
	 */
	void deleteECSBadImg(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【残次品半成品库存】。
	 * @param request
	 * @param section
	 */
	void deleteECSSemiFinishedExg(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【残次品成品库存】。
	 * @param request
	 * @param section
	 */
	void deleteECSFinishedExg(Request request, ECSSection section);
	
	
	/**
	 * 删除 指定批次 section 下的【残次品库存汇总】。
	 * @param request
	 * @param section
	 */
	void deleteECSBadStockResolve(Request request, ECSSection section);
	/**
	 * 删除 指定批次 section 下的【外发库存原材料】。
	 * @param request
	 * @param section
	 */
	void deleteECSStockOutSendImgs(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【外发库存半成品】。
	 * @param request
	 * @param section
	 */
	void deleteECSStockOutSendSemiExgPts(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【外发库存成品】。
	 * @param request
	 * @param section
	 */
	void deleteECSStockOutSendExgPts(Request request, ECSSection section);
	
	
	/**
	 * 删除 指定批次 section 下的【外发库存汇总】。
	 * @param request
	 * @param section
	 */
	void deleteECSStockOutSendAnalyses(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【在制品原材料库存】。
	 * @param request
	 * @param section
	 */
	void deleteECSFinishingImg(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【在制品成品库存】。
	 * @param request
	 * @param section
	 */
	void deleteECSFinishingExg(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【在制品库汇总】。
	 * @param request
	 * @param section
	 */
	void deleteECSFinishingStockResolve(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【半成品库存（已入库)】。
	 * @param request
	 * @param section
	 */
	void deleteECSStockSemiExgPtHadStores(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【呆滞品原材料库存】。
	 * @param request
	 * @param section
	 */
	void deleteECSStagnateImg(Request request, ECSSection section);
	
	/**
	 * 删除 指定批次 section 下的【在制品成品库存】。
	 * @param request
	 * @param section
	 */
	void deleteECSStagnateExg(Request request, ECSSection section);

	// ------------------------- 工厂分析接口 结束---------------------- //
	
	
	// ------------------------- 结转差额接口 开始---------------------- //
	/**
	 * 转换 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSTransferDiffExg> convertECSTransferDiffExgs(Request request,
			ECSSection section);

	/**
	 * 转换 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSTransferDiffImg> convertECSTransferDiffImgs(Request request,
			ECSSection section);

	/**
	 * 分解 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSTransferDiffExgResolve> resolveECSTransferDiffExgResolves(
			Request request, ECSSection section);
	/**
	 * 分解 指定批次 section 下的【残次品库存成品】转料件。
	 * 
	 * @param section
	 */
	 List<ECSFinishedExgResolve> resolveECSFinishedExgResolves(Request request, ECSSection section);
	 /**
		 * 分解 指定批次 section 下的【残次品库存半成品】转料件。
		 * 
		 * @param section
		 */
	List<ECSSemiFinishedExgResolve> resolveECSSemiFinishedExgResolves(Request request, ECSSection section);
	
	/**
	 * 分解 指定批次 section 下的【外发库存成品】转料件。
	 * 
	 * @param section
	 */
	 List<ECSStockOutSendExgPtResolve> resolveECSStockOutSendExgPtResolves(Request request, ECSSection section);
	 /**
		 * 分解 指定批次 section 下的【外发库存半成品】转料件。
		 * 
		 * @param section
		 */
	List<ECSStockOutSendSemiExgPtResolve> resolveECSStockOutSendSemiExgPtResolves(Request request, ECSSection section);
	
	/**
	 * 分解 指定批次 section 下的【在制品库存成品】转料件。
	 * 
	 * @param section
	 */
	 List<ECSFinishingExgResolve> resolveECSFinishingExgResolves(Request request, ECSSection section);
	 
	/**
	 * 分解 指定批次 section 下的【在制品库存成品】转料件。
	 * 
	 * @param section
	*/
	 List<ECSStagnateExgResolve> resolveECSStagnateExgResolves(Request request, ECSSection section);
	 
	 /**
		 * 分解 指定批次 section 下的【半成品库存（已入库）加工折料表】转料件。
		 * 
		 * @param section
		 */
	List<ECSStockSemiExgPtHadStoreResolve> resolveECSStockSemiExgPtHadStoreResolves(Request request, ECSSection section);
	
	/**
	 * 批量保存【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSTransferDiffExgs(Request request, ECSSection section,
			List<ECSTransferDiffExg> list);

	/**
	 * 批量保存【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 * @param list
	 */
	void saveECSTransferDiffImgs(Request request, ECSSection section,
			List<ECSTransferDiffImg> list);

	/**
	 * 查询 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSTransferDiffExg> findECSTransferDiffExgs(Request request,
			ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSTransferDiffImg> findECSTransferDiffImgs(Request request,
			ECSSection section,Integer seqNum);

	/**
	 * 查询 指定批次 section 下的【结转成品差额折料数据】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSTransferDiffExgResolve> findECSTransferDiffExgResolves(
			Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 查询 指定批次 section 下的【结转库存分析数据】。
	 * @param request
	 * @param section
	 */
	List<ECSTransferAnalyse> findECSTransferAnalyses(Request request, ECSSection section,Integer seqNum);
	
	/**
	 * 删除 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param request
	 * @param section
	 */
	void deleteECSTransferDiffExgs(Request request, ECSSection section);

	/**
	 * 删除 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param request
	 * @param section
	 */
	void deleteECSTransferDiffImgs(Request request, ECSSection section);
	
	
	/**
	 * 删除 指定批次 section 下的【结转库存分析数据】。
	 * @param request
	 * @param section
	 */
	void deleteECSTransferAnalyses(Request request, ECSSection section);
	
	
	/**
	 * 结转库存数据分析
	 * @param request
	 * @param section
	 * @return
	 */
	List<ECSTransferAnalyse> transferAnalyse(Request request, ECSSection section);
	
	/**
	 * 从深加工结转获取报关级结转料件数据
	 * @param req
	 * @param section
	 * @return
	 */
	List<ECSTransferDiffExg> generateTransferDiffExgHsFormFpt(Request req,ECSSection section);
	/**
	 * 从深加工结转获取报关级结转场成品数据
	 * @param req
	 * @param section
	 * @return
	 */
	List<ECSTransferDiffImg> generateTransferDiffImgHsFormFpt(Request req,ECSSection section);
	
	// ------------------------- 结转差额接口 结束---------------------- //
	
	
	// ------------------------- 短溢分析接口 开始---------------------- //
	/**
	 * 查询 指定批次 section 下的【短溢分析数据】。
	 * 
	 * @param request
	 * @param section
	 */
	List<ECSAnalyse> findECSAnalyses(Request request, ECSSection section,Integer seqNum);

	/**
	 * 删除 指定批次 section 下的【短溢分析数据】。
	 * 
	 * @param request
	 * @param section
	 */
	void deleteECSAnalyses(Request request, ECSSection section);

	/**
	 * 短溢分析
	 * 
	 * @param request
	 * @param section
	 * @return
	 */
	List<ECSAnalyse> ecsAnalyse(Request request, ECSSection section);
	
	/**
	 * 更新短益分析单价
	 * @param a
	 * @return
	 */
	ECSAnalyse updateAnalysePrice(Request request,ECSAnalyse a);

	// ------------------------- 短溢分析接口 结束---------------------- //
	
	// ------------------------- 其他查询接口 开始---------------------- //
	/**
	 * 查询 所有成品的最大bom版本。
	 * @return
	 */
	List<Object[]> findMaxVersion(Request request);
	
	/**
	 * 查询 所有成品的bom版本。
	 * @return
	 */
	List<Object[]> findAllVersion(Request request);
	
	/**
	 * 查询电子帐册料件
	 * 
	 * @param request
	 * @param section
	 *            核查批次
	 * @return
	 */
	List<EmsHeadH2kImg> findEmsHeadH2kImg(Request request, ECSSection section);
	
	/**
	 * 查询电子帐册成品
	 * 
	 * @param request
	 * @param section
	 *            核查批次
	 * @return
	 */
	List<EmsHeadH2kExg> findEmsHeadH2kExg(Request request, ECSSection section);
	
	/**
	 * 查询 所有电子账册成品的bom版本。
	 * 
	 * @param request
	 * @return
	 */
	List<Object[]> findAllEmsVersion(Request request);
	
	/**
	 * 查询 所有电子账册成品的最大bom版本。
	 * 
	 * @param request
	 * @return
	 */
	List<Object[]> findMaxEmsVersion(Request request);
	// ------------------------- 其他查询接口 结束---------------------- //
		
	/**
	 * 查询所有的报关工厂常用bom版本
	 * @param request
	 * @return
	 */
	public List<MaterialBomVersion> findMaterialBomVersions(Request request);
	/**
	 * 计算残次品汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	List<ECSBadStockResolve> convertECSBadStockResolves(Request request,ECSSection section);
	/**
	 * 计算在制品汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	List<ECSFinishingStockAnalyse> convertECSFinishingStockResolves(Request request,ECSSection section);
	/**
	 * 计算呆滞品汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	List<ECSStagnateStockAnalyse> convertECSStagnateStockResolves(Request request,ECSSection section);
	/**
	 * 计算外发库存汇总表
	 * @param request
	 * @param section
	 * @return
	 */
	List<ECSStockOutSendAnalyse> convertECSStockOutSendAnalyses(Request request,ECSSection section);
	
	/**
	 *  统计某批次的成品统计折料表的笔数
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	 long countECSCustomsCountExgResolveByECSSection(Request request,ECSSection section,String property, Object value, boolean isLike);
	 
	/**
	 * 统计某批次的成品统计表的笔数
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	long countECSCustomsCountExgByECSSection(Request request,
			ECSSection section, String property, Object value, boolean isLike);
	
	/**
	 * 上传盘点核资料附件
	 * @param request  批次
	 * @param file  附件
	 * @param fileName  附件名称
	 * @param fileType  附件类型
	 * @return
	 */
	void upLoadAttachment(Request request,byte[] fileContent,String fileName);
	
	/**
	 * 修改盘点核资料附件
	 * @param request
	 * @param ecsattachmentManagement
	 */
	void updateAttachment(Request request,ECSAttachmentManagement ecsattachmentManagement);
	
	/**
	 * 查询盘点核资料附件
	 * @param request
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	List findECSAttachmentManagement(Request request,ECSSection section);
	
	/**
	 * 下载盘点核资料附件
	 * @param request  批次
	 * @param file  附件
	 * @param fileName  附件名称
	 * @param fileType  附件类型
	 * @return
	 */
	byte[] downLoadAttachment(Request request,String fileName);
	
	/**
	 * 删除盘点核资料附件
	 * @param request
	 * @param fileName
	 * @param fileId
	 */
	void deleteAttachment(Request request,ECSAttachmentManagement attachment);
	
	/**
	 * 删除盘点核资料附件
	 * @param request
	 * @param section
	 */
	void deleteAttachment(Request request,ECSSection section);
	
	/**
	 * 查询所有盘点核查批次
	 * 
	 * @param request
	 * @param isExist 是否在附件管理中，已经存在
	 * @return
	 */
	List<ECSSection> findAttachmentSection(Request request,Boolean isExist);

	/**
	 * 保存核查批次模板
	 * @param request
	 * @param section
	 */
	void saveECSAttachmentTemplate(Request request,ECSSection section);
	
	/**
	 * 保存附件信息
	 * @param request
	 * @param section
	 * @param attachment
	 */
	void saveECSAttachment(Request request,ECSAttachmentManagement attachment);
	
	/**
	 * 保存附件信息
	 * @param request
	 * @param section
	 * @param attachment
	 */
	void saveECSAttachment(Request request,List<ECSAttachmentManagement> attachment);

	/**
	 * 核销表头
	 */
	CancelOwnerHead findCancelOwnerHead(Request request,String cancelTimes);
}