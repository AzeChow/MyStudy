/*
 * Created on 2004-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放伺机资料设置资料
 * 
 * @author Administrator
 */
public class MotorCode extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 司机名称
	 */
	private String name;

	/**
	 * 国内车牌
	 */
	private String homeCard;

	/**
	 * 香港车牌
	 */
	private String hongkongCard;

	/**
	 * 海关编码
	 */
	private String complex;

	/**
	 * IC卡
	 */
	private String icCard;

	/**
	 * 结关口岸
	 */
	private String customsPort;

	/**
	 * 运输单位
	 */
	private String trafficUnit;

	/**
	 * 运输单位地址
	 */
	private String trafficUnitAdd;

	/**
	 * 运输单位电话
	 */
	private String trafficUnitTel;

	/**
	 * 封条号码
	 */
	private String sealNumbers;

	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 货柜箱型号规格
	 */
	private String containerSpec;

	/**
	 * 获取代码
	 * 
	 * @return code 代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 *            代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取海关编码
	 * 
	 * @return complex 海关编码
	 */
	public String getComplex() {
		return complex;
	}

	/**
	 * 设置海关编码
	 * 
	 * @param complex
	 *            海关编码
	 */
	public void setComplex(String complex) {
		this.complex = complex;
	}

	/**
	 * 获取结关口岸
	 * 
	 * @return customsPort 结关口岸
	 */
	public String getCustomsPort() {
		return customsPort;
	}

	/**
	 * 设置结关口岸
	 * 
	 * @param customsPort
	 *            结关口岸
	 */
	public void setCustomsPort(String customsPort) {
		this.customsPort = customsPort;
	}

	/**
	 * 获取国内车牌
	 * 
	 * @return homeCard 国内车牌
	 */
	public String getHomeCard() {
		return homeCard;
	}

	/**
	 * 设置国内车牌
	 * 
	 * @param homeCard
	 *            国内车牌
	 */
	public void setHomeCard(String homeCard) {
		this.homeCard = homeCard;
	}

	/**
	 * 获取香港车牌
	 * 
	 * @return hongkongCard 香港车牌
	 */
	public String getHongkongCard() {
		return hongkongCard;
	}

	/**
	 * 设置香港车牌
	 * 
	 * @param hongkongCard
	 *            香港车牌
	 */
	public void setHongkongCard(String hongkongCard) {
		this.hongkongCard = hongkongCard;
	}

	/**
	 * 获取IC卡
	 * 
	 * @return icCard IC卡
	 */
	public String getIcCard() {
		return icCard;
	}

	/**
	 * 设置IC卡
	 * 
	 * @param icCard
	 *            IC卡
	 */
	public void setIcCard(String icCard) {
		this.icCard = icCard;
	}

	/**
	 * 获取司机名称
	 * 
	 * @return name 司机名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置司机名称
	 * 
	 * @param name
	 *            司机名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取运输单位
	 * 
	 * @return trafficUnit 运输单位
	 */
	public String getTrafficUnit() {
		return trafficUnit;
	}

	/**
	 * 设置运输单位
	 * 
	 * @param trafficUnit
	 *            运输单位
	 */
	public void setTrafficUnit(String trafficUnit) {
		this.trafficUnit = trafficUnit;
	}

	/**
	 * 获取运输单位地址
	 * 
	 * @return trafficUnitAdd 运输单位地址
	 */
	public String getTrafficUnitAdd() {
		return trafficUnitAdd;
	}

	/**
	 * 设置运输单位地址
	 * 
	 * @param trafficUnitAdd
	 *            运输单位地址
	 */
	public void setTrafficUnitAdd(String trafficUnitAdd) {
		this.trafficUnitAdd = trafficUnitAdd;
	}

	/**
	 * 获取运输单位电话
	 * 
	 * @return trafficUnitTel 运输单位电话
	 */
	public String getTrafficUnitTel() {
		return trafficUnitTel;
	}

	/**
	 * 设置运输单位电话
	 * 
	 * @param trafficUnitTel
	 *            运输单位电话
	 */
	public void setTrafficUnitTel(String trafficUnitTel) {
		this.trafficUnitTel = trafficUnitTel;
	}

	/**
	 * 获取封条号码
	 * 
	 * @return
	 */
	public String getSealNumbers() {
		return sealNumbers;
	}

	/**
	 * 设置封条号码
	 * 
	 * @param sealNumbers
	 */
	public void setSealNumbers(String sealNumbers) {
		this.sealNumbers = sealNumbers;
	}

	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * 设置密码
	 * 
	 * @param passWord
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * 获取货柜箱型号规格
	 * 
	 * @return
	 */
	public String getContainerSpec() {
		return containerSpec;
	}

	/**
	 * 设置货柜箱型号规格
	 * 
	 * @param containerSpec
	 */
	public void setContainerSpec(String containerSpec) {
		this.containerSpec = containerSpec;
	}

}
