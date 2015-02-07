/*
 * Created on 2004-10-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * 单据与进口报关单对比
 * @author luosheng
 * 2009/09/07 * 
 */
public class BillCustomBillCmpImgInfo implements Serializable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * 料件名称
	 */
	private String ptName;
	 /**
	 * 料件规格
	 */
	private String ptSpec;
	 /**
	 * 料件单位名称
	 */
	private String ptUnitName;
	 /**
	 * 直接报关进口数量
	 */
	private double f2;
	 /**
	 * 直接报关进口数量
	 */
	private double f2CustomNum;
	 /**
	 *退运出口数量
	 */
	private double f15;
	 /**
	 *退运出口数量
	 */
	private double f15CustomNum;
	 /**
	 *料件退换数量
	 */
	private double fimgExchange;
	/**
	 *料件退换数量
	 */
	private double fimgExchangeCustomNum;
	/**
	 *料件复出数量
	 */
    private double fimgReOut;
     /**
	 *料件复出数量
	 */
    private double fimgReOutCustomNum;
	
	
    /**
     * 取得料件复出数量
	 * @return 料件复出数量(料件复出)
	 */
	public double getFimgReOut() {
        return fimgReOut;
    }
	/**
     * 设置料件复出数量
	 * @param imgReOut 料件复出数量(料件复出)
	 */
    public void setFimgReOut(double imgReOut) {
        fimgReOut = imgReOut;
    }
    /**
     * 取得料件复出数量(用于报关单)
	 * @return 料件复出数量(料件复出)(用于报关单)
	 */
    public double getFimgReOutCustomNum() {
        return fimgReOutCustomNum;
    }
    /**
     * 设置料件复出数量
	 * @param imgReOutCustomNum 料件复出数量(料件复出)(用于报关单)
	 */
    public void setFimgReOutCustomNum(double imgReOutCustomNum) {
        fimgReOutCustomNum = imgReOutCustomNum;
    }
    /**
     * 构造函数
	 * 初始化料件名称,料件规格,单位名称,直接报关进口数量,直接报关进口数量(用于报关单),退运出口数量（料件复出）,料件复出数量（料件复出）(用于报关单)
	 *               料件退换数量,料件退换数量(用于报关单),料件复出数量（料件复出）,料件复出数量（料件复出）(用于报关单).
	 */
    public BillCustomBillCmpImgInfo(){
		ptName = "";
		ptSpec = "";
		ptUnitName = "";
		
		f2 = 0;
		f2CustomNum = 0;
		
		f15 = 0;
		f15CustomNum = 0;
		
		fimgExchange = 0;
		fimgExchangeCustomNum = 0;
        
        fimgReOut = 0;
        fimgReOutCustomNum = 0;
        
	}
    
    /**
	 * 取得键值
	 * @return 料件名称+料件规格+单位名称
	 */
    public String getKey(){
        String ptSpec  = (this.ptSpec == null || "".equals(this.ptSpec))?"":"/"+this.ptSpec;   
        String ptUnitName = (this.ptUnitName == null || "".equals(this.ptUnitName))?"":"/"+this.ptUnitName; 
        return this.ptName+ptSpec+ptUnitName;
    }
	
	/**
	 * 取得退运出口数量（料件复出）
	 * @return 料件退换数量+料件复出数量（料件复出）
	 */
	public double getF15() {
		return this.fimgExchange + this.fimgReOut;
	}
	/**
	 * 设置退运出口数量（料件复出）
	 * @param f15 退运出口数量（料件复出）.
	 */
	public void setF15(double f15) {
		this.f15 = f15;
	}
	/**
	 * 取得退运出口数量（料件复出）(用于报关单)
	 * @return 料件退换数量(用于报关单)+料件复出数量（料件复出）(用于报关单).
	 */
	public double getF15CustomNum() {
        return this.fimgExchangeCustomNum + this.fimgReOutCustomNum;
	}
	/**
	 * 设置退运出口数量（料件复出）(用于报关单)
	 * @param customNum 退运出口数量（料件复出）(用于报关单).
	 */
	public void setF15CustomNum(double customNum) {
		f15CustomNum = customNum;
	}
	/**
	 * 取得直接报关进口数量
	 * @return 直接报关进口数量.
	 */
	public double getF2() {
		return f2;
	}
	/**
	 * 设置直接报关进口数量
	 * @param f2 直接报关进口数量.
	 */
	public void setF2(double f2) {
		this.f2 = f2;
	}
	/**
	 * 取得直接报关进口数量(用于报关单)
	 * @return 直接报关进口数量(用于报关单).
	 */
	public double getF2CustomNum() {
		return f2CustomNum;
	}
	/** 
	 * 设置直接报关进口数量(用于报关单)
	 * @param customNum 直接报关进口数量(用于报关单).
	 */
	public void setF2CustomNum(double customNum) {
		f2CustomNum = customNum;
	}
	/**
	 * 取得料件退换数量
	 * @return 料件退换数量.
	 */
	public double getFimgExchange() {
		return fimgExchange;
	}
	/**
	 * 设置料件退换数量
	 * @param imgExchange 料件退换数量.
	 */
	public void setFimgExchange(double imgExchange) {
		fimgExchange = imgExchange;
	}
	/**
	 * 取得料件退换数量(用于报关单)
	 * @return 料件退换数量(用于报关单).
	 */
	public double getFimgExchangeCustomNum() {
		return fimgExchangeCustomNum;
	}
	/**
	 * 设置料件退换数量(用于报关单)
	 * @param imgExchangeCustomNum 料件退换数量(用于报关单).
	 */
	public void setFimgExchangeCustomNum(double imgExchangeCustomNum) {
		fimgExchangeCustomNum = imgExchangeCustomNum;
	}
	/**
	 * 取得料件名称
	 * @return 料件名称.
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * 设置料件名称
	 * @param ptName 料件名称.
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	/**
	 * 取得料件规格
	 * @return 料件规格.
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**设置料件规格
	 * @param ptSpec 料件规格.
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 取得单位名称
	 * @return 单位名称.
	 */
	public String getPtUnitName() {
		return ptUnitName;
	}
	/**设置单位名称
	 * @param ptUnitName 单位名称.
	 */
	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}
}
