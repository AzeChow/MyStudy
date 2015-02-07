package com.bestway.bcus.cas.authority;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

public interface CasBalanceReportAction {

	void checkBalanceInfoByBrowse(Request request);
	void checkBalanceByBrowse(Request request);
	void checkBalanceByImport(Request request);
	void checkBalanceByDelete(Request request);
	void checkBalanceOfCustomByBrowse(Request request);
	void checkBalanceOfCustomByDelete(Request request);
	void checkBalanceOfCustomByImport(Request request);
}
