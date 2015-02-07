package com.bestway.bls.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.BlsCheckCancelDao;
import com.bestway.bls.entity.BlsCollateBindResultType;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.CollateBindDetail;
import com.bestway.bls.entity.CollateBindDetailList;
import com.bestway.bls.entity.FormType;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;

/**
 * 
 * 
 * 单证核销,逻辑
 * 
 * @author luosheng
 * 
 */
public class BlsCheckCancelLogic {
	private BlsCheckCancelDao blsCheckCancelDao = null;
	private BlsMessageLogic blsMessageLogic;

	/**
	 * @return the blsCheckCancelDao
	 */
	public BlsCheckCancelDao getBlsCheckCancelDao() {
		return blsCheckCancelDao;
	}

	/**
	 * @param blsCheckCancelDao
	 *            the blsCheckCancelDao to set
	 */
	public void setBlsCheckCancelDao(BlsCheckCancelDao blsCheckCancelDao) {
		this.blsCheckCancelDao = blsCheckCancelDao;
	}

	public BlsMessageLogic getBlsMessageLogic() {
		return blsMessageLogic;
	}

	public void setBlsMessageLogic(BlsMessageLogic blsMessageLogic) {
		this.blsMessageLogic = blsMessageLogic;
	}

	// ////////////////////////////////////////////////////////////////
	//
	// 每次新增不用弹出对话框是好的操作模式 要常用,这样用户方便性加强,
	// window状态也好控制
	//
	// //////////////////////////////////////////////////////////////////

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息 CollateBind
	 * */
	public CollateBind newCollateBind() {
		Company com = this.blsCheckCancelDao.findCompany();
		StringBuffer stringBuffer = new StringBuffer();
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		Brief brief = this.blsCheckCancelDao.findBrief(com.getCode());
		if (brief == null) {
			stringBuffer.append("编码 == [" + com.getCode()
					+ "] 在海关注册公司不存在,请检查!!\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}
		CollateBind head = new CollateBind();
		head.setDeclareState(DeclareState.APPLY_POR);
		//
		// get breif by code
		//
		head.setBrief(brief);
		head.setCompany(CommonUtils.getCompany());
		this.blsCheckCancelDao.saveCollateBind(head);
		return head;
	}

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息明细 CollateBindDetail
	 * */
	public CollateBindDetail newCollateBindDetail(CollateBind collateBind) {
		CollateBindDetail head = new CollateBindDetail();
		Integer no = null;
		no = this.blsCheckCancelDao.findMaxNoFromCollateBindDetail(collateBind);
		System.out.println("no=" + no);
		head.setCollateBind(collateBind);
		if (no == null) {
			head.setGno(0);
		} else {
			head.setGno(no + 1);
		}
		head.setCompany(CommonUtils.getCompany());
		this.blsCheckCancelDao.saveCollateBindDetail(head);
		return head;
	}

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息明细列表 CollateBindDetailList
	 * */
	public CollateBindDetailList newCollateBindDetailList(
			CollateBindDetail collateBindDetail) {
		CollateBindDetailList head = new CollateBindDetailList();
		head.setCollateBindDetail(collateBindDetail);
		head.setCompany(CommonUtils.getCompany());
		// 健壮
		if ((collateBindDetail != null)
				&& (collateBindDetail.getCollateBind() != null)
				&& (collateBindDetail.getCollateBind().getBrief() != null)) {
			head.setTradeCode(collateBindDetail.getCollateBind().getBrief()
					.getCode());
		} else {
			head.setTradeCode(((Company) CommonUtils.getCompany()).getCode());
		}
		this.blsCheckCancelDao.saveCollateBindDetailList(head);
		return head;
	}

	/** 导入数据来自仓单 */
	public List<CollateBind> saveImportDataByBill(String taskId,
			List<StorageBill> storageBillList) {
		//
		// 服务端进程任务
		//
		ProgressInfo progressInfo = ProcExeProgress.getInstance()
				.getProgressInfo(taskId);

		List<CollateBind> returnList = new ArrayList<CollateBind>();
		//
		// 这里为什么要用一条一条来 loop ,是因为性能在这里不是首要问题,代码可读性会很好.
		//
		for (StorageBill storageBill : storageBillList) {
			progressInfo.setHintMessage("正在导入仓单 == " + storageBill.getBillNo());
			//
			// make
			//
			CollateBind newCollateBind = makeCollateBind(storageBill);
			//
			// add to list
			//
			returnList.add(newCollateBind);
		}
		return returnList;
	}

	/** 导入数据来自仓单 */
	private CollateBind makeCollateBind(StorageBill storageBill) {
		//
		// 利用现成方法
		//
		CollateBind newCollateBind = this.newCollateBind();
		//
		// 单据号或报关单号
		//
		newCollateBind.setFormID(storageBill.getBillNo());
		//
		// default 0: I 入仓
		//
		String formType = FormType.IM_MFT;
		String collateFormType = FormType.IM_ENT;
		String billIoFlag = "I";
		if (storageBill.getIoFlag() == null
				|| storageBill.getIoFlag().equals("")
				|| storageBill.getIoFlag().equals("I")) {
			billIoFlag = "I";
			System.out.println("storageBill.getIoFlag()="
					+ storageBill.getIoFlag());
		} else {
			billIoFlag = "O";
			System.out.println("storageBill.getIoFlag()="
					+ storageBill.getIoFlag());
		}
		// String billIoFlag = storageBill.getIoFlag() == null
		// ||storageBill.getIoFlag().equals("")? "I" : storageBill
		// .getIoFlag();
		//
		// 进出仓标志 在那里是硬编码.这里只能这样了.
		// 0: I 入仓,1: O出仓
		//
		if (billIoFlag.equals("O")) {
			formType = FormType.EX_MFT; // 1: O出仓
			collateFormType = FormType.EX_ENT;
		} else if (billIoFlag.equals("I")) {
			formType = FormType.IM_MFT;
			collateFormType = FormType.IM_ENT;
		}
		newCollateBind.setFormType(formType);
		newCollateBind.setCollateFormType(collateFormType);
		//
		// 再一次save
		//
		this.blsCheckCancelDao.saveCollateBind(newCollateBind);

		//
		// 获得 StorageBillAfter list
		//
		List<StorageBillAfter> storageBillAfterList = this.blsCheckCancelDao
				.findStorageBillAfterByStorageBill(storageBill.getId());

		for (StorageBillAfter aftet : storageBillAfterList) {
			//
			// make
			//
			makeCollateBindDetail(newCollateBind, aftet);
		}
		return newCollateBind;
	}

	/** 导入数据来自仓单 */
	private void makeCollateBindDetail(CollateBind newCollateBind,
			StorageBillAfter aftet) {
		//
		// detail
		//
		CollateBindDetail collateBindDetail = new CollateBindDetail();
		collateBindDetail.setCollateBind(newCollateBind);
		collateBindDetail.setGno(aftet.getSeqNo());
		collateBindDetail.setCompany(CommonUtils.getCompany());
		this.blsCheckCancelDao.saveCollateBindDetail(collateBindDetail);
		//
		// detail list one to one
		//
		CollateBindDetailList collateBindDetailList = new CollateBindDetailList();
		collateBindDetailList.setCollateBindDetail(collateBindDetail);
		collateBindDetailList.setCompany(CommonUtils.getCompany());
		//
		// form id 来自 核销项信息单证编号为报关单号，货物序号为报关单表体对应的序号
		//
		collateBindDetailList.setFormId(aftet.getEntryID());
		collateBindDetailList.setGno(aftet.getEntryGNo());
		collateBindDetailList.setGcount(aftet.getQty());
		
		//wss3.26:加上一些属性
		collateBindDetailList.setTradeCode(collateBindDetail.getCollateBind().getBrief().getCode());
		collateBindDetailList.setUnit(aftet.getUnit());
		this.blsCheckCancelDao.saveCollateBindDetailList(collateBindDetailList);
	}

	/**
	 * 删除 核销捆绑关系基本信息（表头） CollateBind
	 */
	public void deleteCollateBind(List<CollateBind> list) {
		for (CollateBind c : list) {
			this.deleteCollateBind(c);
		}
	}

	/**
	 * 删除 核销捆绑关系基本信息（表头） CollateBind
	 */
	public void deleteCollateBind(CollateBind collateBind) {
		String collateBindId = collateBind.getId();
		List list = this.blsCheckCancelDao
				.findCollateBindDetailByHead(collateBindId);
		this.deleteCollateBindDetail(list);
		this.blsCheckCancelDao.deleteCollateBind(collateBind);
	}

	/**
	 * 删除 核销捆绑关系基本信息（明细） CollateBindDetail
	 */
	public void deleteCollateBindDetail(List list) {
		for (int i = 0; i < list.size(); i++) {
			CollateBindDetail data = (CollateBindDetail) list.get(i);
			deleteCollateBindDetail(data);
		}
	}

	/**
	 * 删除 核销捆绑关系基本信息（明细） CollateBindDetail
	 */
	public void deleteCollateBindDetail(CollateBindDetail data) {
		List detailList = this.blsCheckCancelDao
				.findCollateBindDetailListByDetail(data.getId());
		for (int j = 0; j < detailList.size(); j++) {
			CollateBindDetailList d = (CollateBindDetailList) detailList.get(j);
			this.blsCheckCancelDao.deleteCollateBindDetailList(d);
		}
		// this.blsCheckCancelDao.deleteCollateBindDetailList(detailList);
		// this.writeBackTransferFactoryBillToCustomsBillRequestId(data
		// .getId());
		this.blsCheckCancelDao.deleteCollateBindDetail(data);
	}

	// /**
	// *
	// * 转厂申请单报文生成
	// *
	// * @param head
	// *
	// * @return FptBillHead
	// */
	// public DeclareFileInfo applyFptAppHead(FptAppHead head, String taskId) {
	// head = this.fptManageDao.findFptAppHeadById(head.getId());
	// Map<String, List> hmData = new HashMap<String, List>();
	// List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
	// List<FptAppHead> lsFptHead = new ArrayList<FptAppHead>();
	// List<FptAppItem> lsFptBill = new ArrayList<FptAppItem>();
	// ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
	// taskId);
	// if (info != null) {
	// info.setMethodName("正在获取要申报的资料");
	// }
	// FptSignInfo signInfo = (FptSignInfo) fptMessageLogic.getCspPtsSignInfo(
	// info, null);
	// //
	// // 获得转入转出标识
	// //
	// String fptInOutFlag = head.getInOutFlag();
	// if (fptInOutFlag.equals(FptInOutFlag.OUT)) {// 转出
	// signInfo.setCopNo(head.getOutCopAppNo());
	// } else {
	// signInfo.setCopNo(head.getInCopAppNo());// 转入
	// }
	// signInfo.setColDcrTime(0);
	//
	// signInfo.setAppNo(head.getAppNo());
	// signInfo.setSeqNo(head.getSeqNo());
	// signInfo.setInOutFlag(head.getInOutFlag());
	// signInfo.setSignDate(new Date());
	// if (DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
	// signInfo.setDeclareType(DelcareType.MODIFY);
	// }
	// lsSignInfo.add(signInfo);
	// if (signInfo.getIdCard() == null
	// || "".equals(signInfo.getIdCard().trim())) {
	// throw new RuntimeException("签名信息中操作员卡号为空");
	// }
	// if (signInfo.getIdCard().length() < 4) {
	// throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
	// }
	// if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
	// head.setOutDate(new Date());
	// } else {
	// head.setInDate(new Date());
	// }
	// // 申报类型 1－备案申请 2－变更申请
	// // if (head.getDelcareType() == null || "".equals(head.getDelcareType()))
	// {
	// // head.setDelcareType(DelcareType.APPLICATION);
	// // } else {
	// // head.setDelcareType(DelcareType.MODIFY);
	// // }
	// head.setDeclareState(DeclareState.WAIT_EAA);
	// lsFptHead.add(head);
	// lsFptBill = this.fptManageDao.findFptAppItemsStateChanged(head.getId(),
	// fptInOutFlag);
	//
	// String formatFile = "FptAppInFormat.xml";// 转入
	// if (fptInOutFlag.equals(FptInOutFlag.OUT)) {// 转出
	// formatFile = "FptAppOutFormat.xml";
	// }
	// hmData.put("FptAppSign", lsSignInfo);
	// hmData.put("FptAppHead", lsFptHead);
	// hmData.put("FptAppItem", lsFptBill);
	// DeclareFileInfo fileInfo = fptMessageLogic.exportMessage(formatFile,
	// hmData, info);
	// this.fptManageDao.saveFptAppHead(head);
	// return fileInfo;
	// }
	//	
	//	
	// /**
	// * 转厂申请单回执处理
	// *
	// * @param fptAppHead
	// * @param existFptAppHead
	// * @return
	// */
	// public String processFptAppHead(final FptAppHead fptAppHead,
	// final FptAppHead existFptAppHead, List lsReturnFile) {
	// String copAppNo = "";
	// if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
	// copAppNo = fptAppHead.getOutCopAppNo();
	// } else {
	// copAppNo = fptAppHead.getInCopAppNo();
	// }
	// return fptMessageLogic.processMessage(FptBusinessType.FPT_APP,
	// copAppNo, new CspProcessMessage() {
	// public void failureHandling(
	// TempCspReceiptResultInfo tempReceiptResult) {
	// resetFptAppHead(fptAppHead, existFptAppHead);
	// }
	//
	// public void successHandling(
	// TempCspReceiptResultInfo tempReceiptResult) {
	// CspReceiptResult receiptResult = tempReceiptResult
	// .getReceiptResult();
	// effectiveFptAppHead(fptAppHead, existFptAppHead,
	// receiptResult);
	// }
	// }, lsReturnFile);
	// }
	//
	// /** reset fptAppHead */
	// private void resetFptAppHead(FptAppHead fptAppHead,
	// FptAppHead existFptAppHead) {
	// if (existFptAppHead == null) {
	// fptAppHead.setDeclareState(DeclareState.APPLY_POR);
	// } else {
	// fptAppHead.setDeclareState(DeclareState.CHANGING_EXE);
	// }
	// this.fptManageDao.saveFptAppHead(fptAppHead);
	// }

	/**
	 * 单证核销海关申报
	 */
	public CollateBind applyCollatebind(CollateBind collateBind) {
		// 系统类型，比如车次，车到，或者核销
		String serviceType = BlsServiceType.COLLATEBIND_DECLARE;
		// 关键值
		String keyCode = collateBind.getFormID();
		// 生成报文查询值参数
		Map<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("id", collateBind.getId());
		// 海关申报
		BlsReceiptResult blsReceiptResult = this.blsMessageLogic
				.declareMessage(serviceType, collateBind.getId(), keyCode,
						queryValues, null, null, (Company) collateBind
								.getCompany());
		String declareState = DeclareState.WAIT_EAA;
		if (BlsCollateBindResultType
				.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsCollateBindResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsCollateBindResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		collateBind.setDeclareState(declareState);
		this.blsCheckCancelDao.saveOrUpdate(collateBind);
		return collateBind;
	}

	/**
	 * 单证核销申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	public CollateBind processCollatebind(CollateBind collateBind,
			BlsReceiptResult blsReceiptResult) {
		String declareState = DeclareState.WAIT_EAA;
		if (BlsCollateBindResultType
				.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsCollateBindResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsCollateBindResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		collateBind.setDeclareState(declareState);
		this.blsCheckCancelDao.saveOrUpdate(collateBind);
		this.blsMessageLogic.processMessage(blsReceiptResult);
		return collateBind;
	}

	/**
	 * 查询未核销进，出仓单
	 */
	public List<StorageBillAfter> findNoCheckBill(String ioFlag,
			String formType, String collateFormType) {
		// 查询已经核销的进/出仓核销信息
		List<Object[]> checkedIn = this.blsCheckCancelDao
				.findCollateBindDetail(ioFlag,formType,collateFormType);
		Map checkedMap = new HashMap<String, String>();
		for (Object[] strs : checkedIn) {
			String key = "" + strs[0] + "/" + strs[1];
			checkedMap.put(key, "");
		}
		// 查询已经生效的进出/仓单
		System.out.println("server1" + formType + "    " + collateFormType);
		List<StorageBillAfter> storeages = this.blsCheckCancelDao
				.findStorageBillAfter(ioFlag);

		List<StorageBillAfter> results = new ArrayList<StorageBillAfter>();
		for (StorageBillAfter after : storeages) {
			String key = (after.getStorageBill().getBillNo()) + "/"
					+ (after.getSeqNo());
			System.out.println("server2" + formType + "    " + collateFormType);
			if (!checkedMap.containsKey(key)) {
				if (FormType.EX_MFT.equals(formType)
						&& FormType.IM_MFT.equals(collateFormType)) {
					if (after.getCorrBillNo() != null
							&& !"".equals(after.getCorrBillNo())
							&& after.getCorrBillGNo() != null
							&& !"".equals(after.getCorrBillGNo())) {
						results.add(after);
					}
				} else {
					if (after.getEntryID() != null
							&& !"".equals(after.getEntryID())
							&& after.getEntryGNo() != null
							&& !"".equals(after.getEntryGNo())) {
						results.add(after);
					}
				}
			}
		}

		return results;
	}

	/**
	 * 自动核销
	 * 
	 * @return
	 */
	public String checkBill(List<StorageBillAfter> storageBills,
			String formType, String collateFormType) {
		// StringBuffer info = new StringBuffer();
		// info.append("\n***************\n");
		// info.append("核销导入开始...\n");
		List<CollateBind> heads = this.blsCheckCancelDao.findCollateBind(
				formType, collateFormType);
		Map<String, CollateBind> mapHead = new HashMap<String, CollateBind>();
		for (CollateBind head : heads) {
			mapHead.put(head.getFormID(), head);
		}
		// 自动核销
		Company com = this.blsCheckCancelDao.findCompany();
		if (com.getCode() == null || "".equals(com.getCode())) {
			// info.append("\n***************\n");
			// info.append("警告：\n");
			// info.append("自动核销失败：公司的加工单位编码不能为空");
			return "";
		}
		Brief brief = this.blsCheckCancelDao.findBrief(com.getCode());
		if (brief == null || "".equals(brief.getCode())) {
			// info.append("\n***************\n");
			// info.append("警告：\n");
			// info.append("自动核销失败：编码 == [" + com.getCode()
			// + "] 在海关注册公司不存在,请检查!");
			return "";
		}

		for (StorageBillAfter bill : storageBills) {
			// if(bill.getEntryID()==null || "".equals(bill.getEntryID())
			// || bill.getEntryGNo()==null || "".equals(bill.getEntryGNo())){
			// continue;
			// }
			// 表头
			CollateBind head = mapHead.get(bill.getStorageBill().getBillNo());
			if (head == null) {
				head = new CollateBind();
				head.setFormID(bill.getStorageBill().getBillNo());
				head.setFormType(formType);
				head.setCollateFormType(collateFormType);
				head.setBrief(brief);
				head.setCompany(CommonUtils.getCompany());
				head.setDeclareState(DeclareState.APPLY_POR);
				this.blsCheckCancelDao.saveCollateBind(head);

				mapHead.put(bill.getStorageBill().getBillNo(), head);
			}
			// 表体
			CollateBindDetail bindDetail = new CollateBindDetail();
			bindDetail.setCollateBind(head);
			bindDetail.setGno(bill.getSeqNo());

			bindDetail.setCompany(CommonUtils.getCompany());
			this.blsCheckCancelDao.saveCollateBindDetail(bindDetail);
			// 表体
			CollateBindDetailList bindDetaiList = new CollateBindDetailList();
			bindDetaiList.setCollateBindDetail(bindDetail);
			if (FormType.IM_MFT.equals(collateFormType)) {
				bindDetaiList.setFormId(bill.getCorrBillNo());
				bindDetaiList.setGno(bill.getCorrBillGNo());
				if (bill.getUnit() != null
						&& bill.getCodeTS() != null
						&& bill.getCodeTS().getFirstUnit() != null
						&& bill.getUnit().getCode().equals(
								bill.getCodeTS().getFirstUnit().getCode())) {
					bindDetaiList.setGcount(bill.getQty());
				} else {
					bindDetaiList.setGcount(bill.getQty());
				}

			} else if (FormType.EX_ENT.equals(collateFormType)
					|| FormType.IM_ENT.equals(collateFormType)) {
				bindDetaiList.setFormId(bill.getEntryID());
				bindDetaiList.setGno(bill.getEntryGNo());
				if (bill.getUnit() != null
						&& bill.getCodeTS() != null
						&& bill.getCodeTS().getFirstUnit() != null
						&& bill.getUnit().getCode().equals(
								bill.getCodeTS().getFirstUnit().getCode())) {
					bindDetaiList.setGcount(bill.getQty());
				} else {
					bindDetaiList.setGcount(bill.getQty1());
				}
			}
			//bindDetaiList.setTradeCode(brief.getCode());
			bindDetaiList.setCompany(CommonUtils.getCompany());
			
			//wss3.26新加
			bindDetaiList.setTradeCode(bindDetail.getCollateBind().getBrief().getCode());
			bindDetaiList.setUnit(bill.getUnit());
			
			this.blsCheckCancelDao.saveCollateBindDetailList(bindDetaiList);
		}
		// info.append("\n***************\n");
		// info.append("信息：\n");
		// info.append("自动核销成功!");
		return "";
	}

}
