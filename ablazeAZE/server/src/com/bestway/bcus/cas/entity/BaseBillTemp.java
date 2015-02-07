/*
 * Created on 2004-9-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.util.Random;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 单据临时表
 */
public class BaseBillTemp extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 错误信息
	 */
	private String errinfo;
	/**
	 * 单据临时字段1
	 */
	private String bill1;

	/**
	 * 单据临时字段2
	 */
	private String bill2;

	/**
	 * 单据临时字段3
	 */
	private String bill3;

	/**
	 * 单据临时字段4
	 */
	private String bill4;

	/**
	 * 单据临时字段5
	 */
	private String bill5;

	/**
	 * 单据临时字段6
	 */
	private String bill6;

	/**
	 * 单据临时字段7
	 */
	private String bill7;

	/**
	 * 单据临时字段8
	 */
	private String bill8;

	/**
	 * 单据临时字段9
	 */
	private String bill9;

	/**
	 * 单据临时字段10
	 */
	private String bill10;

	/**
	 * 单据临时字段11
	 */
	private String bill11;

	/**
	 * 单据临时字段12
	 */
	private String bill12;

	/**
	 * 单据临时字段13
	 */
	private String bill13;

	/**
	 * 对应报关单
	 */
	private String bill14;

	/**
	 * 单据临时字段15
	 */
	private String bill15;

	/**
	 * 单据临时字段16
	 */
	private String bill16;

	/**
	 * 单据临时字段17
	 */
	private String bill17;

	/**
	 * 单据临时字段18
	 */
	private String bill18;

	/**
	 * 单据临时字段19
	 */
	private String bill19;

	/**
	 * 单据临时字段20
	 */
	private String bill20;
	
	/**
	 * 单据临时字段21
	 */
	private String bill21;

	/**
	 * 单据临时字段22
	 */
	private String bill22;

	/**
	 * 单据临时字段23
	 */
	private String bill23;

	/**
	 * 单据临时字段24
	 */
	private String bill24;

	/**
	 * 单据临时字段25
	 */
	private String bill25;

	/**
	 * 单据临时字段26
	 */
	private String bill26;

	/**
	 * 单据临时字段27
	 */
	private String bill27;

	/**
	 * 单据临时字段28
	 */
	private String bill28;

	/**
	 * 单据临时字段29
	 */
	private String bill29;

	/**
	 * 单据临时字段30
	 */
	private String bill30;
	
	/**
	 * 单据临时字段31
	 */
	private String bill31;

	/**
	 * 单据临时字段32
	 */
	private String bill32;

	/**
	 * 单据临时字段33
	 */
	private String bill33;

	/**
	 * 单据临时字段34
	 */
	private String bill34;

	/**
	 * 单据临时字段35
	 */
	private String bill35;

	/**
	 * 单据临时字段36
	 */
	private String bill36;

	/**
	 * 单据临时字段37
	 */
	private String bill37;

	/**
	 * 单据临时字段38
	 */
	private String bill38;

	/**
	 * 单据临时字段39
	 */
	private String bill39;

	/**
	 * 单据临时字段40
	 */
	private String bill40;
	
	/**
	 * 单据临时字段41
	 */
	private String bill41;

	/**
	 * 单据临时字段42
	 */
	private String bill42;

	/**
	 * 单据临时字段43
	 */
	private String bill43;

	/**
	 * 单据临时字段44
	 */
	private String bill44;

	/**
	 * 单据临时字段45
	 */
	private String bill45;

	/**
	 * 单据临时字段46
	 */
	private String bill46;

	/**
	 * 单据临时字段47
	 */
	private String bill47;

	/**
	 * 单据临时字段48
	 */
	private String bill48;

	/**
	 * 单据临时字段49
	 */
	private String bill49;

	/**
	 * 单据临时字段50
	 */
	private String bill50;
	
	/**
	 * 单据临时字段51
	 */
	private String bill51;

	/**
	 * 单据临时字段52
	 */
	private String bill52;

	/**
	 * 单据临时字段53
	 */
	private String bill53;

	/**
	 * 单据临时字段54
	 */
	private String bill54;

	/**
	 * 单据临时字段55
	 */
	private String bill55;


	public BaseBillTemp() {
		super();
	}

	public BaseBillTemp(String errinfo, String bill1, String bill2, String bill3,
			String bill4, String bill5, String bill6, String bill7,
			String bill8, String bill9, String bill10, String bill11,
			String bill12, String bill13, String bill14, String bill15,
			String bill16, String bill17, String bill18, String bill19,
			String bill20, String bill21, String bill22, String bill23,
			String bill24, String bill25, String bill26, String bill27,
			String bill28, String bill29, String bill30, String bill31,
			String bill32, String bill33, String bill34, String bill35,
			String bill36, String bill37, String bill38, String bill39,
			String bill40, String bill41, String bill42, String bill43,
			String bill44, String bill45, String bill46, String bill47,
			String bill48, String bill49, String bill50, String bill51,
			String bill52, String bill53, String bill54, String bill55,
			Double billSum1, Double billSum2, Double billSum3, Double billSum4,
			Double billSum5, Double billSum6, Double billSum7, Double billSum8,
			Double billSum9, Double billSum10, Double billSum11,
			Double billSum12, Double billSum13, Double billSum14,
			Double billSum15, Double billSum16, Double billSum17,
			Double billSum18, Double billSum19, Double billSum20,
			Double billSum21, Double billSum22, String bill100, String orderNo,
			String envelopNo, String emsNo, ScmCoc scmcoc, WareSet wareSet,
			Country country, Curr curr, String takeBillNo, String note) {
		super();
		this.errinfo = errinfo;
		this.bill1 = bill1;
		this.bill2 = bill2;
		this.bill3 = bill3;
		this.bill4 = bill4;
		this.bill5 = bill5;
		this.bill6 = bill6;
		this.bill7 = bill7;
		this.bill8 = bill8;
		this.bill9 = bill9;
		this.bill10 = bill10;
		this.bill11 = bill11;
		this.bill12 = bill12;
		this.bill13 = bill13;
		this.bill14 = bill14;
		this.bill15 = bill15;
		this.bill16 = bill16;
		this.bill17 = bill17;
		this.bill18 = bill18;
		this.bill19 = bill19;
		this.bill20 = bill20;
		this.bill21 = bill21;
		this.bill22 = bill22;
		this.bill23 = bill23;
		this.bill24 = bill24;
		this.bill25 = bill25;
		this.bill26 = bill26;
		this.bill27 = bill27;
		this.bill28 = bill28;
		this.bill29 = bill29;
		this.bill30 = bill30;
		this.bill31 = bill31;
		this.bill32 = bill32;
		this.bill33 = bill33;
		this.bill34 = bill34;
		this.bill35 = bill35;
		this.bill36 = bill36;
		this.bill37 = bill37;
		this.bill38 = bill38;
		this.bill39 = bill39;
		this.bill40 = bill40;
		this.bill41 = bill41;
		this.bill42 = bill42;
		this.bill43 = bill43;
		this.bill44 = bill44;
		this.bill45 = bill45;
		this.bill46 = bill46;
		this.bill47 = bill47;
		this.bill48 = bill48;
		this.bill49 = bill49;
		this.bill50 = bill50;
		this.bill51 = bill51;
		this.bill52 = bill52;
		this.bill53 = bill53;
		this.bill54 = bill54;
		this.bill55 = bill55;
		this.billSum1 = billSum1;
		this.billSum2 = billSum2;
		this.billSum3 = billSum3;
		this.billSum4 = billSum4;
		this.billSum5 = billSum5;
		this.billSum6 = billSum6;
		this.billSum7 = billSum7;
		this.billSum8 = billSum8;
		this.billSum9 = billSum9;
		this.billSum10 = billSum10;
		this.billSum11 = billSum11;
		this.billSum12 = billSum12;
		this.billSum13 = billSum13;
		this.billSum14 = billSum14;
		this.billSum15 = billSum15;
		this.billSum16 = billSum16;
		this.billSum17 = billSum17;
		this.billSum18 = billSum18;
		this.billSum19 = billSum19;
		this.billSum20 = billSum20;
		this.billSum21 = billSum21;
		this.billSum22 = billSum22;
		this.bill100 = bill100;
		this.orderNo = orderNo;
		this.envelopNo = envelopNo;
		this.emsNo = emsNo;
		this.scmcoc = scmcoc;
		this.wareSet = wareSet;
		this.country = country;
		this.curr = curr;
		this.takeBillNo = takeBillNo;
		this.note = note;
	}

	public String getBill21() {
		return bill21;
	}

	public void setBill21(String bill21) {
		this.bill21 = bill21;
	}

	public String getBill22() {
		return bill22;
	}

	public void setBill22(String bill22) {
		this.bill22 = bill22;
	}

	public String getBill23() {
		return bill23;
	}

	public void setBill23(String bill23) {
		this.bill23 = bill23;
	}

	public String getBill24() {
		return bill24;
	}

	public void setBill24(String bill24) {
		this.bill24 = bill24;
	}

	public String getBill25() {
		return bill25;
	}

	public void setBill25(String bill25) {
		this.bill25 = bill25;
	}

	public String getBill26() {
		return bill26;
	}

	public void setBill26(String bill26) {
		this.bill26 = bill26;
	}

	public String getBill27() {
		return bill27;
	}

	public void setBill27(String bill27) {
		this.bill27 = bill27;
	}

	public String getBill28() {
		return bill28;
	}

	public void setBill28(String bill28) {
		this.bill28 = bill28;
	}

	public String getBill29() {
		return bill29;
	}

	public void setBill29(String bill29) {
		this.bill29 = bill29;
	}

	public String getBill30() {
		return bill30;
	}

	public void setBill30(String bill30) {
		this.bill30 = bill30;
	}

	public String getBill31() {
		return bill31;
	}

	public void setBill31(String bill31) {
		this.bill31 = bill31;
	}

	public String getBill32() {
		return bill32;
	}

	public void setBill32(String bill32) {
		this.bill32 = bill32;
	}

	public String getBill33() {
		return bill33;
	}

	public void setBill33(String bill33) {
		this.bill33 = bill33;
	}

	public String getBill34() {
		return bill34;
	}

	public void setBill34(String bill34) {
		this.bill34 = bill34;
	}

	public String getBill35() {
		return bill35;
	}

	public void setBill35(String bill35) {
		this.bill35 = bill35;
	}

	public String getBill36() {
		return bill36;
	}

	public void setBill36(String bill36) {
		this.bill36 = bill36;
	}

	public String getBill37() {
		return bill37;
	}

	public void setBill37(String bill37) {
		this.bill37 = bill37;
	}

	public String getBill38() {
		return bill38;
	}

	public void setBill38(String bill38) {
		this.bill38 = bill38;
	}

	public String getBill39() {
		return bill39;
	}

	public void setBill39(String bill39) {
		this.bill39 = bill39;
	}

	public String getBill40() {
		return bill40;
	}

	public void setBill40(String bill40) {
		this.bill40 = bill40;
	}

	public String getBill41() {
		return bill41;
	}

	public void setBill41(String bill41) {
		this.bill41 = bill41;
	}

	public String getBill42() {
		return bill42;
	}

	public void setBill42(String bill42) {
		this.bill42 = bill42;
	}

	public String getBill43() {
		return bill43;
	}

	public void setBill43(String bill43) {
		this.bill43 = bill43;
	}

	public String getBill44() {
		return bill44;
	}

	public void setBill44(String bill44) {
		this.bill44 = bill44;
	}

	public String getBill45() {
		return bill45;
	}

	public void setBill45(String bill45) {
		this.bill45 = bill45;
	}

	public String getBill46() {
		return bill46;
	}

	public void setBill46(String bill46) {
		this.bill46 = bill46;
	}

	public String getBill47() {
		return bill47;
	}

	public void setBill47(String bill47) {
		this.bill47 = bill47;
	}

	public String getBill48() {
		return bill48;
	}

	public void setBill48(String bill48) {
		this.bill48 = bill48;
	}

	public String getBill49() {
		return bill49;
	}

	public void setBill49(String bill49) {
		this.bill49 = bill49;
	}

	public String getBill50() {
		return bill50;
	}

	public void setBill50(String bill50) {
		this.bill50 = bill50;
	}

	public String getBill51() {
		return bill51;
	}

	public void setBill51(String bill51) {
		this.bill51 = bill51;
	}

	public String getBill52() {
		return bill52;
	}

	public void setBill52(String bill52) {
		this.bill52 = bill52;
	}

	public String getBill53() {
		return bill53;
	}

	public void setBill53(String bill53) {
		this.bill53 = bill53;
	}

	public String getBill54() {
		return bill54;
	}

	public void setBill54(String bill54) {
		this.bill54 = bill54;
	}

	public String getBill55() {
		return bill55;
	}

	public void setBill55(String bill55) {
		this.bill55 = bill55;
	}

	/**
	 * 单据临时字段和1
	 */
	private Double billSum1;

	/**
	 * 单据临时字段和2
	 */
	private Double billSum2;

	/**
	 * 单据临时字段和3
	 */
	private Double billSum3;

	/**
	 * 单据临时字段和4
	 */
	private Double billSum4;

	/**
	 * 单据临时字段和5
	 */
	private Double billSum5;

	/**
	 * 单据临时字段和6
	 */
	private Double billSum6;

	/**
	 * 单据临时字段和7
	 */
	private Double billSum7;

	/**
	 * 单据临时字段和8
	 */
	private Double billSum8;

	/**
	 * 单据临时字段和9
	 */
	private Double billSum9;

	/**
	 * 单据临时字段和10
	 */
	private Double billSum10;

	/**
	 * 单据临时字段和11
	 */
	private Double billSum11;

	/**
	 * 单据临时字段和12
	 */
	private Double billSum12;

	/**
	 * 单据临时字段和13
	 */
	private Double billSum13;

	/**
	 * 单据临时字段和14
	 */
	private Double billSum14;

	/**
	 * 单据临时字段和15
	 */
	private Double billSum15;

	/**
	 * 单据临时字段和16
	 */
	private Double billSum16;

	/**
	 * 单据临时字段和17
	 */
	private Double billSum17;

	/**
	 * 单据临时字段和18
	 */
	private Double billSum18;

	/**
	 * 单据临时字段和19
	 */
	private Double billSum19;

	/**
	 * 单据临时字段和20
	 */
	private Double billSum20;

	/**
	 * 单据临时字段和21
	 */
	private Double billSum21;

	/**
	 * 单据临时字段和22
	 */
	private Double billSum22;

	/**
	 * 现用于存放单据号重复时产生的唯一号
	 */
	private String bill100;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 关封号
	 * wss2010.08.31新添，主要用于 导入结转进口/结转出口的单据时关封号一起导入
	 */
	private String envelopNo;
	
	/**
	 * 手册号
	 * wss2010.09.07新添
	 */
	private String emsNo;

	/**
	 * 客户供应商  sxk用户缓存
	 */
	private ScmCoc scmcoc;
	
	/**
	 * 仓库
	 */
	private WareSet wareSet;
	
	/**
	 * 产销国
	 */
	private Country country;
	
	/**
	 * 币制
	 */
	private Curr curr;
	
	
	public ScmCoc getScmcoc() {
		return scmcoc;
	}

	public void setScmcoc(ScmCoc scmcoc) {
		this.scmcoc = scmcoc;
	}

	public WareSet getWareSet() {
		return wareSet;
	}

	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 关封号
	 * @return
	 */
	public String getEnvelopNo() {
		return envelopNo;
	}

	/**
	 * 关封号
	 * @param envelopNo
	 */
	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}
	
	/**
	 * 手册号
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 手册号
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取订单号
	 * @return
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 设置订单号
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 送货单号
	 */
	private String takeBillNo = null;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * @return Returns the bill1.
	 */
	public String getBill1() {
		return bill1;
	}

	/**
	 * @param bill1
	 *            The bill1 to set.
	 */
	public void setBill1(String bill1) {
		this.bill1 = bill1;
	}

	/**
	 * @return Returns the bill10.
	 */
	public String getBill10() {
		return bill10;
	}

	/**
	 * @param bill10
	 *            The bill10 to set.
	 */
	public void setBill10(String bill10) {
		this.bill10 = bill10;
	}

	/**
	 * @return Returns the bill2.
	 */
	public String getBill2() {
		return bill2;
	}

	/**
	 * @param bill2
	 *            The bill2 to set.
	 */
	public void setBill2(String bill2) {
		this.bill2 = bill2;
	}

	/**
	 * @return Returns the bill3.
	 */
	public String getBill3() {
		return bill3;
	}

	/**
	 * @param bill3
	 *            The bill3 to set.
	 */
	public void setBill3(String bill3) {
		this.bill3 = bill3;
	}

	/**
	 * @return Returns the bill4.
	 */
	public String getBill4() {
		return bill4;
	}

	/**
	 * @param bill4
	 *            The bill4 to set.
	 */
	public void setBill4(String bill4) {
		this.bill4 = bill4;
	}

	/**
	 * @return Returns the bill5.
	 */
	public String getBill5() {
		return bill5;
	}

	/**
	 * @param bill5
	 *            The bill5 to set.
	 */
	public void setBill5(String bill5) {
		this.bill5 = bill5;
	}

	/**
	 * @return Returns the bill6.
	 */
	public String getBill6() {
		return bill6;
	}

	/**
	 * @param bill6
	 *            The bill6 to set.
	 */
	public void setBill6(String bill6) {
		this.bill6 = bill6;
	}

	/**
	 * @return Returns the bill7.
	 */
	public String getBill7() {
		return bill7;
	}

	/**
	 * @param bill7
	 *            The bill7 to set.
	 */
	public void setBill7(String bill7) {
		this.bill7 = bill7;
	}

	/**
	 * @return Returns the bill8.
	 */
	public String getBill8() {
		return bill8;
	}

	/**
	 * @param bill8
	 *            The bill8 to set.
	 */
	public void setBill8(String bill8) {
		this.bill8 = bill8;
	}

	/**
	 * @return Returns the bill9.
	 */
	public String getBill9() {
		return bill9;
	}

	/**
	 * @param bill9
	 *            The bill9 to set.
	 */
	public void setBill9(String bill9) {
		this.bill9 = bill9;
	}

	/**
	 * @return Returns the billSum1.
	 */
	public Double getBillSum1() {
		return billSum1;
	}

	/**
	 * @param billSum1
	 *            The billSum1 to set.
	 */
	public void setBillSum1(Double billSum1) {
		this.billSum1 = billSum1;
	}

	/**
	 * @return Returns the billSum2.
	 */
	public Double getBillSum2() {
		return billSum2;
	}

	/**
	 * @param billSum2
	 *            The billSum2 to set.
	 */
	public void setBillSum2(Double billSum2) {
		this.billSum2 = billSum2;
	}

	/**
	 * @return Returns the billSum3.
	 */
	public Double getBillSum3() {
		return billSum3;
	}

	/**
	 * @param billSum3
	 *            The billSum3 to set.
	 */
	public void setBillSum3(Double billSum3) {
		this.billSum3 = billSum3;
	}

	/**
	 * @return Returns the billSum4.
	 */
	public Double getBillSum4() {
		return billSum4;
	}

	/**
	 * @param billSum4
	 *            The billSum4 to set.
	 */
	public void setBillSum4(Double billSum4) {
		this.billSum4 = billSum4;
	}

	/**
	 * @return Returns the billSum5.
	 */
	public Double getBillSum5() {
		return billSum5;
	}

	/**
	 * @param billSum5
	 *            The billSum5 to set.
	 */
	public void setBillSum5(Double billSum5) {
		this.billSum5 = billSum5;
	}

	/**
	 * @return Returns the billSum8.
	 */
	public Double getBillSum8() {
		return billSum8;
	}

	/**
	 * @param billSum8
	 *            The billSum8 to set.
	 */
	public void setBillSum8(Double billSum8) {
		this.billSum8 = billSum8;
	}

	/**
	 * @return Returns the billSum6.
	 */
	public Double getBillSum6() {
		return billSum6;
	}

	/**
	 * @param billSum6
	 *            The billSum6 to set.
	 */
	public void setBillSum6(Double billSum6) {
		this.billSum6 = billSum6;
	}

	/**
	 * @return Returns the billSum7.
	 */
	public Double getBillSum7() {
		return billSum7;
	}

	/**
	 * @param billSum7
	 *            The billSum7 to set.
	 */
	public void setBillSum7(Double billSum7) {
		this.billSum7 = billSum7;
	}

	/**
	 * @return Returns the billSum10.
	 */
	public Double getBillSum10() {
		return billSum10;
	}

	/**
	 * @param billSum10
	 *            The billSum10 to set.
	 */
	public void setBillSum10(Double billSum10) {
		this.billSum10 = billSum10;
	}

	/**
	 * @return Returns the billSum11.
	 */
	public Double getBillSum11() {
		return billSum11;
	}

	/**
	 * @param billSum11
	 *            The billSum11 to set.
	 */
	public void setBillSum11(Double billSum11) {
		this.billSum11 = billSum11;
	}

	/**
	 * @return Returns the billSum12.
	 */
	public Double getBillSum12() {
		return billSum12;
	}

	/**
	 * @param billSum12
	 *            The billSum12 to set.
	 */
	public void setBillSum12(Double billSum12) {
		this.billSum12 = billSum12;
	}

	/**
	 * @return Returns the billSum13.
	 */
	public Double getBillSum13() {
		return billSum13;
	}

	/**
	 * @param billSum13
	 *            The billSum13 to set.
	 */
	public void setBillSum13(Double billSum13) {
		this.billSum13 = billSum13;
	}

	/**
	 * @return Returns the billSum14.
	 */
	public Double getBillSum14() {
		return billSum14;
	}

	/**
	 * @param billSum14
	 *            The billSum14 to set.
	 */
	public void setBillSum14(Double billSum14) {
		this.billSum14 = billSum14;
	}

	/**
	 * @return Returns the billSum15.
	 */
	public Double getBillSum15() {
		return billSum15;
	}

	/**
	 * @param billSum15
	 *            The billSum15 to set.
	 */
	public void setBillSum15(Double billSum15) {
		this.billSum15 = billSum15;
	}

	/**
	 * @return Returns the billSum16.
	 */
	public Double getBillSum16() {
		return billSum16;
	}

	/**
	 * @param billSum16
	 *            The billSum16 to set.
	 */
	public void setBillSum16(Double billSum16) {
		this.billSum16 = billSum16;
	}

	/**
	 * @return Returns the billSum17.
	 */
	public Double getBillSum17() {
		return billSum17;
	}

	/**
	 * @param billSum17
	 *            The billSum17 to set.
	 */
	public void setBillSum17(Double billSum17) {
		this.billSum17 = billSum17;
	}

	/**
	 * @return Returns the billSum9.
	 */
	public Double getBillSum9() {
		return billSum9;
	}

	/**
	 * @param billSum9
	 *            The billSum9 to set.
	 */
	public void setBillSum9(Double billSum9) {
		this.billSum9 = billSum9;
	}

	public Double getBillSum18() {
		return billSum18;
	}

	public void setBillSum18(Double billSum18) {
		this.billSum18 = billSum18;
	}

	public String getBill11() {
		return bill11;
	}

	public void setBill11(String bill11) {
		this.bill11 = bill11;
	}

	public String getBill12() {
		return bill12;
	}

	public void setBill12(String bill12) {
		this.bill12 = bill12;
	}

	public String getBill13() {
		return bill13;
	}

	public void setBill13(String bill13) {
		this.bill13 = bill13;
	}

	public String getBill14() {
		return bill14;
	}

	public void setBill14(String bill14) {
		this.bill14 = bill14;
	}

	public String getBill15() {
		return bill15;
	}

	public void setBill15(String bill15) {
		this.bill15 = bill15;
	}

	public String getBill16() {
		return bill16;
	}

	public void setBill16(String bill16) {
		this.bill16 = bill16;
	}

	public String getBill17() {
		return bill17;
	}

	public void setBill17(String bill17) {
		this.bill17 = bill17;
	}

	public Double getBillSum19() {
		return billSum19;
	}

	public void setBillSum19(Double billSum19) {
		this.billSum19 = billSum19;
	}

	/**
	 * @return the bill18
	 */
	public String getBill18() {
		return bill18;
	}

	/**
	 * @param bill18
	 *            the bill18 to set
	 */
	public void setBill18(String bill18) {
		this.bill18 = bill18;
	}

	/**
	 * @return the bill19
	 */
	public String getBill19() {
		return bill19;
	}

	/**
	 * @param bill19
	 *            the bill19 to set
	 */
	public void setBill19(String bill19) {
		this.bill19 = bill19;
	}

	/**
	 * @return the bill20
	 */
	public String getBill20() {
		return bill20;
	}

	/**
	 * @param bill20
	 *            the bill20 to set
	 */
	public void setBill20(String bill20) {
		this.bill20 = bill20;
	}

	public String getBill100() {
		return bill100;
	}

	public void setBill100(String bill100) {
		this.bill100 = bill100;
	}

	public Double getBillSum20() {
		return billSum20;
	}

	public void setBillSum20(Double billSum20) {
		this.billSum20 = billSum20;
	}

	public Double getBillSum21() {
		return billSum21;
	}

	public void setBillSum21(Double billSum21) {
		this.billSum21 = billSum21;
	}

	public Double getBillSum22() {
		return billSum22;
	}

	public void setBillSum22(Double billSum22) {
		this.billSum22 = billSum22;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	/**
	 * @return the takeBillNo
	 */
	public String getTakeBillNo() {
		return takeBillNo;
	}

	/**
	 * @param takeBillNo the takeBillNo to set
	 */
	public void setTakeBillNo(String takeBillNo) {
		this.takeBillNo = takeBillNo;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

}
