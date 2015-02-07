package com.bestway.common.outsource.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

@AuthorityClassAnnotation(caption = "委外管理", index = 13)
public class OutsourceAuthorityImpl extends BaseActionImpl implements
		OutsourceAuthority {

	// -----------------
	// 委外明细报表
	// -----------------

	@AuthorityFunctionAnnotation(caption = "委外明细报表", index = 1)
	public void checkOutsourceDetailReportByBrowse(Request request) {

	}

	
	// -----------------
	// 特殊控制
	// -----------------

	@AuthorityFunctionAnnotation(caption = "特殊控制", index = 2)
	public void checkSpecialByBrower(Request request) {

	}

	// -----------------
	// 委外统计报表
	// -----------------

	@AuthorityFunctionAnnotation(caption = "委外统计报表", index = 3)
	public void checkOutsourceStatReportByBrower(Request request) {

	}

	
}
