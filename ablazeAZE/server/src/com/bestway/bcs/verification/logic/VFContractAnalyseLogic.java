package com.bestway.bcs.verification.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcs.verification.dao.VFContractAnalyseDao;
import com.bestway.bcs.verification.dao.VFVerificationDao;
import com.bestway.bcs.verification.entity.VFAnalyType;
import com.bestway.bcs.verification.entity.VFContractAnalyse;
import com.bestway.bcs.verification.entity.VFCustomsCountExg;
import com.bestway.bcs.verification.entity.VFCustomsCountExgConvert;
import com.bestway.bcs.verification.entity.VFCustomsCountImg;
import com.bestway.bcs.verification.entity.VFCustomsExg;
import com.bestway.bcs.verification.entity.VFCustomsImg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.CurrRate;

public class VFContractAnalyseLogic {
	private VFContractAnalyseDao vfContractAnalyseDao;
	private VFVerificationDao vfVerificationDao;

	/**
	 * @param vfVerificationDao
	 *            the vfVerificationDao to set
	 */
	public void setVfVerificationDao(VFVerificationDao vfVerificationDao) {
		this.vfVerificationDao = vfVerificationDao;
	}

	/**
	 * @param vfContractAnalyseDao
	 *            the vfContractAnalyseDao to set
	 */
	public void setVfContractAnalyseDao(
			VFContractAnalyseDao vfContractAnalyseDao) {
		this.vfContractAnalyseDao = vfContractAnalyseDao;
	}

	/**
	 * 获取并保存成品报关明细表
	 * 
	 * @param request
	 * @param section
	 *            批次号
	 * @param isImgOrExg
	 *            料件true或成品false
	 * @return
	 */
	public void gainVFCustomsExg(VFSection section, boolean isImgOrExg) {
		List list = new ArrayList();
		// 根据批次号获得来自报关单明细,合同，备案资料库的资料
		List listComminfo = vfContractAnalyseDao
				.findCustomsDeclarationComminfoBySection(section, isImgOrExg);
		System.out.println("listComminfo------------------"
				+ listComminfo.size());
		long b = System.currentTimeMillis();
		List listexg = vfContractAnalyseDao.finddcimg(isImgOrExg);
		Map<String ,Integer> mapcMap = new HashMap<String, Integer>();
		for(int i=0;i<listexg.size();i++){
			Object[] temp = (Object[])listexg.get(i);
			mapcMap.put(temp[1]+"/"+temp[2], (Integer)temp[0]);
		}
		long e = System.currentTimeMillis();
		System.out.println("查询归并序号消耗时间：" + (e - b)/1000.0 + "秒");
		//查找转换人民币的汇率
		Map<String,Double> mapRate = getMapRate();
		
		List<VFCustomsExg> customsExgList = new ArrayList<VFCustomsExg>();
		for (int i = 0; i < listComminfo.size(); i++) {
			VFCustomsExg customsExg = new VFCustomsExg();
			Object[] obj = (Object[]) listComminfo.get(i);
			customsExg.setSeqNum(Integer.valueOf(obj[0].toString()));// 备案资料库备案序号/记录号
			customsExg.setEmsNo(String.valueOf(obj[1].toString()));// 手册号
			customsExg.setSerialNumber(Integer.valueOf(obj[2].toString()));// 序号
			customsExg.setCustomsDeclarationCode(obj[3]==null?"":String.valueOf(obj[3]
					.toString()));// 报关单号
			customsExg.setDeclarationDate((Date)obj[4]);// 申报日期
			customsExg.setCommName(String.valueOf(obj[5].toString()));// 商品名称
			customsExg.setCommSpec(String.valueOf(obj[6] == null ? "" :obj[6].toString()));// 商品规格
			customsExg.setCommAmount(obj[7]==null?Double.valueOf(0):Double.valueOf(obj[7].toString()));// 商品数量
			
			String curr1Code = obj[14].toString();//币别
			
			customsExg.setCommUnitPrice(Double.valueOf(obj[8].toString()));// 商品单价
			Double commUnitPrice = CaleUtil.multiply(customsExg.getCommUnitPrice(),mapRate.get(curr1Code));
			customsExg.setCommUnitPrice(commUnitPrice);
			
			customsExg.setUnit(String.valueOf(obj[9].toString()));// 单位
			
			customsExg.setTotalMoney(obj[10]==null?Double.valueOf(0):Double.valueOf(obj[10].toString()));// 总金额
			Double totalMoney = CaleUtil.multiply(customsExg.getTotalMoney(),mapRate.get(curr1Code));
			customsExg.setCommUnitPrice(totalMoney);
			
			customsExg.setImpExpType(Integer.valueOf(obj[11].toString()));// 进出口类型
			customsExg.setTradeMode(String.valueOf(obj[12].toString()));// 贸易方式
			customsExg.setCompanyName(String.valueOf(obj[13].toString()));//  企业名称
//			customsExg.setMergerNo(obj[14]==null?Integer.valueOf(0):Integer.valueOf(obj[14].toString()));//  归并序号
			String key = customsExg.getEmsNo() +"/" + customsExg.getSeqNum();	
			customsExg.setMergerNo(mapcMap.get(key) == null ? Integer.valueOf(0) : mapcMap.get(key));//  归并序号
			customsExg.setCompany(CommonUtils.getCompany());
			customsExg.setSection(section);// 批次
			customsExgList.add(customsExg);
			System.out.println("listComminfo------------------"
					+ listComminfo.size());
			System.out.println("保存------商品序号-----------"
					+ customsExg.getSerialNumber());
		}
		vfContractAnalyseDao.batchSaveOrUpdate(customsExgList);
	}

