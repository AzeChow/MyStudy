package com.bestway.owp.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.StatCusNameRelationHsn;
import com.bestway.bcus.cas.entity.TempBomRelation;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.owp.dao.OwpAnalyDao;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.entity.OwpBomDetail;
import com.bestway.owp.entity.OwpBomMaster;
import com.bestway.owp.entity.OwpBomVersion;
import com.bestway.owp.entity.OwpBillRecvHead;
import com.bestway.owp.entity.OwpBillRecvItem;
import com.bestway.owp.entity.OwpBillSendItem;
import com.bestway.owp.entity.TempBillSendDetail;
import com.bestway.owp.entity.TempOfApplyOutGoods;
import com.bestway.owp.entity.TempOwpStockAll;
import com.bestway.owp.entity.TempReturnToBom;

/**
 * 外发加工Bom与统计分析逻辑类
 * 
 * @author hcl check by hcl 2010-10-07
 */
public class OwpAnalyLogic {
	/**
	 * 外发加工Bom与统计分析dao类
	 */
	private OwpAnalyDao owpAnalyDao;

	/**
	 * 获取owpAnalyDao
	 * 
	 * @return owpAnalyDao
	 */
	public OwpAnalyDao getOwpAnalyDao() {
		return owpAnalyDao;
	}

	/**
	 * 设置owpAnalyDao
	 * 
	 */
	public void setOwpAnalyDao(OwpAnalyDao owpDao) {
		this.owpAnalyDao = owpDao;
	}

	/**
	 * 根据报关常用工厂物料保存报关常用工厂BOM成品资料
	 * 
	 * @param lsMaterial
	 *            是Materiel型，报关常用工厂物料
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public List saveOwpBomMaster(List lsMaterial) {
		List lsResult = new ArrayList();
		if (lsMaterial == null) {
			return lsResult;
		}
		for (int i = 0; i < lsMaterial.size(); i++) {
			OwpBomMaster master = new OwpBomMaster();
			EnterpriseMaterial material = (EnterpriseMaterial) lsMaterial
					.get(i);
			master.setPtNo(material.getPtNo());
			master.setPtName(material.getFactoryName());
			master.setPtSpec(material.getFactorySpec());
			System.out.println("ptNo=" + material.getPtNo());
			System.out.println("ptName=" + material.getFactoryName());
			System.out.println("ptSpec=" + material.getFactorySpec());
			master.setCompany(CommonUtils.getCompany());
			this.owpAnalyDao.saveOrUpdate(master);
			lsResult.add(master);
		}
		return lsResult;
	}

	/**
	 * 根据外发加工BOM版本、物料主档资料保存外发加工BOM成品资料
	 * 
	 * @param version
	 *            父件版本号
	 * @param lsCustomDetail
	 *            是Materiel型，报关常用工厂物料
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件资料
	 */
	public List saveOwpBomManage(OwpBomVersion version, List lsCustomDetail) {
		List lsResult = new ArrayList();
		for (int i = 0; i < lsCustomDetail.size(); i++) {
			OwpBomDetail detail = new OwpBomDetail();
			EnterpriseMaterial material = (EnterpriseMaterial) lsCustomDetail
					.get(i);
			detail.setVersion(version);
			detail.setCompentNo(material.getPtNo());
			detail.setCompentName(material.getFactoryName());
			detail.setCompentSpec(material.getFactorySpec());
			detail.setUnitWare(0.0);
			detail.setWare(0.0);
			detail.setUnitUsed(0.0);
			detail.setCompany(CommonUtils.getCompany());
			this.owpAnalyDao.saveOrUpdate(detail);
			lsResult.add(detail);
		}
		return lsResult;
	}

	/**
	 * 保存报关常用工厂BOM成品资料
	 * 
	 * @param materielBomMaster
	 *            报关常用工厂BOM成品资料
	 */
	public void deleteMaterielBomMaster(OwpBomMaster materielBomMaster) {
		List list = this.owpAnalyDao.findOwpBomDetail(materielBomMaster);
		this.owpAnalyDao.deleteAll(list);
		list = this.owpAnalyDao.findMaterielBomVersion(materielBomMaster);
		this.owpAnalyDao.deleteAll(list);
		this.owpAnalyDao.delete(materielBomMaster);
	}

	/**
	 * 根据报关常用工厂BOM版本删除报关常用工厂BOM成品资料
	 * 
	 * @param version
	 *            父件版本号
	 * 
	 */
	public void deleteOwpBomVersion(OwpBomVersion version) {
		List list = this.owpAnalyDao.findOwpBomDetail(version);
		this.owpAnalyDao.deleteAll(list);
		this.owpAnalyDao.delete(version);
	}

