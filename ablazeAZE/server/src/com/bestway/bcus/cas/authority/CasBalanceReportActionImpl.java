package com.bestway.bcus.cas.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//平衡表
@AuthorityClassAnnotation(caption = "海关帐", index = 14)
public class CasBalanceReportActionImpl extends BaseActionImpl implements CasBalanceReportAction{
	
	
	@AuthorityFunctionAnnotation(caption = "平衡表--短溢表", index = 6.1)
	public void checkBalanceInfoByBrowse(Request request){
		
	}

	@AuthorityFunctionAnnotation(caption = "平衡表--实际盘点过程-料件级-浏览", index = 6.2)
	public void checkBalanceByBrowse(Request request){
		
	}
	@AuthorityFunctionAnnotation(caption = "平衡表--实际盘点过程-料件级-导入盘点数据", index = 6.21)
	public void checkBalanceByImport(Request request){
		
	}
	@AuthorityFunctionAnnotation(caption = "平衡表--实际盘点过程-料件级-删除", index = 6.22)
	public void checkBalanceByDelete(Request request){
		
	}
	@AuthorityFunctionAnnotation(caption = "平衡表--实际盘点过程-编码级-浏览", index = 6.3)
	public void checkBalanceOfCustomByBrowse(Request request){
		
	}
	@AuthorityFunctionAnnotation(caption = "平衡表--实际盘点过程-编码级-导入盘点数据", index = 6.31)
	public void checkBalanceOfCustomByImport(Request request){
		
	}
	@AuthorityFunctionAnnotation(caption = "平衡表--实际盘点过程-编码级-删除", index = 6.32)
	public void checkBalanceOfCustomByDelete(Request request){
		
	}
}
