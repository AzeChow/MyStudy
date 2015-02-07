package com.bestway.bcs.contractstat.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 库存情况统计表实体
 */
public class StockStatisticsBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	/**
	 * 手册号
	 */
	private String emsHeadH2k = null;
	
	/**
	 * 合同号
	 */
	private String contract = null;
	
	/**
	 * 备案序号
	 */

	private Integer seqNum = null;  //  @jve:decl-index=0:
	

	/**
	 * 商品编号
	 */
	private String hsCode = null;  //  @jve:decl-index=0:
	
	/**
	 * 商品名称
	 */
	private String hsName = null;  //  @jve:decl-index=0:

	/**
	 * 商品规格
	 */
	private String hsSpec = null;  //  @jve:decl-index=0:

	/**
	 * 商品单位
	 */
	private String hsUnit = null;  //  @jve:decl-index=0:

	/**
	 * 料号/成品货号
	 */
	private String ptNo = null;
	
	/**
	 * 工厂名称
	 */
	private String ptName = null;  //  @jve:decl-index=0:

	/**
	 * 工厂规格
	 */
	private String ptSpec = null;  //  @jve:decl-index=0:

	/**
	 * 工厂单位
	 */
	private String ptUnit = null;  //  @jve:decl-index=0:
	
	/**
	 * 单位折算系数
	 */
	private Double unitConvert = null;
	
	private Double num1;
	private Double num2;
	private Double num3;
	private Double num4;
	private Double num5;
	private Double num6;
	private Double num7;
	private Double num8;
	private Double num9;
	private Double num10;
	private Double num11;
	private Double num12;
	private Double num13;
	private Double num14;
	private Double num15;
	private Double num16;
	private Double num17;
	private Double num18;
	private Double num19;
	private Double num20;
	

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

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public String getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(String ptUnit) {
		this.ptUnit = ptUnit;
	}

	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Double getNum1() {
		return num1;
	}

	public void setNum1(Double num1) {
		this.num1 = num1;
	}

	public Double getNum2() {
		return num2;
	}

	public void setNum2(Double num2) {
		this.num2 = num2;
	}

	public Double getNum3() {
		return num3;
	}

	public void setNum3(Double num3) {
		this.num3 = num3;
	}

	public Double getNum4() {
		return num4;
	}

	public void setNum4(Double num4) {
		this.num4 = num4;
	}

	public Double getNum5() {
		return num5;
	}

	public void setNum5(Double num5) {
		this.num5 = num5;
	}

	public Double getNum6() {
		return num6;
	}

	public void setNum6(Double num6) {
		this.num6 = num6;
	}

	public Double getNum7() {
		return num7;
	}

	public void setNum7(Double num7) {
		this.num7 = num7;
	}

	public Double getNum8() {
		return num8;
	}

	public void setNum8(Double num8) {
		this.num8 = num8;
	}

	public Double getNum9() {
		return num9;
	}

	public void setNum9(Double num9) {
		this.num9 = num9;
	}

	public Double getNum10() {
		return num10;
	}

	public void setNum10(Double num10) {
		this.num10 = num10;
	}

	public Double getNum11() {
		return num11;
	}

	public void setNum11(Double num11) {
		this.num11 = num11;
	}

	public Double getNum12() {
		return num12;
	}

	public void setNum12(Double num12) {
		this.num12 = num12;
	}

	public Double getNum13() {
		return num13;
	}

	public void setNum13(Double num13) {
		this.num13 = num13;
	}

	public Double getNum14() {
		return num14;
	}

	public void setNum14(Double num14) {
		this.num14 = num14;
	}

	public Double getNum15() {
		return num15;
	}

	public void setNum15(Double num15) {
		this.num15 = num15;
	}

	public Double getNum16() {
		return num16;
	}

	public void setNum16(Double num16) {
		this.num16 = num16;
	}

	public Double getNum17() {
		return num17;
	}

	public void setNum17(Double num17) {
		this.num17 = num17;
	}

	public Double getNum18() {
		return num18;
	}

	public void setNum18(Double num18) {
		this.num18 = num18;
	}

	public Double getNum19() {
		return num19;
	}

	public void setNum19(Double num19) {
		this.num19 = num19;
	}

	public Double getNum20() {
		return num20;
	}

	public void setNum20(Double num20) {
		this.num20 = num20;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}
	
	
	public String getHsName() {
		return hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public String getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
	
	

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public void init() {
		num1 = 0.0;
		num2 = 0.0;
		num3 = 0.0;
		num4 = 0.0;
		num5 = 0.0;
		num6 = 0.0;
		num7 = 0.0;
		num8 = 0.0;
		num9 = 0.0;
		num10 = 0.0;
		num11 = 0.0;
		num12 = 0.0;
		num13 = 0.0;
		num14 = 0.0;
		num15 = 0.0;
		num16 = 0.0;
		num17 = 0.0;
		num18 = 0.0;
		num19 = 0.0;
		num20 = 0.0;
		
		unitConvert = 1.0;
	}
}