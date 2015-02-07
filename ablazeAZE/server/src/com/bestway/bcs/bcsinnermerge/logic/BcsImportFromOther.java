/*
 * Created on 2004-7-13
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.bcsinnermerge.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeFileData;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.dao.ParameterCodeDao;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoi;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 * 
 */
public class BcsImportFromOther {

	/**
	 * 工厂通用代码（方法）
	 */
	private MaterialManageDao materialManageDao;

	/**
	 * 内部归并DAO接口
	 */
	private BcsInnerMergeDao bcsInnerMergeDao;

	/**
	 * 商品管理DAO接口
	 */
	private HsCodeDao hsCodeDao;

	/**
	 * 参数代码DAO接口
	 */
	private ParameterCodeDao paramerterCodeDao;

	/**
	 * 单位MAP
	 */

	/**
	 * 内部归并DAO接口
	 * 
	 * @return
	 */
	public BcsInnerMergeDao getBcsInnerMergeDao() {
		return bcsInnerMergeDao;
	}

	/**
	 * 内部归并DAO接口
	 * 
	 * @return
	 */
	public void setBcsInnerMergeDao(BcsInnerMergeDao bcsInnerMergeDao) {
		this.bcsInnerMergeDao = bcsInnerMergeDao;
	}

	/**
	 * 参数代码DAO接口
	 */
	public ParameterCodeDao getParamerterCodeDao() {
		return paramerterCodeDao;
	}

	/**
	 * 参数代码DAO接口
	 */
	public void setParamerterCodeDao(ParameterCodeDao paramerterCodeDao) {
		this.paramerterCodeDao = paramerterCodeDao;
	}

	/**
	 * 参数代码DAO接口
	 */
	public HsCodeDao getHsCodeDao() {
		return hsCodeDao;
	}

	/**
	 * 参数代码DAO接口
	 */
	public void setHsCodeDao(HsCodeDao hsCodeDao) {
		this.hsCodeDao = hsCodeDao;
	}

