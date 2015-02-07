package com.bestway.bcus.cas.authority;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

public interface CasParameterAction {
	void checkCasParameterByBrowse(Request request);

	void checkCasParameterByUpdate(Request request);
}
