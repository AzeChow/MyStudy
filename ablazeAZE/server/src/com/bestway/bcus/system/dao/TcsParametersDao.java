package com.bestway.bcus.system.dao;

import com.bestway.bcus.system.entity.TcsLinkMan;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.common.BaseDao;

public class TcsParametersDao extends BaseDao {

	public TcsParameters getTcsParameters() {
		return (TcsParameters) createQuery("from TcsParameters").uniqueResult();
	}
	
	public TcsLinkMan getTcsLinkMan(){
		TcsLinkMan tcsLinkMan = (TcsLinkMan) createQuery("from TcsLinkMan").uniqueResult();
		return tcsLinkMan;
	}
}
