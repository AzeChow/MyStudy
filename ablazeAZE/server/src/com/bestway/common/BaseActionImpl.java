package com.bestway.common;

import java.security.acl.Acl;
import java.util.List;

public class BaseActionImpl implements BaseAction {
	private String moduleName = null;

	public BaseActionImpl() {
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public Acl getAcl() {
		return null;
	}

	
}
