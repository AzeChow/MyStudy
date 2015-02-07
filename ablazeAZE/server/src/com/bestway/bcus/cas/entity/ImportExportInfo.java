/*
 * Created on 2004-9-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.SBillType;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.jptds.client.common.CommonVars;

/**
 * 委外的进出口商品信息
 */
public class ImportExportInfo implements Serializable {
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
	 * 物料类型
	 */
	private String materielType;
	/**
	 * 进出口类型
	 */
	private Boolean isIn;
	
	/**
	 * 版本号
	 * wss2010.08.27因为成品要分版本号
	 */
	private Integer version;
	 /**
	 * 单净重
	 * lyh2013.01.04
	 */
	 private Double inNetWeight = null;

	/**
	 * 空的构造函数
	 * 
	 */
	public ImportExportInfo() {

	}
	/**
	 * 初始化进出仓日期,单据类型,出仓凭证号、单据号,出仓工厂数量,出仓海关数量,入仓单据号,入仓工厂数量,入仓海关数量,结存工厂数量,结存海关数量,
	 * 等等,根据不同的工厂单据 取得相对应的值
	 * 
	 * @param billDetail
	 */
	public ImportExportInfo(BillDetail billDetail) {
		this.billMasterid = billDetail.getBillMaster() == null ? null
				: billDetail.getBillMaster().getId();
		this.date = billDetail.getBillMaster().getValidDate();
		this.billType = billDetail.getBillMaster().getBillType();
		if (billType.getWareType() == null) {
			return;
		}
		if (billType.getWareType().intValue() == 1) { // 进仓
			this.impBillNo = billDetail.getBillMaster().getBillNo();
			this.impPtAmount = billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount();
			this.impHsAmount = billDetail.getHsAmount() == null ? 0.0
					: billDetail.getHsAmount();
			if (billType.getCode().equals("1001") || // 1001 料件起初单
					billType.getCode().equals("2001") || // 2001 成品起初单
					billType.getCode().equals("5002") || // 5002 残次品起初单
					billType.getCode().equals("6003")) { // 6003 边角料期初单
				this.impBillNo = "期初单";
			}
		} else {
			this.expBillNo = billDetail.getBillMaster().getBillNo();
			this.expPtAmount = billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount();
			this.expHsAmount = billDetail.getHsAmount() == null ? 0.0
					: billDetail.getHsAmount();
		}
		this.rsPtAmount = Double.valueOf(0);
		this.rsHsAmount = Double.valueOf(0);
		String temp = "";
		String customNo = billDetail.getCustomNo() == null ? "" : billDetail
				.getCustomNo().trim();
		String productNo = billDetail.getProductNo() == null ? "" : billDetail
				.getProductNo().trim();
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
		this.scmCoc = billDetail.getBillMaster().getScmCoc();
		this.seqNum = billDetail.getSeqNum();
		this.ptPart = billDetail.getPtPart();
		this.ptName = billDetail.getPtName();
		this.ptSpec = billDetail.getPtSpec();
		this.ptUnit = billDetail.getPtUnit() == null ? new CalUnit()
				: billDetail.getPtUnit();
		this.hsUnit = billDetail.getHsUnit() == null ? new Unit() : billDetail
				.getHsUnit();
		this.wareSet = billDetail.getWareSet() == null ? new WareSet()
				: billDetail.getWareSet();
		this.note = billDetail.getNote();
		this.hsName = billDetail.getHsName();
		this.hsSpec = billDetail.getHsSpec();
		//单净重=净重/数量
		if(billDetail.getPtAmount()!=null&&billDetail.getPtAmount()!=Double.valueOf(0)&&billDetail.getNetWeight()!=null){
			this.inNetWeight = CaleUtil.dividend(billDetail.getNetWeight(), billDetail.getPtAmount(), 6);
		}else{
			this.inNetWeight = Double.valueOf(0);
		}
	}


