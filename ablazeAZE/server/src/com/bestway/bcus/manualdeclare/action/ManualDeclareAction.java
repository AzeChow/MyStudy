/*
 * Created on 2004-8-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.EmsBillListExpProductStat;
import com.bestway.bcus.manualdeclare.entity.EmsBillListImpMaterialStat;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public interface ManualDeclareAction {
	/**
	 * 根据料件货号查找归并前成品
	 * 
	 * @return
	 * @author sxk
	 */
	List findEmsEdiMergerExgBefore(Request request, String ptNo);

	Map<String, Integer> findMaxMaterielBomVersion(Request request);

	List findEmsEdiTrHead(Request request); // 显示经营范围表头

	EmsEdiTrHead emsEdiChange(Request request);

	List findEmsEdiTrImg(Request request, EmsEdiTrHead emsEdiTrHead);

	List findEmsEdiTrExg(Request request, EmsEdiTrHead emsEdiTrHead);

	// 得到所有版本号放在combox中
	List findAllVersion(Request request);

	void deleteEmsEdiTrHead(Request request, EmsEdiTrHead emshead);// 删除经营范围表头

	void deleteEmsEdiTrImg(Request request, EmsEdiTrImg emsImg);

	void deleteEmsEdiTrExg(Request request, EmsEdiTrExg emsExg);

	void deleteEmsEdiTrImgExg(Request request, EmsEdiTrHead emshead);

	EmsEdiTrHead saveEmsEdiTrHead(Request request, EmsEdiTrHead emshead)
			throws DataAccessException;

	EmsEdiTrImg saveEmsEdiTrImg(Request request, EmsEdiTrImg emsImg)
			throws DataAccessException;

	EmsEdiTrExg saveEmsEdiTrExg(Request request, EmsEdiTrExg emsExg)
			throws DataAccessException;

	EmsEdiMergerHead emsMergerAdd(Request request);

	List findEmsEdiMergerHead(Request request);

	EmsEdiMergerHead findEmsEdiMergerHeadById(Request request, String id);

	EmsEdiMergerHead saveEmsEdiMergerHead(Request request,
			EmsEdiMergerHead emshead) throws DataAccessException;

	void deleteEmsEdiMergerHead(Request request, EmsEdiMergerHead emshead);

	void deleteEmsEdiMergerImgExg(Request request, EmsEdiMergerHead emshead);

	void deleteBomByAfter(Request request, EmsEdiMergerExgAfter emsAfter);

	List findEmsEdiMergerBomByAfter(Request request,
			EmsEdiMergerExgAfter emsAfter);

	List findEmsEdiMergerImgAfter(Request request, EmsEdiMergerHead emsMerger);

	List findEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerImgAfter emsAfter);

	List findEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgAfter emsExgAfter);

	RestirictCommodity findRerictCommodity(Request request, Boolean ImpExpType,
			String seqNum);

	List<RestirictCommodity> findRerictCommoditys(Request request,
			Boolean ImpExpType, List<Integer> seqNums);

	List findEmsEdiBom(Request request, EmsEdiMergerExgBefore emsExgBefore);

	List findEmsEdiMergerExgAfter(Request request, EmsEdiMergerHead emsMerger);

	EmsEdiMergerImgAfter saveEmsEdiMergerImgAfter(Request request,
			EmsEdiMergerImgAfter emsAfter) throws DataAccessException;

	EmsEdiMergerImgBefore saveEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerImgBefore emsBefore) throws DataAccessException;

	EmsEdiMergerExgBefore saveEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgBefore emsBefore) throws DataAccessException;

	EmsEdiMergerExgAfter saveEmsEdiMergerExgAfter(Request request,
			EmsEdiMergerExgAfter emsAfter) throws DataAccessException;

	List saveEmsEdiMergerExgAfters(Request request, List exgAfters);

	List saveEmsEdiMergerImgAfters(Request request, List imgAfters);

	void deleteEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerImgBefore emsBefore);

	void deleteEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgBefore emsBefore);

	void deleteEmsEdiMergerImgAfter(Request request,
			EmsEdiMergerImgAfter emsAfter);

	void deleteEmsEdiMergerExgAfter(Request request,
			EmsEdiMergerExgAfter emsAfter);

	List findEmsEdiMergerAfterImgToBom(Request request,
			EmsEdiMergerVersion emsEdiMergerVersion, EmsEdiMergerHead emsHead);

	EmsEdiMergerExgBom saveEmsEdiMergerExgBom(Request request,
			EmsEdiMergerExgBom emsExgBom) throws DataAccessException;

	void deleteEmsEdiMergerExgBom(Request request, EmsEdiMergerExgBom emsExgBom);

	void deleteEmsHeadH2kImgExg(Request request, EmsHeadH2k emshead);

	EmsEdiMergerHead emsEdiMergerChange(Request request);

	List<EmsHeadH2k> findEmsHeadH2k(Request request);// 显示电子帐册表头

	EmsHeadH2k findEmsHeadH2kById(Request request, String emsHeadH2kId);// 显示电子帐册表头

	EmsEdiMergerHead findNewEmsEdiMergerHeadById(Request request,
			String emsEdiMergerHeadId);

	void controlFmCommodityForbidBrowser(Request request);

	void controlFmCommodityForbidAdd(Request request);

	void controlFmCommodityForbidBack(Request request);

	public void authUpdateComplex(Request request);

	void restirictCommodity(Request request);

	EmsHeadH2k emsHeadH2kAdd(Request request);

	public List finEmsHeadH2KBomFromBeginSeqToEndSeq(Request request,
			String string, String string2, int pageNum, EmsHeadH2k head);

	/********************************************************
	 * 保存电子帐册表头信息
	 *********************************************************/
	EmsHeadH2k saveEmsHeadH2k(Request request, EmsHeadH2k emsHead)
			throws DataAccessException;

	void deleteEmsHeadH2k(Request request, EmsHeadH2k emsHead);

	List findEmsHeadH2kImg(Request request, EmsHeadH2k emsHeadH2k);

	List findEmsHeadH2kExg(Request request, EmsHeadH2k emsHeadH2k);

	List findEmsHeadH2kBom(Request request, EmsHeadH2kVersion version);

	List findExistVersion(Request request, EmsHeadH2kExg emsHeadH2kExg,
			String version);

	List findEmsHeadH2kVersion(Request request, EmsHeadH2kExg emsHeadH2kExg);

	List findEmsEdiMergerImgAfterToH2k(Request request,
			EmsEdiMergerHead emsMerger, EmsHeadH2k emsH2k, boolean isNow);

	EmsHeadH2kImg saveEmsHeadH2kImg(Request request, EmsHeadH2kImg emsH2kImg)
			throws DataAccessException;

	List findEmsEdiMergerExgAfterToH2k(Request request,
			EmsEdiMergerHead emsMerger, EmsHeadH2k emsH2k, boolean isNow);

	EmsHeadH2kExg saveEmsHeadH2kExg(Request request, EmsHeadH2kExg emsH2kExg)
			throws DataAccessException;

	EmsHeadH2kVersion saveEmsHeadH2kVersion(Request request,
			EmsHeadH2kVersion emsH2k) throws DataAccessException;

	List findEmsEdiMergerExgBeforeByAfter(Request request,
			EmsHeadH2kExg emsHeadH2kExg, EmsEdiMergerHead emsMergerHead);

	List findEmsEdiBomByExgBefore(Request request,
			EmsEdiMergerExgBefore emsExgBefore);

	List findEmsEdiMergerImgBeforeByPtNo(Request request,
			EmsEdiMergerExgBom emsBom, EmsEdiMergerHead emsHead);

	List findEmsHeadH2kBomBySeqNum(Request request, Integer emsBom,
			EmsHeadH2kVersion emsVersion, EmsHeadH2kExg exg);

	List findBomByExg(Request request, EmsHeadH2kExg emsHeadH2kExg);

	void deleteVersionAndBom(Request request, EmsHeadH2kExg emsHeadH2kExg);

	EmsHeadH2kBom saveEmsHeadH2kBom(Request request, EmsHeadH2kBom emsH2kBom)
			throws DataAccessException;

	RestirictCommodity saveRestirictCommodity(Request request,
			RestirictCommodity restirictCommodity) throws DataAccessException;

	void deleteEmsHeadh2kBomByVersion(Request request, EmsHeadH2kVersion version);

	EmsHeadH2k emsHeadH2kChange(Request request, String changeState,
			EmsHeadH2k emsHead);

	void deleteEmsHeadH2kBom(Request request, EmsHeadH2kBom emsHeadH2kBom);

	void deleteEmsHeadH2kVersion(Request request,
			EmsHeadH2kVersion emsHeadH2kVersion);

	void deleteEmsHeadH2kImg(Request request, EmsHeadH2kImg emsHeadH2kImg);

	void deleteEmsHeadH2kExg(Request request, EmsHeadH2kExg emsHeadH2kExg);

	/**
	 * 显示当前正在执行的电子帐册表头
	 */
	List findEmsHeadH2kInExecuting(Request request);

	List findEmsEdiImgExgs(Request request, EmsEdiTrHead emsEdiTrHead);

	List findEditedEmsEdiImgExgs(Request request, EmsEdiTrHead emsEdiTrHead);

	/**
	 * 显示电子分册表头
	 */
	List findEmsHeadFasAll(Request request, EmsHeadH2kFas emsH2kFas);

	/**
	 * 显示电子分册明细
	 */
	List findEmsHeadFasAllImgd(Request request, EmsHeadH2kFas emsH2kFas);

	/**
	 * 获得归并关系的表头来自申报状态(非历史记录)
	 */
	EmsEdiMergerHead findEmsEdiMergerHeadByDeclareState(Request request,
			String declareState);

	/**
	 * 显示归并关系归并后成品和料件
	 * 
	 * @return
	 */
	List findEmsEdiMergerImgExg(Request request);

	List findEmsHeadH2kImgToBom(Request request, EmsHeadH2k emsHeadH2k,
			EmsHeadH2kVersion version);

	List findEmsHeadH2kFas(Request request, String emsHeadH2kNo);

	EmsHeadH2kFas saveEmsHeadH2kFas(Request request, EmsHeadH2kFas fas)
			throws DataAccessException;

	List findEmsHeadH2kImgToFas(Request request, EmsHeadH2k emsHeadH2k,
			EmsHeadH2kFas emsH2kFas);

	List findEmsHeadH2kExgToFas(Request request, EmsHeadH2k emsHeadH2k,
			EmsHeadH2kFas emsH2kFas);

	EmsHeadH2kFasImg saveEmsHeadH2kFasImg(Request request,
			EmsHeadH2kFasImg fasImg) throws DataAccessException;

	EmsHeadH2kFasExg saveEmsHeadH2kFasExg(Request request,
			EmsHeadH2kFasExg fasExg) throws DataAccessException;

	List findEmsHeadH2kFasImg(Request request, EmsHeadH2kFas fas);

	List findEmsHeadH2kFasExg(Request request, EmsHeadH2kFas fas);

	void deleteEmsHeadH2kFasImg(Request request, EmsHeadH2kFasImg img);

	void deleteEmsHeadH2kFasExg(Request request, EmsHeadH2kFasExg exg);

	void deleteEmsHeadH2kFasImgExg(Request request, EmsHeadH2kFas fas);

	void deleteEmsHeadH2kFas(Request request, EmsHeadH2kFas fas);

	List findEmsHeadH2kFasByCop(Request request, String emsHeadH2kNo,
			String copEmsNo);

	EmsHeadH2kFas emsHeadH2FasChange(Request request, EmsHeadH2kFas fas);

	boolean findFactoryNameFromImg(Request request, String ptNo);

	boolean findFactoryNameFromExg(Request request, String ptNo);

	void changeChange(Request request);

	List findEmsEdiTrHeadAll(Request request);

	List findEmsEdiMergerHeadAll(Request request);

	List findEmsHeadH2kAll(Request request);

	List findEmsHeadH2kFasAll(Request request, String emsHeadH2kNo);

	void deleteEmsEdiMergerImgBeforeAll(Request request,
			EmsEdiMergerImgAfter emsAfter);

	void deleteEmsEdiMergerExgBeforeAll(Request request,
			EmsEdiMergerExgAfter emsAfter);

	void deleteEmsEdiMergerMergerBomAll(Request request,
			EmsEdiMergerExgBefore emsExgBefore);

	List findInnerMergeDataByType10ToEms(Request request, String type,
			EmsEdiTrHead emsEdiTrHead, EmsHeadH2k emsHeadH2k);

	EmsHeadH2k getH2k(Request request, String id);

	EmsEdiTrHead findEmsEdiTrHeadIng(Request request);

	/**
	 * 显示电子帐册所有Bom 排序用于打印
	 */
	List findEmsHeadH2kBomOrder(Request request, EmsHeadH2k emsHeadH2k);

	/**
	 * 显示电子帐册料件变更记录来自变更次数
	 */
	List findEmsHeadH2kImgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer modifyTimes);

	/**
	 * 显示归并关系料件变更记录来自变更次数
	 */
	List findemsEdiMergerHeadImgChange(Request request,
			EmsEdiMergerHead emsEdiMergerHead, Integer modifyTimes);

	/**
	 * 显示归并关系成品变更记录来自变更次数
	 */
	List findemsEdiMergerHeadExgChange(Request request,
			EmsEdiMergerHead emsEdiMergerHead, Integer modifyTimes);

	/**
	 * 显示归并关系BOM变更记录来自变更次数
	 */
	List findemsEdiMergerHeadBomChange(Request request,
			EmsEdiMergerHead emsEdiMergerHead, Integer modifyTimes);

	/**
	 * 显示电子帐册料件变更记录来备案序号段
	 */
	List findEmsHeadH2kImgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer beginNo, Integer endNo, Integer[] seqNumArray);

	/**
	 * 显示电子帐册成品变更记录来自变更次数
	 */
	List findEmsHeadH2kExgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer modifyTimes);

	/**
	 * 显示电子帐册成品变更记录来自变更次数
	 */
	List findEmsHeadH2kExgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer beginNo, Integer endNo, Integer[] seqNumArray);

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	List<List> findEmsHeadH2kBomChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer modifyTimes);

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	List<List> findEmsHeadH2kBomChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer beginNo, Integer endNo, Integer[] seqNumArray);

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	List<List> findEmsHeadH2kBomSingleVersionChange(Request request,
			EmsHeadH2k emsHeadH2k, Map<Integer, List<Integer>> mapAllSeqNum);

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	List<List> findEmsHeadH2kBomByDate(Request request, EmsHeadH2k emsHeadH2k,
			Date beginDate, Date endDate, boolean isDeclared);

	void modityComplex(Request request, boolean isImg, String seqNum,
			Complex complex);

	void modityBomFromComplex(Request request, String seqNum, Complex complex,
			EmsHeadH2k emsHeadh2k);

	void modityBomFromPrice(Request request, String seqNum,
			EmsHeadH2kImg emsAfter, EmsHeadH2k emsHeadh2k);

	// List findEdiMaterielInfo(Request request, boolean isMaterial);

	List getTempEmsEdiH2kForBid(Request request, boolean isMaterial);

	// List getRestirictCommodity(Request request, boolean isMaterial);

	List addCommdityForbid(Request request, List tempList, boolean isMateriel);

	List addCommdityForbidZX(Request request, EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList, List<Integer> versionList,
			boolean isMateriel);

	List addRestirictCommodity(Request request, List tempList,
			boolean isMateriel);

	void deleteCommdityForbidZX(Request request, EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList, List<Integer> versionList,
			boolean isMateriel);

	void changeEmsEdiForbid(Request request, String seqNum, boolean isMateriel,
			boolean isForbid, String version);

	void deleteCommdityForbid(Request request, CommdityForbid obj);

	void deleteRestirictCommodity(Request request, RestirictCommodity obj);

	List findCommdityForbid(Request request, boolean isMateriel,
			String serialNo, Date beginDate, Date endDate);

	List findCommdityForbid(Request request, boolean isMateriel,
			String serialNo, Date beginDate, Date endDate, Boolean isForbid);

	List findCommdityForbidHis(Request request, boolean isMateriel,
			String serialNo, Date beginDate, Date endDate);

	List findRestirictCommodity(Request request, boolean isMateriel,
			Date beginDate, Date endDate);

	List findEmsEdiMergerVersion(Request request,
			EmsEdiMergerExgBefore emsEdiMergerExgBefore);

	List findEmsEdiMergerBom(Request request, EmsEdiMergerVersion version);

	List findManualDescBom(Request req, String ptNo, int version);

	List findExistVersion(Request request, EmsEdiMergerExgBefore emsExgBefore,
			String version);

	List findExistVersionNew(Request request, String ptNo, String version);

	/**
	 * 根据类型与料号查询内部归并的物料
	 * 
	 * @param request
	 * @param type
	 * @param ptNo
	 * @return
	 */
	public Materiel findInnerMergeDataByPtNo(Request request, String type,
			String ptNo);

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersion(Request request, String ptNo, String cmpVersion);

	/**
	 * 根据企业版本查询出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersionByCmpVersion(Request request, String ptNo,
			String cmpVersion);

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getMaxVersion(Request request, String ptNo);

	EmsEdiMergerVersion saveEmsEdiMergerVersion(Request request,
			EmsEdiMergerVersion emsH2k) throws DataAccessException;

	void deleteEmsMergerBomByVersion(Request request,
			EmsEdiMergerVersion version);

	void deleteEmsEdiMergerVersion(Request request,
			EmsEdiMergerVersion emsEdiMergerVersion);

	List getEmsUnitWearList(Request request, String bSeqNum, String eSeqNum,
			int pageNum, EmsHeadH2k head);

	List findEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgAfter emsExgAfter, int firstIndex);

	/**
	 * 归并关系新增BOM来自产品结构BOM
	 * 
	 * @param request
	 * @param exg
	 * @return
	 */
	List addBomFromCustomsBom(Request request, EmsEdiMergerExgBefore exg,
			Integer version);

	/**
	 * 保存已转换的报关常用工厂BOM
	 */
	void addBomFromSelectBom(Request request, EmsEdiMergerExgBefore exg,
			EmsEdiMergerVersion ediMergerVersion);

	List findEmsEdiMergerBomByAfterPage(Request request, Integer seqNum,
			int firstIndex, String declareState, Integer version, String ptNo);

	List findEmsEdiMergerBomPtNoByAfter(Request request, Integer seqNum,
			String declareState, Integer version);

	List bomImport(Request request, EmsEdiMergerVersion versionObj, List list,
			EmsEdiMergerExgAfter exgAfter);

	// List bomImport1(Request request, EmsEdiMergerHead emsEdiMergerHead,
	// List list);

	int[] saveToEmsMerger(Request request, EmsEdiMergerVersion versionObj,
			List list, boolean isChange);

	List saveToEmsMerger(Request request, EmsEdiMergerExgBefore exg,
			EmsEdiMergerBomFrom obj, EmsEdiMergerHead emsEdiMergerHead,
			boolean isChange);

	List addEmsH2kDm(Request request, Integer seqNum, String declareState,
			Integer versionI, String ptNo);

	int[] saveEmsBomFromMergerBom(Request request, List list, EmsHeadH2kExg exg);

	String getBpara(Request request, int type);

	// 保存参数设定
	void saveBpara(Request request, int type, String sValue);

	void changeMergerflag(Request request);

	List bomEmsImport(Request request, EmsHeadH2k emsHeadH2k, List list);

	int[] saveToEmsHeadH2k(Request request, EmsHeadH2k emsHeadH2k, List list,
			boolean cbIsOverwrite);

	int[] saveToEmsHeadH2k(Request request, EmsHeadH2kVersion vobj, List list);

	void editImgBeforeList(Request request, EmsEdiMergerImgAfter emsAfter);

	void editExgBeforeList(Request request, EmsEdiMergerExgAfter emsAfter);

	void editversion(Request request, EmsHeadH2k head);

	void dealEmsHistory(Request request) throws SQLException;

	void changeCustomsFromMateriel(Request request);

	void changeChangeNum(Request request, EmsHeadH2k head);

	void dealEmsHistory2(Request request) throws SQLException;

	void getBcusParaPurview(Request request);

	List findEmsHeadH2kBomByCpSeqNum(Request request, Integer seqNum,
			EmsHeadH2k emsHeadH2k, Integer version);

	List findBomUnitWearCompare(List exglist, EmsEdiMergerHead head,
			Integer exgSeqNum, Integer version);

	void initSendState(Request request, EmsEdiMergerHead head);

	// 修改归并后商品编码自动修改归并前商品编码
	void changeBeforeComplexUnit(Request request,
			EmsEdiMergerImgAfter imgafter, String scomplex, String ncomplex,
			String sunit, String nunit, boolean isMainIsChange);

	void changeBeforeComplexUnit(Request request,
			EmsEdiMergerExgAfter exgafter, String scomplex, String ncomplex,
			String sunit, String nunit);

	void changetemp(Request request, EmsEdiMergerHead head);

	void initEmsSendState(Request request, EmsHeadH2k head);

	List findEmsEdiMergerImgBeforeByGNo(Request request, boolean isMateriel,
			String ptNo, EmsEdiMergerHead emsHead);

	/**
	 * 根据料件查询归并关系前的归并序号
	 * 
	 * @param isMateriel
	 * @param ptNo
	 * @param emsHead
	 * @return
	 */
	public Integer findSeqNumEmsEdiMergerAfterByGNo(Request request,
			boolean isMateriel, String ptNo, EmsEdiMergerHead emsHead);

	/**
	 * 根据查询类型查询电子帐册成品是否禁用
	 * 
	 * @param type
	 * @param seqNum
	 * @return
	 */
	/**
	 * 根据料件查询 归并关系归并前料件表或成品表
	 * 
	 * @param isMateriel
	 * @param ptNo
	 * @param emsHead
	 * @return
	 */
	public Object findEmsEdiMergerAfterByGNo(Request request,
			boolean isMateriel, String ptNo, EmsEdiMergerHead emsHead);

	public List checkExgCommdityForbidBySeqNum(Request request, String type,
			Integer seqNum, Integer version);

	/**
	 * 根据查询类型查询电子帐册料件是否禁用
	 * 
	 * @param type
	 * @param seqNum
	 * @return
	 */
	public List checkImgCommdityForbidBySeqNum(Request request, String type,
			Integer seqNum);

	EmsHeadH2kExg amountExgDefinePrice(Request request, EmsHeadH2kExg exg,
			EmsHeadH2kVersion version, Double a, Double b);

	EmsHeadH2kExg defaultAmountPrice(Request request, EmsHeadH2kExg exg,
			EmsHeadH2kVersion version);

	List findEmsHeadFasAllExgd(Request request, EmsHeadH2kFas emsH2kFas);

	List downLoadUnitWear(Request request, String bSeqNum, String eSeqNum,
			EmsHeadH2k head, boolean isftoj, List list);

	List findEmsHeadH2kImgExg(Request request, EmsHeadH2k emsHeadH2k,
			boolean isImg, String fields, Object sValue);

	List findEmsHeadH2kImgExgCheck(Request request, EmsHeadH2k emsHeadH2k,
			boolean isImg, String fields);

	String findLastUpdateDateByImgExg(Request request, EmsHeadH2k emsHeadH2k,
			boolean isImg);

	void rewriteEmsHeadH2k(Request request, List list, EmsHeadH2k head);

	void amountFactoryPrice(Request request, List list);

	void changeMateriel(Request request, String ptNo, String name, String spec);

	void changeInner(Request request, String type, Integer seqNum,
			Complex complex, String name, String spec, Unit unit);

	void changeCustomsFourNo(Request request, String type, Integer seqNum,
			String complex, String name);

	void modityEmsEdiMergerBom(Request request, String ptNo, String name,
			String spec, EmsEdiMergerHead head);

	void modityInnergerSeqNum(Request request, String materielType,
			Integer oldPtNo, Integer newPtNo);

	public void modityInnergerSeqNumSingle(Request request,
			String materielType, Integer oldPtNo, Integer newPtNo,
			String materielNo);

	void modityInnergerFourSeqNum(Request request, String materielType,
			Integer oldPtNo, Integer newPtNo);

	EmsEdiMergerImgAfter modityInnergerSeqNumImgId(Request request,
			Integer newPtNo);

	EmsEdiMergerExgAfter modityInnergerSeqNumExgId(Request request,
			Integer newPtNo);

	List synchroEmsEdiImg(Request request, List ls);

	List synchroEmsEdiExg(Request request, List ls);

	List synchroEmsMergerImg(Request request, List ls);

	List synchroEmsMergerExg(Request request, List ls);

	void changeMergerSeqNumImg(Request request, List ls,
			EmsEdiMergerHead emsMerger, Integer seqNum);

	void changeMergerSeqNumExg(Request request, List ls,
			EmsEdiMergerHead emsMerger, Integer seqNum);

	List emsSelectAllSendState(Request request, List ls);

	List emsMergerSelectAllSendState(Request request, List ls);

	List findEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerHead emsEdiMergerHead);

	void batchSaveOrUpdate(Request request, List list);

	List makeEmsEdiMergerExgBomWasteToInt(Request request, List list);// 内部归并BOM损耗取整

	List findBomExport(Request request, EmsEdiMergerHead head,
			String beginSeqNum, String endSeqNum, String Bomversion);

	List findEmsHeadH2kBomExport(Request request, EmsHeadH2k head,
			String beginSeqNum, String endSeqNum, String exgModifyMark,
			String bomModifyMark, boolean jRadioButtonBigBOMState);

	List findEdiBomptNoToBomseqNum(Request request, String[] ptNo,
			String versionId);

	/**
	 * 删除没有料件的版本资料
	 * 
	 */
	void deleteEmsHeadH2kVersionWhileNoImg(Request request,
			EmsHeadH2k emsHeadH2k);

	/**
	 * 判断是否有空的版本
	 * 
	 * @param base
	 * @return
	 */
	public Boolean isExistNullEmsHeadH2kVersion(Request request,
			EmsHeadH2k emsHeadH2k);

	/**
	 * 报关清单出口成品情况统计表
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是EmsBillListExpProductStat型，存放统计报表中的出口成品清单情况统计表
	 */
	List<EmsBillListExpProductStat> findBillListExpProductStat(Request reqeust,
			Date beginDate, Date endDate, String billListState);

	/**
	 * 报关清单进口料件情况统计表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是EmsBillListImpMaterialStat型，存放统计报表中的进口料件清单情况统计表资料
	 */
	List<EmsBillListImpMaterialStat> findBillListImpMaterialStat(
			Request reqeust, Date beginDate, Date endDate,
			String billListState, List billlist);

	/**
	 * 获取帐册的申报方式
	 * 
	 * @param request
	 * @return
	 */
	public boolean getIsEmsH2kSend(Request request);

	/**
	 * 对帐册中,返工复出的数量进行检查
	 * 
	 * @param request
	 * @param base
	 *            要检查的报关单
	 * @return
	 */
	public boolean checkCustomssDeclarationCount(Request request,
			BaseCustomsDeclaration base);

	public EmsEdiMergerImgAfter changeState(Request request,
			EmsEdiMergerImgAfter obj, String state);

	public void changeStateList(Request request, String seqNum, String state,
			boolean isImg);

	public EmsEdiMergerImgBefore changeState(Request request,
			EmsEdiMergerImgBefore obj, String state);

	public EmsEdiMergerExgBefore changeState(Request request,
			EmsEdiMergerExgBefore obj, String state);

	public EmsEdiMergerExgBom changeState(Request request,
			EmsEdiMergerExgBom obj, String state);

	public EmsEdiMergerExgAfter changeState(Request request,
			EmsEdiMergerExgAfter obj, String state);

	public EmsEdiMergerImgAfter alterState(Request request,
			EmsEdiMergerImgAfter obj, String state);

	public EmsEdiMergerImgBefore alterState(Request request,
			EmsEdiMergerImgBefore obj, String state);

	public EmsEdiMergerExgBefore alterState(Request request,
			EmsEdiMergerExgBefore obj, String state);

	public EmsEdiMergerExgBom alterState(Request request,
			EmsEdiMergerExgBom obj, String state);

	public void alterStates(Request request, List<EmsEdiMergerExgBom> list);

	public EmsEdiMergerExgAfter alterState(Request request,
			EmsEdiMergerExgAfter obj, String state);

	public EmsHeadH2kImg changeEMSState(Request request, EmsHeadH2kImg obj,
			String state);

	public EmsHeadH2kExg changeEMSState(Request request, EmsHeadH2kExg obj,
			String state);

	public EmsHeadH2kBom changeEMSState(Request request, EmsHeadH2kBom obj,
			String state);

	public EmsHeadH2kBom alterEMSState(Request request, EmsHeadH2kBom obj,
			String state);

	public EmsHeadH2kImg alterEMSState(Request request, EmsHeadH2kImg obj,
			String state);

	public EmsHeadH2kExg alterEMSState(Request request, EmsHeadH2kExg obj,
			String state);

	public void getControlCustomsDeclaretion(Request request);

	/**
	 * 成品BOM复制到操作
	 * 
	 * @param oldList
	 *            要保存的复制BOM
	 * @return 返回复制的BOM列表
	 */
	public List<EmsEdiMergerExgBom> newAndCope(
			List<EmsEdiMergerExgBom> oldList,
			EmsEdiMergerVersion emsEdiMergerVersion);

	/**
	 * 检查当成品及料件的备案单位都为千克时，单耗累加值不能大于1
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public String checkEmsHeadH2kUnitWasteAdd(Request request,
			EmsHeadH2k emsHeadH2k);

	/**
	 * 查找最大版本号
	 * 
	 * @param request
	 * @param emsHeadH2kExg
	 * @return
	 */
	public Map findMaxEmsHeadH2kVersion(Request request);

	// public Map<String, String> findCheckSaveToEmsHeadH2kData(Request request,
	// List<EmsEdiHeadH2kBomFrom> list, EmsHeadH2k emsHeadH2k);

	Map<Integer, Map<Integer, EmsHeadH2kBom>> getEmsHeadH2kBomByExgSeqNum(
			Request request, EmsHeadH2k emsHeadH2k, Integer exgSeqNum);

	/**
	 * 按日期查找更改了的内部归并
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 2010-09-23 hcl add
	 */
	List getInnerMergeListByDate(boolean isEms, boolean isAdd,
			boolean isChange, EmsEdiMergerHead head, EmsHeadH2k emsHead,
			Date startDate, Date endDate);

	/**
	 * 按内部归并的数据更新归并关系
	 * 
	 * @return 2010-09-23 hcl add
	 */
	List updateEmsEdiMerger(EmsEdiMergerHead head, List innerMergeList);

	/**
	 * 按内部归并的数据更新电子账册
	 * 
	 * @return 2010-09-26 hcl add
	 */
	List updateEmsHeadH2k(EmsHeadH2k emsHead, List innerMergeList);

	/**
	 * 当删除电子账册成品时检查
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 */
	List checkDeleteEmsHeadH2kExg(Request request, List currentRows);

	/**
	 * 当删除电子账册成品时检查
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 * @author sxk
	 */
	List checkDeleteEmsHeadH2kExg(Request request, Integer emsHeadH2kExg,
			Integer version);

	/**
	 * 当删除归并关系料件时检查电子账册
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 */
	List checkToEmsH2kImg(Request request, EmsEdiMergerImgAfter emsImgAfter);

	/**
	 * 当删除归并关系成品时检查电子账册
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 */
	List checkToEmsH2kExg(Request request, EmsEdiMergerExgAfter emsExgAfter);

	/**
	 * 当删除电子账册料件时检查
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 */
	int checkToEmsH2kImgBom(Request request, EmsHeadH2kImg currRowImg);

	/**
	 * 电子帐册报关清单XML导入
	 * 
	 * @param request
	 * @return
	 */
	Map loadBGDFromQPXml(Request request, boolean isImportBGD, boolean isChange);

	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findnameValues(Request request, int type);

	EmsEdiMergerHead findEmsEdiMergerHeadByDeclareState1(Request request,
			String processExe);

	// 开始时间
	// 结束时间
	// 状态
	List findEmsHeadH2kImgExgBySeqNum(Request request, EmsHeadH2k emsHeadH2k,
			boolean isImg, Integer seqNumMin, Integer seqNumMax,
			Date beginDate, Date endDate, String states);

	void importEmsHead2kFromMerger(Request request, EmsHeadH2k emsHeadH2k,
			boolean isImg, Integer seqNumMin, Integer seqNumMax,
			Date beginDate, Date endDate, String states, String emsFrom);

	/**
	 * 判断账册成品表、及单耗表的修改标志是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	String checkEmsExgBomModifyMarkIsUnit(Request request, EmsHeadH2k emsHeadH2k);

	List findRelativeInfoFromMaterial(Request request, EmsHeadH2k emsHeadH2k,
			Integer seqNum, Integer version, Boolean isMaterial);
}