	/**
	 * 保存OwpBomDetail(List)
	 * 
	 * @param listTo
	 * 
	 */
	public void saveList(List listTo) {
		for (int i = 0; i < listTo.size(); i++) {
			owpAnalyDao.saveOrUpdate((OwpBomDetail) listTo.get(i));
		}
	}

	/**
	 * 返回外发加工申请表发货明细
	 * 
	 * @param appConditions
	 * @return
	 * @author hcl
	 */
	public List findOwpApplyOutGoods(List<Condition> appConditions,List<Condition> billConditions) {
		// saveTest();
		// 查找发货申请表
		List applyList = owpAnalyDao.findOwpApplyOutGoods(appConditions);
		System.out.println("applayList=" + applyList.size());
		// 查找发货单
		List list = owpAnalyDao.findOwpOutGoods(billConditions);
		System.out.println("list=" + list.size());
		Map<String, TempOfApplyOutGoods> map = new HashMap<String, TempOfApplyOutGoods>();
		for (int i = 0; i < applyList.size(); i++) {
			OwpAppSendItem item = (OwpAppSendItem) applyList.get(i);
			TempOfApplyOutGoods t = map.get(item.getHead().getAppNo()+"/"+item.getSeqNum());
			if (t == null)
				map.put(item.getHead().getAppNo()+"/"+item.getSeqNum(), saveToTempOfApplyOutGoods(item));
		}
		for (int i = 0; i < list.size(); i++) {
			OwpBillSendItem billItem = (OwpBillSendItem) list.get(i);
			TempOfApplyOutGoods temp = map.get(billItem.getHead().getAppNo()+"/"+billItem.getListNo());
			if (temp != null) {
				temp.setCountQty(temp.getCountQty() + (billItem.getQty()==null?0.0:billItem.getQty()));
				temp.setCanQty(temp.getCanQty() - (billItem.getQty()==null?0.0:billItem.getQty()));
			}
		}
		return new ArrayList(map.values());
	}

	/**
	 *保存测试数据
	 * 
	 * @author hcl
	 */
	private void saveTest() {
		OwpAppHead appHead = new OwpAppHead();
		appHead.setCompany(CommonUtils.getCompany());
		appHead.setAppNo("APP002");
		appHead.setInTradeName("InTradeName002");
		this.owpAnalyDao.saveOrUpdate(appHead);

		OwpAppRecvItem sendItem = new OwpAppRecvItem();
		sendItem.setHead(appHead);
		sendItem.setSeqNum(1);
		sendItem.setCompany(CommonUtils.getCompany());
		sendItem.setHsName("HsName");
		sendItem.setQty(100.0);
		this.owpAnalyDao.saveOrUpdate(sendItem);

		OwpBillRecvHead sendBillHead = new OwpBillRecvHead();
		sendBillHead.setCompany(CommonUtils.getCompany());
		sendBillHead.setAppNo("APP002");
		sendBillHead.setInTradeName("InTradeName002");
		this.owpAnalyDao.saveOrUpdate(sendBillHead);

		OwpBillRecvItem sendBillItem = new OwpBillRecvItem();
		sendBillItem.setHead(sendBillHead);
		sendBillItem.setListNo(1);
		sendBillItem.setCompany(CommonUtils.getCompany());
		sendBillItem.setHsName("HsName");
		sendBillItem.setQty(9.0);

		this.owpAnalyDao.saveOrUpdate(sendBillItem);

	}

	/**
	 * 设置中间类并返回（申请表发货明细情况）
	 * 
	 * @param item
	 * @return
	 * @author hcl
	 */
	private TempOfApplyOutGoods saveToTempOfApplyOutGoods(OwpAppSendItem item) {
		TempOfApplyOutGoods temp = new TempOfApplyOutGoods();
		temp.setListNo(item.getSeqNum());
		temp.setAppNo(item.getHead().getAppNo());
		temp.setTrNo(item.getTrNo());
		temp.setComplex(item.getComplex());
		temp.setHsName(item.getHsName());
		temp.setHsSpec(item.getHsSpec());
		temp.setHsUnit(item.getHsUnit());
		temp.setQty(item.getQty());
		temp.setCountQty(0.0);
		temp.setCanQty(item.getQty());
		return temp;
	}

	/**
	 * 设置中间类并返回（申请表收货明细情况）
	 * 
	 * @param item
	 * @return
	 * @author hcl
	 */
	private TempOfApplyOutGoods saveToTempOfApplyInGoods(OwpAppRecvItem item) {
		TempOfApplyOutGoods temp = new TempOfApplyOutGoods();
		temp.setListNo(item.getSeqNum());
		temp.setAppNo(item.getHead().getAppNo());
		temp.setTrNo(item.getTrNo());
		temp.setComplex(item.getComplex());
		temp.setHsName(item.getHsName());
		temp.setHsSpec(item.getHsSpec());
		temp.setHsUnit(item.getHsUnit());
		temp.setQty(item.getQty());
		temp.setCountQty(0.0);
		temp.setCanQty(item.getQty());
		return temp;
	}

