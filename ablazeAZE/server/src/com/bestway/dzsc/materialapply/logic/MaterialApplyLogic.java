package com.bestway.dzsc.materialapply.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscDelcareType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.dzsc.materialapply.dao.MaterialApplyDao;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomMasterApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.dzsc.materialapply.entity.MaterialChange;
import com.bestway.dzsc.materialapply.entity.MaterialHistory;
import com.bestway.dzsc.materialapply.entity.TempExpQPBomMsgSelectParam;
import com.bestway.dzsc.materialapply.entity.TempExpQPMaterialMsgSelectParam;
import com.bestway.dzsc.materialapply.entity.TempMaterialApply;
import com.bestway.dzsc.materialapply.entity.TempMaterialApplySelectParam;
import com.bestway.dzsc.message.logic.DzscMessageLogic;

/**
 * com.bestway.dzsc.materialapply.logic.MaterialApplyLogic
 * 
 * @author yp
 * checked by cjb 2009.10.25
 * 料件相关业务层
 */
public class MaterialApplyLogic {
	
	/**
	 * 料件操作接口
	 */
	private MaterialApplyDao materialApplyDao = null;

	/**
	 * 工厂通用代码操作接口
	 */
	private MaterialManageDao materialManageDao = null;

	/**
	 * 报文处理操作接口
	 */
	private DzscMessageLogic dzscMessageLogic = null;

	/**
	 * 获取materialManageDao
	 * 
	 * @return materialManageDao
	 */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/**
	 * 设置 materialManageDao
	 * 
	 * @param materialManageDao
	 */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/**
	 * 获取materialApplyDao
	 * 
	 * @return materialApplyDao
	 */
	public MaterialApplyDao getMaterialApplyDao() {
		return materialApplyDao;
	}

	/**
	 * 设置materialApplyDao
	 * 
	 * @param materialApplyDao
	 */
	public void setMaterialApplyDao(MaterialApplyDao materialApplyDao) {
		this.materialApplyDao = materialApplyDao;
	}

	/**
	 * 获取dzscMessageLogic
	 * @return
	 */
	public DzscMessageLogic getDzscMessageLogic() {
		return dzscMessageLogic;
	}

	/**
	 * 设置dzscMessageLogic
	 * @param dzscExportMessageLogic
	 */
	public void setDzscMessageLogic(DzscMessageLogic dzscExportMessageLogic) {
		this.dzscMessageLogic = dzscExportMessageLogic;
	}

