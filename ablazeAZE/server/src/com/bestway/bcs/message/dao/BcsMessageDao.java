package com.bestway.bcs.message.dao;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.message.entity.BcsMessageSend;
import com.bestway.bcs.message.entity.BcsReceiptResult;
import com.bestway.bcs.message.entity.BcsReceiptResultDetail;
import com.bestway.common.message.dao.CspMessageDao;
import com.bestway.common.message.entity.CspSignInfo;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BcsMessageDao extends CspMessageDao {

	@Override
	public Class getMessageSendClass() {
		return BcsMessageSend.class;
	}

	@Override
	public Class getParameterSetClass() {
		return BcsParameterSet.class;
	}

	@Override
	public Class getReceiptResultClass() {
		return BcsReceiptResult.class;
	}

	@Override
	public Class getReceiptResultDetailClass() {
		return BcsReceiptResultDetail.class;
	}

	@Override
	public Class getSignInfoClass() {
		return CspSignInfo.class;
	}

}
