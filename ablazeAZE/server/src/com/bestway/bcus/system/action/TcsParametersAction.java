package com.bestway.bcus.system.action;

import com.bestway.bcus.system.entity.TcsLinkMan;
import com.bestway.bcus.system.entity.TcsParameters;
import com.bestway.common.Request;

public interface TcsParametersAction {
	TcsParameters getTcsParameters(Request request);

	void saveParameters(Request request, TcsParameters tcsParameters);

	TcsLinkMan getTcsLinkMan(Request request);

	void saveLinkMan(Request request, TcsLinkMan tcsLinkMan);
}
