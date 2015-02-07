/*
 * Created on 2004-8-7
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.action;

import java.util.List;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.fpt.dao.FptMessageDao;
import com.bestway.common.fpt.logic.FptMessageLogic;
import com.bestway.common.message.action.CspMessageActionImpl;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
//报文查询管理
@AuthorityClassAnnotation(caption = "深加工结转", index = 30)
public class FptMessageActionImpl extends CspMessageActionImpl implements
		FptMessageAction {
	/**
	 * 获取报文格式
	 * 
	 * @param sysType
	 * @param messageFileName
	 * @return
	 */
	public String getFormatFileNameByType(Request request, String sysType,
			String messageFileName) {
		return ((FptMessageLogic) this.getCspMessageLogic())
				.getFormatFileNameByType(sysType, messageFileName);
	}

	/**
	 * 获取申请单/收送货/退货报文的转入转出标志
	 * 
	 * @param messageFileName
	 * @return
	 */
	public String getInOutFlag(Request request, String businessType,
			String messageFileName) {
		return ((FptMessageLogic) this.getCspMessageLogic()).getInOutFlag(
				businessType, messageFileName);
	}

	@Override
	public List httpDownload(Request request,String copEmsNo) {
		return ((FptMessageLogic) this.getCspMessageLogic()).httpDownload(copEmsNo);
	}

	@Override
	public int httpUpload(Request request) {
		return ((FptMessageLogic) this.getCspMessageLogic()).httpUpload();
	}
	
	/**
	 * 读卡加签测试
	 * @param signContent
	 * @return
	 */
	public String testReadCard(Request request,String signContent){
		return ((FptMessageLogic) this.getCspMessageLogic()).testReadCard(signContent);
	}
	
	 /**
     * 查询回执信息
     *
     * @return
     */
    public List findFptReceiptResultByCopNo(Request request,String ediType,String inOutFlag,String copNo) {
    	return ((FptMessageDao)this.getCspMessageDao()).findFptReceiptResultByCopNo(ediType,inOutFlag, copNo);
    }
}
