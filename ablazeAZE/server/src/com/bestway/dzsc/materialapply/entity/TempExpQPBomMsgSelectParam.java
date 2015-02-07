package com.bestway.dzsc.materialapply.entity;

import java.io.Serializable;
import java.util.List;

public class TempExpQPBomMsgSelectParam implements Serializable {
	private boolean isPart = false;

	private List lsPartData;

	public boolean isPart() {
		return isPart;
	}

	public void setPart(boolean isPorAllImgExg) {
		this.isPart = isPorAllImgExg;
	}

	public List getLsPartData() {
		return lsPartData;
	}

	public void setLsPartData(List lsPartImg) {
		this.lsPartData = lsPartImg;
	}

}
