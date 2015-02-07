package com.bestway.common;

import java.security.acl.Acl;

public interface BaseAction {
	Acl getAcl();

	String getModuleName();

	void setModuleName(String moduleName);
}