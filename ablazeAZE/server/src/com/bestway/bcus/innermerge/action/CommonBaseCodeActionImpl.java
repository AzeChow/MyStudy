/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.action;

// 李扬更改程序
import java.awt.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.CustomInnerMergeCondition;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.InnerMergeDataOrder;
import com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.bcus.innermerge.entity.TempAutoInnerMergeParam;
import com.bestway.bcus.innermerge.logic.CommonBaseCodeLogic;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
// 企业内部归并
@AuthorityClassAnnotation(caption = "电子帐册", index = 6)
public class CommonBaseCodeActionImpl extends BaseActionImpl implements
		CommonBaseCodeAction {
	private CommonBaseCodeDao commonBaseCodeDao = null;

	private CommonBaseCodeLogic commonBaseCodeLogic = null;

	public CommonBaseCodeActionImpl() {
		this.setModuleName("InnerMerge");
	}

	public CommonBaseCodeLogic getCommonBaseCodeLogic() {
		return commonBaseCodeLogic;
	}

	public void setCommonBaseCodeLogic(CommonBaseCodeLogic commonBaseCodeLogic) {
		this.commonBaseCodeLogic = commonBaseCodeLogic;
	}

	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMateriel(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-物料主档", index = 1.1)
	public List findMateriel(Request request) {
		return commonBaseCodeDao.findMateriel(); // 显示物料主档所有数据
	}

	/**
	 * 浏览内部归并资料
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeDataByType10(Request request, String type,
			EmsEdiTrHead emsEdiTrHead, EmsEdiMergerHead emsEdiMergerHead,
			String helfType) {
		return commonBaseCodeDao.findInnerMergeDataByType10(type, emsEdiTrHead,
				emsEdiMergerHead, helfType); // 得到十码
	}

	/**
	 * 分页浏览内部料件归并资料
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeDataAfterImgByType10(Request request,
			String type, Integer seqNum, EmsEdiMergerHead emsEdiMergerHead,
			int index, int length, String property, Object value, Boolean isLike) { // 得到归并前
		return commonBaseCodeDao.findInnerMergeDataAfterImgByType10(type,
				seqNum, emsEdiMergerHead, index, length, property, value,
				isLike);
	}

	/**
	 * 分页浏览内部归并成品资料
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeDataAfterExgByType10(Request request,
			String type, Integer seqNum, EmsEdiMergerHead emsEdiMergerHead,
			int index, int length, String property, Object value, Boolean isLike) { // 得到归并前
		return commonBaseCodeDao.findInnerMergeDataAfterExgByType10(type,
				seqNum, emsEdiMergerHead, index, length, property, value,
				isLike);
	}

	/*
	 * 内部归并
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeDataByType
	 * (com.bestway.common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeDataByType(Request request, String type) {
		return this.commonBaseCodeLogic.findInnerMergeDataByType(type);
	}

	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeDataByType(Request request, String type,
			int index, int length, String property, Object value,
			boolean isLike, boolean isShowNoInner) {
		return this.commonBaseCodeDao.findInnerMergeDataByType(type, index,
				length, property, value, isLike, isShowNoInner);
	}

	public List findInnerMergeDataByTypeCount(Request request, String type,
			int index, int length, String property, Object value,
			boolean isLike, boolean isShowNoInner) {
		return this.commonBaseCodeDao.findInnerMergeDataByTypeCount(type,
				index, length, property, value, isLike, isShowNoInner);
	}

	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeDataByTypeNoMerger(Request request, String type) {
		return this.commonBaseCodeDao.findInnerMergeDataByTypeNoMerger(type);
	}

	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeDataByTypeSomeInMerger(Request request,
			String type) {
		return this.commonBaseCodeLogic
				.findInnerMergeDataByTypeSomeInMerger(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeData(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeData(Request request) {
		return this.commonBaseCodeLogic.findInnerMergeData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeReportData
	 * (com.bestway.common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public List findInnerMergeReportData(Request request, String type) {
		return this.commonBaseCodeLogic.findInnerMergeReportData(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeDataById(com
	 * .bestway.common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-浏览", index = 1.1)
	public InnerMergeData findInnerMergeDataById(Request request, String id) {
		return this.commonBaseCodeLogic.findInnerMergeDataById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#saveInnerMergeData(com.bestway
	 * .common.Request, com.bestway.bcus.innermerge.entity.InnerMergeData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public InnerMergeData saveInnerMergeData(Request request,
			InnerMergeData innerMergeData) {
		return this.commonBaseCodeLogic.saveInnerMergeData(innerMergeData);
	}

	public void handleEmsEdiMergerExgBefore(List list,
			EmsEdiMergerExgAfter emsExgAfter, BaseCompany company,
			String isSendSign, EmsEdiMergerHead emsEdiMergerHead) {
		this.commonBaseCodeLogic.handleEmsEdiMergerExgBefore(list, emsExgAfter,
				company, isSendSign, emsEdiMergerHead);
	}

	
	public void handleEmsEdiMergerImgBefore(List list,
			EmsEdiMergerImgAfter emsImgAfter, BaseCompany company,
			String isSendSign, EmsEdiMergerHead emsEdiMergerHead) {
		this.commonBaseCodeLogic.handleEmsEdiMergerImgBefore(list, emsImgAfter,
				company, isSendSign, emsEdiMergerHead);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#saveInnerMergeData(com.bestway
	 * .common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public List saveInnerMergeData(Request request,
			List<InnerMergeData> listInnerMergeData) {
		this.commonBaseCodeDao.saveInnerMergeData(listInnerMergeData);
		return listInnerMergeData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteInnerMergeData(com
	 * .bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.InnerMergeData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-删除", index = 1.3)
	public void deleteInnerMergeData(Request request,
			InnerMergeData innerMergeData) {
		this.commonBaseCodeLogic.deleteInnerMergeData(innerMergeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#importDataFromMaterial(com
	 * .bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void importDataFromMaterial(Request request, List materielTypeList) {
		this.commonBaseCodeLogic.importDataFromMaterial(materielTypeList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkDataForTenMerge(com
	 * .bestway.common.Request, java.util.List)
	 */
	public int checkDataForTenMerge(Request request, List list) {
		return this.commonBaseCodeLogic.checkDataForTenMerge(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#tenInnerMerge(com.bestway
	 * .common.Request, java.util.List,
	 * com.bestway.bcus.custombase.entity.hscode.Complex,
	 * com.bestway.bcus.custombase.entity.parametercode.Unit, java.lang.String,
	 * java.lang.String, com.bestway.bcus.custombase.entity.parametercode.Unit,
	 * com.bestway.bcus.custombase.entity.parametercode.Unit, boolean)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public List tenInnerMerge(Request request, List list, Complex afterComplex,
			Unit afterMemoUnit, String afterTenMaterielname,
			String afterTenMaterielSpec, Unit afterLegalUnit,
			Unit afterSecondLegalUnit, boolean isNew) {
		this.commonBaseCodeLogic.tenInnerMerge(list, afterComplex,
				afterMemoUnit, afterTenMaterielname, afterTenMaterielSpec,
				afterLegalUnit, afterSecondLegalUnit, isNew);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#unDoTenInnerMerge(com.bestway
	 * .common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public List unDoTenInnerMerge(Request request, List list) {
		this.commonBaseCodeLogic.unDoTenInnerMerge(list);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#fourInnerMerge(com.bestway
	 * .common.Request, java.util.List, java.lang.String, boolean)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public List fourInnerMerge(Request request, List list,
			String fourCommodityName, boolean isNew) {
		this.getCommonBaseCodeLogic().fourInnerMerge(list, fourCommodityName,
				isNew);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#undoFourInnerMerge(com.bestway
	 * .common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public List undoFourInnerMerge(Request request, List list) {
		this.getCommonBaseCodeLogic().undoFourInnerMerge(list);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#reSortMergeFourNo(com.bestway
	 * .common.Request, java.util.List, int)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void reSortMergeFourNo(Request request, List selectedRows, int toNo) {
		this.getCommonBaseCodeLogic().reSortMergeFourNo2(selectedRows, toNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#reSortMergeTenNo(com.bestway
	 * .common.Request, java.util.List, int)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void reSortMergeTenNo(Request request, List selectedRows, int toNo) {
		this.getCommonBaseCodeLogic().reSortMergeTenNo2(selectedRows, toNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#autoInnerMergeData(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void autoInnerMergeData(Request request,
			TempAutoInnerMergeParam param) {
		this.getCommonBaseCodeLogic().autoInnerMergeData(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#saveCustomInnerMergeCondition
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.CustomInnerMergeCondition)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void saveCustomInnerMergeCondition(Request request,
			CustomInnerMergeCondition condition) {
		this.getCommonBaseCodeDao().saveCustomInnerMergeCondition(condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteCustomInnerMergeCondition
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.CustomInnerMergeCondition)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void deleteCustomInnerMergeCondition(Request request,
			CustomInnerMergeCondition condition) {
		this.getCommonBaseCodeDao().deleteCustomInnerMergeCondition(condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findCustomInnerMergeConditionByType(com.bestway.common.Request,
	 * java.lang.String)
	 */
	public List findCustomInnerMergeConditionByType(Request request, String type) {
		return this.getCommonBaseCodeDao().findCustomInnerMergeConditionByType(
				type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findCustomInnerMergeCondition
	 * (com.bestway.common.Request)
	 */
	public List findCustomInnerMergeCondition(Request request) {
		return this.getCommonBaseCodeDao().findCustomInnerMergeCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#customInnerMerge(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void customInnerMerge(Request request) {
		this.getCommonBaseCodeLogic().customInnerMerge();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#importDataFromTxtFile(com
	 * .bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void importDataFromTxtFile(Request request, List list) {
		this.getCommonBaseCodeLogic().importDataFromTxtFile(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkFileData(com.bestway
	 * .common.Request, java.util.List, java.util.Hashtable)
	 */
	public List checkFileData(Request request, List list, Hashtable ht,
			String materielType) {
		return this.getCommonBaseCodeLogic().checkFileData(list, ht,
				materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteInnerMergeData(com
	 * .bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void deleteInnerMergeData(Request request, List selectedList) {
		if (selectedList == null) {
			return;
		}
		this.getCommonBaseCodeLogic().deleteInnerMergeData(selectedList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findMaterielByImpExpRequestBillType(com.bestway.common.Request,
	 * java.lang.String, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-物料主档", index = 1.1)
	public List findMaterielByImpExpRequestBillType(Request request,
			String materielType, String impExpRequestBillId, boolean isFilter) {
		return this.getCommonBaseCodeLogic()
				.findMaterielByImpExpRequestBillType(materielType,
						impExpRequestBillId, isFilter);
	}

	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-物料主档", index = 1.1)
	public List findMaterielByMaterielPara(Request request,
			String materielType, String impExpRequestBillId, boolean isFilter,
			String value, String para) {

		return this.getCommonBaseCodeLogic().findMaterielByMaterielPara(
				materielType, impExpRequestBillId, isFilter, value, para);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findMaterielByCustomsEnvelopRequestBillType(com.bestway.common.Request,
	 * java.lang.String, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-物料主档", index = 1.1)
	public List findMaterielByCustomsEnvelopRequestBillType(Request request,
			String materielType, String customsEnvelopRequestId) {
		return this.getCommonBaseCodeLogic()
				.findMaterielByCustomsEnvelopRequestBillType(materielType,
						customsEnvelopRequestId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findMaterielByTransferFactoryBillType(com.bestway.common.Request,
	 * java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-物料主档", index = 1.1)
	public List findMaterielByTransferFactoryBillType(Request request,
			String materielType) {
		return this.getCommonBaseCodeLogic()
				.findMaterielByTransferFactoryBillType(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkMaterielPutOnRecordsByImpExpRequestCommodity
	 * (com.bestway.common.Request, java.util.List, boolean)
	 */
	public List checkMaterielPutOnRecordsByImpExpRequestCommodity(
			Request request, List dataSource, boolean isPutOnRecord) {
		return this.getCommonBaseCodeDao()
				.checkMaterielPutOnRecordsByImpExpRequestCommodity(dataSource,
						isPutOnRecord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkMaterielNotInMerge(
	 * java.util.List)
	 */
	public List checkMaterielNotInMerge(List dataSource) {
		return this.getCommonBaseCodeDao().checkMaterielNotInMerge(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findMaterielByTransferFactoryInitBillType(com.bestway.common.Request,
	 * java.lang.String, java.lang.String)
	 */
	public List findMaterielByTransferFactoryInitBillType(Request request,
			String materielType, String initBillId) {
		return this.commonBaseCodeLogic
				.findMaterielByTransferFactoryInitBillType(materielType,
						initBillId);
	}

	// /**
	// * 返回产品结构管理
	// */
	// public List findMaterBomManage(Request request, String materielType) {
	// return commonBaseCodeDao.findMaterBomManage(materielType);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMaterielFromBomManage
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public void findMaterielFromBomManage(Request request, String materielType) {
		// commonBaseCodeLogic.findMaterielFromBomManage(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findBomImg(com.bestway.common
	 * .Request, java.lang.String)
	 */
	public List findBomImg(Request request, String materielType) {
		return commonBaseCodeDao.findBomImg(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findBomAndInMerger(com.bestway
	 * .common.Request, com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead,
	 * java.lang.String, java.lang.String)
	 */
	public List findBomAndInMerger(Request request, EmsEdiMergerHead head,
			String ptNo, String materielType) {
		return commonBaseCodeDao.findBomAndInMerger(head, ptNo, materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findReverseMergeFourDataByType
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public List findReverseMergeFourDataByType(Request request,
			String materielType) {
		return this.commonBaseCodeLogic
				.findReverseMergeFourDataByType(materielType);
	}

	/**
	 * 把归并关系中的第三层资料插入ReverseMergeFourData类中
	 */
	public void addInnerMergeDataToReverseMergeFourData(Request request,
			String materielType) {
		this.commonBaseCodeLogic
				.addInnerMergeDataToReverseMergeFourData(materielType);
	}

	/**
	 * 把归并关系中的第二层资料插入ReverseMergeTenData类中
	 */
	public void addInnerMergeDataToReverseMergeTenData(Request request,
			String materielType) {
		this.commonBaseCodeLogic
				.addInnerMergeDataToReverseMergeTenData(materielType);
	}

	/**
	 * 把归并关系中的第一层资料插入ReverseMergeBeforeData类中
	 */
	public void addInnerMergeDataToReverseMergeBeforeData(Request request,
			String materielType) {
		this.commonBaseCodeLogic
				.addInnerMergeDataToReverseMergeBeforeData(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#saveReverseMergeFourData
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeFourData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public ReverseMergeFourData saveReverseMergeFourData(Request request,
			ReverseMergeFourData fourData) {
		commonBaseCodeLogic.saveReverseMergeFourData(fourData);
		return fourData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteReverseMergeFourData
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeFourData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-删除", index = 1.3)
	public void deleteReverseMergeFourData(Request request,
			ReverseMergeFourData fourData) {
		this.commonBaseCodeLogic.deleteReverseMergeFourData(fourData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findReverseMergeTenDataByFour
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeFourData)
	 */
	public List findReverseMergeTenDataByFour(Request request,
			ReverseMergeFourData fourData) {
		return this.commonBaseCodeLogic.findReverseMergeTenDataByFour(fourData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findReverseMergeTenDataByType
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public List findReverseMergeTenDataByType(Request request,
			String materielType) {
		return this.commonBaseCodeLogic
				.findReverseMergeTenDataByType(materielType);
	}

	public List findComplexLegalUnit(Request request, String code) {
		return this.commonBaseCodeDao.findComplexLegalUnit(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#saveReverseMergeTenData(
	 * com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeTenData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public ReverseMergeTenData saveReverseMergeTenData(Request request,
			ReverseMergeTenData tenData) {
		this.commonBaseCodeLogic.saveReverseMergeTenData(tenData);
		return tenData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteReverseMergeTenData
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeTenData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-删除", index = 1.3)
	public void deleteReverseMergeTenData(Request request,
			ReverseMergeTenData tenData) {
		this.commonBaseCodeLogic.deleteReverseMergeTenData(tenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findReverseMergeBeforeDataByTen
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeTenData)
	 */
	public List findReverseMergeBeforeDataByTen(Request request,
			ReverseMergeTenData tenData) {
		return this.commonBaseCodeLogic
				.findReverseMergeBeforeDataByTen(tenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#saveReverseMergeBeforeData
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public ReverseMergeBeforeData saveReverseMergeBeforeData(Request request,
			ReverseMergeBeforeData beforeData) {
		this.commonBaseCodeLogic.saveReverseMergeBeforeData(beforeData);
		return beforeData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteReverseMergeBeforeData
	 * (com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-删除", index = 1.3)
	public void deleteReverseMergeBeforeData(Request request,
			ReverseMergeBeforeData beforeData) {
		this.commonBaseCodeLogic.deleteReverseMergeBeforeData(beforeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#addReverseMergeBeforeData
	 * (com.bestway.common.Request, java.util.List,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeTenData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void addReverseMergeBeforeData(Request request, List list,
			ReverseMergeTenData tenData) {
		this.commonBaseCodeLogic.addReverseMergeBeforeData(list, tenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#concatInnerTenAndReverseFour
	 * (com.bestway.common.Request, java.util.List,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeFourData)
	 */
	public void concatInnerTenAndReverseFour(Request request,
			List innerTenDatas, ReverseMergeFourData fourData) {
		this.commonBaseCodeLogic.concatInnerTenAndReverseFour(innerTenDatas,
				fourData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#concatInnerBeforeAndReverseTen
	 * (com.bestway.common.Request, java.util.List,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeTenData)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-编辑", index = 1.2)
	public void concatInnerBeforeAndReverseTen(Request request,
			List innerBeforeDatas, ReverseMergeTenData tenData) {
		this.commonBaseCodeLogic.concatInnerBeforeAndReverseTen(
				innerBeforeDatas, tenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findTenDataNotFourMerge(
	 * com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeFourData,
	 * java.lang.String)
	 */
	public List findTenDataNotFourMerge(Request request,
			ReverseMergeFourData fourData, String materielType) {
		return this.commonBaseCodeLogic.findTenDataNotFourMerge(fourData,
				materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findBeforeDataNotTenMerge
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public List findBeforeDataNotTenMerge(Request request, String materielType) {
		return this.commonBaseCodeLogic.findBeforeDataNotTenMerge(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMaterielNotInInnerMerge
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public List findMaterielNotInInnerMerge(Request request, String materielType) {
		return this.commonBaseCodeLogic
				.findMaterielNotInInnerMerge(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMaxTenReverseMergeNo
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public int findMaxTenReverseMergeNo(Request request, String type) {
		return this.commonBaseCodeLogic.findMaxTenReverseMergeNo(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMaxFourReverseMergeNo
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public int findMaxFourReverseMergeNo(Request request, String type) {
		return this.commonBaseCodeLogic.findMaxFourReverseMergeNo(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findComplexByFourCode(com
	 * .bestway.common.Request, java.lang.String)
	 */
	public List findComplexByFourCode(Request request, String fourCode) {
		return this.commonBaseCodeDao.findComplexByFourCode(fourCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findReverseMergeTenDataCountByFour(com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeFourData)
	 */
	public int findReverseMergeTenDataCountByFour(Request request,
			ReverseMergeFourData fourData) {
		return this.commonBaseCodeDao
				.findReverseMergeTenDataCountByFour(fourData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findReverseMergeBeforeDataCountByTen(com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeTenData)
	 */
	public int findReverseMergeBeforeDataCountByTen(Request request,
			ReverseMergeTenData tenData) {
		return this.commonBaseCodeDao
				.findReverseMergeBeforeDataCountByTen(tenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeDataInEmsEdiTr
	 * (com.bestway.common.Request, java.util.List)
	 */
	public List findInnerMergeDataInEmsEdiTr(Request request,
			List lsInnerMergeData) {
		return this.commonBaseCodeLogic
				.findInnerMergeDataInEmsEdiTr(lsInnerMergeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkWhetherReverseFourDataPutOnRecord(com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeFourData)
	 */
	public boolean checkWhetherReverseFourDataPutOnRecord(Request request,
			ReverseMergeFourData fourData) {
		return this.commonBaseCodeLogic
				.checkWhetherReverseFourDataPutOnRecord(fourData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkWhetherReverseTenDataPutOnRecord(com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeTenData)
	 */
	public boolean checkWhetherReverseTenDataPutOnRecord(Request request,
			ReverseMergeTenData tenData) {
		return this.commonBaseCodeLogic
				.checkWhetherReverseTenDataPutOnRecord(tenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkWhetherReverseBeforeDataPutOnRecord(com.bestway.common.Request,
	 * com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData)
	 */
	public boolean checkWhetherReverseBeforeDataPutOnRecord(Request request,
			ReverseMergeBeforeData beforeData) {
		return this.commonBaseCodeLogic
				.checkWhetherReverseBeforeDataPutOnRecord(beforeData);
	}

	public List findEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerHead emsEdiMergerHead) {
		return commonBaseCodeDao.findEmsEdiMergerImgBefore(emsEdiMergerHead);
	}

	public List findEmsEdiMergerImgBeforeByPtNo(Request request,
			EmsEdiMergerHead emsEdiMergerHead, String ptNo) {
		return commonBaseCodeDao.findEmsEdiMergerImgBeforeByPtNo(
				emsEdiMergerHead, ptNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeDataCount(
	 * com.bestway.common.Request, java.lang.String)
	 */
	public int findInnerMergeDataCount(Request request, String type) {
		return this.commonBaseCodeDao.findInnerMergeDataCount(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkDataForUndoTenInnerMerge
	 * (com.bestway.common.Request, java.util.List)
	 */
	public int checkDataForUndoTenInnerMerge(Request request, List list) {
		return this.getCommonBaseCodeLogic()
				.checkDataForUndoTenInnerMerge(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkDataForFourInnerMerge
	 * (com.bestway.common.Request, java.util.List)
	 */
	public int checkDataForFourInnerMerge(Request request, List list) {
		return this.getCommonBaseCodeLogic().checkDataForFourInnerMerge(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeDataByType4
	 * (com.bestway.common.Request, java.lang.String,
	 * com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead, java.lang.String)
	 */

	public List findInnerMergeDataByType4(Request request, String type,
			EmsEdiTrHead emsEdiTrHead, String helfType) { // 显示四码
		return getCommonBaseCodeDao().findInnerMergeDataByType4(type,
				emsEdiTrHead, helfType);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkDataForFourInnerMergeSort
	 * (com.bestway.common.Request, java.util.List, int)
	 */
	public int checkDataForFourInnerMergeSort(Request request,
			List selectedRows, int toRowNumber) {
		return this.getCommonBaseCodeLogic().checkDataForFourInnerMergeSort(
				selectedRows, toRowNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkDataForTenInnerMergeSort
	 * (com.bestway.common.Request, java.util.List, int)
	 */
	public int checkDataForTenInnerMergeSort(Request request,
			List selectedRows, int toRowNumber) {
		return getCommonBaseCodeLogic().checkDataForTenInnerMergeSort(
				selectedRows, toRowNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkWhetherReverseFourDataPutOnRecord(com.bestway.common.Request,
	 * java.util.List)
	 */
	public boolean checkWhetherReverseFourDataPutOnRecord(Request request,
			List lsFourData) {
		return this.commonBaseCodeLogic
				.checkWhetherReverseFourDataPutOnRecord(lsFourData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkWhetherReverseTenDataPutOnRecord(com.bestway.common.Request,
	 * java.util.List)
	 */
	public boolean checkWhetherReverseTenDataPutOnRecord(Request request,
			List lsTenData) {
		return this.commonBaseCodeLogic
				.checkWhetherReverseTenDataPutOnRecord(lsTenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkWhetherReverseBeforeDataPutOnRecord(com.bestway.common.Request,
	 * java.util.List)
	 */
	public boolean checkWhetherReverseBeforeDataPutOnRecord(Request request,
			List lsBeforeData) {
		return this.commonBaseCodeLogic
				.checkWhetherReverseBeforeDataPutOnRecord(lsBeforeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteReverseMergeFourData
	 * (com.bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-删除", index = 1.3)
	public void deleteReverseMergeFourData(Request request, List lsFourData) {
		this.commonBaseCodeLogic.deleteReverseMergeFourData(lsFourData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteReverseMergeTenData
	 * (com.bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-删除", index = 1.3)
	public void deleteReverseMergeTenData(Request request, List lsTenData) {
		this.commonBaseCodeLogic.deleteReverseMergeTenData(lsTenData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteReverseMergeBeforeData
	 * (com.bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--内部归并-删除", index = 1.3)
	public void deleteReverseMergeBeforeData(Request request, List lsBeforeData) {
		this.commonBaseCodeLogic.deleteReverseMergeBeforeData(lsBeforeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeInEmsEdiMergeAfter
	 * (com.bestway.common.Request, java.util.List)
	 */
	public List findInnerMergeInEmsEdiMergeAfter(Request request,
			List lsInnerMergeData) {
		return this.commonBaseCodeLogic
				.findInnerMergeInEmsEdiMergeAfter(lsInnerMergeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findInnerMergeInEmsEdiMergeBefore(com.bestway.common.Request,
	 * java.util.List)
	 */
	public List findInnerMergeInEmsEdiMergeBefore(Request request,
			List lsInnerMergeData) {
		return this.commonBaseCodeLogic
				.findInnerMergeInEmsEdiMergeBefore(lsInnerMergeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkTenInnerMergeDataInFourMerge(com.bestway.common.Request,
	 * java.util.List, int)
	 */
	public int checkTenInnerMergeDataInFourMerge(Request request,
			List selectedRows, int toRowNumber) {
		return this.commonBaseCodeLogic.checkTenInnerMergeDataInFourMerge(
				selectedRows, toRowNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeByFourNo(com
	 * .bestway.common.Request, java.lang.String, int)
	 */
	public List findInnerMergeByFourNo(Request request, String materielType,
			int fourNo) {
		return this.commonBaseCodeDao.findInnerMergeByFourNo(materielType,
				fourNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findHasEmsEdiTrExg(com.bestway
	 * .common.Request)
	 */
	public boolean findHasEmsEdiTrExg(Request request) {
		return this.commonBaseCodeDao.findHasEmsEdiTrExg();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findHasEmsEdiTrImg(com.bestway
	 * .common.Request)
	 */
	public boolean findHasEmsEdiTrImg(Request request) {
		return this.commonBaseCodeDao.findHasEmsEdiTrImg();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findInnerMergeDataByType10ToEmsH2k(com.bestway.common.Request,
	 * java.lang.String, com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead,
	 * com.bestway.bcus.manualdeclare.entity.EmsHeadH2k, java.lang.String)
	 */
	public List findInnerMergeDataByType10ToEmsH2k(Request request,
			String type, EmsEdiTrHead emsEdiTrHead, EmsHeadH2k emsHeadH2k,
			String helfType) {
		return this.commonBaseCodeDao.findInnerMergeDataByType10ToEmsH2k(type,
				emsEdiTrHead, emsHeadH2k, helfType);
	}

	// // 得到BOM最顶级
	// //@AuthorityFunctionAnnotation(caption = "浏览成品单耗", index = 11)
	// public List getBomExg(Request request) {
	// return this.commonBaseCodeLogic.getBomExg();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkBomBrowseAuthority(
	 * com.bestway.common.Request)
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览成品单耗", index = 11)
	public void checkBomBrowseAuthority(Request request) {

	}

	// /**
	// * BOM子料
	// */
	// //@AuthorityFunctionAnnotation(caption = "浏览成品单耗", index = 11)
	// public List getBomImg(Request request, String ptNo) {
	// return this.commonBaseCodeLogic.getBomImg(ptNo);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMaterielWhereCp(com.
	 * bestway.common.Request, java.lang.String)
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览成品单耗", index = 11)
	public List findMaterielWhereCp(Request request, String ptNo) {
		return this.commonBaseCodeDao.findMaterielWhereCp(ptNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkUnitWasteBrowseAuthority
	 * (com.bestway.common.Request)
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览成品单耗", index = 11)
	public void checkUnitWasteBrowseAuthority(Request request) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkFactoryCustomsBrowseAuthority(com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--报关与工厂资料对照", index = 1.4)
	public void checkFactoryCustomsBrowseAuthority(Request request) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#getInnerMergeDataToCollate
	 * (com.bestway.common.Request, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	// @AuthorityFunctionAnnotation(caption = "浏览报关与工厂资料对照", index = 11)
	// 关务与物流对照表
	public List getInnerMergeDataToCollate(Request request, String type,
			String seqNum, String tenName) {
		return this.commonBaseCodeLogic.getInnerMergeDataToCollate(type,
				seqNum, tenName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * checkFactoryCustomsBrowseAuthority(com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--归并关系禁用维护", index = 1.9)
	public void checkInnerMergeDataIsForbidAuthority(Request request) {

	}

	/**
	 * 查询全部禁用的归并关系
	 * 
	 * @param forBidBeginDate
	 * @param forBidEndData
	 * @param type
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "企业内部归并--归并关系禁用维护", index = 1.9)
	public List findInnerMergeDataIsForbid(Request request,
			Date forBidBeginDate, Date forBidEndData, String type,
			boolean isForbid) {
		return this.commonBaseCodeDao.findInnerMergeDataIsForbid(
				forBidBeginDate, forBidEndData, type, isForbid);
	}

	/**
	 * 修改内部归并的禁用
	 * 
	 * @param type
	 * @param ptNo
	 * @param isForbid
	 */
	public void updateInnerMergeDataIsForbid(Request request, String type,
			String ptNo, boolean isForbid, String user) {
		this.commonBaseCodeDao.updateInnerMergeDataIsForbid(type, ptNo,
				isForbid, user);
	}

	// 得到物料由十码
	/*
	 * (non-Javadoc)
	 */
	public List findInnerMergeDataByTenNo(Request request, String type,
			String tenNo) {
		return this.commonBaseCodeDao.findInnerMergeDataByTenNo(type, tenNo);
	}

	public List findInnerMergeDataByFourNo(Request request, String type,
			String FourNo) {
		return this.commonBaseCodeDao.findInnerMergeDataByFourNo(type, FourNo);
	}

	/**
	 * 得到物料由十码
	 * 
	 * @param type
	 * @param tenNo
	 * @return
	 */
	public List findInnerMergeDataByTenNoFromFile(Request request, String type,
			String tenNo) {
		return commonBaseCodeDao.findInnerMergeDataByTenNoFromFile(type, tenNo);
	}

	/**
	 * 内部归并关系文本导入检查使用
	 * 
	 * @param type
	 * @param fourNo
	 * @return
	 */
	public List findInnerMergeDataByFourNoFromFile(Request request,
			String type, String fourNo) {
		return commonBaseCodeDao.findInnerMergeDataByFourNoFromFile(type,
				fourNo);
	}

	// 初始化单位折算比例
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#initUnitDedault(com.bestway
	 * .common.Request)
	 */
	public void initUnitDedault(Request request) {
		this.commonBaseCodeLogic.initUnitDedault();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#initDzscUnitDedault(com.
	 * bestway.common.Request)
	 */
	public void initDzscUnitDedault(Request request) {
		this.commonBaseCodeLogic.initDzscUnitDedault();
	}

	// 显示pk单
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findPK(com.bestway.common
	 * .Request)
	 */
	public List findPK(Request request) {
		return this.commonBaseCodeDao.findPK();
	}

	// 显示集装箱单
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findErpContainer(com.bestway
	 * .common.Request)
	 */
	public List findErpContainer(Request request) {
		return this.commonBaseCodeDao.findErpContainer();
	}

	// 显示不重复的pk单据
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findDistinctPk(com.bestway
	 * .common.Request)
	 */
	public List findDistinctPk(Request request) {
		return this.commonBaseCodeDao.findDistinctPk();
	}

	// 显示pk单据由pk单号
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findpkForpkno(com.bestway
	 * .common.Request, java.util.List)
	 */
	public List findpkForpkno(Request request, List list) {
		return this.commonBaseCodeDao.findpkForpkno(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findInnerMergeDataByFilter
	 * (com.bestway.common.Request, java.util.List)
	 */
	public boolean findInnerMergeDataByFilter(Request request,
			List selectedInnerMergeData) {
		return this.commonBaseCodeLogic
				.findInnerMergeDataByFilter(selectedInnerMergeData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMaterielNotInInnerMerge
	 * (com.bestway.common.Request, java.lang.String, int, int,
	 * java.lang.String, java.lang.Object, java.lang.Boolean)
	 */
	public List findMaterielNotInInnerMerge(Request request,
			String materielType, int index, int length, String property,
			Object value, Boolean isLike) {
		return this.commonBaseCodeDao.findMaterielNotInInnerMerge(materielType,
				index, length, property, value, isLike, new Boolean(true));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findMaterielAll(com.bestway
	 * .common.Request, java.lang.String, int, int, java.lang.String,
	 * java.lang.Object, java.lang.Boolean)
	 */
	public List findMaterielAll(Request request, String materielType,
			int index, int length, String property, Object value, Boolean isLike) {
		return this.commonBaseCodeDao.findMaterielNotInInnerMerge(materielType,
				index, length, property, value, isLike, null);
	}

	public List findMaterielAllOfCustomsRalation(Request request,
			String materielType, int index, int length, String property,
			Object value, Boolean isLike, Boolean isFilt) {
		List<Materiel> ms = this.commonBaseCodeDao.findMaterielNotInInnerMerge(
				materielType, index, length, property, value, isLike, null);
		// 过滤
		if (isFilt) {
			List<String> list = commonBaseCodeDao
					.findFactoryAndFactualCustomsRalation(materielType);
			Map map = new HashMap();
			for (String key : list) {
				map.put(key, "");
			}
			int size = ms.size();
			for (int i = size - 1; i >= 0; i--) {
				if (map.containsKey(ms.get(i).getPtNo())) {
					ms.remove(i);
				}
			}
		}
		return ms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#importInnerMergeDataFromMateriel
	 * (com.bestway.common.Request, java.util.List, java.lang.String)
	 */
	public List importInnerMergeDataFromMateriel(Request request,
			List materielList, String materielType) {
		return this.commonBaseCodeLogic.importInnerMergeDataFromMateriel(
				materielList, materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#saveInner(com.bestway.common
	 * .Request, com.bestway.bcus.innermerge.entity.InnerMergeData,
	 * com.bestway.common.materialbase.entity.Materiel)
	 */
	public InnerMergeData saveInner(Request request, InnerMergeData inner,
			Materiel materiel) {
		this.commonBaseCodeLogic.saveInner(inner, materiel);
		return inner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#deleteInnerNoten(com.bestway
	 * .common.Request, java.lang.String)
	 */
	public void deleteInnerNoten(Request request, String type) {
		this.commonBaseCodeLogic.deleteInnerNoten(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#getDistinctTenInner(com.
	 * bestway.common.Request, java.lang.String)
	 */
	public List getDistinctTenInner(Request request, String materielType) {
		return this.commonBaseCodeLogic.getDistinctTenInner(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#getDistinctFourceInner(com
	 * .bestway.common.Request, java.lang.String)
	 */
	public List getDistinctFourceInner(Request request, String materielType) {
		return this.commonBaseCodeLogic.getDistinctFourceInner(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#addtenInner(com.bestway.
	 * common.Request, java.lang.String, java.lang.String, java.util.List)
	 */
	public List addtenInner(Request request, String type, String seqNum,
			List list) {
		return this.commonBaseCodeLogic.addtenInner(type, seqNum, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#addfourceInner(com.bestway
	 * .common.Request, java.lang.String, com.bestway.bcus.cas.entity.BillTemp,
	 * java.util.List)
	 */
	public List addfourceInner(Request request, String type, BillTemp obj,
			List list) {
		return this.commonBaseCodeLogic.addfourceInner(type, obj, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#checkDataEndTenInnerMerge
	 * (com.bestway.common.Request, java.util.List)
	 */
	public int checkDataEndTenInnerMerge(Request request, List list) {
		return this.commonBaseCodeLogic.checkDataEndTenInnerMerge(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.bcus.innermerge.action.aaaaaaaaa#
	 * findWhetherInnerMergeInEmsHeadH2k(com.bestway.common.Request,
	 * java.util.List)
	 */
	public List findWhetherInnerMergeInEmsHeadH2k(Request request,
			List lsInnerMergeData) {
		return commonBaseCodeLogic
				.findWhetherInnerMergeInEmsHeadH2k(lsInnerMergeData);
	}

	public List findWhetherReverseMergeInEmsHeadH2k(Request request,
			List lsInnerMergeData) {
		return commonBaseCodeLogic
				.findWhetherReverseMergeInEmsHeadH2k(lsInnerMergeData);
	}

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
	public List[] updateTenAndFourCustoms(Request request, List passList,
			String materielType, Complex tenAfterComplex, String tenName,
			String tenSpec, Unit tenAfterMemoUnit, Integer newTenSeqNum,
			String fourCode, String fourName, Integer newFourSeqNum) {
		return commonBaseCodeLogic.updateTenAndFourCustoms(passList,
				materielType, tenAfterComplex, tenName, tenSpec,
				tenAfterMemoUnit, newTenSeqNum, fourCode, fourName,
				newFourSeqNum);
	}

	// 更新十位商品资料（编码，名称，规格，单位）
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#changeCustoms(com.bestway
	 * .common.Request, com.bestway.bcus.custombase.entity.hscode.Complex,
	 * java.lang.String, java.lang.String,
	 * com.bestway.bcus.custombase.entity.parametercode.Unit, java.lang.String,
	 * java.lang.Integer)
	 */
	public void changeCustoms(Request request, Complex afterComplex,
			String name, String spec, Unit afterMemoUnit, String materielType,
			Integer tenMemoNo) {
		this.commonBaseCodeLogic.changeCustoms(afterComplex, name, spec,
				afterMemoUnit, materielType, tenMemoNo);
	}

	// 更新十位商品序号
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#changeCustomsSeqNum(com.
	 * bestway.common.Request, java.lang.String, java.lang.Integer,
	 * java.lang.Integer)
	 */
	public void changeCustomsSeqNum(Request request, String materielType,
			Integer oldSeqNum, Integer newSeqNum) {
		this.commonBaseCodeLogic.changeCustomsSeqNum(materielType, oldSeqNum,
				newSeqNum);
	}

	// 更新四位商品序号
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#changeCustomsSeqNumFourNo
	 * (com.bestway.common.Request, java.lang.String, java.lang.Integer,
	 * java.lang.Integer)
	 */
	public void changeCustomsSeqNumFourNo(Request request, String materielType,
			Integer oldSeqNum, Integer newSeqNum) {
		this.commonBaseCodeLogic.changeCustomsSeqNumFourNo(materielType,
				oldSeqNum, newSeqNum);
	}

	// 更新四位商品资料（编码，名称）
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#changeCustomsFourNo(com.
	 * bestway.common.Request, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Integer)
	 */
	public void changeCustomsFourNo(Request request, String complex,
			String name, String materielType, Integer fourNo) {
		this.commonBaseCodeLogic.changeCustomsFourNo(complex, name,
				materielType, fourNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#editMergerFlag(com.bestway
	 * .common.Request, java.lang.String, java.lang.Integer, java.lang.String)
	 */
	public void editMergerFlag(Request request, String type, Integer seqNum,
			String ptNo) {
		this.commonBaseCodeLogic.editMergerFlag(type, seqNum, ptNo);
	}

	public void editMergerFlag0(Request request, String type, String ptNo) {
		this.commonBaseCodeLogic.editMergerFlag0(type, ptNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#findVersionByCpNo(com.bestway
	 * .common.Request, java.lang.String)
	 */
	public List findVersionByCpNo(Request request, String ptNo) {
		return this.commonBaseCodeDao.findVersionByCpNo(ptNo);
	}

	/*
	 * public CurrRate findCurrRateByCurr1(Curr curr1){ }
	 */
	// 强制性删除归并关系
	public void deleteInner(Request request, InnerMergeData inner) {
		this.commonBaseCodeLogic.deleteInner(inner);
	}

	public List changeNotMerger(Request request, List list) {
		return this.commonBaseCodeLogic.changeNotMerger(list);
	}

	public Date findMaxImportTimerByInnerMergeData(Request request, String type) {
		return this.commonBaseCodeDao.findMaxImportTimerByInnerMergeData(type);
	}

	public Date findLastUpdateDateByInnerMergeData(Request request, String type) {
		return this.commonBaseCodeDao.findLastUpdateDateByInnerMergeData(type);
	}

	public Date findLastInnestDateByInnerMergeData(Request request, String type) {
		return this.commonBaseCodeDao.findLastInnestDateByInnerMergeData(type);
	}

	public List findInnerMergedataOrder(Request request) {
		return this.commonBaseCodeDao.findInnerMergedataOrder();
	}

	public InnerMergeDataOrder saveInnerOrder(Request reques,
			InnerMergeDataOrder obj) {
		commonBaseCodeDao.saveInnerOrder(obj);
		return obj;
	}

	/**
	 * 把选中的十码在内部关系管理已备案，但对应归并前没备案的数据写到内部关系管理里
	 * 
	 * @param request
	 * @param materielType
	 *            "0"为成品,"1"为料件
	 * @param list
	 *            内部归并的list
	 */
	public void writeBackEmsEdiMergerExgImgBefore(Request request,
			String materielType, List list) {
		commonBaseCodeLogic.writeBackEmsEdiMergerExgImgBefore(materielType,
				list);
	}

	public List findEmsEdiImgbefore(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return commonBaseCodeDao.findEmsEdiImgbefore(index, length, property,
				value, isLike, null);
	}

	public List findEmsEdiBomImgbefore(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return commonBaseCodeDao.findEmsEdiBomImgbefore(index, length,
				property, value, isLike, null);
	}

	public List findInnerMergeDataByPtNo(Request request, String ptNo) {
		return this.commonBaseCodeDao.findInnerMergeDataByPtNo1(ptNo);

	}

	public List findEmsEdiMergerImgAfter(Request request,
			EmsEdiMergerHead emsMerger, Integer seqNum) { // 显示归并关系归并后料件
		return commonBaseCodeDao.findEmsEdiMergerImgAfter(emsMerger, seqNum);
	}

	/**
	 * 查找内部归并后使用到得报关单
	 * 
	 * @param request
	 * @param listCheckData
	 * @return
	 */
	public List findInnerMergeInEmsEdiMergeOfUsed(Request request,
			List<InnerMergeData> listCheckData) {
		InnerMergeData data = listCheckData.get(0);
		return commonBaseCodeDao.findInnerMergeInEmsEdiMergeOfUsed(
				data.getHsAfterTenMemoNo(), data.getImrType());
	}

	public List findInnerMergeInEmsEdiMergeBeforeOfUsed(Request request,
			List list) {
		InnerMergeData data = (InnerMergeData) list.get(0);
		return commonBaseCodeDao.findInnerMergeInEmsEdiMergeBeforeOfUsed(data);
	}
}
