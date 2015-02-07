package com.bestway.cspcard;

import com.bestway.cspcard.entity.ICCardInfo;

public class CSPCardUtils {
	static {
		try {
			System.loadLibrary("CSPCardUtils");
		} catch (Exception ex) {
			throw new RuntimeException("读卡驱动程序CSPCardUtils.dll在系统中不存在");
		}
	}

	private static CSPCardUtils cspCardUtils = new CSPCardUtils();

	/**
	 * ��ȡ����Ϣ
	 * 
	 * @param seqNo
	 * @param pwd
	 * @param OUT
	 * @return
	 */
	public synchronized static int getICCardInfo(String seqNo, String pwd,
			ICCardInfo cardInfo) {
//		System.out.println("1------------getICCardInfo:currentThread id:"
//				+ Thread.currentThread().getId());
		int rtn = -1;
		if (seqNo == null || "".equals(seqNo.trim())) {
			throw new RuntimeException("唯一号不能为空");
		}
		if (pwd == null || "".equals(pwd.trim())) {
			throw new RuntimeException("密码不能为空");
		}
		if (cardInfo == null) {
			throw new RuntimeException("卡信息输出参数不能为空");
		}
		OutParameter out = new OutParameter();
		rtn = cspCardUtils.cspInitCard(seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("打开卡失败，错误代码是:" + rtn + "，错误信息:"
					+ out.getErrMsg());
		}
		rtn = cspCardUtils.cspGetICCode(seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("获取卡号失败，错误代码是:" + rtn + "，错误信息:"
					+ out.getErrMsg());
		}
		cardInfo.setIcCode(out.getIcCode());
		rtn = cspCardUtils.cspCloseCard(seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("关闭卡失败，错误代码是:" + rtn + "，错误信息:"
					+ out.getErrMsg());
		}
		return rtn;
	}

	/**
	 * ��ȡ����Ϣ
	 * 
	 * @param seqNo
	 * @param pwd
	 * @param OUT
	 * @return
	 */
	public synchronized static int getUnitInfo(String seqNo, String pwd,
			int unitType, ICCardInfo cardInfo) {
//		System.out.println("1------------getUnitInfo:currentThread id:"
//				+ Thread.currentThread().getId());
		int rtn = -1;
		if (seqNo == null || "".equals(seqNo.trim())) {
			throw new RuntimeException("唯一号不能为空");
		}
		if (pwd == null || "".equals(pwd.trim())) {
			throw new RuntimeException("密码不能为空");
		}
		if (cardInfo == null) {
			throw new RuntimeException("卡信息输出参数不能为空");
		}
		OutParameter out = new OutParameter();
		rtn = cspCardUtils.cspInitCard(seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("打开卡失败，错误代码是:"+ rtn + "，错误信息:"
					+ out.getErrMsg());
		}
		rtn = cspCardUtils.cspGetUnitInfo(seqNo, unitType, out);
		switch (unitType) {
		case 1:
			if (rtn != 0) {
				throw new RuntimeException("获取证书号失败，错误代码是:"
						+ rtn + "，错误信息:" + out.getErrMsg());
			}
			cardInfo.setCertSeqNo(out.getUnitInfo());
			break;
		case 2:
			if (rtn != 0) {
				throw new RuntimeException(
						"获取申请者名称失败，错误代码是:" + rtn
								+ "，错误信息:" + out.getErrMsg());
			}
			cardInfo.setApplier(out.getUnitInfo());
			break;
		case 3:
			if (rtn != 0) {
				throw new RuntimeException("获取单位名称失败，错误代码是:" + rtn + "，错误信息:"
						+ out.getErrMsg());
			}
			cardInfo.setTradeName(out.getUnitInfo());
			break;
		case 4:
			if (rtn != 0) {
				throw new RuntimeException("获取单位类别代码失败，错误代码是:" + rtn + "，错误信息:"
						+ out.getErrMsg());
			}
			cardInfo.setTradeType(out.getUnitInfo());
			break;
		case 5:
			if (rtn != 0) {
				throw new RuntimeException("获取单位代码失败，错误代码是:" + rtn
						+ "，错误信息:" + out.getErrMsg());
			}
			cardInfo.setTradeCode(out.getUnitInfo());
			break;
		}
		rtn = cspCardUtils.cspCloseCard(seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("关闭卡失败，错误代码是:" + rtn + "，错误信息:"
					+ out.getErrMsg());
		}
		return rtn;
	}

