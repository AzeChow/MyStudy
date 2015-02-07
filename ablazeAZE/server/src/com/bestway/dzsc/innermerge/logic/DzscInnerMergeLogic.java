package com.bestway.dzsc.innermerge.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscDelcareType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.dzsc.dzscmanage.entity.TempDzscProductVersionInfo;
import com.bestway.dzsc.innermerge.dao.DzscInnerMergeDao;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomDetail;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.innermerge.entity.TempInnerMergeApplySelectParam;
import com.bestway.dzsc.materialapply.dao.MaterialApplyDao;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.dzsc.message.logic.DzscMessageLogic;

public class DzscInnerMergeLogic {
	private MaterialManageDao materialManageDao;

	private DzscInnerMergeDao dzscInnerMergeDao = null;

	private DzscMessageLogic dzscMessageLogic = null;

	private MaterialApplyDao materialApplyDao = null;

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	public DzscInnerMergeDao getDzscInnerMergeDao() {
		return dzscInnerMergeDao;
	}

	public void setDzscInnerMergeDao(DzscInnerMergeDao dzscInnerMergeDao) {
		this.dzscInnerMergeDao = dzscInnerMergeDao;
	}

	public DzscMessageLogic getDzscMessageLogic() {
		return dzscMessageLogic;
	}

	public void setDzscMessageLogic(DzscMessageLogic dzscExportMessageLogic) {
		this.dzscMessageLogic = dzscExportMessageLogic;
	}

	public MaterialApplyDao getMaterialApplyDao() {
		return materialApplyDao;
	}

	public void setMaterialApplyDao(MaterialApplyDao materialApplyDao) {
		this.materialApplyDao = materialApplyDao;
	}

