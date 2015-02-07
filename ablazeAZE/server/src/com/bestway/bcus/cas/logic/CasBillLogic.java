/*
 * Created on 2004-7-29
 *getImg
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.logic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasBillDao;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.TempContainer;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.invoice.entity.CasInvoice;
import com.bestway.bcus.cas.invoice.entity.CasInvoiceInfo;
import com.bestway.bcus.cas.invoice.entity.InvoiceAndBillCorresponding;
import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransferFactoryCommodityInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * 海关帐单据logci类，用来写复杂的逻辑方法
 * 
 * 贺巍 于2009年11月5日补充注释
 * 
 */
@SuppressWarnings("unchecked")
public class CasBillLogic {

	/**
	 * 系统日志
	 */
	private static final Log logger = LogFactory.getLog(CasBillLogic.class);

	private CasBillDao casBillDao = null;

	Connection conn = null;

	/**
	 * 类型代码 + ID
	 */
	private Hashtable typeHs = null;// 

	/**
	 * 客户代码 + ID
	 */
	private Hashtable scmHsByCode = null;

	/**
	 * 客户名称 + ID
	 */
	private Hashtable scmHsByName = null;

	/**
	 * 客户名称 + ID
	 */
	private Hashtable scmHsByFName = null;

	/**
	 * 仓库代码 + ID
	 */
	private Hashtable ckHsByCode = null;

	/**
	 * 仓库名称 + ID
	 */
	private Hashtable ckHsByName = null;

	/**
	 * tempNo + ID
	 */
	private Hashtable casHeadHs = null;

	/**
	 * 转厂表头
	 */
	private Hashtable transHeadHs = null;

	/**
	 * 料号 + Obj
	 */
	private Hashtable mtHs = null; // 

	/**
	 * 海关帐DAO类
	 */
	private CasDao casDao = null;

	/**
	 * 数据源
	 */
	private DataSource dataSource = null;

	private EmsEdiTrDao emsEdiTrDao;

	private MaterialManageDao materialManageDao = null; // @jve:decl-index=0:

	/**
	 * 海关报关资料DAO
	 */
	private HsCodeDao hsCodeDao = null;

	/**
	 * 取得casBillDao层的内容
	 * 
	 * @return casBillDao.
	 */
	public CasBillDao getCasBillDao() {
		return casBillDao;
	}

	/**
	 * 设制casBillDao层的内容
	 * 
	 * @param casBillDao
	 */
	public void setCasBillDao(CasBillDao casBillDao) {
		this.casBillDao = casBillDao;
	}

	/**
	 * 删除表体By表头
	 * 
	 * @param billMaster
	 *            单据表头
	 */
	public void deleteBillMaster(BillMaster billMaster) {
		// wss2010.10.08新增：删除在产品期初 单，要反写半成品期初单的备注号
		if ("1002".equals(billMaster.getBillType().getCode())) {
			this.casBillDao.updateBillMaster4009(billMaster.getBillNo());
		}
		this.casBillDao.deleteBillMaster(billMaster);
	}

	/**
	 * 成品出口PK对应
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public void billCorresByPK(Date beginDate, Date endDate) {
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		//
		// 用于存储每个 key = pk号+ptNo(料号) 的对应的报关明细对象
		//
		Map<String, CustomsDeclarationCommInfo> mapCustomsDeclarationCommInfo = new HashMap<String, CustomsDeclarationCommInfo>();
		//
		// 用于存储每个 key = pk号+ptNo(料号) 的对应报关数量
		//
		Map<String, Double> mapAmount = new HashMap<String, Double>();

		List list = casBillDao.findMiddleInfoByPKToCustomType(
				ImpExpType.DIRECT_EXPORT, beginDate, endDate);
		//
		// 生成上面定义的hashMap来自list
		//
		makeMapByListPkOrContainer(mapCustomsDeclarationCommInfo, mapAmount,
				list);

		List<String> pkNoList = new ArrayList<String>();
		List<String> ptNoList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) objs[0];
			String pk = impExpCommodityInfo.getImpExpRequestBill().getBillNo();// pk号
			String ptNo = impExpCommodityInfo.getMateriel() == null ? ""
					: impExpCommodityInfo.getMateriel().getPtNo(); // 料号
			pkNoList.add(pk);
			ptNoList.add(ptNo);
			//
			// 100个参数查询一次
			//
			if ((i % 100) == 0 || i == list.size() - 1) {
				List<BillDetail> listProduct = casBillDao
						.findBillDetailProductbyPk(pkNoList, ptNoList);
				//
				// 生成对应
				//            
				makeBillDetail(mapCustomsDeclarationCommInfo, mapAmount,
						listProduct);
				pkNoList.clear();
				ptNoList.clear();
			}
		}
	}

	/**
	 * 检查临时单据实体类数据
	 * 
	 * @param list
	 * @return
	 */
	public String checkFileData(List list) {
		String temp = "";
		BillTemp fileData = null;
		for (int i = 0; i < list.size(); i++) {
			fileData = (BillTemp) list.get(i);
			if (!checkMaterielPtNo(fileData.getBill6())) {
				temp = temp + "," + fileData.getBill6();
			}
		}
		return temp.substring(1);
	}

	// private Map<String, Materiel> materielMap = null;

	/**
	 * 存放所有的料号的List
	 */
	private List lsAllPtNo = null;

	/**
	 * 检查料号是否存在
	 * 
	 * @param ptNo
	 * @return
	 */
	private boolean checkMaterielPtNo(String ptNo) {
		if (lsAllPtNo == null) {
			lsAllPtNo = casBillDao.findMateriel();
		}
		return lsAllPtNo.contains(ptNo.trim());
	}