	/**
	 * ��msgContent��Ϣ���м�ǩ
	 * 
	 * @param seqNo
	 * @param pwd
	 * @param msgContent
	 * @param OUT
	 * @return
	 */
	public synchronized static int signMsgData(String seqNo, String pwd,
			String msgContent, ICCardInfo cardInfo) {
//		System.out.println("1------------signMsgData:currentThread id:"
//				+ Thread.currentThread().getId());
		int rtn = -1;
		if (seqNo == null || "".equals(seqNo.trim())) {
			throw new RuntimeException("唯一号不能为空");
		}
		if (pwd == null || "".equals(pwd.trim())) {
			throw new RuntimeException("密码不能为空");
		}
		if (msgContent == null || "".equals(msgContent.trim())) {
			throw new RuntimeException("加签内容不能为空");
		}
		if (cardInfo == null) {
			throw new RuntimeException("卡信息输出参数不能为空");
		}

		OutParameter out = new OutParameter();
		// CSPCardUtils cspCardUtils = new CSPCardUtils();
		// cspCardUtils.cspSignMsgData(seqNo,pwd,msgContent,out,cardInfo);
		rtn = cspCardUtils.cspInitCard(seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("打开卡失败，错误代码是:" + rtn
					+ "，错误信息:" + out.getErrMsg());
		}
		rtn = cspCardUtils.cspVerifyPassword(pwd, seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("验证密码失败，错误代码是:"
					+ rtn + "，错误信息:" + out.getErrMsg());
		}
		rtn = cspCardUtils.cspSignData(seqNo, msgContent, out);
		if (rtn != 0) {
			throw new RuntimeException("数据加签失败，错误代码是:" + rtn
					+ "，错误信息:" + out.getErrMsg());
		}
		cardInfo.setSignData(out.getSignData());
		rtn = cspCardUtils.cspCloseCard(seqNo, out);
		if (rtn != 0) {
			throw new RuntimeException("关闭卡失败，错误代码是:" + rtn + "，错误信息:"
					+ out.getErrMsg());
		}
		return rtn;
	}

	// private native int cspGetICCardInfo(String seqNo, String pwd,
	// OutParameter out, ICCardInfo cardInfo);
	//	
	// private native int cspSignMsgData(String seqNo, String pwd,
	// String msgContent, OutParameter out, ICCardInfo cardInfo);

	/**
	 * �򿪿�
	 * 
	 * @param seqNo
	 * @param out
	 * @return
	 */
	private native int cspInitCard(String seqNo, OutParameter out);

	/**
	 * �رտ�
	 * 
	 * @param seqNo
	 * @param out
	 * @return
	 */
	private native int cspCloseCard(String seqNo, OutParameter out);

	/**
	 * ��֤����
	 * 
	 * @param pwd
	 * @param seqNo
	 * @param out
	 * @return
	 */
	private native int cspVerifyPassword(String pwd, String seqNo,
			OutParameter out);

	/**
	 * ��ǩ�ӿ�
	 * 
	 * @param seqNo
	 * @param msgContent
	 * @param out
	 * @return
	 */
	private native int cspSignData(String seqNo, String msgContent,
			OutParameter out);

	/**
	 * ��ȡ����
	 * 
	 * @param seqNo
	 * @param out
	 * @return
	 */
	private native int cspGetICCode(String seqNo, OutParameter out);

	/**
	 * ��ȡ����Ϣ
	 * 
	 * @param seqNo
	 * @param unitType�������������1
	 *            ֤�飬2 ��������ƣ�3 ��λ��ƣ�4 ��λ�����룬5 ��λ���룻
	 * @param out
	 * @return
	 */
	private native int cspGetUnitInfo(String seqNo, int unitType,
			OutParameter out);

	/**
	 * �����֤��һ��
	 * 
	 * @param seqNo
	 * @param serverRandomNum
	 * @param out
	 * @return
	 */
	private native int cspClientSignData(String seqNo, String serverRandomNum,
			OutParameter out);

	/**
	 * �����֤����
	 * 
	 * @param seqNo
	 * @param clientRandomNum
	 * @param clientRandomNumSign
	 * @param serverCertPathName
	 * @param out
	 * @return
	 */
	private native int cspClientVerify(String seqNo, String clientRandomNum,
			String clientRandomNumSign, String serverCertPathName,
			OutParameter out);
}
