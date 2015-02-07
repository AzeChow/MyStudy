package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 结转差额总表
 * @author chensir
 *
 */
public class CarryBalance implements Serializable,Cloneable {
	
	//业务类型
	public static String SHOU_HUO="收货数量";
	public static String SONG_HUO="送货数量";
	public static String JIE_ZHUAN="结转数量";
	public static String CHA_E="差额数量";
	public static String GUAN_FENG="关封数量";
	
	public CarryBalance(){
		
	}
	
	public CarryBalance(String type){
		this.type = type;
	}
	
	public CarryBalance(String type,ScmCoc scmCoc,String code,String name,String spec,String unitName){
		this.type = type;
		this.customerName = scmCoc.getName();
		this.scmCoc = scmCoc;
		this.code = code;
		this.name = name;
		this.spec = spec;
		this.unitName = unitName;
		this.customerCode = scmCoc.getCode();
	}
	/**
	 * 序号
	 */
	private Integer no;
	
	/**
	 * 客户
	 * wss:2010.06.27添加，为方便关联查询
	 */
	private ScmCoc scmCoc;
	/**
	 * 客户工厂代码
	 */
	private String customerCode;
	/**
	 * 客户名称
	 */
	private String customerName;
	
	/**
	 * 商品编码
	 */
	private String code;
	
	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品规格
	 */
	private String spec;
	
	/**
	 * 商品单位
	 */
	private String unitName;
	
	/**
	 * 业务类型
	 */
	private String type;
	
	/**
	 * 期初数量
	 */
	private Double amountFirst;
	
	/**
	 * 1月
	 */
	private Double amountMonth1;
	
	/**
	 * 2月
	 */
	private Double amountMonth2;
	
	/**
	 * 3月
	 */
	private Double amountMonth3;
	/**
	 * 4月
	 */
	private Double amountMonth4;
	/**
	 * 5月
	 */
	private Double amountMonth5;
	/**
	 * 6月
	 */
	private Double amountMonth6;
	/**
	 * 7月
	 */
	private Double amountMonth7;
	/**
	 * 8月
	 */
	private Double amountMonth8;
	/**
	 * 9月
	 */
	private Double amountMonth9;
	/**
	 * 10月
	 */
	private Double amountMonth10;
	/**
	 * 11月
	 */
	private Double amountMonth11;
	/**
	 * 12月
	 */
	private Double amountMonth12;
	
	/**
	 * 差额总值
	 */
	private Double total;
	
	/**
	 * 年度合计
	 */
	private Double yearTotal;
	
	/**
	 * 转厂期限
	 */
	private Date date;
	
	/**
	 * 关封余量
	 */
	private Double remain;
	
	private String define1;
	private String define2;
	private String define3;
	private String define4;
	private String define5;
	//台达用转厂的备注来存放工厂的料号
	private String define6;
	private String define7;
	private String define8;
	private String define9;
	private String define10;

	public static String getSHOU_HUO() {
		return SHOU_HUO;
	}

	public static void setSHOU_HUO(String sHOUHUO) {
		SHOU_HUO = sHOUHUO;
	}
	

	public static String getSONG_HUO() {
		return SONG_HUO;
	}

	public static void setSONG_HUO(String song_huo) {
		SONG_HUO = song_huo;
	}

	public static String getJIE_ZHUAN() {
		return JIE_ZHUAN;
	}

	public static void setJIE_ZHUAN(String jIEZHUAN) {
		JIE_ZHUAN = jIEZHUAN;
	}

	public static String getCHA_E() {
		return CHA_E;
	}

	public static void setCHA_E(String cHAE) {
		CHA_E = cHAE;
	}

	public static String getGUAN_FENG() {
		return GUAN_FENG;
	}

	public static void setGUAN_FENG(String gUANFENG) {
		GUAN_FENG = gUANFENG;
	}

