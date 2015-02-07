package com.bestway.common.fpt.qp.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.dec.qp.action.DecQpServiceClient;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 连接QP转厂模块 *
 * 
 * @author luosheng
 */
public class FptQpServiceClient {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	private static FptQpAction fptQpAction = null;
	private static CspParameterSet paraSet = new CspParameterSet();
	private static final Log log = LogFactory.getLog(FptQpServiceClient.class);
	private static String serverUrl = "http://IP:PORT/fpt/hessian/service";

	/**
	 * 获得服务
	 * 
	 * @param paraSet
	 * @return
	 */
	public static FptQpAction getFptQpAction() {
		return getFptQpAction(null);
	}

	/**
	 * 获得服务
	 * 
	 * @param paraSet
	 * @return
	 */
	public static FptQpAction getFptQpAction(CspParameterSet paraSet) {
		if (paraSet == null) {
			paraSet = new CspParameterSet();
		}
		if (paraSet.getRemoteHostAddress() == null
				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
			paraSet.setRemoteHostAddress("127.0.0.1");
			log.info("FPT QP 服务器在 127.0.0.1 ");
		} else {
			log.info("FPT QP 服务器在  " + paraSet.getRemoteHostAddress());
		}
		if (fptQpAction == null
				|| !paraSet.getRemoteHostAddress().equals(
						FptQpServiceClient.paraSet.getRemoteHostAddress())
				|| !paraSet.getRemoteHostPort().equals(
						FptQpServiceClient.paraSet.getRemoteHostPort())) {
			try {
				String url = serverUrl.replace("IP",
						paraSet.getRemoteHostAddress()).replace("PORT",
						paraSet.getRemoteHostPort());
				log.info("FPT QP 服务器 URL = " + url);
				fptQpAction = (FptQpAction) factory.create(FptQpAction.class,
						url);
				//
				// 临时存在
				//
				FptQpServiceClient.paraSet = paraSet;
			} catch (Exception e) {
				log.info(e.toString(), e);
				throw new RuntimeException(e);
			}
		}
		return fptQpAction;
	}

}
