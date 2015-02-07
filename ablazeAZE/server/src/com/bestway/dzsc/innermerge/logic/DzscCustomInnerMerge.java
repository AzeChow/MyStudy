package com.bestway.dzsc.innermerge.logic;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.innermerge.dao.DzscInnerMergeDao;
import com.bestway.dzsc.innermerge.entity.DzscCustomInnerMergeCondition;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;

/**
 * com.bestway.dzsc.dzscmanage.logic.DzscCustomInnerMerge
 * 
 * @author refdom
 * 
 */
public class DzscCustomInnerMerge {
	private DzscInnerMergeDao dzscInnerMergeDao;

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

	/**
	 * 获取dzscDao
	 * 
	 * @return dzscDao
	 */
	public DzscInnerMergeDao getDzscInnerMergeDao() {
		return dzscInnerMergeDao;
	}

	/**
	 * 设置dzscDao
	 * 
	 * @param dzscDao
	 */
	public void setDzscInnerMergeDao(DzscInnerMergeDao dzscDao) {
		this.dzscInnerMergeDao = dzscDao;
	}

	/**
	 * 获取自定义归并的条件，以物料类别区分，并存放在htCondition中
	 * 
	 * @param materielType
	 *            物料类别区
	 * @return List 是DzscCustomInnerMergeCondition型，电子手册自定义归并条件
	 */
	private List getCustomInnerMergeCondition(String materielType) {
		if (htCondition == null) {
			htCondition = new Hashtable();
		}
		if (htCondition.get(materielType) == null) {
			htCondition.put(materielType, dzscInnerMergeDao
					.findDzscCustomInnerMergeConditionByType(materielType));
		}
		return (List) htCondition.get(materielType);
	}

	/**
	 * 清除获取自定义归并的条件
	 * 
	 */
	private void clearCondition() {
		if (htCondition != null) {
			htCondition.clear();
		}
	}

	/**
	 * 检查物料编码是否符合条件
	 * 
	 * @param codeCondition
	 *            标准的
	 * @param materielCode
	 *            要比较的
	 * @return boolean true表示符合
	 */
	private boolean checkMaterielCode(String codeCondition, String materielCode) {
		if (codeCondition == null || codeCondition.equals("")) {
			return true;
		}
		String regex = codeCondition.trim() + ".*?";
		return materielCode.matches(regex);
	}

