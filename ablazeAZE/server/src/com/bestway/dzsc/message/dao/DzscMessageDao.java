package com.bestway.dzsc.message.dao;

import com.bestway.common.message.dao.CspMessageDao;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.dzsc.message.entity.DzscMessageSend;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.dzsc.message.entity.DzscReceiptResult;
import com.bestway.dzsc.message.entity.DzscReceiptResultDetail;

/**
 * @author
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DzscMessageDao extends CspMessageDao {

	@Override
	public Class getMessageSendClass() {
		return DzscMessageSend.class;
	}

	@Override
	public Class getParameterSetClass() {
		return DzscParameterSet.class;
	}

	@Override
	public Class getReceiptResultClass() {
		return DzscReceiptResult.class;
	}

	@Override
	public Class getReceiptResultDetailClass() {
		return DzscReceiptResultDetail.class;
	}

	@Override
	public Class getSignInfoClass() {
		return CspSignInfo.class;
	}
}