	/**
	 * 直接进口单据对应集装箱
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 */
	public void billCorresByContainer(Date beginDate, Date endDate) {
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		//
		// 用于存储每个 key = container号+ptNo(料号) 的对应的报关明细对象
		//
		Map<String, CustomsDeclarationCommInfo> mapCustomsDeclarationCommInfo = new HashMap<String, CustomsDeclarationCommInfo>();
		//
		// 用于存储每个 key = container号+ptNo(料号) 的对应报关数量
		//
		Map<String, Double> mapAmount = new HashMap<String, Double>();

		List list = casBillDao.findMiddleInfoByPKToCustomType(
				ImpExpType.DIRECT_IMPORT, beginDate, endDate); // 进口

		System.out.println(" 1  findMiddleInfoByPKToCustomType list size == "
				+ list.size());

		//
		// 生成上面定义的hashMap来自list
		//
		makeMapByListPkOrContainer(mapCustomsDeclarationCommInfo, mapAmount,
				list);

		System.out.println(" 2  mapCustomsDeclarationCommInfo  size == "
				+ mapCustomsDeclarationCommInfo.size());

		System.out.println(" 3  mapAmount  size == " + mapAmount.size());

		List<TempContainer> tempContainerList = new ArrayList<TempContainer>();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) objs[0];
			String container = impExpCommodityInfo.getImpExpRequestBill()
					.getBillNo();// Container
			// 号
			String ptNo = impExpCommodityInfo.getMateriel() == null ? ""
					: impExpCommodityInfo.getMateriel().getPtNo(); // 料号
			TempContainer tempContainer = new TempContainer();
			tempContainer.setContainerNo(container);
			tempContainer.setCustomsDeclarationNo(null);
			tempContainer.setPtNo(ptNo);
			tempContainerList.add(tempContainer);
			//
			// 100个参数查询一次
			//
			if ((i % 100) == 0 || i == list.size() - 1) {
				//
				// 根据 container 号找报关单号
				//
				List<CustomsDeclarationContainer> listCustomDeclaration = casBillDao
						.findCustomsDeclarationByContainer(tempContainerList);

				System.out
						.println(" 4  findCustomsDeclarationByContainer  listCustomDeclaration size == "
								+ listCustomDeclaration.size());

				for (int j = 0; j < listCustomDeclaration.size(); j++) {
					CustomsDeclarationContainer customs = listCustomDeclaration
							.get(j);
					if (customs.getContainerCode() == null
							|| "".equals(customs.getContainerCode())) {
						continue;
					}
					map.put(customs.getContainerCode(), customs
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode());
				}
				//
				// 设置集装箱对应的报关单号,并把没有的报关单号的删除
				//
				for (int j = 0; j < tempContainerList.size(); j++) {
					TempContainer temp = tempContainerList.get(j);
					temp
							.setCustomsDeclarationNo(map.get(temp
									.getContainerNo()));
					if (temp.getCustomsDeclarationNo() == null
							|| "".equalsIgnoreCase(temp
									.getCustomsDeclarationNo())) {
						tempContainerList.remove(j);
						j--;
					}
				}
				List<BillDetail> listProduct = casBillDao
						.findBillDetailMaterielByContainer(tempContainerList);

				System.out
						.println("5  findBillDetailMaterielByContainer size ---------"
								+ listProduct.size());

				//
				// 生成对应
				//            
				makeBillDetail(mapCustomsDeclarationCommInfo, mapAmount,
						listProduct);
				tempContainerList.clear();
				map.clear();
			}
		}
	}

	/**
	 * 生成单据对应
	 * 
	 * @param mapCustomsDeclarationCommInfo
	 *            映射报关单内容
	 * @param mapAmount
	 *            映射数量
	 * @param listProduct
	 *            成品
	 */
	private void makeBillDetail(
			Map<String, CustomsDeclarationCommInfo> mapCustomsDeclarationCommInfo,
			Map<String, Double> mapAmount, List<BillDetail> listProduct) {
		//
		// 把 key = pk号 + ptNo料号相同的 BillDetailProdut 放入一个list中
		//
		Map<String, List<BillDetail>> mapProduct = new HashMap<String, List<BillDetail>>();
		for (int j = 0; j < listProduct.size(); j++) {
			BillDetail b = (BillDetail) listProduct.get(j);
			//
			// key = pk号 + 料号
			//
			String key = (b.getNote() == null ? "" : b.getNote())
					+ (b.getPtPart() == null ? "" : b.getPtPart());
			if (!mapProduct.containsKey(key)) {
				List<BillDetail> tempList = new ArrayList<BillDetail>();
				tempList.add(b);
				mapProduct.put(key, tempList);
			} else {
				List<BillDetail> tempList = mapProduct.get(key);
				tempList.add(b);
			}
		}
		Iterator<List<BillDetail>> iterator = mapProduct.values().iterator();
		//
		// 遍历存在(料件对报关)一对多
		//
		while (iterator.hasNext()) {
			List<BillDetail> tempList = iterator.next();
			//
			// 有多个单据对应一个 pk + ptNo
			//
			if (tempList.size() > 1) {
				Double sumAmount = 0.0; // 商品总数量
				for (int j = 0; j < tempList.size(); j++) {
					BillDetail b = tempList.get(j);
					sumAmount += b.getPtAmount() == null ? 0.0 : b
							.getPtAmount();
				}
				if (sumAmount > 0.0) {
					for (int j = 0; j < tempList.size(); j++) {
						BillDetail b = tempList.get(j);
						Double ptAmount = b.getPtAmount() == null ? 0.0 : b
								.getPtAmount();
						// key == pk号 + 料号
						String key = (b.getNote() == null ? "" : b.getNote())
								+ (b.getPtPart() == null ? "" : b.getPtPart());
						Double commAmount = mapAmount.get(key) == null ? 0.0
								: mapAmount.get(key); // 报关数量
						Double hsAmount = (ptAmount / sumAmount) * commAmount;
						// hsAmount = CommonUtils.getDoubleByDigit(hsAmount, 2);
						b.setHsAmount(hsAmount);
						b.setCustomNum(hsAmount);
						CustomsDeclarationCommInfo customsDeclarationCommInfo = mapCustomsDeclarationCommInfo
								.get(key);
						System.out.println("key ---------" + key);
						if (customsDeclarationCommInfo == null)
							System.out
									.println("customsDeclarationCommInfo is null");
						if (customsDeclarationCommInfo != null) {
							b.setComplex(customsDeclarationCommInfo
									.getComplex());
							b.setHsName(customsDeclarationCommInfo
									.getCommName());
							b.setHsSpec(customsDeclarationCommInfo
									.getCommSpec());
							b.setSeqNum(customsDeclarationCommInfo
									.getCommSerialNo());
							b.setHsUnit(customsDeclarationCommInfo.getUnit());
							b.setCustomNo(customsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getCustomsDeclarationCode()
									+ "("
									+ customsDeclarationCommInfo
											.getBaseCustomsDeclaration()
											.getEmsHeadH2k() + ")");
							casBillDao.getHibernateTemplate().saveOrUpdate(b);
						}
					}
				}
			} else {// 一对一的pk --> 单据
				BillDetail b = tempList.get(0);
				// key == pk号 + 料号
				String key = (b.getNote() == null ? "" : b.getNote())
						+ (b.getPtPart() == null ? "" : b.getPtPart());
				Double commAmount = mapAmount.get(key) == null ? 0.0
						: mapAmount.get(key); // 报关数量
				b.setHsAmount(commAmount);
				b.setCustomNum(commAmount);
				CustomsDeclarationCommInfo customsDeclarationCommInfo = mapCustomsDeclarationCommInfo
						.get(key);

				System.out.println("key ---------" + key);
				if (customsDeclarationCommInfo == null)
					System.out.println("customsDeclarationCommInfo is null");

				if (customsDeclarationCommInfo != null) {
					b.setComplex(customsDeclarationCommInfo.getComplex());
					b.setHsName(customsDeclarationCommInfo.getCommName());
					b.setHsSpec(customsDeclarationCommInfo.getCommSpec());
					b.setSeqNum(customsDeclarationCommInfo.getCommSerialNo());
					b.setHsUnit(customsDeclarationCommInfo.getUnit());
					b.setCustomNo(customsDeclarationCommInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode()
							+ "("
							+ customsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getEmsHeadH2k() + ")");
					casBillDao.getHibernateTemplate().saveOrUpdate(b);
				}
			}
		}
	}

	/**
	 * 获取料件数量
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, Integer> getMaterielCount(List list) {
		Map<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			Materiel fileData = (Materiel) list.get(i);
			if (hm.get(fileData.getPtNo()) == null) {
				hm.put(fileData.getPtNo(), 1);
			} else {
				hm.put(fileData.getPtNo(), Integer.valueOf(hm.get(
						fileData.getPtNo()).toString()) + 1);
			}
		}
		return hm;
	}

	/**
	 * 生成hashMap 来自pk单或集装箱对象
	 * 
	 * @param mapMiddleInfo
	 *            映射中间信息
	 * @param mapCustomsDeclarationCommInfo
	 *            映射报关单信息
	 * @param mapAmount
	 *            映射数量
	 * @param list
	 *            进出口商品信息列表
	 */
	private void makeMapByListPkOrContainer(
			Map<String, CustomsDeclarationCommInfo> mapCustomsDeclarationCommInfo,
			Map<String, Double> mapAmount, List list) {
		//
		// 临时中间信息 key = 报关明细ID号 + 料号
		//
		Map<String, List<Object[]>> mapMiddleInfo = new HashMap<String, List<Object[]>>();

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) objs[0];
			CustomsDeclarationCommInfo c = (CustomsDeclarationCommInfo) objs[1];
			BaseCustomsDeclaration customsDeclaration = c
					.getBaseCustomsDeclaration();
			// 
			// pk号 or Container
			// 
			String pk = "";
			if (customsDeclaration.getImpExpType() == ImpExpType.DIRECT_IMPORT) {
				pk = customsDeclaration.getCustomsDeclarationCode(); // 用报关单号
				// 代替
				// 集装箱号
			} else {
				pk = impExpCommodityInfo.getImpExpRequestBill().getBillNo();
			}
			String id = c.getId();// 报关明细ID号
			String ptNo = impExpCommodityInfo.getMateriel() == null ? ""
					: impExpCommodityInfo.getMateriel().getPtNo(); // 料号
			String key = id + ptNo;
			if (!mapMiddleInfo.containsKey(key)) {
				List<Object[]> tempList = new ArrayList<Object[]>();
				tempList.add(objs);
				mapMiddleInfo.put(key, tempList);
			} else {
				List<Object[]> tempList = mapMiddleInfo.get(key);
				tempList.add(objs);
			}
			//
			// 对应的报关明细对象
			//
			key = pk + ptNo;
			mapCustomsDeclarationCommInfo.put(key, c);
		}
		Iterator<List<Object[]>> iterator = mapMiddleInfo.values().iterator();
		//
		// 遍历存在(料件对报关)多对一
		//
		while (iterator.hasNext()) {
			List<Object[]> listObject = iterator.next();
			if (listObject.size() > 1) {
				Double sumAmount = 0.0; // 商品总数量
				for (int j = 0; j < listObject.size(); j++) {
					Object[] objs = listObject.get(j);
					ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) objs[0];
					sumAmount += impExpCommodityInfo.getQuantity() == null ? 0.0
							: impExpCommodityInfo.getQuantity();
				}
				if (sumAmount > 0.0) {
					for (int j = 0; j < listObject.size(); j++) {
						Object[] objs = listObject.get(j);
						ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) objs[0];
						CustomsDeclarationCommInfo c = (CustomsDeclarationCommInfo) objs[1];
						BaseCustomsDeclaration customsDeclaration = c
								.getBaseCustomsDeclaration();

						Double quantity = impExpCommodityInfo.getQuantity() == null ? 0.0
								: impExpCommodityInfo.getQuantity();
						//
						// pk号+ptNo(料号)为key 的对应报关数量
						//
						Double unitCommAmount = (quantity / sumAmount)
								* (c.getCommAmount() == null ? 0.0 : c
										.getCommAmount());

						String pk = "";
						if (customsDeclaration.getImpExpType() == ImpExpType.DIRECT_IMPORT) {
							pk = customsDeclaration.getCustomsDeclarationCode(); // 用报关单号
							// 代替
							// 集装箱号
						} else {
							pk = impExpCommodityInfo.getImpExpRequestBill()
									.getBillNo();// pk号
						}

						String ptNo = impExpCommodityInfo.getMateriel() == null ? ""
								: impExpCommodityInfo.getMateriel().getPtNo(); // 料号
						String key = pk + ptNo;
						Double tempUnitCommAmount = mapAmount.get(key);
						if (tempUnitCommAmount == null) {
							mapAmount.put(key, unitCommAmount);
						} else {
							tempUnitCommAmount += unitCommAmount;
							mapAmount.put(key, tempUnitCommAmount);
						}
					}
				}
			} else {// 一对一的pk --> 单据
				Object[] objs = listObject.get(0);
				ImpExpCommodityInfo impExpCommodityInfo = (ImpExpCommodityInfo) objs[0];
				CustomsDeclarationCommInfo c = (CustomsDeclarationCommInfo) objs[1];
				BaseCustomsDeclaration customsDeclaration = c
						.getBaseCustomsDeclaration();
				//
				// pk号+ptNo(料号)为key 的对应报关数量
				//
				Double unitCommAmount = 1.0 * (c.getCommAmount() == null ? 0.0
						: c.getCommAmount());

				String pk = "";
				if (customsDeclaration.getImpExpType() == ImpExpType.DIRECT_IMPORT) {
					pk = customsDeclaration.getCustomsDeclarationCode(); // 用报关单号
					// 代替
					// 集装箱号
				} else {
					pk = impExpCommodityInfo.getImpExpRequestBill().getBillNo();// pk号
				}
				String ptNo = impExpCommodityInfo.getMateriel() == null ? ""
						: impExpCommodityInfo.getMateriel().getPtNo(); // 料号
				String key = pk + ptNo;
				mapAmount.put(key, unitCommAmount);
			}
		}
	}

	/**
	 * 返回工厂商品名称来自单据明细
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 与指定的物料类型等对应工厂商品名称
	 */
	public List<TempObject> findPtNameFromBillDetail(String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findPtNameFromBillDetail(
				materielType, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回工厂商品规格来自单据明细
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 与指定的物料类型等对应工厂商品名称
	 */
	public List<TempObject> findPtSpecFromBillDetail(String materielType,
			int index, int length, String property, Object value,
			boolean isLike, String ptName) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findPtSpecFromBillDetail(
				materielType, index, length, property, value, isLike, ptName);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 从发票里获取料件名称
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List<TempObject> findPtNameFromInvoice(int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findPtNameFromInvoice(index, length,
				property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回实际报关的商品名称来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 */
	public List<TempObject> findHsNameFromStatCusNameRelationHsn(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findHsNameFromStatCusNameRelationHsn(
				materielType, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			Object[] objs = (Object[]) sourceList.get(i);
			if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
				continue;
			}
			TempObject temp = new TempObject();
			temp.setObject((String) objs[0]); // 名称
			temp.setObject1((String) objs[1]);// 规格
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回实际报关的商品名称来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 */
	public List<TempObject> findHsNameFromFactoryAndFactualCustomsRalation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao
				.findHsNameFromFactoryAndFactualCustomsRalation(materielType,
						index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			Object[] objs = (Object[]) sourceList.get(i);
			if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
				continue;
			}
			TempObject temp = new TempObject();
			temp.setObject3((String) objs[3]);
			temp.setObject((String) objs[0]); // 名称
			temp.setObject1((String) objs[1]);// 规格
			temp.setObject2((Unit) objs[2]);// 单位

			list.add(temp);
		}
		return list;
	}

	/**
	 * 查询转厂关封单据中的商品.
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 */
	public List<TempObject> findHsNameFromTransferFactoryBill(boolean isImp,
			int index, int length, String property, Object value, boolean isLike) {

		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = casBillDao.findHsNameFromTransferFactoryBill(isImp,
				index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			Object[] objs = (Object[]) sourceList.get(i);
			if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
				continue;
			}
			TempObject temp = new TempObject();
			temp.setObject3((String) objs[0]);
			temp.setObject((String) objs[1]); // 名称
			temp.setObject1((String) objs[2]);// 规格
			Unit unit = new Unit();
			unit.setName((String) objs[3]);
			temp.setObject2(unit);// 单位

			list.add(temp);
		}
		return list;
	}

	public List<TempObject> findBillDetailProductNo(String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findBillDetailProductNo(materielType,
				index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			Object[] objs = (Object[]) sourceList.get(i);
			if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
				continue;
			}
			TempObject temp = new TempObject();
			temp.setObject((String) objs[0]); // 名称

			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	public List<TempObject> findHsSpecFromStatCusNameRelationHsn(
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findHsSpecFromStatCusNameRelationHsn(
				materielType, hsName, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回报关商品名称规格来自关封
	 * 
	 * @param request
	 *            请求控制
	 * @param cusName
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	public List<TempObject> findHsNameFromCustomsEnvelopCommodityInfo(
			String cusName, String hsName, int index, int length,
			String property, Object value, boolean isImp, boolean isName,
			boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao
				.findHsNameFromCustomsEnvelopCommodityInfo(cusName, hsName,
						index, length, property, value, isImp, isName, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	public List<TempObject> findHsSpecFromFactoryAndFactualCustomsRalation(
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao
				.findHsSpecFromFactoryAndFactualCustomsRalation(materielType,
						hsName, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	public List<TempObject> findHalfProductHsSpec(String materielType,
			String hsName, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findHalfProductHsSpec(materielType,
				hsName, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回商品大类报关规格来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 商品大类报关规格
	 */
	public List<TempObject> findDetailProductHsSpec(String materielType,
			String hsName, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findDetailProductHsSpec(materielType,
				hsName, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 成品名称
	 * 
	 * @param request
	 * @param materielType
	 * @param hsName
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 * @author 石小凯
	 */
	public List<TempObject> findDetailProductHsName(String materielType,
			String hsName, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List<Object[]> sourceList = this.casBillDao.findDetailProductHsName(
				materielType, hsName, index, length, property, value, isLike);
		for (Object[] item : sourceList) {
			TempObject temp = new TempObject();
			temp.setObject(item[0]);
			temp.setObject1(item[1]);
			list.add(temp);
		}
		return list;
	}

	/**
	 * 半成品名称
	 * 
	 * @param request
	 * @param materielType
	 * @param hsName
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 * @author 石小凯
	 */
	public List<TempObject> findDetailMaterielHsName(String materielType,
			String hsName, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List<Object[]> sourceList = this.casBillDao.findDetailMaterielHsName(
				materielType, hsName, index, length, property, value, isLike);
		for (Object[] item : sourceList) {
			TempObject temp = new TempObject();
			temp.setObject(item[0]);
			temp.setObject1(item[1]);
			list.add(temp);
		}
		return list;
	}

	/**
	 * 残次品名称
	 * 
	 * @param request
	 * @param materielType
	 * @param hsName
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 * @author 石小凯
	 */
	public List<TempObject> findDetailRemainProductHsName(String materielType,
			String hsName, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List<Object[]> sourceList = this.casBillDao
				.findDetailRemainProductHsName(materielType, hsName, index,
						length, property, value, isLike);
		for (Object[] item : sourceList) {
			TempObject temp = new TempObject();
			temp.setObject(item[0]);
			temp.setObject1(item[1]);
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回报关商品单位来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param hsName
	 *            报关商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 对照表中的报关商品单位
	 */
	public List<TempObject> findHsUnitFromFactoryAndFactualCustomsRalation(
			String materielType, String hsName, int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao
				.findHsUnitFromFactoryAndFactualCustomsRalation(materielType,
						hsName, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回工厂商品名称来自工厂物料
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称
	 */
	public List<TempObject> findPtNameFromStatCusNameRelationMt(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findPtNameFromStatCusNameRelationMt(
				materielType, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回工厂商品名称来自工厂物料
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称
	 */
	public List<TempObject> findPtNameFromFactoryAndFactualCustomsRalation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao
				.findPtNameFromFactoryAndFactualCustomsRalation(materielType,
						index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回制单号
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称
	 */
	public List<TempObject> findAllWrokOrder(int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		String sql = "select distinct a.productNo from BillDetailMateriel a where a.productNo!=null";
		List sourceList1 = this.casBillDao.find(sql);
		sql = "select distinct a.productNo from BillDetailProduct a where a.productNo!=null";
		List sourceList2 = this.casBillDao.find(sql);
		sql = "select distinct a.productNo from BillDetailHalfProduct a where a.productNo!=null";
		List sourceList3 = this.casBillDao.find(sql);
		sourceList1.addAll(sourceList2);
		sourceList1.addAll(sourceList3);
		List sourceList = new ArrayList();
		for (int i = 0; i < sourceList1.size(); i++) {
			if (!sourceList.contains(sourceList1.get(i)))
				sourceList.add(sourceList1.get(i));
		}
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回工厂商品规格来自工厂物料
	 * 
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称
	 */
	public List<TempObject> findPtSpecFromStatCusNameRelationMt(
			String materielType, String ptName, int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao.findPtSpecFromStatCusNameRelationMt(
				materielType, ptName, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回工厂商品规格来自工厂物料
	 * 
	 * @param materielType
	 *            物料类型
	 * @param ptName
	 *            工厂商品名称
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 工厂商品名称
	 */
	public List<TempObject> findPtSpecFromFactoryAndFactualCustomsRalation(
			String materielType, String ptName, int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao
				.findPtSpecFromFactoryAndFactualCustomsRalation(materielType,
						ptName, index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 临时保存单据明细
	 */
	public void tempSave() {
		List<BillDetail> list = this.casBillDao.findBillDetails(
				MaterielType.MATERIEL, 0, 1, "1003");
		if (list.size() > 0) {
			System.out.println("i*j*size");
			BillDetail bd = list.get(0);
			BillMaster bm = bd.getBillMaster();
			for (int size = 0; size < 30; size++) {
				System.out.println(size + " 万");
				for (int i = 0; i < 100; i++) {
					bm.setId(null);
					this.casBillDao.getHibernateTemplate().save(bm);
					for (int j = 0; j < 100; j++) {
						// System.out.println(i*j*size);
						bd.setBillMaster(bm);
						bd.setId(null);
						this.casBillDao.getHibernateTemplate().save(bd);
					}
				}
			}
		}
	}
	/**
	 * 显示单据资料明细表来自表头id和单据类型
	 * 
	 * @param request
	 *            请求控制
	 * @param masterId
	 *            表头id
	 * @param sBillType
	 *            单据类型
	 * @return 工厂单据明细
	 */
	public List<BillDetail> findBillDetail(String masterId, int sBillType) {
		List<BillDetail> tempBill = this.casBillDao.findBillDetail(masterId,
				sBillType);
		List<BillDetail> billDetailLits = new ArrayList<BillDetail>();
		for (int i = 0; i < tempBill.size(); i++) {
			// 单净重=净重/数量
			BillDetail billDetail = tempBill.get(i);
			if (billDetail.getPtAmount() != null
					&& billDetail.getPtAmount() != Double.valueOf(0)
					&& billDetail.getNetWeight() != null) {
				billDetail.setInNetWeight(CaleUtil.dividend(billDetail
						.getNetWeight(), billDetail.getPtAmount(), 6));
			} else {
				billDetail.setInNetWeight(Double.valueOf(0));
			}
			billDetailLits.add(billDetail);
		}
		return billDetailLits;
	}
	/**
	 * 获取海关资料类型
	 */
	private void getTypeHs() {
		typeHs = new Hashtable();
		List list = this.casDao.findBillType();
		// List list = this.casDao.findBillType(SBillType.MATERIEL_INOUT);
		for (int i = 0; i < list.size(); i++) {
			BillType obj = (BillType) list.get(i);
			if (obj != null && obj.getCode() != null
					&& !obj.getCode().equals("")) {
				typeHs.put(obj.getCode(), obj.getId());
			}
		}
		// list = this.casDao.findBillType(SBillType.PRODUCE_INOUT);
		// for (int i = 0; i < list.size(); i++) {
		// BillType obj = (BillType) list.get(i);
		// if (obj != null && obj.getCode() != null
		// && !obj.getCode().equals("")) {
		// typeHs.put(obj.getCode(), obj.getId());
		// }
		// }
	}

	/**
	 * 获取客户供应商
	 */
	private void getScmCoc() {
		scmHsByCode = new Hashtable();
		scmHsByName = new Hashtable();
		scmHsByFName = new Hashtable();
		List list = this.casDao.findScmcoc();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc obj = (ScmCoc) list.get(i);
			if (obj != null && obj.getCode() != null
					&& !obj.getCode().equals("")) {
				scmHsByCode.put(obj.getCode(), obj.getId());
			}
			if (obj != null && obj.getName() != null
					&& !obj.getName().equals("")) {
				scmHsByName.put(obj.getName(), obj.getId());
			}
			if (obj != null && obj.getFname() != null
					&& !obj.getFname().equals("")) {
				scmHsByFName.put(obj.getFname(), obj.getId());
			}
		}
	}

	/**
	 * 获取仓库
	 */
	private void getWareSet() {
		ckHsByCode = new Hashtable();
		ckHsByName = new Hashtable();
		List list = this.casDao.findWareSet();
		for (int i = 0; i < list.size(); i++) {
			WareSet obj = (WareSet) list.get(i);
			if (obj != null && obj.getCode() != null
					&& !obj.getCode().equals("")) {
				ckHsByCode.put(obj.getCode(), obj.getId());
			}
			if (obj != null && obj.getName() != null
					&& !obj.getName().equals("")) {
				ckHsByName.put(obj.getName(), obj.getId());
			}
		}
	}

	/**
	 * 获取海关帐物料表头
	 * 
	 * @param classtable
	 */
	private void getCasHead(String classtable) {
		casHeadHs = new Hashtable();
		List list = this.casDao.findCasMasterMateriel(classtable);
		for (int i = 0; i < list.size(); i++) {
			BillMaster obj = (BillMaster) list.get(i);
			if (obj != null && obj.getTempNo() != null
					&& !obj.getTempNo().equals("")) {
				casHeadHs.put(obj.getTempNo(), obj.getId());
			}
		}
	}

	/**
	 * 获得对应类型的单据表头
	 * 
	 * @param tablename
	 * @return
	 */
	public List getCasHeadForImport(String tablename) {
		List returnlist = new ArrayList();
		List list = this.casDao.findCasMasterMateriel(tablename);
		for (int i = 0; i < list.size(); i++) {
			BillMaster obj = (BillMaster) list.get(i);
			if (obj != null) {
				String errorMessage = "单据号 == "
						+ (obj.getBillNo() == null ? "" : obj.getBillNo());
				try {
					//
					// yyyy-MM-dd default
					//
					String validDate = obj.getValidDate() == null ? "null"
							: obj.getValidDate().toString();

					if (obj.getBillType() == null) {
						errorMessage += " 单据类型为空";
					}
					if (obj.getValidDate() == null) {
						errorMessage += " 日期为空";
					}

					// System.out.println("------------ "+ validDate);

					String tempno = obj.getBillNo()
							+ obj.getBillType().getCode()
							+ validDate
							+ (obj.getScmCoc() == null ? "null" : obj
									.getScmCoc().getCode());
					returnlist.add(tempno);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage() + "\n"
							+ errorMessage, e);

				}
			}
		}
		return returnlist;
	}

	/**
	 * 按照单据导入的设定，返回一个客户HASHMAP
	 * 
	 * @param isName
	 * @return
	 */
	public HashMap getScmCocForImport(String isName) {
		HashMap map = new HashMap();
		List list = this.casDao.findScmcoc();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc obj = (ScmCoc) list.get(i);

			if (obj != null && obj.getCode() != null
					&& (obj.getName() != null && !obj.getName().equals(""))) {
				if (isName.equals("名称全称")) {
					map.put(obj.getName(), obj.getCode());
				} else if ("名称简称".equals(isName)) {
					map.put(obj.getFname(), obj.getCode());
				} else {
					map.put(obj.getCode(), obj.getCode());
				}
			}
		}
		return map;
	}

	// // 转厂单据表头
	// private void getTransHead() {
	// transHeadHs = new Hashtable();
	// List list = this.casDao.findTransHead();
	// for (int i = 0; i < list.size(); i++) {
	// FptBillHead obj = (FptBillHead) list.get(i);
	// if (obj != null && obj.getTempNo() != null
	// && !obj.getTempNo().equals("")) {
	// transHeadHs.put(obj.getTempNo(), obj.getId());
	// }
	// }
	// }

	/**
	 * 获取物料表头
	 */
	private void getMtHead(String typename) {// wss:2010.04.28修改
		mtHs = new Hashtable();
		String type = "";
		if (typename != null && typename.equals("料件")) {
			type = MaterielType.MATERIEL;
		} else if (typename != null && typename.equals("成品")) {
			type = MaterielType.FINISHED_PRODUCT;
		} else if (typename != null && typename.equals("设备")) {
			type = MaterielType.MACHINE;
		} else if (typename != null && typename.equals("半成品")) {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
		} else if (typename != null && typename.equals("残次品(料件)")) {
			type = MaterielType.MATERIEL;
		} else if (typename != null && typename.equals("残次品(成品)")) {
			type = MaterielType.FINISHED_PRODUCT;
		} else if (typename != null && typename.equals("边角料")) {
			type = MaterielType.REMAIN_MATERIEL;
		}
		List list = this.casDao.findAllMateriel(type);
		for (int i = 0; i < list.size(); i++) {
			Materiel obj = (Materiel) list.get(i);
			if (obj != null && obj.getPtNo() != null
					&& !obj.getPtNo().equals("")) {
				mtHs.put(obj.getPtNo().trim(), obj);
			}
		}
	}

	/**
	 * 海关帐单据文本固定格式导入(暂无处理重复记录)
	 * 
	 * @param headList
	 * @param detailList
	 * @param paramerMap
	 * @throws SQLException
	 */
	public void executeBillImport(List headList, List detailList,
			Hashtable paramerMap) throws SQLException {
		String casYear = CommonUtils.getYear(); // 海关帐年份
		if (casYear.equals("2005")) {
			casYear = "";
		}
		String stableClass = null;
		String stableClassD = null;
		String stable = null;
		String stableD = null;
		String slj = (paramerMap == null ? "" : (String) paramerMap.get(5));
		boolean isTrue = (Boolean) paramerMap.get("isValid");
		String scmCoi = null;

		if ("料件".equals(slj)) {
			stableClass = "BillMasterMateriel";
			stableClassD = "BillDetailMateriel";
			scmCoi = "料件";
		} else if ("成品".equals(slj)) {
			stableClass = "BillMasterProduct";
			stableClassD = "BillDetailProduct";
			scmCoi = "成品";
		} else if ("设备".equals(slj)) {
			stableClass = "BillMasterFixture";
			stableClassD = "BillDetailFixture";
			scmCoi = "设备";
		} else if ("半成品".equals(slj)) {
			stableClass = "BillMasterHalfProduct";
			stableClassD = "BillDetailHalfProduct";
			scmCoi = "半成品";
		} else if (slj != null && slj.indexOf("残次品") > -1) {
			stableClass = "BillMasterRemainProduct";
			stableClassD = "BillDetailRemainProduct";
			scmCoi = slj;
		} else if ("边角料".equals(slj)) {
			stableClass = "BMLMateriel";
			stableClassD = "BDLMateriel";
			scmCoi = "边角料";
		}

		Boolean isHalf = false;
		if (("半成品".equals(slj)) || "残次品(半成品)".equals(slj)) {
			isHalf = true;
		}

		// 料号和对应对应关系资料（半成品为Materiel）。
		// 当导入数据没有报关资料时以料号为key
		Map<String, Object[]> mapPtNoHs = new HashMap<String, Object[]>();
		Map<String, Object[]> mapHsHs = new HashMap<String, Object[]>();
		Map<String, Object[]> mapHsHsEmsNo = new HashMap<String, Object[]>();
		if (isHalf) {
			mapPtNoHs = findPtNoAndCodeInMateriel(scmCoi);
			// 当导入资料有报关资料存在时就以料号+报关名称+规格
			mapHsHs = findHsDataByMateriel(scmCoi);
		} else {
			mapPtNoHs = findPtNoAndCodeInStatCusNameRelation(scmCoi);
			// 当导入资料有报关资料存在时就以料号+报关名称+规格+单位
			mapHsHs = findHsDataByStatCusNameRelation(scmCoi, false);
			// 当导入资料有报关资料存在时就以料号+报关名称+规格+单位+手册号
			mapHsHsEmsNo = findHsDataByStatCusNameRelation(scmCoi, true);
		}

		// 查找海关帐基本单据项目设定
		// CasBillOption casBillOption = this.casBillDao.findCasBillOption();

		stable = stableClass + casYear;
		stableD = stableClassD + casYear;

		getTypeHs();// 单据类型
		getScmCoc();// 客户供应商
		getWareSet();// 仓库

		// 初始化类型
		Hashtable hsBillType = this.casDao.findCodeAndFactryNameFromBillType();
		try {
			conn = dataSource.getConnection();
			String insertSql = getInsertSqlCasHead(stable, isTrue);
			PreparedStatement stmtInsert = conn.prepareStatement(insertSql);
			System.out
					.println("[JBCUS]          begin insert table casHead time:"
							+ new Date());
			// 在插入表头之前，先去查找一下表头，看单据头的TEMPNO里面存不存在param的key
			if (!scmCoi.equals("边角料"))
				getCasHead(stableClass);
			else
				getCasHead("BillMasterLeftoverMateriel");
			for (int i = 0; i < headList.size(); i++) {
				BillTemp param = (BillTemp) headList.get(i);
				setParameters(stmtInsert, param, paramerMap, hsBillType);
				stmtInsert.addBatch();
				if ((i != 0 && (i % 1000) == 0) || i == headList.size() - 1) {
					stmtInsert.executeBatch();
					stmtInsert.clearBatch();
				}
			}
			stmtInsert.close();
			System.out.println("[JBCUS]          end insert table casHead time:"
							+ new Date());
			if (!scmCoi.equals("边角料"))
				getCasHead(stableClass);
			else
				getCasHead("BillMasterLeftoverMateriel");
			getMtHead(scmCoi);
			insertSql = getInsertSqlCasDetail(stableD);
			PreparedStatement stmtInsertForD = conn.prepareStatement(insertSql);
			System.out.println("[JBCUS]          begin insert table casDetail time:"
							+ new Date());
			for (int i = 0; i < detailList.size(); i++) {
				BillTemp billTemp = (BillTemp) detailList.get(i);
				setParametersForD(stmtInsertForD, billTemp, paramerMap,
						mapPtNoHs, mapHsHs, mapHsHsEmsNo, hsBillType);
				stmtInsertForD.addBatch();
				if ((i != 0 && (i % 1000) == 0) || i == detailList.size() - 1) {
					stmtInsertForD.executeBatch();
					stmtInsertForD.clearBatch();
				}
			}
			stmtInsertForD.close();
			System.out
					.println("[JBCUS]          end insert table casDetail time:"
							+ new Date());
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置参数和绑定数据
	 * 
	 * @param stmt
	 * @param billTemp
	 * @param paramerMap
	 * @param mapPtNoHs
	 * @param hsBillType
	 * @throws SQLException
	 */
	private void setParametersForD(PreparedStatement stmt, BillTemp billTemp,
			Hashtable paramerMap, Map<String, Object[]> mapPtNoHs,
			Map<String, Object[]> mapHsHs, Map<String, Object[]> mapHsHsEmsNo,
			Hashtable hsBillType) throws SQLException {
		String key = (billTemp.getBill1() == null ? "" : billTemp.getBill1())
				+ "/"
				+ (billTemp.getBill2() == null ? "" : billTemp.getBill2())
				+ "/"
				+ (billTemp.getBill3() == null ? "" : billTemp.getBill3())
				+ "/"
				+ (billTemp.getBill4() == null ? "" : billTemp.getBill4())
				+ "/"
				+ (billTemp.getBill5() == null ? "" : billTemp.getBill5())
				+ "/" + billTemp.getBill100();

		System.out.println("==============key" + casHeadHs.get(key));
		stmt.setObject(1, casHeadHs.get(key));// 表头
		Materiel mt = billTemp.getBill6() == null ? null : (Materiel) mtHs
				.get(billTemp.getBill6());

		Object[] objsPtNoHs = mapPtNoHs.get(billTemp.getBill6());

		// 工厂名称
		stmt.setObject(2, mt == null ? null : mt.getFactoryName());

		// 工厂规格
		stmt.setObject(3, mt == null ? null : mt.getFactorySpec());

		// 工厂单位
		stmt.setObject(4, mt == null ? null : (mt.getCalUnit() == null ? null
				: mt.getCalUnit().getId()));

		// 料号
		stmt.setObject(5, billTemp.getBill6());

		// 数量
		double amount = 0.0;
		if (billTemp.getBill9() == null || billTemp.getBill9().equals("")) {
			amount = Double.valueOf(0.0);
			stmt.setObject(6, amount);
		} else {
			amount = Double.parseDouble(billTemp.getBill9());
			stmt.setObject(6, amount);
		}

		// 工厂单价
		double price = 0.0;
		if (billTemp.getBill10() == null || billTemp.getBill10().equals("")) {
			price = Double.valueOf(0.0);
			if (objsPtNoHs == null) {
				if (mt != null) {
					price = mt.getPtPrice() == null ? 0.0 : mt.getPtPrice();
				}
			} else {
				Double dou = Double.valueOf(objsPtNoHs[6] == null ? "0.0"
						: objsPtNoHs[6].toString());
				price = dou;// 工厂单价
			}

			stmt.setObject(7, price);
		} else {
			price = Double.parseDouble(billTemp.getBill10());
			stmt.setObject(7, price);
		}

		// 版本号
		if (billTemp.getBill11() == null || billTemp.getBill11().equals("")) {
			stmt.setObject(8, 0);
		} else {
			stmt.setObject(8, Integer.valueOf(billTemp.getBill11()));
		}

		// 公司
		stmt.setObject(9, CommonUtils.getCompany().getId());

		// 仓库
		String ck = (paramerMap == null ? "" : (String) paramerMap.get(2));
		String sck = billTemp.getBill12();
		if (sck != null && !sck.equals("")) {
			if (ck != null && ck.equals("名称")) {
				sck = (String) ckHsByName.get(billTemp.getBill12());
			} else {
				sck = (String) ckHsByCode.get(billTemp.getBill12());
			}
			stmt.setObject(10, sck);
		} else {
			stmt.setObject(10, null);
		}

		// 制单号 -- 订单号
		stmt.setObject(11, billTemp.getBill13());

		// 净重
		if (billTemp.getBill7() == null || "".equals(billTemp.getBill7())) {
			double netWeight = Double.valueOf(0.0);
			Double du = Double.parseDouble(billTemp.getBill9() == null ? "0.0"
					: billTemp.getBill9());// 数量
			if (objsPtNoHs == null) {
				if (mt != null) {
					netWeight = mt.getPtNetWeight() == null ? 0.0 : mt
							.getPtNetWeight();
				}
			} else {
				// objs[7]为净重
				Double dou = Double.parseDouble(objsPtNoHs[7] == null ? "0.0"
						: objsPtNoHs[7].toString());// 单位净重
				BigDecimal d = new BigDecimal(du * dou);
				netWeight = d.setScale(3, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
			}
			stmt.setObject(12, netWeight);
		} else {
			stmt.setObject(12, Double.parseDouble(billTemp.getBill7()));
		}

		// 对应报关单号
		if ((paramerMap.get("isInputCustoms") != null)
				&& Boolean.parseBoolean(paramerMap.get("isInputCustoms")
						.toString())) {
			stmt.setObject(13, billTemp.getBill17());
		} else {
			stmt.setNull(13, java.sql.Types.VARCHAR);
		}

		// 设置总价
		stmt.setObject(14, price * amount);

		// String sstype = (String) hsBillType.get(stype);

		String type = paramerMap == null ? "" : (String) paramerMap.get(5);

		// 是否是半成品
		Boolean isHalf = false;
		if (("半成品".equals(type) || "残次品(半成品)".equals(type))) {
			isHalf = true;
		}

		//
		// edit by luosheng
		// 文本导入目前对报关名称栏位的处理是默认抓取对应关系中的第一个报关名称，建议修正为如下：
		// 1。当文本中报关名称为空时默认为对应关系中的第一个报关名称
		// 2。当文本中报关名称有数据时，以文本中的报关名称来导入
		// 所以只用报关名称来存在,就修改
		//
		boolean isExistHsName = true;
		if (billTemp.getBill18() == null
				|| "".equals(billTemp.getBill18().trim())) {
			isExistHsName = false;
		}

		// System.out.println("wss isExistHsName:" + isExistHsName);

		// 存在报关名称
		if (isExistHsName) {

			String hsKey1 = (billTemp.getBill6() == null ? "" : billTemp
					.getBill6())
					+ "/"// 料号
					+ (billTemp.getBill18() == null ? "" : billTemp.getBill18())
					+ "/" // 报关名称
					+ (billTemp.getBill19() == null ? "" : billTemp.getBill19())
					+ "/" // 报关规格
					+ (billTemp.getBill20() == null ? "" : billTemp.getBill20()); // 报关单位

			String emsNo = billTemp.getEmsNo() == null ? "" : billTemp
					.getEmsNo();

			Object[] objs = null;
			// 当手册号为空时
			if (emsNo == null || "".equals(emsNo)) {
				if (isHalf) {// 当为半成品或残次品
					hsKey1 = (billTemp.getBill6() == null ? "" : billTemp
							.getBill6())
							+ "/"// 料号
							+ (billTemp.getBill18() == null ? "" : billTemp
									.getBill18())
							+ "/" // 报关名称
							+ (billTemp.getBill19() == null ? "" : billTemp
									.getBill19());// 报关规格
				}
				objs = mapHsHs.get(hsKey1);
			} else {
				// 当不为空时
				hsKey1 += "/" + emsNo;
				objs = mapHsHsEmsNo.get(hsKey1);
			}
			if (objs != null) {
				Unit unit = (Unit) objs[4];
				stmt.setObject(15, objs[1]);// 实际报关资料商品编码id
				stmt.setObject(16, objs[2]);// 实际报关资料名称
				stmt.setObject(17, objs[3]);// 实际报关资料规格
				stmt.setObject(18, unit == null ? null : unit.getCode());// 实际报关资料单位
				if (!isHalf) {
					stmt.setObject(19, objs[8]);// 手册号
				} else {
					stmt.setObject(19, billTemp.getEmsNo());// 手册号
				}
				stmt.setObject(20, objs[5]);// 折算比例
				// 折算报关数量
				if (billTemp.getBill14() == null
						|| "".equals(billTemp.getBill14())) {
					double unitCover = 0.0;
					Double du = Double
							.parseDouble(billTemp.getBill9() == null ? "0.0"
									: billTemp.getBill9());
					if (objs != null) {
						Double dou = Double.parseDouble(objs[5] == null ? "0.0"
								: objs[5].toString());
						unitCover = du * dou;
					}
					stmt.setObject(21, unitCover);
				} else {
					stmt.setObject(21, billTemp.getBill14());
				}
				// 海关单价
				if (billTemp.getBill16() != null
						&& !billTemp.getBill16().equals("")) {
					double pricetemp=0.0;
					try{
						pricetemp=Double.parseDouble(billTemp.getBill16().trim());
					}catch(Exception ex){
						ex.printStackTrace();
					}
					stmt.setObject(23, pricetemp);// 海关单价

				} else {
					double customPrice = 0.0;
					if (objs != null) {
						Double val = Double
								.parseDouble(billTemp.getBill10() == null ? "0.0"
										: billTemp.getBill10());
						Double dp = Double.parseDouble(objs[5] == null ? "0.0"
								: objs[5].toString());
						customPrice = ((dp == 0.0) ? val : (val / dp));// 海关单价
					}
					stmt.setObject(23, customPrice);
				}
			} else {
				stmt.setObject(15, null);// 实际报关资料商品编码
				stmt.setObject(16, null);// 实际报关资料名称
				stmt.setObject(17, null);// 实际报关资料规格
				stmt.setObject(18, null);// 实际报关资料单位
				stmt.setObject(19, null);// 手册号
				stmt.setObject(20, null);// 折算比例
				stmt.setObject(21, null);// 折算报关数量
				stmt.setObject(23, null);// 海关单价
			}

		}

		// 如果没有填报关资料，则只抓相应料号 的 第一条对应关系折算系数最大(或Materiel)
		else {
			Object[] objs = mapPtNoHs.get(billTemp.getBill6());
			if (objs != null) {
				Unit unit = (Unit) objs[4];
				stmt.setObject(15, objs[1]);// 实际报关资料商品编码
				stmt.setObject(16, objs[2]);// 实际报关资料名称
				stmt.setObject(17, objs[3]);// 实际报关资料规格
				stmt.setObject(18, unit.getCode());// 实际报关资料单位

				if (!isHalf) {
					stmt.setObject(19, objs[8]);// 手册号
				} else {
					stmt.setObject(19, billTemp.getEmsNo());// 手册号
				}

				stmt.setObject(20, objs[5]);// 折算比例
				Double dou = Double.valueOf(objs[5] == null ? "1.0" : objs[5]
						.toString());// 获得实际报关资料中单位折算

				double noCustomNum = amount * dou;

				// 折算报关单数量
				if (billTemp.getBill14() != null) {
					stmt.setObject(21, billTemp.getBill14());
				} else {
					stmt.setObject(21, noCustomNum);
				}

				// 海关单价
				double hsprice = (dou==0.0?0.0:price / dou);
				if (billTemp.getBill16() != null) {
					if(!"".equals(billTemp.getBill16().trim())){
						double pricetemp=0.0;
						try{
							pricetemp=Double.parseDouble(billTemp.getBill16().trim());
						}catch(Exception ex){
							ex.printStackTrace();
						}
						stmt.setObject(23, pricetemp);
					}else{
						stmt.setObject(23, null);
					}
				}else{
					stmt.setObject(23, hsprice);
				}
			} else {
				stmt.setObject(15, null);// 实际报关资料商品编码
				stmt.setObject(16, null);// 实际报关资料名称
				stmt.setObject(17, null);// 实际报关资料规格
				stmt.setObject(18, null);// 实际报关资料单位
				stmt.setObject(19, null);// 手册号
				stmt.setObject(20, null);// 折算比例
				stmt.setObject(21, null);// 折算报关数量
				stmt.setObject(23, null);// 海关单价
			}
		}
		// 对应报关数量
		if (billTemp.getBill15() != null && !billTemp.getBill15().equals("")) {
			stmt.setObject(22, Double.parseDouble(billTemp.getBill15()));
		} else {
			stmt.setObject(22, null);// 对应报关数量
//			stmt.setNull(22, java.sql.Types.DOUBLE);
		}

		// 单体备注
		if (billTemp.getNote() != null && !billTemp.getNote().equals("")) {
			stmt.setObject(24, billTemp.getNote());
		} else {
			stmt.setNull(24, java.sql.Types.VARCHAR);
		}

		// 送货单号
		if (billTemp.getTakeBillNo() != null
				&& !billTemp.getTakeBillNo().equals("")) {
			// System.out.println(" billTemp.getTakeBillNo = "
			// + billTemp.getTakeBillNo());
			stmt.setObject(25, billTemp.getTakeBillNo());
		} else {
			stmt.setNull(25, java.sql.Types.VARCHAR);
		}

		// 订单号
		if (billTemp.getOrderNo() != null && !billTemp.getOrderNo().equals("")) {
			// System.out.println(" billTemp.getOrderNo = "
			// + billTemp.getOrderNo());
			stmt.setObject(26, billTemp.getOrderNo());
		} else {
			stmt.setNull(26, java.sql.Types.VARCHAR);
		}
	}

	/**
	 * 设置参数已经绑定数据
	 * 
	 * @param stmt
	 * @param param
	 * @param paramerMap
	 * @param hsBillType
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement stmt, BillTemp param,
			Hashtable paramerMap, Hashtable hsBillType) throws SQLException {
		// 单据类型
		String stype = param.getBill1();
		String sstype = (String) hsBillType.get(stype);

		if (sstype != null) {
			stype = sstype;
		}
		stmt.setObject(1, typeHs.get(stype.trim()));
		// System.out.println("单据类型=" + typeHs.get(stype.trim()));
		stmt.setObject(2, param.getBill2());
		// System.out.println("单据号码=" + param.getBill2());
		// 生效日期
		String isvdate = (paramerMap == null ? "" : (String) paramerMap.get(3));
		String d = param.getBill4();
		// System.out.println("生效日期=" + param.getBill4());
		if (isvdate != null && isvdate.equals("yyyyMMdd")) {
			if (d != null && d.length() == 8) {
				d = d.substring(0, 4) + "-" + d.substring(4, 6) + "-"
						+ d.substring(6, 8);
			}
		} else if (isvdate != null && isvdate.equals("yyyy/MM/dd")) {
			if (d != null && d.length() == 10) {
				d = d.replace("/", "-");
			}
		}
		// 这样就不会有日期问题
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date date = new Date();
		try {
			date = bartDateFormat.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		stmt.setObject(3, new java.sql.Date(date.getTime()));

		// 客户供应商
		String scm = (paramerMap == null ? "" : (String) paramerMap.get(1));
		String sscmcoc = param.getBill3();
		// System.out.println("客户供应商=" + param.getBill3() + "csm=" + scm);
		if (sscmcoc != null && !sscmcoc.equals("")) {
			if (scm != null && scm.equals("名称全称")) {
				sscmcoc = (String) scmHsByName.get(param.getBill3());
			} else if (scm != null && scm.equals("名称简称")) {
				sscmcoc = (String) scmHsByFName.get(param.getBill3());
			} else {
				sscmcoc = (String) scmHsByCode.get(param.getBill3());
			}
			stmt.setObject(4, sscmcoc);
		} else {
			stmt.setObject(4, null);
		}
		// System.out.println("备注=" + param.getBill5());
		// 公司

		stmt.setObject(5, CommonUtils.getCompany().getId());
		String key = (param.getBill1() == null ? "" : param.getBill1()) + "/"
				+ (param.getBill2() == null ? "" : param.getBill2()) + "/"
				+ (param.getBill3() == null ? "" : param.getBill3()) + "/"
				+ (param.getBill4() == null ? "" : param.getBill4()) + "/"
				+ (param.getBill5() == null ? "" : param.getBill5()) + "/"
				+ param.getBill100();
		stmt.setObject(6, key);

		stmt.setObject(7, param.getEnvelopNo());// 关封号
		stmt.setObject(8, param.getBill5());// 备注
	}

	/**
	 * 插入数据到海关帐单据明细
	 * 
	 * @param stableD
	 * @return
	 */
	private String getInsertSqlCasDetail(String stableD) {
		String idstr = "replace(newid(),'-','')";
		DatabaseMetaData dmd;
		try {
			dmd = conn.getMetaData();
			String driverName = dmd.getDriverName().toLowerCase();
			if (driverName.indexOf("sql") >= 0
					&& driverName.indexOf("server") >= 0) { // SqlServer数据库
				idstr = "replace(newid(),'-','')";
			} else if (driverName.indexOf("oracle") >= 0) { // Oracle数据库
				idstr = "sys_guid()";
			} else if (driverName.indexOf("mysql") >= 0) { // mysql数据库
				idstr = "replace(newid(),'-','')";
			}
		} catch (SQLException e) {
		}

		String sql = "insert into "
				+ stableD
				+ " (id,billMasterId,ptName,ptSpec,ptUnitId,ptPart,ptAmount,"
				+ "ptPrice,version,coid,wareSet,productNo,netWeight,customNo,"
				+ "money,complexid,hsName,hsSpec,hsUnitId,emsNo,unitConvert,hsAmount,customNum,hsPrice,"
				+ "note,takeBillNo,oderBillNo) values " + "(" + idstr
				+ ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println("sql:" + sql);
		return sql;
	}

	/**
	 * 获取插入的数据从表头
	 * 
	 * @param stable
	 * @param isTrue
	 * @return
	 */
	private String getInsertSqlCasHead(String stable, boolean isTrue) {
		String idstr = "replace(newid(),'-','')";
		DatabaseMetaData dmd;
		try {
			dmd = conn.getMetaData();
			String driverName = dmd.getDriverName().toLowerCase();
			if (driverName.indexOf("sql") >= 0
					&& driverName.indexOf("server") >= 0) { // SqlServer数据库
				idstr = "replace(newid(),'-','')";
			} else if (driverName.indexOf("oracle") >= 0) { // Oracle数据库
				idstr = "sys_guid()";
			} else if (driverName.indexOf("mysql") >= 0) { // mysql数据库
				idstr = "replace(newid(),'-','')";
			}
		} catch (SQLException e) {
		}
		String isVaild = isTrue ? "1" : "0";
		String sql = "insert into "
				+ stable
				+ " (id,billTypeId,billNo,isValid,validDate,scmCoc,coid,tempNo,envelopNo,notice) values "// wss2010.08.31添加关封号
				+ "(" + idstr + ",?,?,'" + isVaild + "',?,?,?,?,?,?)";
		System.out.println("sql:" + sql);
		return sql;
	}

	/**
	 * 保存发票单据导入数据
	 * 
	 * @param headList
	 * @param detailList
	 * @param taskId
	 * @throws SQLException
	 */
	public void importDataToCasInvoice(List headList, List detailList,
			String taskId) throws SQLException {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(headList.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在保存表头,请稍候...");
		}
		conn = dataSource.getConnection();
		String idstr = "replace(newid(),'-','')";
		DatabaseMetaData dmd;
		try {
			dmd = conn.getMetaData();
			String driverName = dmd.getDriverName().toLowerCase();
			if (driverName.indexOf("sql") >= 0
					&& driverName.indexOf("server") >= 0) { // SqlServer数据库
				idstr = "replace(newid(),'-','')";
			} else if (driverName.indexOf("oracle") >= 0) { // Oracle数据库
				idstr = "sys_guid()";
			} else if (driverName.indexOf("mysql") >= 0) { // mysql数据库
				idstr = "replace(newid(),'-','')";
			}
		} catch (SQLException e) {
		}
		String headSql = "insert into casinvoice(id,invoiceNo,validDate,emsNo,canceled,customer,tempNo,coid) "
				+ "values(" + idstr + ",?,?,?,?,?,?,?)";
		PreparedStatement stmtInsertM = conn.prepareStatement(headSql);
		for (int i = 0; i < headList.size(); i++) {
			BillTemp item = (BillTemp) headList.get(i);
			setValueForMaster(stmtInsertM, item);
			stmtInsertM.addBatch();
			if ((i != 0 && (i % 1000) == 0) || i == headList.size() - 1) {
				stmtInsertM.executeBatch();
				stmtInsertM.clearBatch();
			}
			if (info != null) {
				info.setCurrentNum(i + 1);
			}
		}
		stmtInsertM.close();
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(detailList.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在保存表体,请稍候...");
		}
		String detailSql = "insert into casinvoiceinfo(id,casinvoice,complex,cuname,cuspec,unit,"
				+ "amount,price,totalWeight,canceledNum,coid,totalMoney,seqNum,canceled) "
				+ "values(" + idstr + ",?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		System.out.println("+++++insertsql detail:" + detailSql);
		PreparedStatement stmtInsertD = conn.prepareStatement(detailSql);
		for (int i = 0; i < detailList.size(); i++) {
			BillTemp item = (BillTemp) detailList.get(i);
			setValueForDetail(stmtInsertD, item);
			stmtInsertD.addBatch();
			if ((i != 0 && (i % 1000) == 0) || i == detailList.size() - 1) {
				stmtInsertD.executeBatch();
				stmtInsertD.clearBatch();
			}
			if (info != null) {
				info.setCurrentNum(i + 1);
			}
		}
		stmtInsertD.close();
	}

	/**
	 * 设置表头的栏位值
	 * 
	 * @param stmt
	 * @param item
	 * @throws SQLException
	 */
	private void setValueForMaster(PreparedStatement stmt, BillTemp item)
			throws SQLException {
		String key = (item.getBill1() == null ? "" : item.getBill1()) + "/"
				+ (item.getBill2() == null ? "" : item.getBill2()) + "/"
				+ (item.getBill23() == null ? "" : item.getBill23()) + "/"
				+ (item.getBill4() == null ? "" : item.getBill4()) + "/"
				+ (item.getBill5() == null ? "" : item.getBill5());
		stmt.setObject(1, item.getBill1());// 发票号
		stmt.setObject(2, item.getBill2());// 发票日期
		stmt.setObject(3, item.getBill4());// 手册号
		stmt.setObject(4, item.getBill5());// 核销否
		stmt.setObject(5, item.getBill23());// 客户ID
		// 临时存放字段
		stmt.setObject(6, key);
		// 公司
		stmt.setObject(7, CommonUtils.getCompany().getId());
	}

	/**
	 * 设置明细值
	 * 
	 * @param stmt
	 * @param item
	 * @throws SQLException
	 */
	private void setValueForDetail(PreparedStatement stmt, BillTemp item)
			throws SQLException {
		String key = (item.getBill1() == null ? "" : item.getBill1()) + "/"
				+ (item.getBill2() == null ? "" : item.getBill2()) + "/"
				+ (item.getBill23() == null ? "" : item.getBill23()) + "/"
				+ (item.getBill4() == null ? "" : item.getBill4()) + "/"
				+ (item.getBill5() == null ? "" : item.getBill5());
		// 保存表头
		List list = this.casDao.findTempNofromInvoiceMaster(key);
		if (list != null && list.size() != 0) {
			CasInvoice tmp = (CasInvoice) list.get(0);
			stmt.setObject(1, tmp.getId());
		}
		stmt.setObject(2, item.getBill6());// 海关编码
		stmt.setObject(3, item.getBill7());// 报关名称
		stmt.setObject(4, item.getBill8());// 报关规格
		stmt.setObject(5, item.getBill29());// 报关单位
		stmt.setObject(6, item.getBill11());// 发票数量
		stmt.setObject(7, item.getBill12());// 发票单价
		stmt.setObject(8, item.getBill14());// 总净重
		stmt.setObject(9, item.getBill15());// 核销数量
		stmt.setObject(10, CommonUtils.getCompany().getId());// 公司
		stmt.setObject(11, item.getBill13());// 总金额
		stmt.setObject(12, item.getBill10());// 归并序号
		/**
		 * 2012-4-23 zyy 原因：只要填写了核销数量，发票状态就是已核销
		 */
		stmt.setObject(13, item.getBill5());//核销状态
//		if (Double.parseDouble(item.getBill15()) > 0) {// 根据核销数量来判断核销状态
//			stmt.setObject(13, true);
//		} else {
//			stmt.setObject(13, false);
//		}
	}

	/**
	 * 获取海关帐DAO实体
	 * 
	 * @return
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设置海关帐DAO实体
	 * 
	 * @return
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * 获取数据源实例
	 * 
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 设置数据源实例
	 * 
	 * @return
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 转厂单据文本固定格式导入(暂无处理重复记录)
	 * 
	 * @param headList
	 * @param detailList
	 * @throws SQLException
	 */
	public void executeTransBillImport(List headList, List detailList)
			throws SQLException {

		getScmCoc();

		String insertSql = getInsertSqlTransHead();
		conn = dataSource.getConnection();
		PreparedStatement stmtInsert = conn.prepareStatement(insertSql);
		System.out.println("[JBCUS]          begin insert table casHead time:"
				+ new Date());
		for (int i = 0; i < headList.size(); i++) {
			BillTemp param = (BillTemp) headList.get(i);
			setParametersForTrans(stmtInsert, param);
			stmtInsert.addBatch();
			if ((i != 0 && (i % 1000) == 0) || i == headList.size() - 1) {
				stmtInsert.executeBatch();
				stmtInsert.clearBatch();
			}
		}
		System.out.println("[JBCUS]          end insert table casHead time:"
				+ new Date());
		// getTransHead();
		insertSql = getInsertSqlTransDetail();
		PreparedStatement stmtInsertForD = conn.prepareStatement(insertSql);

		System.out
				.println("[JBCUS]          begin insert table casDetail time:"
						+ new Date());
		for (int i = 0; i < detailList.size(); i++) {
			BillTemp param = (BillTemp) detailList.get(i);
			setParametersForTransD(stmtInsertForD, param);
			stmtInsertForD.addBatch();
			if ((i != 0 && (i % 1000) == 0) || i == detailList.size() - 1) {
				stmtInsertForD.executeBatch();
				stmtInsertForD.clearBatch();
			}
		}
		System.out.println("[JBCUS]          end insert table casDetail time:"
				+ new Date());

	}

	/**
	 * 转厂
	 * 
	 * @param headList
	 * @param detailList
	 * @param hs
	 * @throws SQLException
	 */
	public void executeTransBillImport(List headList, List detailList,
			Hashtable hs) throws SQLException {
		boolean isImpExpGoods = true;
		if ("出货".equals(hs.get(5))) {
			isImpExpGoods = false;
		}
		TransferFactoryBill transferFactoryBill = null;
		List<String> listBill = new ArrayList();
		for (int j = 0; j < detailList.size(); j++) {
			BillTemp billTemp = (BillTemp) detailList.get(j);

			if (!listBill.contains(billTemp.getBill1())) {
				transferFactoryBill = new TransferFactoryBill();
				transferFactoryBill.setTransferFactoryBillNo(billTemp
						.getBill1());// 单据号
				transferFactoryBill.setIsImpExpGoods(isImpExpGoods);
				transferFactoryBill.setScmCoc(billTemp.getScmcoc());// 客户供应商
				transferFactoryBill.setWareSet(billTemp.getWareSet());// 仓库
				transferFactoryBill.setEnvelopNo(billTemp.getBill5());// 关封号
				transferFactoryBill.setMemo(billTemp.getBill6());
				String isvdate = (hs == null ? "" : (String) hs.get(6));
				String d = billTemp.getBill3();// 生效日期
				if (isvdate != null && isvdate.equals("yyyyMMdd")) {
					if (d != null && d.length() == 8) {
						d = d.substring(0, 4) + "-" + d.substring(4, 6) + "-"
								+ d.substring(6, 8);
					}
				} else if (isvdate != null && isvdate.equals("yyyy/MM/dd")) {
					if (d != null && d.length() == 10) {
						d = d.replace("/", "-");
					}
				}
				// 这样就不会有日期问题
				Date date = java.sql.Date.valueOf(d);
				transferFactoryBill.setBeginAvailability(date);
				this.emsEdiTrDao.saveOrUpdate(transferFactoryBill);
				listBill.add(billTemp.getBill1());
			}

			TransferFactoryCommodityInfo transferFactoryCommodityInfo = new TransferFactoryCommodityInfo();
			transferFactoryCommodityInfo
					.setTransferFactoryBill(transferFactoryBill);
			transferFactoryCommodityInfo.setEmsNo(billTemp.getBill7()); // 手册号
			transferFactoryCommodityInfo.setSeqNum(Integer.valueOf(billTemp
					.getBill8()));// 备案序号
			List list = this.casBillDao
					.checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum(
							billTemp.getBill5(), billTemp.getBill7(),
							transferFactoryCommodityInfo.getSeqNum());
			if (list != null && list.size() > 0) {
				CustomsEnvelopCommodityInfo ceci = (CustomsEnvelopCommodityInfo) list
						.get(0);
				transferFactoryCommodityInfo.setComplex(ceci.getComplex());
				transferFactoryCommodityInfo.setUnit(ceci.getUnit());// 单位
				transferFactoryCommodityInfo.setCommName(ceci.getPtName());// 品名
				transferFactoryCommodityInfo.setCommSpec(ceci.getPtSpec());// 规格
				transferFactoryCommodityInfo.setQuantity(ceci
						.getOwnerQuantity());// 关封数量
			}
			transferFactoryCommodityInfo.setTransFactAmount(Double
					.valueOf(billTemp.getBill14() == null ? "0.0" : billTemp
							.getBill14()));// 转厂数量
			transferFactoryCommodityInfo.setUnitPrice(Double.valueOf(billTemp
					.getBill15() == null ? "0.0" : billTemp.getBill15()));// 单价
			transferFactoryCommodityInfo.setTotalPrice(Double.valueOf(billTemp
					.getBill16() == null ? "0.0" : billTemp.getBill16()));// 总价
			transferFactoryCommodityInfo.setCurr(billTemp.getCurr());// 汇率
			transferFactoryCommodityInfo.setGrossWeight(Double.valueOf(billTemp
					.getBill18() == null ? "0.0" : billTemp.getBill18()));// 毛重
			transferFactoryCommodityInfo.setNetWeight(Double.valueOf(billTemp
					.getBill19() == null ? "0.0" : billTemp.getBill19()));// 净重
			transferFactoryCommodityInfo.setCountry(billTemp.getCountry());
			transferFactoryCommodityInfo.setSourceBill(billTemp.getBill21());
			transferFactoryCommodityInfo.setCubage(Double.valueOf(billTemp
					.getBill22() == null ? "0.0" : billTemp.getBill22()));// 体积
			transferFactoryCommodityInfo.setVersion(billTemp.getBill23());
			transferFactoryCommodityInfo.setMemo(billTemp.getBill24());
			this.emsEdiTrDao.saveOrUpdate(transferFactoryCommodityInfo);
		}
		// }
	}

	/**
	 * 转厂单据文本固定格式导入(暂无处理重复记录)
	 * 
	 * @param headList
	 * @param detailList
	 * @throws SQLException
	 *             //
	 */
	// public void executeTransBillImport(List headList, List detailList,
	// Hashtable hs) throws SQLException {
	// boolean isImpExpGoods = true;
	// if ("出货".equals(hs.get(5))) {
	// isImpExpGoods = false;
	// }
	// TransferFactoryBill transferFactoryBill = new TransferFactoryBill();
	// for (int i = 0; i < headList.size(); i++) {
	// BillTemp billTemp = (BillTemp) headList.get(i);
	// transferFactoryBill.setId(null);
	// transferFactoryBill.setIsImpExpGoods(isImpExpGoods);
	// transferFactoryBill.setTransferFactoryBillNo(billTemp.getBill1());// 单据号
	// transferFactoryBill.setScmCoc(billTemp.getScmcoc());// 客户供应商
	// transferFactoryBill.setWareSet(billTemp.getWareSet());// 仓库
	// transferFactoryBill.setEnvelopNo(billTemp.getBill5());// 关封号
	// transferFactoryBill.setMemo(billTemp.getBill6());
	//
	// String isvdate = (hs == null ? "" : (String) hs.get(6));
	// String d = billTemp.getBill3();// 生效日期
	// if (isvdate != null && isvdate.equals("yyyyMMdd")) {
	// if (d != null && d.length() == 8) {
	// d = d.substring(0, 4) + "-" + d.substring(4, 6) + "-"
	// + d.substring(6, 8);
	// }
	// } else if (isvdate != null && isvdate.equals("yyyy/MM/dd")) {
	// if (d != null && d.length() == 10) {
	// d = d.replace("/", "-");
	// }
	// }
	// // 这样就不会有日期问题
	// Date date = java.sql.Date.valueOf(d);
	// transferFactoryBill.setBeginAvailability(date);
	//
	// this.emsEdiTrDao.saveOrUpdate(transferFactoryBill);
	//
	// // // 查找关封下的商品、用于根据备案序号自动查找出编码、名称、规格、单价
	// // List<CustomsEnvelopCommodityInfo> list = this.emsEdiTrDao
	// // .findCustomsEnvelopCommodityInfo(billTemp.getBill5());
	// // Map<Integer, CustomsEnvelopCommodityInfo> map = new
	// // HashMap<Integer, CustomsEnvelopCommodityInfo>();
	// // for (CustomsEnvelopCommodityInfo c : list) {
	// // if (map.get(c.getSeqNum())==null) {
	// // map.put(c.getSeqNum(), c);
	// // }
	// // }
	//
	// for (int j = 0; j < detailList.size(); j++) {
	// TransferFactoryCommodityInfo transferFactoryCommodityInfo = new
	// TransferFactoryCommodityInfo();
	// BillTemp billTemp2 = (BillTemp) detailList.get(j);
	// transferFactoryCommodityInfo.setId(null);
	// transferFactoryCommodityInfo
	// .setTransferFactoryBill(transferFactoryBill);
	// transferFactoryCommodityInfo.setEmsNo(billTemp2.getBill7()); // 手册号
	// transferFactoryCommodityInfo.setSeqNum(Integer
	// .valueOf(billTemp2.getBill8()));// 备案序号
	//
	// // if (map.get(Integer.valueOf(billTemp2.getBill8())) != null) {
	// List list = this.casBillDao
	// .checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum(
	// billTemp.getBill5(), billTemp2.getBill7(),
	// transferFactoryCommodityInfo.getSeqNum());
	// if (list != null && list.size() > 0) {
	// CustomsEnvelopCommodityInfo ceci = (CustomsEnvelopCommodityInfo) list
	// .get(0);
	// transferFactoryCommodityInfo.setComplex(ceci.getComplex());
	// transferFactoryCommodityInfo.setUnit(ceci.getUnit());// 单位
	// transferFactoryCommodityInfo.setCommName(ceci.getPtName());// 品名
	// transferFactoryCommodityInfo.setCommSpec(ceci.getPtSpec());// 规格
	// transferFactoryCommodityInfo.setQuantity(ceci
	// .getOwnerQuantity());// 关封数量
	// }
	// transferFactoryCommodityInfo.setTransFactAmount(Double
	// .valueOf(billTemp2.getBill14() == null ? "0.0"
	// : billTemp2.getBill14()));// 转厂数量
	// transferFactoryCommodityInfo.setUnitPrice(Double
	// .valueOf(billTemp2.getBill15() == null ? "0.0"
	// : billTemp2.getBill15()));// 单价
	// transferFactoryCommodityInfo.setTotalPrice(Double
	// .valueOf(billTemp2.getBill16() == null ? "0.0"
	// : billTemp2.getBill16()));// 总价
	// transferFactoryCommodityInfo.setCurr(billTemp2.getCurr());// 汇率
	// transferFactoryCommodityInfo.setGrossWeight(Double
	// .valueOf(billTemp2.getBill18() == null ? "0.0"
	// : billTemp2.getBill18()));// 毛重
	// transferFactoryCommodityInfo.setNetWeight(Double
	// .valueOf(billTemp2.getBill19() == null ? "0.0"
	// : billTemp2.getBill19()));// 净重
	// transferFactoryCommodityInfo.setCountry(billTemp2.getCountry());
	// transferFactoryCommodityInfo.setSourceBill(billTemp2
	// .getBill21());
	// transferFactoryCommodityInfo.setCubage(Double.valueOf(billTemp2
	// .getBill22() == null ? "0.0" : billTemp2.getBill22()));// 体积
	// transferFactoryCommodityInfo.setVersion(billTemp2.getBill23());
	// transferFactoryCommodityInfo.setMemo(billTemp2.getBill24());
	// this.emsEdiTrDao.saveOrUpdate(transferFactoryCommodityInfo);
	// }
	// }
	// }
	/**
	 * 插入数据到转厂单据表头中
	 * 
	 * @return
	 */
	private String getInsertSqlTransHead() {
		String sql = "insert into transferfactorybill (id,isImpExpGoods,transferFactoryBillNo,isAvailability,beginAvailability,scmCocId,companyId,memo,tempNo,envelopNo) values "
				+ " ( replace(newid(),'-','')" + ",?,?,'1',?,?,?,?,?,?)";
		return sql;
	}

	/**
	 * 查处输入到转厂单据明细中
	 * 
	 * @return
	 */
	private String getInsertSqlTransDetail() {
		String sql = "insert into transferfactorycommodityinfo (id,tfCommodityInfoId,seqNum,transFactAmount,unitPrice,companyId,exchangeRate,memo,"
				+ "complex,commname,commspec,unitid,emsNo) values "
				+ " ( replace(newid(),'-','')" + ",?,?,?,?,?,1,?,?,?,?,?,?)";
		return sql;
	}

	/**
	 * 设置参数给运输方式
	 * 
	 * @param stmt
	 * @param param
	 * @throws SQLException
	 */
	private void setParametersForTrans(PreparedStatement stmt, BillTemp param)
			throws SQLException {
		// 单据类型：0：进口 1：出口
		String stype = param.getBill1();
		stmt.setObject(1, stype);
		// 单据号
		stmt.setObject(2, param.getBill2());
		// 生效日期（YYYY-MM-DD）
		String d = param.getBill4();
		stmt.setObject(3, d);
		// 客户供应商(代码)
		String sscmcoc = param.getBill3();
		if (sscmcoc != null && !sscmcoc.equals("")) {
			sscmcoc = (String) scmHsByCode.get(param.getBill3());
			stmt.setObject(4, sscmcoc);
		} else {
			stmt.setObject(4, null);
		}

		// 公司
		stmt.setObject(5, CommonUtils.getCompany().getId());
		// 备注
		stmt.setObject(6, param.getBill5());
		// 关封号
		stmt.setObject(8, param.getBill10());
		String key = (param.getBill1() == null ? "" : param.getBill1()) + "/"
				+ (param.getBill2() == null ? "" : param.getBill2()) + "/"
				+ (param.getBill3() == null ? "" : param.getBill3()) + "/"
				+ (param.getBill4() == null ? "" : param.getBill4()) + "/"
				+ (param.getBill5() == null ? "" : param.getBill5()) + "/"
				+ (param.getBill10() == null ? "" : param.getBill10());
		stmt.setObject(7, key);
	}

	/**
	 * 设置参数和绑定数据
	 * 
	 * @param stmt
	 * @param param
	 * @throws SQLException
	 */
	private void setParametersForTransD(PreparedStatement stmt, BillTemp param)
			throws SQLException {
		EmsHeadH2k head = null;
		List emsHeadList = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
		if (emsHeadList != null && emsHeadList.size() > 0) {
			head = (EmsHeadH2k) emsHeadList.get(0);
		}

		String key = (param.getBill1() == null ? "" : param.getBill1()) + "/"
				+ (param.getBill2() == null ? "" : param.getBill2()) + "/"
				+ (param.getBill3() == null ? "" : param.getBill3()) + "/"
				+ (param.getBill4() == null ? "" : param.getBill4()) + "/"
				+ (param.getBill5() == null ? "" : param.getBill5()) + "/"
				+ (param.getBill10() == null ? "" : param.getBill10());
		stmt.setObject(1, transHeadHs.get(key));// 表头
		stmt.setObject(2, param.getBill6());
		if (param.getBill7().equals("")) {
			stmt.setObject(3, new Double(0.0));
		} else {
			stmt.setObject(3, Double.parseDouble(param.getBill7()));
		}
		if (param.getBill8().equals("")) {
			stmt.setObject(4, new Double(0.0));
		} else {
			stmt.setObject(4, Double.parseDouble(param.getBill8()));
		}
		stmt.setObject(5, CommonUtils.getCompany().getId());
		stmt.setObject(6, param.getBill9());

		Integer seqNum = Integer.valueOf(param.getBill6());
		String type = param.getBill1();
		if (type.equals("0")) {// 料件
			List ls = emsEdiTrDao.findEmsHeadH2kImgBySeqNum(head, seqNum);
			if (ls != null && ls.size() > 0) {
				EmsHeadH2kImg img = (EmsHeadH2kImg) ls.get(0);
				stmt.setObject(7, img.getComplex().getCode());// 编码
				stmt.setObject(8, img.getName());// 名称
				stmt.setObject(9, img.getSpec());// 规格
				stmt.setObject(10, img.getUnit().getCode());// 单位
			} else {
				stmt.setObject(7, null);// 编码
				stmt.setObject(8, null);// 名称
				stmt.setObject(9, null);// 规格
				stmt.setObject(10, null);// 单位
			}
		} else {// 成品
			List ls = emsEdiTrDao.findEmsHeadH2kExgBySeqNum(head, seqNum);
			if (ls != null && ls.size() > 0) {
				EmsHeadH2kExg exg = (EmsHeadH2kExg) ls.get(0);
				stmt.setObject(7, exg.getComplex().getCode());// 编码
				stmt.setObject(8, exg.getName());// 名称
				stmt.setObject(9, exg.getSpec());// 规格
				stmt.setObject(10, exg.getUnit().getCode());// 单位
			} else {
				stmt.setObject(7, null);// 编码
				stmt.setObject(8, null);// 名称
				stmt.setObject(9, null);// 规格
				stmt.setObject(10, null);// 单位
			}
		}
		stmt.setObject(11, head.getEmsNo());// 帐册号
	}

	/**
	 * 批量修改料号对应的报关商品
	 * 
	 * @param list
	 * @param map
	 * @return
	 */
	public List BatchUpdateHs(List<BillDetail> list, String taskId,
			Map<String, Object> map, String materielType) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在保存数据,请稍候...");
		}
		String tableName = BillUtil.getDBTableNameByMaterielType(materielType);
		String sql = "update "
				+ tableName.trim()
				+ " "
				+ "set complexid = ?,hsName = ?,hsSpec = ?,hsUnitId = ?,hsVersion = ?,"
				+ "hsAmount = ?,hsPrice = ?,money = ? " + "where id = ?";

		if (list != null && list.size() != 0 && map != null) {
			List<List<Object>> tmp = new ArrayList<List<Object>>();
			int i = 0;
			for (BillDetail item : list) {
				List<Object> parameters = new ArrayList<Object>();
				Complex complex = (Complex) map.get("complex");
				String hsName = (String) map.get("hsName");
				String hsSpec = (String) map.get("hsSpec");
				Unit hsUnit = (Unit) map.get("hsUnit");
				Double unitConvert = (Double) map.get("unitConvert");
				Integer hsVersion = 0;
				item.setComplex(complex);// 海关编码
				item.setHsName(hsName);// 报关名称
				item.setHsSpec(hsSpec);// 报关规格
				item.setHsUnit(hsUnit);// 报关单位
				if (map.get("hsVersion") == null) {// 成品版本号
					item.setHsVersion(null);
				} else {
					hsVersion = (Integer) map.get("hsVersion");
					item.setHsVersion(hsVersion);
				}
				item.setUnitConvert(unitConvert);
				item.setHsAmount(item.getPtAmount() * item.getUnitConvert());// 报关数量
				parameters.add(item.getComplex().getId());
				parameters.add(item.getHsName());
				parameters.add(item.getHsSpec());
				parameters.add(item.getHsUnit().getCode());
				parameters.add(item.getHsVersion());
				parameters.add(item.getHsAmount());

				if (item.getPtPrice() != null) {// 报关价格与总额
					item.setHsPrice(item.getPtPrice() / item.getUnitConvert());
					item.setMoney(item.getPtAmount() * item.getPtPrice());
					parameters.add(item.getHsPrice());
					parameters.add(item.getMoney());
				} else {
					parameters.add((Double) 0.0);
					parameters.add((Double) 0.0);
				}
				System.out.println("---------------sql;" + sql);
				parameters.add(item.getId());

				tmp.add(parameters);

				i++;
				if (info != null) {
					info.setCurrentNum(i);
				}
			}
			long time = System.currentTimeMillis();
			this.casBillDao.batchUpdateOrDeleteInSql(sql, tmp, 10);
			System.out.println("------------------------ "
					+ (System.currentTimeMillis() - time));
		}

		return list;
	}

	/**
	 * 返回一个用物料料号作key对应从对应关系的hashMap
	 * 
	 * @param typename
	 * @return hashMap
	 */
	public Map<String, Object[]> findPtNoAndCodeInStatCusNameRelation(
			String typename) {
		Map map = new HashMap();
		List<String> muplPtNo = new ArrayList();
		List relist = this.casDao
				.findPtNoAndCodeInStatCusNameRelation(typename);
		for (int i = 0; i < relist.size(); i++) {
			Object[] objs = (Object[]) relist.get(i);
			if (objs[0] == null) {
				continue;
			}
			// 当有一对多的情况就取折算系数最大的
			if (muplPtNo.contains(objs[0])) {
				continue;
			}
			muplPtNo.add((String) objs[0]);
			map.put((String) objs[0], objs);
		}
		return map;
	}

	/**
	 * 返回一个用物料料号作key对应从报关常用工厂物料信息的hashMap
	 * 
	 * @param typename
	 * @return hashMap
	 * @author wss
	 */
	public Map<String, Object[]> findPtNoAndCodeInMateriel(String typename) {
		Map map = new HashMap();
		List relist = this.casDao.findPtNoAndCodeInMateriel(typename);
		for (int i = 0; i < relist.size(); i++) {
			Object[] objs = (Object[]) relist.get(i);
			map.put((String) objs[0], objs);
		}
		return map;
	}

	/**
	 * 返回一个用物料料号作key对应从数据库抓出的物料信息的hashMap (针对半成品，从报关常用工厂物料中抓取资料)
	 * 
	 * @param typename
	 * @return hashMap
	 * @author wss2010.09.28
	 */
	public Map<String, Object[]> fintPtNoAndCodeInMateriel(String typename) {
		Map map = new HashMap();
		List relist = this.casDao.findPtNoAndCodeInMateriel(typename);
		for (int i = 0; i < relist.size(); i++) {
			Object[] objs = (Object[]) relist.get(i);
			map.put((String) objs[0], objs);
		}
		return map;

	}

	/**
	 * 如果是半成品，返回一个用 料号+报关名称+报关规格+报关单位 对应 物料信息 其它：返回一个用 料号+报关名称+报关规格+报关单位+手册号 对应
	 * 对应关系信息
	 * 
	 * @param type
	 * @return
	 * 
	 */
	public Map<String, Object[]> findHsDataByStatCusNameRelation(
			String typeName, Boolean isEmsNo) {
		Map map = new HashMap();
		List<String> muplPtNo = new ArrayList();
		List relist = this.casDao
				.findPtNoAndCodeInStatCusNameRelation(typeName);
		for (int i = 0; i < relist.size(); i++) {
			Object[] objs = (Object[]) relist.get(i);
			String key = (objs[0] == null ? "" : (String) objs[0]) + "/" // 料号
					+ (objs[2] == null ? "" : (String) objs[2]) + "/" // 报关名称
					+ (objs[3] == null ? "" : (String) objs[3]) + "/" // 报关规格
					+ (objs[4] == null ? "" : ((Unit) objs[4]).getName());// 报关单位
			if (isEmsNo) {
				key += "/" + (objs[8] == null ? "" : (String) objs[8]); // 手册号
				map.put(key, objs);
			} else {
				if (muplPtNo.contains(key)) {
					continue;
				}
				muplPtNo.add(key);
				map.put(key, objs);
			}

		}
		return map;

	}

	/**
	 * 如果是半成品，返回一个用 料号+报关名称+报关规格+报关单位 对应 物料信息
	 * 
	 * @param type
	 * @return
	 * @author wss2010.09.28
	 */
	public Map<String, Object[]> findHsDataByMateriel(String typeName) {
		Map map = new HashMap();
		List relist = this.casDao.findPtNoAndCodeInMateriel(typeName);
		for (int i = 0; i < relist.size(); i++) {
			Object[] objs = (Object[]) relist.get(i);
			String key = (objs[0] == null ? "" : (String) objs[0]) + "/" // 料号
					+ (objs[2] == null ? "" : (String) objs[2]) + "/" // 报关名称
					+ (objs[3] == null ? "" : (String) objs[3]);// 报关规格
			// + (objs[4] == null ? "" : ((Unit) objs[4]).getName()); // 报关单位
			map.put(key, objs);
		}
		return map;

	}

	/**
	 * 查找唯一的 FactoryAndFactualCustomsRalation 名称规格单位
	 * 
	 * @param typeName
	 * @return key = value = 料号+ "/" + 名称+"/"+规格+"/"+单位
	 */
	public Map<String, String> findDistinctFFCByPtNoNameSpecUnit(
			String typeName, Boolean isEmsNo) {
		Map<String, String> map = new HashMap<String, String>();
		if (("半成品".equals(typeName) || "残次品(半成品)".equals(typeName))) {
			return map;
		}
		List list = this.casDao.findDistinctFFCByPtNoNameSpecUnit(typeName);

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String ptNo = objs[0] == null ? "" : objs[0].toString().trim();
			String hsName = objs[1] == null ? "" : objs[1].toString().trim();
			String hsSpec = objs[2] == null ? "" : objs[2].toString().trim();
			String hsUnitName = objs[3] == null ? "" : objs[3].toString()
					.trim();
			String emsNo = objs[4] == null ? "" : objs[4].toString().trim();
			String key = ptNo + "/" + hsName + "/" + hsSpec + "/" + hsUnitName;
			if (isEmsNo) {
				key += "/" + emsNo;
			}
			map.put(key, key);
		}
		return map;

	}

	/**
	 * 查找唯一的 Mateiel 名称规格单位
	 * 
	 * @param typeName
	 * @return key = value = 料号+ "/" + 报关名称+"/" + 报关规格+"/" + 报关单位
	 * @author wss2010.09.28
	 */
	public Map<String, String> findDistinctMaterielByPtNoNameSpecUnit(
			String typeName) {
		Map<String, String> map = new HashMap<String, String>();
		List list = this.casDao
				.findDistinctMaterielByPtNoNameSpecUnit(typeName);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String ptNo = objs[0] == null ? "" : objs[0].toString().trim();
			String hsName = objs[1] == null ? "" : objs[1].toString().trim();
			String hsSpec = objs[2] == null ? "" : objs[2].toString().trim();

			String key = ptNo + "/" + hsName + "/" + hsSpec;
			map.put(key, key);
		}
		return map;

	}

	/**
	 * 增加工厂和工厂报关资料对应关系
	 * 
	 * @param list
	 * @return
	 */
	public List addFactoryAndFactualCustomsRalation(List<BcsInnerMerge> list) {
		if (list == null) {
			return new ArrayList();
		}
		List relist = new ArrayList();
		for (BcsInnerMerge data : list) {
			Materiel mt = data.getMateriel();
			// StatCusNameRelationHsn scn=lid
			BcsTenInnerMerge bcst = data.getBcsTenInnerMerge();
			if (bcst == null || mt == null) {
				continue;
			}
			StatCusNameRelationHsn scn = new StatCusNameRelationHsn();
			scn.setCompany(bcst.getCompany());
			scn.setComplex(bcst.getComplex());
			scn.setCusName(bcst.getName());
			scn.setCusSpec(bcst.getSpec());
			scn.setCusUnit(bcst.getComUnit());
			scn.setMaterielType(data.getMaterielType());
			scn.setSeqNum(bcst.getSeqNum());
			scn.setProjectType(ProjectType.BCS);
			this.casDao.saveOrUpdate(scn);
			FactoryAndFactualCustomsRalation faf = new FactoryAndFactualCustomsRalation();
			faf.setMateriel(mt);
			faf.setCompany(scn.getCompany());
			faf.setStatCusNameRelationHsn(scn);
			this.casDao.saveOrUpdate(faf);
			relist.add(faf);
		}
		return relist;
	}

	/**
	 * 获取经营范围DAO类实例
	 * 
	 * @return
	 */
	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	/**
	 * 设置经营范围DAO类实例
	 * 
	 * @param emsEdiTrDao
	 */
	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	/**
	 * 对应发票和单据的关系
	 * 
	 * @param invoice
	 * @param details
	 * @return
	 */
	public List makeCasInvoiceAndBillRalation(CasInvoiceInfo invoice,
			List<BillDetail> details) {
		List retlist = new ArrayList();
		if (invoice.getToBillsAll() != null && invoice.getToBillsAll()) {
			return retlist;// 如果发票数量已全部对应完，则返回
		}

		for (BillDetail bill : details) {
			if (!"1107".equals(bill.getBillMaster().getBillType().getCode())) {
				double invoiceNum = (invoice.getAmount() == null ? 0 : invoice
						.getAmount())
						- (invoice.getToBillNum() == null ? 0 : invoice
								.getToBillNum());// 发票可对应数量
				System.out.println("发票可对应数量=" + invoiceNum);
				if (invoiceNum <= 0) {
					return retlist;// 说明没有可以对应的了
				}
				if (bill.getHsAmount() == null || bill.getHsAmount() == 0) {
					continue;// 说明没有可以对应的了
				}
				// 单据数量
				Double billNum = (bill.getHsAmount() == null ? 0.0 : bill
						.getHsAmount())
						- (bill.getCustomNum() == null ? 0.0 : bill
								.getCustomNum());
				if (billNum >= invoiceNum) {// 单据的数量大于发票的数量
					// ----------------------------------------------------
					InvoiceAndBillCorresponding ra = new InvoiceAndBillCorresponding();
					ra.setBillDetailkey(bill.getId());
					ra.setInvoiceInfokey(invoice.getId());
					ra.setInvoiceNum(invoiceNum);
					ra.setBillDetailNum(bill.getHsAmount());// 总数量
					ra.setBillNo(bill.getBillMaster().getBillNo());

					if (bill.getNetWeight() == null || bill.getNetWeight() == 0) {
						ra.setInvoiceWeight(0.0);
					} else {
						ra.setInvoiceWeight(bill.getNetWeight() * invoiceNum
								/ bill.getHsAmount());
					}
					ra.setBillWeight(bill.getNetWeight());
					ra.setCusName(invoice.getCuName());
					ra.setCusSpce(invoice.getCuSpec());
					ra.setCustomer(invoice.getCasInvoice().getCustomer());
					ra.setCusUnit(invoice.getUnit());
					ra.setEmsNo(invoice.getCasInvoice().getEmsNo());
					ra.setInvoicePrice(invoice.getPrice());
					ra
							.setTypeCode(bill.getBillMaster().getBillType()
									.getCode());

					this.casBillDao.saveOrUpdate(ra);
					// --------------------------------------------------------
					invoice.setToBillsAll(true);
					invoice.setToBillNum((invoice.getToBillNum() == null ? 0.0
							: invoice.getToBillNum())
							+ invoiceNum);
					this.casDao.saveOrUpdate(invoice);
					bill.setNote((bill.getCustomNo() == null ? "" : bill
							.getCustomNo())
							+ (invoice.getCasInvoice() == null ? "" : invoice
									.getCasInvoice().getInvoiceNo()) + ",");
					bill.setCustomNum((bill.getCustomNum() == null ? 0 : bill
							.getCustomNum())
							+ invoiceNum);
					this.casDao.saveOrUpdate(bill);
					retlist.add(ra);
				} else if (billNum < invoiceNum) {// 发票的数量大于单据的数量
					// ----------------------------------------------------
					InvoiceAndBillCorresponding ra = new InvoiceAndBillCorresponding();
					ra.setBillDetailkey(bill.getId());
					ra.setInvoiceInfokey(invoice.getId());
					ra.setBillDetailNum(bill.getHsAmount());//
					ra.setInvoiceNum(billNum);
					ra.setBillNo(bill.getBillMaster().getBillNo());

					if (bill.getNetWeight() == null || bill.getNetWeight() == 0) {
						ra.setInvoiceWeight(0.0);
					} else {
						ra.setInvoiceWeight(bill.getNetWeight() * billNum
								/ bill.getHsAmount());
					}
					ra.setBillWeight(bill.getNetWeight());
					ra.setCusName(invoice.getCuName());
					ra.setCusSpce(invoice.getCuSpec());
					ra.setCustomer(invoice.getCasInvoice().getCustomer());
					ra.setCusUnit(invoice.getUnit());
					ra.setEmsNo(invoice.getCasInvoice().getEmsNo());
					ra.setInvoicePrice(invoice.getPrice());
					ra
							.setTypeCode(bill.getBillMaster().getBillType()
									.getCode());
					this.casBillDao.saveOrUpdate(ra);
					// --------------------------------------------------------
					invoice.setToBillNum((invoice.getToBillNum() == null ? 0.0
							: invoice.getToBillNum())
							+ billNum);
					this.casDao.saveOrUpdate(invoice);
					bill.setCustomNo((bill.getCustomNo() == null ? "" : bill
							.getCustomNo())
							+ (invoice.getCasInvoice() == null ? "" : invoice
									.getCasInvoice().getInvoiceNo()) + ",");
					bill.setCustomNum((bill.getCustomNum() == null ? 0 : bill
							.getCustomNum())
							+ billNum);
					this.casDao.saveOrUpdate(bill);
					retlist.add(ra);
				}
			} else {// 其它料件退货单
				Double billNum = (bill.getHsAmount() == null ? 0.0 : bill
						.getHsAmount())
						- (bill.getCustomNum() == null ? 0.0 : bill
								.getCustomNum());
				InvoiceAndBillCorresponding ra = new InvoiceAndBillCorresponding();
				ra.setBillDetailkey(bill.getId());
				ra.setInvoiceInfokey(invoice.getId());
				ra.setBillDetailNum(bill.getHsAmount());//
				ra.setInvoiceNum(billNum);
				ra.setBillNo(bill.getBillMaster().getBillNo());

				if (bill.getNetWeight() == null || bill.getNetWeight() == 0) {
					ra.setInvoiceWeight(0.0);
				} else {
					ra.setInvoiceWeight(bill.getNetWeight() * billNum
							/ bill.getHsAmount());
				}
				ra.setBillWeight(bill.getNetWeight());
				ra.setCusName(invoice.getCuName());
				ra.setCusSpce(invoice.getCuSpec());
				ra.setCustomer(invoice.getCasInvoice().getCustomer());
				ra.setCusUnit(invoice.getUnit());
				ra.setEmsNo(invoice.getCasInvoice().getEmsNo());
				ra.setInvoicePrice(invoice.getPrice());
				ra.setTypeCode(bill.getBillMaster().getBillType().getCode());
				this.casBillDao.saveOrUpdate(ra);
				// --------------------------------------------------------
				invoice.setToBillNum((invoice.getToBillNum() == null ? 0.0
						: invoice.getToBillNum())
						- billNum);
				this.casDao.saveOrUpdate(invoice);
				bill.setCustomNo((bill.getCustomNo() == null ? "" : bill
						.getCustomNo())
						+ (invoice.getCasInvoice() == null ? "" : invoice
								.getCasInvoice().getInvoiceNo()) + ",");
				bill.setCustomNum((bill.getCustomNum() == null ? 0 : bill
						.getCustomNum())
						+ billNum);
				this.casDao.saveOrUpdate(bill);
				retlist.add(ra);
			}

		}
		return retlist;
	}

	/**
	 * 检查导入的数据的正确性，并输出错误提示
	 * 
	 * @param list 等待验证的数据
	 * @param colsSets 列映射设置
	 * @param taskId
	 * @param materielType
	 * @param isCover
	 * @return
	 */
	public List checkImportDataForRelation(List list, int[] colsSets,String taskId, String materielType, Map<String, Boolean> map) {
		
		// 判断相应的列是否有设置映射,保存到数组中间去。
		boolean[] isSetValues = new boolean[colsSets.length];
		
		for (int i = 0; i < colsSets.length; i++) {
			if(colsSets[i] > -1) {
				isSetValues[i] = true;
			}
		}
		// 数据条数
		int size = list.size();
		
		// 有问题的数据列表
		List<FactoryAndFactualCustomsRalation> ls = new ArrayList(size);
		// 检查通过的数据列表
		List<FactoryAndFactualCustomsRalation> reBuildLs = new ArrayList(size);
		// 检查是否通过
		boolean hasError = false;
		
		// 工厂和实际客户对应
		FactoryAndFactualCustomsRalation fileData = null;
		
		/*
		 * 显示进度信息
		 */
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在检验文件资料");
		}
		
		

		// 是否物料与报关一对多
		Boolean isOneToMany = map.get("isOneToMany");
		// 料号重复覆盖导入
		Boolean isDupImport = map.get("isDupImport"); 
		// 是否反写物料
		Boolean isReWriteMateriel = map.get("isReWriteMateriel");
		
		
		// 对应关系中的报关资料Key
		Map<String, StatCusNameRelationHsn> mapStatHsn = this.casDao.findKeyFromStatCusNameRelationHsn2(materielType, isOneToMany);
		
		

		// 所有【工厂单位】
		List<CalUnit> calUnitList = this.materialManageDao.findCalUnit();
		// 组装成 工厂单位名——》工厂单位 的 hashmap 方便查询检索
		Map<String, CalUnit> calUnitMap = new HashMap<String, CalUnit>(calUnitList.size());
		for (int i = 0; i < calUnitList.size(); i++) {
			calUnitMap.put(calUnitList.get(i).getName(), calUnitList.get(i));
		}
		
		
		// 所有【报关单位】
		List<Unit> unitList = this.materialManageDao.findUnit();
		// 组装成 报关单位名——》报关单位 的 hashmap 方便查询检索
		Map<String, Unit> unitMap = new HashMap<String, Unit>(unitList.size());
		for (int i = 0; i < unitList.size(); i++) {
			unitMap.put(unitList.get(i).getName(), unitList.get(i));
		}
		
		
		// 所有【物料类别】
		List<ScmCoi> scmcoiList = this.materialManageDao.findScmCois();
		// 组装成 物料类别属性——》物料类别 的 hashmap 方便查询检索
		Map<String, ScmCoi> scmcoiMap = new HashMap<String, ScmCoi>(scmcoiList.size());
		for (int i = 0; i < scmcoiList.size(); i++) {
			scmcoiMap.put(scmcoiList.get(i).getCoiProperty(), scmcoiList.get(i));
		}
		//所有【报关常用物料】
		Map<String,Materiel> materielMap = new HashMap<String,Materiel>();
		//当不反写物料信息时 ,检查物料规格以及名称
		if(!isReWriteMateriel){
			List<Materiel> meterielLs = materialManageDao.findMateriel(Arrays.asList(new String[]{materielType}));
			for(Materiel m : meterielLs){
				materielMap.put(m.getPtNo(), m);
			}
		}

		// 查询所有【工厂和实际客户对应】的【料号+报告编码+报关名称+报关规格+报关单位+手册号】组成的key值，去除重复key 
		// 组装成hashset方便查询比对
		Set<String> keySet = casDao.findAllFactoryAndFactualCustomsRalationKey();
		// 查询所有【工厂和实际客户对应】的【料号+报告编码+报关名称+报关规格+报关单位】组成的key值，去除重复key 
		// 组装成hashset方便查询比对
		Set<String> keySet2 = casDao.findAllFactoryAndFactualCustomsRalationKeyNoEmsNo();
		
		String key = null;
		for (int i = 0; i < list.size(); i++) {
			hasError = false;
			fileData = (FactoryAndFactualCustomsRalation) list.get(i);
			if (isSetValues[5]) {
				// 检查是否存在工厂单位
				if (calUnitMap.containsKey(fileData.getTemp1())) {
					CalUnit unit = calUnitMap.get(fileData.getTemp1());
					fileData.getMateriel().setCalUnit(unit);
				} else {
					//hasError.add(Integer.valueOf(6));
					hasError = true;
					fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason()) + "此工厂单位名称不存在;");
				}
			}
			
			
			
			if (isSetValues[10]) {
				// 检查是否存在报关单位
				if (unitMap.containsKey(fileData.getTemp3())) {
					Unit unit = unitMap.get(fileData.getTemp3());
					fileData.getStatCusNameRelationHsn().setCusUnit(unit);
				} else {
					// hasError.add(Integer.valueOf(11));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "此报关单位名称不存在;");
				}
			}
			
			if (isSetValues[3]) {
				if (fileData.getMateriel().getPtNetWeight() == null) {
					// hasError.add(Integer.valueOf(4));
					hasError = true;
					fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason()) + "工厂物料的净重不为数字格式;");
				}
			}
			if (isSetValues[4]) {
				if (fileData.getMateriel().getPtPrice() == null) {
					// hasError.add(Integer.valueOf(5));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "工厂物料的参考单价不为数字格式;");
				}
			}
			if (isSetValues[6]) {
				if (fileData.getUnitConvert() == null) {
					// hasError.add(Integer.valueOf(7));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "单位折算系数不为数字格式;");
				}
			}
			if (isSetValues[15]) {
				if (fileData.getStatCusNameRelationHsn().getProjectType() == null) {
					// hasError.add(Integer.valueOf(16));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "管理类型设定错误;");
				}
			} else {
				// hasError.add(Integer.valueOf(16));
				hasError = true;
				fileData.setErrorReason((fileData.getErrorReason() == null ? ""
						: fileData.getErrorReason())
						+ "没有设定管理类型;");
			}
			if (isSetValues[13]
					&& fileData.getStatCusNameRelationHsn().getProjectType() != null
					&& fileData.getStatCusNameRelationHsn().getProjectType() != ProjectType.BCUS) {
				if (fileData.getStatCusNameRelationHsn().getEmsBeginDate() == null) {
					// hasError.add(Integer.valueOf(14));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "手册开始日期不为日期格式;");
				}
			}
			if (isSetValues[14]
					&& fileData.getStatCusNameRelationHsn().getProjectType() != null
					&& fileData.getStatCusNameRelationHsn().getProjectType() != ProjectType.BCUS) {
				if (fileData.getStatCusNameRelationHsn().getEmsEndDate() == null) {
					// hasError.add(Integer.valueOf(15));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "手册结束日期不为日期格式;");
				}
			}

			if (isSetValues[0]) {
				//当不反写物料时，检验商品名称以及规格型号
				if(!isReWriteMateriel && fileData.getMateriel().getPtNo() != null){
					Materiel m = materielMap.get(fileData.getMateriel().getPtNo());
					if(m != null && isSetValues[1]){//商品名称
						if(!StringUtils.equals(fileData.getMateriel().getFactoryName(),m.getFactoryName())){
							hasError = true;
							fileData.setErrorReason( (fileData.getErrorReason() == null ? "": fileData.getErrorReason()) 
									+ "商品名称与常用工厂物料中的商品名称不一致;");
						}
					}
					if(m != null && isSetValues[2]){//规格型号
						if(!StringUtils.equals(fileData.getMateriel().getFactorySpec(),m.getFactorySpec())){
							hasError = true;
							fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason()) 
									+ "规格型号与常用工厂物料中的规格型号不一致;");
						}
					}
				}
				if (!hasError) {// 如果没有错误的话才执行
					if (fileData.getMateriel().getPtNo() != null
							&& !fileData.getMateriel().getPtNo().equals("")) {
						ScmCoi scmcoi = scmcoiMap.get(materielType);
						if (scmcoi == null) {
							fileData.setMateriel(null);
							// hasError.add(Integer.valueOf(1));
							hasError = true;
							fileData
									.setErrorReason((fileData.getErrorReason() == null ? ""
											: fileData.getErrorReason())
											+ "没有此料号的物料类型，请在物料基础资料的工厂通用代码中增加;");
						} else {
							fileData.getMateriel().setScmCoi(scmcoi);
						}
					} else {
						fileData.setMateriel(null);
						// hasError.add(Integer.valueOf(1));
						hasError = true;
						fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason())+ "该行的料号为空,请检查;");
					}
				}

			} else {
				fileData.setMateriel(null);
				// hasError.add(Integer.valueOf(1));
				hasError = true;
				fileData.setErrorReason((fileData.getErrorReason() == null ? ""
						: fileData.getErrorReason())
						+ "您还没有设定该行工厂料号对应的列;");
			}

			String matchStr = (fileData.getStatCusNameRelationHsn()
					.getCusName() == null ? "" : fileData
					.getStatCusNameRelationHsn().getCusName())
					+ "," + (fileData.getStatCusNameRelationHsn().getCusSpec() == null ? ""
							: fileData.getStatCusNameRelationHsn().getCusSpec())
					+ "," + (fileData.getStatCusNameRelationHsn().getCusUnit() == null ? ""
							: fileData.getStatCusNameRelationHsn().getCusUnit()
									.getName());
			if (isOneToMany) {// 如果是物料与报关一对多的话，要加手册号
				matchStr = matchStr
						+ "," + (fileData.getStatCusNameRelationHsn().getEmsNo() == null ? ""
								: fileData.getStatCusNameRelationHsn()
										.getEmsNo());
			}
			matchStr = matchStr.replaceAll(" ", "");
			if (isSetValues[8]
					&& isSetValues[9]
					&& isSetValues[10]) {
				// 商品编码和单位,日期已通过验证
				// if (!hasError.contains(Integer.valueOf(8))
				//		&& !hasError.contains(Integer.valueOf(11))
				//		&& !hasError.contains(Integer.valueOf(14))
				//		&& !hasError.contains(Integer.valueOf(15))
				//		&& !hasError.contains(Integer.valueOf(16))
				//		&& !hasError.contains(Integer.valueOf(17))) {
				if (!hasError) {
					if (fileData.getStatCusNameRelationHsn() == null) {
						hasError = true;
						fileData
								.setErrorReason((fileData.getErrorReason() == null ? ""
										: fileData.getErrorReason())
										+ "实际报关资料必填");
					}
					StatCusNameRelationHsn sr = mapStatHsn.get(matchStr
							.trim());
					// System.out.println(matchStr);
					if (sr != null) {
						if (!isOneToMany&&(fileData.getStatCusNameRelationHsn()
								.getEmsNo()!=null||!"".equals(fileData.getStatCusNameRelationHsn()
								.getEmsNo()))) {// 多对一或者一对一
							sr.setEmsNo( fileData
									.getStatCusNameRelationHsn().getEmsNo());
						}
						fileData.setStatCusNameRelationHsn(sr);
					} else {
						if(!isReWriteMateriel) {
							hasError = true;
							if (isOneToMany) {// 如果是物料与报关一对多的话，要加手册号
								fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason()) + "此报关商品在实际报关资料中不存在,对比关键字为名称、规格、单位、手册号缺一不可");
							} else {
								fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason()) + "此报关商品在实际报关资料中不存在,对比关键字为名称、规格、单位缺一不可");
							}
							fileData.setMateriel(null);// 出错,所以设定物料资料为空,防止错误资料反写到MATERIEL表中
						}
					}
				}

			} else {
				fileData.setStatCusNameRelationHsn(null);
				fileData.setMateriel(null);// 出错,所以设定物料资料为空,防止错误资料反写到MATERIEL表中
				// hasError.add(Integer.valueOf(0));
				hasError = true;
				fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason())+ "没有设定实际报关资料对应的列,或者文件中没有实际报关资料数据,请重新检查;");
			}
			
			if (isSetValues[7] 
					&& (fileData.getStatCusNameRelationHsn() != null)) {
				if(fileData.getTemp2().equals("")){
					hasError = true;
					fileData.setErrorReason((fileData.getErrorReason() == null ? "": fileData.getErrorReason())+ "自用商品编码为空！请重新检查;");
				}
				String code = fileData.getTemp2();
				//List codeList = this.materialManageDao.findMaterielByCode(code);
				Complex complex = this.hsCodeDao.findComplexByCode(code);
				if(complex==null){
					hasError = true;
					fileData.setErrorReason((fileData.getErrorReason() == null ? ""
							: fileData.getErrorReason())
							+ "【"+fileData.getTemp2()+"】自用商品编码不存在！请重新检查;");
				}
			}

			// 2010-12-16新加查看是否重复，按料号+报告编码+报关名称+报关规格+报关单位+手册号
			if (!isDupImport && (fileData.getStatCusNameRelationHsn() != null)) {
				if (fileData.getStatCusNameRelationHsn().getComplex() != null
						&& fileData.getStatCusNameRelationHsn().getCusUnit() != null) {
					// key = 料号+报告编码+报关名称+报关规格+报关单位+手册号
					key = (fileData.getMateriel().getPtNo() == null ? "" : fileData.getMateriel().getPtNo()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getComplex().getCode() == null ? "" : fileData.getStatCusNameRelationHsn().getComplex().getCode()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusName() == null ? "" : fileData.getStatCusNameRelationHsn().getCusName()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusSpec() == null ? "" : fileData.getStatCusNameRelationHsn().getCusSpec()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusUnit().getName() == null ? "" : fileData.getStatCusNameRelationHsn().getCusUnit().getName()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getEmsNo() == null ? "" : fileData.getStatCusNameRelationHsn().getEmsNo());
					if (keySet.contains(key)) {
						hasError = true;
						fileData
								.setErrorReason((fileData.getErrorReason() == null ? ""
										: fileData.getErrorReason())
										+ "已经有此对应关系，不能导入，若要导入请选择覆盖导入选项;");
					}
				}
			}
			// 2013-05-31新加在没有重复料件和成品，也没有选择反写而打算覆盖导入，按料号+报告编码+报关名称+报关规格+报关单位
			if (isDupImport&& !isReWriteMateriel && (fileData.getStatCusNameRelationHsn() != null)) {
				if (fileData.getStatCusNameRelationHsn().getComplex() != null
						&& fileData.getStatCusNameRelationHsn().getCusUnit() != null) {
					// key = 料号+报告编码+报关名称+报关规格+报关单位
					key = (fileData.getMateriel().getPtNo() == null ? "" : fileData.getMateriel().getPtNo()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getComplex().getCode() == null ? "" : fileData.getStatCusNameRelationHsn().getComplex().getCode()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusName() == null ? "" : fileData.getStatCusNameRelationHsn().getCusName()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusSpec() == null ? "" : fileData.getStatCusNameRelationHsn().getCusSpec()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusUnit().getName() == null ? "" : fileData.getStatCusNameRelationHsn().getCusUnit().getName()); 
					if (!keySet2.contains(key)) {
						hasError = true;
						fileData
								.setErrorReason((fileData.getErrorReason() == null ? ""
										: fileData.getErrorReason())
										+ "没有此对应关系，不能覆盖导入，若要导入请同时选择覆盖与反写导入选项;");
					}
				}
			}
			if (hasError) {
				fileData.setInvalidationRow(Integer.valueOf(i + 1));
				ls.add(fileData);
			} else {
				reBuildLs.add(fileData);
			}
			
			// 返回当前状态，处理完的条数信息。
			if (info != null && (i+1)%1000 == 0) {
				info.setCurrentNum(i);
			}
		}
		
		ls.addAll(reBuildLs);
		
		return ls;
	}
	
	/**
	 * 检查导入的数据的正确性，并输出错误提示(品基)
	 * 
	 * @param list 等待验证的数据
	 * @param colsSets 列映射设置
	 * @param taskId
	 * @param materielType
	 * @param map
	 * @return
	 */
	public List checkImportDataForRelationPJ(List list, int[] colsSets,
			String taskId, String materielType, Map<String, Boolean> map) {
		
		// 判断相应的列是否有设置映射,保存到数组中间去。
		boolean[] isSetValues = new boolean[colsSets.length];
		for (int i = 0; i < colsSets.length; i++) {
			if(colsSets[i] > -1) {
				isSetValues[i] = true;
			}
		}
		
		// 数据条数
		int size = list.size();
		
		// 有问题的数据列表
		List<FactoryAndFactualCustomsRalation> ls = new ArrayList(size);
		// 检查通过的数据列表
		List<FactoryAndFactualCustomsRalation> reBuildLs = new ArrayList(size);
		// 检查是否通过
		boolean hasError = false;
		// 工厂和实际客户对应
		FactoryAndFactualCustomsRalation fileData = null;
		
		/*
		 * 显示进度信息
		 */
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(size);
			info.setCurrentNum(0);
			info.setMethodName("系统正在检验文件资料");
		}
		
		

		// 是否物料与报关一对多
		Boolean isOneToMany = map.get("isOneToMany");
		// 料号重复覆盖导入
		Boolean isDupImport = map.get("isDupImport"); 
		// 是否反写物料
		Boolean isReWriteMateriel = map.get("isReWriteMateriel");
		// 客户供应商验证 编码或者名称
		Boolean isCode = map.get("isCode");
		
		// 对应关系中的报关资料Key
		Map<String, StatCusNameRelationHsn> mapStatHsn = this.casDao
				.findKeyFromStatCusNameRelationHsn2(materielType, isOneToMany);
		
		

		// 所有【工厂单位】
		List<CalUnit> calUnitList = this.materialManageDao.findCalUnit();
		// 组装成 工厂单位名——》工厂单位 的 hashmap 方便查询检索
		Map<String, CalUnit> calUnitMap = new HashMap<String, CalUnit>(calUnitList.size());
		for (int i = 0; i < calUnitList.size(); i++) {
			calUnitMap.put(calUnitList.get(i).getName(), calUnitList.get(i));
		}
		
		
		// 所有【报关单位】
		List<Unit> unitList = this.materialManageDao.findUnit();
		// 组装成 报关单位名——》报关单位 的 hashmap 方便查询检索
		Map<String, Unit> unitMap = new HashMap<String, Unit>(unitList.size());
		for (int i = 0; i < unitList.size(); i++) {
			unitMap.put(unitList.get(i).getName(), unitList.get(i));
		}
		
		
		// 所有【物料类别】
		List<ScmCoi> scmcoiList = this.materialManageDao.findScmCois();
		// 组装成 物料类别属性——》物料类别 的 hashmap 方便查询检索
		Map<String, ScmCoi> scmcoiMap = new HashMap<String, ScmCoi>(scmcoiList.size());
		for (int i = 0; i < scmcoiList.size(); i++) {
			scmcoiMap.put(scmcoiList.get(i).getCoiProperty(), scmcoiList.get(i));
		}
		// 所有【自用商品编码】
				List<Materiel> materielList = this.materialManageDao.findScmCois();
				
		// 查询所有【工厂和实际客户对应】的【料号+报告编码+报关名称+报关规格+报关单位+手册号】组成的key值，去除重复key 
		// 组装成hashset方便查询比对
		Set<String> keySet = casDao.findAllFactoryAndFactualCustomsRalationKeyPJ();
		
		
		// 组装成 客户供应商编码——》客户供应商 的 hashmap 方便查询检索
		Map<String, ScmCoc> scmcocMap = new HashMap<String, ScmCoc>(scmcoiList.size());
		if(isCode != null) {
			// 查询所有【客户供应商】
			List<ScmCoc> scmCocList = materialManageDao.findScmCocs();
			for (int i = 0; i < scmCocList.size(); i++) {
				scmcocMap.put(isCode ? scmCocList.get(i).getCode() : scmCocList
						.get(i).getName(), scmCocList.get(i));
			}
		}
		
		String key = null;
		for (int i = 0; i < size; i++) {
			hasError = false;
			fileData = (FactoryAndFactualCustomsRalation) list.get(i);
			if (isSetValues[5]) {
				// 检查是否存在工厂单位
				if (calUnitMap.containsKey(fileData.getTemp1())) {
					CalUnit unit = calUnitMap.get(fileData.getTemp1());
					fileData.getMateriel().setCalUnit(unit);
				} else {
					//hasError.add(Integer.valueOf(6));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "此工厂单位名称不存在;");
				}
			}

			if (isSetValues[10]) {
				// 检查是否存在报关单位
				if (unitMap.containsKey(fileData.getTemp3())) {
					Unit unit = unitMap.get(fileData.getTemp3());
					fileData.getStatCusNameRelationHsn().setCusUnit(unit);
				} else {
					// hasError.add(Integer.valueOf(11));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "此报关单位名称不存在;");
				}
			}
			if (isSetValues[3]) {
				if (fileData.getMateriel().getPtNetWeight() == null) {
					// hasError.add(Integer.valueOf(4));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "工厂物料的净重不为数字格式;");
				}
			}
			if (isSetValues[4]) {
				if (fileData.getMateriel().getPtPrice() == null) {
					// hasError.add(Integer.valueOf(5));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "工厂物料的参考单价不为数字格式;");
				}
			}
			if (isSetValues[6]) {
				if (fileData.getUnitConvert() == null) {
					// hasError.add(Integer.valueOf(7));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "单位折算系数不为数字格式;");
				}
			}
			if (isSetValues[15]) {
				if (fileData.getStatCusNameRelationHsn().getProjectType() == null) {
					// hasError.add(Integer.valueOf(16));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "管理类型设定错误;");
				}
			} else {
				// hasError.add(Integer.valueOf(16));
				hasError = true;
				fileData.setErrorReason((fileData.getErrorReason() == null ? ""
						: fileData.getErrorReason())
						+ "没有设定管理类型;");
			}
			if (isSetValues[13]
					&& fileData.getStatCusNameRelationHsn().getProjectType() != null
					&& fileData.getStatCusNameRelationHsn().getProjectType() != ProjectType.BCUS) {
				if (fileData.getStatCusNameRelationHsn().getEmsBeginDate() == null) {
					// hasError.add(Integer.valueOf(14));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "手册开始日期不为日期格式;");
				}
			}
			if (isSetValues[14]
					&& fileData.getStatCusNameRelationHsn().getProjectType() != null
					&& fileData.getStatCusNameRelationHsn().getProjectType() != ProjectType.BCUS) {
				if (fileData.getStatCusNameRelationHsn().getEmsEndDate() == null) {
					// hasError.add(Integer.valueOf(15));
					hasError = true;
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "手册结束日期不为日期格式;");
				}
			}

			if (isSetValues[0]) {
				if (!hasError) {// 如果没有错误的话才执行
					if (fileData.getMateriel().getPtNo() != null
							&& !fileData.getMateriel().getPtNo().equals("")) {
						ScmCoi scmcoi = scmcoiMap.get(materielType);
						if (scmcoi == null) {
							fileData.setMateriel(null);
							// hasError.add(Integer.valueOf(1));
							hasError = true;
							fileData
									.setErrorReason((fileData.getErrorReason() == null ? ""
											: fileData.getErrorReason())
											+ "没有此料号的物料类型，请在物料基础资料的工厂通用代码中增加;");
						} else {
							fileData.getMateriel().setScmCoi(scmcoi);
						}
					} else {
						fileData.setMateriel(null);
						// hasError.add(Integer.valueOf(1));
						hasError = true;
						fileData
								.setErrorReason((fileData.getErrorReason() == null ? ""
										: fileData.getErrorReason())
										+ "该行的料号为空,请检查;");
					}
				}

			} else {
				fileData.setMateriel(null);
				// hasError.add(Integer.valueOf(1));
				hasError = true;
				fileData.setErrorReason((fileData.getErrorReason() == null ? ""
						: fileData.getErrorReason())
						+ "您还没有设定该行工厂料号对应的列;");
			}

			String matchStr = (fileData.getStatCusNameRelationHsn()
					.getCusName() == null ? "" : fileData
					.getStatCusNameRelationHsn().getCusName())
					+ "," + (fileData.getStatCusNameRelationHsn().getCusSpec() == null ? ""
							: fileData.getStatCusNameRelationHsn().getCusSpec())
					+ "," + (fileData.getStatCusNameRelationHsn().getCusUnit() == null ? ""
							: fileData.getStatCusNameRelationHsn().getCusUnit()
									.getName());
			if (isOneToMany) {// 如果是物料与报关一对多的话，要加手册号
				matchStr = matchStr
						+ "," + (fileData.getStatCusNameRelationHsn().getEmsNo() == null ? ""
								: fileData.getStatCusNameRelationHsn()
										.getEmsNo());
			}
			matchStr = matchStr.replaceAll(" ", "");
			if (isSetValues[8]
					&& isSetValues[9]
					&& isSetValues[10]) {
				// 商品编码和单位,日期已通过验证
				// if (!hasError.contains(Integer.valueOf(8))
				//		&& !hasError.contains(Integer.valueOf(11))
				//		&& !hasError.contains(Integer.valueOf(14))
				//		&& !hasError.contains(Integer.valueOf(15))
				//		&& !hasError.contains(Integer.valueOf(16))
				//		&& !hasError.contains(Integer.valueOf(17))) {
				if (!hasError) {
					if (fileData.getStatCusNameRelationHsn() == null) {
						hasError = true;
						fileData
								.setErrorReason((fileData.getErrorReason() == null ? ""
										: fileData.getErrorReason())
										+ "实际报关资料必填");
					}
					StatCusNameRelationHsn sr = mapStatHsn.get(matchStr
							.trim());
					// System.out.println(matchStr);
					if (sr != null) {
						fileData.setStatCusNameRelationHsn(sr);
					} else {
						if(!isReWriteMateriel) {
							hasError = true;
							if (isOneToMany) {// 如果是物料与报关一对多的话，要加手册号
								fileData
										.setErrorReason((fileData
												.getErrorReason() == null ? ""
												: fileData.getErrorReason())
												+ "此报关商品在实际报关资料中不存在,对比关键字为名称、规格、单位、手册号缺一不可");
							} else {
								fileData
										.setErrorReason((fileData
												.getErrorReason() == null ? ""
												: fileData.getErrorReason())
												+ "此报关商品在实际报关资料中不存在,对比关键字为名称、规格、单位缺一不可");
							}
							fileData.setMateriel(null);// 出错,所以设定物料资料为空,防止错误资料反写到MATERIEL表中
						}
					}
				}

			} else {
				fileData.setStatCusNameRelationHsn(null);
				fileData.setMateriel(null);// 出错,所以设定物料资料为空,防止错误资料反写到MATERIEL表中
				// hasError.add(Integer.valueOf(0));
				hasError = true;
				fileData.setErrorReason((fileData.getErrorReason() == null ? ""
						: fileData.getErrorReason())
						+ "没有设定实际报关资料对应的列,或者文件中没有实际报关资料数据,请重新检查;");
			}
			
			
			if(isCode != null) {
				if(isSetValues[17]) {
					String scmCocKey = isCode?fileData.getScmCoc().getCode():fileData.getScmCoc().getName();
					ScmCoc scmCoc = scmcocMap.get(scmCocKey);
					if(scmCoc == null && CommonUtils.notEmpty(scmCocKey)) {
						hasError = true;
						fileData.setErrorReason("客户供应商不存在，客户供应商"
								+ (isCode ? "编码" : "名称")
								+ "："
								+ (isCode ? fileData.getScmCoc().getCode()
										: fileData.getScmCoc().getName()));
					} else {
						fileData.setScmCoc(scmCoc);
					}
				} else {
					hasError = true;
					fileData.setErrorReason("客户供应商没有选择对应列，客户供应商");
				}
			}

			// 2010-12-16新加查看是否重复，按料号+报告编码+报关名称+报关规格+报关单位+手册号
			if (!isDupImport && (fileData.getStatCusNameRelationHsn() != null)) {
				if (fileData.getStatCusNameRelationHsn().getComplex() != null
						&& fileData.getStatCusNameRelationHsn().getCusUnit() != null) {
					// key = 料号+报告编码+报关名称+报关规格+报关单位+手册号
					key = (fileData.getMateriel().getPtNo() == null ? "" : fileData.getMateriel().getPtNo()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getComplex().getCode() == null ? "" : fileData.getStatCusNameRelationHsn().getComplex().getCode()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusName() == null ? "" : fileData.getStatCusNameRelationHsn().getCusName()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusSpec() == null ? "" : fileData.getStatCusNameRelationHsn().getCusSpec()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getCusUnit().getName() == null ? "" : fileData.getStatCusNameRelationHsn().getCusUnit().getName()) 
						+ ","
						+ (fileData.getStatCusNameRelationHsn().getEmsNo() == null ? "" : fileData.getStatCusNameRelationHsn().getEmsNo())
						+ ","
						+ (fileData.getScmCoc() == null ? "" : (fileData.getScmCoc().getId() == null ? "" : fileData.getScmCoc().getId()));
					
					if (keySet.contains(key)) {
						hasError = true;
						fileData
								.setErrorReason((fileData.getErrorReason() == null ? ""
										: fileData.getErrorReason())
										+ "已经有此对应关系，不能导入，若要导入请选择覆盖导入选项;");
					}
				}
			}
			

			if (hasError) {
				fileData.setInvalidationRow(Integer.valueOf(i + 1));
				ls.add(fileData);
			} else {
				reBuildLs.add(fileData);
			}
			
			// 返回当前状态，处理完的条数信息。
			if (info != null && (i+1)%1000 == 0) {
				info.setCurrentNum(i);
			}
		}
		if (ls.size() > 0)
			return ls;
		else
			return reBuildLs;
	}

	/**
	 * 批量保存对应关系表
	 * 
	 * @param list
	 *            从文件导入的对应关系数据
	 * @param isReWriteMateriel 是否反写（将导入文件中的物料资料反写至数据库中的物料资料）
	 * @param materielType 物料类型
	 * @param isConver 是否覆盖（对应关系中的料号重复,覆盖导入）
	 * @param taskId 任务编号 for 进度条，
	 * @param map 任务编号 for 进度条，
	 */
	public List<FactoryAndFactualCustomsRalation> batchSaveFactoryAndFactualCustomsRalation(
			List<FactoryAndFactualCustomsRalation> list,
			boolean isReWriteMateriel, String materielType, boolean isConver,Map<String, Boolean> map,String taskId) {
		/*
		 * 显示进度信息
		 */
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		
		// 是否物料与报关一对多
		Boolean isOneToMany = map.get("isOneToMany");
		
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setMethodName("系统开始保存数据,如果数据量比较大的话时间会很长,请耐心等待...");
			info.setCurrentNum(0);
		}
		logger.info("系统准备数据");

		// //////////////////////////////////////////////////////////////////
		// 查询已存在的对应关系 FactoryAndFactualCustomsRalation
		// ///////////////////////////////////////////////////////////////////
		Map<String, FactoryAndFactualCustomsRalation> existMap = new HashMap<String, FactoryAndFactualCustomsRalation>();
		Map<String, FactoryAndFactualCustomsRalation> existMap2 = new HashMap<String, FactoryAndFactualCustomsRalation>();

		List<FactoryAndFactualCustomsRalation> factAndCust = this.casDao.findFactoryAndFactualCustomsRalation(materielType);
		
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date createDate = (java.sql.Date.valueOf(bartDateFormat
				.format(new Date())));// 导入修改时间
		
		logger.info("系统查询【物料类别】数据");
		// 所有【物料类别】
		List<ScmCoi> scmcoiList = this.materialManageDao.findScmCois();
		// 组装成 物料类别属性——》物料类别 的 hashmap 方便查询检索
		Map<String, ScmCoi> scmcoiMap = new HashMap<String, ScmCoi>(scmcoiList.size());
		for (int i = 0; i < scmcoiList.size(); i++) {
			scmcoiMap.put(scmcoiList.get(i).getCoiProperty(), scmcoiList.get(i));
		}
		
		logger.info("系统查询全部【商品编码】数据");
		// 查询全部【商品编码】的资料
		List<Complex> complexList = casDao.findComplex();
		// 组装成 编码——》商品编码 的 hashmap 方便查询检索
		Map<String, Complex> complexMap = new HashMap<String, Complex>();
		for (int i = 0; i < complexList.size(); i++) {
			complexMap.put(complexList.get(i).getCode(), complexList.get(i));
		}

//		logger.info("系统查询全部【工厂物料】数据");
		// 查询全部【工厂物料】的资料
//		List<EnterpriseMaterial> emList = this.materialManageDao
//				.findEnterpriseMaterial();
//		// 组装成 料号——》工厂物料 的 hashmap 方便查询检索
//		Map<String, EnterpriseMaterial> emMap = new HashMap<String, EnterpriseMaterial>();
//		for (int i = 0; i < emList.size(); i++) {
//			emMap.put(emList.get(i).getPtNo(), emList.get(i));
//		}
		
//		logger.info("系统查询全部【报关常用物料】数据");
//		// 查询全部【报关常用物料】的资料
//		List<Materiel> materielList = this.materialManageDao.findMateriel(Arrays.asList(new String[]{materielType}));
//		// 组装成 料号——》报关常用物料 的 hashmap 方便查询检索
//		Map<String, Materiel> materielMap = new HashMap<String, Materiel>();
//		for (int i = 0; i < materielList.size(); i++) {
//			materielMap.put(materielList.get(i).getPtNo(), materielList.get(i));
//		}
		
		logger.info("系统开始保存数据,如果数据量比较大的话时间会很长");
		// 需要保存的【海关对账】数据
		List<StatCusNameRelationHsn> scrSaveList = new ArrayList<StatCusNameRelationHsn>();
		// 需要保存的【报关常用工厂物料】数据
		List<Materiel> materielSavelList = new ArrayList<Materiel>();
		// 需要保存的【工厂物料】数据
		List<EnterpriseMaterial> emSaveList = new ArrayList<EnterpriseMaterial>();
		// 需要保存的【工厂和实际客户对应】数据
		List<FactoryAndFactualCustomsRalation> ffcSaveList = new ArrayList<FactoryAndFactualCustomsRalation>();

		//
		// 导入 FactoryAndFactualCustomsRalation
		//
		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			FactoryAndFactualCustomsRalation obj = (FactoryAndFactualCustomsRalation) list.get(i);
			Materiel mt = obj.getMateriel();
			StatCusNameRelationHsn sr = obj.getStatCusNameRelationHsn();
			
			String ptNo = mt.getPtNo() == null ? "" : mt.getPtNo().trim();
			String factoryName = mt.getFactoryName() == null ? "" : mt
					.getFactoryName().trim();
			String factorySpec = mt.getFactorySpec() == null ? "" : mt
					.getFactorySpec().trim();
			String ptNetWeight = mt.getPtNetWeight() == null ? "" : mt
					.getPtNetWeight().toString();
			String ptPrice = mt.getPtPrice() == null ? "" : mt.getPtPrice()
					.toString();
			String calUnit = mt.getCalUnit() == null ? "" : mt.getCalUnit()
					.getName().trim();
			String unitConvert = obj.getUnitConvert() == null ? "" : obj
					.getUnitConvert().toString();
			String emsNo = sr.getEmsNo() == null ? "" : sr.getEmsNo().trim();
			String seqNum = sr.getSeqNum() == null ? "" : sr.getSeqNum()
					.toString();
			String complexCode = sr.getComplex() == null ? "" : sr.getComplex()
					.getCode();
			String name = sr.getCusName() == null ? "" : sr.getCusName().trim();
			String spec = sr.getCusSpec() == null ? "" : sr.getCusSpec().trim();
			String unitName = sr.getCusUnit() == null ? "" : sr.getCusUnit()
					.getName().trim();
			String projectType = sr.getProjectType() == null ? "" : sr
					.getProjectType().toString();
			String key ="";
			if(isOneToMany){
				// key 料号 + 工厂商品名称 + 型号规格  + 账册号 + 商品编码 + 品名 + 规格 + 单位 + 项目类型  
				key = ptNo + "/" + factoryName + "/" + factorySpec + "/"
				+ emsNo + "/" + complexCode + "/" + name + "/" + spec + "/" + unitName
				+ "/" + projectType;
			}else{
				// key 料号 + 工厂商品名称 + 型号规格  + 商品编码 + 品名 + 规格 + 单位 + 项目类型  
				key = ptNo + "/" + factoryName + "/" + factorySpec + "/" + complexCode 
				+ "/" + name + "/" + spec + "/" + unitName+ "/" + projectType;
			}
			
//			Materiel materiel = materielMap.get(mt.getPtNo());
			Materiel materiel = this.materialManageDao.findMaterielByPtNo(mt.getPtNo());
			// 判断是否反写 数据库中物流资料，需要就更新，不需要跳过
			// 如果存在
			if (materiel != null) {
				// 1.确定要反写的【报关常用工厂物料】
				if (isReWriteMateriel) {
					materiel.setFactoryName(mt.getFactoryName());
					materiel.setFactorySpec(mt.getFactorySpec());
					materiel.setPtNetWeight(mt.getPtNetWeight());
					materiel.setPtPrice(mt.getPtPrice());
					materiel.setCalUnit(mt.getCalUnit());
					materiel.setScmCoi(mt.getScmCoi());
					materiel.setComplex(sr.getComplex());// 报关编码
					materiel.setPtName(sr.getCusName());// 报关名称
					materiel.setPtSpec(sr.getCusSpec());// 报关规格
					materiel.setPtUnit(sr.getCusUnit());// 报关单位
					materiel.setCompany(CommonUtils.getCompany());
					materiel.setModifyDate(createDate);
					
					materielSavelList.add(materiel);
				}
				mt = materiel;
				obj.setMateriel(mt);
			}else {	// 如果不存在
				mt.setComplex(sr.getComplex());// 报关编码
				mt.setPtName(sr.getCusName());// 报关名称
				mt.setPtSpec(sr.getCusSpec());// 报关规格
				mt.setPtUnit(sr.getCusUnit());// 报关单位
				mt.setCompany(CommonUtils.getCompany());
				mt.setCreateDate(createDate);
				materielSavelList.add(mt);
			}
				
			// 2.确定要反写的 【工厂物料】
//			EnterpriseMaterial enterMaterial = emMap.get(ptNo);
			if (isReWriteMateriel) {
				EnterpriseMaterial enterMaterial = this.materialManageDao.findEnterpriseMaterialByPtNo(ptNo);
				if (enterMaterial != null) {// 存在
					enterMaterial.setFactoryName(mt.getFactoryName());
					enterMaterial.setFactorySpec(mt.getFactorySpec());
					enterMaterial.setPtNetWeight(mt.getPtNetWeight());
					enterMaterial.setPtPrice(mt.getPtPrice());
					enterMaterial.setCalUnit(mt.getCalUnit());
					enterMaterial.setComplex(sr.getComplex());// 报关编码
					enterMaterial.setPtName(sr.getCusName());// 报关名称
					enterMaterial.setPtSpec(sr.getCusSpec());// 报关规格
					enterMaterial.setPtUnit(sr.getCusUnit());// 报关单位
					enterMaterial.setCompany(CommonUtils.getCompany());
					enterMaterial.setCreateDate(createDate);
					emSaveList.add(enterMaterial);
				} else {
					ScmCoi scmcoi = scmcoiMap.get(materielType);
					
					EnterpriseMaterial m = new EnterpriseMaterial();
					m.setPtNo(mt.getPtNo());
					m.setFactoryName(mt.getFactoryName());
					m.setFactorySpec(mt.getFactorySpec());
					m.setPtNetWeight(mt.getPtNetWeight());
					m.setPtPrice(mt.getPtPrice());
					m.setCalUnit(mt.getCalUnit());
					m.setScmCoi(scmcoi);
					m.setComplex(sr.getComplex());// 报关编码
					m.setPtName(sr.getCusName());// 报关名称
					m.setPtSpec(sr.getCusSpec());// 报关规格
					m.setPtUnit(sr.getCusUnit());// 报关单位
					m.setCompany(CommonUtils.getCompany());
					m.setCreateDate(createDate);
					emSaveList.add(m);
				}
				// 3.如果不存在 Complex 这个实体
				if (sr.getId() == null) {
					String tempComplexCode = obj.getTemp2().trim();
					// 如果实际报关商品为空的时候填写,实际报关资料
					if (sr.getComplex() == null || tempComplexCode != null) {
						Complex complex = (Complex) complexMap.get(tempComplexCode);
						sr.setComplex(complex);
					}
					sr.setCompany(CommonUtils.getCompany());
					sr.setCreateDate(createDate);
					scrSaveList.add(sr);
				}
			}
				
			if(factAndCust.size()==0){
				ffcSaveList.add(obj);
			}
			
			FactoryAndFactualCustomsRalation existF = null;
			int factAndCustSize = factAndCust.size();
			String fkey ="";
			Materiel fmt = null;
			String fptNo = null;
			String ffactoryName = null;
			String ffactorySpec =null;
			String fptNetWeight = null;
			String fptPrice = null;
			String fcalUnit = null;
			String funitConvert = null;
			String femsNo = null;
			String fseqNum = null;
			String fcomplexCode = null;
			String fname = null;
			String fspec = null;
			String funitName = null;
			String fprojectType = null;
			StatCusNameRelationHsn fsr = null;
			boolean isExist =false;//是否存在
			for (int j = 0;j < factAndCustSize; j++) {
				existF = factAndCust.get(j);
				fmt = existF.getMateriel();
				fsr = existF.getStatCusNameRelationHsn();
				fptNo = fmt.getPtNo() == null ? "" : fmt.getPtNo().trim();
				ffactoryName = fmt.getFactoryName() == null ? "" : fmt.getFactoryName().trim();
				ffactorySpec = fmt.getFactorySpec() == null ? "" : fmt.getFactorySpec().trim();
				fptNetWeight = fmt.getPtNetWeight() == null ? "" : fmt.getPtNetWeight().toString();
				fptPrice = fmt.getPtPrice() == null ? "" : fmt.getPtPrice()	.toString();
				fcalUnit = fmt.getCalUnit() == null ? "" : fmt.getCalUnit()	.getName().trim();
				funitConvert = existF.getUnitConvert() == null ? "" : existF.getUnitConvert().toString();
				
				femsNo = fsr.getEmsNo() == null ? "" : fsr.getEmsNo().trim();
				fseqNum = fsr.getSeqNum() == null ? "" : fsr.getSeqNum().toString();
				fcomplexCode = fsr.getComplex() == null ? "" : fsr.getComplex()	.getCode();
				fname = fsr.getCusName() == null ? "" : fsr.getCusName().trim();
				fspec = fsr.getCusSpec() == null ? "" : fsr.getCusSpec().trim();
				funitName = fsr.getCusUnit() == null ? "" : fsr.getCusUnit().getName().trim();
				fprojectType = fsr.getProjectType() == null ? "" : fsr.getProjectType().toString();
				
				if(isOneToMany){// 多对一
					//
					// // key 料号 + 工厂商品名称 + 型号规格 + 账册号 + 商品编码 + 品名 + 规格 + 单位
					// + 项目类型 ,用于覆盖对应关系 ..
					//
					fkey = fptNo + "/" + ffactoryName + "/" + ffactorySpec + "/"+ femsNo +"/"
							+ fcomplexCode + "/" + fname + "/" + fspec + "/" + funitName
							+ "/" + fprojectType;
				}else{ //一对一或者多对一
					//
					// key 料号 + 工厂商品名称 + 型号规格 + 商品编码 + 品名 + 规格 + 单位 +
					// 项目类型,用于覆盖对应关系 ..
					//
					fkey = fptNo + "/" + ffactoryName + "/" + ffactorySpec + "/"
					       + fcomplexCode + "/" + fname + "/" + fspec + "/" + funitName
					       + "/" + fprojectType;
				}
				if(key.equals(fkey)){
					// 如果已经存在
					if(existF != null) {
						// 判断是否需要覆盖key值相同的对应关系
						isExist = true;
						if(isConver) {
							existF.setMateriel(mt);
							existF.setStatCusNameRelationHsn(sr);
							// 单位折算
							Double uc = obj.getUnitConvert();
							if (uc != null && uc.doubleValue() != 0) {// 若单位折算为空不覆盖，反之则覆盖
								existF.setUnitConvert(uc);
								existF.setCreateDate(createDate);
							}
							
							existF.setCompany(CommonUtils.getCompany());
							ffcSaveList.add(existF);
						} else { // 不覆盖就新增
							obj.setCompany(CommonUtils.getCompany());
							obj.setCreateDate(createDate);
							ffcSaveList.add(obj);
							break;
						}
					} else { //如果查找到最后一笔，不存在需要保存
						if(j==factAndCustSize-1&&!isExist){
							obj.setCompany(CommonUtils.getCompany());
							obj.setCreateDate(createDate);
							ffcSaveList.add(obj);
						}
					}
				}else{//如果查找到最后一笔，不存在需要保存
					if(j==factAndCustSize-1&&!isExist){
						obj.setCompany(CommonUtils.getCompany());
						obj.setCreateDate(createDate);
						ffcSaveList.add(obj);
					}
				}
			}
		}
		// 保存【海关对账】数据
		info.setMethodName("系统保存【海关对账】数据,一共" + scrSaveList.size() + "笔,请耐心等待...");
		casDao.batchSaveOrUpdate(scrSaveList, info);
		// 保存【报关常用工厂物料】数据
		info.setMethodName("系统保存【报关常用工厂物料】数据,一共" + materielSavelList.size() + "笔,请耐心等待...");
		casDao.batchSaveOrUpdate(materielSavelList, info);
		// 保存【工厂物料】数据
		info.setMethodName("系统保存【工厂物料】数据,一共" + emSaveList.size() + "笔,请耐心等待...");
		casDao.batchSaveOrUpdate(emSaveList, info);
		// 保存【工厂和实际客户对应】数据
		info.setMethodName("系统保存【工厂和实际客户对应】数据,一共" + ffcSaveList.size() + "笔,请耐心等待...");
		casDao.batchSaveOrUpdate(ffcSaveList, info);
		
		return list;
	}

	/**
	 * 检查导入的数据的正确性，并输出错误提示,用于实际报关资料导入
	 * 
	 * @param list
	 * @param ht
	 * @param taskId
	 * @param materielType
	 * @param isCover
	 * @return
	 */
	public List checkImportDataForHsn(List list, Hashtable ht, String taskId,
			String materielType) {
		List<StatCusNameRelationHsn> ls = new ArrayList();
		List<StatCusNameRelationHsn> reBuildLs = new ArrayList();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		StatCusNameRelationHsn fileData = null;
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
			info.setMethodName("系统正在检验文件资料");
		}

		System.out.println("the filsize is " + list.size());
		List hsnList = this.casDao.findKeyFromStatCusNameRelationHsn(materielType);// 实际报关资料Key
		List<Complex> codeList = this.hsCodeDao.findComplexAll();// 在自用商品编码库中存在的编码
		List unitList = this.materialManageDao.findUnitName();// 报关单位名称
		Map<String, Complex> codeMap = new HashMap();
		for (Complex item : codeList) {
			if (item != null) {
				System.out.println("--------------------------------"+item.getCode());
				codeMap.put(item.getCode(), item);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			temp.clear();
			fileData = (StatCusNameRelationHsn) list.get(i);
			if (Integer.parseInt(ht.get(Integer.valueOf(1)).toString()) > -1) {
				if (codeMap != null && codeMap.get(fileData.getTemp1()) != null) {
					fileData.setComplex(codeMap.get(fileData.getTemp1()));
				} else {
					temp.add(Integer.valueOf(1));
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "此海关编码不存在,请检查;");
				}
			}
			if (Integer.parseInt(ht.get(Integer.valueOf(2)).toString()) > -1) {
				if (unitList != null && unitList.contains(fileData.getTemp2())) {
					Unit unit = this.materialManageDao
							.findUnitByNameFromUnit(fileData.getTemp2());
					fileData.setCusUnit(unit);
				} else {
					temp.add(Integer.valueOf(11));
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "此报关单位名称不存在;");
				}
			}
			if (Integer.parseInt(ht.get(Integer.valueOf(6)).toString()) > -1
					&& fileData.getProjectType() != null
					&& fileData.getProjectType() != ProjectType.BCUS) {
				if (fileData.getEmsBeginDate() == null) {
					temp.add(Integer.valueOf(6));
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "手册开始日期不为日期格式;");
				}
			}
			if (Integer.parseInt(ht.get(Integer.valueOf(7)).toString()) > -1
					&& fileData.getProjectType() != null
					&& fileData.getProjectType() != ProjectType.BCUS) {
				if (fileData.getEmsEndDate() == null) {
					temp.add(Integer.valueOf(7));
					fileData
							.setErrorReason((fileData.getErrorReason() == null ? ""
									: fileData.getErrorReason())
									+ "手册结束日期不为日期格式;");
				}
			}

			String matchStr = (fileData.getComplex() == null ? "" : fileData
					.getComplex().getCode())
					+ (fileData.getCusName() == null ? "" : fileData
							.getCusName())
					+ (fileData.getCusSpec() == null ? "" : fileData
							.getCusSpec())
					+ (fileData.getCusUnit() == null ? "" : fileData
							.getCusUnit().getName())
					+ (fileData.getEmsNo() == null ? "" : fileData.getEmsNo());
			matchStr = matchStr.replaceAll(" ", "");
			if (Integer.parseInt(ht.get(Integer.valueOf(1)).toString()) > -1
					&& Integer.parseInt(ht.get(Integer.valueOf(2)).toString()) > -1
					&& Integer.parseInt(ht.get(Integer.valueOf(3)).toString()) > -1
					&& Integer.parseInt(ht.get(Integer.valueOf(4)).toString()) > -1) {
				// 商品编码和单位,日期已通过验证
				if (!temp.contains(Integer.valueOf(1))
						&& !temp.contains(Integer.valueOf(2))
						&& !temp.contains(Integer.valueOf(6))
						&& !temp.contains(Integer.valueOf(7))) {
					if (hsnList != null && hsnList.contains(matchStr.trim())) {
						// 如果在实际报关资料中存在,则剔除
						list.remove(fileData);
						i--;
					}
				}
			} else {
				temp.add(Integer.valueOf(0));
				fileData.setErrorReason((fileData.getErrorReason() == null ? ""
						: fileData.getErrorReason())
						+ "没有设定实际报关资料对应的列,或者文件中没有实际报关资料数据,请重新检查;");
			}
			if (temp.size() > 0) {
				fileData.setInvalidationRow(Integer.valueOf(i + 1));
				if (list.contains(fileData))
					ls.add(fileData);
			} else {
				if (list.contains(fileData))
					reBuildLs.add(fileData);
			}
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
		if (ls.size() > 0)
			return ls;
		else
			return reBuildLs;
	}

	/**
	 * 获取工厂通用代码DAO类实例
	 */
	public MaterialManageDao getMaterialManageDaoo() {
		return materialManageDao;
	}

	/**
	 * 设置工厂通用代码DAO类实例
	 * 
	 */
	public void setMaterialManageDao(MaterialManageDao dao) {
		this.materialManageDao = dao;
	}

	/**
	 * 获取海关编码DAO类实例
	 */
	public HsCodeDao getHsCodeDao() {
		return hsCodeDao;
	}

	/**
	 * 设置海关编码DAO类实例
	 * 
	 */
	public void setHsCodeDao(HsCodeDao dao) {
		this.hsCodeDao = dao;
	}

	/**
	 * 删除与发票资料一致的单据资料
	 * 
	 * @param list
	 */
	public void deleteInvoiceAndBillCorresponding(List list) {
		for (int m = 0; m < list.size(); m++) {
			InvoiceAndBillCorresponding data = (InvoiceAndBillCorresponding) list
					.get(m);
			List lista = this.casDao.findCasInvoiceInfoById(data
					.getInvoiceInfokey());
			if (lista.size() == 0) {
				casDao.delete(data);
				continue;
			}
			List listb = this.casDao.findBillDetailMaterielById(data
					.getBillDetailkey());
			if (listb.size() == 0) {
				casDao.delete(data);
				continue;
			}
			CasInvoiceInfo invoice = (CasInvoiceInfo) lista.get(0);
			BillDetailMateriel me = (BillDetailMateriel) listb.get(0);
			if (!"1107".equals(me.getBillMaster().getBillType().getCode())) {// 其它料件退货单
				invoice.setToBillNum((invoice.getToBillNum() == null ? 0.0
						: invoice.getToBillNum())
						- data.getInvoiceNum());
			} else {
				invoice.setToBillNum((invoice.getToBillNum() == null ? 0.0
						: invoice.getToBillNum())
						+ data.getInvoiceNum());
			}
			invoice.setToBillsAll(false);
			me.setCustomNum(me.getCustomNum() == null ? 0.0 : me.getCustomNum()
					- data.getInvoiceNum());
			String str = me.getCustomNo() == null ? "" : me.getCustomNo();
			String[] strs = str.split(",");
			String no = "";
			for (int i = 0; i < strs.length; i++) {
				if (!strs[i].equals(invoice.getCasInvoice().getInvoiceNo())) {
					no += (invoice.getCasInvoice().getInvoiceNo() + ",");
				}
			}
			me.setCustomNo(no);
			casDao.saveOrUpdate(invoice);
			casDao.saveOrUpdate(me);
			casDao.delete(data);
		}
	}

	/**
	 * 通过类型获取物料
	 * 
	 * @param typename
	 * @return
	 */
	public Map getMaterialByType(String typename) {
		Map map = new HashMap();
		String type = "";
		String type2 = "";
		if (typename != null && typename.equals("料件")) {
			type = MaterielType.MATERIEL;
		} else if (typename != null && typename.equals("成品")) {
			type = MaterielType.FINISHED_PRODUCT;
		} else if (typename != null && typename.equals("设备")) {
			type = MaterielType.MACHINE;
		} else if (typename != null && typename.equals("半成品")) {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
		} else if (typename != null && typename.equals("残次品(料件)")) {
			type = MaterielType.MATERIEL;
		} else if (typename != null && typename.equals("残次品(成品)")) {
			type = MaterielType.FINISHED_PRODUCT;

		} else if (typename != null && typename.equals("残次品(半成品)")) {
			type = MaterielType.SEMI_FINISHED_PRODUCT;
			type2 = MaterielType.BAD_PRODUCT;

		} else if (typename != null && typename.equals("边角料")) {
			type = MaterielType.REMAIN_MATERIEL;
		}
		List list = this.materialManageDao.findMaterielByMaterielType(type);

		// wss2010.07.12残次品导入时包括 半成品 及 残次品
		if (MaterielType.BAD_PRODUCT == type2) {
			list.addAll(this.materialManageDao
					.findMaterielByMaterielType(type2));
		}
		// List list = this.materialManageDao.findMateriel();
		for (int i = 0; i < list.size(); i++) {
			Materiel mt = (Materiel) list.get(i);
			if (mt == null || mt.getPtNo() == null) {
				continue;
			}
			map.put(mt.getPtNo(), mt);
		}
		return map;
	}

	/**
	 * 返回半成品制单号
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * 
	 * @author 石小凯
	 * 
	 * @return 工厂商品名称
	 */
	public List<TempObject> findMWrokOrder(int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		String sql = null;
		sql = "select distinct a.productNo from BillDetailHalfProduct a where a.productNo!=null";
		List sourceList2 = this.casBillDao.find(sql);

		List sourceList = new ArrayList();
		for (int i = 0; i < sourceList2.size(); i++) {
			if (!sourceList.contains(sourceList2.get(i)))
				sourceList.add(sourceList2.get(i));
		}
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回成品制单号
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * 
	 * @author 石小凯
	 * 
	 * @return 工厂商品名称
	 */
	public List<TempObject> findPWrokOrder(int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		String sql = null;
		sql = "select distinct a.productNo from BillDetailProduct a where a.productNo!=null";
		List sourceList2 = this.casBillDao.find(sql);

		List sourceList = new ArrayList();
		for (int i = 0; i < sourceList2.size(); i++) {
			if (!sourceList.contains(sourceList2.get(i)))
				sourceList.add(sourceList2.get(i));
		}
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回成品、半成品制单号
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * 
	 * @author 石小凯
	 * 
	 * @return 工厂商品名称
	 */
	public List<TempObject> findMPWrokOrder(int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		String sql = null;

		sql = "select distinct a.productNo from BillDetailProduct a where a.productNo!=null";
		List sourceList2 = this.casBillDao.find(sql);

		sql = "select distinct a.productNo from BillDetailHalfProduct a where a.productNo!=null";
		List sourceList3 = this.casBillDao.find(sql);

		sourceList2.addAll(sourceList3);

		List sourceList = new ArrayList();

		for (int i = 0; i < sourceList2.size(); i++) {
			if (!sourceList.contains(sourceList2.get(i)))
				sourceList.add(sourceList2.get(i));
		}
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回残次品制单号
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * 
	 * @author 石小凯
	 * 
	 * @return 工厂商品名称
	 */
	public List<TempObject> findBWrokOrder(int index, int length,
			String property, Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		String sql = null;
		sql = "select distinct a.productNo from BillDetailRemainProduct a where a.productNo!=null";
		List sourceList2 = this.casBillDao.find(sql);

		List sourceList = new ArrayList();
		for (int i = 0; i < sourceList2.size(); i++) {
			if (!sourceList.contains(sourceList2.get(i)))
				sourceList.add(sourceList2.get(i));
		}
		for (int i = 0; i < sourceList.size(); i++) {
			TempObject temp = new TempObject();
			if (sourceList.get(i) == null
					|| sourceList.get(i).toString().trim().equals("")) {
				continue;
			}
			temp.setObject(sourceList.get(i));
			list.add(temp);
		}
		return list;
	}

	/**
	 * 返回实际报关的商品名称来自实际报关商品
	 * 
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 实际报关商品名称
	 * 
	 * @author wss
	 */
	public List<TempObject> findHsFromFactoryAndFactualCustomsRalation(
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.casBillDao
				.findHsFromFactoryAndFactualCustomsRalation(materielType,
						index, length, property, value, isLike);
		for (int i = 0; i < sourceList.size(); i++) {
			Object[] objs = (Object[]) sourceList.get(i);
			if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
				continue;
			}
			TempObject temp = new TempObject();
			temp.setObject3((String) objs[3]);
			temp.setObject((String) objs[0]); // 名称
			temp.setObject1((String) objs[1]);// 规格
			temp.setObject2((Unit) objs[2]);// 单位

			list.add(temp);
		}
		return list;
	}

	/**
	 * 分页查询CheckBalance
	 * 
	 * @author wss
	 */
	public List findCheckBalance(Request request, Date beginDate, Date endDate,
			String ptNo, String kcType, String wlType, WareSet wareSet) {
		return null;
	}

	/**
	 * 分页查询CheckBalanceConvertMateriel
	 * 
	 * @author wss
	 */
	public List findCheckBalanceConvertM(Request request, Date beginDate,
			Date endDate, String ptNo, String kcType, String wlType,
			WareSet wareSet) {
		return null;
	}
}
