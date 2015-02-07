package com.bestway.bls.schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.BlsCheckCancelDao;
import com.bestway.bls.dao.BlsMessageDao;
import com.bestway.bls.dao.EntranceMessageDao;
import com.bestway.bls.dao.StorageBillDao;
import com.bestway.bls.entity.BlsAutoBackBillInfo;
import com.bestway.bls.entity.BlsAutoDeclareProcessInfo;
import com.bestway.bls.entity.BlsDeliveryResultType;
import com.bestway.bls.entity.BlsInOutFlag;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.bls.entity.FormType;
import com.bestway.bls.entity.TempBillReturn;
import com.bestway.bls.logic.BlsCheckCancelLogic;
import com.bestway.bls.logic.BlsMessageLogic;
import com.bestway.bls.logic.EntranceMessageLogic;
import com.bestway.bls.logic.StorageBillLogic;
import com.bestway.common.constant.DeclareState;

public class ScheduleSingleLogic {

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private BlsMessageLogic blsMessageLogic = null;
	private BlsMessageDao blsMessageDao = null;
	private StorageBillLogic storageBillLogic = null;
	private StorageBillDao storageBillDao = null;
	private EntranceMessageDao entranceMessageDao = null;
	private EntranceMessageLogic entranceMessageLogic = null;

	private BlsCheckCancelDao blsCheckCancelDao = null;
	private BlsCheckCancelLogic blsCheckCancelLogic = null;

	public BlsMessageDao getBlsMessageDao() {
		return blsMessageDao;
	}

	public void setBlsMessageDao(BlsMessageDao blsMessageDao) {
		this.blsMessageDao = blsMessageDao;
	}

	public StorageBillLogic getStorageBillLogic() {
		return storageBillLogic;
	}

	public void setStorageBillLogic(StorageBillLogic storageBillLogic) {
		this.storageBillLogic = storageBillLogic;
	}

	public StorageBillDao getStorageBillDao() {
		return storageBillDao;
	}

	public void setStorageBillDao(StorageBillDao storageBillDao) {
		this.storageBillDao = storageBillDao;
	}

	public BlsMessageLogic getBlsMessageLogic() {
		return blsMessageLogic;
	}

	public void setBlsMessageLogic(BlsMessageLogic blsMessageLogic) {
		this.blsMessageLogic = blsMessageLogic;
	}

	public EntranceMessageDao getEntranceMessageDao() {
		return entranceMessageDao;
	}

	public void setEntranceMessageDao(EntranceMessageDao entranceMessageDao) {
		this.entranceMessageDao = entranceMessageDao;
	}

	public EntranceMessageLogic getEntranceMessageLogic() {
		return entranceMessageLogic;
	}

	public void setEntranceMessageLogic(
			EntranceMessageLogic entranceMessageLogic) {
		this.entranceMessageLogic = entranceMessageLogic;
	}

	public BlsCheckCancelDao getBlsCheckCancelDao() {
		return blsCheckCancelDao;
	}

	public void setBlsCheckCancelDao(BlsCheckCancelDao blsCheckCancelDao) {
		this.blsCheckCancelDao = blsCheckCancelDao;
	}

	public BlsCheckCancelLogic getBlsCheckCancelLogic() {
		return blsCheckCancelLogic;
	}

	public void setBlsCheckCancelLogic(BlsCheckCancelLogic blsCheckCancelLogic) {
		this.blsCheckCancelLogic = blsCheckCancelLogic;
	}

	public boolean getIsAutoDeclare(Company company) {
		BlsParameterSet blsParameterSet = blsMessageDao
				.findBlsParameterSet(company);
		return (blsParameterSet.getIsAutoDeclare() == null ? false
				: blsParameterSet.getIsAutoDeclare());
	}

	public boolean getIsAutoProcess(Company company) {
		BlsParameterSet blsParameterSet = blsMessageDao
				.findBlsParameterSet(company);
		return (blsParameterSet.getIsAutoProcess() == null ? false
				: blsParameterSet.getIsAutoProcess());
	}

