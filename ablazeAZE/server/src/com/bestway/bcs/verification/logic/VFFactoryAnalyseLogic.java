package com.bestway.bcs.verification.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.verification.dao.VFFactoryAnalyseDao;
import com.bestway.bcs.verification.dao.VFVerificationDao;
import com.bestway.bcs.verification.entity.VFAnalyse;
import com.bestway.bcs.verification.entity.VFAttachmentManagement;
import com.bestway.bcs.verification.entity.VFBadExg;
import com.bestway.bcs.verification.entity.VFBadExgConvert;
import com.bestway.bcs.verification.entity.VFBadImg;
import com.bestway.bcs.verification.entity.VFBadStockAnalyse;
import com.bestway.bcs.verification.entity.VFBaseStockExg;
import com.bestway.bcs.verification.entity.VFBaseStockExgConvert;
import com.bestway.bcs.verification.entity.VFBaseStockImg;
import com.bestway.bcs.verification.entity.VFContractAnalyse;
import com.bestway.bcs.verification.entity.VFCustomsCountExg;
import com.bestway.bcs.verification.entity.VFCustomsCountExgConvert;
import com.bestway.bcs.verification.entity.VFCustomsCountImg;
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
import com.bestway.bcs.verification.entity.VFTransferDiffExgConvert;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.bcs.verification.entity.temp.BOMTemp;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;

public class VFFactoryAnalyseLogic {

	private VFFactoryAnalyseDao vfFactoryAnalyseDao;
	private VFVerificationDao vfVerificationDao;

	// -------------------------------查询方法 开始-----------------------------//

