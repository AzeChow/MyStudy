/*
 * Created on 2005-3-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fixtureonorder.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsImg;

/**
 * 存放合同备案料件资料
 *
 * @author fhz
 *
 */
public class FixtureContractItems extends BaseEmsImg {
    private static final long serialVersionUID = CommonUtils
                                                       .getSerialVersionUID();

    /**
     * 合同表头
     */
    private FixtureContract       contract; 
    
    /**
     * 分协议号
     */
    private String secondProtocol;
    
    /**
     * 进口日期
     */
    private Date importDate;
    
    /**
     * 对应的料号
     */
    private String ptNo;

    /**
     * 数量
     */
    private Double            amount; 

   /**
     * 总金额
     */
    private Double            totalPrice;  

    /**
     * 原产地
     */
    private Country           country;                    

   /**
     * 征减免税方式
     */
    private LevyMode          levyMode         = null;                      
    
    /**
     * 合同状态
     */
    private String declareState;
    
    
    /**
     * 合同变更时是否有修改
     */
    private Boolean isChange=false;
    
    /**
     *  当合同变更时,记录数量运来的数量,作为打印变更合同时用
     */
    private Double      changeAmount;
    
    /**
     *  设备类型 0.为不作价设备，1.为借用设备
     */
    private Integer      fixKind;
    
    
    /**
     * 获取数量
     * 
     * @return  amount 数量
     */
    public Double getAmount() {
        return amount;
    }

    /**
	 * 设置数量
	 * 
	 * @param amount 数量
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取合同表头
     * 
     * @return contract 合同表头
     */
    public FixtureContract getContract() {
        return contract;
    }

    /**
	 * 设置合同表头
	 * 
	 * @param contract 合同表头
     */
    public void setContract(FixtureContract contract) {
        this.contract = contract;
    }

    /**
     * 获取原产地
     * 
     * @return country 原产地
     */
    public Country getCountry() {
        return country;
    }

    /**
	 * 设置原产地
	 * 
	 * @param country 原产地
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * 获取 总金额 = 单价 * 申报数量
     * @return totalPrice 总金额
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
	 * 设置总金额
	 * 
	 * @param totalPrice 总金额
     */
    public void setTotalPrice(Double totalPrice) {        
        this.totalPrice = totalPrice;
    }

    /**
     * 获取serialVersionUID
     * 
     * @return Returns the serialVersionUID.
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    /**
     * 获取征减免税方式
     * 
     * @return levyMode 征减免税方式
     */
    public LevyMode getLevyMode() {
        return levyMode;
    }
    /**
	 * 设置征减免税方式
	 * 
	 * @param levyMode 征减免税方式
     */
    public void setLevyMode(LevyMode levyMode) {
        this.levyMode = levyMode;
    }
    /**
     * 获取合同状态，1：申请变更
     * 
     * @return declareState 合同状态，1：申请变更
     */
    public String getDeclareState() {
        return declareState;
    }
    /**
	 * 设置合同状态，1：申请变更
	 * 
	 * @param declareState 合同状态，1：申请变更
     */
    public void setDeclareState(String declareState) {
        this.declareState = declareState;
    }
	
    public Boolean getIsChange() {
		return isChange;
	}

	public void setIsChange(Boolean isChange) {
		this.isChange = isChange;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeMount) {
		this.changeAmount = changeMount;
	}

	public Integer getFixKind() {
		return fixKind;
	}

	public void setFixKind(Integer fixKind) {
		this.fixKind = fixKind;
	}

	public String getSecondProtocol() {
		return secondProtocol;
	}

	public void setSecondProtocol(String secondProtocol) {
		this.secondProtocol = secondProtocol;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	
	
}