	public String getInCronExpressionFromDB(Company company) {
		String cronExpression = "0 0/5 * * * ?";
		BlsParameterSet blsParameterSet = blsMessageDao
				.findBlsParameterSet(company);
		Integer everydayFrequency = blsParameterSet.getImpEverydayFrequency();
		Integer everydayFrequencyType = blsParameterSet
				.getImpEverydayFrequencyType();
		Integer beginTime = blsParameterSet.getImpBeginTime();
		Integer endTime = blsParameterSet.getImpEndTime();
		String weeklyFrequency = blsParameterSet.getImpWeeklyFrequency();
		if (everydayFrequency == null || everydayFrequencyType == null
				|| weeklyFrequency == null || "".equals(weeklyFrequency.trim())
				|| everydayFrequencyType < 0) {
			System.out.println("1 in---:" + cronExpression);
			return cronExpression;
		}
		String hourInterval = ((beginTime == -1 || beginTime == null) && (endTime == null || endTime == -1)) ? "*"
				: ((beginTime == -1 || beginTime == null) ? "0" : String
						.valueOf(beginTime)
						+ "-"
						+ ((endTime == null || endTime == -1) ? "23" : String
								.valueOf(endTime)));
		if (everydayFrequencyType == 0) {
			cronExpression = "0/" + String.valueOf(everydayFrequency) + " * "
					+ hourInterval;
		} else if (everydayFrequencyType == 1) {
			cronExpression = "0" + " 0/" + String.valueOf(everydayFrequency)
					+ " " + hourInterval;
		} else if (everydayFrequencyType == 2) {
			cronExpression = "0" + " 0 "
					+ (hourInterval.equals("*") ? "0" : hourInterval) + "/"
					+ String.valueOf(everydayFrequency);
		}
		cronExpression += " ? * " + getWeeklyFrequency(weeklyFrequency);
		System.out.println("2 in---:" + cronExpression);
		return cronExpression;
	}

	public String getOutCronExpressionFromDB(Company company) {
		String cronExpression = "0 0/5 * * * ?";
		BlsParameterSet blsParameterSet = blsMessageDao
				.findBlsParameterSet(company);
		Integer everydayFrequency = blsParameterSet.getExpEverydayFrequency();
		Integer everydayFrequencyType = blsParameterSet
				.getExpEverydayFrequencyType();
		Integer beginTime = blsParameterSet.getExpBeginTime();
		Integer endTime = blsParameterSet.getExpEndTime();
		String weeklyFrequency = blsParameterSet.getExpWeeklyFrequency();
		if (everydayFrequency == null || everydayFrequencyType == null
				|| weeklyFrequency == null || "".equals(weeklyFrequency.trim())
				|| everydayFrequencyType < 0) {
			System.out.println("1 out---:" + cronExpression);
			return cronExpression;
		}
		String hourInterval = ((beginTime == -1 || beginTime == null) && (endTime == null || endTime == -1)) ? "*"
				: ((beginTime == -1 || beginTime == null) ? "0" : String
						.valueOf(beginTime)
						+ "-"
						+ ((endTime == null || endTime == -1) ? "23" : String
								.valueOf(endTime)));
		if (everydayFrequencyType == 0) {
			cronExpression = "0/" + String.valueOf(everydayFrequency) + " * "
					+ hourInterval;
		} else if (everydayFrequencyType == 1) {
			cronExpression = "0" + " 0/" + String.valueOf(everydayFrequency)
					+ " " + hourInterval;
		} else if (everydayFrequencyType == 2) {
			cronExpression = "0" + " 0 "
					+ (hourInterval.equals("*") ? "0" : hourInterval) + "/"
					+ String.valueOf(everydayFrequency);
		}
		cronExpression += " ? * " + getWeeklyFrequency(weeklyFrequency);
		System.out.println("2 out---:" + cronExpression);
		return cronExpression;
	}

