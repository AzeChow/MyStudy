/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.CommonUtils;

/**
 * @author 
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempObject implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    private Object object = null;
    private Object object1 = null;
    private Object object2=null;
    private Object object3=null;//商品编码
    private Object object4=null;//联系人
    private Object object5=null;//联系电话
    private Object object6=null;//传真
    public Object getObject2() {
		return object2;
	}
	public void setObject2(Object object2) {
		this.object2 = object2;
	}
	public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public Object getObject1() {
        return object1;
    }
    public void setObject1(Object object1) {
        this.object1 = object1;
    }
	public Object getObject3() {
		return object3;
	}
	public void setObject3(Object object3) {
		this.object3 = object3;
	}
	public Object getObject4() {
		return object4;
	}
	public void setObject4(Object object4) {
		this.object4 = object4;
	}
	public Object getObject5() {
		return object5;
	}
	public void setObject5(Object object5) {
		this.object5 = object5;
	}
	public Object getObject6() {
		return object6;
	}
	public void setObject6(Object object6) {
		this.object6 = object6;
	}
}