	/**
	 * 获取并保存料件报关明细表
	 * 
	 * @param request
	 * @param section
	 *            批次号
	 * @param isImgOrExg
	 *            料件true或成品false
	 * @return
	 */
	public void gainVFCustomsImg(VFSection section, boolean isImgOrExg) {
		List list = new ArrayList();
		// 根据批次号获得来自报关单明细,合同，备案资料库的资料
		System.out.println("第一步：报关单数据开始查询");
		long b = System.currentTimeMillis();
		List listComminfo = vfContractAnalyseDao
				.findCustomsDeclarationComminfoBySection(section, isImgOrExg);
		System.out.println("报关单listComminfo------------------"
				+ listComminfo.size());
		long e = System.currentTimeMillis();
		System.out.println("查询报关单数据消耗时间：" + (e - b)/1000.0 + "秒");
		
		//查询通关手册和备案资料库-归并序号
		b = e;
		List<Object[]> listimg = vfContractAnalyseDao.finddcimg(isImgOrExg);
		Map<String,Integer> mapcMap = new HashMap<String, Integer>();
		for(int i =0;i<listimg.size();i++){
			Object[] temp = (Object[])listimg.get(i);
			mapcMap.put(temp[1]+"/"+temp[2], (Integer)temp[0]);
		}
		e = System.currentTimeMillis();
		System.out.println("查询归并序号消耗时间：" + (e - b)/1000.0 + "秒");
		
		System.out.println("第二步：数据转化开始：");
		//查找转换人民币的汇率
		Map<String,Double> mapRate = getMapRate();
		b = e;
		List<VFCustomsImg> customsImgList = new ArrayList<VFCustomsImg>();
		for (int i = 0; i < listComminfo.size(); i++) {
			VFCustomsImg customsimg = new VFCustomsImg();
			Object[] obj = (Object[]) listComminfo.get(i);
			customsimg.setSeqNum(Integer.valueOf(obj[0].toString()));// 备案资料库备案序号
			customsimg.setEmsNo(String.valueOf(obj[1].toString()));// 手册号
			customsimg.setSerialNumber(Integer.valueOf(obj[2].toString()));// 序号
			customsimg.setCustomsDeclarationCode(obj[3]==null?"":String.valueOf(obj[3]
					.toString()));// 报关单号
			customsimg.setDeclarationDate((Date)obj[4]);// 申报日期
			customsimg.setCommName(String.valueOf(obj[5].toString()));// 商品名称
			customsimg.setCommSpec(String.valueOf(obj[6].toString()));// 商品规格
			customsimg.setCommAmount(obj[7]==null?Double.valueOf(0):Double.valueOf(obj[7].toString()));// 商品数量
			
			String curr1Code = obj[14].toString();//币别
			
			customsimg.setCommUnitPrice(Double.valueOf(obj[8].toString()));// 商品单价
			Double commUnitPrice = CaleUtil.multiply(customsimg.getCommUnitPrice(),mapRate.get(curr1Code));
			customsimg.setCommUnitPrice(commUnitPrice);
			
			customsimg.setUnit(String.valueOf(obj[9].toString()));// 单位
			
			customsimg.setTotalMoney(obj[10]==null?Double.valueOf(0):Double.valueOf(obj[10].toString()));// 总金额
			Double totalMoney = CaleUtil.multiply(customsimg.getTotalMoney(),mapRate.get(curr1Code));
			customsimg.setCommUnitPrice(totalMoney);
			
			customsimg.setImpExpType(Integer.valueOf(obj[11].toString()));// 进出口类型
			customsimg.setTradeMode(String.valueOf(obj[12].toString()));// 贸易方式
			customsimg.setCompanyName(String.valueOf(obj[13].toString()));//  企业名称
//			customsimg.setMergerNo(obj[14]==null?Integer.valueOf(0):Integer.valueOf(obj[14].toString()));//  归并序号
			String key = customsimg.getEmsNo() +"/" + customsimg.getSeqNum();			
			customsimg.setMergerNo(mapcMap.get(key) == null ? Integer.valueOf(0) : mapcMap.get(key));//  归并序号
			
			customsimg.setCompany(CommonUtils.getCompany());
			customsimg.setSection(section);// 批次
			customsImgList.add(customsimg);
		}
		e = System.currentTimeMillis();
		System.out.println("数据转化耗用时间：" + (e - b)/1000.0+ "秒");
		b = e;
		System.out.println("第三步：报关单数据准备保存：" + (e - b)/1000.0);
		vfContractAnalyseDao.batchSaveOrUpdate(customsImgList);
		e = System.currentTimeMillis();
		System.out.println("报关单数据保存完成时间" + (e - b)/1000.0);
	}

