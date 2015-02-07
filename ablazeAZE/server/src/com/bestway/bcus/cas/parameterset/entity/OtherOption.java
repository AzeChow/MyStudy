
/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.parameterset.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 其它项目选项
 */
/**
 * @author Administrator
 *
 */
public class OtherOption extends BaseScmEntity {
    private static final long serialVersionUID             = CommonUtils
                                                                   .getSerialVersionUID();
    /**
     * 名称规格是否重复
     */
    private Boolean           isNameSpecRepeat  = false;
    /**
     * 进出仓栏只显示月结存数
     */
    private Boolean           isImportExportAccountBalance = false;
    
    /**
     * 进出仓栏小数位数控制
     */
    private Integer           inOutMaximumFractionDigits = 6;
    
    /**
     *海关账其他报表小数位数控制
     */
    private Integer           otherReportMaximumFractionDigits ;
    /**
     * 海关张所有报表小数位数控制
     */
    private Integer           allReportInOutMaximumFractionDigits = 6;
    
	/**
     * 是否在年审报表备注栏中显示转厂期初单
     */
    private Boolean			  isShowTransFactIncipient = false;
    
    /**
     * 年审报表中填表人
     */
    
    private String            fillPerson  = null;
    
    
    /**
     * 是否显示对应记录 当单据对应时数量小于0.01时
     */
    private Boolean			  isShowBillCorrRecord = false;
    
    
    /**
     * 短溢表 上次查询时的手册类型Bcs是否选中
     * @return
     */
    private Boolean isBcsSelected = false;
    
    /**
     * 短溢表 上次查询时的手册类型Bcs
     * @return
     */
    private Boolean isBcusSelected = false;
    
    /**
     * 短溢表 上次查询时的手册类型Bcs
     * @return
     */
    private Boolean isDzscSelected = false;
    
    /**
     * 短溢表 上次查询时的日期
     * @return
     */
    private Date lastTimedate;
    
    /**
     * 短溢表 上次查询时的数据来源
     * 是否来源盘点
     * @return
     */
    private Boolean isFromCheck = false;
    
    /**
     * 短溢表 上次查询时的数据来源
     * 是否体现非合同中料件资料 选项
     * @return
     */
    private Boolean isAll = false;
    
    
    
    /**
     * 自我核查 边角料对比表 开始日期
     * @return
     */
    private Date leftOverBeginDate;
    
    /**
     * 自我核查 边角料对比表 结束日期
     * @return
     */
    private Date leftOverEndDate;
    
    /**
     * 自我核查 是否选中电子化手册
     * @return
     */
    private Boolean leftOverIsBcs = false;
    
    /**
     * 自我核查 是否选中电子帐册
     * @return
     */
    private Boolean leftOverIsBcus = false;
    
    /**
     * 自我核查 是否选中电子手册
     * @return
     */
    private Boolean leftOverIsDzsc = false;
    
    /**
     * 自我核查 是否选中核销前打税
     * @return
     */
    private Boolean leftOverIsCancelBefore = false;
    
    /**
     * 平衡表 小数位位数
     */
    private Integer balanceDegree = Integer.valueOf(6);
    
    public String getFillPerson() {
		return fillPerson;
	}
    
    /**
     * 设置年审报表填写人
     */
	public void setFillPerson(String fillPerson) {
		this.fillPerson = fillPerson;
	}
	/**
     * 取得进出仓栏只显示月结存数
     * @return  isImportExportAccountBalance  进出仓栏只显示月结存数
     */
    public Boolean getIsImportExportAccountBalance() {
        return isImportExportAccountBalance;
    }
    /**
     * 判断名称规格是否重复
     * @return  isNameSpecRepeat  名称规格是否重复
     */
    public Boolean getIsNameSpecRepeat() {
        return isNameSpecRepeat;
    }
    /**
     *  设置进出仓栏只显示月结存数
     *  @param  isImportExportAccountBalance  进出仓栏只显示月结存数
     */
    public void setIsImportExportAccountBalance(
            Boolean isImportExportAccountBalance) {
        this.isImportExportAccountBalance = isImportExportAccountBalance;
    }
    /**
     * 设置名称规格是否重复标志
     * @param  isNameSpecRepeat 名称规格是否重复
     */
    public void setIsNameSpecRepeat(Boolean isNameSpecRepeat) {
        this.isNameSpecRepeat = isNameSpecRepeat;
    }
    
