/*
 * Created on 2004-7-13
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bls.dao.BlsInnerMergeDao;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsInnerMergeFileData;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.dao.ParameterCodeDao;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoi;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class BlsImportFromOther {

	private MaterialManageDao materialManageDao;

	private BlsInnerMergeDao blsInnerMergeDao;

	private HsCodeDao hsCodeDao;

	private ParameterCodeDao paramerterCodeDao;

	private Map calUnitMap = null;

	public BlsInnerMergeDao getBlsInnerMergeDao() {
		return blsInnerMergeDao;
	}

	public void setBlsInnerMergeDao(BlsInnerMergeDao blsInnerMergeDao) {
		this.blsInnerMergeDao = blsInnerMergeDao;
	}

	public ParameterCodeDao getParamerterCodeDao() {
		return paramerterCodeDao;
	}

	public void setParamerterCodeDao(ParameterCodeDao paramerterCodeDao) {
		this.paramerterCodeDao = paramerterCodeDao;
	}

	public HsCodeDao getHsCodeDao() {
		return hsCodeDao;
	}

	public void setHsCodeDao(HsCodeDao hsCodeDao) {
		this.hsCodeDao = hsCodeDao;
	}

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	// /**
	// * 导入物了主挡
	// *
	// * @param materielTypeList
	// */
	// public void importDataFromMaterial(List materielTypeList, boolean flag) {
	// for (int i = 0; i < materielTypeList.size(); i++) {
	// if (materielTypeList.get(i) != null) {
	// this.importInnerMergeDataFromMateriel(materielTypeList.get(i)
	// .toString(), flag);
	// }
	// }
	// }

	private Materiel getMateriel(Map<String, Materiel> existMaterielMap,
			BlsInnerMergeFileData data, ScmCoi scmCoi,
			List<String> existEMaterielList) {
		String ptNo = data.getBeforeMaterielCode();
		if (ptNo.trim().equals("")) {
			return null;
		}
		// 检查工厂物料
		if (!existEMaterielList.contains(ptNo)) {
			CalUnit calUnit = this.getCalUnit(data.getBeforeEnterpriseUnit());
			Unit unit = this.getUnit(data.getBeforeUnit());
			EnterpriseMaterial emateriel = new EnterpriseMaterial();
			emateriel.setScmCoi(scmCoi);
			emateriel.setPtNo(data.getBeforeMaterielCode());
			emateriel.setComplex(getComplex(data.getBeforeTenComplexCode()));
			emateriel.setFactoryName(data.getBeforeMaterielName());
			emateriel.setFactorySpec(data.getBeforeMaterielSpec());
			emateriel.setCompany(CommonUtils.getCompany());
			if (data.getUnitWeight() != null
					&& !"".equals(data.getUnitWeight().trim())) {
				try {
					emateriel.setPtNetWeight(Double.parseDouble(data
							.getUnitWeight().trim()));
				} catch (Exception ex) {
				}
			}
			if (data.getUnitConvert() != null
					&& !"".equals(data.getUnitConvert().trim())) {
				try {
					emateriel.setUnitConvert(Double.parseDouble(data
							.getUnitConvert().trim()));
				} catch (Exception ex) {
				}
			}
			if (unit != null) {
				emateriel.setPtUnit(unit);
			}
			if (calUnit != null) {
				emateriel.setCalUnit(calUnit);
			}
			materialManageDao.saveEMateriel(emateriel);
			existEMaterielList.add(ptNo);
		}

		if (existMaterielMap.get(ptNo) != null) {
			Materiel materiel = existMaterielMap.get(ptNo);
			materiel.setIsNewMateriel(new Boolean(false));
			// blsInnerMergeDao.saveOrUpdate(materiel);
			return materiel;
		} else {
			CalUnit calUnit = this.getCalUnit(data.getBeforeEnterpriseUnit());
			Unit unit = this.getUnit(data.getBeforeUnit());
			Materiel materiel = new Materiel();
			materiel.setScmCoi(scmCoi);
			materiel.setPtNo(data.getBeforeMaterielCode());
			materiel.setFactoryName(data.getBeforeMaterielName());
			materiel.setFactorySpec(data.getBeforeMaterielSpec());
			materiel.setComplex(getComplex(data.getBeforeTenComplexCode()));
			materiel.setCompany(CommonUtils.getCompany());
			materiel.setIsNewMateriel(new Boolean(false));
			if (data.getUnitWeight() != null
					&& !"".equals(data.getUnitWeight().trim())) {
				try {
					materiel.setPtNetWeight(Double.parseDouble(data
							.getUnitWeight().trim()));
				} catch (Exception ex) {
				}
			}
			if (data.getUnitConvert() != null
					&& !"".equals(data.getUnitConvert().trim())) {
				try {
					materiel.setUnitConvert(Double.parseDouble(data
							.getUnitConvert().trim()));
				} catch (Exception ex) {
				}
			}
			if (unit != null) {
				materiel.setPtUnit(unit);
			}
			if (calUnit != null) {
				materiel.setCalUnit(calUnit);
			}
			// blsInnerMergeDao.saveOrUpdate(materiel);
			existMaterielMap.put(ptNo, materiel);
			return materiel;
		}
	}

	// /**
	// * 自动从物料主档中导入资料
	// *
	// * @param imrType
	// * 物料类型
	// * @return 返回空的ArrayList()
	// */
	// private void importInnerMergeDataFromMateriel(String imrType, boolean
	// flag) {
	// List list = this.dzscInnerMergeDao
	// .findMaterielForDzscInnerMerge(imrType);
	// List lsExistedPtNo = this.dzscInnerMergeDao
	// .findExistedMaterialPtNoInInnerMerge(imrType);
	// for (int i = list.size() - 1; i >= 0; i--) {
	// Materiel materiel = (Materiel) list.get(i);
	// if (lsExistedPtNo.contains(materiel.getPtNo())) {
	// list.remove(i);
	// }
	// }
	// this.dzscInnerMergeLogic.addInnerMergeData(list, flag);
	// }

	private Map<String, Integer> getMaterielCount(List list) {
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			BlsInnerMergeFileData fileData = (BlsInnerMergeFileData) list
					.get(i);
			String key = ((fileData.getBeforeMaterielCode() == null ? ""
					: fileData.getBeforeMaterielCode().trim())
					+ (fileData.getAfterTenMemoNo() == null ? "" : fileData
							.getAfterTenMemoNo().trim())
					+ (fileData.getAfterTenComplexCode() == null ? ""
							: fileData.getAfterTenComplexCode().trim())
					+ (fileData.getAfterComplexlName() == null ? "" : fileData
							.getAfterComplexlName().trim())
					+ (fileData.getAfterComplexSpec() == null ? "" : fileData
							.getAfterComplexSpec().trim()) + (fileData
					.getAfterUnit() == null ? "" : fileData.getAfterUnit()
					.trim()));
//			System.out.println("3-----------------:"+key);
			if (hm.get(key) == null) {
				hm.put(key, 1);
			} else {
				hm.put(key, Integer.valueOf(hm.get(key).toString()) + 1);
			}
		}
		return hm;
	}

	private Map<String, String> initComplexCodeNameMapBySeqNum() {
		Map<String, String> hm = new HashMap<String, String>();
		List list = this.blsInnerMergeDao.findBlsTenInnerMergeByInnerMerge();
		for (int i = 0; i < list.size(); i++) {
			BlsTenInnerMerge data = (BlsTenInnerMerge) list.get(i);
			String key = (data.getSeqNum() == null ? "" : data.getSeqNum()
					.toString());
			String complexCodeName = ((data.getSeqNum() == null ? "" : data
					.getSeqNum().toString())
					+ (data.getComplex() == null ? "" : data.getComplex()
							.getCode().trim())
					+ (data.getName() == null ? "" : data.getName().trim())
					+ (data.getSpec() == null ? "" : data.getSpec().trim()) + (data
					.getComUnit() == null ? "" : data.getComUnit().getName()
					.trim()));
			hm.put(key, complexCodeName);
		}
		return hm;
	}

	public List checkFileData(List list, Hashtable ht, String taskId) {
		ArrayList<BlsInnerMergeFileData> ls = new ArrayList<BlsInnerMergeFileData>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		calUnitMap = null;
		unitMap = null;
		complexMap = null;
		cMap = null;
		getCustomsComplex();
		BlsInnerMergeFileData fileData = null;
		int[] invalidationColNo;
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在检验文件资料");
		}
		Map<String, Integer> hmMaterielCount = getMaterielCount(list);
		Map<String, String> hmComplexCodeName = initComplexCodeNameMapBySeqNum();
		List exsitPtNoList = blsInnerMergeDao.findInnerMergeDataPtNo();
