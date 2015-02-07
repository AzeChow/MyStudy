package com.bestway.invoice.entity;

import java.io.Serializable;

public class TempInvoiceUsedStatInfo implements Serializable {
	/**
	 * 品名
	 */
	private String commName;
	/**
	 * 期初份数
	 */
	private Integer originalPieces;

	/**
	 * 期初开始发票号
	 */
	private String originalBeginCode;

	/**
	 * 期初结束发票号
	 */
	private String originalEndCode;

	/**
	 * 本期领购份数
	 */
	private Integer currentDraftPieces;

	/**
	 * 本期领购发票开始号
	 */
	private String currentDraftBeginCode;

	/**
	 * 本期领购发票结束号
	 */
	private String currentDraftEndCode;

	/**
	 * 本期使用份数
	 */
	private Integer currentUsedPieces;

	/**
	 * 本期使用发票开始号
	 */
	private String currentUsedBeginCode;

	/**
	 * 本期使用发票结束号
	 */
	private String currentUsedEndCode;

	/**
	 * 本期遗失作废份数
	 */
	private Integer currentCanceledPieces;

	/**
	 * 本期遗失作废发票开始号
	 */
	private String currentCanceledBeginCode;

	/**
	 * 本期遗失作废发票结束号
	 */
	private String currentCanceledEndCode;

	/**
	 * 交回未使用(开具)份数
	 */
	private Integer handInNotUsedPieces;

	/**
	 * 期末份数
	 */
	private Integer finalPieces; 

	/**
	 * 期末开始发票号
	 */
	private String finalBeginCode; 

	/**
	 * 期末结束发票号
	 */
	private String finalEndCode; 
	
	/**
	 * 构造函数
	 */
	public TempInvoiceUsedStatInfo(){
		super();
		this.commName="出口商品发票";
	}

	/**
	 * 取得品名
	 * @return commName 品名
	 */
	public String getCommName() {
		return commName;
	}

	/**
	 * 设置
	 * @param commName 品名
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}

	/**
	 * 取得本期遗失作废发票开始号
	 * @return currentCanceledBeginCode 本期遗失作废发票开始号
	 */
	public String getCurrentCanceledBeginCode() {
		return currentCanceledBeginCode;
	}

	/**
	 * 设置本期遗失作废发票开始号
	 * @param currentCanceledBeginCode 本期遗失作废发票开始号
	 */
	public void setCurrentCanceledBeginCode(String currentCanceledBeginCode) {
		this.currentCanceledBeginCode = currentCanceledBeginCode;
	}

	/**
	 * 取得本期遗失作废发票结束号
	 * @return currentCanceledEndCode 本期遗失作废发票开始号
	 */
	public String getCurrentCanceledEndCode() {
		return currentCanceledEndCode;
	}

	/**
	 * 设置本期遗失作废发票结束号
	 * @param currentCanceledEndCode 本期遗失作废发票结束号
	 */
	public void setCurrentCanceledEndCode(String currentCanceledEndCode) {
		this.currentCanceledEndCode = currentCanceledEndCode;
	}

	/**
	 * 取得本期遗失作废份数
	 * @return currentCanceledPieces 本期遗失作废份数
	 */ 
	public Integer getCurrentCanceledPieces() {
		return currentCanceledPieces;
	}

	/**
	 * 设置本期遗失作废份数
	 * @param currentCanceledPieces 本期遗失作废份数
	 */
	public void setCurrentCanceledPieces(Integer currentCanceledPieces) {
		this.currentCanceledPieces = currentCanceledPieces;
	}

	/**
	 * 取得本期领购发票开始号
	 * @return currentDraftBeginCode 本期领购发票开始号
	 */
	public String getCurrentDraftBeginCode() {
		return currentDraftBeginCode;
	}

	/**
	 * 设置本期领购发票开始号
	 * @param currentDraftBeginCode 本期领购发票开始号
	 */
	public void setCurrentDraftBeginCode(String currentDraftBeginCode) {
		this.currentDraftBeginCode = currentDraftBeginCode;
	}

	/**
	 * 取得本期领购发票结束号
	 * @return currentDraftEndCode 本期领购发票结束号
	 */
	public String getCurrentDraftEndCode() {
		return currentDraftEndCode;
	}

	/**
	 * 设置本期领购发票结束号
	 * @param currentDraftEndCode 本期领购发票结束号
	 */
	public void setCurrentDraftEndCode(String currentDraftEndCode) {
		this.currentDraftEndCode = currentDraftEndCode;
	}

