/*
 * Created on 2004-8-23
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.action;

import java.util.List;

import com.bestway.common.Request;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface CheckCancelAuthorityAction {
    
    public void brownCancelOwner(Request request);
    public void addCancelOwner(Request request);
    public void saveCancelOwner(Request request);
    public void deleteCancelOwner(Request request);
    public void cancelOwnerGetData(Request request);
    public void cancelOwnerCancelCount(Request request);
    public void cancelOwnerCancel(Request request);
    public void cancelOwnerNewCustoms(Request request);
    public void cancelOwnerDeleteCustoms(Request request);
    public void cancelOwnerGetCustoms(Request request);
    public void cancelOwnerModityLj(Request request);
    public void cancelOwnerGetLj(Request request);
    public void cancelOwnerModifyCp(Request request);
    public void cancelOwnerGetCp(Request request);
    public void brownDataCancel(Request request);
    public void addDataCancel(Request request);
    public void saveDataCancel(Request request);
    public void deleteDataCancel(Request request);
    public void dataCancelDueData(Request request);
    public void dataCancelCustoms(Request request);
    public void dataCancelDealReturn(Request request);
    public void dataCancelCancelData(Request request);
    public void dataCancelAddCustoms(Request request);
    public void dataCancelDeleteCustoms(Request request);
    public void dataCancelGetCustoms(Request request);
    public void dataCancelModityLj(Request request);
    public void dataCancelGetLj(Request request);
    public void dataCancelModityCp(Request request);
    public void dataCancelGetCp(Request request);

    public void analyseModity(Request request);
    public void analyseDelete(Request request);
    public void analyseData(Request request);
    public void analysePd(Request request);
    public void analyseCy(Request request);
    public void analyseBrown(Request request);
    public void findAllFactoryStoryProduct(Request request);
	public void financialAudit(Request request);
    
}