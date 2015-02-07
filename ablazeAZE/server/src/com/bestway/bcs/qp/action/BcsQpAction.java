package com.bestway.bcs.qp.action;

import java.util.List;

import com.bestway.bcs.qp.entity.TempQPContractData;
import com.bestway.bcs.qp.entity.TempQPDictPorData;

public interface BcsQpAction {

	/**
	 * <summary> 获得是否正在执行的 电子化手册通关备案表头 </summary> <param
	 * name="isExecute">否正在执行</param> <param name="tradeCode">经营单位代码</param>
	 * <returns>BcsQPDictPorHead List </returns>
	 */
	List findBcsQPDictPorHead(String pwd, String tradeCode, boolean isExecute);

	/**
	 * <summary> 获得 通关备案数据 来自 bcs by tradeCode and isExecute </summary> <param
	 * name="tradeCode">经营单位代码</param> <param name="copEmsNo">企业内部编号</param>
	 * <returns>TempQPDictPorData</returns>
	 */
	TempQPDictPorData findTempQPDictPorData(String pwd, String tradeCode,
			String copEmsNo, boolean isExecute);

	/**
	 * <summary> 获得是否正在执行的 电子化手册合同备案表头 </summary> <param
	 * name="isExecute">否正在执行</param> <param name="tradeCode">经营单位代码</param>
	 * <returns>BcsQPContract List </returns>
	 */
	List findBcsQPContract(String pwd, String tradeCode, boolean isExecute);

	/**
	 * 
	 * 
	 * 获得 合同备案数据 来自 bcs by tradeCode </summary> <param
	 * name="tradeCode">经营单位代码</param> <param name="copEmsNo">企业内部编号</param>
	 * <returns>TempQPContractData</returns>
	 */
	TempQPContractData findTempQPContractData(String pwd, String tradeCode,
			String copEmsNo, boolean isExecute);
}
