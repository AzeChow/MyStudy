/*
 * Created on 2004-9-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 半成品进出仓折料
 */
public class SemiProductInfo implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/** 上期结存 */
	public static String TOTAL_FRONT_AMOUNT_STRING = "上期结存";

	/**
	 * 进出仓日期
	 */
	private Date date;

	/**
	 * 单据类型
	 */
	private BillType billType;

	/**
	 * 入仓凭证号、单据号
	 */

	private String impBillNo;

	/**
	 * 入仓工厂数量
	 */
	private Double impPtAmount;

	/**
	 * 入仓海关数量
	 */
	private Double impHsAmount;

	/**
	 * 出仓凭证号、单据号
	 */

	private String expBillNo;

	/**
	 * 出仓工厂数量
	 */
	private Double expPtAmount;

	/**
	 * 出仓海关数量
	 */
	private Double expHsAmount;

	/**
	 * 结存工厂数量
	 */

	private Double rsPtAmount;

	/**
	 * 结存海关数量
	 */
	private Double rsHsAmount;

	/**
	 * 制单号
	 */

	private String productNo;
	/**
	 * 对应报关单号
	 */
	private String customNo;

	/**
	 * 收送货名称(客户，供应商)
	 */
	private ScmCoc scmCoc;

	/**
	 * 商品序号
	 */
	private Integer seqNum;

	/**
	 * Bom编号，工厂料号
	 */
	private String ptPart;

	/**
	 * 工厂商品名称
	 */
	private String ptName;

	/**
	 * 工厂规格型号
	 */
	private String ptSpec;

	/**
	 * 工厂单位
	 */
	private CalUnit ptUnit;

	/**
	 * 海关单位
	 */
	private Unit hsUnit;

	/**
	 * 仓库
	 */
	private WareSet wareSet;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 报关名称
	 */
	private String hsName;
	/**
	 * 报关规格
	 */
	private String hsSpec;
	/**
	 * 单据头ID
	 */
	private String billMasterid;
	/**
	 * 单据头ID
	 */
	private TempBomBillDetail tempBomBill;
	
	
	/**
	 * 成品-料号
	 */
	private String 	ptPartFinished;
	/**
	 * 成品-名称
	 */
	private String ptNameFinished;
	/**
	 * 成品-规格型号
	 */
	private String ptSpecFinished;
	/**
	 * 成品-单位
	 */
	private CalUnit ptUnitFinished;

	/**
	 * 成品-数量
	 */
	private Double ptAmountFinished;
	/**
	 * 成品-折算报关单位比率
	 */
	private Double unitConvertFinished = null;
	/**
	 * 物料类型
	 */
	private String materielType = null;


	/**
	 * 空的构造函数
	 * 
	 */
	public SemiProductInfo() {

	}

	/**
	 * 初始化进出仓日期,单据类型,出仓凭证号、单据号,出仓工厂数量,出仓海关数量,入仓单据号,入仓工厂数量,入仓海关数量,结存工厂数量,结存海关数量,
	 * 等等,根据不同的工厂单据 取得相对应的值
	 * 
	 * @param tempBomBill
	 */
	public SemiProductInfo(TempBomBillDetail tempBomBill, boolean isWaiFa) {
		this.tempBomBill=tempBomBill;
		this.billMasterid = tempBomBill.getBill().getBillMaster() == null ? null
				: tempBomBill.getBill().getBillMaster().getId();
		this.date = tempBomBill.getBill().getBillMaster().getValidDate();
		this.billType = tempBomBill.getBill().getBillMaster().getBillType();
		if (billType.getWareType() == null) {
			return;
		}
		boolean isIn = false;
		if (isWaiFa) {
			if(billType.getCode().equals("1014")
					|| billType.getCode().equals("1110")
					|| billType.getCode().equals("1112")
					|| billType.getCode().equals("1113")
					|| billType.getCode().equals("4103")
					|| billType.getCode().equals("4104")
					|| billType.getCode().equals("4106")
					|| billType.getCode().equals("2113")
					|| billType.getCode().equals("2114"))
				isIn=true;
		} else {
			if (billType.getCode().equals("1002")
					|| billType.getCode().equals("1101")
					|| billType.getCode().equals("2103")
					|| billType.getCode().equals("4002")
					|| billType.getCode().equals("4003")
					|| billType.getCode().equals("4004")
					|| billType.getCode().equals("4006"))
				isIn = true;
		}

		if (isIn) { // 进仓
			this.impBillNo = tempBomBill.getBill().getBillMaster().getBillNo();
			this.impPtAmount = tempBomBill.getBill().getPtAmount() == null ? 0.0
					: tempBomBill.getBill().getPtAmount();
			this.impHsAmount = tempBomBill.getBill().getHsAmount() == null ? 0.0
					: tempBomBill.getBill().getHsAmount();
			if (billType.getCode().equals("1001") || // 1001 料件起初单
					billType.getCode().equals("2001") || // 2001 成品起初单
					billType.getCode().equals("5002") || // 5002 残次品起初单
					billType.getCode().equals("6003")) { // 6003 边角料期初单
				this.impBillNo = "期初单";
			}
		} else {
			this.expBillNo = tempBomBill.getBill().getBillMaster().getBillNo();
			this.expPtAmount = tempBomBill.getBill().getPtAmount() == null ? 0.0
					: tempBomBill.getBill().getPtAmount();
			this.expHsAmount = tempBomBill.getBill().getHsAmount() == null ? 0.0
					: tempBomBill.getBill().getHsAmount();
		}
		this.rsPtAmount = Double.valueOf(0);
		this.rsHsAmount = Double.valueOf(0);
		String temp = "";
		String customNo = tempBomBill.getBill().getCustomNo() == null ? "" : tempBomBill
				.getBill().getCustomNo().trim();
		String productNo = tempBomBill.getBill().getProductNo() == null ? "" : tempBomBill
				.getBill().getProductNo().trim();
		// if (customNo.equals("") && !productNo.equals("")) {
		// temp += productNo;
		// } else if (!customNo.equals("") && productNo.equals("")) {
		// temp += customNo;
		// } else if (!customNo.equals("") && !productNo.equals("")) {
		// temp += customNo + "/" + productNo;
		// }
		// this.productNo = temp;
		this.customNo = customNo;
		this.productNo = productNo;
		this.scmCoc = tempBomBill.getBill().getBillMaster().getScmCoc();
		this.seqNum = tempBomBill.getBill().getSeqNum();
		this.ptPart = tempBomBill.getBill().getPtPart();
		this.ptName = tempBomBill.getBill().getPtName();
		this.ptSpec = tempBomBill.getBill().getPtSpec();
		this.ptUnit = tempBomBill.getBill().getPtUnit() == null ? new CalUnit()
				: tempBomBill.getBill().getPtUnit();
		this.hsUnit = tempBomBill.getBill().getHsUnit() == null ? new Unit() : tempBomBill
				.getBill().getHsUnit();
		this.wareSet = tempBomBill.getBill().getWareSet() == null ? new WareSet()
				: tempBomBill.getBill().getWareSet();
		this.note = tempBomBill.getBill().getNote();
		this.hsName = tempBomBill.getBill().getHsName();
		this.hsSpec = tempBomBill.getBill().getHsSpec();
		
		//下面为成品信息
		this.materielType=tempBomBill.getMaterielType();
		this.ptPartFinished=tempBomBill.getPtPart();//成品料号
		this.ptNameFinished=tempBomBill.getPtName();//成品名称
		this.ptSpecFinished=tempBomBill.getPtSpec();//成品规格
		this.ptUnitFinished=tempBomBill.getPtUnit();//成品单位
		this.ptAmountFinished=tempBomBill.getPtAmount();//成品折料前数量
		this.unitConvertFinished=tempBomBill.getUnitConvert();//单位比例
		if(tempBomBill.getPtPart()!=null)
		System.out.println("ptPartFinished="+tempBomBill.getPtPart());
		System.out.println("ptNameFinished="+tempBomBill.getPtName());
		System.out.println("ptSpecFinished="+tempBomBill.getPtSpec());
		if(tempBomBill.getPtUnit()!=null)
		System.out.println("ptUnitFinished="+tempBomBill.getPtUnit().getName());
		System.out.println("ptAmountFinished="+tempBomBill.getPtAmount());

		System.out.println("unitConvertFinished="+tempBomBill.getUnitConvert());

		
	}

	/**
	 * 取得单据类别
	 * 
	 * @return billType 单据类别
	 */
	public BillType getBillType() {
		return billType;
	}

	/**
	 * 取得进出仓日期
	 * 
	 * @return date 进出仓日期.
	 */
	public Date getDate() {
		if (date == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(date));
	}

	/**
	 * 取得入仓凭证号,单据号
	 * 
	 * @return expBillNo 入仓凭证号,单据号.
	 */
	public String getExpBillNo() {
		return expBillNo;
	}

	/**
	 * 取得出仓海关数量
	 * 
	 * @return expHsAmount 出仓海关数量.
	 */
	public Double getExpHsAmount() {
		return expHsAmount;
	}

	/**
	 * 取得出仓工厂数量
	 * 
	 * @return expPtAmount 出仓工厂数量.
	 */
	public Double getExpPtAmount() {
		return expPtAmount;
	}

	/**
	 * 取得海关单位
	 * 
	 * @return hsUnit 报关单位.
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}

	/**
	 * 取得入仓凭证号,单据号
	 * 
	 * @return impBillNo 入仓凭证号,单据号.
	 */
	public String getImpBillNo() {
		return impBillNo;
	}

	/**
	 * 取得入仓海关数量
	 * 
	 * @return impHsAmount 入仓海关数量.
	 */
	public Double getImpHsAmount() {
		return impHsAmount;
	}

	/**
	 * 取得入仓工厂数量
	 * 
	 * @return impPtAmount 入仓工厂数量.
	 */
	public Double getImpPtAmount() {
		return impPtAmount;
	}

	/**
	 * 取得备注内容
	 * 
	 * @return note 备注.
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 取得制单号
	 * 
	 * @return productNo 制单号.
	 */
	public String getProductNo() {
		return productNo;
	}

	/**
	 * 设置制单号
	 * 
	 * @return productNo 制单号.
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/**
	 * 取得工厂商品名称
	 * 
	 * @return ptName 工厂商品名称.
	 */
	public String getPtName() {
		return ptName;
	}

	/**
	 * 取得工厂料号,bom编号
	 * 
	 * @return ptPart 工厂料号,bom编号.
	 */
	public String getPtPart() {
		return ptPart;
	}

	/**
	 * 取得工厂商品规格
	 * 
	 * @return ptSpec 工厂商品规格.
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	/**
	 * 取得工厂单位
	 * 
	 * @return ptUnit 工厂单位.
	 */
	public CalUnit getPtUnit() {
		return ptUnit;
	}

	/**
	 * 取得结存海关数量
	 * 
	 * @return rsHsAmount 结存海关数量.
	 */
	public Double getRsHsAmount() {
		return rsHsAmount;
	}

	/**
	 * 取得结存工厂数量
	 * 
	 * @return rsPtAmount 结存工厂数量.
	 */
	public Double getRsPtAmount() {
		return rsPtAmount;
	}

	/**
	 * 取得送货客户名称（客户,供应商）
	 * 
	 * @return scmCoc 客户送货名称（客户,供应商）.
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	/**
	 * 取得商品序号
	 * 
	 * @return seqNum 商品序号.
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 取得仓库
	 * 
	 * @return wareSet 仓库.
	 */
	public WareSet getWareSet() {
		return wareSet;
	}

	/**
	 * 设置结存海关数量
	 * 
	 * @param rsHsAmount
	 *            结存海关数量
	 */
	public void setRsHsAmount(Double rsHsAmount) {
		this.rsHsAmount = rsHsAmount;
	}

	/**
	 * 设置结存工厂数量
	 * 
	 * @param rsPtAmount
	 *            结存工厂数量
	 */
	public void setRsPtAmount(Double rsPtAmount) {
		this.rsPtAmount = rsPtAmount;
	}

	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public void setImpBillNo(String impBillNo) {
		this.impBillNo = impBillNo;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public void setExpHsAmount(Double expHsAmount) {
		this.expHsAmount = expHsAmount;
	}

	public void setExpPtAmount(Double expPtAmount) {
		this.expPtAmount = expPtAmount;
	}

	public void setImpHsAmount(Double impHsAmount) {
		this.impHsAmount = impHsAmount;
	}

	public void setImpPtAmount(Double impPtAmount) {
		this.impPtAmount = impPtAmount;
	}

	public void setExpBillNo(String expBillNo) {
		this.expBillNo = expBillNo;
	}

	public String getCustomNo() {
		return this.customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	public String getHsName() {
		return hsName;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	/**
	 * @return the billMasterid
	 */
	public String getBillMasterid() {
		return billMasterid;
	}

	/**
	 * @param billMasterid
	 *            the billMasterid to set
	 */
	public void setBillMasterid(String billMasterid) {
		this.billMasterid = billMasterid;
	}

	public TempBomBillDetail getTempBomBill() {
		return tempBomBill;
	}

	public void setTempBomBill(TempBomBillDetail tempBomBill) {
		this.tempBomBill = tempBomBill;
	}

	public String getPtPartFinished() {
		return ptPartFinished;
	}

	public void setPtPartFinished(String ptPartFinished) {
		this.ptPartFinished = ptPartFinished;
	}

	public String getPtNameFinished() {
		return ptNameFinished;
	}

	public void setPtNameFinished(String ptNameFinished) {
		this.ptNameFinished = ptNameFinished;
	}

	public String getPtSpecFinished() {
		return ptSpecFinished;
	}

	public void setPtSpecFinished(String ptSpecFinished) {
		this.ptSpecFinished = ptSpecFinished;
	}

	public CalUnit getPtUnitFinished() {
		return ptUnitFinished;
	}

	public void setPtUnitFinished(CalUnit ptUnitFinished) {
		this.ptUnitFinished = ptUnitFinished;
	}

	public Double getPtAmountFinished() {
		return ptAmountFinished;
	}

	public void setPtAmountFinished(Double ptAmountFinished) {
		this.ptAmountFinished = ptAmountFinished;
	}

	public Double getUnitConvertFinished() {
		return unitConvertFinished;
	}

	public void setUnitConvertFinished(Double unitConvertFinished) {
		this.unitConvertFinished = unitConvertFinished;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	
}
