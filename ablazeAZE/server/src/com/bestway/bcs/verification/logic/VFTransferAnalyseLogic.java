package com.bestway.bcs.verification.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.verification.dao.VFTransferAnalyseDao;
import com.bestway.bcs.verification.dao.VFVerificationDao;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFTransferDiffCount;
import com.bestway.bcs.verification.entity.VFTransferDiffExg;
import com.bestway.bcs.verification.entity.VFTransferDiffExgConvert;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExg;
import com.bestway.bcs.verification.entity.VFTransferDiffHsExgResolve;
import com.bestway.bcs.verification.entity.VFTransferDiffHsImg;
import com.bestway.bcs.verification.entity.VFTransferDiffImg;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.jptds.common.AppException;

public class VFTransferAnalyseLogic {
	private VFTransferAnalyseDao vfTransferAnalyseDao;
	private VFVerificationDao vfVerificationDao;

	/**
	 * 是否存在vfsection批此的结转成品数据
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffExgs(VFSection vfSection) {
		return vfVerificationDao.isExistByVFSection(vfSection, VFTransferDiffExg.class.getName());
	}
	/**
	 * 是否存在vfsection批此的结转成品数据(编码级)
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffHsExgs(VFSection vfSection) {
		return vfVerificationDao.isExistByVFSection(vfSection, VFTransferDiffHsExg.class.getName());
	}
	/**
	 * 是否存在vfsection批此的结转料件数据
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffImgs(VFSection vfSection) {
		return vfVerificationDao.isExistByVFSection(vfSection, VFTransferDiffImg.class.getName());
	}
	/**
	 * 判断vfsection（批次）中是否存在结转料件差额(编码级)信息
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffHsImgs(VFSection vfSection) {
		return vfVerificationDao.isExistByVFSection(vfSection, VFTransferDiffHsImg.class.getName());
	}
	
	/**
	 * 是否存在vfsection批此的结转差额汇总数据
	 * @param vfSection
	 * @return
	 */
	public boolean isExistVFTransferDiffCounts(VFSection vfSection) {
		return vfVerificationDao.isExistByVFSection(vfSection, VFTransferDiffCount.class.getName());
	}
	/**
	 * 根据section(批次)查询结转成品差额表信息
	 * @param section 批次信息
	 * @return
	 */
	public List<VFTransferDiffExg> findVFTransferDiffExgs(VFSection section) {
		return vfVerificationDao.findByVFSection(section,VFTransferDiffExg.class.getName());
	}
	/**
	 * 根据section(批次)查询结转成品差额表信息（编码级）
	 * @param section 批次信息
	 * @return
	 */
	public List<VFTransferDiffHsExg> findVFTransferDiffHsExgs(VFSection section) {
		return vfVerificationDao.findByVFSection(section,VFTransferDiffHsExg.class.getName());
	}
	/**
	 * 根据section(批次)查询结转料件差额表信息
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffImg> findVFTransferDiffImgs(VFSection section,Integer seqNum) {
		return vfVerificationDao.findByVFSection(section,VFTransferDiffImg.class.getName(),seqNum);
	}
	/**
	 * 根据section(批次)查询结转料件差额表信息(编码级)
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffHsImg> findVFTransferDiffHsImgs(VFSection section,Integer seqNum){
		return vfVerificationDao.findByVFSection(section,VFTransferDiffHsImg.class.getName(),seqNum);
	}
	/**
	 * 根据section(批次)查询结转成品差额折料转换报关数据信息
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffExgConvert> findVFTransferDiffExgConverts(VFSection section,Integer seqNum){
		return vfTransferAnalyseDao.findVFTransferDiffExgConverts(section,seqNum);
	}
	/**
	 * 根据section(批次)查询结转成品分解料件表(编码级)
	 * @param section 批次信息
	 * @param seqNum 归并序号
	 * @return
	 */
	public List<VFTransferDiffHsExgResolve> findVFTransferDiffHsExgResolves(VFSection section,Integer seqNum){
		return vfVerificationDao.findByVFSection(section,VFTransferDiffHsExgResolve.class.getName(),seqNum);
	}
	/**
	 * 批量保存 结转成品差额表
	 * @param vfSection 批次
	 * @param request
	 * @param exgLs
	 */
	public void saveVFTransferDiffExgs(VFSection vfSection, List<VFTransferDiffExg> exgLs){
		VFTransferDiffExg exg = null;
		for(int i = 0 ;i < exgLs.size();i++){
			exg = exgLs.get(i);
			exg.setSerialNumber(i);
			exg.setSection(vfSection);
		}
		vfVerificationDao.batchSaveOrUpdateDirect(exgLs);		
	}
	/**
	 * 批量保存 结转成品差额表(编码级)
	 * @param vfSection 批次
	 * @param request
	 * @param hsExgLs
	 */
	public void saveVFTransferDiffHsExgs(VFSection vfSection, List<VFTransferDiffHsExg> hsExgLs){
		VFTransferDiffHsExg hsExg = null;
		for(int i = 0 ;i < hsExgLs.size();i++){
			hsExg = hsExgLs.get(i);
			hsExg.setSerialNumber(i);
			hsExg.setSection(vfSection);
		}
		vfVerificationDao.batchSaveOrUpdateDirect(hsExgLs);		
	}
	/**
	 * 批量保存 结转料件差额表
	 * @param request
	 * @param imgLs
	 */
	public void saveVFTransferDiffImgs(VFSection vfSection, List<VFTransferDiffImg> imgLs){		
		VFTransferDiffImg img = null;
		for(int i = 0 ;i < imgLs.size();i++){
			img = imgLs.get(i);
			img.setSerialNumber(i);
			img.setSection(vfSection);							
		}						
		vfVerificationDao.batchSaveOrUpdateDirect(imgLs);			
	}
	/**
	 * 批量保存 结转料件差额表(编码级)
	 * @param request
	 * @param imgLs
	 */
	public void saveVFTransferDiffHsImgs(VFSection vfSection, List<VFTransferDiffHsImg> hsImgLs){		
		VFTransferDiffHsImg img = null;
		for(int i = 0 ;i < hsImgLs.size();i++){
			img = hsImgLs.get(i);
			img.setSerialNumber(i);
			img.setSection(vfSection);							
		}						
		vfVerificationDao.batchSaveOrUpdateDirect(hsImgLs);			
	}
	/**
	 * 根据section(批次)批量删除结成品差额表  
	 * @param section 批次信息
	 */
	public void deleteVFTransferDiffExgs(VFSection section) {
		vfVerificationDao.deleteByVFSection(section,VFTransferDiffExgConvert.class.getName());	
		vfVerificationDao.deleteByVFSection(section,VFTransferDiffExg.class.getName());
	}
	/**
	 * 根据section(批次)批量删除结成品差额表  (编码级)
	 * @param section 批次信息
	 */
	public void deleteVFTransferDiffHsExgs(VFSection section) {
		vfVerificationDao.deleteByVFSection(section,VFTransferDiffHsExgResolve.class.getName());	
		vfVerificationDao.deleteByVFSection(section,VFTransferDiffHsExg.class.getName());
	}
	/**
	 * 根据section(批次)批量删除结转料件差额表  
	 * @param section 批次信息
	 */
	public void deleteVFTransferDiffImgs(VFSection section) {
		vfVerificationDao.deleteByVFSection(section, VFTransferDiffImg.class.getName());
	}
	
