package com.bestway.sca.qp.action;

import java.util.List;

import javax.swing.Timer;

import com.bestway.bswmail.qp.entity.MailReceiveStatusInfo;
import com.bestway.bswmail.qp.entity.MailSendStatusInfo;
import com.bestway.bswmail.qp.entity.MailServiceStatus;
import com.bestway.bswmail.qp.entity.StatusInfo;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.dec.qp.action.DecQpServiceClient;
import com.caucho.hessian.client.HessianProxyFactory;

public class ScaCardQpClient {
	private HessianProxyFactory factory = new HessianProxyFactory();

	private ScaCardAction service = null;

	private CspParameterSet paraSet = null;

	private String errConnectInfo = "连接不到远程的加签服务器";
	
	private String serverUrl = "http://IP:PORT/sca/hessian/service";

	private static ScaCardQpClient serviceClient = null;

	public static ScaCardQpClient getInstance(CspParameterSet paraSet) {
		if (paraSet == null) {
			throw new RuntimeException("远程加签参数不能为空！");
		}
		if (paraSet.getRemoteHostAddress() == null
				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
			throw new RuntimeException("远程加签服务器地址不能为空！");
		}
		if (paraSet.getRemoteHostPort() == null
				|| "".equals(paraSet.getRemoteHostPort().trim())) {
			throw new RuntimeException("远程加签服务器端口不能为空！");
		}
		// if (paraSet.getSeqNo() == null ||
		// "".equals(paraSet.getSeqNo().trim())) {
		// throw new RuntimeException("远程加签读卡唯一号不能为空！");
		// }
		// if (paraSet.getPwd() == null || "".equals(paraSet.getPwd().trim())) {
		// throw new RuntimeException("远程加签读卡密码不能为空！");
		// }
		if (serviceClient == null
				|| serviceClient.paraSet == null
				|| !serviceClient.paraSet.getRemoteHostAddress().equals(
						paraSet.getRemoteHostAddress())
				|| !serviceClient.paraSet.getRemoteHostPort().equals(
						paraSet.getRemoteHostPort())) {
			serviceClient = new ScaCardQpClient(paraSet);
		}
		serviceClient.paraSet = paraSet;
		return serviceClient;
	}

	private ScaCardQpClient(CspParameterSet paraSet) {
		if (paraSet == null) {
			throw new RuntimeException("远程加签参数不能为空！");
		}
		if (paraSet.getRemoteHostAddress() == null
				|| "".equals(paraSet.getRemoteHostAddress().trim())) {
			throw new RuntimeException("远程加签服务器地址不能为空！");
		}
		if (paraSet.getRemoteHostPort() == null
				|| "".equals(paraSet.getRemoteHostPort().trim())) {
			throw new RuntimeException("远程加签服务器端口不能为空！");
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
			String url = serverUrl.replace("IP",
					paraSet.getRemoteHostAddress()).replace("PORT",
					paraSet.getRemoteHostPort());
			service = (ScaCardAction) factory.create(ScaCardAction.class,
					url);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

//	public String helloWorld() {
//		if (service != null) {
//			try {
//				return service.helloWorld("中华人民共和国");
//			} catch (Exception e) {
//				throw new RuntimeException(errConnectInfo);
//			}
//		} else {
//			throw new RuntimeException(errConnectInfo);
//		}
//	}

	 /**
     * 
     *获取证书号 
     * 
     */
	public String[] getCertNo(){
		if (service != null) {
			try {
				return service.getCertNo();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
    }
	
	/**
	 * 取得IC卡信息
	 * 
	 * @return
	 */
	public String[] getICCardID() {
		if (service != null) {
			try {
				return service.getCardID();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 加签数据
	 * 
	 * @param seqNo
	 * @param pwd
	 * @param msgContent
	 * @return
	 */
	public String[] signMsgData(String msgContent) {
		if (service != null) {
			try {
				return service.signMsgData(
						paraSet.getRemoteHostICPwd(), msgContent);
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// DxpEusServiceClient client = new DxpEusServiceClient();
		// client.helloWorld();
	}

}
