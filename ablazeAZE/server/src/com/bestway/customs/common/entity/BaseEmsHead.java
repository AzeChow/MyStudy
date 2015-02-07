package com.bestway.customs.common.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.common.BaseScmEntity;

/**
 * 基础类
 * 
 * @author refdom
 * 
 */
public class BaseEmsHead extends BaseScmEntity {
	/**
	 * 预申报帐册编号
	 */
	private String preEmsNo="";

	/**
	 * 帐册状态 APPLY_POR = "1"; 申请备案 WAIT_EAA = "2"; 等待审批 PROCESS_EXE = "3"; 正在执行
	 * CHANGING_EXE = "4"; 正在变更 CHANGING_CANCEL = "5"; 已经核销
	 */
	private String declareState="";

	/**
	 * 申报类型
	 */
	private String declareType="";

	/**
	 * 帐册编号
	 */
	private String emsNo="";

	/**
	 * 批文帐册号
	 */
	private String sancEmsNo="";

	/**
	 * 经营单位代码
	 */
	private String tradeCode="";

	/**
	 * 经营单位名称
	 */
	private String tradeName="";

	/**
	 * 收货单位代码
	 */
	private String machCode="";

	/**
	 * 收货单位名称
	 */
	private String machName="";

	/**
	 * 开始有效期
	 */
	private Date beginDate=null;

	/**
	 * 结束有效期
	 */
	private Date endDate=null;

	/**
	 * 外商公司
	 */
	private String outTradeCo="";

	/**
	 * 征免方式
	 */
	private LevyMode levyMode=null;

	/**
	 * 征面性质
	 */
	private LevyKind levyKind=null;

	/**
	 * 保税方式
	 */
	private PayMode payMode=null;

	/**
	 * 加工种类
	 */
	private MachiningType machiningType=null;

	/**
	 * 备案批准日期
	 */
	private Date newApprDate=null;

	/**
	 * 变更批准日期
	 */
	private Date changeApprDate=null;

	/**
	 * 批准文号
	 */
	private String emsApprNo="";

	/**
	 * 审批标志
	 */
	private String checkMark="";

	/**
	 * 执行标志
	 */
	private String exeMark="";

	/**
	 * 取得开始有效期
	 * 
	 * @return 开始有效期
	 */
	public Date getBeginDate() {
		// if (beginDate == null) {
		// return null;
		// }
		// SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// return java.sql.Date.valueOf(bartDateFormat.format(beginDate));
		return beginDate;
	}

	/**
	 * 设置开始有效期
	 * 
	 * @param beginDate
	 *            开始有效期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 取得变更批准日期
	 * 
	 * @return 变更批准日期
	 */
	public Date getChangeApprDate() {
		return changeApprDate;
	}

	/**
	 * 设置变更批准日期
	 * 
	 * @param changeApprDate
	 *            变更批准日期
	 */
	public void setChangeApprDate(Date changeApprDate) {
		this.changeApprDate = changeApprDate;
	}

	/**
	 * 取得审批标志
	 * 
	 * @return 审批标志
	 */
	public String getCheckMark() {
		return checkMark;
	}

	/**
	 * 设置审批标志
	 * 
	 * @param checkMark
	 *            审批标志
	 */
	public void setCheckMark(String checkMark) {
		this.checkMark = checkMark;
	}

	/**
	 * 取得帐册状态
	 * 
	 * @return 帐册状态
	 */
	public String getDeclareState() {
		return declareState;
	}

	/**
	 * 设置帐册状态
	 * 
	 * @param declareState
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	/**
	 * 取得申报类型 1,申请备案 2,申请变更
	 * 
	 * @return 申报类型 1,申请备案 2,申请变更
	 */
	public String getDeclareType() {
		return declareType;
	}

	/**
	 * 设置申报类型 1,申请备案 2,申请变更
	 * 
	 * @param declareType
	 *            申报类型 1,申请备案 2,申请变更
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	/**
	 * 取得批准证号
	 * 
	 * @return 批准证号
	 */
	public String getEmsApprNo() {
		return emsApprNo;
	}

	/**
	 * 设置批准证号
	 * 
	 * @param emsApprNo
	 *            批准证号
	 */
	public void setEmsApprNo(String emsApprNo) {
		this.emsApprNo = emsApprNo;
	}