	/**
	 * 根据section(批次)批量删除结转料件差额表  (编码级)
	 * @param section 批次信息
	 */
	public void deleteVFTransferDiffHsImgs(VFSection section) {
		vfVerificationDao.deleteByVFSection(section, VFTransferDiffHsImg.class.getName());
	}
	
	
	/**
	 * 根据section批次 将该批次的结转成品拆分成料件，步骤如下：
	 *  1、准备成品料件对应的MaterialBomVersion数据
	 *  2、准备成品对应的拆解料件MaterialBomDetail数据
	 *  3、循环拆分VFTransferDiffExg（成品信息）-->VFTransferDiffExgConvert（料件信息）
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffExgConvert> unpickExgToImg(VFSection section) {
		Request request = CommonUtils.getRequest();		
		//先删除已经转换的数据
		long t = System.currentTimeMillis();
		long t2 = 0;
		vfVerificationDao.deleteByVFSection(section,VFTransferDiffExgConvert.class.getName());		
		ProgressInfo info = null;
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
			info.setMethodName("正在准备数据,请稍等...");
			info.setTotalNum(100);
		}
		List<VFTransferDiffExg> exgLs = findVFTransferDiffExgs(section);
		if(info != null)
			info.setCurrentNum(10);
		List<VFTransferDiffExgConvert> rsLs = new ArrayList<VFTransferDiffExgConvert>();		
		if(exgLs != null && exgLs.size() >0){
			int serialNum = 0;	//序号
			//1、准备成品料件对应的MaterialBomVersion数据  
			//成品中的Bomversion时取最大版本（并更新成品数据），不为空时为当前版本 并
			//得到结果为：Map<String,MaterialBomVersion> ,Map的key为料号+#+版本号
			List<MaterialBomVersion> mbvLs = vfVerificationDao.findMaterialBomVersions();
			if(info != null)
				info.setCurrentNum(20);
			Map<String,MaterialBomVersion> bomMap = new LinkedHashMap<String, MaterialBomVersion>();
			String _v = null;
			String _ptNo = null;
			for(VFTransferDiffExg exg : exgLs){
				_v = StringUtils.trimToEmpty(exg.getBomVersion());
				_ptNo = exg.getPtNo();				
				for(MaterialBomVersion mbv : mbvLs){
					if(_ptNo.equals(mbv.getMaster().getMateriel().getPtNo())){
						if(StringUtils.isBlank(_v) ){
							_v = String.valueOf(mbv.getVersion());
							exg.setBomVersion(_v);
							bomMap.put(_ptNo+"#"+_v, mbv);
							vfTransferAnalyseDao.saveOrUpdate(exg);
							break;
						}else if(_v.equals(String.valueOf(mbv.getVersion()))){
							bomMap.put(_ptNo+"#"+_v, mbv);
							break;
						}
					}
				}				
			}
			if(info != null)
				info.setCurrentNum(60);
			//2、准备成品对应的拆解料件MaterialBomDetail数据
			//根据版本查询到对应的料件
			//循环料件列表与第一步的结果版本Map 料件对应的版本与第一步的Map的Value中的版本一致时 使用第一步的key:ptNo+#+bomversion(版本)
			//作为成品与料件对应Map的key,将料件添加到Map的Value列表中
			//最终结果得到Map<String, List<MaterialBomDetail>>,key:ptNo+#+bomversion(版本),value为料件list
			List<MaterialBomDetail> detailLs = vfVerificationDao.findMaterialBomDetailByBomVers(bomMap.values());
			if(info != null)
				info.setCurrentNum(70);
			Map<String, List<MaterialBomDetail>> detailMap = new HashMap<String, List<MaterialBomDetail>>();
			List<MaterialBomDetail> tLs = null;
			Set<Entry<String, MaterialBomVersion>> bomVerSet = bomMap.entrySet(); 
			for(MaterialBomDetail detail : detailLs){
				String key = null;
				for(Entry<String, MaterialBomVersion> e : bomVerSet){
					if(detail.getVersion().equals(e.getValue())){
						key = e.getKey();
						break;
					}
				}
				tLs = detailMap.get(key);
				if(tLs == null){
					tLs = new ArrayList<MaterialBomDetail>();
					detailMap.put(key, tLs);
				}
				tLs.add(detail);
			}		
			t2 = System.currentTimeMillis();
			System.out.println("准备料件时间："+ (t2-t)+"毫秒");
			t = t2;
			
			if(info != null){
				info.setCurrentNum(0);
				info.setMethodName("正在拆解料件,请稍等...");
				info.setTotalNum(exgLs.size()/100);
			}
			//循环成品列表进行成品拆解
			List<VFTransferDiffExgConvert> tmpLs = null;
			VFTransferDiffExg exg = null;
			for(int i = 0 ;i < exgLs.size(); i++){
				exg = exgLs.get(i);
				//单条成品折算
				tmpLs = unpick(exg, serialNum,bomMap,detailMap);
				if(tmpLs != null){
					serialNum += tmpLs.size();
					rsLs.addAll(tmpLs);
				}				
				if(info != null && i % 100 == 0){
					info.setCurrentNum(info.getCurrentNum()+1);
				}
			}
			t2 = System.currentTimeMillis();
			System.out.println("拆解料件时间："+ (t2-t)+"毫秒");
			t = t2;
			
			if(info != null){
				info.setCurrentNum(0);
				info.setMethodName("正在保存折算料件数据，请稍等...");
				info.setTotalNum(rsLs.size()/100);
			}
			//转换结果保存入库
			vfVerificationDao.batchSaveOrUpdateDirect(rsLs);
			t2 = System.currentTimeMillis();
			System.out.println("保存料件时间："+ (t2-t)+"毫秒");		
		}
		return rsLs;
	}
	/**
	 * 拆分成品成料件，拆分步骤如下：
	 *  1、根据成品料号找MaterialBomVersion，查找方法：首先根据ptNo+bomVersion参数查找料件BOM版本，找不到时采用ptNo查找最大Bom版本数据 
	 *  2、根据MaterialBomVersion查找MaterialBomDetail(料件)，
	 *  3、转换MaterialBomDetail(料件)成VFTransferDiffExgConvert(结转成品差额折料数据信息)
	 * @param exg 成品
	 * @param serialNum 序号
	 * @return
	 */
	private List<VFTransferDiffExgConvert> unpick(VFTransferDiffExg exg,int serialNum,Map<String,MaterialBomVersion> bomMap,Map<String, List<MaterialBomDetail>> detailMap){
		//第一步：
		String key = exg.getPtNo()+"#"+ StringUtils.trimToEmpty(exg.getBomVersion());
		MaterialBomVersion mbom = bomMap.get(key);
		if(mbom == null)
			throw new AppException("未找到成品【"+exg.getPtNo()+"】Bom版本.");
		//第二步
		List<MaterialBomDetail> details = detailMap.get(key);
		//第三步：遍历转换MaterialBomDetail(料件)成VFTransferDiffExgConvert
		if(details != null && !details.isEmpty()){
			List<VFTransferDiffExgConvert> rsLs = new ArrayList<VFTransferDiffExgConvert>(details.size());
			VFTransferDiffExgConvert exgConv = null;
			Materiel materiel = null;
			Double unSenderNum =  exg.getUnSendferNum();
			Double unTransferNum = exg.getUnTransferNum();
			for(MaterialBomDetail detail : details){
				materiel = detail.getMateriel();
				exgConv = new VFTransferDiffExgConvert();
				//设置转换料件属性
				exgConv.setExg(exg);				
				exgConv.setSerialNumber(++serialNum);
				exgConv.setSection(exg.getSection());	//设置批次
				exgConv.setPtNo(materiel.getPtNo());
				exgConv.setPtName(materiel.getFactoryName());
				exgConv.setPtSpec(materiel.getFactorySpec());
				exgConv.setPtUnit(materiel.getPtUnit() == null ? null : materiel.getPtUnit().getName());
				exgConv.setUnitUsed(detail.getUnitUsed());					//单位用量
				exgConv.setUnitWaste(detail.getUnitWaste());				//单耗
				exgConv.setWaste(detail.getWaste());						//损耗率
				
				Double unitUsed = detail.getUnitUsed() == null ? 0 : detail.getUnitUsed();	//单位用量

				//计算拆分后数量  计算公式：成品数量* 料件单位使用量=料件量
				if(unSenderNum != null){
					exgConv.setUnSendferNum(CaleUtil.multiply(unSenderNum,unitUsed));
				}
				if(unTransferNum != null){
					exgConv.setUnTransferNum(CaleUtil.multiply(unTransferNum,unitUsed));
				}
				rsLs.add(exgConv);
			} 			
			return rsLs;
		}
		return Collections.EMPTY_LIST;
	}	
		
