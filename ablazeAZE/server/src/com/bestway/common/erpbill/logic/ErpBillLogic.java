package com.bestway.common.erpbill.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.common.Request;
import com.bestway.common.erpbill.dao.ErpBillDao;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbill.entity.TempCustomOrderDetailAndAmount;
import com.bestway.common.erpbill.entity.TempMaterialBomDetailAndAmount;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;

public class ErpBillLogic {

	private ErpBillDao erpBillDao;

	public ErpBillDao getErpBillDao() {
		return erpBillDao;
	}

	public void setErpBillDao(ErpBillDao erpBillDao) {
		this.erpBillDao = erpBillDao;
	}

	public void deleteCustomOrder(CustomOrder order) {
		this.erpBillDao.deleteCustomOrdeDetail(order);
		this.erpBillDao.deleteCustomOrder(order);
	}

	public Map<BillMaster, List<BillDetail>> findBillMaster(Map pmap) {
		Map<BillMaster, List<BillDetail>> hmap = new HashMap();
		List list = this.erpBillDao.findBillMaster(pmap);
		for (int k = 0; k < list.size(); k++) {
			BillDetail bd = (BillDetail) list.get(k);
			BillMaster master = bd.getBillMaster();
			if (hmap.get(master) == null) {
				List temp = new ArrayList();
				temp.add(bd);
				hmap.put(master, temp);
			} else {
				List oldList = (List) hmap.get(master);
				oldList.add(bd);
			}
		}
		return hmap;
	}

