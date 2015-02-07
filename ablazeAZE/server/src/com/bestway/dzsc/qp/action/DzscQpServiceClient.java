package com.bestway.dzsc.qp.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bswmail.qp.action.BswMailAction;
import com.bestway.bswmail.qp.action.BswMailQpClient;
import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.dzsc.qp.entity.DzscQPEmsPorBillHead;
import com.bestway.dzsc.qp.entity.DzscQPEmsPorWjHead;
import com.bestway.dzsc.qp.entity.TempQPEmsPorData;
import com.bestway.dzsc.qp.entity.TempQPEmsTrData;
import com.bestway.sys.qp.action.SysQpAction;
import com.bestway.sys.qp.action.SysQpServiceClient;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 连接QP转厂模块 *
 * 
 * @author luosheng
 */
public class DzscQpServiceClient {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	private static DzscQpAction dzscQpAction = null;
	private String errConnectInfo = "连接不到远程服务器";
	private static CspParameterSet paraSet = new CspParameterSet();
	private static final Log log = LogFactory.getLog(DzscQpServiceClient.class);
	private static String serverUrl = "http://IP:PORT/pts/hessian/service";
	private static DzscQpServiceClient serviceClient = null;

	public static DzscQpServiceClient getInstance(CspParameterSet paraSet) {
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
			serviceClient = new DzscQpServiceClient(paraSet);
		}
		serviceClient.paraSet = paraSet;
		return serviceClient;
	}

	private DzscQpServiceClient(CspParameterSet paraSet) {
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
			dzscQpAction = (DzscQpAction) factory.create(DzscQpAction.class,
					url);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

//	/**
//	 * 获得服务
//	 * 
//	 * @param paraSet
//	 * @return
//	 */
//	public static DzscQpAction getDzscQpAction() {
//		return getFptQpAction(null);
//	}
//
//	/**
//	 * 获得服务
//	 * 
//	 * @param paraSet
//	 * @return
//	 */
//	public static DzscQpAction getFptQpAction(CspParameterSet paraSet) {
//		if (paraSet == null) {
//			paraSet = new CspParameterSet();
//		}
//		if (paraSet.getRemoteHostAddress() == null
//				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
//			paraSet.setRemoteHostAddress("127.0.0.1");
//			log.info("FPT QP 服务器在 127.0.0.1 ");
//		} else {
//			log.info("FPT QP 服务器在  " + paraSet.getRemoteHostAddress());
//		}
//		if (dzscQpAction == null
//				|| !paraSet.getRemoteHostAddress().equals(
//						DzscQpServiceClient.paraSet.getRemoteHostAddress())
//				|| !paraSet.getRemoteHostPort().equals(
//						DzscQpServiceClient.paraSet.getRemoteHostPort())) {
//			try {
//				String url = serverUrl.replace("IP",
//						paraSet.getRemoteHostAddress()).replace("PORT",
//						paraSet.getRemoteHostPort());
//				log.info("FPT QP 服务器 URL = " + url);
//				dzscQpAction = (DzscQpAction) factory.create(
//						DzscQpAction.class, url);
//				//
//				// 临时存在
//				//
//				DzscQpServiceClient.paraSet = paraSet;
//			} catch (Exception e) {
//				log.info(e.toString(), e);
//				throw new RuntimeException(e);
//			}
//		}
//		return dzscQpAction;
//	}

	/**
	 * 获得是否正在执行的 电子手册通关备案表头 </summary> <param name="isExecute">否正在执行</param>
	 * <param name="tradeCode">经营单位代码</param>
	 * 
	 * @param tradeCode
	 * @param isExecute
	 * @return
	 */
	public List findDzscQPEmsPorHead(String pwd, String tradeCode,
			boolean isExecute) {
		if (dzscQpAction != null) {
			try {
				List itemsByQp = this.dzscQpAction.findDzscQPEmsPorHead(pwd,
						tradeCode, isExecute);
				//
				List<DzscQPEmsPorBillHead> list = CommonUtils.castListType(
						itemsByQp, DzscQPEmsPorBillHead.class);
				return list;
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 获得 通关备案数据 来自 Dzsc by tradeCode and isExecute
	 * 
	 * @param tradeCode
	 * @param copEmsNo
	 * @param isExecute
	 * @return
	 */
	public TempQPEmsPorData findTempQPEmsPorData(String pwd, String tradeCode,
			String copEmsNo, boolean isExecute) {
		if (dzscQpAction != null) {
			try {
				TempQPEmsPorData itemsByQp = this.dzscQpAction
						.findTempQPEmsPorData(pwd, tradeCode, copEmsNo,
								isExecute);
				itemsByQp = (TempQPEmsPorData) CommonUtils
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
	 * 获得是否正在执行的 电子手册合同备案表头
	 * 
	 * @param tradeCode
	 * @param isExecute
	 * @return
	 */
	public List findDzscQPEmsPorWjHead(String pwd, String tradeCode,
			boolean isExecute) {
		if (dzscQpAction != null) {
			try {
				List itemsByQp = this.dzscQpAction.findDzscQPEmsPorWjHead(pwd,
						tradeCode, isExecute);
				List<DzscQPEmsPorWjHead> list = CommonUtils.castListType(
						itemsByQp, DzscQPEmsPorWjHead.class);
				return list;
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 获得 合同备案数据 来自 Dzsc by tradeCode
	 * 
	 * @param tradeCode
	 * @param copEmsNo
	 * @param isExecute
	 * @return
	 */
	public TempQPEmsTrData findTempQPEmsTrData(String pwd, String tradeCode,
			String copEmsNo, boolean isExecute) {
		if (dzscQpAction != null) {
			try {
				TempQPEmsTrData itemsByQp = this.dzscQpAction
						.findTempQPEmsTrData(pwd, tradeCode, copEmsNo,
								isExecute);
				itemsByQp = (TempQPEmsTrData) CommonUtils
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
