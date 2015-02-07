package com.bestway.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.gson.Gson;

public class HttpClientUtil {

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		int count = 1;
		for (int i = 0; i < count; i++) {
			/**
			 * 列出文件名和下载文件
			 */
			List<String> listDownloadResult = HttpClientUtil.listFiles("webftp.bsw.com.cn", "update", "online", "/CustomsBaseData", null);
			for (String fileName : listDownloadResult) {
				// System.out.println("fileName:" + fileName);
				HttpClientUtil.download("webftp.bsw.com.cn", "update", "online", "/CustomsBaseData", fileName, "E:\\CustomsBaseData", null);
			}
			System.out.println("-----download file count:" + listDownloadResult.size());
			/**
			 * 上传文件
			 */
			File localDir = new File("E:\\CustomsBaseData");
			File[] files = localDir.listFiles();
			for (File file : files) {
				HttpClientUtil.upload("webftp.bsw.com.cn", "bs0000", "bs0000", "/Upload", file.getName(), "E:\\CustomsBaseData", null);
			}
			/**
			 * 删除本地文件
			 */
			File localDeleteDir = new File("E:\\CustomsBaseData");
			File[] deleteFiles = localDeleteDir.listFiles();
			for (File file : deleteFiles) {
				try {
					FileUtils.forceDelete(file);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			/**
			 * 删除服务器文件
			 */
			List<String> listDeleteResult = HttpClientUtil.listFiles("webftp.bsw.com.cn", "bs0000", "bs0000", "/Upload", null);
			System.out.println("-----delete file count:" + listDeleteResult.size());
			for (String fileName : listDeleteResult) {
				HttpClientUtil.delete("webftp.bsw.com.cn", "bs0000", "bs0000", "/Upload", fileName, null);
			}
		}
		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - beginTime) / 1000.0;
		double perTime = totalTime / count;
		System.out.println("共用时：" + totalTime + "秒，每次" + perTime + "秒");
	}

