package com.bestway.bcus.cas.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//财务统计报表
@AuthorityClassAnnotation(caption = "海关帐", index = 14)
public class CasFinanceReportActionImpl extends BaseActionImpl implements
        CasFinanceReportAction{
	
	// 原材料明细帐 1
    // 边角料明细帐 2
    // 产成品明细帐 3
    // 残次品明细帐 4
    // 销售收入统计表 5
    // 资产负债表 6
    // 存货统计表 7
    // 利润表 8
    // 盘点表 9
    // 现金流量表 10
	
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--原材料明细帐", index = 4.1)
    public void check1ByBrower(Request request){
        
    }

    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--边角料明细帐", index = 4.2)
    public void check2ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--产成品明细帐", index = 4.3)
    public void check3ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--残次品明细帐", index = 4.4)
    public void check4ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--销售收入统计表", index = 4.5)
    public void check5ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--资产负债表", index = 4.6)
    public void check6ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--存货统计表", index = 4.7)
    public void check7ByBrower(Request request){
        
    }    
    
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--利润表", index = 4.8)
    public void check8ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--盘点表", index = 4.9)
    public void check9ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "海关财务报表--现金流量表", index = 4.10)
    public void check10ByBrower(Request request){
        
    }
}
