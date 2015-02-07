package com.bestway.bcus.cas.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillFixing;
import com.bestway.bcus.cas.entity.BillFixingBase;
import com.bestway.bcus.cas.entity.BillFixingThing;
import com.bestway.bcus.cas.entity.TempFixtureInTaxationReport;
import com.bestway.bcus.cas.entity.TempFixtureNotInTaxationReport;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.sun.org.apache.xpath.internal.Expression;

/**
 * 海关账设备logic
 * 贺巍于 2009年12月4日补充注释
 * @author ?
 *
 */
public class FixingThingLogic {
	/** 海关帐Dao */
	private CasDao casDao = null;

	/**
	 * 获取海关张DAO类
	 * @return
	 */
	public CasDao getCasDao() {
		return casDao;
	}

	/**
	 * 设置海关张DAO类
	 * @return
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	/**
	 * 查询设备单据
	 * 
	 * @param conditions
	 *            conditions中包括对单据生效日期的过滤
	 * @return 设备单据
	 */
	public List findBillFixing(List<Condition> conditions) {
		String machineBillDetail = BillUtil
				.getBillDetailTableNameByMaterielType(MaterielType.MACHINE); // 设备

		conditions.add(new Condition("and", null, "billMaster.isValid", "=",
				Boolean.valueOf(true), null));
		conditions.add(new Condition("and", null, "hsName is not null ", null,
				null, null));
		String selects = "select sum(a.hsAmount),sum(a.money),a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name  ";
		String groupBy = "group by a.billMaster.billType.code,a.hsName,a.hsSpec, a.hsUnit.name ";
		String leftOuter = " left join a.hsUnit  ";
		// 设备
		List listBillDetailProduct = casDao.commonSearch(selects,
				machineBillDetail, conditions, groupBy, leftOuter);
		List<BillFixing> lsResult = getResultList(listBillDetailProduct);
		//
		// 删除当年度的所有海关帐
		//
		this.casDao.deleteBillFixing();

		for (int i = 0; i < lsResult.size(); i++) {
			BillFixing item = (BillFixing) lsResult.get(i);
			item.setCompany(CommonUtils.getCompany());
			this.casDao.getHibernateTemplate().save(item);
		}
		return lsResult;
	}

