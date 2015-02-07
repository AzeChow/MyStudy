package com.bestway.bcus.financial.entity;

import com.bestway.common.BaseScmEntity;


public class TempMonthlyProductionAndSales extends BaseScmEntity {
	private MonthlyProductionAndSales entity = new MonthlyProductionAndSales();
	
	private String errinfo;


	public MonthlyProductionAndSales getEntity() {
		return entity;
	}

	public void setEntity(MonthlyProductionAndSales entity) {
		this.entity = entity;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}