	/**
	 * 把当前批次 vfSection 下  的所有 报关数据 数据进行转换报关数据
	 * @param vfSection
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String convertVFTransferDiffExgConverts(VFSection vfSection) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
			info.setMethodName("正在准备数据,请稍等...");
			info.setTotalNum(100);
		}
		long t = System.currentTimeMillis(); 				
		List<VFTransferDiffExgConvert> list = findVFTransferDiffExgConverts(vfSection,null);
		if(info != null)
			info.setCurrentNum(30);
		List<BcsInnerMerge> innerMerges = vfVerificationDao.findBcsInnerMerge(MaterielType.MATERIEL);
		if(info != null)
			info.setCurrentNum(80);
		Map<String, BcsInnerMerge> innerMergeMap = new HashMap<String, BcsInnerMerge>();
		innerMergeMap = CommonUtils.listToMap(innerMerges, new GetKeyValueImpl<BcsInnerMerge>() {
			public String getKey(BcsInnerMerge e) {
				return e.getMateriel().getPtNo();
			}
		});
		if(info != null){
			info.setCurrentNum(0);
			info.setMethodName("正在折算料件,请稍等...");
			info.setTotalNum(list.size()/100);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("准备数据时间："+ (t2-t)+"毫秒");
		t = t2;
		VFTransferDiffExgConvert exg = null;
		BcsInnerMerge merge = null;
		BcsTenInnerMerge infoo = null;
		StringBuffer err =new  StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if(info != null && i % 100 ==0){				
				info.setCurrentNum(info.getCurrentNum()+1);
			}
			exg = list.get(i);			
			vfVerificationDao.evict(exg);
			exg.setId(null);
			merge = innerMergeMap.get(exg.getPtNo());
			if(merge == null){
				err.append(err.length() == 0 ? exg.getPtNo() : ("," + exg
						.getPtNo()));
				continue;
			}
			infoo = merge.getBcsTenInnerMerge();
			if (info == null) {
				err.append(err.length() == 0 ? exg.getPtNo() : ("," + exg
						.getPtNo()));
				continue;
			}
			// 根据对应关系把工厂料件转化为报关料件
			convertVFTransferDiffExgConvert(exg, merge);
		}
		t2 = System.currentTimeMillis();
		System.out.println("转换料件时间："+ (t2-t)+"毫秒");		
		t = t2;
		
		if(info != null){
			info.setCurrentNum(0);
			info.setMethodName("正在保存折算数据，请稍等...");
			info.setTotalNum(list.size()/100);
		}
		
		//删除本批次分解数据
		vfVerificationDao.deleteByVFSection(vfSection, VFTransferDiffExgConvert.class.getSimpleName());		
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info.setMethodName("正在保存数据，共"+list.size()+"条");
			info.setTotalNum(list.size()/100);
		}
		//保存本批次转换报关数据
		vfVerificationDao.batchSaveOrUpdateDirect(list);		
		t2 = System.currentTimeMillis();
		System.out.println("更新料件时间："+ (t2-t)+"毫秒");
//		if(err.length() > 0){
//			
//		}
		
		return err.toString();
	}
	
	/**
	 * 根据对应关系把工厂料件转化为报关料件
	 * @param img
	 * @param merge
	 */
	private void convertVFTransferDiffExgConvert(VFTransferDiffExgConvert img, BcsInnerMerge merge) {
		if(merge == null || merge.getBcsTenInnerMerge() == null) return;		
		BcsTenInnerMerge info = merge.getBcsTenInnerMerge();		
		img.setMergerNo(merge.getBcsTenInnerMerge().getSeqNum());
		img.setHsName(info.getName());
		img.setHsSpec(info.getSpec());
		img.setHsUnit(info.getComUnit().getName());
		img.setUnitConvert(CommonUtils.getDoubleExceptNull(merge.getUnitConvert()));
		if(img.getUnSendferNum() != null){
			img.setConvertUnSendHadTransNum(CaleUtil.multiply(img.getUnSendferNum(),CommonUtils.getDoubleExceptNull(merge.getUnitConvert())));
		}else{
			img.setConvertUnSendHadTransNum(0d);
		}
		if(img.getUnTransferNum() != null){
			img.setConvertUnTransHadSendNum(CaleUtil.multiply(img.getUnTransferNum(),CommonUtils.getDoubleExceptNull(merge.getUnitConvert())));
		}else{
			img.setConvertUnTransHadSendNum(0d);
		}
	}
	
