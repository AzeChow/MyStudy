package com.bestway.common.fpt.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.ScmCoc;

public class FptComplex extends BaseScmEntity {
	/**
	 * 根据客户供应商维护商品编码
	 */
	/**
	 * 客户供应商
	 */
	private ScmCoc scmCoc = null;
	/**
	 * 商品编码
	 */
	private String code = null;
	/**
	 * 商品名称
	 */
	private String name = null;
	/**
	 * 商品型号规格
	 */
	private String spec = null;

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
}
