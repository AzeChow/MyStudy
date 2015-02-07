package com.bestway.bcus.checkstock.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.checkstock.dao.ECSCheckStockDao;
import com.bestway.bcus.checkstock.dao.ECSTransferAnalyseDao;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSTransferAnalyse;
import com.bestway.bcus.checkstock.entity.ECSTransferBaseConvert;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffExg;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffExgResolve;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffImg;
import com.bestway.bcus.checkstock.entity.temp.BomTemp;
import com.bestway.bcus.checkstock.entity.temp.MergeTemp;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.fpt.entity.FptBillItem;

public class ECSTransferAnalyseLogic {
	private ECSTransferAnalyseDao ecsTransferAnalyseDao;
	private ECSCheckStockDao ecsCheckStockDao;

	// ---------------------------折算方法 开始-------------------------//
	/**
	 * 把当前批次 section 下 的所有 clazz 数据进行转换报关数据
	 * 
	 * @param section
	 * @param clazz
	 *            实体名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends ECSTransferBaseConvert> List<T> convertECSTransferBaseConverts(
			ECSSection section, String clazz, boolean isImg) {
		if (clazz == null) {
			throw new RuntimeException("clazz = null");
		}

		if (section == null) {
			throw new RuntimeException("section = null");
		}

		long b = System.currentTimeMillis();

		List<T> list = ecsCheckStockDao.findByECSSection(section, clazz);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);

		b = e;
		List<MergeTemp> mergeTemps = null;
		if (isImg) {
			mergeTemps = ecsCheckStockDao.findAllEdiMergeHsPartImg();
		} else {
			mergeTemps = ecsCheckStockDao.findAllEdiMergeHsPartExg();
		}

		Map<String, MergeTemp> mergeTempMap = new HashMap<String, MergeTemp>();
		mergeTempMap = CommonUtils.listToMap(mergeTemps,
				new GetKeyValueImpl<MergeTemp>() {
					public String getKey(MergeTemp e) {
						return e.getPtNo();
					}
				});
		e = System.currentTimeMillis();
		System.out.println("查询对应关系时间：" + (e - b) / 1000.0);

		b = e;
		T img = null;
		MergeTemp merge = null;
		StringBuilder err = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			img = list.get(i);
			merge = mergeTempMap.get(img.getPtNo());
			if (merge == null) {
				err.append(err.length() == 0 ? img.getPtNo() : ("," + img
						.getPtNo()));
				continue;
			}
			// 根据对应关系把工厂料件转化为报关料件
			convertECSTransferBaseConvert(img, merge);
		}
		e = System.currentTimeMillis();
		System.out.println("转换时间：" + (e - b) / 1000.0);

		if (err.length() > 0) {
			throw new RuntimeException(err.toString()
					+ "等料件料号不存在归并关系，或者归并关系内容不完整！");
		}

		b = e;
		ecsCheckStockDao.batchUpdateECSEnity(list);
		e = System.currentTimeMillis();
		System.out.println("保存数据时间：" + (e - b) / 1000.0);

		return list;
	}

	/**
	 * 根据对应关系把工厂料件转化为报关料件
	 * 
	 * @param convert
	 * @param merge
	 */
	private void convertECSTransferBaseConvert(ECSTransferBaseConvert convert, MergeTemp merge) {
		convert.setSeqNum(merge.getSeqNum());
		convert.setHsName(merge.getHsName());
		convert.setHsSpec(merge.getHsSpec());
		convert.setHsUnit(merge.getHsUnit());
		convert.setUnitConvert(merge.getUnitConvert());
		convert.setHsUnSendferNum(CommonUtils.getDoubleExceptNull(convert
				.getPtUnSendferNum())
				* CommonUtils.getDoubleExceptNull(merge.getUnitConvert()));
		convert.setHsUnTransferNum(CommonUtils.getDoubleExceptNull(convert
				.getPtUnTransferNum())
				* CommonUtils.getDoubleExceptNull(merge.getUnitConvert()));
	}

