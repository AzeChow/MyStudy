/*
 * Created on 2004-10-12
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.action;

import java.awt.Component;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.entity.CustomInnerMergeCondition;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.InnerMergeDataOrder;
import com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.bcus.innermerge.entity.TempAutoInnerMergeParam;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates 普通基础代码操作接口
 */
public interface CommonBaseCodeAction {

	List findMateriel(Request request);

	List findInnerMergeDataByType10(Request request, String type,
			EmsEdiTrHead emsEdiTrHead, EmsEdiMergerHead emsEdiMergerHead,
			String helfType);

	List findEmsEdiMergerImgAfter(Request request, EmsEdiMergerHead emsMerger,
			Integer seqNum);

	// 分页
	List findInnerMergeDataAfterImgByType10(Request request, String type,
			Integer seqNum, EmsEdiMergerHead emsEdiMergerHead, int index,
			int length, String property, Object value, Boolean isLike);

	List findEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerHead emsEdiMergerHead);

	List findEmsEdiMergerImgBeforeByPtNo(Request request,
			EmsEdiMergerHead emsEdiMergerHead, String ptNo);

	/**
	 * 获得记录数据
	 * 
	 * @param type
	 * @return
	 */
	int findInnerMergeDataCount(Request request, String type);

	// 分页
	List findInnerMergeDataAfterExgByType10(Request request, String type,
			Integer seqNum, EmsEdiMergerHead emsEdiMergerHead, int index,
			int length, String property, Object value, Boolean isLike);

	/*
	 * 内部归并
	 */
	List findInnerMergeDataByType(Request request, String type);

	/**
	 * 获得分页的内部归并
	 * 
	 * @param type
	 * @param index
	 * @param length
	 * @return
	 */
	List findInnerMergeDataByType(Request request, String type, int index,
			int length, String property, Object value, boolean isLike,
			boolean isShowNoInner);

	
	List findInnerMergeDataByTypeCount(Request request, String type, int index,
			int length, String property, Object value, boolean isLike,
			boolean isShowNoInner);
	
	List findInnerMergeDataByTypeNoMerger(Request request, String type);

	public List findInnerMergeDataByTypeSomeInMerger(Request request,
			String type);

	List findInnerMergeData(Request request);

	/**
	 * 取得内部归并报表的数据
	 * 
	 * @param type
	 * @return
	 */
	List findInnerMergeReportData(Request request, String type);

	InnerMergeData findInnerMergeDataById(Request request, String id);

	InnerMergeData saveInnerMergeData(Request request,
			InnerMergeData innerMergeData);
   /**
    * 将归并前成品和存放企业内部归并两个事务合并成一个事务进行处理
    * @param list
    * @param emsExgAfter
    * @param company
    * @param isSendSign
    * @param emsEdiMergerHead
    */
	void handleEmsEdiMergerExgBefore(List list,
			EmsEdiMergerExgAfter emsExgAfter, BaseCompany company,
			String isSendSign, EmsEdiMergerHead emsEdiMergerHead);
	
	 /**
	    * 将归并前料件和存放企业内部归并两个事务合并成一个事务进行处理
	    * @param list
	    * @param emsExgAfter
	    * @param company
	    * @param isSendSign
	    * @param emsEdiMergerHead
	    */
	void handleEmsEdiMergerImgBefore(List list,
			EmsEdiMergerImgAfter emsImgAfter, BaseCompany company,
			String isSendSign, EmsEdiMergerHead emsEdiMergerHead);
	
	List saveInnerMergeData(Request request,
			List<InnerMergeData> listInnerMergeData);

	void deleteInnerMergeData(Request request, InnerMergeData innerMergeData);

	void importDataFromMaterial(Request request, List materielTypeList);

	int checkDataForTenMerge(Request request, List list);

	List tenInnerMerge(Request request, List list, Complex afterComplex,
			Unit afterMemoUnit, String afterTenMaterielname,
			String afterTenMaterielSpec, Unit afterLegalUnit,
			Unit afterSecondLegalUnit, boolean isNew);

	List unDoTenInnerMerge(Request request, List list);

	/**
	 * 检查能否进行撤消10位归并动作。 如果允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消。
	 * 
	 * @param list
	 * @return
	 */
	int checkDataForUndoTenInnerMerge(Request request, List list);

	/**
	 * 检查所选择的数据能否进行4位归并。 如果检查结果允许归并 返回0； 如果所选择的数据其中有没有经过10位归并的 返回 -1；
	 * 如果所选择的数据的10位商品编码的前4位不同的返回 -2； 如果所选择的已经归并过的数据有不同编码序号的 返回-3; 如果全部已归并返回-4。
	 * 
	 * @param list
	 * @return
	 */
	int checkDataForFourInnerMerge(Request request, List list);

	/**
	 * 4位商品归并 10位商品编码的前4位相同，并且属于同一类商品。
	 * 
	 * @param list
	 * @param isNew
	 */
	List fourInnerMerge(Request request, List list, String fourCommodityName,
			boolean isNew);

	/**
	 * 经营范围--显示四码--来自内部归并
	 */

	List findInnerMergeDataByType4(Request request, String type,
			EmsEdiTrHead emsEdiTrHead, String helfType);

	/**
	 * 取消4位商品归并。
	 * 
	 * @param list
	 */
	List undoFourInnerMerge(Request request, List list);

	/**
	 * 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 重排数据中有空行 -4: 选择的行号在选择的行中,不能进行重排
	 */
	int checkDataForFourInnerMergeSort(Request request, List selectedRows,
			int toRowNumber);

	/**
	 * 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 重排数据中有空行 -4: 选择的行号在选择的行中,不能进行重排
	 */
	int checkDataForTenInnerMergeSort(Request request, List selectedRows,
			int toRowNumber);

	void reSortMergeFourNo(Request request, List selectedRows, int toNo);

	void reSortMergeTenNo(Request request, List selectedRows, int toNo);

	void autoInnerMergeData(Request request, TempAutoInnerMergeParam param);

	void saveCustomInnerMergeCondition(Request request,
			CustomInnerMergeCondition condition);

	void deleteCustomInnerMergeCondition(Request request,
			CustomInnerMergeCondition condition);

	List findCustomInnerMergeConditionByType(Request request, String type);

	List findCustomInnerMergeCondition(Request request);

	void customInnerMerge(Request request);

	void importDataFromTxtFile(Request request, List list);

	List checkFileData(Request request, List list, Hashtable ht,
			String materielType);

	void deleteInnerMergeData(Request request, List selectedList);

	/**
	 * 查找物料主档来自类别-->进出口报关申请单
	 */
	List findMaterielByImpExpRequestBillType(Request request,
			String materielType, String impExpRequestBillId, boolean isFilter);
	/**
	 * 根据料件查询类型查找物料主档来自类别-->进出口报关申请单
	 */
	List findMaterielByMaterielPara(Request request,
			String materielType, String impExpRequestBillId, boolean isFilter,String value,String para);
	
	/**
	 * 查找物料主档来自类别-->关封申请单
	 */
	List findMaterielByCustomsEnvelopRequestBillType(Request request,
			String materielType, String customsEnvelopRequestId);

	/**
	 * 查找物料主档来自类别-->结转单据(转厂)
	 */
	List findMaterielByTransferFactoryBillType(Request request,
			String materielType);

	/**
	 * 检测物料是否备案-->返回 TempMateriel
	 */
	List checkMaterielPutOnRecordsByImpExpRequestCommodity(Request request,
			List dataSource, boolean isPutOnRecord);

	/**
	 * 检测不在内部归并进出商品信息物料名称
	 */
	List checkMaterielNotInMerge(List dataSource);

	/**
	 * 查找物料主档来自类别-->结转初始化单据(转厂)
	 */
	List findMaterielByTransferFactoryInitBillType(Request request,
			String materielType, String initBillId);

	/**
	 * 返回子料件
	 * 
	 * @param request
	 * @param paraentId
	 * @return
	 */
	void findMaterielFromBomManage(Request request, String materielType);

	List findBomImg(Request request, String materielType);

	List findBomAndInMerger(Request request, EmsEdiMergerHead head,
			String ptNo, String materielType);

	/**
	 * 根据物料类别查找反向归并4码数据
	 * 
	 * @param materielType
	 * @return
	 */
	List findReverseMergeFourDataByType(Request request, String materielType);

	/**
	 * 把归并关系中的第三层资料插入ReverseMergeFourData类中
	 */

	void addInnerMergeDataToReverseMergeFourData(Request request,
			String materielType);

	/**
	 * 把归并关系中的第二层资料插入ReverseMergeTenData类中
	 */
	void addInnerMergeDataToReverseMergeTenData(Request request,
			String materielType);

	/**
	 * 把归并关系中的第一层资料插入ReverseMergeBeforeData类中
	 */
	void addInnerMergeDataToReverseMergeBeforeData(Request request,
			String materielType);

	/**
	 * 保存反向归并4码数据 同时更新此相关的内部归并数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "编辑内部归并资料", index = 8)
	ReverseMergeFourData saveReverseMergeFourData(Request request,
			ReverseMergeFourData fourData);

	/**
	 * 删除反向归并4码数据并且同时删除10码数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "删除内部归并资料", index = 9)
	void deleteReverseMergeFourData(Request request,
			ReverseMergeFourData fourData);

	/**
	 * 根据凡响归并4码数据查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	List findReverseMergeTenDataByFour(Request request,
			ReverseMergeFourData fourData);

	/**
	 * 根据物料类别查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	List findReverseMergeTenDataByType(Request request, String materielType);

	/**
	 * 根据商编查找第一法定单位与第二法定单位
	 */
	List findComplexLegalUnit(Request request,String code);

	/**
	 * 保存反向归并10码数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "编辑内部归并资料", index = 8)
	ReverseMergeTenData saveReverseMergeTenData(Request request,
			ReverseMergeTenData tenData);

	/**
	 * 删除反向归并10码数据 同时删除或者更新此10码所关联的归并前数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "删除内部归并资料", index = 9)
	void deleteReverseMergeTenData(Request request, ReverseMergeTenData tenData);

	/**
	 * 根据反向归并后10码数据查找反向归并数据
	 * 
	 * @param fourData
	 * @return
	 */
	List findReverseMergeBeforeDataByTen(Request request,
			ReverseMergeTenData tenData);

	/**
	 * 保存反向归并前数据 同时更新此关联的物料数据和内部归并数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "编辑内部归并资料", index = 8)
	ReverseMergeBeforeData saveReverseMergeBeforeData(Request request,
			ReverseMergeBeforeData beforeData);

	/**
	 * 删除反向归并前数据 并同时要删除由此产生的内部归并数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "删除内部归并资料", index = 9)
	void deleteReverseMergeBeforeData(Request request,
			ReverseMergeBeforeData beforeData);

	/**
	 * 新增反向归并前数据 同时生成新的内部归并数据
	 * 
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "编辑内部归并资料", index = 8)
	void addReverseMergeBeforeData(Request request, List list,
			ReverseMergeTenData tenData);

	/**
	 * 将内部归并10码数据和反向归并4码数据对接起来
	 * 
	 */
	void concatInnerTenAndReverseFour(Request request, List innerTenDatas,
			ReverseMergeFourData fourData);

	/**
	 * 将内部归并前数据和反向归并10码数据对接起来
	 * 
	 */
	@AuthorityFunctionAnnotation(caption = "编辑内部归并资料", index = 8)
	void concatInnerBeforeAndReverseTen(Request request, List innerBeforeDatas,
			ReverseMergeTenData tenData);

	/**
	 * 查询未进行4码归并的内部归并数据(为对接查询使用)
	 * 
	 * @param fourData
	 * @param materielType
	 * @return
	 */
	List findTenDataNotFourMerge(Request request,
			ReverseMergeFourData fourData, String materielType);

	/**
	 * 查询未进行10码归并的内部归并数据(为对接查询使用)
	 * 
	 * @param materielType
	 * @return
	 */
	List findBeforeDataNotTenMerge(Request request, String materielType);

	/**
	 * 查询不在内部归并中存在的物料基本资料
	 * 
	 * @param materielType
	 * @return
	 */
	List findMaterielNotInInnerMerge(Request request, String materielType);

	/**
	 * 返回反向归并10码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	int findMaxTenReverseMergeNo(Request request, String type);

	/**
	 * 返回反向归并4码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	int findMaxFourReverseMergeNo(Request request, String type);

	/**
	 * 查找所有前四码匹配的项目
	 * 
	 * @param fourCode
	 * @return
	 */
	List findComplexByFourCode(Request request, String fourCode);

	/**
	 * 根据凡响归并4码数据查找反向归并10码数据的个数
	 * 
	 * @param fourData
	 * @return
	 */
	int findReverseMergeTenDataCountByFour(Request request,
			ReverseMergeFourData fourData);

	/**
	 * 根据反向归并10码数据查找反向归并前数据个数
	 * 
	 * @param fourData
	 * @return
	 */
	int findReverseMergeBeforeDataCountByTen(Request request,
			ReverseMergeTenData tenData);

	/**
	 * 判断有哪些内部归并数据是否做了经营范围的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	List findInnerMergeDataInEmsEdiTr(Request request, List lsInnerMergeData);

	/**
	 * 检查反向归并4码相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	boolean checkWhetherReverseFourDataPutOnRecord(Request request,
			ReverseMergeFourData fourData);

	/**
	 * 检查反向归并10码相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	boolean checkWhetherReverseTenDataPutOnRecord(Request request,
			ReverseMergeTenData tenData);

	/**
	 * 检查反向归并前相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	boolean checkWhetherReverseBeforeDataPutOnRecord(Request request,
			ReverseMergeBeforeData beforeData);

	/**
	 * 检查反向归并4码相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	boolean checkWhetherReverseFourDataPutOnRecord(Request request,
			List lsFourData);

	/**
	 * 检查反向归并10码相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	boolean checkWhetherReverseTenDataPutOnRecord(Request request,
			List lsTenData);

	/**
	 * 检查反向归并前相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	boolean checkWhetherReverseBeforeDataPutOnRecord(Request request,
			List lsBeforeData);

	/**
	 * 删除反向归并4码数据并且同时删除10码数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "删除内部归并资料", index = 9)
	void deleteReverseMergeFourData(Request request, List lsFourData);

	/**
	 * 删除反向归并10码数据 同时删除或者更新此10码所关联的归并前数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "删除内部归并资料", index = 9)
	void deleteReverseMergeTenData(Request request, List lsTenData);

	/**
	 * 删除反向归并前数据 并同时要删除由此产生的内部归并数据
	 * 
	 * @param data
	 */
	@AuthorityFunctionAnnotation(caption = "删除内部归并资料", index = 9)
	void deleteReverseMergeBeforeData(Request request, List lsBeforeData);

	/**
	 * 判断内部归并后数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	List findInnerMergeInEmsEdiMergeAfter(Request request, List lsInnerMergeData);

	/**
	 * 判断内部归并前数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	List findInnerMergeInEmsEdiMergeBefore(Request request,
			List lsInnerMergeData);

	/**
	 * 检查数据为十位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 在多个四位归并类别中不能重排 -4:
	 * 重排行数超出范围
	 */
	int checkTenInnerMergeDataInFourMerge(Request request, List selectedRows,
			int toRowNumber);

	/**
	 * 查找内部归并数据块来自 四码归并序号
	 * 
	 * @param beginNo
	 * @param toNo
	 * @return
	 */
	List findInnerMergeByFourNo(Request request, String materielType, int fourNo);

	/**
	 * 判断料件经营范围的备案中是否有数据
	 * 
	 * @param innerMergeData
	 * @return
	 */
	boolean findHasEmsEdiTrExg(Request request);

	/**
	 * 判断成品经营范围的备案中是否有数据
	 * 
	 * @param innerMergeData
	 * @return
	 */
	boolean findHasEmsEdiTrImg(Request request);

	List findInnerMergeDataByType10ToEmsH2k(Request request, String type,
			EmsEdiTrHead emsEdiTrHead, EmsHeadH2k emsHeadH2k, String helfType);

	/**
	 * 检查浏览产品结构BOM管理的权限
	 * 
	 * @param request
	 */
	void checkBomBrowseAuthority(Request request);

	/**
	 * 显示物料主档成品
	 */
	@AuthorityFunctionAnnotation(caption = "浏览成品单耗", index = 11)
	List findMaterielWhereCp(Request request, String ptNo);

	/**
	 * 检查浏览成品单耗的权限
	 * 
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "浏览成品单耗", index = 11)
	void checkUnitWasteBrowseAuthority(Request request);

	/**
	 * 检查浏览报关资料工厂对照表权限
	 * 
	 * @param request
	 */
	void checkFactoryCustomsBrowseAuthority(Request request);

	/**
	 * 检查归并关系禁用权限
	 * 
	 * @param request
	 */
	public void checkInnerMergeDataIsForbidAuthority(Request request);

	/**
	 * 物流与归并对照表
	 * 
	 * @param type
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "浏览物流与归并对照表", index = 11)
	List getInnerMergeDataToCollate(Request request, String type,
			String seqNum, String tenName);

	// 得到物料由十码
	List findInnerMergeDataByTenNo(Request request, String type, String tenNo);

	/**
	 * 查询全部禁用的归并关系
	 * 
	 * @param forBidBeginDate
	 * @param forBidEndData
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataIsForbid(Request request,
			Date forBidBeginDate, Date forBidEndData, String type,
			boolean isForbid);

	/**
	 * 修改内部归并的禁用
	 * 
	 * @param type
	 * @param ptNo
	 * @param isForbid
	 */
	public void updateInnerMergeDataIsForbid(Request request, String type,
			String ptNo, boolean isForbid, String user);

	// 得到物料由4码
	List findInnerMergeDataByFourNo(Request request, String type, String FourNo);

	// 初始化单位折算比例
	void initUnitDedault(Request request);

	void initDzscUnitDedault(Request request);

	// 显示pk单
	List findPK(Request request);

	// 显示集装箱单
	List findErpContainer(Request request);

	// 显示不重复的pk单据
	List findDistinctPk(Request request);

	// 显示pk单据由pk单号
	List findpkForpkno(Request request, List list);

	/**
	 * 判断四码是否有其它十码对应
	 * 
	 * @param selectedInnerMergeData
	 * @return
	 */
	boolean findInnerMergeDataByFilter(Request request,
			List selectedInnerMergeData);

	/**
	 * 查找不存在内部归并的物料来自料件类型
	 * 
	 * @param materielType
	 * @return
	 */
	List findMaterielNotInInnerMerge(Request request, String materielType,
			int index, int length, String property, Object value, Boolean isLike);

	List findMaterielAll(Request request, String materielType, int index,
			int length, String property, Object value, Boolean isLike);

	List findMaterielAllOfCustomsRalation(Request request, String materielType,
			int index, int length, String property, Object value,
			Boolean isLike, Boolean isFilt);

	/**
	 * 
	 * 
	 */
	List findEmsEdiImgbefore(Request request, int index, int length,
			String property, Object value, Boolean isLike);

	List findEmsEdiBomImgbefore(Request request, int index, int length,
			String property, Object value, Boolean isLike);

	/**
	 * 导入内部归并来自物料主档
	 * 
	 * @param materielList
	 */
	List importInnerMergeDataFromMateriel(Request request, List materielList,
			String materielType);

	InnerMergeData saveInner(Request request, InnerMergeData inner,
			Materiel materiel);

	void deleteInnerNoten(Request request, String type);

	List getDistinctTenInner(Request request, String materielType);

	List getDistinctFourceInner(Request request, String materielType);

	List addtenInner(Request request, String type, String seqNum, List list);

	List addfourceInner(Request request, String type, BillTemp obj, List list);

	int checkDataEndTenInnerMerge(Request request, List list);

	List findWhetherInnerMergeInEmsHeadH2k(Request request,
			List lsInnerMergeData);

	List findWhetherReverseMergeInEmsHeadH2k(Request request,
			List lsInnerMergeData);

	/**
	 * 新的更新内部归并的报关资料
	 * 
	 * @param passList
	 * @param materielType
	 * @param tenAfterComplex
	 * @param tenName
	 * @param tenSpec
	 * @param tenAfterMemoUnit
	 * @param newTenSeqNum
	 * @param fourCode
	 * @param fourName
	 * @param newFourSeqNum
	 */
	List[] updateTenAndFourCustoms(Request request, List passList,
			String materielType, Complex tenAfterComplex, String tenName,
			String tenSpec, Unit tenAfterMemoUnit, Integer newTenSeqNum,
			String fourCode, String fourName, Integer newFourSeqNum);

	// 更新十位商品资料（编码，名称，规格，单位）
	void changeCustoms(Request request, Complex afterComplex, String name,
			String spec, Unit afterMemoUnit, String materielType,
			Integer tenMemoNo);

	// 更新十位商品序号
	void changeCustomsSeqNum(Request request, String materielType,
			Integer oldSeqNum, Integer newSeqNum);

	// 更新四位商品序号
	void changeCustomsSeqNumFourNo(Request request, String materielType,
			Integer oldSeqNum, Integer newSeqNum);

	// 更新四位商品资料（编码，名称）
	void changeCustomsFourNo(Request request, String complex, String name,
			String materielType, Integer fourNo);

	/**
	 * 电子帐册归并关系删除修改标记
	 */
	void editMergerFlag(Request request, String type, Integer seqNum,
			String ptNo);
	/**
	 * 电子帐册归并关系删除修改标记，其中不需要备案序号为条件查询
	 */
	void editMergerFlag0(Request request, String type,
			String ptNo);

	List findVersionByCpNo(Request request, String ptNo);

	/*
	 * public CurrRate findCurrRateByCurr1(Curr curr1){ }
	 */

	public void deleteInner(Request request, InnerMergeData inner);

	public List changeNotMerger(Request request, List list);

	Date findMaxImportTimerByInnerMergeData(Request request, String type);

	Date findLastUpdateDateByInnerMergeData(Request request, String type);

	public Date findLastInnestDateByInnerMergeData(Request request, String type);

	public List findInnerMergedataOrder(Request request);

	public InnerMergeDataOrder saveInnerOrder(Request reques,
			InnerMergeDataOrder obj);

	List findInnerMergeDataByPtNo(Request request, String ptNo);
	/**
	 * 得到物料由十码
	 * 
	 * @param type
	 * @param tenNo
	 * @return
	 */
	public List findInnerMergeDataByTenNoFromFile(Request request,String type, String tenNo);
	/**
	 * 内部归并关系文本导入检查使用
	 * @param type
	 * @param fourNo
	 * @return
	 */
	public List findInnerMergeDataByFourNoFromFile(Request request, String type, String fourNo);
	/**
	 * 把选中的十码在内部关系管理已备案，但对应归并前没备案的数据写到内部关系管理里
	 * 
	 * @param request
	 * @param materielType
	 *            "0"为成品,"2"为料件
	 * @param list
	 *            内部归并的list
	 */
	public void writeBackEmsEdiMergerExgImgBefore(Request request,
			String materielType, List list);

	/**
	 * 查找内部归并后使用到得报关单
	 * 
	 * @param request
	 * @param listCheckData
	 * @return
	 */
	List findInnerMergeInEmsEdiMergeOfUsed(Request request,
			List<InnerMergeData> listCheckData);

	List findInnerMergeInEmsEdiMergeBeforeOfUsed(Request request, List list);
}