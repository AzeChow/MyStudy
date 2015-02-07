/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 * 

 */
package com.bestway.bcus.manualdeclarereport.action;

import java.util.Date;
import java.util.List;



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
//统计报表
@AuthorityClassAnnotation(caption = "电子帐册", index = 6) 
public class ManualDeclareReportActionImpl extends BaseActionImpl implements
        ManualDeclareReportAction {    
    private EncLogic encLogic  = null;
    
    
    
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-进口料件情况统计表", index = 6.1)
    public void report1(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-出口成品情况统计表", index = 6.1)
    public void report2(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-进口料件报关登记表", index = 6.1)
    public void report3(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-出口成品报关登记表", index = 6.1)
    public void report4(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-料件流水帐", index = 6.1)
    public void report5(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-成品流水帐", index = 6.1)
    public void report6(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-进口报关清单", index = 6.1)
    public void report7(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-出口报关清单", index = 6.1)
    public void report8(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-料件耗用明细-帐册级", index = 6.1)
    public void report9(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-料件耗用明细-料号级", index = 6.1)
    public void report9_1(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-特殊报关单商品列表", index = 6.1)
    public void report10(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-进口料件耗用月报表", index = 6.1)
    public void report11(Request request){
        
    }
    
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-料件进口平衡状况汇总表", index = 6.1)
    public void report12(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-进口料件单重差异稽核表", index = 6.1)
    public void report13(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-帐册出口成品状况表", index = 6.1)
    public void report14(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-帐册进出口量平衡状况表", index = 6.1)
    public void report15(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-帐册总体进出口金额状况统计表", index = 6.1)
    public void report16(Request request){
        
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-进口料件清单情况统计表-料号级", index = 6.1)
	public void imreport17(Request request) {

		
	}
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-出口成品清单情况统计表-料号级", index = 6.1)
	public void exreport18(Request request) {
	}
    
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-大小清单查询", index = 6.1)
	public void requestTOApplyTOCustomsReport(Request request) {
		
	} 
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-清单与报关单对应表", index = 6.1)
    public void ApplyReportTOCustomsReport(Request request) {
		
	}
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-出口成品耗用料件统计表", index = 6.1)
	public void report19(Request request) {
		
	}
    
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-进/出包装统计", index = 6.1)
	public void report20(Request request) {
		
	}
    //============================
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--免抵退税管理报表-进口财务报表", index = 6.2)
	public void financialImpReport(Request request) {
		
	}
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--免抵退税管理报表-出口财务报表", index = 6.2)
	public void financialExgReport(Request request) {
		
	}
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--免抵退税管理报表-转厂出口财务报表", index = 6.2)
	public void financialTrunReport(Request request) {
		
	}
    
    /**
     * 报关单边角料统计
     * @param beginDate
     * @param endDate
     * @return
     */
    public List statRemainMaterial(Request request,Date beginDate, Date endDate) {
        return encLogic.statRemainMaterial(beginDate, endDate);
    }
    
    
    /**
     * @return Returns the encLogic.
     */
    public EncLogic getEncLogic() {
        return encLogic;
    }
    /**
     * @param encLogic The encLogic to set.
     */
    public void setEncLogic(EncLogic encLogic) {
        this.encLogic = encLogic;
    }
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--报表查询打印-浏览", index = 6.1)
	public void checkReportQueryPrintAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}
    @AuthorityFunctionAnnotation(caption = "电子帐册统计报表--免抵退税管理报表-浏览", index = 6.2)
	public void checkFinancialReportQueryAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}

	


}