package com.bestway.dec.qp.action;

import java.util.List;

public interface DecQpAction {
	/**
	 * 下载出口报关单
	 */
	List download_E_Dec(String pwd, String ownerCode, String beginDate,
			String endDate);

	/**
	 * 下载进口报关单
	 */
	List download_I_Dec(String pwd, String ownerCode, String beginDate,
			String endDate);
}
