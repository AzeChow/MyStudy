package com.bestway.common.materialbase.entity;

import java.io.Serializable;

public class ScmCocForInput implements Serializable {
	private static final long serialVersionUID = 398780480042842592L;

	/**
	 * 客户供应商资料
	 */
	private ScmCoc scmCoc = null;
	
	/**
	 * 错误原因
	 */
	private String errinfo = null;

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	
	
}
