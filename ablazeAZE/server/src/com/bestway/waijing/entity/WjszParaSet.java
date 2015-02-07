package com.bestway.waijing.entity;

import com.bestway.common.BaseScmEntity;

public class WjszParaSet  extends BaseScmEntity {
	
	/**
	 * 外经服务器地址
	 */
	private String serverName;
	
	/**
	 * 外经服务器端口
	 */
	private String serverPort;	
	/**
	 *密码
	 */
	private String password;
	
    private Integer module;//模式
    private Integer conModule;//合同备案模式
    public Integer getModule() {
		return module;
	}
	public void setModule(Integer module) {
		this.module = module;
	}
	public Integer getConModule() {
		return conModule;
	}
	public void setConModule(Integer conModule) {
		this.conModule = conModule;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
}
