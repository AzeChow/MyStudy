package com.bestway.bcus.enc.entity;

import java.math.BigDecimal;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 进出口申请单表体资料
 * 
 * @author Administrator table="impexpcommodityinfo"
 * 
 */
public class ImpExpCommodityInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 物料对象
	 */
	private Materiel materiel = null;  //  @jve:decl-index=0:

	/**
	 * 进出口申请单对象
	 */
	private ImpExpRequestBill impExpRequestBill = null;  //  @jve:decl-index=0:

	/**
	 * 国家地区对象
	 */
	private Country country = null;

	/**
	 * 币制对象
	 */
	private Curr currency = null;

	/**
	 * 备案序号
	 */

	private Integer seqNum = null;  //  @jve:decl-index=0:

	/**
	 * 备案各称
	 */
	private String afterName = null;  //  @jve:decl-index=0:

	/**
	 * 备案规格
	 */
	private String afterSpec = null;  //  @jve:decl-index=0:

	/**
	 * 备案单位
	 */
	private String afterUnit = null;  //  @jve:decl-index=0:

	/**
	 * 单价
	 */
	private Double unitPrice = null;

	/**
	 * 净重
	 */
	private Double netWeight = null;  //  @jve:decl-index=0:
	/**
	 * 毛重
	 */
	private Double grossWeight = null;  //  @jve:decl-index=0:
	/**
	 * 单净重
	 */
	private Double invnetWeight = null;
	/**
	 * 单毛重
	 */
	private Double invgrossWeight = null;
	/**
	 * 数量
	 */
	private Double quantity = null;  //  @jve:decl-index=0:
	/**
	 * 件数
	 */
	private Integer piece = null;  //  @jve:decl-index=0:
	/**
	 * 箱号
	 */
	private String boxNo = null;  //  @jve:decl-index=0:

	/**
	 * 海关版本号
	 */
	private String version = null;

	/**2012-02-29 by lxr 为光宝增加
	 * 电子帐册企业使用
	 * 企业版本号
	 */
	private String cmpVersion = null;

	/**
	 * 制单号
	 */
	private String makeBillNo = null;

	/**
	 * 体积
	 */
	private Double bulks = null;

	/**
	 * 工缴费(加工费总价)
	 */
	private Double workUsd;

	/**
	 * 总金额
	 */
	private Double amountPrice = null;

	/**
	 * 是否备案
	 */
	private Boolean isPutOnRecord = false;

	/**
	 * 已转报关清单或已转报关单
	 */
	private Boolean isTransferCustomsBill = false;  //  @jve:decl-index=0:

	/**
	 * 料号
	 */
	private String ptNo = null;

	/**
	 * 净重差异
	 */
	private Double netweightcy;

	/**
	 * 毛重差异
	 */
	private Double gossweightcy;  //  @jve:decl-index=0:

	/**
	 * 总净重
	 */
	private Double totalnetweight = null; // 总净重（对出口来说：总净重=单位净重*数量，对进口来说：总净重=单位净重）  //  @jve:decl-index=0:
	/**
	 * 总毛重--震兴
	 */
	private Double totalgrossWeight = null; // 总毛重（总毛重=单位毛重*件数）  //  @jve:decl-index=0:

	/**
	 * 数量
	 */
	private Double tempAmount = null;  //  @jve:decl-index=0:

	/**
	 * 扩展备注
	 */
	private String extendMemo = null;  //  @jve:decl-index=0:

	/**
	 * 扩展备注1
	 */
	private String extendMemo1 = null;  //  @jve:decl-index=0:

	// --以下插件用到，主程序一般不用，但可以临时使用
	/**
	 * 扩展－－ 商品编码 ，一般插件用到
	 */
	private String complexcode = null;
	/**
	 * 扩展备注2 ，一般插件用到
	 */
	private String extendMemo2 = null;  //  @jve:decl-index=0:
	
	/**
	 * 箱数 - 胜美达
	 */
	private String totalBoxNum = null;  //  @jve:decl-index=0:
	/**
	 * 起始箱号 - 胜美达
	 */
	private String startBoxNo = null;  //  @jve:decl-index=0:
	/**
	 * 截止箱号 - 胜美达
	 */
	private String endBoxNo = null;	
	/**
	 * 包装方式 - 胜美达,联胜,康磁电子使用
	 */
	private Wrap wrapType;  //  @jve:decl-index=0:
	
	/**
	 * 单箱量(每箱货物的重量) - 技嘉
	 */
	private Double indEventweight = null;  //  @jve:decl-index=0:
	/**
	 * 客户别(客户名称) - 技嘉
	 */
	private String customerOtherName = null;  //  @jve:decl-index=0:
	/**
	 * 单位折算 - 联胜使用
	 */
	private Double unitconvert = null;  //  @jve:decl-index=0:
	/**
	 * 报关数量 - 联胜使用
	 */
	private Double commAmount = null;  //  @jve:decl-index=0:
	
	/**
	 * 客户料号 - 康磁使用
	 */
	private String kcPtNo = null;
	/**
	 * 厂别 - 康磁使用
	 */
	private String kcfactoryKind = null;
	
	
	/**
	 * 获得体积
	 * 
	 * @return 体积
	 */
	public Double getBulks() {
		return bulks;
	}

	/**
	 * 设置体积
	 * 
	 * @param bulk
	 *            体积
	 */
	public void setBulks(Double bulk) {
		this.bulks = bulk;
	}

	/**
	 * 获得币制对象
	 * 
	 * @return 币制对象
	 */
	public Curr getCurrency() {
		return currency;
	}

	/**
	 * 设置币制对象
	 * 
	 * @param currency
	 *            币制对象
	 */
	public void setCurrency(Curr currency) {
		this.currency = currency;
	}

	/**
	 * 获得毛重
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
	 *            毛重
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * 获得进出口申请单对象
	 * 
	 * @return 进出口申请单对象
	 */
	public ImpExpRequestBill getImpExpRequestBill() {
		return impExpRequestBill;
	}

	/**
	 * 设置进出口申请单对象
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单对象
	 */
	public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.impExpRequestBill = impExpRequestBill;
	}

	/**
	 * 获得制单号
	 * 
	 * @return 制单号
	 */
	public String getMakeBillNo() {
		return makeBillNo;
	}

	/**
	 * 设置制单号
	 * 
	 * @param makeBillNo
	 *            制单号
	 */
	public void setMakeBillNo(String makeBillNo) {
		this.makeBillNo = makeBillNo;
	}

	/**
	 * 获得物料对象
	 * 
	 * @return 物料对象
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * 设置物料对象
	 * 
	 * @param materiel
	 *            物料对象
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * 获得净重
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
	 *            净重
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * 获得数量
	 * 
	 * @return 数量
	 */
	public Double getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获得箱号
	 * 
	 * @return 箱号
	 */
	public String getBoxNo() {
		return boxNo;
	}

	/**
	 * 设置箱号
	 * 
	 * @param boxNo
	 */

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	/**
	 * 获得单价
	 * 
	 * @return 单价
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 设置单价
	 * 
	 * @param unitPrice
	 *            单价
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 获得海关版本号
	 * 
	 * @return 版本号
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置海关版本号
	 * 
	 * @param version
	 *            版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 设置企业版本号
	 * 
	 * @param version
	 *            版本号
	 */
	public void setCmpVersion(String cmpVersion) {
		this.cmpVersion = cmpVersion;
	}

	/**
	 * 获得企业版本号
	 * 
	 * @return 版本号
	 */
	public String getCmpVersion() {
		return cmpVersion;
	}


	/**
	 * 获得加工费总价
	 * 
	 * @return 加工费总价
	 */
	public Double getWorkUsd() {
		return workUsd;
	}

	/**
	 * 设置加工费总价
	 * 
	 * @param workUsd
	 *            加工费总价
	 */
	public void setWorkUsd(Double workUsd) {
		this.workUsd = workUsd;
	}

	/**
	 * 获得总金额
	 * 
	 * @return 总金额
	 */
	public Double getAmountPrice() {
		if (amountPrice != null) {
			return amountPrice;
		}
		if (this.quantity == null || this.unitPrice == null) {
			return Double.valueOf(0);
		}
		BigDecimal b = new BigDecimal(this.quantity.doubleValue()
				* this.unitPrice.doubleValue());
		return Double.valueOf(b.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

	/**
	 * 获得总毛重
	 * 
	 * @return 总毛重
	 */

	public Double getGWeight() {
		if (this.invgrossWeight != null && !this.invgrossWeight.equals(0.0)) {
			BigDecimal b = new BigDecimal(quantity == null ? 0.0
					: this.quantity.doubleValue()
							* this.invgrossWeight.doubleValue());
			return Double.valueOf(b.setScale(4, BigDecimal.ROUND_HALF_UP)
					.doubleValue());

		} else {
			return grossWeight;
		}
	}
	
	/**
	 * 获得总净重
	 * 
	 * @return 总净重
	 */
	public Double getNWeight() {
		if (this.invnetWeight != null && !this.invnetWeight.equals(0.0)) {
			BigDecimal b = new BigDecimal(quantity == null ? 0.0
					: this.quantity.doubleValue()
							* this.invnetWeight.doubleValue());
			return Double.valueOf(b.setScale(4, BigDecimal.ROUND_HALF_UP)
					.doubleValue());

		} else {
			return netWeight;
		}
	}
		
	/**
	 * 获得国家地区对象
	 * 
	 * @return 国家地区对象
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置国家地区对象
	 * 
	 * @param country
	 *            国家地区对象
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 判断是否备案
	 * 
	 * @return 是否备案
	 */
	public Boolean getIsPutOnRecord() {
		return isPutOnRecord;
	}

	/**
	 * 设置是否备案标志
	 * 
	 * @param isPutOnRecord
	 *            是否备案
	 */
	public void setIsPutOnRecord(Boolean isPutOnRecord) {
		this.isPutOnRecord = isPutOnRecord;
	}

	/**
	 * 判断已转报关清单或已转报关单
	 * 
	 * @return 已转报关清单或已转报关单
	 */
	public Boolean getIsTransferCustomsBill() {
		return isTransferCustomsBill;
	}

	/**
	 * 设置已转报关清单或已转报关单标志
	 * 
	 * @param isTransferCustomsBill
	 *            已转报关清单或已转报关单
	 */
	public void setIsTransferCustomsBill(Boolean isTransferCustomsBill) {
		this.isTransferCustomsBill = isTransferCustomsBill;
	}

	/**
	 * 获得件数
	 * 
	 * @return 件数
	 */
	public Integer getPiece() {
		return piece;
	}

	/**
	 * 设置件数
	 * 
	 * @param piece
	 *            件数
	 */
	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	/**
	 * 获得料号
	 * 
	 * @return 料号
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 设置料号
	 * 
	 * @param ptNo
	 *            料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	/**
	 * 获得毛重差异
	 * 
	 * @return 毛重差异 备用
	 */
	public Double getGossweightcy() {
		return gossweightcy;
	}

	/**
	 * 设置毛重差异
	 * 
	 * @param gossweightcy
	 *            毛重差异 备用
	 */
	public void setGossweightcy(Double gossweightcy) {
		this.gossweightcy = gossweightcy;
	}

	/**
	 * 获得净重差异
	 * 
	 * @return 净重差异 备用
	 */
	public Double getNetweightcy() {
		return netweightcy;
	}

	/**
	 * 设置净重差异
	 * 
	 * @param netweightcy
	 *            净重差异 备用
	 */
	public void setNetweightcy(Double netweightcy) {
		this.netweightcy = netweightcy;
	}

	public Double getTotalnetweight() {
		return totalnetweight;
	}

	public void setTotalnetweight(Double totalnetweight) {
		this.totalnetweight = totalnetweight;
	}

	public Double getTotalgrossWeight() {
		return totalgrossWeight;
	}

	public void setTotalgrossWeight(Double totalgrossWeight) {
		this.totalgrossWeight = totalgrossWeight;
	}

	public Double getTempAmount() {
		return tempAmount;
	}

	public void setTempAmount(Double tempAmount) {
		this.tempAmount = tempAmount;
	}

	public String getExtendMemo() {
		return extendMemo;
	}

	public void setExtendMemo(String extendMemo) {
		this.extendMemo = extendMemo;
	}

	public String getExtendMemo1() {
		return extendMemo1;
	}

	public void setExtendMemo1(String extendMemo1) {
		this.extendMemo1 = extendMemo1;
	}

	public void setAmountPrice(Double amountPrice) {
		this.amountPrice = amountPrice;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getAfterName() {
		return afterName;
	}

	public void setAfterName(String afterName) {
		this.afterName = afterName;
	}

	public String getAfterSpec() {
		return afterSpec;
	}

	public void setAfterSpec(String afterSpec) {
		this.afterSpec = afterSpec;
	}

	public String getAfterUnit() {
		return afterUnit;
	}

	public void setAfterUnit(String afterUnit) {
		this.afterUnit = afterUnit;
	}

	public Double getInvgrossWeight() {
		return invgrossWeight;
	}

	public void setInvgrossWeight(Double invgrossWeight) {
		this.invgrossWeight = invgrossWeight;
	}

	public Double getInvnetWeight() {
		return invnetWeight;
	}

	public void setInvnetWeight(Double invnetWeight) {
		this.invnetWeight = invnetWeight;
	}

	public String getComplexcode() {
		return complexcode;
	}

	public void setComplexcode(String complexcode) {
		this.complexcode = complexcode;
	}

	public String getExtendMemo2() {
		return extendMemo2;
	}

	public void setExtendMemo2(String extendMemo2) {
		this.extendMemo2 = extendMemo2;
	}

	public String getTotalBoxNum() {
		return totalBoxNum;
	}

	public void setTotalBoxNum(String totalBoxNum) {
		this.totalBoxNum = totalBoxNum;
	}

	public String getStartBoxNo() {
		return startBoxNo;
	}

	public void setStartBoxNo(String startBoxNo) {
		this.startBoxNo = startBoxNo;
	}

	public String getEndBoxNo() {
		return endBoxNo;
	}

	public void setEndBoxNo(String endBoxNo) {
		this.endBoxNo = endBoxNo;
	}

	public Wrap getWrapType() {
		return wrapType;
	}

	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}

	public Double getIndEventweight() {
		return indEventweight;
	}

	public void setIndEventweight(Double indEventweight) {
		this.indEventweight = indEventweight;
	}

	public String getCustomerOtherName() {
		return customerOtherName;
	}

	public void setCustomerOtherName(String customerOtherName) {
		this.customerOtherName = customerOtherName;
	}
	/**
	 * 单位折算
	 * @return
	 */
	public Double getUnitconvert() {
		return unitconvert;
	}
	/**
	 * 单位折算
	 */
	public void setUnitconvert(Double unitconvert) {
		this.unitconvert = unitconvert;
	}
	/**
	 * 报关数量
	 * @return
	 */
	public Double getCommAmount() {
		return commAmount;
	}
	/**
	 * 报关数量
	 */
	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	public String getKcfactoryKind() {
		return kcfactoryKind;
	}

	public void setKcfactoryKind(String kcfactoryKind) {
		this.kcfactoryKind = kcfactoryKind;
	}

	public String getKcPtNo() {
		return kcPtNo;
	}

	public void setKcPtNo(String kcPtNo) {
		this.kcPtNo = kcPtNo;
	}
}