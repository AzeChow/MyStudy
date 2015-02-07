/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

import java.util.Date;

/**
 * 导入JBCUS报关单表头数据
 *
 * @author lzj
 */
public class CustomsDeclarationHeadTemp {

    /**
     * 报关单表头Id
     */
    private String id;
    /**
     * 是否为进口类型,是为进口，否为出口
     */
    private Boolean isImport;

    /**
     * 企业代码
     */
    private String companyCode;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业海关编码
     */
    private String customsCode;
    /**
     * 报关单流水号
     */
    private Integer serialNumber;
    /**
     * 帐册号
     */
    private String emsNo;
    /**
     * 申请日期
     */
    private Date sendDate;
    /**
     * 报关单号
     */
    private String rollCovorNO;
    /**
     * 运输方式
     */
    private String transf;
    /**
     * 贸易方式
     */
    private String trade;
    /**
     * 起运国/运抵国
     */
    private String country;
    /**
     * 转入境内目的地/转发出境内货源地
     */
    private String district;
    /**
     * 转入装货港/转出指运港
     */
    private String portLin;
    /**
     * 转入币别/转出币别
     */
    private String currency;
    /**
     * 件数
     */
    private Integer pieces;
    /**
     * 毛重
     */
    private Double grossWeight;
    /**
     * 净重
     */
    private Double netWeight;
    /**
     * 报送海关
     */
    private String customs;
    /**
     * 码头
     */
    private String preDock;
    
    /**
     * 经营单位代码
     */
    private String tradeCode;
    /**
     * 经营单位名称
     */
    private String tradeName;
    /**
     * 进口口岸/出口口岸
     */
    private String port;
    /**
     * 运输工具
     */
    private String conveyance;
    /**
     * 提运单号
     */
    private String billOfLading;
    /**
     * 有纸无纸报关(转入/转出) true为无纸报关
     */
    private Boolean isPaperDelare = true;
    /**
     * 合同协议号
     */
    private String contract;
    /**
     * 转入/转出批准文号
     */
    private String applyNo;
    /**
     * 成交方式
     */
    private String transac;
    /**
     * 包装种类
     */
    private String wrap;
    /**
     * 征免性质(转入/转出)
     */
    private String levyKind;
    /**
     * 备注(转入\转出)
     */
    private String note;
    /**
     * 转出生产厂商
     */
    private String companyOut;
    /**
     * 转出结汇方式
     */
    private String balanceMode;

    /**
     * JBCUS是否下载过
     */
    private Boolean isJBCUSDown;
    
    /**
     *  是否选择与界面主键绑定
     */
    private Boolean isSelected;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Boolean getIsJBCUSDown() {
        return isJBCUSDown;
    }

    public void setIsJBCUSDown(Boolean isJBCUSDown) {
        this.isJBCUSDown = isJBCUSDown;
    }

    public String getEmsNo() {
        return emsNo;
    }

    public void setEmsNo(String emsNo) {
        this.emsNo = emsNo;
    }

    public String getRollCovorNO() {
        return rollCovorNO;
    }

    public void setRollCovorNO(String rollCovorNO) {
        this.rollCovorNO = rollCovorNO;
    }

    public String getTransf() {
        return transf;
    }

    public void setTransf(String transf) {
        this.transf = transf;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPortLin() {
        return portLin;
    }

    public void setPortLin(String portLin) {
        this.portLin = portLin;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public String getPreDock() {
        return preDock;
    }

    public void setPreDock(String preDock) {
        this.preDock = preDock;
    }

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Boolean getIsImport() {
		return isImport;
	}

	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
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

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getConveyance() {
		return conveyance;
	}

	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	public String getBillOfLading() {
		return billOfLading;
	}

	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}

	public Boolean getIsPaperDelare() {
		return isPaperDelare;
	}

	public void setIsPaperDelare(Boolean isPaperDelare) {
		this.isPaperDelare = isPaperDelare;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getTransac() {
		return transac;
	}

	public void setTransac(String transac) {
		this.transac = transac;
	}

	public String getWrap() {
		return wrap;
	}

	public void setWrap(String wrap) {
		this.wrap = wrap;
	}

	public String getLevyKind() {
		return levyKind;
	}

	public void setLevyKind(String levyKind) {
		this.levyKind = levyKind;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCompanyOut() {
		return companyOut;
	}

	public void setCompanyOut(String companyOut) {
		this.companyOut = companyOut;
	}

	public String getBalanceMode() {
		return balanceMode;
	}

	public void setBalanceMode(String balanceMode) {
		this.balanceMode = balanceMode;
	}
    
}