    /**
     * 获取进出仓小数位
     * @return
     */
	public Integer getInOutMaximumFractionDigits() {
		return inOutMaximumFractionDigits;
	}
	/**
	 * 设置进出仓栏小数位数控制
	 * @param inOutMaximumFractionDigits
	 */
	public void setInOutMaximumFractionDigits(Integer inOutMaximumFractionDigits) {
		this.inOutMaximumFractionDigits = inOutMaximumFractionDigits;
	}
	
	 /**
     * 获取海关张所有报表小数位数控制
     * @return
     */
    public Integer getAllReportInOutMaximumFractionDigits() {
		return allReportInOutMaximumFractionDigits;
	}
    
    /**
     * 设置海关张所有报表小数位数控制
     * @param allReportInOutMaximumFractionDigits
     */
	public void setAllReportInOutMaximumFractionDigits(
			Integer allReportInOutMaximumFractionDigits) {
		this.allReportInOutMaximumFractionDigits = allReportInOutMaximumFractionDigits;
	}
	
	/**
	 * @return the isShowTransFactIncipient
	 */
	public Boolean getIsShowTransFactIncipient() {
		return isShowTransFactIncipient;
	}
	/**
	 * 在年审报表的备注栏位统计转厂期初单
	 * @param isShowTransFactIncipient the isShowTransFactIncipient to set
	 */
	public void setIsShowTransFactIncipient(Boolean isShowTransFactIncipient) {
		this.isShowTransFactIncipient = isShowTransFactIncipient;
	}
	
	/**
	 * 是否显示对应记录 当单据对应时数量小于0.01时
	 * @return
	 */
	public Boolean getIsShowBillCorrRecord() {
		return isShowBillCorrRecord;
	}
	
	/**
	 * 是否显示对应记录 当单据对应时数量小于0.01时
	 * @param isShowBillCorrRecord
	 */
	public void setIsShowBillCorrRecord(Boolean isShowBillCorrRecord) {
		this.isShowBillCorrRecord = isShowBillCorrRecord;
	}

	/**
	 * 短溢表 上次查询时的手册类型Bcs是否选中
	 * @return
	 */
	public Boolean getIsBcsSelected() {
		return isBcsSelected;
	}

	/**
	 * 短溢表 上次查询时的手册类型Bcs是否选中
	 * @param isBcsSelected
	 */
	public void setIsBcsSelected(Boolean isBcsSelected) {
		this.isBcsSelected = isBcsSelected;
	}

	/**
	 * 短溢表 上次查询时的手册类型Bcs
	 * @return
	 */
	public Boolean getIsBcusSelected() {
		return isBcusSelected;
	}

	/**
	 * 短溢表 上次查询时的手册类型Bcs
	 * @param isBcusSelected
	 */
	public void setIsBcusSelected(Boolean isBcusSelected) {
		this.isBcusSelected = isBcusSelected;
	}

	/**
	 * 短溢表 上次查询时的手册类型Bcs
	 * @return
	 */
	public Boolean getIsDzscSelected() {
		return isDzscSelected;
	}

	/**
	 * 短溢表 上次查询时的手册类型Bcs
	 * @param isDzscSelected
	 */
	public void setIsDzscSelected(Boolean isDzscSelected) {
		this.isDzscSelected = isDzscSelected;
	}

	/**
	 * 短溢表 上次查询时的日期
	 * @return
	 */
	public Date getLastTimedate() {
		return lastTimedate;
	}

	/**
	 * 短溢表 上次查询时的日期
	 * @param lastTimedate
	 */
	public void setLastTimedate(Date lastTimedate) {
		this.lastTimedate = lastTimedate;
	}

