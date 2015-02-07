package com.bestway.bcus.system.logic;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bestway.common.CommonUtils;

public class JbcusHelpLogic {	
	public String getServerIP() {
		String servername = CommonUtils.getServerIp();
		return servername;

	}

	public String getServerPort(){
		String port = CommonUtils.getPort();
		return port;
	}



	

	


}