	/**
	 * 查询设备的单据(分类别)主要用在设备出入仓
	 * 
	 * @param listBillDetailProduct
	 *            成品单据明细
	 * @return 分类别显示设备单据
	 */
	private List<BillFixing> getResultList(List listBillDetailProduct) {
		Map<String, BillFixing> resultHash = new HashMap<String, BillFixing>();
		for (int i = 0; i < listBillDetailProduct.size(); i++) {
			Object[] objs = (Object[]) listBillDetailProduct.get(i);
			Double hsAmount = objs[1] == null ? 0.0 : (Double) objs[1];
			Double money = objs[0] == null ? 0.0 : (Double) objs[0];
			
			System.out.println("wss objs[0] = " + objs[0]);
			System.out.println("wss objs[1] = " + objs[1]);
			
			System.out.println("wss hsAmount = " + hsAmount);
			System.out.println("wss money = " + money);
			
			String typeCode = objs[4] == null ? "" : (String) objs[4];
			
			String hsName = objs[5] == null ? "" : (String) objs[5];
			String hsSpec = objs[3] == null ? "" : (String) objs[3];
			String hsUnitName = objs[2] == null ? "" : (String) objs[2];
			
			System.out.println("wss typeCode = " + objs[4]);
			System.out.println("wss hsName = " + objs[5]);
			System.out.println("wss hsSpec = " + objs[3]);
			System.out.println("wss hsUnitName = " + objs[2]);
			
			
			//
			// key=名称+"/"+规格+"/"+单位
			//            
			BillFixing item = null;
			String key = hsName + "/" + hsSpec + "/" + hsUnitName;
			if (resultHash.get(key) != null) {
				item = (BillFixing) resultHash.get(key);
			} else {
				item = new BillFixing();
				item.setBill1(key);
				resultHash.put(key, item);
			}
			//
			// 报关总金额
			// 3001设备直接报关进口 +3002设备结转报关进口+3003设备其中征税进口+3006设备其他来源+3005设备国内采购
			//
			if (typeCode.equals("3001") || typeCode.equals("3002")
					|| typeCode.equals("3003") || typeCode.equals("3006")
					|| typeCode.equals("3005")) {
				item.setMoney(item.getMoney() + money);
			}

			//
			// 1. 3001 设备直接报关进口 + 3003 设备其中征税进口
			//
			if (typeCode.equals("3001") || typeCode.equals("3003")) {
				item.setBillSum1(item.getBillSum1() + hsAmount);
			}
			//
			// 2. 3002 设备结转报关进口
			//
			if (typeCode.equals("3002")) {
				item.setBillSum2(item.getBillSum2() + hsAmount);
			}
			//
			// 3. 3003 设备其中征税进口
			//
			if (typeCode.equals("3003")) {
				item.setBillSum3(item.getBillSum3() + hsAmount);
			}
			//
			// 4 .3005 设备国内采购
			//
			if (typeCode.equals("3005")) {
				item.setBillSum4(item.getBillSum4() + hsAmount);
			}
			//
			// 5 . 3006 设备其他来源
			//
			if (typeCode.equals("3006")) {
				item.setBillSum5(item.getBillSum5() + hsAmount);
			}
			//
			// 7，3101 设备退运出口
			//
			if (typeCode.equals("3101")) {
				item.setBillSum7(item.getBillSum7() + hsAmount);
			}
			//
			// 8. 3102 设备结转报关出口
			//
			if (typeCode.equals("3102")) {
				item.setBillSum8(item.getBillSum8() + hsAmount);
			}
			//
			// 9. 3103 设备出借/出口
			//
			if (typeCode.equals("3103")) {
				item.setBillSum9(item.getBillSum9() + hsAmount);
			}
			//
			// 10. 3104 设备海关批准出售
			//
			if (typeCode.equals("3104")) {
				item.setBillSum10(item.getBillSum10() + hsAmount);
			}
			//
			// 11. 3105 设备其他出售
			//
			if (typeCode.equals("3105")) {
				item.setBillSum11(item.getBillSum11() + hsAmount);
			}
			//
			// 12. 3106 设备报废
			//
			if (typeCode.equals("3106")) {
				item.setBillSum12(item.getBillSum12() + hsAmount);
			}
			//
			// 13. 3107 设备闲置
			//
			if (typeCode.equals("3107")) {
				item.setBillSum13(item.getBillSum13() + hsAmount);
			}
			//
			// 14. 3108 设备其他处理
			//
			if (typeCode.equals("3108")) {
				item.setBillSum14(item.getBillSum14() + hsAmount);
			}
		}
		List<BillFixing> lsResult = new ArrayList<BillFixing>();
		lsResult.addAll(resultHash.values());
		return lsResult;
	}

	/**
	 * 重新载入海关资料(设备来自自定资料)
	 * 
	 */
	public void copyBillFixingThing() {
		//
		// 删除的所有海关帐
		//
		this.casDao.deleteBillFixingThing();
		//
		// 分页查询并修改
		//
		int index = 0;
		int length = 1000;
		while (true) {
			List tempBillDetails = this.casDao.findBillFixing(index, length);
			for (int i = 0; i < tempBillDetails.size(); i++) {
				BillFixing temp = (BillFixing) tempBillDetails.get(i);
				BillFixingThing b = new BillFixingThing();
				b.setCompany(temp.getCompany());
				b.setMoney(temp.getMoney()); // 金额
				b.setBill1(temp.getBill1()); // 名称
				b.setBill2(temp.getBill2()); // 备注
				b.setBillSum1(temp.getBillSum1());
				b.setBillSum2(temp.getBillSum2());
				b.setBillSum3(temp.getBillSum3());
				b.setBillSum4(temp.getBillSum4());
				b.setBillSum5(temp.getBillSum5());
				b.setBillSum6(temp.getBillSum6());
				b.setBillSum7(temp.getBillSum7());
				b.setBillSum8(temp.getBillSum8());
				b.setBillSum9(temp.getBillSum9());
				b.setBillSum10(temp.getBillSum10());
				b.setBillSum12(temp.getBillSum12());
				b.setBillSum13(temp.getBillSum13());
				b.setBillSum14(temp.getBillSum14());
				b.setBillSum15(temp.getBillSum15());
				this.casDao.getHibernateTemplate().save(b);
			}
			index += length;
			if (tempBillDetails.size() <= 0 || tempBillDetails.size() < length) {
				break;
			}
		}
	}

