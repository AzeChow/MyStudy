/*
 * Created on 2005-5-19
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.entity;

import java.io.Serializable;

/**
 * 存放合同的成品对应料件单耗表数据
 */
public class ContractUnitWaste implements Serializable, Comparable {

	/**
	 * 分组
	 */
	private Integer groupId = null;

	/**
	 * 料件总表序号
	 */
	private String ptNo = null;

	/**
	 * 料件名称
	 */
	private String ptName = null;
	/**
	 * 料件规格
	 */
	private String ptSpec = null;

	/**
	 * 单耗1
	 */
	private Double unitWaste1 = null;

	
	/**
	 * 成品1
	 */
	private String exg1 = null;
	
	/**
	 * 成品2
	 */
	private String exg2 = null;
	
	/**
	 * 成品3
	 */
	private String exg3 = null;

	/**
	 * 成品4
	 */
	private String exg4 = null;
	/**
	 * 成品5
	 */
	private String exg5 = null;
	
	/**
	 * 成品6
	 */
	private String exg6 = null;
	
	/**
	 * 损耗率1
	 */
	private Double wasteRate1 = null;

	/**
	 * 单耗2
	 */
	private Double unitWaste2 = null;

	/**
	 * 损耗率2
	 */
	private Double wasteRate2 = null;

	/**
	 * 单耗3
	 */
	private Double unitWaste3 = null;

	/**
	 * 损耗率3
	 */
	private Double wasteRate3 = null;

	/**
	 * 单耗4
	 */
	private Double unitWaste4 = null;

	/**
	 * 损耗率4
	 */
	private Double wasteRate4 = null;

	/**
	 * 单耗5
	 */
	private Double unitWaste5 = null;

	/**
	 * 损耗率5
	 */
	private Double wasteRate5 = null;

	/**
	 * 单耗6
	 */
	private Double unitWaste6 = null;

	/**
	 * 损耗率6
	 */
	private Double wasteRate6 = null;

	private Integer exgSeqNum = null;
	
	/**
	 * 成品1序号
	 */
	private Integer exgSeqNum1 = null;
	/**
	 * 成品2序号
	 */
	private Integer exgSeqNum2 = null;
	/**
	 * 成品3序号
	 */
	private Integer exgSeqNum3 = null;
	/**
	 * 成品4序号
	 */
	private Integer exgSeqNum4 = null;
	/**
	 * 成品5序号
	 */
	private Integer exgSeqNum5 = null;
	/**
	 * 成品6序号
	 */
	private Integer exgSeqNum6 = null;
	
	/**
	 * 成品1出口数量
	 */
	private Double amount1 = null;
	
	/**
	 * 成品2出口数量
	 */
	private Double amount2 = null;
	
	/**
	 * 成品3出口数量
	 */
	private Double amount3 = null;
	
	/**
	 * 成品4出口数量
	 */
	private Double amount4 = null;
	
	/**
	 * 成品5出口数量
	 */
	private Double amount5 = null;
	
	/**
	 * 成品6出口数量
	 */
	private Double amount6 = null;
	

	public Integer getExgSeqNum() {
		return exgSeqNum;
	}

	public void setExgSeqNum(Integer exgSeqNum) {
		this.exgSeqNum = exgSeqNum;
	}

	/**
	 * 获取料件名称
	 * 
	 * @return ptName 料件名称
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * 获取料件规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 获取料件总表序号
	 * 
	 * @return ptNo 料件总表序号
	 */
	public String getPtNo() {
		return ptNo;
	}

	/**
	 * 获取单耗1
	 * 
	 * @return unitWaste1 单耗1
	 */
	public Double getUnitWaste1() {
		return unitWaste1;
	}

	/**
	 * 获取单耗2
	 * 
	 * @return unitWaste2 单耗2
	 */
	public Double getUnitWaste2() {
		return unitWaste2;
	}

	/**
	 * 获取单耗3
	 * 
	 * @return unitWaste3 单耗3
	 */
	public Double getUnitWaste3() {
		return unitWaste3;
	}

	/**
	 * 获取单耗4
	 * 
	 * @return unitWaste4 单耗4
	 */
	public Double getUnitWaste4() {
		return unitWaste4;
	}

