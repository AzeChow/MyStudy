/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 关封余量分析
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomsEnvelopSpareAnalyes implements Serializable {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 是否正则执行的关封
	 */
	private boolean isEff                  = false; 	
	/**
	 * 关封生效日期
	 */
	private Date beginAvailability = null; 
	/**
	 * 是进货还是出货
	 */
	private Boolean isImpExpGoods          = false; 	
	/**
	 * 截至日期
	 */
	private String endDate                 = null;
	/**
	 * 是否结案
	 */
	private Boolean isEndCase = false; 
	/**
	 * 单据号(关封号)
	 */
    private String  customsEnvelopBillNo   = null;
    /**
     * 帐册序号
     */
    private String  seqNum                 = null; 
    /**
     * 商品编码
     */
    private Complex complex                = null;
    /**
     * 商品名称
     */
    private String  name                   = null;
    /**
     * 规格型号
     */
    private String  spec                   = null; 
    /**
     * 关封数量
     */
    private Double  ownerQuantity          = null; 
    /**
     * 已转数量
     */
    private Double  transferQuantity       = null; 
    /**
     * 可分配数量
     */
    private Double  dispensabilityQuantity = null; 
    /**
     * 客户供应商
     */
    private ScmCoc  scmCoc                 = null;     
    
	/**
	 * 关封余量
	 */
	private Double customsRemainNum = null;
	
	/**
	 * 送货数量
	 */
	private Double transferFatoryCommodityInfoNum = null;
	
	/**
	 * 手册号或帐册号
	 */
	private String emsNo = null;
	/**
	 * 手同生效日期
	 */
	private Date beginEmsAvailability = null; 
	
	/**
	 * 工厂手册号
	 */
	private String factoryNo = null;
	/**
	 * 备案单位
	 */
	private Unit unit = null; 
	
	/**
	 * 关封序号
	 */
	private Integer ceseqNum;

    public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 *获取生效日期
	 */
	public Date getBeginAvailability() {
		if (beginAvailability == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(beginAvailability));
	}

	/**
	 *设置生效日期
	 */
	public void setBeginAvailability(Date beginAvailability) {
		this.beginAvailability = beginAvailability;
	}
	
	/**
	 *获取手同生效日期
	 */
	public Date getBeginEmsAvailability() {
		if (beginEmsAvailability == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(beginEmsAvailability));
	}

	/**
	 *设置手同生效日期
	 */
	public void setBeginEmsAvailability(Date beginEmsAvailability) {
		this.beginEmsAvailability = beginEmsAvailability;
	}
	/**
     * @return Returns the complex.
     */
    public Complex getComplex() {
        return complex;
    }

    /**
     * @param complex
     *            The complex to set.
     */
    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    /**
     * @return Returns the customsEnvelopBillNo.
     */
    public String getCustomsEnvelopBillNo() {
        return customsEnvelopBillNo;
    }

    /**
     * @param customsEnvelopBillNo
     *            The customsEnvelopBillNo to set.
     */
    public void setCustomsEnvelopBillNo(String customsEnvelopBillNo) {
        this.customsEnvelopBillNo = customsEnvelopBillNo;
    }

    /**
     * @return Returns the dispensabilityQuantity.
     */
    public Double getDispensabilityQuantity() {
        return dispensabilityQuantity;
    }

    /**
     * @param dispensabilityQuantity
     *            The dispensabilityQuantity to set.
     */
    public void setDispensabilityQuantity(Double dispensabilityQuantity) {
        this.dispensabilityQuantity = dispensabilityQuantity;
    }

    /**
     * @return Returns the isImpExpGoods.
     */
    public Boolean getIsImpExpGoods() {
        return isImpExpGoods;
    }

    /**
     * @param isImpExpGoods
     *            The isImpExpGoods to set.
     */
    public void setIsImpExpGoods(Boolean isImpExpGoods) {
        this.isImpExpGoods = isImpExpGoods;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the ownerQuantity.
     */
    public Double getOwnerQuantity() {
        return ownerQuantity;
    }

    /**
     * @param ownerQuantity
     *            The ownerQuantity to set.
     */
    public void setOwnerQuantity(Double ownerQuantity) {
        this.ownerQuantity = ownerQuantity;
    }

    /**
     * @return Returns the scmCoc.
     */
    public ScmCoc getScmCoc() {
        return scmCoc;
    }

    /**
     * @param scmCoc
     *            The scmCoc to set.
     */
    public void setScmCoc(ScmCoc scmCoc) {
        this.scmCoc = scmCoc;
    }

    /**
     * @return Returns the seqNum.
     */
    public String getSeqNum() {
        return seqNum;
    }

    /**
     * @param seqNum
     *            The seqNum to set.
     */
    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    /**
     * @return Returns the spec.
     */
    public String getSpec() {
        return spec;
    }

    /**
     * @param spec
     *            The spec to set.
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * @return Returns the transferQuantity.
     */
    public Double getTransferQuantity() {
        return transferQuantity;
    }

    /**
     * @param transferQuantity
     *            The transferQuantity to set.
     */
    public void setTransferQuantity(Double transferQuantity) {
        this.transferQuantity = transferQuantity;
    }

	public Double getCustomsRemainNum() {
		return customsRemainNum;
	}

	public void setCustomsRemainNum(Double customsRemainNum) {
		this.customsRemainNum = customsRemainNum;
	}

	public Double getTransferFatoryCommodityInfoNum() {
		return transferFatoryCommodityInfoNum;
	}

	public void setTransferFatoryCommodityInfoNum(
			Double transferFatoryCommodityInfoNum) {
		this.transferFatoryCommodityInfoNum = transferFatoryCommodityInfoNum;
	}

	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Boolean getIsEndCase() {
		return isEndCase;
	}

	public void setIsEndCase(Boolean isEndCase) {
		this.isEndCase = isEndCase;
	}

	public String getFactoryNo() {
		return factoryNo;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isEff() {
		return isEff;
	}

	public void setEff(boolean isEff) {
		this.isEff = isEff;
	}

	public Integer getCeseqNum() {
		return ceseqNum;
	}

	public void setCeseqNum(Integer ceseqNum) {
		this.ceseqNum = ceseqNum;
	}
	
	
}