	private Map<String,Double> getMapRate(){
		Map<String,Double> mapCurrRate = new HashMap<String, Double>();
		List<CurrRate> listCurrRate = vfContractAnalyseDao.findCurrRate();
		for (int i = 0; i < listCurrRate.size(); i++) {
			CurrRate currRate = listCurrRate.get(i);
			if("142".equals(currRate.getCurr().getCode())){
				if(mapCurrRate.get(currRate.getCurr1().getCode())==null){
					mapCurrRate.put(currRate.getCurr1().getCode(), currRate.getRate());
				}
			}
		}
		return mapCurrRate;
	}
	
	/**
	 * 根据批次号分页查询查询
	 * @param section
	 *            批次号
	 * @param index
	 *            开始值
	 * @param length
	 *            长度
	 * @param property
	 *            对象属性
	 * @param value
	 *            对象值
	 * @param isLike
	 *            是否模糊
	 * @param analyType 核查分析表类型
	 * @return
	 */
	public List findPageVFByVFSection(VFSection section, int index, int length,
			String property, Object value, boolean isLike, int analyType) {
		List list =null;
		if(VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT==analyType){
			list = this.vfContractAnalyseDao.findPageVFCustomsCountExgConvertSection(
					section, index, length, property, value, isLike);
		} else if(VFAnalyType.VFCUSTOMS_IMG==analyType){
			list = this.vfContractAnalyseDao.findPageVFCustomsImgBySection(
					section, index, length, property, value, isLike);
		}else if(VFAnalyType.VFCUSTOMS_EXG==analyType){
			list = this.vfContractAnalyseDao.findPageVFCustomsExgBySection(
					section, index, length, property, value, isLike);
		}
		return list;
	}

