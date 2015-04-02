/*
 * Created on 2004-7-29
 *getImg
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.logic;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.StatCusNameRelationMt;
import com.bestway.bcus.checkcancel.dao.CheckCancelDao;
import com.bestway.bcus.checkcancel.entity.CancelCusCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelCusExgBefore;
import com.bestway.bcus.checkcancel.entity.CancelCusExgResult;
import com.bestway.bcus.checkcancel.entity.CancelCusHead;
import com.bestway.bcus.checkcancel.entity.CancelCusImgAvgPrice;
import com.bestway.bcus.checkcancel.entity.CancelCusImgBefore;
import com.bestway.bcus.checkcancel.entity.CancelCusImgResult;
import com.bestway.bcus.checkcancel.entity.CancelCusUnitWear;
import com.bestway.bcus.checkcancel.entity.CancelCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelExgBefore;
import com.bestway.bcus.checkcancel.entity.CancelExgResult;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CancelImgAvgPrice;
import com.bestway.bcus.checkcancel.entity.CancelImgBefore;
import com.bestway.bcus.checkcancel.entity.CancelImgResult;
import com.bestway.bcus.checkcancel.entity.CancelOwnerCustomsDeclara;
import com.bestway.bcus.checkcancel.entity.CancelOwnerExgBefore;
import com.bestway.bcus.checkcancel.entity.CancelOwnerExgResult;
import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.bcus.checkcancel.entity.CancelOwnerImgAvgPrice;
import com.bestway.bcus.checkcancel.entity.CancelOwnerImgBefore;
import com.bestway.bcus.checkcancel.entity.CancelOwnerImgResult;
import com.bestway.bcus.checkcancel.entity.CancelOwnerUnitWear;
import com.bestway.bcus.checkcancel.entity.CancelUnitWear;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccount;
import com.bestway.bcus.checkcancel.entity.CheckOwnerAccountComport;
import com.bestway.bcus.checkcancel.entity.CheckParameter;
import com.bestway.bcus.checkcancel.entity.FactoryCheckExg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckExgConverImg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckImg;
import com.bestway.bcus.checkcancel.entity.FactoryCheckImgResult;
import com.bestway.bcus.checkcancel.entity.FactoryStoryProduct;
import com.bestway.bcus.checkcancel.entity.TempCancelImgAvgPrice;
import com.bestway.bcus.checkcancel.entity.TempDD;
import com.bestway.bcus.checkcancel.entity.TempEmsHeadH2kBom;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Deduc;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.ErrorMessage;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * @author Administrator checked by kcb 2008/11/7 电子帐册数据报核逻辑层
 */
@SuppressWarnings("unchecked")
public class CheckCancelLogic {
	/**
	 * 数据报核DAO
	 */
	private CheckCancelDao checkCancelDao = null;
	/**
	 * 公共基础代码DAO
	 */
	private CommonBaseCodeDao commonBaseCodeDao;
	/**
	 * 经营范围DAO类
	 */
	private EmsEdiTrDao emsEdiTrDao = null;
	/**
	 * 存放 料件耗用的 Hashtable
	 */
	private Hashtable hs = new Hashtable();
	/**
	 * 存放 成品的 Hashtable
	 */
	private Hashtable cpHs = new Hashtable();// 成品

	/**
	 * 设置emsEdiTrDao
	 * 
	 * @param emsEdiTrDao
	 */
	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	/**
	 * @return 获取 checkCancelDao.
	 */
	public CheckCancelDao getCheckCancelDao() {
		return checkCancelDao;
	}

	/**
	 * @param 设置
	 *            checkCancelDao
	 */
	public void setCheckCancelDao(CheckCancelDao checkCancelDao) {
		this.checkCancelDao = checkCancelDao;
	}

	/**
	 * key :报关名称+"/"+规格+"/"+单位名称 存放StatCusNameRelationMt的MAP
	 */
	private Map<String, List<StatCusNameRelationMt>> statCusNameRelationMtListMap = null;

	/**
	 * key: ptNo 存放StatCusNameRelationMt的MAP
	 */
	private Map<String, StatCusNameRelationMt> statCusNameRelationMtValueMap = null;

	/**
	 * F26入库成品-出库成品
	 */
	private Map<String, Double> billDetailF26Map = null;
	/**
	 * 存放版本信息的Hashtable
	 */
	private Hashtable<String, VersionInfo> hsVersionInfo = new Hashtable<String, VersionInfo>(); // 成品号

	/**
	 * 版本单位用量
	 */
	private Hashtable<String, CheckOwnerAccount> resultHash = new Hashtable<String, CheckOwnerAccount>();
	/**
	 * 存放返回结果集
	 */
	private Hashtable resultCpHash = new Hashtable();
	/**
	 * 存放所有海关
	 */
	private Hashtable hsCustoms = null;
	/**
	 * 存放平均单价
	 */
	// private Hashtable avePriceHs = new Hashtable();
	/**
	 * 存放总净重
	 */
	private Hashtable totalWeightHs = new Hashtable();

	// /**
	// * 存放退料出口总数量
	// */
	// private Hashtable backMaterielAmountByTypeHs = new Hashtable();
	// /**
	// * 存放退料出口总金额
	// */
	// private Hashtable backMaterielMoneyByTypeHs = new Hashtable();

	/**
	 * 判断是否为空，如果为空则返回0
	 */
	private Double fd(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 获取deducHsI
	 */
	private Map<String, Deduc> getDeducI() {
		// if (deducHsI != null) {
		// deducHsI.clear();
		// }
		Map<String, Deduc> deducHsI = new HashMap<String, Deduc>();
		List list = this.checkCancelDao.findAllDeduc("I");
		for (int i = 0; i < list.size(); i++) {
			Deduc obj = (Deduc) list.get(i);
			deducHsI.put(obj.getTradeCode(), obj);
		}
		return deducHsI;
	}

	/**
	 * 设置deducHsE
	 */
	private Map<String, Deduc> getDeducE() {
		// if (deducHsE != null) {
		// deducHsE.clear();
		// }
		Map<String, Deduc> deducHsE = new HashMap<String, Deduc>();
		List list = this.checkCancelDao.findAllDeduc("E");
		for (int i = 0; i < list.size(); i++) {
			Deduc obj = (Deduc) list.get(i);
			deducHsE.put(obj.getTradeCode(), obj);
		}
		return deducHsE;
	}

	/**
	 * 料件重新获取中间过程
	 * 
	 * @param cancelHead
	 *            核销表头
	 * @param isOwner
	 *            自用还是海关核销
	 * @param obj
	 *            归并前料件
	 * @param hsCustoms
	 *            存放报关单号的Hashtable
	 * @return
	 */
	public List getImgList(CancelHead cancelHead, boolean isOwner,
			CancelImgBefore obj, Hashtable hsCustoms, ProgressInfo progressInfo) {
		if (progressInfo != null) {
			progressInfo.setMethodName("正在获取料件的中间过程资料--从报关单中获取进出口资料");
		}
		List list = checkCancelDao.findImpExpCommInfo(
				cancelHead.getBeginDate(), cancelHead.getEndDate(), false,
				new Boolean(true), cancelHead, isOwner);
		Map<String, Deduc> deducHsI = getDeducI();
		Map<String, Deduc> deducHsE = getDeducE();
		Hashtable ht = new Hashtable();
		if (progressInfo != null) {
			progressInfo.setTotalNum(list.size());
			progressInfo.setCurrentNum(0);
			progressInfo.setMethodName("正在获取料件的中间过程资料");
		}
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			String customsNo = info.getBaseCustomsDeclaration()
					.getCustomsDeclarationCode();
			if (customsNo == null || hsCustoms.get(customsNo) == null) {
				continue;
			}
			String no = String.valueOf(info.getCommSerialNo());// 商品序号
			String trade = info.getBaseCustomsDeclaration().getTradeMode() == null ? null
					: info.getBaseCustomsDeclaration().getTradeMode().getCode();// 贸易方式代码
			String tradeMode = info.getBaseCustomsDeclaration().getTradeMode() == null ? null
					: info.getBaseCustomsDeclaration().getTradeMode().getName();// 贸易方式名称
			String key = no + "/" + trade + "/"
					+ info.getBaseCustomsDeclaration().getImpExpFlag();
			Deduc deduc = null;
			if (info.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.IMPORT) {
				// deduc = checkCancelDao.findDeducByTradeCode(trade, "I");
				deduc = (Deduc) deducHsI.get(trade);
			} else if (info.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.EXPORT) {
				// deduc = checkCancelDao.findDeducByTradeCode(trade, "E");
				deduc = (Deduc) deducHsE.get(trade);
			}
			if (deduc == null) {
				continue;
			}
			if (ht.get(key) != null) { // 查到
				if (isOwner) {
					obj = (CancelOwnerImgBefore) ht.get(key);
				} else {
					obj = (CancelCusImgBefore) ht.get(key);
				}

			} else {
				if (isOwner) {
					obj = new CancelOwnerImgBefore();
				} else {
					obj = new CancelCusImgBefore();
				}

				obj.setCancelHead(cancelHead);
				obj.setEmsSeqNum(Integer.valueOf(no));
				obj.setTradeMode(tradeMode);
				if (deduc != null) {
					obj.setCheckCode(deduc.getCode());
					obj.setCheckName(deduc.getName());
					obj.setCheckWay(deduc.getDeducMark());
					obj.setCheckModeShow(deduc.getDeducNote());
				}
				ht.put(key, obj);
			}
			obj.setNum(fd(obj.getNum()) + fd(info.getCommAmount()));// 数量
			Double totalMoney = fd(info.getDollarTotalPrice());
			obj.setPrice(fd(obj.getPrice()) + totalMoney);// 金额
			obj.setCompany(CommonUtils.getCompany());
//			 this.checkCancelDao.saveOrUpdate(obj);
			if (progressInfo != null) {
				progressInfo.setCurrentNum(i + 1);
			}
		}
		// this.checkCancelDao.findCancelImgBefore(cancelHead, isOwner);
		return new Vector(ht.values());
	}

	/**
	 * 获取hsCustoms
	 */
	private void getHsCustoms(CancelHead cancelHead, boolean isOwner) {
		List listCustoms = checkCancelDao.findCancelCustomsDeclara(cancelHead,
				isOwner);
		if (hsCustoms != null) {
			hsCustoms.clear();
		} else {
			hsCustoms = new Hashtable();
		}
		for (int k = 0; k < listCustoms.size(); k++) {
			CancelCustomsDeclara cusObj = (CancelCustomsDeclara) listCustoms
					.get(k);
			if (cusObj.getCustomNo() == null || "".equals(cusObj.getCustomNo())) {
				continue;
			}
			hsCustoms.put(cusObj.getCustomNo(), cusObj.getCustomNo());
		}
	}

	/**
	 * 重新获取得到料件中间过程
	 * 
	 * @param cancelHead
	 *            核销表头
	 * @param isOwner
	 *            自用还是海关核销
	 */

