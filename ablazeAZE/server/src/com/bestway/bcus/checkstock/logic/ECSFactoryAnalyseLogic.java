package com.bestway.bcus.checkstock.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.checkstock.dao.ECSCheckStockDao;
import com.bestway.bcus.checkstock.dao.ECSFactoryAnalyseDao;
import com.bestway.bcus.checkstock.entity.ECSBadImg;
import com.bestway.bcus.checkstock.entity.ECSBadStockResolve;
import com.bestway.bcus.checkstock.entity.ECSBaseConvert;
import com.bestway.bcus.checkstock.entity.ECSBaseResolve;
import com.bestway.bcus.checkstock.entity.ECSBaseStockExg;
import com.bestway.bcus.checkstock.entity.ECSBaseStockExgResolve;
import com.bestway.bcus.checkstock.entity.ECSBaseStockItem;
import com.bestway.bcus.checkstock.entity.ECSFinishedExg;
import com.bestway.bcus.checkstock.entity.ECSFinishedExgResolve;
import com.bestway.bcus.checkstock.entity.ECSFinishingExg;
import com.bestway.bcus.checkstock.entity.ECSFinishingExgResolve;
import com.bestway.bcus.checkstock.entity.ECSFinishingImg;
import com.bestway.bcus.checkstock.entity.ECSFinishingStockAnalyse;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSSemiFinishedExg;
import com.bestway.bcus.checkstock.entity.ECSSemiFinishedExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStagnateExg;
import com.bestway.bcus.checkstock.entity.ECSStagnateExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStagnateImg;
import com.bestway.bcus.checkstock.entity.ECSStagnateStockAnalyse;
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
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendSemiExgPt;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendSemiExgPtResolve;
import com.bestway.bcus.checkstock.entity.ECSStockProcessImg;
import com.bestway.bcus.checkstock.entity.ECSStockSemiExgPtHadStore;
import com.bestway.bcus.checkstock.entity.ECSStockSemiExgPtHadStoreResolve;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExg;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingImg;
import com.bestway.bcus.checkstock.entity.temp.BadBomTemp;
import com.bestway.bcus.checkstock.entity.temp.BomTemp;
import com.bestway.bcus.checkstock.entity.temp.MergeTemp;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.jptds.common.AppException;

public class ECSFactoryAnalyseLogic {

	private ECSFactoryAnalyseDao ecsFactoryAnalyseDao;
	private ECSCheckStockDao ecsCheckStockDao;