	/**
	 * 返回查询外发加工申请表收货明细
	 * 
	 * @param conditions
	 * @return
	 * @author hcl
	 */
	public List findOwpApplyInGoods(List<Condition> appConditions,List<Condition> billConditions) {
		// saveTest();

		List applyList = owpAnalyDao.findOwpApplyInGoods(appConditions);
		System.out.println("applayList=" + applyList.size());
		List list = owpAnalyDao.findOwpInGoods(billConditions);
		System.out.println("list=" + list.size());
		Map<String, TempOfApplyOutGoods> map = new HashMap<String, TempOfApplyOutGoods>();
		for (int i = 0; i < applyList.size(); i++) {
			OwpAppRecvItem item = (OwpAppRecvItem) applyList.get(i);
			TempOfApplyOutGoods t = map.get(item.getHead().getAppNo()+"/"+item.getSeqNum());
			if (t == null)
				map.put(item.getHead().getAppNo()+"/"+item.getSeqNum(), saveToTempOfApplyInGoods(item));
		}
		// 循环收货单
		for (int i = 0; i < list.size(); i++) {
			OwpBillRecvItem billItem = (OwpBillRecvItem) list.get(i);
			TempOfApplyOutGoods temp = map.get(billItem.getHead().getAppNo()+"/"+billItem.getListNo());
			if (temp != null) {
				temp.setCountQty(temp.getCountQty() + billItem.getQty());
				temp.setCanQty(temp.getCanQty() - billItem.getQty());
			}
		}

		return new ArrayList(map.values());
	}

	/**
	 * 返回外发加工发货明细
	 * 
	 * @param conditions
	 * @return
	 * @author hcl
	 */
	public List findOwpOutGoods(List<Condition> conditions) {
		// saveTest();

		List applyList = owpAnalyDao.findOwpOutGoods(conditions);
		System.out.println("applayList=" + applyList.size());
		List list = new ArrayList();
		for (int i = 0; i < applyList.size(); i++) {
			OwpBillSendItem item = (OwpBillSendItem) applyList.get(i);
			TempBillSendDetail sendDetail = new TempBillSendDetail();
			sendDetail.setItem(item);
			// 根据发货数量判断是发货还是收货退回
			if (item.getQty() >= 0) {
				sendDetail.setType("发货");
			} else {
				sendDetail.setType("收货退回");
			}
			list.add(sendDetail);
		}
		return list;
	}

	/**
	 * 返回外发加工收货明细
	 * 
	 * @param conditions
	 * @return
	 */
	public List findOwpInGoods(List<Condition> conditions) {
		List applyList = owpAnalyDao.findOwpInGoods(conditions);
		System.out.println("applayList=" + applyList.size());
		return applyList;
	}

