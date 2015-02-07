package com.bestway.bcus.manualdeclare.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.entity.ExItemCode;
import com.bestway.bcus.innermerge.entity.InnerMergeData;

/**
 * 存放临时电子帐册中的财务统计报表
 * 
 * @author Administrator
 * 
 */
public class TempFinancialReport implements Serializable {
    
	/**
	 * 所属期
	 */
	private String suoShuQi;
	/**
	 * 序号
	 */
	private String xuHao;
	/**
	 * 所属期标识
	 */
	private String suoShuQiBiaoShi;
	/**
	 * 进料登记册号
	 */
	private String jlDjch;
	/**
	 * 进口报关单号
	 */
	private String jkbgdh;
	/**
	 * 运输方式
	 */
	private String transferMode;
	/**
	 * 运输工具名称
	 */
	private String conveyance;
	/**
	 * 提运单号
	 */
	private String billOfLading;
	/**
	 * 记销售日期
	 */
	private String jxsrq;

	/**
	 * 代理证明号
	 */
	private String dlzmh;
	/**
	 * 出口日期
	 */
	private String ckrq;

	/**
	 * 外汇核销单号
	 */
	private String whxhdh;

	/**
	 * 进/出口数量
	 */
	private String jcksl;
	/**
	 * 原币代码
	 */
	private String ybdm;
	/**
	 * 原币到岸价/原币离岸价
	 */
	private String ybdaj;
	/**
	 * 进/出口商品代码
	 */
	private String jckspdm;
	/**
	 * 出口发票号
	 */
	private String ckfph;
	/**
	 * 计量单位
	 */
	private String jldw;
	/**
	 * 出口商品名称
	 */
	private String ckspmc;
	/**
	 * 报关单商品流水号
	 */
	private String serialNumber;
	
	/**
	 * 进口商品名称
	 */
	private String commName;
	
	/**
	 * 原币币别
	 */
	private String currency;

	/**
	 * 技嘉用出口商品编码库
	 */
	private ExItemCode exItemCode;
	

	private String currName;

    private Double cnrateSum;
    
    private Double usrateSum;
    
    private Double balance;
    
	private String currCode;
	
	private Double currCN;
	public Double getCurrCN() {
		return currCN;
	}

	public void setCurrCN(Double currCN) {
		this.currCN = currCN;
	}

	public Double getCurrUS() {
		return currUS;
	}

	public void setCurrUS(Double currUS) {
		this.currUS = currUS;
	}

	private Double currUS;
	
	/**
	 * 技嘉用出口商品编码库
	 */
	public Double getCnrateSum() {
		return cnrateSum;
	}
	
	public void setCnrateSum(Double cnrateSum) {
		this.cnrateSum = cnrateSum;
	}
	
	public Double getUsrateSum() {
		return usrateSum;
	}
	
