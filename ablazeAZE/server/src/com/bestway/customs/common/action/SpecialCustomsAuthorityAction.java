/*
 * Created on 2004-8-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.customs.common.action;

import java.util.Date;
import java.util.List;

import com.bestway.common.Request;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface SpecialCustomsAuthorityAction {

    public void brownCustoms(Request request);
    
    public void saveCustoms(Request request);

    public void unreelCustoms(Request request);
    
    public void effectCustoms(Request request);
    
    public void innerCommodity(Request request);
    
    public void dataFetchInt(Request request);
    
    public void definedOrder(Request request);
    
    public void autoOrder(Request request);
    
    public void reSortAutoOrder(Request request);
    
    public void customsDelete(Request request);
    
    public void genericDelete(Request request);
    
    public void saveCommodity(Request request);
    
    public void deleteCommodity(Request request);
    public void copyCustoms(Request request);
    public void billToBgD(Request request);
    public void lookCommodity(Request request);

	public void checkAuthorityDeclarationByTcs(Request request);
	public void getSpecialControlCustomsDeclaretion(Request request);
}