	/**
	 * 外发加工库存统计汇总表
	 * 
	 * @param conditions
	 * @return
	 * @author hcl
	 */
	public List findOwpStockAll(List<Condition> conditions) {
		// 返回结果
		List owpOutList = this.owpAnalyDao.findOwpOutGoods(conditions);
		List<Condition> conditionsApply = new ArrayList<Condition>();
		conditionsApply.add(conditions.get(0));
		List owpApplyOutList = this.owpAnalyDao
				.findOwpApplyOutGoods(conditionsApply);
		System.out.println("发货单" + owpOutList.size());
		System.out.println("申请发货单" + owpApplyOutList.size());
		// 用于统计发货单基本信息
		Map<String, TempOwpStockAll> resultNameMap = new HashMap<String, TempOwpStockAll>();
		for (int i = 0; i < owpApplyOutList.size(); i++) {
			OwpAppSendItem item = (OwpAppSendItem) owpApplyOutList.get(i);
			TempOwpStockAll temp = resultNameMap.get(item.getHsName()
					+ item.getHsSpec() + item.getHsUnit().getName());
			if (temp == null) {
				resultNameMap.put(item.getHsName() + "/" + item.getHsSpec()
						+ "/" + item.getHsUnit().getName(),
						getTempOwpStockAllByOwpBillSendItem(item));
			} else {
			}
		}
		// 累计发出、退回数量
		for (int i = 0; i < owpOutList.size(); i++) {
			OwpBillSendItem item = (OwpBillSendItem) owpOutList.get(i);
			TempOwpStockAll temp = resultNameMap.get(item.getHsName() + "/"
					+ item.getHsSpec() + "/" + item.getHsUnit().getName());
			if (temp == null) {
			} else {
				if (item.getQty() >= 0) {
					temp.setOutAllAmount((temp.getOutAllAmount() == null ? 0.0
							: temp.getOutAllAmount())
							+ item.getQty());
					temp
							.setReturnAllAmount((temp.getReturnAllAmount() == null ? 0.0
									: temp.getReturnAllAmount()));
				} else {
					temp
							.setReturnAllAmount((temp.getReturnAllAmount() == null ? 0.0
									: temp.getReturnAllAmount())
									+ item.getQty());
					temp.setOutAllAmount(temp.getOutAllAmount() == null ? 0.0
							: temp.getOutAllAmount());
				}
			}
		}
		for (Condition c : conditions) {
			c.setFieldName("owpBillRecvItem." + c.getFieldName());
		}
		// 抓取中间表，计算返回折料
		List rerationList = owpAnalyDao.commonSearch("",
				"OwpBillAndBillDetail", conditions, "", "");
		System.out.println("中间表=" + rerationList.size());
		// 按中间表抓取单据
		List billList = owpAnalyDao.findBilldetail(rerationList);
		System.out.println("单据=" + billList.size());
		//外发加工Bom
		Map<String, List<OwpBomDetail>> bomMap = new HashMap<String, List<OwpBomDetail>>();
		List<OwpBomDetail> boms = null;
		List bomDetailList = owpAnalyDao.findAllBomDetail();
		System.out.println("bomDetailList=" + bomDetailList.size());
		// 把bom放进map里面
		for (int i = 0; i < bomDetailList.size(); i++) {
			OwpBomDetail detail = (OwpBomDetail) bomDetailList.get(i);
			List list = bomMap.get(detail.getVersion().getParent().getPtNo());
			if (list == null) {
				list = new ArrayList<OwpBomDetail>();
				list.add(detail);
				System.out.println("key="
						+ detail.getVersion().getParent().getPtNo());
				bomMap.put(detail.getVersion().getParent().getPtNo(), list);
			} else {
				list.add(detail);
			}
		}
		// 查找料件的对应关系
		// List listSend = owpAnalyDao
		// .findFactoryAndFactualCustomsRalationMaterialOfStockAll(owpApplyOutList);
		// System.out.println("料件对应关系=" + listSend.size());
		// Map<String, FactoryAndFactualCustomsRalation> materialMap = new
		// HashMap<String, FactoryAndFactualCustomsRalation>();
		// // 料件的对应关系放进Map
		// for (int i = 0; i < listSend.size(); i++) {
		// FactoryAndFactualCustomsRalation f =
		// (FactoryAndFactualCustomsRalation) listSend
		// .get(i);
		// materialMap.put(f.getMateriel().getPtNo(), f);
		// }
		for (int i = 0; i < billList.size(); i++) {
			BillDetail bill = (BillDetail) billList.get(i);
			// 边角料不用折料
			System.out.println("billtype="
					+ bill.getBillMaster().getBillType().getCode());
			String key = bill.getPtPart();
			System.out.println("billkey=" + bill.getPtPart());
			boms = bomMap.get(key);
			// 折料
			if (boms != null) {
				System.out.println("boms.size()=" + boms.size());
				for (OwpBomDetail bom : boms) {
					// 查找料件的对应关系
					List materialBomList = owpAnalyDao
							.findFactoryAndFactualCustomsRalationMaterial(bom
									.getCompentNo());
					System.out.println("bom.getCompentNo()="
							+ bom.getCompentNo());
					System.out.println("materialBomList.size()="
							+ materialBomList.size());
					FactoryAndFactualCustomsRalation f = null;
					if (materialBomList.size() > 0)
						f = (FactoryAndFactualCustomsRalation) materialBomList
								.get(0);

					if (f != null) {
						StatCusNameRelationHsn s = f
								.getStatCusNameRelationHsn();
						if (s != null) {
							TempOwpStockAll temp = resultNameMap.get(s
									.getCusName()
									+ "/"
									+ s.getCusSpec()
									+ "/"
									+ s.getCusUnit().getName());
							System.out.println("s.getCusName()="
									+ s.getCusName() + "/" + s.getCusSpec()
									+ "/" + s.getCusUnit().getName());
							// TempOwpStockAll temp = resultMap.get(key);
							if (temp != null)
								setTempBomBillDetailFinishedOfStockAll(temp,
										bom, bill, f);
						}
					}
				}
			}
		}

		List materialList = new ArrayList(resultNameMap.values());
		for (int i = 0; i < materialList.size(); i++) {
			TempOwpStockAll t = (TempOwpStockAll) materialList.get(i);
			// 发货总量
			t.setAllAmount((t.getOutAllAmount() == null ? 0.0 : t
					.getOutAllAmount())
					+ (t.getReturnAllAmount() == null ? 0.0 : t
							.getReturnAllAmount()));
			// 边角料库存
			t.setOwpRemianAmout((t.getYinRemianAmount() == null ? 0.0 : t
					.getYinRemianAmount())
					- (t.getSjRemianAmount() == null ? 0.0 : t
							.getSjRemianAmount()));
			// 外发加工库存
			t.setOwpStockAmount(t.getAllAmount()
					- (t.getReturnAllAmount() == null ? 0.0 : t
							.getReturnAllAmount())
					- (t.getReturnBadBomAmount() == null ? 0.0 : t
							.getReturnBadBomAmount()) - t.getOwpRemianAmout());
			System.out.println("总发货数量=" + t.getAllAmount());

		}
		// 折料——未完成需求
		// List list = owpAnalyDao
		// .findFactoryAndFactualCustomsRalationMaterialOfStockAll(materialList);
		// System.out.println("料件对应List="+list.size());
		// List results = getPtAndHsMapOfStockAll(list, materialList);
		return materialList;

	}

