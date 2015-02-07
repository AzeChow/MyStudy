/*
 * Created on 2005-12-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.logic;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.dao.TransferFactoryManageDao;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.TempCustomsEnvelopBillList;
import com.bestway.common.transferfactory.entity.TempEnvelopComplex;
import com.bestway.common.transferfactory.entity.TransCustomsCollate;

/**
 * @author
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class TransferQueryLogic {
	private TransferFactoryManageDao transferFactoryManageDao = null;

	private Hashtable transHs = new Hashtable();

	private Hashtable transMoneyHs = new Hashtable();

	private Hashtable customsHs = new Hashtable();

	private Hashtable customsMoneyHs = new Hashtable();

	private Hashtable hsemsinexg = new Hashtable();

	private Hashtable hsemsinimg = new Hashtable();

	/**
	 * 查询转厂中关封的商品信息
	 * 
	 * @return
	 */
	public List findCustomsEnvelopComplex(boolean isImport, boolean isSeqNum) {
		List lsResult = new ArrayList();
		List list = this.transferFactoryManageDao.findCustomsEnvelopComplex(
				isImport, isSeqNum);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			TempEnvelopComplex temp = new TempEnvelopComplex();
			if (isSeqNum) {
				temp.setSeqNum((Integer) objs[0]);
				if (objs[1] != null) {
					temp.setCode(objs[1].toString());
				}
				if (objs[2] != null) {
					temp.setName(objs[2].toString());
				}
				if (objs[3] != null) {
					temp.setSpec(objs[3].toString());
				}
			} else {
				if (objs[0] != null) {
					temp.setCode(objs[0].toString());
				}
				if (objs[1] != null) {
					temp.setName(objs[1].toString());
				}
				if (objs[2] != null) {
					temp.setSpec(objs[2].toString());
				}
			}
			lsResult.add(temp);
		}
		return lsResult;
	}

	// 关封明细报表
	public List findCustomsEnvelopList(String envelopCodeValue, Integer seqNum,
			String complexCode, String scmCoc, boolean isImport, int state,
			Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		List list = transferFactoryManageDao.findCustomsEnvelopList(
				envelopCodeValue, seqNum, complexCode, scmCoc, isImport, state,
				beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo commInfo = (CustomsEnvelopCommodityInfo) list
					.get(i);
			TempCustomsEnvelopBillList temp = new TempCustomsEnvelopBillList();
			String EmsNo = commInfo.getCustomsEnvelopBill() == null ? ""
					: commInfo.getCustomsEnvelopBill().getEmsNo().substring(0,
							12);
			temp.setScmCoc(commInfo.getCustomsEnvelopBill().getScmCoc());
			temp.setEmsNo(EmsNo);
			temp.setBeginAvailability(commInfo.getCustomsEnvelopBill()
					.getBeginAvailability());
			temp.setEndAvailability(commInfo.getCustomsEnvelopBill()
					.getEndAvailability());
			temp.setPurchaseAndSaleContractNo(commInfo.getCustomsEnvelopBill()
					.getPurchaseAndSaleContractNo());
			temp.setSeqNum(commInfo.getSeqNum());
			temp.setComplex(commInfo.getComplex().getCode());
			temp.setPtName(commInfo.getPtName());
			temp.setPtSpec(commInfo.getPtSpec());
			temp.setOwnerQuantity(commInfo.getOwnerQuantity());
			temp.setOppositeEmsNo(commInfo.getOppositeEmsNo());
			temp.setOppositeEmsSerialNo(commInfo.getOppositeEmsSerialNo());
			temp.setOppositeQuantity(commInfo.getOppositeQuantity());
			temp.setOppositeComplexCode(commInfo.getOppositeComplexCode());
			temp.setOppositeName(commInfo.getOppositeName());
			temp.setOppositeSpec(commInfo.getOppositeSpec());
			temp.setOppositeUnit(commInfo.getOppositeUnit());
			temp.setUnitPrice(commInfo.getUnitPrice());
			String customsEnvelopBillNo = commInfo.getCustomsEnvelopBill()
					.getCustomsEnvelopBillNo();
			temp.setTransferFactoryDate(this.transferFactoryManageDao
					.findCustomsDeclarationDateByEnvelop(commInfo
							.getCustomsEnvelopBill().getProjectType(), EmsNo,
							commInfo.getSeqNum(), commInfo.getComplex()
									.getCode(), customsEnvelopBillNo));
			double realTransferFactoryAmount = this.transferFactoryManageDao
					.findCustomsDeclarationAmountByEnvelop(commInfo
							.getCustomsEnvelopBill().getProjectType(), EmsNo,
							commInfo.getSeqNum(), commInfo.getComplex()
									.getCode(), customsEnvelopBillNo);
			temp.setRealTransferFactoryAmount(realTransferFactoryAmount);
			lsResult.add(temp);
		}
		return lsResult;
	}

	// 备案周报表
	public List findPutRecord() {
		Date beginDate = getBeginDate();
		List list = transferFactoryManageDao
				.findAllCustomsEnvelopCommodityInfo(beginDate);
		Hashtable ht = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo obj = (CustomsEnvelopCommodityInfo) list
					.get(i);
			BillTemp temp = null;
			String scmcocCode = obj.getCustomsEnvelopBill().getScmCoc() == null ? ""
					: obj.getCustomsEnvelopBill().getScmCoc().getCode();
			String scmcocName = obj.getCustomsEnvelopBill().getScmCoc() == null ? ""
					: obj.getCustomsEnvelopBill().getScmCoc().getName();
			String name = obj.getPtName();
			String key = scmcocCode + "/" + name;
			String unitName = obj.getUnit() == null ? "" : obj.getUnit()
					.getName();
			if (ht.get(key) != null) { // 查到
				temp = (BillTemp) ht.get(key);
			} else {
				temp = new BillTemp();
				temp.setBill1(scmcocCode);
				temp.setBill2(scmcocName);
				temp.setBill3(name);
				temp.setBill4(unitName);
				ht.put(key, temp);
				Double jieyu = transferFactoryManageDao.findPreviBalance(obj
						.getPtName(), scmcocCode);
				temp.setBillSum2(jieyu);// 结余数量
				Double bgdNum = transferFactoryManageDao.findBgdZcNum(obj
						.getComplex().getCode(), obj.getPtName(), scmcocCode);
				temp.setBillSum3(bgdNum);// 转厂数量
				Double thisCheckNum = transferFactoryManageDao
						.findThisCheckNum(beginDate, new Date(), scmcocCode,
								obj);
				Double thisCheckWeight = transferFactoryManageDao
						.findThisCheckWeight(beginDate, new Date(), scmcocCode,
								obj);
				// if (unitName.equals("条") || unitName.equals("个")) {
				// temp.setBillSum4(jieyu + thisCheckNum - bgdNum);// 收货未结转
				// } else {
				// temp.setBillSum4(jieyu + thisCheckWeight - bgdNum);
				// }
				temp.setBillSum4(jieyu + thisCheckNum - bgdNum);// 收货未结转
				temp.setBillSum5(0 - fd(temp.getBillSum4()));
				temp.setBillSum7(thisCheckNum);// 验收数量
				temp.setBillSum8(thisCheckWeight);// 验收重量
			}
			temp.setBillSum1(fd(temp.getBillSum1())
					+ fd(obj.getOwnerQuantity()));// 备案数量
			// temp.setBillSum5(fd(temp.getBillSum5()) +
			// fd(temp.getBillSum1()));// 备案差异
			temp.setBillSum5(fd(temp.getBillSum1()) + fd(temp.getBillSum4()));// 备案差异
		}
		return new Vector(ht.values());
	}

	// 转厂安排周报表
	public List findTransferPlan() {
		List tempList = new ArrayList();
		Date beginDate = getBeginDate();
		List list = transferFactoryManageDao
				.findAllCustomsEnvelopCommodityInfo(beginDate);
		String s = "";
		int n = 0;
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo obj = (CustomsEnvelopCommodityInfo) list
					.get(i);
			BillTemp temp = new BillTemp();
			String scmcocCode = obj.getCustomsEnvelopBill().getScmCoc() == null ? ""
					: obj.getCustomsEnvelopBill().getScmCoc().getCode();
			String scmcocName = obj.getCustomsEnvelopBill().getScmCoc() == null ? ""
					: obj.getCustomsEnvelopBill().getScmCoc().getName();
			temp.setBill1(scmcocCode);
			temp.setBill2(scmcocName);
			temp.setBill3(obj.getPtName());
			temp.setBill4(obj.getComplex().getCode());
			String unitName = obj.getUnit() == null ? "" : obj.getUnit()
					.getName();
			temp.setBill5(unitName);
			if (s.equals(scmcocCode)) {
				n++;
			} else {
				s = scmcocCode;
				n = 1;
			}
			temp.setBill6(String.valueOf(n));
			temp
					.setBill7(obj.getCustomsEnvelopBill()
							.getCustomsEnvelopBillNo());
			temp.setBillSum1(obj.getOwnerQuantity());// 备案数量
			temp.setBill8(dateToDate(obj.getCustomsEnvelopBill()
					.getBeginAvailability()));// 日期
			Double jieyu = transferFactoryManageDao.findPreviBalance(obj
					.getPtName(), scmcocCode);
			Double thisCheckNum = transferFactoryManageDao.findThisCheckNum(
					beginDate, new Date(), scmcocCode, obj);
			Double thisCheckWeight = transferFactoryManageDao
					.findThisCheckWeight(beginDate, new Date(), scmcocCode, obj);
			Double bgdNum = transferFactoryManageDao.findBgdZcNum(obj
					.getComplex().getCode(), obj.getPtName(), scmcocCode);
			if (unitName.equals("条") || unitName.equals("个")) {
				temp.setBillSum2(jieyu + thisCheckNum - bgdNum);// 收货未结转
			} else {
				temp.setBillSum2(jieyu + thisCheckWeight - bgdNum);
			}
			temp.setBillSum3(CommonUtils
					.getDoubleExceptNull(temp.getBillSum1())
					- CommonUtils.getDoubleExceptNull(temp.getBillSum2()));// 关封结余

			tempList.add(temp);
		}
		return tempList;
	}

	private Double fd(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	public String dateToDate(Date date1) {
		if (date1 == null) {
			return null;
		}
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str1 = formater.format(date1);
		return str1;
	}

	public Date getBeginDate() {
		String strNow = nowToDate();
		strNow = substring(strNow, 0, 8) + "01 00:00:00";
		return strToStandDate(strNow);
	}

	public String nowToDate() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		Date date1 = new Date();
		String str1 = formater.format(date1);
		return str1;
	}

	public String substring(String str, int start, int end) {
		if (str != null && str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else if (str != null && str.getBytes().length > start) {
			return new String(str.getBytes(), start, str.getBytes().length
					- start);
		}
		return "";
	}

	public Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * @return Returns the transferFactoryManageDao.
	 */
	public TransferFactoryManageDao getTransferFactoryManageDao() {
		return transferFactoryManageDao;
	}

	/**
	 * @param transferFactoryManageDao
	 *            The transferFactoryManageDao to set.
	 */
	public void setTransferFactoryManageDao(
			TransferFactoryManageDao transferFactoryManageDao) {
		this.transferFactoryManageDao = transferFactoryManageDao;
	}

	public String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return bartDateFormat.format(date);
	}

	public List findTransferClienCollate(Integer projectType, String emsNo,
			String scmcocCode, Date beginDate, Date endDate, String spec,
			String hsName, Integer impExpType) {
		List list = this.transferFactoryManageDao
				.findTransferFactoryCommodityInfoGroupBySeqNum(emsNo,
						scmcocCode, beginDate, endDate, spec, hsName, impExpType);
		Hashtable hs = new Hashtable();
		Hashtable hsc = new Hashtable();
		List ls = new ArrayList();
		int j = list.size();
		int k = 0;
		Double sum = Double.valueOf(0);
		for (int i = 0; i < j; i++) {
			k = i;
			Object[] obj = (Object[]) list.get(i);
			BillTemp temp = new BillTemp();
			Date date = (Date) obj[0];
			String billNo = (String) obj[1];
			String customNo = (String) obj[2];
			String evNo = (String) obj[3];
			Integer seqNum = (obj[4] == null ? 0 : Integer.parseInt(obj[4]
					.toString()));
			String name = (String) obj[5];
			Double outNum = (obj[6] == null ? 0.0 : Double.parseDouble(obj[6]
					.toString()));
			String key = (String.valueOf(seqNum) + "/" + evNo);
			temp.setBill4(String.valueOf(seqNum));
			temp.setBill5(name);
			temp.setBill6(evNo);
			temp.setBill7((String) obj[obj.length - 1]);
			if (hsc.get(key) == null) {
				if (i != 0) {
					BillTemp obj1 = new BillTemp();
					ls.add(obj1);
				}
				// System.out.println("计算期初值..");
				temp.setBill1("-- 期初");
				Double begNum = 0.0;
				Double begInitNum = transferFactoryManageDao
						.findTransFactInitCommInfoBeginGroupBySeqNum(
								scmcocCode, endDate, impExpType, emsNo, evNo,
								seqNum, spec, hsName);
				// System.out.println("begInitNum:"+begInitNum);
				// 转厂
				Double transNum = 0.0;
				if (beginDate != null) {
					begNum = transferFactoryManageDao
							.findTransferFactoryCommodityInfoBeginGroupBySeqNum(
									scmcocCode, beginDate, impExpType, emsNo,
									evNo, seqNum, spec, hsName);
					transNum = this.transferFactoryManageDao
							.findCustomsDeclarationAmountByEnvelopNoAndSeqNum(
									projectType, emsNo, seqNum, scmcocCode,
									evNo, beginDate, spec, hsName, impExpType);
				}
				// System.out.println("begNum:"+begNum);
				// System.out.println("transNum:"+transNum);
				temp.setBillSum3(begNum + begInitNum - transNum);
				hsc.put(key, key);
				i = k - 1;
				sum = fd(temp.getBillSum3());
				temp.setBillSum4(sum);
				ls.add(temp);
				continue;
			}
			if (hs.get(key) == null) {
				// System.out.println("计算转厂数量..");
				Double transNum = this.transferFactoryManageDao
						.findCustomsDeclarationAmountByEnvelopNoAndSeqNum(
								projectType, emsNo, seqNum, evNo, beginDate,
								endDate, spec, hsName);
				if(impExpType.equals(ImpExpType.TRANSFER_FACTORY_IMPORT))
				{
					temp.setBill1("-- 转厂收货");
				} else {
					temp.setBill1("-- 转厂送货");
				}
				
				temp.setBillSum2(transNum);// 转厂数量
				hs.put(key, key);
				i = k - 1;
				sum = sum - fd(temp.getBillSum2());
				temp.setBillSum4(sum);
				ls.add(temp);
				continue;
			}
			// System.out.println("计算送货数量..");
			temp.setBill1(dateToString(date));// 日期
			temp.setBill2(billNo);
			temp.setBill3(customNo);
			temp.setBillSum1(outNum);// 送货数量
			sum = sum + fd(temp.getBillSum1());
			temp.setBillSum4(sum);
			ls.add(temp);
		}
		return ls;
	}

	private TransCustomsCollate forTransCustomsCollateNum(
			TransCustomsCollate totalNum) {
		totalNum = new TransCustomsCollate();
		totalNum.setScmCoc("各厂商合计");
		totalNum.setScmcoc7("各厂商合计");
		totalNum.setType("送货数");
		totalNum.setState1("2");
		return totalNum;
	}

	private TransCustomsCollate forTransCustomsCollateMoney(
			TransCustomsCollate totalMoney) {
		totalMoney = new TransCustomsCollate();
		totalMoney.setType("金额");
		totalMoney.setState1("2");
		return totalMoney;
	}

	private TransCustomsCollate forTransCustomsCollateCustomsNum(
			TransCustomsCollate totalNum) {
		totalNum = new TransCustomsCollate();
		totalNum.setType("报关数");
		totalNum.setState1("2");
		return totalNum;
	}

	private TransCustomsCollate forTransCustomsCollateCustomsMoney(
			TransCustomsCollate totalMoney) {
		totalMoney = new TransCustomsCollate();
		totalMoney.setType("金额");
		totalMoney.setState1("2");
		return totalMoney;
	}

	// 公司送货&报关数量/金额对照表
	public List traCustomsCollate(String year, boolean isImpType) {
		TransCustomsCollate totalNum = null; // 各厂商合计-送货数
		totalNum = forTransCustomsCollateNum(totalNum);

		TransCustomsCollate totalMoney = null; // 各厂商合计-金额
		totalMoney = forTransCustomsCollateMoney(totalMoney);

		TransCustomsCollate totalcustomsNum = null; // 各厂商合计-报关数
		totalcustomsNum = forTransCustomsCollateCustomsNum(totalcustomsNum);

		TransCustomsCollate totalcustomsMoney = null; // 各厂商合计-金额
		totalcustomsMoney = forTransCustomsCollateCustomsMoney(totalcustomsMoney);

		List<TransCustomsCollate> allList = new ArrayList<TransCustomsCollate>();
		List distinctLs = this.transferFactoryManageDao.findDistinctTrans(year,
				new Boolean(isImpType));
		if (distinctLs == null || distinctLs.size() < 1) {
			return new Vector();
		}
		if (isImpType) {
			getExistEmsImg();// 初始化hsemsinimg,key放了序号,Value放了EmsHeadH2kImg
		} else {
			getExistEmsExg();
		}
		getTransNum(year, new Boolean(isImpType));
		getCustomsNum(year, new Boolean(isImpType));
		getTransMoney(year, new Boolean(isImpType));
		getCustomsMoney(year, new Boolean(isImpType));

		String s = null;
		System.out.println("111111111111");
		for (int i = 0; i < distinctLs.size(); i++) {
			System.out.println("distinctLs:" + distinctLs);
			Object[] obj = (Object[]) distinctLs.get(i);
			Integer seqNum = (Integer) obj[0];// 序号
			String pro = (String) obj[2];
			String proName = (pro == null) ? "" : pro;

			// if (s != null && !s.equals(seqNum)){
			if (s != null && !s.equals(String.valueOf(seqNum) + "/" + proName)) {
				System.out.println("ppppppppppppppppppppppp");
				allList.add(totalNum);// 各厂商合计-送货数
				allList.add(totalMoney);// 各厂商合计-金额

				TransCustomsCollate totalPrice = new TransCustomsCollate();// 各厂商合计-单价
				totalPrice.setType("单价");
				totalPrice.setState1("2");
				totalPrice = getTransCustomsPrice(totalPrice, totalMoney,
						totalNum);
				allList.add(totalPrice);

				allList.add(totalcustomsNum);// 各厂商合计--报关数
				allList.add(totalcustomsMoney);// 各厂商合计--金额

				TransCustomsCollate totalcustomsPrice = new TransCustomsCollate();// 各厂商合计-单价
				totalcustomsPrice.setType("单价");
				totalcustomsPrice.setState1("2");
				totalcustomsPrice = getTransCustomsPrice(totalcustomsPrice,
						totalcustomsMoney, totalcustomsNum);
				allList.add(totalcustomsPrice);

				TransCustomsCollate totalCyNum = new TransCustomsCollate();// 各厂商合计-差异数
				totalCyNum.setType("差异数");
				totalCyNum.setState1("2");
				totalCyNum = getTransCustomsCy(totalCyNum, totalNum,
						totalcustomsNum);
				allList.add(totalCyNum);

				TransCustomsCollate totalCyMoeny = new TransCustomsCollate();// 各厂商合计-金额
				totalCyMoeny.setType("金额");
				totalCyMoeny.setState("1");
				totalCyMoeny.setState1("2");
				totalCyMoeny = getTransCustomsCy(totalCyMoeny, totalMoney,
						totalcustomsMoney);
				allList.add(totalCyMoeny);

				totalNum = forTransCustomsCollateNum(totalNum); // 各厂商合计--送货数
				totalMoney = forTransCustomsCollateMoney(totalMoney); // 各厂商合计--金额
				totalcustomsNum = forTransCustomsCollateCustomsNum(totalcustomsNum); // 各厂商合计--报关数
				totalcustomsMoney = forTransCustomsCollateCustomsMoney(totalcustomsMoney); // 各厂商合计--金额

				i--;
				// s = seqNum;
				s = String.valueOf(seqNum) + "/" + proName;
				continue;
			}
			// s = seqNum;
			s = String.valueOf(seqNum) + "/" + proName;
			System.out.println("wwwwwwwwwww");
			ScmCoc scm = (ScmCoc) obj[1];
			String scmName = (scm == null) ? "" : scm.getName(); // 客户名称
			String key = String.valueOf(seqNum) + "/" + scmName + "/" + proName;
			// --------------------------------------------------------------------------------------------
			// 送货数
			TransCustomsCollate t1 = null;
			if (transHs.get(key) != null) {
				t1 = (TransCustomsCollate) transHs.get(key);
				if (t1.getScmCoc1() != null) {
					t1.setScmcoc7(t1.getScmCoc1());
				}
				if (t1.getNameSpec1() != null) {
					t1.setNameSpec7(t1.getNameSpec1());
				}
				System.out.println("yyyyyyyyyy");
				totalNum = getTransCustomsTotal(totalNum, totalNum, t1);// 送货汇总
				allList.add(t1);
				System.out.println("sssssssssss");
				// 金额
				TransCustomsCollate t2 = (TransCustomsCollate) transMoneyHs
						.get(key);
				if (t1.getScmCoc2() != null) {
					t2.setScmcoc7(t1.getScmCoc2());
				}
				if (t1.getNameSpec2() != null) {
					t2.setNameSpec7(t1.getNameSpec2());
				}
				System.out.println("ggggggggggggggg");
				totalMoney = getTransCustomsTotal(totalMoney, totalMoney, t2);// 金额汇总
				allList.add(t2);
				System.out.println("bbbbbbbbbbbbbbbbbbbb");
				// 单价
				TransCustomsCollate t3 = new TransCustomsCollate();
				if (t1.getScmCoc3() != null) {
					t3.setScmcoc7(t1.getScmCoc3());
				}
				if (t1.getNameSpec3() != null) {
					t3.setNameSpec7(t1.getNameSpec3());
				}
				t3.setType("单价");
				t3 = getTransCustomsPrice(t3, t2, t1);
				allList.add(t3);

				System.out.println("2222222222222222");
				// ------------------------------------------------------------------------------------------
				// 报关数
				TransCustomsCollate t4 = null;
				TransCustomsCollate t5 = null;
				if (customsHs.get(key) != null) {
					t4 = (TransCustomsCollate) customsHs.get(key);
					if (t1.getScmCoc4() != null) {
						t4.setScmcoc7(t1.getScmCoc4());
					}
					if (t1.getNameSpec4() != null) {
						t4.setNameSpec7(t1.getNameSpec4());
					}
					totalcustomsNum = getTransCustomsTotal(totalcustomsNum,
							totalcustomsNum, t4);// 报关汇总
					allList.add(t4);

					// 金额
					t5 = (TransCustomsCollate) customsMoneyHs.get(key);
					if (t1.getScmCoc5() != null) {
						t5.setScmcoc7(t1.getScmCoc5());
					}
					if (t1.getNameSpec5() != null) {
						t5.setNameSpec7(t1.getNameSpec5());
					}
					totalcustomsMoney = getTransCustomsTotal(totalcustomsMoney,
							totalcustomsMoney, t5);// 报关汇总
					allList.add(t5);
				} else {
					t4 = new TransCustomsCollate();
					if (t1.getScmCoc4() != null) {
						t4.setScmcoc7(t1.getScmCoc4());
					}
					if (t1.getNameSpec4() != null) {
						t4.setNameSpec7(t1.getNameSpec4());
					}
					t4.setType("报关数");
					t4 = getTransCustomsZone(t4);
					totalcustomsNum = getTransCustomsTotal(totalcustomsNum,
							totalcustomsNum, t4);// 报关汇总
					allList.add(t4);

					t5 = new TransCustomsCollate();
					if (t1.getScmCoc5() != null) {
						t5.setScmcoc7(t1.getScmCoc5());
					}
					if (t1.getNameSpec5() != null) {
						t5.setNameSpec7(t1.getNameSpec5());
					}
					t5.setType("金额");
					t5 = getTransCustomsZone(t5);
					totalcustomsMoney = getTransCustomsTotal(totalcustomsMoney,
							totalcustomsMoney, t5);// 报关汇总
					allList.add(t5);
				}

				// 单价
				TransCustomsCollate t6 = new TransCustomsCollate();
				if (t1.getScmCoc6() != null) {
					t6.setScmcoc7(t1.getScmCoc6());
				}
				if (t1.getNameSpec6() != null) {
					t6.setNameSpec7(t1.getNameSpec6());
				}
				t6.setType("单价");
				t6 = getTransCustomsPrice(t6, t5, t4);
				allList.add(t6);
				System.out.println("33333333333");
				// --------------------------------------------------------------------------------------------
				// 差异数
				TransCustomsCollate t7 = new TransCustomsCollate();
				t7.setType("差异数");
				t7 = getTransCustomsCy(t7, t1, t4);
				allList.add(t7);
				// 金额
				TransCustomsCollate t8 = new TransCustomsCollate();
				t8.setState("1");
				t8.setType("金额");
				t8 = getTransCustomsCy(t8, t2, t5);
				System.out.println("4444444444444");
				allList.add(t8);
			}

			if (i == (distinctLs.size() - 1)) { // 各厂商合计
				System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqq");
				allList.add(totalNum);// 各厂商合计-送货数
				allList.add(totalMoney);// 各厂商合计-金额

				TransCustomsCollate totalPrice = new TransCustomsCollate();// 各厂商合计-单价
				totalPrice.setType("单价");
				totalPrice.setState1("2");
				totalPrice = getTransCustomsPrice(totalPrice, totalMoney,
						totalNum);
				allList.add(totalPrice);

				allList.add(totalcustomsNum);// 各厂商合计--报关数
				allList.add(totalcustomsMoney);// 各厂商合计--金额

				TransCustomsCollate totalcustomsPrice = new TransCustomsCollate();// 各厂商合计-单价
				totalcustomsPrice.setType("单价");
				totalcustomsPrice.setState1("2");
				totalcustomsPrice = getTransCustomsPrice(totalcustomsPrice,
						totalcustomsMoney, totalcustomsNum);
				allList.add(totalcustomsPrice);

				TransCustomsCollate totalCyNum = new TransCustomsCollate();// 各厂商合计-差异数
				totalCyNum.setType("差异数");
				totalCyNum.setState1("2");
				totalCyNum = getTransCustomsCy(totalCyNum, totalNum,
						totalcustomsNum);
				allList.add(totalCyNum);

				TransCustomsCollate totalCyMoeny = new TransCustomsCollate();// 各厂商合计-金额
				totalCyMoeny.setType("金额");
				totalCyMoeny.setState("1");
				totalCyMoeny.setState1("2");
				totalCyMoeny = getTransCustomsCy(totalCyMoeny, totalMoney,
						totalcustomsMoney);
				allList.add(totalCyMoeny);

				totalNum = forTransCustomsCollateNum(totalNum); // 各厂商合计--送货数
				totalMoney = forTransCustomsCollateMoney(totalMoney); // 各厂商合计--金额
				totalcustomsNum = forTransCustomsCollateCustomsNum(totalcustomsNum); // 各厂商合计--报关数
				totalcustomsMoney = forTransCustomsCollateCustomsMoney(totalcustomsMoney); // 各厂商合计--金额
			}
		}
		return allList;
	}

	private void getTransNum(String year, Boolean isImpExpType) {
		transHs.clear();
		List ls = transferFactoryManageDao.getTransNum(year, isImpExpType);
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			Integer seqNum = (Integer) obj[0];// 序号
			ScmCoc scm = (ScmCoc) obj[1];
			String pro = (String) obj[2];// 事业部
			String proName = (pro == null) ? "" : pro;
			String scmName = (scm == null) ? "" : scm.getName(); // 客户名称
			String key = String.valueOf(seqNum) + "/" + scmName + "/" + proName;
			TransCustomsCollate t = new TransCustomsCollate();
			t.setScmCoc(scmName + "/" + proName);
			t.setScmCocStr(scmName + "/" + proName);
			String nameSpec = "";
			String unitName = "";
			if (isImpExpType.equals(Boolean.valueOf(true))) {
				EmsHeadH2kImg exg = (EmsHeadH2kImg) hsemsinimg.get(seqNum);
				nameSpec = (exg.getName() == null ? "" : exg.getName()) + "/"
						+ (exg.getSpec() == null ? "" : exg.getSpec());
				unitName = (exg.getUnit() == null) ? "" : exg.getUnit()
						.getName();
			} else {
				EmsHeadH2kExg exg = (EmsHeadH2kExg) hsemsinexg.get(seqNum);
				nameSpec = (exg.getName() == null ? "" : exg.getName()) + "/"
						+ (exg.getSpec() == null ? "" : exg.getSpec());
				unitName = (exg.getUnit() == null) ? "" : exg.getUnit()
						.getName();
				t.setComplex(exg.getComplex() == null ? "" : exg.getComplex()
						.getCode());
			}

			t.setNameSpec(nameSpec);
			t.setNameSpecStr(nameSpec);
			t.setUnitName(unitName);

			t.setSeqNum(seqNum);
			t.setType("送货数");
			t.setBeginNum(forNum(fd((Double) obj[3]), 2));
			t.setJanNum(forNum(fd((Double) obj[4]), 2));
			t.setFebNum(forNum(fd((Double) obj[5]), 2));
			t.setMarNum(forNum(fd((Double) obj[6]), 2));
			t.setAprNum(forNum(fd((Double) obj[7]), 2));
			t.setMayNum(forNum(fd((Double) obj[8]), 2));
			t.setJunNum(forNum(fd((Double) obj[9]), 2));
			t.setJulNum(forNum(fd((Double) obj[10]), 2));
			t.setAugNum(forNum(fd((Double) obj[11]), 2));
			t.setSepNum(forNum(fd((Double) obj[12]), 2));
			t.setOctNum(forNum(fd((Double) obj[13]), 2));
			t.setMovNum(forNum(fd((Double) obj[14]), 2));
			t.setDecNum(forNum(fd((Double) obj[15]), 2));
			t.setYearTotalNum(forNum((t.getJanNum() + t.getFebNum()
					+ t.getMarNum() + t.getAprNum() + t.getMayNum()
					+ t.getJunNum() + t.getJulNum() + t.getAugNum()
					+ t.getSepNum() + t.getOctNum() + t.getMovNum() + t
					.getDecNum()), 2));
			t.setTotalNum(forNum((t.getYearTotalNum() + t.getBeginNum()), 2));
			transHs.put(key, t);
		}
	}

	private void getTransMoney(String year, Boolean isImpExpType) {
		transMoneyHs.clear();
		List ls = transferFactoryManageDao.getTransMoney(year, isImpExpType);
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			Integer seqNum = (Integer) obj[0];// 序号
			ScmCoc scm = (ScmCoc) obj[1];
			String pro = (String) obj[2];
			String proName = (pro == null) ? "" : pro;
			String scmName = (scm == null) ? "" : scm.getName(); // 客户名称
			String key = String.valueOf(seqNum) + "/" + scmName + "/" + proName;
			TransCustomsCollate t = new TransCustomsCollate();
			t.setType("金额");
			t.setBeginNum(forNum(fd((Double) obj[3]), 2));
			t.setJanNum(forNum(fd((Double) obj[4]), 2));
			t.setFebNum(forNum(fd((Double) obj[5]), 2));
			t.setMarNum(forNum(fd((Double) obj[6]), 2));
			t.setAprNum(forNum(fd((Double) obj[7]), 2));
			t.setMayNum(forNum(fd((Double) obj[8]), 2));
			t.setJunNum(forNum(fd((Double) obj[9]), 2));
			t.setJulNum(forNum(fd((Double) obj[10]), 2));
			t.setAugNum(forNum(fd((Double) obj[11]), 2));
			t.setSepNum(forNum(fd((Double) obj[12]), 2));
			t.setOctNum(forNum(fd((Double) obj[13]), 2));
			t.setMovNum(forNum(fd((Double) obj[14]), 2));
			t.setDecNum(forNum(fd((Double) obj[15]), 2));
			t.setYearTotalNum(forNum((t.getJanNum() + t.getFebNum()
					+ t.getMarNum() + t.getAprNum() + t.getMayNum()
					+ t.getJunNum() + t.getJulNum() + t.getAugNum()
					+ t.getSepNum() + t.getOctNum() + t.getMovNum() + t
					.getDecNum()), 2));
			t.setTotalNum(forNum((t.getYearTotalNum() + t.getBeginNum()), 2));
			transMoneyHs.put(key, t);
		}
	}

	private void getCustomsNum(String year, Boolean isImpExpType) {
		customsHs.clear();
		List ls = transferFactoryManageDao.getCustomsNum(year, isImpExpType);
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			Integer seqNum = (Integer) obj[0];// 序号
			ScmCoc scm = (ScmCoc) obj[1];
			String pro = (String) obj[2];
			String proName = (pro == null) ? "" : pro;
			String scmName = (scm == null) ? "" : scm.getName(); // 客户名称
			String key = String.valueOf(seqNum) + "/" + scmName + "/" + proName;
			TransCustomsCollate t = new TransCustomsCollate();
			t.setType("报关数");
			t.setBeginNum(forNum(fd((Double) obj[3]), 2));
			t.setJanNum(forNum(fd((Double) obj[4]), 2));
			t.setFebNum(forNum(fd((Double) obj[5]), 2));
			t.setMarNum(forNum(fd((Double) obj[6]), 2));
			t.setAprNum(forNum(fd((Double) obj[7]), 2));
			t.setMayNum(forNum(fd((Double) obj[8]), 2));
			t.setJunNum(forNum(fd((Double) obj[9]), 2));
			t.setJulNum(forNum(fd((Double) obj[10]), 2));
			t.setAugNum(forNum(fd((Double) obj[11]), 2));
			t.setSepNum(forNum(fd((Double) obj[12]), 2));
			t.setOctNum(forNum(fd((Double) obj[13]), 2));
			t.setMovNum(forNum(fd((Double) obj[14]), 2));
			t.setDecNum(forNum(fd((Double) obj[15]), 2));
			t.setYearTotalNum(forNum((t.getJanNum() + t.getFebNum()
					+ t.getMarNum() + t.getAprNum() + t.getMayNum()
					+ t.getJunNum() + t.getJulNum() + t.getAugNum()
					+ t.getSepNum() + t.getOctNum() + t.getMovNum() + t
					.getDecNum()), 2));
			t.setTotalNum(forNum((t.getYearTotalNum() + t.getBeginNum()), 2));
			customsHs.put(key, t);
		}
	}

	private void getCustomsMoney(String year, Boolean isImpExpType) {
		customsMoneyHs.clear();
		List ls = transferFactoryManageDao.getCustomsMoney(year, isImpExpType);
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[]) ls.get(i);
			Integer seqNum = (Integer) obj[0];// 序号
			ScmCoc scm = (ScmCoc) obj[1];
			String pro = (String) obj[2];
			String proName = (pro == null) ? "" : pro;// 事业部
			String scmName = (scm == null) ? "" : scm.getName(); // 客户名称
			String key = String.valueOf(seqNum) + "/" + scmName + "/" + proName;
			TransCustomsCollate t = new TransCustomsCollate();
			t.setType("金额");
			t.setBeginNum(forNum(fd((Double) obj[3]), 2));
			t.setJanNum(forNum(fd((Double) obj[4]), 2));
			t.setFebNum(forNum(fd((Double) obj[5]), 2));
			t.setMarNum(forNum(fd((Double) obj[6]), 2));
			t.setAprNum(forNum(fd((Double) obj[7]), 2));
			t.setMayNum(forNum(fd((Double) obj[8]), 2));
			t.setJunNum(forNum(fd((Double) obj[9]), 2));
			t.setJulNum(forNum(fd((Double) obj[10]), 2));
			t.setAugNum(forNum(fd((Double) obj[11]), 2));
			t.setSepNum(forNum(fd((Double) obj[12]), 2));
			t.setOctNum(forNum(fd((Double) obj[13]), 2));
			t.setMovNum(forNum(fd((Double) obj[14]), 2));
			t.setDecNum(forNum(fd((Double) obj[15]), 2));
			t.setYearTotalNum(forNum((t.getJanNum() + t.getFebNum()
					+ t.getMarNum() + t.getAprNum() + t.getMayNum()
					+ t.getJunNum() + t.getJulNum() + t.getAugNum()
					+ t.getSepNum() + t.getOctNum() + t.getMovNum() + t
					.getDecNum()), 2));
			t.setTotalNum(forNum((t.getYearTotalNum() + t.getBeginNum()), 2));
			customsMoneyHs.put(key, t);
		}
	}

	// 计算差异
	private TransCustomsCollate getTransCustomsCy(TransCustomsCollate c,
			TransCustomsCollate c1, TransCustomsCollate c2) {
		c.setBeginNum(forNum((fd(c1.getBeginNum()) - fd(c2.getBeginNum())), 2));
		c.setJanNum(forNum((fd(c1.getJanNum()) - fd(c2.getJanNum())), 2));
		c.setFebNum(forNum((fd(c1.getFebNum()) - fd(c2.getFebNum())), 2));
		c.setMarNum(forNum((fd(c1.getMarNum()) - fd(c2.getMarNum())), 2));
		c.setAprNum(forNum((fd(c1.getAprNum()) - fd(c2.getAprNum())), 2));
		c.setMayNum(forNum((fd(c1.getMayNum()) - fd(c2.getMayNum())), 2));
		c.setJunNum(forNum((fd(c1.getJunNum()) - fd(c2.getJunNum())), 2));
		c.setJulNum(forNum((fd(c1.getJulNum()) - fd(c2.getJulNum())), 2));
		c.setAugNum(forNum((fd(c1.getAugNum()) - fd(c2.getAugNum())), 2));
		c.setSepNum(forNum((fd(c1.getSepNum()) - fd(c2.getSepNum())), 2));
		c.setOctNum(forNum((fd(c1.getOctNum()) - fd(c2.getOctNum())), 2));
		c.setMovNum(forNum((fd(c1.getMovNum()) - fd(c2.getMovNum())), 2));
		c.setDecNum(forNum((fd(c1.getDecNum()) - fd(c2.getDecNum())), 2));
		c.setYearTotalNum(forNum((fd(c1.getYearTotalNum()) - fd(c2
				.getYearTotalNum())), 2));
		c.setTotalNum(forNum((fd(c1.getTotalNum()) - fd(c2.getTotalNum())), 2));
		return c;
	}

	// 计算汇总
	private TransCustomsCollate getTransCustomsTotal(TransCustomsCollate c,
			TransCustomsCollate c1, TransCustomsCollate c2) {
		c.setBeginNum(forNum((fd(c1.getBeginNum()) + fd(c2.getBeginNum())), 2));
		c.setJanNum(forNum((fd(c1.getJanNum()) + fd(c2.getJanNum())), 2));
		c.setFebNum(forNum((fd(c1.getFebNum()) + fd(c2.getFebNum())), 2));
		c.setMarNum(forNum((fd(c1.getMarNum()) + fd(c2.getMarNum())), 2));
		c.setAprNum(forNum((fd(c1.getAprNum()) + fd(c2.getAprNum())), 2));
		c.setMayNum(forNum((fd(c1.getMayNum()) + fd(c2.getMayNum())), 2));
		c.setJunNum(forNum((fd(c1.getJunNum()) + fd(c2.getJunNum())), 2));
		c.setJulNum(forNum((fd(c1.getJulNum()) + fd(c2.getJulNum())), 2));
		c.setAugNum(forNum((fd(c1.getAugNum()) + fd(c2.getAugNum())), 2));
		c.setSepNum(forNum((fd(c1.getSepNum()) + fd(c2.getSepNum())), 2));
		c.setOctNum(forNum((fd(c1.getOctNum()) + fd(c2.getOctNum())), 2));
		c.setMovNum(forNum((fd(c1.getMovNum()) + fd(c2.getMovNum())), 2));
		c.setDecNum(forNum((fd(c1.getDecNum()) + fd(c2.getDecNum())), 2));
		c.setYearTotalNum(forNum((fd(c1.getYearTotalNum()) + fd(c2
				.getYearTotalNum())), 2));
		c.setTotalNum(forNum((fd(c1.getTotalNum()) + fd(c2.getTotalNum())), 2));
		return c;
	}

	// 计算单价
	private TransCustomsCollate getTransCustomsPrice(TransCustomsCollate c,
			TransCustomsCollate c1, TransCustomsCollate c2) {
		c.setBeginNum(forNum(
				(Double.valueOf(0.0).equals(fd(c2.getBeginNum())) ? Double
						.valueOf(0.0) : (fd(c1.getBeginNum()) / fd(c2
						.getBeginNum()))), 5));
		c.setJanNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getJanNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getJanNum()) / fd(c2.getJanNum()))), 5));
		c.setFebNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getFebNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getFebNum()) / fd(c2.getFebNum()))), 5));
		c.setMarNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getMarNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getMarNum()) / fd(c2.getMarNum()))), 5));
		c.setAprNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getAprNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getAprNum()) / fd(c2.getAprNum()))), 5));
		c.setMayNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getMayNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getMayNum()) / fd(c2.getMayNum()))), 5));
		c.setJunNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getJunNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getJunNum()) / fd(c2.getJunNum()))), 5));
		c.setJulNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getJulNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getJulNum()) / fd(c2.getJulNum()))), 5));
		c.setAugNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getAugNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getAugNum()) / fd(c2.getAugNum()))), 5));
		c.setSepNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getSepNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getSepNum()) / fd(c2.getSepNum()))), 5));
		c.setOctNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getOctNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getOctNum()) / fd(c2.getOctNum()))), 5));
		c.setMovNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getMovNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getMovNum()) / fd(c2.getMovNum()))), 5));
		c.setDecNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getDecNum())) ? Double
						.valueOf(0.0)
						: (fd(c1.getDecNum()) / fd(c2.getDecNum()))), 5));

		c.setYearTotalNum(forNum((Double.valueOf(0.0).equals(
				fd(c1.getYearTotalNum())) ? Double.valueOf(0.0) : (fd(c1
				.getYearTotalNum()) / fd(c2.getYearTotalNum()))), 5));
		c.setTotalNum(forNum(
				(Double.valueOf(0.0).equals(fd(c1.getTotalNum())) ? Double
						.valueOf(0.0) : (fd(c1.getTotalNum()) / fd(c2
						.getTotalNum()))), 5));
		return c;
	}

	private TransCustomsCollate getTransCustomsZone(TransCustomsCollate c) {
		c.setBeginNum(forNum(Double.valueOf(0), 2));
		c.setJanNum(forNum(Double.valueOf(0), 2));
		c.setFebNum(forNum(Double.valueOf(0), 2));
		c.setMarNum(forNum(Double.valueOf(0), 2));
		c.setAprNum(forNum(Double.valueOf(0), 2));
		c.setMayNum(forNum(Double.valueOf(0), 2));
		c.setJunNum(forNum(Double.valueOf(0), 2));
		c.setJulNum(forNum(Double.valueOf(0), 2));
		c.setAugNum(forNum(Double.valueOf(0), 2));
		c.setSepNum(forNum(Double.valueOf(0), 2));
		c.setOctNum(forNum(Double.valueOf(0), 2));
		c.setMovNum(forNum(Double.valueOf(0), 2));
		c.setDecNum(forNum(Double.valueOf(0), 2));
		c.setYearTotalNum(forNum(Double.valueOf(0), 2));
		c.setTotalNum(forNum(Double.valueOf(0), 2));
		return c;
	}

	private void getExistEmsExg() {
		hsemsinexg.clear();
		List ls = transferFactoryManageDao.findEmsHeadH2kExg();
		for (int i = 0; i < ls.size(); i++) {
			EmsHeadH2kExg obj = (EmsHeadH2kExg) ls.get(i);
			hsemsinexg.put(obj.getSeqNum(), obj);
		}
	}

	private void getExistEmsImg() {
		hsemsinimg.clear();
		List ls = transferFactoryManageDao.findEmsHeadH2kImg();
		for (int i = 0; i < ls.size(); i++) {
			EmsHeadH2kImg obj = (EmsHeadH2kImg) ls.get(i);
			hsemsinimg.put(obj.getSeqNum(), obj);
		}
	}

	private Double forNum(Double shuliang, int d) {
		BigDecimal bd = new BigDecimal(shuliang.doubleValue());
		String totalshuliang = bd.setScale(d, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}
}
