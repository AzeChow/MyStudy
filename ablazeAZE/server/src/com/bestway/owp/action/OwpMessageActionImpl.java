/*
 * Created on 2004-8-7
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.owp.action;

import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.message.action.CspMessageActionImpl;
import com.bestway.owp.dao.OwpMessageDao;
import com.bestway.owp.logic.OwpMessageLogic;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
//报文查询管理
@AuthorityClassAnnotation(caption = "转厂管理", index = 12)
public class OwpMessageActionImpl extends CspMessageActionImpl implements
		OwpMessageAction {
	
	
	private OwpMessageDao owpMessageDao;
	
	private OwpMessageLogic owpMessageLogic;
	
	
	public OwpMessageDao getOwpMessageDao() {
		return owpMessageDao;
	}

	public void setOwpMessageDao(OwpMessageDao owpMessageDao) {
		this.owpMessageDao = owpMessageDao;
	}

	public OwpMessageLogic getOwpMessageLogic() {
		return owpMessageLogic;
	}

	public void setOwpMessageLogic(OwpMessageLogic owpMessageLogic) {
		this.owpMessageLogic = owpMessageLogic;
	}

	/**
	 * 获取报文格式
	 * 
	 * @param sysType
	 * @param messageFileName
	 * @return
	 */
	public String getFormatFileNameByType(Request request, String sysType) {
		return ((OwpMessageLogic) this.getCspMessageLogic())
				.getFormatFileNameByType(sysType);
	}

//	/**
//	 * 获取申请单/收送货/退货报文的转入转出标志
//	 * 
//	 * @param messageFileName
//	 * @return
//	 */
//	public String getInOutFlag(Request request, String businessType,
//			String messageFileName) {
//		return ((OwpMessageLogic) this.getCspMessageLogic()).getInOutFlag(
//				businessType, messageFileName);
//	}
}
