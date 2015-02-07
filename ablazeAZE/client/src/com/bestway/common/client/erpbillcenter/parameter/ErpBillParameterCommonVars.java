/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.parameter;

import java.util.Calendar;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingOption;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.cas.parameterset.entity.LotControl;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.cas.parameterset.entity.WriteAccountYear;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ErpBillParameterCommonVars {
	private static CasBillOption			casBillOption				= null;
	private static LotControl				lotControl					= null;

	

	/**
	 * @param casBillOption
	 *            The casBillOption to set.
	 */
	public static void setCasBillOption(CasBillOption casBillOption) {
		ErpBillParameterCommonVars.casBillOption = casBillOption;
	}

	/**
	 * @param lotControl
	 *            The lotControl to set.
	 */
	public static void setLotControl(LotControl lotControl) {
		ErpBillParameterCommonVars.lotControl = lotControl;
	}

	/**
	 * 获得海关帐基本单据
	 * 
	 * @return
	 */
	public static CasBillOption getCasBillOption() {
		if(casBillOption==null){
			casBillOption=new CasBillOption();
		}
		return casBillOption;
	}

	

	/**
	 * 获得制单号对象
	 * 
	 * @return
	 */
	public static LotControl getLotControl() {
		return lotControl;
	}

}
