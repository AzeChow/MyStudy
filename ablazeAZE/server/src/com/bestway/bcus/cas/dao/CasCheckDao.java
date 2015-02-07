package com.bestway.bcus.cas.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BillObject;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.invoice.entity.CarryBalance;
import com.bestway.bcus.cas.invoice.entity.ConditionBalance;
import com.bestway.bcus.cas.invoice.entity.LeftoverMaterielStatInfo;
import com.bestway.bcus.cas.invoice.entity.TempThatDayBalance;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SendState;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.customs.common.entity.BaseContractHead;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

@SuppressWarnings("unchecked")
public class CasCheckDao extends BaseDao {
	/**
	 * 根据商品编码从对应关系中抓出物料料号 出来
	 * 
	 * @param beginComplexCode
	 * @param endComplexCode
	 * @return
	 */
	public List findMaterialPtNoByComplexCodeRange(String beginComplexCode,
			String endComplexCode) {
		String sql = "select distinct a.materiel.ptNo from FactoryAndFactualCustomsRalation a "
				+ "where a.statCusNameRelationHsn.complex.code> = ? and a.statCusNameRelationHsn.complex.code< = ? and a.company.id=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(beginComplexCode);
		paramters.add(endComplexCode);
		paramters.add(CommonUtils.getCompany().getId());
		List list = super.find(sql, paramters.toArray());

		return list;
	}

	/**
	 * 结转差额总表查询(台达)
	 * 
	 */
	@SuppressWarnings("deprecation")
	public List<CarryBalance> findCustomsEnvelopCommodityInfoTaiDa(
			ConditionBalance conditionBalance) {
		Date begin = parseDate(conditionBalance.getDate().getYear());
		Date end = parseDate(conditionBalance.getDate().getYear() + 1);
		end.setDate(end.getDate() - 1);
		System.out.println(begin.toLocaleString());
		System.out.println(end.toLocaleString());
		// 统计期初数
		List<Object[]> qcsList = countQCS(conditionBalance.getCustomerCode(),
				conditionBalance.getName(), conditionBalance.getSpec(), begin,
				end, true, conditionBalance.isM());
		// 统计转厂数据
		List<Object[]> zcList = countSHSTaiDa(
				conditionBalance.getCustomerCode(), conditionBalance.getName(),
				conditionBalance.getSpec(), begin, end, false,
				conditionBalance.isM());

		Map<String, CarryBalance> map = new HashMap<String, CarryBalance>();
		for (int i = 0; i < zcList.size(); i++) {
			Object[] os = zcList.get(i);
			CarryBalance cb = new CarryBalance();
			// 客户名称
			cb.setCustomerName((String) os[0]);
			// 商品名称
			cb.setName((String) os[1]);
			// 商品型号
			cb.setSpec((String) os[2]);
			// 商品单位
			cb.setUnitName((String) os[3]);
			TransferFactoryBill transferFactoryBill = (TransferFactoryBill) os[6];
			// 厂区
			cb.setDefine1(transferFactoryBill.getWareSet() == null ? ""
					: transferFactoryBill.getWareSet().getName());
			if (transferFactoryBill.getScmCoc() != null) {
				// 事业部
				cb.setDefine2(transferFactoryBill.getScmCoc().getFlatCode());
				// 厂商编号
				cb.setDefine3(transferFactoryBill.getScmCoc().getCode());
				// 主管海关
				cb.setDefine4(transferFactoryBill.getScmCoc()
						.getBelongToCustoms() == null ? ""
						: transferFactoryBill.getScmCoc().getBelongToCustoms()
								.getCustomsSerial());
			}
			// 备案序号
			cb.setDefine5(os[7] == null ? "" : os[7].toString());

			cb.setAmountFirst(0.0);
			String key = (os[0] == null ? "" : (String) os[0]) + "/"
					+ (os[1] == null ? "" : (String) os[1]) + "/"
					+ (os[2] == null ? "" : (String) os[2]) + "/"
					+ (os[3] == null ? "" : (String) os[3]);

			map.put(key, cb);
		}
		String tempkey = "";
		// 期初数
		for (Object[] shs : qcsList) {
			tempkey = (shs[0] == null ? "" : (String) shs[0]) + "/"
					+ (shs[1] == null ? "" : (String) shs[1]) + "/"
					+ (shs[2] == null ? "" : (String) shs[2]) + "/"
					+ (shs[3] == null ? "" : (String) shs[3]);
			CarryBalance cb = map.get(tempkey);
			if (cb != null) {
				cb.setAmountFirst(cb.getAmountFirst()
						+ (shs[4] == null ? 0 : (Double) shs[4]));
			}
		}
		conditionBalance.getDate()
				.setDate(conditionBalance.getDate().getDate());
		end = conditionBalance.getDate();
		// 查询转厂单
		Map<String, CarryBalance> zcMap = countByMonth(countSHSTaiDa(
				conditionBalance.getCustomerCode(), conditionBalance.getName(),
				conditionBalance.getSpec(), begin, end, false,
				conditionBalance.isM()));
		// 查询报关单
		Map<String, CarryBalance> bgMap = countByMonth(countZCS(
				conditionBalance.getCustomerCode(), conditionBalance.getName(),
				conditionBalance.getSpec(), begin, end, false,
				conditionBalance.isM()));
		// 查询关封
		Map<String, CarryBalance> gfMap = countByMonth(countDFS(
				conditionBalance.getCustomerCode(), conditionBalance.getName(),
				conditionBalance.getSpec(), begin, end, false,
				conditionBalance.isM()));
		System.out.println(begin.toLocaleString());
		System.out.println(end.toLocaleString());
		List<CarryBalance> rs = new ArrayList<CarryBalance>();

		for (CarryBalance carryBalance : map.values()) {
			String key = carryBalance.getCustomerName() + "/"
					+ carryBalance.getName() + "/" + carryBalance.getSpec()
					+ "/" + carryBalance.getUnitName();
			// 送货
			CarryBalance tmp1 = zcMap.get(key);
			if (tmp1 == null) {
				tmp1 = new CarryBalance();
				tmp1.init();
				tmp1.setName(carryBalance.getName());
				tmp1.setUnitName(carryBalance.getUnitName());
				tmp1.setCustomerName(carryBalance.getCustomerName());
				tmp1.setSpec(carryBalance.getSpec());
			}
			tmp1.setAmountFirst(carryBalance.getAmountFirst());
			tmp1.setType(conditionBalance.isM() ? "收货数" : "送货数");

			// 转厂
			CarryBalance tmp2 = bgMap.get(key);
			if (tmp2 == null) {
				tmp2 = new CarryBalance();
				tmp2.init();
				tmp2.setName(carryBalance.getName());
				tmp2.setUnitName(carryBalance.getUnitName());
				tmp2.setCustomerName(carryBalance.getCustomerName());
				tmp2.setSpec(carryBalance.getSpec());
			}
			tmp2.setAmountFirst(carryBalance.getAmountFirst());
			tmp2.setType("转厂数");

			// 关封
			CarryBalance tmp4 = gfMap.get(key);
			if (tmp4 == null) {
				tmp4 = new CarryBalance();
				tmp4.init();
				tmp4.setName(carryBalance.getName());
				tmp4.setUnitName(carryBalance.getUnitName());
				tmp4.setCustomerName(carryBalance.getCustomerName());
				tmp4.setSpec(carryBalance.getSpec());
			}
			tmp4.setAmountFirst(carryBalance.getAmountFirst());
			tmp4.setType("关封数");

			// 差额
			CarryBalance tmp3 = carryBalance;
			tmp3 = caleDiff(tmp3, tmp1, tmp2, tmp4, conditionBalance.getDate()
					.getMonth() + 1);
			tmp3.setType("差额");

			// 差额总值
			tmp1.setTotal(tmp3.getTotal());
			tmp2.setTotal(tmp3.getTotal());
			tmp4.setTotal(tmp3.getTotal());

			rs.add(tmp1);
			rs.add(tmp2);
			rs.add(tmp3);
			rs.add(tmp4);
		}

		return rs;
	}

	/**
	 * 结转差额总表查询
	 * 
	 */
	@SuppressWarnings("deprecation")
	public List<CarryBalance> findCustomsEnvelopCommodityInfo(
			ConditionBalance conditionBalance) {
		Date begin = parseDate(conditionBalance.getDate().getYear());
		Date end = parseDate(conditionBalance.getDate().getYear() + 1);
		end.setDate(end.getDate() - 1);
		System.out.println(begin.toLocaleString());
		System.out.println(end.toLocaleString());
		// 统计期初数
		List<Object[]> qcsList = countQCS(conditionBalance.getCustomerCode(),
				conditionBalance.getName(), conditionBalance.getSpec(), begin,
				end, true, conditionBalance.isM());
		/*
		 * // 统计收货数 List<Object[]> shsList =
		 * countSHS(conditionBalance.getCustomerCode(),
		 * conditionBalance.getName(), conditionBalance.getSpec(), begin, end,
		 * true, conditionBalance.isM()); // 统计报关数 List<Object[]> zcsList =
		 * countZCS(conditionBalance.getCustomerCode(),
		 * conditionBalance.getName(), conditionBalance.getSpec(), begin, end,
		 * true, conditionBalance.isM());
		 */
		// 统计关封数
		List<Object[]> gfsList = countDFS(conditionBalance.getCustomerCode(),
				conditionBalance.getName(), conditionBalance.getSpec(), begin,
				end, true, conditionBalance.isM());

		Map<String, CarryBalance> map = new HashMap<String, CarryBalance>();
		for (int i = 0; i < gfsList.size(); i++) {
			Object[] os = gfsList.get(i);
			CarryBalance cb = new CarryBalance();
			// 客户名称
			cb.setCustomerName((String) os[0]);
			// 商品名称
			cb.setName((String) os[1]);
			// 商品型号
			cb.setSpec((String) os[2]);
			// 商品单位
			cb.setUnitName((String) os[3]);

			cb.setAmountFirst(0.0);
			String key = (os[0] == null ? "" : (String) os[0]) + "/"
					+ (os[1] == null ? "" : (String) os[1]) + "/"
					+ (os[2] == null ? "" : (String) os[2]) + "/"
					+ (os[3] == null ? "" : (String) os[3]);

			map.put(key, cb);
		}
		String tempkey = "";
		// 期初数
		for (Object[] shs : qcsList) {
			tempkey = (shs[0] == null ? "" : (String) shs[0]) + "/"
					+ (shs[1] == null ? "" : (String) shs[1]) + "/"
					+ (shs[2] == null ? "" : (String) shs[2]) + "/"
					+ (shs[3] == null ? "" : (String) shs[3]);
			CarryBalance cb = map.get(tempkey);
			if (cb != null) {
				cb.setAmountFirst(cb.getAmountFirst()
						+ (shs[4] == null ? 0 : (Double) shs[4]));
			}
		}
		/*
		 * for (Object[] shs : shsList) { tempkey = (shs[0] == null ? "" :
		 * (String) shs[0]) + "/" + (shs[1] == null ? "" : (String) shs[1]) +
		 * "/" + (shs[2] == null ? "" : (String) shs[2]) + "/" + (shs[3] == null
		 * ? "" : (String) shs[3]); CarryBalance cb=map.get(tempkey); if (cb !=
		 * null) { cb.setAmountFirst(cb.getAmountFirst() + (Double) shs[4]); } }
		 * for (Object[] shs : zcsList) { tempkey = (shs[0] == null ? "" :
		 * (String) shs[0]) + "/" + (shs[1] == null ? "" : (String) shs[1]) +
		 * "/" + (shs[2] == null ? "" : (String) shs[2]) + "/" + (shs[3] == null
		 * ? "" : (String) shs[3]); CarryBalance cb=map.get(tempkey); if (cb !=
		 * null) { cb.setAmountFirst(cb.getAmountFirst() - (Double) shs[4]); } }
		 */

		conditionBalance.getDate()
				.setDate(conditionBalance.getDate().getDate());
		end = conditionBalance.getDate();
		// 查询转厂单
		Map<String, CarryBalance> zcMap = countByMonth(countSHS(
				conditionBalance.getCustomerCode(), conditionBalance.getName(),
				conditionBalance.getSpec(), begin, end, false,
				conditionBalance.isM()));
		// 查询报关单
		Map<String, CarryBalance> bgMap = countByMonth(countZCS(
				conditionBalance.getCustomerCode(), conditionBalance.getName(),
				conditionBalance.getSpec(), begin, end, false,
				conditionBalance.isM()));
		// 查询关封
		Map<String, CarryBalance> gfMap = countByMonth(countDFS(
				conditionBalance.getCustomerCode(), conditionBalance.getName(),
				conditionBalance.getSpec(), begin, end, false,
				conditionBalance.isM()));
		System.out.println(begin.toLocaleString());
		System.out.println(end.toLocaleString());
		List<CarryBalance> rs = new ArrayList<CarryBalance>();

		for (CarryBalance carryBalance : map.values()) {
			String key = carryBalance.getCustomerName() + "/"
					+ carryBalance.getName() + "/" + carryBalance.getSpec()
					+ "/" + carryBalance.getUnitName();
			// 送货
			CarryBalance tmp1 = zcMap.get(key);
			if (tmp1 == null) {
				tmp1 = new CarryBalance();
				tmp1.init();
				tmp1.setName(carryBalance.getName());
				tmp1.setUnitName(carryBalance.getUnitName());
				tmp1.setCustomerName(carryBalance.getCustomerName());
				tmp1.setSpec(carryBalance.getSpec());
			}
			tmp1.setAmountFirst(carryBalance.getAmountFirst());
			tmp1.setType(conditionBalance.isM() ? "收货数" : "送货数");

			// 转厂
			CarryBalance tmp2 = bgMap.get(key);
			if (tmp2 == null) {
				tmp2 = new CarryBalance();
				tmp2.init();
				tmp2.setName(carryBalance.getName());
				tmp2.setUnitName(carryBalance.getUnitName());
				tmp2.setCustomerName(carryBalance.getCustomerName());
				tmp2.setSpec(carryBalance.getSpec());
			}
			tmp2.setAmountFirst(carryBalance.getAmountFirst());
			tmp2.setType("转厂数");

			// 关封
			CarryBalance tmp4 = gfMap.get(key);
			if (tmp4 == null) {
				tmp4 = new CarryBalance();
				tmp4.init();
				tmp4.setName(carryBalance.getName());
				tmp4.setUnitName(carryBalance.getUnitName());
				tmp4.setCustomerName(carryBalance.getCustomerName());
				tmp4.setSpec(carryBalance.getSpec());
			}
			tmp4.setAmountFirst(carryBalance.getAmountFirst());
			tmp4.setType("关封数");

			// 差额
			CarryBalance tmp3 = carryBalance;
			tmp3 = caleDiff(tmp3, tmp1, tmp2, tmp4, conditionBalance.getDate()
					.getMonth() + 1);
			tmp3.setType("差额");

			// 差额总值
			tmp1.setTotal(tmp3.getTotal());
			tmp2.setTotal(tmp3.getTotal());
			tmp4.setTotal(tmp3.getTotal());

			rs.add(tmp1);
			rs.add(tmp2);
			rs.add(tmp3);
			rs.add(tmp4);
		}

		return rs;
	}

