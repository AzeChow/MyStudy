/*
 * Created on 2004-9-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.common.CommonUtils;

/**
 * @author
 * 打印使用
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiImgExg implements Serializable,Comparator,Comparable {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 
	 */
	private Integer imSeqNum;
	/**
	 * 
	 */
	private String imName;
	/**
	 * 
	 */
	private String imComplex;
	/**
	 * 
	 */
	private Integer exSeqNum;
	/**
	 * 
	 */
	private String exName;
	/**
	 * 
	 */
	private String exComplex;
	
	/**
	 * @return Returns the exName.
	 */
	public String getExName() {
		return exName;
	}
	/**
	 * @param exName The exName to set.
	 */
	public void setExName(String exName) {
		this.exName = exName;
	}
	/**
	 * @return Returns the exSeqNo.
	 */
	public Integer getExSeqNum() {
		return exSeqNum;
	}
	/**
	 * @param exSeqNo The exSeqNo to set.
	 */
	public void setExSeqNum(Integer exSeqNum) {
		this.exSeqNum = exSeqNum;
	}
	/**
	 * @return Returns the imComplex.
	 */
	public String getImComplex() {
		return imComplex;
	}
	/**
	 * @param imComplex The imComplex to set.
	 */
	public void setImComplex(String imComplex) {
		this.imComplex = imComplex;
	}
	/**
	 * @return Returns the imName.
	 */
	public String getImName() {
		return imName;
	}
	/**
	 * @param imName The imName to set.
	 */
	public void setImName(String imName) {
		this.imName = imName;
	}
	/**
	 * @return Returns the imSeqNo.
	 */
	public Integer getImSeqNum() {
		return imSeqNum;
	}
	/**
	 * @param imSeqNo The imSeqNo to set.
	 */
	public void setImSeqNum(Integer imSeqNum) {
		this.imSeqNum = imSeqNum;
	}
	/**
	 * @return Returns the exComplex.
	 */
	public String getExComplex() {
		return exComplex;
	}
	/**
	 * @param exComplex The exComplex to set.
	 */
	public void setExComplex(String exComplex) {
		this.exComplex = exComplex;
	}
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		try{
			Integer seqNum1 = ((Integer)PropertyUtils.getProperty(arg0,"imSeqNum"));
			Integer seqNum2 = ((Integer)PropertyUtils.getProperty(arg1,"imSeqNum"));
			return seqNum1.compareTo(seqNum2);				
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		try{
			Integer seqNum1 = this.imSeqNum;
			Integer seqNum2 = ((Integer)PropertyUtils.getProperty(arg0,"imSeqNum"));
			return seqNum1.compareTo(seqNum2);	
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

}
