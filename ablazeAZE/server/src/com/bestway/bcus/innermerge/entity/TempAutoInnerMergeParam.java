package com.bestway.bcus.innermerge.entity;

import java.io.Serializable;

public class TempAutoInnerMergeParam implements Serializable{
	private boolean materialComplexSame = true;

	private boolean materialNameSame = true;

	private boolean materialUnitSame = true;

	private boolean tenComplexSame = true;

	private boolean tenNameSame = true;

	private boolean tenUnitSame = true;

	public boolean isMaterialComplexSame() {
		return materialComplexSame;
	}

	public void setMaterialComplexSame(boolean materialComplexSame) {
		this.materialComplexSame = materialComplexSame;
	}

	public boolean isMaterialNameSame() {
		return materialNameSame;
	}

	public void setMaterialNameSame(boolean materialNameSame) {
		this.materialNameSame = materialNameSame;
	}

	public boolean isMaterialUnitSame() {
		return materialUnitSame;
	}

	public void setMaterialUnitSame(boolean materialUnitSame) {
		this.materialUnitSame = materialUnitSame;
	}

	public boolean isTenComplexSame() {
		return tenComplexSame;
	}

	public void setTenComplexSame(boolean tenComplexSame) {
		this.tenComplexSame = tenComplexSame;
	}

	public boolean isTenNameSame() {
		return tenNameSame;
	}

	public void setTenNameSame(boolean tenNameSame) {
		this.tenNameSame = tenNameSame;
	}

	public boolean isTenUnitSame() {
		return tenUnitSame;
	}

	public void setTenUnitSame(boolean tenUnitSame) {
		this.tenUnitSame = tenUnitSame;
	}

}
