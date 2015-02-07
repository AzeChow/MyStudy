package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 客户供应商导入栏位设置
 * @author 石小凯
 */
public class ScmCocControl extends BaseScmEntity {
	private static final long serialVersionUID = -2126856884106304212L;

	/**
	 * 关务海关注册公司
	 */
	private String briefName;
	
	/**
	 * 运抵国
	 */
	private String  countryName;
	
	/**
	 * 指运港
	 */
	private String portLinName;
	
	/**
	 * 产销国
	 */
	private String country2Name;
	
	/**
	 * 出口口岸
	 */
	private String customsName;

	/**
	 * 所属海关
	 */
	private String belongToCustomsName;

	/**
	 * 运输方式
	 */
	private String transfName;

	/**
	 * 码头
	 */
	private String predock;

	/**
	 * 包装种类
	 */
	private String wrapType;

	/**
	 * 贸易方式
	 */
	private String tradeMode;

	/**
	 * 获取关务海关注册公司
	 * @return
	 */
	public String getBriefName() {
		return briefName;
	}

	/**
	 * 设置关务海关注册公司
	 * @param briefName
	 */
	public void setBriefName(String briefName) {
		this.briefName = briefName;
	}

	/**
	 * 获取运抵国
	 * @return
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * 设置运抵国
	 * @param countryName
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * 获取指运港
	 * @return
	 */
	public String getPortLinName() {
		return portLinName;
	}

	/**
	 * 设置指运港
	 * @param portLinName
	 */
	public void setPortLinName(String portLinName) {
		this.portLinName = portLinName;
	}

	/**
	 * 获取产销国
	 * @return
	 */
	public String getCountry2Name() {
		return country2Name;
	}

	/**
	 * 设置产销国
	 * @param country2Name
	 */
	public void setCountry2Name(String country2Name) {
		this.country2Name = country2Name;
	}

	/**
	 * 获取出口口岸
	 * @return
	 */
	public String getCustomsName() {
		return customsName;
	}

	/**
	 * 设置出口口岸
	 * @param customsName
	 */
	public void setCustomsName(String customsName) {
		this.customsName = customsName;
	}

	/**
	 * 获取所属海关
	 * @return
	 */
	public String getBelongToCustomsName() {
		return belongToCustomsName;
	}

	/**
	 * 设置所属海关
	 * @param belongToCustomsName
	 */
	public void setBelongToCustomsName(String belongToCustomsName) {
		this.belongToCustomsName = belongToCustomsName;
	}

	/**
	 * 获取运输方式
	 * @return
	 */
	public String getTransfName() {
		return transfName;
	}

	/**
	 * 设置运输方式
	 * @param transfName
	 */
	public void setTransfName(String transfName) {
		this.transfName = transfName;
	}

	/**
	 * 获取码头
	 * @return
	 */
	public String getPredock() {
		return predock;
	}

	/**
	 * 设置码头
	 * @param predock
	 */
	public void setPredock(String predock) {
		this.predock = predock;
	}

	/**
	 * 获取包装种类
	 * @return
	 */
	public String getWrapType() {
		return wrapType;
	}

	/**
	 * 设置包装种类
	 * @param wrapType
	 */
	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}

	/**
	 * 获取贸易方式
	 * @return
	 */
	public String getTradeMode() {
		return tradeMode;
	}

	/**
	 * 设置贸易方式
	 * @param tradeMode
	 */
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
	
	

}
