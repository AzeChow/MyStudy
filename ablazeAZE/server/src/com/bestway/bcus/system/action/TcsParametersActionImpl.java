package com.bestway.bcus.system.action;

import com.bestway.bcus.system.entity.TcsLinkMan;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.bcus.system.logic.TcsParametersLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;

public class TcsParametersActionImpl extends BaseActionImpl implements TcsParametersAction {
	private TcsParametersLogic tcsParametersLogic;

	public void setTcsParametersLogic(TcsParametersLogic tcsParametersLogic) {
		this.tcsParametersLogic = tcsParametersLogic;
	}
	
	public TcsParameters getTcsParameters(Request request) {
		return tcsParametersLogic.getTcsParameters();
	}
	
	public TcsLinkMan getTcsLinkMan(Request request) {
		return tcsParametersLogic.getTcsLinkMan();
	}
	
	public void saveParameters(Request request, TcsParameters tcsParameters) {
		tcsParametersLogic.saveParams(tcsParameters);
	}

	public void saveLinkMan(Request request, TcsLinkMan tcsLinkMan) {
		tcsParametersLogic.saveLinkMan(tcsLinkMan);
	}
}