	/**
	 * 抓取物料表头
	 * 
	 * @return
	 */
	public DzscMaterielHead findDzscMaterielHead() {
		List list = this.materialApplyDao.findDzscMaterialHead();
		Company company = (Company) CommonUtils.getCompany();
		if (list != null && list.size() > 0) {
			boolean isModify = false;
			DzscMaterielHead head = (DzscMaterielHead) list.get(0);

			if (head.getTradeCode() == null) {
				head.setTradeCode(company.getBuCode());
				head.setTradeName(company.getBuName());
				isModify = true;
			}
			if (head.getMachCode() == null) {
				head.setMachCode(company.getCode());
				head.setMachName(company.getName());
				isModify = true;
			}
			if (head.getMasterCustoms() == null) {
				head.setMasterCustoms(company.getMasterCustoms());
				isModify = true;
			}
			if (isModify) {
				this.materialApplyDao.saveOrUpdate(head);
			}
			return head;
		} else {
			String copEntNo = dzscMessageLogic.getNewCopEntNo(
					"DzscMaterielHead", "copEntNo", "D",
					DzscBusinessType.MATERIAL);

			DzscMaterielHead materielHead = new DzscMaterielHead();
			materielHead.setTradeCode(company.getBuCode());
			materielHead.setTradeName(company.getBuName());
			materielHead.setMachCode(company.getCode());
			materielHead.setMachName(company.getName());
			materielHead.setMasterCustoms(company.getMasterCustoms());
			materielHead.setCopEntNo(copEntNo);
			materielHead.setInputDate(new Date());
			materielHead.setDeclareDate(new Date());
			materielHead.setManageObject(ManageObject.MANUFACTURE_COP);
			materielHead.setMaterialState(DzscState.ORIGINAL);
			materielHead.setInnerMergeState(DzscState.ORIGINAL);
			materielHead.setBomState(DzscState.ORIGINAL);
			this.materialApplyDao.saveOrUpdate(materielHead);
			return materielHead;
		}
	}

	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterialNotInApply(String scmCoiCode) {
		List list = this.materialManageDao.findMaterielByScmCoi(scmCoiCode);
		List lsApplyId = this.materialApplyDao
				.findMaterialIdInApply(scmCoiCode);
		List lsChangeId = this.materialApplyDao
				.findMaterialIdInChange(scmCoiCode);
		for (int i = 0; i < lsChangeId.size(); i++) {
			if (!lsApplyId.contains(lsChangeId.get(i))) {
				lsApplyId.add(lsChangeId.get(i));
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			Materiel materiel = (Materiel) list.get(i);
			if (lsApplyId.size() == 0) {
				return list;
			}
			int index = lsApplyId.indexOf(materiel.getId());
			if (index >= 0) {
				lsApplyId.remove(index);
				list.remove(i);
			}
		}
		return list;
	}

	/**
	 * 将物料增加到物料备案记录中
	 * 
	 * @param list
	 *            是Materiel型，物料
	 * @return List 是MaterialApply型，物料备案记录
	 */
	public List addMaterialApply(List list) {
		List lsResult = new ArrayList();
		int maxNo = 0;
		if (list.size() > 0) {
			DzscMaterielHead dzscMaterialHead = this.findDzscMaterielHead();
			if (dzscMaterialHead != null) {
				int effectiveCount = this.materialApplyDao
						.findMaterielEffectiveCount();
				if (effectiveCount > 0) {
					dzscMaterialHead.setMaterialState(DzscState.CHANGE);
				} else {
					dzscMaterialHead.setMaterialState(DzscState.ORIGINAL);
				}
				this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
			} else {
				throw new RuntimeException("没有物料表头");
			}
			Materiel materiel = (Materiel) list.get(0);
			maxNo = this.materialApplyDao.findMaterielApplyMaxNo(materiel
					.getScmCoi().getCode());
		}
		for (int i = 0; i < list.size(); i++) {
			maxNo++;
			Materiel materiel = (Materiel) list.get(i);
			MaterialApply materialApply = new MaterialApply();
			materialApply.setNo(maxNo);
			materialApply.setMateriel(materiel);
			materialApply.setStateMark(DzscState.ORIGINAL);
			materialApply.setModifyMark(ModifyMarkState.ADDED);
			this.materialApplyDao.saveMaterialApply(materialApply);
			lsResult.add(materialApply);
		}
		return lsResult;
	}

	/**
	 * 将物料增加到物料备案记录中
	 * 
	 * @param list
	 *            是Materiel型，物料
	 * @return List 是MaterialApply型，物料备案记录
	 */
	public List addMaterialChange(List list) {
		List lsResult = new ArrayList();
		int maxNo = 0;
		if (list.size() > 0) {
			DzscMaterielHead dzscMaterialHead = this.findDzscMaterielHead();
			if (dzscMaterialHead != null) {
				// int effectiveCount =
				// this.materialApplyDao.findMaterielEffectiveCount();
				// if(effectiveCount>0){
				dzscMaterialHead.setMaterialState(DzscState.CHANGE);
				// }
				this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
			} else {
				throw new RuntimeException("没有物料表头");
			}
			Materiel materiel = (Materiel) list.get(0);
			int maxNoApply = this.materialApplyDao
					.findMaterielApplyMaxNo(materiel.getScmCoi().getCode());
			int maxNoChange = this.materialApplyDao
					.findMaterielChangeMaxNo(materiel.getScmCoi().getCode());
			if (maxNoApply > maxNoChange) {
				maxNo = maxNoApply;
			} else {
				maxNo = maxNoChange;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Materiel materiel = (Materiel) list.get(i);
			MaterialChange materialChange = new MaterialChange();
			maxNo++;
			try {
				PropertyUtils.copyProperties(materialChange, materiel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			materialChange.setCustomMaterialId(materiel.getId());
			materialChange.setId(null);
			materialChange.setNo(maxNo);
			materialChange.setStateMark(DzscState.CHANGE);
			materialChange.setModifyMark(ModifyMarkState.ADDED);
			this.materialApplyDao.saveMaterialChange(materialChange);
			lsResult.add(materialChange);
		}
		return lsResult;
	}

	/**
	 * 删除已申报和已执行的物料备案记录
	 * 
	 * @param list
	 *            是MaterialApply型，物料备案记录
	 * @return List 是MaterialApply型，物料备案记录，返回移除了符合条件的List
	 */
	public List deleteMaterialApply(List list) {
		for (int i = 0; i < list.size(); i++) {
			MaterialApply materialApply = (MaterialApply) list.get(i);
			if (DzscState.EXECUTE.equals(materialApply.getStateMark())
					|| DzscState.APPLY.equals(materialApply.getStateMark())) {
				list.remove(materialApply);
				continue;
			}
			this.materialApplyDao.deleteMaterialApply(materialApply);
		}
		return list;
	}

	/**
	 * 变更物料主档
	 * 
	 * @param materialApply
	 *            物料备案记录
	 */
	public void changeMaterial(MaterialApply materialApply) {
		List list = this.materialApplyDao
				.findMaterialChangeByMaterialId(materialApply.getMateriel()
						.getId());
		if (list.size() > 0) {
			return;
		}
		MaterialChange materialChange = new MaterialChange();
		try {
			PropertyUtils.copyProperties(materialChange, materialApply
					.getMateriel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialChange.setCustomMaterialId(materialChange.getId());
		materialChange.setId(null);
		materialChange.setNo(materialApply.getNo());
		materialChange.setStateMark(DzscState.CHANGE);
		materialChange.setModifyMark(ModifyMarkState.UNCHANGE);
		this.materialApplyDao.saveMaterialChange(materialChange);
	}

	/**
	 * 删除物料变更记录
	 * 
	 * @param list
	 *            是MaterialChange型，物料变更记录
	 * @return List 是MaterialChange型，物料变更记录
	 */
	public List deleteMaterialChange(List list) {
		for (int i = 0; i < list.size(); i++) {
			MaterialChange change = (MaterialChange) list.get(i);
			this.materialApplyDao.deleteMaterialChange(change);
		}
		return list;
	}

	/**
	 * 删除物料变更记录
	 * 
	 * @param list
	 *            是MaterialChange型，物料变更记录
	 * @return List 是MaterialChange型，物料变更记录
	 */
	public List markDeleteMaterialChange(List list) {
		if (list.size() <= 0) {
			return list;
		}
		String materialType = ((MaterialChange) list.get(0)).getScmCoi()
				.getCoiProperty();
		List lsBomHasPtNo = new ArrayList();
		if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {
			lsBomHasPtNo = this.materialApplyDao
					.findMaterialBomMasterApplyPtNo();
		} else if (MaterielType.MATERIEL.equals(materialType)) {
			lsBomHasPtNo = this.materialApplyDao
					.findMaterialBomDetailApplyPtNo();
		}
		List lsInnerHasPtNo = this.materialApplyDao
				.findExistingInnerMergePtNo(materialType);
		for (int i = 0; i < list.size(); i++) {
			MaterialChange change = (MaterialChange) list.get(i);
			if (lsBomHasPtNo.contains(change.getPtNo())) {
				throw new RuntimeException("物料" + change.getPtNo()
						+ " 在BOM备案中已使用，不能删除");
			}
			if (lsInnerHasPtNo.contains(change.getPtNo())) {
				throw new RuntimeException("物料" + change.getPtNo()
						+ " 在内部归并中已使用，不能删除");
			}
			change.setModifyMark(ModifyMarkState.DELETED);
			this.materialApplyDao.saveMaterialChange(change);
		}
		return list;
	}

	/**
	 * 取得要备案的没有变更过的物料数据
	 * 
	 * @param materialType
	 * @param param
	 * @return
	 */
	private List getOriginalMaterial(String materialType,
			TempMaterialApplySelectParam param) {
		List lsResult = new ArrayList();
		if (MaterielType.MATERIEL.equals(materialType)) {
			if (param.isApplyAllImgExg() || param.isApplyAllImg()) {
				lsResult = this.materialApplyDao
						.findOriginalMaterial(materialType);
			} else if (param.isApplyPartImgExg()
					&& param.getLsPartImg() != null) {
				lsResult = param.getLsPartImg();
			}
		} else if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {
			if (param.isApplyAllImgExg() || param.isApplyAllExg()) {
				lsResult = this.materialApplyDao
						.findOriginalMaterial(materialType);
			} else if (param.isApplyPartImgExg()
					&& param.getLsPartExg() != null) {
				lsResult = param.getLsPartExg();
			}
		}
		return lsResult;
	}

	/**
	 * 取得要备案的变更过的物料数据
	 * 
	 * @param materialType
	 * @param param
	 * @return
	 */
	private List getChangedMaterial(String materialType,
			TempMaterialApplySelectParam param) {
		List lsResult = new ArrayList();
		if (MaterielType.MATERIEL.equals(materialType)) {
			if (param.isApplyAllImgExg() || param.isApplyAllImg()) {
				lsResult = this.materialApplyDao
						.findNotApplyMaterialChange(materialType);
			} else if (param.isApplyPartImgExg()
					&& param.getLsPartImgChange() != null) {
				lsResult = param.getLsPartImgChange();
			}
		} else if (MaterielType.FINISHED_PRODUCT.equals(materialType)) {
			if (param.isApplyAllImgExg() || param.isApplyAllExg()) {
				lsResult = this.materialApplyDao
						.findNotApplyMaterialChange(materialType);
			} else if (param.isApplyPartImgExg()
					&& param.getLsPartExgChange() != null) {
				lsResult = param.getLsPartExgChange();
			}
		}
		return lsResult;
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPMaterialMessage(String taskId, String messageFileName,
			TempExpQPMaterialMsgSelectParam param) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要要导出的资料");
		}
		List list = new ArrayList();
		if (param.isAllExg()) {
			list = this.materialApplyDao
					.findMaterialApply(MaterielType.FINISHED_PRODUCT);
		} else if (param.isAllImg()) {
			list = this.materialApplyDao
					.findMaterialApply(MaterielType.MATERIEL);
		} else if (param.isPartExg()) {
			list = param.getLsPartData();
		} else if (param.isPartImg()) {
			list = param.getLsPartData();
		}
		System.out.println("Begin get data:" + System.currentTimeMillis());

		String formatFile = "MaterielFormat.xml";
		Map<String, List> hmData = new HashMap<String, List>();
		hmData.put("MaterialId", list);
		// dzscMessageLogic.exportQPMessage(messageFileName,
		// BusinessType.MATERIAL, formatFile, hmData, info);
	}

	/**
	 * 查询异常的物料备案资料
	 * 
	 * @return
	 */
	public List findExceptionMaterialApply() {
		List lsResult = new ArrayList();
		List list = this.materialApplyDao.findExceptionMaterialApply();
		lsResult.addAll(list);
		list = this.materialApplyDao.findExceptionMaterialChange();
		lsResult.addAll(list);
		return lsResult;
	}

	/**
	 * 删除异常的物料备案资料
	 * 
	 * @return
	 */
	public void deleteExceptionMaterialApply(List list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Materiel) {
				MaterialApply materialApply = this.materialApplyDao
						.findMaterialApplyByMaterialId(((Materiel) list.get(i))
								.getId());
				if (materialApply != null) {
					this.materialApplyDao.deleteMaterialApply(materialApply);
				}
			} else if (list.get(i) instanceof MaterialChange) {
				this.materialApplyDao
						.deleteMaterialChange((MaterialChange) list.get(i));
			}
		}
	}

	/**
	 * 申报物料主档
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 */
	public DeclareFileInfo applyMaterial(String taskId,
			TempMaterialApplySelectParam param) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscMaterielHead> lsMaterielHead = new ArrayList<DzscMaterielHead>();
		List<TempMaterialApply> lsMaterialExg = new ArrayList<TempMaterialApply>();
		List<TempMaterialApply> lsMaterialImg = new ArrayList<TempMaterialApply>();
		System.out.println("Begin get data:" + System.currentTimeMillis());
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		String formatFile = "MaterialApplyFormat.xml";
		DzscMaterielHead dzscMaterialHead = null;
		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
			dzscMaterialHead.setDeclareDate(new Date());
			dzscMaterialHead.setMaterialState(DzscState.APPLY);
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
			lsMaterielHead.add(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}

		// 料件
		String materialType = MaterielType.MATERIEL;
		List list = new ArrayList();
		// if (param.isApplyAllImgExg() || param.isApplyAllImg()) {
		// list = this.materialApplyDao.findOriginalMaterial(materialType);
		// } else if (param.isApplyPartImgExg() && param.getLsPartImg() != null)
		// {
		// list = param.getLsPartImg();
		// }
		list = this.getOriginalMaterial(materialType, param);
		for (int i = 0; i < list.size(); i++) {
			MaterialApply materialApply = (MaterialApply) list.get(i);
			if (!DzscState.ORIGINAL.equals(materialApply.getStateMark())) {
				continue;
			}
			TempMaterialApply temp = new TempMaterialApply();
			try {
				PropertyUtils.copyProperties(temp, materialApply.getMateriel());
			} catch (Exception e) {
				e.printStackTrace();
			}
			temp.setNo(materialApply.getNo());
			temp.setModifyMark(materialApply.getModifyMark());
			lsMaterialImg.add(temp);
			materialApply.setStateMark(DzscState.APPLY);
			this.materialApplyDao.saveMaterialApply(materialApply);
		}
		// list =
		// this.materialApplyDao.findNotApplyMaterialChange(materialType);
		list = this.getChangedMaterial(materialType, param);
		for (int i = 0; i < list.size(); i++) {
			MaterialChange materialChange = (MaterialChange) list.get(i);
			TempMaterialApply temp = new TempMaterialApply();
			try {
				PropertyUtils.copyProperties(temp, materialChange);
			} catch (Exception e) {
				e.printStackTrace();
			}
			lsMaterialImg.add(temp);
			materialChange.setStateMark(DzscState.APPLY);
			this.materialApplyDao.saveMaterialChange(materialChange);
		}
		// 成品
		materialType = MaterielType.FINISHED_PRODUCT;
		// list = this.materialApplyDao.findOriginalMaterial(materialType);
		list = this.getOriginalMaterial(materialType, param);
		for (int i = 0; i < list.size(); i++) {
			MaterialApply materialApply = (MaterialApply) list.get(i);
			if (!DzscState.ORIGINAL.equals(materialApply.getStateMark())) {
				continue;
			}
			TempMaterialApply temp = new TempMaterialApply();
			try {
				PropertyUtils.copyProperties(temp, materialApply.getMateriel());
			} catch (Exception e) {
				e.printStackTrace();
			}
			temp.setNo(materialApply.getNo());
			temp.setModifyMark(materialApply.getModifyMark());
			lsMaterialExg.add(temp);
			materialApply.setStateMark(DzscState.APPLY);
			this.materialApplyDao.saveMaterialApply(materialApply);
		}
		// list =
		// this.materialApplyDao.findNotApplyMaterialChange(materialType);
		list = this.getChangedMaterial(materialType, param);
		for (int i = 0; i < list.size(); i++) {
			MaterialChange materialChange = (MaterialChange) list.get(i);
			TempMaterialApply temp = new TempMaterialApply();
			try {
				PropertyUtils.copyProperties(temp, materialChange);
			} catch (Exception e) {
				e.printStackTrace();
			}
			lsMaterialExg.add(temp);
			materialChange.setStateMark(DzscState.APPLY);
			this.materialApplyDao.saveMaterialChange(materialChange);
		}
		if (lsMaterialExg.size() <= 0 && lsMaterialImg.size() <= 0) {
			throw new RuntimeException("成品和物料都没有可以申报的记录");
		}
		int effectiveCount = this.materialApplyDao.findMaterielEffectiveCount();
		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info,
				dzscMaterialHead.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(dzscMaterialHead.getCopEntNo());
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.MATERIAL);
		if (effectiveCount > 0) {
			signInfo.setDeclareType(DzscDelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("MaterialHeadId", lsMaterielHead);
		hmData.put("MaterialExgApplyId", lsMaterialExg);
		hmData.put("MaterialImgApplyId", lsMaterialImg);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	/**
	 * 报文回执处理
	 * @param lsReturnFile
	 * @return
	 */
	public String processMaterialApplyResult(List lsReturnFile) {
		DzscMaterielHead dzscMaterialHead = null;
		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		return dzscMessageLogic.processMessage(DzscBusinessType.MATERIAL,
				dzscMaterialHead.getCopEntNo(), new CspProcessMessage() {

					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						backBillMaterialApply();
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						effectiveMaterialApply();
					}
				}, lsReturnFile);
	}

	/**
	 * 生效物料主档
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 */
	private void effectiveMaterialApply() {
		DzscMaterielHead dzscMaterialHead = this.findDzscMaterielHead();
		if (dzscMaterialHead != null) {
			dzscMaterialHead.setMaterialState(DzscState.EXECUTE);
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		List list = this.materialApplyDao.findNotEffectiveMaterial();
		for (int i = 0; i < list.size(); i++) {
			MaterialApply materialApply = (MaterialApply) list.get(i);
			if (!DzscState.APPLY.equals(materialApply.getStateMark())) {
				continue;
			}
			materialApply.setStateMark(DzscState.EXECUTE);
			materialApply.setModifyMark(ModifyMarkState.UNCHANGE);
			this.materialApplyDao.saveMaterialApply(materialApply);
		}
		list = this.materialApplyDao.findNotEffectiveMaterialChange();
		for (int i = 0; i < list.size(); i++) {
			MaterialChange materialChange = (MaterialChange) list.get(i);
			if (ModifyMarkState.ADDED.equals(materialChange.getModifyMark())) {
				Materiel materiel = this.materialManageDao
						.findMaterielById(materialChange.getCustomMaterialId());
				if (materiel != null) {
					MaterialApply materialApply = new MaterialApply();
					try {
						PropertyUtils.copyProperties(materiel, materialChange);
					} catch (Exception e) {
						e.printStackTrace();
					}
					materiel.setId(materialChange.getCustomMaterialId());
					this.materialManageDao.saveMateriel(materiel);
					materialApply.setNo(materialChange.getNo());
					materialApply.setMateriel(materiel);
					materialApply.setModifyMark(ModifyMarkState.UNCHANGE);
					materialApply.setStateMark(DzscState.EXECUTE);
					this.materialApplyDao.saveMaterialApply(materialApply);
				}
			} else if (ModifyMarkState.DELETED.equals(materialChange
					.getModifyMark())) {
				MaterialApply materialApply = this.materialApplyDao
						.findMaterialApplyByMaterialId(materialChange
								.getCustomMaterialId());
				if (materialApply == null) {
					this.materialApplyDao.deleteMaterialChange(materialChange);
					continue;
				}
				this.materialApplyDao.deleteMaterialApply(materialApply);
			} else if (ModifyMarkState.MODIFIED.equals(materialChange
					.getModifyMark())) {
				MaterialApply materialApply = this.materialApplyDao
						.findMaterialApplyByMaterialId(materialChange
								.getCustomMaterialId());
				if (materialApply == null) {
					this.materialApplyDao.deleteMaterialChange(materialChange);
					continue;
				}
				Materiel materiel = materialApply.getMateriel();
				MaterialHistory history = new MaterialHistory();
				try {
					PropertyUtils.copyProperties(history, materiel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				history.setCustomMaterialId(history.getId());
				history.setId(null);
				this.materialApplyDao.saveMaterialHistory(history);
				try {
					PropertyUtils.copyProperties(materiel, materialChange);
				} catch (Exception e) {
					e.printStackTrace();
				}
				materiel.setId(materialChange.getCustomMaterialId());
				this.materialManageDao.saveMateriel(materiel);
				materialApply.setStateMark(DzscState.EXECUTE);
				materialApply.setModifyMark(ModifyMarkState.UNCHANGE);
				this.materialApplyDao.saveMaterialApply(materialApply);
			}
			this.materialApplyDao.deleteMaterialChange(materialChange);
		}
	}

	/**
	 * 物料主档申请退单
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 */
	private void backBillMaterialApply() {
		DzscMaterielHead dzscMaterialHead = this.findDzscMaterielHead();
		if (dzscMaterialHead != null) {
			int effectiveCount = this.materialApplyDao
					.findMaterielEffectiveCount();
			if (effectiveCount > 0) {
				dzscMaterialHead.setMaterialState(DzscState.CHANGE);
			} else {
				dzscMaterialHead.setMaterialState(DzscState.ORIGINAL);
			}
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		List list = this.materialApplyDao.findNotEffectiveMaterial();
		for (int i = 0; i < list.size(); i++) {
			MaterialApply materialApply = (MaterialApply) list.get(i);
			if (!DzscState.APPLY.equals(materialApply.getStateMark())) {
				continue;
			}
			materialApply.setStateMark(DzscState.ORIGINAL);
			this.materialApplyDao.saveMaterialApply(materialApply);
		}
		list = this.materialApplyDao.findNotEffectiveMaterialChange();
		for (int i = 0; i < list.size(); i++) {
			MaterialChange materialChange = (MaterialChange) list.get(i);
			materialChange.setStateMark(DzscState.CHANGE);
			this.materialApplyDao.saveMaterialChange(materialChange);
		}
	}

	/**
	 * 抓取还没有备案的单耗
	 * 
	 * @return List 是MaterialBomMaster型，报关常用BOM
	 */
	public List findMaterialBomNotInApply() {
		List lsBomMaster = this.materialApplyDao.findMaterialBomMaster();
		List lsHasPtNo = this.materialApplyDao.findMaterialBomMasterApplyPtNo();
		List<Object> lsAppliedPtNo = new ArrayList<Object>();
		lsAppliedPtNo.addAll(this.materialApplyDao
				.findMaterialApplyExingPtNo(MaterielType.FINISHED_PRODUCT));
		lsAppliedPtNo
				.addAll(this.materialApplyDao
						.findMaterialApplyExingPtNo(MaterielType.SEMI_FINISHED_PRODUCT));
		for (int i = lsBomMaster.size() - 1; i >= 0; i--) {
			MaterialBomMaster master = (MaterialBomMaster) lsBomMaster.get(i);
			if (lsHasPtNo.contains(master.getMateriel().getPtNo())
					|| !lsAppliedPtNo.contains(master.getMateriel().getPtNo())) {
				lsBomMaster.remove(i);
				// System.out.println("--------" +
				// master.getMateriel().getPtNo());
			}
		}
		return lsBomMaster;
	}

	/**
	 * 将物料单耗增加到物料单耗备案中
	 * 
	 * @param list
	 *            是MaterialBomMaster型，报关常用BOM里的成品
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	public List addMaterialBomApply(List list, boolean isChange) {
		List lsResult = new ArrayList();
		Map<String, Double> map = this.materialApplyDao
				.findInnerMergeUnitConvert();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomMaster bomMaster = (MaterialBomMaster) list.get(i);
			MaterialBomMasterApply materialBomApply = new MaterialBomMasterApply();
			materialBomApply.setMateriel(bomMaster.getMateriel());
			materialBomApply.setIsChanged(isChange);
			if (!isChange) {
				materialBomApply.setStateMark(DzscState.ORIGINAL);
			} else {
				materialBomApply.setStateMark(DzscState.CHANGE);
			}
			this.materialApplyDao.saveMaterialBomApply(materialBomApply);
			Double parentUnitConvert = map.get(bomMaster.getMateriel()
					.getPtNo());
			if (parentUnitConvert == null || parentUnitConvert == 0) {
				parentUnitConvert = 1.0;
			}
			lsResult.add(materialBomApply);
			List lsVersion = this.materialManageDao
					.findMaterielBomVersion(bomMaster);
			for (int m = 0; m < lsVersion.size(); m++) {
				MaterialBomVersion bomVersion = (MaterialBomVersion) lsVersion
						.get(m);
				MaterialBomVersionApply bomVersionApply = new MaterialBomVersionApply();
				bomVersionApply.setBomMasterApply(materialBomApply);
				bomVersionApply.setVersion(bomVersion.getVersion());
				bomVersionApply.setBeginDate(bomVersion.getBeginDate());
				bomVersionApply.setEndDate(bomVersion.getEndDate());
				// bomVersionApply.setIsChanged(isChange);
				// bomVersionApply.setStateMark(DzscState.ORIGINAL);
				this.materialApplyDao
						.saveMaterialBomVersionApply(bomVersionApply);
				List lsDetail = this.materialManageDao
						.findMaterielBomDetail(bomVersion);
				List lsAppliedPtNo = this.materialApplyDao
						.findMaterialApplyExingPtNo(MaterielType.MATERIEL);
				for (int n = 0; n < lsDetail.size(); n++) {
					MaterialBomDetail bomDetail = (MaterialBomDetail) lsDetail
							.get(n);
					if (bomDetail.getMateriel() != null
							&& !lsAppliedPtNo.contains(bomDetail.getMateriel()
									.getPtNo())) {
						continue;
					}
					Double childUnitConvert = map.get(bomDetail.getMateriel()
							.getPtNo());
					if (childUnitConvert == null) {
						childUnitConvert = 1.0;
					}
					MaterialBomDetailApply bomDetailApply = new MaterialBomDetailApply();
					bomDetailApply.setVersionApply(bomVersionApply);
					bomDetailApply.setMateriel(bomDetail.getMateriel());
					// bomDetailApply.setUnitUsed(bomDetail.getUnitUsed());
					bomDetailApply
							.setUnitWaste(CommonUtils
									.getDoubleByDigit(
											CommonUtils
													.getDoubleExceptNull(bomDetail
															.getUnitWaste())
													* CommonUtils
															.getDoubleExceptNull(childUnitConvert)
													/ CommonUtils
															.getDoubleExceptNull(parentUnitConvert),
											9));
					bomDetailApply
							.setUnitUsed(CommonUtils
									.getDoubleByDigit(
											CommonUtils
													.getDoubleExceptNull(bomDetail
															.getUnitUsed())
													* CommonUtils
															.getDoubleExceptNull(childUnitConvert)
													/ CommonUtils
															.getDoubleExceptNull(parentUnitConvert),
											9));
					if (bomDetailApply.getUnitUsed() != 0.0) {
						bomDetailApply
								.setWaste(CommonUtils
										.getDoubleByDigit(
												(CommonUtils
														.getDoubleExceptNull(bomDetailApply
																.getUnitUsed()
																- bomDetailApply
																		.getUnitWaste()) / bomDetailApply
														.getUnitUsed()) * 100.0,
												5));
					} else {
						bomDetailApply.setWaste(0.0);
					}
					bomDetailApply.setNote(bomDetail.getNote());
					bomDetailApply.setModifyMark(ModifyMarkState.ADDED);
					this.materialApplyDao
							.saveMaterialBomDetailApply(bomDetailApply);
				}
			}
		}
		return lsResult;
	}

	/**
	 * 保存BOM料件备案记录
	 * 
	 * @param materialBomDetailApply
	 *            BOM料件备案记录
	 */
	public void saveMaterialBomDetailApply(
			MaterialBomDetailApply materialBomDetailApply) {
		MaterialBomDetailApply oldDetailApply = (MaterialBomDetailApply) this.materialApplyDao
				.load(MaterialBomDetailApply.class, materialBomDetailApply
						.getId());
		if (oldDetailApply == null) {
			this.materialApplyDao
					.saveMaterialBomDetailApply(materialBomDetailApply);
		} else if (!CommonUtils.compareObject(oldDetailApply.getUnitWaste(),
				materialBomDetailApply.getUnitWaste())
				|| !CommonUtils.compareObject(oldDetailApply.getWaste(),
						materialBomDetailApply.getWaste())
				|| !CommonUtils.compareObject(oldDetailApply.getUnitUsed(),
						materialBomDetailApply.getUnitUsed())) {
			if (!ModifyMarkState.ADDED.equals(materialBomDetailApply
					.getModifyMark())) {
				materialBomDetailApply.setModifyMark(ModifyMarkState.MODIFIED);
			}
			this.materialApplyDao
					.saveMaterialBomDetailApply(materialBomDetailApply);
		}
	}

	/**
	 * 删除物料单耗备案
	 * 
	 * @param list
	 *            是MaterialBomMasterApply型，BOM备案记录
	 */
	public void deleteMaterialBomApply(List list) {
		for (int i = 0; i < list.size(); i++) {
			MaterialBomMasterApply bomApply = (MaterialBomMasterApply) list
					.get(i);
			List ls = this.materialApplyDao
					.findMaterialBomVersionApply(bomApply);
			for (int j = 0; j < ls.size(); j++) {
				MaterialBomVersionApply bomVersionApply = (MaterialBomVersionApply) ls
						.get(j);

				this.materialApplyDao.deleteAll(this.materialApplyDao
						.findMaterialBomDetailApply(bomVersionApply));
			}
			this.materialApplyDao.deleteAll(ls);
			ls = this.materialApplyDao.findMaterialBomVersionApply(bomApply);
			for (int j = 0; j < ls.size(); j++) {
				MaterialBomVersionApply bomVersionApply = (MaterialBomVersionApply) ls
						.get(j);
				this.materialApplyDao.deleteAll(this.materialApplyDao
						.findMaterialBomDetailApply(bomVersionApply));
			}
			this.materialApplyDao.deleteAll(ls);
			this.materialApplyDao.deleteMaterialBomApply(bomApply);
		}
	}

	/**
	 * 将变更的BOM显示为删除状态
	 * 
	 * @param list
	 *            是MaterialBomMasterApply型，BOM备案记录
	 */
	public void markDeleteMaterialBomApply(List list) {
		for (int i = 0; i < list.size(); i++) {
			MaterialBomMasterApply bomApply = (MaterialBomMasterApply) list
					.get(i);
			List ls = this.materialApplyDao
					.findMaterialBomVersionApply(bomApply);
			for (int j = 0; j < ls.size(); j++) {
				MaterialBomVersionApply bomVersionApply = (MaterialBomVersionApply) ls
						.get(j);
				List lsDetail = this.materialApplyDao
						.findMaterialBomDetailApply(bomVersionApply);
				for (int m = 0; m < lsDetail.size(); m++) {
					MaterialBomDetailApply bomDetailApply = (MaterialBomDetailApply) lsDetail
							.get(m);
					bomDetailApply.setModifyMark(ModifyMarkState.DELETED);
					this.materialApplyDao
							.saveMaterialBomDetailApply(bomDetailApply);
				}
			}
		}
	}

	/**
	 * 将物料单耗增加到物料单耗备案中
	 * 
	 * @param list
	 *            是MaterialBomMaster型，报关常用BOM里的成品
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	public List changeMaterialBomApply(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomMasterApply materialBomApply = (MaterialBomMasterApply) list
					.get(i);
			MaterialBomMasterApply materialBomApplyChange = new MaterialBomMasterApply();
			materialBomApplyChange.setMateriel(materialBomApply.getMateriel());
			materialBomApplyChange.setIsChanged(true);
			materialBomApplyChange.setStateMark(DzscState.CHANGE);
			this.materialApplyDao.saveMaterialBomApply(materialBomApplyChange);
			lsResult.add(materialBomApplyChange);
			List lsVersion = this.materialApplyDao
					.findMaterialBomVersionApply(materialBomApply);
			for (int m = 0; m < lsVersion.size(); m++) {
				MaterialBomVersionApply bomVersionApply = (MaterialBomVersionApply) lsVersion
						.get(m);
				MaterialBomVersionApply bomVersionApplyChange = new MaterialBomVersionApply();
				try {
					PropertyUtils.copyProperties(bomVersionApplyChange,
							bomVersionApply);
				} catch (Exception e) {
					e.printStackTrace();
				}
				bomVersionApplyChange.setId(null);
				bomVersionApplyChange.setBomMasterApply(materialBomApplyChange);
				bomVersionApplyChange.setVersion(bomVersionApply.getVersion());
				bomVersionApplyChange.setBeginDate(bomVersionApply
						.getBeginDate());
				bomVersionApplyChange.setEndDate(bomVersionApply.getEndDate());
				this.materialApplyDao
						.saveMaterialBomVersionApply(bomVersionApplyChange);
				List lsDetail = this.materialApplyDao
						.findMaterialBomDetailApply(bomVersionApply);
				for (int n = 0; n < lsDetail.size(); n++) {
					MaterialBomDetailApply bomDetailApply = (MaterialBomDetailApply) lsDetail
							.get(n);
					MaterialBomDetailApply bomDetailApplyChange = new MaterialBomDetailApply();
					try {
						PropertyUtils.copyProperties(bomDetailApplyChange,
								bomDetailApply);
					} catch (Exception e) {
						e.printStackTrace();
					}
					bomDetailApplyChange.setVersionApply(bomVersionApplyChange);
					bomDetailApplyChange.setId(null);
					bomDetailApplyChange
							.setModifyMark(ModifyMarkState.UNCHANGE);
					this.materialApplyDao
							.saveMaterialBomDetailApply(bomDetailApplyChange);
				}
			}
		}
		return lsResult;
	}

	/**
	 * 删除BOM版本备案记录
	 * 
	 * @param materialBomVersionApply
	 *            BOM版本备案记录
	 */
	public void deleteMaterialBomVersionApply(
			MaterialBomVersionApply materialBomVersionApply, boolean isChange) {
		if (isChange) {
			List lsDetail = this.materialApplyDao
					.findMaterialBomDetailApply(materialBomVersionApply);
			for (int m = 0; m < lsDetail.size(); m++) {
				MaterialBomDetailApply bomDetailApply = (MaterialBomDetailApply) lsDetail
						.get(m);
				bomDetailApply.setModifyMark(ModifyMarkState.DELETED);
				this.materialApplyDao
						.saveMaterialBomDetailApply(bomDetailApply);
			}
		} else {
			this.materialApplyDao.deleteAll(this.materialApplyDao
					.findMaterialBomDetailApply(materialBomVersionApply));
			this.materialApplyDao
					.deleteMaterialBomVersionApply(materialBomVersionApply);
		}
	}

	/**
	 * 变更BOM版本备案记录
	 * 
	 * @param bomVersionApply
	 *            BOM版本备案记录
	 */
	public void changeBomVersion(MaterialBomVersionApply bomVersionApply) {
		MaterialBomVersionApply versionApplyChanged = new MaterialBomVersionApply();
		try {
			PropertyUtils.copyProperties(versionApplyChanged, bomVersionApply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		versionApplyChanged.setId(null);
		this.materialApplyDao.saveMaterialBomVersionApply(versionApplyChanged);
		List lsDetailApply = this.materialApplyDao
				.findMaterialBomDetailApply(bomVersionApply);
		for (int i = 0; i < lsDetailApply.size(); i++) {
			MaterialBomDetailApply detailApply = (MaterialBomDetailApply) lsDetailApply
					.get(i);
			MaterialBomDetailApply detailApplyChanged = new MaterialBomDetailApply();
			try {
				PropertyUtils.copyProperties(detailApplyChanged, detailApply);
			} catch (Exception e) {
				e.printStackTrace();
			}
			detailApplyChanged.setId(null);
			detailApplyChanged.setVersionApply(versionApplyChanged);
			this.materialApplyDao
					.saveMaterialBomDetailApply(detailApplyChanged);
		}

		// MaterialBomVersion oldBomVersion = this.materialManageDao
		// .findMaterialBomVersionById(bomVersionApply.getBomVersion()
		// .getId());
		// BomVersionHistory history = new BomVersionHistory();
		// try {
		// PropertyUtils.copyProperties(history, oldBomVersion);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// history.setBomVersionId(history.getId());
		// history.setId(null);
		// this.materialApplyDao.saveBomVersionHistory(history);
		// bomVersionApply.setStateMark(DzscState.Change);
		// this.materialApplyDao.saveMaterialBomVersionApply(bomVersionApply);
	}

	// /**
	// * 变更BOM版本
	// *
	// * @param list
	// * @return
	// */
	// public void changeBomDetail(MaterialBomVersionApply bomVersionApply,
	// MaterialBomDetail bomDetail) {
	// MaterialBomDetail oldBomDetail = this.materialManageDao
	// .findMaterialBomDetailById(bomDetail.getId());
	// BomDetailHistory history = new BomDetailHistory();
	// try {
	// PropertyUtils.copyProperties(history, oldBomDetail);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// history.setBomDetailId(history.getId());
	// history.setId(null);
	// this.materialApplyDao.saveBomDetailHistory(history);
	// bomVersionApply.setStateMark(DzscState.Change);
	// this.materialApplyDao.saveMaterialBomVersionApply(bomVersionApply);
	// }
	/**
	 * 保存电子手册－单耗申报的版本资料
	 */
	public MaterialBomVersionApply saveMaterialBomVersionApply(
			MaterialBomVersionApply materialBomVersionApply, boolean isChange) {
		this.materialApplyDao
				.saveMaterialBomVersionApply(materialBomVersionApply);
		if (isChange) {
			List list = this.materialApplyDao
					.findMaterialBomDetailApply(materialBomVersionApply);
			for (int i = 0; i < list.size(); i++) {
				MaterialBomDetailApply detail = (MaterialBomDetailApply) list
						.get(i);
				detail.setModifyMark(ModifyMarkState.MODIFIED);
				this.materialApplyDao.saveMaterialBomDetailApply(detail);
			}
		}
		return materialBomVersionApply;
	}

	/**
	 * 删除电子手册－单耗申报的版本资料
	 * @param materialBomDetailApply
	 * @param isChange
	 */
	public void deleteMaterialBomDetailApply(
			MaterialBomDetailApply materialBomDetailApply, boolean isChange) {
		if (isChange) {
			materialBomDetailApply.setModifyMark(ModifyMarkState.DELETED);
			this.materialApplyDao
					.saveMaterialBomDetailApply(materialBomDetailApply);
		} else {
			this.materialApplyDao
					.deleteMaterialBomDetailApply(materialBomDetailApply);
		}
	}

	/**
	 * 申报物料单耗
	 * 
	 */
	public DeclareFileInfo applyMaterialBom(String taskId) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		DzscMaterielHead dzscMaterialHead = null;
		List<DzscMaterielHead> lsMaterielHead = new ArrayList<DzscMaterielHead>();
		List<MaterialBomDetailApply> lsDetail = new ArrayList<MaterialBomDetailApply>();
		List<MaterialBomMasterApply> lsMaster = new ArrayList<MaterialBomMasterApply>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
			dzscMaterialHead.setDeclareDate(new Date());
			dzscMaterialHead.setBomState(DzscState.APPLY);
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
			lsMaterielHead.add(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		List list = this.materialApplyDao.findOriginalMaterialBomApply();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetailApply bomDetailApply = (MaterialBomDetailApply) list
					.get(i);
			MaterialBomMasterApply bomMasterApply = bomDetailApply
					.getVersionApply().getBomMasterApply();
			if (!lsMaster.contains(bomMasterApply)) {
				bomMasterApply.setStateMark(DzscState.APPLY);
				this.materialApplyDao.saveMaterialBomApply(bomMasterApply);
				lsMaster.add(bomMasterApply);
			}
			lsDetail.add(bomDetailApply);
		}
		List<MaterialBomMasterApply> lsMasterChange = new ArrayList<MaterialBomMasterApply>();
		list = this.materialApplyDao.findChangedMaterialBomApply();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetailApply bomDetailApplyChange = (MaterialBomDetailApply) list
					.get(i);
			MaterialBomMasterApply bomMasterApplyChange = bomDetailApplyChange
					.getVersionApply().getBomMasterApply();
			if (!lsMasterChange.contains(bomMasterApplyChange)) {
				bomMasterApplyChange.setStateMark(DzscState.APPLY);
				this.materialApplyDao
						.saveMaterialBomApply(bomMasterApplyChange);
				lsMasterChange.add(bomMasterApplyChange);
			}
			if (ModifyMarkState.UNCHANGE.equals(bomDetailApplyChange
					.getModifyMark())) {
				continue;
			}
			lsDetail.add(bomDetailApplyChange);
		}
		if (lsDetail.size() <= 0) {
			throw new RuntimeException("没有数据可以申报");
		}
		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info,
				dzscMaterialHead.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(dzscMaterialHead.getCopEntNo());
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.MATERIAL_BOM);
		int effectiveCount = this.materialApplyDao
				.findMaterialBomApplyEffectiveCount();
		if (effectiveCount > 0) {
			signInfo.setDeclareType(DzscDelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		String formatFile = "MaterialBOMApplyFormat.xml";
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("MaterialHeadId", lsMaterielHead);
		hmData.put("MaterielBomDataId", lsDetail);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	/**
	 * 处理BOM申报回执
	 * 
	 */
	public String processMaterialBomResult(List lsReturnFile) {
		DzscMaterielHead dzscMaterialHead = null;
		List lsTemp = this.materialApplyDao.findDzscMaterialHead();
		if (lsTemp.size() > 0) {
			dzscMaterialHead = (DzscMaterielHead) lsTemp.get(0);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		return dzscMessageLogic.processMessage(DzscBusinessType.MATERIAL_BOM,
				dzscMaterialHead.getCopEntNo(), new CspProcessMessage() {
					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						backBillMaterialBom();
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						effectiveMaterialBom();
					}
				},lsReturnFile);
	}

	/**
	 * 生效物料单耗
	 * 
	 */
	private void effectiveMaterialBom() {
		// List<MaterialBomVersionApply> lsVersionApply = new
		// ArrayList<MaterialBomVersionApply>();
		List<MaterialBomMasterApply> lsMasterApply = new ArrayList<MaterialBomMasterApply>();
		DzscMaterielHead dzscMaterialHead = this.findDzscMaterielHead();
		if (dzscMaterialHead != null) {
			dzscMaterialHead.setBomState(DzscState.EXECUTE);
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		List list = this.materialApplyDao.findNotEffectiveMaterialBomApply();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetailApply bomDetailApply = (MaterialBomDetailApply) list
					.get(i);
			MaterialBomVersionApply bomVersionApply = bomDetailApply
					.getVersionApply();
			MaterialBomMasterApply bomMasterApply = bomVersionApply
					.getBomMasterApply();
			if (!lsMasterApply.contains(bomMasterApply)) {
				bomMasterApply.setStateMark(DzscState.EXECUTE);
				this.materialApplyDao.saveMaterialBomApply(bomMasterApply);
				lsMasterApply.add(bomMasterApply);
			}
			// if (!lsVersionApply.contains(bomVersionApply)) {
			// MaterialBomVersion materielBomVersion = this.materialManageDao
			// .findMaterielBomVersion(bomVersionApply
			// .getBomMasterApply().getMateriel(),
			// bomVersionApply.getVersion());
			// if (materielBomVersion != null) {
			// materielBomVersion.setBeginDate(bomVersionApply
			// .getBeginDate());
			// materielBomVersion.setEndDate(bomVersionApply.getEndDate());
			// materielBomVersion.setVersion(bomVersionApply.getVersion());
			// this.materialManageDao
			// .saveMaterielBomVersion(materielBomVersion);
			// }
			// lsVersionApply.add(bomVersionApply);
			// }

			// MaterialBomDetail materialBomDetail = this.materialManageDao
			// .findMaterielBomDetail(bomDetailApply.getVersionApply()
			// .getBomMasterApply().getMateriel(), bomDetailApply
			// .getMateriel(), bomDetailApply.getVersionApply()
			// .getVersion());
			// materialBomDetail.setUnitWaste(bomDetailApply.getUnitWaste());
			// materialBomDetail.setUnitUsed(bomDetailApply.getUnitUsed());
			// materialBomDetail.setWaste(bomDetailApply.getWaste());
			// this.materialManageDao.saveMaterielBomDetail(materialBomDetail);
			bomDetailApply.setModifyMark(ModifyMarkState.UNCHANGE);
			this.materialApplyDao.saveMaterialBomDetailApply(bomDetailApply);
		}
		list = this.materialApplyDao.findChangedNotEffectiveMaterialBomApply();
		// List<MaterialBomVersionApply> lsVersionApplyChange = new
		// ArrayList<MaterialBomVersionApply>();
		List<MaterialBomMasterApply> lsMasterApplyChange = new ArrayList<MaterialBomMasterApply>();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetailApply bomDetailApplyChange = (MaterialBomDetailApply) list
					.get(i);
			MaterialBomVersionApply bomVersionApplyChange = bomDetailApplyChange
					.getVersionApply();
			MaterialBomMasterApply bomMasterApplyChange = bomVersionApplyChange
					.getBomMasterApply();
			if (!lsMasterApplyChange.contains(bomMasterApplyChange)) {
				lsMasterApplyChange.add(bomMasterApplyChange);
			}
			// if (!lsVersionApplyChange.contains(bomVersionApplyChange)) {
			// MaterialBomVersion materielBomVersion = this.materialManageDao
			// .findMaterielBomVersion(bomVersionApplyChange
			// .getBomMasterApply().getMateriel(),
			// bomVersionApplyChange.getVersion());
			// if (materielBomVersion != null) {
			// materielBomVersion.setBeginDate(bomVersionApplyChange
			// .getBeginDate());
			// materielBomVersion.setEndDate(bomVersionApplyChange
			// .getEndDate());
			// materielBomVersion.setVersion(bomVersionApplyChange
			// .getVersion());
			// this.materialManageDao
			// .saveMaterielBomVersion(materielBomVersion);
			// }
			// lsVersionApplyChange.add(bomVersionApplyChange);
			// }
			if (ModifyMarkState.DELETED.equals(bomDetailApplyChange
					.getModifyMark())) {
				this.materialApplyDao
						.deleteMaterialBomDetailApply(bomDetailApplyChange);
			} else if (ModifyMarkState.ADDED.equals(bomDetailApplyChange
					.getModifyMark())
					|| ModifyMarkState.MODIFIED.equals(bomDetailApplyChange
							.getModifyMark())) {
				// MaterialBomDetail materialBomDetail = this.materialManageDao
				// .findMaterielBomDetail(bomDetailApplyChange
				// .getVersionApply().getBomMasterApply()
				// .getMateriel(), bomDetailApplyChange
				// .getMateriel(), bomDetailApplyChange
				// .getVersionApply().getVersion());
				// if (materialBomDetail != null) {
				// materialBomDetail.setUnitWaste(bomDetailApplyChange
				// .getUnitWaste());
				// materialBomDetail.setUnitUsed(bomDetailApplyChange
				// .getUnitUsed());
				// materialBomDetail.setWaste(bomDetailApplyChange.getWaste());
				// this.materialManageDao
				// .saveMaterielBomDetail(materialBomDetail);
				// }
				bomDetailApplyChange.setModifyMark(ModifyMarkState.UNCHANGE);
				this.materialApplyDao
						.saveMaterialBomDetailApply(bomDetailApplyChange);
			}
		}
		List<MaterialBomMasterApply> lsMasterApplyDelete = new ArrayList<MaterialBomMasterApply>();
		for (int i = 0; i < lsMasterApplyChange.size(); i++) {
			MaterialBomMasterApply bomMasterApplyChange = (MaterialBomMasterApply) lsMasterApplyChange
					.get(i);
			MaterialBomMasterApply bomMasterApply = this.materialApplyDao
					.findMaterialBomApply(bomMasterApplyChange.getMateriel()
							.getPtNo(), false);
			if (bomMasterApply != null) {
				lsMasterApplyDelete.add(bomMasterApply);
			}
			bomMasterApplyChange.setStateMark(DzscState.EXECUTE);
			bomMasterApplyChange.setIsChanged(false);
			this.materialApplyDao.saveMaterialBomApply(bomMasterApplyChange);
		}
		this.deleteMaterialBomApply(lsMasterApplyDelete);
	}

	/**
	 * 物料BOM申报退单
	 * 
	 */
	private void backBillMaterialBom() {
		DzscMaterielHead dzscMaterialHead = this.findDzscMaterielHead();
		if (dzscMaterialHead != null) {
			int effectiveCount = this.materialApplyDao
					.findMaterialBomApplyEffectiveCount();
			if (effectiveCount > 0) {
				dzscMaterialHead.setBomState(DzscState.CHANGE);
			} else {
				dzscMaterialHead.setBomState(DzscState.ORIGINAL);
			}
			this.materialApplyDao.saveOrUpdate(dzscMaterialHead);
		} else {
			throw new RuntimeException("没有物料表头");
		}
		List<MaterialBomMasterApply> lsMasterApply = new ArrayList<MaterialBomMasterApply>();
		List list = this.materialApplyDao.findNotEffectiveMaterialBomApply();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetailApply bomDetailApply = (MaterialBomDetailApply) list
					.get(i);
			MaterialBomVersionApply bomVersionApply = bomDetailApply
					.getVersionApply();
			MaterialBomMasterApply bomMasterApply = bomVersionApply
					.getBomMasterApply();
			if (!lsMasterApply.contains(bomMasterApply)) {
				bomMasterApply.setStateMark(DzscState.ORIGINAL);
				this.materialApplyDao.saveMaterialBomApply(bomMasterApply);
				lsMasterApply.add(bomMasterApply);
			}
		}
		list = this.materialApplyDao.findChangedNotEffectiveMaterialBomApply();
		List<MaterialBomMasterApply> lsMasterApplyChange = new ArrayList<MaterialBomMasterApply>();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetailApply bomDetailApplyChange = (MaterialBomDetailApply) list
					.get(i);
			MaterialBomVersionApply bomVersionApplyChange = bomDetailApplyChange
					.getVersionApply();
			MaterialBomMasterApply bomMasterApplyChange = bomVersionApplyChange
					.getBomMasterApply();
			if (!lsMasterApplyChange.contains(bomMasterApplyChange)) {
				bomMasterApplyChange.setStateMark(DzscState.CHANGE);
				this.materialApplyDao
						.saveMaterialBomApply(bomMasterApplyChange);
				lsMasterApply.add(bomMasterApplyChange);
			}
		}
	}

	/**
	 * 新增ＢＯＭ版本
	 * 
	 * @param bomMasterApply
	 *            BOM备案记录
	 * @param list
	 *            是MaterialBomVersion型，报关常用BOM里的版本号
	 */
	public void addMaterialBomVersionApply(
			MaterialBomMasterApply bomMasterApply, List<MaterialBomVersion> list) {
		for (MaterialBomVersion bomVersion : list) {
			MaterialBomVersionApply bomVersionApply = new MaterialBomVersionApply();
			bomVersionApply.setBomMasterApply(bomMasterApply);
			bomVersionApply.setVersion(bomVersion.getVersion());
			bomVersionApply.setBeginDate(bomVersion.getBeginDate());
			bomVersionApply.setEndDate(bomVersion.getEndDate());
			// bomVersionApply.setStateMark(DzscState.ORIGINAL);
			// bomVersionApply.setIsChanged(false);
			this.materialApplyDao.saveMaterialBomVersionApply(bomVersionApply);
			List lsDetail = this.materialManageDao
					.findMaterielBomDetail(bomVersion);
			for (int j = 0; j < lsDetail.size(); j++) {
				MaterialBomDetail bomDetail = (MaterialBomDetail) lsDetail
						.get(j);
				MaterialBomDetailApply bomDetailApply = new MaterialBomDetailApply();
				bomDetailApply.setVersionApply(bomVersionApply);
				bomDetailApply.setMateriel(bomDetail.getMateriel());
				bomDetailApply.setUnitUsed(bomDetail.getUnitUsed());
				bomDetailApply.setUnitWaste(bomDetail.getUnitWaste());
				bomDetailApply.setWaste(bomDetail.getWaste());
				bomDetailApply.setNote(bomDetail.getNote());
				bomDetailApply.setModifyMark(ModifyMarkState.ADDED);
				this.materialApplyDao
						.saveMaterialBomDetailApply(bomDetailApply);
			}
		}
	}

	/**
	 * 新增ＢＯＭ料件
	 * 
	 * @param bomVersionApply
	 *            BOM备案记录
	 * @param list
	 *            是MaterialBomDetail型，报关常用BOM里的料件
	 */
	public List addMaterialBomDetailApply(
			MaterialBomVersionApply bomVersionApply,
			List<MaterialBomDetail> list) {
		List lsResult = new ArrayList();
		for (MaterialBomDetail bomDetail : list) {
			MaterialBomDetailApply bomDetailApply = new MaterialBomDetailApply();
			bomDetailApply.setVersionApply(bomVersionApply);
			bomDetailApply.setMateriel(bomDetail.getMateriel());
			bomDetailApply.setUnitUsed(bomDetail.getUnitUsed());
			bomDetailApply.setUnitWaste(bomDetail.getUnitWaste());
			bomDetailApply.setWaste(bomDetail.getWaste());
			bomDetailApply.setNote(bomDetail.getNote());
			bomDetailApply.setModifyMark(ModifyMarkState.ADDED);
			this.materialApplyDao.saveMaterialBomDetailApply(bomDetailApply);
			lsResult.add(bomDetailApply);
		}
		return lsResult;
	}

	/**
	 * 取消物料被归并禁用
	 * 
	 * @param list
	 */
	public void cancelForbidMergeMaterialApply(List list) {
		for (int i = 0; i < list.size(); i++) {
			MaterialApply materialApply = (MaterialApply) list.get(i);
			if (materialApply.getIsForbidMerge() != null
					&& materialApply.getIsForbidMerge()) {
				materialApply.setIsForbidMerge(false);
				this.materialApplyDao.saveMaterialApply(materialApply);
			}
		}
	}

	/**
	 * 根据BOM版本来抓取BOM明细
	 * 
	 * @param list
	 * @return
	 */
	private List getBomDetailApplyByVersionApply(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			MaterialBomVersionApply versionApply = (MaterialBomVersionApply) list
					.get(i);
			lsResult.addAll(this.materialApplyDao
					.findMaterialBomDetailApply(versionApply));
		}
		return lsResult;
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPBomMessage(String taskId, String messageFileName,
			TempExpQPBomMsgSelectParam param) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要要导出的资料");
		}
		List list = new ArrayList();
		if (param.isPart()) {
			list = getBomDetailApplyByVersionApply(param.getLsPartData());
		} else {
			list = this.materialApplyDao.findMaterialBomDetailApply();
		}
		System.out.println("Begin get data:" + System.currentTimeMillis());

		String formatFile = "BomFormat.xml";
		Map<String, List> hmData = new HashMap<String, List>();
		hmData.put("BomId", list);
		// dzscMessageLogic.exportQPMessage(messageFileName,
		// BusinessType.MATERIAL_BOM, formatFile, hmData, info);
	}
}
