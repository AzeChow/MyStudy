package com.bestway.bcus.checkstock.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.context.ServletContextAware;

import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkstock.dao.ECSCheckStockDao;
import com.bestway.bcus.checkstock.entity.ECSAnalyse;
import com.bestway.bcus.checkstock.entity.ECSAttachmentManagement;
import com.bestway.bcus.checkstock.entity.ECSBadImg;
import com.bestway.bcus.checkstock.entity.ECSBadStockResolve;
import com.bestway.bcus.checkstock.entity.ECSContractAnalyse;
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
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;

public class ECSCheckStockLogic implements ServletContextAware {
	private ECSCheckStockDao ecsCheckStockDao;
	/**
	 * 锁对象
	 */
	private Lock lock = new ReentrantLock();

	/**
	 * 根据批次判断是否存在数据
	 * 
	 * @param section
	 * @param entityClass
	 * @return
	 */
	public boolean isExistsBySection(ECSSection section,
			Class<? extends BaseScmEntity> entityClass) {
		return ecsCheckStockDao.isExistByECSSection(section,
				entityClass.getSimpleName());
	}

	/******************************************* 查询归并关系 开始 ************************************/
	/**
	 * 根据指定料号列表 查询【料件】归并关系(工厂部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergePtPartImg() {
		return ecsCheckStockDao.findAllEdiMergePtPartImg();
	}

	/**
	 * 根据指定料号列表 查询【成品】归并关系(工厂部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergePtPartExg() {
		return ecsCheckStockDao.findAllEdiMergePtPartExg();
	}

	/**
	 * 根据指定料号列表 查询【料件】归并关系(报关部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergeHsPartImg() {
		return ecsCheckStockDao.findAllEdiMergeHsPartImg();
	}

	/**
	 * 根据指定料号列表 查询【成品】归并关系(报关部分信息)。
	 * 
	 * @return
	 */
	public List<MergeTemp> findAllEdiMergeHsPartExg() {
		return ecsCheckStockDao.findAllEdiMergeHsPartExg();
	}

	/******************************************* 查询归并关系结束 ************************************/