	// ---------------------------工厂库存折算方法 开始-------------------------//
	/**
	 * 把当前批次 ecsSection 下 的所有 clazz 数据进行转换报关数据
	 * 
	 * @param section
	 * @param clazz
	 *            实体名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends ECSBaseConvert> List<T> convertECSBaseConverts(
			ECSSection section, String clazz, boolean isImg) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在折算报关数量，请稍等...");
			info.setTotalNum(8);
			info.setCurrentNum(0);
		}

		if (clazz == null) {
			throw new RuntimeException("clazz = null");
		}

		if (section == null) {
			throw new RuntimeException("ecsSection = null");
		}

		long b = System.currentTimeMillis();

		List<T> list = ecsCheckStockDao.findByECSSection(section, clazz);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

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
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

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
			convertECSBaseConvert(img, merge);
		}
		e = System.currentTimeMillis();
		System.out.println("转换时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		if (err.length() > 0) {
			throw new RuntimeException(err.toString()
					+ "等料件料号不存在归并关系，或者归并关系内容不完整！");
		}

		b = e;
		ecsCheckStockDao.batchUpdateECSEnity(list);
		e = System.currentTimeMillis();
		System.out.println("保存数据时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(8);

		return list;
	}

	
	
	/**
	 * 根据对应关系把工厂料件转化为报关料件
	 * 
	 * @param convert
	 * @param merge
	 */
	private void convertECSBaseConvert(ECSBaseConvert convert, MergeTemp merge) {
		convert.setSeqNum(merge.getSeqNum());
		convert.setHsName(merge.getHsName());
		convert.setHsSpec(merge.getHsSpec());
		convert.setHsUnit(merge.getHsUnit());
		convert.setUnitConvert(merge.getUnitConvert());
		convert.setHsAmount(CommonUtils.getDoubleExceptNull(convert
				.getPtAmount())
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
	<T extends ECSBaseResolve> List<T> resolveECSBaseConverts(
			ECSSection section, String exgClass, ECSBaseResolve model) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在折算料件数量，请稍等...");
			info.setTotalNum(8);
			info.setCurrentNum(0);
		}
	
		if (model == null) {
			throw new RuntimeException("必须确定料件类型！");
		}

		// 删除之前的折算料件数据
		ecsCheckStockDao
				.deleteByECSSection(section, model.getClass().getName());
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		

		long b = System.currentTimeMillis();
		List<ECSBaseConvert> list = ecsCheckStockDao.findByECSSection(section,
				exgClass);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		

		b = e;
		List<BomTemp> bomList = ecsCheckStockDao.findEmsHeadH2kBomBySection(
				section, exgClass);
		e = System.currentTimeMillis();
		System.out.println("查询bom时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		

		b = e;
		Map<String, List<BomTemp>> bomMap = new HashMap<String, List<BomTemp>>();
		List<BomTemp> mbdList = null;
		BomTemp bom = null;
		String key = null;
		for (int i = 0; i < bomList.size(); i++) {
			bom = bomList.get(i);
			key = bom.getExgSeqNum()  + "/" + bom.getVersion();
			mbdList = bomMap.get(key);
			if (mbdList == null) {
				mbdList = new ArrayList<BomTemp>();

				bomMap.put(key, mbdList);
			}

			mbdList.add(bom);
		}
		e = System.currentTimeMillis();
		System.out.println("准备bom时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		
		

		b = e;
		List<T> baseResolves = new ArrayList<T>();
		ECSBaseConvert convert = null;
		StringBuilder err = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			convert = list.get(i);
			convert.setSection(section);
			mbdList = bomMap.get(convert.getSeqNum() + "/" + convert.getVersion());

			if (mbdList == null) {
				err.append(convert.getSeqNum() + ",");
				continue;
			}

			baseResolves.addAll(resolveECSBaseConvert(section, convert,
					mbdList, model));
		}
		e = System.currentTimeMillis();
		System.out.println("折料时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		
		
		

		if (err.length() > 0) {
			throw new RuntimeException(err.toString());
		}

		b = e;
		ecsCheckStockDao.batchSaveECSEnity(baseResolves);
		e = System.currentTimeMillis();
		System.out.println("保存时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(8);
		
		return baseResolves;
	}

	
	/**
	 * 残次品（Bad代表残次品）
	 * 模块把当前批次 ecsSection 下 的所有 clazz 数据进行转换报关数据
	 * 
	 * @param section
	 * @param clazz
	 *            实体名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends ECSBaseStockExgResolve> List<T> convertECSBadBaseConverts(
			ECSSection section, String clazz, boolean isImg) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在折算报关数量，请稍等...");
			info.setTotalNum(8);
			info.setCurrentNum(0);
		}

		if (clazz == null) {
			throw new RuntimeException("clazz = null");
		}

		if (section == null) {
			throw new RuntimeException("ecsSection = null");
		}

		long b = System.currentTimeMillis();

		List<T> list = ecsCheckStockDao.findByECSSection(section, clazz);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

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
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

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
			convertECSBadBaseConvert(img, merge);
		}
		e = System.currentTimeMillis();
		System.out.println("转换时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
//		if (err.length() > 0) {
//			throw new RuntimeException(err.toString()
//					+ "等料件料号不存在归并关系，或者归并关系内容不完整！");
//		}

		b = e;
		ecsCheckStockDao.batchUpdateECSEnity(list);
		e = System.currentTimeMillis();
		System.out.println("保存数据时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(8);

		return list;
	}
	/**
	 * 根据对应关系把工厂料件转化为报关料件
	 * 
	 * @param resolve
	 * @param merge
	 */
	private void convertECSBadBaseConvert(ECSBaseStockExgResolve resolve, MergeTemp merge) {
		resolve.setSeqNum(merge.getSeqNum());
		resolve.setHsName(merge.getHsName());
		resolve.setHsSpec(merge.getHsSpec());
		resolve.setHsUnit(merge.getHsUnit());
		resolve.setUnitConvert(merge.getUnitConvert());
		resolve.setHsAmount(CommonUtils.getDoubleExceptNull(resolve
				.getUsedAmount())
				* CommonUtils.getDoubleExceptNull(merge.getUnitConvert()));
		
	}
	
	/**
	 * 残次品（Bad代表残次品）, 在制品模块---成品转料件 
	 * 
	 * @param section
	 * @param exgClass
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T extends ECSBaseStockExgResolve> List<T> resolveECSBaseStockExgConverts(
			ECSSection section, String exgClass, ECSBaseStockExgResolve model) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在折算料件数量，请稍等...");
			info.setTotalNum(8);
			info.setCurrentNum(0);
		}
		if (model == null) {
			throw new RuntimeException("必须确定料件类型！");
		}
		// 删除之前的折算料件数据
		ecsCheckStockDao
				.deleteByECSSection(section, model.getClass().getName());
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		long b = System.currentTimeMillis();
		List<ECSBaseStockExg> list = ecsCheckStockDao.findByECSSection(section,
				exgClass);
		long e = System.currentTimeMillis();
		System.out.println("准备数据时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		
		b = e;
		List<BadBomTemp> bomList = ecsCheckStockDao.findMaterialBomDetailByPtNoSet(
				section, exgClass);
		e = System.currentTimeMillis();
		System.out.println("查询bom时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		b = e;
		Map<String, List<BadBomTemp>> bomMap = new HashMap<String, List<BadBomTemp>>();
		List<BadBomTemp> mbdList = null;
		BadBomTemp bom = null;
		String key = null;
		for (int i = 0; i < bomList.size(); i++) {
			bom = bomList.get(i);
			key = bom.getExgPtNo() +","+ bom.getVersion();
			mbdList = bomMap.get(key);
			if (mbdList == null) {
				mbdList = new ArrayList<BadBomTemp>();
				bomMap.put(key, mbdList);
			}
			mbdList.add(bom);
		}
		e = System.currentTimeMillis();
		System.out.println("准备bom时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		b = e;
		List<T> baseResolves = new ArrayList<T>();
		ECSBaseStockExg convert = null;
		StringBuilder err = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			convert = list.get(i);
			convert.setSection(section);
			mbdList = bomMap.get(convert.getPtNo() +","+ convert.getVersion());
			if (mbdList == null) {
				err.append(convert.getPtNo() + ",");
				continue;
			}

			baseResolves.addAll(resolveECSBaseStockExg(section, convert,
					mbdList, model));
		}
		e = System.currentTimeMillis();
		System.out.println("折料时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		if (err.length() > 0) {
			throw new RuntimeException(err.toString());
		}

		b = e;
		ecsCheckStockDao.batchSaveECSEnity(baseResolves);
		e = System.currentTimeMillis();
		System.out.println("保存时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(8);
		return baseResolves;
	}
	
	@SuppressWarnings("rawtypes")
	private List resolveECSBaseStockExg(ECSSection section, ECSBaseStockExg convert, 
			List<BadBomTemp> mbdList,  ECSBaseStockExgResolve model) {
		List<ECSBaseStockExgResolve> baseResolves = new ArrayList<ECSBaseStockExgResolve>();
		BadBomTemp bom = null;
		ECSBaseStockExgResolve resolve = null;
		for (int i = 0; i < mbdList.size(); i++) {
			bom = mbdList.get(i);
			resolve = (ECSBaseStockExgResolve) model.clone();
			resolve.setSection(section);
			resolve.setBaseStockExg(convert);
			resolve.setPtNo(bom.getPtNo());
			resolve.setPtName(bom.getPtName());
			resolve.setPtSpec(bom.getPtSpec());
			resolve.setPtUnit(bom.getPtUnit());
			resolve.setUnitWaste(CommonUtils.getDoubleExceptNull(bom.getUnitWaste()));
			resolve.setUnitUsed(CommonUtils.getDoubleExceptNull(bom.getUnitUsed()));
			resolve.setWaste(CommonUtils.getDoubleExceptNull(bom.getWaste()));
			resolve.setUsedAmount(CommonUtils.getDoubleExceptNull(convert.getStoreAmount()) * 
					CommonUtils.getDoubleExceptNull(resolve.getUnitUsed()));
			baseResolves.add(resolve);
		}
		return baseResolves;
	}
	
	@SuppressWarnings("rawtypes")
	private List resolveECSBaseConvert(ECSSection section,
			ECSBaseConvert convert, List<BomTemp> mbdList, ECSBaseResolve model) {
		List<ECSBaseResolve> baseResolves = new ArrayList<ECSBaseResolve>();
		BomTemp bom = null;
		ECSBaseResolve resolve = null;
		for (int i = 0; i < mbdList.size(); i++) {
			bom = mbdList.get(i);
			resolve = (ECSBaseResolve) model.clone();
			resolve.setSection(section);
			resolve.setBaseExg(convert);
			resolve.setUnitWaste(CommonUtils.getDoubleExceptNull(bom.getUnitWear()));
			resolve.setUnitUsed(CommonUtils.getDoubleExceptNull(bom.getUnitWear()) / (1 - 0.01 * CommonUtils.getDoubleExceptNull(bom.getWear())));
			resolve.setWaste(0.01 * CommonUtils.getDoubleExceptNull(bom.getWear()));
			resolve.setSeqNum(bom.getSeqNum());
			resolve.setHsName(bom.getName());
			resolve.setHsSpec(bom.getSpec());
			resolve.setHsUnit(bom.getUnitName());
			resolve.setHsAmount(CommonUtils.getDoubleExceptNull(convert.getHsAmount())
					* CommonUtils.getDoubleExceptNull(resolve.getUnitUsed()));

			baseResolves.add(resolve);
		}

		return baseResolves;
	}

	/**
	 * 转换 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param section
	 */
	public List<ECSStockTravelingImg> convertECSStockTravelingImgs(
			ECSSection section) {
		return convertECSBaseConverts(section,
				ECSStockTravelingImg.class.getName(), true);
	}

	/**
	 * 转换 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param section
	 * @param list
	 */
	public List<ECSStockBuyImg> convertECSStockBuyImgs(ECSSection section) {
		return convertECSBaseConverts(section, ECSStockBuyImg.class.getName(),
				true);
	}

	/**
	 * 转换 指定批次 section 下的【成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockExg> convertECSStockExgs(ECSSection section) {
		return convertECSBaseConverts(section, ECSStockExg.class.getName(),
				false);
	}
	/**
	 * 转换 指定批次 section 下的【在途成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingExg> convertECSStockTravelingExgs (ECSSection section){
		return convertECSBaseConverts(section, ECSStockTravelingExg.class.getName(),
				false);
	}
	/**
	 * 转换 指定批次 section 下的【料件库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockImg> convertECSStockImgs(ECSSection section) {
		return convertECSBaseConverts(section, ECSStockImg.class.getName(),
				true);
	}

	/**
	 * 转换 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param section
	 */
	public List<ECSStockOutFactoryImg> convertECSStockOutFactoryImgs(
			ECSSection section) {
		return convertECSBaseConverts(section,
				ECSStockOutFactoryImg.class.getName(), true);
	}

	/**
	 * 转换 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockOutSendExg> convertECSStockOutSendExgs(
			ECSSection section) {
		return convertECSBaseConverts(section,
				ECSStockOutSendExg.class.getName(), false);
	}

	/**
	 * 转换 指定批次 section 下的【在产品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockProcessImg> convertECSStockProcessImgs(
			ECSSection section) {
		return convertECSBaseConverts(section,
				ECSStockProcessImg.class.getName(), true);
	}

	/**
	 * 转换 指定批次 section 下的【残次品原材料库存】。
	 * 
	 * @param section
	 */
	public List<ECSBadImg> convertECSBadImg(ECSSection section) {
		return convertECSBaseConverts(section, ECSBadImg.class.getName(),
				true);
	}
	/**
	 * 转换 指定批次 section 下的【外发原材料库存】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendImg> convertECSStockOutSendImgs( ECSSection section){
		return convertECSBaseConverts(section, ECSStockOutSendImg.class.getName(),
				true);
	}
	/**
	 * 转换 指定批次 section 下的【在制品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSFinishingImg> convertECSFinishingImg(ECSSection section){
		return convertECSBaseConverts(section, ECSFinishingImg.class.getName(),
				true);
	}
	
	/**
	 * 转换 指定批次 section 下的【呆滞品库存原材料】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStagnateImg> convertECSStagnateImg(ECSSection section){
		return convertECSBaseConverts(section, ECSStagnateImg.class.getName(),
				true);
	}
	
	
	/**
	 * 转换 指定批次 section 下的【残次品成品折算报关数据】。
	 * 
	 * @param section
	 */
	public List<ECSFinishedExgResolve> convertECSFinishedExgs(ECSSection section){
		return convertECSBadBaseConverts(section, ECSFinishedExgResolve.class.getName(),
				true);
	}
	/**
	 * 转换 指定批次 section 下的【残次半成品折算报关数据】。
	 * 
	 * @param section
	 */
	public List<ECSSemiFinishedExgResolve> convertECSSemiFinishedExgs(ECSSection section){
		return convertECSBadBaseConverts(section, ECSSemiFinishedExgResolve.class.getName(),
				true);
	}
	
	/**
	 *转换 指定批次 section 下的【外发品库存成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendExgPtResolve> convertECSStockOutSendExgPts(
			ECSSection section){
		return convertECSBadBaseConverts(section, ECSStockOutSendExgPtResolve.class.getName(),
				true);
	}
	/**
	 * 转换 指定批次 section 下的【外发品库存半成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendSemiExgPtResolve> convertECSStockOutSendSemiExgPts(
			ECSSection section){
		return convertECSBadBaseConverts(section, ECSStockOutSendSemiExgPtResolve.class.getName(),
				true);
	}
	
	
	/**
	 * 转换 指定批次 section 下的【在制品库存成品折算报关数据】。
	 * 
	 * @param section
	 */
	public List<ECSFinishingExgResolve> convertECSFinishingExgs(ECSSection section){
		return convertECSBadBaseConverts(section, ECSFinishingExgResolve.class.getName(),
				true);
	}
	
	/**
	 * 转换 指定批次 section 下的【呆滞品库存成品折算报关数据】。
	 * 
	 * @param section
	 */
	public List<ECSStagnateExgResolve> convertECSStagnateExgs(ECSSection section){
		return convertECSBadBaseConverts(section, ECSStagnateExgResolve.class.getName(),
				true);
	}
	
	/**
	 * 转换 指定批次 section 下的【半成品库存（已入库）加工折料表】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockSemiExgPtHadStoreResolve> convertECSStockSemiExgPtHadStoreResolves(
			ECSSection section){
		return convertECSBadBaseConverts(section, ECSStockSemiExgPtHadStoreResolve.class.getName(),
				true);
	}
	/**
	 * 分解 指定批次 section 下的【成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockExgResolve> resolveECSStockExgResolves(
			ECSSection section) {
		return resolveECSBaseConverts(section, ECSStockExg.class.getName(),
				new ECSStockExgResolve());
	}
	
	/**
	 * 分解 指定批次 section 下的【在途成品】。
	 * 
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingExgResolve> resolveECSStockTravelingExgResolves(
			ECSSection section){
		return resolveECSBaseConverts(section, ECSStockTravelingExg.class.getName(),
				new ECSStockTravelingExgResolve());
	}

	/**
	 * 分解 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockOutSendExgResolve> resolveECSStockOutSendExgResolves(
			ECSSection section) {
		return resolveECSBaseConverts(section,
				ECSStockOutSendExg.class.getName(),
				new ECSStockOutSendExgResolve());
	}
	
	/**
	 * 分解 指定批次 section 下的【残次品库存成品】。
	 * 
	 * @param section
	 */
	public List<ECSFinishedExgResolve> resolveECSFinishedExgResolves(
			ECSSection section) {
		return resolveECSBaseStockExgConverts(section, ECSFinishedExg.class.getName(), new ECSFinishedExgResolve());
	}
	/**
	 * 分解 指定批次 section 下的【残次品库存半成品】。
	 * 
	 * @param section
	 */
	public List<ECSSemiFinishedExgResolve> resolveECSSemiFinishedExgResolves(
			ECSSection section) {
		return resolveECSBaseStockExgConverts(section, ECSSemiFinishedExg.class.getName(), new ECSSemiFinishedExgResolve());
	}
	/**
	 * 分解 指定批次 section 下的【外发库存成品】转料件。
	 * 
	 * @param section
	 */
	public List<ECSStockOutSendExgPtResolve> resolveECSStockOutSendExgPtResolves(ECSSection section) {
		return resolveECSBaseStockExgConverts(section, ECSStockOutSendExgPt.class.getName(), new ECSStockOutSendExgPtResolve());
	}
	/**
	 * 分解 指定批次 section 下的【外发库存半成品】转料件。
	 * 
	 * @param section
	 */
   public List<ECSStockOutSendSemiExgPtResolve> resolveECSStockOutSendSemiExgPtResolves(
			ECSSection section) {
		return resolveECSBaseStockExgConverts(section, ECSStockOutSendSemiExgPt.class.getName(), new ECSStockOutSendSemiExgPtResolve());
	}
	/**
	 * 分解 指定批次 section 下的【在制品库存成品】转料件。
	 * 
	 * @param section
	 */
	public List<ECSFinishingExgResolve> resolveECSFinishingExgResolves(
			ECSSection section) {
		return resolveECSBaseStockExgConverts(section,
				ECSFinishingExg.class.getName(), new ECSFinishingExgResolve());
	}
	 /**
		 * 分解 指定批次 section 下的【半成品库存（已入库）加工折料表】转料件。
		 * @param section
		 */
	public List<ECSStockSemiExgPtHadStoreResolve> resolveECSStockSemiExgPtHadStoreResolves(
			ECSSection section) {
		return resolveECSBaseStockExgConverts(section,
				ECSStockSemiExgPtHadStore.class.getName(), new ECSStockSemiExgPtHadStoreResolve());
	}
	/**
	 * 分解 指定批次 section 下的【呆滞品库存成品】转料件。
	 * 
	 * @param section
	 */
	public List<ECSStagnateExgResolve> resolveECSStagnateExgResolves(
			ECSSection section) {
		return resolveECSBaseStockExgConverts(section,
				ECSStagnateExg.class.getName(), new ECSStagnateExgResolve());
	}
	// ---------------------------工厂库存折算方法 结束-------------------------//

	// ---------------------------保存方法 开始-------------------------//
	<T extends ECSBaseConvert> void saveECSBaseConvert(ECSSection section,
			List<T> list) {
		ECSBaseConvert convert = null;
		for (int i = 0; i < list.size(); i++) {
			convert = list.get(i);
			convert.setSection(section);
		}
		ecsCheckStockDao.batchSaveECSEnity(list);
	}

	<T extends ECSBaseResolve> void saveECSBaseResolve(ECSSection section,
			List<T> list) {
		ECSBaseResolve resolve = null;
		for (int i = 0; i < list.size(); i++) {
			resolve = list.get(i);
			resolve.setSection(section);
		}
		ecsCheckStockDao.batchSaveECSEnity(list);
	}

	/**
	 * 批量保存【在途库存料件】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockTravelingImgs(ECSSection section,
			List<ECSStockTravelingImg> list) {
		this.saveECSBaseConvert(section, list);
	}
	/**
	 * 批量保存【在途库存成品】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockTravelingExgs(ECSSection section,
			List<ECSStockTravelingExg> list) {
		this.saveECSBaseConvert(section, list);
	}
	/**
	 * 批量保存【在途成品库存折算料件转换报关数据】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockTravelingExgResolves( ECSSection section,
			List<ECSStockTravelingExgResolve> list){
		this.saveECSBaseResolve(section, list);
	}
	/**
	 * 批量保存【残次品原材料】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSBadImgs(ECSSection section,
			List<ECSBadImg> list) {
		this.saveECSBaseConvert(section, list);
	}
	/**
	 * 批量保存【残次品库存成品，半成品。在制品库存成品,外发库存成品，呆滞品库存成品】。
	 * 
	 * @param section
	 * @param list
	 */
	public <T extends ECSBaseStockExg>void saveECSBaseStockExg(ECSSection section,
			List<T> list){
		ECSBaseStockExg convert = null;
		for (int i = 0; i < list.size(); i++) {
			convert = list.get(i);
			convert.setSection(section);
		}
		ecsCheckStockDao.batchSaveECSEnity(list);
	}
	
	/**
	 * 批量保存【在制品库存原材料】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSFinishingImgs(ECSSection section,
			List<ECSFinishingImg> list) {
		this.saveECSBaseConvert(section, list);
	}
	/**
	 * 批量保存【呆滞品库存原材料】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStagnateImgs(ECSSection section,
			List<ECSStagnateImg> list) {
		this.saveECSBaseConvert(section, list);
	}
	/**
	 * 批量保存【在外发库存原材料】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutSendImgs(ECSSection section,
			List<ECSStockOutSendImg> list) {
		this.saveECSBaseConvert(section, list);
	}
	/**
	 * 批量保存【内购库存料件】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockBuyImgs(ECSSection section,
			List<ECSStockBuyImg> list) {
		this.saveECSBaseConvert(section, list);
	}

	/**
	 * 批量保存【成品库存】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockExgs(ECSSection section, List<ECSStockExg> list) {
		this.saveECSBaseConvert(section, list);
	}
	/**
	 *  批量保存【成品库存折算料件转换报关数据】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockExgResolves(ECSSection section, List<ECSStockExgResolve> list) {
		this.saveECSBaseResolve(section, list);
	}
	/**
	 * 批量保存【料件库存】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockImgs(ECSSection section, List<ECSStockImg> list) {
		this.saveECSBaseConvert(section, list);
	}

	/**
	 * 批量保存【厂外存放料件】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutFactoryImgs(ECSSection section,
			List<ECSStockOutFactoryImg> list) {
		this.saveECSBaseConvert(section, list);
	}

	/**
	 * 批量保存【外发成品库存】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockOutSendExgs(ECSSection section,
			List<ECSStockOutSendExg> list) {
		this.saveECSBaseConvert(section, list);
	}

	/**
	 * 批量保存【在产品库存】。
	 * 
	 * @param section
	 * @param list
	 */
	public void saveECSStockProcessImgs(ECSSection section,
			List<ECSStockProcessImg> list) {
		this.saveECSBaseConvert(section, list);
	}

	// ---------------------------保存方法 结束-------------------------//

	// ---------------------------查询方法 开始-------------------------//
	/**
	 * 查询 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param section
	 */
	public List<ECSStockTravelingImg> findECSStockTravelingImgs(
			ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockTravelingImg.class.getName(), seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param section
	 * @param list
	 */
	public List<ECSStockBuyImg> findECSStockBuyImgs(ECSSection section,
			Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockBuyImg.class.getName(), seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockExg> findECSStockExgs(ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockExg.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【在途成品】。
	 * 
	 * @param section
	 */
	public List<ECSStockTravelingExg> findECSStockTravelingExgs(ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockTravelingExg.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【料件库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockImg> findECSStockImgs(ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockImg.class.getName(), seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param section
	 */
	public List<ECSStockOutFactoryImg> findECSStockOutFactoryImgs(
			ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutFactoryImg.class.getName(), seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockOutSendExg> findECSStockOutSendExgs(ECSSection section,
			Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendExg.class.getName(), seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【在产品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockProcessImg> findECSStockProcessImgs(ECSSection section,
			Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockProcessImg.class.getName(), seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockExgResolve> findECSStockExgResolves(ECSSection section,
			Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockExgResolve.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【在途成品库存折算料件】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockTravelingExgResolve> findECSStockTravelingExgResolves(ECSSection section,Integer seqNum){
		return ecsFactoryAnalyseDao.findECSBaseResolveByECSSection(section,
				ECSStockTravelingExgResolve.class, seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发成品库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockOutSendExgResolve> findECSStockOutSendExgResolves(
			ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendExgResolve.class.getName(), seqNum);
	}

	/**
	 * 查询 指定批次 section 下的【工厂实际总库存】。
	 * 
	 * @param section
	 */
	public List<ECSStockAnalyse> findECSStockAnalyse(ECSSection section,
			Integer seqNum) {
		List<ECSStockAnalyse> list = ecsCheckStockDao.findByECSSection(section,
				ECSStockAnalyse.class.getName(), seqNum);
		for (int i = 0; i < list.size(); i++) {
			ECSStockAnalyse ecs = list.get(i);
			CommonUtils.formatDouble(ecs, 5);
		}
		return list;
	}

	/**
	 * 查询 指定批次 section 下的【残次品原材料库存】。
	 * @param section
	 */
	public List<ECSBadImg> findECSBadImg(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSBadImg.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【残次品半成品库存】。
	 * @param section
	 */
	public List<ECSSemiFinishedExg> findECSSemiFinishedExg(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSSemiFinishedExg.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【残次品半成品折算成单耗】。
	 * @param section
	 *  @param seqNum 料件备案序号
	 */
	public List<ECSSemiFinishedExgResolve> findECSSemiFinishedExgResolve(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSSemiFinishedExgResolve.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【残次品成品库存】。
	 * @param section
	 */
	public List<ECSFinishedExg> findECSFinishedExg( ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSFinishedExg.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【残次品成品折算成品单耗】。
	 * @param section
	 */
	public List<ECSFinishedExgResolve> findECSFinishedExgResolve(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSFinishedExgResolve.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【残次品库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSBadStockResolve> findECSBadStockResolve( ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSBadStockResolve.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【外发库存原材料】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendImg> findECSStockOutSendImgs(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendImg.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发库存半成品】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendSemiExgPt> findECSStockOutSendSemiExgPts(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendSemiExgPt.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发库存半成品折算成单耗】。
	 * @param request
	 * @param section
	 *  @param seqNum 料件备案序号
	 */
	public List<ECSStockOutSendSemiExgPtResolve> findECSStockOutSendSemiExgPtResolves(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendSemiExgPtResolve.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【外发库存成品】。
	 * @param request
	 * @param section
	 * 
	 */
	public List<ECSStockOutSendExgPt> findECSStockOutSendExgPts(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendExgPt.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【外发库存成品折算成品单耗】。
	 * @param request
	 * @param section
	 * @param seqNum 料件备案序号
	 */
	public List<ECSStockOutSendExgPtResolve> findECSStockOutSendExgPtResolves(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendExgPtResolve.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【外发库存库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockOutSendAnalyse> findECSStockOutSendAnalyse(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockOutSendAnalyse.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【在制品原材料库存】。
	 * @param section
	 */
	public List<ECSFinishingImg> findECSFinishingImgs(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSFinishingImg.class.getName(), seqNum);
	}
	/**
	 * 查询 指定批次 section 下的【在制品成品库存】。
	 * @param section
	 * 
	 */
	public List<ECSFinishingExg> findECSFinishingExgs( ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSFinishingExg.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【在制品成品库存折算成品单耗】
	 * @param section
	 */
	public List<ECSFinishingExgResolve> findECSFinishingExgResolves(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSFinishingExgResolve.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【在制品库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSFinishingStockAnalyse> findECSFinishingStockResolves(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSFinishingStockAnalyse.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【半成品库存（已入库)】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockSemiExgPtHadStore> findECSStockSemiExgPtHadStores(
			 ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockSemiExgPtHadStore.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【半成品库存（已入库)加工折料表】。
	 * @param request
	 * @param section
	 */
	public List<ECSStockSemiExgPtHadStoreResolve> findECSStockSemiExgPtHadStoreResolves(
			ECSSection section, Integer seqNum) {
		return ecsCheckStockDao.findByECSSection(section,
				ECSStockSemiExgPtHadStoreResolve.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【呆滞品原材料库存】。
	 * @param section
	 */
	public List<ECSStagnateImg> findECSStagnateImgs(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStagnateImg.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【呆滞品成品库存】。
	 * @param section
	 * 
	 */
	public List<ECSStagnateExg> findECSStagnateExgs( ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStagnateExg.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【呆滞品成品库存折算成品单耗】
	 * @param section
	 */
	public List<ECSStagnateExgResolve> findECSStagnateExgResolves(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStagnateExgResolve.class.getName(), seqNum);
	}
	
	/**
	 * 查询 指定批次 section 下的【呆滞品库存汇总】。
	 * @param request
	 * @param section
	 */
	public List<ECSStagnateStockAnalyse> findECSStagnateStockResolves(ECSSection section,Integer seqNum){
		return ecsCheckStockDao.findByECSSection(section,
				ECSStagnateStockAnalyse.class.getName(), seqNum);
	}
	// ---------------------------查询方法 结束-------------------------//

	// ---------------------------删除方法 开始-------------------------//

	/**
	 * 删除 指定批次 section 下的【在途库存料件】。
	 * 
	 * @param section
	 */
	public void deleteECSStockTravelingImgs(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockTravelingImg.class.getName());
	}

	/**
	 * 删除 指定批次 section 下的【内购库存料件】。
	 * 
	 * @param section
	 */
	public void deleteECSStockBuyImgs(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockBuyImg.class.getName());
	}

	/**
	 * 删除 指定批次 section 下的【成品库存】。
	 * 
	 * @param section
	 */
	public void deleteECSStockExgs(ECSSection section) {
		// 先删除折料数据
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockExgResolve.class.getName());

		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockExg.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【在途成品】。
	 * 
	 * @param section
	 */
	public void deleteECSStockTravelingExgs(ECSSection section){
		// 先删除在途成品库存折算料件数据
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockTravelingExgResolve.class.getName());

		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockTravelingExg.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【料件库存】。
	 * 
	 * @param section
	 */
	public void deleteECSStockImgs(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockImg.class.getName());
	}

	/**
	 * 删除 指定批次 section 下的【厂外存放料件】。
	 * 
	 * @param section
	 */
	public void deleteECSStockOutFactoryImgs(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutFactoryImg.class.getName());
	}

	/**
	 * 删除 指定批次 section 下的【外发成品库存折料数据】。
	 * 
	 * @param section
	 */
	public void deleteECSStockOutSendExgs(ECSSection section) {
		// 先删除折料数据
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendSemiExgPtResolve.class.getName());
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendExg.class.getName());
	}

	/**
	 * 删除 指定批次 section 下的【在产品库存折料数据】。
	 * 
	 * @param section
	 */
	public void deleteECSStockProcessImgs(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockProcessImg.class.getName());
	}

	/**
	 * 删除 指定批次 section 下的【工厂实际总库存】。
	 * 
	 * @param section
	 */
	public void deleteECSStockAnalyse(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockAnalyse.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【残次品原材料库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSBadImg(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSBadImg.class.getName());
	}

	/**
	 * 删除 指定批次 section 下的【残次品半成品库存】。
	 * @param section
	 */
	public void deleteECSSemiFinishedExg(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSSemiFinishedExg.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【残次品半成品折算成品单耗】。
	 * @param section
	 */
	public void deleteECSSemiFinishedExgResolve(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSSemiFinishedExgResolve.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【残次品成品库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishedExg(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSFinishedExg.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【残次品成品折算成品单耗】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishedExgResolve(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSFinishedExgResolve.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【残次品库存汇总】。
	 * @param request
	 * @param section
	 */
	public void deleteECSBadStockResolve(ECSSection section) {
		ecsCheckStockDao.deleteByECSSection(section,
				ECSBadStockResolve.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【外发库存原材料】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendImgs(ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendImg.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【外发库存半成品】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendSemiExgPts( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendSemiExgPtResolve.class.getName());
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendSemiExgPt.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【外发库存成品】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendExgPts(ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendExgPtResolve.class.getName());
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendExgPt.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【外发库存汇总】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStockOutSendAnalyses(ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendAnalyse.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【在制品原材料库存】。
	 * @param section
	 */
	public void deleteECSFinishingImg( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSFinishingImg.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【在制品成品库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishingExg( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSFinishingExg.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【在制品成品折单耗】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishingExgResolve( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSFinishingExgResolve.class.getName());
	}
	
	/**
	 * 删除 指定批次 section 下的【在制品库汇总】。
	 * @param request
	 * @param section
	 */
	public void deleteECSFinishingStockResolve(Request request, ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSFinishingStockAnalyse.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【半成品库存（已入库)】。
	 * @param section
	 */
	public void deleteECSStockSemiExgPtHadStore( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockSemiExgPtHadStore.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【半成品库存（已入库）加工折料表】。
	 * @param section
	 */
	public void deleteECSStockSemiExgPtHadStoreResolve( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockSemiExgPtHadStoreResolve.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【呆滞品原材料库存】。
	 * @param section
	 */
	public void deleteECSStagnateImg( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStagnateImg.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【呆滞品成品折单耗】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStagnateExgResolve( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStagnateExgResolve.class.getName());
	}
	/**
	 * 删除 指定批次 section 下的【呆滞品成品库存】。
	 * @param request
	 * @param section
	 */
	public void deleteECSStagnateExg( ECSSection section){
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStagnateExg.class.getName());
	}
	// ---------------------------删除方法 结束-------------------------//

	/**
	 * 计算 工厂实际总库存
	 * 
	 * @param section
	 * @return
	 */
	public List<ECSStockAnalyse> stockAnalyse(ECSSection section) {
		Map<Integer, ECSStockAnalyse> map = new HashMap<Integer, ECSStockAnalyse>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在汇总库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		// 删除之前库存分析数据
		long b = System.currentTimeMillis();
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockAnalyse.class.getName());
		long e = System.currentTimeMillis();
		System.out.println("删除之前库存分析数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		// 料件库存数据
		b = e;
		countStockAmount(map, ECSStockImg.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计料件库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		// 在产品库存数据
		b = e;
		countStockAmount(map, ECSStockProcessImg.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计在产品库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		
		if(Boolean.TRUE.equals(section.getBuyIsCount())) {
			// 内购库存数据
			b = e;
			countStockAmount(map, ECSStockBuyImg.class, section);
			e = System.currentTimeMillis();
			System.out.println("统计内购库存数据 时间：" + (e - b) / 1000.0);
			if (info != null)
				info.setCurrentNum(info.getCurrentNum() + 1);
		}
		
		

		// 厂外库存数据
		b = e;
		countStockAmount(map, ECSStockOutFactoryImg.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计厂外库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		// 在途库存数据
		b = e;
		countStockAmount(map, ECSStockTravelingImg.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计料件在途库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		
		// 在途成品数据
		b = e;
		countStockAmount(map, ECSStockTravelingExgResolve.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计成品在途库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		// 成品耗用数据
		b = e;
		countStockAmount(map, ECSStockExgResolve.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计成品耗用数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		// 外发耗用数据
		b = e;
		countStockAmount(map, ECSStockOutSendSemiExgPtResolve.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计外发耗用数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);

		// 残次品库存汇总数据
		b = e;
		countStockAmount(map, ECSBadStockResolve.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计残次品库存汇总数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		
		//在制品库存汇总数据
		b = e;
		countStockAmount(map, ECSFinishingStockAnalyse.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计在制品库存汇总数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		
		//半成品（已入库）数据
		b = e;
		countStockAmount(map, ECSStockSemiExgPtHadStoreResolve.class, section);
		e = System.currentTimeMillis();
		System.out.println("统计半成品（已入库）数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		
		//外发库存汇总数据
				b = e;
				countStockAmount(map, ECSStockOutSendAnalyse.class, section);
				e = System.currentTimeMillis();
				System.out.println("统计外发库存汇总数据 时间：" + (e - b) / 1000.0);
				if (info != null)
					info.setCurrentNum(info.getCurrentNum() + 1);
		
		b = e;
		List<ECSStockAnalyse> analyses = new ArrayList<ECSStockAnalyse>(
				map.values());

		Collections.sort(analyses, new Comparator<ECSStockAnalyse>() {
			public int compare(ECSStockAnalyse o1, ECSStockAnalyse o2) {				
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});
		e = System.currentTimeMillis();
		System.out.println("排序 时间：" + (e - b) / 1000.0);

		b = e;
		ecsCheckStockDao.batchSaveOrUpdateDirect(analyses);
		e = System.currentTimeMillis();
		System.out.println("保存库存分析数据 时间：" + (e - b) / 1000.0);

		return analyses;
	}

	/**
	 * 根据clazz统计库存数据
	 * 
	 * @param map
	 * @param clazz
	 * @param section
	 */
	private <T extends ECSBaseStockItem> void countStockAmount(Map<Integer, ECSStockAnalyse> map, Class<T> clazz,
			ECSSection section) {
		
		
		// 成品耗用数据
		List<T> items = ecsCheckStockDao.findECSBaseStockItemByECSSection(
				section, clazz);
		ECSBaseStockItem item = null;
		ECSStockAnalyse analyse = null;
		Integer seqNum = null;
		for (int i = 0; i < items.size(); i++) {
			item = items.get(i);
			seqNum = item.getSeqNum();
//			if(seqNum == null)
//				throw new AppException("盘点分析库下存在未折算报关数量数据，请折算后再进行统计。");
			if(seqNum !=null){
				analyse = map.get(seqNum);
				if (analyse == null) {
					analyse = new ECSStockAnalyse();
					analyse.init();
					analyse.setSection(section);
					analyse.setSeqNum(seqNum);
					analyse.setEmsNo(item.getEmsNo());
					analyse.setHsName(item.getHsName());
					analyse.setHsSpec(item.getHsSpec());
					analyse.setHsUnit(item.getHsUnit());

					map.put(seqNum, analyse);
				}

				// 按类型汇总数据
				this.setStockAmount(analyse, item, clazz);
				
				// 总库存 = 总库存 + 当前数据 
				analyse.setStockTotalAmount(analyse.getStockTotalAmount()
						+ item.getHsAmount());
			}
		}
		
		if(items.size() > 100000) {
			System.out.println("items.size():" + items.size());
			System.out.println("map.size():" + map.size());
		}
	}
	
	/**
	 * 根据类型设置统计的字段
	 * @param analyse
	 * @param item
	 * @param clazz
	 */
	private <T> void setStockAmount(ECSStockAnalyse analyse, ECSBaseStockItem item, Class<T> clazz) {
		if(ECSStockImg.class == clazz) {
			// 料件累计
			analyse.setStockAmountImg(analyse.getStockAmountImg()
					+ item.getHsAmount());
		} else if(ECSStockExgResolve.class == clazz) {
			// 成品耗用累计
			analyse.setStockAmountExg(analyse.getStockAmountExg()
					+ item.getHsAmount());
		} else if(ECSStockBuyImg.class == clazz) {
			// 内购累计
			analyse.setStockAmountBuyImg(analyse.getStockAmountBuyImg()
					+ item.getHsAmount());
		} else if(ECSStockProcessImg.class == clazz) {
			// 在产品累计
			analyse.setStockAmountProcessImg(analyse.getStockAmountProcessImg()
					+ item.getHsAmount());
		} else if(ECSStockOutSendSemiExgPtResolve.class == clazz) {
			// 外发耗用累计
			analyse.setStockAmountOutSendExg(analyse.getStockAmountOutSendExg()
					+ item.getHsAmount());
		} else if(ECSStockOutFactoryImg.class == clazz) {
			// 厂外累计
			analyse.setStockAmountOutFactoryImg(analyse.getStockAmountOutFactoryImg()
					+ item.getHsAmount());
		} else if(ECSStockTravelingImg.class == clazz) {
			// 料件在途累计
			analyse.setStockAmountTravelingImg(analyse.getStockAmountTravelingImg()
					+ item.getHsAmount());
		} else if(ECSStockTravelingExgResolve.class == clazz) {
			// 成品在途累计
			analyse.setStockAmountTravelingExg(analyse.getStockAmountTravelingExg()
					+ item.getHsAmount());
		} else if(ECSBadStockResolve.class == clazz) {
			// 残次品库存累计
			analyse.setStockAmountBadImg(analyse.getStockAmountBadImg()
					+ item.getHsAmount());
		}  else if(ECSStockSemiExgPtHadStoreResolve.class == clazz) {
			//半成品（已入库）
			analyse.setStockAmountSemiExgHStore(analyse.getStockAmountSemiExgHStore()
					+ item.getHsAmount());
		}  else if(ECSFinishingStockAnalyse.class == clazz) {
			// 在制品库存
			analyse.setStockAmountFinishingExg(analyse.getStockAmountFinishingExg()
					+ item.getHsAmount());
		}  else if(ECSStockOutSendAnalyse.class == clazz) {
			// 外发库存
			analyse.setStockAmountOutSendExgPt(analyse.getStockAmountOutSendExgPt()
					+ item.getHsAmount());
		} else {
			throw new RuntimeException("盘点核查-工厂数据汇总，不汇总该类型数据：" + clazz.getName());
		}
	}
	
	

	/**
	 * @param ecsFactoryAnalyseDao
	 *            the ecsFactoryAnalyseDao to set
	 */
	public void setEcsFactoryAnalyseDao(
			ECSFactoryAnalyseDao ecsFactoryAnalyseDao) {
		this.ecsFactoryAnalyseDao = ecsFactoryAnalyseDao;
	}

	/**
	 * @param csCheckStockDao
	 *            the csCheckStockDao to set
	 */
	public void setEcsCheckStockDao(ECSCheckStockDao ecsCheckStockDao) {
		this.ecsCheckStockDao = ecsCheckStockDao;
	}
	/**
	 * 查询所有的报关工厂常用bom版本
	 * @return
	 */
	public List<MaterialBomVersion> findMaterialBomVersions() {
		return this.ecsFactoryAnalyseDao.findMaterialBomVersions();
	}
	/**
	 * 统计残次品库存汇总
	 * @param section
	 */
	public List<ECSBadStockResolve> convertECSBadStockResolve(ECSSection section){
		// 库存汇总
		List<ECSBadStockResolve> badStockLs = new ArrayList<ECSBadStockResolve>();
		// 库存汇总
		Map<String, ECSBadStockResolve> badStockMap = new HashMap<String, ECSBadStockResolve>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在残次品汇总库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		//先删除该批次下的汇总表
		long b = System.currentTimeMillis();
		ecsCheckStockDao.deleteByECSSection(section,
				ECSBadStockResolve.class.getName());
		long e = System.currentTimeMillis();
		System.out.println("删除之前库存分析数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
			
		//原材料库存数据
		b = e;
		List imgLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSBadImg.class);
		mergerECSBadStock(badStockMap, imgLs, "ECSBadImg");
		e = System.currentTimeMillis();
		System.out.println("统计原材料库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		// 残次品半成品折料数据
		b = e;
		List semiExgResolveLs = this.ecsCheckStockDao.convertECSStockBySeqNum(
				section, ECSSemiFinishedExgResolve.class);
		mergerECSBadStock(badStockMap, semiExgResolveLs, "ECSSemiFinishedExgResolve");
		e = System.currentTimeMillis();
		System.out.println("统计残次品半成品折料数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		// 残次品成品折料
		b = e;
		List exgResolveLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSFinishedExgResolve.class);
		mergerECSBadStock(badStockMap, exgResolveLs, "ECSFinishedExgResolve");
		e = System.currentTimeMillis();
		System.out.println("统计残次品成品折料数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		if (badStockMap != null) {
			Iterator badIterator = badStockMap.values().iterator();
			while (badIterator.hasNext()) {
				ECSBadStockResolve ecsBadStock = (ECSBadStockResolve) badIterator.next();
				ecsBadStock.setSection(section);
				badStockLs.add(ecsBadStock);
			}
		}
		this.ecsCheckStockDao.batchSaveECSEnity(badStockLs);
		return badStockLs;
	}
	/** 
	 * 统计残次品库存汇总
	 * 总库存 = 在残次品原材料+残次品半成品折料 + 残次品成品折料
	 * @param badStockMap 总库存 
	 * @param list 在残次品原材料 或 残次品半成品折料 或者 残次品成品折料
	 * @param clazz 
	 */
	private void mergerECSBadStock(Map badStockMap,List list,String clazz){
		if(list!=null){
			String key;
			for (int i = 0; i < list.size(); i++) {
				Object [] obj = (Object[]) list.get(i);
				if(obj[0]!=null){
					key = obj[0].toString();//备案序号
					ECSBadStockResolve badStock = (ECSBadStockResolve) badStockMap.get(key);
					if(badStock==null){
						badStock = new ECSBadStockResolve();
						badStock.init();
						badStock.setSeqNum(Integer.valueOf(obj[0].toString()));
						badStock.setHsName(obj[1]==null?"":obj[1].toString());
						badStock.setHsSpec(obj[2]==null?"":obj[2].toString());
						badStock.setHsUnit(obj[3]==null?"":obj[3].toString());
						if(clazz.equals("ECSBadImg")){//在残次品原材料
							badStock.setImgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}else if(clazz.equals("ECSSemiFinishedExgResolve")){//残次品半成品折料
							badStock.setSemiFinishedHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}else if(clazz.equals("ECSFinishedExgResolve")){// 残次品成品折料
							badStock.setFinishedHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}
						badStock.setHsAmount(CommonUtils
								.getDoubleExceptNull(badStock.getImgHsAmount()
										+ badStock.getSemiFinishedHsAmount()
										+ badStock.getFinishedHsAmount()));
						badStockMap.put(key, badStock);
					}else{//累加
						if(clazz.equals("ECSBadImg")){
							badStock.setImgHsAmount(badStock.getImgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}else if(clazz.equals("ECSSemiFinishedExgResolve")){
							badStock.setSemiFinishedHsAmount(badStock.getSemiFinishedHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}else if(clazz.equals("ECSFinishedExgResolve")){
							badStock.setFinishedHsAmount(badStock.getFinishedHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}
						badStock.setHsAmount(CommonUtils
								.getDoubleExceptNull(badStock.getImgHsAmount()
										+ badStock.getSemiFinishedHsAmount()
										+ badStock.getFinishedHsAmount()));
					}
				}
			}
		}
	}
	
	/**
	 * 统计在制品库存汇总
	 * @param section
	 */
	public List<ECSFinishingStockAnalyse> convertECSFinishingStockResolve(ECSSection section){
		// 库存汇总
		List<ECSFinishingStockAnalyse> finishingStockLs = new ArrayList<ECSFinishingStockAnalyse>();
		// 库存汇总
		Map<String, ECSFinishingStockAnalyse> finishingStockMap = new HashMap<String, ECSFinishingStockAnalyse>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在计算在制品汇总库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		//先删除该批次下的汇总表
		long b = System.currentTimeMillis();
		ecsCheckStockDao.deleteByECSSection(section,
				ECSFinishingStockAnalyse.class.getName());
		long e = System.currentTimeMillis();
		System.out.println("删除之前库存分析数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		//原材料库存数据
		b = e;
		List imgLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSFinishingImg.class);
		mergerECSFinishingStock(finishingStockMap, imgLs, "ECSFinishingImg");
		e = System.currentTimeMillis();
		System.out.println("统计原材料库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		// 成品折料
		b = e;
		List exgResolveLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSFinishingExgResolve.class);
		mergerECSFinishingStock(finishingStockMap, exgResolveLs, "ECSFinishingExgResolve");
		e = System.currentTimeMillis();
		System.out.println("统计在制品成品折料数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		if (finishingStockMap != null) {
			Iterator finishingIterator = finishingStockMap.values().iterator();
			while (finishingIterator.hasNext()) {
				ECSFinishingStockAnalyse ecsFinishingStock = (ECSFinishingStockAnalyse) finishingIterator.next();
				ecsFinishingStock.setSection(section);
				finishingStockLs.add(ecsFinishingStock);
			}
		}
		this.ecsCheckStockDao.batchSaveECSEnity(finishingStockLs);
		return finishingStockLs;
	}
	
	/** 
	 * 统计在制品库存汇总
	 * 总库存 = 在制品原材料+ 在制品成品折料
	 * @param finishingStockMap 总库存 
	 * @param list 在制品原材料+ 在制品成品折料
	 * @param clazz 
	 */
	private void mergerECSFinishingStock(Map badStockMap,List list,String clazz){
		if(list!=null){
			String key;
			for (int i = 0; i < list.size(); i++) {
				Object [] obj = (Object[]) list.get(i);
				if(obj[0]!=null){
					key = obj[0].toString();//备案序号
					ECSFinishingStockAnalyse finishingStock = (ECSFinishingStockAnalyse) badStockMap.get(key);
					if(finishingStock==null){
						finishingStock = new ECSFinishingStockAnalyse();
						finishingStock.init();
						finishingStock.setSeqNum(Integer.valueOf(obj[0].toString()));
						finishingStock.setHsName(obj[1]==null?"":obj[1].toString());
						finishingStock.setHsSpec(obj[2]==null?"":obj[2].toString());
						finishingStock.setHsUnit(obj[3]==null?"":obj[3].toString());
						if(clazz.equals("ECSFinishingImg")){//在制品原材料
							finishingStock.setImgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}else if(clazz.equals("ECSFinishingExgResolve")){// 在制品成品折料
							finishingStock.setExgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}
						finishingStock.setHsAmount(CommonUtils
								.getDoubleExceptNull(finishingStock.getImgHsAmount()
										+ finishingStock.getExgHsAmount()));
						badStockMap.put(key, finishingStock);
					}else{//累加
						if(clazz.equals("ECSFinishingImg")){
							finishingStock.setImgHsAmount(finishingStock.getImgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}else if(clazz.equals("ECSFinishingExgResolve")){
							finishingStock.setExgHsAmount(finishingStock.getExgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}
						finishingStock.setHsAmount(CommonUtils
								.getDoubleExceptNull(finishingStock.getImgHsAmount()
										+ finishingStock.getExgHsAmount()));
					}
				}
			}
		}
	}
	/**
	 * 统计呆滞品库存汇总
	 * @param section
	 */
	public List<ECSStagnateStockAnalyse> convertECSStagnateStockResolve(ECSSection section){
		// 库存汇总
		List<ECSStagnateStockAnalyse> stagnateStockLs = new ArrayList<ECSStagnateStockAnalyse>();
		// 库存汇总
		Map<String, ECSStagnateStockAnalyse> stagnateStockMap = new HashMap<String, ECSStagnateStockAnalyse>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在计算在制品汇总库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		//先删除该批次下的汇总表
		long b = System.currentTimeMillis();
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStagnateStockAnalyse.class.getName());
		long e = System.currentTimeMillis();
		System.out.println("删除之前库存分析数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		//原材料库存数据
		b = e;
		List imgLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSStagnateImg.class);
		mergerECSStagnateStock(stagnateStockMap, imgLs, "ECSStagnateImg");
		e = System.currentTimeMillis();
		System.out.println("统计原材料库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		// 成品折料
		b = e;
		List exgResolveLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSStagnateExgResolve.class);
		mergerECSStagnateStock(stagnateStockMap, exgResolveLs, "ECSStagnateExgResolve");
		e = System.currentTimeMillis();
		System.out.println("统计在制品成品折料数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		if (stagnateStockMap != null) {
			Iterator stagnateIterator = stagnateStockMap.values().iterator();
			while (stagnateIterator.hasNext()) {
				ECSStagnateStockAnalyse ecsStagnateStock = (ECSStagnateStockAnalyse) stagnateIterator.next();
				ecsStagnateStock.setSection(section);
				stagnateStockLs.add(ecsStagnateStock);
			}
		}
		this.ecsCheckStockDao.batchSaveECSEnity(stagnateStockLs);
		return stagnateStockLs;
	}
	
	/** 
	 * 统计呆滞品库存汇总
	 * 总库存 = 在制品原材料+ 在制品成品折料
	 * @param stagnateStockMap 总库存 
	 * @param list 在制品原材料+ 在制品成品折料
	 * @param clazz 
	 */
	private void mergerECSStagnateStock(Map stagnateStockMap,List list,String clazz){
		if(list!=null){
			String key;
			for (int i = 0; i < list.size(); i++) {
				Object [] obj = (Object[]) list.get(i);
				if(obj[0]!=null){
					key = obj[0].toString();//备案序号
					ECSStagnateStockAnalyse stagnateStock = (ECSStagnateStockAnalyse) stagnateStockMap.get(key);
					if(stagnateStock==null){
						stagnateStock = new ECSStagnateStockAnalyse();
						stagnateStock.init();
						stagnateStock.setSeqNum(Integer.valueOf(obj[0].toString()));
						stagnateStock.setHsName(obj[1]==null?"":obj[1].toString());
						stagnateStock.setHsSpec(obj[2]==null?"":obj[2].toString());
						stagnateStock.setHsUnit(obj[3]==null?"":obj[3].toString());
						if(clazz.equals("ECSStagnateImg")){//呆滞品原材料
							stagnateStock.setImgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}else if(clazz.equals("ECSStagnateExgResolve")){// 呆滞品成品折料
							stagnateStock.setExgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}
						stagnateStock.setHsAmount(CommonUtils
								.getDoubleExceptNull(stagnateStock.getImgHsAmount()
										+ stagnateStock.getExgHsAmount()));
						stagnateStockMap.put(key, stagnateStock);
					}else{//累加
						if(clazz.equals("ECSStagnateImg")){
							stagnateStock.setImgHsAmount(stagnateStock.getImgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}else if(clazz.equals("ECSStagnateExgResolve")){
							stagnateStock.setExgHsAmount(stagnateStock.getExgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}
						stagnateStock.setHsAmount(CommonUtils
								.getDoubleExceptNull(stagnateStock.getImgHsAmount()
										+ stagnateStock.getExgHsAmount()));
					}
				}
			}
		}
	}
	/**
	 * 统计外发库存汇总
	 * @param section
	 */
	public List<ECSStockOutSendAnalyse> convertECSStockOutSendAnalyses(ECSSection section){
		// 库存汇总
		List<ECSStockOutSendAnalyse> outSendAnalyseLs = new ArrayList<ECSStockOutSendAnalyse>();
		// 库存汇总
		Map<String, ECSStockOutSendAnalyse> outSendAnalyseMap = new HashMap<String, ECSStockOutSendAnalyse>();
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if (request != null && StringUtils.isNotBlank(request.getTaskId())) {
			info = ProcExeProgress.getInstance().getProgressInfo(
					request.getTaskId());
			info.setMethodName("正在计算外发库存，请稍等...");
			info.setTotalNum(9);
			info.setCurrentNum(0);
		}
		//先删除该批次下的汇总表
		long b = System.currentTimeMillis();
		ecsCheckStockDao.deleteByECSSection(section,
				ECSStockOutSendAnalyse.class.getName());
		long e = System.currentTimeMillis();
		System.out.println("删除之前库存分析数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		//原材料库存数据
		b = e;
		List imgLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSStockOutSendImg.class);
		mergerOutSendStock(outSendAnalyseMap, imgLs, "ECSStockOutSendImg");
		e = System.currentTimeMillis();
		System.out.println("统计原材料库存数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		// 成品折料
		b = e;
		List exgPtResolveLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSStockOutSendExgPtResolve.class);
		mergerOutSendStock(outSendAnalyseMap, exgPtResolveLs, "ECSStockOutSendExgPtResolve");
		e = System.currentTimeMillis();
		System.out.println("统计在制品成品折料数据 时间：" + (e - b) / 1000.0);
		if (info != null)
			info.setCurrentNum(info.getCurrentNum() + 1);
		// 半成品折料
		b = e;
		List semiExgResolveLs = this.ecsCheckStockDao.convertECSStockBySeqNum(section,
				ECSStockOutSendSemiExgPtResolve.class);
		mergerOutSendStock(outSendAnalyseMap, semiExgResolveLs, "ECSStockOutSendSemiExgResolve");
		e = System.currentTimeMillis();
		System.out.println("统计在制品成品折料数据 时间：" + (e - b) / 1000.0);
		if (outSendAnalyseMap != null) {
			Iterator finishingIterator = outSendAnalyseMap.values().iterator();
			while (finishingIterator.hasNext()) {
				ECSStockOutSendAnalyse stockOutSendAnalyse = (ECSStockOutSendAnalyse) finishingIterator.next();
				stockOutSendAnalyse.setSection(section);
				outSendAnalyseLs.add(stockOutSendAnalyse);
			}
		}
		this.ecsCheckStockDao.batchSaveECSEnity(outSendAnalyseLs);
		return outSendAnalyseLs;
	}
	
	/** 
	 * 统计外发库存汇总
	 * 总库存 = 外发原材料+ 外发成品折料+外发半成品折料
	 * @param outSendStockMap 总库存 
	 * @param list 外发原材料+ 外发成品折料+外发半成品折料
	 * @param clazz 
	 */
	private void mergerOutSendStock(Map outSendStockMap,List list,String clazz){
		if(list!=null){
			String key;
			for (int i = 0; i < list.size(); i++) {
				Object [] obj = (Object[]) list.get(i);
				if(obj[0]!=null){
					key = obj[0].toString();//备案序号
					ECSStockOutSendAnalyse outSendAnalyse = (ECSStockOutSendAnalyse) outSendStockMap.get(key);
					if(outSendAnalyse==null){
						outSendAnalyse = new ECSStockOutSendAnalyse();
						outSendAnalyse.init();
						outSendAnalyse.setSeqNum(Integer.valueOf(obj[0].toString()));
						outSendAnalyse.setHsName(obj[1]==null?"":obj[1].toString());
						outSendAnalyse.setHsSpec(obj[2]==null?"":obj[2].toString());
						outSendAnalyse.setHsUnit(obj[3]==null?"":obj[3].toString());
						if(clazz.equals("ECSStockOutSendImg")){//外发原材料
							outSendAnalyse.setImgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}else if(clazz.equals("ECSStockOutSendSemiExgResolve")){//外发半成品折料
							outSendAnalyse.setSemiExgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}else if(clazz.equals("ECSStockOutSendExgPtResolve")){// 外发成品折料
							outSendAnalyse.setExgHsAmount(obj[4]==null?0.0:Double.valueOf(obj[4].toString()));
						}
						outSendAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(outSendAnalyse.getImgHsAmount()
										+ outSendAnalyse.getSemiExgHsAmount()
										+ outSendAnalyse.getExgHsAmount()));
						outSendStockMap.put(key, outSendAnalyse);
					}else{//累加
						if(clazz.equals("ECSStockOutSendImg")){
							outSendAnalyse.setImgHsAmount(outSendAnalyse.getImgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}else if(clazz.equals("ECSStockOutSendSemiExgResolve")){
							outSendAnalyse.setSemiExgHsAmount(outSendAnalyse.getSemiExgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}else if(clazz.equals("ECSStockOutSendExgPtResolve")){
							outSendAnalyse.setExgHsAmount(outSendAnalyse.getExgHsAmount()+(obj[4]==null?0.0:Double.valueOf(obj[4].toString())));
						}
						outSendAnalyse.setHsAmount(CommonUtils
								.getDoubleExceptNull(outSendAnalyse.getImgHsAmount()
										+ outSendAnalyse.getSemiExgHsAmount()
										+ outSendAnalyse.getExgHsAmount()));
					}
				}
			}
		}
	}
}
