package com.bestway.common.fpt.btplsinput.action;

import java.util.List;
import java.util.Map;

import com.bestway.common.Request;
import com.bestway.common.fpt.btplsinput.logic.BtplsDownloadLogic;

@SuppressWarnings("unchecked")
public class BtplsDownloadActionImpl implements BtplsDownloadAction {

	private BtplsDownloadLogic btplsDownloadLogic;

	public void setBtplsDownloadLogic(BtplsDownloadLogic btplsDownloadLogic) {
		this.btplsDownloadLogic = btplsDownloadLogic;
	}

	/**
	 * 下载报关单表体
	 */
	public List downloadCustomsDeclaration(Request request, Map<String, Object> parameter) {

		return btplsDownloadLogic.downloadCustomsDeclaration(parameter);
	}
	/**
	 * 传入参数获取远程数据
	 * 下载申请单表体 
	 */
	public List downloadBtplsFptAppBody(Request req,Map<String,Object> parameter){
		return btplsDownloadLogic.downloadBtplsFptAppBody(parameter);
	}
	/**
	 * 下载收发货表体信息
	 */
	public List downloadMessageIndentureBody(Request req,Map<String, Object> parameter){
		return btplsDownloadLogic.downloadMessageIndentureBody(parameter);
	}
}
