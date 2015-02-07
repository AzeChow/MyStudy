package com.bestway.bcs.dictpor.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 备案资料库表头
 * 
 */
public class BcsDictPorHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 流水号
	 */
	private Integer seqNum = null;

	/**
	 * 报送海关
	 */
	private Customs declareCustoms = null;

	/**
	 * 主管外经部门
	 */
	private RedDep redDep = null;

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
	 * 备案资料库编号
	 */
	private String dictPorEmsNo;

	/**
	 * 企业内部编号
	 */
	private String copEmsNo;

	/**
	 * 经营单位编号
	 */
	private String tradeCode;

	/**
	 * 经营单位名称
	 */
	private String tradeName;

	/**
	 * 加工单位编号
	 */
	private String machCode;

	/**
	 * 加工单位名称
	 */
	private String machName;

	/**
	 * 年加工生产能力
	 */
	private Double productRatio;

	/**
	 * 申报日期
	 */
	private Date declareDate;

	/**
	 * 征免性质
	 */
	private LevyKind levyKind;

	/**
	 * 收货地区
	 */
	private District receiveArea = null;

	/**
	 * 加工种类
	 */
	private MachiningType machiningType;

	/**
	 * 贸易方式
	 */
	private Trade trade = null;

	/**
	 * 币制
	 */
	private Curr curr = null;

	/**
	 * 管理对象
	 */
	private String manageObject;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 录入员代号
	 */
	private String inputER = null;

	/**
	 * 开始有效期
	 */
	private Date beginDate;

	/**
	 * 结束有效期
	 */
	private Date endDate;

    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;

	/**
	 * 限制类标志
	 * ADJUST_BEFORE_EMS = "1";	调整前旧手册
	 * ADJUST_AFTER_EMS = "2";	调整后新手册
	 * ACOUNT_BOOK_USE = "3";	台账专用手册
	 */
	private String limitFlag;

	/**
	 * 台帐银行 （TempInvestMode.PAPER-纸质台帐  TempInvestMode.BANKOFCHINA-中国银行 ICBC-工商银行）
	 */
	private String bankModel=null;
	
	/**
	 * 进口料件范围1
	 */
	private String imgRangeSpec1;

	/**
	 * 进口料件范围2
	 */
	private String imgRangeSpec2;

	/**
	 * 进口料件范围3
	 */
	private String imgRangeSpec3;

	/**
	 * 进口料件范围4
	 */
	private String imgRangeSpec4;

	/**
	 * 进口料件范围5
	 */
	private String imgRangeSpec5;

	/**
	 * 出口成品范围1
	 */
	private String exgRangeSpec1;

	/**
	 * 出口成品范围2
	 */
	private String exgRangeSpec2;

	/**
	 * 出口成品范围3
	 */
	private String exgRangeSpec3;

	/**
	 * 出口成品范围4
	 */
	private String exgRangeSpec4;

	/**
	 * 出口成品范围5
	 */
	private String exgRangeSpec5;

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public String getDictPorEmsNo() {
		return dictPorEmsNo;
	}

	public void setDictPorEmsNo(String credenceEmsNo) {
		this.dictPorEmsNo = credenceEmsNo;
	}

	public Customs getDeclareCustoms() {
		return declareCustoms;
	}

	public void setDeclareCustoms(Customs declareCustoms) {
		this.declareCustoms = declareCustoms;
	}

	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public Double getProductRatio() {
		return productRatio;
	}

	public void setProductRatio(Double productRatio) {
		this.productRatio = productRatio;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getMachCode() {
		return machCode;
	}

	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}

	public String getMachName() {
		return machName;
	}

	public void setMachName(String machName) {
		this.machName = machName;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getManageObject() {
		return manageObject;
	}

	public void setManageObject(String manageObject) {
		this.manageObject = manageObject;
	}

	public String getDeclareCode() {
		if ("0".equals(manageObject)) {
			return this.getTradeCode();
		} else {
			return this.getMachCode();
		}
	}

	public String getDeclareName() {
		if ("0".equals(manageObject)) {
			return this.getTradeName();
		} else {
			return this.getMachName();
		}
	}

	public String getEmsType() {
		return "Z";
	}

	public RedDep getRedDep() {
		return redDep;
	}

	public void setRedDep(RedDep redDep) {
		this.redDep = redDep;
	}

	public String getExgRangeSpec1() {
		return exgRangeSpec1;
	}

	public void setExgRangeSpec1(String exgRangeSpec1) {
		this.exgRangeSpec1 = exgRangeSpec1;
	}

	public String getExgRangeSpec2() {
		return exgRangeSpec2;
	}

	public void setExgRangeSpec2(String exgRangeSpec2) {
		this.exgRangeSpec2 = exgRangeSpec2;
	}

	public String getExgRangeSpec3() {
		return exgRangeSpec3;
	}

	public void setExgRangeSpec3(String exgRangeSpec3) {
		this.exgRangeSpec3 = exgRangeSpec3;
	}

	public String getExgRangeSpec4() {
		return exgRangeSpec4;
	}

	public void setExgRangeSpec4(String exgRangeSpec4) {
		this.exgRangeSpec4 = exgRangeSpec4;
	}

	public String getImgRangeSpec1() {
		return imgRangeSpec1;
	}

	public void setImgRangeSpec1(String imgRangeSpec1) {
		this.imgRangeSpec1 = imgRangeSpec1;
	}

	public String getImgRangeSpec2() {
		return imgRangeSpec2;
	}

	public void setImgRangeSpec2(String imgRangeSpec2) {
		this.imgRangeSpec2 = imgRangeSpec2;
	}

	public String getImgRangeSpec3() {
		return imgRangeSpec3;
	}

	public void setImgRangeSpec3(String imgRangeSpec3) {
		this.imgRangeSpec3 = imgRangeSpec3;
	}

	public String getImgRangeSpec4() {
		return imgRangeSpec4;
	}

	public void setImgRangeSpec4(String imgRangeSpec4) {
		this.imgRangeSpec4 = imgRangeSpec4;
	}

	public String getImgRangeSpec5() {
		return imgRangeSpec5;
	}

	public void setImgRangeSpec5(String imgRangeSpec5) {
		this.imgRangeSpec5 = imgRangeSpec5;
	}

	public String getExgRangeSpec5() {
		return exgRangeSpec5;
	}

	public void setExgRangeSpec5(String exgRangeSpec5) {
		this.exgRangeSpec5 = exgRangeSpec5;
	}

	public void setImgRangeSpec(String imgRangeSpec) {
		this.setMutipFieldValue(imgRangeSpec, "imgRangeSpec1", "imgRangeSpec2",
				"imgRangeSpec3", "imgRangeSpec4", "imgRangeSpec5");
	}

	public void setExgRangeSpec(String exgRangeSpec) {
		this.setMutipFieldValue(exgRangeSpec, "exgRangeSpec1", "exgRangeSpec2",
				"exgRangeSpec3", "exgRangeSpec4", "exgRangeSpec5");
	}

	public String getImgRangeSpec() {
		return (imgRangeSpec1 == null ? "" : imgRangeSpec1)
				+ (imgRangeSpec2 == null ? "" : imgRangeSpec2)
				+ (imgRangeSpec3 == null ? "" : imgRangeSpec3)
				+ (imgRangeSpec4 == null ? "" : imgRangeSpec4)
				+ (imgRangeSpec5 == null ? "" : imgRangeSpec5);
	}

	public String getExgRangeSpec() {
		return (exgRangeSpec1 == null ? "" : exgRangeSpec1)
				+ (exgRangeSpec2 == null ? "" : exgRangeSpec2)
				+ (exgRangeSpec3 == null ? "" : exgRangeSpec3)
				+ (exgRangeSpec4 == null ? "" : exgRangeSpec4)
				+ (exgRangeSpec5 == null ? "" : exgRangeSpec5);
	}

	public String getInputER() {
		return inputER;
	}

	public void setInputER(String inputER) {
		this.inputER = inputER;
	}

	public LevyKind getLevyKind() {
		return levyKind;
	}

	public void setLevyKind(LevyKind levyKind) {
		this.levyKind = levyKind;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public District getReceiveArea() {
		return receiveArea;
	}

	public void setReceiveArea(District receiveArea) {
		this.receiveArea = receiveArea;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public MachiningType getMachiningType() {
		return machiningType;
	}

	public void setMachiningType(MachiningType machiningType) {
		this.machiningType = machiningType;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLimitFlag() {
		return limitFlag;
	}

	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
	}

	public String getBankModel() {
		return bankModel;
	}

	public void setBankModel(String bankModel) {
		this.bankModel = bankModel;
	}
	
	
}
