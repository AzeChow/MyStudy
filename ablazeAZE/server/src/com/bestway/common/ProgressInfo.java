package com.bestway.common;

import java.io.Serializable;
import java.text.NumberFormat;

public class ProgressInfo implements Serializable {
	private String methodName;

	private long beginTime;

	private long nowTime;

	private int totalNum;

	private int currentNum = 0;

	private String hintMessage = null;

	private StringBuffer info = new StringBuffer();

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;

	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
		System.out.println("当前进度：" + methodName);
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getNowTime() {
		return nowTime;
	}

	public void setNowTime(long nowTime) {
		this.nowTime = nowTime;
	}

	public String getTimeInfo() {
		double time = CommonUtils.getDoubleByDigit(
				(this.nowTime - this.beginTime) / 1000.0, 0);
		if (this.totalNum == 0) {
			System.out.println("totalNum==0");
			return "";
		}
		double per = ((this.currentNum * 1.0) / (this.totalNum * 1.0));
		if (per <= 0.0) {
			if (this.currentNum == 0.0) {
				System.out.println("currentNum==0");
			}
			System.out.println("per==0 this.totalNum:" + currentNum
					+ "  this.totalNum:" + this.totalNum);
			return "";
		}
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumIntegerDigits(9);
		numberFormat.setMaximumFractionDigits(3);
		double totalTime = CommonUtils.getDoubleByDigit(time / per, 0);
		// "共需" + numberFormat.format(totalTime) + "秒,剩余" +
		// numberFormat.format(totalTime - time) + "秒" +
		return ("  " + getCurrentNum() + "/" + getTotalNum());
	}

	public String getHintMessage() {
		return hintMessage;
	}

	public void setHintMessage(String hintMessage) {
		this.hintMessage = hintMessage;
		info.append(hintMessage + "\n");
	}

	/**
	 * @return the info
	 */
	public StringBuffer getInfo() {
		return info;
	}

}