	/**
	 * 结转差额总表查询
	 * 
	 */
	@SuppressWarnings("deprecation")
	public List<CarryBalance> getCasTransferfactoryDiffAllInfo(
			final ConditionBalance conditionBalance) {
		// 1.数量按原来的方法计算 2.重量按其他方法计算-单位折算
		List<CarryBalance> rs = new ArrayList<CarryBalance>();
		Date begin = parseDate(conditionBalance.getDate().getYear());
		Date end = parseDate(conditionBalance.getDate().getYear() + 1);
		end.setDate(end.getDate() - 1);
		System.out.println(begin.toLocaleString());
		System.out.println(end.toLocaleString());
		if (conditionBalance.getIsQuantity()) {
			// 统计 已收货未结转期初单-期初数
			// 物料/进口 统计 1015 已收货未结转期初单 billtype.code = "1015"
			// 成品/出口 统计 2011 已交货未结转期初单 billtype.code = "2011"
			List<Object[]> qcsList = countBillNums(begin, end, true,
					conditionBalance.isM() ? "1015" : "2011", conditionBalance);

			conditionBalance.getDate().setDate(
					conditionBalance.getDate().getDate());
			end = conditionBalance.getDate();

			// 查询统计收货数
			// 物料/进口 查询 1004 结转进口单 billtype.code = "1004"
			// 成品/出口 查询 2102 结转出口单 billtype.code = "2102"
			List<Object[]> shsList = countBillNums(begin, end, false,
					conditionBalance.isM() ? "1004" : "2102", conditionBalance);

			// 查询统计退货数
			// 物料/进口 查询 1106 结转料件退货单 billtype.code = "1106"
			// 成品/出口 查询 2009 结转成品退货单 billtype.code = "2009"
			List<Object[]> thsList = countBillNums(begin, end, false,
					conditionBalance.isM() ? "1106" : "2009", conditionBalance);

			// 查询报关单
			List<Object[]> bgList = countZCSForCustom(conditionBalance, begin,
					end);

			KeyAndPut keyAndPut = new KeyAndPut();
			keyAndPut.setGroupCodintion(conditionBalance.getGroupCondition());
			System.out.println("keyAndPut" + keyAndPut.toString());

			// 数据统计map
			Map<String, CarryBalance> map = new HashMap<String, CarryBalance>();

			// 把 期初数 的数据 放入 map
			keyAndPut.isQcs = true;
			CommonUtils.listToMap(qcsList, map, keyAndPut);

			// 把 收货 key 放入 map
			keyAndPut.isQcs = false;
			CommonUtils.listToMap(shsList, map, keyAndPut);

			// 把 退货 key 放入 map
			CommonUtils.listToMap(thsList, map, keyAndPut);

			// 把 转厂数 key 放入 map
			CommonUtils.listToMap(bgList, map, keyAndPut);

			CaleKeyPut caleKeyPut = new CaleKeyPut();
			caleKeyPut.setGroupCodintion(conditionBalance.getGroupCondition());

			// 计算统计收送货数
			Map<String, CarryBalance> shsMap = CommonUtils.listToMap(shsList,
					new HashMap(), caleKeyPut);

			// 计算统计退货数
			Map<String, CarryBalance> thsMap = CommonUtils.listToMap(thsList,
					new HashMap(), caleKeyPut);

			// 计算统计报关单
			Map<String, CarryBalance> bgMap = CommonUtils.listToMap(bgList,
					new HashMap(), caleKeyPut);

			System.out.println(begin.toLocaleString());
			System.out.println(end.toLocaleString());
			List g = conditionBalance.getGroupCondition();
			for (CarryBalance carryBalance : map.values()) {
				/*
				 * key =
				 * ((carryBalance.getCustomerName()==null||"".equals(carryBalance
				 * .getCustomerName())?"":"/"+carryBalance.getCustomerName())+
				 * (carryBalance
				 * .getName()==null||"".equals(carryBalance.getName(
				 * ))?"":"/"+carryBalance.getName())+
				 * (carryBalance.getSpec()==null
				 * ||"".equals(carryBalance.getSpec(
				 * ))?"":"/"+carryBalance.getSpec())+
				 * (carryBalance.getUnitName()
				 * ==null||"".equals(carryBalance.getUnitName
				 * ())?"":"/"+carryBalance.getUnitName()));
				 */

				// setGroupCodintion
				String key = "";
				for (int i = 0, j = 0; j < g.size() && i < 5; i++) {
					String groupCol = (String) g.get(j);

					if (groupCol != null) {
						key += "/";
						if (groupCol.equals("b.billMaster.scmCoc.name")) {
							// 客户名称
							key += carryBalance.getCustomerName() == null
									|| "".equals(carryBalance.getCustomerName()) ? ""
									: "" + carryBalance.getCustomerName();
							j++;
						}
						if (groupCol.equals("b.billMaster.scmCoc.code")
								&& i == 1) {
							// 客户名称
							key += carryBalance.getCustomerName() == null
									|| "".equals(carryBalance.getCustomerCode()) ? ""
									: "" + carryBalance.getCustomerCode();
							j++;
						}
						if (groupCol.equals("b.hsName") && i == 2) {
							// 商品名称
							key += carryBalance.getName() == null
									|| "".equals(carryBalance.getName()) ? ""
									: "" + carryBalance.getName();
							j++;
						}
						if (groupCol.equals("b.hsSpec") && i == 3) {
							// 商品型号
							key += carryBalance.getSpec() == null
									|| "".equals(carryBalance.getSpec()) ? ""
									: "" + carryBalance.getSpec();
							j++;
						}
						if (groupCol.equals("b.hsUnit.name") && i == 4) {
							// 单位
							key += carryBalance.getUnitName() == null
									|| "".equals(carryBalance.getUnitName()) ? ""
									: "" + carryBalance.getUnitName();
							j++;
						}
					}
				}

				// 送货
				CarryBalance shs = shsMap.get(key);
				if (shs == null) {
					shs = new CarryBalance();
					shs.init();
					shs.setName(carryBalance.getName());
					shs.setUnitName(carryBalance.getUnitName());
					shs.setCustomerName(carryBalance.getCustomerName());
					shs.setCustomerCode(carryBalance.getCustomerCode());
					shs.setSpec(carryBalance.getSpec());
					shs.setScmCoc(carryBalance.getScmCoc());
				}
				shs.setAmountFirst(carryBalance.getAmountFirst());
				shs.setType(conditionBalance.isM() ? "收货数" : "送货数");
				System.out.println("setCustomercode"
						+ shs.getScmCoc().getCode());
				// 转厂
				CarryBalance zcs = bgMap.get(key);
				if (zcs == null) {
					zcs = new CarryBalance();
					zcs.init();
					zcs.setName(carryBalance.getName());
					zcs.setUnitName(carryBalance.getUnitName());
					zcs.setCustomerName(carryBalance.getCustomerName());
					zcs.setCustomerCode(carryBalance.getCustomerCode());
					zcs.setSpec(carryBalance.getSpec());
					zcs.setScmCoc(carryBalance.getScmCoc());
				}
				zcs.setAmountFirst(carryBalance.getAmountFirst());
				zcs.setType("转厂数");

				// 退货
				CarryBalance ths = thsMap.get(key);
				if (ths == null) {
					ths = new CarryBalance();
					ths.init();
					ths.setName(carryBalance.getName());
					ths.setUnitName(carryBalance.getUnitName());
					ths.setCustomerName(carryBalance.getCustomerName());
					ths.setCustomerCode(carryBalance.getCustomerCode());
					ths.setSpec(carryBalance.getSpec());
					ths.setScmCoc(carryBalance.getScmCoc());
				}
				ths.setAmountFirst(carryBalance.getAmountFirst());
				ths.setType("退货数");

				// 差额
				CarryBalance diff = carryBalance;
				diff = caleDiffCustom(diff, shs, zcs, ths, conditionBalance
						.getDate().getMonth() + 1);
				diff.setType("差额");

				// 差额总值
				shs.setTotal(diff.getTotal());
				zcs.setTotal(diff.getTotal());
				ths.setTotal(diff.getTotal());

				rs.add(shs);
				rs.add(ths);
				rs.add(zcs);
				rs.add(diff);
			}
		} else {
			// 【期初重量】【收货重量】【退货重量】计算
			// 统计 已收货未结转期初单-期初数
			// 物料/进口 统计 1015 已收货未结转期初单 billtype.code = "1015"
			// 成品/出口 统计 2011 已交货未结转期初单 billtype.code = "2011"
			List<Object[]> qcsList = countBillWeight(begin, end, true,
					conditionBalance.isM() ? "1015" : "2011", conditionBalance);

			List qcsBillList = convertBillWeight(qcsList, false);
			conditionBalance.getDate().setDate(
					conditionBalance.getDate().getDate());
			end = conditionBalance.getDate();
			// 查询统计收货数
			// 物料/进口 查询 1004 结转进口单 billtype.code = "1004"
			// 成品/出口 查询 2102 结转出口单 billtype.code = "2102"
			List<Object[]> shsList = countBillWeight(begin, end, false,
					conditionBalance.isM() ? "1004" : "2102", conditionBalance);

			List shsBillList = convertBillWeight(shsList, false);
			// 查询统计退货数
			// 物料/进口 查询 1106 结转料件退货单 billtype.code = "1106"
			// 成品/出口 查询 2009 结转成品退货单 billtype.code = "2009"
			List<Object[]> thsList = countBillWeight(begin, end, false,
					conditionBalance.isM() ? "1106" : "2009", conditionBalance);
			List thsBillList = convertBillWeight(thsList, false);
			// 查询报关单
			List<Object[]> bgList = countZCSForCustomWeight(conditionBalance,
					begin, end);
			System.out.println("$$$" + bgList.size());
			List bgBillList = convertBillWeight(bgList, true);
			WeightKeyAndPut keyAndPut = new WeightKeyAndPut();
			keyAndPut.setGroupCodintion(conditionBalance.getGroupCondition());

			// 数据统计map
			Map<String, CarryBalance> map = new HashMap<String, CarryBalance>();

			// 把 期初数 的数据 放入 map
			keyAndPut.isQcs = true;
			CommonUtils.listToMap(qcsBillList, map, keyAndPut);

			// 把 收货 key 放入 map
			keyAndPut.isQcs = false;
			CommonUtils.listToMap(shsBillList, map, keyAndPut);

			// 把 退货 key 放入 map
			CommonUtils.listToMap(thsBillList, map, keyAndPut);

			// 把 转厂数 key 放入 map
			CommonUtils.listToMap(bgBillList, map, keyAndPut);

			WeightCaleKeyPut weightCaleKeyPut = new WeightCaleKeyPut();
			weightCaleKeyPut.setGroupCodintion(conditionBalance
					.getGroupCondition());

			// 计算统计收送货数
			Map<String, CarryBalance> shsMap = CommonUtils.listToMap(
					shsBillList, new HashMap(), weightCaleKeyPut);

			// 计算统计退货数
			Map<String, CarryBalance> thsMap = CommonUtils.listToMap(
					thsBillList, new HashMap(), weightCaleKeyPut);

			// 计算统计报关单
			Map<String, CarryBalance> bgMap = CommonUtils.listToMap(bgBillList,
					new HashMap(), weightCaleKeyPut);
			System.out.println(begin.toLocaleString());
			System.out.println(end.toLocaleString());
			List g = conditionBalance.getGroupCondition();
			for (CarryBalance carryBalance : map.values()) {
				/*
				 * key =
				 * ((carryBalance.getCustomerName()==null||"".equals(carryBalance
				 * .getCustomerName())?"":"/"+carryBalance.getCustomerName())+
				 * (carryBalance
				 * .getName()==null||"".equals(carryBalance.getName(
				 * ))?"":"/"+carryBalance.getName())+
				 * (carryBalance.getSpec()==null
				 * ||"".equals(carryBalance.getSpec(
				 * ))?"":"/"+carryBalance.getSpec())+
				 * (carryBalance.getUnitName()
				 * ==null||"".equals(carryBalance.getUnitName
				 * ())?"":"/"+carryBalance.getUnitName()));
				 */

				// setGroupCodintion
				String key = "";
				for (int i = 0, j = 0; j < g.size() && i < 5; i++) {
					String groupCol = (String) g.get(j);
					if (groupCol != null) {
						key += "/";
						if (groupCol.equals("b.billMaster.scmCoc.name")) {
							// 客户名称
							key += carryBalance.getCustomerName() == null
									|| "".equals(carryBalance.getCustomerName()) ? ""
									: "" + carryBalance.getCustomerName();
							j++;
						}
						if (groupCol.equals("b.billMaster.scmCoc.code")
								&& i == 1) {
							// 客户名称
							key += carryBalance.getCustomerCode() == null
									|| "".equals(carryBalance.getCustomerCode()) ? ""
									: "" + carryBalance.getCustomerCode();
							j++;
						}
						if (groupCol.equals("b.hsName") && i == 2) {
							// 商品名称
							key += carryBalance.getName() == null
									|| "".equals(carryBalance.getName()) ? ""
									: "" + carryBalance.getName();
							j++;
						}
						if (groupCol.equals("b.hsSpec") && i == 3) {
							// 商品型号
							key += carryBalance.getSpec() == null
									|| "".equals(carryBalance.getSpec()) ? ""
									: "" + carryBalance.getSpec();
							j++;
						}
						if (groupCol.equals("b.hsUnit.name") && i == 4) {
							// 单位
							key += carryBalance.getUnitName() == null
									|| "".equals(carryBalance.getUnitName()) ? ""
									: "" + carryBalance.getUnitName();
							j++;
						}
					}
				}
				// 送货
				CarryBalance shs = shsMap.get(key);
				if (shs == null) {
					shs = new CarryBalance();
					shs.init();
					shs.setName(carryBalance.getName());
					shs.setUnitName(carryBalance.getUnitName());
					shs.setCustomerName(carryBalance.getCustomerName());
					shs.setSpec(carryBalance.getSpec());
					shs.setScmCoc(carryBalance.getScmCoc());

				}
				shs.setAmountFirst(carryBalance.getAmountFirst());
				shs.setType(conditionBalance.isM() ? "收货重量" : "送货重量");

				// 转厂
				CarryBalance zcs = bgMap.get(key);
				if (zcs == null) {
					zcs = new CarryBalance();
					zcs.init();
					zcs.setName(carryBalance.getName());
					zcs.setUnitName(carryBalance.getUnitName());
					zcs.setCustomerName(carryBalance.getCustomerName());
					zcs.setSpec(carryBalance.getSpec());
					zcs.setScmCoc(carryBalance.getScmCoc());
				}
				zcs.setAmountFirst(carryBalance.getAmountFirst());
				zcs.setType("转厂重量");

				// 退货
				CarryBalance ths = thsMap.get(key);
				if (ths == null) {
					ths = new CarryBalance();
					ths.init();
					ths.setName(carryBalance.getName());
					ths.setUnitName(carryBalance.getUnitName());
					ths.setCustomerName(carryBalance.getCustomerName());
					ths.setSpec(carryBalance.getSpec());
					ths.setScmCoc(carryBalance.getScmCoc());
				}
				ths.setAmountFirst(carryBalance.getAmountFirst());
				ths.setType("退货重量");

				// 差额
				CarryBalance diff = carryBalance;
				diff = caleDiffCustom(diff, shs, zcs, ths, conditionBalance
						.getDate().getMonth() + 1);
				diff.setType("差额重量");

				// 差额总值
				shs.setTotal(diff.getTotal());
				zcs.setTotal(diff.getTotal());
				ths.setTotal(diff.getTotal());

				rs.add(shs);
				rs.add(ths);
				rs.add(zcs);
				rs.add(diff);
			}
		}
		return rs;
	}

	class KeyAndPut extends GetKeyValueImpl<Object[]> {
		private List<String> groupCodintion;
		private int groupColSize = 0;
		boolean isQcs = false;

		public void setGroupCodintion(List<String> groupCodintion) {
			this.groupCodintion = groupCodintion;
			if (groupCodintion != null) {
				groupColSize = groupCodintion.size();
			}
		}

		public String getKey(Object[] t) {
			String key = "";
			String groupCol = null;
			for (int i = 0, j = 0; j < groupCodintion.size() && i < 5; i++) {
				groupCol = groupCodintion.get(j);
				key += "/";
				if (groupCol.equals("b.billMaster.scmCoc.name")) {
					// 客户名称
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.billMaster.scmCoc.code") && i == 1) {
					// 客户名称
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.hsName") && i == 2) {
					// 商品名称
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.hsSpec") && i == 3) {
					// 商品型号
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.hsUnit.name") && i == 4) {
					// 单位
					key += t[j];
					j++;
				}
			}

			return key;
		}

		public void put(Object[] t, Map map) {
			String key = getKey(t);
			CarryBalance cb = (CarryBalance) map.get(key);

			Double amount = (t[groupColSize] == null ? 0
					: (Double) t[groupColSize]); // 数量

			if (cb == null) {
				cb = new CarryBalance();
				cb.init();
				String groupCol = null;
				for (int i = 0; i < groupColSize; i++) {
					groupCol = groupCodintion.get(i);
					if (groupCol.equals("b.billMaster.scmCoc.name")) {
						// 客户名称
						cb.setCustomerName((String) t[i]);
					} else if (groupCol.equals("b.billMaster.scmCoc.code")) {
						cb.setCustomerCode((String) t[i]);
					} else if (groupCol.equals("b.hsName")) {
						// 商品名称
						cb.setName((String) t[i]);
					} else if (groupCol.equals("b.hsSpec")) {
						// 商品型号
						cb.setSpec((String) t[i]);
					} else if (groupCol.equals("b.hsUnit.name")) {
						// 单位
						cb.setUnitName((String) t[i]);
					}
				}

				if (cb.getScmCoc() == null) {
					ScmCoc scmCoc = new ScmCoc();
					scmCoc.setLinkTel(t[t.length - 4] == null ? ""
							: t[t.length - 4].toString());
					scmCoc.setFax(t[t.length - 3] == null ? ""
							: t[t.length - 3].toString());
					scmCoc.setCode(t[t.length - 2] == null ? ""
							: t[t.length - 2].toString());
					scmCoc.setName(t[t.length - 5] == null ? ""
							: t[t.length - 5].toString());
					scmCoc.setLinkMan(t[t.length - 1] == null ? ""
							: t[t.length - 1].toString());
					System.out.println("######" + scmCoc.getLinkMan()
							+ scmCoc.getLinkTel() + scmCoc.getFax()
							+ scmCoc.getCode() + scmCoc.getName());
					cb.setScmCoc(scmCoc);
				}

				if (isQcs) {
					cb.setAmountFirst(amount);
				} else {
					cb.setAmountFirst(0.0);
				}

				map.put(key, cb);
			} else {
				if (isQcs) {
					cb.setAmountFirst(cb.getAmountFirst() + amount);
				}
			}
		}
	}

	class CaleKeyPut extends GetKeyValueImpl<Object[]> {

		private List<String> groupCodintion;
		private int groupColSize = 0;

		public void setGroupCodintion(List<String> groupCodintion) {
			this.groupCodintion = groupCodintion;
			if (groupCodintion != null) {
				groupColSize = groupCodintion.size();
			}
		}

		public String getKey(Object[] t) {
			String key = "";
			String groupCol = null;
			for (int i = 0, j = 0; j < groupCodintion.size() && i < 5; i++) {
				groupCol = groupCodintion.get(j);
				key += "/";
				if (groupCol.equals("b.billMaster.scmCoc.name")) {
					// 客户名称
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.billMaster.scmCoc.code") && i == 1) {
					// 客户编码
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.hsName") && i == 2) {
					// 商品名称
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.hsSpec") && i == 3) {
					// 商品型号
					key += t[j];
					j++;
				}
				if (groupCol.equals("b.hsUnit.name") && i == 4) {
					// 单位
					key += t[j];
					j++;
				}
			}
			return key;
		}

