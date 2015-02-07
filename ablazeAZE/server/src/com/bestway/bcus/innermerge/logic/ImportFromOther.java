/*
 * Created on 2004-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;

// 李扬更改程序
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.dao.ParameterCodeDao;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.InnerMergeFileData;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoi;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportFromOther {

	private CommonBaseCodeDao commonBaseCodeDao;

	private HsCodeDao hsCodeDao;

	private ParameterCodeDao paramerterCodeDao;

	private MaterialManageDao materialManageDao;

	private Materiel materiel;

	private EnterpriseMaterial emateriel;

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
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

	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	/**
	 * 导入物了主挡
	 * 
	 * @param materielTypeList
	 */
	public void importDataFromMaterial(List materielTypeList) {
		List list = commonBaseCodeDao.findMaterielNotInInner(materielTypeList);// 料件
		// InnerMergeData existData = null;
		for (int i = 0; i < list.size(); i++) {
			System.out.println("list.(" + i + ")=" + list.get(i));
		}
		Materiel materiel = null;
		InnerMergeData innerMergeData = null;
		for (int i = 0; i < list.size(); i++) {
			materiel = (Materiel) list.get(i);
			materiel.setIsNewMateriel(new Boolean(false));
			materialManageDao.saveMateriel(materiel);
			/*
			 * existData = commonBaseCodeDao
			 * .findInnerMergeDataByMaterial(materiel); if (existData == null) {
			 */
			innerMergeData = new InnerMergeData();
			innerMergeData.setCompany(CommonUtils.getCompany());

			innerMergeData.setMateriel(materiel);
			innerMergeData.setImrType(materiel.getScmCoi().getCoiProperty());

			if (materiel.getComplex() != null) {
				innerMergeData.setHsBeforeLegalUnit(materiel.getComplex()
						.getFirstUnit());
			}
			if (materiel.getCalUnit() != null) {
				if (materiel.getCalUnit().getUnit() != null) {
					innerMergeData.setHsBeforeEnterpriseUnit(materiel
							.getCalUnit());
				}
			}

			commonBaseCodeDao.saveInnerMergeData(innerMergeData);
		} /*
		 * else { if (existData.getHsAfterTenMemoNo() != null) { continue; } if
		 * (materiel.getComplex() != null) {
		 * existData.setHsBeforeLegalUnit(materiel.getComplex()
		 * .getFirstUnit()); } if (materiel.getCalUnit() != null) { if
		 * (materiel.getCalUnit().getUnit() != null) {
		 * existData.setHsBeforeEnterpriseUnit(materiel
		 * .getCalUnit().getUnit()); } } if (!existData.getImrType().equals(
		 * materiel.getScmCoi().getCoiProperty())) {
		 * existData.setImrType(materiel.getScmCoi().getCoiProperty()); }
		 * commonBaseCodeDao.saveInnerMergeData(existData); }
		 */
		// }
	}

	private Double getDigi(Double d, int num) {
		if (d == null) {
			return 0.0;
		}
		BigDecimal b = new BigDecimal(d);
		return b.setScale(num, BigDecimal.ROUND_HALF_DOWN).doubleValue();
	}
	
	private Materiel getMateriel(Map<String, Materiel> existMaterielMap,
			InnerMergeFileData data, ScmCoi scmCoi,
			Map<String, EnterpriseMaterial> existEMaterielMap) {
		String ptNo = data.getBeforeMaterielCode();
		if (ptNo.trim().equals("")) {
			return null;
		}
		int point = 9;
		CalUnit calUnit = null;
		if (existMaterielMap.get(ptNo) != null) {// 存在
			materiel = existMaterielMap.get(ptNo);
			materiel.setIsNewMateriel(new Boolean(false));
			materiel.setUnitConvert(data.getUnitConvert());
			materiel.setFactoryName(data.getBeforeMaterielName());
			materiel.setFactorySpec(data.getBeforeMaterielSpec());
			materiel.setComplex(getComplex(data.getAfterTenComplexCode()));
			materiel.setPtName(data.getAfterComplexlName());
			materiel.setPtSpec(data.getAfterComplexSpec());
			materiel.setPtUnit(getUnit(data.getAfterMemoUnit()));
			materiel.setUnitConvert(getDigi(data.getUnitConvert(), point));
			materiel.setPtNetWeight(getDigi(data.getUnitWeight(), point));
			materiel.setPtPrice(getDigi(data.getPtPrice(), point));// 单价
			commonBaseCodeDao.getHibernateTemplate().update(materiel);
			return materiel;
		} else {
			calUnit = this.getCalUnit(data.getBeforeEnterpriseUnit());
			materiel = new Materiel();
			materiel.setScmCoi(scmCoi);
			materiel.setPtNo(data.getBeforeMaterielCode());
			materiel.setFactoryName(data.getBeforeMaterielName());
			materiel.setFactorySpec(data.getBeforeMaterielSpec());
			materiel.setComplex(getComplex(data.getAfterTenComplexCode()));
			materiel.setPtName(data.getAfterComplexlName());
			materiel.setPtSpec(data.getAfterComplexSpec());
			materiel.setPtUnit(getUnit(data.getAfterMemoUnit()));
			materiel.setUnitConvert(getDigi(data.getUnitConvert(), point));
			materiel.setPtNetWeight(getDigi(data.getUnitWeight(), point));
			materiel.setCompany(CommonUtils.getCompany());
			materiel.setIsNewMateriel(new Boolean(false));
			materiel.setPtPrice(getDigi(data.getPtPrice(), point));// 单价
			if (calUnit != null) {
				materiel.setCalUnit(calUnit);
			}
			materialManageDao.saveMateriel(materiel);
			existMaterielMap.put(ptNo, materiel);
		}
		// 检查工厂物料
		if (existEMaterielMap.get(ptNo) == null) {
			emateriel = new EnterpriseMaterial();
			emateriel.setScmCoi(scmCoi);
			emateriel.setPtNo(data.getBeforeMaterielCode());
			emateriel.setComplex(getComplex(data.getBeforeTenComplexCode()));
			emateriel.setFactoryName(data.getBeforeMaterielName());
			emateriel.setFactorySpec(data.getBeforeMaterielSpec());
			emateriel.setUnitConvert(getDigi(data.getUnitConvert(), point));
			emateriel.setPtNetWeight(getDigi(data.getUnitWeight(), point));
			emateriel.setCompany(CommonUtils.getCompany());
			emateriel.setPtPrice(getDigi(data.getPtPrice(), point));// 单价
			if (calUnit != null) {
				emateriel.setCalUnit(calUnit);
			}
			materialManageDao.saveEMateriel(emateriel);
			existEMaterielMap.put(ptNo, emateriel);
		}

		return materiel;
	}

	private Map<String, Integer> getMaterielCount(List list) {
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			InnerMergeFileData fileData = (InnerMergeFileData) list.get(i);
			if (hm.get(fileData.getBeforeMaterielCode()) == null) {
				hm.put(fileData.getBeforeMaterielCode(), 1);
			} else {
				hm.put(fileData.getBeforeMaterielCode(), Integer.valueOf(hm
						.get(fileData.getBeforeMaterielCode()).toString()) + 1);
			}
		}
		return hm;
	}

	public List checkFileData(List list, Hashtable ht, String materielType) {
		ArrayList ls = new ArrayList();
		// ArrayList temp = new ArrayList();
		calUnitMap = null;
		unitMap = null;
		complexMap = null;
		cMap = null;
		getCustomsComplex();
		InnerMergeFileData fileData = null;
		int[] invalidationColNo;
		Map<String, Integer> hmMaterielCount = getMaterielCount(list);

		list = checkFileAfterTenError(list, ht);// 检查导入文件中的十码资料
		// <<<<<<< .mine
		// // list = checkFileFourError(list, ht);// 检查导入文件中的四码资料
		// =======
		// // list = checkFileFourError(list, ht);// 检查导入文件中的四码资料
		// >>>>>>> .r10457
		for (int i = 0; i < list.size(); i++) {
			// temp.clear();
			fileData = (InnerMergeFileData) list.get(i);
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.BEFORE_MATERIEL_CODE))
					.toString()) > -1) {
				/*
				 * if (commonBaseCodeDao.findInnerMergeDataByPtNo(fileData
				 * .getBeforeMaterielCode()) != null) { temp.add(Integer
				 * .valueOf(InnerMergeFileData.BEFORE_MATERIEL_CODE));
				 * fileData.setErrorReason(fileData.getErrorReason() +
				 * "归并前料号在归并中已存在;"); } else
				 */
				if (hmMaterielCount.get(fileData.getBeforeMaterielCode()) != null
						&& hmMaterielCount
								.get(fileData.getBeforeMaterielCode()) > 1) {
					/*
					 * temp.add(Integer
					 * .valueOf(InnerMergeFileData.BEFORE_MATERIEL_CODE));
					 */
					fileData.setErrinfo(fileData.getErrinfo() + "文件中归并前料号重复;");
				}
				if (commonBaseCodeDao.findInnerMergeDataByPtNo(fileData
						.getBeforeMaterielCode()) != null) {
					fileData.setErrinfo(fileData.getErrinfo()
							+ "归并前料号已经在内部归并里存在;");
				}
			}
			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(InnerMergeFileData.BEFORE_TEN_COMPLEX_CODE))
							.toString()) > -1) {
				if (getComplex(fileData.getBeforeTenComplexCode()) == null) {
					/*
					 * temp .add(Integer
					 * .valueOf(InnerMergeFileData.BEFORE_TEN_COMPLEX_CODE));
					 */
					fileData.setErrinfo(fileData.getErrinfo()
							+ "归并前海关商品编码库不存在;");
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.BEFORE_LEGAL_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getBeforeLegalUnit()) == null) {
					/*
					 * temp.add(Integer
					 * .valueOf(InnerMergeFileData.BEFORE_LEGAL_UNIT));
					 */
					fileData.setErrinfo(fileData.getErrinfo() + "归并前法定单位不存在;");
				}
			}
			/*
			 * if (Integer.parseInt(ht.get(
			 * Integer.valueOf(InnerMergeFileData.BEFORE_ENTERPRISE_UNIT))
			 * .toString()) > -1) { if
			 * (getCalUnit(fileData.getBeforeEnterpriseUnit()) == null) { temp
			 * .add(Integer
			 * .valueOf(InnerMergeFileData.BEFORE_ENTERPRISE_UNIT));
			 * fileData.setErrorReason(fileData.getErrorReason() +
			 * "归并前企业单位不存在;"); } }
			 */
			List listData = commonBaseCodeDao.findInnerMergeDataByTenNo(
					materielType, fileData.getAfterTenMemoNo());// 根据十码和类别查找内部归并数据
			InnerMergeData mergeData = null;
			if (!listData.isEmpty())
				mergeData = (InnerMergeData) listData.get(0);
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE))
					.toString()) > -1) {
				if (getComplex(fileData.getAfterTenComplexCode()) == null) {
					/*
					 * temp .add(Integer
					 * .valueOf(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE));
					 */
					fileData.setErrinfo(fileData.getErrinfo()
							+ "归并后海关商品编码库不存在;");
				} else if (mergeData != null
						&& !mergeData.getHsAfterComplex().getCode().equals(
								fileData.getAfterTenComplexCode())) {
					fileData.setErrinfo(fileData.getErrinfo()
							+ "文件中“归并后海关商品编码”与数据库里备案序号为"
							+ fileData.getAfterTenMemoNo() + "对应的不同;");
				}
			}

			if (Integer
					.parseInt(ht
							.get(
									Integer
											.valueOf(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC))
							.toString()) > -1) {
				if (fileData.getHsAfterComplexNameSpec() != null) {
					if (mergeData != null
							&& !mergeData.getHsAfterMaterielNameSpec().equals(
									fileData.getHsAfterComplexNameSpec())) {
						fileData.setErrinfo(fileData.getErrinfo()
								+ "文件中“商品名称，规格，型号”与数据库里备案序号为"
								+ fileData.getAfterTenMemoNo() + "对应的不同;");
					}
				}
			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.After_Name)).toString()) > -1) {
				if (fileData.getAfterComplexlName() != null) {
					if (mergeData != null
							&& !mergeData.getHsAfterMaterielTenName().equals(
									fileData.getAfterComplexlName())) {
						fileData.setErrinfo(fileData.getErrinfo()
								+ "文件中“归并后商品名称”与数据库里备案序号为"
								+ fileData.getAfterTenMemoNo() + "对应的不同;");
					}
				}
			}

			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.After_Spec)).toString()) > -1) {
				if (fileData.getAfterComplexSpec() != null) {
					if (mergeData != null
							&& !mergeData.getHsAfterMaterielTenSpec().equals(
									fileData.getAfterComplexSpec())) {
						fileData.setErrinfo(fileData.getErrinfo()
								+ "文件中“归并后型号规格”与数据库里备案序号为"
								+ fileData.getAfterTenMemoNo() + "对应的不同;");
					}
				}
			}

			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.AFTER_LEGAL_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getAfterLegalUnit()) == null) {
					/*
					 * temp.add(Integer
					 * .valueOf(InnerMergeFileData.AFTER_LEGAL_UNIT));
					 */
					fileData.setErrinfo(fileData.getErrinfo() + "归并后法定单位不存在;");
				} else if (mergeData != null
						&& !mergeData.getHsAfterLegalUnit().getName().equals(
								fileData.getAfterLegalUnit())) {
					fileData.setErrinfo(fileData.getErrinfo()
							+ "文件中“计量单位(法定)”与数据库里备案序号为"
							+ fileData.getAfterTenMemoNo() + "对应的不同;");
				}

			}
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.AFTER_MEMO_UNIT))
					.toString()) > -1) {
				if (getUnit(fileData.getAfterMemoUnit()) == null) {
					/*
					 * temp.add(Integer
					 * .valueOf(InnerMergeFileData.AFTER_MEMO_UNIT));
					 */
					fileData.setErrinfo(fileData.getErrinfo() + "归并后备案单位不存在;");
				} else if (mergeData != null
						&& !mergeData.getHsAfterMemoUnit().getName().equals(
								fileData.getAfterMemoUnit())) {
					fileData.setErrinfo(fileData.getErrinfo()
							+ "文件中“计量单位(备案)”与数据库里备案序号为"
							+ fileData.getAfterTenMemoNo() + "对应的不同;");
				}
			}

			// listData = commonBaseCodeDao.findInnerMergeDataByFourNo(
			// materielType, fileData.getFourNo());// 根据四码序号和类别查找内部归并数据
			// mergeData = null;
			// if (!listData.isEmpty())
			// mergeData = (InnerMergeData) listData.get(0);
			//
			// if (Integer.parseInt(ht.get(
			// Integer.valueOf(InnerMergeFileData.FOUR_COMPLEX_CODE))
			// .toString()) > -1) {
			// if (fileData.getFourComplexCode() == null) {
			// /*
			// * temp.add(Integer
			// * .valueOf(InnerMergeFileData.AFTER_LEGAL_UNIT));
			// */
			// fileData.setErrorReason(fileData.getErrorReason()
			// + "4位商品编码不可为空;");
			// } else if (mergeData != null
			// && !mergeData.getHsFourCode().equals(
			// fileData.getFourComplexCode())) {
			// fileData.setErrorReason(fileData.getErrorReason()
			// + "文件中“4位商品编码”与数据库里4位编码序号为" + fileData.getFourNo()
			// + "对应的不同;");
			// }
			//
			// }
			// if (Integer.parseInt(ht.get(
			// Integer.valueOf(InnerMergeFileData.FOUR_COMPLEX_NAME))
			// .toString()) > -1) {
			// if (fileData.getFourComplexName() == null) {
			// /*
			// * temp.add(Integer
			// * .valueOf(InnerMergeFileData.AFTER_MEMO_UNIT));
			// */
			// fileData.setErrorReason(fileData.getErrorReason()
			// + "4位商品名称不可为空;");
			// } else if (mergeData != null
			// && !mergeData.getHsFourMaterielName().equals(
			// fileData.getFourComplexName())) {
			// fileData.setErrorReason(fileData.getErrorReason()
			// + "文件中“4位商品名称”与数据库里4位编码序号为" + fileData.getFourNo()
			// + "对应的不同;");
			// }
			// }
			ls.add(fileData);
			/*
			 * if (temp.size() > 0) { invalidationColNo = new int[temp.size()];
			 * for (int j = 0; j < temp.size(); j++) { invalidationColNo[j] =
			 * Integer.parseInt(temp.get(j) .toString()); }
			 * fileData.setInvalidationColNo(invalidationColNo);
			 * ls.add(fileData); }
			 */
		}
		return ls;
	}

	/**
	 * 检查要导入的数据中的归并后资料
	 * 
	 * @param list
	 *            导入的list
	 * @return
	 */
	private List checkFileAfterTenError(List list, Hashtable ht) {
		InnerMergeFileData fileData = null;
		Map map = new HashMap();
		Map exitMap = new HashMap();
		// 先生成一个map
		int j = 0;
		for (int i = 0; i < list.size(); i++) {
			fileData = (InnerMergeFileData) list.get(i);
			String str = "";
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.AFTER_TEN_MEMO_NO))
					.toString()) > -1) {
				if (fileData.getAfterTenMemoNo() == null) {
					continue;
				} else {
					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE))
									.toString()) > -1) {
						if (exitMap
								.get(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE) == null) {
							exitMap.put(
									InnerMergeFileData.AFTER_TEN_COMPLEX_CODE,
									j);
							j++;
						}
						if (fileData.getAfterTenComplexCode() != null) {
							str = fileData.getAfterTenComplexCode();
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC))
									.toString()) > -1) {
						if (exitMap
								.get(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC) == null) {
							exitMap.put(
									InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC,
									j);
							j++;
						}
						if (fileData.getHsAfterComplexNameSpec() != null) {
							str += "@" + fileData.getHsAfterComplexNameSpec();
						}
					}
					if (Integer.parseInt(ht.get(
							Integer.valueOf(InnerMergeFileData.After_Name))
							.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.After_Name) == null) {
							exitMap.put(InnerMergeFileData.After_Name, j);
							j++;
						}
						if (fileData.getAfterComplexlName() != null) {
							str += "@" + fileData.getAfterComplexlName();
						}
					}

					if (Integer.parseInt(ht.get(
							Integer.valueOf(InnerMergeFileData.After_Spec))
							.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.After_Spec) == null) {
							exitMap.put(InnerMergeFileData.After_Spec, j);
							j++;
						}
						if (fileData.getAfterComplexSpec() != null) {
							str += "@" + fileData.getAfterComplexSpec();
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_LEGAL_UNIT))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.AFTER_LEGAL_UNIT) == null) {
							exitMap.put(InnerMergeFileData.AFTER_LEGAL_UNIT, j);
							j++;
						}
						if (fileData.getAfterLegalUnit() != null) {
							str += "@" + fileData.getAfterLegalUnit();
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_MEMO_UNIT))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.AFTER_MEMO_UNIT) == null) {
							exitMap.put(InnerMergeFileData.AFTER_MEMO_UNIT, j);
							j++;
						}
						if (fileData.getAfterMemoUnit() != null) {
							str += "@" + fileData.getAfterMemoUnit();
						}
					}
				}

				if (str.substring(0, 1).equals("@")) {
					str = str.substring(1, str.length());
				}

				if (fileData.getAfterTenMemoNo() != null
						&& map.get(fileData.getAfterTenMemoNo()) == null) {
					map.put(fileData.getAfterTenMemoNo(), str);
				}
			}

		}

		// 逐项检查数据的正确性
		for (int i = 0; i < list.size(); i++) {
			fileData = (InnerMergeFileData) list.get(i);
			String str = "";
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.AFTER_TEN_MEMO_NO))
					.toString()) > -1) {
				if (fileData.getAfterTenMemoNo() == null) {
					fileData.setErrinfo(fileData.getErrinfo() + "数据没有归并序号;");
					continue;
				} else {
					String checkStr = (String) map.get(fileData
							.getAfterTenMemoNo());
					String[] check = checkStr.split("@");

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE))
									.toString()) > -1) {
						if (exitMap
								.get(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.AFTER_TEN_COMPLEX_CODE)]
									.equals(fileData.getAfterTenComplexCode())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的备案序号但归并后“10位商品编码”不同;");
							}
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC))
									.toString()) > -1) {
						if (exitMap
								.get(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.AFTER_COMPLEX_NAME_SPEC)]
									.equals(fileData
											.getHsAfterComplexNameSpec())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的备案序号但归并后“商品名称，规格，型号”不同;");
							}
						}
					}
					if (Integer.parseInt(ht.get(
							Integer.valueOf(InnerMergeFileData.After_Name))
							.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.After_Name) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.After_Name)]
									.equals(fileData.getAfterComplexlName())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的备案序号但归并后“商品名称”不同;");
							}
						}
					}

					if (Integer.parseInt(ht.get(
							Integer.valueOf(InnerMergeFileData.After_Spec))
							.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.After_Spec) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.After_Spec)]
									.equals(fileData.getAfterComplexSpec())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的备案序号但归并后“型号规格”不同;");
							}
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_LEGAL_UNIT))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.AFTER_LEGAL_UNIT) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.AFTER_LEGAL_UNIT)]
									.equals(fileData.getAfterLegalUnit())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的备案序号但归并后“计量单位(法定)”不同;");
							}
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.AFTER_MEMO_UNIT))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.AFTER_MEMO_UNIT) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.AFTER_MEMO_UNIT)]
									.equals(fileData.getAfterMemoUnit())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的备案序号但归并后“计量单位(备案)”不同;");
							}
						}
					}
				}

			}

		}

		return list;
	}

	/**
	 * 检查要导入的数据中的四码的资料
	 * 
	 * @param list
	 *            导入的list
	 * @return
	 */
	private List checkFileFourError(List list, Hashtable ht) {
		InnerMergeFileData fileData = null;
		Map map = new HashMap();
		Map exitMap = new HashMap();
		// 先生成一个map
		int j = 0;
		for (int i = 0; i < list.size(); i++) {
			fileData = (InnerMergeFileData) list.get(i);
			String str = "";
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.FOUR_NO)).toString()) > -1) {
				if (fileData.getFourNo() == null) {
					continue;
				} else {
					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.FOUR_COMPLEX_CODE))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.FOUR_COMPLEX_CODE) == null) {
							exitMap
									.put(InnerMergeFileData.FOUR_COMPLEX_CODE,
											j);
							j++;
						}
						if (fileData.getFourComplexCode() != null) {
							str = fileData.getFourComplexCode();
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.FOUR_COMPLEX_NAME))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.FOUR_COMPLEX_NAME) == null) {
							exitMap
									.put(InnerMergeFileData.FOUR_COMPLEX_NAME,
											j);
							j++;
						}
						if (fileData.getFourComplexName() != null) {
							str += "@" + fileData.getFourComplexName();
						}
					}

				}

				if (str.substring(0, 1).equals("@")) {
					str = str.substring(1, str.length());
				}

				if (fileData.getFourNo() != null
						&& map.get(fileData.getFourNo()) == null) {
					map.put(fileData.getFourNo(), str);
				}
			}

		}

		// 逐项检查数据的正确性
		for (int i = 0; i < list.size(); i++) {
			fileData = (InnerMergeFileData) list.get(i);
			String str = "";
			if (Integer.parseInt(ht.get(
					Integer.valueOf(InnerMergeFileData.AFTER_TEN_MEMO_NO))
					.toString()) > -1) {
				if (fileData.getFourNo() == null) {
					fileData.setErrinfo(fileData.getErrinfo() + "数据没有四码序号;");
					continue;
				} else {
					String checkStr = (String) map.get(fileData.getFourNo());
					String[] check = checkStr.split("@");

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.FOUR_COMPLEX_CODE))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.FOUR_COMPLEX_CODE) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.FOUR_COMPLEX_CODE)]
									.equals(fileData.getFourComplexCode())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的4位编码序号但“4位商品编码”不同;");
							}
						}
					}

					if (Integer
							.parseInt(ht
									.get(
											Integer
													.valueOf(InnerMergeFileData.FOUR_COMPLEX_NAME))
									.toString()) > -1) {
						if (exitMap.get(InnerMergeFileData.FOUR_COMPLEX_NAME) != null) {
							if (!check[(Integer) exitMap
									.get(InnerMergeFileData.FOUR_COMPLEX_NAME)]
									.equals(fileData.getFourComplexName())) {
								fileData.setErrinfo(fileData.getErrinfo()
										+ "文件中存在相同的4位编码序号但“4位商品名称”不同;");
							}
						}
					}

				}

			}

		}

		return list;
	}

	/**
	 * 导入文件来自文件
	 * 
	 * @param list
	 */
	public void importDataFromTxtFile(List list) {
		// for(int i=0;i<list.size();i++){
		// System.out.println("list.("+i+")="+list.get(i));
		// }
		// InnerMergeFileData test =(InnerMergeFileData)list.get(0);
		// System.out.println("complex="+test.getBeforeTenComplexCode());
		// System.out.println("afterTenComplexCode="+test.getAfterTenComplexCode());
		// System.out.println("afterComplexlName="+test.getAfterComplexlName());
		// System.out.println("afterComplexSpec="+test.getAfterComplexSpec());
		// System.out.println("afterMemoUnit="+test.getAfterMemoUnit());

		InnerMergeFileData fileData = null;
		InnerMergeData innerMergeData = null;
		Materiel materiel = null;

		ScmCoi scmCoi = null;
		if (list.size() > 0) {
			scmCoi = materialManageDao
					.findScmCoiByType(((InnerMergeFileData) list.get(0))
							.getMaterielType());
		}
		if (scmCoi == null) {
			throw new RuntimeException("没有当前选择的工厂物料类别。请到'物流基础资料-工厂通用代码-工厂物料类别'设置"); 
		}
		long startTime = System.currentTimeMillis();
		//
		// 获得已存在的内部归并数据 key = ptNo + 归并后的海关商品编号
		//
		Map<String, InnerMergeData> existInnerMergeMap = new HashMap<String, InnerMergeData>();
		List<InnerMergeData> existInnerMergeDataList = commonBaseCodeDao
				.findInnerMergeDataByType(scmCoi.getCoiProperty());
		for (int i = 0, n = existInnerMergeDataList.size(); i < n; i++) {
			InnerMergeData temp = existInnerMergeDataList.get(i);
			if (temp.getMateriel() == null || temp.getHsAfterComplex() == null) {
				continue;
			}
			String key = temp.getMateriel().getPtNo().trim();
			existInnerMergeMap.put(key, temp);
		}
		long endTime = System.currentTimeMillis();
//		System.out.println("000000000=" + (endTime - startTime));
		//
		// 获得已存在的物料主档数据
		//
		long startTime1 = System.currentTimeMillis();
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
		long endTime1 = System.currentTimeMillis();
		System.out.println("11111111=" + (endTime1 - startTime1));
		/**
		 * 获取已经存在的工厂原始物料
		 */
		long startTime2 = System.currentTimeMillis();
		Map<String, EnterpriseMaterial> existEMaterielMap = new HashMap<String, EnterpriseMaterial>();
		List<EnterpriseMaterial> existEMaterielList = materialManageDao
				.findEMaterielByMaterielType(scmCoi.getCoiProperty());
		for (int i = 0, n = existEMaterielList.size(); i < n; i++) {
			EnterpriseMaterial temp1 = (EnterpriseMaterial) existEMaterielList
					.get(i);
			String key = temp1.getPtNo();
			if (key == null || "".equals(key)) {
				continue;
			}
			existEMaterielMap.put(key, temp1);
		}
		long endTime2 = System.currentTimeMillis();
		System.out.println("22222222=" + (endTime2 - startTime2));
		long startTime3 = System.currentTimeMillis();
		Date importTimer = null;
		int count = this.commonBaseCodeDao.findCountByInnerMergeData(scmCoi
				.getCoiProperty());
		if (count <= 0) { // 如果是没有一条数据导入
			importTimer = null; // 第一次不用标识
		} else {
			importTimer = new Date();
		}
		long endTime3 = System.currentTimeMillis();
		System.out.println("33333333=" + (endTime3 - startTime3));
		//
		// 开始转换
		//
		long startTime4 = System.currentTimeMillis();
		for (int i = 0, n = list.size(); i < n; i++) {
			fileData = (InnerMergeFileData) list.get(i);
			//
			// key = ptNo + 归并后的海关商品编号
			//
			// String existInnerMergeKey =
			// fileData.getBeforeMaterielCode().trim()
			// + fileData.getAfterTenComplexCode().trim();
			String existInnerMergeKey = fileData.getBeforeMaterielCode().trim();
			if (existInnerMergeKey.equals("")) {
				return ;
			}
			if (existInnerMergeMap.get(existInnerMergeKey) != null) {// 内部归并存在
				innerMergeData = existInnerMergeMap.get(existInnerMergeKey);
			} else {
				innerMergeData = new InnerMergeData();
			}
			
			//检查文本导入料号与物料主档料号大小写是否一致
		/*	if (existMaterielMap.get(existInnerMergeKey) != null) {// 存在
				materiel = existMaterielMap.get(existInnerMergeKey);
				chechPtNo(materiel, existInnerMergeKey);//检查文本导入料号与物料主档料号大小写是否一致
			}*/

			//
			// 获得物料主档
			//
			materiel = getMateriel(existMaterielMap, fileData, scmCoi,
					existEMaterielMap);
			//
			// 新增内部归并数据
			//			
			innerMergeData.setImrType(fileData.getMaterielType().trim());
			innerMergeData.setCompany(CommonUtils.getCompany());
			innerMergeData.setMateriel(materiel);

			innerMergeData.setHsBeforeEnterpriseUnit(getCalUnit(fileData
					.getBeforeEnterpriseUnit()));

			innerMergeData
					.setHsBeforeLegalUnit(getUnit(getComplex(
							fileData.getAfterTenComplexCode()).getFirstUnit()
							.getName()));

			if (!fileData.getAfterTenMemoNo().trim().equals("")) {
				innerMergeData.setHsAfterTenMemoNo(Integer.valueOf(fileData
						.getAfterTenMemoNo()));
			}
			innerMergeData.setHsAfterComplex(getComplex(fileData
					.getAfterTenComplexCode()));
			innerMergeData.setHsAfterMaterielTenName(fileData
					.getAfterComplexlName());
			innerMergeData.setHsAfterMaterielTenSpec(fileData
					.getAfterComplexSpec());

			innerMergeData
					.setHsAfterLegalUnit(getUnit(getComplex(
							fileData.getAfterTenComplexCode()).getFirstUnit()
							.getName()));
			innerMergeData
					.setHsAfterSecondLegalUnit(getUnit(getComplex(
							fileData.getAfterTenComplexCode()).getSecondUnit() == null ? ""
							: getComplex(fileData.getAfterTenComplexCode())
									.getSecondUnit().getName()));

			innerMergeData.setHsAfterMemoUnit(getUnit(fileData
					.getAfterMemoUnit()));
			if (!fileData.getFourNo().trim().equals("")) {
				innerMergeData.setHsFourNo(Integer
						.valueOf(fileData.getFourNo()));
			}
			innerMergeData.setHsFourCode(fileData.getFourComplexCode().trim());
			innerMergeData.setHsFourMaterielName(fileData.getFourComplexName()
					.trim());
			innerMergeData.setImportTimer(importTimer);
			innerMergeData.setEmsSerialNo((materiel == null) ? null : materiel
					.getPtNo());
			if(fileData.getFristUnitRatio()!= null){
				innerMergeData.setFristUnitRatio(fileData.getFristUnitRatio());
			}
			if(fileData.getSecondUnitRatio()!=null)
				innerMergeData.setSecondUnitRatio(fileData.getSecondUnitRatio());
			if(fileData.getWeigthUnitGene()!=null)
				innerMergeData.setWeigthUnitGene(fileData.getWeigthUnitGene());
			if(fileData.getIsMainImg()!=null)
				innerMergeData.setIsMainImg(fileData.getIsMainImg());
			
			commonBaseCodeDao.saveInnerMergeData(innerMergeData);
		}
		long endTime4 = System.currentTimeMillis();
		System.out.println("4444444=" + (endTime4 - startTime4));
		calUnitMap = null;
		unitMap = null;
		complexMap = null;
		cMap = null;
	}

	
	private void chechPtNo(Materiel materiel,String ptNo){
		//检查文本导入料号与物料主档料号大小写是否一致
		if(!ptNo.equals(materiel.getPtNo())){
			throw new RuntimeException("文本导入料号["+ptNo+"]与物料主档料号["+materiel.getPtNo()+"]不一致"); 
		}
	}
	
	// ///////////////////////////////////////////////////////////
	// hash 类
	// ///////////////////////////////////////////////////////////
	private Map<String, Complex> complexMap = null;

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
			return complexMap.get(code);
		}
	}

	private Unit getUnit(String name) {
		if (unitMap == null) {
			System.out.println("begin init unitMap");
			unitMap = new HashMap<String, Object>();
			List list = materialManageDao.findUnit();
			for (int i = 0, n = list.size(); i < n; i++) {
				Unit unit = (Unit) list.get(i);
				if (unit.getName() == null) {
					continue;
				}
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
		if (calUnitMap.get(name.trim()) == null) {
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

	/*
	 * private CalUnit getCalUnit(String name) { if (name == null) { return
	 * null; } return (CalUnit) getCalNameUnit((Company)
	 * CommonUtils.getCompany()) .get(name.trim()); }
	 */

	// private Map<String, Map> calNameUnitMap = new HashMap<String, Map>();
	/*
	 * 获得计量单位 以名称为key
	 * 
	 * @param company @return
	 */
	private Map unitMap = null;

	private Map calUnitMap = null;

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