	public void getImg(CancelHead cancelHead, boolean isOwner, String taskId) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取报关单资料");
		}
		getHsCustoms(cancelHead, isOwner);//7-9
		List list = null;
		if (info != null) {
			info.setMethodName("正在获取料件的中间过程资料");
		}
		if (isOwner) {
			CancelOwnerImgBefore obj = null;
			list = getImgList(cancelHead, isOwner, obj, hsCustoms, info);
			if (info != null) {
				info.setTotalNum(list.size());
				info.setCurrentNum(0);
			}
			for (int i = 0; i < list.size(); i++) {
				CancelOwnerImgBefore obj1 = (CancelOwnerImgBefore) list.get(i);
				checkCancelDao.saveCancelImgBefore(obj1);
				if (info != null) {
					info.setCurrentNum(i + 1);
				}
			}
		} else {
			CancelCusImgBefore obj = null;
			list = getImgList(cancelHead, isOwner, obj, hsCustoms, info);
			if (info != null) {
				info.setTotalNum(list.size());
				info.setCurrentNum(0);
			}
			for (int i = 0; i < list.size(); i++) {
				CancelCusImgBefore obj1 = (CancelCusImgBefore) list.get(i);
				checkCancelDao.saveCancelImgBefore(obj1);
				if (info != null) {
					info.setCurrentNum(i + 1);
				}
			}
		}
	}

	/**
	 * 重新获取得到成品中间过程
	 * 
	 * @param cancelHead
	 *            核销表头
	 * @param isOwner
	 *            自用还是海关核销
	 */

	public void getExg(CancelHead cancelHead, boolean isOwner) {
		getHsCustoms(cancelHead, isOwner);
		List list = null;
		if (isOwner) {
			CancelOwnerExgBefore obj = null;
			list = getExgList(cancelHead, isOwner, obj, hsCustoms);
			for (int i = 0; i < list.size(); i++) {
				CancelOwnerExgBefore obj1 = (CancelOwnerExgBefore) list.get(i);
				checkCancelDao.saveCancelExgBefore(obj1);
			}
		} else {
			CancelCusExgBefore obj = null;
			list = getExgList(cancelHead, isOwner, obj, hsCustoms);
			for (int i = 0; i < list.size(); i++) {
				CancelCusExgBefore obj1 = (CancelCusExgBefore) list.get(i);
				checkCancelDao.saveCancelExgBefore(obj1);
			}
		}

	}

	/**
	 * 成品重新获取中间过程
	 * 
	 * @param cancelHead
	 *            核销表头
	 * @param isOwner
	 *            自用还是海关核销
	 * @param obj
	 *            归并前料件
	 * @param hsCustomss
	 *            存放报关单的HashTable
	 * @return
	 */
	public List getExgList(CancelHead cancelHead, boolean isOwner,
			CancelExgBefore obj, Hashtable hsCustomss) {
		List list = checkCancelDao.findImpExpCommInfo(
				cancelHead.getBeginDate(), cancelHead.getEndDate(), true,
				new Boolean(true), cancelHead, isOwner);
		Map<String, Deduc> deducHsI = getDeducI();
		Map<String, Deduc> deducHsE = getDeducE();
		Hashtable ht = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(i);
			String customsNo = info.getBaseCustomsDeclaration()
					.getCustomsDeclarationCode();
			if (customsNo == null || hsCustoms.get(customsNo) == null) {
				continue;
			}
			String no = String.valueOf(info.getCommSerialNo());// 商品序号
			String trade = info.getBaseCustomsDeclaration().getTradeMode() == null ? null
					: info.getBaseCustomsDeclaration().getTradeMode().getCode();// 贸易方式代码
			String tradeMode = info.getBaseCustomsDeclaration().getTradeMode() == null ? null
					: info.getBaseCustomsDeclaration().getTradeMode().getName();// 贸易方式名称
			String key = no + "/" + trade + "/"
					+ info.getBaseCustomsDeclaration().getImpExpFlag();
			Deduc deduc = null;
			if (info.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.IMPORT) {
				// deduc = checkCancelDao.findDeducByTradeCode(trade, "I");
				deduc = (Deduc) deducHsI.get(trade);
			} else if (info.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.EXPORT) {
				// deduc = checkCancelDao.findDeducByTradeCode(trade, "E");
				deduc = (Deduc) deducHsE.get(trade);
			}
			if (deduc == null) {
				continue;
			}
			if (ht.get(key) != null) { // 查到
				if (isOwner) {
					obj = (CancelOwnerExgBefore) ht.get(key);
				} else {
					obj = (CancelCusExgBefore) ht.get(key);
				}

			} else {
				if (isOwner) {
					obj = new CancelOwnerExgBefore();
				} else {
					obj = new CancelCusExgBefore();
				}
				obj.setCancelHead(cancelHead);
				obj.setEmsSeqNum(Integer.valueOf(no));
				obj.setTradeMode(tradeMode);
				if (deduc != null) {
					obj.setCheckCode(deduc.getCode());
					obj.setCheckName(deduc.getName());
					obj.setCheckWay(deduc.getDeducMark());
					obj.setCheckModeShow(deduc.getDeducNote());
				}
				ht.put(key, obj);
			}
			obj.setNum(fd(obj.getNum()) + fd(info.getCommAmount()));// 数量
			Double totalMoney = fd(info.getDollarTotalPrice());
			obj.setPrice(fd(obj.getPrice()) + totalMoney);// 金额
		}
		return new Vector(ht.values());
	}

	/**
	 * 如果值为NULL，则返回空字符
	 */
	private String getStr(Object obj) {
		if (obj == null)
			return "";
		return String.valueOf(obj);
	}

	/**
	 * 四舍五入，保留NUM位小数位
	 */
	public Double formatBig(Object num) {
		// int digits = 5;
		double unroundedValue = 0;
		if (num == null) {
			return Double.valueOf(0.0);
		}
		unroundedValue = Double.parseDouble(num.toString());
		System.out.println("@@@@" + unroundedValue);
		System.out.println("###" + num);
		return CommonUtils.getDoubleByDigit(unroundedValue, 5);
		// int x = 1;
		// for (int i = 0; i < digits; i++) {
		// x = x * 10;
		// }
		// double intPortion = Math.floor(unroundedValue);
		// double unroundedDecimalPortion = (unroundedValue - intPortion);
		// double roundedDecimalPortion = Math.round(unroundedDecimalPortion *
		// x);
		// roundedDecimalPortion = roundedDecimalPortion / x;
		// double total = intPortion + roundedDecimalPortion;
		// return new Double(total);
	}

	/**
	 * 如果值为NULL，则返回0
	 */
	private Double getDou(Object obj) {
		if (obj == null)
			return Double.valueOf(0);
		return (Double) obj;
	}

	/**
	 * 取得进出口报关单商品明细的金额运费与保费的美元总价
	 * 
	 * @return
	 */
	public Double getTotalPrices(List list, List listCustomsDeclara,String preniumPrice) {
		double totalprice = 0;
		if (list == null || list.size() <= 0) {
			return totalprice;
		} else {
			for (int j = 0; j < listCustomsDeclara.size(); j++) {
				CustomsDeclaration customsDeclaration = (CustomsDeclaration) listCustomsDeclara
						.get(j);
				Double prices = 0.0;
				for (int i = 0; i < list.size(); i++) {
					CustomsDeclarationCommInfo commInfo = (CustomsDeclarationCommInfo) list
							.get(i);
					if (customsDeclaration.getCustomsDeclarationCode().equals(
							commInfo.getBaseCustomsDeclaration()
									.getCustomsDeclarationCode())) {
						if (commInfo.getCommTotalPrice() != null) {
							BigDecimal b = new BigDecimal(commInfo
									.getCommTotalPrice().doubleValue());
							Double prices1 = b.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue();
							prices = prices + prices1;
							BigDecimal c = new BigDecimal(prices.doubleValue());
							prices = c.setScale(2, BigDecimal.ROUND_HALF_UP)
									.doubleValue();
						}
					}
				}
				if (preniumPrice != null && "1".equals(preniumPrice)) {
					prices = prices
							+ calcCIF(
									"1",
									String.valueOf(customsDeclaration
											.getFreightageType()),
									customsDeclaration.getTransac() == null ? ""
											: customsDeclaration.getTransac()
													.getName(),
									customsDeclaration.getFreightage(),
									customsDeclaration.getGrossWeight(), prices);
					prices = prices
							+ calcCIF(
									"3",
									String.valueOf(customsDeclaration
											.getInsuranceType()),
									customsDeclaration.getTransac() == null ? ""
											: customsDeclaration.getTransac()
													.getName(),
									customsDeclaration.getInsurance(),
									customsDeclaration.getGrossWeight(), prices);
				}
				Double rate = CommonUtils
						.getDoubleExceptNull(customsDeclaration
								.getExchangeRate());
				if (rate != null && rate > 0.0) {
					totalprice = totalprice + (prices * rate);
				} else {
					totalprice = totalprice + prices;
				}
			}
			return totalprice;
		}
	}

	/**
	 * Arguments: CalcType：可选1和2，1指运费，2指保费 DataType：指运费或保费它的子项 CJFS : 成交方式 FY :
	 * 费用/费率 ZL : 重量 ZJE ：报关单商品总金额 Result: 计算后的金额
	 */
	public Double calcCIF(String calcType, String dataType, String cjfs,
			Double fy, Double zl, Double zje) {
		if (fy == null) {
			fy = new Double(0);
		}
		if (zl == null) {
			zl = new Double(0);
		}
		if (zje == null) {
			zje = new Double(0);
		}
		if ("".equals(dataType)) {
			return new Double(0);
		}
		if ("1".equals(calcType)) {
			if ("1".equals(dataType)) {
				return (fy / 100) * zje;
			} else if ("2".equals(dataType)) {
				return (zl / 1000) * fy;
			} else if ("3".equals(dataType)) {
				return fy;
			}
		} else {
			if ("1".equals(dataType)) {
				if ("C&F".equals(cjfs)) {
					return zje / (1 - fy / 100) - zje; // (FOB+运费) / (1-保险费率)
				} else {
					return zje / (1 - fy / 100) - zje;// 算法改为同上。Jack/2004-9-7//(FY
					// / 100) * ZJE;
				}
			} else if ("3".equals(dataType)) {
				return fy;
			}
		}
		//正式报核递单，海关直接查看进出口总金额跟报关单进出口总金额是否一致，国税局退税财务在此基础上自行扣减运保费
		return new Double(0);
	}

	/**
	 * 计算核销表头本期进出口总金额
	 * 
	 * @param cancelHead
	 * @param impExpFlag
	 * @return
	 */
	public Double getImpExpTotalMoney(CancelHead cancelHead, String impExpFlag,
			boolean isOwner) {
		getHsCustoms(cancelHead, isOwner);
		Double totalMoney = Double.valueOf(0);
		Hashtable hsIE = new Hashtable();
		List deducList = checkCancelDao.findDeducByType(impExpFlag);
		for (int i = 0; i < deducList.size(); i++) {
			Deduc obj = (Deduc) deducList.get(i);
			String keys = obj.getTradeCode();
			if (keys == null || "".equals(keys)) {
				continue;
			}
			if (hsIE.get(keys) == null) {
				hsIE.put(keys, obj);
			}
		}
		List list = checkCancelDao.findAllCustomsList(
				cancelHead.getBeginDate(), cancelHead.getEndDate(),
				new Boolean(true));
		for (int j = 0; j < list.size(); j++) {
			CustomsDeclarationCommInfo info = (CustomsDeclarationCommInfo) list
					.get(j);
			String customsNo = info.getBaseCustomsDeclaration()
					.getCustomsDeclarationCode();
			if (hsCustoms.get(customsNo) == null) {
				continue;
			}
			// 需要检查报关单在核销报关单中
			String tradeCode = info.getBaseCustomsDeclaration().getTradeMode() == null ? null
					: info.getBaseCustomsDeclaration().getTradeMode().getCode();
			if (tradeCode == null || "".equals(tradeCode)) {
				continue;
			}
			if (hsIE.get(tradeCode) != null) {
				Deduc d = (Deduc) hsIE.get(tradeCode);
				Double money = fd(info.getDollarTotalPrice());
				if (d.getDeducMark() != null && d.getDeducMark().equals("+")) {
					totalMoney = totalMoney + money;
				} else if (d.getDeducMark() != null
						&& d.getDeducMark().equals("-")) {
					totalMoney = totalMoney - money;
				}
			}
		}
		return totalMoney;
	}

	/**
	 * 计算核销报表头
	 * 
	 * @param cancelHead
	 * @param isOwner
	 * @return
	 */
	public CancelHead fillCancelHeadData(CancelHead cancelHead, boolean isOwner) {
		List exgNum = null;
		List imgNum = null;
		exgNum = checkCancelDao.findCancelExgResult(cancelHead, isOwner);
		imgNum = checkCancelDao.findCancelImgResult(cancelHead, isOwner);
		System.out.println(emsEdiTrDao);
		String imgImpTotalMoneyControl = emsEdiTrDao
				.getBpara(BcusParameter.ImgImpTotalMoney);
		//2014-3-20 表头进出口金额是否包含报关单表体运保费
		String  preniumPrice = emsEdiTrDao
				.getBpara(BcusParameter.Premium_Price);
		cancelHead.setDeclareExgNum(exgNum.size()); // 报核成品数量
		cancelHead.setDeclareImgNum(imgNum.size()); // 报核料件数量
		// 计算本期进口总金额
		// List cancelCustomsDeclara = checkCancelDao.getbaseCustomsDeclaration(
		// new Boolean(true), cancelHead, isOwner, new Integer[] {
		// ImpExpType.DIRECT_IMPORT,
		// ImpExpType.TRANSFER_FACTORY_IMPORT,
		// ImpExpType.REMAIN_FORWARD_IMPORT }, null);
		// List listimgMoneyBefore = checkCancelDao.getTotalMoneyToBefore(
		// new Boolean(true), false, cancelHead, isOwner);
		//
		// Double imgImpTotalMoney = getTotalPrices(listimgMoneyBefore,
		// cancelCustomsDeclara);// 进

		/**
		 * [注释//报核表头本期进口总金额去掉] hwy2013-2-1
		 * 表头的期末结余金额=本期期初金额+本期进口金额（报关单）-耗用金额=期末结余金额。
		 * 但下一期的期初金额是上一期料件表算出来的=上一期料件表中的结余金额汇总。导致下一期的表头期初金额不等于上一期的结余金额
		 * hwy2013-4-23计算公式修改如下 表头本期进口金额=料件表本期（应剩余金额）总金额+出口耗用总金额-本期期初总金额
		 * hwy2013-4-47因台达要求，本期进口总金额直接取（报关单核增-核减金额）
		 */
		List list = checkCancelDao.getOnlyEndMoney(cancelHead, isOwner);
		Double leaveSumPrice = 0.0;
		if (list != null && list.size() > 0 && list.get(0) != null) {
			Object[] object = (Object[]) list.get(0);

			leaveSumPrice = object[0] == null ? 0.0 : (Double) object[0];

			if (isOwner) {
				// Double endMoney = getDou(cancelHead.getEndBalanceImgMoney());
				cancelHead
						.setEndBalanceImgMoney(formatBig(object[1] == null ? 0.0
								: (Double) object[1]));
			} else {
				// Double endMoney =
				// getDou(cancelHead.getSimulateEndBalanceImgMoney());
				cancelHead
						.setSimulateEndBalanceImgMoney(object[1] == null ? 0.0
								: (Double) object[1]);
			}
		}

		// 余料结转出口
		List imgcancelCustomsDeclara = checkCancelDao
				.getbaseCustomsDeclaration(new Boolean(true), cancelHead,
						isOwner,
						new Integer[] { ImpExpType.REMAIN_FORWARD_EXPORT },
						null);
		List listimgMoneyAfter = checkCancelDao.getTotalMoneyToAfter(
				new Boolean(true), false, cancelHead, isOwner);
		Double remainExportMoney = getTotalPrices(listimgMoneyAfter,
				imgcancelCustomsDeclara,preniumPrice);
		// 自用核销
		if (isOwner) {
			// 余料结转出口
			cancelHead.setRemainExportMoney(formatBig(remainExportMoney));
		} else {
			cancelHead
					.setSimulateRemainExportMoney(formatBig(remainExportMoney));
		}
		// 计算退运料件金额
		List exitcancelCustomsDeclara = checkCancelDao
				.getbaseCustomsDeclaration(new Boolean(true), cancelHead,
						isOwner,
						new Integer[] { ImpExpType.BACK_MATERIEL_EXPORT },
						new String[] { "0265", "0664", "0700", "0300" });
		List listexitImgMoney = checkCancelDao.findCommMoneyByType(new Boolean(
				true), ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
				"0664", "0700", "0300" }, cancelHead, isOwner);
		Double imgBackExportMoney = getTotalPrices(listexitImgMoney,
				exitcancelCustomsDeclara,preniumPrice);
		// 自用核销
		if (isOwner) {
			// 退运料件金额
			cancelHead.setExitImgMoney(formatBig(imgBackExportMoney));
		} else {
			cancelHead.setSimulateExitImgMoney(formatBig(imgBackExportMoney));
		}

		// 计算本期出口总金额
		List exgcancelCustomsDeclara = checkCancelDao
				.getbaseCustomsDeclaration(new Boolean(true), cancelHead,
						isOwner, new Integer[] { ImpExpType.DIRECT_EXPORT,
								ImpExpType.TRANSFER_FACTORY_EXPORT,
								ImpExpType.REWORK_EXPORT }, null);
		List listexgMoneyBefore = checkCancelDao.getTotalMoneyToBefore(
				new Boolean(true), true, cancelHead, isOwner);
		Double exgMoneyBefore = getTotalPrices(listexgMoneyBefore,
				exgcancelCustomsDeclara,preniumPrice);

		List exgcancelCustomsDeclara1 = checkCancelDao
				.getbaseCustomsDeclaration(new Boolean(true), cancelHead,
						isOwner,
						new Integer[] { ImpExpType.BACK_FACTORY_REWORK }, null);
		List listexgMoneyAfter = checkCancelDao.getTotalMoneyToAfter(
				new Boolean(true), true, cancelHead, isOwner);
		Double exgMoneyAfter = getTotalPrices(listexgMoneyAfter,
				exgcancelCustomsDeclara1,preniumPrice);
		// 自用核销
		if (isOwner) {
			cancelHead.setThisOutportMoney(formatBig(exgMoneyBefore
					- exgMoneyAfter));
		} else {
			cancelHead.setSimulateThisOutportMoney(formatBig(exgMoneyBefore
					- exgMoneyAfter));
		}
		// 计算期初总金额
		Double beginMoney = checkCancelDao.getBeginMoney(cancelHead, isOwner);
		if (isOwner) {
			cancelHead.setBeginImgMoney(formatBig(beginMoney));
		} else {
			cancelHead.setSimulateBeginImgMoney(formatBig(beginMoney));
		}
		// 计算内销总金额
		List innercancelCustomsDeclara = checkCancelDao
				.getbaseCustomsDeclaration(new Boolean(true), cancelHead,
						isOwner,
						new Integer[] { ImpExpType.MATERIAL_DOMESTIC_SALES },
						null);

		List innercancelCustomsDeclarasRemian = checkCancelDao
				.getbaseCustomsDeclaration(
						new Boolean(true),
						cancelHead,
						isOwner,
						new Integer[] { ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES },
						null);
		List listinnerMoney = checkCancelDao.findCommMoneyByType(new Boolean(
				true), ImpExpType.MATERIAL_DOMESTIC_SALES, cancelHead, isOwner);
		Double innerSaleMoney = getTotalPrices(listinnerMoney,
				innercancelCustomsDeclara,preniumPrice);
		List listinnerMoneyRemian = checkCancelDao.findCommMoneyByType(
				new Boolean(true), ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES,
				cancelHead, isOwner);
		Double innerRemianMoney = getTotalPrices(listinnerMoneyRemian,
				innercancelCustomsDeclarasRemian,preniumPrice);
		if (isOwner) {
			cancelHead.setInnerCancelMoney(formatBig(innerSaleMoney));
		} else {
			cancelHead.setSimulateInnerCancelMoney(formatBig(innerSaleMoney));
		}
		// 本期进口总金额 ----- 本期进口总金额是否扣减退料出口金额
		// if (imgImpTotalMoneyControl != null
		// && "1".equals(imgImpTotalMoneyControl)) {
		// if (isOwner) {
		// cancelHead.setThisInportMoney(formatBig(imgImpTotalMoney
		// - imgBackExportMoney - innerSaleMoney));
		// } else {
		// cancelHead
		// .setSimulateThisInportMoney(formatBig(imgImpTotalMoney - //
		// 本期进口总金额-退料-内销
		// imgBackExportMoney - innerSaleMoney));
		// }
		// } else {
		// if (isOwner) {
		// cancelHead.setThisInportMoney(formatBig(imgImpTotalMoney));
		// } else {
		// cancelHead
		// .setSimulateThisInportMoney(formatBig(imgImpTotalMoney));
		// }
		// }
		// 计算出口成品耗用料件总金额
		Double useImgMoney = checkCancelDao.getExportUseMoney(cancelHead,
				isOwner);
		if (isOwner) {
			cancelHead.setOutportExgUseImgNum(formatBig(useImgMoney));
		} else {
			cancelHead.setSimulateOutportExgUseImgNum(formatBig(useImgMoney));
			Double thisOutportMoney = cancelHead.getSimulateThisOutportMoney();
			// 比例 =（出口成品耗用总金额/本期出口总金额）*100
			// if (!thisOutportMoney.equals(0)) {
			// cancelHead.setScale((useImgMoney / thisOutportMoney) * 100);
			// } else {
			// cancelHead.setScale(0.0);
			// }
		}

		/**
		 * hwy 2013-2-1修改 表头本期进口金额=料件表本期结余总金额+出口耗用总金额-本期期初总金额 2013-4-23再修改
		 * 表头本期进口金额=料件表本期（应剩余金额）总金额+出口耗用总金额-本期期初总金额 2013-4-26修改
		 * 表头本期进口金额=报关单（核增金额-核减金额）
		 */
		Double imgImpTotalMoneyhz = checkCancelDao.getimgImpTotalMoneyhz(
				cancelHead, isOwner);// 查询核增金额
		Double imgImpTotalMoneyhj = checkCancelDao.getimgImpTotalMoneyhj(
				cancelHead, isOwner);// 查询核减金额
		// 计算期末结余总金额 -----本期进口总金额是否扣减退料出口金额
		// Double endMoney = checkCancelDao.getEndMoney(cancelHead, isOwner);
		// 结余金额 = 本期进口总金额 + 期初料件总金额 - 出口成品耗用料件金额 - 内销金额;// - 退运料件金额
		// 2013-4-27 台达要求修改本期进口总金额=报关单核增 - 核减金额
		// if (imgImpTotalMoneyControl != null
		// && "1".equals(imgImpTotalMoneyControl)) {
		// if (isOwner) {
		// Double imgImpTotalMoney = getDou(leaveSumPrice)
		// + getDou(cancelHead.getOutportExgUseImgNum())
		// - imgBackExportMoney
		// - getDou(cancelHead.getInnerCancelMoney())
		// - cancelHead.getBeginImgMoney();
		// cancelHead.setThisInportMoney(formatBig(imgImpTotalMoney));
		// } else {
		// Double imgImpTotalMoney = leaveSumPrice
		// + cancelHead.getSimulateOutportExgUseImgNum()
		// - imgBackExportMoney
		// - cancelHead.getSimulateInnerCancelMoney()
		// - cancelHead.getSimulateBeginImgMoney();
		// cancelHead
		// .setSimulateThisInportMoney(formatBig(imgImpTotalMoney));
		// }
		//
		// } else {
		// if (isOwner) {
		// Double imgImpTotalMoney = getDou(leaveSumPrice)
		// + getDou(cancelHead.getOutportExgUseImgNum())
		// - getDou(cancelHead.getBeginImgMoney());
		// cancelHead.setThisInportMoney(formatBig(imgImpTotalMoney));
		// } else {
		// Double imgImpTotalMoney = leaveSumPrice
		// + cancelHead.getSimulateOutportExgUseImgNum()
		// - cancelHead.getSimulateBeginImgMoney();
		// cancelHead
		// .setSimulateThisInportMoney(formatBig(imgImpTotalMoney));
		// }
		// }
		// hwy2013-4-28 因核减金额中已经包含内销及退运金额，所以当参数设置不设置时，把内销金额跟退运金额加上去，以平衡数据。
		if (imgImpTotalMoneyControl != null
				&& "1".equals(imgImpTotalMoneyControl)) {
			if (isOwner) {
				Double imgImpTotalMoneyn = formatBig(imgImpTotalMoneyhz)
						- formatBig(imgImpTotalMoneyhj);
				cancelHead.setThisInportMoney(formatBig(imgImpTotalMoneyn));
			} else {
				Double imgImpTotalMoneyn = formatBig(imgImpTotalMoneyhz)
						- formatBig(imgImpTotalMoneyhj);

				cancelHead
						.setSimulateThisInportMoney(formatBig(imgImpTotalMoneyn));
			}
		} else {
			if (isOwner) {
				Double imgImpTotalMoneyn = formatBig(imgImpTotalMoneyhz)
						- formatBig(imgImpTotalMoneyhj)
						+ getDou(cancelHead.getInnerCancelMoney())
						+ cancelHead.getExitImgMoney();
				cancelHead.setThisInportMoney(formatBig(imgImpTotalMoneyn));
			} else {
				Double imgImpTotalMoneyn = formatBig(imgImpTotalMoneyhz)
						- formatBig(imgImpTotalMoneyhj)
						+ cancelHead.getSimulateInnerCancelMoney()
						+ cancelHead.getSimulateExitImgMoney();
				cancelHead
						.setSimulateThisInportMoney(formatBig(imgImpTotalMoneyn));
			}
		}

		// 结余金额 = 本期进口总金额 + 期初料件总金额 - 出口成品耗用料件金额 - 内销金额;// - 退运料件金额 2013-2-1修改
		// if (imgImpTotalMoneyControl != null
		// && "1".equals(imgImpTotalMoneyControl)) {-
		//
		// if (isOwner) {
		// Double endMoney = getDou(cancelHead.getThisInportMoney())
		// + getDou(cancelHead.getBeginImgMoney()) - remainExportMoney
		// - getDou(cancelHead.getOutportExgUseImgNum());
		// cancelHead.setEndBalanceImgMoney(formatBig(endMoney));
		// } else {
		// Double endMoney = cancelHead.getSimulateThisInportMoney()
		// + cancelHead.getSimulateBeginImgMoney()- remainExportMoney
		// - cancelHead.getSimulateOutportExgUseImgNum();
		// cancelHead.setSimulateEndBalanceImgMoney(formatBig(endMoney));
		// }
		// } else {
		// if (isOwner) {
		// Double endMoney = getDou(cancelHead.getThisInportMoney())
		// + getDou(cancelHead.getBeginImgMoney()) - remainExportMoney
		// - imgBackExportMoney
		// - getDou(cancelHead.getOutportExgUseImgNum())
		// - getDou(cancelHead.getInnerCancelMoney());
		// cancelHead.setEndBalanceImgMoney(formatBig(endMoney));
		// } else {
		// Double endMoney = cancelHead.getSimulateThisInportMoney()
		// + cancelHead.getSimulateBeginImgMoney()- remainExportMoney
		// - imgBackExportMoney
		// - cancelHead.getSimulateOutportExgUseImgNum()
		// - cancelHead.getSimulateInnerCancelMoney();
		// cancelHead.setSimulateEndBalanceImgMoney(formatBig(endMoney));
		// }
		// }

		checkCancelDao.saveCancelHead(cancelHead);
		return cancelHead;
	}
//	/**
//	 * 计算核销数量
//	 */
//	public Double[] getCancelNum(CancelHead cancelHead,
//			boolean isOwner) {
//		Double[] d = new Double[2];
//		d[0] = Double.valueOf(0.0);
//		d[1] = Double.valueOf(0.0);
//		List list = checkCancelDao.getCancelImgBefore(cancelHead, 
//				isOwner);
//		double totalHz = 0;
//		double totalHj = 0;
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				CancelImgBefore obj = (CancelImgBefore) list.get(i);
//				// 如果进出口类型是边角料批准内销则不进行核减
//				// if ("进料料件内销".equals(obj.getTradeMode())
//				// || "来料料件内销".equals(obj.getTradeMode())) {
//				// continue;
//				// }
//				double num = obj.getNum() == null ? 0 : obj.getNum()
//						.doubleValue();
//				String fh = obj.getCheckWay();
//				if (fh != null && fh.equals("+")) {
//					totalHz = totalHz + num;
//				} else if (fh != null && fh.equals("-")) {
//					totalHj = totalHj + num;
//				}
//			}
//			d[0] = Double.valueOf(totalHz);
//			d[1] = Double.valueOf(totalHj);
//			return d;
//		}
//		return d;
//	}
//	/**
//	 * 计算核销数量
//	 */
//	public Double[] getCancelNum(CancelHead cancelHead, String seqNum,
//			boolean isOwner) {
//		Double[] d = new Double[2];
//		d[0] = Double.valueOf(0.0);
//		d[1] = Double.valueOf(0.0);
//		List list = checkCancelDao.getCancelImgBefore(cancelHead, seqNum,
//				isOwner);
//		double totalHz = 0;
//		double totalHj = 0;
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				CancelImgBefore obj = (CancelImgBefore) list.get(i);
//				// 如果进出口类型是边角料批准内销则不进行核减
//				// if ("进料料件内销".equals(obj.getTradeMode())
//				// || "来料料件内销".equals(obj.getTradeMode())) {
//				// continue;
//				// }
//				double num = obj.getNum() == null ? 0 : obj.getNum()
//						.doubleValue();
//				String fh = obj.getCheckWay();
//				if (fh != null && fh.equals("+")) {
//					totalHz = totalHz + num;
//				} else if (fh != null && fh.equals("-")) {
//					totalHj = totalHj + num;
//				}
//			}
//			d[0] = Double.valueOf(totalHz);
//			d[1] = Double.valueOf(totalHj);
//			return d;
//		}
//		return d;
//	}
	/**
	 * 计算核销数量
	 */
	private void initCancelMoneyAndNum(CancelHead cancelHead, 
			boolean isOwner,Map<Integer,Double[]> mapMoney,Map<Integer,Double[]> mapNum) {
//		Map<Integer,Double[]> map=new HashMap();
		
		// d[2] = Double.valueOf(0.0);
		// d[3] = Double.valueOf(0.0);
		List list = checkCancelDao.getCancelImgBefore(cancelHead,
				isOwner);

		// double totalHzCount = 0;
		// double totalHjCount = 0;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				double totalHzMoney = 0;
				double totalHjMoney = 0;
				
				double totalHzNum = 0;
				double totalHjNum = 0;
				CancelImgBefore obj = (CancelImgBefore) list.get(i);
				// 如果进出口类型是边角料批准内销则不进行核减
				// if ("进料料件内销".equals(obj.getTradeMode())
				// || "来料料件内销".equals(obj.getTradeMode())) {
				// continue;
				// }
				double money = (obj.getPrice() == null ? 0 : obj.getPrice()
						.doubleValue());
				double num = (obj.getNum() == null ? 0 : obj.getNum()
						.doubleValue());
				String fh = obj.getCheckWay();
				if (fh != null && fh.equals("+")) {
					totalHzMoney =  money;
					totalHzNum=num;
					// totalHzCount = totalHzCount + num;
				} else if (fh != null && fh.equals("-")) {
					totalHjMoney =  money;
					totalHjNum=num;
					// totalHjCount = totalHjCount + num;
				}
				
				Double[] tmoney = mapMoney.get(obj.getEmsSeqNum());
				if(tmoney==null){
					tmoney=new Double[4];
					tmoney[0] = Double.valueOf(0.0);
					tmoney[1] = Double.valueOf(0.0);
				}
				tmoney[0] = tmoney[0]+Double.valueOf(totalHzMoney);
				tmoney[1] = tmoney[1]+Double.valueOf(totalHjMoney);
				mapMoney.put(obj.getEmsSeqNum(), tmoney);
				
				
				Double[] tnum = mapNum.get(obj.getEmsSeqNum());
				if(tnum==null){
					tnum=new Double[4];
					tnum[0] = Double.valueOf(0.0);
					tnum[1] = Double.valueOf(0.0);
				}
				tnum[0] = tnum[0]+Double.valueOf(totalHzNum);
				tnum[1] = tnum[1]+Double.valueOf(totalHjNum);
				mapNum.put(obj.getEmsSeqNum(), tnum);
			}	
		
		}
	}
