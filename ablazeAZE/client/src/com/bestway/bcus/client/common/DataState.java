/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

/**
 * @author bsway
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DataState{
	public static final int BROWSE=0;//浏览状态
	public static final int ADD=1;//新增状态
	public static final int EDIT=2;//编辑状态
	public static final int READONLY=3;//只读状态
	public static final int CHANGE=4;//变更状态
	
	
    public static String getNameByCode(int value) {
        String desc = "";
        if (DataState.BROWSE==value) {
            desc = "查看";
        } else if (DataState.ADD==value) {
            desc = "新增";
        } else if (DataState.EDIT==value) {
            desc = "修改";
        } else if (DataState.READONLY==value) {
            desc = "只读状态";
        } else if (DataState.CHANGE==value) {
            desc = "变更状态";
        }
        return desc;
    }

    public static int getCodeByName(String value) {
        int status = DataState.BROWSE;
        if ("查看".equals(value)) {
            status = DataState.BROWSE;
        } else if ("新增".equals(value)) {
            status = DataState.ADD;
        } else if ("修改".equals(value)) {
            status = DataState.EDIT;
        } else if ("只读状态".equals(value)) {
            status = DataState.READONLY;
        } else if ("变更状态".equals(value)) {
            status = DataState.CHANGE;
        }
        return status;
    }
}
