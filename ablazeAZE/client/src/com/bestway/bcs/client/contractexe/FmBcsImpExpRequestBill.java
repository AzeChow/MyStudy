package com.bestway.bcs.client.contractexe;

import com.bestway.client.custom.common.FmBaseImpExpRequestBill;
import com.bestway.common.constant.ProjectType;

public class FmBcsImpExpRequestBill extends FmBaseImpExpRequestBill {
	
	public FmBcsImpExpRequestBill() {
		super();
		projectType=ProjectType.BCS;
	}

}