//	/**
//	 * 计算核销数量
//	 */
//	public Double[] getCancelMoney(CancelHead cancelHead, String seqNum,
//			boolean isOwner) {
//		Double[] d = new Double[4];
//		d[0] = Double.valueOf(0.0);
//		d[1] = Double.valueOf(0.0);
//		// d[2] = Double.valueOf(0.0);
//		// d[3] = Double.valueOf(0.0);
//		List list = checkCancelDao.getCancelImgBefore(cancelHead, seqNum,
//				isOwner);
//		double totalHz = 0;
//		double totalHj = 0;
//		// double totalHzCount = 0;
//		// double totalHjCount = 0;
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				CancelImgBefore obj = (CancelImgBefore) list.get(i);
//				// 如果进出口类型是边角料批准内销则不进行核减
//				// if ("进料料件内销".equals(obj.getTradeMode())
//				// || "来料料件内销".equals(obj.getTradeMode())) {
//				// continue;
//				// }
//				double money = (obj.getPrice() == null ? 0 : obj.getPrice()
//						.doubleValue());
//				double num = (obj.getNum() == null ? 0 : obj.getNum()
//						.doubleValue());
//				String fh = obj.getCheckWay();
//				if (fh != null && fh.equals("+")) {
//					totalHz = totalHz + money;
//					// totalHzCount = totalHzCount + num;
//				} else if (fh != null && fh.equals("-")) {
//					totalHj = totalHj + money;
//					// totalHjCount = totalHjCount + num;
//				}
//			}
//			d[0] = Double.valueOf(totalHz);
//			d[1] = Double.valueOf(totalHj);
//			// d[2] = Double.valueOf(totalHzCount);
//			// d[3] = Double.valueOf(totalHjCount);
//			return d;
//		}
//		return d;
//	}

	/**
	 * 计算报核周期总的平均单价
	 */
	private Map<Integer, Double> getImgAveragePrice(EmsHeadH2k emsHeadH2k,
			Date beginDate, Date endDate, Boolean isEffect, boolean isProduct,
			CancelHead cancelHead, boolean isOwner, List listImg,
			Map<Integer, Double> beginNumMap, Map<Integer, Double> beginMoneyMap) {
		Map<Integer, Double> avePriceHs = new HashMap<Integer, Double>();
		List list = this.checkCancelDao.findNumAndMoneyForAveragePrice(
				beginDate, endDate, isEffect, isProduct, cancelHead, isOwner);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Integer seqNum = (objs[0] == null ? null : Integer.parseInt(objs[0]
					.toString()));
			Double beginNum = CommonUtils.getDoubleExceptNull(beginNumMap
					.get(seqNum));
			Double beginMoney = CommonUtils.getDoubleExceptNull(beginMoneyMap
					.get(seqNum));
			Double money = (objs[1] == null ? 0.0 : Double.parseDouble(objs[1]
					.toString()));
			Double num = (objs[2] == null ? 0.0 : Double.parseDouble(objs[2]
					.toString()));

			/*
			 * 当beginNum(上期结余数量)或beginMoney（上期结余金额）任一个或两者为负数同时本期进口数量不小于 0：本期平均单价
			 * = 本期进口金额/本期进口数量，如果任一个或两者为负数同时本期进口数量小于0则为空 否则本期平均单价 =
			 * (上期核销结余金额+本期实际进出口金额)/(上期核销结余数量+本期实际进出口数量)
			 */
			Double avePrice = null;
			if (beginNum < Double.valueOf(0) || beginMoney < Double.valueOf(0)) {// 负数
				if (num > 0 || money >Double.valueOf(0)) {
					// 本期平均单价 = 本期进口金额 / 本期进口数量
					avePrice = money / num;
				}
			} else {
				// 本期平均单价 = (上期核销结余金额+本期实际进出口金额) / (上期核销结余数量+本期实际进出口数量)
				//当进口金额+期初金额小于0时，单价取0
				avePrice = ((beginNum + num  == 0.0 || beginMoney + money < 0.0 )? 0.0
						: ((beginMoney + money) / (beginNum + num)));
			}
			avePriceHs.put(seqNum, avePrice);
		}
		/*
		 * 如果某个序号的料件本期没有数据 初始化为上期的数据。
		 */
		for (int i = 0; i < listImg.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) listImg.get(i);
			Integer seqNum = img.getSeqNum();
			if (avePriceHs.get(seqNum) == null) {
				Double beginNum = CommonUtils.getDoubleExceptNull(beginNumMap
						.get(seqNum));
				Double beginMoney = CommonUtils
						.getDoubleExceptNull(beginMoneyMap.get(seqNum));
				Double avePrice = 0.0;
				if (beginNum > Double.valueOf(0)
						&& beginMoney > Double.valueOf(0)) {// 负数
					avePrice = (beginNum == 0.0 ? 0.0
							: ((beginMoney) / (beginNum)));
				}
				avePriceHs.put(seqNum, avePrice);
			}
		}
		return avePriceHs;
	}

	/**
	 * 获取起初的数量和金额缓存
	 * 
	 * @param isOwner
	 * @param beforeTimes
	 * @param beginNumMap
	 * @param beginMoneyMap
	 */
	private void initBeginNumAndMoney(boolean isOwner, String beforeTimes,
			Map<Integer, Double> beginNumMap, Map<Integer, Double> beginMoneyMap) {
		List list = checkCancelDao.findAllResultNum(isOwner, beforeTimes);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Integer seqNum = (objs[0] == null ? null : Integer.parseInt(objs[0]
					.toString()));
			Double beginNum = (objs[1] == null ? null : Double
					.parseDouble(objs[1].toString()));
			Double beginMoney = (objs[2] == null ? null : Double
					.parseDouble(objs[2].toString()));
			beginNumMap.put(seqNum, beginNum);
			beginMoneyMap.put(seqNum, beginMoney);
		}
	}

	/**
	 * 获取报核周期每月总的平均单价
	 */
	private Map<Integer, TempCancelImgAvgPrice> getImgCancelImgAvgPrice(
			CancelHead cancelHead, boolean isOwner) {
		Map<Integer, TempCancelImgAvgPrice> avePriceHs = new HashMap<Integer, TempCancelImgAvgPrice>();
		List list = this.checkCancelDao.findCancelImgAvgPrice(cancelHead,
				isOwner, null, null);
		for (int i = 0; i < list.size(); i++) {
			CancelImgAvgPrice obj = (CancelImgAvgPrice) list.get(i);
			Integer emsSeqNum = obj.getEmsSeqNum();
			Double leaveMoney = fd(obj.getLeaveMoney());
			Double leaveNum = fd(obj.getLeaveNum());
			Double useNum = fd(obj.getUseNum());
			Double useMoney = fd(obj.getUseMoney());
			Double leftOverImgMoney = fd(obj.getLeftOverImgMoney());
			Double leftOverImgNum = fd(obj.getLeftOverImgNum());

			TempCancelImgAvgPrice temp = null;
			if (avePriceHs.get(emsSeqNum) == null) {
				temp = new TempCancelImgAvgPrice();
				temp.setSeqNum(String.valueOf(emsSeqNum));
				temp.setUseNum(useNum);
				temp.setUseMoney(useMoney);
				temp.setLeaveMoney(leaveMoney);
				temp.setLeaveNum(leaveNum);
				temp.setLeftOverImgMoney(leftOverImgMoney);
				temp.setLeftOverImgNum(leftOverImgNum);
				avePriceHs.put(emsSeqNum, temp);
			} else {
				temp = (TempCancelImgAvgPrice) avePriceHs.get(emsSeqNum);
				temp.setUseNum(fd(temp.getUseNum()) + useNum);
				temp.setUseMoney(fd(temp.getUseMoney()) + useMoney);
				temp.setLeftOverImgNum(fd(temp.getLeftOverImgNum())
						+ leftOverImgNum);
				temp.setLeftOverImgMoney(fd(temp.getLeftOverImgMoney())
						+ leftOverImgMoney);
				temp.setLeaveNum(fd(leaveNum));
				temp.setLeaveMoney(fd(leaveMoney));
			}
		}
		return avePriceHs;
	}

	/**
	 * 计算报核周期每月总的平均单价
	 */
	private List getImgAveragePriceByMonth(EmsHeadH2k head, Date beginDate,
			Date endDate, CancelHead cancelHead, boolean isOwner, String taskId) {
		List listResult = new ArrayList();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取电子帐册料件");
		}
		// 电子帐册料件
		List list = checkCancelDao.findEmsEdiImg(head);
		if (info != null) {
			info.setMethodName("正在已存在的平均单价资料");
		}
		// 先删除已存在的平均单价资料
		checkCancelDao.deleteCancelImgAvgPrice(cancelHead, isOwner, beginDate,
				endDate);
		if (info != null) {
			info.setMethodName("正在计算平均单价资料");
		}
		Map<String, TempCancelImgAvgPrice> commCancelTimesByMonthHs = new HashMap<String, TempCancelImgAvgPrice>();
		// 取得当月的月底日期
		SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Integer cancelTiems = 1;
		List<Date[]> listBeginEndDate = new ArrayList<Date[]>();
		try {
			sdfStart.format(beginDate);
			Calendar clStart = sdfStart.getCalendar();
			sdfEnd.format(endDate);
			Calendar clEnd = sdfEnd.getCalendar();
			while (true) {
				if (clStart.before(clEnd) || clStart.equals(clEnd)) {
					Date[] dates = new Date[2];
					// 结束日期与开始日期
					Date beginMonth = clStart.getTime();
					Date lastMonth = null;
					dates[0] = beginMonth;

					cal.setTime(clStart.getTime());
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1,
							0, 0, 0);
					cal.add(Calendar.MONTH, 1);
					cal.add(Calendar.DAY_OF_MONTH, -1);
					lastMonth = cal.getTime();
					// }
					if (cal.after(clEnd) || clStart.equals(clEnd)) {
						lastMonth = clEnd.getTime();
					}
					dates[1] = lastMonth;
					listBeginEndDate.add(dates);
					// System.out.println("－－－－－开始日期="
					// + sdfStart.format(beginMonth) + ";结束日期="
					// + sdfStart.format(lastMonth) + "-------");
					cal.add(Calendar.DAY_OF_MONTH, 1);
					clStart = (Calendar) cal.clone();
					// clStart.set(clStart.DAY_OF_MONTH, clStart
					// .get(clStart.DAY_OF_MONTH));
					// clStart.add(cal.DAY_OF_MONTH, 1);
					// clStart.set(clStart.DAY_OF_MONTH, clStart
					// .get(clStart.DAY_OF_MONTH));
				} else {
					break;
				}
			}
			if (info != null) {
				info.setBeginTime(System.currentTimeMillis());
				info.setTotalNum(listBeginEndDate.size() * list.size());
				info.setCurrentNum(0);
			}
			// while (true) {
			// if (clStart.before(clEnd) || clStart.equals(clEnd)) {
			for (int d = 0; d < listBeginEndDate.size(); d++) {
				// 结束日期与开始日期
				// Date beginMonth = clStart.getTime();
				// Date lastMonth = null;
				Date[] dates = listBeginEndDate.get(d);
				Date beginMonth = dates[0];
				Date lastMonth = dates[1];
				if (info != null) {
					info.setMethodName("开始计算－－－－－开始日期="
							+ sdfStart.format(beginMonth) + ";结束日期="
							+ sdfStart.format(lastMonth) + "-------");
				}
				System.out.println("开始计算－－－－－开始日期="
						+ sdfStart.format(beginMonth) + ";结束日期="
						+ sdfStart.format(lastMonth) + "-------");

				Double useNum = Double.valueOf(0);
				Double leftOverNum = Double.valueOf(0);
				Double impNum = Double.valueOf(0);
				Double impMoney = Double.valueOf(0);

				Double beginImpNum = Double.valueOf(0);
				Double beginImpMoney = Double.valueOf(0);
				Double beginUseNum = Double.valueOf(0);
				Double beginUseMoney = Double.valueOf(0);
				Double beginleaveNum = Double.valueOf(0);
				Double beginleaveMoney = Double.valueOf(0);
				// 查出本月耗料数量,本月边角料数量
				finLjUseNumByMonth(beginMonth, lastMonth, head, isOwner,
						cancelHead);

				// 查出本月物料的数量
				Map<Integer, TempDD> commAmountByMonthHs = findBaseCustomsDeclarationNum(
						beginMonth, lastMonth, cancelHead, isOwner, head);
				// 查出本月物料的金额
				Map<Integer, TempDD> commMoneyByMonthHs = findBaseCustomsDeclarationMoney(
						beginMonth, lastMonth, cancelHead, isOwner, head);
				// 获取上月的结余数量与金额 耗用数量与耗用金额
				Integer newCancelTiems = cancelTiems - 1;
				// findCancelImgAvgPriceLeave(newCancelTiems);

				for (int i = 0; i < list.size(); i++) {
					EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);

					CancelImgAvgPrice avgPrice = null;
					if (isOwner) {
						avgPrice = new CancelOwnerImgAvgPrice();
					} else {
						avgPrice = new CancelCusImgAvgPrice();
					}
					Integer seqNum = img.getSeqNum();
					TempCancelImgAvgPrice y = (TempCancelImgAvgPrice) commCancelTimesByMonthHs
							.get(String.valueOf(seqNum) + "/"
									+ String.valueOf(newCancelTiems));
					if (y != null) {
						beginImpNum = y.getImpNum();
						beginImpMoney = y.getImpMoney();
						beginUseNum = y.getUseNum();
						beginUseMoney = y.getUseMoney();
						beginleaveNum = y.getLeaveNum();
						beginleaveMoney = y.getLeaveMoney();
					}
					// 取得第一次的上一期结束数量与金额
					if (cancelTiems == 1) {
						String cancelTimes = cancelHead.getCancelTimes();
						String beforeTimes = String.valueOf(Integer
								.valueOf(cancelTimes) - 1);
						if (Integer.parseInt(beforeTimes) >= 0) {
							Double[] xx = checkCancelDao.findResultNum(
									String.valueOf(seqNum), isOwner,
									beforeTimes);
							beginleaveNum = xx[0];
							beginleaveMoney = xx[1];
							beginImpNum = beginleaveNum;
							beginImpMoney = beginleaveMoney;
						}
					}
					// 2008.11.8余鹏修改
					avgPrice.setBeginLeaveNum(beginleaveNum);
					avgPrice.setBeginLeaveMoney(beginleaveMoney);
					// if(seqNum.equals(1)){
					// System.out.println("seqNum:"+seqNum+"beginleaveNum:"+beginleaveNum);
					// System.out.println("seqNum:"+seqNum+"beginleaveMoney:"+beginleaveMoney);
					// }
					TempDD x = (TempDD) hs.get(String.valueOf(seqNum));
					if (x != null) {
						useNum = x.getAa();
						leftOverNum = x.getBb();
					}

					TempDD r = (TempDD) commAmountByMonthHs.get(seqNum);
					if (r != null) {
						impNum = r.getAa();
					}
					TempDD t = (TempDD) commMoneyByMonthHs.get(seqNum);
					if (t != null) {
						impMoney = t.getBb();
					}

					// 插入表中

					if ((impNum == 0.0 && beginleaveNum == 0.0)
							|| (beginleaveNum == 0 && impNum == 0)
							|| impNum == null) {
						avgPrice.setAvgPrice(0.0);
					} else {
						// Math.abs();
						avgPrice.setAvgPrice(formatBig((beginleaveMoney + impMoney)
								/ (beginleaveNum + impNum)));
					}
					// if(seqNum.equals(1)){
					// System.out.println("seqNum:"+seqNum+" beginleaveMoney +
					// impMoney:"+(beginleaveMoney
					// + impMoney));
					// System.out.println("seqNum:"+seqNum+" beginleaveNum +
					// impNum:"+(beginleaveNum
					// + impNum));
					// }
					avgPrice.setCancelHead(cancelHead);
					avgPrice.setCompany(CommonUtils.getCompany());
					avgPrice.setEmsSeqNum(seqNum);
					avgPrice.setCancelTimes(cancelTiems);
					avgPrice.setBeginDate(beginMonth);
					avgPrice.setEndDate(lastMonth);
					avgPrice.setDollarTotalPrice(impMoney);
					avgPrice.setCommAmount(impNum);

					// 耗用数量与金额
					avgPrice.setUseNum(formatBig(useNum));
					avgPrice.setUseMoney(formatBig(useNum
							* avgPrice.getAvgPrice()));
					// 边角料数量与金额
					avgPrice.setLeftOverImgNum(formatBig(leftOverNum));
					avgPrice.setLeftOverImgMoney(formatBig(leftOverNum
							* avgPrice.getAvgPrice()));
					// （1－2,3....）进口总数量
					avgPrice.setImpNumTotal(formatBig((beginImpNum + impNum)));
					// （1－2,3....）进口物料金额
					avgPrice.setImpMoneyTotal(formatBig((beginImpMoney + impMoney)));
					// （1－2,3....）出口成品耗用数量
					avgPrice.setUseNumTotal(formatBig((beginUseNum + useNum)));
					// （1－2,3....）耗用金额
					avgPrice.setUseMoneyTotal(formatBig((beginUseMoney + avgPrice
							.getUseMoney())));

					// 物料结余数量＝（1－2,3....）进口总数量－（1－2,3....）出口成品耗用数量
					avgPrice.setLeaveNum(formatBig(avgPrice.getImpNumTotal()
							- avgPrice.getUseNumTotal()));
					// 物料结余金额＝（1－2,3....）进口物料金额－（1－2,3....）耗用金额
					avgPrice.setLeaveMoney(formatBig(avgPrice
							.getImpMoneyTotal() - avgPrice.getUseMoneyTotal()));

					TempCancelImgAvgPrice temp = new TempCancelImgAvgPrice();
					temp.setSeqNum(seqNum + "/" + cancelTiems);
					temp.setImpNum(fd(avgPrice.getImpNumTotal()));
					temp.setImpMoney(fd(avgPrice.getImpMoneyTotal()));
					temp.setUseNum(fd(avgPrice.getUseNumTotal()));
					temp.setUseMoney(fd(avgPrice.getUseMoneyTotal()));
					temp.setLeaveNum(fd(avgPrice.getLeaveNum()));
					temp.setLeaveMoney(fd(avgPrice.getLeaveMoney()));
					// if(seqNum.equals(1)){
					// System.out.println("seqNum:"+seqNum+"AvgPrice:"+avgPrice.getAvgPrice());
					// System.out.println("seqNum:"+seqNum+"LeaveNum:"+temp.getLeaveNum());
					// System.out.println("seqNum:"+seqNum+"LeaveMoney:"+temp.getLeaveMoney());
					// }
					commCancelTimesByMonthHs.put(String.valueOf(seqNum) + "/"
							+ String.valueOf(cancelTiems), temp);

					checkCancelDao.saveCancelImgPriceResult(avgPrice);
					listResult.add(avgPrice);
					if (info != null) {
						info.setCurrentNum(info.getCurrentNum() + 1);
					}
				}
				cancelTiems += 1;
				// clStart.add(cal.DAY_OF_MONTH, 1);
				// clStart.set(clStart.DAY_OF_MONTH, clStart
				// .get(clStart.DAY_OF_MONTH));
				// } else {
				// break;
				// }
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listResult;
	}

	/**
	 * 查出本月的进口总数量
	 */
	public Map<Integer, TempDD> findBaseCustomsDeclarationNum(Date beginMonth,
			Date lastMonth, CancelHead cancelHead, boolean isOwner,
			EmsHeadH2k head) {
		Map<Integer, TempDD> commAmountByMonthHs = new HashMap<Integer, TempDD>();
		// 计算本期进口总数量
		List ls = checkCancelDao.getbaseCustomsDeclarationByAmountMonth(
				cancelHead, isOwner, beginMonth, lastMonth, head);
		commAmountByMonthHs.clear();
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			Integer seqNum = Integer.valueOf(String.valueOf(obj[0]));
			Double commAmount = fd((Double) obj[1]) - fd((Double) obj[2])
					- fd((Double) obj[3]) - fd((Double) obj[4]);
			TempDD temp = null;
			if (commAmountByMonthHs.get(seqNum) == null) {
				temp = new TempDD();
				temp.setLjseqnum(String.valueOf(seqNum));
				temp.setAa(commAmount);
				commAmountByMonthHs.put(seqNum, temp);
			} else {
				temp = (TempDD) commAmountByMonthHs.get(seqNum);
				temp.setAa(fd(temp.getAa()) + commAmount);
			}
		}
		return commAmountByMonthHs;
	}

	/**
	 * 查出本月的进口总金额
	 */
	public Map<Integer, TempDD> findBaseCustomsDeclarationMoney(
			Date beginMonth, Date lastMonth, CancelHead cancelHead,
			boolean isOwner, EmsHeadH2k head) {
		Map<Integer, TempDD> commMoneyByMonthHs = new HashMap<Integer, TempDD>();
		// 计算本期进口总金额
		List ls = checkCancelDao.getbaseCustomsDeclarationByMoneyMonth(
				cancelHead, isOwner, beginMonth, lastMonth, head);
		commMoneyByMonthHs.clear();
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			Integer seqNum = Integer.valueOf(String.valueOf(obj[0]));
			Double commMoney = (obj[1] == null ? 0 : Double.parseDouble(obj[1]
					.toString()))
					- (obj[2] == null ? 0 : Double.parseDouble(obj[2]
							.toString()))
					- (obj[3] == null ? 0 : Double.parseDouble(obj[3]
							.toString()))
					- (obj[4] == null ? 0 : Double.parseDouble(obj[4]
							.toString()));
			TempDD temp = null;
			if (commMoneyByMonthHs.get(seqNum) == null) {
				temp = new TempDD();
				temp.setLjseqnum(String.valueOf(seqNum));
				temp.setBb(commMoney);
				commMoneyByMonthHs.put(seqNum, temp);
			} else {
				temp = (TempDD) commMoneyByMonthHs.get(seqNum);
				temp.setBb(fd(temp.getBb()) + commMoney);
			}
		}
		return commMoneyByMonthHs;
	}

	/**
	 * 查出本月边角料金额
	 */
	public void finLjUseNumByMonth(Date beginMonth, Date lastMonth,
			EmsHeadH2k head, boolean isOwner, CancelHead cancelHead) {
		List ls = this.checkCancelDao.finLjUseNumByMonth(beginMonth, lastMonth,
				head, isOwner, cancelHead);
		hs.clear();
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			String ljNo = String.valueOf(obj[2]);
			Double unitWear = fd((Double) obj[3]);
			Double wear = fd((Double) obj[4]);
			Double unitUse = unitWear / (1 - (wear * 0.01));

			Double useNum = fd((Double) obj[5]) - fd((Double) obj[6]);
			Double ljUseNum = useNum * unitUse;
			Double bjlNum = ljUseNum * (wear * 0.01);
			TempDD temp = null;
			if (hs.get(ljNo) == null) {
				temp = new TempDD();
				temp.setLjseqnum(ljNo);
				temp.setAa(ljUseNum);
				temp.setBb(bjlNum);
				hs.put(ljNo, temp);
			} else {
				temp = (TempDD) hs.get(ljNo);
				temp.setAa(fd(temp.getAa()) + ljUseNum);
				temp.setBb(fd(temp.getBb()) + bjlNum);
			}
		}
	}

	/**
	 * 计算料件总重量
	 */
	private void getImgTotalWeight(Date beginDate, Date endDate,
			Boolean isEffect, boolean isProduct, CancelHead cancelHead,
			boolean isOwner) {
		if (totalWeightHs != null) {
			totalWeightHs.clear();
		}
		List list = this.checkCancelDao.findImgTotalWeight(beginDate, endDate,
				isEffect, isProduct, cancelHead, isOwner);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			totalWeightHs.put(new Integer(obj[0].toString()), new Double(
					obj[1] == null ? "0.0" : obj[1].toString()));
		}
	}

	// /**
	// * 得到退料出口总数量
	// */
	// private void getBackMaterielAmountByType(Date beginDate, Date endDate,
	// Boolean isEffect, int type, CancelHead cancelHead, boolean isOwner) {
	// if (backMaterielAmountByTypeHs != null) {
	// backMaterielAmountByTypeHs.clear();
	// }
	// List list = this.checkCancelDao.findBackMaterielAmountByType(beginDate,
	// endDate, isEffect, type, cancelHead, isOwner);
	// for (int i = 0; i < list.size(); i++) {
	// Object[] obj = (Object[]) list.get(i);
	// backMaterielAmountByTypeHs.put(new Integer(obj[0].toString()),
	// new Double(obj[1] == null ? "0.0" : obj[1].toString()));
	// }
	// }
	//
	// /**
	// * 得到退料出口总金额
	// */
	// private void getBackMaterielMoneyByType(Date beginDate, Date endDate,
	// Boolean isEffect, int type, CancelHead cancelHead, boolean isOwner) {
	// if (backMaterielMoneyByTypeHs != null) {
	// backMaterielMoneyByTypeHs.clear();
	// }
	// List list = this.checkCancelDao.findBackMaterielMoneyByType(beginDate,
	// endDate, isEffect, type, cancelHead, isOwner);
	// for (int i = 0; i < list.size(); i++) {
	// Object[] obj = (Object[]) list.get(i);
	// backMaterielMoneyByTypeHs.put(new Integer(obj[0].toString()),
	// new Double(obj[1] == null ? "0.0" : obj[1].toString()));
	// }
	// }

	/**
	 * 得到料件内销总数量
	 */
	private Map<Integer, Double> getCommAmountByType(Date beginDate,
			Date endDate, Boolean isEffect, int type, CancelHead cancelHead,
			boolean isOwner) {
		// if (commAmountByTypeHs != null) {
		// commAmountByTypeHs.clear();
		// }
		Map<Integer, Double> commAmountByTypeHs = new HashMap<Integer, Double>();
		List list = this.checkCancelDao.findCommAmountByType(beginDate,
				endDate, isEffect, type, cancelHead, isOwner);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			// commAmountByTypeHs.put(new Integer(obj[0].toString()), new
			// Double(
			// obj[1] == null ? "0.0" : obj[1].toString()));
			commAmountByTypeHs.put(obj[0] == null ? null : (Integer) obj[0],
					obj[1] == null ? 0.0 : (Double) obj[1]);
		}
		return commAmountByTypeHs;
	}

	/**
	 * 得到料件内销总重量
	 */
	private Map<Integer, Double> getCommWeightByType(Date beginDate,
			Date endDate, Boolean isEffect, int type, CancelHead cancelHead,
			boolean isOwner) {
		// if (commAmountByTypeHs != null) {
		// commAmountByTypeHs.clear();
		// }
		Map<Integer, Double> commWeightByTypeHs = new HashMap<Integer, Double>();
		List list = this.checkCancelDao.findCommWeightByType(beginDate,
				endDate, isEffect, type, cancelHead, isOwner);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			commWeightByTypeHs.put(new Integer(obj[0].toString()), new Double(
					obj[1] == null ? "0.0" : obj[1].toString()));
		}
		return commWeightByTypeHs;
	}

	/**
	 * 得到料件内销总金额
	 */
	private Map<Integer, Double> getCommMoneyByType(Date beginDate,
			Date endDate, Boolean isEffect, int type, CancelHead cancelHead,
			boolean isOwner) {
		// if (commMoneyByTypeHs != null) {
		// commMoneyByTypeHs.clear();
		// }
		Map<Integer, Double> commMoneyByTypeHs = new HashMap<Integer, Double>();
		List list = this.checkCancelDao.findCommMoneyByType(beginDate, endDate,
				isEffect, type, cancelHead, isOwner);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			commMoneyByTypeHs.put(new Integer(obj[0].toString()), new Double(
					obj[1] == null ? "0.0" : obj[1].toString()));
		}
		return commMoneyByTypeHs;
	}

	/**
	 * 计算报核周期每月的平均单价
	 */
	public List getImgAveragePriceByMonth(CancelHead cancelHead,
			EmsHeadH2k emsHeadH2k, boolean isOwner, String taskId) {

		Date beginDate = cancelHead.getBeginDate();
		Date endDate = cancelHead.getEndDate();
		// 计算报核周期每月的平均单价
		return getImgAveragePriceByMonth(emsHeadH2k, beginDate, endDate,
				cancelHead, isOwner, taskId);
	}

	/**
	 * 重新获取得到料件核算结果 报核周期每月的平均单价
	 */
	public List getCancelImgByMonth(CancelHead cancelHead,
			EmsHeadH2k emsHeadH2k, boolean isOwner, boolean isUnitWare,
			String taskId) { // 计算到料件表中
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在计算报核料件表");
		} else {
			System.out.println("--------------info is null");
		}
		List allList = new ArrayList();

		Date beginDate = cancelHead.getBeginDate();
		Date endDate = cancelHead.getEndDate();

		List list = checkCancelDao.findEmsEdiImg(emsHeadH2k);// 电子帐册料件
		// 获取报核周期每月总的平均单价
		Map<Integer, TempCancelImgAvgPrice> avePriceHs = getImgCancelImgAvgPrice(
				cancelHead, isOwner);

		Map<Integer, Double> commAmountByTypeHs = getCommAmountByType(
				beginDate, endDate, new Boolean(true),
				ImpExpType.MATERIAL_DOMESTIC_SALES, cancelHead, isOwner);
		Map<Integer, Double> commMoneyByTypeHs = getCommMoneyByType(beginDate,
				endDate, new Boolean(true), ImpExpType.MATERIAL_DOMESTIC_SALES,
				cancelHead, isOwner);
		List cancelImgList = new ArrayList();
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
		}
		Map<Integer,Double[]> mapCancelMoney=new HashMap();
		Map<Integer,Double[]> mapCancelNum=new HashMap();
				initCancelMoneyAndNum(cancelHead,isOwner,mapCancelMoney,mapCancelNum);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
			CancelImgResult result = null;
			if (isOwner) {
				result = new CancelOwnerImgResult();
			} else {
				result = new CancelCusImgResult();
			}
			Integer seqNum = img.getSeqNum();
			String name = img.getName();
			String spec = img.getSpec();
			String unitName = img.getUnit() == null ? null : img.getUnit()
					.getName();

			result.setCancelHead(cancelHead);
			result.setCompany(CommonUtils.getCompany());
			result.setEmsSeqNum(seqNum);
			result.setName(name);// 名称
			result.setSpec(spec);// 规格
			result.setUnit(unitName);// 单位
			String s = seqNum.toString();
			// 获取期初数量
			String cancelTimes = cancelHead.getCancelTimes();// 当前核销次数
			String beforeTimes = String
					.valueOf(Integer.valueOf(cancelTimes) - 1);
			if (Integer.parseInt(beforeTimes) >= 0) {
				Double[] xx = checkCancelDao.findResultNum(s, isOwner,
						beforeTimes);
				Double beginNum = xx[0];
				Double beginMoney = xx[1];
				result.setBeginNum(beginNum); // 期初数量
				result.setBeginMoney(beginMoney); // 期初金额
			}
			Double[] num = mapCancelNum.get(seqNum);//getCancelNum(cancelHead, s, isOwner);
			Double total_hz = num[0];
			Double total_hj = num[1];
			result.setCancelAddNum(total_hz); // 核增数量
			result.setCancelMinNum(total_hj); // 核减数量

			Double useNum = Double.valueOf(0);
			Double useMoney = Double.valueOf(0);
			Double leftOverImgNum = Double.valueOf(0);
			Double leftOverImgMoney = Double.valueOf(0);
			Double leaveSumMoney = Double.valueOf(0);
			Double leaveNum = Double.valueOf(0);
			TempCancelImgAvgPrice x = (TempCancelImgAvgPrice) avePriceHs
					.get(seqNum);
			if (x != null) {
				useNum = x.getUseNum();
				useMoney = x.getUseMoney();
				leftOverImgNum = x.getLeftOverImgNum();
				leftOverImgMoney = x.getLeftOverImgMoney();
				leaveNum = x.getLeaveNum();
				leaveSumMoney = x.getLeaveMoney();
			}
			result.setAveragePrice(0.0);
			result.setUseSumNum(formatBig(useNum));// 耗用数量
			result.setUseSumPrice(formatBig(useMoney));// 耗用价值
			Double totalWeight = formatBig(useNum)
					* (img.getWeigthUnitGene() == null ? Double.valueOf(0)
							: img.getWeigthUnitGene());
			if (unitName != null && unitName.equals("千克")) {// 耗用总重量
				result.setUseSumWeight(result.getUseSumNum());
			} else {
				result.setUseSumWeight(formatBig(totalWeight));
			}
			result.setLeftOverImgNum(formatBig(leftOverImgNum));// 边角料数量
			result.setLeftOverImgSumPrice(formatBig(leftOverImgMoney));// 边角料价值

			if (unitName != null && unitName.equals("千克")) {// 边角料重量
				result.setLeftOverImgSumWeight(fd(result.getLeftOverImgNum()));
			} else {
				result.setLeftOverImgSumWeight(Double.valueOf(0));
			}
			Double innerUseNum = (Double) commAmountByTypeHs.get(seqNum);
			innerUseNum = (innerUseNum == null ? Double.valueOf(0)
					: innerUseNum);
			result.setInnerUseSumNum(formatBig(innerUseNum)); // 内销数量
			Double innerUseMoney = (Double) commMoneyByTypeHs.get(seqNum);
			innerUseMoney = (innerUseMoney == null ? Double.valueOf(0)
					: innerUseMoney);
			result.setInnerUseSumPrice(formatBig(innerUseMoney));// 内销金额

			result.setLeaveNum(formatBig(leaveNum));// 应剩余数量
			result.setLeaveSumPrice(formatBig(leaveSumMoney));// 应剩余价值

			// result.setLeaveNum(fd(result.getBeginNum()) +
			// formatBig(leaveNum));// 应剩余数量
			// result.setLeaveSumPrice(fd(result.getBeginMoney())
			// + formatBig(leaveSumMoney));// 应剩余价值
			if (unitName != null && unitName.equals("千克")) { // 应剩余重量
				result.setLeaveSumWeight(fd(result.getLeaveNum()));
			} else {
				result.setLeaveSumWeight(Double.valueOf(0));
			}
			

			result.setFactLeaveNum(fd(result.getLeaveNum()));
			// 实际剩余价值=应剩余价值
			result.setFactLeaveSumPrice(fd(result.getLeaveSumPrice()));
			result.setFactLeaveSumWeight(fd(result.getLeaveSumWeight()));
			result.setResultNum(fd(result.getLeaveNum())); // 结余数量 = 实际应剩余数量
			result.setResultSumPrice(fd(result.getLeaveSumPrice()));// 结余金额 =
			// 实际应剩余金额
			checkCancelDao.saveCancelImgResult(result);
			cancelImgList.add(result);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
			// System.out.println("img---"+i+"/"+list.size());
		}
		allList.add(cancelImgList);
		// 是否需要计算单耗表
		if (isUnitWare) {
			List ls = findLjUseNum(emsHeadH2k, isOwner, cancelHead);
			findLjUseDetail(cancelHead, ls, isOwner, info);
			// allList.add(ls2);
		}
		// else {
		// allList.add(null);
		// }
		return allList;
	}

	/**
	 * 重新获取得到料件核算结果
	 * 
	 * @param cancelHead
	 * @param emsHeadH2k
	 * @param isOwner
	 * @param isUnitWare
	 * @return
	 */
	public void getCancelImg(CancelHead cancelHead, EmsHeadH2k emsHeadH2k,
			boolean isOwner, boolean isUnitWare, String taskId) { // 计算到料件表中
		// List allList = new ArrayList();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在计算报核料件表");
			info.setCurrentNum(0);
		} else {
			System.out.println("--------------info is null");
		}
		Date beginDate = cancelHead.getBeginDate();
		Date endDate = cancelHead.getEndDate();
		System.out.println("系统正在抓取料件");
		if (info != null) {
			info.setMethodName("系统正在抓取料件");
		}
		List list = checkCancelDao.findEmsEdiImg(emsHeadH2k);// 电子帐册料件

		// 期初数量 暂存Map
		Map<Integer, Double> beginNumMap = new HashMap<Integer, Double>();

		// 期初金额 暂存Map
		Map<Integer, Double> beginMoneyMap = new HashMap<Integer, Double>();

		String cancelTimes = cancelHead.getCancelTimes();// 当前核销次数
		String beforeTimes = String.valueOf(Integer.valueOf(cancelTimes) - 1);
		System.out.println("系统正在初始化料件起初数量和金额");
		if (info != null) {
			info.setMethodName("系统正在初始化料件起初数量和金额");
		}

		// 获取期初数量和金额并缓存
		initBeginNumAndMoney(isOwner, beforeTimes, beginNumMap, beginMoneyMap);

		// 计算报核周期总的平均单价
		System.out.println("系统正在计算报核周期总的平均单价");
		if (info != null) {
			info.setMethodName("系统正在计算报核周期总的平均单价");
		}
		// 2013-5-22料件表平均单价取单价表系统单价算法
		// Map<Integer, Double> avePriceHs = getImgAveragePrice(emsHeadH2k,
		// beginDate, endDate, new Boolean(true), false, cancelHead,
		// isOwner, list, beginNumMap, beginMoneyMap);
		System.out.println("系统正在计算料件耗用");
		if (info != null) {
			info.setMethodName("系统正在计算料件耗用");
		}
		List ls = findLjUseNum(emsHeadH2k, isOwner, cancelHead);

		// 计算料件总重量应该来源于电子帐册中的重量比例因子,因此不用
		// getImgTotalWeight(beginDate, endDate, new Boolean(true), false,
		// cancelHead, isOwner);
		// getBackMaterielAmountByType(beginDate, endDate, new Boolean(true),
		// ImpExpType.BACK_MATERIEL_EXPORT, cancelHead, isOwner);
		/*
		 * getBackMaterielMoneyByType(beginDate, endDate, new Boolean(true),
		 * ImpExpType.BACK_MATERIEL_EXPORT, cancelHead, isOwner);
		 */
		System.out.println("系统正在计算料件内销总数量");
		if (info != null) {
			info.setMethodName("系统正在计算料件内销总数量");
		}
		Map<Integer, Double> commAmountByTypeHs = getCommAmountByType(
				beginDate, endDate, new Boolean(true),
				ImpExpType.MATERIAL_DOMESTIC_SALES, cancelHead, isOwner);
		System.out.println("系统正在计算料件内销总金额");
		if (info != null) {
			info.setMethodName("系统正在计算料件内销总金额");
		}
		Map<Integer, Double> commMoneyByTypeHs = getCommMoneyByType(beginDate,
				endDate, new Boolean(true), ImpExpType.MATERIAL_DOMESTIC_SALES,
				cancelHead, isOwner);
		System.out.println("系统正在计算料件内销总重量");
		if (info != null) {
			info.setMethodName("系统正在计算料件内销总重量");
		}
		// Map<Integer, Double> commWeightByTypeHs = getCommWeightByType(
		// beginDate, endDate, new Boolean(true),
		// ImpExpType.MATERIAL_DOMESTIC_SALES, cancelHead, isOwner);
		List cancelImgList = new ArrayList();
		System.out.println("系统正在产生核销料件");
		if (info != null) {
			info.setMethodName("系统正在产生核销料件");
		}
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		Map<Integer,Double[]> mapCancelMoney=new HashMap();
		Map<Integer,Double[]> mapCancelNum=new HashMap();
		initCancelMoneyAndNum(cancelHead,isOwner,mapCancelMoney,mapCancelNum);
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
			CancelImgResult result = null;
			if (isOwner) {
				result = new CancelOwnerImgResult();
			} else {
				result = new CancelCusImgResult();
			}
			/*
			 * 定义 & 赋值
			 */
			Integer seqNum = img.getSeqNum();// 序号
			String s = seqNum.toString();// 序号
			String name = img.getName();// 料件名称
			String spec = img.getSpec();// 料件规格
			String unitName = img.getUnit() == null ? null : img.getUnit()
					.getName();// 单位名称
			Double beginNum = (beginNumMap.get(seqNum) == null ? 0.0
					: beginNumMap.get(seqNum));// 期初数量
			Double beginMoney = (beginMoneyMap.get(seqNum) == null ? 0.0
					: beginMoneyMap.get(seqNum));// 期初金额
			Double totalNumHz = 0.0;// 核增数量
			Double totalNumHj = 0.0;// 核减数量
