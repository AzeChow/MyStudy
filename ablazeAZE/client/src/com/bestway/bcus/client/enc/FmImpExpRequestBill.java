package com.bestway.bcus.client.enc;

import com.bestway.client.custom.common.FmBaseImpExpRequestBill;
import com.bestway.common.constant.ProjectType;

public class FmImpExpRequestBill extends FmBaseImpExpRequestBill {
	
	public FmImpExpRequestBill() {		
		super();
		super.setProjectType(ProjectType.BCUS);		
	}

}
