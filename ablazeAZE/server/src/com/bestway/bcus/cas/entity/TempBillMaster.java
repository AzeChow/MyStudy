/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempBillMaster implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 工厂单据表表头 BillMaster型 
     */
    private BillMaster billMaster = null;
    /**
     * 是否选中
     */
    private Boolean    isSelected = null;
//    /**
//     * 表头中的仓库
//     */
//    private WareSet    wareSet = null;
    /**
     * 判断是否被选中
     * @return isSelected 是否被选中.
     */
    public Boolean getIsSelected() {
        return isSelected;
    }

    /**
     * 设置是否被选中标志
     * @param isSelected  是否选中
     */
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * 取得工厂单据表表头
     * @return billMaster 工厂单据表表头
     */
    public BillMaster getBillMaster() {
        return billMaster;
    }

    /**
     * 设置工厂单据表表头 BillMaster型
     * @param billMaster  工厂单据表表头
     */
    public void setBillMaster(BillMaster billMaster) {
        this.billMaster = billMaster;
    }

//	public WareSet getWareSet() {
//		return wareSet;
//	}
//
//	public void setWareSet(WareSet wareSet) {
//		this.wareSet = wareSet;
//	}

	
}