	/**
	 * 根据参数查询订单明细的一些栏位
	 * 
	 * @param isShowTransfer
	 *            是否显示转厂
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            结束日期
	 * @param customer
	 *            客户供应商
	 * @param billCode
	 *            订单号
	 * @param ptNo
	 *            BOM号
	 * @return
	 */
	public List findSomeObjectInCustomOrderDetail(Boolean isShowTransfer,
			Date startdate, Date enddate, ScmCoc customer, String billCode,
			String ptNo) {
		Integer maxversioncount = erpBillDao.findCustomOrderDetailCount(
				isShowTransfer, startdate, enddate, customer, billCode, ptNo);// 版本个数

		Customparames par = erpBillDao.findparames();// 参数
		Integer zzff = par.getSetzzff();// 做账方式
		Map<String, Object[]> map = new HashMap();
		String billcode = "";// 订单号
		String saldate = "";// 交货日期
		String ptno = "";// 料号
		String version = "";// 版本号
		String name = "";// 名称
		String spec = "";// 规格
		String unitName = "";// 单位名称
		String key = "";
		int count = 0;
		Double zcsum = 0.00;// 转厂数量
		Double cksum = 0.00;// 出口数量
		Double htsum = 0.00;// 已转合同数量
		List reList = erpBillDao.findSomeObjectInCustomOrderDetail(
				isShowTransfer, startdate, enddate, customer, billCode, ptNo);

		for (int j = 0; j < reList.size(); j++) {

			CustomOrderDetail orderDetail = (CustomOrderDetail) reList.get(j);

			Object[] oldobjs = new Object[11 + (5 * maxversioncount) + 1];// 最后一个是用来存储CustomOrderDetail

			billcode = orderDetail.getCustomOrder().getBillCode();
			saldate = String.valueOf(orderDetail.getSalesDate());
			ptno = orderDetail.getMateriel().getPtNo();
			name = orderDetail.getMateriel().getFactoryName();
			spec = orderDetail.getMateriel().getFactorySpec();
			unitName = orderDetail.getMateriel().getCalUnit().getName();
			version = String.valueOf(orderDetail.getVersion());
			key = billcode + "/" + saldate + "/" + ptno + "/" + name + "/"
					+ spec + "/" + unitName + "/" + version;
			Double sum = orderDetail.getAmount();
			if (map.size() <= 0 || map.get(key) == null) {
				Boolean isSelect = new Boolean(false);
				oldobjs[0] = isSelect;// 是否已被选择
				oldobjs[1] = orderDetail.getCustomOrder().getCustomer()
						.getName();// 单头客户供应商名称
				oldobjs[2] = orderDetail.getCustomOrder().getBillCode();// 单头订单号
				oldobjs[3] = orderDetail.getSalesDate();// 交货日期
				oldobjs[4] = orderDetail.getMateriel().getPtNo();// 料号
				oldobjs[5] = orderDetail.getMateriel().getFactoryName();// 名称
				oldobjs[6] = orderDetail.getMateriel().getFactorySpec();// 规格
				oldobjs[7] = orderDetail.getMateriel().getCalUnit().getName();// 单位名称
				oldobjs[8] = orderDetail.getAmount();// 成品数量
				oldobjs[9] = orderDetail.getIfzc();// 是否转厂
				oldobjs[10] = orderDetail.getVersion();// 版本号
				oldobjs[11] = orderDetail.getIfcustomer();// 是否已转合同
				oldobjs[12] = orderDetail.getCustomOrder().getImportcount();// 单头导入次数(订单版本号)

				/***************************************************************
				 * 如果是转厂则增加到转厂资料中，否则增加到直接出口数量中
				 */
				if (orderDetail.getIfzc()) {

					zcsum = sum;

					cksum = 0.00;

				} else {

					cksum = sum;

					zcsum = 0.00;
				}

				/***************************************************************
				 * 如果是已转合同则，增加到已转合同中，否则已转合同数为0
				 */
				if (orderDetail.getIfcustomer()) {

					htsum = sum;

				} else {

					htsum = 0.00;

				}
				count = 0;
				// 统计合计
				oldobjs[oldobjs.length - 4] = cksum;// 出口数量
				oldobjs[oldobjs.length - 3] = zcsum;// 转厂数量
				oldobjs[oldobjs.length - 2] = htsum;// 已转合同数量

				oldobjs[oldobjs.length - 1] = orderDetail;// 最后一个是用来存储CustomOrderDetail

				map.put(key, oldobjs);
			} else {
				count++;
				oldobjs = map.get(key);
				oldobjs[8 + (count * 5)] = orderDetail.getAmount();// 数量
				oldobjs[8 + (count * 5) + 1] = orderDetail.getIfzc();// 是否转厂
				oldobjs[8 + (count * 5) + 2] = orderDetail.getVersion();// 版本号
				oldobjs[8 + (count * 5) + 3] = orderDetail.getIfcustomer();// 是否已转合同
				oldobjs[8 + (count * 5) + 4] = orderDetail.getCustomOrder()
						.getImportcount();// 单头导入次数(订单版本号)

				/***************************************************************
				 * 如果是转厂则增加到转厂资料中，否则增加到直接出口数量中
				 */
				if (orderDetail.getIfzc()) {

					if (zzff == 2) {
						zcsum = sum;
					} else {
						zcsum = zcsum + sum;
					}

					// cksum = 0.00;

				} else {
					if (zzff == 2) {
						cksum = sum;
					} else {
						cksum = cksum + sum;
					}

					// zcsum = 0.00;
				}

				/***************************************************************
				 * 如果是已转合同则，增加到已转合同中，否则已转合同数为0
				 */
				if (orderDetail.getIfcustomer()) {

					htsum = htsum + sum;

				}
				oldobjs[oldobjs.length - 4] = cksum;// 出口数量
				oldobjs[oldobjs.length - 3] = zcsum;// 转厂数量
				oldobjs[oldobjs.length - 2] = htsum;// 已转合同数量
			}

		}
		List report = new ArrayList();
		List arrayList = new ArrayList();
		report.addAll(map.values());
		arrayList.add(report);
		arrayList.add(maxversioncount);

		return arrayList;

	}

