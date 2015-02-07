package com.bestway.common.fpt.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 商品单价维护
 * @author Administrator
 *
 */

public class TransCustomsCollate extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	private String scmCoc;
	
	private String scmCoc1;
	private String scmCoc2;
	private String scmCoc3;
	private String scmCoc4;
	private String scmCoc5;
	private String scmCoc6;
	private String scmcoc7;
	
	
	
	private Integer seqNum;
	private String complex;
	private String nameSpec;
	
	private String nameSpec1;
	private String nameSpec2;
	private String nameSpec3;
	private String nameSpec4;
	private String nameSpec5;
	private String nameSpec6;
	private String nameSpec7;
	
	private String unitName;
	private String type;
	
	private Double beginNum; //期初
	private Double janNum; //1
	private Double febNum; //2
	private Double marNum; //3
	private Double aprNum; //4
	private Double mayNum; //5
	private Double junNum; //6
	private Double julNum; //7
	private Double augNum; //8
	private Double sepNum; //9
	private Double octNum; //10
	private Double movNum; //11
	private Double decNum; //12
	
	private Double yearTotalNum;//本年度合计
	private Double totalNum;//总累计
	
	private String state;
	private String state1;
	
	public Double getAprNum() {
		return aprNum;
	}
	public void setAprNum(Double aprNum) {
		this.aprNum = aprNum;
	}
	public Double getAugNum() {
		return augNum;
	}
	public void setAugNum(Double augNum) {
		this.augNum = augNum;
	}
	public Double getBeginNum() {
		return beginNum;
	}
	public void setBeginNum(Double beginNum) {
		this.beginNum = beginNum;
	}
	public Double getDecNum() {
		return decNum;
	}
	public void setDecNum(Double decNum) {
		this.decNum = decNum;
	}
	public Double getFebNum() {
		return febNum;
	}
	public void setFebNum(Double febNum) {
		this.febNum = febNum;
	}
	public Double getJanNum() {
		return janNum;
	}
	public void setJanNum(Double janNum) {
		this.janNum = janNum;
	}
	public Double getJulNum() {
		return julNum;
	}
	public void setJulNum(Double julNum) {
		this.julNum = julNum;
	}
	public Double getJunNum() {
		return junNum;
	}
	public void setJunNum(Double junNum) {
		this.junNum = junNum;
	}
	public Double getMarNum() {
		return marNum;
	}
	public void setMarNum(Double marNum) {
		this.marNum = marNum;
	}
	public Double getMayNum() {
		return mayNum;
	}
	public void setMayNum(Double mayNum) {
		this.mayNum = mayNum;
	}
	public Double getMovNum() {
		return movNum;
	}
	public void setMovNum(Double movNum) {
		this.movNum = movNum;
	}
	public String getNameSpec() {
		return nameSpec;
	}
	public void setNameSpec(String nameSpec) {
		this.nameSpec = nameSpec;
	}
	public Double getOctNum() {
		return octNum;
	}
	public void setOctNum(Double octNum) {
		this.octNum = octNum;
	}
	public String getScmCoc() {
		return scmCoc;
	}
	public void setScmCoc(String scmCoc) {
		this.scmCoc = scmCoc;
	}
	public Double getSepNum() {
		return sepNum;
	}
	public void setSepNum(Double sepNum) {
		this.sepNum = sepNum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getComplex() {
		return complex;
	}
	public void setComplex(String complex) {
		this.complex = complex;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public String getScmCoc1() {
		return scmCoc1;
	}
	public void setScmCoc1(String scmCoc1) {
		this.scmCoc1 = scmCoc1;
	}
	public String getScmCoc2() {
		return scmCoc2;
	}
	public void setScmCoc2(String scmCoc2) {
		this.scmCoc2 = scmCoc2;
	}
	public String getScmCoc3() {
		return scmCoc3;
	}
	public void setScmCoc3(String scmCoc3) {
		this.scmCoc3 = scmCoc3;
	}
	public String getScmCoc4() {
		return scmCoc4;
	}
	public void setScmCoc4(String scmCoc4) {
		this.scmCoc4 = scmCoc4;
	}
	public String getScmCoc5() {
		return scmCoc5;
	}
	public void setScmCoc5(String scmCoc5) {
		this.scmCoc5 = scmCoc5;
	}
	public String getScmCoc6() {
		return scmCoc6;
	}
	public void setScmCoc6(String scmCoc6) {
		this.scmCoc6 = scmCoc6;
	}
	
	private  String substring(String str, int start, int end) {
		if (str != null && str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else if (str != null && str.getBytes().length > start){
				return new String(str.getBytes(), start, str.getBytes().length
				- start);
		}
		return "";
	}
	
	
	public void setScmCocStr(String scmCoc) {
		if (scmCoc == null || "".equals(scmCoc.trim())) {
			return;
		}
		int length = scmCoc.getBytes().length;
		if (length <= 10) {
			this.setScmCoc1(substring(scmCoc,0, length));
		} else if (length <= 20) {
			this.setScmCoc1(substring(scmCoc,0, 10));
			this.setScmCoc2(substring(scmCoc,10, length));	
		} else if (length <= 30){
			this.setScmCoc1(substring(scmCoc,0, 10));
			this.setScmCoc2(substring(scmCoc,10, 20));	
			this.setScmCoc3(substring(scmCoc,20, length));	
		} else if (length <= 40){
			this.setScmCoc1(substring(scmCoc,0, 10));
			this.setScmCoc2(substring(scmCoc,10, 20));	
			this.setScmCoc3(substring(scmCoc,20, 30));
			this.setScmCoc4(substring(scmCoc,30, length));
		}  else if (length <= 50){
			this.setScmCoc1(substring(scmCoc,0, 10));
			this.setScmCoc2(substring(scmCoc,10, 20));	
			this.setScmCoc3(substring(scmCoc,20, 30));
			this.setScmCoc4(substring(scmCoc,30, 40));
			this.setScmCoc5(substring(scmCoc,40, length));
		}  else if (length <= 60){
			this.setScmCoc1(substring(scmCoc,0, 10));
			this.setScmCoc2(substring(scmCoc,10, 20));	
			this.setScmCoc3(substring(scmCoc,20, 30));
			this.setScmCoc4(substring(scmCoc,30, 40));
			this.setScmCoc5(substring(scmCoc,40, 50));
			this.setScmCoc6(substring(scmCoc,50, length));
		}			
	}
	public String getNameSpec1() {
		return nameSpec1;
	}
	public void setNameSpec1(String nameSpec1) {
		this.nameSpec1 = nameSpec1;
	}
	public String getNameSpec2() {
		return nameSpec2;
	}
	public void setNameSpec2(String nameSpec2) {
		this.nameSpec2 = nameSpec2;
	}
	public String getNameSpec3() {
		return nameSpec3;
	}
	public void setNameSpec3(String nameSpec3) {
		this.nameSpec3 = nameSpec3;
	}
	public String getNameSpec4() {
		return nameSpec4;
	}
	public void setNameSpec4(String nameSpec4) {
		this.nameSpec4 = nameSpec4;
	}
	public String getNameSpec5() {
		return nameSpec5;
	}
	public void setNameSpec5(String nameSpec5) {
		this.nameSpec5 = nameSpec5;
	}
	public String getNameSpec6() {
		return nameSpec6;
	}
	public void setNameSpec6(String nameSpec6) {
		this.nameSpec6 = nameSpec6;
	}
			
			
	
	
	public void setNameSpecStr(String nameSpec) {
		if (nameSpec == null || "".equals(nameSpec.trim())) {
			return;
		}
		int length = nameSpec.getBytes().length;
		if (length <= 10) {
			this.setNameSpec1(substring(nameSpec,0, length));
		} else if (length <= 20) {
			this.setNameSpec1(substring(nameSpec,0, 10));
			this.setNameSpec2(substring(nameSpec,10, length));	
		} else if (length <= 30){
			this.setNameSpec1(substring(nameSpec,0, 10));
			this.setNameSpec2(substring(nameSpec,10, 20));	
			this.setNameSpec3(substring(nameSpec,20, length));	
		} else if (length <= 40){
			this.setNameSpec1(substring(nameSpec,0, 10));
			this.setNameSpec2(substring(nameSpec,10, 20));	
			this.setNameSpec3(substring(nameSpec,20, 30));
			this.setNameSpec4(substring(nameSpec,30, length));
		}  else if (length <= 50){
			this.setNameSpec1(substring(nameSpec,0, 10));
			this.setNameSpec2(substring(nameSpec,10, 20));	
			this.setNameSpec3(substring(nameSpec,20, 30));
			this.setNameSpec4(substring(nameSpec,30, 40));
			this.setNameSpec5(substring(nameSpec,40, length));
		}  else if (length <= 60){
			this.setNameSpec1(substring(nameSpec,0, 10));
			this.setNameSpec2(substring(nameSpec,10, 20));	
			this.setNameSpec3(substring(nameSpec,20, 30));
			this.setNameSpec4(substring(nameSpec,30, 40));
			this.setNameSpec5(substring(nameSpec,40, 50));
			this.setNameSpec6(substring(nameSpec,50, length));
		}			
	}
	public String getNameSpec7() {
		return nameSpec7;
	}
	public void setNameSpec7(String nameSpec7) {
		this.nameSpec7 = nameSpec7;
	}
	public String getScmcoc7() {
		return scmcoc7;
	}
	public void setScmcoc7(String scmcoc7) {
		this.scmcoc7 = scmcoc7;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState1() {
		return state1;
	}
	public void setState1(String state1) {
		this.state1 = state1;
	}
	public Double getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}
	public Double getYearTotalNum() {
		return yearTotalNum;
	}
	public void setYearTotalNum(Double yearTotalNum) {
		this.yearTotalNum = yearTotalNum;
	}
	
}
