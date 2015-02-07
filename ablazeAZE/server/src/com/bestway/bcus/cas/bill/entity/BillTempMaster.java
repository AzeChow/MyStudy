package com.bestway.bcus.cas.bill.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BillTempMaster extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 单据表表头
     */
    private 			BillMaster        	billMaster;
    /**
     * 是否被选中
     */
    private				Boolean				isSelected			=false;
    
    /**
     * 取得单据表表头数据
     * @return billMaster 单据表表头
     */
    public BillMaster getBillMaster() {
        return billMaster;
    }

    /**
     * 设置单据表表头数据
     * @param billMaster  单据表表头
     */
    public void setBillMaster(BillMaster billMaster) {
        this.billMaster = billMaster;
    }
    
    /**
     * 判断是否被选中
     * @return isSelected  是否被选中.
     */
    public Boolean getIsSelected() {
        return isSelected;
    }

    /**
     * 设置是否选种标志
     * @param isSelected  是否被选中.
     */
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
    
//    public void setContractUnit(Double contractUnit){
//    	
//    }
    
    
    
}