	public String getCobCronExpressionFromDB(Company company) {
		String cronExpression = "0 0/5 * * * ?";
		BlsParameterSet blsParameterSet = blsMessageDao
				.findBlsParameterSet(company);
		Integer everydayFrequency = blsParameterSet.getCobEverydayFrequency();
		Integer everydayFrequencyType = blsParameterSet
				.getCobEverydayFrequencyType();
		Integer beginTime = blsParameterSet.getCobBeginTime();
		Integer endTime = blsParameterSet.getCobEndTime();
		String weeklyFrequency = blsParameterSet.getCobWeeklyFrequency();
		if (everydayFrequency == null || everydayFrequencyType == null
				|| weeklyFrequency == null || "".equals(weeklyFrequency.trim())
				|| everydayFrequencyType < 0) {
			System.out.println("1 cob---:" + cronExpression);
			return cronExpression;
		}
		String hourInterval = ((beginTime == -1 || beginTime == null) && (endTime == null || endTime == -1)) ? "*"
				: ((beginTime == -1 || beginTime == null) ? "0" : String
						.valueOf(beginTime)
						+ "-"
						+ ((endTime == null || endTime == -1) ? "23" : String
								.valueOf(endTime)));
		if (everydayFrequencyType == 0) {
			cronExpression = "0/" + String.valueOf(everydayFrequency) + " * "
					+ hourInterval;
		} else if (everydayFrequencyType == 1) {
			cronExpression = "0" + " 0/" + String.valueOf(everydayFrequency)
					+ " " + hourInterval;
		} else if (everydayFrequencyType == 2) {
			cronExpression = "0" + " 0 "
					+ (hourInterval.equals("*") ? "0" : hourInterval) + "/"
					+ String.valueOf(everydayFrequency);
		}
		cronExpression += " ? * " + getWeeklyFrequency(weeklyFrequency);
		System.out.println("2 cob---:" + cronExpression);
		return cronExpression;
	}