	/**
	 * 检查物料名称是否符合条件
	 * 
	 * @param nameCondition
	 *            标准的
	 * @param materielName
	 *            要比较的
	 * @return boolean true表示符合
	 */
	private boolean checkMaterielName(String nameCondition, String materielName) {
		if (nameCondition == null || nameCondition.equals("")) {
			return true;
		}
		// String regex = ".*?" + nameCondition.trim() + ".*?";
		// return materielName.matches(regex);
		if (materielName.indexOf(nameCondition.trim()) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查物料规格是否符合条件
	 * 
	 * @param specCodition
	 *            标准的
	 * @param materielSpec
	 *            要比较的
	 * @return boolean true表示符合
	 */
	private boolean checkMaterielSpec(String specCodition, String materielSpec) {
		if (specCodition == null || specCodition.equals("")) {
			return true;
		}
		// String regex = ".*?" + specCodition.trim() + ".*?";
		// return materielSpec.matches(regex);
		if (materielSpec.indexOf(specCodition.trim()) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查物料单价是否符合条件
	 * 
	 * @param lowestPrice
	 *            下限
	 * @param priceUpExceed
	 *            上限
	 * @param materielPrice
	 *            要比较的
	 * @return boolean true表示符合
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
	 *            电子手册自定义归并条件
	 * @return Integer 最大的十位编码序号
	 */
	private Integer getTenNo(DzscCustomInnerMergeCondition condition) {
		if (htTenNo == null) {
			htTenNo = new Hashtable();
		}
		if (htTenNo.get(condition.getId()) == null) {
			htTenNo.put(condition.getId(), Integer.valueOf(dzscInnerMergeDao
					.findTenInnerMergeNo(condition.getMaterielType()) + 1));
		}
		return (Integer) htTenNo.get(condition.getId());
	}

	/**
	 * 取得4位编码
	 * 
	 * @param afterComplex
	 *            归并后商品
	 * @param materielName
	 *            物料名称
	 * @param materielType
	 *            物料类别
	 * @return Integer 最大的四位编码序号
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
					Integer.valueOf(dzscInnerMergeDao
							.findFourInnerMergeNo(materielType) + 1));
		}
		return (Integer) ht.get(afterComplex.getCode().substring(0, 4)
				+ materielName);
	}

	/**
	 * 将原来4位编码相同，4位商品名称相同的4位序号保存起来。
	 * 
	 * @param fourCode
	 *            四位编码
	 * @param materielName
	 *            物料名称
	 * @param materielType
	 *            物料类别
	 * @param fourNo
	 *            4位序号
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
	 *            归并数据
	 * @return DzscCustomInnerMergeCondition 相符合是为归并条件，否则为null
	 */
	private DzscCustomInnerMergeCondition getDzscCustomInnerMergeCondition(
			DzscInnerMergeData data) {
		List list = getCustomInnerMergeCondition(data.getImrType());
		DzscCustomInnerMergeCondition dzscCustomInnerMergeCondition = null;
		for (int i = 0; i < list.size(); i++) {
			dzscCustomInnerMergeCondition = (DzscCustomInnerMergeCondition) list
					.get(i);
			if (checkMaterielCode(dzscCustomInnerMergeCondition
					.getConMaterielCode(), data.getMateriel().getPtNo())
					&& checkMaterielName(dzscCustomInnerMergeCondition
							.getConMaterielName(), data.getMateriel()
							.getFactoryName())
					&& checkMaterielSpec(dzscCustomInnerMergeCondition
							.getConMaterielSpec(), data.getMateriel()
							.getFactorySpec())
					&& checkMaterielPrice(dzscCustomInnerMergeCondition
							.getConLowestPrice(), dzscCustomInnerMergeCondition
							.getConPriceUpExceed(), data.getMateriel()
							.getPtPrice())) {
				return dzscCustomInnerMergeCondition;
			}
		}
		return null;
	}

	/**
	 * 以物料类别来分类归并
	 * 
	 * @param materielType
	 *            物料类别
	 */
	private void customInnerMergeByEveryType(String materielType) {
		List list = dzscInnerMergeDao
				.findDzscInnerMergeDataByType(materielType);
		DzscInnerMergeData data = null;
		DzscCustomInnerMergeCondition condition = null;
		Map<Integer, DzscTenInnerMerge> hmTen = new HashMap<Integer, DzscTenInnerMerge>();
		Map<Integer, DzscFourInnerMerge> hmFour = new HashMap<Integer, DzscFourInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			data = (DzscInnerMergeData) list.get(i);
			if (data.getMateriel() == null
					|| data.getDzscTenInnerMerge() != null) {
				if (data.getDzscTenInnerMerge() != null
						&& data.getDzscTenInnerMerge().getDzscFourInnerMerge() != null) {
					setExistFourNo(data.getDzscTenInnerMerge()
							.getDzscFourInnerMerge().getFourComplex(), data
							.getDzscTenInnerMerge().getDzscFourInnerMerge()
							.getFourPtName(), materielType, data
							.getDzscTenInnerMerge().getDzscFourInnerMerge()
							.getFourSeqNum());
				}
				continue;
			}
			condition = getDzscCustomInnerMergeCondition(data);
			if (condition != null) {
				Integer tenSeqNum = getTenNo(condition);
				if (hmTen.get(tenSeqNum) == null) {
					DzscTenInnerMerge tenInnerMerge = new DzscTenInnerMerge();
					tenInnerMerge.setTenSeqNum(tenSeqNum);
					tenInnerMerge.setTenSeqNum(getTenNo(condition));
					tenInnerMerge.setTenComplex(condition.getAfterComplex());
					tenInnerMerge
							.setTenPtName(condition.getAfterMaterielName());
					// data.setHsAfterLegalUnit(condition.getAfterLegalUnit());
					// data.setHsAfterSecondLegalUnit(condition.getAfterLegalUnit());
					tenInnerMerge.setTenUnit(condition.getAfterMemoUnit());
					tenInnerMerge.setTenModifyMark(ModifyMarkState.ADDED);
					Integer fourSeqNum = getFourNo(condition.getAfterComplex(),
							condition.getAfterMaterielName(), materielType);
					if (hmFour.get(fourSeqNum) == null) {
						DzscFourInnerMerge fourInnerMerge = new DzscFourInnerMerge();
						fourInnerMerge.setFourSeqNum(fourSeqNum);
						fourInnerMerge.setFourComplex(condition
								.getAfterComplex().getCode().substring(0, 4));
						fourInnerMerge.setFourPtName(condition
								.getAfterMaterielName());
						fourInnerMerge
								.setFourUnit(condition.getAfterMemoUnit());
						this.dzscInnerMergeDao
								.saveDzscFourInnerMerge(fourInnerMerge);
						hmFour.put(fourSeqNum, fourInnerMerge);
					}
					tenInnerMerge.setDzscFourInnerMerge(hmFour.get(fourSeqNum));
					this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
					hmTen.put(tenSeqNum, tenInnerMerge);
				}
				data.setDzscTenInnerMerge(hmTen.get(tenSeqNum));
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
			}
		}
	}

	/**
	 * 自动归并数据
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