	/**
	 * 查询 vfSection 【核查批次】vfstockDenImg【外发库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendExg> findVFStockOutSendExgs(Request request,
			VFSection vfSection) {
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockOutSendExg.class.getName());
	}

	/**
	 * 查询 vfSection【核查批次】VFStockOutFactoryImg 【外发库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendExgConvert> findVFStockOutSendExgConvert(
			Request request, VFSection vfSection) {
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockOutSendExgConvert.class.getName());
	}

	/**
	 * 查询 vfSection【核查批次】VFStockOutFactoryImg 【厂外库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutFactoryImg> findVFStockOutFactoryImg(Request request,
			VFSection vfSection) {
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockOutFactoryImg.class.getName());
	}

	/**
	 * 查询 vfSection 【核查批次】VFStockBuyImg【内购料件库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public List<VFStockBuyImg> findVFStockBuyImgs(Request request,
			VFSection vfSection) {
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockBuyImg.class.getName());
	}

	/**
	 * 查询 vfSection 【核查批次】VFStockBuyImg【在途料件库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingImg> finVFStockTravelingImgs(Request request,
			VFSection vfSection) {
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockTravelingImg.class.getName());
	}

	// ******************************************

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockImg【料件库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockImg> findVFStockImgs(VFSection vfSection, Integer seqNum) {

		return vfVerificationDao.findByVFSection(vfSection, "VFStockImg",
				seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutSendImg【外发库存原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendImg> findVFStockOutSendImgs(VFSection vfSection,
			Integer seqNum) {

		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockOutSendImg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockExg【成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExg> findVFStockExgs(VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection, "VFStockExg",
				seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingExg【在途库存成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingExg> findVFStockTravelingExgs(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockTravelingExg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutSendSemiExg【外发库存半成品】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendSemiExg> findVFStockOutSendSemiExgs(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockOutSendSemiExg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockSemiExgHadStore【半成品库存（已入库）】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockSemiExgHadStore> findVFStockSemiExgHadStore(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockSemiExgHadStore", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockExgConvert【成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExgConvert> findVFStockExgConverts(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockExgConvert", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingExgConvert【在途库存成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingExgConvert> findVFStockTravelingExgConverts(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockTravelingExgConvert", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutSendSemiExgConvert【外发库存半成品存折算料件转换报关数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendSemiExgConvert> findVFStockOutSendSemiExgConverts(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockOutSendSemiExgConvert", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockSemiExgPtHadStoreConvert【半成品库存（已入库）折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockSemiExgHadStoreConvert> findVFStockSemiExgHadStoreConverts(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockSemiExgHadStoreConvert", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockProcessImg【半成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockProcessImg> findVFStockProcessImgs(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockProcessImg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockProcessImg【厂外库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutFactoryImg> findVFStockProcessImgsout(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockOutFactoryImg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockBuyImg【内购库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockBuyImg> findVFStockProcessImgsin(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection, "VFStockBuyImg",
				seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockTravelingImg【在途库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingImg> findVFStockProcessImgslu(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockTravelingImg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockOutSendImg【在途库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendImg> findVFStockProcessImgsoutsend(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockOutSendImg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFBadImg【残次品料件库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingImg> findVFStockProcessImging(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockOutSendImg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFBadImg【残次品料件库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFBadImg> findVFStockProcessImgbad(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFStockOutSendImg", seqNum);
	}

	/**
	 * 查询 核查批次 外发库存成品
	 * 
	 * @param vfSection
	 *            批次号
	 * @return
	 */
	public List<VFStockOutSendExg> findVFStockOutSendExgs(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockOutSendExg.class.getName(), seqNum);
	}

	/**
	 * 查询 核查批次 外发库存成品转换料件
	 * 
	 * @param vfSection
	 *            批次号
	 * @return
	 */
	public List<VFStockOutSendExgConvert> findVFStockExgsConvert(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockOutSendExgConvert.class.getName(), seqNum);
	}

	/**
	 * 查询 核查批次 厂外库存
	 * 
	 * @param vfSection
	 *            批次号
	 * @return
	 */
	public List<VFStockOutFactoryImg> findVFStockOutFactoryImgs(
			VFSection vfSection, Integer seqNum) {
		// vfFactoryAnalyseLogic
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockOutFactoryImg.class.getName(), seqNum);
	}

	/**
	 * 查询 核查批次 内购 库存
	 * 
	 * @param vfSection
	 *            批次号
	 * @return
	 */
	public List<VFStockBuyImg> findVFStockBuyImgs(VFSection vfSection,
			Integer seqNum) {
		// vfFactoryAnalyseLogic
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockBuyImg.class.getName(), seqNum);
	}

	/**
	 * 查询 核查批次 在途库存
	 * 
	 * @param vfSection
	 *            批次号
	 * @param seqNum
	 *            归并序号
	 * @return
	 */
	public List<VFStockTravelingImg> findVFStockTravelingImgs(
			VFSection vfSection, Integer seqNum) {
		// vfFactoryAnalyseLogic
		return vfVerificationDao.findByVFSection(vfSection,
				VFStockTravelingImg.class.getName(), seqNum);
	}

	/**
	 * 查找section【批次】下库存分析数据
	 * 
	 * @param section
	 * @return
	 */
	public List<VFStockAnalyse> findVFStockAnalyse(VFSection section,
			Integer seqNum) {

		List<VFStockAnalyse> list = vfVerificationDao.findByVFSection(section,
				VFStockAnalyse.class.getName(), seqNum, " order by mergerNo");

		for (int i = 0; i < list.size(); i++) {

			VFStockAnalyse vf = list.get(i);

			CommonUtils.formatDouble(vf, 5);

		}
		return list;
	}

	/**
	 * 查找section【批次】下外发库存汇总
	 * 
	 * @param request
	 * @param section
	 * @param seqNum
	 *            归并序号
	 * @return
	 */
	public List<VFStockOutSendAnalyse> findVFStockOutSendAnalyse(
			VFSection section, Integer seqNum) {
		return vfVerificationDao.findByVFSection(section,
				VFStockOutSendAnalyse.class.getName(), seqNum,
				" order by mergerNo");
	}

	// -------------------------------查询方法 结束-----------------------------//

	// -------------------------------保存方法 开始-----------------------------//
	/**
	 * 保存 vfStockImgs【料件库存】在 vfSection【核查批次】下
	 * 
	 * @param vfSection
	 * @param vfStockImgs
	 * @return
	 */
	public void saveVFStockImgs(VFSection vfSection,
			List<VFBaseStockImg> vFBaseStockImg) {
		saveVFBaseStockImg(vfSection, vFBaseStockImg);
	}

	/**
	 * 保存 vfStockExgs【成品库存】在 vfSection【核查批次】下
	 * 
	 * @param vfSection
	 * @param vfStockExgs
	 * @return
	 */
	public void saveVFStockExgs(VFSection vfSection,
			List<VFBaseStockExg> vFBaseStockExg) {
		saveVFBaseStockExg(vfSection, vFBaseStockExg);
	}

	/**
	 * 保存 VFStockSemiExgHadStore【半成品库存（已入库）】在 vfSection【核查批次】下
	 * 
	 * @param vfSection
	 * @param vfStockExgs
	 * @return
	 */
	public void saveVFStockSemiExgHadStore(VFSection vfSection,
			List<VFStockSemiExgHadStore> vFStockSemiExgPtHadStore) {
		saveVFBaseStockExg(vfSection, vFStockSemiExgPtHadStore);
	}

	/**
	 * 保存 vfStockExgConverts【成品折算数据】在 vfSection【核查批次】下
	 * 
	 * @param vfSection
	 * @param vfStockExgConverts
	 * @return
	 */
	public void saveVFStockExgConverts(VFSection vfSection,
			List<VFStockExgConvert> vfStockExgConverts) {
		saveVFBaseStockImg(vfSection, vfStockExgConverts);
	}

	/**
	 * 保存 vfStockProcessImgs【半成品库存】在 vfSection【核查批次】下
	 * 
	 * @param vfSection
	 * @param vfStockProcessImgs
	 * @return
	 */
	public void saveVFStockProcessImgs(VFSection vfSection,
			List<VFStockProcessImg> vfStockProcessImgs) {
		saveVFBaseStockImg(vfSection, vfStockProcessImgs);
	}

	/**
	 * 保存 vfSection 【核查批次】vfstockDenImg【外发库存成品】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void saveVFStockOutSendExgs(VFSection vfSection,
			List<VFStockOutSendExg> list) {
		saveVFBaseStockExg(vfSection, list);
	}

	/**
	 * 保存 vfSection 【核查批次】vfstockDenImg【外发库存成品折算后料件】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void saveVFStockOutSendExgsConvert(VFSection vfSection,
			List<VFStockOutSendExgConvert> list) {
		vfVerificationDao.batchSaveVFEnity(list);
	}

	/**
	 * 保存 vfSection【核查批次】VFStockOutFactoryImg 【厂外库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void saveVFStockOutFactoryImgs(VFSection vfSection,
			List<VFStockOutFactoryImg> list) {
		vfVerificationDao.batchSaveVFEnity(list);
	}

	/**
	 * 保存 vfSection 【核查批次】vfstockDenImg【外购库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void saveVFStockBuyImgs(VFSection vfSection, List<VFStockBuyImg> list) {
		vfVerificationDao.batchSaveVFEnity(list);
	}

	/**
	 * 保存 vfSection 【核查批次】VFStockBuyImg【在途料件库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void saveVFStockTravelingImgs(VFSection vfSection,
			List<VFStockTravelingImg> list) {
		vfVerificationDao.batchSaveVFEnity(list);
	}

	/**
	 * 汇总section【批次】下所有工厂数据 处理步骤如下：
	 * 
	 * @param request
	 * @param section
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VFStockAnalyse> stockAnalyse(VFSection section) {
		deleteVFStockAnalyse(section);
		/****************************** 库存分析 *****************************************/
		List<BcsInnerMerge> mergeLs = vfVerificationDao
				.findBcsInnerMerge(MaterielType.MATERIEL);
		Map<Integer, BcsInnerMerge> mgeMp = new HashMap<Integer, BcsInnerMerge>();
		for (BcsInnerMerge im : mergeLs) {
			if (im.getBcsTenInnerMerge() != null)
				mgeMp.put(im.getBcsTenInnerMerge().getSeqNum(), im);
		}
		Map<Integer, VFStockAnalyse> rsMap = new LinkedHashMap<Integer, VFStockAnalyse>();
		List buyIsCountList = vfVerificationDao.findBuyIsCount(section.getId());
		Boolean flag = null == buyIsCountList.get(0) ? false
				: (Boolean) buyIsCountList.get(0);
		// 料件库存
		List<Object[]> tmpList = vfFactoryAnalyseDao
				.findHadConvertGroupByMergeNo(VFStockImg.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 1);
		// 内购料件库存
		if (flag) {
			tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
					VFStockBuyImg.class, section);
			putToStockAnalyse(tmpList, mgeMp, rsMap, section, 2);
		}
		// 成品库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFStockExgConvert.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 3);
		// 【半成品】库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFStockProcessImg.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 4);
		// 厂外料件库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFStockOutFactoryImg.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 5);
		// 外发加工库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFStockOutSendAnalyse.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 6);
		// 在途料件库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFStockTravelingImg.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 7);
		// 在途成品库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFStockTravelingExgConvert.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 8);

		// 半成品库存（已入库）
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFStockSemiExgHadStoreConvert.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 9);
		// 残次品库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFBadStockAnalyse.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 10);
		// 在制品库存
		tmpList = vfFactoryAnalyseDao.findHadConvertGroupByMergeNo(
				VFFinishingStockAnalyse.class, section);
		putToStockAnalyse(tmpList, mgeMp, rsMap, section, 11);

		if (rsMap.size() > 0) {
			VFStockAnalyse[] rs = new VFStockAnalyse[rsMap.size()];
			rs = rsMap.values().toArray(rs);
			List<VFStockAnalyse> ls = Arrays.asList(rs);
			/*** 排序 按照归并序号排序 **/
			Collections.sort(ls, new Comparator<VFStockAnalyse>() {
				public int compare(VFStockAnalyse o1, VFStockAnalyse o2) {
					return CommonUtils.getIntegerExceptNull(o1.getMergerNo())
							- CommonUtils.getIntegerExceptNull(o2.getMergerNo());
				}
			});
			vfVerificationDao.batchSaveOrUpdateDirect(ls);
			return ls;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 累加工厂库存
	 * 
	 * @param img
	 *            List<[mergerNo,sum(a.hsAmount)]>
	 * @param mgeMp
	 *            BCS内部归并对应表
	 * @param rsMap
	 *            库存表
	 * @param type
	 *            1~7 {1:VFStockImg,2:VFStockBuyImg,3:VFStockExgConvert,4:
	 *            VFStockProcessImg,
	 *            5:VFStockOutFactoryImg,6:VFStockOutSendExgConvert
	 *            ,7:VFStockTravelingImg}
	 */
	private void putToStockAnalyse(List<Object[]> imgLs,
			Map<Integer, BcsInnerMerge> mgeMp,
			Map<Integer, VFStockAnalyse> rsMap, VFSection vfsection, int type) {
		if (CollectionUtils.isNotEmpty(imgLs)) {
			VFStockAnalyse stock = null;
			for (Object[] objs : imgLs) {
				Integer mgNo = ((Number) objs[0]).intValue();
				Double hsAmount = null;
				if (objs[1] != null)
					hsAmount = ((Number) objs[1]).doubleValue();
				stock = rsMap.get(mgNo);
				if (stock == null) {
					stock = new VFStockAnalyse();
					stock.init();
					BcsInnerMerge bim = mgeMp.get(mgNo);
					BcsTenInnerMerge info = bim.getBcsTenInnerMerge();
					stock.setSection(vfsection);
					stock.setCompany(CommonUtils.getCompany());
					stock.setMergerNo(mgNo);
					stock.setHsName(info.getName());
					stock.setHsSpec(info.getSpec());
					stock.setHsUnit(info.getComUnit() == null ? null : info
							.getComUnit().getName());
					rsMap.put(mgNo, stock);
				}
				// 处理库存数量
				stock.setStockTotalAmount(CaleUtil.add(checkNull(hsAmount),
						checkNull(stock.getStockTotalAmount()))); // 库存总量
				switch (type) {
				case 1:
					stock.setStockAmountImg(CaleUtil.add(checkNull(hsAmount),
							checkNull(stock.getStockAmountImg())));
					break;
				case 2:
					stock.setStockAmountBuy(CaleUtil.add(checkNull(hsAmount),
							checkNull(stock.getStockAmountBuy())));
					break;
				case 3:
					stock.setStockAmountExg(CaleUtil.add(checkNull(hsAmount),
							checkNull(stock.getStockAmountExg())));
					break;
				case 4:
					stock.setStockAmountProcessImg(CaleUtil.add(
							checkNull(hsAmount),
							checkNull(stock.getStockAmountProcessImg())));
					break;
				case 5:
					stock.setStockAmountOutFactory(CaleUtil.add(
							checkNull(hsAmount),
							checkNull(stock.getStockAmountOutFactory())));
					break;
				case 6:
					stock.setStockAmountOutSend(CaleUtil.add(
							checkNull(hsAmount),
							checkNull(stock.getStockAmountOutSend())));
					break;
				case 7:
					stock.setStockAmountTraveling(CaleUtil.add(
							checkNull(hsAmount),
							checkNull(stock.getStockAmountTraveling())));
					break;
				case 8:
					stock.setStockAmountTravelingExg(CaleUtil.add(
							checkNull(hsAmount),
							checkNull(stock.getStockAmountTraveling())));
					break;
				case 9:
					stock.setStockAmountSemiExgHadStore(CaleUtil.add(
							checkNull(hsAmount),
							checkNull(stock.getStockAmountSemiExgHadStore())));
					break;
				case 10:
					stock.setStockAmountBad(CaleUtil.add(checkNull(hsAmount),
							checkNull(stock.getStockAmountBad())));
					break;
				case 11:
					stock.setStockAmountFinishing(CaleUtil.add(
							checkNull(hsAmount),
							checkNull(stock.getStockAmountFinishing())));
					break;
				}
			}
		}
	}

	/**
	 * 汇总section【批次】外发库数据
	 * 
	 * @param request
	 * @param section
	 * @return
	 */
	public List<VFStockOutSendAnalyse> stockVFStockOutSendAnalyse(
			VFSection section) {
		// 库存汇总
		List<VFStockOutSendAnalyse> outSendAnalyse = new ArrayList<VFStockOutSendAnalyse>();
		// 库存汇总
		Map<String, VFStockOutSendAnalyse> outSendAnalyseMap = new HashMap<String, VFStockOutSendAnalyse>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在计算外发库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		// 先删除该批次下的汇总表
		vfVerificationDao.deleteByVFSection(section,
				VFStockOutSendAnalyse.class.getName());
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		List imgLs = vfVerificationDao.findConvertVFStockOutSendImgs(section,
				VFStockOutSendImg.class);
		mergerOutSendStock(outSendAnalyseMap, imgLs, "VFStockOutSendImg");

		List outExgLs = vfVerificationDao.findConvertVFStockOutSendImgs(
				section, VFStockOutSendSemiExgConvert.class);
		mergerOutSendStock(outSendAnalyseMap, outExgLs,
				"VFStockOutSendSemiExgConvert");

		List exgLs = vfVerificationDao.findConvertVFStockOutSendImgs(section,
				VFStockOutSendExgConvert.class);
		mergerOutSendStock(outSendAnalyseMap, exgLs, "VFStockOutSendExgConvert");
		if (outSendAnalyseMap != null) {
			Iterator finishingIterator = outSendAnalyseMap.values().iterator();
			while (finishingIterator.hasNext()) {
				VFStockOutSendAnalyse stockOutSendAnalyse = (VFStockOutSendAnalyse) finishingIterator
						.next();
				stockOutSendAnalyse.setSection(section);
				outSendAnalyse.add(stockOutSendAnalyse);
			}
		}
		if (outSendAnalyse.size() > 0) {
			vfVerificationDao.batchSaveOrUpdate(outSendAnalyse);
		}

		return outSendAnalyse;
	}

	/**
	 * 统计外发库存汇总 总库存 = 外发原材料+ 外发成品折料+外发半成品折料
	 * 
	 * @param outSendStockMap
	 *            总库存
	 * @param list
	 *            外发原材料+ 外发成品折料+外发半成品折料
	 * @param clazz
	 */
	private void mergerOutSendStock(Map outSendStockMap, List list, String clazz) {
		if (list != null) {
			String key;
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				if (obj[0] != null) {
					key = obj[0].toString();// 备案序号
					VFStockOutSendAnalyse outSendAnalyse = (VFStockOutSendAnalyse) outSendStockMap
							.get(key);
					if (outSendAnalyse == null) {
						outSendAnalyse = new VFStockOutSendAnalyse();
						outSendAnalyse.init();
						outSendAnalyse.setMergerNo(Integer.valueOf(obj[0]
								.toString()));
						outSendAnalyse.setHsName(obj[1] == null ? "" : obj[1]
								.toString());
						outSendAnalyse.setHsSpec(obj[2] == null ? "" : obj[2]
								.toString());
						outSendAnalyse.setHsUnit(obj[3] == null ? "" : obj[3]
								.toString());
						if (clazz.equals("VFStockOutSendImg")) {// 外发原材料
							outSendAnalyse.setImgHsAmount(obj[4] == null ? 0.0
									: Double.valueOf(obj[4].toString()));
						} else if (clazz.equals("VFStockOutSendSemiExgConvert")) {// 外发半成品折料
							outSendAnalyse
									.setSemiExgHsAmount(obj[4] == null ? 0.0
											: Double.valueOf(obj[4].toString()));
						} else if (clazz.equals("VFStockOutSendExgConvert")) {// 外发成品折料
							outSendAnalyse.setExgHsAmount(obj[4] == null ? 0.0
									: Double.valueOf(obj[4].toString()));
						}
						outSendAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(outSendAnalyse
										.getImgHsAmount()
										+ outSendAnalyse.getSemiExgHsAmount()
										+ outSendAnalyse.getExgHsAmount()));
						outSendStockMap.put(key, outSendAnalyse);
					} else {// 累加
						if (clazz.equals("VFStockOutSendImg")) {
							outSendAnalyse.setImgHsAmount(outSendAnalyse
									.getImgHsAmount()
									+ (obj[4] == null ? 0.0 : Double
											.valueOf(obj[4].toString())));
						} else if (clazz.equals("VFStockOutSendSemiExgConvert")) {
							outSendAnalyse.setSemiExgHsAmount(outSendAnalyse
									.getSemiExgHsAmount()
									+ (obj[4] == null ? 0.0 : Double
											.valueOf(obj[4].toString())));
						} else if (clazz.equals("VFStockOutSendExgConvert")) {
							outSendAnalyse.setExgHsAmount(outSendAnalyse
									.getExgHsAmount()
									+ (obj[4] == null ? 0.0 : Double
											.valueOf(obj[4].toString())));
						}
						outSendAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(outSendAnalyse
										.getImgHsAmount()
										+ outSendAnalyse.getSemiExgHsAmount()
										+ outSendAnalyse.getExgHsAmount()));
					}
				}
			}
		}
	}

	/**
	 * 将Null转换成0
	 * 
	 * @param d
	 * @return
	 */
	private double checkNull(Double d) {
		return d == null ? 0.0 : d;
	}

	// -------------------------------保存方法 结束-----------------------------//

	// -------------------------------删除方法 开始-----------------------------//

	/**
	 * 删除section【批次】下汇总数据
	 * 
	 * @param section
	 */
	public void deleteVFStockAnalyse(VFSection section) {
		vfVerificationDao.deleteByVFSection(section,
				VFStockAnalyse.class.getName());
	}

	/**
	 * 删除section【批次】外发库存汇总
	 * 
	 * @param section
	 */
	public void deleteVFStockOutSendAnalyse(VFSection section) {
		vfVerificationDao.deleteByVFSection(section,
				VFStockOutSendAnalyse.class.getName());
	}

	/**
	 * 删除 vfSection 【核查批次】vfstockDenImg【外发库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockOutSendExgs(Request request, VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection,
				VFStockOutSendExg.class.getName());
	}

	/**
	 * 删除 vfSection 【核查批次】vfstockDenImg【外发库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockOutSendExgsConvert(Request request,
			VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection,
				VFStockOutSendExgConvert.class.getName());
	}

	/**
	 * 删除 vfSection【核查批次】VFStockOutFactoryImg 【厂外库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockOutFactoryImgsConvert(Request request,
			VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockOutFactoryImg");
	}

	/**
	 * 删除 vfSection 【核查批次】VFStockBuyImg【内购料件库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockBuyImgs(Request request, VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockBuyImg");
	}

	/**
	 * 删除 vfSection 【核查批次】VFStockBuyImg【在途料件库存】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockTravelingImgs(Request request, VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockTravelingImg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockImg【料件库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockImgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockImg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockOutSendImg【外发库存原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockOutSendImgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockOutSendImg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockExg【成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockExgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockExg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockTravelingExg【在途库存成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockTravelingExgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockTravelingExg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockOutSendSemiExg【外发库存半成品】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockOutSendSemiExgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockOutSendSemiExg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockSemiExgHadStore【半成品库存（已入库）】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockSemiExgHadStores(VFSection vfSection) {
		vfVerificationDao
				.deleteByVFSection(vfSection, "VFStockSemiExgHadStore");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockExgConvert【成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockExgConverts(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockExgConvert");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockTravelingExgConvert【在途库存成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockTravelingExgConverts(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection,
				"VFStockTravelingExgConvert");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockOutSendSemiExgConvert【外发库存半成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockOutSendSemiExgConverts(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection,
				"VFStockOutSendSemiExgConvert");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockExgConvert【半成品库存（已入库）】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockSemiExgHadStoreConverts(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection,
				"VFStockSemiExgHadStoreConvert");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFStockProcessImg【半成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFStockProcessImgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFStockProcessImg");
	}

	// -------------------------------删除方法 结束-----------------------------//

	// ---------------------------工厂库存折料和转换方法 开始-------------------------//

	/**
	 * 库存料件 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockImg> convertVFStockImgs(VFSection vfSection) {

		return convertVFBaseStockImgs(vfSection, "VFStockImg");
	}

	/**
	 * 外发库存原材料 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendImg> convertVFStockOutSendImgs(VFSection vfSection) {

		return convertVFBaseStockImgs(vfSection, "VFStockOutSendImg");
	}

	/**
	 * 在产品库存 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockProcessImg> convertVFStockProcessImgs(VFSection vfSection) {

		return convertVFBaseStockImgs(vfSection, "VFStockProcessImg");
	}

	/**
	 * 产品库存 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExgConvert> resolveVFStockExgs(VFSection vfSection) {

		return resolveVFBaseStockExgs(vfSection, "VFStockExg",
				new VFStockExgConvert());
	}

	/**
	 * 在途库存成品库存 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingExgConvert> resolveVFStockTravelingExgs(
			VFSection vfSection) {

		return resolveVFBaseStockExgs(vfSection, "VFStockTravelingExg",
				new VFStockTravelingExgConvert());
	}

	/**
	 * 外发库存半成品 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendSemiExgConvert> resolveVFStockOutSendSemiExgs(
			VFSection vfSection) {

		return resolveVFBaseStockExgs(vfSection, "VFStockOutSendSemiExg",
				new VFStockOutSendSemiExgConvert());
	}

	/**
	 * 半成品库存（已入库） 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockSemiExgHadStoreConvert> resolveVFStockSemiExgHadStoreConverts(
			VFSection vfSection) {

		return resolveVFBaseStockExgs(vfSection, "VFStockSemiExgHadStore",
				new VFStockSemiExgHadStoreConvert());
	}

	/**
	 * 折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockExgConvert> convertVFStockExgConverts(VFSection vfSection) {

		return convertVFBaseStockImgs(vfSection, "VFStockExgConvert");
	}

	/**
	 * 折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockExgConvertsEms(VFSection vfSection) {

		return convertVFBaseStockImgsEms(vfSection, "VFStockExgConvert");
	}

	/**
	 * 【料件库存】折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertImgConverts(VFSection vfSection, String table) {

		return convertVFBaseStockImgsEms(vfSection, table);
	}

	/**
	 * 折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockTravelingExgConvertEms(VFSection vfSection) {

		return convertVFBaseStockImgsEms(vfSection,
				"VFStockTravelingExgConvert");
	}

	/**
	 * 【外发库存半成品】折算报关数量,折算时，把对应关系存在的品名折算，没有做对应关系的提示出来
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockOutSendSemiExgConvertsEms(VFSection vfSection) {

		return convertVFBaseStockImgsEms(vfSection,
				"VFStockOutSendSemiExgConvert");
	}

	/**
	 * 【半成品库存（已入库）】折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertVFStockSemiExgHadStoreConvertEms(VFSection vfSection) {

		return convertVFBaseStockImgsEms(vfSection,
				"VFStockSemiExgHadStoreConvert");
	}

	/**
	 * 外发成品 转 料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutSendExgConvert> convertFactoryVFStockOutSendExgsConvert(
			VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return resolveVFBaseStockExgs(vfSection, "VFStockOutSendExg",
				new VFStockOutSendExgConvert());
	}

	/**
	 * 外发成品转料件后转海关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertHsVFStockOutSendExgsConvert(VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return convertVFBaseStockImgsEms(vfSection, "VFStockOutSendExgConvert");
	}

	/**
	 * 厂外库存料件折算
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockOutFactoryImg> convertVFStockOutFactoryImgs(
			VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return convertVFBaseStockImgs(vfSection, "VFStockOutFactoryImg");
	}

	/**
	 * 外购库存料件折算
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockBuyImg> convertVFStockBuyImgs(VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return convertVFBaseStockImgs(vfSection, "VFStockBuyImg");// VFStockBuyImg
	}

	/**
	 * 在途料件折算
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFStockTravelingImg> convertVFStockTravelingImgs(
			VFSection vfSection) {
		// vfFactoryAnalyseLogic
		return convertVFBaseStockImgs(vfSection, "VFStockTravelingImg");
	}

	/**
	 * 把当前批次 vfSection 下 的所有 clazz 数据进行转换报关数据
	 * 
	 * @param vfSection
	 * @param clazz
	 *            实体名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends VFBaseStockImg> List<T> convertVFBaseStockImgs(
			VFSection vfSection, String clazz) {
		if (clazz == null) {
			throw new RuntimeException("clazz = null");
		}

		if (vfSection == null) {
			throw new RuntimeException("vfSection = null");
		}

		long b = System.currentTimeMillis();

		List<T> list = vfVerificationDao.findByVFSection(vfSection, clazz);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);

		b = e;
		vfVerificationDao.deleteByVFSection(vfSection, clazz);
		e = System.currentTimeMillis();
		System.out.println("保存数据时间1：" + (e - b) / 1000.0);

		b = e;
		List<BcsInnerMerge> innerMerges = vfVerificationDao
				.findBcsInnerMerge(MaterielType.MATERIEL);
		Map<String, BcsInnerMerge> innerMergeMap = new HashMap<String, BcsInnerMerge>();
		innerMergeMap = CommonUtils.listToMap(innerMerges,
				new GetKeyValueImpl<BcsInnerMerge>() {
					public String getKey(BcsInnerMerge e) {
						return e.getMateriel().getPtNo();
					}
				});
		e = System.currentTimeMillis();
		System.out.println("查询对应关系时间：" + (e - b) / 1000.0);

		b = e;
		T img = null;
		BcsInnerMerge merge = null;
		BcsTenInnerMerge info = null;
		StringBuilder err = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			img = list.get(i);
			vfVerificationDao.evict(img);
			// img.setId(null);
			merge = innerMergeMap.get(img.getPtNo());
			if (merge == null) {
				err.append(err.length() == 0 ? img.getPtNo() : ("," + img
						.getPtNo()));
				continue;
			}
			info = merge.getBcsTenInnerMerge();
			if (info == null) {
				err.append(err.length() == 0 ? img.getPtNo() : ("," + img
						.getPtNo()));
				continue;
			}

			// 根据对应关系把工厂料件转化为报关料件
			convertVFBaseStockImg(img, merge);
		}
		e = System.currentTimeMillis();
		System.out.println("转换时间：" + (e - b) / 1000.0);

		b = e;
		vfVerificationDao.batchSaveVFEnity(list);
		e = System.currentTimeMillis();
		System.out.println("保存数据时间2：" + (e - b) / 1000.0);

		if (err.length() > 0) {
			throw new RuntimeException(err.toString());
		}

		return list;
	}

	/**
	 * 根据对应关系把工厂料件转化为报关料件
	 * 
	 * @param img
	 * @param merge
	 */
	private void convertVFBaseStockImg(VFBaseStockImg img, BcsInnerMerge merge) {
		// if (merge == null) {
		// throw new RuntimeException("料号 【" + img.getPtNo() + "】 没有做对应关系");
		// }
		BcsTenInnerMerge info = merge.getBcsTenInnerMerge();
		// if (info == null) {
		// throw new RuntimeException("料号 【" + img.getPtNo() + "】 的对应关系没有对应商品");
		// }

		img.setMergerNo(info.getSeqNum());
		img.setHsName(info.getName());
		img.setHsSpec(info.getSpec());
		img.setHsUnit(info.getComUnit().getName());
		img.setUnitConvert(merge.getUnitConvert());
		img.setHsAmount(CommonUtils.getDoubleExceptNull(img.getStoreAmount())
				* CommonUtils.getDoubleExceptNull(merge.getUnitConvert()));
	}

	/**
	 * 【合同折算成品单耗】把当前批次 vfSection 下 的所有 clazz 数据进行转换报关数据
	 * 
	 * @param vfSection
	 * @param clazz
	 *            实体名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String convertVFBaseStockImgsEms(VFSection vfSection, String clazz) {
		if (clazz == null) {
			throw new RuntimeException("clazz = null");
		}

		if (vfSection == null) {
			throw new RuntimeException("vfSection = null");
		}

		long b = System.currentTimeMillis();

		List list = vfVerificationDao.findByVFSection(vfSection, clazz);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);

		b = e;
		vfVerificationDao.deleteByVFSection(vfSection, clazz);
		e = System.currentTimeMillis();
		System.out.println("保存数据时间1：" + (e - b) / 1000.0);

		b = e;
		List<BcsInnerMerge> innerMerges = vfVerificationDao
				.findBcsInnerMerge(MaterielType.MATERIEL);
		Map<String, BcsInnerMerge> innerMergeMap = new HashMap<String, BcsInnerMerge>();
		innerMergeMap = CommonUtils.listToMap(innerMerges,
				new GetKeyValueImpl<BcsInnerMerge>() {
					public String getKey(BcsInnerMerge e) {
						return e.getMateriel().getPtNo();
					}
				});
		e = System.currentTimeMillis();
		System.out.println("查询对应关系时间：" + (e - b) / 1000.0);

		b = e;
		VFBaseStockImg img = null;
		BcsInnerMerge merge = null;
		BcsTenInnerMerge info = null;
		StringBuilder err = new StringBuilder();
		StringBuilder errinfo = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			img = (VFBaseStockImg) list.get(i);
			vfVerificationDao.evict(img);
			// img.setId(null);
			merge = innerMergeMap.get(img.getPtNo());
			if (merge == null) {
				err.append(err.length() == 0 ? img.getPtNo() : ("," + img
						.getPtNo()));
				continue;
			}
			info = merge.getBcsTenInnerMerge();
			if (info == null) {
				err.append(err.length() == 0 ? img.getPtNo() : ("," + img
						.getPtNo()));
				continue;
			}

			// 根据对应关系把工厂料件转化为报关料件
			convertVFBaseStockImg(img, merge);
		}
		e = System.currentTimeMillis();
		System.out.println("转换时间：" + (e - b) / 1000.0);

		if (err.length() > 0) {
			// throw new RuntimeException(err.toString());
			errinfo.append(err.toString());
		}

		b = e;
		vfVerificationDao.batchSaveVFEnity(list);
		e = System.currentTimeMillis();
		System.out.println("保存数据时间2：" + (e - b) / 1000.0);

		return errinfo.toString();
	}

	/**
	 * 成品转料件
	 * 
	 * @param vfSection
	 * @param exgClass
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends VFBaseStockExgConvert> List<T> resolveVFBaseStockExgs(
			VFSection vfSection, String exgClass, VFBaseStockExgConvert model) {
		if (model == null) {
			throw new RuntimeException("必须确定料件类型！");
		}

		// 删除之前的折算料件数据
		vfVerificationDao.deleteByVFSection(vfSection, model.getClass()
				.getName());

		long b = System.currentTimeMillis();
		List<VFBaseStockExg> list = vfVerificationDao.findByVFSection(
				vfSection, exgClass);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);

		b = e;
		List<BOMTemp> bomList = vfVerificationDao
				.findMaterialBomDetailByPtNoSet(vfSection, exgClass);
		e = System.currentTimeMillis();
		System.out.println("查询bom时间：" + (e - b) / 1000.0);

		b = e;
		Map<String, List<BOMTemp>> bomMap = new HashMap<String, List<BOMTemp>>();
		String key = null;
		List<BOMTemp> mbdList = null;
		BOMTemp bom = null;
		Integer version = null;
		String ptNo = null;
		for (int i = 0; i < bomList.size(); i++) {
			bom = bomList.get(i);
			version = bom.getVersion();
			ptNo = bom.getExgPtNo();
			key = ptNo + "," + version;

			mbdList = bomMap.get(key);
			if (mbdList == null) {
				mbdList = new ArrayList<BOMTemp>();

				bomMap.put(key, mbdList);
			}

			mbdList.add(bom);
		}
		e = System.currentTimeMillis();
		System.out.println("准备bom时间：" + (e - b) / 1000.0);

		b = e;
		VFBaseStockExg exg = null;
		List<T> stockImgs = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			exg = list.get(i);
			exg.setSection(vfSection);
			mbdList = bomMap.get(exg.getPtNo() + "," + exg.getVersion());
			stockImgs.addAll(resolveVFBaseStockExg(vfSection, exg, mbdList,
					model));
		}
		e = System.currentTimeMillis();
		System.out.println("折料时间：" + (e - b) / 1000.0);

		b = e;
		vfVerificationDao.batchSaveVFEnity(stockImgs);
		e = System.currentTimeMillis();
		System.out.println("保存时间：" + (e - b) / 1000.0);

		return stockImgs;
	}

	@SuppressWarnings("rawtypes")
	private List resolveVFBaseStockExg(VFSection vfSection, VFBaseStockExg exg,
			List<BOMTemp> mbdList, VFBaseStockExgConvert model) {
		// if(mbdList == null || mbdList.isEmpty()) {
		// throw new RuntimeException("料号 【" + exg.getPtNo() +
		// "】 没有做BOM，BOM版本被删除。");
		// }
		List<VFBaseStockImg> stockImgs = new ArrayList<VFBaseStockImg>();
		BOMTemp bom = null;
		VFBaseStockExgConvert convert = null;
		if (!(mbdList == null || mbdList.isEmpty())) {
			for (int i = 0; i < mbdList.size(); i++) {
				bom = mbdList.get(i);
				convert = (VFBaseStockExgConvert) model.clone();
				convert.setSection(vfSection);
				convert.setStockExg(exg);
				convert.setUnitWaste(bom.getUnitWaste());
				convert.setUnitUsed(bom.getUnitUsed());
				convert.setWaste(bom.getWaste());
				convert.setPtNo(bom.getPtNo());
				convert.setPtName(bom.getPtName());
				convert.setPtSpec(bom.getPtSpec());
				convert.setPtUnit(bom.getPtUnit());
				convert.setStoreAmount(CommonUtils.getDoubleExceptNull(exg
						.getStoreAmount())
						* CommonUtils.getDoubleExceptNull(bom.getUnitUsed()));

				stockImgs.add(convert);
			}
		}
		return stockImgs;
	}

	// ---------------------------工厂库存折算方法 结束-------------------------//

	<T extends VFBaseStockExg> void saveVFBaseStockExg(VFSection vfSection,
			List<T> list) {

		if (vfSection == null) {
			throw new RuntimeException("必须设置批次号！");
		}

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setSection(vfSection);
		}
		vfVerificationDao.batchSaveVFEnity(list);
	}

	<T extends VFBaseStockImg> void saveVFBaseStockImg(VFSection vfSection,
			List<T> list) {

		if (vfSection == null) {
			throw new RuntimeException("必须设置批次号！");
		}

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setSection(vfSection);
		}
		vfVerificationDao.batchSaveVFEnity(list);
	}

	/**
	 * @param vfVerificationDao
	 *            the vfVerificationDao to set
	 */
	public void setVfVerificationDao(VFVerificationDao vfVerificationDao) {
		this.vfVerificationDao = vfVerificationDao;
	}

	/**
	 * @param vfFactoryAnalyseDao
	 *            the vfFactoryAnalyseDao to set
	 */
	public void setVfFactoryAnalyseDao(VFFactoryAnalyseDao vfFactoryAnalyseDao) {
		this.vfFactoryAnalyseDao = vfFactoryAnalyseDao;
	}

	/**
	 * 删除section【批次】下在制品汇总数据
	 * 
	 * @param section
	 */
	public void deleteVFFinishingStockAnalyse(VFSection section) {
		vfVerificationDao.deleteByVFSection(section,
				VFFinishingStockAnalyse.class.getName());
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFFinishingExg【在制品成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingExg> findVFFinishingExgs(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection, "VFFinishingExg",
				seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFFinishingImg【在制品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingImg> findVFFinishingImgs(VFSection vfSection,
			Integer seqNum) {

		return vfVerificationDao.findByVFSection(vfSection, "VFFinishingImg",
				seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFBadImg【残次品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFBadImg> findVFBadImgs(VFSection vfSection, Integer seqNum) {

		return vfVerificationDao.findByVFSection(vfSection, "VFBadImg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFFinishingExgConvert【在制品成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingExgConvert> findVFFinishingExgConverts(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFFinishingExgConvert", seqNum);
	}

	/**
	 * 查找section【批次】下在制品库存分析数据
	 * 
	 * @param section
	 * @return
	 */
	public List<VFFinishingStockAnalyse> findVFFinishingStockAnalyse(
			VFSection section, Integer seqNum) {
		return vfVerificationDao.findByVFSection(section,
				VFFinishingStockAnalyse.class.getName(), seqNum,
				" order by mergerNo");
	}

	/**
	 * 保存 vfFinishingImg【在制品原材料】在 vfSection【核查批次】下
	 * 
	 * @param vfSection
	 * @param vfStockImgs
	 * @return
	 */
	public void saveVFFinishingImgs(VFSection vfSection,
			List<VFFinishingImg> vfStockImgs) {
		saveVFBaseStockImg(vfSection, vfStockImgs);
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFFinishingImg【在制品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFFinishingImgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFFinishingImg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFBadImg【残次品原材料】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFBadImgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFBadImg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFFinishingExg【在制品成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFFinishingExgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFFinishingExg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFFinishingExgConvert【在制品成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFFinishingExgConverts(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFFinishingExgConvert");
	}

	/**
	 * 在制品原材料 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingImg> convertVFFinishingImgs(VFSection vfSection) {

		return convertVFBaseStockImgs(vfSection, "VFFinishingImg");
	}

	/**
	 * 残次品原材料 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFBadImg> convertVFBadImgs(VFSection vfSection) {

		return convertVFBaseStockImgs(vfSection, "VFBadImg");
	}

	/**
	 * 产品库存 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFFinishingExgConvert> resolveVFFinishingExgs(
			VFSection vfSection) {

		return resolveVFBaseStockExgs(vfSection, "VFFinishingExg",
				new VFFinishingExgConvert());
	}

	/**
	 * 在制品折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertVFFinishingExgConvertsEms(VFSection vfSection) {

		return convertVFBaseStockImgsEms(vfSection, "VFFinishingExgConvert");
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * 
	 * @param request
	 * @param vfSection
	 * @return
	 */
	public List<VFAttachmentManagement> findVFAttachmentManagement(
			VFSection section) {
		List<VFAttachmentManagement> parentList = vfVerificationDao
				.findVFAttachmentManagement(section, true);
		List<VFAttachmentManagement> chileList = vfVerificationDao
				.findVFAttachmentManagement(section, false);
		Map<String, VFAttachmentManagement> map = new HashMap<String, VFAttachmentManagement>();
		for (int i = 0; i < parentList.size(); i++) {
			VFAttachmentManagement parentAttachment = parentList.get(i);
			parentAttachment.setIsModify(false);
			map.put(parentAttachment.getId(), parentAttachment);
		}

		for (int i = 0; i < chileList.size(); i++) {
			VFAttachmentManagement childAttachment = chileList.get(i);
			if (map.get(childAttachment.getParentId()) != null) {
				if (map.get(childAttachment.getParentId()).getChildren() == null) {
					List children = new ArrayList();
					map.get(childAttachment.getParentId())
							.setChildren(children);
				}
				childAttachment.setIsModify(false);
				map.get(childAttachment.getParentId()).getChildren()
						.add(childAttachment);
			}
		}
		return parentList;
	}

	/**
	 * 保存vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * 
	 * @param request
	 * @param attachment
	 */
	public void saveVFAttachmentManagements(List<VFAttachmentManagement> list) {
		vfVerificationDao.batchSaveOrUpdate(list);
	}

	/**
	 * 保存vfSection【核查批次】下的所有 VFAttachmentManagement【附件管理】
	 * 
	 * @param request
	 * @param attachment
	 */
	public void saveVFAttachmentManagements(VFAttachmentManagement attachment) {
		vfVerificationDao.saveOrUpdate(attachment);
	}

	/**
	 * 上传核查批次附件
	 * 
	 * @param request
	 *            批次
	 * @param file
	 *            附件
	 * @param fileType
	 *            附件类型
	 * @return
	 */
	public void upLoadAttachment(byte[] fileContent, String fileName) {
		if (fileContent != null) {
			try {
				FileOutputStream out = null;
				File file = new File(getAttachmentFolder() + File.separator
						+ fileName);
				out = new FileOutputStream(file);
				System.out.println("----------------file--------------"
						+ file.getAbsolutePath());
				out.write(fileContent);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getAttachmentFolder() {
		String fi = System.getProperty("user.dir") + File.separator + "VFS"
				+ File.separator + "AttachmentFile";
		File file = new File(fi);
		if (!file.exists()) {
			file.mkdirs();
		}
		return fi;
	}

	/**
	 * 下载核查批次附件
	 * 
	 * @param fileName
	 *            附件
	 * @param fileType
	 *            附件类型
	 * @return
	 */
	public byte[] downLoadAttachment(String fileName) {
		byte[] fileContent = null;
		if (fileName != null) {
			try {
				File file = new File(getAttachmentFolder() + File.separator
						+ fileName);
				fileContent = FileUtils.readFileToByteArray(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileContent;
	}

	/**
	 * 删除核查批次附件
	 */
	public void deleteAttachment(String fileName,
			VFAttachmentManagement attachment) {
		if (fileName != null) {
			String[] fileNames = fileName.split("\\.");
			fileName = fileNames[0] + attachment.getId() + ".zip";
			File file = new File(getAttachmentFolder() + File.separator
					+ fileName);
			file.delete();
		}

		vfVerificationDao.delete(attachment);
	}

	/**
	 * 删除盘点核资料附件
	 */
	public void deleteAttachmentAll(VFSection section) {
		List<VFAttachmentManagement> parentList = vfVerificationDao
				.findVFAttachmentManagement(section, true);
		List<VFAttachmentManagement> chileList = vfVerificationDao
				.findVFAttachmentManagement(section, false);
		for (int i = 0; i < parentList.size(); i++) {
			VFAttachmentManagement parenAttachment = parentList.get(i);
			vfVerificationDao.delete(parenAttachment);
		}

		for (int i = 0; i < chileList.size(); i++) {
			VFAttachmentManagement chileAttachment = chileList.get(i);
			String fileName = chileAttachment.getAttachmentName();
			deleteAttachment(fileName, chileAttachment);
		}
	}

	/**
	 * 查询所有核查批次批次
	 * 
	 * @param request
	 * @param isExist
	 *            是否在附件管理中，已经存在
	 * @return
	 */
	public List<VFSection> findAttachmentSection(Boolean isExist) {
		return vfVerificationDao.findAttachmentSection(isExist);
	}

	/**
	 * 保存核查批次模板
	 * 
	 * @param request
	 * @param section
	 */
	public void saveVFAttachmentTemplate(VFSection section) {
		List<VFAttachmentManagement> list = iterateWholeXML(section);
		for (int i = 0; i < list.size(); i++) {
			VFAttachmentManagement attachment = (VFAttachmentManagement) list
					.get(i);
			attachment.setIsTemplate(true);
		}
		vfVerificationDao.batchSaveVFEnity(list);
		for (int j = 0; j < list.size(); j++) {
			VFAttachmentManagement attachment = (VFAttachmentManagement) list
					.get(j);
			List<VFAttachmentManagement> listChild = attachment.getChildren();
			for (int i = 0; i < listChild.size(); i++) {
				VFAttachmentManagement attachmentChild = listChild.get(i);
				attachmentChild.setParentId(attachment.getId());
				attachmentChild.setIsTemplate(true);
			}
			vfVerificationDao.batchSaveVFEnity(listChild);
		}
	}

	public List<VFAttachmentManagement> iterateWholeXML(VFSection section) {
		try {
			List<VFAttachmentManagement> list = new ArrayList<VFAttachmentManagement>();
			InputStream input = VFFactoryAnalyseLogic.class
					.getResourceAsStream("/com/bestway/bcs/verification/entity/VFAttachmentTemplate.xml");
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(input);
			Element root = document.getRootElement();
			int j = 0;
			Iterator superParentIterator = root.elementIterator();
			while (superParentIterator.hasNext()) {
				Element superParentEle = (Element) superParentIterator.next();

				VFAttachmentManagement emt = nodeToEntity(superParentEle,
						section);

				List subEleLs = superParentEle.elements();
				List<VFAttachmentManagement> emtChild = new ArrayList<VFAttachmentManagement>(
						subEleLs.size());
				for (int i = 0; i < subEleLs.size(); i++) {
					emtChild.add(nodeToEntity((Element) subEleLs.get(i),
							section));
				}
				emt.setChildren(emtChild);
				list.add(emt);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private VFAttachmentManagement nodeToEntity(Element e, VFSection section) {
		VFAttachmentManagement emt = new VFAttachmentManagement();
		List attrLs = e.attributes();
		Attribute attr = null;
		for (int i = 0; i < attrLs.size(); i++) {
			attr = (Attribute) attrLs.get(i);
			try {
				PropertyUtils.setSimpleProperty(emt, attr.getName(),
						attr.getStringValue());
			} catch (Exception ex) {
			}
		}
		emt.setSection(section);
		return emt;
	}

	// ///////////////////
	/**
	 * 汇总section【批次】在制品数据
	 * 
	 * @param request
	 * @param section
	 * @return
	 */
	public List<VFFinishingStockAnalyse> finishingStockAnalyse(VFSection section) {
		// 库存汇总
		List<VFFinishingStockAnalyse> finishingAnalyse = new ArrayList<VFFinishingStockAnalyse>();
		// 库存汇总
		Map<String, VFFinishingStockAnalyse> finishingStockAnalyseMap = new HashMap<String, VFFinishingStockAnalyse>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在计算在制品库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		// 先删除该批次下的汇总表
		vfVerificationDao.deleteByVFSection(section,
				VFFinishingStockAnalyse.class.getName());
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		List imgLs = vfVerificationDao.findConvertVFStockImgs(section,
				VFFinishingImg.class);
		putToFinishingStockAnalyse(finishingStockAnalyseMap, imgLs,
				"VFFinishingImg");

		List exgLs = vfVerificationDao.findConvertVFStockImgs(section,
				VFFinishingExgConvert.class);
		putToFinishingStockAnalyse(finishingStockAnalyseMap, exgLs,
				"VFFinishingExgConvert");
		if (finishingStockAnalyseMap != null) {
			Iterator finishingIterator = finishingStockAnalyseMap.values()
					.iterator();
			while (finishingIterator.hasNext()) {
				VFFinishingStockAnalyse finishingStockAnalyse = (VFFinishingStockAnalyse) finishingIterator
						.next();
				finishingStockAnalyse.setSection(section);
				finishingAnalyse.add(finishingStockAnalyse);
			}
		}
		if (finishingAnalyse.size() > 0) {
			vfVerificationDao.batchSaveOrUpdate(finishingAnalyse);
		}

		return finishingAnalyse;
	}

	/**
	 * 统计在制品库存汇总 总库存 = 在制品原材料+在制品成品折料
	 * 
	 * @param finishingStockMap
	 *            总库存
	 * @param list
	 *            在制品原材料+ 在制品成品折料
	 * @param clazz
	 */
	private void putToFinishingStockAnalyse(Map finishingStockMap, List list,
			String clazz) {
		if (list != null) {
			String key;
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				if (obj[0] != null) {
					key = obj[0].toString();// 备案序号
					VFFinishingStockAnalyse finishingStockAnalyse = (VFFinishingStockAnalyse) finishingStockMap
							.get(key);
					if (finishingStockAnalyse == null) {
						finishingStockAnalyse = new VFFinishingStockAnalyse();
						finishingStockAnalyse.init();
						finishingStockAnalyse.setMergerNo(Integer
								.valueOf(obj[0].toString()));
						finishingStockAnalyse.setHsName(obj[1] == null ? ""
								: obj[1].toString());
						finishingStockAnalyse.setHsSpec(obj[2] == null ? ""
								: obj[2].toString());
						finishingStockAnalyse.setHsUnit(obj[3] == null ? ""
								: obj[3].toString());
						if (clazz.equals("VFFinishingImg")) {// 在制品原材料
							finishingStockAnalyse
									.setImgHsAmount(obj[4] == null ? 0.0
											: Double.valueOf(obj[4].toString()));
						} else if (clazz.equals("VFFinishingExgConvert")) {// 在制品成品折料
							finishingStockAnalyse
									.setExgHsAmount(obj[4] == null ? 0.0
											: Double.valueOf(obj[4].toString()));
						}
						finishingStockAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(finishingStockAnalyse
										.getImgHsAmount()
										+ finishingStockAnalyse
												.getExgHsAmount()));
						finishingStockMap.put(key, finishingStockAnalyse);
					} else {// 累加
						if (clazz.equals("VFFinishingImg")) {
							finishingStockAnalyse
									.setImgHsAmount(finishingStockAnalyse
											.getImgHsAmount()
											+ (obj[4] == null ? 0.0 : Double
													.valueOf(obj[4].toString())));
						} else if (clazz.equals("VFFinishingExgConvert")) {
							finishingStockAnalyse
									.setExgHsAmount(finishingStockAnalyse
											.getExgHsAmount()
											+ (obj[4] == null ? 0.0 : Double
													.valueOf(obj[4].toString())));
						}
						finishingStockAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(finishingStockAnalyse
										.getImgHsAmount()
										+ finishingStockAnalyse
												.getExgHsAmount()));
					}
				}
			}
		}
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFSemiBadExg【残次品成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFSemiBadExg> findVFSemiBadExgs(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection, "VFSemiBadExg",
				seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFSemiBadExgConvert【残次品半成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFSemiBadExgConvert> findVFSemiBadExgConverts(
			VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection,
				"VFSemiBadExgConvert", seqNum);
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFSemiBadExg【残次品半成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFSemiBadExgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFSemiBadExg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFSemiBadExgConvert【残次品半成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFSemiBadExgConverts(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFSemiBadExgConvert");
	}

	/**
	 * 残次品半成品库存 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFSemiBadExgConvert> resolveVFSemiBadExgs(VFSection vfSection) {

		return resolveVFBaseStockExgs(vfSection, "VFSemiBadExg",
				new VFSemiBadExgConvert());
	}

	/**
	 * 残次品半成品折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertVFSemiBadExgConvertsEms(VFSection vfSection) {

		return convertVFBaseStockImgsEms(vfSection, "VFSemiBadExgConvert");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFBadExg【残次品成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFBadExgs(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFBadExg");
	}

	/**
	 * 删除 vfSection【核查批次】下的所有 VFBadExgConvert【残次品成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public void deleteVFBadExgConverts(VFSection vfSection) {
		vfVerificationDao.deleteByVFSection(vfSection, "VFBadExgConvert");
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFBadExg【残次品成品库存】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFBadExg> findVFBadExgs(VFSection vfSection, Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection, "VFBadExg", seqNum);
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFBadExgConvert【残次品成品折算数据】
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFBadExgConvert> findVFBadExgConverts(VFSection vfSection,
			Integer seqNum) {
		return vfVerificationDao.findByVFSection(vfSection, "VFBadExgConvert",
				seqNum);
	}

	/**
	 * 残次品成品库存 转 报关料件
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFBadExgConvert> resolveVFBadExgs(VFSection vfSection) {

		return resolveVFBaseStockExgs(vfSection, "VFBadExg",
				new VFBadExgConvert());
	}

	/**
	 * 残次品成品折算报关数量
	 * 
	 * @param vfSection
	 * @return
	 */
	public String convertVFBadExgConvertsEms(VFSection vfSection) {

		return convertVFBaseStockImgsEms(vfSection, "VFBadExgConvert");
	}

	/**
	 * 删除section【批次】残次品库存汇总
	 * 
	 * @param section
	 */
	public void deleteVFBadStockAnalyse(VFSection section) {
		vfVerificationDao.deleteByVFSection(section,
				VFBadStockAnalyse.class.getName());
	}

	/**
	 * 汇总section【批次】残次品数据
	 * 
	 * @param request
	 * @param section
	 * @return
	 */
	public List<VFBadStockAnalyse> badVFStockAnalyse(VFSection section) {
		// 库存汇总
		List<VFBadStockAnalyse> badAnalyse = new ArrayList<VFBadStockAnalyse>();
		// 库存汇总
		Map<String, VFBadStockAnalyse> badAnalyseMap = new HashMap<String, VFBadStockAnalyse>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在计算残次品库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		// 先删除该批次下的汇总表
		vfVerificationDao.deleteByVFSection(section,
				VFBadStockAnalyse.class.getName());
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		List imgLs = vfVerificationDao.findConvertVFStockImgs(section,
				VFBadImg.class);
		mergerBadStock(badAnalyseMap, imgLs, "VFBadImg");

		List outExgLs = vfVerificationDao.findConvertVFStockImgs(section,
				VFSemiBadExgConvert.class);
		mergerBadStock(badAnalyseMap, outExgLs, "VFSemiBadExgConvert");

		List exgLs = vfVerificationDao.findConvertVFStockImgs(section,
				VFBadExgConvert.class);
		mergerBadStock(badAnalyseMap, exgLs, "VFBadExgConvert");
		if (badAnalyseMap != null) {
			Iterator finishingIterator = badAnalyseMap.values().iterator();
			while (finishingIterator.hasNext()) {
				VFBadStockAnalyse badStockAnalyse = (VFBadStockAnalyse) finishingIterator
						.next();
				badStockAnalyse.setSection(section);
				badAnalyse.add(badStockAnalyse);
			}
		}
		if (badAnalyse.size() > 0) {
			vfVerificationDao.batchSaveOrUpdate(badAnalyse);
		}

		return badAnalyse;
	}

	/**
	 * 统计残次品库存汇总 总库存 = 残次品原材料+ 残次品成品折料+残次品半成品折料
	 * 
	 * @param badStockMap
	 *            总库存
	 * @param list
	 *            残次品原材料+ 残次品成品折料+残次品半成品折料
	 * @param clazz
	 */
	private void mergerBadStock(Map badStockMap, List list, String clazz) {
		String key = "";
		VFBadStockAnalyse badStocksAnalyse = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				if (obj[0] != null) {
					key = obj[0].toString();// 归并序号
					badStocksAnalyse = (VFBadStockAnalyse) badStockMap.get(key);
					if (badStocksAnalyse == null) {
						badStocksAnalyse = new VFBadStockAnalyse();
						badStocksAnalyse.init();
						badStocksAnalyse.setMergerNo(Integer.valueOf(obj[0]
								.toString()));
						badStocksAnalyse.setHsName(obj[1] == null ? "" : obj[1]
								.toString());
						badStocksAnalyse.setHsSpec(obj[2] == null ? "" : obj[2]
								.toString());
						badStocksAnalyse.setHsUnit(obj[3] == null ? "" : obj[3]
								.toString());
						if (clazz.equals("VFBadImg")) {// 残次品原材料
							badStocksAnalyse
									.setImgHsAmount(obj[4] == null ? 0.0
											: Double.valueOf(obj[4].toString()));
						} else if (clazz.equals("VFSemiBadExgConvert")) {// 残次品半成品折料
							badStocksAnalyse
									.setSemiExgHsAmount(obj[4] == null ? 0.0
											: Double.valueOf(obj[4].toString()));
						} else if (clazz.equals("VFBadExgConvert")) {// 残次品成品折料
							badStocksAnalyse
									.setExgHsAmount(obj[4] == null ? 0.0
											: Double.valueOf(obj[4].toString()));
						}
						badStocksAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(badStocksAnalyse
										.getImgHsAmount()
										+ badStocksAnalyse.getSemiExgHsAmount()
										+ badStocksAnalyse.getExgHsAmount()));
						badStockMap.put(key, badStocksAnalyse);
					} else {// 累加
						if (clazz.equals("VFBadImg")) {
							badStocksAnalyse.setImgHsAmount(badStocksAnalyse
									.getImgHsAmount()
									+ (obj[4] == null ? 0.0 : Double
											.valueOf(obj[4].toString())));
						} else if (clazz.equals("VFSemiBadExgConvert")) {
							badStocksAnalyse
									.setSemiExgHsAmount(badStocksAnalyse
											.getSemiExgHsAmount()
											+ (obj[4] == null ? 0.0 : Double
													.valueOf(obj[4].toString())));
						} else if (clazz.equals("VFBadExgConvert")) {
							badStocksAnalyse.setExgHsAmount(badStocksAnalyse
									.getExgHsAmount()
									+ (obj[4] == null ? 0.0 : Double
											.valueOf(obj[4].toString())));
						}
						badStocksAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(badStocksAnalyse
										.getImgHsAmount()
										+ badStocksAnalyse.getSemiExgHsAmount()
										+ badStocksAnalyse.getExgHsAmount()));

					}
				}
			}

		}
	}

	/**
	 * 查找section【批次】下残次品库存汇总
	 * 
	 * @param request
	 * @param section
	 * @param seqNum
	 *            归并序号
	 * @return
	 */
	public List<VFBadStockAnalyse> findVFBadAnalyse(VFSection section,
			Integer seqNum) {
		return vfVerificationDao
				.findByVFSection(section, VFBadStockAnalyse.class.getName(),
						seqNum, " order by mergerNo");
	}

	/**
	 * 查询操作进度
	 * 
	 * @param request
	 * @param section
	 * @return
	 */
	public List<Integer> findVFSectionByProcess(VFSection section) {
		List<Integer> prcess = new ArrayList<Integer>();

		// 3料件明细表
		List list = vfVerificationDao.findTableSize(section,
				VFCustomsImg.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(0);
		}
		// 3成品明细表
		list = vfVerificationDao.findTableSize(section,
				VFCustomsExg.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(1);
		}
		// 2料件情况统计表
		list = vfVerificationDao.findTableSize(section,
				VFCustomsCountImg.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(2);
		}
		// 2成品情况统计表
		list = vfVerificationDao.findTableSize(section,
				VFCustomsCountExg.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(3);
		}
		// 2成品折算统计表
		list = vfVerificationDao.findTableSize(section,
				VFCustomsCountExgConvert.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(32);
		}
		// 1帐册分析
		list = vfVerificationDao.findTableSize(section,
				VFContractAnalyse.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(4);
		}
		// 2料件库存
		list = vfVerificationDao.findTableSize(section,
				VFStockImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(5);
		}
		// 2在产品库存
		list = vfVerificationDao.findTableSize(section,
				VFStockProcessImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(6);
		}
		// 2成品库存
		list = vfVerificationDao.findTableSize(section,
				VFStockExgConvert.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(7);
		}
		// 2厂外库存
		list = vfVerificationDao.findTableSize(section,
				VFStockOutFactoryImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(8);
		}
		// 2内购库存
		list = vfVerificationDao.findTableSize(section,
				VFStockBuyImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(9);
		}
		// 2在途料件
		list = vfVerificationDao.findTableSize(section,
				VFStockTravelingImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(10);
		}
		// 2在途成品
		list = vfVerificationDao.findTableSize(section,
				VFStockTravelingExgConvert.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(11);
		}

		boolean b = false;
		// 外发库存原材料
		list = vfVerificationDao.findTableSize(section,
				VFStockOutSendImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(13);
		}
		// 外发库存半成品(料号级)
		list = vfVerificationDao.findTableSize(section,
				VFStockOutSendSemiExgConvert.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(14);
		}
		// 外发库存成品(料号级)
		list = vfVerificationDao.findTableSize(section,
				VFStockOutSendExgConvert.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(15);
		}
		// 外发库存汇总
		list = vfVerificationDao.findTableSize(section,
				VFStockOutSendAnalyse.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(16);
		}
		if (b) {
			prcess.add(12);
		}

		// 2半成品库存（已入库）
		list = vfVerificationDao.findTableSize(section,
				VFStockSemiExgHadStoreConvert.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(17);
		}

		b = false;
		// 在制品库存原材料
		list = vfVerificationDao.findTableSize(section,
				VFFinishingImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(19);
		}

		// 在制品库存成品(料号级)
		list = vfVerificationDao.findTableSize(section,
				VFFinishingExgConvert.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(20);
		}

		// 在制品库存汇总
		list = vfVerificationDao.findTableSize(section,
				VFFinishingStockAnalyse.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(21);
		}
		if (b) {
			prcess.add(18);
		}

		b = false;
		// 残次品库存原材料
		list = vfVerificationDao.findTableSize(section,
				VFBadImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(23);
		}

		// 残次品库存半成品(料号级)
		list = vfVerificationDao.findTableSize(section,
				VFSemiBadExgConvert.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(24);
		}

		// 残次品库存成品(料号级)
		list = vfVerificationDao.findTableSize(section,
				VFBadExgConvert.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(25);
		}

		// 残次品库存汇总
		list = vfVerificationDao.findTableSize(section,
				VFBadStockAnalyse.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			b = true;
			prcess.add(26);
		}

		if (b) {
			prcess.add(22);
		}

		// 盘点总库存
		list = vfVerificationDao.findTableSize(section,
				VFStockAnalyse.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(27);
		}

		// 料件结转情况表
		list = vfVerificationDao.findTableSize(section,
				VFTransferDiffImg.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(28);
		}

		// 成品结转情况表
		list = vfVerificationDao.findTableSize(section,
				VFTransferDiffExgConvert.class.getSimpleName(), true);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(29);
		}

		// 结转汇总情况表
		list = vfVerificationDao.findTableSize(section,
				VFTransferDiffCount.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(30);
		}
		// 短溢分析
		list = vfVerificationDao.findTableSize(section,
				VFAnalyse.class.getSimpleName(), null);
		if (list.size() > 0 && Integer.valueOf(list.get(0).toString()) > 0) {
			prcess.add(31);
		}
		return prcess;
	}
	// ///////////////////
}