	/**
	 * 把当前批次 vfSection 下  的所有 报关数据 数据进行转换报关数据
	 * @param vfSection
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VFTransferDiffImg> convertVFTransferDiffImgs(VFSection vfSection) {
		Request request = CommonUtils.getRequest();
		ProgressInfo info = null;
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
			info.setMethodName("正在准备数据,请稍等...");
			info.setTotalNum(100);
		}
		long t = System.currentTimeMillis(); 		
		List<VFTransferDiffImg> list = findVFTransferDiffImgs(vfSection,null);
		if(info != null)
			info.setCurrentNum(30);
		List<BcsInnerMerge> innerMerges = vfVerificationDao.findBcsInnerMerge(MaterielType.MATERIEL);
		if(info != null)
			info.setCurrentNum(80);
		Map<String, BcsInnerMerge> innerMergeMap = new HashMap<String, BcsInnerMerge>();
		innerMergeMap = CommonUtils.listToMap(innerMerges, new GetKeyValueImpl<BcsInnerMerge>() {
			public String getKey(BcsInnerMerge e) {
				return e.getMateriel().getPtNo();
			}
		});
		if(info != null){
			info.setCurrentNum(0);
			info.setMethodName("正在折算料件,请稍等...");
			info.setTotalNum(list.size()/100);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("准备数据时间："+ (t2-t)+"毫秒");
		t = t2;
		VFTransferDiffImg img = null;
		for (int i = 0; i < list.size(); i++) {			
			if(info != null && i % 100 ==0){				
				info.setCurrentNum(info.getCurrentNum()+1);
			}
			img = list.get(i);						
			vfVerificationDao.evict(img);
			img.setId(null);
			// 根据对应关系把工厂料件转化为报关料件
			convertVFTransferDiffImg(img, innerMergeMap.get(img.getPtNo()));
		}
		t2 = System.currentTimeMillis();
		System.out.println("转换数据时间1："+ (t2-t)+"毫秒");
		t = t2;
		//删除原有数据
		this.deleteVFTransferDiffImgs(vfSection);
		
		if(info != null){
			info.setCurrentNum(0);
			info.setMethodName("正在保存折算数据，请稍等...");
			info.setTotalNum(list.size()/100);
		}
		vfVerificationDao.batchSaveOrUpdateDirect(list);
		t2 = System.currentTimeMillis();
		System.out.println("更新数据库时间："+ (t2-t)+"毫秒");
		t = t2;
		return list;
	}
	/**
	 * 根据对应关系把工厂料件转化为报关料件
	 * @param img
	 * @param merge
	 */
	private void convertVFTransferDiffImg(VFTransferDiffImg img, BcsInnerMerge merge) {
		BcsTenInnerMerge info = merge.getBcsTenInnerMerge();
		img.setMergerNo(merge.getBcsTenInnerMerge().getSeqNum());
		img.setHsName(info.getName());
		img.setHsSpec(info.getSpec());
		img.setHsUnit(info.getComUnit().getName());
		img.setUnitConvert(merge.getUnitConvert());		
		if(img.getUnReceiveNum() != null){
			img.setConvertUnReceiveHadTransNum(CaleUtil.multiply(img.getUnReceiveNum(),CommonUtils.getDoubleExceptNull(merge.getUnitConvert())));
		}
		if(img.getUnTransferNum() != null){
			img.setConvertUnTransHadReceiveNum(CaleUtil.multiply(img.getUnTransferNum(),CommonUtils.getDoubleExceptNull(merge.getUnitConvert())));
		}
	}	
	/**
	 *  根据section(批次)查询结转汇总信息
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffCount> findVFTransferDiffCount(VFSection section,Integer seqNums) {
		return vfVerificationDao.findByVFSection(section, VFTransferDiffCount.class.getName(),seqNums," order by mergerNo");
	}
	/**
	 * 根据section(批次) 计算成品结转、料件结转报关料件 汇总信息
	 * 汇总条件为归并序号
	 * @param section 批次
	 * @return
	 */
	public List<VFTransferDiffCount> cacuVFTransferDiffCount(VFSection section) {
		deleteVFTransferDiffCount(section);
		/***归并序号为Key值，进行汇总*****/
		Map<Integer,VFTransferDiffCount> rsMp = new LinkedHashMap<Integer, VFTransferDiffCount>();
		/******** 统计编码级别的结转数据 ********/
		if(section.getIsImportHs()){
			List<VFTransferDiffHsExgResolve> exgs = vfVerificationDao.findByVFSection(section,VFTransferDiffHsExgResolve.class.getName());
			if(CollectionUtils.isNotEmpty(exgs)){
				for(VFTransferDiffHsExgResolve ec : exgs){
					cacuHsExgResolveToDiffCount(rsMp,ec);
				}
			}
			List<VFTransferDiffHsImg> imgs = vfVerificationDao.findByVFSection(section,VFTransferDiffHsImg.class.getName());
			if(CollectionUtils.isNotEmpty(imgs)){
				for(VFTransferDiffHsImg ec : imgs){
					cacuHsImgToDiffCount(rsMp,ec);
				}
			}
		}else{	/******** 统计料号级别的结转数据 ********/
			List<VFTransferDiffExgConvert> ecLs = vfTransferAnalyseDao.findHadConvertVFTransferDiffExgConvertByVFSection(section);
			if(CollectionUtils.isNotEmpty(ecLs)){
				for(VFTransferDiffExgConvert ec : ecLs){
					cacuExgConvertToDiffCount(rsMp,ec);
				}
			}
			List<VFTransferDiffImg> imgLs = vfTransferAnalyseDao.findHadConvertVFTransferDiffImgByVFsection(section);
			if(CollectionUtils.isNotEmpty(imgLs)){
				for(VFTransferDiffImg im : imgLs){
					cacuImgToDiffCount(rsMp, im);
				}
			}
		}
		VFTransferDiffCount[] rsAry = new VFTransferDiffCount[rsMp.size()];
		rsAry = rsMp.values().toArray(rsAry);
		List<VFTransferDiffCount> rs = Arrays.asList(rsAry);
		/**根据归并序号排序**/
		Collections.sort(rs,new Comparator<VFTransferDiffCount>() {
			public int compare(VFTransferDiffCount o1, VFTransferDiffCount o2) {
				return CommonUtils.getIntegerExceptNull(o1.getMergerNo()) - CommonUtils.getIntegerExceptNull(o2.getMergerNo());
			}
		});
		vfVerificationDao.batchSaveOrUpdateDirect(rs);		
		return rs;
	}
	
