package com.bestway.dzsc.qp.action;

import java.util.List;

import com.bestway.dzsc.qp.entity.TempQPEmsPorData;
import com.bestway.dzsc.qp.entity.TempQPEmsTrData;

public interface DzscQpAction {
	// / <summary>
	// / 获得是否正在执行的 电子手册通关备案表头
	// / </summary>
	// / <param name="isExecute">否正在执行</param>
	// / <param name="tradeCode">经营单位代码</param>
	// / <returns>DzscQPEmsPorHead List </returns>
	public List findDzscQPEmsPorHead(String pwd, String tradeCode,
			boolean isExecute);

	// / <summary>
	// / 获得 通关备案数据 来自 Dzsc by tradeCode and isExecute
	// / </summary>
	// / <param name="tradeCode">经营单位代码</param>
	// / <param name="copEmsNo">企业内部编号</param>
	// / <returns>TempQPEmsPorData</returns>
	public TempQPEmsPorData findTempQPEmsPorData(String pwd, String tradeCode,
			String copEmsNo, boolean isExecute);

	// / <summary>
	// / 获得是否正在执行的 电子手册合同备案表头
	// / </summary>
	// / <param name="isExecute">否正在执行</param>
	// / <param name="tradeCode">经营单位代码</param>
	// / <returns>DzscQPEmsPorWjHead List </returns>
	public List findDzscQPEmsPorWjHead(String pwd, String tradeCode,
			boolean isExecute);

	// / <summary>
	// / 获得 合同备案数据 来自 Dzsc by tradeCode
	// / </summary>
	// / <param name="tradeCode">经营单位代码</param>
	// / <param name="copEmsNo">企业内部编号</param>
	// / <returns>TempQPEmsTrData</returns>
	public TempQPEmsTrData findTempQPEmsTrData(String pwd, String tradeCode,
			String copEmsNo, boolean isExecute);
}
