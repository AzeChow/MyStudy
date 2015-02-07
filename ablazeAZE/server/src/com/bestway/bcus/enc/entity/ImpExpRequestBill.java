package com.bestway.bcus.enc.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 申请单物料
 * 
 * check by hcl 2011-01-07
 */
public class ImpExpRequestBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 单据类型
	 */
	private Integer billType = null;
	/**
	 * 单据号
	 */
	private String billNo = null;
	/**
	 * 生效日期
	 */
	private Date beginAvailability = null;
	/**
	 * 是否有效
	 */
	private Boolean isAvailability = false;
	/**
	 * 已全转报关清单或全转已转报关单
	 */
	private Boolean isCustomsBill = false;
	/**
	 * 项目总数
	 */
	private Integer itemCount = null;
	/**
	 * 已转报关清单数
	 */
	private Integer toCustomCount = null;
	/**
	 * 已转清单号码
	 */
	private String allBillNo = null;
	/**
	 * 运输工具
	 */
	private String conveyance = null;
	
	//2011.08.29 lxr加入transfMode
	//transferMode以后不再使用,暂时还不能删除因为要把旧的transferMode资料转到transfMode
	//过一段时间屏蔽
	private Transf transferMode = null;
	/**
	 * 运输方式
	 */
	private Transf transfMode = null;
	
	/**2013.01.17 lyh加入transac,联胜使用
	 * 成交方式
	 */
	private Transac transac;
	/*2013.01.17 lyh加入harbour,联胜使用
	 * 港口
	 */
	private String harbour;
	/**
	 * 录入时间
	 */
	private Date imputDate = null;
	/**
	 * 料件成品标识
	 */
	private Integer materielProductFlag = null;
	/**
	 * 仓库对象类
	 */
	private WareSet wareSet = null;
	/**
	 * 客户供应商
	 */
	private ScmCoc scmCoc = null;
	/**
	 * 集装箱号码
	 */
	private String containerCode = null;
	/**
	 * 车牌号码
	 */
	private String catNo = null; // 车牌号码 康舒使用

	/**
	 * 进出口岸
	 */
	private Customs iePort = null;
	/**
	 * 发票号 ，一般插件用到
	 */
	private String invoiceNo = null;
	/**
	 * 合同号 ，一般插件用到
	 */
	private String contractNo = null;
	/**
	 * 包装种类 ，一般插件用到
	 */
	private Wrap wrap = null;

	/**
	 * 备注
	 */
	private String memos = null;

	/**
	 * 获取备注
	 */
	public String getMemos() {
		return memos;
	}

	/**
	 * 设置备注
	 */
	public void setMemos(String memos) {
		this.memos = memos;
	}

	public Transf getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(Transf transferMode) {
		this.transferMode = transferMode;
	}

	/**
	 * 获取运输方式
	 */
	public Transf getTransfMode() {
		return transfMode;
	}

	/**
	 * 设置运输方式
	 */
	public void setTransfMode(Transf transfMode) {
		this.transfMode = transfMode;
	}
	
	/**
	 * 获取成交方式--联胜使用
	 */
	public Transac getTransac() {
		return transac;
	}
	/**
	 * 设置成交方式--联胜使用
	 * @param transac
	 */
	public void setTransac(Transac transac) {
		this.transac = transac;
	}
	/**
	 * 获取港口--联胜使用
	 */
	public String getHarbour() {
		return harbour;
	}
	/**
	 * 设置港口--联胜使用
	 * @param harbour
	 */
	public void setHarbour(String harbour) {
		this.harbour = harbour;
	}

	/**
	 * 获得生效日期
	 * 
	 * @return 生效日期
	 */
	public Date getBeginAvailability() {
		if (beginAvailability == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(beginAvailability));
	}

	/**
	 * 设置生效日期
	 * 
	 * @param beginAvailability
	 *            生效日期
	 */
	public void setBeginAvailability(Date beginAvailability) {
		this.beginAvailability = beginAvailability;
	}

	/**
	 * 获得单据号
	 * 
	 * @return 单据号
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 设置项目总数
	 * 
	 * @param itemCount
	 *            项目总数
	 */
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * 设置单据号
	 * 
	 * @param billNo
	 *            单据号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 获得单据类型(料件进口：0 成品出口：4)
	 * 
	 * @return 单据类型(料件进口：0 成品出口：4)
	 */
	public Integer getBillType() {
		return billType;
	}

	/**
	 * 设置单据类型(料件进口：0 成品出口：4)
	 * 
	 * @param billType
	 *            单据类型(料件进口：0 成品出口：4)
	 */
	public void setBillType(Integer billType) {
		this.billType = billType;
	}

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
	 * 获得录入时间
	 * 
	 * @return 录入时间
	 */
	public Date getImputDate() {
		if (imputDate == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(imputDate));
	}

	/**
	 * 设置录入时间
	 * 
	 * @param imputDate
	 *            录入时间
	 */
	public void setImputDate(Date imputDate) {
		this.imputDate = imputDate;
	}

	/**
	 * 判断是否有效
	 * 
	 * @return 是否有效
	 */
	public Boolean getIsAvailability() {
		return isAvailability;
	}

	/**
	 * 设置是否有效标志
	 * 
	 * @param isAvailability
	 *            是否有效
	 */
	public void setIsAvailability(Boolean isAvailability) {
		this.isAvailability = isAvailability;
	}

	/**
	 * 判断是否已全转报关清单或全转已转报关单（当全部转完为True）
	 * 
	 * @return 是否已全转报关清单或全转已转报关单（当全部转完为True）
	 */
	public Boolean getIsCustomsBill() {
		return isCustomsBill;
	}

	/**
	 * 设置是否已全转报关清单或全转已转报关单标志（当全部转完为True）
	 * 
	 * @param isCustomsBill
	 *            是否已全转报关清单或全转已转报关单（当全部转完为True）
	 */
	public void setIsCustomsBill(Boolean isCustomsBill) {
		this.isCustomsBill = isCustomsBill;
	}

	/**
	 * 获得项目总数
	 * 
	 * @return 项目总数
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	/**
	 * 获得客户供应商
	 * 
	 * @return 客户供应商
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * 设置客户供应商
	 * 
	 * @param scmCoc
	 *            客户供应商
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	/**
	 * 获得仓库对象类
	 * 
	 * @return 仓库对象类
	 */
	public WareSet getWareSet() {
		return wareSet;
	}

	/**
	 * 设置仓库对象类
	 * 
	 * @param wareSet
	 *            仓库对象类
	 */
	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}

	/**
	 * 获得料件成品标识(料件：成品：)
	 * 
	 * @return 料件成品标识(料件：成品：)
	 */
	public Integer getMaterielProductFlag() {
		return materielProductFlag;
	}

	/**
	 * 设置料件成品标识(料件：成品：)
	 * 
	 * @param materielProductFlag
	 *            料件成品标识(料件：成品：)
	 */
	public void setMaterielProductFlag(Integer materielProductFlag) {
		this.materielProductFlag = materielProductFlag;
	}

	/**
	 * 获得已转报关清单数
	 * 
	 * @return 已转报关清单数
	 */
	public Integer getToCustomCount() {
		return toCustomCount;
	}

	/**
	 * 设置已转报关清单数
	 * 
	 * @param toCustomCount
	 *            已转报关清单数
	 */
	public void setToCustomCount(Integer toCustomCount) {
		this.toCustomCount = toCustomCount;
	}

	/**
	 * 获得集装箱号码
	 * 
	 * @return 集装箱号码
	 */
	public String getContainerCode() {
		return containerCode;
	}

	/**
	 * 设置集装箱号码
	 * 
	 * @param containerCode
	 *            集装箱号码
	 */
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	/**
	 * 获得已转清单号码
	 * 
	 * @return 已转清单号码
	 */
	public String getAllBillNo() {
		return allBillNo;
	}

	/**
	 * 设置已转清单号码
	 * 
	 * @param allBillNo
	 *            已转清单号码
	 */
	public void setAllBillNo(String allBillNo) {
		this.allBillNo = allBillNo;
	}

	/**
	 * 获取车票号码
	 * 
	 */
	public String getCatNo() {
		return catNo;
	}

	/**
	 * 设置车票号码
	 * 
	 */
	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}

	/**
	 * 获取进出口岸
	 * 
	 */
	public Customs getIePort() {
		return iePort;
	}

	/**
	 * 设置进出口岸
	 * 
	 */
	public void setIePort(Customs iePort) {
		this.iePort = iePort;
	}

	/**
	 * 获取发票号
	 * 
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * 设置发票号
	 * 
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * 获取合同号
	 * 
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * 设置合同号
	 * 
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 获取包装种类
	 * 
	 */
	public Wrap getWrap() {
		return wrap;
	}

	/**
	 * 设置包装种类
	 * 
	 */
	public void setWrap(Wrap wrap) {
		this.wrap = wrap;
	}
}