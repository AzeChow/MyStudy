/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 根据报关清单自动生成报关单时的参数 Checked by lxr
 * 
 * @author lxr 根据报关清单自动生成报关单时的参数
 */
public class MakeCusomsDeclarationParam implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 进出口日期
	 */
	private Date impExpDate;

	/**
	 * 申报日期
	 */
	private Date delcarationDate;

	/**
	 * 运输工具
	 */
	private String conveyance;

	/**
	 * 贸易方式
	 */
	private Trade trade;

	/**
	 * 运输方式
	 */
	private Transf transf;

	/**
	 * 进出口岸
	 */
	private Customs customs;

	/**
	 * 申报海关
	 */
	private Customs declarationCustoms;

	/**
	 * 征免性质
	 */
	private LevyKind levyKind;

	/**
	 * 征税比例
	 */
	private Double perTax;

	/**
	 * 许可证编号
	 */
	private String license;

	/**
	 * 起运国
	 */
	private Country countryOfLoadingOrUnloading;

	/**
	 * 境内目的地
	 */
	private District domesticDestinationOrSource;

	/**
	 * 装货港口
	 */
	private PortLin portOfLoadingOrUnloading;

	/**
	 * 成交方式
	 */
	private Transac transac;

	/**
	 * 包装种类
	 */
	private Wrap wrapType;

	/**
	 * 客户
	 */
	private ScmCoc customer;

	/**
	 * 是否是报关单
	 */
	private boolean isCustomsDeclaration = false;

	/**
	 * 是否合并清单商品
	 */
	private boolean uniteBillLists = false;

	/**
	 * 是否使用单位折算
	 */
	private boolean useConvert = true;

	/**
	 * 生成报关单使用单价方式
	 */
	private String priceType = "平均单价";

	/**
	 * 报关清单
	 */
	private ApplyToCustomsBillList applyToCustomsBillList = null;

	/**
	 * 是否新增新的清单
	 */
	private boolean isnewBillList = true;

	/**
	 * 电子帐册
	 */
	private EmsHeadH2k emsHeadH2k = null;

	/**
	 * 备注
	 */
	private String memo = null;

	/**
	 * 物料类别
	 */
	private int materielType = Integer.parseInt(MaterielType.MATERIEL);

	/**
	 * 进出口类型
	 */
	private int impExpType = 0;

	/**
	 * 进出口标志
	 */
	private boolean importFlag = true;

	/**
	 * 是否从报关单内转
	 */
	private boolean isFromCustomsDeclaretion = false;

	/**
	 * 是否追加到现有的清单中
	 */
	private boolean isOtherBilllist = true;

	/**
	 * 提运单号
	 */
	private String billOfLading;
	/**
	 * 币制
	 */
	private Curr curr = null;
	
	/**
	 * 当前需转化申请表表头ID
	 */
	private String impexprequestbillId;
	/**
	 * 获得运输工具
	 * 
	 * @return 运输工具
	 */
	public String getConveyance() {
		return conveyance;
	}

	/**
	 * 设置运输工具
	 * 
	 * @param conveyance
	 *            运输工具
	 */
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	/**
	 * 获得起运国 or 运抵国
	 * 
	 * @return 起运国 or 运抵国
	 */
	public Country getCountryOfLoadingOrUnloading() {
		return countryOfLoadingOrUnloading;
	}

	/**
	 * 设置起运国 or 运抵国
	 * 
	 * @param countryOfLoadingOrUnloading
	 *            起运国 or 运抵国
	 */
	public void setCountryOfLoadingOrUnloading(
			Country countryOfLoadingOrUnloading) {
		this.countryOfLoadingOrUnloading = countryOfLoadingOrUnloading;
	}

	/**
	 * 获得客户
	 * 
	 * @return 客户
	 */
	public ScmCoc getCustomer() {
		return customer;
	}

	/**
	 * 设置客户
	 * 
	 * @param customer
	 *            客户
	 */
	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}

	/**
	 * 获得境内目的地 or 境内货源地
	 * 
	 * @return 境内目的地 or 境内货源地
	 */
	public District getDomesticDestinationOrSource() {
		return domesticDestinationOrSource;
	}

	/**
	 * 设置境内目的地 or 境内货源地
	 * 
	 * @param domesticDestinationOrSource
	 *            境内目的地 or 境内货源地
	 */
	public void setDomesticDestinationOrSource(
			District domesticDestinationOrSource) {
		this.domesticDestinationOrSource = domesticDestinationOrSource;
	}

	/**
	 * 获得进出口日期
	 * 
	 * @return 进出口日期
	 */
	public Date getImpExpDate() {
		return impExpDate;
	}

	/**
	 * 设置进出口日期
	 * 
	 * @param impExpDate
	 *            进出口日期
	 */
	public void setImpExpDate(Date impExpDate) {
		this.impExpDate = impExpDate;
	}

	/**
	 * 获得征免性质
	 * 
	 * @return 征免性质
	 */
	public LevyKind getLevyKind() {
		return levyKind;
	}

	/**
	 * 设置征免性质
	 * 
	 * @param levyKind
	 *            征免性质
	 */
	public void setLevyKind(LevyKind levyKind) {
		this.levyKind = levyKind;
	}

	/**
	 * 获得许可证编号
	 * 
	 * @return 许可证编号
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * 设置许可证编号
	 * 
	 * @param license
	 *            许可证编号
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * 获得征税比例
	 * 
	 * @return 征税比例
	 */
	public Double getPerTax() {
		return perTax;
	}

	/**
	 * 设置征税比例
	 * 
	 * @param perTax
	 *            征税比例
	 */
	public void setPerTax(Double perTax) {
		this.perTax = perTax;
	}

	/**
	 * 获得装货港口 or 指运港口
	 * 
	 * @return 装货港口 or 指运港口
	 */
	public PortLin getPortOfLoadingOrUnloading() {
		return portOfLoadingOrUnloading;
	}

	/**
	 * 设置装货港口 or 指运港口
	 * 
	 * @param portOfLoadingOrUnloading
	 *            装货港口 or 指运港口
	 */
	public void setPortOfLoadingOrUnloading(PortLin portOfLoadingOrUnloading) {
		this.portOfLoadingOrUnloading = portOfLoadingOrUnloading;
	}

	/**
	 * 获得成交方式
	 * 
	 * @return 成交方式
	 */
	public Transac getTransac() {
		return transac;
	}

	/**
	 * 设置成交方式
	 * 
	 * @param transac
	 *            成交方式
	 */
	public void setTransac(Transac transac) {
		this.transac = transac;
	}

	/**
	 * 获得包装种类
	 * 
	 * @return 包装种类
	 */
	public Wrap getWrapType() {
		return wrapType;
	}

	/**
	 * 设置包装种类
	 * 
	 * @param wrapType
	 *            包装种类
	 */
	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}

	/**
	 * 获得运输方式
	 * 
	 * @return 运输方式
	 */
	public Transf getTransf() {
		return transf;
	}

	/**
	 * 设置运输方式
	 * 
	 * @param transf
	 *            运输方式
	 */
	public void setTransf(Transf transf) {
		this.transf = transf;
	}

	/**
	 * 获得贸易方式
	 * 
	 * @return 贸易方式
	 */
	public Trade getTrade() {
		return trade;
	}

	/**
	 * 设置贸易方式
	 * 
	 * @param trade
	 *            贸易方式
	 */
	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	/**
	 * 获得进出口岸
	 * 
	 * @return 进出口岸
	 */
	public Customs getCustoms() {
		return customs;
	}

	/**
	 * 设置进出口岸
	 * 
	 * @param customs
	 *            进出口岸
	 */
	public void setCustoms(Customs customs) {
		this.customs = customs;
	}

	/**
	 * 获得申报海关
	 * 
	 * @return 申报海关
	 */
	public Customs getDeclarationCustoms() {
		return declarationCustoms;
	}

	/**
	 * 设置申报海关
	 * 
	 * @param declarationCustoms
	 *            申报海关
	 */
	public void setDeclarationCustoms(Customs declarationCustoms) {
		this.declarationCustoms = declarationCustoms;
	}

	/**
	 * 获得申报日期
	 * 
	 * @return 申报日期
	 */
	public Date getDelcarationDate() {
		return delcarationDate;
	}

	/**
	 * 设置申报日期
	 * 
	 * @param delcarationDate
	 *            申报日期
	 */
	public void setDelcarationDate(Date delcarationDate) {
		this.delcarationDate = delcarationDate;
	}

	/**
	 * 获得报关清单
	 * 
	 * @return 报关清单
	 */
	public ApplyToCustomsBillList getApplyToCustomsBillList() {
		return applyToCustomsBillList;
	}

	/**
	 * 设置报关清单
	 * 
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	public void setApplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		this.applyToCustomsBillList = applyToCustomsBillList;
	}

	/**
	 * 获得电子帐册
	 * 
	 * @return 电子帐册
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * 设置电子帐册
	 * 
	 * @param emsHeadH2k
	 *            电子帐册
	 */
	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * 获得是报关单
	 * 
	 * @return 是报关单
	 */
	public boolean isCustomsDeclaration() {
		return isCustomsDeclaration;
	}

	/**
	 * 设置是报关单
	 * 
	 * @param isCustomsDeclaration
	 *            是报关单
	 */
	public void setCustomsDeclaration(boolean isCustomsDeclaration) {
		this.isCustomsDeclaration = isCustomsDeclaration;
	}

	/**
	 * 获得新增新的清单
	 * 
	 * @return 新增新的清单
	 */
	public boolean isIsnewBillList() {
		return isnewBillList;
	}

	/**
	 * 设置新增新的清单
	 * 
	 * @param isnewBillList
	 *            新增新的清单
	 */
	public void setIsnewBillList(boolean isnewBillList) {
		this.isnewBillList = isnewBillList;
	}

	/**
	 * 获得备注
	 * 
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获得单价方式
	 * 
	 * @return 单价方式
	 */
	public String getPriceType() {
		return priceType;
	}

	/**
	 * 设置单价方式
	 * 
	 * @param priceType
	 *            单价方式
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	/**
	 * 获得合并清单商品
	 * 
	 * @return 合并清单商品
	 */
	public boolean isUniteBillLists() {
		return uniteBillLists;
	}

	/**
	 * 设置合并清单商品
	 * 
	 * @param uniteBillLists
	 *            合并清单商品
	 */
	public void setUniteBillLists(boolean uniteBillLists) {
		this.uniteBillLists = uniteBillLists;
	}

	/**
	 * 获得单位折算
	 * 
	 * @return 单位折算
	 */
	public boolean isUseConvert() {
		return useConvert;
	}

	/**
	 * 设置单位折算
	 * 
	 * @param useConvert
	 *            单位折算
	 */
	public void setUseConvert(boolean useConvert) {
		this.useConvert = useConvert;
	}

	/**
	 * 获得 进出口类型
	 * 
	 * @return 进出口类型
	 */
	public int getImpExpType() {
		return impExpType;
	}

	/**
	 * 设置进出口类型
	 * 
	 * @param impExpType
	 *            进出口类型
	 */
	public void setImpExpType(int impExpType) {
		this.impExpType = impExpType;
	}

	/**
	 * 获得物料类别
	 * 
	 * @return 物料类别
	 */
	public int getMaterielType() {
		return materielType;
	}

	/**
	 * 设置物料类别
	 * 
	 * @param materielType
	 *            物料类别
	 */
	public void setMaterielType(int materielType) {
		this.materielType = materielType;
	}

	/**
	 * 获得进出口标志
	 * 
	 * @return 进出口标志
	 */
	public boolean isImportFlag() {
		return importFlag;
	}

	/**
	 * 设置进出口标志
	 * 
	 * @param importFlag
	 *            进出口标志
	 */
	public void setImportFlag(boolean importFlag) {
		this.importFlag = importFlag;
	}

	/**
	 * 获得是否从报关单内转
	 * 
	 * @return 是否从报关单内转
	 */
	public boolean isFromCustomsDeclaretion() {
		return isFromCustomsDeclaretion;
	}

	/**
	 * 设置是否从报关单内转
	 * 
	 * @param isFromCustomsDeclaretion
	 *            是否从报关单内转
	 */
	public void setFromCustomsDeclaretion(boolean isFromCustomsDeclaretion) {
		this.isFromCustomsDeclaretion = isFromCustomsDeclaretion;
	}

	/**
	 * 获得是否追加到现有的清单中
	 * 
	 * @return 是是否追加到现有的清单中
	 */
	public boolean isOtherBilllist() {
		return isOtherBilllist;
	}

	/**
	 * 设置是否追加到现有的清单中
	 * 
	 * @param isOtherBilllist
	 *            是否追加到现有的清单中
	 */
	public void setOtherBilllist(boolean isOtherBilllist) {
		this.isOtherBilllist = isOtherBilllist;
	}

	/**
	 * 获取提运单号
	 * 
	 * @return the billOfLading
	 */
	public String getBillOfLading() {
		return billOfLading;
	}

	/**
	 * 设置提运单号
	 * 
	 * @param billOfLading
	 *            the billOfLading to set
	 */
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}

	/**
	 * 获取币制
	 * 
	 * @return the 币制
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
	 * 当前需转化申请表表头ID
	 */
	public String getImpexprequestbillId() {
		return impexprequestbillId;
	}
	/**
	 * 当前需转化申请表表头ID
	 */
	public void setImpexprequestbillId(String impexprequestbillId) {
		this.impexprequestbillId = impexprequestbillId;
	}

}