	/**
	 * 短溢表 上次查询时的数据来源 是否来源盘点
	 * @return
	 */
	public Boolean getIsFromCheck() {
		return isFromCheck;
	}

	/**
	 * 短溢表 上次查询时的数据来源 是否来源盘点
	 * @param isFromCheck
	 */
	public void setIsFromCheck(Boolean isFromCheck) {
		this.isFromCheck = isFromCheck;
	}

	/**
	 * 短溢表 上次查询时的数据来源 是否体现非合同中料件资料 选项
	 * @return
	 */
	public Boolean getIsAll() {
		return isAll;
	}

	/**
	 * 短溢表 上次查询时的数据来源 是否体现非合同中料件资料 选项
	 * @param isAll
	 */
	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	/**
	 * 自我核查 边角料对比表 开始日期
	 * @return
	 */
	public Date getLeftOverBeginDate() {
		return leftOverBeginDate;
	}

	/**
	 * 自我核查 边角料对比表 开始日期
	 * @param leftOverBeginDate
	 */
	public void setLeftOverBeginDate(Date leftOverBeginDate) {
		this.leftOverBeginDate = leftOverBeginDate;
	}

	/**
	 * 自我核查 边角料对比表 结束日期
	 * @return
	 */
	public Date getLeftOverEndDate() {
		return leftOverEndDate;
	}

	/**
	 * 自我核查 边角料对比表 结束日期
	 * @param leftOverEndDate
	 */
	public void setLeftOverEndDate(Date leftOverEndDate) {
		this.leftOverEndDate = leftOverEndDate;
	}

	/**
	 * 自我核查 是否选中电子化手册
	 * @return
	 */
	public Boolean getLeftOverIsBcs() {
		return leftOverIsBcs;
	}

	/**
	 * 自我核查 是否选中电子化手册
	 * @param leftOverIsBcs
	 */
	public void setLeftOverIsBcs(Boolean leftOverIsBcs) {
		this.leftOverIsBcs = leftOverIsBcs;
	}

	/**
	 * 自我核查 是否选中电子帐册
	 * @return
	 */
	public Boolean getLeftOverIsBcus() {
		return leftOverIsBcus;
	}

	/**
	 * 自我核查 是否选中电子帐册
	 * @param leftOverIsBcus
	 */
	public void setLeftOverIsBcus(Boolean leftOverIsBcus) {
		this.leftOverIsBcus = leftOverIsBcus;
	}

	/**
	 * 自我核查 是否选中电子手册
	 * @return
	 */
	public Boolean getLeftOverIsDzsc() {
		return leftOverIsDzsc;
	}

	/**
	 * 自我核查 是否选中电子手册
	 * @param leftOverIsDzsc
	 */
	public void setLeftOverIsDzsc(Boolean leftOverIsDzsc) {
		this.leftOverIsDzsc = leftOverIsDzsc;
	}

	/**
	 * 自我核查 是否选中核销前打税
	 * @return
	 */
	public Boolean getLeftOverIsCancelBefore() {
		return leftOverIsCancelBefore;
	}

	/**
	 * 自我核查 是否选中核销前打税
	 * @param leftOverIsCancelBefore
	 */
	public void setLeftOverIsCancelBefore(Boolean leftOverIsCancelBefore) {
		this.leftOverIsCancelBefore = leftOverIsCancelBefore;
	}
	/**
     *海关账其他报表小数位数控制
     */
	public Integer getOtherReportMaximumFractionDigits() {
		return otherReportMaximumFractionDigits;
	}
	/**
     *海关账其他报表小数位数控制
     */
	public void setOtherReportMaximumFractionDigits(
			Integer otherReportMaximumFractionDigits) {
		this.otherReportMaximumFractionDigits = otherReportMaximumFractionDigits;
	}

	public Integer getBalanceDegree() {
		return balanceDegree;
	}

	public void setBalanceDegree(Integer balanceDegree) {
		this.balanceDegree = balanceDegree;
	}
	
}