	/**
	 * 根据发货申请单获取TempOwpStockAll
	 * 
	 * @param item
	 * @return
	 */
	private TempOwpStockAll getTempOwpStockAllByOwpBillSendItem(
			OwpAppSendItem item) {
		TempOwpStockAll temp = new TempOwpStockAll();
		temp.setListNo(item.getSeqNum());
		temp.setHsName(item.getHsName());
		temp.setHsSpec(item.getHsSpec());
		temp.setHsUnit(item.getHsUnit());
		temp.setComplex(item.getComplex());
		temp.setAllAmount(0.0);
		temp.setOutAllAmount(0.0);
		temp.setReturnAllAmount(0.0);
		temp.setReturnBomAmount(0.0);
		temp.setReturnBadBomAmount(0.0);
		temp.setYinRemianAmount(0.0);
		temp.setSjRemianAmount(0.0);
		temp.setOwpStockAmount(0.0);
		temp.setOwpRemianAmout(0.0);
		return temp;
	}

	/**
	 * 根据单据类型得到是否折料等
	 * 
	 * @param bill
	 * @return
	 */
	private TempOwpStockAll getTempOwpStockAllByBillDetail(BillDetail bill) {

		TempOwpStockAll temp = new TempOwpStockAll();
		// 料件"1017""1018""1113"
		if (bill.getBillMaster().getBillType().getCode().equals("1017")
				|| bill.getBillMaster().getBillType().getCode().equals("1018")
				|| bill.getBillMaster().getBillType().getCode().equals("1113")) {
			temp = setTempBaseInfo(temp, bill);
			temp.setPtAmount(bill.getPtAmount());
			temp.setOutAllAmount(bill.getPtAmount() * bill.getUnitConvert());

			// 成品"2015", "2113"
		} else if (bill.getBillMaster().getBillType().getCode().equals("2015")
				|| bill.getBillMaster().getBillType().getCode().equals("2113")) {

			// 半成品"4004", "4006", "4104","4106"
		} else if (bill.getBillMaster().getBillType().getCode().equals("4004")
				|| bill.getBillMaster().getBillType().getCode().equals("4006")
				|| bill.getBillMaster().getBillType().getCode().equals("4104")
				|| bill.getBillMaster().getBillType().getCode().equals("4106")) {

			// 边角料 "6005"
		} else if (bill.getBillMaster().getBillType().getCode().equals("6005")) {
			// 残次品 "5003"
		} else {

		}
		return temp;
	}

