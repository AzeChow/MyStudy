/*
 * Created on 2004-12-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;

// 李扬更改程序
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.bcus.innermerge.entity.TempReverseBeforeData;
import com.bestway.bcus.innermerge.entity.TempReverseTenData;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class ReverseMerge {
	private CommonBaseCodeDao commonBaseCodeDao;

	private MaterialManageDao materialManageDao;

	/**
	 * @return Returns the commonBaseCodeDao.
	 */
	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	/**
	 * @param commonBaseCodeDao
	 *            The commonBaseCodeDao to set.
	 */
	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/**
	 * 根据反向归并前数据查询内部归并的数据
	 * 
	 * @param beforeData
	 * @return
	 */
	private InnerMergeData findInnerMergeDataByBeforeMergeData(
			ReverseMergeBeforeData beforeData) {
		return this.commonBaseCodeDao.findInnerMergeDataById(beforeData
				.getInnerMergeDataId());
	}

	/**
	 * 根据反向归并10码数据查询内部归并的数据
	 * 
	 * @param tenData
	 * @return
	 */
	private List findInnerMergeDataByTenMergeData(ReverseMergeTenData tenData) {
		List result = new ArrayList();
		List list = this.commonBaseCodeDao
				.findReverseMergeBeforeDataByTen(tenData);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			result
					.add(this
							.findInnerMergeDataByBeforeMergeData((ReverseMergeBeforeData) list
									.get(i)));
		}
		return result;
	}

	/**
	 * 根据反向归并4码数据查询内部归并的数据
	 * 
	 * @param tenData
	 * @return
	 */
	private List findInnerMergeDataByFourMergeData(ReverseMergeFourData fourData) {
		List result = new ArrayList();
		List list = this.commonBaseCodeDao
				.findReverseMergeTenDataByFour(fourData);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			result
					.addAll(this
							.findInnerMergeDataByTenMergeData((ReverseMergeTenData) list
									.get(i)));
		}
		return result;
	}

	/**
	 * 当撤消4码数据归并时，同时删除有关联的反向归并数据（删除反向归并10码和归并前数据）
	 * 
	 * @param innerMergeDatas
	 */
	public void deleteReverseMergeCausedByUndoFourInnerMerge(
			List innerMergeDatas) {
		List lsReverseBeforeMerge = this
				.findReverseMergeBeforeByInnerMerge(innerMergeDatas);
		if (lsReverseBeforeMerge.size() == 0) {
			return;
		}
		// 删除反向归并的归并前资料
		List lsReverseTenMerge = new ArrayList();
		int beforeDataSize = lsReverseBeforeMerge.size();
		for (int i = 0; i < beforeDataSize; i++) {
			ReverseMergeBeforeData beforeData = (ReverseMergeBeforeData) lsReverseBeforeMerge
					.get(i);

			if (!lsReverseTenMerge
					.contains(beforeData.getReverseMergeTenData())) {
				lsReverseTenMerge.add(beforeData.getReverseMergeTenData());
			}
			this.commonBaseCodeDao.deleteReverseMergeBeforeData(beforeData);
		}
		// 删除反向归并的10码资料
		List lsReverseFourMerge = new ArrayList();
		int tenDataSize = lsReverseTenMerge.size();
		for (int i = 0; i < tenDataSize; i++) {
			ReverseMergeTenData tenData = (ReverseMergeTenData) lsReverseTenMerge
					.get(i);
			if (!lsReverseFourMerge.contains(tenData.getReverseMergeFourData())) {
				lsReverseFourMerge.add(tenData.getReverseMergeFourData());
			}
			if (this.commonBaseCodeDao
					.findReverseMergeBeforeDataCountByTen(tenData) < 1) {
				this.commonBaseCodeDao.deleteReverseMergeTenData(tenData);

			}
		}
		// 删除反向归并的4码资料
		int innerMergeDataSize = lsReverseFourMerge.size();
		for (int i = 0; i < innerMergeDataSize; i++) {
			ReverseMergeFourData lsReverseFour = (ReverseMergeFourData) lsReverseFourMerge
					.get(i);
			if (this.commonBaseCodeDao
					.findReverseMergeTenDataCountByFour(lsReverseFour) < 1) {
				this.commonBaseCodeDao
						.deleteReverseMergeFourData(lsReverseFour);
			}
		}

	}

	private List findReverseMergeBeforeByInnerMerge(List innerMergeDatas) {
		List lsReverseBeforeMerge = new ArrayList();
		int innerMergeDataSize = innerMergeDatas.size();
		for (int i = 0; i < innerMergeDataSize; i++) {
			lsReverseBeforeMerge
					.addAll(this.commonBaseCodeDao
							.findReverseMergeBeforeDataByInnerMergeData((InnerMergeData) innerMergeDatas
									.get(i)));
		}
		return lsReverseBeforeMerge;
	}

	/**
	 * 当删除内部归并数据时同时删除和内部归并数据相关的反向归并数据
	 * 
	 * @param innerMergeDatas
	 */
	public void deleteReverseMergeCausedByDeleteInnerMerge(List innerMergeDatas) {
		List lsReverseBeforeMerge = this
				.findReverseMergeBeforeByInnerMerge(innerMergeDatas);
		if (lsReverseBeforeMerge.size() == 0) {
			return;
		}
		List lsReverseTenMerge = new ArrayList();
		List lsReverseFourMerge = new ArrayList();
		int beforeDataSize = lsReverseBeforeMerge.size();
		for (int i = 0; i < beforeDataSize; i++) {
			ReverseMergeBeforeData beforeData = (ReverseMergeBeforeData) lsReverseBeforeMerge
					.get(i);
			if (!lsReverseTenMerge
					.contains(beforeData.getReverseMergeTenData())) {
				lsReverseTenMerge.add(beforeData.getReverseMergeTenData());
			}
			if (!lsReverseFourMerge.contains(beforeData
					.getReverseMergeTenData().getReverseMergeFourData())) {
				lsReverseFourMerge.add(beforeData.getReverseMergeTenData()
						.getReverseMergeFourData());
			}
			this.commonBaseCodeDao.deleteReverseMergeBeforeData(beforeData);
		}
		int tenDataSize = lsReverseTenMerge.size();
		for (int i = 0; i < tenDataSize; i++) {
			ReverseMergeTenData tenData = (ReverseMergeTenData) lsReverseTenMerge
					.get(i);
			if (this.commonBaseCodeDao
					.findReverseMergeBeforeDataCountByTen(tenData) < 1) {
				this.commonBaseCodeDao.deleteReverseMergeTenData(tenData);
			}
		}
		int fourDataSize = lsReverseFourMerge.size();
		for (int i = 0; i < fourDataSize; i++) {
			ReverseMergeFourData fourData = (ReverseMergeFourData) lsReverseFourMerge
					.get(i);
			if (this.commonBaseCodeDao
					.findReverseMergeTenDataCountByFour(fourData) < 1) {
				this.commonBaseCodeDao.deleteReverseMergeFourData(fourData);
			}
		}
	}

	/**
	 * 根据物料类别查找反向归并4码数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List findReverseMergeFourDataByType(String materielType) {
		return this.getCommonBaseCodeDao().findReverseMergeFourDataByType(
				materielType);
	}

	/**
	 * 保存反向归并4码数据 同时更新此相关的内部归并数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeFourData(ReverseMergeFourData fourData) {
		this.getCommonBaseCodeDao().saveReverseMergeFourData(fourData);
		List list = this.findInnerMergeDataByFourMergeData(fourData);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			InnerMergeData innerMergeData = (InnerMergeData) list.get(i);
			innerMergeData.setHsFourCode(fourData.getHsFourCode());
			innerMergeData.setHsFourMaterielName(fourData
					.getHsFourMaterielName());
			innerMergeData.setHsFourNo(fourData.getHsFourNo());
			this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);
		}
	}

	/**
	 * 删除反向归并4码数据并且同时删除10码数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeFourData(ReverseMergeFourData fourData) {
		List list = this.getCommonBaseCodeDao().findReverseMergeTenDataByFour(
				fourData);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			this.deleteReverseMergeTenData((ReverseMergeTenData) list.get(i));
		}
		this.getCommonBaseCodeDao().deleteReverseMergeFourData(fourData);
	}

	/**
	 * 删除反向归并4码数据并且同时删除10码数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeFourData(List lsFourData) {
		int size = lsFourData.size();
		for (int i = 0; i < size; i++) {
			this.deleteReverseMergeFourData((ReverseMergeFourData) lsFourData
					.get(i));
		}
	}

	/**
	 * 根据凡响归并4码数据查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByFour(ReverseMergeFourData fourData) {
		return this.getCommonBaseCodeDao().findReverseMergeTenDataByFour(
				fourData);
	}

	/**
	 * 根据物料类别查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByType(String materielType) {
		return this.getCommonBaseCodeDao().findReverseMergeTenDataByType(
				materielType);
	}

	/**
	 * 保存反向归并10码数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeTenData(ReverseMergeTenData tenData) {
		this.getCommonBaseCodeDao().saveReverseMergeTenData(tenData);
		List list = this.findInnerMergeDataByTenMergeData(tenData);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			InnerMergeData innerMergeData = (InnerMergeData) list.get(i);
			innerMergeData.setHsAfterComplex(tenData.getHsAfterComplex());
			innerMergeData.setHsAfterTenMemoNo(tenData.getHsAfterTenMemoNo());
			innerMergeData.setHsAfterMaterielTenName(tenData
					.getHsAfterMaterielTenName());
			innerMergeData.setHsAfterMaterielTenSpec(tenData
					.getHsAfterMaterielTenSpec());
			innerMergeData.setHsAfterLegalUnit(tenData.getHsAfterLegalUnit());
			innerMergeData.setHsAfterSecondLegalUnit(tenData
					.getHsAfterSecondLegalUnit());
			innerMergeData.setHsAfterMemoUnit(tenData.getHsAfterMemoUnit());
			this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);
		}
	}

	/**
	 * 删除反向归并10码数据 同时删除或者更新此10码所关联的归并前数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeTenData(ReverseMergeTenData tenData) {
		List list = this.getCommonBaseCodeDao()
				.findReverseMergeBeforeDataByTen(tenData);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			ReverseMergeBeforeData beforeData = (ReverseMergeBeforeData) list
					.get(i);
			InnerMergeData innerMergeData = this
					.findInnerMergeDataByBeforeMergeData(beforeData);
			/**
			 * 如果是连接上的则把内部归并数据的10码和4码信息置空
			 */
			if (beforeData.getConcatFlag() != null) {
				if (beforeData.getConcatFlag().intValue() == ReverseMergeBeforeData.TEN_CONCAT_FOUR) {
					innerMergeData.setHsFourCode(null);
					innerMergeData.setHsFourMaterielName(null);
					innerMergeData.setHsFourNo(null);
				} else if (beforeData.getConcatFlag().intValue() == ReverseMergeBeforeData.BEFORE_CONCAT_TEN) {
					innerMergeData.setHsAfterComplex(null);
					innerMergeData.setHsAfterLegalUnit(null);
					innerMergeData.setHsAfterMaterielTenName(null);
					innerMergeData.setHsAfterMaterielTenSpec(null);
					innerMergeData.setHsAfterMemoUnit(null);
					innerMergeData.setHsAfterSecondLegalUnit(null);
					innerMergeData.setHsAfterTenMemoNo(null);
					innerMergeData.setHsFourCode(null);
					innerMergeData.setHsFourMaterielName(null);
					innerMergeData.setHsFourNo(null);
				}
				this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);
			} else if (beforeData.getConcatFlag() == null) {
				Materiel obj = innerMergeData.getMateriel();
				obj.setIsNewMateriel(new Boolean(true));// 是否新增物料主档
				materialManageDao.saveMateriel(obj);
				this.getCommonBaseCodeDao()
						.deleteInnerMergeData(innerMergeData);
			}
			this.getCommonBaseCodeDao()
					.deleteReverseMergeBeforeData(beforeData);
		}
		this.getCommonBaseCodeDao().deleteReverseMergeTenData(tenData);
	}

	/**
	 * 删除反向归并10码数据 同时删除或者更新此10码所关联的归并前数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeTenData(List lsTenData) {
		int size = lsTenData.size();
		for (int i = 0; i < size; i++) {
			this.deleteReverseMergeTenData((ReverseMergeTenData) lsTenData
					.get(i));
		}
	}

	/**
	 * 根据反向归并后10码数据查找反向归并数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeBeforeDataByTen(ReverseMergeTenData tenData) {
		return this.getCommonBaseCodeDao().findReverseMergeBeforeDataByTen(
				tenData);
	}

	/**
	 * 保存反向归并前数据 同时更新此关联的物料数据和内部归并数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeBeforeData(ReverseMergeBeforeData beforeData) {
		this.getCommonBaseCodeDao().saveReverseMergeBeforeData(beforeData);
		InnerMergeData innerMergeData = this
				.findInnerMergeDataByBeforeMergeData(beforeData);
		innerMergeData.setHsBeforeEnterpriseUnit(beforeData
				.getHsBeforeEnterpriseUnit());
		innerMergeData.setHsBeforeLegalUnit(innerMergeData
				.getHsBeforeLegalUnit());
		innerMergeData.setMateriel(beforeData.getMateriel());
		Materiel materiel = this.materialManageDao.findMaterielById(beforeData
				.getMateriel().getId());
		if (materiel != null) {
			materiel.setFactoryName(beforeData.getMateriel().getFactoryName());
			materiel.setFactorySpec(beforeData.getMateriel().getFactorySpec());
			materiel.setPtNetWeight(beforeData.getMateriel().getPtNetWeight());
			materiel.setPtPrice(beforeData.getMateriel().getPtPrice());
			materiel.setUnitConvert(beforeData.getMateriel().getUnitConvert());
			this.materialManageDao.saveMateriel(materiel);
		}
		this.getCommonBaseCodeDao().saveInnerMergeData(innerMergeData);
	}

	/**
	 * 删除反向归并前数据 并同时要删除由此产生的内部归并数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeBeforeData(ReverseMergeBeforeData beforeData) {
		InnerMergeData innerMergeData = this
				.findInnerMergeDataByBeforeMergeData(beforeData);
		/**
		 * 如果是连接上的则把内部归并数据的4码信息置空
		 */
		if (beforeData.getConcatFlag() != null
				&& (beforeData.getConcatFlag().intValue() == ReverseMergeBeforeData.TEN_CONCAT_FOUR || beforeData
						.getConcatFlag().intValue() == ReverseMergeBeforeData.BEFORE_CONCAT_TEN)) {
			innerMergeData.setHsAfterComplex(null);
			innerMergeData.setHsAfterLegalUnit(null);
			innerMergeData.setHsAfterMaterielTenName(null);
			innerMergeData.setHsAfterMaterielTenSpec(null);
			innerMergeData.setHsAfterMemoUnit(null);
			innerMergeData.setHsAfterSecondLegalUnit(null);
			innerMergeData.setHsAfterTenMemoNo(null);

			innerMergeData.setHsFourCode(null);
			innerMergeData.setHsFourMaterielName(null);
			innerMergeData.setHsFourNo(null);
			this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);
		} else if (beforeData.getConcatFlag() == null) {
			Materiel obj = innerMergeData.getMateriel();
			obj.setIsNewMateriel(new Boolean(true));// 是否新增物料主档
			materialManageDao.saveMateriel(obj);
			this.getCommonBaseCodeDao().deleteInnerMergeData(innerMergeData);
		}
		this.getCommonBaseCodeDao().deleteReverseMergeBeforeData(beforeData);
	}

	/**
	 * 删除反向归并前数据 并同时要删除由此产生的内部归并数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeBeforeData(List lsBeforeData) {
		int size = lsBeforeData.size();
		for (int i = 0; i < size; i++) {
			this
					.deleteReverseMergeBeforeData((ReverseMergeBeforeData) lsBeforeData
							.get(i));
		}
	}

	/**
	 * 新增反向归并前数据 同时生成新的内部归并数据
	 * 
	 * @param list
	 */
	public void addReverseMergeBeforeData(List list, ReverseMergeTenData tenData) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Materiel materiel = (Materiel) list.get(i);
			/**
			 * 重新抓取mateiel，取得最新版本，防止实体版本差异而保存错误
			 */
			materiel = this.materialManageDao
					.findMaterielById(materiel.getId());
			materiel.setComplex(tenData.getHsAfterComplex());
			materiel.setIsNewMateriel(new Boolean(false));
			this.materialManageDao.saveMateriel(materiel);
			InnerMergeData innerMergeData = new InnerMergeData();
			innerMergeData.setCompany(tenData.getReverseMergeFourData()
					.getCompany());
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
			innerMergeData.setHsAfterTenMemoNo(tenData.getHsAfterTenMemoNo());
			innerMergeData.setHsAfterComplex(tenData.getHsAfterComplex());
			innerMergeData.setHsAfterLegalUnit(tenData.getHsAfterLegalUnit());
			innerMergeData.setHsAfterMemoUnit(tenData.getHsAfterMemoUnit());
			innerMergeData.setHsAfterSecondLegalUnit(tenData
					.getHsAfterSecondLegalUnit());
			innerMergeData.setHsAfterMaterielTenName(tenData
					.getHsAfterMaterielTenName());
			innerMergeData.setHsAfterMaterielTenSpec(tenData
					.getHsAfterMaterielTenSpec());
			innerMergeData.setHsFourCode(tenData.getReverseMergeFourData()
					.getHsFourCode());
			innerMergeData.setHsFourMaterielName(tenData
					.getReverseMergeFourData().getHsFourMaterielName());
			innerMergeData.setHsFourNo(tenData.getReverseMergeFourData()
					.getHsFourNo());
			this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);

			ReverseMergeBeforeData reverseMergeBeforeData = new ReverseMergeBeforeData();
			reverseMergeBeforeData.setInnerMergeDataId(innerMergeData.getId());
			reverseMergeBeforeData.setMateriel(materiel);
			reverseMergeBeforeData.setReverseMergeTenData(tenData);
			this.commonBaseCodeDao
					.saveReverseMergeBeforeData(reverseMergeBeforeData);
		}
	}

	/**
	 * 把归并关系中的第三层资料插入ReverseMergeFourData类中
	 */
	public void addInnerMergeDataToReverseMergeFourData(String imrType) {
		Map<Integer, ReverseMergeFourData> innerMap = new HashMap<Integer, ReverseMergeFourData>();
		// List inner = commonBaseCodeDao.findDistinctFourceInner(imrType);
		// List reverse = commonBaseCodeDao
		// .findReverseMergeFourDataByType(imrType);
		List inner = commonBaseCodeDao
				.findInnerMergeDataExistReverseMergeFourData(imrType);
		// for (int i = 0; i < reverse.size(); i++) {
		// ReverseMergeFourData reverseData = (ReverseMergeFourData) reverse
		// .get(i);
		// Integer key = reverseData.getHsFourNo();
		// innerMap.put(key, reverseData);
		// }

		for (int i = 0; i < inner.size(); i++) {
			Object[] obj = (Object[]) inner.get(i);
			// Integer key = (Integer) obj[0];
			// 当归并关系InnerMergeData中的4码与ReverseMergeFourData4码key不一致插入值
			// if (innerMap.get(key) == null) {
			ReverseMergeFourData data = new ReverseMergeFourData();
			data.setCompany((Company) CommonUtils.getCompany());
			data.setHsFourNo((Integer) obj[0]);
			data.setHsFourCode((String) obj[1]);
			data.setHsFourMaterielName((String) obj[2]);
			data.setImrType(imrType);
			data.setCreateDate(new Date());
			commonBaseCodeDao.saveReverseMergeFourData(data);
			// }
			// else {
			// ReverseMergeFourData data = (ReverseMergeFourData) innerMap
			// .get(key);
			// data.setHsFourNo((Integer) obj[0]);
			// data.setHsFourCode((String) obj[1]);
			// data.setHsFourMaterielName((String) obj[2]);
			// data.setCreateDate(new Date());
			// commonBaseCodeDao.saveReverseMergeFourData(data);
			// }

		}
	}

	/**
	 * 把归并关系中的第二层资料插入ReverseMergeTenData类中
	 */
	public void addInnerMergeDataToReverseMergeTenData(String imrType) {
		Map<Integer, Integer> innerMap = new HashMap<Integer, Integer>();
		// List inner = commonBaseCodeDao.findDistinctTenInner(imrType);
		// List reverse =
		// commonBaseCodeDao.findReverseMergeTenDataByType(imrType);
		// for (int i = 0; i < reverse.size(); i++) {
		// ReverseMergeTenData reverseTenData = (ReverseMergeTenData) reverse
		// .get(i);
		// Integer key = reverseTenData.getHsAfterTenMemoNo();
		// innerMap.put(key, key);
		// }
		List inner = commonBaseCodeDao
				.findInnerMergeDataExistReverseMergeTenData(imrType);
		for (int i = 0; i < inner.size(); i++) {
			Object[] obj = (Object[]) inner.get(i);
			// Integer key = (Integer) obj[0];
			ReverseMergeFourData reverseMergeFourData = commonBaseCodeDao
					.findReverseMergeFourDataByHsFourNo((Integer) obj[5],
							(String) obj[6], (String) obj[7], imrType);
			// 当归并关系InnerMergeData中的10码与ReverseMergeTenData码key不一致插入值
			if (reverseMergeFourData == null) {
				continue;
			}
			// if (innerMap.get(key) == null) {
			ReverseMergeTenData reverseMergeTenData = new ReverseMergeTenData();
			reverseMergeTenData.setCompany((Company) CommonUtils.getCompany());
			reverseMergeTenData.setHsAfterTenMemoNo((Integer) obj[0]);
			reverseMergeTenData.setReverseMergeFourData(reverseMergeFourData);
			Complex c = (Complex) materialManageDao
					.findComplexByCode((String) obj[1]);
			reverseMergeTenData.setHsAfterComplex(c);
			if (c != null) {
				reverseMergeTenData.setHsAfterLegalUnit(c.getFirstUnit());
				reverseMergeTenData
						.setHsAfterSecondLegalUnit(c.getSecondUnit());
			}
			reverseMergeTenData.setHsAfterMaterielTenName((String) obj[2]);
			reverseMergeTenData.setHsAfterMaterielTenSpec((String) obj[3]);
			reverseMergeTenData.setHsAfterMemoUnit((Unit) materialManageDao
					.findUnitByNameFromUnit((String) obj[4]));
			reverseMergeTenData.setCreateDate(new Date());
			commonBaseCodeDao.saveReverseMergeTenData(reverseMergeTenData);
			// }
			// else {
			// ReverseMergeTenData reverseMergeTenData = (ReverseMergeTenData)
			// innerMap
			// .get(key);
			// reverseMergeTenData
			// .setReverseMergeFourData(reverseMergeFourData);
			// Complex c = (Complex) materialManageDao
			// .findComplexByCode((String) obj[1]);
			// reverseMergeTenData.setHsAfterComplex(c);
			// if (c != null) {
			// reverseMergeTenData.setHsAfterLegalUnit(c.getFirstUnit());
			// reverseMergeTenData.setHsAfterSecondLegalUnit(c
			// .getSecondUnit());
			// }
			// reverseMergeTenData.setHsAfterMaterielTenName((String) obj[2]);
			// reverseMergeTenData.setHsAfterMaterielTenSpec((String) obj[3]);
			// reverseMergeTenData.setHsAfterMemoUnit((Unit) materialManageDao
			// .findUnitByNameFromUnit((String) obj[4]));
			// reverseMergeTenData.setCreateDate(new Date());
			// commonBaseCodeDao.saveReverseMergeTenData(reverseMergeTenData);
			// }

		}

	}

	/**
	 * 把归并关系中的第一层资料插入ReverseMergeBeforeData类中
	 */
	public void addInnerMergeDataToReverseMergeBeforeData(String imrType) {
		Map<Materiel, ReverseMergeBeforeData> innerMap = new HashMap<Materiel, ReverseMergeBeforeData>();
		// List inner = commonBaseCodeDao
		// .findInnerMergeDataByTypeMateriel(imrType);
		// List reverse = commonBaseCodeDao
		// .findReverseMergeBeforeDataByMateriel(imrType);
		// for (int i = 0; i < reverse.size(); i++) {
		// ReverseMergeBeforeData reverseBeforeData = (ReverseMergeBeforeData)
		// reverse
		// .get(i);
		// if (reverseBeforeData.getMateriel() == null) {
		// continue;
		// }
		// Materiel key = reverseBeforeData.getMateriel();
		// innerMap.put(key, reverseBeforeData);
		// }
		List inner = commonBaseCodeDao
				.findInnerMergeDataExistReverseMergeBeforeData(imrType);
		for (int i = 0; i < inner.size(); i++) {
			Object[] obj = (Object[]) inner.get(i);
			// Materiel key = (Materiel) obj[1];
			ReverseMergeTenData tenData = commonBaseCodeDao
					.findReverseMergeTenDataByHsAfterTenMemoNo(
							(Integer) obj[2], imrType);
			// 当归并关系InnerMergeData中的物料码与ReverseMergeBeforeData码key不一致插入值
			// if (innerMap.get(key) == null) {
			if (tenData == null) {
				continue;
			}
			ReverseMergeBeforeData reverseMergeBeforeData = new ReverseMergeBeforeData();
			reverseMergeBeforeData.setCompany((Company) CommonUtils
					.getCompany());
			reverseMergeBeforeData.setInnerMergeDataId((String) obj[0]);
			reverseMergeBeforeData.setMateriel((Materiel) obj[1]);
			reverseMergeBeforeData.setReverseMergeTenData(tenData);
			reverseMergeBeforeData.setCreateDate(new Date());
			this.commonBaseCodeDao
					.saveReverseMergeBeforeData(reverseMergeBeforeData);
			// }
			// else {
			// ReverseMergeBeforeData reverseMergeBeforeData =
			// (ReverseMergeBeforeData) innerMap
			// .get(key);
			// reverseMergeBeforeData.setInnerMergeDataId((String) obj[0]);
			// reverseMergeBeforeData.setMateriel((Materiel) obj[1]);
			// reverseMergeBeforeData.setReverseMergeTenData(tenData);
			// reverseMergeBeforeData.setCreateDate(new Date());
			// commonBaseCodeDao
			// .saveReverseMergeBeforeData(reverseMergeBeforeData);
			// }

		}
	}

	/**
	 * 将内部归并10码数据和反向归并4码数据对接起来
	 * 
	 */
	public void concatInnerTenAndReverseFour(List innerTenDatas,
			ReverseMergeFourData fourData) {
		int size = innerTenDatas.size();
		for (int i = 0; i < size; i++) {
			TempReverseTenData tenData = (TempReverseTenData) innerTenDatas
					.get(i);
			ReverseMergeTenData reverseMergeTenData = new ReverseMergeTenData();
			reverseMergeTenData.setReverseMergeFourData(fourData);
			reverseMergeTenData.setHsAfterTenMemoNo(tenData
					.getHsAfterTenMemoNo());
			reverseMergeTenData.setHsAfterComplex(tenData.getHsAfterComplex());
			reverseMergeTenData
					.setHsAfterMemoUnit(tenData.getHsAfterMemoUnit());
			reverseMergeTenData.setHsAfterLegalUnit(tenData
					.getHsAfterLegalUnit());
			reverseMergeTenData.setHsAfterSecondLegalUnit(tenData
					.getHsAfterSecondLegalUnit());
			this.commonBaseCodeDao.saveReverseMergeTenData(reverseMergeTenData);
			List list = this.commonBaseCodeDao
					.findInnerMergeDataByTypeAndTenMemoNo(
							fourData.getImrType(), tenData
									.getHsAfterTenMemoNo());
			int lsize = list.size();
			for (int j = 0; j < lsize; j++) {
				InnerMergeData innerMergeData = (InnerMergeData) list.get(j);
				innerMergeData.setHsFourCode(fourData.getHsFourCode());
				innerMergeData.setHsFourMaterielName(fourData
						.getHsFourMaterielName());
				innerMergeData.setHsFourNo(fourData.getHsFourNo());
				this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);
				ReverseMergeBeforeData reverseMergeBeforeData = new ReverseMergeBeforeData();
				reverseMergeBeforeData
						.setMateriel(innerMergeData.getMateriel());
				reverseMergeBeforeData.setHsBeforeEnterpriseUnit(innerMergeData
						.getHsBeforeEnterpriseUnit());
				reverseMergeBeforeData.setHsBeforeLegalUnit(innerMergeData
						.getHsBeforeLegalUnit());
				reverseMergeBeforeData
						.setReverseMergeTenData(reverseMergeTenData);
				reverseMergeBeforeData.setInnerMergeDataId(innerMergeData
						.getId());
				reverseMergeBeforeData.setConcatFlag(new Integer(
						ReverseMergeBeforeData.TEN_CONCAT_FOUR));
				this.commonBaseCodeDao
						.saveReverseMergeBeforeData(reverseMergeBeforeData);
			}
		}
	}

	/**
	 * 将内部归并前数据和反向归并10码数据对接起来
	 * 
	 */
	public void concatInnerBeforeAndReverseTen(List innerBeforeDatas,
			ReverseMergeTenData tenData) {
		int size = innerBeforeDatas.size();
		for (int i = 0; i < size; i++) {
			TempReverseBeforeData tempBeforeData = (TempReverseBeforeData) innerBeforeDatas
					.get(i);
			InnerMergeData innerMergeData = this.commonBaseCodeDao
					.findInnerMergeDataById(tempBeforeData.getId());
			innerMergeData.setHsAfterTenMemoNo(tenData.getHsAfterTenMemoNo());
			innerMergeData.setHsAfterComplex(tenData.getHsAfterComplex());
			innerMergeData.setHsAfterMemoUnit(tenData.getHsAfterMemoUnit());
			innerMergeData.setHsAfterLegalUnit(tenData.getHsAfterLegalUnit());
			innerMergeData.setHsAfterSecondLegalUnit(tenData
					.getHsAfterSecondLegalUnit());

			innerMergeData.setHsFourCode(tenData.getReverseMergeFourData()
					.getHsFourCode());
			innerMergeData.setHsFourMaterielName(tenData
					.getReverseMergeFourData().getHsFourMaterielName());
			innerMergeData.setHsFourNo(tenData.getReverseMergeFourData()
					.getHsFourNo());
			this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);
			ReverseMergeBeforeData reverseMergeBeforeData = new ReverseMergeBeforeData();
			reverseMergeBeforeData.setMateriel(innerMergeData.getMateriel());
			reverseMergeBeforeData.setHsBeforeEnterpriseUnit(innerMergeData
					.getHsBeforeEnterpriseUnit());
			reverseMergeBeforeData.setHsBeforeLegalUnit(innerMergeData
					.getHsBeforeLegalUnit());
			reverseMergeBeforeData.setReverseMergeTenData(tenData);
			reverseMergeBeforeData.setInnerMergeDataId(innerMergeData.getId());
			reverseMergeBeforeData.setConcatFlag(new Integer(
					ReverseMergeBeforeData.BEFORE_CONCAT_TEN));
			this.commonBaseCodeDao
					.saveReverseMergeBeforeData(reverseMergeBeforeData);
		}
	}

	/**
	 * 查询未进行4码归并的内部归并数据
	 * 
	 * @param fourData
	 * @param materielType
	 * @return
	 */
	public List findTenDataNotFourMerge(ReverseMergeFourData fourData,
			String materielType) {
		List list = this.commonBaseCodeDao.findTenDataNotFourMerge(fourData,
				materielType);
		int size = list.size();
		Object[] objs = null;
		List result = new ArrayList();
		for (int i = 0; i < size; i++) {
			objs = (Object[]) list.get(i);
			TempReverseTenData tenData = new TempReverseTenData();
			tenData.setHsAfterTenMemoNo((Integer) objs[0]);
			tenData.setHsAfterComplex((Complex) objs[1]);
			tenData.setHsAfterMaterielTenName((String) objs[2]);
			tenData.setHsAfterMaterielTenSpec((String) objs[3]);
			tenData.setHsAfterMemoUnit((Unit) objs[4]);
			tenData.setHsAfterLegalUnit((Unit) objs[5]);
			tenData.setHsAfterSecondLegalUnit((Unit) objs[6]);
			result.add(tenData);
		}
		return result;
	}

	/**
	 * 查询未进行10码归并的内部归并数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List findBeforeDataNotTenMerge(String materielType) {
		List list = this.commonBaseCodeDao
				.findBeforeDataNotTenMerge(materielType);
		int size = list.size();
		Object[] objs = null;
		List result = new ArrayList();
		for (int i = 0; i < size; i++) {
			objs = (Object[]) list.get(i);
			TempReverseBeforeData beforeData = new TempReverseBeforeData();
			beforeData.setId((String) objs[0]);
			beforeData.setMateriel((Materiel) objs[1]);
			beforeData.setHsBeforeLegalUnit((Unit) objs[2]);
			beforeData.setHsBeforeEnterpriseUnit((CalUnit) objs[3]);
			result.add(beforeData);
		}
		return result;
	}

	/**
	 * 查询不在内部归并中存在的物料基本资料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterielNotInInnerMerge(String materielType) {
		return this.commonBaseCodeDao.findMaterielNotInInnerMerge(materielType);
	}

	/**
	 * 返回反向归并10码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	public int findMaxTenReverseMergeNo(String type) {
		int maxInnerMergeTenNo = this.commonBaseCodeDao
				.findTenInnerMergeNo(type);
		int maxReverseMergeTenNo = this.commonBaseCodeDao
				.findMaxReverseMergeTenNo(type);
		if (maxInnerMergeTenNo > maxReverseMergeTenNo) {
			return maxInnerMergeTenNo;
		} else {
			return maxReverseMergeTenNo;
		}
	}

	/**
	 * 返回反向归并4码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	public int findMaxFourReverseMergeNo(String type) {
		int maxInnerMergeFourNo = this.commonBaseCodeDao
				.findFourInnerMergeNo(type);
		int maxReverseMergeFourNo = this.commonBaseCodeDao
				.findMaxReverseMergeFourNo(type);
		if (maxInnerMergeFourNo > maxReverseMergeFourNo) {
			return maxInnerMergeFourNo;
		} else {
			return maxReverseMergeFourNo;
		}
	}

	/**
	 * 判断四码是否有其它十码对应
	 * 
	 * @param selectedInnerMergeData
	 * @return
	 */
	public boolean findInnerMergeDataByFilter(List selectedInnerMergeData) {
		String materielType = "";
		int size = selectedInnerMergeData.size();
		if (size > 0) {
			materielType = ((InnerMergeData) selectedInnerMergeData.get(0))
					.getImrType();
		}
		Map<Integer, InnerMergeData> map4 = new HashMap<Integer, InnerMergeData>();
		//
		// 过滤相同的四码
		//
		for (int i = 0; i < size; i++) {
			InnerMergeData innerMergeData = (InnerMergeData) selectedInnerMergeData
					.get(i);
			if (innerMergeData.getHsFourNo() == null) {
				continue;
			}
			map4.put(innerMergeData.getHsFourNo(), innerMergeData);
		}
		//
		// 初始化四码对应的十码记录
		//
		Collection<InnerMergeData> collection = map4.values();
		if (collection.isEmpty()) {
			return true;
		}
		Iterator<InnerMergeData> iterator = collection.iterator();
		while (iterator.hasNext()) {
			InnerMergeData data = iterator.next();
			if (data.getHsFourNo() == null) {
				continue;
			}
			List<InnerMergeData> tenInnerMergeData = new ArrayList<InnerMergeData>();
			for (int i = 0; i < size; i++) {
				InnerMergeData innerMergeData = (InnerMergeData) selectedInnerMergeData
						.get(i);
				if (innerMergeData.getHsFourNo() == null
						|| innerMergeData.getHsAfterTenMemoNo() == null) {
					continue;
				}
				if (innerMergeData.getHsFourNo().intValue() == data
						.getHsFourNo().intValue()) {
					tenInnerMergeData.add(innerMergeData);
				}
			}
			int count = this.commonBaseCodeDao.findInnerMergeByFourNoCount(
					materielType, data.getHsFourNo());
			System.out
					.println(" findInnerMergeByFourNoCount count --------------------------  "
							+ count);
			//
			// 如果选中的十码 <= 数据库的四码对应的十码数据
			//
			if (count <= tenInnerMergeData.size()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断有哪些内部归并数据是否做了经营范围的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findInnerMergeDataInEmsEdiTr(List lsInnerMergeData) {
		int size = lsInnerMergeData.size();
		Map<Integer, InnerMergeData> map4 = new HashMap<Integer, InnerMergeData>();
		//
		// 过滤相同的四码
		//
		for (int i = 0; i < size; i++) {
			InnerMergeData innerMergeData = (InnerMergeData) lsInnerMergeData
					.get(i);
			if (innerMergeData.getHsFourNo() == null) {
				continue;
			}
			map4.put(innerMergeData.getHsFourNo(), innerMergeData);
		}
		Collection<InnerMergeData> collection = map4.values();
		Iterator<InnerMergeData> iterator = collection.iterator();
		List<InnerMergeData> result = new ArrayList<InnerMergeData>();
		while (iterator.hasNext()) {
			InnerMergeData innerMergeData = (InnerMergeData) iterator.next();
			if (this.commonBaseCodeDao
					.findWhetherInnerMergeInEmsEdiTr(innerMergeData)) {
				result.add(innerMergeData);
			}
		}
		return result;
	}

	/**
	 * 检查反向归并4码相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseFourDataPutOnRecord(
			ReverseMergeFourData fourData) {
		List list = this.findInnerMergeDataInEmsEdiTr(this
				.findInnerMergeDataByFourMergeData(fourData));
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查反向归并10码相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseTenDataPutOnRecord(
			ReverseMergeTenData tenData) {
		List list = this.findInnerMergeInEmsEdiMergeAfter(this
				.findInnerMergeDataByTenMergeData(tenData));
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查反向归并前相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseBeforeDataPutOnRecord(
			ReverseMergeBeforeData beforeData) {
		List lsTemp = new ArrayList();
		lsTemp.add(this.findInnerMergeDataByBeforeMergeData(beforeData));
		List list = this.findInnerMergeInEmsEdiMergeBefore(lsTemp);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查反向归并4码相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseFourDataPutOnRecord(List lsFourData) {
		int size = lsFourData.size();
		for (int i = 0; i < size; i++) {
			if (this
					.checkWhetherReverseFourDataPutOnRecord((ReverseMergeFourData) lsFourData
							.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查反向归并10码相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseTenDataPutOnRecord(List lsTenData) {
		int size = lsTenData.size();
		for (int i = 0; i < size; i++) {
			if (this
					.checkWhetherReverseTenDataPutOnRecord((ReverseMergeTenData) lsTenData
							.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查反向归并前相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseBeforeDataPutOnRecord(List lsBeforeData) {
		int size = lsBeforeData.size();
		for (int i = 0; i < size; i++) {
			if (this
					.checkWhetherReverseBeforeDataPutOnRecord((ReverseMergeBeforeData) lsBeforeData
							.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断内部归并后数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findInnerMergeInEmsEdiMergeAfter(List lsInnerMergeData) {
		int size = lsInnerMergeData.size();
		List result = new ArrayList();
		for (int i = 0; i < size; i++) {
			InnerMergeData innerMergeData = (InnerMergeData) lsInnerMergeData
					.get(i);
			if (this.commonBaseCodeDao
					.findWhetherInnerMergeInEmsEdiMergeAfter(innerMergeData)) {
				result.add(innerMergeData);
			}
		}
		return result;
	}

	/**
	 * 判断内部归并后数据是否做了电子帐册的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findWhetherInnerMergeInEmsHeadH2k(List lsInnerMergeData) {
		int size = lsInnerMergeData.size();
		List result = new ArrayList();
		for (int i = 0; i < size; i++) {
			InnerMergeData innerMergeData = (InnerMergeData) lsInnerMergeData
					.get(i);
			if (this.commonBaseCodeDao
					.findWhetherInnerMergeInEmsHeadH2k(innerMergeData)) {
				result.add(innerMergeData);
			}
		}
		return result;
	}

	/**
	 * 判断反向归并后数据是否做了电子帐册的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findWhetherReverseMergeInEmsHeadH2k(List lsInnerMergeData) {
		int size = lsInnerMergeData.size();
		List result = new ArrayList();
		for (int i = 0; i < size; i++) {
			ReverseMergeTenData reverseMergeData = (ReverseMergeTenData) lsInnerMergeData
					.get(i);
			if (this.commonBaseCodeDao
					.findWhetherReverseMergeInEmsHeadH2k(reverseMergeData)) {
				result.add(reverseMergeData);
			}
		}
		return result;
	}

	/**
	 * 判断内部归并前数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findInnerMergeInEmsEdiMergeBefore(List lsInnerMergeData) {
		int size = lsInnerMergeData.size();
		List result = new ArrayList();
		for (int i = 0; i < size; i++) {
			InnerMergeData innerMergeData = (InnerMergeData) lsInnerMergeData
					.get(i);
			if (this.commonBaseCodeDao
					.findWhetherInnerMergeInEmsEdiMergeBefore(innerMergeData)) {
				result.add(innerMergeData);
			}
		}
		return result;
	}
}