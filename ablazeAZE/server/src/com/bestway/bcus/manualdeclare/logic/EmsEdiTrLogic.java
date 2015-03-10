/*
 * Created on 2004-7-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.BaseApplyLoadXML;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.CommdityForbidHis;
import com.bestway.bcus.manualdeclare.entity.EmsEdiHeadH2kBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerVersion;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.bcus.manualdeclare.entity.EmsH2kBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFasImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.manualdeclare.entity.EmsUnitWear;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.manualdeclare.entity.TempOfInnerMergeData;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.message.CspMessageFunctions;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * checked by cjb 2010-3-25 电子账册操作类
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class EmsEdiTrLogic {
	/**
	 * 经营范围DAO类
	 */
	private EmsEdiTrDao emsEdiTrDao;

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	/** 繁转简 */
	private Hashtable gbHash = null;

	/**
	 * 经营范围变更
	 * 
	 * @return 经营范围表头
	 */
	public EmsEdiTrHead emsEdiChange() {
		EmsEdiTrHead emsHead = (EmsEdiTrHead) emsEdiTrDao.findEmsEdiTrHead()
				.get(0);
		EmsEdiTrHead emsHeadChange = getHeadChangeBean();
		List listImg = getImgChangeBean(emsHead, emsHeadChange);
		List listExg = getExgChangeBean(emsHead, emsHeadChange);
		emsEdiTrDao.saveEmsEdiTrHead(emsHeadChange);
		for (int i = 0; i < listImg.size(); i++) {
			emsEdiTrDao.saveEmsEdiTrImg((EmsEdiTrImg) listImg.get(i));
		}
		for (int i = 0; i < listExg.size(); i++) {
			emsEdiTrDao.saveEmsEdiTrExg((EmsEdiTrExg) listExg.get(i));
		}
		return emsHeadChange;
	}

	/**
	 * 根据料件货号查找归并前成品
	 * 
	 * @return
	 */
	// public List findEmsEdiMergerExgBefore(String ptNo) {
	// List list = this.emsEdiTrDao.findEmsEdiMergerExgBefore(ptNo);
	// return list;
	// }
	/**
	 * 复制并修改为变更后的经营范围表头.
	 * 
	 * @return 更后的经营范围表头
	 */
	private EmsEdiTrHead getHeadChangeBean() { // 经营范围表头变更
		List list = null;
		list = emsEdiTrDao.findEmsEdiTrHead(); // 原始
		EmsEdiTrHead emsHead = (EmsEdiTrHead) list.get(0);// 原始
		EmsEdiTrHead emsEdiTrChanged = null; // 变更
		try {
			emsEdiTrChanged = (EmsEdiTrHead) BeanUtils.cloneBean(emsHead);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		int changeTimes = 0;
		if (emsHead.getModifyTimes() != null) {
			changeTimes = emsHead.getModifyTimes().intValue();
		}
		emsEdiTrChanged.setDeclareType("2"); // 申报类型 1,申请备案 2,申请变更
		emsEdiTrChanged.setDeclareState("1"); // 审批状态 1，申请变更
		emsEdiTrChanged.setModifyMark(ModifyMarkState.UNCHANGE); // 变更（未修改）
		emsEdiTrChanged.setModifyTimes(Integer.valueOf(changeTimes + 1)); // 变更次数加1
		emsEdiTrChanged.setCheckMark("0");
		emsEdiTrChanged.setExeMark("2");
		emsEdiTrChanged.setId(null);
		return emsEdiTrChanged;
	}

	/**
	 * 经营范围料件变更, 复制经营范围的料件
	 * 
	 * @param emsHead
	 *            原经营范围表头
	 * @param emsHeadChange
	 *            变更后的经营范围表头
	 * @return
	 */
	private List getImgChangeBean(EmsEdiTrHead emsHead,
			EmsEdiTrHead emsHeadChange) { // 经营范围料件变更
		List list = null;
		List emsImgChanges = new Vector();
		list = emsEdiTrDao.findEmsEdiTrImg(emsHead); // 取未变更料件
		for (int i = 0; i < list.size(); i++) {
			EmsEdiTrImg emsImg = (EmsEdiTrImg) list.get(i);
			EmsEdiTrImg emsImgChanged = null; // 变更
			try {
				emsImgChanged = (EmsEdiTrImg) BeanUtils.cloneBean(emsImg);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsImgChanged.setModifyMark(ModifyMarkState.UNCHANGE); // 修改标记
			emsImgChanged.setId(null);
			emsImgChanged.setEmsEdiTrHead(emsHeadChange);
			emsImgChanges.add(emsImgChanged);
		}
		return emsImgChanges;
	}

	/**
	 * 经营范围成品变更
	 * 
	 * @param emsHead
	 * @param emsHeadChange
	 * @return
	 */
	private List getExgChangeBean(EmsEdiTrHead emsHead,
			EmsEdiTrHead emsHeadChange) {
		List list = null;
		List emsExgChanges = new Vector();
		list = emsEdiTrDao.findEmsEdiTrExg(emsHead); // 取未变更成品
		for (int i = 0; i < list.size(); i++) {
			EmsEdiTrExg emsExg = (EmsEdiTrExg) list.get(i);
			EmsEdiTrExg emsExgChanged = null; // 变更
			try {
				emsExgChanged = (EmsEdiTrExg) BeanUtils.cloneBean(emsExg);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsExgChanged.setModifyMark(ModifyMarkState.UNCHANGE);// 修改标记
			emsExgChanged.setId(null);
			emsExgChanged.setEmsEdiTrHead(emsHeadChange);
			emsExgChanges.add(emsExgChanged);
		}
		return emsExgChanges;
	}

	/**
	 * 归并关系备案新增
	 * 
	 * @return 归并关系备案的表头
	 */
	public EmsEdiMergerHead emsMergerAdd() { // 归并关系增加
		EmsEdiMergerHead emsEdiMergerHead = new EmsEdiMergerHead();
		SystemAction systemAction = (SystemAction) CommonUtils.getContext()
				.getBean("systemAction");
		EmsEdiTrHead emsEdiTrHead = (EmsEdiTrHead) emsEdiTrDao
				.findEmsEdiTrHead().get(0);
		String semsNo = systemAction.generateAutoNo(EmsEdiMergerHead.class);
		// edit by xxm 2006-1-9
		// String semsNo = emsEdiTrHead.getCopEmsNo();
		emsEdiMergerHead.setCopEmsNo(semsNo);// 企业内部编号
		// emsEdiMergerHead.setEmsNo(semsNo); //帐册编号 来自海关
		emsEdiMergerHead.setModifyTimes(Integer.valueOf(0)); // 变更次数

		emsEdiMergerHead.setDeclareType("1");
		emsEdiMergerHead.setDeclareState("1");
		emsEdiMergerHead.setSancEmsNo(emsEdiTrHead.getEmsNo()); // 批文帐册编号=经营范围帐册编号
		emsEdiMergerHead.setEmsApprNo(emsEdiTrHead.getEmsApprNo());// 批准证编号
		emsEdiMergerHead.setEmsType("8");// 帐册类型:便捷通关帐册
		emsEdiMergerHead.setTradeCode(emsEdiTrHead.getTradeCode()); // 经营单位代码
		emsEdiMergerHead.setTradeName(emsEdiTrHead.getTradeName()); // 经营单位名称
		emsEdiMergerHead.setMachCode(emsEdiTrHead.getOwnerCode());// 收货单位代码
		emsEdiMergerHead.setMachName(emsEdiTrHead.getOwnerName());// 收货单位名称
		emsEdiMergerHead.setTel(emsEdiTrHead.getPhone()); // 联系电话
		emsEdiMergerHead.setAddress(emsEdiTrHead.getAddress());// 联系地址
		emsEdiMergerHead.setDeclareErType(emsEdiTrHead.getDeclareErType()); // 申报单位类型
		emsEdiMergerHead.setInvestAmount(emsEdiTrHead.getInvestAmount());// 投资金额
		emsEdiMergerHead.setMachAbility(emsEdiTrHead.getProductRatio()); // 年加工生产能力
		emsEdiMergerHead.setInputEr(CommonUtils.getRequest().getUser()
				.getLoginName());
		emsEdiMergerHead.setModifyMark(ModifyMarkState.ADDED);
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String defaultDate = bartDateFormat.format(now);
		emsEdiMergerHead.setInputDate(java.sql.Date.valueOf(defaultDate));// 录入日期
		emsEdiMergerHead.setBeginDate(java.sql.Date.valueOf(defaultDate));// 开始有效期
		emsEdiMergerHead.setCompany(CommonUtils.getCompany());
		emsEdiMergerHead.setHistoryState(new Boolean(false)); // 历史状态
		emsEdiMergerHead.setNote("");
		if (emsEdiTrHead.getOwnerType() != null) {
			emsEdiMergerHead
					.setOwnerType(emsEdiTrHead.getOwnerType().getCode()); // 企业性质
		}
		if (emsEdiTrHead.getArea() != null) {
			emsEdiMergerHead.setArea(emsEdiTrHead.getArea().getCode()); // 地区代码
		}
		// 最大周转金额 = 年加工生产能力的/2
		emsEdiMergerHead.setMaxTurnMoney(Double.valueOf(emsEdiTrHead
				.getProductRatio().doubleValue() / 2));
		// emsEdiMergerHead.setMaxTurnMoney(Double.valueOf(((Company)CommonUtils.getCompany()).getAmountProduct().doubleValue()/20000));
		emsEdiTrDao.saveEmsEdiMergerHead(emsEdiMergerHead);
		return emsEdiMergerHead;
	}

	/**
	 * 归并关系表头变更
	 * 
	 * @param emsHead
	 * @return
	 */
	private EmsEdiMergerHead getMergerHeadChangeBean(EmsEdiMergerHead emsHead) {
		EmsEdiMergerHead emsEdiMergerChanged = null; // 变更
		try {
			emsEdiMergerChanged = (EmsEdiMergerHead) BeanUtils
					.cloneBean(emsHead);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		int changeTimes = emsHead.getModifyTimes().intValue();
		emsEdiMergerChanged.setDeclareType("2"); // 申报类型 1,申请备案 2,申请变更
		emsEdiMergerChanged.setDeclareState("1"); // 审批状态 1，申请变更
		emsEdiMergerChanged.setModifyMark(ModifyMarkState.UNCHANGE); // 变更（未修改）
		emsEdiMergerChanged.setModifyTimes(Integer.valueOf(changeTimes + 1)); // 变更次数加
		// 1
		emsEdiMergerChanged.setCheckMark("0");
		emsEdiMergerChanged.setExeMark("2");
		// edit by xxm 2007-02-08
		emsEdiMergerChanged.setDeclareDate(null);
		emsEdiMergerChanged.setDeclareTime(null);
		// -----------------------------------------
		emsEdiMergerChanged.setId(null);
		return emsEdiMergerChanged;
	}

	/**
	 * 归并关系后料件变更
	 * 
	 * @param emsHead原归并关系表头
	 * @param emsHeadChange变更后的归并关系表头
	 * @return
	 */
	private List getMergerAfterImgChangeBean(EmsEdiMergerHead emsHead,
			EmsEdiMergerHead emsHeadChange) { // (料件变更)
		List list = null;
		List emsImgChanges = new Vector();
		list = emsEdiTrDao.findEmsEdiMergerImgAfter(emsHead); // 取未变更归并后料件
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgAfter imgAfter = (EmsEdiMergerImgAfter) list.get(i);
			EmsEdiMergerImgAfter imgAfterChanged = null; // 变更
			try {
				imgAfterChanged = (EmsEdiMergerImgAfter) BeanUtils
						.cloneBean(imgAfter);
				imgAfterChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				imgAfterChanged.setId(null);
				imgAfterChanged.setEmsEdiMergerHead(emsHeadChange); // 设置变更后的与表头关连的字段
				emsEdiTrDao.saveEmsEdiMergerImgAfter(imgAfterChanged);
				// 复制Before（变更前内容）

				List listAfter = emsEdiTrDao
						.findEmsEdiMergerImgBefore(imgAfter);
				for (int j = 0; j < listAfter.size(); j++) {
					EmsEdiMergerImgBefore imgBefore = (EmsEdiMergerImgBefore) listAfter
							.get(j);
					EmsEdiMergerImgBefore imgBeforeChange = (EmsEdiMergerImgBefore) BeanUtils
							.cloneBean(imgBefore);
					imgBeforeChange.setModifyMark(ModifyMarkState.UNCHANGE);
					imgBeforeChange.setId(null);
					imgBeforeChange.setEmsEdiMergerImgAfter(imgAfterChanged);
					emsEdiTrDao.saveEmsEdiMergerImgBefore(imgBeforeChange);
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsImgChanges.add(imgAfterChanged);
		}
		return emsImgChanges;
	}

	/**
	 * 归并后成品变更
	 * 
	 * @param emsHead
	 * @param emsHeadChange
	 * @param bomChanges
	 * @return
	 */
	private List getMergerAfterExgChangeBean(EmsEdiMergerHead emsHead,
			EmsEdiMergerHead emsHeadChange, List bomChanges) {
		List list = null;
		List emsExgChanges = new Vector();
		list = emsEdiTrDao.findEmsEdiMergerExgAfter(emsHead); // 取未变更归并后成品
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgAfter exgAfter = (EmsEdiMergerExgAfter) list.get(i);
			EmsEdiMergerExgAfter exgAfterChanged = null; // 变更
			try {
				exgAfterChanged = (EmsEdiMergerExgAfter) BeanUtils
						.cloneBean(exgAfter);
				exgAfterChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				exgAfterChanged.setId(null);
				exgAfterChanged.setEmsEdiMergerHead(emsHeadChange); // 设置变更后的与表头关连的字段
				emsEdiTrDao.saveEmsEdiMergerExgAfter(exgAfterChanged);

				// 复制Before（变更前内容）
				List listAfer2 = emsEdiTrDao
						.findEmsEdiMergerExgBefore(exgAfter);
				for (int k = 0; k < listAfer2.size(); k++) {
					EmsEdiMergerExgBefore exgBefore = (EmsEdiMergerExgBefore) listAfer2
							.get(k);
					EmsEdiMergerExgBefore exgBeforeChange = (EmsEdiMergerExgBefore) BeanUtils
							.cloneBean(exgBefore);
					exgBeforeChange.setModifyMark(ModifyMarkState.UNCHANGE);
					exgBeforeChange.setId(null);
					exgBeforeChange.setEmsEdiMergerExgAfter(exgAfterChanged);
					emsEdiTrDao.saveEmsEdiMergerExgBefore(exgBeforeChange);

					h2kVersionChange(exgBefore, exgBeforeChange);

				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsExgChanges.add(exgAfterChanged);
		}
		return emsExgChanges;
	}

	/**
	 * 版本更改
	 * 
	 * @param exg
	 * @param exgBeforeChange
	 * @return
	 */
	private List h2kVersionChange(EmsEdiMergerExgBefore exg,
			EmsEdiMergerExgBefore exgBeforeChange) {
		List versionChanges = new Vector();
		List versions = emsEdiTrDao.findEmsEdiMergerVersion(exg);
		for (int i = 0; i < versions.size(); i++) {
			EmsEdiMergerVersion version = (EmsEdiMergerVersion) versions.get(i);
			EmsEdiMergerVersion versionChanged = null; // 变更
			try {
				versionChanged = (EmsEdiMergerVersion) BeanUtils
						.cloneBean(version);
				versionChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				versionChanged.setId(null);
				versionChanged.setEmsEdiMergerBefore(exgBeforeChange);

				emsEdiTrDao.saveEmsEdiMergerVersion(versionChanged);
				emsMergerBomChange(version, versionChanged);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			versionChanges.add(versionChanged);
		}
		return versionChanges;
	}

	/**
	 * 归并关系表头变更
	 * 
	 * @return
	 */
	public EmsEdiMergerHead emsEdiMergerChange() {
		// 归并关系变更
		EmsEdiMergerHead emsHead = (EmsEdiMergerHead) emsEdiTrDao
				.findEmsEdiMergerHead().get(0);
		EmsEdiMergerHead emsHeadChange = getMergerHeadChangeBean(emsHead);
		emsEdiTrDao.saveEmsEdiMergerHead(emsHeadChange);
		// 料件归并后归并前变更
		List imgAfterChanges = getMergerAfterImgChangeBean(emsHead,
				emsHeadChange);
		for (int i = 0; i < imgAfterChanges.size(); i++) {
			EmsEdiMergerImgAfter imgAfterChange = (EmsEdiMergerImgAfter) imgAfterChanges
					.get(i);
			emsEdiTrDao.saveEmsEdiMergerImgAfter(imgAfterChange);
		}
		// 成品归并后归并前变更
		List bomChanges = new Vector();
		List exgAfterChanges = getMergerAfterExgChangeBean(emsHead,
				emsHeadChange, bomChanges);
		for (int i = 0; i < exgAfterChanges.size(); i++) {
			EmsEdiMergerExgAfter exgAfterChange = (EmsEdiMergerExgAfter) exgAfterChanges
					.get(i);
			emsEdiTrDao.saveEmsEdiMergerExgAfter(exgAfterChange);
		}
		for (int i = 0; i < bomChanges.size(); i++) {
			emsEdiTrDao.saveEmsEdiMergerExgBom((EmsEdiMergerExgBom) bomChanges
					.get(i));
		}

		return emsHeadChange;
	}

	/**
	 * 电子帐册增加 (来自经营范围)
	 * 
	 * @return
	 */
	public EmsHeadH2k emsHeadH2kAdd() { // 电子帐册增加 (来自经营范围)
		EmsHeadH2k emsHeadH2k = new EmsHeadH2k();
		EmsEdiTrHead emsEdiTrHead = (EmsEdiTrHead) emsEdiTrDao
				.findEmsEdiTrHead().get(0);
		EmsEdiMergerHead emsMerger = (EmsEdiMergerHead) emsEdiTrDao
				.findEmsEdiMergerHeadByDeclareState(DeclareState.PROCESS_EXE);
		emsHeadH2k.setEmsType("8");// 帐册类型:便捷通关帐册
		emsHeadH2k.setDeclareType("1");
		emsHeadH2k.setDeclareState("1");
		emsHeadH2k.setModifyMark(ModifyMarkState.ADDED); // (新增)
		emsHeadH2k.setModifyTimes(Integer.valueOf(0));// 变更次数
		// String semsNo = systemAction.generateAutoNo(EmsHeadH2k.class); //
		// 企业内部编号
		// edit by xxm 2006-1-9
		String semsNo = emsMerger.getCopEmsNo();
		emsHeadH2k.setCopEmsNo(semsNo);// 企业内部编号
		// emsHeadH2k.setEmsNo(semsNo); //帐册编号（自动生成） 来自海关
		emsHeadH2k.setSancEmsNo(emsEdiTrHead.getEmsNo()); // 批文帐册编号==经营范围帐册编号
		emsHeadH2k.setTradeCode(emsEdiTrHead.getTradeCode()); // 经营单位代码
		emsHeadH2k.setTradeName(emsEdiTrHead.getTradeName()); // 经营单位名称
		emsHeadH2k.setMachCode(emsEdiTrHead.getOwnerCode());// 收货单位代码
		emsHeadH2k.setMachName(emsEdiTrHead.getOwnerName());// 收货单位名称
		emsHeadH2k.setDeclareErType(emsEdiTrHead.getDeclareErType()); // 申报单位类型
		emsHeadH2k.setMachAbility(emsEdiTrHead.getProductRatio()); // 年加工生产能力
		emsHeadH2k.setBeginDate(new Date());
		emsHeadH2k.setInvestAmount(emsEdiTrHead.getInvestAmount());
		if (emsEdiTrHead.getOwnerType() != null) {
			emsHeadH2k.setOwnerType(emsEdiTrHead.getOwnerType().getCode());
		}
		if (emsEdiTrHead.getArea() != null) {
			emsHeadH2k.setArea(emsEdiTrHead.getArea().getCode());
		}
		if (emsEdiTrHead.getProductRatio() != null) {
			emsHeadH2k.setMaxTurnMoney(Double.valueOf(emsEdiTrHead
					.getProductRatio().doubleValue() / 2));// 最大周转金额
		} else {
			emsHeadH2k.setMachAbility(null);
		}
		emsHeadH2k.setTel(emsEdiTrHead.getPhone()); // 联系电话
		emsHeadH2k.setAddress(emsEdiTrHead.getAddress());// 联系地址
		emsHeadH2k.setOutTradeCo(((Company) CommonUtils.getCompany())
				.getOutTradeCo());
		emsHeadH2k.setEmsApprNo(emsEdiTrHead.getEmsApprNo());// 批准证编号
		emsHeadH2k
				.setInputEr(CommonUtils.getRequest().getUser().getLoginName());// 录入员代号
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String defaultDate = bartDateFormat.format(now);
		emsHeadH2k.setInputDate(java.sql.Date.valueOf(defaultDate));// 录入日期
		emsHeadH2k.setCompany(CommonUtils.getCompany());
		emsHeadH2k.setHistoryState(new Boolean(false)); // 历史状态
		emsEdiTrDao.saveEmsHeadH2k(emsHeadH2k);
		return emsHeadH2k;
	}

	/**
	 * 对外公布的电子账册变更
	 * 
	 * @return
	 */
	public EmsHeadH2k emsHeadH2kChange(String changeState, EmsHeadH2k emsHead) {

		// 变更表头
		EmsHeadH2k emsHeadChange = emsHeadH2kChange(emsHead, changeState);
		emsEdiTrDao.saveEmsHeadH2k(emsHeadChange);

		h2kImgChange(emsHead, emsHeadChange);// 料件
		h2kExgChange(emsHead, emsHeadChange);// 成品,包括版本,BOM
		return emsHeadChange;
	}

	/**
	 * 电子分册变更
	 * 
	 * @return
	 */
	public EmsHeadH2kFas emsHeadH2FasChange(EmsHeadH2kFas fas) {
		EmsHeadH2kFas emsHeadChange = emsHeadH2kFasChange(fas);
		emsEdiTrDao.saveEmsHeadH2kFas(emsHeadChange);
		h2kFasImgChange(fas, emsHeadChange);// 料件
		h2kFasExgChange(fas, emsHeadChange);// 成品
		return emsHeadChange;
	}

	/**
	 * 电子账册表头变更
	 * 
	 * @param emsHead
	 * @return
	 */
	private EmsHeadH2k emsHeadH2kChange(EmsHeadH2k emsHead, String changeState) { // 电子帐册表头变更
		EmsHeadH2k emsHeadH2kChanged = null; // 变更
		try {
			emsHeadH2kChanged = (EmsHeadH2k) BeanUtils.cloneBean(emsHead);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		int changeTimes = emsHead.getModifyTimes().intValue();
		if (changeState.equals("2")) {
			emsHeadH2kChanged.setDeclareType("2"); // 申报类型 1,申请备案 2,申请变更
		} else if (changeState.equals("3")) {
			emsHeadH2kChanged.setDeclareType("3"); // 预变更
		}
		emsHeadH2kChanged.setDeclareState("1"); // 审批状态 1，申请变更
		emsHeadH2kChanged.setModifyMark(ModifyMarkState.UNCHANGE);// (未修改)
		emsHeadH2kChanged.setModifyTimes(Integer.valueOf(changeTimes + 1)); // 变更次数加
		// 1
		emsHeadH2kChanged.setCheckMark("0");
		emsHeadH2kChanged.setExeMark("2");
		// edit by xxm 2007-02-08
		emsHeadH2kChanged.setDeclareDate(null);
		emsHeadH2kChanged.setDeclareTime(null);
		// -------------------------------------
		emsHeadH2kChanged.setCompany(CommonUtils.getCompany());
		emsHeadH2kChanged.setId(null);
		return emsHeadH2kChanged;
	}

	/**
	 * 电子分册表头变更
	 * 
	 * @param emsHead
	 * @return
	 */
	private EmsHeadH2kFas emsHeadH2kFasChange(EmsHeadH2kFas emsHead) { // 电子分册表头变更
		EmsHeadH2kFas emsHeadH2kFasChanged = null; // 变更
		try {
			emsHeadH2kFasChanged = (EmsHeadH2kFas) BeanUtils.cloneBean(emsHead);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		int changeTimes = emsHead.getModifyTimes().intValue();
		emsHeadH2kFasChanged.setDeclareType("2"); // 申报类型 1,申请备案 2,申请变更
		emsHeadH2kFasChanged.setDeclareState("1"); // 审批状态 1，申请变更
		emsHeadH2kFasChanged.setModifyMark(ModifyMarkState.UNCHANGE);// (未修改)
		emsHeadH2kFasChanged.setModifyTimes(Integer.valueOf(changeTimes + 1)); // 变更次数加
		// 1
		emsHeadH2kFasChanged.setCheckMark("0");
		emsHeadH2kFasChanged.setExeMark("2");
		emsHeadH2kFasChanged.setCompany(CommonUtils.getCompany());
		emsHeadH2kFasChanged.setId(null);
		return emsHeadH2kFasChanged;
	}

	/**
	 * 复制emsHead所对应的归并后料件，并把它们的表头赋值为emsHeadChange.
	 * 
	 * @param emsHead
	 * @param emsHeadChange
	 * @return 返回变更后的归并后料件的列表
	 */
	private List h2kImgChange(EmsHeadH2k emsHead, // 归并关系后料件变更
			EmsHeadH2k emsHeadChange) {
		List emsImgChanges = new Vector();
		List emsHeadH2kImgs = emsEdiTrDao.findEmsHeadH2kImg(emsHead);
		for (int i = 0; i < emsHeadH2kImgs.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) emsHeadH2kImgs.get(i);
			EmsHeadH2kImg imgChanged = null; // 变更
			try {
				imgChanged = (EmsHeadH2kImg) BeanUtils.cloneBean(img);
				imgChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				imgChanged.setId(null);
				imgChanged.setEmsHeadH2k(emsHeadChange);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsImgChanges.add(imgChanged);
			emsEdiTrDao.saveEmsHeadH2kImg(imgChanged);
		}
		return emsImgChanges;
	}

	/**
	 * 电子分册料件变更
	 * 
	 * @param emsHead
	 * @param emsHeadChange
	 * @return
	 */
	private List h2kFasImgChange(EmsHeadH2kFas emsHead, // 电子分册料件变更
			EmsHeadH2kFas emsHeadChange) {
		List emsImgChanges = new Vector();
		List emsHeadH2kImgs = emsEdiTrDao.findEmsHeadH2kFasImg(emsHead); // 分册料件
		for (int i = 0; i < emsHeadH2kImgs.size(); i++) {
			EmsHeadH2kFasImg img = (EmsHeadH2kFasImg) emsHeadH2kImgs.get(i);
			EmsHeadH2kFasImg imgChanged = null; // 变更
			try {
				imgChanged = (EmsHeadH2kFasImg) BeanUtils.cloneBean(img);
				imgChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				imgChanged.setId(null);
				imgChanged.setEmsHeadH2kFas(emsHeadChange);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsImgChanges.add(imgChanged);
			emsEdiTrDao.saveEmsHeadH2kFasImg(imgChanged);
		}
		return emsImgChanges;
	}

	/**
	 * 电子分册成品变更
	 * 
	 * @param emsHead
	 * @param emsHeadChange
	 * @return
	 */
	private List h2kFasExgChange(EmsHeadH2kFas emsHead, // 电子分册成品变更
			EmsHeadH2kFas emsHeadChange) {
		List emsExgChanges = new Vector();
		List emsHeadH2kExgs = emsEdiTrDao.findEmsHeadH2kFasExg(emsHead); // 分册成品
		for (int i = 0; i < emsHeadH2kExgs.size(); i++) {
			EmsHeadH2kFasExg exg = (EmsHeadH2kFasExg) emsHeadH2kExgs.get(i);
			EmsHeadH2kFasExg exgChanged = null; // 变更
			try {
				exgChanged = (EmsHeadH2kFasExg) BeanUtils.cloneBean(exg);
				exgChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				exgChanged.setId(null);
				exgChanged.setEmsHeadH2kFas(emsHeadChange);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsExgChanges.add(exgChanged);
			emsEdiTrDao.saveEmsHeadH2kFasExg(exgChanged);
		}
		return emsExgChanges;
	}

	/**
	 * 复制emsHead所对应的归并后成品，并把它们的表头赋值为emsHeadChange. 同时复制了归并后成品所对应的版本及各版本的单耗．
	 * 
	 * @param emsHead
	 * @param emsHeadChange
	 * @return 返回变更后的归并后成品的列表
	 */
	private List h2kExgChange(EmsHeadH2k emsHead, // 归并关系后料件变更1
			EmsHeadH2k emsHeadChange) {
		List emsExgChanges = new Vector();
		List emsExgs = emsEdiTrDao.findEmsHeadH2kExg(emsHead);
		for (int i = 0; i < emsExgs.size(); i++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) emsExgs.get(i);
			EmsHeadH2kExg exgChanged = null; // 变更
			try {
				exgChanged = (EmsHeadH2kExg) BeanUtils.cloneBean(exg);
				exgChanged.setModifyMark(ModifyMarkState.UNCHANGE);// 未修改
				exgChanged.setId(null);
				exgChanged.setEmsHeadH2k(emsHeadChange);

				emsEdiTrDao.saveEmsHeadH2kExg(exgChanged);
				// 复制版本
				h2kVersionChange(exg, exgChanged);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsExgChanges.add(exgChanged);
		}
		return emsExgChanges;
	}

	/**
	 * 复制版本，同时复制了版本所对应的单耗．
	 * 
	 * @param exg
	 * @param exgChanged
	 * @return 变更后的版本列表
	 */
	private List h2kVersionChange(EmsHeadH2kExg exg, EmsHeadH2kExg exgChanged) {
		List versionChanges = new Vector();
		List versions = emsEdiTrDao.findEmsHeadH2kVersion(exg);
		for (int i = 0; i < versions.size(); i++) {
			EmsHeadH2kVersion version = (EmsHeadH2kVersion) versions.get(i);
			EmsHeadH2kVersion versionChanged = null; // 变更
			try {
				versionChanged = (EmsHeadH2kVersion) BeanUtils
						.cloneBean(version);
				versionChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				versionChanged.setId(null);
				versionChanged.setEmsHeadH2kExg(exgChanged);
				emsEdiTrDao.saveEmsHeadH2kVersion(versionChanged);
				h2kBomChange(version, versionChanged);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			versionChanges.add(versionChanged);
		}
		return versionChanges;
	}

	/**
	 * 版本更改
	 * 
	 * @param version
	 * @param versionChanged
	 * @return
	 */
	private List h2kBomChange(EmsHeadH2kVersion version,
			EmsHeadH2kVersion versionChanged) {
		List bomChanges = new Vector();
		List boms = emsEdiTrDao.findEmsHeadH2kBom(version);
		for (int i = 0; i < boms.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) boms.get(i);
			EmsHeadH2kBom bomChanged = null; // 变更
			try {
				bomChanged = (EmsHeadH2kBom) BeanUtils.cloneBean(bom);
				bomChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				bomChanged.setId(null);
				bomChanged.setEmsHeadH2kVersion(versionChanged);
				emsEdiTrDao.saveEmsHeadH2kBom(bomChanged);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			bomChanges.add(bomChanged);
		}
		return bomChanges;
	}

	/**
	 * 变更成品bom
	 * 
	 * @param version
	 * @param versionChanged
	 * @return
	 */
	private List emsMergerBomChange(EmsEdiMergerVersion version,
			EmsEdiMergerVersion versionChanged) {
		List bomChanges = new Vector();
		List boms = emsEdiTrDao.findEmsEdiMergerBom(version);
		for (int i = 0; i < boms.size(); i++) {
			EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) boms.get(i);
			EmsEdiMergerExgBom bomChanged = null; // 变更
			try {
				bomChanged = (EmsEdiMergerExgBom) BeanUtils.cloneBean(bom);
				bomChanged.setModifyMark(ModifyMarkState.UNCHANGE);
				bomChanged.setId(null);
				bomChanged.setEmsEdiMergerVersion(versionChanged);
				emsEdiTrDao.saveEmsEdiMergerExgBom(bomChanged);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			bomChanges.add(bomChanged);
		}
		return bomChanges;
	}

	/**
	 * 显示归并关系归并后成品和料件
	 * 
	 * @return
	 */
	public List findEmsEdiMergerImgExg() {
		EmsEdiMergerHead emsEdiMergerHead = this.emsEdiTrDao
				.findEmsEdiMergerHeadByDeclareState(DeclareState.PROCESS_EXE);
		List result = this.emsEdiTrDao
				.findEmsEdiMergerExgAfter(emsEdiMergerHead);
		List imgList = this.emsEdiTrDao
				.findEmsEdiMergerImgAfter(emsEdiMergerHead);
		result.addAll(imgList);
		return result;
	}

	/**
	 * 编码更改
	 * 
	 * @param isImg
	 * @param seqNum
	 * @param complex
	 */
	public void modityComplex(boolean isImg, String seqNum, Complex complex) {
		List list = emsEdiTrDao.findInnerBySeqNum(isImg,
				Integer.valueOf(seqNum));
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setHsAfterComplex(complex);
			emsEdiTrDao.saveInnerMergeData(data);
		}
	}

	/**
	 * 更改BOM料件编码
	 */
	public void modityBomFromComplex(String seqNum, Complex complex,
			EmsHeadH2k emsHeadh2k) {
		List list = this.emsEdiTrDao.findBomBySeqNum(seqNum, emsHeadh2k);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) list.get(i);
			bom.setComplex(complex);
			this.emsEdiTrDao.saveEmsHeadH2kBom(bom);
		}
	}

	/**
	 * 更改BOM
	 * 
	 * @param ptNo
	 * @param name
	 * @param spec
	 * @param head
	 */
	public void modityEmsEdiMergerBom(String ptNo, String name, String spec,
			EmsEdiMergerHead head) {
		List ls = this.emsEdiTrDao.findEmsEdiMergerBomByPtNo(head, ptNo);
		for (int i = 0; i < ls.size(); i++) {
			EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) ls.get(i);
			bom.setName(name);
			bom.setSpec(spec);
			this.emsEdiTrDao.saveEmsEdiMergerExgBom(bom);
		}

	}

	/**
	 * 更新四位商品序号
	 * 
	 * @param materielType
	 * @param oldSeqNum
	 * @param newSeqNum
	 */
	public void changeCustomsSeqNumFourNo(String materielType,
			Integer oldSeqNum, Integer newSeqNum) {
		List list = emsEdiTrDao.findInnerMergeByFourNo(materielType,
				newSeqNum.intValue());// 新的序号
		if (list != null && list.size() > 0) { // 已经存在新的序号则更新到旧的
			InnerMergeData obj = (InnerMergeData) list.get(0);
			// 开始更新旧的数据
			List ls = this.emsEdiTrDao.findInnerMergeByFourNo(materielType,
					oldSeqNum.intValue());
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setHsFourNo(newSeqNum);
				data.setHsFourCode(obj.getHsFourCode());// 十位商品编码
				data.setHsFourMaterielName(obj.getHsFourMaterielName());// 归并后商品名称
				this.emsEdiTrDao.saveInnerMergeData(data);
			}
		} else { // 不存在新的序号
			List ls = this.emsEdiTrDao.findInnerMergeByFourNo(materielType,
					oldSeqNum.intValue());
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setHsFourNo(newSeqNum);
				this.emsEdiTrDao.saveInnerMergeData(data);
			}
		}

	}

	/**
	 * 更新十位商品序号
	 * 
	 * @param materielType
	 * @param oldSeqNum
	 * @param newSeqNum
	 */
	public void changeCustomsSeqNum(String materielType, Integer oldSeqNum,
			Integer newSeqNum) {
		List list = emsEdiTrDao.findInnerMergeDataByTypeAndTenMemoNo(
				materielType, newSeqNum);// 新的序号
		if (list != null && list.size() > 0) { // 已经存在新的序号则更新到旧的
			InnerMergeData obj = (InnerMergeData) list.get(0);
			// 开始更新旧的数据
			List ls = this.emsEdiTrDao.findInnerMergeDataByTypeAndTenMemoNo(
					materielType, oldSeqNum);
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
				this.emsEdiTrDao.saveInnerMergeData(data);
			}
		} else { // 不存在新的序号
			List ls = this.emsEdiTrDao.findInnerMergeDataByTypeAndTenMemoNo(
					materielType, oldSeqNum);
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setHsAfterTenMemoNo(newSeqNum);
				this.emsEdiTrDao.saveInnerMergeData(data);
			}
		}

	}

	/**
	 * 修改物料内部归并关系
	 * 
	 * @param materielType
	 * @param oldSeqNum
	 * @param newSeqNum
	 */
	public void changeInner(String materielType, Integer oldSeqNum,
			Integer newSeqNum, String materielNo) {
		List<InnerMergeData> list = emsEdiTrDao
				.findInnerMergeDataByTypeAndTenMemoNo(materielType, newSeqNum);

		if (list != null && list.size() > 0) { // 已经存在新的序号则更新到旧的

			InnerMergeData obj = (InnerMergeData) list.get(0);

			list = this.emsEdiTrDao.findInnerMergeDataByTypeAndTenMemoNo(
					materielType, oldSeqNum);
			for (InnerMergeData innerMergeData : list) {
				if (innerMergeData.getMateriel() == null) {
				} else if (innerMergeData.getMateriel().getPtNo()
						.equals(materielNo)
						&& oldSeqNum.equals(innerMergeData
								.getHsAfterTenMemoNo())) {

					innerMergeData.setHsAfterTenMemoNo(newSeqNum);
					innerMergeData.setHsAfterComplex(obj.getHsAfterComplex());// 十位商品编码
					innerMergeData.setHsAfterMaterielTenName(obj
							.getHsAfterMaterielTenName());// 归并后商品名称
					innerMergeData.setHsAfterMaterielTenSpec(obj
							.getHsAfterMaterielTenSpec());// 归并后商品规格
					innerMergeData.setHsAfterMemoUnit(obj.getHsAfterMemoUnit());// 归并后备案单位
					innerMergeData
							.setHsAfterLegalUnit(obj.getHsAfterComplex() != null ? obj
									.getHsAfterComplex().getFirstUnit() : null); // 归并后第一法定单位
					innerMergeData.setHsAfterSecondLegalUnit(obj
							.getHsAfterComplex() != null ? obj
							.getHsAfterComplex().getSecondUnit() : null);// 归并后第二法定单位
					innerMergeData.setHsFourCode(obj.getHsFourCode()); // 四位商品编码
					innerMergeData.setHsFourMaterielName(obj
							.getHsFourMaterielName());// 四位商品名称
					innerMergeData.setHsFourNo(obj.getHsFourNo());// 四位序号

					emsEdiTrDao.saveInnerMergeData(innerMergeData);
					break;
				}
			}
		} else { // 不存在新的序号
			list = this.emsEdiTrDao.findInnerMergeDataByTypeAndTenMemoNo(
					materielType, oldSeqNum);
			for (InnerMergeData innerMergeData : list) {
				if (innerMergeData.getMateriel() == null) {
				} else if (innerMergeData.getMateriel().getPtNo()
						.equals(materielNo)
						&& oldSeqNum.equals(innerMergeData
								.getHsAfterTenMemoNo())) {
					innerMergeData.setHsAfterTenMemoNo(newSeqNum);
					emsEdiTrDao.saveInnerMergeData(innerMergeData);
					break;
				}
			}
		}

	}

	/*
	 * public List findMergerFromBomManage(String id){ }
	 */
	/**
	 * 电子帐册由预变更正式变更
	 */
	public void changeChange() {
		List result = emsEdiTrDao.findEmsHeadH2k();
		for (int i = 0; i < result.size(); i++) {
			EmsHeadH2k emsH2k = (EmsHeadH2k) result.get(i);
			if (emsH2k.getDeclareType().equals("3")) {
				emsH2k.setDeclareType("2");
				emsEdiTrDao.saveEmsHeadH2k(emsH2k);
			}
		}
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List<List> findEmsHeadH2kBomChange(EmsHeadH2k emsHeadH2k,
			Integer modifyTimes) {
		List<EmsHeadH2kBom> emsHeadH2kBomList = this.emsEdiTrDao
				.findEmsHeadH2kBomChange(emsHeadH2k, modifyTimes);
		return this.findContractUnitWaste(emsHeadH2kBomList);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List findEmsHeadH2kBomChange(EmsHeadH2k emsHeadH2k, Integer beginNo,
			Integer endNo, Integer[] seqNumArray) {
		List<EmsHeadH2kBom> emsHeadH2kBomList = this.emsEdiTrDao
				.findEmsHeadH2kBomChange(emsHeadH2k, beginNo, endNo,
						seqNumArray);
		return this.findContractUnitWaste(emsHeadH2kBomList);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List findEmsHeadH2kBomSingleVersionChange(EmsHeadH2k emsHeadH2k,
			Map<Integer, List<Integer>> mapAllSeqNum) {
		List<EmsHeadH2kBom> emsHeadH2kBomList = this.emsEdiTrDao
				.findEmsHeadH2kBomSingleVersionChange(emsHeadH2k, mapAllSeqNum);
		return this.findContractUnitWaste(emsHeadH2kBomList);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	public List findEmsHeadH2kBomByDate(EmsHeadH2k emsHeadH2k, Date beginDate,
			Date endDate, boolean isDeclared) {
		List<EmsHeadH2kBom> emsHeadH2kBomList = this.emsEdiTrDao
				.findEmsHeadH2kBomByDate(emsHeadH2k, beginDate, endDate,
						isDeclared);
		return this.findContractUnitWaste(emsHeadH2kBomList);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list(0) 成品版本（集合） list(1) 单耗记录（集合）
	 * 
	 * @return
	 */
	private List<List> findContractUnitWaste(
			List<EmsHeadH2kBom> emsHeadH2kBomList) {
		List<List> returnList = new ArrayList<List>();
		List<ContractUnitWaste> contractUnitWasteList = new ArrayList<ContractUnitWaste>();
		List<EmsHeadH2kVersion> emsHeadH2kVersionList = new ArrayList<EmsHeadH2kVersion>();
		Map<String, String> emsHeadH2kVersionMap = new HashMap<String, String>();

		List<EmsHeadH2kImg> emsHeadH2kImgList = new ArrayList<EmsHeadH2kImg>();
		Map<String, EmsHeadH2kBom> emsHeadH2kBomMap = new HashMap<String, EmsHeadH2kBom>();
		Hashtable<Integer, EmsHeadH2kImg> emsHeadH2kImgMap = new Hashtable<Integer, EmsHeadH2kImg>();
		final int columnCount = 6;

		for (int i = 0; i < emsHeadH2kBomList.size(); i++) {
			EmsHeadH2kBom emsHeadH2kBom = (EmsHeadH2kBom) emsHeadH2kBomList
					.get(i);
			EmsHeadH2kVersion bomVersion = emsHeadH2kBom.getEmsHeadH2kVersion();
			if (bomVersion == null || bomVersion.getEmsHeadH2kExg() == null) {
				continue;
			}
			//
			// 成品版本记录
			//
			String bomVersionId = bomVersion.getId();
			if (!emsHeadH2kVersionMap.containsKey(bomVersionId)) {
				emsHeadH2kVersionList.add(emsHeadH2kBom.getEmsHeadH2kVersion());
				emsHeadH2kVersionMap.put(bomVersionId, bomVersionId);
			}
			//
			// 组成料件记录
			//
			if (null == emsHeadH2kImgMap.get(emsHeadH2kBom.getSeqNum())) {
				//
				// 这里只存入两个字段（只是做为一个中间变量使用，最好重新生成一个中间类来代替)
				//
				EmsHeadH2kImg emsHeadH2kImg = new EmsHeadH2kImg();
				emsHeadH2kImg.setSeqNum(emsHeadH2kBom.getSeqNum());
				emsHeadH2kImg.setName(emsHeadH2kBom.getName());
				emsHeadH2kImgMap.put(emsHeadH2kBom.getSeqNum(), emsHeadH2kImg);
			}
			//
			// 成品版本id + 料件序号 == key
			//
			String key = bomVersionId
					+ (emsHeadH2kBom.getSeqNum() == null ? "" : emsHeadH2kBom
							.getSeqNum());
			emsHeadH2kBomMap.put(key, emsHeadH2kBom);
		}
		// ----排序-------------------------------------
		TreeMap ts = new TreeMap();
		Enumeration e1 = emsHeadH2kImgMap.keys();
		while (e1.hasMoreElements()) {
			Object key = e1.nextElement();
			ts.put(new Integer(String.valueOf(key)), key);
		}
		Set st = ts.keySet();
		for (Iterator i = st.iterator(); i.hasNext();) {
			emsHeadH2kImgList.add(emsHeadH2kImgMap.get(ts.get(i.next())));
		}

		//
		// 成品记录个数
		//
		int emsHeadH2kExgCount = emsHeadH2kVersionList.size();
		//
		// 获得所有料件记录
		//

		// emsHeadH2kImgList.addAll(returnList1);
		//
		// 获得交差数据表
		//
		int groupCount = emsHeadH2kExgCount / columnCount
				+ ((emsHeadH2kExgCount % columnCount) > 0 ? 1 : 0);
		//
		// 以成品行数为6条记录进行--手动分组
		//
		for (int g = 0; g < groupCount; g++) {
			//
			// 获取以列的个数分组的临时成品列表
			//
			List<EmsHeadH2kVersion> tempEmsHeadH2kVersionList = new ArrayList<EmsHeadH2kVersion>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < emsHeadH2kExgCount) {
					tempEmsHeadH2kVersionList.add(emsHeadH2kVersionList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < emsHeadH2kImgList.size(); i++) {
				EmsHeadH2kImg emsHeadH2kImg = emsHeadH2kImgList.get(i);
				boolean isExist = false;
				ContractUnitWaste contractUnitWaste = new ContractUnitWaste();
				contractUnitWaste.setGroupId(g);
				contractUnitWaste.setPtNo(String.valueOf(emsHeadH2kImg
						.getSeqNum()));
				contractUnitWaste.setPtName(emsHeadH2kImg.getName());
				for (int j = 0; j < tempEmsHeadH2kVersionList.size(); j++) {
					//
					// 成品版本id + 料件序号 == key
					//
					EmsHeadH2kVersion emsHeadH2kVersion = tempEmsHeadH2kVersionList
							.get(j);
					String key = emsHeadH2kVersion.getId()
							+ (emsHeadH2kImg.getSeqNum() == null ? ""
									: emsHeadH2kImg.getSeqNum());
					EmsHeadH2kBom emsHeadH2kBom = emsHeadH2kBomMap.get(key);
					if (emsHeadH2kBom == null) {
						continue;
					}
					switch (j) {
					case 0:
						contractUnitWaste.setUnitWaste1(emsHeadH2kBom
								.getUnitWear());
						contractUnitWaste
								.setWasteRate1(emsHeadH2kBom.getWear());
						isExist = true;
						break;
					case 1:
						contractUnitWaste.setUnitWaste2(emsHeadH2kBom
								.getUnitWear());
						contractUnitWaste
								.setWasteRate2(emsHeadH2kBom.getWear());
						isExist = true;
						break;
					case 2:
						contractUnitWaste.setUnitWaste3(emsHeadH2kBom
								.getUnitWear());
						contractUnitWaste
								.setWasteRate3(emsHeadH2kBom.getWear());
						isExist = true;
						break;
					case 3:
						contractUnitWaste.setUnitWaste4(emsHeadH2kBom
								.getUnitWear());
						contractUnitWaste
								.setWasteRate4(emsHeadH2kBom.getWear());
						isExist = true;
						break;
					case 4:
						contractUnitWaste.setUnitWaste5(emsHeadH2kBom
								.getUnitWear());
						contractUnitWaste
								.setWasteRate5(emsHeadH2kBom.getWear());
						isExist = true;
						break;
					case 5:
						contractUnitWaste.setUnitWaste6(emsHeadH2kBom
								.getUnitWear());
						contractUnitWaste
								.setWasteRate6(emsHeadH2kBom.getWear());
						isExist = true;
						break;
					}
				}
				if (isExist == true) {
					contractUnitWasteList.add(contractUnitWaste);
				}
			}
		}
		returnList.add(emsHeadH2kVersionList);// 0==成品版本
		returnList.add(contractUnitWasteList);// 1==单耗
		return returnList;
	}

	/**
	 * 得到禁用商品
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getTempEmsEdiH2kForBid(boolean isMaterial) {
		List list = emsEdiTrDao.findEdiMaterielInfo(isMaterial);
		List tempListExg = new ArrayList();
		if (isMaterial) {
			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
				BillTemp obj = new BillTemp();
				obj.setBill1(String.valueOf(img.getSeqNum()));
				obj.setBill2(img.getComplex() == null ? null : img.getComplex()
						.getCode());
				obj.setBill3(img.getName());
				obj.setBill4(img.getSpec());
				obj.setBill5(img.getUnit() == null ? null : img.getUnit()
						.getName());
				obj.setBill7(String.valueOf(img.getQty() == null ? 0.0 : img
						.getQty()));
				tempListExg.add(obj);
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				// EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(i);
				EmsHeadH2kVersion ver = (EmsHeadH2kVersion) list.get(i);
				EmsHeadH2kExg exg = (EmsHeadH2kExg) ver.getEmsHeadH2kExg();
				// List listVersion = emsEdiTrDao.findVersionNo(exg.getId());
				// for (int j = 0; j < listVersion.size(); j++) {
				BillTemp obj = new BillTemp();
				// Object verS = listVersion.get(j);
				// if (verS != null && !verS.equals("")) {
				String version = ver.getVersion().toString();// listVersion.get(j).toString();
				obj.setBill1(String.valueOf(exg.getSeqNum()));
				obj.setBill2(exg.getComplex() == null ? null : exg.getComplex()
						.getCode());
				obj.setBill3(exg.getName());
				obj.setBill4(exg.getSpec());
				obj.setBill5(exg.getUnit() == null ? null : exg.getUnit()
						.getName());
				obj.setBill6(version);
				obj.setBill7(String.valueOf(exg.getQty() == null ? 0.0 : exg
						.getQty()));
				tempListExg.add(obj);
				// }
				// }
			}
		}
		return tempListExg;
	}

	// /**
	// * 账册料件转单据
	// *
	// * @param isMaterial
	// * @return
	// */
	// public List getRestirictCommodity(boolean isMaterial) {
	// List list = emsEdiTrDao.findEdiMaterielInfo(isMaterial);
	// List tempListExg = new ArrayList();
	// if (isMaterial) {
	// for (int i = 0; i < list.size(); i++) {
	// EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
	// BillTemp obj = new BillTemp();
	// obj.setBill1(String.valueOf(img.getSeqNum()));
	// obj.setBill2(img.getComplex() == null ? null : img.getComplex()
	// .getCode());
	// obj.setBill3(img.getName());
	// obj.setBill4(img.getSpec());
	// obj.setBill5(img.getUnit() == null ? null : img.getUnit()
	// .getName());
	// tempListExg.add(obj);
	// }
	// } else {
	// for (int i = 0; i < list.size(); i++) {
	// EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(i);
	// BillTemp obj = new BillTemp();
	// obj.setBill1(String.valueOf(exg.getSeqNum()));
	// obj.setBill2(exg.getComplex() == null ? null : exg.getComplex()
	// .getCode());
	// obj.setBill3(exg.getName());
	// obj.setBill4(exg.getSpec());
	// obj.setBill5(exg.getUnit() == null ? null : exg.getUnit()
	// .getName());
	// tempListExg.add(obj);
	// }
	// }
	// return tempListExg;
	// }

	/**
	 * 商品禁用修改电子帐册
	 * 
	 * @param seqNum
	 * @param isMateriel
	 * @param isForbid
	 * @param version
	 */
	public void changeEmsEdiForbid(String seqNum, boolean isMateriel,
			boolean isForbid, String version) {
		if (isMateriel) {// 料件
			emsEdiTrDao.updateEmsH2kImgIsForbidBySeqNum(seqNum, isForbid);
			// if (list != null && list.size() > 0) {
			// EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(0);
			// img.setIsForbid(Boolean.valueOf(isForbid));
			// emsEdiTrDao.saveEmsHeadH2kImg(img);
			// }
		} else {// 成品
			emsEdiTrDao
					.updateEmsHeadH2kVersionByExgSeqNum(
							Integer.valueOf(seqNum), Integer.valueOf(version),
							isForbid);
			// if (list != null && list.size() > 0) {
			// EmsHeadH2kVersion ver = (EmsHeadH2kVersion) list.get(0);
			// ver.setIsForbid(Boolean.valueOf(isForbid));
			// emsEdiTrDao.saveEmsHeadH2kVersion(ver);
			// }
		}
	}

	public void deleteCommdityForbidZX(EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList, List<Integer> versionList,
			boolean isMateriel) {
		if (isMateriel) {
			for (int i = 0; i < seqNumList.size(); i++) {
				emsEdiTrDao.deleteAll(emsEdiTrDao.findCommdityForbidByExg(
						MaterielType.MATERIEL, seqNumList.get(i)));
				this.changeEmsEdiForbid(seqNumList.get(i).toString(),
						isMateriel, false, null);
			}
		} else {
			for (int i = 0; i < seqNumList.size(); i++) {
				emsEdiTrDao.deleteAll(emsEdiTrDao.findCommdityForbidBySeqNum(
						MaterielType.FINISHED_PRODUCT, seqNumList.get(i),
						versionList.get(i)));
				this.changeEmsEdiForbid(seqNumList.get(i).toString(),
						isMateriel, false, versionList.get(i).toString());
			}
		}
	}

	/**
	 * 增加禁用商品
	 * 
	 * @param tempList
	 * @param isMateriel
	 * @return
	 */
	public List addCommdityForbid(List tempList, boolean isMateriel) {
		List forbidList = new ArrayList();
		for (int i = 0; i < tempList.size(); i++) {
			CommdityForbid obj = new CommdityForbid();
			BillTemp temp = (BillTemp) tempList.get(i);
			obj.setCompany(CommonUtils.getCompany());
			obj.setSeqNum(temp.getBill1());
			obj.setComplex(temp.getBill2());
			obj.setName(temp.getBill3());
			obj.setSpec(temp.getBill4());
			obj.setUnit(temp.getBill5());
			obj.setVersion(temp.getBill6());
			obj.setForbiddate(dateToStandDate(new Date()));
			obj.setForbiduser(CommonUtils.getRequest().getUser().getUserName());
			if (isMateriel) {
				obj.setType(MaterielType.MATERIEL);
			} else {
				obj.setType(MaterielType.FINISHED_PRODUCT);
			}
			emsEdiTrDao.saveCommdityForbid(obj);
			changeEmsEdiForbid(temp.getBill1(), isMateriel, true,
					temp.getBill6());
			forbidList.add(obj);
		}
		return forbidList;

	}

	/**
	 * 增加禁用商品
	 * 
	 * @param tempList
	 * @param isMateriel
	 * @return
	 */
	public List addCommdityForbidZX(EmsHeadH2k emsHeadH2k,
			List<Integer> seqNumList, List<Integer> versionList,
			boolean isMateriel) {
		List forbidList = new ArrayList();
		if (isMateriel) {
			List<EmsHeadH2kImg> imgList = emsEdiTrDao
					.findEmsHeadH2kImgBySeqNum(emsHeadH2k, seqNumList);

			for (int i = 0; i < imgList.size(); i++) {
				CommdityForbid obj = new CommdityForbid();

				EmsHeadH2kImg temp = imgList.get(i);
				obj.setCompany(CommonUtils.getCompany());
				obj.setSeqNum(temp.getSeqNum().toString());
				obj.setComplex(temp.getComplex().getCode());
				obj.setName(temp.getName());
				obj.setSpec(temp.getSpec());
				obj.setUnit(temp.getUnit().getName());
				obj.setForbiddate(dateToStandDate(new Date()));
				obj.setForbiduser(CommonUtils.getRequest().getUser()
						.getUserName());
				if (isMateriel) {
					obj.setType(MaterielType.MATERIEL);
				} else {
					obj.setType(MaterielType.FINISHED_PRODUCT);
				}

				emsEdiTrDao.saveCommdityForbid(obj);
				changeEmsEdiForbid(obj.getSeqNum(), isMateriel, true, null);
				forbidList.add(obj);
			}
		} else {

			for (int i = 0; i < seqNumList.size(); i++) {
				CommdityForbid obj = new CommdityForbid();

				EmsHeadH2kVersion temp = emsEdiTrDao
						.findEmsHeadH2kVersionBySeqNumAndVersion(emsHeadH2k,
								seqNumList.get(i), versionList.get(i));
				obj.setCompany(CommonUtils.getCompany());
				obj.setSeqNum(temp.getEmsHeadH2kExg().getSeqNum().toString());
				obj.setComplex(temp.getEmsHeadH2kExg().getComplex().getCode());
				obj.setName(temp.getEmsHeadH2kExg().getName());
				obj.setSpec(temp.getEmsHeadH2kExg().getSpec());
				obj.setUnit(temp.getEmsHeadH2kExg().getUnit().getName());
				obj.setVersion(temp.getVersion().toString());
				obj.setForbiddate(dateToStandDate(new Date()));
				obj.setForbiduser(CommonUtils.getRequest().getUser()
						.getUserName());
				if (isMateriel) {
					obj.setType(MaterielType.MATERIEL);
				} else {
					obj.setType(MaterielType.FINISHED_PRODUCT);
				}
				emsEdiTrDao.saveCommdityForbid(obj);
				changeEmsEdiForbid(obj.getSeqNum(), isMateriel, true,
						obj.getVersion());
				forbidList.add(obj);
			}
		}

		return forbidList;

	}

	/**
	 * 删除商品禁用信息
	 * 
	 * @param obj
	 *            商品禁用信息
	 */
	public void deleteCommdityForbid(CommdityForbid obj) {
		try {
			CommdityForbidHis his = new CommdityForbidHis();
			BeanUtils.copyProperties(his, obj);
			his.setId(null);
			his.setCommdityForbidId(obj.getId());
			his.setRevertdate(new Date());
			his.setRevertuser(CommonUtils.getAclUser().getUserName());
			emsEdiTrDao.saveCommdityForbidHis(his);
			emsEdiTrDao.deleteCommdityForbid(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加限制商品
	 * 
	 * @param tempList
	 * @param isMateriel
	 * @return
	 */
	public List addRestirictCommodity(List tempList, boolean isMateriel) {
		List forbidList = new ArrayList();
		for (int i = 0; i < tempList.size(); i++) {
			RestirictCommodity obj = new RestirictCommodity();
			BillTemp temp = (BillTemp) tempList.get(i);
			obj.setCompany(CommonUtils.getCompany());
			obj.setSeqNum(temp.getBill1());
			obj.setComplex(temp.getBill2());
			obj.setName(temp.getBill3());
			obj.setSpec(temp.getBill4());
			obj.setUnit(temp.getBill5());
			obj.setVindicadate(dateToStandDate(new Date()));
			obj.setVindicator(CommonUtils.getRequest().getUser().getUserName());
			obj.setAmount(Double.valueOf(temp.getBill7()));
			if (isMateriel) {
				obj.setTypes(MaterielType.MATERIEL);
			} else {
				obj.setTypes(MaterielType.FINISHED_PRODUCT);
			}
			emsEdiTrDao.saveRestirictCommodity(obj);
			forbidList.add(obj);
		}
		return forbidList;

	}

	/**
	 * 日期转成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateToStandDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
	}

	/**
	 * 格式化
	 * 
	 * @param dm
	 * @param price
	 * @return
	 */
	private String gV(String dm, String price) {
		double d = Double.parseDouble((dm == null || dm.equals("")) ? "0" : dm);
		double p = Double.parseDouble((price == null || price.equals("")) ? "0"
				: price);
		if (d == 0 || p == 0) {
			return "";
		}
		return formatBig(String.valueOf(d * p), 8, false);
	}

	/**
	 * 计算成品单耗
	 * 
	 * @param obj
	 * @param i
	 * @param dm
	 * @param sh
	 * @param price
	 * @param totalPrice
	 * @return
	 */
	public EmsUnitWear getImg(EmsUnitWear obj, int i, String dm, String sh,
			String price, String totalPrice) {
		if (i == 1) {
			obj.setDm11(dm);
			obj.setDm12(sh);
			obj.setDm13(price);
			if (totalPrice == null) {
				obj.setDm14(gV(dm, price));
			} else {
				obj.setDm14(formatBig(totalPrice, 8, false));
			}
		} else if (i == 2) {
			obj.setDm21(dm);
			obj.setDm22(sh);
			obj.setDm23(price);
			if (totalPrice == null) {
				obj.setDm24(gV(dm, price));
			} else {
				obj.setDm24(formatBig(totalPrice, 8, false));
			}
		} else if (i == 3) {
			obj.setDm31(dm);
			obj.setDm32(sh);
			obj.setDm33(price);
			if (totalPrice == null) {
				obj.setDm34(gV(dm, price));
			} else {
				obj.setDm34(formatBig(totalPrice, 8, false));
			}
		} else if (i == 4) {
			obj.setDm41(dm);
			obj.setDm42(sh);
			obj.setDm43(price);
			if (totalPrice == null) {
				obj.setDm44(gV(dm, price));
			} else {
				obj.setDm44(formatBig(totalPrice, 8, false));
			}
		} else if (i == 5) {
			obj.setDm51(dm);
			obj.setDm52(sh);
			obj.setDm53(price);
			if (totalPrice == null) {
				obj.setDm54(gV(dm, price));
			} else {
				obj.setDm54(formatBig(totalPrice, 8, false));
			}
		} else if (i == 6) {
			obj.setDm61(dm);
			obj.setDm62(sh);
			obj.setDm63(price);
			if (totalPrice == null) {
				obj.setDm64(gV(dm, price));
			} else {
				obj.setDm64(formatBig(totalPrice, 8, false));
			}
		} else if (i == 7) {
			obj.setDm71(dm);
			obj.setDm72(sh);
			obj.setDm73(price);
			if (totalPrice == null) {
				obj.setDm74(gV(dm, price));
			} else {
				obj.setDm74(formatBig(totalPrice, 8, false));
			}
		} else if (i == 8) {
			obj.setDm81(dm);
			obj.setDm82(sh);
			obj.setDm83(price);
			if (totalPrice == null) {
				obj.setDm84(gV(dm, price));
			} else {
				obj.setDm84(formatBig(totalPrice, 8, false));
			}
		} else if (i == 9) {
			obj.setDm91(dm);
			obj.setDm92(sh);
			obj.setDm93(price);
			if (totalPrice == null) {
				obj.setDm94(gV(dm, price));
			} else {
				obj.setDm94(formatBig(totalPrice, 8, false));
			}
		} else if (i == 10) {
			obj.setDm101(dm);
			obj.setDm102(sh);
			obj.setDm103(price);
			if (totalPrice == null) {
				obj.setDm104(gV(dm, price));
			} else {
				obj.setDm104(formatBig(totalPrice, 8, false));
			}
		}
		return obj;
	}

	/**
	 * 成品单耗设置
	 * 
	 * @param obj
	 * @param i
	 * @return
	 */
	public EmsUnitWear getRow3(EmsUnitWear obj, int i) {
		if (i == 1) {
			obj.setSeqNum("序号");
			obj.setName("材料名称/规格");
			obj.setDm11("单耗");
			obj.setDm12("损耗%");
			obj.setDm13("单价");
			obj.setDm14("总价");
		} else if (i == 2) {
			obj.setDm21("单耗");
			obj.setDm22("损耗%");
			obj.setDm23("单价");
			obj.setDm24("总价");
		} else if (i == 3) {
			obj.setDm31("单耗");
			obj.setDm32("损耗%");
			obj.setDm33("单价");
			obj.setDm34("总价");
		} else if (i == 4) {
			obj.setDm41("单耗");
			obj.setDm42("损耗%");
			obj.setDm43("单价");
			obj.setDm44("总价");
		} else if (i == 5) {
			obj.setDm51("单耗");
			obj.setDm52("损耗%");
			obj.setDm53("单价");
			obj.setDm54("总价");
		} else if (i == 6) {
			obj.setDm61("单耗");
			obj.setDm62("损耗%");
			obj.setDm63("单价");
			obj.setDm64("总价");
		} else if (i == 7) {
			obj.setDm71("单耗");
			obj.setDm72("损耗%");
			obj.setDm73("单价");
			obj.setDm74("总价");
		} else if (i == 8) {
			obj.setDm81("单耗");
			obj.setDm82("损耗%");
			obj.setDm83("单价");
			obj.setDm84("总价");
		} else if (i == 9) {
			obj.setDm91("单耗");
			obj.setDm92("损耗%");
			obj.setDm93("单价");
			obj.setDm94("总价");
		} else if (i == 10) {
			obj.setDm101("单耗");
			obj.setDm102("损耗%");
			obj.setDm103("单价");
			obj.setDm104("总价");
		}
		return obj;
	}

	/**
	 * 成品单耗设置
	 * 
	 * @param obj
	 * @param i
	 * @return
	 */
	public EmsUnitWear getRow2(EmsUnitWear obj, int i, String name,
			String version) {
		if (i == 1) {
			obj.setName("版本号");
			obj.setDm11(name);
			obj.setDm12(version);
		} else if (i == 2) {
			obj.setDm21(name);
			obj.setDm22(version);
		} else if (i == 3) {
			obj.setDm31(name);
			obj.setDm32(version);
		} else if (i == 4) {
			obj.setDm41(name);
			obj.setDm42(version);
		} else if (i == 5) {
			obj.setDm51(name);
			obj.setDm52(version);
		} else if (i == 6) {
			obj.setDm61(name);
			obj.setDm62(version);
		} else if (i == 7) {
			obj.setDm71(name);
			obj.setDm72(version);
		} else if (i == 8) {
			obj.setDm81(name);
			obj.setDm82(version);
		} else if (i == 9) {
			obj.setDm91(name);
			obj.setDm92(version);
		} else if (i == 10) {
			obj.setDm101(name);
			obj.setDm102(version);
		}
		return obj;
	}

	/**
	 * 成品单耗设置
	 * 
	 * @param obj
	 * @param i
	 * @return
	 */
	public EmsUnitWear getRow1(EmsUnitWear obj, int i, String seqNum) {
		if (i == 1) {
			obj.setName("成品序号");
			obj.setDm12(seqNum);
		} else if (i == 2) {
			obj.setDm22(seqNum);
		} else if (i == 3) {
			obj.setDm32(seqNum);
		} else if (i == 4) {
			obj.setDm42(seqNum);
		} else if (i == 5) {
			obj.setDm52(seqNum);
		} else if (i == 6) {
			obj.setDm62(seqNum);
		} else if (i == 7) {
			obj.setDm72(seqNum);
		} else if (i == 8) {
			obj.setDm82(seqNum);
		} else if (i == 9) {
			obj.setDm92(seqNum);
		} else if (i == 10) {
			obj.setDm102(seqNum);
		}
		return obj;
	}

	/**
	 * 格式化
	 * 
	 * @param amount
	 * @param bigD
	 * @param isZero
	 * @return
	 */
	public String formatBig(String amount, int bigD, boolean isZero) {
		if (amount == null || amount.equals("")) {
			amount = "0";
		}
		BigDecimal bd = new BigDecimal(amount);
		String amountStr = bd.setScale(bigD, BigDecimal.ROUND_HALF_UP)
				.toString();
		if (isZero) {
			return amountStr;
		}
		for (int i = amountStr.length(); i > 0; i--) {
			if (amountStr.substring(i - 1, i).equals("0")) {
				amountStr = amountStr.substring(0, i - 1);
			} else if (amountStr.substring(i - 1, i).equals(".")) {
				amountStr = amountStr.substring(0, i - 1);
				break;
			} else {
				break;
			}
		}
		return amountStr;
	}

	/**
	 * 简转繁
	 * 
	 * @param list
	 * @return
	 */
	private Hashtable injTofHsTable(List list) {
		Hashtable gbHash = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			Gbtobig gb = (Gbtobig) list.get(i);
			gbHash.put(gb.getName(), gb.getBigname());
		}

		return gbHash;
	}

	/**
	 * 繁转简
	 * 
	 * @param list
	 * @return
	 */
	private Hashtable infTojHsTable(List list) {
		Hashtable gbHash = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			Gbtobig gb = (Gbtobig) list.get(i);
			gbHash.put(gb.getBigname(), gb.getName());
		}
		return gbHash;
	}

	/**
	 * 格式化
	 * 
	 * @param s
	 * @param gbHash
	 * @return
	 */
	private String changeStr(String s, Hashtable gbHash) {
		String yy = "";
		char[] xxx = s.toCharArray();
		for (int i = 0; i < xxx.length; i++) {
			String z = String.valueOf(xxx[i]);
			if (String.valueOf(xxx[i]).getBytes().length == 2) {
				if (gbHash.get(String.valueOf(xxx[i])) != null) {
					z = (String) gbHash.get(String.valueOf(xxx[i]));
				}
			}
			yy = yy + z;
		}
		return yy;
	}

	/**
	 * 下载单耗表
	 * 
	 * @param bSeqNum
	 * @param eSeqNum
	 * @param head
	 * @param isftoj
	 * @param list
	 * @return
	 */
	public List downLoadUnitWear(String bSeqNum, String eSeqNum,
			EmsHeadH2k head, boolean isftoj, List list) {
		int cpNum = 80;// 一个文件显示的成品个数
		Hashtable gbHash = infTojHsTable(list);
		// 初始化字符转换
		if (isftoj) {
			infTojHsTable(list);
		} else {
			injTofHsTable(list);
		}
		// 加载料件列表
		Hashtable hsEmsImg = initEmsHeadH2kImg(head);
		// 分段查询
		List listexg = emsEdiTrDao.findEmsHeadH2kExgInBegSeqNumEndSeqNum(head,
				Integer.valueOf(bSeqNum), Integer.valueOf(eSeqNum)); // 当前电子帐册成品
		int j = 0;
		int bseqNum = Integer.valueOf(bSeqNum);
		int bverNo = 0;
		List ls = new ArrayList();
		for (int i = 0; i < listexg.size(); i++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) listexg.get(i);
			List vList = emsEdiTrDao.findEmsVersion(exg);
			for (int k = 0; k < vList.size(); k++) {
				EmsHeadH2kVersion version = (EmsHeadH2kVersion) vList.get(k);
				j++;
				if (j == cpNum) {
					List listSeq = new ArrayList();
					listSeq.add(bseqNum);
					listSeq.add(exg.getSeqNum());
					listSeq.add(bverNo);
					listSeq.add(version.getVersion());
					ls.add(listSeq);
					j = 0;
					bseqNum = exg.getSeqNum();
					bverNo = version.getVersion() + 1;
				} else if (j < cpNum && i == (listexg.size() - 1)
						&& k == (vList.size() - 1)) {
					List listSeq = new ArrayList();
					listSeq.add(bseqNum);
					listSeq.add(exg.getSeqNum());
					listSeq.add(bverNo);
					listSeq.add(version.getVersion());
					ls.add(listSeq);
				}
			}
		}
		List l = new ArrayList();
		for (int k = 0; k < ls.size(); k++) {
			List lis = (List) ls.get(k);
			Integer bs = (Integer) lis.get(0);
			Integer es = (Integer) lis.get(1);
			Integer bv = (Integer) lis.get(2);
			Integer ev = (Integer) lis.get(3);
			List l1 = new ArrayList();
			l1 = downLoadUnitWearGetList(bs, es, bv, ev, head, gbHash, hsEmsImg);
			l.add(l1);
		}
		return l;
	}

	/**
	 * 下载单耗表
	 * 
	 * @param bSeqNum
	 * @param eSeqNum
	 * @param bverNo
	 * @param everNo
	 * @param head
	 * @param gbHash
	 * @param hsEmsImg
	 * @return
	 */
	public List downLoadUnitWearGetList(Integer bSeqNum, Integer eSeqNum,
			Integer bverNo, Integer everNo, EmsHeadH2k head, Hashtable gbHash,
			Hashtable hsEmsImg) {
		Hashtable hs = new Hashtable();
		TreeMap ts = new TreeMap();
		List totalList = new ArrayList();
		List listexg = emsEdiTrDao.findEmsHeadH2kExgInBegSeqNumEndSeqNum(head,
				bSeqNum, eSeqNum); // 当前电子帐册成品
		List ls0 = new ArrayList();
		List ls1 = new ArrayList();
		List ls2 = new ArrayList();
		List ls3 = new ArrayList();
		ls0.add(changeStr("标题：", gbHash));
		ls0.add(changeStr("成品对应料件单耗表", gbHash));
		ls1.add("");
		ls1.add("");
		ls2.add("");
		ls2.add("");
		ls3.add(changeStr("序号", gbHash));
		ls3.add(changeStr("材料名称/规格", gbHash));
		int cpNum = 0;
		for (int i = 0; i < listexg.size(); i++) {// 3
			EmsHeadH2kExg exg = (EmsHeadH2kExg) listexg.get(i);
			Integer bversionNo = null;
			Integer eversionNo = null;
			if (i == 0) {
				bversionNo = bverNo;
			} else {
				bversionNo = null;
			}
			if (i == (listexg.size() - 1)) {
				eversionNo = everNo;
			} else {
				eversionNo = null;
			}
			String seqNum = String.valueOf(exg.getSeqNum());// 成品序号
			List vList = emsEdiTrDao
					.findEmsVersion(exg, bversionNo, eversionNo);
			for (int k = 0; k < vList.size(); k++) {
				EmsHeadH2kVersion version = (EmsHeadH2kVersion) vList.get(k);
				String vNo = String.valueOf(version.getVersion()); // 版本号
				// --------------------------
				ls1.add("");
				ls1.add(changeStr("序号：", gbHash) + seqNum);
				ls1.add("");
				// --------------------------
				ls2.add(changeStr((exg.getName() == null ? "" : exg.getName())
						+ "/" + (exg.getSpec() == null ? "" : exg.getSpec()),
						gbHash));
				ls2.add(changeStr("版本：", gbHash) + vNo);
				ls2.add("");
				// --------------------------
				ls3.add(changeStr("单耗", gbHash));
				ls3.add(changeStr("损耗%", gbHash));
				ls3.add(changeStr("单价", gbHash));
				cpNum++;
				List imgList = emsEdiTrDao.findEmsHeadH2kBom(version);
				for (int h = 0; h < imgList.size(); h++) {
					EmsHeadH2kBom bom = (EmsHeadH2kBom) imgList.get(h);
					Integer imgSeqNum = bom.getSeqNum();
					String dm = bom.getUnitWear() == null ? "0.0" : forNumq(
							bom.getUnitWear(), 9); // 单耗
					String sh = bom.getWear() == null ? "0.0" : forNumq(
							bom.getWear(), 7); // 损耗
					EmsHeadH2kImg bomImg = getEmsHeadH2kImg(bom.getSeqNum(),
							hsEmsImg);
					String price = bomImg.getFactoryPrice() == null ? "0.0"
							: forNumq(bomImg.getFactoryPrice(), 5);// 单价
					List lsn = null;
					if (hs.get(imgSeqNum) == null) {
						lsn = new ArrayList();
						lsn.add(String.valueOf(imgSeqNum));
						lsn.add(changeStr(
								(bomImg.getName() == null ? "" : bomImg
										.getName())
										+ "/"
										+ (bomImg.getSpec() == null ? ""
												: bomImg.getSpec()), gbHash));
						hs.put(imgSeqNum, lsn);
						ts.put(imgSeqNum, imgSeqNum);
						int yy = ((cpNum - 1) * 3);
						for (int y = 0; y < yy; y++) {
							lsn.add("");
						}
					} else {
						lsn = (List) hs.get(imgSeqNum);
						int gg = ((((cpNum - 1) * 3) + 2) - lsn.size());
						for (int g = 0; g < gg; g++) {
							lsn.add("");
						}
					}

					System.out.println("单耗=" + dm + " 损耗=" + sh + " 单价="
							+ price);
					lsn.add(dm);
					lsn.add(sh);
					lsn.add(price);
				}
			}
		}
		totalList.add(ls0);
		totalList.add(ls1);
		totalList.add(ls2);
		totalList.add(ls3);

		Set st = ts.keySet();
		for (Iterator i = st.iterator(); i.hasNext();) {
			totalList.add(hs.get(ts.get(i.next())));
		}
		return totalList;
	}

	/**
	 * 查询得到单耗表
	 * 
	 * @param bSeqNum
	 * @param eSeqNum
	 * @param pageNum
	 * @param head
	 * @return
	 */
	public List getEmsUnitWearList(String bSeqNum, String eSeqNum, int pageNum,
			EmsHeadH2k head) {

		// 初始化 所有料件
		Hashtable hsEmsImg = initEmsHeadH2kImg(head);

		Hashtable hs = new Hashtable();

		Hashtable hsImg = new Hashtable();

		List hsImgList = new ArrayList();

		String incrementRate = emsEdiTrDao
				.getBpara(BcusParameter.INCREMENT_RATE);

		Double increRate = Double.valueOf(1);

		if (incrementRate != null && !incrementRate.equals("")) {

			increRate = Double.valueOf(incrementRate);

		}

		String emsbomprice = emsEdiTrDao.getBpara(BcusParameter.EMS_BOM_PRICE);

		Integer bomprice = Integer.valueOf(2);

		if (emsbomprice != null && !emsbomprice.equals("")) {

			try {

				bomprice = Integer.valueOf(emsbomprice);

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		int total = 0;

		// 当前电子帐册成品
		List list = emsEdiTrDao.findEmsHeadH2kExgInBegSeqNumEndSeqNum(head,
				Integer.valueOf(bSeqNum), Integer.valueOf(eSeqNum));

		for (int i = 0; i < list.size(); i++) {

			EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(i);

			String seqNum = String.valueOf(exg.getSeqNum());// 成品序号

			// 成品名称/归并
			String exgName = ((exg.getName() == null) ? "" : exg.getName())
					+ "/" + ((exg.getSpec() == null) ? "" : exg.getSpec());

			if (total >= (pageNum * 10)) {

				break;

			}

			List vList = emsEdiTrDao.findEmsVersion(exg);

			for (int k = 0; k < vList.size(); k++) {

				total = total + 1;

				if (total <= (pageNum - 1) * 10) {

					continue;

				}

				if (total > (pageNum * 10)) {

					break;

				}

				int seq = total - ((pageNum - 1) * 10);

				EmsHeadH2kVersion version = (EmsHeadH2kVersion) vList.get(k);

				String vNo = String.valueOf(version.getVersion()); // 版本号

				if (hs.get(1) != null) {

					EmsUnitWear row1 = (EmsUnitWear) hs.get(1);

					row1 = getRow1(row1, seq, "序号：" + seqNum);

				} else {

					EmsUnitWear row1 = new EmsUnitWear();

					row1 = getRow1(row1, seq, "序号：" + seqNum);

					hs.put(1, row1);

				}

				if (hs.get(2) != null) {

					EmsUnitWear row2 = (EmsUnitWear) hs.get(2);

					row2 = getRow2(row2, seq, exgName, "版本：" + vNo);

				} else {

					EmsUnitWear row2 = new EmsUnitWear();

					row2 = getRow2(row2, seq, exgName, "版本：" + vNo);

					hs.put(2, row2);

				}

				if (hs.get(3) != null) {

					EmsUnitWear row3 = (EmsUnitWear) hs.get(3);

					row3 = getRow3(row3, seq);

				} else {

					EmsUnitWear row3 = new EmsUnitWear();

					row3 = getRow3(row3, seq);

					hs.put(3, row3);
				}

				List imgList = emsEdiTrDao.findEmsHeadH2kBom(version);

				int imgListsize = imgList.size();

				int kk = imgListsize;

				Double totalNum = 0.0;

				for (int j = 0; j < imgListsize; j++) {

					String imgNo = "";

					String dm = "";

					String sh = "";

					String price = "";

					Integer bomSeqNum = 0;

					String name = "";

					String totalPrice = null;

					if (j == kk) {

						dm = "";

						sh = "";

						price = "";

						bomSeqNum = 99998;

						name = "总价汇总";

						totalPrice = formatBig(String.valueOf(totalNum), 8,
								false);

					} else if (j > kk) {

						dm = "";

						sh = "";

						price = "";

						bomSeqNum = 99999;

						name = "成品单价";

						totalPrice = formatBig(
								String.valueOf(totalNum / increRate), bomprice,
								false);

					} else {

						EmsHeadH2kBom bom = (EmsHeadH2kBom) imgList.get(j);

						imgNo = String.valueOf(bom.getSeqNum());

						dm = bom.getUnitWear() == null ? "" : forNumq(
								bom.getUnitWear(), 9);

						sh = bom.getWear() == null ? "" : forNumq(
								bom.getWear(), 7);

						EmsHeadH2kImg bomImg = getEmsHeadH2kImg(
								bom.getSeqNum(), hsEmsImg);

						price = bomImg.getFactoryPrice() == null ? ""
								: forNumq(bomImg.getFactoryPrice(), 2);

						bomSeqNum = bom.getSeqNum();

						name = (bomImg.getName() == null ? "" : bomImg
								.getName())
								+ "/"
								+ (bomImg.getSpec() == null ? "" : bomImg
										.getSpec());

						totalPrice = null;
					}

					EmsUnitWear img = null;

					if (hsImg.get(bomSeqNum) != null) {

						img = (EmsUnitWear) hsImg.get(bomSeqNum);

						img = getImg(img, seq, dm, sh, price, totalPrice);

					} else {

						img = new EmsUnitWear();

						img = getImg(img, seq, dm, sh, price, totalPrice);

						img.setSeqNum(imgNo);

						img.setName(name);

						hsImg.put(bomSeqNum, img);

						hsImgList.add(bomSeqNum);

					}

					totalNum = totalNum
							+ Double.valueOf(gV(dm, price).equals("") ? "0.0"
									: gV(dm, price));

					if (j == imgListsize - 1
							&& (imgListsize == kk || imgListsize == (kk + 1))) {

						imgListsize++;

					}
				}
			}
		}

		List returnList = new ArrayList();

		// 第一行
		returnList.add(hs.get(1));

		returnList.add(hs.get(2));

		returnList.add(hs.get(3));

		// List listVector = new Vector(hsImg.values());

		Collections.sort(hsImgList);

		for (int i = 0; i < hsImgList.size(); i++) {

			if (hsImg.get((Integer) hsImgList.get(i)) != null) {

				returnList.add(hsImg.get((Integer) hsImgList.get(i)));

			}
		}

		/*
		 * for (int i=listVector.size()-1;i>=0;i--){
		 * returnList.add(listVector.get(i)); }
		 */

		return returnList;
	}

	/**
	 * 格式化
	 * 
	 * @param d
	 * @param dig
	 * @return
	 */
	private String forNumq(Double d, int dig) {
		if (d == null || d.equals(Double.valueOf(0))) {
			return "";
		}
		double shuliang = d.doubleValue();
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(dig, BigDecimal.ROUND_HALF_UP)
				.toString();
		for (int i = totalshuliang.length(); i > 0; i--) {
			if (totalshuliang.substring(i - 1, i).equals("0")) {
				totalshuliang = totalshuliang.substring(0, i - 1);
			} else if (totalshuliang.substring(i - 1, i).equals(".")) {
				totalshuliang = totalshuliang.substring(0, i - 1);
				break;
			} else {
				break;
			}
		}
		return totalshuliang;
	}

	/**
	 * 格式化
	 * 
	 * @param d
	 * @return
	 */
	public static String forNum(Double d) {
		double unroundedValue = d.doubleValue();
		int digits = 6;
		int x = 1;
		for (int i = 0; i < digits; i++) {
			x = x * 10;
		}
		double intPortion = Math.floor(unroundedValue);
		double unroundedDecimalPortion = (unroundedValue - intPortion);
		double roundedDecimalPortion = Math.round(unroundedDecimalPortion * x);
		roundedDecimalPortion = roundedDecimalPortion / x;
		double total = intPortion + roundedDecimalPortion;
		Double y = new Double(total);
		return String.valueOf(y);
	}

	//
	// // 归并关系新增BOM来自产品结构BOM
	// public List addBomFromCustomsBom(EmsEdiMergerExgBefore exg,
	// EmsEdiMergerVersion ediMergerVersion) {
	// initImgBefore(exg.getEmsEdiMergerExgAfter().getEmsEdiMergerHead());
	// String ptNo = exg.getPtNo();// 成品料号
	// MaterialBomMaster master = emsEdiTrDao
	// .findMaterialBomMasterByPtno(ptNo);
	// if (master != null) {
	// List vList = emsEdiTrDao.findVersionByBomMaster(master);
	// List bomList = new ArrayList();
	// for (int i = 0; i < vList.size(); i++) {
	// MaterialBomVersion version = (MaterialBomVersion) vList.get(i);
	// if (ediMergerVersion.getVersion() == null
	// || !ediMergerVersion.getVersion().equals(
	// version.getVersion())) {
	// continue;
	// }
	// List bList = emsEdiTrDao.findDetailByVersion(version);
	// for (int j = 0; j < bList.size(); j++) {
	// EmsEdiMergerBomFrom bom = new EmsEdiMergerBomFrom();
	// MaterialBomDetail obj = (MaterialBomDetail) bList.get(j);
	// EmsEdiMergerExgBom exgbom = new EmsEdiMergerExgBom();
	// Materiel mt = obj.getMateriel();
	// String ljNo = mt.getPtNo();
	// EmsEdiMergerImgBefore imgBefore = getImgBefore(ljNo);
	// if (imgBefore != null) {
	// bom.setIsMerger("备案");// 是否已经备案
	// exgbom.setComplex(imgBefore.getComplex());
	// exgbom.setName(imgBefore.getName());
	// exgbom.setSpec(imgBefore.getSpec());
	// exgbom.setUnit(imgBefore.getUnit());
	// } else {
	// bom.setIsMerger("未备案");
	// exgbom.setName(mt.getFactoryName());
	// exgbom.setSpec(mt.getFactorySpec());
	// }
	// exgbom.setPtNo(mt.getPtNo());
	// exgbom.setCompany(CommonUtils.getCompany());
	// Double unitConvert = mt.getUnitConvert();
	// if (unitConvert == null
	// || Double.valueOf(0.0).equals(unitConvert)) {
	// exgbom.setUnitWaste(obj.getUnitWaste());
	// exgbom.setWaste(obj.getWaste() == null ? Double
	// .valueOf(0) : (obj.getWaste() * 100));
	// } else {
	// exgbom.setUnitWaste(obj.getUnitWaste() == null ? Double
	// .valueOf(0)
	// : (obj.getUnitWaste() * unitConvert));// 单耗
	// exgbom.setWaste((obj.getWaste() == null ? Double
	// .valueOf(0) : (obj.getWaste() * 100))
	// * unitConvert);// 损耗
	// }
	// bom.setBom(exgbom);
	// bom.setVersion(version.getVersion());// 版本号
	// bomList.add(bom);
	// }
	// }
	// return bomList;
	// }
	// return null;
	// }
	/**
	 * 归并关系新增BOM来自产品结构BOM
	 * 
	 * @param request
	 * @param exg
	 * @return
	 */
	public List addBomFromCustomsBom(EmsEdiMergerExgBefore exg,
			Integer versionId) {
		Hashtable hsImg = initImgBefore(exg.getEmsEdiMergerExgAfter()
				.getEmsEdiMergerHead());
		String ptNo = exg.getPtNo();// 成品料号
		MaterialBomMaster master = emsEdiTrDao
				.findMaterialBomMasterByPtno(ptNo);
		if (master != null) {
			Materiel mtExg = master.getMateriel();
			// 成品中的折算系数
			Double unitConvertExg = mtExg.getUnitConvert();
			if (unitConvertExg == null || unitConvertExg == 0) {
				throw new RuntimeException("成品料号:" + ptNo + " 折算系数为:"
						+ unitConvertExg);
			}
			List vList = emsEdiTrDao.findVersionByBomMaster(master, versionId);
			List bomList = new ArrayList();
			for (int i = 0; i < vList.size(); i++) {
				MaterialBomVersion version = (MaterialBomVersion) vList.get(i);
				List bList = emsEdiTrDao.findDetailByVersion(version);
				for (int j = 0; j < bList.size(); j++) {
					EmsEdiMergerBomFrom bom = new EmsEdiMergerBomFrom();
					MaterialBomDetail obj = (MaterialBomDetail) bList.get(j);
					EmsEdiMergerExgBom exgbom = new EmsEdiMergerExgBom();
					Materiel mt = obj.getMateriel();
					String ljNo = mt.getPtNo();
					EmsEdiMergerImgBefore imgBefore = getImgBefore(ljNo, hsImg);
					if (imgBefore != null) {
						bom.setIsMerger("备案");// 是否已经备案
						exgbom.setComplex(imgBefore.getComplex());
						exgbom.setName(imgBefore.getName());
						exgbom.setSpec(imgBefore.getSpec());
						exgbom.setUnit(imgBefore.getUnit());
					} else {
						bom.setIsMerger("未备案");
						exgbom.setName(mt.getFactoryName());
						exgbom.setSpec(mt.getFactorySpec());
					}
					bom.setMaterialSource(mt.getMaterialSource());
					exgbom.setPtNo(mt.getPtNo());
					exgbom.setCompany(CommonUtils.getCompany());
					// 物料中的折算系数
					Double unitConvert = mt.getUnitConvert();
					if (unitConvert == null
							|| Double.valueOf(0.0).equals(unitConvert)) {
						exgbom.setUnitWaste(obj.getUnitWaste() / unitConvertExg);
						exgbom.setWaste(obj.getWaste() == null ? Double
								.valueOf(0) : (obj.getWaste() * 100));
					} else {
						exgbom.setUnitWaste(obj.getUnitWaste() == null ? Double
								.valueOf(0)
								: (obj.getUnitWaste() * unitConvert / unitConvertExg));// 单耗
						exgbom.setWaste((obj.getWaste() == null ? Double
								.valueOf(0) : (obj.getWaste() * 100)));// 损耗

					}
					exgbom.setUnitWaste(CommonUtils.getDoubleByDigit(
							exgbom.getUnitWaste(), 9));
					exgbom.setWaste(CommonUtils.getDoubleByDigit(
							exgbom.getWaste(), 5));// 剩以100以后，保留五位
					bom.setBom(exgbom);
					bom.setVersion(version.getVersion());// 版本号
					bom.setUnitWeight(version.getFactUnitWeight());// 单重
					// System.out.println("bom:2109:"+version.getFactUnitWeight());
					bomList.add(bom);
				}
			}
			return bomList;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public void importEmsHead2kFromMerger(EmsHeadH2k emsHeadH2k, boolean isImg,
			Integer seqNumMin, Integer seqNumMax, Date beginDate, Date endDate,
			String states, String emsFrom) {

		// 单耗控制小数位
		int unitweardigits = 9;
		String unitweardig = this.emsEdiTrDao
				.getBpara(BcusParameter.EMSBOM_UNITWEAR_DIGITS);
		if (unitweardig != null && !"".equals(unitweardig)) {
			unitweardigits = Integer.parseInt(unitweardig);
		}

		// 损耗控制小数位
		int weardigits = 5;
		String weardig = this.emsEdiTrDao
				.getBpara(BcusParameter.EMSBOM_WEAR_DIGITS);
		if (weardig != null && !"".equals(weardig)) {
			weardigits = Integer.parseInt(weardig);
		}

		// 应该查询的电子账册状态
		String declareState = null;
		if (emsFrom != null && emsFrom.equals("2")) { // 电子账册bom数据来自归并关系备案转换（不检查状态）
			declareState = DeclareState.APPLY_POR;
		} else {// 电子账册bom数据来自归并关系备案正在执行bom转换（检查调用正在执行的状态）
			declareState = DeclareState.PROCESS_EXE;
		}

		// 电子帐册归并关系表头
		EmsEdiMergerHead merHead = emsEdiTrDao
				.findEmsEdiMergerHead(declareState);

		long begin = System.currentTimeMillis();
		List megerBomList = this.emsEdiTrDao.findEmsHeadH2kImgExgBySeqNum(
				emsHeadH2k, seqNumMin, seqNumMax, beginDate, endDate, states);
		long end = System.currentTimeMillis();
		System.out.println("findEmsHeadH2kImgExgBySeqNum time:" + (end - begin)
				/ 1000.0);
		System.out.println("数量1：" + megerBomList.size());

		begin = end;

		// 没找到符合条件的bom
		if (megerBomList == null || megerBomList.size() < 1) {
			StringBuilder error = new StringBuilder("没找到符合条件的bom！查询条件：");
			error.append("\n开始日期："
					+ (beginDate == null ? "" : beginDate.toLocaleString()));
			error.append("\n结束日期："
					+ (endDate == null ? "" : endDate.toLocaleString()));
			error.append("\n开始序号：" + seqNumMin);
			error.append("\n结束序号：" + seqNumMax);
			error.append("\n状态：" + states);

			throw new RuntimeException(error.toString());
		}

		// 根据 查询有变更的归并bom（需要复制补充的归并bom） 组装生成 电子账册bom
		Object[] datas = buildEmsH2kData(merHead, emsHeadH2k, megerBomList,
				unitweardigits, weardigits);
		end = System.currentTimeMillis();
		System.out.println("buildEmsH2kData time:" + (end - begin) / 1000.0);

		begin = System.currentTimeMillis();
		saveEmsHead2kFromMergerData(datas);
		end = System.currentTimeMillis();
		System.out.println("saveEmsHead2kFromMergerData time:" + (end - begin)
				/ 1000.0);

		// if(1==1) {
		// throw new RuntimeException("认为错误：");
		// }
	}

	private void saveEmsHead2kFromMergerData(Object[] datas) {
		List<EmsHeadH2kVersion> versionList = (List<EmsHeadH2kVersion>) datas[0];
		List<EmsHeadH2kImg> imgList = (List<EmsHeadH2kImg>) datas[1];
		List<EmsHeadH2kBom> emsH2kBomList = (List<EmsHeadH2kBom>) datas[2];

		// 版本列表
		emsEdiTrDao.batchSaveOrUpdate(versionList);
		// 料件列表
		emsEdiTrDao.batchSaveOrUpdate(imgList);
		// bom记录
		emsEdiTrDao.batchSaveOrUpdate(emsH2kBomList);
	}

	/**
	 * 归并关系新增BOM来自产品结构BOM
	 */
	public void addBomFromSelectBom(EmsEdiMergerExgBefore exg,
			EmsEdiMergerVersion ediMergerVersion) {
		String isSendSign = emsEdiTrDao.getBpara(BcusParameter.EmsSEND_Sign);
		String isBomMaterilSoure = emsEdiTrDao
				.getBpara(BcusParameter.EMS_BOM_MaterilSoure);
		EmsEdiMergerHead head = exg.getEmsEdiMergerExgAfter()
				.getEmsEdiMergerHead();
		List bomList = addBomFromCustomsBom(exg, ediMergerVersion.getVersion());
		for (int j = 0; j < bomList.size(); j++) {
			EmsEdiMergerBomFrom bom = (EmsEdiMergerBomFrom) bomList.get(j);
			if (bom.getIsMerger().equals("备案")) {
				if (ediMergerVersion.getVersion() == null
						|| !ediMergerVersion.getVersion().equals(
								bom.getVersion())) {
					continue;
				}
				if ((bom.getMaterialSource() != null && "国内购买".equals(bom
						.getMaterialSource()))
						&& (isBomMaterilSoure == null || "0"
								.equals(isBomMaterilSoure))) {
					continue;
				}
				Integer version = bom.getVersion();
				EmsEdiMergerExgBom e = bom.getBom();
				List versionList = emsEdiTrDao.findExistVersion(exg,
						String.valueOf(version));
				EmsEdiMergerVersion v = null;
				if (versionList != null && versionList.size() > 0) { // 存在版本
					v = (EmsEdiMergerVersion) versionList.get(0); // 取得版本号
					v.setUnitWeight(bom.getUnitWeight());
					// System.out.println("v:2209:"+bom.getUnitWeight());
					EmsEdiMergerExgBom m = null;
					List b = emsEdiTrDao.findEmsEdiMergerBom(v, e.getPtNo()); // 判断是否存在该料件
					if (b != null && b.size() > 0) {
						m = (EmsEdiMergerExgBom) b.get(0);
					}
					if (exg.getEmsEdiMergerExgAfter().getEmsEdiMergerHead()
							.getDeclareType().equals("1")) {// 备案状态
						if (m != null) {// 存在该料件
							if (!m.getUnitWaste().equals(e.getUnitWaste())
									|| !m.getWaste().equals(e.getWaste())) {
								m.setUnitWaste(e.getUnitWaste());
								m.setWaste(e.getWaste());
								m.setModifyTimes(head.getModifyTimes());// 变更次数
								emsEdiTrDao.saveEmsEdiMergerExgBom(m);// 修改原来料件
							}
						} else {// 新增料件
							e.setEmsEdiMergerVersion(v);
							e.setModifyMark(ModifyMarkState.ADDED);
							if (isSendSign != null && "1".equals(isSendSign)) {
								e.setSendState(Integer
										.valueOf(SendState.WAIT_SEND));
							}
							e.setChangeDate(new Date());
							e.setModifyTimes(head.getModifyTimes());// 变更次数
							e.setMachinKind("2"); // 加工性质
							emsEdiTrDao.saveEmsEdiMergerExgBom(e);
						}
					} else { // 变更
						if (m != null) {// 存在该料件
							if (!m.getUnitWaste().equals(e.getUnitWaste())
									|| !m.getWaste().equals(e.getWaste())) {
								/*
								 * m.setUnitWaste(e.getUnitWaste());
								 * m.setWaste(e.getWaste()); if
								 * (m.getModifyMark(
								 * ).equals(ModifyMarkState.UNCHANGE)){
								 * m.setModifyMark
								 * (ModifyMarkState.MODIFIED);//已修改 }
								 * emsEdiTrDao.saveEmsEdiMergerExgBom(m);
								 */
							}
						} else {
							e.setEmsEdiMergerVersion(v);
							e.setModifyMark(ModifyMarkState.ADDED);// 新增
							if (isSendSign != null && "1".equals(isSendSign)) {
								e.setSendState(Integer
										.valueOf(SendState.WAIT_SEND));
							}
							e.setModifyTimes(head.getModifyTimes());// 变更次数
							e.setMachinKind("2"); // 加工性质
							e.setChangeDate(new Date());
							emsEdiTrDao.saveEmsEdiMergerExgBom(e);
						}
					}
				} else { // 不存在版本
					v = new EmsEdiMergerVersion();
					v.setEmsEdiMergerBefore(exg);
					v.setVersion(version);
					v.setUnitWeight(bom.getUnitWeight());
					v.setName("版本：" + String.valueOf(version));
					// System.out.println("v:2209:"+bom.getUnitWeight());
					v.setCompany(CommonUtils.getCompany());
					emsEdiTrDao.saveEmsEdiMergerVersion(v);
					e.setEmsEdiMergerVersion(v);
					e.setModifyMark(ModifyMarkState.ADDED);
					if (isSendSign != null && "1".equals(isSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setModifyTimes(head.getModifyTimes()); // 变更次数
					e.setMachinKind("2");// 加工性质
					e.setChangeDate(new Date());
					emsEdiTrDao.saveEmsEdiMergerExgBom(e);
				}
			}
		}
	}

	/**
	 * 根据 查询有变更的归并bom（需要复制补充的归并bom） 组装成 电子账册bom 同时把归并单耗转化为电子帐册单耗
	 * 
	 * @param merHead
	 *            归并关系表头
	 * @param emsHeadH2k
	 *            电子账册表头
	 * @param list
	 *            需要补充（复制）的bom明显
	 * @param unitweardigits
	 *            单耗控制小数位
	 * @param weardigits
	 *            损耗控制小数位
	 * @return
	 */
	public Object[] buildEmsH2kData(EmsEdiMergerHead merHead,
			EmsHeadH2k emsHeadH2k, List<EmsEdiMergerExgBom> list,
			int unitweardigits, int weardigits) {

		Map<String, EmsEdiMergerImgBefore> emsEdiMergerImgMap = initImgBefore(
				merHead, list);
		Map<String, EmsHeadH2kBom> emsH2kBomMap = new HashMap<String, EmsHeadH2kBom>();
		Map<String, EmsHeadH2kVersion> versionMap = new HashMap<String, EmsHeadH2kVersion>();
		Map<Integer, EmsHeadH2kImg> needSaveImg = new HashMap<Integer, EmsHeadH2kImg>();

		Map<Integer, EmsHeadH2kExg> emsExgMap = initExgMap(emsHeadH2k);
		Map<Integer, EmsHeadH2kImg> emsImgMap = initImgMap(emsHeadH2k);

		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);

		// 成品序号
		Integer exgSeqNum = null;
		// 归并BOM
		EmsEdiMergerExgBom bom = null;
		// 归并前料件
		EmsEdiMergerImgBefore imgBefore = null;
		// 电子账册bom
		EmsHeadH2kBom emsBom = null;
		// 归并后料件序号
		Integer seqNumAfter = null;
		// 归并bom版本号
		Integer version = null;
		// Bom 业务key 判定料件是否合并，（多个料号可能会归并到一个料件序号）// 归并后料件序号 + bom版本号 + 成品序号
		String key = null;
		// 版本业务key
		String vkey = null;
		// 电子账册版本
		EmsHeadH2kVersion v = null;
		// 电子账册成品
		EmsHeadH2kExg exg = null;
		// 电子账册料件
		EmsHeadH2kImg img = null;
		for (int i = 0; i < list.size(); i++) {
			bom = (EmsEdiMergerExgBom) list.get(i);
			exgSeqNum = bom.getEmsEdiMergerVersion().getEmsEdiMergerBefore()
					.getEmsEdiMergerExgAfter().getSeqNum();
			version = bom.getEmsEdiMergerVersion().getVersion();

			imgBefore = emsEdiMergerImgMap.get(bom.getPtNo());
			seqNumAfter = imgBefore.getEmsEdiMergerImgAfter().getSeqNum();

			exg = emsExgMap.get(exgSeqNum);
			img = emsImgMap.get(seqNumAfter);

			key = seqNumAfter + "/" + version + "/" + exgSeqNum;// 归并后料件序号 +
																// bom版本号 + 成品序号

			vkey = version + "/" + exgSeqNum;// bom版本号 + 成品序号

			/*
			 * 生成bom版本信息
			 */
			v = versionMap.get(vkey);
			if (v == null) { // 不存在版本
				v = new EmsHeadH2kVersion();
				v.setEmsHeadH2kExg(exg);
				v.setVersion(version);
				v.setName("版本：" + String.valueOf(version));
				v.setCompany(CommonUtils.getCompany());

				versionMap.put(vkey, v);
			}

			/*
			 * 生成bom信息
			 */
			emsBom = emsH2kBomMap.get(key);
			if (emsBom != null) {
				// 单耗
				Double unitwear = formatD(emsBom.getUnitWear());
				// 损耗
				Double wear = formatD(emsBom.getWear());

				// 单项用量
				Double totalUnitWear = formatD(unitwear
						+ formatD(bom.getUnitWaste()));

				emsBom.setUnitWear(forNum(totalUnitWear, unitweardigits));

				Double f = (Double.valueOf(100).equals(wear)) ? Double
						.valueOf(0) : (unitwear / (1 - (wear / 100)));
				Double f1 = (Double.valueOf(100).equals(bom.getWaste())) ? Double
						.valueOf(0) : formatD(bom.getUnitWaste())
						/ (1 - (formatD(bom.getWaste()) / 100));
				Double totalWear = (Double.valueOf(0).equals(Double.valueOf(f
						+ f1))) ? Double.valueOf(0)
						: (1 - (totalUnitWear / (f + f1)));
				emsBom.setWear(forNum(totalWear * 100, weardigits));

			} else {
				emsBom = new EmsHeadH2kBom();
				emsBom.setCompany(CommonUtils.getCompany());
				emsBom.setSeqNum(seqNumAfter);
				emsBom.setComplex(imgBefore.getComplex());
				emsBom.setName(imgBefore.getEmsEdiMergerImgAfter().getName());
				emsBom.setSpec(imgBefore.getEmsEdiMergerImgAfter().getSpec());
				emsBom.setUnit(imgBefore.getUnit());

				emsBom.setUnitWear(forNum(bom.getUnitWaste(), unitweardigits));
				emsBom.setWear(forNum(bom.getWaste(), weardigits));// 百分比

				emsBom.setPrice(imgBefore.getEmsEdiMergerImgAfter().getPrice());
				emsBom.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				emsBom.setModifyMark(ModifyMarkState.ADDED);

				// 暂存版本
				emsBom.setEmsHeadH2kVersion(v);
				emsH2kBomMap.put(key, emsBom);
			}

			/*
			 * 生成bom中用到而系统中间没有的料件信息
			 */
			if (img == null) {
				img = new EmsHeadH2kImg();
				img.setSeqNum(emsBom.getSeqNum());
				img.setComplex(emsBom.getComplex());
				img.setName(emsBom.getName());
				img.setSpec(emsBom.getSpec());
				img.setUnit(emsBom.getUnit());
				img.setDeclarePrice(emsBom.getPrice());
				img.setEmsHeadH2k(emsHeadH2k);
				img.setChangeDate(new Date());
				img.setModifyMark(ModifyMarkState.ADDED);
				if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)) {
					img.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				}

				needSaveImg.put(seqNumAfter, img);
			}

		}

		return new Object[] {
				new ArrayList<EmsHeadH2kVersion>(versionMap.values()),
				new ArrayList<EmsHeadH2kImg>(needSaveImg.values()),
				new ArrayList<EmsHeadH2kBom>(emsH2kBomMap.values()) };
	}

	/**
	 * 格式化
	 * 
	 * @param num
	 * @param digits
	 * @return
	 */
	public Double forNum(Double num, int digits) {
		double unroundedValue = 0;
		if (num == null || digits == 100) {
			return num;
		}
		unroundedValue = num.doubleValue();
		int x = 1;
		for (int i = 0; i < digits; i++) {
			x = x * 10;
		}
		double intPortion = Math.floor(unroundedValue);
		double unroundedDecimalPortion = (unroundedValue - intPortion);
		double roundedDecimalPortion = Math.round(unroundedDecimalPortion * x);
		roundedDecimalPortion = roundedDecimalPortion / x;
		double total = intPortion + roundedDecimalPortion;
		return new Double(total);
	}

	/*
	 * private static Double forNum(Double shuliang,int digits){ if (shuliang ==
	 * null || digits == 100){ return shuliang; } BigDecimal bd = new
	 * BigDecimal(shuliang.doubleValue()); String totalshuliang =
	 * bd.setScale(digits, BigDecimal.ROUND_HALF_UP).toString(); return
	 * Double.valueOf(totalshuliang); }
	 */

	/**
	 * 初始化BOM map
	 */
	private Hashtable iniHashBom(EmsHeadH2kVersion verObj) {
		Hashtable hsBom = new Hashtable();
		List list = emsEdiTrDao.findEmsHeadH2kBomByVersion(verObj);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom obj = (EmsHeadH2kBom) list.get(i);
			String key = String.valueOf(obj.getSeqNum()) + "/"
					+ String.valueOf(verObj.getVersion()) + "/"
					+ String.valueOf(verObj.getEmsHeadH2kExg().getSeqNum());
			if (hsBom.get(key.trim()) == null) {
				hsBom.put(key.trim(), obj);
			}
		}
		return hsBom;
	}

	// /**
	// * 初始化BOM map
	// */
	// private Hashtable iniHashBoms(EmsHeadH2k head) {
	// Hashtable hsBom = new Hashtable();
	// List list = emsEdiTrDao.findEmsHeadH2kBomSeq(head);
	// for (int i = 0; i < list.size(); i++) {
	// Object[] ojs = (Object[]) list.get(i);
	// // EmsHeadH2kBom obj = (EmsHeadH2kBom) list.get(i);
	// Integer mseq = (Integer) ojs[0];
	// Integer vseq = (Integer) ojs[1];
	// Integer pseq = (Integer) ojs[2];
	// String id = (String) ojs[3];
	// String keys = (mseq == null ? "" : mseq.toString()) + "/"
	// + (vseq == null ? "" : vseq.toString()) + "/"
	// + (pseq == null ? "" : pseq.toString());
	// if (hsBom.get(keys.trim()) == null) {
	// hsBom.put(keys.trim(), id);
	// }
	// }
	// return hsBom;
	// }

	/**
	 * 初始化BOM map
	 */
	private Hashtable iniHashBoms(EmsHeadH2kExg exg) {
		Hashtable hsBom = new Hashtable();
		List list = emsEdiTrDao.findEmsHeadH2kBomByExg(exg);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) list.get(i);
			Integer mseq = bom.getSeqNum();
			Integer vseq = bom.getEmsHeadH2kVersion().getVersion();
			Integer pseq = exg.getSeqNum();
			String id = bom.getId();
			String keys = (mseq == null ? "" : mseq.toString()) + "/"
					+ (vseq == null ? "" : vseq.toString()) + "/"
					+ (pseq == null ? "" : pseq.toString());
			if (hsBom.get(keys.trim()) == null) {
				hsBom.put(keys.trim(), id);
			}
		}
		return hsBom;
	}

	/**
	 * 初始化版本 map
	 */
	private Hashtable iniHashVersion(EmsHeadH2k head) {
		Hashtable hsVersion = new Hashtable();
		List list = emsEdiTrDao.findEmsHeadH2kVersionAll(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kVersion obj = (EmsHeadH2kVersion) list.get(i);
			String key = String.valueOf(obj.getEmsHeadH2kExg().getSeqNum())
					+ "/" + String.valueOf(obj.getVersion());
			if (hsVersion.get(key.trim()) == null) {
				hsVersion.put(key.trim(), obj);
			}
		}
		return hsVersion;
	}

	/**
	 * 初始化版本 map
	 */
	private Hashtable iniHashVersion(EmsHeadH2kExg exg) {
		Hashtable hsVersion = new Hashtable();
		List list = emsEdiTrDao.findEmsHeadH2kVersion(exg);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kVersion obj = (EmsHeadH2kVersion) list.get(i);
			String key = String.valueOf(obj.getEmsHeadH2kExg().getSeqNum())
					+ "/" + String.valueOf(obj.getVersion());
			if (hsVersion.get(key.trim()) == null) {
				hsVersion.put(key.trim(), obj);
			}
		}
		return hsVersion;
	}

	/**
	 * 电子帐册转换单耗
	 * 
	 * @param seqNum
	 * @param declareState
	 * @param versionI
	 * @param ptNo
	 * @return
	 */
	public List addEmsH2kDm(Integer seqNum, String declareState,
			Integer versionI, String ptNo) {
		EmsEdiMergerHead merHead = emsEdiTrDao
				.findEmsEdiMergerHead(declareState);

		Hashtable hsImg = initImgBefore(merHead);
		List list = emsEdiTrDao.findEmsEdiMergerBomByAfter(seqNum,
				declareState, versionI, ptNo);// 所有BOM 对应归并后成品序号

		int unitweardigits = 9;
		int weardigits = 5;
		String unitweardig = this.emsEdiTrDao
				.getBpara(BcusParameter.EMSBOM_UNITWEAR_DIGITS);// 单耗控制小数位
		String weardig = this.emsEdiTrDao
				.getBpara(BcusParameter.EMSBOM_WEAR_DIGITS);// 损耗控制小数位

		if (unitweardig != null && !"".equals(unitweardig)) {
			unitweardigits = Integer.parseInt(unitweardig);
		}
		if (weardig != null && !"".equals(weardig)) {
			weardigits = Integer.parseInt(weardig);
		}
		Hashtable hs = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) list.get(i);// 归并BOM
			// EmsEdiMergerImgBefore imgBefore =
			// emsEdiTrDao.findEmsEdiMergerImgBeforeByImg(bom.getPtNo(),declareState);//归并前料件
			EmsEdiMergerImgBefore imgBefore = (EmsEdiMergerImgBefore) hsImg
					.get(bom.getPtNo());
			EmsH2kBomFrom bomFrom = null;
			EmsHeadH2kBom emsBom = null;
			Integer seqNumAfter = imgBefore.getEmsEdiMergerImgAfter()
					.getSeqNum();// 归并后料件序号
			Integer version = bom.getEmsEdiMergerVersion().getVersion();// 版本号
			String key = String.valueOf(seqNumAfter) + "/"
					+ String.valueOf(version);// 归并后料件序号 + bom版本号
			if (hs.get(key) != null) {
				bomFrom = (EmsH2kBomFrom) hs.get(key);
				emsBom = bomFrom.getBom();
				Double unitwear = formatD(emsBom.getUnitWear());
				Double wear = formatD(emsBom.getWear());
				Double totalUnitWear = formatD(unitwear
						+ formatD(bom.getUnitWaste()));

				emsBom.setUnitWear(forNum(totalUnitWear, unitweardigits));

				Double f = (Double.valueOf(100).equals(wear)) ? Double
						.valueOf(0) : (unitwear / (1 - (wear / 100)));
				Double f1 = (Double.valueOf(100).equals(bom.getWaste())) ? Double
						.valueOf(0) : formatD(bom.getUnitWaste())
						/ (1 - (formatD(bom.getWaste()) / 100));
				Double totalWear = (Double.valueOf(0).equals(Double.valueOf(f
						+ f1))) ? Double.valueOf(0)
						: (1 - (totalUnitWear / (f + f1)));
				emsBom.setWear(forNum(totalWear * 100, weardigits));

				bomFrom.setBom(emsBom);
			} else {
				bomFrom = new EmsH2kBomFrom();
				bomFrom.setVersion(version);
				emsBom = new EmsHeadH2kBom();
				emsBom.setCompany(CommonUtils.getCompany());
				emsBom.setSeqNum(seqNumAfter);
				emsBom.setComplex(imgBefore.getComplex());
				emsBom.setName(imgBefore.getEmsEdiMergerImgAfter().getName());
				emsBom.setSpec(imgBefore.getEmsEdiMergerImgAfter().getSpec());
				emsBom.setUnit(imgBefore.getUnit());

				emsBom.setUnitWear(forNum(bom.getUnitWaste(), unitweardigits));
				emsBom.setWear(forNum(bom.getWaste(), weardigits));// 百分比

				emsBom.setPrice(imgBefore.getEmsEdiMergerImgAfter().getPrice());
				bomFrom.setBom(emsBom);
				hs.put(key, bomFrom);
			}
		}
		return new Vector(hs.values());
	}

	/**
	 * 保存电子帐册转单耗
	 * 
	 * @param list
	 * @param exg
	 * @return
	 */
	public int[] saveEmsBomFromMergerBom(List list, EmsHeadH2kExg exg) {

		long bt = System.currentTimeMillis();
		Hashtable hsBom = iniHashBoms(exg);
		long et = System.currentTimeMillis();
		System.out.println("iniHashBoms time:" + (et - bt) / 1000.0);

		bt = System.currentTimeMillis();
		Map versionMap = iniHashVersion(exg);
		et = System.currentTimeMillis();
		System.out.println("iniHashVersion time:" + (et - bt) / 1000.0);

		bt = System.currentTimeMillis();
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);

		int unitweardigits = 9;
		int weardigits = 5;

		String unitweardig = emsEdiTrDao
				.getBpara(BcusParameter.EMSBOM_UNITWEAR_DIGITS);
		String weardig = emsEdiTrDao.getBpara(BcusParameter.EMSBOM_WEAR_DIGITS);

		if (unitweardig != null && !"".equals(unitweardig)) {
			unitweardigits = Integer.parseInt(unitweardig);
		}
		if (weardig != null && !"".equals(weardig)) {
			weardigits = Integer.parseInt(weardig);
		}

		int totalcount = 0; // 总记录数
		int noImportCount = 0;// 没导入记录数
		int changeCount = 0;// 变更记录数
		int newCount = 0; // 新增记录数
		int noversionCount = 0;// 不存在版本号
		int nochangeCount = 0; // 存在无任何变更
		for (int i = 0; i < list.size(); i++) {
			totalcount++;
			EmsH2kBomFrom obj = (EmsH2kBomFrom) list.get(i);
			Integer version = obj.getVersion();// 版本号
			EmsHeadH2kVersion v = null;
			EmsHeadH2kBom m = null;
			EmsHeadH2kBom e = obj.getBom();// 帐册单耗
			String vkey = String.valueOf(exg.getSeqNum()) + "/"
					+ String.valueOf(version);
			v = (EmsHeadH2kVersion) versionMap.get(vkey);
			if (v != null) {
				String keyb = String.valueOf(e.getSeqNum()) + "/"
						+ String.valueOf(v.getVersion()) + "/"
						+ String.valueOf(exg.getSeqNum());
				String id = (String) hsBom.get(keyb);
				if (id != null) {
					m = this.emsEdiTrDao.findEmsHeadH2kBomById(id);
				}
				if (exg.getEmsHeadH2k().getDeclareType().equals("1")) {// 备案状态
					if (m != null) {// 存在该料件
						if (!m.getUnitWear().equals(e.getUnitWear())
								|| !m.getWear().equals(e.getWear())) {
							m.setUnitWear(CommonUtils.getDoubleByDigit(
									e.getUnitWear(), unitweardigits));
							m.setWear(CommonUtils.getDoubleByDigit(e.getWear(),
									weardigits));
							m.setModifyTimes(exg.getEmsHeadH2k()
									.getModifyTimes());// 变更次数
							emsEdiTrDao.saveEmsHeadH2kBom(m);// 修改原来料件
							changeCount++;
						} else {
							nochangeCount++;
						}
					} else {// 新增料件
						e.setEmsHeadH2kVersion(v);
						e.setModifyMark(ModifyMarkState.ADDED);
						if (isEmsH2kSendSign != null
								&& "1".equals(isEmsH2kSendSign)) {
							e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
						}
						e.setModifyTimes(exg.getEmsHeadH2k().getModifyTimes());// 变更次数
						e.setCompany(CommonUtils.getCompany());
						e.setChangeDate(new Date());
						emsEdiTrDao.saveEmsHeadH2kBom(e);
						makeEmsHeadH2kImgFromBom(e);
						newCount++;
					}
				} else { // 变更
					if (m != null) {// 存在该料件
						if (!m.getUnitWear().equals(e.getUnitWear())
								|| !m.getWear().equals(e.getWear())) {
							changeCount++;
						} else {
							nochangeCount++;
						}
					} else {
						e.setEmsHeadH2kVersion(v);
						e.setModifyMark(ModifyMarkState.ADDED);// 新增
						if (isEmsH2kSendSign != null
								&& "1".equals(isEmsH2kSendSign)) {
							e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
						}
						e.setModifyTimes(exg.getEmsHeadH2k().getModifyTimes());// 变更次数
						e.setCompany(CommonUtils.getCompany());
						e.setChangeDate(new Date());
						emsEdiTrDao.saveEmsHeadH2kBom(e);
						makeEmsHeadH2kImgFromBom(e);
						newCount++;
					}
				}
			} else { // 不存在版本
				v = new EmsHeadH2kVersion();
				v.setEmsHeadH2kExg(exg);
				v.setVersion(version);
				v.setName("版本：" + String.valueOf(version));
				v.setCompany(CommonUtils.getCompany());
				emsEdiTrDao.saveEmsHeadH2kVersion(v);
				e.setEmsHeadH2kVersion(v);
				e.setModifyMark(ModifyMarkState.ADDED);
				if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)) {
					e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				}
				e.setModifyTimes(exg.getEmsHeadH2k().getModifyTimes());// 变更次数
				e.setChangeDate(new Date());
				emsEdiTrDao.saveEmsHeadH2kBom(e);
				makeEmsHeadH2kImgFromBom(e);
				noversionCount++;

				versionMap.put(vkey, v);
			}
		}
		int[] x = new int[6];
		x[0] = noImportCount;
		x[1] = changeCount;
		x[2] = newCount;
		x[3] = noversionCount;
		x[4] = totalcount;
		x[5] = nochangeCount;

		et = System.currentTimeMillis();
		System.out.println("other time:" + (et - bt) / 1000.0);
		return x;
	}

	/**
	 * 根据电子帐册单耗表生成电子帐册的料件
	 * 
	 */
	private void makeEmsHeadH2kImgFromBom(EmsHeadH2kBom bom) {
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);
		List list = this.emsEdiTrDao.findEmsHeadH2kImgBySeqNum(bom
				.getEmsHeadH2kVersion().getEmsHeadH2kExg().getEmsHeadH2k(),
				bom.getSeqNum());
		if (list.size() <= 0) {
			EmsHeadH2kImg emsH2kImg = new EmsHeadH2kImg();
			emsH2kImg.setSeqNum(bom.getSeqNum());
			emsH2kImg.setComplex(bom.getComplex());
			emsH2kImg.setName(bom.getName());
			emsH2kImg.setSpec(bom.getSpec());
			emsH2kImg.setUnit(bom.getUnit());
			emsH2kImg.setDeclarePrice(bom.getPrice());
			emsH2kImg.setEmsHeadH2k(bom.getEmsHeadH2kVersion()
					.getEmsHeadH2kExg().getEmsHeadH2k());
			// emsH2kImg.setCurr(emsH2kImg.getEmsHeadH2k().get)
			emsH2kImg.setChangeDate(new Date());
			emsH2kImg.setModifyMark(ModifyMarkState.ADDED);
			if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)) {
				emsH2kImg.setSendState(Integer.valueOf(SendState.WAIT_SEND));
			}
			this.emsEdiTrDao.saveEmsHeadH2kImg(emsH2kImg);
		}
	}

	/**
	 * 格式化
	 * 
	 * @param x
	 * @return
	 */
	public Double formatD(Double x) {
		if (x == null) {
			return Double.valueOf(0);
		}
		return x;
	}

	/**
	 * 开始导入（检查是否备案）---归并关系BOM文本导入---明门
	 * 
	 * @param versionObj
	 * @param list
	 * @param exgAfter
	 * @return
	 */
	public List bomImport(EmsEdiMergerVersion versionObj, List list,
			EmsEdiMergerExgAfter exgAfter) {
		EmsEdiMergerHead emshead = versionObj.getEmsEdiMergerBefore()
				.getEmsEdiMergerExgAfter().getEmsEdiMergerHead();
		Hashtable hsImg = initImgBefore(emshead);
		List alllist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerBomFrom obj = (EmsEdiMergerBomFrom) list.get(i);
			String ljNo = obj.getBom().getPtNo().trim(); // 料件货号
			EmsEdiMergerImgBefore img = getImgBefore(
					(ljNo == null || "".equals(ljNo)) ? "" : ljNo, hsImg);
			if (img == null) {
				String errinfo = obj.getErrinfo();
				obj.setErrinfo(errinfo + "/料件序号不存在");
			}
			alllist.add(obj);
		}
		return alllist;
	}

	// ----------------------------------------------
	/**
	 * 开始导入（检查是否备案）---电子帐册单耗导入 -- 明门需求
	 */
	public List bomEmsImport(EmsHeadH2k emsHeadH2k, List list) {
		Hashtable hsEmsImg = initEmsHeadH2kImg(emsHeadH2k);
		List alllist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiHeadH2kBomFrom obj = (EmsEdiHeadH2kBomFrom) list.get(i);
			Integer ljNo = obj.getBom().getSeqNum(); // 料件序号
			EmsHeadH2kImg img = getEmsHeadH2kImg(
					ljNo == null ? Integer.valueOf(0) : ljNo, hsEmsImg);
			if (img == null) {
				String errinfo = obj.getErrinfo();
				obj.setErrinfo(errinfo + "/料件序号不存在");
			}
			alllist.add(obj);
		}
		return alllist;
	}

	// -------------------------------------------------------------------
	/**
	 * 得到归并前料件
	 */
	private Hashtable initImgBefore(EmsEdiMergerHead emsEdiMergerHead) {

		Hashtable hsImg = new Hashtable();
		List list = emsEdiTrDao
				.findEmsEdiMergerImgBeforeByPtNo(emsEdiMergerHead);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list.get(i);
			if (hsImg.get(obj.getPtNo().trim()) == null) {
				hsImg.put(obj.getPtNo().trim(), obj);
			}
		}
		return hsImg;
	}

	/**
	 * 得到归并前料件
	 */
	private Hashtable initImgBefore(EmsEdiMergerHead emsEdiMergerHead,
			List<EmsEdiMergerExgBom> bomList) {
		/*
		 * 取得bom要使用的料号
		 */
		Set<String> ptNoSet = new HashSet<String>();
		String ptNo = null;
		for (int i = 0; i < bomList.size(); i++) {
			ptNo = bomList.get(i).getPtNo();
			if (!ptNoSet.contains(ptNo)) {
				ptNoSet.add(ptNo);
			}
		}

		Hashtable hsImg = new Hashtable();
		// 查找指定料号的归并关系归并前料件表
		List list = emsEdiTrDao.findEmsEdiMergerImgBeforeByPtNo(
				emsEdiMergerHead, ptNoSet);

		/*
		 * 把list转化为map，key为料号
		 */
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list.get(i);
			if (hsImg.get(obj.getPtNo().trim()) == null) {
				hsImg.put(obj.getPtNo().trim(), obj);
			}
		}

		return hsImg;
	}

	/**
	 * 电子账册成品Map
	 */
	private Map<Integer, EmsHeadH2kExg> initExgMap(EmsHeadH2k emsHeadH2k) {

		Map<Integer, EmsHeadH2kExg> hsImg = new HashMap<Integer, EmsHeadH2kExg>();
		List<EmsHeadH2kExg> list = emsEdiTrDao.getEmsExg(emsHeadH2k);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kExg obj = list.get(i);
			if (hsImg.get(obj.getSeqNum()) == null) {
				hsImg.put(obj.getSeqNum(), obj);
			}
		}

		return hsImg;
	}

	/**
	 * 电子账册成品Map
	 */
	private Map<Integer, EmsHeadH2kImg> initImgMap(EmsHeadH2k emsHeadH2k) {

		Map<Integer, EmsHeadH2kImg> hsImg = new HashMap<Integer, EmsHeadH2kImg>();
		List<EmsHeadH2kImg> list = emsEdiTrDao.getEmsImg(emsHeadH2k);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg obj = list.get(i);
			if (hsImg.get(obj.getSeqNum()) == null) {
				hsImg.put(obj.getSeqNum(), obj);
			}
		}

		return hsImg;
	}

	/**
	 * 得到归并前 成品/Version/版本号/PtNo/料号
	 * 
	 * @param versionObj
	 * @return
	 */
	private Hashtable initExgVersionImgBefore(EmsEdiMergerVersion versionObj) {
		Hashtable hsExgVersionImg = new Hashtable();
		List list = emsEdiTrDao.findEmsEdiMergerBomByVersion(versionObj);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list.get(i);
			String key = versionObj.getEmsEdiMergerBefore().getPtNo()
					+ "/Version/" + String.valueOf(versionObj.getVersion())
					+ "/PtNo/" + obj.getPtNo();
			if (hsExgVersionImg.get(key.trim()) == null) {
				hsExgVersionImg.put(key.trim(), obj);
			}
		}
		return hsExgVersionImg;
	}

	/**
	 * 获取归并前料件
	 * 
	 * @param cpNo
	 * @param hsImg
	 * @return
	 */
	private EmsEdiMergerImgBefore getImgBefore(String cpNo, Hashtable hsImg) {
		if (hsImg.get(cpNo) != null) {
			return (EmsEdiMergerImgBefore) hsImg.get(cpNo);
		}
		return null;
	}

	/**
	 * 得到帐册料件(备案序号)
	 * 
	 * @param emsHeadH2k
	 * @return
	 */
	private Hashtable initEmsHeadH2kImg(EmsHeadH2k emsHeadH2k) {

		Hashtable hsEmsImg = new Hashtable();

		List list = emsEdiTrDao.findEmsHeadH2kImg(emsHeadH2k);

		for (int i = 0; i < list.size(); i++) {

			EmsHeadH2kImg obj = (EmsHeadH2kImg) list.get(i);

			hsEmsImg.put(obj.getSeqNum(), obj);

		}

		return hsEmsImg;
	}

	/**
	 * 获取电子账册料件
	 * 
	 * @param seqNum
	 * @param hsEmsImg
	 * @return
	 */
	private EmsHeadH2kImg getEmsHeadH2kImg(Integer seqNum, Hashtable hsEmsImg) {
		if (hsEmsImg.get(seqNum) != null) {
			return (EmsHeadH2kImg) hsEmsImg.get(seqNum);
		}
		return null;
	}

	/**
	 * 保存导入归并关系文本导入 -- 明门
	 * 
	 * @param versionObj
	 * @param list
	 * @param isChange
	 * @return
	 */
	public int[] saveToEmsMerger(EmsEdiMergerVersion versionObj, List list,
			boolean isChange) {
		EmsEdiMergerHead head = versionObj.getEmsEdiMergerBefore()
				.getEmsEdiMergerExgAfter().getEmsEdiMergerHead();
		Hashtable hsExgVersionImg = initExgVersionImgBefore(versionObj);// cpPtNo
		// +
		// version
		// +
		// LjptNo
		String isSendSign = emsEdiTrDao.getBpara(BcusParameter.EmsSEND_Sign);// 是否分批发送标记
		int totalcount = 0; // 总记录数
		int noImportCount = 0;// 没导入记录数
		int changeCount = 0;// 变更记录数
		int newCount = 0; // 新增记录数
		int nochangeCount = 0; // 存在但无任何变化
		Hashtable hsImg = initImgBefore(head);
		for (int i = 0; i < list.size(); i++) {
			totalcount++;
			EmsEdiMergerBomFrom obj = (EmsEdiMergerBomFrom) list.get(i);
			if (obj.getErrinfo() != null && !obj.getErrinfo().equals("")) {
				noImportCount++;
				continue;
			}
			String cpNo = obj.getPtNo().trim();
			Integer version = obj.getVersion();
			EmsEdiMergerExgBom m = null;
			EmsEdiMergerExgBom e = obj.getBom();
			String exgVerimgKey = cpNo + "/Version/" + String.valueOf(version)
					+ "/PtNo/" + e.getPtNo();
			if (hsExgVersionImg.get(exgVerimgKey) != null) {
				m = (EmsEdiMergerExgBom) hsExgVersionImg.get(exgVerimgKey);
			}
			if (head.getDeclareType().equals("1")) {// 备案状态
				if (m != null) {// 存在该料件
					if (!m.getUnitWaste().equals(e.getUnitWaste())
							|| !m.getWaste().equals(e.getWaste())) {
						m.setUnitWaste(e.getUnitWaste());
						m.setWaste(e.getWaste());
						emsEdiTrDao.saveEmsEdiMergerExgBom(m);// 修改原来料件
						changeCount++;
					} else {
						nochangeCount++;
					}
				} else {// 新增料件
					EmsEdiMergerImgBefore img = getImgBefore(e.getPtNo(), hsImg);
					e.setEmsEdiMergerVersion(versionObj);
					e.setName(img.getName());
					e.setComplex(img.getComplex());
					e.setSpec(img.getSpec());
					e.setUnit(img.getUnit());
					e.setModifyMark(ModifyMarkState.ADDED);
					if (isSendSign != null && "1".equals(isSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setChangeDate(new Date());
					e.setCompany(CommonUtils.getCompany());
					e.setMachinKind("2");// 加工性质
					emsEdiTrDao.saveEmsEdiMergerExgBom(e);
					newCount++;
				}
			} else { // 变更
				if (m != null) {// 存在该料件
					if (isChange) { // 存在料件是否覆盖
						if (!e.getUnitWaste().equals(m.getUnitWaste())
								|| !e.getWaste().equals(m.getWaste())) {
							m.setUnitWaste(e.getUnitWaste());
							m.setWaste(e.getWaste());
							if (m.getModifyMark().equals(
									ModifyMarkState.UNCHANGE)) {
								m.setModifyMark(ModifyMarkState.MODIFIED);// 已修改
								if (isSendSign != null
										&& "1".equals(isSendSign)) {
									m.setSendState(Integer
											.valueOf(SendState.WAIT_SEND));
								}
							}
							emsEdiTrDao.saveEmsEdiMergerExgBom(m);
							changeCount++;
						} else {
							nochangeCount++;
						}
					} else {
						nochangeCount++;
					}
				} else {
					EmsEdiMergerImgBefore img = getImgBefore(e.getPtNo(), hsImg);
					e.setEmsEdiMergerVersion(versionObj);
					e.setName(img.getName());
					e.setComplex(img.getComplex());
					e.setSpec(img.getSpec());
					e.setUnit(img.getUnit());
					e.setModifyMark(ModifyMarkState.ADDED);// 新增
					if (isSendSign != null && "1".equals(isSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setChangeDate(new Date());
					e.setCompany(CommonUtils.getCompany());
					e.setMachinKind("2"); // 加工性质
					emsEdiTrDao.saveEmsEdiMergerExgBom(e);
					newCount++;
				}
			}
		}
		int[] x = new int[6];
		x[0] = noImportCount;
		x[1] = changeCount;
		x[2] = newCount;
		x[4] = totalcount;
		x[5] = nochangeCount;
		return x;
	}

	/**
	 * 获取已存在版本
	 * 
	 * @param exg
	 * @param version
	 * @param hsinverno
	 * @return
	 */
	private EmsEdiMergerVersion getExistVersion(EmsEdiMergerExgBefore exg,
			Integer version, Hashtable hsinverno) {
		EmsEdiMergerVersion obj = null;
		String key = exg.getPtNo() + "/" + String.valueOf(version);
		if (hsinverno.get(key) == null) {
			List versionList = emsEdiTrDao.findExistVersion(exg,
					String.valueOf(version));// 查找版本
			if (versionList != null && versionList.size() > 0) {
				obj = (EmsEdiMergerVersion) versionList.get(0);
				hsinverno.put(key, obj);
			}
		} else {
			obj = (EmsEdiMergerVersion) hsinverno.get(key);
		}
		return obj;
	}

	/**
	 * 开始导入（检查是否备案）---归并关系BOM文本导入---明门
	 * 
	 * @param emsEdiMergerHead
	 * @param list
	 * @return
	 */
	// public List bomImport1(EmsEdiMergerHead emsEdiMergerHead, List list) {
	// // Hashtable hsinexg = new Hashtable();
	// // Hashtable hsinimg = new Hashtable();
	// // for (int i = 0; i < list.size(); i++) {
	// // EmsEdiMergerBomFrom obj = (EmsEdiMergerBomFrom) list.get(i);
	// //
	// // // 导入成品在归并前成品中不存在不给导入...
	// // String cpNo = obj.getPtNo().trim();
	// // EmsEdiMergerExgBefore exg = getExistBeforeExg(emsEdiMergerHead,
	// // cpNo, hsinexg);
	// // if (exg == null) {
	// // String exgError = "成品货号：(" + cpNo + ")在归并关系中不存在！";
	// // obj.setErrinfo(obj.getErrinfo() + exgError);
	// // }
	// // // 判断料件是否存在，不存在不导入
	// // EmsEdiMergerExgBom e = obj.getBom();
	// // EmsEdiMergerImgBefore img = getExistBeforeImg(emsEdiMergerHead, e
	// // .getPtNo(), hsinimg);
	// // if (img == null) {
	// // String imgError = "料件货号：(" + e.getPtNo() + ")在归并关系中不存在！";
	// // obj.setErrinfo(obj.getErrinfo() + imgError);
	// // }
	// // }
	// return list;
	// }
	/**
	 * 保存导入归并关系文本导入
	 * 
	 * @param emsEdiMergerHead
	 * @param list
	 * @param isChange
	 * @return
	 */
	public List saveToEmsMerger(EmsEdiMergerExgBefore exg,
			EmsEdiMergerBomFrom obj, EmsEdiMergerHead emsEdiMergerHead,
			boolean isChange) {
		Hashtable hsinverno = new Hashtable();
		String isSendSign = emsEdiTrDao.getBpara(BcusParameter.EmsSEND_Sign);// 是否分批发送标记
		// int totalcount = 0; // 总记录数
		// int noImportCount = 0;// 没导入记录数
		// int changeCount = 0;// 变更记录数
		// int newCount = 0; // 新增记录数
		// int noversionCount = 0;// 不存在版本号
		// int nochangeCount = 0; // 存在但无任何变化

		Integer version = obj.getVersion();
		String cmpVersion = obj.getCmpVersion();
		EmsEdiMergerVersion v = null;
		EmsEdiMergerExgBom m = null;
		EmsEdiMergerExgBom e = obj.getBom();

		v = getExistVersion(exg, version, hsinverno);
		if (v != null) { // 存在版本
			List b = emsEdiTrDao.findEmsEdiMergerBom(v, e.getPtNo());// 判断是否存在该料件
			if (b != null && b.size() > 0) {
				m = (EmsEdiMergerExgBom) b.get(0);// 已经存在的BOM
			}
			if (emsEdiMergerHead.getDeclareType().equals("1")) {// 备案状态
				if (m != null) {// 存在该料件
					if (!e.getUnitWaste().equals(m.getUnitWaste())
							|| !e.getWaste().equals(m.getWaste())) {
						m.setUnitWaste(CommonUtils.getDoubleByDigit(
								e.getUnitWaste(), 9));
						m.setWaste(CommonUtils.getDoubleByDigit(e.getWaste(), 5));
						emsEdiTrDao.saveEmsEdiMergerExgBom(m);// 修改原来料件
						// changeCount++;
					} else {
						// nochangeCount++;
					}
				} else {// 新增料件
					// EmsEdiMergerImgBefore img = getExistBeforeImg(
					// emsEdiMergerHead, e.getPtNo(), hsinimg);
					// 查找
					List ls = emsEdiTrDao.findEmsEdiMergerImgBeforeByGNo(true,
							e.getPtNo(), emsEdiMergerHead);
					if (ls != null && ls.size() > 0) {
						EmsEdiMergerImgBefore img = (EmsEdiMergerImgBefore) ls
								.get(0);
						e.setEmsEdiMergerVersion(v);
						e.setName(img.getName());
						e.setComplex(img.getComplex());
						e.setSpec(img.getSpec());
						e.setUnit(img.getUnit());
						e.setMachinKind("2"); // 加工性质
						e.setModifyMark(ModifyMarkState.ADDED);
						if (isSendSign != null && "1".equals(isSendSign)) {
							e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
						}
						e.setChangeDate(new Date());
						e.setCompany(CommonUtils.getCompany());
						emsEdiTrDao.saveEmsEdiMergerExgBom(e);
						// newCount++;
					} else {
						// noImportCount++;
					}
				}
			} else { // 变更
				if (m != null) {// 存在该料件
					if (isChange) { // 存在料件是否覆盖
						if (!e.getUnitWaste().equals(m.getUnitWaste())
								|| !e.getWaste().equals(m.getWaste())) {
							m.setUnitWaste(CommonUtils.getDoubleByDigit(
									e.getUnitWaste(), 9));
							m.setWaste(CommonUtils.getDoubleByDigit(
									e.getWaste(), 5));
							if (m.getModifyMark().equals(
									ModifyMarkState.UNCHANGE)) {
								m.setModifyMark(ModifyMarkState.MODIFIED);// 已修改
								if (isSendSign != null
										&& "1".equals(isSendSign)) {
									m.setSendState(Integer
											.valueOf(SendState.WAIT_SEND));
								}
							}
							emsEdiTrDao.saveEmsEdiMergerExgBom(m);
							// changeCount++;
						} else {
							// nochangeCount++;
						}
					} else {
						// nochangeCount++;
					}
				} else {
					// EmsEdiMergerImgBefore img = getExistBeforeImg(
					// emsEdiMergerHead, e.getPtNo(), hsinimg);
					// 查找
					List ls = emsEdiTrDao.findEmsEdiMergerImgBeforeByGNo(true,
							e.getPtNo(), emsEdiMergerHead);
					if (ls != null && ls.size() > 0) {
						EmsEdiMergerImgBefore img = (EmsEdiMergerImgBefore) ls
								.get(0);
						e.setEmsEdiMergerVersion(v);
						e.setName(img.getName());
						e.setComplex(img.getComplex());
						e.setSpec(img.getSpec());
						e.setUnit(img.getUnit());
						e.setModifyMark(ModifyMarkState.ADDED);// 新增
						e.setMachinKind("2");// 加工性质
						if (isSendSign != null && "1".equals(isSendSign)) {
							e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
						}
						e.setChangeDate(new Date());
						e.setCompany(CommonUtils.getCompany());
						emsEdiTrDao.saveEmsEdiMergerExgBom(e);
						// newCount++;
					} else {
						// noImportCount++;
					}
				}
			}
		} else { // 不存在版本
			v = new EmsEdiMergerVersion();
			v.setEmsEdiMergerBefore(exg);
			v.setVersion(version);
			v.setCmpVersion(cmpVersion);// 企业版本
			v.setName("版本：" + String.valueOf(version));
			v.setCompany(CommonUtils.getCompany());
			emsEdiTrDao.saveEmsEdiMergerVersion(v);
			// EmsEdiMergerImgBefore img =
			// getExistBeforeImg(emsEdiMergerHead,
			// e.getPtNo(), hsinimg);
			// 查找
			List ls = emsEdiTrDao.findEmsEdiMergerImgBeforeByGNo(true,
					e.getPtNo(), emsEdiMergerHead);
			if (ls != null && ls.size() > 0) {
				EmsEdiMergerImgBefore img = (EmsEdiMergerImgBefore) ls.get(0);
				e.setEmsEdiMergerVersion(v);
				e.setName(img.getName());
				e.setComplex(img.getComplex());
				e.setSpec(img.getSpec());
				e.setUnit(img.getUnit());
				e.setModifyMark(ModifyMarkState.ADDED);
				e.setMachinKind("2");// 加工性质
				if (isSendSign != null && "1".equals(isSendSign)) {
					e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
				}
				e.setChangeDate(new Date());
				emsEdiTrDao.saveEmsEdiMergerExgBom(e);
				// noversionCount++;
			} else {
				// noImportCount++;
			}
		}
		List results = new ArrayList();
		// results.add(noImportCount);
		// results.add(changeCount);
		// results.add(newCount);
		// results.add(noversionCount);
		// results.add(totalcount);
		// results.add(nochangeCount);
		return results;
	}

	// ----------------------------------------------------------

	/**
	 * 保存导入电子帐册文本导入 -- 明门需求
	 */
	public int[] saveToEmsHeadH2k(EmsHeadH2kVersion vobj, List list) {
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);
		int totalcount = 0; // 总记录数
		int noImportCount = 0;// 没导入记录数
		int changeCount = 0;// 变更记录数
		int newCount = 0; // 新增记录数
		int nochangeCount = 0; // 存在但无任何变化
		Hashtable hsEmsImg = initEmsHeadH2kImg(vobj.getEmsHeadH2k());
		for (int i = 0; i < list.size(); i++) {
			totalcount++;
			EmsEdiHeadH2kBomFrom obj = (EmsEdiHeadH2kBomFrom) list.get(i);
			if (obj.getErrinfo() != null && !obj.getErrinfo().equals("")) { // 有错误信息
				noImportCount++;
				continue;
			}
			EmsHeadH2kBom m = null;
			EmsHeadH2kBom e = obj.getBom(); // import - emsHeadH2kBom
			// bomseqNum + version + cpSeqNum
			String keyb = String.valueOf(e.getSeqNum()) + "/"
					+ String.valueOf(vobj.getVersion()) + "/"
					+ String.valueOf(vobj.getEmsHeadH2kExg().getSeqNum());
			m = (EmsHeadH2kBom) iniHashBom(vobj).get(keyb);
			if (vobj.getEmsHeadH2kExg().getEmsHeadH2k().getDeclareType()
					.equals("1")) {// 备案状态
				if (m != null) {// 存在该料件
					if (!m.getUnitWear().equals(e.getUnitWear())
							|| !m.getWear().equals(e.getWear())) {
						m.setUnitWear(e.getUnitWear());
						m.setWear(e.getWear());
						m.setModifyTimes(vobj.getEmsHeadH2kExg()
								.getEmsHeadH2k().getModifyTimes());// 修改次数
						emsEdiTrDao.saveEmsHeadH2kBom(m);// 修改原来料件
						changeCount++;
					} else {
						nochangeCount++;
					}
				} else {// 新增料件
					EmsHeadH2kImg img = getEmsHeadH2kImg(e.getSeqNum(),
							hsEmsImg);
					e.setEmsHeadH2kVersion(vobj);
					e.setName(img.getName());
					e.setComplex(img.getComplex());
					e.setSpec(img.getSpec());
					e.setUnit(img.getUnit());
					e.setPrice(img.getDeclarePrice());
					e.setModifyMark(ModifyMarkState.ADDED);
					if (isEmsH2kSendSign != null
							&& "1".equals(isEmsH2kSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setModifyTimes(vobj.getEmsHeadH2kExg().getEmsHeadH2k()
							.getModifyTimes());// 修改次数
					e.setChangeDate(new Date());
					e.setCompany(CommonUtils.getCompany());
					emsEdiTrDao.saveEmsHeadH2kBom(e);
					newCount++;
				}
			} else { // 变更状态
				if (m != null) {// 存在该料件
					if (!m.getUnitWear().equals(e.getUnitWear())
							|| !m.getWear().equals(e.getWear())) {
						changeCount++;
					} else {
						nochangeCount++;
					}
				} else {
					EmsHeadH2kImg img = getEmsHeadH2kImg(e.getSeqNum(),
							hsEmsImg);
					e.setEmsHeadH2kVersion(vobj);
					e.setName(img.getName());
					e.setComplex(img.getComplex());
					e.setSpec(img.getSpec());
					e.setUnit(img.getUnit());
					e.setPrice(img.getDeclarePrice());
					e.setModifyMark(ModifyMarkState.ADDED);// 新增
					if (isEmsH2kSendSign != null
							&& "1".equals(isEmsH2kSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setModifyTimes(vobj.getEmsHeadH2kExg().getEmsHeadH2k()
							.getModifyTimes());// 修改次数
					e.setChangeDate(new Date());
					e.setCompany(CommonUtils.getCompany());
					emsEdiTrDao.saveEmsHeadH2kBom(e);
					newCount++;
				}
			}
		}
		int[] x = new int[6];
		x[0] = noImportCount;
		x[1] = changeCount;
		x[2] = newCount;
		x[4] = totalcount;
		x[5] = nochangeCount;
		return x;
	}

	/**
	 * 保存导入电子帐册文本导入 -- 明门需求
	 * 
	 * @param vobj
	 * @param list
	 * @return
	 */
	public int[] saveToEmsHeadH2kByKCB(EmsHeadH2kVersion vobj, List list) {
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);
		int totalcount = 0; // 总记录数
		int noImportCount = 0;// 没导入记录数
		int changeCount = 0;// 变更记录数
		int newCount = 0; // 新增记录数
		int nochangeCount = 0; // 存在但无任何变化
		Hashtable hsEmsImg = initEmsHeadH2kImg(vobj.getEmsHeadH2k());
		for (int i = 0; i < list.size(); i++) {
			totalcount++;
			EmsEdiHeadH2kBomFrom obj = (EmsEdiHeadH2kBomFrom) list.get(i);
			if (obj.getErrinfo() != null && !obj.getErrinfo().equals("")) { // 有错误信息
				noImportCount++;
				continue;
			}
			EmsHeadH2kBom m = null;
			EmsHeadH2kBom e = obj.getBom(); // import - emsHeadH2kBom
			// bomseqNum + version + cpSeqNum
			String keyb = String.valueOf(e.getSeqNum()) + "/"
					+ String.valueOf(vobj.getVersion()) + "/"
					+ String.valueOf(vobj.getEmsHeadH2kExg().getSeqNum());
			m = (EmsHeadH2kBom) iniHashBom(vobj).get(keyb);
			if (vobj.getEmsHeadH2kExg().getEmsHeadH2k().getDeclareType()
					.equals("1")) {// 备案状态
				if (m != null) {// 存在该料件
					if (!m.getUnitWear().equals(e.getUnitWear())
							|| !m.getWear().equals(e.getWear())) {
						m.setUnitWear(e.getUnitWear());
						m.setWear(e.getWear());
						m.setModifyTimes(vobj.getEmsHeadH2kExg()
								.getEmsHeadH2k().getModifyTimes());// 修改次数
						emsEdiTrDao.saveEmsHeadH2kBom(m);// 修改原来料件
						changeCount++;
					} else {
						nochangeCount++;
					}
				} else {// 新增料件
					EmsHeadH2kImg img = getEmsHeadH2kImg(e.getSeqNum(),
							hsEmsImg);
					e.setEmsHeadH2kVersion(vobj);
					e.setName(img.getName());
					e.setComplex(img.getComplex());
					e.setSpec(img.getSpec());
					e.setUnit(img.getUnit());
					e.setPrice(img.getDeclarePrice());
					e.setModifyMark(ModifyMarkState.ADDED);
					if (isEmsH2kSendSign != null
							&& "1".equals(isEmsH2kSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setModifyTimes(vobj.getEmsHeadH2kExg().getEmsHeadH2k()
							.getModifyTimes());// 修改次数
					e.setChangeDate(new Date());
					e.setCompany(CommonUtils.getCompany());
					emsEdiTrDao.saveEmsHeadH2kBom(e);
					newCount++;
				}
			} else { // 变更状态
				if (m != null) {// 存在该料件
					if (!m.getUnitWear().equals(e.getUnitWear())
							|| !m.getWear().equals(e.getWear())) {
						changeCount++;
					} else {
						nochangeCount++;
					}
				} else {
					EmsHeadH2kImg img = getEmsHeadH2kImg(e.getSeqNum(),
							hsEmsImg);
					e.setEmsHeadH2kVersion(vobj);
					e.setName(img.getName());
					e.setComplex(img.getComplex());
					e.setSpec(img.getSpec());
					e.setUnit(img.getUnit());
					e.setPrice(img.getDeclarePrice());
					e.setModifyMark(ModifyMarkState.ADDED);// 新增
					if (isEmsH2kSendSign != null
							&& "1".equals(isEmsH2kSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setModifyTimes(vobj.getEmsHeadH2kExg().getEmsHeadH2k()
							.getModifyTimes());// 修改次数
					e.setChangeDate(new Date());
					e.setCompany(CommonUtils.getCompany());
					emsEdiTrDao.saveEmsHeadH2kBom(e);
					newCount++;
				}
			}
		}
		int[] x = new int[6];
		x[0] = noImportCount;
		x[1] = changeCount;
		x[2] = newCount;
		x[4] = totalcount;
		x[5] = nochangeCount;
		return x;
	}

	/**
	 * 保存导入电子帐册文本导入 -- 普通
	 * 
	 * @param emsHeadH2k
	 * @param list
	 * @param cbIsOverwrite
	 * @return
	 */
	public int[] saveToEmsHeadH2k(EmsHeadH2k emsHeadH2k,
			List<EmsEdiHeadH2kBomFrom> list, boolean cbIsOverwrite) {

		long begin = System.currentTimeMillis();

		/*
		 * 1、 准备辅助数据
		 */
		// 1.1 验证emsbom数据
		Map<String, EmsHeadH2kBom> emsBomMap = new HashMap<String, EmsHeadH2kBom>();

		// 1.2 验证emsExg数据
		Map<Integer, EmsHeadH2kExg> exgMap = new HashMap<Integer, EmsHeadH2kExg>();

		// 1.3 验证emsVersion数据
		Map<String, EmsHeadH2kVersion> versionMap = new HashMap<String, EmsHeadH2kVersion>();

		// 1.4 验证emsImg数据
		Map<Integer, EmsHeadH2kImg> imgMap = new HashMap<Integer, EmsHeadH2kImg>();

		// 1.5 准备辅助数据
		initAssistDataForSaveToEmsHeadH2k(list, emsHeadH2k, emsBomMap, exgMap,
				versionMap, imgMap);

		long end = System.currentTimeMillis();

		System.out.println("准备数据 用时：" + (end - begin) / 1000.0);

		begin = System.currentTimeMillis();

		/*
		 * 2、生成需要保存的bom
		 */
		Object[] res = buildDataForSaveToEmsHeadH2k(emsHeadH2k, list,
				cbIsOverwrite, emsBomMap, exgMap, versionMap, imgMap);

		end = System.currentTimeMillis();

		System.out.println("生成需要保存的bom 用时：" + (end - begin) / 1000.0);

		begin = System.currentTimeMillis();

		/*
		 * 3、保存数据
		 */
		List<EmsHeadH2kVersion> versionSaveList = (List<EmsHeadH2kVersion>) res[1]; // 需要保存的版本

		List<EmsHeadH2kBom> bomSaveList = (List<EmsHeadH2kBom>) res[2];// 需要保存的bom

		emsEdiTrDao.batchSaveOrUpdate(versionSaveList);// 保存的版本

		emsEdiTrDao.batchSaveOrUpdate(bomSaveList);// 保存的bom

		System.out.println("保存数据 用时：" + (end - begin) / 1000.0);

		return (int[]) res[0];
	}

	/**
	 * 建立数据为了保存到电子账册
	 * 
	 * @param emsHeadH2k
	 * @param list
	 * @param cbIsOverwrite
	 * @param emsBomMap
	 * @param exgMap
	 * @param versionMap
	 * @param imgMap
	 * @return
	 */
	private Object[] buildDataForSaveToEmsHeadH2k(EmsHeadH2k emsHeadH2k,
			List<EmsEdiHeadH2kBomFrom> list, boolean cbIsOverwrite,
			Map<String, EmsHeadH2kBom> emsBomMap,
			Map<Integer, EmsHeadH2kExg> exgMap,
			Map<String, EmsHeadH2kVersion> versionMap,
			Map<Integer, EmsHeadH2kImg> imgMap) {

		//
		List<EmsHeadH2kVersion> versionSaveList = new ArrayList<EmsHeadH2kVersion>();

		List<EmsHeadH2kBom> bomSaveList = new ArrayList<EmsHeadH2kBom>();

		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);

		int totalcount = 0; // 总记录数
		int noImportCount = 0;// 没导入记录数
		int changeCount = 0;// 变更记录数
		int newCount = 0; // 新增记录数
		int noversionCount = 0;// 不存在版本号
		int nochangeCount = 0; // 存在但无任何变化

		EmsEdiHeadH2kBomFrom obj = null;
		EmsHeadH2kBom m = null;// 表中bom
		EmsHeadH2kBom e = null;// 导入bom

		Integer exgSeqNum = null;// 成品序号
		Integer version = null;// 版本号
		Integer imgSeqNum = null;// 料件序号
		String key = null;// 料件序号 + 版本号 + 成品序号
		String vkey = null;// 版本号 + 成品序号

		EmsHeadH2kExg exg = null; // 成品
		EmsHeadH2kVersion v = null;// 版本
		EmsHeadH2kImg img = null;// 料件
		for (int i = 0; i < list.size(); i++) {
			totalcount++;
			obj = list.get(i);

			if (obj.getErrinfo() != null && !obj.getErrinfo().equals("")) {
				noImportCount++;
				continue;
			}

			e = obj.getBom();
			e.setUnitWear(CommonUtils.getDoubleByDigit(e.getUnitWear(), 9));
			e.setWear(CommonUtils.getDoubleByDigit(e.getWear(), 5));

			exgSeqNum = obj.getSeqNum();
			version = obj.getVersion();
			imgSeqNum = e.getSeqNum();

			// 料件序号 + / + 版本号 + / + 成品序号
			key = imgSeqNum + "/" + version + "/" + exgSeqNum;
			// 版本号 + / + 成品序号
			vkey = version + "/" + exgSeqNum;

			exg = exgMap.get(exgSeqNum);
			// 成品不存在
			if (exg == null) {
				nochangeCount++;
				continue;
			}

			v = versionMap.get(vkey);
			if (v != null) { // 存在版本

				// 判断在当前版本下是否存在该料件
				m = emsBomMap.get(key);

				if (emsHeadH2k.getDeclareType().equals("1")) {// 备案状态
					if (m != null) {// 该版本存在该料件作为子件的bom
						if ((!e.getUnitWear().equals(m.getUnitWear()) || !e
								.getWear().equals(m.getWear()))
								&& m.getModifyMark().equals(3)) {// 并且bom为新增状态
							m.setUnitWear(e.getUnitWear());
							m.setWear(e.getWear());
							m.setModifyTimes(emsHeadH2k.getModifyTimes());// 修改次数

							// 记录修改料件
							bomSaveList.add(m);
							changeCount++;
						} else {
							nochangeCount++;
						}
					} else {// 不存在该料件作为子件的bom 新增 该料件作为子件的bom

						img = imgMap.get(imgSeqNum);

						if (img != null) {// 料件表里面存在该料件
							e.setEmsHeadH2kVersion(v);
							e.setName(img.getName());
							e.setComplex(img.getComplex());
							e.setSpec(img.getSpec());
							e.setUnit(img.getUnit());
							e.setPrice(img.getDeclarePrice());
							e.setModifyMark(ModifyMarkState.ADDED);
							if (isEmsH2kSendSign != null
									&& "1".equals(isEmsH2kSendSign)) {
								e.setSendState(Integer
										.valueOf(SendState.WAIT_SEND));
							}
							e.setModifyTimes(emsHeadH2k.getModifyTimes());// 修改次数
							e.setChangeDate(new Date());
							e.setCompany(CommonUtils.getCompany());

							emsBomMap.put(key, e);
							// 记录保存料件
							bomSaveList.add(e);

							newCount++;
						} else {
							nochangeCount++;
						}
					}
				} else { // 变更
					if (m != null) {// 存在该料件
						if ((!e.getUnitWear().equals(m.getUnitWear()) || !e
								.getWear().equals(m.getWear()))
								&& m.getModifyMark().equals("3")) {
							if (cbIsOverwrite) {
								m.setUnitWear(e.getUnitWear());
								m.setWear(e.getWear());

								// 记录保存料件
								bomSaveList.add(m);
							}
							changeCount++;
						} else {
							nochangeCount++;
						}
					} else {
						img = imgMap.get(imgSeqNum);

						if (img != null) { // 料件表里面存在该料件
							e.setEmsHeadH2kVersion(v);
							e.setName(img.getName());
							e.setComplex(img.getComplex());
							e.setSpec(img.getSpec());
							e.setUnit(img.getUnit());
							e.setPrice(img.getDeclarePrice());
							e.setModifyMark(ModifyMarkState.ADDED);// 新增
							if (isEmsH2kSendSign != null
									&& "1".equals(isEmsH2kSendSign)) {
								e.setSendState(Integer
										.valueOf(SendState.WAIT_SEND));
							}
							e.setModifyTimes(emsHeadH2k.getModifyTimes());// 修改次数
							e.setChangeDate(new Date());
							e.setCompany(CommonUtils.getCompany());

							emsBomMap.put(key, e);
							// 记录保存料件
							bomSaveList.add(e);
							newCount++;
						} else {
							nochangeCount++;
						}
					}
				}
			} else { // 不存在版本
				v = new EmsHeadH2kVersion();
				v.setEmsHeadH2kExg(exg);
				v.setVersion(version);
				v.setName("版本：" + String.valueOf(version));
				v.setCompany(CommonUtils.getCompany());

				versionMap.put(vkey, v);
				versionSaveList.add(v);

				img = imgMap.get(imgSeqNum);

				if (img != null) { // 料件表里面存在该料件
					e.setEmsHeadH2kVersion(v);
					e.setName(img.getName());
					e.setComplex(img.getComplex());
					e.setSpec(img.getSpec());
					e.setUnit(img.getUnit());
					e.setPrice(img.getDeclarePrice());
					e.setModifyMark(ModifyMarkState.ADDED);
					if (isEmsH2kSendSign != null
							&& "1".equals(isEmsH2kSendSign)) {
						e.setSendState(Integer.valueOf(SendState.WAIT_SEND));
					}
					e.setModifyTimes(emsHeadH2k.getModifyTimes());// 修改次数
					e.setChangeDate(new Date());

					emsBomMap.put(key, e);
					bomSaveList.add(e);
					noversionCount++;
				} else {
					nochangeCount++;
				}
			}
		}
		int[] x = new int[6];
		x[0] = noImportCount;
		x[1] = changeCount;
		x[2] = newCount;
		x[3] = noversionCount;
		x[4] = totalcount;
		x[5] = nochangeCount;

		return new Object[] { x, versionSaveList, bomSaveList };
	}

	/*
	 * 1、 准备辅助数据：验证emsbom数据、验证emsExg数据、验证emsExg数据、验证emsExg数据
	 */
	private void initAssistDataForSaveToEmsHeadH2k(
			List<EmsEdiHeadH2kBomFrom> list, EmsHeadH2k emsHeadH2k,
			Map<String, EmsHeadH2kBom> emsBomMap,
			Map<Integer, EmsHeadH2kExg> exgMap,
			Map<String, EmsHeadH2kVersion> versionMap,
			Map<Integer, EmsHeadH2kImg> imgMap) {
		/*
		 * 1、查询电子帐册bom表用来和导入bom做对比，转化为map，key=成品序号+版本号+料件序号
		 */
		int size = list.size();

		// 1.1 获取有bom导入的成品序号和料件序号
		List<Integer> seqNumList = new ArrayList<Integer>();

		List<Integer> imgSeqNumList = new ArrayList<Integer>();

		// 成品序号
		Integer seqNum = null;

		// 料件序号
		Integer imgSeqNum = null;

		EmsEdiHeadH2kBomFrom bomFrom = null;

		for (int i = 0; i < size; i++) {

			bomFrom = list.get(i);

			// 记录成品序号
			seqNum = bomFrom.getSeqNum();

			if (!seqNumList.contains(seqNum)) {
				seqNumList.add(seqNum);
			}

			// 记录料件序号
			imgSeqNum = bomFrom.getBom().getSeqNum();

			if (!imgSeqNumList.contains(imgSeqNum)) {
				imgSeqNumList.add(imgSeqNum);
			}
		}

		// 1.2 查询已知成品序号得下的所有版本bom；
		List<EmsHeadH2kBom> emsBomList = emsEdiTrDao
				.findEmsHeadH2kBomByProduceSeqNum(seqNumList, emsHeadH2k);

		// 1.3 把emsBomList转化为emsBomMap, 同时获versionMap
		String key = null;

		EmsHeadH2kBom b = null;

		for (int i = 0; i < emsBomList.size(); i++) {
			b = emsBomList.get(i);
			// 料件序号 + 版本号 + 成品序号
			key = b.getSeqNum() + "/" + b.getEmsHeadH2kVersion().getVersion()
					+ "/" + b.getEmsHeadH2kExg().getSeqNum();

			emsBomMap.put(key, b);
		}

		/*
		 * 2、查询电子帐册成品表用来和导入成品做对比，转化为map，key=成品序号
		 */
		// 2.1 查询电子帐册成品
		List<EmsHeadH2kExg> emsExgList = emsEdiTrDao.findEmsHeadH2kExgBySeqNum(
				emsHeadH2k, seqNumList);

		// 2.2 把emsExgList转化为exgMap
		for (int i = 0; i < emsExgList.size(); i++) {
			exgMap.put(emsExgList.get(i).getSeqNum(), emsExgList.get(i));
		}

		/*
		 * 3、查询电子帐册料件表用来和导入料件做对比，转化为map，key=料件序号
		 */
		// 3.1 查询电子帐册料件
		List<EmsHeadH2kImg> emsImgList = emsEdiTrDao.findEmsHeadH2kImgBySeqNum(
				emsHeadH2k, imgSeqNumList);

		// 3.2 把emsExgList转化为exgMap
		for (int i = 0; i < emsImgList.size(); i++) {
			imgMap.put(emsImgList.get(i).getSeqNum(), emsImgList.get(i));
		}

		/*
		 * 4、查询已知成品序号下的电子帐册所有版本号用来和导入bomVersion做对比，转化为map，key=成品序号+版本号
		 */
		// 4.1 查询已知成品序号下的电子帐册所有版本号
		List<EmsHeadH2kVersion> versionList = emsEdiTrDao
				.findEmsHeadH2kVersionByProduceSeqNum(emsHeadH2k, seqNumList);

		// 4.2 把versionList转化为exgMap
		String vkey = null;
		EmsHeadH2kVersion v = null;
		for (int i = 0; i < versionList.size(); i++) {
			v = versionList.get(i);

			// 版本号 + 成品序号
			vkey = v.getVersion() + "/" + v.getEmsHeadH2kExg().getSeqNum();

			if (!versionMap.containsKey(vkey)) {
				versionMap.put(vkey, v);
			}
		}
	}

	// -----------------------------------------------------------------

	/**
	 * 保存BCUS参数设定
	 */
	public void saveBpara(int type, String sValue) {
		BcusParameter obj = null;
		List list = this.emsEdiTrDao.getBparaList(type);
		if (list != null && list.size() > 0) {
			obj = (BcusParameter) list.get(0);
		} else {
			obj = new BcusParameter();
			obj.setType(type);
			obj.setCompany(CommonUtils.getCompany());
		}
		obj.setStrValue(sValue);
		this.emsEdiTrDao.saveBcusParameter(obj);
	}

	/**
	 * 临时使用，改变归并状态
	 */
	public void changeMergerflag() {
		List list = this.emsEdiTrDao.findEmsEdiMergerExgBeforeAll();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list.get(i);
			String ptNo = obj.getPtNo();
			List ls = this.emsEdiTrDao.findInnerMergeDataByTenNoPtNo(
					MaterielType.FINISHED_PRODUCT, obj
							.getEmsEdiMergerExgAfter().getSeqNum(), ptNo);
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setIsExistMerger(new Boolean(true));
				this.emsEdiTrDao.saveInnerMergeData(data);
			}
		}

		list = this.emsEdiTrDao.findEmsEdiMergerImgBeforeAll();
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list.get(i);
			String ptNo = obj.getPtNo();
			List ls = this.emsEdiTrDao.findInnerMergeDataByTenNoPtNo(
					MaterielType.MATERIEL, obj.getEmsEdiMergerImgAfter()
							.getSeqNum(), ptNo);
			for (int j = 0; j < ls.size(); j++) {
				InnerMergeData data = (InnerMergeData) ls.get(j);
				data.setIsExistMerger(new Boolean(true));
				this.emsEdiTrDao.saveInnerMergeData(data);
			}
		}
	}

	/**
	 * 修改对应关系状态
	 * 
	 * @param type
	 * @param seqNum
	 * @param ptNo
	 */
	public void editMergerFlag(String type, Integer seqNum, String ptNo) {
		List list = emsEdiTrDao.findInnerMergeDataByTenNoPtNo(type, seqNum,
				ptNo);
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			data.setIsExistMerger(new Boolean(false));
			emsEdiTrDao.saveInnerMergeData(data);
		}
	}

	/**
	 * 修改归并前料件
	 * 
	 * @param emsAfter
	 */
	public void editImgBeforeList(EmsEdiMergerImgAfter emsAfter) {
		List list = this.emsEdiTrDao.findEmsEdiMergerImgBefore(emsAfter);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list.get(i);
			editMergerFlag(MaterielType.MATERIEL, obj.getEmsEdiMergerImgAfter()
					.getSeqNum(), obj.getPtNo());
		}
	}

	/**
	 * 修改归并前成品
	 * 
	 * @param emsAfter
	 */
	public void editExgBeforeList(EmsEdiMergerExgAfter emsAfter) {
		List list = this.emsEdiTrDao.findEmsEdiMergerExgBefore(emsAfter);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list.get(i);
			editMergerFlag(MaterielType.FINISHED_PRODUCT, emsAfter.getSeqNum(),
					obj.getPtNo());
		}
	}

	/**
	 * 修改版本
	 * 
	 * @param head
	 */
	public void editversion(EmsHeadH2k head) {
		List list = this.emsEdiTrDao.findEmsHeadH2kVersionAll(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kVersion obj = (EmsHeadH2kVersion) list.get(i);
			obj.setFactoryPrice(obj.getEmsHeadH2kExg().getFactoryPrice());
			obj.setUnitGrossWeight(obj.getEmsHeadH2kExg().getUnitGrossWeight());
			obj.setUnitNetWeight(obj.getEmsHeadH2kExg().getUnitNetWeight());
			obj.setBeginDate(obj.getEmsHeadH2kExg().getBeginDate());
			obj.setEndDate(obj.getEmsHeadH2kExg().getEndDate());
			this.emsEdiTrDao.saveEmsHeadH2kVersion(obj);
		}
	}

	/**
	 * 获取单耗比较
	 * 
	 * @param exglist
	 * @param head
	 * @param exgSeqNum
	 * @param version
	 * @return
	 */
	public List findBomUnitWearCompare(List exglist, EmsEdiMergerHead head,
			Integer exgSeqNum, Integer version) {
		Hashtable<Integer, EmsEdiHeadH2kBomFrom> hs = new Hashtable<Integer, EmsEdiHeadH2kBomFrom>();
		for (int j = 0; j < exglist.size(); j++) {
			EmsHeadH2kBom emsbom = (EmsHeadH2kBom) exglist.get(j);
			EmsEdiHeadH2kBomFrom obj = new EmsEdiHeadH2kBomFrom();
			obj.setBom(emsbom);
			hs.put(emsbom.getSeqNum(), obj);
		}

		List list = this.emsEdiTrDao.findEmsEdiMergerBomBySeqNum(head,
				exgSeqNum, version);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) list.get(i);
			String ljptNo = bom.getPtNo();
			InnerMergeData inner = this.emsEdiTrDao
					.findInnerMergeSeqNumByTenNoPtNo(MaterielType.MATERIEL,
							ljptNo);
			if (inner == null) {
				continue;
			}
			Integer tenSeqNum = inner.getHsAfterTenMemoNo();
			if (hs.get(tenSeqNum) != null) {
				EmsEdiHeadH2kBomFrom bomfrom = (EmsEdiHeadH2kBomFrom) hs
						.get(tenSeqNum);
				bomfrom.setUnitWear((bomfrom.getUnitWear() == null ? Double
						.valueOf(0) : bomfrom.getUnitWear())
						+ (bom.getUnitWaste() == null ? Double.valueOf(0) : bom
								.getUnitWaste()));
			}
		}
		ArrayList returnList = new ArrayList();

		TreeMap ts = new TreeMap();
		Enumeration e = hs.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			ts.put(new Integer(String.valueOf(key)), key);
		}
		Set st = ts.keySet();
		for (Iterator i = st.iterator(); i.hasNext();) {
			returnList.add(hs.get(ts.get(i.next())));
		}
		return returnList;
	}

	/**
	 * 初始化申报状态
	 * 
	 * @param head
	 */
	public void initSendState(EmsEdiMergerHead head) {
		// 料件归并后
		List list = emsEdiTrDao.findEmsEdiMergerImgAfter(head);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgAfter obj = (EmsEdiMergerImgAfter) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsEdiMergerImgAfter(obj);
		}
		// 成品归并后
		list = emsEdiTrDao.findEmsEdiMergerExgAfter(head);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgAfter obj = (EmsEdiMergerExgAfter) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsEdiMergerExgAfter(obj);
		}

		// 料件归并前
		list = emsEdiTrDao.findEmsEdiMergerImgBefore(head);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerImgBefore obj = (EmsEdiMergerImgBefore) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsEdiMergerImgBefore(obj);
		}
		// 成品归并前
		list = emsEdiTrDao.findEmsEdiMergerExgBefore(head);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBefore obj = (EmsEdiMergerExgBefore) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsEdiMergerExgBefore(obj);
		}
		// 成品归并BOM
		list = emsEdiTrDao.findEmsEdiMergerBom(head);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsEdiMergerExgBom(obj);
		}

	}

	/**
	 * 初始化申报状态
	 * 
	 * @param head
	 */
	public void initEmsSendState(EmsHeadH2k head) {
		// 料件
		List list = emsEdiTrDao.findEmsHeadH2kImg(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg obj = (EmsHeadH2kImg) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsHeadH2kImg(obj);
		}
		// 成品
		list = emsEdiTrDao.findEmsHeadH2kExg(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kExg obj = (EmsHeadH2kExg) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsHeadH2kExg(obj);
		}

		// 成品BOM
		list = emsEdiTrDao.findEmsHeadH2kBom(head);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom obj = (EmsHeadH2kBom) list.get(i);
			if (obj.getModifyMark() != null
					&& obj.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				obj.setSendState(Integer.valueOf(SendState.SEND));
			} else {
				obj.setSendState(Integer.valueOf(SendState.NOT_SEND));
			}
			emsEdiTrDao.saveEmsHeadH2kBom(obj);
		}

	}

	/**
	 * 修改归并后商品编码自动修改归并前商品编码
	 * 
	 * @param imgafter
	 * @param scomplex
	 * @param ncomplex
	 * @param sunit
	 * @param nunit
	 */
	public void changeBeforeComplexUnit(EmsEdiMergerImgAfter imgafter,
			String scomplex, String ncomplex, String sunit, String nunit,
			boolean isMainIsChange) {
		long beginTime = System.currentTimeMillis();
		if (!scomplex.equals(ncomplex) || !sunit.equals(nunit)
				|| isMainIsChange) {
			// List list = emsEdiTrDao.findEmsEdiMergerImgBefore(imgafter);//
			// 将归并前更新
			// for (int i = 0; i < list.size(); i++) {
			// EmsEdiMergerImgBefore img = (EmsEdiMergerImgBefore) list.get(i);
			// if (img.getComplex() == null && imgafter.getComplex() != null
			// || img.getComplex() != null
			// && imgafter.getComplex() == null
			// || img.getComplex() != null
			// && imgafter.getComplex() != null
			// && !img.getComplex().equals(imgafter.getComplex())
			// || img.getUnit() == null && imgafter.getUnit() != null
			// || img.getUnit() != null && imgafter.getUnit() == null
			// || img.getUnit() != null && imgafter.getUnit() != null
			// && !img.getUnit().equals(imgafter.getUnit())) {
			// img.setComplex(imgafter.getComplex());// 修改商品编码
			// img.setUnit(imgafter.getUnit());// 修改报关单位
			// if (img.getModifyMark() != null
			// && img.getModifyMark().equals(
			// ModifyMarkState.UNCHANGE)) {
			// img.setModifyMark(ModifyMarkState.MODIFIED);
			// img.setSendState(Integer.parseInt(SendState.WAIT_SEND));
			// }
			// emsEdiTrDao.saveOrUpdateDirect(img);
			// }
			// 修改BOM
			// List bomls = this.emsEdiTrDao.findEmsEdiMergerBomByPtNo(
			// imgafter.getEmsEdiMergerHead(), img.getPtNo());
			// for (int k = 0; k < bomls.size(); k++) {
			// EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) bomls.get(k);
			// if (bom.getComplex() == null
			// && imgafter.getComplex() != null
			// || bom.getComplex() != null
			// && imgafter.getComplex() == null
			// || bom.getComplex() != null
			// && imgafter.getComplex() != null
			// && !bom.getComplex().equals(imgafter.getComplex())
			// || bom.getUnit() == null
			// && imgafter.getUnit() != null
			// || bom.getUnit() != null
			// && imgafter.getUnit() == null
			// || bom.getUnit() != null
			// && imgafter.getUnit() != null
			// && !bom.getUnit().equals(imgafter.getUnit())) {
			// bom.setComplex(imgafter.getComplex());
			// bom.setUnit(imgafter.getUnit());
			// this.emsEdiTrDao.saveEmsEdiMergerExgBom(bom);
			// }
			// }
			// }
			this.emsEdiTrDao
					.updateEmsEdiMergerImgBeforeComplexEqualsImgAfter(imgafter);
			if (!scomplex.equals(ncomplex) || !sunit.equals(nunit)) {
				this.emsEdiTrDao
						.updateEmsEdiMergerExgBomComplexEqualsImgAfter(imgafter);
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("-------------" + (endTime - beginTime) / 1000.0
				+ "秒");
	}

	/**
	 * 修改归并后成品
	 * 
	 * @param exgafter
	 * @param scomplex
	 * @param ncomplex
	 * @param sunit
	 * @param nunit
	 */
	public void changeBeforeComplexUnit(EmsEdiMergerExgAfter exgafter,
			String scomplex, String ncomplex, String sunit, String nunit) {
		List list = emsEdiTrDao.findEmsEdiMergerExgBefore(exgafter);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBefore exg = (EmsEdiMergerExgBefore) list.get(i);
			exg.setComplex(exgafter.getComplex());// 修改商品编码
			exg.setUnit(exgafter.getUnit());// 修改报关单位

			if (exg.getModifyMark() != null
					&& exg.getModifyMark().equals(ModifyMarkState.UNCHANGE)) {
				exg.setModifyMark(ModifyMarkState.MODIFIED);
				exg.setSendState(Integer.parseInt(SendState.WAIT_SEND));
			}

			emsEdiTrDao.saveEmsEdiMergerExgBefore(exg);
		}
		// this.emsEdiTrDao
		// .updateEmsEdiMergerExgBeforeComplexEqualsExgAfter(exgafter);
	}

	/**
	 * 计算自定义成品单价
	 * 
	 * @param exg
	 * @param version
	 * @param a
	 * @param b
	 * @return
	 */
	public EmsHeadH2kExg amountExgDefinePrice(EmsHeadH2kExg exg,
			EmsHeadH2kVersion version, Double a, Double b) {
		a = (a == null) ? Double.valueOf(0) : a;
		b = (b == null) ? Double.valueOf(0) : b;
		Double sd = Double.valueOf(0);
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);// 分批申报
		List list = emsEdiTrDao.findEmsHeadH2kBom(version);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) list.get(i);
			Double unitWear = bom.getUnitWear() == null ? Double.valueOf(0)
					: bom.getUnitWear();// 单耗
			Double wear = bom.getWear() == null ? Double.valueOf(0) : bom
					.getWear(); // 损耗
			Double price = bom.getPrice() == null ? Double.valueOf(0) : bom
					.getPrice(); // 单价
			Double unitDosage = wear.equals(Double.valueOf(100.0)) ? Double
					.valueOf(0) : (unitWear / (1.0 - (wear * 0.01)));
			sd = sd + (unitDosage * price);
		}
		sd = sd * (1 + (a / 100)) + b;
		exg.setDeclarePrice(sd);
		if (!exg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !exg.getModifyMark().equals(ModifyMarkState.ADDED)
				&& exg.getEmsHeadH2k().getDeclareType()
						.equals(DelcareType.MODIFY)) {
			exg.setModifyMark(ModifyMarkState.MODIFIED);
			if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)) {
				exg.setSendState(1);// 准备申报
			}
			exg.setModifyTimes(exg.getEmsHeadH2k().getModifyTimes());// 变更次数
			exg.setChangeDate(new Date());
		}
		emsEdiTrDao.saveEmsHeadH2kExg(exg);
		return exg;
	}

	/**
	 * 默认计算成品单价
	 * 
	 * @param exg
	 * @param version
	 * @return
	 */
	public EmsHeadH2kExg defaultAmountPrice(EmsHeadH2kExg exg,
			EmsHeadH2kVersion version) {
		Double sd = Double.valueOf(0);
		String isEmsH2kSendSign = emsEdiTrDao
				.getBpara(BcusParameter.EmsEdiH2kSend_Sign);// 分批申报
		List list = emsEdiTrDao.findEmsHeadH2kBom(version);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) list.get(i);
			Double unitWear = bom.getUnitWear() == null ? Double.valueOf(0)
					: bom.getUnitWear();// 单耗
			Double wear = bom.getWear() == null ? Double.valueOf(0) : bom
					.getWear(); // 损耗
			Double price = bom.getPrice() == null ? Double.valueOf(0) : bom
					.getPrice(); // 单价
			Double unitDosage = wear.equals(Double.valueOf(100.0)) ? Double
					.valueOf(0) : (unitWear / (1.0 - (wear * 0.01)));
			sd = sd + (unitDosage * price);
		}
		exg.setDeclarePrice(sd);
		if (!exg.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !exg.getModifyMark().equals(ModifyMarkState.ADDED)
				&& exg.getEmsHeadH2k().getDeclareType()
						.equals(DelcareType.MODIFY)) {
			exg.setModifyMark(ModifyMarkState.MODIFIED);
			if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)) {
				exg.setSendState(1);// 准备申报
			}
			exg.setModifyTimes(exg.getEmsHeadH2k().getModifyTimes());// 变更次数
			exg.setChangeDate(new Date());
		}
		emsEdiTrDao.saveEmsHeadH2kExg(exg);
		return exg;
	}

	/**
	 * 计算工厂单价
	 * 
	 * @param list
	 */
	public void amountFactoryPrice(List list) {
		Date beginDate = new Date();
		String sbeginDate = dateToDate(beginDate);
		String ds = String
				.valueOf(Integer.parseInt(sbeginDate.substring(0, 4)) + 1)
				+ sbeginDate.substring(4, sbeginDate.length());
		Date endDate = strToStandDate(ds);

		Double increRate = Double.valueOf(1);
		String incrementRate = emsEdiTrDao
				.getBpara(BcusParameter.INCREMENT_RATE);
		if (incrementRate != null && !incrementRate.equals("")) {
			increRate = Double.valueOf(incrementRate);
		}
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(i);
			List aList = this.emsEdiTrDao.findEmsHeadH2kVersion(exg);
			for (int j = 0; j < aList.size(); j++) {
				EmsHeadH2kVersion vobj = (EmsHeadH2kVersion) aList.get(j);
				List bomlist = emsEdiTrDao.findEmsHeadH2kBom(vobj);
				Double sd = Double.valueOf(0);
				for (int k = 0; k < bomlist.size(); k++) {
					EmsHeadH2kBom bom = (EmsHeadH2kBom) bomlist.get(k);
					Double unitWear = bom.getUnitWear() == null ? Double
							.valueOf(0) : bom.getUnitWear();// 单耗
					Double price = bom.getPrice() == null ? Double.valueOf(0)
							: bom.getPrice(); // 单价
					sd = sd + (unitWear * price);
				}
				vobj.setFactoryPrice(sd / increRate);
				vobj.setBeginDate(beginDate);
				vobj.setEndDate(endDate);
				this.emsEdiTrDao.saveEmsHeadH2kVersion(vobj);
			}
		}
	}

	/**
	 * 将统计出来的成品单价反写到电子帐册成品单价中去
	 * 
	 * @param list
	 * @param head
	 */
	public void rewriteEmsHeadH2k(List list, EmsHeadH2k head) {
		Date d = new Date();
		String sd = dateToDate(d);
		String ds = String.valueOf(Integer.parseInt(sd.substring(0, 4)) + 1)
				+ sd.substring(4, sd.length());
		Date s = strToStandDate(ds);

		String emsbomprice = emsEdiTrDao.getBpara(BcusParameter.EMS_BOM_PRICE);

		Integer bomprice = Integer.valueOf(2);
		if (emsbomprice != null && !emsbomprice.equals("")) {
			try {
				bomprice = Integer.valueOf(emsbomprice);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		iniHashVersion(head);

		EmsUnitWear seqObj = null;
		EmsUnitWear verObj = null;
		EmsUnitWear cpPrice = null;

		for (int i = 0; i < list.size(); i++) {
			EmsUnitWear obj = (EmsUnitWear) list.get(i);
			if (obj.getName() != null && obj.getName().equals("成品序号")) {
				seqObj = obj;
			} else if (obj.getName() != null && obj.getName().equals("版本号")) {
				verObj = obj;
			} else if (obj.getName() != null && obj.getName().equals("成品单价")) {
				cpPrice = obj;
			}
		}
		String cpSeqNum = seqObj.getDm12();
		String cpVersion = verObj.getDm12();
		String sPrice = cpPrice.getDm14();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm22();
		cpVersion = verObj.getDm22();
		sPrice = cpPrice.getDm24();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm32();
		cpVersion = verObj.getDm32();
		sPrice = cpPrice.getDm34();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm42();
		cpVersion = verObj.getDm42();
		sPrice = cpPrice.getDm44();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm52();
		cpVersion = verObj.getDm52();
		sPrice = cpPrice.getDm54();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm62();
		cpVersion = verObj.getDm62();
		sPrice = cpPrice.getDm64();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm72();
		cpVersion = verObj.getDm72();
		sPrice = cpPrice.getDm74();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm82();
		cpVersion = verObj.getDm82();
		sPrice = cpPrice.getDm84();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm92();
		cpVersion = verObj.getDm92();
		sPrice = cpPrice.getDm94();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
		// ---------------------------------------------------------
		cpSeqNum = seqObj.getDm102();
		cpVersion = verObj.getDm102();
		sPrice = cpPrice.getDm104();
		saveEmsHeadH2kVersionByPrice(cpSeqNum, cpVersion, sPrice, d, s, head,
				bomprice);
	}

	/**
	 * 保存平均单价
	 * 
	 * @param cpSeqNum
	 * @param cpVersion
	 * @param sPrice
	 * @param beginDate
	 * @param endDate
	 * @param head
	 * @param pricelen
	 */
	private void saveEmsHeadH2kVersionByPrice(String cpSeqNum,
			String cpVersion, String sPrice, Date beginDate, Date endDate,
			EmsHeadH2k head, int pricelen) {
		if (cpSeqNum == null || cpSeqNum.trim().equals("")) {
			return;
		}
		cpSeqNum = cpSeqNum.substring(3, cpSeqNum.length());
		cpVersion = cpVersion.substring(3, cpVersion.length());
		EmsHeadH2kVersion obj = (EmsHeadH2kVersion) iniHashVersion(head).get(
				cpSeqNum + "/" + cpVersion);
		if (obj != null) {
			Double price = (sPrice == null ? Double.valueOf(0) : Double
					.valueOf(sPrice));
			obj.setFactoryPrice(formatBigD(price, pricelen));// 保留一位小数点
			obj.setBeginDate(beginDate);
			obj.setEndDate(endDate);
		}
		this.emsEdiTrDao.saveEmsHeadH2kVersion(obj);
	}

	/**
	 * 格式化
	 * 
	 * @param amount
	 * @param bigD
	 * @return
	 */
	public Double formatBigD(Double amount, int bigD) {
		if (amount == null) {
			return Double.valueOf(0);
		}

		int ws = (amount + "").length() - (amount + "").indexOf(".");
		if (ws > bigD) {
			amount = amount + (Math.pow(0.1, ws));
		}

		BigDecimal bd = new BigDecimal(amount);
		String amountStr = bd.setScale(bigD, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(amountStr);
	}

	/**
	 * 格式化
	 * 
	 * @param amount
	 * @param bigD
	 * @return
	 */
	private String dateToDate(Date date1) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str1 = formater.format(date1);
		return str1;
	}

	/**
	 * 格式化
	 * 
	 * @param amount
	 * @param bigD
	 * @return
	 */
	private Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成成品BOM
	 * 
	 * @param amount
	 * @param bigD
	 * @return
	 */
	public List makeEmsEdiMergerExgBomWasteToInt(List list) {
		for (int k = 0; k < list.size(); k++) {
			EmsEdiMergerExgBom ebom = (EmsEdiMergerExgBom) list.get(k);
			ebom.setWaste(Double.valueOf(String.valueOf(Math.round(ebom
					.getWaste() == null ? 0.0 : ebom.getWaste()))));
			this.emsEdiTrDao.saveOrUpdate(ebom);
		}
		return list;
	}

	/**
	 * 删除没有料件的版本资料
	 * 
	 */
	public void deleteEmsHeadH2kVersionWhileNoImg(EmsHeadH2k emsHeadH2k) {
		List lsAllVersion = this.emsEdiTrDao
				.findEmsHeadH2kVersionAll(emsHeadH2k);
		List lsBomVersion = this.emsEdiTrDao
				.findEmsHeadH2kVersionAllFromBom(emsHeadH2k);
		for (int i = lsAllVersion.size() - 1; i >= 0; i--) {
			EmsHeadH2kVersion version = (EmsHeadH2kVersion) lsAllVersion.get(i);
			if (!lsBomVersion.contains(version)) {
				this.emsEdiTrDao.deleteEmsHeadH2kVersion(version);
			}
		}
	}

	/**
	 * 判断是否有空的版本
	 * 
	 * @param base
	 * @return
	 */
	public Boolean isExistNullEmsHeadH2kVersion(EmsHeadH2k emsHeadH2k) {
		List lsAllVersion = this.emsEdiTrDao
				.findEmsHeadH2kVersionAll(emsHeadH2k);
		List lsBomVersion = this.emsEdiTrDao
				.findEmsHeadH2kVersionAllFromBom(emsHeadH2k);
		for (int i = lsAllVersion.size() - 1; i >= 0; i--) {
			EmsHeadH2kVersion version = (EmsHeadH2kVersion) lsAllVersion.get(i);
			if (!lsBomVersion.contains(version)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查报关单数量
	 * 
	 * @param base
	 * @return
	 */
	public boolean checkCustomssDeclarationCount(BaseCustomsDeclaration base) {
		String result = "";
		if (base.getImpExpType() != ImpExpType.REWORK_EXPORT) {
			return true;
		}
		if (base instanceof CustomsDeclaration) {
			// --------------------------------------------------------------------------
			List inlist = this.emsEdiTrDao
					.findCustomsDeclarationCountByImpexpType(ImpExpType.BACK_FACTORY_REWORK);
			Map inmap = new HashMap();
			// key = 商品序号+@+版本号
			String key = "";
			for (int i = 0; i < inlist.size(); i++) {
				Object[] obs = (Object[]) inlist.get(i);
				if (obs[0] == null || obs[1] == null || obs[2] == null) {
					continue;
				} else {
					key = obs[0].toString() + "@" + obs[2].toString();
					inmap.put(key, obs[1]);
				}
			}
			// --------------------------------------------------------------------------
			Map outmap = new HashMap();
			List outlist = this.emsEdiTrDao
					.findCustomsDeclarationCountByImpexpType(base.getId(),
							ImpExpType.REWORK_EXPORT);
			for (int i = 0; i < outlist.size(); i++) {
				Object[] obs = (Object[]) outlist.get(i);
				if (obs != null) {
					if (obs[0] == null || obs[1] == null || obs[2] == null) {
						continue;
					} else {
						key = obs[0].toString() + "@" + obs[2].toString();
						outmap.put(key, obs[1]);
					}
				}
			}
			// --------------------------------------------------------------------------
			List nowlist = this.emsEdiTrDao
					.findCustomsDeclarationCountById(base.getId());
			if (nowlist.size() == 0) {
				result += "报关单中没有商品！";
				return false;
			}
			for (int i = 0; i < nowlist.size(); i++) {
				Object[] obs = (Object[]) nowlist.get(i);
				if (obs[0] == null) {
					result += "报关单中商品序号有为空！";
					return false;
				} else {
					Integer seq = (Integer) obs[0];
					String version = (String) obs[2];
					key = seq + "@" + version;
					if (key != null) {
						Double in = inmap.get(key) == null ? 0.0
								: (Double) inmap.get(key);
						Double out = outmap.get(key) == null ? 0.0
								: (Double) outmap.get(key);
						Double dif = in - out;
						Double now = (Double) obs[1];
						System.out.println(seq + "=============" + dif);
						if (dif < 0) {
							result += "商品序号为: " + seq + "版本号为" + version
									+ "的成品,\n退厂返工总数量为：" + in + "\n返工复出数量为："
									+ out + "\n可返工复出量为： " + dif + "\n报关单中数量为："
									+ now + "\n数量不符合要求，不能生效！\n";
							result += "----------------------------------------------------\n";
						}
					}

				}
			}
			// --------------------------------------------------------------------------
		}
		if (!result.equals("")) {
			throw new RuntimeException(result);
		}
		return true;
	}

	/**
	 * 查找成品BOM
	 * 
	 * @param head
	 * @param beginSeqNum
	 * @param endSeqNum
	 * @param Bomversion
	 * @return
	 */
	public List findBomExport(EmsEdiMergerHead head, String beginSeqNum,
			String endSeqNum, String Bomversion) {
		List tempList = new ArrayList();
		List list = emsEdiTrDao.findBomExport(head, beginSeqNum, endSeqNum,
				Bomversion);
		for (int i = 0; i < list.size(); i++) {
			EmsEdiMergerExgBom obj = (EmsEdiMergerExgBom) list.get(i);
			BillTemp bill = new BillTemp();
			Integer seqNum = emsEdiTrDao.findBomExportSeqnum(head,
					obj.getPtNo());
			bill.setBill10(String.valueOf(obj.getEmsEdiMergerVersion()
					.getEmsEdiMergerBefore().getSeqNum()));
			bill.setBill11(obj.getEmsEdiMergerVersion().getEmsEdiMergerBefore()
					.getPtNo());
			bill.setBill12(String.valueOf(obj.getEmsEdiMergerVersion()
					.getVersion()));
			bill.setBill9(obj.getEmsEdiMergerVersion().getCmpVersion());
			bill.setBill13(obj.getEmsEdiMergerVersion().getEmsEdiMergerBefore()
					.getNameSpec());
			if (seqNum != null) {
				bill.setBill14(String.valueOf(seqNum));
			}
			bill.setBill15(obj.getPtNo());
			bill.setBill16(obj.getNameSpec());
			bill.setBill17(CommonUtils.formatDoubleByDigit(obj.getUnitWaste(),
					9));
			bill.setBill18(String.valueOf(obj.getWasteL()));
			tempList.add(bill);
		}
		Collections.sort(tempList, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				BillTemp b1 = (BillTemp) o1;
				BillTemp b2 = (BillTemp) o2;
				// 成品序号，版本号，料件序号
				String stu1 = b1.getBill10() + b1.getBill12() + b1.getBill14();
				String stu2 = b2.getBill10() + b2.getBill12() + b2.getBill14();
				return Long.valueOf(stu1).compareTo(Long.valueOf(stu2));
				// return stu1.compareTo(stu2);
			}

		});
		return tempList;

	}

	/**
	 * 成品BOM复制到操作
	 * 
	 * @param oldList
	 *            要保存的复制BOM
	 * @return 返回复制的BOM列表
	 */
	public List<EmsEdiMergerExgBom> newAndCope(
			List<EmsEdiMergerExgBom> oldList,
			EmsEdiMergerVersion emsEdiMergerVersion) {
		List<EmsEdiMergerExgBom> newList = new ArrayList<EmsEdiMergerExgBom>();
		for (EmsEdiMergerExgBom ems : oldList) {
			EmsEdiMergerExgBom bom = new EmsEdiMergerExgBom();
			try {
				PropertyUtils.copyProperties(bom, ems);
			} catch (Exception e2) {

			}
			bom.setId(null);
			bom.setEmsEdiMergerVersion(emsEdiMergerVersion);
			bom.setModifyMark(ModifyMarkState.ADDED);
			bom.setSendState(1);
			emsEdiTrDao.saveOrUpdate(bom);
			newList.add(bom);
		}
		return newList;
	}

	/**
	 * 检查当成品及料件的备案单位都为千克时，单耗累加值不能大于1
	 * 
	 * @param request
	 *            请求控制
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return
	 */
	public String checkEmsHeadH2kUnitWasteAdd(EmsHeadH2k emsHeadH2k) {
		String result = "";
		List emsHeadH2kBomList = emsEdiTrDao
				.findEmsHeadH2kBomUnitWearTotal(emsHeadH2k);
		for (int i = 0; i < emsHeadH2kBomList.size(); i++) {
			Object[] objs = (Object[]) emsHeadH2kBomList.get(i);
			Integer seqNum = (Integer) objs[0];
			Integer version = (Integer) objs[1];
			Double unitWearTotal = (Double) objs[2];
			if (unitWearTotal > 1) {
				result += "商品序号为：[" + seqNum + "]，版本号为：[" + version
						+ "]的成品单耗累加值大于1！\n";
			}
		}
		return result;
	}

	public Map<Integer, Map<Integer, EmsHeadH2kBom>> getEmsHeadH2kBomByExgSeqNum(
			EmsHeadH2k emsHeadH2k, Integer exgSeqNum) {
		long begin = System.currentTimeMillis();

		Map<Integer, Map<Integer, EmsHeadH2kBom>> map = new HashMap<Integer, Map<Integer, EmsHeadH2kBom>>();
		List<EmsHeadH2kBom> list = this.emsEdiTrDao
				.findEmsHeadH2kBomByProduceSeqNum(exgSeqNum, emsHeadH2k);
		for (EmsHeadH2kBom h2kBom : list) {
			Map<Integer, EmsHeadH2kBom> mapBom = map.get(h2kBom
					.getEmsHeadH2kVersion().getVersion());
			if (mapBom == null) {
				mapBom = new HashMap<Integer, EmsHeadH2kBom>();
				map.put(h2kBom.getEmsHeadH2kVersion().getVersion(), mapBom);
			}
			mapBom.put(h2kBom.getSeqNum(), h2kBom);
		}
		long end = System.currentTimeMillis();

		System.out.println(" time " + (end - begin) / 1000.0);

		return map;
	}

	// /**
	// * 根据现有bom 查询bom key map
	// * @param list
	// * @param emsHeadH2k
	// * @return
	// */
	// public Map<String, String>
	// findCheckSaveToEmsHeadH2kData(List<EmsEdiHeadH2kBomFrom> list, EmsHeadH2k
	// emsHeadH2k) {
	//
	//
	//
	// // 1.1 验证emsbom数据
	// Map<String, String> emsBomMap = new HashMap<String, String>();
	//
	// int size = list.size();
	// // Set<EmsEdiHeadH2kBomFrom> set = new HashSet<EmsEdiHeadH2kBomFrom>();
	// // int buffSize = 1500;
	// // for (int i = 0; i < size; i++) {
	// // if (i == (size - 1)) {
	// // set.addAll((Collection<? extends EmsEdiHeadH2kBomFrom>)
	// emsEdiTrDao.findEmsHeadH2kBomByBom(list.subList(
	// // (size / buffSize) * buffSize, size), emsHeadH2k));
	// // System.out.println(set.size());
	// // } else if ((i + 1) % buffSize == 0) {
	// // set.addAll((Collection<? extends EmsEdiHeadH2kBomFrom>)
	// emsEdiTrDao.findEmsHeadH2kBomByBom(list.subList(i
	// // - buffSize + 1, i + 1), emsHeadH2k));
	// // System.out.println(set.size());
	// // }
	// // }
	//
	// // 1.2 查询已知成品序号得下的所有版本bom；
	// // List<EmsHeadH2kBom> emsBomList = new ArrayList<EmsHeadH2kBom>();
	// // emsBomList.addAll((Collection<? extends EmsHeadH2kBom>) set);
	//
	// // 1.3 把emsBomList转化为emsBomMap, 同时获versionMap
	// // String key = null;
	// // EmsHeadH2kBom b = null;
	// // for (int i = 0; i < emsBomList.size(); i++) {
	// // b = emsBomList.get(i);
	// // // 料件序号 + 版本号 + 成品序号
	// // key = b.getSeqNum() + "/"
	// // + b.getEmsHeadH2kVersion().getVersion() + "/"
	// // + b.getEmsHeadH2kExg().getSeqNum();
	// //
	// // emsBomMap.put(key, key);
	// //
	// // }
	// //
	// //
	// // return emsBomMap;
	//
	// }

	/**
	 * 根据内部归并更新归并关系
	 * 
	 * @param head
	 * @param innerMergeList
	 * @return
	 */
	public List updateEmsEdiMerger(EmsEdiMergerHead head, List innerMergeList) {
		// 保存统计信息
		int afterImgAmount = 0;
		int beforeImgAmount = 0;
		int afterExgAmount = 0;
		int beforeExgAmount = 0;
		// 归并后料件
		List afterImgList = emsEdiTrDao.findEmsEdiMergerImgAfter(head);
		// 归并前料件
		List beforeImgList = emsEdiTrDao.findEmsEdiMergerImgBefore(head);
		// 归并后成品
		List afterExgList = emsEdiTrDao.findEmsEdiMergerExgAfter(head);
		// 归并前成品
		List beforeExgList = emsEdiTrDao.findEmsEdiMergerExgBefore(head);
		// 相对应的List放进MAP
		System.out.println("afterImgList=" + afterImgList.size());
		System.out.println("beforeImgList=" + beforeImgList.size());
		System.out.println("afterExgList=" + afterExgList.size());
		System.out.println("beforeExgList=" + beforeExgList.size());
		Map afterImgMap = new HashMap<Integer, EmsEdiMergerImgAfter>();
		Map beforeImgMap = new HashMap<String, EmsEdiMergerImgBefore>();
		Map afterExgMap = new HashMap<Integer, EmsEdiMergerExgAfter>();
		Map beforeExgMap = new HashMap<String, EmsEdiMergerExgBefore>();
		for (int i = 0; i < afterImgList.size(); i++) {
			EmsEdiMergerImgAfter afterImg = (EmsEdiMergerImgAfter) afterImgList
					.get(i);
			afterImgMap.put(afterImg.getSeqNum(), afterImg);
		}
		for (int i = 0; i < beforeImgList.size(); i++) {
			EmsEdiMergerImgBefore beforeImg = (EmsEdiMergerImgBefore) beforeImgList
					.get(i);
			beforeImgMap.put(beforeImg.getPtNo(), beforeImg);
		}
		for (int i = 0; i < afterExgList.size(); i++) {
			EmsEdiMergerExgAfter afterExg = (EmsEdiMergerExgAfter) afterExgList
					.get(i);
			afterExgMap.put(afterExg.getSeqNum(), afterExg);
		}
		for (int i = 0; i < beforeExgList.size(); i++) {
			EmsEdiMergerExgBefore beforeExg = (EmsEdiMergerExgBefore) beforeExgList
					.get(i);
			beforeExgMap.put(beforeExg.getPtNo(), beforeExg);
		}
		// 开始循环内部归并，判断是否要更新
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < innerMergeList.size(); i++) {
			InnerMergeData data = (InnerMergeData) innerMergeList.get(i);

			String afterKey = (data.getHsAfterComplex() == null ? "" : data
					.getHsAfterComplex().getCode())
					+ "/"
					+ data.getHsAfterMaterielTenName()
					+ "/"
					+ data.getHsAfterMaterielTenSpec()
					+ "/"
					+ (data.getHsAfterMemoUnit() == null ? "" : data
							.getHsAfterMemoUnit().getName());
			String beforeKey = (data.getHsAfterComplex() == null ? "" : data
					.getHsAfterComplex().getCode())
					+ "/"
					+ data.getMateriel().getFactoryName()
					+ "/"
					+ data.getMateriel().getFactorySpec();
			if (data.getImrType().equals(MaterielType.MATERIEL)) {
				EmsEdiMergerImgAfter afterImg = (EmsEdiMergerImgAfter) afterImgMap
						.get(data.getHsAfterTenMemoNo());
				data.setIsExistMerger(new Boolean(true));
				emsEdiTrDao.saveOrUpdate(data);
				if (afterImg != null) {// 判断归并后料件
					String mark = afterImg.getModifyMark();// 获取修改标志
					String key = (afterImg.getComplex() == null ? "" : afterImg
							.getComplex().getCode())
							+ "/"
							+ afterImg.getName()
							+ "/"
							+ afterImg.getSpec()
							+ "/"
							+ (afterImg.getUnit() == null ? "" : afterImg
									.getUnit().getName());
					if (!key.equals(afterKey)) {// 不一样，有更改要更新
						if (mark.equals("0")) {// 未修改
							afterImg.setModifyMark("1");// 修改标记位
							afterImg.setSendState(1);
							afterImg.setComplex(data.getHsAfterComplex());
							afterImg.setName(data.getHsAfterMaterielTenName());
							afterImg.setSpec(data.getHsAfterMaterielTenSpec());
							afterImg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								afterImg.setChangeDate(data.getUpdateDate());
							else
								afterImg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(afterImg);
							afterImgAmount++;
						} else if (mark.equals("1")) {// 已修改
							afterImg.setSendState(1);
							afterImg.setComplex(data.getHsAfterComplex());
							afterImg.setName(data.getHsAfterMaterielTenName());
							afterImg.setSpec(data.getHsAfterMaterielTenSpec());
							afterImg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								afterImg.setChangeDate(data.getUpdateDate());
							else
								afterImg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(afterImg);
							afterImgAmount++;
						} else if (mark.equals("2")) {// 已删除

						} else if (mark.equals("3")) {// 新增
							afterImg.setSendState(1);
							afterImg.setComplex(data.getHsAfterComplex());
							afterImg.setName(data.getHsAfterMaterielTenName());
							afterImg.setSpec(data.getHsAfterMaterielTenSpec());
							afterImg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								afterImg.setChangeDate(data.getUpdateDate());
							else
								afterImg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(afterImg);
							afterImgAmount++;
						}
					}
				} else {// 没有此归并后新增进来
					EmsEdiMergerImgAfter newAfterImg = new EmsEdiMergerImgAfter();
					newAfterImg.setCompany(CommonUtils.getCompany());
					newAfterImg.setEmsEdiMergerHead(head);
					newAfterImg.setComplex(data.getHsAfterComplex());
					newAfterImg.setName(data.getHsAfterMaterielTenName());
					newAfterImg.setSpec(data.getHsAfterMaterielTenSpec());
					newAfterImg.setUnit(data.getHsAfterMemoUnit());
					newAfterImg.setSeqNum(data.getHsAfterTenMemoNo());
					newAfterImg.setModifyTimes(0);
					newAfterImg.setModifyMark("3");
					newAfterImg.setSendState(1);
					newAfterImg.setChangeDate(java.sql.Date
							.valueOf(bartDateFormat.format(new Date())));
					emsEdiTrDao.saveOrUpdate(newAfterImg);
					afterImgMap.put(newAfterImg.getSeqNum(), newAfterImg);
					afterImgAmount++;
				}
				EmsEdiMergerImgBefore beforeImg = (EmsEdiMergerImgBefore) beforeImgMap
						.get(data.getMateriel().getPtNo());
				if (beforeImg != null) {// 判断归并前料件
					String mark = beforeImg.getModifyMark();// 获取修改标志
					String key = (beforeImg.getComplex() == null ? ""
							: beforeImg.getComplex().getCode())
							+ "/"
							+ beforeImg.getName() + "/" + beforeImg.getSpec();
					EmsEdiMergerImgAfter after = (EmsEdiMergerImgAfter) afterImgMap
							.get(data.getHsAfterTenMemoNo());
					if (after != null
							&& !after.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
						if (!key.equals(beforeKey)) {
							if (mark.equals("0")) {// 未修改
								beforeImg.setModifyMark("1");
								beforeImg.setSendState(1);
								beforeImg.setComplex(data.getHsAfterComplex());
								beforeImg.setName(data.getMateriel()
										.getFactoryName());
								beforeImg.setSpec(data.getMateriel()
										.getFactorySpec());

								if (data.getUpdateDate() != null) {
									beforeImg.setChangeDate(data
											.getUpdateDate());
								} else {
									beforeImg.setChangeDate(java.sql.Date
											.valueOf(bartDateFormat
													.format(new Date())));
								}
								emsEdiTrDao.saveOrUpdate(beforeImg);
								beforeImgAmount++;
							} else if (mark.equals("1")) {// 已修改
								beforeImg.setSendState(1);
								beforeImg.setComplex(data.getHsAfterComplex());
								beforeImg.setName(data.getMateriel()
										.getFactoryName());
								beforeImg.setSpec(data.getMateriel()
										.getFactorySpec());
								if (data.getUpdateDate() != null)
									beforeImg.setChangeDate(data
											.getUpdateDate());
								else
									beforeImg.setChangeDate(java.sql.Date
											.valueOf(bartDateFormat
													.format(new Date())));
								emsEdiTrDao.saveOrUpdate(beforeImg);
								beforeImgAmount++;
							} else if (mark.equals("2")) {// 已删除
							} else if (mark.equals("3")) {// 新增
								beforeImg.setSendState(1);
								beforeImg.setComplex(data.getHsAfterComplex());
								beforeImg.setName(data.getMateriel()
										.getFactoryName());
								beforeImg.setSpec(data.getMateriel()
										.getFactorySpec());
								if (data.getUpdateDate() != null) {
									beforeImg.setChangeDate(data
											.getUpdateDate());
								} else {
									beforeImg.setChangeDate(java.sql.Date
											.valueOf(bartDateFormat
													.format(new Date())));
								}
								emsEdiTrDao.saveOrUpdate(beforeImg);

								beforeImgAmount++;
							}
						}
					}
				} else {// 没有找到料号，先按备案序号找归并后，判断是否删除状态
					EmsEdiMergerImgAfter after = (EmsEdiMergerImgAfter) afterImgMap
							.get(data.getHsAfterTenMemoNo());
					if (after != null
							&& !after.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
						EmsEdiMergerImgBefore newBeforeImg = new EmsEdiMergerImgBefore();
						newBeforeImg.setEmsEdiMergerImgAfter(after);
						newBeforeImg.setSeqNum(after.getSeqNum());
						newBeforeImg.setCompany(CommonUtils.getCompany());
						newBeforeImg.setComplex(data.getHsAfterComplex());
						newBeforeImg.setName(data.getMateriel()
								.getFactoryName());
						newBeforeImg.setSpec(data.getMateriel()
								.getFactorySpec());
						newBeforeImg.setUnit(after.getUnit());
						newBeforeImg.setPtNo(data.getMateriel().getPtNo());
						newBeforeImg.setModifyMark("3");
						newBeforeImg.setSendState(1);
						newBeforeImg.setChangeDate(java.sql.Date
								.valueOf(bartDateFormat.format(new Date())));
						emsEdiTrDao.saveOrUpdate(newBeforeImg);
						beforeImgMap.put(newBeforeImg.getPtNo(), newBeforeImg);
						beforeImgAmount++;
					}
				}

			} else if (data.getImrType().equals(MaterielType.FINISHED_PRODUCT)) {
				EmsEdiMergerExgAfter afterExg = (EmsEdiMergerExgAfter) afterExgMap
						.get(data.getHsAfterTenMemoNo());
				data.setIsExistMerger(new Boolean(true));
				emsEdiTrDao.saveOrUpdate(data);
				if (afterExg != null) {// 归并后成品
					String mark = afterExg.getModifyMark();// 获取修改标志
					String key = (afterExg.getComplex() == null ? "" : afterExg
							.getComplex().getCode())
							+ "/"
							+ afterExg.getName()
							+ "/"
							+ afterExg.getSpec()
							+ "/"
							+ (afterExg.getUnit() == null ? "" : afterExg
									.getUnit().getName());
					if (!key.equals(afterKey)) {// 不一样，有更改要更新
						if (mark.equals("0")) {// 未修改
							afterExg.setModifyMark("1");// 修改标记位
							afterExg.setSendState(1);
							afterExg.setComplex(data.getHsAfterComplex());
							afterExg.setName(data.getHsAfterMaterielTenName());
							afterExg.setSpec(data.getHsAfterMaterielTenSpec());
							afterExg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								afterExg.setChangeDate(data.getUpdateDate());
							else
								afterExg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(afterExg);
							afterExgAmount++;
						} else if (mark.equals("1")) {// 已修改
							afterExg.setSendState(1);
							afterExg.setComplex(data.getHsAfterComplex());
							afterExg.setName(data.getHsAfterMaterielTenName());
							afterExg.setSpec(data.getHsAfterMaterielTenSpec());
							afterExg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								afterExg.setChangeDate(data.getUpdateDate());
							else
								afterExg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(afterExg);
							afterExgAmount++;
						} else if (mark.equals("2")) {// 已删除

						} else if (mark.equals("3")) {// 新增
							afterExg.setSendState(1);
							afterExg.setComplex(data.getHsAfterComplex());
							afterExg.setName(data.getHsAfterMaterielTenName());
							afterExg.setSpec(data.getHsAfterMaterielTenSpec());
							afterExg.setUnit(data.getHsAfterMemoUnit());
							afterExg.setChangeDate(java.sql.Date
									.valueOf(bartDateFormat.format(new Date())));
							emsEdiTrDao.saveOrUpdate(afterExg);
							afterExgAmount++;
						}
					}
				} else {// 没有此归并后新增进来
					EmsEdiMergerExgAfter newAfterExg = new EmsEdiMergerExgAfter();
					newAfterExg.setCompany(CommonUtils.getCompany());
					newAfterExg.setEmsEdiMergerHead(head);
					newAfterExg.setComplex(data.getHsAfterComplex());
					newAfterExg.setName(data.getHsAfterMaterielTenName());
					newAfterExg.setSpec(data.getHsAfterMaterielTenSpec());
					newAfterExg.setUnit(data.getHsAfterMemoUnit());
					newAfterExg.setSeqNum(data.getHsAfterTenMemoNo());
					newAfterExg.setModifyTimes(0);
					newAfterExg.setModifyMark("3");
					newAfterExg.setSendState(1);
					newAfterExg.setChangeDate(java.sql.Date
							.valueOf(bartDateFormat.format(new Date())));
					emsEdiTrDao.saveOrUpdate(newAfterExg);
					afterExgMap.put(newAfterExg.getSeqNum(), newAfterExg);
					afterExgAmount++;
				}
				EmsEdiMergerExgBefore beforeExg = (EmsEdiMergerExgBefore) beforeExgMap
						.get(data.getMateriel().getPtNo());
				if (beforeExg != null) {// 归并前成品
					String mark = beforeExg.getModifyMark();// 获取修改标志
					String key = (beforeExg.getComplex() == null ? ""
							: beforeExg.getComplex().getCode())
							+ "/"
							+ beforeExg.getName() + "/" + beforeExg.getSpec();
					EmsEdiMergerExgAfter after = (EmsEdiMergerExgAfter) afterExgMap
							.get(data.getHsAfterTenMemoNo());
					if (after != null
							&& !after.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
						if (!key.equals(beforeKey)) {
							if (mark.equals("0")) {// 未修改
								beforeExg.setModifyMark("1");
								beforeExg.setSendState(1);
								beforeExg.setComplex(data.getHsAfterComplex());
								beforeExg.setName(data.getMateriel()
										.getFactoryName());
								beforeExg.setSpec(data.getMateriel()
										.getFactorySpec());
								if (data.getUpdateDate() != null)
									beforeExg.setChangeDate(data
											.getUpdateDate());
								else
									beforeExg.setChangeDate(java.sql.Date
											.valueOf(bartDateFormat
													.format(new Date())));
								emsEdiTrDao.saveOrUpdate(beforeExg);

								beforeExgAmount++;
							} else if (mark.equals("1")) {// 已修改
								beforeExg.setSendState(1);
								beforeExg.setComplex(data.getHsAfterComplex());
								beforeExg.setName(data.getMateriel()
										.getFactoryName());
								beforeExg.setSpec(data.getMateriel()
										.getFactorySpec());
								if (data.getUpdateDate() != null)
									beforeExg.setChangeDate(data
											.getUpdateDate());
								else
									beforeExg.setChangeDate(java.sql.Date
											.valueOf(bartDateFormat
													.format(new Date())));
								emsEdiTrDao.saveOrUpdate(beforeExg);

								beforeExgAmount++;
							} else if (mark.equals("2")) {// 已删除

							} else if (mark.equals("3")) {// 新增
								beforeExg.setSendState(1);
								beforeExg.setComplex(data.getHsAfterComplex());
								beforeExg.setName(data.getMateriel()
										.getFactoryName());
								beforeExg.setSpec(data.getMateriel()
										.getFactorySpec());
								beforeExg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
								emsEdiTrDao.saveOrUpdate(beforeExg);

								beforeExgAmount++;
							}
						}
					}
				} else {
					EmsEdiMergerExgAfter after = (EmsEdiMergerExgAfter) afterExgMap
							.get(data.getHsAfterTenMemoNo());
					if (after != null
							&& !after.getModifyMark().equals(
									ModifyMarkState.DELETED)) {
						EmsEdiMergerExgBefore newBeforeExg = new EmsEdiMergerExgBefore();
						newBeforeExg.setEmsEdiMergerExgAfter(after);
						newBeforeExg.setSeqNum(after.getSeqNum());
						newBeforeExg.setCompany(CommonUtils.getCompany());
						newBeforeExg.setComplex(data.getHsAfterComplex());
						newBeforeExg.setName(data.getMateriel()
								.getFactoryName());
						newBeforeExg.setSpec(data.getMateriel()
								.getFactorySpec());
						newBeforeExg.setUnit(after.getUnit());
						newBeforeExg.setPtNo(data.getMateriel().getPtNo());
						newBeforeExg.setModifyMark("3");
						newBeforeExg.setSendState(1);
						newBeforeExg.setChangeDate(java.sql.Date
								.valueOf(bartDateFormat.format(new Date())));
						emsEdiTrDao.saveOrUpdate(newBeforeExg);
						beforeExgMap.put(newBeforeExg.getPtNo(), newBeforeExg);
						beforeExgAmount++;
					}
				}
			}
		}
		List returnInfo = new ArrayList();
		returnInfo.add("料件归并后更改的有:" + afterImgAmount + " 条记录\n");
		returnInfo.add("料件归并前更改的有:" + beforeImgAmount + " 条记录\n");
		returnInfo.add("成品归并后更改的有:" + afterExgAmount + " 条记录\n");
		returnInfo.add("成品归并前更改的有:" + beforeExgAmount + " 条记录\n");
		return returnInfo;
	}

	/**
	 * 根据内部归并更新电子账册
	 * 
	 * @param head
	 * @param innerMergeList
	 * @return
	 */
	public List updateEmsEdiMerger(EmsHeadH2k emsHead, List innerMergeList) {
		// 保存统计信息
		int imgAmount = 0;
		int exgAmount = 0;
		// 账册料件
		List imgList = emsEdiTrDao.findEmsHeadH2kImgExg(emsHead, true, null,
				null);
		// 账册成品
		List exgList = emsEdiTrDao.findEmsHeadH2kImgExg(emsHead, false, null,
				null);
		// 相对应的List放进MAP
		System.out.println("imgList=" + imgList.size());
		System.out.println("exgList=" + exgList.size());
		Map<Integer, EmsHeadH2kImg> imgMap = new HashMap<Integer, EmsHeadH2kImg>();
		Map<Integer, EmsHeadH2kExg> exgMap = new HashMap<Integer, EmsHeadH2kExg>();
		for (int i = 0; i < imgList.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) imgList.get(i);
			imgMap.put(img.getSeqNum(), img);
		}
		for (int i = 0; i < exgList.size(); i++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) exgList.get(i);
			exgMap.put(exg.getSeqNum(), exg);
		}
		// 开始循环内部归并，判断是否要更新
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < innerMergeList.size(); i++) {
			InnerMergeData data = (InnerMergeData) innerMergeList.get(i);
			System.out.println("date.spect="
					+ data.getMateriel().getFactorySpec());
			String imgKey = (data.getHsAfterComplex() == null ? "" : data
					.getHsAfterComplex().getCode())
					+ "/"
					+ data.getHsAfterMaterielTenName()
					+ "/"
					+ data.getHsAfterMaterielTenSpec()
					+ "/"
					+ (data.getHsAfterMemoUnit() == null ? "" : data
							.getHsAfterMemoUnit().getName());
			if (data.getImrType().equals(MaterielType.MATERIEL)) {
				System.out.println("22222222=");
				EmsHeadH2kImg img = (EmsHeadH2kImg) imgMap.get(data
						.getHsAfterTenMemoNo());
				if (img != null) {
					System.out.println("333333333=");
					String mark = img.getModifyMark();// 获取修改标志
					String key = (img.getComplex() == null ? "" : img
							.getComplex().getCode())
							+ "/"
							+ img.getName()
							+ "/"
							+ img.getSpec()
							+ "/"
							+ (img.getUnit() == null ? "" : img.getUnit()
									.getName());
					if (!key.equals(imgKey)) {// 不一样，有更改要更新
						if (mark.equals("0")) {// 未修改
							img.setModifyMark("1");// 修改标记位
							img.setSendState(1);
							img.setComplex(data.getHsAfterComplex());
							img.setName(data.getHsAfterMaterielTenName());
							img.setSpec(data.getHsAfterMaterielTenSpec());
							img.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null) {
								img.setChangeDate(data.getUpdateDate());
							} else
								img.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(img);
							imgAmount++;
						} else if (mark.equals("1")) {// 已修改
							img.setSendState(1);
							img.setComplex(data.getHsAfterComplex());
							img.setName(data.getHsAfterMaterielTenName());
							img.setSpec(data.getHsAfterMaterielTenSpec());
							img.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								img.setChangeDate(data.getUpdateDate());
							else
								img.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(img);
							imgAmount++;
						} else if (mark.equals("2")) {// 已删除

						} else if (mark.equals("3")) {// 新增
							img.setSendState(1);
							img.setComplex(data.getHsAfterComplex());
							img.setName(data.getHsAfterMaterielTenName());
							img.setSpec(data.getHsAfterMaterielTenSpec());
							img.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								img.setChangeDate(data.getUpdateDate());
							else
								img.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(img);
							imgAmount++;
						}
					}
				} else {// 没有此归并后新增进来
					EmsHeadH2kImg newImg = new EmsHeadH2kImg();
					newImg.setCompany(CommonUtils.getCompany());
					newImg.setEmsHeadH2k(emsHead);
					newImg.setComplex(data.getHsAfterComplex());
					newImg.setName(data.getHsAfterMaterielTenName());
					newImg.setSpec(data.getHsAfterMaterielTenSpec());
					newImg.setUnit(data.getHsAfterMemoUnit());
					newImg.setSeqNum(data.getHsAfterTenMemoNo());
					newImg.setModifyTimes(0);
					newImg.setModifyMark("3");
					newImg.setSendState(1);
					newImg.setChangeDate(java.sql.Date.valueOf(bartDateFormat
							.format(new Date())));
					emsEdiTrDao.saveOrUpdate(newImg);
					imgMap.put(newImg.getSeqNum(), newImg);
					imgAmount++;
				}

			} else if (data.getImrType().equals(MaterielType.FINISHED_PRODUCT)) {
				EmsHeadH2kExg exg = (EmsHeadH2kExg) exgMap.get(data
						.getHsAfterTenMemoNo());
				if (exg != null) {
					System.out.println("333333333=");
					String mark = exg.getModifyMark();// 获取修改标志
					String key = (exg.getComplex() == null ? "" : exg
							.getComplex().getCode())
							+ "/"
							+ exg.getName()
							+ "/"
							+ exg.getSpec()
							+ "/"
							+ (exg.getUnit() == null ? "" : exg.getUnit()
									.getName());
					if (!key.equals(imgKey)) {// 不一样，有更改要更新
						if (mark.equals("0")) {// 未修改
							exg.setModifyMark("1");// 修改标记位
							exg.setSendState(1);
							exg.setComplex(data.getHsAfterComplex());
							exg.setName(data.getHsAfterMaterielTenName());
							exg.setSpec(data.getHsAfterMaterielTenSpec());
							exg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								exg.setChangeDate(data.getUpdateDate());
							else
								exg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(exg);
							exgAmount++;
						} else if (mark.equals("1")) {// 已修改
							exg.setSendState(1);
							exg.setComplex(data.getHsAfterComplex());
							exg.setName(data.getHsAfterMaterielTenName());
							exg.setSpec(data.getHsAfterMaterielTenSpec());
							exg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								exg.setChangeDate(data.getUpdateDate());
							else
								exg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(exg);
							exgAmount++;
						} else if (mark.equals("2")) {// 已删除

						} else if (mark.equals("3")) {// 新增
							exg.setSendState(1);
							exg.setComplex(data.getHsAfterComplex());
							exg.setName(data.getHsAfterMaterielTenName());
							exg.setSpec(data.getHsAfterMaterielTenSpec());
							exg.setUnit(data.getHsAfterMemoUnit());
							if (data.getUpdateDate() != null)
								exg.setChangeDate(data.getUpdateDate());
							else
								exg.setChangeDate(java.sql.Date
										.valueOf(bartDateFormat
												.format(new Date())));
							emsEdiTrDao.saveOrUpdate(exg);
							exgAmount++;
						}
					}
				} else {// 没有此归并后新增进来
					EmsHeadH2kExg newExg = new EmsHeadH2kExg();
					newExg.setCompany(CommonUtils.getCompany());
					newExg.setEmsHeadH2k(emsHead);
					newExg.setComplex(data.getHsAfterComplex());
					newExg.setName(data.getHsAfterMaterielTenName());
					newExg.setSpec(data.getHsAfterMaterielTenSpec());
					newExg.setUnit(data.getHsAfterMemoUnit());
					newExg.setSeqNum(data.getHsAfterTenMemoNo());
					newExg.setModifyTimes(0);
					newExg.setModifyMark("3");
					newExg.setSendState(1);
					newExg.setChangeDate(java.sql.Date.valueOf(bartDateFormat
							.format(new Date())));
					emsEdiTrDao.saveOrUpdate(newExg);
					exgMap.put(newExg.getSeqNum(), newExg);
					exgAmount++;
				}

			}
		}
		List returnInfo = new ArrayList();
		returnInfo.add("账册料件更改的:" + imgAmount + " 条记录\n");
		returnInfo.add("账册成品更改的:" + exgAmount + " 条记录\n");
		return returnInfo;
	}

	/**
	 * 按照条件查找内部归并
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getInnerMergeListByDate(boolean isEms, boolean isAdd,
			boolean isChange, EmsEdiMergerHead head, EmsHeadH2k emsHead,
			Date startDate, Date endDate) {
		// 内部归并
		List result = new ArrayList();
		List list = emsEdiTrDao.getInnerMergeListByDate(startDate, endDate);
		if (!isEms) {
			// 归并关系-归并后料件
			List imgAfterList = emsEdiTrDao.getImgAfterSeqNum(head);
			Map imgAfterMap = new HashMap<String, EmsEdiMergerImgAfter>();
			for (int i = 0; i < imgAfterList.size(); i++) {
				EmsEdiMergerImgAfter img = (EmsEdiMergerImgAfter) imgAfterList
						.get(i);
				imgAfterMap.put(img.getSeqNum(), img);
			}
			// 归并关系-归并前料件
			List imgBeforeList = emsEdiTrDao.getImgBeforePtNo();
			Map imgBeforeMap = new HashMap<String, EmsEdiMergerImgBefore>();
			for (int i = 0; i < imgBeforeList.size(); i++) {
				EmsEdiMergerImgBefore img = (EmsEdiMergerImgBefore) imgBeforeList
						.get(i);
				imgBeforeMap.put(img.getPtNo(), img);
			}
			// 归并关系-归并后成品
			List exgAfterList = emsEdiTrDao.getExgAfterSeqNum(head);
			Map exgAfterMap = new HashMap<String, EmsEdiMergerExgAfter>();
			for (int i = 0; i < exgAfterList.size(); i++) {
				EmsEdiMergerExgAfter exg = (EmsEdiMergerExgAfter) exgAfterList
						.get(i);
				exgAfterMap.put(exg.getSeqNum(), exg);
			}
			// 归并关系-归并前成品
			List exgBeforeList = emsEdiTrDao.getExgBeforePtNo();
			Map exgBeforeMap = new HashMap<String, EmsEdiMergerExgBefore>();
			for (int i = 0; i < exgBeforeList.size(); i++) {
				EmsEdiMergerExgBefore exg = (EmsEdiMergerExgBefore) exgBeforeList
						.get(i);
				exgBeforeMap.put(exg.getPtNo(), exg);
			}

			for (int i = 0; i < list.size(); i++) {
				InnerMergeData data = (InnerMergeData) list.get(i);
				TempOfInnerMergeData temp = new TempOfInnerMergeData();
				temp.setChangeTypeAfter("未修改");
				temp.setChangeTypeBefore("未修改");
				temp.setData(data);
				if (data.getMateriel() == null) {
					throw new RuntimeException("存在料号为空值");
				}
				String afterKey = (data.getHsAfterComplex() == null ? "" : data
						.getHsAfterComplex().getCode())
						+ "/@/"
						+ data.getHsAfterMaterielTenName()
						+ "/@/"
						+ data.getHsAfterMaterielTenSpec()
						+ "/@/"
						+ (data.getHsAfterMemoUnit() == null ? "" : data
								.getHsAfterMemoUnit().getName());
				String beforeKey = (data.getHsAfterComplex() == null ? ""
						: data.getHsAfterComplex().getCode())
						+ "/@/"
						+ data.getMateriel().getFactoryName()
						+ "/@/"
						+ data.getMateriel().getFactorySpec();
				if (data.getImrType().equals(MaterielType.MATERIEL)) {

					// 判断归并后料件
					EmsEdiMergerImgAfter afterImg = (EmsEdiMergerImgAfter) imgAfterMap
							.get((data.getHsAfterTenMemoNo()));
					if (afterImg == null) {
						temp.setChangeTypeAfter("新增");
					} else {

						String key = (afterImg.getComplex() == null ? ""
								: afterImg.getComplex().getCode())
								+ "/@/"
								+ afterImg.getName()
								+ "/@/"
								+ afterImg.getSpec()
								+ "/@/"
								+ (afterImg.getUnit() == null ? "" : afterImg
										.getUnit().getName());
						if (!afterImg.getModifyMark().equals(
								ModifyMarkState.DELETED)) {
							if (!key.equals(afterKey)) {
								temp.setChangeTypeAfter("变更");
							}
						} else {
							temp.setChangeTypeAfter("已删除");
						}
					}
					// 判断归并前料件
					EmsEdiMergerImgBefore beforeImg = (EmsEdiMergerImgBefore) imgBeforeMap
							.get((data.getMateriel().getPtNo()));
					if (beforeImg == null) {
						temp.setChangeTypeBefore("新增");
					} else if (!beforeImg.getModifyMark().equals(
							ModifyMarkState.DELETED)) {

						String key1 = (beforeImg.getComplex() == null ? ""
								: beforeImg.getComplex().getCode())
								+ "/@/"
								+ beforeImg.getName()
								+ "/@/"
								+ beforeImg.getSpec();
						if (!key1.equals(beforeKey))
							temp.setChangeTypeBefore("变更");

					} else {
						temp.setChangeTypeBefore("已删除");
					}

					// 成品
				} else if (data.getImrType().equals(
						MaterielType.FINISHED_PRODUCT)) {

					// 判断归并后成品
					EmsEdiMergerExgAfter afterExg = (EmsEdiMergerExgAfter) exgAfterMap
							.get((data.getHsAfterTenMemoNo()));
					if (afterExg == null) {
						temp.setChangeTypeAfter("新增");
					} else {

						String key = (afterExg.getComplex() == null ? ""
								: afterExg.getComplex().getCode())
								+ "/@/"
								+ afterExg.getName()
								+ "/@/"
								+ afterExg.getSpec()
								+ "/@/"
								+ (afterExg.getUnit() == null ? "" : afterExg
										.getUnit().getName());
						if (!afterExg.getModifyMark().equals(
								ModifyMarkState.DELETED)) {
							if (!key.equals(afterKey)) {
								temp.setChangeTypeAfter("变更");
							}
						} else {
							temp.setChangeTypeAfter("已删除");
						}
					}
					// 判断归并前料件
					EmsEdiMergerExgBefore beforeExg = (EmsEdiMergerExgBefore) exgBeforeMap
							.get((data.getMateriel().getPtNo()));
					if (beforeExg == null) {
						temp.setChangeTypeBefore("新增");
					} else if (!beforeExg.getModifyMark().equals(
							ModifyMarkState.DELETED)) {

						String key1 = (beforeExg.getComplex() == null ? ""
								: beforeExg.getComplex().getCode())
								+ "/@/"
								+ beforeExg.getName()
								+ "/@/"
								+ beforeExg.getSpec();
						if (!key1.equals(beforeKey))
							temp.setChangeTypeBefore("变更");

					} else {
						temp.setChangeTypeBefore("已删除");
					}

				}
				if ((isAdd && (temp.getChangeTypeAfter().equals("新增") || temp
						.getChangeTypeBefore().equals("新增")))
						|| (isChange && (temp.getChangeTypeAfter().equals("变更") || temp
								.getChangeTypeBefore().equals("变更")))) {
					result.add(temp);
				}

			}
			// 账册管理
		} else {
			// 账册管理-料件
			List emsImgList = emsEdiTrDao.getEmsImg(emsHead);
			Map imgMap = new HashMap<String, EmsHeadH2kImg>();
			for (int i = 0; i < emsImgList.size(); i++) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) emsImgList.get(i);
				imgMap.put(img.getSeqNum(), img);
			}
			// 账册管理-成品
			List emsExgList = emsEdiTrDao.getEmsExg(emsHead);
			Map exgMap = new HashMap<String, EmsHeadH2kExg>();
			for (int i = 0; i < emsExgList.size(); i++) {
				EmsHeadH2kExg exg = (EmsHeadH2kExg) emsExgList.get(i);
				exgMap.put(exg.getSeqNum(), exg);
			}
			for (int i = 0; i < list.size(); i++) {
				InnerMergeData data = (InnerMergeData) list.get(i);
				TempOfInnerMergeData temp = new TempOfInnerMergeData();
				temp.setChangeTypeAfter("未修改");
				temp.setChangeTypeBefore("未修改");
				temp.setData(data);
				String afterKey = (data.getHsAfterComplex() == null ? "" : data
						.getHsAfterComplex().getCode())
						+ "/@/"
						+ data.getHsAfterMaterielTenName()
						+ "/@/"
						+ data.getHsAfterMaterielTenSpec()
						+ "/@/"
						+ (data.getHsAfterMemoUnit() == null ? "" : data
								.getHsAfterMemoUnit().getName());
				if (data.getImrType().equals(MaterielType.MATERIEL)) {
					EmsHeadH2kImg img = (EmsHeadH2kImg) imgMap.get((data
							.getHsAfterTenMemoNo()));
					if (img == null) {
						temp.setChangeTypeAfter("新增");
					} else {

						String key = (img.getComplex() == null ? "" : img
								.getComplex().getCode())
								+ "/@/"
								+ img.getName()
								+ "/@/"
								+ img.getSpec()
								+ "/@/"
								+ (img.getUnit() == null ? "" : img.getUnit()
										.getName());
						if (!img.getModifyMark()
								.equals(ModifyMarkState.DELETED)) {
							if (!key.equals(afterKey)) {
								temp.setChangeTypeAfter("变更");
							}
						} else {
							temp.setChangeTypeAfter("已删除");
						}
					}
				} else if (data.getImrType().equals(
						MaterielType.FINISHED_PRODUCT)) {
					EmsHeadH2kExg exg = (EmsHeadH2kExg) exgMap.get((data
							.getHsAfterTenMemoNo()));
					if (exg == null) {
						temp.setChangeTypeAfter("新增");
					} else {

						String key = (exg.getComplex() == null ? "" : exg
								.getComplex().getCode())
								+ "/@/"
								+ exg.getName()
								+ "/@/"
								+ exg.getSpec()
								+ "/@/"
								+ (exg.getUnit() == null ? "" : exg.getUnit()
										.getName());
						if (!exg.getModifyMark()
								.equals(ModifyMarkState.DELETED)) {
							if (!key.equals(afterKey)) {
								temp.setChangeTypeAfter("变更");
							}
						} else {
							temp.setChangeTypeAfter("已删除");
						}
					}
				}
				if ((isAdd && temp.getChangeTypeAfter().equals("新增"))
						|| (isChange && temp.getChangeTypeAfter().equals("变更"))) {
					result.add(temp);
				}

			}

		}
		return result;
	}

	/**
	 * 当删除电子账册料件时检查
	 * 
	 * @param request
	 * @param currentRows
	 * @return
	 */
	public int checkToEmsH2kImgBom(EmsHeadH2kImg img) {
		List bomIsList = emsEdiTrDao.checkToEmsH2kImgBom(img);
		int type = 0;
		List listCustom = emsEdiTrDao.checkDeleteEmsHeadH2kImgExg(
				img.getSeqNum(), MaterielType.MATERIEL);
		if (listCustom != null && listCustom.size() > 0)
			return type = 2;
		if (bomIsList != null && bomIsList.size() > 0) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) bomIsList.get(0);
			// 若在成品单耗中存在
			if (bom.getModifyMark().equals(ModifyMarkState.UNCHANGE)
					|| bom.getModifyMark().equals(ModifyMarkState.MODIFIED)) {
				return type = 1;
			} else if (bom.getModifyMark().equals(ModifyMarkState.DELETED)) {
				List list = emsEdiTrDao.checkDeleteEmsHeadH2kImgExg(
						bom.getSeqNum(), MaterielType.MATERIEL);
				if (list != null && list.size() > 0)
					type = 2;
			} else if (bom.getModifyMark().equals(ModifyMarkState.ADDED)) {
				List list = emsEdiTrDao.checkDeleteEmsHeadH2kImgExg(
						bom.getSeqNum(), MaterielType.MATERIEL);
				if (list != null && list.size() > 0)
					type = 2;
			}
		}
		return type;
	}

	/**
	 * 报关清单XML导入
	 * 
	 * @return
	 */
	public Map loadBGDFromQPXml(boolean isImportBGD, boolean isChange) {
		String path = this.getQPBGDXmlPath();
		if (path == null || path.equals("")) {
			throw new RuntimeException("没有设定导入报关单文件存放路径");
		}
		this.createDefaultDirectory(path);
		System.out.println("isImportBGD=" + isImportBGD);
		List<File> lsFiles = getAllXmlFiles(path, isImportBGD);
		List<BaseApplyLoadXML> lsSuccess = new ArrayList<BaseApplyLoadXML>();
		List<BaseApplyLoadXML> lsError = new ArrayList<BaseApplyLoadXML>();
		// 繁体转简体
		if (isChange) {
			infTojHsTable();

		}
		System.out.println("lsFiles.size()=" + lsFiles.size());
		for (File file : lsFiles) {
			if (isxmlfile(file)) {
				if (isChange) {
					file = changeFile(file);
				}
				loadXmlFile(file, lsSuccess, lsError, isChange);
				if (isChange) {
					file.delete();
				}
			}
		}
		this.moveFile(isImportBGD, path, lsSuccess, lsError);
		Map<Integer, List> map = new HashMap<Integer, List>();
		map.put(0, lsSuccess);
		map.put(-1, lsError);
		return map;
	}

	// 将文件繁体转简体
	private File changeFile(File file) {
		List line = new ArrayList();
		String sb = "";
		line = readTxt(file);
		String[] strs = new String[line.size()];
		for (int i = 0; i < line.size(); i++) {
			strs[i] = (String) line.get(i);
		}
		strs = changStrs(strs);
		for (String s : strs) {
			sb += s;
		}
		file = creatFile(strs, file);
		return file;

	}

	private File creatFile(String[] strs, File file) {
		File newFile = new File(file.getPath() + ".tmp");
		System.out.println("-----------------------" + newFile.getPath());
		OutputStreamWriter bw = null;
		try {
			bw = new OutputStreamWriter(new FileOutputStream(newFile), "utf-8");
			for (String s : strs) {
				// byte[] bytes=s.getBytes("utf-8");
				// s=new String(bytes,"utf-8");
				bw.write(s);
				System.out.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return newFile;
	}

	public List readTxt(File file) {
		List line = new ArrayList();
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));
			while ((s = br.readLine()) != null) {
				// XML格式首行多一个?
				if (line.size() == 0) {
					if (!s.startsWith("<"))
						s = s.substring(1);
				}
				line.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	/**
	 * 解析XML文件
	 * 
	 * @param file
	 * @param lsSuccess
	 * @param lsError
	 */
	private void loadXmlFile(File file, List<BaseApplyLoadXML> lsSuccess,
			List<BaseApplyLoadXML> lsError, boolean isChange) {
		List<AtcMergeBeforeComInfo> listBefore = new ArrayList();
		List<AtcMergeAfterComInfo> listAfter = new ArrayList();
		BaseApplyLoadXML xmlApply = new BaseApplyLoadXML();
		SAXBuilder builder = new SAXBuilder();
		Document doc;
		// 监管方式Map
		Map tradeModeMap = getObjectMapByTableName("Trade");
		// 申报地海关Map
		Map declareCustomMap = getObjectMapByTableName("Customs");
		// 运输方式Map
		Map transportModeMap = getObjectMapByTableName("Transf");
		// 申报单位Map
		Map declareCompanyMap = getObjectMapByTableName("Company");
		// 进/出口岸Map
		Map entrancePortMap = getObjectMapByTableName("Customs");
		// 客户
		Map scmMap = getObjectMapByTableName("ScmCoc");
		// 产销国
		Map countryMap = getObjectMapByTableName("Country");
		// 币制
		Map currMap = getObjectMapByTableName("Curr");
		// 征免方式
		Map levyModeMap = getObjectMapByTableName("LevyMode");
		// 用途
		Map useToMap = getObjectMapByTableName("Uses");
		// 事业部
		Map projectDeptMap = getObjectMapByTableName("ProjectDept");
		Map<String, Integer> impExpTypeMap = getImpExpTypeMap();// 进出口类型Map
		try {
			ApplyToCustomsBillList apply = new ApplyToCustomsBillList();
			xmlApply.setApply(apply);
			if (isChange) {
				String fileName = file.getName();
				fileName = fileName.replace(".tmp", "");
				xmlApply.setFileName(fileName);
			} else {
				xmlApply.setFileName(file.getName());
			}
			doc = builder.build(file);
			Element foo = doc.getRootElement();
			// 获取清单表头
			List childrenHead = foo.getChildren("CBL_HEAD");
			if (childrenHead == null || childrenHead.size() == 0) {
				xmlApply.setErrorInfo("获取不到“CBL_HEAD”标签的表头；");
				lsError.add(xmlApply);
				return;
			}
			Element head = (Element) childrenHead.get(0);
			String impExpTypeName = isImpOrExp(head.getChild("I_E").getText());
			System.out.println("进出口类型:" + impExpTypeName);
			Integer impExpType = impExpTypeMap.get(head.getChild("I_E")
					.getText());
			// 进出口类型
			System.out.println("I_E=" + head.getChild("I_E").getText());
			if (impExpType == null) {
				xmlApply.setErrorInfo("进出口类型错误；");
				lsError.add(xmlApply);
				return;
			}
			List emsList = emsEdiTrDao.findEmsHeadH2kAll();
			EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsList.get(0);
			// 帐册编号
			apply.setEmsHeadH2k(emsHeadH2k.getEmsNo());
			// 录入员
			apply.setCreatedUser(CommonUtils.getAclUser());
			apply.setListState(0);// 草稿状态
			apply.setImpExpType(impExpType);
			apply.setCreatedDate(new Date());
			apply.setTradeCode(((Company) CommonUtils.getCompany()).getBuCode());// 经营单位代码
			apply.setTradeName(((Company) CommonUtils.getCompany()).getBuName());// 经营单位名称
			// 录入单位
			String[] property = new String[] { "code" };
			Object[] value = new Object[] { ((Company) CommonUtils.getCompany())
					.getBuCode() };
			List list1 = emsEdiTrDao.findObjectByTableNames("Brief", property,
					value, false);
			apply.setCreatedCompany((Brief) list1.get(0));

			if (impExpType == 0 || impExpType == 1 || impExpType == 2
					|| impExpType == 3 || impExpType == 25 || impExpType == 26
					|| impExpType == 17) {// || impExpType
				apply.setImpExpFlag(0);// 进出口标志,进
			} else {
				apply.setImpExpFlag(1);// 进出口标志,出
			}
			String billNo = head.getChildText("BILL_NO");
			if (billNo == null) {
				xmlApply.setErrorInfo("清单内部编号不能为空；");
				lsError.add(xmlApply);
				return;
			}
			if (checkBillListNoOverlap(billNo)) {
				xmlApply.setErrorInfo("清单内部编号重复；");
				lsError.add(xmlApply);
				return;
			}
			apply.setListNo(head.getChildText("BILL_NO"));
			// 申报海关
			String declareCIQ = head.getChildText("CUSTOM_MASTER");
			if (declareCIQ == null) {
				xmlApply.setErrorInfo("申报海关不能为空；");
				lsError.add(xmlApply);
				return;
			}
			if (declareCustomMap.get(declareCIQ) == null) {
				xmlApply.setErrorInfo("申报海关不正确,无该申报海关；");
				lsError.add(xmlApply);
				return;
			} else {
				apply.setDeclareCIQ((Customs) declareCustomMap.get(declareCIQ));
			}
			// 运输方式
			String trafMode = head.getChildText("TRAF_MODE");
			if (trafMode != null) {
				if (transportModeMap.get(trafMode) == null) {
					xmlApply.setErrorInfo("运输方式不正确,无该运输方式；");
					lsError.add(xmlApply);
					return;
				} else {
					apply.setTransportMode((Transf) transportModeMap
							.get(trafMode));
				}
			}
			// <I_E_PORT>进出口岸</I_E_PORT>
			String ieport = head.getChildText("I_E_PORT");
			if (ieport == null) {
				xmlApply.setErrorInfo("进出口岸不能为空；");
				lsError.add(xmlApply);
				return;
			}
			if (entrancePortMap.get(ieport) == null) {
				xmlApply.setErrorInfo("进出口岸不正确,无该进出口岸；");
				lsError.add(xmlApply);
				return;
			} else {
				apply.setImpExpCIQ((Customs) entrancePortMap.get(ieport));
			}
			// <TRADE_MODE>监管方式</TRADE_MODE>
			String tradeMode = head.getChildText("TRADE_MODE");
			if (tradeMode != null) {
				if (tradeModeMap.get(tradeMode) == null) {
					xmlApply.setErrorInfo("监管方式不正确,无该监管方式；");
					lsError.add(xmlApply);
					return;
				} else {
					apply.setTradeMode((Trade) tradeModeMap.get(tradeMode));
				}
			}
			// <SCM_COC>客户</SCM_COC>
			String scm = head.getChildText("SCM_COC");
			if (scm == null) {
				xmlApply.setErrorInfo("客户不能为空；");
				lsError.add(xmlApply);
				return;
			}
			if (scmMap.get(scm) == null) {
				xmlApply.setErrorInfo("客户不正确,无该客户；");
				lsError.add(xmlApply);
				return;
			} else {
				apply.setScmCoc((ScmCoc) scmMap.get(scm));
			}
			// <D_COMP>申报单位</D_COMP>
			String comp = head.getChildText("D_COMP");
			if (comp == null) {
				xmlApply.setErrorInfo("申报单位；");
				lsError.add(xmlApply);
				return;
			}
			if (declareCompanyMap.get(comp) == null) {
				xmlApply.setErrorInfo("申报单位不正确,无该申报单位；");
				lsError.add(xmlApply);
				return;
			} else {
				apply.setDeclarationCompany((Company) declareCompanyMap
						.get(comp));
			}
			// <NOTE>表头备注</NOTE>
			apply.setMemos(head.getChildText("NOTE"));
			List contracts = emsEdiTrDao.findEmsHeadH2kInExecuting();
			if (contracts != null)
				apply.setEmsHeadH2k(((EmsHeadH2k) contracts.get(contracts
						.size() - 1)).getEmsNo());
			apply.setCompany(CommonUtils.getCompany());

			List allChildrenList = foo.getChildren("CBL_LIST");
			// 商品项数
			apply.setMaterialNum(allChildrenList.size());
			// 获取清单表体
			// ---------------
			for (int i = 0; i < allChildrenList.size(); i++) {
				AtcMergeBeforeComInfo before = new AtcMergeBeforeComInfo();
				Element body = (Element) allChildrenList.get(i);
				// <PT_NO>料号</PT_NO>
				String ptNo = body.getChildText("PT_NO");
				if (ptNo == null) {
					xmlApply.setErrorInfo("料号不能为空；");
					lsError.add(xmlApply);
					return;
				}
				// 是否有归并后
				List merger = null;
				List bom = null;
				System.out.println("impExpTypeName====" + impExpTypeName);
				if (impExpTypeName.equals("料件")) {
					apply.setMaterielProductFlag(2);
					merger = emsEdiTrDao.findEmsHeadH2kByPtNoFromMerger(ptNo);
					System.out.println("hcl 1====");

				} else {
					// <EXG_VERSION>版本号</EXG_VERSION>
					apply.setMaterielProductFlag(0);
					String versionTxt = body.getChildText("EXG_VERSION");
					if (versionTxt == null) {
						xmlApply.setErrorInfo("版本号不能为空；");
					}
					try {
						Integer version = Integer.valueOf(versionTxt);
						before.setVersion(version);
						merger = emsEdiTrDao
								.findEmsHeadH2kExgByPtNoFromMerger(ptNo);
						bom = emsEdiTrDao.findEmsHeadH2kBomByPtNoFromMerger(
								ptNo, version);
					} catch (NumberFormatException e) {
						xmlApply.setErrorInfo("该料号[" + ptNo + "]版本号不是整数！");
						lsError.add(xmlApply);
						return;
					}
					System.out.println("hcl 2====");
				}
				if (merger.size() <= 0) {
					xmlApply.setErrorInfo("[" + ptNo + "]" + "该料号不存在于电子账册中；");
					lsError.add(xmlApply);
					return;
				}
				if (null != bom && bom.size() > 0) {
					xmlApply.setErrorInfo("[" + ptNo + "]"
							+ "该成品在电子账册中有BOM料件未申报；");
					lsError.add(xmlApply);
					return;
				}

				// 根据导入归并前数据判断对应的资料是否在归并关系管理、内部归并、报关物料主档里都存在
				String para = "";
				String tableName = "";
				if (impExpTypeName.equals("料件")) {// 2为料件
					tableName = "EmsEdiMergerImgBefore";
					para = "emsEdiMergerImgAfter";

				} else {
					tableName = "EmsEdiMergerExgBefore";
					para = "emsEdiMergerExgAfter";
				}
				// 在归并关系管理、内部归并、报关物料主档里都存在的List
				List listData = emsEdiTrDao.findBeforeMaterielPtNo(ptNo,
						tableName, null, para);

				// 如果没有对应的归并前商品货号就返回
				if (listData.isEmpty() || listData.size() < 0) {
					xmlApply.setErrorInfo("该料号[" + ptNo + "]没有在归并关系中备案！");
					lsError.add(xmlApply);
					return;
				}
				// <DECL_PRICE>企业申报单价</DECL_PRICE>
				String price = body.getChildText("DECL_PRICE");
				if (price == null) {
					xmlApply.setErrorInfo("企业申报单价不能为空；");
					lsError.add(xmlApply);
					return;
				}
				before.setDeclaredPrice(Double.valueOf(price));
				// <G_QTY>申报数量</G_QTY>
				String qty = body.getChildText("G_QTY");
				if (qty == null) {
					xmlApply.setErrorInfo("申报数量不能为空；");
					lsError.add(xmlApply);
					return;
				}
				before.setDeclaredAmount(Double.valueOf(qty));
				// <ORIGIN_COUNTRY>产销国</ORIGIN_COUNTRY>
				String country = body.getChildText("ORIGIN_COUNTRY");
				if (country == null) {
					xmlApply.setErrorInfo("产销国不能为空；");
					lsError.add(xmlApply);
					return;
				}
				if (countryMap.get(country) == null) {
					xmlApply.setErrorInfo("产销国不正确；");
					lsError.add(xmlApply);
					return;
				} else {
					before.setSalesCountry((Country) countryMap.get(country));
				}
				// <PACK_NO>件数</PACK_NO>
				String pack = body.getChildText("PACK_NO");
				if (pack == null) {
					xmlApply.setErrorInfo("件数不能为空；");
					lsError.add(xmlApply);
					return;
				}
				before.setPiece(Integer.valueOf(pack));
				// <CURR>币制</CURR>
				String curr = body.getChildText("CURR");
				if (curr == null) {
					xmlApply.setErrorInfo("币制不能为空；");
					lsError.add(xmlApply);
					return;
				}
				if (currMap.get(curr) == null) {
					xmlApply.setErrorInfo("币制不正确；");
					lsError.add(xmlApply);
					return;
				} else {
					before.setCurrency((Curr) currMap.get(curr));
				}
				// <NET_WT>净重</NET_WT>
				String netWt = body.getChildText("NET_WT");
				if (netWt != null) {
					before.setNetWeight(Double.valueOf(netWt));
				}
				// <GROSS_WT>毛重</GROSS_WT>
				String grossWt = body.getChildText("GROSS_WT");
				if (grossWt != null) {
					before.setGrossWeight(Double.valueOf(grossWt));
				}
				// <DUTY_MODE>征免方式</DUTY_MODE>
				String dutyDode = body.getChildText("DUTY_MODE");
				if (dutyDode == null) {
					xmlApply.setErrorInfo("征免方式不能为空；");
					lsError.add(xmlApply);
					return;
				}
				if (levyModeMap.get(dutyDode) == null) {
					xmlApply.setErrorInfo("征免方式不正确；");
					lsError.add(xmlApply);
					return;
				} else {
					before.setLevyMode((LevyMode) levyModeMap.get(dutyDode));
				}
				// <USE_TO>用途</USE_TO>
				String useTo = body.getChildText("USE_TO");
				if (useTo == null) {
					xmlApply.setErrorInfo("用途不能为空；");
					lsError.add(xmlApply);
					return;
				}
				if (useToMap.get(useTo) == null) {
					xmlApply.setErrorInfo("用途不正确；");
					lsError.add(xmlApply);
					return;
				} else {
					before.setUsesCode((Uses) useToMap.get(useTo));
				}
				// <PROJECT_DEPT>事业部</PROJECT_DEPT>
				String projectDept = body.getChildText("PROJECT_DEPT");
				if (projectDept == null
						&& projectDeptMap.get(projectDept) == null) {
					xmlApply.setErrorInfo("事业部不正确；");
					lsError.add(xmlApply);
					return;
				} else {
					before.setProjectDept((ProjectDept) projectDeptMap
							.get(projectDept));
				}
				// <QTY_1>法定数量</QTY_1>
				String qty1 = body.getChildText("QTY_1");
				if (qty1 == null) {
					xmlApply.setErrorInfo("第一法定数量不能为空；");
					lsError.add(xmlApply);
					return;
				}
				before.setLegalAmount(Double.valueOf(qty1));
				// <QTY_2>第二法定数量</QTY_2>
				String qty2 = body.getChildText("QTY_2");
				if (qty1 == null) {
					xmlApply.setErrorInfo("第二法定数量不能为空；");
					lsError.add(xmlApply);
					return;
				}
				before.setSecondLegalAmount(Double.valueOf(qty2));
				// <NOTE_1>表体备注</NOTE_1>
				String memo = body.getChildText("NOTE_1");
				before.setMemos(memo);
				// <NOTE_2>扩展备注</NOTE_2>
				String extendMemo = body.getChildText("NOTE_2");
				before.setExtendMemo(extendMemo);
				// 创建归并后
				// 查到的内部归并资料
				InnerMergeData innerMergeData = (InnerMergeData) listData
						.get(0);
				AtcMergeAfterComInfo after = createAfter(before,
						innerMergeData, apply);
				before.setMateriel(innerMergeData.getMateriel());
				before.setAfterComInfo(after);
				// 查找最大的商品序号
				// Integer maxBeforeSerialNo = emsEdiTrDao
				// .getMaxBeforeSerialNo(after);
				// before.setSerialNo(maxBeforeSerialNo+1);// 商品序号
				before.setSerialNo(i + 1);
				listBefore.add(before);
				listAfter.add(after);
			}
			lsSuccess.add(xmlApply);
			// 没错误才全部保存
			// 清单表头
			for (int i = 0; i < lsSuccess.size(); i++) {
				ApplyToCustomsBillList bApply = lsSuccess.get(i).getApply();
				emsEdiTrDao.saveOrUpdate(bApply);
			}
			// 清单归并后
			for (int i = 0; i < listAfter.size(); i++) {
				AtcMergeAfterComInfo bAfter = listAfter.get(i);
				emsEdiTrDao.saveOrUpdate(bAfter);
			}
			// 清单归并前
			for (int i = 0; i < listBefore.size(); i++) {
				AtcMergeBeforeComInfo bBfore = listBefore.get(i);
				emsEdiTrDao.saveOrUpdate(bBfore);
			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private AtcMergeAfterComInfo createAfter(AtcMergeBeforeComInfo before,
			InnerMergeData innerMergeData, ApplyToCustomsBillList apply) {
		Integer sTotalPrice = 3;
		AtcMergeAfterComInfo after = new AtcMergeAfterComInfo();
		after.setBillList(apply);
		System.out.println("innerMergeData.getHsAfterTenMemoNo()="
				+ innerMergeData.getHsAfterTenMemoNo());
		System.out.println("innerMergeData.getEmsSerialNo()="
				+ innerMergeData.getEmsSerialNo());
		System.out.println("innerMergeData..getMateriel().getPtNo()="
				+ innerMergeData.getMateriel().getPtNo());
		after.setEmsSerialNo(Integer.valueOf(innerMergeData
				.getHsAfterTenMemoNo()));
		after.setCompany(CommonUtils.getCompany());
		after.setComplex(innerMergeData.getHsAfterComplex());
		after.setComplexName(innerMergeData.getHsAfterMaterielTenName());
		after.setComplexSpec(innerMergeData.getHsAfterMaterielTenSpec());
		after.setUnit(innerMergeData.getHsAfterMemoUnit());
		after.setLegalUnit(innerMergeData.getHsAfterLegalUnit());
		after.setSecondLegalUnit(innerMergeData.getHsAfterSecondLegalUnit());
		if (before.getVersion() != null)
			after.setVersion(String.valueOf(before.getVersion()));
		// 获取参数设置小数位
		String cTotalPrice = (String) emsEdiTrDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_TOTALPRICE);
		if (cTotalPrice == null || cTotalPrice.equals("")) {
			sTotalPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sTotalPrice = Integer.parseInt(cTotalPrice);
		}
		// 计算归并后总价
		after.setTotalPrice(CommonUtils.getDoubleByDigit(
				before.getTotalPrice(), sTotalPrice));
		// 计算归并后的件数
		after.setPiece(before.getPiece() == null ? 0 : before.getPiece());
		// 计算归并后数量
		after.setDeclaredAmount((before.getDeclaredAmount() == null ? 0
				: before.getDeclaredAmount()));
		// 计算归并后单价
		after.setPrice((before.getDeclaredPrice() == null ? 0 : before
				.getDeclaredPrice()));
		// 计算归并后单重
		after.setNetWeight((before.getNetWeight() == null ? 0 : before
				.getNetWeight()));
		// 计算归并后毛重
		after.setGrossWeight((before.getGrossWeight() == null ? 0 : before
				.getGrossWeight()));
		// 计算归并后第一法定数量
		after.setLegalAmount((before.getLegalAmount() == null ? 0 : before
				.getLegalAmount()));
		// 计算归并后第二法定数量
		after.setSecondLegalAmount((before.getSecondLegalAmount() == null ? 0
				: before.getSecondLegalAmount()));
		// 原产国
		after.setCountry(before.getSalesCountry());
		// 事业部
		after.setProjectDept(before.getProjectDept());
		// before.setAfterComInfo(after);// 设置对应的归并后
		// if (before != null
		// && before.getAfterComInfo() != null
		// && before.getAfterComInfo().getUnit() != null
		// && before.getAfterComInfo().getLegalUnit() != null
		// && before.getAfterComInfo().getUnit().getName()
		// .trim().equals(
		// before.getAfterComInfo()
		// .getLegalUnit().getName().trim())) {
		// System.out.println("1111111");
		// before.setLegalAmount(before.getDeclaredAmount());
		// }
		// if (before != null
		// && before.getAfterComInfo() != null
		// && before.getAfterComInfo().getUnit() != null
		// && before.getAfterComInfo().getSecondLegalUnit() != null
		// && before.getAfterComInfo().getUnit().getName()
		// .trim().equals(
		// before.getAfterComInfo()
		// .getSecondLegalUnit().getName().trim())) {
		// System.out.println("222222");
		// before.setSecondLegalAmount(before.getDeclaredAmount());
		// }
		return after;
	}

	/**
	 * 判断成品还是料件
	 * 
	 * @param value
	 * @return
	 */
	private String isImpOrExp(String value) {
		String ImpExpType = null;
		if ("料件进口".equals(value) || "料件转厂".equals(value)
				|| "一般贸易进口".equals(value) || "修理物品".equals(value)
				|| "退料出口".equals(value) || "边角料退港".equals(value)
				|| "料件内销".equals(value) || "边角料内销".equals(value)) {
			ImpExpType = "料件";
		} else {
			ImpExpType = "成品";
		}
		return ImpExpType;
	}

	/**
	 * 断判导入资料中清单编号是否重复
	 * 
	 * @param value
	 * @return
	 */
	public boolean checkBillListNoOverlap(String value) {

		List list = this.emsEdiTrDao.findApplyToCustomsBillList(value);
		for (int i = 0; i < list.size(); i++) {
			ApplyToCustomsBillList hd = (ApplyToCustomsBillList) list.get(i);
			if (value.equals(hd.getListNo())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 根据tableName，查找map
	 * 
	 * @param tableName
	 *            要找的实体类
	 * @param codeOrName
	 *            0时用code作为键,1时用name作为键
	 * @return
	 */
	private Map getObjectMapByTableName(String tableName) {
		Map map = new HashMap();
		List list = emsEdiTrDao.findObjectByTableNames(tableName, null, null,
				false);
		if (list.isEmpty())
			return map;
		CustomBaseEntity customBaseEntity = null;
		for (int i = 0; i < list.size(); i++) {
			if (tableName.equals("Trade")) {
				customBaseEntity = (Trade) list.get(i);
			} else if (tableName.equals("Customs")) {
				customBaseEntity = (Customs) list.get(i);
			} else if (tableName.equals("Transf")) {
				customBaseEntity = (Transf) list.get(i);
			} else if (tableName.equals("Company")) {
				Company company = (Company) list.get(i);
				if (map.get(company.getName()) == null) {
					map.put(company.getName(), company);
				}
				return map;
			} else if (tableName.equals("ProjectDept")) {
				ProjectDept projectDept = (ProjectDept) list.get(i);
				if (map.get(projectDept.getName()) == null) {
					map.put(projectDept.getName(), projectDept);
				}
				// return map;
			} else if (tableName.equals("Curr")) {
				customBaseEntity = (Curr) list.get(i);
			} else if (tableName.equals("Country")) {
				customBaseEntity = (Country) list.get(i);
			} else if (tableName.equals("LevyMode")) {
				customBaseEntity = (LevyMode) list.get(i);
			} else if (tableName.equals("Uses")) {
				customBaseEntity = (Uses) list.get(i);
			}

			if (customBaseEntity != null
					&& map.get(customBaseEntity.getName()) == null) {
				map.put(customBaseEntity.getName(), customBaseEntity);
			}
			// 客户
			if (tableName.equals("ScmCoc")) {
				ScmCoc c = (ScmCoc) list.get(i);
				map.put(c.getName(), c);
			}

		}
		return map;
	}

	/**
	 * 繁体转换成简体
	 * 
	 * @param source
	 * @return
	 */

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = changeStr(source[i]);
		}
		return newStrs;
	}

	/**
	 * 繁转简
	 * 
	 * @param s
	 * @return
	 */

	private String changeStr(String s) {
		String yy = "";
		char[] xxx = s.toCharArray();
		for (int i = 0; i < xxx.length; i++) {
			String z = String.valueOf(xxx[i]);
			if (String.valueOf(xxx[i]).getBytes().length == 2) {
				if (gbHash.get(String.valueOf(xxx[i])) != null) {
					z = (String) gbHash.get(String.valueOf(xxx[i]));
				}
			}
			yy = yy + z;
		}
		return yy;
	}

	/**
	 * 初始化繁转简的Map
	 */

	private void infTojHsTable() {
		if (gbHash == null) {
			gbHash = new Hashtable();
			List list = emsEdiTrDao.find("Gbtobig", "", "");
			for (int i = 0; i < list.size(); i++) {
				Gbtobig gb = (Gbtobig) list.get(i);
				gbHash.put(gb.getBigname(), gb.getName());
			}
		}
	}

	/**
	 * 获取进出口类型
	 * 
	 * @return
	 */
	private Map getImpExpTypeMap() {
		HashMap map = new HashMap();
		map.put("料件进口", ImpExpType.DIRECT_IMPORT);
		map.put("直接进口", ImpExpType.DIRECT_IMPORT);
		map.put("料件转厂", ImpExpType.TRANSFER_FACTORY_IMPORT);
		map.put("转厂进口", ImpExpType.TRANSFER_FACTORY_IMPORT);
		map.put("退厂返工", ImpExpType.BACK_FACTORY_REWORK);
		map.put("一般贸易进口", ImpExpType.GENERAL_TRADE_IMPORT);
		map.put("进料成品退换", ImpExpType.IMPORT_EXG_BACK);
		map.put("修理物品", ImpExpType.IMPORT_REPAIR_MATERIAL);

		map.put("成品出口", ImpExpType.DIRECT_EXPORT);
		map.put("直接出口", ImpExpType.DIRECT_EXPORT);

		map.put("转厂出口", ImpExpType.TRANSFER_FACTORY_EXPORT);
		map.put("退料出口", ImpExpType.BACK_MATERIEL_EXPORT);
		map.put("返工复出", ImpExpType.REWORK_EXPORT);
		map.put("边角料退港", ImpExpType.REMIAN_MATERIAL_BACK_PORT);
		map.put("边角料内销", ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
		map.put("一般贸易出口", ImpExpType.GENERAL_TRADE_EXPORT);
		map.put("修理物品复出", ImpExpType.EXPORT_MATERIAL_REBACK);
		map.put("进料成品退换复出", ImpExpType.EXPORT_EXG_REBACK);
		map.put("料件内销", ImpExpType.MATERIAL_DOMESTIC_SALES);
		return map;
	}

	/**
	 * 获取保存路径
	 * 
	 * @return
	 */
	protected String getQPBGDXmlPath() {
		String dir = this.getBpara(BcusParameter.LOAD_APPLY_QP_BGD_DIR);
		return (dir == null ? "" : dir);
	}

	/**
	 * 判断是否是XML文件
	 */
	private boolean isxmlfile(File file) {
		if (file.isDirectory()) {
			return false;
		} else {
			String str = new String();
			str = file.getName();
			str = str.substring(file.getName().length() - 4).toLowerCase();
			return str.equals(".xml");
		}
	}

	// 返回参数设定
	public String getBpara(int type) {
		List list = emsEdiTrDao
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}

	private void createDefaultDirectory(String path) {
		String successDirI = path + File.separator + "success" + File.separator
				+ "I";
		String successDirE = path + File.separator + "success" + File.separator
				+ "E";
		String failI = path + File.separator + "fail" + File.separator + "I";
		String failE = path + File.separator + "fail" + File.separator + "E";
		File file = new File(successDirI);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(successDirE);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(failI);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(failE);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 读取目录下的所有文件
	 */
	private List<File> getAllXmlFiles(String path, boolean isImportBGD) {
		File curdir = new File(path);
		if (!curdir.exists()) {
			throw new RuntimeException("设定的报关单导入路径：" + path + " 不存在！");
		}
		List<File> lsXmlFile = new ArrayList<File>();
		File[] ieDir = curdir.listFiles();
		for (int j = 0; j < ieDir.length; j++) {
			if (!ieDir[j].isDirectory()) {
				continue;
			}
			if (isImportBGD) {
				if (!"I".equals(ieDir[j].getName())) {
					continue;
				}
			} else {
				if (!"E".equals(ieDir[j].getName())) {
					continue;
				}
			}
			File[] xmlFiles = ieDir[j].listFiles();
			for (int m = 0; m < xmlFiles.length; m++) {
				if (xmlFiles[m].isDirectory()) {
					continue;
				}
				lsXmlFile.add(xmlFiles[m]);
			}
		}
		// }
		return lsXmlFile;
	}

	/**
	 * 移动文件
	 * 
	 * @param isImportBGD
	 * @param path
	 * @param lsSuccess
	 * @param lsError
	 */
	private void moveFile(boolean isImportBGD, String path, List lsSuccess,
			List lsError) {
		System.out.println("lsSuccess.size()=" + lsSuccess.size());
		System.out.println("lsError.size()=" + lsError.size());
		String successDirI = path + File.separator + "success" + File.separator
				+ "I";
		String successDirE = path + File.separator + "success" + File.separator
				+ "E";
		String failI = path + File.separator + "fail" + File.separator + "I";
		String failE = path + File.separator + "fail" + File.separator + "E";
		for (int i = 0; i < lsSuccess.size(); i++) {
			BaseApplyLoadXML temp = (BaseApplyLoadXML) lsSuccess.get(i);
			String fileName = temp.getFileName();
			if (isImportBGD) {
				File file = new File(path + File.separator + "I"
						+ File.separator + fileName);
				CspMessageFunctions.moveFile(file, successDirI);
			} else {
				File file = new File(path + File.separator + "E"
						+ File.separator + fileName);
				CspMessageFunctions.moveFile(file, successDirE);
			}
		}
		for (int i = 0; i < lsError.size(); i++) {
			BaseApplyLoadXML temp = (BaseApplyLoadXML) lsError.get(i);
			String fileName = temp.getFileName();
			if (isImportBGD) {
				File file = new File(path + File.separator + "I"
						+ File.separator + fileName);
				CspMessageFunctions.moveFile(file, failI);
			} else {
				File file = new File(path + File.separator + "E"
						+ File.separator + fileName);
				CspMessageFunctions.moveFile(file, failE);
			}
		}

	}

	/**
	 * 
	 * @author Administrator date : Mar 29, 2012 4:01:38 PM
	 * @param head
	 * @param beginSeqNum
	 * @param endSeqNum
	 * @param exgModifyMark
	 * @param bomModifyMark
	 * @param jRadioButtonBigBOMState
	 * @return
	 */
	public List findEmsHeadH2kBomExport(EmsHeadH2k head, String beginSeqNum,
			String endSeqNum, String exgModifyMark, String bomModifyMark,
			boolean jRadioButtonBigBOMState) {
		List returnList = new ArrayList();
		List list = emsEdiTrDao.findEmsHeadH2kBomExport(head, beginSeqNum,
				endSeqNum, exgModifyMark, bomModifyMark);
		if (jRadioButtonBigBOMState) {
			Map<String, Integer> mapVersion = new HashMap<String, Integer>();
			List listVersionList = emsEdiTrDao
					.findMaxVersionFromEmsHeadH2kVersion(head, exgModifyMark);
			for (int i = 0; i < listVersionList.size(); i++) {
				Object[] objects = (Object[]) listVersionList.get(i);
				mapVersion.put(objects[0] + "/" + objects[1],
						(Integer) objects[1]);
			}
			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2kBom emsHeadH2kBom = (EmsHeadH2kBom) list.get(i);
				Integer seqnum = emsHeadH2kBom.getEmsHeadH2kExg().getSeqNum();
				Integer version = emsHeadH2kBom.getEmsHeadH2kVersion()
						.getVersion();
				if (mapVersion.get(seqnum + "/" + version) == null) {
					continue;
				}
				returnList.add(emsHeadH2kBom);
			}
			return returnList;
		} else {
			return list;
		}

	}

	/**
	 * 判断账册成品表、及单耗表的修改标志是否一致,返回不一致部分成品
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	public String checkEmsExgBomModifyMarkIsUnit(EmsHeadH2k emsHeadH2k) {
		StringBuffer message = new StringBuffer();
		Set set = new HashSet<Integer>();
		List<EmsHeadH2kBom> bomList = this.emsEdiTrDao
				.checkEmsExgBomModifyMarkIsUnit(emsHeadH2k);
		for (int i = 0; i < bomList.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) bomList.get(i);
			set.add(bom.getEmsHeadH2kVersion().getEmsHeadH2kExg().getSeqNum());
		}
		if (set.size() > 0) {
			Object[] obj = set.toArray();
			if (obj.length > 30) {
				for (int i = 0; i < 30; i++) {
					message.append(obj[i] + ",");
				}
				message.append("。。");
			} else {
				for (int i = 0; i < obj.length; i++) {
					message.append(obj[i] + ",");
				}
			}
		}
		return message.toString();
	}
}