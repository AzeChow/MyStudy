package com.bestway.dzsc.materialapply.entity;

import java.util.List;

public class TempExpQPMaterialMsgSelectParam implements java.io.Serializable {
	private boolean isPartExg = false;

	private boolean isPartImg = false;

	private boolean isAllImg = false;

	private boolean isAllExg = false;

	private List lsPartData;

	public boolean isAllExg() {
		return isAllExg;
	}

	public void setAllExg(boolean isPorAllExg) {
		this.isAllExg = isPorAllExg;
	}

	public boolean isAllImg() {
		return isAllImg;
	}

	public void setAllImg(boolean isPorAllImg) {
		this.isAllImg = isPorAllImg;
	}

	public boolean isPartExg() {
		return isPartExg;
	}

	public void setPartExg(boolean isPorAllImgExg) {
		this.isPartExg = isPorAllImgExg;
	}

	public List getLsPartData() {
		return lsPartData;
	}

	public void setLsPartData(List lsPartImg) {
		this.lsPartData = lsPartImg;
	}

	public boolean isPartImg() {
		return isPartImg;
	}

	public void setPartImg(boolean isPorPartImgExg) {
		this.isPartImg = isPorPartImgExg;
	}
}