	/**
	 * 成品转料件
	 * 
	 * @param section
	 * @param exgClass
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends ECSTransferDiffExgResolve> List<T> resolveECSTransferBaseConverts(
			ECSSection section, String exgClass, ECSTransferDiffExgResolve model) {
		if (model == null) {
			throw new RuntimeException("必须确定料件类型！");
		}

		// 删除之前的折算料件数据
		ecsCheckStockDao.deleteByECSSection(section, model.getClass().getName());

		long b = System.currentTimeMillis();
		List<ECSTransferBaseConvert> list = ecsCheckStockDao.findByECSSection(section,
				exgClass);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);

		b = e;
//		Map<Integer, Integer> seqNumVersionMap = new HashMap<Integer, Integer>();
		ECSTransferBaseConvert convert = null;
//		for (int i = 0; i < list.size(); i++) {
//			convert = list.get(i);
//			seqNumVersionMap.put(convert.getSeqNum(), convert.getVersion());
//		}
		List<BomTemp> bomList = ecsCheckStockDao
				.findEmsHeadH2kBomBySection(section, exgClass);
		e = System.currentTimeMillis();
		System.out.println("查询bom时间：" + (e - b) / 1000.0);

		b = e;
		Map<String, List<BomTemp>> bomMap = new HashMap<String, List<BomTemp>>();
		List<BomTemp> mbdList = null;
		BomTemp bom = null;
		String key = null;
		for (int i = 0; i < bomList.size(); i++) {
			bom = bomList.get(i);
			key = bom.getExgSeqNum() + "/" +  bom.getVersion();
			mbdList = bomMap.get(key);
			if (mbdList == null) {
				mbdList = new ArrayList<BomTemp>();

				bomMap.put(key, mbdList);
			}

			mbdList.add(bom);
		}
		e = System.currentTimeMillis();
		System.out.println("准备bom时间：" + (e - b) / 1000.0);

		b = e;
		StringBuilder err = new StringBuilder();
		List<T> baseResolves = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			convert = list.get(i);
			convert.setSection(section);
			mbdList = bomMap.get(convert.getSeqNum() + "/" + convert.getVersion());
			if(mbdList == null) {
				err.append(convert.getSeqNum() + ",");
				continue;
			}
			
			baseResolves.addAll(resolveECSTransferBaseConvert(section, convert,mbdList, model));
		}
		e = System.currentTimeMillis();
		System.out.println("折料时间：" + (e - b) / 1000.0);

		if(err.length() > 0) {
			throw new RuntimeException(err.toString());
		}
		
		b = e;
		ecsCheckStockDao.batchSaveECSEnity(baseResolves);
		e = System.currentTimeMillis();
		System.out.println("保存时间：" + (e - b) / 1000.0);

		return baseResolves;
	}

	@SuppressWarnings("rawtypes")
	private List resolveECSTransferBaseConvert(ECSSection section,
			ECSTransferBaseConvert convert, List<BomTemp> mbdList,
			ECSTransferDiffExgResolve model) {
		List<ECSTransferDiffExgResolve> baseResolves = new ArrayList<ECSTransferDiffExgResolve>();
		BomTemp bom = null;
		ECSTransferDiffExgResolve resolve = null;
		for (int i = 0; i < mbdList.size(); i++) {
			bom = mbdList.get(i);
			resolve = (ECSTransferDiffExgResolve) model.clone();
			resolve.setSection(section);
			resolve.setBaseExg(convert);
			resolve.setUnitWaste(CommonUtils.getDoubleExceptNull(bom.getUnitWear()));
			resolve.setUnitUsed(CommonUtils.getDoubleExceptNull(bom.getUnitWear()) / (1 - 0.01 * CommonUtils.getDoubleExceptNull(bom.getWear())));
			resolve.setWaste(0.01 * CommonUtils.getDoubleExceptNull(bom.getWear()));
			resolve.setSeqNum(bom.getSeqNum());
			resolve.setHsName(bom.getName());
			resolve.setHsSpec(bom.getSpec());
			resolve.setHsUnit(bom.getUnitName());
			resolve.setHsUnSendferNum(CommonUtils.getDoubleExceptNull(convert.getHsUnSendferNum()) * CommonUtils.getDoubleExceptNull(resolve.getUnitUsed()));
			resolve.setHsUnTransferNum(CommonUtils.getDoubleExceptNull(convert.getHsUnTransferNum()) * CommonUtils.getDoubleExceptNull(resolve.getUnitUsed()));

			baseResolves.add(resolve);
		}

		return baseResolves;
	}

	/**
	 * 转换 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param section
	 */
	public List<ECSTransferDiffExg> convertECSTransferDiffExgs(ECSSection section) {
		return convertECSTransferBaseConverts(section, ECSTransferDiffExg.class.getName(),
				false);
	}

	/**
	 * 转换 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param section
	 */
	public List<ECSTransferDiffImg> convertECSTransferDiffImgs(ECSSection section) {
		return convertECSTransferBaseConverts(section, ECSTransferDiffImg.class.getName(),
				true);
	}