	/**
	 * 工厂通用代码（方法）
	 */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/**
	 * 工厂通用代码（方法）
	 */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/**
	 * 计算料件数量
	 */
	private Map<String, Integer> getMaterielCount(List list) {
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMergeFileData fileData = (BcsInnerMergeFileData) list
					.get(i);
			if (hm.get(fileData.getBeforeMaterielCode()) == null) {
				hm.put(fileData.getBeforeMaterielCode(), 1);
			} else {
				hm.put(fileData.getBeforeMaterielCode(), Integer.valueOf(hm
						.get(fileData.getBeforeMaterielCode()).toString()) + 1);
			}
		}
		return hm;
	}

	/**
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @return
	 */
	public List checkFileData(List list, Hashtable ht, String taskId) {
		ArrayList<BcsInnerMergeFileData> ls = new ArrayList<BcsInnerMergeFileData>();
		// ArrayList<Integer> temp = new ArrayList<Integer>();
		// calUnitMap = null;
		// unitMap = null;
		// complexMap = null;
		// cMap = null;
		// getCustomsComplex();
		// BcsInnerMergeFileData fileData = null;
		// int[] invalidationColNo;
		// ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
		// taskId);
		// if (info != null) {
		// info.setBeginTime(System.currentTimeMillis());
		// info.setTotalNum(list.size());
		// info.setCurrentNum(0);
		// info.setMethodName("系统正在检验文件资料");
		// }
		// Map<String, Integer> hmMaterielCount = getMaterielCount(list);
		// List exsitPtNoList = bcsInnerMergeDao.findInnerMergeDataPtNo();
		// for (int i = 0; i < list.size(); i++) {
		// temp.clear();
		// fileData = (BcsInnerMergeFileData) list.get(i);
		// if (Integer
		// .parseInt(ht
		// .get(
		// Integer
		// .valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_CODE))
		// .toString()) > -1) {
		// // if
		// // (bcsInnerMergeDao.findInnerMergeDataByMaterialCode(fileData
		// // .getBeforeMaterielCode()) != null) {
		// if (exsitPtNoList.contains(fileData.getBeforeMaterielCode()
		// .trim())) {
		// temp
		// .add(Integer
		// .valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_CODE));
		// fileData.setErrorReason(fileData.getErrorReason()
		// + "归并前料号在归并中已存在;");
		// } else if (hmMaterielCount
		// .get(fileData.getBeforeMaterielCode()) != null
		// && hmMaterielCount
		// .get(fileData.getBeforeMaterielCode()) > 1) {
		// temp
		// .add(Integer
		// .valueOf(BcsInnerMergeFileData.BEFORE_MATERIEL_CODE));
		// fileData.setErrorReason(fileData.getErrorReason()
		// + "归并前料号重复;");
		// }
		// }
		// if (Integer
		// .parseInt(ht
		// .get(
		// Integer
		// .valueOf(BcsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE))
		// .toString()) > -1) {
		// if (getComplex(fileData.getBeforeTenComplexCode()) == null) {
		// temp
		// .add(Integer
		// .valueOf(BcsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE));
		// fileData.setErrorReason(fileData.getErrorReason()
		// + "归并前海关商品编码库不存在;");
		// }
		// }
		// if (Integer.parseInt(ht.get(
		// Integer.valueOf(BcsInnerMergeFileData.BEFORE_UNIT))
		// .toString()) > -1) {
		// if (getUnit(fileData.getBeforeUnit()) == null) {
		// temp
		// .add(Integer
		// .valueOf(BcsInnerMergeFileData.BEFORE_UNIT));
		// fileData.setErrorReason(fileData.getErrorReason()
		// + "归并前申报单位不存在;");
		// }
		// }
		// // if (Integer.parseInt(ht.get(
		// // Integer.valueOf(DzscInnerMergeFileData.BEFORE_LEGAL_UNIT))
		// // .toString()) > -1) {
		// // if (getUnit(fileData.getBeforeLegalUnit()) == null) {
		// // temp.add(Integer
		// // .valueOf(DzscInnerMergeFileData.BEFORE_LEGAL_UNIT));
		// // fileData.setErrorReason(fileData.getErrorReason()
		// // + "归并前法定单位不存在;");
		// // }
		// // }
		// /*
		// * if (Integer .parseInt(ht .get( Integer
		// * .valueOf(DzscInnerMergeFileData.BEFORE_ENTERPRISE_UNIT))
		// * .toString()) > -1) { if
		// * (getCalUnit(fileData.getBeforeEnterpriseUnit()) == null) { temp
		// * .add(Integer
		// * .valueOf(DzscInnerMergeFileData.BEFORE_ENTERPRISE_UNIT));
		// * fileData.setErrorReason(fileData.getErrorReason() +
		// * "归并前企业单位不存在;"); } }
		// */
		// if (Integer
		// .parseInt(ht
		// .get(
		// Integer
		// .valueOf(BcsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE))
		// .toString()) > -1) {
		// if (getComplex(fileData.getAfterTenComplexCode()) == null) {
		// temp
		// .add(Integer
		// .valueOf(BcsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE));
		// fileData.setErrorReason(fileData.getErrorReason()
		// + "归并后海关商品编码库不存在;");
		// }
		// }
		// if (Integer.parseInt(ht.get(
		// Integer.valueOf(BcsInnerMergeFileData.AFTER_UNIT))
		// .toString()) > -1) {
		// if (getUnit(fileData.getAfterUnit()) == null) {
		// temp.add(Integer.valueOf(BcsInnerMergeFileData.AFTER_UNIT));
		// fileData.setErrorReason(fileData.getErrorReason()
		// + "归并后单位不存在;");
		// }
		// }
		// // if (Integer.parseInt(ht.get(
		// // Integer.valueOf(DzscInnerMergeFileData.AFTER_LEGAL_UNIT))
		// // .toString()) > -1) {
		// // if (getUnit(fileData.getAfterLegalUnit()) == null) {
		// // temp.add(Integer
		// // .valueOf(DzscInnerMergeFileData.AFTER_LEGAL_UNIT));
		// // fileData.setErrorReason(fileData.getErrorReason()
		// // + "归并后法定单位不存在;");
		// // }
		// // }
		// // if (Integer.parseInt(ht.get(
		// // Integer.valueOf(DzscInnerMergeFileData.AFTER_MEMO_UNIT))
		// // .toString()) > -1) {
		// // if (getUnit(fileData.getAfterMemoUnit()) == null) {
		// // temp.add(Integer
		// // .valueOf(DzscInnerMergeFileData.AFTER_MEMO_UNIT));
		// // fileData.setErrorReason(fileData.getErrorReason()
		// // + "归并后备案单位不存在;");
		// // }
		// // }
		//
		// if (temp.size() > 0) {
		// invalidationColNo = new int[temp.size()];
		// for (int j = 0; j < temp.size(); j++) {
		// invalidationColNo[j] = Integer.parseInt(temp.get(j)
		// .toString());
		// }
		// fileData.setInvalidationColNo(invalidationColNo);
		// ls.add(fileData);
		// }
		// if (info != null) {
		// info.setCurrentNum(i);
		// }
		// }
		return ls;
	}
	
	/*public void chooseOldImportOrNewImport(List list,String taskId, String materielType, Boolean importMerge){
		if(list.size() == 1){
			importOldDataFromTxtFile(list, taskId, materielType, importMerge);
		}
		if(list.size() == 3){
			importDataFromTxtFile(list,taskId,materielType,importMerge);
		}
	}*/

	/**
	 * 导入物料与报关对应表资料
	 * 
	 * @param list
	 * @param isConver
	 * @param taskId
	 */
