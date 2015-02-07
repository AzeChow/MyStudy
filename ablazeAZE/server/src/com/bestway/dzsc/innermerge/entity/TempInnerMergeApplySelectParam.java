package com.bestway.dzsc.innermerge.entity;

import java.util.List;

public class TempInnerMergeApplySelectParam implements java.io.Serializable {
	private boolean isApplyAllImgExg = false;
	
	private boolean isApplyPartImgExg = false;
	
	private boolean isApplyAllImg = false;

	private boolean isApplyAllExg = false;

	private List lsPartImg;

	private List lsPartExg;
	
	private List lsPartImgChange;

	private List lsPartExgChange;

	public boolean isApplyAllExg() {
		return isApplyAllExg;
	}

	public void setApplyAllExg(boolean isPorAllExg) {
		this.isApplyAllExg = isPorAllExg;
	}

	public boolean isApplyAllImg() {
		return isApplyAllImg;
	}

	public void setApplyAllImg(boolean isPorAllImg) {
		this.isApplyAllImg = isPorAllImg;
	}

	public boolean isApplyAllImgExg() {
		return isApplyAllImgExg;
	}

	public void setApplyAllImgExg(boolean isPorAllImgExg) {
		this.isApplyAllImgExg = isPorAllImgExg;
	}

	public List getLsPartExg() {
		return lsPartExg;
	}

	public void setLsPartExg(List lsPartExg) {
		this.lsPartExg = lsPartExg;
	}

	public List getLsPartImg() {
		return lsPartImg;
	}

	public void setLsPartImg(List lsPartImg) {
		this.lsPartImg = lsPartImg;
	}

	public List getLsPartExgChange() {
		return lsPartExgChange;
	}

	public void setLsPartExgChange(List lsPartExgChange) {
		this.lsPartExgChange = lsPartExgChange;
	}

	public List getLsPartImgChange() {
		return lsPartImgChange;
	}

	public void setLsPartImgChange(List lsPartImgChange) {
		this.lsPartImgChange = lsPartImgChange;
	}

	public boolean isApplyPartImgExg() {
		return isApplyPartImgExg;
	}

	public void setApplyPartImgExg(boolean isPorPartImgExg) {
		this.isApplyPartImgExg = isPorPartImgExg;
	}
}
