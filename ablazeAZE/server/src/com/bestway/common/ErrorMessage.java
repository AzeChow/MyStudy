/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lxr
 */
public class ErrorMessage implements Serializable {

    /**
     * 检查时间
     */
    private Date checkDate = null;
    /**
     * 错误信息标题
     */
    private String title = null;
    /**
     * 错误信息
     */
    private String message = null;
    /**
     * 备注
     */
    private String reMark = null;
    /**
     * 允许
     */
    private Boolean isAllow = null;

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public Boolean getIsAllow() {
		return isAllow;
	}

	public void setIsAllow(Boolean isAllow) {
		this.isAllow = isAllow;
	}
}