	/**
	 * t统计某批次的报关单明细的资料的笔数
	 * 
	 * @param section
	 *            批次
	 * @param isImgOrExg
	 *            料件true或成品false
	 * @return
	 */
	public long countVFCustomsImgOrExgBySection(VFSection section,String property, 
			Object value, boolean isLike,int analyType) {
		String entityName = "VFCustomsImg";
		if (VFAnalyType.VFCUSTOMS_IMG==analyType) {
			entityName = "VFCustomsImg";
		} else if(VFAnalyType.VFCUSTOMS_EXG==analyType){
			entityName = "VFCustomsExg";
		}else if(VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT==analyType){
			entityName = "VFCustomsCountExgConvert";
		}
		return vfVerificationDao.countByVFSection(section, entityName,property,value,isLike);
	}
	/**
	 * 获得并保存料件报关数据统计表
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public void gainVFCustomsCountImg(VFSection section){//1.查询【料件报关明细表】
		List<VFCustomsImg> vfCustomsImgList = vfVerificationDao.findByVFSection(section,"VFCustomsImg");
		//2.对商品根据【归并序号】+【手册号】+【备案序号】进行汇总，并统计。
		/*
		 * *****************************************************************
		本期总进口数量=sum进口类型【料件进口】【料件转厂】【余料结转进口】-【余料结转出口】数量-【海关批准内销】
		本期总进口金额=sum进口类型【料件进口】【料件转厂】【余料结转进口】-【余料结转出口】金额-【海关批准内销】
		单价= 本期总进口金额/本期总进口数量
		本期余料转入量=sum进口类型【余料结转进口】
		******************************************************************/
		Map<String,VFCustomsCountImg> imgMap = new HashMap<String, VFCustomsCountImg>();
		double impTotalAmont = 0;
		double impTotalMoney = 0;
		double remainForwordAmount = 0;
		for (int i = 0; i < vfCustomsImgList.size(); i++) {
			VFCustomsImg customsimg = (VFCustomsImg)vfCustomsImgList.get(i);
			String key = customsimg.getMergerNo()+customsimg.getEmsNo()+customsimg.getSeqNum();
			VFCustomsCountImg countImg = (VFCustomsCountImg) imgMap.get(key);
			if(countImg==null){
				countImg = new VFCustomsCountImg();
				countImg.setCommName(customsimg.getCommName());
				countImg.setSerialNumber(customsimg.getSerialNumber());
				countImg.setSeqNum(customsimg.getSeqNum());
				countImg.setDeclarationDate(customsimg.getDeclarationDate());
				countImg.setCommSpec(customsimg.getCommSpec());
				countImg.setUnit(customsimg.getUnit());
				countImg.setMergerNo(customsimg.getMergerNo());
				countImg.setEmsNo(customsimg.getEmsNo());
				countImg.setCompanyName(customsimg.getCompanyName());
				countImg.setSection(section);
				imgMap.put(key, countImg);				 
			}
			impTotalAmont = CommonUtils.getDoubleExceptNull(countImg.getImpTotalAmont());
			impTotalMoney = CommonUtils.getDoubleExceptNull(countImg.getImpTotalMoney());
			remainForwordAmount = CommonUtils.getDoubleExceptNull(countImg.getRemainForwordAmount());
		    int impExpType = customsimg.getImpExpType();
		    switch (impExpType) {
				case ImpExpType.DIRECT_IMPORT: 
				case ImpExpType.TRANSFER_FACTORY_IMPORT: 
				case ImpExpType.REMAIN_FORWARD_IMPORT:
					 impTotalAmont +=customsimg.getCommAmount();
					 impTotalMoney +=customsimg.getTotalMoney();
					 break;
				case ImpExpType.REMAIN_FORWARD_EXPORT:
				case ImpExpType.BACK_MATERIEL_EXPORT:
				case ImpExpType.MATERIAL_EXCHANGE:
				case ImpExpType.MATERIAL_REOUT:
				case ImpExpType.MATERIAL_DOMESTIC_SALES: 
					 impTotalAmont -=customsimg.getCommAmount();
					 impTotalMoney -=customsimg.getTotalMoney();
					 break;
			}
		    switch (impExpType) {
		    	case ImpExpType.REMAIN_FORWARD_IMPORT:
		    		remainForwordAmount +=customsimg.getCommAmount();
		    		break;
			}
		    countImg.setImpTotalAmont(impTotalAmont);								//设置进口总数量
		    countImg.setImpTotalMoney(impTotalMoney); 	 							//设置进口总金额
		    countImg.setImpPrice(CaleUtil.dividend(impTotalMoney, impTotalAmont));  //设置单价
		    countImg.setRemainForwordAmount(remainForwordAmount);
		}
		List<VFCustomsCountImg> vfList = new ArrayList<VFCustomsCountImg>(imgMap.values());		
		//3、统计后保存至【料件数据统计表】
		vfVerificationDao.batchSaveOrUpdate(vfList);
	}
	
	/**
	 * 获得并保存成品报关数据统计表
	 * 
	 * @param section
	 *            批次
	 * @return
	 */
	public void gainVFCustomsCountExg(VFSection section) {// 1.查询【成品报关明细表】
		List vfCustomsCountExgList = vfVerificationDao.findByVFSection(section,
				"VFCustomsExg");
		// 2.对商品根据【归并序号】+【手册号】+【备案序号】进行汇总，并统计。
		/*
		 * *****************************************************************
		 * 本期总出口数量=sum出口类型【成品出口】+【结转出口】+【返工复出】-【退厂返工】数量
		 * 本期总出口金额=sum出口类型【料件进口】【料件转厂】+【返工复出】-【退厂返工】金额 单价= 本期总进口金额/本期总进口数量
		 * ****************************************************************
		 */
		Map exgMap = new HashMap<String, VFCustomsCountExg>();
		double expTotalAmont = 0;
		double expTotalMoney = 0;
		for (int i = 0; i < vfCustomsCountExgList.size(); i++) {
			VFCustomsExg vf = (VFCustomsExg) vfCustomsCountExgList.get(i);
			String key = vf.getMergerNo() + vf.getEmsNo() + vf.getSeqNum();
			VFCustomsCountExg countExg= (VFCustomsCountExg) exgMap.get(key);
			if (countExg==null) {
				VFCustomsCountExg vfCountExg = new VFCustomsCountExg();
				vfCountExg.setCommName(vf.getCommName());
				vfCountExg.setSerialNumber(vf.getSerialNumber());
				vfCountExg.setSeqNum(vf.getSeqNum());
				vfCountExg.setDeclarationDate(vf.getDeclarationDate());
				vfCountExg.setCommSpec(vf.getCommSpec());
				vfCountExg.setUnit(vf.getUnit());
				vfCountExg.setEmsNo(vf.getEmsNo());
				vfCountExg.setCompanyName(vf.getCompanyName());
				vfCountExg.setExpTotalAmont(vf.getCommAmount() == null ? Double
						.valueOf(0) : vf.getCommAmount());
				vfCountExg.setExpTotalMoney(vf.getTotalMoney() == null ? Double
						.valueOf(0) : vf.getTotalMoney());
				vfCountExg.setSection(section);
				exgMap.put(key, vfCountExg);
			} else {
				expTotalAmont = countExg.getExpTotalAmont();
				expTotalMoney = countExg.getExpTotalMoney();
				int impExpType = vf.getImpExpType();

				// 本期总出口数量=sum出口类型【成品出口】+【结转出口】+【返工复出】-【退厂返工】数量
				switch (impExpType) {
				case ImpExpType.DIRECT_EXPORT:
				case ImpExpType.TRANSFER_FACTORY_EXPORT:
				case ImpExpType.REWORK_EXPORT:
					expTotalAmont += vf.getCommAmount();
					break;
				case ImpExpType.BACK_FACTORY_REWORK:
					expTotalAmont -= vf.getCommAmount();
					break;
				}
				// 本期总出口金额=sum出口类型【料件进口】【料件转厂】+【返工复出】-【退厂返工】金额
				switch (impExpType) {
				case ImpExpType.DIRECT_IMPORT:
				case ImpExpType.TRANSFER_FACTORY_IMPORT:
				case ImpExpType.REWORK_EXPORT:
					expTotalMoney += vf.getTotalMoney();
					break;
				case ImpExpType.BACK_FACTORY_REWORK:
					expTotalMoney -= vf.getTotalMoney();
					break;
				}
				countExg.setExpTotalAmont(expTotalAmont);
				countExg.setExpTotalMoney(expTotalMoney);
			}
		}
		// 3、根据备案序号+手册号统计后保存至【成品数据统计表】
		List vfList = new ArrayList();
		Iterator it = exgMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			VFCustomsCountExg vf = (VFCustomsCountExg) entry.getValue();
			vfList.add(vf);
		}
		vfVerificationDao.batchSaveOrUpdate(vfList);
	}
	
	/**
	 * 折算成品并保存到成品折料统计表
	 * 
	 * @param section
	 *            批次
	 */
	public void convertCustomsCountExgToImg(VFSection section) {
		//1、查询【成品数据统计表】
		List<VFCustomsCountExg> vfCustomsCountExgList = vfVerificationDao.findByVFSection(section,
				"VFCustomsCountExg");
		Set<Integer> credenceNoSet = new HashSet<Integer>();
		Set<String> emsNoSet = new HashSet<String>();
		List<Integer> credenceNoList = new ArrayList<Integer>();
		List<String> emsNoList = new ArrayList<String>();
		for (int i = 0; i < vfCustomsCountExgList.size(); i++) {
			VFCustomsCountExg countExg = (VFCustomsCountExg) vfCustomsCountExgList
					.get(i);
			credenceNoSet.add(countExg.getSeqNum());
			emsNoSet.add(countExg.getEmsNo());
		}
		credenceNoList.addAll(credenceNoSet);
		emsNoList.addAll(emsNoSet);
//		Iterator it = credenceNoSet.iterator();
//		while (it.hasNext()) {
//			if (it.next() != null) {
//				credenceNoList.add((Integer) it.next());
//			}
//		}
//		it = emsNoSet.iterator();
//		while (it.hasNext()) {
//			if (it.next() != null) {
//				emsNoList.add((String) it.next());
//			}
//		}
		//2、根据备案序号和手册号，找到对应的合同单耗,把成品折成单耗，
		//耗用量=出口成品数量*（单耗*1-（损耗率））既 出口成品数量*单项用量
		 	//2.1根据成品数据统计表的备案序号和手册号，获得所有成品BOM
		List bomList = vfContractAnalyseDao
				.getContractBomByCountExg(credenceNoList, emsNoList);
		List<VFCustomsCountExgConvert> exgConvertList =new ArrayList<VFCustomsCountExgConvert>();
		for (VFCustomsCountExg countExg : vfCustomsCountExgList) {
			for (int i = 0; i < bomList.size(); i++) {
				Object[] objs = (Object[])bomList.get(i);
				Integer innerMergeSeqNum = (Integer)objs[1];//料件归并序号
				ContractBom bom = (ContractBom)objs[0];//单耗
				countExg.getSeqNum();
				//BOM所属的成品备案序号，手册号与成品数据统计的备案序号，手册号相同
				if ((countExg.getSeqNum().equals(bom.getContractExg().getSeqNum()))
						&& (countExg.getEmsNo().equals(bom.getContractExg().getContract().getEmsNo()))) {
					VFCustomsCountExgConvert exgConvert = new VFCustomsCountExgConvert();
//					exgConvert.setSerialNumber(innerMergeSeqNum);
					exgConvert.setVfCustomsCountExg(countExg);
					exgConvert.setSeqNum(bom.getContractImgSeqNum());
					exgConvert.setCommName(bom.getName());
					exgConvert.setCommSpec(bom.getSpec());
					exgConvert.setUnit(bom.getUnit() == null ? "" : bom.getUnit().getName());
					//耗用量=出口成品数量*（单耗*1-（损耗率））既 出口成品数量*单项用量
					Double expTotalAmont =CommonUtils.getDoubleExceptNull(countExg.getExpTotalAmont());
					Double unitDosage =CommonUtils.getDoubleExceptNull(bom.getUnitDosage());
					exgConvert.setWasteAmount(CaleUtil.multiply(expTotalAmont,unitDosage,8));
					exgConvert.setEmsNo(countExg.getEmsNo());
					exgConvert.setMergerNo(innerMergeSeqNum);
					exgConvert.setSection(section);
					exgConvert.setUnitWaste(CommonUtils.getDoubleExceptNull(bom.getUnitWaste()));
					exgConvert.setWaste(CommonUtils.getDoubleExceptNull(bom.getWaste()));
					exgConvert.setUnitUsed(CommonUtils.getDoubleExceptNull(bom.getUnitDosage()));
					exgConvertList.add(exgConvert);
					System.out.println("成品折料-----------------"+bom.getSeqNum());
				}
			}
		}
		// 3、保存至【成品折料数据统计表】
			vfVerificationDao.batchSaveOrUpdate(exgConvertList);
	}
	
	/**
	 * 查询并保存到合同分析表
	 * 
	 * @param section
	 *            批次
	 */
	public void gainContractAnalyse(VFSection section){
		//1、查询数量来源于【料件数据统计表】【成品折算统计表】
		List countImgList = vfContractAnalyseDao.findSumImportImgByVFSection(section);
		List exgConvertList = vfContractAnalyseDao.findSumWasteQtyByVFSection(section);
		/*
		 * 根据归并序号
		 * *****************************************************************
		 * 进口数量=sun（本期总进口数量）【料件数据统计表】
		 * 出口数量=sun（合计耗量）【成品折算统计表】
		 * 合同应剩余数量=进口数量-出口数量；
		 * 库存数量= 合同应剩余数量；
		 * 当进口数量-出口数量大于0时，就是溢出
		 * 当进口数量-出口数量小于0时，就是短少；
		 * ****************************************************************
		 */
		//进口数量  数据来自【料件数据统计表】 统计料件的进口数量
		Map<Integer,VFContractAnalyse> contractAnalyseMap = new HashMap<Integer,VFContractAnalyse>();
		for (int i = 0; i < countImgList.size(); i++) {
			Object[] countImg = (Object[]) countImgList.get(i);
			Integer mergerNo = Integer.valueOf(countImg[1].toString());
			VFContractAnalyse contracAnalyse = (VFContractAnalyse) contractAnalyseMap.get(mergerNo);
			if(contracAnalyse==null){
				VFContractAnalyse contractAnalyse = new VFContractAnalyse();
				contractAnalyse.init();
				contractAnalyse.setMergerNo(mergerNo);
				Double totalAmont =countImg[2]==null?0.0:Double.valueOf(countImg[2].toString());
				Double totalMoney =countImg[3]==null?0.0:Double.valueOf(countImg[3].toString());
				if(!totalAmont.equals(Double.valueOf(0))){
					contractAnalyse.setUnitPrice(CaleUtil.dividend(totalMoney, totalAmont,4));//料件进口平均单价,取四位
				}
				contractAnalyse.setImpAmount(CommonUtils.getDoubleExceptNull(totalAmont));
				contractAnalyse.setSection(section);
				contractAnalyse.setCompany(CommonUtils.getCompany());
				contractAnalyseMap.put(mergerNo, contractAnalyse);
			}else{
				Double totalAmont =countImg[2]==null?0.0:Double.valueOf(countImg[2].toString());
				contracAnalyse.setImpAmount(CommonUtils
						.getDoubleExceptNull(totalAmont)
						+ contracAnalyse.getImpAmount());
			}
		}
		
//		//出口数量 数据来自【成品折算统计表】 统计成品的出口数量
		Double wasteAmount =0.0;
		for (int i = 0; i < exgConvertList.size(); i++) {
			Object[] countExgConvert = (Object[])exgConvertList.get(i);
			Integer mergerNo = Integer.valueOf(countExgConvert[1].toString());
			VFContractAnalyse contractAnalyse = (VFContractAnalyse) contractAnalyseMap.get(mergerNo);
			if(contractAnalyse==null){
				contractAnalyse = new VFContractAnalyse();
				contractAnalyse.init();
				contractAnalyse.setMergerNo(mergerNo);
				wasteAmount = countExgConvert[2]==null?0.0:Double.valueOf(countExgConvert[2].toString());
				contractAnalyse.setExpAmount(wasteAmount);
				contractAnalyse.setSection(section);
				contractAnalyse.setCompany(CommonUtils.getCompany());
				contractAnalyseMap.put(mergerNo, contractAnalyse);
			}
			else{
				wasteAmount = countExgConvert[2]==null?0.0:Double.valueOf(countExgConvert[2].toString());
				contractAnalyse.setExpAmount(wasteAmount
						+ contractAnalyse.getExpAmount());
			}
		}
		//从contractAnalyseMap中取出数据进行如下统计：
		// 合同应剩余数量=进口数量-出口数量；
		// 库存数量= 合同应剩余数量；
		// 当进口数量-出口数量大于0时，就是溢出
		// 当进口数量-出口数量小于0时，就是短少；
		Iterator<Entry<Integer,VFContractAnalyse>> it = contractAnalyseMap.entrySet().iterator();
		List<VFContractAnalyse> contractAnalyseList = new ArrayList<VFContractAnalyse>();
		while (it.hasNext()) {
			Map.Entry<Integer,VFContractAnalyse> entry = it.next();
			VFContractAnalyse object = (VFContractAnalyse) entry.getValue();
			if(object!=null){
				object.setContractLeaveNum(CaleUtil.subtract(object.getImpAmount(), object.getExpAmount(),4));//合同应剩余数量
				object.setStockAmount(object.getContractLeaveNum());//库存数量
				double num = object.getImpAmount()-object.getExpAmount();//当进口数量-出口数量
				if(num>0){
					object.setOverflowAmount(num);//溢出数量
				}else if(num<0){
					object.setDeficitAmount(num);//短少数量
				}
				Double  unitPrice = object.getUnitPrice();
				if(unitPrice!=null){
					if(num>0){
						object.setOverflowPrice(CaleUtil.multiply(unitPrice, num,4));//溢出金额
					}else if(num<0){
						object.setDeficitPrice(CaleUtil.multiply(unitPrice, num,4));//短少金额
					}
				}
			}
			contractAnalyseList.add(object);
		}
		
	  //补充	合同分析资料的料件名称，料件规格，料件单位
		List innerMergeLs = this.vfContractAnalyseDao.findBcsTenInnerMerge();
		Map innerMergeMap = new HashMap<Integer,Object[]>();
		for (int i = 0; i < innerMergeLs.size(); i++) {
			Object[] img = (Object[]) innerMergeLs.get(i);
			innerMergeMap.put(img[0],img);
		}
		for (int i = 0; i < contractAnalyseList.size(); i++) {
			VFContractAnalyse contractAnalyse  = (VFContractAnalyse) contractAnalyseList.get(i);
			Object[] obj = (Object[]) innerMergeMap.get(contractAnalyse.getMergerNo());
			if(obj!=null){
				contractAnalyse.setHsName(CommonUtils.getStringExceptNull(obj[1].toString()));
				contractAnalyse.setHsSpec(CommonUtils.getStringExceptNull(obj[2] == null ? "" :obj[2].toString()));
				contractAnalyse.setHsUnit(CommonUtils.getStringExceptNull(obj[3] == null ? "" :obj[3].toString()));
			}
		}
		
		/***排序 按照归并序号排序**/
//		Collections.sort(contractAnalyseList,new Comparator<VFContractAnalyse>() {
//			public int compare(VFContractAnalyse o1, VFContractAnalyse o2) {				
//				return CommonUtils.getIntegerExceptNull(o1.getMergerNo()) - CommonUtils.getIntegerExceptNull(o2.getMergerNo());
//			}
//		});
		// 3、保存至【合同分析表】
		vfVerificationDao.batchSaveOrUpdate(contractAnalyseList);
	}
	/**
	 * 获得某个批次号的数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public List findVFBySection(VFSection section,
			int analyType,Integer mergerNo) {
		String table = "VFCustomsCountImg";
		if (VFAnalyType.VFCUSTOMS_COUNTIMG==analyType) {
			table = "VFCustomsCountImg";
		} else if(VFAnalyType.VFCUSTOMS_COUNTEXG==analyType){
			table = "VFCustomsCountExg";
		}else if(VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT==analyType){
			table = "VFCustomsCountExgConvert";
		}else if(VFAnalyType.VFCONTRACT_ANALYSE==analyType){
			table = "VFContractAnalyse";
		}
		return vfVerificationDao.findByVFSection(section, table,mergerNo);
	}
	/**
	 *  获得某个批次号合同数据分析数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *           报表类型
	 *   @param  mergerNo 归并序号 
	 * @return
	 */
	public List<VFContractAnalyse> findVFContractAnalyseVFBySection(VFSection section,Integer mergerNo){
		List<VFContractAnalyse> list = vfContractAnalyseDao.findVFContractAnalyseBySection(section, mergerNo);
		for (int i = 0; i < list.size(); i++) {
			VFContractAnalyse vf = list.get(i);
			CommonUtils.formatDouble(vf, 5);
		}
		return list;
	}
	
	/**
	 * 获得某个批次号的数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public List findVFContractBySection(VFSection section,
			int analyType) {
		List list = null;
		if (VFAnalyType.VFCUSTOMS_COUNTIMG==analyType) {
			list = vfContractAnalyseDao.findVFCustomsCountImgBySection(section);
		} else if(VFAnalyType.VFCUSTOMS_COUNTEXG==analyType){
			list = vfContractAnalyseDao.findVFCustomsCountExgBySection(section);
		}else if(VFAnalyType.VFCONTRACT_ANALYSE==analyType){
			list = vfContractAnalyseDao.findVFContractAnalyseBySection(section,null);
		}
		return list; 
	}
	
	/**
	 * 根据批次号删除数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public int delectVFBySection(VFSection section,
			int analyType) {
		String table = "VFCustomsCountImg";
		if (VFAnalyType.VFCUSTOMS_COUNTIMG==analyType) {
			table = "VFCustomsCountImg";
		} else if(VFAnalyType.VFCUSTOMS_COUNTEXG==analyType){
			table = "VFCustomsCountExg";
		}else if(VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT==analyType){
			table = "VFCustomsCountExgConvert";
		} else if(VFAnalyType.VFCUSTOMS_IMG==analyType){
			table = "VFCustomsImg";
		}else if(VFAnalyType.VFCUSTOMS_EXG==analyType){
			table = "VFCustomsExg";
		}else if(VFAnalyType.VFCONTRACT_ANALYSE==analyType){
			table = "VFContractAnalyse";
		}
		return vfVerificationDao.deleteByVFSection(section, table);
	}

	/**
	 * 判断是否存在某批次的数据表
	 * 
	 * @param section
	 *            批次
	 * @param analyType
	 *            核查分析类型
	 * @return
	 */
	public boolean isExistVFBySection(VFSection section,
			int analyType) {
		String table = "VFCustomsCountImg";
		if (VFAnalyType.VFCUSTOMS_COUNTIMG==analyType) {
			table = "VFCustomsCountImg";
		} else if(VFAnalyType.VFCUSTOMS_COUNTEXG==analyType){
			table = "VFCustomsCountExg";
		}else if(VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT==analyType){
			table = "VFCustomsCountExgConvert";
		} else if(VFAnalyType.VFCUSTOMS_IMG==analyType){
			table = "VFCustomsImg";
		}else if(VFAnalyType.VFCUSTOMS_EXG==analyType){
			table = "VFCustomsExg";
		}else if(VFAnalyType.VFCONTRACT_ANALYSE==analyType){
			table = "VFContractAnalyse";
		}
		return vfContractAnalyseDao.isExistVFBySection(section,table);
	}

	
	/**
	 * 根据批次号、料件归并序号成品折料统计表
	 * 
	 * @param section
	 *            批次
	 * @param mergerNo
	 *           料件归并序号
	 */
	public List<VFCustomsCountExgConvert> findConvertCustomsCountExg(VFSection section,Integer mergerNo){
		return vfContractAnalyseDao.findConvertCustomsCountExg(section, mergerNo);
	}
	
	/**
	 * 查询料件报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param seqNum 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	public List<VFCustomsImg> findVFCustomsImg(VFSection section, String emsNo,Integer seqNum, Integer impExpType){
		return vfContractAnalyseDao.findVFCustomsImg(section,emsNo,seqNum,impExpType);
	}
	/**
	 * 查询成品报关明细数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param seqNum 备案号
	 * @param impExpType 进出类型
	 * @return
	 */
	public List<VFCustomsExg> findVFCustomsExg(VFSection section, String emsNo,Integer seqNum){
		return vfContractAnalyseDao.findVFCustomsExg(section,emsNo,seqNum);
	}
}