	/**
	 * 设置返回类的基本信息资料
	 * 
	 * @param temp
	 * @param bill
	 * @return
	 */
	private TempOwpStockAll setTempBaseInfo(TempOwpStockAll temp,
			BillDetail bill) {
		temp.setPtNo(bill.getPtPart());
		temp.setPtName(bill.getPtName());
		temp.setPtSpec(bill.getPtSpec());
		temp.setPtUnit(bill.getPtUnit() == null ? "" : bill.getPtUnit()
				.getName());
		temp.setComplex(bill.getComplex());
		temp.setHsName(bill.getHsName());
		temp.setHsSpec(bill.getHsSpec());
		temp.setHsUnit(bill.getHsUnit());
		temp.setPtAmount(0.0);
		temp.setAllAmount(0.0);
		temp.setOutAllAmount(0.0);
		temp.setOwpRemianAmout(0.0);
		temp.setOwpStockAmount(0.0);
		temp.setSjRemianAmount(0.0);
		temp.setYinRemianAmount(0.0);
		temp.setReturnAllAmount(0.0);
		temp.setReturnBadBomAmount(0.0);
		temp.setReturnBomAmount(0.0);
		return temp;

	}
/**
 * 根据单据明细设置库存汇总临时类
 * 
 */
	private TempOwpStockAll setTempOwpStockAllByBillDetail(BillDetail bill,
			TempOwpStockAll temp) {
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * 得到KEY
	 * 
	 * @param bill
	 * @return
	 */
	private String billDetailGetKey(BillDetail bill) {
		String key = bill.getPtPart() + "/" + bill.getPtName() + "/"
				+ bill.getPtSpec() + "/"
				+ (bill.getPtUnit() == null ? "" : bill.getPtUnit().getName());
		System.out.println("getKey=" + key);
		return key;
	}

	/**
	 * 根据发货单设置外发库存汇总表中间类
	 * 
	 * @param outBillItem
	 * @return hcl
	 */
	private TempOwpStockAll setTempOwpStrockAllByOwpBillSendItem(
			OwpBillSendItem outBillItem) {
		TempOwpStockAll temp = new TempOwpStockAll();
		temp.setListNo(outBillItem.getListNo());
		temp.setEmsNo(outBillItem.getHead().getEmsNo());
		temp.setComplex(outBillItem.getComplex());
		temp.setHsName(outBillItem.getHsName());
		temp.setHsSpec(outBillItem.getHsSpec());
		temp.setHsUnit(outBillItem.getHsUnit());
		temp.setAllAmount(0.0);
		temp.setOutAllAmount(0.0);
		temp.setReturnAllAmount(0.0);
		temp.setOwpRemianAmout(0.0);
		temp.setYinRemianAmount(0.0);
		temp.setSjRemianAmount(0.0);
		temp.setReturnBomAmount(0.0);
		temp.setReturnBadBomAmount(0.0);
		temp.setOwpRemianAmout(0.0);
		temp.setOwpStockAmount(0.0);
		if (outBillItem.getQty() >= 0)
			temp.setOutAllAmount(outBillItem.getQty());
		else
			temp.setOutAllAmount(outBillItem.getQty() * (-1));
		return temp;
	}

	/**
	 * 查找所有申请表 hcl
	 * 
	 */
	public List findAllApplyNo() {
		List applyList = owpAnalyDao.findAllApplyNo();
		return applyList;
	}

	/**
	 * 返回折料情况表
	 * 
	 * @param conditions
	 * @return
	 */
	public List findReturnToBom(List<Condition> conditions) {
		System.out.println("conditions=" + conditions.size());
		// 抓取中间表，计算返回折料
		List rerationList = owpAnalyDao.commonSearch("",
				"OwpBillAndBillDetail", conditions, "", "");
		System.out.println("rerationList=" + rerationList.size());
		// 按中间表抓取单据
		List billList = owpAnalyDao.findBilldetail(rerationList);
		System.out.println("billList=" + billList.size());
		Map<String, List<OwpBomDetail>> bomMap = new HashMap<String, List<OwpBomDetail>>();
		// 装返回数据的Map
		Map<String, TempReturnToBom> owpReturn = new HashMap<String, TempReturnToBom>();
		List<OwpBomDetail> boms = null;
		List bomDetailList = owpAnalyDao.findAllBomDetail();
		System.out.println("bomDetailList=" + bomDetailList.size());
		// 把bom放进map里面
		for (int i = 0; i < bomDetailList.size(); i++) {
			OwpBomDetail detail = (OwpBomDetail) bomDetailList.get(i);
			List list = bomMap.get(detail.getVersion().getParent().getPtNo());
			if (list == null) {
				list = new ArrayList<OwpBomDetail>();
				list.add(detail);
				System.out.println("key="
						+ detail.getVersion().getParent().getPtNo());
				bomMap.put(detail.getVersion().getParent().getPtNo(), list);
			} else {
				list.add(detail);
			}
		}
		// 查找成品的对应关系
		List listSend = owpAnalyDao
				.findFactoryAndFactualCustomsRalationFinished(billList);
		Map finishedMap = new HashMap<String, FactoryAndFactualCustomsRalation>();
		// 成品的对应关系放进Map
		for (int i = 0; i < listSend.size(); i++) {
			FactoryAndFactualCustomsRalation f = (FactoryAndFactualCustomsRalation) listSend
					.get(i);
			finishedMap.put(f.getMateriel().getPtNo(), f);
		}
		List materialList = new ArrayList();
		for (int i = 0; i < billList.size(); i++) {
			BillDetail bill = (BillDetail) billList.get(i);
			String key = bill.getPtPart();
			System.out.println("billkey=" + bill.getPtPart());
			boms = bomMap.get(key);
			// 折料
			if (boms != null) {
				System.out.println("boms.size()=" + boms.size());
				for (OwpBomDetail bom : boms) {
					TempReturnToBom temp = owpReturn.get(key);
					materialList.add(setTempBomBillDetailFinished(bom, bill,
							finishedMap));
				}
			}
		}
		// 查找料件的对应关系
		List list = owpAnalyDao
				.findFactoryAndFactualCustomsRalationMaterial(materialList);
		List result = getPtAndHsMap(list, materialList);
		System.out.println("result.size()=" + result.size());
		return new ArrayList(result);
	}

	/**
	 * 折算料件（返回折料）
	 * 
	 * @param list
	 * @param materialList
	 * @return
	 */
	private List getPtAndHsMap(List list, List materialList) {
		Map<String, FactoryAndFactualCustomsRalation> map = new HashMap<String, FactoryAndFactualCustomsRalation>();
		for (int i = 0; i < list.size(); i++) {
			FactoryAndFactualCustomsRalation f = (FactoryAndFactualCustomsRalation) list
					.get(i);
			String key = f.getMateriel().getPtNo();
			FactoryAndFactualCustomsRalation mapF = map.get(key);
			if (mapF == null) {
				map.put(key, f);
			}
		}
		for (int i = 0; i < materialList.size(); i++) {
			TempReturnToBom temp = (TempReturnToBom) materialList.get(i);
			String key = temp.getPtNo();
			FactoryAndFactualCustomsRalation f = map.get(key);
			if (f != null) {
				setTempReturnToBomResult(f, temp);
			}
		}
		return materialList;
	}

	/**
	 * 折算料件（库存汇总）
	 * 
	 * @param list
	 * @param materialList
	 * @return
	 */
	private List getPtAndHsMapOfStockAll(List list, List materialList) {
		Map<String, FactoryAndFactualCustomsRalation> map = new HashMap<String, FactoryAndFactualCustomsRalation>();
		for (int i = 0; i < list.size(); i++) {
			FactoryAndFactualCustomsRalation f = (FactoryAndFactualCustomsRalation) list
					.get(i);
			String key = f.getMateriel().getPtNo();
			FactoryAndFactualCustomsRalation mapF = map.get(key);
			if (mapF == null) {
				map.put(key, f);
			}
		}
		for (int i = 0; i < materialList.size(); i++) {
			TempOwpStockAll temp = (TempOwpStockAll) materialList.get(i);
			String key = temp.getPtNo();
			FactoryAndFactualCustomsRalation f = map.get(key);
			if (f != null) {
				setTempReturnToBomResultOfStockAll(f, temp);
			}
		}
		return materialList;
	}

	/**
	 * 工厂数量与报关数量折算（返回折料）
	 * 
	 * @param f
	 * @param temp
	 */
	private void setTempReturnToBomResult(FactoryAndFactualCustomsRalation f,
			TempReturnToBom temp) {
		List list = owpAnalyDao.findTempReturnToBomResultHsInfo(f);
		OwpAppSendItem item = (OwpAppSendItem) list.get(0);
		temp.setUnitConvert(f.getUnitConvert());
		temp.setEmsNo(item.getHead().getEmsNo());
		temp.setTrNo(item.getTrNo());
		temp.setListNo(item.getSeqNum());
		temp.setHsQty((temp.getPtQty() == null ? 0.0 : temp.getPtQty())
				* (f.getUnitConvert() == null ? 0.0 : f.getUnitConvert()));
	}

	/**
	 * 工厂数量与报关数量折算（返回折料）
	 * 
	 * @param f
	 * @param temp
	 */
	private void setTempReturnToBomResultOfStockAll(
			FactoryAndFactualCustomsRalation f, TempOwpStockAll temp) {
		List list = owpAnalyDao.findTempReturnToBomResultHsInfo(f);
		OwpAppSendItem item = (OwpAppSendItem) list.get(0);
		temp.setUnitConvert(f.getUnitConvert());
		temp.setEmsNo(item.getHead().getEmsNo());
		temp.setListNo(item.getSeqNum());

	}

	/**
	 * 根据bom和bill得到返回类
	 * 
	 * @param bom
	 * @param bill
	 * @param finishedMap
	 * @return
	 */
	private TempReturnToBom setTempBomBillDetailFinished(OwpBomDetail bom,
			BillDetail bill, Map finishedMap) {
		TempReturnToBom newBill = new TempReturnToBom();
		FactoryAndFactualCustomsRalation f = (FactoryAndFactualCustomsRalation) finishedMap
				.get(bill.getPtPart());
		System.out.println("finished.key=" + bill.getPtPart());
		newBill.setPtNo(bom.getCompentNo());
		newBill.setPtName(bom.getCompentName());
		newBill.setPtSpec(bom.getCompentSpec());
		if (f != null) {
			newBill.setComplex(f.getStatCusNameRelationHsn().getComplex());
			newBill.setHsName(f.getStatCusNameRelationHsn().getCusName());
			newBill.setHsSpec(f.getStatCusNameRelationHsn().getCusSpec());
			newBill.setHsUnit(f.getStatCusNameRelationHsn().getCusUnit());
		}
		newBill.setBillDetailNo(bill.getBillMaster().getBillNo());
		newBill.setWaste(bom.getWare());
		newBill.setUnitUsed(bom.getUnitUsed());
		newBill.setUnitWaste(bom.getUnitWare());
		newBill.setPtQty(bill.getPtAmount() * bom.getUnitUsed());
		return newBill;
	}

	/**
	 * 根据bom和bill得到返回类(库存汇总表)
	 * 
	 * @param newBill
	 * 
	 * @param bom
	 * @param bill
	 * @param finishedMap
	 * @return
	 */
	private TempOwpStockAll setTempBomBillDetailFinishedOfStockAll(
			TempOwpStockAll newBill, OwpBomDetail bom, BillDetail bill,
			FactoryAndFactualCustomsRalation f) {
		System.out.println("finished.key=" + bill.getPtPart());
		//残次品折料
		if (bill.getBillMaster().getBillType().getCode().equals("5003")) {

			newBill
					.setReturnBadBomAmount((newBill.getReturnBadBomAmount() == null ? 0.0
							: newBill.getReturnBadBomAmount())
							+ (bill == null ? 0.0 : bill.getPtAmount())
							* (f == null ? 0.0 : f.getUnitConvert())
							* (bom == null ? 0.0 : bom.getUnitUsed()));
			System.out.println("bill.getPtAmount()=" + bill.getPtAmount());
			System.out.println("bom.getUnitWare()=" + bom.getUnitWare());
			System.out.println("f.getUnitConvert()=" + f.getUnitConvert());
			System.out.println("返回残次品折料数量=" + newBill.getReturnBadBomAmount());
			//边角料折料
		} else if(bill.getBillMaster().getBillType().getCode().equals("6005")){
			newBill.setSjRemianAmount((newBill.getSjRemianAmount() == null ? 0.0
							: newBill.getSjRemianAmount())
							+ (bill == null ? 0.0 : bill.getPtAmount())
							* (f == null ? 0.0 : f.getUnitConvert())
							* (bom == null ? 0.0 : bom.getUnitUsed()));
		}else{
			newBill
					.setReturnBomAmount((newBill.getReturnBomAmount() == null ? 0.0
							: newBill.getReturnBomAmount())
							+ (bill == null ? 0.0 : bill.getPtAmount())
							* (f == null ? 0.0 : f.getUnitConvert())
							* (bom == null ? 0.0 : bom.getUnitUsed()));
			newBill
					.setYinRemianAmount((newBill.getYinRemianAmount() == null ? 0.0
							: newBill.getYinRemianAmount())
							+ (bill == null ? 0.0 : bill.getPtAmount())
							* (f == null ? 0.0 : f.getUnitConvert())
							* (bom == null ? 0.0 : bom.getUnitUsed())
							* (1 - bom.getWare()));
		}
		return newBill;
	}

	/**
	 * 获取临时Bom
	 * 
	 * @param bill
	 * @return
	 */
	private List<TempBomRelation> getBomRelation(BillDetail bill) {
		List boms = owpAnalyDao.getBomByBillDetail(bill);
		List<TempBomRelation> result = new ArrayList<TempBomRelation>();
		for (int i = 0; i < boms.size(); i++) {
			OwpBomDetail bom = (OwpBomDetail) boms.get(i);
			TempBomRelation temp = new TempBomRelation();
			temp.setPtNo(bom.getVersion().getParent().getPtNo());
			temp.setPtSpec(bill.getPtSpec());
			temp.setPtUnit(bill.getPtUnit());
			temp.setComplex(bill.getComplex());
			temp.setHsName(bill.getHsName());
			temp.setHsSpec(bill.getHsSpec());
			temp.setHsUnit(bill.getHsUnit());
			temp.setWaste(bom.getWare());
			temp.setUnitConvert(bill.getUnitConvert());
			temp.setUnitWaste(bom.getUnitWare());
			temp.setUnitUsed(bom.getUnitUsed());
			result.add(temp);
		}
		return result;

	}
}
