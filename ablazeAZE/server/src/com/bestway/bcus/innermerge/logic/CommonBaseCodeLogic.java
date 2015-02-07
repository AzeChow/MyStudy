/*
 * Created on 2004checkFileData-7-2
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;

// 李扬更改程序
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.cas.entity.BillTemp;
//import com.bestway.bcus.client.common.CommonVars;
//import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.dao.ParameterCodeDao;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeBeforeData;
import com.bestway.bcus.innermerge.entity.ReverseMergeFourData;
import com.bestway.bcus.innermerge.entity.ReverseMergeTenData;
import com.bestway.bcus.innermerge.entity.TempAutoInnerMergeParam;
import com.bestway.bcus.innermerge.entity.TempInnerMergeData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonBaseCodeLogic {
	private CommonBaseCodeDao commonBaseCodeDao;

	private EmsEdiTrDao emsEdiTrDao = null;

	private HsCodeDao hsCodeDao;

	private MaterialManageDao materialManageDao;

	private ParameterCodeDao paramerterCodeDao;

	private SortInnerMergeNo sortInnerMergeNo;

	private HandInnerMerge handInnerMerge;

	private AutoInnerMerge autoInnerMerge;

	private CustomInnerMerge customInnerMerge;

	private ImportFromOther importFromOther;

	private ReverseMerge reverseMerge;

	private TransferTempMateriel transferTempMateriel;

	private List bomList = null;

	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	public HsCodeDao getHsCodeDao() {
		return hsCodeDao;
	}

	public void setHsCodeDao(HsCodeDao hsCodeDao) {
		this.hsCodeDao = hsCodeDao;
	}

	public ParameterCodeDao getParamerterCodeDao() {
		return paramerterCodeDao;
	}

	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	public void setParamerterCodeDao(ParameterCodeDao paramerterCodeDao) {
		this.paramerterCodeDao = paramerterCodeDao;
	}

	public List findInnerMergeDataByType(String type) {
		return this.commonBaseCodeDao.findInnerMergeDataByType(type);
	}

	public List findInnerMergeData() {
		return this.commonBaseCodeDao.findInnerMergeData();
	}

	public InnerMergeData findInnerMergeDataById(String id) {
		return this.commonBaseCodeDao.findInnerMergeDataById(id);
	}

	public InnerMergeData saveInnerMergeData(InnerMergeData innerMergeData) {
		this.commonBaseCodeDao.saveInnerMergeData(innerMergeData);
		return innerMergeData;
	}

	public void handleEmsEdiMergerExgBefore(List list,
			EmsEdiMergerExgAfter emsExgAfter, BaseCompany company,
			String isSendSign, EmsEdiMergerHead emsEdiMergerHead) {
		EmsEdiMergerExgBefore emsExgBefore = null;
		for (int i = 0; i < list.size(); i++) {
			emsExgBefore = (EmsEdiMergerExgBefore) list.get(i);
			emsExgBefore.setEmsEdiMergerExgAfter(emsExgAfter);
			emsExgBefore.setCompany(company);
			emsExgBefore.setModifyMark(ModifyMarkState.ADDED);
			if (isSendSign != null && "1".equals(isSendSign)) {
				emsExgBefore.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			emsExgBefore.setModifyTimes(emsEdiMergerHead.getModifyTimes());
			emsExgBefore.setChangeDate(new Date());
			emsEdiTrDao.saveEmsEdiMergerExgBefore(emsExgBefore);
			InnerMergeData data = emsExgBefore.getInnerMergerData();
			data.setIsExistMerger(new Boolean(true));
			this.saveInnerMergeData(data);
		}
	}

	public void handleEmsEdiMergerImgBefore(List list,
			EmsEdiMergerImgAfter emsImgAfter, BaseCompany company,
			String isSendSign, EmsEdiMergerHead emsEdiMergerHead) {
		EmsEdiMergerImgBefore emsImgBefore = null;
		for (int i = 0; i < list.size(); i++) {
			emsImgBefore = (EmsEdiMergerImgBefore) list.get(i);
			emsImgBefore.setEmsEdiMergerImgAfter(emsImgAfter);
			emsImgBefore.setCompany(company);
			emsImgBefore.setModifyMark(ModifyMarkState.ADDED);
			if (isSendSign != null && "1".equals(isSendSign)) {
				emsImgBefore.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			emsImgBefore.setModifyTimes(emsEdiMergerHead.getModifyTimes());
			emsImgBefore.setChangeDate(new Date());
			emsEdiTrDao.saveEmsEdiMergerImgBefore(emsImgBefore);
			InnerMergeData data = emsImgBefore.getInnerMergerData();
			data.setIsExistMerger(new Boolean(true));
			this.saveInnerMergeData(data);
		}
	}

	public void deleteInnerMergeData(InnerMergeData innerMergeData) {
		List list = new ArrayList();
		list.add(innerMergeData);
		Materiel obj = innerMergeData.getMateriel();
		obj.setIsNewMateriel(new Boolean(true));// 是否新增物料主档
		materialManageDao.saveMateriel(obj);
		this.getReverseMerge().deleteReverseMergeCausedByDeleteInnerMerge(list);
		this.commonBaseCodeDao.deleteInnerMergeData(innerMergeData);
	}

	// 强制性删除归并关系
	public void deleteInner(InnerMergeData inner) {
		Materiel obj = inner.getMateriel();
		obj.setIsNewMateriel(new Boolean(true));
		materialManageDao.saveMateriel(obj);
		this.commonBaseCodeDao.deleteInnerMergeData(inner);
	}

	public void deleteInnerMergeData(List list) {
		this.getReverseMerge().deleteReverseMergeCausedByDeleteInnerMerge(list);
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData obj = (InnerMergeData) list.get(i);
			Materiel m = obj.getMateriel();
			m.setIsNewMateriel(new Boolean(true));
			materialManageDao.saveMateriel(m);
			commonBaseCodeDao.deleteInnerMergeData(obj);
		}
		// this.commonBaseCodeDao.deleteInnerMergeData(list);
	}

	private HandInnerMerge getHandInnerMerge() {
		if (handInnerMerge == null) {
			handInnerMerge = new HandInnerMerge();
			handInnerMerge.setCommonBaseCodeDao(this.getCommonBaseCodeDao());
		}
		return handInnerMerge;
	}

	/**
	 * 取得内部归并报表的数据
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeReportData(String type) {
		List list = this.commonBaseCodeDao
				.findTenFourIsnotNullInnerMergeDataByType(type);
		list.addAll(this.commonBaseCodeDao
				.findTenNotNullFourIsnullInnerMergeDataByType(type));
		list.addAll(this.commonBaseCodeDao
				.findTenFourIsNullInnerMergeDataByType(type));
		return list;
	}

	/**
	 * 检查所选择的数据能否进行10位归并 如果数据有效则并且归并后的10位商品编码全部为空返回0，归并后的10位商品编码只要有一不为空返回1。；否则，
	 * 如果有编码不同的数据返回-1； 申报计量单位不同返回-2； 商品名称不同返回-3； 如果全部归并的话 返回-4；
	 * 如果选择的数据的备案序号不同返回-5。
	 * 
	 * @param list
	 * @return
	 */
	public int checkDataForTenMerge(List list) {
		return getHandInnerMerge().checkDataForTenMerge(list);
	}

	/**
	 * 10位商品归并。
	 * 
	 * @param list
	 *            归并的数据（一笔或多笔）
	 * @param afterComplex
	 *            归并后的10位商品
	 * @param afterMemoUnit
	 *            归并后的备案单位
	 * @param afterTenMaterielName
	 *            归并后的商品名称
	 * @param afterTenMaterielSpec
	 *            归并后的商品规格
	 * @param isNew
	 *            如果为true代表list中的数据重新归并到一新类型中， 如果为false，list中的数据归并到
	 *            list中数据已有的类型中。
	 */
	public void tenInnerMerge(List list, Complex afterComplex,
			Unit afterMemoUnit, String afterTenMaterielName,
			String afterTenMaterielSpec, Unit afterLegalUnit,
			Unit afterSecondLegalUnit, boolean isNew) {
		getHandInnerMerge().tenInnerMerge(list, afterComplex, afterMemoUnit,
				afterTenMaterielName, afterTenMaterielSpec, afterLegalUnit,
				afterSecondLegalUnit, isNew);
	}

	/**
	 * 取消10为归并
	 * 
	 * @param list
	 */
	public void unDoTenInnerMerge(List list) {
		getHandInnerMerge().unDoTenInnerMerge(list);
	}

	/**
	 * 检查能否进行撤消10位归并动作。 如果允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消。
	 * 
	 * @param list
	 * @return
	 */
	public int checkDataForUndoTenInnerMerge(List list) {
		return getHandInnerMerge().checkDataForUndoTenInnerMerge(list);
	}

	/**
	 * 检查所选择的数据能否进行4位归并。 如果检查结果允许归并 返回0； 如果所选择的数据其中有没有经过10位归并的 返回 -1；
	 * 如果所选择的数据的10位商品编码的前4位不同的返回 -2； 如果所选择的已经归并过的数据有不同编码序号的 返回-3; 如果全部已归并返回-4。
	 * 
	 * @param list
	 * @return
	 */
	public int checkDataForFourInnerMerge(List list) {
		return getHandInnerMerge().checkDataForFourInnerMerge(list);
	}

	/**
	 * 4位商品归并 10位商品编码的前4位相同，并且属于同一类商品。
	 * 
	 * @param list
	 * @param isNew
	 */
	public void fourInnerMerge(List list, String fourCommodityName,
			boolean isNew) {
		getHandInnerMerge().fourInnerMerge(list, fourCommodityName, isNew);
	}

	/**
	 * 取消4位商品归并。
	 * 
	 * @param list
	 */
	public void undoFourInnerMerge(List list) {
		this.getReverseMerge().deleteReverseMergeCausedByUndoFourInnerMerge(
				list);
		getHandInnerMerge().undoFourInnerMerge(list);
	}

	private SortInnerMergeNo getSortInnerMergeNo() {
		if (sortInnerMergeNo == null) {
			sortInnerMergeNo = new SortInnerMergeNo();
			sortInnerMergeNo.setCommonBaseCodeDao(this.getCommonBaseCodeDao());
		}
		return sortInnerMergeNo;
	}

	/**
	 * 检查数据为四位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 重排数据中有空行 -4:
	 * 选择的行号在选择的行中,不能进行重排
	 */
	public int checkDataForFourInnerMergeSort(List selectedRows, int toRowNumber) {
		return getSortInnerMergeNo().checkDataForFourInnerMergeSort(
				selectedRows, toRowNumber);
	}

	/**
	 * 检查数据为十位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 重排数据中有空行 -4:
	 * 选择的行号在选择的行中,不能进行重排
	 */
	public int checkDataForTenInnerMergeSort(List selectedRows, int toRowNumber) {
		return getSortInnerMergeNo().checkDataForTenInnerMergeSort(
				selectedRows, toRowNumber);
	}

	public void reSortMergeFourNo(List selectedRows, List allRows, int toNo) {
		getSortInnerMergeNo().reSortMergeFourNo(selectedRows, allRows, toNo);
	}

	public void reSortMergeTenNo(List selectedRows, List allRows, int toNo) {
		getSortInnerMergeNo().reSortMergeTenNo(selectedRows, allRows, toNo);
	}

	public void reSortMergeTenNo2(List selectedRows, int toNo) {
		getSortInnerMergeNo().reSortMergeTenNo2(selectedRows, toNo);
	}

	public void reSortMergeFourNo2(List selectedRows, int toNo) {
		getSortInnerMergeNo().reSortMergeFourNo2(selectedRows, toNo);
	}

	private AutoInnerMerge getAutoInnerMerge() {
		if (autoInnerMerge == null) {
			autoInnerMerge = new AutoInnerMerge();
			autoInnerMerge.setCommonBaseCodeDao(this.getCommonBaseCodeDao());
		}
		return autoInnerMerge;
	}

	public void autoInnerMergeData(TempAutoInnerMergeParam param) {
		getAutoInnerMerge().innerMergeData(param);
	}

	private CustomInnerMerge getCustomInnerMerge() {
		if (customInnerMerge == null) {
			customInnerMerge = new CustomInnerMerge();
			customInnerMerge.setCommonBaseCodeDao(this.getCommonBaseCodeDao());
		}
		return customInnerMerge;
	}

	public void customInnerMerge() {
		getCustomInnerMerge().customInnerMergeAll();
	}

	private ImportFromOther getImportFromOther() {
		if (importFromOther == null) {
			importFromOther = new ImportFromOther();
			importFromOther.setCommonBaseCodeDao(this.getCommonBaseCodeDao());
			importFromOther.setHsCodeDao(this.getHsCodeDao());
			importFromOther.setParamerterCodeDao(this.getParamerterCodeDao());
			importFromOther.setMaterialManageDao(this.getMaterialManageDao());
		}
		return importFromOther;
	}

	public void importDataFromMaterial(List materielTypeList) {
		getImportFromOther().importDataFromMaterial(materielTypeList);
	}

	public void importDataFromTxtFile(List list) {
		getImportFromOther().importDataFromTxtFile(list);
	}

	public List checkFileData(List list, Hashtable ht, String materielType) {
		return getImportFromOther().checkFileData(list, ht, materielType);
	}

	/**
	 * @return Returns the transferTempMateriel.
	 */
	private TransferTempMateriel getTransferTempMateriel() {
		if (transferTempMateriel == null) {
			transferTempMateriel = new TransferTempMateriel();
			transferTempMateriel.setCommonBaseCodeDao(this.commonBaseCodeDao);
			transferTempMateriel.setEmsEdiTrDao(this.emsEdiTrDao);
			transferTempMateriel.setMaterialManageDao(this
					.getMaterialManageDao());
		}
		return transferTempMateriel;
	}

	/**
	 * 查找物料主档来自类别-->进出口报关申请单返回 TempMateriel
	 */
	public List findMaterielByImpExpRequestBillType(String materielType,
			String impExpRequestBillId, boolean isFilter) {
		return this.getTransferTempMateriel()
				.findMaterielByImpExpRequestBillAndType(materielType,
						impExpRequestBillId, isFilter);
	}

	/**
	 * 根据料件查询类型查找物料主档来自类别-->进出口报关申请单返回 TempMateriel
	 */
	public List findMaterielByMaterielPara(String materielType,
			String impExpRequestBillId, boolean isFilter, String value,
			String para) {
		List<TransferTempMateriel> list = this.getTransferTempMateriel()
				.findMaterielByImpExpRequestBillAndType(materielType,
						impExpRequestBillId, isFilter);

		return null;
	}

	/**
	 * 查找物料主档来自类别-->关封申请单
	 */
	public List findMaterielByCustomsEnvelopRequestBillType(
			String materielType, String customsEnvelopRequestId) {
		return this.getTransferTempMateriel()
				.findMaterielByCustomsEnvelopRequestBillType(materielType,
						customsEnvelopRequestId);
	}

	/**
	 * 查找物料主档来自类别-->结转单据(转厂)
	 */
	public List findMaterielByTransferFactoryBillType(String materielType) {
		return this.getTransferTempMateriel()
				.findMaterielByTransferFactoryBillType(materielType);
	}

	/**
	 * 查找物料主档来自类别-->结转初始化单据(转厂)
	 */
	public List findMaterielByTransferFactoryInitBillType(String materielType,
			String initBillId) {
		return this.getTransferTempMateriel()
				.findMaterielByTransferFactoryInitBillType(materielType,
						initBillId);
	}

	private ReverseMerge getReverseMerge() {
		if (reverseMerge == null) {
			reverseMerge = new ReverseMerge();
			reverseMerge.setCommonBaseCodeDao(this.getCommonBaseCodeDao());
			reverseMerge.setMaterialManageDao(this.getMaterialManageDao());
		}
		return reverseMerge;
	}

	/**
	 * 根据物料类别查找反向归并4码数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List findReverseMergeFourDataByType(String materielType) {
		return this.getReverseMerge().findReverseMergeFourDataByType(
				materielType);
	}

	/**
	 * 把归并关系中的第三层资料插入ReverseMergeFourData类中
	 * 
	 * @param materielType
	 * 
	 */
	public void addInnerMergeDataToReverseMergeFourData(String materielType) {
		this.getReverseMerge().addInnerMergeDataToReverseMergeFourData(
				materielType);
	}

	/**
	 * 把归并关系中的第2层资料插入ReverseMergeTenData类中
	 * 
	 * @param materielType
	 * 
	 */
	public void addInnerMergeDataToReverseMergeTenData(String materielType) {
		this.getReverseMerge().addInnerMergeDataToReverseMergeTenData(
				materielType);
	}

	/**
	 * 把归并关系中的第一层资料插入ReverseMergeBeforeData类中
	 * 
	 * @param materielType
	 * 
	 */
	public void addInnerMergeDataToReverseMergeBeforeData(String materielType) {
		this.getReverseMerge().addInnerMergeDataToReverseMergeBeforeData(
				materielType);
	}

	/**
	 * 保存反向归并4码数据 同时更新此相关的内部归并数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeFourData(ReverseMergeFourData fourData) {
		this.getReverseMerge().saveReverseMergeFourData(fourData);
	}

	/**
	 * 删除反向归并4码数据并且同时删除10码数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeFourData(ReverseMergeFourData fourData) {
		this.getReverseMerge().deleteReverseMergeFourData(fourData);
	}

	/**
	 * 根据凡响归并4码数据查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByFour(ReverseMergeFourData fourData) {
		return this.getReverseMerge().findReverseMergeTenDataByFour(fourData);
	}

	/**
	 * 根据物料类别查找反向归并10码数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeTenDataByType(String materielType) {
		return this.getReverseMerge().findReverseMergeTenDataByType(
				materielType);
	}

	/**
	 * 保存反向归并10码数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeTenData(ReverseMergeTenData tenData) {
		this.getReverseMerge().saveReverseMergeTenData(tenData);
	}

	/**
	 * 删除反向归并10码数据 同时删除或者更新此10码所关联的归并前数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeTenData(ReverseMergeTenData tenData) {
		this.getReverseMerge().deleteReverseMergeTenData(tenData);
	}

	/**
	 * 根据反向归并后10码数据查找反向归并数据
	 * 
	 * @param fourData
	 * @return
	 */
	public List findReverseMergeBeforeDataByTen(ReverseMergeTenData tenData) {
		return this.getReverseMerge().findReverseMergeBeforeDataByTen(tenData);
	}

	/**
	 * 保存反向归并前数据 同时更新此关联的物料数据和内部归并数据
	 * 
	 * @param data
	 */
	public void saveReverseMergeBeforeData(ReverseMergeBeforeData beforeData) {
		this.getReverseMerge().saveReverseMergeBeforeData(beforeData);
	}

	/**
	 * 删除反向归并前数据 并同时要删除由此产生的内部归并数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeBeforeData(ReverseMergeBeforeData beforeData) {
		this.getReverseMerge().deleteReverseMergeBeforeData(beforeData);
	}

	/**
	 * 新增反向归并前数据 同时生成新的内部归并数据
	 * 
	 * @param list
	 */
	public void addReverseMergeBeforeData(List list, ReverseMergeTenData tenData) {
		this.getReverseMerge().addReverseMergeBeforeData(list, tenData);
	}

	/**
	 * 将内部归并10码数据和反向归并4码数据对接起来
	 * 
	 */
	public void concatInnerTenAndReverseFour(List innerTenDatas,
			ReverseMergeFourData fourData) {
		this.getReverseMerge().concatInnerTenAndReverseFour(innerTenDatas,
				fourData);
	}

	/**
	 * 将内部归并前数据和反向归并10码数据对接起来
	 * 
	 */
	public void concatInnerBeforeAndReverseTen(List innerBeforeDatas,
			ReverseMergeTenData tenData) {
		this.getReverseMerge().concatInnerBeforeAndReverseTen(innerBeforeDatas,
				tenData);
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
		return this.getReverseMerge().findTenDataNotFourMerge(fourData,
				materielType);
	}

	/**
	 * 查询未进行10码归并的内部归并数据
	 * 
	 * @param materielType
	 * @return
	 */
	public List findBeforeDataNotTenMerge(String materielType) {
		return this.getReverseMerge().findBeforeDataNotTenMerge(materielType);
	}

	/**
	 * 查询不在内部归并中存在的物料基本资料
	 * 
	 * @param materielType
	 * @return
	 */
	public List findMaterielNotInInnerMerge(String materielType) {
		return this.getReverseMerge().findMaterielNotInInnerMerge(materielType);
	}

	/**
	 * 返回反向归并10码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	public int findMaxTenReverseMergeNo(String type) {
		return getReverseMerge().findMaxTenReverseMergeNo(type);
	}

	/**
	 * 返回反向归并4码最大的归并序号
	 * 
	 * @param type
	 * @return
	 */
	public int findMaxFourReverseMergeNo(String type) {
		return getReverseMerge().findMaxFourReverseMergeNo(type);
	}

	/**
	 * 判断有哪些内部归并数据是否做了经营范围的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findInnerMergeDataInEmsEdiTr(List lsInnerMergeData) {
		return getReverseMerge().findInnerMergeDataInEmsEdiTr(lsInnerMergeData);
	}

	/**
	 * 判断四码是否有其它十码对应
	 * 
	 * @param selectedInnerMergeData
	 * @return
	 */
	public boolean findInnerMergeDataByFilter(List selectedInnerMergeData) {
		return getReverseMerge().findInnerMergeDataByFilter(
				selectedInnerMergeData);
	}

	/**
	 * 检查反向归并4码相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseFourDataPutOnRecord(
			ReverseMergeFourData fourData) {
		return getReverseMerge().checkWhetherReverseFourDataPutOnRecord(
				fourData);
	}

	/**
	 * 检查反向归并10码相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseTenDataPutOnRecord(
			ReverseMergeTenData tenData) {
		return getReverseMerge().checkWhetherReverseTenDataPutOnRecord(tenData);
	}

	/**
	 * 检查反向归并前相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseBeforeDataPutOnRecord(
			ReverseMergeBeforeData beforeData) {
		return getReverseMerge().checkWhetherReverseBeforeDataPutOnRecord(
				beforeData);
	}

	/**
	 * 检查反向归并4码相关联的内部归并数据是否做了经营范围的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseFourDataPutOnRecord(List lsFourData) {
		return getReverseMerge().checkWhetherReverseFourDataPutOnRecord(
				lsFourData);
	}

	/**
	 * 检查反向归并10码相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseTenDataPutOnRecord(List lsTenData) {
		return getReverseMerge().checkWhetherReverseTenDataPutOnRecord(
				lsTenData);
	}

	/**
	 * 检查反向归并前相关联的内部归并数据是否做了归并关系的备案
	 * 
	 * @param fourData
	 * @return
	 */
	public boolean checkWhetherReverseBeforeDataPutOnRecord(List lsBeforeData) {
		return getReverseMerge().checkWhetherReverseBeforeDataPutOnRecord(
				lsBeforeData);
	}

	/**
	 * 删除反向归并4码数据并且同时删除10码数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeFourData(List lsFourData) {
		getReverseMerge().deleteReverseMergeFourData(lsFourData);
	}

	/**
	 * 删除反向归并10码数据 同时删除或者更新此10码所关联的归并前数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeTenData(List lsTenData) {
		getReverseMerge().deleteReverseMergeTenData(lsTenData);
	}

	/**
	 * 删除反向归并前数据 并同时要删除由此产生的内部归并数据
	 * 
	 * @param data
	 */
	public void deleteReverseMergeBeforeData(List lsBeforeData) {
		getReverseMerge().deleteReverseMergeBeforeData(lsBeforeData);
	}

	/**
	 * 判断内部归并后数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findInnerMergeInEmsEdiMergeAfter(List lsInnerMergeData) {
		return getReverseMerge().findInnerMergeInEmsEdiMergeAfter(
				lsInnerMergeData);
	}

	/**
	 * 判断内部归并后数据是否做了电子帐册的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findWhetherInnerMergeInEmsHeadH2k(List lsInnerMergeData) {
		return getReverseMerge().findWhetherInnerMergeInEmsHeadH2k(
				lsInnerMergeData);
	}

	/**
	 * 判断反向归并数据是否做了电子帐册的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findWhetherReverseMergeInEmsHeadH2k(List lsInnerMergeData) {
		return getReverseMerge().findWhetherReverseMergeInEmsHeadH2k(
				lsInnerMergeData);
	}

	/**
	 * 判断内部归并前数据是否做了归并关系的备案
	 * 
	 * @param innerMergeData
	 * @return
	 */
	public List findInnerMergeInEmsEdiMergeBefore(List lsInnerMergeData) {
		return getReverseMerge().findInnerMergeInEmsEdiMergeBefore(
				lsInnerMergeData);
	}

	/**
	 * 检查数据为十位归并重排数据 0 : 成功 -1: 当前没有选择的项 -2: 重排行号超出范围 -3: 在多个四位归并类别中不能重排 -4:
	 * 重排行数超出范围
	 */
	public int checkTenInnerMergeDataInFourMerge(List selectedRows,
			int toRowNumber) {
		return getSortInnerMergeNo().checkTenInnerMergeDataInFourMerge(
				selectedRows, toRowNumber);
	}

	private String DouToStr(Object obj) {
		if (obj != null) {
			return String.valueOf(obj);
		}
		return null;
	}

	// 关务与物流对照表
	public List getInnerMergeDataToCollate(String type, String seqNum,
			String tenName) {
		List<Object> tempLists = new Vector<Object>();
		List list = commonBaseCodeDao.findInnerMergeDataToCollate(type, seqNum,
				tenName);
		for (int i = 0; i < list.size(); i++) {
			BillTemp temp = new BillTemp();
			Object[] obj = (Object[]) list.get(i);
			temp.setBill1(String.valueOf(obj[0]));// 序号
			temp.setBill2((String) obj[1]);// 编码
			temp.setBill3((String) obj[2]);// 名称
			temp.setBill4((String) obj[3]);// 规格
			temp.setBill5((String) obj[4]);// 单位
			tempLists.add(temp);
		}
		return tempLists;
	}

	private Double getConvert(String materielName, String customsName) {
		List list = materialManageDao.findCalUnitByUnitName(materielName,
				customsName);
		if (list != null && list.size() > 0) {
			return ((CalUnit) list.get(0)).getScale();
		}
		return Double.valueOf(0);
	}

	// 初始化单位折算比例
	public void initUnitDedault() {
		List list = commonBaseCodeDao.findMaterielFromInnerMergeData(); // 得到所有内部归并
		if (list == null) {
			return;
		}
		System.out.println("-- 共：" + list.size() + " 笔记录");
		ArrayList vec = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] inner = (Object[]) list.get(i);
			Materiel materiel = (Materiel) inner[0];
			String customUnit = (String) inner[1];
			if (materiel != null && materiel.getCalUnit() != null
					&& customUnit != null) {
				Double convert = getConvert(materiel.getCalUnit().getName(),
						(String) inner[1]);
				materiel.setUnitConvert(convert.doubleValue() == Double
						.valueOf(0) ? materiel.getUnitConvert() : convert);
				vec.add(materiel);
			}
			if (i % 10000 == 0) {
				System.out.println("-- 已经初始化：" + i + " 笔记录");
				System.gc();
			}
		}
		for (int j = 0; j < vec.size(); j++) {
			Materiel materiel = (Materiel) vec.get(j);
			materialManageDao.saveMateriel(materiel);
			if (j % 1000 == 0) {
				System.out.println("-- 已经初始化：" + j + " 笔记录");
				System.gc();
			}
		}

	}

	public void initDzscUnitDedault() {
		List list = commonBaseCodeDao.findMaterielFromInnerMergeData(); // 得到所有内部归并
		if (list == null) {
			return;
		}
		System.out.println("-- 共：" + list.size() + " 笔记录");
		ArrayList vec = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] inner = (Object[]) list.get(i);
			Materiel materiel = (Materiel) inner[0];
			String customUnit = (String) inner[1];
			if (materiel != null && materiel.getCalUnit() != null
					&& customUnit != null) {
				Double convert = getConvert(materiel.getCalUnit().getName(),
						(String) inner[1]);
				materiel.setUnitConvert(convert);
				vec.add(materiel);
			}
			if (i % 10000 == 0) {
				System.out.println("-- 已经初始化：" + i + " 笔记录");
				System.gc();
			}
		}
		for (int j = 0; j < vec.size(); j++) {
			Materiel materiel = (Materiel) vec.get(j);
			materialManageDao.saveMateriel(materiel);
			if (j % 1000 == 0) {
				System.out.println("-- 已经初始化：" + j + " 笔记录");
				System.gc();
			}
		}

	}

	/**
	 * 导入内部归并来自物料主档
	 * 
	 * @param materielList
	 */
	public List importInnerMergeDataFromMateriel(List materielList,
			String materielType) {
		List<InnerMergeData> returnList = new ArrayList<InnerMergeData>();
		for (int i = 0; i < materielList.size(); i++) {
			Materiel materiel = (Materiel) materielList.get(i);

			InnerMergeData b = new InnerMergeData();
			b.setCompany(materiel.getCompany());
			b.setImrType(materielType);
			b.setMateriel(materiel);
			b.setHsBeforeLegalUnit(materiel.getComplex() == null ? null
					: materiel.getComplex().getFirstUnit());
			b.setHsBeforeEnterpriseUnit(materiel.getCalUnit());
			Date importTimer = null;
			int count = this.commonBaseCodeDao
					.findCountByInnerMergeData(materiel.getScmCoi()
							.getCoiProperty());
			if (count <= 0) { // 如果是没有一条数据导入
				importTimer = null; // 第一次不用标识
			} else {
				importTimer = new Date();
			}
			b.setImportTimer(importTimer);
			this.commonBaseCodeDao.saveInnerMergeData(b);

			materiel.setIsNewMateriel(new Boolean(false));// 不为新增得物料主档
			materialManageDao.saveMateriel(materiel);

			returnList.add(b);
		}
		return returnList;
	}

	public void saveInner(InnerMergeData inner, Materiel materiel) {
		List mlist = materialManageDao.findEnterpriseMaterialByptNo(materiel
				.getPtNo());
		if (mlist.size() > 0) {
			EnterpriseMaterial enterpriseMaterial = (EnterpriseMaterial) mlist
					.get(0);
			String id = enterpriseMaterial.getId();
			try {
				PropertyUtils.copyProperties(enterpriseMaterial, materiel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			enterpriseMaterial.setId(id);
			materialManageDao.saveOrUpdate(enterpriseMaterial);
		}
		materialManageDao.saveMateriel(materiel);
		inner.setMateriel(materiel);
		commonBaseCodeDao.saveInnerMergeData(inner);
	}

	public void deleteInnerNoten(String type) {
		List list = commonBaseCodeDao.findInnerByNoten(type);
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData obj = (InnerMergeData) list.get(i);
			Materiel materiel = obj.getMateriel();
			materiel.setIsNewMateriel(new Boolean(true));// 删除未归并
			materialManageDao.saveMateriel(materiel);
			this.commonBaseCodeDao.deleteInnerMergeData(obj);
		}

	}

	private String ObjToStr(Object obj) {
		if (obj == null || obj.equals("")) {
			return null;
		}
		return String.valueOf(obj);
	}

	public List getDistinctTenInner(String materielType) {
		List list = commonBaseCodeDao.findDistinctTenInner(materielType);
		List innerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BillTemp bill = new BillTemp();
			Object[] obj = (Object[]) list.get(i);
			bill.setBill1(ObjToStr(obj[0]));
			bill.setBill2(ObjToStr(obj[1]));
			bill.setBill3(ObjToStr(obj[2]));
			bill.setBill4(ObjToStr(obj[3]));
			bill.setBill5(ObjToStr(obj[4]));
			innerList.add(bill);
		}
		return innerList;
	}

	public List getDistinctFourceInner(String materielType) {
		List list = commonBaseCodeDao.findDistinctFourceInner(materielType);
		List innerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BillTemp bill = new BillTemp();
			Object[] obj = (Object[]) list.get(i);
			bill.setBill1(ObjToStr(obj[0]));
			bill.setBill2(ObjToStr(obj[1]));
			bill.setBill3(ObjToStr(obj[2]));
			innerList.add(bill);
		}
		return innerList;
	}

	public List addtenInner(String type, String seqNum, List list) {
		List ls = commonBaseCodeDao.findInnerBySeqNum(type, seqNum);
		List lss = new ArrayList();
		if (ls != null && ls.size() > 0) {
			InnerMergeData obj = (InnerMergeData) ls.get(0);
			for (int i = 0; i < list.size(); i++) {
				InnerMergeData data = (InnerMergeData) list.get(i);
				data.setHsAfterTenMemoNo(obj.getHsAfterTenMemoNo());
				data.setHsAfterComplex(obj.getHsAfterComplex());
				data.setHsAfterMaterielTenName(obj.getHsAfterMaterielTenName());
				data.setHsAfterMaterielTenSpec(obj.getHsAfterMaterielTenSpec());
				data.setHsAfterMemoUnit(obj.getHsAfterMemoUnit());
				data.setHsAfterLegalUnit(obj.getHsAfterLegalUnit());
				data.setHsAfterSecondLegalUnit(obj.getHsAfterSecondLegalUnit());
				data.setHsFourNo(obj.getHsFourNo());
				data.setHsFourMaterielName(obj.getHsFourMaterielName());
				data.setHsFourCode(obj.getHsFourCode());
				data.setUpdateDate(new Date());
				commonBaseCodeDao.saveInnerMergeData(data);
				lss.add(data);
			}
			return lss;
		}
		return list;
	}

	public List addfourceInner(String type, BillTemp obj, List list) {
		List lss = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setHsFourNo(Integer.valueOf(obj.getBill1()));
			data.setHsFourMaterielName(obj.getBill3());
			data.setHsFourCode(obj.getBill2());
			data.setUpdateDate(new Date());
			commonBaseCodeDao.saveInnerMergeData(data);
			lss.add(data);
		}
		return lss;
	}

	public int checkDataEndTenInnerMerge(List list) {
		return getHandInnerMerge().checkDataEndTenInnerMerge(list);
	}

	/**
	 * 新的更新内部归并的报关资料
	 * 
	 * @param passList
	 * @param materielType
	 * @param tenAfterComplex
	 * @param tenName
	 * @param tenSpec
	 * @param tenAfterMemoUnit
	 * @param newTenSeqNum
	 * @param fourCode
	 * @param fourName
	 * @param newFourSeqNum
	 */
	public List[] updateTenAndFourCustoms(List passList, String materielType,
			Complex tenAfterComplex, String tenName, String tenSpec,
			Unit tenAfterMemoUnit, Integer newTenSeqNum, String fourCode,
			String fourName, Integer newFourSeqNum) {

		List[] returnList = new ArrayList[2];

		List removeList = new ArrayList();
		List updataList = new ArrayList();
		for (int j = 0; j < passList.size(); j++) {
			InnerMergeData data = (InnerMergeData) passList.get(j);
			Integer oldTenSeqNum = data.getHsAfterTenMemoNo();
			data.setHsAfterTenMemoNo(newTenSeqNum);// 十码序号
			data.setHsAfterComplex(tenAfterComplex);// 十位商品编码
			data.setHsAfterMaterielTenName(tenName);// 归并后商品名称
			data.setHsAfterMaterielTenSpec(tenSpec);// 归并后商品规格
			data.setHsAfterMemoUnit(tenAfterMemoUnit);// 归并后备案单位
			data.setHsAfterLegalUnit(tenAfterComplex != null ? tenAfterComplex
					.getFirstUnit() : null); // 归并后第一法定单位
			data.setHsAfterSecondLegalUnit(tenAfterComplex != null ? tenAfterComplex
					.getSecondUnit() : null);// 归并后第二法定单位

			data.setHsFourNo(newFourSeqNum); // 四码序号
			data.setHsFourCode(fourCode); // 四码编码
			data.setHsFourMaterielName(fourName);// 四码名称

			data = this.commonBaseCodeDao.saveInnerMergeData(data);
			updatReverseMerge(materielType, newTenSeqNum,
					data.getHsAfterTenMemoNo());
			if (!newTenSeqNum.toString().equals(oldTenSeqNum.toString())) {
				removeList.add(data);
			} else {
				updataList.add(data);
			}
		}
		returnList[0] = removeList;
		returnList[1] = updataList;
		return returnList;
	}

	/**
	 * 更新反向归并
	 * 
	 * @param materielType
	 * @param newSeqNum
	 * @param oldSeqNum
	 */
	public void updatReverseMerge(String materielType, Integer newSeqNum,
			Integer oldSeqNum) {
		// －－－－－－－－－－－－ 修改反向归并的十码序号
		List lt = this.commonBaseCodeDao
				.findReverseMergeTenDataByHsAfterTenMemoNo1(newSeqNum,
						materielType);
		if (lt != null && lt.size() > 0) { // 已经存在新的序号则更新到旧的
			// --------- 开始更新旧的物料码数据
			List newBeforeDataList = commonBaseCodeDao
					.findReverseMergeBeforeDataByTen(newSeqNum, materielType);
			if (newBeforeDataList != null && newBeforeDataList.size() > 0) {
				ReverseMergeBeforeData newBeforeData = (ReverseMergeBeforeData) newBeforeDataList
						.get(0);
				List oldBeforeDataList = commonBaseCodeDao
						.findReverseMergeBeforeDataByTen(oldSeqNum,
								materielType);
				for (int j = 0; j < oldBeforeDataList.size(); j++) {
					ReverseMergeBeforeData data = (ReverseMergeBeforeData) oldBeforeDataList
							.get(j);
					data.setReverseMergeTenData(newBeforeData
							.getReverseMergeTenData());
					commonBaseCodeDao.saveReverseMergeBeforeData(data);
				}
			}

			ReverseMergeTenData obj = (ReverseMergeTenData) lt.get(0);
			ReverseMergeFourData newFourdata = ((ReverseMergeTenData) lt.get(0))
					.getReverseMergeFourData();
			// 开始更新旧的数据
			List ls = this.commonBaseCodeDao
					.findReverseMergeTenDataByHsAfterTenMemoNo1(oldSeqNum,
							materielType);
			for (int j = 0; j < ls.size(); j++) {
				ReverseMergeTenData data = (ReverseMergeTenData) ls.get(j);
				data.setHsAfterTenMemoNo(newSeqNum);
				data.setHsAfterComplex(obj.getHsAfterComplex());// 十位商品编码
				data.setHsAfterMaterielTenName(obj.getHsAfterMaterielTenName());// 归并后商品名称
				data.setHsAfterMaterielTenSpec(obj.getHsAfterMaterielTenSpec());// 归并后商品规格
				data.setHsAfterMemoUnit(obj.getHsAfterMemoUnit());// 归并后备案单位
				data.setHsAfterLegalUnit(obj.getHsAfterComplex() != null ? obj
						.getHsAfterComplex().getFirstUnit() : null); // 归并后第一法定单位
				data.setHsAfterSecondLegalUnit(obj.getHsAfterComplex() != null ? obj
						.getHsAfterComplex().getSecondUnit() : null);// 归并后第二法定单位
				// ----------开始更新4码资料
				ReverseMergeFourData fourdata = data.getReverseMergeFourData();
				fourdata.setHsFourCode(newFourdata.getHsFourCode());
				// 四位商品编码
				fourdata.setHsFourMaterielName(newFourdata
						.getHsFourMaterielName());
				// 四位商品名称
				fourdata.setHsFourNo(newFourdata.getHsFourNo());// 四位序号
				this.commonBaseCodeDao.saveReverseMergeFourData(fourdata);
				data.setReverseMergeFourData(newFourdata);
				this.commonBaseCodeDao.saveReverseMergeTenData(data);

				if (commonBaseCodeDao
						.findReverseMergeBeforeDataCountByTen(data) < 1) {
					commonBaseCodeDao.deleteReverseMergeTenData(data);
				}
				if (commonBaseCodeDao
						.findReverseMergeTenDataCountByFour(fourdata) < 1) {
					commonBaseCodeDao.deleteReverseMergeFourData(fourdata);
				}
			}
		} else { // 不存在新的序号
			List ls = this.commonBaseCodeDao
					.findReverseMergeTenDataByHsAfterTenMemoNo1(oldSeqNum,
							materielType);
			for (int j = 0; j < ls.size(); j++) {
				ReverseMergeTenData data = (ReverseMergeTenData) ls.get(j);
				data.setHsAfterTenMemoNo(newSeqNum);
				this.commonBaseCodeDao.saveReverseMergeTenData(data);
			}
		}

	}

	// 更新十位商品资料（编码，名称，规格，单位）
	public void changeCustoms(Complex afterComplex, String name, String spec,
			Unit afterMemoUnit, String materielType, Integer tenMemoNo) {
		List list = this.commonBaseCodeDao
				.findInnerMergeDataByTypeAndTenMemoNo(materielType, tenMemoNo);
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setHsAfterComplex(afterComplex);// 十位商品编码
			data.setHsAfterMaterielTenName(name);// 归并后商品名称
			data.setHsAfterMaterielTenSpec(spec);// 归并后商品规格
			data.setHsAfterMemoUnit(afterMemoUnit);// 归并后备案单位
			data.setHsAfterLegalUnit(afterComplex != null ? afterComplex
					.getFirstUnit() : null); // 归并后第一法定单位
			data.setHsAfterSecondLegalUnit(afterComplex != null ? afterComplex
					.getSecondUnit() : null);// 归并后第二法定单位
			this.commonBaseCodeDao.saveInnerMergeData(data);
		}
		// ------------ 修改反向归并的10码（编码，名称）
		List tenDataList = this.commonBaseCodeDao
				.findReverseMergeTenDataByHsAfterTenMemoNo1(tenMemoNo,
						materielType);
		for (int i = 0; i < tenDataList.size(); i++) {
			ReverseMergeTenData data = (ReverseMergeTenData) tenDataList.get(i);
			data.setHsAfterComplex(afterComplex);// 十位商品编码
			data.setHsAfterMaterielTenName(name);// 归并后商品名称
			data.setHsAfterMaterielTenSpec(spec);// 归并后商品规格
			data.setHsAfterMemoUnit(afterMemoUnit);// 归并后备案单位
			data.setHsAfterLegalUnit(afterComplex != null ? afterComplex
					.getFirstUnit() : null); // 归并后第一法定单位
			data.setHsAfterSecondLegalUnit(afterComplex != null ? afterComplex
					.getSecondUnit() : null);// 归并后第二法定单位
			this.commonBaseCodeDao.saveReverseMergeTenData(data);
		}
	}

	// 更新十位商品序号
	public void changeCustomsSeqNum(String materielType, Integer oldSeqNum,
			Integer newSeqNum) {
		List list = commonBaseCodeDao.findInnerMergeDataByTypeAndTenMemoNo(
				materielType, newSeqNum);// 新的序号
		if (list != null && list.size() > 0) { // 已经存在新的序号则更新到旧的
			InnerMergeData obj = (InnerMergeData) list.get(0);
			// 开始更新旧的数据
			List ls = this.commonBaseCodeDao
					.findInnerMergeDataByTypeAndTenMemoNo(materielType,
							oldSeqNum);
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setHsAfterTenMemoNo(newSeqNum);
				data.setHsAfterComplex(obj.getHsAfterComplex());// 十位商品编码
				data.setHsAfterMaterielTenName(obj.getHsAfterMaterielTenName());// 归并后商品名称
				data.setHsAfterMaterielTenSpec(obj.getHsAfterMaterielTenSpec());// 归并后商品规格
				data.setHsAfterMemoUnit(obj.getHsAfterMemoUnit());// 归并后备案单位
				data.setHsAfterLegalUnit(obj.getHsAfterComplex() != null ? obj
						.getHsAfterComplex().getFirstUnit() : null); // 归并后第一法定单位
				data.setHsAfterSecondLegalUnit(obj.getHsAfterComplex() != null ? obj
						.getHsAfterComplex().getSecondUnit() : null);// 归并后第二法定单位
				data.setHsFourCode(obj.getHsFourCode()); // 四位商品编码
				data.setHsFourMaterielName(obj.getHsFourMaterielName());// 四位商品名称
				data.setHsFourNo(obj.getHsFourNo());// 四位序号
				this.commonBaseCodeDao.saveInnerMergeData(data);
			}
		} else { // 不存在新的序号
			List ls = this.commonBaseCodeDao
					.findInnerMergeDataByTypeAndTenMemoNo(materielType,
							oldSeqNum);
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setHsAfterTenMemoNo(newSeqNum);
				this.commonBaseCodeDao.saveInnerMergeData(data);
			}
		}

		// －－－－－－－－－－－－ 修改反向归并的十码序号
		List lt = this.commonBaseCodeDao
				.findReverseMergeTenDataByHsAfterTenMemoNo1(newSeqNum,
						materielType);
		if (lt != null && lt.size() > 0) { // 已经存在新的序号则更新到旧的
			// --------- 开始更新旧的物料码数据
			List newBeforeDataList = commonBaseCodeDao
					.findReverseMergeBeforeDataByTen(newSeqNum, materielType);
			if (newBeforeDataList != null && newBeforeDataList.size() > 0) {
				ReverseMergeBeforeData newBeforeData = (ReverseMergeBeforeData) newBeforeDataList
						.get(0);
				List oldBeforeDataList = commonBaseCodeDao
						.findReverseMergeBeforeDataByTen(oldSeqNum,
								materielType);
				for (int j = 0; j < oldBeforeDataList.size(); j++) {
					ReverseMergeBeforeData data = (ReverseMergeBeforeData) oldBeforeDataList
							.get(j);
					data.setReverseMergeTenData(newBeforeData
							.getReverseMergeTenData());
					commonBaseCodeDao.saveReverseMergeBeforeData(data);
				}
			}

			ReverseMergeTenData obj = (ReverseMergeTenData) lt.get(0);
			ReverseMergeFourData newFourdata = ((ReverseMergeTenData) lt.get(0))
					.getReverseMergeFourData();
			// 开始更新旧的数据
			List ls = this.commonBaseCodeDao
					.findReverseMergeTenDataByHsAfterTenMemoNo1(oldSeqNum,
							materielType);
			for (int j = 0; j < ls.size(); j++) {
				ReverseMergeTenData data = (ReverseMergeTenData) ls.get(j);
				data.setHsAfterTenMemoNo(newSeqNum);
				data.setHsAfterComplex(obj.getHsAfterComplex());// 十位商品编码
				data.setHsAfterMaterielTenName(obj.getHsAfterMaterielTenName());// 归并后商品名称
				data.setHsAfterMaterielTenSpec(obj.getHsAfterMaterielTenSpec());// 归并后商品规格
				data.setHsAfterMemoUnit(obj.getHsAfterMemoUnit());// 归并后备案单位
				data.setHsAfterLegalUnit(obj.getHsAfterComplex() != null ? obj
						.getHsAfterComplex().getFirstUnit() : null); // 归并后第一法定单位
				data.setHsAfterSecondLegalUnit(obj.getHsAfterComplex() != null ? obj
						.getHsAfterComplex().getSecondUnit() : null);// 归并后第二法定单位
				// ----------开始更新4码资料
				ReverseMergeFourData fourdata = data.getReverseMergeFourData();
				fourdata.setHsFourCode(newFourdata.getHsFourCode());
				// 四位商品编码
				fourdata.setHsFourMaterielName(newFourdata
						.getHsFourMaterielName());
				// 四位商品名称
				fourdata.setHsFourNo(newFourdata.getHsFourNo());// 四位序号
				this.commonBaseCodeDao.saveReverseMergeFourData(fourdata);
				data.setReverseMergeFourData(newFourdata);
				this.commonBaseCodeDao.saveReverseMergeTenData(data);

				if (commonBaseCodeDao
						.findReverseMergeBeforeDataCountByTen(data) < 1) {
					commonBaseCodeDao.deleteReverseMergeTenData(data);
				}
				if (commonBaseCodeDao
						.findReverseMergeTenDataCountByFour(fourdata) < 1) {
					commonBaseCodeDao.deleteReverseMergeFourData(fourdata);
				}
			}

		} else { // 不存在新的序号
			List ls = this.commonBaseCodeDao
					.findReverseMergeTenDataByHsAfterTenMemoNo1(oldSeqNum,
							materielType);
			for (int j = 0; j < ls.size(); j++) {
				ReverseMergeTenData data = (ReverseMergeTenData) ls.get(j);
				data.setHsAfterTenMemoNo(newSeqNum);
				this.commonBaseCodeDao.saveReverseMergeTenData(data);
			}
		}

	}

	// 更新四位商品资料（编码，名称）
	public void changeCustomsFourNo(String complex, String name,
			String materielType, Integer fourNo) {
		List list = this.commonBaseCodeDao.findInnerMergeByFourNo(materielType,
				fourNo);
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setHsFourCode(complex);
			data.setHsFourMaterielName(name);
			this.commonBaseCodeDao.saveInnerMergeData(data);
		}
		// ------------ 修改反向归并的4码（编码，名称）
		List fourDataList = this.commonBaseCodeDao
				.findReverseMergeFourDataByHsFourNo1(fourNo, materielType);
		for (int i = 0; i < fourDataList.size(); i++) {
			ReverseMergeFourData data = (ReverseMergeFourData) fourDataList
					.get(i);
			data.setHsFourCode(complex);
			data.setHsFourMaterielName(name);
			this.commonBaseCodeDao.saveReverseMergeFourData(data);
		}
	}

	// 更新四位商品序号
	public void changeCustomsSeqNumFourNo(String materielType,
			Integer oldSeqNum, Integer newSeqNum) {
		List list = commonBaseCodeDao.findInnerMergeByFourNo(materielType,
				newSeqNum.intValue());// 新的序号
		if (list != null && list.size() > 0) { // 已经存在新的序号则更新到旧的
			InnerMergeData obj = (InnerMergeData) list.get(0);
			// 开始更新旧的数据
			List ls = this.commonBaseCodeDao.findInnerMergeByFourNo(
					materielType, oldSeqNum.intValue());
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setHsFourNo(newSeqNum);
				data.setHsFourCode(obj.getHsFourCode());// 十位商品编码
				data.setHsFourMaterielName(obj.getHsFourMaterielName());// 归并后商品名称
				this.commonBaseCodeDao.saveInnerMergeData(data);
			}

		} else { // 不存在新的序号
			List ls = this.commonBaseCodeDao.findInnerMergeByFourNo(
					materielType, oldSeqNum.intValue());
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setHsFourNo(newSeqNum);
				this.commonBaseCodeDao.saveInnerMergeData(data);
			}
		}

		// ------------ 修改反向归并的4码序号
		List lt = this.commonBaseCodeDao.findReverseMergeFourDataByHsFourNo1(
				newSeqNum, materielType);
		if (lt != null && lt.size() > 0) { // 已经存在新的序号则更新到旧的
			// --------- 开始更新旧的10码数据
			List newTenDataList = commonBaseCodeDao
					.findReverseMergeTenDataByFour(newSeqNum, materielType);
			if (newTenDataList != null && newTenDataList.size() > 0) {
				ReverseMergeTenData newTenData = (ReverseMergeTenData) newTenDataList
						.get(0);
				List oldTenDataList = commonBaseCodeDao
						.findReverseMergeTenDataByFour(oldSeqNum, materielType);
				for (int j = 0; j < oldTenDataList.size(); j++) {
					ReverseMergeTenData data = (ReverseMergeTenData) oldTenDataList
							.get(j);
					data.setReverseMergeFourData(newTenData
							.getReverseMergeFourData());
					commonBaseCodeDao.saveReverseMergeTenData(data);
				}
			}
			// --------- 开始更新旧的4码数据

			ReverseMergeFourData newFourData = (ReverseMergeFourData) lt.get(0);

			List oldList = this.commonBaseCodeDao
					.findReverseMergeFourDataByHsFourNo1(oldSeqNum,
							materielType);
			for (int j = 0; j < oldList.size(); j++) {
				ReverseMergeFourData data = (ReverseMergeFourData) oldList
						.get(j);
				data.setHsFourNo(newSeqNum);
				data.setHsFourCode(newFourData.getHsFourCode());// 十位商品编码
				data.setHsFourMaterielName(newFourData.getHsFourMaterielName());// 归并后商品名称
				this.commonBaseCodeDao.saveReverseMergeFourData(data);
				// -------删除被修改的4码数据
				if (commonBaseCodeDao.findReverseMergeTenDataCountByFour(data) < 1) {
					commonBaseCodeDao.deleteReverseMergeFourData(data);
				}

			}

		} else { // 不存在新的序号
			List ls = this.commonBaseCodeDao
					.findReverseMergeFourDataByHsFourNo1(oldSeqNum,
							materielType);
			for (int j = 0; j < ls.size(); j++) {
				ReverseMergeFourData data = (ReverseMergeFourData) ls.get(j);
				data.setHsFourNo(newSeqNum);
				this.commonBaseCodeDao.saveReverseMergeFourData(data);
			}
		}

	}

	/**
	 * 电子帐册归并关系删除修改标记
	 */
	public void editMergerFlag(String type, Integer seqNum, String ptNo) {
		List list = commonBaseCodeDao.findInnerMergeDataByTenNoPtNo(type,
				seqNum, ptNo);
		System.out.println("-- list:" + list.size());
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setIsExistMerger(new Boolean(false));
			commonBaseCodeDao.saveInnerMergeData(data);
		}
	}

	public void editMergerFlag0(String type, String ptNo) {
		List list = commonBaseCodeDao
				.findInnerMergeDataByTenNoPtNo0(type, ptNo);
		System.out.println("-- list:" + list.size());
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"
					+ data.getHsBeforeMaterielNameSpec());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"
					+ data.getIsExistMerger());
			data.setIsExistMerger(new Boolean(false));
			commonBaseCodeDao.saveInnerMergeData(data);
		}
	}

	/**
	 * 隐藏键变更状态
	 * 
	 * @param list
	 * @return
	 */
	public List changeNotMerger(List list) {
		List ls = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			if (data.getIsExistMerger() != null
					&& data.getIsExistMerger().equals(new Boolean(true))) {
				data.setIsExistMerger(new Boolean(false));
			} else {
				data.setIsExistMerger(new Boolean(true));
			}
			commonBaseCodeDao.saveInnerMergeData(data);
			ls.add(data);
		}
		return ls;
	}

	/**
	 * 显示未备案的
	 * 
	 * 
	 * @param type
	 * @return
	 */
	public List findInnerMergeDataByTypeSomeInMerger(String type) {
		List arrayList = new ArrayList();

		// 不在备案中
		List list = commonBaseCodeDao.findInnerMergeDataByTypeNoMerger(type);
		Map mapBefore = getEmsEdiMergerExgImgBeforeData(type);// 获取归并前资料
		Map mapAfter = getEmsEdiMergerExgImgAfterData(type);// 获取归并后资料
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData innerMergeData = (InnerMergeData) list.get(i);
			if (mapAfter.get(innerMergeData.getHsAfterTenMemoNo()) == null) {
				continue;
			}
			if (mapBefore.get(innerMergeData.getMateriel().getPtNo()) != null) {
				continue;
			}
			TempInnerMergeData tempInnerMergeData = new TempInnerMergeData();
			tempInnerMergeData.setIsSelected(false);
			tempInnerMergeData.setInnerMergeData(innerMergeData);
			arrayList.add(tempInnerMergeData);

		}
		return arrayList;

	}

	/**
	 * 获取归并前资料
	 * 
	 * @param type
	 * @return
	 */
	private Map getEmsEdiMergerExgImgBeforeData(String type) {
		Map map = new HashMap();
		List list = commonBaseCodeDao.findEmsEdiMergerExgImgBeforeData(type);
		EmsEdiMergerExgBefore exgBefore;
		EmsEdiMergerImgBefore imgBefore;
		if (type.equals("0")) {
			for (int i = 0; i < list.size(); i++) {
				exgBefore = (EmsEdiMergerExgBefore) list.get(i);
				if (map.get(exgBefore.getPtNo()) == null)
					map.put(exgBefore.getPtNo(), exgBefore.getPtNo());
			}
		} else if (type.equals("2")) {
			for (int i = 0; i < list.size(); i++) {
				imgBefore = (EmsEdiMergerImgBefore) list.get(i);
				if (map.get(imgBefore.getPtNo()) == null)
					map.put(imgBefore.getPtNo(), imgBefore.getPtNo());
			}
		}
		return map;
	}

	/**
	 * 获取归并前资料
	 * 
	 * @param type
	 * @return
	 */
	private Map getEmsEdiMergerExgImgAfterData(String type) {
		Map map = new HashMap();
		List list = commonBaseCodeDao.findEmsEdiMergerExgImgAfterData(type);
		EmsEdiMergerExgAfter exgAfter;
		EmsEdiMergerImgAfter imgAfter;
		if (type.equals("0")) {
			for (int i = 0; i < list.size(); i++) {
				exgAfter = (EmsEdiMergerExgAfter) list.get(i);
				if (map.get(exgAfter.getSeqNum()) == null)
					map.put(exgAfter.getSeqNum(), exgAfter.getSeqNum());
			}
		} else if (type.equals("2")) {
			for (int i = 0; i < list.size(); i++) {
				imgAfter = (EmsEdiMergerImgAfter) list.get(i);
				if (map.get(imgAfter.getSeqNum()) == null)
					map.put(imgAfter.getSeqNum(), imgAfter.getSeqNum());
			}
		}
		return map;
	}

	/**
	 * 把选中的十码在内部关系管理已备案，但对应归并前没备案的数据写到内部关系管理里
	 * 
	 * @param request
	 * @param materielType
	 *            "0"为成品,"2"为料件
	 * @param list
	 *            内部归并的list
	 */
	public void writeBackEmsEdiMergerExgImgBefore(String materielType, List list) {
		if (list.isEmpty())
			return;
		List mergerList = new ArrayList();
		List mergerBeforeList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempInnerMergeData tempInnerMergeData = (TempInnerMergeData) list
					.get(i);
			if (tempInnerMergeData.getIsSelected() == false)
				continue;
			InnerMergeData innerMergeData = tempInnerMergeData
					.getInnerMergeData();
			List list1 = commonBaseCodeDao.findEmsEdiMergeAfterByInnerMerge(
					materielType, innerMergeData);
			if (list1.isEmpty())
				continue;
			if (materielType.equals("0")) {
				EmsEdiMergerExgAfter emsEdiMergerExgAfter = (EmsEdiMergerExgAfter) list1
						.get(0);
				EmsEdiMergerExgBefore emsEdiMergerExgBefore = new EmsEdiMergerExgBefore();
				emsEdiMergerExgBefore
						.setEmsEdiMergerExgAfter(emsEdiMergerExgAfter);
				emsEdiMergerExgBefore.setSeqNum(innerMergeData
						.getHsAfterTenMemoNo());
				emsEdiMergerExgBefore.setPtNo(innerMergeData.getMateriel()
						.getPtNo());
				emsEdiMergerExgBefore.setComplex(innerMergeData
						.getHsAfterComplex());
				emsEdiMergerExgBefore.setName(innerMergeData.getMateriel()
						.getFactoryName());
				emsEdiMergerExgBefore.setSpec(innerMergeData.getMateriel()
						.getFactorySpec());
				emsEdiMergerExgBefore.setUnit(emsEdiMergerExgAfter.getUnit());// 与归并后单位相同
				/*
				 * emsEdiMergerExgBefore.setLegalUnit(innerMergeData
				 * .getHsAfterLegalUnit());
				 * emsEdiMergerExgBefore.setLegalUnit2(innerMergeData
				 * .getHsAfterSecondLegalUnit());
				 */
				emsEdiMergerExgBefore.setInnerMergerData(innerMergeData);

				emsEdiMergerExgBefore.setCompany(emsEdiMergerExgAfter
						.getCompany());
				emsEdiMergerExgBefore.setModifyMark(ModifyMarkState.ADDED);
				emsEdiMergerExgBefore.setModifyTimes(emsEdiMergerExgAfter
						.getEmsEdiMergerHead().getModifyTimes());
				emsEdiMergerExgBefore.setSendState(1);
				emsEdiMergerExgBefore.setChangeDate(new Date());
				innerMergeData.setIsExistMerger(true);
				mergerList.add(innerMergeData);
				mergerBeforeList.add(emsEdiMergerExgBefore);
				// commonBaseCodeDao.saveOrUpdate(innerMergeData);
				// commonBaseCodeDao.saveOrUpdate(emsEdiMergerExgBefore);
			} else if (materielType.equals("2")) {
				EmsEdiMergerImgAfter emsEdiMergerImgAfter = (EmsEdiMergerImgAfter) list1
						.get(0);
				EmsEdiMergerImgBefore emsEdiMergerImgBefore = new EmsEdiMergerImgBefore();
				emsEdiMergerImgBefore
						.setEmsEdiMergerImgAfter(emsEdiMergerImgAfter);
				emsEdiMergerImgBefore.setSeqNum(innerMergeData
						.getHsAfterTenMemoNo());
				emsEdiMergerImgBefore.setPtNo(innerMergeData.getMateriel()
						.getPtNo());
				emsEdiMergerImgBefore.setComplex(innerMergeData
						.getHsAfterComplex());
				emsEdiMergerImgBefore.setName(innerMergeData.getMateriel()
						.getFactoryName());
				emsEdiMergerImgBefore.setSpec(innerMergeData.getMateriel()
						.getFactorySpec());
				emsEdiMergerImgBefore.setUnit(emsEdiMergerImgAfter.getUnit());// 与归并后单位相同
				/*
				 * emsEdiMergerImgBefore.setLegalUnit(innerMergeData
				 * .getHsAfterLegalUnit());
				 * emsEdiMergerImgBefore.setLegalUnit2(innerMergeData
				 * .getHsAfterSecondLegalUnit());
				 */
				emsEdiMergerImgBefore.setInnerMergerData(innerMergeData);

				emsEdiMergerImgBefore.setCompany(emsEdiMergerImgAfter
						.getCompany());
				emsEdiMergerImgBefore.setModifyMark(ModifyMarkState.ADDED);
				emsEdiMergerImgBefore.setModifyTimes(emsEdiMergerImgAfter
						.getEmsEdiMergerHead().getModifyTimes());
				emsEdiMergerImgBefore.setSendState(1);
				emsEdiMergerImgBefore.setChangeDate(new Date());
				innerMergeData.setIsExistMerger(true);
				mergerList.add(innerMergeData);
				mergerBeforeList.add(emsEdiMergerImgBefore);
				// commonBaseCodeDao.saveOrUpdate(innerMergeData);
				// commonBaseCodeDao.saveOrUpdate(emsEdiMergerImgBefore);
			}
		}
		commonBaseCodeDao.batchSaveOrUpdate(mergerList);
		commonBaseCodeDao.batchSaveOrUpdate(mergerBeforeList);
	}

}