package com.bestway.bcus.checkstock.action;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;


public interface ECSCheckStockAuthority {
	
	public void checkSection(Request request);
	
	public void checkCustomsCountImg(Request request);
	
	public void checkDeclarationCommInfoExg(Request request);
	
	public void checkContractAnalyse(Request request);
	
	public void checkStockImg(Request request);
	
	public void checkStockProcessImg(Request request);
	
	public void checkStockExg(Request request);
	
	public void checkStockOutSendExg(Request request);
	
	public void checkStockOutFactoryImg(Request request);
	
	public void checkStockBuyImg(Request request);
	
	public void checkStockTravelingImg(Request request);
	
	public void checkStockAnalyse(Request request);
	
	public void checkTransferImgBalance(Request request);
	
	public void checkTransferExgBalance(Request request);

	public void checkTransferCollectBalance(Request request);
	
	public void checkAnalyse(Request request);
	
	public void checkBadImg(Request request);
	
	public void checkSemiFinishedExg(Request request);
	
	public void checkFinishedExg(Request request);
	
	public void checkBadStock(Request request);
	
	public void checkFinishingImg(Request request);
	
	public void checkFinishingExg(Request request);
	
	public void checkFinishingStock(Request request);
	
	public void checkStockOutSendImg(Request request);
	
	public void checkStockOutSendExgPt(Request request);
	
	public void checkStockOutSendSemiExgPt(Request request);
	
	public void checkStockOutSendAnalyse(Request request);
	
	public void checkECSStockSemiExgPtHadStore(Request request);
	
	public void checkStagnateImg(Request request);
	
	public void checkStagnateExg(Request request);
	
	public void checkStagnateStock(Request request);
	
}