//			Double[] money = getCancelMoney(cancelHead, s, isOwner);
			Double[] money = mapCancelMoney.get(seqNum);

			Double totalMoneyHz = 0.0;
			if(money!=null&&money.length>0){
				totalMoneyHz = money[0];// 核增金额
			}
			Double totalMoneyHj = 0.0;
			if(money!=null&&money.length>1){
				totalMoneyHj = money[1];// 核减金额
			}
			
			// Double totalCounthz = money[2];// 核增数量
			// Double totalCounthj = money[3];// 核减数量
			// Double price = (avePriceHs.get(seqNum) == null ?
			// Double.valueOf(0)
			// : (Double) avePriceHs.get(seqNum));// 平均单价

			Double useNum = Double.valueOf(0);// 耗用数量
			Double useMoney = Double.valueOf(0);// 耗用价值
			Double bjlNum = Double.valueOf(0);// 边角料数量
			Double bjlMoney = Double.valueOf(0);// 边角料价值
			Double totalWeight = null;// 耗用总重量
			// 内销数量
			Double innerUseNum = (commAmountByTypeHs.get(seqNum) == null ? Double
					.valueOf(0) : (Double) commAmountByTypeHs.get(seqNum));
			/*
			 * 逻辑计算
			 */
			Double[] num = mapCancelNum.get(seqNum);//getCancelNum(cancelHead, s, isOwner);
			
			if(num!=null&&num.length>0){
				totalNumHz = num[0];// 核增数量
			}
			if(num!=null&&num.length>1){
				totalNumHj = num[1];// 核减数量
			}
			
			
			TempDD x = (TempDD) hs.get(String.valueOf(seqNum));

			// 2013-5-22讨论确定后，料件表平均单价计算取单价表计算方法。平均单价=系统单价（期初+进口）金额 /（期初+进口）数量
			//当进口金额+期初金额小于0时，单价取0
			Double InMoney = beginMoney + totalMoneyHz - totalMoneyHj;
			Double InNum = beginNum + totalNumHz - totalNumHj;
			Double price = 0.0;
			if (beginMoney > Double.valueOf(0) || beginNum > Double.valueOf(0)) {
				if ((totalNumHz - totalNumHj) > Double.valueOf(0) && InMoney > Double.valueOf(0)) {
					price = (totalMoneyHz - totalMoneyHj)
							/ (totalNumHz - totalNumHj);
				}
			} else {
				if (InNum != 0) {
					price = InMoney < Double.valueOf(0) ? 0.0 : InMoney / InNum;
				} else {
					price = 0.0;
				}

			}
			System.out.print("price" + price);

			if (x != null) {
				useNum = x.getAa();
				bjlNum = x.getBb();
				useMoney = fd(useNum) * price;
				bjlMoney = fd(bjlNum) * price;
				System.out.println("$$$" + useNum);
			}
			if (unitName != null && unitName.equals("千克")) {// 耗用总重量
				totalWeight = formatBig(useNum);
			} else {
				totalWeight = formatBig(formatBig(useNum)
						* (img.getWeigthUnitGene() == null ? Double.valueOf(0)
								: img.getWeigthUnitGene()));// 耗用总重量
			}

			result.setCancelHead(cancelHead);
			result.setCompany(CommonUtils.getCompany());
			result.setEmsSeqNum(seqNum);
			result.setName(name);// 名称
			result.setSpec(spec);// 规格
			result.setUnit(unitName);// 单位
			result.setBeginNum(beginNum); // 期初数量
			result.setBeginMoney(beginMoney); // 期初金额
			result.setCancelAddNum(totalNumHz); // 核增数量
			result.setCancelMinNum(totalNumHj); // 核减数量
			result.setAveragePrice(price); // 平均单价
			result.setUseSumNum(formatBig(useNum));// 耗用数量
			result.setUseSumPrice(formatBig(useMoney));// 耗用价值
			result.setUseSumWeight(totalWeight);// 耗用总重量
			result.setLeftOverImgNum(formatBig(bjlNum));// 边角料数量
			result.setLeftOverImgSumPrice(formatBig(bjlMoney));// 边角料价值

			if (unitName != null && unitName.equals("千克")) {// 边角料重量
				result.setLeftOverImgSumWeight(fd(result.getLeftOverImgNum()));
			} else {
				result.setLeftOverImgSumWeight(Double.valueOf(0));
			}
			result.setInnerUseSumNum(formatBig(innerUseNum)); // 内销数量

			Double innerUseMoney = (Double) commMoneyByTypeHs.get(seqNum);
			innerUseMoney = (innerUseMoney == null ? Double.valueOf(0)
					: innerUseMoney);
			result.setInnerUseSumPrice(formatBig(innerUseMoney));// 内销金额

			// 应剩余数量=核增数量-核减数量+期初数量-成品耗用数量
			result.setLeaveNum(formatBig(totalNumHz - totalNumHj
					+ fd(result.getBeginNum()) - fd(result.getUseSumNum())));

			// 应剩余价值 =核增金额-核减金额 + 期初金额 - 耗用总价值
			// result.setLeaveSumPrice(formatBig(totalMoneyHz - totalMoneyHj)
			// + fd(result.getBeginMoney()) - fd(result.getUseSumPrice()));
			// 2012-11-20
			// lyh因单价有变，所以金额改为以下（为了防止上一期期末金额有负数，所以转到下一期时，大部分金额需与平均单价相乘）
			// result.setLeaveSumPrice(formatBig(totalMoneyHz - totalMoneyHj)
			// + (fd(result.getBeginNum()) - fd(result.getUseSumNum()))
			// * price);

			// hwy2013-4-26因台达要求，核增核减金额应取报关单核增-核减金额
			// 应剩余价值 =报关单核增金额-核减金额 + 期初金额 -（ 耗用数量) * 平均单价
			//原因：以前程序计算问题导致，有出现应剩余数量为正数，但应剩余金额为负数的情况，当应剩余金额为负数时，直接取应剩余数量*平均单价
			result.setLeaveSumPrice((formatBig(totalMoneyHz - totalMoneyHj
					+ fd(result.getBeginMoney()))
					- fd(result.getUseSumNum()) * price) < Double.valueOf(0) ? formatBig(totalNumHz - totalNumHj
							+ fd(result.getBeginNum()) - fd(result.getUseSumNum())) * price :
								formatBig(totalMoneyHz - totalMoneyHj
										+ fd(result.getBeginMoney()))
										- fd(result.getUseSumNum()) * price	);

			if (unitName != null && unitName.equals("千克")) { // 应剩余重量
				result.setLeaveSumWeight(fd(result.getLeaveNum()));
			} else {
				result.setLeaveSumWeight(Double.valueOf(0));
			}


			result.setFactLeaveNum(fd(result.getLeaveNum()));
			// 实际剩余价值=应剩余价值
			result.setFactLeaveSumPrice(fd(result.getLeaveSumPrice()));
			result.setFactLeaveSumWeight(fd(result.getLeaveSumWeight()));

			result.setResultNum(fd(result.getLeaveNum())); // 结余数量 = 应剩余数量
			result.setResultSumPrice(fd(result.getLeaveSumPrice()));// 结余金额 =
			result.setCancelAddMoney(totalMoneyHz);// 核增金额
			result.setInChinaBuyMoney(CommonUtils.getDoubleExceptNull(result
					.getInChinaBuyNum())
					* CommonUtils.getDoubleExceptNull(result.getAveragePrice()));// 国内购买金额
			result.setCancelNum(formatBig(totalNumHz - totalNumHj));
			result.setCancelMoney(formatBig(totalMoneyHz - totalMoneyHj));

			// Double sysPrice = 0.0;// 系统单价
			// result.setSysPrice(0.0);
			// result.setSelfPrice(0.0);
			// if (result.getBeginNum() < Double.valueOf(0)
			// || result.getBeginMoney() < Double.valueOf(0)) {
			// if (result.getCancelAddNum() > 0) {
			// sysPrice = result.getCancelAddMoney()
			// / result.getCancelAddNum();
			// }
			// } else {
			// 系统单价=（期初+进口+国内购买）金额 /（期初+进口+国内购买）数量
			// 2013-5-15原因：现有情况：料件表平均单价取进出口类型，而单价表根据核增方式统计，料件表中计算进口数量和金额有扣减退料出口的数量和金额，而单价表中没有，从而导致两者计算出来的单价不一致。
			// 2013-5-15 料件表中的平均单价与系统单价均要求按核扣方式中的（期初金额+进口总金额）/（期初数量+进口总数量）
			// Double dus = (CommonUtils.getDoubleExceptNull(result
			// .getBeginNum())
			// + CommonUtils.getDoubleExceptNull(result
			// .getCancelAddNum()) + CommonUtils
			// .getDoubleExceptNull(result.getInChinaBuyNum()));
			// Double dus1 = (CommonUtils.getDoubleExceptNull(result
			// .getBeginMoney())
			// + CommonUtils.getDoubleExceptNull(result
			// .getCancelAddMoney()) + CommonUtils
			// .getDoubleExceptNull(result.getInChinaBuyMoney()));
			// if (dus != 0) {
			// sysPrice = dus1 / dus;
			// }
			// sysPrice = (avePriceHs.get(seqNum) == null ? Double.valueOf(0)
			// : (Double) avePriceHs.get(seqNum));// 系统单价 取 平均单价算法
			// }
			Double sysPrice = price;// 系统单价
			result.setSysPrice(sysPrice);
			result.setSelfPrice(sysPrice);
			if (info != null) {
				info.setCurrentNum(i + 1);
			}
			result.setCompany(CommonUtils.getCompany());
