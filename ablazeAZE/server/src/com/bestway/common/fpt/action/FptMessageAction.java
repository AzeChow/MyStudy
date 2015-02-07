/*
 * Created on 2004-9-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.action;

import java.util.List;

import com.bestway.common.Request;
import com.bestway.common.message.action.CspMessageAction;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public interface FptMessageAction extends CspMessageAction {
	/**
	 * 获取报文格式
	 * 
	 * @param sysType
	 * @param messageFileName
	 * @return
	 */
	String getFormatFileNameByType(Request request, String sysType,
			String messageFileName);

	/**
	 * 获取申请单/收送货/退货报文的转入转出标志
	 * 
	 * @param messageFileName
	 * @return
	 */
	String getInOutFlag(Request request, String businessType,
			String messageFileName);
	
	
	/**
	 * 上传报文到远程ftp
	 * @param projectType
	 */
	int httpUpload(Request request);
	
	/**
	 * 下载报文
	 * @param projectType 项目类型
	 * @return
	 */
	List httpDownload(Request request,String copEmsNo);
	
	/**
	 * 读卡加签测试
	 * @param signContent
	 * @return
	 */
	String testReadCard(Request request,String signContent);
	
	/**
     * 查询回执信息
     *
     * @return
     */
    List findFptReceiptResultByCopNo(Request request,String ediType,String inOutFlag,String copNo) ;
}