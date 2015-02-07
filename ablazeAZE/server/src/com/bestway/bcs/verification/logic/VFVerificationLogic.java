package com.bestway.bcs.verification.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.verification.dao.VFVerificationDao;
import com.bestway.bcs.verification.entity.VFAnalyse;
import com.bestway.bcs.verification.entity.VFBaseStockExg;
import com.bestway.bcs.verification.entity.VFBaseStockImg;
import com.bestway.bcs.verification.entity.VFCategory;
import com.bestway.bcs.verification.entity.VFCategoryAnalyse;
import com.bestway.bcs.verification.entity.VFCategoryBcsTenInnerMerge;
import com.bestway.bcs.verification.entity.VFContractAnalyse;
import com.bestway.bcs.verification.entity.VFCustomsCountExg;
import com.bestway.bcs.verification.entity.VFCustomsCountExgConvert;
import com.bestway.bcs.verification.entity.VFCustomsCountImg;
import com.bestway.bcs.verification.entity.VFCustomsExg;
import com.bestway.bcs.verification.entity.VFCustomsImg;
import com.bestway.bcs.verification.entity.VFFinishingExg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFSemiBadExg;
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
import com.bestway.bcs.verification.entity.VFStockTravelingImg;
import com.bestway.bcs.verification.entity.VFTransferDiffCount;
import com.bestway.bcs.verification.entity.VFTransferDiffExg;
import com.bestway.bcs.verification.entity.VFTransferDiffExgConvert;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.bcus.checkstock.entity.ECSAnalyse;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.jptds.client.common.CommonVars;

public class VFVerificationLogic {
	private VFVerificationDao vfVerificationDao;

