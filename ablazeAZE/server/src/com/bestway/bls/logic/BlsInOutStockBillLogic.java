package com.bestway.bls.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bls.dao.BlsInOutStockBillDao;
import com.bestway.bls.entity.BillMatchInfo;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.MakeBillToWarehouse;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.entity.StorageBillBefore;
import com.bestway.bls.entity.TempInOutStockBills;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 进出仓单据Logic类
 * 
 * @author hw
 * 
 */
public class BlsInOutStockBillLogic {
	/**
	 * 进出仓单据DAO类
	 */
	private BlsInOutStockBillDao blsInOutStockBillDao = null;

	public BlsInOutStockBillDao getBlsInOutStockBillDao() {
		return blsInOutStockBillDao;
	}

	public void setBlsInOutStockBillDao(
			BlsInOutStockBillDao blsInOutStockBillDao) {
		this.blsInOutStockBillDao = blsInOutStockBillDao;
	}

	/**
	 * 导入出入仓商品明细
	 * 
	 * @param materielList
	 *            是Materiel型，报关常用物料
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInOutStockBillDetail 型
	 */
	public List importInnerMergeDataFromMateriel(BlsInOutStockBill bsb,
			List materielList) {
		List<BlsInOutStockBillDetail> returnList = new ArrayList<BlsInOutStockBillDetail>();
		for (int i = 0; i < materielList.size(); i++) {
			Materiel materiel = (Materiel) materielList.get(i);
			/**
			 * 使用为真
			 */
			// materiel.setIsUseInBlsInnerMerge(true);
			this.blsInOutStockBillDao.saveOrUpdate(materiel);
			BlsInOutStockBillDetail b = new BlsInOutStockBillDetail();
			b.setBsb(bsb);
			b.setNetWeight(materiel.getPtNetWeight());
			b.setGrossWeight(materiel.getPtOutWeight());
			b.setDetailQty(materiel.getAmount());
			b.setUnitPrice(materiel.getPtPrice());
			b.setUnit(materiel.getCalUnit());
			b.setSpec(materiel.getFactorySpec());
			b.setWarehouseName(materiel.getFactoryName());
			// b.setTotalPrice(totalPrice)
			b.setPartNo(materiel);
			this.blsInOutStockBillDao.saveBlsInOutStockBillDetail(b);
			returnList.add(b);
		}
		return returnList;
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
	 * 获得最大的单据号来自出入仓单据表
	 * 
	 * @param type
	 *            此参数没有被用到
	 * @return 最大的编号或者是0001
	 */
	public String getMaxBillNoByType() {
		String strtoday = convertDateToString();
		List list = blsInOutStockBillDao.getMaxBillNoByType(strtoday);
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
	 * 转抄出入仓单据
	 * 
	 * @param BlsInOutStockBill
	 *            要转抄的出入仓单据表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @param billNo
	 *            单据号
	 * @return 新的出入仓单据表头
	 */
	public BlsInOutStockBill copyBlsInOutStockBillAndCommodityInfo(
			BlsInOutStockBill blsInOutStockBill, Boolean copyInfo, String billNo) {
		BlsInOutStockBill newBlsInOutStockBill = new BlsInOutStockBill();
		newBlsInOutStockBill.setCorrOwner(blsInOutStockBill.getCorrOwner());
		newBlsInOutStockBill.setBillNo(billNo);
		newBlsInOutStockBill.setIoFlag(blsInOutStockBill.getIoFlag());
		newBlsInOutStockBill.setWareHouseCode(blsInOutStockBill
				.getWareHouseCode());
		newBlsInOutStockBill.setValidDate(blsInOutStockBill.getValidDate());
		List arrayList = new ArrayList();
		if (copyInfo) {
			List list = blsInOutStockBillDao
					.findBlsInOutStockBillDetail(blsInOutStockBill.getId());
			for (int i = 0; i < list.size(); i++) {
				BlsInOutStockBillDetail blsInOutStockBillDetail = (BlsInOutStockBillDetail) list
						.get(i);
				BlsInOutStockBillDetail newblsInOutStockBillDetail = (BlsInOutStockBillDetail) blsInOutStockBillDetail
						.clone();
				newblsInOutStockBillDetail.setId(null);
				newblsInOutStockBillDetail.setBsb(newBlsInOutStockBill);
				newblsInOutStockBillDetail.setIsStockBill(false);
				newblsInOutStockBillDetail.setCurr(blsInOutStockBillDetail
						.getCurr());
				newblsInOutStockBillDetail
						.setOriginCountry(blsInOutStockBillDetail
								.getOriginCountry());
				newblsInOutStockBillDetail.setNetWeight(blsInOutStockBillDetail
						.getNetWeight());
				newblsInOutStockBillDetail
						.setGrossWeight(blsInOutStockBillDetail
								.getGrossWeight());
				newblsInOutStockBillDetail.setPackCount(blsInOutStockBillDetail
						.getPackCount());
				newblsInOutStockBillDetail.setPartNo(blsInOutStockBillDetail
						.getPartNo());
				arrayList.add(newblsInOutStockBillDetail);
			}
			this.blsInOutStockBillDao
					.saveBlsInOutStockBill(newBlsInOutStockBill);
			if (!arrayList.isEmpty()) {
				this.blsInOutStockBillDao
						.saveBlsInOutStockBillDetails(arrayList);
			}
		}
		return newBlsInOutStockBill;
	}

	/**
	 * 找出归并前的进出仓信息
	 * 
	 * @param sbill
	 * @return
	 */
	public List findStorageBillBeforeForSwitch(StorageBill sbill) {
		List reList = new ArrayList();
		Map rmap = new HashMap();
		List tlist = new ArrayList();
		List hlist = blsInOutStockBillDao.findStorageBillAfter(sbill);
		for (int i = 0; i < hlist.size(); i++) {
			StorageBillAfter aft = (StorageBillAfter) hlist.get(i);
			if (aft.getSeqNum() == null || aft.getCodeTS() == null) {
				continue;
			}
			List slist = blsInOutStockBillDao.findStorageBillBefore(aft);
			for (int j = 0; j < slist.size(); j++) {
				StorageBillBefore bfe = (StorageBillBefore) slist.get(j);
				if (bfe.getPartNo() == null) {
					continue;
				}
				String key = aft.getSeqNum().toString() + "/"
						+ aft.getCodeTS().getCode() + "/"
						+ bfe.getPartNo().getPtNo();
				System.out.println("key1---------------" + key);
				tlist.add(key);
			}
		}
		List innerList = this.blsInOutStockBillDao.findStorageBillBefore();
		for (int i = 0; i < innerList.size(); i++) {
			BlsInnerMerge bls = (BlsInnerMerge) innerList.get(i);
			if (bls.getBlsTenInnerMerge() == null
					|| bls.getBlsTenInnerMerge().getSeqNum() == null
					|| bls.getBlsTenInnerMerge().getComplex() == null
					|| bls.getMateriel() == null) {
				continue;
			}
			String key = bls.getBlsTenInnerMerge().getSeqNum().toString() + "/"
					+ bls.getBlsTenInnerMerge().getComplex().getCode() + "/"
					+ bls.getMateriel().getPtNo();
//			System.out.println("key2---------------" + key);
			if (!tlist.contains(key)) {
				reList.add(bls);
			}
		}
		return reList;
	}

	/**
	 * 保存归并前归并后的进出仓单表体信息
	 * 
	 * @param list
	 * @return
	 */
	public List saveStorageBillAfterAndBefore(List list) {
		List hasList = (List) list.get(0);
		saveOrUpdateObjects(hasList);
		// List headList = (List) list.get(1);
		// saveOrUpdateObjects(headList);
		// Map nomap = (Map) list.get(2);
		// for (int i = 0; i < headList.size(); i++) {
		// StorageBillAfter atfs = (StorageBillAfter) headList.get(i);
		//			
		// System.out.println("atfs.getOriginCountry().getName()="+atfs.getOriginCountry().getName());
		// if (atfs.getCodeTS() == null || atfs.getSeqNum() == null ||
		// atfs.getOriginCountry()==null) {
		// continue;
		// }
		// String keys = atfs.getCodeTS().getCode() + "/"
		// + atfs.getSeqNum().toString()+"/"+atfs.getOriginCountry().getName();
		//			
		// System.out.println("keys="+keys);
		// List delist = (List) nomap.get(keys);
		//			
		// System.out.println("delist.size()="+delist.size());
		// if (delist == null) {
		// continue;
		// }
		// for (int j = 0; j < delist.size(); j++) {
		// StorageBillBefore bef = (StorageBillBefore) delist.get(j);
		// if (bef == null) {
		// continue;
		// }
		// bef.setStorageBillAfter(atfs);
		// saveOrUpdateObject(bef);
		// }
		// }
		return hasList;
	}

	/**
	 * 新建或更新单据对应信息
	 * 
	 * @param matchInfo
	 * @return
	 */
	public void saveBillMatchInfo(List<BillMatchInfo> billMatchInfos) {
		for (BillMatchInfo info : billMatchInfos) {
			this.blsInOutStockBillDao.saveBillMatchInfo(info);
		}
	}

	/**
	 * 保存对象方法
	 * 
	 * @param objs
	 * @return
	 */
	public List saveOrUpdateObjects(List objs) {
		if (objs == null) {
			System.out.println("Exception :  objs  is null !!!");
			return new ArrayList();
		}
		for (int i = 0; i < objs.size(); i++) {
			blsInOutStockBillDao.saveOrUpdate(objs.get(i));
		}
		return objs;
	}

	/**
	 * 保存对象方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object saveOrUpdateObject(Object obj) {
		blsInOutStockBillDao.saveOrUpdate(obj);
		return obj;
	}

	/**
	 * 转仓单方法
	 */
	public List makeBillToWarehouseWarrnat(MakeBillToWarehouse paras) {
		List arrayList = new ArrayList();
		BlsInnerMerge inner = new BlsInnerMerge();
		inner.setMateriel(paras.getPartNo());
		arrayList.add(inner);
		return arrayList;
	}

	/**
	 * 保存进出仓单据表头及表体,文本导入时使用.
	 */
	public List saveInOutStockBillAndImpExpCommodityInfo(List list,
			boolean isMergeCommInfo) {
		Map<String, BlsInOutStockBill> mapHead = new HashMap<String, BlsInOutStockBill>();
		Map<String, BlsInOutStockBillDetail> mapDetail = new HashMap<String, BlsInOutStockBillDetail>();
		Map<String, Integer> mapCount = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			TempInOutStockBills temp = (TempInOutStockBills) list.get(i);
			BlsInOutStockBill bsb = temp.getBsb();
			BlsInOutStockBillDetail bsd = temp.getBsd();
			String key = bsb.getBillNo()
					+ (bsb.getCorrOwner() == null ? "" : bsb.getCorrOwner()
							.getCode());
			Integer count = mapCount.get(key);
			if (count == null) {
				count = this.blsInOutStockBillDao
						.findInOutStockBillCountByBillNo(bsb.getBillNo(), bsb
								.getCorrOwner());
				mapCount.put(key, count);
			}
			if (count > 0) {
				continue;
			}
			if (mapHead.get(key) == null) {
				if (bsb.getValidDate() != null) {
					bsb.setCreateDate(bsb.getValidDate());
				} else {
					bsb.setCreateDate(new Date());
				}
				this.blsInOutStockBillDao.saveBlsInOutStockBill(bsb);
				mapHead.put(key, bsb);
			} else {
				bsb = (BlsInOutStockBill) mapHead.get(key);
			}
			if (isMergeCommInfo) {
				System.out.println("bsd=" + bsd);
				String commInfoKey = key
						+ bsd.getPartNo().getPtNo()
						+ (bsd.getOriginCountry() == null ? "" : bsd
								.getOriginCountry().getCode());
				if (mapDetail.get(commInfoKey) == null) {
					bsd.setBsb(mapHead.get(key));
					this.blsInOutStockBillDao.saveBlsInOutStockBillDetail(bsd);
					mapDetail.put(commInfoKey, bsd);
				} else {
					BlsInOutStockBillDetail oldCommInfo = mapDetail
							.get(commInfoKey);
					oldCommInfo.setDetailQty(CommonUtils
							.getDoubleExceptNull(oldCommInfo.getDetailQty())
							+ CommonUtils.getDoubleExceptNull(bsd
									.getDetailQty()));
					this.blsInOutStockBillDao
							.saveBlsInOutStockBillDetail(oldCommInfo);
				}
			}
		}
		List lsResult = new ArrayList();
		lsResult.addAll(mapHead.values());
		return lsResult;
	}

	/**
	 * 删除进出仓单据表头表体信息
	 * 
	 * @param blsInOutStockBill
	 */
	public void deleteBlsInOutStockBillHeadAndBody(
			BlsInOutStockBill blsInOutStockBill) {
		List after = this.blsInOutStockBillDao
				.findBlsInOutStockBillDetail(blsInOutStockBill.getId());
		// for (int i = 0; i < after.size(); i++) {
		// List list=null;
		// BlsInOutStockBillDetail bsd = (BlsInOutStockBillDetail) after.get(i);
		// list.add(bsd);
		// this.blsInOutStockBillDao.deleteAll(list);
		// }
		this.blsInOutStockBillDao.deleteAll(after);
		this.blsInOutStockBillDao.delete(blsInOutStockBill);
	}

	/**
	 * 查询出仓单据里的没有对应转入单据商品，或不存在的对应转入单据商品信息
	 * 
	 * @param request
	 * @return
	 */
	public List findBlsOutStockBillDetailNoInputBill() {
		List<String[]> names = new ArrayList<String[]>();

		// 查询出仓单据里的商品信息
		List<BlsInOutStockBillDetail> details = blsInOutStockBillDao
				.findBlsOutStockBillDetailNoInputBill();

		// 查询出入仓单据里的所有单据号
		List<String> bills = blsInOutStockBillDao.findAllInBillNo();

		// 入仓单据里的所有单据号map
		Map billMap = new HashMap<String, String>();
		for (String key : bills) {
			billMap.put(key, "");
		}
		Long start = System.currentTimeMillis();
		// 获取当出仓单据对应的入仓单据为空，或不存在的名称，规格
		for (BlsInOutStockBillDetail detail : details) {
			if (detail.getInBillNo() == null
					|| !billMap.containsKey(detail.getInBillNo())) {
				String[] strs = new String[2];
				strs[0] = (String) (detail.getWarehouseName() == null ? ""
						: detail.getWarehouseName().trim());
				strs[1] = (String) (detail.getSpec() == null ? "" : detail
						.getSpec().trim());
				names.add(strs);
			}
		}
		System.out.println("筛选结果发了：" + (System.currentTimeMillis() - start));
		return names;
	}

	/**
	 * 查询出仓单据待对应的商品停息
	 * 
	 * @param warehouseName商品名称
	 * @param spec商品规格
	 * @param scmCoc客户
	 * @param beginDate开始日期
	 * @param endDate结束日期
	 */
	public List findBlsOutStockBillDetail(String warehouseName, String spec,
			ScmCoc scmCoc, Date beginDate, Date endDate) {
		List<BlsInOutStockBillDetail> list = this.blsInOutStockBillDao
				.findBlsOutStockBillDetail(warehouseName, spec, scmCoc,
						beginDate, endDate);
		// 查询出入仓单据里的所有单据号
		List<String> bills = blsInOutStockBillDao.findAllInBillNo();
		// 入仓单据里的所有单据号map
		Map billMap = new HashMap<String, String>();
		for (String key : bills) {
			billMap.put(key, "");
		}
		// 获取当出仓单据对应的入仓单据为空，或不存在的商品信息
		List<BlsInOutStockBillDetail> returnList = new ArrayList<BlsInOutStockBillDetail>();
		for (BlsInOutStockBillDetail detail : list) {
			if (detail.getInBillNo() == null || "".equals(detail.getInBillNo())
					|| !billMap.containsKey(detail.getInBillNo())) {
				returnList.add(detail);
			}
		}
		return returnList;

	}

	/**
	 * 保存进出仓单表体
	 * 
	 * @param blsInOutStockBill
	 *            进出仓单据表体
	 */
	public void saveBlsInOutStockBillDetail(
			List<BlsInOutStockBillDetail> blsInOutStockBillDetailS) {
		for (BlsInOutStockBillDetail bill : blsInOutStockBillDetailS) {
			this.blsInOutStockBillDao.saveBlsInOutStockBillDetail(bill);
		}
	}

	/**
	 * hw 归并料号相同的商品信息数量毛重净重
	 */
	@SuppressWarnings("unchecked")
	public List mergerBlsInOutStockCommodityInfo(List list, boolean isImp,
			boolean isExp) {
		Map<String, BlsInOutStockBillDetail> map = new HashMap<String, BlsInOutStockBillDetail>();
		if (isImp) {
			for (int i = 0; i < list.size(); i++) {
				BlsInOutStockBillDetail t = (BlsInOutStockBillDetail) list
						.get(i);
				String conditions = (t.getPartNo() == null ? null : t
						.getPartNo().getPtNo())
						+ (t.getOriginCountry() == null ? null : t
								.getOriginCountry().getName())
						+ (t.getRemarks1() == null ? "" : t.getRemarks1());
				BlsInOutStockBillDetail temp = (BlsInOutStockBillDetail) map
						.get(conditions);
				if (temp == null) {
					map.put(conditions, t);
				} else {
					Double detailQty = ((t.getDetailQty() == null ? 0.0 : t
							.getDetailQty().doubleValue()) + (temp
							.getDetailQty() == null ? 0.0 : temp.getDetailQty()
							.doubleValue()));
					Double netWeight = ((t.getNetWeight() == null ? 0.0 : t
							.getNetWeight().doubleValue()
							+ (temp.getNetWeight() == null ? 0.0 : temp
									.getNetWeight().doubleValue())));
					Double grossWeight = ((t.getGrossWeight() == null ? 0.0 : t
							.getGrossWeight()) + (temp.getGrossWeight() == null ? 0.0
							: temp.getGrossWeight().doubleValue()));
					int packCount = ((t.getPackCount() == null ? 0 : t
							.getPackCount()) + (temp.getPackCount() == null ? 0
							: temp.getPackCount()));

					temp.setNetWeight(netWeight);
					temp.setGrossWeight(grossWeight);
					temp.setDetailQty(detailQty);
					temp.setPackCount(packCount);
				}
			}
		} else if (isExp) {
			for (int i = 0; i < list.size(); i++) {
				BlsInOutStockBillDetail t = (BlsInOutStockBillDetail) list
						.get(i);
				String conditions = t.getInBillNo() == null ? "" : t
						.getInBillNo()
						+ (t.getPartNo() == null ? null : t.getPartNo()
								.getPtNo())
						+ (t.getRemarks2() == null ? "" : t.getRemarks2())
						+ (t.getOriginCountry() == null ? null : t
								.getOriginCountry().getName());
				BlsInOutStockBillDetail temp = (BlsInOutStockBillDetail) map
						.get(conditions);
				if (temp == null) {
					map.put(conditions, t);
				} else {
					Double detailQty = ((t.getDetailQty() == null ? 0.0 : t
							.getDetailQty().doubleValue()) + (temp
							.getDetailQty() == null ? 0.0 : temp.getDetailQty()
							.doubleValue()));
					Double netWeight = ((t.getNetWeight() == null ? 0.0 : t
							.getNetWeight().doubleValue()
							+ (temp.getNetWeight() == null ? 0.0 : temp
									.getNetWeight().doubleValue())));
					Double grossWeight = ((t.getGrossWeight() == null ? 0.0 : t
							.getGrossWeight()) + (temp.getGrossWeight() == null ? 0.0
							: temp.getGrossWeight().doubleValue()));
					int packCount = ((t.getPackCount() == null ? 0 : t
							.getPackCount()) + (temp.getPackCount() == null ? 0
							: temp.getPackCount()));
					temp.setNetWeight(netWeight);
					temp.setGrossWeight(grossWeight);
					temp.setDetailQty(detailQty);
					temp.setPackCount(packCount);
				}
			}
		}
		List<BlsInOutStockBillDetail> listBls = new ArrayList<BlsInOutStockBillDetail>();
		listBls.addAll(map.values());
		return listBls;
	}

	/**
	 * 单据拆分，当总合超过20项时
	 */
	public List splitBlsInOutStockDetil(StorageBill storageBill, List listBls) {
		int t = listBls.size() / 20 + (listBls.size() % 20 == 0 ? 0 : 1);//

		for (int i = 0; i < listBls.size(); i++) {
			if (i != 0 && i % 20 == 0) {
				try {
					storageBill = (StorageBill) BeanUtils
							.cloneBean(storageBill);
					storageBill.setId(null);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据对应信息查询对应的出仓单据
	 * 
	 * @param matchInfo
	 * @return
	 */
	public void deleteMatchInfo(List<BillMatchInfo> billMatchInfoS){
		List<BlsInOutStockBillDetail> bills = new ArrayList<BlsInOutStockBillDetail>();
//		List<BlsInOutStockBillDetail> inBills = new ArrayList<BlsInOutStockBillDetail>();
		for (BillMatchInfo billMatchInfo : billMatchInfoS) {
			BlsInOutStockBillDetail outBill = (BlsInOutStockBillDetail) blsInOutStockBillDao
					.findBlsOutStockBillDetailByMatchInfo(billMatchInfo);			
			BlsInOutStockBillDetail firstBill = (BlsInOutStockBillDetail) blsInOutStockBillDao
			.findBlsInStockBillDetailByMatchInfo(
					billMatchInfo);
			
			if(outBill==null){									
				// 删除对应
				blsInOutStockBillDao
						.deletesaveBillMatchInfo(billMatchInfo);
				continue;
			}
			//还原入仓单据
			if(firstBill!=null){
				
				firstBill.setNowDetailQty(CommonUtils.getDoubleExceptNull(firstBill.getNowDetailQty())
						+CommonUtils.getDoubleExceptNull(billMatchInfo.getDetailQty()));
				bills.add(firstBill);
//				blsInOutStockBillDao.saveBlsInOutStockBillDetail( firstBill);
				
			}

			// 还原出仓单据对应
			outBill.setInBillNo(billMatchInfo
					.getOldInBillNo());
			outBill.setInBillGoodNo(billMatchInfo
					.getOldInBillGoodNo());
			bills.add(outBill);
//			blsInOutStockBillDao
//					.saveBlsInOutStockBillDetail(outBill);
			// 删除对应
//			blsInOutStockBillDao
//					.deletesaveBillMatchInfo(billMatchInfo);
		}
		for(BillMatchInfo bm : billMatchInfoS){
			blsInOutStockBillDao
			.deletesaveBillMatchInfo(bm);
		}
		for(BlsInOutStockBillDetail bill : bills){
			blsInOutStockBillDao.saveBlsInOutStockBillDetail( bill);
		}
	}
	
	/**
	 * 单据对应
	 * @param request
	 * @param outBills
	 * @param firstBills
	 */
	public void matchInAndOutBill(List<BlsInOutStockBillDetail> outBills,List<BlsInOutStockBillDetail> firstBills){
		//期初单据keys
		Map<String,BlsInOutStockBillDetail> firstBillMap = getFirstBillMap(firstBills);
		//单据对应信息
		List<BillMatchInfo> matchBillInfoS = new ArrayList<BillMatchInfo>();
		
		for(BlsInOutStockBillDetail outBill : outBills){
//			 start = System.currentTimeMillis();
			if(firstBillMap.containsKey(getKey(outBill))){//出仓单据有对应的初期单据，则对应
				//获得对应的初期单据
				BlsInOutStockBillDetail firstBill = firstBillMap.get(getKey(outBill));
//				//计算初期单据剩余数量
//				Double historyQty = blsInOutStockBillAction.findFirstBillMatchQty(new Request(
//					      CommonVars.getCurrUser(), true),firstBill);
//				Double nowQty = (CommonUtils.getDoubleExceptNull(firstBill.getDetailQty())
//						-CommonUtils.getDoubleExceptNull(historyQty));
				
				//检查待对应出仓单据对应的入仓数量是否足够					
				if(CommonUtils.getDoubleExceptNull(outBill.getDetailQty())
						> CommonUtils.getDoubleExceptNull(firstBill.getNowDetailQty()) ){
					 continue;
				}
				//新建对应记录
				BillMatchInfo billMatchInfo = new BillMatchInfo();
				billMatchInfo.setInBillNo(firstBill.getBsb().getBillNo());
				billMatchInfo.setOutBillNo(outBill.getBsb().getBillNo());
				billMatchInfo.setMatchNo(firstBill.getSeqNo());
				billMatchInfo.setOutMatchNo(outBill.getSeqNo());
				billMatchInfo.setMatchName(firstBill.getWarehouseName());
				billMatchInfo.setMatchSpec(firstBill.getSpec());
				billMatchInfo.setUnitName(firstBill.getUnit().getName());
				billMatchInfo.setCustomerName(firstBill.getBsb().getCorrOwner().getName());
				billMatchInfo.setOldInBillNo(outBill.getInBillNo());
				billMatchInfo.setOldInBillGoodNo(outBill.getSeqNo());
				billMatchInfo.setDetailQty(outBill.getDetailQty());//对应数量,待检查
				billMatchInfo.setMatchDate(new Date());
				billMatchInfo.setCompany(firstBill.getCompany());	
				billMatchInfo.setPtNo(firstBill.getPartNo().getPtNo());
				matchBillInfoS.add(billMatchInfo);
				//保存对应
				blsInOutStockBillDao.saveBillMatchInfo( billMatchInfo);
				//对应了之后，出仓单据对应初期单据
				outBill.setInBillNo(firstBill.getBsb().getBillNo());
				outBill.setInBillGoodNo(firstBill.getSeqNo());
				
				//期初单据数量
				firstBill.setNowDetailQty(CommonUtils.getDoubleExceptNull(firstBill.getNowDetailQty())
						- CommonUtils.getDoubleExceptNull(outBill.getDetailQty()));
				 
			}	
		}
		//出仓单据更新
		this.saveBlsInOutStockBillDetail(outBills);
//		System.out.println("出更新："+(System.currentTimeMillis()-start));
//		start = System.currentTimeMillis();
		//期初单据更新
		this.saveBlsInOutStockBillDetail(firstBills);
		
	}
	
	/**
	    * 计算单据key HashMap
	    * @param firstBills
	    * @return
	    */
	   private Map<String,BlsInOutStockBillDetail> getFirstBillMap(List<BlsInOutStockBillDetail> bills){
			Map firstBillMap = null;		
		   if(bills!=null && bills.size()>0){
			   firstBillMap = new HashMap<String, String>();
			   for(BlsInOutStockBillDetail bill : bills){
					String key = getKey(bill);				
					firstBillMap.put(key, bill);
				}
		   }
		   return firstBillMap;
	   }
	   
	   /**
	    * 计算初期单据的KEY=(期初单据key=名称+规格+单位(编码)+客户(编码)+(料号))
	    * @param bill
	    * @return
	    */
	   private String getKey(BlsInOutStockBillDetail bill){
		   String key = 
//			        (bill.getWarehouseName()==null?"":bill.getWarehouseName())+"/"
//					+(bill.getSpec()==null?"":bill.getSpec())+"/"
//					+(bill.getUnit()==null?"":bill.getUnit().getCode())+"/"
//					+
					(bill.getBsb().getCorrOwner()==null?"":bill.getBsb().getCorrOwner().getCode()+"/"
					+(bill.getPartNo()==null?"":bill.getPartNo().getPtNo()));	   
		   return key;
	   }
}
