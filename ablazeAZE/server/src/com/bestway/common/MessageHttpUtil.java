package com.bestway.common;

import java.util.List;

import com.bestway.bcus.system.dao.CompanyDao;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.constant.HttpMsgTransType;

public class MessageHttpUtil {
	private static CompanyDao companyDao = null;

	static {
		companyDao = (CompanyDao) CommonUtils.getBeanInApplicationContext("companyDao");
	}

	/**
	 * 测试连接
	 * 
	 * @return
	 */
	public  static boolean testConnect(String httpMsgTransType) {
		try {
			listFiles(httpMsgTransType,"/");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 读取目录文件列表
	 * 
	 * @param dir
	 * @return
	 */
	public  static List listFiles(String httpMsgTransType,String dir) {
		checkPrmet(dir);
		CompanyOther p = getCompanyOther();
		String serverName = p.getHttpAddress();
		String userName = "";
		String pwd ="";
		if(HttpMsgTransType.TCS.equals(httpMsgTransType)){
			userName=p.getHttpTcsUserName();
			pwd=p.getHttpTcsPwd();
		}else if(HttpMsgTransType.FPT.equals(httpMsgTransType)){
			userName=p.getHttpFptUserName();
			pwd=p.getHttpFptPwd();
		}else{
			throw new RuntimeException("无效的HTTP报文传输类型："+httpMsgTransType);
		}
		HttpClientUtil.HttpProxyParam proxyParam = null;
		if (p.getProxyAddress() != null && !"".equals(p.getProxyAddress().trim())) {
			proxyParam = HttpClientUtil.getHttpProxyParam(p.getProxyAddress(), p.getProxyPort(), p.getProxyUserName(), p.getProxyPwd());
		}
		return HttpClientUtil.listFiles(serverName, userName, pwd, dir, proxyParam);
	}

	/**
	 * 下载文件
	 * 
	 * @param dir
	 * @param fileName
	 * @param localDir
	 */
	public  static void download(String httpMsgTransType,String dir, String fileName, String localDir) {
		checkPrmet(dir,fileName,localDir);
		CompanyOther p = getCompanyOther();
		String serverName = p.getHttpAddress();
		String userName = "";
		String pwd ="";
		if(HttpMsgTransType.TCS.equals(httpMsgTransType)){
			userName=p.getHttpTcsUserName();
			pwd=p.getHttpTcsPwd();
		}else if(HttpMsgTransType.FPT.equals(httpMsgTransType)){
			userName=p.getHttpFptUserName();
			pwd=p.getHttpFptPwd();
		}else{
			throw new RuntimeException("无效的HTTP报文传输类型："+httpMsgTransType);
		}
		HttpClientUtil.HttpProxyParam proxyParam = null;
		if (p.getProxyAddress() != null && !"".equals(p.getProxyAddress().trim())) {
			proxyParam = HttpClientUtil.getHttpProxyParam(p.getProxyAddress(), p.getProxyPort(), p.getProxyUserName(), p.getProxyPwd());
		}
		HttpClientUtil.download(serverName, userName, pwd, dir, fileName, localDir, proxyParam);
	}

	/**
	 * 删除文件
	 * 
	 * @param dir
	 * @param fileName
	 */
	public  static void delete(String httpMsgTransType,String dir, String fileName) {
		checkPrmet(dir,fileName);
		CompanyOther p = getCompanyOther();
		String serverName = p.getHttpAddress();
		String userName = "";
		String pwd ="";
		if(HttpMsgTransType.TCS.equals(httpMsgTransType)){
			userName=p.getHttpTcsUserName();
			pwd=p.getHttpTcsPwd();
		}else if(HttpMsgTransType.FPT.equals(httpMsgTransType)){
			userName=p.getHttpFptUserName();
			pwd=p.getHttpFptPwd();
		}else{
			throw new RuntimeException("无效的HTTP报文传输类型："+httpMsgTransType);
		}
		HttpClientUtil.HttpProxyParam proxyParam = null;
		if (p.getProxyAddress() != null && !"".equals(p.getProxyAddress().trim())) {
			proxyParam = HttpClientUtil.getHttpProxyParam(p.getProxyAddress(), p.getProxyPort(), p.getProxyUserName(), p.getProxyPwd());
		}
		HttpClientUtil.delete(serverName, userName, pwd, dir, fileName, proxyParam);
	}

	/**
	 * 上传文件
	 * 
	 * @param dir
	 * @param fileName
	 * @param localDir
	 */
	public  static void upload(String httpMsgTransType,String dir, String fileName, String localDir) {
		checkPrmet(dir,fileName,localDir);
		CompanyOther p = getCompanyOther();
		String serverName = p.getHttpAddress();
		String userName = "";
		String pwd ="";
		if(HttpMsgTransType.TCS.equals(httpMsgTransType)){
			userName=p.getHttpTcsUserName();
			pwd=p.getHttpTcsPwd();
		}else if(HttpMsgTransType.FPT.equals(httpMsgTransType)){
			userName=p.getHttpFptUserName();
			pwd=p.getHttpFptPwd();
		}else{
			throw new RuntimeException("无效的HTTP报文传输类型："+httpMsgTransType);
		}
		HttpClientUtil.HttpProxyParam proxyParam = null;
		if (p.getProxyAddress() != null && !"".equals(p.getProxyAddress().trim())) {
			proxyParam = HttpClientUtil.getHttpProxyParam(p.getProxyAddress(), p.getProxyPort(), p.getProxyUserName(), p.getProxyPwd());
		}

		HttpClientUtil.upload(serverName, userName, pwd, dir, fileName, localDir, proxyParam);
	}

	private static CompanyOther getCompanyOther() {
		List list = companyDao.findCompanyOther();
		if (list.isEmpty()) {
			throw new RuntimeException("请先进行系统参数设置。");
		}

		CompanyOther p = (CompanyOther) list.get(0);
		String info = "请先进行系统参数设置 -> 其他参数 -> 报文参数设置 ->";
		if (null == p.getHttpAddress() && "".equals(p.getHttpAddress())) {
			throw new RuntimeException(info + "服务器");
		}

		if (null == p.getHttpTcsUserName() && "".equals(p.getHttpTcsUserName())) {
			throw new RuntimeException(info + "用户名");
		}

		if (null == p.getHttpTcsPwd() && "".equals(p.getHttpTcsPwd())) {
			throw new RuntimeException(info + "密码");
		}

		return p;
	}

	private static void checkPrmet(String dir) {
		if (null == dir && "".equals(dir)) {
			throw new RuntimeException("文件夹目录不能为空");
		}
	}

	private static void checkPrmet(String dir, String fileName) {
		if (null == dir && "".equals(dir)) {
			throw new RuntimeException("文件夹目录不能为空");
		}
		if (null == fileName && "".equals(fileName)) {
			throw new RuntimeException("空文件夹或者文件找不到");
		}
	}

	private static void checkPrmet(String dir, String fileName, String localDir) {
		if (null == dir && "".equals(dir)) {
			throw new RuntimeException("文件夹目录不能为空");
		}
		if (null == fileName && "".equals(fileName)) {
			throw new RuntimeException("空文件夹或者文件找不到");
		}
		if (null == localDir && "".equals(localDir)) {
			throw new RuntimeException("本地文件夹目录不能为空");
		}
	}

}
