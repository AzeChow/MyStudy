package com.bestway.bcus.cas.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//其它报表
@AuthorityClassAnnotation(caption = "海关帐", index = 14)
public class CasOtherReportActionImpl extends BaseActionImpl implements CasOtherReportAction{
	

	@AuthorityFunctionAnnotation(caption = "其他报表--边角料查询报表", index = 5.1)
	public void checkCalLeftoverMaterielByBrowse(Request request){
		
	}
	
    @AuthorityFunctionAnnotation(caption = "其他报表--转厂差额明细表", index = 5.2)
    public void checkAuthorityTranFactoryMarginByBrowse(Request request) {
    }
    
    @AuthorityFunctionAnnotation(caption = "其他报表--减免税设备清单", index = 5.3)
    public void checkFixtureNotInTaxation(Request request) {
    }
    
    @AuthorityFunctionAnnotation(caption = "其他报表--征税税设备清单", index = 5.4)
    public void checkFixtureInTaxation(Request request) {
    }
    @AuthorityFunctionAnnotation(caption = "其他报表--收/送货明细表", index = 5.5)
    public void checkMiRecvSendDetailInfo(Request request) {
    }
    @AuthorityFunctionAnnotation(caption = "其他报表--结转对照表", index = 5.6)
    public void checkTransFactCompareInfo(Request request) {
    }
    
}