	/**
	 * 取得帐册编号
	 * 
	 * @return 帐册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置帐册编号
	 * 
	 * @param emsNo
	 *            帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 取得结束有效期
	 * 
	 * @return 结束有效期
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束有效期
	 * 
	 * @param endDate
	 *            结束有效期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 取得执行标志
	 * 
	 * @return 执行标志
	 */
	public String getExeMark() {
		return exeMark;
	}

	/**
	 * 设置执行标志
	 * 
	 * @param exeMark
	 *            执行标志
	 */
	public void setExeMark(String exeMark) {
		this.exeMark = exeMark;
	}

	/**
	 * 取得收货单位代码
	 * 
	 * @return 收货单位代码
	 */
	public String getMachCode() {
		return machCode;
	}

	/**
	 * 设置收货单位代码
	 * 
	 * @param machCode
	 *            收货单位代码
	 */
	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}

	/**
	 * 取得加工种类
	 * 
	 * @return 加工种类
	 */
	public MachiningType getMachiningType() {
		return machiningType;
	}

	/**
	 * 设置加工种类
	 * 
	 * @param machiningType
	 *            加工种类
	 */
	public void setMachiningType(MachiningType machiningType) {
		this.machiningType = machiningType;
	}

	/**
	 * 取得收货单位名称
	 * 
	 * @return 收货单位名称
	 */
	public String getMachName() {
		return machName;
	}

	/**
	 * 设置收货单位名称
	 * 
	 * @param machName
	 *            收货单位名称
	 */
	public void setMachName(String machName) {
		this.machName = machName;
	}

	/**
	 * 取得备案批准日期
	 * 
	 * @return 备案批准日期
	 */
	public Date getNewApprDate() {
		return newApprDate;
	}

	/**
	 * 设置备案批准日期
	 * 
	 * @param newApprDate
	 *            备案批准日期
	 */
	public void setNewApprDate(Date newApprDate) {
		this.newApprDate = newApprDate;
	}

	/**
	 * 取得外商公司
	 * 
	 * @return 外商公司
	 */
	public String getOutTradeCo() {
		return outTradeCo;
	}

	/**
	 * 设置外商公司
	 * 
	 * @param outTradeCo
	 *            外商公司
	 */
	public void setOutTradeCo(String outTradeCo) {
		this.outTradeCo = outTradeCo;
	}

	/**
	 * 取得保税方式
	 * 
	 * @return 保税方式
	 */
	public PayMode getPayMode() {
		return payMode;
	}

	/**
	 * 设置保税方式
	 * 
	 * @param payMode
	 *            保税方式
	 */
	public void setPayMode(PayMode payMode) {
		this.payMode = payMode;
	}

	/**
	 * 取得预申报帐册编号
	 * 
	 * @return 预申报帐册编号
	 */
	public String getPreEmsNo() {
		return preEmsNo;
	}

	/**
	 * 设置预申报帐册编号
	 * 
	 * @param preEmsNo
	 *            预申报帐册编号
	 */
	public void setPreEmsNo(String preEmsNo) {
		this.preEmsNo = preEmsNo;
	}

	/**
	 * 取得批文帐册号
	 * 
	 * @return 批文帐册号
	 */
	public String getSancEmsNo() {
		return sancEmsNo;
	}

	/**
	 * 设置批文帐册号
	 * 
	 * @param sancEmsNo
	 *            批文帐册号
	 */
	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}

	/**
	 * 取得经营单位代码
	 * 
	 * @return 经营单位代码
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * 设置经营单位代码
	 * 
	 * @param tradeCode
	 *            经营单位代码
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * 取得经营单位名称
	 * 
	 * @return 经营单位名称
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * 设置经营单位名称
	 * 
	 * @param tradeName
	 *            经营单位名称
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * 取得征免方式
	 * 
	 * @return 征免方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * 设置征免方式
	 * 
	 * @param levyMode
	 *            征免方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	/**
	 * 取得征面性质
	 * 
	 * @return 征面性质
	 */
	public LevyKind getLevyKind() {
		return levyKind;
	}

	/**
	 * 设置征面性质
	 * 
	 * @param levyKind
	 *            征面性质
	 */
	public void setLevyKind(LevyKind levyKind) {
		this.levyKind = levyKind;
	}
}
