package com.bestway.common.erpbillcenter.authority;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

@AuthorityClassAnnotation(caption = "单据中心", index = 4)
public class ErpBillCenterAuthorityImpl extends BaseActionImpl implements
		ErpBillCenterAuthority {

	// -----------------
	// 单据中心参数设置
	// -----------------

	@AuthorityFunctionAnnotation(caption = "参数设置--浏览", index = 1)
	public void checkErpBillCenterParameterByBrowse(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "参数设置--修改", index = 2)
	public void checkErpBillCenterParameterByUpdate(Request request) {

	}
	
	@AuthorityFunctionAnnotation(caption = "实际报关资料--浏览", index = 2.1)
	public void checkFactualCustoms(Request request) {
		// TODO Auto-generated method stub
		
	}

	// -----------------
	// 商品大类关系对应
	// -----------------

	@AuthorityFunctionAnnotation(caption = "对应关系", index = 3)
	public void checkRelationByBrower(Request request) {

	}

	// -----------------
	// 工厂单据类型
	// -----------------

	@AuthorityFunctionAnnotation(caption = "工厂单据类型", index = 4)
	public void checkBillTypeByBrower(Request request) {

	}

	// -----------------
	// 单据
	// -----------------

	@AuthorityFunctionAnnotation(caption = "工厂单据管理--浏览", index = 5)
	public void checkBillByBrower(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "工厂单据管理--保存", index = 6)
	public void checkBillByUpdate(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "工厂单据管理--删除", index = 7)
	public void checkBillByDelete(Request request) {
	}
	@AuthorityFunctionAnnotation(caption = "订单管理", index = 7.1)
	public void checkCustomOrder(Request request) {
		// TODO Auto-generated method stub
		
	}
	
	// -----------------
	// 特殊控制(单据中心)
	// -----------------

	@AuthorityFunctionAnnotation(caption = "特殊控制--浏览", index = 8)
	public void checkByBrower(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "特殊控制--大批量删除或回卷或生效单据", index = 8.1)
	public void check1ByBrower(Request request){
		
	}

	
	@AuthorityFunctionAnnotation(caption = "特殊控制--财务成批记帐", index = 8.2)
	public void check2ByBrower(Request request){
		
	}
    
    @AuthorityFunctionAnnotation(caption = "特殊控制--修改单据单价", index = 8.3)
    public void check3ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "特殊控制--单据对应", index = 8.4)
    public void check4ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "特殊控制--批量修改“工厂商品资料”对应报关商品资料", index = 8.5)
    public void check5ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "特殊控制--报关单和单据对应查询", index = 8.6)
    public void check6ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "特殊控制--报关单和单据对应查询", index = 8.7)
    public void check7ByBrower(Request request){
        
    }    
    
    
    @AuthorityFunctionAnnotation(caption = "特殊控制--生成单据的折算报关数量", index = 8.8)
    public void check8ByBrower(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "特殊控制--半成品委外管理", index = 8.9)
    public void check9ByBrower(Request request){
        
    }

}
