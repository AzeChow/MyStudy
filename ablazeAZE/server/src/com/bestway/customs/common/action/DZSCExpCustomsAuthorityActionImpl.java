/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 * 

 */
package com.bestway.customs.common.action;

import java.util.Date;
import java.util.List;



import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.logic.EncLogic;

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
//通关--出口报关单
@AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class DZSCExpCustomsAuthorityActionImpl extends BaseActionImpl implements
DZSCExpCustomsAuthorityAction {   
    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--浏览报关单", index = 8.2)
	    public void brownCustoms(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--保存报关单", index = 8.2)
	    public void saveCustoms(Request request){        
	    }

	    @AuthorityFunctionAnnotation(caption = "出口报关单--转抄报关单", index = 8.2)
	    public void copyCustoms(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--回卷报关单", index = 8.2)
	    public void unreelCustoms(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--生效报关单", index = 8.2)
	    public void effectCustoms(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--内部商品", index = 8.2)
	    public void innerCommodity(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "出口报关单--申请单转报关单", index = 8.2)
	    public void billToBgD(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "出口报关单--数据取整", index = 8.2)
	    public void dataFetchInt(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "出口报关单--查看", index = 8.2)
	    public void lookCommodity(Request request){        
	    }
	    @AuthorityFunctionAnnotation(caption = "出口报关单--自定义排序", index = 8.2)
	    public void definedOrder(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--自动排序", index = 8.2)
	    public void autoOrder(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--流水号重排", index = 8.2)
	    public void reSortAutoOrder(Request request){
	    	
	    }
	    @AuthorityFunctionAnnotation(caption = "出口报关单--海关删单", index = 8.2)
	    public void customsDelete(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--一般作废", index = 8.2)
	    public void genericDelete(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--保存报关单商品信息", index = 8.2)
	    public void saveCommodity(Request request){        
	    }
	    
	    @AuthorityFunctionAnnotation(caption = "出口报关单--删除报关单商品信息", index = 8.2)
	    public void deleteCommodity(Request request){        
	    }

	    @AuthorityFunctionAnnotation(caption = "出口报关单--集成通申报", index = 8.2)
		public void checkAuthorityDeclarationByTcs(Request request) {
			// TODO Auto-generated method stub
			
		}
	    @AuthorityFunctionAnnotation(caption = "出口报关单--强制", index = 8.2)
		public void getExpControlCustomsDeclaretion(Request request) {
			// TODO Auto-generated method stub
			
		}

}