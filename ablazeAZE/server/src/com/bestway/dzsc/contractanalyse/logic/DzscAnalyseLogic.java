/*
 * Created on 2005-6-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contractanalyse.entity.WrapStat;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SearchType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.checkcancel.entity.DzscContractBalance;
import com.bestway.dzsc.contractanalyse.dao.DzscAnalyseDao;
import com.bestway.dzsc.contractanalyse.entity.DzscExpFinishProductProgressDetail;
import com.bestway.dzsc.contractanalyse.entity.DzscExpFinishProductProgressTotal;
import com.bestway.dzsc.contractanalyse.entity.DzscExpFinishProductStat;
import com.bestway.dzsc.contractanalyse.entity.DzscImpMaterielExeDetail;
import com.bestway.dzsc.contractanalyse.entity.DzscImpMaterielExeStat;
import com.bestway.dzsc.contractanalyse.entity.DzscImpMaterielExeState;
import com.bestway.dzsc.contractanalyse.entity.DzscStorageStat;
import com.bestway.dzsc.contractanalyse.entity.DzscWrapStat;
import com.bestway.dzsc.contractanalyse.entity.TempDzscContractImg;
import com.bestway.dzsc.contractanalyse.entity.TempDzscExpContractStat;
import com.bestway.dzsc.contractanalyse.entity.TempDzscFinishProduct;
import com.bestway.dzsc.contractanalyse.entity.TempDzscImpContractStat;
import com.bestway.dzsc.contractanalyse.entity.TempFinishedProductExportAmount;
import com.bestway.dzsc.contractanalyse.entity.TempImpPORImgStatInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractstat.dao.DzscStatDao;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscImpExpCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.contractstat.logic.DzscStatLogic;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;

/**
 * com.bestway.dzsc.contractanalyse.logic.DzscAnalyseLogic 
 * 电子手册报关分析Logic
 * edit by cjb 2010-12
 * 
 * 
 * @author Administrator
 */
public class DzscAnalyseLogic {

	/**
	 * 电子手册报关单相关操作dao
	 */
	DzscStatDao dzscStatDao = null;

	/**
	 * 电子手册报关分析dao
	 */
	DzscAnalyseDao dzscAnalyseDao = null;

	/**
	 * 电子手册备案查询dao
	 */
	DzscDao dzscDao = null;

	/**
	 * 电子手册备案查询logic
	 */
	DzscStatLogic dzscStatLogic = null;

	/**
	 * 获取dzscStatDao
	 * 
	 * @return Returns the contractStatDao.
	 */
	public DzscStatDao getDzscStatDao() {
		return dzscStatDao;
	}

	/**
	 * 获取dzscDao
	 * 
	 * @return Returns the contractDao.
	 */
	public DzscDao getDzscDao() {
		return dzscDao;
	}

	/**
	 * 设置dzscDao
	 * 
	 * @param contractDao
	 *            The contractDao to set.
	 */
	public void setDzscDao(DzscDao contractDao) {
		this.dzscDao = contractDao;
	}

	/**
	 * 设置dzscStatDao
	 * 
	 * @param contractStatDao
	 */
	public void setDzscStatDao(DzscStatDao contractStatDao) {
		this.dzscStatDao = contractStatDao;
	}

	/**
	 * 获取dzscAnalyseDao
	 * 
	 * @return Returns the contractAnalyseDao.
	 */
	public DzscAnalyseDao getDzscAnalyseDao() {
		return dzscAnalyseDao;
	}

	/**
	 * 设置dzscAnalyseDao
	 * 
	 * @param contractAnalyseDao
	 *            The contractAnalyseDao to set.
	 */
	public void setDzscAnalyseDao(DzscAnalyseDao contractAnalyseDao) {
		this.dzscAnalyseDao = contractAnalyseDao;
	}

	/**
	 * 电子手册备案查询logic
	 */
	public DzscStatLogic getDzscStatLogic() {
		return dzscStatLogic;
	}

