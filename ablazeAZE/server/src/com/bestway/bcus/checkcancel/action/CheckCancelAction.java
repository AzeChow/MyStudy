/*
 * Created on 2005-4-10
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.action;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.checkcancel.entity.CancelCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelExgBefore;
import com.bestway.bcus.checkcancel.entity.CancelExgResult;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CancelImgBefore;
import com.bestway.bcus.checkcancel.entity.CancelImgResult;
import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkcancel.entity.CancelTemp;
import com.bestway.bcus.checkcancel.entity.CheckExg;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccountComport;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.checkcancel.entity.ColorSet;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsBgExg;
import com.bestway.bcus.checkcancel.entity.EmsBgExgBg;
import com.bestway.bcus.checkcancel.entity.EmsBgImg;
import com.bestway.bcus.checkcancel.entity.EmsBgTotal;
import com.bestway.bcus.checkcancel.entity.EmsCy;
import com.bestway.bcus.checkcancel.entity.EmsPdExg;
import com.bestway.bcus.checkcancel.entity.EmsPdExgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdExgImgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdImg;
import com.bestway.bcus.checkcancel.entity.EmsPdImgBg;
import com.bestway.bcus.checkcancel.entity.EmsPdTotal;
import com.bestway.bcus.checkcancel.entity.EmsTransFactory;
import com.bestway.bcus.checkcancel.entity.EmsTransFactoryBg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckExg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.common.Request;

/**
 * @author xxm // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface CheckCancelAction {
	public abstract List findCheckHead(Request request, CheckParameter head);

	public abstract CheckHead saveCheckHead(Request request, CheckHead checkHead)
			throws DataAccessException;

	public abstract void deleteCheckHead(Request request, CheckHead checkHead);

	public abstract List findCheckImg(Request request, CheckHead checkHead);

	public abstract CheckImg saveCheckImg(Request request, CheckImg checkImg)
			throws DataAccessException;

	public abstract void deleteAllCheckImgExg(Request request,
			CheckHead checkHead);

	public abstract void deleteCheckImg(Request request, CheckImg checkImg);

	public abstract List findCheckExg(Request request, CheckHead checkHead);

	public abstract CheckExg saveCheckExg(Request request, CheckExg checkExg)
			throws DataAccessException;

	public abstract void deleteCheckExg(Request request, CheckExg checkExg);

	public abstract List findImgFromInnerMerge(Request request,
			EmsHeadH2k emsHeadH2k, CheckHead checkHead);

	public abstract List findExgFromInnerMerge(Request request,
			EmsHeadH2k emsHeadH2k, CheckHead checkHead);

	public abstract List findCancelHead(Request request, boolean isOwner);

	public abstract CancelHead saveCancelHead(Request request,
			CancelHead cancelHead) throws DataAccessException;

	public abstract void deleteCancelHead(Request request, CancelHead cancelHead);

	public abstract List findCancelCustomsDeclara(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract CancelCustomsDeclara saveCancelCustomsDeclara(
			Request request, CancelCustomsDeclara cancelCustomsDeclara)
			throws DataAccessException;

	/*
	 * public abstract List findCustomsDeclaration(Request request, CancelHead
	 * cancelHead);
	 */

	public abstract void deleteCancelCustomsDeclara(Request request,
			CancelCustomsDeclara cancelCustomsDeclara);

	public abstract List findCancelImgBefore(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract List findCancelImgMiddle(Request request,
			CancelHead cancelHead);

	public abstract List findCancelImgResult(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract List findCancelExgBefore(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract List findCancelExgMiddle(Request request,
			CancelHead cancelHead);

	public abstract List findCancelExgResult(Request request,
			CancelHead cancelHead, boolean isOwner);

	// public abstract List findImgFromCustomsDeclarationCommInfo(Request
	// request);

	// public abstract List findExgFromCustomsDeclarationCommInfo(Request
	// request);

	public abstract CancelImgBefore saveCancelImgBefore(Request request,
			CancelImgBefore cancelImgBefore) throws DataAccessException;

	public abstract void getImg(Request request,CancelHead cancelHead, boolean isOwner);

	public abstract CancelExgBefore saveCancelExgBefore(Request request,
			CancelExgBefore cancelExgBefore) throws DataAccessException;

	public void getExg(Request request, CancelHead cancelHead, boolean isOwner);

	public abstract CancelImgResult saveCancelImgResult(Request request,
			CancelImgResult cancelImgResult) throws DataAccessException;

	public abstract CancelExgResult saveCancelExgResult(Request request,
			CancelExgResult cancelExgResult) throws DataAccessException;

	public abstract void deleteCancelImg(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract void deleteCancelImgAvgPrice(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract void deleteCancelTemp();

	public abstract void deleteCancelExg(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract List findCancelCustomsDeclaraNum(Request request,
			CancelHead cancelHead, String inOutportFlat, boolean isOwner);

	public abstract CancelHead fillCancelHeadData(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract CancelTemp saveCancelTemp(Request request,
			CancelTemp cancelTemp) throws DataAccessException;

	public abstract void deleteCancelAll(Request request,
			CancelHead cancelHead, boolean isOwner);

	public abstract List findExgCustoms(Request request);

	public abstract List findGroupImg(Request request);

	public abstract void getCancelImg(Request request, CancelHead cancelHead,
			EmsHeadH2k emsHeadH2k, boolean isOwner, boolean isUnitWare);

	public abstract List getCancelImgByMonth(Request request,
			CancelHead cancelHead, EmsHeadH2k emsHeadH2k, boolean isOwner,
			boolean isUnitWare);

	public abstract List getImgAveragePriceByMonth(Request request,
			CancelHead cancelHead, EmsHeadH2k emsHeadH2k, boolean isOwner);

	public abstract List findImgFromCustomsDeclarationCommInfoByResult(
			Request request, String seqNum);

	public abstract CancelHead getHead(Request request, CancelHead cancelHead,
			boolean isOwner);

	// ---
	public abstract List findEmsAnalyHead(Request request);

	public abstract List findCancelImgAvgPriceLeave(Request request,
			CancelHead cancelHead,boolean isOwner, Date beginDate, Date endDate);

	/**
	 * 得到流水号
	 */
	public abstract Integer getNum(Request request, String className,
			String seqNum);

	/**
	 * 保存核销头
	 */
	public abstract EmsAnalyHead SaveEmsAnalyHead(Request request,
			EmsAnalyHead head);

	/**
	 * 返回盘点料件
	 */
	public abstract List findEmsPdImg(Request request, EmsAnalyHead head);

	/**
	 * 返回盘点报关料件
	 */
	public abstract List findEmsPdImgBg(Request request, EmsAnalyHead head);

	/**
	 * 返回盘点成品
	 */
	public abstract List findEmsPdExg(Request request, EmsAnalyHead head);

	/**
	 * 返回盘点报关成品
	 */
	public abstract List findEmsPdExgBg(Request request, EmsAnalyHead head);

	/**
	 * 返回盘点总表
	 */
	public abstract List findEmsPdTotal(Request request, EmsAnalyHead head);

	/**
	 * 返回差异分析
	 */
	public abstract List findEmsCy(Request request, EmsAnalyHead head);

	/**
	 * 返回报关--总表
	 */
	public abstract List findEmsBgTotal(Request request, EmsAnalyHead head);

	/**
	 * 返回报关--出口成品折算料件
	 */
	public abstract List findEmsBgExgBg(Request request, EmsAnalyHead head,String exgId);

	/**
	 * 返回报关--出口成品
	 */
	public abstract List findEmsBgExg(Request request, EmsAnalyHead head);

	/**
	 * 返回报关--进口料件
	 */
	public abstract List findEmsBgImg(Request request, EmsAnalyHead head);

	/**
	 * 获得物料主档
	 */
	public abstract List findMateriel(Request request, EmsAnalyHead head,
			String type);

	/**
	 * 保存盘点料件
	 */
	public abstract EmsPdImg SaveEmsPdImg(Request request, EmsPdImg obj);

	/**
	 * 保存盘点成品
	 */
	public abstract EmsPdExg SaveEmsPdExg(Request request, EmsPdExg obj);

	/**
	 * 组织HQL报表条件公共查询
	 * 
	 * @param className
	 * @param conditions
	 * @return
	 */
	public abstract List commonSearch(Request request, String selects,
			String className, List conditions, String groupBy);

	/**
	 * 删除报关料件进口
	 */
	public abstract void deleteEmsBgImgAll(Request request, EmsAnalyHead head);

	// 报关进口料件查询
	public abstract List calculateBgImg(Request request, List conditions,
			EmsAnalyHead head);

	/**
	 * 删除报关成品折料
	 */
	public abstract void deleteEmsBgExgImgAll(Request request, EmsAnalyHead head, String emsBgExgId,
			boolean isAllOrSinlg);

	/**
	 * 删除报关成品出口
	 */
	public abstract void deleteEmsBgExgAll(Request request, EmsAnalyHead head);

	// 报关出口成品查询
	public abstract List calculateBgExg(Request request, List conditions,
			EmsAnalyHead head);

	/**
	 * 保存报关料件
	 */
	public abstract EmsBgImg SaveEmsBgImg(Request request, EmsBgImg obj);

	/**
	 * 保存报关成品
	 */
	public abstract EmsBgExg SaveEmsBgExg(Request request, EmsBgExg obj);

	/**
	 * 保存报关成品折算料件
	 */
	public abstract EmsBgExgBg SaveEmsBgExgImg(Request request, EmsBgExgBg obj);

	/**
	 * 报关数据分析--- 成品进出口---报关成品折料
	 * 
	 * @param head
	 * @return
	 */
	public abstract List convertBgExgToImg(Request request, EmsAnalyHead head,String emsBgExgId,boolean isAllOrSing);
	
	void convertBgExgToImg(Request request, EmsAnalyHead head);
	
	/**
	 * 盘点数据分析 ---成品折料-- 盘点成品折料
	 * @param head
	 */
	public abstract List convertPdExgBgToPdExgImgBg(Request request, EmsAnalyHead head,List list) ;

	/**
	 * 保存报关总和
	 */
	public abstract EmsBgTotal SaveEmsBgTotal(Request request, EmsBgTotal obj);

	// 报关进出口总数
	public abstract List findBgExgImg(Request request, EmsAnalyHead head);

	/**
	 * 删除报关进出口总表
	 */
	public abstract void deleteEmsBgTotal(Request request, EmsAnalyHead head);

//	/**
//	 * 盘点得到报关料件来自内部归并
//	 */
//	public abstract List findBgImgFromInnerMerger(Request request, String type,
//			String ptNo);

	/**
	 * 盘点资料转换报关料件资料
	 */
	public abstract List<String> convertPdImgBg(Request request, EmsAnalyHead head, List<EmsPdImg> imgList);

	/**
	 * 删除盘点料件报关
	 */
	public abstract void deletePdImgBg(Request request, EmsAnalyHead head);

	/**
	 * 删除盘点成品报关
	 */
	public abstract void deletePdExgBg(Request request, EmsAnalyHead head);
	
	/**
	 * 盘点返回报关成品资料
	 */
	public abstract void findPdExg(Request request, EmsAnalyHead head,boolean isMaxVersion,EmsPdExg exg );
	
	/**
	 * 查看所有盘点成品未归并
	 * @param head
	 * @return
	 */
	public List findAllEmsPdExgNoEms(Request request, EmsAnalyHead head);

	/**
	 * 保存报关料件
	 */
	public abstract EmsPdImgBg SaveEmsPdImgBg(Request request, EmsPdImgBg obj);

	/**
	 * 保存报关成品
	 */
	public abstract EmsPdExgBg SaveEmsPdExgBg(Request request, EmsPdExgBg obj);

	/**
	 * 删除折料成品料件
	 */
	public abstract void deleteEmsPdExgImgBgAll(Request request,
			EmsAnalyHead head);

	/**
	 * 返回盘点报关折料料件
	 * 
	 */
	public abstract List findEmsExgImgBg(Request request, EmsAnalyHead head);

	/**
	 * 返回报关--所有出口成品折算料件
	 */
	public abstract List findEmsBgExgBgAll(Request request, EmsAnalyHead head);

	/**
	 * 保存盘点成品折料
	 */
	public abstract EmsPdExgImgBg SaveEmsPdExgImgBg(Request request,
			EmsPdExgImgBg obj);

//	// 盘点成品折料
//	public abstract void pdExgImg(Request request, EmsAnalyHead head);

	/**
	 * 保存盘点分析总表
	 */
	public abstract EmsPdTotal SaveEmsPdTotal(Request request, EmsPdTotal obj);

	// 盘点分析总表
	public abstract List findPdExgImg(Request request, EmsAnalyHead head);

	/**
	 * 清空盘点分析总表
	 */
	public abstract void deleteEmsPdTotalAll(Request request, EmsAnalyHead head);

	/**
	 * 清空差异分析总表
	 */
	public abstract void deleteEmsCyAll(Request request, EmsAnalyHead head);

	/**
	 * 保存差异
	 */
	public abstract EmsCy SaveEmsCy(Request request, EmsCy obj);

	// 差异分析总表
	public abstract List<EmsCy> calculateEmsCy(Request request, EmsAnalyHead head);

	/**
	 * 删除盘点料件
	 */
	public abstract void deleteEmsPdImgAll(Request request, EmsAnalyHead head);

	/**
	 * 删除盘点成品
	 */
	public abstract void deleteEmsPdExgAll(Request request, EmsAnalyHead head);
	
	/**
	 * 删除任何实体
	 * @param request
	 * @param obj
	 */
	public void deleteObject(Request request,Object obj);

	public void deleteEmsAnalyHead(Request request, EmsAnalyHead obj);

	/**
	 * 查询所有盘点报关数据
	 * @param request
	 * @param head
	 * @return
	 */
	public List findEmsPdExgBgAll(Request request, EmsAnalyHead head);

	public List getCustomsToCheckHead(Request request, CancelHead cancelHead,
			boolean isOwner);

	public List findCustomsDeclaration(Request request, CancelHead cancelHead,
			boolean isOwner,Date beginDate,Date endDate,String customsDecCode);

	public List getCustomsIEToTemp(Request request, CancelHead cancelHead,
			boolean isOwner);

	public CancelHead modityCancelHead(Request request, CancelHead cancelHead,
			boolean isOwner);

	public List findCancelHeadByType(Request request, String declareType,
			boolean isOwner);

	public List findCancelHeadByState(Request request, String declareState,
			boolean isOwner);

	public List getCancelExg(Request request, CancelHead cancelHead,
			EmsHeadH2k emsHeadH2k, boolean isOwner);

	public void synCancel(Request request, CancelOwnerHead head);

	public Integer getCheckTimes(Request request);

	public List findCheckOwnerHead(Request request);

	public void getCancel(Request request, String times);

	public List addEmsLjPd(Request request, List list, EmsAnalyHead head);

	public List addEmsCpPd(Request request, List list, EmsAnalyHead head);

	/**
	 * 返回核查参数设定
	 * 
	 * @return
	 */
	public List findCheckParameter(Request request);

	/**
	 * 用作权限控制
	 * 
	 * @return
	 */
	public void controlFactoryCehck(Request request);

	/**
	 * 保存核查参数设定
	 */
	public CheckParameter SaveCheckParameter(Request request,
			CheckParameter head);

	/**
	 * 返回工厂盘点料件
	 */
	public List findFactoryCheckImg(Request request, CheckParameter head);

	/**
	 * 返回工厂盘点成品
	 */
	public List findFactoryCheckExg(Request request, CheckParameter head);

	/**
	 * 返回工厂盘点成品折算料件
	 */
	public List findFactoryCheckExgConverImg(Request request,
			CheckParameter head);

	public List getFactoryCheckImg(Request request, List list,
			CheckParameter head);

	public List getFactoryCheckExg(Request request, List list,
			CheckParameter head);

	/**
	 * 成品折算为料件
	 */
	public List factoryExgConvertImg(Request request, List list);

	public List findCheckOwnerAccount(Request request, CheckParameter head);

	public List findFactoryCheckImgCollect(Request request, CheckParameter head);

	public void deleteCheckOwnerAccount(Request request, CheckParameter head);

	public void deleteFactoryCheckExgConverImg(Request request,
			CheckParameter head);

	/**
	 * 权限控制
	 */
	void controlCheckHead(Request request);

	/**
	 * 计算核查计算表
	 */
	public List accountCheckAccount(Request request, CheckParameter head);

	public List totalMateriel(Request request, List imgList, List convertImgList);

	public void deleteTotalImg(Request request, CheckParameter head);

	/**
	 * 返回中期核查计算表对比
	 * 
	 * @return
	 */
	public List findCheckOwnerAccountComport(Request request,
			CheckParameter head);

	public void deleteCheckOwnerAccountComport(Request request,
			CheckParameter head);

	public List getAccountComport(Request request, List accountList,
			CheckParameter head);

	// 同步到核查表中
	public void newToCheckHead(Request request, CheckParameter head,
			EmsHeadH2k emsHeadH2k);

	public List findAllColor(Request request);

	public ColorSet findColorSet(Request request, int color);

	public void saveColorSet(Request request, ColorSet obj);

	public ColorSet findColorByNum(Request request, Double num);

	public CheckOwnerAccountComport saveCheckOwnerAccountComport(
			Request request, CheckOwnerAccountComport obj);

	// 明门成品折算料件所用
	public List findFactoryStoryProduct(Request request);

	public List findEmsHeadH2kVersionByFactoryStoryProductPtNo(Request request,
			String ptNo);

	public List findAllEmsHeadH2kExgVersion(Request request);

	public List findEmsHeadH2kBom(Request request, EmsHeadH2kVersion version,
			Double amountPrice);

	public void saveOrUpdateFactoryStoryProduct(Request request, List datalist);

	public void delFactoryStoryProduct(Request request, List datalist);

	public List findTempEmsHeadH2kBomAnyle(Request request, List list);

	public List importDataToFactoryCheck(Request request, List list,
			String materielType, CheckParameter head);

	public void saveDataToFactoryCheck(Request request, List list,
			CheckParameter head, String materielType);

	public List importDataToCheck(Request request, List list, CheckHead head);

	public void saveDataToCheck(Request request, List list, CheckHead head);

	public void deleteCheckParameter(Request request, CheckParameter obj);

	public void deleteList(Request request, List list);

	public void deleteFactoryCheckImgForBg(Request request, CheckParameter head);

	public List findFactoryCheckImgForBgAll(Request request, CheckParameter head);

	public List convertBgImg(Request request, List list, CheckParameter head);

	public List importImgForBg(Request request, CheckParameter head);

	public List findCheckBgCy(Request request, CheckParameter head);

	public void deleteCheckImgBgComport(Request request, CheckParameter head);

	public List findFactoryCheckBgCy(Request request, CheckParameter head);
	/**
	 * 
	 * 显示核销单耗
	 * 
	 * @param head
	 *            核销表头
	 * @param isOwner
	 *            是否是自用核销或者是海关核销
	 * @return 所有List数据
	 */
	public List findCancelUnitWear(Request request, CancelHead head,
			boolean isOwner, int index, int length, String property,
			Object value, Boolean isLike) ;
	
	public List findCancelUnitWear(Request request, CancelHead head,
			boolean isOwner);

	public void deleteCancelUnitWear(Request request, CancelHead cancelHead,
			boolean isOwner);

	public CancelHead findMaxCancelTimesCancelHead(Request request,
			boolean isOwner,String declareType);

	public void deleteCancelImgExgList(Request request, List ls);

	public void makeCustomsDeclarationisEmsCav(Request repuest,
			CancelHead cancelHead, boolean emscav);
	
	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */
	public List findEmsHeadH2k(Request request);

	public abstract void checkfindCheckOwnerAccountnew(Request request);
	
	public List checkCancelData(Request request,CancelHead cancelHead);
	
	public List<EmsTransFactory> importEmsTransFactory(Request request, EmsAnalyHead head, List<EmsTransFactory> list);
	
	public void deleteEmsTransFactory(Request request, List<EmsTransFactory> list);
	
	public List<EmsTransFactoryBg> convertEmsTransFactoryToBg(Request request, EmsAnalyHead head,
			List<EmsTransFactory> list);
	
	public List<EmsTransFactory> findEmsTransFactory(Request request, EmsAnalyHead head);
	
	public List<EmsTransFactoryBg> findEmsTransFactoryBg(Request request, EmsAnalyHead head);
	
	public void deleteEmsTransFactoryBg(Request request, EmsAnalyHead head) ;
	
	public FactoryCheckImg saveFactoryCheckImg(Request request,FactoryCheckImg factoryCheckImg);
	public FactoryCheckExg saveFactoryCheckExg(Request request,FactoryCheckExg factoryCheckExg);
}