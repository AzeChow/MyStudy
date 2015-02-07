/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.entity;

import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放申请单转报关单中间表
 * 
 * @author ls
 */
public class MakeDzscCustomsDeclaration extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 报关单商品信息
     */
    private DzscCustomsDeclarationCommInfo dzscCustomsDeclarationCommInfo = null;
    
    /**
     * 报关申请单物料
     */
    private ImpExpCommodityInfo   impExpCommodityInfo   = null;

   

    /**
     * 获取报关申请单物料
     * 
     * @return impExpCommodityInfo 报关申请单物料
     */
    public ImpExpCommodityInfo getImpExpCommodityInfo() {
        return impExpCommodityInfo;
    }

   
    /**
     * 设置报关申请单物料
     * 
     * @param impExpCommodityInfo 报关申请单物料
     */
    public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
        this.impExpCommodityInfo = impExpCommodityInfo;
    }


	/**
	 * 获取报关单商品信息
	 * 
	 * @return dzscCustomsDeclarationCommInfo 报关单商品信息
	 */
	public DzscCustomsDeclarationCommInfo getDzscCustomsDeclarationCommInfo() {
		return dzscCustomsDeclarationCommInfo;
	}


	/**
	 * 设置报关单商品信息
	 * 
	 * @param dzscCustomsDeclarationCommInfo 报关单商品信息
	 */
	public void setDzscCustomsDeclarationCommInfo(
			DzscCustomsDeclarationCommInfo dzscCustomsDeclarationCommInfo) {
		this.dzscCustomsDeclarationCommInfo = dzscCustomsDeclarationCommInfo;
	}
}