/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.action;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.checkcancel.dao.CheckCancelDao;
import com.bestway.bcus.checkcancel.dao.EmsAnalyDao;
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
import com.bestway.bcus.checkcancel.logic.CheckCancelLogic;
import com.bestway.bcus.checkcancel.logic.EmsAnalyLogic;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
//核查核销
 @AuthorityClassAnnotation(caption = "电子帐册", index = 6)
public class CheckCancelActionImpl extends BaseActionImpl implements
		CheckCancelAction {
	private CheckCancelDao checkCancelDao = null;

	private EmsAnalyDao emsAnalyDao = null;

	private EmsAnalyLogic emsAnalyLogic = null;

	private CheckCancelLogic checkCancelLogic = null;

	// @AuthorityFunctionAnnotation(caption = "浏览中期核查", index = 1)
	public List findCheckHead(Request request, CheckParameter head) { // 显示中期核查表头
		return checkCancelDao.findCheckHead(head);
	}

	// @AuthorityFunctionAnnotation(caption = "保存中期核查", index = 2)
	public CheckHead saveCheckHead(Request request, CheckHead checkHead)
			throws DataAccessException {
		checkCancelDao.saveCheckHead(checkHead); // 保存中期核查表头
		return checkHead;
	}

	// @AuthorityFunctionAnnotation(caption = "删除中期核查", index = 3)
	public void deleteCheckHead(Request request, CheckHead checkHead) { // 删除中期核查表头
		checkCancelDao.deleteCheckHead(checkHead);
	}

	public List findCheckImg(Request request, CheckHead checkHead) { // 显示中期核查料件
		return checkCancelDao.findCheckImg(checkHead);
	}

	public CheckImg saveCheckImg(Request request, CheckImg checkImg)
			throws DataAccessException {
		checkCancelDao.saveCheckImg(checkImg); // 保存中期核查料件
		return checkImg;
	}

	// @AuthorityFunctionAnnotation(caption = "删除中期核查", index = 3)
	public void deleteAllCheckImgExg(Request request, CheckHead checkHead) { // 删除中期核查所有表体
		checkCancelDao.deleteAllCheckImgExg(checkHead);
	}

	// @AuthorityFunctionAnnotation(caption = "删除中期核查", index = 3)
	public void deleteCheckImg(Request request, CheckImg checkImg) { // 删除中期核查料件
		checkCancelDao.deleteCheckImg(checkImg);
	}

	public List findCheckExg(Request request, CheckHead checkHead) { // 显示中期核查成品
		return checkCancelDao.findCheckExg(checkHead);
	}

	// @AuthorityFunctionAnnotation(caption = "保存中期核查", index = 2)
	public CheckExg saveCheckExg(Request request, CheckExg checkExg)
			throws DataAccessException {
		checkCancelDao.saveCheckExg(checkExg); // 保存中期核查成品
		return checkExg;
	}

	// @AuthorityFunctionAnnotation(caption = "删除中期核查", index = 3)
	public void deleteCheckExg(Request request, CheckExg checkExg) { // 删除中期核查成品
		checkCancelDao.deleteCheckExg(checkExg);
	}

	public List findImgFromInnerMerge(Request request, EmsHeadH2k emsHeadH2k,
			CheckHead checkHead) { // 中期核查--添加料件
		return checkCancelDao.findImgFromInnerMerge(emsHeadH2k, checkHead);
	}

	public List findExgFromInnerMerge(Request request, EmsHeadH2k emsHeadH2k,
			CheckHead checkHead) { // 中期核查--添加成品
		return checkCancelDao.findExgFromInnerMerge(emsHeadH2k, checkHead);
	}

	// @AuthorityFunctionAnnotation(caption = "浏览数据报核", index = 4)
	public List findCancelHead(Request request, boolean isOwner) { // 显示核销表头
		return checkCancelDao.findCancelHead(isOwner);
	}

	// @AuthorityFunctionAnnotation(caption = "保存数据报核", index = 5)
	public CancelHead saveCancelHead(Request request, CancelHead cancelHead)
			throws DataAccessException {
		checkCancelDao.saveCancelHead(cancelHead); // 保存核销表头
		return cancelHead;
	}

	// @AuthorityFunctionAnnotation(caption = "删除数据报核", index = 6)
	public void deleteCancelHead(Request request, CancelHead cancelHead) { // 删除核销表头
		checkCancelDao.deleteCancelHead(cancelHead);
	}

	public List findCancelCustomsDeclara(Request request,
			CancelHead cancelHead, boolean isOwner) { // 显示核销--报关单
		return checkCancelDao.findCancelCustomsDeclara(cancelHead, isOwner); // 显示核销--报关单
	}

	public CancelCustomsDeclara saveCancelCustomsDeclara(Request request,
			CancelCustomsDeclara cancelCustomsDeclara)
			throws DataAccessException {
		checkCancelDao.saveCancelCustomsDeclara(cancelCustomsDeclara); // 保存核销--报关单
		return cancelCustomsDeclara;
	}

	public void deleteCancelCustomsDeclara(Request request,
			CancelCustomsDeclara cancelCustomsDeclara) {
		checkCancelDao.deleteCancelCustomsDeclara(cancelCustomsDeclara); // 核销--删除报关单
	}

	public List findCancelImgBefore(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return checkCancelDao.findCancelImgBefore(cancelHead, isOwner); // 显示核销--料件原始
	}

	public List findCancelImgMiddle(Request request, CancelHead cancelHead) {
		return checkCancelDao.findCancelImgMiddle(cancelHead); // 显示核销--料件中间
	}

	public List findCancelImgResult(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return checkCancelDao.findCancelImgResult(cancelHead, isOwner); // 显示核销--料件结果
	}

	public List findCancelExgBefore(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return checkCancelDao.findCancelExgBefore(cancelHead, isOwner); // 显示核销--成品原始
	}

	public List findCancelExgMiddle(Request request, CancelHead cancelHead) {
		return checkCancelDao.findCancelExgMiddle(cancelHead); // 显示核销--成品中间
	}

	public List findCancelExgResult(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return checkCancelDao.findCancelExgResult(cancelHead, isOwner); // 显示核销--成品结果
	}

	/*
	 * public List findImgFromCustomsDeclarationCommInfo(Request request) {
	 * return checkCancelDao.findImgFromCustomsDeclarationCommInfo(); //获取料件 }
	 */
	/*
	 * public List findExgFromCustomsDeclarationCommInfo(Request request) {
	 * return checkCancelDao.findExgFromCustomsDeclarationCommInfo(); //获取成品 }
	 */

	public CancelImgBefore saveCancelImgBefore(Request request,
			CancelImgBefore cancelImgBefore) throws DataAccessException {
		checkCancelDao.saveCancelImgBefore(cancelImgBefore); // 保存--料件原始数据
		return cancelImgBefore;
	}

	public void getImg(Request request, CancelHead cancelHead, boolean isOwner) {
		checkCancelLogic.getImg(cancelHead, isOwner, request.getTaskId()); //
	}

	public CancelExgBefore saveCancelExgBefore(Request request,
			CancelExgBefore cancelExgBefore) throws DataAccessException {
		checkCancelDao.saveCancelExgBefore(cancelExgBefore); // 保存--成品原始数据
		return cancelExgBefore;

	}

	public void getExg(Request request, CancelHead cancelHead, boolean isOwner) {
		checkCancelLogic.getExg(cancelHead, isOwner); //
	}

	public CancelImgResult saveCancelImgResult(Request request,
			CancelImgResult cancelImgResult) throws DataAccessException {
		checkCancelDao.saveCancelImgResult(cancelImgResult); // 保存--料件结果数据
		return cancelImgResult;
	}

	public CancelExgResult saveCancelExgResult(Request request,
			CancelExgResult cancelExgResult) throws DataAccessException {
		checkCancelDao.saveCancelExgResult(cancelExgResult); // 保存--成品结果数据
		return cancelExgResult;
	}

	/**
	 * 批量删除核销料件原始/结果
	 */
	public void deleteCancelImg(Request request, CancelHead cancelHead,
			boolean isOwner) {
		checkCancelDao.deleteCancelImg(cancelHead, isOwner);
	}

	/**
	 * 批量删除核销平均单价表
	 */
	public void deleteCancelImgAvgPrice(Request request, CancelHead cancelHead,
			boolean isOwner) {
		checkCancelDao.deleteCancelImgAvgPrice(cancelHead, isOwner);
	}

	/**
	 * 删除核销单耗表
	 * 
	 * @param cancelHead
	 * @param isOwner
	 */
	public void deleteCancelUnitWear(Request request, CancelHead cancelHead,
			boolean isOwner) {
		checkCancelDao.deleteCancelUnitWear(cancelHead, isOwner);
	}

	public void deleteCancelTemp() { // 批量删除临时表所有数据
		checkCancelDao.deleteCancelTemp();
	}

	public void deleteCancelExg(Request request, CancelHead cancelHead,
			boolean isOwner) { // 批量删除核销成品原始/结果
		checkCancelDao.deleteCancelExg(cancelHead, isOwner);
	}

	public List findCancelCustomsDeclaraNum(Request request,
			CancelHead cancelHead, String inOutportFlat, boolean isOwner) { // 求报关单份数
		return checkCancelDao.findCancelCustomsDeclaraNum(cancelHead,
				inOutportFlat, isOwner);
	}

	public CancelHead fillCancelHeadData(Request request,
			CancelHead cancelHead, boolean isOwner) { // 修改核销表头（核销计算）
		return checkCancelLogic.fillCancelHeadData(cancelHead, isOwner);
	}

	public CancelTemp saveCancelTemp(Request request, CancelTemp cancelTemp)
			throws DataAccessException {
		checkCancelDao.saveCancelTemp(cancelTemp); // 保存到临时表
		return cancelTemp;
	}

	public void deleteCancelAll(Request request, CancelHead cancelHead,
			boolean isOwner) {
		checkCancelDao.deleteCancelAll(cancelHead, isOwner); // 批量删除所有表体
	}

	public List findExgCustoms(Request request) {
		return checkCancelDao.findExgCustoms();
	}

	public List findGroupImg(Request request) {
		return checkCancelDao.findGroupImg(); // 分组统计临时表
	}

	/**
	 * 得到核销料件结果
	 */
	public void getCancelImg(Request request, CancelHead cancelHead,
			EmsHeadH2k emsHeadH2k, boolean isOwner, boolean isUnitWare) {
		checkCancelLogic.getCancelImg(cancelHead, emsHeadH2k, isOwner,
				isUnitWare, request.getTaskId());
	}

	/**
	 * 得到核销料件结果
	 */
	public List getCancelImgByMonth(Request request, CancelHead cancelHead,
			EmsHeadH2k emsHeadH2k, boolean isOwner, boolean isUnitWare) {
		return checkCancelLogic.getCancelImgByMonth(cancelHead, emsHeadH2k,
				isOwner, isUnitWare, request.getTaskId());
	}

	/**
	 * 取得每月的平均单价
	 */
	public List getImgAveragePriceByMonth(Request request,
			CancelHead cancelHead, EmsHeadH2k emsHeadH2k, boolean isOwner) {
		return checkCancelLogic.getImgAveragePriceByMonth(cancelHead,
				emsHeadH2k, isOwner, request.getTaskId());
	}

	public void deleteCancelImgExgList(Request request, List ls) {
		this.checkCancelDao.deleteCancelImgExgList(ls);
	}

	public List findCancelImgAvgPriceLeave(Request request,
			CancelHead cancelHead, boolean isOwner, Date beginDate, Date endDate) {
		return this.checkCancelDao.findCancelImgAvgPrice(cancelHead, isOwner,
				beginDate, endDate);
	}

	public List findImgFromCustomsDeclarationCommInfoByResult(Request request,
			String seqNum) { // 得到料件由结果
		return checkCancelDao
				.findImgFromCustomsDeclarationCommInfoByResult(seqNum);
	}

	public CancelHead getHead(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return checkCancelDao.getHead(cancelHead, isOwner);
	}

	/**
	 * @return Returns the checkCancelDao.
	 */
	public CheckCancelDao getCheckCancelDao() {
		return checkCancelDao;
	}

	/**
	 * @param checkCancelDao
	 *            The checkCancelDao to set.
	 */
	public void setCheckCancelDao(CheckCancelDao checkCancelDao) {
		this.checkCancelDao = checkCancelDao;
	}

	/**
	 * @return Returns the checkCancelLogic.
	 */
	public CheckCancelLogic getCheckCancelLogic() {
		return checkCancelLogic;
	}

	/**
	 * @param checkCancelLogic
	 *            The checkCancelLogic to set.
	 */
	public void setCheckCancelLogic(CheckCancelLogic checkCancelLogic) {
		this.checkCancelLogic = checkCancelLogic;
	}

	// ---
	/**
	 * 返回帐分析表头
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览帐帐分析", index = 7)
	public List findEmsAnalyHead(Request request) {
		return this.emsAnalyDao.findEmsAnalyHead();
	}

	/**
	 * 得到流水号
	 */
	public Integer getNum(Request request, String className, String seqNum) {
		return this.emsAnalyDao.getNum(className, seqNum);
	}

	/**
	 * 保存核销头
	 */

	// @AuthorityFunctionAnnotation(caption = "保存帐帐分析", index = 8)
	public EmsAnalyHead SaveEmsAnalyHead(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.SaveEmsAnalyHead(head);
		return head;
	}

	/**
	 * 保存核查参数设定
	 */
	// @AuthorityFunctionAnnotation(caption = "修改参数设置", index = 9.2)
	public CheckParameter SaveCheckParameter(Request request,
			CheckParameter head) {
		this.emsAnalyDao.SaveCheckParameter(head);
		return head;
	}

	/**
	 * @return Returns the emsAnalyDao.
	 */
	public EmsAnalyDao getEmsAnalyDao() {
		return emsAnalyDao;
	}

	/**
	 * @param emsAnalyDao
	 *            The emsAnalyDao to set.
	 */
	public void setEmsAnalyDao(EmsAnalyDao emsAnalyDao) {
		this.emsAnalyDao = emsAnalyDao;
	}

	/**
	 * 返回盘点料件
	 */
	public List findEmsPdImg(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsPdImg(head);
	}

	/**
	 * 返回盘点报关料件
	 */
	public List findEmsPdImgBg(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsPdImgBg(head);
	}

	/**
	 * 返回盘点成品
	 */
	public List findEmsPdExg(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsPdExg(head);
	}

	/**
	 * 返回盘点报关成品
	 */
	public List findEmsPdExgBg(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsPdExgBg(head);
	}

	/**
	 * 返回盘点总表
	 */
	public List findEmsPdTotal(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsPdTotal(head);
	}

	/**
	 * 返回差异分析
	 */
	public List findEmsCy(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsCy(head);
	}

	/**
	 * 返回报关--总表
	 */
	public List findEmsBgTotal(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsBgTotal(head);
	}

	/**
	 * 返回报关--出口成品折算料件
	 */
	public List findEmsBgExgBg(Request request, EmsAnalyHead head, String exgId) {
		return this.emsAnalyDao.findEmsBgExgBg(head, exgId);
	}

	/**
	 * 返回报关--出口成品
	 */
	public List findEmsBgExg(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsBgExg(head);
	}

	/**
	 * 返回报关--进口料件
	 */
	public List findEmsBgImg(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsBgImg(head);
	}

	/**
	 * 获得物料主档
	 */
	public List findMateriel(Request request, EmsAnalyHead head, String type) {
		return this.emsAnalyDao.findMateriel(head, type);
	}

	/**
	 * 保存盘点料件
	 */
	public EmsPdImg SaveEmsPdImg(Request request, EmsPdImg obj) {
		this.emsAnalyDao.SaveEmsPdImg(obj);
		return obj;
	}

	/**
	 * 保存盘点成品
	 */
	public EmsPdExg SaveEmsPdExg(Request request, EmsPdExg obj) {
		this.emsAnalyDao.SaveEmsPdExg(obj);
		return obj;
	}

	/**
	 * @return Returns the emsAnalyLogic.
	 */
	public EmsAnalyLogic getEmsAnalyLogic() {
		return emsAnalyLogic;
	}

	/**
	 * @param emsAnalyLogic
	 *            The emsAnalyLogic to set.
	 */
	public void setEmsAnalyLogic(EmsAnalyLogic emsAnalyLogic) {
		this.emsAnalyLogic = emsAnalyLogic;
	}

	/**
	 * 组织HQL报表条件公共查询
	 * 
	 * @param className
	 * @param conditions
	 * @return
	 */
	public List commonSearch(Request request, String selects, String className,
			List conditions, String groupBy) {
		return this.emsAnalyDao.commonSearch(selects, className, conditions,
				groupBy);
	}

	/**
	 * 删除报关料件进口
	 */
	public void deleteEmsBgImgAll(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsBgImgAll(head);
	}

	// 报关进口料件查询
	public List calculateBgImg(Request request, List conditions, EmsAnalyHead head) {
		return this.emsAnalyLogic.calculateBgImg(conditions, head);
	}

	/**
	 * 删除报关成品折料
	 */
	public void deleteEmsBgExgImgAll(Request request, EmsAnalyHead head,
			String emsBgExgId, boolean isAllOrSinlg) {
		this.emsAnalyDao.deleteEmsBgExgImgAll(head, emsBgExgId, isAllOrSinlg);
	}

	/**
	 * 删除报关成品出口
	 */
	public void deleteEmsBgExgAll(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsBgExgAll(head);
	}

	// 报关出口成品查询
	public List calculateBgExg(Request request, List conditions, EmsAnalyHead head) {
		return this.emsAnalyLogic.calculateBgExg(conditions, head);
	}

	/**
	 * 保存报关料件
	 */
	public EmsBgImg SaveEmsBgImg(Request request, EmsBgImg obj) {
		this.emsAnalyDao.SaveEmsBgImg(obj);
		return obj;
	}

	/**
	 * 保存报关成品
	 */
	public EmsBgExg SaveEmsBgExg(Request request, EmsBgExg obj) {
		this.emsAnalyDao.SaveEmsBgExg(obj);
		return obj;
	}

	/**
	 * 保存报关成品折算料件
	 */
	public EmsBgExgBg SaveEmsBgExgImg(Request request, EmsBgExgBg obj) {
		this.emsAnalyDao.SaveEmsBgExgImg(obj);
		return obj;
	}

	/**
	 * 报关数据分析--- 成品进出口---报关成品折料
	 * 
	 * @param head
	 * @return
	 */
	public List convertBgExgToImg(Request request, EmsAnalyHead head, String emsBgExgId,
			boolean isAllOrSing) {
		return this.emsAnalyLogic.convertBgExgToImg(head, emsBgExgId, isAllOrSing);
	}
	
	public void convertBgExgToImg(Request request, EmsAnalyHead head){
		this.emsAnalyLogic.convertBgExgToImg(head);
	}

	/**
	 * 盘点数据分析 ---成品折料-- 盘点成品折料
	 * 
	 * @param head
	 */
	public List convertPdExgBgToPdExgImgBg(Request request, EmsAnalyHead head,List list) {
		return this.emsAnalyLogic.convertPdExgBgToPdExgImgBg(head,list);
	}

	/**
	 * 保存报关总和
	 */
	public EmsBgTotal SaveEmsBgTotal(Request request, EmsBgTotal obj) {
		this.emsAnalyDao.SaveEmsBgTotal(obj);
		return obj;
	}

	// 报关进出口总数
	public List findBgExgImg(Request request, EmsAnalyHead head) {
		return this.emsAnalyLogic.findBgExgImg(head);
	}

	/**
	 * 删除报关进出口总表
	 */
	// @AuthorityFunctionAnnotation(caption = "删除帐帐分析", index = 9)
	public void deleteEmsBgTotal(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsBgTotal(head);
	}

//	/**
//	 * 盘点得到报关料件来自内部归并
//	 */
//	public List findBgImgFromInnerMerger(Request request, String type,
//			String ptNo) {
//		return this.emsAnalyDao.findBgImgFromInnerMerger(type, ptNo);
//	}

	/**
	 * 盘点资料转换为报关料件资料
	 */
	public List<String> convertPdImgBg(Request request, EmsAnalyHead head, List<EmsPdImg> imgList) {
		return this.emsAnalyLogic.convertPdImgBg(head,  imgList);
	}

	/**
	 * 删除盘点料件报关
	 */
	public void deletePdImgBg(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deletePdImgBg(head);
	}

	/**
	 * 删除盘点成品报关
	 */
	public void deletePdExgBg(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deletePdExgBg(head);
	}

	/**
	 * 盘点返回报关成品资料
	 */
	public void findPdExg(Request request, EmsAnalyHead head,boolean isMaxVersion,EmsPdExg exg ) {
		this.emsAnalyLogic.findPdExg(head,isMaxVersion, exg );
	}

	/**
	 * 保存报关料件
	 */
	public EmsPdImgBg SaveEmsPdImgBg(Request request, EmsPdImgBg obj) {
		this.emsAnalyDao.SaveEmsPdImgBg(obj);
		return obj;
	}

	/**
	 * 保存报关成品
	 */
	public EmsPdExgBg SaveEmsPdExgBg(Request request, EmsPdExgBg obj) {
		this.emsAnalyDao.SaveEmsPdExgBg(obj);
		return obj;
	}

	/**
	 * 删除折料成品料件
	 */
	public void deleteEmsPdExgImgBgAll(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsPdExgImgBgAll(head);
	}

	/**
	 * 返回盘点报关折料料件
	 * 
	 */
	public List findEmsExgImgBg(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsExgImgBg(head);
	}

	/**
	 * 返回报关--所有出口成品折算料件
	 */
	public List findEmsBgExgBgAll(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsBgExgBgAll(head);
	}

	/**
	 * 保存盘点成品折料
	 */
	public EmsPdExgImgBg SaveEmsPdExgImgBg(Request request, EmsPdExgImgBg obj) {
		this.emsAnalyDao.SaveEmsPdExgImgBg(obj);
		return obj;
	}

	/**
	 * 保存盘点分析总表
	 */
	public EmsPdTotal SaveEmsPdTotal(Request request, EmsPdTotal obj) {
		this.emsAnalyDao.SaveEmsPdTotal(obj);
		return obj;
	}

	// 盘点分析总表
	public List findPdExgImg(Request request, EmsAnalyHead head) {
		return this.emsAnalyLogic.findPdExgImg(head);
	}

	/**
	 * 清空盘点分析总表
	 */
	public void deleteEmsPdTotalAll(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsPdTotalAll(head);
	}

	/**
	 * 清空差异分析总表
	 */
	public void deleteEmsCyAll(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsCyAll(head);
	}

	/**
	 * 保存差异
	 */
	public EmsCy SaveEmsCy(Request request, EmsCy obj) {
		this.emsAnalyDao.SaveEmsCy(obj);
		return obj;
	}

	// 差异分析总表
	public List<EmsCy> calculateEmsCy(Request request, EmsAnalyHead head) {
		return this.emsAnalyLogic.calculateEmsCy(head);
	}

	/**
	 * 删除盘点料件
	 */
	public void deleteEmsPdImgAll(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsPdImgAll(head);
	}

	/**
	 * 删除盘点成品
	 */
	public void deleteEmsPdExgAll(Request request, EmsAnalyHead head) {
		this.emsAnalyDao.deleteEmsPdExgAll(head);
	}

	/**
	 * 删除任何实体
	 * @param request
	 * @param obj
	 */
	public void deleteObject(Request request,Object obj) {
		emsAnalyDao.delete(obj);
	}
	/**
	 * 删除核销期表头
	 */
	public void deleteEmsAnalyHead(Request request, EmsAnalyHead obj) {
		this.emsAnalyDao.deleteEmsAnalyHead(obj);
	}

	/**
	 * 返回所有盘点报关成品
	 */
	public List findEmsPdExgBgAll(Request request, EmsAnalyHead head) {
		return this.emsAnalyDao.findEmsPdExgBgAll(head);
	}

	// 获取报关单
	public List getCustomsToCheckHead(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return checkCancelLogic.getCustomsToCheckHead(cancelHead, isOwner);
	}

	// 新增报关单
	public List findCustomsDeclaration(Request request, CancelHead cancelHead,
			boolean isOwner,Date beginDate,Date endDate,String customsDecCode) {
		return this.checkCancelDao.findCustomsDeclaration(cancelHead, isOwner,beginDate,endDate,customsDecCode);
	}

	/**
	 * 预报核报表
	 */
	public List getCustomsIEToTemp(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return this.checkCancelLogic.getCustomsIEToTemp(cancelHead, isOwner);
	}

	/**
	 * 计算核销表头信息
	 * 
	 * @param request
	 * @param cancelHead
	 * @return
	 */
	public CancelHead modityCancelHead(Request request, CancelHead cancelHead,
			boolean isOwner) {
		return this.checkCancelLogic.modityCancelHead(cancelHead, isOwner);
	}

	/**
	 * 返回核销类型
	 */
	public List findCancelHeadByType(Request request, String declareType,
			boolean isOwner) {
		return this.checkCancelDao.findCancelHeadByType(declareType, isOwner);
	}

	/**
	 * 返回核销状态
	 */
	public List findCancelHeadByState(Request request, String declareState,
			boolean isOwner) {
		return this.checkCancelDao.findCancelHeadByState(declareState, isOwner);
	}

	// 重新获取得到料件核算结果
	public List getCancelExg(Request request, CancelHead cancelHead,
			EmsHeadH2k emsHeadH2k, boolean isOwner) {
		return this.checkCancelLogic.getCancelExg(cancelHead, emsHeadH2k,
				isOwner);
	}

	// 同步核销表
	public void synCancel(Request request, CancelOwnerHead head) {
		this.checkCancelLogic.synCancel(head);
	}

	/**
	 * 取得自用核销次
	 * 
	 * @return
	 */
	public Integer getCheckTimes(Request request) {
		return this.checkCancelDao.getCheckTimes();
	}

	public List findCheckOwnerHead(Request request) {
		return this.checkCancelDao.findCheckOwnerHead();
	}

	// 获取数据报核销
	public void getCancel(Request request, String times) {
		this.checkCancelLogic.getCancel(times);
	}

	// 新增料件盘点
	public List addEmsLjPd(Request request, List list, EmsAnalyHead head) {
		return this.emsAnalyLogic.addEmsLjPd(list, head);
	}

	// 新增料件盘点
	public List addEmsCpPd(Request request, List list, EmsAnalyHead head) {
		return this.emsAnalyLogic.addEmsCpPd(list, head);
	}

	/**
	 * 返回核查参数设定
	 * 
	 * @return
	 */
	 @AuthorityFunctionAnnotation(caption = "中期核查--参数设置", index = 5)
	public List findCheckParameter(Request request) {
		return this.emsAnalyDao.findCheckParameter();
	}

	/**
	 * 返回工厂盘点料件
	 */
	public List findFactoryCheckImg(Request request, CheckParameter head) {
		return this.checkCancelDao.findFactoryCheckImg(head);
	}

	/**
	 * 返回工厂盘点成品
	 */
	public List findFactoryCheckExg(Request request, CheckParameter head) {
		return this.checkCancelDao.findFactoryCheckExg(head);
	}

	/**
	 * 用作权限控制
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "中期核查--工厂盘点", index = 5.1)
	public void controlFactoryCehck(Request request) {

	}

	/**
	 * 返回工厂盘点成品折算料件
	 */
	public List findFactoryCheckExgConverImg(Request request,
			CheckParameter head) {
		return this.checkCancelDao.findFactoryCheckExgConverImg(head);
	}

	/**
	 * 新增工厂盘点料件
	 * 
	 * @param list
	 * @param head
	 * @return
	 */
	public List getFactoryCheckImg(Request request, List list,
			CheckParameter head) {
		return this.checkCancelLogic.getFactoryCheckImg(list, head);
	}

	/**
	 * 新增工厂盘点成品
	 * 
	 * @param list
	 * @param head
	 * @return
	 */
	public List getFactoryCheckExg(Request request, List list,
			CheckParameter head) {
		return this.checkCancelLogic.getFactoryCheckExg(list, head);
	}

	/**
	 * 原材料汇总
	 */
	public List totalMateriel(Request request, List imgList, List convertImgList) {
		return this.checkCancelLogic.totalMateriel(imgList, convertImgList);
	}

	/**
	 * 成品折算为料件
	 */
	public List factoryExgConvertImg(Request request, List list) {
		return this.checkCancelLogic.factoryExgConvertImg(list);
	}

	/**
	 * 返回中期核查计算表
	 * 
	 * @return
	 */
	 @AuthorityFunctionAnnotation(caption = "中期核查--自用中期核查计算表", index = 5.2)
	public List findCheckOwnerAccount(Request request, CheckParameter head) {
		return this.checkCancelDao.findCheckOwnerAccount(head);
	}

	 @AuthorityFunctionAnnotation(caption = "中期核查--自用中期核查计算表", index = 5.2)
		public void checkfindCheckOwnerAccountnew(Request request) {
		// TODO Auto-generated method stub
		
	}
	 
	/**
	 * 权限控制
	 */
	 @AuthorityFunctionAnnotation(caption = "中期核查--浏览中期核查", index = 5.3)
	public void controlCheckHead(Request request) {

	}

	/**
	 * 返回中期核查工厂盘点料件汇总
	 */

	public List findFactoryCheckImgCollect(Request request, CheckParameter head) {
		return this.checkCancelDao.findFactoryCheckImgCollect(head);
	}

	/**
	 * 删除所有中期核查计算表
	 * 
	 * @param head
	 */
	public void deleteCheckOwnerAccount(Request request, CheckParameter head) {
		this.checkCancelDao.deleteCheckOwnerAccount(head);
	}

	/**
	 * 删除工厂盘点成品折算料件
	 * 
	 * @param ptNo
	 * @return
	 */
	public void deleteFactoryCheckExgConverImg(Request request,
			CheckParameter head) {
		this.checkCancelDao.deleteFactoryCheckExgConverImg(head);
	}

	public void deleteFactoryCheckImgForBg(Request request, CheckParameter head) {
		this.checkCancelDao.deleteFactoryCheckImgForBg(head);
	}

	public List findFactoryCheckImgForBgAll(Request request, CheckParameter head) {
		return this.checkCancelDao.findFactoryCheckImgForBgAll(head);
	}

	/**
	 * 计算核查计算表
	 */
	public List accountCheckAccount(Request request, CheckParameter head) {
		return this.checkCancelLogic.accountCheckAccount(head);
	}

	public void deleteTotalImg(Request request, CheckParameter head) {
		this.checkCancelDao.deleteTotalImg(head);
	}

	/**
	 * 返回中期核查计算表对比
	 * 
	 * @return
	 */
	public List findCheckOwnerAccountComport(Request request,
			CheckParameter head) {
		return this.checkCancelDao.findCheckOwnerAccountComport(head);
	}

	public void deleteCheckOwnerAccountComport(Request request,
			CheckParameter head) {
		this.checkCancelDao.deleteCheckOwnerAccountComport(head);
	}

	public void deleteCheckImgBgComport(Request request, CheckParameter head) {
		this.checkCancelDao.deleteCheckImgBgComport(head);
	}

	// 对比表
	public List getAccountComport(Request request, List accountList,
			CheckParameter head) {
		return this.checkCancelLogic.getAccountComport(accountList, head);
	}

	// 同步到核查表中
	public void newToCheckHead(Request request, CheckParameter head,
			EmsHeadH2k emsHeadH2k) {
		this.checkCancelLogic.newToCheckHead(head, emsHeadH2k);
	}

	public List findAllColor(Request request) {
		return this.checkCancelDao.findAllColor();
	}

	public ColorSet findColorSet(Request request, int color) {
		return this.checkCancelDao.findColorSet(color);
	}

	public void saveColorSet(Request request, ColorSet obj) {
		this.checkCancelDao.saveColorSet(obj);
	}

	public ColorSet findColorByNum(Request request, Double num) {
		return this.checkCancelDao.findColorByNum(num);
	}

	public CheckOwnerAccountComport saveCheckOwnerAccountComport(
			Request request, CheckOwnerAccountComport obj) {
		this.checkCancelDao.saveCheckOwnerAccountComport(obj);
		return obj;
	}

	// 明门成品折算料件所用

	public List findFactoryStoryProduct(Request request) {
		return this.checkCancelDao.findFactoryStoryProduct();
	}

	public List findEmsHeadH2kVersionByFactoryStoryProductPtNo(Request request,
			String ptNo) {
		return checkCancelLogic
				.findEmsHeadH2kVersionByFactoryStoryProductPtNo(ptNo);
	}

	public List findEmsHeadH2kBom(Request request, EmsHeadH2kVersion version,
			Double amountPrice) {
		return this.checkCancelLogic.findEmsHeadH2kBom(version, amountPrice);
	}

	public void saveOrUpdateFactoryStoryProduct(Request request, List datalist) {
		this.checkCancelLogic.saveOrUpdateFactoryStoryProduct(datalist);
	}

	public void delFactoryStoryProduct(Request request, List datalist) {
		this.checkCancelLogic.delFactoryStoryProduct(datalist);
	}

	public List findAllEmsHeadH2kExgVersion(Request request) {
		return this.checkCancelLogic.findAllEmsHeadH2kExgVersion();
	}

	public List findTempEmsHeadH2kBomAnyle(Request request, List list) {
		return this.checkCancelLogic.findTempEmsHeadH2kBomAnyle(list);
	}

	public List importDataToFactoryCheck(Request request, List list,
			String materielType, CheckParameter head) {
		return this.emsAnalyLogic.importDataToFactoryCheck(list, materielType,
				head);
	}

	public void saveDataToFactoryCheck(Request request, List list,
			CheckParameter head, String materielType) {
		this.emsAnalyLogic.saveDataToFactoryCheck(list, head, materielType);
	}

	public List importDataToCheck(Request request, List list, CheckHead head) {
		return this.emsAnalyLogic.importDataToCheck(list, head);
	}

	public void saveDataToCheck(Request request, List list, CheckHead head) {
		this.emsAnalyLogic.saveDataToCheck(list, head);
	}

	public void deleteCheckParameter(Request request, CheckParameter obj) {
		this.checkCancelDao.deleteCheckParameter(obj);
	}

	public void deleteList(Request request, List list) {
		this.checkCancelDao.deleteList(list);
	}

	public List convertBgImg(Request request, List list, CheckParameter head) {
		return emsAnalyLogic.convertBgImg(list, head);
	}

	public List importImgForBg(Request request, CheckParameter head) {
		return emsAnalyLogic.importImgForBg(head);
	}

	public List findCheckBgCy(Request request, CheckParameter head) {
		return this.checkCancelDao.findCheckBgCy(head);
	}

	public List findFactoryCheckBgCy(Request request, CheckParameter head) {
		return this.emsAnalyLogic.findFactoryCheckBgCy(head);
	}

	/**
	 * 显示核销单耗
	 */
	public List findCancelUnitWear(Request request, CancelHead head,
			boolean isOwner) {
		return this.checkCancelDao.findCancelUnitWear(head, isOwner);
	}

	/**
	 * 查看所有盘点成品未归并
	 * 
	 * @param head
	 * @return
	 */
	public List findAllEmsPdExgNoEms(Request request, EmsAnalyHead head) {
		return emsAnalyDao.findAllEmsPdExgNoEms(head);
	}

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
			Object value, Boolean isLike) {
		return this.checkCancelDao.findCancelUnitWear(head, isOwner, index,
				length, property, value, isLike);
	}

	public CancelHead findMaxCancelTimesCancelHead(Request request,
			boolean isOwner, String declareType) {
		return this.checkCancelDao.findMaxCancelTimesCancelHead(isOwner,
				declareType);
	}

	/**
	 * 回写报关单是否核销栏位
	 */
	public void makeCustomsDeclarationisEmsCav(Request repuest,
			CancelHead cancelHead, boolean emscav) {
		this.emsAnalyLogic.makeCustomsDeclarationisEmsCav(cancelHead, emscav);

	}
	
	public List findEmsHeadH2k(Request request){
		return checkCancelDao.findEmsHeadH2k();
	}
	/**
	 * 检查核销表栏位
	 */
	public List checkCancelData(Request request,CancelHead cancelHead){
		return this.checkCancelLogic.checkCancelData(cancelHead);
	}

	/* (non-Javadoc)
	 * @see com.bestway.bcus.checkcancel.action.CheckCancelAction#importEmsTransFactory(com.bestway.common.Request, java.util.List)
	 */
	@Override
	public List<EmsTransFactory> importEmsTransFactory(Request request, EmsAnalyHead head,
			List<EmsTransFactory> list) {
		return emsAnalyLogic.importEmsTransFactory(head, list);
	}

	/* (non-Javadoc)
	 * @see com.bestway.bcus.checkcancel.action.CheckCancelAction#deleteEmsTransFactory(java.util.List)
	 */
	@Override
	public void deleteEmsTransFactory(Request request, List<EmsTransFactory> list) {
		emsAnalyLogic.deleteEmsTransFactory(list);
	}
	
	public List<EmsTransFactoryBg> convertEmsTransFactoryToBg(Request request, EmsAnalyHead head,
			List<EmsTransFactory> list) {
		return emsAnalyLogic.convertEmsTransFactoryToBg(head, list);
	}
	
	public List<EmsTransFactory> findEmsTransFactory(Request request, EmsAnalyHead head) {
		return emsAnalyDao.findEmsTransFactory(head);
	}
	
	public List<EmsTransFactoryBg> findEmsTransFactoryBg(Request request, EmsAnalyHead head) {
		return emsAnalyDao.findEmsTransFactoryBg(head);
	}
	
	public void deleteEmsTransFactoryBg(Request request, EmsAnalyHead head) {
		emsAnalyLogic.deleteEmsTransFactoryBg(head);
	}
	public FactoryCheckImg saveFactoryCheckImg(Request request,FactoryCheckImg factoryCheckImg){
		checkCancelDao.saveFactoryCheckImg(factoryCheckImg);
		return factoryCheckImg;
	}
	public FactoryCheckExg saveFactoryCheckExg(Request request,FactoryCheckExg factoryCheckExg){
		checkCancelDao.saveFactoryCheckExg(factoryCheckExg);
		return factoryCheckExg;
	}
}
