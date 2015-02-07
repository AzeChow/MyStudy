package com.bestway.customs.common.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 报关单物料信息
 * 
 * @author refdom
 *
 */
public class BaseCustomsFromMateriel extends BaseScmEntity{
	
	/**
	 * 物料主档
	 */
	private Materiel materiel = null; 
	/**
	 * 报关单体
	 */
	private String infoid = null; 
	/**
	 * 工厂料号
	 */
	private Double ptNum = null;
	/**
	 * 总箱数
	 */
	private Double sumBoxNum = null;
	/**
	 * 每箱件数
	 */
	private Double boxNum = null;
	/**
	 * 总净重
	 */
	private Double sumNetWeight = null;
	/**
	 * 总毛重
	 */
	private Double sumGrossWeight = null;
	/**
	 * 实际净重
	 */
	private Double sjNetWeight = null;
	/**
	 * 实际毛重
	 */
	private Double sjGrossWeight = null;
	/**
	 * 客户
	 */
	private ScmCoc scmCoc = null;
	/**
	 * 数量
	 */	
	private Double bgCommAmount = null;   
	/**
	 * 总价
	 */
	private Double bgCommTotalPrice = null; //总价
	/**
	 * 净重
	 */
	private Double bgNetWeight = null;      //净重
	/**
	 * 毛重
	 */
	private Double bgGrossWeight = null;    //毛重
	/**
	 * 第一法定数量
	 */
	private Double bgFirstAmount = null;    //第一法定数量
	/**
	 * 第二法定数量
	 */
	private Double bgSecondAmount = null;   //第二法定数量
	/**
	 * 件数
	 */
	private Integer bgPieces = null;        //件数
	
	
	

	/**
	 * 取得物料主档内容
	 * @return 物料主档
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	/**
	 * 设置物料主档内容
	 * @param materiel 物料主档
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
	
	/**
	 * 取得报关单体
	 * @return 报关单体
	 */
	public String getInfoid() {
		return infoid;
	}
	/**
	 * 设置报关单体
	 * @param infoid 报关单体
	 */
	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}
	/**
	 * 取得每箱件数
	 * @return 每箱件数
	 */
	public Double getBoxNum() {
		return boxNum;
	}
	/**
	 * 设置每箱件数
	 * @param boxNum 每箱件数
	 */
	public void setBoxNum(Double boxNum) {
		this.boxNum = boxNum;
	}
	/**
	 * 取得工厂料号
	 * @return 工厂料号
	 */
	public Double getPtNum() {
		return ptNum;
	}
	/**
	 * 设置工厂料号
	 * @param ptNum 工厂料号
	 */
	public void setPtNum(Double ptNum) {
		this.ptNum = ptNum;
	}
	/**
	 * 取得总箱数
	 * @return 总箱数
	 */
	public Double getSumBoxNum() {
		return sumBoxNum;
	}
	/**
	 * 设置总箱数
	 * @param sumBoxNum 总箱数
	 */
	public void setSumBoxNum(Double sumBoxNum) {
		this.sumBoxNum = sumBoxNum;
	}
	/**
	 * 取得总毛重
	 * @return 总毛重
	 */
	public Double getSumGrossWeight() {
		return sumGrossWeight;
	}
	/**
	 * 设置总毛重
	 * @param sumGrossWeight 总毛重
	 */
	public void setSumGrossWeight(Double sumGrossWeight) {
		this.sumGrossWeight = sumGrossWeight;
	}
	/**
	 * 取得总净重
	 * @return 总净重
	 */
	public Double getSumNetWeight() {
		return sumNetWeight;
	}
	/**
	 * 设置总净重
	 * @param sumNetWeight 总净重
	 */
	public void setSumNetWeight(Double sumNetWeight) {
		this.sumNetWeight = sumNetWeight;
	}
	/**
	 * 取得实际毛重
	 * @return 实际毛重
	 */
	public Double getSjGrossWeight() {
		return sjGrossWeight;
	}
	/**
	 * 取得实际毛重
	 * @param sjGrossWeight 实际毛重
	 */
	public void setSjGrossWeight(Double sjGrossWeight) {
		this.sjGrossWeight = sjGrossWeight;
	}
	/**
	 * 取得实际净重
	 * @return 实际净重
	 */
	public Double getSjNetWeight() {
		return sjNetWeight;
	}
	/**
	 * 设置实际净重
	 * @param sjNetWeight 实际净重
	 */
	public void setSjNetWeight(Double sjNetWeight) {
		this.sjNetWeight = sjNetWeight;
	}
	/**
	 * 取得客户
	 * @return 客户
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}
	/**
	 * 设置客户
	 * @param scmCoc 客户
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}
	public Double getBgCommAmount() {
		return bgCommAmount;
	}
	public void setBgCommAmount(Double bgCommAmount) {
		this.bgCommAmount = bgCommAmount;
	}
	public Double getBgCommTotalPrice() {
		return bgCommTotalPrice;
	}
	public void setBgCommTotalPrice(Double bgCommTotalPrice) {
		this.bgCommTotalPrice = bgCommTotalPrice;
	}
	public Double getBgFirstAmount() {
		return bgFirstAmount;
	}
	public void setBgFirstAmount(Double bgFirstAmount) {
		this.bgFirstAmount = bgFirstAmount;
	}
	public Double getBgGrossWeight() {
		return bgGrossWeight;
	}
	public void setBgGrossWeight(Double bgGrossWeight) {
		this.bgGrossWeight = bgGrossWeight;
	}
	public Double getBgNetWeight() {
		return bgNetWeight;
	}
	public void setBgNetWeight(Double bgNetWeight) {
		this.bgNetWeight = bgNetWeight;
	}
	
	public Double getBgSecondAmount() {
		return bgSecondAmount;
	}
	public void setBgSecondAmount(Double bgSecondAmount) {
		this.bgSecondAmount = bgSecondAmount;
	}
	public Integer getBgPieces() {
		return bgPieces;
	}
	public void setBgPieces(Integer bgPieces) {
		this.bgPieces = bgPieces;
	}
}
