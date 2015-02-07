/*
 * Created on 2005-1-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillMasterMateriel;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.dao.CasTransferFactoryDao;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.TempAllTransFactDiffInfo;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.entity.TempTransFactCompareInfo;
import com.bestway.bcus.cas.entity.TempTransFactRecvSendDetailInfo;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SBillType;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;

/**
 * 贺巍于2010年1月7日添加部分注释 海关帐转厂Logic类
 * 
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class TransferInfoLogic {
	/**
	 * 海关帐Dao
	 */
	private CasDao casDao = null;

	/**
	 * 海关帐转厂DAO
	 */
	private CasTransferFactoryDao casTransferFactoryDao = null;

	/**
	 * 取得海关帐Dao的内容
	 * 
	 * @return 海关帐Dao
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设计海关帐Dao
	 * 
	 * @param casDao
	 *            海关帐Dao
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * 获取海关帐转厂DAO
	 * 
	 * @return
	 */
	public CasTransferFactoryDao getCasTransferFactoryDao() {
		return casTransferFactoryDao;
	}

	/**
	 * 设置海关帐转厂DAO
	 * 
	 * @param casTransferFactoryDao
	 */
	public void setCasTransferFactoryDao(
			CasTransferFactoryDao casTransferFactoryDao) {
		this.casTransferFactoryDao = casTransferFactoryDao;
	}

	/**
	 * 查询单据明细来自已选商品信息
	 * 
	 * @param list
	 *            临时单据明细
	 * @return 根据已选商品单据获得其所有未转结转单的商品信息
	 */
	public List findBillDetailByParent(List list) {
		List oldList = this.casDao.findBillDetailByParent(list);
		List newList = new ArrayList();
		for (int i = 0; i < oldList.size(); i++) {
			TempBillDetail tempBillDetail = new TempBillDetail();
			tempBillDetail.setBillDetail((BillDetail) oldList.get(i));
			tempBillDetail.setIsSelected(new Boolean(false));
			newList.add(tempBillDetail);
		}
		return newList;
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 IXRB == Import Exprot Request Bill 进出口申请单 billCode
	 * 单据类型下的单据代号
	 * 
	 * @param impExpType
	 *            单据类型
	 * @return 与指定类型匹配的单据商品信息不是全部转为真的单据记录
	 */
	public List findBillMasterIsAvailabilityToIXRB(int impExpType) {
		int sBillType = -1;
		String[] billCode = null;
		if (impExpType == ImpExpType.DIRECT_IMPORT) {
			sBillType = SBillType.MATERIEL_IN;// 1、 料件入仓
			billCode = new String[1];
			billCode[0] = "1003";// 直接进口：1003-----料件进口
		} else if (impExpType == ImpExpType.GENERAL_TRADE_IMPORT) {
			sBillType = SBillType.MATERIEL_IN;// 1、 料件入仓
			billCode = new String[1];
			billCode[0] = "1011";// 海关征税进口：1011-----一般性贸易进口
		} else if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
			sBillType = SBillType.MATERIEL_OUT;// 2、 料件出仓
			billCode = new String[2];
			billCode[0] = "1102";// 料件退回：1102---料件出口
			billCode[1] = "1103";// 料件复出：1103---料件出口
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			sBillType = SBillType.MATERIEL_OUT;// 2、 料件出仓
			billCode = new String[1];
			billCode[0] = "1004";// 料件转仓出库：1105----料件转厂
		}

		else if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			sBillType = SBillType.PRODUCE_IN;// //成品入库
			billCode = new String[1];
			billCode[0] = "2003";// 退厂返工：2003-----退厂返工
		} else if (impExpType == ImpExpType.DIRECT_EXPORT) {
			sBillType = SBillType.PRODUCE_OUT;// //成品出库
			billCode = new String[1];
			billCode[0] = "2101";// 直接出口：2101-----成品出口
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT) {
			sBillType = SBillType.PRODUCE_OUT;// //成品出库
			billCode = new String[1];
			billCode[0] = "2102";// 直接出口：2101-----成品出口
		} else if (impExpType == ImpExpType.REWORK_EXPORT) {
			sBillType = SBillType.PRODUCE_OUT;// //成品出库
			billCode = new String[1];
			billCode[0] = "2104";// 直接出口：2101-----成品出口
		} else if (impExpType == ImpExpType.REMIAN_MATERIAL_BACK_PORT) {
			sBillType = SBillType.LEFTOVER_MATERIEL_OUT;// //边角料出仓
			billCode = new String[1];
			billCode[0] = "6102";// 边角料退运出口：6102--边角料退港
		} else if (impExpType == ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES) {
			sBillType = SBillType.LEFTOVER_MATERIEL_OUT;// //边角料出仓
			billCode = new String[1];
			billCode[0] = "6104";// 边角料征税内销：6104---边角料内销
		}
		List templist = new ArrayList();
		for (int m = 0; m < billCode.length; m++) {
			List oldList = this.casDao.findBillMasterIsAvailabilityToIXRB(
					sBillType, billCode[m]);
			templist.addAll(oldList);
		}
		List newList = new ArrayList();
		for (int i = 0; i < templist.size(); i++) {
			TempBillMaster tempBillMaster = new TempBillMaster();
			tempBillMaster.setBillMaster((BillMaster) templist.get(i));
			tempBillMaster.setIsSelected(new Boolean(false));
			newList.add(tempBillMaster);
		}
		return newList;
	}

	/**
	 * 根据已选商品单据获得其所有未转进出口的商品信息 IXRB=improt expromt request bill
	 * 
	 * @param list
	 *            临时单据表头明细
	 * @return 所有未转进出口的商品信息
	 */
	public List findBillDetailByParentToIMXP(List list) {
		List oldList = this.casDao.findBillDetailByParentToIMXP(list);
		List newList = new ArrayList();
		for (int i = 0; i < oldList.size(); i++) {
			TempBillDetail tempBillDetail = new TempBillDetail();
			tempBillDetail.setBillDetail((BillDetail) oldList.get(i));
			tempBillDetail.setIsSelected(new Boolean(false));
			newList.add(tempBillDetail);
		}
		return newList;
	}

	/**
	 * 查询转厂收／送货商品名称
	 * 
	 * @return
	 */
	public List<TempObject> findTransFactRecvSendCommName(boolean isImport,
			ScmCoc scmCoc, String commName, Date beginDate, Date endDate) {
		String billDetailName = (isImport ? "BillDetailMateriel"
				: "BillDetailProduct");
		String[] billTypes = (isImport ? new String[] { "1004", "1106", "1015",
				"1016" }// 结转进口，结转料件退货单,已收货未结转期初单,已结转未收货期初单
				: new String[] { "2009", "2102", "2011", "2012" });// 结转成品退货单，结转出口,已交货未结转期初单,已结转未交货期初单
		List<TempObject> list = this.casTransferFactoryDao
				.findTransFactRecvSendCommName(billDetailName, billTypes,
						scmCoc, commName, beginDate, endDate);
		return list;
	}

	/**
	 * 查询转厂收／送货商品名称
	 * 
	 * @return
	 */
	public List findTransFactRecvSendCommName(boolean isImport, ScmCoc scmCoc,
			String beginMonth, String endMonth) {
		Date beginDate = this.getBeginEndDateByTerm(beginMonth)[0];
		Date endDate = this.getBeginEndDateByTerm(endMonth)[1];

		return this.findTransFactRecvSendCommName(isImport, scmCoc, null,
				CommonUtils.getBeginDate(beginDate), CommonUtils
						.getEndDate(endDate));
	}
	/**
	 * 查询转厂收／送货商品名称y
	 * 
	 * @return
	 */
	public List findTransFactRecvSendCommName2(boolean isImport, ScmCoc scmCoc,String commName,
			String beginMonth, String endMonth) {
		Date beginDate = this.getBeginEndDateByTerm(beginMonth)[0];
		Date endDate = this.getBeginEndDateByTerm(endMonth)[1];

		return this.findTransFactRecvSendCommName(isImport, scmCoc, commName,
				CommonUtils.getBeginDate(beginDate), CommonUtils
						.getEndDate(endDate));
	}

	/**
	 * 查询转厂收／送货明细表
	 * 
	 * @return
	 */
	public List findTransFactRecvSendDetailInfo(boolean isImport,boolean isCustomNo,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		String billDetailName = (isImport ? "BillDetailMateriel"
				: "BillDetailProduct");
		String[] billTypes = (isImport ? new String[] { "1004", "1106" }// 结转进口、结转料件退货单
				: new String[] { "2009", "2102" });// 结转成品退货单、结转出口
		List list = this.casTransferFactoryDao.findTransFactRecvSendDetailInfo(
				billDetailName,isCustomNo, billTypes, scmCoc, beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			BillDetail billDetail = (BillDetail) list.get(i);
			TempTransFactRecvSendDetailInfo tempInfo = new TempTransFactRecvSendDetailInfo();
			tempInfo.setBillNo(billDetail.getBillMaster().getBillNo());
			tempInfo.setValidDate(billDetail.getBillMaster().getValidDate());
			tempInfo.setPtPart(billDetail.getPtPart());
			tempInfo.setPtName(billDetail.getPtName());
			tempInfo.setPtSpec(billDetail.getPtSpec());
			tempInfo.setPtUnit(billDetail.getPtUnit());
			tempInfo.setPoSoBillNo(billDetail.getOderBillNo());
			tempInfo.setComplex(billDetail.getComplex());
			tempInfo.setHsName(billDetail.getHsName());
			tempInfo.setHsSpec(billDetail.getPtSpec());
			tempInfo.setHsUnit(billDetail.getHsUnit());
			tempInfo.setScmCoc(billDetail.getBillMaster().getScmCoc());
			tempInfo.setHsSpec(billDetail.getHsSpec());
			tempInfo.setNotice(billDetail.getNote());
			tempInfo.setNote(billDetail.getBillMaster().getNotice());
			tempInfo.setCustomNo(billDetail.getCustomNo());
			tempInfo.setTakeBillNo(billDetail.getTakeBillNo());
			if (isImport) {// 结转进口，结转料件退货单
							//hwy2013-1-4 把退货单数量\净重\折算报关数量转化为负数显示
				if ("1004".equals(billDetail.getBillMaster().getBillType().getCode())) {
					tempInfo.setPtRecvAmount(billDetail.getPtAmount()==null?0:billDetail.getPtAmount());
					tempInfo.setHsRecvAmount(billDetail.getHsAmount()==null?0:billDetail.getHsAmount());
					tempInfo.setRecvNetWeight(billDetail.getNetWeight()==null?0:billDetail.getNetWeight());
					
				} else if ("1106".equals(billDetail.getBillMaster()
						.getBillType().getCode())) {
					Double Amount =billDetail.getPtAmount();
					Double HsAmount = billDetail.getHsAmount();
					Double NetWeight = billDetail.getNetWeight();
					tempInfo.setPtRecvAmount(Amount==null?0:(0-Amount));
					tempInfo.setHsRecvAmount(HsAmount==null?0:(0-HsAmount));
					tempInfo.setRecvNetWeight(NetWeight==null?0:(0-NetWeight));
				}
			} else {// 结转成品退货单，结转出口
				if ("2102".equals(billDetail.getBillMaster().getBillType()
						.getCode())) {
					tempInfo.setPtRecvAmount(billDetail.getPtAmount()==null?0:billDetail.getPtAmount());
					tempInfo.setHsRecvAmount(billDetail.getHsAmount()==null?0:billDetail.getHsAmount());
					tempInfo.setRecvNetWeight(billDetail.getNetWeight()==null?0:billDetail.getNetWeight());
				} else if ("2009".equals(billDetail.getBillMaster()
						.getBillType().getCode())) {
					tempInfo.setPtRecvAmount(billDetail.getPtAmount()==null?0:(0-billDetail.getPtAmount()));
					tempInfo.setHsRecvAmount(billDetail.getHsAmount()==null?0:(0-billDetail.getHsAmount()));
					tempInfo.setRecvNetWeight(billDetail.getNetWeight()==null?0:(0-billDetail.getNetWeight()));
				}
			}
			
			tempInfo.setComplex(billDetail.getComplex());
			tempInfo.setHsName(billDetail.getHsName());
			tempInfo.setHsSpec(billDetail.getPtSpec());
			tempInfo.setHsUnit(billDetail.getHsUnit());
			tempInfo.setScmCoc(billDetail.getBillMaster().getScmCoc());
			tempInfo.setHsSpec(billDetail.getHsSpec());
			tempInfo.setNotice(billDetail.getNote());
			tempInfo.setCustomNo(billDetail.getCustomNo());
		/*	if(isCustomNo){
				lsResult.add(tempInfo);
			}else{
				if(tempInfo.getCustomNo()!=null){
					
				}
			}*/
			lsResult.add(tempInfo);
		}
		return lsResult;
	}

	/**
	 * 根据名称查询单位
	 * 
	 * @param hsName
	 * @return
	 */
	private Unit findUnitByHsName(String hsName) {
		String name = "";
		if (hsName.indexOf("/") > 0) {
			name = hsName.substring(0, hsName.indexOf("/"));
		} else {
			name = hsName;
		}
		if (name.startsWith("[")) {
			name = name.substring(1);
		}
		List<FactoryAndFactualCustomsRalation> list = this.casTransferFactoryDao
				.findFactoryAndFactualCustomsRalationLikeHsName(name);
		// ("第一步查询结果："+list.size());
		// ("-----------" + list.size());
		for (FactoryAndFactualCustomsRalation f : list) {
			String fNameSpec = f.getStatCusNameRelationHsn().getCusName() == null ? ""
					: f.getStatCusNameRelationHsn().getCusName()
							+ ((f.getStatCusNameRelationHsn().getCusSpec() != null && !""
									.equals(f.getStatCusNameRelationHsn()
											.getCusSpec())) ? "/"
									+ f.getStatCusNameRelationHsn()
											.getCusSpec() : "");
			// ("fNamespec:"+fNameSpec);
			if (fNameSpec.equals(hsName)) {
				// ("查询结果："+f.getStatCusNameRelationHsn().getCusUnit().getName());
				return f.getStatCusNameRelationHsn().getCusUnit();
			}
		}
		// if (list.size() > 0 && list.get(0) != null) {
		// FactoryAndFactualCustomsRalation ralation =
		// (FactoryAndFactualCustomsRalation) list
		// .get(0);
		// if (ralation.getStatCusNameRelationHsn() != null) {
		// ("查询结果："+ralation.getStatCusNameRelationHsn().getCusUnit().getName());
		// return ralation.getStatCusNameRelationHsn().getCusUnit();
		// }
		// }
		return null;
	}

	/**
	 * 填充数据到重量中
	 * 
	 * @param list
	 * @param mapAmount
	 * @param mapWeight
	 */
	private void fillListToAmountWeightMap(TempObject commInfo,
			DateFormat dateFormat, List list, Map<String, Double> mapAmount,
			Map<String, Double> mapWeight, List lsDate) {
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] == null) {// 日期
				continue;
			}
			if (objs[3] == null) {// 报关名称
				continue;
			}
			if (objs[4] == null) {// 报关规格
				continue;
			}
			if (objs[3].equals(commInfo.getObject1())
					&& objs[4].equals(commInfo.getObject2())) {
				String dateStr = dateFormat.format((Date) objs[0]);
				double amount = (objs[1] == null ? 0.0 : Double
						.parseDouble(objs[1].toString()));
				double weight = (objs[2] == null ? 0.0 : Double
						.parseDouble(objs[2].toString()));
				mapAmount.put(dateStr, CommonUtils
						.getDoubleExceptNull(mapAmount.get(dateStr))
						+ amount);
				mapWeight.put(dateStr, CommonUtils
						.getDoubleExceptNull(mapWeight.get(dateStr))
						+ weight);
				// ("日期为=="+dateStr+" amount:"+amount+"
				// weight: " + weight);
				if (!lsDate.contains(dateStr)) {
					lsDate.add(dateStr);
				}
			}
		}
	}

	/**
	 * 转厂差额明细表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List findTransFactCompareInfo(boolean isImg, TempObject commInfo,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		if (scmCoc == null) {
			scmCoc = new ScmCoc();
			scmCoc.setId(commInfo.getObject3().toString());
			scmCoc.setName(commInfo.getObject().toString());
		}
		List lsResult = new ArrayList();
		// List lsRalation = this.casTransferFactoryDao
		// .findFactoryAndFactualCustomsRalationByHsName(commInfo.getObject1().toString());
		Map<String, Double> mapSendAmount = new HashMap<String, Double>();// 送货数
		Map<String, Double> mapSendWeight = new HashMap<String, Double>();// 送货重量
		Map<String, Double> mapBackAmount = new HashMap<String, Double>();// 退货数
		Map<String, Double> mapBackWeight = new HashMap<String, Double>();// 退货重量
		Map<String, Double> mapPlusWeight = new HashMap<String, Double>();// 期初单为正的
		Map<String, Double> mapPlusAmount = new HashMap<String, Double>();// 期初单为正的
		Map<String, Double> mapMinusWeight = new HashMap<String, Double>();// 期初单为负的
		Map<String, Double> mapMinusAmount = new HashMap<String, Double>();// 期初单为负的
		Map<String, Double> mapTransAmount = new HashMap<String, Double>();// 转厂数量
		Map<String, Double> mapTransWeight = new HashMap<String, Double>();// 转厂重量

		Map<String, CustomsEnvelopBill> mapEnvelop = new HashMap<String, CustomsEnvelopBill>();
		Map<String, FptAppHead> mapFptApp = new HashMap<String, FptAppHead>();
		Map<String, Double> mapEnvelopAmount = new HashMap<String, Double>();
		Map<String, Double> mapAppRemainAmount = new HashMap<String, Double>();
		Map<String, String> mapEnvelopBillNo = new HashMap<String, String>();
		Map<String, String> mapFptAppNo = new HashMap<String, String>();
		List<String> lsDate = new ArrayList<String>();
		List<String> lsEnvelopDate = new ArrayList<String>();
		List<String> lsFptDate = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List sendList = this.casTransferFactoryDao
				.findBillDetailSendAmountWeight(isImg, scmCoc, beginDate,
						endDate);// 查找结转进口/结转出口/
		this.fillListToAmountWeightMap(commInfo, dateFormat, sendList,
				mapSendAmount, mapSendWeight, lsDate);
		List backList = this.casTransferFactoryDao
				.findBillDetailBackAmountWeight(isImg, scmCoc, beginDate,
						endDate);// 查找结转料件退货单/结转成品退货单
		this.fillListToAmountWeightMap(commInfo, dateFormat, backList,
				mapBackAmount, mapBackWeight, lsDate);
		List PlusList = this.casTransferFactoryDao
				.findBillDetailPlusAmountWeight(isImg, scmCoc, beginDate,
						endDate);// 查找已收货未结转期初单/已交货未结转期初单
		this.fillListToAmountWeightMap(commInfo, dateFormat, PlusList,
				mapPlusAmount, mapPlusWeight, lsDate);// 期初单为正的
		List MinusList = this.casTransferFactoryDao
				.findBillDetailMinusAmountWeight(isImg, scmCoc, beginDate,
						endDate);// 查找已结转未收货期初单/已结转未交货期初单
		this.fillListToAmountWeightMap(commInfo, dateFormat, MinusList,
				mapMinusAmount, mapMinusWeight, lsDate);// 期初单为负的
		// 查找报关单中的数量与重量
		//电子化手册
		List bcsTransList = this.casTransferFactoryDao
				.findCustomsDeclarationAmountWeight(isImg, ProjectType.BCS,
						scmCoc, beginDate, endDate);
		this.fillListToAmountWeightMap(commInfo, dateFormat, bcsTransList,
				mapTransAmount, mapTransWeight, lsDate);
		//电子帐册
		List bcusTransList = this.casTransferFactoryDao
				.findCustomsDeclarationAmountWeight(isImg, ProjectType.BCUS,
						scmCoc, beginDate, endDate);
		this.fillListToAmountWeightMap(commInfo, dateFormat, bcusTransList,
				mapTransAmount, mapTransWeight, lsDate);
		//电子手册
		List dzscTransList = this.casTransferFactoryDao
				.findCustomsDeclarationAmountWeight(isImg, ProjectType.DZSC,
						scmCoc, beginDate, endDate);
		this.fillListToAmountWeightMap(commInfo, dateFormat, dzscTransList,
				mapTransAmount, mapTransWeight, lsDate);
		// ----------------------------------------------------------------------------
		// 查找关封
		List envelopList = this.casTransferFactoryDao.findEnvelopAmountWeight(
				isImg, scmCoc, beginDate, endDate);

		// System.out.println("===envelopList="+envelopList.size()+"
		// =beginDate="+beginDate+" endDate="+endDate);
		// List list2 =
		// this.casTransferFactoryDao.findEnvelopAmountWeight(isImg,
		// scmCoc, beginDate, endDate);

		for (int j = 0; j < envelopList.size(); j++) {
			Object[] objs = (Object[]) envelopList.get(j);
			if (objs[0] == null) {// 生效日期
				continue;
			}
			if (objs[3] == null) {// 名称
				continue;
			}
			if (objs[4] == null) {// 规格
				continue;
			}
			// System.out.println("===getObject1()="+commInfo.getObject1()+"
			// =objs[3]="+objs[3]+" objs[4]="+objs[4]+"
			// getObject2()="+commInfo.getObject2());
			if (objs[3].equals(commInfo.getObject1())
					&& objs[4].equals(commInfo.getObject2())) {
				String dateStr = dateFormat.format((Date) objs[0]);// 关封里面的生效日期
				String envelopNo = (objs[1] == null ? "" : objs[1].toString()
						.trim());
				double amount = (objs[2] == null ? 0.0 : Double
						.parseDouble(objs[2].toString()));
				mapEnvelopAmount.put(envelopNo, CommonUtils
						.getDoubleExceptNull(mapEnvelopAmount.get(envelopNo))
						+ amount);
				// System.out.println("===dataStr="+dateStr+"==envelopNo="+envelopNo);
				// 这里有问题，如果是同一天办两个关封号就不可用下面的，会把之前的关封号给替代 lxr2012-04-21
				if (mapEnvelopBillNo.get(dateStr) == null
						|| mapEnvelopBillNo.get(dateStr).equals("")) {
					mapEnvelopBillNo.put(dateStr, envelopNo);
				}
				if (!lsDate.contains(dateStr)) {// 单据日期加上关封里面的生效日期一起
					lsDate.add(dateStr);
				}
				if (!lsEnvelopDate.contains(dateStr)) {// 关封里面的生效日期
					lsEnvelopDate.add(dateStr);
				}
			}
		}

		// -----------深加工结转----------
		List fptlist = this.casTransferFactoryDao.findFptlopAmountWeight(isImg,
				scmCoc, beginDate, endDate);
		for (int j = 0; j < fptlist.size(); j++) {
			Object[] objs = (Object[]) fptlist.get(j);
			if (objs[0] == null) {
				continue;
			}
			if (objs[3] == null) {
				continue;
			}
			if (objs[4] == null) {
				continue;
			}
			if (objs[3].equals(commInfo.getObject1())
					&& objs[4].equals(commInfo.getObject2())) {
				String dateStr = dateFormat.format((Date) objs[0]);
				String envelopNo = (objs[1] == null ? "" : objs[1].toString()
						.trim());
				double amount = (objs[2] == null ? 0.0 : Double
						.parseDouble(objs[2].toString()));
				mapAppRemainAmount.put(envelopNo, CommonUtils
						.getDoubleExceptNull(mapAppRemainAmount.get(envelopNo))
						+ amount);
				if (mapFptAppNo.get(dateStr) == null
						|| mapFptAppNo.get(dateStr).equals("")) {
					mapFptAppNo.put(dateStr, envelopNo);
				}
				if (!lsDate.contains(dateStr)) {
					lsDate.add(dateStr);
				}
				if (!lsFptDate.contains(dateStr)) {
					lsFptDate.add(dateStr);
				}
			}
		}
		// ----------------------------------------------------------------------------
		List envelopBillList = this.casTransferFactoryDao
				.findCustomsEnvelopBill(isImg, scmCoc, beginDate, endDate);
		// ("查询出来的关封管理明细的长度是：" + list.size());
		for (int j = 0; j < envelopBillList.size(); j++) {
			Object[] objs = (Object[]) envelopBillList.get(j);
			if (objs[1] == null) {// 名称
				continue;
			}
			if (objs[2] == null) {// 规格
				continue;
			}
			if (objs[1].equals(commInfo.getObject1())
					&& objs[2].equals(commInfo.getObject2())) {
				CustomsEnvelopBill envelopBill = (CustomsEnvelopBill) objs[0];
				mapEnvelop.put(envelopBill.getCustomsEnvelopBillNo(),
						envelopBill);
			}
		}
		List FapAppItemList = this.casTransferFactoryDao.findFptAppItem(isImg,
				scmCoc, beginDate, endDate);
		for (int j = 0; j < FapAppItemList.size(); j++) {
			Object[] objs = (Object[]) FapAppItemList.get(j);
			if (objs[1] == null) {
				continue;
			}
			if (objs[2] == null) {
				continue;
			}
			if (objs[1].equals(commInfo.getObject1())
					&& objs[2].equals(commInfo.getObject2())) {
				FptAppHead aptApp = (FptAppHead) objs[0];
				mapFptApp.put(aptApp.getAppNo(), aptApp);
			}
		}
		Collections.sort(lsDate);
		Collections.sort(lsEnvelopDate);
		Collections.sort(lsFptDate);
		// ----------------------------------------------------------------------------
		Unit unit = this.findUnitByHsName(commInfo.getObject1().toString());
		System.out.println("===lsDate==" + lsDate.size());
		for (int i = 0; i < lsDate.size(); i++) {
			double remainAmount = 0;
			double remainWeight = 0;
			String date = lsDate.get(i).toString();
			TempTransFactCompareInfo temp = new TempTransFactCompareInfo();
			temp.setScmCocName(commInfo.getObject().toString());
			temp.setCommName(commInfo.getObject1().toString());
			temp.setPtSpec(commInfo.getObject2().toString());
			temp.setHsUnit(unit == null ? "" : unit.getName());
			temp.setDate(date);
			temp.setSendAmount(CommonUtils.getDoubleExceptNull(mapSendAmount
					.get(date))
					- CommonUtils.getDoubleExceptNull(mapBackAmount.get(date)));
			temp.setSendWeight(CommonUtils.getDoubleExceptNull(mapSendWeight
					.get(date))
					- CommonUtils.getDoubleExceptNull(mapBackWeight.get(date)));
			temp.setTransAmount(mapTransAmount.get(date));
			temp.setTransWeight(mapTransWeight.get(date));
			// System.out.println("==getSendAmount=="+CommonUtils.getDoubleExceptNull(temp.getSendAmount()));
			// System.out.println("==getTransAmount=="+CommonUtils.getDoubleExceptNull(temp.getTransAmount()));
			// System.out.println("==mapPlusAmount=="+CommonUtils.getDoubleExceptNull(mapPlusAmount.get(date)));
			// System.out.println("==mapMinusAmount=="+CommonUtils.getDoubleExceptNull(mapMinusAmount.get(date)));
			// System.out.println("==getSendWeight=="+CommonUtils.getDoubleExceptNull(temp.getSendWeight()));
			//结余= 送货数-退货数-转厂数+已收货未结转期初单/已交货未结转期初单-已结转未收货期初单/已结转未交货期初单
			remainAmount = remainAmount
					+ CommonUtils.getDoubleExceptNull(temp.getSendAmount())
					- CommonUtils.getDoubleExceptNull(temp.getTransAmount())
					+ CommonUtils.getDoubleExceptNull(mapPlusAmount.get(date))
					- CommonUtils.getDoubleExceptNull(mapMinusAmount.get(date));
			// System.out.println("========");
			// System.out.println("==remainAmount=="+remainAmount);
			remainWeight = remainWeight
					+ CommonUtils.getDoubleExceptNull(temp.getSendWeight())
					- CommonUtils.getDoubleExceptNull(temp.getTransWeight())
					+ CommonUtils.getDoubleExceptNull(mapPlusWeight.get(date))
					- CommonUtils.getDoubleExceptNull(mapMinusWeight.get(date));
			temp.setRemainAmount(remainAmount);
			temp.setRemainWeight(remainWeight);
			
			// ----------------
			String envelopNo = this.getRecentEnvelopNo(date, lsEnvelopDate,
					CommonUtils.getDoubleExceptNull(temp.getSendAmount()),
					mapEnvelopAmount, mapEnvelopBillNo, mapEnvelop);

			if (envelopNo != null && !envelopNo.trim().equals("")) {
				Double transferQuantity = this.casTransferFactoryDao
						.findEnvelopAmountWeights(isImg, scmCoc, beginDate,
								endDate, envelopNo) == null ? 0.0
						: this.casTransferFactoryDao.findEnvelopAmountWeights(
								isImg, scmCoc, beginDate, endDate, envelopNo);
				// 关封余量=关封量-报关单中的数量
				double envelopRemainAmount = CommonUtils
						.getDoubleExceptNull(mapEnvelopAmount.get(envelopNo))
						- transferQuantity;
				// 关封余量=关封量-报关单中的数量
				// double envelopRemainAmount = CommonUtils
				// .getDoubleExceptNull(mapEnvelopAmount
				// .get(envelopNo))
				// - temp.getTransAmount();
				System.out.println("--------->关封号="
						+ envelopNo
						+ "  关封余量="
						+ envelopRemainAmount
						+ " 关封数="
						+ CommonUtils.getDoubleExceptNull(mapEnvelopAmount
								.get(envelopNo)) + " 转厂数="
						+ transferQuantity);
				temp
						.setEnvelopNo(envelopNo == null || "".equals(envelopNo) ? ""
								: envelopNo);
				temp.setEnvelopAmount(envelopRemainAmount);
				CustomsEnvelopBill envelopBill = mapEnvelop.get(envelopNo);
				if (envelopBill != null) {
					temp
							.setTerm((envelopBill.getBeginAvailability() == null ? ""
									: dateFormat.format(envelopBill
											.getBeginAvailability()))
									+ "<->"
									+ (envelopBill.getEndAvailability() == null ? ""
											: dateFormat.format(envelopBill
													.getEndAvailability())));
					temp.setIsEndCase(envelopBill.getIsEndCase());
				}
				mapEnvelopAmount.put(envelopNo, envelopRemainAmount);
			}

			// ---------------下面查询深加工结转平台的模块的关封------------
			// ("查询关封号");
			String fptAppNo = this.getRecentFptApp(date, lsFptDate, CommonUtils
					.getDoubleExceptNull(temp.getSendAmount()),
					mapEnvelopAmount, mapFptAppNo, mapFptApp);
			if (fptAppNo != null && !fptAppNo.trim().equals("")) {
				double fprAppRemainAmount = CommonUtils
						.getDoubleExceptNull(mapAppRemainAmount.get(fptAppNo))
						- CommonUtils.getDoubleExceptNull(temp.getSendAmount());
				// 关封余量=关封量-送货/收货数量
				if (envelopNo == null || envelopNo.trim().equals("")) {
					FptAppHead envelopBill = mapFptApp.get(fptAppNo);
					if (envelopBill != null) {
						Date dt = null;
						if (envelopBill.getInOutFlag() == null
								|| envelopBill.getInOutFlag().equals("1")) {
							dt = envelopBill.getInDate();
						} else {
							dt = envelopBill.getOutDate();
						}

						temp.setTerm(dt == null ? "" : dateFormat.format(dt));
						if (dt != null) {
							if (dateFormat.format(dt).compareTo(temp.getDate()) <= 0) {
								temp.setIsEndCase(true);
							} else {
								temp.setIsEndCase(false);
							}
						} else {
							temp.setIsEndCase(false);
						}
					}
					mapAppRemainAmount.put(fptAppNo, fprAppRemainAmount);
				} else {
					// temp.setEnvelopNo(temp.getEnvelopNo() == null ? "" : temp
					// .getEnvelopNo()
					// + "," + fptAppNo);
					// temp
					// .setEnvelopAmount(temp.getEnvelopAmount() == null ? 0.0
					// : temp.getEnvelopAmount()
					// + (mapAppRemainAmount.get(fptAppNo) == null ? 0.0
					// : mapAppRemainAmount
					// .get(fptAppNo)));
				}

				mapAppRemainAmount.put(fptAppNo, fprAppRemainAmount);
			}
			lsResult.add(temp);
		}
		return lsResult;
	}

	/**
	 * 得到关封号码
	 * 
	 * @param date
	 * @param lsEnvelopDate
	 * @param sendAmount
	 * @param mapEnvelopRemainAmount
	 * @param mapEnvelopBillNo
	 * @param mapEnvelop
	 * @return
	 */
	private String getRecentEnvelopNo(String date, List<String> lsEnvelopDate,
			Double sendAmount, Map<String, Double> mapEnvelopRemainAmount,
			Map<String, String> mapEnvelopBillNo,
			Map<String, CustomsEnvelopBill> mapEnvelop) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// if (preEnvelopNo == null || "".equals(preEnvelopNo.trim())) {
		// String recenDate = this.getRecentEnvelopDate(date, lsEnvelopDate);
		// if (recenDate == null) {
		// return null;
		// } else {
		// return mapEnvelopBillNo.get(recenDate);
		// }
		// } else {
		for (int i = 0; i < lsEnvelopDate.size(); i++) {
			String envelopDate = lsEnvelopDate.get(i).toString();
			System.out.println("lsEnvelopDate:" + lsEnvelopDate.get(i)
					+ " date=" + date + "  =" + date.compareTo(envelopDate));
			if (date.compareTo(envelopDate) < 0) {
				continue;
			} else {
				String envelopNo = mapEnvelopBillNo.get(date);
				CustomsEnvelopBill envelopBill = mapEnvelop.get(envelopNo);
				if (envelopBill == null) {
					continue;
				}
				if ((envelopBill.getBeginAvailability() == null ? true : date
						.compareTo(dateFormat.format(envelopBill
								.getBeginAvailability())) >= 0)
						&& (envelopBill.getEndAvailability() == null ? true
								: date.compareTo(dateFormat.format(envelopBill
										.getEndAvailability())) <= 0)) {
					double remainAmount = CommonUtils
							.getDoubleExceptNull(mapEnvelopRemainAmount
									.get(envelopBill.getCustomsEnvelopBillNo()));
					System.out.println("-----------remainAmount:"
							+ remainAmount);
					System.out.println("-----------sendAmount:" + sendAmount);
					if (remainAmount >= CommonUtils
							.getDoubleExceptNull(sendAmount)) {
						return envelopNo;
					} else {
						System.out.println("关封余量<送货数");
						continue;
					}
				} else {
					System.out
							.println("---------------------4444444444444444444");
					continue;
				}
			}
		}
		// }
		return null;
	}

	/**
	 * 得到日期最近的关封申请单
	 * 
	 * @param date
	 * @param lsEnvelopDate
	 * @param sendAmount
	 * @param mapEnvelopRemainAmount
	 * @param mapEnvelopBillNo
	 * @param mapEnvelop
	 * @return
	 */
	private String getRecentFptApp(String date, List<String> lsEnvelopDate,
			Double sendAmount, Map<String, Double> mapEnvelopRemainAmount,
			Map<String, String> mapEnvelopBillNo,
			Map<String, FptAppHead> mapEnvelop) {
		// if (preEnvelopNo == null || "".equals(preEnvelopNo.trim())) {
		// String recenDate = this.getRecentEnvelopDate(date, lsEnvelopDate);
		// if (recenDate == null) {
		// return null;
		// } else {
		// return mapEnvelopBillNo.get(recenDate);
		// }
		// } else {
		for (int i = 0; i < lsEnvelopDate.size(); i++) {
			String envelopDate = lsEnvelopDate.get(i).toString();
			if (date.compareTo(envelopDate) < 0) {
				return null;
			} else {
				String envelopNo = mapEnvelopBillNo.get(date);
				FptAppHead envelopBill = mapEnvelop.get(envelopNo);
				if (envelopBill == null) {
					continue;
				}
				return envelopNo;
				// Date dt = null;
				// if (envelopBill.getInOutFlag() == null
				// || envelopBill.getInOutFlag().equals("1")) {
				// dt = envelopBill.getInDate();
				// } else {
				// dt = envelopBill.getOutDate();
				// }
				// if ((dt == null ? true
				// : date.compareTo(dateFormat.format(dt)) >= 0)
				// && (dt == null ? true : date.compareTo(dateFormat
				// .format(dt)) <= 0)) {
				// double remainAmount = CommonUtils
				// .getDoubleExceptNull(mapEnvelopRemainAmount.get(dt));
				// // ("-----------remainAmount:"
				// + remainAmount);
				// // ("-----------sendAmount:" +
				// sendAmount);
				// if (remainAmount >= CommonUtils
				// .getDoubleExceptNull(sendAmount)) {
				// return envelopNo;
				// } else {
				// continue;
				// }
				// } else {
				// continue;
				// }
			}
		}
		// }
		return null;
	}

	/**
	 * 转厂差额明细表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List findTransFactCompareInfoOnDay(boolean isImg,
			TempObject commInfos, ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<TempTransFactCompareInfo> lsResult = new ArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginDate == null || endDate == null) {
			return lsResult;
		}
		String beginDateStr = dateFormat.format(beginDate);
		List<TempTransFactCompareInfo> list = this.findTransFactCompareInfo(
				isImg, commInfos, scmCoc, beginDate, endDate);
		int index = 0;
		String nameSpec = "";
		for (int i = 0; i < list.size(); i++) {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(i);
			if (i == 0) {
				nameSpec = temp.getCommName();
			}
			if (beginDateStr.compareTo(temp.getDate()) == 0) {
				index = i;
				break;
			} else if (beginDateStr.compareTo(temp.getDate()) < 0) {
				index = i - 1;
				break;
			}
		}
		if (index <= 0) {
			lsResult.addAll(list);
		} else {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(index);
			temp.setDate("期初");
			temp.setSendAmount(null);
			temp.setSendWeight(null);
			temp.setTransAmount(null);
			temp.setTransWeight(null);
			lsResult.add(temp);
			for (int i = index + 1; i < list.size(); i++) {
				lsResult.add(list.get(i));
			}
		}
		// 统计结余
		int i = 0;
		Double amount = 0.0;
		Double weight = 0.0;
		for (TempTransFactCompareInfo item : lsResult) {
			if (i == 0 || !item.getCommName().equals(nameSpec)) {
				amount = item.getRemainAmount();
				weight = item.getRemainWeight();
				nameSpec = item.getCommName();
				i++;
				continue;
			}
			amount = amount + item.getRemainAmount();
			item.setRemainAmount(amount);
			weight = weight + item.getRemainWeight();
			item.setRemainWeight(weight);
			i++;
		}

		return lsResult;
	}

	/**
	 * 转厂差额明细表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findTransFactCompareInfoOnDay(boolean isImg, String commName,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		List<TempObject> commInfos = this.findTransFactRecvSendCommName(isImg,
				scmCoc, commName, beginDate, endDate);
		TempObject tmp = null;
		for (int i = 0; i < commInfos.size(); i++) {
			tmp = commInfos.get(i);
			if (tmp.getObject1() != null && !"".equals(tmp.getObject1())) {
				List list = this.findTransFactCompareInfoOnDay(isImg, tmp,
						scmCoc, beginDate, endDate);
				lsResult.addAll(list);
			}
		}
		return lsResult;
	}

	/**
	 * 根据一个长的日期来获取开始日期和结束日期
	 * 
	 * @param term
	 * @return
	 */
	private Date[] getBeginEndDateByTerm(String term) {
		Date[] dates = new Date[2];
		int year = Integer.parseInt(term.substring(0, 4));
		int month = Integer.parseInt(term.substring(5)) - 1;
		GregorianCalendar calendar = new GregorianCalendar(year, month, 1);
		dates[0] = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		dates[1] = calendar.getTime();
		return dates;
	}

	/**
	 * 结转对帐表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	private List findTransFactCompareInfoOnMonth2(boolean isImg,
			String commName, ScmCoc scmCoc, Date beginMonth, Date endMonth) {
		List lsResult = new ArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Date beginDate = this.getBeginEndDateByTerm(beginMonth)[0];
		// Date endDate = this.getBeginEndDateByTerm(endMonth)[1];
		String linkMan = "", tel = "", fax = "";
		linkMan = scmCoc.getLinkMan() == null ? "" : scmCoc.getLinkMan();
		tel = scmCoc.getLinkTel() == null ? "" : scmCoc.getLinkTel();
		fax = scmCoc.getFax() == null ? "" : scmCoc.getFax();
		// System.out
		// .println("linkMan+tel+fax=" + linkMan + "/" + tel + "/" + fax);
		if (beginMonth == null || endMonth == null) {
			return lsResult;
		}
		// (beginDate);
		// (endDate);
		String beginDateStr = dateFormat.format(beginMonth);
		String firstMonth = beginDateStr.substring(5, 10);
		TempObject commInfo = new TempObject();
		commInfo.setObject1(commName);
		List list = this.findTransFactCompareInfo(isImg, commInfo, scmCoc,
				beginMonth, endMonth);
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(i);
			if (firstMonth.equals("01-01")) {
				if (beginDateStr.compareTo(temp.getDate()) == 0)
					break;
			}
			if (beginDateStr.compareTo(temp.getDate()) == 0) {
				index = i - 1;
				break;
			} else if (beginDateStr.compareTo(temp.getDate()) < 0) {
				index = i - 1;
				break;
			}
		}
		if (index > 0 || (index == 0 && firstMonth.equals("01-01"))
				&& list.size() > 0) {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(0);
			temp.setDate("期初");
			temp.setSendAmount(null);
			temp.setSendWeight(null);
			temp.setTransAmount(null);
			temp.setTransWeight(null);
			lsResult.add(temp);
		}
		String month = "";
		TempTransFactCompareInfo info = null;// new
		// TempTransFactCompareInfo();
		for (int i = index + 1; i < list.size(); i++) {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(i);
			if (!month.equals(temp.getDate().substring(0, 7))) {
				if (info != null && i > 0) {
					TempTransFactCompareInfo preTemp = (TempTransFactCompareInfo) list
							.get(i - 1);
					info.setEnvelopNo(preTemp.getEnvelopNo());
					info.setEnvelopAmount(preTemp.getEnvelopAmount());
					info.setEnvelopWeight(preTemp.getEnvelopWeight());
					info.setTerm(preTemp.getTerm());
					info.setIsEndCase(preTemp.getIsEndCase());
					lsResult.add(info);
				}
				info = new TempTransFactCompareInfo();
				info.setCommName(temp.getCommName());
				info.setHsUnit(temp.getHsUnit());
				info.setDate(temp.getDate().substring(0, 7));
				month = info.getDate();
			}
			info.setSendAmount(CommonUtils.getDoubleExceptNull(info
					.getSendAmount())
					+ CommonUtils.getDoubleExceptNull(temp.getSendAmount()));
			info.setSendWeight(CommonUtils.getDoubleExceptNull(info
					.getSendWeight())
					+ CommonUtils.getDoubleExceptNull(temp.getSendWeight()));
			info.setTransAmount(CommonUtils.getDoubleExceptNull(info
					.getTransAmount())
					+ CommonUtils.getDoubleExceptNull(temp.getTransAmount()));
			info.setTransWeight(CommonUtils.getDoubleExceptNull(info
					.getTransWeight())
					+ CommonUtils.getDoubleExceptNull(temp.getTransWeight()));
			info.setRemainAmount(CommonUtils.getDoubleExceptNull(info
					.getRemainAmount())
					+ CommonUtils.getDoubleExceptNull(temp.getRemainAmount()));
			info.setRemainWeight(CommonUtils.getDoubleExceptNull(info
					.getRemainWeight())
					+ CommonUtils.getDoubleExceptNull(temp.getRemainWeight()));
			info.setFax(fax);
			info.setTel(tel);

			info.setLinkMan(linkMan);
			if (i == list.size() - 1) {
				info.setEnvelopNo(temp.getEnvelopNo());
				System.out
						.println("temp.getEnvelopNo()=" + temp.getEnvelopNo());
				info.setEnvelopAmount(temp.getEnvelopAmount());
				info.setEnvelopWeight(temp.getEnvelopWeight());
				info.setTerm(temp.getTerm());
				info.setIsEndCase(temp.getIsEndCase());
				lsResult.add(info);
			}
			System.out.println("==getSendWeight11==" + info.getSendWeight());
		}
		if (lsResult != null && lsResult.size() != 0) {
			String nameSpec = "";
			TempTransFactCompareInfo ttfi = (TempTransFactCompareInfo) lsResult
					.get(0);
			nameSpec = ttfi.getCommName();
			double remainAmount = CommonUtils.getDoubleExceptNull(ttfi
					.getRemainAmount());
			double remainWeight = CommonUtils.getDoubleExceptNull(ttfi
					.getRemainWeight());
			for (int i = 0; i < lsResult.size(); i++) {
				TempTransFactCompareInfo tmp = (TempTransFactCompareInfo) lsResult
						.get(i);
				if (i == 0 || tmp.getDate().equals("期初")
						|| !tmp.getCommName().equals(nameSpec)) {
					remainAmount = CommonUtils.getDoubleExceptNull(tmp
							.getRemainAmount());
					remainWeight = CommonUtils.getDoubleExceptNull(tmp
							.getRemainWeight());
					nameSpec = tmp.getCommName();
					continue;
				}
				remainAmount = remainAmount
						+ CommonUtils.getDoubleExceptNull(tmp.getSendAmount())
						- CommonUtils.getDoubleExceptNull(tmp.getTransAmount());
				remainWeight = remainWeight
						+ CommonUtils.getDoubleExceptNull(tmp.getSendWeight())
						- CommonUtils.getDoubleExceptNull(tmp.getTransWeight());
				tmp.setRemainAmount(remainAmount);
				tmp.setRemainWeight(remainWeight);
			}
		}
		return lsResult;
	}

	/**
	 * 结转对帐表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	private List calcTransFactCompareInfoOnMonth(boolean isImg,
			TempObject commInfo, ScmCoc scmCoc, String beginMonth,
			String endMonth) {
		List lsResult = new ArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = this.getBeginEndDateByTerm(beginMonth)[0];
		Date endDate = this.getBeginEndDateByTerm(endMonth)[1];
		String linkMan = "", tel = "", fax = "", scmCocName="";
		if (scmCoc == null) {
			scmCoc = new ScmCoc();
			scmCoc.setId(commInfo.getObject3().toString());
			scmCoc.setName(commInfo.getObject().toString());
			scmCoc.setLinkMan(commInfo.getObject4()==null?"":commInfo.getObject4().toString());
			scmCoc.setLinkTel(commInfo.getObject5()==null?"":commInfo.getObject5().toString());
			scmCoc.setFax(commInfo.getObject6()==null?"":commInfo.getObject6().toString());
			linkMan = scmCoc.getLinkMan() == null ? "" : scmCoc.getLinkMan();
			tel = scmCoc.getLinkTel() == null ? "" : scmCoc.getLinkTel();
			fax = scmCoc.getFax() == null ? "" : scmCoc.getFax();
			scmCocName = scmCoc.getName()==null?"":scmCoc.getName();
		}else{
			linkMan = scmCoc.getLinkMan() == null ? "" : scmCoc.getLinkMan();
			tel = scmCoc.getLinkTel() == null ? "" : scmCoc.getLinkTel();
			fax = scmCoc.getFax() == null ? "" : scmCoc.getFax();
			scmCocName = scmCoc.getName()==null?"":scmCoc.getName();
		}
		if (beginDate == null || endDate == null) {
			return lsResult;
		}
		String beginDateStr = dateFormat.format(beginDate);
		String firstMonth = beginDateStr.substring(5, 10);

		// System.out.println("beginDate=" + beginDate.toLocaleString() + "/" +
		// endDate.toLocaleString());
		List list = this.findTransFactCompareInfo(isImg, commInfo, scmCoc,
				beginDate, endDate);

		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(i);
			if (firstMonth.equals("01-01")) {
				if (beginDateStr.compareTo(temp.getDate()) == 0)
					break;
			}
			if (beginDateStr.compareTo(temp.getDate()) == 0) {
				index = i - 1;
				break;
			} else if (beginDateStr.compareTo(temp.getDate()) < 0) {
				index = i - 1;
				break;
			}
		}
		if (index > 0 || (index == 0 && firstMonth.equals("01-01"))
				&& list.size() > 0) {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(0);
			temp.setDate("期初");
			temp.setSendAmount(null);
			temp.setSendWeight(null);
			temp.setTransAmount(null);
			temp.setTransWeight(null);
			lsResult.add(temp);
		}

		// ------------------------------------
		// key == month ,7月
		Map<String, TempTransFactCompareInfo> monthMap = new HashMap<String, TempTransFactCompareInfo>();

		// ------------------------------------

		// TempTransFactCompareInfo();
		for (int i = index + 1; i < list.size(); i++) {
			TempTransFactCompareInfo temp = (TempTransFactCompareInfo) list
					.get(i);
			String keyMonth = temp.getDate().substring(0, 7);
			TempTransFactCompareInfo existTemp = monthMap.get(keyMonth);
			if (existTemp == null) {
				temp.setDate(keyMonth);
				temp.setFax(fax);
				temp.setTel(tel);
				temp.setLinkMan(linkMan);
				temp.setScmCocName(scmCocName);
				monthMap.put(keyMonth, temp);
				lsResult.add(temp);
			} else {
				if (existTemp.getEnvelopNo() == null
						|| "".equals(existTemp.getEnvelopNo().trim())) {
					existTemp.setFax(fax);
					existTemp.setTel(tel);
					existTemp.setLinkMan(linkMan);
					existTemp.setScmCocName(scmCocName);
					existTemp.setEnvelopNo("");
					existTemp.setEnvelopAmount(0.0);
					existTemp.setIsEndCase(existTemp.getIsEndCase());
				}

				String envelopNo = existTemp.getEnvelopNo();// 关封号
				String newEnvelopNo = "";
				if (envelopNo != null && !"".equals(envelopNo)) {
					newEnvelopNo = getNotExistEnvelopNo(envelopNo, temp
							.getEnvelopNo());
				} else {
					newEnvelopNo = temp.getEnvelopNo();
				}
				existTemp.setEnvelopNo(newEnvelopNo);

				existTemp
						.setSendAmount(CommonUtils
								.getDoubleExceptNull(existTemp.getSendAmount())
								+ CommonUtils.getDoubleExceptNull(temp
										.getSendAmount()));
				existTemp
						.setSendWeight(CommonUtils
								.getDoubleExceptNull(existTemp.getSendWeight())
								+ CommonUtils.getDoubleExceptNull(temp
										.getSendWeight()));
				existTemp.setTransAmount(CommonUtils
						.getDoubleExceptNull(existTemp.getTransAmount())
						+ CommonUtils
								.getDoubleExceptNull(temp.getTransAmount()));
				existTemp.setTransWeight(CommonUtils
						.getDoubleExceptNull(existTemp.getTransWeight())
						+ CommonUtils
								.getDoubleExceptNull(temp.getTransWeight()));
				existTemp.setRemainAmount(CommonUtils
						.getDoubleExceptNull(existTemp.getRemainAmount())
						+ CommonUtils.getDoubleExceptNull(temp
								.getRemainAmount()));
				existTemp.setRemainWeight(CommonUtils
						.getDoubleExceptNull(existTemp.getRemainWeight())
						+ CommonUtils.getDoubleExceptNull(temp
								.getRemainWeight()));
				existTemp.setEnvelopAmount(CommonUtils
						.getDoubleExceptNull(existTemp.getEnvelopAmount())
						+ CommonUtils.getDoubleExceptNull(temp
								.getEnvelopAmount()));

				existTemp.setFax(fax);
				existTemp.setTel(tel);
				existTemp.setLinkMan(linkMan);
				existTemp.setScmCocName(scmCocName);
			}
		}
		if (lsResult != null && lsResult.size() != 0) {
			String nameSpec = "";
			TempTransFactCompareInfo ttfi = (TempTransFactCompareInfo) lsResult
					.get(0);
			nameSpec = ttfi.getCommName();
			double remainAmount = CommonUtils.getDoubleExceptNull(ttfi
					.getRemainAmount());
			double remainWeight = CommonUtils.getDoubleExceptNull(ttfi
					.getRemainWeight());
			for (int i = 0; i < lsResult.size(); i++) {
				TempTransFactCompareInfo tmp = (TempTransFactCompareInfo) lsResult
						.get(i);
				if (i == 0 || tmp.getDate().equals("期初")
						|| !tmp.getCommName().equals(nameSpec)) {
					remainAmount = CommonUtils.getDoubleExceptNull(tmp
							.getRemainAmount());
					remainWeight = CommonUtils.getDoubleExceptNull(tmp
							.getRemainWeight());
					nameSpec = tmp.getCommName();	
					continue;
				}
				remainAmount = remainAmount
						+ CommonUtils.getDoubleExceptNull(tmp.getSendAmount())
						- CommonUtils.getDoubleExceptNull(tmp.getTransAmount());
				remainWeight = remainWeight
						+ CommonUtils.getDoubleExceptNull(tmp.getSendWeight())
						- CommonUtils.getDoubleExceptNull(tmp.getTransWeight());
				tmp.setRemainAmount(remainAmount);
				tmp.setRemainWeight(remainWeight);
			}
		}
		return lsResult;
	}

	/**
	 * 判断是否存在关封号
	 * 
	 * @param allbillNo
	 * @param newbillNo
	 * @return
	 */
	public String getNotExistEnvelopNo(String allbillNo, String newbillNo) {
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
	 * 结转对帐表
	 * 
	 * @param isImg
	 * @param commName
	 * @param scmCoc
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public List findTransFactCompareInfoOnMonth(boolean isImg,
			String commName,String commSpec, ScmCoc scmCoc, String beginMonth, String endMonth,List groupCondition) {
		List lsResult = new ArrayList();
		List<TempObject> commInfos = this.findTransFactRecvSendCommName2(isImg,
				scmCoc,commName, beginMonth, endMonth);
		TempObject tmp = null;
		if (commInfos != null) {
			for (int i = 0; i < commInfos.size(); i++) {
				tmp = commInfos.get(i);
				if (tmp.getObject1() != null && !"".equals(tmp.getObject1())) {
					List list = calcTransFactCompareInfoOnMonth(isImg,
							tmp, scmCoc, beginMonth, endMonth);
					lsResult.addAll(list);
				}
			}
		}
		
		//过滤
		if(commSpec!=null&&!"".equals(commSpec)){
			List tempInfoList = new ArrayList();
			for (int j = 0; j < lsResult.size(); j++) {
				TempTransFactCompareInfo tempInfo = (TempTransFactCompareInfo)lsResult.get(j);
				if(tempInfo!=null&&tempInfo.getPtSpec().equals(commSpec)){
					tempInfoList.add(tempInfo);
				}
			}
			lsResult.clear();
			lsResult = tempInfoList;
		}
		//分组条件
		String groupConditionStr = "";
		for (int i = 0; i < groupCondition.size(); i++) {
			String conditionStr = (String) groupCondition.get(i);
			if("scmCocName".equals(conditionStr)){
				groupConditionStr += "scmCocName";
			}else if("hsName".equals(conditionStr)){
				groupConditionStr += "hsName";
			}else if("hsSpec".equals(conditionStr)){
				groupConditionStr += "hsSpec";
			}
		}
		
		//分组
		if(!"".equals(groupConditionStr)&&groupConditionStr!=null){
			//key = 分组条件 + 日期
			String key = "";
			Map map = new HashMap<String,TempTransFactCompareInfo>(); 
			for (int i = 0; i < lsResult.size(); i++) {
				TempTransFactCompareInfo tempInfo = (TempTransFactCompareInfo) lsResult.get(i);
				if(groupConditionStr.contains("scmCocName")&&groupConditionStr.contains("hsName")&&groupConditionStr.contains("hsSpec")){
					key = tempInfo.getScmCocName()+ tempInfo.getCommName()+ tempInfo.getPtSpec()+tempInfo.getDate();
				}else if(groupConditionStr.contains("scmCocName")&&groupConditionStr.contains("hsName")){
					key = tempInfo.getScmCocName()+ tempInfo.getCommName()+ tempInfo.getDate();
				}else if(groupConditionStr.contains("scmCocName")&&groupConditionStr.contains("hsSpec")){
					key = tempInfo.getScmCocName()+ tempInfo.getPtSpec()+tempInfo.getDate();
				}else if(groupConditionStr.contains("hsName")&&groupConditionStr.contains("hsSpec")){
					key = tempInfo.getCommName()+ tempInfo.getPtSpec()+tempInfo.getDate();
				}else if(groupConditionStr.contains("scmCocName")){
					key = tempInfo.getScmCocName()+tempInfo.getDate();
				}else if(groupConditionStr.contains("hsName")){
					key = tempInfo.getCommName()+tempInfo.getDate();
				} else if (groupConditionStr.contains("hsSpec")) {
					key = tempInfo.getPtSpec()+tempInfo.getDate();
				}
				
				if (map.containsKey(key)) {
					TempTransFactCompareInfo oldInfo = (TempTransFactCompareInfo)map.get(key);
					//合并数据
					//日期 收货质量 收货数量 转厂质量 转厂数量 结余质量 结余数量 关封质量 关封数量
					// 收货质量 收货数量
					oldInfo
							.setSendWeight((oldInfo.getSendWeight() == null ? 0
									: oldInfo.getSendWeight())
									+ (tempInfo.getSendWeight() == null ? 0
											: tempInfo.getSendWeight()));
					oldInfo
							.setSendAmount((oldInfo.getSendAmount() == null ? 0
									: oldInfo.getSendAmount())
									+ (tempInfo.getSendAmount() == null ? 0
											: tempInfo.getSendAmount()));	
					//转厂质量 转厂数量
					oldInfo
							.setTransWeight((oldInfo.getTransWeight() == null ? 0
									: oldInfo.getTransWeight())
									+ (tempInfo.getTransWeight() == null ? 0
											: tempInfo.getTransWeight()));
					oldInfo
							.setTransAmount((oldInfo.getTransAmount() == null ? 0
									: oldInfo.getTransAmount())
									+ (tempInfo.getTransAmount() == null ? 0
											: tempInfo.getTransAmount()));		
					//结余质量 结余数量
					oldInfo
							.setRemainWeight((oldInfo.getRemainWeight() == null ? 0
									: oldInfo.getRemainWeight())
									+ (tempInfo.getRemainWeight() == null ? 0
											: tempInfo.getRemainWeight()));
					oldInfo
							.setRemainAmount((oldInfo.getRemainAmount() == null ? 0
									: oldInfo.getRemainAmount())
									+ (tempInfo.getRemainAmount() == null ? 0
											: tempInfo.getRemainAmount()));
					//关封质量 关封数量
					oldInfo
							.setEnvelopWeight((oldInfo.getEnvelopWeight() == null ? 0
									: oldInfo.getEnvelopWeight())
									+ (tempInfo.getEnvelopWeight() == null ? 0
											: tempInfo.getEnvelopWeight()));
					oldInfo
							.setEnvelopAmount((oldInfo.getEnvelopAmount() == null ? 0
							: oldInfo.getEnvelopAmount())
							+ (tempInfo.getEnvelopAmount() == null ? 0
									: tempInfo.getEnvelopAmount()));
				} else {
					map.put(key, tempInfo);
				}
			}
			lsResult.clear();
			Set keySet = map.keySet();//hashMap<key value>中的key遍历
			Iterator keyIterator = keySet.iterator();
			while(keyIterator.hasNext()){
				TempTransFactCompareInfo object = (TempTransFactCompareInfo)map.get(keyIterator.next());
				
				//根据分组条件 处理供应商，商品名称，规格型号 ，关封，联系人，联系电话，传真表格数据，适应表格，打印报表显示问题
				if(groupConditionStr.contains("scmCocName")&&groupConditionStr.contains("hsName")&&groupConditionStr.contains("hsSpec")){
					
				}else if(groupConditionStr.contains("scmCocName")&&groupConditionStr.contains("hsName")){
					object.setPtSpec("");
					object =processDataByCommNameSpec(object);
				}else if(groupConditionStr.contains("scmCocName")&&groupConditionStr.contains("hsSpec")){
					object.setCommName("");
					object =processDataByCommNameSpec(object);
				}else if(groupConditionStr.contains("hsName")&&groupConditionStr.contains("hsSpec")){
					object.setScmCocName("");
					object =processDataByScmCocName(object);
				}else if(groupConditionStr.contains("scmCocName")){
					object.setCommName("");
					object.setPtSpec("");
					object =processDataByCommNameSpec(object);
				}else if(groupConditionStr.contains("hsName")){
					object.setScmCocName("");
					object.setPtSpec("");
					object =processDataByScmCocName(object);
					object = processDataByCommNameSpec(object);
				} else if (groupConditionStr.contains("hsSpec")) {
					object.setCommName("");
					object.setScmCocName("");
					object = processDataByScmCocName(object);
					object = processDataByCommNameSpec(object);
				}
				
				lsResult.add(object);
			}
		}
		return lsResult;
	}
	
	/**
	 * 根据分组条件供应商处理联系人，联系电话，传真表格数据，适应表格，打印报表显示问题
	 * @param info
	 * @return
	 */
	private TempTransFactCompareInfo processDataByScmCocName(TempTransFactCompareInfo info){
		info.setTel("");
		info.setFax("");
		info.setLinkMan("");
		return info;
	}
	
	/**
	 * 根据分组条件商品名称，商品规格处理关封数据，适应表格，打印报表显示问题
	 * @param info
	 * @return
	 */
	private TempTransFactCompareInfo processDataByCommNameSpec(TempTransFactCompareInfo info){
		info.setEnvelopAmount(Double.valueOf(0));
		info.setEnvelopWeight(Double.valueOf(0));
		info.setEnvelopNo("");
		info.setTerm("");
		info.setIsEndCase(false);
		return info;
	}
	
	/**
	 * 填充数据到重量中
	 * 
	 * @param list
	 * @param mapAmount
	 * @param mapWeight
	 */
	private void fillListToAmountWeightMap(List list,
			Map<String, Double> mapAmount, Map<String, Double> mapWeight) {
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String nameSpec = "";
			String spec = "";
			if (mapWeight == null) {// 此处是为了应付关封没有统计重量的情况
				if (objs[5] != null)
					spec = objs[5].toString().trim();
			} else {
				if (objs[6] != null)
					spec = objs[6].toString().trim();
			}
			if (objs[1] != null) {
				nameSpec = objs[1].toString().trim() + "/" + spec;
				if (nameSpec.length() != 0
						&& nameSpec.substring(nameSpec.length() - 1,
								nameSpec.length()).equals("/")) {
					if (nameSpec.substring(nameSpec.length() - 2,
							nameSpec.length() - 1).equals("/"))
						nameSpec = nameSpec.substring(0, nameSpec.length() - 2);
					else
						nameSpec = nameSpec.substring(0, nameSpec.length() - 1);
				}
			}
			String key = ((objs[0] == null ? "" : objs[0].toString().trim())
					+ "@#@"
					+ nameSpec.trim()// 名称加规格
					+ "@#@"
					+ (objs[2] == null ? "" : objs[2].toString().trim())
					+ "@#@" + (objs[3] == null ? "" : objs[3].toString().trim()));
			if (mapAmount != null) {
				if (mapAmount.get(key) == null) {
					mapAmount.put(key, objs[4] == null ? 0.0 : Double
							.parseDouble(objs[4].toString()));
				} else {
					mapAmount.put(key, mapAmount.get(key)
							+ (objs[4] == null ? 0.0 : Double
									.parseDouble(objs[4].toString())));
				}
			}
			if (mapWeight != null) {
				if (mapWeight.get(key) == null) {
					mapWeight.put(key, objs[5] == null ? 0.0 : Double
							.parseDouble(objs[5].toString()));
				} else {
					mapWeight.put(key, mapWeight.get(key)
							+ (objs[5] == null ? 0.0 : Double
									.parseDouble(objs[5].toString())));
				}
			}
		}
	}

	/**
	 * 填充数据到日期中
	 * 
	 * @param list
	 * @param mapAmount
	 * @param mapWeight
	 */
	private void fillListToDateMap(List list, Map<String, Date> mapBillDate) {
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String nameSpec = "";
			if (objs[1] != null && objs[5] != null) {
				nameSpec = objs[1].toString().trim() + "/"
						+ objs[5].toString().trim();
				if (nameSpec.length() != 0
						&& nameSpec.substring(nameSpec.length() - 1,
								nameSpec.length()).equals("/")) {
					nameSpec = nameSpec.substring(0, nameSpec.length() - 1);
				}
			}
			String key = ((objs[0] == null ? "" : objs[0].toString().trim())
					+ "@#@" + nameSpec + "@#@"
					+ (objs[2] == null ? "" : objs[2].toString().trim())
					+ "@#@" + (objs[3] == null ? "" : objs[3].toString().trim()));
			if (mapBillDate != null) {
				mapBillDate.put(key, (Date) objs[4]);
			}
		}
	}

	/**
	 * 转厂差额总表
	 * 
	 * @param endDate
	 * @return
	 */
	public List findAllTransFactDiffInfo(boolean isImg, boolean isFindByScmcoc,
			ScmCoc scmCoc, Date endDate) {
		List lsResult = new ArrayList();
		Map<String, Double> mapSendAmount = new HashMap<String, Double>();
		Map<String, Double> mapSendWeight = new HashMap<String, Double>();
		Map<String, Double> mapBackAmount = new HashMap<String, Double>();
		Map<String, Double> mapBackWeight = new HashMap<String, Double>();
		Map<String, Double> mapPlusAmount = new HashMap<String, Double>();
		Map<String, Double> mapPlusWeight = new HashMap<String, Double>();
		Map<String, Double> mapMinusAmount = new HashMap<String, Double>();
		Map<String, Double> mapMinusWeight = new HashMap<String, Double>();
		Map<String, Double> mapTransAmount = new HashMap<String, Double>();
		Map<String, Double> mapTransWeight = new HashMap<String, Double>();
		Map<String, Double> mapEnvelopAmount = new HashMap<String, Double>();
		Map<String, Double> mapEnvelopWeight = new HashMap<String, Double>();
		Map<String, Date> mapBillDate = new HashMap<String, Date>();

		Map<String, TempAllTransFactDiffInfo> mapDiffInfo = new HashMap<String, TempAllTransFactDiffInfo>();
		// 单据
		List list = new ArrayList();
		if (isFindByScmcoc) {
			list = this.casTransferFactoryDao.findTotalSendAmount(isImg,
					scmCoc, endDate);
			this.fillListToAmountWeightMap(list, mapSendAmount, mapSendWeight);
		} else {
			list = this.casTransferFactoryDao.findTotalSendAmountNoScm(isImg,
					endDate);
			this.fillListToAmountWeightMap(list, mapSendAmount, mapSendWeight);
		}

		// list = this.casTransferFactoryDao.findTotalSendAmount(false,
		// endDate);
		// this.fillListToAmountWeightMap(list, mapSendAmount, mapSendWeight);
		if (isFindByScmcoc) {
			list = this.casTransferFactoryDao.findTotalBackAmount(isImg,
					scmCoc, endDate);
			this.fillListToAmountWeightMap(list, mapBackAmount, mapBackWeight);
		} else {
			list = this.casTransferFactoryDao.findTotalBackAmountNoScm(isImg,
					endDate);
			this.fillListToAmountWeightMap(list, mapBackAmount, mapBackWeight);
		}

		// list = this.casTransferFactoryDao.findTotalBackAmount(false,
		// endDate);
		// this.fillListToAmountWeightMap(list, mapBackAmount, mapBackWeight);

		if (isImg) {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao.findTotalAmountBy(isImg,
						scmCoc, "1015", endDate);// 已收货未结转期初单
				this.fillListToAmountWeightMap(list, mapPlusAmount,
						mapPlusWeight);
			} else {
				list = this.casTransferFactoryDao.findTotalAmountByNoScm(isImg,
						"1015", endDate);// 已收货未结转期初单
				this.fillListToAmountWeightMap(list, mapPlusAmount,
						mapPlusWeight);
			}
		} else {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao.findTotalAmountBy(false,
						scmCoc, "2011", endDate);// 已交货未结转期初单
				this.fillListToAmountWeightMap(list, mapPlusAmount,
						mapPlusWeight);
				// this.fillListToAmountWeightMap(list, mapMinusAmount,
				// mapMinusWeight);
			} else {
				list = this.casTransferFactoryDao.findTotalAmountByNoScm(false,
						"2011", endDate);// 已交货未结转期初单
				this.fillListToAmountWeightMap(list, mapPlusAmount,
						mapPlusWeight);
				// this.fillListToAmountWeightMap(list, mapMinusAmount,
				// mapMinusWeight);
			}
		}

		if (isImg) {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao.findTotalAmountBy(isImg,
						scmCoc, "1016", endDate);// 未收货已结转期初单
			} else {
				list = this.casTransferFactoryDao.findTotalAmountByNoScm(isImg,
						"1016", endDate);// 未收货已结转期初单
			}
		} else {
			// this
			// .fillListToAmountWeightMap(list, mapMinusAmount,
			// mapMinusWeight);
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao.findTotalAmountBy(false,
						scmCoc, "2012", endDate);// 未交货已结转期初单
			} else {
				list = this.casTransferFactoryDao.findTotalAmountByNoScm(false,
						"2012", endDate);// 未交货已结转期初单
			}
		}

		this.fillListToAmountWeightMap(list, mapMinusAmount, mapMinusWeight);
		// 转厂
		if (isImg) {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeight(isImg,
								ProjectType.BCS, endDate, scmCoc);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			} else {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeightNoScm(isImg,
								ProjectType.BCS, endDate);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			}
		} else {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeight(false,
								ProjectType.BCS, endDate, scmCoc);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			} else {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeightNoScm(false,
								ProjectType.BCS, endDate);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			}
		}

		if (isImg) {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeight(isImg,
								ProjectType.BCUS, endDate, scmCoc);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			} else {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeightNoScm(isImg,
								ProjectType.BCUS, endDate);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			}
		} else {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeight(false,
								ProjectType.BCUS, endDate, scmCoc);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			} else {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeightNoScm(false,
								ProjectType.BCUS, endDate);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			}
		}

		if (isImg) {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeight(isImg,
								ProjectType.DZSC, endDate, scmCoc);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			} else {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeightNoScm(isImg,
								ProjectType.DZSC, endDate);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			}
		} else {
			if (isFindByScmcoc) {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeight(false,
								ProjectType.DZSC, endDate, scmCoc);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			} else {
				list = this.casTransferFactoryDao
						.findTotalCustomsDeclarationAmountWeightNoScm(false,
								ProjectType.DZSC, endDate);
				this.fillListToAmountWeightMap(list, mapTransAmount,
						mapTransWeight);
			}
		}

		if (isFindByScmcoc) {
			// 关封
			list = this.casTransferFactoryDao.findTotalEnvelopAmountWeight(
					endDate, scmCoc);
		} else {
			// 关封
			list = this.casTransferFactoryDao
					.findTotalEnvelopAmountWeightNoScm(endDate);
		}

		// -------------------深加工结转
		if (isImg) {
			if (isFindByScmcoc) {
				List fptListin = this.casTransferFactoryDao
						.findTotalFptAmountWeightIn(endDate, scmCoc);
				list.addAll(fptListin);
			} else {
				List fptListin = this.casTransferFactoryDao
						.findTotalFptAmountWeightInNoScm(endDate);
				list.addAll(fptListin);
			}

		} else {
			if (isFindByScmcoc) {
				List fptListOut = this.casTransferFactoryDao
						.findTotalFptAmountWeightOut(endDate, scmCoc);
				list.addAll(fptListOut);
			} else {
				List fptListOut = this.casTransferFactoryDao
						.findTotalFptAmountWeightOutNoScm(endDate);
				list.addAll(fptListOut);
			}
		}
		// -------------------深加工结转
		this.fillListToAmountWeightMap(list, mapEnvelopAmount, null);

		if (isFindByScmcoc) {
			list = this.casTransferFactoryDao.findLastBillMasterDate(isImg,
					endDate, scmCoc);
			this.fillListToDateMap(list, mapBillDate);
		} else {
			list = this.casTransferFactoryDao.findLastBillMasterDateNoScm(
					isImg, endDate);
			this.fillListToDateMap(list, mapBillDate);
		}
		// list = this.casTransferFactoryDao
		// .findLastBillMasterDate(false, endDate);
		// this.fillListToDateMap(list, mapBillDate);

		Iterator iterator = mapSendAmount.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			TempAllTransFactDiffInfo diffInfo = mapDiffInfo.get(key);
			if (diffInfo == null) {
				String[] strs = key.split("@#@");
				diffInfo = new TempAllTransFactDiffInfo();
				if (strs.length > 0) {
					diffInfo.setComplexCode(strs[0]);
				}
				if (strs.length > 1) {
					diffInfo.setCommName(strs[1]);
				}

				if (strs.length > 2) {
					diffInfo.setCommUnit(strs[2]);
				}
				if (strs.length > 3) {
					diffInfo.setScmCocName(strs[3]);
				}
				mapDiffInfo.put(key, diffInfo);
			}
			diffInfo.setSendAmount(CommonUtils.getDoubleExceptNull(diffInfo
					.getSendAmount())
					+ CommonUtils.getDoubleExceptNull(mapSendAmount.get(key)));
			diffInfo.setSendWeight(CommonUtils.getDoubleExceptNull(diffInfo
					.getSendWeight())
					+ CommonUtils.getDoubleExceptNull(mapSendWeight.get(key)));
		}
		iterator = mapBackAmount.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			TempAllTransFactDiffInfo diffInfo = mapDiffInfo.get(key);
			if (diffInfo == null) {
				String[] strs = key.split("@#@");
				diffInfo = new TempAllTransFactDiffInfo();
				if (strs.length > 0) {
					diffInfo.setComplexCode(strs[0]);
				}
				if (strs.length > 1) {
					diffInfo.setCommName(strs[1]);
				}
				if (strs.length > 2) {
					diffInfo.setCommUnit(strs[2]);
				}
				if (strs.length > 3) {
					diffInfo.setScmCocName(strs[3]);
				}
				mapDiffInfo.put(key, diffInfo);
			}
			diffInfo.setBackAmount(CommonUtils.getDoubleExceptNull(diffInfo
					.getBackAmount())
					+ CommonUtils.getDoubleExceptNull(mapBackAmount.get(key)));
			diffInfo.setBackWeight(CommonUtils.getDoubleExceptNull(diffInfo
					.getBackWeight())
					+ CommonUtils.getDoubleExceptNull(mapBackWeight.get(key)));
		}
		iterator = mapTransAmount.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			TempAllTransFactDiffInfo diffInfo = mapDiffInfo.get(key);
			if (diffInfo == null) {
				String[] strs = key.split("@#@");
				diffInfo = new TempAllTransFactDiffInfo();
				if (strs.length > 0) {
					diffInfo.setComplexCode(strs[0]);
				}
				if (strs.length > 1) {
					diffInfo.setCommName(strs[1]);
				}
				if (strs.length > 2) {
					diffInfo.setCommUnit(strs[2]);
				}
				if (strs.length > 3) {
					diffInfo.setScmCocName(strs[3]);
				}
				mapDiffInfo.put(key, diffInfo);
			}
			diffInfo.setTransAmount(CommonUtils.getDoubleExceptNull(diffInfo
					.getTransAmount())
					+ CommonUtils.getDoubleExceptNull(mapTransAmount.get(key)));
			diffInfo.setTransWeight(CommonUtils.getDoubleExceptNull(diffInfo
					.getTransWeight())
					+ CommonUtils.getDoubleExceptNull(mapTransWeight.get(key)));
		}
		iterator = mapEnvelopAmount.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			TempAllTransFactDiffInfo diffInfo = mapDiffInfo.get(key);
			if (diffInfo == null) {
				String[] strs = key.split("@#@");
				diffInfo = new TempAllTransFactDiffInfo();
				if (strs.length > 0) {
					diffInfo.setComplexCode(strs[0]);
				}
				if (strs.length > 1) {
					diffInfo.setCommName(strs[1]);
				}
				// edit by xxm
				if (strs.length > 2) {
					diffInfo.setCommUnit(strs[2]);
				}
				if (strs.length > 3) {
					diffInfo.setScmCocName(strs[3]);
				}
				mapDiffInfo.put(key, diffInfo);
			}
			diffInfo.setEnvelopAmount(CommonUtils.getDoubleExceptNull(diffInfo
					.getEnvelopAmount())
					+ CommonUtils
							.getDoubleExceptNull(mapEnvelopAmount.get(key)));
			diffInfo.setEnvelopWeight(CommonUtils.getDoubleExceptNull(diffInfo
					.getEnvelopWeight())
					+ CommonUtils
							.getDoubleExceptNull(mapEnvelopWeight.get(key)));
		}
		iterator = mapPlusAmount.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			TempAllTransFactDiffInfo diffInfo = mapDiffInfo.get(key);
			if (diffInfo == null) {
				String[] strs = key.split("@#@");
				diffInfo = new TempAllTransFactDiffInfo();
				if (strs.length > 0) {
					diffInfo.setComplexCode(strs[0]);
				}
				if (strs.length > 1) {
					diffInfo.setCommName(strs[1]);
				}
				if (strs.length > 2) {
					diffInfo.setCommUnit(strs[2]);
				}
				if (strs.length > 3) {
					diffInfo.setScmCocName(strs[3]);
				}
				mapDiffInfo.put(key, diffInfo);
			}
			diffInfo.setPlusAmount(CommonUtils.getDoubleExceptNull(diffInfo
					.getPlusAmount())
					+ CommonUtils.getDoubleExceptNull(mapPlusAmount.get(key)));
			diffInfo.setPlusWeight(CommonUtils.getDoubleExceptNull(diffInfo
					.getPlusWeight())
					+ CommonUtils.getDoubleExceptNull(mapPlusWeight.get(key)));
		}
		iterator = mapMinusAmount.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			TempAllTransFactDiffInfo diffInfo = mapDiffInfo.get(key);
			if (diffInfo == null) {
				String[] strs = key.split("@#@");
				diffInfo = new TempAllTransFactDiffInfo();
				if (strs.length > 0) {
					diffInfo.setComplexCode(strs[0]);
				}
				if (strs.length > 1) {
					diffInfo.setCommName(strs[1]);
				}
				if (strs.length > 2) {
					diffInfo.setCommUnit(strs[2]);
				}
				if (strs.length > 3) {
					diffInfo.setScmCocName(strs[3]);
				}
				mapDiffInfo.put(key, diffInfo);
			}
			diffInfo.setMinusAmount(CommonUtils.getDoubleExceptNull(diffInfo
					.getMinusAmount())
					+ CommonUtils.getDoubleExceptNull(mapMinusAmount.get(key)));
			diffInfo.setMinusWeight(CommonUtils.getDoubleExceptNull(diffInfo
					.getMinusWeight())
					+ CommonUtils.getDoubleExceptNull(mapMinusWeight.get(key)));
		}

		lsResult.addAll(mapDiffInfo.values());
		Collections.sort(lsResult);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < lsResult.size(); i++) {
			TempAllTransFactDiffInfo diffInfo = (TempAllTransFactDiffInfo) lsResult
					.get(i);
			// 已转或待转
			double damount = CommonUtils.getDoubleExceptNull(diffInfo
					.getSendAmount())
					- CommonUtils.getDoubleExceptNull(diffInfo.getBackAmount())
					- CommonUtils
							.getDoubleExceptNull(diffInfo.getTransAmount())
					+ CommonUtils.getDoubleExceptNull(diffInfo.getPlusAmount())// 已收货
					- CommonUtils.getDoubleExceptNull(diffInfo// 未收货
							.getMinusAmount());
			double dweight = CommonUtils.getDoubleExceptNull(diffInfo
					.getSendWeight())
					- CommonUtils.getDoubleExceptNull(diffInfo.getBackWeight())
					- CommonUtils
							.getDoubleExceptNull(diffInfo.getTransWeight())
					+ CommonUtils.getDoubleExceptNull(diffInfo.getPlusWeight())// 已收货
					- CommonUtils.getDoubleExceptNull(diffInfo// 未收货
							.getMinusWeight());
			// 当damonut为正数为待转，否则为已转
			if (damount > 0) {
				diffInfo.setWaitTransAmount(damount);
				diffInfo.setWaitTransWeight(dweight);
			} else {
				diffInfo.setExceedTransAmount(-damount);
				diffInfo.setExceedTransWeight(-dweight);
			}
			diffInfo
					.setEnvelopRemainAmount(CommonUtils
							.getDoubleExceptNull(diffInfo.getEnvelopAmount())
							- (CommonUtils.getDoubleExceptNull(diffInfo
									.getSendAmount()) - CommonUtils
									.getDoubleExceptNull(diffInfo
											.getBackAmount())));
			diffInfo
					.setEnvelopRemainWeight(CommonUtils
							.getDoubleExceptNull(diffInfo.getEnvelopWeight())
							- (CommonUtils.getDoubleExceptNull(diffInfo
									.getSendWeight()) - CommonUtils
									.getDoubleExceptNull(diffInfo
											.getBackWeight())));
			// 差额=关封余量-待转+先转
			diffInfo.setDiffAmount(CommonUtils.getDoubleExceptNull(diffInfo
					.getEnvelopRemainAmount())
					- CommonUtils.getDoubleExceptNull(diffInfo
							.getWaitTransAmount())
					+ CommonUtils.getDoubleExceptNull(diffInfo
							.getExceedTransAmount()));
			// 差额=关封余量-待转+先转
			diffInfo.setDiffWeight(CommonUtils.getDoubleExceptNull(diffInfo
					.getEnvelopRemainWeight())
					- CommonUtils.getDoubleExceptNull(diffInfo
							.getWaitTransWeight())
					+ CommonUtils.getDoubleExceptNull(diffInfo
							.getExceedTransWeight()));
			diffInfo.setEnvelopRemainWeight(CommonUtils
					.getDoubleExceptNull(diffInfo.getEnvelopRemainWeight())
					- CommonUtils.getDoubleExceptNull(diffInfo
							.getWaitTransWeight())
					+ CommonUtils.getDoubleExceptNull(diffInfo
							.getExceedTransWeight()));
			Date date = mapBillDate.get(diffInfo.getKey());
			if (date != null) {
				diffInfo.setBeginDate(dateFormat.format(date));
			}
		}
		return lsResult;
	}

	/**
	 * 删除海关账发票
	 * 
	 * @param obj
	 * @return
	 */
	public boolean deleteCasInvoice(CasInvoice obj) {
		List list = this.casDao.findCasInvoiceInfo(obj, null);
		for (int i = 0; i < list.size(); i++) {
			CasInvoiceInfo info = (CasInvoiceInfo) list.get(i);
			if (info.isCanceled() != null && info.isCanceled()) {
				return false;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			CasInvoiceInfo info = (CasInvoiceInfo) list.get(i);
			List klist = this.casDao
					.findInvoiceAndBillCorrespondingByCasInvoiceInfo(info);
			for (int m = 0; m < klist.size(); m++) {
				Object temp = list.get(m);
				this.casDao.delete(temp);
			}
			this.casDao.delete(info);
		}
		this.casDao.delete(obj);
		return true;
	}

	// /**
	// * 获得在单据中商品信息不是全部转为真的单据记录 Tfe == 结转出口
	// *
	// * @param scmCocId
	// * 客户id
	// * @return 单据类型为成品出入库的单据中商品信息不是全部转为真的单据记录
	// */
	// public List findTempBillMasterIsAvailabilityToTFBByfe(String scmCocId,
	// String envelopNo) {
	// String billCode = String.valueOf(2102);
	// int sBillType = SBillType.PRODUCE_INOUT;
	// List oldList = this.casDao.findBillMasterIsAvailabilityToFpt(scmCocId,
	// sBillType, billCode, envelopNo);
	// List<TempBillMaster> newList = new ArrayList<TempBillMaster>();
	// for (int i = 0, n = oldList.size(); i < n; i++) {
	// TempBillMaster tempBillMaster = new TempBillMaster();
	// // List
	// wareset=this.casDao.getwareSetNameFB(sBillType,(BillMaster)oldList.get(i));
	// // for (int j = 0; j < wareset.size(); j++) {
	// // tempBillMaster.setWareSet((WareSet) wareset.get(j));
	// // }
	// tempBillMaster.setBillMaster((BillMaster) oldList.get(i));
	// tempBillMaster.setIsSelected(new Boolean(false));
	//			
	// newList.add(tempBillMaster);
	// }
	// return newList;
	// }
	//	
	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转
	 * 
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	 */
	public List findTempBillMasterIsAvailabilityToFpt(String scmCocId,
			String appNo, String billCode, Date beginDate, Date endDate) {
		List oldList = this.casDao.findBillMasterIsAvailabilityToFpt(scmCocId,
				billCode, appNo, beginDate, endDate);
		List<TempBillMaster> newList = new ArrayList<TempBillMaster>();
		for (int i = 0, n = oldList.size(); i < n; i++) {
			TempBillMaster tempBillMaster = new TempBillMaster();
			// List wareset=this.casDao.getwareSetNameFB(sBillType,
			// (BillMaster)oldList.get(i));
			// tempBillMaster.setWareSet((WareSet) wareset.get(i));
			tempBillMaster.setBillMaster((BillMaster) oldList.get(i));
			tempBillMaster.setIsSelected(new Boolean(false));
			newList.add(tempBillMaster);
		}
		return newList;

	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfe == 结转出口
	 * 
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为成品出入库的单据中商品信息不是全部转为真的单据记录
	 */
	public List findTempBillMasterIsAvailabilityToTFBByfe(String scmCocId,
			String envelopNo, Date begin, Date end) {
		String billCode = String.valueOf(2102);
		int sBillType = SBillType.PRODUCE_INOUT;
		List oldList = this.casDao.findBillMasterIsAvailabilityToTFB(scmCocId,
				sBillType, billCode, envelopNo, begin, end);
		List<TempBillMaster> newList = new ArrayList<TempBillMaster>();
		for (int i = 0, n = oldList.size(); i < n; i++) {
			TempBillMaster tempBillMaster = new TempBillMaster();
			// List
			// wareset=this.casDao.getwareSetNameFB(sBillType,(BillMaster)oldList.get(i));
			// for (int j = 0; j < wareset.size(); j++) {
			// tempBillMaster.setWareSet((WareSet) wareset.get(j));
			// }
			tempBillMaster.setBillMaster((BillMaster) oldList.get(i));
			tempBillMaster.setIsSelected(new Boolean(false));

			newList.add(tempBillMaster);
		}
		return newList;
	}

	/**
	 * 获得在单据中商品信息不是全部转为真的单据记录 Tfi == 结转进口
	 * 
	 * @param scmCocId
	 *            客户id
	 * @return 单据类型为料件入库的单据中商品信息不是全部转为真的单据记录
	 */
	public List findTempBillMasterIsAvailabilityToTFBByTfi(String scmCocId,
			String envelopNo, Date begin, Date end) {
		String billCode = String.valueOf(1004);
		int sBillType = SBillType.MATERIEL_IN;
		List oldList = this.casDao.findBillMasterIsAvailabilityToTFB(scmCocId,
				sBillType, billCode, envelopNo, begin, end);

		List<TempBillMaster> newList = new ArrayList<TempBillMaster>();
		for (int i = 0, n = oldList.size(); i < n; i++) {
			TempBillMaster tempBillMaster = new TempBillMaster();
			tempBillMaster.setBillMaster((BillMaster) oldList.get(i));
			tempBillMaster.setIsSelected(new Boolean(false));
			newList.add(tempBillMaster);
		}
		return newList;

	}
	
	
	/**
	 * 结转对帐表
	 * 
	 * @param isMaterial
	 * @param commName
	 * @param scmCoc
	 * @param beginMonth
	 * @param endMonth
	 * @return
	 */
	public List findTransFactCompare(boolean isMaterial, String commName,
			String commSpec, ScmCoc scmCoc, Date beginMonth, Date endMonth,
			List<String> groupCondition) {
		//保存客户供应商资料 key = code  value = name 
		Map<String,ScmCoc> mapScmCoc = new HashMap<String,ScmCoc>();
		List listScmCoc = casTransferFactoryDao.findScmCoc();
		for (int i = 0; i < listScmCoc.size(); i++) {
			ScmCoc scm = (ScmCoc) listScmCoc.get(i);
			if(scm!=null){
				mapScmCoc.put(scm.getCode(), scm);
			}
		}
		
		// 查询单据数据
		List<Object[]> billDatas = casTransferFactoryDao
				.findBillDetailSumAmountWeight(isMaterial, commName, commSpec,
						scmCoc, beginMonth, endMonth, null);
		// 查询转厂数据
		List<Object[]> transDatas = casTransferFactoryDao
				.findCustomsDeclarationCommInfoSumAmountWeight(isMaterial,
						commName, commSpec, scmCoc, beginMonth, endMonth, null);
		
		// 查询关封数据
		List<Object[]> envelopDatas = casTransferFactoryDao
				.findCustomsCustomsEnvelopSumAmountWeight(isMaterial, commName,
						commSpec, scmCoc, beginMonth, endMonth, null); 
		
		
		Map<String, TempTransFactCompareInfo> dataMap = new HashMap<String, TempTransFactCompareInfo>();
		Object[] data = null;
		TempTransFactCompareInfo info = null;
		String key = null;
		String scmCocName = null;
		String hsName = null;
		String hsSpec = null;
		Integer year = null;
		Integer month = null;
		String hsUnit = null;
		String billTypeCode = null;
		Double amount = null;
		Double netWeight = null;
		// 统计关联单据数据
		for (int i = 0; i < billDatas.size(); i++) {
			data = billDatas.get(i);
			ScmCoc sc = mapScmCoc.get((String) data[0]);
			scmCocName = sc.getName();
			hsName = (String) data[1];
			hsSpec = (String) data[2];
			year = (Integer) data[3];
			month = (Integer) data[4];
			hsUnit = (String) data[5];
			billTypeCode = (String) data[6];
			amount = CommonUtils.getDoubleExceptNull((Double) data[7]);
			netWeight = CommonUtils.getDoubleExceptNull((Double) data[8]);
			
			// key = 供应商 + 商品名称 + 商品规格 + 年份 + 月份 + 关封号
			key = scmCocName + "," + hsName + "," + hsSpec + "," + year + ","
					+ month + "," + hsUnit;
			info = dataMap.get(key);
			
			if (info == null) {
				info = new TempTransFactCompareInfo();
				info.initAmount();
				info.setScmCocName(scmCocName);
				info.setCommName(hsName);
				info.setCommSpec(hsSpec);
				info.setDate(year + "-" + month);
				info.setHsUnit(hsUnit);
				info.setLinkMan(sc.getLinkMan());
				info.setTel(sc.getLinkTel());
				info.setFax(sc.getFax());
				
				dataMap.put(key, info);
			}
			
			if (isMaterial) {
				// 收货数 = 1004	结转进口 + 1015 已收货未结转期初单 - 1106 结转料件退货单
				if("1004".equals(billTypeCode)) {
					info.setSendAmount(info.getSendAmount() + amount);
					info.setSendWeight(info.getSendWeight() + netWeight);
				} else if("1015".equals(billTypeCode)) {
					info.setSendAmount(info.getSendAmount() + amount);
					info.setSendWeight(info.getSendWeight() + netWeight);
				} else if("1106".equals(billTypeCode)) {
					info.setSendAmount(info.getSendAmount() - amount);
					info.setSendWeight(info.getSendWeight() - netWeight);
				}
			} else {
				// 发货数 = 2102	结转出口 + 2011 已交货未结转期初单 - 2009 结转成品退货单
				if("2102".equals(billTypeCode)) {
					info.setSendAmount(info.getSendAmount() + amount);
					info.setSendWeight(info.getSendWeight() + netWeight);
				} else if("2011".equals(billTypeCode)) {
					info.setSendAmount(info.getSendAmount() + amount);
					info.setSendWeight(info.getSendWeight() + netWeight);
				} else if("2009".equals(billTypeCode)) {
					info.setSendAmount(info.getSendAmount() - amount);
					info.setSendWeight(info.getSendWeight() - netWeight);
				}
			}
			
			info.setRemainAmount(info.getSendAmount() - info.getTransAmount());
			info.setRemainWeight(info.getSendWeight() - info.getTransWeight());
		}
		
		// 统计关联转厂数据
		//String tradeMode = null;
		for (int i = 0; i < transDatas.size(); i++) {
			data = transDatas.get(i);
			scmCocName = (String) data[0];
			hsName = (String) data[1];
			hsSpec = (String) data[2];
			year = (Integer) data[3];
			month = (Integer) data[4];
			hsUnit = (String) data[5];
			//tradeMode = (String) data[6];
			amount = CommonUtils.getDoubleExceptNull((Double) data[7]);
			netWeight = CommonUtils.getDoubleExceptNull((Double) data[8]);
			
			// key = 供应商 + 商品名称 + 商品规格 + 年份 + 月份 + 关封号
			key = scmCocName + "," + hsName + "," + hsSpec + "," + year + ","
					+ month + "," + hsUnit;
			info = dataMap.get(key);

			if (info == null) {
				info = new TempTransFactCompareInfo();
				info.initAmount();
				info.setScmCocName(scmCocName);
				info.setCommName(hsName);
				info.setCommSpec(hsSpec);
				info.setDate(year + "-" + month);
				info.setHsUnit(hsUnit);
				
				dataMap.put(key, info);
			}
			
			info.setTransAmount(info.getTransAmount() + amount);
			info.setTransWeight(info.getTransWeight() + netWeight);
			
			info.setRemainAmount(info.getSendAmount() - info.getTransAmount());
			info.setRemainWeight(info.getSendWeight() - info.getTransWeight());
		}
		
		// 统计关封数据
		String envelopNo = null;
		Map<String, TempTransFactCompareInfo> envelopMap = new HashMap<String, TempTransFactCompareInfo>();
		for (int i = 0; i < envelopDatas.size(); i++) {
			data = envelopDatas.get(i);
			envelopNo = (String) data[0];
			scmCocName = (String) data[1];
			hsName = (String) data[2];
			hsSpec = (String) data[3];
			hsUnit = (String) data[4];
			amount = CommonUtils.getDoubleExceptNull((Double) data[5]);
			// 供应商 + 商品名称 + 商品规格
			key = scmCocName + "," + hsName + "," + hsSpec;
			
			info = envelopMap.get(key);

			if (info == null) {
				info = new TempTransFactCompareInfo();
				info.initAmount();
				info.setScmCocName(scmCocName);
				info.setCommName(hsName);
				info.setCommSpec(hsSpec);
				info.setHsUnit(hsUnit);
				
				envelopMap.put(key, info);
			}
			
			if (CommonUtils.isEmpty(info.getEnvelopNo())) {
				info.setEnvelopNo(envelopNo);
			} else {
				info.setEnvelopNo(info.getEnvelopNo() + "," + envelopNo);
			}
			
			info.setEnvelopAmount(info.getEnvelopAmount() + amount);
		}
		List<TempTransFactCompareInfo> list = new ArrayList<TempTransFactCompareInfo>(dataMap.values());

		// 关联关封数据 和 统计结余数据
		TempTransFactCompareInfo temp = null;
		//String timeKey = null;
		Map<String, Double[]> remainMap = new HashMap<String, Double[]>();
		Double[] remains = null;
		for (int i = 0; i < list.size(); i++) {
			info = list.get(i);
			scmCocName = info.getScmCocName();
			hsName = info.getCommName();
			hsSpec = info.getCommSpec();
			
			// 供应商 + 商品名称 + 商品规格
			key = scmCocName + "," + hsName + "," + hsSpec;
			//timeKey = key + "," + info.getDate();
			temp = envelopMap.get(key);
			if (temp != null) {
				info.setEnvelopNo(temp.getEnvelopNo());
				info.setEnvelopAmount(temp.getEnvelopAmount());
			}
			
			
			// 上个月结余数据
			remains = remainMap.get(key);
			if(remains == null) {
				remains = new Double[]{info.getRemainAmount(), info.getRemainWeight()};
				remainMap.put(key, remains);
			} else {
				// 累计上月结余数据
				info.setRemainAmount(info.getRemainAmount() + remains[0]);
				info.setRemainWeight(info.getRemainWeight() + remains[1]);
				remains[0] = info.getRemainAmount();
				remains[1] = info.getRemainWeight();
			}
		}
		
		// 排序
		Collections.sort(list, new Comparator<TempTransFactCompareInfo>() {
			@Override
			public int compare(TempTransFactCompareInfo o1,
					TempTransFactCompareInfo o2) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
					try {
						String stu1 = o1.getScmCocName() + "," +
								o1.getCommName() + "," +
								o1.getCommSpec() + "," + 
								sdf.parse(o1.getDate()).getTime();
						String stu2 = o2.getScmCocName() + "," +
								o2.getCommName() + "," +
								o2.getCommSpec() + "," + 
								sdf.parse(o2.getDate()).getTime();
						
						return stu1.compareTo(stu2);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				return 0;
			}
		});
		
		//////////////////////////////////////////////////////////////////////////////////////////
		Map<String,Object[]> materielMap = new HashMap<String,Object[]>();
		List materiels = casTransferFactoryDao.findBillDetailMateriel(beginMonth, endMonth,isMaterial);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		for (int i = 0; i < materiels.size(); i++) {
			Object[] bm = (Object[])materiels.get(i);
			// key = 供应商 + 商品名称 + 商品规格 + 年份 + 月份 + 关封号
			String bmKey = bm[0]+","+bm[1]+","+bm[2]+","
					+df.format(bm[3])+","+bm[4];
			System.out.println("===="+bmKey);
			materielMap.put(bmKey, bm);
		}
		
		List returnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			info = list.get(i);
			// key = 供应商 + 商品名称 + 商品规格 + 年份 + 月份 + 关封号
			String bmKey = "";
			try {
				bmKey = (info.getScmCocName()==null?"":info.getScmCocName())+","
						+(info.getCommName()==null?"":info.getCommName())+","
						+(info.getCommSpec()==null?"":info.getCommSpec())+","
						+df.format(df.parse(info.getDate()))+","
						+(info.getEnvelopNo()==null?"":info.getEnvelopNo());
				System.out.println("--"+bmKey);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Object[] bm = materielMap.get(bmKey);
			Double remainAmount = 0.0;
			if(bm!=null){
				TempTransFactCompareInfo tempInfo = new TempTransFactCompareInfo();
				tempInfo.setScmCocName(bm[0]==null?"":bm[0].toString());
				tempInfo.setCommName(bm[1]==null?"":bm[1].toString());
				tempInfo.setCommSpec(bm[2]==null?"":bm[2].toString());
				tempInfo.setDate("期初");
				remainAmount = NumberUtils.toDouble(bm[5]==null?"0":bm[5].toString());
				tempInfo.setRemainAmount(remainAmount);
				tempInfo.setEnvelopNo(bm[4]==null?"":bm[4].toString());
				returnList.add(tempInfo);
			}
			
			info.setSendAmount(CaleUtil.subtract(info.getRemainAmount(), remainAmount));
			returnList.add(info);
		}
		/////////////////////////////////////////////////////////////////////////////////////////
		return returnList;
	}
	
	
}
