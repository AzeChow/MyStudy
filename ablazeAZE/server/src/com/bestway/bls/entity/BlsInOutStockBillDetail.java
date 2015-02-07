package com.bestway.bls.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 出入仓单据（表体）
 * 
 * @author hw
 */
public class BlsInOutStockBillDetail extends BaseScmEntity {
	/**
	 * 是否生效
	 */
	private Boolean isEffective = false;

	/**
	 * 进出仓单据表头
	 */
	private BlsInOutStockBill bsb = null;
	/**
	 * 原产国
	 */
	private Country originCountry = null;
	/**
	 * 企业内部货号
	 */
	private Materiel partNo = null;
	/**
	 * 数量
	 */
	private Double detailQty = null;
	/**
	 * 申报单价
	 */
	private Double unitPrice = null;

	/**
	 * 币值
	 */
	private Curr curr = null;
	/**
	 * 毛重
	 */
	private Double grossWeight = null;
	/**
	 * 净重
	 */
	private Double netWeight = null;
	/**
	 * 件数
	 */
	private Integer packCount = null;

	/**
	 * 已全转仓单
	 */
	private Boolean isStockBill = false;

	/**
	 * GNo 归并序号(Integer)
	 * 
	 * @uml.property name="gNo"
	 */
	private Integer seqNum;
	/**
	 * 单位
	 */
	private CalUnit unit;
	/**
	 * 型号规格
	 */
	private String spec;

	/**
	 * 商品名称
	 */
	private String warehouseName;
	/**
	 * 总价
	 */
	private Double totalPrice;

	/**
	 * 报关单18位编号
	 */
	private String entryID;

	/**
	 *入仓单据单据号 出仓单据专用
	 */
	private String inBillNo;
	
	/**
	 * 期初单据现剩余数量
	 */
	private Double nowDetailQty;

	/**
	 * 入仓单据商品序号 出仓单专用
	 */
	private Integer inBillGoodNo;

	/**
	 * 账册序号
	 */
	private Integer emsNo;
	
	/**
	 * 获取账册序号
	 * @return
	 */
	public Integer getEmsNo() {
		return emsNo;
	}
	
	/**
	 * 设置账册序号
	 * @param emsNo
	 */
	public void setEmsNo(Integer emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取入仓单据商品序号 出仓单专用
	 * @return
	 */
	public Integer getInBillGoodNo() {
		return inBillGoodNo;
	}

	/**
	 * 设置入仓单据商品序号 出仓单专用
	 * @param inBillGoodNo
	 */
	public void setInBillGoodNo(Integer inBillGoodNo) {
		this.inBillGoodNo = inBillGoodNo;
	}

	/**
	 * 报关单商品序号
	 */
	private String entryGNo;

	/**
	 * 备注1
	 */
	private String remarks1;

	/**
	 * 备注2
	 */
	private String remarks2;

	/**
	 * 商品流水号 出仓单专用
	 */
	private Integer seqNo;

	/**
	 * 获取流水号
	 * 
	 * @return
	 */
	public Integer getSeqNo() {
		return seqNo;
	}

	/**
	 * 设置流水号
	 * 
	 * @param seqNo
	 */
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 设置报关单18位编号
	 * 
	 * @param entryID
	 */
	public void setEntryID(String entryID) {
		this.entryID = entryID;
	}

	/**
	 * 获取报关单18位编号
	 * 
	 * @return
	 */
	public String getEntryID() {
		return entryID;
	}

	/**
	 * 获取报关单商品序号
	 * 
	 * @return
	 */
	public String getEntryGNo() {
		return entryGNo;
	}

	/**
	 * 设置报关单商品序号
	 * 
	 * @param entryGNo
	 */
	public void setEntryGNo(String entryGNo) {
		this.entryGNo = entryGNo;
	}

	/**
	 * 得到备注1
	 * 
	 * @return
	 */
	public String getRemarks1() {
		return remarks1;
	}

	/**
	 * 设置备注1
	 * 
	 * @param remarks1
	 */
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

	/**
	 * 获取备注2
	 * 
	 * @return
	 */
	public String getRemarks2() {
		return remarks2;
	}

	/**
	 * 设置备注2
	 * 
	 * @param remarks2
	 */
	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}

