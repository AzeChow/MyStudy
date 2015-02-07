package com.bestway.bcus.cas.authority;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

public interface CasOtherReportAction {

	void checkCalLeftoverMaterielByBrowse(Request request);

	 /**
	 * 转厂差额表
	 * @param request
	 */
	 void checkAuthorityTranFactoryMarginByBrowse(Request request);

	// @AuthorityFunctionAnnotation(caption = "保税设备清单", index = 4)
	public void checkFixtureNotInTaxation(Request request);

	// @AuthorityFunctionAnnotation(caption = "征税税设备清单", index = 5)
	public void checkFixtureInTaxation(Request request);
	 /**
	 * 收/送货明细表
	 * @param request
	 */
    public void checkMiRecvSendDetailInfo(Request request);
    /**
	 * 结转对照表
	 * @param request
	 */
    public void checkTransFactCompareInfo(Request request);
}