	/**
	 * 查询 指定批次 section 下的【短溢分析数据】。
	 * 
	 * @param section
	 */
	public List<ECSAnalyse> findECSAnalyses(ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSAnalyse.class.getName(), seqNum);
	}

	/**
	 * 删除 指定批次 section 下的【短溢分析数据】。
	 * 
	 * @param section
	 */
	public void deleteECSAnalyses(ECSSection section) {
		ecsCheckStockDao
				.deleteByECSSection(section, ECSAnalyse.class.getName());
	}

	/**
	 * 更新短益分析单价
	 * 
	 * @param a
	 * @return
	 */
	public ECSAnalyse updateAnalysePrice(ECSAnalyse a) {
		Double lowrate = ecsCheckStockDao.findComplexLowrate(a.getSection()
				.getCancelOwnerHead().getEmsNo(), a.getSeqNum());
		Double tmp = CaleUtil.multiply(a.getDiffAmount(), a.getPrice());
		// 计算关税
		a.setUsd(CaleUtil.multiply(tmp, lowrate));
		// 计算增值税
		a.setUsdAdd(CaleUtil.multiply(CaleUtil.add(a.getUsd(), tmp), 0.17));
		ecsCheckStockDao.saveOrUpdate(a);
		return a;
	}

	/**
	 * 短溢分析
	 * 
	 * @param section
	 * @return
	 */
	public List<ECSAnalyse> ecsAnalyse(ECSSection section) {

		// 先删除之前的ECSSection统计数据
		ecsCheckStockDao
				.deleteByECSSection(section, ECSAnalyse.class.getName());
		Map<Integer, ECSAnalyse> map = new HashMap<Integer, ECSAnalyse>();
		/*
		 * 统计账册数据
		 */
		List<ECSContractAnalyse> contractAnalyses = ecsCheckStockDao
				.findByECSSection(section, ECSContractAnalyse.class.getName());
		ECSAnalyse analyse = null;
		ECSContractAnalyse ca = null;
		Integer seqNum = null;
		for (int i = 0; i < contractAnalyses.size(); i++) {
			ca = contractAnalyses.get(i);
			seqNum = ca.getSeqNum();
			analyse = map.get(seqNum);

			if (analyse == null) {
				analyse = new ECSAnalyse();

				analyse.init();
				analyse.setSection(section);
				analyse.setSeqNum(seqNum);
				analyse.setHsName(ca.getCommName());
				analyse.setHsSpec(ca.getCommSpec());
				analyse.setHsUnit(ca.getUnit());

				map.put(seqNum, analyse);
			}

			// 账面结余(A)
			analyse.setEmsBalance(analyse.getEmsBalance()
					+ ca.getResultAmount());
			// 帐面结余:进口数量、成品耗用
			analyse.setImportAmount(analyse.getImportAmount()
					+ ca.getImportAmount());
			analyse.setExgWasteAmount(analyse.getExgWasteAmount()
					+ ca.getWasteAmount());
			// 差异数 = B – A + C
			analyse.setDiffAmount(analyse.getStockBalance()
					- analyse.getEmsBalance() + analyse.getTransferBalance());
		}

		/*
		 * 统计盘点库存数据
		 */
		List<ECSStockAnalyse> stockAnalyses = ecsCheckStockDao
				.findByECSSection(section, ECSStockAnalyse.class.getName());
		ECSStockAnalyse sa = null;
		for (int i = 0; i < stockAnalyses.size(); i++) {
			sa = stockAnalyses.get(i);
			seqNum = sa.getSeqNum();
			analyse = map.get(seqNum);

			if (analyse == null) {
				analyse = new ECSAnalyse();

				analyse.init();
				analyse.setSection(section);
				analyse.setSeqNum(seqNum);
				analyse.setHsName(sa.getHsName());
				analyse.setHsSpec(sa.getHsSpec());
				analyse.setHsUnit(sa.getHsUnit());

				map.put(seqNum, analyse);
			}

			// 实物库存(B)
			analyse.setStockBalance(analyse.getStockBalance()
					+ sa.getStockTotalAmount());

			/************************************************************************
			 * 实物库存:料件库存,在产品库存,成品库存,外发加工库存, 厂外存放库存,
			 * 内购库存,料件在途库存,成品在途库存,残次品库存,半成品（已入库）】库存, 外发库存,在制品库存
			 *************************************************************************/
			analyse.setStockAmountImg(analyse.getStockAmountImg()
					+ sa.getStockAmountImg());
			analyse.setStockAmountProcessImg(analyse.getStockAmountProcessImg()
					+ sa.getStockAmountProcessImg());
			analyse.setStockAmountExg(analyse.getStockAmountExg()
					+ sa.getStockAmountExg());
			analyse.setStockAmountOutSendExg(analyse.getStockAmountOutSendExg()
					+ sa.getStockAmountOutSendExg());
			analyse.setStockAmountOutFactoryImg(analyse
					.getStockAmountOutFactoryImg()
					+ sa.getStockAmountOutFactoryImg());
			analyse.setStockAmountBuyImg(analyse.getStockAmountBuyImg()
					+ sa.getStockAmountBuyImg());
			analyse.setStockAmountTravelingImg(analyse
					.getStockAmountTravelingImg()
					+ sa.getStockAmountTravelingImg());
			analyse.setStockAmountTravelingExg(analyse
					.getStockAmountTravelingExg()
					+ sa.getStockAmountTravelingExg());
			analyse.setStockAmountBadImg(analyse.getStockAmountBadImg()
					+ sa.getStockAmountBadImg());
			analyse.setStockAmountSemiExgHStore(analyse
					.getStockAmountSemiExgHStore()
					+ sa.getStockAmountSemiExgHStore());
			analyse.setStockAmountFinishingExg(analyse
					.getStockAmountFinishingExg()
					+ sa.getStockAmountFinishingExg());
			analyse.setStockAmountOutSendExgPt(analyse
					.getStockAmountOutSendExgPt()
					+ sa.getStockAmountOutSendExgPt());

			// 差异数 = B – A + C
			analyse.setDiffAmount(analyse.getStockBalance()
					- analyse.getEmsBalance() + analyse.getTransferBalance());
		}
		/*
		 * 统计结转数据
		 */
		List<ECSTransferAnalyse> transferAnalyses = ecsCheckStockDao
				.findByECSSection(section, ECSTransferAnalyse.class.getName());
		ECSTransferAnalyse ta = null;
		for (int i = 0; i < transferAnalyses.size(); i++) {
			ta = transferAnalyses.get(i);
			seqNum = ta.getSeqNum();
			analyse = map.get(seqNum);

			if (analyse == null) {
				analyse = new ECSAnalyse();

				analyse.init();
				analyse.setSection(section);
				analyse.setSeqNum(seqNum);
				analyse.setHsName(ta.getHsName());
				analyse.setHsSpec(ta.getHsSpec());
				analyse.setHsUnit(ta.getHsUnit());

				map.put(seqNum, analyse);
			}

			// 结转差额(B)
			analyse.setTransferBalance(analyse.getTransferBalance()
					+ ta.getDiffAmount());
			// 结转差额(已送货未转厂数 ,已转厂未送货数,已收货未转厂数,已转厂未收货数)
			analyse.setUnTransHadSendNum(analyse.getUnTransHadSendNum()
					+ ta.getUnTransHadSendNum());
			analyse.setUnSendHadTransNum(analyse.getUnSendHadTransNum()
					+ ta.getUnSendHadTransNum());
			analyse.setUnTransHadReceiveNum(analyse.getUnTransHadReceiveNum()
					+ ta.getUnTransHadReceiveNum());
			analyse.setUnReceiveHadTransNum(analyse.getUnReceiveHadTransNum()
					+ ta.getUnReceiveHadTransNum());
			// 差异数 = B – A + C
			analyse.setDiffAmount(analyse.getStockBalance()
					- analyse.getEmsBalance() + analyse.getTransferBalance());
		}

		// 查询自用商品编码
		List<EmsHeadH2kImg> imgList = ecsCheckStockDao
				.findEmsHeadH2kImg(section.getCancelOwnerHead().getEmsNo());
		Map<Integer, Double> rateMap = new HashMap<Integer, Double>();
		EmsHeadH2kImg img = null;
		for (int i = 0; i < imgList.size(); i++) {
			img = imgList.get(i);
			rateMap.put(
					img.getSeqNum(),
					(img.getComplex() == null || img.getComplex().getLowrate() == null) ? 0.0
							: Double.parseDouble(img.getComplex().getLowrate()));
		}

		// 获取单价
		Map<Integer, Double> priceMap = getAnalysePriceMap(section);

		// 关联价格、计算关税和增值税
		List<ECSAnalyse> analyses = new ArrayList<ECSAnalyse>(map.values());
		for (int i = 0; i < analyses.size(); i++) {
			analyse = analyses.get(i);
			seqNum = analyse.getSeqNum();
			analyse.setPrice(CommonUtils.getDoubleExceptNull(priceMap
					.get(seqNum)));

			// 关税 =差异数*单价*该备案序号最新报关单商品编码对应的最优税率
			analyse.setUsd(CaleUtil.multiply(
					CaleUtil.abs(analyse.getDiffAmount()) * analyse.getPrice(),
					rateMap.get(seqNum), 5));

			// 增值税 = (差异数*单价+关税)*17%。
			analyse.setUsdAdd(CaleUtil.multiply(
					(CaleUtil.abs(analyse.getDiffAmount()) * analyse.getPrice() + analyse
							.getUsd()), 0.17, 5));
		}

		Collections.sort(analyses, new Comparator<ECSAnalyse>() {
			public int compare(ECSAnalyse o1, ECSAnalyse o2) {
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});

		ecsCheckStockDao.batchSaveECSEnity(analyses);

		return analyses;
	}

	/**
	 * 获取单价
	 * 
	 * @param section
	 * @return
	 */
	private Map<Integer, Double> getAnalysePriceMap(ECSSection section) {
		// 查询核销时间内的最新进口料件报关单单价（排除退厂返工）
		Map<Integer, Double> priceMap = new HashMap<Integer, Double>();
		if (section.getPriceFromType() == 3) { // 来源报关单单价
			List<Object[]> priceList = ecsCheckStockDao
					.findPriceFromCustomsDeclaration(section);
			Object[] objs = null;
			for (int i = 0; i < priceList.size(); i++) {
				objs = priceList.get(i);
				priceMap.put((Integer) objs[0], (Double) objs[1]);
			}
		} else {
			CancelOwnerHead cancelHead = null;
			if (section.getPriceFromType() == 2) { // 来源上一期自用核销料件平均单价
				cancelHead = ecsCheckStockDao.getLastCanceOwnerlHead(section
						.getCancelOwnerHead());
			} else if (section.getPriceFromType() == 1) { // 获取本周自用核销料件平均单价
				cancelHead = section.getCancelOwnerHead();
			}
			if (cancelHead != null) {
				List<Object[]> priceList = ecsCheckStockDao
						.getCancelOwnerHeadAvgPrice(cancelHead);
				for (Object[] objs : priceList) {
					priceMap.put((Integer) objs[0], (Double) objs[1]);
				}
			}
		}
		return priceMap;
	}

	/**
	 * @param csCheckStockDao
	 *            the csCheckStockDao to set
	 */
	public void setEcsCheckStockDao(ECSCheckStockDao ecsCheckStockDao) {
		this.ecsCheckStockDao = ecsCheckStockDao;
	}

	/******************************************* 盘点核查批次开始 ************************************/

	/**
	 * 根据自用核销表头查询账册盘点核查批次
	 * 
	 * @param request
	 * @param head
	 *            自用核销表头
	 * @return
	 */
	public List<ECSSection> findEcsSectionByCancelOwnerHead(CancelOwnerHead head) {
		return ecsCheckStockDao.findEcsSection(head);
	}
	
	/**
	 * 查询流程
	 * @param request
	 * @return
	 */
	public List<Integer> findPrcess(ECSSection section){
		List<Integer> prcess = new ArrayList<Integer>();
		
		//3料件明细表
		List list = ecsCheckStockDao.findTableSize(section,ECSDeclarationCommInfoImg.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(0);
		}
		//3成品明细表
		list = ecsCheckStockDao.findTableSize(section,ECSDeclarationCommInfoExg.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(1);
		}
		//2料件情况统计表
		list = ecsCheckStockDao.findTableSize(section,ECSCustomsCountImg.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(2);
		}
		//2成品情况统计表
		list = ecsCheckStockDao.findTableSize(section,ECSCustomsCountExgResolve.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(3);
		}
		//1帐册分析
		list = ecsCheckStockDao.findTableSize(section,ECSContractAnalyse.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(4);
		}
		//2料件库存
		list = ecsCheckStockDao.findTableSize(section,ECSStockImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(5);
		}
		//2在产品库存
		list = ecsCheckStockDao.findTableSize(section,ECSStockProcessImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(6);
		}
		//2成品库存
		list = ecsCheckStockDao.findTableSize(section,ECSStockExgResolve.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(7);
		}
		//2厂外库存
		list = ecsCheckStockDao.findTableSize(section,ECSStockOutFactoryImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(8);
		}
		//2内购库存
		list = ecsCheckStockDao.findTableSize(section,ECSStockBuyImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(9);
		}
		//2在途料件
		list = ecsCheckStockDao.findTableSize(section,ECSStockTravelingImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(10);
		}
		//2在途成品
		list = ecsCheckStockDao.findTableSize(section,ECSStockTravelingExgResolve.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(11);
		}
		
		boolean b = false;
		//外发库存原材料
		list = ecsCheckStockDao.findTableSize(section,ECSStockOutSendImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(13);
		}
		//外发库存半成品(料号级)
		list = ecsCheckStockDao.findTableSize(section,ECSStockOutSendSemiExgPtResolve.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(14);
		}
		//外发库存成品(料号级)
		list = ecsCheckStockDao.findTableSize(section,ECSStockOutSendExgPtResolve.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(15);
		}
		//外发库存汇总
		list = ecsCheckStockDao.findTableSize(section,ECSStockOutSendAnalyse.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(16);
		}
		if(b){
			prcess.add(12);
		}
		
		//2半成品库存（已入库）
		list = ecsCheckStockDao.findTableSize(section,ECSStockSemiExgPtHadStoreResolve.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(17);
		}
		
		
		b=false;
		//在制品库存原材料
		list = ecsCheckStockDao.findTableSize(section,ECSFinishingImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(19);
		}
		
		//在制品库存成品(料号级)
		list = ecsCheckStockDao.findTableSize(section,ECSFinishingExgResolve.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(20);
		}
		
		//在制品库存汇总
		list = ecsCheckStockDao.findTableSize(section,ECSFinishingStockAnalyse.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(21);
		}
		if(b){
			prcess.add(18);
		}
		
		b=false;
		//残次品库存原材料
		list = ecsCheckStockDao.findTableSize(section,ECSBadImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(23);
		}		
		
		//残次品库存半成品(料号级)
		list = ecsCheckStockDao.findTableSize(section,ECSSemiFinishedExgResolve.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(24);
		}		
		
		
		//残次品库存成品(料号级)
		list = ecsCheckStockDao.findTableSize(section,ECSFinishedExgResolve.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(25);
		}		
		
		//残次品库存汇总
		list = ecsCheckStockDao.findTableSize(section,ECSBadStockResolve.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(26);
		}		
		
		if(b){
			prcess.add(22);
		}
		
		//盘点总库存
		list = ecsCheckStockDao.findTableSize(section,ECSStockAnalyse.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(27);
		}
		
		//料件结转情况表
		list = ecsCheckStockDao.findTableSize(section,ECSTransferDiffImg.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(28);
		}
		
		//成品结转情况表
		list = ecsCheckStockDao.findTableSize(section,ECSTransferDiffExgResolve.class.getSimpleName(),true);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(29);
		}
		
		//结转汇总情况表
		list = ecsCheckStockDao.findTableSize(section,ECSTransferAnalyse.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			b=true;
			prcess.add(30);
		}
		//短溢分析
		list = ecsCheckStockDao.findTableSize(section,ECSAnalyse.class.getSimpleName(),null);
		if(list.size()>0&&Integer.valueOf(list.get(0).toString())>0){
			prcess.add(31);
		}
		return prcess;
	}
	/**
	 * 是否存在section批次下的盘点核查数据
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public boolean isExistEcsCheckDataBySection(ECSSection section) {
		return ecsCheckStockDao.isExistByECSSection(section,
				ECSCustomsCountImg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSCustomsCountExg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSStockBuyImg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSStockExg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSStockImg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSStockOutFactoryImg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSStockOutSendExg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSStockProcessImg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSTransferDiffExg.class.getSimpleName())
				|| ecsCheckStockDao.isExistByECSSection(section,
						ECSTransferDiffImg.class.getSimpleName());
	}

	/**
	 * 保存账册盘点核查批次
	 * 
	 * @param request
	 * @param section
	 * @return
	 */
	public ECSSection saveEcsSection(ECSSection section) {
		try {
			lock.lock();
			if (StringUtils.isBlank(section.getId())) {
				int verifiNum = ecsCheckStockDao.getMaxVerificationNo(section
						.getCancelOwnerHead());
				section.setVerificationNo(verifiNum + 1);
			}
			ecsCheckStockDao.saveOrUpdate(section);
			return section;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 删除批次数据以及相关的统计数据
	 * 
	 * @param section
	 */
	public void deleteECSSection(ECSSection section) {
		Class<?> entitys[] = {
				ECSAnalyse.class, // 短溢
				// 结转
				ECSTransferAnalyse.class,
				ECSTransferDiffImg.class,
				ECSTransferDiffExgResolve.class,
				ECSTransferDiffExg.class,
				// 工厂
				ECSStockAnalyse.class, ECSStockTravelingImg.class,
				ECSStockBuyImg.class, ECSStockOutFactoryImg.class,
				ECSStockProcessImg.class, ECSStockImg.class,
				ECSStockOutSendSemiExgPtResolve.class,
				ECSStockOutSendExg.class, ECSStockExgResolve.class,
				ECSStockExg.class,
				// 账册数据分析
				ECSContractAnalyse.class, ECSCustomsCountImg.class,
				ECSCustomsCountExgResolve.class, ECSCustomsCountExg.class };

		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在删除数据,请稍等...");
			info.setTotalNum(entitys.length + 1);
			info.setCurrentNum(0);
		}

		for (int i = 0; i < entitys.length; i++) {
			ecsCheckStockDao.deleteByECSSection(section,
					entitys[i].getSimpleName());
			if (info != null)
				info.setCurrentNum(i + 1);
		}
		ecsCheckStockDao.delete(section);
		if (info != null)
			info.setCurrentNum(info.getTotalNum());
		deleteAttachmentManagement(section);
	}

	/**
	 * 删除改批次在“0核查文档管理”中的附件
	 * 
	 * @param section
	 * @return
	 */
	public void deleteAttachmentManagement(ECSSection section) {
		deleteAttachment(section);
	}

	/**
	 * 查询 所有成品的最大bom版本。
	 * 
	 * @return
	 */
	public List<Object[]> findMaxVersion() {
		return ecsCheckStockDao.findMaxVersion();
	}

	/**
	 * 查询 所有成品的bom版本。
	 * 
	 * @return
	 */
	public List<Object[]> findAllVersion() {
		return ecsCheckStockDao.findAllVersion();
	}

	/******************************************* 盘点核查批次结束 ************************************/

	/**
	 * 查询电子帐册料件
	 * 
	 * @param section
	 *            核查批次
	 * @return
	 */
	public List<EmsHeadH2kImg> findEmsHeadH2kImg(ECSSection section) {
		return ecsCheckStockDao.findEmsHeadH2kImg(section.getCancelOwnerHead()
				.getEmsNo());
	}

	/**
	 * 查询电子帐册成品
	 * 
	 * @param section
	 *            核查批次
	 * @return
	 */
	public List<EmsHeadH2kExg> findEmsHeadH2kExg(ECSSection section) {
		return ecsCheckStockDao.findEmsHeadH2kExg(section.getCancelOwnerHead()
				.getEmsNo());
	}

	/**
	 * 查询 所有电子账册成品的bom版本。
	 * 
	 * @return
	 */
	public List<Object[]> findAllEmsVersion() {
		return ecsCheckStockDao.findAllEmsVersion();
	}

	/**
	 * 查询 所有电子账册成品的最大bom版本。
	 * 
	 * @return
	 */
	public List<Object[]> findMaxEmsVersion() {
		return ecsCheckStockDao.findMaxEmsVersion();
	}

	/**
	 * 上传盘点核资料附件
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

	/**
	 * 上传盘点核资料附件
	 * 
	 * @param request
	 *            批次
	 * @param file
	 *            附件
	 * @param fileType
	 *            附件类型
	 * @return
	 */
	public void upLoadAttachment(ECSAttachmentManagement ecsattachmentManagement) {
		ecsCheckStockDao.saveOrUpdate(ecsattachmentManagement);
	}

	/**
	 * 查询核资料附件
	 * 
	 * @param request
	 * @param fileType
	 * @param fileId
	 * @return
	 */
	public List findECSAttachmentManagement(ECSSection section) {
		List<ECSAttachmentManagement> parentList = ecsCheckStockDao
				.findECSAttachmentManagement(section, true);
		List<ECSAttachmentManagement> chileList = ecsCheckStockDao
				.findECSAttachmentManagement(section, false);
		Map<String, ECSAttachmentManagement> map = new HashMap<String, ECSAttachmentManagement>();
		for (int i = 0; i < parentList.size(); i++) {
			ECSAttachmentManagement parentAttachment = parentList.get(i);
			parentAttachment.setIsModify(false);
			map.put(parentAttachment.getId(), parentAttachment);
		}

		for (int i = 0; i < chileList.size(); i++) {
			ECSAttachmentManagement childAttachment = chileList.get(i);
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
	 * 下载盘点核资料附件
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
	 * 删除盘点核资料附件
	 */
	public void deleteAttachment(String fileName, String fileId) {
		if (fileName != null) {
			String[] fileNames = fileName.split("\\.");
			fileName = fileNames[0] + fileId + ".zip";
			File file = new File(getAttachmentFolder() + File.separator
					+ fileName);
			file.delete();
		}

		if (fileId != null) {
			ecsCheckStockDao.deleteECSAttachmentManagement(fileId);
		}
	}

	/**
	 * 删除盘点核资料附件
	 */
	public void deleteAttachment(ECSSection section) {
		List<ECSAttachmentManagement> parentList = ecsCheckStockDao
				.findECSAttachmentManagement(section, true);
		List<ECSAttachmentManagement> chileList = ecsCheckStockDao
				.findECSAttachmentManagement(section, false);
		for (int i = 0; i < parentList.size(); i++) {
			ECSAttachmentManagement parenAttachment = parentList.get(i);
			ecsCheckStockDao.deleteECSAttachmentManagement(parenAttachment.getId());
		}
		
		for (int i = 0; i < chileList.size(); i++) {
			ECSAttachmentManagement chileAttachment = chileList.get(i);
			String fileName = chileAttachment.getAttachmentName();
			deleteAttachment(fileName,chileAttachment.getId());
		}
	}
	
	/**
	 * 查询所有盘点核查批次
	 * 
	 * @param request
	 * @param isExist
	 *            是否在附件管理中，已经存在
	 * @return
	 */
	public List<ECSSection> findAttachmentSection(Boolean isExist) {
		return ecsCheckStockDao.findAttachmentSection(isExist);
	}

	/**
	 * 保存核查批次模板
	 * 
	 * @param request
	 * @param section
	 */
	public void saveECSAttachmentTemplate(ECSSection section) {
		List<ECSAttachmentManagement> list = iterateWholeXML(section);
		for (int i = 0; i < list.size(); i++) {
			ECSAttachmentManagement attachment = (ECSAttachmentManagement) list.get(i);
			attachment.setIsTemplate(true);
		}
		ecsCheckStockDao.batchSaveECSEnity(list);
		for (int j = 0; j < list.size(); j++) {
			ECSAttachmentManagement attachment = (ECSAttachmentManagement) list
					.get(j);
			List<ECSAttachmentManagement> listChild = attachment.getChildren();
			for (int i = 0; i < listChild.size(); i++) {
				ECSAttachmentManagement attachmentChild = listChild.get(i);
				attachmentChild.setParentId(attachment.getId());
				attachmentChild.setIsTemplate(true);
			}
			ecsCheckStockDao.batchSaveECSEnity(listChild);
		}
	}

	/**
	 * 保存附件信息
	 * 
	 * @param section
	 * @param attachment
	 */
	public void saveECSAttachment(ECSAttachmentManagement attachment) {
		ecsCheckStockDao.saveOrUpdate(attachment);
	}

	/**
	 * 保存附件信息
	 * @param request
	 * @param section
	 * @param attachment
	 */
	public void saveECSAttachment(List<ECSAttachmentManagement> attachment){
		ecsCheckStockDao.batchSaveOrUpdateDirect(attachment);
	}
	
	
	public List<ECSAttachmentManagement> iterateWholeXML(ECSSection section) {
		try {
			List<ECSAttachmentManagement> list = new ArrayList<ECSAttachmentManagement>();
			InputStream input = ECSCheckStockLogic.class
					.getResourceAsStream("/com/bestway/bcus/checkstock/entity/ECSAttachmentTemplate.xml");
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(input);
			Element root = document.getRootElement();
			int j = 0;
			Iterator superParentIterator = root.elementIterator();
			while (superParentIterator.hasNext()) {
				Element superParentEle = (Element) superParentIterator.next();

				ECSAttachmentManagement emt = nodeToEntity(superParentEle,
						section);

				List subEleLs = superParentEle.elements();
				List<ECSAttachmentManagement> emtChild = new ArrayList<ECSAttachmentManagement>(
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

	private ECSAttachmentManagement nodeToEntity(Element e, ECSSection section) {
		ECSAttachmentManagement emt = new ECSAttachmentManagement();
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
		emt.setEcssection(section);
		return emt;
	}

	private String getAttachmentFolder() {
		String fi = System.getProperty("user.dir") + File.separator+"ECS"+File.separator
				+ "AttachmentFile";
		File file = new File(fi);
		if (!file.exists()) {
			file.mkdirs();
		}
		return fi;
	}

	ServletContext context = null;

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}