	/**
	 * 用于委外的构造函数
	 * 
	 * @param billDetail
	 * @param isConsignQueryFlag
	 *            只是标识而已
	 */
	public ImportExportInfo(BillDetail billDetail, boolean isConsignQueryFlag) {
		//
		// code="1110" name="外发加工出库"
		// code="1112" name="外发加工返修出库"
		// code="1012" name="外发加工退回料件"
		// code="1013" name="外发加工返回料件"
		// code="1014" name="委外期初单"
		// code="4106" name="外发加工半成品出库"
		// code="4004" name="外发加工半成品退回"
		// code="2112" name="外发加工成品出库"
		// code="2017" name="外发加工成品退回"
		this.billMasterid = billDetail.getBillMaster() == null ? null
				: billDetail.getBillMaster().getId();
		this.date = billDetail.getBillMaster().getValidDate();
		this.billType = billDetail.getBillMaster().getBillType();
		if (billType.getWareType() == null) {
			return;
		}
		if (billType.getCode().equals("1017")
				|| billType.getCode().equals("4004")
				|| billType.getCode().equals("2017")
				|| billType.getCode().equals("1018")
				|| billType.getCode().equals("4006")
				|| billType.getCode().equals("2015")
				|| billType.getCode().equals("6005")
				|| billType.getCode().equals("5003")
				|| billType.getCode().equals("1008")
				|| billType.getCode().equals("4005")
				|| billType.getCode().equals("2014")
				|| billType.getCode().equals("1019")
				|| billType.getCode().equals("4008")
				|| billType.getCode().equals("2016")
				|| billType.getCode().equals("6006")
				|| billType.getCode().equals("5004")) {// 进仓

			System.out.println("1017,4004,2017");
			this.expBillNo = billDetail.getBillMaster().getBillNo();
			this.expPtAmount = billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount();
			this.expHsAmount = billDetail.getHsAmount() == null ? 0.0
					: billDetail.getHsAmount();
		} else if (billType.getCode().equals("1113")
				|| billType.getCode().equals("4106")
				|| billType.getCode().equals("2114")
				|| billType.getCode().equals("1112")
				|| billType.getCode().equals("4104")
				|| billType.getCode().equals("2113")
				|| billType.getCode().equals("6105")
				|| billType.getCode().equals("1111")
				|| billType.getCode().equals("4107")
				|| billType.getCode().equals("2111")
				|| billType.getCode().equals("1114")
				|| billType.getCode().equals("4108")
				|| billType.getCode().equals("2112")
				|| billType.getCode().equals("6106")
				|| billType.getCode().equals("5104")) { // 出仓
			System.out.println("1113,4106,2114");
			this.impBillNo = billDetail.getBillMaster().getBillNo();
			this.impPtAmount = billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount();
			this.impHsAmount = billDetail.getHsAmount() == null ? 0.0
					: billDetail.getHsAmount();
			if (billType.getCode().equals("1014")) { // 1014 委外期初单
				this.impBillNo = "期初单";
			}
		}
		this.rsPtAmount = Double.valueOf(0);
		this.rsHsAmount = Double.valueOf(0);
		String temp = "";
		String customNo = billDetail.getCustomNo() == null ? "" : billDetail
				.getCustomNo().trim();
		String productNo = billDetail.getProductNo() == null ? "" : billDetail
				.getProductNo().trim();
		// if (customNo.equals("") && !productNo.equals("")) {
		// temp += productNo;
		// } else if (!customNo.equals("") && productNo.equals("")) {
		// temp += customNo;
		// }
		// if (!customNo.equals("") && !productNo.equals("")) {
		// temp += customNo + "/" + productNo;
		// }
		// this.productNo = temp;//之前对应报关单号与制单号放在一个栏位,以分号隔开
		this.customNo = customNo;
		this.productNo = productNo;
		this.scmCoc = billDetail.getBillMaster().getScmCoc();
		this.seqNum = billDetail.getSeqNum();
		this.ptPart = billDetail.getPtPart();
		this.ptName = billDetail.getPtName();
		this.ptSpec = billDetail.getPtSpec();
		this.ptUnit = billDetail.getPtUnit() == null ? new CalUnit()
				: billDetail.getPtUnit();
		this.hsUnit = billDetail.getHsUnit() == null ? new Unit() : billDetail
				.getHsUnit();
		this.wareSet = billDetail.getWareSet() == null ? new WareSet()
				: billDetail.getWareSet();
		this.note = billDetail.getNote();
		this.hsName = billDetail.getHsName();
		this.hsSpec = billDetail.getHsSpec();
	}

