/*
 * Created on 2005-4-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProjectTypeParam implements Serializable{
	
    private Boolean isBcus = false;
    private Boolean isDzsc = false;
    private Boolean isBcs = false;
    
    public Boolean getIsBcs() {
        return isBcs;
    }
    public void setIsBcs(Boolean isBcs) {
        this.isBcs = isBcs;
    }
    public Boolean getIsBcus() {
        return isBcus;
    }
    public void setIsBcus(Boolean isBcus) {
        this.isBcus = isBcus;
    }
    public Boolean getIsDzsc() {
        return isDzsc;
    }
    public void setIsDzsc(Boolean isDzsc) {
        this.isDzsc = isDzsc;
    }
    
    
    
}
