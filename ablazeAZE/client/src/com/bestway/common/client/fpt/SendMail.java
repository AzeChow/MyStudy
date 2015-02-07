package com.bestway.common.client.fpt;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.entity.CompanyOther;

public class SendMail {
	private static SendMail fp = null;

	public static SendMail getInstance() {
		if (fp == null) {
			fp = new SendMail();
		}
		return fp;

	}

	private MimeMessage mimeMsg; // MIME�ʼ�����

	private Session session; // �ʼ��Ự����
	private Properties props; // ϵͳ����
	private boolean needAuth = false; // smtp�Ƿ���Ҫ��֤

	private String username = ""; // smtp��֤�û��������
	private String password = "";

	private Multipart mp; // Multipart����,�ʼ�����,����,���������ݾ���ӵ����к������MimeMessage����

	private String smtpServer = null;

	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	/**
	 * 
	 */
	// public sendMail() {
	// setSmtpHost("smtp.gmail.com");//
	// ���û��ָ���ʼ�������,�ʹ�getConfig���л�ȡgetConfig.mailHost
	// createMimeMessage();
	// }
	public void sendMail(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * @param hostName
	 *            String
	 */
	public void setSmtpHost(String hostName) {
		System.out.println("����ϵͳ���ԣ�smtp设置系统属性：smtp.gmail.com  = "
				+ hostName);
		if (props == null)
			props = System.getProperties(); // ���ϵͳ���Զ���
		props.put("mail.smtp.host", hostName); // ����SMTP���
	}

	/**
	 * @return boolean
	 */
	public boolean createMimeMessage() {
		try {
			System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // ����ʼ��Ự����
		} catch (Exception e) {
			System.err.println("��ȡ�ʼ��Ự����ʱ�������" + e);
			return false;
		}

		System.out.println("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // ����MIME�ʼ�����
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			System.err.println("创建MIME邮件对象失败！" + e);
			return false;
		}
	}

	/**
	 * @param need
	 *            boolean
	 */
	public void setNeedAuth(boolean need, String smtp, String port) {
		System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (smtp.indexOf("gmail") > 0) {
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			if (port != null && !"".equals(port)) {
				props.put("mail.smtp.port", port);
				props.setProperty("mail.smtp.socketFactory.port", port);
			}
		} else {
			if (port != null && !"".equals(port)) {
				props.put("mail.smtp.port", port);
			}
		}
		if (need) {
			props.setProperty("mail.smtp.host", smtp);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	public void setsmtpServer(String smtp) {
		smtpServer = smtp;
	}

	/**
	 * @param mailSubject
	 *            String
	 * @return boolean
	 */
	public void setSubject(String mailSubject) {
		System.out.println("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
		} catch (Exception e) {
			System.err.println("设置邮件主题发生错误！");
		}
	}

	/**
	 * @param mailBody
	 *            String
	 */
	public void setBody(String mailBody) {
		if (mailBody == null)
			return;
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/plain;charset=GB2312");
			mp.addBodyPart(bp);

		} catch (Exception e) {
			System.err.println("设置邮件正文时发生错误！" + e);
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void addFileAffix(String filename) {
		if (filename == null || "".equals(filename))
			return;
		System.out.println("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
		} catch (Exception e) {
			System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void setFrom(String from) {
		System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 
		} catch (Exception e) {
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void setTo(String to) {
		if (to == null)
			return;
		try {
			// String mailList[] = to.split(";");
			// for (int i = 0; i < mailList.length; i++) {
			// mimeMsg.addRecipient(Message.RecipientType.TO,
			// new InternetAddress(mailList[i]));
			// }
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
		} catch (Exception e) {
		}

	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean sendout(boolean isAuthenticator) {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			System.out.println("正在发送邮件....");
			Session mailSession =null;
			if (isAuthenticator) {
				MyAuthenticator sa = new MyAuthenticator(username, password);
				 mailSession = Session.getInstance(props, sa);
			} else {
				 mailSession = Session.getInstance(props, null);
			}
			Transport transport = mailSession.getTransport("smtp");
			try {
				transport.connect((String) props.get(smtpServer), username,
						password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			transport.sendMessage(mimeMsg, mimeMsg
					.getRecipients(Message.RecipientType.TO));
			System.out.println("发送邮件成功！");
			transport.close();

			return true;
		} catch (Exception e) {
			System.err.println("邮件发送失败！" + e);
			return false;
		}
	}

	class MyAuthenticator extends javax.mail.Authenticator {
		private String strUser;
		private String strPwd;

		public MyAuthenticator(String user, String password) {
			this.strUser = user;
			this.strPwd = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(strUser, strPwd);
		}
	}

	public boolean sendEmail(String smtp, String setSubject, String mailbody,
			String toEmail, String fromEmail, String fileAffix, String uerName,
			String passWord, String port, boolean isAuthenticator) {

		sendMail(smtp);
		setNeedAuth(isAuthenticator, smtp, port);
		setSubject(setSubject);
		setBody(mailbody);
		setTo(toEmail);
		setFrom(fromEmail);
		addFileAffix(fileAffix);
		setNamePass(uerName, passWord);
		setsmtpServer(smtp);
		if (sendout(isAuthenticator) == false)
			return false;
		return true;
	}
	
	//数据导入发送邮件
	public void sendEmail(String title,String message){
		CompanyOther other = CommonVars.getOther();
		if(other==null){
			System.out.println("没有条件...null");
			return;
		}
		String sendMail = other.getSendMail();
		if(!other.getIsSendMail() || sendMail==null || "".equals(sendMail)){
			System.out.println("没有条件...");
			return;
		}
		//发送邮件 
		try{
			// 设定所要用的Mail 服务器和所使用的传输协议
            java.util.Properties props = System.getProperties();
            props.put("mail.host", "smtp.gmail.com");
            //这里是我们是用的 smtp
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable","true");
            
            Authenticator auth = new MyAuthenticator("bestway020","bestway888");
            Session mailSession = Session.getDefaultInstance(props, auth);
            Message msg = new MimeMessage(mailSession);
            
            InternetAddress[] address = InternetAddress.parse(sendMail, false);
            msg.setRecipients(Message.RecipientType.TO, address);
            // 设定信中的主题
            msg.setSubject(title);
            // 设定送信的时间
            msg.setSentDate(new Date());
            msg.setDataHandler(new javax.activation.DataHandler("love", "text/html"));
            //这里要注意一下activation用到它了吧 也就是说我们可以发送超文本的邮件 html格式 “love”参数你可以随
            //便指定为任意字符串
            // 设定传送信的MIME Type
            // 送信
            msg.setContent(message, "text/html;charset=gb2312");
            //content 是你要发送的内容 也就是你调用sendMsg（String mailAddress,String title,String content）传
            //进来的的值
            Transport.send(msg);
            //out.println("邮件已顺利传送");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
