/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.TempMateriel;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class TransferTempMateriel {
	private CommonBaseCodeDao commonBaseCodeDao = null;

	private EmsEdiTrDao emsEdiTrDao = null;

	private MaterialManageDao materialManageDao;

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

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
	 * 查找物料主档来自类别-->进出口报关申请单
	 */
	public List findMaterielByImpExpRequestBillType(String materielType,
			String impExpRequestBillId) {
		List newList = new ArrayList();

		EmsEdiMergerHead emsEdiMergerHead = this.emsEdiTrDao
				.findEmsEdiMergerHeadByDeclareState(DeclareState.PROCESS_EXE); // 正在执行的归并关系表头
		List emsList = this.emsEdiTrDao.findEmsHeadH2kInExecuting(); // 正在执行的电子帐册表头
		if (emsList == null || emsList.size() <= 0) { // emsEdiMergerHead ==
			// null || //电子帐册为空
			//
			// 将返回所有的末备案的物料主档商品信息
			//
			List list = this.materialManageDao.findMaterielByType(materielType);
			for (int i = 0; i < list.size(); i++) {
				TempMateriel tempMateriel = new TempMateriel();
				tempMateriel.setIsMemo(new Boolean(false));
				tempMateriel.setMateriel((Materiel) list.get(i));
				newList.add(tempMateriel);
			}
			return newList;
		}

		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsList.get(0);
		List list = this.commonBaseCodeDao.findMaterielByPutOnRecords(
				// 在归并关系中备案
				materielType, " and m.id not in (select  c.materiel.id "
						+ "from ImpExpCommodityInfo c "
						+ "where c.impExpRequestBill.id = ? ) ",
				new Object[] { impExpRequestBillId }, emsEdiMergerHead,
				emsHeadH2k);
		System.out.println("-- List" + list.size());
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(true));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		list = this.commonBaseCodeDao.findMaterielByNotPutOnRecords(
				materielType, " and  m.id not in (select  c.materiel.id "
						+ "from ImpExpCommodityInfo c "
						+ "where c.impExpRequestBill.id = ? ) ",
				new Object[] { impExpRequestBillId }, emsEdiMergerHead,
				emsHeadH2k);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(false));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		return newList;
	}

	/**
	 * 查找物料主档来自类别-->进出口报关申请单
	 */
	public List findMaterielByImpExpRequestBillAndType(String materielType,
			String impExpRequestBillId, boolean isFilter) {
		List newList = new ArrayList();
		// EmsEdiMergerHead emsEdiMergerHead = this.emsEdiTrDao
		// .findEmsEdiMergerHeadByDeclareState(DeclareState.PROCESS_EXE); //
		// 正在执行的归并关系表头
		List emsList = this.emsEdiTrDao.findEmsHeadH2kInExecuting(); // 正在执行的电子帐册表头
		if (emsList == null || emsList.size() <= 0) { // emsEdiMergerHead ==
			// null || //电子帐册为空
			//
			// 将返回所有的末备案的物料主档商品信息
			//
			List list = this.materialManageDao.findMaterielByType(materielType);
			for (int i = 0; i < list.size(); i++) {
				TempMateriel tempMateriel = new TempMateriel();
				tempMateriel.setIsMemo(false);
				tempMateriel.setIsEmsRecords(false);
				tempMateriel.setMateriel((Materiel) list.get(i));
				newList.add(tempMateriel);
			}
			return newList;
		}
		// -----------------------------------------------------------------
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsList.get(0);
		List listInnerMergeData = this.commonBaseCodeDao
				.findInnerMergeData(materielType);
		Map mapSN = new HashMap();
		Map mapSNname = new HashMap();
		Map mapSNspec = new HashMap();
		Map mapSNUnit = new HashMap();
		for (int k = 0; k < listInnerMergeData.size(); k++) {
			Object[] objs = (Object[]) listInnerMergeData.get(k);
			mapSN.put(objs[0], objs[1]);
			mapSNname.put(objs[0], objs[2]);
			mapSNspec.put(objs[0], objs[3]);
			mapSNUnit.put(objs[0], objs[4]);
		}
		List listEmsHeadH2kImgOrExg = this.commonBaseCodeDao
				.findEmsHeadH2kImgOrExg(emsHeadH2k, materielType);
		List listinfo = new ArrayList();
		if (isFilter) {
			List listinfot = this.commonBaseCodeDao
					.findImpExpCommodityInfoMId(impExpRequestBillId);
			for (int i = 0; i < listinfot.size(); i++) {
				listinfo.add(listinfot.get(i) == null ? "" : listinfot.get(i)
						.toString());
			}
		}
		List listMateriel = this.commonBaseCodeDao.findMateriel(materielType);
		for (int m = 0; m < listMateriel.size(); m++) {
			Materiel mt = (Materiel) listMateriel.get(m);

			if (mapSN.get(mt.getPtNo()) == null) {
				continue;
			}

			if (listinfo.contains(mt.getId())) {
				continue;
			}
			boolean isInRecords = false;
			boolean isEmsRecords = false;
			if (mapSN.get(mt.getPtNo()) != null) {

				isInRecords = true;
				if (listEmsHeadH2kImgOrExg.contains(mapSN.get(mt.getPtNo()))) {
					isEmsRecords = true;
				}
			}
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setMateriel(mt);
			tempMateriel.setIsMemo(isInRecords);
			tempMateriel.setIsEmsRecords(isEmsRecords);
			tempMateriel.setSeqNum((Integer) mapSN.get(mt.getPtNo()));

			tempMateriel.setAfterTenName((String) mapSNname.get(mt.getPtNo()));
			tempMateriel.setAfterSpec((String) mapSNspec.get(mt.getPtNo()));
			tempMateriel.setAfterUnit((String) mapSNUnit.get(mt.getPtNo()));
			newList.add(tempMateriel);
		}

		return newList;
	}

	/**
	 * 查找物料主档来自类别-->关封申请单
	 */
	public List findMaterielByCustomsEnvelopRequestBillType(
			String materielType, String customsEnvelopRequestId) {
		List newList = new ArrayList();
		List list = this.findMaterielByNotPutOnRecords(materielType,
				" and  m.id not in (select  c.materiel.id "
						+ "from FptAppItem c "
						+ "where c.customsEnvelopRequestBill.id = ? ) ",
				new Object[] { customsEnvelopRequestId });
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(false));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		list = this.findMaterielByPutOnRecords(materielType,
				" and  m.id not in (select  c.materiel.id "
						+ "from FptAppItem c "
						+ "where c.customsEnvelopRequestBill.id = ? ) ",
				new Object[] { customsEnvelopRequestId });
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(true));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		return newList;
	}

	/**
	 * 查找物料主档来自类别-->结转单据(转厂)
	 */
	public List findMaterielByTransferFactoryBillType(String materielType) {
		List newList = new ArrayList();
		List list = this
				.findMaterielByNotPutOnRecords(materielType, null, null);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(false));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		list = this.findMaterielByPutOnRecords(materielType, null, null);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(true));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		return newList;
	}

	/**
	 * 查找物料主档来自类别-->结转结转初始化单据单据(转厂)
	 */
	public List findMaterielByTransferFactoryInitBillType(String materielType,
			String initBillId) {
		List newList = new ArrayList();
		System.out.println(initBillId);
		List list = this
				.findMaterielByNotPutOnRecords(
						materielType,
						" and m.ptNo not in "
								+ " ( select n.materiel.ptNo from TransferFactoryInitBillCommodityInfo n "
								+ " where n.transferFactoryInitBill.id=? )",
						new Object[] { initBillId });
		// List list = this
		// .findMaterielByNotPutOnRecords(materielType, null, null);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(false));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		list = this
				.findMaterielByPutOnRecords(
						materielType,
						" and m.ptNo not in "
								+ " (select n.materiel.ptNo from TransferFactoryInitBillCommodityInfo n "
								+ " where n.transferFactoryInitBill.id=? )",
						new Object[] { initBillId });
		// list = this.findMaterielByPutOnRecords(materielType, null,null);
		for (int i = 0; i < list.size(); i++) {
			TempMateriel tempMateriel = new TempMateriel();
			tempMateriel.setIsMemo(new Boolean(true));
			tempMateriel.setMateriel((Materiel) list.get(i));
			newList.add(tempMateriel);
		}
		return newList;
	}

	/**
	 * 查找物料主档来自类别-->没有备案
	 */
	private List findMaterielByNotPutOnRecords(String materielType,
			String conditionSql, Object[] conditionParameter) {
		EmsEdiMergerHead emsEdiMergerHead = this.emsEdiTrDao
				.findEmsEdiMergerHeadByDeclareState(DeclareState.PROCESS_EXE);
		List emsList = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
		if (emsEdiMergerHead == null || emsList == null || emsList.size() <= 0) {
			return new ArrayList();
		}
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsList.get(0);
		List list = this.commonBaseCodeDao.findMaterielByNotPutOnRecords(
				materielType, conditionSql, conditionParameter,
				emsEdiMergerHead, emsHeadH2k);
		return list;
	}

	/**
	 * 查找物料主档来自类别-->有备案
	 */
	private List findMaterielByPutOnRecords(String materielType,
			String conditionSql, Object[] conditionParameter) {
		EmsEdiMergerHead emsEdiMergerHead = this.emsEdiTrDao
				.findEmsEdiMergerHeadByDeclareState(DeclareState.PROCESS_EXE);
		List emsList = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
		if (emsEdiMergerHead == null || emsList == null || emsList.size() <= 0) {
			return new ArrayList();
		}
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsList.get(0);
		List list = this.commonBaseCodeDao.findMaterielByPutOnRecords(
				materielType, conditionSql, conditionParameter,
				emsEdiMergerHead, emsHeadH2k);
		return list;
	}
}