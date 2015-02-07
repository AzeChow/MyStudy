package com.bestway.dzsc.qp.test;

import java.util.List;

import junit.framework.TestCase;

import com.bestway.common.CommonUtils;
import com.bestway.dzsc.qp.action.DzscQpAction;
import com.bestway.dzsc.qp.action.DzscQpServiceClient;
import com.bestway.dzsc.qp.entity.DzscQPEmsPorBillHead;
import com.bestway.dzsc.qp.entity.DzscQPEmsPorWjHead;
import com.bestway.dzsc.qp.entity.TempQPEmsPorData;
import com.bestway.dzsc.qp.entity.TempQPEmsTrData;

public class DzscQpTest extends TestCase {

	// / <summary>
	// / 获得是否正在执行的 电子手册通关备案表头
	// / </summary>
	// / <param name="isExecute">否正在执行</param>
	// / <param name="tradeCode">经营单位代码</param>
	// / <returns>DzscQPEmsPorHead List </returns>
	public void testFindDzscQPEmsPorHead() {
		// "4419960068",true
//		String tradeCode = "44199B3812";
//		boolean isExecute = true;
//
//		DzscQpAction dzscQpAction = DzscQpServiceClient.getDzscQpAction();
//
//		List itemsByQp = dzscQpAction.findDzscQPEmsPorHead("88888888",
//				tradeCode, isExecute);
//
//		//
//		List<DzscQPEmsPorBillHead> list = CommonUtils.castListType(itemsByQp,
//				DzscQPEmsPorBillHead.class);
//
//		System.out.println(itemsByQp.size());
//
//		for (DzscQPEmsPorBillHead p : list) {
//			System.out.println("手册号-------" + p.getEmsNo());
//		}
	}

	// / <summary>
	// / 获得 通关备案数据 来自 Dzsc by tradeCode and isExecute
	// / </summary>
	// / <param name="tradeCode">经营单位代码</param>
	// / <param name="copEmsNo">企业内部编号</param>
	// / <returns>TempQPEmsPorData</returns>
	public void testFindTempQPEmsPorData() {
//		String tradeCode = "4419960068";
//		String copEmsNo = "B52048151179";
//		boolean isExecute = true;
//
//		DzscQpAction dzscQpAction = DzscQpServiceClient.getDzscQpAction();
//
//		TempQPEmsPorData itemsByQp = dzscQpAction.findTempQPEmsPorData(
//				"88888888", tradeCode, copEmsNo, isExecute);
//
//		//
//		// 如果对象有List 要转换一下
//		//
//		itemsByQp = (TempQPEmsPorData) CommonUtils.castObjectType(itemsByQp);
//
//		// super.assertEquals(itemsByQp.getPtsEmsHead().getTradeCode(),
//		// tradeCode);
//		System.out.println(itemsByQp.getPtsEmsHead().getEmsNo());
//		System.out.println(itemsByQp.getPtsEmsListAExg().size());
//		System.out.println(itemsByQp.getPtsEmsListAImg().size());
//		System.out.println(itemsByQp.getPtsEmsListExg().size());
//		System.out.println(itemsByQp.getPtsEmsListImg().size());
//		System.out.println(itemsByQp.getPtsEmsListCm().size());

	}

	// / <summary>
	// / 获得是否正在执行的 电子手册合同备案表头
	// / </summary>
	// / <param name="isExecute">否正在执行</param>
	// / <param name="tradeCode">经营单位代码</param>
	// / <returns>DzscQPEmsPorWjHead List </returns>
	public void testFindDzscQPEmsPorWjHead() {
		// "4419960068",true
//		String tradeCode = "44199B3812";
//		boolean isExecute = true;
//
//		DzscQpAction dzscQpAction = DzscQpServiceClient.getDzscQpAction();
//
//		List itemsByQp = dzscQpAction.findDzscQPEmsPorWjHead("88888888",
//				tradeCode, isExecute);
//
//		//
//		List<DzscQPEmsPorWjHead> list = CommonUtils.castListType(itemsByQp,
//				DzscQPEmsPorWjHead.class);
//
//		System.out.println(itemsByQp.size());
//
//		for (DzscQPEmsPorWjHead p : list) {
//			System.out.println("手册号-------" + p.getEmsNo());
//		}
	}

	// / <summary>
	// / 获得 合同备案数据 来自 Dzsc by tradeCode
	// / </summary>
	// / <param name="tradeCode">经营单位代码</param>
	// / <param name="copEmsNo">企业内部编号</param>
	// / <returns>TempQPEmsTrData</returns>
	public void testFindTempQPEmsTrData() {
//		String tradeCode = "4419960068";
//		String copEmsNo = "20060011";
//		boolean isExecute = true;
//
//		DzscQpAction dzscQpAction = DzscQpServiceClient.getDzscQpAction();
//
//		TempQPEmsTrData itemsByQp = dzscQpAction.findTempQPEmsTrData(
//				"88888888", tradeCode, copEmsNo, isExecute);
//
//		//
//		// 如果对象有List 要转换一下
//		//
//		itemsByQp = (TempQPEmsTrData) CommonUtils.castObjectType(itemsByQp);
//
//		// super.assertEquals(itemsByQp.getPtsEmsHead().getTradeCode(),
//		// tradeCode);
//		System.out.println(itemsByQp.getPtsTrHead().getEmsNo());
//		System.out.println("itemsByQp.getPtsTrListExg().size() = "
//				+ itemsByQp.getPtsTrListExg().size());
//		if (itemsByQp.getPtsTrListExg().size() > 0) {
//			System.out.println(itemsByQp.getPtsTrListExg().get(0).getSeqNum());
//		}
//		System.out.println("itemsByQp.getPtsTrListImg().size() = "
//				+ itemsByQp.getPtsTrListImg().size());
//		if (itemsByQp.getPtsTrListImg().size() > 0) {
//			System.out.println(itemsByQp.getPtsTrListImg().get(0).getSeqNum());
//		}

	}

}
