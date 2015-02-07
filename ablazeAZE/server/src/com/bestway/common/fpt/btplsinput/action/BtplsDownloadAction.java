package com.bestway.common.fpt.btplsinput.action;

import java.util.List;
import java.util.Map;

import com.bestway.common.Request;

public interface BtplsDownloadAction {
	/*
	 * 下载报关单表体信息
	 */
	List downloadCustomsDeclaration(Request request, Map<String, Object> parameter);
	
	/**
	 * 下载申请表表体信息
	 */
	List downloadBtplsFptAppBody(Request req,Map<String, Object> parameter);
	/**
	 * 下载收发货表体信息
	 */
	List downloadMessageIndentureBody(Request req,Map<String, Object> parameter);
}
