package com.bestway.bcus.checkstock.action;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

@AuthorityClassAnnotation(caption="盘点核查（账册）",index=19)
public class ECSCheckStockAuthorityImpl  extends BaseActionImpl implements ECSCheckStockAuthority{
	
	@AuthorityFunctionAnnotation(caption="盘点核销批次设定",index=19.1)
	public void checkSection(Request request){}
	
	@AuthorityFunctionAnnotation(caption="帐册数据分析-料件情况统计表",index=19.201)
	public void checkCustomsCountImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="帐册数据分析-成品情况统计表",index=19.202)
	public void checkDeclarationCommInfoExg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="帐册数据分析-账册分析",index=19.203)
	public void checkContractAnalyse(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-料件库存",index=19.301)
	public void checkStockImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-在产品库存",index=19.302)
	public void checkStockProcessImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-成品库存",index=19.303)
	public void checkStockExg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-外发库存",index=19.304)
	public void checkStockOutSendExg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-厂外库存",index=19.305)
	public void checkStockOutFactoryImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-内购库存",index=19.306)
	public void checkStockBuyImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-在途库存",index=19.307)
	public void checkStockTravelingImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="盘点数据分析-盘点总库存",index=19.308)
	public void checkStockAnalyse(Request request){}
	
	@AuthorityFunctionAnnotation(caption="残次品库存原材料",index=19.3091)
	public void checkBadImg(Request request){};
	
	@AuthorityFunctionAnnotation(caption="残次品库存半成品",index=19.3092)
	public void checkSemiFinishedExg(Request request){};
	
	@AuthorityFunctionAnnotation(caption="残次品库存成品",index=19.3093)
	public void checkFinishedExg(Request request){};
	
	@AuthorityFunctionAnnotation(caption="残次品库存汇总",index=19.3094)
	public void checkBadStock(Request request){};
	
	@AuthorityFunctionAnnotation(caption="在制品库存原材料",index=19.310)
	public void checkFinishingImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="在制品库存成品",index=19.311)
	public void checkFinishingExg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="在制品库存汇总",index=19.312)
	public void checkFinishingStock(Request request){}
	
	@AuthorityFunctionAnnotation(caption="外发库存原材料",index=19.320)
	public void checkStockOutSendImg(Request request) {}

	@AuthorityFunctionAnnotation(caption="外发库存成品",index=19.321)
	public void checkStockOutSendExgPt(Request request) {}

	@AuthorityFunctionAnnotation(caption="外发库存半成品",index=19.322)
	public void checkStockOutSendSemiExgPt(Request request) {}

	@AuthorityFunctionAnnotation(caption="外发库存汇总",index=19.323)
	public void checkStockOutSendAnalyse(Request request) {}
	
	@AuthorityFunctionAnnotation(caption="半成品库存（已入库)",index=19.330)
	public void checkECSStockSemiExgPtHadStore(Request request){}
	
	@AuthorityFunctionAnnotation(caption="呆滞品库存原材料",index=19.340)
	public void checkStagnateImg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="呆滞品库存成品",index=19.341)
	public void checkStagnateExg(Request request){}
	
	@AuthorityFunctionAnnotation(caption="呆滞品库存汇总",index=19.342)
	public void checkStagnateStock(Request request){}
	
	@AuthorityFunctionAnnotation(caption="结转数据分析-料件结转差额表",index=19.401)
	public void checkTransferImgBalance(Request request){}
	
	@AuthorityFunctionAnnotation(caption="结转数据分析-成品结转差额表",index=19.402)
	public void checkTransferExgBalance(Request request){}

	@AuthorityFunctionAnnotation(caption="结转数据分析-结转差额总表",index=19.403)
	public void checkTransferCollectBalance(Request request){}
	
	@AuthorityFunctionAnnotation(caption="短溢分析",index=19.5)
	public void checkAnalyse(Request request){}

}
