/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TempTransferStatisticTotalQuantity implements Serializable {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	private String materielCode=null;//料品代号
    private String materielName=null;//名称
    private String unit=null;//单位
    private String emsNo=null;//账册/手册号
    private String seqNum = null;//帐册序号
    private String scmcocName = null; //客户名称
    private Double preImpExpGoodsQuantity=null;//前期进出货累计
    private Double preTransferQuantity=null;//前期转厂累计
    private Double preNoTransferQuantity=null;//前期未转厂累计
    private Double nowImpExpGoodsQuantity=null;//本期进出货累计
    private Double nowTransferQuantity=null;//本期转厂累计
    private Double nowNoTransferQuantity=null;//本期未转厂累计
    private Double totalNoTransferQuantity=null;//合计未转厂累计
    private String envlopNo = null; //关封号
    
    /**
     * @return Returns the materielCode.
     */
    public String getMaterielCode() {
        return materielCode;
    }
    /**
     * @param materielCode The materielCode to set.
     */
    public void setMaterielCode(String materielCode) {
        this.materielCode = materielCode;
    }
    /**
     * @return Returns the materielName.
     */
    public String getMaterielName() {
        return materielName;
    }
    /**
     * @param materielName The materielName to set.
     */
    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }
    /**
     * @return Returns the nowImpExpGoodsQuantity.
     */
    public Double getNowImpExpGoodsQuantity() {
        return nowImpExpGoodsQuantity;
    }
    /**
     * @param nowImpExpGoodsQuantity The nowImpExpGoodsQuantity to set.
     */
    public void setNowImpExpGoodsQuantity(Double nowImpExpGoodsQuantity) {
        this.nowImpExpGoodsQuantity = nowImpExpGoodsQuantity;
    }
    /**
     * @return Returns the nowNoTransferQuantity.
     */
    public Double getNowNoTransferQuantity() {
        return nowNoTransferQuantity;
    }
    /**
     * @param nowNoTransferQuantity The nowNoTransferQuantity to set.
     */
    public void setNowNoTransferQuantity(Double nowNoTransferQuantity) {
        this.nowNoTransferQuantity = nowNoTransferQuantity;
    }
    /**
     * @return Returns the nowTransferQuantity.
     */
    public Double getNowTransferQuantity() {
        return nowTransferQuantity;
    }
    /**
     * @param nowTransferQuantity The nowTransferQuantity to set.
     */
    public void setNowTransferQuantity(Double nowTransferQuantity) {
        this.nowTransferQuantity = nowTransferQuantity;
    }
    /**
     * @return Returns the preImpExpGoodsQuantity.
     */
    public Double getPreImpExpGoodsQuantity() {
        return preImpExpGoodsQuantity;
    }
    /**
     * @param preImpExpGoodsQuantity The preImpExpGoodsQuantity to set.
     */
    public void setPreImpExpGoodsQuantity(Double preImpExpGoodsQuantity) {
        this.preImpExpGoodsQuantity = preImpExpGoodsQuantity;
    }
    /**
     * @return Returns the preNoTransferQuantity.
     */
    public Double getPreNoTransferQuantity() {
        return preNoTransferQuantity;
    }
    /**
     * @param preNoTransferQuantity The preNoTransferQuantity to set.
     */
    public void setPreNoTransferQuantity(Double preNoTransferQuantity) {
        this.preNoTransferQuantity = preNoTransferQuantity;
    }
    /**
     * @return Returns the preTransferQuantity.
     */
    public Double getPreTransferQuantity() {
        return preTransferQuantity;
    }
    /**
     * @param preTransferQuantity The preTransferQuantity to set.
     */
    public void setPreTransferQuantity(Double preTransferQuantity) {
        this.preTransferQuantity = preTransferQuantity;
    }
    /**
     * @return Returns the totalNoTransferQuantity.
     */
    public Double getTotalNoTransferQuantity() {
        return totalNoTransferQuantity;
    }
    /**
     * @param totalNoTransferQuantity The totalNoTransferQuantity to set.
     */
    public void setTotalNoTransferQuantity(Double totalNoTransferQuantity) {
        this.totalNoTransferQuantity = totalNoTransferQuantity;
    }
    /**
     * @return Returns the unit.
     */
    public String getUnit() {
        return unit;
    }
    /**
     * @param unit The unit to set.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getScmcocName() {
		return scmcocName;
	}
	public void setScmcocName(String scmcocName) {
		this.scmcocName = scmcocName;
	}
	public String getEnvlopNo() {
		return envlopNo;
	}
	public void setEnvlopNo(String envlopNo) {
		this.envlopNo = envlopNo;
	}
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
}	
