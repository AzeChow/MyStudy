package com.bestway.bcs.contract.entity;

import java.io.Serializable;

/**
 * 存放合同的成品对应料件单耗表数据
 * @author Administrator
 *
 */
public class ImgExgObject implements Serializable, Comparable{
	
	private Object[][] imgObj = null;
	
	private Object[] exgObj = null;
	
	public Object[][] getImgObj() {
		return imgObj;
	}
	public void setImgObj(Object[][] imgObj) {
		this.imgObj = imgObj;
	}
	public Object[] getExgObj() {
		return exgObj;
	}
	public void setExgObj(Object[] exgObj) {
		this.exgObj = exgObj;
	}



	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
