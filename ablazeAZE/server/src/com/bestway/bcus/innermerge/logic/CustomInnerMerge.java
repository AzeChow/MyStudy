/*
 * Created on 2004-7-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;

import java.util.Hashtable;
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.CustomInnerMergeCondition;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.common.MaterielType;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomInnerMerge {
	private CommonBaseCodeDao commonBaseCodeDao;

	/**
	 * 存放自定义归并的条件，以物料类别为区分。
	 */
	private Hashtable htCondition = null;

	/**
	 * 存放已产生的10位编码
	 */
	private Hashtable htTenNo = null;

	/**
	 * 存放已产生的4位编码
	 */
	private Hashtable htFourNo = null;

	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	/**
	 * 获取自定义归并的条件，以物料类别区分，并存放在htCondition中
	 * 
	 * @param materielType
	 * @return
	 */
	private List getCustomInnerMergeCondition(String materielType) {
		if (htCondition == null) {
			htCondition = new Hashtable();
		}
		if (htCondition.get(materielType) == null) {
			htCondition.put(materielType, commonBaseCodeDao
					.findCustomInnerMergeConditionByType(materielType));
		}
		return (List) htCondition.get(materielType);
	}

	private void clearCondition() {
		if (htCondition != null) {
			htCondition.clear();
		}
	}

	/**
	 * 检查物料编码是否符合条件
	 * 
	 * @param codeCondition
	 * @param materielCode
	 * @return
	 */
	private boolean checkMaterielCode(String codeCondition, String materielCode) {
		if (codeCondition == null || codeCondition.equals("")) {
			return true;
		}
		String regex = codeCondition + ".*?";
		return materielCode.matches(regex);
	}

	/**
	 * 检查物料名称是否符合条件
	 * 
	 * @param nameCondition
	 * @param materielName
	 * @return
	 */
	private boolean checkMaterielName(String nameCondition, String materielName) {
		if (nameCondition == null || nameCondition.equals("")) {
			return true;
		}
		String regex = ".*?" + nameCondition + ".*?";
		return materielName.matches(regex);
	}

	/**
	 * 检查物料规格是否符合条件
	 * 
	 * @param specCodition
	 * @param materielSpec
	 * @return
	 */
	private boolean checkMaterielSpec(String specCodition, String materielSpec) {
		if (specCodition == null || specCodition.equals("")) {
			return true;
		}
		String regex = ".*?" + specCodition + ".*?";
		return materielSpec.matches(regex);
	}

	/**
	 * 检查物料单价是否符合条件
	 * 
	 * @param lowestPrice
	 * @param priceUpExceed
	 * @param materielPrice
	 * @return
	 */
	private boolean checkMaterielPrice(Double lowestPrice,
			Double priceUpExceed, Double materielPrice) {
		if (lowestPrice == null) {
			return true;
		}
		if (materielPrice == null) {
			return false;
		}
		double lowest = lowestPrice.doubleValue();
		double upExceed = 0;
		if (priceUpExceed != null) {
			upExceed = priceUpExceed.doubleValue();
		}
		double max = lowest * (1 + (upExceed / 100.0));
		if (materielPrice.doubleValue() >= lowest
				&& materielPrice.doubleValue() <= max) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 取得10位编码
	 * 
	 * @param condition
	 * @return
	 */
	private Integer getTenNo(CustomInnerMergeCondition condition) {
		if (htTenNo == null) {
			htTenNo = new Hashtable();
		}
		if (htTenNo.get(condition.getId()) == null) {
			htTenNo.put(condition.getId(), Integer.valueOf(commonBaseCodeDao
					.findTenInnerMergeNo(condition.getMaterielType()) + 1));
		}
		return (Integer) htTenNo.get(condition.getId());
	}

	/**
	 * 取得4位编码
	 * 
	 * @param afterComplex
	 * @param materielType
	 * @return
	 */
	private Integer getFourNo(Complex afterComplex, String materielName,
			String materielType) {
		if (htFourNo == null) {
			htFourNo = new Hashtable();
		}
		if (htFourNo.get(materielType) == null) {
			htFourNo.put(materielType, new Hashtable());
		}
		Hashtable ht = (Hashtable) htFourNo.get(materielType);
		if (ht.get(afterComplex.getCode().substring(0, 4) + materielName) == null) {
			ht.put(afterComplex.getCode().substring(0, 4) + materielName,
					Integer.valueOf(commonBaseCodeDao
							.findFourInnerMergeNo(materielType) + 1));
		}
		return (Integer) ht.get(afterComplex.getCode().substring(0, 4));
	}

	/**
	 * 将原来4位编码相同，4位商品名称相同的4位序号保存起来。
	 * @param fourCode
	 * @param materielName
	 * @param materielType
	 * @param fourNo
	 */
	private void setExistFourNo(String fourCode, String materielName,
			String materielType, Integer fourNo) {
		if (htFourNo == null) {
			htFourNo = new Hashtable();
		}
		if (htFourNo.get(materielType) == null) {
			htFourNo.put(materielType, new Hashtable());
		}
		Hashtable ht = (Hashtable) htFourNo.get(materielType);
		if (ht.get(fourCode + materielName) == null) {
			ht.put(fourCode + materielName, fourNo);
		}
	}

	/**
	 * 取得和当前物料数据相符合的归并条件
	 * 
	 * @param data
	 * @return
	 */
	private CustomInnerMergeCondition getCustomInnerMergeCondition(
			InnerMergeData data) {
		List list = getCustomInnerMergeCondition(data.getImrType());
		CustomInnerMergeCondition customInnerMergeCondition = null;
		for (int i = 0; i < list.size(); i++) {
			customInnerMergeCondition = (CustomInnerMergeCondition) list.get(i);
			if (checkMaterielCode(customInnerMergeCondition
					.getConMaterielCode(), data.getMateriel().getPtNo())
					&& checkMaterielName(customInnerMergeCondition
							.getConMaterielName(), data.getMateriel()
							.getFactoryName())
					&& checkMaterielSpec(customInnerMergeCondition
							.getConMaterielSpec(), data.getMateriel()
							.getFactorySpec())
					&& checkMaterielPrice(customInnerMergeCondition
							.getConLowestPrice(), customInnerMergeCondition
							.getConPriceUpExceed(), data.getMateriel()
							.getPtPrice())) {
				return customInnerMergeCondition;
			}
		}
		return null;
	}

	/**
	 * 以物料类别来分类归并
	 * 
	 * @param materielType
	 */
	private void customInnerMergeByEveryType(String materielType) {
		List list = commonBaseCodeDao.findInnerMergeDataByType(materielType);
		InnerMergeData data = null;
		CustomInnerMergeCondition condition = null;
		for (int i = 0; i < list.size(); i++) {
			data = (InnerMergeData) list.get(i);
			if (data.getMateriel() == null
					|| data.getHsAfterTenMemoNo() != null) {
				if (data.getHsAfterTenMemoNo() != null
						&& data.getHsFourNo() != null) {
					setExistFourNo(data.getHsFourCode(), data
							.getHsFourMaterielName(), materielType, data
							.getHsFourNo());
				}
				continue;
			}
			condition = getCustomInnerMergeCondition(data);
			if (condition != null) {
				data.setHsAfterTenMemoNo(getTenNo(condition));
				data.setHsAfterComplex(condition.getAfterComplex());
				data
						.setHsAfterMaterielTenName(condition
								.getAfterMaterielName());
				data.setHsAfterLegalUnit(condition.getAfterLegalUnit());
				data.setHsAfterSecondLegalUnit(condition.getAfterLegalUnit());
				data.setHsAfterMemoUnit(condition.getAfterMemoUnit());
				data.setHsFourNo(getFourNo(condition.getAfterComplex(),
						condition.getAfterMaterielName(), materielType));
				data.setHsFourCode(condition.getAfterComplex().getCode()
						.substring(0, 4));
				data.setHsFourMaterielName(condition.getAfterMaterielName());
				commonBaseCodeDao.saveInnerMergeData(data);
			}
		}
	}

	/**
	 * 归并全部资料
	 *  
	 */
	public void customInnerMergeAll() {
		clearCondition();
		customInnerMergeByEveryType(MaterielType.FINISHED_PRODUCT);
		customInnerMergeByEveryType(MaterielType.SEMI_FINISHED_PRODUCT);
		customInnerMergeByEveryType(MaterielType.MATERIEL);
		customInnerMergeByEveryType(MaterielType.MACHINE);
		customInnerMergeByEveryType(MaterielType.REMAIN_MATERIEL);
		customInnerMergeByEveryType(MaterielType.BAD_PRODUCT);
	}
}