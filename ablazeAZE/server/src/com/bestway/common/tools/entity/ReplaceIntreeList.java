package com.bestway.common.tools.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReplaceIntreeList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ReplaceIntreeList> list = new ArrayList<ReplaceIntreeList>();
	private String repalcePath = null;
	private String RePalceClass = null;

	public void addReplaceIntreeList(ReplaceIntreeList data) {
		this.list.add(data);
	}

	public List<ReplaceIntreeList> getList() {
		return list;
	}

	public String getRepalcePath() {
		return repalcePath;
	}

	public void setRepalcePath(String repalcePath) {
		this.repalcePath = repalcePath;
	}

	public String getRePalceClass() {
		return RePalceClass;
	}

	public void setRePalceClass(String rePalceClass) {
		RePalceClass = rePalceClass;
	}

}
