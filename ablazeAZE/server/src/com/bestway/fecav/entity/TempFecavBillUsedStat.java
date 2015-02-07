package com.bestway.fecav.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TempFecavBillUsedStat implements Serializable{
	/**
	 * 领单日期
	 */
	private Date outerObtainDate;
	/**
	 * 外部领用开始号码
	 */
	private String outerObtainBeginCode;
	/**
	 * 外部领用结束号码
	 */
	private String outerObtainEndCode;
	/**
	 * 外部领用的份数
	 */
	private Integer outerObtainPieces;
	/**
	 * 外部领单人
	 */
	private String outerObtainMan;
	/**
	 * 内部领单人
	 */
	private String innerObtainMan;
	/**
	 * 使用日期
	 */
	private Date usedDate;
	/**
	 * 领用的开始号码
	 */
	private String usedBeginCode;
	/**
	 * 领用的结束号码
	 */
	private String usedEndCode;
	/**
	 * 使用的份数
	 */
	private Integer usedPieces;
	/**
	 * 收单人
	 */
	private String usedMan;
	/**
	 * 作废日期
	 */
	private Date blankOutDate;
	/**
	 * 作废开始号码
	 */
	private String blankOutBeginCode;
	/**
	 * 作废结束号码
	 */
	private String blankOutEndCode;
	/**
	 * 份数
	 */
	private Integer blankOutPieces;
	/**
	 * 交单人
	 */
	private String blankOutMan;
	/**
	 * 获得作废开始号码
	 * @return 作废开始号码
	 */
	public String getBlankOutBeginCode() {
		return blankOutBeginCode;
	}
	/**
	 * 设置作废开始号码
	 * @param blankOutBeginCode 作废开始号码
	 */
	public void setBlankOutBeginCode(String blankOutBeginCode) {
		this.blankOutBeginCode = blankOutBeginCode;
	}
	/**
	 * 获得作废日期
	 * @return 作废日期
	 */
	public Date getBlankOutDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (blankOutDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(blankOutDate));
		}
		return blankOutDate;
	}
	/**
	 * 设置作废日期
	 * @param blankOutDate 作废日期
	 */
	public void setBlankOutDate(Date blankOutDate) {
		this.blankOutDate = blankOutDate;
	}
	/**
	 * 获得结束号码
	 * @return 结束号码
	 */
	public String getBlankOutEndCode() {
		return blankOutEndCode;
	}
	/**
	 * 填写结束号码
	 * @param blankOutEndCode 结束号码
	 */
	public void setBlankOutEndCode(String blankOutEndCode) {
		this.blankOutEndCode = blankOutEndCode;
	}
	/**
	 * 获得交单人
	 * @return 交单人
	 */
	public String getBlankOutMan() {
		return blankOutMan;
	}
	/**
	 * 填写交单人
	 * @param blankOutMan 交单人
	 */
	public void setBlankOutMan(String blankOutMan) {
		this.blankOutMan = blankOutMan;
	}
	/**
	 * 获得份数
	 * @return 份数
	 */
	public Integer getBlankOutPieces() {
		return blankOutPieces;
	}
	/**
	 * 设置份数
	 * @param blankOutPieces 份数
	 */
	public void setBlankOutPieces(Integer blankOutPieces) {
		this.blankOutPieces = blankOutPieces;
	}
	/**
	 * 获得外部领用开始号码
	 * @return 外部领用开始号码
	 */
	public String getOuterObtainBeginCode() {
		return outerObtainBeginCode;
	}
	/**
	 * 设置外部领用开始号码
	 * @param outerObtainBeginCode 外部领用开始号码
	 */
	public void setOuterObtainBeginCode(String outerObtainBeginCode) {
		this.outerObtainBeginCode = outerObtainBeginCode;
	}
	/**
	 * 获得领单日期
	 * @return 领单日期
	 */
	public Date getOuterObtainDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (outerObtainDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(outerObtainDate));
		}
		return outerObtainDate;
	}
	/**
	 * 设置领单日期
	 * @param outerObtainDate 领单日期
	 */
	public void setOuterObtainDate(Date outerObtainDate) {
		this.outerObtainDate = outerObtainDate;
	}
	/**
	 * 获得外部领用结束号码
	 * @return 外部领用结束号码
	 */
	public String getOuterObtainEndCode() {
		return outerObtainEndCode;
	}
	/**
	 * 填写外部领用结束号码
	 * @param outerObtainEndCode 外部领用结束号码
	 */
	public void setOuterObtainEndCode(String outerObtainEndCode) {
		this.outerObtainEndCode = outerObtainEndCode;
	}
	/**
	 * 获得外部领单人
	 * @return 外部领单人 
	 */
	public String getOuterObtainMan() {
		return outerObtainMan;
	}
	/**
	 * 填写外部领单人
	 * @param outerObtainMan 外部领单人
	 */
	public void setOuterObtainMan(String outerObtainMan) {
		this.outerObtainMan = outerObtainMan;
	}
	/**
	 * 获得份数
	 * @return 份数
	 */
	public Integer getOuterObtainPieces() {
		return outerObtainPieces;
	}
	/**
	 * 设置份数
	 * @param outerObtainPieces 份数
	 */
	public void setOuterObtainPieces(Integer outerObtainPieces) {
		this.outerObtainPieces = outerObtainPieces;
	}
	/**
	 * 获得内部领单人
	 * @return 内部领单人
	 */
	public String getInnerObtainMan() {
		return innerObtainMan;
	}
	/**
	 * 填写内部领单人
	 * @param innerObtainMan 内部领单人
	 */
	public void setInnerObtainMan(String innerObtainMan) {
		this.innerObtainMan = innerObtainMan;
	}
	/**
	 * 获得领用的开始号码
	 * @return 领用的开始号码
	 */
	public String getUsedBeginCode() {
		return usedBeginCode;
	}
	/**
	 * 设置领用的开始号码
	 * @param usedBeginCode 领用的开始号码
	 */
	public void setUsedBeginCode(String usedBeginCode) {
		this.usedBeginCode = usedBeginCode;
	}
	/**
	 * 获得使用日期
	 * @return 使用日期
	 */
	public Date getUsedDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (usedDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(usedDate));
		}
		return usedDate;
	}
	/**
	 * 设置使用日期
	 * @param usedDate 使用日期
	 */
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}
	/**
	 * 获得领用的结束号码
	 * @return 领用的结束号码
	 */
	public String getUsedEndCode() {
		return usedEndCode;
	}
	/**
	 * 填写领用的结束号码
	 * @param usedEndCode 领用的结束号码
	 */
	public void setUsedEndCode(String usedEndCode) {
		this.usedEndCode = usedEndCode;
	}
	/**
	 * 获得收单人
	 * @return 收单人
	 */
	public String getUsedMan() {
		return usedMan;
	}
	/**
	 * 填写收单人
	 * @param usedMan 收单人
	 */
	public void setUsedMan(String usedMan) {
		this.usedMan = usedMan;
	}
	/**
	 * 获得使用的份数
	 * @return 使用的份数
	 */
	public Integer getUsedPieces() {
		return usedPieces;
	}
	/**
	 * 设置使用的份数
	 * @param usedPieces 使用的份数 
	 */
	public void setUsedPieces(Integer usedPieces) {
		this.usedPieces = usedPieces;
	}
}