	/**
	 * 电子手册备案查询logic
	 */
	public void setDzscStatLogic(DzscStatLogic dzscStatLogic) {
		this.dzscStatLogic = dzscStatLogic;
	}
//	/**
//	 * 根据手册号和进出口标志查找报报关单表体Map
//	 * 
//	 * @param impExpFlag
//	 *            进出口标志
//	 * @param emsNo
//	 *            手册号
//	 * @return
//	 */
//	public Map getCommInfoListMap(Integer[] impExpType,
//			String emsNO,Map<String,List<DzscCustomsDeclarationCommInfo>> CommInfoListMap
//			,Date beginDate , Date endDate) {
//			
//		List list = dzscDao.findDzscCustomsDeclarationCommInfoNew(impExpType,
//				emsNO,beginDate,endDate);
//		
//		System.out.println("单本合同料件总数："+list.size());
//		
//		for (int i = 0; i < list.size(); i++) {
//			DzscCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) list
//					.get(i);
//			String key="";
//			if(commInfo!=null){
//				String commSpec=commInfo.getCommSpec()==null?"":commInfo.getCommSpec();
//				
//				key=commInfo.getComplex().getCode().trim()+"/"
//				+commInfo.getCommName()+"/"+commSpec+"/"+commInfo.getUnit().getName()
//				+"/"+String.valueOf(commInfo.getCommUnitPrice()).trim();
////				key=String.valueOf(commInfo.getCommSerialNo()).trim();
//				System.out.println("保存KEY:"+key);
//				
//			}
//			
//			if (CommInfoListMap.get(key) == null) {
//				List commInfoList = new ArrayList();
//				commInfoList.add(commInfo);
//				CommInfoListMap.put(key, commInfoList);
//			} else {
//				List commInfoList = (List) CommInfoListMap.get(key);
//				commInfoList.add(commInfo);
//			}
//		}
//		System.out.println("单本合同料件集合:"+CommInfoListMap.size());
//		
//		
//		return CommInfoListMap;
//
//	}
//	/**
//	 * 合同月结报表
//	 * @param date 截止日期  
//	 * @return 
//	 * @author 石小凯
//	 */
//	public List findDzscContractBalance(Date date) {
//		System.out.println("enddate="+date);
//		//进出口类型
//		Integer impExpType[] = new Integer[] { ImpExpType.DIRECT_IMPORT,
//				ImpExpType.TRANSFER_FACTORY_IMPORT,
//				ImpExpType.REMAIN_FORWARD_IMPORT,
//				ImpExpType.REMAIN_FORWARD_EXPORT,
//				ImpExpType.BACK_MATERIEL_EXPORT,
//				ImpExpType.MATERIAL_DOMESTIC_SALES};
//		
//		//因为老方法带有起始时间、懒的改动直接重用了、所以虚设一个时间
//		//需求表更后会有个起始时间的.
//		Date beginDate=new Date();
//		try{
//		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
//		beginDate= myFormatter.parse("1990-05-1"); 
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		 
//		boolean isCancel=false;
//		//获取所有正在执行合同
//		List<DzscEmsPorHead> contract =  dzscDao.findDzscEmsPorHeadProcess(isCancel);
//		
//		//最终结果MAP
//		Map<String,DzscContractBalance> map = new HashMap<String,DzscContractBalance> ();
//		
//		//合同编号LIST
//		List<String> emsNolist=new ArrayList<String>();
//		
//		for(DzscEmsPorHead d:contract){
//			emsNolist.add(d.getEmsNo());
//		}
//		String commSpec="";
//		//循环合同
//		for(int i=0;i<contract.size();i++){
//			//返回所遍历的合同所有料件余料实体
//			DzscEmsPorHead contractCav=(DzscEmsPorHead)contract.get(i);
////			List<DzscContractBalance> data = this.findImpMaterialStated(impExpType,
////					contractCav,contract, beginDate, date);
//			List<DzscContractBalance> data = this.findImpMaterialStat(impExpType,
//					contractCav,contract, beginDate, date);
//			
//			//遍历已经计算完成后的料件、进行最后的余料累加
//			for(DzscContractBalance newdata :data){
//				//KEY为商品编码、名称、规格、单位、单价
//				commSpec=newdata.getCommSpec()==null?"":newdata.getCommSpec();
////				String key=newdata.getComplexCode().trim()+"/"+newdata.getCommName()+"/"
////				+newdata.getCommSpec()+"/"+newdata.getUnitName()+"/"+String.valueOf(newdata.getCommUnitPrice()).trim();
//				String key=newdata.getComplexCode().trim()+"/"+newdata.getCommName()+"/"
//				+commSpec+"/"+newdata.getUnitName()+"/"+String.valueOf(newdata.getCommUnitPrice()).trim();
//				
//				if (map.get(key) == null) {
//					newdata.setTotal(newdata.getContract().get(contractCav.getEmsNo()));
//					newdata.setContractEmsNo(emsNolist);
//					map.put(key, newdata);
//				}
//				else{
//					//取得MAP里面以后的料件
//					DzscContractBalance oldData = (DzscContractBalance) map.get(key);
//					
//					//累加该料件的余量
//					Map<String,Double> newkeymap = newdata.getContract();
//					
//					Map<String,Double> oldkeymap = oldData.getContract();
//					
//					if(newkeymap!=null){
//						oldkeymap.putAll(newkeymap);
//					}
//					
//					Double totle = 0.0;
//					for(Object o : oldkeymap.keySet()){ 
//					    totle+=(Double)oldkeymap.get(o); 
//					} 
//					oldData.setTotal(totle);
//				}
//			
//			}
//		}
//		List lsResult = new ArrayList(map.values());
//		return lsResult;
//	}
//	/**
//	 * @param impExpType 进出口类型
//	 * @param contractCav 遍历所需要计算的单个合同
//	 * @param beginDate 起始时间
//	 * @param date      截止日期
//	 * @return 返回所遍历的合同所有料件余料实体
//	 * @author 石小凯
//	 */
//	public List<DzscContractBalance> findImpMaterialStated(Integer impExpType[],
//		DzscEmsPorHead contractCav,List<DzscEmsPorHead> contract, Date beginDate, Date date) {
//		List<DzscContractBalance> data=new ArrayList();
//		
//		String emsNo=contractCav.getEmsNo();
//		
//		//所有料件MAP
//		Map<String,List<DzscCustomsDeclarationCommInfo>> commInfoListMap = new HashMap<String,List<DzscCustomsDeclarationCommInfo>>();
//
//		//根据手册号和进出口标志查找报报关单表体Map、程序运算速度较慢就是因为此方法
//		//循环合同所有报关单取出相同KEY的料件
//		commInfoListMap = getCommInfoListMap(impExpType,contractCav.getEmsNo(),commInfoListMap, beginDate, date);
//		
//		//料件耗用量
//		Map<String, Double> hmImgUse = new HashMap<String, Double>();
//		
//		//取得合同內所有料件的KEY
//		List<String> keys=new ArrayList(commInfoListMap.keySet());
//		
//		Collections.sort(keys);
//		
//		//计算料件耗用
//		this.calContractImgUseByXiao(contractCav, hmImgUse, beginDate, date);
//		
//		
//		//测试用
////		List<DzscEmsImgBill> list = this.dzscDao.findDzscEmsImgBill(contractCav
////				.getId());
//
//		//遍历所有料件
//		for(String key :keys){
////		for (DzscEmsImgBill contractImg : list) {
//			double directImportAmount = 0.0;// 进口数量 (其中已包括余料结转数)
//			double transferFactoryImport = 0.0;// 料件转厂
//			double remainImport = 0.0;// 余料进口 (余料结转进口)
//			double remainExport = 0.0;// 余料出口 (余料结转出口)
//			double internalAmount = 0.0;// 内销数量
//			double backExport = 0.0;// 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
//			double exchangeExport = 0.0;// 料件退换出口数
//			double totle=0.0; //余料总数量
//			/**
//			 * 进口总数量＝ 料件进口数+余料结转进口数＋ 料件转厂进口数－料件退换出口数//+料件退换进口数(已经在料件进口中)
//			 */
//			double importTotal=0.0;
//			/**
//			 * 内销数量
//			 */
//			double internalAmounts=0.0;
//			/**
//			 * 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265） //
//			 */
//			double backExports=0.0;
//			/**
//			 * 余料出口 (余料结转出口)
//			 */
//			double remainExports=0.0;
//			/**
//			 * 成品耗用
//			 */
//			double productWaste =0.0;
//			
//			/**
//			 * 料件KEY
//			 */
//			String commInfokey="";
//			
//			/**
//			 * 料件进口量
//			 */
//			double directImport=0.0;
//			/**
//			 * 退料退换
//			 */
//			double backMaterialExchange=0.0;
//			
//			/**
//			 * 退运复出
//			 */
//			double backMaterialReturn=0.0;
//			
//			/**
//			 * 余料结转出口
//			 */
//			double remainForward=0.0;
//			//获取MAP中KEY相同的料件LIST
//			List<DzscCustomsDeclarationCommInfo> lists = commInfoListMap.get(key);
//			
//			//料件余量存储实体
//			DzscContractBalance dcb=new DzscContractBalance();
//			//获取每一个料件
////			for(DzscCustomsDeclarationCommInfo commInfo:lists){
//			DzscCustomsDeclarationCommInfo commInfo=lists.get(0);
//				Integer commSerialNo = commInfo.getCommSerialNo();
//				dcb.setComplexCode(commInfo.getComplex().getCode());
//				dcb.setCommSerialNo(commInfo.getCommSerialNo());
//				dcb.setCommName(commInfo.getCommName());
//				dcb.setCommSpec(commInfo.getCommSpec());
//				dcb.setCommNameSpec(commInfo.getCommName()+commInfo.getCommSpec());
//				dcb.setUnitName(commInfo.getUnit().getName());
//				dcb.setCommUnitPrice(commInfo.getCommUnitPrice());
//			
////			Integer commSerialNo = contractImg.getSeqNum();
////			dcb.setComplexCode(contractImg.getComplex().getCode());
////			dcb.setCommSerialNo(commSerialNo);
////			dcb.setCommName(contractImg.getName());
////			dcb.setCommSpec(contractImg.getSpec());
////			dcb.setCommNameSpec(contractImg.getName()+contractImg.getSpec());
////			dcb.setUnitName(contractImg.getUnit().getName());
////			dcb.setCommUnitPrice(contractImg.getPrice());
//			
////				String tradeModeCod = null;
//				//料件的KEY（编号、名称、规格、单位、单价）
////				commInfokey=commInfo.getComplex().getCode()+"/"+commInfo.getCommName()+"/"+commInfo.getCommSpec()
////				+"/"+commInfo.getUnit().getName()+"/"+commInfo.getCommUnitPrice();
//				
//				/**
//				 * 料件进口量
//				 */
//				directImport+=this.dzscDao
//						.findCommInfoTotalAmount(dcb.getComplexCode().trim(),dcb.getCommName(),
//							dcb.getCommSpec(),dcb.getUnitName(),dcb.getCommUnitPrice(),
//								ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
//								null, emsNo, beginDate, date);
////				directImport=this.dzscDao
////				.findCommInfoTotalAmountOld(commSerialNo,
////						ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
////						null, emsNo, beginDate, date);
//				/**
//				 * 转厂进口量
//				 */
////				transferFactoryImport+=this.dzscDao
////						.findCommInfoTotalAmount(dcb.getComplexCode().trim(),dcb.getCommName(),
////								dcb.getCommSpec(),dcb.getUnitName(),dcb.getCommUnitPrice(),
////								ImpExpFlag.IMPORT,
////								ImpExpType.TRANSFER_FACTORY_IMPORT, null,
////								emsNo, beginDate,date);
//				transferFactoryImport=this.dzscDao
//				.findCommInfoTotalAmountOld(commSerialNo,
//						ImpExpFlag.IMPORT,
//						ImpExpType.TRANSFER_FACTORY_IMPORT, null,
//						emsNo, beginDate,date);
//				/**
//				 * 退料退换
//				 */
////				backMaterialExchange+=this.dzscDao
////						.findCommInfoTotalAmount(dcb.getComplexCode().trim(),dcb.getCommName(),
////								dcb.getCommSpec(),dcb.getUnitName(),dcb.getCommUnitPrice(),
////								ImpExpFlag.EXPORT,
////								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
////										"0300", "0700" }, emsNo, beginDate,
////								date);
//				backMaterialExchange=this.dzscDao
//				.findCommInfoTotalAmountOld(commSerialNo,
//						ImpExpFlag.EXPORT,
//						ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
//								"0300", "0700" }, emsNo, beginDate,
//						date);
//
//				/**
//				 * 余料进口 (余料结转进口)
//				 */
////				remainImport+=this.dzscDao
////						.findCommInfoTotalAmount(dcb.getComplexCode().trim(),dcb.getCommName(),
////								dcb.getCommSpec(),dcb.getUnitName(),dcb.getCommUnitPrice(),
////								ImpExpFlag.IMPORT,
////								ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
////								beginDate, date);
//				remainImport=this.dzscDao
//				.findCommInfoTotalAmountOld(commSerialNo,
//						ImpExpFlag.IMPORT,
//						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
//						beginDate, date);
//				
//				/**
//				 * 退料复出
//				 */
////				backMaterialReturn=this.dzscDao
////						.findCommInfoTotalAmount(dcb.getComplexCode().trim(),dcb.getCommName(),
////								dcb.getCommSpec(),dcb.getUnitName(),dcb.getCommUnitPrice(),
////								ImpExpFlag.EXPORT,
////								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
////										"0265", "0664" }, emsNo, beginDate,
////								date);
//				backMaterialReturn=this.dzscDao
//				.findCommInfoTotalAmountOld(commSerialNo,
//						ImpExpFlag.EXPORT,
//						ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
//								"0265", "0664" }, emsNo, beginDate,
//						date);
//				/**
//				 * 余料出口 (余料结转出口)
//				 */
////				remainForward=this.dzscDao
////						.findCommInfoTotalAmount(dcb.getComplexCode().trim(),dcb.getCommName(),
////								dcb.getCommSpec(),dcb.getUnitName(),dcb.getCommUnitPrice(),
////								ImpExpFlag.EXPORT,
////								ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
////								beginDate, date);
//				remainForward=this.dzscDao
//				.findCommInfoTotalAmountOld(commSerialNo,
//						ImpExpFlag.EXPORT,
//						ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
//						beginDate, date);
//				/**
//				 * 进口总数量＝ 料件进口数+余料结转进口数＋ 料件转厂进口数－料件退换出口数//+料件退换进口数(已经在料件进口中)
//				 */
////				importTotal=directImportAmount+transferFactoryImport+remainImport+exchangeExport;
//				
//				/**
//				 * 进口总量=直接进口量+转厂进口量+余料进口-退料退换(料件退换进口量已经包含在料件进口量中)
//				 */
//				importTotal=CommonUtils.getDoubleExceptNull(directImport)
//				+ CommonUtils.getDoubleExceptNull(transferFactoryImport)
//				+ CommonUtils.getDoubleExceptNull(remainImport)
//				- CommonUtils.getDoubleExceptNull(backMaterialExchange);
//			
//				System.out.println("单个料件每个报关单的进口量"+importTotal);
//				/**
//				 * 成品耗用
//				 */
//				productWaste = CommonUtils
//				.getDoubleExceptNull(hmImgUse
//						.get(commSerialNo == null ? "" : commSerialNo
//								.toString()));
//				
//				System.out.println("该合同单个料件的耗用量"+productWaste);
//				/**
//				 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）－退运出口数量（复出）4）-余料结转出口数量(yp2008-12-19修改)
//				 */
////				totle=CommonUtils
////				.getDoubleExceptNull(importTotal)
////				-CommonUtils
////				.getDoubleExceptNull(internalAmount)
////				-CommonUtils
////				.getDoubleExceptNull(productWaste)
////				-CommonUtils
////				.getDoubleExceptNull(remainExport)
////				-CommonUtils
////				.getDoubleExceptNull(backExport);
//				/**
//				 * 余量=进口总量-退运出口(复出)-成品使用量-余料结转出口
//				 */
//				totle = (CommonUtils
//						.getDoubleExceptNull(importTotal)
//						- CommonUtils.getDoubleExceptNull(productWaste)
//						- CommonUtils.getDoubleExceptNull(backMaterialReturn)
//						- CommonUtils.getDoubleExceptNull(remainForward));
//				
//				Map<String,Double> map=new HashMap<String,Double>();
//				//将总量以<合同名、余量>存储于实体的MAP中
//				//map.put(commInfokey+"/"+contractCav.getEmsNo(), totle);
//				map.put(contractCav.getEmsNo(), CommonUtils
//						.getDoubleExceptNull(totle));
//				dcb.setContract(map);
//				
//				//如果是该料件第一次计算的合同、初始化所有合同总余量为0.0
////				if(dcb.getTotal()==null){
////					dcb.setTotal(0.0);
////				}
//				
//				//测试用的实体
////				List<Double> list = new ArrayList<Double>();
////				list.add(totle);
////				dcb.setContractTotle(list);
//	//			System.out.println("进口总数量"+importTotal);
//	//			System.out.println("内销数量"+internalAmounts);
//	//			System.out.println("产品总耗用量"+productWaste);
//	//			System.out.println("退运出口数量"+backExports);
//	//			System.out.println("余料结转出口数量"+remainExports);
//	//			System.out.println("余料="+totle);
//				
//				data.add(dcb);
//				
//			}
//	//		List<List<DzscCustomsDeclarationCommInfo>> lists=(List)commInfoListMap.values();
//			return data;
//		}
//	/**
//	 * 检查是否为空，如果是则返回0
//	 * 
//	 * @param dou
//	 *            小数Double
//	 * @return
//	 */
//	private Double checkNullForDouble(Double dou) {
//		return dou == null ? 0.0 : dou;
//	}
//	/**
//	 * 计算成品所有的料件的耗用总量
//	 * 
//	 * @param contract
//	 *            手册通关备案表头
//	 * @param hmImgUse
//	 *            手册通过备案的BOM资料对应的数量
//	 * @param beginDate
//	 *            开始日期
//	 * @param endDate
//	 *            结束日期
//	 * @author 石小凱
//	 */
//	private void calContractImgUseByXiao(DzscEmsPorHead contract,
//			Map<String, Double> hmImgUse, Date beginDate, Date endDate) {
//		List<DzscExpProductStat> lsProductStat = this.findExpProductStat(
//				contract, beginDate, endDate);
//		for (DzscExpProductStat productStat : lsProductStat) {
//			double exgAmount = CommonUtils.getDoubleExceptNull(productStat
//					.getExpTotalAmont());
//			List<DzscEmsBomBill> list = this.dzscDao.findBomByExg(contract
//					.getEmsNo(), productStat.getSeqNum());
//			for (DzscEmsBomBill contractBom : list) {
//				if (contractBom.getImgSeqNum() == null) {
//					continue;
//				}
//				// double imgAmount = exgAmount
//				// * CommonUtils.getDoubleExceptNull(contractBom
//				// .getUnitDosage());
//				double imgAmount = exgAmount
//						* CommonUtils.getDoubleExceptNull(CommonUtils
//								.getDoubleExceptNull(contractBom.getUnitWare())
//								/ (1 - CommonUtils
//										.getDoubleExceptNull(contractBom
//												.getWare()) / 100.0));
//				// System.out.println(productStat.getCommName()+"::"+contractBom.getName()+"imgAmount:"+imgAmount);
//				// double imgWaste = imgAmount
//				// * ((contractBom.getWare() == null ? 0.0 : contractBom
//				// .getWare()) / 100.0);
//				String commSpec = contractBom.getSpec()==null?"":contractBom.getSpec();
//				
//				String contractBomkey = contractBom.getComplex().getCode().trim()+"/"+contractBom.getName()+"/"+commSpec+"/"
//				+contractBom.getUnit().getName()+"/"+String.valueOf(contractBom.getPrice()).trim();
////				double hasAmount = CommonUtils.getDoubleExceptNull(hmImgUse
////						.get(contractBomkey));
//				double hasAmount = CommonUtils.getDoubleExceptNull(hmImgUse
//						.get(contractBom.getImgSeqNum().toString()));
//				// System.out.println(productStat.getCommName()+"::"+contractBom.getName()+"hasAmount:"+hasAmount);
//				hmImgUse.put(contractBomkey, imgAmount + hasAmount);
//				
////				hmImgUse.put(contractBom.getImgSeqNum().toString(), imgAmount
////						+ hasAmount);
////				System.out.println("保存成品耗用:"+contractBomkey);
//			}
//		}
//	}
//	/**
//	 * @param impExpType 进出口类型
//	 * @param contractCav 遍历所需要计算的单个合同
//	 * @param beginDate 起始时间
//	 * @param date      截止日期
//	 * @return 返回所遍历的合同所有料件余料实体
//	 * @author 石小凯
//	 */
//	public List<DzscContractBalance> findImpMaterialStat(Integer impExpType[],
//		DzscEmsPorHead contractCav,List<DzscEmsPorHead> contract, Date beginDate, Date date) {
//		//该方法最终返回的料件LIST
//		List<DzscContractBalance> data=new ArrayList();
//		
//		//获得手册号
//		String emsNo=contractCav.getEmsNo();
//		
//		//所有料件MAP
//		Map<String,List<DzscCustomsDeclarationCommInfo>> commInfoListMap = new HashMap<String,List<DzscCustomsDeclarationCommInfo>>();
//
//		//根据手册号和进出口标志查找报报关单表体Map、程序运算速度较慢就是因为此方法
//		//循环合同所有报关单取出相同KEY的料件
//		commInfoListMap = getCommInfoListMap(impExpType,contractCav.getEmsNo(),commInfoListMap, beginDate, date);
//		
//		//料件耗用量
//		Map<String, Double> hmImgUse = new HashMap<String, Double>();
//		
//		//取得合同內所有料件的KEY
////		List<String> keys=new ArrayList(commInfoListMap.keySet());
////		
////		Collections.sort(keys);
//		
//		//计算料件耗用
//		this.calContractImgUseByXiao(contractCav, hmImgUse, beginDate, date);
//		
//		
//		//合同备案料件
////		List<DzscEmsImgBill> list = this.dzscDao.findDzscEmsImgBill(contractCav
////				.getId());
//		//取得合同內所有料件的KEY
//		List<String> keys=new ArrayList(commInfoListMap.keySet());
//		
//		
//		Collections.sort(keys);
//		//遍历合同备案料件
//		for(String key :keys){
////		for (DzscEmsImgBill contractImg : list) {
//			double directImportAmount = 0.0;// 进口数量 (其中已包括余料结转数)
//			double transferFactoryImport = 0.0;// 料件转厂
//			double remainImport = 0.0;// 余料进口 (余料结转进口)
//			double remainExport = 0.0;// 余料出口 (余料结转出口)
//			double internalAmount = 0.0;// 内销数量
//			double backExport = 0.0;// 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
//			double exchangeExport = 0.0;// 料件退换出口数
//			double totle=0.0; //余料总数量
//			double importTotal=0.0;//进口总数量
//			double productWaste=0.0; // 成品耗用
//			/**
//			 * 料件KEY
//			 */
//			String commInfokey="";
//			//获取MAP中KEY相同的料件LIST
//			
//			//料件余量存储实体
//			DzscContractBalance dcb=new DzscContractBalance();
//			//获取每一个料件
//			List<DzscCustomsDeclarationCommInfo> lists = commInfoListMap.get(key);
//			
//			//获取每一个料件
//
//			DzscCustomsDeclarationCommInfo contractImg=lists.get(0);
//			
//			Integer commSerialNo = contractImg.getSerialNumber();
//			String commSerialNos=String.valueOf(commSerialNo).trim();
//			
//			String commSpec = contractImg.getCommSpec()==null?"":contractImg.getCommSpec();
//			
//			dcb.setComplexCode(contractImg.getComplex().getCode());
//			dcb.setCommSerialNo(commSerialNo);
//			dcb.setCommName(contractImg.getCommName());
//			dcb.setCommSpec(commSpec);
//			dcb.setCommNameSpec(contractImg.getCommName()+commSpec);
//			dcb.setUnitName(contractImg.getUnit().getName());
//			dcb.setCommUnitPrice(contractImg.getCommUnitPrice());
//			List commInfoList = (List) commInfoListMap.get(contractImg.getComplex().getCode().trim()+"/"
//					+contractImg.getCommName()+"/"+commSpec+"/"+contractImg.getUnit().getName()+"/"
//					+String.valueOf(contractImg.getCommUnitPrice()).trim());
//			
//			System.out.println("获取KEY:"+contractImg.getComplex().getCode().trim()+"/"
//					+contractImg.getCommName()+"/"+commSpec+"/"+contractImg.getUnit().getName()+"/"
//					+String.valueOf(contractImg.getCommUnitPrice()).trim());
//
//			//====================================================
////			List commInfoList = (List) commInfoListMap.get(commSerialNos);		
//			
////			Integer commSerialNo = contractImg.getSeqNum();
////			String commSerialNos=String.valueOf(commSerialNo).trim();
////			
////			String commSpec = contractImg.getSpec()==null?"":contractImg.getSpec();
////			
////			dcb.setComplexCode(contractImg.getComplex().getCode());
////			dcb.setCommSerialNo(commSerialNo);
////			dcb.setCommName(contractImg.getName());
////			dcb.setCommSpec(commSpec);
////			dcb.setCommNameSpec(contractImg.getName()+commSpec);
////			dcb.setUnitName(contractImg.getUnit().getName());
////			dcb.setCommUnitPrice(contractImg.getPrice());
//////			List commInfoList = (List) commInfoListMap.get(commSerialNos);
////			
////			List commInfoList = (List) commInfoListMap.get(contractImg.getComplex().getCode().trim()+"/"
////					+contractImg.getName()+"/"+commSpec+"/"+contractImg.getUnit().getName()+"/"
////					+String.valueOf(contractImg.getPrice()).trim());
////			
////			System.out.println("获取KEY:"+contractImg.getComplex().getCode().trim()+"/"
////					+contractImg.getName()+"/"+commSpec+"/"+contractImg.getUnit().getName()+"/"
////					+String.valueOf(contractImg.getPrice()).trim());
////			List commInfoList = (List) commInfoListMap.get(contractImg.getComplex().getCode().trim()+"/"
////					+contractImg.getName()+"/"+commSpec+"/"+contractImg.getUnit().getName()+"/"
////					+String.valueOf(contractImg.getPrice()).trim());
////			
////			System.out.println("获取KEY:"+contractImg.getComplex().getCode().trim()+"/"
////					+contractImg.getName()+"/"+commSpec+"/"+contractImg.getUnit().getName()+"/"
////					+String.valueOf(contractImg.getPrice()).trim());
//			if (commInfoList != null)
//				//老方法、获得料件进出口数量
//				for(DzscCustomsDeclarationCommInfo commInfo:lists){
////				for (int j = 0; j < commInfoList.size(); j++) {
////					BaseCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) commInfoList
////							.get(j);
//					String tradeModeCod = null;
//
//					if (commInfo.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.IMPORT) {// 进口
//						if (commInfo.getBaseCustomsDeclaration()
//								.getImpExpType() == ImpExpType.DIRECT_IMPORT) {
//							directImportAmount += commInfo.getCommAmount();// 进口数量
//							// (其中已包括余料结转数)
//						} else if (commInfo.getBaseCustomsDeclaration()
//								.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
//							transferFactoryImport += commInfo.getCommAmount();// 料件转厂
//						} else if (commInfo.getBaseCustomsDeclaration()
//								.getImpExpType() == ImpExpType.REMAIN_FORWARD_IMPORT) {
//							remainImport += commInfo.getCommAmount();// 余料进口
//							// (余料结转进口)
//						} 
//					} else {// 出口
//						if (commInfo.getBaseCustomsDeclaration().getTradeMode() != null)
//							tradeModeCod = commInfo.getBaseCustomsDeclaration()
//									.getTradeMode().getCode();
//						if (commInfo.getBaseCustomsDeclaration()
//								.getImpExpType() == ImpExpType.BACK_MATERIEL_EXPORT) {
//							if (tradeModeCod != null
//									&& (tradeModeCod.equals("0265") || tradeModeCod
//											.equals("0664"))) {
//								backExport += commInfo.getCommAmount();// 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
//							}
//						}
//						if (commInfo.getBaseCustomsDeclaration()
//								.getImpExpType() == ImpExpType.BACK_MATERIEL_EXPORT) {
//							if (tradeModeCod != null
//									&& (tradeModeCod.equals("0300") || tradeModeCod
//											.equals("0700"))) {
//								exchangeExport += commInfo.getCommAmount();// 料件退换出口数
//							}
//						}
//						if (commInfo.getBaseCustomsDeclaration()
//								.getImpExpType() == ImpExpType.REMAIN_FORWARD_EXPORT) {
//							remainExport += commInfo.getCommAmount();// 余料出口
//							// (余料结转出口)
//						}
//					}
//
//					if (commInfo.getBaseCustomsDeclaration().getTradeMode() != null) {
//						tradeModeCod = commInfo.getBaseCustomsDeclaration()
//								.getTradeMode().getCode();
//						if (tradeModeCod != null
//								&& (tradeModeCod.equals("0245") || tradeModeCod
//										.equals("0644"))) {
//							internalAmount += commInfo.getCommAmount();
//						}
//					}
//				}
//			
//				/**
//				 * 进口总数量＝ 料件进口数+余料结转进口数＋ 料件转厂进口数－料件退换出口数//+料件退换进口数(已经在料件进口中)
//				 */
//				importTotal=CommonUtils.getDoubleExceptNull(directImportAmount)
//				+CommonUtils.getDoubleExceptNull(transferFactoryImport)
//				+CommonUtils.getDoubleExceptNull(remainImport)
//				-CommonUtils.getDoubleExceptNull(exchangeExport);
//			
//				System.out.println("单个料件每个报关单的进口量"+importTotal);
////				/**
////				 * 成品耗用
////				 */
////				productWaste = CommonUtils
////				.getDoubleExceptNull(hmImgUse
////						.get(commSerialNo
////								.toString()));
//				/**
//				 * 成品耗用
//				 */
//				productWaste = CommonUtils
//				.getDoubleExceptNull(hmImgUse
//						.get(commInfoList));
//				System.out.println("该合同单个料件的耗用量"+productWaste);
//				/**
//				 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）－退运出口数量（复出）4）-余料结转出口数量(yp2008-12-19修改)
//				 */
//				totle=CommonUtils
//				.getDoubleExceptNull(importTotal)
//				-CommonUtils
//				.getDoubleExceptNull(internalAmount)
//				-CommonUtils
//				.getDoubleExceptNull(productWaste)
//				-CommonUtils
//				.getDoubleExceptNull(remainExport)
//				-CommonUtils
//				.getDoubleExceptNull(backExport);
//				
//				
//				
//				Map<String,Double> map=new HashMap<String,Double>();
//				//将总量以<合同名、余量>存储于实体的MAP中
//				//map.put(commInfokey+"/"+contractCav.getEmsNo(), totle);
//				map.put(contractCav.getEmsNo(), CommonUtils
//						.getDoubleExceptNull(totle));
//				dcb.setContract(map);
//				data.add(dcb);
//				
//			}
//			return data;
//		}
//	/**
//	 * 出口成品报关情况统计表
//	 * 
//	 * @param contract
//	 *            手册通关备案表头
//	 * @param beginDate
//	 *            开始日期
//	 * @param endDate
//	 *            结束日期
//	 * @return List 是DzscExpProductStat型， 存放统计报表中的出口成品报关情况表资料
//	 * 
//	 * @author 石小凱
//	 */
//	public List<DzscExpProductStat> findExpProductStat(DzscEmsPorHead contract,
//			Date beginDate, Date endDate) {
//		List<DzscExpProductStat> lsResult = new ArrayList<DzscExpProductStat>();
//		String emsNo = contract.getEmsNo();
//		/**
//		 * 关封余量－深加工结转
//		 */
//		HashMap<String, Double> hmCustomsEnvelop = converListToHashTable(this.dzscStatDao
//				.findFptAppItemCount(emsNo, beginDate, endDate,
//						false));
//		/**
//		 * 关封余量－转厂
//		 */
//		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTable(this.dzscStatDao
//				.findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
//		List nset = new ArrayList(hmCustomsEnvelopTrans.keySet());
//		for (int i = 0; i < nset.size(); i++) {
//			String key = nset.get(i) == null ? "" : nset.get(i).toString();
//			if (hmCustomsEnvelop.get(key) == null) {
//				hmCustomsEnvelop.put(key == null ? "" : key.toString(),
//						hmCustomsEnvelopTrans.get(key));
//			} else {
//				Double ss = hmCustomsEnvelop.get(key);
//				Double dd = hmCustomsEnvelopTrans.get(key);
//				hmCustomsEnvelop.put(key, (ss + dd));
//			}
//		}
//		// List list = this.dzscCavDao.findAllCommInfo(false, emsNo, beginDate,
//		// endDate);
//		List<DzscEmsExgBill> list = this.dzscDao.findDzscEmsExgBill(contract);
//		for (DzscEmsExgBill contractExg : list) {
//			Integer commSerialNo = contractExg.getSeqNum();
//			// DzscEmsExgBill contractExg =
//			// this.dzscCavDao.findExgByCommSerialNo(
//			// emsNo, commSerialNo);
//			if (contractExg != null) {
//				DzscExpProductStat expProductStat = new DzscExpProductStat();
//				expProductStat.setSeqNum(contractExg.getSeqNum());
//				expProductStat.setComplex(contractExg.getComplex().getCode());
//				expProductStat.setCommName(contractExg.getName());
//				expProductStat.setCommSpec(contractExg.getSpec());
//				expProductStat.setUnit(contractExg.getUnit());
//				expProductStat.setUnitPrice(contractExg.getPrice());
//				/**
//				 * 合同定量
//				 */
//				expProductStat.setContractAmount(contractExg.getAmount());
//				/**
//				 * 成品出口量
//				 */
//				expProductStat.setDirectExport(this.dzscDao
//						.findCommInfoTotalAmountByXiao(commSerialNo,
//								ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT,
//								null, emsNo, beginDate, endDate));
//				/**
//				 * 转厂出口
//				 */
//				expProductStat.setTransferFactoryExport(this.dzscDao
//						.findCommInfoTotalAmountByXiao(commSerialNo,
//								ImpExpFlag.EXPORT,
//								ImpExpType.TRANSFER_FACTORY_EXPORT, null,
//								emsNo, beginDate, endDate));
//				/**
//				 * 退厂返工数
//				 */
//				expProductStat.setBackFactoryRework(this.dzscDao
//						.findCommInfoTotalAmountByXiao(commSerialNo,
//								ImpExpFlag.IMPORT,
//								ImpExpType.BACK_FACTORY_REWORK, null, emsNo,
//								beginDate, endDate));
//				/**
//				 * 返工复出数
//				 */
//				expProductStat.setReworkExport(this.dzscDao
//						.findCommInfoTotalAmountByXiao(commSerialNo,
//								ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT,
//								null, emsNo, beginDate, endDate));
//				/**
//				 * 总出口量=成品出口量+转厂出口量-退厂返工量+返工复出数
//				 */
//				expProductStat
//						.setExpTotalAmont((expProductStat.getDirectExport() == null ? 0.0
//								: expProductStat.getDirectExport())
//								+ (expProductStat.getTransferFactoryExport() == null ? 0.0
//										: expProductStat
//												.getTransferFactoryExport())
//								- (expProductStat.getBackFactoryRework() == null ? 0.0
//										: expProductStat.getBackFactoryRework())
//								+ (expProductStat.getReworkExport() == null ? 0.0
//										: expProductStat.getReworkExport()));
//				double canExportAmount = (expProductStat.getContractAmount() == null ? 0.0
//						: expProductStat.getContractAmount())
//						- (expProductStat.getExpTotalAmont() == null ? 0.0
//								: expProductStat.getExpTotalAmont());
//				if (canExportAmount > 0) {
//					/**
//					 * 可出口量 = 合同定量-总进口量
//					 */
//					expProductStat.setCanExportAmount(canExportAmount);
//				} else {
//					/**
//					 * 超量
//					 */
//					expProductStat.setOverAmount(-canExportAmount);
//				}
//				/**
//				 * 比例
//				 */
//				expProductStat
//						.setScale(CommonUtils
//								.getDoubleByDigit(
//										((expProductStat.getCanExportAmount() == null ? 0.0
//												: expProductStat
//														.getCanExportAmount()) / (expProductStat
//												.getContractAmount() == null ? 1.0
//												: expProductStat
//														.getContractAmount())) * 100,
//										2));
//				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
//						.toString()
//						+ contractExg.getComplex().getCode());
//				/**
//				 * 关封余量=关封总量-转厂出口量
//				 */
//				expProductStat
//						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
//								: customsEnvelopCount)
//								- (expProductStat.getTransferFactoryExport() == null ? 0.0
//										: expProductStat
//												.getTransferFactoryExport()));
//				expProductStat
//						.setProcessUnitPrice(contractExg.getMachinPrice());
//				expProductStat.setProcessTotalPrice(CommonUtils
//						.getDoubleExceptNull(expProductStat
//								.getProcessUnitPrice())
//						* CommonUtils.getDoubleExceptNull(expProductStat
//								.getExpTotalAmont()));
//				expProductStat.setLegalAmount(contractExg.getAmount());
//				expProductStat.setLegalUnit(contractExg.getUnit());
//				expProductStat.setUnitWeight(contractExg.getUnitGrossWeight());
//				expProductStat.setUnitGrossWeight(contractExg
//						.getUnitGrossWeight());
//				expProductStat.setUnitNetWeight(contractExg.getUnitNetWeight());
//				expProductStat.setLevyMode(contractExg.getLevyMode());
//				lsResult.add(expProductStat);
//			}
//		}
//		return lsResult;
//	}
	/**
	 * 查询已报关的商品
	 * 
	 * @param isImport
	 *            判断是否进口类型，true为进口
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            手册通关备案表头
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findCommInfoImpExpList(boolean isImport, Integer seqNum,
			String customer, String impExpType, Date beginDate, Date endDate,
			DzscEmsPorHead contract) {
		String emsNo = null;
		List exgList = new ArrayList();
		List<DzscCustomsDeclarationCommInfo> lsResult = new ArrayList<DzscCustomsDeclarationCommInfo>();
		if (contract != null) {
			emsNo = contract.getEmsNo();
			//获取单本手册通关备案的备案成品,目的单单为DzscImpExpCustomsDeclarationCommInfo添加补充说明栏位（栢能）
			exgList = dzscDao.findDzscEmsExgBill(contract);
		}
		lsResult = this.dzscStatDao.findImpExpCommInfoList(isImport, seqNum,
				customer, impExpType, beginDate, endDate, emsNo);
		//取手册通关备案【成品及单耗】中的【补充说明】栏位,添加到中间实体中（栢能）
		Map hashMap = new HashMap<String, String>();
		for (int k = 0; k < exgList.size(); k++) {
			DzscEmsExgBill bill = (DzscEmsExgBill)exgList.get(k);
			String key = bill.getDzscEmsPorHead().getEmsNo()+bill.getSeqNum();
			hashMap.put(key, bill.getNote());
		}
		for (DzscCustomsDeclarationCommInfo commInfo : lsResult) {
			//key =  手册编号+ 商品序号
			String key = commInfo.getBaseCustomsDeclaration().getEmsHeadH2k()
					+ commInfo.getCommSerialNo();
			if(hashMap.containsKey(key)){
				commInfo.setNote(hashMap.get(key).toString());
			}
		}
		return lsResult;
	}

	/**
	 * 进口包装统计
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param wrapCode
	 *            包装种类编码
	 * @return List 是DzscWrapStat型，存放进口包装统计资料
	 */
	public List findImportWrapStat(List contracts, Date beginDate,
			Date endDate, String wrapCode) {
		List list = this.dzscAnalyseDao.findImportWrapStat(contracts,
				beginDate, endDate, wrapCode);
		List<DzscWrapStat> lsResult = new ArrayList<DzscWrapStat>();
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) list
					.get(i);
			Wrap wrap = declaration.getWrapType();
			DzscWrapStat wrapStat = new DzscWrapStat();
			wrapStat.setWrapName(wrap.getName());// 包装名
			Double grossWeight = declaration.getGrossWeight() == null ? 0
					: declaration.getGrossWeight();// 毛重
			Double netWeight = declaration.getNetWeight() == null ? 0
					: declaration.getNetWeight();// 净重
			wrapStat.setWrapWeight(grossWeight - netWeight);
			wrapStat.setWrapUnit("千克");
			wrapStat.setEmsNo(declaration.getEmsHeadH2k());// 手册号
			wrapStat.setCustomsDeclarationCode(declaration
					.getCustomsDeclarationCode());// 报关单号
			lsResult.add(wrapStat);
		}
		return lsResult;
	}

	// /**
	// * 料件，成品进出口报关情况表
	// * @param isImport 判断是否进口，true为进口
	// * @param commCode
	// * @param customer 客户Id
	// * @param impExpType 进出口类型
	// * @param beginDate 开始日期
	// * @param endDate 结束日期
	// * @param contract 手册通关备案表头No
	// * @param emsNo
	// * @return List 是型，
	// */
	// public List findImpExpCommodityStatus(boolean isImport, String commCode,
	// String customer, String impExpType, Date beginDate, Date endDate,
	// DzscEmsPorHead contract) {
	// String contractNo=contract.getImpContractNo();
	// String emsNo=contract.getEmsNo();
	// List list=this.contractAnalyseDao.findImpExpCommodityStatus(isImport,
	// commCode,
	// customer, impExpType, beginDate, endDate,
	// contractNo, emsNo);
	// List lsResult=new ArrayList();
	// return lsResult;
	// }
	/**
	 * 报关单预录入库查询
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffective
	 *            是否生效，true为生效
	 * @return List 是DzscCustomsDeclaration型，报关单表头
	 */
	public List findPreCustomsDeclaration(List contracts, Date beginDate,
			Date endDate, boolean isEffective) {
		return this.dzscAnalyseDao.findPreCustomsDeclaration(contracts,
				beginDate, endDate, isEffective);
	}

	/**
	 * 库存统计
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param materielType
	 *            物料类型
	 * @return List 库存分析资料
	 */
	public List findStorageStat(List contracts, Date beginDate, Date endDate,
			String materielType) {
		Map<String, DzscStorageStat> storageStatMap = new HashMap<String, DzscStorageStat>();
		List returnList = new ArrayList();
		if (materielType.equals(MaterielType.MATERIEL)) {
			Map<String, Map<String, Double>> imgUseMap = new HashMap<String, Map<String, Double>>();
			for (int i = 0; i < contracts.size(); i++) {
				DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
				HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
				List contractList = new ArrayList();
				contractList.add(contract);
				this.calContractImgUse(contractList, hmImgUse, beginDate,
						endDate, SearchType.NAME_SPEC_CODE_UNIT);
				imgUseMap.put(contract.getId(), hmImgUse);
			}
			List contractImgList = this.dzscAnalyseDao
					.findContractImgByParentId(contracts);
			for (int i = 0; i < contractImgList.size(); i++) {
				DzscEmsImgBill contractImg = (DzscEmsImgBill) contractImgList
						.get(i);
				/**
				 * 编码相同,名称,规格,单位相同 == key
				 */
				String key = contractImg.getComplex()
						+ (contractImg.getName() == null ? "" : contractImg
								.getName().trim())
						+ (contractImg.getSpec() == null ? "" : contractImg
								.getSpec().trim())
						+ (contractImg.getUnit() == null ? "" : contractImg
								.getUnit().getCode());
				if (storageStatMap.get(key) == null) {
					DzscStorageStat storageStat = new DzscStorageStat();
					storageStat.setComplexCode(contractImg.getComplex()
							.getCode());
					storageStat.setNameSpec((contractImg.getName() == null ? ""
							: contractImg.getName())
							+ (contractImg.getSpec() == null ? "" : "/"
									+ contractImg.getSpec()));
					storageStat.setUnit(contractImg.getUnit());
					List<TempDzscImpContractStat> tempContractStatList = new ArrayList<TempDzscImpContractStat>();
					for (int j = 0; j < contracts.size(); j++) {
						DzscEmsPorHead contract = (DzscEmsPorHead) contracts
								.get(j);
						HashMap<String, Double> hmImgUse = (HashMap<String, Double>) imgUseMap
								.get(contract.getId());
						// if (hmImgUse == null) {
						// hmImgUse = new HashMap<String, Double>();
						// List contractList = new ArrayList();
						// contractList.add(contract);
						// this.calContractImgUse(contractList, hmImgUse,
						// beginDate, endDate,
						// SearchType.NAME_SPEC_CODE_UNIT);
						// imgUseMap.put(contract.getId(), hmImgUse);
						// }
						TempDzscImpContractStat tempImpContractStat = new TempDzscImpContractStat();
						tempImpContractStat.setContractId(contract.getId());
						if (contract.getId().equals(
								contractImg.getDzscEmsPorHead().getId())) {
							tempImpContractStat = makeTempImpContractStat(
									beginDate, endDate, contractImg, contract,
									tempImpContractStat, hmImgUse);
						}
						/**
						 * 加入到List中
						 */
						tempContractStatList.add(tempImpContractStat);
					}
					storageStat.setTempContractStatList(tempContractStatList);
					storageStatMap.put(key, storageStat);
				} else { // 存在key
					DzscStorageStat storageStat = storageStatMap.get(key);
					List tempContractStatList = storageStat
							.getTempContractStatList();
					for (int j = 0; j < tempContractStatList.size(); j++) {
						TempDzscImpContractStat tempImpContractStat = (TempDzscImpContractStat) tempContractStatList
								.get(j);
						if (tempImpContractStat.getContractId().equals(
								contractImg.getDzscEmsPorHead().getId())) {
							HashMap<String, Double> hmImgUse = (HashMap<String, Double>) imgUseMap
									.get(tempImpContractStat.getContractId());
							// if (hmImgUse == null) {
							// hmImgUse = new HashMap<String, Double>();
							// List contractList = new ArrayList();
							// contractList.add(contractImg
							// .getDzscEmsPorHead());
							// this.calContractImgUse(contractList, hmImgUse,
							// beginDate, endDate,
							// SearchType.NAME_SPEC_CODE_UNIT);
							// imgUseMap.put(tempImpContractStat
							// .getContractId(), hmImgUse);
							// }
							tempImpContractStat = makeTempImpContractStat(
									beginDate, endDate, contractImg,
									contractImg.getDzscEmsPorHead(),
									tempImpContractStat, hmImgUse);
							break;
						}
					}
				}
				Iterator values = storageStatMap.values().iterator();
				while (values.hasNext()) {
					Double totals = 0.0;
					DzscStorageStat dzscstorageStat = (DzscStorageStat) values
							.next();
					for (int q = 0; q < dzscstorageStat
							.getTempContractStatList().size(); q++) {
						TempDzscImpContractStat tempDzscImpContractStat = (TempDzscImpContractStat) dzscstorageStat
								.getTempContractStatList().get(q);
						totals += (CommonUtils
								.getDoubleExceptNull(tempDzscImpContractStat
										.getTotalImpAmount()) - CommonUtils
								.getDoubleExceptNull(tempDzscImpContractStat
										.getFinishProductDosageAmount()));
					}
					dzscstorageStat.setTotalize(totals);
				}

			}

		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			List contractExgList = this.dzscAnalyseDao
					.findContractExgByParentId(contracts);
			for (int i = 0; i < contractExgList.size(); i++) {
				DzscEmsExgBill contractExg = (DzscEmsExgBill) contractExgList
						.get(i);
				/**
				 * 编码相同,名称,规格,单位相同 == key
				 */
				String key = contractExg.getComplex()
						+ (contractExg.getName() == null ? "" : contractExg
								.getName().trim())
						+ (contractExg.getSpec() == null ? "" : contractExg
								.getSpec().trim())
						+ (contractExg.getUnit() == null ? "" : contractExg
								.getUnit().getCode());
				if (storageStatMap.get(key) == null) {
					DzscStorageStat storageStat = new DzscStorageStat();
					storageStat.setComplexCode(contractExg.getComplex()
							.getCode());
					storageStat.setNameSpec((contractExg.getName() == null ? ""
							: contractExg.getName())
							+ (contractExg.getSpec() == null ? "" : "/"
									+ contractExg.getSpec()));
					storageStat.setUnit(contractExg.getUnit());
					List<TempDzscExpContractStat> tempContractStatList = new ArrayList<TempDzscExpContractStat>();
					for (int j = 0; j < contracts.size(); j++) {
						DzscEmsPorHead contract = (DzscEmsPorHead) contracts
								.get(j);
						TempDzscExpContractStat tempExpContractStat = new TempDzscExpContractStat();
						tempExpContractStat.setContractId(contract.getId());
						if (contract.getId().equals(
								contractExg.getDzscEmsPorHead().getId())) {
							tempExpContractStat = makeTempExpContractStat(
									beginDate, endDate, contractExg, contract,
									tempExpContractStat);

						}
						tempContractStatList.add(tempExpContractStat);
					}
					storageStat.setTempContractStatList(tempContractStatList);
					storageStat.setNote(contractExg.getNote());
					storageStatMap.put(key, storageStat);
				} else {
					DzscStorageStat storageStat = storageStatMap.get(key);
					List tempContractStatList = storageStat
							.getTempContractStatList();
					for (int j = 0; j < tempContractStatList.size(); j++) {
						TempDzscExpContractStat tempExpContractStat = (TempDzscExpContractStat) tempContractStatList

						.get(j);
						if (tempExpContractStat.getContractId().equals(
								contractExg.getDzscEmsPorHead().getId())) {
							tempExpContractStat = makeTempExpContractStat(
									beginDate, endDate, contractExg,
									contractExg.getDzscEmsPorHead(),
									tempExpContractStat);
						}
					}
				}

			}
		}
		returnList.addAll(storageStatMap.values());
		return returnList;
	}

	/**
	 * 生成库存统计成品列表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            手册通关备案表头Exg
	 * @param contract
	 *            手册通关备案表头
	 * @param tempExpContractStat
	 *            存放库存分析资料，成品
	 * @return TempDzscExpContractStat 存放库存分析资料，成品
	 */
	private TempDzscExpContractStat makeTempExpContractStat(Date beginDate,
			Date endDate, DzscEmsExgBill contractExg, DzscEmsPorHead contract,
			TempDzscExpContractStat tempExpContractStat) {
		/**
		 * 大单出口量 = 所有出口报关单数量之和(成品出口,转厂出口) 生效的报关单
		 */
		Double orderExpAmount = this.dzscAnalyseDao.findOrderExpAmount(contract
				.getEmsNo(), contractExg.getSeqNum(), beginDate, endDate, true);
		tempExpContractStat.setOrderExpAmount(CommonUtils.getDoubleByDigit(
				orderExpAmount, 3));
		/**
		 * 返工数量 = 所有返工复出类型数量之和
		 */
		Double returnAmount = this.dzscAnalyseDao.findReturnAmount(contract
				.getEmsNo(), contractExg.getSeqNum(), beginDate, endDate);
		tempExpContractStat.setReturnAmount(CommonUtils.getDoubleByDigit(
				returnAmount, 3));
		/**
		 * 总出口量 = 大单出口量 - 返工数量
		 */
		Double totalExpAmount = orderExpAmount - returnAmount;
		tempExpContractStat.setTotalExpAmount(CommonUtils.getDoubleByDigit(
				totalExpAmount, 3));
		/**
		 * 合同定量 = 合同成品数量
		 */
		Double contractRation = contractExg.getAmount();
		tempExpContractStat.setContractRation(CommonUtils.getDoubleByDigit(
				contractRation, 3));
		/**
		 * 可出口余数 = 合同定量 - 总出口量
		 */
		tempExpContractStat.setCanExpRemain(CommonUtils.getDoubleByDigit(
				contractRation - totalExpAmount, 3));
		/**
		 * 现出口数量
		 * 
		 * 大单出口量 = 所有出口报关单数量之和(成品出口,转厂出口) 生效的报关单
		 */
		Double orderExpAmountNoEffective = this.dzscAnalyseDao
				.findOrderExpAmount(contract.getEmsNo(), contractExg
						.getSeqNum(), beginDate, endDate, false);
		tempExpContractStat
				.setNowExpAmount(CommonUtils.getDoubleByDigit(contractRation
						- totalExpAmount - orderExpAmountNoEffective, 3));
		return tempExpContractStat;
	}

	/**
	 * 生成库存统计料件列表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contractImg
	 *            手册通关备案料件
	 * @param contract
	 *            手册通关备案表头
	 * @param tempImpContractStat
	 *            库存分析资料，料件
	 * @return TempDzscImpContractStat 库存分析资料，料件
	 */
	private TempDzscImpContractStat makeTempImpContractStat(Date beginDate,
			Date endDate, DzscEmsImgBill contractImg, DzscEmsPorHead contract,
			TempDzscImpContractStat tempImpContractStat,
			HashMap<String, Double> hmImgUse) {

		// /**
		// * 退厂返工
		// */
		// Double backFactoryReworkDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.BACK_FACTORY_REWORK);
		//
		// /**
		// * 出口成品
		// */
		// Double directExportDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.DIRECT_EXPORT);
		//
		// /**
		// * 转厂出口
		// */
		// Double transferFactoryExportDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.TRANSFER_FACTORY_EXPORT);
		//
		// /**
		// * 返工复出
		// */
		// Double reworkExportDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.REWORK_EXPORT);
		String key = contractImg.getComplex().getCode()
				+ (contractImg.getName() == null ? "" : "/"
						+ contractImg.getName())
				+ (contractImg.getSpec() == null ? "" : "/"
						+ contractImg.getSpec()) + "/"
				+ contractImg.getUnit().getCode();
		/**
		 * 成品使用量
		 */
		// Double finishProductDosageAmount = directExportDosageAmount
		// + transferFactoryExportDosageAmount + reworkExportDosageAmount
		// - backFactoryReworkDosageAmount;
		Double finishProductDosageAmount = CommonUtils
				.getDoubleExceptNull(hmImgUse.get(key));
		tempImpContractStat.setFinishProductDosageAmount(CommonUtils
				.getDoubleByDigit(finishProductDosageAmount, 3));
		/**
		 * 进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂，余料转入类型)
		 */
		Double impAmount = this.dzscAnalyseDao.findImpAmount(contract
				.getEmsNo(), contractImg.getSeqNum(), beginDate, endDate);
		/**
		 * 大单进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂)
		 */
		Double orderImpAmount = this.dzscAnalyseDao.findOrderImpAmount(contract
				.getEmsNo(), contractImg.getSeqNum(), beginDate, endDate);
		tempImpContractStat.setOrderImpAmount(CommonUtils.getDoubleByDigit(
				orderImpAmount, 3));
		/**
		 * 退料出口量 = 所有使用该合同出口报关单数量之和(退料出口类型)
		 */
		Double backMaterielExpAmount = this.dzscAnalyseDao
				.findBackMaterielExpAmount(contract.getEmsNo(), contractImg
						.getSeqNum(), beginDate, endDate);
		tempImpContractStat.setBackMaterielExpAmount(backMaterielExpAmount);

		/**
		 * 退料出口量 = 所有使用该合同出口报关单数量之和(退料出口类型)
		 */
		Double backMaterielExchangeAmount = this.dzscAnalyseDao
				.findBackMaterielExchangeAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate);
		tempImpContractStat.setBackMaterielExpAmount(backMaterielExpAmount);
		/**
		 * 总进口量 = 进口量 - 退料出口量(退换数量)
		 */
		Double totalImpAmount = impAmount - backMaterielExchangeAmount;
		tempImpContractStat.setTotalImpAmount(CommonUtils.getDoubleByDigit(
				totalImpAmount, 3));
		/**
		 * 现进口量 = 合同备案数量 - 总进口量
		 */
		tempImpContractStat.setNowImpAmount(CommonUtils.getDoubleByDigit(
				contractImg.getAmount() - totalImpAmount, 3));
		/**
		 * 余料库存 = 总进口量 - 成品使用量 - 余料结转之合
		 */
		Double remainForwardAmount = this.dzscAnalyseDao
				.findRemainForwardAmount(contract.getEmsNo(), contractImg
						.getSeqNum(), beginDate, endDate);
		tempImpContractStat.setRemainStorageAmount(CommonUtils
				.getDoubleByDigit(totalImpAmount - finishProductDosageAmount
						- remainForwardAmount, 3));
		return tempImpContractStat;
	}

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @return List 是TempDzscContractImg型，存放合同料件的临时资料
	 */
	public List findTempContractImg() {
		List<TempDzscContractImg> returnList = new ArrayList<TempDzscContractImg>();
		List tempContractImgList = this.dzscAnalyseDao.findTempContractImg();
		for (int i = 0; i < tempContractImgList.size(); i++) {
			Object[] objects = (Object[]) tempContractImgList.get(i);
			TempDzscContractImg temp = new TempDzscContractImg();
			temp
					.setComplexCode(objects[0] == null ? "" : objects[0]
							.toString());
			temp.setName(objects[1] == null ? "" : objects[1].toString());
			temp.setSpec(objects[2] == null ? "" : objects[2].toString());
			temp.setUnit((Unit) objects[3]);
			temp.setUnitWeight(objects[4] == null ? null : Double
					.valueOf(objects[4].toString()));
			returnList.add(temp);
		}
		return returnList;
	}

	/**
	 * 查找料件执行情况
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 料件执行情况资料
	 */
	public List findImpMaterielExeState(List contracts,
			TempDzscContractImg tempContractImg) {
		List returnList = new ArrayList();
		returnList.addAll(this.findImpMaterielExeStateByImportMateriel(
				contracts, tempContractImg));
		returnList.addAll(this.findImpMaterielExeStateByExportMateriel(
				contracts, tempContractImg));
		returnList.addAll(this.findImpMaterielExeStateByImportProduct(
				contracts, tempContractImg));
		returnList.addAll(this.findImpMaterielExeStateByExportProduct(
				contracts, tempContractImg));
		return returnList;
	}

	/**
	 * 查找料件执行情况 来自进口料件
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 料件执行情况资料
	 */
	private List findImpMaterielExeStateByImportMateriel(List contracts,
			TempDzscContractImg tempContractImg) {
		List returnList = new ArrayList();
		for (int k = 0; k < contracts.size(); k++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(k);
			List importMaterielList = this.dzscAnalyseDao
					.findImpMaterielExeStateByImportMateriel(contract,
							tempContractImg);
			/**
			 * 把相同报关单,相同料件序号的料件 SUM()
			 */
			Map<String, DzscImpMaterielExeState> map = new HashMap<String, DzscImpMaterielExeState>();
			for (int i = 0; i < importMaterielList.size(); i++) {
				DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) importMaterielList
						.get(i);
				BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
						.getBaseCustomsDeclaration();
				String key = baseCustomsDeclaration.getEmsHeadH2k()
						+ bcsCustomsDeclarationCommInfo.getCommSerialNo()
						+ baseCustomsDeclaration.getCustomsDeclarationCode();
				if (map.get(key) == null) {
					DzscImpMaterielExeState impMaterielExeState = new DzscImpMaterielExeState();
					impMaterielExeState
							.setApplyToCustomBillNo(baseCustomsDeclaration
									.getCustomsDeclarationCode());
					impMaterielExeState
							.setApplyToCustomDate(baseCustomsDeclaration
									.getDeclarationDate());
					impMaterielExeState.setEmsNo(baseCustomsDeclaration
							.getEmsHeadH2k());
					impMaterielExeState
							.setImpAmount(bcsCustomsDeclarationCommInfo
									.getCommAmount());
					impMaterielExeState
							.setExplain(bcsCustomsDeclarationCommInfo
									.getCommName()
									+ "/"
									+ (bcsCustomsDeclarationCommInfo
											.getCommSpec() == null ? ""
											: bcsCustomsDeclarationCommInfo
													.getCommSpec()));
					impMaterielExeState.setImpExpType(baseCustomsDeclaration
							.getImpExpType());
					if (bcsCustomsDeclarationCommInfo.getCommAmount() > 0)
						map.put(key, impMaterielExeState);
				} else {
					DzscImpMaterielExeState temp = map.get(key);
					temp
							.setImpAmount((temp.getImpAmount() == null ? 0.0
									: temp.getImpAmount())
									+ (bcsCustomsDeclarationCommInfo
											.getCommAmount() == null ? 0.0
											: bcsCustomsDeclarationCommInfo
													.getCommAmount()));
				}

			}
			returnList.addAll(map.values());
		}
		return returnList;
	}

	/**
	 * 查找料件执行情况 来自出口料件
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 料件执行情况资料
	 */
	private List findImpMaterielExeStateByExportMateriel(List contracts,
			TempDzscContractImg tempContractImg) {
		List returnList = new ArrayList();
		for (int k = 0; k < contracts.size(); k++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(k);
			List exportMaterielList = this.dzscAnalyseDao
					.findImpMaterielExeStateByExportMateriel(contract,
							tempContractImg);
			/**
			 * 把相同报关单,相同料件序号的料件 SUM()
			 */
			Map<String, DzscImpMaterielExeState> map = new HashMap<String, DzscImpMaterielExeState>();
			for (int i = 0; i < exportMaterielList.size(); i++) {
				DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) exportMaterielList
						.get(i);
				BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
						.getBaseCustomsDeclaration();
				String key = baseCustomsDeclaration.getEmsHeadH2k()
						+ bcsCustomsDeclarationCommInfo.getCommSerialNo()
						+ baseCustomsDeclaration.getCustomsDeclarationCode();
				if (map.get(key) == null) {
					DzscImpMaterielExeState impMaterielExeState = new DzscImpMaterielExeState();
					impMaterielExeState
							.setApplyToCustomBillNo(baseCustomsDeclaration
									.getCustomsDeclarationCode());
					impMaterielExeState
							.setApplyToCustomDate(baseCustomsDeclaration
									.getDeclarationDate());
					impMaterielExeState.setEmsNo(baseCustomsDeclaration
							.getEmsHeadH2k());
					impMaterielExeState
							.setExpAmount(bcsCustomsDeclarationCommInfo
									.getCommAmount());
					impMaterielExeState
							.setExplain(bcsCustomsDeclarationCommInfo
									.getCommName()
									+ "/"
									+ (bcsCustomsDeclarationCommInfo
											.getCommSpec() == null ? ""
											: bcsCustomsDeclarationCommInfo
													.getCommSpec()));
					impMaterielExeState.setImpExpType(baseCustomsDeclaration
							.getImpExpType());
					if (bcsCustomsDeclarationCommInfo.getCommAmount() > 0)
						map.put(key, impMaterielExeState);
				} else {
					DzscImpMaterielExeState temp = map.get(key);
					temp
							.setExpAmount((temp.getExpAmount() == null ? 0.0
									: temp.getExpAmount())
									+ (bcsCustomsDeclarationCommInfo
											.getCommAmount() == null ? 0.0
											: bcsCustomsDeclarationCommInfo
													.getCommAmount()));
				}

			}
			returnList.addAll(map.values());
		}
		return returnList;
	}

	/**
	 * 查找料件执行情况 来自进口成品(退厂返工类型)
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 料件执行情况资料
	 */
	private List findImpMaterielExeStateByImportProduct(List contracts,
			TempDzscContractImg tempContractImg) {
		List returnList = new ArrayList();
		for (int k = 0; k < contracts.size(); k++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(k);
			List importProductList = this.dzscAnalyseDao
					.findImpMaterielExeStateByImportProduct(contract,
							tempContractImg);
			/**
			 * 把相同报关单,相同料件序号的料件 SUM()
			 */
			Map<String, DzscImpMaterielExeState> map = new HashMap<String, DzscImpMaterielExeState>();
			for (int i = 0; i < importProductList.size(); i++) {
				DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) importProductList
						.get(i);
				BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
						.getBaseCustomsDeclaration();
				String key = baseCustomsDeclaration.getEmsHeadH2k()
						+ bcsCustomsDeclarationCommInfo.getCommSerialNo()
						+ baseCustomsDeclaration.getCustomsDeclarationCode();
				if (map.get(key) == null) {
					DzscImpMaterielExeState impMaterielExeState = new DzscImpMaterielExeState();
					impMaterielExeState
							.setApplyToCustomBillNo(baseCustomsDeclaration
									.getCustomsDeclarationCode());
					impMaterielExeState
							.setApplyToCustomDate(baseCustomsDeclaration
									.getDeclarationDate());
					impMaterielExeState.setEmsNo(baseCustomsDeclaration
							.getEmsHeadH2k());
					/**
					 * 料件数量 = 成品数量 * 单项用量
					 */
					Double productAmount = bcsCustomsDeclarationCommInfo
							.getCommAmount() == null ? 0.0
							: bcsCustomsDeclarationCommInfo.getCommAmount();
					Double unitDosage = this.dzscAnalyseDao.getUnitDosage(
							contract.getId(), bcsCustomsDeclarationCommInfo
									.getCommSerialNo(), tempContractImg);
					impMaterielExeState
							.setImpAmount(productAmount * unitDosage);
					impMaterielExeState
							.setExplain(bcsCustomsDeclarationCommInfo
									.getCommName()
									+ "/"
									+ (bcsCustomsDeclarationCommInfo
											.getCommSpec() == null ? ""
											: bcsCustomsDeclarationCommInfo
													.getCommSpec()));
					impMaterielExeState.setImpExpType(baseCustomsDeclaration
							.getImpExpType());
					if (productAmount * unitDosage > 0)
						map.put(key, impMaterielExeState);
				} else {
					DzscImpMaterielExeState temp = map.get(key);
					/**
					 * 料件数量 = 成品数量 * 单项用量
					 */
					Double productAmount = bcsCustomsDeclarationCommInfo
							.getCommAmount() == null ? 0.0
							: bcsCustomsDeclarationCommInfo.getCommAmount();
					Double unitDosage = this.dzscAnalyseDao.getUnitDosage(
							contract.getId(), bcsCustomsDeclarationCommInfo
									.getCommSerialNo(), tempContractImg);
					temp.setImpAmount((temp.getImpAmount() == null ? 0.0 : temp
							.getImpAmount())
							+ (productAmount * unitDosage));
				}
			}
			returnList.addAll(map.values());
		}
		return returnList;
	}

	/**
	 * 查找料件执行情况 来自进口成品(退厂返工类型)
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 料件执行情况资料
	 */
	private List findImpMaterielExeStateByExportProduct(List contracts,
			TempDzscContractImg tempContractImg) {
		List returnList = new ArrayList();
		for (int k = 0; k < contracts.size(); k++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(k);
			List exportProductList = this.dzscAnalyseDao
					.findImpMaterielExeStateByExportProduct(contract,
							tempContractImg);
			/**
			 * 把相同报关单,相同料件序号的料件 SUM()
			 */
			Map<String, DzscImpMaterielExeState> map = new HashMap<String, DzscImpMaterielExeState>();
			for (int i = 0; i < exportProductList.size(); i++) {
				DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) exportProductList
						.get(i);
				BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
						.getBaseCustomsDeclaration();
				String key = baseCustomsDeclaration.getEmsHeadH2k()
						+ bcsCustomsDeclarationCommInfo.getCommSerialNo()
						+ baseCustomsDeclaration.getCustomsDeclarationCode();
				if (map.get(key) == null) {
					DzscImpMaterielExeState impMaterielExeState = new DzscImpMaterielExeState();
					impMaterielExeState
							.setApplyToCustomBillNo(baseCustomsDeclaration
									.getCustomsDeclarationCode());
					impMaterielExeState
							.setApplyToCustomDate(baseCustomsDeclaration
									.getDeclarationDate());
					impMaterielExeState.setEmsNo(baseCustomsDeclaration
							.getEmsHeadH2k());
					/**
					 * 料件数量 = 成品数量 * 单项用量
					 */
					Double productAmount = bcsCustomsDeclarationCommInfo
							.getCommAmount() == null ? 0.0
							: bcsCustomsDeclarationCommInfo.getCommAmount();
					Double unitDosage = this.dzscAnalyseDao.getUnitDosage(
							contract.getId(), bcsCustomsDeclarationCommInfo
									.getCommSerialNo(), tempContractImg);
					impMaterielExeState
							.setExpAmount(productAmount * unitDosage);
					impMaterielExeState
							.setExplain(bcsCustomsDeclarationCommInfo
									.getCommName()
									+ "/"
									+ (bcsCustomsDeclarationCommInfo
											.getCommSpec() == null ? ""
											: bcsCustomsDeclarationCommInfo
													.getCommSpec()));
					impMaterielExeState.setImpExpType(baseCustomsDeclaration
							.getImpExpType());
					if (productAmount * unitDosage > 0)
						map.put(key, impMaterielExeState);
				} else {
					DzscImpMaterielExeState temp = map.get(key);
					/**
					 * 料件数量 = 成品数量 * 单项用量
					 */
					Double productAmount = bcsCustomsDeclarationCommInfo
							.getCommAmount() == null ? 0.0
							: bcsCustomsDeclarationCommInfo.getCommAmount();
					Double unitDosage = this.dzscAnalyseDao.getUnitDosage(
							contract.getId(), bcsCustomsDeclarationCommInfo
									.getCommSerialNo(), tempContractImg);
					temp.setExpAmount((temp.getExpAmount() == null ? 0.0 : temp
							.getExpAmount())
							+ (productAmount * unitDosage));
				}
			}
			returnList.addAll(map.values());
		}
		return returnList;
	}

	/**
	 * 查找料件执行明细情况 来自[进口料件,料件转厂,退料出口],
	 * 
	 * @param contractImg
	 *            手册通关备案料件
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件执行明细资料资料
	 */
	public List findImpMaterielExeDetail(DzscEmsImgBill contractImg,
			int impExpType, Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		DzscEmsPorHead contract = contractImg.getDzscEmsPorHead();
		if (impExpType == -1) {
			returnList.addAll(findImpMaterielExeDetailByDirectImport(contract,
					contractImg, beginDate, endDate));
			returnList.addAll(findImpMaterielExeDetailByTransferFactoryImport(
					contract, contractImg, beginDate, endDate));
			returnList.addAll(findImpMaterielExeDetailByBackMaterielExport(
					contract, contractImg, beginDate, endDate));
		} else if (impExpType == ImpExpType.DIRECT_IMPORT) {
			returnList.addAll(findImpMaterielExeDetailByDirectImport(contract,
					contractImg, beginDate, endDate));
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			returnList.addAll(findImpMaterielExeDetailByTransferFactoryImport(
					contract, contractImg, beginDate, endDate));
		} else if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
			returnList.addAll(findImpMaterielExeDetailByBackMaterielExport(
					contract, contractImg, beginDate, endDate));
		}
		return returnList;
	}

	/**
	 * 获得料件直接进口
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param contractImg
	 *            手册通关备案料件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件执行明细资料资料
	 */
	private List findImpMaterielExeDetailByDirectImport(
			DzscEmsPorHead contract, DzscEmsImgBill contractImg,
			Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		List tempList = this.dzscAnalyseDao.findImpMaterielExeDetail(contract,
				contractImg, ImpExpType.DIRECT_IMPORT, beginDate, endDate);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, DzscImpMaterielExeDetail> map = new HashMap<String, DzscImpMaterielExeDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				DzscImpMaterielExeDetail impMaterielExeDetail = new DzscImpMaterielExeDetail();
				impMaterielExeDetail
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeDetail
						.setApplyToCustomDate(baseCustomsDeclaration
								.getDeclarationDate());
				impMaterielExeDetail.setConveyance(baseCustomsDeclaration
						.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeDetail
						.setMaterielImportAmount(materielImportAmount);
				impMaterielExeDetail.setCustomer(baseCustomsDeclaration
						.getCustomer() == null ? "" : baseCustomsDeclaration
						.getCustomer().getName());
				map.put(key, impMaterielExeDetail);
			} else {
				DzscImpMaterielExeDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp
						.setMaterielImportAmount((temp
								.getMaterielImportAmount() == null ? 0.0 : temp
								.getMaterielImportAmount())
								+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得料件料件转厂
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param contractImg
	 *            手册通关备案料件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件执行明细资料
	 */
	private List findImpMaterielExeDetailByTransferFactoryImport(
			DzscEmsPorHead contract, DzscEmsImgBill contractImg,
			Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		List tempList = this.dzscAnalyseDao.findImpMaterielExeDetail(contract,
				contractImg, ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate,
				endDate);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, DzscImpMaterielExeDetail> map = new HashMap<String, DzscImpMaterielExeDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				DzscImpMaterielExeDetail impMaterielExeDetail = new DzscImpMaterielExeDetail();
				impMaterielExeDetail
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeDetail
						.setApplyToCustomDate(baseCustomsDeclaration
								.getDeclarationDate());
				impMaterielExeDetail.setConveyance(baseCustomsDeclaration
						.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeDetail
						.setTransferFactoryAmount(materielImportAmount);
				impMaterielExeDetail.setCustomer(baseCustomsDeclaration
						.getCustomer() == null ? "" : baseCustomsDeclaration
						.getCustomer().getName());
				map.put(key, impMaterielExeDetail);
			} else {
				DzscImpMaterielExeDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp
						.setTransferFactoryAmount((temp
								.getTransferFactoryAmount() == null ? 0.0
								: temp.getTransferFactoryAmount())
								+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得料料来自退料出口
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param contractImg
	 *            手册通关备案料件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件执行明细资料
	 */
	private List findImpMaterielExeDetailByBackMaterielExport(
			DzscEmsPorHead contract, DzscEmsImgBill contractImg,
			Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		List tempList = this.dzscAnalyseDao.findImpMaterielExeDetail(contract,
				contractImg, ImpExpType.BACK_MATERIEL_EXPORT, beginDate,
				endDate);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, DzscImpMaterielExeDetail> map = new HashMap<String, DzscImpMaterielExeDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				DzscImpMaterielExeDetail impMaterielExeDetail = new DzscImpMaterielExeDetail();
				impMaterielExeDetail
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeDetail
						.setApplyToCustomDate(baseCustomsDeclaration
								.getDeclarationDate());
				impMaterielExeDetail.setConveyance(baseCustomsDeclaration
						.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeDetail
						.setBackMaterielExportAmount(materielImportAmount);
				impMaterielExeDetail.setCustomer(baseCustomsDeclaration
						.getCustomer() == null ? "" : baseCustomsDeclaration
						.getCustomer().getName());
				map.put(key, impMaterielExeDetail);
			} else {
				DzscImpMaterielExeDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setTransferFactoryAmount((temp
						.getBackMaterielExportAmount() == null ? 0.0 : temp
						.getBackMaterielExportAmount())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 计算成品所有的料件的耗用总量
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param hmImgUse
	 *            合同备案BOM资料对应的数量
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 */
	private void calContractImgUse(List contracts,
			HashMap<String, Double> hmImgUse, Date beginDate, Date endDate,
			int searchType) {
		for (int i = 0; i < contracts.size(); i++) {
			DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) contracts.get(i);
			List<DzscExpProductStat> lsProductStat = this.dzscStatLogic
					.findExpProductStat(dzscEmsPorHead, beginDate, endDate);
			List<DzscEmsImgBill> listImg = this.dzscDao
					.findDzscEmsImgBill(dzscEmsPorHead);
			Map<Integer, DzscEmsImgBill> mapImg = new HashMap<Integer, DzscEmsImgBill>();
			for (DzscEmsImgBill img : listImg) {
				mapImg.put(img.getSeqNum(), img);
			}
			for (DzscExpProductStat productStat : lsProductStat) {
				double exgAmount = CommonUtils.getDoubleExceptNull(productStat
						.getExpTotalAmont());
				// System.out.println("sssssssssss:" +productStat.getCommName()+
				// exgAmount);
				List<DzscEmsBomBill> list = this.dzscDao
						.findDzscEmsBomBillByExgSeqNum(dzscEmsPorHead, Integer
								.valueOf(productStat.getSeqNum()));
				for (DzscEmsBomBill dzscEmsBomBill : list) {
					if (dzscEmsBomBill.getImgSeqNum() == null) {
						// System.out.println("eeeeeeeeeeeeeeeeeeee: is null");
						continue;
					}
					String key = "";
					DzscEmsImgBill img = mapImg.get(dzscEmsBomBill
							.getImgSeqNum());
					if (img == null) {
						continue;
					}
					if (searchType == SearchType.NAME) {
						// impMaterielExeStat.setNameSpec(objects[0] == null ?
						// "" : objects[0]
						// .toString());
						key = (img.getName() == null ? "" : img.getName());
					} else if (searchType == SearchType.NAME_SPEC) {
						key = (img.getName() == null ? "" : img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec());
					} else if (searchType == SearchType.NAME_SPEC_CODE) {
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec());
					} else if (searchType == SearchType.CODE) {
						key = img.getComplex().getCode();
					} else if (searchType == SearchType.CODE_NAME) {
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName());
					} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec()) + "/"
								+ img.getUnit().getCode();
					}
					// double imgAmount = exgAmount
					// * CommonUtils.getDoubleExceptNull(contractBom
					// .getUnitDosage());
					double imgAmount = exgAmount
							* CommonUtils.getDoubleExceptNull(CommonUtils
									.getDoubleExceptNull(dzscEmsBomBill
											.getUnitWare())
									/ (1 - CommonUtils
											.getDoubleExceptNull(dzscEmsBomBill
													.getWare()) / 100.0));
					double hasAmount = CommonUtils.getDoubleExceptNull(hmImgUse
							.get(key));
					// System.out.println(productStat.getCommName()+"::"+contractBom.getName()+"hasAmount:"+hasAmount);
					hmImgUse.put(key, imgAmount + hasAmount);
					// System.out.println("ddddddddddddddd: is "
					// + (imgAmount + hasAmount));
				}
			}
		}
		// List<ExpProductStat> lsProductStat =
		// this.findExpProductStat(contract,
		// beginDate, endDate, state);

	}

	/**
	 * 查找进出口料件统计
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @return List 进口料件统计资料
	 */
	public List findImpMaterielExeStat(List contracts, Date beginDate,
			Date endDate, int searchType) {
		List<DzscImpMaterielExeStat> returnList = new ArrayList<DzscImpMaterielExeStat>();
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		calContractImgUse(contracts, hmImgUse, beginDate, endDate, searchType);
		List contractImgList = this.dzscAnalyseDao.findContractImg(searchType,
				contracts);
		for (int i = 0; i < contractImgList.size(); i++) {
			DzscImpMaterielExeStat impMaterielExeStat = new DzscImpMaterielExeStat();
			Object[] objects = null;
			if (contractImgList.get(i).getClass().isArray()) {
				objects = (Object[]) contractImgList.get(i);
			} else {
				objects = new Object[] { contractImgList.get(i) };
			}
			impMaterielExeStat = this.makeImpMaterielExeStat(contracts,
					objects, impMaterielExeStat, hmImgUse, searchType ,beginDate,endDate);
			returnList.add(impMaterielExeStat);
		}
		/**
		 * 加一条空的记录
		 */
		returnList.add(new DzscImpMaterielExeStat());
		/**
		 * 加入统计记录
		 */
		DzscImpMaterielExeStat impMaterielExeStatTotal = makeTotalImpMaterielExeStat(returnList);
		returnList.add(impMaterielExeStatTotal);
		return returnList;
	}

	/**
	 * 对进口料件进行统计
	 * 
	 * @param returnList
	 *            进口料件统计
	 * @return DzscImpMaterielExeStat 进口料件统计资料
	 */
	private DzscImpMaterielExeStat makeTotalImpMaterielExeStat(
			List<DzscImpMaterielExeStat> returnList) {
		Double contractTotalAmount = 0.0; // 合同总订量
		Double totalImpAmount = 0.0; // 总进口量
		Double orderImpAmount = 0.0; // 大单进口量
		Double materielImpAmount = 0.0; // 料件进口总量
		Double transferImpAmount = 0.0; // 转厂进口总量
		Double backMaterielExpAmount = 0.0; // 退料出口总量
		Double reworkExpAmount = 0.0; // 退料复出总量
		Double backMaterielExchange = 0.0; // 退料退换总量
		Double expFinishProductAmount = 0.0; // 出口成品使用总量
		Double remainStat = 0.0; // 余料情况
		Double lackStat = 0.0; // 缺料情况
		Double canImpAmount = 0.0; // 可进口总量
		Double remainForwordExpAmount = 0.0; // 余料结转转出
		Double remainForwordImpAmount = 0.0; // 余料结转转入
		for (int i = 0; i < returnList.size(); i++) {
			DzscImpMaterielExeStat impMaterielExeStat = (DzscImpMaterielExeStat) returnList
					.get(i);
			contractTotalAmount += impMaterielExeStat.getContractTotalAmount() == null ? 0.0
					: impMaterielExeStat.getContractTotalAmount(); // 合同总订量
			totalImpAmount += impMaterielExeStat.getTotalImpAmount() == null ? 0.0
					: impMaterielExeStat.getTotalImpAmount();// 总进口量
			orderImpAmount += impMaterielExeStat.getOrderImpAmount() == null ? 0.0
					: impMaterielExeStat.getOrderImpAmount(); // 大单进口量
			materielImpAmount += impMaterielExeStat.getMaterielImpAmount() == null ? 0.0
					: impMaterielExeStat.getMaterielImpAmount(); // 料件进口总量
			transferImpAmount += impMaterielExeStat.getTransferImpAmount() == null ? 0.0
					: impMaterielExeStat.getTransferImpAmount();
			/**
			 * 转厂进口总量
			 */
			backMaterielExpAmount += impMaterielExeStat
					.getBackMaterielExpAmount() == null ? 0.0
					: impMaterielExeStat.getBackMaterielExpAmount();
			/**
			 * 退料出口总量
			 */
			reworkExpAmount += impMaterielExeStat.getReworkExpAmount() == null ? 0.0
					: impMaterielExeStat.getReworkExpAmount();
			/**
			 * 退料复出总量
			 */
			backMaterielExchange += impMaterielExeStat
					.getBackMaterielExchange() == null ? 0.0
					: impMaterielExeStat.getBackMaterielExchange();
			/**
			 * 退料退换总量
			 */
			expFinishProductAmount += impMaterielExeStat
					.getExpFinishProductAmount() == null ? 0.0
					: impMaterielExeStat.getExpFinishProductAmount();
			/**
			 * 出口成品使用总量
			 */
			remainStat += impMaterielExeStat.getRemainStat() == null ? 0.0
					: impMaterielExeStat.getRemainStat();
			/**
			 * 余料情况
			 */
			lackStat += impMaterielExeStat.getLackStat() == null ? 0.0
					: impMaterielExeStat.getLackStat();
			/**
			 * 缺料情况
			 */
			canImpAmount += impMaterielExeStat.getCanImpAmount() == null ? 0.0
					: impMaterielExeStat.getCanImpAmount();

			/**
			 * 可进口总量
			 */
			remainForwordExpAmount += impMaterielExeStat
					.getRemainForwordExpAmount() == null ? 0.0
					: impMaterielExeStat.getRemainForwordExpAmount();

			/**
			 * 余料结转转出
			 */
			remainForwordImpAmount += impMaterielExeStat
					.getRemainForwordImpAmount() == null ? 0.0
					: impMaterielExeStat.getRemainForwordImpAmount();
			; //  
			/**
			 * 余料结转转入
			 */

		}
		DzscImpMaterielExeStat impMaterielExeStatTotal = new DzscImpMaterielExeStat();
		impMaterielExeStatTotal.setComplexCode("合计");
		impMaterielExeStatTotal.setContractTotalAmount(contractTotalAmount);
		impMaterielExeStatTotal.setTotalImpAmount(totalImpAmount);
		impMaterielExeStatTotal.setOrderImpAmount(orderImpAmount);
		impMaterielExeStatTotal.setMaterielImpAmount(materielImpAmount);
		impMaterielExeStatTotal.setTransferImpAmount(transferImpAmount);
		impMaterielExeStatTotal.setBackMaterielExpAmount(backMaterielExpAmount);
		impMaterielExeStatTotal.setReworkExpAmount(reworkExpAmount);
		impMaterielExeStatTotal.setBackMaterielExchange(backMaterielExchange);
		impMaterielExeStatTotal
				.setExpFinishProductAmount(expFinishProductAmount);
		impMaterielExeStatTotal.setRemainStat(remainStat);
		impMaterielExeStatTotal.setLackStat(lackStat);
		impMaterielExeStatTotal.setCanImpAmount(canImpAmount);
		impMaterielExeStatTotal
				.setRemainForwordExpAmount(remainForwordExpAmount);
		impMaterielExeStatTotal
				.setRemainForwordImpAmount(remainForwordImpAmount);
		return impMaterielExeStatTotal;
	}

	/**
	 * 统计进口料件统计报表
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @param impMaterielExeStat
	 *            进口料件统计资料
	 * @param searchType
	 *            设置要查找的属性
	 * @return DzscImpMaterielExeStat 进口料件统计
	 */
	private DzscImpMaterielExeStat makeImpMaterielExeStat(List contracts,
			Object[] objects, DzscImpMaterielExeStat impMaterielExeStat,
			HashMap<String, Double> hmImgUse, int searchType,Date beginDate,
			Date endDate) {
		/**
		 * 成品使用量
		 */
		Double imgUsed = 0.0;
		String key = "";
		if (searchType == SearchType.NAME) {
			impMaterielExeStat.setNameSpec(objects[0] == null ? "" : objects[0]
					.toString());
			key = impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.NAME_SPEC) {
			impMaterielExeStat.setNameSpec((objects[0] == null ? ""
					: objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			key = impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			impMaterielExeStat.setNameSpec((objects[0] == null ? ""
					: objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			impMaterielExeStat.setComplexCode(objects[2] == null ? ""
					: objects[2].toString());
			key = impMaterielExeStat.getComplexCode() + "/"
					+ impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.CODE) {
			impMaterielExeStat.setComplexCode(objects[0] == null ? ""
					: objects[0].toString());
			key = impMaterielExeStat.getComplexCode();
		} else if (searchType == SearchType.CODE_NAME) {
			impMaterielExeStat.setComplexCode(objects[0] == null ? ""
					: objects[0].toString());
			impMaterielExeStat.setNameSpec(objects[1] == null ? "" : objects[1]
					.toString());
			key = impMaterielExeStat.getComplexCode() + "/"
					+ impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			impMaterielExeStat.setNameSpec((objects[0] == null ? ""
					: objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			impMaterielExeStat.setComplexCode(objects[2] == null ? ""
					: objects[2].toString());
			impMaterielExeStat.setUnit((Unit) objects[3]);
			key = impMaterielExeStat.getComplexCode() + "/"
					+ impMaterielExeStat.getNameSpec() + "/"
					+ impMaterielExeStat.getUnit().getCode();
		}
		imgUsed = CommonUtils.getDoubleExceptNull(hmImgUse.get(key));
		/**
		 * 料件进口总量 = 所有进口报关单 [料件进口类型] 之和
		 */
		Double directImportAmount = this.dzscAnalyseDao.findImpMaterielExeStat(
				contracts, searchType, objects, ImpExpType.DIRECT_IMPORT, null,
				null,beginDate,endDate);
		impMaterielExeStat.setMaterielImpAmount(directImportAmount);
		/**
		 * 转厂进口总量 = 所有进口报关单 [料件转厂类型] 之和
		 */
		Double transferFacotryImportAmount = this.dzscAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, null,beginDate,endDate);
		impMaterielExeStat.setTransferImpAmount(transferFacotryImportAmount);

		/**
		 * 退料复出 = 所有出口报关单 [退料出口类型] 并且贸易方式为 来料料件复出,进料料件复出
		 */
		Double backMaterielReExport = this.dzscAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.BACK_MATERIEL_EXPORT, true, null,beginDate,endDate);
		impMaterielExeStat.setReworkExpAmount(backMaterielReExport);
		/**
		 * 退料退换 = 所有出口报关单 [退料出口类型] 并且贸易方式为 来料料件退换,进料料件退换
		 */
		Double backMaterielExchange = this.dzscAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.BACK_MATERIEL_EXPORT, false, null,beginDate,endDate);
		impMaterielExeStat.setBackMaterielExchange(backMaterielExchange);
		/**
		 * 退料出口 = 退料复出 + 退料退换
		 */
		Double backMaterielExpAmount = backMaterielReExport
				+ backMaterielExchange;
		impMaterielExeStat.setBackMaterielExpAmount(backMaterielExpAmount);
		/**
		 * 余料结转(转入) = 所有进口报关单 [料件进口类型] 并且贸易方式为 来料余料结转,进料余料结转
		 */
		Double remainForwardImpAmount = this.dzscAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.REMAIN_FORWARD_IMPORT, true, true,beginDate,endDate);		
		/**
		 * 余料结转(转出) = 所有出口报关单 [余料结转类型] 并且贸易方式为 来料余料结转,进料余料结转
		 */
		Double remainForwardExpAmount = this.dzscAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.REMAIN_FORWARD_EXPORT, true, true,beginDate,endDate);
		impMaterielExeStat.setRemainForwordExpAmount(remainForwardExpAmount);
		
		/**
		 * 总进口量 = 料件进口总量 +转厂进口总量 - 退料出口(退换+复出)+余料转入
		 */
		Double totalImpAmount = directImportAmount
				+ transferFacotryImportAmount - backMaterielExpAmount
				+ remainForwardImpAmount;// backMaterielExchange;
		impMaterielExeStat.setTotalImpAmount(totalImpAmount);
		/**
		 * 大单进口量 = 料件进口总量 +转厂进口总量
		 */
		Double orderImpAmount = directImportAmount
				+ transferFacotryImportAmount;
		impMaterielExeStat.setOrderImpAmount(orderImpAmount);

		impMaterielExeStat.setRemainForwordImpAmount(remainForwardImpAmount);

		/**
		 * 合同进口料件总订量 = 所有合同的进口料件总量
		 */
		Double contractImportAmount = this.dzscAnalyseDao
				.findContractImportAmount(contracts, searchType, objects);
		impMaterielExeStat.setContractTotalAmount(contractImportAmount);
		/**
		 * 出口成品使用量
		 */
		// Double exportProductDosageAmount = 0.0;
		//
		// for (int i = 0; i < contracts.size(); i++) {
		// DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
		//
		// /**
		// * 退厂返工
		// */
		// Double backFactoryReworkDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.BACK_FACTORY_REWORK);
		//
		// /**
		// * 出口成品
		// */
		// Double directExportDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.DIRECT_EXPORT);
		//
		// /**
		// * 转厂出口
		// */
		// Double transferFactoryExportDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.TRANSFER_FACTORY_EXPORT);
		//
		// /**
		// * 返工复出
		// */
		// Double reworkExportDosageAmount = this.dzscAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.REWORK_EXPORT);
		// /**
		// * 成品使用量 = 出口成品 + 转厂出口 + 返工复出 - 退厂返工
		// */
		// exportProductDosageAmount += (directExportDosageAmount
		// + transferFactoryExportDosageAmount
		// + reworkExportDosageAmount - backFactoryReworkDosageAmount);
		//
		// }
		impMaterielExeStat.setExpFinishProductAmount(imgUsed);
		/**
		 * 缺料情况 = 总进量 - 成品使用量< 0
		 */
		Double remainStat = totalImpAmount - imgUsed;
		if (remainStat >= 0) {
			impMaterielExeStat.setRemainStat(remainStat);
		} else {
			impMaterielExeStat.setLackStat(remainStat);
		}
		/**
		 * 可进口数量 = 合同总订量 - 总进口量
		 */
		impMaterielExeStat.setCanImpAmount(contractImportAmount
				- totalImpAmount);
		/**
		 * 比例 = 余料情况 / 总进口量
		 */
		if (remainStat >= 0) {
			if (totalImpAmount >= 0 && remainStat >= 0) {
				impMaterielExeStat.setProportion(String
						.valueOf((remainStat / totalImpAmount) * 100)
						+ "%");
			}
		} else {// 状态缺料为*
			impMaterielExeStat.setState("★");
		}
		return impMaterielExeStat;
	}

	/**
	 * 查找成品执行明细情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param DzscEmsExgBill
	 *            手册通关备案表头
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 出口成品执行进度明细资料
	 */
	public List findExpProductExeDetail(DzscEmsExgBill DzscEmsExgBill,
			int impExpType, Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		DzscEmsPorHead contract = DzscEmsExgBill.getDzscEmsPorHead();
		if (impExpType == -1) {
			returnList.addAll(findExpProductExeDetailByDirectExport(contract,
					DzscEmsExgBill, beginDate, endDate));
			returnList.addAll(this.findExpProductExeDetailByBackFactoryRework(
					contract, DzscEmsExgBill, beginDate, endDate));
			returnList.addAll(this
					.findExpProductExeDetailByTransferFactoryExport(contract,
							DzscEmsExgBill, beginDate, endDate));
			returnList.addAll(this.findExpProductExeDetailByReworkExport(
					contract, DzscEmsExgBill, beginDate, endDate));
		} else if (impExpType == ImpExpType.DIRECT_EXPORT) {
			returnList.addAll(findExpProductExeDetailByDirectExport(contract,
					DzscEmsExgBill, beginDate, endDate));
		} else if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			returnList.addAll(this.findExpProductExeDetailByBackFactoryRework(
					contract, DzscEmsExgBill, beginDate, endDate));
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT) {
			returnList.addAll(this
					.findExpProductExeDetailByTransferFactoryExport(contract,
							DzscEmsExgBill, beginDate, endDate));
		} else if (impExpType == ImpExpType.REWORK_EXPORT) {
			returnList.addAll(this.findExpProductExeDetailByReworkExport(
					contract, DzscEmsExgBill, beginDate, endDate));
		}
		return returnList;
	}

	/**
	 * 获得成品出口
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param contractExg
	 *            手册通关备案成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByDirectExport(DzscEmsPorHead contract,
			DzscEmsExgBill contractExg, Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		List tempList = this.dzscAnalyseDao.findExpProductExeDetail(contract,
				contractExg, ImpExpType.DIRECT_EXPORT, beginDate, endDate);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, DzscExpFinishProductProgressDetail> map = new HashMap<String, DzscExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				DzscExpFinishProductProgressDetail temp = new DzscExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setDirectExportAmount(materielImportAmount);
				temp
						.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
								: baseCustomsDeclaration.getCustomer()
										.getName());
				map.put(key, temp);
			} else {
				DzscExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp
						.setDirectExportAmount((temp.getDirectExportAmount() == null ? 0.0
								: temp.getDirectExportAmount())
								+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得成品转厂出口
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param contractExg
	 *            手册通关备案成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByTransferFactoryExport(
			DzscEmsPorHead contract, DzscEmsExgBill contractExg,
			Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		List tempList = this.dzscAnalyseDao.findExpProductExeDetail(contract,
				contractExg, ImpExpType.TRANSFER_FACTORY_EXPORT, beginDate,
				endDate);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, DzscExpFinishProductProgressDetail> map = new HashMap<String, DzscExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				DzscExpFinishProductProgressDetail temp = new DzscExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setTransferFactoryExport(materielImportAmount);
				temp
						.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
								: baseCustomsDeclaration.getCustomer()
										.getName());
				map.put(key, temp);
			} else {
				DzscExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp
						.setTransferFactoryExport((temp
								.getTransferFactoryExport() == null ? 0.0
								: temp.getTransferFactoryExport())
								+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得成品退厂返工
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param contractExg
	 *            手册通关备案表头芯片
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByBackFactoryRework(
			DzscEmsPorHead contract, DzscEmsExgBill contractExg,
			Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		List tempList = this.dzscAnalyseDao
				.findExpProductExeDetail(contract, contractExg,
						ImpExpType.BACK_FACTORY_REWORK, beginDate, endDate);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, DzscExpFinishProductProgressDetail> map = new HashMap<String, DzscExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				DzscExpFinishProductProgressDetail temp = new DzscExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setBackFactoryRework(materielImportAmount);
				temp
						.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
								: baseCustomsDeclaration.getCustomer()
										.getName());
				map.put(key, temp);
			} else {
				DzscExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp
						.setBackFactoryRework((temp.getBackFactoryRework() == null ? 0.0
								: temp.getBackFactoryRework())
								+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得成品返工复出
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param contractExg
	 *            手册通关备案成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByReworkExport(DzscEmsPorHead contract,
			DzscEmsExgBill contractExg, Date beginDate, Date endDate) {
		List returnList = new ArrayList();
		List tempList = this.dzscAnalyseDao.findExpProductExeDetail(contract,
				contractExg, ImpExpType.REWORK_EXPORT, beginDate, endDate);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, DzscExpFinishProductProgressDetail> map = new HashMap<String, DzscExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			DzscCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (DzscCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				DzscExpFinishProductProgressDetail temp = new DzscExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setReworkExport(materielImportAmount);
				temp
						.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
								: baseCustomsDeclaration.getCustomer()
										.getName());
				map.put(key, temp);
			} else {
				DzscExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setReworkExport((temp.getReworkExport() == null ? 0.0
						: temp.getReworkExport())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 查找成品执行总表情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscExpFinishProductProgressTotal型，出口成品执行进度总表资料
	 */
	public List findExpProductExeTotal(DzscEmsPorHead contract, Date beginDate,
			Date endDate) {
		List returnList = new ArrayList();
		List contractExgList = this.dzscDao.findDzscEmsExgBill(contract);
		for (int i = 0; i < contractExgList.size(); i++) {
			DzscEmsExgBill contractExg = (DzscEmsExgBill) contractExgList
					.get(i);
			DzscExpFinishProductProgressTotal temp = new DzscExpFinishProductProgressTotal();
			temp.setContractExg(contractExg);
			/**
			 * 成品出口数量
			 */
			Double finishProductExpAmount = this.dzscAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.DIRECT_EXPORT, beginDate, endDate);
			temp.setFinishProductExpAmount(finishProductExpAmount);
			/**
			 * 转厂出口数量
			 */
			Double transferExpAmount = this.dzscAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.TRANSFER_FACTORY_EXPORT, beginDate,
							endDate);
			temp.setTransferExpAmount(transferExpAmount);
			/**
			 * 退厂返工总量
			 */
			Double backFactoryRework = this.dzscAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.BACK_FACTORY_REWORK, beginDate, endDate);
			temp.setBackFactoryRework(backFactoryRework);
			/**
			 * 返工复出总量
			 */
			Double reworkExpAmount = this.dzscAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.REWORK_EXPORT, beginDate, endDate);
			temp.setReworkExpAmount(reworkExpAmount);
			/**
			 * 出口合计 = 成品出口数量 + 转厂出口数量 +返工复出总量 - 退厂返工总量
			 */
			Double expTotal = finishProductExpAmount + transferExpAmount
					+ reworkExpAmount - backFactoryRework;
			temp.setExpTotal(expTotal);
			/**
			 * 可出口总量 = 合同总定量 - 出口合计
			 */
			Double exportAmount = contractExg.getAmount() == null ? 0.0
					: contractExg.getAmount();
			/**
			 * 可出口总量
			 */
			Double canExpAmount = exportAmount - expTotal;
			temp.setCanExpAmount(canExpAmount);
			returnList.add(temp);

		}
		return returnList;
	}

	/**
	 * 查找出口成品统计 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @return List 是DzscExpFinishProductStat型，出口成品统计资料
	 */
	public List findExpFinishProductStat(List contracts, Date beginDate,
			Date endDate, int searchType) {
		List<DzscExpFinishProductStat> returnList = new ArrayList<DzscExpFinishProductStat>();
		List contractExgList = this.dzscAnalyseDao.findContractExg(searchType,
				contracts);
		for (int i = 0; i < contractExgList.size(); i++) {
			DzscExpFinishProductStat temp = new DzscExpFinishProductStat();
			Object[] objects = null;
			if (contractExgList.get(i).getClass().isArray()) {
				objects = (Object[]) contractExgList.get(i);
			} else {
				objects = new Object[] { contractExgList.get(i) };
			}
			temp = this.makeExpProductExeStat(contracts, objects, temp,
					searchType, beginDate, endDate);
			returnList.add(temp);
		}
		/**
		 * 加一条空的记录
		 */
		returnList.add(new DzscExpFinishProductStat());
		/**
		 * 加入统计记录
		 */
		DzscExpFinishProductStat temp = makeTotalExpFinishProductStat(returnList);
		returnList.add(temp);

		return returnList;
	}

	/**
	 * 生成成品统计对象
	 * 
	 * @param contracts
	 *            手册通关备案表头
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @param temp
	 *            出口成品统计资料
	 * @param searchType
	 *            设置要查找的属性
	 * @return DzscExpFinishProductStat 出口成品统计资料
	 */
	private DzscExpFinishProductStat makeExpProductExeStat(List contracts,
			Object[] objects, DzscExpFinishProductStat temp, int searchType, Date beginDate,
			Date endDate) {
		if (searchType == SearchType.NAME) {
			temp.setNameSpec(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.NAME_SPEC) {
			temp.setNameSpec((objects[0] == null ? "" : objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			temp.setNameSpec((objects[0] == null ? "" : objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			temp
					.setComplexCode(objects[2] == null ? "" : objects[2]
							.toString());
		} else if (searchType == SearchType.CODE) {
			temp
					.setComplexCode(objects[0] == null ? "" : objects[0]
							.toString());
		} else if (searchType == SearchType.CODE_NAME) {
			temp
					.setComplexCode(objects[0] == null ? "" : objects[0]
							.toString());
			temp.setNameSpec(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			temp.setNameSpec((objects[0] == null ? "" : objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			temp
					.setComplexCode(objects[2] == null ? "" : objects[2]
							.toString());
			temp.setUnit((Unit) objects[3]);
		}
		/**
		 * 合同总订量
		 */
		Double contractTotalAmount = this.dzscAnalyseDao
				.findContractExportAmount(contracts, searchType, objects);
		temp.setContractTotalAmount(contractTotalAmount);
		/**
		 * 成品出口总量
		 */
		Double finishProductExpAmount = this.dzscAnalyseDao
				.findExpFinishProductStat(contracts, searchType, objects,
						ImpExpType.DIRECT_EXPORT, beginDate, endDate);
		temp.setFinishProductExpAmount(finishProductExpAmount);
		/**
		 * 转厂出口总量
		 */
		Double transferExpAmount = this.dzscAnalyseDao
				.findExpFinishProductStat(contracts, searchType, objects,
						ImpExpType.TRANSFER_FACTORY_EXPORT, beginDate, endDate);
		temp.setTransferExpAmount(transferExpAmount);
		/**
		 * 退厂返工总量
		 */
		Double backFactoryRework = this.dzscAnalyseDao
				.findExpFinishProductStat(contracts, searchType, objects,
						ImpExpType.BACK_FACTORY_REWORK, beginDate, endDate);
		temp.setBackFactoryRework(backFactoryRework);
		/**
		 * 返工复出总量
		 */
		Double reworkExpAmount = this.dzscAnalyseDao.findExpFinishProductStat(
				contracts, searchType, objects, ImpExpType.REWORK_EXPORT, beginDate, endDate);
		temp.setReworkExpAmount(reworkExpAmount);
		/**
		 * 总出口量 = 成品出口总量 + 转厂出口总量 + 返工复出总量 - 退厂返工总量
		 */
		Double totalExpAmount = finishProductExpAmount + transferExpAmount
				+ reworkExpAmount - backFactoryRework; // 出口合计
		temp.setTotalExpAmount(totalExpAmount);
		/**
		 * 可进口总量 = 合同总订量 - 总出口量
		 */
		Double canExpAmount = contractTotalAmount - totalExpAmount;
		temp.setCanExpAmount(canExpAmount);
		return temp;
	}

	/**
	 * 生成成品统计记录
	 * 
	 * @param returnList
	 *            出口成品统计资料
	 * @return List 是DzscExpFinishProductStat型，出口成品统计资料，
	 */
	private DzscExpFinishProductStat makeTotalExpFinishProductStat(
			List<DzscExpFinishProductStat> returnList) {
		DzscExpFinishProductStat temp = new DzscExpFinishProductStat();
		/**
		 * 合同总订量
		 */
		Double contractTotalAmount = 0.0;
		/**
		 * 成品出口总量
		 */
		Double finishProductExpAmount = 0.0;
		/**
		 * 转厂出口总量
		 */
		Double transferExpAmount = 0.0;
		/**
		 * 退厂返工总量
		 */
		Double backFactoryRework = 0.0;
		/**
		 * 返工复出总量
		 */
		Double reworkExpAmount = 0.0;
		/**
		 * 总出口量
		 */
		Double totalExpAmount = 0.0;
		/**
		 * 可进口总量
		 */
		Double canExpAmount = 0.0;

		for (int i = 0; i < returnList.size(); i++) {
			DzscExpFinishProductStat e = returnList.get(i);
			/**
			 * 合同总订量
			 */
			contractTotalAmount += (e.getContractTotalAmount() == null ? 0.0
					: e.getContractTotalAmount());
			/**
			 * 成品出口总量
			 */
			finishProductExpAmount += (e.getFinishProductExpAmount() == null ? 0.0
					: e.getFinishProductExpAmount());
			/**
			 * 转厂出口总量
			 */
			transferExpAmount += (e.getTransferExpAmount() == null ? 0.0 : e
					.getTransferExpAmount());
			/**
			 * 退厂返工总量
			 */
			backFactoryRework += (e.getBackFactoryRework() == null ? 0.0 : e
					.getBackFactoryRework());
			/**
			 * 返工复出总量
			 */
			reworkExpAmount += (e.getReworkExpAmount() == null ? 0.0 : e
					.getReworkExpAmount());
			/**
			 * 总出口量
			 */
			totalExpAmount += (e.getTotalExpAmount() == null ? 0.0 : e
					.getTotalExpAmount());
			/**
			 * 可进口总量
			 */
			canExpAmount += (e.getCanExpAmount() == null ? 0.0 : e
					.getCanExpAmount());
		}
		/**
		 * 合同总订量
		 */
		temp.setContractTotalAmount(contractTotalAmount);
		/**
		 * 成品出口总量
		 */
		temp.setFinishProductExpAmount(finishProductExpAmount);
		/**
		 * 转厂出口总量
		 */
		temp.setTransferExpAmount(transferExpAmount);
		/**
		 * 退厂返工总量
		 */
		temp.setBackFactoryRework(backFactoryRework);
		/**
		 * 返工复出总量
		 */
		temp.setReworkExpAmount(reworkExpAmount);
		/**
		 * 总出口量
		 */
		temp.setTotalExpAmount(totalExpAmount);
		/**
		 * 可进口总量
		 */
		temp.setCanExpAmount(canExpAmount);
		temp.setComplexCode("合计");
		return temp;
	}

	/**
	 * 根据合同备案表头、和临时的合同料件资料 查找合同成品资料
	 * 
	 * @param contractImgList
	 *            存放合同料件的临时资料
	 * @param contractList
	 *            手册通关备案表头
	 * @return List 是TempDzscFinishProduct型，存放临时的成品资料
	 */
	public List<TempDzscFinishProduct> findContractBomByContractImg(
			List<TempDzscContractImg> contractImgList,
			List<DzscEmsPorHead> contractList) {
		List<TempDzscFinishProduct> returnList = new ArrayList<TempDzscFinishProduct>();
		List dataSource = this.dzscAnalyseDao.findContractBomByContractImg(
				contractImgList, contractList);
		for (int i = 0; i < dataSource.size(); i++) {
			Object[] objs = (Object[]) dataSource.get(i);
			TempDzscFinishProduct temp = new TempDzscFinishProduct();
			temp.setUnitDosage((Double) objs[0]);
			temp.setProductName((String) objs[1]);
			returnList.add(temp);
		}
		return returnList;
	}

	/**
	 * 查询成品物料的出口数量
	 * 
	 * @param ptNo
	 * @param versionNo
	 * @param contractList
	 * @param beginDate
	 * @param endDate
	 * @param impExpTypes
	 * @return
	 */
	public List findFinishedProductExportAmount(List lsMateriel,
			List<DzscEmsPorHead> contractList, Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		for (int i = 0; i < lsMateriel.size(); i++) {
			Materiel materiel = ((MaterialApply) lsMateriel.get(i))
					.getMateriel();
			List lsBom = this.dzscAnalyseDao
					.findMaterialBomApplyByDetail(materiel);
			for (int j = 0; j < lsBom.size(); j++) {
				MaterialBomDetailApply bomDetailApply = (MaterialBomDetailApply) lsBom
						.get(j);
				TempFinishedProductExportAmount finishedProductExport = new TempFinishedProductExportAmount();
				finishedProductExport.setFinishedProduct(bomDetailApply
						.getVersionApply().getBomMasterApply().getMateriel());
				finishedProductExport.setMateriel(bomDetailApply.getMateriel());
				finishedProductExport.setVersion(bomDetailApply
						.getVersionApply().getVersion());
				finishedProductExport.setUnitWaste(bomDetailApply
						.getUnitWaste());
				finishedProductExport.setWaste(bomDetailApply.getWaste());
				double exportAmount = this.dzscAnalyseDao
						.findFinishedProductExportAmount(finishedProductExport
								.getFinishedProduct().getPtNo(),
								finishedProductExport.getVersion(),
								contractList, beginDate, endDate,
								new Integer[] { ImpExpType.DIRECT_EXPORT,
										ImpExpType.TRANSFER_FACTORY_EXPORT });
				double backAmount = this.dzscAnalyseDao
						.findFinishedProductExportAmount(
								finishedProductExport.getFinishedProduct()
										.getPtNo(),
								finishedProductExport.getVersion(),
								contractList,
								beginDate,
								endDate,
								new Integer[] { ImpExpType.BACK_FACTORY_REWORK });
				finishedProductExport
						.setExportAmount(exportAmount - backAmount);
				finishedProductExport.setUsedAmount(CommonUtils
						.getDoubleExceptNull(finishedProductExport
								.getExportAmount())
						* CommonUtils.getDoubleExceptNull(bomDetailApply
								.getUnitUsed()));
				lsResult.add(finishedProductExport);
			}
		}
		return lsResult;
	}

	/**
	 * 把list转换为HashMap，list(o)为key,list(1)value
	 * 
	 * @param list
	 *            要转换的list
	 * @return HashMap
	 */
	private HashMap<String, Double> converListToHashTable(List list) {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String key = (objs[0] == null ? "" : objs[0].toString());
			Double value = objs[1] == null ? 0 : Double.parseDouble(objs[1]
					.toString());
			// System.out.println("::::::::" + key + "|||||||" + value);
			hm.put(key, value);
		}
		return hm;
	}

	/**
	 * 进口料件备案统计表
	 * 
	 * @param head
	 * @return
	 */
	public List findImpPORImgStatInfo(DzscEmsPorHead head) {
		List lsResult = new ArrayList();
		String emsNo = head.getEmsNo();
		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmDirectImport = converListToHashTable(this.dzscAnalyseDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, null, null));

		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmTransferFactoryImport = converListToHashTable(this.dzscAnalyseDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo, null,
						null));

		/**
		 * 余料进口 (余料结转进口)
		 */
		HashMap<String, Double> hmRemainImport = converListToHashTable(this.dzscAnalyseDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo, null,
						null));// new String[] { "0657",
		// "0258" }

		// /**
		// * 余料出口 (余料结转出口)
		// */
		// HashMap<String, Double> hmRemainForward =
		// converListToHashTable(this.dzscAnalyseDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo, null,
		// null));// new String[] {
		// "0657","0258" }

		// /**
		// * 料件退换进口数
		// */
		// HashMap<String, Double> hmExchangeImport =
		// converListToHashTable(this.dzscAnalyseDao
		// .findCommInfoTotalAmount(ImpExpFlag.IMPORT,
		// ImpExpType.DIRECT_IMPORT,
		// new String[] { "0300", "0700" }, emsNo, null, null));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmExchangeExport = converListToHashTable(this.dzscAnalyseDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, null, null));

		/**
		 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		 */
		HashMap<String, Double> hmBackMaterialReturn = converListToHashTable(this.dzscAnalyseDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, null, null));
		/**
		 * 备案成品耗用料件数量
		 */
		HashMap<String, Double> hmExgUsedImgAmount = new HashMap<String, Double>();
		// converListToHashTable(this.dzscAnalyseDao
		// .findDzscExgUsedImgAmount(emsNo));
		List lsBomBill = this.dzscDao.findDzscEmsBomBill(head.getId());
		for (int i = 0; i < lsBomBill.size(); i++) {
			DzscEmsBomBill bomBill = (DzscEmsBomBill) lsBomBill.get(i);
			String imgSeqNum = (bomBill.getImgSeqNum() == null ? "" : bomBill
					.getImgSeqNum().toString());
			double unitWaste = CommonUtils.getDoubleByDigit(bomBill
					.getUnitWare(), 9);
			double waste = CommonUtils.getDoubleByDigit(bomBill.getWare(), 5);
			double imgAmount = CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(bomBill.getDzscEmsExgBill()
							.getAmount())
					* (unitWaste / (1 - waste / 100.0)), 5);
			double hasAmount = CommonUtils
					.getDoubleExceptNull(hmExgUsedImgAmount.get(imgSeqNum));
			hmExgUsedImgAmount.put(imgSeqNum, imgAmount + hasAmount);
		}
		// /**
		// * 退料出口量
		// */
		// HashMap<String, Double> hmBackMaterialExport =
		// converListToHashTable(this.dzscAnalyseDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo, null,
		// null));

		List lsImg = this.dzscDao.findDzscEmsImgBill(head.getId());
		for (int i = 0; i < lsImg.size(); i++) {
			TempImpPORImgStatInfo info = new TempImpPORImgStatInfo();
			DzscEmsImgBill imgBill = (DzscEmsImgBill) lsImg.get(i);
			String seqNum = imgBill.getSeqNum().toString();
			info.setSeqNum(imgBill.getSeqNum());
			info.setComplex(imgBill.getComplex());
			info.setCommName(imgBill.getName());
			info.setCommSpec(imgBill.getSpec());
			info.setUnit(imgBill.getUnit());
			info.setUnitPrice(imgBill.getPrice());
			info.setImgAmount(imgBill.getAmount());
			double directImport = CommonUtils
					.getDoubleExceptNull(hmDirectImport.get(seqNum));
			double transferFactoryImport = CommonUtils
					.getDoubleExceptNull(hmTransferFactoryImport.get(seqNum));
			double remainImport = CommonUtils
					.getDoubleExceptNull(hmRemainImport.get(seqNum));
			double exchangeExport = CommonUtils
					.getDoubleExceptNull(hmExchangeExport.get(seqNum));
			double backMaterialReturn = CommonUtils.getDoubleByDigit(
					hmBackMaterialReturn.get(seqNum), 5);
			double exgUsedImgAmount = CommonUtils.getDoubleByDigit(
					hmExgUsedImgAmount.get(seqNum), 5);
			info.setExgUsedAmount(exgUsedImgAmount);
			info.setExceedAmount(info.getImgAmount() - info.getExgUsedAmount());
			info
					.setTotalImpAmount(CommonUtils.getDoubleByDigit(
							directImport + transferFactoryImport + remainImport
									- exchangeExport, 5));
			info.setImgBackExport(backMaterialReturn);
			info.setCanUseAmount(info.getTotalImpAmount()
					- info.getExgUsedAmount());
			lsResult.add(info);
		}
		return lsResult;
	}
	
	
}
