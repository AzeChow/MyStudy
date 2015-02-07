package com.bestway.common.client.fpt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptEmail;

public class RecivedMail {
	private static RecivedMail fp = null;
	private static FptManageAction fptManageAction = null; // @jve:decl-index=0:

	public static RecivedMail getInstance() {
		if (fp == null) {
			fp = new RecivedMail();
			fptManageAction = (FptManageAction) CommonVars
					.getApplicationContext().getBean(
							"fptManageAction");
		}
		return fp;

	}

	private StringBuffer bodytext = new StringBuffer(); // @jve:decl-index=0:
	private String saveAttachPath = "";// 附件下载后的存放目录 // @jve:decl-index=0:

	public String getBodytext() {
		// String body = "";
		// try {
		// body = decodeText(bodytext.toString());
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return bodytext.toString();
	}

	public String getSaveAttachPath() {
		Properties properties = System.getProperties();
		String s = new String(properties.getProperty("user.dir"));
		String w = "javamailAttach";
		File temp = new File(s + "\\" + w);
		if (!temp.isDirectory()) {
			temp.mkdir();
		}
		saveAttachPath = s + "\\" + w;
		return saveAttachPath;
	}

	public void setSaveAttachPath(String saveAttachPath) {

		this.saveAttachPath = saveAttachPath;
	}

	protected String decodeText(String text)
			throws UnsupportedEncodingException {
		if (text == null)
			return null;
		if (text.startsWith("=?GB") || text.startsWith("=?gb"))
			text = MimeUtility.decodeText(text);
		else
			text = new String(text.getBytes("ISO8859_1"));
		return text;
	}

	// 处理Multipart邮件，包括了保存附件的功能���Ĺ���
	private void handleMultipart(Message msg) throws Exception {
		String disposition;
		BodyPart part;
		Multipart mp;
		System.out.println(" msg.getContent()="
				+ msg.getContent().getClass().toString());
		if ("class java.lang.String".equals(msg.getContent().getClass()
				.toString())) {
			getMailContent(msg);
			return;
		}
		mp = (Multipart) msg.getContent();
		int mpCount = mp.getCount();
		for (int m = 0; m < mpCount; m++) {
			part = mp.getBodyPart(m);
			disposition = part.getDisposition();
			// 判断是否有附件�ж��Ƿ��и���
			System.out.println("disposition=" + disposition);
			if ((disposition != null)
					&& ((disposition.equals(Part.ATTACHMENT)))) {
				// 这个方法负责保存附件��
				saveAttachMent(part);
			} else {
				getMailContent(part);
			}
		}
	}