	/**
	 * 取得本期领购份数
	 * @return  本期领购份数
	 */
	public Integer getCurrentDraftPieces() {
		return currentDraftPieces;
	}

	/**
	 * 设置本期领购份数
	 * @param currentDraftPieces 本期领购份数
	 */
	public void setCurrentDraftPieces(Integer currentDraftPieces) {
		this.currentDraftPieces = currentDraftPieces;
	}

	/**
	 * 取得本期使用发票开始号
	 * @return currentUsedBeginCode 本期使用发票开始号
	 */
	public String getCurrentUsedBeginCode() {
		return currentUsedBeginCode;
	}

	/**
	 * 设置本期使用发票开始号
	 * @param currentUsedBeginCode 本期使用发票开始号
	 */
	public void setCurrentUsedBeginCode(String currentUsedBeginCode) {
		this.currentUsedBeginCode = currentUsedBeginCode;
	}

	/**
	 * 取得本期使用发票结束号
	 * @return currentUsedEndCode 本期使用发票结束号
	 */
	public String getCurrentUsedEndCode() {
		return currentUsedEndCode;
	}

	/**
	 * 设置本期使用发票结束号
	 * @param currentUsedEndCode 本期使用发票结束号
	 */
	public void setCurrentUsedEndCode(String currentUsedEndCode) {
		this.currentUsedEndCode = currentUsedEndCode;
	}

	/**
	 * 取得本期使用份数
	 * @return currentUsedPieces 本期使用份数
	 */
	public Integer getCurrentUsedPieces() {
		return currentUsedPieces;
	}

	/**
	 * 设置本期使用份数
	 * @param currentUsedPieces 本期使用份数
	 */
	public void setCurrentUsedPieces(Integer currentUsedPieces) {
		this.currentUsedPieces = currentUsedPieces;
	}

	/**
	 * 取得期末开始发票号
	 * @return finalBeginCode 期末开始发票号
	 */
	public String getFinalBeginCode() {
		return finalBeginCode;
	}

	/**
	 * 设置期末开始发票号
	 * @param finalBeginCode 期末开始发票号
	 */
	public void setFinalBeginCode(String finalBeginCode) {
		this.finalBeginCode = finalBeginCode;
	}

	/**
	 * 取得期末结束发票号
	 * @return finalEndCode 期末结束发票号
	 */
	public String getFinalEndCode() {
		return finalEndCode;
	}

	/**
	 * 设置期末结束发票号
	 * @param finalEndCode 期末结束发票号
	 */
	public void setFinalEndCode(String finalEndCode) {
		this.finalEndCode = finalEndCode;
	}

	/**
	 * 取得期末份数
	 * @return finalPieces 期末份数
	 */
	public Integer getFinalPieces() {
		return finalPieces;
	}

	/**
	 * 设置期末份数
	 * @param finalPieces 期末份数
	 */
	public void setFinalPieces(Integer finalPieces) {
		this.finalPieces = finalPieces;
	}

	/**
	 * 取得交回未使用(开具)份数
	 * @return handInNotUsedPieces 交回未使用(开具)份数
	 */
	public Integer getHandInNotUsedPieces() {
		return handInNotUsedPieces;
	}

	/**
	 *设置交回未使用(开具)份数
	 * @param handInNotUsedPieces 交回未使用(开具)份数
	 */
	public void setHandInNotUsedPieces(Integer handInNotUsedPieces) {
		this.handInNotUsedPieces = handInNotUsedPieces;
	}

	/**
	 * 取得期初开始发票号
	 * @return 期初开始发票号
	 */
	public String getOriginalBeginCode() {
		return originalBeginCode;
	}

	/** 
	 * 设置期初开始发票号
	 * @param originalBeginCode 期初开始发票号
	 */
	public void setOriginalBeginCode(String originalBeginCode) {
		this.originalBeginCode = originalBeginCode;
	}

	/**
	 * 取得期初结束发票号
	 * @return originalEndCode 期初结束发票号
	 */
	public String getOriginalEndCode() {
		return originalEndCode;
	}

	/**
	 * 设置期初结束发票号
	 * @param originalEndCode 期初结束发票号
	 */
	public void setOriginalEndCode(String originalEndCode) {
		this.originalEndCode = originalEndCode;
	}

	/**
	 * 取得期初份数
	 * @return 期初份数
	 */
	public Integer getOriginalPieces() {
		return originalPieces;
	}

	/**
	 * 设置期初份数
	 * @param originalPieces 期初份数
	 */
	public void setOriginalPieces(Integer originalPieces) {
		this.originalPieces = originalPieces;
	}


}
