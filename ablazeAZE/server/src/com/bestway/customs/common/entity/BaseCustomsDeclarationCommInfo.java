package com.bestway.customs.common.entity;

import java.util.HashMap;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.ExItemCode;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.ProjectDept;

/**
 * 内部商品新增报关单表体
 * 
 * @author refdom
 */
public class BaseCustomsDeclarationCommInfo extends BaseScmEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3125838937393709661L;

	/**
	 * 报关单头
	 */
	private BaseCustomsDeclaration baseCustomsDeclaration = null;

	/**
	 * 报关单商品流水号
	 */
	private Integer serialNumber;

	/**
	 * 商品序号
	 */
	private Integer commSerialNo;

	/**
	 * 商品信息
	 */
	private Complex complex;

	/**
	 * 商品名称
	 */
	private String commName;

	/**
	 * 商品规格
	 */
	private String commSpec;

	/**
	 * 规范申报规格
	 */
	private String declareSpec;

	/**
	 * 商品数量
	 */
	private Double commAmount;

	/**
	 * 商品单价
	 */
	private Double commUnitPrice;

	/**
	 * 美元总价
	 */
	private Double dollarTotalPrice;

	/**
	 * 商品总价
	 */
	private Double commTotalPrice;

	/**
	 * 工缴费(加工费总价)
	 */
	private Double workUsd;

	/**
	 * 单位
	 */
	private Unit unit;

	/**
	 * 法定单位
	 */
	private Unit legalUnit;

	/**
	 * 第二法定单位
	 */
	private Unit secondLegalUnit;

	/**
	 * 第一法定数量
	 */
	private Double firstAmount;

	/**
	 * 第一法定比例因子
	 */
	private Double legalUnitGene;
	/**
	 * 第二法定数量
	 */
	private Double secondAmount;

	/**
	 * 第二法定比例因子
	 */
	private Double legalUnit2Gene;

	/**
	 * 单位重量
	 */
	private Double unitWeight;

	/**
	 * 原产国
	 */
	private Country country;

	/**
	 * 用途
	 */
	private Uses uses;

	/**
	 * 减免方式
	 */
	private LevyMode levyMode;

	/**
	 * 毛重
	 */
	private Double grossWeight;

	/**
	 * 净重
	 */
	private Double netWeight;

	/**
	 * 件(箱)数
	 */
	private Integer pieces;

	/**
	 * 货号
	 */
	private String materielNo;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 新增来源
	 */
	private String addType;

	/**
	 * 税金
	 */
	private Double tax;

	/**
	 * 申报单位
	 */
	private Company declarationCompany;

	/**
	 * 备注
	 */
	private String detailNote;

	/**
	 * 事业部
	 */
	private ProjectDept projectDept;
	/**
	 * 包装种类
	 */
	private Wrap wrapType;
	/**
	 * 箱号
	 */
	private String boxNo;
	/**
	 * 料号
	 */
	private String ptNo;
	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 扩展字段1 港芝插件用于打印载货清单,包装种类全部取表体,当多个商品合并存放多个包装种类
	 */
	private String extendField1;

	/**
	 * 技嘉出口财务报表
	 */
	private ExItemCode exItemCode;

	/**
	 * 扩展字段2 港芝插件
	 */
	private HashMap extendKeyValue;

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	/**
	 * 取得报关单商品流水号
	 * 
	 * @return 报关单商品流水号
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 设置报关单商品流水号
	 * 
	 * @param serialNumber
	 *            报关单商品流水号
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 取得申报单位
	 * 
	 * @return 申报单位
	 */
	public Company getDeclarationCompany() {
		return declarationCompany;
	}

	/**
	 * 设置申报单位
	 * 
	 * @param declarationCompany
	 *            申报单位
	 */
	public void setDeclarationCompany(Company declarationCompany) {
		this.declarationCompany = declarationCompany;
	}

	/**
	 * 取得货号
	 * 
	 * @return 货号
	 */
	public String getMaterielNo() {
		return materielNo;
	}

	/**
	 * 取得版本号
	 * 
	 * @return 版本号
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置货号
	 * 
	 * @param materielNo
	 *            货号
	 */
	public void setMaterielNo(String materielNo) {
		this.materielNo = materielNo;
	}

	/**
	 * 设置版本号
	 * 
	 * @param version
	 *            版本号
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 取得报关单头
	 * 
	 * @return 报关单头
	 */
	public BaseCustomsDeclaration getBaseCustomsDeclaration() {
		return baseCustomsDeclaration;
	}

	/**
	 * 设置报关单头
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单头
	 */
	public void setBaseCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.baseCustomsDeclaration = baseCustomsDeclaration;
	}

	/**
	 * 取得商品名称
	 * 
	 * @return 商品名称
	 */
	public String getCommName() {
		return commName;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param commName
	 *            商品名称
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}

	/**
	 * 取得单位重量
	 * 
	 * @return 单位重量
	 */
	public Double getUnitWeight() {
		return unitWeight;
	}

	/**
	 * 设置单位重量
	 * 
	 * @param unitWeight
	 *            单位重量
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * 取得商品信息
	 * 
	 * @return 商品信息
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品信息
	 * 
	 * @param complex
	 *            商品信息
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 取得商品数量
	 * 
	 * @return 商品数量
	 */
	public Double getCommAmount() {
		return commAmount;
	}

	/**
	 * 设置商品数量
	 * 
	 * @param commAmount
	 *            商品数量
	 */
	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	/**
	 * 取得商品总价
	 * 
	 * @return 商品总价
	 */
	public Double getCommTotalPrice() {
		return commTotalPrice;
	}

	/**
	 * 设置商品总价
	 * 
	 * @param commPrice
	 *            商品总价
	 */
	public void setCommTotalPrice(Double commPrice) {
		this.commTotalPrice = commPrice;
	}

	/**
	 * 取得商品序号 (BCUS:电子账册的商品序号，BCS:合同序号)
	 * 
	 * @return 商品序号 (BCUS:电子账册的商品序号，BCS:合同序号)
	 */
	public Integer getCommSerialNo() {
		return commSerialNo;
	}

	/**
	 * 设置商品序号 (BCUS:电子账册的商品序号，BCS:合同序号)
	 * 
	 * @param commSerialNo
	 *            商品序号 (BCUS:电子账册的商品序号，BCS:合同序号)
	 */
	public void setCommSerialNo(Integer commSerialNo) {
		this.commSerialNo = commSerialNo;
	}

	/**
	 * 取得商品规格
	 * 
	 * @return 商品规格
	 */
	public String getCommSpec() {
		return commSpec;
	}

	/**
	 * 设置商品规格
	 * 
	 * @param commSpec
	 *            商品规格
	 */
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	/**
	 * 取得商品单价
	 * 
	 * @return 商品单价
	 */
	public Double getCommUnitPrice() {
		return commUnitPrice;
	}

	/**
	 * 设置商品单价
	 * 
	 * @param commUnitPrice
	 *            商品单价
	 */
	public void setCommUnitPrice(Double commUnitPrice) {
		this.commUnitPrice = commUnitPrice;
	}

	/**
	 * 取得原产国 or 最终目的产国
	 * 
	 * @return 原产国 or 最终目的产国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置原产国 or 最终目的产国
	 * 
	 * @param country
	 *            原产国 or 最终目的产国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 取得毛重
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
	 * 取得第一法定数量
	 * 
	 * @return 第一法定数量
	 */
	public Double getFirstAmount() {
		return firstAmount;
	}

	/**
	 * 设置第一法定数量
	 * 
	 * @param legalAmount
	 *            第一法定数量
	 */
	public void setFirstAmount(Double legalAmount) {
		this.firstAmount = legalAmount;
	}

	/**
	 * 取得法定单位
	 * 
	 * @return 法定单位
	 */
	public Unit getLegalUnit() {
		if (this.complex != null) {
			return this.complex.getFirstUnit();
		}
		return legalUnit;
	}

	/**
	 * 设置法定单位
	 * 
	 * @param legalUnit
	 *            法定单位
	 */
	public void setLegalUnit(Unit legalUnit) {
		this.legalUnit = legalUnit;
	}

	/**
	 * 取得减免方式
	 * 
	 * @return 减免方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * 设置减免方式
	 * 
	 * @param levyMode
	 *            减免方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	/**
	 * 取得净重
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
	 * 取得件(箱)数
	 * 
	 * @return 件(箱)数
	 */
	public Integer getPieces() {
		return pieces;
	}

	/**
	 * 设置件(箱)数
	 * 
	 * @param pieces
	 *            件(箱)数
	 */
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	/**
	 * 取得第二法定数量
	 * 
	 * @return 第二法定数量
	 */
	public Double getSecondAmount() {
		return secondAmount;
	}

	/**
	 * 设置第二法定数量
	 * 
	 * @param secondLegalAmount
	 *            第二法定数量
	 */
	public void setSecondAmount(Double secondLegalAmount) {
		this.secondAmount = secondLegalAmount;
	}

	/**
	 * 取得单位
	 * 
	 * @return 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 取得用途
	 * 
	 * @return 用途
	 */
	public Uses getUses() {
		return uses;
	}

	/**
	 * 设置用途
	 * 
	 * @param uses
	 *            用途
	 */
	public void setUses(Uses uses) {
		this.uses = uses;
	}

	/**
	 * 取得第二法定单位
	 * 
	 * @return 第二法定单位
	 */
	public Unit getSecondLegalUnit() {
		if (this.complex != null) {
			return this.complex.getSecondUnit();
		}
		return secondLegalUnit;
	}

	/**
	 * 设置第二法定单位
	 * 
	 * @param legalSecondUnit
	 *            第二法定单位
	 */
	public void setSecondLegalUnit(Unit legalSecondUnit) {
		this.secondLegalUnit = legalSecondUnit;
	}

	/**
	 * 取得新增来源
	 * 
	 * @return 新增来源
	 */
	public String getAddType() {
		return addType;
	}

	/**
	 * 设置新增来源
	 * 
	 * @param addType
	 *            新增来源
	 */
	public void setAddType(String addType) {
		this.addType = addType;
	}

	/**
	 * 美元总价
	 * 
	 * @return
	 */

	public Double getDollarTotalPrice() {
		Double rate = this.getBaseCustomsDeclaration().getExchangeRate();
		if (this.getBaseCustomsDeclaration() != null && rate != null
				&& rate > 0.0) {
			return (this.getCommTotalPrice() == null ? Double.valueOf(0) : this
					.getCommTotalPrice()) * rate;
		}
		return this.getCommTotalPrice();
	}

	public void setDollarTotalPrice(Double dollarTotalPrice) {
		this.dollarTotalPrice = dollarTotalPrice;
	}

	public String getDetailNote() {
		return detailNote;
	}

	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}

	public ProjectDept getProjectDept() {
		return projectDept;
	}

	public void setProjectDept(ProjectDept projectDept) {
		this.projectDept = projectDept;
	}

	/**
	 * 货名
	 * 
	 * @return
	 */
	public String getTempName() {
		return commName
				+ ((commSpec == null || "".equals(commSpec)) ? "" : "/"
						+ commSpec);
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public Wrap getWrapType() {
		return wrapType;
	}

	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}

	public Double getWorkUsd() {
		return workUsd;
	}

	public void setWorkUsd(Double workUsd) {
		this.workUsd = workUsd;
	}

	/**
	 * 得到连接数量及单位后的字符串
	 */
	public String getUnitAddcommAmount() {
		String commAmount1 = String.valueOf(commAmount);
		String sum = commAmount1 + " " + unit.getName();
		return sum;
	}

	/**
	 * 获取 料号
	 * 
	 * @return
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 设置 料号
	 * 
	 * @param ptNo
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	/**
	 * 获取 备注
	 * 
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	public ExItemCode getExItemCode() {
		return exItemCode;
	}

	public void setExItemCode(ExItemCode exItemCode) {
		this.exItemCode = exItemCode;
	}

	/**
	 * 设置 备注
	 * 
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getExtendField1() {
		return extendField1;
	}

	public void setExtendField1(String extendField1) {
		this.extendField1 = extendField1;
	}

	public HashMap getExtendKeyValue() {
		return extendKeyValue;
	}

	public void setExtendKeyValue(HashMap extendKeyValue) {
		this.extendKeyValue = extendKeyValue;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	/**
	 * @return the declareSpec
	 */
	public String getDeclareSpec() {
		return declareSpec;
	}

	/**
	 * @param declareSpec
	 *            the declareSpec to set
	 */
	public void setDeclareSpec(String declareSpec) {
		this.declareSpec = declareSpec;
	}

}