//	public void importOldDataFromTxtFile(List<BcsInnerMergeFileData> list, String taskId, String materielType, Boolean importMerge) {
	public void importDataFromTxtFile(List list, String taskId, String materielType, Boolean importMerge,Boolean isUpdateMaterial) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在导入文件资料");
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+"list");
		// 导入对应关系
		if(importMerge) {
			importBcsInnerMerge(list, materielType, info);
		}else {
			importMaterial(list, materielType, info,isUpdateMaterial);
		}
	}
	/**
	 * 导入物料主档(工厂和报关常用) 和 对应关系工厂资料部分。
	 * @param list
	 * @param scmCoi
	 * @param info
	 */
	private void importMaterial(List _list, String materielType, ProgressInfo info,Boolean isUpdateMaterial) {
	
		ScmCoi scmCoi = null;
	    List list=null;
		if (_list.size() > 0) {
			scmCoi = materialManageDao.findScmCoiByType(materielType);
			list = (List)_list.get(0);
		} else {
			throw new RuntimeException("没有新增的归并可导入");
		}
		if (scmCoi == null) {
			String typeName = "";
			if (MaterielType.MATERIEL.equals(materielType)) {
				typeName = "料件";
			} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
				typeName = "成品";
			} else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
				typeName = "半成品";
			}
			throw new RuntimeException(typeName + " 没有设定企业物料类别 ");
		}

		// 导入物料主档(工厂和报关常用)
		List<EnterpriseMaterial> enterpriseMaterials = materialManageDao.findEnterpriseMaterials();
		Map<String,EnterpriseMaterial> emMap = new HashMap<String,EnterpriseMaterial>();
		for (int i = 0; i < enterpriseMaterials.size(); i++) {
			EnterpriseMaterial enterpriseMaterial = enterpriseMaterials.get(i);
			emMap.put(enterpriseMaterial.getPtNo(), enterpriseMaterial);
		}
		
		//计量单位资料
		Map<String, CalUnit> unitMap = new HashMap<String, CalUnit>();
		List<CalUnit> units = materialManageDao.findCalUnit();
		for (int i = 0, n = units.size(); i < n; i++) {
			CalUnit u = units.get(i);
			unitMap.put(u.getName().trim(), u);
		}
		
		// 物料主档
		Map<String, Materiel> materialMap = new HashMap<String, Materiel>();
		List<Materiel> materiels = materialManageDao.findMateriel();
		for (int i = 0; i < materiels.size(); i++) {
			Materiel m = materiels.get(i);
			materialMap.put(m.getPtNo(), m);
		}
		
		//更新或新增工厂物料主档资料
		List<EnterpriseMaterial> listEm = new ArrayList<EnterpriseMaterial>();
		
		//报关常用工厂物料资料
		List<Materiel> listMateriel = new ArrayList<Materiel>();
		
		//BCS内部归并对应表
		List<BcsInnerMerge> listBcsInnerMerge = new ArrayList<BcsInnerMerge>();
		
		for (int i = 0; i < list.size(); i++) {
			BcsInnerMergeFileData data = (BcsInnerMergeFileData) list.get(i);
			String ptNo = data.getBeforeMaterielCode();
			if (ptNo.trim().equals("")) {
				continue;
			}
			
			CalUnit calUnit = unitMap.get(data.getBeforeEnterpriseUnit());
			if(calUnit == null) {
				throw new RuntimeException("不存在单位：" + data.getBeforeEnterpriseUnit() + "！");
			} 
			
			EnterpriseMaterial em = emMap.get(ptNo);//获得工厂物料主档
			if(isUpdateMaterial!=null&&isUpdateMaterial){
				if(em==null){
					em = new EnterpriseMaterial();
				}
				em.setPtNo(data.getBeforeMaterielCode());
				em.setCompany(CommonUtils.getCompany());
				em.setScmCoi(scmCoi);
				em.setFactoryName(data.getBeforeMaterielName());
				em.setFactorySpec(data.getBeforeMaterielSpec());
				em.setPtNetWeight(data.getUnitWeight() == null ? 0.0
						: Double.parseDouble(data.getUnitWeight()));
				em.setUnitConvert(data.getUnitConvert() == null ? 0.0
						: Double.parseDouble(data.getUnitConvert()));
				em.setPtPrice(data.getPtPrice() == null ? 0.0 : Double
						.parseDouble(data.getPtPrice()));
				em.setCalUnit(calUnit);
				if(data.getUnitConvert()!=null){
					em.setUnitConvert(Double.parseDouble(data.getUnitConvert()));
				}
				listEm.add(em);
			}else{
				if(em==null){
				 	throw new RuntimeException("料号"+data.getBeforeMaterielCode()+"在工厂物料主档找不到！");
				}
			}
			
			
			Materiel materiel = materialMap.get(ptNo);//获得报关常用工厂物料资料
			if(isUpdateMaterial!=null&&isUpdateMaterial){
				if (materiel == null) {
					materiel = new Materiel();
				}else{
					checkType(scmCoi,materiel,materielType);
				}
				materiel.setPtNo(data.getBeforeMaterielCode());
				materiel.setIsNewMateriel(new Boolean(true));
				materiel.setCompany(CommonUtils.getCompany());
				materiel.setScmCoi(scmCoi);
				materiel.setFactoryName(data.getBeforeMaterielName());
				materiel.setFactorySpec(data.getBeforeMaterielSpec());
				materiel.setPtNetWeight(data.getUnitWeight() == null ? 0.0
						: Double.parseDouble(data.getUnitWeight()));
				materiel.setUnitConvert(data.getUnitConvert() == null ? 0.0
						: Double.parseDouble(data.getUnitConvert()));
				materiel.setPtPrice(data.getPtPrice() == null ? 0.0 : Double
						.parseDouble(data.getPtPrice()));
				materiel.setCalUnit(calUnit);
				
				if(data.getUnitConvert()!=null){
					materiel.setUnitConvert(Double.parseDouble(data.getUnitConvert()));
				}
				
			}else {
				if (materiel != null) {
					checkType(scmCoi,materiel,materielType);
				}else {
					throw new RuntimeException("料号"+data.getBeforeMaterielCode()+"在报关常用工厂物料资料找不到！");
				}
			}
			materiel.setIsUseInBcsInnerMerge(true);
			listMateriel.add(materiel);
			
			BcsInnerMerge bcsInnerMergeData = new BcsInnerMerge();
			bcsInnerMergeData.setCompany(CommonUtils.getCompany());
			bcsInnerMergeData.setMaterielType(materielType);
			bcsInnerMergeData.setMateriel(materiel);
			if(data.getUnitConvert()!=null){
				bcsInnerMergeData.setUnitConvert(Double.parseDouble(data.getUnitConvert()));
			}
			listBcsInnerMerge.add(bcsInnerMergeData);
			
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
		
		if(listEm.size()>0){
			materialManageDao.batchSaveOrUpdate(listEm);
		}
		
		if(listMateriel.size()>0){
			materialManageDao.batchSaveOrUpdate(listMateriel);
		}
		materialManageDao.batchSaveOrUpdate(listBcsInnerMerge);
	}
	
	private void checkType(ScmCoi scmCoi,Materiel materiel,String materielType){
		// 不为空的时候检查物料属性，防止成品对应关系导入的时候引用料件物料。或者反之。
		if (!scmCoi.getCode().equals(materiel.getScmCoi().getCode())) {
			String typeName = "";
			if (MaterielType.MATERIEL.equals(materielType)) {
				typeName = "料件";
			} else if (MaterielType.FINISHED_PRODUCT
					.equals(materielType)) {
				typeName = "成品";
			} else if (MaterielType.SEMI_FINISHED_PRODUCT
					.equals(materielType)) {
				typeName = "半成品";
			}
			throw new RuntimeException("此料号:"+materiel.getPtNo()+"在报关常用工厂物料中属性为：" + typeName+"无法导入");
		}
	}

	/**
	 * 导入对应关系
	 * @param list
	 * @param materielType
	 * @param info
	 */
	private void importBcsInnerMerge(List<BcsInnerMergeFileData> list, String materielType, ProgressInfo info) {

		
		// 物料主档
		Map<String, Materiel> materialMap = new HashMap<String, Materiel>();
		long t = System.currentTimeMillis();
		List<Materiel> materiels = materialManageDao.findMateriel();
		Materiel m = null;
		for (int i = 0; i < materiels.size(); i++) {
			m = materiels.get(i);
			materialMap.put(m.getPtNo(), m);
		}
		// 报关商品资料
		Map<Integer, BcsTenInnerMerge> tenMap = new HashMap<Integer, BcsTenInnerMerge>();
		List<BcsTenInnerMerge> tenInnerMerges = bcsInnerMergeDao.findBcsTenInnerMerge(materielType);
		BcsTenInnerMerge tenInnerMerge = null;
		for (int i = 0; i < tenInnerMerges.size(); i++) {
			tenInnerMerge = tenInnerMerges.get(i);
			tenMap.put(tenInnerMerge.getSeqNum(), tenInnerMerge);
		}
		// 归并关系业务keys
		Set<String> mergeSet = new HashSet<String>(bcsInnerMergeDao.findBcsInnerMergeKeys(materielType));
		BcsInnerMergeFileData fileData = null;
		List<String> ptNos = new ArrayList<String>();
		Map<String,BcsInnerMerge> oldMergerMap = new HashMap<String,BcsInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			fileData = list.get(i);
			if(fileData.getIsCount() && !ptNos.contains(fileData.getBeforeMaterielCode())){
				ptNos.add(fileData.getBeforeMaterielCode());
			}			
		}
		if(!ptNos.isEmpty()){
			List<BcsInnerMerge> ls = bcsInnerMergeDao.findBcsInnerMergeByPtNos(ptNos);
			for(BcsInnerMerge bim : ls){
				oldMergerMap.put(bim.getMateriel().getPtNo(), bim);
			}
		}
		String ptNo = null;
		Integer seqNum = null;
		BcsInnerMerge bcsInnerMergeData = null;
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String key = null;
		ScmCoi scmCoi = null;
		List<BcsInnerMerge> mergeDataLs = new ArrayList<BcsInnerMerge>();
		List<Materiel> materielLs = new ArrayList<Materiel>();
		for (int i = 0, n = list.size(); i < n; i++) {
			fileData = list
					.get(i);
			ptNo = fileData.getBeforeMaterielCode();
			seqNum = Integer.parseInt(fileData.getAfterTenMemoNo());
			
			key = ptNo + "/" + seqNum;
			
			
			if (mergeSet.contains(key)) {
				throw new RuntimeException("已经存在料号：" + ptNo + "，和报关资料：" + seqNum + "的物料报关对应关系！");
			} else {
				bcsInnerMergeData = new BcsInnerMerge();
				bcsInnerMergeData.setCompany(CommonUtils.getCompany());
			}
			
			m = materialMap.get(ptNo);
			if(m == null) {
				throw new RuntimeException("报关常用工厂物料不存在料号：" + ptNo + "！");
			} else {
				// 不为空的时候检查物料属性，防止成品对应关系导入的时候引用料件物料。或者反之。
				
				if (list.size() > 0) {
					if(scmCoi == null){
						scmCoi = materialManageDao
								.findScmCoiByType(materielType);
					}
				} else {
					throw new RuntimeException("没有新增的归并可导入");
				}
				if (scmCoi == null) {
					String typeName = "";
					if (MaterielType.MATERIEL.equals(materielType)) {
						typeName = "料件";
					} else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
						typeName = "成品";
					} else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
						typeName = "半成品";
					}
					throw new RuntimeException(typeName + " 没有设定企业物料类别 ");
				}
				
				if (!scmCoi.getCode().equals(m.getScmCoi().getCode())) {
					String typeName = "";
					if (MaterielType.MATERIEL.equals(materielType)) {
						typeName = "料件";
					} else if (MaterielType.FINISHED_PRODUCT
							.equals(materielType)) {
						typeName = "成品";
					} else if (MaterielType.SEMI_FINISHED_PRODUCT
							.equals(materielType)) {
						typeName = "半成品";
					}
					
					throw new RuntimeException(typeName + " 工厂资料导入，已经存在料号为"
							+ m.getPtNo() + "报关常用工厂物料，但是物料属性不为：" + typeName);
				}
			}
			
			tenInnerMerge = tenMap.get(seqNum);
			if(tenInnerMerge == null) {
				throw new RuntimeException("不存在报关商品资料序号：" + seqNum + "！");
			}
			
			bcsInnerMergeData.setMaterielType(materielType);
			bcsInnerMergeData.setMateriel(m);
			bcsInnerMergeData.setBcsTenInnerMerge(tenInnerMerge);
			bcsInnerMergeData
					.setUnitConvert(fileData.getUnitConvert() == null ? 0.0
							: Double.parseDouble(fileData.getUnitConvert()));
			bcsInnerMergeData.setCreateDate(java.sql.Date
					.valueOf(bartDateFormat.format(new Date())));// 导入时间
			bcsInnerMergeData.setIsUsing(fileData.getIsCount());
			/*********************************************************************************
			 * 如果新增的归并关系设置为当前使用，那么先以Key=料号，判断存在的归并关系中是否
			*存在相同的料号也设为当前使用，把前面的归并设置为”不使用“
			**********************************************************************************/
			if(bcsInnerMergeData.getIsUsing() && oldMergerMap.containsKey(ptNo)){	
//				BcsInnerMerge oldBcsInnerMerge = bcsInnerMergeDao.findBcsInnerMergeByPtNo(ptNo);
//				if(oldBcsInnerMerge!=null){
//					oldBcsInnerMerge.setIsUsing(Boolean.FALSE);
//					bcsInnerMergeDao.saveOrUpdateNoCache(oldBcsInnerMerge);
//				}
				BcsInnerMerge oldBcsInnerMerge = oldMergerMap.get(ptNo);
				oldBcsInnerMerge.setIsUsing(Boolean.FALSE);
//				bcsInnerMergeDao.saveOrUpdateNoCache(oldBcsInnerMerge);
			}			
