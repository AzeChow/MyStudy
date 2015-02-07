/*
 * Created on 2004-7-5
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 * 

 */
package com.bestway.bcus.checkcancel.action;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;


/**
 * @author 001
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates implements
 */
//核查核销
@AuthorityClassAnnotation(caption = "电子帐册", index = 6)
public class CheckCancelAuthorityActionImpl extends BaseActionImpl implements
        CheckCancelAuthorityAction {   
    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-浏览", index = 4.1)
	    public void brownCancelOwner(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-新增", index = 4.1)
	    public void addCancelOwner(Request request){        
	    }	
	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-保存", index = 4.1)
	    public void saveCancelOwner(Request request){        
	    }	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-删除", index = 4.1)
	    public void deleteCancelOwner(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-获取数据报核", index = 4.1)
	    public void cancelOwnerGetData(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-核销计算", index = 4.1)
	    public void cancelOwnerCancelCount(Request request){        
	    }	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-同步核销表", index = 4.1)
	    public void cancelOwnerCancel(Request request){        
	    }	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-新增报关单", index = 4.1)
	    public void cancelOwnerNewCustoms(Request request){        
	    }	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-移除报关单", index = 4.1)
	    public void cancelOwnerDeleteCustoms(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-获取核销报关单", index = 4.1)
	    public void cancelOwnerGetCustoms(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-修改料件", index = 4.1)
	    public void cancelOwnerModityLj(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-料件重新获取", index = 4.1)
	    public void cancelOwnerGetLj(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-修改成品", index = 4.1)
	    public void cancelOwnerModifyCp(Request request){        
	    }	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--自用核销-成品重新获取", index = 4.1)
	    public void cancelOwnerGetCp(Request request){        
	    }  
	    
	    
	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-浏览", index = 4.2)
	    public void brownDataCancel(Request request){        
	    }	   
	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-新增", index = 4.2)
	    public void addDataCancel(Request request){        
	    }	 
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-保存", index = 4.2)
	    public void saveDataCancel(Request request){        
	    }	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-删除", index = 4.2)
	    public void deleteDataCancel(Request request){        
	    }	    
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-正式报核", index = 4.2)
	    public void dataCancelDueData(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-海关申报", index = 4.2)
	    public void dataCancelCustoms(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-回执处理", index = 4.2)
	    public void dataCancelDealReturn(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-核销计算", index = 4.2)
	    public void dataCancelCancelData(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-新增报关单", index = 4.2)
	    public void dataCancelAddCustoms(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-移除报关单", index = 4.2)
	    public void dataCancelDeleteCustoms(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-获取核销报关单", index = 4.2)
	    public void dataCancelGetCustoms(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-修改料件", index = 4.2)
	    public void dataCancelModityLj(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-料件重新获取", index = 4.2)
	    public void dataCancelGetLj(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-修改成品", index = 4.2)
	    public void dataCancelModityCp(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-财务审核", index = 4.2)
	    public void financialAudit(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--数据报核-成品重新获取", index = 4.2)
	    public void dataCancelGetCp(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--帐帐分析-浏览", index = 4.3)
	    public void analyseBrown(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--帐帐分析-修改", index = 4.3)
	    public void analyseModity(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--帐帐分析-删除", index = 4.3)
	    public void analyseDelete(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--帐帐分析-报关数据分析", index = 4.3)
	    public void analyseData(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--帐帐分析-盘点数据分析", index = 4.3)
	    public void analysePd(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "滚动核销--帐帐分析-差异分析", index = 4.3)
	    public void analyseCy(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "中期核查--成品折算料件", index = 5.5)
	    public void findAllFactoryStoryProduct(Request request){        
	    }
}