	public String getCustomerName() {
		return customerName;
//		return getScmCoc().getName();
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmountFirst() {
		return amountFirst;
	}

	public void setAmountFirst(Double amountFirst) {
		this.amountFirst = amountFirst;
	}

	public Double getAmountMonth1() {
		return amountMonth1;
	}

	public void setAmountMonth1(Double amountMonth1) {
		this.amountMonth1 = amountMonth1;
	}

	public Double getAmountMonth2() {
		return amountMonth2;
	}

	public void setAmountMonth2(Double amountMonth2) {
		this.amountMonth2 = amountMonth2;
	}

	public Double getAmountMonth3() {
		return amountMonth3;
	}

	public void setAmountMonth3(Double amountMonth3) {
		this.amountMonth3 = amountMonth3;
	}

	public Double getAmountMonth4() {
		return amountMonth4;
	}

	public void setAmountMonth4(Double amountMonth4) {
		this.amountMonth4 = amountMonth4;
	}

	public Double getAmountMonth5() {
		return amountMonth5;
	}

	public void setAmountMonth5(Double amountMonth5) {
		this.amountMonth5 = amountMonth5;
	}

	public Double getAmountMonth6() {
		return amountMonth6;
	}

	public void setAmountMonth6(Double amountMonth6) {
		this.amountMonth6 = amountMonth6;
	}

	public Double getAmountMonth7() {
		return amountMonth7;
	}

	public void setAmountMonth7(Double amountMonth7) {
		this.amountMonth7 = amountMonth7;
	}

	public Double getAmountMonth8() {
		return amountMonth8;
	}

	public void setAmountMonth8(Double amountMonth8) {
		this.amountMonth8 = amountMonth8;
	}

	public Double getAmountMonth9() {
		return amountMonth9;
	}

	public void setAmountMonth9(Double amountMonth9) {
		this.amountMonth9 = amountMonth9;
	}

	public Double getAmountMonth10() {
		return amountMonth10;
	}

	public void setAmountMonth10(Double amountMonth10) {
		this.amountMonth10 = amountMonth10;
	}

	public Double getAmountMonth11() {
		return amountMonth11;
	}

	public void setAmountMonth11(Double amountMonth11) {
		this.amountMonth11 = amountMonth11;
	}

	public Double getAmountMonth12() {
		return amountMonth12;
	}

	public void setAmountMonth12(Double amountMonth12) {
		this.amountMonth12 = amountMonth12;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getYearTotal() {
		return yearTotal;
	}

	public void setYearTotal(Double yearTotal) {
		this.yearTotal = yearTotal;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getRemain() {
		return remain;
	}

	public void setRemain(Double remain) {
		this.remain = remain;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CarryBalance clone(){
		try {
			return (CarryBalance) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void init() {
		customerName = "";
		customerCode = "";
		name = "";
		spec = "";
		unitName = "";
		
		amountMonth1 = 0.0;
		amountMonth2 = 0.0;
		amountMonth3 = 0.0;
		amountMonth4 = 0.0;
		amountMonth5 = 0.0;
		amountMonth6 = 0.0;
		amountMonth7 = 0.0;
		amountMonth8 = 0.0;
		amountMonth9 = 0.0;
		amountMonth10 = 0.0;
		amountMonth11 = 0.0;
		amountMonth12 = 0.0;
		yearTotal = 0.0;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getDefine1() {
		return define1;
	}

	public void setDefine1(String define1) {
		this.define1 = define1;
	}

	public String getDefine2() {
		return define2;
	}

	public void setDefine2(String define2) {
		this.define2 = define2;
	}

	public String getDefine3() {
		return define3;
	}

	public void setDefine3(String define3) {
		this.define3 = define3;
	}

	public String getDefine4() {
		return define4;
	}

	public void setDefine4(String define4) {
		this.define4 = define4;
	}

	public String getDefine5() {
		return define5;
	}

	public void setDefine5(String define5) {
		this.define5 = define5;
	}

	public String getDefine6() {
		return define6;
	}

	public void setDefine6(String define6) {
		this.define6 = define6;
	}

	public String getDefine7() {
		return define7;
	}

	public void setDefine7(String define7) {
		this.define7 = define7;
	}

	public String getDefine8() {
		return define8;
	}

	public void setDefine8(String define8) {
		this.define8 = define8;
	}

	public String getDefine9() {
		return define9;
	}

	public void setDefine9(String define9) {
		this.define9 = define9;
	}

	public String getDefine10() {
		return define10;
	}

	public void setDefine10(String define10) {
		this.define10 = define10;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}


	
	
	
}