	/**
	 * 合并订单成品资料
	 * 
	 * @param temCustomOrderList
	 *            成品资料List
	 * @return
	 */
	public List checkAndUniteMaster(List temCustomOrderList) {

		HashMap<String, TempCustomOrderDetailAndAmount> map = new HashMap();
		String billcode = "";// 订单号
		String saldate = "";// 交货日期
		String ptno = "";// 料号
		String version = "";// 版本号
		String name = "";// 名称
		String spec = "";// 规格
		String unitName = "";// 单位名称
		String key = "";
		for (int i = 0; i < temCustomOrderList.size(); i++) {

			TempCustomOrderDetailAndAmount temCustomOrder = (TempCustomOrderDetailAndAmount) temCustomOrderList
					.get(i);
			CustomOrderDetail orderDetail = (CustomOrderDetail) temCustomOrder
					.getCustomOrderDetail();

			billcode = orderDetail.getCustomOrder().getBillCode();
			saldate = String.valueOf(orderDetail.getSalesDate());
			ptno = orderDetail.getMateriel().getPtNo();
			name = orderDetail.getMateriel().getFactoryName();
			spec = orderDetail.getMateriel().getFactorySpec();
			unitName = orderDetail.getMateriel().getCalUnit().getName();
			version = String.valueOf(orderDetail.getVersion());
			key = saldate + "/" + ptno + "/" + name + "/" + spec + "/"
					+ unitName + "/" + version;
			if (map.get(key) == null) {
				map.put(key, temCustomOrder);
			} else {
				TempCustomOrderDetailAndAmount tem = map.get(key);
				tem.setGrossAmount(temCustomOrder.getGrossAmount()
						+ tem.getGrossAmount());
			}
		}
		List list = new ArrayList();
		list.addAll(map.values());
		return list;
	}

	/**
	 * 根据订单中的成品资料合并料件资料
	 * 
	 * @param temCustomOrderList
	 *            成品资料List
	 * @return
	 */
	public List checkAndUniteDetail(List temCustomOrderList) {
		HashMap<Materiel, TempMaterialBomDetailAndAmount> map = new HashMap();
		List arrayList = new ArrayList();
		// List list = checkAndUniteMaster(temCustomOrderList);
		TempMaterialBomDetailAndAmount tempBomDetail;
		for (int i = 0; i < temCustomOrderList.size(); i++) {
			TempCustomOrderDetailAndAmount temCustomOrder = (TempCustomOrderDetailAndAmount) temCustomOrderList
					.get(i);
			Materiel materiel = temCustomOrder.getCustomOrderDetail()
					.getMateriel();
			Integer version = temCustomOrder.getCustomOrderDetail()
					.getVersion();
			// 查找报关常用工厂BOM料件
			List bomDetailList = erpBillDao.findMaterialBomDetailByPar(
					materiel, version);
			if (bomDetailList.size() <= 0)
				continue;
			for (int j = 0; j < bomDetailList.size(); j++) {
				MaterialBomDetail bomDetail = (MaterialBomDetail) bomDetailList
						.get(j);
				if (map.get(bomDetail.getMateriel()) == null) {
					tempBomDetail = new TempMaterialBomDetailAndAmount();
					tempBomDetail.setMaterialBomDetail(bomDetail);
					Double grossAmount = bomDetail.getUnitUsed()
							* temCustomOrder.getGrossAmount();
					tempBomDetail.setGrossAmount(grossAmount);
					tempBomDetail.setCustomOrderDetail(temCustomOrder
							.getCustomOrderDetail());
					map.put(bomDetail.getMateriel(), tempBomDetail);
				} else {
					tempBomDetail = (TempMaterialBomDetailAndAmount) map
							.get(bomDetail.getMateriel());
					Double grossAmount = bomDetail.getUnitUsed()
							* temCustomOrder.getGrossAmount();
					tempBomDetail.setGrossAmount(grossAmount
							+ tempBomDetail.getGrossAmount());
				}
			}

		}
		arrayList.addAll(map.values());
		return arrayList;
	}


}
