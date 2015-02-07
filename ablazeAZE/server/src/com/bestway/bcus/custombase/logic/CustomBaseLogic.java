package com.bestway.bcus.custombase.logic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.CustomBrokerTemp;
import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.dao.TcsParametersDao;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.fpt.btplsinput.entity.CustomsDeclarationHeadTemp;
import com.bestway.common.fpt.btplsinput.logic.BtplsDownloadLogic;
import com.bestway.customs.common.entity.TempAllTypeInnerMergeDate;
import com.bestway.jptds.common.AppException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 海关基础逻辑类 2010-07-07check by hcl
 * 
 * @author Administrator
 * 
 */
public class CustomBaseLogic {
	private HsCodeDao hsCodeDao = null; // 海关编码DAO
	private TcsParametersDao tcsParametersDao;//TCS参数设置

	/**
	 * 
	 * 获取海关编码DAO：hsCodeDao
	 */
	public HsCodeDao getHsCodeDao() {
		return hsCodeDao;
	}

	/**
	 * 
	 * 设置海关编码DAO：hsCodeDao
	 */
	public void setHsCodeDao(HsCodeDao hsCodeDao) {
		this.hsCodeDao = hsCodeDao;
	}

	
	public TcsParametersDao getTcsParametersDao() {
		return tcsParametersDao;
	}

	public void setTcsParametersDao(TcsParametersDao tcsParametersDao) {
		this.tcsParametersDao = tcsParametersDao;
	}

