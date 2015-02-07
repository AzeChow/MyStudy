package com.bestway.common.outsource.authority;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

public interface OutsourceAuthority {

	// -----------------
	// 委外明细报表
	// -----------------

	void checkOutsourceDetailReportByBrowse(Request request);

	// -----------------
	// 特殊控制
	// -----------------

	void checkSpecialByBrower(Request request);

	// -----------------
	// 委外统计报表
	// -----------------

	void checkOutsourceStatReportByBrower(Request request);
}
