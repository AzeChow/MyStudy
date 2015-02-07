package com.bestway.bls.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.EntranceMessageDao;
import com.bestway.bls.entity.BlsEntranceMessageResultType;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;

/**
 * 货到报告Logic类
 * 
 * @author hw
 * 
 */
public class EntranceMessageLogic {
	/**
	 * 货到报告Dao类
	 */
	private EntranceMessageDao entranceMessageDao = null;
	/**
	 * 生成发送报文类
	 */
	private BlsMessageLogic blsMessageLogic;

	/**
	 * 得到货到报告Dao
	 * 
	 * @return
	 */
	public EntranceMessageDao getEntranceMessageDao() {
		return entranceMessageDao;
	}

	/**
	 * 设置货到报告Dao
	 * 
	 * @param entranceMessageDao
	 */
	public void setEntranceMessageDao(EntranceMessageDao entranceMessageDao) {
		this.entranceMessageDao = entranceMessageDao;
	}

	public BlsMessageLogic getBlsMessageLogic() {
		return blsMessageLogic;
	}

	public void setBlsMessageLogic(BlsMessageLogic blsMessageLogic) {
		this.blsMessageLogic = blsMessageLogic;
	}

	/**
	 * 导入车次信息
	 * 
	 * @param deliveryList
	 *            是Delivery型，车次信息
	 * 
	 * @return List 是Delivery 型
	 */
	public List importDelivery(List deliveryList) {
		List<EntranceMessage> returnList = new ArrayList<EntranceMessage>();
		for (int i = 0; i < deliveryList.size(); i++) {
			Delivery delivery = (Delivery) deliveryList.get(i);

			// materiel.setIsUseInBlsInnerMerge(true);
			this.entranceMessageDao.saveOrUpdate(delivery);
			EntranceMessage entranceMessage = new EntranceMessage();
			entranceMessage.setCarrierCode(delivery.getCarrierCode());
			entranceMessage.setDeliveryNo(delivery.getDeliveryNo());
			entranceMessage.setNetWeight(delivery.getNetWeight());
			entranceMessage.setGrossWeight(delivery.getGrossWeight());
			entranceMessage.setBillCount(delivery.getBillCount());
			entranceMessage.setCarrierName(delivery.getCarrierName());
			entranceMessage.setContainerNo1(delivery.getContainerNo1());
			entranceMessage.setContainerNo2(delivery.getContainerNo2());
			entranceMessage.setSealNo1(delivery.getSealNo1());
			entranceMessage.setSealNo2(delivery.getSealNo2());
			entranceMessage.setTradeCo(delivery.getTradeCo());
			entranceMessage.setIoFlag(delivery.getInOut());
			entranceMessage.setDeclareState(DeclareState.APPLY_POR);
			entranceMessage.setVehicleLicense(delivery.getVehicleLicense());
			this.entranceMessageDao.saveEntranceMessage(entranceMessage);
			returnList.add(entranceMessage);
		}
		return returnList;
	}

	/**
	 * 车到报告海关申报
	 */
	public EntranceMessage applyFreightage(EntranceMessage entranceMessage) {
		// 系统类型，比如车次，车到，或者核销
		String serviceType = BlsServiceType.FREIGHTAGE_DECLARE;
		// 关键值
		String keyCode = entranceMessage.getDeliveryNo();
		// 生成报文查询值参数
		Map<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("id", entranceMessage.getId());
		// 海关申报
		BlsReceiptResult blsReceiptResult = this.blsMessageLogic
				.declareMessage(serviceType, entranceMessage.getId(), keyCode,
						queryValues, null, null,(Company) entranceMessage.getCompany());
		String declareState = DeclareState.WAIT_EAA;
		if (BlsEntranceMessageResultType.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsEntranceMessageResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsEntranceMessageResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		System.out.println("declareState="+declareState);
		entranceMessage.setDeclareState(declareState);
		this.entranceMessageDao.saveOrUpdate(entranceMessage);
		return entranceMessage;
	}

	/**
	 * 车到报告申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	public EntranceMessage processFreightage(EntranceMessage entranceMessage,
			BlsReceiptResult blsReceiptResult) {
		String declareState = DeclareState.WAIT_EAA;
		if (BlsEntranceMessageResultType.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsEntranceMessageResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsEntranceMessageResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		entranceMessage.setDeclareState(declareState);
		this.entranceMessageDao.saveOrUpdate(entranceMessage);
		this.blsMessageLogic.processMessage(blsReceiptResult);
		return entranceMessage;
	}

	/**
	 * 过滤掉货到报告中已有的车次信息
	 */
	public List findAndFiltrateDeliveryNo(String ioFlag) {
		List<String> entranceMessageList = this.entranceMessageDao
				.findDeliveryNoFromEntranceMessage(ioFlag);
		List deliveryList = this.entranceMessageDao.findProcessDelivery(ioFlag);
		for (int j = deliveryList.size() - 1; j >= 0; j--) {
			Delivery d = (Delivery) deliveryList.get(j);
			String deliveryNo = d.getDeliveryNo();
			if (entranceMessageList.contains(deliveryNo)) {
				deliveryList.remove(j);
			}
		}
		return deliveryList;
	}
	// list2 = this.entranceMessageDao
	// .findDeliveryByDeliveryNos((String) list.get(i));
	// System.out.println("list3.size()=" + list3.size());
}
