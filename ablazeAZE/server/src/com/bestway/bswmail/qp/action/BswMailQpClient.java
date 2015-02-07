package com.bestway.bswmail.qp.action;

import java.util.List;

import javax.swing.Timer;

import com.bestway.bswmail.qp.entity.MailReceiveStatusInfo;
import com.bestway.bswmail.qp.entity.MailSendStatusInfo;
import com.bestway.bswmail.qp.entity.MailServiceStatus;
import com.bestway.bswmail.qp.entity.StatusInfo;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.dec.qp.action.DecQpServiceClient;
import com.caucho.hessian.client.HessianProxyFactory;

public class BswMailQpClient {
	private HessianProxyFactory factory = new HessianProxyFactory();

	private BswMailAction service = null;

	private CspParameterSet paraSet = null;

	private String errConnectInfo = "连接不到远程的邮件服务器";
	
	private String serverUrl = "http://IP:PORT/bswmail/hessian/service";

	private static BswMailQpClient serviceClient = null;

	public static BswMailQpClient getInstance(CspParameterSet paraSet) {
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
			serviceClient = new BswMailQpClient(paraSet);
		}
		serviceClient.paraSet = paraSet;
		return serviceClient;
	}

	private BswMailQpClient(CspParameterSet paraSet) {
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
			service = (BswMailAction) factory.create(BswMailAction.class,
					url);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public String helloWorld() {
		if (service != null) {
			try {
				return service.helloWorld("中华人民共和国");
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 开始发送邮件
	 */
	public void startSendMail() {
		if (service != null) {
			try {
				if (service.getSendMailStatus() != MailServiceStatus.BUSY) {
					service.startMailSend(paraSet.getRemoteHostICPwd());
					System.out.println("开始发送邮件成功");
				} else {
					System.out.println("已经开始发送邮件");
				}
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 停止发送邮件
	 */
	public void stopSendMail() {
		if (service != null) {
			try {
				service.stopMailSend();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 开始接收邮件
	 */
	public void startPushMailReceive() {
		if (service != null) {
			try {
				if (service.getPushReceiveMailStatus() != MailServiceStatus.BUSY) {
					service.startPushMailReceive(paraSet.getRemoteHostICPwd());
					System.out.println("开始接收邮件成功");
				} else {
					System.out.println("已经开始接收邮件");
				}
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 停止接收邮件
	 */
	public void stopPushReceiveMail() {
		if (service != null) {
			try {
				service.stopPushMailReceive();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 紧急获取邮件
	 */
	public void activeGet() {
		if (service != null) {
			try {
				service.activeGet(paraSet.getRemoteHostICPwd());
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 获取服务器上回执文件个数
	 * 
	 * @return
	 */
	public int getReturnFileCount() {
		if (service != null) {
			try {
				return service.getReturnFileCount();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 测试邮件服务器和中国电子口岸的连接是否正常
	 * 
	 * @return
	 */
	public boolean testServiceConnection() {
		if (service != null) {
			try {
				return service.testServiceConnection();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 取得发送邮件的状态
	 * 
	 * @return
	 */
	public int getSendMailStatus() {
		if (service != null) {
			try {
				return service.getSendMailStatus();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 取得接收邮件的状态
	 * 
	 * @return
	 */
	public int getPushReceiveMailStatus() {
		if (service != null) {
			try {
				return service.getPushReceiveMailStatus();
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
	public String[] getICCardInfo() {
		if (service != null) {
			try {
				return service.getICCardInfo(paraSet.getSeqNo(), paraSet
						.getRemoteHostICPwd());
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 取得所有IC卡中的信息
	 * 
	 * @return
	 */
	public String[] getAllUnitInfo() {
		if (service != null) {
			try {
				return service.getAllUnitInfo(paraSet.getSeqNo(), paraSet
						.getRemoteHostICPwd());
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
				return service.signMsgData(paraSet.getSeqNo(),
						paraSet.getRemoteHostICPwd(), msgContent);
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 验证IC及密码
	 * 
	 * @param pwd
	 * @return
	 */
	public String testIcCard() {
		if (service != null) {
			try {
				return service.testIcCard(paraSet.getRemoteHostICPwd());
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 取得邮件服务器发送的状态信息
	 * 
	 * @return
	 */
	public MailSendStatusInfo getMailSendStatusInfo() {
		if (service != null) {
			try {
				return service.getMailSendStatusInfo();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 取得邮件服务器发送的状态信息
	 * 
	 * @return
	 */
	public StatusInfo getStatusInfo() {
		if (service != null) {
			try {
				return service.getStatusInfo();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 取得邮件服务器接收的状态信息
	 * 
	 * @return
	 */
	public MailReceiveStatusInfo getMailReceiveStatusInfo() {
		if (service != null) {
			try {
				return service.getMailReceiveStatusInfo();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 清空发送文件列表
	 * 
	 * 
	 */
	public void clearMailSendFileList() {
		if (service != null) {
			try {
				service.clearMailSendFileList();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 请空接收文件列表
	 * 
	 */
	public void clearMailReceiveFileList() {
		if (service != null) {
			try {
				service.clearMailReceiveFileList();
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 
	 * 读取文件发送的日志
	 * 
	 */
	public String getSendLogFileContent(String logFileName) {
		if (service != null) {
			try {
				return service.getSendLogFileContent(logFileName);
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 
	 * 读取回执接收的日志
	 * 
	 */
	public String getReceiveLogFileContent(String logFileName) {
		if (service != null) {
			try {
				return service.getReceiveLogFileContent(logFileName);
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 读取发送日志文件名
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List getSendLogFileNameList(String beginDate, String endDate) {
		if (service != null) {
			try {
				return service.getSendLogFileNameList(beginDate, endDate);
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 读取接收日志文件名
	 * 
	 */
	public List getReceiveLogFileNameList(String beginDate, String endDate) {
		if (service != null) {
			try {
				return service.getReceiveLogFileNameList(beginDate, endDate);
			} catch (Exception e) {
				throw new RuntimeException(errConnectInfo);
			}
		} else {
			throw new RuntimeException(errConnectInfo);
		}
	}

	/**
	 * 
	 * 获取服务器的连接地址
	 */
	public String getConnectionStr() {
		if (service != null) {
			try {
				return service.getConnectionStr();
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
