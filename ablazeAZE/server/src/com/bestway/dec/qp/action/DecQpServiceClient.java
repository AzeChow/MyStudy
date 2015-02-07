package com.bestway.dec.qp.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.sys.qp.action.SysQpAction;
import com.bestway.sys.qp.action.SysQpServiceClient;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 连接QP转厂模块 *
 * 
 * @author luosheng
 */
public class DecQpServiceClient {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	private static DecQpAction decQpAction = null;
	private static CspParameterSet paraSet = new CspParameterSet();
	private static final Log log = LogFactory.getLog(DecQpServiceClient.class);
	private static String serverUrl = "http://IP:PORT/dec/hessian/service";

	/**
	 * 获得服务
	 * 
	 * @param paraSet
	 * @return
	 */
	public static DecQpAction getCommonQpAction() {
		return getDecQpAction(null);
	}

	/**
	 * 获得服务
	 * 
	 * @param paraSet
	 * @return
	 */
	public static DecQpAction getDecQpAction(CspParameterSet paraSet) {
		if (paraSet == null) {
			paraSet = new CspParameterSet();
		}
		if (paraSet.getRemoteHostAddress() == null
				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
			throw new RuntimeException("请先设定好远程服务地址");
		} else {
			log.info("Common QP 服务器在  " + paraSet.getRemoteHostAddress());
		}
		SysQpAction sysQpAction = SysQpServiceClient.getSysQpAction(paraSet);
		if (!sysQpAction.checkAllowInvoke(((Company) CommonUtils.getCompany())
				.getCode())) {
			throw new RuntimeException("禁止访问远程服务器！");
		}
		if (decQpAction == null
				|| !paraSet.getRemoteHostAddress().equals(
						DecQpServiceClient.paraSet.getRemoteHostAddress())
				|| !paraSet.getRemoteHostPort().equals(
						DecQpServiceClient.paraSet.getRemoteHostPort())) {
			try {
				String url = serverUrl.replace("IP",
						paraSet.getRemoteHostAddress()).replace("PORT",
						paraSet.getRemoteHostPort());
				log.info("Common QP 服务器 URL = " + url);
				decQpAction = (DecQpAction) factory.create(DecQpAction.class,
						url);
				//
				// 临时存在
				//
				DecQpServiceClient.paraSet = paraSet;
			} catch (Exception e) {
				log.info(e.toString(), e);
				throw new RuntimeException(e);
			}
		}
		return decQpAction;
	}

}