	/**
	 * 更新短溢分析单价数据（同步关税、增值税栏位）
	 * 
	 * @param analyse
	 * @return
	 */
	public VFAnalyse updateAnalysePrice(VFAnalyse analyse) {
		Complex c = vfVerificationDao.findComplex(analyse.getComplex());
		Double rate = (c != null && StringUtils.isNotBlank(c.getLowrate())) ? NumberUtils
				.toDouble(c.getLowrate()) : 0.0;
		computeUsdAndUsdAdd(analyse, rate);
		return update(analyse);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 * @return
	 */
	public <E extends BaseEntity> E update(E entity) {
		vfVerificationDao.saveOrUpdateDirect(entity);
		return entity;
	}

	/**
	 * 根据批次判断是否存在数据
	 * 
	 * @param request
	 * @param section
	 * @param entityClass
	 * @return
	 */
	public boolean isExistsBySection(VFSection section,
			Class<? extends BaseScmEntity> entityClass) {
		return vfVerificationDao.isExistByVFSection(section,
				entityClass.getSimpleName());
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMerge(String materielType) {
		return vfVerificationDao.findBcsInnerMerge(materielType);
	}

	/**
	 * 查找 正在执行的合同料件,并从备案资料库带出归并序号
	 * 
	 * 请求控制
	 * 
	 * @param emsNo
	 *            合同号
	 * 
	 * @return List 是Object[]型，存放合同备案料件资料
	 */
	public List<Object[]> findContractImgByEmsNo(List<String> emsNos) {
		return vfVerificationDao.findContractImgByEmsNo(emsNos);
	}

	/**
	 * 查找 正在执行的合同成品,并从备案资料库带出归并序号
	 * 
	 * 请求控制
	 * 
	 * @param emsNo
	 *            合同号
	 * 
	 * @return List 是Object[]型，存放合同备案成品资料
	 */
	public List<Object[]> findContractExgByEmsNo(List<String> emsNos) {
		return vfVerificationDao.findContractExgByEmsNo(emsNos);
	}

	/**
	 * 获取成品对应bom版本列表
	 * 
	 * @return Map<PtNo(料号)#BOM版本号,MaterialBomVersion>
	 */
	public Map<String, MaterialBomVersion> getMaterielMasterBomVersion() {
		List<MaterialBomVersion> boms = vfVerificationDao
				.findMaterialBomVersions();
		Map<String, MaterialBomVersion> rs = new LinkedHashMap<String, MaterialBomVersion>(
				boms.size());
		for (MaterialBomVersion mbv : boms) {
			rs.put(mbv.getMaster().getMateriel().getPtNo().concat("#")
					.concat(mbv.getVersion().toString()), mbv);
		}
		return rs;
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockImg【 对应关系数据】
	 * 
	 * @param request
	 * @param vf
	 * @return
	 */
	public List<Object> findVFFactoryBcsInnerMergeList() {
		return vfVerificationDao.findVFfactionBcsInnerMergeList();
	}

	/**
	 * 查询 vfSection【核查批次】下的所有 VFStockImg【 对应关系数据】
	 * 
	 * @param request
	 * @param vf
	 * @return
	 */
	public List<Object> findVFOutSendExgInBom() {
		return vfVerificationDao.findVFOutSendExgInBom();
	}

	public List<Object> findVFOutSendExgInMaxBom() {
		return vfVerificationDao.findVFOutSendExgInMaxBom();
	}

	/**
	 * 批次设定 获得最大ID
	 */
	public List<Object> findMaxNO() {
		return vfVerificationDao.findMaxNO();
	}

	/**
	 * 批次设定 获得表数据
	 */
	public List<VFSection> findVFSectionList() {
		return vfVerificationDao.findVFSectionList();
	}

	/**
	 * 批次设定 delete data by
	 */
	public void deleteVFSection(VFSection vf) {
		List<String> entitys = new ArrayList<String>();

		// 短溢
		entitys.add(VFAnalyse.class.getName());
		// 结转
		entitys.add(VFTransferDiffCount.class.getName());
		entitys.add(VFTransferDiffExgConvert.class.getName());
		entitys.add(VFTransferDiffExg.class.getName());
		entitys.add(VFTransferDiffImg.class.getName());

		// 工厂
		entitys.add(VFStockAnalyse.class.getName());
		entitys.add(VFStockTravelingImg.class.getName());
		entitys.add(VFStockBuyImg.class.getName());
		entitys.add(VFStockOutFactoryImg.class.getName());

		entitys.add(VFStockOutSendExgConvert.class.getName());
		entitys.add(VFStockOutSendExg.class.getName());

		entitys.add(VFStockExgConvert.class.getName());
		entitys.add(VFStockExg.class.getName());

		entitys.add(VFStockProcessImg.class.getName());
		entitys.add(VFStockImg.class.getName());

		entitys.add(VFStockOutSendImg.class.getName());
		entitys.add(VFStockOutSendSemiExgConvert.class.getName());
		entitys.add(VFStockOutSendSemiExg.class.getName());
		entitys.add(VFStockOutSendAnalyse.class.getName());
		
		entitys.add(VFStockSemiExgHadStoreConvert.class.getName());
		entitys.add(VFStockSemiExgHadStore.class.getName());

		// 合同
		entitys.add(VFContractAnalyse.class.getName());
		entitys.add(VFCustomsCountExgConvert.class.getName());
		entitys.add(VFCustomsCountExg.class.getName());
		entitys.add(VFCustomsCountImg.class.getName());
		entitys.add(VFCustomsExg.class.getName());
		entitys.add(VFCustomsImg.class.getName());
		
		entitys.add(VFCategoryAnalyse.class.getName());

		for (String entity : entitys) {
			vfVerificationDao.deleteByVFSection(vf, entity);
		}

		// 批次
		vfVerificationDao.deleteVFSection(vf);
		// deleteVFSectionByList(vfsList); //保留
		return;
	}

	/**
	 * 批次设定 delete data by
	 */
	public void deleteVFSectionByList(List<VFSection> list) {
		for (int i = 0; i < list.size(); i++) {
			vfVerificationDao.deleteVFSection(list.get(i));
		}
		return;
	}

	/**
	 * 批次设定 save data
	 */
	public VFSection saveVFSection(VFSection vf) {
		
//		List<Object> list = this.findMaxNO();		
//		if (vf != null) {
//			if (list != null && list.get(0) != null) {
//				max = ((Integer) list.get(0)) + 1;
//			}
//			vf.setCompany((Company) CommonUtils.getCompany());
//			vf.setVerificationNo(max);
//			vfVerificationDao.saveVFSection(vf);
//		}
		
		
		if(vf.getId()==null || "".equals(vf.getId().trim()) || vf.getVerificationNo() == null){
			List<Object> list = this.findMaxNO();		
			if(list!=null&&list.size()>0){
				int max = Integer.parseInt(list.get(0)==null?"0":list.get(0).toString());
				vf.setVerificationNo(max+1);
			}
		}
		vf.setCompany((Company) CommonUtils.getCompany());
		vfVerificationDao.saveVFSection(vf);
		return  vf;
	}
	
	public void saveVFModifySection(VFSection vf){
		vfVerificationDao.saveVFModifySection(vf);
	}

	/**
	 * 批次设定 update data
	 */
	public void updateVFSection(VFSection vf) {
		vfVerificationDao.saveOrUpdate(vf);
	}

	/**
	 * 判断是否存在
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFSection> findExistsVFSection(VFSection vfSection) {
		return vfVerificationDao.findExistsVFSection(vfSection);
	}

	/**
	 * 删除section【批次】下核查分析汇总数据
	 * 
	 * @param section
	 */
	public void deleteVFAnalyse(VFSection section) {
		vfVerificationDao.deleteByVFSection(section, "VFAnalyse");
	}

	/**
	 * 短溢分析查看历史
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFAnalyse> findVFAnalyses(VFSection vfSection, Integer seqNum) {
		List<VFAnalyse> list = vfVerificationDao.findByVFSection(vfSection,
				VFAnalyse.class.getName(), seqNum, " order by mergerNo");
		for (int i = 0; i < list.size(); i++) {
			VFAnalyse vfAnalyse = list.get(i);
			//先将所有Double类型的小数位数设置成5位
			CommonUtils.formatDouble(vfAnalyse,5);
			//再设置金额类型的设置成2位
			vfAnalyse.setUsd(CommonUtils.getDoubleByDigit(vfAnalyse.getUsd(),2));
			vfAnalyse.setUsdAdd(CommonUtils.getDoubleByDigit(vfAnalyse.getUsdAdd(),2));
		}
		return list;
	}

	/**
	 * 大类短溢分析查看历史
	 * 
	 * @param vfSection
	 * @param seqNum
	 * @return
	 */
	public List<VFCategoryAnalyse> findVFCategoryAnalyses(VFSection vfSection,
			Integer seqNum) {
		List<VFCategoryAnalyse> list =  vfVerificationDao.findByVFSection(vfSection,
				VFCategoryAnalyse.class.getSimpleName(), seqNum);
		for (int i = 0; i < list.size(); i++) {
			//设置小数位数
			CommonUtils.formatDouble(list.get(i), 5);
		}
		return list;
	}

	/**
	 * 删除section【批次】下核查分析汇总数据
	 * 
	 * @param request
	 * @param section
	 */
	public void deleteVFCategoryAnalyse(VFSection section) {
		vfVerificationDao.deleteByVFSection(section,
				VFCategoryAnalyse.class.getSimpleName());
	}

	/**
	 * 大类短溢分析
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFCategoryAnalyse> categoryAnalyse(VFSection vfSection) {

		// 先删除之前的VFAnalyse统计数据
		vfVerificationDao.deleteByVFSection(vfSection,
				VFCategoryAnalyse.class.getSimpleName());

		Map<String, VFCategoryAnalyse> analyseMap = new HashMap<String, VFCategoryAnalyse>();
		// 加载大类对应表数据
		List<VFCategoryBcsTenInnerMerge> tmpLs = vfVerificationDao
				.findVFCategoryBcsTenInnerMerge();
		Map<String, VFCategory> categoryMap = new HashMap<String, VFCategory>();
		for (VFCategoryBcsTenInnerMerge cbim : tmpLs) {
			categoryMap.put(
					String.valueOf(cbim.getBcsTenInnerMerge().getSeqNum()),
					cbim.getCategory());
		}
		/** 统计合同数据 **/
		List<VFContractAnalyse> contractAnalyses = vfVerificationDao
				.findByVFSection(vfSection, "VFContractAnalyse");
		VFContractAnalyse ca = null;
		VFCategoryAnalyse analyse = null;
		for (int i = 0; i < contractAnalyses.size(); i++) {
			ca = contractAnalyses.get(i);
			analyse = getCategoryAnalyse(vfSection, ca, categoryMap, analyseMap);
			// 进口数量
			analyse.setImpAmount(analyse.getImpAmount() + ca.getImpAmount());
			// 出口总耗用
			analyse.setExpAmount(analyse.getExpAmount() + ca.getExpAmount());
			// 合同应剩余量 = 进口总数 - 出口耗用
			analyse.setContractLeaveNum(analyse.getImpAmount()
					- analyse.getExpAmount());
			// 短溢 = 工厂库存 + 结转差额 - 合同结余
			analyse.setOverOrshortNum(analyse.getStockTotalAmount()
					+ analyse.getTransferDiffNum()
					- analyse.getContractLeaveNum());
		}

		/** 统计库存数据 **/
		List<VFStockAnalyse> stockAnalyses = vfVerificationDao.findByVFSection(
				vfSection, "VFStockAnalyse");
		VFStockAnalyse sa = null;
		for (int i = 0; i < stockAnalyses.size(); i++) {
			sa = stockAnalyses.get(i);
			analyse = getCategoryAnalyse(vfSection, sa, categoryMap, analyseMap);

			// 料件库存数量
			analyse.setStockAmountImg(analyse.getStockAmountImg()
					+ sa.getStockAmountImg());
			// 在产品库存数量
			analyse.setStockAmountProcessImg(analyse.getStockAmountProcessImg()
					+ sa.getStockAmountProcessImg());
			// 成品库存
			analyse.setStockAmountExg(analyse.getStockAmountExg()
					+ sa.getStockAmountExg());
			// 外发库存
			analyse.setStockAmountOutSend(analyse.getStockAmountOutSend()
					+ sa.getStockAmountOutSend());
			// 厂外存放库存
			analyse.setStockAmountOutFactory(analyse.getStockAmountOutFactory()
					+ sa.getStockAmountOutFactory());
			// 内购库存
			analyse.setStockAmountBuy(analyse.getStockAmountBuy()
					+ sa.getStockAmountBuy());
			// 在途库存
			analyse.setStockAmountTraveling(analyse.getStockAmountTraveling()
					+ sa.getStockAmountTraveling());
			// 库存数量
			analyse.setStockTotalAmount(analyse.getStockTotalAmount()
					+ sa.getStockTotalAmount());
			// 短溢 = 工厂库存 + 结转差额 - 合同结余
			analyse.setOverOrshortNum(analyse.getStockTotalAmount()
					+ analyse.getTransferDiffNum()
					- analyse.getContractLeaveNum());
		}

		/*** 统计结转数据 ***/
		List<VFTransferDiffCount> transferDiffCounts = vfVerificationDao
				.findByVFSection(vfSection, "VFTransferDiffCount");
		VFTransferDiffCount tdc = null;
		for (int i = 0; i < transferDiffCounts.size(); i++) {
			tdc = transferDiffCounts.get(i);
			analyse = getCategoryAnalyse(vfSection, tdc, categoryMap,
					analyseMap);
			// 已转厂未收货
			analyse.setUnReceiveHadTransNum(analyse.getUnReceiveHadTransNum()
					+ tdc.getConvertUnReceiveHadTransNum());
			// 已送货未转厂
			analyse.setUnTransHadSendNum(analyse.getUnTransHadSendNum()
					+ tdc.getConvertUnTransHadSendNum());
			// 已转厂未送货
			analyse.setUnSendHadTransNum(analyse.getUnSendHadTransNum()
					+ tdc.getConvertUnSendHadTransNum());
			// 已收货未转厂
			analyse.setUnTransHadReceiveNum(analyse.getUnTransHadReceiveNum()
					+ tdc.getConvertUnTransHadReceiveNum());

			// 结转差额=已转厂未收货+已送货未转厂-已转厂未送货-已收货未转厂
			analyse.setTransferDiffNum(analyse.getUnReceiveHadTransNum()
					+ analyse.getUnTransHadSendNum()
					- analyse.getUnSendHadTransNum()
					- analyse.getUnTransHadReceiveNum());

			// 短溢 = 工厂库存 + 结转差额 - 合同结余
			analyse.setOverOrshortNum(analyse.getStockTotalAmount()
					+ analyse.getTransferDiffNum()
					- analyse.getContractLeaveNum());
		}

		List<VFCategoryAnalyse> list = new ArrayList<VFCategoryAnalyse>(
				analyseMap.values());
		Collections.sort(list, new Comparator<VFCategoryAnalyse>() {
			public int compare(VFCategoryAnalyse o1, VFCategoryAnalyse o2) {
				if (NumberUtils.isDigits(o1.getSeqNum())
						&& NumberUtils.isNumber(o2.getSeqNum()))
					return (Integer.valueOf(o1.getSeqNum()) < Integer
							.valueOf(o2.getSeqNum()) ? -1 : 1);
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});
		this.vfVerificationDao.batchSaveOrUpdateDirect(list);
		return list;
	}

	/**
	 * 合同分析中获取分析数据
	 * 
	 * @param vfSection
	 *            批次
	 * @param analyse
	 *            分析数据
	 * @param categoryMap
	 *            大类别
	 * @param analyseMap
	 *            分析结果表
	 * @return
	 */
	private VFCategoryAnalyse getCategoryAnalyse(VFSection vfSection,
			VFContractAnalyse analyse, Map<String, VFCategory> categoryMap,
			Map<String, VFCategoryAnalyse> analyseMap) {
		Integer mergerNo = analyse.getMergerNo();
		VFCategoryAnalyse categoryAnalyse = analyseMap.get(String
				.valueOf(mergerNo));
		if (categoryAnalyse == null) {
			VFCategory cg = categoryMap.get(String.valueOf(mergerNo));
			if (cg == null) {
				categoryAnalyse = new VFCategoryAnalyse();
				categoryAnalyse.init();
				categoryAnalyse.setSection(vfSection);
				categoryAnalyse.setSeqNum(String.valueOf(mergerNo));
				categoryAnalyse.setMergerNo(mergerNo);
				categoryAnalyse.setHsName(analyse.getHsName());
				categoryAnalyse.setHsSpec(analyse.getHsSpec());
				categoryAnalyse.setHsUnit(analyse.getHsUnit());
				analyseMap.put(categoryAnalyse.getSeqNum(), categoryAnalyse);
			} else {
				categoryAnalyse = analyseMap.get("*" + cg.getSeqNum());
				if (categoryAnalyse == null) {
					categoryAnalyse = new VFCategoryAnalyse();
					categoryAnalyse.init();
					categoryAnalyse.setSection(vfSection);
					categoryAnalyse.setSeqNum("*"
							+ String.valueOf(cg.getSeqNum()));
					categoryAnalyse.setMergerNo(cg.getSeqNum());
					categoryAnalyse.setHsName(cg.getComplexName());
					categoryAnalyse.setHsSpec(cg.getSpec());
					categoryAnalyse.setHsUnit(cg.getUnit());
					analyseMap
							.put(categoryAnalyse.getSeqNum(), categoryAnalyse);
				}
			}
		}
		return categoryAnalyse;
	}

	/**
	 * 工厂库存分析中获取分析数据
	 * 
	 * @param vfSection
	 *            批次
	 * @param analyse
	 *            分析数据
	 * @param categoryMap
	 *            大类别
	 * @param analyseMap
	 *            分析结果表
	 * @return
	 */
	private VFCategoryAnalyse getCategoryAnalyse(VFSection vfSection,
			VFStockAnalyse analyse, Map<String, VFCategory> categoryMap,
			Map<String, VFCategoryAnalyse> analyseMap) {
		Integer mergerNo = analyse.getMergerNo();
		VFCategoryAnalyse categoryAnalyse = analyseMap.get(String
				.valueOf(mergerNo));
		if (categoryAnalyse == null) {
			VFCategory cg = categoryMap.get(String.valueOf(mergerNo));
			if (cg == null) {
				categoryAnalyse = new VFCategoryAnalyse();
				categoryAnalyse.init();
				categoryAnalyse.setSection(vfSection);
				categoryAnalyse.setSeqNum(String.valueOf(mergerNo));
				categoryAnalyse.setMergerNo(mergerNo);
				categoryAnalyse.setHsName(analyse.getHsName());
				categoryAnalyse.setHsSpec(analyse.getHsSpec());
				categoryAnalyse.setHsUnit(analyse.getHsUnit());
				analyseMap.put(categoryAnalyse.getSeqNum(), categoryAnalyse);
			} else {
				categoryAnalyse = analyseMap.get("*" + cg.getSeqNum());
				if (categoryAnalyse == null) {
					categoryAnalyse = new VFCategoryAnalyse();
					categoryAnalyse.init();
					categoryAnalyse.setSection(vfSection);
					categoryAnalyse.setSeqNum("*"
							+ String.valueOf(cg.getSeqNum()));
					categoryAnalyse.setMergerNo(cg.getSeqNum());
					categoryAnalyse.setHsName(cg.getComplexName());
					categoryAnalyse.setHsSpec(cg.getSpec());
					categoryAnalyse.setHsUnit(cg.getUnit());
					analyseMap
							.put(categoryAnalyse.getSeqNum(), categoryAnalyse);
				}
			}
		}
		return categoryAnalyse;
	}

	/**
	 * 结转分析中获取分析数据
	 * 
	 * @param vfSection
	 *            批次
	 * @param analyse
	 *            分析数据
	 * @param categoryMap
	 *            大类别
	 * @param analyseMap
	 *            分析结果表
	 * @return
	 */
	private VFCategoryAnalyse getCategoryAnalyse(VFSection vfSection,
			VFTransferDiffCount analyse, Map<String, VFCategory> categoryMap,
			Map<String, VFCategoryAnalyse> analyseMap) {
		Integer mergerNo = analyse.getMergerNo();
		VFCategoryAnalyse categoryAnalyse = analyseMap.get(String
				.valueOf(mergerNo));
		if (categoryAnalyse == null) {
			VFCategory cg = categoryMap.get(String.valueOf(mergerNo));
			if (cg == null) {
				categoryAnalyse = new VFCategoryAnalyse();
				categoryAnalyse.init();
				categoryAnalyse.setSection(vfSection);
				categoryAnalyse.setSeqNum(String.valueOf(mergerNo));
				categoryAnalyse.setMergerNo(mergerNo);
				categoryAnalyse.setHsName(analyse.getHsName());
				categoryAnalyse.setHsSpec(analyse.getHsSpec());
				categoryAnalyse.setHsUnit(analyse.getHsUnit());
				analyseMap.put(categoryAnalyse.getSeqNum(), categoryAnalyse);
			} else {
				categoryAnalyse = analyseMap.get("*" + cg.getSeqNum());
				if (categoryAnalyse == null) {
					categoryAnalyse = new VFCategoryAnalyse();
					categoryAnalyse.init();
					categoryAnalyse.setSection(vfSection);
					categoryAnalyse.setSeqNum("*"
							+ String.valueOf(cg.getSeqNum()));
					categoryAnalyse.setMergerNo(cg.getSeqNum());
					categoryAnalyse.setHsName(cg.getComplexName());
					categoryAnalyse.setHsSpec(cg.getSpec());
					categoryAnalyse.setHsUnit(cg.getUnit());
					analyseMap
							.put(categoryAnalyse.getSeqNum(), categoryAnalyse);
				}
			}
		}
		return categoryAnalyse;
	}

	/**
	 * 短溢分析
	 * 
	 * @param vfSection
	 * @return
	 */
	public List<VFAnalyse> verificationAnalyse(VFSection vfSection) {

		// 先删除之前的VFAnalyse统计数据
		vfVerificationDao.deleteByVFSection(vfSection, "VFAnalyse");

		Map<Integer, VFAnalyse> analyseMap = new HashMap<Integer, VFAnalyse>();

		List<BcsTenInnerMerge> innerMerges = vfVerificationDao.findBcsTenInnerMerge();
		Map<Integer, BcsTenInnerMerge> mergeMap = new HashMap<Integer, BcsTenInnerMerge>();
		BcsTenInnerMerge merge = null;
		for (int i = 0; i < innerMerges.size(); i++) {
			merge = innerMerges.get(i);
			if (merge.getComplex() != null) {
				mergeMap.put(merge.getSeqNum(), merge);
			}
		}
		/**
		 * 单价信息 数据来源于统计合同数据中的单价
		 */
		Map<Integer, Double> priceMap = new HashMap<Integer, Double>();

		/**
		 * 统计合同数据
		 */
		List<VFContractAnalyse> contractAnalyses = vfVerificationDao
				.findByVFSection(vfSection, "VFContractAnalyse");
		Integer mergerNo = null;
		VFContractAnalyse ca = null;
		VFAnalyse analyse = null;
		for (int i = 0; i < contractAnalyses.size(); i++) {
			ca = contractAnalyses.get(i);
			mergerNo = ca.getMergerNo();
			analyse = analyseMap.get(mergerNo);

			if (analyse == null) {
				analyse = new VFAnalyse();
				analyse.init();
				analyse.setSection(vfSection);
				analyse.setMergerNo(mergerNo);
				if (mergeMap.get(mergerNo) != null) {
					BcsTenInnerMerge bcsTenInnerMerge = mergeMap.get(mergerNo);
					analyse.setComplex(bcsTenInnerMerge.getComplex().getCode());
					analyse.setHsName(bcsTenInnerMerge.getName());
					analyse.setHsSpec(bcsTenInnerMerge.getSpec());
					analyse.setHsUnit(bcsTenInnerMerge.getComUnit().getName());
				}
				analyseMap.put(mergerNo, analyse);

				priceMap.put(mergerNo, ca.getUnitPrice());
			}

			// 进口数量
			analyse.setImpAmount(analyse.getImpAmount() + ca.getImpAmount());

			// 出口总耗用
			analyse.setExpAmount(analyse.getExpAmount() + ca.getExpAmount());

			// 合同应剩余量 = 进口总数 - 出口耗用
			analyse.setContractLeaveNum(analyse.getImpAmount()
					- analyse.getExpAmount());

			// 短溢 = 工厂库存 + 结转差额 - 合同结余
			analyse.setOverOrshortNum(analyse.getStockTotalAmount()
					+ analyse.getTransferDiffNum()
					- analyse.getContractLeaveNum());
		}
		/**
		 * 统计库存数据
		 */
		List<VFStockAnalyse> stockAnalyses = vfVerificationDao.findByVFSection(
				vfSection, "VFStockAnalyse");
		List buyIsCountList = vfVerificationDao
				.findBuyIsCount(vfSection.getId()); 
		Boolean flag = null == buyIsCountList.get(0) ? false
				: (Boolean) buyIsCountList.get(0);
		VFStockAnalyse sa = null;
		for (int i = 0; i < stockAnalyses.size(); i++) {
			sa = stockAnalyses.get(i);
			mergerNo = sa.getMergerNo();
			analyse = analyseMap.get(mergerNo);

			if (analyse == null) {
				analyse = new VFAnalyse();
				analyse.init();
				analyse.setSection(vfSection);
				analyse.setMergerNo(mergerNo);
				if (mergeMap.get(mergerNo) != null) {
					BcsTenInnerMerge bcsTenInnerMerge = mergeMap.get(mergerNo);
					analyse.setComplex(bcsTenInnerMerge.getComplex().getCode());
					analyse.setHsName(bcsTenInnerMerge.getName());
					analyse.setHsSpec(bcsTenInnerMerge.getSpec());
					analyse.setHsUnit(bcsTenInnerMerge.getComUnit().getName());
				}

				analyseMap.put(mergerNo, analyse);
			}

			// 料件库存数量
			analyse.setStockAmountImg(analyse.getStockAmountImg()
					+ sa.getStockAmountImg());

			// 在产品库存数量
			analyse.setStockAmountProcessImg(analyse.getStockAmountProcessImg()
					+ sa.getStockAmountProcessImg());

			// 成品库存
			analyse.setStockAmountExg(analyse.getStockAmountExg()
					+ sa.getStockAmountExg());

			// 外发库存
			analyse.setStockAmountOutSend(analyse.getStockAmountOutSend()
					+ sa.getStockAmountOutSend());

			// 厂外存放库存
			analyse.setStockAmountOutFactory(analyse.getStockAmountOutFactory()
					+ sa.getStockAmountOutFactory());

			// 内购库存
			if(flag){
			analyse.setStockAmountBuy(analyse.getStockAmountBuy()
					+ sa.getStockAmountBuy());
			}

			// 在途库存
			analyse.setStockAmountTraveling(analyse.getStockAmountTraveling()
					+ sa.getStockAmountTraveling()+sa.getStockAmountTravelingExg());
			
			// 半成品库存(已入库)
			analyse.setStockAmountSemiExgHadStore(analyse.getStockAmountSemiExgHadStore()
					+ sa.getStockAmountSemiExgHadStore());
			
			// 在制品库存
			analyse.setStockAmountFinishing(analyse.getStockAmountFinishing()
					+ sa.getStockAmountFinishing());
			
			// 残次品库存
			analyse.setStockAmountBad(analyse.getStockAmountBad()
					+ sa.getStockAmountBad());
			
			// 库存数量
			analyse.setStockTotalAmount(analyse.getStockTotalAmount()
					+ sa.getStockTotalAmount());

			// 短溢 = 工厂库存 + 结转差额 - 合同结余
			analyse.setOverOrshortNum(analyse.getStockTotalAmount()
					+ analyse.getTransferDiffNum()
					- analyse.getContractLeaveNum());
		}
		/**
		 * 统计结转数据
		 */
		List<VFTransferDiffCount> transferDiffCounts = vfVerificationDao
				.findByVFSection(vfSection, "VFTransferDiffCount");
		VFTransferDiffCount tdc = null;
		for (int i = 0; i < transferDiffCounts.size(); i++) {
			tdc = transferDiffCounts.get(i);
			mergerNo = tdc.getMergerNo();
			analyse = analyseMap.get(mergerNo);

			if (analyse == null) {
				analyse = new VFAnalyse();
				analyse.init();
				analyse.setSection(vfSection);
				analyse.setMergerNo(mergerNo);
				if (mergeMap.get(mergerNo) != null) {
					BcsTenInnerMerge bcsTenInnerMerge = mergeMap.get(mergerNo);
					analyse.setComplex(bcsTenInnerMerge.getComplex().getCode());
					analyse.setHsName(bcsTenInnerMerge.getName());
					analyse.setHsSpec(bcsTenInnerMerge.getSpec());
					analyse.setHsUnit(bcsTenInnerMerge.getComUnit().getName());
				}

				analyseMap.put(mergerNo, analyse);
			}

			// 已转厂未收货
			analyse.setUnReceiveHadTransNum(analyse.getUnReceiveHadTransNum()
					+ tdc.getConvertUnReceiveHadTransNum());
			// 已送货未转厂
			analyse.setUnTransHadSendNum(analyse.getUnTransHadSendNum()
					+ tdc.getConvertUnTransHadSendNum());
			// 已转厂未送货
			analyse.setUnSendHadTransNum(analyse.getUnSendHadTransNum()
					+ tdc.getConvertUnSendHadTransNum());
			// 已收货未转厂
			analyse.setUnTransHadReceiveNum(analyse.getUnTransHadReceiveNum()
					+ tdc.getConvertUnTransHadReceiveNum());

			// 结转差额=已转厂未收货+已送货未转厂-已转厂未送货-已收货未转厂
			analyse.setTransferDiffNum(analyse.getUnReceiveHadTransNum()
					+ analyse.getUnTransHadSendNum()
					- analyse.getUnSendHadTransNum()
					- analyse.getUnTransHadReceiveNum());

			// 短溢 = 工厂库存 + 结转差额 - 合同结余
			analyse.setOverOrshortNum(analyse.getStockTotalAmount()
					+ analyse.getTransferDiffNum()
					- analyse.getContractLeaveNum());
		}

		List<VFAnalyse> list = new ArrayList<VFAnalyse>(analyseMap.values());
		double rate = 0; // 优惠税率
		double price = 0; // 单价
		// 关联价格、计算关税和增值税
		for (int i = 0; i < list.size(); i++) {
			analyse = list.get(i);
			mergerNo = analyse.getMergerNo();
			// 优惠税率
			if (mergeMap.get(mergerNo) != null) {
				rate = StringUtils.isNotBlank(mergeMap.get(mergerNo)
						.getComplex().getLowrate()) ? Double.parseDouble(mergeMap.get(
						mergerNo).getComplex().getLowrate()) : 0.0;
			}
			price = priceMap.get(mergerNo) == null ? 0.0 : priceMap
					.get(mergerNo);
			// 设置单价
			analyse.setPrice(price);

			// 计算关税增值税
			computeUsdAndUsdAdd(analyse, rate);
		}
		Collections.sort(list, new Comparator<VFAnalyse>() {
			public int compare(VFAnalyse o1, VFAnalyse o2) {
				return o1.getMergerNo().compareTo(o2.getMergerNo());
			}
		});

		this.vfVerificationDao.batchSaveVFEnity(list);

		return list;
	}
	
	public List findBuyIsCount(String sectionid){
		return vfVerificationDao.findBuyIsCount(sectionid);
	}

	public List findMaxBuyIsCount(){
		return vfVerificationDao.findMaxBuyIsCount();
	}
	/**
	 * 计算关税增值税
	 * 
	 * @param analyse
	 * @param rate
	 */
	private void computeUsdAndUsdAdd(VFAnalyse analyse, Double rate) {
		// 关税 =差异数*单价*该备案序号最新报关单商品编码对应的最优税率
		analyse.setUsd(CaleUtil.multiply(
				CaleUtil.abs(analyse.getOverOrshortNum() * analyse.getPrice()),
				rate, 5));

		// 增值税 = (差异数*单价+关税)*17%。
		analyse.setUsdAdd(CaleUtil.multiply(
				(CaleUtil.abs(analyse.getOverOrshortNum()) * analyse.getPrice() + analyse
						.getUsd()), 0.17, 5));
	}

	/**
	 * 查询所有的报关工厂常用bom版本
	 * 
	 * @return
	 */
	public List<MaterialBomVersion> findMaterialBomVersions() {
		return vfVerificationDao.findMaterialBomVersions();
	}

	/**
	 * @param vfVerificationDao
	 *            the vfVerificationDao to set
	 */
	public void setVfVerificationDao(VFVerificationDao vfVerificationDao) {
		this.vfVerificationDao = vfVerificationDao;
	}

	/**
	 * 查询未归类商品编码
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerNotInCategory(int index,
			int length, String property, Object value, boolean isLike) {
		return vfVerificationDao.findBcsTenInnerNotInCategory(index, length,
				property, value, isLike);
	}

	/**
	 * 添加报关商品到大类中
	 * 
	 * @param list
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> addBcsTenInnerMergeToCategory(
			List<BcsTenInnerMerge> list) {
		List<VFCategoryBcsTenInnerMerge> rsList = new ArrayList<VFCategoryBcsTenInnerMerge>(
				list.size());
		VFCategoryBcsTenInnerMerge categoryMerger = null;
		for (BcsTenInnerMerge m : list) {
			categoryMerger = new VFCategoryBcsTenInnerMerge();
			categoryMerger.setBcsTenInnerMerge(m);
			categoryMerger.setCreateDate(new Date());
			categoryMerger.setCreatePeople(CommonUtils.getRequest().getUser()
					.getName());
			rsList.add(categoryMerger);
		}
		vfVerificationDao.batchSaveOrUpdate(rsList);
		return rsList;
	}

	/**
	 * 查询大类名称列表
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<VFCategory> findVFCategory(int index, int length,
			String property, Object value, boolean isLike) {
		return vfVerificationDao.findVFCategory(index, length, property, value,
				isLike);
	}

	/**
	 * 查询大类报关商品对应关系表
	 * 
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> findVFCategoryBcsTenInnerMerge(
			int index, int length, String property, Object value, boolean isLike) {
		return vfVerificationDao.findVFCategoryBcsTenInnerMerge(index, length,
				property, value, isLike);
	}

	/**
	 * 删除大类对应关系表
	 * 
	 * @param sels
	 */
	public void deleteVFCategoryBcsTenInnerMerge(
			List<VFCategoryBcsTenInnerMerge> sels) {
		vfVerificationDao.deleteAll(sels);
		for (VFCategoryBcsTenInnerMerge btim : sels) {
			deleteCategory(btim.getCategory());
		}
	}

	/**
	 * 删除大类，在删除前判断是否存在关联关系
	 * 
	 * @param category
	 */
	private void deleteCategory(VFCategory category) {
		if (!vfVerificationDao.isExistsCategoryBcsTenInnerMerge(category)) {
			vfVerificationDao.deleteCategor(category);
		}
	}

	/**
	 * 撤销大类报关对应关系
	 * 
	 * @param sels
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> undoVFCategoryBcsTenInnerMerge(
			List<VFCategoryBcsTenInnerMerge> sels) {
		List<VFCategory> cateLs = new ArrayList<VFCategory>();
		for (VFCategoryBcsTenInnerMerge cbcm : sels) {
			cateLs.add(cbcm.getCategory());

			cbcm.setCategory(null);
			cbcm.setModifyDate(new Date());
			cbcm.setModifyPeople(CommonUtils.getRequest().getUser().getName());
		}
		vfVerificationDao.batchSaveOrUpdate(sels);
		for (VFCategory c : cateLs) {
			deleteCategory(c);
		}
		return sels;
	}

	/**
	 * 添加补充大类对应关系
	 * 
	 * @param categoryInnerMerges
	 *            大类对应关系
	 * @param category
	 *            大类
	 * @param isAdd
	 *            是否新增大类
	 * @return
	 */
	public List<VFCategoryBcsTenInnerMerge> addCategoryToBcsTenInnerMergeCategory(
			List<VFCategoryBcsTenInnerMerge> categoryInnerMerges,
			VFCategory category, boolean isAdd) {
		if (isAdd)
			category = saveVFCategory(category);
		for (VFCategoryBcsTenInnerMerge cbcm : categoryInnerMerges) {
			cbcm.setCategory(category);
			cbcm.setModifyDate(new Date());
			cbcm.setModifyPeople(CommonUtils.getRequest().getUser().getName());
		}
		vfVerificationDao.batchSaveOrUpdate(categoryInnerMerges);
		return categoryInnerMerges;
	}

	/**
	 * 保存大类
	 * 
	 * @param category
	 * @return
	 */
	public VFCategory saveVFCategory(VFCategory category) {
		if (category.getId() == null) {
			Integer seqNum = vfVerificationDao.getMaxSeqNumOfVFCategory();
			category.setSeqNum(seqNum + 1);
		}
		vfVerificationDao.saveOrUpdate(category);
		return category;
	}
	
	/**
	 * 保存成品
	 * @param request
	 * @param baseStockExg
	 */
	public void saveVFBaseStockExg(VFBaseStockExg baseStockExg){
		vfVerificationDao.saveOrUpdate(baseStockExg);
	}
	
	/**
	 * 保存料件
	 * @param request
	 * @param baseStockImg
	 */
	public void saveVFBaseStockImg(VFBaseStockImg baseStockImg){
		vfVerificationDao.saveOrUpdate(baseStockImg);
	}
	
	/**
	 * 删除成品
	 * @param request
	 * @param baseStockExg
	 */
	public void deleteVFBaseStockExgs(List<VFBaseStockExg> baseStockExgs){
		for (int i = 0; i < baseStockExgs.size(); i++) {
			String entityClassName = null;
			if(baseStockExgs.get(i) instanceof VFStockExg){
				entityClassName = "VFStockExgConvert";
			}else if(baseStockExgs.get(i) instanceof VFFinishingExg){
				entityClassName = "VFFinishingExgConvert";
			}else if(baseStockExgs.get(i) instanceof VFSemiBadExg){
				entityClassName = "VFSemiBadExgConvert";
			}else if(baseStockExgs.get(i) instanceof VFStockOutSendExg){
				entityClassName = "VFStockOutSendExgConvert";
			}else if(baseStockExgs.get(i) instanceof VFStockOutSendSemiExg){
				entityClassName = "VFStockOutSendSemiExgConvert";
			} else if(baseStockExgs.get(i) instanceof VFStockSemiExgHadStore){
				entityClassName = "VFStockSemiExgHadStoreConvert";
			}else if(baseStockExgs.get(i) instanceof VFStockTravelingExg){
				entityClassName = "VFStockTravelingExgConvert";
			}
			
			List list = vfVerificationDao.findEntitys(entityClassName,"stockExg.id",baseStockExgs.get(i).getId());
			vfVerificationDao.deleteAll(list);
		}
		
		vfVerificationDao.deleteAll(baseStockExgs);
	}
	
	/**
	 * 删除料件
	 * @param request
	 * @param baseStockImg
	 */
	public void deleteVFBaseStockImgs(List<VFBaseStockImg> baseStockImgs){
		vfVerificationDao.deleteAll(baseStockImgs);
	}
}
