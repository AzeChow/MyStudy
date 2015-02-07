/*
 * Created on 2005-3-31
 * 证件对象
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

/**
 * @author 证件对象
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Certificate {
    private String code = null;
    private String name = null;

    public Certificate(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param code
     *            The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return code + name;
    }
}
