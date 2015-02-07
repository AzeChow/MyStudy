/*
 * Created on 2005-10-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.logic;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.cas.entity.TempEmsImgExg;
import com.bestway.bcus.cas.entity.TempStatCusNameRelationHsnMt;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;

/**
 * @author ls
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class MakeCorrespondenceLogic {
	/**
	 * 纸质手册内部归并Dao
	 */
	public BcsInnerMergeDao bcsInnerMergeDao = null;

	/**
	 * 常用报关代码设置Dao
	 */
	public CommonBaseCodeDao bcusInnerMergeDao = null;

	public EmsEdiTrDao emsEdiTrDao = null;

	/**
	 * 海关帐Dao
	 */
	public CasDao casDao = null;

	/**
	 * 取得海关帐Dao的内容
	 * 
	 * @return 海关帐Dao
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设计海关帐Dao的内容
	 * 
	 * @param casDao
	 *            海关帐Dao
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * 取得纸质手册内部归并Dao的内容
	 * 
	 * @return 纸质手册内部归并Dao
	 */
	public BcsInnerMergeDao getBcsInnerMergeDao() {
		return bcsInnerMergeDao;
	}

	/**
	 * 设计纸质手册内部归并Dao的内容
	 * 
	 * @param bcsInnerMergeDao
	 *            纸质手册内部归并Dao
	 */
	public void setBcsInnerMergeDao(BcsInnerMergeDao bcsInnerMergeDao) {
		this.bcsInnerMergeDao = bcsInnerMergeDao;
	}

	/**
	 * 取得常用报关代码设置Dao的内容
	 * 
	 * @return 常用报关代码设置Dao
	 */
	public CommonBaseCodeDao getBcusInnerMergeDao() {
		return bcusInnerMergeDao;
	}

	/**
	 * 设计常用报关代码设置Dao
	 * 
	 * @param bcusInnerMergeDao
	 *            常用报关代码设置Dao
	 */
	public void setBcusInnerMergeDao(CommonBaseCodeDao bcusInnerMergeDao) {
		this.bcusInnerMergeDao = bcusInnerMergeDao;
	}

	public void makeBcsFactoryAndFactualCustomsRalation(String materielType,
			int projectType) {
		Map<String, List<StatCusNameRelationHsn>> fmap = new HashMap<String, List<StatCusNameRelationHsn>>();
		Set set = new HashSet();
		List factualCustomsRalation = casDao
				.findFactoryAndFactualCustomsRalation(materielType, projectType);
		for (int k = 0; k < factualCustomsRalation.size(); k++) {
			FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) factualCustomsRalation
					.get(k);
			String sek = ffc.getMateriel().getId()
					+ ffc.getStatCusNameRelationHsn().getEmsNo()
					+ ffc.getStatCusNameRelationHsn().getSeqNum();
			set.add(sek);
		}
		List factualCustomsList = casDao.findFactualCustoms(materielType,
				projectType);
		for (int i = 0; i < factualCustomsList.size(); i++) {
			StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
					.get(i);
			String key = fc.getSeqNum() == null ? "" : fc.getSeqNum()
					.toString();
			if (fmap.get(key) == null) {
				List temp = new ArrayList();
				temp.add(fc);
				fmap.put(key, temp);
			} else {
				fmap.get(key).add(fc);
			}
		}
		List<BcsInnerMerge> listBcsInnerMerge = bcsInnerMergeDao
				.findBcsInnerMergeByMerge(materielType);
		System.out.println("listBcsInnerMerge  size == "
				+ listBcsInnerMerge.size());
		for (BcsInnerMerge bcsInnerMerge : listBcsInnerMerge) {
			BcsTenInnerMerge bcsTenInnerMerge = bcsInnerMerge
					.getBcsTenInnerMerge();
			List<StatCusNameRelationHsn> klist = fmap.get(bcsTenInnerMerge
					.getSeqNum().toString());
			if (klist == null) {
				continue;
			}
			for (StatCusNameRelationHsn data : klist) {
				String tkey = bcsInnerMerge.getMateriel().getId()
						+ data.getEmsNo() + data.getSeqNum();
				if (set.contains(tkey)) {
					continue;
				}
				FactoryAndFactualCustomsRalation ffr = new FactoryAndFactualCustomsRalation();
				ffr.setMateriel(bcsInnerMerge.getMateriel());
				ffr.setStatCusNameRelationHsn(data);

				ffr.setUnitConvert(makeUnitConvert(ffr));
				casDao.saveOrUpdate(ffr);
			}
		}
	}

	public boolean makeAllFactoryAndFactualCustomsRalation(String materielType,
			int projectType) {
		try {
			switch (projectType) {
			case ProjectType.BCS:
				makeBcsFactoryAndFactualCustomsRalation(materielType,
						projectType);
				break;
			case ProjectType.DZSC:
				makeDzscFactoryAndFactualCustomsRalation(materielType,
						projectType);
				break;
			case ProjectType.BCUS:
				makeBcusFactoryAndFactualCustomsRalation(materielType,
						projectType);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void makeDzscFactoryAndFactualCustomsRalation(String materielType,
			int projectType) {
		Map<String, List<StatCusNameRelationHsn>> fmap = new HashMap<String, List<StatCusNameRelationHsn>>();
		Set set = new HashSet();
		List factualCustomsRalation = casDao
				.findFactoryAndFactualCustomsRalation(materielType, projectType);
		for (int k = 0; k < factualCustomsRalation.size(); k++) {
			FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) factualCustomsRalation
					.get(k);
			String sek = ffc.getMateriel().getId()
					+ ffc.getStatCusNameRelationHsn().getEmsNo()
					+ ffc.getStatCusNameRelationHsn().getSeqNum();
			set.add(sek);
		}
		List factualCustomsList = casDao.findFactualCustoms(materielType,
				projectType);
		for (int i = 0; i < factualCustomsList.size(); i++) {
			StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
					.get(i);
			String key = fc.getSeqNum() == null ? "" : fc.getSeqNum()
					.toString();
			if (fmap.get(key) == null) {
				List temp = new ArrayList();
				temp.add(fc);
				fmap.put(key, temp);
			} else {
				fmap.get(key).add(fc);
			}
		}
		List<DzscInnerMergeData> listDzscInnerMerge = casDao
				.findDzscInnerMergeDataByType(materielType);

		System.out.println("listDzscInnerMerge  size == "
				+ listDzscInnerMerge.size());
		int i = 0;
		for (DzscInnerMergeData dzscInnerMerge : listDzscInnerMerge) {
			List<StatCusNameRelationHsn> klist = fmap.get(dzscInnerMerge
					.getDzscTenInnerMerge().getTenSeqNum().toString());
			if (klist == null) {
				continue;
			}
			for (StatCusNameRelationHsn data : klist) {
				String tkey = dzscInnerMerge.getMateriel().getId()
						+ data.getEmsNo() + data.getSeqNum();
				if (set.contains(tkey)) {
					continue;
				}
				FactoryAndFactualCustomsRalation ffr = new FactoryAndFactualCustomsRalation();
				ffr.setMateriel(dzscInnerMerge.getMateriel());
				ffr.setStatCusNameRelationHsn(data);
				ffr.setUnitConvert(makeUnitConvert(ffr));
				casDao.saveOrUpdate(ffr);
			}
		}
	}

	public void makeBcusFactoryAndFactualCustomsRalation(String materielType,
			int projectType) {
		Map<String, List<StatCusNameRelationHsn>> fmap = new HashMap<String, List<StatCusNameRelationHsn>>();
		Set set = new HashSet();
		List factualCustomsRalation = casDao
				.findFactoryAndFactualCustomsRalation(materielType, projectType);
		for (int k = 0; k < factualCustomsRalation.size(); k++) {
			FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) factualCustomsRalation
					.get(k);
			String sek = ffc.getMateriel().getId()
					+ ffc.getStatCusNameRelationHsn().getEmsNo()
					+ ffc.getStatCusNameRelationHsn().getSeqNum();
			set.add(sek);
		}// 把已经存在的放在SET中
		List factualCustomsList = casDao.findFactualCustoms(materielType,
				projectType);
		for (int i = 0; i < factualCustomsList.size(); i++) {
			StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
					.get(i);
			String key = fc.getSeqNum() == null ? "" : fc.getSeqNum()
					.toString();
			if (fmap.get(key) == null) {
				List temp = new ArrayList();
				temp.add(fc);
				fmap.put(key, temp);
			} else {
				fmap.get(key).add(fc);
			}
		}
		List<InnerMergeData> listBcusInnerMerge = bcusInnerMergeDao
				.findTenIsnotNullInnerMergeDataByType(materielType);
		System.out.println("listBcusInnerMerge  size == "
				+ listBcusInnerMerge.size());

		for (InnerMergeData innerMergeData : listBcusInnerMerge) {
			List<StatCusNameRelationHsn> klist = fmap.get(innerMergeData
					.getHsAfterTenMemoNo().toString());
			if (klist == null) {
				continue;
			}
			for (StatCusNameRelationHsn data : klist) {
				String tkey = innerMergeData.getMateriel().getId()
						+ data.getEmsNo() + data.getSeqNum();
				if (set.contains(tkey)) {
					continue;
				}
				FactoryAndFactualCustomsRalation ffr = new FactoryAndFactualCustomsRalation();
				ffr.setMateriel(innerMergeData.getMateriel());
				ffr.setStatCusNameRelationHsn(data);
				ffr.setUnitConvert(makeUnitConvert(ffr));
				casDao.saveOrUpdate(ffr);
			}
		}
	}

	public boolean makeBcsContractImgExgToFactualCustoms(String materielType,
			int projectType) {
		try {
			Map<String, StatCusNameRelationHsn> fmap = new HashMap<String, StatCusNameRelationHsn>();
			List emsDetail = bcsInnerMergeDao
					.findExeStateBcsContract(materielType);// 查找所有正在执行的合同表体
			List factualCustomsList = casDao.findFactualCustoms(materielType,
					projectType); // 海关帐对帐表

			boolean isMateriel = MaterielType.MATERIEL.equals(materielType);

			for (int i = 0; i < factualCustomsList.size(); i++) {
				StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
						.get(i);
				String key = (fc.getEmsNo() == null ? "" : fc.getEmsNo())
						+ (fc.getSeqNum() == null ? "" : fc.getSeqNum());
				String nameSpecUnitName = (fc.getCusName() == null ? "" : fc
						.getCusName())
						+ (fc.getCusSpec() == null ? "" : fc.getCusSpec())
						+ (fc.getCusUnit() == null ? "" : fc.getCusUnit()
								.getName());
				fmap.put(key + nameSpecUnitName, fc);
			}
			//
			// 1.获得实际报关资料的信息(以归并序号与手册号为关键字)
			// 2.获得正在执行的资料库备案(以备案序号+备案资料库编号为关键字存储其内部归并序号)
			// 3.
			//
			Map<String, Integer> dictNumMap = new HashMap<String, Integer>();
			Map<String, List> dictMap = new HashMap<String, List>();// 存放所有正在执行备案库资料value备案序号
			Map<Integer, List> dictEmsImgExgMap = new HashMap<Integer, List>();
			List dict = bcsInnerMergeDao.findExeStateBcsDictPor(materielType);// 查找所有正在执行的资料库备案
			for (int i = 0; i < dict.size(); i++) {
				if (isMateriel) {
					BcsDictPorImg pimg = (BcsDictPorImg) dict.get(i);
					String key = (pimg.getSeqNum() == null ? "" : pimg
							.getSeqNum().toString())
							+ "/"
							+ (pimg.getDictPorHead().getDictPorEmsNo() == null ? ""
									: pimg.getDictPorHead().getDictPorEmsNo());
					// -----------------------------------------------------
					if (dictNumMap.get(key) == null) {
						dictNumMap.put(key, pimg.getInnerMergeSeqNum());
					}
				} else {
					BcsDictPorExg pimg = (BcsDictPorExg) dict.get(i);
					String key = pimg.getSeqNum().toString() + "/"
							+ pimg.getDictPorHead().getDictPorEmsNo();
					// -----------------------------------------------------
					if (dictNumMap.get(key) == null) {
						dictNumMap.put(key, pimg.getInnerMergeSeqNum());
					}
				}
			}
			for (int i = 0; i < emsDetail.size(); i++) {
				Object bs = emsDetail.get(i);
				// if (bs instanceof ContractImg) { 这样开销会大，性能会降低
				if (isMateriel) {
					ContractImg img = (ContractImg) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setEmsBeginDate(img.getContract().getBeginDate());
					te.setEmsEndDate(img.getContract().getEndDate());
					te.setEmsNo(img.getContract().getEmsNo());
					//
					// 电子手册
					//
					if (img.getContract().getIsContractEms() == null
							&& !img.getContract().getIsContractEms()) { //
						te.setSeqNum(img.getSeqNum());
					} else { // 电子化手册
						String key = (img.getCredenceNo() == null ? "" : img
								.getCredenceNo().toString())
								+ "/"
								+ (img.getContract().getCorrEmsNo() == null ? ""
										: img.getContract().getCorrEmsNo());
						te.setSeqNum(dictNumMap.get(key));
					}
					te.setProjectType(ProjectType.BCS);
					te.setCusName(img.getName());
					te.setCusSpec(img.getSpec());
					te.setComplex(img.getComplex());
					te.setCusUnit(img.getUnit());
					te.setMaterielType(materielType);
					//
					// emsNo + seqNum + name + sepc + unitName
					//
					String fkey = (te.getEmsNo() == null ? "" : te.getEmsNo())
							+ (te.getSeqNum() == null ? "" : te.getSeqNum())
							+ (te.getCusName() == null ? "" : te.getCusName())
							+ (te.getCusSpec() == null ? "" : te.getCusSpec())
							+ (te.getCusUnit() == null ? "" : te.getCusUnit()
									.getName());

					if (!fmap.containsKey(fkey)) {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				} else {
					ContractExg exg = (ContractExg) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setEmsBeginDate(exg.getContract().getBeginDate());
					te.setEmsEndDate(exg.getContract().getEndDate());
					te.setEmsNo(exg.getContract().getEmsNo());
					//
					// 电子手册
					//
					if (exg.getContract().getIsContractEms() == null
							&& !exg.getContract().getIsContractEms()) {
						te.setSeqNum(exg.getSeqNum());
					} else { // 电子化手册
						String key = (exg.getCredenceNo() == null ? "" : exg
								.getCredenceNo().toString()
								+ "/" + exg.getContract().getCorrEmsNo());
						te.setSeqNum(dictNumMap.get(key));
					}
					te.setProjectType(ProjectType.BCS);
					te.setCusName(exg.getName());
					te.setCusSpec(exg.getSpec());
					te.setComplex(exg.getComplex());
					te.setCusUnit(exg.getUnit());
					te.setMaterielType(materielType);
					//
					// emsNo + seqNum + name + sepc + unitName
					//
					String fkey = (te.getEmsNo() == null ? "" : te.getEmsNo())
							+ (te.getSeqNum() == null ? "" : te.getSeqNum())
							+ (te.getCusName() == null ? "" : te.getCusName())
							+ (te.getCusSpec() == null ? "" : te.getCusSpec())
							+ (te.getCusUnit() == null ? "" : te.getCusUnit()
									.getName());
					if (!fmap.containsKey(fkey)) {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean makeBcusContractImgExgToFactualCustoms(String materielType,
			int projectType) {
		try {
			Map<String, StatCusNameRelationHsn> fmap = new HashMap<String, StatCusNameRelationHsn>();
			List emsDetail = bcsInnerMergeDao
					.findExeStateBcusEms2K(materielType);// 查找所有正在执行的合同表体,设备时查询归并资料
			List factualCustomsList = casDao.findFactualCustoms(materielType,
					projectType);

			System.out.println(factualCustomsList.size());
			for (int i = 0; i < factualCustomsList.size(); i++) {
				StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
						.get(i);
				String key = (fc.getEmsNo() == null ? "" : fc.getEmsNo())
						+ (fc.getSeqNum() == null ? "" : fc.getSeqNum());
				fmap.put(key, fc);
			}
			Map<Integer, List> tempEmsImgExgMap = new HashMap<Integer, List>();
			for (int i = 0; i < emsDetail.size(); i++) {
				Object bs = emsDetail.get(i);
				if (bs instanceof EmsHeadH2kImg) {
					EmsHeadH2kImg img = (EmsHeadH2kImg) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setEmsBeginDate(img.getEmsHeadH2k().getBeginDate());
					te.setEmsEndDate(img.getEmsHeadH2k().getEndDate());
					te.setEmsNo(img.getEmsHeadH2k().getEmsNo());
					te.setSeqNum(img.getSeqNum());
					te.setProjectType(ProjectType.BCUS);
					te.setCusName(img.getName());
					te.setCusSpec(img.getSpec());
					te.setComplex(img.getComplex());
					te.setCusUnit(img.getUnit());
					te.setMaterielType(materielType);
					String fkey = (te.getEmsNo() == null ? "" : te.getEmsNo())
							+ (te.getSeqNum() == null ? "" : te.getSeqNum());

					if (fmap.containsKey(fkey)) {
						StatCusNameRelationHsn ta = fmap.get(fkey);
						String old = (ta.getCusName() == null ? "" : ta
								.getCusName())
								+ (ta.getCusSpec() == null ? "" : ta
										.getCusSpec()
										+ (ta.getCusUnit() == null ? "" : ta
												.getCusUnit().getCode()));
						String ne = (te.getCusName() == null ? "" : te
								.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (te.getCusUnit() == null ? "" : te
												.getCusUnit().getCode()));
						if (!old.equals(ne)) {
							casDao.saveOrUpdate(te);
							List ffcList = casDao
									.findFactoryAndFactualCustomsRalation(ta);
							for (int j = 0; j < ffcList.size(); j++) {
								FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ffcList
										.get(j);
								ffc.setStatCusNameRelationHsn(te);
								casDao.saveOrUpdate(ffc);
							}
							casDao.delete(ta);
						}

					} else {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				} else if (bs instanceof EmsHeadH2kExg) {
					EmsHeadH2kExg exg = (EmsHeadH2kExg) bs;
					List dataSourceVersion = emsEdiTrDao
							.findEmsHeadH2kVersion(exg);
					if (dataSourceVersion != null
							&& dataSourceVersion.size() != 0) {
						for (int k = 0; k < dataSourceVersion.size(); k++) {
							EmsHeadH2kVersion e = (EmsHeadH2kVersion) dataSourceVersion
									.get(k);
							StatCusNameRelationHsn te = new StatCusNameRelationHsn();
							te.setEmsBeginDate(exg.getEmsHeadH2k()
									.getBeginDate());
							te.setEmsEndDate(exg.getEmsHeadH2k().getEndDate());
							te.setEmsNo(exg.getEmsHeadH2k().getEmsNo());
							te.setSeqNum(exg.getSeqNum());
							te.setProjectType(ProjectType.BCUS);
							te.setCusName(exg.getName());
							te.setCusSpec(exg.getSpec());
							te.setComplex(exg.getComplex());
							te.setCusUnit(exg.getUnit());
							te.setMaterielType(materielType);
							te.setVersion(e.getVersion());// 版本号,add by sls
							String fkey = (te.getEmsNo() == null ? "" : te
									.getEmsNo())
									+ (te.getSeqNum() == null ? "" : te
											.getSeqNum());
							if (fmap.containsKey(fkey)) {
								StatCusNameRelationHsn ta = fmap.get(fkey);
								String old = (ta.getCusName() == null ? "" : ta
										.getCusName())
										+ (ta.getCusSpec() == null ? "" : ta
												.getCusSpec()
												+ (ta.getCusUnit() == null ? ""
														: ta.getCusUnit()
																.getCode()));
								String ne = (te.getCusName() == null ? "" : te
										.getCusName())
										+ (te.getCusSpec() == null ? "" : te
												.getCusSpec()
												+ (te.getCusUnit() == null ? ""
														: te.getCusUnit()
																.getCode()));
								if (!old.equals(ne)) {
									casDao.saveOrUpdate(te);
									List ffcList = casDao
											.findFactoryAndFactualCustomsRalation(ta);
									for (int j = 0; j < ffcList.size(); j++) {
										FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ffcList
												.get(j);
										ffc.setStatCusNameRelationHsn(te);
										casDao.saveOrUpdate(ffc);
									}
									casDao.delete(ta);
								}

							} else {
								fmap.put(fkey, te);
								casDao.saveOrUpdate(te);
							}
						}
					}
				} else if (bs instanceof InnerMergeData) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;

	}

	public boolean makeDzscContractImgExgToFactualCustoms(String materielType,
			int projectType, boolean isNeedSeqNum) {
		try {
			Map<String, StatCusNameRelationHsn> fmap = new HashMap<String, StatCusNameRelationHsn>();
			List factualCustomsList = casDao.findFactualCustoms(materielType,
					projectType);
			for (int i = 0; i < factualCustomsList.size(); i++) {
				StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
						.get(i);
				if (isNeedSeqNum) {// 如果需要导入归并序号，Key为手册号加上归并序号
					String key = (fc.getEmsNo() == null ? "" : fc.getEmsNo())
							+ (fc.getSeqNum() == null ? "" : fc.getSeqNum());
					fmap.put(key, fc);
				} else {// 如果不需要导入归并序号，Key为手册号加上名称规格单位
					String key = (fc.getEmsNo() == null ? "" : fc.getEmsNo())
							+ (fc.getCusName() == null ? "" : fc.getCusName())
							+ (fc.getCusSpec() == null ? "" : fc.getCusSpec())
							+ (fc.getCusUnit() == null ? "" : fc.getCusUnit()
									.getName());
					if (fmap.get(key) == null) {
						fmap.put(key, fc);
					}
				}
			}
			List emsDetail = bcsInnerMergeDao
					.findExeStateDzscEmsPorHead(materielType);// 查找所有正在执行的合同表体
			Map<Integer, List> tempEmsImgExgMap = new HashMap<Integer, List>();
			for (int i = 0; i < emsDetail.size(); i++) {
				Object bs = emsDetail.get(i);
				if (bs instanceof DzscEmsImgBill) {
					DzscEmsImgBill img = (DzscEmsImgBill) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setEmsBeginDate(img.getDzscEmsPorHead().getBeginDate());
					te.setEmsEndDate(img.getDzscEmsPorHead().getEndDate());
					te.setEmsNo(img.getDzscEmsPorHead().getEmsNo());
					te.setSeqNum(img.getTenSeqNum());
					te.setProjectType(ProjectType.DZSC);
					te.setCusName(img.getName());
					te.setCusSpec(img.getSpec());
					te.setComplex(img.getComplex());
					te.setCusUnit(img.getUnit());
					te.setMaterielType(materielType);
					String fkey = "";
					if (isNeedSeqNum) {
						fkey = (te.getEmsNo() == null ? "" : te.getEmsNo())
								+ (te.getSeqNum() == null ? "" : te.getSeqNum());
					} else {
						fkey = (te.getEmsNo() == null ? "" : te.getEmsNo())
								+ (te.getCusName() == null ? "" : te
										.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec())
								+ (te.getCusUnit() == null ? "" : te
										.getCusUnit().getName());
					}

					if (fmap.containsKey(fkey)) {
						StatCusNameRelationHsn ta = fmap.get(fkey);
						String old = (ta.getCusName() == null ? "" : ta
								.getCusName())
								+ (ta.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (ta.getCusUnit() == null ? "" : ta
												.getCusUnit().getCode()));
						String ne = (te.getCusName() == null ? "" : te
								.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (te.getCusUnit() == null ? "" : te
												.getCusUnit().getCode()));
						if (!old.equals(ne)) {
							casDao.saveOrUpdate(te);
							List ffcList = casDao
									.findFactoryAndFactualCustomsRalation(ta);
							for (int j = 0; j < ffcList.size(); j++) {
								FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ffcList
										.get(j);
								ffc.setStatCusNameRelationHsn(te);
								casDao.saveOrUpdate(ffc);
							}
							casDao.delete(ta);
						}

					} else {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				} else if (bs instanceof DzscEmsExgBill) {
					DzscEmsExgBill exg = (DzscEmsExgBill) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setEmsBeginDate(exg.getDzscEmsPorHead().getBeginDate());
					te.setEmsEndDate(exg.getDzscEmsPorHead().getEndDate());
					te.setEmsNo(exg.getDzscEmsPorHead().getEmsNo());
					te.setSeqNum(exg.getTenSeqNum());
					te.setProjectType(ProjectType.DZSC);
					te.setCusName(exg.getName());
					te.setCusSpec(exg.getSpec());
					te.setComplex(exg.getComplex());
					te.setCusUnit(exg.getUnit());
					te.setMaterielType(materielType);
					String fkey = "";
					if (isNeedSeqNum) {
						fkey = (te.getEmsNo() == null ? "" : te.getEmsNo())
								+ (te.getSeqNum() == null ? "" : te.getSeqNum());
					} else {
						fkey = (te.getEmsNo() == null ? "" : te.getEmsNo())
								+ (te.getCusName() == null ? "" : te
										.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec())
								+ (te.getCusUnit() == null ? "" : te
										.getCusUnit().getName());
					}
					if (fmap.containsKey(fkey)) {
						StatCusNameRelationHsn ta = fmap.get(fkey);
						String old = (ta.getCusName() == null ? "" : ta
								.getCusName())
								+ (ta.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (ta.getCusUnit() == null ? "" : ta
												.getCusUnit().getCode()));
						String ne = (te.getCusName() == null ? "" : te
								.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (te.getCusUnit() == null ? "" : te
												.getCusUnit().getCode()));
						if (!old.equals(ne)) {
							casDao.saveOrUpdate(te);
							List ffcList = casDao
									.findFactoryAndFactualCustomsRalation(ta);
							for (int j = 0; j < ffcList.size(); j++) {
								FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ffcList
										.get(j);
								ffc.setStatCusNameRelationHsn(te);
								casDao.saveOrUpdate(ffc);
							}
							casDao.delete(ta);
						}

					} else {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;

	}

	public boolean makeAllContractImgExgToFactualCustoms(String materielType,
			int projectType, boolean isNeedSeqNum) {
		try {
			switch (projectType) {
			case ProjectType.BCS:
				makeBcsContractImgExgToFactualCustoms(materielType, projectType);
				break;
			case ProjectType.DZSC:
				makeDzscContractImgExgToFactualCustoms(materielType,
						projectType, isNeedSeqNum);
				break;
			case ProjectType.BCUS:
				makeBcusContractImgExgToFactualCustoms(materielType,
						projectType);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 处理归并资料设备的导入
	 * 
	 * @param projectType
	 * @return
	 */
	public boolean makeAllInnerFixtureToFactualCustoms(int projectType) {
		try {
			Map<String, StatCusNameRelationHsn> fmap = new HashMap<String, StatCusNameRelationHsn>();
			List emsDetail = casDao.findFixturInnerDate(projectType);// 查找各个模块的内部归并设备资料
			List factualCustomsList = casDao.findFactualCustoms( // 查询各模块的设备海关帐对帐表
					MaterielType.MACHINE, projectType);

			for (int i = 0; i < factualCustomsList.size(); i++) {
				StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
						.get(i);
				String key = (fc.getProjectName() == null ? "" : fc
						.getProjectName())
						+ (fc.getSeqNum() == null ? "" : fc.getSeqNum());// 帐册类型加归并序号
				fmap.put(key, fc);
			}
			Map<Integer, List> tempEmsImgExgMap = new HashMap<Integer, List>();
			for (int i = 0; i < emsDetail.size(); i++) {
				Object bs = emsDetail.get(i);
				if (bs instanceof BcsInnerMerge) {// 手册电子化
					BcsInnerMerge innerData = (BcsInnerMerge) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setSeqNum(innerData.getBcsTenInnerMerge().getSeqNum());
					te.setProjectType(ProjectType.BCS);
					te.setCusName(innerData.getBcsTenInnerMerge().getName());
					te.setCusSpec(innerData.getBcsTenInnerMerge().getSpec());
					te.setComplex(innerData.getBcsTenInnerMerge().getComplex());
					te.setCusUnit(innerData.getBcsTenInnerMerge().getComUnit());
					te.setMaterielType(MaterielType.MACHINE);
					String fkey = (te.getProjectName() == null ? "" : te
							.getProjectName())
							+ (te.getSeqNum() == null ? "" : te.getSeqNum());

					if (fmap.containsKey(fkey)) {
						StatCusNameRelationHsn ta = fmap.get(fkey);
						String old = (ta.getCusName() == null ? "" : ta
								.getCusName())
								+ (ta.getCusSpec() == null ? "" : ta
										.getCusSpec()
										+ (ta.getCusUnit() == null ? "" : ta
												.getCusUnit().getCode()));
						String ne = (te.getCusName() == null ? "" : te
								.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (te.getCusUnit() == null ? "" : te
												.getCusUnit().getCode()));
						if (!old.equals(ne)) {
							casDao.saveOrUpdate(te);
							List ffcList = casDao
									.findFactoryAndFactualCustomsRalation(ta);
							for (int j = 0; j < ffcList.size(); j++) {
								FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ffcList
										.get(j);
								ffc.setStatCusNameRelationHsn(te);
								casDao.saveOrUpdate(ffc);
							}
							casDao.delete(ta);
						}

					} else {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				} else if (bs instanceof InnerMergeData) {// 联网监管
					InnerMergeData innerData = (InnerMergeData) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setSeqNum(innerData.getHsAfterTenMemoNo());
					te.setProjectType(ProjectType.BCUS);
					te.setCusName(innerData.getHsAfterMaterielTenName());
					te.setCusSpec(innerData.getHsAfterMaterielTenSpec());
					te.setComplex(innerData.getHsAfterComplex());
					te.setCusUnit(innerData.getHsAfterLegalUnit());
					te.setMaterielType(MaterielType.MACHINE);
					String fkey = (te.getProjectName() == null ? "" : te
							.getProjectName())
							+ (te.getSeqNum() == null ? "" : te.getSeqNum());

					if (fmap.containsKey(fkey)) {
						StatCusNameRelationHsn ta = fmap.get(fkey);
						String old = (ta.getCusName() == null ? "" : ta
								.getCusName())
								+ (ta.getCusSpec() == null ? "" : ta
										.getCusSpec()
										+ (ta.getCusUnit() == null ? "" : ta
												.getCusUnit().getCode()));
						String ne = (te.getCusName() == null ? "" : te
								.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (te.getCusUnit() == null ? "" : te
												.getCusUnit().getCode()));
						if (!old.equals(ne)) {
							casDao.saveOrUpdate(te);
							List ffcList = casDao
									.findFactoryAndFactualCustomsRalation(ta);
							for (int j = 0; j < ffcList.size(); j++) {
								FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ffcList
										.get(j);
								ffc.setStatCusNameRelationHsn(te);
								casDao.saveOrUpdate(ffc);
							}
							casDao.delete(ta);
						}

					} else {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				} else if (bs instanceof DzscInnerMergeData) {// 电子手册
					DzscInnerMergeData innerData = (DzscInnerMergeData) bs;
					StatCusNameRelationHsn te = new StatCusNameRelationHsn();
					te.setSeqNum(innerData.getDzscTenInnerMerge()
							.getTenSeqNum());
					te.setProjectType(ProjectType.DZSC);
					te.setCusName(innerData.getDzscTenInnerMerge()
							.getTenPtName());
					te.setCusSpec(innerData.getDzscTenInnerMerge()
							.getTenPtSpec());
					te.setComplex(innerData.getDzscTenInnerMerge()
							.getTenComplex());
					te
							.setCusUnit(innerData.getDzscTenInnerMerge()
									.getTenUnit());
					te.setMaterielType(MaterielType.MACHINE);
					String fkey = (te.getProjectName() == null ? "" : te
							.getProjectName())
							+ (te.getSeqNum() == null ? "" : te.getSeqNum());

					if (fmap.containsKey(fkey)) {
						StatCusNameRelationHsn ta = fmap.get(fkey);
						String old = (ta.getCusName() == null ? "" : ta
								.getCusName())
								+ (ta.getCusSpec() == null ? "" : ta
										.getCusSpec()
										+ (ta.getCusUnit() == null ? "" : ta
												.getCusUnit().getCode()));
						String ne = (te.getCusName() == null ? "" : te
								.getCusName())
								+ (te.getCusSpec() == null ? "" : te
										.getCusSpec()
										+ (te.getCusUnit() == null ? "" : te
												.getCusUnit().getCode()));
						if (!old.equals(ne)) {
							casDao.saveOrUpdate(te);
							List ffcList = casDao
									.findFactoryAndFactualCustomsRalation(ta);
							for (int j = 0; j < ffcList.size(); j++) {
								FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) ffcList
										.get(j);
								ffc.setStatCusNameRelationHsn(te);
								casDao.saveOrUpdate(ffc);
							}
							casDao.delete(ta);
						}

					} else {
						fmap.put(fkey, te);
						casDao.saveOrUpdate(te);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 合并内部归并(包括 bcs,bcus,dzsc) 去掉重复的归并数据
	 * 
	 * @param materielType
	 *            物料类型
	 * @param mapTempStatCusNameRelationHsnMt
	 *            临时的工厂物料实际报关商品映射
	 * @param mapMateriel
	 *            映射物料
	 * @param mapStatCusNameRelationHsn
	 *            映射实际报关商品
	 * @param mapMaterielOneToManyCustom
	 *            物料----->报关 一对多
	 */
	private void combineBcsInnerMerge(
			String materielType,
			Map<String, TempStatCusNameRelationHsnMt> mapTempStatCusNameRelationHsnMt,
			Map<String, TempStatCusNameRelationHsnMt> mapMateriel,
			Map<String, StatCusNameRelationHsn> mapStatCusNameRelationHsn,
			Map<String, List<TempStatCusNameRelationHsnMt>> mapMaterielOneToManyCustom) {
		List emsDetail = bcsInnerMergeDao.findExeStateBcsContract(materielType);// 查找所有正在执行的合同表体
		BcsParameterSet parameterSet = bcsInnerMergeDao.findBcsParameterSet();// 查找BCS参数设置，是否是电子化手册
		Map<Integer, List> tempEmsImgExgMap = new HashMap<Integer, List>();
		if (parameterSet.getIsContractEms() != null
				&& parameterSet.getIsContractEms()) {// 说明是电子化手册
			Map<String, Integer> dictNumMap = new HashMap<String, Integer>();
			Map<String, List> dictMap = new HashMap<String, List>();// 存放所有正在执行备案库资料value备案序号
			Map<Integer, List> dictEmsImgExgMap = new HashMap<Integer, List>();
			List dict = bcsInnerMergeDao.findExeStateBcsDictPor(materielType);// 查找所有正在执行的资料库备案
			for (int i = 0; i < dict.size(); i++) {
				if (dict.get(i) instanceof BcsDictPorImg) {
					BcsDictPorImg pimg = (BcsDictPorImg) dict.get(i);
					String key = (pimg.getSeqNum() == null ? "" : pimg
							.getSeqNum().toString())
							+ "/"
							+ (pimg.getDictPorHead().getDictPorEmsNo() == null ? ""
									: pimg.getDictPorHead().getDictPorEmsNo());
					// -----------------------------------------------------
					if (dictEmsImgExgMap.get(pimg.getInnerMergeSeqNum()) == null) {
						List dilist = new ArrayList();
						dilist.add(key);
						dictEmsImgExgMap
								.put(pimg.getInnerMergeSeqNum(), dilist);
					} else {
						List alist = dictEmsImgExgMap.get(pimg
								.getInnerMergeSeqNum());
						alist.add(key);
					}
					// -----------------------------------------------------
					if (dictNumMap.get(key) == null) {
						dictNumMap.put(key, pimg.getInnerMergeSeqNum());
					}
				} else if (dict.get(i) instanceof BcsDictPorExg) {
					BcsDictPorExg pimg = (BcsDictPorExg) dict.get(i);
					String key = pimg.getSeqNum().toString() + "/"
							+ pimg.getDictPorHead().getDictPorEmsNo();
					// -----------------------------------------------------
					if (dictEmsImgExgMap.get(pimg.getInnerMergeSeqNum()) == null) {
						List dilist = new ArrayList();
						dilist.add(key);
						dictEmsImgExgMap
								.put(pimg.getInnerMergeSeqNum(), dilist);
					} else {
						List alist = dictEmsImgExgMap.get(pimg
								.getInnerMergeSeqNum());
						alist.add(key);
					}
					// -----------------------------------------------------
					if (dictNumMap.get(key) == null) {
						dictNumMap.put(key, pimg.getInnerMergeSeqNum());
					}
				}
			}
			for (int i = 0; i < emsDetail.size(); i++) {
				Object bs = emsDetail.get(i);
				if (bs instanceof ContractImg) {
					ContractImg img = (ContractImg) bs;
					if (img.getContract().getIsContractEms() == null
							&& !img.getContract().getIsContractEms()) {
						continue;
					}
					String key = (img.getCredenceNo() == null ? "" : img
							.getCredenceNo().toString())
							+ "/"
							+ (img.getContract().getCorrEmsNo() == null ? ""
									: img.getContract().getCorrEmsNo());
					TempEmsImgExg te = new TempEmsImgExg();
					te.setBeginDate(img.getContract().getBeginDate());
					te.setEndDate(img.getContract().getEndDate());
					te.setEmsNo(img.getContract().getEmsNo());
					te.setSecNo(dictNumMap.get(key));

					if (dictMap.get(key) == null) {
						List dlist = new ArrayList();
						dlist.add(te);
						dictMap.put(key, dlist);
					} else {
						List oldlist = dictMap.get(dictMap.get(key));
						oldlist.add(te);
					}

				} else if (bs instanceof ContractExg) {
					ContractExg exg = (ContractExg) bs;
					if (exg.getContract().getIsContractEms() == null
							&& !exg.getContract().getIsContractEms()) {
						continue;
					}
					String key = (exg.getCredenceNo() == null ? "" : exg
							.getCredenceNo().toString()
							+ "/" + exg.getContract().getCorrEmsNo());
					TempEmsImgExg te = new TempEmsImgExg();
					te.setBeginDate(exg.getContract().getBeginDate());
					te.setEndDate(exg.getContract().getEndDate());
					te.setEmsNo(exg.getContract().getEmsNo());
					te.setSecNo(dictNumMap.get(key));
					if (dictMap.get(key) == null) {
						List dlist = new ArrayList();
						dlist.add(te);
						dictMap.put(key, dlist);
					} else {
						List oldlist = dictMap.get(key);
						oldlist.add(te);
					}
				}
			}
			Iterator itr = dictEmsImgExgMap.keySet().iterator();
			while (itr.hasNext()) {
				Integer seq = Integer.parseInt(itr.next().toString());
				if (tempEmsImgExgMap.get(seq) == null) {
					List relist = new ArrayList();
					List det = dictEmsImgExgMap.get(seq);
					for (int i = 0; i < det.size(); i++) {
						String key = det.get(i).toString();
						relist.addAll(dictMap.get(key));
					}
					tempEmsImgExgMap.put(seq, relist);
				}
			}

		} else {// 说明是普通纸质手册
			for (int i = 0; i < emsDetail.size(); i++) {// 说明是普通纸质手册
				Object bs = emsDetail.get(i);
				if (bs instanceof ContractImg) {
					ContractImg img = (ContractImg) bs;
					if (img.getContract().getIsContractEms() != null
							&& img.getContract().getIsContractEms()) {
						continue;
					}
					TempEmsImgExg te = new TempEmsImgExg();
					te.setBeginDate(img.getContract().getBeginDate());
					te.setEndDate(img.getContract().getEndDate());
					te.setEmsNo(img.getContract().getEmsNo());
					te.setSecNo(img.getCredenceNo());
					if (tempEmsImgExgMap.get(img.getCredenceNo()) == null) {
						List dlist = new ArrayList();
						dlist.add(te);
						tempEmsImgExgMap.put(img.getCredenceNo(), dlist);
					} else {
						List oldlist = tempEmsImgExgMap
								.get(img.getCredenceNo());
						oldlist.add(te);
					}

				} else if (bs instanceof ContractExg) {
					ContractExg exg = (ContractExg) bs;
					if (exg.getContract().getIsContractEms() != null
							&& exg.getContract().getIsContractEms()) {
						continue;
					}
					TempEmsImgExg te = new TempEmsImgExg();
					te.setBeginDate(exg.getContract().getBeginDate());
					te.setEndDate(exg.getContract().getEndDate());
					te.setEmsNo(exg.getContract().getEmsNo());
					te.setSecNo(exg.getCredenceNo());
					if (tempEmsImgExgMap.get(exg.getCredenceNo()) == null) {
						List dlist = new ArrayList();
						dlist.add(te);
						tempEmsImgExgMap.put(exg.getCredenceNo(), dlist);
					} else {
						List oldlist = tempEmsImgExgMap
								.get(exg.getCredenceNo());
						oldlist.add(te);
					}
				}
			}
		}

		//
		// 合并所有的归并数据去掉重复的记录,
		// key = 料件id + 编码 + 规格 + 名称 + 单位
		//
		List<BcsInnerMerge> listBcsInnerMerge = bcsInnerMergeDao
				.findBcsInnerMergeByMerge(materielType);

		System.out.println("listBcsInnerMerge  size == "
				+ listBcsInnerMerge.size());
		for (BcsInnerMerge bcsInnerMerge : listBcsInnerMerge) {
			BcsTenInnerMerge bcsTenInnerMerge = bcsInnerMerge
					.getBcsTenInnerMerge();
			List tempEmsImgExglist = tempEmsImgExgMap.get(bcsTenInnerMerge
					.getSeqNum());
			if (tempEmsImgExglist == null || tempEmsImgExglist.size() == 0) {
				continue;// 说明在合同中不存在对应
			}
			String materielId = bcsInnerMerge.getMateriel().getId();
			if (bcsTenInnerMerge.getComplex() == null) {
				continue;
			}
			//
			// key 去掉重复的记录
			//
			String keyTenCode = bcsTenInnerMerge.getComplex().getCode()
					+ (bcsTenInnerMerge.getName() == null ? ""
							: bcsTenInnerMerge.getName())
					+ (bcsTenInnerMerge.getSpec() == null ? ""
							: bcsTenInnerMerge.getSpec())
					+ (bcsTenInnerMerge.getComUnit() == null ? ""
							: bcsTenInnerMerge.getComUnit().getCode())
					+ (bcsTenInnerMerge.getSeqNum() == null ? ""
							: bcsTenInnerMerge.getSeqNum());
			//
			// key = 料件id + 编码 + 规格 + 名称 + 单位
			//
			String key = materielId + keyTenCode;

			for (int j = 0; j < tempEmsImgExglist.size(); j++) {
				TempStatCusNameRelationHsnMt tempStatCusNameRelationHsnMt = new TempStatCusNameRelationHsnMt();
				//
				// 物料
				//
				StatCusNameRelationMt sm = new StatCusNameRelationMt();
				sm.setMateriel(bcsInnerMerge.getMateriel());
				sm.setCompany(CommonUtils.getCompany());
				//
				// 报关十码
				//

				TempEmsImgExg tempt = (TempEmsImgExg) tempEmsImgExglist.get(j);
				StatCusNameRelationHsn sh = new StatCusNameRelationHsn();
				sh.setCompany(CommonUtils.getCompany());
				sh.setComplex(bcsTenInnerMerge.getComplex());
				sh.setCusName(bcsTenInnerMerge.getName());
				sh.setCusSpec(bcsTenInnerMerge.getSpec());
				sh.setCusUnit(bcsTenInnerMerge.getComUnit());
				sh.setSeqNum(bcsTenInnerMerge.getSeqNum());
				sh.setEmsBeginDate(tempt.getBeginDate());
				sh.setEmsEndDate(tempt.getEndDate());
				sh.setEmsNo(tempt.getEmsNo());
				sh.setProjectType(ProjectType.BCS);

				//
				// 存入map
				//
				tempStatCusNameRelationHsnMt.setStatCusNameRelationMt(sm);
				tempStatCusNameRelationHsnMt.setStatCusNameRelationHsn(sh);
				String nkey = key
						+ (sh.getEmsNo() == null ? "" : sh.getEmsNo());
				if (!mapTempStatCusNameRelationHsnMt.containsKey(nkey)) {
					mapTempStatCusNameRelationHsnMt.put(nkey,
							tempStatCusNameRelationHsnMt);
				}
				//
				// 填充 mapMateriel key=materiel.id
				//                
				if (!mapMateriel.containsKey(materielId)) {
					mapMateriel.put(materielId, tempStatCusNameRelationHsnMt);
				} else {
					List<TempStatCusNameRelationHsnMt> tempList = new ArrayList<TempStatCusNameRelationHsnMt>();
					tempList.add(tempStatCusNameRelationHsnMt);
					if (!mapMaterielOneToManyCustom.containsKey(materielId)) {
						tempList.add(mapMateriel.get(materielId));
						mapMaterielOneToManyCustom.put(materielId, tempList);
					} else {
						List<TempStatCusNameRelationHsnMt> oldTempList = mapMaterielOneToManyCustom
								.get(materielId);
						oldTempList.addAll(tempList);
					}
				}
				//
				// 获取十码商品集合没有重复的记(报关商品)
				//
				String nkeyTenCode = keyTenCode
						+ (sh.getEmsNo() == null ? "" : sh.getEmsNo());
				if (!mapStatCusNameRelationHsn.containsKey(nkeyTenCode)) {
					mapStatCusNameRelationHsn.put(nkeyTenCode, sh);
				}

			}
		}
	}

	/**
	 * 合并内部归并(包括 bcs,bcus,dzsc) 去掉重复的归并数据
	 * 
	 * @param materielType
	 *            物料类型
	 * @param mapTempStatCusNameRelationHsnMt
	 *            临时的工厂物料实际报关商品映射
	 * @param mapMateriel
	 *            映射物料
	 * @param mapStatCusNameRelationHsn
	 *            映射实际报关商品
	 * @param mapMaterielOneToManyCustom
	 *            物料----->报关 一对多
	 */
	private void combineBcusInnerMerge(
			String materielType,
			Map<String, TempStatCusNameRelationHsnMt> mapTempStatCusNameRelationHsnMt,
			Map<String, TempStatCusNameRelationHsnMt> mapMateriel,
			Map<String, StatCusNameRelationHsn> mapStatCusNameRelationHsn,
			Map<String, List<TempStatCusNameRelationHsnMt>> mapMaterielOneToManyCustom) {

		List emsDetail = bcsInnerMergeDao.findExeStateBcusEms2K(materielType);// 查找所有正在执行的合同表体
		Map<Integer, List> tempEmsImgExgMap = new HashMap<Integer, List>();
		for (int i = 0; i < emsDetail.size(); i++) {
			Object bs = emsDetail.get(i);
			if (bs instanceof EmsHeadH2kImg) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) bs;
				TempEmsImgExg te = new TempEmsImgExg();
				te.setBeginDate(img.getEmsHeadH2k().getBeginDate());
				te.setEndDate(img.getEmsHeadH2k().getEndDate());
				te.setEmsNo(img.getEmsHeadH2k().getEmsNo());
				te.setSecNo(img.getSeqNum());
				if (tempEmsImgExgMap.get(img.getSeqNum()) == null) {
					List dlist = new ArrayList();
					dlist.add(te);
					tempEmsImgExgMap.put(img.getSeqNum(), dlist);
				} else {
					List oldlist = tempEmsImgExgMap.get(img.getSeqNum());
					oldlist.add(te);
				}
			} else if (bs instanceof EmsHeadH2kExg) {
				EmsHeadH2kImg exg = (EmsHeadH2kImg) bs;
				TempEmsImgExg te = new TempEmsImgExg();
				te.setBeginDate(exg.getEmsHeadH2k().getBeginDate());
				te.setEndDate(exg.getEmsHeadH2k().getEndDate());
				te.setEmsNo(exg.getEmsHeadH2k().getEmsNo());
				te.setSecNo(exg.getSeqNum());
				if (tempEmsImgExgMap.get(exg.getSeqNum()) == null) {
					List dlist = new ArrayList();
					dlist.add(te);
					tempEmsImgExgMap.put(exg.getSeqNum(), dlist);
				} else {
					List oldlist = tempEmsImgExgMap.get(exg.getSeqNum());
					oldlist.add(te);
				}
			}
		}
		//
		// 合并所有的归并数据去掉重复的记录,
		// key = 料件id + 编码 + 规格 + 名称 + 单位
		//
		List<InnerMergeData> listBcusInnerMerge = bcusInnerMergeDao
				.findTenIsnotNullInnerMergeDataByType(materielType);
		System.out.println("listBcusInnerMerge  size == "
				+ listBcusInnerMerge.size());

		for (InnerMergeData innerMergeData : listBcusInnerMerge) {
			// BcsTenInnerMerge bcsTenInnerMerge = innerMergeData
			// .getBcsTenInnerMerge();
			List tempEmsImgExglist = tempEmsImgExgMap.get(innerMergeData
					.getHsAfterTenMemoNo());
			String materielId = innerMergeData.getMateriel().getId();
			if (innerMergeData.getHsAfterComplex() == null) {
				continue;
			}
			if (tempEmsImgExglist == null || tempEmsImgExglist.size() == 0) {
				continue;// 说明在合同中不存在对应
			}
			//
			// key 去掉重复的记录
			// 
			String keyTenCode = innerMergeData.getHsAfterComplex().getCode()
					+ (innerMergeData.getHsAfterMaterielTenName() == null ? ""
							: innerMergeData.getHsAfterMaterielTenName())
					+ (innerMergeData.getHsAfterMaterielTenSpec() == null ? ""
							: innerMergeData.getHsAfterMaterielTenSpec())
					+ (innerMergeData.getHsAfterMemoUnit() == null ? ""
							: innerMergeData.getHsAfterMemoUnit().getCode())
					+ (innerMergeData.getHsAfterTenMemoNo() == null ? ""
							: innerMergeData.getHsAfterTenMemoNo());
			//
			// key = 料件id + 编码 + 规格 + 名称 + 单位
			//
			String key = materielId + keyTenCode;

			for (int i = 0; i < tempEmsImgExglist.size(); i++) {

				TempStatCusNameRelationHsnMt TempStatCusNameRelationHsnMt = new TempStatCusNameRelationHsnMt();
				//
				// 物料
				//
				StatCusNameRelationMt sm = new StatCusNameRelationMt();
				sm.setMateriel(innerMergeData.getMateriel());
				sm.setCompany(CommonUtils.getCompany());
				//
				// 报关十码
				//
				TempEmsImgExg tempt = (TempEmsImgExg) tempEmsImgExglist.get(i);
				StatCusNameRelationHsn sh = new StatCusNameRelationHsn();
				sh.setCompany(CommonUtils.getCompany());
				sh.setComplex(innerMergeData.getHsAfterComplex());
				sh.setCusName(innerMergeData.getHsAfterMaterielTenName());
				sh.setCusSpec(innerMergeData.getHsAfterMaterielTenSpec());
				sh.setCusUnit(innerMergeData.getHsAfterMemoUnit());
				sh.setSeqNum(innerMergeData.getHsAfterTenMemoNo());
				sh.setEmsBeginDate(tempt.getBeginDate());
				sh.setEmsEndDate(tempt.getEndDate());
				sh.setEmsNo(tempt.getEmsNo());
				sh.setProjectType(ProjectType.BCUS);

				//
				// 存入map
				//
				TempStatCusNameRelationHsnMt.setStatCusNameRelationMt(sm);
				TempStatCusNameRelationHsnMt.setStatCusNameRelationHsn(sh);
				String nkey = key
						+ (sh.getEmsNo() == null ? "" : sh.getEmsNo());
				if (!mapTempStatCusNameRelationHsnMt.containsKey(nkey)) {
					mapTempStatCusNameRelationHsnMt.put(nkey,
							TempStatCusNameRelationHsnMt);
				}
				//
				// 填充 mapMateriel key=materiel.id
				//                
				if (!mapMateriel.containsKey(materielId)) {
					mapMateriel.put(materielId, TempStatCusNameRelationHsnMt);
				} else {
					List<TempStatCusNameRelationHsnMt> tempList = new ArrayList<TempStatCusNameRelationHsnMt>();
					tempList.add(TempStatCusNameRelationHsnMt);
					if (!mapMaterielOneToManyCustom.containsKey(materielId)) {
						tempList.add(mapMateriel.get(materielId));
						mapMaterielOneToManyCustom.put(materielId, tempList);
					} else {
						List<TempStatCusNameRelationHsnMt> oldTempList = mapMaterielOneToManyCustom
								.get(materielId);
						oldTempList.addAll(tempList);
					}
				}
				//
				// 获取十码商品集合没有重复的记(报关商品)
				//
				String nkeyTenCode = keyTenCode
						+ (sh.getEmsNo() == null ? "" : sh.getEmsNo());
				if (!mapStatCusNameRelationHsn.containsKey(nkeyTenCode)) {
					mapStatCusNameRelationHsn.put(nkeyTenCode, sh);
				}
			}
		}
	}

	/**
	 * 合并内部归并(包括 bcs,bcus,dzsc) 去掉重复的归并数据
	 * 
	 * @param materielType
	 *            物料类型
	 * @param mapTempStatCusNameRelationHsnMt
	 *            临时的工厂物料实际报关商品映射
	 * @param mapMateriel
	 *            映射物料
	 * @param mapStatCusNameRelationHsn
	 *            映射实际报关商品
	 * @param mapMaterielOneToManyCustom
	 *            物料----->报关 一对多
	 */
	private void combineDzscInnerMerge(
			String materielType,
			Map<String, TempStatCusNameRelationHsnMt> mapTempStatCusNameRelationHsnMt,
			Map<String, TempStatCusNameRelationHsnMt> mapMateriel,
			Map<String, StatCusNameRelationHsn> mapStatCusNameRelationHsn,
			Map<String, List<TempStatCusNameRelationHsnMt>> mapMaterielOneToManyCustom) {

		List emsDetail = bcsInnerMergeDao
				.findExeStateDzscEmsPorHead(materielType);// 查找所有正在执行的合同表体
		Map<Integer, List> tempEmsImgExgMap = new HashMap<Integer, List>();
		for (int i = 0; i < emsDetail.size(); i++) {
			Object bs = emsDetail.get(i);
			if (bs instanceof DzscEmsImgBill) {
				DzscEmsImgBill img = (DzscEmsImgBill) bs;
				TempEmsImgExg te = new TempEmsImgExg();
				te.setBeginDate(img.getDzscEmsPorHead().getBeginDate());
				te.setEndDate(img.getDzscEmsPorHead().getEndDate());
				te.setEmsNo(img.getDzscEmsPorHead().getEmsNo());
				te.setSecNo(img.getTenSeqNum());
				if (tempEmsImgExgMap.get(img.getTenSeqNum()) == null) {
					List dlist = new ArrayList();
					dlist.add(te);
					tempEmsImgExgMap.put(img.getTenSeqNum(), dlist);
				} else {
					List oldlist = tempEmsImgExgMap.get(img.getTenSeqNum());
					oldlist.add(te);
				}
			} else if (bs instanceof DzscEmsExgBill) {
				DzscEmsExgBill exg = (DzscEmsExgBill) bs;
				TempEmsImgExg te = new TempEmsImgExg();
				te.setBeginDate(exg.getDzscEmsPorHead().getBeginDate());
				te.setEndDate(exg.getDzscEmsPorHead().getEndDate());
				te.setEmsNo(exg.getDzscEmsPorHead().getEmsNo());
				te.setSecNo(exg.getTenSeqNum());
				if (tempEmsImgExgMap.get(exg.getTenSeqNum()) == null) {
					List dlist = new ArrayList();
					dlist.add(te);
					tempEmsImgExgMap.put(exg.getTenSeqNum(), dlist);
				} else {
					List oldlist = tempEmsImgExgMap.get(exg.getTenSeqNum());
					oldlist.add(te);
				}
			}
		}
		//
		// 合并所有的归并数据去掉重复的记录,
		// key = 料件id + 编码 + 规格 + 名称 + 单位
		//
		List<DzscInnerMergeData> listDzscInnerMerge = casDao
				.findDzscInnerMergeDataByType(materielType);

		System.out.println("listDzscInnerMerge  size == "
				+ listDzscInnerMerge.size());
		int i = 0;
		for (DzscInnerMergeData dzscInnerMerge : listDzscInnerMerge) {
			Complex complex = dzscInnerMerge.getDzscTenInnerMerge()
					.getTenComplex();
			String materielId = dzscInnerMerge.getMateriel().getId();
			if (complex == null || complex.getCode() == null) {
				System.out.println("dzscComplex.getCode() is null " + ++i);
				continue;
			}
			List tempEmsImgExglist = tempEmsImgExgMap.get(dzscInnerMerge
					.getDzscTenInnerMerge().getTenSeqNum());
			if (tempEmsImgExglist == null || tempEmsImgExglist.size() == 0) {
				System.out.println("帐册中不存在序号:"
						+ dzscInnerMerge.getDzscTenInnerMerge().getTenSeqNum());
				continue;// 说明在合同中不存在对应
			}
			//
			// key 去掉重复的记录
			//
			String keyTenCode = complex.getCode()
					+ (dzscInnerMerge.getDzscTenInnerMerge().getTenPtName() == null ? ""
							: dzscInnerMerge.getDzscTenInnerMerge()
									.getTenPtName())
					+ (dzscInnerMerge.getDzscTenInnerMerge().getTenPtSpec() == null ? ""
							: dzscInnerMerge.getDzscTenInnerMerge()
									.getTenPtSpec())
					+ (dzscInnerMerge.getDzscTenInnerMerge().getTenUnit() == null ? ""
							: dzscInnerMerge.getDzscTenInnerMerge()
									.getTenUnit().getCode())
					+ (dzscInnerMerge.getDzscTenInnerMerge().getTenSeqNum() == null ? ""
							: dzscInnerMerge.getDzscTenInnerMerge()
									.getTenSeqNum());
			//
			// key = 料件id + 编码 + 规格 + 名称 + 单位
			//
			String key = materielId + keyTenCode;

			for (int k = 0; k < tempEmsImgExglist.size(); k++) {
				TempStatCusNameRelationHsnMt tempStatCusNameRelationHsnMt = new TempStatCusNameRelationHsnMt();
				//
				// 物料
				//
				StatCusNameRelationMt sm = new StatCusNameRelationMt();
				sm.setMateriel(dzscInnerMerge.getMateriel());
				sm.setCompany(CommonUtils.getCompany());
				//
				// 报关十码
				//
				TempEmsImgExg tempt = (TempEmsImgExg) tempEmsImgExglist.get(k);
				StatCusNameRelationHsn sh = new StatCusNameRelationHsn();
				sh.setCompany(CommonUtils.getCompany());
				sh.setComplex(dzscInnerMerge.getDzscTenInnerMerge()
						.getTenComplex());
				sh.setCusName(dzscInnerMerge.getDzscTenInnerMerge()
						.getTenPtName());
				sh.setCusSpec(dzscInnerMerge.getDzscTenInnerMerge()
						.getTenPtSpec());
				sh.setCusUnit(dzscInnerMerge.getDzscTenInnerMerge()
						.getTenUnit());
				sh.setSeqNum(dzscInnerMerge.getDzscTenInnerMerge()
						.getTenSeqNum());
				sh.setEmsBeginDate(tempt.getBeginDate());
				sh.setEmsEndDate(tempt.getEndDate());
				sh.setEmsNo(tempt.getEmsNo());
				sh.setProjectType(ProjectType.DZSC);
				//
				// 存入map
				//
				tempStatCusNameRelationHsnMt.setStatCusNameRelationMt(sm);
				tempStatCusNameRelationHsnMt.setStatCusNameRelationHsn(sh);
				String nkey = key
						+ (sh.getEmsNo() == null ? "" : keyTenCode
								+ sh.getEmsNo());
				if (!mapTempStatCusNameRelationHsnMt.containsKey(nkey)) {
					mapTempStatCusNameRelationHsnMt.put(nkey,
							tempStatCusNameRelationHsnMt);
				}
				//
				// 填充 mapMateriel key=materiel.id
				//                
				if (!mapMateriel.containsKey(materielId)) {
					mapMateriel.put(materielId, tempStatCusNameRelationHsnMt);
				} else {
					List<TempStatCusNameRelationHsnMt> tempList = new ArrayList<TempStatCusNameRelationHsnMt>();
					tempList.add(tempStatCusNameRelationHsnMt);
					if (!mapMaterielOneToManyCustom.containsKey(materielId)) {
						tempList.add(mapMateriel.get(materielId));
						mapMaterielOneToManyCustom.put(materielId, tempList);
					} else {
						List<TempStatCusNameRelationHsnMt> oldTempList = mapMaterielOneToManyCustom
								.get(materielId);
						oldTempList.addAll(tempList);
					}
				}
				//
				// 获取十码商品集合没有重复的记(报关商品)
				//
				String nkeyTenCode = keyTenCode
						+ (sh.getEmsNo() == null ? "" : keyTenCode
								+ sh.getEmsNo());
				if (!mapStatCusNameRelationHsn.containsKey(nkeyTenCode)) {
					mapStatCusNameRelationHsn.put(nkeyTenCode, sh);
				}
			}
		}
	}

	/**
	 * 刷新对应表
	 * 
	 * @param materielType
	 *            物料类型
	 * @param param
	 *            模块类型
	 * @return 刷新所有对应表
	 */
	// public boolean makeCorrespondence(String materielType,
	// ProjectTypeParam param) {
	//
	// Map<String, TempStatCusNameRelationHsnMt> mapTempStatCusNameRelationHsnMt
	// = new HashMap<String, TempStatCusNameRelationHsnMt>();
	// Map<String, TempStatCusNameRelationHsnMt> mapMateriel = new
	// HashMap<String, TempStatCusNameRelationHsnMt>();
	// Map<String, StatCusNameRelationHsn> mapStatCusNameRelationHsn = new
	// HashMap<String, StatCusNameRelationHsn>();
	// Map<String, List<TempStatCusNameRelationHsnMt>>
	// mapMaterielOneToManyCustom = new HashMap<String,
	// List<TempStatCusNameRelationHsnMt>>();
	// //
	// // bcs
	// //
	// if (param.getIsBcs() == true) {
	// this.combineBcsInnerMerge(materielType,
	// mapTempStatCusNameRelationHsnMt, mapMateriel,
	// mapStatCusNameRelationHsn, mapMaterielOneToManyCustom);
	// }
	// //
	// // bcus
	// //
	// if (param.getIsBcus() == true) {
	// this.combineBcusInnerMerge(materielType,
	// mapTempStatCusNameRelationHsnMt, mapMateriel,
	// mapStatCusNameRelationHsn, mapMaterielOneToManyCustom);
	// }
	// //
	// // dzsc
	// //
	// if (param.getIsDzsc() == true) {
	// this.combineDzscInnerMerge(materielType,
	// mapTempStatCusNameRelationHsnMt, mapMateriel,
	// mapStatCusNameRelationHsn, mapMaterielOneToManyCustom);
	// }
	//
	// System.out.println("mapTempStatCusNameRelationHsnMt size --- "
	// + mapTempStatCusNameRelationHsnMt.size());
	// System.out.println("mapMateriel size --- " + mapMateriel.size());
	// System.out.println("mapStatCusNameRelationHsn size --- "
	// + mapStatCusNameRelationHsn.size());
	// System.out.println("mapMaterielOneToManyCustom size --- "
	// + mapMaterielOneToManyCustom.size());
	//
	// // -------------------------------------------------//
	// // 1.删除物料不存在的记录
	// // 2.删除十码不存在的记录
	// // 3.删除(十码,物料)不存在的记录的大类记录
	// // 4.删除所有没有十码 or 物料引用的大类对象
	// // 5.遍历其所有物料与报关一对多的记录(归并关系记录)
	// // 6.遍历其所有物料与报关
	// // -------------------------------------------------//
	//
	// // 已存在的十码
	// Map<String, StatCusNameRelationHsn> mapExistStatCusNameRelationHsn = new
	// HashMap<String, StatCusNameRelationHsn>();
	//
	// // 要删除的十码
	// List<StatCusNameRelationHsn> listDeleteStatCusNameRelationHsn = new
	// ArrayList<StatCusNameRelationHsn>();
	//
	// // 已存在的物料
	// Map<String, StatCusNameRelationMt> mapExistStatCusNameRelationMt = new
	// HashMap<String, StatCusNameRelationMt>();
	//
	// // 要删除的物料
	// List<StatCusNameRelationMt> listDeleteStatCusNameRelationMt = new
	// ArrayList<StatCusNameRelationMt>();
	//
	// // 存储大类对十码的记录的物料
	// Map<String, Map<String, StatCusNameRelationHsn>>
	// mapOneToManyStatCusNameRelationHsn = new HashMap<String, Map<String,
	// StatCusNameRelationHsn>>();
	//
	// //
	// // 删除十码折算比例为空的记录后 再获得已存在的关系对应记录(十码)并 和要删除的记录
	// //
	// this.casDao.deleteStatCusNameRelationHsn(materielType);
	// List<StatCusNameRelationHsn> listExistStatCusNameRelationHsn =
	// this.casDao
	// .findStatCusNameRelationHsn(materielType);
	// for (int i = 0; i < listExistStatCusNameRelationHsn.size(); i++) {
	// StatCusNameRelationHsn oldSh = listExistStatCusNameRelationHsn
	// .get(i);
	// //
	// // keyTenCode == 编码 + 规格 + 名称 + 单位
	// //
	// String keyTenCode = oldSh.getComplex().getCode()
	// + (oldSh.getCusName() == null ? "" : oldSh.getCusName())
	// + (oldSh.getCusSpec() == null ? "" : oldSh.getCusSpec())
	// + (oldSh.getCusUnit() == null ? ""
	// : oldSh.getCusUnit().getCode()
	// + (oldSh.getSeqNum() == null ? "" : oldSh
	// .getSeqNum())
	// + (oldSh.getEmsNo() == null ? "" : oldSh
	// .getEmsNo()));
	// if (!mapStatCusNameRelationHsn.containsKey(keyTenCode)) {
	// listDeleteStatCusNameRelationHsn.add(oldSh);
	// }
	// }
	// //
	// // 删除物料折算比例为空的记录后 获得已存在的关系对应记录(物料)
	// //
	// this.casDao.deleteStatCusNameRelationMt(materielType);
	// // List<StatCusNameRelationMt> listExistStatCusNameRelationMt =
	// this.casDao
	// // .findStatCusNameRelationMt(materielType);
	// // for (int i = 0; i < listExistStatCusNameRelationMt.size(); i++) {
	// // StatCusNameRelationMt oldMt = listExistStatCusNameRelationMt.get(i);
	// // //
	// // // materielId == 物料Id
	// // //
	// // String materielId = oldMt.getMateriel().getId();
	// // //
	// // // 如果已经存在的有，而新对应记录中不存在,这里是要删除的
	// // //
	// // if (!mapMateriel.containsKey(materielId)) {
	// // listDeleteStatCusNameRelationMt.add(oldMt);
	// // }
	// // }
	// //
	// // 删除物料,删除十码
	// //
	// this.casDao.deleteAll(listDeleteStatCusNameRelationMt);
	// this.casDao.deleteAll(listDeleteStatCusNameRelationHsn);
	// //
	// // 删除所有没有十码 or 物料引用的大类对象
	// //
	// List<StatCusNameRelation> listDeleteStstCusNameRelation = this.casDao
	// .findStatCusNameRelationNotInMtHsn(materielType);
	// for (int i = 0, n = listDeleteStstCusNameRelation.size(); i < n; i++) {
	// StatCusNameRelation statCusNameRelation = listDeleteStstCusNameRelation
	// .get(i);
	// this.casDao.deleteStatCusNameRelationHsn(statCusNameRelation);
	// this.casDao.deleteStatCusNameRelationMt(statCusNameRelation);
	// this.casDao.deleteStatCusNameRelation(statCusNameRelation);
	// }
	//
	// //
	// // 重新查询十码
	// //
	// listExistStatCusNameRelationHsn = this.casDao
	// .findStatCusNameRelationHsn(materielType);
	// // for (int i = 0; i < listExistStatCusNameRelationHsn.size(); i++) {
	// // StatCusNameRelationHsn oldSh = listExistStatCusNameRelationHsn
	// // .get(i);
	// // StatCusNameRelation sr = oldSh.getStatCusNameRelation();
	// // //
	// // // keyTenCode == 编码 + 规格 + 名称 + 单位
	// // //
	// // String keyTenCode = oldSh.getComplex().getCode()
	// // + (oldSh.getCusName() == null ? "" : oldSh.getCusName())
	// // + (oldSh.getCusSpec() == null ? "" : oldSh.getCusSpec())
	// // + (oldSh.getCusUnit() == null ? ""
	// // : oldSh.getCusUnit().getCode()
	// // + (oldSh.getSeqNum() == null ? "" : oldSh
	// // .getSeqNum())
	// // + (oldSh.getEmsNo() == null ? "" : oldSh
	// // .getEmsNo()));
	// // if (mapStatCusNameRelationHsn.containsKey(keyTenCode)) {
	// // mapExistStatCusNameRelationHsn.put(keyTenCode, oldSh);
	// // }// 存放已经存在的HS
	// // if (!mapOneToManyStatCusNameRelationHsn.containsKey(sr.getId())) {
	// // Map<String, StatCusNameRelationHsn> map = new HashMap<String,
	// StatCusNameRelationHsn>();
	// // map.put(keyTenCode, oldSh);
	// // mapOneToManyStatCusNameRelationHsn.put(sr.getId(), map);
	// // } else {
	// // Map<String, StatCusNameRelationHsn> map =
	// mapOneToManyStatCusNameRelationHsn
	// // .get(sr.getId());
	// // map.put(keyTenCode, oldSh);
	// // }// 存放一对多（一个大类对应多个HS）
	// // }
	// //
	// // 重新查询物料
	// //
	// // listExistStatCusNameRelationMt = this.casDao
	// // .findStatCusNameRelationMt(materielType);
	// // for (int i = 0; i < listExistStatCusNameRelationMt.size(); i++) {
	// // StatCusNameRelationMt oldMt = listExistStatCusNameRelationMt.get(i);
	// // //
	// // // materielId == 物料Id
	// // //
	// // String materielId = oldMt.getMateriel().getId();
	// // //
	// // // 如果已经存在的有，而新对应记录中不存在,这里是要删除的
	// // //
	// // if (mapMateriel.containsKey(materielId)) {
	// // mapExistStatCusNameRelationMt.put(materielId, oldMt);
	// // }
	// // }
	//
	// //
	// // 生成新增的十码，十码大类，报关物料
	// //
	// Iterator<List<TempStatCusNameRelationHsnMt>> iterator =
	// mapMaterielOneToManyCustom
	// .values().iterator();
	// //
	// // 遍历存在(料件对报关)一对多
	// //
	// while (iterator.hasNext()) {
	// List<TempStatCusNameRelationHsnMt> list = iterator.next();
	//
	// StatCusNameRelationHsn existStatCusNameRelationHsn = null;
	// StatCusNameRelationMt existStatCusNameRelationMt = null;
	// for (int i = 0; i < list.size(); i++) {
	// TempStatCusNameRelationHsnMt temp = list.get(i);
	// StatCusNameRelationHsn tempSh = temp
	// .getStatCusNameRelationHsn();
	// StatCusNameRelationMt tempSm = temp.getStatCusNameRelationMt();
	// //
	// // keyTenCode == 编码 + 规格 + 名称 + 单位
	// //
	// String tempKeyTenCode = tempSh.getComplex().getCode()
	// + (tempSh.getCusName() == null ? "" : tempSh
	// .getCusName())
	// + (tempSh.getCusSpec() == null ? "" : tempSh
	// .getCusSpec())
	// + (tempSh.getCusUnit() == null ? "" : tempSh
	// .getCusUnit().getCode()
	// + (tempSh.getSeqNum() == null ? "" : tempSh
	// .getSeqNum())
	// + (tempSh.getEmsNo() == null ? "" : tempSh
	// .getEmsNo()));
	// if (existStatCusNameRelationHsn == null) {
	// if (mapExistStatCusNameRelationHsn
	// .containsKey(tempKeyTenCode)) {
	// existStatCusNameRelationHsn = mapExistStatCusNameRelationHsn
	// .get(tempKeyTenCode);
	// }
	// }
	// if (existStatCusNameRelationMt == null) {
	// if (mapExistStatCusNameRelationMt.containsKey(tempSm
	// .getMateriel().getId())) {
	// existStatCusNameRelationMt = mapExistStatCusNameRelationMt
	// .get(tempSm.getMateriel().getId());
	// }
	// }
	// if (existStatCusNameRelationHsn != null
	// && existStatCusNameRelationMt != null) {
	// break;
	// }
	// }
	//
	// //
	// // 如果一对多的（物料对报关十码）所有的料件存在
	// //
	// if (existStatCusNameRelationMt != null) {
	// for (int i = 0; i < list.size(); i++) {
	// TempStatCusNameRelationHsnMt temp = list.get(i);
	// StatCusNameRelationHsn tempSh = temp
	// .getStatCusNameRelationHsn();
	// StatCusNameRelation sr = existStatCusNameRelationMt
	// .getStatCusNameRelation();
	// //
	// // keyTenCode == 编码 + 规格 + 名称 + 单位
	// //
	// String tempKeyTenCode = tempSh.getComplex().getCode()
	// + (tempSh.getCusName() == null ? "" : tempSh
	// .getCusName())
	// + (tempSh.getCusSpec() == null ? "" : tempSh
	// .getCusSpec())
	// + (tempSh.getCusUnit() == null ? "" : tempSh
	// .getCusUnit().getCode()
	// + (tempSh.getSeqNum() == null ? "" : tempSh
	// .getSeqNum())
	// + (tempSh.getEmsNo() == null ? "" : tempSh
	// .getEmsNo()));
	// if (mapOneToManyStatCusNameRelationHsn.containsKey(sr
	// .getId())) {
	// Map<String, StatCusNameRelationHsn> map =
	// mapOneToManyStatCusNameRelationHsn
	// .get(sr.getId());
	// if (map.containsKey(tempKeyTenCode)) {
	// // 说明已存在
	// continue;
	// } else {
	// if (!mapExistStatCusNameRelationHsn
	// .containsKey(tempKeyTenCode)) {
	// // tempSh.setStatCusNameRelation(sr);
	// // this.saveStatCusNameRelationHsn(tempSh);
	// // mapExistStatCusNameRelationHsn.put(
	// // tempKeyTenCode, tempSh);
	// // map.put(tempKeyTenCode, tempSh);
	// }
	// }
	// }
	// }
	// } else if (existStatCusNameRelationMt == null) {//
	// 如果一对多的（物料对报关十码）所有的料件不存在
	// if (existStatCusNameRelationHsn != null) {
	// for (int i = 0; i < list.size(); i++) {
	// TempStatCusNameRelationHsnMt temp = list.get(i);
	// StatCusNameRelationHsn tempSh = temp
	// .getStatCusNameRelationHsn();
	// // StatCusNameRelation sr = existStatCusNameRelationHsn
	// // .getStatCusNameRelation();
	// // //
	// // // keyTenCode == 编码 + 规格 + 名称 + 单位
	// // //
	// // String tempKeyTenCode = tempSh.getComplex().getCode()
	// // + (tempSh.getCusName() == null ? "" : tempSh
	// // .getCusName())
	// // + (tempSh.getCusSpec() == null ? "" : tempSh
	// // .getCusSpec())
	// // + (tempSh.getCusUnit() == null ? "" : tempSh
	// // .getCusUnit().getCode()
	// // + (tempSh.getSeqNum() == null ? ""
	// // : tempSh.getSeqNum())
	// // + (tempSh.getEmsNo() == null ? ""
	// // : tempSh.getEmsNo()));
	// // Map<String, StatCusNameRelationHsn> map =
	// mapOneToManyStatCusNameRelationHsn
	// // .get(sr.getId());
	// //
	// // // 说明不存在,要保存
	// // if (map != null && !map.containsKey(tempKeyTenCode)) {
	// // if (!mapExistStatCusNameRelationHsn
	// // .containsKey(tempKeyTenCode)) {
	// // tempSh.setStatCusNameRelation(sr);
	// // this.saveStatCusNameRelationHsn(tempSh);
	// // mapExistStatCusNameRelationHsn.put(
	// // tempKeyTenCode, tempSh);
	// // map.put(tempKeyTenCode, tempSh);
	// // }
	// // }
	// //
	// // 要继续保存 sm 因为只有一条 materiel
	// //
	// StatCusNameRelationMt tempSm = temp
	// .getStatCusNameRelationMt();
	// if (!mapExistStatCusNameRelationMt.containsKey(tempSm
	// .getMateriel().getId())) {
	// tempSm.setStatCusNameRelation(sr);
	// this.saveStatCusNameRelationMt(tempSm);
	// mapExistStatCusNameRelationMt.put(tempSm
	// .getMateriel().getId(), tempSm);
	// }
	// }
	// } else if (existStatCusNameRelationHsn == null) { // 但报关十码也不存在
	// StatCusNameRelation sr = new StatCusNameRelation();
	// StatCusNameRelationMt tempSm = null;
	// Map<String, StatCusNameRelationHsn> map = new HashMap<String,
	// StatCusNameRelationHsn>();
	// for (int i = 0; i < list.size(); i++) {
	// TempStatCusNameRelationHsnMt temp = list.get(i);
	// StatCusNameRelationHsn tempSh = temp
	// .getStatCusNameRelationHsn();
	// //
	// // keyTenCode == 编码 + 规格 + 名称 + 单位
	// //
	// String tempKeyTenCode = tempSh.getComplex().getCode()
	// + (tempSh.getCusName() == null ? "" : tempSh
	// .getCusName())
	// + (tempSh.getCusSpec() == null ? "" : tempSh
	// .getCusSpec())
	// + (tempSh.getCusUnit() == null ? "" : tempSh
	// .getCusUnit().getCode()
	// + (tempSh.getSeqNum() == null ? ""
	// : tempSh.getSeqNum())
	// + (tempSh.getEmsNo() == null ? ""
	// : tempSh.getEmsNo()));
	// if (i == 0) {
	// sr.setCompany(CommonUtils.getCompany());
	// sr.setComplex(tempSh.getComplex());
	// sr.setCusName(tempSh.getCusName());
	// sr.setCusSpec(tempSh.getCusSpec());
	// sr.setCusUnit(tempSh.getCusUnit());
	// sr.setMaterielType(materielType);
	// this.casDao.saveStatCusNameRelation(sr);
	// }
	// // tempSh.setStatCusNameRelation(sr);
	// // this.saveStatCusNameRelationHsn(tempSh);
	// // map.put(tempKeyTenCode, tempSh);
	// // mapExistStatCusNameRelationHsn.put(tempKeyTenCode,
	// // tempSh);
	// // if (tempSm == null) {
	// // tempSm = temp.getStatCusNameRelationMt();
	// // tempSm.setStatCusNameRelation(sr);
	// // this.saveStatCusNameRelationMt(tempSm);
	// // mapExistStatCusNameRelationMt.put(tempSm
	// // .getMateriel().getId(), tempSm);
	// // }
	// }
	// if (!map.isEmpty()) {
	// mapOneToManyStatCusNameRelationHsn.put(sr.getId(), map);
	// }
	// // 如果一对多的（物料对报关十码）所有的料件不存在
	// // 但报关十码存在
	// }
	// }
	// }
	//
	// //
	// // 生成新增的十码，十码大类，报关物料
	// //
	// Iterator<TempStatCusNameRelationHsnMt> iteratorAll =
	// mapTempStatCusNameRelationHsnMt
	// .values().iterator();
	// //
	// // 遍历存在(料件对报关)所有
	// //
	// while (iteratorAll.hasNext()) {
	// TempStatCusNameRelationHsnMt tempStatCusNameRelationHsnMt = iteratorAll
	// .next();
	// StatCusNameRelationHsn sh = tempStatCusNameRelationHsnMt
	// .getStatCusNameRelationHsn();
	// StatCusNameRelationMt sm = tempStatCusNameRelationHsnMt
	// .getStatCusNameRelationMt();
	//
	// StatCusNameRelation sr = new StatCusNameRelation();
	// //
	// // materiel id
	// //
	// String matreielId = sm.getMateriel().getId();
	// //
	// // keyTenCode == 编码 + 规格 + 名称 + 单位
	// //
	// String keyTenCode = sh.getComplex().getCode()
	// + (sh.getCusName() == null ? "" : sh.getCusName())
	// + (sh.getCusSpec() == null ? "" : sh.getCusSpec())
	// + (sh.getCusUnit() == null ? "" : sh.getCusUnit().getCode()
	// + (sh.getSeqNum() == null ? "" : sh.getSeqNum())
	// + (sh.getEmsNo() == null ? "" : sh.getEmsNo()));
	//
	// //
	// // 除去一对多的(物料对报关单）不参与计算原因上面已经计算过了
	// //
	// if (mapMaterielOneToManyCustom.containsKey(matreielId)) {
	// continue;
	// }
	//
	// //
	// // 去掉已存在对应表中的记录
	// //
	// if (mapExistStatCusNameRelationMt.containsKey(matreielId)) {
	// StatCusNameRelationMt existSm = mapExistStatCusNameRelationMt
	// .get(matreielId);
	// StatCusNameRelation tempSr = existSm.getStatCusNameRelation();
	// if (mapOneToManyStatCusNameRelationHsn.containsKey(tempSr
	// .getId())) {
	// Map<String, StatCusNameRelationHsn> map =
	// mapOneToManyStatCusNameRelationHsn
	// .get(tempSr.getId());
	// if (map.containsKey(keyTenCode)) {
	// System.out
	// .println("keyTenCode --------- " + keyTenCode);
	// // 说明已存在
	// continue;
	// }
	// }
	// }
	//
	// //
	// // 是否存在对应十码
	// //
	// StatCusNameRelationHsn existStatCusNameRelationHsn =
	// mapExistStatCusNameRelationHsn
	// .get(keyTenCode);
	// //
	// // 是否存在对应料件
	// //
	// StatCusNameRelationMt existStatCusNameRelationMt =
	// mapExistStatCusNameRelationMt
	// .get(matreielId);
	//
	// //
	// // 料件存在
	// //
	// if (existStatCusNameRelationMt != null) {
	// sr = existStatCusNameRelationMt.getStatCusNameRelation();
	// if (existStatCusNameRelationHsn == null) {
	// sh.setStatCusNameRelation(sr);
	// this.saveStatCusNameRelationHsn(sh);
	// mapExistStatCusNameRelationHsn.put(keyTenCode, sh);
	// //
	// // 加入到一对多的表中
	// //
	// if (mapOneToManyStatCusNameRelationHsn.containsKey(sr
	// .getId())) {
	// Map<String, StatCusNameRelationHsn> map =
	// mapOneToManyStatCusNameRelationHsn
	// .get(sr.getId());
	// if (!mapExistStatCusNameRelationHsn
	// .containsKey(keyTenCode)) {
	// map.put(keyTenCode, sh);
	// }
	// }
	// }
	// } else if (existStatCusNameRelationMt == null) {// 如果（物料对报关十码）所有的料件不存在
	// if (existStatCusNameRelationHsn != null) {// 但报关十码存在
	// // sr = existStatCusNameRelationHsn.getStatCusNameRelation();
	// // sm.setStatCusNameRelation(sr);
	// // this.saveStatCusNameRelationMt(sm);
	// // mapExistStatCusNameRelationMt.put(sm.getId(), sm);
	//
	// } else if (existStatCusNameRelationHsn == null) { // 但报关十码也不存在
	// sr.setCompany(CommonUtils.getCompany());
	// sr.setComplex(sh.getComplex());
	// sr.setCusName(sh.getCusName());
	// sr.setCusSpec(sh.getCusSpec());
	// sr.setCusUnit(sh.getCusUnit());
	// sr.setMaterielType(materielType);
	// //
	// // 保存中间大类对象
	// //
	// this.casDao.saveStatCusNameRelation(sr);
	// //
	// // 保存十码对象
	// //
	// // sh.setStatCusNameRelation(sr);
	// this.saveStatCusNameRelationHsn(sh);
	// mapExistStatCusNameRelationHsn.put(keyTenCode, sh);
	// //
	// // 保存物料对象
	// //
	// sm.setStatCusNameRelation(sr);
	// this.saveStatCusNameRelationMt(sm);
	// mapExistStatCusNameRelationMt.put(sm.getId(), sm);
	// //
	// // 加入到一对多的表中
	// //
	// Map<String, StatCusNameRelationHsn> map = new HashMap<String,
	// StatCusNameRelationHsn>();
	// map.put(keyTenCode, sh);
	// mapOneToManyStatCusNameRelationHsn.put(sr.getId(), map);
	// }
	// }
	// }
	// return true;
	// }
	/**
	 * 保存十码对象 ,加入初始化折算比
	 * 
	 * @param sh
	 *            实际报关商品
	 */
	// private void saveStatCusNameRelationHsn(StatCusNameRelationHsn sh) {
	// // StatCusNameRelation sr = sh.getStatCusNameRelation();
	// //
	// // 如果报关单位，和大类报关单位相同 折算比为 1.0,其它的为 null
	// //
	// Double unitConvert = null;
	// // if (sr.getCusUnit() != null && sh.getCusUnit() != null) {
	// // if (sr.getCusUnit().getCode().equalsIgnoreCase(
	// // sh.getCusUnit().getCode())) {
	// // unitConvert = 1.0;
	// // }
	// // }
	// // sh.setUnitConvert(unitConvert);
	// this.casDao.saveStatCusNameRelationHsn(sh);
	// }
	/**
	 * 单位折算
	 * 
	 * @param ut1
	 *            报关单位
	 * @param m
	 *            企业物料
	 */
	private Double makeUnitConvert(FactoryAndFactualCustomsRalation ffr) {
		Unit unit = ffr.getStatCusNameRelationHsn().getCusUnit();
		Materiel m = ffr.getMateriel();
		Double unitConvert = null;
		if (m.getUnitConvert() == null
				|| m.getUnitConvert().doubleValue() == 0.0) {
			CalUnit calUnit = m.getCalUnit();// 工厂单位
			CalUnit calWeightUnit = m.getCalWeightUnit();// 重量单位
			if (unit != null && calUnit != null) {
				String unitName = unit.getName().trim();
				String calUnitName = calUnit.getName().trim();
				//
				// 如果报关单位==工厂单位
				//
				if (unitName.equalsIgnoreCase(calUnitName)) {
					unitConvert = 1.0;
				} else if (unitName.indexOf("千克") != -1
						|| unitName.indexOf("公斤") != -1
						|| unitName.toLowerCase().indexOf("kg") != -1) { // 如果报关单位!=工厂单位
					// 而且报关是 [千克,公斤]
					System.out.println(" unitName = " + unitName);

					if (calUnitName.indexOf("千克") != -1
							|| calUnitName.indexOf("公斤") != -1
							|| calUnitName.toLowerCase().indexOf("kg") != -1) {
						unitConvert = 1.0;
					} else if (calUnitName.indexOf("克") != -1
							|| calUnitName.toLowerCase().indexOf("g") != -1) {
						unitConvert = 0.001;
					} else if (calUnitName.indexOf("磅") != -1
							|| calUnitName.toLowerCase().indexOf("英磅") != -1) {
						unitConvert = 0.4536;
					} else if (calUnitName.indexOf("斤") != -1) {
						unitConvert = 0.5;
					} else if (calUnitName.indexOf("两") != -1) {
						unitConvert = 0.05;
					} else if (calWeightUnit != null) {
						Double ptNetWeight = m.getPtNetWeight() == null ? 0.0
								: m.getPtNetWeight();
						String tempUnitName = calWeightUnit.getName() == null ? ""
								: calWeightUnit.getName().trim();
						if (tempUnitName.indexOf("千克") != -1
								|| tempUnitName.indexOf("公斤") != -1
								|| tempUnitName.toLowerCase().indexOf("kg") != -1) {
							unitConvert = ptNetWeight;
						} else if (tempUnitName.indexOf("克") != -1
								|| tempUnitName.toLowerCase().indexOf("g") != -1) {
							unitConvert = ptNetWeight * 0.001;
						} else if ("磅".equalsIgnoreCase(tempUnitName)
								|| "英磅".equalsIgnoreCase(tempUnitName)) {
							unitConvert = ptNetWeight * 0.4536;
						} else if ("斤".equalsIgnoreCase(tempUnitName)) {
							unitConvert = ptNetWeight * 0.5;
						} else if ("两".equalsIgnoreCase(tempUnitName)) {
							unitConvert = ptNetWeight * 0.05;
						}
					}
				} else {
					unitConvert = 1.0;
				}
			}
		} else {
			unitConvert = m.getUnitConvert();
		}
		return unitConvert;
	}

	/**
	 * 保存物料对象对象 ,加入初始化折算比
	 * 
	 * @param sm
	 *            企业物料
	 */
	private void saveStatCusNameRelationMt(StatCusNameRelationMt sm) {
		StatCusNameRelation sr = sm.getStatCusNameRelation();

		Double unitConvert = null;
		Materiel m = sm.getMateriel();
		//
		// 如果工厂折算系数为空
		//
		if (m.getUnitConvert() == null
				|| m.getUnitConvert().doubleValue() == 0.0) {
			Unit unit = sr.getCusUnit(); // 报关(大类)单位
			CalUnit calUnit = m.getCalUnit();// 工厂单位
			CalUnit calWeightUnit = m.getCalWeightUnit();// 重量单位
			if (unit != null && calUnit != null) {
				String unitName = unit.getName().trim();
				String calUnitName = calUnit.getName().trim();
				//
				// 如果报关单位==工厂单位
				//
				if (unitName.equalsIgnoreCase(calUnitName)) {
					unitConvert = 1.0;
				} else if (unitName.indexOf("千克") != -1
						|| unitName.indexOf("公斤") != -1
						|| unitName.toLowerCase().indexOf("kg") != -1) { // 如果报关单位!=工厂单位
					// 而且报关是 [千克,公斤]
					System.out.println(" unitName = " + unitName);

					if (calUnitName.indexOf("千克") != -1
							|| calUnitName.indexOf("公斤") != -1
							|| calUnitName.toLowerCase().indexOf("kg") != -1) {
						unitConvert = 1.0;
					} else if (calUnitName.indexOf("克") != -1
							|| calUnitName.toLowerCase().indexOf("g") != -1) {
						unitConvert = 0.001;
					} else if (calUnitName.indexOf("磅") != -1
							|| calUnitName.toLowerCase().indexOf("英磅") != -1) {
						unitConvert = 0.4536;
					} else if (calUnitName.indexOf("斤") != -1) {
						unitConvert = 0.5;
					} else if (calUnitName.indexOf("两") != -1) {
						unitConvert = 0.05;
					} else if (calWeightUnit != null) {
						Double ptNetWeight = m.getPtNetWeight() == null ? 0.0
								: m.getPtNetWeight();
						String tempUnitName = calWeightUnit.getName() == null ? ""
								: calWeightUnit.getName().trim();
						if (tempUnitName.indexOf("千克") != -1
								|| tempUnitName.indexOf("公斤") != -1
								|| tempUnitName.toLowerCase().indexOf("kg") != -1) {
							unitConvert = ptNetWeight;
						} else if (tempUnitName.indexOf("克") != -1
								|| tempUnitName.toLowerCase().indexOf("g") != -1) {
							unitConvert = ptNetWeight * 0.001;
						} else if ("磅".equalsIgnoreCase(tempUnitName)
								|| "英磅".equalsIgnoreCase(tempUnitName)) {
							unitConvert = ptNetWeight * 0.4536;
						} else if ("斤".equalsIgnoreCase(tempUnitName)) {
							unitConvert = ptNetWeight * 0.5;
						} else if ("两".equalsIgnoreCase(tempUnitName)) {
							unitConvert = ptNetWeight * 0.05;
						}
					}
				} else {
					unitConvert = 1.0;
				}
			}

		} else {
			unitConvert = m.getUnitConvert();
		}
		// if (unitConvert == null
		// || unitConvert <= 0.0) {
		// unitConvert = 1.0;
		// }
		//
		// 初始化折算比
		//         
		sm.setUnitConvert(unitConvert);
		this.casDao.saveStatCusNameRelationMt(sm);
	}

	/**
	 * 建立设备的对应关系
	 * 
	 * @param projectType
	 * @return
	 */
	public boolean makeAllFixFactoryAndFactualCustomsRalation(int projectType) {
		try {
			Map<String, List<StatCusNameRelationHsn>> fmap = new HashMap<String, List<StatCusNameRelationHsn>>();
			Set set = new HashSet();
			List factualCustomsRalation = casDao
					.findFactoryAndFactualCustomsRalation(MaterielType.MACHINE,
							projectType);
			for (int k = 0; k < factualCustomsRalation.size(); k++) {
				FactoryAndFactualCustomsRalation ffc = (FactoryAndFactualCustomsRalation) factualCustomsRalation
						.get(k);
				String sek = ffc.getMateriel().getId() + "/"
						+ ffc.getStatCusNameRelationHsn().getProjectType()
						+ "/" + ffc.getStatCusNameRelationHsn().getEmsNo()
						+ "/" + ffc.getStatCusNameRelationHsn().getSeqNum();
				set.add(sek);
			}// 把已经存在的放在SET中
			List factualCustomsList = casDao.findFactualCustoms(
					MaterielType.MACHINE, projectType);
			for (int i = 0; i < factualCustomsList.size(); i++) {
				StatCusNameRelationHsn fc = (StatCusNameRelationHsn) factualCustomsList
						.get(i);
				// if (fc.getEmsNo() == null || fc.getSeqNum() == null)
				// continue;
				String key = fc.getProjectType() + "/"
						+ fc.getSeqNum().toString();
				if (fmap.get(key) == null) {
					List temp = new ArrayList();
					temp.add(fc);
					fmap.put(key, temp);
				} else {
					fmap.get(key).add(fc);
				}
			}
			List listBcusInnerMerge = casDao.findFixturInnerDate(projectType);
			System.out.println("listBcusInnerMerge  size == "
					+ listBcusInnerMerge.size());

			for (Object bs : listBcusInnerMerge) {
				String seqNum = null;
				Materiel materiel = null;
				if (bs instanceof BcsInnerMerge) {// 手册电子化
					BcsInnerMerge innerData = (BcsInnerMerge) bs;
					seqNum = ProjectType.BCS
							+ "/"
							+ innerData.getBcsTenInnerMerge().getSeqNum()
									.toString();
					materiel = innerData.getMateriel();
				} else if (bs instanceof InnerMergeData) {// 联网监管
					InnerMergeData innerData = (InnerMergeData) bs;
					seqNum = ProjectType.BCUS + "/"
							+ innerData.getHsAfterTenMemoNo().toString();
					materiel = innerData.getMateriel();
				} else if (bs instanceof DzscInnerMergeData) {// 电子手册
					DzscInnerMergeData innerData = (DzscInnerMergeData) bs;
					seqNum = ProjectType.DZSC
							+ "/"
							+ innerData.getDzscTenInnerMerge().getTenSeqNum()
									.toString();
					materiel = innerData.getMateriel();
				}

				List<StatCusNameRelationHsn> klist = fmap.get(seqNum);
				if (klist == null) {
					continue;
				}
				for (StatCusNameRelationHsn data : klist) {
					String tkey = materiel.getId() + "/"
							+ data.getProjectType() + "/" + data.getEmsNo()
							+ "/" + data.getSeqNum();
					if (set.contains(tkey)) {
						continue;
					}

					FactoryAndFactualCustomsRalation ffr = new FactoryAndFactualCustomsRalation();
					ffr.setMateriel(materiel);
					ffr.setStatCusNameRelationHsn(data);
					ffr.setUnitConvert(makeUnitConvert(ffr));
					casDao.saveOrUpdate(ffr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @return the emsEdiTrDao
	 */
	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	/**
	 * @param emsEdiTrDao
	 *            the emsEdiTrDao to set
	 */
	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

}
