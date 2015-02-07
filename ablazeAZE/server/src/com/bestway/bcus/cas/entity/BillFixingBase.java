/*
 * Created on 2004-9-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 设备单据表
 */
public class BillFixingBase extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * 设备名称/单位
	 */
	private String bill1;
	/**
	 * 备注
	 */
	private String bill2;
	/**
	 * 直接报关进口
	 */
	private Double billSum1=0.0;
	/**
	 * 结转报关进口    
	 */
	private Double billSum2=0.0;
	/**
	 * 其中征税进口
	 */
	private Double billSum3=0.0;
	/**
	 * 国内采购
	 */
	private Double billSum4=0.0;
	/**
	 * 其他来源
	 */
	private Double billSum5=0.0;
	/**
	 * 来源合计
	 */
	private Double billSum6=0.0;
	/**
	 * 退运出口
	 */
	private Double billSum7=0.0;
	/**
	 * 结转报关出口
	 */
	private Double billSum8=0.0;
	/**
	 * 出借/出租
	 */
	
	private Double billSum9=0.0;
	/**
	 * 海关批准出售
	 */
	private Double billSum10=0.0;
	/**
	 * 其他出售
	 */
	private Double billSum11=0.0;
	/**
	 * 报废
	 */
	private Double billSum12=0.0;
	/**
	 * 闲置
	 */
	private Double billSum13=0.0;
	/**
	 * 其他处置
	 */
	private Double billSum14=0.0;
	/**
	 * 在生产数量
	 */
	private Double billSum15=0.0;
	/**
	 * 金额
	 */
    private Double money =0.0;

	/**
	 * 取得设备名称/单位
	 * @return  bill1 设备名称/单位
	 */
	public String getBill1() {
		return bill1;
	}
	/**
	 * 设置设备名称/单位
	 * @param bill1 设备名称/单位
	 */
	public void setBill1(String bill1) {
		this.bill1 = bill1;
	}
	/**
	 * 取得备注内容
	 * @return bill2 备注
	 */
	public String getBill2() {
		return bill2;
	}
	/**
	 * 填写备注内容
	 * @param bill2 备注
	 */
	public void setBill2(String bill2) {
		this.bill2 = bill2;
	}
	/**
	 * 取得直接报关进口数量
	 * @return billSum1 直接报关进口
	 */
	public Double getBillSum1() {
		return billSum1;
	}
	/**
	 * 设置直接报关进口数量
	 * @param billSum1 报关进口
	 */
	public void setBillSum1(Double billSum1) {
		this.billSum1 = billSum1;
	}
	/**
	 * 取得海关批准出售数量
	 * @return billSum10 海关批准出售
	 */
	public Double getBillSum10() {
		return billSum10;
	}
	/**
	 * 设置海关批准出售数量
	 * @param billSum10 海关批准出售
	 */
	public void setBillSum10(Double billSum10) {
		this.billSum10 = billSum10;
	}
	/**
	 * 取得其它出售数量
	 * @return billSum11 其它出售数量
	 */
	public Double getBillSum11() {
		return billSum11;
	}
	/**
	 * 设置其它出售数量
	 * @param billSum11 其它出售
	 */
	public void setBillSum11(Double billSum11) {
		this.billSum11 = billSum11;
	}
	/**
	 * 取得报废数量
	 * @return billSum12 报废
	 */
	public Double getBillSum12() {
		return billSum12;
	}
	/**
	 * 设置报废数量
	 * @param billSum12 报废
	 */
	public void setBillSum12(Double billSum12) {
		this.billSum12 = billSum12;
	}
	/**
	 * 取得闲置数量
	 * @return billSum13 闲置数量
	 */
	public Double getBillSum13() {
		return billSum13;
	}
	/**
	 * 设置闲置数量
	 * @param billSum13  闲置数量
	 */
	public void setBillSum13(Double billSum13) {
		this.billSum13 = billSum13;
	}
	/**
	 * 取得其它处置数量
	 * @return billSum14 其它处置数量
	 */
	public Double getBillSum14() {
		return billSum14;
	}
	/**
	 * 设置其它处置数量
	 * @param billSum14 其它处置
	 */
	public void setBillSum14(Double billSum14) {
		this.billSum14 = billSum14;
	}
	/**
	 * 取得在生产数量
	 * @return  在生产数量 =来源合计-退运出口-结转报关出口-出借/出租-海关批准出售-其他出售-报废-闲置-其它处置 
	 * 
	 */
	public Double getBillSum15() {
		return this.getBillSum6() - billSum7 - billSum8 - billSum9 - billSum10 - billSum11 - billSum12 - billSum13 - billSum14;
	}
	/**
	 * 设置在生产数量
	 * @param billSum15 在生产数量
	 */
	public void setBillSum15(Double billSum15) {
		this.billSum15 = billSum15;
	}
	/**
	 * 取得结转报关进口数量
	 * @return billSum2 结转报关进口
	 */
	public Double getBillSum2() {
		return billSum2;
	}
	/**
	 * 设置结转报关进口数量
	 * @param billSum2 结转报关进口
	 */
	public void setBillSum2(Double billSum2) {
		this.billSum2 = billSum2;
	}
	/**
	 * 取得征税进口数量
	 * @return billSum3 征税进口
	 */
	public Double getBillSum3() {
		return billSum3;
	}
	/**
	 * 设置征税进口数量
	 * @param billSum3 征税进口
	 */
	public void setBillSum3(Double billSum3) {
		this.billSum3 = billSum3;
	}
	/**
	 * 取得国内采购数量
	 * @return billSum4 国内采购
	 */
	public Double getBillSum4() {
		return billSum4;
	}
	/**
	 * 设置国内采购数量
	 * @param billSum4 国内采购
	 */
	public void setBillSum4(Double billSum4) {
		this.billSum4 = billSum4;
	}
	/**取得其它来源数量
	 * @return 其它来源
	 */
	public Double getBillSum5() {
		return billSum5;
	}
	/**
	 * 设置其它来源数量
	 * @param billSum5 其它来源
	 */
	public void setBillSum5(Double billSum5) {
		this.billSum5 = billSum5;
	}
	/**
	 * 取得来源合计
	 * @return billSum6 = billSum1+billSum2+billSum4+billSum5  来源合计 = 结转报关进口+直接报关进口+国内采购+其他来源
	 */
	public Double getBillSum6() {
		return billSum1+billSum2+billSum4+billSum5;
	}
	/**
	 * 设置来源合计 
	 * @param billSum6 来源合计 
	 */
	public void setBillSum6(Double billSum6) {
		this.billSum6 = billSum6;
	}
	/**
	 * 取得退运出口数量
	 * @return billSum7 退运出口
	 */
	public Double getBillSum7() {
		return billSum7;
	}
	/**
	 * 设置退运出口数量
	 * @param billSum7  退运出口
	 */
	public void setBillSum7(Double billSum7) {
		this.billSum7 = billSum7;
	}
	/**
	 * 取得结转报关出口数量
	 * @return  billSum8 结转报关出口
	 */
	public Double getBillSum8() {
		return billSum8;
	}
	/**
	 * 取得结转报关出口数量
	 * @param billSum8 结转报关出口
	 */
	public void setBillSum8(Double billSum8) {
		this.billSum8 = billSum8;
	}
	/**
	 * 取得出租或出借的数量
	 * @return  billSum9  出租或出借
	 */
	public Double getBillSum9() {
		return billSum9;
	}
	/**
	 * 设置出租或出借的数量
	 * @param billSum9  出租或出借  
	 */
	public void setBillSum9(Double billSum9) {
		this.billSum9 = billSum9;
	}
	/**
	 * 统计总体金额
	 * @return money   金额 
	 */
    public Double getMoney() {
        return money;
    }
    /**
	 * 设置总体金额 
	 * @param money 金额 
	 */
    public void setMoney(Double money) {
        this.money = money;
    }
}
