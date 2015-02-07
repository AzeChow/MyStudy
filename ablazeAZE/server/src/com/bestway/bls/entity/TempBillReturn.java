package com.bestway.bls.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 查询反馈表
 * @author hw
 *
 */
public class TempBillReturn implements Serializable{
	/**
	 * 服务Handle
	 */
	private String serviceHandle = null;
	/**
	 * 服务状态
	 */
	private String serviceStatus = null;
	/**
	 * 反馈描述
	 */
	private String description = null;
	/**
	 * 企业编码
	 */
	private String TradeCo=null;
	
	/**
	 * 电子帐册号
	 */
	private String emsNo = null;
	/**
	 * 仓库编码
	 */
	private String wareHouseCode = null;
	/**
	 * 关区代码
	 */
	private String customsCode = null;
	/**
	 * 仓单号码 
	 */
	private String billNo=null;
	/**
	 * 进出口标志
	 */
	private String iOFlag=null;
	/**
	 * 进出口口岸
	 */
	private String iEPort=null;
	/**
	 * 车次号码
	 */
	private String deliveryNo=null;
	/**
	 * 仓单类型
	 */
	private String billType=null;
	/**
	 * 仓单表体数量
	 */
	private int itemsCount;
	/**
	 * 仓单通过审核时间
	 */
	private String billOperTime=null;
	/**
	 * 是否收到货到通知
	 */
	private int isDeliveryArrived;
	/**
	 * 货到具体信息
	 */
	private TempDeliveryEntity deliveryInfo;
	/**
	 * 仓单是否进行库存核扣1为完成,0为未完成
	 */
	private int isCollateMftStockFinishend;
	/**
	 * 出入仓核销是否完成
	 */
	private int isCollateMftInOutFinishend;
	/**
	 * 仓单报关单核销是否完成
	 */
	private int isCollateMftEntFinished;
	/**
	 * 查询仓单表体项
	 */
	private List queryBillLists=new ArrayList();
	
	/**
	 * 获取查询仓单表体项
	 * @return
	 */
	public List getQueryBillLists() {
		return queryBillLists;
	}
	
	/**
	 * 设置查询仓单表体项
	 * @param queryBillLists
	 */
	public void setQueryBillLists(List queryBillLists) {
		this.queryBillLists = queryBillLists;
	}
	/**
	 * 获取服务Handle
	 * @return
	 */
	public String getServiceHandle() {
		return serviceHandle;
	}
	/**
	 * 设置服务Handle
	 * @param serviceHandle
	 */
	public void setServiceHandle(String serviceHandle) {
		this.serviceHandle = serviceHandle;
	}
	/**
	 * 获取服务状态
	 * @return
	 */
	public String getServiceStatus() {
		return serviceStatus;
	}
	/**
	 * 设置服务状态
	 * @param serviceStatus
	 */
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	/**
	 * 获取反馈描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置反馈描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取电子账册号
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * 设置电子账册号
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * 获取仓库编码
	 * @return
	 */
	public String getWareHouseCode() {
		return wareHouseCode;
	}
	/**
	 * 设置仓库编码
	 * @param wareHouseCode
	 */
	public void setWareHouseCode(String wareHouseCode) {
		this.wareHouseCode = wareHouseCode;
	}
	/**
	 * 获取关区代码
	 * @return
	 */
	public String getCustomsCode() {
		return customsCode;
	}
	/**
	 * 设置关区代码
	 * @param customsCode
	 */
	public void setCustomsCode(String customsCode) {
		this.customsCode = customsCode;
	}
	/**
	 * 获取仓单号码
	 * @return
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * 设置仓单号码
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * 获取进出口标志
	 * @return
	 */
	public String getIOFlag() {
		return iOFlag;
	}
	/**
	 * 设置进出口标志
	 * @param flag
	 */
	public void setIOFlag(String flag) {
		iOFlag = flag;
	}
	/**
	 * 获取进出口口岸
	 * @return
	 */
	public String getIEPort() {
		return iEPort;
	}
	/**
	 * 设置进出口口岸
	 * @param port
	 */
	public void setIEPort(String port) {
		iEPort = port;
	}
	/**
	 *  获取车次号码
	 * @return
	 */
	public String getDeliveryNo() {
		return deliveryNo;
	}
	/**
	 * 设置车次号码
	 * @param deliveryNo
	 */
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	/**
	 * 获取仓单类型
	 * @return
	 */
	public String getBillType() {
		return billType;
	}
	/**
	 * 设置仓单类型
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/**
	 * 获取仓单表体数量
	 * @return
	 */
	public int getItemsCount() {
		return itemsCount;
	}
	/**
	 * 设置仓单表体数量
	 * @param itemsCount
	 */
	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}
	/**
	 * 获取仓单通过审核时间
	 * @return
	 */
	public String getBillOperTime() {
		return billOperTime;
	}
	/**
	 * 设置仓单通过审核时间
	 * @param billOperTime
	 */
	public void setBillOperTime(String billOperTime) {
		this.billOperTime = billOperTime;
	}
	/**
	 * 获取是否收到货到通知标志位
	 * @return
	 */
	public int getIsDeliveryArrived() {
		return isDeliveryArrived;
	}
	/**
	 * 设置是否收到货到通知标志位
	 * @param isDeliveryArrived
	 */
	public void setIsDeliveryArrived(int isDeliveryArrived) {
		this.isDeliveryArrived = isDeliveryArrived;
	}
	/**
	 * 获取货到具体信息
	 * @return
	 */
	public TempDeliveryEntity getDeliveryInfo() {
		return deliveryInfo;
	}
	/**
	 * 设置货到具体信息
	 * @param deliveryInfo
	 */
	public void setDeliveryInfo(TempDeliveryEntity deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	
	/**
	 * 获取出入仓核销是否完成标志位
	 * @return
	 */
	public int getIsCollateMftInOutFinishend() {
		return isCollateMftInOutFinishend;
	}
	/**
	 * 设置出入仓核销是否完成标志位
	 * @param isCollateMftInOutFinishend
	 */
	public void setIsCollateMftInOutFinishend(int isCollateMftInOutFinishend) {
		this.isCollateMftInOutFinishend = isCollateMftInOutFinishend;
	}
	/**
	 * 获取仓单报关单核销是否完成标志位
	 * @return
	 */
	public int getIsCollateMftEntFinished() {
		return isCollateMftEntFinished;
	}
	/**
	 * 设置仓单报关单核销是否完成标志位
	 * @param isCollateMftEntFinished
	 */
	public void setIsCollateMftEntFinished(int isCollateMftEntFinished) {
		this.isCollateMftEntFinished = isCollateMftEntFinished;
	}

	/**
	 * 获取企业编码
	 * @return
	 */
	public String getTradeCo() {
		return TradeCo;
	}
	/**
	 * 设置企业编码
	 * @param tradeCo
	 */
	public void setTradeCo(String tradeCo) {
		TradeCo = tradeCo;
	}

	public int getIsCollateMftStockFinishend() {
		return isCollateMftStockFinishend;
	}

	public void setIsCollateMftStockFinishend(int isCollateMftStockFinishend) {
		this.isCollateMftStockFinishend = isCollateMftStockFinishend;
	}
}