		@SuppressWarnings("deprecation")
		public void put(Object[] t, Map map) {
			String key = getKey(t);
			CarryBalance cb = (CarryBalance) map.get(key);

			Double num = (t[groupColSize] == null ? 0
					: (Double) t[groupColSize]); // 数量

			if (cb == null) {
				cb = new CarryBalance();
				cb.init();
				String groupCol = null;
				for (int i = 0; i < groupColSize; i++) {
					groupCol = groupCodintion.get(i);
					if (groupCol.equals("b.billMaster.scmCoc.name")) {
						// 客户名称
						cb.setCustomerName((String) t[i]);
					} else if (groupCol.equals("b.billMaster.scmCoc.code")) {
						// 客户代码
					} else if (groupCol.equals("b.hsName")) {
						// 商品名称
						cb.setName((String) t[i]);
					} else if (groupCol.equals("b.hsSpec")) {
						// 商品型号
						cb.setSpec((String) t[i]);
					} else if (groupCol.equals("b.hsUnit.name")) {
						// 单位
						cb.setUnitName((String) t[i]);
					}
				}

				if (cb.getScmCoc() == null) {
					ScmCoc scmCoc = new ScmCoc();
					scmCoc.setLinkTel(t[t.length - 4] == null ? ""
							: t[t.length - 4].toString());
					scmCoc.setFax(t[t.length - 3] == null ? ""
							: t[t.length - 3].toString());
					scmCoc.setCode(t[t.length - 2] == null ? ""
							: t[t.length - 2].toString());
					scmCoc.setName(t[t.length - 5] == null ? ""
							: t[t.length - 5].toString());
					scmCoc.setLinkMan(t[t.length - 1] == null ? ""
							: t[t.length - 1].toString());
					System.out.println("$$$$$$" + scmCoc.getLinkMan() + "/"
							+ scmCoc.getLinkTel() + "/" + scmCoc.getFax() + "/"
							+ scmCoc.getCode() + "/" + scmCoc.getName());
					cb.setScmCoc(scmCoc);
				}

				map.put(key, cb);
			}
			int month = ((Date) (t[groupColSize + 1])).getMonth();
			switch (month) {
			case 0:
				cb.setAmountMonth1(cb.getAmountMonth1() + num);
				break;
			case 1:
				cb.setAmountMonth2(cb.getAmountMonth2() + num);
				break;
			case 2:
				cb.setAmountMonth3(cb.getAmountMonth3() + num);
				break;
			case 3:
				cb.setAmountMonth4(cb.getAmountMonth4() + num);
				break;
			case 4:
				cb.setAmountMonth5(cb.getAmountMonth5() + num);
				break;
			case 5:
				cb.setAmountMonth6(cb.getAmountMonth6() + num);
				break;
			case 6:
				cb.setAmountMonth7(cb.getAmountMonth7() + num);
				break;
			case 7:
				cb.setAmountMonth8(cb.getAmountMonth8() + num);
				break;
			case 8:
				cb.setAmountMonth9(cb.getAmountMonth9() + num);
				break;
			case 9:
				cb.setAmountMonth10(cb.getAmountMonth10() + num);
				break;
			case 10:
				cb.setAmountMonth11(cb.getAmountMonth11() + num);
				break;
			case 11:
				cb.setAmountMonth12(cb.getAmountMonth12() + num);
				break;
			default:
				break;
			}
		}

	}

	class WeightKeyAndPut extends GetKeyValueImpl<BillObject> {
		private List<String> groupCodintion;
		private int groupColSize = 0;
		boolean isQcs = false;

		public void setGroupCodintion(List<String> groupCodintion) {
			this.groupCodintion = groupCodintion;
			if (groupCodintion != null) {
				groupColSize = groupCodintion.size();
			}
		}

		public String getKey(BillObject t) {
			String key = "";
			String groupCol = null;
			for (int i = 0, j = 0; j < groupCodintion.size() && i < 5; i++) {
				groupCol = groupCodintion.get(j);
				key += "/";
				if (groupCol.equals("b.billMaster.scmCoc.name")) {
					// 客户名称
					key += t.getBill13();
					j++;
				}
				if (groupCol.equals("b.billMaster.scmCoc.code") && i == 1) {
					// 客户代码
					key += t.getBill14();
					j++;
				}
				if (groupCol.equals("b.hsName") && i == 2) {
					// 商品名称
					key += t.getBill11();
					j++;
				}
				if (groupCol.equals("b.hsSpec") && i == 3) {
					// 商品型号
					key += t.getBill12();
					j++;
				}
				if (groupCol.equals("b.hsUnit.name") && i == 4) {
					// 单位
					key += t.getBill5();
					j++;
				}
			}

			return key;
		}

		public void put(BillObject t, Map map) {
			String key = getKey(t);
			CarryBalance cb = (CarryBalance) map.get(key);
			Double amount = (t.getBill10() == null ? 0 : (Double) t.getBill10()); // 数量
			if (cb == null) {
				cb = new CarryBalance();
				cb.init();
				String groupCol = null;
				for (int i = 0; i < groupColSize; i++) {
					groupCol = groupCodintion.get(i);
					if (groupCol.equals("b.billMaster.scmCoc.name")) {
						// 客户名称
						cb.setCustomerName(t.getBill13());
					} else if (groupCol.equals("b.billMaster.scmCoc.code")) {
						// 客户代码
						cb.setCustomerCode(t.getBill14());
					} else if (groupCol.equals("b.hsName")) {
						// 商品名称
						cb.setName(t.getBill11());
					} else if (groupCol.equals("b.hsSpec")) {
						// 商品型号
						cb.setSpec(t.getBill12());
					} else if (groupCol.equals("b.hsUnit.name")) {
						// 单位
						cb.setUnitName(t.getBill5());
					}
				}

				if (cb.getScmCoc() == null) {
					ScmCoc scmCoc = new ScmCoc();
					scmCoc.setLinkTel(t.getBill8() == null ? "" : t.getBill8());
					scmCoc.setFax(t.getBill9() == null ? "" : t.getBill9());
					scmCoc.setCode(t.getBill14() == null ? "" : t.getBill14());
					scmCoc.setName(t.getBill13() == null ? "" : t.getBill13());
					scmCoc.setLinkMan(t.getBill15() == null ? "" : t
							.getBill15());
					System.out.println("!!!!!" + scmCoc.getLinkMan()
							+ scmCoc.getLinkTel() + scmCoc.getFax()
							+ scmCoc.getCode() + scmCoc.getName());
					cb.setScmCoc(scmCoc);
				}

				if (isQcs) {
					cb.setAmountFirst(amount);
				} else {
					cb.setAmountFirst(0.0);
				}
				map.put(key, cb);
			} else {
				if (isQcs) {
					cb.setAmountFirst(cb.getAmountFirst() + amount);
				}
			}
		}
	}

	class WeightCaleKeyPut extends GetKeyValueImpl<BillObject> {

		private List<String> groupCodintion;
		private int groupColSize = 0;

		public void setGroupCodintion(List<String> groupCodintion) {
			this.groupCodintion = groupCodintion;
			if (groupCodintion != null) {
				groupColSize = groupCodintion.size();
			}
		}

		public String getKey(BillObject t) {
			String key = "";
			String groupCol = null;
			for (int i = 0, j = 0; j < groupCodintion.size() && i < 5; i++) {
				groupCol = groupCodintion.get(j);
				key += "/";
				if (groupCol.equals("b.billMaster.scmCoc.name")) {
					// 客户名称
					key += t.getBill13();
					j++;
				}
				if (groupCol.equals("b.billMaster.scmCoc.code") && i == 1) {
					// 客户代码
					key += t.getBill14();
					j++;
				}
				if (groupCol.equals("b.hsName") && i == 2) {
					// 商品名称
					key += t.getBill11();
					j++;
				}
				if (groupCol.equals("b.hsSpec") && i == 3) {
					// 商品型号
					key += t.getBill12();
					j++;
				}
				if (groupCol.equals("b.hsUnit.name") && i == 4) {
					// 单位
					key += t.getBill5();
					j++;
				}
			}

			return key;
		}

		@SuppressWarnings("deprecation")
		public void put(BillObject t, Map map) {
			String key = getKey(t);
			CarryBalance cb = (CarryBalance) map.get(key);
			Double num = t.getBill10() == null ? 0 : t.getBill10(); // 数量
			if (cb == null) {
				cb = new CarryBalance();
				cb.init();
				String groupCol = null;
				for (int i = 0; i < groupColSize; i++) {
					groupCol = groupCodintion.get(i);
					if (groupCol.equals("b.billMaster.scmCoc.name")) {
						// 客户名称
						cb.setCustomerName(t.getBill13());
					} else if (groupCol.equals("b.billMaster.scmCoc.code")) {
						// 客户代码
						cb.setCustomerCode(t.getBill14());
					} else if (groupCol.equals("b.hsName")) {
						// 商品名称
						cb.setName(t.getBill11());
					} else if (groupCol.equals("b.hsSpec")) {
						// 商品型号
						cb.setSpec(t.getBill12());
					} else if (groupCol.equals("b.hsUnit.name")) {
						// 单位
						cb.setUnitName(t.getBill5());
					}
				}

				if (cb.getScmCoc() == null) {
					ScmCoc scmCoc = new ScmCoc();
					scmCoc.setLinkTel(t.getBill8() == null ? "" : t.getBill8());
					scmCoc.setFax(t.getBill9() == null ? "" : t.getBill9());
					scmCoc.setCode(t.getBill14() == null ? "" : t.getBill14());
					scmCoc.setName(t.getBill13() == null ? "" : t.getBill13());
					scmCoc.setLinkMan(t.getBill15() == null ? "" : t
							.getBill15());
					System.out.println("@@@@@" + scmCoc.getLinkMan()
							+ scmCoc.getLinkTel() + scmCoc.getFax()
							+ scmCoc.getCode() + scmCoc.getName());
					cb.setScmCoc(scmCoc);
				}
				map.put(key, cb);
			}
			int month = t.getBill7().getMonth();
			switch (month) {
			case 0:
				cb.setAmountMonth1(cb.getAmountMonth1() + num);
				break;
			case 1:
				cb.setAmountMonth2(cb.getAmountMonth2() + num);
				break;
			case 2:
				cb.setAmountMonth3(cb.getAmountMonth3() + num);
				break;
			case 3:
				cb.setAmountMonth4(cb.getAmountMonth4() + num);
				break;
			case 4:
				cb.setAmountMonth5(cb.getAmountMonth5() + num);
				break;
			case 5:
				cb.setAmountMonth6(cb.getAmountMonth6() + num);
				break;
			case 6:
				cb.setAmountMonth7(cb.getAmountMonth7() + num);
				break;
			case 7:
				cb.setAmountMonth8(cb.getAmountMonth8() + num);
				break;
			case 8:
				cb.setAmountMonth9(cb.getAmountMonth9() + num);
				break;
			case 9:
				cb.setAmountMonth10(cb.getAmountMonth10() + num);
				break;
			case 10:
				cb.setAmountMonth11(cb.getAmountMonth11() + num);
				break;
			case 11:
				cb.setAmountMonth12(cb.getAmountMonth12() + num);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 【期初重量】【收货重量】【退货重量】计算方法
	 * 
	 * @param list
	 * @param isZCS是否转厂数
	 * @return 计算后的单据重量,billList
	 * 
	 */

	private List convertBillWeight(List list, boolean isZCS) {
		List billList = new ArrayList();
		if (list != null) {
			Object[] obj;
			for (int i = 0; i < list.size(); i++) {
				obj = (Object[]) list.get(i);
				BillObject billObject = new BillObject();
				billObject.setBill13(obj[0] == null ? "" : obj[0].toString());// 客户
				billObject.setBill11(obj[1] == null ? "" : obj[1].toString());// 海关商品名称
				billObject.setBill12(obj[2] == null ? "" : obj[2].toString());// 海关商品规格型号
				billObject.setBill5(obj[3] == null ? "" : obj[3].toString());// 报关法定单位
				billObject.setBill1(obj[4] == null ? "" : obj[4].toString());// 料号
				billObject.setBill2(obj[5] == null ? "" : obj[5].toString());// 单据明细中的单位或报关单明细中的单位
				billObject.setBill3(obj[6] == null ? Double.valueOf(0) : Double
						.valueOf(obj[6].toString()));// 工厂数量或报关净重
				billObject.setBill4(obj[7] == null ? "" : obj[7].toString());// 商品编码
				billObject.setBill6(obj[8] == null ? Double.valueOf(0) : Double
						.valueOf(obj[8].toString()));// 报关数量或报关净重

				billObject.setBill7((Date) obj[9]);// 生效日期
				System.out.println("^^^^^" + billObject.getBill7());
				billObject.setBill8(obj[10] == null ? "" : obj[10].toString());// 联系电话
				billObject.setBill9(obj[11] == null ? "" : obj[11].toString());// 传真
				billObject.setBill14(obj[12] == null ? "" : obj[12].toString());// 客户代码
				billObject.setBill15(obj[13] == null ? "" : obj[13].toString());// 联系人
				// 重量
				/*
				 * 判断：一、当报关单为千克时，取折算报关单数量（与原计算方法不变） 二、当报关单位不为千克，且工厂单位为千克时，取工厂数量
				 * 三、当报关单位不为千克，且工厂单位不为千克时，取工厂数量*单净重
				 */
				if (isZCS) {

					billObject.setBill10(obj[8] == null ? Double.valueOf(0)
							: Double.valueOf(obj[8].toString()));// 报关净重
					System.out.println("^^^^^" + billObject.getBill10());
				} else {
					if (!("千克".equals(obj[5].toString()))
							&& !("千克".equals(obj[3].toString()))) {
						List fafclist = findFactoryAndFactualCustomsRalationByPartComplex(
								obj[4].toString(), obj[7].toString());
						for (int j = 0; j < fafclist.size(); j++) {
							FactoryAndFactualCustomsRalation fafc = (FactoryAndFactualCustomsRalation) fafclist
									.get(j);
							if (fafc.getMateriel().getPtNo()
									.equals(obj[4].toString())) {// 区分料号大小写
								Double netWeight = fafc.getMateriel()
										.getPtNetWeight() == null ? Double
										.valueOf(0) : fafc.getMateriel()
										.getPtNetWeight();
								Double customsWeight = Double
										.valueOf(CommonUtils.formatDoubleByDigit(
												(billObject.getBill3() * netWeight),
												4));
								System.out.println("customsWeight======"
										+ customsWeight + "==料号：=="
										+ obj[4].toString() + "=商品编码：="
										+ obj[7].toString());
								billObject.setBill10(customsWeight);
							} else {
								billObject.setBill10(Double.valueOf(0));
							}
						}
					} else if ("千克".equals(obj[5].toString())
							&& !"千克".equals(obj[3].toString())) {
						billObject.setBill10(obj[6] == null ? Double.valueOf(0)
								: Double.valueOf(obj[6].toString()));
					} else if ("千克".equals(obj[3].toString())) {
						billObject.setBill10(obj[8] == null ? Double.valueOf(0)
								: Double.valueOf(obj[8].toString()));
					} else {
						billObject.setBill10(obj[8] == null ? Double.valueOf(0)
								: Double.valueOf(obj[8].toString()));
					}
				}
				billList.add(billObject);
			}
		}
		return billList;
	}

	@SuppressWarnings("deprecation")
	private static Date parseDate(int year) {
		Date date = new Date(1900 + year + "/01/01");
		return date;
	}

	/**
	 * 统计期初数
	 * 
	 * @param customerCode
	 *            客户编号
	 * @param name
	 *            商品名称
	 * @param spec
	 *            商品规格
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param isGroup
	 *            是否分组统计
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countQCS(String customerCode, String name,
			String spec, Date begin, Date end, Boolean isGroup,
			Boolean isImpExpGoods) {
		List<Object> args = new ArrayList<Object>();
		args.add(end);
		args.add(begin);
		args.add(isImpExpGoods);
		StringBuilder hsql = new StringBuilder(
				"select td.transferFactoryInitBill.scmCoc.name,"
						+ "td.commName,td.commSpec,td.unit.name")
				.append(isGroup ? ",sum(td.noTransferQuantity)"
						: ",td.noTransferQuantity"
								+ ",td.transferFactoryInitBill.effectiveDate")
				.append(" from TransferFactoryInitBillCommodityInfo as td")
				.append(" where td.transferFactoryInitBill.effectiveDate <= ?")
				.append(" and td.transferFactoryInitBill.effectiveDate >= ?")
				.append(" and td.transferFactoryInitBill.isImpExpFlag = ?");

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append(" and td.transferFactoryInitBill.scmCoc.code = ?");
			args.add(customerCode);
		}
		if (name != null && !"".equals(name)) {
			hsql.append(" and td.commName = ?");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append(" and td.commSpec = ?");
			args.add(spec);
		}

		if (isGroup) {
			hsql.append(" group by td.transferFactoryInitBill.scmCoc.name,td.commName,td.commSpec,td.unit.name");
			hsql.append(" order by td.transferFactoryInitBill.scmCoc.name,td.commName,td.commSpec,td.unit.name");
		}
		return find(hsql.toString(), args.toArray());
	}

	/**
	 * (海关帐)统计/查询 单据数量 (已收货未结转期初单、结转进口单)
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param isGroup
	 *            是否分组统计
	 * @param billType
	 *            单据类型
	 * @param condition
	 *            查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countBillNums(Date begin, Date end, Boolean isGroup,
			String billType, ConditionBalance condition) {

		// customerCode 客户编号
		// name 商品名称
		// spec 商品规格
		String customerCode = condition.getCustomerCode();
		String name = condition.getName();
		String spec = condition.getSpec();
		String customerName = condition.getCustomerName();
		// 是否进口
		boolean isM = condition.isM();

		// 拼装查询分组字段
		StringBuilder groupSb = new StringBuilder();
		for (int i = 0; i < condition.getGroupCondition().size(); i++) {
			if (i == 0) {
				groupSb.append(condition.getGroupCondition().get(i));
			} else {
				groupSb.append(", " + condition.getGroupCondition().get(i));
			}
		}

		// 查询参数
		List<Object> args = new ArrayList<Object>();

		/*
		 * 组装hql查询语句
		 */
		StringBuilder hsql = new StringBuilder("select ");

		// 追加查询分组字段
		hsql.append(groupSb);

		if (isGroup) {
			hsql.append(", sum(b.hsAmount),b.billMaster.validDate,b.billMaster.scmCoc.name, b.billMaster.scmCoc.linkTel, b.billMaster.scmCoc.fax,b.billMaster.scmCoc.code,b.billMaster.scmCoc.linkMan ");
		} else {
			hsql.append(", b.hsAmount, b.billMaster.validDate,b.billMaster.scmCoc.name, b.billMaster.scmCoc.linkTel, b.billMaster.scmCoc.fax,b.billMaster.scmCoc.code,b.billMaster.scmCoc.linkMan ");
		}
		hsql.append(
				"from " + (isM ? "BillDetailMateriel" : "BillDetailProduct")
						+ " as b ")
				.append("where b.billMaster.isValid = true ")
				.append("and b.billMaster.billType.code = ? ")
				.append("and b.billMaster.validDate <= ? ")
				.append("and b.billMaster.validDate >= ? ");
		args.add(billType); // 单据类型
		args.add(end); // 结束时间
		args.add(begin); // 开始时间

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append("and b.billMaster.scmCoc.code = ? ");
			args.add(customerCode);
		}
		if (customerName != null && !"".equals(customerName)) {
			hsql.append("and b.billMaster.scmCoc.name = ? ");
			args.add(customerName);
		}
		if (name != null && !"".equals(name)) {
			hsql.append("and b.hsName = ? ");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append("and b.hsSpec = ? ");
			args.add(spec);
		}

		if (isGroup) {
			hsql.append("group by "
					+ groupSb
					+ ",b.billMaster.scmCoc.linkTel, b.billMaster.scmCoc.fax,b.billMaster.scmCoc.linkMan,b.billMaster.validDate ");
			hsql.append("order by "
					+ groupSb
					+ ",b.billMaster.scmCoc.linkTel, b.billMaster.scmCoc.fax,b.billMaster.scmCoc.linkMan,b.billMaster.validDate ");
		}
		System.out.println("hsql" + hsql);

		return find(hsql.toString(), args.toArray());
	}

