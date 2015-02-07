package com.bestway.fixtureonorder.entity;

import java.io.Serializable;

/**
 * 临时实体类,TempFixtureCustomsDeclarCommInfo
 * 
 * @author fhz
 */
public class TempFixtureCustomsDeclarCommInfo implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;

	/**
	 * 备案序号
	 */
	private Integer seqNum;

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 规格
	 */
	private String spec;

	/**
	 * 获取备案序号
	 * 
	 * @return seqNum 备案序号
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置备案序号
	 * 
	 * @param seqNum
	 *            备案序号
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取代码
	 * 
	 * @return code 代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置代码
	 * 
	 * @param code
	 *            代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取名称
	 * 
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取规格
	 * 
	 * @return spec 规格
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置规格
	 * 
	 * @param spec
	 *            规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	public int compareTo(Object o) {
		TempFixtureCustomsDeclarCommInfo obj = (TempFixtureCustomsDeclarCommInfo) o;
		if (this.seqNum > obj.seqNum) {
			return 1;
		} else if (this.seqNum < obj.seqNum) {
			return -1;
		} else
			return 0;
	}
}
