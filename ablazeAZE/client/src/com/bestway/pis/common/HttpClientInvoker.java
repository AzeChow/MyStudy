package com.bestway.pis.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author kpc
 */
public class HttpClientInvoker {

	public static void main(String[] args) throws URISyntaxException {
		// String urlAddress =
		// "http://127.0.0.1:8080/core-war/HttpInvokeTest";//
		String urlAddress = "http://192.168.3.21:8080/esp-war/webdirect.jnlp.jsp?href=http%3A//192.168.1.52%3A8080/esp-war/&useremail=1111111111&userpwd=2222222222";//
		// String urlAddress = "http://ip.cn";
		HttpClientInvoker clientInvoker = new HttpClientInvoker();
		Map<String, String> params = new HashMap();
		String result = clientInvoker.executeMethod(urlAddress, params, null);
		System.out.println("------------" + result);
	}

	public String executeMethod(String url, Map<String, String> params,
			HttpProxyParam proxyParam) {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		if (proxyParam != null) {
			setHttpProxy(httpClient, proxyParam);
		}
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");

		List<NameValuePair> listParams = new ArrayList();
		for (java.util.Map.Entry entry : params.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			listParams.add(new NameValuePair(key, value));
		}
		postMethod.setRequestBody(listParams
				.toArray(new NameValuePair[listParams.size()]));
		try {
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("无法访问到:" + url + ",错误代码:"
						+ statusCode);
			}
			String responseContent = readContentFromResponseStream(postMethod);// postMethod.getResponseBodyAsString();
			return responseContent;

		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			postMethod.releaseConnection();
		}
	}

	private String readContentFromResponseStream(PostMethod postMethod) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					postMethod.getResponseBodyAsStream(),
					postMethod.getResponseCharSet()));
			System.out.println(" ResponseCharSet:"
					+ postMethod.getResponseCharSet());
			StringBuilder sb = new StringBuilder();
			int chari;
			while ((chari = in.read()) != -1) {
				sb.append((char) chari);
			}
			return sb.toString();
		} catch (Exception ex) {
			Logger.getLogger(MsgHttpClientUtil.class.getName()).log(
					Level.SEVERE, null, ex);
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

	/**
	 * 设置HTTP代理
	 *
	 * @param httpClient
	 * @param proxyParam
	 */
	private void setHttpProxy(HttpClient httpClient, HttpProxyParam proxyParam) {
		// 设置HTTP代理IP和端口
		httpClient.getHostConfiguration().setProxy(
				proxyParam.getProxyServerAddress(), proxyParam.getProxyPort());

		// 代理认证
		if (proxyParam.getProxyUserName() != null
				&& !"".equals(proxyParam.getProxyUserName().trim())) {
			// 使用抢先认证
			httpClient.getParams().setAuthenticationPreemptive(true);

			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
					proxyParam.getProxyUserName(), proxyParam.getProxyPwd());

			httpClient.getState().setProxyCredentials(AuthScope.ANY, creds);
		}
	}
}
