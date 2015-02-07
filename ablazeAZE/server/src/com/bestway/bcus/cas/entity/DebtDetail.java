/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 资产负债表明细
 * @author luosheng  checked 2008-11-29
 */
public class DebtDetail extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * 资产负债表表头
	 */
	private DebtMaster debtMaster;
	/**
	 *项目1（资产）
	 */
	private String item1;       
	/**
	 * 行次1
	 */
	private String times1;       
	/**
	 * 年初数1
	 */
	private Double beginValue1;  
	/**
	 * 期末数1
	 */
	private Double endValue1; 
	
	
	/**
	 *项目2（负债及所有者权益)
	 */
	private String item2;        
	/**
	 * 行次2
	 */
	private String times2;       
	/**
	 * 年初数2
	 */
	private Double beginValue2;  
	/**
	 * 期末数2
	 */
	private Double endValue2;    


	
	/**
	 * 取得年初数1的内容
	 * @return beginValue1 年初数1.
	 */
	public Double getBeginValue1() {
		return beginValue1;
	}
	/**
	 * 设置年初数1
	 * @param beginValue1 年初数1.
	 */
	public void setBeginValue1(Double beginValue1) {
		this.beginValue1 = beginValue1;
	}
	/**
	 * 取得年初数2的内容
	 * @return beginValue2 年初数2.
	 */
	public Double getBeginValue2() {
		return beginValue2;
	}
	/**
	 * 设置年初数2
	 * @param beginValue2 年初数2.
	 */
	public void setBeginValue2(Double beginValue2) {
		this.beginValue2 = beginValue2;
	}
	/**
	 * 取得资产负债表表头
	 * @return debtMaster 资产负债表表头.
	 */
	public DebtMaster getDebtMaster() {
		return debtMaster;
	}
	/**
	 * 设置资产负债表表头
	 * @param debtMaster 资产负债表表头.
	 */
	public void setDebtMaster(DebtMaster debtMaster) {
		this.debtMaster = debtMaster;
	}
	/**
	 * 取得期末数1
	 * @return endValue1 期末数1.
	 */
	public Double getEndValue1() {
		return endValue1;
	}
	/**
	 * 设置期末数1
	 * @param endValue1 期末数1.
	 */
	public void setEndValue1(Double endValue1) {
		this.endValue1 = endValue1;
	}
	/**
	 * 取得期末数2
	 * @return endValue2 期末数2.
	 */
	public Double getEndValue2() {
		return endValue2;
	}
	/**
	 * 设置期末数2
	 * @param endValue2 期末数2.
	 */
	public void setEndValue2(Double endValue2) {
		this.endValue2 = endValue2;
	}
	/**
	 * 取得项目1（资产）
	 * @return item1 项目1（资产）.
	 */
	public String getItem1() {
		return item1;
	}
	/**
	 * 设置项目1（资产）
	 * @param item1 项目1（资产）.
	 */
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	/**
	 * 取得项目2（负债及所有者权益）
	 * @return item2 项目2（负债及所有者权益）.
	 */
	public String getItem2() {
		return item2;
	}
	/**
	 * 设置项目2（负债及所有者权益）
	 * @param item2 项目2（负债及所有者权益）.
	 */
	public void setItem2(String item2) {
		this.item2 = item2;
	}
	/**
	 * 取得行次1
	 * @return times1 行次1.
	 */
	public String getTimes1() {
		return times1;
	}
	/**
	 * 设置行次1
	 * @param times1 行次1.
	 */
	public void setTimes1(String times1) {
		this.times1 = times1;
	}
	/**
	 * 取得行次2
	 * @return times2 行次2.
	 */
	public String getTimes2() {
		return times2;
	}
	/**
	 * 设置行次2
	 * @param times2 行次2.
	 */
	public void setTimes2(String times2) {
		this.times2 = times2;
	}
}