	public void setUsrateSum(Double usrateSum) {
		this.usrateSum = usrateSum;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public ExItemCode getExItemCode() {
		return exItemCode;
	}

	/**
	 * 技嘉用出口商品编码库
	 */
	public void setExItemCode(ExItemCode exItemCode) {
		this.exItemCode = exItemCode;
	}
	
	public String getCurrName() {
		return currName;
	}

	public void setCurrName(String currName) {
		this.currName = currName;
	}
	/**
	 * 取得报关单商品流水号
	 * 
	 * @return 报关单商品流水号
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 设置报关单商品流水号
	 * 
	 * @param serialNumber
	 *            报关单商品流水号
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 所属期
	 */
	public String getSuoShuQi() {
		return suoShuQi;
	}

	/**
	 * 所属期
	 */
	public void setSuoShuQi(String suoShuQi) {
		this.suoShuQi = suoShuQi;
	}

	/**
	 * 序号
	 */
	public String getXuHao() {
		return xuHao;
	}

	/**
	 * 序号
	 */
	public void setXuHao(String xuHao) {
		this.xuHao = xuHao;
	}

	/**
	 * 所属期标识
	 * 
	 * @return
	 */
	public String getSuoShuQiBiaoShi() {
		return suoShuQiBiaoShi;
	}

	/**
	 * 所属期标识
	 * 
	 * @return
	 */
	public void setSuoShuQiBiaoShi(String suoShuQiBiaoShi) {
		this.suoShuQiBiaoShi = suoShuQiBiaoShi;
	}

	/**
	 * 进料登记册号
	 */
	public String getJlDjch() {
		return jlDjch;
	}

	/**
	 * 进料登记册号
	 */
	public void setJlDjch(String jlDjch) {
		this.jlDjch = jlDjch;
	}

	/**
	 * 进口报关单号
	 */

	public String getJkbgdh() {
		return jkbgdh;
	}

	/**
	 * 进口报关单号
	 */
	public void setJkbgdh(String jkbgdh) {
		this.jkbgdh = jkbgdh;
	}

	/**
	 * 记销售日期
	 */
	public String getJxsrq() {
		return jxsrq;
	}

	/**
	 * 记销售日期
	 */
	public void setJxsrq(String jxsrq) {
		this.jxsrq = jxsrq;
	}

	/**
	 * 代理证明号
	 */
	public String getDlzmh() {
		return dlzmh;
	}

	/**
	 * 代理证明号
	 */
	public void setDlzmh(String dlzmh) {
		this.dlzmh = dlzmh;
	}

	/**
	 * 出口日期
	 */
	public String getCkrq() {
		return ckrq;
	}

	/**
	 * 出口日期
	 */
	public void setCkrq(String ckrq) {
		this.ckrq = ckrq;
	}

	/**
	 * 外汇核销单号
	 */
	public String getWhxhdh() {
		return whxhdh;
	}

	/**
	 * 外汇核销单号
	 */
	public void setWhxhdh(String whxhdh) {
		this.whxhdh = whxhdh;
	}

	/**
	 * 进/出口数量
	 */
	public String getJcksl() {
		return jcksl;
	}

	/**
	 * 进/出口数量
	 */
	public void setJcksl(String jcksl) {
		this.jcksl = jcksl;
	}

	/**
	 * 原币代码
	 */
	public String getYbdm() {
		return ybdm;
	}

	/**
	 * 原币代码
	 */
	public void setYbdm(String ybdm) {
		this.ybdm = ybdm;
	}

	/**
	 * 原币到岸价/原币离岸价
	 */
	public String getYbdaj() {
		return ybdaj;
	}

	/**
	 * 原币到岸价/原币离岸价
	 */
	public void setYbdaj(String ybdaj) {
		this.ybdaj = ybdaj;
	}

	/**
	 * 进/出口商品代码
	 */
	public String getJckspdm() {
		return jckspdm;
	}

	/**
	 * 进/出口商品代码
	 */
	public void setJckspdm(String jckspdm) {
		this.jckspdm = jckspdm;
	}

	/**
	 * 出口发票号
	 */
	public String getCkfph() {
		return ckfph;
	}

	/**
	 * 出口发票号
	 */
	public void setCkfph(String ckfph) {
		this.ckfph = ckfph;
	}

	/**
	 * 计量单位
	 */
	public String getJldw() {
		return jldw;
	}

	/**
	 * 计量单位
	 */
	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	/**
	 * 出口商品名称
	 */
	public String getCkspmc() {
		return ckspmc;
	}

	/**
	 * 出口商品名称
	 */
	public void setCkspmc(String ckspmc) {
		this.ckspmc = ckspmc;
	}
	/**
	 * 运输方式
	 * @return
	 */
	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}
	 /**
     * 运输工具
     * @param conVeyance
     */
	public String getConveyance() {
		return conveyance;
	}
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}
	/**
	 * 提运单号
	 * @return
	 */
	public String getBillOfLading() {
		return billOfLading;
	}
	
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
    
}
