package com.bestway.bcs.verification.action;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * 核查分析权限接口
 * @author xc
 *
 */
public interface VFVerificationAuthority {
	
	public void checkSection(Request request); 
	
	public void checkDeclarationCommInfoImg(Request request);	
	public void checkDeclarationCommInfoExg(Request request);	
	public void checkCustomsCountImg(Request request);	
	public void checkCustomsCountExg(Request request);	
	public void checkCustomsCountExgConvert(Request request);	
	public void checkContractAnalyse(Request request);
	
	public void checkStockImg(Request request);
	public void checkStockHalfExg(Request request);	
	public void checkStockExg(Request request);
	public void checkStockOutSendImg(Request request);
	public void checkStockOutSendSemiExg(Request request);
	public void checkStockOutSendExg(Request request);
	public void checkStockOutSendAnalyse(Request request);
	public void checkStockSemiExgHadStore(Request request);
	public void checkStockOutFactoryImg(Request request);
	public void checkStockBuyImg(Request request);
	public void checkStockTravelingImg(Request request);
	public void checkStockTravelingExg(Request request);
	public void checkStockAnalyse(Request request);
	
	public void checkTransferDiffImg(Request request);
	public void checkTransferDiffExg(Request request);
	public void checkTransferDiffCount(Request request);
	
	public void checkAnalyse(Request request);
	
}