	/**
	 * (海关帐)统计/查询 单据重量 (已收货未结转期初单、结转进口单)
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param isGroup
	 *            是否分组统计
	 * @param billType
	 *            单据类型
	 * @param condition
	 *            查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countBillWeight(Date begin, Date end,
			Boolean isGroup, String billType, ConditionBalance condition) {

		// customerCode 客户编号
		// name 商品名称
		// spec 商品规格
		String customerCode = condition.getCustomerCode();
		String name = condition.getName();
		String spec = condition.getSpec();
		// 是否进口
		boolean isM = condition.isM();
		// 查询参数
		List<Object> args = new ArrayList<Object>();
		// 客户海关商品名称 海关商品规格型号 报关单位 料号 工厂单位 工厂数量 商品编码 报关数量 生效日期 联系电话 传真
		StringBuilder hsql = new StringBuilder("select ");
		hsql.append("b.billMaster.scmCoc.name,b.hsName,b.hsSpec,b.hsUnit.name,b.ptPart,b.ptUnit.name,"
				+ " b.ptAmount,b.complex.code,b.hsAmount,b.billMaster.validDate ,b.billMaster.scmCoc.linkTel,"
				+ " b.billMaster.scmCoc.fax,b.billMaster.scmCoc.code,b.billMaster.scmCoc.linkMan ");
		hsql.append(
				"from " + (isM ? "BillDetailMateriel" : "BillDetailProduct")
						+ " as b ")
				.append("where b.billMaster.isValid = true ")
				.append("and b.billMaster.billType.code = ? ")
				.append("and b.billMaster.validDate <= ? ")
				.append("and b.billMaster.validDate >= ? ");
		args.add(billType); // 单据类型
		args.add(end); // 结束时间
		args.add(begin); // 开始时间
		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append("and b.billMaster.scmCoc.code = ? ");
			args.add(customerCode);
		}
		if (name != null && !"".equals(name)) {
			hsql.append("and b.hsName = ? ");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append("and b.hsSpec = ? ");
			args.add(spec);
		}
		hsql.append("order by b.billMaster.scmCoc.linkTel, b.billMaster.scmCoc.fax ");
		System.out.println("hsqlzl" + hsql);
		return find(hsql.toString(), args.toArray());
	}

	/**
	 * 统计收货数,查询转厂收送货单据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countSHS(String customerCode, String name,
			String spec, Date begin, Date end, Boolean isGroup,
			Boolean isImpExpGoods) {
		List<Object> args = new ArrayList<Object>();
		args.add(end);
		args.add(begin);
		args.add(isImpExpGoods);

		StringBuilder hsql = new StringBuilder(
				"select td.transferFactoryBill.scmCoc.name,"
						+ "td.commName,td.commSpec,td.unit.name")
				.append(isGroup ? ",sum(td.transFactAmount)"
						: ",td.transFactAmount"
								+ ",td.transferFactoryBill.beginAvailability")
				.append(" from TransferFactoryCommodityInfo as td")
				.append(" where td.transferFactoryBill.beginAvailability <= ?")
				.append(" and td.transferFactoryBill.beginAvailability >= ?")
				.append(" and td.transferFactoryBill.isImpExpGoods = ?");

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append(" and td.transferFactoryBill.scmCoc.code = ?");
			args.add(customerCode);
		}
		if (name != null && !"".equals(name)) {
			hsql.append(" and td.commName = ?");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append(" and td.commSpec = ?");
			args.add(spec);
		}

		if (isGroup) {
			hsql.append(" group by td.transferFactoryBill.scmCoc.name,td.commName,td.commSpec,td.unit.name");
			hsql.append(" order by td.transferFactoryBill.scmCoc.name,td.commName,td.commSpec,td.unit.name");
		}

		return find(hsql.toString(), args.toArray());
	}

	/**
	 * 统计收货数,查询转厂收送货单据(台达)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countSHSTaiDa(String customerCode, String name,
			String spec, Date begin, Date end, Boolean isGroup,
			Boolean isImpExpGoods) {
		List<Object> args = new ArrayList<Object>();
		args.add(end);
		args.add(begin);
		args.add(isImpExpGoods);

		StringBuilder hsql = new StringBuilder(
				"select td.transferFactoryBill.scmCoc.name,"
						+ "td.commName,td.commSpec,td.unit.name")
				.append(isGroup ? ",sum(td.transFactAmount)"
						: ",td.transFactAmount"
								+ ",td.transferFactoryBill.beginAvailability")
				.append(",td.transferFactoryBill,td.seqNum")
				.append(" from TransferFactoryCommodityInfo as td")
				.append(" where td.transferFactoryBill.beginAvailability <= ?")
				.append(" and td.transferFactoryBill.beginAvailability >= ?")
				.append(" and td.transferFactoryBill.isImpExpGoods = ?");

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append(" and td.transferFactoryBill.scmCoc.code = ?");
			args.add(customerCode);
		}
		if (name != null && !"".equals(name)) {
			hsql.append(" and td.commName = ?");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append(" and td.commSpec = ?");
			args.add(spec);
		}

		if (isGroup) {
			hsql.append(" group by td.transferFactoryBill.scmCoc.name,td.commName,"
					+ "td.commSpec,td.unit.name,td.transferFactoryBill.wareSet.name"
					+ ",td.transferFactoryBill,td.seqNum");
			hsql.append(" order by td.transferFactoryBill.scmCoc.name,td.commName,"
					+ "td.commSpec,td.unit.name,td.transferFactoryBill.wareSet.name"
					+ ",td.transferFactoryBill,td.seqNum");
		}

		return find(hsql.toString(), args.toArray());
	}

	/**
	 * 统计转厂数,查询报关单据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countZCS(String customerCode, String name,
			String spec, Date begin, Date end, Boolean isGroup,
			Boolean isImpExpGoods) {
		List<Object> args = new ArrayList<Object>();
		args.add(end);
		args.add(begin);
		args.add(isImpExpGoods ? ImpExpType.TRANSFER_FACTORY_IMPORT
				: ImpExpType.TRANSFER_FACTORY_EXPORT);

		StringBuilder hsql = new StringBuilder(
				"select td.baseCustomsDeclaration.customer.name,"
						+ "td.commName,td.commSpec,td.unit.name")
				.append(isGroup ? ",sum(td.commAmount)" : ",td.commAmount"
						+ ",td.baseCustomsDeclaration.declarationDate")
				.append(" from BcsCustomsDeclarationCommInfo as td")
				.append(" where td.baseCustomsDeclaration.declarationDate <= ?")
				.append(" and td.baseCustomsDeclaration.declarationDate >= ?")
				.append(" and td.baseCustomsDeclaration.impExpType = ?");

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append(" and td.baseCustomsDeclaration.customer.code = ?");
			args.add(customerCode);
		}
		if (name != null && !"".equals(name)) {
			hsql.append(" and td.commName = ?");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append(" and td.commSpec = ?");
			args.add(spec);
		}

		if (isGroup) {
			hsql.append(" group by td.baseCustomsDeclaration.customer.name,td.commName,td.commSpec,td.unit.name");
			hsql.append(" order by td.baseCustomsDeclaration.customer.name,td.commName,td.commSpec,td.unit.name");
		}

		return find(hsql.toString(), args.toArray());
	}

	/**
	 * 统计转厂数,查询报关单据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countZCSForCustom(ConditionBalance condition,
			Date begin, Date end) {
		// customerCode 客户编号
		// name 商品名称
		// spec 商品规格
		String customerCode = condition.getCustomerCode();
		String name = condition.getName();
		String spec = condition.getSpec();
		String customerName = condition.getCustomerName();

		// 拼装查询分组字段
		StringBuilder groupSb = new StringBuilder();
		for (int i = 0; i < condition.getGroupCondition().size(); i++) {
			if (i == 0) {
				groupSb.append(condition.getGroupCondition().get(i));
			} else {
				groupSb.append(", " + condition.getGroupCondition().get(i));
			}
		}

		// 查询语句
		StringBuilder hsql = new StringBuilder("select ");

		// 查询参数
		List<Object> args = new ArrayList<Object>();

		/*
		 * 组装hql查询语句 和 参数
		 */

		// 追加查询分组字段
		hsql.append(groupSb
				.toString()
				.replace("b.billMaster.scmCoc.name",
						"td.baseCustomsDeclaration.customer.name")
				.replace("b.billMaster.scmCoc.code",
						"td.baseCustomsDeclaration.customer.code")
				.replace("b.hsName", "td.commName")
				.replace("b.hsSpec", "td.commSpec")
				.replace("b.hsUnit.name", "td.unit.name"));

		hsql.append(
				",td.commAmount,td.baseCustomsDeclaration.declarationDate "
						+ ",td.baseCustomsDeclaration.customer.name ")
				.append(",td.baseCustomsDeclaration.customer.linkTel, td.baseCustomsDeclaration.customer.fax,td.baseCustomsDeclaration.customer.code "
						+ ",td.baseCustomsDeclaration.customer.linkMan ")
				.append("from BcsCustomsDeclarationCommInfo as td ")
				.append("where td.baseCustomsDeclaration.declarationDate <= ? ")
				.append("and td.baseCustomsDeclaration.declarationDate >= ? ")
				.append("and td.baseCustomsDeclaration.impExpType = ? ");
		args.add(end);
		args.add(begin);
		args.add(condition.isM() ? ImpExpType.TRANSFER_FACTORY_IMPORT
				: ImpExpType.TRANSFER_FACTORY_EXPORT);

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append(" and td.baseCustomsDeclaration.customer.code = ?");
			args.add(customerCode);
		}
		if (customerName != null && !"".equals(customerName)) {
			hsql.append(" and td.baseCustomsDeclaration.customer.name = ?");
			args.add(customerName);
		}
		if (name != null && !"".equals(name)) {
			hsql.append(" and td.commName = ?");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append(" and td.commSpec = ?");
			args.add(spec);
		}

