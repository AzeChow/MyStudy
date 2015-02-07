/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物流通用代码－－客户供应商资料
 * 
 * @author Administrator
 */
public class ScmCoc extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 全称
	 */
	private String name;
	/**
	 * 简称
	 */
	private String fname; 

	/**
	 * 英文名称
	 */
	private String engName;
	/**
	 * 客户
	 */
	private Boolean isCustomer = false; 

	/**
	 * 供应商
	 */
	private Boolean isManufacturer = false; 

	/**
	 * 是否结转出口
	 */
	private Boolean isTransferFactoryOut = false; 

	/**
	 * 是否结转进口
	 */
	private Boolean isTransferFactoryIn = false; 

	/**
	 * 是否直接出口
	 */
	private Boolean isStraightOut = false; 

	/**
	 * 是否直接进口
	 */
	private Boolean isStraightIn = false; 

	/**
	 * 是否国内购买
	 */
	private Boolean isHomeBuy = false; 

	/**
	 * 是否合作伙伴资料
	 */
	private Boolean isCollaborater = false; 
	
	/**
	 * 是否内销客户
	 */
	private Boolean isLeiXiao = false;

	/**
	 * 海关注册公司
	 */
	private Brief brief;

	// 以下新加 转厂使用
	/**
	 * 国标代码
	 */
	private String flatCode; 

	/**
	 * 是否经营单位
	 */
	private Boolean isWork = false; 

	/**
	 * 联系人
	 */
	private String linkMan; 

	/**
	 * 联系电话+移动电话
	 */
	private String linkTel; 

	/**
	 * 移动电话
	 */
	private String moveTel; 

	/**
	 * 邮政编码
	 */
	private String post; 

	/**
	 * 传真
	 */
	private String fax; 

	/**
	 * 地址
	 */
	private String addre; 

	/**
	 * 银行+帐号
	 */
	private String bank; 

	/**
	 * 帐号
	 */
	private String accounts; 

	/**
	 * 备注
	 */
	private String note; 

	/**
	 * 运抵国
	 */
	private Country country; 

	/**
	 * 指运港
	 */
	private PortLin portLin; 

	/**
	 * 产销国
	 */
	private Country country2; 

	/**
	 * 出口口岸
	 */
	private Customs customs; 

	/**
	 * 运输方式
	 */
	private Transf transf; 

	/**
	 * 所属海关
	 */
	private Customs belongToCustoms; 

	/**
	 * 贸易方式
	 */
	private Trade trade; 

	/**
	 * 包装种类
	 */
	private Wrap warp; 

	/**
	 * 码头
	 */
	private PreDock predock; 

	/**
	 * 运输耗时
	 */
	private Double transportationTime;
	/**
	 * 送货距离
	 */
	private Double deliveryDistance;
	
	/** 目的地 */
	private District srcDistrict = null;
	
	/**
	 * 获取海关注册公司
	 * 
	 * @return brief 海关注册公司
	 */
	public Brief getBrief() {
		return brief;
	}

	/**
	 * 设置海关注册公司
	 * 
	 * @param brief 海关注册公司
	 */
	public void setBrief(Brief brief) {
		this.brief = brief;
	}

	/**
	 * 获取是否合作伙伴资料
	 * 
	 * @return isCollaborater 是否合作伙伴资料
	 */
	public Boolean getIsCollaborater() {
		return isCollaborater;
	}

	/**
	 * 设置是否合作伙伴资料
	 * 
	 * @param isCollaborater 是否合作伙伴资料
	 */
	public void setIsCollaborater(Boolean isCollaborater) {
		this.isCollaborater = isCollaborater;
	}

	/**
	 * 获取是否结转出口
	 * 
	 * @return isTransferFactoryOut 是否结转出口
	 */
	public Boolean getIsTransferFactoryOut() {
		return isTransferFactoryOut;
	}

	/**
	 * 设置是否结转出口
	 * 
	 * @param isTransferFactoryOut 是否结转出口
	 */
	public void setIsTransferFactoryOut(Boolean isTransferFactoryOut) {
		this.isTransferFactoryOut = isTransferFactoryOut;
	}

	/**
	 * 获取是否结转进口
	 * 
	 * @return isTransferFactoryIn 是否结转进口
	 */
	public Boolean getIsTransferFactoryIn() {
		return isTransferFactoryIn;
	}

	/**
	 * 设置是否结转进口
	 * 
	 * @param isTransferFactoryIn 是否结转进口
	 */
	public void setIsTransferFactoryIn(Boolean isTransferFactoryIn) {
		this.isTransferFactoryIn = isTransferFactoryIn;
	}

	/**
	 * 获取是否直接出口
	 * 
	 * @return isStraightOut 是否直接出口
	 */
	public Boolean getIsStraightOut() {
		return isStraightOut;
	}

	/**
	 * 设置是否直接出口
	 * 
	 * @param isStraightOut 是否直接出口
	 */
	public void setIsStraightOut(Boolean isStraightOut) {
		this.isStraightOut = isStraightOut;
	}

	/**
	 * 获取是否直接进口
	 * 
	 * @return isStraightIn 是否直接进口
	 */
	public Boolean getIsStraightIn() {
		return isStraightIn;
	}

	/**
	 * 设置是否直接进口
	 * 
	 * @param isStraightIn 是否直接进口
	 */
	public void setIsStraightIn(Boolean isStraightIn) {
		this.isStraightIn = isStraightIn;
	}

	/**
	 * 获取是否国内购买
	 * 
	 * @return isHomeBuy 是否国内购买
	 */
	public Boolean getIsHomeBuy() {
		return isHomeBuy;
	}

	/**
	 * 设置是否国内购买
	 * 
	 * @param isHomeBuy 是否国内购买
	 */
	public void setIsHomeBuy(Boolean isHomeBuy) {
		this.isHomeBuy = isHomeBuy;
	}

	/**
	 * 获取客户
	 * 
	 * @return isCustomer 客户
	 */
	public Boolean getIsCustomer() {
		return isCustomer;
	}

	/**
	 * 设置客户
	 * 
	 * @param isCustomer 客户
	 */
	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	/**
	 * 获取供应商
	 * 
	 * @return isManufacturer 供应商
	 */
	public Boolean getIsManufacturer() {
		return isManufacturer;
	}

	/**
	 * 设置供应商
	 * 
	 * @param isManufacturer 供应商
	 */
	public void setIsManufacturer(Boolean isManufacturer) {
		this.isManufacturer = isManufacturer;
	}

	/**
	 * 获取编码
	 * 
	 * @return cocCode 编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置编码
	 * 
	 * @param cocCode 编码
	 */
	public void setCode(String cocCode) {
		this.code = cocCode;
	}

	/**
	 * 获取名称
	 * 
	 * @return cocName 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param cocName 名称
	 */
	public void setName(String cocName) {
		this.name = cocName;
	}

	/**
	 * 获取全称
	 * 
	 * @return fname 全称
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * 设置全称
	 * 
	 * @param fname 全称
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * 获取帐号
	 * 
	 * @return accounts 帐号
	 */
	public String getAccounts() {
		return accounts;
	}

	/**
	 * 设置帐号
	 * 
	 * @param accounts 帐号
	 */
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	/**
	 * 获取地址
	 * 
	 * @return addre 地址
	 */
	public String getAddre() {
		return addre;
	}

	/**
	 * 设置地址
	 * 
	 * @param addre 地址
	 */
	public void setAddre(String addre) {
		this.addre = addre;
	}

	/**
	 * 获取银行+帐号
	 * 
	 * @return bank 银行+帐号
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * 设置银行+帐号
	 * 
	 * @param bank 银行+帐号
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 获取运抵国
	 * 
	 * @return country 运抵国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置运抵国
	 * 
	 * @param country 运抵国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 获取产销国
	 * 
	 * @return country2 产销国
	 */
	public Country getCountry2() {
		return country2;
	}

	/**
	 * 设置产销国
	 * 
	 * @param country2 产销国
	 */
	public void setCountry2(Country country2) {
		this.country2 = country2;
	}

	/**
	 * 获取出口口岸
	 * 
	 * @return customs 出口口岸
	 */
	public Customs getCustoms() {
		return customs;
	}

	/**
	 * 设置出口口岸
	 * 
	 * @param customs 出口口岸
	 */
	public void setCustoms(Customs customs) {
		this.customs = customs;
	}

	/**
	 * 获取传真
	 * 
	 * @return fax 传真
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 设置传真
	 * 
	 * @param fax 传真
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 获取国标代码
	 * 
	 * @return flatCode 国标代码
	 */
	public String getFlatCode() {
		return flatCode;
	}

	/**
	 * 设置国标代码
	 * 
	 * @param flatCode 国标代码
	 */
	public void setFlatCode(String flatCode) {
		this.flatCode = flatCode;
	}

	/**
	 * 获取是否经营单位
	 * 
	 * @return isWork 是否经营单位
	 */
	public Boolean getIsWork() {
		return isWork;
	}

	/**
	 * 设置是否经营单位
	 * 
	 * @param isWork 是否经营单位
	 */
	public void setIsWork(Boolean isWork) {
		this.isWork = isWork;
	}

	/**
	 * 获取联系人
	 * 
	 * @return linkMan 联系人
	 */
	public String getLinkMan() {
		return linkMan;
	}

	/**
	 * 设置联系人
	 * 
	 * @param linkMan 联系人
	 */
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	/**
	 * 获取联系电话+移动电话
	 * 
	 * @return linkTel 联系电话+移动电话
	 */
	public String getLinkTel() {
		return linkTel;
	}

	/**
	 * 设置联系电话+移动电话
	 * 
	 * @param linkTel 联系电话+移动电话
	 */
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	/**
	 * 获取移动电话
	 * 
	 * @return moveTel 移动电话
	 */
	public String getMoveTel() {
		return moveTel;
	}

	/**
	 * 设置移动电话
	 * 
	 * @param moveTel 移动电话
	 */
	public void setMoveTel(String moveTel) {
		this.moveTel = moveTel;
	}

	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注
	 * 
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取指运港
	 * 
	 * @return portLin 指运港
	 */
	public PortLin getPortLin() {
		return portLin;
	}

	/**
	 * 设置指运港
	 * 
	 * @param portLin 指运港
	 */
	public void setPortLin(PortLin portLin) {
		this.portLin = portLin;
	}

	/**
	 * 获取邮政编码
	 * 
	 * @return post 邮政编码
	 */
	public String getPost() {
		return post;
	}

	/**
	 * 设置邮政编码
	 * 
	 * @param post 邮政编码
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * 获取运输方式
	 * 
	 * @return transf 运输方式
	 */
	public Transf getTransf() {
		return transf;
	}

	/**
	 * 设置运输方式
	 * 
	 * @param transf 运输方式
	 */
	public void setTransf(Transf transf) {
		this.transf = transf;
	}

	/**
	 * 获取所属海关
	 * 
	 * @return belongToCustoms 所属海关
	 */
	public Customs getBelongToCustoms() {
		return belongToCustoms;
	}

	/**
	 * 设置所属海关
	 * 
	 * @param belongToCustoms 所属海关
	 */
	public void setBelongToCustoms(Customs belongToCustoms) {
		this.belongToCustoms = belongToCustoms;
	}

	/**
	 * 获取码头
	 * 
	 * @return predock 码头
	 */
	public PreDock getPredock() {
		return predock;
	}

	/**
	 * 设置码头
	 * 
	 * @param predock 码头
	 */
	public void setPredock(PreDock predock) {
		this.predock = predock;
	}

	/**
	 * 获取贸易方式
	 * 
	 * @return trade 贸易方式
	 */
	public Trade getTrade() {
		return trade;
	}

	/**
	 * 设置贸易方式
	 * 
	 * @param trade 贸易方式
	 */
	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	/**
	 * 获取包装种类
	 * 
	 * @return warp 包装种类
	 */
	public Wrap getWarp() {
		return warp;
	}

	/**
	 * 设置包装种类
	 * 
	 * @param warp 包装种类
	 */
	public void setWarp(Wrap warp) {
		this.warp = warp;
	}
	/**
	 * 获取运输时间
	 * @return
	 */
	public Double getTransportationTime()
	{
		return transportationTime;
	}
    /**
     * 设置运输时间
     * @param transportationTime 设置运输时间
     */
	public void setTransportationTime(Double transportationTime)
	{
		this.transportationTime = transportationTime;
	}
	/**
     * 获取送货距离
     * @param transportationTime
     */
	public Double getDeliveryDistance()
	{
		return deliveryDistance;
	}
	/**
     * 设置送货距离
     * @param deliveryDistance 送货距离
     */
	public void setDeliveryDistance(Double deliveryDistance)
	{
		this.deliveryDistance = deliveryDistance;
	}

	/**
	 * @return the srcDistrict
	 */
	public District getSrcDistrict() {
		return srcDistrict;
	}

	/**
	 * @param srcDistrict the srcDistrict to set
	 */
	public void setSrcDistrict(District srcDistrict) {
		this.srcDistrict = srcDistrict;
	}

	public Boolean getIsLeiXiao() {
		if(isLeiXiao==null){
			return false;
		}
		return isLeiXiao;
	}

	public void setIsLeiXiao(Boolean isLeiXiao) {
		this.isLeiXiao = isLeiXiao;
	}
	
	

}