	/**
	 * 获取单耗5
	 * 
	 * @return unitWaste5 单耗5
	 */
	public Double getUnitWaste5() {
		return unitWaste5;
	}

	/**
	 * 获取单耗6
	 * 
	 * @return unitWaste6 单耗6
	 */
	public Double getUnitWaste6() {
		return unitWaste6;
	}

	/**
	 * 获取损耗率1
	 * 
	 * @return wasteRate1 损耗率1
	 */
	public Double getWasteRate1() {
		return wasteRate1;
	}

	/**
	 * 获取损耗率2
	 * 
	 * @return wasteRate2 损耗率2
	 */
	public Double getWasteRate2() {
		return wasteRate2;
	}

	/**
	 * 获取损耗率3
	 * 
	 * @return wasteRate3 损耗率3
	 */
	public Double getWasteRate3() {
		return wasteRate3;
	}

	/**
	 * 获取损耗率4
	 * 
	 * @return wasteRate4 损耗率4
	 */
	public Double getWasteRate4() {
		return wasteRate4;
	}

	/**
	 * 获取损耗率5
	 * 
	 * @return wasteRate5 损耗率5
	 */
	public Double getWasteRate5() {
		return wasteRate5;
	}

	/**
	 * 获取损耗率6
	 * 
	 * @return wasteRate6 损耗率6
	 */
	public Double getWasteRate6() {
		return wasteRate6;
	}

	/**
	 * 设置料件名称
	 * 
	 * @param ptName
	 *            料件名称
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 设置料件总表序号
	 * 
	 * @param ptNo
	 *            料件总表序号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	/**
	 * 设置单耗1
	 * 
	 * @param unitWaste1
	 *            单耗1
	 */
	public void setUnitWaste1(Double unitWaste1) {
		this.unitWaste1 = unitWaste1;
	}

	/**
	 * 设置单耗2
	 * 
	 * @param unitWaste2
	 *            单耗2
	 */
	public void setUnitWaste2(Double unitWaste2) {
		this.unitWaste2 = unitWaste2;
	}

	/**
	 * 设置单耗3
	 * 
	 * @param unitWaste3
	 *            单耗3
	 */
	public void setUnitWaste3(Double unitWaste3) {
		this.unitWaste3 = unitWaste3;
	}

	/**
	 * 设置单耗4
	 * 
	 * @param unitWaste4
	 *            单耗4
	 */
	public void setUnitWaste4(Double unitWaste4) {
		this.unitWaste4 = unitWaste4;
	}

	/**
	 * 设置单耗5
	 * 
	 * @param unitWaste5
	 *            单耗5
	 */
	public void setUnitWaste5(Double unitWaste5) {
		this.unitWaste5 = unitWaste5;
	}

	/**
	 * 设置单耗6
	 * 
	 * @param unitWaste6
	 *            单耗6
	 */
	public void setUnitWaste6(Double unitWaste6) {
		this.unitWaste6 = unitWaste6;
	}

	/**
	 * 设置损耗率1
	 * 
	 * @param wasteRate1
	 *            损耗率1
	 */
	public void setWasteRate1(Double wasteRate1) {
		this.wasteRate1 = wasteRate1;
	}

	/**
	 * 设置损耗率2
	 * 
	 * @param wasteRate2
	 *            损耗率2
	 */
	public void setWasteRate2(Double wasteRate2) {
		this.wasteRate2 = wasteRate2;
	}

	/**
	 * 设置损耗率3
	 * 
	 * @param wasteRate3
	 *            损耗率3
	 */
	public void setWasteRate3(Double wasteRate3) {
		this.wasteRate3 = wasteRate3;
	}

	/**
	 * 设置损耗率4
	 * 
	 * @param wasteRate4
	 *            损耗率4
	 */
	public void setWasteRate4(Double wasteRate4) {
		this.wasteRate4 = wasteRate4;
	}

	/**
	 * 设置损耗率5
	 * 
	 * @param wasteRate5
	 *            损耗率5
	 */
	public void setWasteRate5(Double wasteRate5) {
		this.wasteRate5 = wasteRate5;
	}

