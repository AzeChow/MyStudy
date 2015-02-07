package com.bestway.bcus.client.enc;

import com.bestway.client.custom.common.FmBaseImpExpRequestBill;
import com.bestway.common.constant.ProjectType;

public class FmBcusImpExpRequestBill extends FmBaseImpExpRequestBill {
	
	public FmBcusImpExpRequestBill() {
		super();
		projectType=ProjectType.BCUS;
	}

}
