package com.bestway.bcs.qp.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcs.qp.entity.BcsQPContract;
import com.bestway.bcs.qp.entity.BcsQPDictPorHead;
import com.bestway.bcs.qp.entity.TempQPContractData;
import com.bestway.bcs.qp.entity.TempQPDictPorData;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.sys.qp.action.SysQpAction;
import com.bestway.sys.qp.action.SysQpServiceClient;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 连接QP转厂模块 *
 * 
 */
public class BcsQpServiceClient {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	private static BcsQpAction bcsQpAction = null;
	private String errConnectInfo = "连接不到远程服务器";
	private static CspParameterSet paraSet = new CspParameterSet();
	private static final Log log = LogFactory.getLog(BcsQpServiceClient.class);
	private static String serverUrl = "http://IP:PORT/pts/hessian/service";
	private static BcsQpServiceClient serviceClient = null;

	public static BcsQpServiceClient getInstance(CspParameterSet paraSet) {
		if (paraSet == null) {
			throw new RuntimeException("远程加签参数不能为空！");
		}
		if (paraSet.getRemoteHostAddress() == null
				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
			throw new RuntimeException("远程服务器地址不能为空！");
		}
		if (paraSet.getRemoteHostPort() == null
				|| "".equals(paraSet.getRemoteHostPort().trim())) {
			throw new RuntimeException("远程服务器端口不能为空！");
		}
		// if (paraSet.getSeqNo() == null ||
		// "".equals(paraSet.getSeqNo().trim())) {
		// throw new RuntimeException("远程加签读卡唯一号不能为空！");
		// }
		// if (paraSet.getPwd() == null || "".equals(paraSet.getPwd().trim())) {
		// throw new RuntimeException("远程加签读卡密码不能为空！");
		// }
		SysQpAction sysQpAction = SysQpServiceClient.getSysQpAction(paraSet);
		if (!sysQpAction.checkAllowInvoke(((Company) CommonUtils.getCompany())
				.getCode())) {
			throw new RuntimeException("禁止访问远程服务器！");
		}
		if (serviceClient == null
				|| serviceClient.paraSet == null
				|| !serviceClient.paraSet.getRemoteHostAddress().equals(
						paraSet.getRemoteHostAddress())
				|| !serviceClient.paraSet.getRemoteHostPort().equals(
						paraSet.getRemoteHostPort())) {
			serviceClient = new BcsQpServiceClient(paraSet);
		}
		serviceClient.paraSet = paraSet;
		return serviceClient;
	}

	private BcsQpServiceClient(CspParameterSet paraSet) {
		if (paraSet == null) {
			throw new RuntimeException("远程加签参数不能为空！");
		}
		if (paraSet.getRemoteHostAddress() == null
				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
			throw new RuntimeException("远程服务器地址不能为空！");
		}
		if (paraSet.getRemoteHostPort() == null
				|| "".equals(paraSet.getRemoteHostPort().trim())) {
			throw new RuntimeException("远程服务器端口不能为空！");
		}
		// if (paraSet.getSeqNo() == null ||
		// "".equals(paraSet.getSeqNo().trim())) {
		// throw new RuntimeException("远程加签读卡唯一号不能为空！");
		// }
		// if (paraSet.getPwd() == null || "".equals(paraSet.getPwd().trim())) {
		// throw new RuntimeException("远程加签读卡密码不能为空！");
		// }
		this.paraSet = paraSet;
		try {
			String url = serverUrl
					.replace("IP", paraSet.getRemoteHostAddress()).replace(
							"PORT", paraSet.getRemoteHostPort());
			bcsQpAction = (BcsQpAction) factory.create(BcsQpAction.class, url);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * <summary> 获得是否正在执行的 电子化手册通关备案表头 </summary> <param
	 * name="isExecute">否正在执行</param> <param name="tradeCode">经营单位代码</param>
	 * <returns>BcsQPDictPorHead List </returns>
	 */
	public List findBcsQPDictPorHead(String pwd, String tradeCode,
			boolean isExecute) {
		if (bcsQpAction != null) {
			try {
				List itemsByQp = this.bcsQpAction.findBcsQPDictPorHead(pwd,
						tradeCode, isExecute);
				List<BcsQPDictPorHead> list = CommonUtils.castListType(
						itemsByQp, BcsQPDictPorHead.class);
				return list;
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * <summary> 获得 通关备案数据 来自 bcs by tradeCode and isExecute </summary> <param
	 * name="tradeCode">经营单位代码</param> <param name="copEmsNo">企业内部编号</param>
	 * <returns>TempQPDictPorData</returns>
	 */
	public TempQPDictPorData findTempQPDictPorData(String pwd,
			String tradeCode, String copEmsNo, boolean isExecute) {
		if (bcsQpAction != null) {
			try {
				TempQPDictPorData itemsByQp = this.bcsQpAction
						.findTempQPDictPorData(pwd, tradeCode, copEmsNo,
								isExecute);
				itemsByQp = (TempQPDictPorData) CommonUtils
						.castObjectType(itemsByQp);
				return itemsByQp;
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * <summary> 获得是否正在执行的 电子化手册合同备案表头 </summary> <param
	 * name="isExecute">否正在执行</param> <param name="tradeCode">经营单位代码</param>
	 * <returns>BcsQPContract List </returns>
	 */
	public List findBcsQPContract(String pwd, String tradeCode,
			boolean isExecute) {
		if (bcsQpAction != null) {
			try {
				List itemsByQp = this.bcsQpAction.findBcsQPContract(pwd,
						tradeCode, isExecute);
				List<BcsQPContract> list = CommonUtils.castListType(itemsByQp,
						BcsQPContract.class);
				return list;
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 
	 * 
	 * 获得 合同备案数据 来自 bcs by tradeCode </summary> <param
	 * name="tradeCode">经营单位代码</param> <param name="copEmsNo">企业内部编号</param>
	 * <returns>TempQPContractData</returns>
	 */
	public TempQPContractData findTempQPContractData(String pwd,
			String tradeCode, String copEmsNo, boolean isExecute) {
		if (bcsQpAction != null) {
			try {
				TempQPContractData itemsByQp = this.bcsQpAction
						.findTempQPContractData(pwd, tradeCode, copEmsNo,
								isExecute);
				itemsByQp = (TempQPContractData) CommonUtils
						.castObjectType(itemsByQp);
				return itemsByQp;
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}
}
