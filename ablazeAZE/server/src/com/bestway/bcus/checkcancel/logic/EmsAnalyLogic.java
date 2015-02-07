/*
 * Created on 2004-7-29
 *getImg
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.hibernate.dialect.IngresDialect;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.checkcancel.dao.CheckCancelDao;
import com.bestway.bcus.checkcancel.dao.EmsAnalyDao;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CancelImgResult;
import com.bestway.bcus.checkcancel.entity.CheckBgCy;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
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
import com.bestway.bcus.checkcancel.entity.FactoryCheckImgForBg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckImgResult;
import com.bestway.bcus.checkcancel.entity.ImportDataForCheckImg;
import com.bestway.bcus.checkcancel.entity.ImportDataForFactoryPd;
import com.bestway.bcus.checkcancel.entity.TempDD;
import com.bestway.bcus.custombase.entity.parametercode.Deduc;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.RrportDelcareType;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class EmsAnalyLogic {
	private EmsAnalyDao emsAnalyDao = null;
	private CheckCancelDao checkCancelDao = null;
	private EmsEdiTrDao emsEdiTrDao = null;
	private Hashtable deducHsI = new Hashtable();

	private Hashtable deducHsE = new Hashtable();

	private void getDeducI() {
		if (deducHsI != null) {
			deducHsI.clear();
		}
		List list = this.checkCancelDao.findAllDeduc("I");
		for (int i = 0; i < list.size(); i++) {
			Deduc obj = (Deduc) list.get(i);
			deducHsI.put(obj.getTradeCode(), obj);
		}
	}

	private void getDeducE() {
		if (deducHsE != null) {
			deducHsE.clear();
		}
		List list = this.checkCancelDao.findAllDeduc("E");
		for (int i = 0; i < list.size(); i++) {
			Deduc obj = (Deduc) list.get(i);
			deducHsE.put(obj.getTradeCode(), obj);
		}
	}

	/**
	 * 原料进口料件查询
	 * 
	 * @param conditions
	 * @param head
	 * @return
	 */
	public List calculateBgImg(List conditions, EmsAnalyHead head) {
		List list = emsAnalyDao.commonSearch("", "CustomsDeclarationCommInfo",
				conditions, "");
		System.out.println("----:" + list);
		Hashtable storeInfos = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			String seqNum = String.valueOf(info.getCommSerialNo());
			String name = info.getCommName();
			String spec = info.getCommSpec();
			String unit = info.getUnit() == null ? null : info.getUnit()
					.getCode();
			String key = seqNum + name + spec + unit;
			if (storeInfos.get(key) == null) {
				EmsBgImg img = new EmsBgImg();
				img.setEmsNo(info.getBaseCustomsDeclaration().getEmsHeadH2k());
				img.setSeqNum(info.getCommSerialNo());
				img.setName(info.getCommName());
				img.setSpec(info.getCommSpec());
				img.setUnit(info.getUnit());
				if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.DIRECT_IMPORT)) { // 料件进口
					img.setInNum(formatDouble(info.getCommAmount()));
					img.setTotalNum(formatDouble(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)) {// 转厂
					img.setChangeNum(formatDouble(info.getCommAmount()));
					img.setTotalNum(formatDouble(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.BACK_MATERIEL_EXPORT)) {// 减退料
					img.setExitNum(formatDouble(info.getCommAmount()));
					img.setTotalNum(jisuanCutt(Double.valueOf(0),
							info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.REMAIN_FORWARD_IMPORT)) {// 余量转入
					img.setRemainInNum(formatDouble(info.getCommAmount()));
					img.setTotalNum(info.getCommAmount());
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.REMAIN_FORWARD_EXPORT)) {// 余量转出
					img.setRemainOutNum(formatDouble(info.getCommAmount()));
					img.setTotalNum(jisuanCutt(Double.valueOf(0),
							info.getCommAmount()));
				}
				storeInfos.put(key, img);
			} else {
				EmsBgImg img = (EmsBgImg) storeInfos.get(key);
				if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.DIRECT_IMPORT)) { // 料件进口
					img.setInNum(jisuan(img.getInNum(), info.getCommAmount()));
					img.setTotalNum(jisuan(img.getTotalNum(), info
							.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)) {// 转厂
					img.setChangeNum(jisuan(img.getChangeNum(), info
							.getCommAmount()));
					img.setTotalNum(jisuan(img.getTotalNum(), info
							.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.BACK_MATERIEL_EXPORT)) {// 减退料
					img.setExitNum(jisuan(img.getExitNum(), info
							.getCommAmount()));
					img.setTotalNum(jisuanCutt(img.getTotalNum(), info
							.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.REMAIN_FORWARD_IMPORT)) {// 余量转入
					img.setRemainInNum(jisuan(img.getRemainInNum(), info.getCommAmount()));
					img.setTotalNum(jisuan(img.getTotalNum(), info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType() != null
						&& info.getBaseCustomsDeclaration().getImpExpType()
								.equals(ImpExpType.REMAIN_FORWARD_EXPORT)) {// 余量转出
					img.setRemainOutNum(jisuan(img.getRemainOutNum(), info.getCommAmount()));
					img.setTotalNum(jisuanCutt(img.getTotalNum(),
							info.getCommAmount()));
				}

			}

		}
		// ==========计算期初数取起始日期前的核销帐册的结余数==============
		HashMap<String, Double> mapInitNum = getEmgBgInitNum();

		List list1 = new Vector(storeInfos.values());
		for (int i = 0; i < list1.size(); i++) {
			EmsBgImg img = (EmsBgImg) list1.get(i);

			Integer seqNum = img.getSeqNum();
			String name = img.getName();
			String spec = img.getSpec();
			String unit = img.getUnit() == null ? null : img.getUnit()
					.getCode();
			String key = seqNum + "@/@" + name + "@/@" + spec + "@/@" + unit;
			img.setInitNum(mapInitNum.get(key) != null ? mapInitNum.get(key)
					: 0.0);
			img.setTotalNum(jisuan(img.getTotalNum(), img.getInitNum()));// 总出口数+期初
			img.setHead(head);
			img.setCompany(CommonUtils.getCompany());
			this.emsAnalyDao.SaveEmsBgImg(img);
		}
		return new Vector(storeInfos.values());
	}

	/**
	 * 得到电子帐册核销最大一次的结余 电子帐册的料号+名称+规格+单位
	 */
	private HashMap getEmgBgInitNum() {
		HashMap<String, Double> mapInitNum = new HashMap<String, Double>();
		CancelHead cancelHead = checkCancelDao.findMaxCancelTimesCancelHead(
				false, RrportDelcareType.DELCARE);
		List list = checkCancelDao.findCancelImgResult(cancelHead, false);
		for (int i = 0; i < list.size(); i++) {
			CancelImgResult imgResult = (CancelImgResult) list.get(i);
			Integer seqNum = imgResult.getEmsSeqNum();
			String name = imgResult.getName();
			String spec = imgResult.getSpec();
			String unit = imgResult.getUnit();
			String key = seqNum + "@/@" + name + "@/@" + spec + "@/@" + unit;
			mapInitNum.put(key, imgResult.getResultNum());
		}
		return mapInitNum;
	}

	private Double jisuan(Double x, Double y) {
		if (x == null) {
			x = Double.valueOf(0);
		}
		if (y == null) {
			y = Double.valueOf(0);
		}
		return Double.valueOf(x.doubleValue() + y.doubleValue());
	}

	private Double jisuanCutt(Double x, Double y) {
		if (x == null) {
			x = Double.valueOf(0);
		}
		if (y == null) {
			y = Double.valueOf(0);
		}
		return Double.valueOf(x.doubleValue() - y.doubleValue());
	}

	private Double formatDouble(Double x) {
		if (x != null) {
			return x;
		}
		return Double.valueOf(0);
	}

	private double Dtod(Double x) {
		if (x != null) {
			return x.doubleValue();
		}
		return 0;
	}

	// 报关出口成品查询
	public List calculateBgExg(List conditions, EmsAnalyHead head) {
		List list = emsAnalyDao.commonSearch("", "CustomsDeclarationCommInfo",
				conditions, "");
		Hashtable storeInfos = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			String seqNum = String.valueOf(info.getCommSerialNo());
			String name = info.getCommName();
			String spec = info.getCommSpec();
			String unit = info.getUnit().getCode();
			String versionNo = info.getVersion();
			String key = seqNum + name + spec + unit + versionNo;
			if (storeInfos.get(key) == null) {
				EmsBgExg img = new EmsBgExg();
				img.setEmsNo(info.getBaseCustomsDeclaration().getEmsHeadH2k());
				img.setSeqNum(info.getCommSerialNo());
				img.setName(info.getCommName());
				img.setVersionNo(info.getVersion());
				img.setSpec(info.getCommSpec());
				img.setUnit(info.getUnit());

				if (info.getBaseCustomsDeclaration().getImpExpType().equals(
						ImpExpType.DIRECT_EXPORT)) { // 出口
					img.setOutNum(formatDouble(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)) {// 转厂
					img.setChangeNum(formatDouble(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.BACK_FACTORY_REWORK)) {// 退厂返工
					img.setBackReWork(formatDouble(info.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.REWORK_EXPORT)) {// 返工复出
					img.setReWorkBack(formatDouble(info.getCommAmount()));
				}
				img.setTotalNum(formatDouble(info.getCommAmount()));
				storeInfos.put(key, img);
			} else {
				EmsBgExg img = (EmsBgExg) storeInfos.get(key);
				if (info.getBaseCustomsDeclaration().getImpExpType().equals(
						ImpExpType.DIRECT_EXPORT)) { // 出口
					img
							.setOutNum(jisuan(img.getOutNum(), info
									.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)) {// 转厂
					img.setChangeNum(jisuan(img.getOutNum(), info
							.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.REWORK_EXPORT)) {// 返工复出
					img.setReWorkBack(jisuan(img.getOutNum(), info
							.getCommAmount()));
				} else if (info.getBaseCustomsDeclaration().getImpExpType()
						.equals(ImpExpType.BACK_FACTORY_REWORK)) {// 减退厂返工
					img.setBackReWork(jisuanCutt(img.getOutNum(), info
							.getCommAmount()));
				}
				img
						.setTotalNum(jisuan(img.getTotalNum(), info
								.getCommAmount()));
			}

		}
		List list1 = new Vector(storeInfos.values());
		for (int i = 0; i < list1.size(); i++) {
			EmsBgExg exg = (EmsBgExg) list1.get(i);
			exg.setCompany(CommonUtils.getCompany());
			exg.setHead(head);
			this.emsAnalyDao.SaveEmsBgExg(exg);
		}
		return new Vector(storeInfos.values());
	}

	/**
	 * 报关数据分析--- 成品进出口---报关成品折料
	 * 
	 * @param head
	 * @return
	 */
	public List<EmsBgExgBg> convertBgExgToImg(EmsAnalyHead head, String emsBgExgId,
			boolean isAllOrSing) {
		long b = System.currentTimeMillis();
		System.out.println("0、convertBgExgToImg开始。");
		
		EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
		
		
		List<EmsBgExg> list = null;
		if (isAllOrSing) { // 当为true代表所有
			list = this.emsAnalyDao.findEmsBgExg(head);// findEmsBgExgBg
		} else {
			list = this.emsAnalyDao.findEmsBgExgByHeadIdOrBgExgId(head,
					emsBgExgId);
		}
		
		System.out.println("1、查询EmsBgExg数据完成。");
		
		
		Map<String, List<EmsHeadH2kBom>> bomMap = this.findBomMapByEmsBgExg(list, emsHeadH2k);
		
		
		System.out.println("2、查询BOM数据完成。");
		
		// 折料-计算-统计
		EmsBgExg exg = null;
		List<EmsBgExgBg> imgList = new ArrayList<EmsBgExgBg>();
		String key = "";
		for (int i = 0; i < list.size(); i++) {
			exg = list.get(i);
			key = exg.getSeqNum() + "/" + exg.getVersionNo();
			List<EmsHeadH2kBom> listImg = bomMap.get(key);// 获得BOM资料
			for (int j = 0; j < listImg.size(); j++) {
				EmsBgExgBg img = new EmsBgExgBg();
				EmsHeadH2kBom bom = (EmsHeadH2kBom) listImg.get(j);
				img.setCompany(CommonUtils.getCompany());
				img.setEmsNo(exg.getEmsNo());
				img.setHead(head);
				img.setExg(exg);
				img.setName(bom.getName());
				img.setSeqNum(Integer.valueOf(bom.getSeqNum()));
				img.setSpec(bom.getSpec());
				img.setUnit(bom.getUnit());
				img.setUnitWare(bom.getUnitWear());
				img.setWare(bom.getWear());
				img.setTotalWare(Double
						.valueOf((Dtod(bom.getUnitWear()) * Dtod(exg
								.getTotalNum()))
								/ (1 - Dtod(bom.getWear()) / 100)));
				imgList.add(img);
			}
		}
		System.out.println("3、折料完成。");
		
		this.emsAnalyDao.batchSaveOrUpdate(imgList);
		System.out.println("4、保存数据完成。");
		
		long e = System.currentTimeMillis();
		System.out.println("全部convertBgExgToImg折料时间：" + (e-b)/1000.0);
		
		return imgList;
	}
	
	public void convertBgExgToImg(EmsAnalyHead head){
		long beginTime = System.currentTimeMillis();
		Map<String,Unit> map = getUnitMap();
		long endTime = System.currentTimeMillis();
		System.out.println("查询单位用时："+(endTime-beginTime)/1000+"秒");
		beginTime = endTime;
		
		List<Object[]> list = emsAnalyDao.findEmsHeadH2kBom(head);
		endTime = System.currentTimeMillis();
		System.out.println("查询bom用时："+(endTime-beginTime)/1000+"秒");
		beginTime = endTime;
		
		List<EmsBgExgBg> listBom = new ArrayList<EmsBgExgBg>();
		for (int i = 0; i < list.size(); i++) {
			Object[] object = list.get(i);
			EmsBgExgBg img = new EmsBgExgBg();
			saveEmsBgExgBg(map,head,img,object);
			listBom.add(img);
		}
		endTime = System.currentTimeMillis();
		System.out.println("循环赋值用时："+(endTime-beginTime)/1000+"秒");
		beginTime = endTime;
		this.emsAnalyDao.batchSaveOrUpdate(listBom);
		endTime = System.currentTimeMillis();
		System.out.println("保存用时："+(endTime-beginTime)/1000+"秒");
	}
	
	public Map<String,Unit> getUnitMap(){
		List<Unit> list = emsAnalyDao.findUnit();
		Map<String,Unit> map = new HashMap<String, Unit>();
		for (int i = 0; i < list.size(); i++) {
			Unit unit = list.get(i);
			map.put(unit.getCode(), unit);
		}
		return map;
	}
	
	private void saveEmsBgExgBg(Map<String,Unit> map,EmsAnalyHead head,
			EmsBgExgBg img,Object[] object){
		img.setCompany(CommonUtils.getCompany());
		EmsBgExg exg = null;
		if(object[0]!=null){
			if(object[0] instanceof EmsBgExg){
				exg = (EmsBgExg)object[0];
			}
		}
		img.setEmsNo(exg.getEmsNo()==null?"":exg.getEmsNo().toString());
		img.setHead(head);
		img.setExg(exg);
		if(object[1]!=null){
			img.setSeqNum(Integer.valueOf(object[1].toString()));
		}
		
		if(object[2]!=null){
			img.setName(object[2].toString());
		}
		
		if(object[3]!=null){
			img.setSpec(object[3].toString());
		}
		
		if(object[4]!=null){
			img.setUnit(map.get(object[4].toString()));
		}
		if(object[5]!=null){
			img.setUnitWare(Double.valueOf(object[5].toString()));
		}
		
		if(object[6]!=null){
			img.setWare(Double.valueOf(object[6].toString()));
		}
		
		if(img.getUnitWare()!=null&&exg.getTotalNum()!=null
				&&img.getWare()!=null&&(1 - Dtod(img.getWare()))!=0){
			img.setTotalWare(Double.valueOf((Dtod(img.getUnitWare()) * Dtod(exg
					.getTotalNum()))/ (1 - Dtod(img.getWare()) / 100)));
		}
		
	}
	
	/**
	 * 查找bom数据并且组装成map
	 * @param list
	 * @param emsHeadH2k
	 * @return
	 */
	private Map<String, List<EmsHeadH2kBom>> findBomMapByEmsBgExg(List<EmsBgExg> list, EmsHeadH2k emsHeadH2k) {
		// 取得 list EmsBgExg 中的 备案序号, 版本号
		Map<Integer, Set<Integer>> seqNumMap = new HashMap<Integer, Set<Integer>>();
		Set<Integer> versionSet = null;
		EmsBgExg exg = null;
		for (int i = 0; i < list.size(); i++) {
			exg = list.get(i);
			versionSet = seqNumMap.get(exg.getSeqNum());
			
			if(versionSet == null) {
				versionSet = new HashSet<Integer>();
				seqNumMap.put(exg.getSeqNum(), versionSet);
			}
			versionSet.add(CommonUtils.isEmpty(exg.getVersionNo()) ? null
					: Integer.parseInt(exg.getVersionNo()));		
		}
		
		// 查询bom
		List<EmsHeadH2kBom> bomList = new ArrayList<EmsHeadH2kBom>();
		
		Iterator<Integer> it = seqNumMap.keySet().iterator();
		Integer prodeceSeqNum = null;
		while(it.hasNext()) {
			prodeceSeqNum = it.next();
			bomList.addAll(emsEdiTrDao.findEmsHeadH2kBomByProduceSeqNumAndVersionList(seqNumMap.get(prodeceSeqNum), prodeceSeqNum, emsHeadH2k));
		}
		
		// 把bom list变成map
		Map<String, List<EmsHeadH2kBom>> bomMap = new HashMap<String, List<EmsHeadH2kBom>>();
		CommonUtils.listToMap(bomList, bomMap, new GetKeyValueImpl<EmsHeadH2kBom>(){
			public String getKey(EmsHeadH2kBom e) {
				return e.getEmsHeadH2kExg().getSeqNum() + "/" + e.getEmsHeadH2kVersion().getVersion();
			}

			@SuppressWarnings("rawtypes")
			public void put(EmsHeadH2kBom e, Map map) {
				String key = getKey(e);
				
				List<EmsHeadH2kBom> tmpList = null;
				tmpList = (List<EmsHeadH2kBom>) map.get(key);
				if(tmpList == null) {
					tmpList = new ArrayList<EmsHeadH2kBom>();
					map.put(key, tmpList);
				}
				
				tmpList.add(e);
			}
			
		});
		
		return bomMap;
	}
	
	

	/**
	 * 盘点数据分析 ---成品折料-- 盘点成品折料
	 * 
	 * @param head
	 */
	public List<EmsPdExgImgBg> convertPdExgBgToPdExgImgBg(EmsAnalyHead head, List<EmsPdExgBg> list) {
		
		EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
		
		EmsPdExgBg pdExgBg = null;
		
		
		// 查询bom
		Map<Integer, List<EmsHeadH2kBom>> maxVersionBom = new HashMap<Integer, List<EmsHeadH2kBom>>(); // 保存成品最大版本 bomlist
		Map<String, List<EmsHeadH2kBom>> bomMap = findBomMapByEmsPdExgBg(list, emsHeadH2k, maxVersionBom);// 成品版本 bomlist
		
		List<EmsPdExgImgBg> arrayList = new ArrayList<EmsPdExgImgBg>();
		for (int i = 0; i < list.size(); i++) {
			pdExgBg = list.get(i);
			List<EmsHeadH2kBom> listBom = null;
			if (pdExgBg.getVersionNo() == null || "".equals(pdExgBg.getVersionNo())) {
				// 当成品的版本为空时最电子帐册中最大的版本号进行折算
				listBom = maxVersionBom.get(pdExgBg.getSeqNum());// 获得BOM资料
			} else {
				listBom = bomMap.get(pdExgBg.getSeqNum() + "/" + pdExgBg.getVersionNo());// 获得BOM资料
			}
			for (int j = 0; j < listBom.size(); j++) {
				EmsPdExgImgBg img = new EmsPdExgImgBg();
				EmsHeadH2kBom bom = listBom.get(j);
				img.setCompany(CommonUtils.getCompany());
				img.setEmsNo(pdExgBg.getEmsNo());
				img.setPdExgBg(pdExgBg);
				img.setHead(head);
				img.setName(bom.getName());
				img.setSeqNum(Integer.valueOf(bom.getSeqNum()));
				img.setSpec(bom.getSpec());
				img.setUnit(bom.getUnit());
				img.setUnitWare(bom.getUnitWear());
				img.setWare(bom.getWear());
				img.setTotalWate(Double
						.valueOf((Dtod(bom.getUnitWear()) * Dtod(pdExgBg
								.getTotalNum()))
								/ (1 - Dtod(bom.getWear()) / 100)));

				this.emsAnalyDao.saveOrUpdate(img);
				arrayList.add(img);
			}
		}
		return arrayList;
	}
	
	
	/**
	 * 查找bom数据并且组装成map
	 * @param list
	 * @param emsHeadH2k
	 * @return
	 */
	public Map<String, List<EmsHeadH2kBom>> findBomMapByEmsPdExgBg(List<EmsPdExgBg> list, EmsHeadH2k emsHeadH2k, Map<Integer, List<EmsHeadH2kBom>> maxVersionBom) {
		// 取得 list EmsBgExg 中的 备案序号, 版本号
		Map<Integer, Set<Integer>> seqNumMap = new HashMap<Integer, Set<Integer>>();
		Set<Integer> versionSet = null;
		EmsPdExgBg exg = null;
		for (int i = 0; i < list.size(); i++) {
			exg = list.get(i);
			versionSet = seqNumMap.get(exg.getSeqNum());
			
			if(versionSet == null) {
				versionSet = new HashSet<Integer>();
				seqNumMap.put(exg.getSeqNum(), versionSet);
			}
			versionSet.add(CommonUtils.isEmpty(exg.getVersionNo()) ? null
					: Integer.parseInt(exg.getVersionNo()));		
		}
		
		// 查询bom
		List<EmsHeadH2kBom> bomList = new ArrayList<EmsHeadH2kBom>();
		
		Iterator<Integer> it = seqNumMap.keySet().iterator();
		Integer prodeceSeqNum = null;
		while(it.hasNext()) {
			prodeceSeqNum = it.next();
			bomList.addAll(emsEdiTrDao.findEmsHeadH2kBomByProduceSeqNumAndVersionList(seqNumMap.get(prodeceSeqNum), prodeceSeqNum, emsHeadH2k));
		}
		
		// 把bom list变成map
		Map<String, List<EmsHeadH2kBom>> bomMap = new HashMap<String, List<EmsHeadH2kBom>>();
		String key = "";
		EmsHeadH2kBom e = null;
		List<EmsHeadH2kBom> tmpList = null;
		List<EmsHeadH2kBom> tmpList1 = null;
		for (int i = 0; i < bomList.size(); i++) {
			e = bomList.get(i);
			key = e.getEmsHeadH2kExg().getSeqNum() + "/" + e.getEmsHeadH2kVersion().getVersion();
			
			
			tmpList = bomMap.get(key);
			if(tmpList == null) {
				tmpList = new ArrayList<EmsHeadH2kBom>();
				bomMap.put(key, tmpList);
				
				// 比较版本，看是否是更大版本，如果是更大版本或者以前为空就更新
				tmpList1 = maxVersionBom.get(e.getEmsHeadH2kExg().getSeqNum());
				if(tmpList1 == null || tmpList1.size() == 0) {
					maxVersionBom.put(e.getEmsHeadH2kExg().getSeqNum(), tmpList);
				} else if(e.getEmsHeadH2kVersion().getVersion() == null) {
					continue;
				} else if(tmpList1.get(0).getEmsHeadH2kVersion().getVersion() == null
						|| tmpList1.get(0).getEmsHeadH2kVersion().getVersion() > e.getEmsHeadH2kVersion().getVersion()) {
					maxVersionBom.put(e.getEmsHeadH2kExg().getSeqNum(), tmpList);
				}
			}
			
			tmpList.add(e);
		}
		
		return bomMap;
	}

	// 报关进出口总数
	public List<EmsBgTotal> findBgExgImg(EmsAnalyHead head) {
		List<EmsBgImg> listIn = this.emsAnalyDao.findEmsBgImg(head); // 所有进口
		List<EmsBgExgBg> listout = this.emsAnalyDao.findEmsBgExgImgAll(head);// 所有出口
		Map<String, EmsBgTotal> storeInfos = new HashMap<String, EmsBgTotal>();
		for (int i = 0; i < listIn.size(); i++) {
			EmsBgImg info = (EmsBgImg) listIn.get(i);
			String seqNum = String.valueOf(info.getSeqNum());
			String name = info.getName();
			String spec = info.getSpec();
			String unit = info.getUnit() == null ? null : info.getUnit()
					.getCode();
			String key = seqNum + name + spec + unit;
			if (storeInfos.get(key) == null) {
				EmsBgTotal img = new EmsBgTotal();
				img.setEmsNo(info.getEmsNo());
				img.setSeqNum(info.getSeqNum());
				img.setName(info.getName());
				img.setSpec(info.getSpec());
				img.setUnit(info.getUnit());
				img.setInNum(info.getTotalNum());
				img.setExgWare(Double.valueOf(0));
				img.setBalance(info.getTotalNum());
				storeInfos.put(key, img);
			} else {
				EmsBgTotal img = (EmsBgTotal) storeInfos.get(key);
				img.setInNum(jisuan(img.getInNum(), info.getTotalNum()));
				img.setBalance(jisuan(img.getBalance(), info.getTotalNum()));
			}

		}

		for (int i = 0; i < listout.size(); i++) {
			EmsBgExgBg info = (EmsBgExgBg) listout.get(i);
			String seqNum = String.valueOf(info.getSeqNum());
			String name = info.getName();
			String spec = info.getSpec();
			String unit = info.getUnit() == null ? null : info.getUnit()
					.getCode();
			String key = seqNum + name + spec + unit;
			if (storeInfos.get(key) == null) {
				EmsBgTotal img = new EmsBgTotal();
				img.setEmsNo(info.getEmsNo());
				img.setSeqNum(info.getSeqNum());
				img.setName(info.getName());
				img.setSpec(info.getSpec());
				img.setUnit(info.getUnit());
				img.setInNum(Double.valueOf(0));
				img.setExgWare(info.getTotalWare());
				img.setBalance(jisuanCutt(Double.valueOf(0), info
						.getTotalWare()));
				storeInfos.put(key, img);
			} else {
				EmsBgTotal img = (EmsBgTotal) storeInfos.get(key);
				img.setExgWare(jisuan(img.getExgWare(), info.getTotalWare()));
				img
						.setBalance(jisuanCutt(img.getBalance(), info
								.getTotalWare()));
			}

		}
		
		
		List<EmsBgTotal> list = new ArrayList<EmsBgTotal>(storeInfos.values());
		for (int i = 0; i < list.size(); i++) {
			EmsBgTotal img = (EmsBgTotal) list.get(i);
			img.setHead(head);
			img.setCompany(CommonUtils.getCompany());
			this.emsAnalyDao.SaveEmsBgTotal(img);
		}

		return list;
	}

	/**
	 * 盘点返回报关料件资料
	 */
	public List<String> convertPdImgBg(EmsAnalyHead head, List<EmsPdImg> imgList) {
		
		List<String> noMergePtNos = new ArrayList<String>();
		
		// 获得电子账册
		EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
		
		// 获得盘点list中的料号
		Set<String> ptNoSet = new HashSet<String>();
		for (int i = 0; i < imgList.size(); i++) {
			ptNoSet.add(imgList.get(i).getPtNo());
		}
		
		List<InnerMergeData> innerDataList = this.emsAnalyDao.findMaterialInnerMergerByPtNo(ptNoSet, emsHeadH2k);
		Map<String, InnerMergeData> innerDataMap = CommonUtils.listToMap(
				innerDataList, new GetKeyValueImpl<InnerMergeData>() {
					@Override
					public String getKey(InnerMergeData e) {
						return e.getMateriel().getPtNo();
					}
				});
		
		EmsPdImgBg imgBg = null;
		InnerMergeData inner = null;
		EmsPdImg pdImg = null;
		for (int j = 0; j < imgList.size(); j++) {
			imgBg = new EmsPdImgBg();
			pdImg = imgList.get(j);
			inner = innerDataMap.get(pdImg.getPtNo());
			if(inner == null) {
				// throw new RuntimeException("料号：" + pdImg.getPtNo() + "，没有归并关系！或者没有内部归并！");
				noMergePtNos.add(pdImg.getPtNo());
				System.out.println(pdImg.getPtNo());
				continue;
				
			}
			imgBg.setEmsNo(emsHeadH2k.getEmsNo());
			imgBg.setCompany(CommonUtils.getCompany());
			imgBg.setSeqNum(inner.getHsAfterTenMemoNo());
			imgBg.setName(inner.getHsAfterMaterielTenName());
			imgBg.setSpec(inner.getHsAfterMaterielTenSpec());
			imgBg.setUnit(inner.getHsAfterMemoUnit());
			imgBg.setConvertNUm(CommonUtils.getDoubleExceptNull(inner
					.getMateriel().getUnitConvert()));
			imgBg.setTotalNum(CommonUtils.getDoubleExceptNull(pdImg
					.getPdNum())
					* imgBg.getConvertNUm());
			imgBg.setPdImg(pdImg);
			imgBg.setHead(head);
			this.emsAnalyDao.SaveEmsPdImgBg(imgBg);
		}
		
		return noMergePtNos;
	}

	/**
	 * 盘点返回报关成品资料
	 */
	public void findPdExg(EmsAnalyHead head, boolean isMaxVersion, EmsPdExg exg) {
		// List list = this.emsAnalyDao.findEmsPdExg(head);
		// for (int i = 0; i < list.size(); i++) {
		// EmsPdExg exg = (EmsPdExg) list.get(i);

		Map<Integer, Integer> mapEmsHeadH2kVersion = new HashMap<Integer, Integer>();
		List<Integer> lineSeqNum = new ArrayList();
		List listBgImg = this.emsAnalyDao.findBgImgFromInnerMerger(
				MaterielType.FINISHED_PRODUCT, exg.getPtNo(), "EXG");
		if (isMaxVersion) {
			Integer maxVersion = null;
			for (int j = 0; j < listBgImg.size(); j++) {
				Object[] obj = (Object[]) listBgImg.get(j);
				EmsHeadH2kVersion h2kImg = (EmsHeadH2kVersion) obj[1];
				Integer seqNum = h2kImg.getEmsHeadH2kExg().getSeqNum();

				if (!lineSeqNum.contains(seqNum)) {
					maxVersion = h2kImg.getVersion();
					lineSeqNum.add(seqNum);
				}
				if (maxVersion < h2kImg.getVersion()) {
					maxVersion = h2kImg.getVersion();
				}
				mapEmsHeadH2kVersion.put(seqNum, maxVersion);

			}
		}
		Integer maxVersion = null;
		for (int j = 0; j < listBgImg.size(); j++) {
			Object[] obj = (Object[]) listBgImg.get(j);
			InnerMergeData inner = (InnerMergeData) obj[0];
			EmsHeadH2kVersion h2kImg = (EmsHeadH2kVersion) obj[1];
			Integer seqNum = h2kImg.getEmsHeadH2kExg().getSeqNum();
			if (isMaxVersion) {
				if (mapEmsHeadH2kVersion.get(seqNum) == null) {
					continue;
				}
				if (mapEmsHeadH2kVersion.get(seqNum) != null
						&& mapEmsHeadH2kVersion.get(seqNum) != h2kImg
								.getVersion()) {
					continue;
				}
				maxVersion = mapEmsHeadH2kVersion.get(seqNum);
			} else {
				maxVersion = h2kImg.getVersion() != null ? h2kImg.getVersion()
						: null;
			}
			EmsPdExgBg exgBg = new EmsPdExgBg();
			exgBg.setEmsNo(h2kImg.getEmsHeadH2k().getEmsNo());
			exgBg.setCompany(CommonUtils.getCompany());
			exgBg.setSeqNum(inner.getHsAfterTenMemoNo());
			exgBg.setName(inner.getHsAfterMaterielTenName());
			exgBg.setSpec(inner.getHsAfterMaterielTenSpec());
			exgBg.setUnit(inner.getHsAfterMemoUnit());
			exgBg.setConvertNUm(CommonUtils.getDoubleExceptNull(inner
					.getMateriel().getUnitConvert()));
			exgBg.setTotalNum(CommonUtils.getDoubleExceptNull(exg
					.getPdNum())
					* exgBg.getConvertNUm());
			exgBg.setVersionNo(String.valueOf(maxVersion));
			exgBg.setHead(head);
			exgBg.setPdExg(exg);
			this.emsAnalyDao.SaveEmsPdExgBg(exgBg);
			// }

		}
	}

	/**
	 * 统计盘点分析总表
	 * 
	 * @param head
	 * @return
	 */
	/**
	 * @param head
	 * @return
	 */
	public List<EmsPdTotal> findPdExgImg(EmsAnalyHead head) {
		List<EmsPdImgBg> listIn = this.emsAnalyDao.findEmsPdImgBgAll(head); // 盘点料件
		List<EmsPdExgImgBg> listout = this.emsAnalyDao.findEmsPdExgImgBg(head);// 成品折算料件
		List<EmsTransFactoryBg> listTrans = this.emsAnalyDao.findEmsTransFactoryBg(head);//转厂盘点
		
		Map<String, EmsPdTotal> storeInfos = new HashMap<String, EmsPdTotal>();
		EmsPdTotal pdTotal = null;
		for (int i = 0; i < listIn.size(); i++) {
			EmsPdImgBg info = listIn.get(i);
			String seqNum = String.valueOf(info.getSeqNum());
			String name = info.getName();
			String spec = info.getSpec();
			String unit = info.getUnit().getCode();
			String key = seqNum + name + spec + unit;
			if (storeInfos.get(key) == null) {
				pdTotal = new EmsPdTotal();
				pdTotal.setEmsNo(info.getEmsNo());
				pdTotal.setSeqNum(info.getSeqNum());
				pdTotal.setName(info.getName());
				pdTotal.setSpec(info.getSpec());
				pdTotal.setUnit(info.getUnit());
				pdTotal.setImgNum(info.getTotalNum());
				pdTotal.setExgNum(Double.valueOf(0));
				pdTotal.setTotalNum(info.getTotalNum());
				storeInfos.put(key, pdTotal);
			} else {
				pdTotal = storeInfos.get(key);
				pdTotal.setImgNum(jisuan(pdTotal.getImgNum(), info.getTotalNum()));
				pdTotal.setTotalNum(jisuan(pdTotal.getTotalNum(), info.getTotalNum()));
			}

		}

		for (int i = 0; i < listout.size(); i++) {
			EmsPdExgImgBg info = listout.get(i);
			String seqNum = String.valueOf(info.getSeqNum());
			String name = info.getName();
			String spec = info.getSpec();
			String unit = info.getUnit().getCode();
			String key = seqNum + name + spec + unit;
			if (storeInfos.get(key) == null) {
				pdTotal = new EmsPdTotal();
				pdTotal.setEmsNo(info.getEmsNo());
				pdTotal.setSeqNum(info.getSeqNum());
				pdTotal.setName(info.getName());
				pdTotal.setSpec(info.getSpec());
				pdTotal.setUnit(info.getUnit());
				pdTotal.setImgNum(Double.valueOf(0));
				pdTotal.setExgNum(Dtod(info.getTotalWate()));
				pdTotal.setTotalNum(Dtod(info.getTotalWate()));
				storeInfos.put(key, pdTotal);
			} else {
				pdTotal = storeInfos.get(key);
				pdTotal.setExgNum(jisuan(pdTotal.getExgNum(), info.getTotalWate()));
				pdTotal.setTotalNum(jisuan(pdTotal.getTotalNum(), info.getTotalWate()));
			}

		}
		
		
		for (int i = 0; i < listTrans.size(); i++) {
			EmsTransFactoryBg info = listTrans.get(i);
			String seqNum = String.valueOf(info.getSeqNum());
			String name = info.getName();
			String spec = info.getSpec();
			String unit = info.getUnit().getCode();
			String key = seqNum + name + spec + unit;
			if (storeInfos.get(key) == null) {
				pdTotal = new EmsPdTotal();
				pdTotal.setEmsNo(info.getEmsNo());
				pdTotal.setSeqNum(info.getSeqNum());
				pdTotal.setName(info.getName());
				pdTotal.setSpec(info.getSpec());
				pdTotal.setUnit(info.getUnit());
				pdTotal.setImgNum(0.0);
				pdTotal.setExgNum(0.0);
				pdTotal.setUnTransferNum(Dtod(info.getUnTransferNum()));
				pdTotal.setUnReceiveNum(Dtod(info.getUnReceiveNum()));
				pdTotal.setTotalNum(pdTotal.getUnReceiveNum() - pdTotal.getUnTransferNum());
				storeInfos.put(key, pdTotal);
			} else {
				pdTotal = storeInfos.get(key);
				pdTotal.setUnTransferNum(jisuan(pdTotal.getUnTransferNum(), info.getUnTransferNum()));
				pdTotal.setUnReceiveNum(jisuan(pdTotal.getUnReceiveNum(), info.getUnReceiveNum()));
				pdTotal.setTotalNum(pdTotal.getImgNum() 
						+ pdTotal.getExgNum()
						- pdTotal.getUnTransferNum()
						+ pdTotal.getUnReceiveNum());
			}

		}
		
		List<EmsPdTotal> list = new ArrayList<EmsPdTotal>(storeInfos.values());
		for (int i = 0; i < list.size(); i++) {
			pdTotal = (EmsPdTotal) list.get(i);
			pdTotal.setHead(head);
			pdTotal.setCompany(CommonUtils.getCompany());
		}
		
		this.emsAnalyDao.batchSaveOrUpdate(list);

		return new ArrayList<EmsPdTotal>(storeInfos.values());
	}

	// 差异分析总表
	public List<EmsCy> calculateEmsCy(EmsAnalyHead head) {
		// 取得当前帐号
		EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
		
		List<EmsBgTotal> listIn = this.emsAnalyDao.findEmsBgTotal(head); // 帐面结余 (报关)
		List<EmsPdTotal> listout = this.emsAnalyDao.findEmsPdTotal(head);// 实物库存 （盘点）
		
		Map<String, EmsCy> storeInfos = new HashMap<String, EmsCy>();
		for (int i = 0; i < listIn.size(); i++) {
			EmsBgTotal info = listIn.get(i);
			String seqNum = String.valueOf(info.getSeqNum());
			String name = info.getName();
			String spec = info.getSpec();
			String unit = info.getUnit().getCode();
			String key = seqNum + "@/@" + name + "@/@" + spec + "@/@" + unit;
			if (storeInfos.get(key) == null) {
				EmsCy img = new EmsCy();
				img.setEmsNo(info.getEmsNo());
				img.setSeqNum(info.getSeqNum());
				img.setName(info.getName());
				img.setSpec(info.getSpec());
				img.setUnit(info.getUnit());
				img.setEmsBalance(info.getBalance());
				img.setFactNum(Double.valueOf(0));
				img.setCyNum(info.getBalance());
				storeInfos.put(key, img);
			} else {
				EmsCy img = storeInfos.get(key);
				img
						.setEmsBalance(jisuan(img.getEmsBalance(), info
								.getBalance()));
				img.setCyNum(jisuan(img.getCyNum(), info.getBalance()));
			}

		}

		for (int i = 0; i < listout.size(); i++) {
			EmsPdTotal info = listout.get(i);
			String seqNum = String.valueOf(info.getSeqNum());
			String name = info.getName();
			String spec = info.getSpec();
			String unit = info.getUnit().getCode();
			String key = seqNum + "@/@" + name + "@/@" + spec + "@/@" + unit;
			if (storeInfos.get(key) == null) {
				EmsCy img = new EmsCy();
				img.setEmsNo(info.getEmsNo());
				img.setSeqNum(info.getSeqNum());
				img.setName(info.getName());
				img.setSpec(info.getSpec());
				img.setUnit(info.getUnit());
				img.setEmsBalance(Double.valueOf(0));
				img.setFactNum(info.getTotalNum());
				img.setCyNum(-info.getTotalNum());
				storeInfos.put(key, img);
			} else {
				EmsCy img = storeInfos.get(key);
				img.setFactNum(jisuan(img.getFactNum(), info.getTotalNum()));
				img.setCyNum(jisuanCutt(img.getCyNum(), info.getTotalNum()));
			}

		}
		
		
		// 查询自用商品编码
		List<EmsHeadH2kImg> imgList = emsEdiTrDao.findEmsHeadH2kImg(emsHeadH2k);
		Map<String, Double> rateMap = CommonUtils.listToMap(imgList, new GetKeyValueImpl<EmsHeadH2kImg>(){

			public String getKey(EmsHeadH2kImg e) {
				return e.getSeqNum().toString();
			}

			public Object getValue(EmsHeadH2kImg e) {
				if(CommonUtils.isEmpty(e.getComplex().getLowrate())){
					return 0.0;
				}
				return new Double(e.getComplex().getLowrate());
			}
			
		});
		
		
		// 查询核销时间内的最新进口料件报关单单价（排除退厂返工）
		List<Object[]> priceList = emsAnalyDao.findPriceFromCustomsDeclaration(head);
		Map<Integer, Double> priceMap = new HashMap<Integer, Double>();
		Object[] objs = null;
		for (int i = 0; i < priceList.size(); i++) {
			objs = priceList.get(i);
			if(priceMap.get(objs[0]) == null){
				priceMap.put((Integer)objs[0], (Double) objs[1]);
			}
		}
		
		
		List<EmsCy> list = new ArrayList<EmsCy>(storeInfos.values());
		for (int i = 0; i < list.size(); i++) {
			EmsCy img = (EmsCy) list.get(i);
			img.setHead(head);
			img.setPrice(priceMap.get(img.getSeqNum()));
			img.setCompany(CommonUtils.getCompany());
			
			// 关税
			img.setUsd(CommonUtils.getDoubleExceptNull(img.getCyNum())
					* CommonUtils.getDoubleExceptNull(img.getPrice())
					* CommonUtils.getDoubleExceptNull(rateMap.get(img.getSeqNum().toString())));
			// 增值税
			img.setUsdAdd((CommonUtils.getDoubleExceptNull(img.getCyNum()) 
					* CommonUtils.getDoubleExceptNull(img.getPrice()) + img.getUsd()) * 0.17);
			
//			System.out.println("seq:" + img.getSeqNum() + ",rate:" + rateMap.get(img.getSeqNum().toString())
//					+ ",cyNum:" + img.getCyNum() + ",price:" + img.getPrice() + ",usd:" + img.getUsd()
//					+ ",usdAdd:" + img.getUsdAdd());
		}
		
		
		this.emsAnalyDao.batchSaveOrUpdate(list);

		return new ArrayList<EmsCy>(storeInfos.values());
	}

	// 新增料件盘点
	public List addEmsLjPd(List list, EmsAnalyHead head) {
		List alist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Materiel m = (Materiel) list.get(i);
			EmsPdImg pdimg = new EmsPdImg();
			pdimg.setHead(head);
			pdimg.setPtNo(m.getPtNo());
			pdimg.setPtName(m.getFactoryName());
			pdimg.setPtSpec(m.getFactorySpec());
			pdimg.setCalUnit(m.getCalUnit());
			emsAnalyDao.SaveEmsPdImg(pdimg);
			alist.add(pdimg);
		}
		return alist;
	}

	// 新增料件盘点
	public List addEmsCpPd(List list, EmsAnalyHead head) {
		List alist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Materiel m = (Materiel) list.get(i);
			EmsPdExg pdimg = new EmsPdExg();
			pdimg.setHead(head);
			pdimg.setPtNo(m.getPtNo());
			pdimg.setPtName(m.getFactoryName());
			pdimg.setPtSpec(m.getFactorySpec());
			pdimg.setCalUnit(m.getCalUnit());
			emsAnalyDao.SaveEmsPdExg(pdimg);
			alist.add(pdimg);
		}
		return alist;
	}

	// 中期工厂核查
	public List importDataToFactoryCheck(List list, String materielType,
			CheckParameter head) {
		int totalNum = 0; // 总数目
		int notInMaterielNum = 0; // 不在物料主档中数目
		int changeNum = 0; // 更新核查数目
		int newNum = 0; // 新增数目
		List existMateriel = new ArrayList();
		totalNum = (list == null) ? 0 : list.size();
		List sList = new ArrayList();
		// ------------------------------初始化物料--------------------------------------
		Map<String, Materiel> existMtMap = new HashMap<String, Materiel>();
		List existMtList = this.emsAnalyDao.findAllMateriel();
		for (int i = 0, n = existMtList.size(); i < n; i++) {
			Materiel mt = (Materiel) existMtList.get(i);
			String key = mt.getPtNo().trim();
			if (key == null || "".equals(key)) {
				continue;
			}
			existMtMap.put(key, mt);
		}
		// ----------------------------------------------------------------------------

		if (materielType.equals(MaterielType.MATERIEL)) { // 料件
			// ------------------------------------------------------------------
			Map<String, FactoryCheckImg> existCheckImgMap = new HashMap<String, FactoryCheckImg>();
			List existCheckList = this.checkCancelDao.findFactoryCheckImg(head);
			for (int i = 0, n = existCheckList.size(); i < n; i++) {
				FactoryCheckImg img = (FactoryCheckImg) existCheckList.get(i);
				String key = img.getPtNo().trim();
				if (key == null || "".equals(key)) {
					continue;
				}
				existCheckImgMap.put(key, img);
			}
			// -----------------加载归并关系归并前料件--------------------------------------------
			Map<String, EmsEdiMergerImgBefore> existEmsMergerImgBeforeMap = new HashMap<String, EmsEdiMergerImgBefore>();
			List existEmsMergerImgBeforeList = this.emsAnalyDao
					.findEmsEdiMergerImgBefore();
			for (int i = 0, n = existEmsMergerImgBeforeList.size(); i < n; i++) {
				EmsEdiMergerImgBefore temp = (EmsEdiMergerImgBefore) existEmsMergerImgBeforeList
						.get(i);
				String key = temp.getPtNo().trim();
				if (key == null || "".equals(key)) {
					continue;
				}
				existEmsMergerImgBeforeMap.put(key, temp);
			}
			// ----------------------------------------------------------------
			List<FactoryCheckImg> alist = new ArrayList<FactoryCheckImg>();
			for (int i = 0; i < list.size(); i++) {
				ImportDataForFactoryPd obj = (ImportDataForFactoryPd) list
						.get(i);
				String ptNo = obj.getPtNo() == null ? "" : obj.getPtNo().trim();
				if (existEmsMergerImgBeforeMap.get(ptNo) != null) {
					FactoryCheckImg img = null;
					if (existCheckImgMap.get(ptNo) == null) {// 不存在
						EmsEdiMergerImgBefore imgbefore = (EmsEdiMergerImgBefore) existEmsMergerImgBeforeMap
								.get(ptNo);
						img = new FactoryCheckImg();
						img.setPtNo(imgbefore.getPtNo());
						img.setName(imgbefore.getName());
						img.setSpec(imgbefore.getSpec());
						img.setUnit(imgbefore.getUnit());
						newNum++;
					} else {// 存在
						img = (FactoryCheckImg) existCheckImgMap.get(ptNo);
						changeNum++;
					}
					img.setMaterStockNum(obj.getMaterStockNum());// 工厂库存数量
					if (existMtMap.get(ptNo) != null) {
						Materiel mt = existMtMap.get(ptNo);
						img.setCalUnit(mt.getCalUnit());// 工厂单位
						Double unitRate = (mt.getUnitConvert() == null || Double
								.valueOf(0).equals(mt.getUnitConvert())) ? Double
								.valueOf(1)
								: mt.getUnitConvert();// 折算系数
						img.setBgNum(formatDouble(obj.getMaterStockNum())
								* unitRate);// 折算报关数量
					}
					alist.add(img);
				} else {
					existMateriel.add(obj);
				}
			}
			sList.add(alist);
		} else { // 成品
			// ------------------------------------------------------------------
			Map<String, FactoryCheckExg> existCheckExgMap = new HashMap<String, FactoryCheckExg>();
			List existCheckList = this.checkCancelDao.findFactoryCheckExg(head);
			for (int i = 0, n = existCheckList.size(); i < n; i++) {
				FactoryCheckExg exg = (FactoryCheckExg) existCheckList.get(i);
				String key = exg.getPtNo().trim() + "/" + exg.getVersion() == null ? ""
						: exg.getVersion().trim();
				if (key == null || "".equals(key)) {
					continue;
				}
				existCheckExgMap.put(key, exg);
			}
			// ------------------------------------------------------------------
			// -----------------加载归并关系归并前成品--------------------------------------------

			Map<String, EmsEdiMergerVersion> existEmsMergerVersionMap = new HashMap<String, EmsEdiMergerVersion>();
			List existEmsMergerVersionList = this.emsAnalyDao
					.findEmsEdiMergerExgBefore();
			for (int i = 0, n = existEmsMergerVersionList.size(); i < n; i++) {
				EmsEdiMergerVersion temp = (EmsEdiMergerVersion) existEmsMergerVersionList
						.get(i);
				String key = (temp.getEmsEdiMergerBefore().getPtNo().trim())
						+ "/"
						+ (temp.getVersion() == null ? "" : temp.getVersion());
				if (key == null || "".equals(key)) {
					continue;
				}
				existEmsMergerVersionMap.put(key, temp);
			}
			// -------------------------------------------------------------------------
			List<FactoryCheckExg> rightlist = new ArrayList<FactoryCheckExg>();
			for (int i = 0; i < list.size(); i++) {
				ImportDataForFactoryPd obj = (ImportDataForFactoryPd) list
						.get(i);
				String ptNo = obj.getPtNo() == null ? "" : obj.getPtNo().trim();
				String version = obj.getVersion() == null ? "" : obj
						.getVersion().trim();
				if (existEmsMergerVersionMap.get(ptNo + "/" + version) != null) {
					FactoryCheckExg exg = null;
					if (existCheckExgMap.get(ptNo + "/" + version) == null) {// 不存在
						EmsEdiMergerVersion temp = (EmsEdiMergerVersion) existEmsMergerVersionMap
								.get(ptNo + "/" + version);
						exg = new FactoryCheckExg();
						exg.setPtNo(temp.getEmsEdiMergerBefore().getPtNo());
						exg.setName(temp.getEmsEdiMergerBefore().getName());
						exg.setSpec(temp.getEmsEdiMergerBefore().getSpec());
						exg.setVersion(version);// 文本导入的版本号
						exg.setUnit(temp.getEmsEdiMergerBefore().getUnit());
						newNum++;
					} else { // 存在
						exg = (FactoryCheckExg) existCheckExgMap.get(ptNo + "/"
								+ version);
						changeNum++;
					}
					exg.setMaterStockNum(obj.getMaterStockNum());
					if (existMtMap.get(ptNo) != null) {
						Materiel mt = existMtMap.get(ptNo);
						exg.setCalUnit(mt.getCalUnit());// 工厂单位
						Double unitRate = (mt.getUnitConvert() == null || Double
								.valueOf(0).equals(mt.getUnitConvert())) ? Double
								.valueOf(1.0)
								: mt.getUnitConvert();// 折算系数
						exg.setBgNum(formatDouble(obj.getMaterStockNum())
								* unitRate);// 折算报关数量
					}
					rightlist.add(exg);
				} else {
					existMateriel.add(obj);
				}
			}
			sList.add(rightlist); // get(0);//新增或更新
		}
		notInMaterielNum = (existMateriel == null) ? 0 : existMateriel.size();
		sList.add(existMateriel);// get(1);//未在归并关系归并前
		BillTemp b = new BillTemp();
		b.setBill1(String.valueOf(totalNum));
		b.setBill2(String.valueOf(notInMaterielNum));
		b.setBill3(String.valueOf(changeNum));
		b.setBill4(String.valueOf(newNum));
		sList.add(b);

		return sList;
	}

	public void saveDataToFactoryCheck(List list, CheckParameter head,
			String materielType) {
		if (materielType.equals(MaterielType.MATERIEL)) {
			for (int i = 0; i < list.size(); i++) {
				FactoryCheckImg img = (FactoryCheckImg) list.get(i);
				img.setCompany(CommonUtils.getCompany());
				img.setHead(head);
				this.checkCancelDao.saveFactoryCheckImg(img);
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				FactoryCheckExg img = (FactoryCheckExg) list.get(i);
				img.setCompany(CommonUtils.getCompany());
				img.setHead(head);
				this.checkCancelDao.saveFactoryCheckExg(img);
			}
		}
	}

	public List importDataToCheck(List list, CheckHead head) {
		int totalNum = 0; // 总数目
		int notInMaterielNum = 0; // 不在物料主档中数目
		int changeNum = 0; // 更新核查数目
		int newNum = 0; // 新增数目
		List existList = new ArrayList();
		totalNum = (list == null) ? 0 : list.size();
		// -----------------加载归并关系归并前料件--------------------------------------------
		List sList = new ArrayList();
		Map<String, EmsEdiMergerImgBefore> existMaterielMap = new HashMap<String, EmsEdiMergerImgBefore>();
		List existMaterielList = this.emsAnalyDao.findEmsEdiMergerImgBefore();
		for (int i = 0, n = existMaterielList.size(); i < n; i++) {
			EmsEdiMergerImgBefore temp = (EmsEdiMergerImgBefore) existMaterielList
					.get(i);
			String key = temp.getPtNo().trim();
			if (key == null || "".equals(key)) {
				continue;
			}
			existMaterielMap.put(key, temp);
		}

		// ----------------加载核查料件-------------------------------------------------------
		Map<String, CheckImg> existCheckImgMap = new HashMap<String, CheckImg>();
		List existCheckList = this.checkCancelDao.findCheckImg(head);
		for (int i = 0, n = existCheckList.size(); i < n; i++) {
			CheckImg img = (CheckImg) existCheckList.get(i);
			String key = img.getPtNo().trim();
			if (key == null || "".equals(key)) {
				continue;
			}
			existCheckImgMap.put(key, img);
		}
		// --------------------------------------------------------------
		List<CheckImg> alist = new ArrayList<CheckImg>();
		int n = list.size();
		for (int i = 0; i < n; i++) {
			ImportDataForCheckImg obj = (ImportDataForCheckImg) list.get(i);
			String ptNo = obj.getPtNo() == null ? "" : obj.getPtNo().trim();
			if (existMaterielMap.get(ptNo) != null) {
				EmsEdiMergerImgBefore imgbefore = existMaterielMap.get(ptNo);
				CheckImg img = null;
				if (existCheckImgMap.get(ptNo) == null) {// 不存在
					img = new CheckImg();
					img.setPtNo(imgbefore.getPtNo());
					img.setName(imgbefore.getName());
					img.setSpec(imgbefore.getSpec());
					img.setSeqNum(String.valueOf(imgbefore
							.getEmsEdiMergerImgAfter().getSeqNum()));
					img.setComplex(imgbefore.getEmsEdiMergerImgAfter()
							.getComplex());
					img.setUnit(imgbefore.getEmsEdiMergerImgAfter().getUnit());
					newNum++;
				} else {// 存在
					img = (CheckImg) existCheckImgMap.get(ptNo);
					changeNum++;
				}
				img.setMaterStock(obj.getMaterStock());
				img.setMaterByWay(obj.getMaterByWay());
				img.setPassExgUse(obj.getPassExgUse());
				img.setOnlines(obj.getOnlines());
				img.setOtherConvert(obj.getOtherConvert());
				img.setDepose(obj.getDepose());
				img.setTurnInNoReport(obj.getTurnInNoReport());
				alist.add(img);
			} else {
				existList.add(obj);
			}
		}
		sList.add(alist);// get(0);
		notInMaterielNum = (existList == null) ? 0 : existList.size();
		sList.add(existList);// get(1);//未在物料主档
		BillTemp b = new BillTemp();
		b.setBill1(String.valueOf(totalNum));
		b.setBill2(String.valueOf(notInMaterielNum));
		b.setBill3(String.valueOf(changeNum));
		b.setBill4(String.valueOf(newNum));
		sList.add(b);

		return sList;
	}

	public void saveDataToCheck(List list, CheckHead head) {
		for (int i = 0; i < list.size(); i++) {
			CheckImg img = (CheckImg) list.get(i);
			img.setCompany(CommonUtils.getCompany());
			img.setCheckHead(head);
			this.checkCancelDao.saveCheckImg(img);
		}
	}

	public List convertBgImg(List list, CheckParameter head) {
		// -----------------加载归并关系归并前料件--------------------------------------------
		Map<String, EmsEdiMergerImgBefore> existEmsMergerImgBeforeMap = new HashMap<String, EmsEdiMergerImgBefore>();
		List existEmsMergerImgBeforeList = this.emsAnalyDao
				.findEmsEdiMergerImgBefore();
		for (int i = 0, n = existEmsMergerImgBeforeList.size(); i < n; i++) {
			EmsEdiMergerImgBefore temp = (EmsEdiMergerImgBefore) existEmsMergerImgBeforeList
					.get(i);
			String key = temp.getPtNo().trim();
			if (key == null || "".equals(key)) {
				continue;
			}
			existEmsMergerImgBeforeMap.put(key, temp);
		}
		List<FactoryCheckImgForBg> ls = new ArrayList<FactoryCheckImgForBg>();
		Hashtable<Integer, FactoryCheckImgForBg> hs = new Hashtable<Integer, FactoryCheckImgForBg>();
		for (int i = 0; i < list.size(); i++) {
			FactoryCheckImgResult obj = (FactoryCheckImgResult) list.get(i);
			String ptNo = obj.getPtNo().trim();
			if (existEmsMergerImgBeforeMap.get(ptNo) != null) {
				EmsEdiMergerImgBefore imgbefore = (EmsEdiMergerImgBefore) existEmsMergerImgBeforeMap
						.get(ptNo);
				Integer seqNum = imgbefore.getEmsEdiMergerImgAfter()
						.getSeqNum();
				FactoryCheckImgForBg imgbg = null;
				if (hs.get(seqNum) != null) {
					imgbg = (FactoryCheckImgForBg) hs.get(seqNum);
				} else {
					imgbg = new FactoryCheckImgForBg();
					imgbg.setSeqNum(seqNum);
					imgbg
							.setName(imgbefore.getEmsEdiMergerImgAfter()
									.getName());
					imgbg
							.setSpec(imgbefore.getEmsEdiMergerImgAfter()
									.getSpec());
					imgbg
							.setUnit(imgbefore.getEmsEdiMergerImgAfter()
									.getUnit());
					imgbg.setCompany(CommonUtils.getCompany());
					imgbg.setHead(head);
					hs.put(seqNum, imgbg);
				}
				imgbg.setBgNum(formatDouble(imgbg.getBgNum())
						+ formatDouble(obj.getMaterielStockNum()));
				this.checkCancelDao.saveCheckImgForBg(imgbg);
				ls.add(imgbg);
			}
		}
		return ls;
	}

	public List importImgForBg(CheckParameter head) {
		List<FactoryCheckImgResult> ls = new ArrayList<FactoryCheckImgResult>();
		List list = this.checkCancelDao.findCheckImgByHead(head);
		for (int i = 0; i < list.size(); i++) {
			CheckImg img = (CheckImg) list.get(i);
			FactoryCheckImgResult obj = new FactoryCheckImgResult();
			obj.setCompany(CommonUtils.getCompany());
			obj.setHead(head);
			obj.setMaterielStockNum(img.getMaterStock());
			obj.setPtNo(img.getPtNo());
			obj.setName(img.getName());
			obj.setSpec(img.getSpec());
			obj.setUnit(img.getUnit());
			this.checkCancelDao.saveFactoryCheckImgResult(obj);
			ls.add(obj);
		}
		return ls;
	}

	// 得到报关级差异 = 帐面库存 - 实际库存
	public List findFactoryCheckBgCy(CheckParameter head) {
		List alist = new ArrayList();
		Hashtable hs = new Hashtable();
		List list = this.checkCancelDao.findFactoryCheckImgForBgAll(head);
		for (int i = 0; i < list.size(); i++) {
			FactoryCheckImgForBg obj = (FactoryCheckImgForBg) list.get(i);
			CheckBgCy cy = new CheckBgCy();
			cy.setCompany(CommonUtils.getCompany());
			cy.setHead(head);
			cy.setSeqNum(obj.getSeqNum());
			cy.setName(obj.getName());
			cy.setSpec(obj.getSpec());
			cy.setUnit(obj.getUnit());
			cy.setFactNum(obj.getBgNum());
			cy.setEmsBalance(Double.valueOf(0));
			cy.setCyNum(0 - formatDouble(obj.getBgNum()));
			hs.put(obj.getSeqNum(), cy);
		}
		List bgls = getBgRemainNum(head, null, false);
		for (int j = 0; j < bgls.size(); j++) {
			CheckBgCy obj = (CheckBgCy) bgls.get(j);
			CheckBgCy cy = null;
			if (hs.get(obj.getSeqNum()) != null) {
				cy = (CheckBgCy) hs.get(obj.getSeqNum());
			} else {
				cy = new CheckBgCy();
				cy.setHead(head);
				cy.setCompany(CommonUtils.getCompany());
				cy.setSeqNum(obj.getSeqNum());
				cy.setName(obj.getName());
				cy.setSpec(obj.getSpec());
				cy.setUnit(obj.getUnit());
				hs.put(obj.getSeqNum(), cy);
			}
			cy.setEmsBalance(formatDouble(cy.getEmsBalance())
					+ formatDouble(obj.getEmsBalance()));
			cy.setCyNum(formatDouble(cy.getCyNum())
					+ formatDouble(obj.getEmsBalance()));
		}
		List ls = new Vector(hs.values());
		for (int k = 0; k < ls.size(); k++) {
			CheckBgCy obj = (CheckBgCy) ls.get(k);
			this.checkCancelDao.saveCheckBgCy(obj);
			alist.add(obj);
		}
		return alist;
	}

	public List getBgRemainNum(CheckParameter head, CancelHead cancelHead,
			boolean isOwner) {

		Hashtable hs = new Hashtable();
		List emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k emshead = (EmsHeadH2k) emslist.get(0);
		findLjUseNum(hs, emshead, null, head.getEndDate(), new Boolean(true),
				false);
		List list = checkCancelDao.findImpExpCommInfo(null, head.getEndDate(),
				false, new Boolean(true), cancelHead, isOwner);
		Hashtable ht = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			Integer no = info.getCommSerialNo();// 商品序号
			String trade = info.getBaseCustomsDeclaration().getTradeMode() == null ? null
					: info.getBaseCustomsDeclaration().getTradeMode().getCode();// 贸易方式代码
			Deduc deduc = null;
			CheckBgCy obj = null;
			if (info.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.IMPORT) {
				// deduc = checkCancelDao.findDeducByTradeCode(trade, "I");
				deduc = (Deduc) deducHsI.get(trade);
			} else if (info.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.EXPORT) {
				// deduc = checkCancelDao.findDeducByTradeCode(trade, "E");
				deduc = (Deduc) deducHsE.get(trade);
			}
			if (deduc == null) {
				continue;
			}
			if (ht.get(no) != null) { // 查到
				obj = (CheckBgCy) ht.get(no);
			} else {
				obj = new CheckBgCy();
				obj.setSeqNum(no);
				obj.setName(info.getCommName());
				obj.setSpec(info.getCommSpec());
				obj.setUnit(info.getUnit());
				ht.put(no, obj);
			}
			TempDD x = (TempDD) hs.get(String.valueOf(no));
			Double ljUse = Double.valueOf(0);
			if (x != null) {
				ljUse = x.getAa();
			}
			obj.setEmsBalance(formatDouble(obj.getEmsBalance())
					- formatDouble(info.getCommAmount()));// 数量
			if (deduc != null) {
				if (deduc.getDeducMark() != null
						&& deduc.getDeducMark().equals("+")) {
					obj.setEmsBalance(formatDouble(obj.getEmsBalance())
							+ formatDouble(info.getCommAmount()));// 数量
				} else if (deduc.getDeducMark() != null
						&& deduc.getDeducMark().equals("-")) {
					obj.setEmsBalance(formatDouble(obj.getEmsBalance())
							- formatDouble(info.getCommAmount()));// 数量
				}
			}
		}
		return new Vector(ht.values());
	}

	public void findLjUseNum(Hashtable hs, EmsHeadH2k head, Date beginDate,
			Date endDate, Boolean isEffect, boolean isDeclaration) {
		hs.clear();
		List list = this.checkCancelDao.finLjUseNum(head, beginDate, endDate,
				isEffect, isDeclaration);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String ljNo = String.valueOf(obj[2]);
			Double unitWear = formatDouble((Double) obj[3]);
			Double wear = formatDouble((Double) obj[4]);
			Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量

			Double useNum = formatDouble((Double) obj[5])
					- formatDouble((Double) obj[6]);

			Double ljUseNum = useNum * unitUse;
			TempDD temp = null;
			if (hs.get(ljNo) == null) {
				temp = new TempDD();
				temp.setLjseqnum(ljNo);
				temp.setAa(ljUseNum);
				hs.put(ljNo, temp);
			} else {
				temp = (TempDD) hs.get(ljNo);
				temp.setAa(formatDouble(temp.getAa()) + ljUseNum);
			}
		}
	}

	public void makeCustomsDeclarationisEmsCav(CancelHead cancelHead,
			boolean emscav) {
		List ls = this.checkCancelDao
				.findmakeCustomsDeclarationisEmsCav(cancelHead);
		System.out.println(ls.size());
		for (int i = 0; i < ls.size(); i++) {
			CustomsDeclaration customs = (CustomsDeclaration) ls.get(i);
			if (emscav == true) {
				customs.setIsEmsCav(true);
			} else {
				customs.setIsEmsCav(false);
			}
			System.out.println(customs.getIsEmsCav());
		}

	}
	
	
	public List<EmsTransFactory> importEmsTransFactory(EmsAnalyHead head, List<EmsTransFactory> list) {
		for (EmsTransFactory emsTransFactory : list) {
			emsTransFactory.setHead(head);
		}
		this.checkCancelDao.batchSaveOrUpdate(list);
		return list;
	}
	
	public List<EmsTransFactory> deleteEmsTransFactory(List<EmsTransFactory> list) {
		EmsTransFactory transFactory = null;
		for (int i = 0; list != null && i < list.size(); i++) {
			transFactory = list.get(i);
			this.checkCancelDao.delete(transFactory);
		}
		return list;
	}
	
	public List<EmsTransFactoryBg> deleteEmsTransFactoryBg(EmsAnalyHead head) {
		List<EmsTransFactoryBg> list = emsAnalyDao.findEmsTransFactoryBg(head);
		for (EmsTransFactoryBg bg : list) {
			emsAnalyDao.delete(bg);
		}
		return list;
	}
	
	public List<EmsTransFactoryBg> convertEmsTransFactoryToBg(EmsAnalyHead head, List<EmsTransFactory> list) {
		EmsHeadH2k emsHeadH2k = getEmsHeadH2k();
		
		if(list != null && !list.isEmpty()) {
			List<EmsTransFactoryBg> bgList = new ArrayList<EmsTransFactoryBg>(list.size());
			
			/*
			 * 获取list里的工厂料号
			 * 根据工厂料号查询内部归并资料
			 */
			Set<String> ptNoSet = new HashSet<String>();
			for (int i = 0; i < list.size(); i++) {
				ptNoSet.add(list.get(i).getPtNo());
			}
			List<InnerMergeData> imgBeforeList = emsAnalyDao.findBgImgFromInnerMerger(MaterielType.MATERIEL, ptNoSet);
			
			// imgBeforeList 转化为 imgAfterMap
			// key = 归并前料号，value = 归并后料件
			Map<String, InnerMergeData> imgAfterMap = CommonUtils.listToMap(imgBeforeList, new GetKeyValueImpl<InnerMergeData>(){
				public String getKey(InnerMergeData e) {
					return e.getMateriel().getPtNo();
				}
			});
			
			/*
			 * 把 EmsTransFactory 转换为 EmsTransFactoryBg
			 * 把 工厂料号 转化为 归并料号
			 */
			EmsTransFactoryBg bg = null;
			EmsTransFactory transFactory = null;
			InnerMergeData innerMerge = null;
			for (int i = 0; i < list.size(); i++) {
				transFactory = list.get(i);
				bg = new EmsTransFactoryBg();
				bg.setemsTransFactory(transFactory);
				bg.setEmsNo(emsHeadH2k.getEmsNo());
				
				innerMerge = imgAfterMap.get(transFactory.getPtNo());
				
				if(innerMerge == null) {
					throw new RuntimeException("料号：" + transFactory.getPtNo() + "找不到内部归并关系或者账册料件。");
				}
				
				bg.setHead(head);
				bg.setName(innerMerge.getHsAfterMaterielTenName());
				bg.setSeqNum(innerMerge.getHsAfterTenMemoNo());
				bg.setSpec(innerMerge.getHsAfterMaterielTenSpec());
				bg.setConvertRate(CommonUtils.getDoubleExceptNull(innerMerge.getMateriel().getUnitConvert()));
				bg.setUnit(innerMerge.getHsAfterMemoUnit());
				bg.setUnReceiveNum(transFactory.getUnReceiveNum() * bg.getConvertRate());
				bg.setUnTransferNum(transFactory.getUnTransferNum() * bg.getConvertRate());
				
				bgList.add(bg);
			}
			
			emsAnalyDao.batchSaveOrUpdate(bgList);
			
			return bgList;
		}
		return null;
	}
	
	/**
	 * 获得当前帐册号
	 * @return
	 */
	private EmsHeadH2k getEmsHeadH2k() {
		// 确定帐册
		List<EmsHeadH2k> head2kList = emsEdiTrDao.findEmsHeadH2k();
		EmsHeadH2k emsHeadH2k = null;
		if(head2kList.size() > 1) {
			for (int i = 0; i < head2kList.size(); i++) {
				if(DeclareState.CHANGING_EXE.equals(head2kList.get(i).getDeclareState())) {
					emsHeadH2k = head2kList.get(i);
					break;
				}
			}
			if(emsHeadH2k == null) {
				emsHeadH2k = head2kList.get(0);
			}
			
		} else {
			emsHeadH2k = head2kList.get(0);
		}
		
		return emsHeadH2k;
	}
	
	
	/**
	 * 显示电子帐册表头
	 * 
	 * @return
	 */
	public List findEmsHeadH2k() {
		return this.checkCancelDao.findEmsHeadH2k();
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

	public CheckCancelDao getCheckCancelDao() {
		return checkCancelDao;
	}

	public void setCheckCancelDao(CheckCancelDao checkCancelDao) {
		this.checkCancelDao = checkCancelDao;
	}

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}
}