	private String getWeeklyFrequency(String s) {
		StringBuffer sb = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if ("1".equals(String.valueOf(c))) {
				if (i == 0) {
					sb.append(String.valueOf(i + 1));
				} else {
					sb.append("," + String.valueOf(i + 1));
				}
			}
		}
		if (sb.toString().equals("")) {
			return "*";
		} else {
			return sb.toString();
		}
	}

	/**
	 * 自动海关申报入仓单
	 * 
	 * @param company
	 */
	public void singleAutoDeclareInDelivery(Company company, Delivery delivery) {
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, delivery.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		System.out.println("自动申报进仓车次：" + delivery.getDeliveryNo());
		boolean hasException = false;
		String inOutFlag = BlsInOutFlag.IN;
		try {
			delivery = storageBillLogic.applyDelivery(delivery);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, true, delivery
							.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
					inOutFlag, delivery.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(delivery.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR
					.equals(delivery.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(delivery
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, true, delivery
							.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
						inOutFlag, delivery.getDeliveryNo());
			}
		}
	}

	/**
	 * 自动处理回执入仓单
	 * 
	 * @param company
	 */
	public void singleAutoProcessInDelivery(Company company, Delivery delivery) {
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, delivery.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		boolean hasException = false;
		String inOutFlag = BlsInOutFlag.IN;
		try {
			BlsReceiptResult blsReceiptResult = blsMessageLogic
					.findBlsReceiptResultFromHG(
							BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
							company);
			System.out.println("自动处理进仓车次：" + delivery.getDeliveryNo());
			delivery = storageBillLogic.processDelivery(delivery,
					blsReceiptResult);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, false, delivery
							.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
					inOutFlag, delivery.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(delivery.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR
					.equals(delivery.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(delivery
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, false, delivery
							.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
						inOutFlag, delivery.getDeliveryNo());
			}
		}
	}

	/**
	 * 自动海关申报出仓单
	 * 
	 * @param company
	 */
	public void singleAutoDeclareOutDelivery(Company company, Delivery delivery) {
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, delivery.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		System.out.println("自动申报出仓车次：" + delivery.getDeliveryNo());
		boolean hasException = false;
		String inOutFlag = BlsInOutFlag.OUT;
		try {
			delivery = storageBillLogic.applyDelivery(delivery);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, true, delivery
							.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
					inOutFlag, delivery.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(delivery.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR
					.equals(delivery.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(delivery
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, true, delivery
							.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
						inOutFlag, delivery.getDeliveryNo());
			}
		}
	}

	/**
	 * 自动处理回执出仓单
	 * 
	 * @param company
	 */
	public void singleAutoProcessOutDelivery(Company company, Delivery delivery) {
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, delivery.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		String inOutFlag = BlsInOutFlag.OUT;
		boolean hasException = false;
		try {
			BlsReceiptResult blsReceiptResult = blsMessageLogic
					.findBlsReceiptResultFromHG(
							BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
							company);
			System.out.println("自动处理出仓车次：" + delivery.getDeliveryNo());
			delivery = storageBillLogic.processDelivery(delivery,
					blsReceiptResult);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, false, delivery
							.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
					inOutFlag, delivery.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(delivery.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR
					.equals(delivery.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(delivery
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.MANIFEST_DECLARE, inOutFlag, false, delivery
							.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.MANIFEST_DECLARE, delivery.getId(),
						inOutFlag, delivery.getDeliveryNo());
			}
		}
	}

	/**
	 * 自动海关申报入仓货到报告
	 * 
	 * @param company
	 */
	public void singleAutoDeclareInEntranceMessage(Company company,
			EntranceMessage entranceMessage) {
		List list = this.storageBillDao.findDelivery(company, entranceMessage
				.getDeliveryNo());
		if (list.size() <= 0) {
			return;
		}
		Delivery delivery = (Delivery) list.get(0);
		BlsReceiptResult blsReceiptResult = this.blsMessageLogic
				.findBlsReceiptResultFromHG(BlsServiceType.MANIFEST_DECLARE,
						delivery.getId(), (Company) delivery.getCompany());
		System.out.println("自动申报入仓货到报告  查询仓单状态是："
				+ blsReceiptResult.getServiceStatus().trim());
		if (!String.valueOf(BlsDeliveryResultType.R3).equals(
				blsReceiptResult.getServiceStatus().trim())) {
			return;
		}
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, entranceMessage
						.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		System.out.println("自动申报入仓货到：" + entranceMessage.getDeliveryNo());
		String inOutFlag = BlsInOutFlag.IN;
		boolean hasException = false;
		try {
			entranceMessage = this.entranceMessageLogic
					.applyFreightage(entranceMessage);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, true,
					entranceMessage.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage.getId(),
					inOutFlag, entranceMessage.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(entranceMessage.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR.equals(entranceMessage
					.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(entranceMessage
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, true,
					entranceMessage.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage
								.getId(), inOutFlag, entranceMessage
								.getDeliveryNo());
			}
		}
	}

	/**
	 * 自动处理回执入仓货到报告
	 * 
	 * @param company
	 */
	public void singleAutoProcessInEntranceMessage(Company company,
			EntranceMessage entranceMessage) {
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, entranceMessage
						.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		String inOutFlag = BlsInOutFlag.IN;
		boolean hasException = false;
		try {
			BlsReceiptResult blsReceiptResult = blsMessageLogic
					.findBlsReceiptResultFromHG(
							BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage
									.getId(), company);
			System.out.println("自动处理入仓货到：" + entranceMessage.getDeliveryNo());
			entranceMessage = entranceMessageLogic.processFreightage(
					entranceMessage, blsReceiptResult);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, false,
					entranceMessage.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage.getId(),
					inOutFlag, entranceMessage.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(entranceMessage.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR.equals(entranceMessage
					.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(entranceMessage
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, false,
					entranceMessage.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage
								.getId(), inOutFlag, entranceMessage
								.getDeliveryNo());
				this.blsMessageDao.saveOrUpdate(backInfo);
			}
		}
	}

	/**
	 * 自动海关申报出仓货到报告
	 * 
	 * @param company
	 */
	public void singleAutoDeclareOutEntranceMessage(Company company,
			EntranceMessage entranceMessage) {
		List list = this.storageBillDao.findDelivery(company, entranceMessage
				.getDeliveryNo());
		if (list.size() <= 0) {
			return;
		}
		Delivery delivery = (Delivery) list.get(0);
		BlsReceiptResult blsReceiptResult = this.blsMessageLogic
				.findBlsReceiptResultFromHG(BlsServiceType.MANIFEST_DECLARE,
						delivery.getId(), (Company) delivery.getCompany());
		System.out.println("自动申报出仓货到报告  查询仓单状态是："
				+ blsReceiptResult.getServiceStatus().trim());
		if (!String.valueOf(BlsDeliveryResultType.R8).equals(
				blsReceiptResult.getServiceStatus().trim())) {
			return;
		}
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, entranceMessage
						.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		System.out.println("自动申报出仓货到：" + entranceMessage.getDeliveryNo());
		boolean hasException = false;
		String inOutFlag = BlsInOutFlag.OUT;
		try {
			entranceMessage = this.entranceMessageLogic
					.applyFreightage(entranceMessage);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, true,
					entranceMessage.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage.getId(),
					inOutFlag, entranceMessage.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(entranceMessage.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR.equals(entranceMessage
					.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(entranceMessage
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, true,
					entranceMessage.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage
								.getId(), inOutFlag, entranceMessage
								.getDeliveryNo());
			}
		}
	}

	/**
	 * 自动处理回执出仓货到报告
	 * 
	 * @param company
	 */
	public void singleAutoProcessOutEntranceMessage(Company company,
			EntranceMessage entranceMessage) {
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, entranceMessage
						.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		String inOutFlag = BlsInOutFlag.OUT;
		boolean hasException = false;
		try {
			BlsReceiptResult blsReceiptResult = blsMessageLogic
					.findBlsReceiptResultFromHG(
							BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage
									.getId(), company);
			System.out.println("自动处理出仓货到：" + entranceMessage.getDeliveryNo());
			entranceMessage = entranceMessageLogic.processFreightage(
					entranceMessage, blsReceiptResult);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, false,
					entranceMessage.getDeliveryNo(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage.getId(),
					inOutFlag, entranceMessage.getDeliveryNo());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(entranceMessage.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR.equals(entranceMessage
					.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(entranceMessage
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.FREIGHTAGE_DECLARE, inOutFlag, false,
					entranceMessage.getDeliveryNo(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.FREIGHTAGE_DECLARE, entranceMessage
								.getId(), inOutFlag, entranceMessage
								.getDeliveryNo());
			}
		}
	}

	/**
	 * 单笔自动海关申报捆绑核销
	 * 
	 */
	public void singleAutoDeclareCollateBind(Company company,
			CollateBind collateBind) {
		/**
		 * 查询仓单状态，如果入仓单的状态是3,4则进行核销申报，如果出仓单的状态时8,9,10则进行核销申报
		 */
		// List<TempBillReturn> listBillReturn = this.storageBillLogic
		// .findStorageBillStatusFromHP(collateBind.getBrief().getCode(),
		// collateBind
		// .getFormID());
		// if (listBillReturn.size() <= 0) {
		// return;
		// }
		// TempBillReturn tempBillReturn = (TempBillReturn)
		// listBillReturn.get(0);
		String serviceStatus = this.storageBillLogic.getDeliveryServiceStatus(
				collateBind.getFormID(), company);
		System.out.println("自动申报捆绑核销  查询仓单状态是：" + serviceStatus.trim());
		if (FormType.IM_MFT.equals(collateBind.getFormType())) {
			if (!String.valueOf(BlsDeliveryResultType.R3).equals(serviceStatus)
					&& !String.valueOf(BlsDeliveryResultType.R4).equals(
							serviceStatus)) {
				return;
			}
		} else if (FormType.EX_MFT.equals(collateBind.getFormType())) {
			if (!String.valueOf(BlsDeliveryResultType.R8).equals(serviceStatus)
					&& !String.valueOf(BlsDeliveryResultType.R9).equals(
							serviceStatus)
					&& !String.valueOf(BlsDeliveryResultType.R10).equals(
							serviceStatus)) {
				return;
			}
		}
		// -------------------------------------
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, collateBind.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		System.out.println("------------------------自动申报捆绑核销："
				+ collateBind.getFormID());
		boolean hasException = false;
		try {
			collateBind = this.blsCheckCancelLogic
					.applyCollatebind(collateBind);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.COLLATEBIND_DECLARE, "", true, collateBind
							.getFormID(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.COLLATEBIND_DECLARE, collateBind.getId(),
					"", collateBind.getFormID());
			System.out.println("eeeeeeeeeeeeedddddddddddddddddddd:"
					+ ex.getMessage());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(collateBind.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR.equals(collateBind
					.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(collateBind
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.COLLATEBIND_DECLARE, "", true, collateBind
							.getFormID(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.COLLATEBIND_DECLARE,
						collateBind.getId(), "", collateBind.getFormID());
			}
			System.out.println("sssssssssssssssfffffffffffffffffff:" + result);
		}
	}

	/**
	 * 自动处理回执核销
	 * 
	 * @param company
	 */
	public void singleAutoProcessCollateBind(Company company,
			CollateBind collateBind) {
		BlsAutoBackBillInfo backInfo = this.blsMessageDao
				.findBlsAutoBackBillInfoByRelateId(company, collateBind.getId());
		if (backInfo != null && backInfo.getDeclareTimes() != null
				&& backInfo.getDeclareTimes() >= 3) {
			return;
		}
		boolean hasException = false;
		try {
			BlsReceiptResult blsReceiptResult = blsMessageLogic
					.findBlsReceiptResultFromHG(
							BlsServiceType.COLLATEBIND_DECLARE, collateBind
									.getId(), company);
			System.out.println("--------------自动处理捆绑核销："
					+ collateBind.getFormID());
			collateBind = this.blsCheckCancelLogic.processCollatebind(
					collateBind, blsReceiptResult);
		} catch (Exception ex) {
			hasException = true;
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.COLLATEBIND_DECLARE, "", false, collateBind
							.getFormID(), "3", ex.getMessage());
			this.saveBlsAutoBackBillInfo(backInfo, company,
					BlsServiceType.COLLATEBIND_DECLARE, collateBind.getId(),
					"", collateBind.getFormID());
			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee:"
					+ ex.getMessage());
		}
		if (!hasException) {
			String result = "2";
			if (DeclareState.WAIT_EAA.equals(collateBind.getDeclareState())) {
				result = "2";// 正在审批
			} else if (DeclareState.APPLY_POR.equals(collateBind
					.getDeclareState())) {
				result = "0";// 退单
			} else if (DeclareState.PROCESS_EXE.equals(collateBind
					.getDeclareState())) {
				result = "1";// 审批通过
			}
			this.saveBlsAutoDeclareProcessInfo(company,
					BlsServiceType.COLLATEBIND_DECLARE, "", false, collateBind
							.getFormID(), result, "");
			// 退单
			if ("0".equals(result)) {
				this.saveBlsAutoBackBillInfo(backInfo, company,
						BlsServiceType.COLLATEBIND_DECLARE,
						collateBind.getId(), "", collateBind.getFormID());
			}
			System.out.println("ssssssssssssssssssssssssssssssssss:" + result);
		}
	}

	/**
	 * 保存自动申报或自动审批日志信息
	 * 
	 * @param company
	 * @param serviceType
	 * @param isAutoDeclare
	 * @param billCode
	 * @param result
	 * @param memo
	 */
	private void saveBlsAutoDeclareProcessInfo(Company company,
			String serviceType, String inOutFlag, Boolean isAutoDeclare,
			String billCode, String result, String memo) {
		BlsAutoDeclareProcessInfo info = new BlsAutoDeclareProcessInfo();
		info.setCompany(company);
		info.setServiceType(serviceType);
		info.setInOut(inOutFlag);
		info.setIsAutoDeclare(isAutoDeclare);
		info.setBillCode(billCode);
		info.setDeclareProcessDate(new Date());
		info.setDeclareProcessResult(result);
		info.setMemo(memo);
		this.blsMessageDao.saveOrUpdate(info);
	}

	/**
	 * 保存退单日志信息
	 * 
	 * @param backInfo
	 * @param company
	 * @param serviceType
	 * @param relateId
	 * @param inOutFlag
	 * @param billCode
	 */
	private void saveBlsAutoBackBillInfo(BlsAutoBackBillInfo backInfo,
			Company company, String serviceType, String relateId,
			String inOutFlag, String billCode) {
		if (backInfo == null) {
			backInfo = new BlsAutoBackBillInfo();
			backInfo.setServiceType(serviceType);
			backInfo.setCompany(company);
			backInfo.setRelateId(relateId);
			backInfo.setInOut(inOutFlag);
			backInfo.setBillCode(billCode);
			backInfo.setDeclareTimes(1);
			backInfo.setDeclareDates(dateFormat.format(new Date()));
		} else {
			backInfo.setDeclareTimes(backInfo.getDeclareTimes() == null ? 1
					: backInfo.getDeclareTimes() + 1);
		}
		this.blsMessageDao.saveOrUpdate(backInfo);
	}
}
