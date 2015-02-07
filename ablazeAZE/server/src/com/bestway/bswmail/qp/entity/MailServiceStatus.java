package com.bestway.bswmail.qp.entity;

public final class MailServiceStatus implements java.io.Serializable {
	/**
	 * 空闲状态
	 */
	public final static int SPARE = 0;

	/**
	 * 忙碌状态
	 */
	public final static int BUSY = 1;

	/**
	 * 停止状态
	 * 
	 */
	public final static int STOP = 2;

	/**
	 *结束状态
	 */
	public final static int END = 3;

	/**
	 * 关闭状态
	 */
	public final static int CLOSE = 4;

	public final static String getStatusSpec(int status) {
		String result = "";
		switch (status) {
		case 0:
			result = "空闲状态";
			break;
		case 1:
			result = "忙碌状态";
			break;
		case 2:
			result = "停止状态";
			break;
		case 3:
			result = "结束状态";
			break;
		case 4:
			result = "关闭状态";
			break;
		default:
			result = "未识状态";
		}
		return result;
	}

}
