package com.bestway.dzsc.contractanalyse.entity;

import com.bestway.common.materialbase.entity.Materiel;

public class TempFinishedProductExportAmount implements java.io.Serializable{
	private Materiel materiel;
	
	private Materiel finishedProduct;
	
	private Integer version;
	/**
	 * 单耗
	 */
	private Double unitWaste;

	/**
	 * 损耗
	 */
	private Double waste;
	private Double exportAmount;
	
	private Double usedAmount;

	public Double getExportAmount() {
		return exportAmount;
	}

	public void setExportAmount(Double exportAmount) {
		this.exportAmount = exportAmount;
	}

	public Materiel getFinishedProduct() {
		return finishedProduct;
	}

	public void setFinishedProduct(Materiel finishedProduct) {
		this.finishedProduct = finishedProduct;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Double getUnitWaste() {
		return unitWaste;
	}

	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	public Double getWaste() {
		return waste;
	}

	public void setWaste(Double waste) {
		this.waste = waste;
	}

	public Double getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(Double usedAmount) {
		this.usedAmount = usedAmount;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
