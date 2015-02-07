/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.specificontrol.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;


/**
 * @author Administrator 生成单据的折算报关数量(未折算报关数量的单据) 
 *         generated type comment go to Window - Preferences - Java - Code Style -
 *         Code Templates
 */
public class TempMaterielTypeSetup implements Serializable {
    private static final long serialVersionUID = CommonUtils
                                                       .getSerialVersionUID();
    /**
     * 是否是成品类型单据
     */
    private Boolean           isProuduct       = false;
    /**
     * 是否是料件类型单据
     */
    private Boolean           isMateriel       = false;
    /**
     * 是否是设备类型单据
     */
    private Boolean           isMachine        = false;
    /**
     * 是否是半成品类型单据
     */
    private Boolean           isSemiProduct    = false;
    /**
     *是否是边角料类型单据 
     */
    private Boolean           isRemainMateriel = false;
    /**
     * 是否是残次品类型单据
     */
    private Boolean           isBadProduct     = false;
    /**
     * 是否强制（已存在折算数量的单据）生成折算报关数量
     */
    private Boolean           isMakeCustomsInfo= false;
    /**
     * 是否生成单据对应的报关资料
     */
    private Boolean           isUpdateHsAmount = false;
    
    
    /**
     * 判断是否是残次品 是为true 否则为false
     * @return isBadProduct 是否是残次品
     */
    public Boolean getIsBadProduct() {
        return isBadProduct;
    }
    /**
     * 设置是否是残次品标志
	 * @param isBadProduct 是否是残次品
	 */
    public void setIsBadProduct(Boolean isBadProduct) {
        this.isBadProduct = isBadProduct;
    }
    /**
     * 判断是否是设备 
     * @return isMachine 是否是设备 
     */
    public Boolean getIsMachine() {
        return isMachine;
    }
    /**
     * 设置是否是设备的标志
	 * @param isMachine 是否是设备 
	 */
    public void setIsMachine(Boolean isMachine) {
        this.isMachine = isMachine;
    }
    /**
     * 判断是否是料件单据
     * @return isMateriel 是否是料件单据
     */
    public Boolean getIsMateriel() {
        return isMateriel;
    }
    /**
     * 设置是否是料件单据的标志
	 * @param isMateriel 是否是料件单据
	 */
    public void setIsMateriel(Boolean isMateriel) {
        this.isMateriel = isMateriel;
    }
    /**
     * 判断是否是成品单据
     * @return isProuduct  是否是成品单据
     */
    public Boolean getIsProuduct() {
        return isProuduct;
    }
    /**
     * 设置是否是成品单据的标志
	 * @param  isProuduct 是否是成品单据
	 */
    public void setIsProuduct(Boolean isProuduct) {
        this.isProuduct = isProuduct;
    }
    /**
     * 判断是否是边角料单据
     * @return isRemainMateriel 是否是边角料单据
     */
    public Boolean getIsRemainMateriel() {
        return isRemainMateriel;
    }
    /**
     * 设置是否是边角料单据的标志
	 * @param isRemainMateriel 是否是边角料单据
	 */
    public void setIsRemainMateriel(Boolean isRemainMateriel) {
        this.isRemainMateriel = isRemainMateriel;
    }
    /**
     * 判断是否是半成品单据
     * @return isSemiProduct 是否是残次品单据
     */
    public Boolean getIsSemiProduct() {
        return isSemiProduct;
    }
    /**
     * 设置是否是半成品单据的标志
	 * @param isSemiProduct 是否是半成品单据
	 */
    public void setIsSemiProduct(Boolean isSemiProduct) {
        this.isSemiProduct = isSemiProduct;
    }
    /**
     * 取得是否生成单据对应的报关资料
     * @return isMakeCustomsInfo  是否生成单据对应的报关资料
     */
    public Boolean getIsMakeCustomsInfo() {
        return isMakeCustomsInfo;
    }
    /**
     * 设置是否生成单据对应的报关资料的标志
	 * @param isMakeCustomsInfo  是否生成单据对应的报关资料
	 */
    public void setIsMakeCustomsInfo(Boolean isMakeCustomsInfo) {
        this.isMakeCustomsInfo = isMakeCustomsInfo;
    }
    /**
     * 取得是否强制（已存在折算数量的单据）生成折算报关数量的标志
     * @return isUpdateHsAmount  是否强制（已存在折算数量的单据）生成折算报关数量 
     */
    public Boolean getIsUpdateHsAmount() {
        return isUpdateHsAmount;
    }
    /**
     * 设置是否强制（已存在折算数量的单据）生成折算报关数量的标志
	 * @param isUpdateHsAmount  是否强制（已存在折算数量的单据）生成折算报关数量
	 */
    public void setIsUpdateHsAmount(Boolean isUpdateHsAmount) {
        this.isUpdateHsAmount = isUpdateHsAmount;
    }
    
    

}