	/**
	 * 合并成品结转报关信息 汇总
	 * @param cmaps
	 */
	private void cacuHsExgResolveToDiffCount(Map<Integer,VFTransferDiffCount> cmaps,VFTransferDiffHsExgResolve exgc){
		if(exgc.getMergerNo() == null)
			return;
		VFTransferDiffCount dc = cmaps.get(exgc.getMergerNo());
		if(dc == null){
			dc = new VFTransferDiffCount();
			dc.setHsName(exgc.getHsName());
			dc.setHsSpec(exgc.getHsSpec());
			dc.setHsUnit(exgc.getHsUnit());
			dc.setCompany(CommonUtils.getCompany());
			dc.setMergerNo(exgc.getMergerNo());
			dc.setSection(exgc.getSection());
			dc.setSerialNumber(cmaps.size() +1);			
			dc.setConvertUnReceiveHadTransNum(0.0d);
			dc.setConvertUnTransHadReceiveNum(0.0d);
			dc.setConvertUnSendHadTransNum(exgc.getUnSendferNum());
			dc.setConvertUnTransHadSendNum(exgc.getUnTransferNum());
			cmaps.put(exgc.getMergerNo(), dc);
		}else{			
			dc.setConvertUnSendHadTransNum(CaleUtil.add(forD(exgc.getUnSendferNum()),forD(dc.getConvertUnSendHadTransNum())));
			dc.setConvertUnTransHadSendNum(CaleUtil.add(forD(exgc.getUnTransferNum()),forD(dc.getConvertUnTransHadSendNum())));
		}
		//计算差额
		cacuDiffCountDiffAmount(dc);
	}
	
