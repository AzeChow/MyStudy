/*
 * Created on 2005-4-15
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractcav.entity;

import java.io.UnsupportedEncodingException;
import java.util.Date;

//import net.sf.hibernate.cfg.DefaultNamingStrategy;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;

/**
 * 合同核销表头
 */
public class ContractCav extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 合同号
	 */
	private String contractNo = null;

	/**
	 * 帐册编号
	 */
	private String emsNo;

	/**
	 * 开始有效期
	 */
	private Date beginDate;

	/**
	 * 结束有效期
	 */
	private Date endDate;

	/**
	 * 批准证号
	 */
	private String emsApprNo;

	/**
	 * 进口总金额
	 */
	private Double impAmount = null;

	/**
	 * 出口总金额
	 */
	private Double expAmount = null;

	/**
	 * 加工费总价
	 */
	private Double processTotalPrice;

	/**
	 * 进口报关单总数
	 */
	private Integer impCDCount = null;

	/**
	 * 出口报关单总数
	 */
	private Integer expCDCount = null;
	/**
	 * 核销料件总件数
	 */
	private Integer imgCount;
	/**
	 * 核销成品总件数
	 */
	private Integer exgCount;
	/**
	 * 内销金额
	 */
	private Double internalSale = null;

	/**
	 * 进口设备总值
	 */
	private Double impDeviceGross = null;

	/**
	 * 内销补税金额
	 */
	private Double internalSaleTax = null;

	/**
	 * 内销批准证号
	 */
	private String internalSaleWarrant = null;

	/**
	 * 内销补税单号
	 */
	private String internalSaleTaxBill = null;

	/**
	 * 余料金额
	 */
	private Double remainMoney = null;

	/**
	 * 转入余料手册编号
	 */
	private String remainEmsNo = null;

	/**
	 * 保证金额
	 */
	private Double cautionMoney = null;

	/**
	 * 保证金额
	 */
	private Boolean isSusceptivity = false;

	/**
	 * 是否限制商品
	 */
	private Boolean isLimit = false;

	/**
	 * 总页数
	 */
	private Integer totalPages = null;

	/**
	 * 币制
	 */
	private Curr curr = null;

	/**
	 * 企业内部编号
	 */
	private String copEmsNo = null;
	/**
	 * 申报类型 EMS_POR_WJ = "1"; 经营范围/合同备案/备案资料库备案 EMS_POR_BILL = "2";
	 * 电子帐册备案商品/通关备案 CANCEL_AFTER_VERIFICA = "4"; 数据报核cancel after
	 */
	private String declareType = null;
	/**
	 * 申报状态 APPLY_POR = "1"; 申请备案 WAIT_EAA = "2"; 等待审批 PROCESS_EXE = "3"; 正在执行
	 * CHANGING_EXE = "4"; 正在变更 CHANGING_CANCEL = "5"; 已经核销
	 */
	private String declareState = null;
	/**
	 * 申报日期
	 */
	private Date declareDate = null;
	/**
	 * 申报次数
	 */
	private Integer declareTimes = null;

	/**
	 * 录入员代号
	 */
	private String inputER = null;
	/**
	 * 是海关
	 */
	private Boolean isCustoms = false;

	/**
	 * 增值率
	 */
	private Double valueAddedRate = null;
	
	/**
	 * 获取增值率
	 * 
	 * @return
	 */
	public Double getValueAddedRate() {
		return valueAddedRate;
	}

	/**
	 * 设置增值率
	 * 
	 * @param valueAddedRate
	 */
	public void setValueAddedRate(Double valueAddedRate) {
		this.valueAddedRate = valueAddedRate;
	}

	/**
	 * 获取海关、自用判断，true代表是海关用
	 * 
	 * @return isCustoms 海关、自用判断，true代表是海关用
	 */
	public Boolean getIsCustoms() {
		return isCustoms;
	}

	/**
	 * 设置海关、自用判断，true代表是海关用
	 * 
	 * @param isCustoms
	 *            海关、自用判断，true代表是海关用
	 */
	public void setIsCustoms(Boolean isCustoms) {
		this.isCustoms = isCustoms;
	}

	// DefaultNamingStrategy asdf;
	// private org.springframework.orm.hibernate.LocalSessionFactoryBean
	// sessionFactory;
	/**
	 * 获取serialVersionUID
	 * 
	 * @return serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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
	 * @param beginDate
	 *            开始有效期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取保证金额
	 * 
	 * @return cautionMoney 保证金额
	 */
	public Double getCautionMoney() {
		return cautionMoney;
	}

	/**
	 * 设置保证金额
	 * 
	 * @param cautionMoney
	 *            保证金额
	 */
	public void setCautionMoney(Double cautionMoney) {
		this.cautionMoney = cautionMoney;
	}

	/**
	 * 获取币制
	 * 
	 * @return curr 币制
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币制
	 * 
	 * @param curr
	 *            币制
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
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
	 * @param emsApprNo
	 *            批准证号
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
	 * @param emsNo
	 *            帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取结束日期
	 * 
	 * @return endDate 结束日期
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束日期
	 * 
	 * @param endDate
	 *            结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取出口总金额
	 * 
	 * @return exgAmount 出口总金额
	 */
	public Double getExpAmount() {
		return expAmount;
	}

	/**
	 * 设置出口总金额
	 * 
	 * @param exgAmount
	 *            出口总金额
	 */
	public void setExpAmount(Double exgAmount) {
		this.expAmount = exgAmount;
	}

	/**
	 * 获取出口报关单总数
	 * 
	 * @return expCDCount 出口报关单总数
	 */
	public Integer getExpCDCount() {
		return expCDCount;
	}

	/**
	 * 设置出口报关单总数
	 * 
	 * @param expCDCount
	 *            出口报关单总数
	 */
	public void setExpCDCount(Integer expCDCount) {
		this.expCDCount = expCDCount;
	}

	/**
	 * 获取进口总金额
	 * 
	 * @return imgAmount 进口总金额
	 */
	public Double getImpAmount() {
		return impAmount;
	}

	/**
	 * 设置进口总金额
	 * 
	 * @param imgAmount
	 *            进口总金额
	 */
	public void setImpAmount(Double imgAmount) {
		this.impAmount = imgAmount;
	}

	/**
	 * 获取进口报关单总数
	 * 
	 * @return impCDCount 进口报关单总数
	 */
	public Integer getImpCDCount() {
		return impCDCount;
	}

	/**
	 * 设置进口报关单总数
	 * 
	 * @param impCDCount
	 *            进口报关单总数
	 */
	public void setImpCDCount(Integer impCDCount) {
		this.impCDCount = impCDCount;
	}

	/**
	 * 获取内销金额
	 * 
	 * @return internalSale 内销金额
	 */
	public Double getInternalSale() {
		return internalSale;
	}

	/**
	 * 设置内销金额
	 * 
	 * @param internalSale
	 *            内销金额
	 */
	public void setInternalSale(Double internalSale) {
		this.internalSale = internalSale;
	}

	/**
	 * 获取内销补税金额
	 * 
	 * @return internalSaleTax 内销补税金额
	 */
	public Double getInternalSaleTax() {
		return internalSaleTax;
	}

	/**
	 * 设置内销补税金额
	 * 
	 * @param internalSaleTax
	 *            内销补税金额
	 */
	public void setInternalSaleTax(Double internalSaleTax) {
		this.internalSaleTax = internalSaleTax;
	}

	/**
	 * 获取内销补税单号
	 * 
	 * @return internalSaleTaxBill 内销补税单号
	 */
	public String getInternalSaleTaxBill() {
		return internalSaleTaxBill;
	}

	/**
	 * 设置内销补税单号
	 * 
	 * @param internalSaleTaxBill
	 *            内销补税单号
	 */
	public void setInternalSaleTaxBill(String internalSaleTaxBill) {
		this.internalSaleTaxBill = internalSaleTaxBill;
	}

	/**
	 * 获取内销批准证号
	 * 
	 * @return internalSaleWarrant 内销批准证号
	 */
	public String getInternalSaleWarrant() {
		return internalSaleWarrant;
	}

	/**
	 * 设置内销批准证号
	 * 
	 * @param internalSaleWarrant
	 *            内销批准证号
	 */
	public void setInternalSaleWarrant(String internalSaleWarrant) {
		this.internalSaleWarrant = internalSaleWarrant;
	}

	/**
	 * 获取限制商品判断
	 * 
	 * @return isLimit 是否限制商品，true为限制商品
	 */
	public Boolean getIsLimit() {
		return isLimit;
	}

	/**
	 * 设置是否限制商品
	 * 
	 * @param isLimit
	 *            是否限制商品，true为限制商品
	 */
	public void setIsLimit(Boolean isLimit) {
		this.isLimit = isLimit;
	}

	/**
	 * 获取敏感商品判断
	 * 
	 * @return isSusceptivity 是否敏感商品，true为敏感商品
	 */
	public Boolean getIsSusceptivity() {
		return isSusceptivity;
	}

	/**
	 * 设置是否敏感商品
	 * 
	 * @param isSusceptivity
	 *            是否敏感商品，true为敏感商品
	 */
	public void setIsSusceptivity(Boolean isSusceptivity) {
		this.isSusceptivity = isSusceptivity;
	}

	/**
	 * 获取加工费总价
	 * 
	 * @return processTotalPrice 加工费总价
	 */
	public Double getProcessTotalPrice() {
		return processTotalPrice;
	}

	/**
	 * 设置加工费总价
	 * 
	 * @param processTotalPrice
	 *            加工费总价
	 */
	public void setProcessTotalPrice(Double processTotalPrice) {
		this.processTotalPrice = processTotalPrice;
	}

	/**
	 * 获取转入余料手册编号
	 * 
	 * @return remainEmsNo 转入余料手册编号
	 */
	public String getRemainEmsNo() {
		return remainEmsNo;
	}

	/**
	 * 设置转入余料手册编号
	 * 
	 * @param remainEmsNo
	 *            转入余料手册编号
	 */
	public void setRemainEmsNo(String remainEmsNo) {
		if (remainEmsNo != null) {
			try {
				if (remainEmsNo.getBytes("UTF-8").length >= 255) {
					String newRemainEmsNo = "";
					String slice = null;
					String temp=null;
					for (int i = 0; i < remainEmsNo.length(); i++) {
							temp = new String(newRemainEmsNo);
							slice = remainEmsNo.substring(i, i + 1);
							newRemainEmsNo += slice;
							if (newRemainEmsNo.getBytes("UTF-8").length >= 255){
								newRemainEmsNo = temp;
								break;
							}
					}
					this.remainEmsNo = newRemainEmsNo;
				} else {
					this.remainEmsNo = remainEmsNo;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			this.remainEmsNo = remainEmsNo;
		}
	}

	/**
	 * 获取余料金额
	 * 
	 * @return remainMoney 余料金额
	 */
	public Double getRemainMoney() {
		return remainMoney;
	}

	/**
	 * 设置余料金额
	 * 
	 * @param remainMoney
	 *            余料金额
	 */
	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	/**
	 * 获取总页数
	 * 
	 * @return totalPages 总页数
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * 设置总页数
	 * 
	 * @param totalPages
	 *            总页数
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * 获取合同号
	 * 
	 * @return contractNo 合同号
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * 设置合同号
	 * 
	 * @param contractNo
	 *            合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public Integer getDeclareTimes() {
		return declareTimes;
	}

	public void setDeclareTimes(Integer declareTimes) {
		this.declareTimes = declareTimes;
	}

	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public Integer getExgCount() {
		return exgCount;
	}

	public void setExgCount(Integer exgCount) {
		this.exgCount = exgCount;
	}

	public Integer getImgCount() {
		return imgCount;
	}

	public void setImgCount(Integer imgCount) {
		this.imgCount = imgCount;
	}

	public String getInputER() {
		return inputER;
	}

	public void setInputER(String inputER) {
		this.inputER = inputER;
	}

	/**
	 * 获取进口设备总值
	 * 
	 * @return
	 */
	public Double getImpDeviceGross() {
		return impDeviceGross;
	}

	/**
	 * 设置进口设备总值
	 * 
	 * @param impDiviceGross
	 */
	public void setImpDeviceGross(Double impDeviceGross) {
		this.impDeviceGross = impDeviceGross;
	}
}