		System.out.println("HSQLBG" + hsql);
		return find(hsql.toString(), args.toArray());
	}

	/**
	 * 统计转厂数,查询报关单据 -重量
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countZCSForCustomWeight(ConditionBalance condition,
			Date begin, Date end) {
		// customerCode 客户编号
		// name 商品名称
		// spec 商品规格
		String customerCode = condition.getCustomerCode();
		String name = condition.getName();
		String spec = condition.getSpec();
		// 查询语句
		StringBuilder hsql = new StringBuilder("select ");

		// 查询参数
		List<Object> args = new ArrayList<Object>();

		/*
		 * 组装hql查询语句 和 参数
		 */
		// 客户 海关商品名称 海关商品规格型号 单位 料号 报关法定单位 报关净重 商品编码 报关净重 生效日期 联系电话 传真
		hsql.append(
				" td.baseCustomsDeclaration.customer.name,td.commName,td.commSpec,td.unit.name ")
				.append(",td.ptNo,td.legalUnit.name,td.netWeight,td.complex.code,td.netWeight")
				.append(",td.baseCustomsDeclaration.declarationDate ")
				.append(",td.baseCustomsDeclaration.customer.linkTel, td.baseCustomsDeclaration.customer.fax,td.baseCustomsDeclaration.customer.code,td.baseCustomsDeclaration.customer.linkMan ")
				.append("from BcsCustomsDeclarationCommInfo as td ")
				.append("where td.baseCustomsDeclaration.declarationDate <= ? ")
				.append("and td.baseCustomsDeclaration.declarationDate >= ? ")
				.append("and td.baseCustomsDeclaration.impExpType = ? ");
		args.add(end);
		args.add(begin);
		args.add(condition.isM() ? ImpExpType.TRANSFER_FACTORY_IMPORT
				: ImpExpType.TRANSFER_FACTORY_EXPORT);

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append(" and td.baseCustomsDeclaration.customer.code = ?");
			args.add(customerCode);
		}
		if (name != null && !"".equals(name)) {
			hsql.append(" and td.commName = ?");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append(" and td.commSpec = ?");
			args.add(spec);
		}
		System.out.println("hsql&&&" + hsql);
		return find(hsql.toString(), args.toArray());

	}

	/**
	 * 统计关封数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> countDFS(String customerCode, String name,
			String spec, Date begin, Date end, Boolean isGroup,
			Boolean isImpExpGoods) {
		List<Object> args = new ArrayList<Object>();
		args.add(end);
		args.add(begin);
		args.add(end);
		args.add(begin);
		args.add(isImpExpGoods);

		StringBuilder hsql = new StringBuilder(
				"select td.customsEnvelopBill.scmCoc.name,"
						+ "td.ptName,td.ptSpec,td.unit.name")
				.append(isGroup ? ",sum(td.ownerQuantity)"
						: ",td.ownerQuantity"
								+ ",td.customsEnvelopBill.beginAvailability")
				.append(" from CustomsEnvelopCommodityInfo as td")
				.append(" where ((td.customsEnvelopBill.beginAvailability <= ?")
				.append(" and td.customsEnvelopBill.beginAvailability >= ?)")
				.append(" or (td.customsEnvelopBill.endAvailability <= ?")
				.append(" and td.customsEnvelopBill.endAvailability >= ?))")
				.append(" and td.customsEnvelopBill.isImpExpGoods = ?");

		if (customerCode != null && !"".equals(customerCode)) {
			hsql.append(" and td.customsEnvelopBill.scmCoc.code = ?");
			args.add(customerCode);
		}
		if (name != null && !"".equals(name)) {
			hsql.append(" and td.ptName = ?");
			args.add(name);
		}
		if (spec != null && !"".equals(spec)) {
			hsql.append(" and td.ptSpec = ?");
			args.add(spec);
		}

		if (isGroup) {
			hsql.append(" group by td.customsEnvelopBill.scmCoc.name,td.ptName,td.ptSpec,td.unit.name");
			hsql.append(" order by td.customsEnvelopBill.scmCoc.name,td.ptName,td.ptSpec,td.unit.name");
		}

		return find(hsql.toString(), args.toArray());
	}

	@SuppressWarnings("deprecation")
	private Map<String, CarryBalance> countByMonthForCustom(List<Object[]> list) {
		Map<String, CarryBalance> map = new HashMap<String, CarryBalance>();
		CarryBalance cb = null;
		String key = null;
		for (Object[] os : list) {

			key = (os[0] == null ? "" : (String) os[0]) + "/"
					+ (os[1] == null ? "" : (String) os[1]) + "/"
					+ (os[2] == null ? "" : (String) os[2]);
			cb = (CarryBalance) map.get(key);
			Double num = (Double) (os[3] == null ? 0.0 : os[3]);
			if (cb == null) {
				cb = new CarryBalance();
				cb.init();
				// 客户名称
				cb.setCustomerName((String) os[0]);
				// 商品名称
				cb.setName((String) os[1]);
				// 商品型号
				cb.setSpec((String) os[2]);
				map.put(key, cb);
			}
			int month = ((Date) (os[4])).getMonth();
			switch (month) {
			case 0:
				cb.setAmountMonth1(cb.getAmountMonth1() + num);
				break;
			case 1:
				cb.setAmountMonth2(cb.getAmountMonth2() + num);
				break;
			case 2:
				cb.setAmountMonth3(cb.getAmountMonth3() + num);
				break;
			case 3:
				cb.setAmountMonth4(cb.getAmountMonth4() + num);
				break;
			case 4:
				cb.setAmountMonth5(cb.getAmountMonth5() + num);
				break;
			case 5:
				cb.setAmountMonth6(cb.getAmountMonth6() + num);
				break;
			case 6:
				cb.setAmountMonth7(cb.getAmountMonth7() + num);
				break;
			case 7:
				cb.setAmountMonth8(cb.getAmountMonth8() + num);
				break;
			case 8:
				cb.setAmountMonth9(cb.getAmountMonth9() + num);
				break;
			case 9:
				cb.setAmountMonth10(cb.getAmountMonth10() + num);
				break;
			case 10:
				cb.setAmountMonth11(cb.getAmountMonth11() + num);
				break;
			case 11:
				cb.setAmountMonth12(cb.getAmountMonth12() + num);
				break;
			default:
				break;
			}

		}
		return map;
	}

	@SuppressWarnings("deprecation")
	private Map<String, CarryBalance> countByMonth(List<Object[]> list) {
		Map<String, CarryBalance> map = new HashMap<String, CarryBalance>();
		CarryBalance cb = null;
		String key = null;
		for (Object[] os : list) {

			key = (os[0] == null ? "" : (String) os[0]) + "/"
					+ (os[1] == null ? "" : (String) os[1]) + "/"
					+ (os[2] == null ? "" : (String) os[2]) + "/"
					+ (os[3] == null ? "" : (String) os[3]);
			cb = (CarryBalance) map.get(key);
			Double num = (Double) (os[4] == null ? 0.0 : os[4]);
			if (cb == null) {
				cb = new CarryBalance();
				cb.init();

				// 客户名称
				cb.setCustomerName((String) os[0]);
				// 商品名称
				cb.setName((String) os[1]);
				// 商品型号
				cb.setSpec((String) os[2]);
				// 商品单位
				cb.setUnitName((String) os[3]);
				if (os.length >= 11) {
					cb.setDefine1(os[6].toString());
					cb.setDefine2(os[7].toString());
					cb.setDefine3(os[8].toString());
					cb.setDefine4(os[9].toString());
					cb.setDefine5(os[10].toString());
				}
				map.put(key, cb);
			}
			int month = ((Date) (os[5])).getMonth();
			switch (month) {
			case 0:
				cb.setAmountMonth1(cb.getAmountMonth1() + num);
				break;
			case 1:
				cb.setAmountMonth2(cb.getAmountMonth2() + num);
				break;
			case 2:
				cb.setAmountMonth3(cb.getAmountMonth3() + num);
				break;
			case 3:
				cb.setAmountMonth4(cb.getAmountMonth4() + num);
				break;
			case 4:
				cb.setAmountMonth5(cb.getAmountMonth5() + num);
				break;
			case 5:
				cb.setAmountMonth6(cb.getAmountMonth6() + num);
				break;
			case 6:
				cb.setAmountMonth7(cb.getAmountMonth7() + num);
				break;
			case 7:
				cb.setAmountMonth8(cb.getAmountMonth8() + num);
				break;
			case 8:
				cb.setAmountMonth9(cb.getAmountMonth9() + num);
				break;
			case 9:
				cb.setAmountMonth10(cb.getAmountMonth10() + num);
				break;
			case 10:
				cb.setAmountMonth11(cb.getAmountMonth11() + num);
				break;
			case 11:
				cb.setAmountMonth12(cb.getAmountMonth12() + num);
				break;
			default:
				break;
			}

		}
		return map;
	}

	//
	private CarryBalance caleDiff(CarryBalance diff, CarryBalance shs,
			CarryBalance zcs, CarryBalance gfs, int month) {
		diff.setAmountMonth1(shs.getAmountFirst() + shs.getAmountMonth1()
				- zcs.getAmountMonth1());
		diff.setAmountMonth2(diff.getAmountMonth1() + shs.getAmountMonth2()
				- zcs.getAmountMonth2());
		diff.setAmountMonth3(diff.getAmountMonth2() + shs.getAmountMonth3()
				- zcs.getAmountMonth3());
		diff.setAmountMonth4(diff.getAmountMonth3() + shs.getAmountMonth4()
				- zcs.getAmountMonth4());
		diff.setAmountMonth5(diff.getAmountMonth4() + shs.getAmountMonth5()
				- zcs.getAmountMonth5());
		diff.setAmountMonth6(diff.getAmountMonth5() + shs.getAmountMonth6()
				- zcs.getAmountMonth6());
		diff.setAmountMonth7(diff.getAmountMonth6() + shs.getAmountMonth7()
				- zcs.getAmountMonth7());
		diff.setAmountMonth8(diff.getAmountMonth7() + shs.getAmountMonth8()
				- zcs.getAmountMonth8());
		diff.setAmountMonth9(diff.getAmountMonth8() + shs.getAmountMonth9()
				- zcs.getAmountMonth9());
		diff.setAmountMonth10(diff.getAmountMonth9() + shs.getAmountMonth10()
				- zcs.getAmountMonth10());
		diff.setAmountMonth11(diff.getAmountMonth10() + shs.getAmountMonth11()
				- zcs.getAmountMonth11());
		diff.setAmountMonth12(diff.getAmountMonth11() + shs.getAmountMonth12()
				- zcs.getAmountMonth12());
		diff.setTotal(diff.getAmountMonth12());
		switch (month) {
		case 1:
			shs.setAmountMonth2(0.0);
			zcs.setAmountMonth2(0.0);
			gfs.setAmountMonth2(0.0);
			diff.setAmountMonth2(0.0);
			break;
		case 2:
			shs.setAmountMonth3(0.0);
			zcs.setAmountMonth3(0.0);
			gfs.setAmountMonth3(0.0);
			diff.setAmountMonth3(0.0);
			break;
		case 3:
			shs.setAmountMonth4(0.0);
			zcs.setAmountMonth4(0.0);
			gfs.setAmountMonth4(0.0);
			diff.setAmountMonth4(0.0);
			break;
		case 4:
			shs.setAmountMonth5(0.0);
			zcs.setAmountMonth5(0.0);
			gfs.setAmountMonth5(0.0);
			diff.setAmountMonth5(0.0);
			break;
		case 5:
			shs.setAmountMonth6(0.0);
			zcs.setAmountMonth6(0.0);
			gfs.setAmountMonth6(0.0);
			diff.setAmountMonth6(0.0);
			break;
		case 6:
			shs.setAmountMonth7(0.0);
			zcs.setAmountMonth7(0.0);
			gfs.setAmountMonth7(0.0);
			diff.setAmountMonth7(0.0);
			break;
		case 7:
			shs.setAmountMonth8(0.0);
			zcs.setAmountMonth8(0.0);
			gfs.setAmountMonth8(0.0);
			diff.setAmountMonth8(0.0);
			break;
		case 8:
			shs.setAmountMonth9(0.0);
			zcs.setAmountMonth9(0.0);
			gfs.setAmountMonth9(0.0);
			diff.setAmountMonth9(0.0);
			break;
		case 9:
			shs.setAmountMonth10(0.0);
			zcs.setAmountMonth10(0.0);
			gfs.setAmountMonth10(0.0);
			diff.setAmountMonth10(0.0);
			break;
		case 10:
			shs.setAmountMonth11(0.0);
			zcs.setAmountMonth11(0.0);
			gfs.setAmountMonth11(0.0);
			diff.setAmountMonth11(0.0);
			break;
		case 11:
			shs.setAmountMonth12(0.0);
			zcs.setAmountMonth12(0.0);
			gfs.setAmountMonth12(0.0);
			diff.setAmountMonth12(0.0);
			break;
		}

		switch (month) {
		case 12:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth12());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth12());
			break;
		case 11:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth11());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth11());
			break;
		case 10:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth10());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth10());
			break;
		case 9:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth9());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth9());
			break;
		case 8:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth8());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth8());
			break;
		case 7:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth7());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth7());
			break;
		case 6:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth6());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth6());
			break;
		case 5:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth5());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth5());
			break;
		case 4:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth4());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth4());
			break;
		case 3:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth3());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth3());
			break;
		case 2:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth2());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth2());
			break;
		case 1:
			shs.setYearTotal(shs.getYearTotal() + shs.getAmountMonth1());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth1());
			break;
		}

		return diff;
	}

	/**
	 * 期初数=已收货未结转期初单/当跨年时=上一年差额数, 1月收货数=1月份结转进口单据折算报关数量hsAmount-1月份结转退货单折算报关数量,
	 * 1月转厂数= 1月份所有手册结转报关单数量Amount, 1月份差额 = 期初数 + 1月份收送货数 - 1月份退货数 - 1月份转厂数,,
	 * 2月份差额 = 1月份差额 + 2月份收送货数 - 2月份退货数 - 2月份转厂数。 3、4、5…月份计算依次类推
	 * 差额总值=所选查询日期所在月份的差额数量； 年度合计（收送货数量=1、2、3、4、5、6、7、8、9、10、11、12月份收送货数量汇总）。
	 * 如查询时间为9月那么所选在的月份之内进行sum收送货数量，
	 * 年度合计（结转数量=1、2、3、4、5、6、7、8、9、10、11、12月份结转数量汇总） 年度合计（差额数量=在此不进行汇总）
	 * 
	 * @param diff
	 *            差额
	 * @param ssh
	 *            收货数
	 * @param zcs
	 *            转厂数
	 * @param thd
	 *            退货数
	 * @param month
	 * @return
	 */
	private CarryBalance caleDiffCustom(CarryBalance diff, CarryBalance ssh,
			CarryBalance zcs, CarryBalance thd, int month) {
		// 计算 每月
		// 1月份差额 = 期初数 + 1月份收送货数 - 1月份退货数 - 1月份转厂数,
		// 2月份差额 = 1月份差额 + 2月份收送货数 - 2月份退货数 - 2月份转厂数。
		diff.setAmountMonth1(ssh.getAmountFirst() + ssh.getAmountMonth1()
				- thd.getAmountMonth1() - zcs.getAmountMonth1());
		diff.setAmountMonth2(diff.getAmountMonth1() + ssh.getAmountMonth2()
				- thd.getAmountMonth2() - zcs.getAmountMonth2());
		diff.setAmountMonth3(diff.getAmountMonth2() + ssh.getAmountMonth3()
				- thd.getAmountMonth3() - zcs.getAmountMonth3());
		diff.setAmountMonth4(diff.getAmountMonth3() + ssh.getAmountMonth4()
				- thd.getAmountMonth4() - zcs.getAmountMonth4());
		diff.setAmountMonth5(diff.getAmountMonth4() + ssh.getAmountMonth5()
				- thd.getAmountMonth5() - zcs.getAmountMonth5());
		diff.setAmountMonth6(diff.getAmountMonth5() + ssh.getAmountMonth6()
				- thd.getAmountMonth6() - zcs.getAmountMonth6());
		diff.setAmountMonth7(diff.getAmountMonth6() + ssh.getAmountMonth7()
				- thd.getAmountMonth7() - zcs.getAmountMonth7());
		diff.setAmountMonth8(diff.getAmountMonth7() + ssh.getAmountMonth8()
				- thd.getAmountMonth8() - zcs.getAmountMonth8());
		diff.setAmountMonth9(diff.getAmountMonth8() + ssh.getAmountMonth9()
				- thd.getAmountMonth9() - zcs.getAmountMonth9());
		diff.setAmountMonth10(diff.getAmountMonth9() + ssh.getAmountMonth10()
				- thd.getAmountMonth10() - zcs.getAmountMonth10());
		diff.setAmountMonth11(diff.getAmountMonth10() + ssh.getAmountMonth11()
				- thd.getAmountMonth11() - zcs.getAmountMonth11());
		diff.setAmountMonth12(diff.getAmountMonth11() + ssh.getAmountMonth12()
				- thd.getAmountMonth12() - zcs.getAmountMonth12());
		diff.setTotal(diff.getAmountMonth12());
		switch (month) {
		case 1:
			ssh.setAmountMonth2(0.0);
			zcs.setAmountMonth2(0.0);
			thd.setAmountMonth2(0.0);
			diff.setAmountMonth2(0.0);
			break;
		case 2:
			ssh.setAmountMonth3(0.0);
			zcs.setAmountMonth3(0.0);
			thd.setAmountMonth3(0.0);
			diff.setAmountMonth3(0.0);
			break;
		case 3:
			ssh.setAmountMonth4(0.0);
			zcs.setAmountMonth4(0.0);
			thd.setAmountMonth4(0.0);
			diff.setAmountMonth4(0.0);
			break;
		case 4:
			ssh.setAmountMonth5(0.0);
			zcs.setAmountMonth5(0.0);
			thd.setAmountMonth5(0.0);
			diff.setAmountMonth5(0.0);
			break;
		case 5:
			ssh.setAmountMonth6(0.0);
			zcs.setAmountMonth6(0.0);
			thd.setAmountMonth6(0.0);
			diff.setAmountMonth6(0.0);
			break;
		case 6:
			ssh.setAmountMonth7(0.0);
			zcs.setAmountMonth7(0.0);
			thd.setAmountMonth7(0.0);
			diff.setAmountMonth7(0.0);
			break;
		case 7:
			ssh.setAmountMonth8(0.0);
			zcs.setAmountMonth8(0.0);
			thd.setAmountMonth8(0.0);
			diff.setAmountMonth8(0.0);
			break;
		case 8:
			ssh.setAmountMonth9(0.0);
			zcs.setAmountMonth9(0.0);
			thd.setAmountMonth9(0.0);
			diff.setAmountMonth9(0.0);
			break;
		case 9:
			ssh.setAmountMonth10(0.0);
			zcs.setAmountMonth10(0.0);
			thd.setAmountMonth10(0.0);
			diff.setAmountMonth10(0.0);
			break;
		case 10:
			ssh.setAmountMonth11(0.0);
			zcs.setAmountMonth11(0.0);
			thd.setAmountMonth11(0.0);
			diff.setAmountMonth11(0.0);
			break;
		case 11:
			ssh.setAmountMonth12(0.0);
			zcs.setAmountMonth12(0.0);
			thd.setAmountMonth12(0.0);
			diff.setAmountMonth12(0.0);
			break;
		}

		switch (month) {
		case 12:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth12());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth12());
			break;
		case 11:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth11());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth11());
			break;
		case 10:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth10());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth10());
			break;
		case 9:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth9());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth9());
			break;
		case 8:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth8());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth8());
			break;
		case 7:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth7());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth7());
			break;
		case 6:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth6());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth6());
			break;
		case 5:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth5());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth5());
			break;
		case 4:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth4());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth4());
			break;
		case 3:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth3());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth3());
			break;
		case 2:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth2());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth2());
			break;
		case 1:
			ssh.setYearTotal(ssh.getYearTotal() + ssh.getAmountMonth1());
			zcs.setYearTotal(zcs.getYearTotal() + zcs.getAmountMonth1());
			break;
		}

		return diff;
	}

	/**
	 * 查询收／送货单据
	 * 
	 * @param conditionBalance
	 * @return
	 */
	public List<BillDetail> getBillDetail(ConditionBalance conditionBalance,
			Date start, Date end) {
		String tableName = "";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(true);
		paramters.add(start);
		paramters.add(end);
		if (conditionBalance.isM()) {// 1.收货，料件
			tableName = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.MATERIEL);
			paramters.add("1015");// 已收货未结转期初单
			paramters.add("1004");// 结转进口
			paramters.add("1106");// 结转料件退货单
			paramters.add("1016");// 已结转未收货期初单
		} else {// 2.送货，成品
			tableName = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.FINISHED_PRODUCT);
			paramters.add("2011");// 已交货未结转期初单
			paramters.add("2009");// 结转成品退货单
			paramters.add("2102");// 结转出口
			paramters.add("2012");// 已结转未交货期初单
		}
		String hsql = "select a from "
				+ tableName
				+ " as a where a.company.id=? and a.billMaster.isValid=?"
				+ " and a.billMaster.validDate>=? and a.billMaster.validDate<=?  and a.billMaster.billType.code in(?,?,?,?)";
		if (conditionBalance.getCustomerCode() != null
				&& !"".equals(conditionBalance.getCustomerCode())) {
			hsql += " and a.billMaster.scmCoc.code=?";
			paramters.add(conditionBalance.getCustomerCode());
		}
		System.out.println("hsql:" + hsql);
		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 根据料号，名称，规格查询对应关系
	 * 
	 * @param ptNo
	 * @param name
	 * @param spec
	 * @return
	 */
	public FactoryAndFactualCustomsRalation getBillDetailFactoryAndFactualCustomsRalation(
			String ptNo, String name, String spec) {
		String hsql = " select a from FactoryAndFactualCustomsRalation a "
				+ " where a.company.id=? and  a.materiel.ptNo=? and a.materiel.factoryName=? and a.materiel.factorySpec=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(ptNo);
		paramters.add(name);
		paramters.add(spec);
		List list = super.find(hsql, paramters.toArray());
		if (list.size() == 0) {
			return null;
		} else {
			return (FactoryAndFactualCustomsRalation) list.get(0);
		}
	}

	/**
	 * 获取三种监管方式的结转报关信息
	 * 
	 * @param conditionBalance
	 * @return
	 */
	public List<BaseCustomsDeclarationCommInfo> findCustomsDeclarationCommInfo(
			ConditionBalance conditionBalance, Date start, Date end) {
		List<BaseCustomsDeclarationCommInfo> rs = null;
		String tableName = " BcsCustomsDeclarationCommInfo ";// 电子化手册
		String hsql1 = "select a from ";
		String hsql2 = " as a where a.company.id=? and a.baseCustomsDeclaration.impExpType=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.declarationDate>=? and a.baseCustomsDeclaration.declarationDate<=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());// ?1
		if (conditionBalance.isM()) {
			paramters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 料件转厂 ?2
			paramters.add(ImpExpFlag.IMPORT);// ?3

		} else {
			paramters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口?2
			paramters.add(ImpExpFlag.EXPORT);// ?3

		}
		paramters.add(true);// ?4
		paramters.add(start);// ?5
		paramters.add(end);// ?6
		// 客户名称
		if (!"".equals(conditionBalance.getCustomerCode())) {
			hsql2 += " and a.baseCustomsDeclaration.customer.code=? ";
			paramters.add(conditionBalance.getCustomerCode());
		}
		// 商品名称
		if (!"".equals(conditionBalance.getName())) {
			hsql2 += " and a.commName=? ";
			paramters.add(conditionBalance.getName());
		}
		// 商品规格
		if (!"".equals(conditionBalance.getSpec())) {
			hsql2 += " and a.commSpec=? ";
			paramters.add(conditionBalance.getSpec());
		}
		rs = super.find(hsql1 + tableName + hsql2, paramters.toArray());
		System.out.println("hsql = " + hsql1 + tableName + hsql2);
		tableName = " DzscCustomsDeclarationCommInfo "; // 电子手册
		rs.addAll(super.find(hsql1 + tableName + hsql2, paramters.toArray()));
		System.out.println("hsql = " + hsql1 + tableName + hsql2);
		tableName = " CustomsDeclarationCommInfo "; // 电子帐册
		rs.addAll(super.find(hsql1 + tableName + hsql2, paramters.toArray()));
		System.out.println("hsql = " + hsql1 + tableName + hsql2);
		System.out.println("rs:" + rs.size());
		return rs;
	}

	/**
	 * 获取深加工结转明细
	 * 
	 * @param conditionBalance
	 * @param start
	 * @param end
	 * @return
	 * @author chenSir
	 */
	public List<FptAppItem> findFptAppItem(ConditionBalance conditionBalance,
			Date start, Date end) {
		String hsql = " select a from FptAppItem as a where a.company.id=? and a.fptAppHead.inOutFlag=? "
				+ " and a.fptAppHead.declareState=? ";
		String hsql2;
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());// ?1
		if (conditionBalance.isM()) {
			paramters.add("1");// ?2
			hsql2 = " and a.fptAppHead.inDate>=? and a.fptAppHead.inDate<=? ";
		} else {
			paramters.add("0");// ?2
			hsql2 = " and a.fptAppHead.outDate>=? and a.fptAppHead.outDate<=? ";
		}
		hsql += hsql2;
		paramters.add(DeclareState.PROCESS_EXE);// ?3 正在执行
		paramters.add(start);// ?4
		paramters.add(end);// ?5
		// 客户/供应商
		if (!"".equals(conditionBalance.getCustomerCode())) {
			hsql += " and a.fptAppHead.scmCoc.code=? ";
			paramters.add(conditionBalance.getCustomerCode());
		}
		// 报关名称
		if (!"".equals(conditionBalance.getName())) {
			hsql += " and a.name=? ";
			paramters.add(conditionBalance.getName());
		}
		// 报关规格
		if (!"".equals(conditionBalance.getSpec())) {
			hsql += " and a.spec=? ";
			paramters.add(conditionBalance.getSpec());
		}
		System.out.println(hsql);
		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 获取关封明细
	 * 
	 * @param conditionBalance
	 * @param start
	 * @param end
	 * @return
	 */
	public List<CustomsEnvelopCommodityInfo> findCustomsEnvelopCommodityInfo(
			ConditionBalance conditionBalance, Date start, Date end) {
		String hsql = "select a from CustomsEnvelopCommodityInfo as a where a.company.id=? and a.customsEnvelopBill.isImpExpGoods=?"
				+ " and a.customsEnvelopBill.isAvailability=? and a.customsEnvelopBill.beginAvailability>=? and a.customsEnvelopBill.beginAvailability<=? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(CommonUtils.getCompany().getId());
		if (conditionBalance.isM()) {
			paramters.add(true);
		} else {
			paramters.add(false);
		}
		paramters.add(true);// 正在执行
		paramters.add(start);
		paramters.add(end);
		// 客户\供应商
		if (!"".equals(conditionBalance.getCustomerCode())) {
			hsql += " and a.customsEnvelopBill.scmCoc.code=? ";
			paramters.add(conditionBalance.getCustomerCode());
		}
		// 商品名称
		if (!"".equals(conditionBalance.getName())) {
			hsql += " and a.ptName=? ";
			paramters.add(conditionBalance.getName());
		}
		// 商品规格
		if (!"".equals(conditionBalance.getSpec())) {
			hsql += " and a.ptSpec=? ";
			paramters.add(conditionBalance.getSpec());
		}
		System.out.println("sql:" + hsql);
		return super.find(hsql, paramters.toArray());
	}

	/**
	 * 查找所有的合同来自正在执行的电子化手册bom
	 * 
	 * @return List 是Contract型，合同备案表头 黄创亮
	 */
	public List findContractBomByConditionsBCS(List conditionsMaterial,
			List conditionsContract, ConditionBalance conditionBalance) {
		String sql = "select a from ContractBom a where a.contractExg.contract.company= ? and ( a.contractExg.contract.isCancel=? ) and a.contractExg.contract.declareState=?  ";
		Condition condition = null;
		Object[] parm = new Object[] { CommonUtils.getCompany(),
				new Boolean(false), DeclareState.PROCESS_EXE };
		if (conditionsContract != null && conditionsContract.size() != 0) {
			for (int i = 0; i < conditionsContract.size(); i++) {
				condition = (Condition) conditionsContract.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a.contractExg.contract." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null)
					sql += "'" + condition.getValue() + "' ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		System.out.println("conditionsMaterial.size()"
				+ conditionsMaterial.size());
		if (conditionsMaterial != null && conditionsMaterial.size() != 0) {
			for (int i = 0; i < conditionsMaterial.size(); i++) {
				condition = (Condition) conditionsMaterial.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null)
					sql += "'" + condition.getValue() + "'";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (conditionBalance != null) {
			if (!conditionBalance.getName().equals(""))
				sql += " and a.contractExg.name='" + conditionBalance.getName()
						+ "'";
			if (!conditionBalance.getSpec().equals(""))
				sql += " and a.contractExg.spec='" + conditionBalance.getSpec()
						+ "'";
		}
		sql += " order by a.contractExg.contract.beginDate";
		System.out.println("+++++++++++++++" + sql);
		return this.find(sql, parm);
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param exgId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByExgId(String exgId) {
		return this.find("select a from ContractBom a left join a.contractExg"
				+ " where a.contractExg.id = ?  and a.company.id=? "
				+ "  order by a.createDate ", new Object[] { exgId,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找所有的合同来自正在执行的电子手册bom
	 * 
	 * @param conditionsMaterial料件条件
	 * @param conditionsContract合同条件
	 * @return 黄创亮
	 */
	public List<DzscEmsBomBill> findContractBomByConditionsZDSC(
			List<Condition> conditionsMaterial,
			List<Condition> conditionsContract) {
		String sql = "select a from DzscEmsBomBill a where a.company= ?  and a.dzscEmsExgBill.dzscEmsPorHead.declareState=? ";
		Condition condition = null;
		if (conditionsMaterial != null && conditionsMaterial.size() != 0) {
			for (int i = 0; i < conditionsMaterial.size(); i++) {
				condition = (Condition) conditionsMaterial.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null)
					sql += "'" + condition.getValue() + "' ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (conditionsContract != null && conditionsContract.size() != 0) {
			for (int i = 0; i < conditionsContract.size(); i++) {
				condition = (Condition) conditionsContract.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a.dzscEmsExgBill.dzscEmsPorHead."
							+ condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null)
					sql += "'" + condition.getValue() + "' ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}

		sql += "order by a.dzscEmsExgBill.dzscEmsPorHead.beginDate";
		System.out.println("sql=" + sql);
		return this.find(sql, new Object[] { CommonUtils.getCompany(),
				DzscState.EXECUTE });
	}

	public List<EmsHeadH2kBom> findContractBomByConditionsBCUS(
			ConditionBalance conditionBalance,
			List<Condition> conditionsMaterial,
			List<Condition> conditionsContract) {
		String sql = "select a from EmsHeadH2kBom a where a.company= ?  ";
		if (conditionBalance.getName() != null
				&& !conditionBalance.getName().equals(""))
			sql += " and a.emsHeadH2kVersion.emsHeadH2kExg.name='"
					+ conditionBalance.getName() + "' ";
		if (conditionBalance.getSpec() != null
				&& !conditionBalance.getSpec().equals(""))
			sql += " and a.emsHeadH2kVersion.emsHeadH2kExg.spec='"
					+ conditionBalance.getSpec() + "' ";
		Condition condition = null;
		if (conditionsMaterial != null && conditionsMaterial.size() != 0) {
			for (int i = 0; i < conditionsMaterial.size(); i++) {
				condition = (Condition) conditionsMaterial.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null)
					sql += "'" + condition.getValue() + "' ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		if (conditionsContract != null && conditionsContract.size() != 0) {
			for (int i = 0; i < conditionsContract.size(); i++) {
				condition = (Condition) conditionsContract.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k."
							+ condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null)
					sql += "'" + condition.getValue() + "' ";
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}
		sql += "order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum";
		System.out.println("sql=" + sql);
		return this.find(sql, new Object[] { CommonUtils.getCompany() });
	}

	/**
	 * 查询转厂报关单的商品信息(相应合同下的，三种手册通用)
	 * 
	 * @param isMaterial料件判断
	 *            ，true位料件，否则为成品
	 * @param lsContract
	 *            合同表头
	 * @param state
	 *            状态类型
	 * @param currentType
	 *            手册类型
	 * @return List 存放报关单商品的一些资料
	 * @author wss
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			List lsContract, int state, int currentType) {
		List<Object> parameters = new ArrayList<Object>();
		String tableName;
		switch (currentType) {
		case 0:
			tableName = " BcsCustomsDeclarationCommInfo ";// 电子化手册报关单商品信息
			break;
		case 1:
			tableName = " CustomsDeclarationCommInfo ";// 电子帐册报关单商品信息
			break;
		case 2:
			tableName = " DzscCustomsDeclarationCommInfo ";// 电子手册报关单商品信息
			break;
		default:
			tableName = " BcsCustomsDeclarationCommInfo ";// 默认为电子化手册报关单商品信息
			break;
		}
		String hsql = " select distinct a.commSerialNo,a.complex.code,a.commName,a.commSpec,a.unit.name "
				+ " from " + tableName + " as a ";

		if (isMaterial) {
			hsql += " where a.baseCustomsDeclaration.impExpType = ? ";
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);

		} else {
			hsql += " where a.baseCustomsDeclaration.impExpType = ? ";
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}

		hsql += " and a.baseCustomsDeclaration.company.id=? ";

		parameters.add(CommonUtils.getCompany().getId());

		// 电子化手册与 电子手册
		if (currentType == 0 || currentType == 2) {
			if (lsContract.size() == 1) {
				BaseContractHead contract = (BaseContractHead) lsContract
						.get(0);
				hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
				parameters.add(contract.getEmsNo());
			} else {
				for (int i = 0; i < lsContract.size(); i++) {
					BaseContractHead contract = (BaseContractHead) lsContract
							.get(i);
					if (i == 0) {
						hsql += " and (a.baseCustomsDeclaration.emsHeadH2k=? ";
					} else if (i > 0 && i < lsContract.size() - 1) {
						hsql += " or a.baseCustomsDeclaration.emsHeadH2k=? ";
					} else if (i == lsContract.size() - 1) {
						hsql += " or a.baseCustomsDeclaration.emsHeadH2k=? ) ";
					}
					parameters.add(contract.getEmsNo());
				}
			}
		}

		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(false);
		}
		hsql += " order by a.commSerialNo ";
		System.out.println("hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查转厂报关的客户
	 * 
	 * @param isMateriel料件判断
	 *            ，true位料件，否则为成品
	 * @param lsContract合同表头
	 * @param state状态类型
	 * @param currentType
	 *            手册类型
	 * @return List 是customer型，存放了已报关的客户
	 * @author wss
	 */
	public List findCustomsDeclarationCustomer(boolean isMateriel,
			List lsContract, int state, int currentType) {

		String tableContract;// 报关单表头
		switch (currentType) {
		case 0:// 电子化手册
			tableContract = " BcsCustomsDeclaration ";
			break;
		case 1:// 电子帐册
			tableContract = " CustomsDeclaration ";
			break;
		case 2:// 电子手册
			tableContract = " DzscCustomsDeclaration ";
			break;
		default:// 默认为电子化手册
			tableContract = " BcsCustomsDeclaration ";
			break;
		}

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from " + tableContract
				+ "  as a where  a.company.id=?  ";
		parameters.add(CommonUtils.getCompany().getId());

		// 如果是电子化手册，电子手册
		if (currentType == 0 || currentType == 2) {
			if (lsContract.size() == 1) {
				BaseContractHead contract = (BaseContractHead) lsContract
						.get(0);
				hsql += " and a.emsHeadH2k=? ";
				parameters.add(contract.getEmsNo());
			} else {
				for (int i = 0; i < lsContract.size(); i++) {
					BaseContractHead contract = (BaseContractHead) lsContract
							.get(i);
					if (i == 0) {
						hsql += " and (a.emsHeadH2k=? ";
					} else if (i > 0 && i < lsContract.size() - 1) {
						hsql += " or a.emsHeadH2k=? ";
					} else if (i == lsContract.size() - 1) {
						hsql += " or a.emsHeadH2k=? ) ";
					}
					parameters.add(contract.getEmsNo());
				}
			}
		}

		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.effective=?";
			parameters.add(false);
		}
		if (isMateriel) {
			hsql += " and a.impExpType = ?";
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);

		} else {
			hsql += " and a.impExpType =? ";
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 通过合同查询转成厂报关商品
	 * 
	 * @param isImport
	 *            判断是否进口类型，true为进口
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param emsNo
	 *            手册号
	 * @param state
	 *            状态类型
	 * @return List 是CustomsDeclarationCommInfo型，报关单物料资料
	 * @author wss
	 */
	public List findTransferCommInfoListContract(boolean isMaterial,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag, int currentType) {
		List<Object> parameters = new ArrayList<Object>();

		String tableCommInfo;// 报关单物料资料表名
		String tableImg;// 合同备案料件资料表名
		String tableExg;// 合同备案成品资料表名
		String contractPropertyName;// 合同属性名

		switch (currentType) {
		case 0:// 电子化手册
			tableCommInfo = " BcsCustomsDeclarationCommInfo ";
			tableImg = " ContractImg ";
			tableExg = " ContractExg ";
			contractPropertyName = "b.contract.";
			break;

		case 1:// 电子帐册
			tableCommInfo = " CustomsDeclarationCommInfo ";
			tableImg = " EmsHeadH2kImg ";
			tableExg = " EmsHeadH2kExg ";
			contractPropertyName = "b.emsHeadH2k.";
			break;

		case 2:// 电子手册
			tableCommInfo = " DzscCustomsDeclarationCommInfo ";
			tableImg = " DzscEmsImgBill ";
			tableExg = " DzscEmsExgBill ";
			contractPropertyName = "b.dzscEmsPorHead.";
			break;
		default:// 默认为电子化手册
			tableCommInfo = " BcsCustomsDeclarationCommInfo ";
			tableImg = " ContractImg ";
			tableExg = " ContractExg ";
			contractPropertyName = "b.contract.";
			break;
		}

		String hsql = "select a, b from " + tableCommInfo + " as a ";
		if (isMaterial) {
			hsql += " , " + tableImg + " as b "
					+ " where a.baseCustomsDeclaration.emsHeadH2k = "
					+ contractPropertyName + "emsNo "
					+ " and a.commSerialNo=b.seqNum";
		} else {
			hsql += " , " + tableExg + " as b "
					+ " where a.baseCustomsDeclaration.emsHeadH2k = "
					+ contractPropertyName + "emsNo "
					+ " and a.commSerialNo=b.seqNum ";
		}

		hsql += " and " + contractPropertyName + "declareState=? "
				+ " and a.baseCustomsDeclaration.company.id=? ";

		if (2 == currentType) {
			parameters.add(DzscState.EXECUTE);//
		} else {
			parameters.add(DeclareState.PROCESS_EXE);//
		}

		parameters.add(CommonUtils.getCompany().getId());

		// 电子化手册 电子手册
		if (currentType == 0 || currentType == 2) {
			int temp = 0;
			for (int i = 0; i < contracts.size(); i++) {
				String emsNo = ((BaseContractHead) contracts.get(i)).getEmsNo();
				if (emsNo != null && (!emsNo.trim().equals(""))) {
					temp++;
					if (temp == 1) {
						hsql += " and ( " + contractPropertyName + "emsNo=? ";
						parameters.add(emsNo);
					} else {
						hsql += " or " + contractPropertyName + "emsNo=? ";
						parameters.add(emsNo);
					}
				}
			}
			if (temp != 0) {
				hsql += " )";
			}
		}

		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (code != null && (!code.equals(""))) {
			hsql += " and a.complex.code=? ";
			parameters.add(code);
		}
		if (name != null && (!name.equals(""))) {
			hsql += " and a.commName=? ";
			parameters.add(name);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}

		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		} else {
			if (isMaterial) {
				hsql += " and a.baseCustomsDeclaration.impExpType=? ";
				parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			} else {
				hsql += " and a.baseCustomsDeclaration.impExpType=? ";
				parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			}
		}

		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(false);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate, "
				+ " a.baseCustomsDeclaration.customsDeclarationCode,a.commSerialNo ";
		System.out.println("hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	public List findMertailOrFinishedProductHs(String materiel, int type,
			String[] contracts) {
		String hsql = "";
		if (type == 0) {
			if (materiel.equals(MaterielType.MATERIEL)) {
				hsql = "select distinct a.name,a.spec,a.unit from ContractImg a ";
			} else if (materiel.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql = "select distinct a.name,a.spec,a.unit from ContractExg a ";
			}
			for (int i = 0; i < contracts.length; i++) {
				if (i == 0)
					hsql += "where a.contract.emsNo='" + contracts[i] + "' ";
				else
					hsql += "or a.contract.emsNo='" + contracts[i] + "' ";
			}
		} else if (type == 1) {
			if (materiel.equals(MaterielType.MATERIEL)) {
				hsql = "select distinct a.name,a.spec,a.unit from DzscEmsImgBill a ";
			} else if (materiel.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql = "select distinct a.name,a.spec,a.unit from DzscEmsExgBill a ";
			}
			for (int i = 0; i < contracts.length; i++) {
				if (i == 0)
					hsql += "where a.dzscEmsPorHead.emsNo='" + contracts[i]
							+ "' ";
				else
					hsql += "or a.dzscEmsPorHead.emsNo='" + contracts[i] + "' ";
			}
		} else {
			if (materiel.equals(MaterielType.MATERIEL)) {
				hsql = "select distinct a.name,a.spec,a.unit from EmsHeadH2kImg a ";
			} else if (materiel.equals(MaterielType.FINISHED_PRODUCT)) {
				hsql = "select distinct a.name,a.spec,a.unit from EmsHeadH2kExg a ";
			}
			for (int i = 0; i < contracts.length; i++) {
				if (i == 0)
					hsql += "where a.emsHeadH2k.emsNo='" + contracts[i] + "' ";
				else
					hsql += "or a.emsHeadH2k.emsNo='" + contracts[i] + "' ";
			}
		}
		System.out.println("hsql=" + hsql);

		return this.find(hsql);
	}

	public List findMertailOrFinishedProductHs(String materiel, String product) {
		String hsql = "select distinct a.statCusNameRelationHsn.cusSpec from FactoryAndFactualCustomsRalation as a "
				+ "  where a.company= ? and a.statCusNameRelationHsn.materielType = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(CommonUtils.getCompany());
		parameters.add(materiel);
		System.out.println("materielType==" + materiel);
		if (product != null && !product.equals("")) {
			hsql += " and a.statCusNameRelationHsn.cusName like ?  ";
			parameters.add(product + "%");
		}

		return this.find(hsql, parameters.toArray());
	}

	/**
	 * wss:抄改 查找合同料件 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByContractId(String parentId) {
		return this.find(
				"select a from ContractImg a where a.contract.id = ? and a.company.id=?"
						+ " order by a.seqNum ", new Object[] { parentId,
						CommonUtils.getCompany().getId() });
	}

	/**
	 * wss:抄改 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期 结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findFptAppItemCount(String emsNo, boolean isMaterial,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (isMaterial) {// 料件
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.inEmsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.IN);
		} else {// 成品
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.fptAppHead.emsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.OUT);
		}
		hsql += " and b.fptAppHead.declareState=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		hsql += " and b.inOutFlag=b.fptAppHead.inOutFlag ";
		hsql += " group by b.trNo,b.codeTs.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * wss:抄改
	 * 
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findCommodityInfoRemain(String emsNo, boolean isMaterial,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";

		hsql = "select a.seqNum,a.complex.code,sum(a.ownerQuantity)"
				+ " from CustomsEnvelopCommodityInfo a "
				+ "where a.company.id=? and a.emsNo=? "
				+ "and a.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(isMaterial);// 进货是出货
		hsql += "and a.customsEnvelopBill.isAvailability=? ";
		parameters.add(true);
		hsql += " group by a.seqNum,a.complex.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * wss:抄改
	 * 
	 * 抓取关封数量--转厂
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findCustomsEnvelopCommInfoCount(String emsNo,
			boolean isMaterial, Date beginDate, Date endDate) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select b.seqNum,b.complex.code,sum(b.ownerQuantity) "
				+ " from CustomsEnvelopCommodityInfo as b"
				+ " where b.customsEnvelopBill.company.id=? "
				+ " and b.emsNo=? "
				+ " and b.customsEnvelopBill.isImpExpGoods=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		parameters.add(isMaterial);

		hsql += " group by b.seqNum,b.complex.code ";
		List list = this.find(hsql, parameters.toArray());

		return list;
	}

	/**
	 * 
	 * wss：抄改 查询报关单中某项商品序号、商品编码对应的数量总量
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 存放了商品序号、商品编码和对应的数量总量
	 */
	public List findCommInfoTotalAmount(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo, sum(a.commAmount) "
				+ " from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * wss:抄改 查询bcs报关单的进口折算合同币制汇率金额
	 */
	public List findCommInfoTotalMoney(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate,
			int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.commSerialNo, sum(a.commTotalPrice* "
				+ " case when a.baseCustomsDeclaration.exchangeRate  is null then 1.0"
				+ " when a.baseCustomsDeclaration.exchangeRate=0.0 then 1.0 "
				+ " else a.baseCustomsDeclaration.exchangeRate end) "
				+ " from  BcsCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		hsql += " group by a.commSerialNo";
		// System.out.println("--hsql:" + hsql);
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * wss:抄改 查询合同备案料件资料中某项商品序号、未转报关单对应的数量总量
	 * 
	 * @param isLj
	 *            是否为料件
	 * @param emsNo
	 *            手册编号
	 * @return List 合同备案料件资料
	 */
	public List findNoTransCustomsNum(Boolean isLj, String emsNo) {
		return this.find("select a.seqNum,sum(a.num) from ImpExpGoodsBill a"
				+ " where a.contractNo = ? and a.isTcustoms = ?"
				+ "  and a.company.id = ? and a.isLj = ? group by  a.seqNum",
				new Object[] { emsNo, new Boolean(false),
						CommonUtils.getCompany().getId(), isLj });
	}

	/**
	 * wss:抄改 统计核销发票数量
	 * 
	 * @param emsNo
	 * @param seqNum
	 * @return
	 */

	public Double findCasInvoiceInfoNum(String emsNo, Integer seqNum) {
		Double sum = 0.0;
		List list = this
				.find("select SUM(a.canceledNum) from CasInvoiceInfo as a where a.company.id=? "
						+ " and a.casInvoice.emsNo=?  and a.cancelEmsSeqNum= ? "
						+ " and  a.canceledNum is not null ", new Object[] {
						CommonUtils.getCompany().getId(), emsNo, seqNum });
		if (list.size() > 0 && list.get(0) != null) {
			sum = Double.parseDouble(list.get(0).toString());
		}
		return sum;
	}

	/**
	 * wss:抄改 查找合同成品 来自 合同备案表头Id
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByParentId(String parentId) {
		String hsql = "select a from ContractExg a where a.contract.id = ?  and a.company.id=?"
				+ " order by a.seqNum ";
		return this.find(hsql, new Object[] { parentId,
				CommonUtils.getCompany().getId() });

	}

	/**
	 * 根据商品序号，查询合同BOM信息
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param exgSeqNum
	 *            商品序号
	 * @param declareState
	 *            合同状态
	 * @return List 是ContractBom型，合同备案BOM
	 */
	public List<ContractBom> findBomByExg(String emsNo, Integer exgSeqNum,
			String declareState) {
		if (declareState == null) {
			String hsql = "select a from ContractBom as a where a.contractExg.seqNum=? "
					+ " and a.contractExg.contract.emsNo=?"
					+ " and a.contractExg.contract.company.id=? ";
			return this.find(hsql, new Object[] { exgSeqNum, emsNo,
					CommonUtils.getCompany().getId() });
		} else {
			if (declareState != null && "5".equals(declareState)) {
				String hsql = "select a from ContractBom as a where a.contractExg.seqNum=? "
						+ " and a.contractExg.contract.emsNo=?"
						+ " and a.contractExg.contract.declareState=? "
						+ " and a.contractExg.contract.isCancel=? "
						+ " and a.contractExg.contract.company.id=? ";
				return this.find(hsql, new Object[] { exgSeqNum, emsNo,
						DeclareState.CHANGING_CANCEL, new Boolean(true),
						CommonUtils.getCompany().getId() });
			} else {
				String hsql = "select a from ContractBom as a where a.contractExg.seqNum=? "
						+ " and a.contractExg.contract.emsNo=?"
						+ " and a.contractExg.contract.declareState=? "
						+ " and a.contractExg.contract.isCancel=? "
						+ " and a.contractExg.contract.company.id=? ";
				return this.find(hsql, new Object[] { exgSeqNum, emsNo,
						DeclareState.PROCESS_EXE, new Boolean(false),
						CommonUtils.getCompany().getId() });
			}
		}
	}

	/**
	 * wss:抄改（电子帐册） 查询已报关的商品
	 * 
	 * @param isImport
	 *            是否是料件
	 * @return 生效的进出口报关商品
	 */
	public List findCustomsDeclarationCommInfo(boolean isImport) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commSerialNo,a.commName,a.commSpec from CustomsDeclarationCommInfo as a ";
		hsql += " left outer join a.complex ";
		hsql += " where ";
		if (isImport) {// 料件
			hsql += "  a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			// ---
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		} else {// 成品
			hsql += "  a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		hsql += " and a.commSerialNo is not null and a.baseCustomsDeclaration.company.id=? and a.baseCustomsDeclaration.effective = ? order by a.commSerialNo";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Boolean.valueOf(true));
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * wss:抄改 查询已报关的客户
	 * 
	 * @param isImport
	 *            是否是料件
	 * @return 所有报关的报关单中的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from CustomsDeclaration as a where  "
				+ " a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (isImport) {// 料件
			hsql += " and  a.impExpType in (?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		} else {// 成品
			hsql += " and  a.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * wss:抄改（用于电子帐册） 获得进出口报关单商品信息
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isProduct
	 *            是否是成品
	 * @param iseffective
	 *            是否生效
	 * @return 符合指定条件生效的报关单商品信息
	 */
	public List findImpExpCommInfo(Integer seqNum, String customer,
			String iEType, Date beginDate, Date endDate, boolean isProduct,
			int iseffective, boolean isDeclaration, boolean isDept,
			String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo as a";
		hsql += " left outer join fetch a.complex "
				+ " left outer join fetch a.legalUnit "
				+ " left outer join fetch a.secondLegalUnit "
				+ " left outer join fetch a.country "
				+ " left outer join fetch a.uses "
				+ " left outer join fetch a.levyMode "
				+ " left outer join fetch a.projectDept "
				+ " left outer join fetch a.unit ";
		hsql += " where a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (customer != null && !customer.trim().equals("")) {// 客户供应商
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (iEType != null && !iEType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(iEType));
		}
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			parameters.add(ImpExpType.MATERIAL_DOMESTIC_SALES);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);

		}
		if (beginDate != null && isDeclaration) { // 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (iseffective == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (iseffective == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?  or a.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}

		// 事业部
		if (isDept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ?";
			parameters.add(deptCode);
		} else if (isDept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}

		hsql += " order by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * wss:抄改（合同分析 电子帐册）
	 * 
	 * @return
	 */
	public boolean getIsEmsH2kSend() {
		List list = this
				.find("select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsEdiH2kSend,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * wss:抄改（用于电子帐册）
	 * 
	 * @return
	 */
	public List findEmsHeadH2kInExecuting() {
		if (getIsEmsH2kSend()) {
			return this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.historyState=?",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false) });
		} else {
			return this
					.find("select a from EmsHeadH2k a where a.company.id= ? and a.declareState=? and a.historyState=?",
							new Object[] { CommonUtils.getCompany().getId(),
									DeclareState.PROCESS_EXE,
									new Boolean(false) });
		}
	}

	/**
	 * wss：抄改(合同分析 电子帐册）
	 * 
	 * @param head
	 * @param beginDate
	 * @param endDate
	 * @param isEffect
	 * @param isDeclaration
	 * @param isdept
	 * @param deptCode
	 * @return
	 */
	public List finLjUseNum(EmsHeadH2k head, Date beginDate, Date endDate,
			int isEffect, boolean isDeclaration, boolean isdept, String deptCode) {// --事业部代码
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
				+ " a.unitWear as unitWear, a.wear as wear,"
				// -----------------------------------------------------------------返工复出
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
				+ " where b.baseCustomsDeclaration.impExpType in (?)  ";
		parameters.add(ImpExpType.REWORK_EXPORT);

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and b.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (b.baseCustomsDeclaration.effective = ?  "
					+ " or b.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and b.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and b.projectDept is null ";
		}

		hsql += " and  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  "
				+ "and  b.version = a.emsHeadH2kVersion.version ) as aa,"
				// -----------------------------------------------------------------转厂出口
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
				+ " where b.baseCustomsDeclaration.impExpType in (?)  ";

		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and b.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (b.baseCustomsDeclaration.effective = ?  "
					+ " or b.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and b.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and b.projectDept is null ";
		}

		hsql += " and  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum "
				+ " and  b.version = a.emsHeadH2kVersion.version ) as ff,"

				// ------------------------------------------------------------------直接出口
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
				+ " where b.baseCustomsDeclaration.impExpType in (?)  ";
		parameters.add(ImpExpType.DIRECT_EXPORT);

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and b.baseCustomsDeclaration.effective = ? ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (b.baseCustomsDeclaration.effective = ?  "
					+ " or b.baseCustomsDeclaration.effective is null) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and b.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and b.projectDept is null ";
		}

		hsql += " and  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum "
				+ " and  b.version = a.emsHeadH2kVersion.version ) as tt,"

				// --------------------------------------------------------退厂返工
				+ " (select sum(c.commAmount) from CustomsDeclarationCommInfo c "
				+ " where c.baseCustomsDeclaration.impExpType in (?)  ";
		parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (c.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (c.baseCustomsDeclaration.effective = ? or c.baseCustomsDeclaration.effective is null ) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and c.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and c.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and c.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and c.projectDept is null ";
		}

		hsql += " and  c.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum "
				+ " and  c.version = a.emsHeadH2kVersion.version ) as cc "
				// -----------------------------------------------------------------------------------
				+ " from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
		parameters.add(head.getId());
		if (getIsEmsH2kSend()) {
			hsql += " and a.sendState = ? ";
			parameters.add(Integer.valueOf(SendState.SEND));
		}
		hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
		List list = find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * wss:抄改 电子帐册料件
	 * 
	 * @return 正在执行的电子帐册料件
	 */
	public List findEmsEdiImg(Integer seqNum) {
		if (getIsEmsH2kSend()) {
			List<Object> para = new ArrayList<Object>();
			String hsql = "select a from EmsHeadH2kImg a where a.sendState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(Integer.valueOf(SendState.SEND));
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());
		} else {
			List<Object> para = new ArrayList<Object>();
			String hsql = "select a from EmsHeadH2kImg a where a.emsHeadH2k.declareState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(DeclareState.PROCESS_EXE);
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			hsql += " order by a.seqNum";
			return this.find(hsql, para.toArray());
		}
	}

	/**
	 * 查询耗用料件的总数量
	 * 
	 * @param tenSeqNum
	 *            十位备案序号
	 * @return 与十位备案序号匹配的成品耗用料件的总数量
	 */
	public List findSumAmount(Integer tenSeqNum) {
		return this
				.find("select a.materiel.amount,a.materiel.unitConvert from InnerMergeData a "
						+ "where a.hsAfterTenMemoNo=? and a.company.id=? and a.imrType=?",
						new Object[] { tenSeqNum,
								CommonUtils.getCompany().getId(),
								MaterielType.MATERIEL });
	}

	/*
	 * wss:抄改 （合同分析 电子帐册）
	 */
	public List finLjUseNumForTran(EmsHeadH2k head, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration, boolean isdept,
			String deptCode) {// --事业部代码
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select distinct a.emsHeadH2kVersion.emsHeadH2kExg.seqNum as cpNum,a.emsHeadH2kVersion.version as version,a.seqNum as ljNum,"
				+ " a.unitWear as unitWear, a.wear as wear,"
				+ " (select sum(b.commAmount) from CustomsDeclarationCommInfo b "
				+ " where b.baseCustomsDeclaration.impExpType in (?)  ";
		parameters.add(ImpExpType.DIRECT_EXPORT);// 成品出口

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (b.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (b.baseCustomsDeclaration.effective = ? or b.baseCustomsDeclaration.effective is null ) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and b.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and b.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and b.projectDept is null ";
		}

		hsql += " and  b.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  b.version = a.emsHeadH2kVersion.version ) as aa,"
				+ " (select sum(c.commAmount) from CustomsDeclarationCommInfo c "
				+ " where c.baseCustomsDeclaration.impExpType in (?)  ";
		parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);// 转厂出口

		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (c.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (c.baseCustomsDeclaration.effective = ? or c.baseCustomsDeclaration.effective is null ) ";
			parameters.add(false);
		}
		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and c.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and c.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and c.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and c.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and c.projectDept is null ";
		}

		hsql += " and  c.commSerialNo = a.emsHeadH2kVersion.emsHeadH2kExg.seqNum  and  c.version = a.emsHeadH2kVersion.version ) as cc "
				+ " from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.emsHeadH2k.id = ? ";
		parameters.add(head.getId());
		if (getIsEmsH2kSend()) {
			hsql += " and a.sendState = ? ";
			parameters.add(Integer.valueOf(SendState.SEND));
		}
		hsql += " order by a.emsHeadH2kVersion.emsHeadH2kExg.seqNum,a.emsHeadH2kVersion.version,a.seqNum";
		List list = find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * wss:抄改 查询电子帐册成品信息
	 * 
	 * @return 正在执行的电子帐册成品信息
	 */
	public List findEmsEdiExg(Integer seqNum) {
		if (getIsEmsH2kSend()) {
			List<Object> para = new ArrayList<Object>();
			String hsql = "from EmsHeadH2kExg a where a.sendState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(Integer.valueOf(SendState.SEND));
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			return this.find(hsql, para.toArray());
		} else {
			List<Object> para = new ArrayList<Object>();
			String hsql = "from EmsHeadH2kExg a where a.emsHeadH2k.declareState=? and a.emsHeadH2k.historyState=? and a.company.id= ?";
			para.add(DeclareState.PROCESS_EXE);
			para.add(new Boolean(false));
			para.add(CommonUtils.getCompany().getId());
			if (seqNum != null) {
				hsql += " and a.seqNum = ? ";
				para.add(seqNum);
			}
			return this.find(hsql, para.toArray());
		}
	}

	/**
	 * wss:抄改 查询电子帐册版本信息
	 * 
	 * @param obj
	 *            电子帐册成品
	 * @return 指定的电子帐册成品中的版本信息
	 */
	public List findEmsVersion(EmsHeadH2kExg obj) {
		return this
				.find("select a from EmsHeadH2kVersion a where a.emsHeadH2kExg.id =? and a.company= ?",
						new Object[] { obj.getId(), CommonUtils.getCompany() });
	}

	/**
	 * wss:抄改
	 * 
	 * @param deptCode
	 * @return
	 */

	public List findAllDept(String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from ProjectDept a where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (deptCode != null && !deptCode.equals("")) {
			hsql += " and a.code = ? ";
			parameters.add(deptCode);
		}
		return this.find(hsql, parameters.toArray());
	}

	public Double getSumCommAmount(int type, Date beginDate, Date endDate,
			int isEffect, String tradeName, Integer seqNum,
			boolean isDeclaration, boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ "where a.baseCustomsDeclaration.impExpType in (?) and a.baseCustomsDeclaration.company.id = ? ";
		parameters.add(type);
		parameters.add(CommonUtils.getCompany().getId());
		if (seqNum != null && !seqNum.equals("")) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (isEffect == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(true);
		} else if (isEffect == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and (a.baseCustomsDeclaration.effective = ? or a.baseCustomsDeclaration.effective is null )";
			parameters.add(false);
		}
		if (tradeName != null && !"".equals(tradeName)) {
			hsql += " and a.baseCustomsDeclaration.tradeMode.name = ?";
			parameters.add(tradeName);
		}

		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ?";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}
		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	// 计算料件平均单价
	public Double getImgAveragePrice(Integer seqNum, Date beginDate,
			Date endDate, Boolean isEffect, boolean isProduct,
			boolean isDeclaration, boolean isdept, String deptCode) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select sum(a.dollarTotalPrice)/sum(a.commAmount) from CustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.company.id = ? "
				+ " and a.commSerialNo = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		if (!isProduct) {// 料件
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
			parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		} else {// 成品
			hsql += " and a.baseCustomsDeclaration.impExpType in (?,?,?,?) ";
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		}
		if (isEffect != null && isEffect.equals(new Boolean(true))) {
			hsql += " and (a.baseCustomsDeclaration.effective = ?) ";
			parameters.add(isEffect);
		}
		if (beginDate != null && !isDeclaration) {// 结关日期
			hsql += " and a.baseCustomsDeclaration.impExpDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && !isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.impExpDate<=? ";
			parameters.add(endDate);
		}

		if (beginDate != null && isDeclaration) {// 申报日期
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(beginDate);
		}
		if (endDate != null && isDeclaration) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(endDate);
		}
		// 事业部
		if (isdept && deptCode != null) { // 区分事业部: A
			hsql += " and a.projectDept.code = ? ";
			parameters.add(deptCode);
		} else if (isdept && (deptCode == null)) {
			hsql += " and a.projectDept is null ";
		}

		List list = this.find(hsql, parameters.toArray());
		if (list != null && list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findFptAppItemCount(String emsNo, Date beginDate, Date endDate,
			boolean isMaterial) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (isMaterial) {// 料件
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.inEmsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.IN);
		} else {// 成品
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.fptAppHead.emsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.OUT);
		}
		hsql += " and b.fptAppHead.declareState=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		hsql += " and b.inOutFlag=b.fptAppHead.inOutFlag ";
		hsql += " group by b.trNo,b.codeTs.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findCustomsEnvelopCommInfoCount(String emsNo, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select b.seqNum,b.complex.code,sum(b.ownerQuantity) "
				+ " from CustomsEnvelopCommodityInfo as b"
				+ " where b.customsEnvelopBill.company.id=? "
				+ " and b.customsEnvelopBill.emsNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		hsql += " group by b.seqNum,b.complex.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}

	/**
	 * 查找通关备案料件 来自 合同ID 如果想要过滤其在Bom没被引用的料件还要一句 in
	 * 
	 * @param parentId
	 *            通关备案通关备案表头Id
	 * @return List 是DzscEmsExgBill型，通关备案料件
	 */
	public List findDzscEmsImgBill(String headId) {
		return this.find(
				"select a from DzscEmsImgBill a where a.dzscEmsPorHead.id = ? "
						+ " order by a.seqNum ", new Object[] { headId });
	}

	/**
	 * 查询电子手册手册商品数量总量
	 * 
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            电子手册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return double 返回手册商品数量总量
	 */
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " left join a.baseCustomsDeclaration b "
				+ " where b.company.id=? "
				+ " and b.effective=? "
				+ " and b.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and b.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and b.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and b.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and b.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " b.tradeMode.code=? ";
				} else {
					hsql += " b.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 查询电子手册手册商品数量总量
	 * 
	 * @param commSerialNo
	 *            商品序号
	 * @param impExpFlag
	 *            进口标志
	 * @param impExpType
	 *            进口类型
	 * @param tradeCodes
	 *            贸易方式编码
	 * @param emsNo
	 *            电子手册手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return double 手册商品数量总量
	 */
	public double findCommInfoTotalAmount(Integer commSerialNo,
			Integer impExpFlag, Integer impExpType, String[] tradeCodes,
			String emsNo, Date beginDate, Date endDate, int state) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select  sum(a.commAmount)  from  DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				// + " and a.baseCustomsDeclaration.effective=? "
				+ " and a.baseCustomsDeclaration.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		// parameters.add(new Boolean(true));
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.baseCustomsDeclaration.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.baseCustomsDeclaration.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (commSerialNo != null) {
			hsql += " and a.commSerialNo=?";
			parameters.add(Integer.valueOf(commSerialNo));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? ";
				} else {
					hsql += " a.baseCustomsDeclaration.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		if (state == CustomsDeclarationState.EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(true);
		} else if (state == CustomsDeclarationState.NOT_EFFECTIVED) {
			hsql += " and a.baseCustomsDeclaration.effective=?";
			parameters.add(false);
		}
		List list = this.find(hsql, parameters.toArray());
		if (list.size() < 1 || list.get(0) == null) {
			return 0;
		} else {
			return Double.parseDouble(list.get(0).toString());
		}
	}

	/**
	 * 根据商品序号，查询成品商品信息
	 * 
	 * @param emsNo
	 *            电子手册手册编号
	 * @param exgSeqNum
	 *            商品序号
	 * @return List 是DzscEmsBomBill类型，电子手册手册Bom资料
	 */
	public List<DzscEmsBomBill> findBomByExg(String emsNo, Integer exgSeqNum) {
		String hsql = "select a from DzscEmsBomBill as a "
				+ " left join a.dzscEmsExgBill as b " + " where b.seqNum=? "
				+ " and b.dzscEmsPorHead.emsNo=?"
				+ " and b.dzscEmsPorHead.declareState=? "
				+ " and b.dzscEmsPorHead.company.id=? ";
		return this.find(hsql, new Object[] { Integer.valueOf(exgSeqNum),
				emsNo, DzscState.EXECUTE, CommonUtils.getCompany().getId() });
	}

	/**
	 * 返回通关备案成品资料
	 * 
	 * @param head
	 *            通关备案表头
	 * @return List 是DzscEmsExgBill型，通关备案成品资料
	 */
	public List findDzscEmsExgBill(DzscEmsPorHead head) {
		return this.find(
				"select a from DzscEmsExgBill a where a.company.id = ?"
						+ " and a.dzscEmsPorHead.id = ?"
						+ " order by a.seqNum ", new Object[] {
						CommonUtils.getCompany().getId(), head.getId() });
	}

	/**
	 * 返回根据临时类的料号、版本号返回Bom
	 * 
	 * @param bill
	 * @return
	 */
	public List<MaterialBomDetail> getProductBom(TempThatDayBalance bill) {
		return this
				.find("select a from MaterialBomDetail a"
						+ " where a.company.id=? and a.version.master.materiel.ptNo=? and a.version.version=?",
						new Object[] { CommonUtils.getCompany().getId(),
								bill.getPtPart(), bill.getVersion() });

	}

	public List findInputSemiData(List<Condition> finishedConditions) {
		String hsql = "select a from CheckBalance a where  a.kcType=? ";
		List<Object> params = new ArrayList<Object>();
		// params.add(CommonUtils.getCompany());

		params.add("3");
		if (finishedConditions != null) {
			for (int i = 0; i < finishedConditions.size(); i++) {
				Condition condition = (Condition) finishedConditions.get(i);
				if (condition.getLogic() != null) {
					hsql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					hsql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					hsql += "a." + condition.getFieldName();

				}
				if (condition.getOperator() != null) {
					hsql += " " + condition.getOperator() + " ";
				}
				if (condition.getValue() != null) {
					hsql += "? ";
					params.add(condition.getValue());
					System.out.println("value=" + condition.getValue());
				}
				if (condition.getEndBracket() != null)
					hsql += condition.getEndBracket();
			}
		}
		System.out.println("hsql=-----" + hsql);
		List list = this.find(hsql, params.toArray());
		return list;
	}

	/**
	 * 根据成品条件和料件条件查询CheckBalanceConvertMateriel类
	 * 
	 * @param finishedConditions
	 *            成品查询条件
	 * @param materielCondtion
	 *            料件查询条件
	 * @param groupBy
	 *            分组
	 * @return 查询结果 2010-07-21 hcl
	 */
	public List getCheckBalanceConvertMateriel(List finishedConditions,
			List materielCondtion, String groupBy) {

		String sql = " select a from CheckBalanceConvertMateriel  a  where a.company =? ";

		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		// 添加成品条件
		if (finishedConditions != null) {
			for (int i = 0; i < finishedConditions.size(); i++) {
				Condition condition = (Condition) finishedConditions.get(i);
				if (condition.getValue() != null
						&& !condition.getValue().equals("")) {
					if (condition.getLogic() != null) {
						sql += " " + condition.getLogic() + "  ";
					}
					if (condition.getBeginBracket() != null) {
						sql += condition.getBeginBracket();
					}
					if (condition.getFieldName() != null) {
						sql += "a.fatherCheckBalance."
								+ condition.getFieldName();
					}
					if (condition.getOperator() != null) {
						sql += condition.getOperator();
					}
					if (condition.getValue() != null) {
						if (condition.getOperator().indexOf("in") > 0) {
							String[] strs = (String[]) condition.getValue();
							for (int j = 0; j < strs.length; j++) {
								if (j == 0) {
									sql += "?";
								} else {
									sql += ",?";
								}
							}
							for (String str : strs) {
								params.add(str);
							}
						} else {
							sql += "? ";
							params.add(condition.getValue());
							System.out
									.println("value1=" + condition.getValue());
						}
					}
					if (condition.getEndBracket() != null)
						sql += condition.getEndBracket();
				}
			}
		}// 添加料件条件
		if (materielCondtion != null) {
			for (int i = 0; i < materielCondtion.size(); i++) {
				Condition condition = (Condition) materielCondtion.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {
					if (condition.getOperator().indexOf("in") > 0) {
						String[] strs = (String[]) condition.getValue();
						for (int j = 0; j < strs.length; j++) {
							if (j == 0) {
								sql += "?";
							} else {
								sql += ",?";
							}
						}
						for (String str : strs) {
							params.add(str);
						}
					} else {
						sql += "? ";
						params.add(condition.getValue());
						System.out.println("value1=" + condition.getValue());
					}
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}
		}

		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
		System.out.println("---------------sql:" + sql);
		List result = this.find(sql, params.toArray());
		return result;
	}

	/**
	 * 获得边角料查询报表内容
	 * 
	 * @return 边角料查询报表
	 * @author wss
	 */
	public List<LeftoverMaterielStatInfo> findLeftoverMaterielStatInfo() {
		String hsql = "select a from LeftoverMaterielStatInfo as a where a.company.id = ? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 批量删除边角料查询报表内容
	 * 
	 * @author wss
	 */
	public void deleteLeftoverMaterielStatInfo() {
		this.batchUpdateOrDelete(
				"delete from LeftoverMaterielStatInfo m where m.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 查找电子手册的所有版本
	 * 
	 * @param pkNoList料号
	 * @return 与指定料号匹配的且损耗大于零的电子手册的所有版本
	 * @author wss
	 */
	public List findEmsHeadH2kBomDetail(List<String> pkNoList) {
		String hsql = "select a.name,a.spec,a.unit.name,a.wear ,a.unitWear,a.emsHeadH2kVersion.id "
				+ "from EmsHeadH2kBom a left join a.unit where ( a.wear is not null and a.wear >= 0 ) ";
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and (  a.emsHeadH2kVersion.id = ?  ";
			} else {
				hsql += " or  a.emsHeadH2kVersion.id = ?   ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(pkNoList.get(i));// 料号
		}
		if (parameters.size() <= 0) {
			return new ArrayList();
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找纸质手册的所有版本
	 * 
	 * @param pkNoList料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 * @author wss转抄
	 */
	public List findContractBomDetail(List<String> pkNoList) {
		String hsql = "select a.name,a.spec,a.unit.name,a.waste ,a.unitWaste,a.contractExg.id "
				+ "from ContractBom a left join a.unit where (2>1) ";// (
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and (  a.contractExg.id = ?  ";
			} else {
				hsql += " or  a.contractExg.id = ?   ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(pkNoList.get(i));// 料号
		}
		if (parameters.size() <= 0) {
			return new ArrayList();
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 查找纸质手册的所有版本
	 * 
	 * @param pkNoList料号
	 * @return 与指定料号匹配的且损耗大于零的纸质手册的所有版本
	 * @author wss
	 */
	public List findDzscEmsBomBill(List<String> pkNoList) {
		String hsql = "select a.name,a.spec,a.unit.name,a.ware ,a.unitWare,a.dzscEmsExgBill.id "
				+ "from DzscEmsBomBill a left join a.unit where (1=1) ";// (
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < pkNoList.size(); i++) {
			if (i == 0) {
				hsql += " and (  a.dzscEmsExgBill.id = ?  ";
			} else {
				hsql += " or  a.dzscEmsExgBill.id = ?   ";
			}
			if (i == pkNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(pkNoList.get(i));// 料号
		}
		if (parameters.size() <= 0) {
			return new ArrayList();
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 获得获得工厂和实际报关对应表通过工厂料号和商编码
	 * 
	 * @param ptNo料号
	 * @param complexNo
	 *            商编码
	 * @return 与指定工厂和实际报关对应的料件
	 * @author lyh
	 */
	public List findFactoryAndFactualCustomsRalationByPartComplex(String ptNo,
			String complexNo) {
		String hsql = "select a from FactoryAndFactualCustomsRalation a "
				+ " where a.materiel.ptNo = ? and a.statCusNameRelationHsn.complex.code = ? ";// (
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(ptNo);
		parameters.add(complexNo);
		return this.findNoCache(hsql, parameters.toArray());
	}
}