	/**
	 * 合并料件结转报关信息 汇总
	 * @param cmaps
	 * @param img
	 */
	private void cacuHsImgToDiffCount(Map<Integer,VFTransferDiffCount> cmaps,VFTransferDiffHsImg img){
		if(img.getMergerNo() == null)
			return;
		VFTransferDiffCount dc = cmaps.get(img.getMergerNo());
		if(dc == null){
			dc = new VFTransferDiffCount();
			dc.setHsName(img.getHsName());
			dc.setHsSpec(img.getHsSpec());
			dc.setHsUnit(img.getHsUnit());
			dc.setCompany(CommonUtils.getCompany());
			dc.setMergerNo(img.getMergerNo());
			dc.setSection(img.getSection());
			dc.setSerialNumber(cmaps.size() +1);
			
			dc.setConvertUnReceiveHadTransNum(img.getUnReceiveHadTransNum());
			dc.setConvertUnTransHadReceiveNum(img.getUnTransHadReceiveNum());
			dc.setConvertUnSendHadTransNum(0.0d);
			dc.setConvertUnTransHadSendNum(0.0d);
			cmaps.put(img.getMergerNo(), dc);
		}else{			
			dc.setConvertUnReceiveHadTransNum(CaleUtil.add(forD(img.getUnReceiveHadTransNum()),forD(dc.getConvertUnReceiveHadTransNum())));
			dc.setConvertUnTransHadReceiveNum(CaleUtil.add(forD(img.getUnTransHadReceiveNum()),forD(dc.getConvertUnTransHadReceiveNum())));
		}
		//计算差额
		cacuDiffCountDiffAmount(dc);
	}
	/**
	 * 合并成品结转报关信息 汇总
	 * @param cmaps
	 */
	private void cacuExgConvertToDiffCount(Map<Integer,VFTransferDiffCount> cmaps,VFTransferDiffExgConvert exgc){
		VFTransferDiffCount dc = cmaps.get(exgc.getMergerNo());
		if(dc == null){
			dc = new VFTransferDiffCount();
			dc.setHsName(exgc.getHsName());
			dc.setHsSpec(exgc.getHsSpec());
			dc.setHsUnit(exgc.getHsUnit());
			dc.setCompany(CommonUtils.getCompany());
			dc.setMergerNo(exgc.getMergerNo());
			dc.setSection(exgc.getSection());
			dc.setSerialNumber(cmaps.size() +1);
			
			dc.setConvertUnReceiveHadTransNum(0.0d);
			dc.setConvertUnTransHadReceiveNum(0.0d);
			dc.setConvertUnSendHadTransNum(exgc.getConvertUnSendHadTransNum());
			dc.setConvertUnTransHadSendNum(exgc.getConvertUnTransHadSendNum());
			cmaps.put(exgc.getMergerNo(), dc);
		}else{			
			dc.setConvertUnSendHadTransNum(CaleUtil.add(forD(exgc.getConvertUnSendHadTransNum()),forD(dc.getConvertUnSendHadTransNum())));
			dc.setConvertUnTransHadSendNum(CaleUtil.add(forD(exgc.getConvertUnTransHadSendNum()),forD(dc.getConvertUnTransHadSendNum())));
		}
		//计算差额
		cacuDiffCountDiffAmount(dc);
	}
	/**
	 * 合并料件结转报关信息 汇总
	 * @param cmaps
	 * @param img
	 */
	private void cacuImgToDiffCount(Map<Integer,VFTransferDiffCount> cmaps,VFTransferDiffImg img){
		VFTransferDiffCount dc = cmaps.get(img.getMergerNo());
		if(dc == null){
			dc = new VFTransferDiffCount();
			dc.setHsName(img.getHsName());
			dc.setHsSpec(img.getHsSpec());
			dc.setHsUnit(img.getHsUnit());
			dc.setCompany(CommonUtils.getCompany());
			dc.setMergerNo(img.getMergerNo());
			dc.setSection(img.getSection());
			dc.setSerialNumber(cmaps.size() +1);
			
			dc.setConvertUnReceiveHadTransNum(img.getConvertUnReceiveHadTransNum());
			dc.setConvertUnTransHadReceiveNum(img.getConvertUnTransHadReceiveNum());
			dc.setConvertUnSendHadTransNum(0.0d);
			dc.setConvertUnTransHadSendNum(0.0d);
			cmaps.put(img.getMergerNo(), dc);
		}else{			
			dc.setConvertUnReceiveHadTransNum(CaleUtil.add(forD(img.getConvertUnReceiveHadTransNum()),forD(dc.getConvertUnReceiveHadTransNum())));
			dc.setConvertUnTransHadReceiveNum(CaleUtil.add(forD(img.getConvertUnTransHadReceiveNum()),forD(dc.getConvertUnTransHadReceiveNum())));
		}
		//计算差额
		cacuDiffCountDiffAmount(dc);
	}
	/**
	 * 计算差额
	 * @param dc
	 */
	private void cacuDiffCountDiffAmount(VFTransferDiffCount dc){
		Double sum1 = CaleUtil.add(dc.getConvertUnTransHadSendNum(), dc.getConvertUnReceiveHadTransNum());
		Double sum2 = CaleUtil.add(dc.getConvertUnSendHadTransNum(),dc.getConvertUnTransHadReceiveNum() );
		dc.setDiffAmount(CaleUtil.subtract(sum1, sum2));		
	}
	
	/**
	 * 从深加工结转收货单获取报关编码级结转料件
	 * @param section
	 * @return
	 */
	public List<VFTransferDiffHsImg> generateTransferDiffHsImgHsFormFpt(VFSection section){
		Request request = CommonUtils.getRequest();		
		ProgressInfo info = null;
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
			info.setMethodName("正在删除本批次的历史数据,请稍等...");
			info.setTotalNum(5);
		}
		//先删除已经转换的数据
		this.deleteVFTransferDiffHsImgs(section);
		if(info!= null){
			info.setMethodName("正在获取收货单据信息,请稍等...");
			info.setCurrentNum(1);
		}
		Map<String,VFTransferDiffHsImg> resultMap = new HashMap<String, VFTransferDiffHsImg>();
		String key = "";	//key 手册号+'/'+备案号
		VFTransferDiffHsImg img = null;
		/*** 第一步处理收货信息***/
		List<Object[]> items = vfTransferAnalyseDao.findInFptBillItem(section);
		FptBillItem item = null;
		for(Object[] o :items){
			item = (FptBillItem)o[0];
			key = item.getInEmsNo()+"/"+item.getTrGno();
			img = resultMap.get(key);
			if(img == null){
				img = new VFTransferDiffHsImg();
				img.init();
				img.setSection(section);
				img.setHsName(item.getCommName());
				img.setHsSpec(item.getCommSpec());
				img.setHsUnit(item.getUnit() == null ? null : item.getUnit().getName());
				img.setEmsNo(item.getInEmsNo());
				img.setMergerNo((Integer)o[1]);
				img.setSeqNum(item.getTrGno());				
				img.setCompany(CommonUtils.getCompany());
				resultMap.put(key, img);
			}
			if(FptBusinessType.FPT_BILL.equals(item.getFptBillHead().getSysType())){				
				img.setReceiveBillNum(CaleUtil.add(img.getReceiveBillNum(),item.getQty()));
			}else if(FptBusinessType.FPT_BILL_BACK.equals(item.getFptBillHead().getSysType())){
				img.setReceiveBillNum(CaleUtil.subtract(img.getReceiveBillNum(),item.getQty()));
			}
		}
		if(info!= null){
			info.setMethodName("正在获取报关单据信息,请稍等...");
			info.setCurrentNum(2);
		}
		/***************第二步处理报关信息**************/
		List<Object[]> commInfos = vfTransferAnalyseDao.findImpFptBcsCustomsDeclarationCommInfo(section);
		BcsCustomsDeclarationCommInfo bcdc = null;
		for(Object[] commInfo : commInfos){
			bcdc = (BcsCustomsDeclarationCommInfo)commInfo[0];
			key = bcdc.getBaseCustomsDeclaration().getEmsHeadH2k() + "/" + bcdc.getCommSerialNo();
			img = resultMap.get(key);
			if(img == null){
				img = new VFTransferDiffHsImg();
				img.init();
				img.setSection(section);
				img.setHsName(bcdc.getCommName());
				img.setHsSpec(bcdc.getCommSpec());
				img.setHsUnit(bcdc.getUnit() == null ? null : bcdc.getUnit().getName());
				img.setEmsNo(bcdc.getBaseCustomsDeclaration().getEmsHeadH2k());
				img.setMergerNo((Integer)commInfo[1]);
				img.setSeqNum(bcdc.getCommSerialNo());				
				img.setCompany(CommonUtils.getCompany());
				resultMap.put(key, img);
			}
			img.setCustomsBillNum(CaleUtil.add(img.getCustomsBillNum(),bcdc.getCommAmount()));
		}
		