	/**
	 * 用于自我核查
	 * 初始化进出仓日期,单据类型,出仓凭证号、单据号,出仓工厂数量,出仓海关数量,入仓单据号,入仓工厂数量,入仓海关数量,结存工厂数量,结存海关数量,
	 * 等等,根据不同的工厂单据 取得相对应的值
	 * 
	 * @param billDetail
	 */
	public ImportExportInfo(BillDetail billDetail, String materielType) {
		this.billMasterid = billDetail.getBillMaster() == null ? null
				: billDetail.getBillMaster().getId();
		this.date = billDetail.getBillMaster().getValidDate();
		this.billType = billDetail.getBillMaster().getBillType();
		if (billType.getWareType() == null) {
			return;
		}

		if (isIn(billDetail, materielType)) { // 进仓
			this.impBillNo = billDetail.getBillMaster().getBillNo();
			this.impPtAmount = billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount();
			this.impHsAmount = billDetail.getHsAmount() == null ? 0.0
					: billDetail.getHsAmount();
			if (billType.getCode().equals("1001") || // 1001 料件起初单
					billType.getCode().equals("2001") || // 2001 成品起初单
					billType.getCode().equals("5002") || // 5002 残次品起初单
					billType.getCode().equals("6003")) { // 6003 边角料期初单
				this.impBillNo = "期初单";
			}
		} else {
			this.expBillNo = billDetail.getBillMaster().getBillNo();
			this.expPtAmount = billDetail.getPtAmount() == null ? 0.0
					: billDetail.getPtAmount();
			this.expHsAmount = billDetail.getHsAmount() == null ? 0.0
					: billDetail.getHsAmount();
		}
		this.rsPtAmount = Double.valueOf(0);
		this.rsHsAmount = Double.valueOf(0);
		String temp = "";
		String customNo = billDetail.getCustomNo() == null ? "" : billDetail
				.getCustomNo().trim();
		String productNo = billDetail.getProductNo() == null ? "" : billDetail
				.getProductNo().trim();
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
		this.scmCoc = billDetail.getBillMaster().getScmCoc();
		this.seqNum = billDetail.getSeqNum();
		this.ptPart = billDetail.getPtPart();
		this.ptName = billDetail.getPtName();
		this.ptSpec = billDetail.getPtSpec();
		this.ptUnit = billDetail.getPtUnit() == null ? new CalUnit()
				: billDetail.getPtUnit();
		this.hsUnit = billDetail.getHsUnit() == null ? new Unit() : billDetail
				.getHsUnit();
		this.wareSet = billDetail.getWareSet() == null ? new WareSet()
				: billDetail.getWareSet();
		this.note = billDetail.getNote();
		this.hsName = billDetail.getHsName();
		this.hsSpec = billDetail.getHsSpec();
		this.materielType = getMaterielTypeString(billDetail.getBillMaster()
				.getBillType().getBillType());

	}

	private boolean isIn(BillDetail billDetail, String materielType) {
		if (materielType.equals(MaterielType.MATERIEL)) {
			if (billDetail.getBillMaster().getBillType().getCode().equals(
					"1001")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1003")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1004")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1005")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1006")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1007")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1009")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1010")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1011")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1012")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1013")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1017")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("1018")) {
				isIn = true;
				return true;
			} else {
				isIn = false;
				return false;
			}
		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			if (billDetail.getBillMaster().getBillType().getCode().equals(
					"2001")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2002")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2003")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2004")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2005")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2008")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2009")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2015")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("2017")) {
				isIn = true;
				return true;
			} else {
				isIn = false;
				return false;
			}

		} else if (materielType.equals(MaterielType.BAD_PRODUCT)) {
			if (billDetail.getBillMaster().getBillType().getCode().equals(
					"5001")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("5002")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("5003")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("5005")) {
				isIn = true;
				return true;
			} else {
				isIn = false;
				return false;
			}

		} else if (materielType.equals(MaterielType.REMAIN_MATERIEL)) {
			if (billDetail.getBillMaster().getBillType().getCode().equals(
					"6001")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("6002")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("6003")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("6004")
					|| billDetail.getBillMaster().getBillType().getCode()
							.equals("6005")) {
				isIn = true;
				return true;
			} else {
				isIn = false;
				return false;
			}

		}
		return false;
	}

	/**
	 * 辨别物料类型名称
	 * 
	 * @param billType
	 * @return
	 */
	public String getMaterielTypeString(int billType) {
		String materielTypeString = null;
		if (billType == SBillType.MATERIEL_IN
				|| billType == SBillType.MATERIEL_OUT) {
			materielTypeString = "料件";
		} else if (billType == SBillType.PRODUCE_IN
				|| billType == SBillType.PRODUCE_OUT) {
			materielTypeString = "成品";
		} else if (billType == SBillType.HALF_PRODUCE_IN
				|| billType == SBillType.HALF_PRODUCE_OUT
				|| billType == SBillType.HALF_PRODUCE_INOUT) {
			materielTypeString = "半成品";
		} else if (billType == SBillType.FIXTURE_IN
				|| billType == SBillType.FIXTURE_OUT) {
			materielTypeString = "设备";
		} else if (billType == SBillType.LEFTOVER_MATERIEL_IN
				|| billType == SBillType.LEFTOVER_MATERIEL_OUT
				|| billType == SBillType.LEFTOVER_MATERIEL_INOUT) {
			materielTypeString = "边角料";
		} else if (billType == SBillType.REMAIN_PRODUCE_IN
				|| billType == SBillType.REMAIN_PRODUCE_OUT
				|| billType == SBillType.REMAIN_PRODUCE_INOUT) {
			materielTypeString = "残次品";
		}
		return materielTypeString;
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

	public String getMaterielType() {
		return materielType;
	}

	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	public Boolean getIsIn() {
		return isIn;
	}

	public void setIsIn(Boolean isIn) {
		this.isIn = isIn;
	}
	
	/**
	 * 版本号 wss2010.08.27因为成品要分版本号
	 * @return
	 */
	public Integer getVersion() {
		return version;
	}
	
	/**
	 * 版本号 wss2010.08.27因为成品要分版本号
	 * @param version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Double getInNetWeight() {
		return inNetWeight;
	}
	public void setInNetWeight(Double inNetWeight) {
		this.inNetWeight = inNetWeight;
	}
	
}