	/**
	 * 抓取全部归并记录（不包含变更的）
	 * 
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergeDataByType(String type, int index, int length,
			String property, Object value, boolean isLike) {
		return this.dzscInnerMergeDao.findInnerMergeDataByType(type, index,
				length, property, value, isLike);
	}

	public void saveDzscInnerMergeDataInFactoryAndCustoms(
			DzscInnerMergeData dzscInnerMergeData) {
		// Integer seq =
		// dzscInnerMergeData.getDzscTenInnerMerge().getTenSeqNum();
		// String type = dzscInnerMergeData.getImrType();
		// List list = this.dzscInnerMergeDao.findDzscInnerMergeDataByTenSeqNum(
		// type, seq);
		// for (int i = 0; i < list.size(); i++) {
		// DzscInnerMergeData dimd = (DzscInnerMergeData) list.get(i);
		// dimd.setTenComplex(dzscInnerMergeData.getDzscTenInnerMerge()
		// .getTenComplex());
		// dimd.setTenPtName(dzscInnerMergeData.getDzscTenInnerMerge()
		// .getTenPtName());
		// dimd.setTenPtSpec(dzscInnerMergeData.getDzscTenInnerMerge()
		// .getTenPtSpec());
		// dimd.setTenUnit(dzscInnerMergeData.getDzscTenInnerMerge()
		// .getTenUnit());
		// this.dzscInnerMergeDao.saveDzscInnerMergeData(dimd);
		// }
		this.dzscInnerMergeDao.saveDzscInnerMergeData(dzscInnerMergeData);
	}

	/**
	 * 保存内部归并10码资料
	 * 
	 * @param list
	 */
	public void saveDzscTenInnerMerge(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscTenInnerMerge tenInnerMerge = (DzscTenInnerMerge) list.get(i);
			this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
		}
	}

	/**
	 * 取得要备案的物料数据
	 * 
	 * @param materialType
	 * @return
	 */
	public List[] findNotApplyInnerMergeData(String materialType) {
		List[] lsResults = new ArrayList[2];
		List lsResult = new ArrayList();
		List lsResultChange = new ArrayList();
		List list = dzscInnerMergeDao.findNotApplyInnerMergeData(materialType);
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData innerMergeData = (DzscInnerMergeData) list
					.get(i);
			if (innerMergeData.getIsChange() != null
					&& innerMergeData.getIsChange()) {
				lsResultChange.add(innerMergeData);
			} else {
				lsResult.add(innerMergeData);
			}
		}
		lsResults[0] = lsResult;
		lsResults[1] = lsResultChange;
		return lsResults;
	}

	/**
	 * 删除内部归并
	 * 
	 * @param data
	 */
	public void deleteDzscInnerMergeData(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			this.dzscInnerMergeDao.deleteDzscInnerMergeData(data);
			if (data.getDzscTenInnerMerge() != null) {
				DzscTenInnerMerge tenInnerMerge = data.getDzscTenInnerMerge();
				if (this.dzscInnerMergeDao
						.findDzscInnerMergeData(tenInnerMerge).size() <= 0) {
					this.dzscInnerMergeDao
							.deleteDzscTenInnerMerge(tenInnerMerge);
				}
				if (data.getDzscTenInnerMerge().getDzscFourInnerMerge() != null) {
					DzscFourInnerMerge fourInnerMerge = data
							.getDzscTenInnerMerge().getDzscFourInnerMerge();
					if (this.dzscInnerMergeDao.findDzscTenInnerMerge(
							fourInnerMerge).size() <= 0) {
						this.dzscInnerMergeDao
								.deleteDzscFourInnerMerge(fourInnerMerge);
					}
				}
			}
		}
		List lsDelete = this.getAllDeleteTenInnerMerge();
		for (int i = 0; i < lsDelete.size(); i++) {
			DzscTenInnerMerge tenInnerMerge = (DzscTenInnerMerge) lsDelete
					.get(i);
			if (this.dzscInnerMergeDao.findDzscInnerMergeCount(tenInnerMerge) <= 0) {
				this.deleteTenInnerMerge(tenInnerMerge);
			}
		}
	}

	/**
	 * 取得要备案的物料数据
	 * 
	 * @param materialType
	 * @param param
	 * @return
	 */
	private List getApplyInnerMergeData(String materialType,
			TempInnerMergeApplySelectParam param) {
		List lsResult = new ArrayList();
		if (MaterielType.MATERIEL.equals(materialType)) {
			if (param.isApplyAllImgExg() || param.isApplyAllImg()) {
				lsResult = dzscInnerMergeDao
						.findNotApplyInnerMergeData(materialType);
			} else if (param.isApplyPartImgExg()
					&& param.getLsPartImg() != null) {
				lsResult = param.getLsPartImg();
				lsResult.addAll(param.getLsPartImgChange());
			}
		} else if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {
			if (param.isApplyAllImgExg() || param.isApplyAllExg()) {
				lsResult = dzscInnerMergeDao
						.findNotApplyInnerMergeData(materialType);
			} else if (param.isApplyPartImgExg()
					&& param.getLsPartExg() != null) {
				lsResult = param.getLsPartExg();
				lsResult.addAll(param.getLsPartExgChange());
			}
		}
		return lsResult;
	}

	/**
	 * 获取删处状态的10码资料
	 * 
	 * @param materialType
	 * @return
	 */
	private List<DzscTenInnerMerge> getDeleteTenInnerMerge(List list,
			String materialType) {
		List<DzscTenInnerMerge> lsResult = new ArrayList<DzscTenInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			DzscTenInnerMerge delTenInnerMerge = (DzscTenInnerMerge) list
					.get(i);
			if (this.dzscInnerMergeDao.findDzscTenInnerMerge(delTenInnerMerge,
					materialType, false) != null) {
				lsResult.add(delTenInnerMerge);
				System.out.println("-----------------not null " + materialType
						+ "  " + delTenInnerMerge.getTenPtName());
			} else {
				System.out.println("-----------------null " + materialType
						+ "  " + delTenInnerMerge.getTenPtName());
			}
		}
		return lsResult;
	}

	/**
	 * 获取删处状态的10码资料
	 * 
	 * @param materialType
	 * @return
	 */
	private List<DzscTenInnerMerge> getAllDeleteTenInnerMerge() {
		List list = this.dzscInnerMergeDao.findDeleteDzscTenInnerMerge();
		List lsAllTenId = this.dzscInnerMergeDao.findAllDzscTenInnerMergeId();
		for (int i = list.size() - 1; i >= 0; i--) {
			DzscTenInnerMerge delTenInnerMerge = (DzscTenInnerMerge) list
					.get(i);
			if (lsAllTenId.contains(delTenInnerMerge.getId())) {
				list.remove(i);
			}
		}
		return list;
	}

	/**
	 * 申报商品归并资料
	 */
	public DeclareFileInfo applyInnerMerge(String taskId,
			TempInnerMergeApplySelectParam param) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscMaterielHead> lsMaterielHead = new ArrayList<DzscMaterielHead>();
		Map<Integer, DzscTenInnerMerge> hmExg = new HashMap<Integer, DzscTenInnerMerge>();
		Map<Integer, DzscTenInnerMerge> hmImg = new HashMap<Integer, DzscTenInnerMerge>();
		List<DzscInnerMergeData> lsMaterialExgDetail = new ArrayList<DzscInnerMergeData>();
		List<DzscInnerMergeData> lsMaterialImgDetail = new ArrayList<DzscInnerMergeData>();
		List<DzscTenInnerMerge> lsTenImgDetail = new ArrayList<DzscTenInnerMerge>();
		List<DzscTenInnerMerge> lsTenExgDetail = new ArrayList<DzscTenInnerMerge>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}

		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		DzscMaterielHead dzscMaterialHead = null;
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
			dzscMaterialHead.setDeclareDate(new Date());
			dzscMaterialHead.setInnerMergeState(DzscState.APPLY);
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
			lsMaterielHead.add(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}

		List lsEffectivedMaterial = this.materialApplyDao
				.findEffectivedMaterialApplyPtNo();
		List<String> lsNotEffectiveMaterial = new ArrayList<String>();

		// List list = dzscInnerMergeDao
		// .findNotApplyInnerMergeData(MaterielType.FINISHED_PRODUCT);
		List list = getApplyInnerMergeData(MaterielType.FINISHED_PRODUCT, param);
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (DzscState.ORIGINAL.equals(data.getStateMark())) {
				data.setStateMark(DzscState.APPLY);
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
				lsMaterialExgDetail.add(data);
			} else if (DzscState.CHANGE.equals(data.getStateMark())) {
				data.setStateMark(DzscState.APPLY);
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
				if (!ModifyMarkState.UNCHANGE
						.equals(data.getBeforeModifyMark())) {
					lsMaterialExgDetail.add(data);
				}
			}
			if (hmExg.get(data.getDzscTenInnerMerge().getTenSeqNum()) == null
					&& !ModifyMarkState.UNCHANGE.equals(data
							.getDzscTenInnerMerge().getTenModifyMark())) {
				hmExg.put(data.getDzscTenInnerMerge().getTenSeqNum(), data
						.getDzscTenInnerMerge());
			}
		}
		// list = dzscInnerMergeDao
		// .findNotApplyInnerMergeData(MaterielType.MATERIEL);
		list = getApplyInnerMergeData(MaterielType.MATERIEL, param);
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (DzscState.ORIGINAL.equals(data.getStateMark())) {
				data.setStateMark(DzscState.APPLY);
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
				lsMaterialImgDetail.add(data);
			} else if (DzscState.CHANGE.equals(data.getStateMark())) {
				data.setStateMark(DzscState.APPLY);
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
				if (!ModifyMarkState.UNCHANGE
						.equals(data.getBeforeModifyMark())) {
					lsMaterialImgDetail.add(data);
				}
			}
			if (hmImg.get(data.getDzscTenInnerMerge().getTenSeqNum()) == null
					&& !ModifyMarkState.UNCHANGE.equals(data
							.getDzscTenInnerMerge().getTenModifyMark())) {
				hmImg.put(data.getDzscTenInnerMerge().getTenSeqNum(), data
						.getDzscTenInnerMerge());
			}
		}
		lsTenExgDetail.addAll(hmExg.values());
		lsTenImgDetail.addAll(hmImg.values());
		for (int i = 0; i < lsMaterialExgDetail.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) lsMaterialExgDetail
					.get(i);
			if (!lsEffectivedMaterial.contains(data.getMateriel().getPtNo())) {
				lsNotEffectiveMaterial.add(data.getMateriel().getPtNo());
			}
		}
		for (int i = 0; i < lsMaterialImgDetail.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) lsMaterialImgDetail
					.get(i);
			if (!lsEffectivedMaterial.contains(data.getMateriel().getPtNo())) {
				lsNotEffectiveMaterial.add(data.getMateriel().getPtNo());
			}
		}
		if (lsNotEffectiveMaterial.size() > 0) {
			StringBuffer strBuffer = new StringBuffer();
			for (int i = 0; i < lsNotEffectiveMaterial.size(); i++) {
				strBuffer.append(lsNotEffectiveMaterial.get(i) + ";");
			}
			throw new RuntimeException("以下物料:\n" + strBuffer.toString()
					+ "\n没有备案");
		}
		List lsAllDeleteTenInnerMerge = this.getAllDeleteTenInnerMerge();
		List<DzscTenInnerMerge> lsDelExgTen = this.getDeleteTenInnerMerge(
				lsAllDeleteTenInnerMerge, MaterielType.FINISHED_PRODUCT);
		List<DzscTenInnerMerge> lsDelImgTen = this.getDeleteTenInnerMerge(
				lsAllDeleteTenInnerMerge, MaterielType.MATERIEL);

		lsTenImgDetail.addAll(lsDelImgTen);
		lsTenExgDetail.addAll(lsDelExgTen);

		if (lsMaterialExgDetail.size() <= 0 && lsMaterialImgDetail.size() <= 0
				&& lsTenImgDetail.size() <= 0 && lsTenExgDetail.size() <= 0) {
			throw new RuntimeException("内部归并没有任何可以申报的资料");
		}
		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info,
				dzscMaterialHead.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(dzscMaterialHead.getCopEntNo());
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.INNER_MERGE);
		int effectiveCount = this.dzscInnerMergeDao
				.findInnerMergeDataEffectiveCount();
		if (effectiveCount > 0) {
			signInfo.setDeclareType(DzscDelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		String formatFile = "DzscInnerMergeFormat.xml";
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("MaterialHeadId", lsMaterielHead);
		hmData.put("MaterialExgDetail", lsMaterialExgDetail);
		hmData.put("MaterialImgDetail", lsMaterialImgDetail);
		hmData.put("TenImgDetail", lsTenImgDetail);
		hmData.put("TenExgDetail", lsTenExgDetail);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);

	}

	/**
	 * 处理内部归并申请回执
	 * 
	 * @return
	 */
	public String proccessDzscInnerMerge(List lsReturnFile) {
		DzscMaterielHead dzscMaterialHead = null;
		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		return dzscMessageLogic.processMessage(DzscBusinessType.INNER_MERGE,
				dzscMaterialHead.getCopEntNo(), new CspProcessMessage() {

					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						backBillInnerMerge();
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						effectiveInnerMerge();
					}
				},lsReturnFile);
	}

	/**
	 * 生效商品归并资料
	 */
	private void effectiveInnerMerge() {
		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		DzscMaterielHead dzscMaterialHead = null;
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
			dzscMaterialHead.setDeclareDate(new Date());
			dzscMaterialHead.setInnerMergeState(DzscState.EXECUTE);
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		List listDetail = dzscInnerMergeDao.findAppliedInnerMergeData();
		List<DzscTenInnerMerge> lsTenInnerMerge = new ArrayList<DzscTenInnerMerge>();
		List<DzscTenInnerMerge> lsTenInnerMergeChange = new ArrayList<DzscTenInnerMerge>();
		List<DzscTenInnerMerge> lsOldTenInnerMerge = new ArrayList<DzscTenInnerMerge>();
		for (int i = 0; i < listDetail.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) listDetail.get(i);
			if (data.getIsChange() != null && data.getIsChange()) {
				DzscInnerMergeData oldData = this.dzscInnerMergeDao
						.findInnerMergeDataByMaterialCode(data.getMateriel()
								.getPtNo());
				/**
				 * 将正在执行的删除掉
				 */
				this.dzscInnerMergeDao.deleteDzscInnerMergeData(oldData);
				if (!lsOldTenInnerMerge
						.contains(oldData.getDzscTenInnerMerge())) {
					lsOldTenInnerMerge.add(oldData.getDzscTenInnerMerge());
				}
				/**
				 * 如果是删除状态，要将此记录删除
				 */
				if (ModifyMarkState.DELETED.equals(data.getBeforeModifyMark())) {
					dzscInnerMergeDao.deleteDzscInnerMergeData(data);
				} else {
					data.setIsChange(false);
					data.setBeforeModifyMark(ModifyMarkState.UNCHANGE);
					// data.setTenModifyMark(ModifyMarkState.UNCHANGE);
					data.setStateMark(DzscState.EXECUTE);
					dzscInnerMergeDao.saveDzscInnerMergeData(data);
				}
				if (!lsTenInnerMergeChange
						.contains(data.getDzscTenInnerMerge())) {
					lsTenInnerMergeChange.add(data.getDzscTenInnerMerge());
				}
			} else {
				data.setIsChange(false);
				data.setBeforeModifyMark(ModifyMarkState.UNCHANGE);
				// data.setTenModifyMark(ModifyMarkState.UNCHANGE);
				data.setStateMark(DzscState.EXECUTE);
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
				if (!lsTenInnerMerge.contains(data.getDzscTenInnerMerge())) {
					lsTenInnerMerge.add(data.getDzscTenInnerMerge());
				}
			}

		}

		for (DzscTenInnerMerge tenInnerMerge : lsOldTenInnerMerge) {
			if (this.dzscInnerMergeDao.findDzscInnerMergeCount(tenInnerMerge) <= 0) {
				this.deleteTenInnerMerge(tenInnerMerge);
			}
		}

		for (DzscTenInnerMerge tenInnerMerge : lsTenInnerMerge) {
			tenInnerMerge.setTenModifyMark(ModifyMarkState.UNCHANGE);
			dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
		}

		for (DzscTenInnerMerge tenInnerMerge : lsTenInnerMergeChange) {
			// DzscTenInnerMerge oldData = this.dzscInnerMergeDao
			// .findTenInnerMergeByTenSeqNum(tenInnerMerge.getTenSeqNum(),
			// tenInnerMerge.getTenComplex());
			if (ModifyMarkState.DELETED
					.equals(tenInnerMerge.getTenModifyMark())) {
				this.deleteTenInnerMerge(tenInnerMerge);
				continue;
				// this.deleteTenInnerMerge(oldData);
				// dzscInnerMergeDao.deleteDzscTenInnerMerge(tenInnerMerge);
				// dzscInnerMergeDao.deleteDzscTenInnerMerge(oldData);
			}
			tenInnerMerge.setTenModifyMark(ModifyMarkState.UNCHANGE);
			dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
		}

		List lsAllDeleteTenInnerMerge = this.getAllDeleteTenInnerMerge();
		for (int i = 0; i < lsAllDeleteTenInnerMerge.size(); i++) {
			DzscTenInnerMerge delTenInnerMerge = (DzscTenInnerMerge) lsAllDeleteTenInnerMerge
					.get(i);
			this.deleteTenInnerMerge(delTenInnerMerge);
		}
	}

	/**
	 * 删处10码归并
	 * 
	 * @param tenInnerMerge
	 */
	private void deleteTenInnerMerge(DzscTenInnerMerge tenInnerMerge) {
		DzscFourInnerMerge fourInnerMerge = tenInnerMerge
				.getDzscFourInnerMerge();
		this.dzscInnerMergeDao.deleteDzscTenInnerMerge(tenInnerMerge);
		if (fourInnerMerge == null
				|| this.dzscInnerMergeDao
						.findDzscTenInnerMergeCount(fourInnerMerge) <= 0) {
			this.dzscInnerMergeDao.deleteDzscFourInnerMerge(fourInnerMerge);
		}
	}

	/**
	 * 生效商品归并资料
	 */
	private void backBillInnerMerge() {
		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		DzscMaterielHead dzscMaterialHead = null;
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
			dzscMaterialHead.setDeclareDate(new Date());
			int effectiveCount = this.dzscInnerMergeDao
					.findInnerMergeDataEffectiveCount();
			if (effectiveCount > 0) {
				dzscMaterialHead.setInnerMergeState(DzscState.CHANGE);
			} else {
				dzscMaterialHead.setInnerMergeState(DzscState.ORIGINAL);
			}
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		List listDetail = dzscInnerMergeDao.findAppliedInnerMergeData();
		for (int i = 0; i < listDetail.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) listDetail.get(i);
			if (data.getIsChange() != null && data.getIsChange()) {
				data.setStateMark(DzscState.CHANGE);
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
			} else {
				data.setStateMark(DzscState.ORIGINAL);
				dzscInnerMergeDao.saveDzscInnerMergeData(data);
			}
			dzscInnerMergeDao.saveDzscInnerMergeData(data);
		}
	}

	/**
	 * 变更商品归并资料
	 * 
	 * @param list
	 *            是DzscInnerMergeData型，商品归并资料
	 * @return List 是DzscInnerMergeData型，商品归并资料(未变更的）
	 */
	public List changeInnerMerge(List list) { // 归并变更
		List lsInnerChanges = new Vector();
		Map<Integer, DzscFourInnerMerge> hmFourInnerMerge = new HashMap<Integer, DzscFourInnerMerge>();
		Map<Integer, DzscTenInnerMerge> hmTenInnerMerge = new HashMap<Integer, DzscTenInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData detail = (DzscInnerMergeData) list.get(i);
			if (!DzscState.EXECUTE.equals(detail.getStateMark())) {
				continue;
			}
			DzscInnerMergeData changed = this.dzscInnerMergeDao
					.findChangedMergeDataByMaterialCode(detail.getMateriel()
							.getPtNo());
			if (changed != null) {
				lsInnerChanges.add(detail);
				continue;
			}
			DzscInnerMergeData detailChanged = null; // 变更
			DzscTenInnerMerge tenChanged = null; // 变更
			DzscFourInnerMerge fourChanged = null; // 变更
			try {
				fourChanged = hmFourInnerMerge.get(detail
						.getDzscTenInnerMerge().getDzscFourInnerMerge()
						.getFourSeqNum());
				if (fourChanged == null) {
					fourChanged = (DzscFourInnerMerge) BeanUtils
							.cloneBean(detail.getDzscTenInnerMerge()
									.getDzscFourInnerMerge());
					fourChanged.setId(null);
					this.dzscInnerMergeDao.saveDzscFourInnerMerge(fourChanged);
					hmFourInnerMerge.put(fourChanged.getFourSeqNum(),
							fourChanged);
				}
				tenChanged = hmTenInnerMerge.get(detail.getDzscTenInnerMerge()
						.getTenSeqNum());
				if (tenChanged == null) {
					tenChanged = (DzscTenInnerMerge) BeanUtils.cloneBean(detail
							.getDzscTenInnerMerge());
					tenChanged.setId(null);
					tenChanged.setDzscFourInnerMerge(fourChanged);
					tenChanged.setTenModifyMark(ModifyMarkState.UNCHANGE);
					this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenChanged);
					hmTenInnerMerge.put(tenChanged.getTenSeqNum(), tenChanged);
				}
				detailChanged = (DzscInnerMergeData) BeanUtils
						.cloneBean(detail);
				detailChanged.setId(null);
				detailChanged.setIsChange(true);
				detailChanged.setDzscTenInnerMerge(tenChanged);
				detailChanged.setStateMark(DzscState.CHANGE);
				detailChanged.setBeforeModifyMark(ModifyMarkState.UNCHANGE);
				this.dzscInnerMergeDao.saveDzscInnerMergeData(detailChanged);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			// this.dzscInnerMergeDao.saveDzscInnerMergeData(detail);
		}
		return lsInnerChanges;
	}

	/**
	 * 增加归并数据
	 * 
	 * @param list
	 *            是materiel型，电子手册的物料基本资料
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List addInnerMergeData(List list, boolean flag) {
		List lsResult = new ArrayList();
		if (list == null || list.size() < 1) {
			return lsResult;
		}
		Materiel materiel = (Materiel) list.get(0);
		String type = materiel.getScmCoi().getCoiProperty();
		for (int i = 0; i < list.size(); i++) {
			materiel = (Materiel) list.get(i);
			DzscInnerMergeData innerMergeData = new DzscInnerMergeData();
			innerMergeData.setImrType(materiel.getScmCoi().getCoiProperty());
			innerMergeData.setMateriel(materiel);
			if (flag) {
				innerMergeData.setStateMark(DzscState.CHANGE);
			} else {
				innerMergeData.setStateMark(DzscState.ORIGINAL);
			}
			innerMergeData.setBeforeModifyMark(ModifyMarkState.ADDED);
			innerMergeData.setIsChange(flag);
			this.dzscInnerMergeDao.saveDzscInnerMergeData(innerMergeData);
			lsResult.add(innerMergeData);
		}
		return lsResult;
	}

	/**
	 * 抓取电子手册的物料基本资料（不在电子手册归并存在的）
	 * 
	 * @param materielType
	 *            物料类别属性
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是materiel型，电子手册的物料基本资料
	 */
	public List findMaterielForDzscInnerMerge(String materielType, int index,
			int length, String property, Object value, Boolean isLike) {
		// List lsResult = new ArrayList();
		// List list =
		// this.dzscInnerMergeDao.findMaterielForDzscInnerMerge(materielType,
		// index, length, property, value, isLike);
		// for (int i = 0; i < list.size(); i++) {
		// Object[] objs = (Object[]) list.get(i);
		// if (objs.length > 0) {
		// lsResult.add((Materiel) objs[0]);
		// }
		// }
		// return lsResult;
		List list = this.dzscInnerMergeDao.findMaterielForDzscInnerMerge(
				materielType, index, length, property, value, isLike);
		List lsExistedPtNo = this.dzscInnerMergeDao
				.findExistedMaterialPtNoInInnerMerge(materielType);
		for (int i = list.size() - 1; i >= 0; i--) {
			Materiel materiel = (Materiel) list.get(i);
			if (lsExistedPtNo.contains(materiel.getPtNo())) {
				list.remove(i);
			}
		}
		return list;
	}

	// /**
	// * 归并全部资料
	// *
	// */
	// public void dzscCustomInnerMerge() {
	// getDzscCustomInnerMerge().customInnerMergeAll();
	// }
	//
	// /**
	// * 归并数据
	// *
	// */
	// public void dzscAutoInnerMergeData() {
	// getDzscAutoInnerMerge().dzscInnerMergeData();
	// }
	public void initUnitDedault() {
		List list = this.dzscInnerMergeDao.findInnerMergeDataAll();
		if (list == null) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData dzscInnerMergeData = (DzscInnerMergeData) list
					.get(i);
			if (dzscInnerMergeData.getMateriel() == null
					|| dzscInnerMergeData.getDzscTenInnerMerge() == null
					|| dzscInnerMergeData.getMateriel().getCalUnit() == null
					|| dzscInnerMergeData.getDzscTenInnerMerge().getTenUnit() == null) {
				continue;
			}
			CalUnit materielcalUnit = dzscInnerMergeData.getMateriel()
					.getCalUnit();
			Unit dzscTenInnerMergeUnit = dzscInnerMergeData
					.getDzscTenInnerMerge().getTenUnit();

			Double convert = getConvert(materielcalUnit.getName(),
					dzscTenInnerMergeUnit.getName());
			dzscInnerMergeData.setUnitConvert(convert);

			this.dzscInnerMergeDao.saveDzscInnerMergeData(dzscInnerMergeData);
		}
	}

	private Double getConvert(String materielName, String customsName) {
		List list = materialManageDao.findCalUnitByUnitName(materielName,
				customsName);
		if (list != null && list.size() > 0) {
			return ((CalUnit) list.get(0)).getScale();
		}
		return Double.valueOf(0);
	}

	public void convertOldDataToNewData() {
		convertOldDataToNewData(MaterielType.MATERIEL);
		convertOldDataToNewData(MaterielType.SEMI_FINISHED_PRODUCT);
		convertOldDataToNewData(MaterielType.FINISHED_PRODUCT);
	}

	private void convertOldDataToNewData(String type) {
		// List list=this.dzscInnerMergeDao.findFourInnerMergeDataByType(type);
		// for(int i=0;i<list.size();i++){
		// DzscFourInnerMerge fourInnerMerge=(DzscFourInnerMerge)list.get(i);
		// List
		// lsTen=this.dzscInnerMergeDao.findDzscTenInnerMerge(fourInnerMerge);
		// for(int j=0;j<lsTen.size();j++){
		// DzscTenInnerMerge tenInnerMerge=(DzscTenInnerMerge)lsTen.get(j);
		// List
		// lsBefore=this.dzscInnerMergeDao.findDzscInnerMergeData(tenInnerMerge);
		// for(int m=0;m<lsBefore.size();m++){
		// DzscInnerMergeData
		// innerMergeData=(DzscInnerMergeData)lsBefore.get(m);
		// innerMergeData.setDzscTenInnerMerge(null);
		// this.dzscInnerMergeDao.saveDzscInnerMergeData(innerMergeData);
		// }
		// this.dzscInnerMergeDao.deleteDzscTenInnerMerge(tenInnerMerge);
		// }
		// this.dzscInnerMergeDao.deleteDzscFourInnerMerge(fourInnerMerge);
		// }

		// Map<Integer,DzscTenInnerMerge> tenMap=new
		// HashMap<Integer,DzscTenInnerMerge>();
		// Map<Integer,DzscFourInnerMerge> fourMap=new
		// HashMap<Integer,DzscFourInnerMerge>();
		// List list=this.dzscInnerMergeDao.findDzscInnerMergeDataByType(type);
		// for(int i=0;i<list.size();i++){
		// DzscInnerMergeData innerMergeData=(DzscInnerMergeData)list.get(i);
		// if(innerMergeData.getTenSeqNum()==null||innerMergeData.getFourSeqNum()==null){
		// continue;
		// }
		// DzscTenInnerMerge
		// tenInnerMerge=tenMap.get(innerMergeData.getTenSeqNum());
		// if(tenInnerMerge==null){
		// DzscFourInnerMerge
		// fourInnerMerge=fourMap.get(innerMergeData.getFourSeqNum());
		// if(fourInnerMerge==null){
		// fourInnerMerge=new DzscFourInnerMerge();
		// fourInnerMerge.setFourSeqNum(innerMergeData.getFourSeqNum());
		// fourInnerMerge.setFourComplex(innerMergeData.getFourComplex());
		// fourInnerMerge.setFourPtName(innerMergeData.getFourPtName());
		// fourInnerMerge.setFourPtSpec(innerMergeData.getFourPtSpec());
		// fourInnerMerge.setFourUnit(innerMergeData.getFourUnit());
		// this.dzscInnerMergeDao.saveDzscFourInnerMerge(fourInnerMerge);
		// fourMap.put(innerMergeData.getFourSeqNum(), fourInnerMerge);
		// }
		// tenInnerMerge=new DzscTenInnerMerge();
		// tenInnerMerge.setTenSeqNum(innerMergeData.getTenSeqNum());
		// tenInnerMerge.setTenComplex(innerMergeData.getTenComplex());
		// tenInnerMerge.setTenPtName(innerMergeData.getTenPtName());
		// tenInnerMerge.setTenPtSpec(innerMergeData.getTenPtSpec());
		// tenInnerMerge.setTenUnit(innerMergeData.getTenUnit());
		// tenInnerMerge.setDzscFourInnerMerge(fourInnerMerge);
		// tenInnerMerge.setTenModifyMark(ModifyMarkState.UNCHANGE);
		// this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
		// tenMap.put(innerMergeData.getTenSeqNum(), tenInnerMerge);
		// }
		// innerMergeData.setDzscTenInnerMerge(tenInnerMerge);
		// this.dzscInnerMergeDao.saveDzscInnerMergeData(innerMergeData);
		// }
	}

	/**
	 * 特殊处理，将归并关系从正在生效状态回滚到原始状态
	 * 
	 * @param list
	 */
	public void resetExecuteToOriginal(List list) {
		List<DzscTenInnerMerge> lsTenInnerMerge = new ArrayList<DzscTenInnerMerge>();
		for (int i = 0; i < list.size(); i++) {
			DzscInnerMergeData data = (DzscInnerMergeData) list.get(i);
			if (DzscState.EXECUTE.equals(data.getStateMark())) {
				if (data.getDzscTenInnerMerge() != null) {
					if (!lsTenInnerMerge.contains(data.getDzscTenInnerMerge())) {
						lsTenInnerMerge.add(data.getDzscTenInnerMerge());
					}
				}
				data.setStateMark(DzscState.ORIGINAL);
				data.setBeforeModifyMark(ModifyMarkState.ADDED);
				this.dzscInnerMergeDao.saveDzscInnerMergeData(data);
			}
		}
		for (int i = 0; i < lsTenInnerMerge.size(); i++) {
			DzscTenInnerMerge tenInnerMerge = lsTenInnerMerge.get(i);
			tenInnerMerge.setTenModifyMark(ModifyMarkState.ADDED);
			this.dzscInnerMergeDao.saveDzscTenInnerMerge(tenInnerMerge);
		}
	}

	/**
	 * 查询不在报关单耗成品里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomExg() {
		List lsTenInnerMerge = this.dzscInnerMergeDao.findDzscTenInnerMerge(
				MaterielType.FINISHED_PRODUCT, false, DzscState.EXECUTE);
		List listExistExgId = this.dzscInnerMergeDao
				.findDzscCustomsBomExgTenSeqNum();
		for (int i = lsTenInnerMerge.size() - 1; i >= 0; i--) {
			DzscTenInnerMerge dzscTenInnerMerge = (DzscTenInnerMerge) lsTenInnerMerge
					.get(i);
			if (listExistExgId.contains(dzscTenInnerMerge.getTenSeqNum())) {
				lsTenInnerMerge.remove(i);
			}
		}
		return lsTenInnerMerge;
	}

	/**
	 * 查询不在报关单耗明细里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomDetail(
			DzscCustomsBomExg dzscCustomsBomExg) {
		List lsTenInnerMerge = this.dzscInnerMergeDao.findDzscTenInnerMerge(
				MaterielType.MATERIEL, false, DzscState.EXECUTE);
		List listExistDetailId = this.dzscInnerMergeDao
				.findDzscCustomsBomDetailTenSeqNum(dzscCustomsBomExg);
		for (int i = lsTenInnerMerge.size() - 1; i >= 0; i--) {
			DzscTenInnerMerge dzscTenInnerMerge = (DzscTenInnerMerge) lsTenInnerMerge
					.get(i);
			if (listExistDetailId.contains(dzscTenInnerMerge.getTenSeqNum())) {
				lsTenInnerMerge.remove(i);
			}
		}
		return lsTenInnerMerge;
	}

	/**
	 * 根据归并增加报关单耗成品
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	public List addDzscCustomsBomExgFromInnerMerge(List lsTenInnerMerge) {
		List<DzscCustomsBomExg> lsResult = new ArrayList<DzscCustomsBomExg>();
		for (int i = 0; i < lsTenInnerMerge.size(); i++) {
			DzscTenInnerMerge dzscTenInnerMerge = (DzscTenInnerMerge) lsTenInnerMerge
					.get(i);
			DzscCustomsBomExg dzscCustomsBomExg = new DzscCustomsBomExg();
			dzscCustomsBomExg.setTenSeqNum(dzscTenInnerMerge.getTenSeqNum());
			dzscCustomsBomExg.setComplex(dzscTenInnerMerge.getTenComplex());
			dzscCustomsBomExg.setName(dzscTenInnerMerge.getTenPtName());
			dzscCustomsBomExg.setSpec(dzscTenInnerMerge.getTenPtSpec());
			dzscCustomsBomExg.setUnit(dzscTenInnerMerge.getTenUnit());
			this.dzscInnerMergeDao.saveOrUpdate(dzscCustomsBomExg);
			lsResult.add(dzscCustomsBomExg);
		}
		return lsResult;
	}

	/**
	 * 根据归并增加报关单耗明细
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	public List addDzscCustomsBomDetailFromInnerMerge(
			DzscCustomsBomExg dzscCustomsBomExg, List lsTenInnerMerge) {
		List<DzscCustomsBomDetail> lsResult = new ArrayList<DzscCustomsBomDetail>();
		for (int i = 0; i < lsTenInnerMerge.size(); i++) {
			DzscTenInnerMerge dzscTenInnerMerge = (DzscTenInnerMerge) lsTenInnerMerge
					.get(i);
			DzscCustomsBomDetail dzscCustomsBomDetail = new DzscCustomsBomDetail();
			dzscCustomsBomDetail.setDzscCustomsBomExg(dzscCustomsBomExg);
			dzscCustomsBomDetail.setTenSeqNum(dzscTenInnerMerge.getTenSeqNum());
			dzscCustomsBomDetail.setComplex(dzscTenInnerMerge.getTenComplex());
			dzscCustomsBomDetail.setName(dzscTenInnerMerge.getTenPtName());
			dzscCustomsBomDetail.setSpec(dzscTenInnerMerge.getTenPtSpec());
			dzscCustomsBomDetail.setUnit(dzscTenInnerMerge.getTenUnit());
			this.dzscInnerMergeDao.saveOrUpdate(dzscCustomsBomDetail);
			lsResult.add(dzscCustomsBomDetail);
		}
		return lsResult;
	}

	/**
	 * 删除报关单耗成品资料
	 * 
	 * @param dzscCustomsBomExg
	 */
	public void deleteDzscCustomsBomExg(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscCustomsBomExg dzscCustomsBomExg = (DzscCustomsBomExg) list
					.get(i);
			this.dzscInnerMergeDao.deleteAll(this.dzscInnerMergeDao
					.findDzscCustomsBomDetail(dzscCustomsBomExg));
			this.dzscInnerMergeDao.delete(dzscCustomsBomExg);
		}
	}

	/**
	 * 删除报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public void deleteDzscCustomsBomDetail(List list) {
		for (int i = 0; i < list.size(); i++) {
			DzscCustomsBomDetail dzscCustomsBomDetail = (DzscCustomsBomDetail) list
					.get(i);
			this.dzscInnerMergeDao.delete(dzscCustomsBomDetail);
		}
	}

	/**
	 * 根据报关单耗的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exg
	 * @return
	 */
	public List findMaterialExgByCustomsBomExg(DzscCustomsBomExg exg) {
		List lsResult = new ArrayList();
		List lsMateriel = this.dzscInnerMergeDao
				.findDzscInnerMergeDataByTenSeqNum(exg.getTenSeqNum(),
						MaterielType.FINISHED_PRODUCT, DzscState.EXECUTE);
		if (lsMateriel.size() <= 0) {
			throw new RuntimeException("在归并关系里找不到归并序号为：" + exg.getTenSeqNum()
					+ "的归并");
		}
		for (int i = 0; i < lsMateriel.size(); i++) {
			Materiel master = ((DzscInnerMergeData) lsMateriel.get(i))
					.getMateriel();
			List lsVersionApply = materialApplyDao
					.findMaterialBomVersionApply(master);
			if (lsVersionApply.size() <= 0) {
				continue;
			}
			for (int j = 0; j < lsVersionApply.size(); j++) {
				MaterialBomVersionApply versionApply = (MaterialBomVersionApply) lsVersionApply
						.get(j);
				List lsDetailApply = this.materialApplyDao
						.findMaterialBomDetailApply(versionApply);
				if (lsDetailApply.size() > 0) {
					TempDzscProductVersionInfo info = new TempDzscProductVersionInfo();
					info.setParentNo(master.getPtNo());
					info.setVersionNo(versionApply.getVersion().toString());
					info.setBeginDate(versionApply.getBeginDate());
					info.setEndDate(versionApply.getEndDate());
					// if (j == lsVersionApply.size() - 1) {
					// info.setAmount(100.0);
					// }
					lsResult.add(info);
				}
			}
		}
		if (lsResult.size() > 0) {
			for (int i = 0; i < lsResult.size(); i++) {
				TempDzscProductVersionInfo info = (TempDzscProductVersionInfo) lsResult
				.get(i);
				info.setAmount(100.0);
			}
		}
		return lsResult;
	}

	/**
	 * 自动计算报关单耗
	 * 
	 * @param list
	 * @return
	 */
	public List autoCalDzscCustomsBom(List list, DzscCustomsBomExg exg) {
		List lsResult = new ArrayList();
		Map<String, Double> hmMaterialBomUnitWaste = new HashMap<String, Double>();// 归并前单耗总数量
		Map<String, Double> hmMaterialBomUnitUsed = new HashMap<String, Double>();// 归并前单项用量总数量
		Map<Integer, Double> hmEmsUnitWasteAmount = new HashMap<Integer, Double>();// 归并后单耗总数量
		Map<Integer, Double> hmEmsUnitUsedAmount = new HashMap<Integer, Double>();// 归并后单项用量总数量
		Map<Integer, DzscCustomsBomDetail> hmEmsUnitUsedInfo = new HashMap<Integer, DzscCustomsBomDetail>();// 归并后单耗
		for (int i = 0; i < list.size(); i++) {
			TempDzscProductVersionInfo info = (TempDzscProductVersionInfo) list
					.get(i);
			if (info.getAmount() == null || info.getAmount() <= 0) {
				continue;
			}
			Double exgAmount = 100.0;
			// CommonUtils
			// .getDoubleExceptNull(info.getAmount());
			String ptNo = info.getParentNo();
			Integer versionNo = Integer.valueOf(info.getVersionNo());
			List lsDetail = this.materialApplyDao.findMaterialBomDetailApply(
					ptNo, versionNo);
			for (int j = 0; j < lsDetail.size(); j++) {
				MaterialBomDetailApply detailApply = (MaterialBomDetailApply) lsDetail
						.get(j);
				String subPtNo = detailApply.getMateriel().getPtNo();
				Double oldUnitWaste = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitWaste
								.get(subPtNo));
				Double oldUnitUsed = CommonUtils
						.getDoubleExceptNull(hmMaterialBomUnitUsed.get(subPtNo));
				hmMaterialBomUnitWaste.put(subPtNo, oldUnitWaste
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(detailApply
								.getUnitWaste()));
				hmMaterialBomUnitUsed.put(subPtNo, oldUnitUsed
						+ exgAmount
						* CommonUtils.getDoubleExceptNull(detailApply
								.getUnitUsed()));
			}
		}
		Set<String> hsPtNo = hmMaterialBomUnitWaste.keySet();
		Iterator itDetail = hsPtNo.iterator();
		while (itDetail.hasNext()) {
			String detailPtNo = itDetail.next().toString();
			DzscInnerMergeData innerMergeData = this.dzscInnerMergeDao
					.findInnerMergeDataByMaterialCode(detailPtNo);
			if (innerMergeData == null
					|| innerMergeData.getDzscTenInnerMerge() == null) {
				throw new RuntimeException("料件" + detailPtNo + "没有做归并关系");
			}
			if (!DzscState.EXECUTE.equals(innerMergeData.getStateMark())) {
				throw new RuntimeException("料件" + detailPtNo + "在归并关系中没有备案");
			}
			Integer tenSeqNum = innerMergeData.getDzscTenInnerMerge()
					.getTenSeqNum();
			Double value = 0.0;
			DzscCustomsBomDetail bomBill = new DzscCustomsBomDetail();
			if (hmEmsUnitUsedInfo.get(tenSeqNum) == null) {
				DzscTenInnerMerge tenInnerMerge = innerMergeData
						.getDzscTenInnerMerge();
				bomBill.setTenSeqNum(tenInnerMerge.getTenSeqNum());
				bomBill.setComplex(tenInnerMerge.getTenComplex());
				bomBill.setName(tenInnerMerge.getTenPtName());
				bomBill.setSpec(tenInnerMerge.getTenPtSpec());
				bomBill.setUnit(tenInnerMerge.getTenUnit());
				hmEmsUnitUsedInfo.put(tenInnerMerge.getTenSeqNum(), bomBill);
			}else {
				bomBill = hmEmsUnitUsedInfo.get(tenSeqNum);
			}
			
			// 报关单项用量=，折算系数1*工厂单项用量1+，折算系数2*工厂单项用量2……
			// 报关单耗=折算系数1*工厂单耗1+折算系数2*工厂单耗2……
			// 这里先放折算系数
			value = innerMergeData.getUnitConvert() == null ? 0.0
					: innerMergeData.getUnitConvert();
			Double oldEmsUnitWaste = CommonUtils
					.getDoubleExceptNull(hmEmsUnitWasteAmount.get(tenSeqNum));
			Double oldEmsUnitUsed = CommonUtils
					.getDoubleExceptNull(hmEmsUnitUsedAmount.get(tenSeqNum));
			hmEmsUnitWasteAmount.put(tenSeqNum, oldEmsUnitWaste
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitWaste
							.get(detailPtNo)*value));
			hmEmsUnitUsedAmount.put(tenSeqNum, oldEmsUnitUsed
					+ CommonUtils.getDoubleExceptNull(hmMaterialBomUnitUsed
							.get(detailPtNo)*value));
			
		}
		Iterator itBomImgSeqNum = hmEmsUnitUsedInfo.keySet().iterator();
		Double totalExgAmount = 100.0;
		// CommonUtils.getDoubleExceptNull(exg
		// .getAmount());
		while (itBomImgSeqNum.hasNext()) {
			Integer tenSeqNum = Integer.valueOf(itBomImgSeqNum.next()
					.toString());
			DzscCustomsBomDetail bomBill = hmEmsUnitUsedInfo.get(tenSeqNum);
			if (bomBill == null) {
				continue;
			}
			Double unitWasteAmount = CommonUtils
					.getDoubleExceptNull(hmEmsUnitWasteAmount.get(tenSeqNum));
			Double unitUsedAmount = CommonUtils
					.getDoubleExceptNull(hmEmsUnitUsedAmount.get(tenSeqNum));
			Double unitWaste = (totalExgAmount == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(unitWasteAmount / totalExgAmount, 6));
			Double unitUsed = (totalExgAmount == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(unitUsedAmount / totalExgAmount, 6));
			Double waste = (unitWaste == 0 ? 0.0 : CommonUtils
					.getDoubleByDigit(
							((unitUsed - unitWaste) / unitWaste) * 100, 6));
			bomBill.setUnitWare(unitWaste);
			bomBill.setWare(waste);
			bomBill.setUnitDosage(unitUsed);
		}
		lsResult.addAll(hmEmsUnitUsedInfo.values());
		return lsResult;
	}

	/**
	 * 保存自动计算的报关单耗
	 * 
	 * @param list
	 * @param exg
	 */
	public void saveAutoCalDzscCustomsBomDetail(List list, DzscCustomsBomExg exg) {
		this.dzscInnerMergeDao.deleteAll(this.dzscInnerMergeDao
				.findDzscCustomsBomDetail(exg));
		for (int i = 0; i < list.size(); i++) {
			DzscCustomsBomDetail bomBill = (DzscCustomsBomDetail) list.get(i);
			bomBill.setDzscCustomsBomExg(exg);
			this.dzscInnerMergeDao.saveOrUpdate(bomBill);
		}

	}

}