//		for(int i=0;i<exsitPtNoList.size();i++){
//			System.out.println("1-----------------:"+exsitPtNoList.get(i));
//		}
		
		for (int i = 0; i < list.size(); i++) {
			temp.clear();
			fileData = (BlsInnerMergeFileData) list.get(i);
			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_CODE))
							.toString()) > -1) {
				String key = ((fileData.getBeforeMaterielCode() == null ? ""
						: fileData.getBeforeMaterielCode().trim())
						+ (fileData.getAfterTenMemoNo() == null ? "" : fileData
								.getAfterTenMemoNo().trim())
						+ (fileData.getAfterTenComplexCode() == null ? ""
								: fileData.getAfterTenComplexCode().trim())
						+ (fileData.getAfterComplexlName() == null ? ""
								: fileData.getAfterComplexlName().trim())
						+ (fileData.getAfterComplexSpec() == null ? ""
								: fileData.getAfterComplexSpec().trim()) + (fileData
						.getAfterUnit() == null ? "" : fileData.getAfterUnit()
						.trim()));
//				System.out.println("2-----------------:"+key);
				String showInfo = "归并前料号：" + fileData.getBeforeMaterielCode()
						+ "\n" + "归并后归并序号：" + fileData.getAfterTenMemoNo()
						+ "\n" + "归并后商品编码：" + fileData.getAfterTenComplexCode()
						+ "\n" + "归并后商品名称：" + fileData.getAfterComplexlName()
						+ "\n" + "归并后商品规格：" + fileData.getAfterComplexSpec()
						+ "\n" + "归并后常用单位：" + fileData.getAfterUnit() + "\n";
				String seqNum = (fileData.getAfterTenMemoNo() == null ? ""
						: fileData.getAfterTenMemoNo().trim());
				String complexCodeName = ((fileData.getAfterTenMemoNo() == null ? ""
						: fileData.getAfterTenMemoNo().trim())
						+ (fileData.getAfterTenComplexCode() == null ? ""
								: fileData.getAfterTenComplexCode().trim())
						+ (fileData.getAfterComplexlName() == null ? ""
								: fileData.getAfterComplexlName().trim())
						+ (fileData.getAfterComplexSpec() == null ? ""
								: fileData.getAfterComplexSpec().trim()) + (fileData
						.getAfterUnit() == null ? "" : fileData.getAfterUnit()
						.trim()));
				if (exsitPtNoList.contains(key)) {
					temp
							.add(Integer
									.valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ showInfo + "在归并中已存在;");
				} else if (hmMaterielCount.get(key) != null
						&& hmMaterielCount.get(key) > 1) {
					temp
							.add(Integer
									.valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ showInfo + "重复;");
				} else {
					if (hmComplexCodeName.get(seqNum) != null
							&& !complexCodeName.equals(hmComplexCodeName
									.get(seqNum))) {
						temp
								.add(Integer
										.valueOf(BlsInnerMergeFileData.BEFORE_MATERIEL_CODE));
						fileData.setErrorReason(fileData.getErrorReason()
								+ "归并序号" + seqNum + "已存在所对应的商品编码名称规格单位"
								+ " 和 将要导入的文件中 归并序号" + seqNum
								+ "所对应的商品编码名称规格单位不一致");
					}
				}
			}
			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(BlsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE))
							.toString()) > -1) {
				if (getComplex(fileData.getBeforeTenComplexCode()) == null) {
					temp
							.add(Integer
									.valueOf(BlsInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并前海关商品编码库不存在;");
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(BlsInnerMergeFileData.BEFORE_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getBeforeUnit()) == null) {
					temp
							.add(Integer
									.valueOf(BlsInnerMergeFileData.BEFORE_UNIT));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并前申报单位不存在;");
				}
			}
			// if (Integer.parseInt(ht.get(
			// Integer.valueOf(DzscInnerMergeFileData.BEFORE_LEGAL_UNIT))
			// .toString()) > -1) {
			// if (getUnit(fileData.getBeforeLegalUnit()) == null) {
			// temp.add(Integer
			// .valueOf(DzscInnerMergeFileData.BEFORE_LEGAL_UNIT));
			// fileData.setErrorReason(fileData.getErrorReason()
			// + "归并前法定单位不存在;");
			// }
			// }
			/*
			 * if (Integer .parseInt(ht .get( Integer
			 * .valueOf(DzscInnerMergeFileData.BEFORE_ENTERPRISE_UNIT))
			 * .toString()) > -1) { if
			 * (getCalUnit(fileData.getBeforeEnterpriseUnit()) == null) { temp
			 * .add(Integer
			 * .valueOf(DzscInnerMergeFileData.BEFORE_ENTERPRISE_UNIT));
			 * fileData.setErrorReason(fileData.getErrorReason() +
			 * "归并前企业单位不存在;"); } }
			 */
			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(BlsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE))
							.toString()) > -1) {
				if (getComplex(fileData.getAfterTenComplexCode()) == null) {
					temp
							.add(Integer
									.valueOf(BlsInnerMergeFileData.AFTER_TEN_COMPLEX_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并后海关商品编码库不存在;");
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(BlsInnerMergeFileData.AFTER_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getAfterUnit()) == null) {
					temp.add(Integer.valueOf(BlsInnerMergeFileData.AFTER_UNIT));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并后单位不存在;");
				}
			}
			// if (Integer.parseInt(ht.get(
			// Integer.valueOf(DzscInnerMergeFileData.AFTER_LEGAL_UNIT))
			// .toString()) > -1) {
			// if (getUnit(fileData.getAfterLegalUnit()) == null) {
			// temp.add(Integer
			// .valueOf(DzscInnerMergeFileData.AFTER_LEGAL_UNIT));
			// fileData.setErrorReason(fileData.getErrorReason()
			// + "归并后法定单位不存在;");
			// }
			// }
			// if (Integer.parseInt(ht.get(
			// Integer.valueOf(DzscInnerMergeFileData.AFTER_MEMO_UNIT))
			// .toString()) > -1) {
			// if (getUnit(fileData.getAfterMemoUnit()) == null) {
			// temp.add(Integer
			// .valueOf(DzscInnerMergeFileData.AFTER_MEMO_UNIT));
			// fileData.setErrorReason(fileData.getErrorReason()
			// + "归并后备案单位不存在;");
			// }
			// }

			if (temp.size() > 0) {
				invalidationColNo = new int[temp.size()];
				for (int j = 0; j < temp.size(); j++) {
					invalidationColNo[j] = Integer.parseInt(temp.get(j)
							.toString());
				}
				fileData.setInvalidationColNo(invalidationColNo);
				ls.add(fileData);
			}
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
		return ls;
	}

	/**
	 * 导入文件来自文件
	 * 
	 * @param list
	 */
	public void importDataFromTxtFile(List list, String taskId) {
		BlsInnerMergeFileData fileData = null;
		BlsInnerMerge blsInnerMergeData = null;
		Materiel materiel = null;
		ScmCoi scmCoi = null;
		Map<Integer, BlsTenInnerMerge> mapTen = new HashMap<Integer, BlsTenInnerMerge>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在导入文件资料");
		}
		// String materielType = "";
		if (list.size() > 0) {
			// scmCoi = materialManageDao
			// .findScmCoiByType(((BlsInnerMergeFileData) list.get(0))
			// .getMaterielType());
			List lsScmCoi = materialManageDao.findScmCois();
			if (!lsScmCoi.isEmpty()) {
				scmCoi = (ScmCoi) lsScmCoi.get(0);
			}
			// materielType = ((BlsInnerMergeFileData) list.get(0))
			// .getMaterielType();
		} else {
			throw new RuntimeException("没有新增的归并可导入");
		}
		// if (scmCoi == null) {
		// String typeName = "";
		// if (MaterielType.MATERIEL.equals(materielType)) {
		// typeName = "料件";
		// } else if (MaterielType.FINISHED_PRODUCT.equals(materielType)) {
		// typeName = "成品";
		// } else if (MaterielType.SEMI_FINISHED_PRODUCT.equals(materielType)) {
		// typeName = "半成品";
		// }
		// throw new RuntimeException(typeName + " 没有设定企业物料类别 ");
		// }
		//
		// 获得已存在的内部归并数据 key = ptNo
		//
		Map<String, BlsInnerMerge> existInnerMergeMap = new HashMap<String, BlsInnerMerge>();
		List<BlsInnerMerge> existInnerMergeDataList = blsInnerMergeDao
				.findBlsInnerMerge();// scmCoi.getCoiProperty()
		for (int i = 0, n = existInnerMergeDataList.size(); i < n; i++) {
			BlsInnerMerge temp = existInnerMergeDataList.get(i);
			if (temp.getMateriel() == null) {
				continue;
			}
			String key = temp.getMateriel().getPtNo().trim();
			existInnerMergeMap.put(key, temp);
		}
		//
		// 获得已存在的物料主档数据
		//
		Map<String, Materiel> existMaterielMap = new HashMap<String, Materiel>();
		List<Materiel> existMaterielList = materialManageDao.findMateriel();
		for (int i = 0, n = existMaterielList.size(); i < n; i++) {
			Materiel temp = existMaterielList.get(i);
			String key = temp.getPtNo();
			if (key == null || "".equals(key)) {
				continue;
			}
			existMaterielMap.put(key, temp);
		}

		/**
		 * 获取已经存在的工厂原始物料
		 */
		List<String> existEMaterielList = materialManageDao.findEMaterielPtNo();
		//
		// 开始转换
		//
		for (int i = 0, n = list.size(); i < n; i++) {
			fileData = (BlsInnerMergeFileData) list.get(i);
			//
			// key = ptNo + 归并后的海关商品编号
			//
			// String existInnerMergeKey =
			// fileData.getBeforeMaterielCode().trim()
			// + fileData.getAfterTenComplexCode().trim();
			String existInnerMergeKey = fileData.getBeforeMaterielCode().trim();

			if (existInnerMergeMap.get(existInnerMergeKey) != null) {
				continue;
			}
			//
			// 获得物料主档
			//
			materiel = getMateriel(existMaterielMap, fileData, scmCoi,
					existEMaterielList);
			// materiel.setIsUseInBlsInnerMerge(true);
			this.blsInnerMergeDao.saveOrUpdate(materiel);
			//
			// 新增内部归并数据
			//
			blsInnerMergeData = new BlsInnerMerge();
			// blsInnerMergeData
			// .setMaterielType(fileData.getMaterielType().trim());
			blsInnerMergeData.setCompany(CommonUtils.getCompany());
			blsInnerMergeData.setMateriel(materiel);
			Integer tenSeqNum = null;
			if (!fileData.getAfterTenMemoNo().trim().equals("")) {
				tenSeqNum = Integer.valueOf(fileData.getAfterTenMemoNo());
			}
			if (tenSeqNum != null) {
				BlsTenInnerMerge tenInnerMerge = mapTen.get(tenSeqNum);
				if (tenInnerMerge == null) {
					tenInnerMerge = new BlsTenInnerMerge();
					tenInnerMerge.setSeqNum(tenSeqNum);
					// tenInnerMerge.setScmCoi(fileData.getMaterielType().trim());
					tenInnerMerge.setComplex(getComplex(fileData
							.getAfterTenComplexCode()));
					tenInnerMerge.setName(fileData.getAfterComplexlName());
					tenInnerMerge.setSpec(fileData.getAfterComplexSpec());
					tenInnerMerge.setComUnit(getUnit(fileData.getAfterUnit()));
					tenInnerMerge.setCompany(CommonUtils.getCompany());
					// this.blsInnerMergeDao.saveBlsTenInnerMerge(tenInnerMerge);
					this.blsInnerMergeDao.saveOrUpdateNoCache(tenInnerMerge);
					mapTen.put(tenSeqNum, tenInnerMerge);
				}
				blsInnerMergeData.setBlsTenInnerMerge(tenInnerMerge);
				if (fileData.getUnitConvert() != null
						&& !"".equals(fileData.getUnitConvert().trim())) {
					try {
						blsInnerMergeData.setUnitConvert(Double
								.parseDouble(fileData.getUnitConvert().trim()));
					} catch (Exception ex) {
					}
				}
			}
			// blsInnerMergeDao.saveBlsInnerMerge(blsInnerMergeData);
			blsInnerMergeDao.saveOrUpdateNoCache(blsInnerMergeData);
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
		calUnitMap = null;
		unitMap = null;
		complexMap = null;
		cMap = null;
	}

	// ///////////////////////////////////////////////////////////
	// hash 类
	// ///////////////////////////////////////////////////////////
	private Map<String, Complex> complexMap = null;

	// 获取报关计量单位
	private Unit getUnit(String name) {
		if (unitMap == null) {
			System.out.println("begin init unitMap");
			unitMap = new HashMap<String, Object>();
			List list = materialManageDao.findUnit();
			for (int i = 0, n = list.size(); i < n; i++) {
				Unit unit = (Unit) list.get(i);
				unitMap.put(unit.getName().trim(), unit);
			}
			System.out.println("end init unitMap");
		}
		if (name == null) {
			return null;
		}
		return (Unit) unitMap.get(name.trim());
	}

	// 初始化并获取工厂计量单位
	private CalUnit getCalUnit(String name) {
		if (calUnitMap == null) {
			calUnitMap = new HashMap<String, Object>();
			List list = materialManageDao.findCalUnit((Company) CommonUtils
					.getCompany());
			for (int i = 0, n = list.size(); i < n; i++) {
				CalUnit unit = (CalUnit) list.get(i);
				if (unit.getName() == null) {
					continue;
				}
				calUnitMap.put(unit.getName().trim(), unit);
			}
		}
		if (name == null) {
			return null;
		}
		if (calUnitMap.get(name.trim()) == null) { // 如果工厂单位不存在，先使用名称代替代码，导入完毕后进行修改工厂计量单位代码
			CalUnit obj = new CalUnit();
			obj.setCode(name.trim());
			obj.setName(name.trim());
			obj.setCompany(CommonUtils.getCompany());
			materialManageDao.saveUnit(obj);
			calUnitMap.put(name.trim(), obj);
			return obj;
		} else {
			return (CalUnit) calUnitMap.get(name.trim());
		}
	}

	private Complex getComplex(String code) {
		if (complexMap == null) {
			complexMap = new HashMap();
			List list = hsCodeDao.findComplex();
			for (int i = 0, n = list.size(); i < n; i++) {
				Complex complex = (Complex) list.get(i);
				complexMap.put(complex.getCode().trim(), complex);
			}
		}
		if (code == null) {
			return null;
		}
		if (complexMap.get(code.trim()) == null) {
			CustomsComplex c = (CustomsComplex) cMap.get(code.trim());
			if (c != null) {
				Complex complex = new Complex();
				complex.setId(c.getCode());
				complex.setCode(c.getCode());
				complex.setName(c.getName());
				complex.setIsOut(c.getIsOut());
				complex.setNote(c.getNote());
				complex.setFirstUnit(c.getFirstUnit());
				complex.setSecondUnit(c.getSecondUnit());
				complex.setLowrate(c.getLowrate());
				complex.setHighrate(c.getHighrate());
				hsCodeDao.saveComplex(complex);
				complexMap.put(c.getCode(), complex);
				return complex;
			} else {
				return null;
			}
		} else {
			return complexMap.get(code.trim());
		}
	}

	/*
	 * 获得计量单位 以名称为key
	 */
	private Map unitMap = null;

	private Map cMap = null;

	private void getCustomsComplex() {
		if (cMap == null) {
			cMap = new HashMap();
			List list = materialManageDao.findCustomsComplex();
			for (int i = 0; i < list.size(); i++) {
				CustomsComplex c = (CustomsComplex) list.get(i);
				cMap.put(c.getCode(), c);
			}
		}
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