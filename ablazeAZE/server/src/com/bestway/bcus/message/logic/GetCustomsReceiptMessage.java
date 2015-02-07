/*
 * Created on 2004-8-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

import java.util.List;


/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GetCustomsReceiptMessage {
	private List lines;
	
	public GetCustomsReceiptMessage(List messageLines){	
		this.lines = messageLines;
	}
	/**
	 * 得到统一编号
	 * @return
	 */
	public String getUniteCode() {
	    String sTemp = (String) lines.get(3); 
	    try{
		    return new String(sTemp.getBytes(), 22, 18);
	    } catch (Exception e){
	    	return "";
	    }
	}
	/**
	 * 得到报关单号
	 * @return
	 */
	public String getDeclaraCode(){
		String sTemp = (String) lines.get(3); 
		try{
		   return new String(sTemp.getBytes(), 22+18, 18);
		} catch (Exception e) {
		   return "";
		}
	}
	/**
	 * 得到通知日期
	 * @return
	 */
	public String getNoticeDate(){
		String sTemp = (String) lines.get(3); 
		try{
		    return new String(sTemp.getBytes(), 22+18+18, 17);
		} catch (Exception e){
			return "";
		}
	}	
	/**
	 * 得到回执标记
	 * @return
	 */
	public String getReturnSign(){
		String sTemp = (String) lines.get(3); 
		try{
		    return new String(sTemp.getBytes(), 22+18+18+17, 1);
		} catch (Exception e){
			return "";
		}
	}
	/**
	 * 得到回执备注
	 * @return
	 */
	public String getReturnNote(){
		String sTemp = (String) lines.get(3); 
		try{
		    return new String(sTemp.getBytes(), 22+18+18+17+1, sTemp.getBytes().length-(22+18+18+17+1));
		} catch (Exception e){
			return "";
		}
	}
    public String getMessageCode(){
    	String sTemp = (String) lines.get(0);
    	try{
    	    return new String(sTemp.getBytes(),3,3);
    	} catch (Exception e){
    		return "";
    	}
    }
	/**
	 * @return Returns the lines.
	 */
	public List getLines() {
		return lines;
	}

}