		/*********第三步：按照备案序号排序*******/
		List<VFTransferDiffHsImg> result = new ArrayList<VFTransferDiffHsImg>(resultMap.values());
		Collections.sort(result, new Comparator<VFTransferDiffHsImg>() {
			public int compare(VFTransferDiffHsImg o1, VFTransferDiffHsImg o2) {
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});
		if(info!= null){
			info.setMethodName("正在结算差额数据,请稍等...");
			info.setCurrentNum(3);
		}
		/*********第四步：计算并设置差额数据*******/
		double d = 0;
		for(VFTransferDiffHsImg hsImg : result){
			d = CaleUtil.subtract(hsImg.getReceiveBillNum(),hsImg.getCustomsBillNum());
			if(d > 0){
				hsImg.setUnTransHadReceiveNum(d);
			}else{
				hsImg.setUnReceiveHadTransNum(-d);
			}			
		}
		if(info!= null){
			info.setMethodName("正在保存结果,请稍等...");
			info.setCurrentNum(4);
		}
		/*********第五步：批量保存数据***********************/
		vfVerificationDao.batchSaveOrUpdateDirect(result);
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
	public List<VFTransferDiffHsExg> generateTransferDiffHsExgsFormFpt(VFSection section){
		Request request = CommonUtils.getRequest();		
		ProgressInfo info = null;
		if(request != null && StringUtils.isNotBlank(request.getTaskId())){
			info =  ProcExeProgress.getInstance().getProgressInfo(request.getTaskId());
			info.setMethodName("正在删除本批次的历史数据,请稍等...");
			info.setTotalNum(6);
		}

		this.deleteVFTransferDiffHsExgs(section);
		if(info!= null){
			info.setMethodName("正在获取发货单据信息,请稍等...");
			info.setCurrentNum(1);
		}
		Map<String,VFTransferDiffHsExg> resultMap = new HashMap<String, VFTransferDiffHsExg>();
		String key = "";	//key 手册号+'/'+备案号
		VFTransferDiffHsExg exg = null;
		/*** 第一步处理收货信息***/
		List<FptBillItem> items = vfTransferAnalyseDao.findOutFptBillItem(section);
		for(FptBillItem item :items){
			key = item.getFptBillHead().getOutEmsNo()+"/"+item.getTrGno();
			exg = resultMap.get(key);
			if(exg == null){
				exg = new VFTransferDiffHsExg();
				exg.setSection(section);
				exg.setHsName(item.getCommName());
				exg.setHsSpec(item.getCommSpec());
				exg.setHsUnit(item.getUnit() == null ? null:item.getUnit().getName());
				exg.setEmsNo(item.getFptBillHead().getOutEmsNo());
				exg.setSeqNum(item.getTrGno());				
				exg.setCompany(CommonUtils.getCompany());
				resultMap.put(key, exg);
			}
			if(FptBusinessType.FPT_BILL.equals(item.getFptBillHead().getSysType())){				
				exg.setSendBillNum(CaleUtil.add(exg.getSendBillNum(),item.getQty()));
			}else if(FptBusinessType.FPT_BILL_BACK.equals(item.getFptBillHead().getSysType())){
				exg.setSendBillNum(CaleUtil.subtract(exg.getSendBillNum(),item.getQty()));
			}
		}
		if(info!= null){
			info.setMethodName("正在获取报关单据信息,请稍等...");
			info.setCurrentNum(2);
		}
		/***************第二步处理报关信息**************/
		List<BcsCustomsDeclarationCommInfo> commInfos = vfTransferAnalyseDao.findExpFptBcsCustomsDeclarationCommInfo(section);
		for(BcsCustomsDeclarationCommInfo bcdc : commInfos){
			key = bcdc.getBaseCustomsDeclaration().getEmsHeadH2k() + "/" + bcdc.getCommSerialNo();
			exg = resultMap.get(key);
			if(exg == null){
				exg = new VFTransferDiffHsExg();
				exg.setSection(section);
				exg.setHsName(bcdc.getCommName());
				exg.setHsSpec(bcdc.getCommSpec());
				exg.setHsUnit(bcdc.getUnit() == null ? null : bcdc.getUnit().getName());
				exg.setEmsNo(bcdc.getBaseCustomsDeclaration().getEmsHeadH2k());
				exg.setSeqNum(bcdc.getCommSerialNo());				
				exg.setCompany(CommonUtils.getCompany());
				resultMap.put(key, exg);
			}
			exg.setCustomsBillNum(CaleUtil.add(exg.getCustomsBillNum(),bcdc.getCommAmount()));
		}
		/*********第三步：按照备案序号排序*******/
		List<VFTransferDiffHsExg> result = new ArrayList<VFTransferDiffHsExg>(resultMap.values());
		Collections.sort(result, new Comparator<VFTransferDiffHsExg>() {
			public int compare(VFTransferDiffHsExg o1, VFTransferDiffHsExg o2) {
				return o1.getSeqNum().compareTo(o2.getSeqNum());
			}
		});
		if(info!= null){
			info.setMethodName("正在结算差额数据,请稍等...");
			info.setCurrentNum(3);
		}
		/*********第四步：计算并设置差额数据*******/
		double d = 0;
		for(VFTransferDiffHsExg hsexg : result){
			d = CaleUtil.subtract(hsexg.getSendBillNum(),hsexg.getCustomsBillNum());
			if(d > 0){
				hsexg.setUnTransHadSendNum(d);
			}else{
				hsexg.setUnSendHadTransNum(-d);
			}			
		}
		if(info!= null){
			info.setMethodName("正在保存结果,请稍等...");
			info.setCurrentNum(4);
		}
		/*********第五步：批量保存数据***********************/
		vfVerificationDao.batchSaveOrUpdateDirect(result);
		if(info!= null){
			info.setMethodName("从深加工结转获取数据完毕，正在进行折料,请稍等...");
			info.setCurrentNum(5);
		}
		/*****************第六步：折算料件******************/
		resolveTransferDiffHsExgs(section,result,false);
		if(info!= null){
			info.setMethodName("正在返回结果数据,请稍等...");
			info.setCurrentNum(6);
		}
		return result;
	}
	
	public List<VFTransferDiffHsExgResolve> resolveTransferDiffHsExgs(VFSection section){
		List<VFTransferDiffHsExg> ls= findVFTransferDiffHsExgs(section);
		return resolveTransferDiffHsExgs(section, ls, Boolean.TRUE);
	}
	/**
	 * 编码级结转结转成品折算料件
	 * @param exgs
	 * @return
	 */
	private List<VFTransferDiffHsExgResolve> resolveTransferDiffHsExgs(VFSection section,List<VFTransferDiffHsExg> exgs,boolean isVaild){
		String key = null;	//手册号+‘/’+备案序号
		/**********第一步：准备成品单耗数据**********/
		List<Object[]> bomResultLs = vfTransferAnalyseDao.findProcessExeContractBom();
		Map<String,List<ContractBom>> exgBomMap = new HashMap<String,List<ContractBom>>();
		List<ContractBom> bomls = null;
		for(Object[] o : bomResultLs){
			key = o[0]+"/"+o[1];
			bomls = exgBomMap.get(key);
			if(bomls == null){
				bomls = new ArrayList<ContractBom>();
				exgBomMap.put(key,bomls);
			}
			bomls.add(((ContractBom)o[2]));
		}
		/**********第二步：准备料件归并序号数据**********/
		Map<String,Integer> mergeMap = new HashMap<String,Integer>();
		List<Object[]> mergeLs = vfTransferAnalyseDao.findMergerNoBySeqNum();
		for(Object[] o : mergeLs){
			key = o[0]+"/"+o[1];
			mergeMap.put(key, (Integer)o[2]);
		}
		/*********第三步：拆分成品至料件数据******/
		List<VFTransferDiffHsExgResolve> resultLs = new ArrayList<VFTransferDiffHsExgResolve>();
		List<VFTransferDiffHsExgResolve> tmpLs = null;
		for(VFTransferDiffHsExg exg : exgs){
			tmpLs = resolveTransferDiffHsExg(section,exg, exgBomMap, mergeMap,isVaild);
			if(CollectionUtils.isNotEmpty(tmpLs))
				resultLs.addAll(tmpLs);
		}
		/*********第四步：保存折算料件数据******/
		vfVerificationDao.batchSaveOrUpdateDirect(resultLs);
		return resultLs;
	}
	/**
	 * 单个成品编码报关级别折料件
	 * @param exg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<VFTransferDiffHsExgResolve> resolveTransferDiffHsExg(VFSection section,VFTransferDiffHsExg exg,Map<String,List<ContractBom>> bomMap,Map<String,Integer> mergeMap,boolean isVaild){
		List<ContractBom> bomls = null;
		String key = exg.getEmsNo()+"/"+exg.getSeqNum();
		bomls = bomMap.get(key);
		if(bomls == null || bomls.isEmpty()){
			if(isVaild)
				throw new AppException("【通关手册中】手册:"+exg.getEmsNo()+" 成品序号:"+exg.getSeqNum()+" 单耗表数据为空");
		}else{		
			List<VFTransferDiffHsExgResolve> resultLs = new ArrayList<VFTransferDiffHsExgResolve>(bomls.size());
			VFTransferDiffHsExgResolve resolve = null; 
			for(ContractBom bom : bomls){
				resolve = new VFTransferDiffHsExgResolve();
				resolve.setSection(section);
				resolve.setCompany(CommonUtils.getCompany());				
				resolve.setHsExg(exg);
				//商品名称
				resolve.setHsName(bom.getName());
				//规格
				resolve.setHsSpec(bom.getSpec());
				//单位
				resolve.setHsUnit(bom.getUnit() == null ? null : bom.getUnit().getName());
				//备案序号
				resolve.setSeqNum(bom.getContractImgSeqNum());				
				//已转厂未送货
				resolve.setUnSendferNum(CaleUtil.multiply(exg.getUnSendHadTransNum(), bom.getUnitDosage()));
				//已送货未转厂
				resolve.setUnTransferNum(CaleUtil.multiply(exg.getUnTransHadSendNum(), bom.getUnitDosage()));
				//归并序号
				Integer mergeNo = mergeMap.get(exg.getEmsNo()+"/"+resolve.getSeqNum());
				if(isVaild && mergeNo == null)
					throw new AppException("【 备案资料库料件表】手册："+exg.getEmsNo()+" 料件序号："+bom.getContractImgSeqNum()+"的物料归并序号无法找到");				
				resolve.setMergerNo(mergeNo);
				
				resultLs.add(resolve);
			}
			return resultLs;
		}
		return Collections.EMPTY_LIST;
	}
	
	/**
	 * 根据section(批次)删除汇总信息
	 * @param section 批次
	 */
	public void deleteVFTransferDiffCount(VFSection vfsection) {
		vfVerificationDao.deleteByVFSection(vfsection, VFTransferDiffCount.class.getName());
	}
	
	private Double forD(Double d){
		return d == null ? 0.0d : d;
	}
	/**
	 * @param vfVerificationDao
	 *            the vfVerificationDao to set
	 */
	public void setVfVerificationDao(VFVerificationDao vfVerificationDao) {
		this.vfVerificationDao = vfVerificationDao;
	}

	/**
	 * @param vfTransferAnalyseDao the vfTransferAnalyseDao to set
	 */
	public void setVfTransferAnalyseDao(VFTransferAnalyseDao vfTransferAnalyseDao) {
		this.vfTransferAnalyseDao = vfTransferAnalyseDao;
	}
		
}
