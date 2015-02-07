/*
 * Created on 2004-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

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
import com.bestway.dzsc.innermerge.dao.DzscInnerMergeDao;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeFileData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.materialapply.dao.MaterialApplyDao;
import com.bestway.dzsc.materialapply.entity.MaterialApply;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DzscImportFromOther {

	private MaterialManageDao materialManageDao;

	private MaterialApplyDao materialApplyDao;

	private DzscInnerMergeDao dzscInnerMergeDao;

	private DzscInnerMergeLogic dzscInnerMergeLogic;

	private HsCodeDao hsCodeDao;

	private ParameterCodeDao paramerterCodeDao;

	private Map calUnitMap = null;

	// private Materiel materiel;

	public void setDzscInnerMergeLogic(DzscInnerMergeLogic dzscInnerMergeLogic) {
		this.dzscInnerMergeLogic = dzscInnerMergeLogic;
	}

	public DzscInnerMergeDao getDzscInnerMergeDao() {
		return dzscInnerMergeDao;
	}

	public void setDzscInnerMergeDao(DzscInnerMergeDao dzscInnerMergeDao) {
		this.dzscInnerMergeDao = dzscInnerMergeDao;
	}

	public DzscInnerMergeLogic getDzscInnerMergeLogic() {
		return dzscInnerMergeLogic;
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

	public MaterialApplyDao getMaterialApplyDao() {
		return materialApplyDao;
	}

	public void setMaterialApplyDao(MaterialApplyDao materialApplyDao) {
		this.materialApplyDao = materialApplyDao;
	}

	/**
	 * 导入物了主挡
	 * 
	 * @param materielTypeList
	 */
	public void importDataFromMaterial(List materielTypeList, boolean flag) {
		for (int i = 0; i < materielTypeList.size(); i++) {
			if (materielTypeList.get(i) != null) {
				this.importInnerMergeDataFromMateriel(materielTypeList.get(i)
						.toString(), flag);
			}
		}
	}

	private Materiel getMateriel(Map<String, Materiel> existMaterielMap,
			DzscInnerMergeFileData data, ScmCoi scmCoi,
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
				emateriel.setPtNetWeight(Double.parseDouble(data
						.getUnitWeight().trim()));
			}
			if (unit != null) {
				emateriel.setPtUnit(unit);
			}
			if (calUnit != null) {
				emateriel.setCalUnit(calUnit);
			}
			// materialManageDao.saveEMateriel(emateriel);
			materialManageDao.saveOrUpdateNoCache(emateriel);
			existEMaterielList.add(ptNo);
		}

		if (existMaterielMap.get(ptNo) != null) {
			Materiel materiel = existMaterielMap.get(ptNo);
			materiel.setIsNewMateriel(new Boolean(false));
			dzscInnerMergeDao.saveOrUpdate(materiel);
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
				materiel.setPtNetWeight(Double.parseDouble(data.getUnitWeight()
						.trim()));
			}
			if (unit != null) {
				materiel.setPtUnit(unit);
			}
			if (calUnit != null) {
				materiel.setCalUnit(calUnit);
			}
			// dzscInnerMergeDao.saveOrUpdate(materiel);
			dzscInnerMergeDao.saveOrUpdateNoCache(materiel);
			existMaterielMap.put(ptNo, materiel);
			return materiel;
		}
	}

	/**
	 * 自动从物料主档中导入资料
	 * 
	 * @param imrType
	 *            物料类型
	 * @return 返回空的ArrayList()
	 */
	private void importInnerMergeDataFromMateriel(String imrType, boolean flag) {
		List list = this.dzscInnerMergeDao
				.findMaterielForDzscInnerMerge(imrType);
		List lsExistedPtNo = this.dzscInnerMergeDao
				.findExistedMaterialPtNoInInnerMerge(imrType);
		for (int i = list.size() - 1; i >= 0; i--) {
			Materiel materiel = (Materiel) list.get(i);
			if (lsExistedPtNo.contains(materiel.getPtNo())) {
				list.remove(i);
			}
		}
		this.dzscInnerMergeLogic.addInnerMergeData(list, flag);
	}

	private Map<String, Integer> getMaterielCount(List list) {
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeFileData fileData = (DzscInnerMergeFileData) list
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

	public List checkFileData(List list, Hashtable ht, String taskId) {
		ArrayList<DzscInnerMergeFileData> ls = new ArrayList<DzscInnerMergeFileData>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		calUnitMap = null;
		unitMap = null;
		complexMap = null;
		cMap = null;
		getCustomsComplex();
		DzscInnerMergeFileData fileData = null;
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
		List exsitPtNoList = dzscInnerMergeDao.findInnerMergeDataPtNo();
		for (int i = 0; i < list.size(); i++) {
			temp.clear();
			fileData = (DzscInnerMergeFileData) list.get(i);
			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(DzscInnerMergeFileData.BEFORE_MATERIEL_CODE))
							.toString()) > -1) {
				if (exsitPtNoList.contains(fileData.getBeforeMaterielCode()
						.trim())) {
					temp
							.add(Integer
									.valueOf(DzscInnerMergeFileData.BEFORE_MATERIEL_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并前料号在归并中已存在;");
				} else if (hmMaterielCount
						.get(fileData.getBeforeMaterielCode()) != null
						&& hmMaterielCount
								.get(fileData.getBeforeMaterielCode()) > 1) {
					temp
							.add(Integer
									.valueOf(DzscInnerMergeFileData.BEFORE_MATERIEL_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并前料号重复;");
				}
			}
			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(DzscInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE))
							.toString()) > -1) {
				if (getComplex(fileData.getBeforeTenComplexCode()) == null) {
					temp
							.add(Integer
									.valueOf(DzscInnerMergeFileData.BEFORE_TEN_COMPLEX_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并前海关商品编码库不存在;");
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(DzscInnerMergeFileData.BEFORE_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getBeforeUnit()) == null) {
					temp.add(Integer
							.valueOf(DzscInnerMergeFileData.BEFORE_UNIT));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并前申报单位不存在;");
				}
			}
			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(DzscInnerMergeFileData.AFTER_TEN_COMPLEX_CODE))
							.toString()) > -1) {
				if (getComplex(fileData.getAfterTenComplexCode()) == null) {
					temp
							.add(Integer
									.valueOf(DzscInnerMergeFileData.AFTER_TEN_COMPLEX_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并后海关商品编码库不存在;");
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(DzscInnerMergeFileData.AFTER_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getAfterUnit()) == null) {
					temp
							.add(Integer
									.valueOf(DzscInnerMergeFileData.AFTER_UNIT));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "归并后单位不存在;");
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(DzscInnerMergeFileData.FOUR_COMPLEX_CODE))
					.toString()) > -1) {
				if (!NumberUtils.isNumber(fileData.getFourComplexCode())) {
					temp.add(Integer
							.valueOf(DzscInnerMergeFileData.FOUR_COMPLEX_CODE));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "4码编码不是有效数字;");
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(DzscInnerMergeFileData.FOUR_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getFourUnit()) == null) {
					temp.add(Integer.valueOf(DzscInnerMergeFileData.FOUR_UNIT));
					fileData.setErrorReason(fileData.getErrorReason()
							+ "4码单位不存在;");
				}
			}
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
		DzscInnerMergeFileData fileData = null;
		DzscInnerMergeData dzscInnerMergeData = null;
		Materiel materiel = null;
		ScmCoi scmCoi = null;
		Map<Integer, DzscTenInnerMerge> mapTen = new HashMap<Integer, DzscTenInnerMerge>();
		Map<Integer, DzscFourInnerMerge> mapFour = new HashMap<Integer, DzscFourInnerMerge>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在导入文件资料");
		}
		String materielType = "";
		if (list.size() > 0) {
			scmCoi = materialManageDao
					.findScmCoiByType(((DzscInnerMergeFileData) list.get(0))
							.getMaterielType());
			materielType = ((DzscInnerMergeFileData) list.get(0))
					.getMaterielType();
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
		//
		// 获得已存在的内部归并数据 key = ptNo
		//
		Map<String, DzscInnerMergeData> existInnerMergeMap = new HashMap<String, DzscInnerMergeData>();
		List<DzscInnerMergeData> existInnerMergeDataList = dzscInnerMergeDao
				.findInnerMergeDataByType(scmCoi.getCoiProperty());
		for (int i = 0, n = existInnerMergeDataList.size(); i < n; i++) {
			DzscInnerMergeData temp = existInnerMergeDataList.get(i);
			if (temp.getMateriel() == null) {
				continue;
			}
			String key = temp.getMateriel().getPtNo().trim();
			existInnerMergeMap.put(key, temp);
			if (temp.getDzscTenInnerMerge() != null) {
				if (mapTen.get(temp.getDzscTenInnerMerge().getTenSeqNum()) == null) {
					mapTen.put(temp.getDzscTenInnerMerge().getTenSeqNum(), temp
							.getDzscTenInnerMerge());
				}
				if (temp.getDzscTenInnerMerge().getDzscFourInnerMerge() != null) {
					if (mapFour.get(temp.getDzscTenInnerMerge()
							.getDzscFourInnerMerge().getFourSeqNum()) == null) {
						mapFour
								.put(temp.getDzscTenInnerMerge()
										.getDzscFourInnerMerge()
										.getFourSeqNum(), temp
										.getDzscTenInnerMerge()
										.getDzscFourInnerMerge());
					}
				}
			}
		}
		//
		// 获得已存在的物料主档数据
		//
		Map<String, Materiel> existMaterielMap = new HashMap<String, Materiel>();
		List<Materiel> existMaterielList = materialManageDao
				.findMaterielByMaterielType(scmCoi.getCoiProperty());
		for (int i = 0, n = existMaterielList.size(); i < n; i++) {
			Materiel temp = existMaterielList.get(i);
			String key = temp.getPtNo();
			if (key == null || "".equals(key)) {
				continue;
			}
			existMaterielMap.put(key, temp);
		}
		//
		// 获得已存在的物料主档备案数据
		//
		List lsExistApplyPtNo = this.materialApplyDao
				.findMaterielApplyPtNo(scmCoi);

		/**
		 * 获取已经存在的工厂原始物料
		 */
		List<String> existEMaterielList = materialManageDao
				.findEMaterielPtNoByMaterielType(scmCoi.getCoiProperty());
		//
		// 开始转换
		//
		for (int i = 0, n = list.size(); i < n; i++) {
			fileData = (DzscInnerMergeFileData) list.get(i);
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
			//
			// 如果在物料备案中不存在的话，就插入物料备案
			//
			if (!lsExistApplyPtNo.contains(materiel.getPtNo())) {
				MaterialApply materialApply = new MaterialApply();
				materialApply.setNo(this.materialApplyDao
						.findMaterielApplyMaxNo(scmCoi.getCode()) + 1);
				materialApply.setMateriel(materiel);
				materialApply.setModifyMark(ModifyMarkState.ADDED);
				materialApply.setStateMark(DzscState.ORIGINAL);
				// this.materialApplyDao.saveMaterialApply(materialApply);
				this.materialApplyDao.saveOrUpdateNoCache(materialApply);
				lsExistApplyPtNo.add(materiel.getPtNo());
			}
			//
			// 新增内部归并数据
			//
			dzscInnerMergeData = new DzscInnerMergeData();
			dzscInnerMergeData.setStateMark(DzscState.ORIGINAL);
			dzscInnerMergeData.setImrType(fileData.getMaterielType().trim());
			dzscInnerMergeData.setCompany(CommonUtils.getCompany());
			dzscInnerMergeData.setMateriel(materiel);
			dzscInnerMergeData.setBeforeModifyMark(ModifyMarkState.ADDED);
			Integer tenSeqNum = null;
			if (!fileData.getAfterTenMemoNo().trim().equals("")) {
				tenSeqNum = Integer.valueOf(fileData.getAfterTenMemoNo());
			}
			if (tenSeqNum != null) {
				DzscTenInnerMerge tenInnerMerge = mapTen.get(tenSeqNum);
				if (tenInnerMerge == null) {
					tenInnerMerge = new DzscTenInnerMerge();
					tenInnerMerge.setTenSeqNum(tenSeqNum);
					tenInnerMerge.setTenComplex(getComplex(fileData
							.getAfterTenComplexCode()));
					tenInnerMerge.setTenPtName(fileData.getAfterComplexlName());
					tenInnerMerge.setTenPtSpec(fileData.getAfterComplexSpec());
					tenInnerMerge.setTenUnit(getUnit(fileData.getAfterUnit()));
					if (fileData.getLegalUnitGene() != null
							&& !"".equals(fileData.getLegalUnitGene().trim())) {
						tenInnerMerge
								.setLegalUnitGene(Double.parseDouble(fileData
										.getLegalUnitGene().trim()));
					}
					if (fileData.getLegalUnit2Gene() != null
							&& !"".equals(fileData.getLegalUnit2Gene().trim())) {
						tenInnerMerge.setLegalUnit2Gene(Double
								.parseDouble(fileData.getLegalUnit2Gene()
										.trim()));
					}
					if (fileData.getWeigthUnitGene() != null
							&& !"".equals(fileData.getWeigthUnitGene().trim())) {
						tenInnerMerge.setWeigthUnitGene(Double
								.parseDouble(fileData.getWeigthUnitGene()
										.trim()));
					}
					tenInnerMerge.setTenModifyMark(ModifyMarkState.ADDED);
					Integer fourSeqNum = null;
					if (!fileData.getFourNo().trim().equals("")) {
						fourSeqNum = Integer.valueOf(fileData.getFourNo());
					}
					if (fourSeqNum != null) {
						DzscFourInnerMerge fourInnerMerge = mapFour
								.get(fourSeqNum);
						if (fourInnerMerge == null) {
							fourInnerMerge = new DzscFourInnerMerge();
							fourInnerMerge.setFourSeqNum(Integer
									.valueOf(fileData.getFourNo()));
							fourInnerMerge.setFourComplex(fileData
									.getFourComplexCode().trim());
							fourInnerMerge.setFourPtName(fileData
									.getFourComplexName().trim());
							fourInnerMerge.setFourUnit(getUnit(fileData
									.getFourUnit()));
							// this.dzscInnerMergeDao
							// .saveDzscFourInnerMerge(fourInnerMerge);
							this.dzscInnerMergeDao
									.saveOrUpdateNoCache(fourInnerMerge);
							mapFour.put(fourSeqNum, fourInnerMerge);
						}
						tenInnerMerge.setDzscFourInnerMerge(fourInnerMerge);
					}
					// this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
					this.dzscInnerMergeDao.saveOrUpdateNoCache(tenInnerMerge);
					mapTen.put(tenSeqNum, tenInnerMerge);
				}
				dzscInnerMergeData.setDzscTenInnerMerge(tenInnerMerge);
				if (fileData.getUnitConvert() != null
						&& !"".equals(fileData.getUnitConvert().trim())) {
					dzscInnerMergeData.setUnitConvert(Double
							.parseDouble(fileData.getUnitConvert().trim()));
				}
			}
			// dzscInnerMergeDao.saveDzscInnerMergeData(dzscInnerMergeData);
			dzscInnerMergeDao.saveOrUpdateNoCache(dzscInnerMergeData);
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
		if (complexMap.get(code) == null) {
			CustomsComplex c = (CustomsComplex) cMap.get(code);
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