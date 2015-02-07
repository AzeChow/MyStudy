package com.bestway.customs.common.entity;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.constant.ProjectType;


/**
 * 存放报关行申报资料
 * @author hwy
 *
 */
public class CustomsBrokerDetail extends BaseScmEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customDeclarationId = null;
	/**
	 * 手册类型
	 */
	private Integer projectType = ProjectType.BCUS;
    /**
     * 申报日期
     */
    private Date declarationDate;
    
    /**
     * 报关行代码
     */
    private String customsBrokerCode;
    
    /**
     * 报关行名称
     */
    private String customsBrokerName;
    
    /**
     * 进出口类型
     */
    private Integer impExpFlag;
    
    /**
     * 贸易方式
     */
    private String tradeMode;
    
    /**
     * 申报人
     */
    private String customserName;
    /**
     * 账册号
     */
    private String emsNo;

    /**
     * 申报日期
     */
	public Date getDeclarationDate() {
		return declarationDate;
	}
    /**
     * 申报日期
     */
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
    
    /**
     * 报关行代码
     */
	public String getCustomsBrokerCode() {
		return customsBrokerCode;
	}
    
    /**
     * 报关行代码
     */
	public void setCustomsBrokerCode(String customsBrokerCode) {
		this.customsBrokerCode = customsBrokerCode;
	}
	  /**
     * 报关行名称
     */
	public String getCustomsBrokerName() {
		return customsBrokerName;
	}
	  /**
     * 报关行名称
     */
	public void setCustomsBrokerName(String customsBrokerName) {
		this.customsBrokerName = customsBrokerName;
	}
	/**
     * 进出口类型
     */
	public Integer getImpExpFlag() {
		return impExpFlag;
	}
	/**
     * 进出口类型
     */
	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}
    /**
     * 贸易方式
     */
	public String getTradeMode() {
		return tradeMode;
	}
    /**
     * 贸易方式
     */
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
	/**
     * 申报人
     */
	public String getCustomserName() {
		return customserName;
	}
	/**
     * 申报人
     */
	public void setCustomserName(String customserName) {
		this.customserName = customserName;
	}
	/**
     * 账册号
     */
	public String getEmsNo() {
		return emsNo;
	}
	/**
     * 账册号
     */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getCustomDeclarationId() {
		return customDeclarationId;
	}
	public void setCustomDeclarationId(String customDeclarationId) {
		this.customDeclarationId = customDeclarationId;
	}
}
