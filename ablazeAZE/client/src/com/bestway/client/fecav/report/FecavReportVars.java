/*
 * Created on 2005-5-27
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fecav.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FecavReportVars {

	private static FecavReportVars		c			= null;
	private static List					list		= new ArrayList();
	private static Map<String, Double>	map			= new HashMap<String, Double>();
	

	private FecavReportVars(List rateList) {
		if(rateList == null){
			return;
		}
		for (int i = 0; i < rateList.size(); i++) {
			CurrRate currRate = (CurrRate) rateList.get(i);
			//
			// key == 当前币别号 + 对应币别号
			//
			String key = (currRate.getCurr().getCode() == null ? "" : currRate
					.getCurr().getCode())
					+ (currRate.getCurr1().getCode() == null ? "" : currRate
							.getCurr1().getCode());
			map.put(key, currRate.getRate());
		}

	}

	public static FecavReportVars getInstance(List dataSource,List rateList) {
		if (c == null) {
			c = new FecavReportVars(rateList);
			FecavReportVars.list = dataSource;
		}
		return c;
	}

	/**
	 * 获得总份数
	 * 
	 * @param pageNumber
	 * @return
	 */
	public Integer getTotalRecordCountByPage(int pageNumber) {
		return (list.size() - (25 * pageNumber) >= 0) ? 25
				: (list.size() - (25 * (pageNumber - 1)));
	}

	/**
	 * 获得单笔十万港币的个数（当前页)
	 * 
	 * @param pageNumber
	 * @param pageRecordCount
	 * @return
	 */

	public Integer getOvertop100000Count(int pageNumber, int pageRecordCount) {
		if (list == null) {
			return 0;
		}
		StrikeImpCustomsDeclaration s = null;
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i >= ((pageNumber - 1) * pageRecordCount)
					&& i < (pageNumber * pageRecordCount)) {
				s = (StrikeImpCustomsDeclaration) list.get(i);
				if (s.getStrikeMoney() != null) {
					double strikeMoney = 0.0;
					strikeMoney = s.getStrikeMoney()
							* this.getRateHKD(s.getCurr().getCode());
					if (strikeMoney >= 100000) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * 获得当前币制对应的港币汇率
	 * 
	 * @param currCode
	 * @return
	 */
	private double getRateHKD(String currCode) {
		double rateHKD = 1;
		if (currCode != null) {
			if ("110".equals(currCode.trim())) {
				rateHKD = 1;
			} else {
				//
				// key == 当前币别号 + 对应币别号 110 为港币号
				//
				String key = currCode + "110";
				rateHKD = map.get(key) == null ? 1 : map.get(key);
			}
		}
		return rateHKD;
	}

	public String getMemo(String strikeMoney, String currCode) {
		//
		// 如果港币不写入
		//
		if ("110".equals(currCode.trim())) {
			return "";
		}
		Double money = new Double(strikeMoney == null ? "0" : strikeMoney);
		if (money == 0) {
			return "";
		}
		int intMoney = new Double(money * this.getRateHKD(currCode)).intValue();
		return "HDK" + intMoney;
	}

}