	/**
	 * 设备统计报表(自定义)
	 * 
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 所有设备的内容
	 */
	public List findBillFixing(Boolean isOverMillion) {
		List<BillFixingBase> returnList = new ArrayList<BillFixingBase>();
		List list = this.casDao.findBillFixing();
		if (isOverMillion == true) {
			for (int i = 0; i < list.size(); i++) {
				BillFixingBase exgExportInfo = (BillFixingBase) list.get(i);
				if (exgExportInfo.getMoney() < 1000000) {
					BillFixingBase temp = new BillFixingBase();
					temp.setBill1(exgExportInfo.getBill1());
					temp.setBill2("金额小于一百万");
					returnList.add(temp);
				} else {
					exgExportInfo.setBill2("金额大于一百万");
					returnList.add(exgExportInfo);
				}
			}
			return returnList;
		} else {
			return list;
		}
	}

	/**
	 * 设备统计报表(海关)
	 * 
	 * @param isOverMillion
	 *            是否超过百万
	 * @return 海关用的设备资料
	 */
	public List findBillFixingThing(Boolean isOverMillion) {
		List<BillFixingBase> returnList = new ArrayList<BillFixingBase>();
		List list = this.casDao.findBillFixingThing();
		if (isOverMillion == true) {
			for (int i = 0; i < list.size(); i++) {
				BillFixingBase exgExportInfo = (BillFixingBase) list.get(i);
				if (exgExportInfo.getMoney() < 1000000) {
					BillFixingBase temp = new BillFixingBase();
					temp.setBill1(exgExportInfo.getBill1());
					temp.setBill2("小于一百万");
					returnList.add(temp);
				} else {
					returnList.add(exgExportInfo);
				}
			}
			return returnList;
		} else {
			return list;
		}
	}

	/**
	 * 获取保税设备清单
	 * 
	 * @param request
	 * @param stateCode
	 *            状态,空为无限制,001为未解除监管,002为解除监管
	 * @param beginDate
	 *            开始申报日期
	 * @param endDate
	 *            结束申报日期
	 * @return TempFixtureNotInTaxationReport
	 */
	public List getFixtureNotInTaxationReport(String stateCode, Date beginDate,
			Date endDate) {
		List arrayList = new ArrayList();
		List list = casDao.findFixtureInCustomSCommInfo(beginDate, endDate);
		BaseCustomsDeclarationCommInfo baseCustomsCommInfo;// 体
		BaseCustomsDeclaration baseDeclaration;// 头
		Map fixMap = this.getBillDetailFixtureMap();// 单据中心的设备资料(包含设备的入出库)
		int j = 0;// 记录序号
		for (int i = 0; i < list.size(); i++) {
			baseCustomsCommInfo = (BaseCustomsDeclarationCommInfo) list.get(i);
			baseDeclaration = baseCustomsCommInfo.getBaseCustomsDeclaration();

			String code = ((Company) baseCustomsCommInfo.getCompany())
					.getCode();// 加工当为代码
			char[] a = new String(code.getBytes(), 0, code.length())
					.toCharArray();

			if (a[5] >= 'A' && a[5] <= 'z' && a[6] >= 'A' && a[6] <= 'z') {// 当公司的加工单位编码的第六和第七位为字母为来料公司否则为三资
				if (!baseDeclaration.getTradeMode().getName().equals("不作价设备"))// 来料时贸易方式为"不作价设备"时为保税
					continue;
			} else {
				if (!baseDeclaration.getTradeMode().getName().equals("减免设备结转")
						&& !baseDeclaration.getLevyKind().getName().equals(
								"加工设备"))// 三资时贸易方式为"减免设备结转"和征免方式为"加工设备"时为保税
					continue;
			}

			String key = baseDeclaration.getContract() + "/"
					+ baseDeclaration.getCustomsDeclarationCode() + "/"
					+ baseCustomsCommInfo.getComplex().getCode() + "/"
					+ baseCustomsCommInfo.getCommName() + "/"
					+ baseCustomsCommInfo.getCommSpec() + "/"
					+ baseCustomsCommInfo.getUnit().getCode();
			if (fixMap.get(key) == null)// 不要设备没有跟报关单做对应的
				continue;

			BillDetail billDetail = (BillDetail) fixMap.get(key);
			String tradeModeCode = baseDeclaration.getTradeMode().getCode();// 贸易方式代码
			if (tradeModeCode.equals("0110")) {// 一般贸易为非保税即征税
				continue;
			}

			// 获取当前日期
			Date nowDate = new Date();
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(nowDate);

			// 监管期限
			Calendar seeCalendar = Calendar.getInstance();
			seeCalendar.setTime(baseDeclaration.getImpExpDate());// 进出口日期
			seeCalendar.set(Calendar.YEAR, seeCalendar.get(Calendar.YEAR) + 5);

			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String stateName = null;
			if (!stateCode.equals(" ")) {
				if (stateCode.equals("001") && seeCalendar.after(nowCalendar)) {// 未解除监管
					stateName = "未解除监管";
				} else if (stateCode.equals("002")
						&& seeCalendar.before(nowCalendar)) {// 已解除监管
					stateName = "已解除监管";
				}
			} else {// 未解除监管、已解除监管都获取
				if (seeCalendar.before(nowCalendar))
					stateName = "已解除监管";
				else
					stateName = "未解除监管";
			}

			if (stateName == null)
				continue;
			TempFixtureNotInTaxationReport tempFixtureNotInTaxationReport = new TempFixtureNotInTaxationReport();
			tempFixtureNotInTaxationReport.setStateName(stateName);
			tempFixtureNotInTaxationReport.setSerialNumber(++j);
			tempFixtureNotInTaxationReport
					.setBaseCustomsCommInfo(baseCustomsCommInfo);// 设置报关单体资料
			tempFixtureNotInTaxationReport.setBillDetail(billDetail);// 设置报关单体资料
			arrayList.add(tempFixtureNotInTaxationReport);
		}
		System.out.println("arrayList.size()---------------------------"
				+ arrayList.size());
		return arrayList;

	}

