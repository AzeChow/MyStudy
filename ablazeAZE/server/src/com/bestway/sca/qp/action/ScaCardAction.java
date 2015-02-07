package com.bestway.sca.qp.action;

import java.util.List;

import com.bestway.bswmail.qp.entity.MailReceiveStatusInfo;
import com.bestway.bswmail.qp.entity.MailSendStatusInfo;
import com.bestway.bswmail.qp.entity.StatusInfo;

public interface ScaCardAction {
    /**
     * 
     *获取证书号 
     * 
     */
    String[] getCertNo();
	/**
	 * 获取IC卡号
	 * 
	 */
	String[] getCardID();

	/**
	 * 
	 * 进行数据加签
	 * 
	 */
	String[] signMsgData(String pwd, String msgContent);
}