	/**
	 * 分解 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param section
	 */
	public List<ECSTransferDiffExgResolve> resolveECSTransferDiffExgResolves(
			ECSSection section) {
		return resolveECSTransferBaseConverts(section, ECSTransferDiffExg.class.getName(),
				new ECSTransferDiffExgResolve());
	}
	// ---------------------------折算方法 结束-------------------------//


	// ---------------------------保存方法 开始-------------------------//
	/**
	 * 批量保存【结转成品差额】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSTransferDiffExgs(ECSSection section, List<ECSTransferDiffExg> list) {
		ECSTransferDiffExg exg = null;
		for (int i = 0; i < list.size(); i++) {
			exg = list.get(i);
			exg.setSection(section);
		}
		ecsCheckStockDao.batchSaveECSEnity(list);
	}

	/**
	 * 批量保存【结转料件差额】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSTransferDiffImgs(ECSSection section, List<ECSTransferDiffImg> list) {
		ECSTransferDiffImg img = null;
		for (int i = 0; i < list.size(); i++) {
			img = list.get(i);
			img.setSection(section);
		}
		ecsCheckStockDao.batchSaveECSEnity(list);
	}
	// ---------------------------保存方法 结束-------------------------//

	// ---------------------------查询方法 开始-------------------------//	

	/**
	 * 查询 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param section
	 */
	public List<ECSTransferDiffExg> findECSTransferDiffExgs(ECSSection section,Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSTransferDiffExg.class.getName(),seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param section
	 */
	public List<ECSTransferDiffImg> findECSTransferDiffImgs(ECSSection section,Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSTransferDiffImg.class.getName(),seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【结转成品差额折料数据】。
	 * 
	 * @param section
	 */
	public List<ECSTransferDiffExgResolve> findECSTransferDiffExgResolves(ECSSection section,Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSTransferDiffExgResolve.class.getName(),seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【结转库存分析数据】。
	 * 
	 * @param section
	 */
	public List<ECSTransferAnalyse> findECSTransferAnalyses(ECSSection section,Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSTransferAnalyse.class.getName(),seqNum);
	}

	// ---------------------------查询方法 结束-------------------------//

	// ---------------------------删除方法 开始-------------------------//

	/**
	 * 删除 指定批次 section 下的【结转成品差额】。
	 * 
	 * @param section
	 */
	public void deleteECSTransferDiffExgs(ECSSection section) {
		// 先删除折料数据
		ecsCheckStockDao.deleteByECSSection(section,
				ECSTransferDiffExgResolve.class.getName());

		ecsCheckStockDao.deleteByECSSection(section,
				ECSTransferDiffExg.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【结转料件差额】。
	 * 
	 * @param section
	 */
	public void deleteECSTransferDiffImgs(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSTransferDiffImg.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【结转库存分析数据】。
	 * 
	 * @param section
	 */
	public void deleteECSTransferAnalyses(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSTransferAnalyse.class.getName());
	}

	// ---------------------------删除方法 结束-------------------------//

	/**
	 * 结转库存数据分析
	 * @param section
	 * @return
	 */
	public List<ECSTransferAnalyse> transferAnalyse(ECSSection section) {
		Map<Integer, ECSTransferAnalyse> map = new HashMap<Integer, ECSTransferAnalyse>();
		
		// 删除之前结转库存数据
		ecsCheckStockDao.deleteByECSSection(section, ECSTransferAnalyse.class.getName());
		
		
		/*
		 * 统计料件库存数据
		 */
		List<ECSTransferDiffImg> imgs = ecsCheckStockDao.findByECSSection(section, ECSTransferDiffImg.class.getName());
		ECSTransferDiffImg img = null;
		ECSTransferAnalyse analyse = null;
		Integer seqNum = null;
		for (int i = 0; i < imgs.size(); i++) {
			img = imgs.get(i);
			seqNum = img.getSeqNum();
			analyse = map.get(seqNum);
			if (analyse == null) {
				analyse = new ECSTransferAnalyse();
				analyse.init();
				analyse.setSection(section);
				analyse.setSeqNum(seqNum);
				analyse.setEmsNo(img.getEmsNo());
				analyse.setHsName(img.getHsName());
				analyse.setHsSpec(img.getHsSpec());
				analyse.setHsUnit(img.getHsUnit());

				map.put(seqNum, analyse);
			}

			// 已收货未转厂 = 料件结转差额【已收货未转厂】(C)
			analyse.setUnTransHadReceiveNum(analyse.getUnTransHadReceiveNum() + img.getHsUnTransferNum());
			
			// 已转厂未收货 = 料件结转差额【已转厂未收货】(D)
			analyse.setUnReceiveHadTransNum(analyse.getUnReceiveHadTransNum() + img.getHsUnSendferNum());
			
			// 差额 =  已送货未转厂(A) + 已转厂未收货 (D) – 已转厂未送货(B) – 已收货未转厂(C)
			analyse.setDiffAmount(analyse.getUnTransHadSendNum() + analyse.getUnReceiveHadTransNum() - analyse.getUnSendHadTransNum() - analyse.getUnTransHadReceiveNum());
		}
		
		
		/*
		 * 统计成品耗用数据
		 */
		List<ECSTransferDiffExgResolve> resolves = ecsCheckStockDao.findByECSSection(
				section, ECSTransferDiffExgResolve.class.getName());
		ECSTransferDiffExgResolve resolve = null;
		for (int i = 0; i < resolves.size(); i++) {
			resolve = resolves.get(i);
			seqNum = resolve.getSeqNum();
			analyse = map.get(seqNum);
			if (analyse == null) {
				analyse = new ECSTransferAnalyse();
				analyse.init();
				analyse.setSection(section);
				analyse.setSeqNum(seqNum);
				analyse.setEmsNo(resolve.getEmsNo());
				analyse.setHsName(resolve.getHsName());
				analyse.setHsSpec(resolve.getHsSpec());
				analyse.setHsUnit(resolve.getHsUnit());

				map.put(seqNum, analyse);
			}

			// 已送货未转厂 = 成品结转差额【已送货未结转】(A)
			analyse.setUnTransHadSendNum(analyse.getUnTransHadSendNum() + resolve.getHsUnTransferNum());
			
			// 已转厂未送货 = 成品结转差额【已结转为送货】(B)
			analyse.setUnSendHadTransNum(analyse.getUnSendHadTransNum() + resolve.getHsUnSendferNum());

			// 差额 =  已送货未转厂(A) + 已转厂未收货 (D) – 已转厂未送货(B) – 已收货未转厂(C)
			analyse.setDiffAmount(analyse.getUnTransHadSendNum() + analyse.getUnReceiveHadTransNum() - analyse.getUnSendHadTransNum() - analyse.getUnTransHadReceiveNum());
		}
		
		List<ECSTransferAnalyse> analyses = new ArrayList<ECSTransferAnalyse>(map.values());
		
		Collections.sort(analyses, new Comparator<ECSTransferAnalyse>() {
			public int compare(ECSTransferAnalyse o1, ECSTransferAnalyse o2) {
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});
		
		ecsCheckStockDao.batchSaveECSEnity(analyses);
		
		return analyses;
	}
	
	 /* 从深加工结转收货单获取报关编码级结转料件
	 * @param section
	 * @return
	 */
	public List<ECSTransferDiffImg> generateTransferDiffHsImgFormFpt(ECSSection section){
		Request request = CommonUtils.getRequest();		
		ProgressInfo info = null;
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
			info.setMethodName("正在删除本批次的历史数据,请稍等...");
			info.setTotalNum(5);
		}

		this.deleteECSTransferDiffImgs(section);
		if(info!= null){
			info.setMethodName("正在获取收货单据信息,请稍等...");
			info.setCurrentNum(1);
		}
		Map<String,ECSTransferDiffImg> resultMap = new HashMap<String, ECSTransferDiffImg>();
		String key = "";	//key 手册号+'/'+备案号
		ECSTransferDiffImg img = null;
		/*** 第一步处理收货信息***/
		List<FptBillItem> items = ecsTransferAnalyseDao.findInFptBillItem(section);
		for(FptBillItem item :items){
			key = item.getInEmsNo()+"/"+item.getTrGno();
			img = resultMap.get(key);
			if(img == null){
				img = new ECSTransferDiffImg();
				img.init();
				img.setSection(section);
				img.setHsName(item.getCommName());
				img.setHsSpec(item.getCommSpec());
				img.setHsUnit(item.getUnit() == null ? null : item.getUnit().getName());
				img.setEmsNo(item.getInEmsNo());
				img.setSeqNum(item.getTrGno());				
				img.setCompany(CommonUtils.getCompany());
				resultMap.put(key, img);
			}
			if(FptBusinessType.FPT_BILL.equals(item.getFptBillHead().getSysType())){				
				img.setHsBillNum(CaleUtil.add(img.getHsBillNum(),item.getQty()));
			}else if(FptBusinessType.FPT_BILL_BACK.equals(item.getFptBillHead().getSysType())){
				img.setHsBillNum(CaleUtil.subtract(img.getHsBillNum(),item.getQty()));
			}
		}
		if(info!= null){
			info.setMethodName("正在获取报关单据信息,请稍等...");
			info.setCurrentNum(2);
		}
		/***************第二步处理报关信息**************/
		List<CustomsDeclarationCommInfo> commInfos = ecsTransferAnalyseDao.findImpFptBcsCustomsDeclarationCommInfo(section);
		for(CustomsDeclarationCommInfo commInfo : commInfos){
			key = commInfo.getBaseCustomsDeclaration().getEmsHeadH2k() + "/" + commInfo.getCommSerialNo();
			img = resultMap.get(key);
			if(img == null){
				img = new ECSTransferDiffImg();
				img.init();
				img.setSection(section);
				img.setHsName(commInfo.getCommName());
				img.setHsSpec(commInfo.getCommSpec());
				img.setHsUnit(commInfo.getUnit() == null ? null:commInfo.getUnit().getName());
				img.setEmsNo(commInfo.getBaseCustomsDeclaration().getEmsHeadH2k());
				img.setSeqNum(commInfo.getCommSerialNo());				
				img.setCompany(CommonUtils.getCompany());
				resultMap.put(key, img);
			}
			img.setHsCustomsNum(CaleUtil.add(img.getHsCustomsNum(),commInfo.getCommAmount()));
		}
		/*********第三步：按照备案序号排序*******/
		List<ECSTransferDiffImg> result = new ArrayList<ECSTransferDiffImg>(resultMap.values());
		Collections.sort(result, new Comparator<ECSTransferDiffImg>() {
			public int compare(ECSTransferDiffImg o1, ECSTransferDiffImg o2) {
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});
		if(info!= null){
			info.setMethodName("正在结算差额数据,请稍等...");
			info.setCurrentNum(3);
		}
		/*********第四步：计算并设置差额数据*******/
		double d = 0;
		for(ECSTransferDiffImg hsImg : result){
			d = CaleUtil.subtract(hsImg.getHsBillNum(),hsImg.getHsCustomsNum());
			if(d > 0){
				hsImg.setHsUnTransferNum(d);
			}else{
				hsImg.setHsUnSendferNum(-d);
			}			
		}
		if(info!= null){
			info.setMethodName("正在保存结果,请稍等...");
			info.setCurrentNum(4);
		}
		/*********第五步：批量保存数据***********************/
		ecsTransferAnalyseDao.batchSaveOrUpdate(result);
		if(info!= null){
			info.setMethodName("已完成正返回结果,请稍等...");
			info.setCurrentNum(5);
		}
		return result;
	}
	/**
	 * 从深加工结转发货单获取报关编码级结转结转成品
	 * @param section
	 * @return
	 */
	public List<ECSTransferDiffExg> generateTransferDiffHsExgsFormFpt(ECSSection section){
		Request request = CommonUtils.getRequest();		
		ProgressInfo info = null;
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
			info.setMethodName("正在删除本批次的历史数据,请稍等...");
			info.setTotalNum(6);
		}
		this.deleteECSTransferDiffExgs(section);
		if(info!= null){
			info.setMethodName("正在获取发货单据信息,请稍等...");
			info.setCurrentNum(1);
		}
		/****获取最大版本号***/
		List<Object[]> maxVerLs = ecsCheckStockDao.findMaxEmsVersion();
		Map<Integer,Integer> maxVerMap = new HashMap<Integer, Integer>();
		for(Object[] o : maxVerLs){
			maxVerMap.put((Integer)o[0], (Integer)o[1]);
		}
		Map<String,ECSTransferDiffExg> resultMap = new HashMap<String, ECSTransferDiffExg>();
		String key = "";	//key 手册号+'/'+备案号
		
		ECSTransferDiffExg exg = null;		
		/*** 第一步处理收货信息***/
		List<FptBillItem> items = ecsTransferAnalyseDao.findOutFptBillItem(section);
		for(FptBillItem item :items){
			key = item.getFptBillHead().getOutEmsNo()+"/"+item.getTrGno();
			exg = resultMap.get(key);
			if(exg == null){
				exg = new ECSTransferDiffExg();
				exg.init();							
				exg.setSection(section);
				exg.setHsName(item.getCommName());				
				exg.setHsSpec(item.getCommSpec());
				exg.setHsUnit(item.getUnit() == null ? null:item.getUnit().getName());
				exg.setEmsNo(item.getInEmsNo());
				exg.setSeqNum(item.getTrGno());
				exg.setVersion(maxVerMap.get(exg.getSeqNum()));	//版本号
				exg.setCompany(CommonUtils.getCompany());
				resultMap.put(key, exg);
			}
			if(FptBusinessType.FPT_BILL.equals(item.getFptBillHead().getSysType())){				
				exg.setHsBillNum(CaleUtil.add(exg.getHsBillNum(),item.getQty()));
			}else if(FptBusinessType.FPT_BILL_BACK.equals(item.getFptBillHead().getSysType())){
				exg.setHsBillNum(CaleUtil.subtract(exg.getHsBillNum(),item.getQty()));
			}
		}
		if(info!= null){
			info.setMethodName("正在获取报关单据信息,请稍等...");
			info.setCurrentNum(2);
		}
		/***************第二步处理报关信息**************/
		List<CustomsDeclarationCommInfo> commInfos = ecsTransferAnalyseDao.findExpFptCustomsDeclarationCommInfo(section);
		for(CustomsDeclarationCommInfo bcdc : commInfos){
			key = bcdc.getBaseCustomsDeclaration().getEmsHeadH2k() + "/" + bcdc.getCommSerialNo();
			exg = resultMap.get(key);
			if(exg == null){
				exg = new ECSTransferDiffExg();
				exg.init();
				exg.setSection(section);
				exg.setHsName(bcdc.getCommName());
				exg.setHsSpec(bcdc.getCommSpec());
				exg.setHsUnit(bcdc.getUnit() == null ? null : bcdc.getUnit().getName());
				exg.setEmsNo(bcdc.getBaseCustomsDeclaration().getEmsHeadH2k());
				exg.setSeqNum(bcdc.getCommSerialNo());	
				exg.setVersion(maxVerMap.get(exg.getSeqNum()));	//版本号
				exg.setCompany(CommonUtils.getCompany());
				resultMap.put(key, exg);
			}
			exg.setHsCustomsNum(CaleUtil.add(exg.getHsCustomsNum(),bcdc.getCommAmount()));
		}
		/*********第三步：按照备案序号排序*******/
		List<ECSTransferDiffExg> result = new ArrayList<ECSTransferDiffExg>(resultMap.values());
		Collections.sort(result, new Comparator<ECSTransferDiffExg>() {
			public int compare(ECSTransferDiffExg o1, ECSTransferDiffExg o2) {
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});
		if(info!= null){
			info.setMethodName("正在结算差额数据,请稍等...");
			info.setCurrentNum(3);
		}
		/*********第四步：计算并设置差额数据*******/
		double d = 0;
		for(ECSTransferDiffExg hsexg : result){
			d = CaleUtil.subtract(hsexg.getHsBillNum(),hsexg.getHsCustomsNum());
			if(d > 0){
				hsexg.setHsUnTransferNum(d);
			}else{
				hsexg.setHsUnSendferNum(-d);
			}			
		}
		if(info!= null){
			info.setMethodName("正在保存结果,请稍等...");
			info.setCurrentNum(4);
		}
		/*********第五步：批量保存数据***********************/
		ecsTransferAnalyseDao.batchSaveOrUpdate(result);
		if(info!= null){
			info.setMethodName("从深加工结转获取数据完毕，正在进行折料,请稍等...");
			info.setCurrentNum(5);
		}
		resolveECSTransferBaseConverts(section, ECSTransferDiffExg.class.getName(),new ECSTransferDiffExgResolve());
		if(info!= null){
			info.setMethodName("正在返回结果数据,请稍等...");
			info.setCurrentNum(6);
		}
		return result;
	}	
	
	/**
	 * @param ecsTransferAnalyseDao
	 *            the ecsTransferAnalyseDao to set
	 */
	public void setEcsTransferAnalyseDao(
			ECSTransferAnalyseDao ecsTransferAnalyseDao) {
		this.ecsTransferAnalyseDao = ecsTransferAnalyseDao;
	}

	/**
	 * @param csCheckStockDao
	 *            the csCheckStockDao to set
	 */
	public void setEcsCheckStockDao(ECSCheckStockDao ecsCheckStockDao) {
		this.ecsCheckStockDao = ecsCheckStockDao;
	}
}
