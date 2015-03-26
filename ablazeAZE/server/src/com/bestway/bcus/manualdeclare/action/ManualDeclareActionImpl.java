/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 浏览经营范围 1
 保存经营范围 2
 删除经营范围 3
 变更经营范围 3.1

 浏览归并关系 4
 保存归并关系 5
 删除归并关系 6

 浏览电子帐册 7
 保存电子帐册 8
 删除电子帐册 9
 变更电子帐册 10

 浏览电子帐册分册 11
 保存电子帐册分册 12
 删除电子帐册分册 13
 变更电子帐册分册 14
 */
package com.bestway.bcus.manualdeclare.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.logic.CommonBaseCodeLogic;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
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
import com.bestway.bcus.manualdeclare.logic.DealEmsHistoryLogic;
import com.bestway.bcus.manualdeclare.logic.EmsEdiTrLogic;
import com.bestway.bcus.manualdeclare.logic.ReportLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates implements
 */

@AuthorityClassAnnotation(caption = "电子帐册", index = 6)
@SuppressWarnings("unchecked")
public class ManualDeclareActionImpl extends BaseActionImpl implements
		ManualDeclareAction {
	/**
	 * @return Returns the reportLogic.
	 */
	public ReportLogic getReportLogic() {
		return reportLogic;
	}

	public Map findMaxMaterielBomVersion(Request request) {
		return this.emsEdiTrDao.findMaxMaterielBomVersion();
	}

	/**
	 * @param reportLogic
	 *            The reportLogic to set.
	 */
	public void setReportLogic(ReportLogic reportLogic) {
		this.reportLogic = reportLogic;
	}

	private CommonBaseCodeLogic commonBaseCodeLogic = null;
	private EmsEdiTrDao emsEdiTrDao = null;

	private EmsEdiTrLogic emsEdiTrLogic = null;

	private ReportLogic reportLogic = null;

	private DealEmsHistoryLogic dealEmsHistoryLogic = null;

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-浏览", index = 2.1)
	public List findEmsEdiTrHead(Request request) // 显示经营范围表头
	{
		return emsEdiTrDao.findEmsEdiTrHead();
	}

	public List findEmsEdiMergerExgBefore(Request request, String ptNo) {
		return emsEdiTrDao.findEmsEdiMergerExgBefore(ptNo);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-变更", index = 2.1)
	public EmsEdiTrHead emsEdiChange(Request request) {// 经营范围变更
		return emsEdiTrLogic.emsEdiChange();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-浏览", index = 2.1)
	public List findEmsEdiTrImg(Request request, EmsEdiTrHead emsEdiTrHead) { // 显示经营范围料件表
		return emsEdiTrDao.findEmsEdiTrImg(emsEdiTrHead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-浏览", index = 2.1)
	public List findEmsEdiTrExg(Request request, EmsEdiTrHead emsEdiTrHead) { // 显示经营范围成品表
		return emsEdiTrDao.findEmsEdiTrExg(emsEdiTrHead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-删除", index = 2.1)
	public void deleteEmsEdiTrHead(Request request, EmsEdiTrHead emshead) // 删除经营范围表头
	{
		emsEdiTrDao.deleteEmsEdiTrHead(emshead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-删除", index = 2.1)
	public void deleteEmsEdiTrImg(Request request, EmsEdiTrImg emsImg) { // 删除经营范围料件表
		emsEdiTrDao.deleteEmsEdiTrImg(emsImg);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-删除", index = 2.1)
	public void deleteEmsEdiTrExg(Request request, EmsEdiTrExg emsExg) { // 删除经营范围成品表
		emsEdiTrDao.deleteEmsEdiTrExg(emsExg);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-删除", index = 2.1)
	public void deleteEmsEdiTrImgExg(Request request, EmsEdiTrHead emshead) { // 批量删除经营范围表体
		emsEdiTrDao.deleteEmsEdiTrImgExg(emshead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-保存", index = 2.1)
	public EmsEdiTrHead saveEmsEdiTrHead(Request request, EmsEdiTrHead emshead)
			throws DataAccessException {
		emsEdiTrDao.saveEmsEdiTrHead(emshead); // 保存经营范围表头
		return emshead;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-保存", index = 2.1)
	public EmsEdiTrImg saveEmsEdiTrImg(Request request, EmsEdiTrImg emsImg)
			throws DataAccessException {
		emsEdiTrDao.saveEmsEdiTrImg(emsImg); // 保存经营范围料件表
		return emsImg;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-保存", index = 2.1)
	public EmsEdiTrExg saveEmsEdiTrExg(Request request, EmsEdiTrExg emsExg)
			throws DataAccessException {
		emsEdiTrDao.saveEmsEdiTrExg(emsExg); // 保存经营范围成品表
		return emsExg;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public EmsEdiMergerHead emsMergerAdd(Request request) { // 归并关系表头新增
		return emsEdiTrLogic.emsMergerAdd();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerHead(Request request) { // 显示归并关系表头
		return emsEdiTrDao.findEmsEdiMergerHead();
	}

	public EmsEdiMergerHead findEmsEdiMergerHeadById(Request request, String id) { // 显示归并关系表头
		return (EmsEdiMergerHead) emsEdiTrDao.get(EmsEdiMergerHead.class, id);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public EmsEdiMergerHead saveEmsEdiMergerHead(Request request,
			EmsEdiMergerHead emshead) throws DataAccessException { // 保存归并关系表头
		emsEdiTrDao.saveEmsEdiMergerHead(emshead);
		return emshead;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerHead(Request request, EmsEdiMergerHead emshead) {
		emsEdiTrDao.deleteEmsEdiMergerHead(emshead); // 删除归并关系表头
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerImgExg(Request request,
			EmsEdiMergerHead emshead) {// 批量删除归并关系表体
		emsEdiTrDao.deleteEmsEdiMergerImgExg(emshead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteBomByAfter(Request request, EmsEdiMergerExgAfter emsAfter) {
		emsEdiTrDao.deleteBomByAfter(emsAfter);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerBomByAfter(Request request,
			EmsEdiMergerExgAfter emsAfter) {
		return emsEdiTrDao.findEmsEdiMergerBomByAfter(emsAfter);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerImgAfter(Request request,
			EmsEdiMergerHead emsMerger) { // 显示归并关系归并后料件
		return emsEdiTrDao.findEmsEdiMergerImgAfter(emsMerger);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerImgAfter emsAfter) { // 显示归并关系归并前料件
		return emsEdiTrDao.findEmsEdiMergerImgBefore(emsAfter);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgAfter emsExgAfter) {
		return emsEdiTrDao.findEmsEdiMergerExgBefore(emsExgAfter);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiBom(Request request,
			EmsEdiMergerExgBefore emsExgBefore) { // 显示Bom
		return emsEdiTrDao.findEmsEdiMergerBom(emsExgBefore);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerExgAfter(Request request,
			EmsEdiMergerHead emsMerger) { // 显示归并关系归并后成品
		return emsEdiTrDao.findEmsEdiMergerExgAfter(emsMerger);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public EmsEdiMergerImgAfter saveEmsEdiMergerImgAfter(Request request,
			EmsEdiMergerImgAfter emsAfter) throws DataAccessException { // 保存归并关系归并后料件
		emsEdiTrDao.saveEmsEdiMergerImgAfter(emsAfter);
		return emsAfter;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public EmsEdiMergerImgBefore saveEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerImgBefore emsBefore) throws DataAccessException { // 保存归并关系归并前料件
		emsEdiTrDao.saveEmsEdiMergerImgBefore(emsBefore);
		return emsBefore;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public EmsEdiMergerExgBefore saveEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgBefore emsBefore) throws DataAccessException { // 保存归并关系归并前成品
		emsEdiTrDao.saveEmsEdiMergerExgBefore(emsBefore);
		return emsBefore;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public EmsEdiMergerExgAfter saveEmsEdiMergerExgAfter(Request request,
			EmsEdiMergerExgAfter emsAfter) throws DataAccessException { // 保存归并关系归并后成品
		emsEdiTrDao.saveEmsEdiMergerExgAfter(emsAfter);
		return emsAfter;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public List saveEmsEdiMergerExgAfters(Request request, List exgAfters) {
		return emsEdiTrDao.saveEmsEdiMergerExgAfters(exgAfters);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public List saveEmsEdiMergerImgAfters(Request request, List imgAfters) {
		return emsEdiTrDao.saveEmsEdiMergerImgAfters(imgAfters);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerImgBefore(Request request,
			EmsEdiMergerImgBefore emsBefore) {
		emsEdiTrDao.deleteEmsEdiMergerImgBefore(emsBefore); // 删除归并关系归并前料件
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgBefore emsBefore) {
		emsEdiTrDao.deleteEmsEdiMergerExgBefore(emsBefore); // 删除归并关系归并前成品
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerImgAfter(Request request,
			EmsEdiMergerImgAfter emsAfter) {
		emsEdiTrDao.deleteEmsEdiMergerImgAfter(emsAfter); // 删除归并关系归并后料件
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerExgAfter(Request request,
			EmsEdiMergerExgAfter emsAfter) {
		emsEdiTrDao.deleteEmsEdiMergerExgAfter(emsAfter); // 删除归并关系归并后成品
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerAfterImgToBom(Request request,
			EmsEdiMergerVersion emsEdiMergerVersion, EmsEdiMergerHead emsHead) {
		return emsEdiTrDao.findEmsEdiMergerAfterImgToBom(emsEdiMergerVersion,
				emsHead);// BOM
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-保存", index = 2.2)
	public EmsEdiMergerExgBom saveEmsEdiMergerExgBom(Request request,
			EmsEdiMergerExgBom emsExgBom) throws DataAccessException { // 保存归并关系Bom
		emsEdiTrDao.saveEmsEdiMergerExgBom(emsExgBom);
		return emsExgBom;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerExgBom(Request request,
			EmsEdiMergerExgBom emsExgBom) {
		emsEdiTrDao.deleteEmsEdiMergerExgBom(emsExgBom); // 删除归并关系Bom
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsHeadH2kImgExg(Request request, EmsHeadH2k emshead) { // 批量删除BOM
		emsEdiTrDao.deleteEmsHeadH2kImgExg(emshead);
	}

	/**
	 * @return Returns the emsEdiTrLogic.
	 */
	public EmsEdiTrLogic getEmsEdiTrLogic() {
		return emsEdiTrLogic;
	}

	/**
	 * @param emsEdiTrLogic
	 *            The emsEdiTrLogic to set.
	 */
	public void setEmsEdiTrLogic(EmsEdiTrLogic emsEdiTrLogic) {
		this.emsEdiTrLogic = emsEdiTrLogic;
	}

	public EmsEdiMergerHead emsEdiMergerChange(Request request) {
		return emsEdiTrLogic.emsEdiMergerChange();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List<EmsHeadH2k> findEmsHeadH2k(Request request) // 显示电子帐册表头
	{
		return emsEdiTrDao.findEmsHeadH2k();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public EmsHeadH2k findEmsHeadH2kById(Request request, String emsHeadH2kId) // 显示电子帐册表头
	{
		return emsEdiTrDao.findEmsHeadH2kById(emsHeadH2kId);
	}

	public EmsEdiMergerHead findNewEmsEdiMergerHeadById(Request request,
			String emsEdiMergerHeadId) {
		return emsEdiTrDao.findNewEmsEdiMergerHeadById(emsEdiMergerHeadId);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册商品禁用维护-浏览", index = 2.6)
	public void controlFmCommodityForbidBrowser(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册商品禁用维护-新增", index = 2.6)
	public void controlFmCommodityForbidAdd(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册商品禁用维护-恢复", index = 2.6)
	public void controlFmCommodityForbidBack(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--限制类商品维护", index = 2.8)
	public void restirictCommodity(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--批量修改商品编码与法定单位", index = 2.9)
	public void authUpdateComplex(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-保存", index = 2.3)
	public EmsHeadH2k emsHeadH2kAdd(Request request) { // 新增电子帐册
		return emsEdiTrLogic.emsHeadH2kAdd();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-保存", index = 2.3)
	public EmsHeadH2k saveEmsHeadH2k(Request request, EmsHeadH2k emsHead)
			throws DataAccessException { // 保存电子帐册表头
		emsEdiTrDao.saveEmsHeadH2k(emsHead);
		return emsHead;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-删除", index = 2.3)
	public void deleteEmsHeadH2k(Request request, EmsHeadH2k emsHead) {
		emsEdiTrDao.deleteEmsHeadH2k(emsHead); // 删除电子帐册表头
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadH2kImg(Request request, EmsHeadH2k emsHeadH2k) {
		return emsEdiTrDao.findEmsHeadH2kImg(emsHeadH2k); // 显示电子帐册料件
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadH2kExg(Request request, EmsHeadH2k emsHeadH2k) {
		return emsEdiTrDao.findEmsHeadH2kExg(emsHeadH2k); // 显示电子帐册成品
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadH2kBom(Request request, EmsHeadH2kVersion version) {
		return emsEdiTrDao.findEmsHeadH2kBom(version);
	} // 显示电子帐册Bom表

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findExistVersion(Request request, EmsHeadH2kExg emsHeadH2kExg,
			String version) {
		return emsEdiTrDao.findExistVersion(emsHeadH2kExg, version); // 检查版本号是否重复
	}

	public List findExistVersionNew(Request request, String ptNo, String version) {
		return emsEdiTrDao.findExistVersionNew(ptNo, version); // 检查版本号是否重复
	}

	/**
	 * 根据类型与料号查询内部归并的物料
	 * 
	 * @param request
	 * @param type
	 * @param ptNo
	 * @return
	 */
	public Materiel findInnerMergeDataByPtNo(Request request, String type,
			String ptNo) {
		return emsEdiTrDao.findInnerMergeDataByPtNo(type, ptNo);
	}

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersion(Request request, String ptNo, String cmpVersion) {
		return emsEdiTrDao.getVersion(ptNo, cmpVersion);
	}

	/**
	 * 根据企业版本查询出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersionByCmpVersion(Request request, String ptNo,
			String cmpVersion) {
		return emsEdiTrDao.getVersionByCmpVersion(ptNo, cmpVersion);
	}

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getMaxVersion(Request request, String ptNo) {
		return emsEdiTrDao.getMaxVersion(ptNo);
	}

	public List findExistVersion(Request request,
			EmsEdiMergerExgBefore emsExgBefore, String version) {
		return emsEdiTrDao.findExistVersion(emsExgBefore, version); // 检查版本号是否重复
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadH2kVersion(Request request,
			EmsHeadH2kExg emsHeadH2kExg) {
		return emsEdiTrDao.findEmsHeadH2kVersion(emsHeadH2kExg); // 显示电子帐册成品
	}

	public Map findMaxEmsHeadH2kVersion(Request request) {
		return emsEdiTrDao.findMaxEmsHeadH2kVersion(); // 显示电子帐册成品
	}

	/**
	 * 显示所有版本（成品筛选--归并关系）
	 * 
	 * @param emsHeadH2kExg
	 * @return
	 */
	public List findEmsEdiMergerVersion(Request request,
			EmsEdiMergerExgBefore emsEdiMergerExgBefore) {
		return this.emsEdiTrDao.findEmsEdiMergerVersion(emsEdiMergerExgBefore);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsEdiMergerImgAfterToH2k(Request request,
			EmsEdiMergerHead emsMerger, EmsHeadH2k emsH2k, boolean isNow) {
		return emsEdiTrDao.findEmsEdiMergerImgAfterToH2k(emsMerger, emsH2k,
				isNow);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-保存", index = 2.3)
	public EmsHeadH2kImg saveEmsHeadH2kImg(Request request,
			EmsHeadH2kImg emsH2kImg) throws DataAccessException { // 保存电子帐册料件
		emsEdiTrDao.saveEmsHeadH2kImg(emsH2kImg);
		return emsH2kImg;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsEdiMergerExgAfterToH2k(Request request,
			EmsEdiMergerHead emsMerger, EmsHeadH2k emsH2k, boolean isNow) {
		return emsEdiTrDao.findEmsEdiMergerExgAfterToH2k(emsMerger, emsH2k,
				isNow);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-保存", index = 2.3)
	public EmsHeadH2kExg saveEmsHeadH2kExg(Request request,
			EmsHeadH2kExg emsH2kExg) throws DataAccessException { // 保存电子帐册成品
		emsEdiTrDao.saveEmsHeadH2kExg(emsH2kExg);
		return emsH2kExg;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-保存", index = 2.3)
	public EmsHeadH2kVersion saveEmsHeadH2kVersion(Request request,
			EmsHeadH2kVersion emsH2k) throws DataAccessException { // 保存版本号
		emsEdiTrDao.saveEmsHeadH2kVersion(emsH2k);
		return emsH2k;
	}

	public EmsEdiMergerVersion saveEmsEdiMergerVersion(Request request,
			EmsEdiMergerVersion emsH2k) throws DataAccessException { // 保存版本号
		emsEdiTrDao.saveEmsEdiMergerVersion(emsH2k);
		return emsH2k;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsEdiMergerExgBeforeByAfter(Request request,
			EmsHeadH2kExg emsHeadH2kExg, EmsEdiMergerHead emsMergerHead) {
		return emsEdiTrDao.findEmsEdiMergerExgBeforeByAfter(emsHeadH2kExg,
				emsMergerHead); // 得到归并前成品
	} // 由归并后成品（在电子帐册中）

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiBomByExgBefore(Request request,
			EmsEdiMergerExgBefore emsExgBefore) {
		return emsEdiTrDao.findEmsEdiBomByExgBefore(emsExgBefore); // 得出BOM
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerImgBeforeByPtNo(Request request,
			EmsEdiMergerExgBom emsBom, EmsEdiMergerHead emsHead) {
		return emsEdiTrDao.findEmsEdiMergerImgBeforeByGNo(emsBom, emsHead); // 得到归并前料件
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadH2kBomBySeqNum(Request request, Integer emsBom,
			EmsHeadH2kVersion emsVersion, EmsHeadH2kExg exg) {
		return emsEdiTrDao.findEmsHeadH2kBomBySeqNum(emsBom, emsVersion, exg); // 查找是否在电子帐册存在料件序号
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findBomByExg(Request request, EmsHeadH2kExg emsHeadH2kExg) {
		return emsEdiTrDao.findBomByExg(emsHeadH2kExg); // 显示所有BOM
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-删除", index = 2.3)
	public void deleteVersionAndBom(Request request, EmsHeadH2kExg emsHeadH2kExg) {
		emsEdiTrDao.deleteVersionAndBom(emsHeadH2kExg); // 删除所有版本和BOM
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改修改标志", index = 2.3)
	public void changeEMSState() {
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改申报状态", index = 2.3)
	public void alterEMSState() {
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-保存", index = 2.3)
	public EmsHeadH2kBom saveEmsHeadH2kBom(Request request,
			EmsHeadH2kBom emsH2kBom) throws DataAccessException {
		emsEdiTrDao.saveEmsHeadH2kBom(emsH2kBom);
		return emsH2kBom;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--限制类商品维护-保存", index = 2.8)
	public RestirictCommodity saveRestirictCommodity(Request request,
			RestirictCommodity restirictCommodity) throws DataAccessException {
		emsEdiTrDao.saveRestirictCommodity(restirictCommodity);
		return restirictCommodity;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-删除", index = 2.3)
	public void deleteEmsHeadh2kBomByVersion(Request request,
			EmsHeadH2kVersion version) {
		emsEdiTrDao.deleteEmsHeadh2kBomByVersion(version);
	}

	// 批量删除归并关系BOM表
	public void deleteEmsMergerBomByVersion(Request request,
			EmsEdiMergerVersion version) {
		emsEdiTrDao.deleteEmsMergerBomByVersion(version);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-变更", index = 2.3)
	public EmsHeadH2k emsHeadH2kChange(Request request, String changeState,
			EmsHeadH2k emsHead) {
		return emsEdiTrLogic.emsHeadH2kChange(changeState, emsHead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-删除", index = 2.3)
	public void deleteEmsHeadH2kBom(Request request, EmsHeadH2kBom emsHeadH2kBom) {
		emsEdiTrDao.deleteEmsHeadH2kBom(emsHeadH2kBom);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-删除", index = 2.3)
	public void deleteEmsHeadH2kVersion(Request request,
			EmsHeadH2kVersion emsHeadH2kVersion) {
		emsEdiTrDao.deleteEmsHeadH2kVersion(emsHeadH2kVersion);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-删除", index = 2.3)
	public void deleteEmsHeadH2kImg(Request request, EmsHeadH2kImg emsHeadH2kImg) {
		emsEdiTrDao.deleteEmsHeadH2kImg(emsHeadH2kImg);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-删除", index = 2.3)
	public void deleteEmsHeadH2kExg(Request request, EmsHeadH2kExg emsHeadH2kExg) {
		emsEdiTrDao.deleteEmsHeadH2kExg(emsHeadH2kExg);
	}

	/**
	 * 显示当前正在执行的电子帐册表头
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadH2kInExecuting(Request request) {
		return emsEdiTrDao.findEmsHeadH2kInExecuting();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsEdiImgExgs(Request request, EmsEdiTrHead emsEdiTrHead) {
		return reportLogic.findEmsEdiImgExgs(emsEdiTrHead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEditedEmsEdiImgExgs(Request request,
			EmsEdiTrHead emsEdiTrHead) {
		return reportLogic.findEditedEmsEdiImgExgs(emsEdiTrHead);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadFasAll(Request request, EmsHeadH2kFas emsH2kFas) {
		return reportLogic.findEmsHeadFasAll(emsH2kFas);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findEmsHeadFasAllImgd(Request request, EmsHeadH2kFas emsH2kFas) {
		return reportLogic.findEmsHeadFasAllImgd(emsH2kFas);
	}

	public List findEmsHeadFasAllExgd(Request request, EmsHeadH2kFas emsH2kFas) {
		return reportLogic.findEmsHeadFasAllExgd(emsH2kFas);
	}

	/**
	 * 获得归并关系的表头来自申报状态(非历史记录)
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public EmsEdiMergerHead findEmsEdiMergerHeadByDeclareState(Request request,
			String declareState) {
		return this.emsEdiTrDao
				.findEmsEdiMergerHeadByDeclareState(declareState);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--BOM单耗比较", index = 2.7)
	public EmsEdiMergerHead findEmsEdiMergerHeadByDeclareState1(
			Request request, String declareState) {
		return this.emsEdiTrDao
				.findEmsEdiMergerHeadByDeclareState(declareState);
	}

	/**
	 * 显示归并关系归并后成品和料件
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsEdiMergerImgExg(Request request) {
		return this.emsEdiTrLogic.findEmsEdiMergerImgExg();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public List findEmsHeadH2kImgToBom(Request request, EmsHeadH2k emsHeadH2k,
			EmsHeadH2kVersion version) {
		return emsEdiTrDao.findEmsHeadH2kImgToBom(emsHeadH2k, version);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改修改标志", index = 2.2)
	public void saveModifyMarkState() {
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-浏览", index = 2.4)
	public List findEmsHeadH2kFas(Request request, String emsHeadH2kNo) {
		return emsEdiTrDao.findEmsHeadH2kFas(emsHeadH2kNo);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-保存", index = 2.4)
	public EmsHeadH2kFas saveEmsHeadH2kFas(Request request, EmsHeadH2kFas fas)
			throws DataAccessException {
		emsEdiTrDao.saveEmsHeadH2kFas(fas);
		return fas;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-浏览", index = 2.4)
	public List findEmsHeadH2kImgToFas(Request request, EmsHeadH2k emsHeadH2k,
			EmsHeadH2kFas emsH2kFas) {
		return emsEdiTrDao.findEmsHeadH2kImgToFas(emsHeadH2k, emsH2kFas);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-浏览", index = 2.4)
	public List findEmsHeadH2kExgToFas(Request request, EmsHeadH2k emsHeadH2k,
			EmsHeadH2kFas emsH2kFas) {
		return emsEdiTrDao.findEmsHeadH2kExgToFas(emsHeadH2k, emsH2kFas);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-保存", index = 2.4)
	public EmsHeadH2kFasImg saveEmsHeadH2kFasImg(Request request,
			EmsHeadH2kFasImg fasImg) throws DataAccessException {
		emsEdiTrDao.saveEmsHeadH2kFasImg(fasImg);
		return fasImg;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-保存", index = 2.4)
	public EmsHeadH2kFasExg saveEmsHeadH2kFasExg(Request request,
			EmsHeadH2kFasExg fasExg) throws DataAccessException {
		emsEdiTrDao.saveEmsHeadH2kFasExg(fasExg);
		return fasExg;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-浏览", index = 2.4)
	public List findEmsHeadH2kFasImg(Request request, EmsHeadH2kFas fas) {
		return emsEdiTrDao.findEmsHeadH2kFasImg(fas);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-浏览", index = 2.4)
	public List findEmsHeadH2kFasExg(Request request, EmsHeadH2kFas fas) {
		return emsEdiTrDao.findEmsHeadH2kFasExg(fas);

	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-删除", index = 2.4)
	public void deleteEmsHeadH2kFasImg(Request request, EmsHeadH2kFasImg img) {
		emsEdiTrDao.deleteEmsHeadH2kFasImg(img);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-删除", index = 2.4)
	public void deleteEmsHeadH2kFasExg(Request request, EmsHeadH2kFasExg exg) {
		emsEdiTrDao.deleteEmsHeadH2kFasExg(exg);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-删除", index = 2.4)
	public void deleteEmsHeadH2kFasImgExg(Request request, EmsHeadH2kFas fas) {
		emsEdiTrDao.deleteEmsHeadH2kFasImgExg(fas);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-删除", index = 2.4)
	public void deleteEmsHeadH2kFas(Request request, EmsHeadH2kFas fas) {
		emsEdiTrDao.deleteEmsHeadH2kFas(fas);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-浏览", index = 2.4)
	public List findEmsHeadH2kFasByCop(Request request, String emsHeadH2kNo,
			String copEmsNo) {
		return emsEdiTrDao.findEmsHeadH2kFasByCop(emsHeadH2kNo, copEmsNo);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--帐册分册管理-变更", index = 2.4)
	public EmsHeadH2kFas emsHeadH2FasChange(Request request, EmsHeadH2kFas fas) {
		return emsEdiTrLogic.emsHeadH2FasChange(fas);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public boolean findFactoryNameFromImg(Request request, String ptNo) {
		return emsEdiTrDao.findFactoryNameFromImg(ptNo);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-浏览", index = 2.2)
	public boolean findFactoryNameFromExg(Request request, String ptNo) {
		return emsEdiTrDao.findFactoryNameFromExg(ptNo);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-变更", index = 2.3)
	public void changeChange(Request request) {
		emsEdiTrLogic.changeChange();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--历史变更记录查询", index = 2.5)
	public List findEmsEdiTrHeadAll(Request request) {
		return this.emsEdiTrDao.findEmsEdiTrHeadAll();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--历史变更记录查询", index = 2.5)
	public List findEmsEdiMergerHeadAll(Request request) {
		return this.emsEdiTrDao.findEmsEdiMergerHeadAll();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--历史变更记录查询", index = 2.5)
	public List findEmsHeadH2kAll(Request request) {
		return this.emsEdiTrDao.findEmsHeadH2kAll();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--历史变更记录查询", index = 2.5)
	public List findEmsHeadH2kFasAll(Request request, String emsHeadH2kNo) {
		return this.emsEdiTrDao.findEmsHeadH2kFasAll(emsHeadH2kNo);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerImgBeforeAll(Request request,
			EmsEdiMergerImgAfter emsAfter) {
		this.emsEdiTrDao.deleteEmsEdiMergerImgBeforeAll(emsAfter);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerExgBeforeAll(Request request,
			EmsEdiMergerExgAfter emsAfter) {
		this.emsEdiTrDao.deleteEmsEdiMergerExgBeforeAll(emsAfter);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-删除", index = 2.2)
	public void deleteEmsEdiMergerMergerBomAll(Request request,
			EmsEdiMergerExgBefore emsExgBefore) {
		this.emsEdiTrDao.deleteEmsEdiMergerMergerBomAll(emsExgBefore);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public List findInnerMergeDataByType10ToEms(Request request, String type,
			EmsEdiTrHead emsEdiTrHead, EmsHeadH2k emsHeadH2k) {
		return this.emsEdiTrDao.findInnerMergeDataByType10ToEms(type,
				emsEdiTrHead, emsHeadH2k);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-浏览", index = 2.3)
	public EmsHeadH2k getH2k(Request request, String id) {
		return this.emsEdiTrDao.getH2k(id);
	}

	/**
	 * 查找正在执行的经营范围
	 * 
	 * @return
	 */
	public EmsEdiTrHead findEmsEdiTrHeadIng(Request request) {
		return this.emsEdiTrDao.findEmsEdiTrHeadIng();
	}

	/**
	 * 显示电子帐册所有Bom 排序用于打印
	 */
	public List findEmsHeadH2kBomOrder(Request request, EmsHeadH2k emsHeadH2k) {
		return this.emsEdiTrDao.findEmsHeadH2kBomOrder(emsHeadH2k);
	}

	/**
	 * 显示电子帐册料件变更记录来自变更次数
	 */
	public List findEmsHeadH2kImgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer modifyTimes) {
		return this.emsEdiTrDao
				.findEmsHeadH2kImgChange(emsHeadH2k, modifyTimes);
	}

	/**
	 * 显示归并关系料件变更记录来自变更次数
	 */
	public List findemsEdiMergerHeadImgChange(Request request,
			EmsEdiMergerHead EmsEdiMergerHead, Integer modifyTimes,
			Date beginDate, Date endDate) {
		return this.emsEdiTrDao.findemsEdiMergerHeadImgChange(EmsEdiMergerHead,
				modifyTimes, beginDate, endDate);
	}

	/**
	 * 显示归并关系成品变更记录来自变更次数
	 */
	public List findemsEdiMergerHeadExgChange(Request request,
			EmsEdiMergerHead EmsEdiMergerHead, Integer modifyTimes,
			Date beginDate, Date endDate) {
		return this.emsEdiTrDao.findemsEdiMergerHeadExgChange(EmsEdiMergerHead,
				modifyTimes, beginDate, endDate);
	}

	/**
	 * 显示归并关系BOM变更记录来自变更次数
	 */
	public List findemsEdiMergerHeadBomChange(Request request,
			EmsEdiMergerHead EmsEdiMergerHead, Integer modifyTimes,
			Date beginDate, Date endDate) {
		return this.emsEdiTrDao.findemsEdiMergerHeadBomChange(EmsEdiMergerHead,
				modifyTimes, beginDate, endDate);
	}

	/**
	 * 显示电子帐册料件变更记录来备案序号段
	 */
	public List findEmsHeadH2kImgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer beginNo, Integer endNo, Integer[] seqNumArray) {
		return this.emsEdiTrDao.findEmsHeadH2kImgChange(emsHeadH2k, beginNo,
				endNo, seqNumArray);
	}

	/**
	 * 显示电子帐册成品变更记录来自变更次数
	 */
	public List findEmsHeadH2kExgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer modifyTimes) {
		return this.emsEdiTrDao
				.findEmsHeadH2kExgChange(emsHeadH2k, modifyTimes);
	}

	/**
	 * 显示电子帐册成品变更记录来自变更次数
	 */
	public List findEmsHeadH2kExgChange(Request request, EmsHeadH2k emsHeadH2k,
			Integer beginNo, Integer endNo, Integer[] seqNumArray) {
		return this.emsEdiTrDao.findEmsHeadH2kExgChange(emsHeadH2k, beginNo,
				endNo, seqNumArray);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List<List> findEmsHeadH2kBomChange(Request request,
			EmsHeadH2k emsHeadH2k, Integer modifyTimes) {
		return this.emsEdiTrLogic.findEmsHeadH2kBomChange(emsHeadH2k,
				modifyTimes);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List<List> findEmsHeadH2kBomChange(Request request,
			EmsHeadH2k emsHeadH2k, Integer beginNo, Integer endNo,
			Integer[] seqNumArray) {
		return this.emsEdiTrLogic.findEmsHeadH2kBomChange(emsHeadH2k, beginNo,
				endNo, seqNumArray);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List findEmsHeadH2kBomSingleVersionChange(Request request,
			EmsHeadH2k emsHeadH2k, Map<Integer, List<Integer>> mapAllSeqNum) {
		return this.emsEdiTrLogic.findEmsHeadH2kBomSingleVersionChange(
				emsHeadH2k, mapAllSeqNum);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List<List> findEmsHeadH2kBomByDate(Request request,
			EmsHeadH2k emsHeadH2k, Date beginDate, Date endDate,
			boolean isDeclared) {
		return this.emsEdiTrLogic.findEmsHeadH2kBomByDate(emsHeadH2k,
				beginDate, endDate, isDeclared);
	}

	public void modityComplex(Request request, boolean isImg, String seqNum,
			Complex complex) {
		this.emsEdiTrLogic.modityComplex(isImg, seqNum, complex);
	}

	public void modityBomFromComplex(Request request, String seqNum,
			Complex complex, EmsHeadH2k emsHeadh2k) {
		this.emsEdiTrLogic.modityBomFromComplex(seqNum, complex, emsHeadh2k);
	}

	public void modityBomFromPrice(Request request, String seqNum,
			EmsHeadH2kImg emsAfter, EmsHeadH2k emsHeadh2k) {
		this.dealEmsHistoryLogic.modityBomFromPrice(seqNum, emsAfter,
				emsHeadh2k);
	}

	// /**
	// * 查询电子帐册备案料件信息(注意：商品禁用使用到)
	// */
	// public List findEdiMaterielInfo(Request request, boolean isMaterial) {
	// return this.emsEdiTrDao.findEdiMaterielInfo(isMaterial);
	// }

	// 得到禁用商品
	public List getTempEmsEdiH2kForBid(Request request, boolean isMaterial) {
		return this.emsEdiTrLogic.getTempEmsEdiH2kForBid(isMaterial);
	}

	public List addCommdityForbid(Request request, List tempList,
			boolean isMateriel) {
		return this.emsEdiTrLogic.addCommdityForbid(tempList, isMateriel);
	}

	public List addCommdityForbidZX(Request request, EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList, List<Integer> versionList,
			boolean isMateriel) {
		return this.emsEdiTrLogic.addCommdityForbidZX(emsHeadH2k, seqNumList,
				versionList, isMateriel);
	}

	public List addRestirictCommodity(Request request, List tempList,
			boolean isMateriel) {
		return this.emsEdiTrLogic.addRestirictCommodity(tempList, isMateriel);
	}

	public void changeEmsEdiForbid(Request request, String seqNum,
			boolean isMateriel, boolean isForbid, String version) {
		this.emsEdiTrLogic.changeEmsEdiForbid(seqNum, isMateriel, isForbid,
				version);
	}

	public void deleteCommdityForbid(Request request, CommdityForbid obj) {
		this.emsEdiTrLogic.deleteCommdityForbid(obj);
	}

	public void deleteRestirictCommodity(Request request, RestirictCommodity obj) {
		this.emsEdiTrDao.deleteRestirictCommodity(obj);
	}

	public List findCommdityForbid(Request request, boolean isMateriel,
			String serialNo, Date beginDate, Date endDate) {
		return this.emsEdiTrDao.findCommdityForbid(isMateriel, beginDate,
				serialNo, endDate);
	}

	public List findCommdityForbid(Request request, boolean isMateriel,
			String serialNo, Date beginDate, Date endDate, Boolean isForbid) {
		return this.emsEdiTrDao.findCommdityForbid(isMateriel, beginDate,
				serialNo, endDate, isForbid);
	}

	public List findCommdityForbidHis(Request request, boolean isMateriel,
			String serialNo, Date beginDate, Date endDate) {
		return this.emsEdiTrDao.findCommdityForbidHis(isMateriel, beginDate,
				serialNo, endDate);
	}

	public List findRestirictCommodity(Request request, boolean isMateriel,
			Date beginDate, Date endDate) {
		return this.emsEdiTrDao.findRestirictCommodity(isMateriel, beginDate,
				endDate);
	}

	public void deleteCommdityForbidZX(Request request, EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList, List<Integer> versionList,
			boolean isMateriel) {
		this.emsEdiTrLogic.deleteCommdityForbidZX(emsHeadH2k, seqNumList,
				versionList, isMateriel);
	}

	/**
	 * 显示归并关系BOM （由一条版本号筛选）
	 * 
	 * @param version
	 * @return
	 */
	public List findEmsEdiMergerBom(Request request, EmsEdiMergerVersion version) {
		return this.emsEdiTrDao.findEmsEdiMergerBom(version);
	}

	public List findManualDescBom(Request req, String spNo, int version) {
		return this.emsEdiTrDao.findmanualdescbom(spNo, version);
	}

	public void deleteEmsEdiMergerVersion(Request request,
			EmsEdiMergerVersion emsEdiMergerVersion) {
		this.emsEdiTrDao.deleteEmsEdiMergerVersion(emsEdiMergerVersion);
	}

	public List getEmsUnitWearList(Request request, String bSeqNum,
			String eSeqNum, int pageNum, EmsHeadH2k head) {
		return this.emsEdiTrLogic.getEmsUnitWearList(bSeqNum, eSeqNum, pageNum,
				head);
	}

	public List downLoadUnitWear(Request request, String bSeqNum,
			String eSeqNum, EmsHeadH2k head, boolean isftoj, List list) {
		return this.emsEdiTrLogic.downLoadUnitWear(bSeqNum, eSeqNum, head,
				isftoj, list);
	}

	public List findEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerExgAfter emsExgAfter, int firstIndex) {
		return this.emsEdiTrDao.findEmsEdiMergerExgBefore(emsExgAfter,
				firstIndex);
	}

	/**
	 * 归并关系新增BOM来自产品结构BOM
	 * 
	 * @param request
	 * @param exg
	 * @return
	 */
	public List addBomFromCustomsBom(Request request,
			EmsEdiMergerExgBefore exg, Integer version) {
		return this.emsEdiTrLogic.addBomFromCustomsBom(exg, version);
	}

	/**
	 * 保存已转换的报关常用工厂BOM
	 */
	public void addBomFromSelectBom(Request request, EmsEdiMergerExgBefore exg,
			EmsEdiMergerVersion ediMergerVersion) {
		this.emsEdiTrLogic.addBomFromSelectBom(exg, ediMergerVersion);
	}

	public List findEmsEdiMergerBomByAfterPage(Request request, Integer seqNum,
			int firstIndex, String declareState, Integer version, String ptNo) {
		return this.emsEdiTrDao.findEmsEdiMergerBomByAfterPage(seqNum,
				firstIndex, declareState, version, ptNo);
	}

	public List findEmsEdiMergerBomPtNoByAfter(Request request, Integer seqNum,
			String declareState, Integer version) {
		return emsEdiTrDao.findEmsEdiMergerBomPtNoByAfter(seqNum, declareState,
				version);
	}

	public List bomImport(Request request, EmsEdiMergerVersion versionObj,
			List list, EmsEdiMergerExgAfter exgAfter) {
		return this.emsEdiTrLogic.bomImport(versionObj, list, exgAfter);
	}

	/**
	 * 根据料件查询归并关系前的归并序号
	 * 
	 * @param isMateriel
	 * @param ptNo
	 * @param emsHead
	 * @return
	 */
	public Integer findSeqNumEmsEdiMergerAfterByGNo(Request request,
			boolean isMateriel, String ptNo, EmsEdiMergerHead emsHead) {
		return emsEdiTrDao.findSeqNumEmsEdiMergerAfterByGNo(isMateriel, ptNo,
				emsHead);
	}

	/**
	 * 根据料件查询 归并关系归并前料件表或成品表
	 * 
	 * @param isMateriel
	 * @param ptNo
	 * @param emsHead
	 * @return
	 */
	public Object findEmsEdiMergerAfterByGNo(Request request,
			boolean isMateriel, String ptNo, EmsEdiMergerHead emsHead) {
		return emsEdiTrDao
				.findEmsEdiMergerAfterByGNo(isMateriel, ptNo, emsHead);
	}

	/**
	 * 根据查询类型查询电子帐册成品是否禁用
	 * 
	 * @param type
	 * @param seqNum
	 * @return
	 */
	public List checkExgCommdityForbidBySeqNum(Request request, String type,
			Integer seqNum, Integer version) {
		return emsEdiTrDao
				.checkExgCommdityForbidBySeqNum(type, seqNum, version);
	}

	/**
	 * 根据查询类型查询电子帐册料件是否禁用
	 * 
	 * @param type
	 * @param seqNum
	 * @return
	 */
	public List checkImgCommdityForbidBySeqNum(Request request, String type,
			Integer seqNum) {
		return emsEdiTrDao.checkImgCommdityForbidBySeqNum(type, seqNum);
	}

	// public List bomImport1(Request request, EmsEdiMergerHead
	// emsEdiMergerHead,
	// List list) {
	// return this.emsEdiTrLogic.bomImport1(emsEdiMergerHead, list);
	// }

	// 不要的方法保存导入
	public int[] saveToEmsMerger(Request request,
			EmsEdiMergerVersion versionObj, List list, boolean isChange) {
		return this.emsEdiTrLogic.saveToEmsMerger(versionObj, list, isChange);
	}

	/**
	 * 保存归并关系BOM导入
	 */
	public List saveToEmsMerger(Request request, EmsEdiMergerExgBefore exg,
			EmsEdiMergerBomFrom obj, EmsEdiMergerHead emsEdiMergerHead,
			boolean isChange) {
		/*
		 * List list = null; try { list =
		 * this.emsEdiTrLogic.saveToEmsMerger(exg, obj, emsEdiMergerHead,
		 * isChange); } catch (Exception e) { list = new ArrayList();
		 * list.add(e.getMessage()); }
		 */

		return this.emsEdiTrLogic.saveToEmsMerger(exg, obj, emsEdiMergerHead,
				isChange);
	}

	public void importEmsHead2kFromMerger(Request request,
			EmsHeadH2k emsHeadH2k, boolean isImg, Integer seqNumMin,
			Integer seqNumMax, Date beginDate, Date endDate, String states,
			String emsFrom) {
		emsEdiTrLogic.importEmsHead2kFromMerger(emsHeadH2k, isImg, seqNumMin,
				seqNumMax, beginDate, endDate, states, emsFrom);
	}

	// 电子帐册转换单耗
	public List addEmsH2kDm(Request request, Integer seqNum,
			String declareState, Integer versionI, String ptNo) {
		return this.emsEdiTrLogic.addEmsH2kDm(seqNum, declareState, versionI,
				ptNo);
	}

	// 保存电子帐册转单耗
	public int[] saveEmsBomFromMergerBom(Request request, List list,
			EmsHeadH2kExg exg) {
		return this.emsEdiTrLogic.saveEmsBomFromMergerBom(list, exg);
	}

	// 返回参数设定
	@AuthorityFunctionAnnotation(caption = "参数设置", index = 0)
	public void getBcusParaPurview(Request request) {

	}

	// 报关单特殊操作
	// @AuthorityFunctionAnnotation(caption = "报关单--强制", index = 3)
	public void getControlCustomsDeclaretion(Request request) {

	}

	public String getBpara(Request request, int type) {
		return this.emsEdiTrDao.getBpara(type);
	}

	// 保存参数设定
	public void saveBpara(Request request, int type, String sValue) {
		this.emsEdiTrLogic.saveBpara(type, sValue);
	}

	public void changeMergerflag(Request request) {
		this.emsEdiTrLogic.changeMergerflag();
	}

	// 开始导入（检查是否备案）---电子帐册单耗导入
	public List bomEmsImport(Request request, EmsHeadH2k emsHeadH2k, List list) {
		return this.emsEdiTrLogic.bomEmsImport(emsHeadH2k, list);
	}

	// 保存导入电子帐册文本导入
	public int[] saveToEmsHeadH2k(Request request, EmsHeadH2kVersion vobj,
			List list) {
		return this.emsEdiTrLogic.saveToEmsHeadH2k(vobj, list);
	}

	public int[] saveToEmsHeadH2k(Request request, EmsHeadH2k emsHeadH2k,
			List list, boolean cbIsOverwrite) {
		return this.emsEdiTrLogic.saveToEmsHeadH2k(emsHeadH2k, list,
				cbIsOverwrite);
	}

	public void editImgBeforeList(Request request, EmsEdiMergerImgAfter emsAfter) {
		this.emsEdiTrLogic.editImgBeforeList(emsAfter);
	}

	public void editExgBeforeList(Request request, EmsEdiMergerExgAfter emsAfter) {
		this.emsEdiTrLogic.editExgBeforeList(emsAfter);
	}

	public void editversion(Request request, EmsHeadH2k head) {
		this.emsEdiTrLogic.editversion(head);
	}

	public DealEmsHistoryLogic getDealEmsHistoryLogic() {
		return dealEmsHistoryLogic;
	}

	public void setDealEmsHistoryLogic(DealEmsHistoryLogic dealEmsHistoryLogic) {
		this.dealEmsHistoryLogic = dealEmsHistoryLogic;
	}

	public void dealEmsHistory(Request request) throws SQLException {
		this.dealEmsHistoryLogic.dealEmsHistory();
	}

	public void changeCustomsFromMateriel(Request request) {
		this.dealEmsHistoryLogic.changeCustomsFromMateriel();
	}

	public void changeChangeNum(Request request, EmsHeadH2k head) {
		this.dealEmsHistoryLogic.changeChangeNum(head);
	}

	public void dealEmsHistory2(Request request) throws SQLException {
		this.dealEmsHistoryLogic.dealEmsHistory2();
	}

	/**
	 * 显示电子帐册所有Bom
	 */
	public List findEmsHeadH2kBomByCpSeqNum(Request request, Integer seqNum,
			EmsHeadH2k emsHeadH2k, Integer version) {
		return this.emsEdiTrDao.findEmsHeadH2kBomByCpSeqNum(seqNum, emsHeadH2k,
				version);
	}

	public List findBomUnitWearCompare(List exglist, EmsEdiMergerHead head,
			Integer exgSeqNum, Integer version) {
		return this.emsEdiTrLogic.findBomUnitWearCompare(exglist, head,
				exgSeqNum, version);
	}

	// 初始化申报状态
	public void initSendState(Request request, EmsEdiMergerHead head) {
		this.emsEdiTrLogic.initSendState(head);
	}

	// 修改归并后商品编码自动修改归并前商品编码
	public void changeBeforeComplexUnit(Request request,
			EmsEdiMergerImgAfter imgafter, String scomplex, String ncomplex,
			String sunit, String nunit, boolean isMainIsChange) {
		emsEdiTrLogic.changeBeforeComplexUnit(imgafter, scomplex, ncomplex,
				sunit, nunit, isMainIsChange);
	}

	public void changeBeforeComplexUnit(Request request,
			EmsEdiMergerExgAfter exgafter, String scomplex, String ncomplex,
			String sunit, String nunit) {
		emsEdiTrLogic.changeBeforeComplexUnit(exgafter, scomplex, ncomplex,
				sunit, nunit);
	}

	public void changetemp(Request request, EmsEdiMergerHead head) {
		dealEmsHistoryLogic.changetemp(head);
	}

	// 初始化申报状态
	public void initEmsSendState(Request request, EmsHeadH2k head) {
		emsEdiTrLogic.initEmsSendState(head);
	}

	public List findEmsEdiMergerImgBeforeByGNo(Request request,
			boolean isMateriel, String ptNo, EmsEdiMergerHead emsHead) {
		return emsEdiTrDao.findEmsEdiMergerImgBeforeByGNo(isMateriel, ptNo,
				emsHead);
	}

	// 计算自定义成品单价
	public EmsHeadH2kExg amountExgDefinePrice(Request request,
			EmsHeadH2kExg exg, EmsHeadH2kVersion version, Double a, Double b) {
		return emsEdiTrLogic.amountExgDefinePrice(exg, version, a, b);
	}

	// 默认计算成品单价
	public EmsHeadH2kExg defaultAmountPrice(Request request, EmsHeadH2kExg exg,
			EmsHeadH2kVersion version) {
		return emsEdiTrLogic.defaultAmountPrice(exg, version);
	}

	/**
	 * 显示所有电子帐册料件
	 */
	public List findEmsHeadH2kImgExg(Request request, EmsHeadH2k emsHeadH2k,
			boolean isImg, String fields, Object sValue) {
		return this.emsEdiTrDao.findEmsHeadH2kImgExg(emsHeadH2k, isImg, fields,
				sValue);
	}

	public List findEmsHeadH2kImgExgBySeqNum(Request request,
			EmsHeadH2k emsHeadH2k, boolean isImg, Integer seqNumMin,
			Integer seqNumMax, Date beginDate, Date endDate, String states) {
		return this.emsEdiTrDao.findEmsHeadH2kImgExgBySeqNum(emsHeadH2k, isImg,
				seqNumMin, seqNumMax, beginDate, endDate, states);
	}

	public List findEmsHeadH2kImgExgCheck(Request request,
			EmsHeadH2k emsHeadH2k, boolean isImg, String fields) {
		return this.emsEdiTrDao.findEmsHeadH2kImgExgCheck(emsHeadH2k, isImg,
				fields);
	}

	public String findLastUpdateDateByImgExg(Request request,
			EmsHeadH2k emsHeadH2k, boolean isImg) {
		return this.emsEdiTrDao.findLastUpdateDateByImgExg(emsHeadH2k, isImg);
	}

	public void rewriteEmsHeadH2k(Request request, List list, EmsHeadH2k head) {
		this.emsEdiTrLogic.rewriteEmsHeadH2k(list, head);
	}

	public void amountFactoryPrice(Request request, List list) {
		this.emsEdiTrLogic.amountFactoryPrice(list);
	}

	public void changeMateriel(Request request, String ptNo, String name,
			String spec) {
		this.dealEmsHistoryLogic.changeMateriel(ptNo, name, spec);
	}

	public void changeInner(Request request, String type, Integer seqNum,
			Complex complex, String name, String spec, Unit unit) {
		this.dealEmsHistoryLogic.changeInner(type, seqNum, complex, name, spec,
				unit);
	}

	public void changeCustomsFourNo(Request request, String type,
			Integer seqNum, String complex, String name) {
		this.dealEmsHistoryLogic.changeCustomsFourNo(complex, name, type,
				seqNum);
	}

	public void modityEmsEdiMergerBom(Request request, String ptNo,
			String name, String spec, EmsEdiMergerHead head) {
		this.emsEdiTrLogic.modityEmsEdiMergerBom(ptNo, name, spec, head);
	}

	// 同步到内部归并Ten关系
	public void modityInnergerSeqNum(Request request, String materielType,
			Integer oldPtNo, Integer newPtNo) {
		this.emsEdiTrLogic.changeCustomsSeqNum(materielType, oldPtNo, newPtNo);
	}

	// 同步到内部归并Ten关系 2011 - 7 - 19
	public void modityInnergerSeqNumSingle(Request request,
			String materielType, Integer oldPtNo, Integer newPtNo,
			String materielNo) {
		this.emsEdiTrLogic.changeInner(materielType, oldPtNo, newPtNo,
				materielNo);
	}

	// 同步到内部归并Four关系
	public void modityInnergerFourSeqNum(Request request, String materielType,
			Integer oldPtNo, Integer newPtNo) {
		this.emsEdiTrLogic.changeCustomsSeqNumFourNo(materielType, oldPtNo,
				newPtNo);
	}

	// 获得Id
	public EmsEdiMergerImgAfter modityInnergerSeqNumImgId(Request request,
			Integer newPtNo) {
		return this.emsEdiTrDao.modityInnergerSeqNumImgId(newPtNo);
	}

	// 获得Id
	public EmsEdiMergerExgAfter modityInnergerSeqNumExgId(Request request,
			Integer newPtNo) {
		return this.emsEdiTrDao.modityInnergerSeqNumExgId(newPtNo);
	}

	// 同步电子帐册料件
	public List synchroEmsEdiImg(Request request, List ls) {
		return this.dealEmsHistoryLogic.synchroEmsEdiImg(ls);
	}

	// 同步电子帐册成品
	public List synchroEmsEdiExg(Request request, List ls) {
		return this.dealEmsHistoryLogic.synchroEmsEdiExg(ls);
	}

	// 同步归并关系料件
	public List synchroEmsMergerImg(Request request, List ls) {
		return this.dealEmsHistoryLogic.synchroEmsMergerImg(ls);
	}

	// 同步归并关系成品
	public List synchroEmsMergerExg(Request request, List ls) {
		return this.dealEmsHistoryLogic.synchroEmsMergerExg(ls);
	}

	// 归并关系归并前重新排序
	public void changeMergerSeqNumImg(Request request, List ls,
			EmsEdiMergerHead emsMerger, Integer seqNum) {
		this.dealEmsHistoryLogic.changeMergerSeqNumImg(ls, emsMerger, seqNum);
	}

	// 归并关系归并前重新排序
	public void changeMergerSeqNumExg(Request request, List ls,
			EmsEdiMergerHead emsMerger, Integer seqNum) {
		this.dealEmsHistoryLogic.changeMergerSeqNumExg(ls, emsMerger, seqNum);
	}

	// 电子帐册单耗全选
	public List emsSelectAllSendState(Request request, List ls) {
		return this.dealEmsHistoryLogic.emsSelectAllSendState(ls);
	}

	public List finEmsHeadH2KBomFromBeginSeqToEndSeq(Request request,
			String beginSeq, String endSeq, int pageNum, EmsHeadH2k head) {

		return emsEdiTrDao.finEmsHeadH2KBomFromBeginSeqToEndSeq(beginSeq,
				endSeq, pageNum, head);

	}

	// 归并关系BOM全选
	public List emsMergerSelectAllSendState(Request request, List ls) {
		return this.dealEmsHistoryLogic.emsMergerSelectAllSendState(ls);
	}

	public List findEmsEdiMergerExgBefore(Request request,
			EmsEdiMergerHead emsEdiMergerHead) {
		return this.emsEdiTrDao.findEmsEdiMergerExgBefore(emsEdiMergerHead);
	}

	public void batchSaveOrUpdate(Request request, List list) {
		this.emsEdiTrDao.batchSaveOrUpdate(list);
	}

	public List findBomExport(Request request, EmsEdiMergerHead head,
			String beginSeqNum, String endSeqNum, String Bomversion) {
		return this.emsEdiTrLogic.findBomExport(head, beginSeqNum, endSeqNum,
				Bomversion);
	}

	public List findEmsHeadH2kBomExport(Request request, EmsHeadH2k head,
			String beginSeqNum, String endSeqNum, String exgModifyMark,
			String bomModifyMark, boolean jRadioButtonBigBOMState) {
		return this.emsEdiTrLogic.findEmsHeadH2kBomExport(head, beginSeqNum,
				endSeqNum, exgModifyMark, bomModifyMark,
				jRadioButtonBigBOMState);
	}

	public List makeEmsEdiMergerExgBomWasteToInt(Request request, List list) {
		return this.emsEdiTrLogic.makeEmsEdiMergerExgBomWasteToInt(list);
	}

	/**
	 * 删除没有料件的版本资料
	 * 
	 */
	public void deleteEmsHeadH2kVersionWhileNoImg(Request request,
			EmsHeadH2k emsHeadH2k) {
		this.emsEdiTrLogic.deleteEmsHeadH2kVersionWhileNoImg(emsHeadH2k);
	}

	/**
	 * 判断是否有空的版本
	 * 
	 * @param base
	 * @return
	 */
	public Boolean isExistNullEmsHeadH2kVersion(Request request,
			EmsHeadH2k emsHeadH2k) {
		return this.emsEdiTrLogic.isExistNullEmsHeadH2kVersion(emsHeadH2k);
	}

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
	public List<EmsBillListExpProductStat> findBillListExpProductStat(
			Request reqeust, Date beginDate, Date endDate, String billListState) {
		return this.reportLogic.findBillListExpProductStat(beginDate, endDate,
				billListState);
	}

	/**
	 * 报关清单进口料件情况统计表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是EmsBillListImpMaterialStat型，存放统计报表中的进口料件清单情况统计表资料
	 */
	public List<EmsBillListImpMaterialStat> findBillListImpMaterialStat(
			Request reqeust, Date beginDate, Date endDate,
			String billListState, List billList) {
		return this.reportLogic.findBillListImpMaterialStat(beginDate, endDate,
				billListState, billList);
	}

	/**
	 * 获取帐册的申报方式
	 * 
	 * @param request
	 * @return
	 */
	public boolean getIsEmsH2kSend(Request request) {
		return emsEdiTrDao.getIsEmsH2kSend();
	}

	/**
	 * 对帐册中,返工复出的数量进行检查
	 * 
	 * @param request
	 * @param base
	 *            要检查的报关单
	 * @return
	 */
	public boolean checkCustomssDeclarationCount(Request request,
			BaseCustomsDeclaration base) {
		return this.emsEdiTrLogic.checkCustomssDeclarationCount(base);
	}

	public RestirictCommodity findRerictCommodity(Request request,
			Boolean ImpExpType, String seqNum) {
		return this.emsEdiTrDao.findRerictCommodity(ImpExpType, seqNum);
	}

	public List<RestirictCommodity> findRerictCommoditys(Request request,
			Boolean ImpExpType, List<Integer> seqNums) {
		return this.emsEdiTrDao.findRerictCommoditys(ImpExpType, seqNums);
	}

	//
	// public List getRestirictCommodity(Request request, boolean isMaterial) {
	// return this.emsEdiTrLogic.getRestirictCommodity(isMaterial);
	// }

	public List findEdiBomptNoToBomseqNum(Request request, String[] ptNo,
			String versionId) {
		return this.emsEdiTrDao.findEdiBomptNoToBomseqNum(ptNo, versionId);
	}

	public List findAllVersion(Request request) {
		return this.emsEdiTrDao.findAllVersion();
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改申报状态", index = 2.2)
	public EmsEdiMergerImgAfter alterState(Request request,
			EmsEdiMergerImgAfter obj, String state) {
		// TODO Auto-generated method stub
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsEdiMergerImgAfter(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改申报状态", index = 2.2)
	public EmsEdiMergerImgBefore alterState(Request request,
			EmsEdiMergerImgBefore obj, String state) {
		// TODO Auto-generated method stub
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsEdiMergerImgBefore(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改申报状态", index = 2.2)
	public EmsEdiMergerExgBefore alterState(Request request,
			EmsEdiMergerExgBefore obj, String state) {
		// TODO Auto-generated method stub
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsEdiMergerExgBefore(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改申报状态", index = 2.2)
	public EmsEdiMergerExgBom alterState(Request request,
			EmsEdiMergerExgBom obj, String state) {
		// TODO Auto-generated method stub
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsEdiMergerExgBom(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改申报状态", index = 2.2)
	public void alterStates(Request request, List<EmsEdiMergerExgBom> list) {
		emsEdiTrDao.saveEmsEdiMergerExgBoms(list);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改申报状态", index = 2.2)
	public EmsEdiMergerExgAfter alterState(Request request,
			EmsEdiMergerExgAfter obj, String state) {
		// TODO Auto-generated method stub
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsEdiMergerExgAfter(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改修改标志", index = 2.2)
	public EmsEdiMergerImgAfter changeState(Request request,
			EmsEdiMergerImgAfter obj, String state) {
		// TODO Auto-generated method stub
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsEdiMergerImgAfter(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改修改标志", index = 2.2)
	public void changeStateList(Request request, String seqNum, String state,
			boolean isImg) {
		// TODO Auto-generated method stub
		emsEdiTrDao.saveEmsEdiMergerImgAfterToBefore(state, seqNum, isImg);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改修改标志", index = 2.2)
	public EmsEdiMergerImgBefore changeState(Request request,
			EmsEdiMergerImgBefore obj, String state) {
		// TODO Auto-generated method stub
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsEdiMergerImgBefore(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改修改标志", index = 2.2)
	public EmsEdiMergerExgBefore changeState(Request request,
			EmsEdiMergerExgBefore obj, String state) {
		// TODO Auto-generated method stub
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsEdiMergerExgBefore(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改修改标志", index = 2.2)
	public EmsEdiMergerExgBom changeState(Request request,
			EmsEdiMergerExgBom obj, String state) {
		// TODO Auto-generated method stub
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsEdiMergerExgBom(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--归并关系管理-更改修改标志", index = 2.2)
	public EmsEdiMergerExgAfter changeState(Request request,
			EmsEdiMergerExgAfter obj, String state) {
		// TODO Auto-generated method stub
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsEdiMergerExgAfter(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改申报状态", index = 2.3)
	public EmsHeadH2kImg alterEMSState(Request request, EmsHeadH2kImg obj,
			String state) {
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsHeadH2kImg(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改申报状态", index = 2.3)
	public EmsHeadH2kExg alterEMSState(Request request, EmsHeadH2kExg obj,
			String state) {
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsHeadH2kExg(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改修改标志", index = 2.3)
	public EmsHeadH2kImg changeEMSState(Request request, EmsHeadH2kImg obj,
			String state) {
		// TODO Auto-generated method stub
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsHeadH2kImg(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改修改标志", index = 2.3)
	public EmsHeadH2kExg changeEMSState(Request request, EmsHeadH2kExg obj,
			String state) {
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsHeadH2kExg(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改申报状态", index = 2.3)
	public EmsHeadH2kBom alterEMSState(Request request, EmsHeadH2kBom obj,
			String state) {
		obj.setSendState(Integer.valueOf(state));
		emsEdiTrDao.saveEmsHeadH2kBom(obj);
		return obj;
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--电子帐册管理-更改修改标志", index = 2.3)
	public EmsHeadH2kBom changeEMSState(Request request, EmsHeadH2kBom obj,
			String state) {
		obj.setModifyMark(state);
		emsEdiTrDao.saveEmsHeadH2kBom(obj);
		return obj;
	}

	/**
	 * 成品BOM复制到操作
	 * 
	 * @param oldList
	 *            要保存的复制BOM
	 * @return 返回复制的BOM列表
	 */
	public List<EmsEdiMergerExgBom> newAndCope(
			List<EmsEdiMergerExgBom> oldList,
			EmsEdiMergerVersion emsEdiMergerVersion) {
		return emsEdiTrLogic.newAndCope(oldList, emsEdiMergerVersion);
	}

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
			EmsHeadH2k emsHeadH2k) {
		return this.emsEdiTrLogic.checkEmsHeadH2kUnitWasteAdd(emsHeadH2k);
	}

	// public Map<String, String> findCheckSaveToEmsHeadH2kData(Request request,
	// List<EmsEdiHeadH2kBomFrom> list, EmsHeadH2k emsHeadH2k) {
	// return this.emsEdiTrLogic.findCheckSaveToEmsHeadH2kData(list,
	// emsHeadH2k);
	// }

	public Map<Integer, Map<Integer, EmsHeadH2kBom>> getEmsHeadH2kBomByExgSeqNum(
			Request request, EmsHeadH2k emsHeadH2k, Integer exgSeqNum) {
		return this.emsEdiTrLogic.getEmsHeadH2kBomByExgSeqNum(emsHeadH2k,
				exgSeqNum);
	}

	/**
	 * 按日期查找更改了的内部归并
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 2010-09-23 hcl add
	 */
	public List getInnerMergeListByDate(boolean isEms, boolean isAdd,
			boolean isChange, EmsEdiMergerHead head, EmsHeadH2k emsHead,
			Date startDate, Date endDate) {
		return this.emsEdiTrLogic.getInnerMergeListByDate(isEms, isAdd,
				isChange, head, emsHead, startDate, endDate);
	}

	/**
	 * 按日期查找更改了的内部归并
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 2010-09-23 hcl add
	 */
	public List updateEmsEdiMerger(EmsEdiMergerHead head, List innerMergeList) {
		return this.emsEdiTrLogic.updateEmsEdiMerger(head, innerMergeList);
	}

	/**
	 * 按日期查找更改了的内部归并
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 2010-09-26 hcl add
	 */
	public List updateEmsHeadH2k(EmsHeadH2k emsHead, List innerMergeList) {
		return this.emsEdiTrLogic.updateEmsEdiMerger(emsHead, innerMergeList);
	}

	public List checkDeleteEmsHeadH2kExg(Request request, List list) {
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(i);
			result.addAll(emsEdiTrDao.checkDeleteEmsHeadH2kImgExg(
					exg.getSeqNum(), MaterielType.FINISHED_PRODUCT));
		}
		if (result == null)
			result = new ArrayList();
		return result;
	}

	/**
	 * 当删除电子账册成品时检查
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 * @author sxk
	 */
	public List checkDeleteEmsHeadH2kExg(Request request,
			Integer emsHeadH2kExg, Integer version) {
		List result = emsEdiTrDao.checkDeleteEmsHeadH2kImgExg(emsHeadH2kExg,
				MaterielType.FINISHED_PRODUCT, version);
		return result;
	}

	public List checkToEmsH2kImg(Request request,
			EmsEdiMergerImgAfter emsImgAfter) {

		return emsEdiTrDao.checkToEmsH2kImg(emsImgAfter);
	}

	public List checkToEmsH2kExg(Request request,
			EmsEdiMergerExgAfter emsExgAfter) {

		return emsEdiTrDao.checkToEmsH2kExg(emsExgAfter);
	}

	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findnameValues(Request request, int type) {
		return emsEdiTrDao.findnameValues(type);
	}

	/**
	 * 当删除电子账册料件时检查
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 */
	public int checkToEmsH2kImgBom(Request request, EmsHeadH2kImg currRowImg) {
		return this.emsEdiTrLogic.checkToEmsH2kImgBom(currRowImg);
	}

	/**
	 * 电子帐册报关清单XML导入
	 * 
	 * @param request
	 * @return
	 */
	public Map loadBGDFromQPXml(Request request, boolean isImportBGD,
			boolean isChange) {
		return this.emsEdiTrLogic.loadBGDFromQPXml(isImportBGD, isChange);
	}

	@Override
	public String checkEmsExgBomModifyMarkIsUnit(Request request,
			EmsHeadH2k emsHeadH2k) {
		return emsEdiTrLogic.checkEmsExgBomModifyMarkIsUnit(emsHeadH2k);
	}

	@Override
	public List findRelativeInfoFromMaterial(Request request,
			EmsHeadH2k emsHeadH2k, Integer seqNum, Integer version,
			Boolean isMaterial) {
		return emsEdiTrDao.findRelativeInfoFromMaterial(emsHeadH2k, seqNum,
				version, isMaterial);
	}

}