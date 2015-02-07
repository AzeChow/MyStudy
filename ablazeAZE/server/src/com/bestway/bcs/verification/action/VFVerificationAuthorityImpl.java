package com.bestway.bcs.verification.action;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
@AuthorityClassAnnotation(caption="核查分析（手册）",index = 18)
public class VFVerificationAuthorityImpl extends BaseActionImpl implements VFVerificationAuthority{

	@AuthorityFunctionAnnotation(caption="核查批次设定",index=18.1)
	public void checkSection(Request request){} 
	
	@AuthorityFunctionAnnotation(caption="合同数据分析-料件报关明细表",index=18.201)
	public void checkDeclarationCommInfoImg(Request request){}	
	@AuthorityFunctionAnnotation(caption="合同数据分析-成品报关明细表",index=18.202)
	public void checkDeclarationCommInfoExg(Request request){}	
	@AuthorityFunctionAnnotation(caption="合同数据分析-料件数据统计表",index=18.203)
	public void checkCustomsCountImg(Request request){}	
	@AuthorityFunctionAnnotation(caption="合同数据分析-成品数据统计表",index=18.204)
	public void checkCustomsCountExg(Request request){}	
	@AuthorityFunctionAnnotation(caption="合同数据分析-成品折算统计表",index=18.205)
	public void checkCustomsCountExgConvert(Request request){}	
	@AuthorityFunctionAnnotation(caption="合同数据分析-合同分析",index=18.206)
	public void checkContractAnalyse(Request request){}
	
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-料件库存",index=18.301)
	public void checkStockImg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-在产品库存",index=18.302)
	public void checkStockHalfExg(Request request){}	
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-成品库存",index=18.303)
	public void checkStockExg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-外发库存原材料",index=18.304)
	public void checkStockOutSendImg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-外发库存半成品",index=18.305)
	public void checkStockOutSendSemiExg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-外发库存成品",index=18.306)
	public void checkStockOutSendExg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-外发库存汇总",index=18.307)
	public void checkStockOutSendAnalyse(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-半成品库存(已入库)",index=18.308)
	public void checkStockSemiExgHadStore(Request request){}
	
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-厂外库存",index=18.309)
	public void checkStockOutFactoryImg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-内购库存",index=18.310)
	public void checkStockBuyImg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-在途库存料件",index=18.311)
	public void checkStockTravelingImg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-在途库存成品",index=18.313)
	public void checkStockTravelingExg(Request request){}
	@AuthorityFunctionAnnotation(caption="工厂盘点数据分析-工厂库存汇总表",index=18.312)
	public void checkStockAnalyse(Request request){}
	
	@AuthorityFunctionAnnotation(caption="结转数据分析-料件结转差额表",index=18.401)
	public void checkTransferDiffImg(Request request){}
	@AuthorityFunctionAnnotation(caption="结转数据分析-成品结转差额表",index=18.402)
	public void checkTransferDiffExg(Request request){}
	@AuthorityFunctionAnnotation(caption="结转数据分析-结转差额汇总表",index=18.402)
	public void checkTransferDiffCount(Request request){}
	
	@AuthorityFunctionAnnotation(caption="短溢分析",index=18.5)
	public void checkAnalyse(Request request){}
}