	private static String readContentFromResponseStream(PostMethod postMethod) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), postMethod.getResponseCharSet()));
			StringBuilder sb = new StringBuilder();
			int chari;
			while ((chari = in.read()) != -1) {
				sb.append((char) chari);
			}
			return sb.toString();
		} catch (Exception ex) {
			Logger.getLogger(HttpClientUtil.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return "";
	}

	// public static List listFiles(String serverName, String userName, String
	// pwd, String dir) {
	// return HttpClientUtil.listFiles(serverName, userName, pwd, dir, null);
	// }
	//
	/**
	 * 获取服务器目录中所有文件列表
	 * 
	 * @param serverName
	 *            服务器端地址
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param dir
	 *            服务器目录
	 * @param proxyParam
	 *            代理设置
	 * @return
	 */
	public static List listFiles(String serverName, String userName, String pwd, String dir, HttpProxyParam proxyParam) {
		List listResult = new ArrayList();
		HttpClient httpClient = new HttpClient();
		String url = "http://" + serverName + "/list";
		PostMethod postMethod = new PostMethod(url);
		if (proxyParam != null) {
			setHttProxy(httpClient, proxyParam);
		}
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		// 执行postMethod
		// 执行postMethod
		NameValuePair[] data = { new NameValuePair("username", userName), new NameValuePair("password", pwd), new NameValuePair("dir", dir) };
		postMethod.setRequestBody(data);
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			// System.out.println("------statusCode:" + statusCode);
			// System.out.println("------getResponseCharSet:" + postMethod
			// .getResponseCharSet());

			String responseContent = readContentFromResponseStream(postMethod);// sb.toString();
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("无法访问到:" + url + ",错误代码:" + statusCode);
			}
			// String responseContent = postMethod.getResponseBodyAsString();
			// System.out.println("---responseContent:" + responseContent);
			Gson gson = new Gson();
			Map resultMap = gson.fromJson(responseContent, Map.class);
			if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
				throw new RuntimeException("返回结果为空");
			}
			// System.out.println("errorcode:" + resultMap.get("errcode"));
			Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
			if (errcode == 0) {
				if (!(resultMap.get("msg") instanceof List)) {
					throw new RuntimeException("返回结果类型错误");
				}
				listResult = (List) resultMap.get("msg");
			} else {
				String errormsg = (String) resultMap.get("msg");
				throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
			}
			// in.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			postMethod.releaseConnection();
		}
		return listResult;
	}

	/**
	 * 从服务器上下载文件
	 * 
	 * @param serverName
	 * @param userName
	 * @param pwd
	 * @param dir
	 * @param fileName
	 *            文件名
	 * @param localDir
	 *            本地地址
	 * @param proxyParam
	 */
	public static void download(String serverName, String userName, String pwd, String dir, String fileName, String localDir, HttpProxyParam proxyParam) {
		HttpClient httpClient = new HttpClient();
		String url = "http://" + serverName + "/download";
		PostMethod postMethod = new PostMethod(url);
		if (proxyParam != null) {
			setHttProxy(httpClient, proxyParam);
		}
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		// 执行postMethod
		// 执行postMethod
		NameValuePair[] data = { new NameValuePair("username", userName), new NameValuePair("password", pwd), new NameValuePair("dir", dir),
				new NameValuePair("file", fileName) };
		postMethod.setRequestBody(data);
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("无法访问到:" + url + ",错误代码:" + statusCode);
			}
			String responseContent = readContentFromResponseStream(postMethod);// postMethod.getResponseBodyAsString();
			// System.out.println("---responseContent:" + responseContent);
			Gson gson = new Gson();
			Map resultMap = gson.fromJson(responseContent, Map.class);
			if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
				throw new RuntimeException("返回结果为空");
			}
			// System.out.println("errorcode:" + resultMap.get("errcode"));
			Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
			if (errcode != 0) {
				String errormsg = (String) resultMap.get("msg");
				throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
			}
			if (!(resultMap.get("msg") instanceof Map)) {
				throw new RuntimeException("返回结果类型错误");
			}
			Map mapContent = (Map) resultMap.get("msg");
			String md5 = (String) mapContent.get("md5");
			String fileContent = (String) mapContent.get("content");
			if (fileContent == null || "".equals(fileContent.trim())) {
				throw new RuntimeException("返回文件：" + fileName + "内容为空。");
			}
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes = decoder.decodeBuffer(fileContent);
			String localmd5 = DigestUtils.md5Hex(bytes);
			if (!localmd5.equals(md5)) {
				throw new RuntimeException("下载文件内容不完整");
			}
			File file = new File(localDir + File.separator + fileName);
			FileUtils.writeByteArrayToFile(file, bytes);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 从服务器上删除文件
	 * 
	 * @param serverName
	 * @param userName
	 * @param pwd
	 * @param dir
	 * @param fileName
	 * @param proxyParam
	 */
	public static void delete(String serverName, String userName, String pwd, String dir, String fileName, HttpProxyParam proxyParam) {
		HttpClient httpClient = new HttpClient();
		String url = "http://" + serverName + "/delete";
		PostMethod postMethod = new PostMethod(url);
		if (proxyParam != null) {
			setHttProxy(httpClient, proxyParam);
		}
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		// 执行postMethod
		// 执行postMethod
		NameValuePair[] data = { new NameValuePair("username", userName), new NameValuePair("password", pwd), new NameValuePair("dir", dir),
				new NameValuePair("file", fileName) };
		postMethod.setRequestBody(data);
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("无法访问到:" + url + ",错误代码:" + statusCode);
			}
			String responseContent = readContentFromResponseStream(postMethod);// postMethod.getResponseBodyAsString();
			// System.out.println("---responseContent:" + responseContent);
			Gson gson = new Gson();
			Map resultMap = gson.fromJson(responseContent, Map.class);
			if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
				throw new RuntimeException("返回结果为空");
			}
			// System.out.println("errorcode:" + resultMap.get("errcode"));
			Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
			if (errcode != 0) {
				String errormsg = (String) resultMap.get("msg");
				throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
			}

		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 向服务器上上传文件
	 * 
	 * @param serverName
	 * @param userName
	 * @param pwd
	 * @param dir
	 * @param fileName
	 * @param localDir
	 * @param proxyParam
	 */
	public static void upload(String serverName, String userName, String pwd, String dir, String fileName, String localDir, HttpProxyParam proxyParam) {
		HttpClient httpClient = new HttpClient();
		String url = "http://" + serverName + "/upload";
		PostMethod postMethod = new PostMethod(url);
		if (proxyParam != null) {
			setHttProxy(httpClient, proxyParam);
		}
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		BASE64Encoder encoder = new BASE64Encoder();
		File file = new File(localDir + File.separator + fileName);
		byte[] bytes = null;
		try {
			bytes = FileUtils.readFileToByteArray(file);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		String md5 = DigestUtils.md5Hex(bytes);
		String fileContent = encoder.encode(bytes);
		// 执行postMethod
		// 执行postMethod
		NameValuePair[] data = { new NameValuePair("username", userName), new NameValuePair("password", pwd), new NameValuePair("dir", dir),
				new NameValuePair("file", fileName), new NameValuePair("md5", md5), new NameValuePair("content", fileContent) };
		postMethod.setRequestBody(data);
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("无法访问到:" + url + ",错误代码:" + statusCode);
			}
			String responseContent = readContentFromResponseStream(postMethod);// postMethod.getResponseBodyAsString();
			// System.out.println("---responseContent:" + responseContent);
			Gson gson = new Gson();
			Map resultMap = gson.fromJson(responseContent, Map.class);
			if (resultMap.isEmpty() || resultMap.get("errcode") == null || resultMap.get("msg") == null) {
				throw new RuntimeException("返回结果为空");
			}
			// System.out.println("errorcode:" + resultMap.get("errcode"));
			Integer errcode = Double.valueOf(resultMap.get("errcode").toString().trim()).intValue();
			if (errcode != 0) {
				String errormsg = (String) resultMap.get("msg");
				throw new RuntimeException("errorcode:" + errcode + ";msg:" + errormsg);
			}

		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 设置HTTP代理
	 * 
	 * @param httpClient
	 * @param proxyParam
	 */
	private static void setHttProxy(HttpClient httpClient, HttpProxyParam proxyParam) {
		// 设置HTTP代理IP和端口
		httpClient.getHostConfiguration().setProxy(proxyParam.getProxyServerAddress(), proxyParam.getProxyPort());
		// 代理认证
		// 代理认证
		if (proxyParam.getProxyUserName() != null && !"".equals(proxyParam.getProxyUserName().trim())) {
			// 使用抢先认证
			httpClient.getParams().setAuthenticationPreemptive(true);
			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(proxyParam.getProxyUserName(), proxyParam.getProxyPwd());
			httpClient.getState().setProxyCredentials(AuthScope.ANY, creds);
		}
	}

	public static HttpProxyParam getHttpProxyParam(String proxyServerAddress, Integer proxyPort, String proxyUserName, String proxyPwd) {
		return new HttpProxyParam(proxyServerAddress, proxyPort, proxyUserName, proxyPwd);
	}

	public static class HttpProxyParam {

		private String proxyServerAddress;
		private Integer proxyPort;
		private String proxyUserName;
		private String proxyPwd;

		public HttpProxyParam(String proxyServerAddress, Integer proxyPort, String proxyUserName, String proxyPwd) {
			this.proxyServerAddress = proxyServerAddress;
			this.proxyPort = proxyPort;
			this.proxyUserName = proxyUserName;
			this.proxyPwd = proxyPwd;
		}

		public String getProxyServerAddress() {
			return proxyServerAddress;
		}

		public void setProxyServerAddress(String proxyServerAddress) {
			this.proxyServerAddress = proxyServerAddress;
		}

		public Integer getProxyPort() {
			return proxyPort;
		}

		public void setProxyPort(Integer proxyPort) {
			this.proxyPort = proxyPort;
		}

		public String getProxyUserName() {
			return proxyUserName;
		}

		public void setProxyUserName(String proxyUserName) {
			this.proxyUserName = proxyUserName;
		}

		public String getProxyPwd() {
			return proxyPwd;
		}

		public void setProxyPwd(String proxyPwd) {
			this.proxyPwd = proxyPwd;
		}

	}

}
