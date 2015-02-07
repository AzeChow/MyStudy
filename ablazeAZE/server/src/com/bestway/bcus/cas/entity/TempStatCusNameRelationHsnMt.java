/*
 * Created on 2005-10-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

/**
 * @author ls
 * 
 */
public class TempStatCusNameRelationHsnMt implements Serializable {
	/**
	 *实际报关商品
	 */
    StatCusNameRelationHsn statCusNameRelationHsn = null;
    /**
     * 工厂物料
	 */
    StatCusNameRelationMt statCusNameRelationMt = null;
    /**
	 * 取得实际报关商品的内容
	 * @return statCusNameRelationHsn 实际报关商品
	 */
    public StatCusNameRelationHsn getStatCusNameRelationHsn() {
        return statCusNameRelationHsn;
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
	 * 取得工厂物料内容
	 * @return statCusNameRelationMt 工厂物料
	 */
    public StatCusNameRelationMt getStatCusNameRelationMt() {
        return statCusNameRelationMt;
    }
    /**
	 * 设置工厂物料内容
	 * @param statCusNameRelationMt  工厂物料
	 */
    public void setStatCusNameRelationMt(
            StatCusNameRelationMt statCusNameRelationMt) {
        this.statCusNameRelationMt = statCusNameRelationMt;
    }
}
