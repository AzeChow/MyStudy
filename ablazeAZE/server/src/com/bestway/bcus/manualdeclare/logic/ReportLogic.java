/*
 * Created on 2004-9-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsBillListExpProductStat;
import com.bestway.bcus.manualdeclare.entity.EmsBillListImpMaterialStat;
import com.bestway.bcus.manualdeclare.entity.EmsEdiImgExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.bcus.manualdeclare.entity.EmsFasAllImgPrint;
import com.bestway.bcus.manualdeclare.entity.EmsFasAllPrint;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ReportLogic {
	EmsEdiTrDao emsEdiTrDao = null;

	/**
	 * @return Returns the emsEdiTrDao.
	 */
	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	/**
	 * @param emsEdiTrDao
	 *            The emsEdiTrDao to set.
	 */
	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	/**
	 * 打印申请批准证表
	 * 
	 * @param emsEdiTrHead
	 * @return
	 */
	public List findEmsEdiImgExgs(EmsEdiTrHead emsEdiTrHead) {
		List imgs = emsEdiTrDao.findEmsEdiTrImg(emsEdiTrHead);
		List exgs = emsEdiTrDao.findEmsEdiTrExg(emsEdiTrHead);
		Hashtable htImgExgs = new Hashtable();// 以seqnum为key,以新的EmsEdiImgExg对象为值
		List emsEdiImgExgs = new Vector();
		for (int i = 0; i < imgs.size(); i++) {
			String seqNum = ((EmsEdiTrImg) imgs.get(i)).getPtNo().toString();
			if (htImgExgs.get(seqNum) == null) {
				EmsEdiTrImg img = (EmsEdiTrImg) imgs.get(i);
				EmsEdiImgExg temp = new EmsEdiImgExg();
				htImgExgs.put(seqNum, temp);
				temp.setImComplex(img.getComplex());
				temp.setImName(img.getName());
				temp.setImSeqNum(img.getPtNo());
			} else {
				throw new RuntimeException("数据错误，经营范围中的料件序号不可以重复。");
			}
		}
		for (int i = 0; i < exgs.size(); i++) {
			EmsEdiTrExg exg = (EmsEdiTrExg) exgs.get(i);
			String seqNum = exg.getPtNo().toString();
			if (htImgExgs.get(seqNum) != null) {
				EmsEdiImgExg imgExg = (EmsEdiImgExg) htImgExgs.get(seqNum);
				imgExg.setExName(exg.getName());
				imgExg.setExSeqNum(exg.getPtNo());
				imgExg.setExComplex(exg.getComplex());
			} else {
				EmsEdiImgExg temp = new EmsEdiImgExg();
				temp.setExComplex(exg.getComplex());
				temp.setExName(exg.getName());
				temp.setExSeqNum(exg.getPtNo());
				temp.setImSeqNum(exg.getPtNo());
				htImgExgs.put(exg.getPtNo(), temp);
			}
		}
		List result = new Vector(htImgExgs.values());
		Collections.sort(result);

		return result;
	}
	
	/**
	 * 打印申请批准证表
	 * 
	 * @param emsEdiTrHead
	 * @return
	 */
	public List findEditedEmsEdiImgExgs(EmsEdiTrHead emsEdiTrHead) {
		List imgs = emsEdiTrDao.findEditedEmsEdiTrImg(emsEdiTrHead);
		List exgs = emsEdiTrDao.findEditedEmsEdiTrExg(emsEdiTrHead);
		Hashtable htImgExgs = new Hashtable();// 以seqnum为key,以新的EmsEdiImgExg对象为值
		List emsEdiImgExgs = new Vector();
		for (int i = 0; i < imgs.size(); i++) {
			String seqNum = ((EmsEdiTrImg) imgs.get(i)).getPtNo().toString();
			if (htImgExgs.get(seqNum) == null) {
				EmsEdiTrImg img = (EmsEdiTrImg) imgs.get(i);
				EmsEdiImgExg temp = new EmsEdiImgExg();
				htImgExgs.put(seqNum, temp);
				temp.setImComplex(img.getComplex());
				temp.setImName(img.getName());
				temp.setImSeqNum(img.getPtNo());
			} else {
				throw new RuntimeException("数据错误，经营范围中的料件序号不可以重复。");
			}
		}
		for (int i = 0; i < exgs.size(); i++) {
			EmsEdiTrExg exg = (EmsEdiTrExg) exgs.get(i);
			String seqNum = exg.getPtNo().toString();
			if (htImgExgs.get(seqNum) != null) {
				EmsEdiImgExg imgExg = (EmsEdiImgExg) htImgExgs.get(seqNum);
				imgExg.setExName(exg.getName());
				imgExg.setExSeqNum(exg.getPtNo());
				imgExg.setExComplex(exg.getComplex());
			} else {
				EmsEdiImgExg temp = new EmsEdiImgExg();
				temp.setExComplex(exg.getComplex());
				temp.setExName(exg.getName());
				temp.setExSeqNum(exg.getPtNo());
				temp.setImSeqNum(exg.getPtNo());
				htImgExgs.put(exg.getPtNo(), temp);
			}
		}
		List result = new Vector(htImgExgs.values());
		Collections.sort(result);

		return result;
	}

	public List findEmsHeadFasAll(EmsHeadH2kFas emsH2kFas) {
		List fasAll = emsEdiTrDao.findEmsHeadH2kFas(emsH2kFas);
		Hashtable fasAlls = new Hashtable();
		for (int i = 0; i < fasAll.size(); i++) {

			String seqNum = ((EmsHeadH2kFas) fasAll.get(i)).getEmsNo() == null ? ""
					: ((EmsHeadH2kFas) fasAll.get(i)).getEmsNo();
			if (fasAlls.get(seqNum) == null) {
				EmsHeadH2kFas img = (EmsHeadH2kFas) fasAll.get(i);
				EmsFasAllPrint temp = new EmsFasAllPrint();
				fasAlls.put(seqNum, temp);
				temp.setEmsHeadH2kNo(img.getEmsHeadH2kNo());
				if (img.getEmsType() == null) {
					temp.setEmsType("");
				} else
					temp.setEmsType(img.getEmsType().equals("2") ? "异地报关分册"
							: "异地结转分册");
				temp.setCopEmsNo(img.getCopEmsNo() == null ? "" : img
						.getCopEmsNo());
				temp.setEmsNo(img.getEmsNo());
				temp.setEmsType(img.getEmsType());
				temp.setTradeCode(img.getTradeCode() == null ? "" : img
						.getTradeCode());
				temp.setTradeName(img.getTradeName() == null ? "" : img
						.getTradeName());
				temp.setDeclareCode(img.getDeclareCode() == null ? "" : img
						.getDeclareCode());
				temp.setDeclareName(img.getDeclareName() == null ? "" : img
						.getDeclareName());
				temp.setNewApprDate(img.getNewApprDate());
				temp.setChangeApprDate(img.getChangeApprDate());
				temp.setInputDate(img.getInputDate());
				temp.setDeclareDate(img.getDeclareDate());
				temp.setDeclareMark(img.getDeclareMark() == null ? "" : img
						.getDeclareMark().getName());
				temp.setIePort(img.getIePort() == null ? "" : img.getIePort()
						.getName());
				temp.setImSeqNum(i);
			}
		}
		List result = new Vector(fasAlls.values());
		Collections.sort(result);
		return result;
	}

	public List findEmsHeadFasAllImgd(EmsHeadH2kFas emsH2kFas) {
		List fasImgd = emsEdiTrDao.findEmsHeadH2kFasImgd(emsH2kFas);
		Hashtable fasAlls = new Hashtable();
		for (int j = 0; j < fasImgd.size(); j++) {
			EmsHeadH2kFasImg imgd = (EmsHeadH2kFasImg) fasImgd.get(j);
			String seqImgd = imgd.getSeqNum().toString();
			if (fasAlls.get(seqImgd) != null) {
				EmsFasAllImgPrint temp = (EmsFasAllImgPrint) fasAlls
						.get(seqImgd);
				temp.setSeqNum(imgd.getSeqNum());
				temp.setComplex(imgd.getComplex() == null ? "" : imgd
						.getComplex().getCode());
				temp.setName(imgd.getName() == null ? "" : imgd.getName());
				temp.setSpec(imgd.getSpec() == null ? "" : imgd.getSpec());
				temp.setUnit(imgd.getUnit() == null ? "" : imgd.getUnit()
						.getName());
				temp.setDeclarePrice(imgd.getDeclarePrice() == null ? 0 : imgd
						.getDeclarePrice());
				temp.setAllowAmount(imgd.getAllowAmount() == null ? 0 : imgd
						.getAllowAmount());
			} else {
				EmsFasAllImgPrint temp = new EmsFasAllImgPrint();
				temp.setSeqNum(imgd.getSeqNum());
				temp.setComplex(imgd.getComplex() == null ? "" : imgd
						.getComplex().getCode());
				temp.setName(imgd.getName() == null ? "" : imgd.getName());
				temp.setSpec(imgd.getSpec() == null ? "" : imgd.getSpec());
				temp.setUnit(imgd.getUnit() == null ? "" : imgd.getUnit()
						.getName());
				temp.setDeclarePrice(imgd.getDeclarePrice() == null ? 0 : imgd
						.getDeclarePrice());
				temp.setAllowAmount(imgd.getAllowAmount() == null ? 0 : imgd
						.getAllowAmount());
				temp.setImSeqNum(imgd.getSeqNum());
				fasAlls.put(imgd.getSeqNum(), temp);
			}
		}
		List result = new Vector(fasAlls.values());
		Collections.sort(result);
		return result;
	}

	public List findEmsHeadFasAllExgd(EmsHeadH2kFas emsH2kFas) {
		List fasImgd = emsEdiTrDao.findEmsHeadH2kFasExgd(emsH2kFas);
		Hashtable fasAlls = new Hashtable();
		for (int j = 0; j < fasImgd.size(); j++) {
			EmsHeadH2kFasExg imgd = (EmsHeadH2kFasExg) fasImgd.get(j);
			String seqImgd = imgd.getSeqNum().toString();
			if (fasAlls.get(seqImgd) != null) {
				EmsFasAllImgPrint temp = (EmsFasAllImgPrint) fasAlls
						.get(seqImgd);
				temp.setSeqNum(imgd.getSeqNum());
				temp.setComplex(imgd.getComplex() == null ? "" : imgd
						.getComplex().getCode());
				temp.setName(imgd.getName() == null ? "" : imgd.getName());
				temp.setSpec(imgd.getSpec() == null ? "" : imgd.getSpec());
				temp.setUnit(imgd.getUnit() == null ? "" : imgd.getUnit()
						.getName());
				temp.setDeclarePrice(imgd.getDeclarePrice() == null ? 0 : imgd
						.getDeclarePrice());
				temp.setAllowAmount(imgd.getAllowAmount() == null ? 0 : imgd
						.getAllowAmount());
			} else {
				EmsFasAllImgPrint temp = new EmsFasAllImgPrint();
				temp.setSeqNum(imgd.getSeqNum());
				temp.setComplex(imgd.getComplex() == null ? "" : imgd
						.getComplex().getCode());
				temp.setName(imgd.getName() == null ? "" : imgd.getName());
				temp.setSpec(imgd.getSpec() == null ? "" : imgd.getSpec());
				temp.setUnit(imgd.getUnit() == null ? "" : imgd.getUnit()
						.getName());
				temp.setDeclarePrice(imgd.getDeclarePrice() == null ? 0 : imgd
						.getDeclarePrice());
				temp.setAllowAmount(imgd.getAllowAmount() == null ? 0 : imgd
						.getAllowAmount());
				temp.setImSeqNum(imgd.getSeqNum());
				fasAlls.put(imgd.getSeqNum(), temp);
			}
		}
		List result = new Vector(fasAlls.values());
		Collections.sort(result);
		return result;
	}

	private Double fd(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 报关清单进口料件情况统计表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscBillListImpMaterialStat型，存放统计报表中的进口料件清单情况统计表资料
	 */
	public List<EmsBillListImpMaterialStat> findBillListImpMaterialStat(
			Date beginDate, Date endDate, String billListState, List billlist) {
		List<EmsBillListImpMaterialStat> lsResult = new ArrayList<EmsBillListImpMaterialStat>();

		// List<Materiel> list = this.emsEdiTrDao.findAllMaterial(true,
		// beginDate,
		// endDate, billListState);
		// List<AtcMergeBeforeComInfo>list=billlist;
		// for (AtcMergeBeforeComInfo atcMergeBeforeComInfo : billlist) {
		for (int k = 0; k < billlist.size(); k++) {
			EmsBillListImpMaterialStat impMaterialStat = (EmsBillListImpMaterialStat)billlist.get(k);
			String ptNo = impMaterialStat.getCommCode();
			Integer emsSerialNo = this.emsEdiTrDao.findEmsComInfo(ptNo,
					billListState, beginDate, endDate);
			String scmcocName= this.emsEdiTrDao.findScmCoc(ptNo,
					billListState, beginDate, endDate);
			impMaterialStat.setEmsSerialNo(emsSerialNo);
			impMaterialStat.setCommCode(ptNo);
			impMaterialStat.setScmcocName(scmcocName);
			impMaterialStat.setCommName(impMaterialStat.getCommName());
			impMaterialStat.setCommSpec(impMaterialStat.getCommSpec());
			impMaterialStat.setUnit(this.emsEdiTrDao.getUnit("2",ptNo));
//			impMaterialStat.setUnitPrice(atcMergeBeforeComInfo.getMateriel()
//					.getPtPrice());
			/**
			 * 料件进口数量
			 */
			impMaterialStat
					.setDirectImport(this.emsEdiTrDao.findMaterialTotalAmount(
							ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT, null,
							beginDate, endDate, billListState,ptNo));
			/**
			 * 转厂进口量
			 */
			impMaterialStat.setTransferFactoryImport(this.emsEdiTrDao
					.findMaterialTotalAmount(ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null,
							beginDate, endDate, billListState,
							ptNo));
			/**
			 * 料件退换进口数
			 */
			impMaterialStat
					.setExchangeImport(this.emsEdiTrDao
							.findMaterialTotalAmount(ImpExpFlag.IMPORT,
									ImpExpType.DIRECT_IMPORT, new String[] {
											"0300", "0700" }, beginDate,
									endDate, billListState,
									ptNo));
			/**
			 * 料件退换出口数
			 */
			impMaterialStat
					.setExchangeExport(this.emsEdiTrDao
							.findMaterialTotalAmount(ImpExpFlag.EXPORT,
									ImpExpType.BACK_MATERIEL_EXPORT,
									new String[] { "0300", "0700" }, beginDate,
									endDate, billListState,
									ptNo));
			/**
			 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
			 */
			impMaterialStat
					.setBackMaterialReturn(this.emsEdiTrDao
							.findMaterialTotalAmount(ImpExpFlag.EXPORT,
									ImpExpType.BACK_MATERIEL_EXPORT,
									new String[] { "0265", "0664" }, beginDate,
									endDate, billListState,
									ptNo));
			/**
			 * 料件内销，海关批准内销
			 */
			impMaterialStat
					.setInternalAmount(this.emsEdiTrDao
							.findMaterialTotalAmount(ImpExpFlag.EXPORT,
									ImpExpType.MATERIAL_DOMESTIC_SALES,
									new String[] { "0245", "0644" }, beginDate,
									endDate, billListState,
									ptNo));
			/**
			 * 退料出口量
			 */
			impMaterialStat.setBackMaterialExport(this.emsEdiTrDao
					.findMaterialTotalAmount(ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, null, beginDate,
							endDate, billListState, ptNo));
			// 成品使用量
			Double productUse = 0.0;
			List listExpBom = this.emsEdiTrDao.findEmsEdiMergerExgBom(ptNo,
					billListState, beginDate, endDate);
			for (int i = 0; i < listExpBom.size(); i++) {
				Object[] obj = (Object[]) listExpBom.get(i);
				Double unitWear = fd((Double) obj[2]);
				Double wear = fd((Double) obj[3]);
				Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量

				Double useNum = fd((Double) obj[4]) - fd((Double) obj[5]);

				Double ljUseNum = useNum * unitUse;
				productUse = productUse + ljUseNum;
			}
			/**
			 * 进口总量=料件进口量+转厂进口量+料件退换进口量-料件退换出口量
			 */
			impMaterialStat
					.setImpTotalAmont((impMaterialStat.getDirectImport() == null ? 0.0
							: impMaterialStat.getDirectImport())
							+ (impMaterialStat.getTransferFactoryImport() == null ? 0.0
									: impMaterialStat
											.getTransferFactoryImport())
							+ (impMaterialStat.getExchangeImport() == null ? 0.0
									: impMaterialStat.getExchangeImport())
							- (impMaterialStat.getExchangeExport() == null ? 0.0
									: impMaterialStat.getExchangeExport()));

			/**
			 * 大单进口量 ＝ 直接进口 ＋ 转厂进口
			 */
			impMaterialStat
					.setBigImpAmount((impMaterialStat.getDirectImport() == null ? 0.0
							: impMaterialStat.getDirectImport())
							+ (impMaterialStat.getTransferFactoryImport() == null ? 0.0
									: impMaterialStat
											.getTransferFactoryImport()));

			/**
			 * 成品使用量 = 总数量＊（单耗／１－损耗率／１００）
			 */
			// impMaterialStat.setProductUse(hmProductUse.get(materiel.getPtNo()));
			impMaterialStat.setProductUse(productUse);
			/**
			 * 余料情况 ＝ 总进口量 － 成品使用量 － 料件内销 -料件复出
			 */

			impMaterialStat
					.setRemainAmount((impMaterialStat.getImpTotalAmont() == null ? 0.0
							: impMaterialStat.getImpTotalAmont())
							- (impMaterialStat.getProductUse() == null ? 0.0
									: impMaterialStat.getProductUse())
							- (impMaterialStat.getInternalAmount() == null ? 0.0
									: impMaterialStat.getInternalAmount())
							- (impMaterialStat.getBackMaterialReturn() == null ? 0.0
									: impMaterialStat.getBackMaterialReturn()));

			lsResult.add(impMaterialStat);

		}
		return lsResult;
	}

	/**
	 * 报关清单出口成品情况统计表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是EmsBillListExpProductStat型，存放统计报表中的出口成品清单情况统计表
	 */
	public List<EmsBillListExpProductStat> findBillListExpProductStat(
			Date beginDate, Date endDate, String billListState) {
		List<EmsBillListExpProductStat> lsResult = new ArrayList<EmsBillListExpProductStat>();
		List<Materiel> list = this.emsEdiTrDao.findAllMaterial(false,
				beginDate, endDate, billListState);
		for (Materiel materiel : list) {
			String ptNo = materiel.getPtNo();
			EmsBillListExpProductStat expProductStat = new EmsBillListExpProductStat();
			Integer emsSerialNo = this.emsEdiTrDao.findEmsComInfo(ptNo,
					billListState, beginDate, endDate);
			String ScmcocName=this.emsEdiTrDao.findScmCoc(ptNo,
					billListState, beginDate, endDate);
			expProductStat.setEmsSerialNo(emsSerialNo);
			expProductStat.setCommCode(materiel.getPtNo());
			expProductStat.setCommName(materiel.getFactoryName());
			expProductStat.setCommSpec(materiel.getFactorySpec());
			expProductStat.setScmcocName(ScmcocName);
			expProductStat.setUnit(this.emsEdiTrDao.getUnit("0", materiel
					.getPtNo()));
			expProductStat.setUnitPrice(materiel.getPtPrice());
			/**
			 * 成品出口数量
			 */
			expProductStat.setDirectExport(this.emsEdiTrDao
					.findMaterialTotalAmount(ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null, beginDate, endDate,
							billListState, ptNo));
			/**
			 * 转厂出口数量
			 */
			expProductStat.setTransferFactoryExport(this.emsEdiTrDao
					.findMaterialTotalAmount(ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							beginDate, endDate, billListState, ptNo));
			/**
			 * 退厂返工数量
			 */
			expProductStat.setBackFactoryRework(this.emsEdiTrDao
					.findMaterialTotalAmount(ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, beginDate,
							endDate, billListState, ptNo));
			/**
			 * 返工复出数量
			 */
			expProductStat.setReworkExport(this.emsEdiTrDao
					.findMaterialTotalAmount(ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null, beginDate, endDate,
							billListState, ptNo));
			// =======================下面是保存在数据表中====================
			/**
			 * 总出口量=报关单出口量+转厂出口-退厂返工量+返工复出数
			 */
			expProductStat
					.setExpTotalAmont((expProductStat.getDirectExport() == null ? 0.0
							: expProductStat.getDirectExport())
							+ (expProductStat.getTransferFactoryExport() == null ? 0.0
									: expProductStat.getTransferFactoryExport())
							- (expProductStat.getBackFactoryRework() == null ? 0.0
									: expProductStat.getBackFactoryRework())
							+ (expProductStat.getReworkExport() == null ? 0.0
									: expProductStat.getReworkExport()));
			/**
			 * 大单出口量
			 */
			expProductStat
					.setBigExpAmount((expProductStat.getDirectExport() == null ? 0.0
							: expProductStat.getDirectExport())
							+ (expProductStat.getTransferFactoryExport() == null ? 0.0
									: expProductStat.getTransferFactoryExport()));
			lsResult.add(expProductStat);
		}
		return lsResult;
	}

	/**
	 * 把list转换为HashMap，list(o)为key,list(1)value
	 * 
	 * @param list
	 *            要转换的list
	 * @return HashMap
	 */
	private HashMap<String, Double> customsListToHashMap(List list) {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String key = (objs[0] == null ? "" : objs[0].toString());
			Double value = objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
					.toString());
			System.out.println("::::::::" + key + "|||||||" + value);
			hm.put(key, value);
		}
		return hm;
	}

}