//			checkCancelDao.saveCancelImgResult(result);
			cancelImgList.add(result);
		}
		checkCancelDao.batchSaveOrUpdate(cancelImgList);
		// 是否需要计算单耗表

		if (isUnitWare) {
			System.out.println("系统正在产生核销单耗表");
			findLjUseDetail(cancelHead, ls, isOwner, info);
		}
	}

	/**
	 * 重新获取得到成品核算结果 报核周期每月的平均单价
	 */
	public List getCancelExg(CancelHead cancelHead, EmsHeadH2k emsHeadH2k,
			boolean isOwner) { // 计算到成品表中
		findCpNum(emsHeadH2k, isOwner, cancelHead);
		List list = checkCancelDao.findEmsEdiExg(emsHeadH2k);// 电子帐册成品
		List cancelExgList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(i);
			CancelExgResult result = null;
			if (isOwner) {
				result = new CancelOwnerExgResult();
			} else {
				result = new CancelCusExgResult();
			}
			Integer seqNum = exg.getSeqNum();
			String name = exg.getName();
			String spec = exg.getSpec();
			String unitName = exg.getUnit() == null ? null : exg.getUnit()
					.getName();

			result.setCancelHead(cancelHead);
			result.setCompany(CommonUtils.getCompany());
			result.setEmsSeqNum(seqNum);
			result.setName(name);// 名称
			result.setSpec(spec);// 规格
			result.setUnit(unitName);// 单位
			Double bgnum = Double.valueOf(0);
			Double totalWeight = Double.valueOf(0);
			Double bgmoney = Double.valueOf(0);
			TempDD x = (TempDD) cpHs.get(String.valueOf(seqNum));
			if (x != null) {
				bgnum = x.getAa();
				bgmoney = x.getBb();
				// totalWeight = x.getUnitUse();
			}

			result.setUseSumNum(formatBig(bgnum));// 消耗数量
			totalWeight = formatBig(bgnum)
					* (exg.getWeigthUnitGene() == null ? Double.valueOf(0)
							: exg.getWeigthUnitGene());
			if (unitName != null && unitName.equals("千克")) {// 耗用总重量
				result.setUseSumWeight(result.getUseSumNum());
			} else {
				result.setUseSumWeight(formatBig(totalWeight));
			}

			result.setUseSumPrice(formatBig(bgmoney));// 消耗金额

			checkCancelDao.saveCancelExgResult(result);
			cancelExgList.add(result);
		}
		return cancelExgList;
	}

	/**
	 * 重新获取得到料件核算结果
	 */
	public List getCustomsToCheckHead(CancelHead cancelHead, boolean isOwner) {
		List cancelList = checkCancelDao.findCancelCustomsDeclara(cancelHead,
				isOwner);
		checkCancelDao.deleteAll(cancelList);
		List listCheck = new ArrayList();
		List list = checkCancelDao.findCustomsDeclaration(
				cancelHead.getBeginDate(), cancelHead.getEndDate(), isOwner);
		for (int i = 0; i < list.size(); i++) {
			CustomsDeclaration customs = (CustomsDeclaration) list.get(i);
			CancelCustomsDeclara cancelCustoms = null;
			if (isOwner) {
				cancelCustoms = new CancelOwnerCustomsDeclara();
			} else {
				cancelCustoms = new CancelCusCustomsDeclara();
			}
			cancelCustoms.setCancelHead(cancelHead);
			cancelCustoms.setCompany(CommonUtils.getCompany());
			cancelCustoms.setCustom(customs.getDeclarationCustoms());
			cancelCustoms.setCustomNo(customs.getCustomsDeclarationCode());
			cancelCustoms.setDeclareDate(customs.getDeclarationDate());
			cancelCustoms.setInOutportDate(customs.getImpExpDate());
			if (customs.getImpExpFlag() != null
					&& customs.getImpExpFlag().equals(ImpExpFlag.IMPORT)) {
				cancelCustoms.setInOutportFlat("I");
			} else if (customs.getImpExpFlag() != null
					&& customs.getImpExpFlag().equals(ImpExpFlag.EXPORT)) {
				cancelCustoms.setInOutportFlat("E");
			} else if (customs.getImpExpType() != null
					&& isImport(customs.getImpExpType().intValue())
					&& customs.getImpExpFlag().equals(ImpExpFlag.SPECIAL)) {
				cancelCustoms.setInOutportFlat("I");
			} else if (customs.getImpExpType() != null
					&& !isImport(customs.getImpExpType().intValue())
					&& customs.getImpExpFlag().equals(ImpExpFlag.SPECIAL)) {
				cancelCustoms.setInOutportFlat("E");
			}
			cancelCustoms.setInOutportType(customs.getImpExpType() == null
					|| "".equals(customs.getImpExpType()) ? null : customs
					.getImpExpType().intValue());
			cancelCustoms.setNote(subString(customs.getMemos()));
			cancelCustoms.setTradeMode(customs.getTradeMode());
			checkCancelDao.saveCancelCustomsDeclara(cancelCustoms);
			listCheck.add(cancelCustoms);
		}
		return listCheck;
	}

	private String subString(String stu){
		if(stu==null){
			return null;
		}
		try {
			if(stu.getBytes("UTF-8").length>255){
				for (int i = 0; i < stu.length(); i++) {
					stu = stu.substring(0, stu.length()-i);
					if(stu.getBytes("UTF-8").length<255){
						return stu;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return stu;
	}
	
	/**
	 * 是进口还是出口
	 */
	public static boolean isImport(int billType) {
		boolean isImport = false;
		switch (billType) {
		case ImpExpType.DIRECT_IMPORT:
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
		case ImpExpType.BACK_FACTORY_REWORK:
		case ImpExpType.GENERAL_TRADE_IMPORT:
		case ImpExpType.EQUIPMENT_IMPORT:
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:// 边角料内销
		case ImpExpType.MATERIAL_DOMESTIC_SALES:// 料件内销
		case ImpExpType.REMAIN_FORWARD_IMPORT: //
			isImport = true;
			break;
		case ImpExpType.DIRECT_EXPORT:
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
		case ImpExpType.BACK_MATERIEL_EXPORT:
		case ImpExpType.REWORK_EXPORT:
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
		case ImpExpType.GENERAL_TRADE_EXPORT:
		case ImpExpType.EQUIPMENT_BACK_PORT:
		case ImpExpType.BACK_PORT_REPAIR:
		case ImpExpType.REMAIN_FORWARD_EXPORT:
			isImport = false;
			break;
		}
		return isImport;
	}

	/**
	 * 修改核销表头
	 */

	public CancelHead modityCancelHead(CancelHead cancelHead, boolean isOwner) {
		Integer Icustoms = checkCancelDao.findCancelCustomsDeclaraByFlagCount(
				cancelHead, "I", isOwner);
		Integer Ecustoms = checkCancelDao.findCancelCustomsDeclaraByFlagCount(
				cancelHead, "E", isOwner);
		cancelHead.setInportCustomNum(Icustoms.intValue());
		cancelHead.setOutportCustomNum(Ecustoms.intValue());
		checkCancelDao.saveCancelHead(cancelHead);
		return cancelHead;
	}

	/**
	 * date->字符型"yyyy-MM-dd"
	 */
	public String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(date);
	}

	/**
	 * 预报核报表
	 */
	public List getCustomsIEToTemp(CancelHead cancelHead, boolean isOwner) {
		List list = new ArrayList();
		List listI = checkCancelDao.findCancelCustomsDeclaraByFlag(cancelHead,
				"I", isOwner);
		List listE = checkCancelDao.findCancelCustomsDeclaraByFlag(cancelHead,
				"E", isOwner);

		int iNum = listI == null ? 0 : listI.size();
		int eNum = listE == null ? 0 : listE.size();

		// 排序
		if (listI != null && listI.size() > 1) {
			Collections.sort(listI, new Comparator<CancelCustomsDeclara>() {

				public int compare(CancelCustomsDeclara o1,
						CancelCustomsDeclara o2) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					int state = dateFormat.format(o1.getDeclareDate())
							.compareTo(dateFormat.format(o2.getDeclareDate()));
					if (state == 0) {
						return o1.getCustomNo().trim()
								.compareTo(o2.getCustomNo().trim());
					}
					return state;
				}

			});
		}

		if (listE != null && listE.size() > 1) {
			Collections.sort(listE, new Comparator<CancelCustomsDeclara>() {

				public int compare(CancelCustomsDeclara o1,
						CancelCustomsDeclara o2) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					int state = dateFormat.format(o1.getDeclareDate())
							.compareTo(dateFormat.format(o2.getDeclareDate()));
					if (state == 0) {
						return o1.getCustomNo().trim()
								.compareTo(o2.getCustomNo().trim());
					}
					return state;
				}

			});
		}

		if (iNum > eNum) {
			Hashtable hs = new Hashtable();
			for (int i = 0; i < iNum; i++) {
				CancelCustomsDeclara obj = (CancelCustomsDeclara) listI.get(i);
				BillTemp bill = new BillTemp();
				bill.setBill1(String.valueOf(i + 1));// 序号
				bill.setBill2(obj.getCustomNo());// 进口报关单
				bill.setBill3(dateToString(obj.getDeclareDate()));// 申报日期
				bill.setBill4(dateToString(obj.getInOutportDate()));// 进出口日期
				bill.setBill5(obj.getTradeMode() == null ? "" : obj
						.getTradeMode().getName());// 贸易方式
				hs.put(i, bill);
			}
			for (int j = 0; j < eNum; j++) {
				CancelCustomsDeclara obj = (CancelCustomsDeclara) listE.get(j);
				BillTemp bill = (BillTemp) hs.get(j);
				bill.setBill6(obj.getCustomNo());
				bill.setBill7(dateToString(obj.getDeclareDate()));// 申报日期
				bill.setBill8(dateToString(obj.getInOutportDate()));// 进出口日期
				bill.setBill9(obj.getTradeMode() == null ? "" : obj
						.getTradeMode().getName());// 贸易方式
			}
			for (int k = hs.size() - 1; k >= 0; k--) {
				BillTemp bill = (BillTemp) hs.values().toArray()[k];
				list.add(bill);
			}
		} else {// 出口数目大于进口数目
			Hashtable hs = new Hashtable();
			for (int i = 0; i < eNum; i++) {
				CancelCustomsDeclara obj = (CancelCustomsDeclara) listE.get(i);
				BillTemp bill = new BillTemp();

				bill.setBill6(obj.getCustomNo());
				bill.setBill7(dateToString(obj.getDeclareDate()));// 申报日期
				bill.setBill8(dateToString(obj.getInOutportDate()));// 进出口日期
				bill.setBill9(obj.getTradeMode() == null ? "" : obj
						.getTradeMode().getName());// 贸易方式
				bill.setBill1(String.valueOf(i + 1));// 序号
				hs.put(i, bill);
			}
			for (int j = 0; j < iNum; j++) {
				CancelCustomsDeclara obj = (CancelCustomsDeclara) listI.get(j);
				BillTemp bill = (BillTemp) hs.get(j);
				bill.setBill2(obj.getCustomNo());// 进口报关单
				bill.setBill3(dateToString(obj.getDeclareDate()));// 申报日期
				bill.setBill4(dateToString(obj.getInOutportDate()));// 进出口日期
				bill.setBill5(obj.getTradeMode() == null ? "" : obj
						.getTradeMode().getName());// 贸易方式
			}
			for (int k = hs.size() - 1; k >= 0; k--) {
				BillTemp bill = (BillTemp) hs.values().toArray()[k];
				list.add(bill);
			}
		}
		Collections.sort(list, new Comparator<BillTemp>() {

			public int compare(BillTemp o1, BillTemp o2) {
				return Integer.valueOf(o1.getBill1()).compareTo(
						Integer.valueOf(o2.getBill1()));
			}

		});
		return list;
	}

	/**
	 * 将时间转化为指定格式
	 */

	private Date getDateByString(Object obj1) {
		if (obj1 == null || obj1.equals("")) {
			return null;
		}
		String obj = obj1.toString();
		String dates = obj + " 00:00:00";
		return strToStandDate(dates);
	}

	/**
	 * 将时间转化为指定格式
	 */
	// 日期转换
	private Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return (Date) dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 同步核销表
	 * 
	 * @param head
	 */
	// 同步核销表
	public void synCancel(CancelOwnerHead head) {

		CancelCusHead obj = new CancelCusHead();
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(head);
		for (int i = 0; i < pds.length; i++) {
			try {
				Object thisValue = PropertyUtils.getProperty(head,
						pds[i].getName());

				if (pds[i].getPropertyType().equals(Date.class)) {
					Object obj1 = getDateByString(thisValue);
					BeanUtils.setProperty(obj, pds[i].getName(), obj1);
				} else {
					BeanUtils.setProperty(obj, pds[i].getName(), thisValue);
				}
				// 把自用核销部分数据同步到数据报核的模拟栏位，不同步到正式表上
				obj.setSimulateBeginImgMoney(obj.getBeginImgMoney());
				obj.setBeginImgMoney(0.0);
				obj.setSimulateEndBalanceImgMoney(obj.getEndBalanceImgMoney());
				obj.setEndBalanceImgMoney(0.0);
				obj.setSimulateExitImgMoney(obj.getExitImgMoney());
				obj.setExitImgMoney(0.0);
				obj.setSimulateInnerCancelMoney(obj.getInnerCancelMoney());
				obj.setInnerCancelMoney(0.0);
				obj.setSimulateOutportExgUseImgNum(obj.getOutportExgUseImgNum());
				obj.setOutportExgUseImgNum(0.0);
				obj.setSimulateRemainExportMoney(obj.getRemainExportMoney());
				obj.setRemainExportMoney(0.0);
				obj.setSimulateThisInportMoney(obj.getThisInportMoney());
				obj.setThisInportMoney(0.0);
				obj.setSimulateThisOutportMoney(obj.getThisOutportMoney());
				obj.setThisOutportMoney(0.0);
			} catch (Exception e) {
				System.out.println("pds[i].getName():" + pds[i].getName());
			}
		}
		obj.setId(null);
		this.checkCancelDao.saveObject(obj);
		System.out.println("-- 同步表头");

		List list = this.checkCancelDao.findCancelCustomsDeclara(head, true);// 自用核销表
		for (int i = 0; i < list.size(); i++) {
			CancelOwnerCustomsDeclara declara = (CancelOwnerCustomsDeclara) list
					.get(i);
			CancelCusCustomsDeclara obj1 = new CancelCusCustomsDeclara();
			PropertyDescriptor[] pds1 = PropertyUtils
					.getPropertyDescriptors(declara);
			for (int j = 0; j < pds1.length; j++) {
				try {

					Object thisValue = PropertyUtils.getProperty(declara,
							pds1[j].getName());

					if (pds1[j].getPropertyType().equals(Date.class)) {
						Object obj2 = getDateByString(thisValue);
						BeanUtils.setProperty(obj1, pds1[j].getName(), obj2);
					} else {
						BeanUtils.setProperty(obj1, pds1[j].getName(),
								thisValue);
					}

				} catch (Exception e) {
				}
			}
			obj1.setCancelHead(obj);
			obj1.setId(null);
			this.checkCancelDao.saveObject(obj1);
		}
		System.out.println("-- 同步报关单");

		List listimgbefore = this.checkCancelDao
				.findCancelImgBefore(head, true);
		for (int j = 0; j < listimgbefore.size(); j++) {
			CancelOwnerImgBefore imgBefore = (CancelOwnerImgBefore) listimgbefore
					.get(j);
			CancelCusImgBefore obj2 = new CancelCusImgBefore();
			PropertyDescriptor[] pds2 = PropertyUtils
					.getPropertyDescriptors(imgBefore);
			for (int k = 0; k < pds2.length; k++) {
				try {
					Object thisValue = PropertyUtils.getProperty(imgBefore,
							pds2[k].getName());
					if (pds2[k].getPropertyType().equals(Date.class)) {
						Object obj3 = getDateByString(thisValue);
						BeanUtils.setProperty(obj2, pds2[k].getName(), obj3);
					} else {
						BeanUtils.setProperty(obj2, pds2[k].getName(),
								thisValue);
					}
				} catch (Exception e) {
				}
			}
			obj2.setCancelHead(obj);
			obj2.setId(null);
			this.checkCancelDao.saveObject(obj2);
		}
		System.out.println("-- 同步料件原始");

		List listimgresult = this.checkCancelDao
				.findCancelImgResult(head, true);
		for (int l = 0; l < listimgresult.size(); l++) {
			CancelOwnerImgResult imgResult = (CancelOwnerImgResult) listimgresult
					.get(l);
			CancelCusImgResult obj3 = new CancelCusImgResult();
			PropertyDescriptor[] pds3 = PropertyUtils
					.getPropertyDescriptors(imgResult);
			for (int m = 0; m < pds3.length; m++) {
				try {
					Object thisValue = PropertyUtils.getProperty(imgResult,
							pds3[m].getName());
					if (pds3[m].getPropertyType().equals(Date.class)) {
						Object obj4 = getDateByString(thisValue);
						BeanUtils.setProperty(obj3, pds3[m].getName(), obj4);
					} else {
						BeanUtils.setProperty(obj3, pds3[m].getName(),
								thisValue);
					}

				} catch (Exception e) {
				}
			}
			obj3.setCancelHead(obj);
			obj3.setId(null);
			this.checkCancelDao.saveObject(obj3);
		}
		System.out.println("-- 同步料件结果");

		List listexgbefore = this.checkCancelDao
				.findCancelExgBefore(head, true);
		for (int j = 0; j < listexgbefore.size(); j++) {
			CancelOwnerExgBefore exgBefore = (CancelOwnerExgBefore) listexgbefore
					.get(j);
			CancelCusExgBefore obj4 = new CancelCusExgBefore();
			PropertyDescriptor[] pds4 = PropertyUtils
					.getPropertyDescriptors(exgBefore);
			for (int k = 0; k < pds4.length; k++) {
				try {
					Object thisValue = PropertyUtils.getProperty(exgBefore,
							pds4[k].getName());
					if (pds4[k].getPropertyType().equals(Date.class)) {
						Object obj5 = getDateByString(thisValue);
						BeanUtils.setProperty(obj4, pds4[k].getName(), obj5);
					} else {
						BeanUtils.setProperty(obj4, pds4[k].getName(),
								thisValue);
					}

				} catch (Exception e) {
				}
			}
			obj4.setCancelHead(obj);
			obj4.setId(null);
			this.checkCancelDao.saveObject(obj4);
		}
		System.out.println("-- 同步成品原始");

		List listexgresult = this.checkCancelDao
				.findCancelExgResult(head, true);
		for (int l = 0; l < listexgresult.size(); l++) {
			CancelOwnerExgResult exgResult = (CancelOwnerExgResult) listexgresult
					.get(l);
			CancelCusExgResult obj5 = new CancelCusExgResult();
			PropertyDescriptor[] pds5 = PropertyUtils
					.getPropertyDescriptors(exgResult);
			for (int m = 0; m < pds5.length; m++) {
				try {
					Object thisValue = PropertyUtils.getProperty(exgResult,
							pds5[m].getName());
					if (pds5[m].getPropertyType().equals(Date.class)) {
						Object obj6 = getDateByString(thisValue);
						BeanUtils.setProperty(obj5, pds5[m].getName(), obj6);
					} else {
						BeanUtils.setProperty(obj5, pds5[m].getName(),
								thisValue);
					}

				} catch (Exception e) {
				}
			}
			obj5.setCancelHead(obj);
			obj5.setId(null);
			this.checkCancelDao.saveObject(obj5);
		}
		System.out.println("-- 同步成品结果");

		List listunitwear = this.checkCancelDao.findCancelUnitWear(head, true);
		for (int l = 0; l < listunitwear.size(); l++) {
			CancelOwnerUnitWear exgResult = (CancelOwnerUnitWear) listunitwear
					.get(l);
			CancelCusUnitWear obj5 = new CancelCusUnitWear();
			PropertyDescriptor[] pds5 = PropertyUtils
					.getPropertyDescriptors(exgResult);
			for (int m = 0; m < pds5.length; m++) {
				try {
					Object thisValue = PropertyUtils.getProperty(exgResult,
							pds5[m].getName());
					BeanUtils.setProperty(obj5, pds5[m].getName(), thisValue);

				} catch (Exception e) {
				}
			}
			obj5.setCancelHead(obj);
			obj5.setId(null);
			this.checkCancelDao.saveObject(obj5);
		}
		System.out.println("-- 同步单耗表");

	}

	/**
	 * 获取数据报核销
	 * 
	 * @param times
	 */
	public void getCancel(String times) {
		CancelCusHead head = null; // 数据报核
		List list = checkCancelDao.findCancelHeadByTimes(times, false);
		if (list != null && list.size() > 0) {
			head = (CancelCusHead) list.get(0);
			CancelOwnerHead obj = new CancelOwnerHead();
			PropertyDescriptor[] pds = PropertyUtils
					.getPropertyDescriptors(head);
			for (int i = 0; i < pds.length; i++) {
				try {
					Object thisValue = PropertyUtils.getProperty(head,
							pds[i].getName());
					if (pds[i].getPropertyType().equals(Date.class)) {
						Object obj2 = getDateByString(thisValue);
						BeanUtils.setProperty(obj, pds[i].getName(), obj2);
					} else {
						BeanUtils.setProperty(obj, pds[i].getName(), thisValue);
					}

				} catch (Exception e) {
				}
			}
			obj.setId(null);
			this.checkCancelDao.saveObjectFlush(obj);
			System.out.println("-- 同步表头");

			list = this.checkCancelDao.findCancelCustomsDeclara(head, false);// 核销表
			for (int i = 0; i < list.size(); i++) {
				CancelCusCustomsDeclara declara = (CancelCusCustomsDeclara) list
						.get(i);
				CancelOwnerCustomsDeclara obj1 = new CancelOwnerCustomsDeclara();
				PropertyDescriptor[] pds1 = PropertyUtils
						.getPropertyDescriptors(declara);
				for (int j = 0; j < pds1.length; j++) {
					try {
						Object thisValue = PropertyUtils.getProperty(declara,
								pds1[j].getName());
						if (pds1[j].getPropertyType().equals(Date.class)) {
							Object obj3 = getDateByString(thisValue);
							BeanUtils
									.setProperty(obj1, pds1[j].getName(), obj3);
						} else {
							BeanUtils.setProperty(obj1, pds1[j].getName(),
									thisValue);
						}

					} catch (Exception e) {
					}
				}
				obj1.setCancelHead(obj);
				obj1.setId(null);
				this.checkCancelDao.saveObject(obj1);
			}
			System.out.println("-- 同步报关单");

			List listimgbefore = this.checkCancelDao.findCancelImgBefore(head,
					false);
			for (int j = 0; j < listimgbefore.size(); j++) {
				CancelCusImgBefore imgBefore = (CancelCusImgBefore) listimgbefore
						.get(j);
				CancelOwnerImgBefore obj2 = new CancelOwnerImgBefore();
				PropertyDescriptor[] pds2 = PropertyUtils
						.getPropertyDescriptors(imgBefore);
				for (int k = 0; k < pds2.length; k++) {
					try {
						Object thisValue = PropertyUtils.getProperty(imgBefore,
								pds2[k].getName());
						if (pds2[k].getPropertyType().equals(Date.class)) {
							Object obj4 = getDateByString(thisValue);
							BeanUtils
									.setProperty(obj2, pds2[k].getName(), obj4);
						} else {
							BeanUtils.setProperty(obj2, pds2[k].getName(),
									thisValue);
						}

					} catch (Exception e) {
					}
				}
				obj2.setCancelHead(obj);
				obj2.setId(null);
				this.checkCancelDao.saveObject(obj2);
			}
			System.out.println("-- 同步料件原始");

			List listimgresult = this.checkCancelDao.findCancelImgResult(head,
					false);
			for (int l = 0; l < listimgresult.size(); l++) {
				CancelCusImgResult imgResult = (CancelCusImgResult) listimgresult
						.get(l);
				CancelOwnerImgResult obj3 = new CancelOwnerImgResult();
				PropertyDescriptor[] pds3 = PropertyUtils
						.getPropertyDescriptors(imgResult);
				for (int m = 0; m < pds3.length; m++) {
					try {
						Object thisValue = PropertyUtils.getProperty(imgResult,
								pds3[m].getName());
						if (pds3[m].getPropertyType().equals(Date.class)) {
							Object obj5 = getDateByString(thisValue);
							BeanUtils
									.setProperty(obj3, pds3[m].getName(), obj5);
						} else {
							BeanUtils.setProperty(obj3, pds3[m].getName(),
									thisValue);
						}

					} catch (Exception e) {
					}
				}
				obj3.setCancelHead(obj);
				obj3.setId(null);
				this.checkCancelDao.saveObject(obj3);
			}
			System.out.println("-- 同步料件结果");

			List listexgbefore = this.checkCancelDao.findCancelExgBefore(head,
					false);
			for (int j = 0; j < listexgbefore.size(); j++) {
				CancelCusExgBefore exgBefore = (CancelCusExgBefore) listexgbefore
						.get(j);
				CancelOwnerExgBefore obj4 = new CancelOwnerExgBefore();
				PropertyDescriptor[] pds4 = PropertyUtils
						.getPropertyDescriptors(exgBefore);
				for (int k = 0; k < pds4.length; k++) {
					try {
						Object thisValue = PropertyUtils.getProperty(exgBefore,
								pds4[k].getName());
						if (pds4[k].getPropertyType().equals(Date.class)) {
							Object obj6 = getDateByString(thisValue);
							BeanUtils
									.setProperty(obj4, pds4[k].getName(), obj6);
						} else {
							BeanUtils.setProperty(obj4, pds4[k].getName(),
									thisValue);
						}

					} catch (Exception e) {
					}
				}
				obj4.setCancelHead(obj);
				obj4.setId(null);
				this.checkCancelDao.saveObject(obj4);
			}
			System.out.println("-- 同步成品原始");

			List listexgresult = this.checkCancelDao.findCancelExgResult(head,
					false);
			for (int l = 0; l < listexgresult.size(); l++) {
				CancelCusExgResult exgResult = (CancelCusExgResult) listexgresult
						.get(l);
				CancelOwnerExgResult obj5 = new CancelOwnerExgResult();
				PropertyDescriptor[] pds5 = PropertyUtils
						.getPropertyDescriptors(exgResult);
				for (int m = 0; m < pds5.length; m++) {
					try {
						Object thisValue = PropertyUtils.getProperty(exgResult,
								pds5[m].getName());
						if (pds5[m].getPropertyType().equals(Date.class)) {
							Object obj7 = getDateByString(thisValue);
							BeanUtils
									.setProperty(obj5, pds5[m].getName(), obj7);
						} else {
							BeanUtils.setProperty(obj5, pds5[m].getName(),
									thisValue);
						}

					} catch (Exception e) {
					}
				}
				obj5.setCancelHead(obj);
				obj5.setId(null);
				this.checkCancelDao.saveObject(obj5);
			}
			System.out.println("-- 同步成品结果");
			// ---------------------------------------------------------------------------
			// List listunitwear = this.checkCancelDao.findCancelUnitWear(head,
			// false);
			// for (int l = 0; l < listunitwear.size(); l++) {
			// CancelCusUnitWear exgResult = (CancelCusUnitWear) listunitwear
			// .get(l);
			// CancelOwnerUnitWear obj5 = new CancelOwnerUnitWear();
			// PropertyDescriptor[] pds5 = PropertyUtils
			// .getPropertyDescriptors(exgResult);
			// for (int m = 0; m < pds5.length; m++) {
			// try {
			// Object thisValue = PropertyUtils.getProperty(exgResult,
			// pds5[m].getName());
			// BeanUtils.setProperty(obj5, pds5[m].getName(),
			// thisValue);
			// } catch (Exception e) {
			// }
			// }
			// obj5.setCancelHead(obj);
			// obj5.setId(null);
			// this.checkCancelDao.saveObject(obj5);
			// }
			int count = checkCancelDao.convertCancelUnitWear(obj.getId(),
					head.getId());
			System.out.println("-- 同步单耗表:共" + count + "记录");
		}
	}

	/**
	 * 新增工厂盘点料件
	 * 
	 * @param list
	 * @param head
	 * @return
	 */
	public List getFactoryCheckImg(List list, CheckParameter head) {
		List<FactoryCheckImg> arrayList = new ArrayList<FactoryCheckImg>();
		for (int i = 0; i < list.size(); i++) {
			Materiel m = (Materiel) list.get(i);
			FactoryCheckImg obj = new FactoryCheckImg();
			obj.setHead(head);
			obj.setPtNo(m.getPtNo());
			obj.setName(m.getFactoryName());
			obj.setSpec(m.getFactorySpec());
			obj.setCompany(CommonUtils.getCompany());
			obj.setMaterStockNum(m.getAmount());
			obj.setCalUnit(m.getCalUnit());
			this.checkCancelDao.saveFactoryCheckImg(obj);
			arrayList.add(obj);
		}
		return arrayList;
	}

	/**
	 * 新增工厂盘点成品
	 * 
	 * @param list
	 * @param head
	 * @return
	 */
	public List getFactoryCheckExg(List list, CheckParameter head) {
		List<FactoryCheckExg> arrayList = new ArrayList<FactoryCheckExg>();
		for (int i = 0; i < list.size(); i++) {
			Materiel m = (Materiel) list.get(i);
			FactoryCheckExg obj = new FactoryCheckExg();
			obj.setHead(head);
			obj.setPtNo(m.getPtNo());
			obj.setName(m.getFactoryName());
			obj.setSpec(m.getFactorySpec());
			obj.setCompany(CommonUtils.getCompany());
			obj.setVersion(m.getPtVersion());
			obj.setMaterStockNum(m.getAmount());
			this.checkCancelDao.saveFactoryCheckExg(obj);
			arrayList.add(obj);
		}
		return arrayList;
	}

	/**
	 * 成品折算为料件
	 */
	public List factoryExgConvertImg(List list) {
		List<FactoryCheckExgConverImg> arrayList = new ArrayList<FactoryCheckExgConverImg>();
		for (int r = 0; r < list.size(); r++) {
			FactoryCheckExg exg = (FactoryCheckExg) list.get(r);
			String ptNo = exg.getPtNo().trim(); // 成品料号
			String version = exg.getVersion(); // 成品版本号
			Double num = forD(exg.getBgNum());
			if (version == null) {
				continue;
			}

			List bomList = this.checkCancelDao.findEmsEdiMergerBomByCpPtNo(
					ptNo, version);
			for (int i = 0; i < bomList.size(); i++) {
				EmsEdiMergerExgBom bom = (EmsEdiMergerExgBom) bomList.get(i);
				FactoryCheckExgConverImg cimg = new FactoryCheckExgConverImg();
				cimg.setHead(exg.getHead());
				cimg.setPtNo(bom.getPtNo());
				cimg.setName(bom.getName());
				cimg.setSpec(bom.getSpec());
				cimg.setUnit(bom.getUnit());
				Double dm = forD(bom.getUnitWaste());
				Double sh = forD(bom.getWaste());
				Double dl = dm / (1.0 - (sh * 0.01));
				cimg.setBgNum(num * dl);
				this.checkCancelDao.saveFactoryCheckExgConverImg(cimg);
				arrayList.add(cimg);
			}
		}
		return arrayList;
	}

	/**
	 * 原材料汇总
	 */
	public List totalMateriel(List imgList, List convertImgList) {
		Hashtable<String, FactoryCheckImgResult> hs = new Hashtable<String, FactoryCheckImgResult>();
		for (int i = 0; i < imgList.size(); i++) {
			FactoryCheckImgResult totalImg = null;
			FactoryCheckImg img = (FactoryCheckImg) imgList.get(i);
			String key = img.getPtNo().trim();
			if (hs.get(key) != null) {
				totalImg = (FactoryCheckImgResult) hs.get(key);
			} else {
				totalImg = new FactoryCheckImgResult();
				totalImg.setPtNo(img.getPtNo());
				totalImg.setName(img.getName());
				totalImg.setSpec(img.getSpec());
				totalImg.setUnit(img.getUnit());
				totalImg.setCompany(CommonUtils.getCompany());
				totalImg.setHead(img.getHead());
				hs.put(key, totalImg);
			}
			totalImg.setMaterielStockNum(forD(totalImg.getMaterielStockNum())
					+ forD(img.getMaterStockNum()));
		}

		for (int j = 0; j < convertImgList.size(); j++) {
			FactoryCheckImgResult totalImg = null;
			FactoryCheckExgConverImg coimg = (FactoryCheckExgConverImg) convertImgList
					.get(j);
			String key = coimg.getPtNo().trim();
			if (hs.get(key) != null) {
				totalImg = (FactoryCheckImgResult) hs.get(key);
			} else {
				totalImg = new FactoryCheckImgResult();
				totalImg.setPtNo(coimg.getPtNo());
				totalImg.setName(coimg.getName());
				totalImg.setSpec(coimg.getSpec());
				totalImg.setUnit(coimg.getUnit());
				totalImg.setCompany(CommonUtils.getCompany());
				totalImg.setHead(coimg.getHead());
				hs.put(key, totalImg);
			}
			totalImg.setMaterielStockNum(forD(totalImg.getMaterielStockNum())
					+ forD(coimg.getBgNum()));

		}
		List list = new Vector(hs.values());
		for (int i = 0; i < list.size(); i++) {
			FactoryCheckImgResult obj = (FactoryCheckImgResult) list.get(i);
			this.checkCancelDao.saveFactoryCheckImgResult(obj);
		}
		return list;

	}

	/**
	 * 对比表
	 * 
	 * @param accountList
	 * @param head
	 * @return
	 */
	public List getAccountComport(List accountList, CheckParameter head) {
		Hashtable<String, CheckOwnerAccountComport> hs = new Hashtable<String, CheckOwnerAccountComport>();
		for (int i = 0; i < accountList.size(); i++) {
			CheckOwnerAccountComport totalImg = null;

			CheckOwnerAccount img = (CheckOwnerAccount) accountList.get(i);
			String key = img.getPtNo();
			if (hs.get(key) != null) {
				totalImg = (CheckOwnerAccountComport) hs.get(key);
			} else {
				totalImg = new CheckOwnerAccountComport();
				totalImg.setCompany(CommonUtils.getCompany());
				totalImg.setHead(img.getHead());
				totalImg.setPtNo(img.getPtNo());
				totalImg.setName(img.getName());
				totalImg.setSpec(img.getSpec());
				totalImg.setUnit(img.getUnit());
				hs.put(key, totalImg);
			}
			totalImg.setMaterielStockNum(forD(totalImg.getMaterielStockNum())
					+ forD(img.getMaterielStockNum()));
			totalImg.setMaterielOnwayNum(forD(totalImg.getMaterielOnwayNum())
					+ forD(img.getMaterielOnwayNum()));
			totalImg.setExgConvertImgNum(forD(totalImg.getExgConvertImgNum())
					+ forD(img.getExgConvertImgNum()));
			totalImg.setTranNoCustomsNum(forD(totalImg.getTranNoCustomsNum())
					+ forD(img.getTranNoCustomsNum()));
			totalImg.setOtherStorkNum(forD(totalImg.getOtherStorkNum())
					+ forD(img.getOnLineNum())
					+ forD(img.getHalfExgConvertImgNum())
					+ forD(img.getFlotsamNum()));
			totalImg.setCyNum(0 - (totalImg.getMaterielStockNum()
					+ totalImg.getMaterielOnwayNum()
					+ totalImg.getExgConvertImgNum()
					+ totalImg.getOtherStorkNum() - totalImg
					.getTranNoCustomsNum()));
		}
		List convertImgList = this.checkCancelDao
				.findFactoryCheckImgCollect(head);
		for (int j = 0; j < convertImgList.size(); j++) {
			CheckOwnerAccountComport totalImg = null;
			FactoryCheckImgResult coimg = (FactoryCheckImgResult) convertImgList
					.get(j);
			String key = coimg.getPtNo();
			if (hs.get(key) != null) {
				totalImg = (CheckOwnerAccountComport) hs.get(key);
			} else {
				totalImg = new CheckOwnerAccountComport();
				totalImg.setCompany(CommonUtils.getCompany());
				totalImg.setHead(coimg.getHead());
				totalImg.setPtNo(coimg.getPtNo());
				totalImg.setName(coimg.getName());
				totalImg.setSpec(coimg.getSpec());
				totalImg.setUnit(coimg.getUnit());
				hs.put(key, totalImg);
			}
			totalImg.setMaterielNum(forD(totalImg.getMaterielNum())
					+ forD(coimg.getMaterielStockNum()));
			totalImg.setCyNum(forD(totalImg.getCyNum())
					+ forD(totalImg.getMaterielNum()));
		}
		List alist = new Vector(hs.values());
		for (int i = 0; i < alist.size(); i++) {
			CheckOwnerAccountComport obj = (CheckOwnerAccountComport) alist
					.get(i);
			this.checkCancelDao.saveCheckOwnerAccountComport(obj);
		}
		return alist;
	}

	/**
	 * 同步到核查表中
	 * 
	 * @param head
	 * @param emsHeadH2k
	 */
	public void newToCheckHead(CheckParameter head, EmsHeadH2k emsHeadH2k) {
		deleteCheckHeadAll(head);
		CheckHead checkHead = new CheckHead();
		checkHead.setEmsNo(emsHeadH2k.getEmsNo());
		checkHead.setTradeCode(emsHeadH2k.getTradeCode());
		checkHead.setTradeName(emsHeadH2k.getTradeName());
		checkHead.setMachCode(emsHeadH2k.getMachCode());
		checkHead.setMachName(emsHeadH2k.getMachName());
		checkHead.setHead(head);
		checkHead.setCompany(CommonUtils.getCompany()); // 公司id
		this.checkCancelDao.saveCheckHead(checkHead);
		List list = this.checkCancelDao.findCheckOwnerAccount(head);
		for (int i = 0; i < list.size(); i++) {
			CheckOwnerAccount account = (CheckOwnerAccount) list.get(i);
			CheckImg obj = new CheckImg();
			obj.setCheckHead(checkHead);
			obj.setCompany(CommonUtils.getCompany());
			String ptNo = account.getPtNo();
			List mergerlist = this.checkCancelDao
					.findMaterielFromInnerMergeData(ptNo);
			if (mergerlist != null && mergerlist.size() > 0) {
				Object[] objs = (Object[]) mergerlist.get(0);
				Integer seqNum = (Integer) objs[0];
				Complex complex = (Complex) objs[1];
				Unit unit = (Unit) objs[2];

				obj.setSeqNum(String.valueOf(seqNum));
				obj.setComplex(complex);
			}
			obj.setPtNo(account.getPtNo());
			obj.setName(account.getName());
			obj.setSpec(account.getSpec());
			obj.setTurnInNoReport(account.getTranNoCustomsNum());
			obj.setMaterByWay(account.getMaterielOnwayNum());
			obj.setPassExgUse(account.getExgConvertImgNum());
			obj.setDepose(account.getFlotsamNum());
			obj.setOnlines(account.getOnLineNum());
			obj.setMaterStock(account.getMaterielStockNum());
			obj.setHalfExgConvert(account.getHalfExgConvertImgNum());
			this.checkCancelDao.saveCheckImg(obj);
		}
	}

	/**
	 * 初始化单位
	 * 
	 * @return
	 */
	private Hashtable initUnit() {
		List list = commonBaseCodeDao.findUnit();
		Hashtable hs = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			Unit obj = (Unit) list.get(i);
			hs.put(obj.getName(), obj);
		}
		return hs;
	}

	/**
	 * 计算核查计算表
	 */
	public List accountCheckAccount(CheckParameter head) {

		Date beginDate = head.getBeginDate();// 核销起始日
		Date endDate = head.getEndDate(); // 核查结束日
		// 原料库存数量 = 期初库存 + 原料入库数量 - 原料出库数量
		// 料件进仓单据 = 直接进口 + 接转进口 + 国内购买 + 海关征税进口 + 车间返回 + 料件盘盈单 + 料件起初单
		// + 外发加工退回料件 + 受托加工进库 + 其他来源 + 料件转仓入库

		List<BalanceInfo> reslutList = new ArrayList<BalanceInfo>();
		HashMap<String, CheckOwnerAccount> resultHash = new HashMap<String, CheckOwnerAccount>();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				">=", beginDate, null));
		conditions.add(new Condition("and", null, " billMaster.validDate ",
				"<=", endDate, null));

		String billDetailRemainMateriel = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);

		String selects = "select sum(a.hsAmount),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name, a.ptPart  ";
		String groupBy = " group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name, a.ptPart";
		String leftOuter = " left join a.billMaster left join a.billMaster.billType left join a.hsUnit   ";

		List listBillDetailMateriel = this.checkCancelDao.commonSearch(selects,
				billDetailRemainMateriel, conditions, groupBy, leftOuter);

		List<FirstSumInfo> firstSumInfoList = new ArrayList<FirstSumInfo>();
		for (int i = 0; i < listBillDetailMateriel.size(); i++) {
			Object[] values = (Object[]) listBillDetailMateriel.get(i);
			FirstSumInfo firstSumInfo = new FirstSumInfo(values);
			firstSumInfoList.add(firstSumInfo);
		}

		List<CheckOwnerAccount> resultList = getResultList(firstSumInfoList,
				head); // 将List进行解析对不同的类型返回List

		return resultList;
	}

	/**
	 * 计算料件库存
	 * 
	 * @param firstSumInfoList
	 */
	// 计算料件海关帐库存数量
	public void jisuanLjStock(List<FirstSumInfo> firstSumInfoList) {
		Hashtable unitHs = initUnit();
		for (int i = 0; i < firstSumInfoList.size(); i++) {
			FirstSumInfo firstSumInfo = firstSumInfoList.get(i);
			CheckOwnerAccount item = null;
			Double hsAmount = firstSumInfo.getHsAmount() == null ? 0.0
					: firstSumInfo.getHsAmount();
			String typeCode = firstSumInfo.getTypeCode() == null ? ""
					: firstSumInfo.getTypeCode();
			String hsName = firstSumInfo.getHsName() == null ? ""
					: firstSumInfo.getHsName();
			String hsUnitName = firstSumInfo.getHsUnitName() == null ? ""
					: firstSumInfo.getHsUnitName();
			String hsSpec = firstSumInfo.getHsSpec() == null ? ""
					: firstSumInfo.getHsSpec();
			String ptNo = firstSumInfo.getPtNo() == null ? "" : firstSumInfo
					.getPtNo();
			// key = 料号+"/"+名称+"/"+规格+"/"+单位
			String key = ptNo + "/" + hsName + "/" + hsSpec + "/" + hsUnitName;
			if (resultHash.get(key) != null) {
				item = (CheckOwnerAccount) resultHash.get(key);
			} else {
				item = new CheckOwnerAccount();
				item.setPtNo(ptNo);
				item.setName(hsName);
				item.setSpec(hsSpec);
				item.setUnit((Unit) unitHs.get(hsUnitName));
				resultHash.put(key, item);
			}
			if (typeCode.equals("1003") // 1003 直接进口
					|| typeCode.equals("1004") // 1004 结转进口
					|| typeCode.equals("1005") // 1005 国内购买
					|| typeCode.equals("1011") // 1011 海关征税进口
					|| typeCode.equals("1006") // 1006 车间返回
					|| typeCode.equals("1007") // 1007 料件盘盈单
					|| typeCode.equals("1001") // 1001 料件期初单
					|| typeCode.equals("1012") // 1012 外发加工退回料件
					|| typeCode.equals("1008") // 1008 受托加工进库
					|| typeCode.equals("1009") // 1009 其他来源
					|| typeCode.equals("1010") // 1010 料件转仓入库
			) {
				item.setImportStockNum(forD(item.getImportStockNum())
						+ forD(hsAmount));
				item.setMaterielStockNum(forD(item.getMaterielStockNum())
						+ forD(hsAmount));
			} else if (typeCode.equals("1101") // 1101 车间领用
					|| typeCode.equals("1109") // 1109 受托加工领用
					|| typeCode.equals("1102") // 1102 料件退换
					|| typeCode.equals("1103") // 1103 料件复出
					|| typeCode.equals("1106") // 1106 结转料件退货单
					|| typeCode.equals("1104") // 1104 料件盘亏单
					|| typeCode.equals("1110") // 1110 外发加工出库
					|| typeCode.equals("1111") // 1111 受托加工退回料件
					|| typeCode.equals("1108") // 1108 其他领用
					|| typeCode.equals("1107") // 1107 其他料件退货单
					|| typeCode.equals("1105") // 1105 料件转仓出库
			) { // 出库
				item.setExportStockNum(forD(item.getExportStockNum())
						+ forD(hsAmount));
				item.setMaterielStockNum(forD(item.getMaterielStockNum())
						- forD(hsAmount));
			}
			if (typeCode.equals("1004")) { // 1004 结转进口
				item.setTransImportDoods(forD(item.getTransImportDoods())
						+ forD(hsAmount));
			}
		}
	}

	/**
	 * 将List进行解析对不同的类型返回List
	 * 
	 * @param firstSumInfoList
	 * @param head
	 * @return
	 */
	public List<CheckOwnerAccount> getResultList(
			List<FirstSumInfo> firstSumInfoList, CheckParameter head) {

		Date beginDate = head.getBeginDate();// 核查起始日
		Date endDate = head.getEndDate(); // 核查结束日

		// 计算海关帐料件
		resultHash.clear();
		jisuanLjStock(firstSumInfoList);

		// 计算海关帐成品
		resultCpHash.clear();
		jisuanCpStock(beginDate, endDate);

		Iterator<CheckOwnerAccount> iterator = resultHash.values().iterator();
		List arrayList = new ArrayList();
		while (iterator.hasNext()) {
			CheckOwnerAccount items = iterator.next();
			String ljNo = items.getPtNo().trim();

			// 0.在途数量 = 期初数量 +（1.清单进口 - 2.原料入库） + （3.成品出库 - 4.清单出口）折料

			// 1.清单进口
			Double listImportNum = this.checkCancelDao
					.findAtcMergeBeforeComInfoByptNo(ImpExpFlag.IMPORT,
							beginDate, endDate, ljNo, null);
			// 2.原料入库
			Double materielImportStockNum = items.getImportStockNum();
			// -----------------------------------------------------------------------

			// 3.成品出库折料
			// 初始化版本信息
			initVersionAndCp(ljNo);
			Double exportStockNum = getExportCpAmountByPtNo();
			// ------------------------------------------------------------------------
			// 4.计算出口清单折料
			jisuanExportListConvertImg(beginDate, endDate);
			Double exportListNum = Double.valueOf(0);
			// ------------------------------------------------------------------------
			// 0.原料在途数量
			items.setMaterielOnwayNum(listImportNum - materielImportStockNum
					+ exportStockNum - exportListNum);
			// ------------------------------------------------------------------------
			// 库存成品折料
			Double exportStockTotalNum = getExportCpAmountByPtNo1();
			items.setExgConvertImgNum(exportStockTotalNum);
			// ------------------------------------------------------------------------
			// 0.转进未报数量 = 1.期初数量 ＋ 2.本期结转进口单据 - 3.本期结转进口的报关清单
			Double transImportDoods = items.getTransImportDoods();
			Double transImportList = this.checkCancelDao
					.findAtcMergeBeforeComInfoByptNo(ImpExpFlag.IMPORT,
							beginDate, endDate, ljNo,
							Integer.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT));
			items.setTranNoCustomsNum(forD(transImportDoods)
					- forD(transImportList));
			// -------------------------------------------------------------------------
			// 0.废料数量 = 1.期初废料数量 ＋ 2.本期废料入库数量

			// -------------------------------------------------------------------------
			// 0.在线数量 = 1.期初数量 + 1.本期车间领用料件数量 - 2.本期成品入库折原材料数量 - 3.本期其他出车间料件数量
			// ( )
			items.setOnLineNum(forD(transImportDoods));

			items.setCompany(CommonUtils.getCompany());
			items.setHead(head);
			this.checkCancelDao.saveCheckOwnerAccount(items);
			arrayList.add(items);
		}
		return arrayList;
	}

	/**
	 * 根据料号及海关帐成品出口数量汇总
	 * 
	 * @return
	 */
	private Double getExportCpAmountByPtNo() {
		Double x = Double.valueOf(0);
		List list = new Vector(hsVersionInfo.values());
		for (int i = 0; i < list.size(); i++) {
			VersionInfo obj = (VersionInfo) list.get(i);
			String versionNo = obj.getCpandversion();
			VersionInfo info = (VersionInfo) resultCpHash.get(versionNo);
			Double totalAmount = ((info == null) ? Double.valueOf(0) : info
					.getExportAmount());
			x = x + (forD(totalAmount) * forD(obj.getUnitUse()));
		}
		return x;
	}

	/**
	 * 根据料号及海关帐成品出口数量汇总
	 * 
	 * @return
	 */
	private Double getExportCpAmountByPtNo1() {
		Double x = Double.valueOf(0);
		List list = new Vector(hsVersionInfo.values());
		for (int i = 0; i < list.size(); i++) {
			VersionInfo obj = (VersionInfo) list.get(i);
			String versionNo = obj.getCpandversion();
			VersionInfo info = (VersionInfo) resultCpHash.get(versionNo);
			Double totalAmount = ((info == null) ? Double.valueOf(0) : info
					.getTotalAmount());
			x = x + (forD(totalAmount) * forD(obj.getUnitUse()));
		}
		return x;
	}

	/**
	 * 初始化成品 + 版本 + 料件 + 单位用量 + 出口成品数量
	 */
	public void initVersionAndCp(String ptNo) {
		hsVersionInfo.clear();
		List list = this.checkCancelDao.findDetailByVersionAndPtNo(ptNo);
		for (int i = 0; i < list.size(); i++) {
			MaterialBomDetail bom = (MaterialBomDetail) list.get(i);
			String cpNo = bom.getVersion().getMaster().getMateriel().getPtNo();// 成品号
			String version = bom.getVersion().getVersion().toString();
			String key = cpNo + "/" + version;
			VersionInfo obj = null;
			if (hsVersionInfo.get(key) == null) {
				obj = new VersionInfo();
				obj.setCpandversion(key);
				obj.setUnitUse(forD(bom.getUnitWaste())
						/ (1.0 - forD(bom.getWaste())));
				hsVersionInfo.put(key, obj);
			}
		}
	}

	/**
	 * 出口成品折算料件
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	public void jisuanCpStock(Date beginDate, Date endDate) {
		String productTableName = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
		String selects = " select sum(a.hsAmount),a.billMaster.billType.code,a.ptPart,a.version from "
				+ productTableName
				+ " a "
				+ " where a.billMaster.isValid = ? and a.billMaster.validDate >= ? and a.billMaster.validDate <= ? "
				+ " and a.billMaster.company.id = ? "
				+ " group by a.billMaster.billType.code,a.ptPart,a.version "
				+ " order by a.version ";// order by 确保 bom id 只查询一次

		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(beginDate);
		params.add(endDate);
		params.add(CommonUtils.getCompany().getId());

		// 成品
		List list = this.checkCancelDao.commonSearch(selects, params.toArray());

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			Integer version = (Integer) objs[3];
			String typeCode = (String) objs[1] == null ? "" : (String) objs[1];
			String ptNo = (String) objs[2];
			Double hsAmount = (Double) objs[0];
			String key = ptNo + "/"
					+ String.valueOf(version == null ? "" : version);
			VersionInfo obj = null;
			if (resultCpHash.get(key) != null) {
				obj = (VersionInfo) resultCpHash.get(key);
			} else {
				obj = new VersionInfo();
				obj.setCpandversion(key);
				resultCpHash.put(key, obj);
			}
			if (typeCode.equals("2001") // 2001 成品期初单
					|| typeCode.equals("2002")// 2002 车间入库
					|| typeCode.equals("2003")// 2003 退厂返工
					|| typeCode.equals("2004")// 2004 成品盘盈单
					|| typeCode.equals("2005")// 2005 成品转仓入库
					|| typeCode.equals("2007")// 2007 受托加工车间入库
					|| typeCode.equals("2008")// 2008 其他成品退货单
					|| typeCode.equals("2009")// 2009 结转成品退货单
					|| typeCode.equals("2010")// 2010 受托加工退回成品
			) { // 入库
				obj.setImportAmount(forD(obj.getTotalAmount()) + forD(hsAmount));
				obj.setTotalAmount(forD(obj.getTotalAmount()) + forD(hsAmount));
			}
			if (typeCode.equals("2101") // 2101 直接出口
					|| typeCode.equals("2102")// 2102 结转出口
					|| typeCode.equals("2103")// 2103 返回车间
					|| typeCode.equals("2104")// 2104 返工复出
					|| typeCode.equals("2105")// 2105 成品盘亏单
					|| typeCode.equals("2106")// 2106 海关批准内销
					|| typeCode.equals("2107")// 2107 其他内销
					|| typeCode.equals("2108")// 2108 其他处理
					|| typeCode.equals("2109")// 2109 成品转仓出库
					|| typeCode.equals("2110")// 2110 受托加工返回
			) { // 出库
				obj.setExportAmount(forD(obj.getTotalAmount()) + forD(hsAmount));
				obj.setTotalAmount(forD(obj.getTotalAmount()) - forD(hsAmount));
			}
		}
	}

	/**
	 * 计算出口清单折料数量
	 * 
	 * @param beginDate
	 * @param endDate
	 */
	public void jisuanExportListConvertImg(Date beginDate, Date endDate) {
		List list = this.checkCancelDao.findAtcMergeBeforeComInfoCpByptNo(
				ImpExpFlag.EXPORT, beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String cpNo = (String) objs[1];
			String versionNo = (String) objs[2];
			Double cpAmount = (Double) objs[0];
			String key = cpNo + "/" + versionNo;
			VersionInfo obj = null;
			if (hsVersionInfo.get(key) != null) {
				obj = (VersionInfo) hsVersionInfo.get(key);
				// Double unitAmount = forD(obj.getHsAmount());
				/*
				 * obj.setTotalAmount(forD(obj.getTotalAmount()) -
				 * (forD(cpAmount) unitAmount));
				 */

			}
		}
	}

	private Double forD(Double d) {
		if (d != null) {
			return d;
		}
		return Double.valueOf(0);
	}

	/**
	 * 最初的统计信息
	 */
	public class FirstSumInfo {
		private Double hsAmount = Double.valueOf(0);

		private String typeCode;

		private String hsName;

		private String hsSpec;

		private String hsUnitName;

		private String ptNo;

		public FirstSumInfo() {

		}

		public FirstSumInfo(Object[] values) {
			hsAmount = (Double) values[0];
			typeCode = (String) values[1];
			hsName = (String) values[2];
			hsSpec = (String) values[3];
			hsUnitName = (String) values[4];
			ptNo = (String) values[5];
		}

		/**
		 * 取得报关数量
		 * 
		 * @return hsAmount 报关数量.
		 */
		public Double getHsAmount() {
			return hsAmount;
		}

		/**
		 * 设置报关数量
		 * 
		 * @param ptAmount
		 *            工厂数量.
		 */
		public void setHsAmount(Double ptAmount) {
			this.hsAmount = ptAmount;
		}

		/**
		 * 取得报关名称
		 * 
		 * @return hsName 报关名称 .
		 */
		public String getHsName() {
			return hsName;
		}

		/**
		 * 设置报关名称
		 * 
		 * @param ptName
		 *            工厂名称
		 */
		public void setHsName(String ptName) {
			this.hsName = ptName;
		}

		/**
		 * 取得报关规格
		 * 
		 * @return hsSpec 报关规格.
		 */
		public String getHsSpec() {
			return hsSpec;
		}

		/**
		 * 设置报关规格
		 * 
		 * @param ptSpec
		 *            工厂规格
		 */
		public void setHsSpec(String ptSpec) {
			this.hsSpec = ptSpec;
		}

		/**
		 * 取得报关单位
		 * 
		 * @return hsUnitName 报关单位
		 */
		public String getHsUnitName() {
			return hsUnitName;
		}

		/**
		 * 设置报关单位
		 * 
		 * @param ptUnitName
		 *            工厂单位
		 */
		public void setHsUnitName(String ptUnitName) {
			this.hsUnitName = ptUnitName;
		}

		/**
		 * 取得单据类型代码
		 * 
		 * @return typeCode 单据类型代码.
		 */
		public String getTypeCode() {
			return typeCode;
		}

		/**
		 * 设置单据类型代码
		 * 
		 * @param typeCode
		 *            单据类型代码
		 */
		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}

		public String getPtNo() {
			return ptNo;
		}

		public void setPtNo(String ptNo) {
			this.ptNo = ptNo;
		}

	}

	/**
	 * 内部类－－最初的统计信息
	 */
	public class VersionInfo {
		/**
		 * 单位用量
		 */
		private Double unitUse = Double.valueOf(0);// 单位用量
		/**
		 * 成品号+"/"+"版本号"
		 */
		private String cpandversion = null; // 成品号+"/"+"版本号"
		/**
		 * 入库 - 出库
		 */
		private Double totalAmount = Double.valueOf(0); // 入库 - 出库
		/**
		 * 成品入库
		 */
		private Double importAmount = Double.valueOf(0);// 成品入库
		/**
		 * 成品出库
		 */
		private Double exportAmount = Double.valueOf(0);// 成品出库

		/**
		 * 获取cpandversion
		 */
		public String getCpandversion() {
			return cpandversion;
		}

		/**
		 * 设置 cpandversion
		 */
		public void setCpandversion(String cpandversion) {
			this.cpandversion = cpandversion;
		}

		/**
		 * 获取totalAmount
		 */
		public Double getTotalAmount() {
			return totalAmount;
		}

		/**
		 * 设置 totalAmount
		 */
		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

		/**
		 * 获取exportAmount
		 */
		public Double getExportAmount() {
			return exportAmount;
		}

		/**
		 * 设置 exportAmount
		 */
		public void setExportAmount(Double exportAmount) {
			this.exportAmount = exportAmount;
		}

		/**
		 * 获取importAmount
		 */
		public Double getImportAmount() {
			return importAmount;
		}

		/**
		 * 设置 importAmount
		 */
		public void setImportAmount(Double importAmount) {
			this.importAmount = importAmount;
		}

		/**
		 * 获取unitUse
		 */
		public Double getUnitUse() {
			return unitUse;
		}

		/**
		 * 设置 unitUse
		 * 
		 * @param unitUse
		 */
		public void setUnitUse(Double unitUse) {
			this.unitUse = unitUse;
		}

	}

	public void deleteCheckHeadAll(CheckParameter head) {
		List list = this.checkCancelDao.findCheckHead(head);
		for (int i = 0; i < list.size(); i++) {
			CheckHead obj = (CheckHead) list.get(i);
			this.checkCancelDao.deleteCheckHead(obj);
			this.checkCancelDao.deleteAllCheckImgExg(obj);
		}
	}

	/**
	 * 获取commonBaseCodeDao
	 * 
	 * @return
	 */
	public CommonBaseCodeDao getCommonBaseCodeDao() {
		return commonBaseCodeDao;
	}

	/**
	 * 删除成品折算料件
	 * 
	 * @return
	 */
	public void setCommonBaseCodeDao(CommonBaseCodeDao commonBaseCodeDao) {
		this.commonBaseCodeDao = commonBaseCodeDao;
	}

	public List findLjUseNum(EmsHeadH2k head, boolean isOwner,
			CancelHead cancelHead) {
		hs.clear();
		List list = this.checkCancelDao.finLjUseNum(head, isOwner, cancelHead);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String ljNo = String.valueOf(obj[2]);
			Double unitWear = fd((Double) obj[3]);
			Double wear = fd((Double) obj[4]);
			Double unitUse = unitWear / (1 - (wear * 0.01));

//			Double useNum = fd((Double) obj[5]) - fd((Double) obj[6]);  //旧
			Double useNum = (Double) obj[5]; //新
			Double ljUseNum = useNum * unitUse;
			Double bjlNum = ljUseNum * (wear * 0.01);
			TempDD temp = null;
			if (hs.get(ljNo) == null) {
				temp = new TempDD();
				temp.setLjseqnum(ljNo);
				temp.setAa(ljUseNum);
				temp.setBb(bjlNum);
				hs.put(ljNo, temp);
			} else {
				temp = (TempDD) hs.get(ljNo);
				temp.setAa(fd(temp.getAa()) + ljUseNum);
				temp.setBb(fd(temp.getBb()) + bjlNum);
			}
		}
		return list;
	}

	public List findLjUseDetail(CancelHead head, List list, boolean isOwner,
			ProgressInfo info) {
		List ls = new ArrayList();
		if (info != null) {
			info.setMethodName("系统正在计算料件耗用");
		}
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String cpNo = String.valueOf(obj[0]);
			String version = String.valueOf(obj[1]);
			String ljNo = String.valueOf(obj[2]);
			Double unitWear = fd((Double) obj[3]);
			Double wear = fd((Double) obj[4]);
			Double unitUse = unitWear / (1 - (wear * 0.01));
			Double useNum = fd((Double) obj[5]);// - fd((Double) obj[6]);
			Double ljUseNum = useNum * unitUse;
			CancelUnitWear bill = null;
			if (isOwner) {
				bill = new CancelOwnerUnitWear();
			} else {
				bill = new CancelCusUnitWear();
			}
			bill.setCpSeqNum(Integer.valueOf(cpNo));
			bill.setVersion(Integer.valueOf(version));
			bill.setLjSeqNum(Integer.valueOf(ljNo));
			bill.setUnitWear(unitWear);
			bill.setWear(wear);
			bill.setUnitUse(unitUse);
			bill.setCpUse(useNum);
			bill.setLjUse(ljUseNum);
			bill.setCancelHead(head);
			bill.setCompany(CommonUtils.getCompany());
			this.checkCancelDao.saveOrUpdateDirect(bill);
			ls.add(bill);
			// System.out.println("bom---"+i+"/"+list.size());
			if (info != null) {
				info.setCurrentNum(i + 1);
			}
		}
		// this.checkCancelDao.batchSaveOrUpdate(ls);
		return ls;
	}

	public void findCpNum(EmsHeadH2k head, boolean isOwner,
			CancelHead cancelHead) {
		cpHs.clear();
		List list = this.checkCancelDao.finCpUseNum(head, isOwner, cancelHead);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			String cpNo = String.valueOf(obj[0]);
			Double aa = fd((Double) obj[1]);
			Double cc = fd((Double) obj[2]);
			Double dd = fd((Double) obj[3]);// 重量
			Double ee = fd((Double) obj[4]);
			Double ff = fd((Double) obj[5]);
			Double exportNum = aa - cc;// 出口数量
			Double exportMoney = ee - ff;// 出口金额

			TempDD temp = null;
			if (cpHs.get(cpNo) == null) {
				temp = new TempDD();
				temp.setLjseqnum(cpNo);
				temp.setAa(exportNum);
				temp.setBb(exportMoney);
				temp.setUnitUse(dd);
				cpHs.put(cpNo, temp);
			} else {
				temp = (TempDD) cpHs.get(cpNo);
				temp.setAa(fd(temp.getAa()) + exportNum);
				temp.setBb(fd(temp.getBb()) + exportMoney);
				temp.setUnitUse(fd(temp.getUnitUse()) + dd);
			}
		}
	}

	public List findEmsHeadH2kVersionByFactoryStoryProductPtNo(String ptNo) {
		List resultlist = new ArrayList();
		List list = checkCancelDao.findEmsEdiMergerExgBeforeByPtNo(ptNo);
		if (list == null || list.size() == 0) {
			return resultlist;
		}
		Integer seqNum = (Integer) list.get(0);
		List exglist = checkCancelDao.findEmsHeadH2kExgBySeqNum(seqNum);
		if (exglist == null || exglist.size() == 0) {
			return resultlist;
		}
		EmsHeadH2kExg emsHeadH2kExg = (EmsHeadH2kExg) exglist.get(0);
		List bomlist = checkCancelDao.findEmsHeadH2kVersion(emsHeadH2kExg);
		resultlist.addAll(bomlist);
		return resultlist;
	}

	public List findAllEmsHeadH2kExgVersion() {
		List result = new ArrayList();
		List<FactoryStoryProduct> list = checkCancelDao
				.findFactoryStoryProduct();
		if (list == null || list.size() == 0) {
			return result;
		}
		for (FactoryStoryProduct data : list) {
			List resultlist = new ArrayList();
			resultlist
					.addAll(findEmsHeadH2kVersionByFactoryStoryProductPtNo(data
							.getPtNo()));
			for (int p = 0; p < resultlist.size(); p++) {
				Object[] objs = new Object[3];
				objs[0] = data.getPtNo();
				objs[1] = data.getQuantity() == null ? new Double(0.0) : data
						.getQuantity();
				objs[2] = ((EmsHeadH2kVersion) resultlist.get(p)).getName();
				result.add(objs);
			}
		}
		return result;
	}

	public List findEmsHeadH2kBom(EmsHeadH2kVersion version, Double amountPrice) {
		List result = new ArrayList();
		List<EmsHeadH2kBom> list = this.checkCancelDao
				.findEmsHeadH2kBom(version);
		for (EmsHeadH2kBom data : list) {
			TempEmsHeadH2kBom teh = new TempEmsHeadH2kBom();
			teh.setComplex(data.getComplex());
			teh.setName(data.getName());
			teh.setNote(data.getNote());
			teh.setPrice(data.getPrice());
			teh.setSeqNum(data.getSeqNum());
			teh.setSpec(data.getSpec());
			teh.setUnit(data.getUnit());
			teh.setUnitWear(data.getUnitWear());
			teh.setWear(data.getWear());
			teh.setComplex(data.getComplex());
			Double unitUse = null;
			if (data.getWear() != null && data.getUnitWear() != null
					&& data.getWear() < 100 && data.getWear() >= 0) {
				double dou = data.getUnitWear();
				try {
					unitUse = dou / (1.0 - data.getWear() / 100);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				teh.setTotalUse(unitUse
						* (amountPrice == null ? 0.0 : amountPrice));
			} else if (data.getWear() == null && data.getUnitWear() != null) {
				try {
					teh.setTotalUse(data.getUnitWear() * amountPrice);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				teh.setTotalUse(0.0);
			}
			result.add(teh);
		}
		return result;
	}

	public void saveOrUpdateFactoryStoryProduct(List datalist) {
		for (int k = 0; k < datalist.size(); k++) {
			FactoryStoryProduct data = (FactoryStoryProduct) datalist.get(k);
			List checklist = checkCancelDao
					.findEmsEdiMergerExgBeforeByPtNo(data.getPtNo());
			List checklistptNo = checkCancelDao
					.findFactoryStoryProductByPtNo(data.getPtNo());
			if (checklist.size() != 0 && checklistptNo.size() == 0) {
				data.setIsInnerMeger(true);
				checkCancelDao.saveOrUpdate(data);
			}
		}

	}

	public void delFactoryStoryProduct(List datalist) {
		for (int k = 0; k < datalist.size(); k++) {
			FactoryStoryProduct data = (FactoryStoryProduct) datalist.get(k);
			checkCancelDao.delete(data);
		}
	}

	/**
	 * 查找帐册BOM－－报表分析用
	 * 
	 * @return
	 */
	public List findTempEmsHeadH2kBomAnyle(List list) {
		Map map = new HashMap();
		List<TempEmsHeadH2kBom> templist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] strs = (Object[]) list.get(i);
			String ptno = (String) strs[0];
			String versionname = (String) strs[2];
			Double quantity = (Double) strs[1];
			List lists = checkCancelDao.findEmsEdiMergerExgBeforeByPtNo(ptno);
			if (lists == null || lists.size() == 0) {
				continue;
			}
			Integer seqNum = (Integer) lists.get(0);
			List exglist = checkCancelDao.findEmsHeadH2kExgBySeqNum(seqNum);
			if (exglist == null || exglist.size() == 0) {
				continue;
			}
			EmsHeadH2kExg emsHeadH2kExg = (EmsHeadH2kExg) exglist.get(0);
			List bomlist = checkCancelDao.findEmsHeadH2kVersion(emsHeadH2kExg);
			if (bomlist == null || bomlist.size() == 0) {
				continue;
			}
			for (int s = 0; s < bomlist.size(); s++) {
				EmsHeadH2kVersion version = (EmsHeadH2kVersion) bomlist.get(s);
				if (version.getName().equals(versionname)) {
					templist.addAll(findEmsHeadH2kBom(version, quantity));
				}
			}
		}
		for (TempEmsHeadH2kBom data : templist) {
			if (map.get(data.getSeqNum().toString()) != null) {
				TempEmsHeadH2kBom old = (TempEmsHeadH2kBom) map.get(data
						.getSeqNum().toString());
				data.setTotalUse((old.getTotalUse() == null ? 0.0 : old
						.getTotalUse())
						+ (data.getTotalUse() == null ? 0.0 : data
								.getTotalUse()));
			}
			map.put(data.getSeqNum().toString(), data);
		}
		return new ArrayList(map.values());
	}

	public List<ErrorMessage> checkCancelData(CancelHead cancelHead) {
		List<ErrorMessage> listError = new ArrayList<ErrorMessage>();
		if (cancelHead != null) {
			// 检查比例栏位
			if (cancelHead.getScale() != null) {
				if (cancelHead.getScale() > 100) {
					ErrorMessage error = new ErrorMessage();
					error.setTitle("比例");
					error.setMessage("出口成品耗用料总金额与本期出口总金额的比例一般为80%至85%之间，此比例超过了100%，请检查。");
					error.setIsAllow(false);
					error.setCheckDate(new Date());
					listError.add(error);
				}
			}
			// 检查财务人员栏位
			if (cancelHead.getCheckStaffName() == null
					|| "".equals(cancelHead.getCheckStaffName())) {
				ErrorMessage error = new ErrorMessage();
				error.setTitle("财务人员");
				error.setMessage("为了不影响税局正常核销,在向海关申报正式\n报销前，请与财务部门核对本期各项金额数据。");
				error.setIsAllow(false);
				error.setCheckDate(new Date());
				listError.add(error);
			}
			// 检查录入员代码
			if (cancelHead.getInputUser() != null
					|| !"".equals(cancelHead.getInputUser())) {
				if (!cancelHead.getInputUser().matches("[0-9]+")) {
					ErrorMessage error = new ErrorMessage();
					error.setTitle("录入员代码");
					error.setMessage("核销表头【录入员代码】" + cancelHead.getInputUser()
							+ "值只允许填写数字型字符如：001，请检查 。");
					error.setIsAllow(false);
					error.setCheckDate(new Date());
					listError.add(error);
				}
			}
			// 检查料件明细表
			List<CancelCusImgResult> aoirlist = checkCancelDao
					.findCancelImgResult(cancelHead, false);
			for (CancelCusImgResult aoir : aoirlist) {
				if (aoir.getAveragePrice() == null
						|| aoir.getAveragePrice() == 0) {
					ErrorMessage error = new ErrorMessage();
					error.setTitle("【备案号】" + aoir.getEmsSeqNum() + "/【名称】"
							+ aoir.getName());
					error.setMessage("【备案号】" + aoir.getEmsSeqNum()
							+ "的平均单价为空或零，请手工调整单价,调整完后重新计算核销表头。");
					error.setIsAllow(false);
					error.setCheckDate(new Date());
					listError.add(error);
				}
				if (aoir.getResultNum() != null
						|| aoir.getResultSumPrice() != null) {
					if (aoir.getResultNum() < 0 || aoir.getResultSumPrice() < 0) {
						ErrorMessage error = new ErrorMessage();
						String errtitle = "";
						if (aoir.getResultNum() < 0
								&& aoir.getResultSumPrice() < 0) {
							errtitle = "结余数量" + aoir.getResultNum().toString()
									+ "/结余金额"
									+ aoir.getResultSumPrice().toString();
						} else if (aoir.getResultNum() < 0) {
							errtitle = "结余数量" + aoir.getResultNum().toString();
						} else {
							errtitle = "结余金额"
									+ aoir.getResultSumPrice().toString();
						}
						error.setTitle("【备案号】" + aoir.getEmsSeqNum() + "/"
								+ aoir.getName());
						error.setMessage("【备案号】" + aoir.getEmsSeqNum()
								+ errtitle + "为负数，请检查。");
						error.setIsAllow(false);
						error.setCheckDate(new Date());
						listError.add(error);
					}
				}
			}
		}
		return listError;
	}
}