	/**
	 * 获取 归并序号(Integer)
	 * 
	 * @return
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置 归并序号(Integer)
	 * 
	 * @param seqNum
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取已转仓单的(Boolean)型标志位
	 * 
	 * @return
	 */
	public Boolean getIsStockBill() {
		return isStockBill;
	}

	/**
	 * 设置已转仓单的(Boolean)型标志位
	 * 
	 * @param isStockBill
	 */
	public void setIsStockBill(Boolean isStockBill) {
		this.isStockBill = isStockBill;
	}

	/**
	 * 得到进出仓单据表头
	 * 
	 * @return 进出仓单据表头
	 */
	public BlsInOutStockBill getBsb() {
		return bsb;
	}

	/**
	 * 设置进出仓单据表头
	 * 
	 * @param packCount
	 */
	public void setBsb(BlsInOutStockBill bsb) {
		this.bsb = bsb;
	}

	/**
	 * 得到原产国
	 * 
	 * @return 原产国
	 */
	public Country getOriginCountry() {
		return originCountry;
	}

	/**
	 * 设置原产国
	 * 
	 * @param originCountry
	 */
	public void setOriginCountry(Country originCountry) {
		this.originCountry = originCountry;
	}

	/**
	 * 得到企业内部货号
	 * 
	 * @return 企业内部货号
	 */
	public Materiel getPartNo() {
		return partNo;
	}

	/**
	 * 设置企业内部货号
	 * 
	 * @param partNo
	 */
	public void setPartNo(Materiel partNo) {
		this.partNo = partNo;
	}

	/**
	 * 得到数量
	 * 
	 * @return 数量
	 */
	public Double getDetailQty() {
		return detailQty;
	}

	/**
	 * 设置数量
	 * 
	 * @param detailQty
	 */
	public void setDetailQty(Double detailQty) {
		this.detailQty = detailQty;
	}

	/**
	 * 得到申报单价
	 * 
	 * @return 申报单价
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 设置申报单价
	 * 
	 * @param unitPrice
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 得到币制
	 * 
	 * @return 币制
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币制
	 * 
	 * @param curr
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 得到毛重
	 * 
	 * @return 毛重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * 设置毛重
	 * 
	 * @param grossWeight
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * 得到净重
	 * 
	 * @return 净重
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * 设置净重
	 * 
	 * @param netWeight
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * 得到件数
	 * 
	 * @return 件数
	 */
	public Integer getPackCount() {
		return packCount;
	}

	/**
	 * 设置件数
	 * 
	 * @param packCount
	 */
	public void setPackCount(Integer packCount) {
		this.packCount = packCount;
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
	 * 得到单位
	 * 
	 * @return
	 */
	public CalUnit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 */
	public void setUnit(CalUnit unit) {
		this.unit = unit;
	}

	/**
	 * 得到型号规格
	 * 
	 * @return
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置型号规格
	 * 
	 * @param spec
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 得到商品名称
	 * 
	 * @return
	 */
	public String getWarehouseName() {
		return warehouseName;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param warehouseName
	 */
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	/**
	 * 得到总价
	 * 
	 * @return
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 设置总价
	 * 
	 * @param totalPrice
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
    
	/**
	 * 获取入仓单据号(出仓单专用)
	 * @return
	 */
	public String getInBillNo() {
		return inBillNo;
	}
  
	/**
	 * 设置入仓单据号(出仓单专用)
	 * @param inBillNo
	 */
	public void setInBillNo(String inBillNo) {
		this.inBillNo = inBillNo;
	}

	public Double getNowDetailQty() {
		return nowDetailQty;
	}

	public void setNowDetailQty(Double nowDetailQty) {
		this.nowDetailQty = nowDetailQty;
	}


}
