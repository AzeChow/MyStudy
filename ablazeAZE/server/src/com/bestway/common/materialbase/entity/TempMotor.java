package com.bestway.common.materialbase.entity;

import java.io.Serializable;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

public class TempMotor extends BaseScmEntity
{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	private MotorCode mc=null;
	/**
	 * 错误信息
	 */
	private String errinfo =null;
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

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getHomeCard()
	{
		return homeCard;
	}

	public void setHomeCard(String homeCard)
	{
		this.homeCard = homeCard;
	}

	public String getHongkongCard()
	{
		return hongkongCard;
	}

	public void setHongkongCard(String hongkongCard)
	{
		this.hongkongCard = hongkongCard;
	}

	public String getComplex()
	{
		return complex;
	}

	public void setComplex(String complex)
	{
		this.complex = complex;
	}

	public String getIcCard()
	{
		return icCard;
	}

	public void setIcCard(String icCard)
	{
		this.icCard = icCard;
	}

	public String getCustomsPort()
	{
		return customsPort;
	}

	public void setCustomsPort(String customsPort)
	{
		this.customsPort = customsPort;
	}

	public String getTrafficUnit()
	{
		return trafficUnit;
	}

	public void setTrafficUnit(String trafficUnit)
	{
		this.trafficUnit = trafficUnit;
	}

	public String getTrafficUnitAdd()
	{
		return trafficUnitAdd;
	}

	public void setTrafficUnitAdd(String trafficUnitAdd)
	{
		this.trafficUnitAdd = trafficUnitAdd;
	}

	public String getTrafficUnitTel()
	{
		return trafficUnitTel;
	}

	public void setTrafficUnitTel(String trafficUnitTel)
	{
		this.trafficUnitTel = trafficUnitTel;
	}

	public String getErrinfo()
	{
		return errinfo;
	}

	public void setErrinfo(String errinfo)
	{
		this.errinfo = errinfo;
	}

	public MotorCode getMc()
	{
		return mc;
	}

	public void setMc(MotorCode mc)
	{
		this.mc = mc;
	}
}