	/**
	 * 设置损耗率6
	 * 
	 * @param wasteRate6
	 *            损耗率6
	 */
	public void setWasteRate6(Double wasteRate6) {
		this.wasteRate6 = wasteRate6;
	}

	/**
	 * 获取分组
	 * 
	 * @return groupId 分组
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * 设置分组
	 * 
	 * @param groupId
	 *            分组
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public int compareTo(Object obj) {
		ContractUnitWaste o = (ContractUnitWaste) obj;
		if (this.exgSeqNum == null && o.getExgSeqNum() == null) {
			return 0;
		} else if (this.exgSeqNum == null && o.getExgSeqNum() != null) {
			return -1;
		} else if (this.exgSeqNum != null && o.getExgSeqNum() == null) {
			return 1;
		} else if (this.exgSeqNum > o.getExgSeqNum()) {
			return 1;
		} else if (this.exgSeqNum == o.getExgSeqNum()) {
			return 0;
		} else if (this.exgSeqNum < o.getExgSeqNum()) {
			return -1;
		}
		return -1;
	}

	public String getExg1() {
		return exg1;
	}

	public void setExg1(String exg1) {
		this.exg1 = exg1;
	}

	public String getExg2() {
		return exg2;
	}

	public void setExg2(String exg2) {
		this.exg2 = exg2;
	}

	public String getExg3() {
		return exg3;
	}

	public void setExg3(String exg3) {
		this.exg3 = exg3;
	}

	public String getExg4() {
		return exg4;
	}

	public void setExg4(String exg4) {
		this.exg4 = exg4;
	}

	public String getExg5() {
		return exg5;
	}

	public void setExg5(String exg5) {
		this.exg5 = exg5;
	}

	public String getExg6() {
		return exg6;
	}

	public void setExg6(String exg6) {
		this.exg6 = exg6;
	}

	public Integer getExgSeqNum1() {
		return exgSeqNum1;
	}

	public void setExgSeqNum1(Integer exgSeqNum1) {
		this.exgSeqNum1 = exgSeqNum1;
	}

	public Integer getExgSeqNum2() {
		return exgSeqNum2;
	}

	public void setExgSeqNum2(Integer exgSeqNum2) {
		this.exgSeqNum2 = exgSeqNum2;
	}

	public Integer getExgSeqNum3() {
		return exgSeqNum3;
	}

	public void setExgSeqNum3(Integer exgSeqNum3) {
		this.exgSeqNum3 = exgSeqNum3;
	}

	public Integer getExgSeqNum4() {
		return exgSeqNum4;
	}

	public void setExgSeqNum4(Integer exgSeqNum4) {
		this.exgSeqNum4 = exgSeqNum4;
	}

	public Integer getExgSeqNum5() {
		return exgSeqNum5;
	}

	public void setExgSeqNum5(Integer exgSeqNum5) {
		this.exgSeqNum5 = exgSeqNum5;
	}

	public Integer getExgSeqNum6() {
		return exgSeqNum6;
	}

	public void setExgSeqNum6(Integer exgSeqNum6) {
		this.exgSeqNum6 = exgSeqNum6;
	}

	public Double getAmount1() {
		if(amount1==null){
			return 0d;
		}
		return amount1;
	}

	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}

	public Double getAmount2() {
		if(amount2==null){
			return 0d;
		}
		return amount2;
	}

	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}

	public Double getAmount3() {
		if(amount3==null){
			return 0d;
		}
		return amount3;
	}

	public void setAmount3(Double amount3) {
		this.amount3 = amount3;
	}

	public Double getAmount4() {
		if(amount4==null){
			return 0d;
		}
		return amount4;
	}

	public void setAmount4(Double amount4) {
		this.amount4 = amount4;
	}

	public Double getAmount5() {
		if(amount5==null){
			return 0d;
		}
		return amount5;
	}

	public void setAmount5(Double amount5) {
		this.amount5 = amount5;
	}

	public Double getAmount6() {
		if(amount6==null){
			return 0d;
		}
		return amount6;
	}

	public void setAmount6(Double amount6) {
		this.amount6 = amount6;
	}
	
	
}
