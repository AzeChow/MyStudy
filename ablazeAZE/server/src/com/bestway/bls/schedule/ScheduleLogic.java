package com.bestway.bls.schedule;

import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.BlsCheckCancelDao;
import com.bestway.bls.dao.BlsMessageDao;
import com.bestway.bls.dao.EntranceMessageDao;
import com.bestway.bls.dao.StorageBillDao;
import com.bestway.bls.entity.BlsInOutFlag;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.common.constant.DeclareState;

public class ScheduleLogic {
	private BlsMessageDao blsMessageDao = null;
	
	private StorageBillDao storageBillDao = null;
	
	private EntranceMessageDao entranceMessageDao = null;
	
	private BlsCheckCancelDao blsCheckCancelDao = null;
	
	private ScheduleSingleLogic scheduleSingleLogic=null;

	public ScheduleSingleLogic getScheduleSingleLogic() {
		return scheduleSingleLogic;
	}

	public void setScheduleSingleLogic(ScheduleSingleLogic scheduleSingleLogic) {
		this.scheduleSingleLogic = scheduleSingleLogic;
	}

	public BlsMessageDao getBlsMessageDao() {
		return blsMessageDao;
	}

	public void setBlsMessageDao(BlsMessageDao blsMessageDao) {
		this.blsMessageDao = blsMessageDao;
	}

	public StorageBillDao getStorageBillDao() {
		return storageBillDao;
	}

	public void setStorageBillDao(StorageBillDao storageBillDao) {
		this.storageBillDao = storageBillDao;
	}

	public EntranceMessageDao getEntranceMessageDao() {
		return entranceMessageDao;
	}

	public void setEntranceMessageDao(EntranceMessageDao entranceMessageDao) {
		this.entranceMessageDao = entranceMessageDao;
	}

	public BlsCheckCancelDao getBlsCheckCancelDao() {
		return blsCheckCancelDao;
	}

	public void setBlsCheckCancelDao(BlsCheckCancelDao blsCheckCancelDao) {
		this.blsCheckCancelDao = blsCheckCancelDao;
	}

	public boolean getIsAutoDeclare(Company company) {
		return scheduleSingleLogic.getIsAutoDeclare(company);
	}

	public boolean getIsAutoProcess(Company company) {
		return scheduleSingleLogic.getIsAutoProcess(company);
	}

	public String getInCronExpressionFromDB(Company company) {
		return scheduleSingleLogic.getInCronExpressionFromDB(company);
	}

	public String getOutCronExpressionFromDB(Company company) {
		return scheduleSingleLogic.getOutCronExpressionFromDB(company);
	}

	public String getCobCronExpressionFromDB(Company company) {
		return scheduleSingleLogic.getCobCronExpressionFromDB(company);
	}

	/**
	 * 自动海关申报入仓单
	 * 
	 * @param company
	 */
	public void allAutoDeclareInDelivery(Company company) {
		List listDelivery = this.storageBillDao.findDelivery(company, BlsInOutFlag.IN,
				true, DeclareState.APPLY_POR);
		for (int i = 0; i < listDelivery.size(); i++) {
			Delivery delivery = (Delivery) listDelivery.get(i);
			this.scheduleSingleLogic.singleAutoDeclareInDelivery(company, delivery);
		}
	}

	/**
	 * 自动处理回执入仓单
	 * 
	 * @param company
	 */
	public void allAutoProcessInDelivery(Company company) {
		List listDelivery = this.storageBillDao.findDelivery(company, BlsInOutFlag.IN,
				true, DeclareState.WAIT_EAA);
		for (int i = 0; i < listDelivery.size(); i++) {
			Delivery delivery = (Delivery) listDelivery.get(i);
			this.scheduleSingleLogic.singleAutoProcessInDelivery(company, delivery);
		}
	}
	
	/**
	 * 自动海关申报出仓单
	 * 
	 * @param company
	 */
	public void allAutoDeclareOutDelivery(Company company) {
		List listDelivery = this.storageBillDao.findDelivery(company, BlsInOutFlag.OUT,
				true, DeclareState.APPLY_POR);
		for (int i = 0; i < listDelivery.size(); i++) {
			Delivery delivery = (Delivery) listDelivery.get(i);
			this.scheduleSingleLogic.singleAutoDeclareOutDelivery(company, delivery);
		}
	}