	private void getMailContent(Part part) {
		try {
			if (part.getContentType() == null) {
				return;
			}
			if (part.isMimeType("text/plain")) {
				bodytext.append(((String) part.getContent()).trim());
			} else if (part.isMimeType("text/html")) {
				// bodytext.append(((String) part.getContent()).trim());
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					getMailContent(multipart.getBodyPart(i));
				}
			} else if (part.isMimeType("message/rfc822")) {
				getMailContent((Part) part.getContent());
			} else {

			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String base64Decoder(String s) throws Exception {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		byte[] b = decoder.decodeBuffer(s);
		return (new String(b));
	}

	public void saveAttachMent(BodyPart mpart) throws Exception {
		String fileName = "";
		String temp = mpart.getFileName();
		if (mpart.getFileName() == null) {
			return;
		}
		if (temp.toLowerCase().indexOf("gb2312") != -1) {
			fileName = MimeUtility.decodeText(temp);
		} else if (temp.substring(8, temp.indexOf("?=")) != null) {
			String s = temp.substring(8, temp.indexOf("?="));
			fileName = base64Decoder(s);
		} else {
			fileName = temp;
		}
		saveFile(fileName, mpart.getInputStream());
		// if (part.isMimeType("multipart/*")) {
		// Multipart mp = (Multipart) part.getContent();
		// for (int i = 0; i < mp.getCount(); i++) {
		// BodyPart mpart = mp.getBodyPart(i);
		// String disposition = mpart.getDisposition();
		// System.out.println("ffffffffffff="+disposition);
		// if ((disposition != null)
		// && ((disposition.equals(Part.ATTACHMENT)) || (disposition
		// .equals(Part.INLINE)))) {
		// fileName = mpart.getFileName();
		// System.out.println("fileName=" + fileName);
		// if (fileName.toLowerCase().indexOf("gb2312") != -1) {
		// fileName = MimeUtility.decodeText(fileName);
		// }
		// saveFile(fileName, mpart.getInputStream());
		// } else if (mpart.isMimeType("multipart/*")) {
		// saveAttachMent(mpart);
		// } else {
		// fileName = mpart.getFileName();
		// if ((fileName != null)
		// && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
		// fileName = MimeUtility.decodeText(fileName);
		// saveFile(fileName, mpart.getInputStream());
		// }
		// }
		// }
		// } else if (part.isMimeType("message/rfc822")) {
		// saveAttachMent((Part) part.getContent());
		// }
	}

	private void saveFile(String fileName, InputStream in) throws Exception {
		// String osName = System.getProperty("os.name");
		String storedir = getSaveAttachPath();
		System.out.println("storedir=" + storedir);
		String separator = "\\";
		// if (osName == null)
		// osName = "";
		// if (osName.toLowerCase().indexOf("win") != -1) {
		// separator = "\\";
		// if (storedir == null || storedir.equals("")) {
		// storedir = "c:\\javamailAttach";
		// } else {
		// separator = "/";
		// storedir = "/tmp";
		// }
		File storefile = new File(storedir + separator + fileName);
		System.out.println("storefile's　path:　" + storefile.toString());
		setSaveAttachPath(storefile.toString());
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(storefile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception("文件保存失败!");
		} finally {
			bos.close();
			bis.close();
		}
	}

	public Message[] receiveMail(String popServer,
			String receiverMailBoxAddress, String username, String password,
			String popport, boolean isAuthenticator) {
		String host = receiverMailBoxAddress;// "pop.mail.yahoo.com.cn";
		Message[] msg = null;
		try {
			Properties props = new Properties();
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

			props.put("mail.pop3.host", host);
			if (host.indexOf("gmail") > 0) {
				props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
				props.setProperty("mail.pop3.socketFactory.fallback", "false");
				if (popport != null && !"".equals(popport)) {
					props.setProperty("mail.pop3.port", popport);
					props.setProperty("mail.pop3.socketFactory.port", popport);
				}
			} else {
				if (popport != null && !"".equals(popport)) {
					props.setProperty("mail.pop3.port", popport);
				}
			}

			// session.setDebug(true);
			// URLName urln = new URLName("pop3", "pop.gmail.com", 995, null,
			// "[邮箱帐号]", "[邮箱密码]");
			// Store store = session.getStore(urln);
			// store.connect();
			Session session = null;
			if (isAuthenticator) {
				MyAuthenticator sa = new MyAuthenticator(username, password);
				// Session mailSession = Session.getInstance(props,sa);
				session = Session.getInstance(props, sa);
			} else {
				session = Session.getInstance(props);
			}
			Store store = session.getStore(popServer);
			try {
				store.connect(host, username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Folder inbox = store.getDefaultFolder().getFolder("INBOX");
			// 设置inbox对象属性为可读写，这样可以控制在读完邮件后直接删除该附件ֱ��ɾ��ø���
			inbox.open(Folder.READ_WRITE);

			msg = inbox.getMessages();

			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
			inbox.fetch(msg, profile);
			System.out.println("msg=" + msg.length);
			for (int i = 0; i < msg.length; i++) {
				bodytext = new StringBuffer();
				handleMultipart(msg[i]);
				FptEmail email = new FptEmail();
				email.setMailSubject(msg[i].getSubject());
				if (msg[i].getSubject().indexOf("发货") >= 0) {
					email.setSysType("2");
				} else if (msg[i].getSubject().indexOf("退货") >= 0) {
					email.setSysType("3");
				} else if (msg[i].getSubject().indexOf("结转") >= 0) {
					email.setSysType("1");
				} else {
					email.setSysType("6");
				}
				String from = "";
				from = decodeText(msg[i].getFrom()[0].toString());
				InternetAddress ia = new InternetAddress(from);
				if (ia.getPersonal() == null) {
					email.setToEmailAdress(ia.getAddress());
				} else {
					email.setToEmailAdress(ia.getPersonal() + '('
							+ ia.getAddress() + ')');
				}
				email.setMailIRType(FptEmail.reciveEmail);
				email.setCreateDate(msg[i].getSentDate());
				email.setSmtpServer(host);
				email.setMailFileAffix(saveAttachPath);
				System.out.println("getBodytext="
						+ getBodytext().replace(" ", "").trim());
				email.setMailbody(getBodytext().replace(" ", "").trim());
				fptManageAction.saveFptEmail(new Request(CommonVars
						.getCurrUser()), email);
			}
			if (store != null) {
				store.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
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
}
