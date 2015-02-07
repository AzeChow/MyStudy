package com.bestway.bcus.cas.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//特殊控制
//@AuthorityClassAnnotation(caption = "海关帐", index = 14)
public class CasSpecifControlActionImpl extends BaseActionImpl implements CasSpecifControlAction{
    
    // 大批量删除或回卷或生效单据 1
    // 财务成批记帐 2
    // 修改单据单价 3
    // 单据对应 4
    // 手工批量对应 5
    // 报关单和单据对应查询 6
    // 单据对应--PK单对应 7 
    // 生成单据的折算报关数量 8
    // 半成品委外管理 9
	
	@AuthorityFunctionAnnotation(caption = "大批量删除或回卷或生效单据", index = 1)
	public void check1ByBrower(Request request){
		
	}

	
	@AuthorityFunctionAnnotation(caption = "财务成批记帐", index = 2)
	public void check2ByBrower(Request request){
		
	}
    
    @AuthorityFunctionAnnotation(caption = "修改单据单价", index = 3)
    public void check3ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "单据对应", index = 4)
    public void check4ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "手工批量对应", index = 5)
    public void check5ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "报关单和单据对应查询", index = 6)
    public void check6ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "单据对应--PK单对应", index = 7)
    public void check7ByBrower(Request request){
        
    }    
    
    
    @AuthorityFunctionAnnotation(caption = "生成单据的折算报关数量", index = 8)
    public void check8ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "半成品委外管理", index = 9)
    public void check9ByBrower(Request request){
        
    }
}
