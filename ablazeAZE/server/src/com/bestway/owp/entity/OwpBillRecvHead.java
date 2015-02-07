package com.bestway.owp.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.authority.entity.AclUser;
/**
 * 外发加工 - 收发货登记 - 收货单表头
 * @author 石小凯 2010.08.10
 */
public class OwpBillRecvHead extends BaseScmEntity {
	private static final long serialVersionUID = -7168482525375063455L;
	/**
	 * 流水号
	 */
	private Integer seqNum = null;
	/**
	 * 收货单编号
	 */
	private String billNo ;
	
	/**
	 * 电子口岸统一编号
	 */
	private String seqNo ;
	
	/**
	 * 申请表编号
	 */
	private String appNo ;
	
	/**
	 * 企业内部编号
	 */
	private String copBillNo ;
	
	/**
	 * 帐册号
	 */
	private String emsNo ;
	
	/**
	 * 委托方企业编码
	 */
	private String outTradeCode ;
	
	/**
	 * 委托方企业名称
	 */
	private String outTradeName ;
	
	/**
	 * 承揽方企业编码
	 */
	private String inTradeCode ;
	
	/**
	 * 承揽方企业名称
	 */
	private String inTradeName ;
	
	/**
	 * 收货申报人
	 */
	private String isDeclaEm;
	
	/**
	 * 收货日期
	 */
	private Date collectDate ;
	
	/**
	 * 备注
	 */
	private String note ;
	
	/**
	 * 录入员
	 */
	private String creater;
	
	/**
	 * 录入时间
	 */
	private Date createrDate;
	/**
	 * 撤销标记  0:撤销 、 1:已撤销
	 */
	private Integer cancelMark  ;
	
	/**
	 * 撤销原因
	 */
	private String cancelReason ;

	/**
	 * 获取收货单编号
	 * @return
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 审批状态
	 * APPLY_POR = "1";	申请备案
	 * WAIT_EAA = "2";	等待审批
	 * PROCESS_EXE = "3";	正在执行
	 * CHANGING_EXE = "4";	正在变更
	 * CHANGING_CANCEL = "5";	已经核销
	 */
	private String declareState;  
	
	/**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;
	
	/**
	 * 是否从申请表导入. 是：申请表。否：单据中心。
	 */
	private Boolean isInsertByApp;
	
	
	public Boolean getIsInsertByApp() {
		return isInsertByApp;
	}

	public void setIsInsertByApp(Boolean isInsertByApp) {
		this.isInsertByApp = isInsertByApp;
	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * 设置收货单编号
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 获取电子口岸统一编号
	 * @return
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * 设置电子口岸统一编号
	 * @param seqNo
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 获取申请表编号
	 * @return
	 */
	public String getAppNo() {
		return appNo;
	}

	/**
	 * 设置申请表编号
	 * @param appNo
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * 获取企业内部编号
	 * @return
	 */
	public String getCopBillNo() {
		return copBillNo;
	}

	/**
	 * 设置企业内部编号
	 * @param copBillNo
	 */
	public void setCopBillNo(String copBillNo) {
		this.copBillNo = copBillNo;
	}

	/**
	 * 获取帐册号
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置帐册号
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取委托方企业编码
	 * @return
	 */
	public String getOutTradeCode() {
		return outTradeCode;
	}

	/**
	 * 设置委托方企业编码
	 * @param outTradeCode
	 */
	public void setOutTradeCode(String outTradeCode) {
		this.outTradeCode = outTradeCode;
	}

	/**
	 * 获取委托方企业名称
	 * @return
	 */
	public String getOutTradeName() {
		return outTradeName;
	}

	/**
	 * 设置委托方企业名称
	 * @param outTradeName
	 */
	public void setOutTradeName(String outTradeName) {
		this.outTradeName = outTradeName;
	}

	/**
	 * 获取承揽方企业编码
	 * @return
	 */
	public String getInTradeCode() {
		return inTradeCode;
	}

	/**
	 * 设置承揽方企业编码
	 * @param inTradeCode
	 */
	public void setInTradeCode(String inTradeCode) {
		this.inTradeCode = inTradeCode;
	}

	/**
	 * 获取承揽方企业名称
	 * @return
	 */
	public String getInTradeName() {
		return inTradeName;
	}

	/**
	 * 设置承揽方企业名称
	 * @param inTradeName
	 */
	public void setInTradeName(String inTradeName) {
		this.inTradeName = inTradeName;
	}

	/**
	 * 获取收货申报人
	 * @return
	 */
	public String getIsDeclaEm() {
		return isDeclaEm;
	}

	/**
	 * 设置收货申报人
	 * @param isDeclaEm
	 */
	public void setIsDeclaEm(String isDeclaEm) {
		this.isDeclaEm = isDeclaEm;
	}

	/**
	 * 获取收货日期
	 * @return
	 */
	public Date getCollectDate() {
		return collectDate;
	}

	/**
	 * 设置收货日期
	 * @param collectDate
	 */
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	/**
	 * 获取备注
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取录入员
	 * @return
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * 设置录入员
	 * @param creater
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * 获取录入时间
	 * @return
	 */
	public Date getCreaterDate() {
		return createrDate;
	}

	/**
	 * 设置录入时间
	 * @param createrDate
	 */
	public void setCreaterDate(Date createrDate) {
		this.createrDate = createrDate;
	}

	/**
	 * 获取撤销标记 0:撤销 、 1:已撤销
	 * @return
	 */
	public Integer getCancelMark() {
		return cancelMark;
	}

	/**
	 * 设置撤销标记 0:撤销 、 1:已撤销
	 * @param cancelMark
	 */
	public void setCancelMark(Integer cancelMark) {
		this.cancelMark = cancelMark;
	}

	/**
	 * 获取撤销原因
	 * @return
	 */
	public String getCancelReason() {
		return cancelReason;
	}

	/**
	 * 设置撤销原因
	 * @param cancelReason
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	
	
}
