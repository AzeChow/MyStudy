package com.bestway.bls.entity;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseScmEntity;

public class BlsInOutStockSwichParameterSet extends BaseScmEntity {

	/**
	 * 进出仓标志(String)
	 * com.bestway.bls.entity.BlsIOStockBillIOF 
	 * 入仓 IMPORT = "I"
	 * 出仓 EXPORT = "O"
	 */
	private String ioFlag = null;

	/**
     * 报送海关
     */
    private Customs declarationCustoms; 
    
    /**
	 * 包装种类
	 */
    private Wrap wrapType; 
    
    /**
	 *  进、出口岸
	 */
    private Customs customs;
    
    /**
	 * 仓单类型(String) （00-申报初始库存，01-后报关方式 02-先报关分批送货方式03-特殊审核）
	 * 
	 * @uml.property name="billType"
	 */
	private String billType;
	
	/**
	 * 帐册编号(String) 电子账册编号或底账编号
	 * 
	 * @uml.property name="emsNo"
	 */
	private String emsNo;
	
	/**
	 * 获取进出仓标志(String)
	 * @return
	 */
	public String getIoFlag() {
		return ioFlag;
	}

	/**
	 * 设置进出仓标志(String)
	 * @param ioFlag
	 */
	public void setIoFlag(String ioFlag) {
		this.ioFlag = ioFlag;
	}

	/**
	 * 获取报送海关
	 * @return
	 */
	public Customs getDeclarationCustoms() {
		return declarationCustoms;
	}

	/**
	 * 设置报送海关
	 * @param declarationCustoms
	 */
	public void setDeclarationCustoms(Customs declarationCustoms) {
		this.declarationCustoms = declarationCustoms;
	}

	/**
	 * 获取包装种类
	 * @return
	 */
	public Wrap getWrapType() {
		return wrapType;
	}

	/**
	 * 设置包装种类
	 * @param wrapType
	 */
	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}

	/**
	 * 获取进、出口岸
	 * @return
	 */
	public Customs getCustoms() {
		return customs;
	}

	/**
	 * 设置进、出口岸
	 * @param customs
	 */
	public void setCustoms(Customs customs) {
		this.customs = customs;
	}

	/**
	 * 获取仓单类型(String) （00-申报初始库存，01-后报关方式 02-先报关分批送货方式03-特殊审核）
	 * @return
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * 设置仓单类型(String) （00-申报初始库存，01-后报关方式 02-先报关分批送货方式03-特殊审核）
	 * @param billType
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * 获取帐册编号
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置帐册编号
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
}
