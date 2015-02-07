package com.bestway.bls.logic;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.bestway.bls.entity.BlsParameterSet;

public class BswHpServiceClient {
	// private HessianProxyFactory factory = new HessianProxyFactory();

	// private BswMailAction service = null;
	private BlsParameterSet paraSet = null;
	// private String service_url =
	// "http://www3.hpedi.com.cn/HpService/HpService.asmx";
	// private String service_url =
	// "http://www3.hpedi.com.cn/HpServiceTest/HpService.asmx";
	// private String errConnectInfo = "连接不到远程的邮件服务器";
	//
	// private String serverUrl = "http://IP:PORT/bswmail/hessian/service";

	private static BswHpServiceClient serviceClient = null;

	public static BswHpServiceClient getInstance(BlsParameterSet paraSet) {
		if (paraSet == null) {
			throw new RuntimeException("参数不能为空！");
		}
		// if (paraSet.getRemoteHostAddress() == null
		// || "".equals(paraSet.getRemoteHostAddress().trim())) {
		// throw new RuntimeException("远程加签服务器地址不能为空！");
		// }
		// if (paraSet.getRemoteHostPort() == null
		// || "".equals(paraSet.getRemoteHostPort().trim())) {
		// throw new RuntimeException("远程加签服务器端口不能为空！");
		// }
		// if (paraSet.getSeqNo() == null ||
		// "".equals(paraSet.getSeqNo().trim())) {
		// throw new RuntimeException("远程加签读卡唯一号不能为空！");
		// }
		// if (paraSet.getPwd() == null || "".equals(paraSet.getPwd().trim())) {
		// throw new RuntimeException("远程加签读卡密码不能为空！");
		// }
		// if (serviceClient == null){
		// || serviceClient.paraSet == null
		// || !serviceClient.paraSet.getRemoteHostAddress().equals(
		// paraSet.getRemoteHostAddress())
		// || !serviceClient.paraSet.getRemoteHostPort().equals(
		// paraSet.getRemoteHostPort())) {
		serviceClient = new BswHpServiceClient(paraSet);
		// }
		// serviceClient.paraSet = paraSet;
		return serviceClient;
	}

	private BswHpServiceClient(BlsParameterSet paraSet) {
		this.paraSet = paraSet;
	}

	/**
	 * 
	 * 获取调用黄埔服务接口
	 */
	public String hpServiceExec(String serviceInfostring, String handlestring) {
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			String serviceUrl = paraSet.getHpServiceUrl();
			if (serviceUrl == null || "".equals(serviceUrl.trim())) {
				serviceUrl = "http://www3.hpedi.com.cn/HpService/HpService.asmx";
			}
			call.setTargetEndpointAddress(new java.net.URL(serviceUrl));

			// 设置要调用的方法
			call.setOperationName(new QName("http://tempuri.org/", "Exec"));

			// 该方法需要的参数
			call.addParameter(new QName("http://tempuri.org/",
					"xmlsServiceInfo"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "xmlParams"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);

			// 方法的返回值类型
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			call.setUseSOAPAction(true);
			call.setSOAPActionURI("http://tempuri.org/Exec");

			// 调用该方法
			String res = (String) call.invoke(new Object[] { serviceInfostring,
					handlestring });

			System.out.println(" ----------------Result:  " + res.toString());
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			// System.err.println(e);
			throw new RuntimeException("调用黄埔服务接口发生错误！",e);
		}
//		return null;
	}

	// /**
	// *
	// * 取得IC卡号
	// */
	// public String getIcCode() {
	// if (service != null) {
	// try {
	// String result = service.getIcCode();
	// if (result.indexOf("失败") >= 0) {
	// throw new RuntimeException("获取IC卡号失败！" + result);
	// }
	// return result;
	// } catch (Exception e) {
	// throw new RuntimeException(errConnectInfo);
	// }
	// } else {
	// throw new RuntimeException(errConnectInfo);
	// }
	// }

	// /**
	// *
	// * 加签数据
	// */
	// public String signData(String userData) {
	// if (service != null) {
	// try {
	// String password = paraSet.getPwd();
	// if (password == null || "".equals(password.trim())) {
	// throw new RuntimeException("读卡密码为空");
	// }
	// String result = service.signData(userData, password);
	// if (result.indexOf("失败") >= 0) {
	// throw new RuntimeException("加签数据失败！" + result);
	// }
	// return result;
	// } catch (Exception e) {
	// throw new RuntimeException(errConnectInfo);
	// }
	// } else {
	// throw new RuntimeException(errConnectInfo);
	// }
	// }

	// /**
	// * 取得连接地址
	// */
	// public String getServiceUrl(){
	// return this.service_url;
	// }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BswHpServiceClient client = new BswHpServiceClient(null);
		String msg = client
				.hpServiceExec(
						"<?xml version=\"1.0\" encoding=\"gb2312\" ?><ServiceInfo>	<ServiceID>soa://hp.customs.gov.cn/System/GetStatus</ServiceID>	<RequestorDept>4419910037</RequestorDept>	<Role>MnlManifestRole</Role>	<TimeStamp>2009-03-04 07:47:13</TimeStamp>	<Key>MnlEpz</Key>	<CertificateID></CertificateID>	<KeySignature>12345678</KeySignature>	<FullSignature></FullSignature></ServiceInfo>",
						"<?xml version=\"1.0\" encoding=\"gb2312\" ?><ServiceQuery>	<ServiceHandle>b54313c5-1f33-4599-ae4f-7baa199e7401</ServiceHandle></ServiceQuery>");
		System.out.println("---------" + msg);

	}

}
