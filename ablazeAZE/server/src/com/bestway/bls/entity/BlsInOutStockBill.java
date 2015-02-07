package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 出入仓单据（表头）实体类
 * 
 * @author hw
 * 
 */
public class BlsInOutStockBill extends BaseScmEntity {
	/**
	 * 进出仓标志(Integer)
	 * com.bestway.bls.entity.BlsIOStockBillIOF 
	 * 入仓 IMPORT = "I"
	 * 出仓 EXPORT = "O"
	 */
	private String ioFlag = null;

	/**
	 * 是否生效
	 */
	private Boolean isEffective = false;

	/**
	 * 单据编号
	 */
	private String billNo = null;
	/**
	 * 单据生效日期
	 */
	private Date validDate = null;
	/**
	 * 供货方企业
	 */
	private ScmCoc corrOwner = null;
	/**
	 * 仓库编码
	 */
	private WareSet wareHouseCode = null;

	/**
	 * 标示入仓单据是否为期初单据
	 */
	private Boolean isFirstBill = false;// 默认false
	// /**
	// * 录入日期
	// */
	// private Date createDate;
	// /**
	// * 得到录入日期
	// * @return
	// */
	// public Date getCreateDate() {
	// return createDate;
	// }
	// /**
	// * 设置录入日期
	// * @param createdDate
	// */
	// public void setCreateDate(Date createdDate) {
	// this.createDate = createdDate;
	// }

	/**
	 * 得到进出仓标志
	 * 
	 * @return 进出仓标志
	 */
	public String getIoFlag() {
		return ioFlag;
	}

	/**
	 * 设置进出仓标志
	 * 
	 * @param ioFlag
	 */

	public void setIoFlag(String ioFlag) {
		this.ioFlag = ioFlag;
	}

	/**
	 * 得到单据编号
	 * 
	 * @return 单据编号
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 设置单据编号
	 * 
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 得到单据生效日期
	 * 
	 * @return 单据生效日期
	 */
	public Date getValidDate() {
		return validDate;
	}

	/**
	 * 设置单据生效日期
	 * 
	 * @param validDate
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 得到供货方企业
	 * 
	 * @return 供货方企业
	 */
	public ScmCoc getCorrOwner() {
		return corrOwner;
	}

	/**
	 * 设置供货方企业
	 * 
	 * @param corrOwnerCode
	 */
	public void setCorrOwner(ScmCoc corrOwnerCode) {
		this.corrOwner = corrOwnerCode;
	}

	/**
	 * 得到仓库编码
	 * 
	 * @return 仓库编码
	 */
	public WareSet getWareHouseCode() {
		return wareHouseCode;
	}

	/**
	 * 设置仓库编码
	 * 
	 * @param 仓库编码
	 */
	public void setWareHouseCode(WareSet wareHouseCode) {
		this.wareHouseCode = wareHouseCode;
	}

	/**
	 * 获取生效的(Boolean)标志位
	 * 
	 * @return
	 */
	public Boolean getIsEffective() {
		return isEffective;
	}

	/**
	 * 设置生效的(Boolean)标志位
	 * 
	 * @param effective
	 */
	public void setIsEffective(Boolean isEffective) {
		this.isEffective = isEffective;
	}

	/**
	 * 获取标示入仓单据是否为期初单据
	 */
	public Boolean getIsFirstBill() {
		if (isFirstBill == null) {
			return false;
		} else {
			return isFirstBill;
		}

	}

	/**
	 *设置标示入仓单据是否为期初单据
	 */
	public void setIsFirstBill(Boolean isFirstBill) {
		if (isFirstBill == null) {
			this.isFirstBill = false;
		} else {
			this.isFirstBill = isFirstBill;
		}
	}

}
