package com.bestway.common.authority.entity;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 用户IP地址
 * 
 * @author Administrator
 * 
 */
public class AclIPaddress extends BaseScmEntity implements Principal {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 起始IP
	 */
	String begianIP = null;

	/**
	 * 结束IP
	 */
	String endIP = null;

	/**
	 * 备注
	 */
	String explain = null;

	/**
	 * 创建时间
	 */
	Date createDate = null;

	/**
	 * 修改时间
	 */
	Date lastModifDate = null;
	/**
	 * 用于显示起始IP，不保存数据库
	 */
	private String showBegianIP = null;
	/**
	 * 用于显示结束IP，不保存数据库
	 */
	private String showEndIP = null;

	public String getBegianIP() {
		return begianIP;
	}

	public void setBegianIP(String begianIP) {
		this.begianIP = begianIP;
	}

	public String getEndIP() {
		return endIP;
	}

	public void setEndIP(String endIP) {
		this.endIP = endIP;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * getName 是实现的Principal接口的方法
	 * 
	 * @return String
	 */
	public String getName() {
		return this.getId().toString();
	}

	public Date getCreateDate() {

		return createDate;
	}

	public String getShowCreateDate() {
		if (createDate != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(createDate);
		}
		return "";
	}

	public String getShowLastModifDate() {
		if (lastModifDate != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(lastModifDate);
		}
		return "";
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifDate() {
		return lastModifDate;
	}

	public void setLastModifDate(Date lastModifDate) {
		this.lastModifDate = lastModifDate;
	}

	private String makeIPToMinNum(String ip) {
		if (ip == null) {
			return "";
		}
		String resultstr = "";
		String[] strs = CommonUtils.split(ip.trim(), ".");
		for (int p = 0; p < strs.length; p++) {
			if (strs[p].contains(".")) {
				strs[p] = strs[p].substring(0, strs.length - 1);
			}
		}
		int[] ints = new int[strs.length];
		try {
			for (int t = 0; t < strs.length; t++) {
				ints[t] = Integer.parseInt(strs[t]);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		for (int k = 0; k < ints.length; k++) {
			if (new Integer(strs[k]).toString().length() == 1) {
				resultstr += (new Integer(strs[k]).toString() + ".");
			} else if (new Integer(strs[k]).toString().length() == 2) {
				resultstr += (new Integer(strs[k]).toString() + ".");
			} else {
				resultstr += (new Integer(strs[k]).toString() + ".");
			}
		}

		return resultstr.substring(0, resultstr.length() - 1);
	}

	public String getShowBegianIP() {
		if (showBegianIP == null) {
			showBegianIP = makeIPToMinNum(begianIP);
		}
		return showBegianIP;
	}

	public String getShowEndIP() {
		if (showEndIP == null) {
			showEndIP = makeIPToMinNum(endIP);
		}
		return showEndIP;
	}
}
