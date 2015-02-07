package com.bestway.common.fpt.entity;

import java.io.Serializable;

public class TempEnvelopComplex implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer seqNum;//备案序号
	private String code; //代码
	private String name; //名称
	private String spec; //规格
	
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
}