	/**
	 * 获取设备单据表体全部
	 * 
	 * @return
	 */
	private Map getBillDetailFixtureMap() {
		Map fixMap = new HashMap();
		List list = casDao.findBillDetailFixture();
		System.out.println("list.size()------------map---------------"
				+ list.size());
		for (int i = 0; i < list.size(); i++) {
			BillDetail billDetail = (BillDetail) list.get(i);
			String key = billDetail.getEmsNo() + "/" + billDetail.getCustomNo()
					+ "/" + billDetail.getComplex().getCode() + "/"
					+ billDetail.getHsName() + "/" + billDetail.getHsSpec()
					+ "/" + billDetail.getHsUnit().getCode();
			System.out.println("key------------map---------------" + key);
			if (fixMap.get(key) == null)
				fixMap.put(key, billDetail);
		}
		return fixMap;

	}

	/**
	 * 获取非保税设备清单,已解除监管
	 * 
	 * @param request
	 * @param beginDate
	 *            开始申报日期
	 * @param endDate
	 *            结束申报日期
	 * @return TempFixtureNotInTaxationReport
	 */
	public List getFixtureInTaxationReport(Date beginDate, Date endDate) {
		List arrayList = new ArrayList();
		List list = casDao.findFixtureInCustomSCommInfo(beginDate, endDate);
		BaseCustomsDeclarationCommInfo baseCustomsCommInfo;// 体
		BaseCustomsDeclaration baseDeclaration;// 头
		Map fixMap = this.getBillDetailFixtureMap();// 单据中心的设备资料
		int j = 0;// 序号
		for (int i = 0; i < list.size(); i++) {// 非国内购买而且要与单据与报关单对应
			baseCustomsCommInfo = (BaseCustomsDeclarationCommInfo) list.get(i);
			baseDeclaration = baseCustomsCommInfo.getBaseCustomsDeclaration();

			String tradeModeName = baseDeclaration.getTradeMode().getName();// 贸易方式代码
			if (!tradeModeName.equals("一般贸易")) {// 来料、三资贸易方式为"一般贸易"时为非保税
				continue;
			}

			
			// 获取当前日期
			Date nowDate = new Date();
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(nowDate);

			// 监管期限
			Calendar seeCalendar = Calendar.getInstance();
			seeCalendar.setTime(baseDeclaration.getImpExpDate());// 进出口日期
			seeCalendar.set(Calendar.YEAR, seeCalendar.get(Calendar.YEAR) + 5);

			if (nowCalendar.before(seeCalendar))
				continue;

			String key = baseDeclaration.getContract() + "/"
					+ baseDeclaration.getCustomsDeclarationCode() + "/"
					+ baseCustomsCommInfo.getComplex().getCode() + "/"
					+ baseCustomsCommInfo.getCommName() + "/"
					+ baseCustomsCommInfo.getCommSpec() + "/"
					+ baseCustomsCommInfo.getUnit().getCode();
			if (fixMap.get(key) == null)// 不要设备没有跟报关单做对应的
				continue;

			BillDetail billDetail = (BillDetail) fixMap.get(key);
			TempFixtureInTaxationReport tempFixtureInTaxationReport = new TempFixtureInTaxationReport();

			tempFixtureInTaxationReport.setSerialNumber(++j);// 序号
			tempFixtureInTaxationReport.setPtPart(billDetail.getPtPart());// 设备料号
			tempFixtureInTaxationReport.setPtName(billDetail.getPtName());// 工厂商品名称
			tempFixtureInTaxationReport.setCommName(baseCustomsCommInfo
					.getCommName());// 报关商品名称
			tempFixtureInTaxationReport.setCommAmount(baseCustomsCommInfo
					.getCommAmount());// 商品数量
			tempFixtureInTaxationReport.setCommUnitPrice(baseCustomsCommInfo
					.getCommUnitPrice());// 商品单价
			tempFixtureInTaxationReport.setCommTotalPrice(baseCustomsCommInfo
					.getCommTotalPrice());// 商品总价
			tempFixtureInTaxationReport.setUnitName(baseCustomsCommInfo
					.getUnit().getName());// 单位
			tempFixtureInTaxationReport.setContractNo(baseDeclaration
					.getContract());// 合同协议号
			tempFixtureInTaxationReport.setImpExpDate(baseDeclaration
					.getImpExpDate());// 进出口日期
			tempFixtureInTaxationReport.setOutImpExpDate(seeCalendar.getTime());// 解除监管日期
			tempFixtureInTaxationReport.setTradeModeName(baseDeclaration
					.getTradeMode().getName());// 贸易方式

			String customInvoiceNo = baseDeclaration
					.getCustomsDeclarationCode();// 报关单号/发票号
			customInvoiceNo += "/" + baseDeclaration.getInvoiceCode() == null ? ""
					: baseDeclaration.getInvoiceCode() == null ? ""
							: baseDeclaration.getInvoiceCode();
			tempFixtureInTaxationReport.setCustomInvoiceNo(customInvoiceNo);// 报关单号/发票号
			tempFixtureInTaxationReport.setLocation(billDetail.getLocation());// 存放地点
			tempFixtureInTaxationReport.setHoldMan(billDetail.getHoldMan());// 保管人
			tempFixtureInTaxationReport.setNote(billDetail.getNote());// 备注
			arrayList.add(tempFixtureInTaxationReport);

		}
		System.out.println("arrayList.size()-----------1----------------"
				+ arrayList.size());
		list = casDao.findCountryBuyBillDetailFixture(beginDate, endDate);// 获取国内购买的数据
		for (int i = 0; i < list.size(); i++) {// 国内购买数据
			BillDetail billDetail = (BillDetail) list.get(i);
			// 获取当前日期
			Date nowDate = new Date();
			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(nowDate);

			// 监管期限
			Calendar seeCalendar = Calendar.getInstance();
			seeCalendar.setTime(billDetail.getBillMaster().getValidDate());// 生效日期
			seeCalendar.set(Calendar.YEAR, seeCalendar.get(Calendar.YEAR) + 5);
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("nowCalendar---2------"
					+ bartDateFormat.format(nowCalendar.getTime()));
			System.out.println("seeCalendar----2-----"
					+ bartDateFormat.format(seeCalendar.getTime()));
			if (nowCalendar.before(seeCalendar))
				continue;

			TempFixtureInTaxationReport tempFixtureInTaxationReport = new TempFixtureInTaxationReport();

			tempFixtureInTaxationReport.setSerialNumber(++j);// 序号
			tempFixtureInTaxationReport.setPtPart(billDetail.getPtPart());// 设备料号
			tempFixtureInTaxationReport.setPtName(billDetail.getPtName());// 工厂商品名称
			tempFixtureInTaxationReport.setCommName(billDetail.getPtName());// 报关商品名称
			tempFixtureInTaxationReport.setCommAmount(billDetail.getPtAmount());// 商品数量
			tempFixtureInTaxationReport.setCommUnitPrice(billDetail
					.getPtPrice());// 商品单价
			double totalPrice = (billDetail.getPtPrice() == null ? 0
					: billDetail.getPtPrice())
					* (billDetail.getPtAmount() == null ? 0 : billDetail
							.getPtAmount());
			tempFixtureInTaxationReport.setCommTotalPrice(totalPrice);// 商品总价
			tempFixtureInTaxationReport.setUnitName(billDetail.getPtUnit()
					.getName());// 单位
			tempFixtureInTaxationReport.setContractNo(billDetail.getEmsNo());// 合同协议号
			tempFixtureInTaxationReport.setImpExpDate(billDetail
					.getBillMaster().getValidDate());// 进出口日期
			tempFixtureInTaxationReport.setOutImpExpDate(seeCalendar.getTime());// 解除监管日期
			tempFixtureInTaxationReport.setTradeModeName("国内购买");// 贸易方式

			tempFixtureInTaxationReport.setCustomInvoiceNo(billDetail
					.getCustomNo());// 报关单号/发票号
			tempFixtureInTaxationReport.setLocation(billDetail.getLocation());// 存放地点
			tempFixtureInTaxationReport.setHoldMan(billDetail.getHoldMan());// 保管人
			tempFixtureInTaxationReport.setNote(billDetail.getNote());// 备注
			arrayList.add(tempFixtureInTaxationReport);
		}

		System.out.println("arrayList.size()-----------2----------------"
				+ arrayList.size());
		return arrayList;

	}
	
	
	/**
	 * wss:用原生sql计算
	 * 生产设备使用情况表
	 */
	public List calBillFixingBySql(Date beginDate,Date endDate){
		
		String machineBillDetail = BillUtil
					.getBillDetailTableNameByMaterielType(MaterielType.MACHINE); // 设备单据体
		String machineBillMaster = BillUtil
					.getBillMasterTableNameByMaterielType(MaterielType.MACHINE); // 设备单据头
		
		int beginYear = beginDate.getYear() + 1900;
		int endYear = endDate.getYear()+ 1900;
		System.out.println("beginYear:" + beginYear + "endYear:" + endYear);

		//wss:转化为原生sql查询
		String selects = " select sum(a.hsAmount) ahsamount,sum(a.money) amoney,c.code,a.hsName,a.hsSpec,d.name ";
		String where = " where a.billMasterId = b.id " //
							+ " and b.billTypeId = c.id "// 
								+ " and a.hsUnitId = d.code " //
									+ " and b.validDate >=  ? " 
										+"  and b.validDate <= ? " 
											+ " and b.isValid = ?  " 
												+ " and a.hsName is not null  ";
		
		String groupBy = " group by c.code,a.hsName,a.hsSpec,d.name ";
		//返回类型设备
		Map backTypeMap = new HashMap<String, NullableType>();
		backTypeMap.put("ahsamount", Hibernate.DOUBLE);
		backTypeMap.put("amoney", Hibernate.DOUBLE);
		backTypeMap.put("code", Hibernate.STRING);
		backTypeMap.put("hsName", Hibernate.STRING);
		backTypeMap.put("hsSpec", Hibernate.STRING);
		backTypeMap.put("name", Hibernate.STRING);
		
		//传入参数
		List objParams = new ArrayList();
		objParams.add(CommonUtils.getBeginDate(beginDate));
		objParams.add(CommonUtils.getEndDate(endDate));
		objParams.add(1);		
		
		List listAll = new ArrayList();
		List listBillDetailProduct = new ArrayList();
		String billTypeTable = "billtype";
		String hsUnitTable = "unit";
		
		for(int i=beginYear ; i<=endYear ; i++){
			String detailTable = machineBillDetail.trim() + i;
			String masterTable = machineBillMaster.trim() + i;
			
			System.out.println("now wss: detailTable = " + detailTable);
			System.out.println("now wss: masterTable = " + masterTable);
			
			String from = " from  " + detailTable  + " a, " 
									+ masterTable + " b, "
									 + billTypeTable + " c, "
									  + hsUnitTable + " d ";

			String sql = selects + from + where + groupBy ;
			
			System.out.println("wss sql = " + sql);
			
			try{
				
				listBillDetailProduct = casDao.findBySql(sql, backTypeMap, objParams);
				if(listBillDetailProduct == null || listBillDetailProduct.size()== 0){
					throw new Exception("查询结果为null 或没有数据");
				}
				listAll.addAll(listBillDetailProduct);
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("可能没有这一年的数据，或是有其它sql查询问题！！！wss");
				continue;
			}
		}
		
		
		List<BillFixing> lsResult = getResultList(listAll);
		//
		// 删除当年度的所有海关帐
		//
		this.casDao.deleteBillFixing();
		
		for (int i = 0; i < lsResult.size(); i++) {
			BillFixing item = (BillFixing) lsResult.get(i);
			item.setCompany(CommonUtils.getCompany());
			this.casDao.getHibernateTemplate().save(item);
		}
		return lsResult;
	}
		
}