	/**
	 * 保存商品编码
	 * 
	 * @param list
	 *            商品编码LIST
	 */
	public List saveComplex(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			CustomsComplex c = (CustomsComplex) list.get(i);
			Complex complex = new Complex();
			complex.setId(c.getCode());
			complex.setCode(c.getCode());
			complex.setName(c.getName());
			complex.setIsOut(c.getIsOut());
			complex.setNote(c.getNote());
			complex.setFirstUnit(c.getFirstUnit());
			complex.setSecondUnit(c.getSecondUnit());
			complex.setLowrate(c.getLowrate());
			complex.setHighrate(c.getHighrate());
			complex.setCcontrol(c.getCcontrol());
			// complex.setRegrate(c.getRegrate());
			// complex.setOutrate(c.getOutrate());
			// complex.setTaxrate(c.getTaxrate());
			this.hsCodeDao.saveComplex(complex);
			lsResult.add(complex);
		}
		return lsResult;
	}

	/**
	 * 保存报关行
	 * 
	 * @param list
	 *            商品编码LIST
	 */
	public List<CustomsBroker> saveCustomsBroker(List<CustomsBroker> list) {
		List<CustomsBroker> lsResult = new ArrayList<CustomsBroker>();
		for (int i = 0; i < list.size(); i++) {
			CustomsBroker c = (CustomsBroker) list.get(i);
			this.hsCodeDao.saveOrUpdate(c);
			lsResult.add(c);
		}
		return lsResult;
	}
	
	/**
	 * 根据海关商品编码生成自用商品编码
	 * 
	 * @param code海关商品编码
	 * @return
	 */
	public Complex makeComplexFromCustomsComplex(String code) {
		List ls = this.hsCodeDao.findCustomsComplex(code);
		if (ls != null && ls.size() > 0) {
			CustomsComplex obj = (CustomsComplex) ls.get(0);
			Complex c = new Complex();
			c.setId(obj.getCode());
			c.setCode(obj.getCode());
			c.setName(obj.getName());
			c.setFirstUnit(obj.getFirstUnit());
			c.setSecondUnit(obj.getSecondUnit());
			c.setLowrate(obj.getLowrate());
			c.setHighrate(obj.getHighrate());
			c.setNote(obj.getNote());
			this.hsCodeDao.saveOrUpdate(c);
			return c;
		}
		return null;
	}

	/**
	 * 删除商品编码
	 * 
	 * @param complex海关商品编码
	 */
	public void deleteComplex(Complex complex) {
		this.hsCodeDao.deleteComplex(complex);
	}

	/**
	 * 删除商品编码
	 * 
	 * @param 海关商品编码
	 *            (List)
	 */
	public void deleteComplex(List list) {
		for (int i = 0; i < list.size(); i++) {
			Complex c = (Complex) list.get(i);
			this.hsCodeDao.deleteComplex(c);
		}
	}
	
	/**
	 * 禁用商品编码
	 * 
	 * @param 海关商品编码
	 *            (List)
	 */
	public List disableComplex(List list) {
		// 禁用商品时
		/*
		 * 2、把商品编码的状态设置为作废。
		 */
		for (int i = 0; i < list.size(); i++) {
			Complex c = (Complex) list.get(i);
			//禁用作废状态
			c.setIsOut("1");
			hsCodeDao.saveComplex(c);
		}
		
		return list;
	}
	
	/**
	 * 启用商品编码
	 * 
	 * @param 海关商品编码
	 *            (List)
	 */
	public List enableComplex(List list) {
		// 启用商品编码时
		/*
		 * 2、把商品编码的状态设置为使用。
		 */
		for (int i = 0; i < list.size(); i++) {
			Complex c = (Complex) list.get(i);
		
			//设置状态为启用
			c.setIsOut("0");
			hsCodeDao.saveComplex(c);
		}
		
		return list;
	}

	/**
	 * 检察是否有重复的自用商品编码
	 * 
	 * @param code
	 *            商品编码
	 * 
	 * @return 如果有重复则返回是，否则反回否
	 */
	public boolean checkComplexCode(String code) {
		List list = this.hsCodeDao.checkComplexCode(code);
		if (list == null || list.size() < 1) {
			return false;
		}
		int m = (Integer) list.get(0);
		if (m > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新商品编码表
	 * 
	 * @param taskId
	 *            商品编码表ID
	 * @return
	 */
	public List findChangedComplex(String taskId) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在缓存商品编码");
		}
		List customsComplexList = this.hsCodeDao.findCustomsComplex();
		List complexList = this.hsCodeDao.findComplex();
		List<Complex> lsResult = new ArrayList<Complex>();
		Hashtable hs = new Hashtable();
		for (int i = 0; i < customsComplexList.size(); i++) {
			CustomsComplex c = (CustomsComplex) customsComplexList.get(i);
			hs.put(c.getCode(), c);
		}
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setMethodName("正在对比商品编码");
			info.setTotalNum(complexList.size());
		}
		for (int j = 0; j < complexList.size(); j++) {// 自用商品编码
			if (info != null) {
				info.setCurrentNum(j);
			}
			Complex obj = (Complex) complexList.get(j);
			CustomsComplex c = (CustomsComplex) hs.get(obj.getCode());
			if (c != null) {
				if (!(obj.getFirstUnit() == null ? "" : obj.getFirstUnit()
						.getCode()).equals((c.getFirstUnit() == null ? "" : c
						.getFirstUnit().getCode()))) {
					lsResult.add(obj);
					continue;
					// obj.setIsChange("1");
				} else if (!(obj.getSecondUnit() == null ? "" : obj
						.getSecondUnit().getCode())
						.equals((c.getSecondUnit() == null ? "" : c
								.getSecondUnit().getCode()))) {
					lsResult.add(obj);
					continue;
					// obj.setIsChange("1");
				} else if (!(obj.getName() == null ? "" : obj.getName())
						.equals((c.getName() == null ? "" : c.getName()))) {
					lsResult.add(obj);
					continue;
					// obj.setIsChange("1");
					
				}else if(!(obj.getCcontrol()== null? "" : obj.getCcontrol())
						.equals((c.getCcontrol() == null? "" : c.getCcontrol()))){
					lsResult.add(obj);
					System.out.println("许可证代码不同");
				}
			}
		}
		return lsResult;
	}

	/**
	 * 更新商品编码
	 * 
	 * @param taskId
	 *            编码
	 * @param lsComplex
	 *            商品编码（List）
	 * @return
	 */
	public List updateComplex(String taskId, List lsComplex) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setMethodName("正在同步商品");
			info.setTotalNum(lsComplex.size());
		}
		List lsResult = new ArrayList();
		if (lsComplex.size() <= 1000) {
			for (int i = 0; i < lsComplex.size(); i++) {
				if (info != null) {
					info.setCurrentNum(i+1);
				}
				Complex complex = (Complex) lsComplex.get(i);
				List list = this.hsCodeDao
						.findCustomsComplex(complex.getCode());
				List list2 = this.hsCodeDao.findCheckupComplex(complex.getCode());
				if (list != null && list.size() > 0) {
					CustomsComplex c = (CustomsComplex) list.get(0);
					complex.setName(c.getName());
					complex.setIsOut(c.getIsOut());
					complex.setNote(c.getNote());
					complex.setFirstUnit(c.getFirstUnit());
					complex.setSecondUnit(c.getSecondUnit());
					complex.setLowrate(c.getLowrate());
					complex.setHighrate(c.getHighrate());
					complex.setCcontrol(c.getCcontrol());
					hsCodeDao.saveComplex(complex);
					
					if (list2!=null&&list2.size() > 0) {
						CheckupComplex cc = (CheckupComplex) list2.get(0);
						Complex cp = cc.getComplex();
						cp.setName(c.getName());
						cp.setIsOut(c.getIsOut());
						cp.setNote(c.getNote());
						cp.setFirstUnit(c.getFirstUnit());
						cp.setSecondUnit(c.getSecondUnit());
						cp.setLowrate(c.getLowrate());
						cp.setHighrate(c.getHighrate());
						cp.setCcontrol(c.getCcontrol());
						cc.setComplex(cp);
						hsCodeDao.saveCheckupComplex(cc);
						System.out.println("商检同步成功---------------");
					}
					lsResult.add(complex);
				}
			}
		} else {
			List customsComplexList = this.hsCodeDao.findCustomsComplex();
			List checkupComplexList = this.hsCodeDao.findCheckupComplex();
			Hashtable hs = new Hashtable();
			Hashtable hs2 = new Hashtable();
			if (info != null) {
				info.setMethodName("正在缓存商品");
			}
			for (int i = 0; i < customsComplexList.size(); i++) {
				CustomsComplex c = (CustomsComplex) customsComplexList.get(i);
				hs.put(c.getCode(), c);
//				CheckupComplex  cc =  (CheckupComplex)checkupComplexList.get(i);
//				hs2.put(cc.getComplex().getCode(),cc);
			}
			for (int i = 0; i < checkupComplexList.size(); i++) {
				CheckupComplex  cc =  (CheckupComplex)checkupComplexList.get(i);
				hs2.put(cc.getComplex().getCode(),cc);
			}
			if (info != null) {
				info.setMethodName("正在同步商品");
			}
			for (int i = 0; i < lsComplex.size(); i++) {
				if (info != null) {
					info.setCurrentNum(i+1);
				}
				Complex complex = (Complex) lsComplex.get(i);
				CustomsComplex c = (CustomsComplex) hs.get(complex.getCode());
				CheckupComplex cc = (CheckupComplex)hs2.get(complex.getCode()); 
//				Complex cp = cc.getComplex();
				if (c != null) {
					
//					Complex cp = cc.getComplex();
					
					complex.setName(c.getName());
					complex.setIsOut(c.getIsOut());
					complex.setNote(c.getNote());
					complex.setFirstUnit(c.getFirstUnit());
					complex.setSecondUnit(c.getSecondUnit());
					complex.setLowrate(c.getLowrate());
					complex.setHighrate(c.getHighrate());
					complex.setCcontrol(c.getCcontrol());
					hsCodeDao.saveComplex(complex);
				     
//					cp.setName(c.getName());
//					cp.setIsOut(c.getIsOut());
//					cp.setNote(c.getNote());
//					cp.setFirstUnit(c.getFirstUnit());
//					cp.setSecondUnit(c.getSecondUnit());
//					cp.setLowrate(c.getLowrate());
//					cp.setHighrate(c.getHighrate());
//					cp.setCcontrol(c.getCcontrol());
					
					if(cc!=null){
						cc.setComplex(complex);
						hsCodeDao.saveCheckupComplex(cc);
						System.out.println("商检同步成功---------------");
					}
					
					lsResult.add(complex);
				}
			}
		}
		return lsResult;
	}

	/**
	 * 查找更新自用商品编码
	 */
	public List findUpdateComplex() {

		Hashtable<String, CustomsComplex> allCusCodeHs = new Hashtable<String, CustomsComplex>();
		List allls = hsCodeDao.findCustomsComplex();// 海关编码（10位）
		for (int i = 0; i < allls.size(); i++) {
			CustomsComplex d = (CustomsComplex) allls.get(i);
			allCusCodeHs.put(d.getCode(), d);
		}

		System.out.println("--开始更新" + new Date());
		List<BillTemp> errLs = new ArrayList<BillTemp>();
		HashMap<String, BillTemp> tempMap = new HashMap<String, BillTemp>();
		List complexList = hsCodeDao.findComplexCode();
		System.out.println("--获取所有自用商品编码完毕！");
		for (int i = 0; i < complexList.size(); i++) {
			Complex c = (Complex) complexList.get(i);
			String code = c.getCode();
			// 2011以后应该都不存在8位的商编
			// if (code != null && code.length() == 8) {// 8位编码
			// List fls = this.hsCodeDao.findCustomsComplexLikeCode(code);
			// if (fls != null && fls.size() == 1) {// 为一条记录
			// // (8+00或8+10或8+90)
			// CustomsComplex cusCode = (CustomsComplex) fls.get(0);
			// if (cusCode.getCode().equals(code + "00")) {// 为8+00
			// if (isEquUnit(c.getFirstUnit(), cusCode.getFirstUnit())
			// && isEquUnit(c.getSecondUnit(), cusCode
			// .getSecondUnit())) {// 法定单位相同
			// c.setCode(cusCode.getCode());
			// this.hsCodeDao.saveOrUpdateNoCache(c);
			// } else {// 法定单位不相同
			// diffCustomAndComplex(errLs, cusCode, c);
			// }
			// } else {
			// BillTemp b = new BillTemp();
			// newBillTemp(b, cusCode, c);
			// String note = b.getBill10() + " 二者的编码不相同" + "/";
			// b.setBill10(note);
			// errLs.add(b);
			// }
			// } else if (fls != null && fls.size() > 0) {
			// for (int j = 0; j < fls.size(); j++) {
			// CustomsComplex cusCode = (CustomsComplex) fls.get(j);
			// BillTemp b = new BillTemp();
			// b.setBill1(code);
			// b.setBill2(c.getName());
			// b.setBill3(c.getFirstUnit() == null ? "" : c.getFirstUnit()
			// .getName());
			// b.setBill4(c.getSecondUnit() == null ? "" : c
			// .getSecondUnit().getName());
			// b.setBill5(c.getCcontrol());
			// //===========
			// b.setBill6(cusCode.getName());
			// b.setBill7(cusCode.getFirstUnit() == null ? "" :
			// cusCode.getFirstUnit()
			// .getName());
			// b.setBill8(cusCode.getSecondUnit() == null ? "" : cusCode
			// .getSecondUnit().getName());
			// b.setBill9(cusCode.getCcontrol());
			// b.setBill10("自用商编对应多个海关商品编码");
			// errLs.add(b);
			// }
			// } else {
			// BillTemp b = new BillTemp();
			// b.setBill1(code);
			// b.setBill2(c.getName());
			// b.setBill3(c.getFirstUnit() == null ? "" : c.getFirstUnit()
			// .getName());
			// b.setBill4(c.getSecondUnit() == null ? "" : c
			// .getSecondUnit().getName());
			// b.setBill5(c.getCcontrol());
			// b.setBill10("自用商品编码在海关商品编码中不存在");
			// errLs.add(b);
			// }
			// } else {// 10位编码
			if (allCusCodeHs.get(code) != null) {
				CustomsComplex cusCode = allCusCodeHs.get(code);
				diffCustomAndComplex(errLs, tempMap, cusCode, c);
			} else {
				BillTemp b = new BillTemp();
				b.setBill1(code);
				b.setBill2(c.getName());
				b.setBill3(c.getFirstUnit() == null ? "" : c.getFirstUnit()
						.getName());
				b.setBill4(c.getSecondUnit() == null ? "" : c.getSecondUnit()
						.getName());
				b.setBill5(c.getCcontrol());
				b.setBill10("自用商品编码在海关商品编码中不存在");
				errLs.add(b);
			}
		}
		// }
		System.out.println("---end:" + new Date());
		return errLs;
	}

	/**
	 * 开始比较
	 * 
	 * @param errLs
	 * @param cusCode
	 * @param c
	 * @return
	 */
	private List diffCustomAndComplex(List<BillTemp> errLs,
			HashMap<String, BillTemp> tempMap, CustomsComplex cusCode, Complex c) {
		BillTemp b = null;

		String code = c.getCode();
		if (!c.getName().equals(cusCode.getName())) {
			// 名称不相同
			if (tempMap.get(code) != null) {
				b = tempMap.get(code);
			} else {
				b = new BillTemp();
				newBillTemp(b, cusCode, c);
			}
			String note = (b.getBill10() == null ? "" : b.getBill10())
					+ " 二者的名称不相同" + "/";
			b.setBill10(note);
			// errLs.add(b);
			tempMap.put(code, b);
		}
		if (!isEquUnit(c.getFirstUnit(), cusCode.getFirstUnit())) {
			// 第一法定单位不相同
//			System.out.println("=="+tempMap.get(code));
			if (tempMap.get(code) != null) {
				b = tempMap.get(code);
			} else {
				b = new BillTemp();
				newBillTemp(b, cusCode, c);
			}
			String note = (b.getBill10() == null ? "" : b.getBill10())
					+ " 二者的第一法定单位不相同" + "/";
			b.setBill10(note);
			// errLs.add(b);
			tempMap.put(code, b);
		}
		if (!isEquUnit(c.getSecondUnit(), cusCode.getSecondUnit())) {
			// 第二法定单位不相同
			if (tempMap.get(code) != null) {
				b = tempMap.get(code);
			} else {
				b = new BillTemp();
				newBillTemp(b, cusCode, c);
			}
			String note = (b.getBill10() == null ? "" : b.getBill10())
					+ " 二者的第二法定单位不相同" + "/";
			b.setBill10(note);
			// errLs.add(b);
			tempMap.put(code, b);
		}
		if (!(c.getCcontrol() == null ? "" : c.getCcontrol()).equals(cusCode
				.getCcontrol() == null ? "" : cusCode.getCcontrol())) {
			// 证单不相同
			if (tempMap.get(code) != null) {
				b = tempMap.get(code);
			} else {
				b = new BillTemp();
				newBillTemp(b, cusCode, c);
			}
			String note = (b.getBill10() == null ? "" : b.getBill10())
					+ " 二者的证件代码不相同" + "/";
			b.setBill10(note);
			// errLs.add(b);
			tempMap.put(code, b);
		}
		if (b != null) {
			errLs.add(b);
		}
		return errLs;
	}

	/**
	 * 设置差异比较数据
	 * 
	 * @param b
	 * @param cusCode
	 * @param c
	 * @return
	 */
	private BillTemp newBillTemp(BillTemp b, CustomsComplex cusCode, Complex c) {

		b.setBill1(c.getCode());
		b.setBill2(c.getName());
		b.setBill3(c.getFirstUnit() == null ? "" : c.getFirstUnit().getName());
		b
				.setBill4(c.getSecondUnit() == null ? "" : c.getSecondUnit()
						.getName());
		b.setBill5(c.getCcontrol());
		// =======
		b.setBill6(cusCode.getName());
		b.setBill7(cusCode.getFirstUnit() == null ? "" : cusCode.getFirstUnit()
				.getName());
		b.setBill8(cusCode.getSecondUnit() == null ? "" : cusCode
				.getSecondUnit().getName());
		b.setBill9(cusCode.getCcontrol());
		return b;
	}

	private boolean isEquUnit(Unit u1, Unit u2) {
		String u1code = "";
		String u2code = "";
		if (u1 != null) {
			u1code = u1.getCode();
		}
		if (u2 != null) {
			u2code = u2.getCode();
		}
		if (u1code.equals(u2code)) {
			return true;
		}
		return false;
	}

	/**
	 * 查找更新的商品编码
	 * 
	 */
	// public List findUpdateComplex(List list1){
	// for(int i = 0;i<list1.size();i++){
	// Complex complex = (Complex)list1.get(i);
	// complex = new Complex();
	// // CustomsComplex c = (CustomsComplex)list.get(i);
	// CustomsComplex c = new CustomsComplex();
	// if(c.getCode()!= complex.getCode()){
	// return list1;
	// }else if(c.getFirstUnit() != complex.getFirstUnit()){
	// return list1;
	// }else if(c.getName() != complex.getName()){
	// return list1;
	// }
	// else if(c.getSecondUnit() != complex.getSecondUnit()){
	// return list1;
	// }
	// list1.add(complex);
	// }
	//	
	// return list1;
	// }
	public void Edit() {
		String cc = null;
		List list = hsCodeDao.findCustomsComplex();
		CustomsComplex customsComplex = new CustomsComplex();
		// List list1 = hsCodeDao.findCustomsDocuName(licenseDocu.getCode());
		for (int i = 0; i < list.size(); i++) {
			String bb = null;
			// String mm ;
			String ss = ((CustomsComplex) list.get(i)).getCcontrol();
			System.out.println("------------------------" + ss);
			for (int j = 0; j < ss.length(); j++) {
				String a = ss.substring(j, j + 1);
				if (hsCodeDao.findLicenseDocuByCode(a)) {
					LicenseDocu licenseDocu = new LicenseDocu();
					bb = licenseDocu.getName();
				}
				cc = a + ":" + bb;
				// for(int m= 0;m < list1.size();m++){
				// String cc = ((LicenseDocu)list1.get(m)).getCode();
				// if(a.equals(cc)){
				// bb= bb+((LicenseDocu)list1.get(m)).getName();

				// System.out.println("-------------------"+a+"---------");

				// // for(int m= 0;m < list1.size();m++){
				// // String cc = ((LicenseDocu)list1.get(m)).getCode();
				// // if(a.equals(cc)){
				// //
				// customsComplex.setLicExp(((LicenseDocu)list1.get(m)).getName());
				// // }

				// afeb

				// c = LicenseDocu.code+":"+License.name;
			}
			// List list1 =
			// customBaseAction.findCustomsDocuName(licenseDocu.getCode());
			// for(int m= 0;m < list1.size();m++){
			// String cc = ((LicenseDocu)list1.get(m)).getCode();
			// System.out.println("--------------"+cc+"~~~~~~~~~~~~~~~~~~~~`");

			//				    
			// tableModel.updateRow(customsComplex);

			// }
			// System.out.println("&&&&&&&&"+bb+"@@@@@@@@@@@");

			// }
		}
		customsComplex.setLicExp(cc);
	}

	/**
	 * 
	 * 查获商品编码（不在海关商品编码库里）
	 * 
	 * @return
	 */

	public List findComplexNotInCustomsComplex() {
		List resultList = new ArrayList();
		Map map = new HashMap();
		List customComplex = this.hsCodeDao.findCustomsComplexCode();
		for (int i = 0; i < customComplex.size(); i++) {
			map.put((String) customComplex.get(i), 1);
		}
		List complex = this.hsCodeDao.findComplexCode();
		for (int m = 0; m < complex.size(); m++) {
			Complex c = (Complex) complex.get(m);
			if (map.get(c.getCode()) == null) {
				resultList.add(c);
			}
		}
		return resultList;
	}

	/**
	 * 
	 * 保存国家地区信息
	 * 
	 * @param list
	 * @param ischeck
	 *            是否是疫区
	 * @return
	 */
	public List saveCoutryAsChecked(List list, boolean ischeck) {
		List rlist = new ArrayList();
		if (list == null) {
			return rlist;
		}
		for (int i = 0; i < list.size(); i++) {
			Country cou = (Country) list.get(i);
			cou.setIsChcekup(ischeck);
			this.hsCodeDao.saveOrUpdate(cou);
			rlist.add(cou);
		}
		return rlist;
	}

	/**
	 * 
	 * 检查成品更新商品编码
	 * 
	 * @param list
	 *            检查成品
	 * @param flag
	 *            进出口标志
	 * @return
	 */
	public List addCheckupComplex(List list, Integer flag) {
		List rlist = new ArrayList();
		if (list == null)
			return rlist;
		for (int i = 0; i < list.size(); i++) {
			Complex cm = (Complex) list.get(i);
			CheckupComplex cc = new CheckupComplex();
			cc.setComplex(cm);
			cc.setImpExpFlag(flag);
			this.hsCodeDao.saveOrUpdate(cc);
			rlist.add(cc);
		}
		return rlist;
	}

	// /**
	// * 查找存放海关商品编码资料
	// * @param impexpfalg 进出口标志
	// * @return
	// */
	// public List findComplexForCheckupNotIn(Integer impexpfalg) {
	// List list = hsCodeDao.findComplexForCheckupNotIn(impexpfalg);
	// List rlist = new ArrayList();
	// for (int i = 0; i < list.size(); i++) {
	// CustomsComplex c = (CustomsComplex) list.get(i);
	// if (impexpfalg == null) {
	//
	// } else if (impexpfalg == ImpExpFlag.IMPORT) {
	// if (c.getCcontrol() != null && c.getCcontrol().contains("A")) {
	// rlist.add(c);
	// }
	// } else if (impexpfalg == ImpExpFlag.EXPORT) {
	// if (c.getCcontrol() != null && c.getCcontrol().contains("B")) {
	// rlist.add(c);
	// }
	// }
	// }
	// return rlist;
	// }
	/**
	 * 
	 * 查找存放报关商品资料
	 * 
	 * @param index
	 *            分页查询参数
	 * @param length
	 *            分页查询参数
	 * @param property
	 *            查询属性
	 * @param value
	 *            具体值
	 * @param isLike
	 *            是否模糊查询
	 * @param isImport
	 *            是否主料
	 * @return
	 */
	public List findComplexBcsTenInnerMerge(int index, int length,
			String property, Object value, boolean isLike, boolean isImport) {
		List arrayList = new ArrayList();
		List list = hsCodeDao.findComplexBcsTenInnerMerge(index, length,
				property, value, isLike, isImport);
		System.out.println("--------" + list.size() + "-------");
		if (list.isEmpty())
			return arrayList;
		for (int i = 0; i < list.size(); i++) {
			// TempAllTypeInnerMergeDate
			TempAllTypeInnerMergeDate tempInnerMergeDate = new TempAllTypeInnerMergeDate();
			BcsTenInnerMerge innerMergeData = (BcsTenInnerMerge) list.get(i);
			tempInnerMergeDate.setHsAfterComplex(innerMergeData.getComplex());
			tempInnerMergeDate.setHsAfterTenMemoNo(innerMergeData.getSeqNum());
			tempInnerMergeDate.setHsAfterMaterielTenName(innerMergeData
					.getName());
			tempInnerMergeDate.setHsAfterMaterielTenSpec(innerMergeData
					.getSpec());
			tempInnerMergeDate.setHsAfterLegalUnit(innerMergeData.getComUnit());
			arrayList.add(tempInnerMergeDate);

		}

		return arrayList;
	}

	/**
	 * 查找所有运输方式
	 * 
	 * @return
	 */
	public List findTransferMode() {
		return hsCodeDao.findTransferMode();
	}
	/**
	 * 删除报关行
	 * 
	 * @return
	 */
	public void deleteCustomsBroker(CustomsBroker customsBroker) {
		 hsCodeDao.deleteCustomsBroker(customsBroker);
	}
	
	/**
	 * 删除报关行
	 * 
	 * @return
	 */
	public void deleteCustomsBroker(List list) {
		for (int i = 0; i < list.size(); i++) {
			 CustomsBroker customsBroker = (CustomsBroker) list.get(i);
			 this.deleteCustomsBroker(customsBroker);
		}
	}
	
	/**
	 * 从运维平台下载报关行
	 * 
	 * @return
	 */
	public  List downCustomsBrokerFormbgcs() {
		//指定方法的参数值
		Object[] parameters =new Object[]{};
		TcsParameters para = tcsParametersDao.getTcsParameters();
		if (para == null) {
			throw new AppException("请确认EDI接口参数是否设置正确!");
		}
		String serverIP = para.getBtplsAddress();// 测试："192.168.2.51";//报关行 bcgs.bg114.cn
		String serverPort = para.getBtplsPort()==null?"":para.getBtplsPort().toString();// 测试："8080"; //端口80
		if (serverIP == null || "".equals(serverIP) || serverPort == null
				|| "".equals(serverPort)) {
			throw new AppException("请确认EDI接口参数地址,端口是否设置正确!");
		}

		String methodName = "dowloadBghData";
		Object[] objs =null;
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		try {
			//指定调用WebService的URL
			String urlPath = "http://" + serverIP + ":" + serverPort+ "/bcgs-ejb/BghWS";
			// 使用RPC方式调用WebService
			RPCServiceClient serviceClient = new RPCServiceClient();
			serviceClient.getOptions()
					.setProperty(HTTPConstants.CHUNKED, false);
			Options options = serviceClient.getOptions();
			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(urlPath);
			options.setTo(targetEPR);
			// 指定方法返回值的数据类型的Class对象
			Class[] clazz = new Class[] { String[].class };
			QName opAddEntry = new QName(
					"http://service.webservice.bcgs.bsw.com.cn/", methodName);
			objs = (Object[]) serviceClient.invokeBlocking(opAddEntry,
					parameters, clazz)[0];
		} catch (Exception e) {
			 throw new AppException("连接不上服务器!", e);
		}
		List<CustomBrokerTemp> tempList = new ArrayList<CustomBrokerTemp>();
		List<CustomsBroker> list = new ArrayList<CustomsBroker>();
		if (objs[0] != null) {
			String temp = objs[0].toString();
			System.out.println(temp);
			tempList = gson.fromJson(temp,
					new TypeToken<List<CustomBrokerTemp>>() {
					}.getType());
		}
		
		//运维平台与JBCSU实体类栏位对应
		CustomBrokerTemp customBrokerTemp =new CustomBrokerTemp();
		for (int i = 0; i < tempList.size(); i++) {
			customBrokerTemp = tempList.get(i);
			CustomsBroker customBroker =new CustomsBroker();
			customBroker.setAddress(customBrokerTemp.getAddress());
			customBroker.setCode(customBrokerTemp.getCode());
			//customBroker.setIsOut(null);
			customBroker.setLinkName(customBrokerTemp.getLinkMan());
			customBroker.setLinkTel(customBrokerTemp.getPhoneNum());
			customBroker.setName(customBrokerTemp.getName());
			list.add(customBroker);
		}
		return list;
	}
	
	public void SaveBriefs(List<Brief> list) {
		// TODO Auto-generated method stub
		for(Brief brief : list){
			this.tcsParametersDao.saveOrUpdate(brief);
		}
	}
	
}
