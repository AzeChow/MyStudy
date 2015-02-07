/*
 * Created on 2004-8-31
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ConvertNumberToUpperCase;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.Parame;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomsDeclarationSubReportDataSource extends
		CustomReportDataSource {
	private static List subReportData = null;

	private static List containerData = null;

	private CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
			.getApplicationContext().getBean("customBaseAction");

	private MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
			.getApplicationContext().getBean("materialManageAction");

	private ContractAction contractAction = (ContractAction) CommonVars
			.getApplicationContext().getBean("contractAction");

	private DzscAction dzscAction = (DzscAction) CommonVars
			.getApplicationContext().getBean("dzscAction");
	
	private OtherOption otherOption = CasCommonVars.getOtherOption(); // 其它项目选项
	
	private SystemAction systemAction = (SystemAction) CommonVars.getApplicationContext()
			.getBean("systemAction");

	// private Hashtable gbHash = null;

	/**
	 * @param containerData
	 *            The containerData to set.
	 */
	public static void setContainerData(List containerData) {
		CustomsDeclarationSubReportDataSource.containerData = containerData;
	}
	
	/**
	 * @return Returns the subReportData.
	 */
	public static List getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData
	 *            The subReportData to set.
	 */
	public static void setSubReportData(List subReportData) {
		CustomsDeclarationSubReportDataSource.subReportData = subReportData;
	}

	/**
	 * @param list
	 */
	private CustomsDeclarationSubReportDataSource(List list) {
		super(list);
	}

	public static CustomsDeclarationSubReportDataSource getInstance(
			String CustomsDeclarationID) {
		// list=new ArrayList();
		// int count =subReportData.size();
		// BaseCustomsDeclarationCommInfo commInfo=null;
		// for(int i=0;i<count;i++){
		// commInfo=(BaseCustomsDeclarationCommInfo)subReportData.get(i);
		// if(commInfo.getBaseCustomsDeclaration().getId().equals(CustomsDeclarationID)){
		// list.add(commInfo);
		// }
		// }
		return new CustomsDeclarationSubReportDataSource(subReportData);
	}

	public static CustomsDeclarationSubReportDataSource getInstance() {
		// list=new ArrayList();
		// int count =subReportData.size();
		// BaseCustomsDeclarationCommInfo commInfo=null;
		// for(int i=0;i<count;i++){
		// commInfo=(BaseCustomsDeclarationCommInfo)subReportData.get(i);
		// if(commInfo.getBaseCustomsDeclaration().getId().equals(CustomsDeclarationID)){
		// list.add(commInfo);
		// }
		// }
		return new CustomsDeclarationSubReportDataSource(subReportData);
	}

	public static CustomsDeclarationSubReportDataSource getContainerDataSource(
			String CustomsDeclarationID) {
		return new CustomsDeclarationSubReportDataSource(
				containerData == null ? new ArrayList() : containerData);
	}

	/**
	 * 获得进出口退港申请书商品明细资料
	 * 
	 * @param impExpRequestBillId
	 * @return
	 */
	public static CustomsDeclarationSubReportDataSource getImpExpBackPortRequestBook(
			String impExpRequestBillId) {
		List list = new ArrayList();
		int count = subReportData.size();
		ImpExpCommodityInfo commInfo = null;
		for (int i = 0; i < count; i++) {
			commInfo = (ImpExpCommodityInfo) subReportData.get(i);
			if (commInfo.getImpExpRequestBill().getId().equals(
					impExpRequestBillId)) {
				list.add(commInfo);
			}
		}
		return new CustomsDeclarationSubReportDataSource(list);
	}

	public Integer getRowCount() {
		if (subReportData == null) {
			return new Integer(0);
		} else {
			return new Integer(subReportData.size());
		}
	}

	/**
	 * 取得进出口报关单商品明细的金额总和
	 * 
	 * @return
	 */
	public Double getTotalPrices() {
		BaseCustomsDeclarationCommInfo commInfo = null;
		BaseCustomsDeclaration customsDeclaration = null;
		Double prices = 0.0;
		if (subReportData == null) {
			return prices;
		} else {
			if (subReportData.size() > 0) {
				customsDeclaration = ((BaseCustomsDeclarationCommInfo) subReportData
						.get(0)).getBaseCustomsDeclaration();
				customsDeclaration = (BaseCustomsDeclaration) customBaseAction
						.load(customsDeclaration.getClass(), customsDeclaration
								.getId());
			}
			for (int i = 0; i < subReportData.size(); i++) {
				commInfo = (BaseCustomsDeclarationCommInfo) subReportData
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
			/*
			 * eidt by cjb 2009.11.3 String tramsacName =
			 * (customsDeclaration.getTransac() == null ? "" :
			 * (customsDeclaration.getTransac().getName() == null ? "" :
			 * customsDeclaration.getTransac().getName())); //
			 * freightageType：运费类型 transac:成交方式 Freightage:运费 GrossWeight:毛重 //
			 * 如果成交方式是CIF时 ，系统要减去运费和保费金额 if ("CIF".equals(tramsacName.trim())) {
			 * prices = prices - calcCIF("1", String.valueOf(customsDeclaration
			 * .getFreightageType()), tramsacName,
			 * customsDeclaration.getFreightage(),
			 * customsDeclaration.getGrossWeight(), prices); //
			 * nsuranceType：保险费类型 transac:成交方式 Insurance:保险费 GrossWeight:毛重
			 * prices = prices - calcCIF("3", String.valueOf(customsDeclaration
			 * .getInsuranceType()), tramsacName,
			 * customsDeclaration.getInsurance(),
			 * customsDeclaration.getGrossWeight(), prices); } else { prices =
			 * prices + calcCIF("1", String.valueOf(customsDeclaration
			 * .getFreightageType()), tramsacName,
			 * customsDeclaration.getFreightage(),
			 * customsDeclaration.getGrossWeight(), prices); //
			 * nsuranceType：保险费类型 transac:成交方式 Insurance:保险费 GrossWeight:毛重
			 * prices = prices + calcCIF("3", String.valueOf(customsDeclaration
			 * .getInsuranceType()), tramsacName,
			 * customsDeclaration.getInsurance(),
			 * customsDeclaration.getGrossWeight(), prices); }
			 */
			// 对于有小数位的直接截取取两位小数
			prices = getpices(prices);
			return prices;
		}
	}

	/**
	 * 取得进出口报关单商品明细的金额总和
	 * 
	 * @return
	 */
	public Double getTotalPrices(String type, String date) {
		// throw new RuntimeException("料号未做完整对应关系！请检查！");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date declarationDate = null;
		try {
			declarationDate = format.parse(date);
			System.out.println("转换前declarationDate=" + declarationDate);
			Calendar gc = Calendar.getInstance();
			gc.setTime(declarationDate);
			gc.add(Calendar.DAY_OF_MONTH, 1);
			declarationDate = gc.getTime();
			System.out.println("转换后declarationDate=" + declarationDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("进口tpye=" + type);
		BaseCustomsDeclarationCommInfo commInfo = null;
		BaseCustomsDeclaration customsDeclaration = null;
		Double prices = 0.0;
		if (subReportData == null) {
			return prices;
		} else {
			if (subReportData.size() > 0) {
				customsDeclaration = ((BaseCustomsDeclarationCommInfo) subReportData
						.get(0)).getBaseCustomsDeclaration();
				customsDeclaration = (BaseCustomsDeclaration) customBaseAction
						.load(customsDeclaration.getClass(), customsDeclaration
								.getId());
			}
			for (int i = 0; i < subReportData.size(); i++) {
				commInfo = (BaseCustomsDeclarationCommInfo) subReportData
						.get(i);
				commInfo = (BaseCustomsDeclarationCommInfo) customBaseAction
						.load(commInfo.getClass(), commInfo.getId());
				if (commInfo.getCommTotalPrice() != null) {
					BigDecimal b = new BigDecimal(commInfo.getCommTotalPrice()
							.doubleValue());
					Double prices1 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					// 税费金额＝商品总价
					prices = prices + prices1;

					BigDecimal c = new BigDecimal(prices.doubleValue());
					prices = c.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}
			}
			try {
				BigDecimal freightage = null;// 运费总价
				BigDecimal insurance = null;// 保险费总价

				//币别
				Curr currency = commInfo.getBaseCustomsDeclaration().getCurrency();
				//运费币制
				Curr feeCurr = commInfo.getBaseCustomsDeclaration().getFeeCurr();
				
				// 运费总价
				if (null != feeCurr) {
					String freightageCurr = feeCurr.getCurrSymb().trim();
					String curr = currency.getCurrSymb().trim();
					List list = contractAction
							.findCurrRateByDeclarationDate(new Request(
									CommonVars.getCurrUser()), curr,
									freightageCurr, declarationDate);
					// 本币汇率
					Double currrate = 0.0;
					CurrRate cr = null;
					if(list.size()>0){
						cr = (CurrRate) list.get(list.size() - 1);
						currrate = cr.getRate();
					}
					// 运费率
					if (commInfo.getBaseCustomsDeclaration()
							.getFreightageType() == 1) {
						if (!feeCurr.getCurrSymb().trim().equals(currency.getCurrSymb().trim())) {
							freightage = new BigDecimal(
									(prices* commInfo.getBaseCustomsDeclaration()
											.getFreightage()
											.doubleValue() * currrate) / 100);
						}else{
							freightage = new BigDecimal(
									(prices* commInfo.getBaseCustomsDeclaration()
											.getFreightage()
											.doubleValue()) / 100);
						}
					}
					// 运费单价
					else if (commInfo.getBaseCustomsDeclaration()
							.getFreightageType() == 2) {
						freightage = new BigDecimal(0.0);
					}
					// 运费总价
					else if (commInfo.getBaseCustomsDeclaration()
							.getFreightageType() == 3) {
						if (!feeCurr.getCurrSymb().trim().equals(currency.getCurrSymb().trim())) {
							freightage = new BigDecimal(commInfo
									.getBaseCustomsDeclaration()
									.getFreightage() == null ? 0.0 : commInfo
											.getBaseCustomsDeclaration()
											.getFreightage().doubleValue()
											* currrate);
						}else{
							freightage = new BigDecimal(commInfo
									.getBaseCustomsDeclaration()
									.getFreightage() == null ? 0.0 : commInfo
											.getBaseCustomsDeclaration()
											.getFreightage().doubleValue());
						}
						
					}
				}
				// 保险费总价
				if (null != customsDeclaration.getInsurCurr()) {
						String insuranceCurr = customsDeclaration.getInsurCurr()
								.getCurrSymb().trim();
						String curr = customsDeclaration
								.getCurrency().getCurrSymb().trim();
						List list = contractAction
								.findCurrRateByDeclarationDate(new Request(
										CommonVars.getCurrUser()), curr,
										insuranceCurr, declarationDate);
						// 本币汇率
						Double currrate = 0.0;
						CurrRate cr = null;
						if(list.size()>0){
							cr = (CurrRate) list.get(list.size() - 1);
							currrate = cr.getRate();
						}
						if (customsDeclaration
								.getInsuranceType() == 1) {
							if (!customsDeclaration.getInsurCurr().getCurrSymb().trim()
									.equals(customsDeclaration.getCurrency().getCurrSymb().trim())) {
								
								insurance = new BigDecimal(
										(prices* customsDeclaration.getInsurance()
												.doubleValue() * currrate) / 100);
							}else{
								insurance = new BigDecimal(
										(prices* customsDeclaration.getInsurance()
												.doubleValue()) / 100);
							}
						}
						// 保费总价
						else if (customsDeclaration.getInsuranceType() == 3) {
							if (!customsDeclaration.getInsurCurr().getCurrSymb().trim()
									.equals(customsDeclaration.getCurrency().getCurrSymb().trim())) {
								insurance = new BigDecimal(
										customsDeclaration.getInsurance() == null ? 0.0
												: customsDeclaration.getInsurance().doubleValue()* currrate);
							}else{
								insurance = new BigDecimal(
										customsDeclaration.getInsurance() == null ? 0.0
												: customsDeclaration.getInsurance().doubleValue());
							}
						}
				}
				// BY 石小凯 10.6.30
				Double prices2 = 0.0;
				Double prices3 = 0.0;
				if (freightage == null) {
					prices2 = 0.0;
				} else {
					prices2 = freightage.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}

				if (insurance == null) {
					prices3 = 0.0;
				} else {
					prices3 = insurance.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
				}

				if (type.equals("FOB")) {
					// 税费金额＝商品总价＋运费总价＋保险费总价
					System.out.println("商品总价" + prices);
					System.out.println("运费总价" + prices2);
					System.out.println("保险费总价" + prices3);
					prices = prices + prices2 + prices3;
				} else if (type.equals("C&F")) {
					// 税费金额＝商品总价＋保险费总价
					prices = prices + prices3;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 对于有小数位的直接截取取两位小数
			System.out.println("prices" + prices);
			prices = getpices(prices);
			return prices;
		}
	}

	/**
	 * 取得进出口报关单商品明细的金额总和
	 * 
	 * @return
	 */
	public Double getTotalPrices(String type, String type2, String date) {
		Double prices = 0.0;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date declarationDate = null;
			declarationDate = format.parse(date);
			Calendar gc = Calendar.getInstance();
			gc.setTime(declarationDate);
			gc.add(Calendar.DAY_OF_MONTH, 1);
			declarationDate = gc.getTime();
			BaseCustomsDeclarationCommInfo commInfo = null;
			BaseCustomsDeclaration customsDeclaration = null;
			if (subReportData == null) {
				return prices;
			} else {
				if (subReportData.size() > 0) {
					customsDeclaration = ((BaseCustomsDeclarationCommInfo) subReportData
							.get(0)).getBaseCustomsDeclaration();
				}
				for (int i = 0; i < subReportData.size(); i++) {
					commInfo = (BaseCustomsDeclarationCommInfo) subReportData
							.get(i);
					if (commInfo.getCommTotalPrice() != null) {
						BigDecimal b = new BigDecimal(commInfo.getCommTotalPrice()
								.doubleValue());
						Double prices1 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						// 税费金额＝商品总价
						prices = prices + prices1;
	
						BigDecimal c = new BigDecimal(prices.doubleValue());
						prices = c.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
					}
				}
				BigDecimal freightage = null;
				BigDecimal insurance = null;
				// 运费总价
				if (null != commInfo.getBaseCustomsDeclaration().getFeeCurr()
						&&commInfo.getBaseCustomsDeclaration().getFreightageType()!=null
						&&commInfo.getBaseCustomsDeclaration().getFreightage()!=null) {
					String freightageCurr = commInfo
							.getBaseCustomsDeclaration().getFeeCurr()
							.getCurrSymb().trim();
					String curr = commInfo.getBaseCustomsDeclaration()
							.getCurrency().getCurrSymb().trim();
					List list = contractAction.findCurrRateByDeclarationDate(
							new Request(CommonVars.getCurrUser()), curr,
							freightageCurr, declarationDate);
					// 本币汇率
					Double currrate = 0.0;
					CurrRate cr = null;
					if(list.size()>0){
						cr = (CurrRate) list.get(list.size() - 1);
						currrate = cr.getRate();
					}
					
					// 运费率
					if (commInfo.getBaseCustomsDeclaration()
							.getFreightageType() == 1) {
						if (!commInfo.getBaseCustomsDeclaration().getFeeCurr()
								.getCurrSymb().trim().equals(
										commInfo.getBaseCustomsDeclaration()
										.getCurrency().getCurrSymb().trim())) {
							freightage = new BigDecimal(
									(prices* commInfo.getBaseCustomsDeclaration()
											.getFreightage().doubleValue() * currrate) / 100);
						}else{
							freightage = new BigDecimal(
									(prices* commInfo.getBaseCustomsDeclaration()
											.getFreightage().doubleValue()) / 100);
						}
					}
					// 运费单价
					else if (commInfo.getBaseCustomsDeclaration()
							.getFreightageType() == 2) {
						freightage = new BigDecimal(0.0);
					}
					// 运费总价
					else if (commInfo.getBaseCustomsDeclaration()
							.getFreightageType() == 3) {
						if (!commInfo.getBaseCustomsDeclaration().getFeeCurr()
								.getCurrSymb().trim().equals(
										commInfo.getBaseCustomsDeclaration()
										.getCurrency().getCurrSymb().trim())) {
							freightage = new BigDecimal(
									commInfo.getBaseCustomsDeclaration()
									.getFreightage() == null ? 0.0
											: commInfo.getBaseCustomsDeclaration()
											.getFreightage().doubleValue()
											* currrate);
						}else{
							freightage = new BigDecimal(
									commInfo.getBaseCustomsDeclaration()
									.getFreightage() == null ? 0.0
											: commInfo.getBaseCustomsDeclaration()
											.getFreightage().doubleValue());
						}
					}
				}
					
	
				// 保险费总价
				if (null != commInfo.getBaseCustomsDeclaration().getInsurCurr()
						&&commInfo.getBaseCustomsDeclaration().getInsuranceType()!=null
						&&commInfo.getBaseCustomsDeclaration().getInsurance()!=null) {
					String insuranceCurr = commInfo.getBaseCustomsDeclaration()
							.getInsurCurr().getCurrSymb().trim();
					String curr = commInfo.getBaseCustomsDeclaration()
							.getCurrency().getCurrSymb().trim();
					List list = contractAction.findCurrRateByDeclarationDate(
							new Request(CommonVars.getCurrUser()), curr,
							insuranceCurr, declarationDate);
					// 本币汇率
					Double currrate = 0.0;
					CurrRate cr = null;
					if(list.size()>0){
						cr = (CurrRate) list.get(list.size() - 1);
						currrate = cr.getRate();
					}
					
					if (commInfo.getBaseCustomsDeclaration().getInsuranceType() == 1) {
						if (!commInfo.getBaseCustomsDeclaration().getInsurCurr()
								.getCurrSymb().trim().equals(
										commInfo.getBaseCustomsDeclaration()
												.getCurrency().getCurrSymb().trim())) {
							insurance = new BigDecimal((prices* commInfo.getBaseCustomsDeclaration()
									.getInsurance().doubleValue() * currrate) / 100);
						}else{
							insurance = new BigDecimal((prices* commInfo.getBaseCustomsDeclaration()
									.getInsurance().doubleValue()) / 100);
						}
					}else if (commInfo.getBaseCustomsDeclaration()
							.getInsuranceType() == 3) {
						if (!commInfo.getBaseCustomsDeclaration().getInsurCurr()
								.getCurrSymb().trim().equals(
										commInfo.getBaseCustomsDeclaration()
												.getCurrency().getCurrSymb().trim())) {
							insurance = new BigDecimal(
									commInfo.getBaseCustomsDeclaration()
									.getInsurance() == null ? 0.0
											: commInfo.getBaseCustomsDeclaration()
											.getInsurance().doubleValue()
											* currrate);
						}else{
							insurance = new BigDecimal(
									commInfo.getBaseCustomsDeclaration()
									.getInsurance() == null ? 0.0
											: commInfo.getBaseCustomsDeclaration()
											.getInsurance().doubleValue());
						}
					}
				}
				// BY 石小凯 10.6.30
				Double prices2 = 0.0;
				Double prices3 = 0.0;
				if (type.equals("CIF")) {
					// 运费
					try {
						if (freightage == null) {
							prices2 = 0.0;
						} else {
							prices2 = freightage.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue();
						}
						if (insurance == null) {
							prices3 = 0.0;
						} else {
							prices3 = insurance.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue();
						}
						// 税费金额＝商品总价＋运费总价＋保险费总价
						prices = prices - prices2 - prices3;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if (type.equals("C&F")) {
					System.out.println("进C&F");
					if (insurance == null) {
						prices3 = 0.0;
					} else {
						prices3 = insurance.setScale(2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
					}
					// 税费金额＝商品总价＋保险费总价
					prices = prices - prices3;
				}
				// 对于有小数位的直接截取取两位小数
				prices = getpices(prices);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prices;
	}

	public Double getpices(Double prices) {
		// String s = ".";
		// String str = String.valueOf(prices);
		// Double xx = 0.0;
		// if (str.indexOf(s) > 0) {
		// Integer h = str.indexOf(s);
		// String bb = str.substring(h + 1, str.length());
		// if (bb.length() > 2) {
		// xx = Double.parseDouble(str.substring(0, h) + "."
		// + bb.substring(0, 2));
		// } else {
		// xx = prices;
		// }
		// }
		// return xx;
		BigDecimal b = new BigDecimal(prices);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private Double calcCIF(String calcType, String dataType, String CJFS,
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
		if (dataType.equals("")) {
			return new Double(0);
		}
		if (calcType.equals("1")) {
			if (dataType.equals("1")) {
				return (FY / 100) * ZJE;
			} else if (dataType.equals("2")) {
				return (ZL / 1000) * FY;
			} else if (dataType.equals("3")) {
				return FY;
			}
		} else {
			if (dataType.equals("1")) {
				if (CJFS.equals("C&F")) {
					return ZJE / (1 - FY / 100) - ZJE; // (FOB+运费) / (1-保险费率)
				} else {
					return ZJE / (1 - FY / 100) - ZJE;// 算法改为同上。Jack/2004-9-7//(FY
					// / 100) * ZJE;
				}
			} else if (dataType.equals("3")) {
				return FY;
			}
		}
		return new Double(0);
	}

	private Double formatDouble(Double value) {
		if (value == null) {
			return new Double(0);
		}
		return value;
	}

	/**
	 * 取得进出口报关单商品明细的 原产国 or 最终目的产国
	 * 
	 * @return
	 */
	public String getSourceOrDestCountry() {
		BaseCustomsDeclarationCommInfo commInfo = null;
		List temp = new ArrayList();
		String str = "";
		StringBuffer sb = new StringBuffer("");
		if (subReportData == null) {
			return "";
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				commInfo = (BaseCustomsDeclarationCommInfo) subReportData
						.get(i);
				if (commInfo.getCountry() != null) {
					str = commInfo.getCountry().getName();
					if (!temp.contains(str)) {
						temp.add(str);
					}
				}
			}
			for (int i = 0; i < temp.size(); i++) {
				if (i != temp.size() - 1) {
					sb.append(temp.get(i).toString() + ",");
				} else {
					sb.append(temp.get(i).toString());
				}
			}
			return sb.toString();
		}
	}

	/**
	 * 获得进出口报关单商品明细的金额总和的中文大写
	 */
	public String getTotalPricesByUpperCase() {
		return ConvertNumberToUpperCase.convert(String.valueOf(this
				.getTotalPrices().longValue()));
	}

	public Double getTotalPricesInvoice() {
		return this.getTotalPrices();
	}

	/**
	 * 获得进出口报关单总商品数量
	 */
	public String getTotalCommodityNumByUpperCase() {
		return ConvertNumberToUpperCase.convert(String.valueOf(this
				.getTotalPrices().longValue()));
	}

	/**
	 * 获得指定页面记录数的最大页数
	 */
	public int getPageSize(int pageRecords) {
		int pageSize = -1;
		if (subReportData.size() == 0) {
			pageSize = 1;
		} else {
			int temp = subReportData.size();
			pageSize = temp / pageRecords + (temp % pageRecords > 0 ? 1 : 0);
		}
		return pageSize;
	}

	/**
	 * 取得进出口报关单商品明细的毛重总和
	 * 
	 * @return
	 */
	public Double getTotalGrossWeight() {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double grossWeight = 0;
		if (subReportData == null) {
			return new Double(0);
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				commInfo = (BaseCustomsDeclarationCommInfo) subReportData
						.get(i);
				if (commInfo.getGrossWeight() != null) {
					grossWeight += commInfo.getGrossWeight().doubleValue();
				}
			}
			return new Double(CommonVars.getDoubleByDigit(grossWeight, 6));
		}
	}

	/**
	 * 取得进出口报关单商品明细的净重总和
	 * 
	 * @return
	 */
	public Double getTotalNetWeight() {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double netWeight = 0;
		if (subReportData == null) {
			return new Double(0);
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				commInfo = (BaseCustomsDeclarationCommInfo) subReportData
						.get(i);
				if (commInfo.getNetWeight() != null) {
					netWeight += commInfo.getNetWeight().doubleValue();
				}
			}
			return new Double(CommonVars.getDoubleByDigit(netWeight, 6));
		}
	}

	/**
	 * 获取所有出口报关单集装箱编号：（如果是冷藏柜，要注明）
	 */
	public String getAllContainerCode() {
		StringBuffer allContainerCode = new StringBuffer("");
		Set set = new HashSet();
		HashMap map = new HashMap();
		BaseCustomsDeclaration customsDeclaration;
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			customsDeclaration = commInfo.getBaseCustomsDeclaration();
			if (map.get(customsDeclaration.getConveyance()) == null)
				map.put(customsDeclaration.getConveyance(), customsDeclaration
						.getConveyance());
		}
		List lsContainers = new ArrayList();
		int count = containerData.size();
		System.out.println("count------------" + count);
		BaseCustomsDeclarationContainer container = null;
		for (int j = 0; j < count; j++) {
			container = (BaseCustomsDeclarationContainer) containerData.get(j);
			if (map.get(container.getBaseCustomsDeclaration().getConveyance()) != null) {
				lsContainers.add(container);
			}
			System.out.println("lsContainers.size()------------"
					+ lsContainers.size());
			set.addAll(lsContainers);
		}

		List tempList = new ArrayList();
		if (!set.isEmpty()) {
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				BaseCustomsDeclarationContainer customsDeclarationContainer = (BaseCustomsDeclarationContainer) iterator
						.next();
				System.out.println("customsDeclarationContainer------------"
						+ customsDeclarationContainer);
				String temp = customsDeclarationContainer.getContainerCode()
						.trim();
				if (tempList.contains(temp)) {
					continue;
				}
				tempList.add(temp);
				if (customsDeclarationContainer.getSrtJzx() != null) {
					String srtUsing = customsDeclarationContainer.getSrtJzx()
							.getSrtUsing().trim();// 特征
					String name = customsDeclarationContainer.getSrtJzx()
							.getName().trim();// 尺寸
					System.out.println("--name:" + name);
					System.out.println("--srtUsing:" + srtUsing);
					String attachSuffix = srtUsing.substring(
							srtUsing.length() - 3, srtUsing.length() - 1);
					System.out.println("--attachSuffix:" + attachSuffix);
					temp += "/" + name.substring(0, 2);// 取尺寸的大小
					if (attachSuffix.equals("冻柜") || attachSuffix.equals("冷藏柜")) {
						temp += "[冷藏柜]";
					}
				}
				temp += ";";
				System.out.println("--temp2:" + temp);
				allContainerCode.append(temp);
			}
		}
		System.out.println("--allContainerCode.toString()"
				+ allContainerCode.toString());
		return allContainerCode.toString();
	}

	/**
	 * 取得出口报关单商品明细的当前页的金额总和
	 * 
	 * @return
	 */
	public Double getTotalPricesByCurrentPage(int groupPage, int pageRecordCount) {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double prices = 0;
		if (subReportData == null) {
			return new Double(0);
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				if (i >= ((groupPage - 1) * pageRecordCount)
						&& i < (groupPage * pageRecordCount)) {
					commInfo = (BaseCustomsDeclarationCommInfo) subReportData
							.get(i);
					if (commInfo.getCommTotalPrice() != null) {
						prices += commInfo.getCommTotalPrice().doubleValue();
					}
				}
			}
			return new Double(prices);
		}
	}

	/**
	 * 取得出口报关单商品明细的当前页的数量总和
	 * 
	 * @return
	 */
	public Double getTotalCommAmountByCurrentPage(int groupPage,
			int pageRecordCount) {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double prices = 0;
		if (subReportData == null) {
			return new Double(0);
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				if (i >= ((groupPage - 1) * pageRecordCount)
						&& i < (groupPage * pageRecordCount)) {
					commInfo = (BaseCustomsDeclarationCommInfo) subReportData
							.get(i);
					if (commInfo.getPieces() != null) {
						prices += commInfo.getPieces();
					}
				}
			}
			return new Double(prices);
		}
	}
	/**
	 * 取得出口报关单商品明细的当前页的数量总和2
	 * 
	 * @return
	 */
	public String getTotalCommAmountByCurrentPage2(int groupPage,
			int pageRecordCount) {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double prices = 0;
		if (subReportData == null) {
			return "";
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				if (i >= ((groupPage - 1) * pageRecordCount)
						&& i < (groupPage * pageRecordCount)) {
					commInfo = (BaseCustomsDeclarationCommInfo) subReportData
							.get(i);
					if (commInfo.getCommAmount() != null) {
						prices += commInfo.getCommAmount();
					}
				}
			}
			
			return CommonUtils.formatDoubleByDigit(prices, 9);
		}
	}

	public String[] getCommNameforReport(String id) {
		CompanyOther companyOther = (CompanyOther) systemAction.findCompanyOther(new Request(CommonVars.getCurrUser())).get(0);
		Boolean isSpecification = companyOther.getIsSpecification();
		String[] nameSpec = new String[2];
		nameSpec[0] = "";
		nameSpec[1] = "";
		BaseCustomsDeclarationCommInfo commInfo = null;
		if (subReportData != null) {
			for (int i = 0; i < subReportData.size(); i++) {
				commInfo = (BaseCustomsDeclarationCommInfo) subReportData
						.get(i);
				if (id.equals(commInfo.getId())) {
					nameSpec[0] = commInfo.getCommName() == null ? ""
							: commInfo.getCommName();
					if(isSpecification!=null&&isSpecification){
						nameSpec[1] = commInfo.getDeclareSpec() == null ? ""
								: commInfo.getDeclareSpec();
					}else{
						nameSpec[1] = commInfo.getCommSpec() == null ? ""
								: commInfo.getCommSpec();
					}
					int len =13;
					if (nameSpec[1].length() > len) {
						len = val(nameSpec[1], len);
						nameSpec[1] = nameSpec[1].substring(0, len)
								+ "\n"
								+ nameSpec[1].substring(len, nameSpec[1]
										.length());
					}
					System.out.println("nameSpec[1]=" + nameSpec[1]);
					return nameSpec;
				}
			}
		}
		return nameSpec;
	}

	private int val(String nameSpec, int len) {
		if (len < nameSpec.length()) {
			String str = nameSpec.substring(0, len);
//			System.out.println("=222=="+str.getBytes().length+"===111="+str.length()+" len="+len+" str=="+str);
			if (str.getBytes().length == str.length()) {// 无汉字，一个字节
				if(len==25){
					return len;
				}
				len = val(nameSpec, len + 1);
			}else{
				len = val(nameSpec, len + 1);
			}
		}
		return len;
	}

	public Double getTotalCommMoney() {
		if (CommonVars.getSumMoney() == null
				|| CommonVars.getSumMoney().equals(Double.valueOf(0))) {
			BaseCustomsDeclarationCommInfo commInfo = null;
			double money = 0;
			if (subReportData == null) {
				return new Double(0);
			} else {
				for (int i = 0; i < subReportData.size(); i++) {
					commInfo = (BaseCustomsDeclarationCommInfo) subReportData
							.get(i);
					if (commInfo.getCommTotalPrice() != null) {
						money += commInfo.getCommTotalPrice();
					}
				}
			}
			CommonVars.setSumMoney(Double.valueOf(money));
			return new Double(money);
		} else {
			return CommonVars.getSumMoney();
		}
	}

	/**
	 * 取得出口报关单商品明细的当前页的毛重总和
	 * 
	 * @return
	 */
	public Double getTotalGrossWeightByCurrentPage(int groupPage,
			int pageRecordCount) {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double prices = 0;
		if (subReportData == null) {
			return new Double(0);
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				if (i >= ((groupPage - 1) * pageRecordCount)
						&& i < (groupPage * pageRecordCount)) {
					commInfo = (BaseCustomsDeclarationCommInfo) subReportData
							.get(i);
					if (commInfo.getGrossWeight() != null) {
						prices += commInfo.getGrossWeight().doubleValue();
					}
				}
			}
			return new Double(prices);
		}
	}

	/**
	 * 取得出口报关单商品明细的当前页的净重总和
	 * 
	 * @return
	 */
	public Double getTotalNetWeightByCurrentPage(int groupPage,
			int pageRecordCount) {
		BaseCustomsDeclarationCommInfo commInfo = null;
		double prices = 0;
		if (subReportData == null) {
			return new Double(0);
		} else {
			for (int i = 0; i < subReportData.size(); i++) {
				if (i >= ((groupPage - 1) * pageRecordCount)
						&& i < (groupPage * pageRecordCount)) {
					commInfo = (BaseCustomsDeclarationCommInfo) subReportData
							.get(i);
					if (commInfo.getNetWeight() != null) {
						prices += commInfo.getNetWeight().doubleValue();
					}
				}
			}
			return new Double(prices);
		}
	}

	public String getCountTry() {
		BaseCustomsDeclarationCommInfo commInfo = null;
		if (subReportData != null && subReportData.size() > 0) {
			commInfo = (BaseCustomsDeclarationCommInfo) subReportData.get(0);
			return commInfo.getCountry() == null ? "" : commInfo.getCountry()
					.getName();
		} else {
			return "";
		}
	}

	/**
	 * 取得出口报关单商品明细的当前页的金额总和的中文大写
	 */
	public String getCurrentPageTotalPricesByUpperCase(int groupPage,
			int pageRecordCount) {
		/*
		 * return ConvertNumberToUpperCase
		 * .convert(String.valueOf(getTotalPricesByCurrentPage(groupPage,
		 * pageRecordCount).longValue()));
		 */
		return ConvertNumberToUpperCase.convertd(formatBig(
				String.valueOf(getTotalPricesByCurrentPage(groupPage,
						pageRecordCount)), 2, true));
	}

	/**
	 * 序号格式化
	 */
	public String formatSeqnum(String seqNum, int lengths) {
		if (seqNum == null && seqNum.equals("")) {
			return null;
		}
		int sLength = seqNum.length();
		for (int i = 0; i < lengths - sLength; i++) {
			seqNum = "0" + seqNum;
		}
		return seqNum;
	}
	
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
	
	public String formatBig(String amount, String bigD, boolean isZero) {
		return formatBig(amount, Integer.valueOf(bigD),  isZero);
	}
	
	/**
	 * 海关张所有工厂资料报表小数位数控制
	 * @param damount
	 * @param isZero
	 * @return
	 */
	public String formatBigAllReportInOutMaximumFractionDigits(Double damount, boolean isZero) {
		String  amount = damount.toString();
		Integer bigD = otherOption.getAllReportInOutMaximumFractionDigits() == null ? 2
				: otherOption.getAllReportInOutMaximumFractionDigits();
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

	public String changeImpexpType(String type) {
		String str = "";
		if (type == null || type.equals("")) {
			return str;
		}
		if (type.equals("0")) {
			return "料件进口";
		} else if (type.equals("1")) {
			return "料件转厂";
		} else if (type.equals("6")) {
			return "退料出口";
		} else if (type.equals("4")) {
			return "成品出口";
		} else if (type.equals("5")) {
			return "转厂出口";
		} else if (type.equals("2")) {
			return "退厂返工";
		} else if (type.equals("7")) {
			return "返工复出";
		}
		return str;
	}

	// 得到合同加工费与原料费
	public Double[] getMachiningMoney(String contractNo, String seqNum,
			String num, String projectType) {
		Double[] d = new Double[4];
		Double num1 = Double.valueOf(0);
		if (String.valueOf(ProjectType.BCS).equals(projectType)) {
			List list = contractAction.findContractExgBySeqNum(new Request(
					CommonVars.getCurrUser()), contractNo, seqNum);
			if (list != null && list.size() > 0) {
				d[0] = ((ContractExg) list.get(0)).getProcessUnitPrice() == null ? Double
						.valueOf(0)
						: ((ContractExg) list.get(0)).getProcessUnitPrice();
				d[1] = ((ContractExg) list.get(0)).getMaterialFee() == null ? Double
						.valueOf(0)
						: ((ContractExg) list.get(0)).getMaterialFee();
				if (num == null || num.equals("")) {
					num1 = Double.valueOf(0);
				} else {
					num1 = Double.valueOf(num);
				}
				d[2] = d[0] * num1;
				d[3] = d[1] * num1;
			} else {
				d[0] = Double.valueOf(0);
				d[1] = Double.valueOf(0);
				d[2] = Double.valueOf(0);
				d[3] = Double.valueOf(0);
			}
		} else if (String.valueOf(ProjectType.DZSC).equals(projectType)) {
			List list = contractAction.findDzscEmsExgBillBySeqNum(new Request(
					CommonVars.getCurrUser()), contractNo, seqNum);
			if (list != null && list.size() > 0) {
				d[0] = ((DzscEmsExgBill) list.get(0)).getMachinPrice() == null ? Double
						.valueOf(0)
						: ((DzscEmsExgBill) list.get(0)).getMachinPrice();
				d[1] = ((DzscEmsExgBill) list.get(0)).getImgMoney() == null ? Double
						.valueOf(0)
						: ((DzscEmsExgBill) list.get(0)).getImgMoney();
				if (num == null || num.equals("")) {
					num1 = Double.valueOf(0);
				} else {
					num1 = Double.valueOf(num);
				}
				d[2] = d[0] * num1;
				d[3] = d[1] * num1;
			} else {
				d[0] = Double.valueOf(0);
				d[1] = Double.valueOf(0);
				d[2] = Double.valueOf(0);
				d[3] = Double.valueOf(0);
			}
		}
		CommonVars.setSumMoney(CommonVars.getSumMoney() + d[2] + d[3]);
		return d;
	}

	// 得到ECS加工费与原料费
	public Double[] getEcsMachiningMoney(String contractNo, String seqNum,
			String num) {
		Double[] d = new Double[4];
		Double num1 = Double.valueOf(0);
		List list = dzscAction.findDzscEmsExgBillBySeqNum(new Request(
				CommonVars.getCurrUser()), contractNo, seqNum);
		if (list != null && list.size() > 0) {
			d[0] = ((DzscEmsExgBill) list.get(0)).getMachinPrice() == null ? Double
					.valueOf(0)
					: ((DzscEmsExgBill) list.get(0)).getMachinPrice();
			d[1] = ((DzscEmsExgBill) list.get(0)).getImgMoney() == null ? Double
					.valueOf(0)
					: ((DzscEmsExgBill) list.get(0)).getImgMoney();
			if (num == null || num.equals("")) {
				num1 = Double.valueOf(0);
			} else {
				num1 = Double.valueOf(num);
			}
			d[2] = d[0] * num1;
			d[3] = d[1] * num1;
		} else {
			d[0] = Double.valueOf(0);
			d[1] = Double.valueOf(0);
			d[2] = Double.valueOf(0);
			d[3] = Double.valueOf(0);
		}
		CommonVars.setSumMoney(CommonVars.getSumMoney() + d[2] + d[3]);
		return d;
	}

	public Double getSumMoney() {
		Double result = (CommonVars.getSumMoney() / 2)
				- CommonVars.getBeforeSumMoney();
		CommonVars.setBeforeSumMoney(result);
		return result;
	}

	public String getjizhuangxiangByType(String beginValue) {
		return materialManageAction.findParaSetBytype(new Request(CommonVars
				.getCurrUser()), Parame.jizhuangxiangcode, beginValue);
	}

	public String getconveyance(String conveyance) {
		if (conveyance == null || "".equals(conveyance)) {
			return "";
		}
		return conveyance.substring(1, conveyance.length());
	}

	// private Hashtable getHs() {
	// if (gbHash == null) {
	// gbHash = new Hashtable();
	// List list = CustomBaseList.getInstance().getGbtobigs();
	// for (int i = 0; i < list.size(); i++) {
	// Gbtobig gb = (Gbtobig) list.get(i);
	// gbHash.put(gb.getBigname(), gb.getName());
	// // gbHash.put(gb.getName(),gb.getBigname());
	// }
	// }
	// return gbHash;
	// }

	public String CS(String s) { // 繁转简
		// String yy = "";
		// char[] xxx = s.toCharArray();
		// for (int i = 0; i < xxx.length; i++) {
		// String z = String.valueOf(xxx[i]);
		// if (String.valueOf(xxx[i]).getBytes().length == 2) {
		// if (getHs().get(String.valueOf(xxx[i])) != null) {
		// z = (String) getHs().get(String.valueOf(xxx[i]));
		// }
		// }
		// yy = yy + z;
		// }
		// return yy;
		return CommonClientBig5GBConverter.getInstance().big5ConvertToGB(s);
	}

	/*
	 * public static String cut(String str, int bytesCount){ String xx = ""; if
	 * (str == null || str.equals("")){ return str; } byte[] bytes =
	 * str.getBytes(); char[] chars = new String(bytes, 0,
	 * bytesCount).toCharArray(); char[] charsPlus = new String(bytes, 0,
	 * bytesCount + 1).toCharArray(); if (chars.length == charsPlus.length){ xx
	 * = new String(bytes, 0, bytesCount - 1); } else { xx = new String(bytes,
	 * 0, bytesCount); } String mStr = ""; for(int i=0;i<255;i++){ mStr = mStr +
	 * " "; } return new String((xx+mStr).getBytes(),0,bytesCount); }
	 * 
	 * 
	 * //截取字符串 public static String cutStr(String Value,int bytesCount) { if
	 * (CommonVars.isCompany("明门幼童") || CommonVars.isCompany("宝钜儿童用品")){ return
	 * Value; } String mStr = ""; for(int i=0;i<255;i++){ mStr = mStr + " "; }
	 * String result = cut(Value+mStr,bytesCount); return result; }
	 */

	/**
	 * 获取用到给定的"运输工具"的所有报关单头中的合同号栏位
	 * 
	 * @param conveyance
	 *            "运输工具"
	 * @param projectType
	 *            类型
	 * @return
	 */
	public String getAllContractNumInCustomsDeclaration() {
		StringBuffer contractNum = new StringBuffer("");
		HashMap map = new HashMap();

		if (subReportData.isEmpty())
			return contractNum.toString();

		BaseCustomsDeclaration baseCustomsDeclaration;
		String key = "";
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			baseCustomsDeclaration = commInfo.getBaseCustomsDeclaration();
			if (baseCustomsDeclaration.getEmsHeadH2k() != null
					|| !baseCustomsDeclaration.getEmsHeadH2k().trim()
							.equals("")) {
				key = baseCustomsDeclaration.getEmsHeadH2k();
				if (map.get(key) == null) {
					map.put(key, key);
					contractNum.append(key + ",");
				}
			}
		}
		if (contractNum.length() > 0)
			contractNum.delete(contractNum.length() - 1, contractNum.length());
		return contractNum.toString();
	}

	/**
	 * 获取用到给定的"运输工具"的所有报关单头中的贸易方式栏位
	 * 
	 * @param conveyance
	 *            "运输工具"
	 * @param projectType
	 *            类型
	 * @return
	 */
	public String getAllTradeModeInCustomsDeclaration() {
		StringBuffer contractNum = new StringBuffer("");
		HashMap map = new HashMap();

		if (subReportData.isEmpty())
			return contractNum.toString();

		BaseCustomsDeclaration baseCustomsDeclaration;
		String key = "";
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			baseCustomsDeclaration = commInfo.getBaseCustomsDeclaration();
			if (baseCustomsDeclaration.getTradeMode() != null
					|| !baseCustomsDeclaration.getTradeMode().getName().trim()
							.equals("")) {
				key = baseCustomsDeclaration.getTradeMode().getName();
				if (map.get(key) == null) {
					map.put(key, key);
					contractNum.append(key + "/");
				}
			}
		}
		if (contractNum.length() > 0)
			contractNum.delete(contractNum.length() - 1, contractNum.length());
		return contractNum.toString();
	}

	/**
	 * 获取用到给定的"运输工具"的所有报关单头中的合同协议号栏位
	 * 
	 * @param conveyance
	 *            "运输工具"
	 * @param projectType
	 *            类型
	 * @return
	 */
	public String getAllContractNoInCustomsDeclaration() {
		StringBuffer contractNum = new StringBuffer("");
		HashMap map = new HashMap();

		if (subReportData.isEmpty())
			return contractNum.toString();

		BaseCustomsDeclaration baseCustomsDeclaration;
		String key = "";
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			baseCustomsDeclaration = commInfo.getBaseCustomsDeclaration();
			if (baseCustomsDeclaration.getContract() != null
					|| !baseCustomsDeclaration.getContract().trim().equals("")) {
				key = baseCustomsDeclaration.getContract();
				if (map.get(key) == null) {
					map.put(key, key);
					contractNum.append(key + ",");
				}
			}
		}
		if (contractNum.length() > 0)
			contractNum.delete(contractNum.length() - 1, contractNum.length());
		System.out.println("contractNum=" + contractNum);
		return contractNum.toString();
	}

	/**
	 * 获取用到给定的"运输工具"的所有报关单体中的"原产国/最终目的国"的名字
	 * 
	 * @param conveyance
	 *            "运输工具"
	 * @param projectType
	 *            类型
	 * @return
	 */
	public String getAllCountryNameInCustomsDeclarationCommInfo() {
		StringBuffer countryName = new StringBuffer("");
		HashMap map = new HashMap();

		if (subReportData.isEmpty())
			return countryName.toString();

		BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo;
		String key = "";
		for (int i = 0; i < subReportData.size(); i++) {
			baseCustomsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			if (baseCustomsDeclarationCommInfo.getCountry() != null) {
				key = baseCustomsDeclarationCommInfo.getCountry().getName()
						.trim();
				if (map.get(key) == null) {
					map.put(key, key);
					countryName.append(key + "/");
				}
			}
		}
		if (countryName.length() > 0)
			countryName.delete(countryName.length() - 1, countryName.length());
		return countryName.toString();
	}

	/**
	 * 获取用到给定的"运输工具"的所有报关单头和集团公司资料, 进口用"集团公司的外商公司名称",出口用"报关单头的收货单位名称"
	 * 
	 * @param conveyance
	 *            "运输工具"
	 * @param projectType
	 *            类型
	 * @return
	 */
	public String getAllInfoInCustomsDeclaration(String impExpFlag) {
		StringBuffer companyInfo = new StringBuffer("");
		HashMap map = new HashMap();

		if (subReportData.isEmpty())
			return companyInfo.toString();

		BaseCustomsDeclaration baseCustomsDeclaration;
		String key = "";
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			baseCustomsDeclaration = commInfo.getBaseCustomsDeclaration();
			if (baseCustomsDeclaration.getCompany() != null) {
				if (Integer.valueOf(impExpFlag) == 0) {// 0表示进口
					key = ((Company) baseCustomsDeclaration.getCompany())
							.getOutTradeCo() == null ? ""
							: ((Company) baseCustomsDeclaration.getCompany())
									.getOutTradeCo();
				} else {// 出口
					key = baseCustomsDeclaration.getMachName() == null ? ""
							: baseCustomsDeclaration.getMachName();
				}
				if (map.get(key) == null) {
					map.put(key, key);
					companyInfo.append(key + ",");
				}
			}
		}
		if (companyInfo.length() > 0)
			companyInfo.delete(companyInfo.length() - 1, companyInfo.length());
		return companyInfo.toString();
	}

	/**
	 * 获取用到给定的"运输工具"的所有报关单头和集团公司资料
	 * 
	 * @param conveyance
	 *            "运输工具"
	 * @param projectType
	 *            类型
	 * @return
	 */
	public String[][] getSendCompany(String impExpFlag) {
		String[][] sendCompany = { { "", "" }, { "", "" }, { "", "" } };// 0栏位放的是名称资料,1栏位放的是地址;进口时取的是报关单头的客户名称和地址,出口时取的是集团公司的加工单位名称和地址
		HashMap sendMap = new HashMap();

		if (subReportData.isEmpty())
			return sendCompany;

		BaseCustomsDeclaration baseCustomsDeclaration;
		String sendKey = "";
		int j = 0;
		// System.out.println("===subRe="+subReportData.size());
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			baseCustomsDeclaration = commInfo.getBaseCustomsDeclaration();
			if (Integer.valueOf(impExpFlag) == 0) {// 0表示进口
				// 进口时取的是报关单头的客户名称和地址
				if (baseCustomsDeclaration.getCustomer() != null) {
					if (j < 3) {
						sendKey = baseCustomsDeclaration.getCustomer().getId();
						if (sendMap.get(sendKey) == null) {
							sendMap.put(sendKey, sendKey);
							sendCompany[j][0] = baseCustomsDeclaration
									.getCustomer().getName() == null ? ""
									: baseCustomsDeclaration.getCustomer()
											.getName();
							sendCompany[j][1] = baseCustomsDeclaration
									.getCustomer().getAddre() == null ? ""
									: ("("
											+ baseCustomsDeclaration
													.getCustomer().getAddre() + ")");
							j++;
						}
					}
				}
			} else {// 出口
				// 出口时取的是报关单头的客户名称和地址
				if (baseCustomsDeclaration.getCompany() != null) {
					sendKey = baseCustomsDeclaration.getCompany().getId();
					if (j < 3) {
						if (sendMap.get(sendKey) == null) {
							sendMap.put(sendKey, sendKey);
							sendCompany[j][0] = ((Company) baseCustomsDeclaration
									.getCompany()).getName() == null ? ""
									: ((Company) baseCustomsDeclaration
											.getCompany()).getName();
							sendCompany[j][1] = ((Company) baseCustomsDeclaration
									.getCompany()).getAddress() == null ? ""
									: ("("
											+ ((Company) baseCustomsDeclaration
													.getCompany()).getAddress() + ")");
							j++;
						}
					}
				}
			}
		}
		// System.out.println("===sendCompany="+sendCompany);
		return sendCompany;
	}

	/**
	 * 获取用到给定的"运输工具"的所有报关单头和集团公司资料
	 * 
	 * @param conveyance
	 *            "运输工具"
	 * @param projectType
	 *            类型
	 * @return
	 */
	public String[][] getAcceptCompany(String impExpFlag) {
		String[][] acceptCompany = { { "", "" }, { "", "" }, { "", "" } };// //0栏位放的是名称资料,1栏位放的是地址;进口时取的是集团公司的加工单位名称和地址,出口时取的是报关单头的客户名称和地址
		HashMap acceptMap = new HashMap();

		if (subReportData.isEmpty())
			return acceptCompany;

		BaseCustomsDeclaration baseCustomsDeclaration;
		String acceptKey = "";
		int j = 0;
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			baseCustomsDeclaration = commInfo.getBaseCustomsDeclaration();
			if (Integer.valueOf(impExpFlag) == 0) {// 0表示进口
				// 进口时取的是集团公司的加工单位名称和地址
				if (baseCustomsDeclaration.getCompany() != null) {
					acceptKey = baseCustomsDeclaration.getCompany().getId();
					if (j < 3) {
						if (acceptMap.get(acceptKey) == null) {
							acceptMap.put(acceptKey, acceptKey);
							acceptCompany[j][0] = ((Company) baseCustomsDeclaration
									.getCompany()).getName() == null ? ""
									: ((Company) baseCustomsDeclaration
											.getCompany()).getName();
							acceptCompany[j][1] = ((Company) baseCustomsDeclaration
									.getCompany()).getAddress() == null ? ""
									: ("("
											+ ((Company) baseCustomsDeclaration
													.getCompany()).getAddress() + ")");
							j++;
						}
					}
				}

			} else {// 出口
				// 出口时取的是集团公司的加工单位名称和地址
				if (baseCustomsDeclaration.getCustomer() != null) {
					if (j < 3) {
						acceptKey = baseCustomsDeclaration.getCustomer()
								.getId();
						if (acceptMap.get(acceptKey) == null) {
							acceptMap.put(acceptKey, acceptKey);
							acceptCompany[j][0] = baseCustomsDeclaration
									.getCustomer().getName() == null ? ""
									: baseCustomsDeclaration.getCustomer()
											.getName();
							acceptCompany[j][1] = baseCustomsDeclaration
									.getCustomer().getAddre() == null ? ""
									: ("("
											+ baseCustomsDeclaration
													.getCustomer().getAddre() + ")");
							j++;
						}
					}
				}
			}
		}
		return acceptCompany;
	}

	/**
	 * 获取用到给定的"运输工具"的所有报关单头中不同的收货单位和送货单位名称
	 * 
	 * @return
	 */
	public String getMachName() {
		String machName = "";// 进口时取报关单头的收获单位名称,出口时取报关单头的送获单位名称
		HashMap machMap = new HashMap();

		if (subReportData.isEmpty())
			return machName;

		BaseCustomsDeclaration baseCustomsDeclaration;
		String machKey = "";
		for (int i = 0; i < subReportData.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) subReportData
					.get(i);
			baseCustomsDeclaration = commInfo.getBaseCustomsDeclaration();
			// 取报关单头的收获、送获单位名称
			if (baseCustomsDeclaration.getMachName() != null
					&& !baseCustomsDeclaration.getMachName().equals("")) {
				machKey = baseCustomsDeclaration.getMachName();
				if (machMap.get(machKey) == null) {
					machMap.put(machKey, machKey);
					if (machName.equals(""))
						machName = machKey;
					else
						machName = machName + "\n" + machKey;
				}
			}
		}
		return machName;
	}

	public List getTempContainerApply(List contianList) {
		List rlist = new ArrayList();
		int c = contianList.size() / 40
				+ (contianList.size() % 40 == 0 ? 0 : 1);
		for (int m = 0; m < c; m++) {
			for (int i = 0; i < 20; i++) {
				int index = m * 40 + i;
				if (contianList.size() <= index) {
					return rlist;
				}
				BaseCustomsDeclarationContainer c1 = (BaseCustomsDeclarationContainer) contianList
						.get(index);
				BaseCustomsDeclarationContainer c2 = null;
				if (contianList.size() > index + 20) {
					c2 = (BaseCustomsDeclarationContainer) contianList
							.get(index + 20);
				} else {
					c2 = new BaseCustomsDeclarationContainer();
				}
				TempContainerApply temp = new TempContainerApply();
				temp.setContainerCode1(c1.getContainerCode() == null ? "" : c1
						.getContainerCode());
				temp.setContainerCode2(c2.getContainerCode() == null ? "" : c2
						.getContainerCode());
				temp.setSpec1(c1.getSrtJzx().getName());
				temp.setSpec2(c2.getSrtJzx() == null ? "" : c2.getSrtJzx()
						.getName());
				temp.setSeq1(index + 1);
				if (contianList.size() > index + 20) {
					temp.setSeq2(index + 20 + 1);
				}
				System.out.println(temp.getSeq1());
				rlist.add(temp);
			}
		}

		return rlist;
	}

	public String formatBigChen(String amount, int bigD) {
		if (amount == null || amount.equals("")) {
			amount = "0.00";
		}
		Double number = Double.valueOf(amount);
		amount = String.format("%." + bigD + "f", number);
		return amount;
	}

}