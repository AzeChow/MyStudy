package com.bestway.dzsc.innermerge.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.common.MaterielType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.innermerge.dao.DzscInnerMergeDao;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.innermerge.entity.TempDzscAutoInnerMergeParam;

/**
 * com.bestway.dzsc.dzscmanage.logic.DzscAutoInnerMerge
 * 
 * @author yp
 * 
 */
public class DzscAutoInnerMerge {
	private DzscInnerMergeDao dzscInnerMergeDao;

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
	 * 归并数据
	 * 
	 * @param materielType
	 *            物料类别
	 */
	private void innerMerge(String materielType,
			TempDzscAutoInnerMergeParam param) {
		List list = dzscInnerMergeDao
				.findDzscInnerMergeDataByTypeOrderby(materielType);
		DzscInnerMergeData data = null;
		String tenTemp = "";
		String fourTemp = "";
		int tenNo = -1;
		int fourNo = -1;
		Map<String, DzscTenInnerMerge> hmTen = new HashMap<String, DzscTenInnerMerge>();
		Map<String, DzscFourInnerMerge> hmFour = new HashMap<String, DzscFourInnerMerge>();

		for (int i = 0; i < list.size(); i++) {
			data = (DzscInnerMergeData) list.get(i);
			if (data.getMateriel() == null
					|| data.getMateriel().getComplex() == null) {
				continue;
			}
			if (data.getDzscTenInnerMerge() != null
					&& data.getDzscTenInnerMerge().getDzscFourInnerMerge() != null) {
				continue;
			}
			DzscTenInnerMerge tenInnerMerge = null;
			tenTemp = (param.isMaterialComplexSame() ? data.getMateriel()
					.getComplex().getCode() : "")
					+ (param.isMaterialNameSame() ? data.getMateriel()
							.getFactoryName() : "")
					+ (param.isMaterialUnitSame() ? data.getMateriel()
							.getPtUnit().getCode() : "");
			if (data.getDzscTenInnerMerge() == null) {
				if (hmTen.get(tenTemp) == null) {
					tenNo = dzscInnerMergeDao.findTenInnerMergeNo(materielType) + 1;
					tenInnerMerge = new DzscTenInnerMerge();
					tenInnerMerge.setTenSeqNum(Integer.valueOf(tenNo));
					tenInnerMerge
							.setTenComplex(data.getMateriel().getComplex());
					tenInnerMerge.setTenPtName(data.getMateriel().getComplex()
							.getName());
					if (data.getMateriel().getPtUnit() != null) {
						tenInnerMerge
								.setTenUnit(data.getMateriel().getPtUnit());
					}
					tenInnerMerge.setTenModifyMark(ModifyMarkState.ADDED);
					hmTen.put(tenTemp, tenInnerMerge);
				} else {
					tenInnerMerge = hmTen.get(tenTemp);
				}
			} else {
				tenInnerMerge = data.getDzscTenInnerMerge();
				hmTen.put(tenTemp, data.getDzscTenInnerMerge());
			}
			// data.setDzscTenInnerMerge(hmTen.get(tenTemp));
			fourTemp = (param.isTenComplexSame() ? tenInnerMerge
					.getTenComplex().getCode().substring(0, 4) : "")
					+ (param.isTenNameSame() ? tenInnerMerge.getTenPtName()
							: "")
					+ (param.isTenUnitSame() ? tenInnerMerge.getTenUnit()
							.getCode() : "");
			DzscFourInnerMerge fourInnerMerge = null;
			if (tenInnerMerge.getDzscFourInnerMerge() == null) {
				if (hmFour.get(fourTemp) == null) {
					fourNo = dzscInnerMergeDao
							.findFourInnerMergeNo(materielType) + 1;
					fourInnerMerge = new DzscFourInnerMerge();
					fourInnerMerge.setFourSeqNum(Integer.valueOf(fourNo));
					fourInnerMerge.setFourComplex(tenInnerMerge.getTenComplex()
							.getCode().substring(0, 4));
					fourInnerMerge.setFourPtName(tenInnerMerge.getTenPtName());
					fourInnerMerge.setFourUnit(tenInnerMerge.getTenUnit());
					this.dzscInnerMergeDao
							.saveDzscFourInnerMerge(fourInnerMerge);
					hmFour.put(fourTemp, fourInnerMerge);
				}
				tenInnerMerge.setDzscFourInnerMerge(hmFour.get(fourTemp));
				this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
			}
			data.setDzscTenInnerMerge(hmTen.get(tenTemp));
			dzscInnerMergeDao.saveDzscInnerMergeData(data);
		}
	}

	/**
	 * 自动归并数据
	 * 
	 */
	public void dzscInnerMergeData(TempDzscAutoInnerMergeParam param) {
		innerMerge(MaterielType.FINISHED_PRODUCT, param);
		innerMerge(MaterielType.SEMI_FINISHED_PRODUCT, param);
		innerMerge(MaterielType.MATERIEL, param);
		innerMerge(MaterielType.MACHINE, param);
		innerMerge(MaterielType.REMAIN_MATERIEL, param);
		innerMerge(MaterielType.BAD_PRODUCT, param);
	}
}
