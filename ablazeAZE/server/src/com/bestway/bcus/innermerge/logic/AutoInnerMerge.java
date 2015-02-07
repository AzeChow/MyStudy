/*
 * Created on 2004-7-13
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;
//李扬更改程序
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.TempAutoInnerMergeParam;
import com.bestway.common.MaterielType;
import com.bestway.dzsc.innermerge.entity.TempDzscAutoInnerMergeParam;

public class AutoInnerMerge {
	private CommonBaseCodeDao commonBaseCodeDao;

	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	private void innerMerge(String materielType,
			TempAutoInnerMergeParam param) {
		List list = commonBaseCodeDao
				.findInnerMergeDataByTypeOrderby(materielType);
		InnerMergeData data = null;
		String tenTemp = "";
		String fourTemp = "";
		int tenNo = -1;
		int fourNo = -1;
		boolean isExistTenInnermerge = false;
		boolean isExistFourInnermerge = false;
		boolean isModity = false;

		Integer existTenNo = null;
		Complex existAfterComplex = null;
		String existTenMaterielName = null;
		String existTenMaterielSpec = null;
		Unit existLegalUnit = null;
		Unit existSecondLegalUnit = null;
		Unit existMemoUnit = null;

		Integer existFourNo = null;
		String existFourMaterielName = null;
		String existFourMaterielCode = null;
		for (int i = 0; i < list.size(); i++) {
			data = (InnerMergeData) list.get(i);
			if (data.getMateriel() == null
					|| data.getMateriel().getComplex() == null
					|| data.getMateriel().getCalUnit() == null) {//|| data.getHsBeforeEnterpriseUnit() == null
				continue;
			}
			if (!(data.getMateriel().getComplex().getCode()
					+ data.getMateriel().getFactoryName()
					+ data.getMateriel().getCalUnit().getCode()).equals(tenTemp)) {// + data.getMateriel().getCalUnit().getCode()
				if (data.getHsAfterTenMemoNo() != null) {
					existTenNo = data.getHsAfterTenMemoNo();
					existAfterComplex = data.getHsAfterComplex();
					existTenMaterielName = data.getHsAfterMaterielTenName();
					existTenMaterielSpec = data.getHsAfterMaterielTenSpec();
					existLegalUnit = data.getHsAfterLegalUnit();
					existSecondLegalUnit = data.getHsAfterSecondLegalUnit();
					existMemoUnit = data.getHsAfterMemoUnit();
					isExistTenInnermerge = true;
				} else {
					tenNo = commonBaseCodeDao.findTenInnerMergeNo(materielType) + 1;
					isExistTenInnermerge = false;
				}
				tenTemp = (param.isMaterialComplexSame() ?
						   data.getMateriel().getComplex().getCode() : "")
						+ (param.isMaterialNameSame() ? 
						   data.getMateriel().getFactoryName() : "")
						+ (param.isMaterialUnitSame() ? 
						   data.getMateriel().getCalUnit().getCode() : "");//+ data.getHsBeforeEnterpriseUnit().getCode();
			}
			if (data.getHsAfterTenMemoNo() == null) {
				if (isExistTenInnermerge) {
					data.setHsAfterTenMemoNo(existTenNo);
					data.setHsAfterComplex(existAfterComplex);
					data.setHsAfterMaterielTenName(existTenMaterielName);
					data.setHsAfterMaterielTenSpec(existTenMaterielSpec);
					data.setHsAfterLegalUnit(existLegalUnit);
					data.setHsAfterSecondLegalUnit(existSecondLegalUnit);
					data.setHsAfterMemoUnit(existMemoUnit);
					isModity = true;
				} else {
					data.setHsAfterTenMemoNo(Integer.valueOf(tenNo));
					data.setHsAfterComplex(data.getMateriel().getComplex());
					data.setHsAfterMaterielTenName(data.getMateriel()
							.getFactoryName());
					data.setHsAfterLegalUnit(data.getHsBeforeLegalUnit());
					data.setHsAfterSecondLegalUnit(data.getHsBeforeLegalUnit());
					data.setHsAfterMemoUnit(data.getHsBeforeEnterpriseUnit().getUnit());
					isModity = true;
				}
			}
			if (data.getHsAfterTenMemoNo() != null && data.getHsAfterComplex()!=null&&data.getHsAfterMemoUnit()!=null) {
				if (!(data.getHsAfterComplex().getCode().substring(0, 4) + 
					  data.getHsAfterMaterielTenName()+
					  data.getHsAfterMemoUnit().getCode()).equals(fourTemp)) {
					if (data.getHsFourNo() != null) {
						existFourNo = data.getHsFourNo();
						existFourMaterielName = data.getHsFourMaterielName();
						existFourMaterielCode = data.getHsFourCode();
						isExistFourInnermerge = true;
					} else {
						fourNo = commonBaseCodeDao
								.findFourInnerMergeNo(materielType) + 1;
						isExistFourInnermerge = false;
					}
					fourTemp = (param.isTenComplexSame() ?
							    data.getHsAfterComplex().getCode().substring(0,4) : "")
					         + (param.isTenNameSame() ? 
					        	data.getHsAfterMaterielTenName() : "")
					         +  (param.isTenUnitSame() ? 
					        	data.getHsAfterMemoUnit().getCode() : "");
				}
				if (data.getHsFourNo() == null) {
					if (isExistFourInnermerge) {
						data.setHsFourNo(existFourNo);
						data.setHsFourCode(existFourMaterielCode);
						data.setHsFourMaterielName(existFourMaterielName);
						isModity = true;
					} else {
						data.setHsFourNo(Integer.valueOf(fourNo));
						data.setHsFourCode(data.getHsAfterComplex()
								.getCode().substring(0, 4));
						data.setHsFourMaterielName(data
								.getHsAfterMaterielTenName());
						isModity = true;
					}
				}
			}
			if (isModity) {
				commonBaseCodeDao.saveInnerMergeData(data);
				isModity = false;
			}
		}
	}

	public void innerMergeData(TempAutoInnerMergeParam param) {
		innerMerge(MaterielType.FINISHED_PRODUCT,param);
		innerMerge(MaterielType.SEMI_FINISHED_PRODUCT,param);
		innerMerge(MaterielType.MATERIEL,param);
		innerMerge(MaterielType.MACHINE,param);
		innerMerge(MaterielType.REMAIN_MATERIEL,param);
		innerMerge(MaterielType.BAD_PRODUCT,param);
	}

}