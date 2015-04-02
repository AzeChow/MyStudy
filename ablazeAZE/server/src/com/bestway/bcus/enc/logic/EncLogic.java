/*
 * Created on 2004-7-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.logic;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contractstat.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.checkcancel.entity.TempDD;
import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.BcusContractExeInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.enc.entity.DiffrenceAnalyseCommInfo;
import com.bestway.bcus.enc.entity.ExpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ExportInvoiceItem;
import com.bestway.bcus.enc.entity.ImpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.MakeApplyToCustoms;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.enc.entity.MakeImpExpRequestBill;
import com.bestway.bcus.enc.entity.MakeListToCustoms;
import com.bestway.bcus.enc.entity.TempBillListCommInfo;
import com.bestway.bcus.enc.entity.TempComplex;
import com.bestway.bcus.enc.entity.TempCustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.TempCustomsList;
import com.bestway.bcus.enc.entity.TempImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempRemainMaterialStat;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.manualdeclare.entity.TempFinancialReport;
import com.bestway.bcus.system.dao.CreateDirDao;
import com.bestway.bcus.system.dao.SysCodeDao;
import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.bcus.system.entity.BcusParameterSet;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.AddType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SBillType;
import com.bestway.common.fpt.dao.FptManageDao;
import com.bestway.common.fpt.entity.FptBillPriceMaintenance;
import com.bestway.common.fpt.logic.FptManageLogic;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.transferfactory.entity.BillPriceMaintenance;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.TempAllTypeInnerMergeDate;
import com.bestway.customs.common.logic.BaseEncLogic;

/**
 * saveCustomsDeclarationCommInfox
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EncLogic extends BaseEncLogic {
	private SysCodeDao sysCodeDao = null;

	public SysCodeDao getSysCodeDao() {
		return sysCodeDao;
	}

	public void setSysCodeDao(SysCodeDao sysCodeDao) {
		this.sysCodeDao = sysCodeDao;
	}

	/**
	 * 经营范围Dao
	 */
	private EmsEdiTrDao emsEdiTrDao = null;

	/**
	 * 进出口申请单内容
	 */
	private EncDao encDao = null;

	/**
	 * 基础代码Dao
	 */
	private BaseCodeDao baseCodeDao = null;

	/**
	 * 转厂管理的Logic
	 */
	private FptManageLogic fptManageLogic = null;

	/**
	 * 转厂管理的Dao
	 */
	private FptManageDao fptManageDao = null;

	/**
	 * 创建目录Dao
	 */
	private CreateDirDao createDirDao = null;

	/**
	 * 常用代码Dao
	 */
	private CommonBaseCodeDao commonCodeDao = null;

	private DataSource dataSource = null;

	/**
	 * 海关帐Dao
	 */
	private CasDao casDao = null;

	private Hashtable hs = new Hashtable();

	private Map<String, TempDD> timehs = new HashMap<String, TempDD>();

	private Hashtable tranThs = new Hashtable();

	// private Hashtable hs1 = new Hashtable();

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 申请单转报关单1：申请单折算报关单 2：汇总（无第一法定数量，第二法定数量） 取得创建目录Dao
	 * 
	 * @return 创建目录Dao
	 */
	public CreateDirDao getCreateDirDao() {
		return createDirDao;
	}

	/**
	 * 设置创建目录Dao
	 * 
	 * @param createDirDao
	 *            创建目录Dao
	 */
	public void setCreateDirDao(CreateDirDao createDirDao) {
		this.createDirDao = createDirDao;
	}

	/**
	 * 取得转厂管理的Logic
	 * 
	 * @return 转厂管理的Logic
	 */
	public FptManageLogic getFptManageLogic() {
		return fptManageLogic;
	}

	/**
	 * 取得海关帐Dao
	 * 
	 * @return 海关帐Dao
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设置海关帐Dao
	 * 
	 * @param casDao
	 *            海关帐Dao
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * 取得转厂管理的Dao
	 * 
	 * @return 转厂管理的Dao
	 */
	public FptManageDao getFptManageDao() {
		return fptManageDao;
	}

	/**
	 * 设计转厂管理的Dao
	 * 
	 * @param transferFactoryManageDao
	 *            转厂管理的Dao
	 */
	public void setFptManageDao(FptManageDao transferFactoryManageDao) {
		this.fptManageDao = transferFactoryManageDao;
	}

	/**
	 * 取得常用代码Dao
	 * 
	 * @return 常用代码Dao
	 */
	public CommonBaseCodeDao getCommonCodeDao() {
		return commonCodeDao;
	}

	/**
	 * 设计常用代码Dao
	 * 
	 * @param commonCodeDao
	 *            常用代码Dao
	 */
	public void setCommonCodeDao(CommonBaseCodeDao commonCodeDao) {
		this.commonCodeDao = commonCodeDao;
	}

	/**
	 * 设计转厂管理的Logic
	 * 
	 * @param transferFactoryManageLogic
	 *            转厂管理的Logic
	 */
	public void setFptManageLogic(FptManageLogic transferFactoryManageLogic) {
		this.fptManageLogic = transferFactoryManageLogic;
	}

	/**
	 * 取得经营范围Dao
	 * 
	 * @return 经营范围Dao
	 */
	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	/**
	 * 设计经营范围Dao
	 * 
	 * @param emsEdiTrDao
	 *            经营范围Dao
	 */
	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	/**
	 * 查找所有进出口报关清单
	 * 
	 * @return 进出口报关清单
	 */
	public List findApplyToCustomsBillList() {
		return ((EncDao) this.getBaseEncDao()).findApplyToCustomsBillList();
	}

	/**
	 * 根据清单类型查找报关清单
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 与指定的进出口标志符合的报关清单
	 */
	public List findApplyToCustomsBillListByType(int impExpFlag,
			Date beginDate, Date endDate) {
		return ((EncDao) this.getBaseEncDao())
				.findApplyToCustomsBillListByType(impExpFlag, beginDate,
						endDate);
	}

	/**
	 * 删除报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	/*
	 * public void deleteApplyToCustomsBillList( ApplyToCustomsBillList
	 * applyToCustomsBillList) {
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String listNo = applyToCustomsBillList.getListNo(); List list = null;
	 * AtcMergeAfterComInfo atcMergeAfterComInfo = null; AtcMergeBeforeComInfo
	 * atcMergeBeforeComInfo = null; list = ((EncDao) this.getBaseEncDao())
	 * .findAtcMergeBeforeComInfoByListID(applyToCustomsBillList); // 清单归并前信息
	 * for (int i = 0; i < list.size(); i++) { atcMergeBeforeComInfo =
	 * (AtcMergeBeforeComInfo) list.get(i); // 清单归并前 // // // // 回写转厂关封 // // //
	 * if (atcMergeBeforeComInfo.getIsAutoCreate() != null) { // if
	 * (atcMergeBeforeComInfo.getIsAutoCreate().booleanValue() == // true) { //
	 * transferFactoryManageLogic //
	 * .writeBackTransferFactoryBillToApplyToCustomsBillId(atcMergeBeforeComInfo
	 * // .getId()); // } // } // // 回写进出口申请单 //
	 * this.writeBackImpExpRequestBillByApplyToCustomsBillId(
	 * atcMergeBeforeComInfo.getId(), listNo); ((EncDao) this.getBaseEncDao())
	 * .deleteAtcMergeBeforeComInfo(atcMergeBeforeComInfo); } list = ((EncDao)
	 * this.getBaseEncDao())
	 * .findAtcMergeAfterComInfoByListID(applyToCustomsBillList); for (int i =
	 * 0; i < list.size(); i++) { atcMergeAfterComInfo = (AtcMergeAfterComInfo)
	 * list.get(i); transferFactoryManageLogic
	 * .writeBackTransFactBillWhenDelAfterCommInfo(atcMergeAfterComInfo
	 * .getId()); ((EncDao) this.getBaseEncDao())
	 * .deleteAtcMergeAfterComInfo(atcMergeAfterComInfo); } String
	 * applyToCustomsBillListId = applyToCustomsBillList.getId(); ((EncDao)
	 * this.getBaseEncDao())
	 * .deleteApplyToCustomsBillList(applyToCustomsBillList); }
	 */

	/**
	 * 保存报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	public void saveApplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		((EncDao) this.getBaseEncDao())
				.saveApplyToCustomsBillList(applyToCustomsBillList);
	}

	/**
	 * 根据清单编号查询归并后商品信息
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 与指定的清单编号匹配的归并后商品信息
	 */
	public List findAtcMergeAfterComInfoByListID(
			ApplyToCustomsBillList applyToCustomsBillList) {
		List list = ((EncDao) this.getBaseEncDao())
				.findAtcMergeAfterComInfoByListID(applyToCustomsBillList);

		Collections.sort(list);
		return list;
	}

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @return 与指定的归并后商品信息id匹配的归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByAfterID(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		// return ((EncDao) this.getBaseEncDao())
		// .findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfo);
		List list = ((EncDao) this.getBaseEncDao())
				.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfo);
		// if (list.size() > 0) {
		// AtcMergeBeforeComInfo totalBeforeInfo = new AtcMergeBeforeComInfo();
		// Materiel materiel = new Materiel();
		// // materiel.setPtNo("合计");
		// totalBeforeInfo.setMateriel(materiel);
		// for (int i = 0; i < list.size(); i++) {
		// AtcMergeBeforeComInfo beforeInfo = (AtcMergeBeforeComInfo) list
		// .get(i);
		// totalBeforeInfo.setDeclaredAmount(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo
		// .getDeclaredAmount())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getDeclaredAmount()));
		// totalBeforeInfo.setLegalAmount(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getLegalAmount())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getLegalAmount()));
		// totalBeforeInfo.setSecondLegalAmount(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo
		// .getSecondLegalAmount())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getSecondLegalAmount()));
		// totalBeforeInfo.setGrossWeight(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getGrossWeight())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getGrossWeight()));
		// totalBeforeInfo.setNetWeight(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getNetWeight())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getNetWeight()));
		// totalBeforeInfo
		// .setPiece((totalBeforeInfo.getPiece() == null ? 0
		// : totalBeforeInfo.getPiece())
		// + (beforeInfo.getPiece() == null ? 0
		// : beforeInfo.getPiece()));
		// totalBeforeInfo.setTotalPrice(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getTotalPrice())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getTotalPrice()));
		// totalBeforeInfo.setWorkUsd(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getWorkUsd())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getWorkUsd()));
		// }
		// list.add(totalBeforeInfo);
		// }
		return list;
	}

	/**
	 * 根据报送单信息查询归并前商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @return 与指定的归并后商品信息id匹配的归并前商品信息
	 */
	public List findAllAtcMergerBeforeComInfo(Request request,
			ApplyToCustomsBillList billList) {
		List list = ((EncDao) getBaseEncDao())
				.findAllAtcMergerBeforeComInfo(billList);
		// if (list.size() > 0) {
		// AtcMergeBeforeComInfo totalBeforeInfo = new AtcMergeBeforeComInfo();
		// Materiel materiel = new Materiel();
		// materiel.setPtNo("合计");
		// totalBeforeInfo.setMateriel(materiel);
		// for (int i = 0; i < list.size(); i++) {
		// AtcMergeBeforeComInfo beforeInfo = (AtcMergeBeforeComInfo) list
		// .get(i);
		// totalBeforeInfo.setDeclaredAmount(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo
		// .getDeclaredAmount())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getDeclaredAmount()));
		// totalBeforeInfo.setLegalAmount(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getLegalAmount())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getLegalAmount()));
		// totalBeforeInfo.setSecondLegalAmount(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo
		// .getSecondLegalAmount())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getSecondLegalAmount()));
		// totalBeforeInfo.setGrossWeight(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getGrossWeight())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getGrossWeight()));
		// totalBeforeInfo.setNetWeight(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getNetWeight())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getNetWeight()));
		// totalBeforeInfo
		// .setPiece((totalBeforeInfo.getPiece() == null ? 0
		// : totalBeforeInfo.getPiece())
		// + (beforeInfo.getPiece() == null ? 0
		// : beforeInfo.getPiece()));
		// totalBeforeInfo.setTotalPrice(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getTotalPrice())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getTotalPrice()));
		// totalBeforeInfo.setWorkUsd(CommonUtils
		// .getDoubleExceptNull(totalBeforeInfo.getWorkUsd())
		// + CommonUtils.getDoubleExceptNull(beforeInfo
		// .getWorkUsd()));
		// }
		// list.add(totalBeforeInfo);
		// }
		return list;
	}

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param atcMergeBeforeComInfo
	 *            归并前商品信息
	 * @return 删除后报关清单的信息
	 */
	public ApplyToCustomsBillList deleteAtcMergeBeforeComInfo(
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		AtcMergeAfterComInfo atcMergeAfterComInfo = atcMergeBeforeComInfo
				.getAfterComInfo();
		ApplyToCustomsBillList billList = atcMergeAfterComInfo.getBillList();
		// 删除归并前数据
		((EncDao) this.getBaseEncDao())
				.deleteAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
		// //
		// // 回写转厂关封
		// //
		// if (atcMergeBeforeComInfo.getIsAutoCreate() != null) {
		// if (atcMergeBeforeComInfo.getIsAutoCreate().booleanValue() == true) {
		// transferFactoryManageLogic
		// .writeBackTransferFactoryBillToApplyToCustomsBillId(atcMergeBeforeComInfo
		// .getId());
		// }
		// }
		//
		// 回写进出口申请单
		//
		this.writeBackImpExpRequestBillByApplyToCustomsBillId(
				atcMergeBeforeComInfo.getId(), billList.getListNo());

		List list = ((EncDao) this.getBaseEncDao())
				.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfo);
		if (list.size() < 1) {
			// transferFactoryManageLogic
			// .writeBackTransFactBillWhenDelAfterCommInfo(atcMergeAfterComInfo
			// .getId());
			/*
			 * ((EncDao) this.getBaseEncDao())
			 * .deleteAtcMergeAfterComInfo(atcMergeAfterComInfo);
			 */
			billList.setMaterialNum(Integer.valueOf(((EncDao) this
					.getBaseEncDao()).getAtcMergeAfterCommonNum(billList)));
			((EncDao) this.getBaseEncDao())
					.saveApplyToCustomsBillList(billList);
		}
		statMergeAfterCommInfo(atcMergeAfterComInfo);
		return billList;
	}

	/**
	 * 删除报关清单归并前商品信息(多笔)
	 * 
	 * @param list
	 *            报关清单归并前商品信息
	 * @return 删除归并前商品信息后报关清单的信息
	 */
	public ApplyToCustomsBillList deleteAtcMergeBeforeComInfo(List list) {
		if (list.size() < 1) {
			return null;
		}
		AtcMergeBeforeComInfo atcMergeBeforeComInfo = null;
		AtcMergeAfterComInfo atcMergeAfterComInfo = null;
		atcMergeAfterComInfo = ((AtcMergeBeforeComInfo) list.get(0))
				.getAfterComInfo();
		ApplyToCustomsBillList billList = atcMergeAfterComInfo.getBillList();
		for (int i = 0; i < list.size(); i++) {
			atcMergeBeforeComInfo = (AtcMergeBeforeComInfo) list.get(i);
			//
			// 回写进出口申请单
			//
			this.writeBackImpExpRequestBillByApplyToCustomsBillId(
					atcMergeBeforeComInfo.getId(), billList.getListNo());
			((EncDao) this.getBaseEncDao())
					.deleteAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
		}
		list = ((EncDao) this.getBaseEncDao())
				.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfo);
		if (list.size() < 1) {
			// transferFactoryManageLogic
			// .writeBackTransFactBillWhenDelAfterCommInfo(atcMergeAfterComInfo
			// .getId());
			/*
			 * ((EncDao) this.getBaseEncDao())
			 * .deleteAtcMergeAfterComInfo(atcMergeAfterComInfo);
			 */
			billList.setMaterialNum(Integer.valueOf(((EncDao) this
					.getBaseEncDao()).getAtcMergeAfterCommonNum(billList)));
			((EncDao) this.getBaseEncDao())
					.saveApplyToCustomsBillList(billList);
		}
		statMergeAfterCommInfo(atcMergeAfterComInfo);
		return billList;
	}

	/**
	 * 保存报关清单归并前商品信息
	 */
	public void saveAtcMergeBeforeComInfo(AtcMergeBeforeComInfo beforeComInfo) {
		System.out.println("test");

		AtcMergeAfterComInfo atcMergeAfterComInfo = beforeComInfo
				.getAfterComInfo();
		atcMergeAfterComInfo = (AtcMergeAfterComInfo) this.encDao.get(
				AtcMergeAfterComInfo.class, atcMergeAfterComInfo.getId());
		if (atcMergeAfterComInfo != null) {
			atcMergeAfterComInfo
					.setVersion(beforeComInfo.getVersion() == null ? "0"
							: beforeComInfo.getVersion().toString());
			// ----------------------------------------------
			// 转报关单时用到
			atcMergeAfterComInfo.setLevyMode(beforeComInfo.getLevyMode());
			atcMergeAfterComInfo.setUsesCode(beforeComInfo.getUsesCode());
			atcMergeAfterComInfo.setProjectDept(beforeComInfo.getProjectDept());
			atcMergeAfterComInfo.setCountry(beforeComInfo.getSalesCountry());
			((EncDao) this.getBaseEncDao())
					.saveAtcMergeAfterComInfo(atcMergeAfterComInfo);
		}
		((EncDao) this.getBaseEncDao())
				.saveAtcMergeBeforeComInfo(beforeComInfo);
		// ----------------------------------------------
		statMergeAfterCommInfo(atcMergeAfterComInfo);

	}

	// 更新清单
	public void changeApplyBillState(Integer flat) {
		// List list = ((EncDao) this.getBaseEncDao()).findApplyBillList(
		// ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION,
		List list = ((EncDao) this.getBaseEncDao())
				.findApplyBillListeffectstate(true, null, flat, null, null);
		for (int i = 0; i < list.size(); i++) {
			ApplyToCustomsBillList obj = (ApplyToCustomsBillList) list.get(i);
			List isTraList = ((EncDao) this.getBaseEncDao())
					.findAtcMergeAfterComInfoNotToCustoms(obj,
							new Boolean(true));
			if (isTraList != null && isTraList.size() == 0) {
				obj.setListState(ApplyToCustomsBillList.DRAFT);
				((EncDao) this.getBaseEncDao()).saveApplyToCustomsBillList(obj);
			}
		}
	}

	/**
	 * 统计报关清单归并后的数据信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后数据信息
	 */
	private void statMergeAfterCommInfo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		atcMergeAfterComInfo = (AtcMergeAfterComInfo) this.encDao.get(
				AtcMergeAfterComInfo.class, atcMergeAfterComInfo.getId());
		System.out.println("1111111111111111111111111111111111");
		Hashtable ht = getMergeBeforeAmountByMergeAfter(atcMergeAfterComInfo);
		atcMergeAfterComInfo.setDeclaredAmount(Double.valueOf(ht.get(
				"declaredAmount").toString()));
		atcMergeAfterComInfo.setLegalAmount(Double.valueOf(ht
				.get("legalAmount").toString()));
		atcMergeAfterComInfo.setSecondLegalAmount(Double.valueOf(ht.get(
				"secondLegalAmount").toString()));
		atcMergeAfterComInfo.setGrossWeight(Double.valueOf(ht
				.get("grossWeight").toString()));
		atcMergeAfterComInfo.setNetWeight(Double.valueOf(ht.get("netWeight")
				.toString()));
		atcMergeAfterComInfo.setTotalPrice(Double.valueOf(ht.get("totalPrice")
				.toString()));
		atcMergeAfterComInfo.setWorkUsd(Double.valueOf(ht.get("workUsd")
				.toString()));
		atcMergeAfterComInfo.setPiece(Integer.valueOf(ht.get("piece")
				.toString()));
		atcMergeAfterComInfo.setBoxNo(ht.get("boxNo").toString());
		((EncDao) this.getBaseEncDao())
				.saveAtcMergeAfterComInfo(atcMergeAfterComInfo);
	}

	/**
	 * 将报关清单归并前商品信息的申报数量,法定数量...等进行统计
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @return 报关清单归并前商品信息的统计情况
	 */
	private Hashtable getMergeBeforeAmountByMergeAfter(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		Hashtable ht = new Hashtable();
		// AtcMergeBeforeComInfo atcMergeBeforeComInfo = null;
		List list = ((EncDao) this.getBaseEncDao())
				.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfo);
		double declaredAmount = 0;// 申报数量
		double legalAmount = 0;// 法定数量
		double secondLegalAmount = 0;// 第二法定数量
		double grossWeight = 0;// 毛重
		double netWeight = 0;// 净重
		double totalPrice = 0;// 净重
		int piece = 0;// 件数
		String boxNoString = "";// 箱号
		Double workUsd = 0.0; // 加工费总价
		for (int i = 0; i < list.size(); i++) {
			AtcMergeBeforeComInfo atcMergeBeforeComInfo = (AtcMergeBeforeComInfo) list
					.get(i);
			if (atcMergeBeforeComInfo.getDeclaredAmount() != null) {
				declaredAmount += atcMergeBeforeComInfo.getDeclaredAmount()
						.doubleValue();
			}
			if (atcMergeBeforeComInfo.getLegalAmount() != null) {
				legalAmount += atcMergeBeforeComInfo.getLegalAmount()
						.doubleValue();
			}
			if (atcMergeBeforeComInfo.getSecondLegalAmount() != null) {
				secondLegalAmount += atcMergeBeforeComInfo
						.getSecondLegalAmount().doubleValue();
			}
			if (atcMergeBeforeComInfo.getGrossWeight() != null) {
				grossWeight += atcMergeBeforeComInfo.getGrossWeight()
						.doubleValue();
			}
			if (atcMergeBeforeComInfo.getNetWeight() != null) {
				netWeight += atcMergeBeforeComInfo.getNetWeight().doubleValue();
			}
			if (atcMergeBeforeComInfo.getTotalPrice() != null) {
				totalPrice += atcMergeBeforeComInfo.getTotalPrice()
						.doubleValue();
			}
			if (atcMergeBeforeComInfo.getWorkUsd() != null) {
				workUsd += atcMergeBeforeComInfo.getWorkUsd().doubleValue();
			}
			// 修改归并前同时修改归并后的件数
			if (atcMergeBeforeComInfo.getPiece() != null) {
				try {
					Integer.valueOf(atcMergeBeforeComInfo.getPiece());
				} catch (Exception e) {
					throw new RuntimeException("清单中的件数不合法");
				}
				piece += atcMergeBeforeComInfo.getPiece().intValue();
			}
			// 箱数
			String boxNo = boxNoString;
			// System.out.println("===boxBo==" + boxNo);
			if (boxNo != null && !"".equals(boxNo)) {
				if (atcMergeBeforeComInfo.getBoxNo() != null
						&& !"".equals(atcMergeBeforeComInfo.getBoxNo())) {
					boxNoString = boxNo + ","
							+ atcMergeBeforeComInfo.getBoxNo();
				} else {
					boxNoString = boxNo;
				}
			} else {
				boxNoString = atcMergeBeforeComInfo.getBoxNo() == null ? ""
						: atcMergeBeforeComInfo.getBoxNo();
			}
		}
		ht.put("declaredAmount", Double.valueOf(declaredAmount));
		ht.put("legalAmount", Double.valueOf(legalAmount));
		ht.put("secondLegalAmount", Double.valueOf(secondLegalAmount));
		ht.put("grossWeight", Double.valueOf(grossWeight));
		ht.put("netWeight", Double.valueOf(netWeight));
		ht.put("totalPrice", Double.valueOf(totalPrice));
		ht.put("workUsd", Double.valueOf(workUsd));
		ht.put("piece", Integer.valueOf(piece));
		ht.put("boxNo", boxNoString);
		return ht;
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param declaredDataList
	 *            临时单据商品信息
	 * @param billList
	 *            报关清单
	 */
	public void saveAtcMergeBeforeComInfo(List declaredDataList,
			ApplyToCustomsBillList billList) {
		AtcMergeAfterComInfo atcMergeAfterComInfo = null;
		AtcMergeBeforeComInfo atcMergeBeforeComInfo = null;
		TempBillListCommInfo commInfoData = null;
		for (int i = 0; i < declaredDataList.size(); i++) {
			commInfoData = (TempBillListCommInfo) declaredDataList.get(i);
			List list = ((EncDao) this.getBaseEncDao())
					.findAtcMergeAfterComInfo(billList, commInfoData
							.getEmsSerialNo(), commInfoData.getComplex()
							.getCode());
			if (list.size() > 0) {
				atcMergeAfterComInfo = (AtcMergeAfterComInfo) list.get(0);
				billList = atcMergeAfterComInfo.getBillList();
			} else {
				atcMergeAfterComInfo = new AtcMergeAfterComInfo();
				atcMergeAfterComInfo.setEmsSerialNo(Integer
						.valueOf(commInfoData.getEmsSerialNo()));
				atcMergeAfterComInfo.setComplex(commInfoData.getComplex());
				atcMergeAfterComInfo.setComplexName(commInfoData.getName());
				atcMergeAfterComInfo.setComplexSpec(commInfoData.getSpec());
				atcMergeAfterComInfo.setBillList(billList);
				if (commInfoData.getUnit() != null) {
					atcMergeAfterComInfo.setUnit(commInfoData.getUnit());
				}
				if (commInfoData.getLegalUnit() != null) {
					atcMergeAfterComInfo.setLegalUnit(commInfoData
							.getLegalUnit());
				}
				if (commInfoData.getLegalUnit2() != null) {
					atcMergeAfterComInfo.setLegalUnit(commInfoData
							.getLegalUnit2());
				}
				((EncDao) this.getBaseEncDao())
						.saveAtcMergeAfterComInfo(atcMergeAfterComInfo);
				statMergeAfterCommInfo(atcMergeAfterComInfo);
			}
			atcMergeBeforeComInfo = new AtcMergeBeforeComInfo();
			atcMergeBeforeComInfo.setMateriel(commInfoData.getMateriel());
			atcMergeBeforeComInfo.setAfterComInfo(atcMergeAfterComInfo);
			((EncDao) this.getBaseEncDao())
					.saveAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
		}
		billList.setMaterialNum(Integer.valueOf(((EncDao) this.getBaseEncDao())
				.getAtcMergeAfterCommonNum(billList)));
		((EncDao) this.getBaseEncDao()).saveApplyToCustomsBillList(billList);
	}

	/**
	 * 取得临时申报商品信息
	 * 
	 * @param billList
	 *            报关清单
	 * @param materielProductFlag
	 *            物料成品标志
	 * @return 临时申报商品信息
	 */
	public List getTempDeclaredCommInfo(ApplyToCustomsBillList billList,
			Integer materielProductFlag) {
		List result = new ArrayList();
		List lsParameters = this.createDirDao
				.findnameValues(ParameterType.emsFrom);
		String emsFrom = "0";
		if (lsParameters.size() > 0) {
			ParameterSet para = (ParameterSet) lsParameters.get(0);
			emsFrom = para.getNameValues();
		}
		if (materielProductFlag == null ? true : materielProductFlag.toString()
				.equals(MaterielType.MATERIEL)) {
			List list = new ArrayList();
			if (emsFrom.trim().equals("1")) {
				list = ((EncDao) this.getBaseEncDao())
						.findDeclaredMaterielInfo(DeclareState.PROCESS_EXE,
								billList);
			} else if (emsFrom.trim().equals("2")) {
				list = ((EncDao) this.getBaseEncDao())
						.findDeclaredMaterielInfo(DeclareState.APPLY_POR,
								billList);
			} else if (emsFrom.trim().equals("3")) {
				list = ((EncDao) this.getBaseEncDao())
						.findTempCommInfoFromInnerMergeForAtc(false, billList);
			}
			convertArrayToList(list, result, MaterielType.MATERIEL);
		} else {
			List list = new ArrayList();
			if (emsFrom.trim().equals("1")) {
				list = ((EncDao) this.getBaseEncDao()).findDeclaredProductInfo(
						DeclareState.PROCESS_EXE, billList);
			} else if (emsFrom.trim().equals("2")) {
				list = ((EncDao) this.getBaseEncDao()).findDeclaredProductInfo(
						DeclareState.APPLY_POR, billList);
			} else if (emsFrom.trim().equals("3")) {
				list = ((EncDao) this.getBaseEncDao())
						.findTempCommInfoFromInnerMergeForAtc(true, billList);
			}
			convertArrayToList(list, result, MaterielType.FINISHED_PRODUCT);
		}
		return result;
	}

	/**
	 * 将数组转换成List
	 * 
	 * @param source
	 *            list的对象来源
	 * @param dest
	 *            转换后的list内容
	 * @param materielType
	 *            物料类型
	 */
	private void convertArrayToList(List source, List dest, String materielType) {
		TempBillListCommInfo commInfo = null;
		for (int i = 0; i < source.size(); i++) {
			commInfo = new TempBillListCommInfo();
			Object[] objs = (Object[]) source.get(i);
			// commInfo.setIsPutOnRecord(new Boolean(true));
			if (objs[0] != null) {
				commInfo.setEmsSerialNo(objs[0].toString());
			}
			if (objs[1] != null) {
				commInfo.setMateriel((Materiel) objs[1]);
			}
			if (objs[2] != null) {
				commInfo.setComplex((Complex) objs[2]);
			}
			if (objs[3] != null) {
				commInfo.setUnit((Unit) objs[3]);
			}
			if (objs[4] != null) {
				commInfo.setLegalUnit((Unit) objs[4]);
			}
			if (objs[5] != null) {
				commInfo.setLegalUnit2((Unit) objs[5]);
			}
			if (objs[6] != null) {
				commInfo.setName(objs[6].toString());
			}
			if (objs[7] != null) {
				commInfo.setSpec(objs[7].toString());
			}
			commInfo.setMaterielType(materielType);
			dest.add(commInfo);
		}
	}

	/**
	 * 获得最大的单据号来自进出口申请单表
	 * 
	 * @param type
	 *            此参数没有被用到
	 * @return 最大的编号或者是0001
	 */
	public String getMaxBillNoByType(int type) {
		String strtoday = convertDateToString();
		List list = ((EncDao) this.getBaseEncDao())
				.getMaxBillNoByType(strtoday);
		if (list.size() <= 0 || list.get(0) == null) {
			return String.valueOf(getBillNo("0001"));
		}
		String billNoStr = list.get(0).toString().trim();
		String prefix = "";
		try {
			prefix = billNoStr.substring(0, 8);
		} catch (Exception e) {
			prefix = "1";
		}
		if (prefix.equals(convertDateToString())) {
			String suffix = billNoStr.substring(billNoStr.length() - 4);
			String suffixNumber = String.valueOf(Integer.parseInt(suffix) + 1);
			suffix = replace("0", 4 - suffixNumber.length()) + suffixNumber;
			return String.valueOf(getBillNo(suffix));
		} else {
			return String.valueOf(getBillNo("0001"));
		}
	}

	/**
	 * 获得最大的单据号来自进出口申请单表
	 * 
	 * @param comapny
	 *            公司名称
	 * @return 最大的单据号或0001
	 */
	public String getMaxBillNoByCompany(Company comapny) {
		List list = ((EncDao) this.getBaseEncDao()).getMaxBillNo(comapny);
		if (list.size() <= 0 || list.get(0) == null) {
			return String.valueOf(getBillNo("0001"));
		}
		String billNoStr = list.get(0).toString().trim();
		String prefix = billNoStr.substring(0, 8);
		if (prefix.equals(convertDateToString())) {
			String suffix = billNoStr.substring(billNoStr.length() - 4);
			String suffixNumber = String.valueOf(Integer.parseInt(suffix) + 1);
			suffix = replace("0", 4 - suffixNumber.length()) + suffixNumber;
			return String.valueOf(getBillNo(suffix));
		} else {
			return String.valueOf(getBillNo("0001"));
		}
	}

	/**
	 * 把char转换为string
	 * 
	 * @param replaceChar
	 *            char型
	 * @param count
	 *            总位数
	 * @return string
	 */
	private String replace(String replaceChar, int count) {
		String str = "";
		for (int i = 0; i < count; i++) {
			str += replaceChar;
		}
		return str;
	}

	/**
	 * 取得编号
	 * 
	 * @param suffix
	 *            前缀
	 * @return 日期+前缀
	 */
	private long getBillNo(String suffix) {
		try {
			Integer.parseInt(suffix);
		} catch (Exception ee) {
			return -1;
		}
		return Long.parseLong(convertDateToString() + suffix);
	}

	/**
	 * 把日期型转换为字符型
	 * 
	 * @return 转换为字符型的日期
	 */
	private String convertDateToString() {
		String yearMonthDay = "";
		String month = "";
		String day = "";
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		yearMonthDay += calendar.get(Calendar.YEAR);
		month += calendar.get(Calendar.MONTH) + 1;
		yearMonthDay += replace("0", 2 - month.length()) + month;
		day += calendar.get(Calendar.DAY_OF_MONTH);
		yearMonthDay += replace("0", 2 - day.length()) + day;
		return yearMonthDay;
	}

	/**
	 * 保存进出口申请单数据
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单
	 */
	public void saveImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		encDao.saveImpExpRequestBill(impExpRequestBill);
	}

	/**
	 * 计算保存申请单表头项数
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单
	 */
	public void saveImpExpRequestBillItemCount(
			ImpExpRequestBill impExpRequestBill) {

		if (impExpRequestBill.getId() == null) {// 首次新增
			encDao.saveImpExpRequestBill(impExpRequestBill);
		} else {
			Integer itemCount = encDao
					.findImpExpCommodityInfoCount(impExpRequestBill.getId());
			impExpRequestBill.setItemCount(itemCount);
			encDao.saveImpExpRequestBill(impExpRequestBill);
		}
		// ImpExpRequestBill save = encDao
		// .findImpExpRequestBillById(impExpRequestBill.getId());
		// if (save != null) {
		// save.setItemCount(itemCount);
		// encDao.saveImpExpRequestBill(save);
		// }
	}

	/**
	 * 获得商品信息记录来自数据是否正确的检验
	 * 
	 * @param parentId
	 *            父对象的id
	 * @return 检验后的商品信息记录
	 */
	public List findImpExpCommodityInfoByCheck(String parentId) {
		List<String> newList = new ArrayList();
		List list = ((EncDao) this.getBaseEncDao())
				.findImpExpCommodityInfo(parentId);
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) list
					.get(i);
			if (impExpCommodityInfo.getCurrency() == null) {
				newList.add("币制为空");
			}
			if (impExpCommodityInfo.getAmountPrice() == null) {
				newList.add("总金额为空");
			} else if (impExpCommodityInfo.getAmountPrice().doubleValue() < 0) {
				newList.add("金额不能为负");
			}
			if (impExpCommodityInfo.getCountry() == null) {
				newList.add("国家地区不能空");
			}
			if (impExpCommodityInfo.getGrossWeight() == null) {
				newList.add("毛重不能为空");
			} else if (impExpCommodityInfo.getGrossWeight().doubleValue() < 0) {
				newList.add("毛重不能为负");
			}
			if (impExpCommodityInfo.getNetWeight() == null) {
				newList.add("净重不能为空");
			} else if (impExpCommodityInfo.getNetWeight().doubleValue() < 0) {
				newList.add("净重不能为负");
			}
		}
		return newList;
	}

	/**
	 * 生成报关清单
	 * 
	 * @param applyToCustomsBillList
	 * @param dataSource
	 * @param isMaterial
	 * @param isNewRecord
	 * @param head
	 * @return
	 */
	public synchronized List makeApplyToCustomsRequestBillList(List dataSource,
			boolean isMaterial, MakeCusomsDeclarationParam param) {
		EmsHeadH2k head = param.getEmsHeadH2k();
		boolean isBillCustoms = param.isUniteBillLists();// 是否合并清单商品
		boolean useConvert = param.isUseConvert();// 是否单位折算
		ApplyToCustomsBillList billList = param.getApplyToCustomsBillList();
		Integer impExpFlag = billList.getImpExpFlag();
		Integer sAamount = null;
		Integer sPrice = null;
		Integer sTotalPrice = null;
		String cAamount = (String) commonCodeDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_AMOUNT);
		if (cAamount == null) {
			sAamount = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sAamount = Integer.parseInt(cAamount);
		}
		String cPrice = (String) commonCodeDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_PRICE);
		if (cPrice == null) {
			sPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sPrice = Integer.parseInt(cPrice);
		}
		String cTotalPrice = (String) commonCodeDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_TOTALPRICE);

		if (cTotalPrice == null) {
			sTotalPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sTotalPrice = Integer.parseInt(cTotalPrice);
		}
		StringBuffer errorMessage = new StringBuffer();
		// 罗盛 改变清单归并前参数默认设置 2006/8/26 获得清单参数设置
		ApplyCustomBillParameter billParameter = this.sysCodeDao
				.findApplyCustomBillParameter();
		/*
		 * 把所有相同的ptNo数量等其它项整合成一条(原产国不同分开处理) 单价：取平均单价，数量，净重，毛重，件数：合并
		 * totalNetWeight：总净重（对出口来说：总净重=单位净重数量，对进口来说：总净重=单位净重）
		 */
		List impExpCommodityInfoList = null;
		if (!isBillCustoms) {// 申请单转清单（料号相同不合并）
			impExpCommodityInfoList = this
					.mergerImpExpCommodityInfoNo(dataSource);
		} else {
			impExpCommodityInfoList = this
					.mergerImpExpCommodityInfo(dataSource);
		}

		Hashtable<String, TempCustomsList> hsimg = new Hashtable<String, TempCustomsList>();
		Hashtable<String, TempCustomsList> hsexg = new Hashtable<String, TempCustomsList>();
		double legalUnitGene = 0.0;
		double legalUnit2Gene = 0.0;
		List impExpReCommodityInfoList = sortSangJianImpExpCommodityInfo(
				impExpCommodityInfoList, isMaterial, impExpFlag);
		System.out.println("impExpReCommodityInfoList"
				+ impExpReCommodityInfoList.size());
		for (int i = 0; i < impExpReCommodityInfoList.size(); i++) {// distinct
			ImpExpCommodityInfo t = (ImpExpCommodityInfo) impExpReCommodityInfoList
					.get(i);
			String ptNo = t.getMateriel().getPtNo();
			if (isMaterial == true) {// 料件 不检查版本号
				// 查找物料对应十码
				List flist = emsEdiTrDao
						.findCommdityForbid(MaterielType.MATERIEL);
				Collection cl = new Vector();
				for (int j = 0; j < flist.size(); j++) {
					CommdityForbid cf = (CommdityForbid) flist.get(j);
					String s = cf.getSeqNum() == null ? "" : cf.getSeqNum();
					cl.add(s);
				}
				InnerMergeData data = fptManageDao.findInnerMergerByPtNo(ptNo,
						MaterielType.MATERIEL);
				if (data == null) {
					errorMessage.append("料号：" + ptNo + " 在内部归并关系中不存在!\n");
				}
				if (cl.contains(data.getHsAfterTenMemoNo() == null ? "" : data
						.getHsAfterTenMemoNo().toString())) {
					errorMessage.append("料号：" + ptNo + " 所对应十码序号已被禁用!\n");
					continue;
				}
				TempCustomsList tempCustomsList = null;
				Integer tenSeqNum = data.getHsAfterTenMemoNo(); // 十码序号
				String countryName = t.getCountry() == null ? "" : t
						.getCountry().getName();
				// ---------------------------------------------------------------
				AtcMergeBeforeComInfo beforeinfo = makeAtcMergeBeforeComInfo(t,
						billParameter, useConvert, data, MaterielType.MATERIEL,
						sAamount, sPrice, sTotalPrice);// 新增清单归并前
				// ------------------------------------------
				String key = String.valueOf(tenSeqNum) + "/" + countryName;
				EmsHeadH2kImg emsImg = null;
				AtcMergeAfterComInfo afterinfo = null;
				if (hsimg.get(key) != null) {
					tempCustomsList = (TempCustomsList) hsimg.get(key);
					afterinfo = tempCustomsList.getAfterinfo();
					// ------------------------------
					afterinfo = makeAtcInnerMergerComInfoByMateriel(afterinfo,
							null, beforeinfo, sAamount, sPrice, sTotalPrice,
							legalUnitGene, legalUnit2Gene);// 新增归并后
					// -------------------------------
					List list = tempCustomsList.getList();
					if (list == null) {
						list = new ArrayList();
					}
					list.add(beforeinfo);
					tempCustomsList.setList(list);
				} else {
					emsImg = ((EncDao) this.getBaseEncDao())
							.findEmsHeadH2kImgBySeqNum(head, tenSeqNum);
					if (emsImg == null) {
						errorMessage.append("料号：" + ptNo + " 对应帐册中状态不是已发送!\n");
						continue;
					}
					legalUnitGene = emsImg.getLegalUnitGene() == null ? 0.0
							: emsImg.getLegalUnitGene();
					legalUnit2Gene = emsImg.getLegalUnit2Gene() == null ? 0.0
							: emsImg.getLegalUnit2Gene();
					afterinfo = makeAtcInnerMergerComInfoByMateriel(null,
							emsImg, beforeinfo, sAamount, sPrice, sTotalPrice,
							legalUnitGene, legalUnit2Gene); // 新增归并后

					tempCustomsList = new TempCustomsList();
					tempCustomsList.setAfterinfo(afterinfo);
					List list = tempCustomsList.getList();
					if (list == null) {
						list = new ArrayList();
					}
					list.add(beforeinfo);
					tempCustomsList.setList(list);
					tempCustomsList.setIsSelected(new Boolean(true));
					hsimg.put(key, tempCustomsList);
				}
			} else { // 成品
				// 查找物料对应十码
				InnerMergeData data = fptManageDao.findInnerMergerByPtNo(ptNo,
						MaterielType.FINISHED_PRODUCT);
				if (data == null) {
					errorMessage.append("料号：" + ptNo + " 在内部归并中不存在!\n");
					continue;
				}
				TempCustomsList tempCustomsList = null;
				Integer tenSeqNum = data.getHsAfterTenMemoNo(); // 十码序号
				String countryName = t.getCountry() == null ? "" : t
						.getCountry().getName();

				// 查找禁用商品，把序号及版本作为KEY，存入.
				List forbidList = emsEdiTrDao.findCommdityForbidByExg(
						MaterielType.FINISHED_PRODUCT, tenSeqNum);
				Collection clCommdityForbid = new Vector();
				for (int j = 0; j < forbidList.size(); j++) {
					CommdityForbid cf = (CommdityForbid) forbidList.get(j);
					String s = (cf.getSeqNum() == null ? "" : cf.getSeqNum())
							+ "/"
							+ (cf.getVersion() == null ? "" : cf.getVersion());
					clCommdityForbid.add(s);
				}
				// -----------------------------------------------------
				AtcMergeBeforeComInfo beforeinfo = makeAtcMergeBeforeComInfo(t,
						billParameter, useConvert, data,
						MaterielType.FINISHED_PRODUCT, sAamount, sPrice,
						sTotalPrice);// 增加清单归并前
				// -------------------------------------------------------
				// 申请单转清单是否来源于归并关系BOM中的最大版本号
				Integer ver = null;
				String materielVersion = null;
				String isMergerBomMaxVer = emsEdiTrDao
						.getBpara(BcusParameter.MERGER_BOM_MaxVer);
				if (isMergerBomMaxVer == null || "0".equals(isMergerBomMaxVer)) {
					materielVersion = t.getVersion(); // 获取申请单里的版本号
					if (t.getVersion() == null || t.getVersion().equals("")) {
						errorMessage.append("料号：" + ptNo + " 申请单中没有填写版本!\n");
						continue;
					} else {
						try {
							Integer.parseInt(t.getVersion());
						} catch (Exception e) {
							e.printStackTrace();
							errorMessage.append("料号：" + ptNo
									+ " 申请单中版本填写不全法 !\n");
							continue;
						}
					}
					ver = Integer.parseInt(t.getVersion());
				} else {
					// 取得归并关系中的最大版本号
					ver = ((EncDao) this.getBaseEncDao())
							.findMergerBomlVersioId(ptNo, forbidList);
					if (ver == null) {
						errorMessage.append("料号：" + ptNo
								+ " 所对应的归并关系BOM中版本号未能找到!\n");
						continue;
					}
					materielVersion = String.valueOf(ver);
				}

				List allVList = ((EncDao) this.getBaseEncDao())
						.findEmsHeadH2kVersionAll(head, tenSeqNum, ver);// 所有版本
				// System.out.println("===ver=="+ver+"==allVList=="+allVList);
				if (allVList.size() == 0) {
					errorMessage.append("料号：" + ptNo + " 所对应的备案序号:" + tenSeqNum
							+ " 版本号：" + ver + "在对应帐册中不存在\n");
					continue;
				}
				for (int j = 0; j < allVList.size(); j++) {
					EmsHeadH2kVersion emsVersion = (EmsHeadH2kVersion) allVList
							.get(j);
					List vlist = ((EncDao) this.getBaseEncDao())
							.findEmsHeadH2kBom(emsVersion);
					if (vlist.size() == 0) {
						errorMessage.append("料号：" + ptNo + " 版本号："
								+ emsVersion.getVersion() + "在帐册中不存在!\n");
						continue;
					}
					String str = (emsVersion.getEmsHeadH2kExg().getSeqNum() == null ? ""
							: emsVersion.getEmsHeadH2kExg().getSeqNum()
									.toString())
							+ "/"
							+ (emsVersion.getVersion() == null ? ""
									: emsVersion.getVersion().toString());
					if (clCommdityForbid.contains(str)) {
						errorMessage
								.append("料号："
										+ ptNo
										+ " 所对应十码序号成品版本"
										+ (emsVersion.getVersion() == null ? ""
												: emsVersion.getVersion())
										+ " 已被禁用!\n");
						continue;// 说明禁用商品中存在，不让转
					}
					String key = String.valueOf(tenSeqNum) + "/"
							+ emsVersion.getVersion() + "/" + countryName;
					EmsHeadH2kExg emsExg = null;
					AtcMergeAfterComInfo afterinfo = null;
					if (hsexg.get(key) != null) {
						tempCustomsList = (TempCustomsList) hsexg.get(key);
						afterinfo = tempCustomsList.getAfterinfo();
						afterinfo = makeAtcInnerMergerComInfoByMaterielExg(
								afterinfo, null, emsVersion, beforeinfo,
								sAamount, sPrice, sTotalPrice, legalUnitGene,
								legalUnit2Gene);
						List list = tempCustomsList.getList();
						if (list == null) {
							list = new ArrayList();
						}
						beforeinfo.setVersion(emsVersion.getVersion());
						// beforeinfo.
						if (CommonUtils.isCompany("明门幼童")
								|| CommonUtils.isCompany("宝钜儿童用品")) {
							beforeinfo.setDeclaredPrice(afterinfo.getPrice());
						}
						list.add(beforeinfo);
						tempCustomsList.setList(list);
					} else {
						emsExg = emsVersion.getEmsHeadH2kExg();
						legalUnitGene = emsExg.getLegalUnitGene() == null ? 0.0
								: emsExg.getLegalUnitGene();
						legalUnit2Gene = emsExg.getLegalUnit2Gene() == null ? 0.0
								: emsExg.getLegalUnit2Gene();

						afterinfo = makeAtcInnerMergerComInfoByMaterielExg(
								null, emsExg, emsVersion, beforeinfo, sAamount,
								sPrice, sTotalPrice, legalUnitGene,
								legalUnit2Gene); // 新增归并后
						afterinfo.setCountry(t.getCountry());
						tempCustomsList = new TempCustomsList();
						tempCustomsList.setAfterinfo(afterinfo);
						List list = tempCustomsList.getList();
						if (list == null) {
							list = new ArrayList();
						}
						if (CommonUtils.isCompany("明门幼童")
								|| CommonUtils.isCompany("宝钜儿童用品")) {
							beforeinfo.setDeclaredPrice(afterinfo.getPrice());
						}
						list.add(beforeinfo);
						tempCustomsList.setList(list);
						if (String.valueOf(emsVersion.getVersion()).equals(
								materielVersion)) {
							beforeinfo.setVersion(emsVersion.getVersion());
							tempCustomsList.setIsSelected(new Boolean(true));
						} else {
							tempCustomsList.setIsSelected(new Boolean(false));
						}
						if (tempCustomsList.getIsSelected()) {
							hsexg.put(key, tempCustomsList);
						}
					}
				}
			}
		}

		if (isMaterial == true) {
			ArrayList returnList = new ArrayList(hsimg.values());
			returnList.add(errorMessage);
			return returnList;
		} else {
			ArrayList returnList = new ArrayList(hsexg.values());
			returnList.add(errorMessage);
			return returnList;
		}

	}

	private List sortSangJianImpExpCommodityInfo(List impExpCommodityInfo,
			boolean isMaterial, Integer impExpFlag) {
		List impExpReCommodityInfoList = new ArrayList();
		List yesList = new ArrayList();
		List noList = new ArrayList();
		for (int i = 0; i < impExpCommodityInfo.size(); i++) {
			ImpExpCommodityInfo t = (ImpExpCommodityInfo) impExpCommodityInfo
					.get(i);
			String ptNo = t.getMateriel().getPtNo();
			InnerMergeData data = null;
			if (isMaterial == true) {// 料件 不检查版本号
				data = fptManageDao.findInnerMergerByPtNo(ptNo,
						MaterielType.MATERIEL);
			} else {
				data = fptManageDao.findInnerMergerByPtNo(ptNo,
						MaterielType.FINISHED_PRODUCT);
			}
			if (data == null) {
				continue;
			}
			t.setSeqNum(data.getHsAfterTenMemoNo());
			if (data.getHsAfterComplex() == null
					|| data.getHsAfterComplex().getCode() == null) {
				noList.add(t);
				continue;
			}
			if (data.getHsAfterComplex() != null) {
				CheckupComplex complex = emsEdiTrDao.findCheckupComplex(data
						.getHsAfterComplex().getCode(), impExpFlag);
				if (complex != null) {
					yesList.add(t);
				} else {
					noList.add(t);
				}
			}
		}
		Comparator comp = new Comparator() {
			public int compare(Object o1, Object o2) {
				ImpExpCommodityInfo p1 = (ImpExpCommodityInfo) o2;
				ImpExpCommodityInfo p2 = (ImpExpCommodityInfo) o1;
				if (p1.getSeqNum() == null) {
					return 1;
				}
				if (p2.getSeqNum() == null) {
					return -1;
				}
				if (p1.getSeqNum() > p2.getSeqNum()) {
					return -1;
				} else if (p1.getSeqNum() == p2.getSeqNum()) {
					if (p1.getVersion() == null || "".equals(p1.getVersion())) {
						return 1;
					}
					if (p2.getVersion() == null || "".equals(p2.getVersion())) {
						return -1;
					}
					if (Integer.parseInt(p1.getVersion()) > Integer.parseInt(p2
							.getVersion())) {
						return -1;
					} else if (Integer.parseInt(p1.getVersion()) == Integer
							.parseInt(p2.getVersion())) {
						return 0;
					} else {
						return 1;
					}
				} else {
					return 1;
				}
			}
		};
		Collections.sort(yesList, comp);
		Collections.sort(noList, comp);
		impExpReCommodityInfoList.addAll(yesList);
		impExpReCommodityInfoList.addAll(noList);
		return impExpReCommodityInfoList;
	}

	/**
	 * 进出口申请单--->报关清单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isImportGoods
	 * 是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @param dataSource
	 *            数据源
	 * @param emsEdiMergerHead
	 *            电子帐册归并表头
	 * @param isImportGoods
	 *            是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * @param isNewRecord
	 *            是代表生成新的清单还是追加到原有的清单
	 * @return list.get(0)==清单单头 list.get(1)==申请单头（修改后）
	 */

	private Double forD(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 把所有相同的ptNo数量等其它项整合成一条
	 * 
	 * @param dataSource
	 *            临时的进出口商品信息
	 * @return 整合后的进出口商品信息
	 */
	private List mergerImpExpCommodityInfo(List dataSource) {
		List impExpCommodityList = new ArrayList();
		Hashtable hs = new Hashtable();
		for (int i = 0; i < dataSource.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
					.get(i);
			if (t.getImpExpCommodityInfo() == null
					|| t.getImpExpCommodityInfo().getMateriel() == null) {
				continue;
			}
			String key = t.getImpExpCommodityInfo().getMateriel().getPtNo()
					+ "/"
					+ (t.getImpExpCommodityInfo().getCountry() == null ? ""
							: t.getImpExpCommodityInfo().getCountry().getName()// 原产国
									+ "/"
									+ (t.getImpExpCommodityInfo().getVersion() == null ? ""
											: t.getImpExpCommodityInfo()
													.getVersion().toString()));// 版本号
			Object obj = hs.get(key);
			if (obj != null) {
				ImpExpCommodityInfo data = (ImpExpCommodityInfo) obj;
				Double quantity = Double
						.valueOf((data.getQuantity() == null ? 0 : data
								.getQuantity().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getQuantity())
												.doubleValue()));// 总数
				Double totalMoney = Double
						.valueOf((data.getAmountPrice() == null ? 0 : data
								.getAmountPrice().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getAmountPrice())
												.doubleValue()));// 总金额
				Double workUsd = Double.valueOf((data.getWorkUsd() == null ? 0
						: data.getWorkUsd().doubleValue())
						+ (t.getImpExpCommodityInfo() == null ? 0 : forD(
								t.getImpExpCommodityInfo().getWorkUsd())
								.doubleValue()));// 加工费总金额

				Double unitPrice = 0.0;
				if (quantity != 0) {
					unitPrice = totalMoney / quantity;
					unitPrice = CommonUtils.getDoubleByDigit(unitPrice, 6);
				}

				Double grossWeight = Double
						.valueOf((data.getGrossWeight() == null ? 0 : data
								.getGrossWeight().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getGrossWeight())
												.doubleValue()));// 毛重
				Double netWeight = Double
						.valueOf((data.getNetWeight() == null ? 0 : data
								.getNetWeight().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getNetWeight())
												.doubleValue()));// 净重
				Double cubage = Double.valueOf((data.getBulks() == null ? 0
						: data.getBulks().doubleValue())
						+ (t.getImpExpCommodityInfo() == null ? 0 : forD(
								t.getImpExpCommodityInfo().getBulks())
								.doubleValue()));// 体积
				Integer piece = (data.getPiece() == null ? 0 : data.getPiece()
						+ (t.getImpExpCommodityInfo().getPiece() == null ? 0
								: t.getImpExpCommodityInfo().getPiece()));// 件数
				// if (isMaterial) {// 料件
				// // //总净重（对出口来说：总净重=单位净重*数量，对进口来说：总净重=单位净重）
				Double totalNetWeight = Double.valueOf((data
						.getTotalnetweight() == null ? 0 : data
						.getTotalnetweight().doubleValue())
						+ (t.getImpExpCommodityInfo() == null ? 0 : forD(
								t.getImpExpCommodityInfo().getNetWeight())
								.doubleValue()));
				String boxNo = data.getBoxNo();// 箱号
				String newBoxNo = "";
				if (boxNo != null && !"".equals(boxNo)) {
					newBoxNo = getNotExistBoxNo(boxNo, t
							.getImpExpCommodityInfo().getBoxNo());
				} else {
					newBoxNo = t.getImpExpCommodityInfo().getBoxNo();
				}
				data.setBoxNo(newBoxNo);
				data.setAmountPrice(totalMoney);// 总金额
				data.setWorkUsd(workUsd);// 加工费总价
				data.setQuantity(quantity);// 总数量
				data.setUnitPrice(unitPrice);// 单价
				data.setPiece(piece);// 件数
				data.setGrossWeight(grossWeight);// 毛重
				data.setNetWeight(netWeight); // 净重
				data.setTotalnetweight(totalNetWeight);// 总净重
				data.setBulks(cubage);// 体积
			} else {
				ImpExpCommodityInfo data = t.getImpExpCommodityInfo();
				hs.put(key, data);
			}
		}
		impExpCommodityList.addAll(hs.values());
		return impExpCommodityList;
	}

	private List mergerImpExpCommodityInfoNo(List dataSource) {
		List impExpCommodityList = new ArrayList();
		List list = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
					.get(i);
			if (t.getImpExpCommodityInfo() == null
					|| t.getImpExpCommodityInfo().getMateriel() == null) {
				continue;
			}
			list.add(t.getImpExpCommodityInfo());
		}
		impExpCommodityList.addAll(list);
		return impExpCommodityList;
	}

	/**
	 * 生成报关清单关归后商品信息(物料)
	 * 
	 * @param atcMergeAfterComInfo
	 *            清单归并后商品
	 * @param img
	 *            电子账册物料
	 * @param beforeinfo
	 *            清单归并前商品
	 * @param sAamount
	 *            数量小数位控制
	 * @param sPrice
	 *            单价小数位控制
	 * @param sTotalPrice
	 *            总价小数位控制
	 * @param legalUnitGene
	 * @param legalUnit2Gene
	 * @return
	 */
	private AtcMergeAfterComInfo makeAtcInnerMergerComInfoByMateriel(
			AtcMergeAfterComInfo atcMergeAfterComInfo, EmsHeadH2kImg img,
			AtcMergeBeforeComInfo beforeinfo, int sAamount, int sPrice,
			int sTotalPrice, double legalUnitGene, double legalUnit2Gene) {
		if (atcMergeAfterComInfo == null) {
			AtcMergeAfterComInfo afterinfo = new AtcMergeAfterComInfo();// 清单归并后
			afterinfo.setCompany(CommonUtils.getCompany());
			afterinfo.setComplex(img.getComplex());
			afterinfo.setComplexName(img.getName());
			afterinfo.setComplexSpec(img.getSpec());
			afterinfo.setEmsSerialNo(img.getSeqNum());
			afterinfo.setUnit(img.getUnit());
			afterinfo.setLegalUnit(img.getComplex() == null ? null : img
					.getComplex().getFirstUnit());
			afterinfo.setSecondLegalUnit(img.getComplex() == null ? null : img
					.getComplex().getSecondUnit());
			afterinfo.setCountry(beforeinfo.getSalesCountry());
			// ---------------------------------------------------------------------
			Double declaraamount = beforeinfo.getDeclaredAmount() == null ? 0.0
					: beforeinfo.getDeclaredAmount();
			afterinfo.setDeclaredAmount(CommonUtils.getDoubleByDigit(
					beforeinfo.getDeclaredAmount(), sAamount));// 数量
			afterinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
					beforeinfo.getTotalPrice(), sTotalPrice));// 总价
			afterinfo.setWorkUsd(CommonUtils.getDoubleByDigit(
					beforeinfo.getWorkUsd(), sTotalPrice));// 加工费总价
			if (afterinfo.getDeclaredAmount() != 0) {
				afterinfo.setPrice(afterinfo.getTotalPrice()
						/ afterinfo.getDeclaredAmount());//
				afterinfo.setPrice(afterinfo.getPrice() == null ? 0.0
						: CommonUtils.getDoubleByDigit(afterinfo.getPrice(),
								sPrice));// 4舍5入，保留四位
			}// 单价
			if (CommonUtils.isCompany("胜美达")) {
				afterinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
						afterinfo.getPrice() * afterinfo.getDeclaredAmount(),
						sPrice));
				beforeinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
						beforeinfo.getDeclaredPrice()
								* beforeinfo.getDeclaredAmount(), sPrice));
			}
			afterinfo.setGrossWeight(beforeinfo.getGrossWeight());// 毛重
			afterinfo.setTotalNetWeight(beforeinfo.getTotalnetweight());// 总净重
			afterinfo.setNetWeight(beforeinfo.getNetWeight());// 净重
			afterinfo.setPiece(beforeinfo.getPiece());// 件数
			afterinfo.setBoxNo(beforeinfo.getBoxNo() == null ? "" : beforeinfo
					.getBoxNo());// 箱号
			afterinfo.setLevyMode(beforeinfo.getLevyMode());
			afterinfo.setUsesCode(beforeinfo.getUsesCode());
			afterinfo.setProjectDept(beforeinfo.getProjectDept());
			afterinfo.setCurrency(beforeinfo.getCurrency());// 币制
			// 取得第一、第二的法定数量
			Unit secunit = afterinfo.getSecondLegalUnit();
			Unit firunit = afterinfo.getLegalUnit();
			String unitName = afterinfo.getUnit() == null ? "" : afterinfo
					.getUnit().getName();
			afterinfo = getAfterCommInFirstAmountOrSencdAmount(firunit,
					secunit, unitName, afterinfo.getEmsSerialNo(),
					legalUnitGene, legalUnit2Gene, afterinfo, declaraamount,
					sAamount);
			// afterinfo = (AtcMergeAfterComInfo) encDao
			// .saveAndReturnObject(afterinfo);
			beforeinfo.setAfterComInfo(afterinfo);// 设置对应的归并后商品信息
			// encDao.saveOrUpdate(beforeinfo);
			return afterinfo;
		} else {
			// ---------------------------------------------------------------------
			atcMergeAfterComInfo.setGrossWeight(atcMergeAfterComInfo
					.getGrossWeight() + beforeinfo.getGrossWeight());// 毛重
			atcMergeAfterComInfo.setNetWeight(atcMergeAfterComInfo
					.getNetWeight() + beforeinfo.getNetWeight());// 净重
			atcMergeAfterComInfo.setTotalNetWeight(atcMergeAfterComInfo
					.getTotalNetWeight() + beforeinfo.getTotalnetweight());// 总净重

			Double declaraamount = atcMergeAfterComInfo.getDeclaredAmount()
					+ beforeinfo.getDeclaredAmount();

			atcMergeAfterComInfo.setDeclaredAmount(CommonUtils
					.getDoubleByDigit(atcMergeAfterComInfo.getDeclaredAmount()
							+ beforeinfo.getDeclaredAmount(), sAamount));// 数量

			atcMergeAfterComInfo.setTotalPrice(CommonUtils.getDoubleByDigit(
					atcMergeAfterComInfo.getTotalPrice()
							+ beforeinfo.getTotalPrice(), sTotalPrice));// 总金额
			atcMergeAfterComInfo
					.setWorkUsd(CommonUtils.getDoubleByDigit(
							atcMergeAfterComInfo.getWorkUsd()
									+ beforeinfo.getWorkUsd(), sTotalPrice));// 加工费总金额
			if (atcMergeAfterComInfo.getDeclaredAmount() != 0) {
				atcMergeAfterComInfo.setPrice(atcMergeAfterComInfo
						.getTotalPrice()
						/ atcMergeAfterComInfo.getDeclaredAmount());
				atcMergeAfterComInfo.setPrice(CommonUtils.getDoubleByDigit(
						atcMergeAfterComInfo.getPrice(), sPrice));
			}// 单价
			if (CommonUtils.isCompany("胜美达")) {
				atcMergeAfterComInfo.setTotalPrice(CommonUtils
						.getDoubleByDigit(atcMergeAfterComInfo.getPrice()
								* atcMergeAfterComInfo.getDeclaredAmount(),
								sPrice));
				beforeinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
						beforeinfo.getDeclaredPrice()
								* beforeinfo.getDeclaredAmount(), sPrice));
			}
			atcMergeAfterComInfo.setPiece(atcMergeAfterComInfo.getPiece()
					+ beforeinfo.getPiece());// 件数
			// 取得第一、第二的法定数量
			Unit secunit = atcMergeAfterComInfo.getSecondLegalUnit();
			Unit firunit = atcMergeAfterComInfo.getLegalUnit();
			String unitName = atcMergeAfterComInfo.getUnit() == null ? ""
					: atcMergeAfterComInfo.getUnit().getName();
			atcMergeAfterComInfo = getAfterCommInFirstAmountOrSencdAmount(
					firunit, secunit, unitName,
					atcMergeAfterComInfo.getEmsSerialNo(), legalUnitGene,
					legalUnit2Gene, atcMergeAfterComInfo, declaraamount,
					sAamount);
			// 法定单位是千克的，法定数量可直接抓取净重
			// atcMergeAfterComInfo = (AtcMergeAfterComInfo) encDao
			// .saveAndReturnObject(atcMergeAfterComInfo);
			beforeinfo.setAfterComInfo(atcMergeAfterComInfo);// 设置对应的归并后商品信息
			// encDao.saveOrUpdate(beforeinfo);
			return atcMergeAfterComInfo;
		}
	}

	private AtcMergeAfterComInfo makeAtcInnerMergerComInfoByMaterielExg(
			AtcMergeAfterComInfo atcMergeAfterComInfo, EmsHeadH2kExg inner,
			EmsHeadH2kVersion versionObj, AtcMergeBeforeComInfo beforeinfo,
			int sAamount, int sPrice, int sTotalPrice, double legalUnitGene,
			double legalUnit2Gene) {
		if (atcMergeAfterComInfo == null) {
			AtcMergeAfterComInfo afterinfo = new AtcMergeAfterComInfo();// 清单归并后
			afterinfo.setCompany(CommonUtils.getCompany());
			afterinfo.setComplex(inner.getComplex());
			afterinfo.setComplexName(inner.getName());
			afterinfo.setComplexSpec(inner.getSpec());
			afterinfo.setEmsSerialNo(inner.getSeqNum());
			afterinfo.setUnit(inner.getUnit());
			afterinfo.setLegalUnit(inner.getComplex() == null ? null : inner
					.getComplex().getFirstUnit());
			afterinfo.setSecondLegalUnit(inner.getComplex() == null ? null
					: inner.getComplex().getSecondUnit());
			afterinfo.setVersion(versionObj.getVersion() == null ? "" : String
					.valueOf(versionObj.getVersion()));
			// ----------------------------------------------------------------------------
			afterinfo.setGrossWeight(beforeinfo.getGrossWeight());// 毛重
			afterinfo.setNetWeight(beforeinfo.getNetWeight());// 净重
			afterinfo.setTotalNetWeight(beforeinfo.getTotalnetweight());// 总净重
			Double declaraamount = beforeinfo.getDeclaredAmount() == null ? 0.0
					: beforeinfo.getDeclaredAmount();
			afterinfo.setDeclaredAmount(CommonUtils.getDoubleByDigit(
					beforeinfo.getDeclaredAmount(), sAamount));// 数量
			afterinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
					beforeinfo.getTotalPrice(), sTotalPrice));// 总价
			afterinfo.setCurrency(beforeinfo.getCurrency());// 币制
			afterinfo.setWorkUsd(CommonUtils.getDoubleByDigit(
					beforeinfo.getWorkUsd(), sTotalPrice));// 加工费总价
			if (afterinfo.getDeclaredAmount() != 0) {
				afterinfo.setPrice(afterinfo.getTotalPrice()
						/ afterinfo.getDeclaredAmount());
				afterinfo.setPrice(CommonUtils.getDoubleByDigit(
						afterinfo.getPrice(), sPrice));// 保留小数位
			}// 单价

			if (CommonUtils.isCompany("胜美达")) {
				afterinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
						afterinfo.getPrice() * afterinfo.getDeclaredAmount(),
						sPrice));
				beforeinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
						beforeinfo.getDeclaredPrice()
								* beforeinfo.getDeclaredAmount(), sPrice));
			}

			// 取得第一、第二的法定数量
			Unit secunit = afterinfo.getSecondLegalUnit();
			Unit firunit = afterinfo.getLegalUnit();
			String unitName = afterinfo.getUnit() == null ? "" : afterinfo
					.getUnit().getName();

			afterinfo = getAfterCommInFirstAmountOrSencdAmount(firunit,
					secunit, unitName, afterinfo.getEmsSerialNo(),
					legalUnitGene, legalUnit2Gene, afterinfo, declaraamount,
					sAamount);
			// afterinfo.setLegalAmount(beforeinfo.getLegalAmount() == null ?
			// 0.0
			// : beforeinfo.getLegalAmount());// 第一法数量
			// afterinfo
			// .setSecondLegalAmount(beforeinfo.getSecondLegalAmount() == null ?
			// 0.0
			// : beforeinfo.getSecondLegalAmount());// 第二法数量
			afterinfo.setPiece(beforeinfo.getPiece());// 件数
			afterinfo.setBoxNo(beforeinfo.getBoxNo() == null ? "" : beforeinfo
					.getBoxNo());// 箱号
			afterinfo.setPrice(afterinfo.getPrice() == null ? 0.0 : afterinfo
					.getPrice());// 后面保留小数

			afterinfo.setTotalPrice(afterinfo.getTotalPrice() == null ? 0.0
					: afterinfo.getTotalPrice());
			afterinfo
					.setDeclaredAmount(afterinfo.getDeclaredAmount() == null ? 0.0
							: afterinfo.getDeclaredAmount());
			afterinfo = (AtcMergeAfterComInfo) encDao
					.saveAndReturnObject(afterinfo);
			beforeinfo.setAfterComInfo(afterinfo);// 设置对应的归并后商品信息
			encDao.saveOrUpdate(beforeinfo);
			return afterinfo;
		} else {
			// ----------------------------------------------------------------------------
			atcMergeAfterComInfo.setGrossWeight(atcMergeAfterComInfo
					.getGrossWeight() + beforeinfo.getGrossWeight());// 毛重
			atcMergeAfterComInfo.setNetWeight(atcMergeAfterComInfo
					.getNetWeight() + beforeinfo.getNetWeight());// 净重
			atcMergeAfterComInfo.setTotalNetWeight(atcMergeAfterComInfo
					.getTotalNetWeight() + beforeinfo.getTotalnetweight());// 总净重

			Double declaraamount = atcMergeAfterComInfo.getDeclaredAmount()
					+ beforeinfo.getDeclaredAmount();
			atcMergeAfterComInfo.setDeclaredAmount(CommonUtils
					.getDoubleByDigit(atcMergeAfterComInfo.getDeclaredAmount()
							+ beforeinfo.getDeclaredAmount(), sAamount));// 总数量

			atcMergeAfterComInfo.setTotalPrice(CommonUtils.getDoubleByDigit(
					atcMergeAfterComInfo.getTotalPrice()
							+ beforeinfo.getTotalPrice(), sTotalPrice));// 总金额

			atcMergeAfterComInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
					atcMergeAfterComInfo.getWorkUsd() == null ? 0.0
							: atcMergeAfterComInfo.getWorkUsd()
									+ beforeinfo.getWorkUsd(), sTotalPrice));// 加工费金额

			if (atcMergeAfterComInfo.getDeclaredAmount() != 0) {
				atcMergeAfterComInfo.setPrice(atcMergeAfterComInfo
						.getTotalPrice()
						/ atcMergeAfterComInfo.getDeclaredAmount());
				atcMergeAfterComInfo.setPrice(CommonUtils.getDoubleByDigit(
						atcMergeAfterComInfo.getPrice(), sPrice));// 保留小数位
			}// 单价

			if (CommonUtils.isCompany("胜美达")) {
				atcMergeAfterComInfo.setTotalPrice(CommonUtils
						.getDoubleByDigit(atcMergeAfterComInfo.getPrice()
								* atcMergeAfterComInfo.getDeclaredAmount(),
								sPrice));
				beforeinfo.setTotalPrice(CommonUtils.getDoubleByDigit(
						beforeinfo.getDeclaredPrice()
								* beforeinfo.getDeclaredAmount(), sPrice));
			}

			// 取得第一、第二的法定数量
			Unit secunit = atcMergeAfterComInfo.getSecondLegalUnit();
			Unit firunit = atcMergeAfterComInfo.getLegalUnit();
			String unitName = atcMergeAfterComInfo.getUnit() == null ? ""
					: atcMergeAfterComInfo.getUnit().getName();
			atcMergeAfterComInfo = getAfterCommInFirstAmountOrSencdAmount(
					firunit, secunit, unitName,
					atcMergeAfterComInfo.getEmsSerialNo(), legalUnitGene,
					legalUnit2Gene, atcMergeAfterComInfo, declaraamount,
					sAamount);

			atcMergeAfterComInfo.setPiece(atcMergeAfterComInfo.getPiece()
					+ beforeinfo.getPiece());
			atcMergeAfterComInfo
					.setPrice(atcMergeAfterComInfo.getPrice() == null ? 0.0
							: atcMergeAfterComInfo.getPrice());

			String boxNo = atcMergeAfterComInfo.getBoxNo();// 箱号
			String newBoxNo = "";
			if (boxNo != null && !"".equals(boxNo)) {
				newBoxNo = getNotExistBoxNo(boxNo, beforeinfo.getBoxNo());
			} else {
				newBoxNo = beforeinfo.getBoxNo();
			}
			atcMergeAfterComInfo.setBoxNo(newBoxNo);

			atcMergeAfterComInfo.setTotalPrice(atcMergeAfterComInfo
					.getTotalPrice() == null ? 0.0 : atcMergeAfterComInfo
					.getTotalPrice());
			atcMergeAfterComInfo.setDeclaredAmount(atcMergeAfterComInfo
					.getDeclaredAmount() == null ? 0.0 : atcMergeAfterComInfo
					.getDeclaredAmount());
			atcMergeAfterComInfo = (AtcMergeAfterComInfo) encDao
					.saveAndReturnObject(atcMergeAfterComInfo);
			beforeinfo.setAfterComInfo(atcMergeAfterComInfo);// 设置对应的归并后商品信息
			encDao.saveOrUpdate(beforeinfo);
			return atcMergeAfterComInfo;
		}

	}

	/**
	 * 根据内部归并后信息与进出口商品信息生成报关清单归并前的商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @param impExpCommodityInfo
	 *            进出口商品信息
	 * @return 报关清单归并前商品信息
	 */

	private AtcMergeBeforeComInfo makeAtcMergeBeforeComInfo(
			ImpExpCommodityInfo impExpCommodityInfo,
			ApplyCustomBillParameter a, boolean useConvert,
			InnerMergeData data, String materielType, int sAamount, int sPrice,
			int sTotalPrice) {

		// ========初始化内部归并的第一法定单位与第二法定单位
		data.setHsAfterLegalUnit(data.getHsAfterComplex().getFirstUnit());
		data.setHsAfterSecondLegalUnit(data.getHsAfterComplex().getSecondUnit());
		Double unitScale = Double.valueOf(0.0);
		if (useConvert) {// 如果使用单位折算，才使用，否则设为0
			unitScale = impExpCommodityInfo.getMateriel().getUnitConvert() == null ? 0.0
					: impExpCommodityInfo.getMateriel().getUnitConvert();
		}
		System.out.println("  ------是否使用折算True:使用，False:不使用------->"
				+ useConvert + "-->折算系数=" + unitScale);
		// if (unitScale == null || Double.valueOf(0).equals(unitScale)) { //
		// 单位换算比例
		// unitScale = Double.valueOf(0.0);
		// }
		// ----------------------------------------------------------------
		AtcMergeBeforeComInfo actMergeBeforreComInfo = new AtcMergeBeforeComInfo();
		actMergeBeforreComInfo.setCompany(CommonUtils.getCompany());
		actMergeBeforreComInfo.setCurrency(impExpCommodityInfo.getCurrency());
		Double declaraamount = impExpCommodityInfo.getQuantity() == null ? 0.0
				: impExpCommodityInfo.getQuantity();

		// 申报数量
		if (data.getHsAfterMemoUnit() != null
				&& "千克".equals(data.getHsAfterMemoUnit().getName())
				&& data.getMateriel().getCalUnit() != null
				&& "千克".equals(data.getMateriel().getCalUnit().getName())) {

			actMergeBeforreComInfo.setDeclaredAmount(impExpCommodityInfo
					.getNetWeight() == null ? 0.0 : impExpCommodityInfo
					.getNetWeight());
		} else {
			actMergeBeforreComInfo.setDeclaredAmount(CommonUtils
					.getDoubleByDigit(declaraamount * unitScale.doubleValue(),
							sAamount));
		}

		actMergeBeforreComInfo.setTotalPrice(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(impExpCommodityInfo
						.getAmountPrice()), sTotalPrice));// 总金额

		actMergeBeforreComInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(impExpCommodityInfo
						.getWorkUsd()), sTotalPrice));// 加工费金额// 小数位跟总价保持一致

		if (actMergeBeforreComInfo.getDeclaredAmount().doubleValue() != Double
				.valueOf(0)) {
			actMergeBeforreComInfo.setDeclaredPrice(impExpCommodityInfo
					.getAmountPrice()
					/ actMergeBeforreComInfo.getDeclaredAmount()); // 单价
			actMergeBeforreComInfo.setDeclaredPrice(CommonUtils
					.getDoubleByDigit(
							actMergeBeforreComInfo.getDeclaredPrice(), sPrice));
		}
		// 归并前总金额=数量*单价 无对应栏位
		actMergeBeforreComInfo.setGrossWeight(impExpCommodityInfo
				.getGrossWeight() == null ? 0.0 : impExpCommodityInfo
				.getGrossWeight()); // 毛重小数位在申请单中默认就是4位
		actMergeBeforreComInfo
				.setNetWeight(impExpCommodityInfo.getNetWeight() == null ? 0.0
						: impExpCommodityInfo.getNetWeight()); // 净重4位
		actMergeBeforreComInfo.setMateriel(impExpCommodityInfo.getMateriel());
		actMergeBeforreComInfo.setTotalnetweight(impExpCommodityInfo
				.getTotalnetweight() == null ? 0.0 : impExpCommodityInfo
				.getTotalnetweight());// 总净重

		actMergeBeforreComInfo
				.setPiece(impExpCommodityInfo.getPiece() == null ? 0
						: impExpCommodityInfo.getPiece());// 件数
		actMergeBeforreComInfo.setBoxNo(impExpCommodityInfo.getBoxNo());// 箱号
		actMergeBeforreComInfo
				.setSalesCountry(impExpCommodityInfo.getCountry()); // 原产国
		actMergeBeforreComInfo.setExtendMemo(impExpCommodityInfo
				.getExtendMemo());// 扩展字段
		actMergeBeforreComInfo.setExtendMemo1(impExpCommodityInfo
				.getExtendMemo1());// 扩展字段1

		Unit secunit = data.getHsAfterSecondLegalUnit();
		Unit firunit = data.getHsAfterLegalUnit();
		String unitName = data.getHsAfterMemoUnit() == null ? "" : data
				.getHsAfterMemoUnit().getName();
		// 取得第一、第二的法定数量
		actMergeBeforreComInfo = getBeforeFirstAmountOrSencdAmount(firunit,
				secunit, unitName, data.getHsAfterTenMemoNo(), materielType,
				actMergeBeforreComInfo, declaraamount, sAamount);
		// 清单参数设置
		if (a != null) {
			if (actMergeBeforreComInfo.getCurrency() == null) {
				actMergeBeforreComInfo.setCurrency(a.getCurr());
			}
			if (actMergeBeforreComInfo.getSalesCountry() == null) {
				actMergeBeforreComInfo.setSalesCountry(a.getCountry());
			}
			if (actMergeBeforreComInfo.getUsesCode() == null) {
				actMergeBeforreComInfo.setUsesCode(a.getUses());
			}
			if (actMergeBeforreComInfo.getLevyMode() == null) {
				actMergeBeforreComInfo.setLevyMode(a.getLevyMode());
			}
		}
		return actMergeBeforreComInfo;
	}

	/**
	 * 得到清单的第一法定数量与第二法定数量
	 */
	private AtcMergeAfterComInfo getAfterCommInFirstAmountOrSencdAmount(
			Unit firunit, Unit secunit, String unitName,
			Integer afterTenSeqNum, double legalUnitGene,
			double legalUnit2Gene, AtcMergeAfterComInfo actMergeAfterComInfo,
			Double declaraamount, int sAamount) {
		// --------------------------------------------------------------
		// 计算第一法定数量、第二法定数量:
		// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
		// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
		// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
		// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
		if (firunit != null) {

			String firstUnitName = firunit.getName();
			if (unitName.equals(firstUnitName)) {// 条件1
				actMergeAfterComInfo.setLegalAmount(CommonUtils
						.getDoubleExceptNull(actMergeAfterComInfo
								.getDeclaredAmount()));
			} else if ("千克".equals(firstUnitName)) {// 条件2
				actMergeAfterComInfo.setLegalAmount(CommonUtils
						.getDoubleExceptNull(actMergeAfterComInfo
								.getNetWeight()));
			} else if (getUnitRateMap().get(unitName + "+" + firstUnitName) != null) {// 条件3
				Double unitRate = Double.parseDouble(getUnitRateMap().get(
						unitName + "+" + firstUnitName).toString());
				actMergeAfterComInfo.setLegalAmount(CommonUtils
						.getDoubleByDigit(
								declaraamount
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								sAamount));

			} else {// 条件4
				actMergeAfterComInfo.setLegalAmount(CommonUtils
						.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(declaraamount
										* legalUnitGene), sAamount));
			}
		}
		// 法定单位是千克的，法定数量可直接抓取净重
		if (secunit != null) {

			String secondUnitName = secunit.getName();
			if (unitName.equals(secondUnitName)) {
				actMergeAfterComInfo.setSecondLegalAmount(actMergeAfterComInfo
						.getDeclaredAmount() == null ? 0.0
						: actMergeAfterComInfo.getDeclaredAmount());
			} else if ("千克".equals(secondUnitName)) {
				actMergeAfterComInfo.setSecondLegalAmount(actMergeAfterComInfo
						.getNetWeight() == null ? 0.0 : actMergeAfterComInfo
						.getNetWeight());
			} else if (getUnitRateMap().get(unitName + "+" + secondUnitName) != null) {
				Double unitRate = Double.parseDouble(getUnitRateMap().get(
						unitName + "+" + secondUnitName).toString());
				actMergeAfterComInfo.setSecondLegalAmount(CommonUtils
						.getDoubleByDigit(
								declaraamount
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								sAamount));
			} else {
				actMergeAfterComInfo.setSecondLegalAmount(CommonUtils
						.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(declaraamount
										* legalUnit2Gene), sAamount));
			}
		}
		return actMergeAfterComInfo;
	}

	/**
	 * 得到清单的第一法定数量与第二法定数量
	 */
	private AtcMergeBeforeComInfo getBeforeFirstAmountOrSencdAmount(
			Unit firunit, Unit secunit, String unitName,
			Integer afterTenSeqNum, String materielType,
			AtcMergeBeforeComInfo actMergeBeforreComInfo, Double declaraamount,
			int sAamount) {
		// --------------------------------------------------------------
		double legalUnitGene = 0.0;
		double legalUnit2Gene = 0.0;
		List listH2kImgOrExg = ((EncDao) this.getBaseEncDao())
				.findImgExgBillBySeqNum(afterTenSeqNum, materielType, null);
		if (materielType.equals(MaterielType.MATERIEL)) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) listH2kImgOrExg.get(0);
			legalUnitGene = img.getLegalUnitGene() == null ? 0.0 : img
					.getLegalUnitGene();
			legalUnit2Gene = img.getLegalUnit2Gene() == null ? 0.0 : img
					.getLegalUnit2Gene();
		} else {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) listH2kImgOrExg.get(0);
			legalUnitGene = exg.getLegalUnitGene() == null ? 0.0 : exg
					.getLegalUnitGene();
			legalUnit2Gene = exg.getLegalUnit2Gene() == null ? 0.0 : exg
					.getLegalUnit2Gene();
		}
		//
		// 计算第一法定数量、第二法定数量:
		// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
		// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
		// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
		// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
		if (firunit != null) {

			String firstUnitName = firunit.getName();
			if (unitName.equals(firstUnitName)) {// 条件1
				actMergeBeforreComInfo.setLegalAmount(CommonUtils
						.getDoubleExceptNull(actMergeBeforreComInfo
								.getDeclaredAmount()));
			} else if ("千克".equals(firstUnitName)) {// 条件2
				actMergeBeforreComInfo.setLegalAmount(CommonUtils
						.getDoubleExceptNull(actMergeBeforreComInfo
								.getNetWeight()));
			} else if (getUnitRateMap().get(unitName + "+" + firstUnitName) != null) {// 条件3
				Double unitRate = Double.parseDouble(getUnitRateMap().get(
						unitName + "+" + firstUnitName).toString());
				actMergeBeforreComInfo.setLegalAmount(CommonUtils
						.getDoubleByDigit(
								declaraamount
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								sAamount));

			} else {// 条件4
				actMergeBeforreComInfo.setLegalAmount(CommonUtils
						.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(declaraamount
										* legalUnitGene), sAamount));
			}
		}
		// 法定单位是千克的，法定数量可直接抓取净重
		if (secunit != null) {

			String secondUnitName = secunit.getName();
			if (unitName.equals(secondUnitName)) {
				actMergeBeforreComInfo
						.setSecondLegalAmount(actMergeBeforreComInfo
								.getDeclaredAmount() == null ? 0.0
								: actMergeBeforreComInfo.getDeclaredAmount());
			} else if ("千克".equals(secondUnitName)) {
				actMergeBeforreComInfo
						.setSecondLegalAmount(actMergeBeforreComInfo
								.getNetWeight() == null ? 0.0
								: actMergeBeforreComInfo.getNetWeight());
			} else if (getUnitRateMap().get(unitName + "+" + secondUnitName) != null) {
				Double unitRate = Double.parseDouble(getUnitRateMap().get(
						unitName + "+" + secondUnitName).toString());
				actMergeBeforreComInfo.setSecondLegalAmount(CommonUtils
						.getDoubleByDigit(
								declaraamount
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								sAamount));
			} else {
				actMergeBeforreComInfo.setSecondLegalAmount(CommonUtils
						.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(declaraamount
										* legalUnit2Gene), sAamount));
			}
		}
		return actMergeBeforreComInfo;
	}

	/**
	 * 判断单据号是否存在
	 * 
	 * @param allbillNo
	 *            所有的单据号
	 * @param newbillNo
	 *            新单据号
	 * @return 若存在为true 否则为false
	 */
	public boolean isExistBillNo(String allbillNo, String newbillNo) {
		if (allbillNo == null) {
			return false;
		}
		String[] yy = allbillNo.split(",");
		for (int i = 0; i < yy.length; i++) {
			if (yy[i].toString().equals(newbillNo)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 回写进出口申请单--->并修改是否生成报关请单为false
	 * 
	 * @param applyToCustomsBillId
	 *            报关清单id
	 * @param listNo
	 *            单据号
	 */
	public void writeBackImpExpRequestBillByApplyToCustomsBillId(
			String applyToCustomsBillId, String listNo) {
		List dataSource = ((EncDao) this.getBaseEncDao())
				.findMakeApplyToCustomsByatcMergeBeforeComInfo(applyToCustomsBillId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			MakeApplyToCustoms m = (MakeApplyToCustoms) dataSource.get(i);
			//
			// 修改进出口商品信息是否转报关请单为false
			//
			ImpExpCommodityInfo t = m.getImpExpCommodityInfo();
			t.setIsTransferCustomsBill(new Boolean(false)); // 修改申请单表体是否转清单
			List list = new ArrayList();
			list.add(t);
			((EncDao) this.getBaseEncDao()).saveImpExpCommodityInfo(list);
			//
			// 修改结转单据是否转关封申请单为真
			//
			list.clear();
			list = ((EncDao) this.getBaseEncDao()).findImpExpCommodityInfo(t
					.getImpExpRequestBill().getId());
			boolean isModify = this
					.validateImpExpCommodityInfoAllDataIsFalse(list);
			if (isModify == true) { // 修改申请单头信息
				if (list == null || list.size() <= 0) {
					return;
				}
				ImpExpRequestBill impExpRequestBill = t.getImpExpRequestBill();
				int tocustomCount = ((EncDao) this.getBaseEncDao())
						.findImpExpInfoToCustomCount(impExpRequestBill.getId());
				if (tocustomCount < impExpRequestBill.getItemCount()) {
					impExpRequestBill.setIsCustomsBill(new Boolean(false)); // 修改表头是否全部转清单
				}
				impExpRequestBill.setToCustomCount(tocustomCount); // 修改表头已转清单数量
				String newAllListNo = "";
				if (impExpRequestBill.getAllBillNo() != null
						&& !"".equals(impExpRequestBill.getAllBillNo())) {
					newAllListNo = deleListNo(impExpRequestBill.getAllBillNo(),
							listNo);
				}

				impExpRequestBill.setAllBillNo(newAllListNo);
				((EncDao) this.getBaseEncDao())
						.saveImpExpRequestBill(impExpRequestBill);
			}
			//
			// 删除中间表信息
			//
			((EncDao) this.getBaseEncDao()).deleteMakeApplyToCustoms(m);

		}
	}

	/**
	 * 删除单据号
	 * 
	 * @param allListNo
	 *            所有单据号
	 * @param listNo
	 *            单据号
	 * @return 单据号
	 */
	private String deleListNo(String allListNo, String listNo) {
		if (allListNo == null || allListNo.equals("")) {
			return allListNo;
		}
		String[] yy = allListNo.split(",");
		String newListNo = "";
		for (int i = 0; i < yy.length; i++) {
			if (i == 0) {
				if (yy[i].equals(listNo)) {
					break;
				} else {
					newListNo = yy[0];
				}
			} else {
				if (yy[i].equals(listNo)) {
					break;
				}
				if (newListNo.equals("")) {
					newListNo = yy[i];
				} else {
					newListNo += "," + yy[i];
				}
			}
		}
		return newListNo;
	}

	/**
	 * 验证所有进出口商品信息是否都转报关清单
	 * 
	 * @param list
	 *            进出口商品信息
	 * @return 全部转了为true 否则为false
	 */
	private boolean validateImpExpCommodityInfoAllDataIsFalse(List list) {
		boolean isFlag = true;
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo t = (ImpExpCommodityInfo) list.get(i);
			if (t.getIsTransferCustomsBill() != null) {
				if (t.getIsTransferCustomsBill().booleanValue() == true) {
					isFlag = false;
					break;
				}
			}
		}
		return isFlag;
	}

	/**
	 * 取得所有海关注册公司信息来自海关注册公司代码
	 * 
	 * @param breifCode
	 *            海关注册公司代码
	 * @return 海关注册公司信息
	 */
	private Brief getBrief(String breifCode) {
		List list = baseCodeDao.getBrief("code", breifCode);
		if (list.size() < 1) {
			return null;
		} else {
			return (Brief) list.get(0);
		}
	}

	/**
	 * 报关清单生成报关单的基本信息 (表头信息)
	 * 
	 * @param billList
	 *            报关清单
	 * @param para
	 *            参数
	 * @param listSerialNumber
	 *            生成的报关单流水号
	 * @return 与指定的报关清单匹配的报关单的表头信息
	 */
	public CustomsDeclaration makeCustomsDeclaration(
			ApplyToCustomsBillList billList, MakeCusomsDeclarationParam para,
			CustomsDeclaration customs) {
		EmsHeadH2k emsHeadH2k = para.getEmsHeadH2k();
		if (customs == null) {
			customs = new CustomsDeclaration();
		}
		customs.setEmsHeadH2k(emsHeadH2k.getEmsNo()); // 帐册编号
		customs.setTradeCode(emsHeadH2k.getTradeCode());//
		customs.setTradeName(emsHeadH2k.getTradeName());
		customs.setMachCode(emsHeadH2k.getTradeCode());
		customs.setMachName(emsHeadH2k.getMachName());
		// 如果是清单追加到报关单中，报关单不需要更改原来报关单的流水号
		if (customs.getSerialNumber() == null) {
			// 流水号
			Integer serialNumber = ((EncDao) this.getBaseEncDao())
					.getCustomsDeclarationSerialNumber(billList.getImpExpFlag()
							.intValue(), emsHeadH2k.getEmsNo());
			customs.setSerialNumber(serialNumber);
		}
		customs.setCompany(CommonUtils.getCompany());
		customs.setManufacturer(getBrief(((Company) CommonUtils.getCompany())
				.getCode()));
		customs.setCreater(CommonUtils.getRequest().getUser());
		customs.setImpExpType(billList.getImpExpType());
		customs.setImpExpFlag(billList.getImpExpFlag());
		// ---------------------------------
		customs.setImpExpDate(para.getImpExpDate());// 进出口日期
		customs.setDeclarationDate(para.getDelcarationDate());// 申报日期
		customs.setConveyance(para.getConveyance());// 运输工具
		customs.setLevyKind(para.getLevyKind());// 征免性质
		customs.setPerTax(para.getPerTax());// 征收
		customs.setLicense(para.getLicense());
		customs.setCountryOfLoadingOrUnloading(para
				.getCountryOfLoadingOrUnloading());// 抵运国
		customs.setDomesticDestinationOrSource(para
				.getDomesticDestinationOrSource());// 目的 or 货源地
		customs.setPortOfLoadingOrUnloading(para.getPortOfLoadingOrUnloading());// 装载港或指运港
		customs.setTransac(para.getTransac());// 成交方式
		customs.setWrapType(para.getWrapType());// 包装种类
		customs.setCustomer(para.getCustomer());// 客户
		customs.setTransferMode(para.getTransf()); // 运输方式
		customs.setDeclarationCustoms(para.getDeclarationCustoms()); // 申报海关
		customs.setCustoms(para.getCustoms()); // 进出口岸
		customs.setTradeMode(para.getTrade()); // 贸易方式
		customs.setDeclarationDate(para.getDelcarationDate());// 申报日期
		customs.setCurrency(para.getCurr());// 币制
		((EncDao) this.getBaseEncDao()).saveCustomsDeclaration(customs);// 这个一定要

		// ----------------------------------------------
		customs.setBillListId(billList.getListNo()); // 清单号码
		// 申请单转报关单带出客户
		if (billList != null) {
			ScmCoc k = billList.getScmCoc();
			if (k != null) {
				customs.setCountryOfLoadingOrUnloading(k.getCountry());
				customs.setPortOfLoadingOrUnloading(k.getPortLin());
				if (k.getCustoms() != null) {
					customs.setCustoms(k.getCustoms());
				}
				customs.setTransferMode(k.getTransf());
				customs.setCustomer(k);// 客户
			}
		}
		// 进口回写集装箱号
		if (customs.getImpExpFlag() == ImpExpFlag.IMPORT) { // 进口
			List list = ((EncDao) this.getBaseEncDao()).findjzno(billList);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == null) {
					continue;
				}
				String c = (String) list.get(i);
				if (c == null || "".equals(c)) {
					continue;
				}
				CustomsDeclarationContainer container = new CustomsDeclarationContainer();
				container.setBaseCustomsDeclaration(customs);
				container.setContainerCode(c);
				((EncDao) this.getBaseEncDao())
						.saveCustomsDeclarationContainer(container);
			}
		}

		// 调用系统参数设置--报关单参数设置
		customs = getSysCustoms(customs);

		((EncDao) this.getBaseEncDao()).saveCustomsDeclaration(customs);
		return customs;
	}

	// 调用系统参数设置--报关单参数设置
	private CustomsDeclaration getSysCustoms(CustomsDeclaration customs) {
		CustomsDeclarationSet other1 = this.findCustomsSet(customs
				.getImpExpType());
		if (other1 != null) {
			if (other1.getDeclarationCustoms() != null) {
				customs.setDeclarationCustoms(other1.getDeclarationCustoms());
			}
			if (other1.getCustomlevyKind() != null) {
				customs.setLevyKind(other1.getCustomlevyKind());
			}
			if (other1.getBillOfLading() != null
					&& !other1.getBillOfLading().equals("")) {
				customs.setBillOfLading(other1.getBillOfLading());
			}
			if (other1.getBalanceMode() != null) {
				customs.setBalanceMode(other1.getBalanceMode());
			}
			if (other1.getTradeMode() != null) {
				customs.setTradeMode(other1.getTradeMode());
			}
			if (other1.getConveyance() != null
					&& !other1.getConveyance().equals("")) {
				customs.setConveyance(other1.getConveyance());
			}
			if (other1.getTransac() != null) {
				customs.setTransac(other1.getTransac());
			}
			if (other1.getCoun() != null) {
				customs.setCountryOfLoadingOrUnloading(other1.getCoun());
			}
			if (other1.getPredock() != null) {
				customs.setPredock(other1.getPredock());
			}
			if (other1.getPort() != null) {
				customs.setPortOfLoadingOrUnloading(other1.getPort());
			}
			if (other1.getWrapType() != null) {
				customs.setWrapType(other1.getWrapType());
			}
			if (other1.getCustoms() != null) {
				customs.setCustoms(other1.getCustoms());
			}
			if (other1.getTransferMode() != null) {
				customs.setTransferMode(other1.getTransferMode());
			}
			if (other1.getDistrict() != null) {
				customs.setDomesticDestinationOrSource(other1.getDistrict());
			}
			if (other1.getCurr() != null && customs.getCurrency() == null) {
				customs.setCurrency(other1.getCurr());
			}

		}
		return customs;
	}

	/**
	 * 在根据报关清单自动生成报关单的时候，检查报关清单的数据是否符合条件
	 * 
	 * @param list
	 *            报关清单内容
	 * @return 如果成功返回0；
	 */
	public int checkBillListForMakeCustomsDeclaration(List list) {
		int result = 0;
		return result;
	}

	/**
	 * 修改清单报关单流水号
	 * 
	 * @param billlist
	 *            报关清单
	 * @param seqNum
	 *            单据号
	 */
	private void modifyApplyToCustomsBillList(ApplyToCustomsBillList billlist,
			String seqNum, Integer afterinfosize) {
		String AlllistNo = billlist.getCustomsDeclarationCode();
		if (!isExistBillNo(AlllistNo, seqNum)) {
			if (AlllistNo != null && !AlllistNo.equals("")) {
				billlist.setCustomsDeclarationCode(AlllistNo + "," + seqNum);
			} else {
				billlist.setCustomsDeclarationCode(seqNum);
			}
		}
		List teamlist = new ArrayList();// 临时的list
		teamlist.add(billlist);
		List<AtcMergeAfterComInfo> aInfoList = encDao
				.findAtcMergeAfterComInfoByParents(teamlist);
		teamlist.clear();
		for (AtcMergeAfterComInfo atcMergeAfterComInfo : aInfoList) {
			if (atcMergeAfterComInfo.getIsTransferCustomsBill()) {
				teamlist.add(atcMergeAfterComInfo);// 存放已转的清单中的申请单
			}
		}
		// 如果teamlist中为null，说明这份清单之前没转做，这是第一次转,否则是第二次转
		if (teamlist.size() == 0) {
			billlist.setMaterialNum(afterinfosize);
		} else {
			Integer MaterialNum = billlist.getMaterialNum();
			if (MaterialNum != null) {
				billlist.setMaterialNum(MaterialNum + afterinfosize);
			} else {
				billlist.setMaterialNum(afterinfosize);
			}
		}

		billlist.setListState(Integer.valueOf(ApplyToCustomsBillList.Effect));

		// 只有当清单中的商品全部转为报关单后，清单状态才为已转报关单
		if (billlist.getMaterialNum() >= aInfoList.size()) {
			billlist.setEffectstate(true);// 清单状态：已转报关单
		}
		((EncDao) this.getBaseEncDao()).saveApplyToCustomsBillList(billlist);
	}

	/**
	 * 申清单---清单--生成报关单 生成清单
	 * 
	 * @param impexpbills
	 * @param afterinfoLists
	 * @param param
	 * @param customs
	 *            报关单表头
	 * @return
	 */
	public synchronized List makeCusomsDeclarationFromBillList(
			List impexpbills, List afterinfoLists,
			MakeCusomsDeclarationParam param, CustomsDeclaration customs) {
		// 存放生成的清单
		List returnBilllist = new ArrayList();
		// 存放生成的报关单
		List returnCustomsDeclaretionlist = new ArrayList();
		// 输入的清单表头信息
		ApplyToCustomsBillList billList = param.getApplyToCustomsBillList();
		// 是否转报关单
		boolean isCustomsDeclaration = param.isCustomsDeclaration();

		// 临时存放清单表体
		List hsList = new ArrayList();
		// 清单表体按照序号排列
		for (int y = 0; y < afterinfoLists.size(); y++) {
			TempCustomsList tempcustoms = (TempCustomsList) afterinfoLists
					.get(y);
			if (tempcustoms.getAfterinfo() != null) {
				AtcMergeAfterComInfo info = tempcustoms.getAfterinfo();
				info.setBoxNo(subStringBoxNo(info.getBoxNo()));// 截取箱号
			}
			hsList.add(tempcustoms);
		}

		// ============开始按商检排序============
		// 进出口类型
		Integer type = billList.getImpExpFlag();
		// 商检排序
		hsList = sortListByCheckupComplex(hsList, type);

		// 确定清单号码
		if (billList.getId() == null) {
			String maxNo = ((EncDao) this.getBaseEncDao())
					.getApplyToCustomsBillListMaxNo();
			billList.setListNo(maxNo);
		}

		// 首先保存清单表头
		billList = ((EncDao) this.getBaseEncDao()).saveBillList(billList);
		returnBilllist.add(billList);

		// 保存清单表体
		int serNo = 1;
		for (int i = 0; i < hsList.size(); i++) {
			// 如果超过20项，拆分
			if (i != 0 && i % 20 == 0) {
				try {
					billList = (ApplyToCustomsBillList) BeanUtils
							.cloneBean(billList);
					billList.setId(null);
					String maxNo = ((EncDao) this.getBaseEncDao())
							.getApplyToCustomsBillListMaxNo();
					billList.setListNo(maxNo);
					billList = ((EncDao) this.getBaseEncDao())
							.saveBillList(billList);
					returnBilllist.add(billList);
					serNo = 1;
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			TempCustomsList tempcustoms = (TempCustomsList) hsList.get(i);
			AtcMergeAfterComInfo afterinfo = tempcustoms.getAfterinfo();
			afterinfo.setBillList(billList);

			// System.out.println(afterinfo.getEmsSerialNo() + ":"
			// + afterinfo.getCountry().getCode());
			((EncDao) this.getBaseEncDao()).saveAtcMergeAfterComInfo(afterinfo);
			// /////////

			if (!param.isOtherBilllist()) {
				Integer max = ((EncDao) this.getBaseEncDao())
						.findMaxAtcMergeAfterComInfoByListID(billList);
				if (max != null) {
					serNo = max;
				}
			}
			// 如果是追加到现有清单，则归并后流水后应该相应增加
			// --------------------------------------------------------------------------------
			List beforeList = tempcustoms.getList();
			Hashtable<String, AtcMergeBeforeComInfo> beforeHs = new Hashtable<String, AtcMergeBeforeComInfo>();

			for (int k = 0; k < beforeList.size(); k++) {
				AtcMergeBeforeComInfo beforeinfo = (AtcMergeBeforeComInfo) beforeList
						.get(k);
				beforeinfo.setAfterComInfo(afterinfo);// 归并前 set 归并后
				beforeinfo.setSerialNo(serNo);
				serNo++;
				beforeinfo.setBoxNo(subStringBoxNo(beforeinfo.getBoxNo()));// 截取箱号
				/**
				 * 明门特殊处理
				 */
				if (CommonUtils.isCompany("明门幼童")
						|| CommonUtils.isCompany("宝钜儿童用品")) {
					beforeinfo = getSpecalDeal(beforeinfo);
				}
				/**
				 * 康舒特殊处理
				 */
				if (CommonUtils.isCompany("康舒电子")) {
					beforeinfo = getSpecalDealKs(beforeinfo);
				}

				((EncDao) this.getBaseEncDao())
						.saveAtcMergeBeforeComInfo(beforeinfo);
				String ptNo = beforeinfo.getMateriel().getPtNo()
						+ "/"
						+ (beforeinfo.getSalesCountry() == null ? ""
								: beforeinfo.getSalesCountry().getName());
				if (beforeHs.get(ptNo) == null) {
					beforeHs.put(ptNo, beforeinfo);
				}
			}
			// --------------------------------------------------------------------------------
			// 回写申请单
			List makeApplyToCustomsList = new ArrayList();
			for (int t = 0; t < impexpbills.size(); t++) {
				TempImpExpCommodityInfo tt = (TempImpExpCommodityInfo) impexpbills
						.get(t);
				String ptpart = tt.getImpExpCommodityInfo().getMateriel()
						.getPtNo()
						+ "/"
						+ (tt.getImpExpCommodityInfo().getCountry() == null ? ""
								: tt.getImpExpCommodityInfo().getCountry()
										.getName());

				if (beforeHs.get(ptpart) != null) {
					ImpExpCommodityInfo iecomm = tt.getImpExpCommodityInfo();
					iecomm.setBoxNo(subStringBoxNo(iecomm.getBoxNo()));// 截取箱号
					iecomm.setIsTransferCustomsBill(new Boolean(true)); // 已转报关清单
					((EncDao) this.getBaseEncDao())
							.savaimpExpCommodityInfo(iecomm);
					// 回写申请单转清单中间表信息
					MakeApplyToCustoms m = new MakeApplyToCustoms();
					m.setAtcMergeBeforeComInfo((AtcMergeBeforeComInfo) beforeHs
							.get(ptpart));
					m.setImpExpCommodityInfo(tt.getImpExpCommodityInfo());
					m.setCompany(CommonUtils.getCompany());
					makeApplyToCustomsList.add(m);

					// 回写报关单表头
					ImpExpRequestBill impExpRequestBill = iecomm
							.getImpExpRequestBill();
					int count = impExpRequestBill.getToCustomCount() == null ? 0
							: impExpRequestBill.getToCustomCount();
					System.out.println("@@@@@@@@@@1");
					if (impExpRequestBill.getItemCount() == null
							|| impExpRequestBill.getItemCount() == 0) {
						int itemCount = ((EncDao) this.getBaseEncDao())
								.getApplyToCustomsBillListSumNo(impExpRequestBill
										.getId());
						impExpRequestBill.setItemCount(itemCount);
						((EncDao) this.getBaseEncDao())
								.saveOrUpdate(impExpRequestBill);
					}
					impExpRequestBill.setToCustomCount((count + 1));

					// 回写申请单中的 转 报关清单号
					if (impExpRequestBill.getAllBillNo() == null) {
						impExpRequestBill.setAllBillNo(m
								.getAtcMergeBeforeComInfo().getAfterComInfo()
								.getBillList().getListNo());
					} else if (!impExpRequestBill.getAllBillNo().contains(
							m.getAtcMergeBeforeComInfo().getAfterComInfo()
									.getBillList().getListNo())) {
						impExpRequestBill.setAllBillNo(impExpRequestBill
								.getAllBillNo()
								+ ","
								+ m.getAtcMergeBeforeComInfo()
										.getAfterComInfo().getBillList()
										.getListNo());
					}

					// 回写申请单 是否已转报关清单
					if (impExpRequestBill.getToCustomCount().equals(
							impExpRequestBill.getItemCount())) {
						impExpRequestBill.setIsCustomsBill(true);
					}
					((EncDao) this.getBaseEncDao())
							.saveImpExpRequestBill(impExpRequestBill);
				}
			}
			// 回写表头商品项数
			((EncDao) this.getBaseEncDao())
					.saveMakeApplyToCustoms(makeApplyToCustomsList);

			List beforesize = ((EncDao) this.getBaseEncDao())
					.findAtcMergeBeforeComInfoByListID(billList);
			int size = beforesize == null ? 0 : beforesize.size();
			billList.setMaterialNum(size);
			((EncDao) this.getBaseEncDao()).saveBillList(billList);
		}
		List returnList = new ArrayList();
		returnList.add(returnBilllist);
		returnList.add(returnCustomsDeclaretionlist);

		// --------------------------------------------------------------------------------------------------------------------
		if (!isCustomsDeclaration) {// 判断是否继续转报关单
			return returnList;
		} else {
			for (int i = 0; i < returnBilllist.size(); i++) {
				ApplyToCustomsBillList obillList = (ApplyToCustomsBillList) returnBilllist
						.get(i);
				CustomsDeclaration ocustoms = makeBilllistsToCustomDeclaretions(
						obillList, customs, param, null, true);
				returnCustomsDeclaretionlist.add(ocustoms);
			}
		}
		return returnList;
	}

	/**
	 * 截取箱号
	 * 
	 * @param stu
	 * @return
	 */
	private String subStringBoxNo(String stu) {
		if (stu == null) {
			return "";
		}
		for (int i = 0; i < stu.length(); i++) {
			String s = stu.substring(0, stu.length() - i);
			try {
				if (s.getBytes("UTF-8").length < 255) {
					return s;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 清单转---报关单
	 * 
	 * @param billList
	 * @param customs
	 * @param param
	 * @param billDetailList
	 * @param arrayType
	 * @return hcl 2010-09-09增加一boolean值判断报关单流水号是否要按清单序号排序 lyh
	 *         2012-12-11当一份申请单中的商品为完全转为报关单时，申请单不标示为以转报关单
	 */
	public CustomsDeclaration makeBilllistsToCustomDeclaretions(
			// TODO "申请单转报关单"
			ApplyToCustomsBillList billList, CustomsDeclaration customs,
			MakeCusomsDeclarationParam param, List billDetailList,
			boolean isSanJianArrayType) {
		customs = makeCustomsDeclaration(billList, param, customs);// 添加到报关单头,
		List flist = null;
		if (billDetailList == null) {
			flist = this.findAtcMergeAfterComInfoByListID(billList);// 合计
		} else {
			flist = billDetailList;
		}
		Integer type = billList.getImpExpFlag();
		int weightFraction = 4;// 设置表体的净重，毛重小数位为4
		// =====================按商检排序放前面============
		if (isSanJianArrayType) {
			flist = sortListByCheckupComplexForAtcMergeAfterComInfo(flist, type);
		}
		for (int i = 0; i < flist.size(); i++) {
			AtcMergeAfterComInfo afterinfo = (AtcMergeAfterComInfo) flist
					.get(i);
			//出现 Null 指针问题 处理一下
			boolean isTransferCustomsBill = afterinfo
					.getIsTransferCustomsBill() == null ? Boolean.FALSE
					: afterinfo.getIsTransferCustomsBill();
			if (isTransferCustomsBill || afterinfo.getEmsSerialNo() == null) {
				continue;// 如果已经转报关单，则不再转
			}
			// 修改清单报关单流水号,状态，商品项
			modifyApplyToCustomsBillList(afterinfo.getBillList(),
					String.valueOf(customs.getSerialNumber()),
					Integer.valueOf(1));//
			// 保存清单报关单号
			// -------------------------------------------------------------------------------
			BaseCustomsDeclarationCommInfo existcommInfo = null;
			CustomsDeclarationCommInfo commInfo = null;
			EmsHeadH2kExg exg = null;
			EmsHeadH2kImg img = null;
			String commInfoid = null;
			// 小数位参数
			CompanyOther otherPra = (CompanyOther) sysCodeDao
					.findCompanyOther().get(0);
			int amountNum = otherPra.getCustomAmountNum();
			int totalNum = otherPra.getCustomTotalNum();
			int priceNum = otherPra.getCustomPriceNum();
			// 通过清单后获得电子帐册商品信息
			if (billList.getMaterielProductFlag().equals(
					Integer.valueOf(MaterielType.MATERIEL))) {
				existcommInfo = ((EncDao) this.getBaseEncDao())
						.findBaseCustomsDeclarationCommInfoBySequm(
								Integer.valueOf(afterinfo.getEmsSerialNo()),
								customs, null, afterinfo.getCountry());
				img = ((EncDao) this.getBaseEncDao()).getEmsHeadImg(Integer
						.valueOf(afterinfo.getEmsSerialNo()));
				if (img == null) {
					continue;
				}
				if (existcommInfo == null) {
					CustomsDeclarationSet other = this.findCustomsSet(customs
							.getImpExpType());
					commInfo = new CustomsDeclarationCommInfo();
					commInfo.setBaseCustomsDeclaration(customs);
					commInfo.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(customs));
					if (billList.getMemos() != null) {
						commInfo.getBaseCustomsDeclaration().setMemos(
								billList.getMemos());
					}
					commInfo.setCommSerialNo(afterinfo.getEmsSerialNo());
					commInfo.setComplex(img.getComplex());
					commInfo.setCommName(img.getName());
					commInfo.setDetailNote(img.getDetailNote()); // 详细型号规格
					commInfo.setCommSpec(img.getSpec());
					commInfo.setUnit(img.getUnit());
					commInfo.setLegalUnit(img.getComplex().getFirstUnit());
					commInfo.setSecondLegalUnit(img.getComplex()
							.getSecondUnit());
					commInfo.setCountry(afterinfo.getCountry());// 国家
					commInfo.setLevyMode(afterinfo.getLevyMode());//
					commInfo.setUses(afterinfo.getUsesCode());
					commInfo.setProjectDept(afterinfo.getProjectDept());

					// -----------------------------------------------------------------------
					commInfo.setNetWeight(formatD((afterinfo.getNetWeight() == null ? 0.0
							: afterinfo.getNetWeight())));// 净重
					commInfo.setGrossWeight(formatD((afterinfo.getGrossWeight() == null ? 0.0
							: afterinfo.getGrossWeight())));// 毛重
					// 件数 == 件数
					commInfo.setPieces(afterinfo.getPiece() == null ? 0
							: afterinfo.getPiece().intValue());

					commInfo.setBoxNo(afterinfo.getBoxNo());// 箱号
					commInfo.setCommAmount(afterinfo.getDeclaredAmount() == null ? 0.0
							: afterinfo.getDeclaredAmount());// 数量

					commInfo.setWorkUsd(afterinfo.getWorkUsd() == null ? 0.0
							: afterinfo.getWorkUsd());// 加工费总价

					if (param.getPriceType().equals("平均单价")) {
						commInfo.setCommTotalPrice(afterinfo.getTotalPrice());// 总金额
						if (afterinfo.getDeclaredAmount() != null
								&& afterinfo.getDeclaredAmount() != 0) {
							commInfo.setCommUnitPrice(afterinfo.getTotalPrice()
									/ afterinfo.getDeclaredAmount());
						} else {
							commInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						commInfo.setCommUnitPrice(img.getDeclarePrice() == null ? 0.0
								: img.getDeclarePrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						commInfo.setCommUnitPrice(img.getFactoryPrice() == null ? 0.0
								: img.getFactoryPrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					}
					commInfo.setFirstAmount(afterinfo.getLegalAmount() == null ? 0.0
							: afterinfo.getLegalAmount());// 第一法定数量
					commInfo.setSecondAmount(afterinfo.getSecondLegalAmount() == null ? 0.0
							: afterinfo.getSecondLegalAmount());// 第二法定数量
					// -----------------------------------------------------------------------
					if (other != null) {
						commInfo.setUses(commInfo.getUses() == null ? other
								.getUses() : commInfo.getUses());
						commInfo.setLevyMode(commInfo.getLevyMode() == null ? other
								.getLevyMode() : commInfo.getLevyMode());

					}

					// ========= 小数位控制
					commInfo.setAddType(AddType.APPLY);
					commInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							commInfo.getGrossWeight(), weightFraction));
					commInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							commInfo.getNetWeight(), weightFraction));
					commInfo.setCommAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommAmount()), amountNum));
					commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommUnitPrice()), priceNum));
					commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommTotalPrice()), totalNum));

					commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getWorkUsd()), totalNum));

					// 第一法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getLegalUnit() != null
							&& commInfo.getUnit().getName()
									.equals(commInfo.getLegalUnit().getName())) {
						commInfo.setFirstAmount(commInfo.getCommAmount());
					} else if (commInfo.getLegalUnit() != null
							&& commInfo.getLegalUnit().getName().equals("千克")) {
						commInfo.setFirstAmount(commInfo.getNetWeight());
					}
					// 第二法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getSecondLegalUnit() != null
							&& commInfo
									.getUnit()
									.getName()
									.equals(commInfo.getSecondLegalUnit()
											.getName())) {
						commInfo.setSecondAmount(commInfo.getCommAmount());
					} else if (commInfo.getSecondLegalUnit() != null
							&& commInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						commInfo.setSecondAmount(commInfo.getNetWeight());
					}
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(commInfo);
					commInfoid = commInfo.getId();

				} else {
					// -----------------------------------------------------------------------
					existcommInfo.setNetWeight(formatD(existcommInfo
							.getNetWeight())
							+ formatD((afterinfo.getNetWeight() == null ? 0.0
									: afterinfo.getNetWeight())));
					existcommInfo.setGrossWeight(formatD(existcommInfo
							.getGrossWeight())
							+ formatD((afterinfo.getGrossWeight() == null ? 0.0
									: afterinfo.getGrossWeight())));
					existcommInfo
							.setPieces(existcommInfo.getPieces() == null ? 0
									: existcommInfo.getPieces()
											+ (afterinfo.getPiece() == null ? 0
													: afterinfo.getPiece()
															.intValue()));
					String boxNo = existcommInfo.getBoxNo();// 箱号
					String newBoxNo = "";
					if (boxNo != null && !"".equals(boxNo)) {
						newBoxNo = getNotExistBoxNo(boxNo, afterinfo.getBoxNo());
					} else {
						newBoxNo = afterinfo.getBoxNo();
					}
					existcommInfo.setBoxNo(subStringBoxNo(newBoxNo));
					existcommInfo.setFirstAmount(formatD(existcommInfo
							.getFirstAmount())
							+ formatD(afterinfo.getLegalAmount()));// 第一法定数量
					existcommInfo.setSecondAmount(formatD(existcommInfo
							.getSecondAmount())
							+ formatD(afterinfo.getSecondLegalAmount()));// 第二法定数量
					existcommInfo
							.setCommAmount((existcommInfo.getCommAmount() == null ? 0.0
									: existcommInfo.getCommAmount())
									+ (afterinfo.getDeclaredAmount() == null ? 0.0
											: afterinfo.getDeclaredAmount()));// 数量
					existcommInfo
							.setWorkUsd((existcommInfo.getWorkUsd() == null ? 0.0
									: existcommInfo.getWorkUsd())
									+ (afterinfo.getWorkUsd() == null ? 0.0
											: afterinfo.getWorkUsd()));// 加工费总价

					if (param.getPriceType().equals("平均单价")) {
						existcommInfo.setCommTotalPrice(existcommInfo
								.getCommTotalPrice()
								+ afterinfo.getTotalPrice());// 总金额
						if (existcommInfo.getCommAmount() != null
								&& existcommInfo.getCommAmount() != 0) {
							existcommInfo.setCommUnitPrice(existcommInfo
									.getCommTotalPrice()
									/ existcommInfo.getCommAmount());
						} else {
							existcommInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						existcommInfo
								.setCommUnitPrice(img.getDeclarePrice() == null ? 0.0
										: img.getDeclarePrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						existcommInfo
								.setCommUnitPrice(img.getFactoryPrice() == null ? 0.0
										: img.getFactoryPrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					}

					existcommInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getGrossWeight(), weightFraction));
					existcommInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getNetWeight(), weightFraction));

					// 第一法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getLegalUnit()
											.getName())) {
						existcommInfo.setFirstAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getLegalUnit() != null
							&& existcommInfo.getLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setFirstAmount(existcommInfo
								.getNetWeight());
					}

					// 第二法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getSecondLegalUnit()
											.getName())) {
						existcommInfo.setSecondAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setSecondAmount(existcommInfo
								.getNetWeight());
					}
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(existcommInfo);
					commInfoid = existcommInfo.getId();
				}
			} else {// 成品（出口报关单）
				existcommInfo = ((EncDao) this.getBaseEncDao())
						.findBaseCustomsDeclarationCommInfoBySequm(
								Integer.valueOf(afterinfo.getEmsSerialNo()),
								customs, afterinfo.getVersion(),
								afterinfo.getCountry());
				Integer version = (afterinfo.getVersion() == null || ""
						.equals(afterinfo.getVersion())) ? 0 : Integer
						.valueOf(afterinfo.getVersion());
				EmsHeadH2kVersion versionObj = ((EncDao) this.getBaseEncDao())
						.findEmsHeadH2kVersion(
								Integer.valueOf(afterinfo.getEmsSerialNo()),
								version);
				if (versionObj == null) {

					throw new RuntimeException("该备案号["
							+ afterinfo.getEmsSerialNo() + "]的版本[" + version
							+ "]在帐册中不存在");
				}
				exg = versionObj.getEmsHeadH2kExg();
				CustomsDeclarationSet other1 = this.findCustomsSet(customs
						.getImpExpType());

				if (existcommInfo == null) {
					commInfo = new CustomsDeclarationCommInfo();
					commInfo.setBaseCustomsDeclaration(customs);
					if (billList.getMemos() != null) {
						commInfo.getBaseCustomsDeclaration().setMemos(
								billList.getMemos());
					}
					commInfo.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(customs));
					commInfo.setCommSerialNo(afterinfo.getEmsSerialNo());
					commInfo.setComplex(exg.getComplex());
					commInfo.setCommName(exg.getName());
					commInfo.setCommSpec(exg.getSpec());
					commInfo.setUnit(exg.getUnit());
					commInfo.setLegalUnit(exg.getComplex().getFirstUnit());
					commInfo.setSecondLegalUnit(exg.getComplex()
							.getSecondUnit());
					commInfo.setCountry(afterinfo.getCountry());
					commInfo.setLevyMode(afterinfo.getLevyMode());//
					commInfo.setUses(afterinfo.getUsesCode());
					commInfo.setProjectDept(afterinfo.getProjectDept());
					commInfo.setBoxNo(afterinfo.getBoxNo());// 箱号
					// ---------------------------------------------------------------
					commInfo.setVersion(afterinfo.getVersion());
					if (other1 != null) {
						commInfo.setUses(commInfo.getUses() == null ? other1
								.getUses() : commInfo.getUses());
						commInfo.setLevyMode(commInfo.getLevyMode() == null ? other1
								.getLevyMode() : commInfo.getLevyMode());
					}
					// --------------------------------------------------------------
					// 件数 == 件数
					commInfo.setPieces(afterinfo.getPiece() == null ? 0
							: afterinfo.getPiece().intValue());
					commInfo.setNetWeight(formatD(afterinfo.getNetWeight() == null ? 0.0
							: afterinfo.getNetWeight())); //
					commInfo.setGrossWeight(formatD(afterinfo.getGrossWeight() == null ? 0.0
							: afterinfo.getGrossWeight()));

					commInfo.setFirstAmount(forInterNum(afterinfo
							.getLegalAmount() == null ? 0.0 : afterinfo
							.getLegalAmount())); // 第一法定数量
					commInfo.setSecondAmount(afterinfo.getSecondLegalAmount() == null ? 0.0
							: afterinfo.getSecondLegalAmount());// 第二法定数量
					commInfo.setCommAmount(afterinfo.getDeclaredAmount() == null ? 0.0
							: afterinfo.getDeclaredAmount());
					commInfo.setWorkUsd(afterinfo.getWorkUsd() == null ? 0.0
							: afterinfo.getWorkUsd());// 加工费总价

					if (param.getPriceType().equals("平均单价")) {
						commInfo.setCommTotalPrice(afterinfo.getTotalPrice());// 总金额
						if (afterinfo.getDeclaredAmount() != null
								&& afterinfo.getDeclaredAmount() != 0) {
							commInfo.setCommUnitPrice(afterinfo.getTotalPrice()
									/ afterinfo.getDeclaredAmount());
						} else {
							commInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						commInfo.setCommUnitPrice(exg.getDeclarePrice() == null ? 0.0
								: exg.getDeclarePrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						commInfo.setCommUnitPrice(exg.getFactoryPrice() == null ? 0.0
								: exg.getFactoryPrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					}
					// --------------------------------------------------------------
					commInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							commInfo.getGrossWeight(), weightFraction));
					commInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							commInfo.getNetWeight(), weightFraction));
					// 小数位控制
					commInfo.setCommAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommAmount()), amountNum));
					commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommUnitPrice()), priceNum));
					// System.out.println("====totalNum=="+totalNum+" 11
					// "+commInfo
					// .getCommTotalPrice()+" 22
					// ="+CommonUtils.getDoubleByDigit(
					// CommonUtils.getDoubleExceptNull(commInfo
					// .getCommTotalPrice()), totalNum));
					commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommTotalPrice()), totalNum));
					commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getWorkUsd()), totalNum));
					// 第一法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getLegalUnit() != null
							&& commInfo.getUnit().getName()
									.equals(commInfo.getLegalUnit().getName())) {
						commInfo.setFirstAmount(commInfo.getCommAmount());
					} else if (commInfo.getLegalUnit() != null
							&& commInfo.getLegalUnit().getName().equals("千克")) {
						commInfo.setFirstAmount(commInfo.getNetWeight());
					}
					// 第二法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getSecondLegalUnit() != null
							&& commInfo
									.getUnit()
									.getName()
									.equals(commInfo.getSecondLegalUnit()
											.getName())) {
						commInfo.setSecondAmount(commInfo.getCommAmount());
					} else if (commInfo.getSecondLegalUnit() != null
							&& commInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						commInfo.setSecondAmount(commInfo.getNetWeight());
					}

					commInfo.setAddType(AddType.APPLY);
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(commInfo);
					commInfoid = commInfo.getId();
				} else {
					// --------------------------------------------------------------
					existcommInfo
							.setPieces(existcommInfo.getPieces() == null ? 0
									: existcommInfo.getPieces()
											+ forInterNum(
													afterinfo.getPiece() == null ? 0.0
															: afterinfo
																	.getPiece())
													.intValue());
					existcommInfo.setBoxNo(afterinfo.getBoxNo());
					existcommInfo.setNetWeight(formatD(existcommInfo// 净重
							.getNetWeight())
							+ formatD(afterinfo.getNetWeight()));
					existcommInfo.setGrossWeight(formatD(existcommInfo// 毛重
							.getGrossWeight())
							+ formatD(afterinfo.getGrossWeight()));
					existcommInfo.setFirstAmount((existcommInfo
							.getFirstAmount() == null ? 0.0 : existcommInfo
							.getFirstAmount())
							+ (afterinfo.getLegalAmount() == null ? 0.0
									: afterinfo.getLegalAmount()));
					existcommInfo.setSecondAmount((existcommInfo
							.getSecondAmount() == null ? 0.0 : existcommInfo
							.getSecondAmount())
							+ (afterinfo.getSecondLegalAmount() == null ? 0.0
									: afterinfo.getSecondLegalAmount()));
					existcommInfo
							.setCommAmount((existcommInfo.getCommAmount() == null ? 0.0
									: existcommInfo.getCommAmount())
									+ afterinfo.getDeclaredAmount());// 数量
					existcommInfo
							.setWorkUsd((existcommInfo.getWorkUsd() == null ? 0.0
									: existcommInfo.getWorkUsd())
									+ afterinfo.getWorkUsd());// 加工费总
					if (param.getPriceType().equals("平均单价")) {
						existcommInfo.setCommTotalPrice(existcommInfo
								.getCommTotalPrice()
								+ afterinfo.getTotalPrice());// 总金额
						if (existcommInfo.getCommAmount() != null
								&& existcommInfo.getCommAmount() != 0) {
							existcommInfo.setCommUnitPrice(existcommInfo
									.getCommTotalPrice()
									/ existcommInfo.getCommAmount());
						} else {
							existcommInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						existcommInfo
								.setCommUnitPrice(exg.getDeclarePrice() == null ? 0.0
										: exg.getDeclarePrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						existcommInfo
								.setCommUnitPrice(exg.getFactoryPrice() == null ? 0.0
										: exg.getFactoryPrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					}
					existcommInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getGrossWeight(), weightFraction));
					existcommInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getNetWeight(), weightFraction));
					existcommInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(existcommInfo
									.getSecondAmount()), amountNum));
					existcommInfo.setCommUnitPrice(CommonUtils
							.getDoubleByDigit(CommonUtils
									.getDoubleExceptNull(existcommInfo
											.getCommUnitPrice()), priceNum));
					// System.out.println("汇总====totalNum=="+totalNum+"11
					// "+commInfo
					// .getCommTotalPrice()+" 22
					// ="+CommonUtils.getDoubleByDigit(
					// CommonUtils.getDoubleExceptNull(commInfo
					// .getCommTotalPrice()), totalNum));
					existcommInfo.setCommTotalPrice(CommonUtils
							.getDoubleByDigit(CommonUtils
									.getDoubleExceptNull(existcommInfo
											.getCommTotalPrice()), totalNum));
					existcommInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(existcommInfo
									.getWorkUsd()), totalNum));
					// 第一法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getLegalUnit()
											.getName())) {
						existcommInfo.setFirstAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getLegalUnit() != null
							&& existcommInfo.getLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setFirstAmount(existcommInfo
								.getNetWeight());
					}
					// 第二法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getSecondLegalUnit()
											.getName())) {
						existcommInfo.setSecondAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setSecondAmount(existcommInfo
								.getNetWeight());
					}
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(existcommInfo);
					commInfoid = existcommInfo.getId();

				}
			}
			afterinfo.setIsTransferCustomsBill(new Boolean(true));// 已转报关单
			((EncDao) this.getBaseEncDao()).saveAtcMergeAfterComInfo(afterinfo);
			// 保存到中间表中----------------------------------------------
			MakeListToCustoms obj = new MakeListToCustoms();
			obj.setAtcMergeAfterComInfo(afterinfo);
			if (existcommInfo != null) {
				obj.setCustomsInfo((CustomsDeclarationCommInfo) existcommInfo);
			} else {
				obj.setCustomsInfo(commInfo);
			}
			obj.setCompany(CommonUtils.getCompany());
			((EncDao) this.getBaseEncDao()).saveMakeListToCustoms(obj);
			// 记录添加过程------------------------------------------------
			CustomsFromMateriel obj1 = new CustomsFromMateriel();
			obj1.setInfoid(commInfoid);
			/*
			 * obj1.setBcusexgbill(exg); obj1.setBcusimgbill(img);
			 */
			obj1.setBcusexgNo(exg == null ? null : exg.getSeqNum());
			obj1.setBcusimgNo(img == null ? null : img.getSeqNum());
			obj1.setScmCoc(customs.getCustomer());
			obj1.setCompany(CommonUtils.getCompany());
			((EncDao) this.getBaseEncDao()).SaveCustomsFromMateriel(obj1);
			// ----------------------------------------------------------
		}

		CompanyOther other = ((EncDao) this.getBaseEncDao())
				.saveCommonUtilsOther();
		// 保存报关单头的隋附单据
		if (other != null) {
			if (other.getIsCustomAutoAttachedBill() != null
					&& other.getIsCustomAutoAttachedBill()) {
				getAttachedBill(customs);
			}
		}
		/**
		 * 是否自动计算报关单件数，毛重及净重
		 * 
		 */
		if (other != null) {
			if (other.getIsAutoWeight() != null && other.getIsAutoWeight()) {
				((EncDao) this.getBaseEncDao()).getPiceAndWeight(customs);
			}
		}

		// -----------------------------------设置报关单汇率
		Double dou = this.getCurrRateByCurr(customs.getCurrency(),
				customs.getDeclarationDate(), customs.getEmsHeadH2k());
		if (dou != null) {
			customs.setExchangeRate(dou);
		}// 设置报关单汇率
		this.baseCodeDao.saveOrUpdate(customs);
		return customs;
	}

	/**
	 * 判断箱号是否存在
	 * 
	 * @param allbillNo
	 *            所有的箱号
	 * @param newbillNo
	 *            新箱号
	 * @return 若存在为true 否则为false
	 */
	public String getNotExistBoxNo(String allbillNo, String newbillNo) {
		String newBoxNo = allbillNo;
		if (newbillNo == null || "".equals(newbillNo)) {
			return newBoxNo;
		}
		String[] yy = newbillNo.split(",");
		for (int i = 0; i < yy.length; i++) {
			if (allbillNo.contains(yy[i])) {
				continue;
			}
			newBoxNo += "," + yy[i];
		}
		return newBoxNo;
	}

	/**
	 * 删除报关单
	 * 
	 * @param customsDeclaration
	 *            报关单信息
	 */
	public void deleteCustomsDeclaration(CustomsDeclaration customsDeclaration) {
		//
		// 删除出口发票项目对象
		//
		List itemList = ((EncDao) this.getBaseEncDao())
				.findExportInvoiceItemByCustomsDeclarationId(customsDeclaration
						.getId());
		for (int i = 0; i < itemList.size(); i++) {
			ExportInvoiceItem exportInvoiceItem = (ExportInvoiceItem) itemList
					.get(i);
			((EncDao) this.getBaseEncDao())
					.deleteExportInvoiceItem(exportInvoiceItem);
		}
		super.deleteCustomsDeclaration(customsDeclaration, false);
	}

	/**
	 * 作废报关单
	 * 
	 * @param customsDeclaration
	 *            报关单信息
	 */
	public void cancelCustomsDeclaration(CustomsDeclaration customsDeclaration) {
		if ((customsDeclaration.getBillListId() != null)
				&& (!customsDeclaration.getBillListId().equals(""))) {
			List list = ((EncDao) this.getBaseEncDao())
					.findApplyToCustomsBillListById(customsDeclaration
							.getBillListId());
			if (list.size() > 0) {
				ApplyToCustomsBillList billList = (ApplyToCustomsBillList) list
						.get(0);
				billList.setListState(Integer
						.valueOf(ApplyToCustomsBillList.DRAFT));
				((EncDao) this.getBaseEncDao())
						.saveApplyToCustomsBillList(billList);
			}
		}
		customsDeclaration.setCancel(new Boolean(true));
		customsDeclaration.setBillListId("");
		((EncDao) this.getBaseEncDao())
				.saveCustomsDeclaration(customsDeclaration);
	}

	/**
	 * 保存报关单商品信息
	 * 
	 * @param isMaterial
	 *            是否是物料
	 * @param tempCommInfoList
	 *            临时报关单信息
	 * @param customsDeclaration
	 *            报关单内容
	 */
	public void saveCustomsDeclarationCommInfo(boolean isMaterial,
			List tempCommInfoList, BaseCustomsDeclaration customsDeclaration) {
		CustomsDeclarationCommInfo commInfo = null;
		TempCustomsDeclarationCommInfo tempCommInfo = null;
		for (int i = 0; i < tempCommInfoList.size(); i++) {
			tempCommInfo = (TempCustomsDeclarationCommInfo) tempCommInfoList
					.get(i);
			commInfo = new CustomsDeclarationCommInfo();
			commInfo.setBaseCustomsDeclaration(customsDeclaration);
			commInfo.setSerialNumber(this
					.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));
			commInfo.setCommSerialNo(Integer.valueOf(tempCommInfo
					.getEmsSerialNo()));
			commInfo.setComplex(tempCommInfo.getComplex());
			commInfo.setCommName(tempCommInfo.getName());
			if (isMaterial) {
				commInfo.setDetailNote(tempCommInfo.getDetailNote()); // 详细型号规格
			}
			commInfo.setCommSpec(tempCommInfo.getSpec());
			commInfo.setUnit(tempCommInfo.getUnit());
			commInfo.setLegalUnit(tempCommInfo.getLegalUnit());
			commInfo.setSecondLegalUnit(tempCommInfo.getLegalUnit2());
			commInfo.setCommSpec(tempCommInfo.getSpec());

			// 规范申报规格
			if (CommonUtils.isEmpty(commInfo.getDeclareSpec())) {
				commInfo.setDeclareSpec(commInfo.getCommSpec());
			}

			if (tempCommInfo.getVersion() != null
					&& !tempCommInfo.getVersion().equals("")) {
				commInfo.setVersion(String.valueOf(tempCommInfo.getVersion()));
			}
			// 默认设置
			commInfo.setUses(tempCommInfo.getUses());
			commInfo.setLevyMode(tempCommInfo.getLevyMode());
			commInfo.setCommUnitPrice(tempCommInfo.getPrice());
			// 详细型号规格
			commInfo.setDetailNote(tempCommInfo.getDetailNote());
			// 第一法定比例因子
			commInfo.setLegalUnitGene(tempCommInfo.getLegalAmount() == null ? 0.0
					: tempCommInfo.getLegalAmount());
			// 第二法定比例因子
			commInfo.setLegalUnit2Gene(tempCommInfo.getSecondAmount() == null ? 0.0
					: tempCommInfo.getSecondAmount());
			((EncDao) this.getBaseEncDao())
					.saveCustomsDeclarationCommInfo(commInfo);
		}
	}

	/**
	 * 根据报关清单取得清单和报关单商品信息的差异
	 * 
	 * @param billList
	 *            报关清单
	 * @return 报关清单和报关单内容
	 */
	public List findDiffrenceAnalyseCommInfo(ApplyToCustomsBillList billList) {
		List result = new ArrayList();
		DiffrenceAnalyseCommInfo diffrenceCommInfo = null;
		AtcMergeAfterComInfo mergeAfterCommInfo = null;
		CustomsDeclarationCommInfo declarationCommInfo = null;
		Object[] objs = null;
		List list = ((EncDao) this.getBaseEncDao())
				.findDiffrenceAnalyseCommInfo(billList);
		for (int i = 0; i < list.size(); i++) {
			objs = (Object[]) list.get(i);
			mergeAfterCommInfo = (AtcMergeAfterComInfo) objs[0];
			declarationCommInfo = (CustomsDeclarationCommInfo) objs[1];
			diffrenceCommInfo = new DiffrenceAnalyseCommInfo();
			if (mergeAfterCommInfo != null) {
				diffrenceCommInfo.setCommSerialNo(Integer
						.valueOf(mergeAfterCommInfo.getEmsSerialNo()));
				diffrenceCommInfo.setComplex(mergeAfterCommInfo.getComplex());
				diffrenceCommInfo.setCommName(mergeAfterCommInfo
						.getComplexName());
				diffrenceCommInfo.setCommSpec(mergeAfterCommInfo
						.getComplexSpec());
				diffrenceCommInfo.setUnit(mergeAfterCommInfo.getUnit());
				diffrenceCommInfo.setAfterMergeAmount(mergeAfterCommInfo
						.getDeclaredAmount());
			}
			if (declarationCommInfo != null) {
				diffrenceCommInfo
						.setCustomsDelarationAmount(declarationCommInfo
								.getCommAmount());
			}
			double afterMergeAmount = 0;
			double customsDelarationAmount = 0;
			if (diffrenceCommInfo.getAfterMergeAmount() != null) {
				afterMergeAmount = diffrenceCommInfo.getAfterMergeAmount()
						.doubleValue();
			}
			if (diffrenceCommInfo.getCustomsDelarationAmount() != null) {
				customsDelarationAmount = diffrenceCommInfo
						.getCustomsDelarationAmount().doubleValue();
			}
			diffrenceCommInfo.setDiffrenceAmount(new Double(afterMergeAmount
					- customsDelarationAmount));
			result.add(diffrenceCommInfo);
		}
		return result;
	}

	public List getTempCustomsDeclarationCommInfo(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration) {
		List result = new ArrayList();
		TempCustomsDeclarationCommInfo commInfo = null;
		List list = null;
		Map map = new HashMap();
		List listForbid = new ArrayList();
		if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
				|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			List<BillPriceMaintenance> blist = this.getBaseEncDao()
					.findBillPriceMaintenance(ProjectType.BCUS,
							customsDeclaration.getCustomer());
			for (BillPriceMaintenance data : blist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
			List<FptBillPriceMaintenance> clist = this.getBaseEncDao()
					.findFptBillPriceMaintenance(ProjectType.BCUS,
							customsDeclaration.getCustomer());
			for (FptBillPriceMaintenance data : clist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
		}
		if (isMaterial) {
			listForbid = this.emsEdiTrDao
					.findCommdityForbid(MaterielType.MATERIEL);
			list = ((EncDao) this.getBaseEncDao())
					.findEdiMaterielInfo(DeclareState.PROCESS_EXE);
		} else {
			listForbid = this.emsEdiTrDao
					.findCommdityForbid(MaterielType.FINISHED_PRODUCT);
			list = ((EncDao) this.getBaseEncDao())
					.findEdiProductInfo(DeclareState.PROCESS_EXE);
		}
		CustomsDeclarationSet other = this.findCustomsSet(customsDeclaration
				.getImpExpType());
		// System.out.println("===list66666666=="+list.size());
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (!isMaterial) { // 成品
				// System.out.println("====objs=="+objs[7].toString());
				List listVersionNo = ((EncDao) this.getBaseEncDao())
						.findVersionNo(objs[7].toString());
				for (int j = 0; j < listVersionNo.size(); j++) {
					commInfo = new TempCustomsDeclarationCommInfo();
					Object verS = listVersionNo.get(j);
					Integer version = 0;
					if (verS != null && !verS.equals("")) {
						version = Integer.valueOf(listVersionNo.get(j)
								.toString());
						commInfo.setVersion(version);
					}

					if (objs[0] != null) {
						commInfo.setEmsSerialNo(objs[0].toString());
					}
					if (objs[1] != null) {
						commInfo.setComplex((Complex) objs[1]);
					}
					if (objs[2] != null) {
						commInfo.setUnit((Unit) objs[2]);
					}
					if (objs[3] != null) {
						commInfo.setLegalUnit((Unit) objs[3]);
					}
					if (objs[4] != null) {
						commInfo.setLegalUnit2((Unit) objs[4]);
					}
					if (objs[5] != null) {
						commInfo.setName(objs[5].toString());
					}
					if (objs[6] != null) {
						commInfo.setSpec(objs[6].toString());
					}

					// 默认设置
					if (other != null) {
						commInfo.setUses(other.getUses());
						commInfo.setLevyMode(other.getLevyMode());
					}
					EmsHeadH2kVersion versionObj = ((EncDao) this
							.getBaseEncDao())
							.findEmsHeadH2kVersion(
									Integer.valueOf(commInfo.getEmsSerialNo()),
									version);
					Double declaraprice = CommonUtils
							.getEmsExgPrice(versionObj);
					if (!Double.valueOf(0).equals(declaraprice)) {
						commInfo.setPrice(declaraprice);
					}
					/*
					 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
					 */
					if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
							|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
						if (objs[0] != null && objs[1] != null) {
							String str = objs[0].toString()
									+ ((Complex) objs[1]).getCode();
							if (map.containsKey(str)) {
								if (!Double.valueOf(0).equals(declaraprice)) {
									commInfo.setPrice((Double) map.get(str));
								}
							}
						}
					}
					/*
					 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
					 */
					if (objs[8] != null) {
						commInfo.setLegalAmount((Double) objs[8]);
					}
					if (objs[9] != null) {
						commInfo.setSecondAmount((Double) objs[9]);
					}
					result.add(commInfo);
				}
			} else { // 料件
				commInfo = new TempCustomsDeclarationCommInfo();
				if (objs[0] != null) {
					commInfo.setEmsSerialNo(objs[0].toString());
				}
				if (objs[1] != null) {
					commInfo.setComplex((Complex) objs[1]);
				}
				if (objs[2] != null) {
					commInfo.setUnit((Unit) objs[2]);
				}
				if (objs[3] != null) {
					commInfo.setLegalUnit((Unit) objs[3]);
				}
				if (objs[4] != null) {
					commInfo.setLegalUnit2((Unit) objs[4]);
				}
				if (objs[5] != null) {
					commInfo.setName(objs[5].toString());
				}
				if (objs[6] != null) {
					commInfo.setSpec(objs[6].toString());
				}
				// 详细型号规格
				if (objs[7] != null) {
					commInfo.setDetailNote(objs[7].toString());
				}

				// 默认设置
				if (other != null) {
					commInfo.setUses(other.getUses());
					commInfo.setLevyMode(other.getLevyMode());
				}
				EmsHeadH2kImg img = ((EncDao) this.getBaseEncDao())
						.getEmsHeadImg(Integer.valueOf(commInfo
								.getEmsSerialNo()));
				Double declaraprice = CommonUtils.getEmsImgPrice(img);
				if (!Double.valueOf(0).equals(declaraprice)) {
					commInfo.setPrice(declaraprice);
				}
				/*
				 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
				 */
				if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
						|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
					if (objs[0] != null && objs[1] != null) {
						String str = objs[0].toString()
								+ ((Complex) objs[1]).getCode();
						if (map.containsKey(str)) {
							if (!Double.valueOf(0).equals(declaraprice)) {
								commInfo.setPrice((Double) map.get(str));
							}
						}
					}
				}
				/*
				 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
				 */
				if (objs[8] != null) {
					commInfo.setLegalAmount((Double) objs[8]);
				}
				if (objs[9] != null) {
					commInfo.setSecondAmount((Double) objs[9]);
				}
				result.add(commInfo);
			}

		}
		// --------------------------------------------------------------------------
		List maoForbid = new ArrayList();
		for (int i = 0; i < listForbid.size(); i++) {
			CommdityForbid f = (CommdityForbid) listForbid.get(i);
			if (f == null || f.getSeqNum() == null) {
				continue;
			}
			String key = f.getSeqNum().toString()
					+ (f.getVersion() == null ? "" : f.getVersion().trim());
			maoForbid.add(key);
		}
		List rlist = new ArrayList();
		for (int i = 0; i < result.size(); i++) {
			TempCustomsDeclarationCommInfo info = (TempCustomsDeclarationCommInfo) result
					.get(i);
			String key = (info.getEmsSerialNo() == null ? "" : info
					.getEmsSerialNo())
					+ (info.getVersion() == null ? "" : info.getVersion()
							.toString());
			if (!maoForbid.contains(key)) {
				rlist.add(info);
			} else {
				System.out.println("商品已禁用：" + key);
			}
		}
		return rlist;
	}

	/**
	 * 取得报关单商品临时信息(如果在报关单中已存在的话，将其过滤)
	 * 
	 * @param isMaterial
	 *            true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单
	 * @return 经过过滤后的报关单信息
	 */
	public List getTempCustomsDeclarationCommInfo(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration, String sfield,
			Object svalues) {
		List result = new ArrayList();
		TempCustomsDeclarationCommInfo commInfo = null;
		List list = null;
		Map map = new HashMap();
		List listForbid = new ArrayList();
		if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
				|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			List<BillPriceMaintenance> blist = this.getBaseEncDao()
					.findBillPriceMaintenance(ProjectType.BCUS,
							customsDeclaration.getCustomer());
			for (BillPriceMaintenance data : blist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
			List<FptBillPriceMaintenance> clist = this.getBaseEncDao()
					.findFptBillPriceMaintenance(ProjectType.BCUS,
							customsDeclaration.getCustomer());
			for (FptBillPriceMaintenance data : clist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
		}
		if (isMaterial) {
			listForbid = this.emsEdiTrDao
					.findCommdityForbid(MaterielType.MATERIEL);
			list = ((EncDao) this.getBaseEncDao()).findEdiMaterielInfo(
					DeclareState.PROCESS_EXE,
					(CustomsDeclaration) customsDeclaration, sfield, svalues);
		} else {
			listForbid = this.emsEdiTrDao
					.findCommdityForbid(MaterielType.FINISHED_PRODUCT);
			list = ((EncDao) this.getBaseEncDao()).findEdiProductInfo(
					DeclareState.PROCESS_EXE,
					(CustomsDeclaration) customsDeclaration, sfield, svalues);
		}
		CustomsDeclarationSet other = this.findCustomsSet(customsDeclaration
				.getImpExpType());
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (!isMaterial) { // 成品
				List listVersionNo = ((EncDao) this.getBaseEncDao())
						.findVersionNo(objs[7].toString());
				// 当是成品退厂返工或者返工复出的时候，增加被禁用的版本
				if (customsDeclaration.getImpExpType() == ImpExpType.BACK_FACTORY_REWORK
						|| customsDeclaration.getImpExpType() == ImpExpType.REWORK_EXPORT) {
					List listVersion = ((EncDao) this.getBaseEncDao())
							.findVersion(objs[7].toString());
					if (listVersion != null && listVersion.size() > 0) {
						for (int j = 0; j < listVersion.size(); j++) {
							if (!listVersionNo.contains(listVersion.get(j))) {
								listVersionNo.add(listVersion.get(j));
							}
						}
					}
				}
				for (int j = 0; j < listVersionNo.size(); j++) {
					commInfo = new TempCustomsDeclarationCommInfo();
					Object verS = listVersionNo.get(j);
					Integer version = 0;
					if (verS != null && !verS.equals("")) {
						version = Integer.valueOf(listVersionNo.get(j)
								.toString());
						commInfo.setVersion(version);
					}

					if (objs[0] != null) {
						commInfo.setEmsSerialNo(objs[0].toString());
					}
					if (objs[1] != null) {
						commInfo.setComplex((Complex) objs[1]);
					}
					if (objs[2] != null) {
						commInfo.setUnit((Unit) objs[2]);
					}
					if (objs[3] != null) {
						commInfo.setLegalUnit((Unit) objs[3]);
					}
					if (objs[4] != null) {
						commInfo.setLegalUnit2((Unit) objs[4]);
					}
					if (objs[5] != null) {
						commInfo.setName(objs[5].toString());
					}
					if (objs[6] != null) {
						commInfo.setSpec(objs[6].toString());
					}

					// 默认设置
					if (other != null) {
						commInfo.setUses(other.getUses());
						commInfo.setLevyMode(other.getLevyMode());
					}
					EmsHeadH2kVersion versionObj = ((EncDao) this
							.getBaseEncDao())
							.findEmsHeadH2kVersion(
									Integer.valueOf(commInfo.getEmsSerialNo()),
									version);
					Double declaraprice = CommonUtils
							.getEmsExgPrice(versionObj);
					if (!Double.valueOf(0).equals(declaraprice)) {
						commInfo.setPrice(declaraprice);
					}
					/*
					 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
					 */
					if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
							|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
						if (objs[0] != null && objs[1] != null) {
							String str = objs[0].toString()
									+ ((Complex) objs[1]).getCode();
							if (map.containsKey(str)) {
								if (!Double.valueOf(0).equals(declaraprice)) {
									commInfo.setPrice((Double) map.get(str));
								}
							}
						}
					}
					/*
					 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
					 */
					if (objs[8] != null) {
						commInfo.setLegalAmount((Double) objs[8]);
					}
					if (objs[9] != null) {
						commInfo.setSecondAmount((Double) objs[9]);
					}
					result.add(commInfo);
				}
			} else { // 料件
				commInfo = new TempCustomsDeclarationCommInfo();
				if (objs[0] != null) {
					commInfo.setEmsSerialNo(objs[0].toString());
				}
				if (objs[1] != null) {
					commInfo.setComplex((Complex) objs[1]);
				}
				if (objs[2] != null) {
					commInfo.setUnit((Unit) objs[2]);
				}
				if (objs[3] != null) {
					commInfo.setLegalUnit((Unit) objs[3]);
				}
				if (objs[4] != null) {
					commInfo.setLegalUnit2((Unit) objs[4]);
				}
				if (objs[5] != null) {
					commInfo.setName(objs[5].toString());
				}
				if (objs[6] != null) {
					commInfo.setSpec(objs[6].toString());
				}
				// 详细型号规格
				if (objs[7] != null) {
					commInfo.setDetailNote(objs[7].toString());
				}

				// 默认设置
				if (other != null) {
					commInfo.setUses(other.getUses());
					commInfo.setLevyMode(other.getLevyMode());
				}
				EmsHeadH2kImg img = ((EncDao) this.getBaseEncDao())
						.getEmsHeadImg(Integer.valueOf(commInfo
								.getEmsSerialNo()));
				Double declaraprice = CommonUtils.getEmsImgPrice(img);
				if (!Double.valueOf(0).equals(declaraprice)) {
					commInfo.setPrice(declaraprice);
				}
				/*
				 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
				 */
				if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
						|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
					if (objs[0] != null && objs[1] != null) {
						String str = objs[0].toString()
								+ ((Complex) objs[1]).getCode();
						if (map.containsKey(str)) {
							if (!Double.valueOf(0).equals(declaraprice)) {
								commInfo.setPrice((Double) map.get(str));
							}
						}
					}
				}
				/*
				 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
				 */
				if (objs[8] != null) {
					commInfo.setLegalAmount((Double) objs[8]);
				}
				if (objs[9] != null) {
					commInfo.setSecondAmount((Double) objs[9]);
				}
				result.add(commInfo);
			}
		}
		// --------------------------------------------------------------------------
		List maoForbid = new ArrayList();
		for (int i = 0; i < listForbid.size(); i++) {
			CommdityForbid f = (CommdityForbid) listForbid.get(i);
			if (f == null || f.getSeqNum() == null) {
				continue;
			}
			String key = f.getSeqNum().toString() + "/"
					+ (f.getVersion() == null ? "" : f.getVersion().trim());
			maoForbid.add(key);
		}
		List rlist = new ArrayList();
		for (int i = 0; i < result.size(); i++) {
			TempCustomsDeclarationCommInfo info = (TempCustomsDeclarationCommInfo) result
					.get(i);
			String key = (info.getEmsSerialNo() == null ? "" : info
					.getEmsSerialNo())
					+ "/"
					+ (info.getVersion() == null ? "" : info.getVersion()
							.toString());
			if (customsDeclaration.getImpExpType() == ImpExpType.BACK_FACTORY_REWORK
					|| customsDeclaration.getImpExpType() == ImpExpType.REWORK_EXPORT) {
				rlist.add(info);
			} else {
				if (!maoForbid.contains(key)) {
					rlist.add(info);
				} else {
					System.out.println("商品已禁用：" + key);
				}
			}
		}
		return rlist;
	}

	/**
	 * 取得基础代码Dao
	 * 
	 * @return 基础代码Dao
	 */
	public BaseCodeDao getBaseCodeDao() {
		return baseCodeDao;
	}

	/**
	 * 设计基础代码Dao
	 * 
	 * @param baseCodeDao
	 *            基础代码Dao
	 */
	public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
		this.baseCodeDao = baseCodeDao;
	}

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	public List findTempImpExpRequestBillByImpExpTypeToATC(int impExpType) {
		List list = ((EncDao) this.getBaseEncDao())
				.findImpExpRequestBillByImpExpTypeToATC(impExpType);
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ImpExpRequestBill obj = (ImpExpRequestBill) list.get(i);
			TempImpExpRequestBill temp = new TempImpExpRequestBill();
			temp.setImpExpRequestBill(obj);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	public List findTempImpExpRequestBillByImpExpTypeToATC(
			Date beginAvailabilityDate, Date endAvailabilityDate,
			String billNo, int impExpType) {
		List list = ((EncDao) this.getBaseEncDao())
				.findImpExpRequestBillByImpExpTypeToATC(beginAvailabilityDate,
						endAvailabilityDate, billNo, impExpType);
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ImpExpRequestBill obj = (ImpExpRequestBill) list.get(i);
			TempImpExpRequestBill temp = new TempImpExpRequestBill();
			temp.setImpExpRequestBill(obj);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 申请单表头是否完全转报关单及所转清单
	 * 
	 * @param list
	 * @param listNo
	 */
	public void changeImpexpbillByBillNo(List list, String listNo) {// String
		// listNo
		for (int i = 0; i < list.size(); i++) {
			ImpExpRequestBill obj = (ImpExpRequestBill) list.get(i);
			List listdetail = ((EncDao) this.getBaseEncDao())
					.findImpExpCommodityInfoByisTransferCustomsBill(obj);
			/*
			 * if (listdetail == null || listdetail.size() <= 0) {
			 * obj.setIsCustomsBill(new Boolean(true)); }
			 */
			// 判断表头是否完全转报关单
			System.out.println("================yyyyyyyy" + listNo);
			if (listdetail != null && listdetail.size() > 0) {
				obj.setIsCustomsBill(new Boolean(false));
			} else {
				obj.setIsCustomsBill(new Boolean(true));
			}
			String AlllistNo = obj.getAllBillNo();
			if (!isExistBillNo(AlllistNo, listNo)) {
				if (AlllistNo != null && !AlllistNo.equals("")) {
					obj.setAllBillNo(AlllistNo + "," + listNo);
				} else {
					obj.setAllBillNo(listNo);
				}
			}
			obj.setToCustomCount((obj.getItemCount() == null ? Integer
					.valueOf(0) : obj.getItemCount())
					- (listdetail == null ? Integer.valueOf(0) : listdetail
							.size()));
			((EncDao) this.getBaseEncDao()).saveImpExpRequestBill(obj);
		}
	}

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param parentList
	 *            父对象
	 * @return 进出口商品信息
	 */
	public List findTempImpExpCommodityInfoByParent(List parentList) {
		List list = ((EncDao) this.getBaseEncDao())
				.findImpExpCommodityInfoByParent(parentList);
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempImpExpCommodityInfo temp = new TempImpExpCommodityInfo();
			temp.setImpExpCommodityInfo((ImpExpCommodityInfo) list.get(i));
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 删除进出口商品信息数据
	 * 
	 * @param list
	 *            进出口商品信息
	 */
	public void deleteImpExpCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo data = (ImpExpCommodityInfo) list.get(i);
			writeBackBillDetailToCasFactoryCommodityInfoId(data.getId());
			((EncDao) this.getBaseEncDao()).deleteImpExpCommodityInfo(data);
		}
	}

	/**
	 * 删除进出口申请单数据
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单据
	 */
	public void deleteImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		List list = ((EncDao) this.getBaseEncDao())
				.findImpExpCommodityInfo(impExpRequestBill.getId());

		MakeImpExpRequestBill mieb = null;
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo imp = (ImpExpCommodityInfo) list.get(i);
			writeBackBillDetailToCasFactoryCommodityInfoId(imp.getId());
			mieb = casDao.findMakeImpExpRequestBillByIEId(imp.getId());
			((EncDao) this.getBaseEncDao()).deleteImpExpCommodityInfo(imp);
			casDao.delete(mieb);
		}

		ImpBackPortRequestBook obj = ((EncDao) this.getBaseEncDao())
				.findImpBackPortRequestBookById(impExpRequestBill.getId());
		if (obj != null) {
			((EncDao) this.getBaseEncDao()).deleteImpBackPortRequestBook(obj);
		} else {
			ExpBackPortRequestBook exp = ((EncDao) this.getBaseEncDao())
					.findExpBackPortRequestBookById(impExpRequestBill.getId());
			if (exp != null) {
				((EncDao) this.getBaseEncDao())
						.deleteExpBackPortRequestBook(exp);
			}
		}
		((EncDao) this.getBaseEncDao())
				.deleteImpExpRequestBill(impExpRequestBill);
		// 检查此单据是否从单据中心导入，如果是，删除的时候要set isImpExpRequestBill = 0
		String billCode = getBillCode(impExpRequestBill);
		BillType billType = casDao.findBillTypeByCode(billCode);
		if (billType != null) {
			BillMaster billmaster = casDao.findBillMasterByBillTypeAndBillNo(
					billType, impExpRequestBill.getBillNo());
			if (billmaster != null) {
				List<BillDetail> detaillist = casDao.findBillDetail(billmaster);
				for (BillDetail e : detaillist) {
					e.setIsImpExpRequestBill(false);
					casDao.saveBillDetail(e);
				}
			}
		}
	}

	public String getBillCode(ImpExpRequestBill impExpRequestBill) {
		String billcode = new String();
		if (impExpRequestBill.getBillType() == ImpExpType.DIRECT_IMPORT) {
			// 直接进口：1003-----料件进口
			billcode = "1003";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.GENERAL_TRADE_IMPORT) {
			// 海关征税进口：1011-----一般性贸易进口
			billcode = "1011";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			// 转厂进口：1004-----料件进口
			billcode = "1004";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.BACK_MATERIEL_EXPORT) {
			// 料件退回：1102---料件出口
			billcode = "1102";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			// 料件转仓出库：1105----料件转厂
			billcode = "1105";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.BACK_FACTORY_REWORK) {
			// 退厂返工：2003-----退厂返工
			billcode = "2003";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.DIRECT_EXPORT) {
			// 直接出口：2101-----成品出口
			billcode = "2101";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.TRANSFER_FACTORY_EXPORT) {
			// 结转出口：2102-----转厂出口
			billcode = "2101";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.REWORK_EXPORT) {
			// 返工复出：2104---返工复出
			billcode = "2104";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.REMIAN_MATERIAL_BACK_PORT) {
			// 边角料退运出口：6102--边角料退港
			billcode = "6102";// billCode
		} else if (impExpRequestBill.getBillType() == ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES) {
			// 边角料征税内销：6104---边角料内销
			billcode = "6104";// billCode
		}
		return billcode;
	}

	/**
	 * 生成进出口申请单据
	 * 
	 * @param tempBillMasterList
	 *            临时单据表头
	 * @param tempBillDetailList
	 *            临时单据明细/商品信息
	 */
	public void makeImpExpRequestBill(List tempBillMasterList,
			List tempBillDetailList) {
		List makeImpExpRequestBillList = new ArrayList();
		for (int i = 0; i < tempBillMasterList.size(); i++) {
			BillMaster billMaster = ((TempBillMaster) tempBillMasterList.get(i))
					.getBillMaster();
			ImpExpRequestBill impExpRequestBill = this
					.makeImpExpRequestBill(billMaster);
			int itemCount = 0;
			for (int j = 0; j < tempBillDetailList.size(); j++) {
				BillDetail billDetail = ((TempBillDetail) tempBillDetailList
						.get(j)).getBillDetail();
				if (billMaster.getId().equals(
						billDetail.getBillMaster().getId()) == true) {
					ImpExpCommodityInfo impExpCommodityInfo = this
							.makeImpExpCommodityInfo(impExpRequestBill,
									billDetail);
					//
					// 生成中间表
					//
					makeImpExpRequestBillList.add(this
							.makeMakeImpExpRequestBill(impExpCommodityInfo,
									billDetail));
					//
					// 修改海关帐商品信息中已转结转单据为true
					//
					billDetail.setIsImpExpRequestBill(new Boolean(true));
					casDao.saveBillDetail(billDetail);
					itemCount++;
				}
			}
			//
			// 再次保存结转商品对象个数的信息
			//
			impExpRequestBill.setItemCount(new Integer(itemCount));
			((EncDao) this.getBaseEncDao())
					.saveImpExpRequestBill(impExpRequestBill);
		}
		//
		// 保存所有中间表信息
		//
		((EncDao) this.getBaseEncDao())
				.saveMakeImpExpRequestBill(makeImpExpRequestBillList);
	}

	/**
	 * 生成由海关单据到进出口申请单中间表
	 * 
	 * @param impExpCommodityInfo
	 *            进出口商品信息
	 * @param billDetail
	 *            单据明细
	 * @return 进出口申请单中间表
	 */
	private MakeImpExpRequestBill makeMakeImpExpRequestBill(
			ImpExpCommodityInfo impExpCommodityInfo, BillDetail billDetail) {
		MakeImpExpRequestBill m = new MakeImpExpRequestBill();
		m.setCompany(CommonUtils.getCompany());
		m.setCfcId(billDetail.getId());
		m.setIecId(impExpCommodityInfo.getId());
		return m;
	}

	/**
	 * 生成进出口单据
	 * 
	 * @param billMaster
	 *            单据表头
	 * @return 进出口申请单据
	 */
	private ImpExpRequestBill makeImpExpRequestBill(BillMaster billMaster) {
		ImpExpRequestBill t = new ImpExpRequestBill();
		t.setCompany(CommonUtils.getCompany());
		t.setScmCoc(billMaster.getScmCoc());
		t.setBeginAvailability(billMaster.getValidDate());
		// t.setWareSet(billMaster.getWareSet());
		t.setIsAvailability(new Boolean(false));
		t.setImputDate(new Date());
		t.setConveyance(null);
		t.setIsCustomsBill(new Boolean(false));
		int billType = billMaster.getBillType().getBillType().intValue();
		String billCode = billMaster.getBillType().getCode();
		Integer materielProductFlag = getMaterielProductFlag(billType, billCode);
		t.setMaterielProductFlag(materielProductFlag);
		Integer impExpBillType = getImpExpRequestBillType(billType, billCode);
		t.setBillType(impExpBillType);
		// t.setBillNo(getMaxBillNoByType(impExpBillType.intValue()));
		t.setBillNo(billMaster.getBillNo());

		((EncDao) this.getBaseEncDao()).saveImpExpRequestBill(t);
		return t;
	}

	/**
	 * 获得成品料件标识
	 * 
	 * @param billType
	 *            海关单据类型
	 * @param billCode
	 *            海关单据类型编号
	 * @return 与单据类型,编号匹配的成品料件标识
	 */
	private Integer getMaterielProductFlag(int billType, String billCode) {
		int flag = -1;
		if (billType == SBillType.MATERIEL_IN) {// 1、 料件入仓
			if (billCode.equalsIgnoreCase("1003")) {// 直接进口：1003-----料件进口
				flag = Integer.parseInt(MaterielType.MATERIEL);
			} else if (billCode.equalsIgnoreCase("1011")) {// 海关征税进口：1011-----一般性贸易进口
				flag = Integer.parseInt(MaterielType.MATERIEL);
			} else if (billCode.equalsIgnoreCase("1004")) {// 1104----料件转厂
				flag = Integer.parseInt(MaterielType.MATERIEL);
			}
		} else if (billType == SBillType.MATERIEL_OUT) {// 2、 料件出仓
			if (billCode.equalsIgnoreCase("1102")) {// 料件退回：1102---料件出口
				flag = Integer.parseInt(MaterielType.MATERIEL);
			} else if (billCode.equalsIgnoreCase("1103")) {// 料件复出：1105----料件转厂
				flag = Integer.parseInt(MaterielType.MATERIEL);
			}
		} else if (billType == SBillType.PRODUCE_IN) {// //成品入库
			if (billCode.equalsIgnoreCase("2003")) {// 退厂返工：2003-----退厂返工
				flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			}
		} else if (billType == SBillType.PRODUCE_OUT) {// //成品出库
			if (billCode.equalsIgnoreCase("2101")) {// 直接出口：2101-----成品出口
				flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			} else if (billCode.equalsIgnoreCase("2102")) {// 结转出口：2102-----转厂出口
				flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			} else if (billCode.equalsIgnoreCase("2104")) {// 返工复出：2104---返工复出
				flag = Integer.parseInt(MaterielType.FINISHED_PRODUCT);
			}
		} else if (billType == SBillType.LEFTOVER_MATERIEL_OUT) {// //9、
			// 边角料出仓
			if (billCode.equalsIgnoreCase("6102")) {// 边角料退运出口：6102--边角料退港
				flag = Integer.parseInt(MaterielType.MATERIEL);
			} else if (billCode.equalsIgnoreCase("6104")) {// 边角料征税内销：6104---边角料内销
				flag = Integer.parseInt(MaterielType.MATERIEL);
			}
		}
		return new Integer(flag);
	}

	/**
	 * 获得进出口申请单据类型
	 * 
	 * @param billType
	 *            海关单据类型
	 * @param billCode
	 *            海关单据类型编号
	 * @return 与单据类型,编号匹配的进出口申请单据类型
	 */
	private Integer getImpExpRequestBillType(int billType, String billCode) {
		int flag = -1;
		if (billType == SBillType.MATERIEL_IN) {// 1、 料件入仓
			if (billCode.equalsIgnoreCase("1003")) {// 直接进口：1003-----料件进口
				flag = ImpExpType.DIRECT_IMPORT;
			} else if (billCode.equalsIgnoreCase("1011")) {// 海关征税进口：1011-----一般性贸易进口
				flag = ImpExpType.GENERAL_TRADE_IMPORT;
			} else if (billCode.equalsIgnoreCase("1004")) {
				flag = ImpExpType.TRANSFER_FACTORY_IMPORT;// 转厂进口：1003-----料件进口
			}
		} else if (billType == SBillType.MATERIEL_OUT) {// 2、 料件出仓
			if (billCode.equalsIgnoreCase("1102")
					|| billCode.equalsIgnoreCase("1103")) {// 料件退回：1102---料件出口
				flag = ImpExpType.BACK_MATERIEL_EXPORT;
			} else if (billCode.equalsIgnoreCase("1105")) {// 料件转仓出库：1105----料件转厂
				flag = ImpExpType.TRANSFER_FACTORY_IMPORT;
			}
		} else if (billType == SBillType.PRODUCE_IN) {// //成品入库
			if (billCode.equalsIgnoreCase("2003")) {// 退厂返工：2003-----退厂返工
				flag = ImpExpType.BACK_FACTORY_REWORK;
			}
		} else if (billType == SBillType.PRODUCE_OUT) {// //成品出库
			if (billCode.equalsIgnoreCase("2101")) {// 直接出口：2101-----成品出口
				flag = ImpExpType.DIRECT_EXPORT;
			} else if (billCode.equalsIgnoreCase("2102")) {// 结转出口：2102-----转厂出口
				flag = ImpExpType.TRANSFER_FACTORY_EXPORT;
			} else if (billCode.equalsIgnoreCase("2104")) {// 返工复出：2104---返工复出
				flag = ImpExpType.REWORK_EXPORT;
			}
		} else if (billType == SBillType.LEFTOVER_MATERIEL_OUT) {// //9、
			// 边角料出仓
			if (billCode.equalsIgnoreCase("6102")) {// 边角料退运出口：6102--边角料退港
				flag = ImpExpType.REMIAN_MATERIAL_BACK_PORT;
			} else if (billCode.equalsIgnoreCase("6104")) {// 边角料征税内销：6104---边角料内销
				flag = ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES;
			}
		}
		return new Integer(flag);
	}

	/**
	 * 生成进出口商品信息
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单
	 * @param billDetail
	 *            单据明细
	 * @return 与指定的申请单单据明细匹配的进出口商品信息
	 */
	private ImpExpCommodityInfo makeImpExpCommodityInfo(
			ImpExpRequestBill impExpRequestBill, BillDetail billDetail) {
		ImpExpCommodityInfo i = new ImpExpCommodityInfo();
		i.setImpExpRequestBill(impExpRequestBill);
		// i.setBulks(billDetail)
		// i.setCountry()
		// i.setCurrency(billDetail.getCurr());
		// i.setGrossWeight()
		i.setIsPutOnRecord(new Boolean(false));
		i.setIsTransferCustomsBill(new Boolean(false));
		i.setMakeBillNo(billDetail.getProductNo());
		i.setMateriel(getMaterielByPtNo(billDetail.getPtPart()));
		i.setNetWeight(billDetail.getNetWeight());
		i.setQuantity(billDetail.getPtAmount());
		i.setUnitPrice(billDetail.getPtPrice());
		// TODO chensir edit
		List<InnerMergeData> imps = encDao
				.findImpExpCommodtyInfoByMateriel(billDetail.getPtPart());
		if (imps.size() > 0) {
			InnerMergeData imp = imps.get(0);
			i.setSeqNum(imp.getHsAfterTenMemoNo());
			i.setAfterName(imp.getHsAfterMaterielTenName());
			i.setAfterSpec(imp.getHsAfterMaterielTenSpec());
			i.setAfterUnit(imp.getHsAfterMemoUnit().getName());
		}
		// TODO
		impExpRequestBill.setWareSet(billDetail.getWareSet());
		if (billDetail.getVersion() != null) {
			i.setVersion(billDetail.getVersion().toString());
		}
		List list = new ArrayList();
		list.add(i);
		((EncDao) this.getBaseEncDao()).saveImpExpCommodityInfo(list);
		return i;
	}

	private Map map = null;

	/**
	 * 通过ptNo来获得Materiel对象
	 * 
	 * @param ptNo
	 *            工厂料号
	 * @return 物料内容
	 */
	private Materiel getMaterielByPtNo(String ptNo) {
		if (map == null) {
			map = new HashMap();
			List list = commonCodeDao.findMateriel();
			for (int i = 0; i < list.size(); i++) {
				Materiel m = (Materiel) list.get(i);
				map.put(m.getPtNo(), m);
			}
		}
		return (Materiel) map.get(ptNo);
	}

	/**
	 * 回写单据明细转为海关工厂商品信息
	 * 
	 * @param impExpCommodityInfoId
	 *            进出口商品信息
	 */
	private void writeBackBillDetailToCasFactoryCommodityInfoId(
			String impExpCommodityInfoId) {
		List dataSource = ((EncDao) this.getBaseEncDao())
				.findMakeImpExpRequestBillByImpExpCommodityInfo(impExpCommodityInfoId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			MakeImpExpRequestBill m = (MakeImpExpRequestBill) dataSource.get(i);
			//
			// 修改海关商品资料已转进出口单据为false
			//
			// BillDetail billDetail = null;
			// BillDetail billDetail = casDao
			// .findBillDetailById(casFactoryCommodityInfoId);
			// if (billDetail != null) {
			// billDetail.setIsImpExpRequestBill(new Boolean(false));
			// casDao.saveBillDetail(billDetail);
			// }
			//
			// 删除中间表信息
			//
			((EncDao) this.getBaseEncDao()).deleteMakeImpExpRequestBill(m);
		}
	}

	/**
	 * 进出口申请单转报关清单的检查--返回没有在电子帐册中备案的数据(成品)
	 * 
	 * @param list
	 *            临时进出口商品信息
	 * @param emsEdiMergerHead
	 *            经营范围归并表头
	 * @param type
	 *            物料类型
	 * @return 没有在电子帐册中备案的成品数据
	 */
	public List checkTempImpExpCommodityInfoByFinishProduct(List list,
			EmsEdiMergerHead emsEdiMergerHead, String type) {
		List newList = new ArrayList();
		List emsList = fptManageDao.findEmsEdiMergerBeforeByPtNo(
				emsEdiMergerHead, type); // 返回所有归并关系归并前料件或成品
		Map map = new HashMap();
		if (emsList.size() <= 0) {
			return list;
		}
		for (int i = 0; i < emsList.size(); i++) {
			String ptNo = emsList.get(i).toString().toUpperCase().trim();
			map.put(ptNo, ptNo);
		}
		for (int i = 0; i < list.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) list.get(i);
			Object ptNo = map.get(t.getImpExpCommodityInfo().getMateriel()
					.getPtNo().trim().toUpperCase());
			if (ptNo == null) {
				newList.add(t);
			}
		}
		return newList;
	}

	/**
	 * 申请单转清单时归并关系的来源选择
	 * 
	 * @param emsH2k
	 * @param emsEdiMergerHead
	 * @param type
	 * @param isbeian
	 * @param emsFrom
	 * @return
	 */
	public List findInnerMergeDataByPtNo(EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead, String type, boolean isbeian,
			String emsFrom) {
		if (emsFrom.equals("1")) {// 走清单申报要注意是否在电子帐册归并关系中备案
			return fptManageDao.findInnerMergeDataByPtNo(emsEdiMergerHead,
					emsH2k, type);
		} else if (emsFrom.equals("2")) {
			// 获取所有已在电子帐册备案的料号
			return fptManageDao.findInnerMergeDataByPtNo(emsH2k, type);
		}
		return null;
	}

	/**
	 * 得到当前物料不在电子帐册中备案的记录
	 * 
	 * @param impexpbills
	 *            临时进出口商品信息
	 * @param emsH2k
	 *            电子帐册表头
	 * @param emsEdiMergerHead
	 *            经营范围归并信息表头
	 * @param type
	 *            物料类型
	 * @return 没有在电子帐册中备案的物料记录
	 */
	public List checkTempImpExpCommodityInfoByFinishProduct2(List impexpbills,
			EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead, String type,
			boolean isbeian, String emsFrom) {// isBillCustoms
		// ：表示是否清单申报
		List newList = new ArrayList();
		List emsList = null;
		if (emsFrom.equals("1")) {// 走清单申报要注意是否在电子帐册归并关系中备案
			emsList = fptManageDao.findInnerMergeDataByPtNo(emsEdiMergerHead,
					emsH2k, type);
		} else if (emsFrom.equals("2")) {
			// 获取所有已在电子帐册备案的料号
			emsList = fptManageDao.findInnerMergeDataByPtNo(emsH2k, type);
		} else {
			return impexpbills;
		}
		Map map = new HashMap();
		if (emsList.size() <= 0) {
			return impexpbills;
		}
		for (int i = 0; i < emsList.size(); i++) {
			Object[] object = (Object[]) emsList.get(i);
			String ptNo = object[0].toString().toUpperCase().trim();
			map.put(ptNo, ptNo);
		}

		for (int i = 0; i < impexpbills.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) impexpbills
					.get(i);
			Object ptNo = map
					.get(t.getImpExpCommodityInfo().getMateriel() == null ? t
							.getImpExpCommodityInfo().getMakeBillNo() : t
							.getImpExpCommodityInfo().getMateriel().getPtNo()
							.trim().toUpperCase());
			if (isbeian) {
				// 返回已备案
				if (ptNo != null) {
					newList.add(t);
				}
			} else {
				// 返回未备案的料号
				if (ptNo == null) {
					newList.add(t);
				}
			}
		}
		return newList;
	}

	/**
	 * 进出口申请单转报关清单的检查--返回没有在电子帐册中备案的数据(料件)
	 * 
	 * @param list
	 *            临时进出口商品信息
	 * @param emsH2k
	 *            电子帐册表头
	 * @param emsEdiMergerHead
	 *            经营范围归并关系表头
	 * @return 没有在电子帐册中备案的料件数据
	 */
	public List checkTempImpExpCommodityInfoByMateriel(List list,
			EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead) {
		List newList = new ArrayList();
		Map map = new HashMap();
		List emsList = fptManageDao.findEmsEdiMergerImgBeforeByEms(emsH2k,
				emsEdiMergerHead);
		if (emsList.size() <= 0) {
			return list;
		}
		for (int i = 0; i < emsList.size(); i++) {
			String ptNo = emsList.get(i).toString().toUpperCase().trim();
			map.put(ptNo, ptNo);
		}
		for (int i = 0; i < list.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) list.get(i);
			Object ptNo = map.get(t.getImpExpCommodityInfo().getMateriel()
					.getPtNo().trim().toUpperCase());
			if (ptNo == null) {
				newList.add(t);
			}
		}
		return newList;
	}

	/**
	 * 计算单耗
	 * 
	 * @param bom
	 *            电子帐册单耗表
	 * @return 指定单耗表的单耗情况
	 */
	private Double calUnitWaste(EmsHeadH2kBom bom) {
		Double wear = bom.getWear(); // 损耗
		if (wear == null || wear.doubleValue() == 0) {
			return new Double(0);
		}
		return new Double((wear.doubleValue() / 100.0)
				* calTotalWaste(bom).doubleValue());
	}

	/**
	 * 计算总耗
	 * 
	 * @param bom
	 *            电子帐册单耗表
	 * @return 指定的电子帐册单耗表的总耗
	 */
	private Double calTotalWaste(EmsHeadH2kBom bom) {
		Double unitWear = bom.getUnitWear(); // 单耗
		Double wear = bom.getWear(); // 损耗
		if (unitWear == null || wear == null || unitWear.doubleValue() == 0
				|| wear.doubleValue() == 0) {
			return new Double(0);
		}
		return new Double(unitWear.doubleValue()
				/ (1.0 - wear.doubleValue() / 100.0));
	}

	/**
	 * 报关单边角料统计
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内报关边角料的统计情况
	 */
	public List statRemainMaterial(Date beginDate, Date endDate) {
		List list = ((EncDao) this.getBaseEncDao()).findTotalExportCommInfo(
				beginDate, endDate);
		List<TempRemainMaterialStat> rmList = new ArrayList<TempRemainMaterialStat>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			double commAmount = objs[2] == null ? 0 : Double
					.parseDouble(objs[2].toString());
			List lsBom = ((EncDao) this.getBaseEncDao()).findBomByCommSerialNo(
					objs[0].toString(), objs[1].toString());
			for (int j = 0; j < lsBom.size(); j++) {
				EmsHeadH2kBom bom = (EmsHeadH2kBom) lsBom.get(j);
				TempRemainMaterialStat rm = new TempRemainMaterialStat();
				rm.setMaterialCode(bom.getComplex().getCode());
				rm.setMaterialName(bom.getName());
				rm.setMaterialSpec(bom.getSpec());
				rm.setUnit(bom.getUnit().getName());
				rm.setTotalWasteAmount(new Double(calTotalWaste(bom)
						.doubleValue() * commAmount));
				rm.setRemainMaterialTotalAmount(new Double(calUnitWaste(bom)
						.doubleValue() * commAmount));
				int index = rmList.indexOf(rm);
				if (index > -1) {
					TempRemainMaterialStat tempRm = (TempRemainMaterialStat) rmList
							.get(index);
					tempRm.setRemainMaterialTotalAmount(new Double(tempRm
							.getRemainMaterialTotalAmount().doubleValue()
							+ rm.getRemainMaterialTotalAmount().doubleValue()));
					tempRm.setTotalWasteAmount(new Double(tempRm
							.getTotalWasteAmount().doubleValue()
							+ rm.getTotalWasteAmount().doubleValue()));
				} else {
					rmList.add(rm);
				}
			}
		}
		return rmList;
	}

	/**
	 * 料件进出平衡状况汇总表--------------------进出口日期
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内料件进出状况汇总
	 */
	public List jisuanImgBalanceTotal(Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration, boolean isJisuan) {// isDeclaration
																	// : 申报日期

		List arrayList = new ArrayList();
		List list = ((EncDao) this.getBaseEncDao()).findEmsEdiImg(null);
		List emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		findLjUseNum(timehs, head, beginDate, endDate, isEffect, isDeclaration,
				false, null, null);
		String cancelHeadId = findCancelCusHeaddByDate(beginDate, endDate);
		Map map = commonCodeDao.findCancelImgByHeadId(cancelHeadId);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
			Integer no = img.getSeqNum(); // 料件备案序号
			BillTemp temp = new BillTemp();
			temp.setBill1(String.valueOf(no));// 备案序号
			// ---------------------------------------------------
			temp.setBill2(img.getComplex() == null ? null : img.getComplex()
					.getCode());// 商品编码
			// ---------------------------------------------------
			String namespec = img.getName() + "/" + img.getSpec();
			temp.setBill3(namespec);// 商品名称规格
			// ---------------------------------------------------
			temp.setBill4(img.getUnit() == null ? null : img.getUnit()
					.getName());// 单位
			// ---------------------------------------------------
			temp.setBill5("");// 事业部名称
			// ---------------------------------------------------
			Double averprice = ((EncDao) this.getBaseEncDao())
					.getImgAveragePrice(no, beginDate, endDate, new Boolean(
							true), false, isDeclaration, false, null); // 报关申报平均单价
			temp.setBillSum1(averprice);
			// ---------------------------------------------------
			Double price = CommonUtils.getEmsImgPrice(img);
			temp.setBillSum2(price);// 平均美元 单价
			// ---------------------------------------------------
			Double lj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.DIRECT_IMPORT, beginDate, endDate, isEffect,
					null, no, isDeclaration, false, null);
			Double zj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);
			Double tj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.BACK_MATERIEL_EXPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);

			Double yljk = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.REMAIN_FORWARD_IMPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);
			Double ylck = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.REMAIN_FORWARD_EXPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);
			Double z = lj + zj + yljk - tj - ylck;// 本期进口量(数量) =
			// 料件进口+转厂进口+余料结转（进口）-退料出口－余料转出
			temp.setBillSum3(z); // 本期进口量(数量)
			temp.setBillSum4(lj);// 直接进口
			temp.setBillSum5(zj);// 结转进口
			temp.setBillSum6(yljk);// 余料结转进口
			temp.setBillSum7(ylck);// 余料结转出口
			temp.setBillSum8(tj);// 退料出口
			// ---------------------------------------------------
			Double sljnx = ((EncDao) this.getBaseEncDao())
					.getSumCommAmountForInnerUsed(
							ImpExpType.MATERIAL_DOMESTIC_SALES, beginDate,
							endDate, isEffect, null, no, isDeclaration, false,
							null);
			temp.setBillSum9(sljnx);// 本期料件内销
			// ---------------------------------------------------
			Double ljM = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.DIRECT_IMPORT, beginDate, endDate, isEffect,
					null, no, isDeclaration, false, null);
			Double zjM = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);
			Double tjM = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.BACK_MATERIEL_EXPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);
			Double yljkM = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.REMAIN_FORWARD_IMPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);
			Double ylckM = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.REMAIN_FORWARD_EXPORT, beginDate, endDate,
					isEffect, null, no, isDeclaration, false, null);
			Double zM = ljM + zjM + yljkM - tjM - ylckM;// 本期进口总金额
			temp.setBillSum10(zM);// 总进口量美元金额
			// -------------------------------------------------------------------------
			TempDD x = (TempDD) timehs.get(String.valueOf(no));
			Double ljUse = Double.valueOf(0);
			Double bjlUse = Double.valueOf(0);
			Double zcUse = Double.valueOf(0);
			Double zjckUse = Double.valueOf(0);
			Double fgfcUse = Double.valueOf(0);
			Double tcfcUse = Double.valueOf(0);
			if (x != null) {
				ljUse = x.getAa();
				bjlUse = x.getBb();
				zcUse = x.getZchy();
				zjckUse = x.getZjckhy();
				fgfcUse = x.getFgfchy();
				tcfcUse = x.getTcfghy();
			}
			temp.setBillSum11(ljUse);
			// ---------------------------------
			Double s12 = (temp.getBillSum11() == null ? 0.0 : temp
					.getBillSum11())
					* (temp.getBillSum1() == null ? 0.0 : temp.getBillSum1());
			temp.setBillSum12(s12);
			// ----------------------------------

			temp.setBillSum22(map.get(no) == null ? 0.0 : (Double) map.get(no)); // 期初库存量

			// ---------------------------------
			if (isJisuan) {
				Double s13 = (temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3())
						- (temp.getBillSum11() == null ? 0.0 : temp
								.getBillSum11())
						- (temp.getBillSum9() == null ? 0.0 : temp
								.getBillSum9())
						+ (temp.getBillSum22() == null ? 0.0 : temp
								.getBillSum22());
				temp.setBillSum13(s13);
			} else {
				Double s13 = (temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3())
						- (temp.getBillSum11() == null ? 0.0 : temp
								.getBillSum11())
						- (temp.getBillSum9() == null ? 0.0 : temp
								.getBillSum9());
				temp.setBillSum13(s13);
			}

			// ---------------------------------
			List listAmount = ((EncDao) this.getBaseEncDao()).findSumAmount(no);
			Double ckAmount = getSumCkAmount(listAmount);
			temp.setBillSum14(ckAmount);// 仓库数
			// ---------------------------------
			Double s15 = (temp.getBillSum13() == null ? 0.0 : temp
					.getBillSum13())
					- (temp.getBillSum14() == null ? 0.0 : temp.getBillSum14());
			temp.setBillSum15(s15);
			// ---------------------------------
			Double s16 = (temp.getBillSum3() == null ? 0.0 : temp.getBillSum3())
					- (temp.getBillSum11() == null ? 0.0 : temp.getBillSum11());
			temp.setBillSum16(s16);
			// ---------------------------------
			temp.setBillSum17(bjlUse); //
			temp.setBillSum18(zjckUse); //
			temp.setBillSum19(zcUse);//
			temp.setBillSum20(fgfcUse);//
			temp.setBillSum21(tcfcUse); //
			// --------------------------------------------------------------------------

			arrayList.add(temp);
		}
		return arrayList;
	}

	/**
	 * 料件进出平衡状况汇总表--------------------进出口日期
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内料件进出状况汇总
	 */
	public List<BillTemp> calculateImgBalanceTotal(Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration, boolean isJisuan) {// isDeclaration
																					// :
																					// 申报日期
		List<BillTemp> arrayList = new ArrayList<BillTemp>();

		// 正在执行的电子帐册料件
		List<EmsHeadH2kImg> list = ((EncDao) this.getBaseEncDao())
				.findEmsEdiImg(null);

		List<EmsHeadH2k> emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();

		// 当前正在执行的电子帐册表头
		EmsHeadH2k head = null;
		if (emslist.size() == 0) {
			head = new EmsHeadH2k();
		} else {
			head = emslist.get(0);
		}

		// 计算料件耗用
		calculateMaterialUseNum(timehs, head, beginDate, endDate, isEffect,
				isDeclaration, false, null, null);

		// 查找核销表头id
		String cancelHeadId = findCancelCusHeaddByDate(beginDate, endDate);

		// 查找结余数量
		Map map = commonCodeDao.findCancelImgByHeadId(cancelHeadId);

		// 获得期初金额
		Map mapBeginMoney = commonCodeDao
				.findCancelImgByHeadIdMoney(cancelHeadId);

		// 获得期初数量
		Map mapBeginCmmAmount = commonCodeDao
				.findCancelImgByHeadIdCmmAmount(cancelHeadId);

		// 料件进出口平均单价
		Map<Integer, Double> priceMap = new HashMap<Integer, Double>();
		// 料件进出口总价
		Map<String, Double> moneyMap = new HashMap<String, Double>();
		// 查询料件的进口金额，
		Map<String, Double> amountMap = new HashMap<String, Double>();
		// 仓库数量
		Map<Integer, Double> ckAmountMap = new HashMap<Integer, Double>();

		countMaterialImport(priceMap, moneyMap, amountMap, ckAmountMap,
				beginDate, endDate, isEffect, isDeclaration, false, null,
				mapBeginMoney, mapBeginCmmAmount);

		EmsHeadH2kImg img = null;
		Integer seqNum = null; // 料件备案序号
		BillTemp temp = null;
		Double usaAvgPrice = null;
		Double price = null;
		String namespec = null;

		for (int i = 0; i < list.size(); i++) {
			img = list.get(i);
			seqNum = img.getSeqNum(); // 料件备案序号
			temp = new BillTemp();

			temp.setBill1(String.valueOf(seqNum));// 备案序号
			temp.setBill2(img.getComplex() == null ? null : img.getComplex()
					.getCode());// 商品编码

			namespec = img.getName() + "/" + img.getSpec();
			temp.setBill3(namespec);// 商品名称规格

			temp.setBill4(img.getUnit() == null ? null : img.getUnit()
					.getName());// 单位

			temp.setBill5("");// 事业部名称

			usaAvgPrice = CommonUtils.getDoubleExceptNull(priceMap.get(seqNum)); // 平均美元
																					// 单价

			temp.setBillSum1(usaAvgPrice);

			price = CommonUtils.getEmsImgPrice(img);
			temp.setBillSum2(price);// 报关申报平均单价

			// 料件进口
			Double lj = CommonUtils.getDoubleExceptNull(amountMap.get(seqNum
					+ "/" + ImpExpType.DIRECT_IMPORT));
			// 转厂进口
			Double zj = CommonUtils.getDoubleExceptNull(amountMap.get(seqNum
					+ "/" + ImpExpType.TRANSFER_FACTORY_IMPORT));
			// 退料出口
			Double tj = CommonUtils.getDoubleExceptNull(amountMap.get(seqNum
					+ "/" + ImpExpType.BACK_MATERIEL_EXPORT));
			// 余料结转
			Double yljk = CommonUtils.getDoubleExceptNull(amountMap.get(seqNum
					+ "/" + ImpExpType.REMAIN_FORWARD_IMPORT));
			// 余料转出
			Double ylck = CommonUtils.getDoubleExceptNull(amountMap.get(seqNum
					+ "/" + ImpExpType.REMAIN_FORWARD_EXPORT));
			Double z = lj + zj + yljk - tj - ylck;// 本期进口量(数量) =
			// 料件进口+转厂进口+余料结转（进口）-退料出口－余料转出
			temp.setBillSum3(z); // 本期进口量(数量)
			temp.setBillSum4(lj);// 直接进口
			temp.setBillSum5(zj);// 结转进口
			temp.setBillSum6(yljk);// 余料结转进口
			temp.setBillSum7(ylck);// 余料结转出口
			temp.setBillSum8(tj);// 退料出口
			Double sljnx = CommonUtils.getDoubleExceptNull(amountMap.get(seqNum
					+ "/" + ImpExpType.MATERIAL_DOMESTIC_SALES));

			temp.setBillSum9(sljnx);// 本期料件内销

			Double ljM = CommonUtils.getDoubleExceptNull(moneyMap.get(seqNum
					+ "/" + ImpExpType.DIRECT_IMPORT));// 料件进口
			Double zjM = CommonUtils.getDoubleExceptNull(moneyMap.get(seqNum
					+ "/" + ImpExpType.TRANSFER_FACTORY_IMPORT));
			Double tjM = CommonUtils.getDoubleExceptNull(moneyMap.get(seqNum
					+ "/" + ImpExpType.BACK_MATERIEL_EXPORT));
			Double yljkM = CommonUtils.getDoubleExceptNull(moneyMap.get(seqNum
					+ "/" + ImpExpType.REMAIN_FORWARD_IMPORT));
			Double ylckM = CommonUtils.getDoubleExceptNull(moneyMap.get(seqNum
					+ "/" + ImpExpType.REMAIN_FORWARD_EXPORT));
			Double zM = ljM + zjM + yljkM - tjM - ylckM;// 本期进口总金额
			temp.setBillSum10(zM);// 总进口量美元金额

			TempDD x = timehs.get(String.valueOf(seqNum));
			Double ljUse = Double.valueOf(0);
			Double bjlUse = Double.valueOf(0);
			Double zcUse = Double.valueOf(0);
			Double zjckUse = Double.valueOf(0);
			Double fgfcUse = Double.valueOf(0);
			Double tcfcUse = Double.valueOf(0);
			if (x != null) {
				ljUse = x.getAa();
				bjlUse = x.getBb();
				zcUse = x.getZchy();
				zjckUse = x.getZjckhy();
				fgfcUse = x.getFgfchy();
				tcfcUse = x.getTcfghy();
			}
			temp.setBillSum11(ljUse);

			Double s12 = (temp.getBillSum11() == null ? 0.0 : temp
					.getBillSum11()) * usaAvgPrice;
			temp.setBillSum12(s12);

			double cmmAmount = map.get(seqNum) == null ? 0.0 : (Double) map
					.get(seqNum);
			temp.setBillSum22(cmmAmount); // 期初库存量

			if (isJisuan) {
				Double s13 = (temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3())
						- (temp.getBillSum11() == null ? 0.0 : temp
								.getBillSum11())
						- (temp.getBillSum9() == null ? 0.0 : temp
								.getBillSum9())
						+ (temp.getBillSum22() == null ? 0.0 : temp
								.getBillSum22());
				temp.setBillSum13(s13);
			} else {
				Double s13 = (temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3())
						- (temp.getBillSum11() == null ? 0.0 : temp
								.getBillSum11())
						- (temp.getBillSum9() == null ? 0.0 : temp
								.getBillSum9());
				temp.setBillSum13(s13);
			}
			Double ckAmount = ckAmountMap.get(seqNum);
			temp.setBillSum14(ckAmount);// 仓库数

			Double s15 = (temp.getBillSum13() == null ? 0.0 : temp
					.getBillSum13())
					- (temp.getBillSum14() == null ? 0.0 : temp.getBillSum14());
			temp.setBillSum15(s15);

			Double s16 = (temp.getBillSum3() == null ? 0.0 : temp.getBillSum3())
					- (temp.getBillSum11() == null ? 0.0 : temp.getBillSum11());
			temp.setBillSum16(s16);

			temp.setBillSum17(bjlUse); //
			temp.setBillSum18(zjckUse); //
			temp.setBillSum19(zcUse);//
			temp.setBillSum20(fgfcUse);//
			temp.setBillSum21(tcfcUse); //
			temp.setBillSum23(usaAvgPrice * sljnx);// 内销金额 = 平均美元单价*本期料件内销
			temp.setBillSum24(usaAvgPrice * cmmAmount);// 期初金额 = 平均美元 单价*期初库存量
			temp.setBillSum25(usaAvgPrice * cmmAmount + zM - s12 - usaAvgPrice
					* sljnx);// 结余金额 = 期初金额+总进口金额-耗用金额-内销金额
			arrayList.add(temp);
		}

		return arrayList;
	}

	/**
	 * 查询料件的进出口金额，平均单价，总价，仓库数量
	 * 
	 * @param priceMap
	 * @param moneyMap
	 * @param amountMap
	 * @param ckAmountMap
	 * @param beginDate
	 * @param endDate
	 * @param isEffect
	 * @param isDeclaration
	 * @param isDept
	 * @param deptCode
	 */
	private void countMaterialImport(Map<Integer, Double> priceMap,
			Map<String, Double> moneyMap, Map<String, Double> amountMap,
			Map<Integer, Double> ckAmountMap, Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration, boolean isDept,
			String deptCode, Map<Integer, Double> mapBeginMoney,
			Map<Integer, Double> mapBeginCmmAmount) {

		List<Object[]> list = encDao.countSeqNumImport(beginDate, endDate,
				isEffect, isDeclaration, isDept, deptCode, false);
		Object[] record = null;
		String key = "";
		for (int i = 0; i < list.size(); i++) {
			record = list.get(i);
			key = record[0] + "/" + record[1];
			// 数量
			amountMap.put(key, (Double) record[2]);
			// 金额
			moneyMap.put(key, (Double) record[3]);
		}

		list = encDao.countSeqNumImport(beginDate, endDate, isEffect,
				isDeclaration, isDept, deptCode, true);
		// ///////////
		// 获取数量 总价
		List<Object[]> listPriceAmount = encDao.findPriceAndAmount(beginDate,
				endDate, isEffect, isDeclaration, isDept, deptCode);

		// 获取平均单价 总价/数量
		for (int i = 0; i < listPriceAmount.size(); i++) {
			Object[] object = listPriceAmount.get(i);
			// 商品序号
			int commSerialNo = Integer.parseInt(object[0].toString());
			double price = Double.parseDouble(object[1].toString());
			double amount = Double.parseDouble(object[2].toString());
			if (!(mapBeginCmmAmount.get(commSerialNo) == null)) {
				// 加上期初数量
				amount += mapBeginCmmAmount.get(commSerialNo);
			}
			if (!(mapBeginMoney.get(commSerialNo) == null)) {
				// 加上期初金额
				price += mapBeginMoney.get(commSerialNo);
			}
			// 保存料件进出口平均单价
			priceMap.put(commSerialNo, price / amount);
		}
		// ///////////////

		record = null;
		// for (int i = 0; i < list.size(); i++) {
		// record = list.get(i);
		// // 单价
		// priceMap.put((Integer) record[0], (Double) record[1]);
		// }

		list = encDao.findSumAmount();
		record = null;
		for (int i = 0; i < list.size(); i++) {
			record = list.get(i);
			// 仓库数量
			ckAmountMap.put((Integer) record[0], (Double) record[1]);
		}

	}

	// 分事业部统计
	public List jisuanImgBalanceTotalForDept(Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration, List<String> deptCode) {// isDeclaration
		// :
		// 申报日期
		List arrayList = new ArrayList();
		List list = ((EncDao) this.getBaseEncDao()).findEmsEdiImg(null);
		List deptList = ((EncDao) this.getBaseEncDao())
				.findAllDeptMore(deptCode);// 获取所有事业部
		List emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		int size = deptList.size() + 1;
		String cancelHeadId = findCancelCusHeaddByDate(beginDate, endDate);
		System.out.println(cancelHeadId);
		Map map = commonCodeDao.findCancelImgByHeadId(cancelHeadId);

		for (int k = 0; k < size; k++) {// 循环事业部
			String projectDeptCode = null;
			String projectDeptName = null;
			if (k == (size - 1) && deptCode == null) {
				projectDeptCode = null;
				projectDeptName = null;
			} else if (k == (size - 1)) {
				continue;
			} else {
				ProjectDept pobj = (ProjectDept) deptList.get(k);
				if (pobj.getCode() == null || pobj.getCode().equals("")) {
					projectDeptCode = null;
					projectDeptName = null;
				} else {
					projectDeptCode = pobj.getCode();// 事业部代码
					projectDeptName = pobj.getName();

				}
			}
			findLjUseNum(timehs, head, beginDate, endDate, isEffect,
					isDeclaration, true, projectDeptCode, null);
			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
				Integer no = img.getSeqNum(); // 料件备案序号
				BillTemp temp = new BillTemp();
				temp.setBill1(String.valueOf(no));// 备案序号
				// ---------------------------------------------------
				temp.setBill2(img.getComplex() == null ? null : img
						.getComplex().getCode());// 商品编码
				// ---------------------------------------------------
				String namespec = img.getName() + "/" + img.getSpec();
				temp.setBill3(namespec);// 商品名称规格
				// ---------------------------------------------------
				temp.setBill4(img.getUnit() == null ? null : img.getUnit()
						.getName());// 单位
				// ---------------------------------------------------
				temp.setBill5(projectDeptName);// 事业部名称
				System.out.println("hcl 事业部：" + temp.getBill5());
				// ---------------------------------------------------
				Double averprice = ((EncDao) this.getBaseEncDao())
						.getImgAveragePrice(no, beginDate, endDate,
								new Boolean(true), false, isDeclaration, true,
								projectDeptCode); // 报关申报平均单价
				temp.setBillSum1(averprice);
				// ---------------------------------------------------
				Double price = CommonUtils.getEmsImgPrice(img);
				temp.setBillSum2(price);// 平均美元 单价
				// ---------------------------------------------------
				Double lj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.DIRECT_IMPORT, beginDate, endDate, isEffect,
						null, no, isDeclaration, true, projectDeptCode);
				Double zj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double tj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.BACK_MATERIEL_EXPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);

				Double yljk = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.REMAIN_FORWARD_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double ylck = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.REMAIN_FORWARD_EXPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double z = lj + zj + yljk - tj - ylck;// 本期进口量(数量) =
				// 料件进口+转厂进口+余料结转（进口）-退料出口－余料转出
				temp.setBillSum3(z); // 本期进口量(数量)
				temp.setBillSum4(lj);// 直接进口
				temp.setBillSum5(zj);// 结转进口
				temp.setBillSum6(yljk);// 余料结转进口
				temp.setBillSum7(ylck);// 余料结转出口
				temp.setBillSum8(tj);// 退料出口
				// ---------------------------------------------------
				Double sljnx = ((EncDao) this.getBaseEncDao())
						.getSumCommAmountForInnerUsed(
								ImpExpType.MATERIAL_DOMESTIC_SALES, beginDate,
								endDate, isEffect, null, no, isDeclaration,
								true, projectDeptCode);
				temp.setBillSum9(sljnx);// 料件内销
				// ---------------------------------------------------
				Double ljM = ((EncDao) this.getBaseEncDao()).getSumMoney(
						ImpExpType.DIRECT_IMPORT, beginDate, endDate, isEffect,
						null, no, isDeclaration, true, projectDeptCode);
				Double zjM = ((EncDao) this.getBaseEncDao()).getSumMoney(
						ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double tjM = ((EncDao) this.getBaseEncDao()).getSumMoney(
						ImpExpType.BACK_MATERIEL_EXPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double yljkM = ((EncDao) this.getBaseEncDao()).getSumMoney(
						ImpExpType.REMAIN_FORWARD_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double ylckM = ((EncDao) this.getBaseEncDao()).getSumMoney(
						ImpExpType.REMAIN_FORWARD_EXPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double zM = ljM + zjM + yljkM - tjM - ylckM;// 本期进口总金额
				temp.setBillSum10(zM);// 总进口量美元金额
				// -------------------------------------------------------------------------
				TempDD x = (TempDD) timehs.get(String.valueOf(no));
				Double ljUse = Double.valueOf(0);
				Double bjlUse = Double.valueOf(0);
				Double zcUse = Double.valueOf(0);
				Double zjckUse = Double.valueOf(0);
				Double fgfcUse = Double.valueOf(0);
				Double tcfcUse = Double.valueOf(0);
				if (x != null) {
					ljUse = x.getAa();
					bjlUse = x.getBb();
					zcUse = x.getZchy();
					zjckUse = x.getZjckhy();
					fgfcUse = x.getFgfchy();
					tcfcUse = x.getTcfghy();
				}
				temp.setBillSum11(ljUse);
				// ---------------------------------
				Double s12 = (temp.getBillSum11() == null ? 0.0 : temp
						.getBillSum11())
						* (temp.getBillSum1() == null ? 0.0 : temp
								.getBillSum1());
				temp.setBillSum12(s12);
				// ---------------------------------
				Double s13 = (temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3())
						- (temp.getBillSum11() == null ? 0.0 : temp
								.getBillSum11())
						- (temp.getBillSum9() == null ? 0.0 : temp
								.getBillSum9());
				temp.setBillSum13(s13);
				// ---------------------------------
				List listAmount = ((EncDao) this.getBaseEncDao())
						.findSumAmount(no);
				Double ckAmount = getSumCkAmount(listAmount);
				temp.setBillSum14(ckAmount);// 仓库数
				// ---------------------------------
				Double s15 = (temp.getBillSum13() == null ? 0.0 : temp
						.getBillSum13())
						- (temp.getBillSum14() == null ? 0.0 : temp
								.getBillSum14());
				temp.setBillSum15(s15);
				// ---------------------------------
				Double s16 = (temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3())
						- (temp.getBillSum11() == null ? 0.0 : temp
								.getBillSum11());
				temp.setBillSum16(s16);
				// ---------------------------------
				temp.setBillSum17(bjlUse); //
				temp.setBillSum18(zjckUse); //
				temp.setBillSum19(zcUse);//
				temp.setBillSum20(fgfcUse);//
				temp.setBillSum21(tcfcUse); //
				// --------------------------------------------------------------------------
				temp.setBillSum22(map.get(no) == null ? 0.0 : (Double) map
						.get(no)); // 期初库存量

				arrayList.add(temp);
			}
		}
		return arrayList;
	}

	/**
	 * 帐册出口成品状况表--------------------申报日期
	 * 
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 指定的日期内帐册出口成品状况
	 */
	public List jisuanEmsExpExg(Date beginDate, Date endDate, Boolean isEffect) {
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfoOther(
				null, null, null, beginDate, endDate, null, null, true,
				isEffect, null, null, null, null, false, null); // 选择所有报关单表体
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsEdiTrDao
				.findEmsHeadH2kInExecuting().get(0);
		Hashtable ht = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			String no = String.valueOf(info.getCommSerialNo());// 备案序号
			String complex = info.getComplex() == null ? null : info
					.getComplex().getCode();// 商品编码
			String emsNo = emsHeadH2k.getEmsNo();// 帐册号
			String namespec = info.getCommName();// 商品名称
			String unit = info.getUnit() == null ? null : info.getUnit()
					.getName();// 单位
			String version = info.getVersion();// 版本号
			Integer versionIn = (version == null || "".equals(version)) ? 0
					: Integer.valueOf(version);
			Integer keys = formatNum(no, versionIn);
			if (ht.get(keys) != null) { // 查到
				temp = (BillTemp) ht.get(keys);
			} else {
				temp = new BillTemp();
				temp.setBill1(emsNo);
				temp.setBill2(complex);
				temp.setBill3(namespec);
				temp.setBill4(no);
				temp.setBill5(version);
				temp.setBill6(unit);
				ht.put(keys, temp);

				EmsHeadH2kVersion versionObj = ((EncDao) this.getBaseEncDao())
						.findEmsHeadH2kVersion(Integer.valueOf(no), versionIn);
				if (versionObj == null) {
					continue;
				}
				Double price = CommonUtils.getEmsExgPrice(versionObj);
				temp.setBillSum1(price);
				Double unitWeight = (versionObj == null) ? Double.valueOf(0)
						: versionObj.getUnitNetWeight();
				temp.setBillSum2(unitWeight);// 合同签定单重
				Double grossWeight = versionObj == null ? Double.valueOf(0)
						: versionObj.getUnitGrossWeight();
				temp.setBillSum3(grossWeight);// 合同签定毛重
				if (unitWeight != null) {
					Double weightRate = (Double.valueOf(0)).equals(unitWeight) ? Double
							.valueOf(0) : (grossWeight / unitWeight);
					temp.setBillSum4(weightRate);// 合同签定比例
				}
			}
			// 总出口 = 成出 + 转长 +返工副出 - 退厂返工
			if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.DIRECT_EXPORT)
					|| info.getBaseCustomsDeclaration().getImpExpType()
							.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)
					|| info.getBaseCustomsDeclaration().getImpExpType()
							.equals(ImpExpType.REWORK_EXPORT)) { // 成出 + 转长
																	// +返工副出
				temp.setBillSum5(fd(temp.getBillSum5())
						+ fd(info.getCommAmount()));
				temp.setBillSum8(fd(temp.getBillSum8())
						+ fd(info.getNetWeight()));// 实际报关出口净重(根据类型计算)
				temp.setBillSum11(fd(temp.getBillSum11())
						+ fd(info.getGrossWeight())); // 实际也要根据类型
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.BACK_FACTORY_REWORK)) {// 退厂返工
				temp.setBillSum5(fd(temp.getBillSum5())
						- fd(info.getCommAmount()));
				temp.setBillSum8(fd(temp.getBillSum8())
						- fd(info.getNetWeight()));// 实际报关出口净重(根据类型计算)
				temp.setBillSum11(fd(temp.getBillSum11())
						- fd(info.getGrossWeight())); // 实际也要根据类型
			}
			if (unit != null && (unit.equals("千克") || unit.equals("公斤"))) {
				temp.setBillSum7(temp.getBillSum5()); // 帐册应出口净重
				temp.setBillSum10(fd(temp.getBillSum5())// 帐册应出口毛重 =
						// 賬冊應出口凈重*合同簽訂比率
						* fd(temp.getBillSum4()));
			} else if (unit != null && (unit.equals("个") || unit.equals("辆"))) {
				temp.setBillSum7(fd(temp.getBillSum5())
						* fd(temp.getBillSum2()));
				temp.setBillSum10(fd(temp.getBillSum5())// 賬冊出口數量*合同簽訂毛重
						* fd(temp.getBillSum3()));
			}
			Integer piece = info.getPieces() == null ? 0 : info.getPieces();
			Integer piece0 = Integer.valueOf(temp.getBill7() == null ? "0"
					: temp.getBill7()) + piece;
			temp.setBill7(String.valueOf(piece0));// 帐册累计已出口件数
			temp.setBillSum9(fd(temp.getBillSum7()) - fd(temp.getBillSum8())); // 帐册净重-实际出口净重差异
			temp.setBillSum12(fd(temp.getBillSum10()) - fd(temp.getBillSum11()));
		}
		// ----排序-------------------------------------
		ArrayList returnList = new ArrayList();
		TreeMap ts = new TreeMap();
		Enumeration e = ht.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			ts.put(new Integer(String.valueOf(key)), key);
		}
		Set st = ts.keySet();
		for (Iterator i = st.iterator(); i.hasNext();) {
			returnList.add(ht.get(ts.get(i.next())));
		}
		return returnList;
	}

	// ----分事业部处理
	public List jisuanEmsExpExgForDept(Date beginDate, Date endDate,
			Boolean isEffect, String projectDept) {
		boolean isdept = true;
		if (projectDept == null) {
			isdept = false;
		} else {
			isdept = true;
		}
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfoOther(
				null, null, null, beginDate, endDate, null, null, true,
				isEffect, null, null, null, null, isdept, projectDept); // 选择所有报关单表体
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsEdiTrDao
				.findEmsHeadH2kInExecuting().get(0);
		Hashtable ht = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			String no = String.valueOf(info.getCommSerialNo());// 备案序号
			String complex = info.getComplex() == null ? null : info
					.getComplex().getCode();// 商品编码
			String emsNo = emsHeadH2k.getEmsNo();// 帐册号
			String namespec = info.getCommName();// 商品名称
			String unit = info.getUnit() == null ? null : info.getUnit()
					.getName();// 单位
			String deptCode = info.getProjectDept() == null ? "" : info
					.getProjectDept().getCode();// 事业部代码
			String deptName = info.getProjectDept() == null ? "" : info
					.getProjectDept().getName();// 事业部代码
			String version = info.getVersion() == null ? "0" : info
					.getVersion();// 版本号

			String keys = no + "/" + version + "/" + deptCode;
			if (ht.get(keys) != null) { // 查到
				temp = (BillTemp) ht.get(keys);
			} else {
				temp = new BillTemp();
				temp.setBill1(emsNo);
				temp.setBill2(complex);
				temp.setBill3(namespec);
				temp.setBill4(no);
				temp.setBill5(version);
				temp.setBill6(unit);
				temp.setBill8(deptName);
				ht.put(keys, temp);

				EmsHeadH2kVersion versionObj = ((EncDao) this.getBaseEncDao())
						.findEmsHeadH2kVersion(Integer.valueOf(no),
								Integer.valueOf(version));
				if (versionObj == null) {
					continue;
				}
				Double price = CommonUtils.getEmsExgPrice(versionObj);
				temp.setBillSum1(price);
				Double unitWeight = (versionObj == null) ? Double.valueOf(0)
						: versionObj.getUnitNetWeight();
				temp.setBillSum2(unitWeight);// 合同签定单重
				Double grossWeight = versionObj == null ? Double.valueOf(0)
						: versionObj.getUnitGrossWeight();
				temp.setBillSum3(grossWeight);// 合同签定毛重
				if (unitWeight != null) {
					Double weightRate = (Double.valueOf(0)).equals(unitWeight) ? Double
							.valueOf(0) : (grossWeight / unitWeight);
					temp.setBillSum4(weightRate);// 合同签定比例
				}
			}
			// 总出口 = 成出 + 转长 +返工副出 - 退厂返工
			if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.DIRECT_EXPORT)
					|| info.getBaseCustomsDeclaration().getImpExpType()
							.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)
					|| info.getBaseCustomsDeclaration().getImpExpType()
							.equals(ImpExpType.REWORK_EXPORT)) { // 成出 + 转长
																	// +返工副出
				temp.setBillSum5(fd(temp.getBillSum5())
						+ fd(info.getCommAmount()));
				temp.setBillSum8(fd(temp.getBillSum8())
						+ fd(info.getNetWeight()));// 实际报关出口净重(根据类型计算)
				temp.setBillSum11(fd(temp.getBillSum11())
						+ fd(info.getGrossWeight())); // 实际也要根据类型
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.BACK_FACTORY_REWORK)) {// 退厂返工
				temp.setBillSum5(fd(temp.getBillSum5())
						- fd(info.getCommAmount()));
				temp.setBillSum8(fd(temp.getBillSum8())
						- fd(info.getNetWeight()));// 实际报关出口净重(根据类型计算)
				temp.setBillSum11(fd(temp.getBillSum11())
						- fd(info.getGrossWeight())); // 实际也要根据类型
			}
			if (unit != null && (unit.equals("千克") || unit.equals("公斤"))) {
				temp.setBillSum7(temp.getBillSum5()); // 帐册应出口净重
				temp.setBillSum10(fd(temp.getBillSum5())// 帐册应出口毛重 =
						// 賬冊應出口凈重*合同簽訂比率
						* fd(temp.getBillSum4()));
			} else if (unit != null && (unit.equals("个") || unit.equals("辆"))) {
				temp.setBillSum7(fd(temp.getBillSum5())
						* fd(temp.getBillSum2()));
				temp.setBillSum10(fd(temp.getBillSum5())// 賬冊出口數量*合同簽訂毛重
						* fd(temp.getBillSum3()));
			}
			Integer piece = info.getPieces() == null ? 0 : info.getPieces();
			Integer piece0 = Integer.valueOf(temp.getBill7() == null ? "0"
					: temp.getBill7()) + piece;
			temp.setBill7(String.valueOf(piece0));// 帐册累计已出口件数
			temp.setBillSum9(fd(temp.getBillSum7()) - fd(temp.getBillSum8())); // 帐册净重-实际出口净重差异
			temp.setBillSum12(fd(temp.getBillSum10()) - fd(temp.getBillSum11()));
		}
		/*
		 * // ----排序------------------------------------- ArrayList returnList =
		 * new ArrayList(); TreeMap ts = new TreeMap(); Enumeration e =
		 * ht.keys(); while (e.hasMoreElements()) { Object key =
		 * e.nextElement(); ts.put(new Integer(String.valueOf(key)), key); } Set
		 * st = ts.keySet(); for (Iterator i = st.iterator(); i.hasNext();) {
		 * returnList.add(ht.get(ts.get(i.next()))); }
		 */
		return new Vector(ht.values());
	}

	private Integer formatNum(String cp, int version) {
		if (version < 10) {
			return Integer.valueOf(cp + "0" + String.valueOf(version));
		} else if (version >= 10 && version < 100) {
			return Integer.valueOf(cp + String.valueOf(version));
		} else {
			return Integer.valueOf(cp + String.valueOf(version));
		}
	}

	/**
	 * 把日期转换为字符型
	 * 
	 * @param date
	 *            日期
	 * @return 字符型的日期
	 */
	public String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(date);
	}

	/**
	 * 精确的日期
	 * 
	 * @param date1
	 *            日期
	 * @return 精确的日期
	 */
	public String dateToDate(Date date1) {
		if (date1 == null) {
			return null;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str1 = formater.format(date1);
		return str1;
	}

	/**
	 * 取得进出口报关单商品明细的金额运费与保费的美元总价
	 * 
	 * @return
	 */
	public Double getTotalPrices(List list,
			CustomsDeclaration customsDeclaration) {
		double totalprice = 0;
		Double prices = 0.0;
		if (list == null || list.size() <= 0) {
			return totalprice;
		} else {
			for (int i = 0; i < list.size(); i++) {
				CustomsDeclarationCommInfo commInfo = (CustomsDeclarationCommInfo) list
						.get(i);
				if (commInfo.getCommTotalPrice() != null) {
					BigDecimal b = new BigDecimal(commInfo.getCommTotalPrice()
							.doubleValue());
					Double prices1 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					prices = prices + prices1;
					BigDecimal c = new BigDecimal(prices.doubleValue());
					prices = c.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
			}
			prices = prices
					+ CalcCIF("1", String.valueOf(customsDeclaration
							.getFreightageType()), customsDeclaration
							.getTransac().getName(),
							customsDeclaration.getFreightage(),
							customsDeclaration.getGrossWeight(), prices);
			prices = prices
					+ CalcCIF("3", String.valueOf(customsDeclaration
							.getInsuranceType()), customsDeclaration
							.getTransac().getName(),
							customsDeclaration.getInsurance(),
							customsDeclaration.getGrossWeight(), prices);
			Double rate = customsDeclaration.getExchangeRate();
			if (rate != null && rate > 0.0) {
				totalprice = totalprice + (prices * rate);
			} else {
				totalprice = totalprice + getpices(prices);
			}
			return getpices(totalprice);
		}
	}

	public Double getpices(Double prices) {
		String s = ".";
		String str = String.valueOf(prices);
		Double xx = 0.0;
		if (str.indexOf(s) > 0) {
			Integer h = str.indexOf(s);
			String bb = str.substring(h + 1, str.length());
			if (bb.length() > 2) {
				xx = Double.parseDouble(str.substring(0, h) + "."
						+ bb.substring(0, 2));
			} else {
				xx = prices;
			}
		}
		return xx;

	}

	public Double CalcCIF(String CalcType, String DataType, String CJFS,
			Double FY, Double ZL, Double ZJE) {
		/*
		 * Arguments: CalcType：可选1和2，1指运费，2指保费 DataType：指运费或保费它的子项 CJFS : 成交方式
		 * FY : 费用/费率 ZL : 重量 ZJE ：报关单商品总金额 Result: 计算后的金额
		 */
		if (FY == null) {
			FY = new Double(0);
		}
		if (ZL == null) {
			ZL = new Double(0);
		}
		if (ZJE == null) {
			ZJE = new Double(0);
		}
		if (DataType.equals("")) {
			return new Double(0);
		}
		if (CalcType.equals("1")) {
			if (DataType.equals("1")) {
				return (FY / 100) * ZJE;
			} else if (DataType.equals("2")) {
				return (ZL / 1000) * FY;
			} else if (DataType.equals("3")) {
				return FY;
			}
		} else {
			if (DataType.equals("1")) {
				if (CJFS.equals("C&F")) {
					return ZJE / (1 - FY / 100) - ZJE; // (FOB+运费) / (1-保险费率)
				} else {
					return ZJE / (1 - FY / 100) - ZJE;// 算法改为同上。Jack/2004-9-7//(FY
					// / 100) * ZJE;
				}
			} else if (DataType.equals("3")) {
				return FY;
			}
		}
		return new Double(0);
	}

	/**
	 * 出口报关清单
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内出口报关清单的情况
	 */
	public List jisuanExpCustomsBill(Date beginDate, Date endDate) {
		List list = ((EncDao) this.getBaseEncDao())
				.findImpExpCustomsDeclaration(beginDate, endDate,
						ImpExpFlag.EXPORT);

		System.out.println("--------------------:" + list.size());
		Double netweight = Double.valueOf(0);
		Double gossweight = Double.valueOf(0);
		Double money = Double.valueOf(0);
		Integer fs = 0;
		List tempList = new ArrayList();
		BillTemp temp = null;
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclaration obj = (CustomsDeclaration) list.get(i);
			temp = new BillTemp();
			temp.setBill1(String.valueOf(obj.getSerialNumber()));
			temp.setBill2(dateToString(obj.getDeclarationDate()));
			temp.setBill3(obj.getCustomsDeclarationCode());
			List listCominfo = ((EncDao) this.getBaseEncDao())
					.getTotalMoney(obj);
			temp.setBillSum1(getTotalPrices(listCominfo, obj));
			temp.setBill4(String.valueOf(obj.getCommodityNum()));
			temp.setBill5(String.valueOf(obj.getGrossWeight()));
			temp.setBill6(String.valueOf(obj.getNetWeight()));
			netweight = netweight + fd(obj.getNetWeight());
			gossweight = gossweight + fd(obj.getGrossWeight());
			money = money + fd(temp.getBillSum1());
			Integer x = ((EncDao) this.getBaseEncDao()).getTotalNum(obj);
			Integer y = x / 5;
			if (x > y * 5) {
				temp.setBill7(String.valueOf(y + 1));
			} else {
				temp.setBill7(String.valueOf(y));
			}
			fs = fs + Integer.valueOf(temp.getBill7());
			temp.setBill8(obj.getTradeMode() == null ? "" : obj.getTradeMode()
					.getName());
			temp.setBill9("成品");
			temp.setBill10(obj.getCustomer() == null ? "" : obj.getCustomer()
					.getName());
			tempList.add(temp);
		}
		BillTemp temp1 = new BillTemp();
		temp1.setBill5(String.valueOf(gossweight));
		temp1.setBill6(String.valueOf(netweight));
		temp1.setBillSum1(money);
		temp1.setBill7(String.valueOf(fs));
		tempList.add(temp1);
		return tempList;
	}

	/**
	 * 进口报关清单
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内进口报关清单的情况
	 */
	public List jisuanImpCustomsBill(Date beginDate, Date endDate) {
		List list = ((EncDao) this.getBaseEncDao())
				.findImpExpCustomsDeclaration(beginDate, endDate,
						ImpExpFlag.IMPORT);
		Double netweight = Double.valueOf(0);
		Double gossweight = Double.valueOf(0);
		Double money = Double.valueOf(0);
		Integer fs = 0;
		Hashtable<String, BillTemp> hs = new Hashtable<String, BillTemp>();
		Hashtable<String, BillTemp> hstrade = new Hashtable<String, BillTemp>();
		List<BillTemp> tempList = new ArrayList<BillTemp>();
		BillTemp temp = null;
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclaration obj = (CustomsDeclaration) list.get(i);
			temp = new BillTemp();
			temp.setBill1(String.valueOf(obj.getSerialNumber()));
			temp.setBill2(dateToString(obj.getDeclarationDate()));
			temp.setBill3(obj.getCustomsDeclarationCode());
			List listCominfo = ((EncDao) this.getBaseEncDao())
					.getTotalMoney(obj);

			temp.setBillSum1(getTotalPrices(listCominfo, obj));
			temp.setBill4(String.valueOf(obj.getCommodityNum() == null ? 0
					: obj.getCommodityNum()));
			temp.setBill5(String.valueOf(obj.getGrossWeight()));
			temp.setBill6(String.valueOf(obj.getNetWeight()));
			netweight = netweight + fd(obj.getNetWeight());
			gossweight = gossweight + fd(obj.getGrossWeight());
			money = money + fd(temp.getBillSum1());
			Integer x = ((EncDao) this.getBaseEncDao()).getTotalNum(obj);
			Integer y = x / 5;
			if (x > y * 5) {
				temp.setBill7(String.valueOf(y + 1));
			} else {
				temp.setBill7(String.valueOf(y));
			}
			fs = fs + Integer.valueOf(temp.getBill7());
			temp.setBill8(obj.getWrapType() == null ? "" : obj.getWrapType()
					.getName());
			temp.setBill9(obj.getTradeMode() == null ? "" : obj.getTradeMode()
					.getName());
			temp.setBill10(obj.getCustomer() == null ? "" : obj.getCustomer()
					.getName());
			tempList.add(temp);

			String key = obj.getWrapType() == null ? "" : obj.getWrapType()
					.getName();
			BillTemp xobj = null;
			if (hs.get(key) != null) {
				xobj = (BillTemp) hs.get(key);
			} else {
				xobj = new BillTemp();
				xobj.setBill1(key);
				hs.put(key, xobj);
			}
			xobj.setBillSum1(fd(xobj.getBillSum1()) + fd(obj.getGrossWeight())
					- fd(obj.getNetWeight()));

			String key1 = obj.getTradeMode() == null ? "" : obj.getTradeMode()
					.getName(); // 贸易方式
			BillTemp tobj = null;
			if (hstrade.get(key1) == null) {
				tobj = new BillTemp();
				tobj.setBill1(key1);
				hstrade.put(key1, tobj);
			}
		}
		// --------------------------------------------
		tempList.add(new BillTemp());
		// --------------------------------------------
		BillTemp temp1 = new BillTemp();
		temp1.setBill1("总计：");
		temp1.setBill5(String.valueOf(gossweight));
		temp1.setBill6(String.valueOf(netweight));
		temp1.setBillSum1(money);
		temp1.setBill7(String.valueOf(fs));
		tempList.add(temp1);

		// 计算贸易方式
		List trList = new Vector(hstrade.values());
		for (int j = 0; j < trList.size(); j++) {
			BillTemp temp5 = new BillTemp();
			BillTemp tobj = (BillTemp) trList.get(j);
			if (j == 0) {
				temp5.setBill1("贸易方式:");
			}
			temp5.setBill2(tobj.getBill1());
			Double mymoney = ((EncDao) this.getBaseEncDao())
					.getSumMoneyByDeclaretionDate(beginDate, endDate,
							new Boolean(true), tobj.getBill1(),
							ImpExpFlag.IMPORT);
			BigDecimal bd = new BigDecimal(mymoney);
			String totalmoney = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toString();
			temp5.setBill3(totalmoney);
			tempList.add(temp5);
		}
		// 计算包装种类
		List hslist = new Vector(hs.values());
		for (int i = 0; i < hslist.size(); i++) {
			BillTemp temp4 = new BillTemp();
			BillTemp yobj = (BillTemp) hslist.get(i);
			if (i == 0) {
				temp4.setBill1("包装种类:");
			}
			temp4.setBill2(yobj.getBill1());
			temp4.setBill3(String.valueOf(yobj.getBillSum1()));
			tempList.add(temp4);
		}
		return tempList;
	}

	// 计算包装种类

	/**
	 * 应退换料件
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内与指定的进出口标志匹配的应退换料件的情况
	 */
	public List jisuanExchangeImp(Integer impExpFlag, Date beginDate,
			Date endDate) {
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfoByType(
				ImpExpType.MATERIAL_EXCHANGE, null, null, new Boolean(true));
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsEdiTrDao
				.findEmsHeadH2kInExecuting().get(0);
		Hashtable ht = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			String no = String.valueOf(info.getCommSerialNo());// 备案序号
			String complex = info.getComplex() == null ? null : info
					.getComplex().getCode();// 商品编码
			String emsNo = emsHeadH2k.getEmsNo();// 帐册号
			String namespec = info.getCommName() + "/" + info.getCommSpec();// 商品名称/规格
			String unit = info.getUnit() == null ? null : info.getUnit()
					.getName();// 单位
			String declarationDate = dateToString(info
					.getBaseCustomsDeclaration().getDeclarationDate());
			String keys = no;
			if (ht.get(keys) != null) { // 查到
				temp = (BillTemp) ht.get(keys);
			} else {
				temp = new BillTemp();
				temp.setBill1(emsNo);
				temp.setBill2(no);
				temp.setBill3(complex);
				temp.setBill4(namespec);
				temp.setBill5(unit);
				temp.setBill6(declarationDate);
				String begindate = dateToDate(info.getBaseCustomsDeclaration()
						.getDeclarationDate());
				String enddate = dateToDate(new Date());
				temp.setBill7(String
						.valueOf(90 - countDays(begindate, enddate)));
			}
			temp.setBillSum1(fd(temp.getBillSum1()) + fd(info.getCommAmount()));
			temp.setBillSum2(fd(temp.getBillSum1()));
		}
		return new Vector(ht.values());

	}

	/**
	 * 计算时间内天数
	 * 
	 * @param s1
	 *            时间1
	 * @param s2
	 *            时间2
	 * @return 指定的两个时间中的天数
	 */
	public int countDays(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		try {
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
			Date d1 = s.parse(s1);
			Date d2 = s.parse(s2);
			Calendar c1 = new GregorianCalendar();
			Calendar c2 = new GregorianCalendar();
			c1.setTime(d1);
			c2.setTime(d2);
			int days = 0;
			while (c2.after(c1)) {
				days++;
				c1.add(Calendar.DATE, 1);
			}
			return days;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 应退换成品
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内应退换成品的情况
	 */
	public List jisuanExchangeExp(Integer impExpFlag, Date beginDate,
			Date endDate) {
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfoByType(
				ImpExpType.BACK_FACTORY_REWORK, null, null, new Boolean(true));
		EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsEdiTrDao
				.findEmsHeadH2kInExecuting().get(0);
		Hashtable ht = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			String no = String.valueOf(info.getCommSerialNo());// 备案序号
			String complex = info.getComplex() == null ? null : info
					.getComplex().getCode();// 商品编码
			String emsNo = emsHeadH2k.getEmsNo();// 帐册号
			String namespec = info.getCommName() + "/" + info.getCommSpec();// 商品名称/规格
			String unit = info.getUnit() == null ? null : info.getUnit()
					.getName();// 单位
			String declarationDate = dateToString(info
					.getBaseCustomsDeclaration().getDeclarationDate());
			String keys = no;
			if (ht.get(keys) != null) { // 查到
				temp = (BillTemp) ht.get(keys);
			} else {
				temp = new BillTemp();
				temp.setBill1(emsNo);
				temp.setBill2(no);
				temp.setBill3(complex);
				temp.setBill4(namespec);
				temp.setBill5(unit);
				temp.setBill6(declarationDate);
				String begindate = dateToDate(info.getBaseCustomsDeclaration()
						.getDeclarationDate());
				String enddate = dateToDate(new Date());
				temp.setBill7(String
						.valueOf(90 - countDays(begindate, enddate)));
			}
			temp.setBillSum1(fd(temp.getBillSum1()) + fd(info.getCommAmount()));
			temp.setBillSum2(fd(temp.getBillSum1()));
		}
		return new Vector(ht.values());
	}

	/**
	 * 帐册进出口量平横状况
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内帐册进出口情况
	 */
	public List jisuanEmsImpExpBalance(Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration) { // isDeclaration : 申报日期

		List arrayList = new ArrayList();
		List list = ((EncDao) this.getBaseEncDao()).findEmsEdiImg(null);
		List emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		findLjUseNum(hs, head, null, endDate, isEffect, isDeclaration, false,
				null, null);
		findLjUseNum(timehs, head, beginDate, endDate, isEffect, isDeclaration,
				false, null, null);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
			String no = String.valueOf(img.getSeqNum());// 备案序号
			Double price = CommonUtils.getEmsImgPrice(img);// 单价
			String complex = img.getComplex() == null ? null : img.getComplex()
					.getCode();// 商品编码
			String emsNo = img.getEmsHeadH2k().getEmsNo();// 帐册号
			String namespec = img.getName() + "/" + img.getSpec();// 商品名称/规格
			String unit = img.getUnit() == null ? null : img.getUnit()
					.getName();// 单位
			BillTemp temp = new BillTemp();
			temp.setBill1(complex);
			temp.setBill2(namespec);
			temp.setBill3(emsNo);
			temp.setBill4(no);
			temp.setBillSum1(price);
			temp.setBill5(unit);
			TempDD x = (TempDD) hs.get(no);
			Double ljUse = Double.valueOf(0);
			if (x != null) {
				ljUse = x.getAa();
			}
			TempDD y = (TempDD) timehs.get(no);
			Double allljUse = Double.valueOf(0);
			if (y != null) {
				allljUse = y.getAa();
			}
			temp.setBillSum7(ljUse);// 成品耗用量
			temp.setBillSum6(allljUse);// 本期成品耗用量

			// 总进口量 = 料件进口+转厂进口-退料出口
			Double slj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.DIRECT_IMPORT, null, endDate, isEffect, null,
					img.getSeqNum(), isDeclaration, false, null);
			Double szj = ((EncDao) this.getBaseEncDao())
					.getSumCommAmount(ImpExpType.TRANSFER_FACTORY_IMPORT, null,
							endDate, isEffect, null, img.getSeqNum(),
							isDeclaration, false, null);
			Double stj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.BACK_MATERIEL_EXPORT, null, endDate, isEffect,
					null, img.getSeqNum(), isDeclaration, false, null);

			Double yljk = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
					ImpExpType.REMAIN_FORWARD_IMPORT, null, endDate, isEffect,
					null, img.getSeqNum(), isDeclaration, false, null);

			Double sz = slj + szj + yljk - stj; // 进口量(数量)
			temp.setBillSum3(sz);
			temp.setBillSum2(slj + szj + stj + yljk); // 备案数量

			temp.setBillSum5(fd(temp.getBillSum3()) - fd(temp.getBillSum7())); // 差异=总进口-成品耗用
			arrayList.add(temp);
		}
		return arrayList;
	}

	// ------------------------
	public List jisuanEmsImpExpBalanceForDept(Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration, String deptCode) { // isDeclaration
		// :
		// 申报日期

		List arrayList = new ArrayList();
		List list = ((EncDao) this.getBaseEncDao()).findEmsEdiImg(null);
		List deptList = ((EncDao) this.getBaseEncDao()).findAllDept(deptCode);// 获取所有事业部
		List emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		int size = deptList.size() + 1;
		for (int k = 0; k < size; k++) {// 循环事业部
			String projectDeptCode = null;
			String projectDeptName = null;
			if (k == (size - 1) && deptCode == null) {
				projectDeptCode = null;
				projectDeptName = null;
			} else if (k == (size - 1)) {
				continue;
			} else {
				ProjectDept pobj = (ProjectDept) deptList.get(k);
				if (pobj.getCode() == null || pobj.getCode().equals("")) {
					projectDeptCode = null;
					projectDeptName = null;
				} else {
					projectDeptCode = pobj.getCode();// 事业部代码
					projectDeptName = pobj.getName();

				}
			}
			findLjUseNum(hs, head, null, endDate, isEffect, isDeclaration,
					true, projectDeptCode, null);
			findLjUseNum(timehs, head, beginDate, endDate, isEffect,
					isDeclaration, true, projectDeptCode, null);
			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
				String no = String.valueOf(img.getSeqNum());// 备案序号
				Double price = CommonUtils.getEmsImgPrice(img);// 单价
				String complex = img.getComplex() == null ? null : img
						.getComplex().getCode();// 商品编码
				String emsNo = img.getEmsHeadH2k().getEmsNo();// 帐册号
				String namespec = img.getName() + "/" + img.getSpec();// 商品名称/规格
				String unit = img.getUnit() == null ? null : img.getUnit()
						.getName();// 单位
				BillTemp temp = new BillTemp();
				temp.setBill1(complex);
				temp.setBill2(namespec);
				temp.setBill3(emsNo);
				temp.setBill4(no);
				temp.setBillSum1(price);
				temp.setBill5(unit);
				temp.setBill6(projectDeptName);
				TempDD x = (TempDD) hs.get(no);
				Double ljUse = Double.valueOf(0);
				if (x != null) {
					ljUse = x.getAa();
				}
				TempDD y = (TempDD) timehs.get(no);
				Double allljUse = Double.valueOf(0);
				if (y != null) {
					allljUse = y.getAa();
				}
				temp.setBillSum7(ljUse);// 成品耗用量
				temp.setBillSum6(allljUse);// 本期成品耗用量

				// 总进口量 = 料件进口+转厂进口-退料出口
				Double slj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.DIRECT_IMPORT, null, endDate, isEffect,
						null, img.getSeqNum(), isDeclaration, true,
						projectDeptCode);
				Double szj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, endDate,
						isEffect, null, img.getSeqNum(), isDeclaration, true,
						projectDeptCode);
				Double stj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.BACK_MATERIEL_EXPORT, null, endDate,
						isEffect, null, img.getSeqNum(), isDeclaration, true,
						projectDeptCode);

				Double yljk = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.REMAIN_FORWARD_IMPORT, null, endDate,
						isEffect, null, img.getSeqNum(), isDeclaration, true,
						projectDeptCode);

				Double sz = slj + szj + yljk - stj; // 进口量(数量)
				temp.setBillSum3(sz);
				temp.setBillSum2(slj + szj + stj + yljk); // 备案数量

				temp.setBillSum5(fd(temp.getBillSum3())
						- fd(temp.getBillSum7())); // 差异=总进口-成品耗用
				arrayList.add(temp);
			}
		}
		return arrayList;
	}

	/**
	 * 进口料件使用情况
	 * 
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            料件序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内符合条件的进口料件情况：同一个序号，可有多个事业部
	 */
	public List findImpExpCommInfoUseForDept(boolean isImport, Integer seqNum,
			String customer, String IEType, Date beginDate, Date endDate,
			boolean isDeclaration, List deptCode, int isEffect) { // --根据事业部来统计
		// boolean isEffect = true;
		List arrayList = new ArrayList();
		List list = ((EncDao) this.getBaseEncDao()).findEmsEdiImg(null);
		List deptList = ((EncDao) this.getBaseEncDao())
				.findAllDeptMore(deptCode);// 获取所有事业部
		List emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		int size = deptList.size() + 1;
		System.out.println("deptList=" + deptList.size());
		for (int k = 0; k < size; k++) {// 循环事业部
			String projectDeptCode = null;
			String projectDeptName = null;
			if (k == (size - 1) && deptCode == null) {
				projectDeptCode = null;
				projectDeptName = null;
			} else if (k == (size - 1)) {
				continue;
			} else {
				ProjectDept pobj = (ProjectDept) deptList.get(k);
				if (pobj.getCode() == null || pobj.getCode().equals("")) {
					projectDeptCode = null;
					projectDeptName = null;
				} else {
					projectDeptCode = pobj.getCode();// 事业部代码
					projectDeptName = pobj.getName();

				}
			}
			findLjUseNum(hs, head, null, endDate, isEffect, isDeclaration,
					true, projectDeptCode, seqNum);
			findLjUseNum(timehs, head, beginDate, endDate, isEffect,
					isDeclaration, true, projectDeptCode, seqNum);

			findLjUseNumForTran(tranThs, head, beginDate, endDate, isEffect,
					isDeclaration, true, projectDeptCode, seqNum);

			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
				Integer no = img.getSeqNum(); // 料件备案序号
				BillTemp temp = new BillTemp();
				temp.setBill1(String.valueOf(no));// 备案序号
				temp.setBill2(img.getComplex() == null ? null : img
						.getComplex().getCode());// 商品编码
				temp.setBill3(img.getName());
				temp.setBill9(img.getSpec());
				temp.setBill4(img.getUnit() == null ? "" : img.getUnit()
						.getName());

				Double allLjUse = Double.valueOf(0);
				Double allbjl = Double.valueOf(0);
				Double useNum = Double.valueOf(0);
				Double bjlNum = Double.valueOf(0);
				Double cpUseNum = Double.valueOf(0);
				Double tranUseNum = Double.valueOf(0);
				Double fgfchy = Double.valueOf(0);
				Double tcfghy = Double.valueOf(0);
				TempDD x = (TempDD) hs.get(String.valueOf(no));
				TempDD y = (TempDD) timehs.get(String.valueOf(no));
				TempDD z = (TempDD) tranThs.get(String.valueOf(no));
				if (x != null) {
					allLjUse = x.getAa();
					allbjl = x.getBb();
				}
				if (y != null) {
					useNum = y.getAa();
					bjlNum = y.getBb();
					fgfchy = y.getFgfchy();
					tcfghy = y.getTcfghy();
				}
				if (z != null) {
					cpUseNum = z.getAa();
					tranUseNum = z.getBb();
				}

				temp.setBillSum7(allLjUse);// 6.成品使用量
				temp.setBillSum8(allbjl);
				temp.setBillSum9(0 - allLjUse);
				temp.setBillSum13(useNum);
				temp.setBillSum14(bjlNum);
				temp.setBillSum16(cpUseNum);
				temp.setBillSum17(tranUseNum);
				temp.setBill5(projectDeptName);// 事业部
				temp.setBillSum20(fgfchy);// 返工复出
				temp.setBillSum21(tcfghy);// 退厂返工

				Double lj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.DIRECT_IMPORT, beginDate, endDate, isEffect,// 直接进口
						null, no, isDeclaration, true, projectDeptCode);
				Double zj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);// 转厂

				Double stj = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.BACK_MATERIEL_EXPORT, null, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);// 退料出口
				Double yljk = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.REMAIN_FORWARD_IMPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double ylck = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.REMAIN_FORWARD_EXPORT, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);
				Double nx = ((EncDao) this.getBaseEncDao()).getSumCommAmount(
						ImpExpType.MATERIAL_DOMESTIC_SALES, beginDate, endDate,
						isEffect, null, no, isDeclaration, true,
						projectDeptCode);// 料件内销，海关批准内销
				temp.setBillSum4(lj);// 3.直接进口量
				temp.setBillSum5(zj);// 4.转厂进口量
				temp.setBillSum6(stj);// 5.退料出口量
				temp.setBillSum3(lj + zj);// "2.大单进口量=3+4"
				temp.setBillSum1(yljk);// 11.余料结转(进口)
				temp.setBillSum2(lj + zj + yljk - stj - ylck);// "1.总进口量=2+11+4-5-余料转出"
				temp.setBillSum9(lj + zj + yljk - stj - allLjUse - nx - ylck);// 8.余料情况=1-6
				temp.setBillSum18(nx);// 8.料件内销，海关批准内销

				Double averprice = ((EncDao) this.getBaseEncDao())
						.getImgAveragePrice(no, beginDate, endDate,
								new Boolean(true), false, isDeclaration, true,
								projectDeptCode); // 报关申报平均单价

				Double billsum10 = (fd(temp.getBillSum4())
						+ fd(temp.getBillSum5()) - fd(temp.getBillSum6()) - fd(temp
							.getBillSum7())) * averprice;
				temp.setBillSum10(billsum10);

				List listAmount = ((EncDao) this.getBaseEncDao())
						.findSumAmount(img.getSeqNum());
				Double ckAmount = getSumCkAmount(listAmount);
				temp.setBillSum11(ckAmount);
				temp.setBillSum12(fd(temp.getBillSum9()) - ckAmount);

				arrayList.add(temp);
			}
		}
		return arrayList;

	}

	// -----------------------------------------------------------------------------------------------------------------

	// 不分事业部 进口料件情况统计表
	public List findImpExpCommInfoUseNoDept(boolean isImport, Integer seqNum,
			String customer, String IEType, Date beginDate, Date endDate,
			boolean isDeclaration, int isEffect, boolean isCountStoreNum) {
		// 所有进口报关单
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfo(seqNum,
				customer, IEType, beginDate, endDate, false, isEffect,
				isDeclaration, false, null); // isDeclaration

		// 申报日期
		Hashtable ht = new Hashtable();
		List emslist = emsEdiTrDao.findEmsHeadH2kInExecuting();
		EmsHeadH2k head = (EmsHeadH2k) emslist.get(0);
		// long begintime = System.currentTimeMillis();
		findLjUseNum(hs, head, null, endDate, isEffect, isDeclaration, false,
				null, seqNum);// 所有料件耗用量
		findLjUseNum(timehs, head, beginDate, endDate, isEffect, isDeclaration,
				false, null, seqNum);// 本期料件耗用量
		findLjUseNumForTran(tranThs, head, beginDate, endDate, isEffect,
				isDeclaration, false, null, seqNum);
		// long endtime = System.currentTimeMillis();
		// System.out.println("-------2222-----time1:" + (endtime - begintime)
		// / 1000.0);
		// 临时封装对象;
		BillTemp temp = null;
		String complex = null; // 商品编码
		Integer no = null; // 商品序号
		CustomsDeclarationCommInfo info = null;
		String projectDeptName = null; // 事业单位
		String name = null; // 报关单（商品名称）
		String spec = null; // 报关单（型号规格）
		String unit = null; // 报关单（单位）
		// Double allLjUse = 0.0; // 成品耗用量
		// Double allbjl = 0.0; // 边角料
		// Double useNum = 0.0; // 本期成品耗用量
		// Double bjlNum = 0.0; // 本期边角料
		// Double cpUseNum = 0.0; // 本期直接出口使用量
		// Double tranUseNum = 0.0; // 本期转厂出口使用量
		// TempDD x = null;
		// TempDD y = null;
		// TempDD z = null;
		Double ckAmount = 0.0; // 统计仓库数量

		for (int i = 0; i < list.size(); i++) {
			info = (CustomsDeclarationCommInfo) list.get(i);

			no = info.getCommSerialNo();

			if (info.getComplex() != null) {
				complex = info.getComplex() == null ? "" : info.getComplex()
						.getCode();
			}
			projectDeptName = (info.getProjectDept() == null ? "" : info
					.getProjectDept().getName());
			name = info.getCommName();
			spec = info.getCommSpec();

			if (info.getUnit() != null) {
				unit = info.getUnit() == null ? "" : info.getUnit().getName();
			}
			Integer key = no;
			if (ht.get(key) != null) {
				temp = (BillTemp) ht.get(key);
			} else {
				temp = new BillTemp();
				temp.setBill1(String.valueOf(no));
				temp.setBill2(complex);
				temp.setBill3(name);
				temp.setBill9(spec);
				temp.setBill4(unit);
				temp.setBill5(projectDeptName);
				ht.put(key, temp);
				Double allLjUse = Double.valueOf(0);
				Double allbjl = Double.valueOf(0);
				Double useNum = Double.valueOf(0);
				Double bjlNum = Double.valueOf(0);
				Double cpUseNum = Double.valueOf(0);
				Double tranUseNum = Double.valueOf(0);
				Double tcfghy = Double.valueOf(0);
				Double fgfchy = Double.valueOf(0);
				TempDD x = (TempDD) hs.get(String.valueOf(no));
				TempDD y = (TempDD) timehs.get(String.valueOf(no));
				TempDD z = (TempDD) tranThs.get(String.valueOf(no));
				if (x != null) {
					allLjUse = x.getAa();
					allbjl = x.getBb();
				}
				if (y != null) {
					useNum = y.getAa();
					bjlNum = y.getBb();
					fgfchy = y.getFgfchy();
					tcfghy = y.getTcfghy();
				}
				if (z != null) {
					cpUseNum = z.getAa();
					tranUseNum = z.getBb();
				}
				temp.setBillSum7(allLjUse);
				temp.setBillSum8(allbjl);// 边角料
				temp.setBillSum9(fd(temp.getBillSum2()) - useNum
						- fd(temp.getBillSum18()));
				temp.setBillSum13(useNum);// 本期成品使用量
				temp.setBillSum14(bjlNum);// 本期边角料
				temp.setBillSum16(cpUseNum);
				temp.setBillSum17(tranUseNum);
				temp.setBillSum20(fgfchy);// 返工复出
				temp.setBillSum21(tcfghy);// 退厂返工
			}
			if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.DIRECT_IMPORT)) { // 直接进口-
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));

				temp.setBillSum2(fd(temp.getBillSum2())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						+ fd(info.getCommAmount()));

			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)) { // 料件转厂
				temp.setBillSum5(fd(temp.getBillSum5())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				temp.setBillSum2(fd(temp.getBillSum2())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						+ fd(info.getCommAmount()));
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.BACK_MATERIEL_EXPORT)) {// 退料出口
				temp.setBillSum6(fd(temp.getBillSum6())
						+ fd(info.getCommAmount()));
				temp.setBillSum2(fd(temp.getBillSum2())
						- fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						- fd(info.getCommAmount()));

			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.REMAIN_FORWARD_IMPORT)) {
				temp.setBillSum2(fd(temp.getBillSum2())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						+ fd(info.getCommAmount()));

				temp.setBillSum1(fd(temp.getBillSum1())
						+ fd(info.getCommAmount()));
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.MATERIAL_DOMESTIC_SALES)) {
				temp.setBillSum18(fd(temp.getBillSum18())
						+ fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						- fd(info.getCommAmount()));
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.REMAIN_FORWARD_EXPORT)) {
				temp.setBillSum2(fd(temp.getBillSum2())
						- fd(info.getCommAmount()));
				temp.setBillSum9(fd(temp.getBillSum9())
						- fd(info.getCommAmount()));
			}

			Double dollarUnitPrice = (info.getCommAmount() == null || (Double
					.valueOf(0)).equals(info.getCommAmount())) ? Double
					.valueOf(0) : (fd(info.getDollarTotalPrice()) / fd(info
					.getCommAmount()));
			Double billsum10 = (fd(temp.getBillSum4()) + fd(temp.getBillSum5())
					- fd(temp.getBillSum6()) - fd(temp.getBillSum7()))
					* dollarUnitPrice;
			temp.setBillSum10(billsum10);

			if (isCountStoreNum) { // 如果需要统计仓库数量
				List listAmount = ((EncDao) this.getBaseEncDao())
						.findSumAmount(info.getCommSerialNo());
				ckAmount = getSumCkAmount(listAmount);
			}

			temp.setBillSum11(ckAmount);
			temp.setBillSum12(fd(temp.getBillSum9()) - ckAmount);

		}
		// 查找电子帐册料件
		EmsHeadH2kImg img = null;
		Integer seqNum1 = null;
		String complex1 = null;
		String name1 = null;
		String spec1 = null;
		String unit1 = null;
		Integer key1 = null;

		List emsImg = ((EncDao) this.getBaseEncDao()).findEmsEdiImg(seqNum);

		for (int j = 0; j < emsImg.size(); j++) {
			img = (EmsHeadH2kImg) emsImg.get(j);
			seqNum1 = img.getSeqNum();
			complex1 = img.getComplex() == null ? "" : img.getComplex()
					.getCode();
			name1 = img.getName();
			spec1 = img.getSpec();
			unit1 = img.getUnit() == null ? "" : img.getUnit().getName();
			key1 = seqNum1;

			if (ht.get(key1) == null) {

				temp = new BillTemp();
				temp.setBill1(String.valueOf(seqNum1));
				temp.setBill2(complex1);
				temp.setBill3(name1);
				temp.setBill9(spec1);
				temp.setBill4(unit1);
				ht.put(key1, temp);
				Double allLjUse = Double.valueOf(0);
				Double allbjl = Double.valueOf(0);
				Double useNum = Double.valueOf(0);
				Double bjlNum = Double.valueOf(0);
				Double cpUseNum = Double.valueOf(0);
				Double tranUseNum = Double.valueOf(0);
				Double tcfghy = Double.valueOf(0);
				Double fgfchy = Double.valueOf(0);

				TempDD x = (TempDD) hs.get(String.valueOf(img.getSeqNum()));
				TempDD y = (TempDD) timehs.get(String.valueOf(img.getSeqNum()));
				TempDD z = (TempDD) tranThs
						.get(String.valueOf(img.getSeqNum()));

				if (x != null) {
					allLjUse = x.getAa();
					allbjl = x.getBb();
				}
				if (y != null) {
					useNum = y.getAa();
					bjlNum = y.getBb();
					fgfchy = y.getFgfchy();
					tcfghy = y.getTcfghy();
				}
				if (z != null) {
					cpUseNum = z.getAa();
					tranUseNum = z.getBb();
				}
				temp.setBillSum13(useNum);// 本期成品耗用量
				temp.setBillSum7(allLjUse);// 成品耗用量
				temp.setBillSum8(allbjl);// 边角料
				temp.setBillSum14(bjlNum);// 本期边角料
				temp.setBillSum16(cpUseNum);
				temp.setBillSum17(tranUseNum);
				temp.setBillSum20(fgfchy);// 返工复出
				temp.setBillSum21(tcfghy);// 退厂返工

				temp.setBillSum9(fd(temp.getBillSum2()) - useNum
						- fd(temp.getBillSum18()));
				// 统计仓库数量
				if (isCountStoreNum) { // 如果需要统计仓库数量
					List listAmount = ((EncDao) this.getBaseEncDao())
							.findSumAmount(info.getCommSerialNo());
					ckAmount = getSumCkAmount(listAmount);
				}
				temp.setBillSum11(ckAmount);
				temp.setBillSum12(fd(temp.getBillSum9()) - ckAmount);

			}

		}

		ArrayList returnList = new ArrayList();
		TreeMap ts = new TreeMap();
		Enumeration e = ht.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			ts.put(new Integer(String.valueOf(key)), key);
		}
		Set st = ts.keySet();
		for (Iterator i = st.iterator(); i.hasNext();) {
			returnList.add(ht.get(ts.get(i.next())));
		}
		return returnList;
	}

	/**
	 * 出口成品使用情况表
	 * 
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            成品序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内符合条件的成品使用情况 需要增加事业部
	 */
	public List findImpExpCommInfoUseForExgForDept(boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, boolean isdept, List deptCode,
			int isEffect) {

		if (isdept && deptCode == null) {
			isdept = false;
		}

		// 所有出口报关单
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfo(seqNum,
				customer, IEType, beginDate, endDate, true, isEffect,
				isDeclaration, isdept, deptCode);// isDeclaration
		// :
		// 是否申报
		Hashtable ht = new Hashtable();

		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			String no = String.valueOf(info.getCommSerialNo());
			String complex = null;
			if (info.getComplex() != null) {
				complex = info.getComplex().getCode();
			}
			String name = info.getCommName();
			String spec = info.getCommSpec();
			String projectDeptCode = info.getProjectDept() == null ? "" : info
					.getProjectDept().getCode();// 事业部代码
			String projectDeptName = info.getProjectDept() == null ? "" : info
					.getProjectDept().getName();// 事业部名称
			String unit = null;
			if (info.getUnit() != null) {
				unit = info.getUnit().getName();
			}
			String version = info.getVersion() == null ? "0" : info
					.getVersion();
			String key = no + "/" + version + "/" + projectDeptCode;
			if (ht.get(key) != null) {
				temp = (BillTemp) ht.get(key);
			} else {
				temp = new BillTemp();
				temp.setBill1(no);
				temp.setBill2(complex);
				temp.setBill3(name);
				temp.setBill9(spec);
				temp.setBill6(projectDeptName);
				ht.put(key, temp);
				temp.setBill4(unit);
				temp.setBill5(version);

			}
			Double money = fd(info.getCommAmount())
					* fd(info.getCommUnitPrice());
			temp.setBillSum2(fd(temp.getBillSum2()) + money);
			if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.DIRECT_EXPORT)) {
				temp.setBillSum5(fd(temp.getBillSum5())
						+ fd(info.getCommAmount()));
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}

			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)) {
				temp.setBillSum6(fd(temp.getBillSum6())
						+ fd(info.getCommAmount()));
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.BACK_FACTORY_REWORK)) {
				temp.setBillSum7(fd(temp.getBillSum7())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						- fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.REWORK_EXPORT)) {
				temp.setBillSum8(fd(temp.getBillSum8())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			}
		}
		return new Vector(ht.values());
	}

	// 不分事业部 成品执行情况统计表
	public List findImpExpCommInfoUseForExgNoDept(boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, int isEffect) {
		System.out.println("isImport->" + isImport);
		System.out.println("seqNum->" + seqNum);
		System.out.println("customer->" + customer);
		System.out.println("IEType->" + IEType);
		System.out.println("beginDate->" + beginDate);
		System.out.println("isDeclaration->" + isDeclaration);
		// 所有出口报关单
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfo(seqNum,
				customer, IEType, beginDate, endDate, true, isEffect,
				isDeclaration, false, null);// isDeclaration
		// : 是否申报
		Hashtable ht = new Hashtable();

		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			BillTemp temp = null;
			String no = String.valueOf(info.getCommSerialNo());
			System.out.println("s**********************************" + no);
			String complex = null;
			if (info.getComplex() != null) {
				complex = info.getComplex().getCode();
			}
			String name = info.getCommName();
			String spec = info.getCommSpec();
			String projectDeptName = info.getProjectDept() == null ? "" : info
					.getProjectDept().getName();
			String unit = null;
			if (info.getUnit() != null) {
				unit = info.getUnit().getName();
			}
			String version = info.getVersion();
			String key = no + "000" + (version == null ? "0" : version);

			if (ht.get(key) != null) {
				temp = (BillTemp) ht.get(key);
			} else {
				temp = new BillTemp();
				temp.setBill1(no);
				temp.setBill2(complex);
				temp.setBill3(name);
				temp.setBill9(spec);
				temp.setBill6(projectDeptName);
				ht.put(key, temp);
				temp.setBill4(unit);
				temp.setBill5(version);

			}
			Double money = fd(info.getCommAmount())
					* fd(info.getCommUnitPrice());
			temp.setBillSum2(fd(temp.getBillSum2()) + money);
			if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.DIRECT_EXPORT)) {
				temp.setBillSum5(fd(temp.getBillSum5())
						+ fd(info.getCommAmount()));
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)) {
				temp.setBillSum6(fd(temp.getBillSum6())
						+ fd(info.getCommAmount()));
				temp.setBillSum4(fd(temp.getBillSum4())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.BACK_FACTORY_REWORK)) {
				temp.setBillSum7(fd(temp.getBillSum7())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						- fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			} else if (info.getBaseCustomsDeclaration().getImpExpType()
					.equals(ImpExpType.REWORK_EXPORT)) {
				temp.setBillSum8(fd(temp.getBillSum8())
						+ fd(info.getCommAmount()));
				temp.setBillSum3(fd(temp.getBillSum3())
						+ fd(info.getCommAmount()));
				double sum2 = temp.getBillSum2() == null ? 0.0 : temp
						.getBillSum2();
				double sum3 = temp.getBillSum3() == null ? 0.0 : temp
						.getBillSum3();
				if (sum3 != 0) {
					double priace = (sum2 / sum3);
					priace = CommonUtils.getDoubleByDigit(priace, 3);
					temp.setBillSum1(priace);
				}
			}
		}
		List emsImg = ((EncDao) this.getBaseEncDao()).findEmsEdiExg(seqNum);
		for (int j = 0; j < emsImg.size(); j++) {
			EmsHeadH2kExg img = (EmsHeadH2kExg) emsImg.get(j);
			List vList = ((EncDao) this.getBaseEncDao()).findEmsVersion(img);
			for (int k = 0; k < vList.size(); k++) {
				EmsHeadH2kVersion version = (EmsHeadH2kVersion) vList.get(k);
				String vNo = String.valueOf(version.getVersion());
				String seqNum1 = String.valueOf(img.getSeqNum());

				String complex1 = img.getComplex() == null ? "" : img
						.getComplex().getCode();
				String name1 = img.getName();
				String spec1 = img.getSpec();
				String unit1 = img.getUnit() == null ? "" : img.getUnit()
						.getName();
				String key1 = seqNum1 + "000" + (vNo == null ? "0" : vNo);
				if (ht.get(key1) == null) {
					BillTemp temp = new BillTemp();
					temp.setBill1(seqNum1);
					temp.setBill2(complex1);
					temp.setBill3(name1);
					temp.setBill9(spec1);
					temp.setBill4(unit1);
					temp.setBill5(vNo);
					ht.put(key1, temp);
				}
			}

		}
		// ArrayList returnList = new ArrayList();
		// TreeMap ts = new TreeMap();
		// Enumeration e = ht.keys();
		// while (e.hasMoreElements()) {
		// Object key = e.nextElement();
		// ts.put(String.valueOf(key), key);
		// }
		// Set st = ts.keySet();
		// for (Iterator i = st.iterator(); i.hasNext();) {
		// returnList.add(ht.get(ts.get(i.next())));
		// }
		return new ArrayList(ht.values());

	}

	/**
	 * 根据类型得到明细
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param iseffective
	 *            是否生效
	 * @return 在有效期内与指定的类型匹配生效的进出口商品明细
	 */
	public List findImpExpCommInfoPriceByType(boolean isProduct,
			Date beginDate, Date endDate, Boolean iseffective) {
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfoOther(
				null, null, null, beginDate, endDate, null, null, isProduct,
				true, null, null, null, null, false, null);
		Map<String, CustomsDeclarationCommInfo> map = new HashMap<String, CustomsDeclarationCommInfo>();
		List arrayList = new ArrayList();
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				CustomsDeclarationCommInfo commInfo = (CustomsDeclarationCommInfo) list
						.get(i);
				String s = commInfo.getCommName() + commInfo.getCommUnitPrice();
				map.put(s, commInfo);
			}
		}
		if (map.size() != 0) {
			Object[] ary = map.keySet().toArray();
			for (int i = 0; i < ary.length; i++) {
				arrayList.add(map.get(ary[i].toString()));
			}
		}
		return arrayList;
	}

	private Double fd(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 根据类型得到明细
	 * 
	 * @param type
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param iseffective
	 *            是否生效
	 * @return 在有效期内与指定的类型匹配生效的进出口商品明细
	 */
	// public List findImpExpComminfoDj(Integer type, Trade tradeMode,
	// Integer commName, Date beginDate, Date endDate, boolean iseffective,Map
	// paraMap) {
	// List list = ((EncDao) this.getBaseEncDao()).findImpExpComminfoDj(type,
	// tradeMode, commName, beginDate, endDate, iseffective,paraMap);
	// Map<String, CustomsDeclarationCommInfo> map = new HashMap<String,
	// CustomsDeclarationCommInfo>();
	// List arrayList = new ArrayList();
	// if (list != null && list.size() != 0) {
	// for (int i = 0; i < list.size(); i++) {
	// CustomsDeclarationCommInfo commInfo = (CustomsDeclarationCommInfo) list
	// .get(i);
	// String s = commInfo.getBaseCustomsDeclaration()
	// .getCustomsDeclarationCode()
	// + commInfo.getCommName() + commInfo.getCommSpec();
	// map.put(s, commInfo);
	// }
	// }
	// if (map.size() != 0) {
	// Object[] ary = map.keySet().toArray();
	// for (int i = 0; i < ary.length; i++) {
	// arrayList.add(map.get(ary[i].toString()));
	// }
	// }
	// if (list.size() >= 0) {
	// for (int i = 0; i < list.size(); i++) {
	// CustomsDeclarationCommInfo comminfo = (CustomsDeclarationCommInfo) list
	// .get(i);
	// String customsdeclarationcodeNew = null;
	// String customsdeclarationcodeOld = comminfo
	// .getBaseCustomsDeclaration()
	// .getCustomsDeclarationCode() == null ? "" : comminfo
	// .getBaseCustomsDeclaration()
	// .getCustomsDeclarationCode();
	// String serialNumber = String.format("%03d", (comminfo
	// .getSerialNumber() == null ? "" : comminfo
	// .getSerialNumber()));
	// customsdeclarationcodeNew = customsdeclarationcodeOld
	// + serialNumber;
	// comminfo.getBaseCustomsDeclaration().setCustomsDeclarationCode(
	// customsdeclarationcodeNew);
	// }
	// }
	// return list;
	// }

	static Double dj(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 进出口报关商品信息
	 * 
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @param seqNum
	 *            序号
	 * @param customer
	 *            客户供应商
	 * @param ImpExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param version
	 *            版本
	 * @return 有效期内符合条件的进出口报关商品信息
	 */
	public List findImpExpCommInfoList(boolean isImport, Integer seqNum,
			String customer, String ImpExpType, Date beginDate, Date endDate,
			String version, boolean isDeclaration, boolean isdept,
			List deptCode, int isEffect) {
		if (isdept && deptCode == null) {
			isdept = false;
		}
		List<ImpExpCustomsDeclarationCommInfo> lsResult = new ArrayList<ImpExpCustomsDeclarationCommInfo>();
		List list = ((EncDao) this.getBaseEncDao()).findImpExpCommInfoList(
				isImport, seqNum, customer, ImpExpType, beginDate, endDate,
				version, isDeclaration, isdept, deptCode, isEffect); // 选择所有报关单表体
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		Hashtable hs = new Hashtable();
		double sumPrice = 0.0;
		double sumPriceFg = 0.0;
		for (int i = 0; i < list.size(); i++) {
			ImpExpCustomsDeclarationCommInfo tempCommInfo = new ImpExpCustomsDeclarationCommInfo();
			CustomsDeclarationCommInfo commInfo = (CustomsDeclarationCommInfo) list
					.get(i);
			Integer serialNum = commInfo.getBaseCustomsDeclaration()
					.getSerialNumber();
			if (hs.get(serialNum) == null) {
				hs.put(serialNum, serialNum);
			}
			try {
				PropertyUtils.copyProperties(tempCommInfo, commInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			double addupAmount = map.get(tempCommInfo.getCommSerialNo()) == null ? 0.0
					: Double.parseDouble(map
							.get(tempCommInfo.getCommSerialNo()).toString());
			if (isImport) {
				if (tempCommInfo.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.IMPORT) {
					addupAmount += (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
					sumPrice += (tempCommInfo.getDollarTotalPrice() == null ? 0.0
							: tempCommInfo.getDollarTotalPrice());
					sumPriceFg += (tempCommInfo.getCommTotalPrice() == null ? 0.0
							: tempCommInfo.getCommTotalPrice());
				} else if (tempCommInfo.getBaseCustomsDeclaration()
						.getImpExpFlag() == ImpExpFlag.EXPORT) {
					addupAmount -= (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
					sumPrice -= (tempCommInfo.getDollarTotalPrice() == null ? 0.0
							: tempCommInfo.getDollarTotalPrice());
					sumPriceFg -= (tempCommInfo.getCommTotalPrice() == null ? 0.0
							: tempCommInfo.getCommTotalPrice());
				}
			} else {
				if (tempCommInfo.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.EXPORT) {
					addupAmount += (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
					sumPrice += (tempCommInfo.getDollarTotalPrice() == null ? 0.0
							: tempCommInfo.getDollarTotalPrice());
					sumPriceFg += (tempCommInfo.getCommTotalPrice() == null ? 0.0
							: tempCommInfo.getCommTotalPrice());
				} else if (tempCommInfo.getBaseCustomsDeclaration()
						.getImpExpFlag() == ImpExpFlag.IMPORT) {
					addupAmount -= (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
					sumPrice -= (tempCommInfo.getDollarTotalPrice() == null ? 0.0
							: tempCommInfo.getDollarTotalPrice());
					sumPriceFg -= (tempCommInfo.getCommTotalPrice() == null ? 0.0
							: tempCommInfo.getCommTotalPrice());
				}
			}
			Double addupAmount1 = Double.valueOf(formatBig(
					String.valueOf(addupAmount), 4));
			Double sumPrice1 = Double.valueOf(formatBig(
					String.valueOf(sumPrice), 4));
			Double sumPriceFg1 = Double.valueOf(formatBig(
					String.valueOf(sumPriceFg), 4));
			tempCommInfo.setCommAddUpAmount(addupAmount1);// 总数量
			tempCommInfo.setSumPrice(sumPrice1);// 美圆金额
			tempCommInfo.setSumPriceFg(sumPriceFg1);// 总价值
			tempCommInfo.setBgdNum(hs.size());
			map.put(tempCommInfo.getCommSerialNo(), addupAmount1);
			lsResult.add(tempCommInfo);
		}
		return lsResult;
	}

	public List findSpecialImpExpCommInfoList(boolean isImport, Integer seqNum,
			String customer, String ImpExpType, Date beginDate, Date endDate,
			boolean isDeclaration, boolean isdept, List deptCode, int isEffect) {
		if (isdept && deptCode == null) {
			isdept = false;
		}
		List<ImpExpCustomsDeclarationCommInfo> lsResult = new ArrayList<ImpExpCustomsDeclarationCommInfo>();
		List list = ((EncDao) this.getBaseEncDao())
				.findSpecialImpExpCommInfoList(isImport, seqNum, customer,
						ImpExpType, beginDate, endDate, null, isDeclaration,
						isdept, deptCode, isEffect); // 选择所有报关单表体
		Hashtable hs = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			ImpExpCustomsDeclarationCommInfo tempCommInfo = new ImpExpCustomsDeclarationCommInfo();
			CustomsDeclarationCommInfo commInfo = (CustomsDeclarationCommInfo) list
					.get(i);
			Integer serialNum = commInfo.getBaseCustomsDeclaration()
					.getSerialNumber();
			if (hs.get(serialNum) == null) {
				hs.put(serialNum, serialNum);
			}
			try {
				PropertyUtils.copyProperties(tempCommInfo, commInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			double addupAmount = (tempCommInfo.getCommAmount() == null ? 0.0
					: tempCommInfo.getCommAmount());
			double sumPrice = (tempCommInfo.getDollarTotalPrice() == null ? 0.0
					: tempCommInfo.getDollarTotalPrice());
			double sumPriceFg = (tempCommInfo.getCommTotalPrice() == null ? 0.0
					: tempCommInfo.getCommTotalPrice());

			Double addupAmount1 = Double.valueOf(formatBig(
					String.valueOf(addupAmount), 4));
			Double sumPrice1 = Double.valueOf(formatBig(
					String.valueOf(sumPrice), 4));
			Double sumPriceFg1 = Double.valueOf(formatBig(
					String.valueOf(sumPriceFg), 4));
			String tmpType = com.bestway.common.constant.ImpExpType
					.getImpExpTypeDesc(Integer.valueOf((commInfo
							.getBaseCustomsDeclaration().getImpExpFlag())));
			tempCommInfo.setImpType(tmpType);
			tempCommInfo.setCommAddUpAmount(addupAmount1);// 总数量
			tempCommInfo.setSumPrice(sumPrice1);// 美圆金额
			tempCommInfo.setSumPriceFg(sumPriceFg1);// 总价值
			tempCommInfo.setBgdNum(hs.size());
			lsResult.add(tempCommInfo);
		}
		return lsResult;
	}

	/**
	 * 格式化小数位
	 * 
	 * @param amount
	 *            要格式化的数
	 * @param bigD
	 *            要保留的位数
	 * @return 按要求格式化后的数
	 */
	/*
	 * public String formatBig(String amount, int bigD) { if (amount == null ||
	 * amount.equals("")) { amount = "0"; } BigDecimal bd = new
	 * BigDecimal(amount); String amountStr = bd.setScale(bigD,
	 * BigDecimal.ROUND_HALF_UP) .toString(); return amountStr; }
	 */

	public String formatBig(String num, int digits) {
		double unroundedValue = 0;
		if (num == null || "".equals(num)) {
			return "0";
		}
		unroundedValue = Double.parseDouble(num);
		int x = 1;
		for (int i = 0; i < digits; i++) {
			x = x * 10;
		}
		double intPortion = Math.floor(unroundedValue);
		double unroundedDecimalPortion = (unroundedValue - intPortion);
		double roundedDecimalPortion = Math.round(unroundedDecimalPortion * x);
		roundedDecimalPortion = roundedDecimalPortion / x;
		double total = intPortion + roundedDecimalPortion;
		return (new Double(total)).toString();
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 以报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport) {
		return ((EncDao) this.getBaseEncDao())
				.findCustomsDeclarationCustomer(isImport);
	}

	/**
	 * 计算耗用情况
	 * 
	 * @param seqNum
	 *            料件序号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内成品耗用料件的情况
	 */
	/*
	 * public Double[] jiUse(Integer seqNum, Date beginDate, Date endDate,
	 * Boolean isEffect) { List list = ((EncDao)
	 * this.getBaseEncDao()).findBomBySeqNum(seqNum);// 根据料件序号找到BOM Double[]
	 * total = new Double[2]; total[0] = Double.valueOf(0); total[1] =
	 * Double.valueOf(0); Double xx = Double.valueOf(0); for (int i = 0; i <
	 * list.size(); i++) { EmsHeadH2kBom bom = (EmsHeadH2kBom) list.get(i);
	 * Integer CpseqNum = bom.getEmsHeadH2kVersion().getEmsHeadH2kExg()
	 * .getSeqNum();// 成品号 Integer version =
	 * bom.getEmsHeadH2kVersion().getVersion();// 版本号 Double unitwear =
	 * bom.getUnitWear(); Double wear = bom.getWear(); Double cdm = fd(unitwear)
	 * / (1 - (fd(wear) 0.01)); Double dm = fd(wear) 0.01; Double bgNum1 =
	 * ((EncDao) this.getBaseEncDao()).findCommAmount( CpseqNum,
	 * String.valueOf(version), beginDate, endDate, isEffect);// 成品出口，转厂出口，返工复出
	 * Double bgNum2 = ((EncDao) this.getBaseEncDao()).findCommAmount2(
	 * CpseqNum, String.valueOf(version), beginDate, endDate, isEffect);// 退厂返工
	 * Double bgnum = bgNum1 - bgNum2;// 总出口量 total[0] = total[0] + (cdm bgnum);
	 * total[1] = total[1] + (dm bgnum); } return total; }
	 */

	/**
	 * 查询已报关的商品名称
	 * 
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfo(boolean isImport) {
		List list = ((EncDao) this.getBaseEncDao())
				.findCustomsDeclarationCommInfo(isImport);
		List lists = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] listObj = (Object[]) list.get(i);
			TempComplex obj = new TempComplex();
			String name = "";
			if (listObj[1] != null) {
				name += listObj[1].toString();
			}
			if (listObj[2] != null) {
				name += ("/" + listObj[2].toString());
			}
			obj.setCode(listObj[0].toString());// 序号
			obj.setName(name);// 名称/规格
			obj.setEffective((Boolean) listObj[3]);
			lists.add(obj);
		}
		return lists;
	}

	/**
	 * 查询已报关的商品名称
	 * 
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfoNoSpec(boolean isImport) {
		List list = ((EncDao) this.getBaseEncDao())
				.findCustomsDeclarationCommInfo(isImport);
		List lists = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] listObj = (Object[]) list.get(i);
			TempComplex obj = new TempComplex();
			String name = "";
			if (listObj[1] != null) {
				name += listObj[1].toString();
			}
			// if (listObj[2] != null) {
			// name += ("/" + listObj[2].toString());
			// }
			obj.setCode(listObj[0].toString());// 序号
			obj.setName(name);// 名称/规格
			lists.add(obj);
		}
		return lists;
	}

	/**
	 * 把对象转化为字符型
	 * 
	 * @param obj
	 *            对象
	 * @return 字符型的对象
	 */
	private String forStr(Object obj) {
		if (obj != null) {
			return obj.toString();
		}
		return "";
	}

	/**
	 * 查询物料总量
	 * 
	 * @param tenSeqNum
	 *            十位备案序号
	 * @return 与指定的十位备案序号匹配的物料总量
	 */
	public List findMaterielAmountBySeqNum(Integer tenSeqNum) {
		List list = ((EncDao) this.getBaseEncDao())
				.findMaterielAmountBySeqNum(tenSeqNum);
		List mLists = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] listObj = (Object[]) list.get(i);
			BillTemp b = new BillTemp();
			b.setBill1(forStr(listObj[0]));// 料号
			b.setBill2(forStr(listObj[1]));// 名称
			b.setBill3(forStr(listObj[2]));// 规格
			b.setBill4(forStr(listObj[3]));// 单位
			if (listObj[4] == null) {
				b.setBillSum1(Double.valueOf(0));// 仓库折算报关数量
			} else {
				b.setBillSum1(toD(listObj[5]) / toD(listObj[4]));// 仓库折算报关数量
			}
			mLists.add(b);
		}
		return mLists;
	}

	private Double toD(Object obj) {
		if (obj == null) {
			return Double.valueOf(0);
		}
		return Double.valueOf(obj.toString());
	}

	/**
	 * 得到仓库数据汇总
	 * 
	 * @param list
	 *            指定的对象
	 * @return 仓库数据的汇总
	 */
	public Double getSumCkAmount(List list) {
		Double sum = Double.valueOf(0);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Double amount = Double.valueOf(0);
			if (objs[0] != null) {
				amount = toD(objs[0]);
			}
			Double unitConvert = Double.valueOf(1);
			if (objs[1] != null) {
				unitConvert = toD(objs[1]);
			}
			if (unitConvert.equals(Double.valueOf(0))) {
				continue;
			}
			sum = (amount / unitConvert) + sum;
		}
		return sum;
	}

	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill2(
			BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill) {

		if (exgbill != null) {// 出口
			BaseCustomsDeclarationCommInfo existcommInfo = ((EncDao) this
					.getBaseEncDao())
					.findBaseCustomsDeclarationCommInfoBySequm(
							exgbill.getSeqNum(), customsDeclaration,
							commInfo.getVersion(), commInfo.getCountry());
			if (existcommInfo != null) {
				existcommInfo.setCommAmount(fd(existcommInfo.getCommAmount())
						- fd(commInfo.getCommAmount()));
				existcommInfo.setFirstAmount(fd(existcommInfo.getFirstAmount())
						- fd(commInfo.getFirstAmount()));
				existcommInfo.setSecondAmount(fd(existcommInfo
						.getSecondAmount()) - fd(commInfo.getSecondAmount()));
				existcommInfo.setNetWeight(fd(existcommInfo.getNetWeight())
						- fd(commInfo.getNetWeight()));
				existcommInfo.setGrossWeight(fd(existcommInfo.getGrossWeight())
						- fd(commInfo.getGrossWeight()));
				existcommInfo.setPieces((existcommInfo.getPieces() == null ? 0
						: existcommInfo.getPieces())
						- (commInfo.getPieces() == null ? 0 : commInfo
								.getPieces()));
				double price = 0;
				if (existcommInfo.getCommUnitPrice() != null) {
					price = existcommInfo.getCommUnitPrice().doubleValue();
				}
				existcommInfo.setCommTotalPrice(forNum(price
						* existcommInfo.getCommAmount()));

				((EncDao) this.getBaseEncDao())
						.saveCustomsDeclarationCommInfo(existcommInfo);
			}
		} else {// 进口
			BaseCustomsDeclarationCommInfo existcommInfo = ((EncDao) this
					.getBaseEncDao())
					.findBaseCustomsDeclarationCommInfoBySequm(
							imgbill.getSeqNum(), customsDeclaration, null,
							commInfo.getCountry());
			if (existcommInfo != null) {
				existcommInfo.setCommAmount(fd(existcommInfo.getCommAmount())
						- fd(commInfo.getCommAmount()));
				existcommInfo.setFirstAmount(fd(existcommInfo.getFirstAmount())
						- fd(commInfo.getFirstAmount()));
				existcommInfo.setSecondAmount(fd(existcommInfo
						.getSecondAmount()) - fd(commInfo.getSecondAmount()));
				existcommInfo.setNetWeight(fd(existcommInfo.getNetWeight())
						- fd(commInfo.getNetWeight()));
				existcommInfo.setGrossWeight(fd(existcommInfo.getGrossWeight())
						- fd(commInfo.getGrossWeight()));
				existcommInfo.setPieces((existcommInfo.getPieces() == null ? 0
						: existcommInfo.getPieces())
						- (commInfo.getPieces() == null ? 0 : commInfo
								.getPieces()));
				double price = 0;
				if (existcommInfo.getCommUnitPrice() != null) {
					price = existcommInfo.getCommUnitPrice().doubleValue();
				}
				existcommInfo.setCommTotalPrice(forNum(price
						* existcommInfo.getCommAmount()));

				((EncDao) this.getBaseEncDao())
						.saveCustomsDeclarationCommInfo(existcommInfo);
			}
		}
		return null;
	}

	/**
	 * 内部商品新增报关单表体
	 * 
	 * @param commInfo
	 *            报关单商品信息
	 * @param customsDeclaration
	 *            报关单内容
	 * @param exgbill
	 *            电子帐册成品表
	 * @param imgbill
	 *            电子帐册料件表
	 * @return 报关单表体内容
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill(
			BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill) {
		CustomsDeclarationSet other = this.findCustomsSet(customsDeclaration
				.getImpExpType());

		if (exgbill != null) {// 出口，注意版本号
			BaseCustomsDeclarationCommInfo existcommInfo = ((EncDao) this
					.getBaseEncDao())
					.findBaseCustomsDeclarationCommInfoBySequm(
							exgbill.getSeqNum(),
							customsDeclaration,
							(exgbill.getVersion() == null ? null : String
									.valueOf(exgbill.getVersion())), commInfo
									.getCountry());
			if (existcommInfo == null) {
				EmsHeadH2kVersion versionObj = ((EncDao) this.getBaseEncDao())
						.findEmsHeadH2kVersion(exgbill.getSeqNum(),
								exgbill.getVersion());
				commInfo.setBaseCustomsDeclaration(customsDeclaration);
				commInfo.setSerialNumber(this
						.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));
				commInfo.setCompany(CommonUtils.getCompany());
				commInfo.setCommName(exgbill.getName());
				commInfo.setCommSpec(exgbill.getSpec());
				commInfo.setCommSerialNo(exgbill.getSeqNum());
				commInfo.setComplex(exgbill.getComplex());
				Double declareprice = CommonUtils.getEmsExgPrice(versionObj);
				commInfo.setCommUnitPrice(declareprice);
				double price = 0;
				if (declareprice != null) {
					price = declareprice;
				}
				commInfo.setCommTotalPrice(forNum(price
						* commInfo.getCommAmount()));
				commInfo.setUnit(exgbill.getUnit());
				commInfo.setLegalUnit(exgbill.getComplex().getFirstUnit());
				commInfo.setSecondLegalUnit(exgbill.getComplex()
						.getSecondUnit());

				if (customsDeclaration.getCustomer() != null) {
					commInfo.setCountry(customsDeclaration.getCustomer()
							.getCountry2());
				}
				if (other != null) {
					commInfo.setUses(other.getUses());
					commInfo.setLevyMode(other.getLevyMode());
				}
				commInfo.setVersion(String.valueOf(exgbill.getVersion()));// 版本号

				commInfo.setAddType(AddType.INNER);
				((EncDao) this.getBaseEncDao())
						.saveCustomsDeclarationCommInfo(commInfo);
				return commInfo;
			} else {
				existcommInfo.setCommAmount(formatD(existcommInfo
						.getCommAmount()) + formatD(commInfo.getCommAmount()));
				existcommInfo
						.setNetWeight(formatD(existcommInfo.getNetWeight())
								+ formatD(commInfo.getNetWeight()));
				existcommInfo
						.setGrossWeight(formatD(existcommInfo.getGrossWeight())
								+ formatD(commInfo.getGrossWeight()));
				existcommInfo
						.setFirstAmount(formatD(existcommInfo.getFirstAmount())
								+ formatD(commInfo.getFirstAmount()));
				existcommInfo.setSecondAmount(formatD(existcommInfo
						.getSecondAmount())
						+ formatD(commInfo.getSecondAmount()));
				existcommInfo.setPieces(existcommInfo.getPieces() == null ? 0
						: existcommInfo.getPieces()
								+ (commInfo.getPieces() == null ? 0 : commInfo
										.getPieces()));

				double price = 0;
				if (existcommInfo.getCommUnitPrice() != null) {
					price = existcommInfo.getCommUnitPrice().doubleValue();
				}
				existcommInfo.setCommTotalPrice(forNum(price
						* existcommInfo.getCommAmount()));
				((EncDao) this.getBaseEncDao())
						.saveCustomsDeclarationCommInfo(existcommInfo);
				return existcommInfo;
			}
		} else { // 进口
			BaseCustomsDeclarationCommInfo existcommInfo = ((EncDao) this
					.getBaseEncDao())
					.findBaseCustomsDeclarationCommInfoBySequm(
							imgbill.getSeqNum(), customsDeclaration, null,
							commInfo.getCountry());
			if (existcommInfo == null) {
				commInfo.setBaseCustomsDeclaration(customsDeclaration);
				commInfo.setSerialNumber(this
						.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));
				commInfo.setCompany(CommonUtils.getCompany());

				commInfo.setCommName(imgbill.getName());
				commInfo.setDetailNote(imgbill.getDetailNote()); // 详细型号规格
				commInfo.setCommSpec(imgbill.getSpec());
				commInfo.setCommSerialNo(imgbill.getSeqNum());
				commInfo.setComplex(imgbill.getComplex());
				Double declaraprice = CommonUtils.getEmsImgPrice(imgbill);
				commInfo.setCommUnitPrice(declaraprice);
				double price = 0;
				if (declaraprice != null) {
					price = declaraprice;
				}
				commInfo.setCommTotalPrice(forNum(price
						* commInfo.getCommAmount()));
				commInfo.setUnit(imgbill.getUnit());
				commInfo.setLegalUnit(imgbill.getComplex().getFirstUnit());
				commInfo.setSecondLegalUnit(imgbill.getComplex()
						.getSecondUnit());

				if (customsDeclaration.getCustomer() != null) {
					commInfo.setCountry(customsDeclaration.getCustomer()
							.getCountry2());
				}
				if (other != null) {
					commInfo.setUses(other.getUses());
					commInfo.setLevyMode(other.getLevyMode());
				}
				commInfo.setAddType(AddType.INNER);
				((EncDao) this.getBaseEncDao())
						.saveCustomsDeclarationCommInfo(commInfo);
				return commInfo;
			} else {
				existcommInfo.setCommAmount(formatD(existcommInfo
						.getCommAmount()) + formatD(commInfo.getCommAmount()));
				existcommInfo
						.setNetWeight(formatD(existcommInfo.getNetWeight())
								+ formatD(commInfo.getNetWeight()));
				existcommInfo
						.setGrossWeight(formatD(existcommInfo.getGrossWeight())
								+ formatD(commInfo.getGrossWeight()));
				existcommInfo
						.setFirstAmount(formatD(existcommInfo.getFirstAmount())
								+ formatD(commInfo.getFirstAmount()));
				existcommInfo.setSecondAmount(formatD(existcommInfo
						.getSecondAmount())
						+ formatD(commInfo.getSecondAmount()));
				existcommInfo.setPieces(existcommInfo.getPieces() == null ? 0
						: existcommInfo.getPieces()
								+ (commInfo.getPieces() == null ? 0 : commInfo
										.getPieces()));

				double price = 0;
				if (existcommInfo.getCommUnitPrice() != null) {
					price = existcommInfo.getCommUnitPrice().doubleValue();
				}
				existcommInfo.setCommTotalPrice(forNum(price
						* existcommInfo.getCommAmount()));
				((EncDao) this.getBaseEncDao())
						.saveCustomsDeclarationCommInfo(existcommInfo);
				return existcommInfo;
			}
		}

	}

	/**
	 * 数量取整
	 * 
	 * @param list
	 *            报关单商品信息
	 */
	public void customsInfoForMatInt(List list) {
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
					.get(i);
			commInfo.setFirstAmount(forInterNum(commInfo.getFirstAmount()));
			commInfo.setSecondAmount(forInterNum(commInfo.getSecondAmount()));
			commInfo.setCommAmount(forInterNum(commInfo.getCommAmount()));
			commInfo.setNetWeight(forInterNum(commInfo.getNetWeight()));
			commInfo.setGrossWeight(forInterNum(commInfo.getGrossWeight()));

			double price = 0;
			if (commInfo.getCommUnitPrice() != null) {
				price = commInfo.getCommUnitPrice().doubleValue();
			}
			commInfo.setCommTotalPrice(forNum(price
					* (commInfo.getCommAmount() == null ? 0.0 : commInfo
							.getCommAmount())));
			((EncDao) this.getBaseEncDao())
					.saveCustomsDeclarationCommInfo(commInfo);
		}
	}

	/**
	 * 把Integer型转换为Double型
	 * 
	 * @param shuliang
	 *            要转换为Double型的数
	 * @return 转换为Double的Integer数据
	 */
	private Double forInterNum(Double shuliang) {
		if (shuliang == null) {
			return shuliang;
		}
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(0, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}

	/**
	 * 格式化小数位
	 * 
	 * @param shuliang
	 *            要转化的数量
	 * @return 要格式化的数保留6位小数
	 */
	/*
	 * private Double forNum(double shuliang) { BigDecimal bd = new
	 * BigDecimal(shuliang); String totalshuliang = bd.setScale(6,
	 * BigDecimal.ROUND_HALF_UP) .toString(); return
	 * Double.valueOf(totalshuliang); }
	 */

	public Double forNum(double unroundedValue) {
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
		return new Double(total);
	}

	/**
	 * 帐册总体进出金额状况统计表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内帐册总体进出金额
	 */
	public List jisuanEmsImpExpMoneyTotal(Date beginDate, Date endDate,
			int isEffect) {
		List list = new ArrayList();
		BillTemp bill = new BillTemp();
		EmsHeadH2k h = ((EncDao) this.getBaseEncDao()).getEmsNo();
		if (h != null) {
			String emsNo = h.getEmsNo();
			Double money = h.getMaxTurnMoney();
			bill.setBill1(emsNo);// 帐册号
			bill.setBillSum1(fd(money) * 10000);// 周转金额
			// 总进口量 = 料件进口+转厂进口-退料出口
			Double lj = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.DIRECT_IMPORT, beginDate, endDate, isEffect,
					null, null, true, false, null);
			Double zj = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.TRANSFER_FACTORY_IMPORT, beginDate, endDate,
					isEffect, null, null, true, false, null);
			Double tj = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.BACK_MATERIEL_EXPORT, beginDate, endDate,
					isEffect, null, null, true, false, null);

			Double yljk = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.REMAIN_FORWARD_IMPORT, beginDate, endDate,
					isEffect, null, null, true, false, null);
			Double ylzc = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.REMAIN_FORWARD_EXPORT, beginDate, endDate,
					isEffect, null, null, true, false, null);
			Double z = lj + zj + yljk - tj - ylzc;
			bill.setBillSum2(z);
			// 总出口 = 成出 + 转长 +返工副出 - 退厂返工
			Double cc = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.DIRECT_EXPORT, beginDate, endDate, isEffect,
					null, null, true, false, null);
			Double zc = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.TRANSFER_FACTORY_EXPORT, beginDate, endDate,
					isEffect, null, null, true, false, null);
			Double fg = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.REWORK_EXPORT, beginDate, endDate, isEffect,
					null, null, true, false, null);
			Double tc = ((EncDao) this.getBaseEncDao()).getSumMoney(
					ImpExpType.BACK_FACTORY_REWORK, beginDate, endDate,
					isEffect, null, null, true, false, null);
			Double z1 = cc + zc + fg - tc;
			bill.setBillSum3(z1);
			// 折料
			// Double zhesuan = getZheSuanLj(h, beginDate, endDate, isEffect);

			Double zhesuan = getTotalLJUseNum(h, beginDate, endDate);
			bill.setBillSum4(zhesuan);
			bill.setBillSum5(z - fd(zhesuan));
			list.add(bill);
		}
		return list;
	}

	private Double getTotalLJUseNum(EmsHeadH2k h, Date beginDate, Date endDate) {
		findLjUseNum(hs, h, beginDate, endDate, 0, true, false, null, null);// 所有料件耗用量

		Double total = Double.valueOf(0);
		Enumeration e = hs.keys();
		while (e.hasMoreElements()) {
			Object key = e.nextElement();
			TempDD objhs = (TempDD) hs.get(key);
			Double ljNum = objhs.getAa();
			Double averprice = ((EncDao) this.getBaseEncDao())
					.getImgAveragePrice(Integer.valueOf(key.toString()), null,
							endDate, new Boolean(true), false, true, false,
							null); // 报关申报平均单价
			total = total + (ljNum * averprice);
		}
		return total;
	}

	/**
	 * 进口料件单重差异稽核表
	 * 
	 * @param list
	 *            进出口申请单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内进口料件单重差异
	 */
	public List jisuanImpUnitWeightCheck(List list, Date beginDate, Date endDate) {
		List billList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ImpExpRequestBill head = (ImpExpRequestBill) list.get(i);
			List infoList = ((EncDao) this.getBaseEncDao())
					.findImpExpCommodityInfo(head.getId());
			for (int j = 0; j < infoList.size(); j++) {
				BillTemp bill = new BillTemp();
				ImpExpCommodityInfo obj = (ImpExpCommodityInfo) infoList.get(j);
				if (obj.getMateriel() == null) {
					continue;
				}
				AtcMergeBeforeComInfo b = ((EncDao) this.getBaseEncDao())
						.getAtcMergeBeforeComInfoByMaterielInfo(obj);
				if (b == null || b.getAfterComInfo() == null) {
					continue;
				}
				CustomsDeclarationCommInfo info = ((EncDao) this
						.getBaseEncDao()).getCustomsDeclarationCommInfoByBefo(
						b.getAfterComInfo(), beginDate, endDate);
				if (info == null) {
					continue;
				}
				if (info.getBaseCustomsDeclaration().getTradeMode() == null) {
					continue;
				}
				if (!info.getBaseCustomsDeclaration().getTradeMode().getName()
						.equals("进料对口")) {
					continue;
				}

				bill.setBill1(obj.getMateriel().getPtNo()); // 料号
				bill.setBill2(obj.getMateriel().getFactoryName());// 名称
				bill.setBill3(obj.getMateriel().getFactorySpec());// 规格
				bill.setBill4(obj.getMateriel().getCalUnit() == null ? "" : obj
						.getMateriel().getCalUnit().getName());// 单位
				bill.setBill5(info.getCommName());
				bill.setBill6(dateToDate(info.getBaseCustomsDeclaration()
						.getDeclarationDate()));
				bill.setBill7(info.getBaseCustomsDeclaration()
						.getWlserialNumber());
				bill.setBill8(info.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode());
				bill.setBill9(obj.getImpExpRequestBill().getBillNo());//
				bill.setBillSum1(info.getCommAmount());
				bill.setBillSum2(info.getNetWeight());
				bill.setBillSum3(obj.getMateriel().getPtNetWeight());
				bill.setBillSum4(fd(bill.getBillSum3())
						- fd(bill.getBillSum2()));
				bill.setBillSum5(info.getGrossWeight());
				bill.setBillSum6(obj.getMateriel().getPtOutWeight());
				bill.setBillSum7(fd(bill.getBillSum6())
						- fd(bill.getBillSum5()));
				bill.setBillSum8(fd(bill.getBillSum1())
						* fd(bill.getBillSum4()));
				bill.setBillSum9(fd(bill.getBillSum1())
						* fd(bill.getBillSum7()));
				billList.add(bill);
			}
		}

		return billList;
	}

	public Double formatD(Double x) {
		if (x == null) {
			return Double.valueOf(0);
		}
		return x;
	}

	// // 转清单
	// public void makeBillList(ApplyToCustomsBillList billList, List
	// impexpbills,
	// List afterinfoLists, int billListcount) {
	//
	// List headList = new ArrayList();
	// Hashtable beforeHs = new Hashtable(); // 准备生成报关清单归并前商品信息 distinct
	// int z = 0;
	// int n = 0;
	// String maxNo = "";
	// String billNo = "";
	// System.out.println("归并前商品信息：" + impexpbills.size());
	// System.out.println("归并后商品信息: " + afterinfoLists.size());
	// ApplyToCustomsBillList billHead = null;
	// for (int y = 0; y < afterinfoLists.size(); y++) {
	// TempCustomsList tempcustoms = (TempCustomsList) afterinfoLists
	// .get(y);
	// AtcMergeAfterComInfo afterinfo = tempcustoms.getAfterinfo();
	// List beforeList = tempcustoms.getList();
	// System.out.println("归并后名称：" + afterinfo.getEmsSerialNo());
	// System.out.println("归并前:" + beforeList.size());
	// if (((y + billListcount) % billListcount == 0)) {
	// try {
	// if (y < billListcount && billList.getId() != null) {
	// billHead = billList;// 此时为追加到现有清单中
	// } else {
	// billHead = (ApplyToCustomsBillList) BeanUtils
	// .cloneBean(billList);
	// billHead.setId(null);
	// maxNo = ((EncDao) this.getBaseEncDao())
	// .getApplyToCustomsBillListMaxNo();
	// billHead.setListNo(maxNo);
	// }
	// billHead = ((EncDao) this.getBaseEncDao())
	// .saveBillList(billHead);
	// headList.add(billHead);
	// } catch (Exception e) {
	// throw new RuntimeException(e.getMessage());
	// }
	// }
	// afterinfo.setBillList(billHead);
	// afterinfo = ((EncDao) this.getBaseEncDao())
	// .saveAtcMergeAfterComInfo1(afterinfo);
	// for (int i = 0; i < beforeList.size(); i++) {
	// AtcMergeBeforeComInfo beforeinfo = (AtcMergeBeforeComInfo) beforeList
	// .get(i);
	// beforeinfo.setAfterComInfo(afterinfo);// 归并前 set 归并后
	// if (!billNo.equals(maxNo)) {
	// n = 1;
	// billNo = maxNo;
	// }
	// beforeinfo.setSerialNo(n);
	// /**
	// * 明门特殊处理
	// */
	// if (CommonUtils.isCompany("明门幼童")
	// || CommonUtils.isCompany("宝钜儿童用品")) {
	// beforeinfo = getSpecalDeal(beforeinfo);
	// }
	// /**
	// * 康舒特殊处理
	// */
	// if (CommonUtils.isCompany("康舒电子")) {
	// beforeinfo = getSpecalDealKs(beforeinfo);
	// }
	// /**
	// * 通用：计算第一法定数量，第二法定数量
	// */
	// beforeinfo = getFistSecondUnitAmount(beforeinfo);
	// ((EncDao) this.getBaseEncDao())
	// .saveAtcMergeBeforeComInfo(beforeinfo);
	// String ptNo = beforeinfo.getMateriel().getPtNo()
	// + "/"
	// + (beforeinfo.getSalesCountry() == null ? ""
	// : beforeinfo.getSalesCountry().getName());
	// if (beforeHs.get(ptNo) == null) {
	// beforeHs.put(ptNo, beforeinfo);
	// }
	// n++;
	// }
	// }
	// // 回写申请单体是否已转报关清单中
	// List makeApplyToCustomsList = new ArrayList();
	// for (int t = 0; t < impexpbills.size(); t++) {
	// TempImpExpCommodityInfo tt = (TempImpExpCommodityInfo) impexpbills
	// .get(t);
	// String ptpart = tt.getImpExpCommodityInfo().getMateriel().getPtNo()
	// + "/"
	// + (tt.getImpExpCommodityInfo().getCountry() == null ? ""
	// : tt.getImpExpCommodityInfo().getCountry()
	// .getName());
	// if (beforeHs.get(ptpart) != null) {
	// ImpExpCommodityInfo iecomm = tt.getImpExpCommodityInfo();
	// iecomm.setIsTransferCustomsBill(new Boolean(true)); // 已转报关清单
	// ((EncDao) this.getBaseEncDao()).savaimpExpCommodityInfo(iecomm);
	// // 回写申请单转清单中间表信息
	// MakeApplyToCustoms m = new MakeApplyToCustoms();
	// m.setAtcMergeBeforeComInfo((AtcMergeBeforeComInfo) beforeHs
	// .get(ptpart));
	// m.setImpExpCommodityInfo(tt.getImpExpCommodityInfo());
	// m.setCompany(CommonUtils.getCompany());
	// makeApplyToCustomsList.add(m);
	// }
	// }
	// ((EncDao) this.getBaseEncDao())
	// .saveMakeApplyToCustoms(makeApplyToCustomsList);
	//
	// // 修改表头清单数量
	// for (int k = 0; k < headList.size(); k++) {
	// ApplyToCustomsBillList head = (ApplyToCustomsBillList) headList
	// .get(k);
	// List beforesize = ((EncDao) this.getBaseEncDao())
	// .findAtcMergeBeforeComInfoByListID(head);
	// int size = beforesize == null ? 0 : beforesize.size();
	// head.setMaterialNum(size);
	// ((EncDao) this.getBaseEncDao()).saveBillList(head);
	// }
	// }

	// 作转换特殊处理（明门特殊处理）
	private AtcMergeBeforeComInfo getSpecalDeal(AtcMergeBeforeComInfo before) {
		AtcMergeAfterComInfo after = before.getAfterComInfo();
		Double amount = Double.valueOf(0); // 申报数量
		Double firstamount = Double.valueOf(0); // 第一法定数量
		Double secondamount = Double.valueOf(0);// 第二法定数量
		Double grossWeight = Double.valueOf(0); // 毛重
		Double netWeight = Double.valueOf(0); // 净重
		if (after.getBillList().getImpExpFlag()
				.equals(Integer.valueOf(ImpExpFlag.IMPORT))) {// 进口报关单
			grossWeight = before.getGrossWeight();
			netWeight = before.getNetWeight();
			if (after.getUnit() != null
					&& (after.getUnit().getName().equals("千克") || after
							.getUnit().getName().equals("公斤"))) {
				amount = (before.getNetWeight() == null ? 0.0 : before
						.getNetWeight());// /注：明门提出(数量=净重(此净重为总净重))
			} else {
				amount = (before.getDeclaredAmount() == null ? 0.0 : before
						.getDeclaredAmount());
			}
			if (after.getComplex().getFirstUnit() != null
					&& after.getComplex().getFirstUnit().getName().trim()
							.equals("米")) {
				firstamount = ((before.getDeclaredAmount() * 0.9144));
			} else if (after.getComplex().getFirstUnit() != null
					&& (after.getComplex().getFirstUnit().getName().trim()
							.equals("千克") || after.getComplex().getFirstUnit()
							.getName().trim().equals("公斤"))) {
				firstamount = ((amount));
			}
			if (after.getComplex().getSecondUnit() != null
					&& after.getComplex().getSecondUnit().getName().trim()
							.equals("米")) {
				secondamount = ((before.getDeclaredAmount() * 0.9144));
			} else if (after.getComplex().getSecondUnit() != null
					&& (after.getComplex().getSecondUnit().getName().trim()
							.equals("千克") || after.getComplex().getSecondUnit()
							.getName().trim().equals("公斤"))) {
				secondamount = ((amount));
			}

		} else {// 出口
			Double netWeightParameter = Double.valueOf(0);
			CompanyOther other = CommonUtils.getOther();
			if (other != null) {
				netWeightParameter = (other.getWeightPara() == null) ? Double
						.valueOf(0) : other.getWeightPara();
			}

			Integer version = before.getVersion();
			EmsHeadH2kVersion versionObj = ((EncDao) this.getBaseEncDao())
					.findEmsHeadH2kVersion(
							Integer.valueOf(after.getEmsSerialNo()), version);

			if (after.getUnit() != null
					&& (after.getUnit().getName().equals("千克") || after
							.getUnit().getName().equals("公斤"))) {

				amount = (before.getNetWeight() == null ? 0.0 // mergeAfterComInfo:由pK单汇总得到申报数量
						: before.getNetWeight()) * netWeightParameter;
				//
				// 合同比例(合同单位毛重/合同单位净重)
				//
				double contractRate = 0.0;
				double _grossWeight = versionObj.getUnitGrossWeight() == null ? 0.0
						: versionObj.getUnitGrossWeight();
				double _netWeight = versionObj.getUnitNetWeight() == null ? 0.0
						: versionObj.getUnitNetWeight();
				if (_grossWeight != 0.0 || _netWeight != 0.0) {
					contractRate = _grossWeight / _netWeight;
				}

				netWeight = (before.getNetWeight() == null ? 0.0 : before
						.getNetWeight()) * netWeightParameter;
				// 申报单位净重 = 申报数量 * ERP净重 * 重量比例
				// 申报单位毛重 = 申报单位净重 * 合同比例(合同单位毛重/合同单位净重)
				grossWeight = netWeight * contractRate;

				// 第一法定单位
				if (after.getComplex().getFirstUnit() != null) {
					if (after.getComplex().getFirstUnit().getName().trim()
							.equals("个")
							|| after.getComplex().getFirstUnit().getName()
									.trim().equals("辆")
							|| after.getComplex().getFirstUnit().getName()
									.trim().equals("件")) {
						firstamount = Double
								.valueOf(before.getPiece() == null ? 0 : before
										.getPiece());

					} else if (after.getComplex().getFirstUnit().getName()
							.trim().equals("千克")
							|| after.getComplex().getFirstUnit().getName()
									.trim().equals("公斤")) {
						firstamount = netWeight;
					}
				}
				// 第二法定单位
				if (after.getComplex().getSecondUnit() != null) {
					if (after.getComplex().getSecondUnit().getName().trim()
							.equals("个")
							|| after.getComplex().getSecondUnit().getName()
									.trim().equals("辆")
							|| after.getComplex().getSecondUnit().getName()
									.trim().equals("件")) {
						secondamount = Double
								.valueOf(before.getPiece() == null ? 0 : before
										.getPiece());
					} else if (after.getComplex().getSecondUnit().getName()
							.trim().equals("千克")
							|| after.getComplex().getSecondUnit().getName()
									.trim().equals("公斤")) {
						secondamount = netWeight;
					}
				}
			} else { // 件,个

				amount = before.getDeclaredAmount() == null ? 0.0 : before
						.getDeclaredAmount();
				grossWeight = amount
						* (versionObj.getUnitGrossWeight() == null ? 0.0
								: versionObj.getUnitGrossWeight());
				netWeight = amount
						* (versionObj.getUnitNetWeight() == null ? 0.0
								: versionObj.getUnitNetWeight());
				// 第一法定单位
				if (after.getComplex().getFirstUnit() != null) {
					if (after.getComplex().getFirstUnit().getName().trim()
							.equals("个")
							|| after.getComplex().getFirstUnit().getName()
									.trim().equals("辆")
							|| after.getComplex().getFirstUnit().getName()
									.trim().equals("件")) {
						firstamount = amount;
					}
				}
				// 第二法定单位
				if (after.getComplex().getSecondUnit() != null
						&& (after.getComplex().getSecondUnit().getName().trim()
								.equals("千克") || after.getComplex()
								.getSecondUnit().getName().trim().equals("公斤"))) {
					secondamount = netWeight;
				}
			}
		}
		before.setDeclaredAmount(forInterNum(amount));
		before.setLegalAmount(forInterNum(firstamount));// 第一法定数量
		before.setSecondLegalAmount(forInterNum(secondamount)); // 第二法定数量
		before.setGrossWeight(forInterNum(grossWeight));
		before.setNetWeight(forInterNum(netWeight));

		return before;
	}

	// 作转换特殊处理
	private AtcMergeBeforeComInfo getSpecalDealKs(AtcMergeBeforeComInfo before) {
		AtcMergeAfterComInfo after = before.getAfterComInfo();
		Double firstamount = Double.valueOf(0); // 第一法定数量
		Double secondamount = Double.valueOf(0);// 第二法定数量
		Double netWeight = (before.getNetWeight() == null ? 0.0 : before
				.getNetWeight());
		if (after.getComplex().getFirstUnit() != null
				&& (after.getComplex().getFirstUnit().getName().trim()
						.equals("千克") || after.getComplex().getFirstUnit()
						.getName().trim().equals("公斤"))) {
			firstamount = netWeight;
		} else {
			firstamount = before.getLegalAmount() == null ? 0.0 : before
					.getLegalAmount();
		}
		if (after.getComplex().getSecondUnit() != null
				&& (after.getComplex().getSecondUnit().getName().trim()
						.equals("千克") || after.getComplex().getSecondUnit()
						.getName().trim().equals("公斤"))) {
			secondamount = netWeight;
		} else {
			secondamount = before.getSecondLegalAmount() == null ? 0.0 : before
					.getSecondLegalAmount();
		}
		before.setLegalAmount(firstamount);
		System.out.println("firstamount=" + firstamount);
		before.setSecondLegalAmount(secondamount);
		return before;
	}

	// --通过事业部过滤
	public void findLjUseNum(Map hs, EmsHeadH2k head, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration, boolean isdept,
			String deptCode, Integer imgSeqNum) {
		Map<String, List<Object[]>> mapBom = new HashMap<String, List<Object[]>>();
		hs.clear();
		List listBomInfo = ((EncDao) this.getBaseEncDao())
				.findEmsHeadH2kBomUnitWear(head, imgSeqNum);
		for (int i = 0; i < listBomInfo.size(); i++) {
			Object[] objs = (Object[]) listBomInfo.get(i);
			String cpNo = (objs[0] == null ? "" : objs[0].toString());
			String versionNo = (objs[1] == null ? "" : objs[1].toString());
			String key = cpNo + "/" + versionNo;
			List<Object[]> listLj = mapBom.get(key);
			if (listLj == null) {
				listLj = new ArrayList();
				mapBom.put(key, listLj);
			}
			listLj.add(objs);
		}
		List listAmount = ((EncDao) this.getBaseEncDao())
				.findCustomsDeclarationCommInfoAmount(head, beginDate, endDate,
						isEffect, isDeclaration, isdept, deptCode);
		for (int i = 0; i < listAmount.size(); i++) {
			Object[] objs = (Object[]) listAmount.get(i);
			Integer impExpType = (objs[0] == null ? null : Integer
					.valueOf(objs[0].toString()));
			String cpNo = (objs[1] == null ? "" : objs[1].toString());
			String versionNo = (objs[2] == null ? "" : objs[2].toString()
					.trim());
			String key = cpNo + "/" + versionNo;
			Double amount = (objs[3] == null ? 0.0 : Double.valueOf(objs[3]
					.toString()));
			List<Object[]> listBom = mapBom.get(key);
			if (listBom == null || listBom.size() <= 0) {
				continue;
			}
			Double fgfc = 0.0;// 近工复出
			Double zcck = 0.0;// 转厂出口
			Double zjck = 0.0;// 直接出口
			Double tcfg = 0.0;// 退厂返工
			if (ImpExpType.REWORK_EXPORT == impExpType) {// 返工复出
				fgfc = amount;
			} else if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType) {// 转厂出口
				zcck = amount;
			} else if (ImpExpType.DIRECT_EXPORT == impExpType) {// 直接出口
				zjck = amount;
			} else if (ImpExpType.BACK_FACTORY_REWORK == impExpType) {// 退厂返工
				tcfg = amount;
			}
			for (int j = 0; j < listBom.size(); j++) {
				Object[] objBom = (Object[]) listBom.get(j);
				String ljNo = String.valueOf(objBom[2]);
				Double unitWear = fd((Double) objBom[3]);
				Double wear = fd((Double) objBom[4]);
				Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量
				//
				Double useNum = fd(fgfc + zcck + zjck - tcfg);
				Double ljUseNum = useNum * unitUse;// 成品耗用量
				Double zchy = zcck * unitUse;// 转厂耗用量
				Double zjckhy = zjck * unitUse;// 直接出口耗用量
				Double bjlNum = ljUseNum * (wear * 0.01);// 边角料
				Double fgfcNum = fgfc * unitUse;
				Double tcfgNum = tcfg * unitUse;
				TempDD temp = null;
				if (hs.get(ljNo) == null) {
					temp = new TempDD();
					temp.setLjseqnum(ljNo);
					temp.setAa(ljUseNum);
					temp.setBb(bjlNum);
					temp.setZchy(zchy);
					temp.setZjckhy(zjckhy);
					temp.setTcfghy(tcfgNum);
					temp.setFgfchy(fgfcNum);
					hs.put(ljNo, temp);
				} else {
					temp = (TempDD) hs.get(ljNo);
					temp.setAa(fd(temp.getAa()) + ljUseNum);
					temp.setBb(fd(temp.getBb()) + bjlNum);
					temp.setZchy(fd(temp.getZchy()) + zchy);
					temp.setZjckhy(fd(temp.getZjckhy()) + zjckhy);
					temp.setTcfghy(fd(temp.getTcfghy()) + tcfgNum);
					temp.setFgfchy(fd(temp.getFgfchy()) + fgfcNum);
				}
			}
		}
	}

	/**
	 * 计算成品耗用的料件数
	 * 
	 * @param hs
	 * @param head
	 * @param beginDate
	 * @param endDate
	 * @param isEffect
	 * @param isDeclaration
	 * @param isdept
	 * @param deptCode
	 * @param imgSeqNum
	 */
	public void calculateMaterialUseNum(Map<String, TempDD> hs,
			EmsHeadH2k head, Date beginDate, Date endDate, int isEffect,
			boolean isDeclaration, boolean isdept, String deptCode,
			Integer imgSeqNum) {
		Map<String, List<Object[]>> bomMap = new HashMap<String, List<Object[]>>();
		hs.clear();
		List<Object[]> bomList = encDao.findEmsHeadH2kBomUnitWear(head,
				imgSeqNum);
		Object[] objs = null;
		String seqNum = null;
		String version = null;
		String key = null;
		List<Object[]> boms = null;
		for (int i = 0; i < bomList.size(); i++) {
			objs = bomList.get(i);
			seqNum = (objs[0] == null ? "" : objs[0].toString());
			version = (objs[1] == null ? "" : objs[1].toString());
			key = seqNum + "/" + version;
			boms = bomMap.get(key);
			if (boms == null) {
				boms = new ArrayList();
				bomMap.put(key, boms);
			}
			boms.add(objs);
		}

		List listAmount = encDao.findCustomsDeclarationCommInfoAmount(head,
				beginDate, endDate, isEffect, isDeclaration, isdept, deptCode);

		Double amount = null;
		Integer impExpType = null;
		for (int i = 0; i < listAmount.size(); i++) {
			objs = (Object[]) listAmount.get(i);
			impExpType = (objs[0] == null ? null : Integer.valueOf(objs[0]
					.toString()));
			seqNum = (objs[1] == null ? "" : objs[1].toString());
			version = (objs[2] == null ? "" : objs[2].toString().trim());

			key = seqNum + "/" + version;
			amount = (objs[3] == null ? 0.0 : Double
					.valueOf(objs[3].toString()));

			List<Object[]> listBom = bomMap.get(key);
			if (listBom == null || listBom.size() <= 0) {
				continue;
			}

			Double fgfc = 0.0;// 近工复出
			Double zcck = 0.0;// 转厂出口
			Double zjck = 0.0;// 直接出口
			Double tcfg = 0.0;// 退厂返工
			if (ImpExpType.REWORK_EXPORT == impExpType) {// 返工复出
				fgfc = amount;
			} else if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType) {// 转厂出口
				zcck = amount;
			} else if (ImpExpType.DIRECT_EXPORT == impExpType) {// 直接出口
				zjck = amount;
			} else if (ImpExpType.BACK_FACTORY_REWORK == impExpType) {// 退厂返工
				tcfg = amount;
			}
			for (int j = 0; j < listBom.size(); j++) {
				Object[] objBom = (Object[]) listBom.get(j);
				String ljNo = String.valueOf(objBom[2]);
				Double unitWear = fd((Double) objBom[3]);
				Double wear = fd((Double) objBom[4]);
				Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量
				//
				Double useNum = fd(fgfc + zcck + zjck - tcfg);
				Double ljUseNum = useNum * unitUse;// 成品耗用量
				Double zchy = zcck * unitUse;// 转厂耗用量
				Double zjckhy = zjck * unitUse;// 直接出口耗用量
				Double bjlNum = ljUseNum * (wear * 0.01);// 边角料
				Double fgfcNum = fgfc * unitUse;
				Double tcfgNum = tcfg * unitUse;
				TempDD temp = null;
				if (hs.get(ljNo) == null) {
					temp = new TempDD();
					temp.setLjseqnum(ljNo);
					temp.setAa(ljUseNum);
					temp.setBb(bjlNum);
					temp.setZchy(zchy);
					temp.setZjckhy(zjckhy);
					temp.setTcfghy(tcfgNum);
					temp.setFgfchy(fgfcNum);
					hs.put(ljNo, temp);
				} else {
					temp = hs.get(ljNo);
					temp.setAa(fd(temp.getAa()) + ljUseNum);
					temp.setBb(fd(temp.getBb()) + bjlNum);
					temp.setZchy(fd(temp.getZchy()) + zchy);
					temp.setZjckhy(fd(temp.getZjckhy()) + zjckhy);
					temp.setTcfghy(fd(temp.getTcfghy()) + tcfgNum);
					temp.setFgfchy(fd(temp.getFgfchy()) + fgfcNum);
				}
			}
		}
	}

	// // --通过事业部过滤
	// public void findLjUseNum(Hashtable hs, EmsHeadH2k head, Date beginDate,
	// Date endDate, int isEffect, boolean isDeclaration, boolean isdept,
	// String deptCode, Integer imgSeqNum) {
	// hs.clear();
	// // long begintime=System.currentTimeMillis();
	// List list = ((EncDao) this.getBaseEncDao()).finLjUseNum(head,
	// beginDate, endDate, isEffect, isDeclaration, isdept, deptCode,
	// imgSeqNum);
	// // long endtime=System.currentTimeMillis();
	// // System.out.println("------------time1:"+(endtime-begintime)/1000.0);
	// for (int i = 0; i < list.size(); i++) {
	// Object[] obj = (Object[]) list.get(i);
	// String ljNo = String.valueOf(obj[2]);
	// Double unitWear = fd((Double) obj[3]);
	// Double wear = fd((Double) obj[4]);
	// Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量
	// Double fgfc = fd((Double) obj[5]);// 近工复出
	// Double zcck = fd((Double) obj[6]);// 转厂出口
	// Double zjck = fd((Double) obj[7]);// 直接出口
	// Double tcfg = fd((Double) obj[8]);// 退厂返工
	//
	// Double useNum = fd(fgfc + zcck + zjck - tcfg);
	// Double ljUseNum = useNum * unitUse;// 成品耗用量
	// Double zchy = zcck * unitUse;// 转厂耗用量
	// Double zjckhy = zjck * unitUse;// 直接出口耗用量
	// Double bjlNum = ljUseNum * (wear * 0.01);// 边角料
	// Double fgfcNum = fgfc * unitUse;
	// Double tcfgNum = tcfg * unitUse;
	// TempDD temp = null;
	// if (hs.get(ljNo) == null) {
	// temp = new TempDD();
	// temp.setLjseqnum(ljNo);
	// temp.setAa(ljUseNum);
	// temp.setBb(bjlNum);
	// temp.setZchy(zchy);
	// temp.setZjckhy(zjckhy);
	// temp.setTcfghy(tcfgNum);
	// temp.setFgfchy(fgfcNum);
	// hs.put(ljNo, temp);
	// } else {
	// temp = (TempDD) hs.get(ljNo);
	// temp.setAa(fd(temp.getAa()) + ljUseNum);
	// temp.setBb(fd(temp.getBb()) + bjlNum);
	// temp.setZchy(fd(temp.getZchy()) + zchy);
	// temp.setZjckhy(fd(temp.getZjckhy()) + zjckhy);
	// temp.setTcfghy(fd(temp.getTcfghy()) + tcfgNum);
	// temp.setFgfchy(fd(temp.getFgfchy()) + fgfcNum);
	// }
	// }
	// }

	public void findLjUseNumForTran(Hashtable hs, EmsHeadH2k head,
			Date beginDate, Date endDate, int isEffect, boolean isDeclaration,
			boolean isdept, String deptCode, Integer imgSeqNum) {
		hs.clear();
		Map<String, List<Object[]>> mapBom = new HashMap<String, List<Object[]>>();
		List listBomInfo = ((EncDao) this.getBaseEncDao())
				.findEmsHeadH2kBomUnitWear(head, imgSeqNum);
		for (int i = 0; i < listBomInfo.size(); i++) {
			Object[] objs = (Object[]) listBomInfo.get(i);
			String cpNo = (objs[0] == null ? "" : objs[0].toString());
			String versionNo = (objs[1] == null ? "" : objs[1].toString());
			String key = cpNo + "/" + versionNo;
			List<Object[]> listLj = mapBom.get(key);
			if (listLj == null) {
				listLj = new ArrayList();
				mapBom.put(key, listLj);
			}
			listLj.add(objs);
		}
		List listAmount = ((EncDao) this.getBaseEncDao())
				.findCustomsDeclarationCommInfoTransAmount(head, beginDate,
						endDate, isEffect, isDeclaration, isdept, deptCode);
		for (int i = 0; i < listAmount.size(); i++) {
			Object[] objs = (Object[]) listAmount.get(i);
			Integer impExpType = (objs[0] == null ? null : Integer
					.valueOf(objs[0].toString()));
			String cpNo = (objs[1] == null ? "" : objs[1].toString());
			String versionNo = (objs[2] == null ? "" : objs[2].toString()
					.trim());
			String key = cpNo + "/" + versionNo;
			Double amount = (objs[3] == null ? 0.0 : Double.valueOf(objs[3]
					.toString()));
			List<Object[]> listBom = mapBom.get(key);
			if (listBom == null || listBom.size() <= 0) {
				continue;
			}
			Double useNum = 0.0;
			Double tranNum = 0.0;
			if (ImpExpType.TRANSFER_FACTORY_EXPORT == impExpType) {// 转厂出口
				tranNum = amount;
			} else if (ImpExpType.DIRECT_EXPORT == impExpType) {// 直接出口
				useNum = amount;
			}
			for (int j = 0; j < listBom.size(); j++) {
				Object[] objBom = (Object[]) listBom.get(j);
				String ljNo = String.valueOf(objBom[2]);
				Double unitWear = fd((Double) objBom[3]);
				Double wear = fd((Double) objBom[4]);
				Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量

				Double ljUseNum = useNum * unitUse;
				Double tranUseNum = tranNum * unitUse;
				TempDD temp = null;
				if (hs.get(ljNo) == null) {
					temp = new TempDD();
					temp.setLjseqnum(ljNo);
					temp.setAa(ljUseNum);
					temp.setBb(tranUseNum);
					hs.put(ljNo, temp);
				} else {
					temp = (TempDD) hs.get(ljNo);
					temp.setAa(fd(temp.getAa()) + ljUseNum);
					temp.setBb(fd(temp.getBb()) + tranUseNum);
				}
			}
		}
	}

	// public void findLjUseNumForTran(Hashtable hs, EmsHeadH2k head,
	// Date beginDate, Date endDate, int isEffect, boolean isDeclaration,
	// boolean isdept, String deptCode, Integer imgSeqNum) {
	// hs.clear();
	// // System.out.println("");
	// long begintime=System.currentTimeMillis();
	// List list = ((EncDao) this.getBaseEncDao()).finLjUseNumForTran(head,
	// beginDate, endDate, isEffect, isDeclaration, isdept, deptCode,
	// imgSeqNum);
	// long endtime=System.currentTimeMillis();
	// System.out.println("------------time2:"+(endtime-begintime)/1000.0);
	// for (int i = 0; i < list.size(); i++) {
	// Object[] obj = (Object[]) list.get(i);
	// String ljNo = String.valueOf(obj[2]);
	// Double unitWear = fd((Double) obj[3]);
	// Double wear = fd((Double) obj[4]);
	// Double unitUse = unitWear / (1 - (wear * 0.01));// 单位用量
	//
	// Double useNum = fd((Double) obj[5]);
	// Double tranNum = fd((Double) obj[6]);
	// Double ljUseNum = useNum * unitUse;
	// Double tranUseNum = tranNum * unitUse;
	// TempDD temp = null;
	// if (hs.get(ljNo) == null) {
	// temp = new TempDD();
	// temp.setLjseqnum(ljNo);
	// temp.setAa(ljUseNum);
	// temp.setBb(tranUseNum);
	// hs.put(ljNo, temp);
	// } else {
	// temp = (TempDD) hs.get(ljNo);
	// temp.setAa(fd(temp.getAa()) + ljUseNum);
	// temp.setBb(fd(temp.getBb()) + tranUseNum);
	// }
	// }
	// }

	public List checkIsNull(ApplyToCustomsBillList head) {
		List list = ((EncDao) this.getBaseEncDao())
				.findAtcMergeBeforeComInfoByListID(head);
		List<String> ls = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String errstr = "";
			AtcMergeBeforeComInfo info = (AtcMergeBeforeComInfo) list.get(i);
			Curr curr = info.getCurrency();
			if (curr == null) {
				errstr += ",币制不可为空";
			}
			Double deamount = info.getDeclaredAmount();
			if (deamount == null || Double.valueOf(0).equals(deamount)) {
				errstr += ",申报数量不可为空";
			}
			Unit firstUnit = info.getAfterComInfo().getComplex().getFirstUnit();
			Double legalAmount = info.getLegalAmount();
			if (firstUnit != null
					&& (legalAmount == null || Double.valueOf(0).equals(
							legalAmount))) {
				errstr += ",法定数量不可为空";
			}
			Unit secondUnit = info.getAfterComInfo().getComplex()
					.getSecondUnit();
			Double SecondLegalAmount = info.getSecondLegalAmount();
			if (secondUnit != null
					&& (SecondLegalAmount == null || Double.valueOf(0).equals(
							SecondLegalAmount))) {
				errstr += ",法定数量二不可为空";
			}
			Double declaredPrice = info.getDeclaredPrice();
			if (declaredPrice == null
					|| Double.valueOf(0).equals(declaredPrice)) {
				errstr += ",申报单价不可为空";
			}
			Country salesCountry = info.getSalesCountry();
			if (salesCountry == null) {
				errstr += ",产销国不可为空";
			}
			Uses usesCode = info.getUsesCode();
			if (usesCode == null) {
				errstr += ",用途代码不可为空";
			}
			LevyMode levyMode = info.getLevyMode();
			if (levyMode == null) {
				errstr += ",征免方式不可为空";
			}
			if (!errstr.equals("")) {
				ls.add("序号：" + String.valueOf(info.getSerialNo()) + errstr);
			}
		}
		return ls;
	}

	@Override
	protected String getQPBGDXmlPath() {
		String dir = this.emsEdiTrDao.getBpara(BcusParameter.LOAD_QP_BGD_DIR);
		return (dir == null ? "" : dir);
	}

	/**
	 * 通过帐册序号返回帐册信息
	 * 
	 * @param seqNum
	 *            序号
	 * @param type
	 *            物料类型
	 * @param emsNo
	 *            手册号
	 * @return 与指定的序号手册号匹配的帐册信息
	 */
	public Map<Integer, String> findImgExgBillBySeqNum(Integer seqNum,
			String materielType, String emsNo) {
		double legalUnitGene = 0.0;
		double legalUnit2Gene = 0.0;
		Map<Integer, String> unitRateMap = new HashMap<Integer, String>();
		List listH2kImgOrExg = ((EncDao) this.getBaseEncDao())
				.findImgExgBillBySeqNum(seqNum, materielType, null);
		// System.out.println("===listH2kImgOrExg="+listH2kImgOrExg.size());
		if (listH2kImgOrExg == null || listH2kImgOrExg.size() <= 0) {
			return unitRateMap;
		}
		if (materielType.equals(MaterielType.MATERIEL)) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) listH2kImgOrExg.get(0);
			legalUnitGene = img.getLegalUnitGene() == null
					|| "".equals(img.getLegalUnitGene()) ? 0.0 : img
					.getLegalUnitGene();
			legalUnit2Gene = img.getLegalUnit2Gene() == null
					|| "".equals(img.getLegalUnit2Gene()) ? 0.0 : img
					.getLegalUnit2Gene();

		} else {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) listH2kImgOrExg.get(0);
			legalUnitGene = exg.getLegalUnitGene() == null
					|| "".equals(exg.getLegalUnitGene()) ? 0.0 : exg
					.getLegalUnitGene();
			legalUnit2Gene = exg.getLegalUnit2Gene() == null
					|| "".equals(exg.getLegalUnit2Gene()) ? 0.0 : exg
					.getLegalUnit2Gene();
		}
		unitRateMap.put(seqNum, legalUnitGene + "/" + legalUnit2Gene);
		return unitRateMap;
	}

	/**
	 * 获得计量单位的比例
	 * 
	 * @return
	 */
	public Map getUnitRateMap() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("克+千克", 0.001);
		map.put("个+千个", 0.001);
		map.put("支+千支", 0.001);
		map.put("块+千块", 0.001);
		map.put("米+千米", 0.001);
		map.put("千克+克", 1000.0);
		map.put("千个+个", 1000.0);
		map.put("千支+支", 1000.0);
		map.put("千块+块", 1000.0);
		map.put("千米+米", 1000.0);
		return map;
	}

	public boolean saveSplitedImpExpRequestBill(ImpExpRequestBill oldBill,
			Set set, List list) {
		Map<ImpExpRequestBill, List> map = new HashMap<ImpExpRequestBill, List>();
		Map<String, ImpExpRequestBill> tmap = new HashMap<String, ImpExpRequestBill>();
		// -----------------------------------------------------------
		try {
			Iterator itor = set.iterator();
			while (itor.hasNext()) {
				ImpExpRequestBill impExpRequestBill = new ImpExpRequestBill();
				String bno = (String) itor.next();
				try {
					PropertyUtils.copyProperties(impExpRequestBill, oldBill);
					impExpRequestBill.setId(null);
					impExpRequestBill.setBillNo(bno);
					map.put(impExpRequestBill, new ArrayList());
					tmap.put(bno, impExpRequestBill);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return false;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					return false;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					return false;
				}
			}
			// 以上生成所有新的申请单------------------------------------------
			for (int i = 0; i < list.size(); i++) {
				TempImpExpCommodityInfo tinfo = (TempImpExpCommodityInfo) list
						.get(i);
				String bno = tinfo.getInImpExpRequestBill();
				List blist = map.get(tmap.get(bno));
				blist.add(tinfo.getImpExpCommodityInfo());
			}
			// 以上把对应表体放入表头------------------------------------------
			String oldBillno = oldBill.getBillNo();
			if (set.contains(oldBillno)) {
				Map<String, ImpExpCommodityInfo> bmap = new HashMap();
				List blist = map.get(tmap.get(oldBillno));
				for (int i = 0; i < blist.size(); i++) {
					ImpExpCommodityInfo binfo = (ImpExpCommodityInfo) blist
							.get(i);
					String key = binfo.getMateriel().getPtNo()
							+ (binfo.getCountry() == null ? "" : binfo
									.getCountry().getCode());
					bmap.put(key, binfo);
				}
				List olist = ((EncDao) this.getBaseEncDao())
						.findImpExpCommodityInfo(oldBill.getId());
				List mlist = new ArrayList();
				for (int i = 0; i < olist.size(); i++) {
					ImpExpCommodityInfo einfo = (ImpExpCommodityInfo) olist
							.get(i);
					String key = einfo.getMateriel().getPtNo()
							+ (einfo.getCountry() == null ? "" : einfo
									.getCountry().getCode());
					if (mlist.contains(key)) {
						continue;
					}
					ImpExpCommodityInfo dinfo = bmap.get(key);
					if (dinfo != null) {
						List infos = ((EncDao) this.getBaseEncDao())
								.findImpExpCommodityInfo(einfo
										.getImpExpRequestBill().getId(), einfo
										.getMateriel().getPtNo(), einfo
										.getCountry() == null ? null : einfo
										.getCountry().getCode());
						if (infos.size() > 1) {
							infos.remove(einfo);
							for (int j = 0; j < infos.size(); j++) {
								ImpExpCommodityInfo inf = (ImpExpCommodityInfo) infos
										.get(j);
								// ((EncDao) this.getBaseEncDao()).
								List klist = new ArrayList();
								klist.add(inf);
								deleteImpExpCommodityInfo(klist);
								oldBill.setItemCount(oldBill.getItemCount() - 1);
							}
							mlist.add(key);
						}
						einfo.setQuantity(dinfo.getQuantity());
						einfo.setAmountPrice(dinfo.getAmountPrice());
						((EncDao) this.getBaseEncDao())
								.savaimpExpCommodityInfo(einfo);
					} else {
						List klist = new ArrayList();
						klist.add(einfo);
						// ((EncDao) this.getBaseEncDao()).
						deleteImpExpCommodityInfo(klist);
						oldBill.setItemCount(oldBill.getItemCount() - 1);
					}
				}
				((EncDao) this.getBaseEncDao()).saveImpExpRequestBill(oldBill);
			} else {
				deleteImpExpRequestBill(oldBill);
			}
			map.remove(tmap.get(oldBillno));// 除去被拆分的清单,因为上面已做了处理
			// 以上具体分析原来申请单是否存在------------------------------------------
			Iterator itr = map.keySet().iterator();
			while (itr.hasNext()) {
				ImpExpRequestBill et = (ImpExpRequestBill) itr.next();
				List infolist = map.get(et);
				et.setItemCount(infolist.size());
				((EncDao) this.getBaseEncDao()).saveImpExpRequestBill(et);
				for (int i = 0; i < infolist.size(); i++) {
					ImpExpCommodityInfo cinfo = (ImpExpCommodityInfo) infolist
							.get(i);
					cinfo.setImpExpRequestBill(et);

					((EncDao) this.getBaseEncDao())
							.savaimpExpCommodityInfo(cinfo);
				}
			}
			// 保存新增加的申请单------------------------------------------
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 更新电子帐册中的申请单物料的版本号，为最大的版本号
	 * 
	 */
	public void updateMaxVersion() {
		// ((EncDao) this.getBaseEncDao()).updateMaxVersion();
		Connection conn = null;
		Statement stmt = null;
		Statement stmt1 = null;
		try {
			conn = dataSource.getConnection();
			String sql1 = "update impexpcommodityinfo set extendmemo1  =  a.hsaftertenmemono "
					+ " from innermergedata a where a.materielid = impexpcommodityinfo.materielid "
					+ " and exists (select 1 from impexprequestbill where billtype in ('2','4','5','7') "
					+ " and impexprequestbill.id = impexpcommodityinfo.impexprequestbillid "
					+ " and impexprequestbill.isCustomsBill='0') "
					+ " and (impexpcommodityinfo.version is null or isnull(impexpcommodityinfo.version,'') = '')";
			stmt = conn.createStatement();
			stmt.execute(sql1);
			stmt.close();

			String sql2 = "update impexpcommodityinfo set  version  =  isNull(b.maxVersion,'0') from "
					+ " (select max(a.version) as maxVersion,c.seqnum from emsheadh2kversion a left join "
					+ " emsheadh2kexg c on c.id = a.emsheadh2kexg where isnull(a.isforbid,'') = '' "
					+ " group by c.seqnum) as b where b.seqnum = impexpcommodityinfo.extendmemo1 "
					+ " and exists (select 1 from impexprequestbill where billtype in ('2','4','5','7') "
					+ " and impexprequestbill.id = impexpcommodityinfo.impexprequestbillid "
					+ " and impexprequestbill.isCustomsBill='0') "
					+ " and (impexpcommodityinfo.version is null or  isnull(impexpcommodityinfo.version,'') = '')";
			stmt1 = conn.createStatement();
			stmt1.execute(sql2);
			stmt1.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新电子帐册中的报关单商品的版本号，为最大的版本号,如果没有最大版本号，默认为0
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 */
	public void updateMaxVersionCustomsDeclarationCommInfo(
			BaseCustomsDeclaration customsDeclaration) {
		List<BaseCustomsDeclarationCommInfo> infoList = new ArrayList<BaseCustomsDeclarationCommInfo>();
		infoList = this.findCustomsDeclarationInfo(customsDeclaration);
		if (infoList.size() != 0) {
			for (BaseCustomsDeclarationCommInfo info : infoList) {
				Integer version = encDao.findMaxVersionCustoms(info);
				if (version != null) {
					info.setVersion(version.toString());
				} else {
					info.setVersion("0");
				}
				encDao.saveOrUpdate(info);
			}
		}
	}

	/**
	 * 获得各个模块的归并资料
	 * 
	 * @param projectType
	 *            模块信息
	 * @return 查询归并中的设备资料
	 */
	public List getInnerDateForSpeFix(int projectType, int index, int length,
			String property, Object value, boolean isLike, boolean isImport) {
		List arrayList = new ArrayList();
		List list = ((EncDao) this.getBaseEncDao()).findFixturInnerDate(
				projectType, index, length, property, value, isLike, isImport);
		System.out.println("list.size()---------------------" + list.size());
		if (list.isEmpty())
			return arrayList;
		for (int i = 0; i < list.size(); i++) {
			TempAllTypeInnerMergeDate tempInnerMergeDate = new TempAllTypeInnerMergeDate();
			Object[] obj = (Object[]) list.get(i);
			Complex complex = obj[0] == null ? null : (Complex) obj[0];
			Integer seqNum = obj[1] == null ? null : (Integer) obj[1];
			String name = obj[2] == null ? null : (String) obj[2];
			String spec = obj[3] == null ? null : (String) obj[3];
			Unit unitName = obj[4] == null ? null : (Unit) obj[4];
			tempInnerMergeDate.setHsAfterComplex(complex);
			tempInnerMergeDate.setHsAfterTenMemoNo(seqNum);
			tempInnerMergeDate.setHsAfterMaterielTenName(name);
			tempInnerMergeDate.setHsAfterMaterielTenSpec(spec);
			tempInnerMergeDate.setHsAfterLegalUnit(unitName);

			// if (projectType == ProjectType.BCUS) {
			// // InnerMergeData innerMergeData = (InnerMergeData) list.get(i);
			//
			//
			//
			// } else if (projectType == ProjectType.BCS) {
			// BcsInnerMerge bcsInnerMerge = (BcsInnerMerge) list.get(i);
			// // tempInnerMergeDate.setBcsTenInnerMerge(innerMergeData
			// // .getBcsTenInnerMerge());
			// BcsTenInnerMerge innerMergeData = bcsInnerMerge
			// .getBcsTenInnerMerge();
			// tempInnerMergeDate.setHsAfterComplex(innerMergeData
			// .getComplex());
			// tempInnerMergeDate.setHsAfterTenMemoNo(innerMergeData
			// .getSeqNum());
			// tempInnerMergeDate.setHsAfterMaterielTenName(innerMergeData
			// .getName());
			// tempInnerMergeDate.setHsAfterMaterielTenSpec(innerMergeData
			// .getSpec());
			// tempInnerMergeDate.setHsAfterLegalUnit(innerMergeData
			// .getComUnit());
			// } else {
			// DzscInnerMergeData dzscinnerMergeData = (DzscInnerMergeData) list
			// .get(i);
			// DzscTenInnerMerge innerMergeData = dzscinnerMergeData
			// .getDzscTenInnerMerge();
			// // tempInnerMergeDate.setDzscTenInnerMerge(innerMergeData
			// // .getDzscTenInnerMerge());
			// tempInnerMergeDate.setHsAfterComplex(innerMergeData
			// .getTenComplex());
			// tempInnerMergeDate.setHsAfterTenMemoNo(innerMergeData
			// .getTenSeqNum());
			// tempInnerMergeDate.setHsAfterMaterielTenName(innerMergeData
			// .getTenPtName());
			// tempInnerMergeDate.setHsAfterMaterielTenSpec(innerMergeData
			// .getTenPtSpec());
			// tempInnerMergeDate.setHsAfterLegalUnit(innerMergeData
			// .getTenUnit());
			// }

			arrayList.add(tempInnerMergeDate);

		}

		return arrayList;
	}

	public AtcMergeAfterComInfo findMakeListToCustomsBycustomsInfo(
			CustomsDeclarationCommInfo customsInfo) {
		AtcMergeAfterComInfo info = null;
		List list = ((EncDao) this.getBaseEncDao())
				.findMakeListToCustomsBycustomsInfo(customsInfo);
		if (list.size() > 0) {
			info = (AtcMergeAfterComInfo) list.get(0);
		}
		return info;
	}

	/**
	 * 按检查成品和手册序号排序
	 * 
	 */
	public List sortListByCheckupComplexForAtcMergeAfterComInfo(List datalist,
			Integer flag) {
		if (datalist == null) {
			return new ArrayList();
		}
		List<String> codelist = this.commonCodeDao.findComplexByFlag(flag);
		List yesList = new ArrayList();
		List noList = new ArrayList();
		List relist = new ArrayList();
		for (int i = 0; i < datalist.size(); i++) {
			AtcMergeAfterComInfo tempCustomsList = (AtcMergeAfterComInfo) datalist
					.get(i);
			if (tempCustomsList.getComplex() == null
					|| tempCustomsList.getComplex().getCode() == null) {
				noList.add(tempCustomsList);
				continue;
			}
			if (codelist.contains(tempCustomsList.getComplex().getCode())) {
				yesList.add(tempCustomsList);
			} else {
				noList.add(tempCustomsList);
			}
		}
		Collections.sort(yesList);
		Collections.sort(noList);
		relist.addAll(yesList);
		relist.addAll(noList);
		return relist;
	}

	/**
	 * 按商检排序
	 * 
	 * @param datalist
	 * @param flag
	 * @return
	 */
	public List sortListByCheckupComplex(List datalist, Integer flag) {
		if (datalist == null) {
			return new ArrayList();
		}
		List<String> codelist = this.commonCodeDao.findComplexByFlag(flag);
		List yesList = new ArrayList();
		List noList = new ArrayList();
		List relist = new ArrayList();
		for (int i = 0; i < datalist.size(); i++) {
			TempCustomsList tempCustomsList = (TempCustomsList) datalist.get(i);
			if (tempCustomsList.getAfterinfo() == null
					|| tempCustomsList.getAfterinfo().getComplex() == null
					|| tempCustomsList.getAfterinfo().getComplex().getCode() == null) {
				noList.add(tempCustomsList);
				continue;
			}
			if (codelist.contains(tempCustomsList.getAfterinfo().getComplex()
					.getCode())) {
				yesList.add(tempCustomsList);
			} else {
				noList.add(tempCustomsList);
			}
		}
		Collections.sort(yesList);
		Collections.sort(noList);
		relist.addAll(yesList);
		relist.addAll(noList);
		return relist;
	}

	private String findCancelCusHeaddByDate(Date beginDate, Date endDate) {
		return this.commonCodeDao.findCancelCusHeaddByDate(beginDate, endDate);
	}

	public EncDao getEncDao() {
		return encDao;
	}

	public void setEncDao(EncDao encDao) {
		this.encDao = encDao;
	}

	@Override
	protected CspParameterSet getCspParameterSet() {
		BcusParameterSet parameter = this.encDao.findBcusParameterSet();
		return parameter;
	}

	/**
	 * 抓取报关单某项商品的合同定量,合同余量,当前余量
	 * 
	 * @param isMaterial
	 *            true代表料件，false代表成品
	 * @param impExpType
	 *            单据类型
	 * @param tradeCode
	 *            贸易方式编码
	 * @param emsHeadH2k
	 *            合同备案表头
	 * @param seqNum
	 *            物料序号
	 * @return BcsContractExeInfo 存放合同成品的合同定量、合同余量、当前余量资料
	 */
	public BcusContractExeInfo findEmsHeadH2kExeInfo(boolean isMaterial,
			int impExpType, String tradeCode, EmsHeadH2k emsHeadH2k,
			Integer seqNum) {
		BcusContractExeInfo info = new BcusContractExeInfo();
		if (isMaterial) {
			EmsHeadH2kImg img = encDao.findEmsHeadh2kImgByEmsNoSeqNum(
					emsHeadH2k.getEmsNo(), seqNum);
			if (img == null) {
				return info;
			}

			/**
			 * 合同定量
			 */
			info.setContractAmount(img.getQty() == null ? 0.0 : img.getQty());

			/**
			 * 已生效料件进口数量
			 */
			double effectiveDirectImport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT, null,
					emsHeadH2k.getEmsNo(), null, null);
			System.out.println("已生效料件进口数量=" + effectiveDirectImport);
			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double effectiveRemainForwardImport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null,
							emsHeadH2k.getEmsNo(), null, null);
			/**
			 * 全部(已生效+未生效)余料结转出口数
			 */
			// double effectiveRemainForwardExport = this.encDao
			// .findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
			// ImpExpType.REMAIN_FORWARD_EXPORT, null, emsHeadH2k
			// .getEmsNo(), null, null);
			/**
			 * 已生效转厂进口数量
			 */
			double effectiveTransferFactoryImport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null,
							emsHeadH2k.getEmsNo(), null, null);
			/**
			 * 已生效料件退换出口数量
			 */
			double effectiveExchangeExport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, emsHeadH2k.getEmsNo(),
							null, null);
			System.out.println("已生效料件退换出口数量=" + effectiveExchangeExport);
			/**
			 * 全部(已生效+未生效)料件进口数量
			 */
			double allDirectImport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT, null,
					emsHeadH2k.getEmsNo(), null, null, -1);
			System.out.println("全部(已生效+未生效)料件进口数量=" + allDirectImport);
			/**
			 * 全部(已生效+未生效)料件内销数量
			 */
			double alllefovMateriaImport = encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.IMPORT,
					ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] { "0644",
							"0245" }, emsHeadH2k.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double allRemainForwardImport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null,
							emsHeadH2k.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)余料结转出口数
			 */
			double allRemainForwardExport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REMAIN_FORWARD_EXPORT, null,
							emsHeadH2k.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂进口数量
			 */
			double allTransferFactoryImport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null,
							emsHeadH2k.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)退料退还出口数量(退料退还);
			 */
			double allExchangeExport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.EXPORT, ImpExpType.BACK_MATERIEL_EXPORT,
					new String[] { "0300", "0700" }, emsHeadH2k.getEmsNo(),
					null, null, -1);
			/**
			 * 全部(已生效+未生效)退料退还进口数量(退料退还);
			 */
			double allExchangeImport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
					new String[] { "0300", "0700" }, emsHeadH2k.getEmsNo(),
					null, null, -1);
			/**
			 * 全部(已生效+未生效)退料退还出口数量(退料复出);
			 */
			double allBackExportNotImport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0664", "0265" }, emsHeadH2k.getEmsNo(),
							null, null, -1);

			if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {// 可退料出口的数量
				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- allRemainForwardExport);
				// - this.getProductUsedAmount(img));
			} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {// 可余料转出的数量
				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- allRemainForwardExport
						- this.getProductUsedAmount(img));
			} else if (impExpType == ImpExpType.DIRECT_IMPORT
					&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {// 可退料退还进口数量
				info.setCurrentRemain(allExchangeExport - allExchangeImport);
			} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES
					&& (tradeCode.equals("0644") || tradeCode.equals("0245"))) {// 海关批准内销料件内销
				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- alllefovMateriaImport
						- this.getProductUsedAmount(img));

			} else {// 可直接进口/可转厂进口数量/可余料结转转入数量
				info.setContractRemain(info.getContractAmount()
						- (effectiveDirectImport
								+ effectiveTransferFactoryImport
								+ effectiveRemainForwardImport - effectiveExchangeExport));
				// -effectiveRemainForwardExport
				info.setCurrentRemain(info.getContractAmount()
						- (allDirectImport + allTransferFactoryImport
								+ allRemainForwardImport - allExchangeExport));
				// -allRemainForwardExport
			}
		} else {
			EmsHeadH2kExg exg = encDao.findEmsHeadh2kExgByEmsNoSeqNum(
					emsHeadH2k.getEmsNo(), seqNum);
			if (exg == null) {
				return info;
			}
			/**
			 * 合同定量
			 */
			info.setContractAmount(exg.getQty() == null ? 0.0 : exg.getQty());
			/**
			 * 已生效成品出口数量
			 */
			double effectiveDirectExport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT, null,
					emsHeadH2k.getEmsNo(), null, null);
			/**
			 * 已生效转厂出口数量
			 */
			double effectiveTransferFactoryExport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							emsHeadH2k.getEmsNo(), null, null);
			/**
			 * 已生效成品退厂返工数量
			 */
			double effectiveBackFactoryRework = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null,
							emsHeadH2k.getEmsNo(), null, null);

			/**
			 * 已生效成品返工复出数量
			 */
			double effectiveReworkExport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT, null,
					emsHeadH2k.getEmsNo(), null, null);

			CompanyOther other = CommonUtils.getOther();
			if (other == null || !other.getIsCludeReturn()) {
				info.setContractRemain(info.getContractAmount()
						- (effectiveDirectExport + effectiveTransferFactoryExport));
			} else {
				info.setContractRemain(info.getContractAmount()
						- (effectiveDirectExport
								+ effectiveTransferFactoryExport
								- effectiveBackFactoryRework + effectiveReworkExport));
			}

			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT, null,
					emsHeadH2k.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							emsHeadH2k.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.IMPORT, ImpExpType.BACK_FACTORY_REWORK,
					null, emsHeadH2k.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT, null,
					emsHeadH2k.getEmsNo(), null, null, -1);

			/**
			 * 可退厂返工量
			 */
			if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
				info.setCurrentRemain(allDirectExport
						+ allTransferFactoryExport - allBackFactoryRework
						+ allReworkExport);
			}
			/**
			 * 可返工复出量
			 */
			else if (impExpType == ImpExpType.REWORK_EXPORT) {
				info.setCurrentRemain(allBackFactoryRework - allReworkExport);
			}
			/**
			 * 其他
			 */
			else {
				if (other == null || !other.getIsCludeReturn()) {
					info.setCurrentRemain(info.getContractAmount()
							- (allDirectExport + allTransferFactoryExport));
				} else {
					info.setCurrentRemain(info.getContractAmount()
							- (allDirectExport + allTransferFactoryExport
									- allBackFactoryRework + allReworkExport));
				}
			}
		}
		info.setContractAmount(CommonUtils.getDoubleByDigit(
				info.getContractAmount(), 5));
		info.setContractRemain(CommonUtils.getDoubleByDigit(
				info.getContractRemain(), 5));
		info.setCurrentRemain(CommonUtils.getDoubleByDigit(
				info.getCurrentRemain(), 5));
		return info;
	}

	/**
	 * 获取成品耗用量
	 * 
	 * @param emsNo
	 * @param seqNum
	 */
	private double getProductUsedAmount(EmsHeadH2kImg img) {
		double totalUsedAmount = 0.0;
		List lsBom = this.encDao.findEmsHeadH2kBomByImgSeqNum(img);
		for (int i = 0; i < lsBom.size(); i++) {
			EmsHeadH2kBom bom = (EmsHeadH2kBom) lsBom.get(i);
			EmsHeadH2kVersion version = bom.getEmsHeadH2kVersion();
			EmsHeadH2kExg exg = version.getEmsHeadH2kExg();
			Integer seqNum = exg.getSeqNum();
			EmsHeadH2k contract = exg.getEmsHeadH2k();
			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT, null,
					contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = this.encDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.IMPORT, ImpExpType.BACK_FACTORY_REWORK,
					null, contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = this.encDao.findCommInfoTotalAmount(
					seqNum, ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT, null,
					contract.getEmsNo(), null, null, -1);

			double totalExgAmount = allDirectExport + allTransferFactoryExport
					- allBackFactoryRework + allReworkExport;
			// totalUsedAmount += (totalExgAmount * CommonUtils
			// .getDoubleExceptNull(bom.getUnitDosage()));
			totalUsedAmount += (totalExgAmount * CommonUtils
					.getDoubleExceptNull(CommonUtils.getDoubleExceptNull(bom
							.getUnitWear())
							/ (1 - CommonUtils.getDoubleExceptNull(bom
									.getWear()) / 100.0)));
		}
		return totalUsedAmount;
	}

	/**
	 * 统计电子帐册财务报表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param mark
	 * @return
	 */
	public List calcCustomsDeclarationCommInfoAsFinancial(Date beginDate,
			Date endDate, String mark) {
		List returnList = new ArrayList();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
		List list = encDao.findCustomsDeclarationCommInfoAsFinancial(beginDate,
				endDate, mark);
		if ("0".equals(mark)) {// 进口财务报表
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				String jlDjch = obj[0] == null ? "" : (String) obj[0];// 帐册号
				String jkbgdh = obj[1] == null ? "" : (String) obj[1];// 进口报关单号
				String jckspdm = obj[2] == null ? "" : (String) obj[2];// 进口商品代码
				String ybdm = obj[3] == null ? "" : (String) obj[3];// 原币代码
				Date suoShuQi = obj[4] == null ? null : (Date) obj[4];// 进出口日期
				Double jcksl = fd((Double) obj[5]);// 进口数量
				Double ybdaj = fd((Double) obj[6]);// 原币到岸价
				Integer serialNumber = obj[7] == null ? 0 : (Integer) obj[7];// 报关单商品项号
				String commName = obj[8] == null ? "" : (String) obj[8];// 进口商品名称
				String unit = obj[9] == null ? "" : (String) obj[9];// 计量单位
				String curr = obj[10] == null ? "" : (String) obj[10];// 原币币别
				TempFinancialReport financial = new TempFinancialReport();
				financial.setSuoShuQi(suoShuQi == null ? "" : format
						.format(suoShuQi));// 所属期
				financial.setXuHao(String.format("%04d", i + 1));// 序号
				financial.setSuoShuQiBiaoShi(suoShuQi == null ? "" : formatYear
						.format(suoShuQi));// 所属期标识
				financial.setJlDjch(jlDjch);
				financial.setJkbgdh(jkbgdh);
				financial.setJckspdm(jckspdm);
				financial.setYbdm(ybdm);
				financial.setJcksl(String.valueOf(jcksl));
				financial.setYbdaj(String.valueOf(ybdaj));
				financial.setSerialNumber(String.format("%03d", serialNumber));
				financial.setCommName(commName);
				financial.setJldw(unit);
				financial.setCurrency(curr);
				returnList.add(financial);
			}
		} else if ("1".equals(mark)) {// 出口财务报表
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				String jlDjch = obj[0] == null ? "" : (String) obj[0];// 帐册号
				String jkbgdh = obj[1] == null ? "" : (String) obj[1];// 进口报关单号
				Date ckrq = obj[2] == null ? null : (Date) obj[2];// 出口日期
				String whxhdh = obj[3] == null ? "" : (String) obj[3];// 外汇核销单号
				String jckspdm = obj[4] == null ? "" : (String) obj[4];// 出口商品代码
				String ckspmc = obj[5] == null ? "" : (String) obj[5];// 出口商品名称
				String jldw = obj[6] == null ? "" : (String) obj[6];// 计量单位
				String ybdm = obj[7] == null ? "" : (String) obj[7];// 原币代码
				String ckfph = obj[8] == null ? "" : (String) obj[8];// 出口发票号
				Date suoShuQi = obj[9] == null ? null : (Date) obj[9];// 进出口日期
				Double jcksl = fd((Double) obj[10]);// 进口数量
				Double ybdaj = fd((Double) obj[11]);// 原币到岸价
				Integer serialNumber = obj[12] == null ? 0 : (Integer) obj[12];// 报关单商品项号
				String transferMode = obj[13] == null ? "" : (String) obj[13];// 运输方式
				String conveyance = obj[14] == null ? "" : (String) obj[14];// 运输工具
				String billOfLading = obj[15] == null ? "" : (String) obj[15];// 提运单号
				TempFinancialReport financial = new TempFinancialReport();
				financial.setSuoShuQi(suoShuQi == null ? "" : format
						.format(suoShuQi));// 所属期
				financial.setXuHao(String.format("%04d", i + 1));// 序号
				financial.setSuoShuQiBiaoShi(suoShuQi == null ? "" : formatYear
						.format(suoShuQi));// 所属期标识
				financial.setJxsrq(suoShuQi == null ? ""
						: new SimpleDateFormat("yyyy-MM-dd").format(CommonUtils
								.lastDayOfMonth(suoShuQi)));// 记销售日期(本月最后日期)
				financial.setJlDjch(jlDjch);
				financial.setJkbgdh(jkbgdh);
				financial.setCkrq(ckrq == null ? "" : new SimpleDateFormat(
						"yyyy-MM-dd").format(ckrq));
				financial.setWhxhdh(whxhdh);
				financial.setJckspdm(jckspdm);
				financial.setCkspmc(ckspmc);
				financial.setJldw(jldw);
				financial.setYbdm(ybdm);
				financial.setCkfph(ckfph);
				financial.setJcksl(String.valueOf(jcksl));
				financial.setYbdaj(String.valueOf(ybdaj));
				financial.setSerialNumber(String.format("%03d", serialNumber));
				financial.setTransferMode(transferMode);
				financial.setConveyance(conveyance);
				financial.setBillOfLading(billOfLading);
				returnList.add(financial);

			}
		} else if ("2".equals(mark)) {// 转厂出口财务
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				String jlDjch = obj[0] == null ? "" : (String) obj[0];// 帐册号
				String jkbgdh = obj[1] == null ? "" : (String) obj[1];// 进口报关单号
				Date ckrq = obj[2] == null ? null : (Date) obj[2];// 出口日期
				String whxhdh = obj[3] == null ? "" : (String) obj[3];// 外汇核销单号
				String jckspdm = obj[4] == null ? "" : (String) obj[4];// 出口商品代码
				String ckspmc = obj[5] == null ? "" : (String) obj[5];// 出口商品名称
				String jldw = obj[6] == null ? "" : (String) obj[6];// 计量单位
				String ybdm = obj[7] == null ? "" : (String) obj[7];// 原币代码
				String ckfph = obj[8] == null ? "" : (String) obj[8];// 出口发票号
				Date suoShuQi = obj[9] == null ? null : (Date) obj[9];// 进出口日期
				Double jcksl = fd((Double) obj[10]);// 进口数量
				Double ybdaj = fd((Double) obj[11]);// 原币到岸价
				Integer serialNumber = obj[12] == null ? 0 : (Integer) obj[12];// 报关单商品项号
				TempFinancialReport financial = new TempFinancialReport();
				financial.setSuoShuQi(suoShuQi == null ? "" : format
						.format(suoShuQi));// 所属期
				financial.setXuHao(String.format("%04d", i + 1));// 序号
				financial.setSuoShuQiBiaoShi(suoShuQi == null ? "" : formatYear
						.format(suoShuQi));// 所属期标识
				financial.setJxsrq(suoShuQi == null ? ""
						: new SimpleDateFormat("yyyy-MM-dd").format(CommonUtils
								.lastDayOfMonth(suoShuQi)));// 记销售日期(本月最后日期)
				financial.setJlDjch(jlDjch);
				financial.setJkbgdh(jkbgdh);
				financial.setCkrq(ckrq == null ? "" : new SimpleDateFormat(
						"yyyy-MM-dd").format(ckrq));
				financial.setWhxhdh(whxhdh);
				financial.setJckspdm(jckspdm);
				financial.setCkspmc(ckspmc);
				financial.setJldw(jldw);
				financial.setYbdm(ybdm);
				financial.setCkfph(ckfph);
				financial.setJcksl(String.valueOf(jcksl));
				financial.setYbdaj(String.valueOf(ybdaj));
				financial.setSerialNumber(String.format("%03d", serialNumber));
				returnList.add(financial);
			}
		}
		return returnList;
	}

	/**
	 * 生成报关清单(广达订制)
	 * 
	 * @param applyToCustomsBillList
	 * @param dataSource
	 * @param isMaterial
	 * @param isNewRecord
	 * @param head
	 * @return
	 */
	public List makeApplyToCustomsRequestBillListForGUANGDA(List dataSource,
			boolean isMaterial, MakeCusomsDeclarationParam param) {
		EmsHeadH2k head = param.getEmsHeadH2k();
		boolean isBillCustoms = param.isUniteBillLists();// 是否合并清单商品
		boolean useConvert = param.isUseConvert();// 是否单位折算
		ApplyToCustomsBillList billList = param.getApplyToCustomsBillList();
		Integer impExpFlag = billList.getImpExpFlag();
		Integer sAamount = null;
		Integer sPrice = null;
		Integer sTotalPrice = null;
		String cAamount = (String) commonCodeDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_AMOUNT);
		if (cAamount == null) {
			sAamount = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sAamount = Integer.parseInt(cAamount);
		}
		String cPrice = (String) commonCodeDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_PRICE);
		if (cPrice == null) {
			sPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sPrice = Integer.parseInt(cPrice);
		}
		String cTotalPrice = (String) commonCodeDao
				.getBpara(BcusParameter.BILL_TO_CONTROL_TOTALPRICE);

		if (cTotalPrice == null) {
			sTotalPrice = Integer.parseInt(BcusParameter.APLLY_TO_BILL_DEFAULT);
		} else {
			sTotalPrice = Integer.parseInt(cTotalPrice);
		}
		StringBuffer errorMessage = new StringBuffer();
		// 罗盛 改变清单归并前参数默认设置 2006/8/26 获得清单参数设置
		ApplyCustomBillParameter billParameter = this.sysCodeDao
				.findApplyCustomBillParameter();
		/*
		 * 把所有相同的ptNo数量等其它项整合成一条(原产国不同分开处理) 单价：取平均单价，数量，净重，毛重，件数：合并
		 * totalNetWeight：总净重（对出口来说：总净重=单位净重数量，对进口来说：总净重=单位净重）
		 */
		List impExpCommodityInfoList = null;
		if (!isBillCustoms) {// 申请单转清单（料号相同不合并）
			impExpCommodityInfoList = this
					.mergerImpExpCommodityInfoNo(dataSource);
		} else {
			impExpCommodityInfoList = this
					.mergerImpExpCommodityInfoGUANGDA(dataSource);
		}

		Hashtable<String, TempCustomsList> hsimg = new Hashtable<String, TempCustomsList>();
		Hashtable<String, TempCustomsList> hsexg = new Hashtable<String, TempCustomsList>();
		double legalUnitGene = 0.0;
		double legalUnit2Gene = 0.0;
		List impExpReCommodityInfoList = sortSangJianImpExpCommodityInfo(
				impExpCommodityInfoList, isMaterial, impExpFlag);

		for (int i = 0; i < impExpReCommodityInfoList.size(); i++) {// distinct
			ImpExpCommodityInfo t = (ImpExpCommodityInfo) impExpReCommodityInfoList
					.get(i);
			String ptNo = t.getMateriel().getPtNo();
			if (isMaterial == true) {// 料件 不检查版本号
				// 查找物料对应十码
				List flist = emsEdiTrDao
						.findCommdityForbid(MaterielType.MATERIEL);
				Collection cl = new Vector();
				for (int j = 0; j < flist.size(); j++) {
					CommdityForbid cf = (CommdityForbid) flist.get(j);
					String s = cf.getSeqNum() == null ? "" : cf.getSeqNum();
					cl.add(s);
				}
				InnerMergeData data = fptManageDao.findInnerMergerByPtNo(ptNo,
						MaterielType.MATERIEL);
				if (data == null) {
					errorMessage.append("料号：" + ptNo + " 在内部归并关系中不存在!\n");
				}
				if (cl.contains(data.getHsAfterTenMemoNo() == null ? "" : data
						.getHsAfterTenMemoNo().toString())) {
					errorMessage.append("料号：" + ptNo + " 所对应十码序号已被禁用!\n");
					continue;
				}
				TempCustomsList tempCustomsList = null;
				Integer tenSeqNum = data.getHsAfterTenMemoNo(); // 十码序号
				String countryName = t.getCountry() == null ? "" : t
						.getCountry().getName();
				Double unitPrice = t.getUnitPrice();
				// ---------------------------------------------------------------
				AtcMergeBeforeComInfo beforeinfo = makeAtcMergeBeforeComInfo(t,
						billParameter, useConvert, data, MaterielType.MATERIEL,
						sAamount, sPrice, sTotalPrice);// 新增清单归并前
				// ------------------------------------------
				// 合并的可key值
				String key = String.valueOf(tenSeqNum) + "/" + countryName
						+ "/" + unitPrice;
				EmsHeadH2kImg emsImg = null;
				AtcMergeAfterComInfo afterinfo = null;
				if (hsimg.get(key) != null) {
					tempCustomsList = (TempCustomsList) hsimg.get(key);
					afterinfo = tempCustomsList.getAfterinfo();
					// ------------------------------
					afterinfo = makeAtcInnerMergerComInfoByMateriel(afterinfo,
							null, beforeinfo, sAamount, sPrice, sTotalPrice,
							legalUnitGene, legalUnit2Gene);// 新增归并后
					// -------------------------------
					List list = tempCustomsList.getList();
					if (list == null) {
						list = new ArrayList();
					}
					list.add(beforeinfo);
					tempCustomsList.setList(list);
				} else {
					emsImg = ((EncDao) this.getBaseEncDao())
							.findEmsHeadH2kImgBySeqNum(head, tenSeqNum);
					if (emsImg == null) {
						errorMessage.append("料号：" + ptNo + " 对应帐册中状态不是已发送!\n");
						continue;
					}
					legalUnitGene = emsImg.getLegalUnitGene() == null ? 0.0
							: emsImg.getLegalUnitGene();
					legalUnit2Gene = emsImg.getLegalUnit2Gene() == null ? 0.0
							: emsImg.getLegalUnit2Gene();
					afterinfo = makeAtcInnerMergerComInfoByMateriel(null,
							emsImg, beforeinfo, sAamount, sPrice, sTotalPrice,
							legalUnitGene, legalUnit2Gene); // 新增归并后

					tempCustomsList = new TempCustomsList();
					tempCustomsList.setAfterinfo(afterinfo);
					List list = tempCustomsList.getList();
					if (list == null) {
						list = new ArrayList();
					}
					list.add(beforeinfo);
					tempCustomsList.setList(list);
					tempCustomsList.setIsSelected(new Boolean(true));
					hsimg.put(key, tempCustomsList);
				}
			} else { // 成品
				// 查找物料对应十码
				InnerMergeData data = fptManageDao.findInnerMergerByPtNo(ptNo,
						MaterielType.FINISHED_PRODUCT);
				if (data == null) {
					errorMessage.append("料号：" + ptNo + " 在内部归并中不存在!\n");
					continue;
				}
				TempCustomsList tempCustomsList = null;
				Integer tenSeqNum = data.getHsAfterTenMemoNo(); // 十码序号
				String countryName = t.getCountry() == null ? "" : t
						.getCountry().getName();

				// 查找禁用商品，把序号及版本作为KEY，存入.
				List forbidList = emsEdiTrDao.findCommdityForbidByExg(
						MaterielType.FINISHED_PRODUCT, tenSeqNum);
				Collection clCommdityForbid = new Vector();
				for (int j = 0; j < forbidList.size(); j++) {
					CommdityForbid cf = (CommdityForbid) forbidList.get(j);
					String s = (cf.getSeqNum() == null ? "" : cf.getSeqNum())
							+ "/"
							+ (cf.getVersion() == null ? "" : cf.getVersion());
					clCommdityForbid.add(s);
				}
				// -----------------------------------------------------
				AtcMergeBeforeComInfo beforeinfo = makeAtcMergeBeforeComInfo(t,
						billParameter, useConvert, data,
						MaterielType.FINISHED_PRODUCT, sAamount, sPrice,
						sTotalPrice);// 增加清单归并前
				// -------------------------------------------------------
				// 申请单转清单是否来源于归并关系BOM中的最大版本号
				Integer ver = null;
				String materielVersion = null;
				String isMergerBomMaxVer = emsEdiTrDao
						.getBpara(BcusParameter.MERGER_BOM_MaxVer);
				if (isMergerBomMaxVer == null || "0".equals(isMergerBomMaxVer)) {
					materielVersion = t.getVersion(); // 获取申请单里的版本号
					if (t.getVersion() == null || t.getVersion().equals("")) {
						errorMessage.append("料号：" + ptNo + " 申请单中没有填写版本!\n");
						continue;
					} else {
						try {
							Integer.parseInt(t.getVersion());
						} catch (Exception e) {
							e.printStackTrace();
							errorMessage.append("料号：" + ptNo
									+ " 申请单中版本填写不全法 !\n");
							continue;
						}
					}
					ver = Integer.parseInt(t.getVersion());
				} else {
					// 取得归并关系中的最大版本号
					ver = ((EncDao) this.getBaseEncDao())
							.findMergerBomlVersioId(ptNo, forbidList);
					if (ver == null) {
						errorMessage.append("料号：" + ptNo
								+ " 所对应的归并关系BOM中版本号未能找到!\n");
						continue;
					}
					materielVersion = String.valueOf(ver);
				}

				List allVList = ((EncDao) this.getBaseEncDao())
						.findEmsHeadH2kVersionAll(head, tenSeqNum, ver);// 所有版本
				if (allVList.size() == 0) {
					errorMessage.append("料号：" + ptNo + " 所对应的备案序号:" + tenSeqNum
							+ " 版本号：" + ver + "在对应帐册中不存在\n");
					continue;
				}
				for (int j = 0; j < allVList.size(); j++) {
					EmsHeadH2kVersion emsVersion = (EmsHeadH2kVersion) allVList
							.get(j);
					List vlist = ((EncDao) this.getBaseEncDao())
							.findEmsHeadH2kBom(emsVersion);
					if (vlist.size() == 0) {
						errorMessage.append("料号：" + ptNo + " 版本号："
								+ emsVersion.getVersion() + "在帐册中不存在!\n");
						continue;
					}
					String str = (emsVersion.getEmsHeadH2kExg().getSeqNum() == null ? ""
							: emsVersion.getEmsHeadH2kExg().getSeqNum()
									.toString())
							+ "/"
							+ (emsVersion.getVersion() == null ? ""
									: emsVersion.getVersion().toString());
					if (clCommdityForbid.contains(str)) {
						errorMessage
								.append("料号："
										+ ptNo
										+ " 所对应十码序号成品版本"
										+ (emsVersion.getVersion() == null ? ""
												: emsVersion.getVersion())
										+ " 已被禁用!\n");
						continue;// 说明禁用商品中存在，不让转
					}
					Double unitPrice = t.getUnitPrice();
					String key = String.valueOf(tenSeqNum) + "/"
							+ emsVersion.getVersion() + "/" + countryName + "/"
							+ unitPrice;
					EmsHeadH2kExg emsExg = null;
					AtcMergeAfterComInfo afterinfo = null;
					if (hsexg.get(key) != null) {
						tempCustomsList = (TempCustomsList) hsexg.get(key);
						afterinfo = tempCustomsList.getAfterinfo();
						afterinfo = makeAtcInnerMergerComInfoByMaterielExg(
								afterinfo, null, emsVersion, beforeinfo,
								sAamount, sPrice, sTotalPrice, legalUnitGene,
								legalUnit2Gene);
						List list = tempCustomsList.getList();
						if (list == null) {
							list = new ArrayList();
						}
						beforeinfo.setVersion(emsVersion.getVersion());
						list.add(beforeinfo);
						tempCustomsList.setList(list);
					} else {
						emsExg = emsVersion.getEmsHeadH2kExg();
						legalUnitGene = emsExg.getLegalUnitGene() == null ? 0.0
								: emsExg.getLegalUnitGene();
						legalUnit2Gene = emsExg.getLegalUnit2Gene() == null ? 0.0
								: emsExg.getLegalUnit2Gene();

						afterinfo = makeAtcInnerMergerComInfoByMaterielExg(
								null, emsExg, emsVersion, beforeinfo, sAamount,
								sPrice, sTotalPrice, legalUnitGene,
								legalUnit2Gene); // 新增归并后
						afterinfo.setCountry(t.getCountry());
						tempCustomsList = new TempCustomsList();
						tempCustomsList.setAfterinfo(afterinfo);
						List list = tempCustomsList.getList();
						if (list == null) {
							list = new ArrayList();
						}
						list.add(beforeinfo);
						tempCustomsList.setList(list);
						if (String.valueOf(emsVersion.getVersion()).equals(
								materielVersion)) {
							beforeinfo.setVersion(emsVersion.getVersion());
							tempCustomsList.setIsSelected(new Boolean(true));
						} else {
							tempCustomsList.setIsSelected(new Boolean(false));
						}
						if (tempCustomsList.getIsSelected()) {
							hsexg.put(key, tempCustomsList);
						}
					}
				}
			}
		}
		if (isMaterial == true) {
			ArrayList returnList = new ArrayList(hsimg.values());
			returnList.add(errorMessage);

			System.out.println("returnList" + returnList.size());
			return returnList;
		} else {
			ArrayList returnList = new ArrayList(hsexg.values());
			returnList.add(errorMessage);
			return returnList;
		}
	}

	/**
	 * 把所有相同的ptNo数量等其它项整合成一条
	 * 
	 * @param dataSource
	 *            临时的进出口商品信息
	 * @return 整合后的进出口商品信息
	 */
	private List mergerImpExpCommodityInfoGUANGDA(List dataSource) {
		List impExpCommodityList = new ArrayList();
		Hashtable hs = new Hashtable();
		for (int i = 0; i < dataSource.size(); i++) {
			TempImpExpCommodityInfo t = (TempImpExpCommodityInfo) dataSource
					.get(i);
			if (t.getImpExpCommodityInfo() == null
					|| t.getImpExpCommodityInfo().getMateriel() == null) {
				continue;
			}
			String key = t.getImpExpCommodityInfo().getMateriel().getPtNo()
					+ "/"
					+ (t.getImpExpCommodityInfo().getCountry() == null ? ""
							: t.getImpExpCommodityInfo().getCountry().getName()// 原产国
									+ "/"
									+ (t.getImpExpCommodityInfo().getVersion() == null ? ""
											: t.getImpExpCommodityInfo()
													.getVersion().toString()))
					+ "/" + t.getImpExpCommodityInfo().getUnitPrice();// 版本号
			System.out.println("key:" + key);
			Object obj = hs.get(key);
			if (obj != null) {
				ImpExpCommodityInfo data = (ImpExpCommodityInfo) obj;
				Double quantity = Double
						.valueOf((data.getQuantity() == null ? 0 : data
								.getQuantity().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getQuantity())
												.doubleValue()));// 总数
				Double totalMoney = Double
						.valueOf((data.getAmountPrice() == null ? 0 : data
								.getAmountPrice().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getAmountPrice())
												.doubleValue()));// 总金额
				Double workUsd = Double.valueOf((data.getWorkUsd() == null ? 0
						: data.getWorkUsd().doubleValue())
						+ (t.getImpExpCommodityInfo() == null ? 0 : forD(
								t.getImpExpCommodityInfo().getWorkUsd())
								.doubleValue()));// 加工费总金额

				Double unitPrice = 0.0;
				if (quantity != 0) {
					unitPrice = totalMoney / quantity;
					unitPrice = CommonUtils.getDoubleByDigit(unitPrice, 6);
				}

				Double grossWeight = Double
						.valueOf((data.getGrossWeight() == null ? 0 : data
								.getGrossWeight().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getGrossWeight())
												.doubleValue()));// 毛重
				Double netWeight = Double
						.valueOf((data.getNetWeight() == null ? 0 : data
								.getNetWeight().doubleValue())
								+ (t.getImpExpCommodityInfo() == null ? 0
										: forD(
												t.getImpExpCommodityInfo()
														.getNetWeight())
												.doubleValue()));// 净重
				Double cubage = Double.valueOf((data.getBulks() == null ? 0
						: data.getBulks().doubleValue())
						+ (t.getImpExpCommodityInfo() == null ? 0 : forD(
								t.getImpExpCommodityInfo().getBulks())
								.doubleValue()));// 体积
				Integer piece = (data.getPiece() == null ? 0 : data.getPiece()
						+ (t.getImpExpCommodityInfo().getPiece() == null ? 0
								: t.getImpExpCommodityInfo().getPiece()));// 件数
				// if (isMaterial) {// 料件
				// // //总净重（对出口来说：总净重=单位净重*数量，对进口来说：总净重=单位净重）
				Double totalNetWeight = Double.valueOf((data
						.getTotalnetweight() == null ? 0 : data
						.getTotalnetweight().doubleValue())
						+ (t.getImpExpCommodityInfo() == null ? 0 : forD(
								t.getImpExpCommodityInfo().getNetWeight())
								.doubleValue()));
				String boxNo = data.getBoxNo();// 箱号
				String newBoxNo = "";
				if (boxNo != null && !"".equals(boxNo)) {
					newBoxNo = getNotExistBoxNo(boxNo, t
							.getImpExpCommodityInfo().getBoxNo());
				} else {
					newBoxNo = t.getImpExpCommodityInfo().getBoxNo();
				}
				data.setBoxNo(newBoxNo);
				data.setAmountPrice(totalMoney);// 总金额
				data.setWorkUsd(workUsd);// 加工费总价
				data.setQuantity(quantity);// 总数量
				data.setUnitPrice(unitPrice);// 单价
				data.setPiece(piece);// 件数
				data.setGrossWeight(grossWeight);// 毛重
				data.setNetWeight(netWeight); // 净重
				data.setTotalnetweight(totalNetWeight);// 总净重
				data.setBulks(cubage);// 体积
			} else {
				ImpExpCommodityInfo data = t.getImpExpCommodityInfo();
				hs.put(key, data);
			}
		}
		impExpCommodityList.addAll(hs.values());
		return impExpCommodityList;
	}

	/**
	 * 申清单---清单--生成报关单 生成清单
	 * 
	 * @param impexpbills
	 * @param afterinfoLists
	 * @param param
	 * @param customs
	 *            报关单表头
	 * @return
	 */
	public List makeCusomsDeclarationFromBillListGUANGDA(List impexpbills,
			List afterinfoLists, MakeCusomsDeclarationParam param,
			CustomsDeclaration customs) {

		// 存放生成的清单
		List returnBilllist = new ArrayList();
		// 存放生成的报关单
		List returnCustomsDeclaretionlist = new ArrayList();
		// 输入的清单表头信息
		ApplyToCustomsBillList billList = param.getApplyToCustomsBillList();
		// 是否转报关单
		boolean isCustomsDeclaration = param.isCustomsDeclaration();
		// 临时存放清单表体
		List hsList = new ArrayList();
		// 清单表体按照序号排列
		for (int y = 0; y < afterinfoLists.size(); y++) {
			TempCustomsList tempcustoms = (TempCustomsList) afterinfoLists
					.get(y);
			hsList.add(tempcustoms);
		}

		// ============开始按商检排序============
		// 进出口类型
		Integer type = billList.getImpExpFlag();
		// 商检排序
		hsList = sortListByCheckupComplex(hsList, type);

		// 确定清单号码
		if (billList.getId() == null) {
			String maxNo = ((EncDao) this.getBaseEncDao())
					.getApplyToCustomsBillListMaxNo();
			billList.setListNo(maxNo);
		}

		// 首先保存清单表头
		billList = ((EncDao) this.getBaseEncDao()).saveBillList(billList);
		returnBilllist.add(billList);

		// 保存清单表体
		int serNo = 1;
		for (int i = 0; i < hsList.size(); i++) {
			// 如果超过20项，拆分
			if (i != 0 && i % 20 == 0) {
				try {
					billList = (ApplyToCustomsBillList) BeanUtils
							.cloneBean(billList);
					billList.setId(null);
					String maxNo = ((EncDao) this.getBaseEncDao())
							.getApplyToCustomsBillListMaxNo();
					billList.setListNo(maxNo);
					billList = ((EncDao) this.getBaseEncDao())
							.saveBillList(billList);
					returnBilllist.add(billList);
					serNo = 1;
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}

			TempCustomsList tempcustoms = (TempCustomsList) hsList.get(i);
			AtcMergeAfterComInfo afterinfo = tempcustoms.getAfterinfo();
			afterinfo.setBillList(billList);
			// System.out.println(afterinfo.getEmsSerialNo() + ":"
			// + afterinfo.getCountry().getCode());
			((EncDao) this.getBaseEncDao()).saveAtcMergeAfterComInfo(afterinfo);
			// /////////
			if (!param.isOtherBilllist()) {
				Integer max = ((EncDao) this.getBaseEncDao())
						.findMaxAtcMergeAfterComInfoByListID(billList);
				if (max != null) {
					serNo = max;
				}
			}
			// 如果是追加到现有清单，则归并后流水后应该相应增加
			// --------------------------------------------------------------------------------
			List beforeList = tempcustoms.getList();
			Hashtable<String, AtcMergeBeforeComInfo> beforeHs = new Hashtable<String, AtcMergeBeforeComInfo>();
			for (int k = 0; k < beforeList.size(); k++) {
				AtcMergeBeforeComInfo beforeinfo = (AtcMergeBeforeComInfo) beforeList
						.get(k);
				beforeinfo.setAfterComInfo(afterinfo);// 归并前 set 归并后
				beforeinfo.setSerialNo(serNo);
				serNo++;

				/**
				 * 明门特殊处理
				 */
				if (CommonUtils.isCompany("明门幼童")
						|| CommonUtils.isCompany("宝钜儿童用品")) {
					beforeinfo = getSpecalDeal(beforeinfo);
				}
				/**
				 * 康舒特殊处理
				 */
				if (CommonUtils.isCompany("康舒电子")) {
					beforeinfo = getSpecalDealKs(beforeinfo);
				}

				((EncDao) this.getBaseEncDao())
						.saveAtcMergeBeforeComInfo(beforeinfo);
				String ptNo = beforeinfo.getMateriel().getPtNo()
						+ "/"
						+ (beforeinfo.getSalesCountry() == null ? ""
								: beforeinfo.getSalesCountry().getName());
				if (beforeHs.get(ptNo) == null) {
					beforeHs.put(ptNo, beforeinfo);
				}
			}
			// --------------------------------------------------------------------------------
			// 回写申请单
			List makeApplyToCustomsList = new ArrayList();
			for (int t = 0; t < impexpbills.size(); t++) {
				TempImpExpCommodityInfo tt = (TempImpExpCommodityInfo) impexpbills
						.get(t);
				String ptpart = tt.getImpExpCommodityInfo().getMateriel()
						.getPtNo()
						+ "/"
						+ (tt.getImpExpCommodityInfo().getCountry() == null ? ""
								: tt.getImpExpCommodityInfo().getCountry()
										.getName());
				if (beforeHs.get(ptpart) != null) {
					ImpExpCommodityInfo iecomm = tt.getImpExpCommodityInfo();
					iecomm.setIsTransferCustomsBill(new Boolean(true)); // 已转报关清单
					((EncDao) this.getBaseEncDao())
							.savaimpExpCommodityInfo(iecomm);
					// 回写申请单转清单中间表信息
					MakeApplyToCustoms m = new MakeApplyToCustoms();
					m.setAtcMergeBeforeComInfo((AtcMergeBeforeComInfo) beforeHs
							.get(ptpart));
					m.setImpExpCommodityInfo(tt.getImpExpCommodityInfo());
					m.setCompany(CommonUtils.getCompany());
					makeApplyToCustomsList.add(m);

					// 回写报关单表头
					ImpExpRequestBill impExpRequestBill = iecomm
							.getImpExpRequestBill();

					int count = impExpRequestBill.getToCustomCount() == null ? 0
							: impExpRequestBill.getToCustomCount();
					System.out.println("@@@@@@@@@@1");
					if (impExpRequestBill.getItemCount() == null
							|| impExpRequestBill.getItemCount() == 0) {
						int itemCount = ((EncDao) this.getBaseEncDao())
								.getApplyToCustomsBillListSumNo(impExpRequestBill
										.getId());
						impExpRequestBill.setItemCount(itemCount);
						((EncDao) this.getBaseEncDao())
								.saveOrUpdate(impExpRequestBill);
					}
					impExpRequestBill.setToCustomCount((count + 1));

					// 回写申请单中的 转 报关清单号
					if (impExpRequestBill.getAllBillNo() == null) {
						impExpRequestBill.setAllBillNo(m
								.getAtcMergeBeforeComInfo().getAfterComInfo()
								.getBillList().getListNo());
					} else if (!impExpRequestBill.getAllBillNo().contains(
							m.getAtcMergeBeforeComInfo().getAfterComInfo()
									.getBillList().getListNo())) {
						impExpRequestBill.setAllBillNo(impExpRequestBill
								.getAllBillNo()
								+ ","
								+ m.getAtcMergeBeforeComInfo()
										.getAfterComInfo().getBillList()
										.getListNo());
					}

					// 回写申请单 是否已转报关清单
					if (impExpRequestBill.getToCustomCount().equals(
							impExpRequestBill.getItemCount())) {
						impExpRequestBill.setIsCustomsBill(true);
					}
					((EncDao) this.getBaseEncDao())
							.saveImpExpRequestBill(impExpRequestBill);
				}
			}
			// 回写表头商品项数
			((EncDao) this.getBaseEncDao())
					.saveMakeApplyToCustoms(makeApplyToCustomsList);

			List beforesize = ((EncDao) this.getBaseEncDao())
					.findAtcMergeBeforeComInfoByListID(billList);
			int size = beforesize == null ? 0 : beforesize.size();
			billList.setMaterialNum(size);
			((EncDao) this.getBaseEncDao()).saveBillList(billList);
		}
		List returnList = new ArrayList();
		returnList.add(returnBilllist);
		returnList.add(returnCustomsDeclaretionlist);

		// --------------------------------------------------------------------------------------------------------------------
		if (!isCustomsDeclaration) {// 判断是否继续转报关单
			return returnList;
		} else {
			for (int i = 0; i < returnBilllist.size(); i++) {
				ApplyToCustomsBillList obillList = (ApplyToCustomsBillList) returnBilllist
						.get(i);
				CustomsDeclaration ocustoms = makeBilllistsToCustomDeclaretionsGUANGDA(
						obillList, customs, param, null, true);
				returnCustomsDeclaretionlist.add(ocustoms);
			}
		}
		return returnList;
	}

	/**
	 * 清单转---报关单
	 * 
	 * @param billList
	 * @param customs
	 * @param param
	 * @param billDetailList
	 * @param arrayType
	 * @return hcl 2010-09-09增加一boolean值判断报关单流水号是否要按清单序号排序 lyh
	 *         2012-12-11当一份申请单中的商品为完全转为报关单时，申请单不标示为以转报关单
	 */
	public CustomsDeclaration makeBilllistsToCustomDeclaretionsGUANGDA(
			// TODO "申请单转报关单"
			ApplyToCustomsBillList billList, CustomsDeclaration customs,
			MakeCusomsDeclarationParam param, List billDetailList,
			boolean isSanJianArrayType) {
		customs = makeCustomsDeclaration(billList, param, customs);// 添加到报关单头,
		List flist = null;
		if (billDetailList == null) {
			flist = this.findAtcMergeAfterComInfoByListID(billList);// 合计
		} else {
			flist = billDetailList;
		}
		Integer type = billList.getImpExpFlag();
		int weightFraction = 4;// 设置表体的净重，毛重小数位为4
		// =====================按商检排序放前面============
		if (isSanJianArrayType) {
			flist = sortListByCheckupComplexForAtcMergeAfterComInfo(flist, type);
		}
		for (int i = 0; i < flist.size(); i++) {
			AtcMergeAfterComInfo afterinfo = (AtcMergeAfterComInfo) flist
					.get(i);
			if (afterinfo.getIsTransferCustomsBill()
					|| afterinfo.getEmsSerialNo() == null) {
				continue;// 如果已经转报关单，则不再转
			}
			// 修改清单报关单流水号,状态，商品项
			modifyApplyToCustomsBillList(afterinfo.getBillList(),
					String.valueOf(customs.getSerialNumber()),
					Integer.valueOf(1));//
			// 保存清单报关单号
			// -------------------------------------------------------------------------------
			BaseCustomsDeclarationCommInfo existcommInfo = null;
			CustomsDeclarationCommInfo commInfo = null;
			EmsHeadH2kExg exg = null;
			EmsHeadH2kImg img = null;
			String commInfoid = null;
			// 小数位参数
			CompanyOther otherPra = (CompanyOther) sysCodeDao
					.findCompanyOther().get(0);
			int amountNum = otherPra.getCustomAmountNum();
			int totalNum = otherPra.getCustomTotalNum();
			int priceNum = otherPra.getCustomPriceNum();
			// 通过清单后获得电子帐册商品信息
			if (billList.getMaterielProductFlag().equals(
					Integer.valueOf(MaterielType.MATERIEL))) {
				existcommInfo = ((EncDao) this.getBaseEncDao())
						.findBaseCustomsDeclarationCommInfoBySequm(
								Integer.valueOf(afterinfo.getEmsSerialNo()),
								customs, null, afterinfo.getCountry());

				if (existcommInfo == null
						|| existcommInfo.getCommUnitPrice() == null
						|| !existcommInfo.getCommUnitPrice().equals(
								afterinfo.getPrice())) {
					existcommInfo = null;
				}

				img = ((EncDao) this.getBaseEncDao()).getEmsHeadImg(Integer
						.valueOf(afterinfo.getEmsSerialNo()));
				if (img == null) {
					continue;
				}
				if (existcommInfo == null) {
					CustomsDeclarationSet other = this.findCustomsSet(customs
							.getImpExpType());
					commInfo = new CustomsDeclarationCommInfo();
					commInfo.setBaseCustomsDeclaration(customs);
					commInfo.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(customs));
					if (billList.getMemos() != null) {
						commInfo.getBaseCustomsDeclaration().setMemos(
								billList.getMemos());
					}
					commInfo.setCommSerialNo(afterinfo.getEmsSerialNo());
					commInfo.setComplex(img.getComplex());
					commInfo.setCommName(img.getName());
					commInfo.setDetailNote(img.getDetailNote()); // 详细型号规格
					commInfo.setCommSpec(img.getSpec());
					commInfo.setUnit(img.getUnit());
					commInfo.setLegalUnit(img.getComplex().getFirstUnit());
					commInfo.setSecondLegalUnit(img.getComplex()
							.getSecondUnit());
					commInfo.setCountry(afterinfo.getCountry());// 国家
					commInfo.setLevyMode(afterinfo.getLevyMode());//
					commInfo.setUses(afterinfo.getUsesCode());
					commInfo.setProjectDept(afterinfo.getProjectDept());

					// -----------------------------------------------------------------------
					commInfo.setNetWeight(formatD((afterinfo.getNetWeight() == null ? 0.0
							: afterinfo.getNetWeight())));// 净重
					commInfo.setGrossWeight(formatD((afterinfo.getGrossWeight() == null ? 0.0
							: afterinfo.getGrossWeight())));// 毛重
					// 件数 == 件数
					commInfo.setPieces(afterinfo.getPiece() == null ? 0
							: afterinfo.getPiece().intValue());

					commInfo.setBoxNo(afterinfo.getBoxNo());// 箱号
					commInfo.setCommAmount(afterinfo.getDeclaredAmount() == null ? 0.0
							: afterinfo.getDeclaredAmount());// 数量

					commInfo.setWorkUsd(afterinfo.getWorkUsd() == null ? 0.0
							: afterinfo.getWorkUsd());// 加工费总价

					if (param.getPriceType().equals("平均单价")) {
						commInfo.setCommTotalPrice(afterinfo.getTotalPrice());// 总金额
						if (afterinfo.getDeclaredAmount() != null
								&& afterinfo.getDeclaredAmount() != 0) {
							commInfo.setCommUnitPrice(afterinfo.getTotalPrice()
									/ afterinfo.getDeclaredAmount());
						} else {
							commInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						commInfo.setCommUnitPrice(img.getDeclarePrice() == null ? 0.0
								: img.getDeclarePrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						commInfo.setCommUnitPrice(img.getFactoryPrice() == null ? 0.0
								: img.getFactoryPrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					}
					commInfo.setFirstAmount(afterinfo.getLegalAmount() == null ? 0.0
							: afterinfo.getLegalAmount());// 第一法定数量
					commInfo.setSecondAmount(afterinfo.getSecondLegalAmount() == null ? 0.0
							: afterinfo.getSecondLegalAmount());// 第二法定数量
					// -----------------------------------------------------------------------
					if (other != null) {
						commInfo.setUses(commInfo.getUses() == null ? other
								.getUses() : commInfo.getUses());
						commInfo.setLevyMode(commInfo.getLevyMode() == null ? other
								.getLevyMode() : commInfo.getLevyMode());

					}

					// ========= 小数位控制
					commInfo.setAddType(AddType.APPLY);
					commInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							commInfo.getGrossWeight(), weightFraction));
					commInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							commInfo.getNetWeight(), weightFraction));
					commInfo.setCommAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommAmount()), amountNum));
					commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommUnitPrice()), priceNum));
					commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommTotalPrice()), totalNum));

					commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getWorkUsd()), totalNum));

					// 第一法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getLegalUnit() != null
							&& commInfo.getUnit().getName()
									.equals(commInfo.getLegalUnit().getName())) {
						commInfo.setFirstAmount(commInfo.getCommAmount());
					} else if (commInfo.getLegalUnit() != null
							&& commInfo.getLegalUnit().getName().equals("千克")) {
						commInfo.setFirstAmount(commInfo.getNetWeight());
					}
					// 第二法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getSecondLegalUnit() != null
							&& commInfo
									.getUnit()
									.getName()
									.equals(commInfo.getSecondLegalUnit()
											.getName())) {
						commInfo.setSecondAmount(commInfo.getCommAmount());
					} else if (commInfo.getSecondLegalUnit() != null
							&& commInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						commInfo.setSecondAmount(commInfo.getNetWeight());
					}
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(commInfo);
					commInfoid = commInfo.getId();

				} else {
					// -----------------------------------------------------------------------
					existcommInfo.setNetWeight(formatD(existcommInfo
							.getNetWeight())
							+ formatD((afterinfo.getNetWeight() == null ? 0.0
									: afterinfo.getNetWeight())));
					existcommInfo.setGrossWeight(formatD(existcommInfo
							.getGrossWeight())
							+ formatD((afterinfo.getGrossWeight() == null ? 0.0
									: afterinfo.getGrossWeight())));
					existcommInfo
							.setPieces(existcommInfo.getPieces() == null ? 0
									: existcommInfo.getPieces()
											+ (afterinfo.getPiece() == null ? 0
													: afterinfo.getPiece()
															.intValue()));
					String boxNo = existcommInfo.getBoxNo();// 箱号
					String newBoxNo = "";
					if (boxNo != null && !"".equals(boxNo)) {
						newBoxNo = getNotExistBoxNo(boxNo, afterinfo.getBoxNo());
					} else {
						newBoxNo = afterinfo.getBoxNo();
					}
					existcommInfo.setBoxNo(newBoxNo);
					existcommInfo.setFirstAmount(formatD(existcommInfo
							.getFirstAmount())
							+ formatD(afterinfo.getLegalAmount()));// 第一法定数量
					existcommInfo.setSecondAmount(formatD(existcommInfo
							.getSecondAmount())
							+ formatD(afterinfo.getSecondLegalAmount()));// 第二法定数量
					existcommInfo
							.setCommAmount((existcommInfo.getCommAmount() == null ? 0.0
									: existcommInfo.getCommAmount())
									+ (afterinfo.getDeclaredAmount() == null ? 0.0
											: afterinfo.getDeclaredAmount()));// 数量
					existcommInfo
							.setWorkUsd((existcommInfo.getWorkUsd() == null ? 0.0
									: existcommInfo.getWorkUsd())
									+ (afterinfo.getWorkUsd() == null ? 0.0
											: afterinfo.getWorkUsd()));// 加工费总价

					if (param.getPriceType().equals("平均单价")) {
						existcommInfo.setCommTotalPrice(existcommInfo
								.getCommTotalPrice()
								+ afterinfo.getTotalPrice());// 总金额
						if (existcommInfo.getCommAmount() != null
								&& existcommInfo.getCommAmount() != 0) {
							existcommInfo.setCommUnitPrice(existcommInfo
									.getCommTotalPrice()
									/ existcommInfo.getCommAmount());
						} else {
							existcommInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						existcommInfo
								.setCommUnitPrice(img.getDeclarePrice() == null ? 0.0
										: img.getDeclarePrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						existcommInfo
								.setCommUnitPrice(img.getFactoryPrice() == null ? 0.0
										: img.getFactoryPrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					}

					existcommInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getGrossWeight(), weightFraction));
					existcommInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getNetWeight(), weightFraction));

					// 第一法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getLegalUnit()
											.getName())) {
						existcommInfo.setFirstAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getLegalUnit() != null
							&& existcommInfo.getLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setFirstAmount(existcommInfo
								.getNetWeight());
					}

					// 第二法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getSecondLegalUnit()
											.getName())) {
						existcommInfo.setSecondAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setSecondAmount(existcommInfo
								.getNetWeight());
					}
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(existcommInfo);
					commInfoid = existcommInfo.getId();
				}
			} else {// 成品（出口报关单）
				existcommInfo = ((EncDao) this.getBaseEncDao())
						.findBaseCustomsDeclarationCommInfoBySequm(
								Integer.valueOf(afterinfo.getEmsSerialNo()),
								customs, afterinfo.getVersion(),
								afterinfo.getCountry());
				if (existcommInfo == null
						|| existcommInfo.getCommUnitPrice() == null
						|| !existcommInfo.getCommUnitPrice().equals(
								afterinfo.getPrice())) {
					existcommInfo = null;
				}
				Integer version = (afterinfo.getVersion() == null || ""
						.equals(afterinfo.getVersion())) ? 0 : Integer
						.valueOf(afterinfo.getVersion());
				EmsHeadH2kVersion versionObj = ((EncDao) this.getBaseEncDao())
						.findEmsHeadH2kVersion(
								Integer.valueOf(afterinfo.getEmsSerialNo()),
								version);
				if (versionObj == null) {

					throw new RuntimeException("该备案号["
							+ afterinfo.getEmsSerialNo() + "]的版本[" + version
							+ "]在帐册中不存在");
				}
				exg = versionObj.getEmsHeadH2kExg();
				CustomsDeclarationSet other1 = this.findCustomsSet(customs
						.getImpExpType());

				if (existcommInfo == null) {
					commInfo = new CustomsDeclarationCommInfo();
					commInfo.setBaseCustomsDeclaration(customs);
					if (billList.getMemos() != null) {
						commInfo.getBaseCustomsDeclaration().setMemos(
								billList.getMemos());
					}
					commInfo.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(customs));
					commInfo.setCommSerialNo(afterinfo.getEmsSerialNo());
					commInfo.setComplex(exg.getComplex());
					commInfo.setCommName(exg.getName());
					commInfo.setCommSpec(exg.getSpec());
					commInfo.setUnit(exg.getUnit());
					commInfo.setLegalUnit(exg.getComplex().getFirstUnit());
					commInfo.setSecondLegalUnit(exg.getComplex()
							.getSecondUnit());
					commInfo.setCountry(afterinfo.getCountry());
					commInfo.setLevyMode(afterinfo.getLevyMode());//
					commInfo.setUses(afterinfo.getUsesCode());
					commInfo.setProjectDept(afterinfo.getProjectDept());
					commInfo.setBoxNo(afterinfo.getBoxNo());// 箱号
					// ---------------------------------------------------------------
					commInfo.setVersion(afterinfo.getVersion());
					if (other1 != null) {
						commInfo.setUses(commInfo.getUses() == null ? other1
								.getUses() : commInfo.getUses());
						commInfo.setLevyMode(commInfo.getLevyMode() == null ? other1
								.getLevyMode() : commInfo.getLevyMode());
					}
					// --------------------------------------------------------------
					// 件数 == 件数
					commInfo.setPieces(afterinfo.getPiece() == null ? 0
							: afterinfo.getPiece().intValue());
					commInfo.setNetWeight(formatD(afterinfo.getNetWeight() == null ? 0.0
							: afterinfo.getNetWeight())); //
					commInfo.setGrossWeight(formatD(afterinfo.getGrossWeight() == null ? 0.0
							: afterinfo.getGrossWeight()));

					commInfo.setFirstAmount(forInterNum(afterinfo
							.getLegalAmount() == null ? 0.0 : afterinfo
							.getLegalAmount())); // 第一法定数量
					commInfo.setSecondAmount(afterinfo.getSecondLegalAmount() == null ? 0.0
							: afterinfo.getSecondLegalAmount());// 第二法定数量
					commInfo.setCommAmount(afterinfo.getDeclaredAmount() == null ? 0.0
							: afterinfo.getDeclaredAmount());
					commInfo.setWorkUsd(afterinfo.getWorkUsd() == null ? 0.0
							: afterinfo.getWorkUsd());// 加工费总价

					if (param.getPriceType().equals("平均单价")) {
						commInfo.setCommTotalPrice(afterinfo.getTotalPrice());// 总金额
						if (afterinfo.getDeclaredAmount() != null
								&& afterinfo.getDeclaredAmount() != 0) {
							commInfo.setCommUnitPrice(afterinfo.getTotalPrice()
									/ afterinfo.getDeclaredAmount());
						} else {
							commInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						commInfo.setCommUnitPrice(exg.getDeclarePrice() == null ? 0.0
								: exg.getDeclarePrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						commInfo.setCommUnitPrice(exg.getFactoryPrice() == null ? 0.0
								: exg.getFactoryPrice());
						commInfo.setCommTotalPrice(commInfo.getCommAmount()
								* (commInfo.getCommUnitPrice() == null ? 0.0
										: commInfo.getCommUnitPrice()));
					}
					// --------------------------------------------------------------
					commInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							commInfo.getGrossWeight(), weightFraction));
					commInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							commInfo.getNetWeight(), weightFraction));
					// 小数位控制
					commInfo.setCommAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommAmount()), amountNum));
					commInfo.setCommUnitPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommUnitPrice()), priceNum));
					// System.out.println("====totalNum=="+totalNum+" 11
					// "+commInfo
					// .getCommTotalPrice()+" 22
					// ="+CommonUtils.getDoubleByDigit(
					// CommonUtils.getDoubleExceptNull(commInfo
					// .getCommTotalPrice()), totalNum));
					commInfo.setCommTotalPrice(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getCommTotalPrice()), totalNum));
					commInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(commInfo
									.getWorkUsd()), totalNum));
					// 第一法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getLegalUnit() != null
							&& commInfo.getUnit().getName()
									.equals(commInfo.getLegalUnit().getName())) {
						commInfo.setFirstAmount(commInfo.getCommAmount());
					} else if (commInfo.getLegalUnit() != null
							&& commInfo.getLegalUnit().getName().equals("千克")) {
						commInfo.setFirstAmount(commInfo.getNetWeight());
					}
					// 第二法定数量
					if (commInfo.getUnit() != null
							&& commInfo.getSecondLegalUnit() != null
							&& commInfo
									.getUnit()
									.getName()
									.equals(commInfo.getSecondLegalUnit()
											.getName())) {
						commInfo.setSecondAmount(commInfo.getCommAmount());
					} else if (commInfo.getSecondLegalUnit() != null
							&& commInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						commInfo.setSecondAmount(commInfo.getNetWeight());
					}

					commInfo.setAddType(AddType.APPLY);
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(commInfo);
					commInfoid = commInfo.getId();
				} else {
					// --------------------------------------------------------------
					existcommInfo
							.setPieces(existcommInfo.getPieces() == null ? 0
									: existcommInfo.getPieces()
											+ forInterNum(
													afterinfo.getPiece() == null ? 0.0
															: afterinfo
																	.getPiece())
													.intValue());
					existcommInfo.setBoxNo(afterinfo.getBoxNo());
					existcommInfo.setNetWeight(formatD(existcommInfo// 净重
							.getNetWeight())
							+ formatD(afterinfo.getNetWeight()));
					existcommInfo.setGrossWeight(formatD(existcommInfo// 毛重
							.getGrossWeight())
							+ formatD(afterinfo.getGrossWeight()));
					existcommInfo.setFirstAmount((existcommInfo
							.getFirstAmount() == null ? 0.0 : existcommInfo
							.getFirstAmount())
							+ (afterinfo.getLegalAmount() == null ? 0.0
									: afterinfo.getLegalAmount()));
					existcommInfo.setSecondAmount((existcommInfo
							.getSecondAmount() == null ? 0.0 : existcommInfo
							.getSecondAmount())
							+ (afterinfo.getSecondLegalAmount() == null ? 0.0
									: afterinfo.getSecondLegalAmount()));
					existcommInfo
							.setCommAmount((existcommInfo.getCommAmount() == null ? 0.0
									: existcommInfo.getCommAmount())
									+ afterinfo.getDeclaredAmount());// 数量
					existcommInfo
							.setWorkUsd((existcommInfo.getWorkUsd() == null ? 0.0
									: existcommInfo.getWorkUsd())
									+ afterinfo.getWorkUsd());// 加工费总
					if (param.getPriceType().equals("平均单价")) {
						existcommInfo.setCommTotalPrice(existcommInfo
								.getCommTotalPrice()
								+ afterinfo.getTotalPrice());// 总金额
						if (existcommInfo.getCommAmount() != null
								&& existcommInfo.getCommAmount() != 0) {
							existcommInfo.setCommUnitPrice(existcommInfo
									.getCommTotalPrice()
									/ existcommInfo.getCommAmount());
						} else {
							existcommInfo.setCommUnitPrice(0.0);// 单价
						}
					} else if (param.getPriceType().equals("对应帐册申报单价")) {
						existcommInfo
								.setCommUnitPrice(exg.getDeclarePrice() == null ? 0.0
										: exg.getDeclarePrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					} else if (param.getPriceType().equals("对应帐册工厂单价")) {
						existcommInfo
								.setCommUnitPrice(exg.getFactoryPrice() == null ? 0.0
										: exg.getFactoryPrice());
						existcommInfo
								.setCommTotalPrice(existcommInfo
										.getCommAmount()
										* (existcommInfo.getCommUnitPrice() == null ? 0.0
												: existcommInfo
														.getCommUnitPrice()));
					}
					existcommInfo.setGrossWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getGrossWeight(), weightFraction));
					existcommInfo.setNetWeight(CommonUtils.getDoubleByDigit(
							existcommInfo.getNetWeight(), weightFraction));
					existcommInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(existcommInfo
									.getSecondAmount()), amountNum));
					existcommInfo.setCommUnitPrice(CommonUtils
							.getDoubleByDigit(CommonUtils
									.getDoubleExceptNull(existcommInfo
											.getCommUnitPrice()), priceNum));
					// System.out.println("汇总====totalNum=="+totalNum+"11
					// "+commInfo
					// .getCommTotalPrice()+" 22
					// ="+CommonUtils.getDoubleByDigit(
					// CommonUtils.getDoubleExceptNull(commInfo
					// .getCommTotalPrice()), totalNum));
					existcommInfo.setCommTotalPrice(CommonUtils
							.getDoubleByDigit(CommonUtils
									.getDoubleExceptNull(existcommInfo
											.getCommTotalPrice()), totalNum));
					existcommInfo.setWorkUsd(CommonUtils.getDoubleByDigit(
							CommonUtils.getDoubleExceptNull(existcommInfo
									.getWorkUsd()), totalNum));
					// 第一法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getLegalUnit()
											.getName())) {
						existcommInfo.setFirstAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getLegalUnit() != null
							&& existcommInfo.getLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setFirstAmount(existcommInfo
								.getNetWeight());
					}
					// 第二法定数量
					if (existcommInfo.getUnit() != null
							&& existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo
									.getUnit()
									.getName()
									.equals(existcommInfo.getSecondLegalUnit()
											.getName())) {
						existcommInfo.setSecondAmount(existcommInfo
								.getCommAmount());
					} else if (existcommInfo.getSecondLegalUnit() != null
							&& existcommInfo.getSecondLegalUnit().getName()
									.equals("千克")) {
						existcommInfo.setSecondAmount(existcommInfo
								.getNetWeight());
					}
					((EncDao) this.getBaseEncDao())
							.saveCustomsDeclarationCommInfo(existcommInfo);
					commInfoid = existcommInfo.getId();

				}
			}
			afterinfo.setIsTransferCustomsBill(new Boolean(true));// 已转报关单
			((EncDao) this.getBaseEncDao()).saveAtcMergeAfterComInfo(afterinfo);
			// 保存到中间表中----------------------------------------------
			MakeListToCustoms obj = new MakeListToCustoms();
			obj.setAtcMergeAfterComInfo(afterinfo);
			if (existcommInfo != null) {
				obj.setCustomsInfo((CustomsDeclarationCommInfo) existcommInfo);
			} else {
				obj.setCustomsInfo(commInfo);
			}
			obj.setCompany(CommonUtils.getCompany());
			((EncDao) this.getBaseEncDao()).saveMakeListToCustoms(obj);
			// 记录添加过程------------------------------------------------
			CustomsFromMateriel obj1 = new CustomsFromMateriel();
			obj1.setInfoid(commInfoid);
			/*
			 * obj1.setBcusexgbill(exg); obj1.setBcusimgbill(img);
			 */
			obj1.setBcusexgNo(exg == null ? null : exg.getSeqNum());
			obj1.setBcusimgNo(img == null ? null : img.getSeqNum());
			obj1.setScmCoc(customs.getCustomer());
			obj1.setCompany(CommonUtils.getCompany());
			((EncDao) this.getBaseEncDao()).SaveCustomsFromMateriel(obj1);
			// ----------------------------------------------------------
		}

		CompanyOther other = ((EncDao) this.getBaseEncDao())
				.saveCommonUtilsOther();
		// 保存报关单头的隋附单据
		if (other != null) {
			if (other.getIsCustomAutoAttachedBill() != null
					&& other.getIsCustomAutoAttachedBill()) {
				getAttachedBill(customs);
			}
		}
		/**
		 * 是否自动计算报关单件数，毛重及净重
		 * 
		 */
		if (other != null) {
			if (other.getIsAutoWeight() != null && other.getIsAutoWeight()) {
				((EncDao) this.getBaseEncDao()).getPiceAndWeight(customs);
			}
		}

		// -----------------------------------设置报关单汇率
		Double dou = this.getCurrRateByCurr(customs.getCurrency(),
				customs.getDeclarationDate(), customs.getEmsHeadH2k());
		if (dou != null) {
			customs.setExchangeRate(dou);
		}// 设置报关单汇率
		this.baseCodeDao.saveOrUpdate(customs);

		return customs;
	}
}