	/**
	 * 自动处理回执出仓单
	 * 
	 * @param company
	 */
	public void allAutoProcessOutDelivery(Company company) {
		List listDelivery = this.storageBillDao.findDelivery(company, BlsInOutFlag.OUT,
				true, DeclareState.WAIT_EAA);
		for (int i = 0; i < listDelivery.size(); i++) {
			Delivery delivery = (Delivery) listDelivery.get(i);
			this.scheduleSingleLogic.singleAutoProcessOutDelivery(company, delivery);
		}
	}

	/**
	 * 自动海关申报入仓货到报告
	 * 
	 * @param company
	 */
	public void allAutoDeclareInEntranceMessage(Company company) {
		List listEntranceMessage = this.entranceMessageDao.findEntranceMessage(
				company, BlsInOutFlag.IN, DeclareState.APPLY_POR);
		for (int i = 0; i < listEntranceMessage.size(); i++) {
			EntranceMessage entranceMessage = (EntranceMessage) listEntranceMessage
					.get(i);
			this.scheduleSingleLogic.singleAutoDeclareInEntranceMessage(company, entranceMessage);
		}
	}

	/**
	 * 自动处理回执入仓货到报告
	 * 
	 * @param company
	 */
	public void allAutoProcessInEntranceMessage(Company company) {
		List listEntranceMessage = this.entranceMessageDao.findEntranceMessage(
				company, BlsInOutFlag.IN, DeclareState.WAIT_EAA);
		for (int i = 0; i < listEntranceMessage.size(); i++) {
			EntranceMessage entranceMessage = (EntranceMessage) listEntranceMessage
					.get(i);
			this.scheduleSingleLogic.singleAutoProcessInEntranceMessage(company, entranceMessage);
		}
	}

	/**
	 * 自动海关申报出仓货到报告
	 * 
	 * @param company
	 */
	public void allAutoDeclareOutEntranceMessage(Company company) {
		List listEntranceMessage = this.entranceMessageDao.findEntranceMessage(
				company, BlsInOutFlag.OUT, DeclareState.APPLY_POR);
		for (int i = 0; i < listEntranceMessage.size(); i++) {
			EntranceMessage entranceMessage = (EntranceMessage) listEntranceMessage
					.get(i);
			this.scheduleSingleLogic.singleAutoDeclareOutEntranceMessage(company, entranceMessage);
		}
	}

	/**
	 * 自动处理回执出仓货到报告
	 * 
	 * @param company
	 */
	public void allAutoProcessOutEntranceMessage(Company company) {
		List listEntranceMessage = this.entranceMessageDao.findEntranceMessage(
				company, BlsInOutFlag.OUT, DeclareState.WAIT_EAA);
		for (int i = 0; i < listEntranceMessage.size(); i++) {
			EntranceMessage entranceMessage = (EntranceMessage) listEntranceMessage
					.get(i);
			this.scheduleSingleLogic.singleAutoProcessOutEntranceMessage(company, entranceMessage);
		}
	}


	/**
	 * 自动海关申报捆绑核销
	 * 
	 * @param company
	 */
	public void allAutoDeclareCollateBind(Company company) {
		System.out.println("##################自动申报捆绑核销：");
		List listCollateBind = this.blsCheckCancelDao.findCollateBind(company,
				DeclareState.APPLY_POR);
		System.out.println("------------------------自动申报捆绑核销个数："
				+ listCollateBind.size());
		for (int i = 0; i < listCollateBind.size(); i++) {
			CollateBind collateBind = (CollateBind) listCollateBind.get(i);
			this.scheduleSingleLogic.singleAutoDeclareCollateBind(company, collateBind);
		}
	}

	/**
	 * 自动处理回执出仓货到报告
	 * 
	 * @param company
	 */
	public void allAutoProcessCollateBind(Company company) {
		System.out.println("+++++++++++++++++++++++自动处理捆绑核销：");
		List listCollateBind = this.blsCheckCancelDao.findCollateBind(company,
				DeclareState.WAIT_EAA);
		for (int i = 0; i < listCollateBind.size(); i++) {
			CollateBind collateBind = (CollateBind) listCollateBind.get(i);
			this.scheduleSingleLogic.singleAutoProcessCollateBind(company, collateBind);
		}
	}
}
