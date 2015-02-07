package com.bestway.common.fpt.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 商品单价维护
 * 
 * @author Administrator
 * 
 */
public class FptEmail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 发送单类型
	 */
	private String sysType = null;
	/**
	 * 对方邮件地址
	 */
	private String toEmailAdress = null;
	/**
	 * 我方邮件地址
	 */
	private String myEmailAdress = null;
	/*
	 * 标题
	 */
	private String mailSubject = null;
	/**
	 * 正文
	 */
	private String mailbody1 = null;
	private String mailbody2 = null;
	private String mailbody3 = null;
	private String mailbody4 = null;
	private String mailbody5 = null;
	private String mailbody6 = null;
	private String mailbody7 = null;
	private String mailbody8 = null;
	private String mailbody9 = null;
	private String mailbody10 = null;
	/**
	 * 备注
	 */
	private String mailbodyNote = null;
	/**
	 * 附件
	 */
	private String mailFileAffix = null;
	/**
	 * 帐户
	 */
	private String smtpServer = null;
	/**
	 * 收发类型 0-收邮件1-已发邮件 2-已删除
	 */
	private String mailIRType = null;

	public static final String reciveEmail = "0";
	public static final String issueEmail = "1";
	public static final String emailDelete = "2";
	/**
	 * 是否已处理邮件到单据中
	 */
	private Boolean isCancel = false;

	public String getToEmailAdress() {
		return toEmailAdress;
	}

	public void setToEmailAdress(String toEmailAdress) {
		this.toEmailAdress = toEmailAdress;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	//获取语句
	public String getMailbody() {
		if (mailbody1 == null) {
			mailbody1 = "";
		}
		if (mailbody2 == null) {
			mailbody2 = "";
		}
		if (mailbody3 == null) {
			mailbody3 = "";
		}
		if (mailbody4 == null) {
			mailbody4 = "";
		}
		if (mailbody5 == null) {
			mailbody5 = "";
		}
		if (mailbody6 == null) {
			mailbody6 = "";
		}
		if (mailbody7 == null) {
			mailbody7 = "";
		}
		if (mailbody8 == null) {
			mailbody8 = "";
		}
		if (mailbody9 == null) {
			mailbody9 = "";
		}
		if (mailbody10 == null) {
			mailbody10 = "";
		}
		return this.mailbody1 + this.mailbody2 + this.mailbody3 + this.mailbody4
				+ this.mailbody5 + this.mailbody6 + this.mailbody7 + this.mailbody8
				+ this.mailbody9 + this.mailbody10;
	}

	
	//设置语句
	public void setMailbody(String mailbody) {
		if (mailbody == null || "".equals(mailbody.trim())) {
			return;
		}
		int length = mailbody.getBytes().length;
		if (length <= 225) {
			this.setMailbody1(substring(mailbody,0, length));
			this.setMailbody2("");
			this.setMailbody3("");
			this.setMailbody4("");
			this.setMailbody5("");
			this.setMailbody6("");
			this.setMailbody7("");
			this.setMailbody8("");
			this.setMailbody9("");
			this.setMailbody10("");
		} else if (length <= 450) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, length));
			this.setMailbody3("");
			this.setMailbody4("");
			this.setMailbody5("");
			this.setMailbody6("");
			this.setMailbody7("");
			this.setMailbody8("");
			this.setMailbody9("");
			this.setMailbody10("");			
		} else if (length <= 675) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, 450));
			this.setMailbody3(substring(mailbody,450, length));
			this.setMailbody4("");
			this.setMailbody5("");
			this.setMailbody6("");
			this.setMailbody7("");
			this.setMailbody8("");
			this.setMailbody9("");
			this.setMailbody10("");
		} else if (length <= 900) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, 450));
			this.setMailbody3(substring(mailbody,450, 675));
			this.setMailbody4(substring(mailbody,675, length));
			this.setMailbody5("");
			this.setMailbody6("");
			this.setMailbody7("");
			this.setMailbody8("");
			this.setMailbody9("");
			this.setMailbody10("");
		} else if (length <= 1125) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, 450));
			this.setMailbody3(substring(mailbody,450, 675));
			this.setMailbody4(substring(mailbody,675, 900));
			this.setMailbody5(substring(mailbody,900, length));
			this.setMailbody6("");
			this.setMailbody7("");
			this.setMailbody("");
			this.setMailbody9("");
			this.setMailbody10("");
		}else if (length <= 1350) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, 450));
			this.setMailbody3(substring(mailbody,450, 675));
			this.setMailbody4(substring(mailbody,675, 900));
			this.setMailbody5(substring(mailbody,900, 1125));
			this.setMailbody6(substring(mailbody,1125,length));
			this.setMailbody7("");
			this.setMailbody8("");
			this.setMailbody9("");
			this.setMailbody10("");
		} else if (length <= 1575) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, 450));
			this.setMailbody3(substring(mailbody,450, 675));
			this.setMailbody4(substring(mailbody,675, 900));
			this.setMailbody5(substring(mailbody,900, 1125));
			this.setMailbody6(substring(mailbody,1125,1350));
			this.setMailbody7(substring(mailbody,1350,length));
			this.setMailbody8("");
			this.setMailbody9("");
			this.setMailbody10("");
		} else if (length <= 1800) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, 450));
			this.setMailbody3(substring(mailbody,450, 675));
			this.setMailbody4(substring(mailbody,675, 900));
			this.setMailbody5(substring(mailbody,900, 1125));
			this.setMailbody6(substring(mailbody,1125,1350));
			this.setMailbody7(substring(mailbody,1350,1575));
			this.setMailbody8(substring(mailbody,1575,length));
			this.setMailbody9("");
			this.setMailbody10("");
		} else if (length <= 2025) {
			this.setMailbody1(substring(mailbody,0, 225));
			this.setMailbody2(substring(mailbody,225, 450));
			this.setMailbody3(substring(mailbody,450, 675));
			this.setMailbody4(substring(mailbody,675, 900));
			this.setMailbody5(substring(mailbody,900, 1125));
			this.setMailbody6(substring(mailbody,1125,1350));
			this.setMailbody7(substring(mailbody,1350,1575));
			this.setMailbody8(substring(mailbody,1575,1800));
			this.setMailbody9(substring(mailbody,1800,length));
			this.setMailbody10("");
		}else if (length <= 2250) {
			this.setMailbody1(substring(mailbody,0, 225));			
			this.setMailbody2(substring(mailbody,225, 450));			
			this.setMailbody3(substring(mailbody,450, 675));			
			this.setMailbody4(substring(mailbody,675, 900));			
			this.setMailbody5(substring(mailbody,900, 1125));			
			this.setMailbody6(substring(mailbody,1125,1350));			
			this.setMailbody7(substring(mailbody,1350,1575));			
			this.setMailbody8(substring(mailbody,1575,1800));			
			this.setMailbody9(substring(mailbody,1800,2025));			
			this.setMailbody10(substring(mailbody,2025,length));
		}
	}
 
   private  String substring(String str, int start, int end) {
		if (str != null && str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else if (str != null && str.getBytes().length > start){
				return new String(str.getBytes(), start, str.getBytes().length
				- start);
		}
		return "";
	}

	public String getMailFileAffix() {
		return mailFileAffix;
	}

	public void setMailFileAffix(String mailFileAffix) {
		this.mailFileAffix = mailFileAffix;
	}

	public String getMailIRType() {
		return mailIRType;
	}

	public void setMailIRType(String mailIRType) {
		this.mailIRType = mailIRType;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getMailbodyNote() {
		return mailbodyNote;
	}

	public void setMailbodyNote(String mailbodyNote) {
		this.mailbodyNote = mailbodyNote;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public Boolean getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}

	public String getMyEmailAdress() {
		return myEmailAdress;
	}

	public void setMyEmailAdress(String myEmailAdress) {
		this.myEmailAdress = myEmailAdress;
	}

	public String getMailbody1() {
		return mailbody1;
	}

	public void setMailbody1(String mailbody1) {
		this.mailbody1 = mailbody1;
	}

	public String getMailbody2() {
		return mailbody2;
	}

	public void setMailbody2(String mailbody2) {
		this.mailbody2 = mailbody2;
	}

	public String getMailbody3() {
		return mailbody3;
	}

	public void setMailbody3(String mailbody3) {
		this.mailbody3 = mailbody3;
	}

	public String getMailbody4() {
		return mailbody4;
	}

	public void setMailbody4(String mailbody4) {
		this.mailbody4 = mailbody4;
	}

	public String getMailbody5() {
		return mailbody5;
	}

	public void setMailbody5(String mailbody5) {
		this.mailbody5 = mailbody5;
	}

	public String getMailbody6() {
		return mailbody6;
	}

	public void setMailbody6(String mailbody6) {
		this.mailbody6 = mailbody6;
	}

	public String getMailbody7() {
		return mailbody7;
	}

	public void setMailbody7(String mailbody7) {
		this.mailbody7 = mailbody7;
	}

	public String getMailbody8() {
		return mailbody8;
	}

	public void setMailbody8(String mailbody8) {
		this.mailbody8 = mailbody8;
	}

	public String getMailbody9() {
		return mailbody9;
	}

	public void setMailbody9(String mailbody9) {
		this.mailbody9 = mailbody9;
	}

	public String getMailbody10() {
		return mailbody10;
	}

	public void setMailbody10(String mailbody10) {
		this.mailbody10 = mailbody10;
	}
}
