package com.bestway.common.fpt.btplsinput.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 
 * 深加工下载参数类lyh
 * @author lyh
 * 
 */
public class BtplsDownloadPara extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * ip地址
	 */
	private String ipAddre = null;
	/**
	 * 端口
	 */
	private String port = null;
	
	/**
	 * 客户供应商资料
	 */
	private ScmCoc  scmCoc =null;

	public String getIpAddre() {
		return ipAddre;
	}

	public void setIpAddre(String ipAddre) {
		this.ipAddre = ipAddre;
	}
	
	/**
	 *  获取端口
	 */
	public String getPort() {
		return port;
	}
	/**
	 *  设置端口
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 *  获取客户供应商资料
	 */
	public ScmCoc getScmCoc() {
		return scmCoc;
	}
	/**
	 *  设置客户供应商资料
	 */
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}
	
}
