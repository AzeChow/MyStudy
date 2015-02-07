package com.bestway.owp.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.common.CommonUtils;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.message.dao.CspMessageDao;
import com.bestway.owp.entity.OwpMessageSend;
import com.bestway.owp.entity.OwpParameterSet;
import com.bestway.owp.entity.OwpReceiptResult;
import com.bestway.owp.entity.OwpReceiptResultDetail;
import com.bestway.owp.entity.OwpSignInfo;


/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class OwpMessageDao extends CspMessageDao {

	@Override
	public Class getMessageSendClass() {
		return OwpMessageSend.class;
	}

	@Override
	public Class getParameterSetClass() {
		return OwpParameterSet.class;
	}

	@Override
	public Class getReceiptResultClass() {
		return OwpReceiptResult.class;
	}

	@Override
	public Class getReceiptResultDetailClass() {
		return OwpReceiptResultDetail.class;
	}

	@Override
	public Class getSignInfoClass() {
		return OwpSignInfo.class;
	}

//	/**
//	 * 查询收送货单据
//	 * 
//	 * @param billNo
//	 * @param inOutFlag
//	 * @param declareState
//	 * @return
//	 */
//	public FptAppHead findFptAppHead(String copNo, String inOutFlag,
//			String declareState) {
//		List list = this.find(
//				"select a from FptAppHead a where a.company.id= ? and a.outCopAppNo=? "
//						+ " and a.inOutFlag=? and a.declareState=? ",
//				new Object[] { CommonUtils.getCompany().getId(), copNo,
//						inOutFlag, declareState });
//		if (list.size() > 0) {
//			return (FptAppHead) list.get(0);
//		} else {
//			return null;
//		}
//	}

//	/**
//	 * 查找条件是申报状态的单据明细
//	 * 
//	 * @param parentId
//	 * @return
//	 */
//	public List findFptAppItem(String parentId) {
//		return this.find(
//				"select a from FptAppItem a where a.fptAppHead.id = ? "
//						+ " and a.company.id=?"
//						+ " and a.inOutFlag=a.fptAppHead.inOutFlag"
//						+ " order by a.listNo ", new Object[] { parentId,
//						CommonUtils.getCompany().getId() });
//	}

//	/**
//	 * 查询收送货单据
//	 * 
//	 * @param billNo
//	 * @param billSort
//	 * @param declareState
//	 * @return
//	 */
//	public FptBillHead findFptBillHead(String copNo, String billSort,
//			String declareState) {
//		List list = new ArrayList();
//		if (FptInOutFlag.OUT.equals(billSort)) {
//			list = this.find(
//					"select a from FptBillHead a where a.company.id= ? and a.issueCopBillNo=? "
//							+ " and a.billSort=? and a.appState=? ",
//					new Object[] { CommonUtils.getCompany().getId(), copNo,
//							billSort, declareState });
//		} else if (FptInOutFlag.IN.equals(billSort)) {
//			list = this.find(
//					"select a from FptBillHead a where a.company.id= ? and a.receiveCopBillNo=? "
//							+ " and a.billSort=? and a.appState=? ",
//					new Object[] { CommonUtils.getCompany().getId(), copNo,
//							billSort, declareState });
//		}
//		if (list.size() > 0) {
//			return (FptBillHead) list.get(0);
//		} else {
//			return null;
//		}
//	}

//	/**
//	 * 查找条件是申报状态的单据明细
//	 * 
//	 * @param parentId
//	 * @return
//	 */
//	public List findFptBillItem(String parentId) {
//		return this.find(
//				"select a from FptBillItem a where a.fptBillHead.id = ? "
//						+ " and a.company.id=?"
//						+ " and a.billSort=a.fptBillHead.billSort"
//						+ " order by a.listNo ", new Object[] { parentId,
//						CommonUtils.getCompany().getId() });
//	}
//	
	

}
