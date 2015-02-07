package com.bestway.sys.qp.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.common.message.entity.CspParameterSet;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 连接QP转厂模块 *
 * 
 * @author luosheng
 */
public class SysQpServiceClient {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	private static SysQpAction sysQpAction = null;
	private static CspParameterSet paraSet = new CspParameterSet();
	private static final Log log = LogFactory
			.getLog(SysQpServiceClient.class);
	private static String serverUrl = "http://IP:PORT/sys/hessian/service";

	/**
	 * 获得服务
	 * 
	 * @param paraSet
	 * @return
	 */
	public static SysQpAction getSysQpAction(CspParameterSet paraSet) {
		if (paraSet == null) {
			paraSet = new CspParameterSet();
		}
		if (paraSet.getRemoteHostAddress() == null
				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
			// paraSet.setRemoteHostAddress("127.0.0.1");
			// log.info("Common QP 服务器在 127.0.0.1 ");
			throw new RuntimeException("请先设定好远程服务地址");
		} else {
			log.info("Common QP 服务器在  " + paraSet.getRemoteHostAddress());
		}
		if (sysQpAction == null
				|| !paraSet.getRemoteHostAddress().equals(
						SysQpServiceClient.paraSet.getRemoteHostAddress())
				|| !paraSet.getRemoteHostPort().equals(
						SysQpServiceClient.paraSet.getRemoteHostPort())) {
			try {
				String url = serverUrl.replace("IP",
						paraSet.getRemoteHostAddress()).replace("PORT",
						paraSet.getRemoteHostPort());
				log.info("Common QP 服务器 URL = " + url);
				sysQpAction = (SysQpAction) factory.create(
						SysQpAction.class, url);
				//
				// 临时存在
				//
				SysQpServiceClient.paraSet = paraSet;
			} catch (Exception e) {
				log.info(e.toString(), e);
				throw new RuntimeException(e);
			}
		}
		return sysQpAction;
	}

}
