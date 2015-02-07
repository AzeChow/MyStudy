/*
 * Created on 2005-4-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProjectType {
	/**
	 * 电子帐册
	 */
	public final static int BCUS = 0;

	/**
	 * 电子化手册
	 */
	public final static int BCS = 1;

//	public final static int DZBA = 2;
	/**
	 * 电子手册
	 */
	public final static int DZSC = 3;
	
	public static final String getNote(int value) {
		switch (value) {
		case 0:
			return "电子帐册";
		case 1:
			return "电子化手册";
		case 2:
			return "电子备案";
		case 3:
			return "电子手册";
		}
		return "";
	}
	
}
