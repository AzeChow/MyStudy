package com.bestway.bcus.client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;

public class CheckAutoUpgradeState extends Thread {
	String tipMessage = "";

	public CheckAutoUpgradeState(String tipMessage) {
		this.tipMessage = tipMessage;
	}

	public void run() {
		UUID uuid = UUID.randomUUID();
		final String flag = uuid.toString();
		CommonProgress.showProgressDialog(flag, FmMain.getInstance(), false,
				null, 5000);
		CommonProgress.setMessage(flag, "正在进行 [" + tipMessage + "] 请稍等......");

		//
		// 获取主机 host,port
		//
		int yearInt = CasCommonVars.getYearInt();
		String host = CommonVars.getServerName();
		String port = CommonVars.getPort();
		System.out.println("wss host =" + host);
		System.out.println("wss port =" + port);

		while (true) {
			try {
				sleep(2000);
				//sleep(1000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				URL url = new URL("http", host, Integer.parseInt(port),"/" + yearInt + ".html");

				HttpURLConnection httpconn = (HttpURLConnection) url
						.openConnection();

				// 设置连接主机
				//
				// setHostHeader(connection);
				setHostHeader(httpconn);
				System.out.println("wss httpconn host"
						+ httpconn.getRequestProperty("Host"));
				httpconn.setConnectTimeout(1000);// in milliseconds
				// 
				// 进行连接
				//
				try{
					httpconn.connect();
				}catch (Exception e) {
					CommonProgress.setMessage("系统正在重启中,请耐心等等1到2分钟....");
					continue;
				}
				
				System.out.println("wss httpconn url"
						+ httpconn.getURL().toString());

				if (httpconn != null) {
					int status = httpconn.getResponseCode();
					System.out.println("wss httpconn status = " + status);
					 if (status == HttpURLConnection.HTTP_OK) {// 说明存在
					 break;
//					 }
//					if (status != -1) {// 说明存在
//						//CommonProgress.closeProgressDialog(flag);
//						break;
					}else{
						continue;
					}
				} else {
					System.out.println(" wss httpconn =null ");
				}
			} catch (Exception ex) {
				CommonProgress.setMessage("系统正在重启中,请耐心等等1到2分钟.....");
				continue;
			}
		}
		//CommonProgress.closeProgressDialog();
		CommonProgress.closeProgressDialog(flag);
		JOptionPane.showMessageDialog(FmMain.getInstance(),
				tipMessage + "成功!!", "提示！", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * 设置连接主机地址
	 * 
	 * @param urlc
	 */
	public static void setHostHeader(URLConnection urlc) {

		int port = urlc.getURL().getPort();
		String host = urlc.getURL().getHost();
		if (port != -1 && port != 80) {
			host += ":" + String.valueOf(port);
		}
		urlc.setRequestProperty("Host", host);

	}
}
