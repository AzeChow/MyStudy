/*
 * Created on 2005-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempStatCusNameRelation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 商品大类
	 */
	private StatCusNameRelation statCusNameRelation;
	/**
	 * 实际报关商品
	 */
	private StatCusNameRelationHsn statCusNameRelationHsn;
	/**
	 * 企业物料
	 */
	private StatCusNameRelationMt statCusNameRelationMt;

	/**
	 * 取得商品大类的内容
	 * @return statCusNameRelation  商品大类
	 */
	public StatCusNameRelation getStatCusNameRelation() {
		return statCusNameRelation;
	}

	/**
	 * 取得实际报关商品的内容
	 * @return statCusNameRelationHsn 实际报关商品
	 */
	public StatCusNameRelationHsn getStatCusNameRelationHsn() {
		return statCusNameRelationHsn;
	}

	/**
	 * 取得工厂物料内容
	 * @return statCusNameRelationMt 工厂物料
	 */
	public StatCusNameRelationMt getStatCusNameRelationMt() {
		return statCusNameRelationMt;
	}

	/**
	 * 设置商品大类的内容
	 * @param statCusNameRelation 商品大类
	 */
	public void setStatCusNameRelation(StatCusNameRelation statCusNameRelation) {
		this.statCusNameRelation = statCusNameRelation;
	}

	/**
	 * 设置实际报关商品的内容
	 * @param statCusNameRelationHsn  实际报关商品
	 */
	public void setStatCusNameRelationHsn(
			StatCusNameRelationHsn statCusNameRelationHsn) {
		this.statCusNameRelationHsn = statCusNameRelationHsn;
	}

	/**
	 * 设置工厂物料内容
	 * @param statCusNameRelationMt  工厂物料
	 */
	public void setStatCusNameRelationMt(
			StatCusNameRelationMt statCusNameRelationMt) {
		this.statCusNameRelationMt = statCusNameRelationMt;
	}
	/**
	 * 取得报关商品序号和商品编码作为唯一键值
	 * @return seqNum+commCode 报关商品序号+商品编码
	 */
	public String getSeqNumAndCommCode(){
		String seqNum=this.statCusNameRelationHsn==null?"":this.statCusNameRelationHsn.getSeqNum().toString().trim();
		String commCode = this.statCusNameRelationHsn == null ? ""
				: this.statCusNameRelationHsn.getComplex().getCode().trim();
		return seqNum+commCode;
	}
	/* 比较传入的参数objiect是否和TempStatCusNameRelation类的实例是否相等 相等为true ，否则为false
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TempStatCusNameRelation)) {
			return false;
		}
		if (this.statCusNameRelationMt == null) {
			if (((TempStatCusNameRelation) obj).getStatCusNameRelationMt() != null) {
				return false;
			} else {
				if (this.statCusNameRelation
						.equals(((TempStatCusNameRelation) obj)
								.getStatCusNameRelation())) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			if (((TempStatCusNameRelation) obj).getStatCusNameRelationMt() == null) {
				return false;
			} else {
				if (this.statCusNameRelation
						.equals(((TempStatCusNameRelation) obj)
								.getStatCusNameRelation())
						&& this.statCusNameRelationMt
								.equals(((TempStatCusNameRelation) obj)
										.getStatCusNameRelationMt())) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
}
