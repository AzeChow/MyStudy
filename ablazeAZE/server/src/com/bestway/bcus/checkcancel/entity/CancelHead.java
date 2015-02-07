/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 给核销表头继承
*/
public class CancelHead extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    /**
	 * 帐册编号
	 */
	private String emsNo;      
	
    /**
	 * 批准证号
	 */
	private String emsApprNo;  
	
    /**
	 * 报核类型
	 */
	private String declareType;
	
    /**
	 * 核销次数
	 */
	private String cancelTimes; 
	
    /**
	 * 开始有效期
	 */
	private Date beginDate;   
	
    /**
	 * 结束有效期
	 */
	private Date endDate;      
	
    /**
	 * 进口报关单份数
	 */
	private int inportCustomNum;   
	
    /**
	 * 出口报关单份数
	 */
	private int outportCustomNum; 
	
    /**
	 * 报核料件项数
	 */
	private int declareImgNum;  
	
    /**
	 * 报核成品项数
	 */
	private int declareExgNum;    
	
    /**
	 * 本期进口总金额
	 */
	private Double thisInportMoney;  
	
    /**
	 * 本期出口总金额
	 */
	private Double thisOutportMoney;  
	
    /**
	 * 期初料件总金额
	 */
	private Double beginImgMoney;    
	
    /**
	 * 期末结余料件总金额
	 */
	private Double endBalanceImgMoney; 
	
    /**
	 * 内销金额
	 */
	private Double innerCancelMoney;  
	
    /**
	 * 内销补税税额
	 */
	private Double innerCancelFillTaxMoney; 
	
    /**
	 * 出口成品耗用料件金额
	 */
	private Double outportExgUseImgNum;   
	
    /**
	 * 退运料件金额
	 */
	private Double exitImgMoney;          
	
    /**
	 * 备注
	 */
	private String note;                
	
    /**
	 * 录入日期
	 */
	private Date inputDate;              
	
    /**
	 * 录入员代号
	 */
	private String inputUser;          
	
    /**
	 * 申报日期
	 */
	private Date declareDate;           
	
    /**
	 * 申报时间
	 */
	private String declareTime;          
	
	
    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;       
	
    /**
	 * 审批标志
	 */
	private String checkMark;     
	
    /**
	 * 执行标志
	 */
	private String exeMark;     
	
    /**
	 * 帐册状态
	 */
	private String declareState; 
	
	/**
	 * 比例
	 */
	private Double scale;
	/**
	 * 财务审核时间
	 */
	private Date checkStaffTime;
	/**
	 * 财务审核人员
	 */
	private String checkStaffName;
	/**
	 * 余料结转出口
	 */
	private Double remainExportMoney;
	
	
	
	/**
	 * 
	 * 模拟期初料件总金额
	 */
	private Double simulateBeginImgMoney;
	/**
	 * 
	 * 模拟本期进口总金额
	 */
	private Double simulateThisInportMoney;
	/**
	 * 
	 * 模拟本期出口总金额
	 */
	private Double simulateThisOutportMoney;
	/**
	 * 
	 * 模拟出口成品耗用总金额
	 */
	private Double simulateOutportExgUseImgNum;
	
	/**
	 * 
	 * 模拟内销金额
	 */
	private Double simulateInnerCancelMoney;  
	/**
	 * 
	 * 模拟退运料件金额
	 */
	private Double simulateExitImgMoney; 
	/**
	 * 
	 * 模拟余料结转出口 ;
	 */
	private Double simulateRemainExportMoney;
	/**
	 * 
	 * 模拟期末结余料件总金额
	 */
	private Double simulateEndBalanceImgMoney;
	
	
	
	/**
	 * 获取开始有效期
	 * 
	 * @return beginDate 开始有效期
	 */
	public Date getBeginDate() {
		return beginDate;
	}
		
	/**
	 * 设置开始有效期
	 * 
	 * @param beginDate 开始有效期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 *  
	 * 
	 * @return beginImgMoney 期初料件总金额
	 */
	public Double getBeginImgMoney() {
		return beginImgMoney;
	}
		
	/**
	 * 设置期初料件总金额
	 * 
	 * @param beginImgMoney 期初料件总金额
	 */
	public void setBeginImgMoney(Double beginImgMoney) {
		this.beginImgMoney = beginImgMoney;
	}
	
	/**
	 * 获取核销次数
	 * 
	 * @return cancelTimes 核销次数
	 */
	public String getCancelTimes() {
		return cancelTimes;
	}
		
	/**
	 * 设置核销次数
	 * 
	 * @param cancelTimes 核销次数
	 */
	public void setCancelTimes(String cancelTimes) {
		this.cancelTimes = cancelTimes;
	}
	
	/**
	 * 获取申报日期
	 * 
	 * @return declareDate 申报日期
	 */
	public Date getDeclareDate() {
		return declareDate;
	}
		
	/**
	 * 设置申报日期
	 * 
	 * @param declareDate 
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}
	
	/**
	 * 获取报核成品项数
	 * 
	 * @return declareExgNum 报核成品项数
	 */
	public int getDeclareExgNum() {
		return declareExgNum;
	}
		
	/**
	 * 设置报核成品项数
	 * 
	 * @param declareExgNum 报核成品项数
	 */
	public void setDeclareExgNum(int declareExgNum) {
		this.declareExgNum = declareExgNum;
	}
	
	/**
	 * 获取报核料件项数
	 * 
	 * @return declareImgNum 报核料件项数
	 */
	public int getDeclareImgNum() {
		return declareImgNum;
	}
		
	/**
	 * 设置报核料件项数
	 * 
	 * @param declareImgNum 报核料件项数
	 */
	public void setDeclareImgNum(int declareImgNum) {
		this.declareImgNum = declareImgNum;
	}
	
	
	/**
	 * 获取报核类型     预报核：正式报核 
	 * 
	 * @return declareType 报核类型     预报核：正式报核 
	 */
	public String getDeclareType() {
		return declareType;
	}
		
	/**
	 * 设置报核类型     预报核：正式报核 
	 * 
	 * @param declareType 报核类型     预报核：正式报核 
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}
	
	/**
	 * 获取批准证号
	 * 
	 * @return emsApprNo 批准证号
	 */
	public String getEmsApprNo() {
		return emsApprNo;
	}
		
	/**
	 * 设置批准证号
	 * 
	 * @param emsApprNo 批准证号
	 */
	public void setEmsApprNo(String emsApprNo) {
		this.emsApprNo = emsApprNo;
	}
	
	/**
	 * 获取帐册编号
	 * 
	 * @return emsNo 帐册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}
		
	/**
	 * 设置帐册编号
	 * 
	 * @param emsNo 帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	
	/**
	 * 获取期末结余料件总金额
	 * 
	 * @return endBalanceImgMoney 期末结余料件总金额
	 */
	public Double getEndBalanceImgMoney() {
		return endBalanceImgMoney;
	}
		
	/**
	 * 设置期末结余料件总金额
	 * 
	 * @param endBalanceImgMoney 期末结余料件总金额
	 */
	public void setEndBalanceImgMoney(Double endBalanceImgMoney) {
		this.endBalanceImgMoney = endBalanceImgMoney;
	}
	
	/**
	 * 获取结束有效期
	 * 
	 * @return endDate 结束有效期
	 */
	public Date getEndDate() {
		return endDate;
	}
		
	/**
	 * 设置结束有效期
	 * 
	 * @param endDate 结束有效期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取退运料件金额
	 * 
	 * @return exitImgMoney 退运料件金额
	 */
	public Double getExitImgMoney() {
		return exitImgMoney;
	}
		
	/**
	 * 设置退运料件金额
	 * 
	 * @param exitImgMoney 退运料件金额
	 */
	public void setExitImgMoney(Double exitImgMoney) {
		this.exitImgMoney = exitImgMoney;
	}
	
	/**
	 * 获取内销补税税额
	 * 
	 * @return innerCancelFillTaxMoney 内销补税税额
	 */
	public Double getInnerCancelFillTaxMoney() {
		return innerCancelFillTaxMoney;
	}
		
	/**
	 * 设置内销补税税额
	 * 
	 * @param innerCancelFillTaxMoney 内销补税税额
	 */
	public void setInnerCancelFillTaxMoney(Double innerCancelFillTaxMoney) {
		this.innerCancelFillTaxMoney = innerCancelFillTaxMoney;
	}
	
	/**
	 * 获取内销金额
	 * 
	 * @return innerCancelMoney 内销金额
	 */
	public Double getInnerCancelMoney() {
		return innerCancelMoney;
	}
		
	/**
	 * 设置内销金额
	 * 
	 * @param innerCancelMoney 内销金额
	 */
	public void setInnerCancelMoney(Double innerCancelMoney) {
		this.innerCancelMoney = innerCancelMoney;
	}
	
	/**
	 * 获取进口报关单份数
	 * 
	 * @return inportCustomNum 进口报关单份数
	 */
	public int getInportCustomNum() {
		return inportCustomNum;
	}
		
	/**
	 * 设置进口报关单份数
	 * 
	 * @param inportCustomNum 进口报关单份数
	 */
	public void setInportCustomNum(int inportCustomNum) {
		this.inportCustomNum = inportCustomNum;
	}
	
	/**
	 * 获取录入日期
	 * 
	 * @return inputDate 录入日期
	 */
	public Date getInputDate() {
		return inputDate;
	}
		
	/**
	 * 设置录入日期
	 * 
	 * @param inputDate 录入日期
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	/**
	 * 获取录入员代号
	 * 
	 * @return inputUser 录入员代号
	 */
	public String getInputUser() {
		return inputUser;
	}
		
	/**
	 * 设置录入员代号
	 * 
	 * @param inputUser 录入员代号
	 */
	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}
	
	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}
		
	/**
	 * 设置备注
	 * 
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * 获取出口报关单份数
	 * 
	 * @return outportCustomNum 出口报关单份数
	 */
	public int getOutportCustomNum() {
		return outportCustomNum;
	}
		
	/**
	 * 设置出口报关单份数
	 * 
	 * @param outportCustomNum 出口报关单份数
	 */
	public void setOutportCustomNum(int outportCustomNum) {
		this.outportCustomNum = outportCustomNum;
	}
	
	/**
	 * 获取出口成品耗用料件金额
	 * 
	 * @return outportExgUseImgNum 出口成品耗用料件金额
	 */
	public Double getOutportExgUseImgNum() {
		return outportExgUseImgNum;
	}
		
	/**
	 * 设置出口成品耗用料件金额
	 * 
	 * @param outportExgUseImgNum 出口成品耗用料件金额
	 */
	public void setOutportExgUseImgNum(Double outportExgUseImgNum) {
		this.outportExgUseImgNum = outportExgUseImgNum;
	}
	
	/**
	 * 获取本期进口总金额
	 * 
	 * @return thisInportMoney 本期进口总金额
	 */
	public Double getThisInportMoney() {
		return thisInportMoney;
	}
		
	/**
	 * 设置本期进口总金额
	 * 
	 * @param thisInportMoney 本期进口总金额
	 */
	public void setThisInportMoney(Double thisInportMoney) {
		this.thisInportMoney = thisInportMoney;
	}
	
	/**
	 * 获取本期出口总金额
	 * 
	 * @return thisOutportMoney 本期出口总金额
	 */
	public Double getThisOutportMoney() {
		return thisOutportMoney;
	}
		
	/**
	 * 设置本期出口总金额
	 * 
	 * @param thisOutportMoney 本期出口总金额
	 */
	public void setThisOutportMoney(Double thisOutportMoney) {
		this.thisOutportMoney = thisOutportMoney;
	}
	
	/**
	 * 获取审批标志
	 * 
	 * @return checkMark 审批标志
	 */
	public String getCheckMark() {
		return checkMark;
	}
		
	/**
	 * 设置审批标志
	 * 
	 * @param checkMark 审批标志
	 */
	public void setCheckMark(String checkMark) {
		this.checkMark = checkMark;
	}
	
	/**
	 * 获取执行标志
	 * 
	 * @return exeMark 执行标志
	 */
	public String getExeMark() {
		return exeMark;
	}
		
	/**
	 * 设置执行标志
	 * 
	 * @param exeMark 执行标志
	 */
	public void setExeMark(String exeMark) {
		this.exeMark = exeMark;
	}
	
	/**
	 * 获取修改标志
	 * 
	 * @return modifyMark 修改标志
	 */
	public String getModifyMark() {
		return modifyMark;
	}
		
	/**
	 * 设置修改标志
	 * 
	 * @param modifyMark 修改标志
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
	
	/**
	 * 获取帐册状态 1，正在预录入，2，等待审批
	 * 
	 * @return declareState 帐册状态 1，正在预录入，2，等待审批
	 */
	public String getDeclareState() {
		return declareState;
	}
		
	/**
	 * 设置帐册状态 1，正在预录入，2，等待审批
	 * 
	 * @param declareState 帐册状态 1，正在预录入，2，等待审批
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}
	
	/**
	 * 获取申报时间
	 * 
	 * @return declareTime 申报时间
	 */
	public String getDeclareTime() {
		return declareTime;
	}
	
	/**
	 * 设置申报时间
	 * 
	 * @param declareTime 申报时间
	 */
	public void setDeclareTime(String declareTime) {
		this.declareTime = declareTime;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public Date getCheckStaffTime() {
		return checkStaffTime;
	}

	public void setCheckStaffTime(Date checkStaffTime) {
		this.checkStaffTime = checkStaffTime;
	}

	public String getCheckStaffName() {
		return checkStaffName;
	}

	public void setCheckStaffName(String checkStaffName) {
		this.checkStaffName = checkStaffName;
	}

	/**
	 * 获取期初料件总金额(模拟)
	 */
	public Double getSimulateBeginImgMoney() {
		return simulateBeginImgMoney;
	}
	/**
	 * 设置期初料件总金额(模拟)
	 * 
	 * @param simulateBeginImgMoney 期初料件总金额
	 */
	public void setSimulateBeginImgMoney(Double simulateBeginImgMoney) {
		this.simulateBeginImgMoney = simulateBeginImgMoney;
	}

	/**
	 * 获取本期进口总金额(模拟)
	 * 
	 * @return simulateThisInportMoney 本期进口总金额
	 */
	public Double getSimulateThisInportMoney() {
		return simulateThisInportMoney;
	}
	/**
	 * 
	 * 设置本期进口总金额(模拟)
	 */
	public void setSimulateThisInportMoney(Double simulateThisInportMoney) {
		this.simulateThisInportMoney = simulateThisInportMoney;
	}
	/**
	 * 获取本期出口总金额(模拟)
	 */
	public Double getSimulateThisOutportMoney() {
		return simulateThisOutportMoney;
	}
	/**
	 * 设置本期出口总金额(模拟)
	 * 
	 * @param simulateThisOutportMoney 本期出口总金额
	 */
	public void setSimulateThisOutportMoney(Double simulateThisOutportMoney) {
		this.simulateThisOutportMoney = simulateThisOutportMoney;
	}
	/**
	 * 获取出口成品耗用料件金额(模拟)
	 */
	public Double getSimulateOutportExgUseImgNum() {
		return simulateOutportExgUseImgNum;
	}
	/**
	 * 设置出口成品耗用料件金额(模拟)
	 * 
	 * @param simulateOutportExgUseImgNum 出口成品耗用料件金额
	 */
	public void setSimulateOutportExgUseImgNum(Double simulateOutportExgUseImgNum) {
		this.simulateOutportExgUseImgNum = simulateOutportExgUseImgNum;
	}
	/**
	 * 获取内销金额(模拟)
	 */
	public Double getSimulateInnerCancelMoney() {
		return simulateInnerCancelMoney;
	}
	/**
	 * 设置内销金额
	 * 
	 * @param simulateInnerCancelMoney 内销金额(模拟)
	 */
	public void setSimulateInnerCancelMoney(Double simulateInnerCancelMoney) {
		this.simulateInnerCancelMoney = simulateInnerCancelMoney;
	}
	/**
	 * 
	 * 获取退运料件金额(模拟)
	 */
	public Double getSimulateExitImgMoney() {
		return simulateExitImgMoney;
	}
	/**
	 * 设置退运料件金额(模拟)
	 * 
	 * @param simulateExitImgMoney 退运料件金额
	 */
	public void setSimulateExitImgMoney(Double simulateExitImgMoney) {
		this.simulateExitImgMoney = simulateExitImgMoney;
	}

	/**
	 * 
	 * 获得退运料件金额
	 */
	public Double getRemainExportMoney() {
		return remainExportMoney;
	}
	/**
	 * 设置退运料件金额
	 * 
	 * @param remainExportMoney 退运料件金额
	 */
	public void setRemainExportMoney(Double remainExportMoney) {
		this.remainExportMoney = remainExportMoney;
	}
	/**
	 * 获得余料结转出口(模拟)
	 */
	public Double getSimulateRemainExportMoney() {
		return simulateRemainExportMoney;
	}
	/**
	 * 设置余料结转出口(模拟)
	 * 
	 * @param simulateRemainExportMoney 
	 */
	public void setSimulateRemainExportMoney(Double simulateRemainExportMoney) {
		this.simulateRemainExportMoney = simulateRemainExportMoney;
	}
	/**
	 * 获取期末结余料件总金额(模拟)
	 * 
	 */
	public Double getSimulateEndBalanceImgMoney() {
		return simulateEndBalanceImgMoney;
	}
	/**
	 * 设置期末结余料件总金额(模拟)
	 * 
	 * @return simulateEndBalanceImgMoney 期末结余料件总金额
	 */
	public void setSimulateEndBalanceImgMoney(Double simulateEndBalanceImgMoney) {
		this.simulateEndBalanceImgMoney = simulateEndBalanceImgMoney;
	}
	
	
}
