package com.bestway.dzsc.checkcancel.entity;

import java.io.Serializable;
import java.util.List;

public class TempExpQPCavMsgSelectParam implements Serializable {
	private boolean isExg = false;

	private List lsPartData;

	public boolean isExg() {
		return isExg;
	}

	public void setExg(boolean isPorAllImgExg) {
		this.isExg = isPorAllImgExg;
	}

	public List getLsPartData() {
		return lsPartData;
	}

	public void setLsPartData(List lsPartImg) {
		this.lsPartData = lsPartImg;
	}

}