//			bcsInnerMergeDao.saveOrUpdateNoCache(bcsInnerMergeData);

			
			//回写物料 的是否被引用字段
			m.setIsUseInBcsInnerMerge(Boolean.TRUE);
//			bcsInnerMergeDao.saveOrUpdateNoCache(m);
			materielLs.add(m);
			
			mergeDataLs.add(bcsInnerMergeData);
			
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
		bcsInnerMergeDao.batchSaveOrUpdate(mergeDataLs);
		bcsInnerMergeDao.batchSaveOrUpdate(materielLs);
		bcsInnerMergeDao.batchSaveOrUpdate(new ArrayList(oldMergerMap.values()));
	}
	

	/**
	 * 通过code查询海光商品编码
	 * 
	 * @param code商品编码代码
	 * @return 商品编码
	 */
	public Complex findComplexByCode(String sValue) {
		return hsCodeDao.findComplexByCode(sValue);
	}
	/*
	 * public Map getCalNameUnit(Company company) { if
	 * (calNameUnitMap.get(company.getId()) == null) { System.out.println("begin
	 * init CalUnit "); Map map = new HashMap(); List list =
	 * materialManageDao.findCalUnit(company); for (int i = 0; i < list.size();
	 * i++) { CalUnit calUnit = (CalUnit) list.get(i); if (calUnit.getName() ==
	 * null) { continue; } map.put(calUnit.getName().trim(), calUnit); }
	 * calNameUnitMap.put(company.getId(), map); System.out.println("end init
	 * CalUnit "); } return calNameUnitMap.get(company.getId()); }
	 */

}